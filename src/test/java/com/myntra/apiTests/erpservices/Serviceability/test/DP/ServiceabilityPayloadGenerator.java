package com.myntra.apiTests.erpservices.Serviceability.test.DP;

import com.myntra.lms.client.response.*;
import com.myntra.lms.client.status.PaymentMode;
import com.myntra.lms.client.status.ShippingMethod;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.serviceability.client.request.ItemServiceabilityEntry;
import com.myntra.serviceability.client.request.OrderServiceabilityRequest;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ServiceabilityPayloadGenerator {
	
	public List<String> getPayloadList(long pincode) throws JAXBException {
		
		List<String> payloadList = new ArrayList<>();
		
		payloadList.add(APIUtilities.convertXMLObjectToString(payload1(pincode)));
		payloadList.add(APIUtilities.convertXMLObjectToString(payload1_MT(pincode)));
		
		
	/*	payloadList.add(APIUtilities.convertXMLObjectToString(payload2(pincode)));
		payloadList.add(APIUtilities.convertXMLObjectToString(payload3(pincode)));
		payloadList.add(APIUtilities.convertXMLObjectToString(payload4(pincode)));
		payloadList.add(APIUtilities.convertXMLObjectToString(payload5(pincode)));
		*/
		return payloadList;
	}
	
	public PortalCourierServiceabilityRequest payload1(long pincode) {
		
		List<PortalItemInfo> portalItemInfoList = new ArrayList<>();
		Set<Long> warehousesSet = getWareHouseSet();
		List<String> skuIdList = getSkuIdList();
		
		for (String skuId : skuIdList) {
			
			PortalItemInfo portalItemInfo = buildItemRequest(skuId, warehousesSet, "false", "false", "false", "false");
			portalItemInfoList.add(portalItemInfo);
		}
		
		PortalCourierServiceabilityRequest payloadObject = buildRequest(pincode, "ALL", "ALL", "DELIVERY",
				portalItemInfoList, "true");
		return payloadObject;
	}
	
	
	public OrderServiceabilityRequest payload1_MT(long pincode) {
		
		List<ItemServiceabilityEntry> itemServiceabilityEntryList = new ArrayList<>();
		Set<Long> availableInWarehouses = getWareHouseSet();
		availableInWarehouses.add(27l);
		availableInWarehouses.add(20l);
		List<String> skuIdList = getSkuIdList();
		
		for (String skuId : skuIdList) {
			
			ItemServiceabilityEntry itemServiceabilityInfo = buildItemRequest_MT(skuId, availableInWarehouses, "false", "false", "false", "false");
			itemServiceabilityEntryList.add(itemServiceabilityInfo);
		}
		
		OrderServiceabilityRequest payloadObject = buildRequest_MT(pincode, "ALL", "ALL", "DELIVERY",
				itemServiceabilityEntryList, "true");
		return payloadObject;
	}
	
	public PortalCourierServiceabilityRequest payload2(long pincode) {
		
		List<PortalItemInfo> portalItemInfoList = new ArrayList<>();
		Set<Long> warehousesSet = getWareHouseSet();
		warehousesSet.add(27l);
		warehousesSet.add(20l);
		List<String> skuIdList = getSkuIdList();
		
		for (String skuId : skuIdList) {
			
			PortalItemInfo portalItemInfo = buildItemRequest(skuId, warehousesSet, "true", "false", "false", "false");
			portalItemInfoList.add(portalItemInfo);
		}
		
		PortalCourierServiceabilityRequest payloadObject = buildRequest(pincode, "ALL", "ALL", "ALL",
				portalItemInfoList, "true");
		return payloadObject;
	}
	
	public PortalCourierServiceabilityRequest payload3(long pincode) {
		
		List<PortalItemInfo> portalItemInfoList = new ArrayList<>();
		Set<Long> warehousesSet = getWareHouseSet();
		List<String> skuIdList = getSkuIdList();
		
		for (String skuId : skuIdList) {
			
			PortalItemInfo portalItemInfo = buildItemRequest(skuId, warehousesSet, "false", "true", "false", "false");
			portalItemInfoList.add(portalItemInfo);
		}
		
		PortalCourierServiceabilityRequest payloadObject = buildRequest(pincode, "ALL", "ALL", "ALL",
				portalItemInfoList, "true");
		return payloadObject;
	}
	
	public PortalCourierServiceabilityRequest payload4(long pincode) {
		
		List<PortalItemInfo> portalItemInfoList = new ArrayList<>();
		Set<Long> warehousesSet = getWareHouseSet();
		List<String> skuIdList = getSkuIdList();
		
		for (String skuId : skuIdList) {
			
			PortalItemInfo portalItemInfo = buildItemRequest(skuId, warehousesSet, "false", "false", "true", "false");
			portalItemInfoList.add(portalItemInfo);
		}
		
		PortalCourierServiceabilityRequest payloadObject = buildRequest(pincode, "ALL", "ALL", "ALL",
				portalItemInfoList, "true");
		return payloadObject;
	}
	
	public PortalCourierServiceabilityRequest payload5(long pincode) {
		
		List<PortalItemInfo> portalItemInfoList = new ArrayList<>();
		Set<Long> warehousesSet = getWareHouseSet();
		List<String> skuIdList = getSkuIdList();
		
		for (String skuId : skuIdList) {
			
			PortalItemInfo portalItemInfo = buildItemRequest(skuId, warehousesSet, "true", "true", "true", "false");
			portalItemInfoList.add(portalItemInfo);
		}
		
		PortalCourierServiceabilityRequest payloadObject = buildRequest(pincode, "ALL", "ALL", "ALL",
				portalItemInfoList, "true");
		return payloadObject;
	}
	
	public PortalCourierServiceabilityRequest buildRequest(long pincode, String paymentMode, String shippingMethod,
	                                                       String serviceType, List<PortalItemInfo> portalItemInfoList, String capaityCheck) {
		
		PortalCourierServiceabilityRequest courierServiceabilityRequest = new PortalCourierServiceabilityRequest();
		
		List<PortalOrder> orderList = new ArrayList<>();
		
		PortalOrder portalOrder = new PortalOrder();
		
		portalOrder.setCapacityCheckRequired(Boolean.valueOf(capaityCheck));
		portalOrder.setExpressCapacityCheckRequired(true);
		portalOrder.setFetchCouriers(true);
		portalOrder.setShippingCutoffCheckRequired(true);
		portalOrder.setPincode(pincode);
		portalOrder.setPaymentMode(PaymentMode.valueOf(paymentMode));
		portalOrder.setServiceType(ServiceType.valueOf(serviceType));
		portalOrder.setShippingMethod(ShippingMethod.valueOf(shippingMethod));
		portalOrder.setItem(portalItemInfoList);
		
		orderList.add(portalOrder);
		courierServiceabilityRequest.setOrder(orderList);
		
		return courierServiceabilityRequest;
	}
	
	public OrderServiceabilityRequest buildRequest_MT(long pincode, String paymentMode, String shippingMethod,
	                                                       String serviceType, List<ItemServiceabilityEntry> itemServiceabilityEntryList, String capaityCheck) {
		
		OrderServiceabilityRequest orderServiceabilityRequest = new OrderServiceabilityRequest();
		
		/*private Long pincode;
		private String clientId;
		private com.myntra.serviceability.client.util.ServiceType serviceType;
		private com.myntra.serviceability.client.util.ShippingMethod shippingMethod;
		private com.myntra.serviceability.client.util.PaymentMode paymentMode;
		private Boolean consolidationEnabled;
		private List<ItemServiceabilityEntry> items;
		private String shipmentId;*/
		
		
	// TODO : set the client id
		orderServiceabilityRequest.setPincode(pincode);
		//Using the complete path , since there is Payment mode in com.myntra.serviceability.client.util and com.myntra.lms.client.status
		orderServiceabilityRequest.setPaymentMode(com.myntra.serviceability.client.util.PaymentMode.valueOf(paymentMode));
		orderServiceabilityRequest.setServiceType(com.myntra.serviceability.client.util.ServiceType.valueOf(serviceType));
		orderServiceabilityRequest.setShippingMethod(com.myntra.serviceability.client.util.ShippingMethod.valueOf(shippingMethod));
		orderServiceabilityRequest.setItems(itemServiceabilityEntryList);
		
		return orderServiceabilityRequest;
	}
	public PortalItemInfo buildItemRequest(String skuId, Set<Long> warehousesSet, String isLarge, String isJewellery,
	                                       String isFragile, String isHazmat) {
		
		PortalItemInfo portalItemInfo = new PortalItemInfo();
		List<ServiceTypeParam> itemTypeList = new ArrayList<>();
		
		itemTypeList.add(getServiceTypeParam("isLarge", isLarge));
		itemTypeList.add(getServiceTypeParam("isJewellery", isJewellery));
		itemTypeList.add(getServiceTypeParam("isFragile", isFragile));
		itemTypeList.add(getServiceTypeParam("isHazmat", isHazmat));
		itemTypeList.add(getServiceTypeParam("isTryAndBuyEnabled", "true"));
		
		portalItemInfo.setItemType(itemTypeList);
		portalItemInfo.setSkuId(skuId);
		portalItemInfo.setLeadTime(3);
		
		portalItemInfo.setWarehouseId(warehousesSet);
		
		return portalItemInfo;
	}
	public ItemServiceabilityEntry buildItemRequest_MT(String skuId, Set<Long> warehousesSet, String isLarge, String isJewellery,
	                                       String isFragile, String isHazmat) {
		
		
		ItemServiceabilityEntry itemServiceabilityEntryInfo = new ItemServiceabilityEntry();
		
		HashSet<String> warehousesSetString = new HashSet<>(warehousesSet.size());
		warehousesSet.forEach(i -> warehousesSetString.add(i.toString()));
		
		itemServiceabilityEntryInfo.setAvailableInWarehouses(warehousesSetString);
		itemServiceabilityEntryInfo.setIsLarge(Boolean.valueOf(isLarge));
		itemServiceabilityEntryInfo.setIsJewellery(Boolean.valueOf(isJewellery));
		itemServiceabilityEntryInfo.setIsFragile(Boolean.valueOf(isFragile));
		itemServiceabilityEntryInfo.setIsHazmat(Boolean.valueOf(isHazmat));
		itemServiceabilityEntryInfo.setTryAndBuyEnabled(true);
		itemServiceabilityEntryInfo.setSkuId(skuId);
		itemServiceabilityEntryInfo.setProcurementTimeInDays(3);
		
		
		return itemServiceabilityEntryInfo;
	}
	
	public Set<Long> getWareHouseSet() {
		
		Set<Long> warehousesSet = new HashSet<>();
		warehousesSet.add(36L);
	//TODO : get more warehouse
		//	warehousesSet.add(28L);
		return warehousesSet;
	}
	
	public List<String> getSkuIdList() {
		
		List<String> skuIdList = new ArrayList<>();
		
		skuIdList.add("3831");
		//TODO : add more sku
	/*	skuIdList.add("3132");
		skuIdList.add("3913");*/
		// skuIdList.add("3831");
		// skuIdList.add("");
		
		return skuIdList;
	}
	
	public ServiceTypeParam getServiceTypeParam(String name, String value) {
		
		ServiceTypeParam serviceTypeParam = new ServiceTypeParam();
		//serviceTypeParam.setDataType(RuleMetaDataParameterDataType.BOOLEAN);
		serviceTypeParam.setName(name);
		serviceTypeParam.setValue(value);
		
		return serviceTypeParam;
	}
	
	public List<Integer> getLeadTimeList() {
		
		List<Integer> leadTimeList = new ArrayList<>();
		
		leadTimeList.add(0);
		leadTimeList.add(1);
		leadTimeList.add(2);
		leadTimeList.add(3);
		leadTimeList.add(4);
		leadTimeList.add(5);
		
		return leadTimeList;
	}
	
	public List<Long> getPincodeList(String fileName) {
		
		List<Long> pincodeList = new ArrayList<>();
		
		try {
			
			List<String> stringList = Files.readAllLines(Paths.get(fileName));
			pincodeList = stringList.parallelStream().map(Long::parseLong).collect(Collectors.toList());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return pincodeList;
	}
}
