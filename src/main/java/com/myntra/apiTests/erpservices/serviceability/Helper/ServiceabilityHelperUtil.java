package com.myntra.apiTests.erpservices.serviceability.Helper;

import com.myntra.apiTests.erpservices.lms.lmsClient.*;
import com.myntra.lordoftherings.DataOrc;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.boromir.DBUtilities;
import com.myntra.test.commons.testbase.BaseTest;
import org.apache.log4j.Logger;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Shubham Gupta on 7/21/17.
 */
public class ServiceabilityHelperUtil extends BaseTest {
    static Initialize init = new Initialize("/Data/configuration");
    static Logger log = Logger.getLogger(ServiceabilityHelperUtil.class);

    public void deleteShipmentSortationConfig( ServiceabilityRequest request) throws SQLException {
        StringBuilder values = new StringBuilder();
        Arrays.asList(request.getCouriers()).forEach(courier->values.append(",'"+courier+"'"));
        String shipment_sortation_config = "delete from shipment_sortation_config where pincode = "+request.getPincode()+" and courier_code not in("+values.toString().replaceFirst(",","")+")";
        DBUtilities.exUpdateQuery(shipment_sortation_config, "myntra_lms");
    }

    public void deleteServiceabilityConfig( String sortationId, String hubCode) throws SQLException {
        String serviceability_config = "delete from serviceability_config where shipment_sortation_config_id = "+sortationId+" and hub_code = '"+hubCode+"'";
        DBUtilities.exUpdateQuery(serviceability_config, "myntra_lms");
    }

    public void deleteHubCourierCapacity( String hubCode, String regionCode, String courierCode) throws SQLException {
        String hub_courier_capacity = "delete from hub_courier_capacity where hub_code = '"+hubCode+"' and region_code = '"+regionCode+"' and courier_code = '"+courierCode+"'";
        DBUtilities.exUpdateQuery(hub_courier_capacity, "myntra_lms");
    }

    public void deleteServiceabilityPreferences( String hubCode, String region) throws SQLException {
        String hub_courier_preference = "delete from hub_courier_preference where region_code = '"+region+"' and hub_code = '"+hubCode+"'";
        DBUtilities.exUpdateQuery(hub_courier_preference, "myntra_lms");
    }

    public void deleteCourierCapacityConfig( String hubCode, String region, String courierCode) throws SQLException {
        deleteHubCourierCapacity(hubCode,region,courierCode);
        String delServiceabilityConfig = "delete from hub_courier_capacity_config where region_code = '"+region+"' and hub_code = '"+hubCode+"' and courier_code = '"+courierCode+"'";
        DBUtilities.exUpdateQuery(delServiceabilityConfig, "myntra_lms");
    }

    public void deleteTat( String hubCode, String pincode, String courierCode) throws SQLException {
        String hub_tat = "delete from hub_tat where pincode = '"+pincode+"' and hub_code = '"+hubCode+"' and courier_code = '"+courierCode+"'";
        DBUtilities.exUpdateQuery(hub_tat, "myntra_lms");
    }

    public String getRegion( String pincode) throws SQLException{
        log.info("getting region from database");
        String query = "select region_code from pincode where id= "+pincode ;
        @SuppressWarnings("unchecked")
		List<Map<String,Object>> rs =  DBUtilities.exSelectQuery(query,"myntra_lms");
        log.info("Region: "+rs.get(0).get("region_code"));
        return ""+rs.get(0).get("region_code");
    }

    public String getShipmentSortationId( String pincode, String courierCode)throws SQLException{
        log.info("getting shipment_sortation_config from database");
        String query = "select id from shipment_sortation_config where pincode = "+pincode+" and courier_code = '"+courierCode+"'";
        @SuppressWarnings("unchecked")
        List<Map<String,Object>> rs = DBUtilities.exSelectQuery(query,"myntra_lms");
        log.info("Sortation_id: "+rs.get(0).get("id"));
        return ""+rs.get(0).get("id");
    }

    public String  getHubForWHnServiceType( String warehouseId, String serviceType) throws SQLException{
        log.info("getting dispatch_hub_warehouse_config/return_hub_warehouse_config for warehouse_id: "+warehouseId+" from database");
        String query = "";
        if (serviceType.equals(ServiceType.OPEN_BOX_PICKUP)||serviceType.equals(ServiceType.CLOSED_BOX_PICKUP)||serviceType.equals("PICKUP"))
            query = "select hub_code from return_hub_warehouse_config where warehouse_id = " + warehouseId;
        else query = "select hub_code from dispatch_hub_warehouse_config where warehouse_id = "+warehouseId;
        @SuppressWarnings("unchecked")
        List<Map<String,Object>> rs = DBUtilities.exSelectQuery(query,"myntra_lms");
        log.info("HUB: "+rs.get(0).get("hub_code"));
        return rs.get(0).get("hub_code").toString();
    }

    public Map<String,String> getPincodeData( String pincode) throws SQLException{
        log.info("getting pincode data from database");
        Map<String,String> pincodeData = new HashMap<>();
        String query = "select * from pincode where id= "+pincode ;
        @SuppressWarnings("unchecked")
        List<Map<String,Object>> rs = DBUtilities.exSelectQuery(query,"myntra_lms");
        pincodeData.put("pincode",rs.get(0).get("id").toString());
        pincodeData.put("cityCode",rs.get(0).get("city_code").toString());
        pincodeData.put("cityName",rs.get(0).get("city_name").toString());
        pincodeData.put("areaCode",rs.get(0).get("area_code").toString());
        pincodeData.put("areaName",rs.get(0).get("area_name").toString());
        pincodeData.put("regionCode",rs.get(0).get("region_code").toString());
        pincodeData.put("stateCode",rs.get(0).get("state_code").toString());
        return pincodeData;
    }

    public List<List<Object>> getServiceabiityConfigCombinations(String courierCode){
        List<List<Object>> combinations = new ArrayList<>();
        List<Object> list;
        Object[][] allComb = getServiceabiityConfigAllCombinations();
        for(int i = 0; i < allComb.length; i++) {
            list = Arrays.asList(allComb[i]);
            combinations.add(list);
        }
        List<List<Object>> collect = combinations.stream()
                .filter(x -> !(x.get(0).equals(ServiceType.TRYNBUY) && !courierCode.equals("ML")))
                .filter(x -> !(x.get(0).equals(ServiceType.OPEN_BOX_PICKUP) && x.get(1).equals(ShippingMethod.EXPRESS)))
                .filter(x -> !(x.get(0).equals(ServiceType.CLOSED_BOX_PICKUP) && x.get(1).equals(ShippingMethod.EXPRESS)))
                .filter(x -> !(x.get(0).equals(ServiceType.OPEN_BOX_PICKUP) && x.get(1).equals(ShippingMethod.SDD)))
                .filter(x -> !(x.get(0).equals(ServiceType.CLOSED_BOX_PICKUP) && x.get(1).equals(ShippingMethod.SDD)))
                .filter(x -> !(x.get(0).equals(ServiceType.EXCHANGE) && x.get(1).equals(ShippingMethod.EXPRESS)))
                .filter(x -> !(x.get(0).equals(ServiceType.EXCHANGE) && x.get(1).equals(ShippingMethod.SDD)))
                .filter(x -> !(x.get(0).equals(ServiceType.CLOSED_BOX_PICKUP) && (courierCode.equals("ML") || courierCode.equals("EK"))))
                .filter(x -> !(x.get(0).equals(ServiceType.OPEN_BOX_PICKUP) && !(courierCode.equals("ML") || courierCode.equals("EK"))))
                .collect(Collectors.toList());
        return collect;
    }

    public List<List<Object>> getServiceabiityConfigCombinations(String courierCode, ServiceTypeEntry serviceType, ShippingMethodEntry shippingMethod, PaymentModeEntry paymentMode){
        List<List<Object>> combinations = new ArrayList<>();
        List<Object> list;
        Object[][] allComb = getServiceabiityConfigAllCombinations(serviceType, shippingMethod, paymentMode);
        for(int i = 0; i < allComb.length; i++) {
            list = Arrays.asList(allComb[i]);
            combinations.add(list);
        }
        List<List<Object>> collect = combinations.stream()
                .filter(x -> !(x.get(0).equals(ServiceType.TRYNBUY) && !courierCode.equals("ML")))
                .filter(x -> !(x.get(0).equals(ServiceType.OPEN_BOX_PICKUP) && x.get(1).equals(ShippingMethod.EXPRESS)))
                .filter(x -> !(x.get(0).equals(ServiceType.CLOSED_BOX_PICKUP) && x.get(1).equals(ShippingMethod.EXPRESS)))
                .filter(x -> !(x.get(0).equals(ServiceType.OPEN_BOX_PICKUP) && x.get(1).equals(ShippingMethod.SDD)))
                .filter(x -> !(x.get(0).equals(ServiceType.CLOSED_BOX_PICKUP) && x.get(1).equals(ShippingMethod.SDD)))
                .filter(x -> !(x.get(0).equals(ServiceType.EXCHANGE) && x.get(1).equals(ShippingMethod.EXPRESS)))
                .filter(x -> !(x.get(0).equals(ServiceType.EXCHANGE) && x.get(1).equals(ShippingMethod.SDD)))
                .filter(x -> !(x.get(0).equals(ServiceType.CLOSED_BOX_PICKUP) && (courierCode.equals("ML") || courierCode.equals("EK"))))
                .filter(x -> !(x.get(0).equals(ServiceType.OPEN_BOX_PICKUP) && !(courierCode.equals("ML") || courierCode.equals("EK"))))
                .collect(Collectors.toList());
        return collect;
    }

    public List<List<Object>> getServiceabiityPreferenceCombinations(String courierCode){
        List<List<Object>> combinations = new ArrayList<>();
        List<Object> list;
        Object[][] allComb = getServiceabiityPreferenceAllCombinations();
        for(int i = 0; i < allComb.length; i++) {
            list = Arrays.asList(allComb[i]);
            combinations.add(list);
        }
        List<List<Object>> collect = combinations.stream()
                .filter(x -> !(x.get(0).equals(ServiceType.TRYNBUY) && !courierCode.equals("ML")))
                .filter(x -> !(x.get(0).equals(ServiceType.OPEN_BOX_PICKUP) && x.get(1).equals(ShippingMethod.EXPRESS)))
                .filter(x -> !(x.get(0).equals(ServiceType.CLOSED_BOX_PICKUP) && x.get(1).equals(ShippingMethod.EXPRESS)))
                .filter(x -> !(x.get(0).equals(ServiceType.OPEN_BOX_PICKUP) && x.get(1).equals(ShippingMethod.SDD)))
                .filter(x -> !(x.get(0).equals(ServiceType.CLOSED_BOX_PICKUP) && x.get(1).equals(ShippingMethod.SDD)))
                .filter(x -> !(x.get(0).equals(ServiceType.EXCHANGE) && x.get(1).equals(ShippingMethod.EXPRESS)))
                .filter(x -> !(x.get(0).equals(ServiceType.EXCHANGE) && x.get(1).equals(ShippingMethod.SDD)))
                .filter(x -> !(x.get(0).equals(ServiceType.CLOSED_BOX_PICKUP) && (courierCode.equals("ML") || courierCode.equals("EK"))))
                .filter(x -> !(x.get(0).equals(ServiceType.OPEN_BOX_PICKUP) && !(courierCode.equals("ML") || courierCode.equals("EK"))))
                .collect(Collectors.toList());
        return collect;
    }

    public List<List<Object>> getServiceabiityPreferenceCombinations(String courierCode, ServiceTypeEntry serviceType, ShippingMethodEntry shippingMethod,
                                                                     PaymentModeEntry paymentMode, ItemAttributeEntry itemAttribute){
        List<List<Object>> combinations = new ArrayList<>();
        List<Object> list;
        Object[][] allComb = getServiceabiityPreferenceAllCombinations(serviceType, shippingMethod, paymentMode, itemAttribute);
        for(int i = 0; i < allComb.length; i++) {
            list = Arrays.asList(allComb[i]);
            combinations.add(list);
        }
        List<List<Object>> collect = combinations.stream()
                .filter(x -> !(x.get(0).equals(ServiceType.TRYNBUY) && !courierCode.equals("ML")))
                .filter(x -> !(x.get(0).equals(ServiceType.OPEN_BOX_PICKUP) && x.get(1).equals(ShippingMethod.EXPRESS)))
                .filter(x -> !(x.get(0).equals(ServiceType.CLOSED_BOX_PICKUP) && x.get(1).equals(ShippingMethod.EXPRESS)))
                .filter(x -> !(x.get(0).equals(ServiceType.OPEN_BOX_PICKUP) && x.get(1).equals(ShippingMethod.SDD)))
                .filter(x -> !(x.get(0).equals(ServiceType.CLOSED_BOX_PICKUP) && x.get(1).equals(ShippingMethod.SDD)))
                .filter(x -> !(x.get(0).equals(ServiceType.EXCHANGE) && x.get(1).equals(ShippingMethod.EXPRESS)))
                .filter(x -> !(x.get(0).equals(ServiceType.EXCHANGE) && x.get(1).equals(ShippingMethod.SDD)))
                .filter(x -> !(x.get(0).equals(ServiceType.CLOSED_BOX_PICKUP) && (courierCode.equals("ML") || courierCode.equals("EK"))))
                .filter(x -> !(x.get(0).equals(ServiceType.OPEN_BOX_PICKUP) && !(courierCode.equals("ML") || courierCode.equals("EK"))))
                .collect(Collectors.toList());
        return collect;
    }


    public Object[][] getServiceabiityConfigAllCombinations(){
        ArrayList<String> serviceTypesPreference = new ArrayList<>();
        ArrayList<String> shippingMethodsPreference = new ArrayList<>();
        ArrayList<String> paymenModePreference = new ArrayList<>();

        serviceTypesPreference.add(ServiceType.DELIVERY);
        serviceTypesPreference.add(ServiceType.EXCHANGE);
        serviceTypesPreference.add(ServiceType.TRYNBUY);
        serviceTypesPreference.add(ServiceType.OPEN_BOX_PICKUP);
        serviceTypesPreference.add(ServiceType.CLOSED_BOX_PICKUP);

        shippingMethodsPreference.add(ShippingMethod.NORMAL);
        shippingMethodsPreference.add(ShippingMethod.EXPRESS);
        shippingMethodsPreference.add(ShippingMethod.SDD);

        paymenModePreference.add(PaymentMethod.cod);
        paymenModePreference.add(PaymentMethod.on);

        DataOrc combinations = new DataOrc(serviceTypesPreference, shippingMethodsPreference, paymenModePreference);
        return combinations.Explode();
    }

    public Object[][] getServiceabiityConfigAllCombinations(ServiceTypeEntry serviceType, ShippingMethodEntry shippingMethod, PaymentModeEntry paymentMode){
        ArrayList<String> serviceTypesPreference = new ArrayList<>();
        ArrayList<String> shippingMethodsPreference = new ArrayList<>();
        ArrayList<String> paymenModePreference = new ArrayList<>();

        if (serviceType.isDelivery())serviceTypesPreference.add(ServiceType.DELIVERY);
        if (serviceType.isExchange())serviceTypesPreference.add(ServiceType.EXCHANGE);
        if (serviceType.isTrynbuy())serviceTypesPreference.add(ServiceType.TRYNBUY);
        if (serviceType.isOpenBoxPickup())serviceTypesPreference.add(ServiceType.OPEN_BOX_PICKUP);
        if (serviceType.isCloseBoxPickup())serviceTypesPreference.add(ServiceType.CLOSED_BOX_PICKUP);

        if (shippingMethod.getNormal()>0)shippingMethodsPreference.add(ShippingMethod.NORMAL);
        if (shippingMethod.getExpress()>0)shippingMethodsPreference.add(ShippingMethod.EXPRESS);
        if (shippingMethod.getSdd()>0)shippingMethodsPreference.add(ShippingMethod.SDD);
        if (shippingMethod.getValueShipping()>0)shippingMethodsPreference.add(ShippingMethod.VALUE_SHIPPING);

        if (paymentMode.isCod())paymenModePreference.add(PaymentMethod.cod);
        if (paymentMode.isOn())paymenModePreference.add(PaymentMethod.on);

        DataOrc combinations = new DataOrc(serviceTypesPreference, shippingMethodsPreference, paymenModePreference);
        return combinations.Explode();
    }

    public Object[][] getServiceabiityPreferenceAllCombinations(){
        ArrayList<String> serviceTypesPreference = new ArrayList<>();
        ArrayList<String> shippingMethodsPreference = new ArrayList<>();
        ArrayList<String> paymenModePreference = new ArrayList<>();
        ArrayList<String> itemAttributes = new ArrayList<>();

        serviceTypesPreference.add(ServiceType.DELIVERY);
        serviceTypesPreference.add(ServiceType.EXCHANGE);
        serviceTypesPreference.add(ServiceType.TRYNBUY);
        serviceTypesPreference.add(ServiceType.OPEN_BOX_PICKUP);
        serviceTypesPreference.add(ServiceType.CLOSED_BOX_PICKUP);

        shippingMethodsPreference.add(ShippingMethod.NORMAL);
        shippingMethodsPreference.add(ShippingMethod.EXPRESS);
        shippingMethodsPreference.add(ShippingMethod.SDD);

        paymenModePreference.add(PaymentMethod.cod);
        paymenModePreference.add(PaymentMethod.on);

        itemAttributes.add(ItemAttribute.JEWELLERY);
        itemAttributes.add(ItemAttribute.FRAGILE);
        itemAttributes.add(ItemAttribute.HAZMAT);
        itemAttributes.add(ShippingMethod.NORMAL);
        itemAttributes.add(ItemAttribute.LARGE);

        DataOrc combinations = new DataOrc(serviceTypesPreference, shippingMethodsPreference, paymenModePreference,itemAttributes);
        return combinations.Explode();
    }

    public Object[][] getServiceabiityPreferenceAllCombinations(ServiceTypeEntry serviceType, ShippingMethodEntry shippingMethod,
                                                                PaymentModeEntry paymentMode, ItemAttributeEntry itemAttribute){
        ArrayList<String> serviceTypesPreference = new ArrayList<>();
        ArrayList<String> shippingMethodsPreference = new ArrayList<>();
        ArrayList<String> paymenModePreference = new ArrayList<>();
        ArrayList<String> itemAttributes = new ArrayList<>();

        if (serviceType.isDelivery())serviceTypesPreference.add(ServiceType.DELIVERY);
        if (serviceType.isExchange())serviceTypesPreference.add(ServiceType.EXCHANGE);
        if (serviceType.isTrynbuy())serviceTypesPreference.add(ServiceType.TRYNBUY);
        if (serviceType.isOpenBoxPickup())serviceTypesPreference.add(ServiceType.OPEN_BOX_PICKUP);
        if (serviceType.isCloseBoxPickup())serviceTypesPreference.add(ServiceType.CLOSED_BOX_PICKUP);

        if (shippingMethod.getNormal()>0)shippingMethodsPreference.add(ShippingMethod.NORMAL);
        if (shippingMethod.getExpress()>0)shippingMethodsPreference.add(ShippingMethod.EXPRESS);
        if (shippingMethod.getSdd()>0)shippingMethodsPreference.add(ShippingMethod.SDD);
        if (shippingMethod.getValueShipping()>0)shippingMethodsPreference.add(ShippingMethod.VALUE_SHIPPING);

        if (paymentMode.isCod())paymenModePreference.add(PaymentMethod.cod);
        if (paymentMode.isOn())paymenModePreference.add(PaymentMethod.on);

        if (itemAttribute.isJewellery())itemAttributes.add(ItemAttribute.JEWELLERY);
        if (itemAttribute.isFragile())itemAttributes.add(ItemAttribute.FRAGILE);
        if (itemAttribute.isHazmat())itemAttributes.add(ItemAttribute.HAZMAT);
        if (itemAttribute.isLarge())itemAttributes.add(ItemAttribute.LARGE);
        itemAttributes.add(ShippingMethod.NORMAL);

        DataOrc combinations = new DataOrc(serviceTypesPreference, shippingMethodsPreference, paymenModePreference,itemAttributes);
        return combinations.Explode();
    }

    public List<String> getServiceTypeForCapacity(String courier){
        List<String> serviceType = new ArrayList<>();
        serviceType.add(ShippingMethod.NORMAL);
        serviceType.add(ShippingMethod.EXPRESS);
        serviceType.add(ShippingMethod.SDD);
        if (courier.equals("ML")||courier.equals("EK")) serviceType.add(ServiceType.OPEN_BOX_PICKUP);
        else serviceType.add(ServiceType.CLOSED_BOX_PICKUP);
        return serviceType;
    }

    public List<String> getServiceTypeForCapacity(ShippingMethodEntry shippingMethod, String courier){
        List<String> res = new ArrayList<>();
        if (shippingMethod.getNormal()>0)res.add(ShippingMethod.NORMAL);
        if (shippingMethod.getExpress()>0)res.add(ShippingMethod.EXPRESS);
        if (shippingMethod.getSdd()>0)res.add(ShippingMethod.SDD);
        if (courier.equals("ML")||courier.equals("EK")) res.add(ServiceType.OPEN_BOX_PICKUP);
        else res.add(ServiceType.CLOSED_BOX_PICKUP);
        return res;
    }

    public List<String> getServiceTypeForTat(){
        List<String> serviceType = new ArrayList<>();
        serviceType.add(ShippingMethod.NORMAL);
        serviceType.add(ShippingMethod.EXPRESS);
        serviceType.add(ShippingMethod.SDD);
        return serviceType;
    }
}
