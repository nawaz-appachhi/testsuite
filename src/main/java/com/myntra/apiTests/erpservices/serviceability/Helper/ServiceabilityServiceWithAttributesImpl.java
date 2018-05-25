package com.myntra.apiTests.erpservices.serviceability.Helper;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONException;

import com.myntra.apiTests.common.ExceptionHandler;
import com.myntra.apiTests.common.Constants.EnumSCM;
import com.myntra.apiTests.erpservices.lms.Helper.LmsServiceHelper;
import com.myntra.apiTests.erpservices.lms.lmsClient.ItemAttributeEntry;
import com.myntra.apiTests.erpservices.lms.lmsClient.ServiceType;
import com.myntra.apiTests.erpservices.lms.lmsClient.ServiceabilityRequestWithAttributesEntry;
import com.myntra.apiTests.erpservices.lms.lmsClient.ShippingMethod;
import com.myntra.apiTests.erpservices.lms.lmsClient.ShippingMethodEntry;
import com.myntra.commons.exception.ManagerException;
import com.myntra.lms.client.response.CourierServiceabilityResponse;
import com.myntra.lordoftherings.boromir.DBUtilities;
import com.myntra.test.commons.testbase.BaseTest;

/**
 * Created by Shubham Gupta on 7/21/17.
 */
public class ServiceabilityServiceWithAttributesImpl extends BaseTest {
    
	static Logger log = Logger.getLogger(ServiceabilityServiceWithAttributesImpl.class);
    ServiceabilityHelperUtil helper = new ServiceabilityHelperUtil();
    LmsServiceHelper lmsServiceHelper = new LmsServiceHelper();

    @SuppressWarnings("unchecked")
    public String updateServiceability(ServiceabilityRequestWithAttributesEntry request) throws IOException, SQLException, ClassNotFoundException, InterruptedException, XMLStreamException, ManagerException, JSONException, JAXBException {
            String dhHub = helper.getHubForWHnServiceType(request.getWarehouseId(), ServiceType.DELIVERY);
            String rtHub = helper.getHubForWHnServiceType(request.getWarehouseId(),ServiceType.PICKUP);
            String regionCode = helper.getRegion(request.getPincode());
            helper.deleteServiceabilityPreferences(rtHub,regionCode);
            helper.deleteServiceabilityPreferences(dhHub,regionCode);
            insertshipmentSortationConfig( request);
            int preference = 0;
            request.getCouriers().forEach(courier -> {
                try {
                    insertServiceabilityConfig( courier.getItemAttribute(), request.getPincode(), request.getWarehouseId(),courier.getCourierCode(),
                            courier.getCodLimit(),helper.getServiceabiityConfigCombinations(courier.getCourierCode(), courier.getServiceType(),
                                    courier.getShippingMethod(), courier.getPaymentMode()));
                    insertServiceabilityPreferences(request.getWarehouseId(), request.getPincode(), courier.getCourierCode(),
                            helper.getServiceabiityPreferenceCombinations(courier.getCourierCode(),courier.getServiceType(),courier.getShippingMethod(),
                                    courier.getPaymentMode(),courier.getItemAttribute()),preference);
                    insertHubCourierCapacityConfig(request.getWarehouseId(),request.getPincode(),courier.getCourierCode(),
                            helper.getServiceTypeForCapacity(courier.getShippingMethod(), courier.toString()), courier.getCapacity());
                    insertHubTat(request.getWarehouseId(),request.getPincode(),courier.getCourierCode(), courier.getShippingMethod());
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

    public void insertshipmentSortationConfig( ServiceabilityRequestWithAttributesEntry request) throws SQLException {
        request.getCouriers().forEach(courier->{
                    try {
                        String sortationId = helper.getShipmentSortationId(request.getPincode(),courier.getCourierCode());
                        log.info("Sortation id:"+sortationId);
                        log.info("Deleting serviceability_config if any exits for the sortation id");
                        helper.deleteServiceabilityConfig(sortationId,helper.getHubForWHnServiceType(request.getWarehouseId(),ServiceType.DELIVERY));
                        helper.deleteServiceabilityConfig(sortationId,helper.getHubForWHnServiceType(request.getWarehouseId(),ServiceType.PICKUP));
                    }catch (Exception e){
                        log.info("Insert data into shipment_sortation_config");
                        Map<String, String> data = null;
                        try {
                            data = helper.getPincodeData( request.getPincode());
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }
                        String values = "("+ request.getPincode() + ", '" + courier.getCourierCode() + "', '" + data.get("cityCode") + "', '" + data.get("cityName") + "', '" + data.get("areaCode") + "', " +
                                "'" + data.get("areaName") + "', 'erpMessageQueue')";
                        String insertq = "INSERT INTO `shipment_sortation_config` (`pincode`, `courier_code`, `city_code`, `city_name`, `area_code`, `area_name`, `created_by`) VALUES " + values;
                        DBUtilities.exUpdateQuery(insertq, "myntra_lms");
                    }
                }
        );
    }

    public void insertServiceabilityConfig( ItemAttributeEntry itemAttribute, String pincode, String warehouse, String courierCode, Integer codLimit, List<List<Object>> data) throws SQLException {
        String dhHub = helper.getHubForWHnServiceType(warehouse,ServiceType.DELIVERY);
        String rtHub = helper.getHubForWHnServiceType(warehouse,ServiceType.PICKUP);
        String sortationId = helper.getShipmentSortationId(pincode,courierCode);
        StringBuilder values = new StringBuilder();
        Integer codLimitA = codLimit>0?codLimit:20000;
        data.stream().
                filter(x -> !(x.get(0).equals(ServiceType.OPEN_BOX_PICKUP) || x.get(0).equals(ServiceType.CLOSED_BOX_PICKUP))).
                forEach(x -> {
                    values.append(",(" + sortationId + ", '" + dhHub + "', '" + x.get(0) + "', '" + x.get(1) + "', '" + x.get(2) +
                            "', "+itemAttribute.isFragile()+", "+itemAttribute.isHazmat()+", "+itemAttribute.isLarge()
                            +", "+itemAttribute.isJewellery()+", 1, "+codLimitA+", "+(codLimitA+10000)+", 'util')");
                });
        data.stream().
                filter(x -> (x.get(0).equals(ServiceType.OPEN_BOX_PICKUP) || x.get(0).equals(ServiceType.CLOSED_BOX_PICKUP))).
                filter(x-> x.get(2).equals("on")).
                forEach(x -> {
                    values.append(",(" + sortationId + ", '" + rtHub + "', '" + x.get(0) + "', '" + x.get(1) + "', '" + x.get(2) +
                            "', "+itemAttribute.isFragile()+", "+itemAttribute.isHazmat()+", "+itemAttribute.isLarge()
                            +", "+itemAttribute.isJewellery()+", 1, "+codLimitA+", "+(codLimitA+10000)+", 'util')");
                });
        String insertq = "INSERT INTO `serviceability_config` (`shipment_sortation_config_id`, `hub_code`, `service_type`, `shipping_method`, `payment_mode`, `is_fragile_supported`, " +
                "`is_hazmat_supported`, `is_large_supported`, `is_jewellery_supported`, `is_normal_supported`, `cod_limit`, `item_value_limit`, `created_by`) VALUES "+values.toString().replaceFirst(",","");
        log.info("Insert data into serviceability_config");
        DBUtilities.exUpdateQuery(insertq, "myntra_lms");
    }

    public void insertServiceabilityPreferences( String warehouse, String pincode, String courierCode, List<List<Object>> data, int preference) throws SQLException {
        String dhHub = helper.getHubForWHnServiceType(warehouse, ServiceType.DELIVERY);
        String rtHub = helper.getHubForWHnServiceType(warehouse,ServiceType.PICKUP);
        String regionCode = helper.getRegion(pincode);
        StringBuilder values = new StringBuilder();
        data.stream().
                filter(x -> !(x.get(0).equals(ServiceType.OPEN_BOX_PICKUP) || x.get(0).equals(ServiceType.CLOSED_BOX_PICKUP))).
                forEach(x -> {
                    values.append(",('" + dhHub + "', '" + regionCode + "', '" + courierCode + "','" + x.get(0) + "', '" + x.get(1) + "', '" + x.get(2) + "', '" + x.get(3) + "', " + preference + ", 'util')");
                });
        data.stream().
                filter(x -> (x.get(0).equals(ServiceType.OPEN_BOX_PICKUP) || x.get(0).equals(ServiceType.CLOSED_BOX_PICKUP))).
                forEach(x -> {
                    values.append(",('" + rtHub + "', '" + regionCode + "', '" + courierCode + "','" + x.get(0) + "', '" + x.get(1) + "', '" + x.get(2) + "', '" + x.get(3) + "', " + preference + ", 'util')");
                });
        String insertq = "INSERT INTO `hub_courier_preference` (`hub_code`, `region_code`, `courier_code`,`service_type`, `shipping_method`, `payment_mode`, `item_attribute`, `preference`, `created_by`) VALUES "+values.toString().replaceFirst(",","");
        log.info("Insert data into hub_courier_preference");
        DBUtilities.exUpdateQuery(insertq, "myntra_lms");
    }

    public void insertHubCourierCapacityConfig( String warehouseId, String pincode, String courierCode, List<String> data, Integer capacity) throws SQLException {
        String dhHub = helper.getHubForWHnServiceType(warehouseId,ServiceType.DELIVERY);
        String rtHub = helper.getHubForWHnServiceType(warehouseId,ServiceType.PICKUP);
        String regionCode = helper.getRegion( pincode);
        helper.deleteCourierCapacityConfig( dhHub,regionCode,courierCode);
        helper.deleteCourierCapacityConfig( rtHub,regionCode,courierCode);
        StringBuilder values = new StringBuilder();
        data.stream().
                filter(x->!(x.equals(ServiceType.CLOSED_BOX_PICKUP))||!(x.equals(ServiceType.OPEN_BOX_PICKUP))).
                forEach(x->{
                    values.append(",('"+dhHub+"', '"+regionCode+"', '"+courierCode+"', '"+x+"', "+capacity+", 'util') ");
                });
        data.stream().
                filter(x->(x.equals(ServiceType.CLOSED_BOX_PICKUP))||x.equals(ServiceType.OPEN_BOX_PICKUP)).
                forEach(x->{
                    values.append(",('"+rtHub+"', '"+regionCode+"', '"+courierCode+"', '"+x+"', "+capacity+", 'util') ");
                });
        String insertq = "INSERT INTO `hub_courier_capacity_config` (`hub_code`, `region_code`, `courier_code`, `courier_service_type`, `capacity`, `created_by`) VALUES "+values.toString().replaceFirst(",","");
        log.info("Insert data into hub_courier_capacity_config");
        DBUtilities.exUpdateQuery(insertq, "myntra_lms");
    }

    public void insertHubTat( String warehouseId, String pincode, String courierCode, ShippingMethodEntry shippingMethod) throws SQLException {
        String dhHub = helper.getHubForWHnServiceType(warehouseId,ServiceType.DELIVERY);
        helper.deleteTat(dhHub,pincode,courierCode);
        StringBuilder values = new StringBuilder();
        if (shippingMethod.getNormal()>0)values.append(", ('"+dhHub+"', "+pincode+", '"+courierCode+"', '"+ ShippingMethod.NORMAL+"', "+shippingMethod.getNormal()+", '21:45:00', 'util')");
        if (shippingMethod.getExpress()>0) values.append(", ('"+dhHub+"', "+pincode+", '"+courierCode+"', '"+ShippingMethod.EXPRESS+"', "+shippingMethod.getExpress()+", '21:45:00', 'util')");
        if (shippingMethod.getSdd()>0)values.append(", ('"+dhHub+"', "+pincode+", '"+courierCode+"', '"+ShippingMethod.SDD+"', "+shippingMethod.getSdd()+", '21:45:00', 'util')");
        if (shippingMethod.getValueShipping()>0) values.append(", ('"+dhHub+"', "+pincode+", '"+courierCode+"', '"+ShippingMethod.VALUE_SHIPPING+"', "+shippingMethod.getValueShipping()+", '21:45:00', 'util')");
        String insertq = "INSERT INTO `hub_tat` (`hub_code`, `pincode`, `courier_code`, `shipping_method`, `tat_time_in_hours`, `shipping_cutoff`, `created_by`) VALUES"+values.toString().replaceFirst(",","");
        log.info("Insert data into hub_tat");
        DBUtilities.exUpdateQuery(insertq, "myntra_lms");
    }
}
