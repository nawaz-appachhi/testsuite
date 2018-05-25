package com.myntra.apiTests.erpservices.serviceability.Helper;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONException;

import com.myntra.apiTests.common.ExceptionHandler;
import com.myntra.apiTests.common.Constants.EnumSCM;
import com.myntra.apiTests.erpservices.lms.Helper.LmsServiceHelper;
import com.myntra.apiTests.erpservices.lms.lmsClient.ServiceType;
import com.myntra.apiTests.erpservices.lms.lmsClient.ServiceabilityRequest;
import com.myntra.commons.exception.ManagerException;
import com.myntra.lms.client.response.CourierServiceabilityResponse;
import com.myntra.lordoftherings.boromir.DBUtilities;
import com.myntra.test.commons.testbase.BaseTest;

/**
 * Created by Shubham Gupta on 7/21/17.
 */
@SuppressWarnings("unchecked")
public class ServiceabilityServiceImpl extends BaseTest {
    
	static Logger log = Logger.getLogger(ServiceabilityServiceImpl.class);

    ServiceabilityHelperUtil helper = new ServiceabilityHelperUtil();
    LmsServiceHelper lmsServiceHelper = new LmsServiceHelper();

    public String updateServiceability(ServiceabilityRequest request) throws IOException, SQLException, ClassNotFoundException, InterruptedException, XMLStreamException, ManagerException, JSONException, JAXBException {
            insertshipmentSortationConfig( request);
            Arrays.asList(request.getCouriers()).forEach(courier -> {
                try {
                    insertServiceabilityConfig( request, courier, helper.getServiceabiityConfigCombinations(courier));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
            String dhHub = helper.getHubForWHnServiceType(request.getWarehouseId(),"DELIVERY");
            String rtHub = helper.getHubForWHnServiceType(request.getWarehouseId(),"PICKUP");
            String regionCode = helper.getRegion(request.getPincode());
            helper.deleteServiceabilityPreferences(rtHub,regionCode);
            helper.deleteServiceabilityPreferences(dhHub,regionCode);
            for (int i =0;i<request.getCouriers().length;i++){
                insertServiceabilityPreferences(request, request.getCouriers()[i], helper.getServiceabiityPreferenceCombinations(request.getCouriers()[i]),i);
            }

            Arrays.asList(request.getCouriers()).forEach(courier -> {
                try {
                    insertHubCourierCapacity(request.getWarehouseId(),request.getPincode(),courier, helper.getServiceTypeForCapacity(courier.toString()));
                    insertHubTat(request.getWarehouseId(),request.getPincode(),courier, helper.getServiceTypeForTat());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
            if (request.isFulshRedis()) {
                /*helper.flushLMSRedis(request.getEnv(), 6379);
                helper.flushLMSRedis(request.getEnv(), 6378);*/
                ExceptionHandler.handleEquals(((CourierServiceabilityResponse)lmsServiceHelper.refreshServiceabilityForRegion.apply(regionCode)).
                        getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to trigger Refresh serviceability for region : "+regionCode);
            }
        return "SUCCESS";
    }



    public void insertshipmentSortationConfig( ServiceabilityRequest request) throws SQLException {
        for(String courier:request.getCouriers()){
            try {
                String sortationId = helper.getShipmentSortationId(request.getPincode(),courier);
                log.info("Sortation id:"+sortationId);
                log.info("Deleting serviceability_config if any exits for the sortation id");
                helper.deleteServiceabilityConfig(sortationId,helper.getHubForWHnServiceType(request.getWarehouseId(),"DELIVERY"));
                helper.deleteServiceabilityConfig(sortationId,helper.getHubForWHnServiceType(request.getWarehouseId(),"PICKUP"));
            }catch (Exception e){
                log.info("Insert data into shipment_sortation_config");
                Map<String, String> data = helper.getPincodeData( request.getPincode());
                String values = "("+ request.getPincode() + ", '" + courier + "', '" + data.get("cityCode") + "', '" + data.get("cityName") + "', '" + data.get("areaCode") + "', " +
                        "'" + data.get("areaName") + "', 'erpMessageQueue')";
                String insertq = "INSERT INTO `shipment_sortation_config` (`pincode`, `courier_code`, `city_code`, `city_name`, `area_code`, `area_name`, `created_by`) VALUES " + values;
                log.info("Insert records into serviceability_config");
                DBUtilities.exUpdateQuery(insertq, "myntra_lms");
            }
        }
    }

    public void insertServiceabilityConfig( ServiceabilityRequest request,String courierCode, List<List<Object>> data) throws SQLException {
        String dhHub = helper.getHubForWHnServiceType(request.getWarehouseId(),"DELIVERY");
        String rtHub = helper.getHubForWHnServiceType(request.getWarehouseId(),"PICKUP");
        String sortationId = helper.getShipmentSortationId(request.getPincode(),courierCode);
        StringBuilder values = new StringBuilder();
        if (request.getPaymentMode()!=null && (request.getPaymentMode().equals("cod")||request.getPaymentMode().equals("on"))) {
            data.stream().
                    filter(x -> (x.get(2).equals(request.getPaymentMode()))).
                    filter(x -> !(x.get(0).equals("OPEN_BOX_PICKUP") || x.get(0).equals("CLOSED_BOX_PICKUP"))).
                    forEach(x -> {
                        values.append(",(" + sortationId + ", '" + dhHub + "', '" + x.get(0) + "', '" + x.get(1) + "', '" + x.get(2) + "', 1, 1, 1, 1, 1, 20000, 30000, 'util')");
                    });
            data.stream().
                    filter(x -> (x.get(2).equals(request.getPaymentMode()))).
                    filter(x -> (x.get(0).equals("OPEN_BOX_PICKUP") || x.get(0).equals("CLOSED_BOX_PICKUP"))).
                    filter(x-> x.get(2).equals("on")).
                    forEach(x -> {
                        values.append(",(" + sortationId + ", '" + rtHub + "', '" + x.get(0) + "', '" + x.get(1) + "', '" + x.get(2) + "', 1, 1, 1, 1, 1, 20000, 30000, 'util')");
                    });
        }else {
            data.stream().
                    filter(x -> !(x.get(0).equals("OPEN_BOX_PICKUP") || x.get(0).equals("CLOSED_BOX_PICKUP"))).
                    forEach(x -> {
                        values.append(",(" + sortationId + ", '" + dhHub + "', '" + x.get(0) + "', '" + x.get(1) + "', '" + x.get(2) + "', 1, 1, 1, 1, 1, 20000, 30000, 'util')");
                    });
            data.stream().
                    filter(x -> (x.get(0).equals("OPEN_BOX_PICKUP") || x.get(0).equals("CLOSED_BOX_PICKUP"))).
                    filter(x-> x.get(2).equals("on")).
                    forEach(x -> {
                        values.append(",(" + sortationId + ", '" + rtHub + "', '" + x.get(0) + "', '" + x.get(1) + "', '" + x.get(2) + "', 1, 1, 1, 1, 1, 20000, 30000, 'util')");
                    });
        }
        String insertq = "INSERT INTO `serviceability_config` (`shipment_sortation_config_id`, `hub_code`, `service_type`, `shipping_method`, `payment_mode`, `is_fragile_supported`, " +
                "`is_hazmat_supported`, `is_large_supported`, `is_jewellery_supported`, `is_normal_supported`, `cod_limit`, `item_value_limit`, `created_by`) VALUES "+values.toString().replaceFirst(",","");
        log.info("Insert data into serviceability_config");
        DBUtilities.exUpdateQuery(insertq, "myntra_lms");
    }

    public void insertServiceabilityPreferences( ServiceabilityRequest request, String courierCode, List<List<Object>> data, int preference) throws SQLException {
        String dhHub = helper.getHubForWHnServiceType(request.getWarehouseId(),"DELIVERY");
        String rtHub = helper.getHubForWHnServiceType(request.getWarehouseId(),"PICKUP");
        String regionCode = helper.getRegion(request.getPincode());
        StringBuilder values = new StringBuilder();
        if (request.getPaymentMode()!=null && (request.getPaymentMode().equals("cod")||request.getPaymentMode().equals("on"))) {
            data.stream().
                    filter(x->(x.get(2).equals(request.getPaymentMode()))).
                    filter(x -> !(x.get(0).equals("OPEN_BOX_PICKUP") || x.get(0).equals("CLOSED_BOX_PICKUP"))).
                    forEach(x -> {
                        values.append(",('" + dhHub + "', '" + regionCode + "', '" + courierCode + "','" + x.get(0) + "', '" + x.get(1) + "', '" + x.get(2) + "', '" + x.get(3) + "', " + preference + ", 'util')");
                    });
            data.stream().
                    filter(x->(x.get(2).equals(request.getPaymentMode()))).
                    filter(x -> (x.get(0).equals("OPEN_BOX_PICKUP") || x.get(0).equals("CLOSED_BOX_PICKUP"))).
                    filter(x-> x.get(2).equals("on")).
                    forEach(x -> {
                        values.append(",('" + rtHub + "', '" + regionCode + "', '" + courierCode + "','" + x.get(0) + "', '" + x.get(1) + "', '" + x.get(2) + "', '" + x.get(3) + "', " + preference + ", 'util')");
                    });
        }else {
            data.stream().
                    filter(x -> !(x.get(0).equals("OPEN_BOX_PICKUP") || x.get(0).equals("CLOSED_BOX_PICKUP"))).
                    forEach(x -> {
                        values.append(",('" + dhHub + "', '" + regionCode + "', '" + courierCode + "','" + x.get(0) + "', '" + x.get(1) + "', '" + x.get(2) + "', '" + x.get(3) + "', " + preference + ", 'util')");
                    });
            data.stream().
                    filter(x -> (x.get(0).equals("OPEN_BOX_PICKUP") || x.get(0).equals("CLOSED_BOX_PICKUP"))).
                    filter(x-> x.get(2).equals("on")).
                    forEach(x -> {
                        values.append(",('" + rtHub + "', '" + regionCode + "', '" + courierCode + "','" + x.get(0) + "', '" + x.get(1) + "', '" + x.get(2) + "', '" + x.get(3) + "', " + preference + ", 'util')");
                    });
        }
        String insertq = "INSERT INTO `hub_courier_preference` (`hub_code`, `region_code`, `courier_code`,`service_type`, `shipping_method`, `payment_mode`, `item_attribute`, `preference`, `created_by`) VALUES "+values.toString().replaceFirst(",","");
        log.info("Insert data into hub_courier_preference");
        DBUtilities.exUpdateQuery(insertq, "myntra_lms");
    }

    public void insertHubCourierCapacity( String warehouseId, String pincode, String courierCode, List<String> data) throws SQLException {
        String dhHub = helper.getHubForWHnServiceType(warehouseId,"DELIVERY");
        String rtHub = helper.getHubForWHnServiceType(warehouseId,"PICKUP");
        String regionCode = helper.getRegion( pincode);
        helper.deleteCourierCapacityConfig( dhHub,regionCode,courierCode);
        helper.deleteCourierCapacityConfig( rtHub,regionCode,courierCode);
        StringBuilder values = new StringBuilder();
        data.stream().
                filter(x->!(x.equals(ServiceType.OPEN_BOX_PICKUP))||!(x.equals(ServiceType.CLOSED_BOX_PICKUP))).
                forEach(x->{
                    values.append(",('"+dhHub+"', '"+regionCode+"', '"+courierCode+"', '"+x+"', 50000, 'util') ");
                });
        data.stream().
                filter(x->(x.equals(ServiceType.OPEN_BOX_PICKUP))||x.equals(ServiceType.CLOSED_BOX_PICKUP)).
                forEach(x->{
                    values.append(",('"+rtHub+"', '"+regionCode+"', '"+courierCode+"', '"+x+"', 50000, 'util') ");
                });
        String insertq = "INSERT INTO `hub_courier_capacity_config` (`hub_code`, `region_code`, `courier_code`, `courier_service_type`, `capacity`, `created_by`) VALUES "+values.toString().replaceFirst(",","");
        log.info("Insert data into hub_courier_capacity_config");
        DBUtilities.exUpdateQuery(insertq, "myntra_lms");
    }

    public void insertHubTat( String warehouseId, String pincode, String courierCode, List<String> data) throws SQLException {
        String dhHub = helper.getHubForWHnServiceType(warehouseId,"DELIVERY");
        helper.deleteTat(dhHub,pincode,courierCode);
        StringBuilder values = new StringBuilder();
        data.stream().
                filter(x->x.equals("NORMAL"))
                .forEach(x->{
                    values.append(", ('"+dhHub+"', "+pincode+", '"+courierCode+"', '"+x+"', 120, '21:45:00', 'util')");
                });
        data.stream().
                filter(x->x.equals("EXPRESS"))
                .forEach(x->{
                    values.append(", ('"+dhHub+"', "+pincode+", '"+courierCode+"', '"+x+"', 24, '21:45:00', 'util')");
                });
        data.stream().
                filter(x->x.equals("SDD"))
                .forEach(x->{
                    values.append(", ('"+dhHub+"', "+pincode+", '"+courierCode+"', '"+x+"', 6, '21:45:00', 'util')");
                });
        String insertq = "INSERT INTO `hub_tat` (`hub_code`, `pincode`, `courier_code`, `shipping_method`, `tat_time_in_hours`, `shipping_cutoff`, `created_by`) VALUES"+values.toString().replaceFirst(",","");
        log.info("Insert data into hub_tat");
        DBUtilities.exUpdateQuery(insertq, "myntra_lms");
    }

}
