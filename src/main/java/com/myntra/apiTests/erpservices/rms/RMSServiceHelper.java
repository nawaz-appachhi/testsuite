package com.myntra.apiTests.erpservices.rms;

import com.jayway.jsonpath.JsonPath;
import com.myntra.apiTests.SERVICE_TYPE;
import com.myntra.apiTests.common.APINAME;
import com.myntra.apiTests.common.Myntra;
import com.myntra.apiTests.common.ServiceType;
import com.myntra.apiTests.common.entries.FlipkartReturnEnteries;
import com.myntra.apiTests.end2end.End2EndHelper;
import com.myntra.apiTests.erpservices.Constants;
import com.myntra.apiTests.erpservices.lms.Helper.LMSUtils;
import com.myntra.apiTests.erpservices.lms.Helper.LmsServiceHelper;
import com.myntra.apiTests.erpservices.oms.OMSServiceHelper;
import com.myntra.apiTests.erpservices.silkroute.SilkRouteServiceHelper;
import com.myntra.commons.codes.StatusResponse;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.SlackMessenger;
import com.myntra.lordoftherings.boromir.DBUtilities;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.PayloadType;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.oms.client.entry.OrderEntry;
import com.myntra.oms.client.entry.OrderLineEntry;
import com.myntra.oms.client.entry.OrderReleaseEntry;
import com.myntra.returns.common.enums.CancellationType;
import com.myntra.returns.common.enums.PickupStatus;
import com.myntra.returns.common.enums.RefundMode;
import com.myntra.returns.common.enums.ReturnMode;
import com.myntra.returns.common.enums.ReturnType;
import com.myntra.returns.common.enums.code.ReturnActionCode;
import com.myntra.returns.common.enums.code.ReturnLineStatus;
import com.myntra.returns.common.enums.code.ReturnStatus;
import com.myntra.returns.entry.ReturnAddressDetailsEntry;
import com.myntra.returns.entry.ReturnEntry;
import com.myntra.returns.entry.ReturnLineEntry;
import com.myntra.returns.entry.ReturnLineUpdateRequestEntry;
import com.myntra.returns.entry.ReturnRefundDetailsEntry;
import com.myntra.returns.entry.ReturnTrackingDetailsEntry;
import com.myntra.returns.entry.ReturnUpdateRequestEntry;
import com.myntra.returns.entry.RtoEntry;
import com.myntra.returns.response.ReturnLineResponse;
import com.myntra.returns.response.ReturnResponse;
import com.myntra.silkroute.client.response.RtoResponse;
import com.myntra.test.commons.service.HTTPMethods;
import com.myntra.test.commons.service.HttpExecutorService;
import com.myntra.test.commons.service.Svc;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.testng.Assert;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.*;

public class RMSServiceHelper {
	
	private static Logger log = Logger.getLogger(OMSServiceHelper.class);
	static Initialize init = new Initialize("/Data/configuration");
	SilkRouteServiceHelper silkRouteServiceHelper = new SilkRouteServiceHelper();
	ReturnEntry returnEntry = new ReturnEntry();
	ReturnAddressDetailsEntry returnAddressDetailsEntry = new ReturnAddressDetailsEntry();
	ReturnLineEntry returnLineEntry = new ReturnLineEntry();
	List<ReturnLineEntry> returnlineEntries = new ArrayList<>();
	OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
	private OrderLineEntry orderLineEntry;
	private OrderEntry orderEntry;
	private OrderReleaseEntry orderReleaseEntry;
	Date date = new Date(System.currentTimeMillis());
	
	/**
	 * Create Return for a Given Line ID
	 * @param lineID,quantity,returnMethod,returnReasonID
	 * @return {@link ReturnResponse}
	 * @throws JAXBException
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public ReturnResponse createReturn(String lineID, int quantity, ReturnMode returnMethod, Long returnReasonID) throws JAXBException, JsonGenerationException, JsonMappingException, IOException{
		
		OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
		OrderLineEntry orderLineEntry = omsServiceHelper.getOrderLineEntry(""+lineID);
		OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderLineEntry.getOrderId().toString());
		OrderReleaseEntry orderReleaseEntry = omsServiceHelper.getOrderReleaseEntry(omsServiceHelper.getReleaseId(orderEntry.getId().toString()));
		
		ReturnEntry returnEntry = new ReturnEntry();
		returnEntry.setOrderId(orderLineEntry.getOrderId());
		returnEntry.setStoreOrderId(orderLineEntry.getStoreOrderId());
		returnEntry.setComment("Return Creattion Entry For Line ID: "+ lineID + " Quantity : " + quantity);
		returnEntry.setLogin(orderEntry.getLogin());
		returnEntry.setCustomerName(orderReleaseEntry.getReceiverName());
		returnEntry.setEmail(orderReleaseEntry.getEmail());
		returnEntry.setReturnMode(returnMethod);
		returnEntry.setMobile("9823888800");
		
		//Set ReturnAddressDetailsEntry
		ReturnAddressDetailsEntry returnAddressDetailsEntry = new ReturnAddressDetailsEntry();
		//returnAddressDetailsEntry.setReturnAddress("Myntra Design, AKR B, 3rd Floor");
		returnAddressDetailsEntry.setAddress("Myntra Design, AKR B, 3rd Floor");
		returnAddressDetailsEntry.setAddressId(6118982L);
		returnAddressDetailsEntry.setCity("Bangalore");
		returnAddressDetailsEntry.setCountry("India");
		returnAddressDetailsEntry.setState("KA");
		returnAddressDetailsEntry.setZipcode("560067");
		returnEntry.setReturnAddressDetailsEntry(returnAddressDetailsEntry);
		
		//Set Return Line Entries
		ReturnLineEntry returnLineEntry = new ReturnLineEntry();
		returnLineEntry.setComment("Line Entry Comment For Line ID "+lineID);
		returnLineEntry.setOrderId(orderLineEntry.getOrderId());
		returnLineEntry.setOrderLineId(Long.parseLong(lineID));
		returnLineEntry.setOrderReleaseId(orderLineEntry.getOrderReleaseId());
		returnLineEntry.setQuantity(quantity);
		returnLineEntry.setSupplyType(orderLineEntry.getSupplyType());
		returnLineEntry.setReturnReasonId(returnReasonID);
		returnLineEntry.setSkuId(orderLineEntry.getSkuId());
		returnLineEntry.setOptionId(orderLineEntry.getOptionId());
		returnLineEntry.setStyleId(orderLineEntry.getStyleId());
		List<ReturnLineEntry> returnlineEntries = new ArrayList<>();
		returnlineEntries.add(returnLineEntry);
		returnEntry.setReturnLineEntries(returnlineEntries);
		
		ReturnRefundDetailsEntry returnRefundDetailsEntry = new ReturnRefundDetailsEntry();
		returnRefundDetailsEntry.setRefundMode(RefundMode.CASHBACK);
		//returnRefundDetailsEntry.setRefundAccountId();
		
		ReturnTrackingDetailsEntry returnTrackingDetailsEntry = new ReturnTrackingDetailsEntry();
		returnTrackingDetailsEntry.setCourierCode("ML");
		
		returnEntry.setReturnTrackingDetailsEntry(returnTrackingDetailsEntry);
		returnEntry.setReturnRefundDetailsEntry(returnRefundDetailsEntry);
		String payLoad = APIUtilities.getObjectToJSON(returnEntry);
		Svc service = HttpExecutorService.executeHttpService(Constants.RMS_PATH.CREATE_RETURN, null, SERVICE_TYPE.RMS_SVC.toString(), HTTPMethods.POST, payLoad, getRMSHeader());
		ReturnResponse returnResponse =(ReturnResponse) APIUtilities.getJsontoObject(service.getResponseBody(), new ReturnResponse());
		return returnResponse;
	}
	
	/**
	 * Create Return for a Given Line ID with refundMode
	 * @param lineID,quantity,returnMethod,returnReasonID,refundMode
	 * @return {@link ReturnResponse}
	 * @throws JAXBException
	 * @throws IOException
	 */
	public ReturnResponse createReturn(String lineID, int quantity, ReturnMode returnMethod, Long returnReasonID, RefundMode refundMode) throws JAXBException, IOException{
		
		OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
		OrderLineEntry orderLineEntry = omsServiceHelper.getOrderLineEntry(""+lineID);
		OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderLineEntry.getOrderId().toString());
		OrderReleaseEntry orderReleaseEntry = omsServiceHelper.getOrderReleaseEntry(omsServiceHelper.getReleaseId(orderEntry.getId().toString()));
		
		ReturnEntry returnEntry = new ReturnEntry();
		returnEntry.setOrderId(orderLineEntry.getOrderId());
		returnEntry.setStoreOrderId(orderLineEntry.getStoreOrderId());
		returnEntry.setComment("Return Creattion Entry For Line ID: "+ lineID + " Quantity : " + quantity);
		returnEntry.setLogin(orderEntry.getLogin());
		returnEntry.setCustomerName(orderReleaseEntry.getReceiverName());
		returnEntry.setEmail(orderReleaseEntry.getEmail());
		returnEntry.setReturnMode(returnMethod);
		returnEntry.setMobile("9823888800");
		
		//Set ReturnAddressDetailsEntry
		ReturnAddressDetailsEntry returnAddressDetailsEntry = new ReturnAddressDetailsEntry();
		returnAddressDetailsEntry.setAddress("Myntra Design, AKR B, 3rd Floor");
		returnAddressDetailsEntry.setAddressId(6118982L);
		returnAddressDetailsEntry.setCity("Bangalore");
		returnAddressDetailsEntry.setCountry("India");
		returnAddressDetailsEntry.setState("KA");
		returnAddressDetailsEntry.setZipcode("560067");
		returnEntry.setReturnAddressDetailsEntry(returnAddressDetailsEntry);
		
		//Set Return Line Entries
		ReturnLineEntry returnLineEntry = new ReturnLineEntry();
		returnLineEntry.setComment("Line Entry Comment For Line ID "+lineID);
		returnLineEntry.setOrderId(orderLineEntry.getOrderId());
		returnLineEntry.setOrderLineId(Long.parseLong(lineID));
		returnLineEntry.setOrderReleaseId(orderLineEntry.getOrderReleaseId());
		returnLineEntry.setQuantity(quantity);
		returnLineEntry.setSupplyType(orderLineEntry.getSupplyType());
		returnLineEntry.setReturnReasonId(returnReasonID);
		returnLineEntry.setSkuId(orderLineEntry.getSkuId());
		returnLineEntry.setOptionId(orderLineEntry.getOptionId());
		returnLineEntry.setStyleId(orderLineEntry.getStyleId());
		List<ReturnLineEntry> returnlineEntries = new ArrayList<>();
		returnlineEntries.add(returnLineEntry);
		returnEntry.setReturnLineEntries(returnlineEntries);
		
		ReturnRefundDetailsEntry returnRefundDetailsEntry = new ReturnRefundDetailsEntry();
		returnRefundDetailsEntry.setRefundMode(refundMode);
		returnRefundDetailsEntry.setRefundAccountId("418");
		//returnRefundDetailsEntry.setRefundAccountId();
		
		ReturnTrackingDetailsEntry returnTrackingDetailsEntry = new ReturnTrackingDetailsEntry();
		returnTrackingDetailsEntry.setCourierCode("ML");
		
		returnEntry.setReturnTrackingDetailsEntry(returnTrackingDetailsEntry);
		returnEntry.setReturnRefundDetailsEntry(returnRefundDetailsEntry);
		String payLoad = APIUtilities.getObjectToJSON(returnEntry);
		Svc service = HttpExecutorService.executeHttpService(Constants.RMS_PATH.CREATE_RETURN, null, SERVICE_TYPE.RMS_SVC.toString(), HTTPMethods.POST, payLoad, getRMSHeader());
		ReturnResponse returnResponse =(ReturnResponse) APIUtilities.getJsontoObject(service.getResponseBody(), new ReturnResponse());
		return returnResponse;
	}
	
	/**
	 * Create Return for a Given Line ID
	 * @param lineID,quantity,returnMethod,returnReasonID,refundMode,mobileNumber,address,city,state,zipCode,courierCode
	 * @return {@link ReturnResponse}
	 * @throws JAXBException
	 * @throws IOException
	 */
	public ReturnResponse createReturn(String lineID, int quantity, ReturnMode returnMethod, Long returnReasonID, RefundMode refundMode, String mobileNumber,
	                                   String address, String city, String state, String zipCode, String courierCode) throws JAXBException, IOException{
		
		OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
		OrderLineEntry orderLineEntry = omsServiceHelper.getOrderLineEntry(""+lineID);
		OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderLineEntry.getOrderId().toString());
		OrderReleaseEntry orderReleaseEntry = omsServiceHelper.getOrderReleaseEntry(omsServiceHelper.getReleaseId(orderEntry.getId().toString()));
		
		ReturnEntry returnEntry = new ReturnEntry();
		returnEntry.setOrderId(orderLineEntry.getOrderId());
		returnEntry.setStoreOrderId(orderLineEntry.getStoreOrderId());
		returnEntry.setComment("Return Creattion Entry For Line ID: "+ lineID + " Quantity : " + quantity);
		returnEntry.setLogin(orderEntry.getLogin());
		returnEntry.setCustomerName(orderReleaseEntry.getReceiverName());
		returnEntry.setEmail(orderReleaseEntry.getEmail());
		returnEntry.setReturnMode(returnMethod);
		returnEntry.setMobile(mobileNumber);
		returnEntry.setReturnType(ReturnType.NORMAL);
		
		//Set ReturnAddressDetailsEntry
		ReturnAddressDetailsEntry returnAddressDetailsEntry = new ReturnAddressDetailsEntry();
		returnAddressDetailsEntry.setAddress(address);
		returnAddressDetailsEntry.setAddressId(1615L);
		
		returnAddressDetailsEntry.setCity(city);
		returnAddressDetailsEntry.setCountry("India");
		returnAddressDetailsEntry.setState(state);
		returnAddressDetailsEntry.setZipcode(zipCode);
		returnEntry.setReturnAddressDetailsEntry(returnAddressDetailsEntry);
		
		//Set Return Line Entries
		ReturnLineEntry returnLineEntry = new ReturnLineEntry();
		returnLineEntry.setComment("Line Entry Comment For Line ID "+lineID);
		returnLineEntry.setOrderId(orderLineEntry.getOrderId());
		returnLineEntry.setOrderLineId(Long.parseLong(lineID));
		returnLineEntry.setOrderReleaseId(orderLineEntry.getOrderReleaseId());
		returnLineEntry.setQuantity(quantity);
		returnLineEntry.setSupplyType(orderLineEntry.getSupplyType());
		returnLineEntry.setReturnReasonId(returnReasonID);
		returnLineEntry.setSkuId(orderLineEntry.getSkuId());
		returnLineEntry.setOptionId(orderLineEntry.getOptionId());
		returnLineEntry.setStyleId(orderLineEntry.getStyleId());
		List<ReturnLineEntry> returnlineEntries = new ArrayList<>();
		returnlineEntries.add(returnLineEntry);
		returnEntry.setReturnLineEntries(returnlineEntries);
		
		ReturnRefundDetailsEntry returnRefundDetailsEntry = new ReturnRefundDetailsEntry();
		returnRefundDetailsEntry.setRefundMode(refundMode);
		returnRefundDetailsEntry.setRefundAccountId("418");
		//returnRefundDetailsEntry.setRefundAccountId();
		
		ReturnTrackingDetailsEntry returnTrackingDetailsEntry = new ReturnTrackingDetailsEntry();
		returnTrackingDetailsEntry.setCourierCode(courierCode);
		
		returnEntry.setReturnTrackingDetailsEntry(returnTrackingDetailsEntry);
		returnEntry.setReturnRefundDetailsEntry(returnRefundDetailsEntry);
		String payLoad = APIUtilities.getObjectToJSON(returnEntry);
		Svc service = HttpExecutorService.executeHttpService(Constants.RMS_PATH.CREATE_RETURN, null, SERVICE_TYPE.RMS_SVC.toString(), HTTPMethods.POST, payLoad, getRMSHeader());
		ReturnResponse returnResponse =(ReturnResponse) APIUtilities.getJsontoObject(service.getResponseBody(), new ReturnResponse());
		return returnResponse;
	}
	
	/**
	 * Create a PurePlay Return for a Given Line ID
	 * @param lineID,quantity,returnMethod,returnReasonID,refundMode
	 * @return {@link ReturnResponse}
	 * @throws JAXBException
	 * @throws IOException
	 */
	public ReturnResponse createReturn_pureplay(String lineID, int quantity, ReturnMode returnMethod, Long returnReasonID, RefundMode refundMode) throws JAXBException, IOException{
		
		OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
		OrderLineEntry orderLineEntry = omsServiceHelper.getOrderLineEntry(""+lineID);
		OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderLineEntry.getOrderId().toString());
		OrderReleaseEntry orderReleaseEntry = omsServiceHelper.getOrderReleaseEntry(omsServiceHelper.getReleaseId(orderEntry.getId().toString()));
		
		ReturnEntry returnEntry = new ReturnEntry();
		returnEntry.setOrderId(orderLineEntry.getOrderId());
		returnEntry.setStoreOrderId(orderLineEntry.getStoreOrderId());
		returnEntry.setComment("Return Creattion Entry For Line ID: "+ lineID + " Quantity : " + quantity);
		returnEntry.setLogin(orderEntry.getLogin());
		returnEntry.setCustomerName(orderReleaseEntry.getReceiverName());
		returnEntry.setEmail(orderReleaseEntry.getEmail());
		returnEntry.setReturnMode(returnMethod);
		returnEntry.setMobile("9823888800");
		
		//Set ReturnAddressDetailsEntry
		ReturnAddressDetailsEntry returnAddressDetailsEntry = new ReturnAddressDetailsEntry();
		//returnAddressDetailsEntry.setReturnAddress("Myntra Design, AKR B, 3rd Floor");
		returnAddressDetailsEntry.setAddress("Myntra Design, AKR B, 3rd Floor");
		returnAddressDetailsEntry.setAddressId(6118982L);
		returnAddressDetailsEntry.setCity("Bangalore");
		returnAddressDetailsEntry.setCountry("India");
		returnAddressDetailsEntry.setState("KA");
		returnAddressDetailsEntry.setZipcode("560067");
		returnEntry.setReturnAddressDetailsEntry(returnAddressDetailsEntry);
		
		//Set Return Line Entries
		ReturnLineEntry returnLineEntry = new ReturnLineEntry();
		returnLineEntry.setComment("Line Entry Comment For Line ID "+lineID);
		returnLineEntry.setOrderId(orderLineEntry.getOrderId());
		returnLineEntry.setOrderLineId(Long.parseLong(lineID));
		returnLineEntry.setOrderReleaseId(orderLineEntry.getOrderReleaseId());
		returnLineEntry.setQuantity(quantity);
		returnLineEntry.setSupplyType(orderLineEntry.getSupplyType());
		returnLineEntry.setReturnReasonId(returnReasonID);
		returnLineEntry.setSkuId(orderLineEntry.getSkuId());
		returnLineEntry.setOptionId(orderLineEntry.getOptionId());
		returnLineEntry.setStyleId(orderLineEntry.getStyleId());
		List<ReturnLineEntry> returnlineEntries = new ArrayList<>();
		returnlineEntries.add(returnLineEntry);
		returnEntry.setReturnLineEntries(returnlineEntries);
		
		ReturnRefundDetailsEntry returnRefundDetailsEntry = new ReturnRefundDetailsEntry();
		returnRefundDetailsEntry.setRefundMode(refundMode);
		returnRefundDetailsEntry.setRefundAccountId("418");
		//returnRefundDetailsEntry.setRefundAccountId();
		
		ReturnTrackingDetailsEntry returnTrackingDetailsEntry = new ReturnTrackingDetailsEntry();
		returnTrackingDetailsEntry.setCourierCode("TB");
		
		returnEntry.setReturnTrackingDetailsEntry(returnTrackingDetailsEntry);
		returnEntry.setReturnRefundDetailsEntry(returnRefundDetailsEntry);
		String payLoad = APIUtilities.getObjectToJSON(returnEntry);
		Svc service = HttpExecutorService.executeHttpService(Constants.RMS_PATH.CREATE_RETURN, null, SERVICE_TYPE.RMS_SVC.toString(), HTTPMethods.POST, payLoad, getRMSHeader());
		ReturnResponse returnResponse =(ReturnResponse) APIUtilities.getJsontoObject(service.getResponseBody(), new ReturnResponse());
		return returnResponse;
	}
	

	/**
	 * Revamped create return for a Given Line ID
	 * @param lineID,returnType,returnMode,quantity,returnReasonID , refundMode , refundAccountId
	 * @return {@link ReturnResponse}
	 * @throws JAXBException
	 * @throws IOException
	 */
	public ReturnResponse createReturn(Long lineID,ReturnType returnType,ReturnMode returnMode,int quantity, Long returnReasonID, RefundMode refundMode,String refundAccountId) throws JAXBException, IOException{
		
		OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
		OrderLineEntry orderLineEntry = omsServiceHelper.getOrderLineEntry(""+lineID);
		OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderLineEntry.getOrderId().toString());
		OrderReleaseEntry orderReleaseEntry = omsServiceHelper.getOrderReleaseEntry(omsServiceHelper.getReleaseId(orderEntry.getId().toString()));
		
		ReturnEntry returnEntry = new ReturnEntry();
		returnEntry.setOrderId(orderLineEntry.getOrderId());
		returnEntry.setReturnType(returnType);
		returnEntry.setReturnMode(returnMode);
		returnEntry.setStoreId(1);
		returnEntry.setLogin(orderEntry.getLogin());
		returnEntry.setCustomerName(orderReleaseEntry.getReceiverName());
		//returnEntry.setStoreOrderId(orderLineEntry.getStoreOrderId());
		returnEntry.setComment("Return Creation Entry For Line ID: "+ lineID + " Quantity : " + quantity);
		returnEntry.setEmail(orderReleaseEntry.getEmail());
		returnEntry.setMobile(orderReleaseEntry.getMobile());
		
		//Set ReturnAddressDetailsEntry
		ReturnAddressDetailsEntry returnAddressDetailsEntry = new ReturnAddressDetailsEntry();
		returnAddressDetailsEntry.setAddress(orderReleaseEntry.getAddress());
		returnAddressDetailsEntry.setAddressId(orderReleaseEntry.getAddressId());
		returnAddressDetailsEntry.setCity(orderReleaseEntry.getCity());
		returnAddressDetailsEntry.setCountry(orderReleaseEntry.getCountry());
		returnAddressDetailsEntry.setState(orderReleaseEntry.getState());
		returnAddressDetailsEntry.setZipcode(orderReleaseEntry.getZipcode());
		returnEntry.setReturnAddressDetailsEntry(returnAddressDetailsEntry);
		
		//Set Return Line Entries
		ReturnLineEntry returnLineEntry = new ReturnLineEntry();
		returnLineEntry.setComment("Line Entry Comment For Line ID "+lineID);
		returnLineEntry.setOrderId(orderLineEntry.getOrderId());
		returnLineEntry.setOrderLineId(lineID);
		returnLineEntry.setOrderReleaseId(orderLineEntry.getOrderReleaseId());
		returnLineEntry.setQuantity(quantity);
		returnLineEntry.setSupplyType(orderLineEntry.getSupplyType());
		returnLineEntry.setReturnReasonId(returnReasonID);
		returnLineEntry.setSkuId(orderLineEntry.getSkuId());
		returnLineEntry.setOptionId(orderLineEntry.getOptionId());
		returnLineEntry.setStyleId(orderLineEntry.getStyleId());
		List<ReturnLineEntry> returnlineEntries = new ArrayList<>();
		returnlineEntries.add(returnLineEntry);
		returnEntry.setReturnLineEntries(returnlineEntries);
		
		ReturnRefundDetailsEntry returnRefundDetailsEntry = new ReturnRefundDetailsEntry();
		returnRefundDetailsEntry.setRefundMode(refundMode);
		//returnRefundDetailsEntry.setRefundAccountId("418");
		returnRefundDetailsEntry.setRefundAccountId(refundAccountId);
		returnEntry.setReturnRefundDetailsEntry(returnRefundDetailsEntry);
		
		//returnRefundDetailsEntry.setRefundAccountId();
		
		//ReturnTrackingDetailsEntry returnTrackingDetailsEntry = new ReturnTrackingDetailsEntry();
		//returnTrackingDetailsEntry.setCourierCode(courierCode);
		
		//returnEntry.setReturnTrackingDetailsEntry(returnTrackingDetailsEntry);
		String payLoad = APIUtilities.getObjectToJSON(returnEntry);
		Svc service = HttpExecutorService.executeHttpService(Constants.RMS_PATH.CREATE_RETURN, null, SERVICE_TYPE.RMS_SVC.toString(), HTTPMethods.POST, payLoad, getRMSHeader());
		ReturnResponse returnResponse =(ReturnResponse) APIUtilities.getJsontoObject(service.getResponseBody(), new ReturnResponse());
		return returnResponse;
	}
	
	/**
	 * Revamped create return with address as input for a Given Line ID
	 * @param lineID,returnType,returnMode,quantity,returnReasonID , refundMode , refundAccountId , address,addressId,pincode, city, state,country, mobileNumber
	 * @return {@link ReturnResponse}
	 * @throws JAXBException
	 * @throws IOException
	 */
	public ReturnResponse createReturn(Long lineID,ReturnType returnType,ReturnMode returnMode,int quantity, Long returnReasonID, RefundMode refundMode,String refundAccountId,String address,String addressId, String pincode,String city,String state,String country,String mobileNumber) throws JAXBException, IOException{
		
		OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
		OrderLineEntry orderLineEntry = omsServiceHelper.getOrderLineEntry(""+lineID);
		OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderLineEntry.getOrderId().toString());
		OrderReleaseEntry orderReleaseEntry = omsServiceHelper.getOrderReleaseEntry(omsServiceHelper.getReleaseId(orderEntry.getId().toString()));
		
		ReturnEntry returnEntry = new ReturnEntry();
		returnEntry.setOrderId(orderLineEntry.getOrderId());
		returnEntry.setReturnType(returnType);
		returnEntry.setReturnMode(returnMode);
		returnEntry.setStoreId(1);
		returnEntry.setLogin(orderEntry.getLogin());
		returnEntry.setCustomerName(orderReleaseEntry.getReceiverName());
		//returnEntry.setStoreOrderId(orderLineEntry.getStoreOrderId());
		returnEntry.setComment("Return Creation Entry For Line ID: "+ lineID + " Quantity : " + quantity);
		returnEntry.setEmail(orderReleaseEntry.getEmail());
		returnEntry.setMobile(mobileNumber);
		
		
		
		//Set ReturnAddressDetailsEntry
		ReturnAddressDetailsEntry returnAddressDetailsEntry = new ReturnAddressDetailsEntry();
		returnAddressDetailsEntry.setAddress(address);
		returnAddressDetailsEntry.setAddressId(Long.valueOf(addressId));
		returnAddressDetailsEntry.setCity(city);
		returnAddressDetailsEntry.setCountry(country);
		returnAddressDetailsEntry.setState(state);
		returnAddressDetailsEntry.setZipcode(pincode);
		returnEntry.setReturnAddressDetailsEntry(returnAddressDetailsEntry);
		
		//Set Return Line Entries
		ReturnLineEntry returnLineEntry = new ReturnLineEntry();
		returnLineEntry.setComment("Line Entry Comment For Line ID "+lineID);
		returnLineEntry.setOrderId(orderLineEntry.getOrderId());
		returnLineEntry.setOrderLineId(lineID);
		returnLineEntry.setOrderReleaseId(orderLineEntry.getOrderReleaseId());
		returnLineEntry.setQuantity(quantity);
		returnLineEntry.setSupplyType(orderLineEntry.getSupplyType());
		returnLineEntry.setReturnReasonId(returnReasonID);
		returnLineEntry.setSkuId(orderLineEntry.getSkuId());
		returnLineEntry.setOptionId(orderLineEntry.getOptionId());
		returnLineEntry.setStyleId(orderLineEntry.getStyleId());
		List<ReturnLineEntry> returnlineEntries = new ArrayList<>();
		returnlineEntries.add(returnLineEntry);
		returnEntry.setReturnLineEntries(returnlineEntries);
		
		ReturnRefundDetailsEntry returnRefundDetailsEntry = new ReturnRefundDetailsEntry();
		returnRefundDetailsEntry.setRefundMode(refundMode);
		//returnRefundDetailsEntry.setRefundAccountId("418");
		returnRefundDetailsEntry.setRefundAccountId(refundAccountId);
		returnEntry.setReturnRefundDetailsEntry(returnRefundDetailsEntry);
		
		//returnRefundDetailsEntry.setRefundAccountId();
		
		//ReturnTrackingDetailsEntry returnTrackingDetailsEntry = new ReturnTrackingDetailsEntry();
		//returnTrackingDetailsEntry.setCourierCode(courierCode);
		
		//returnEntry.setReturnTrackingDetailsEntry(returnTrackingDetailsEntry);
		String payLoad = APIUtilities.getObjectToJSON(returnEntry);
		Svc service = HttpExecutorService.executeHttpService(Constants.RMS_PATH.CREATE_RETURN, null, SERVICE_TYPE.RMS_SVC.toString(), HTTPMethods.POST, payLoad, getRMSHeader());
		ReturnResponse returnResponse =(ReturnResponse) APIUtilities.getJsontoObject(service.getResponseBody(), new ReturnResponse());
		return returnResponse;
	}
	
	
	/**
	 * To mark return declined
	 * @param returnId,returnActionCode,skuId,quantity
	 * @return {@link ReturnResponse}
	 * @throws IOException
	 */
	public ReturnResponse returnDecline(Long returnId, ReturnActionCode returnActionCode, Long skuId, int quantity) throws IOException {
		
		ReturnEntry returnEntry = new ReturnEntry();
		ReturnUpdateRequestEntry returnUpdate= new ReturnUpdateRequestEntry();
		returnUpdate.setReturnId(returnId);
		returnUpdate.setCancellationType(CancellationType.LOGISTICS_CANCELLATION);
		returnUpdate.setCreatedOn(date);
		returnUpdate.setCreatedBy("Logistic-ops");
		returnUpdate.setUserComment("RMS automation");
		returnUpdate.setReturnActionCode(returnActionCode);
		
		ReturnLineUpdateRequestEntry returnLineUpdate = new ReturnLineUpdateRequestEntry();
		returnLineUpdate.setSkuId(skuId);
		returnLineUpdate.setQuantity(quantity);
		List<ReturnLineUpdateRequestEntry> returnLineUpdateRequestEntryList= new ArrayList<>();
		returnLineUpdateRequestEntryList.add(returnLineUpdate);
		returnUpdate.setReturnLineUpdateRequestEntryList(returnLineUpdateRequestEntryList);
		
		String payLoad = APIUtilities.getObjectToJSON(returnUpdate);
		Svc service = HttpExecutorService.executeHttpService(Constants.RMS_PATH.RETURN_STATUS_PROCESS, null, SERVICE_TYPE.RMS_SVC.toString(), HTTPMethods.POST, payLoad, getRMSHeader());
		ReturnResponse returnResponse =(ReturnResponse) APIUtilities.getJsontoObject(service.getResponseBody(), new ReturnResponse());
		return returnResponse;
	}
	
	/**
	 * To receive a return 
	 * @param returnId,returnActionCode,skuId,quantity
	 * @return {@link ReturnResponse}
	 * @throws IOException
	 */
	public ReturnResponse returnReceive(Long returnId, ReturnActionCode returnActionCode, Long skuId, int quantity) throws IOException {
		
		ReturnEntry returnEntry = new ReturnEntry();
		ReturnUpdateRequestEntry returnUpdate= new ReturnUpdateRequestEntry();
		returnUpdate.setReturnId(returnId);
		returnUpdate.setCreatedOn(date);
		returnUpdate.setCreatedBy("Logistic-ops");
		returnUpdate.setUserComment("RMS automation");
		returnUpdate.setReturnActionCode(returnActionCode);
		
		ReturnLineUpdateRequestEntry returnLineUpdate = new ReturnLineUpdateRequestEntry();
		returnLineUpdate.setSkuId(skuId);
		returnLineUpdate.setQuantity(quantity);
		List<ReturnLineUpdateRequestEntry> returnLineUpdateRequestEntryList= new ArrayList<>();
		returnLineUpdateRequestEntryList.add(returnLineUpdate);
		returnUpdate.setReturnLineUpdateRequestEntryList(returnLineUpdateRequestEntryList);
		
		String payLoad = APIUtilities.getObjectToJSON(returnUpdate);
		Svc service = HttpExecutorService.executeHttpService(Constants.RMS_PATH.RETURN_STATUS_PROCESS, null, SERVICE_TYPE.RMS_SVC.toString(), HTTPMethods.POST, payLoad, getRMSHeader());
		ReturnResponse returnResponse =(ReturnResponse) APIUtilities.getJsontoObject(service.getResponseBody(), new ReturnResponse());
		return returnResponse;
	}
	
	/**
	 * To reship a return
	 * @param returnId,returnActionCode,skuId,quantity
	 * @return {@link ReturnResponse}
	 * @throws IOException
	 */
	public ReturnResponse returnReadyToReship(Long returnId, ReturnActionCode returnActionCode, Long skuId, int quantity) throws IOException {
		
		ReturnEntry returnEntry = new ReturnEntry();
		ReturnUpdateRequestEntry returnUpdate= new ReturnUpdateRequestEntry();
		returnUpdate.setReturnId(returnId);
		returnUpdate.setCreatedOn(date);
		returnUpdate.setCreatedBy("Logistic-ops");
		returnUpdate.setUserComment("RMS automation");
		returnUpdate.setReturnActionCode(returnActionCode);
		
		ReturnLineUpdateRequestEntry returnLineUpdate = new ReturnLineUpdateRequestEntry();
		returnLineUpdate.setSkuId(skuId);
		returnLineUpdate.setQuantity(quantity);
		List<ReturnLineUpdateRequestEntry> returnLineUpdateRequestEntryList= new ArrayList<>();
		returnLineUpdateRequestEntryList.add(returnLineUpdate);
		returnUpdate.setReturnLineUpdateRequestEntryList(returnLineUpdateRequestEntryList);
		
		String payLoad = APIUtilities.getObjectToJSON(returnUpdate);
		Svc service = HttpExecutorService.executeHttpService(Constants.RMS_PATH.RETURN_STATUS_PROCESS, null, SERVICE_TYPE.RMS_SVC.toString(), HTTPMethods.POST, payLoad, getRMSHeader());
		ReturnResponse returnResponse =(ReturnResponse) APIUtilities.getJsontoObject(service.getResponseBody(), new ReturnResponse());
		return returnResponse;
	}
	
	/**
	 * To mark return ship to customer
	 * @param returnId,returnActionCode,skuId,quantity
	 * @return {@link ReturnResponse}
	 * @throws IOException
	 */
	public ReturnResponse returnShipToCustomer(Long returnId, ReturnActionCode returnActionCode, Long skuId, int quantity) throws IOException {
		
		ReturnEntry returnEntry = new ReturnEntry();
		ReturnUpdateRequestEntry returnUpdate= new ReturnUpdateRequestEntry();
		returnUpdate.setReturnId(returnId);
		returnUpdate.setCreatedOn(date);
		returnUpdate.setCreatedBy("Logistic-ops");
		returnUpdate.setUserComment("RMS automation");
		returnUpdate.setReturnActionCode(returnActionCode);
		returnUpdate.setReshipCourier("DE");
		returnUpdate.setReshipTrackingNumber("DE123456789");
		
		ReturnLineUpdateRequestEntry returnLineUpdate = new ReturnLineUpdateRequestEntry();
		returnLineUpdate.setSkuId(skuId);
		returnLineUpdate.setQuantity(quantity);
		List<ReturnLineUpdateRequestEntry> returnLineUpdateRequestEntryList= new ArrayList<>();
		returnLineUpdateRequestEntryList.add(returnLineUpdate);
		returnUpdate.setReturnLineUpdateRequestEntryList(returnLineUpdateRequestEntryList);
		
		String payLoad = APIUtilities.getObjectToJSON(returnUpdate);
		Svc service = HttpExecutorService.executeHttpService(Constants.RMS_PATH.RETURN_STATUS_PROCESS, null, SERVICE_TYPE.RMS_SVC.toString(), HTTPMethods.POST, payLoad, getRMSHeader());
		ReturnResponse returnResponse =(ReturnResponse) APIUtilities.getJsontoObject(service.getResponseBody(), new ReturnResponse());
		return returnResponse;
	}
	
	/**
	 * To complete logistics processing for a return
	 * @param returnId,returnActionCode,skuId,quantity
	 * @return {@link ReturnResponse}
	 * @throws IOException
	 */
	public ReturnResponse returnCompleteLogisticsProcessing(Long returnId, ReturnActionCode returnActionCode, Long skuId, int quantity) throws IOException {
		
		ReturnEntry returnEntry = new ReturnEntry();
		ReturnUpdateRequestEntry returnUpdate= new ReturnUpdateRequestEntry();
		returnUpdate.setReturnId(returnId);
		returnUpdate.setCreatedOn(date);
		returnUpdate.setCreatedBy("Logistic-ops");
		returnUpdate.setUserComment("RMS automation");
		returnUpdate.setReturnActionCode(returnActionCode);
		
		
		ReturnLineUpdateRequestEntry returnLineUpdate = new ReturnLineUpdateRequestEntry();
		returnLineUpdate.setSkuId(skuId);
		returnLineUpdate.setQuantity(quantity);
		List<ReturnLineUpdateRequestEntry> returnLineUpdateRequestEntryList= new ArrayList<>();
		returnLineUpdateRequestEntryList.add(returnLineUpdate);
		returnUpdate.setReturnLineUpdateRequestEntryList(returnLineUpdateRequestEntryList);
		
		String payLoad = APIUtilities.getObjectToJSON(returnUpdate);
		Svc service = HttpExecutorService.executeHttpService(Constants.RMS_PATH.RETURN_STATUS_PROCESS, null, SERVICE_TYPE.RMS_SVC.toString(), HTTPMethods.POST, payLoad, getRMSHeader());
		ReturnResponse returnResponse =(ReturnResponse) APIUtilities.getJsontoObject(service.getResponseBody(), new ReturnResponse());
		return returnResponse;
	}
	
	
	/**
	 * To mark return lost
	 * @param returnId,returnActionCode,skuId,quantity
	 * @return {@link ReturnResponse}
	 * @throws IOException
	 */
	public ReturnResponse returnLost(Long returnId, ReturnActionCode returnActionCode, Long skuId, int quantity) throws IOException {
		
		ReturnEntry returnEntry = new ReturnEntry();
		ReturnUpdateRequestEntry returnUpdate= new ReturnUpdateRequestEntry();
		returnUpdate.setReturnId(returnId);
		returnUpdate.setCreatedOn(date);
		returnUpdate.setCreatedBy("Logistic-ops");
		returnUpdate.setUserComment("RMS automation");
		returnUpdate.setReturnActionCode(returnActionCode);
		
		
		ReturnLineUpdateRequestEntry returnLineUpdate = new ReturnLineUpdateRequestEntry();
		returnLineUpdate.setSkuId(skuId);
		returnLineUpdate.setQuantity(quantity);
		List<ReturnLineUpdateRequestEntry> returnLineUpdateRequestEntryList= new ArrayList<>();
		returnLineUpdateRequestEntryList.add(returnLineUpdate);
		returnUpdate.setReturnLineUpdateRequestEntryList(returnLineUpdateRequestEntryList);
		
		String payLoad = APIUtilities.getObjectToJSON(returnUpdate);
		Svc service = HttpExecutorService.executeHttpService(Constants.RMS_PATH.RETURN_STATUS_PROCESS, null, SERVICE_TYPE.RMS_SVC.toString(), HTTPMethods.POST, payLoad, getRMSHeader());
		ReturnResponse returnResponse =(ReturnResponse) APIUtilities.getJsontoObject(service.getResponseBody(), new ReturnResponse());
		return returnResponse;
	}
	
	
	/**
	 * Create a Try and Buy Return for a Given Line ID
	 * @param lineID,quantity,returnMethod,returnReasonID,refundMode,warehouseId,courier,trackingNo
	 * @return {@link ReturnResponse}
	 * @throws JAXBException
	 * @throws IOException
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 */
	public ReturnResponse createReturnTnB(String lineID, int quantity, ReturnMode returnMethod,Integer warehouseId, Long returnReasonID, RefundMode refundMode, String courier, String trackingNo) throws JAXBException, JsonGenerationException, JsonMappingException, IOException{
		
		OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
		OrderLineEntry orderLineEntry = omsServiceHelper.getOrderLineEntry(""+lineID);
		OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderLineEntry.getOrderId().toString());
		//OrderReleaseEntry orderReleaseEntry = omsServiceHelper.getOrderReleaseEntry(orderLineEntry.getOrderReleaseId());
		OrderReleaseEntry orderReleaseEntry = omsServiceHelper.getOrderReleaseEntry(omsServiceHelper.getReleaseId(orderEntry.getId().toString()));
		
		ReturnEntry returnEntry = new ReturnEntry();
		//returnEntry.setOrderId(orderLineEntry.getOrderId());
		returnEntry.setComment("Return Creattion Entry For Line ID: "+ lineID + " Quantity : " + quantity);
		//returnEntry.setLogin(orderEntry.getLogin());
		//returnEntry.setCustomerName(orderReleaseEntry.getReceiverName());
		//returnEntry.setEmail(orderReleaseEntry.getEmail());
		returnEntry.setReturnMode(returnMethod);
		//returnEntry.setMobile("9823888800");
		
		//Set ReturnAddressDetailsEntry
		//ReturnAddressDetailsEntry returnAddressDetailsEntry = new ReturnAddressDetailsEntry();
		//returnAddressDetailsEntry.setReturnAddress("Myntra Design, AKR B, 3rd Floor");
		//returnAddressDetailsEntry.setAddressId(6118982L);
		//returnAddressDetailsEntry.setCity("Bangalore");
		//returnAddressDetailsEntry.setCountry("India");
		//returnAddressDetailsEntry.setState("KA");
		//returnAddressDetailsEntry.setZipcode("560067");
		//returnEntry.setReturnAddressDetailsEntry(returnAddressDetailsEntry);
		
		//Set Return Line Entries
		ReturnLineEntry returnLineEntry = new ReturnLineEntry();
		//returnLineEntry.setComment("Line Entry Comment For Line ID "+lineID);
		//returnLineEntry.setOrderId(orderLineEntry.getOrderId());
		returnLineEntry.setOrderLineId(Long.parseLong(lineID));
		//returnLineEntry.setOrderReleaseId(orderLineEntry.getOrderReleaseId());
		returnLineEntry.setQuantity(quantity);
		//returnLineEntry.setSupplyType(orderLineEntry.getSupplyType());
		returnLineEntry.setReturnReasonId(returnReasonID);
		//returnLineEntry.setSkuId(orderLineEntry.getSkuId());
		//returnLineEntry.setOptionId(orderLineEntry.getOptionId());
		//returnLineEntry.setStyleId(orderLineEntry.getStyleId());
		returnLineEntry.setWarehouseId(warehouseId);
		List<ReturnLineEntry> returnlineEntries = new ArrayList<>();
		returnlineEntries.add(returnLineEntry);
		returnEntry.setReturnLineEntries(returnlineEntries);
		
		//ReturnRefundDetailsEntry returnRefundDetailsEntry = new ReturnRefundDetailsEntry();
		//returnRefundDetailsEntry.setRefundMode(refundMode);
		//returnRefundDetailsEntry.setRefundAccountId("418");
		//returnRefundDetailsEntry.setRefundAccountId();
		
		ReturnTrackingDetailsEntry returnTrackingDetailsEntry = new ReturnTrackingDetailsEntry();
		returnTrackingDetailsEntry.setCourierCode(courier);
		returnTrackingDetailsEntry.setTrackingNo(trackingNo);
		//returnTrackingDetailsEntry.setWarehouseId(1);
		
		//ReturnAdditionalDetailsEntry returnAdditionalDetailsEntry = new ReturnAdditionalDetailsEntry();
		//returnAdditionalDetailsEntry.setIdealReturnWarehouse(1L);
		
		//returnEntry.setReturnAdditionalDetailsEntry(returnAdditionalDetailsEntry);
		
		returnEntry.setReturnTrackingDetailsEntry(returnTrackingDetailsEntry);
		//returnEntry.setReturnRefundDetailsEntry(returnRefundDetailsEntry);
		String payLoad = APIUtilities.getObjectToJSON(returnEntry);
		Svc service = HttpExecutorService.executeHttpService(Constants.RMS_PATH.CREATE_RETURN_TNB, null, SERVICE_TYPE.RMS_SVC.toString(), HTTPMethods.POST, payLoad, getRMSHeader());
		ReturnResponse returnResponse =(ReturnResponse) APIUtilities.getJsontoObject(service.getResponseBody(), new ReturnResponse());
		return returnResponse;
	}
	
	/**
	 * Create a SelfShip Return for a Given Line ID
	 * @param lineID,quantity,returnMethod,returnReasonID,refundMode,courier,trackingNo
	 * @return {@link ReturnResponse}
	 * @throws JAXBException
	 * @throws IOException
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 */
	public ReturnResponse createReturnSelf(String lineID, int quantity, ReturnMode returnMethod, Long returnReasonID, RefundMode refundMode, String courier, String trackingNo) throws JAXBException, JsonGenerationException, JsonMappingException, IOException{
		
		OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
		OrderLineEntry orderLineEntry = omsServiceHelper.getOrderLineEntry(""+lineID);
		OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderLineEntry.getOrderId().toString());
		OrderReleaseEntry orderReleaseEntry = omsServiceHelper.getOrderReleaseEntry(orderLineEntry.getOrderReleaseId().toString());
		
		ReturnEntry returnEntry = new ReturnEntry();
		returnEntry.setOrderId(orderLineEntry.getOrderId());
		returnEntry.setStoreOrderId(orderLineEntry.getStoreOrderId());
		returnEntry.setComment("Return Creattion Entry For Line ID: "+ lineID + " Quantity : " + quantity);
		returnEntry.setLogin(orderEntry.getLogin());
		returnEntry.setCustomerName(orderReleaseEntry.getReceiverName());
		returnEntry.setEmail(orderReleaseEntry.getEmail());
		returnEntry.setReturnMode(returnMethod);
		//returnEntry.setMobile("9823888800");
		
		//Set ReturnAddressDetailsEntry
		ReturnAddressDetailsEntry returnAddressDetailsEntry = new ReturnAddressDetailsEntry();
		returnAddressDetailsEntry.setAddress("Myntra Design, AKR B, 3rd Floor");
		returnAddressDetailsEntry.setAddressId(6118982L);
		returnAddressDetailsEntry.setCity("Bangalore");
		returnAddressDetailsEntry.setCountry("India");
		returnAddressDetailsEntry.setState("KA");
		returnAddressDetailsEntry.setZipcode("560067");
		returnEntry.setReturnAddressDetailsEntry(returnAddressDetailsEntry);
		
		//Set Return Line Entries
		ReturnLineEntry returnLineEntry = new ReturnLineEntry();
		returnLineEntry.setComment("Line Entry Comment For Line ID "+lineID);
		returnLineEntry.setOrderId(orderLineEntry.getOrderId());
		returnLineEntry.setOrderLineId(Long.parseLong(lineID));
		returnLineEntry.setOrderReleaseId(orderLineEntry.getOrderReleaseId());
		returnLineEntry.setQuantity(quantity);
		returnLineEntry.setSupplyType(orderLineEntry.getSupplyType());
		returnLineEntry.setReturnReasonId(returnReasonID);
		returnLineEntry.setSkuId(orderLineEntry.getSkuId());
		returnLineEntry.setOptionId(orderLineEntry.getOptionId());
		returnLineEntry.setStyleId(orderLineEntry.getStyleId());
		List<ReturnLineEntry> returnlineEntries = new ArrayList<>();
		returnlineEntries.add(returnLineEntry);
		returnEntry.setReturnLineEntries(returnlineEntries);
		
		ReturnRefundDetailsEntry returnRefundDetailsEntry = new ReturnRefundDetailsEntry();
		returnRefundDetailsEntry.setRefundMode(refundMode);
		returnRefundDetailsEntry.setRefundAccountId("418");
		//returnRefundDetailsEntry.setRefundAccountId();
		
		ReturnTrackingDetailsEntry returnTrackingDetailsEntry = new ReturnTrackingDetailsEntry();
		returnTrackingDetailsEntry.setCourierCode(courier);
		returnTrackingDetailsEntry.setTrackingNo(trackingNo);
		
		returnEntry.setReturnTrackingDetailsEntry(returnTrackingDetailsEntry);
		returnEntry.setReturnRefundDetailsEntry(returnRefundDetailsEntry);
		String payLoad = APIUtilities.getObjectToJSON(returnEntry);
		Svc service = HttpExecutorService.executeHttpService(Constants.RMS_PATH.CREATE_RETURN, null, SERVICE_TYPE.RMS_SVC.toString(), HTTPMethods.POST, payLoad, getRMSHeader());
		ReturnResponse returnResponse =(ReturnResponse) APIUtilities.getJsontoObject(service.getResponseBody(), new ReturnResponse());
		return returnResponse;
	}
	
	/**
	 * Change State of Return for a given Return ID
	 * @param returnID,ReturnStatus,fromState,toState
	 * @return {@link ReturnResponse}
	 * @throws IOException
	 */
	public ReturnResponse returnStatusProcessNew(String returnID, ReturnStatus fromState, ReturnStatus toState) throws IOException {
		
		ReturnUpdateRequestEntry returnUpdate= new ReturnUpdateRequestEntry();
		returnUpdate.setFromState(fromState);
		returnUpdate.setToState(toState);
		returnUpdate.setReturnId(Long.parseLong(returnID));
		
		String payLoad = APIUtilities.getObjectToJSON(returnUpdate);
		Svc service = HttpExecutorService.executeHttpService(Constants.RMS_PATH.RETURN_STATUS_PROCESS, null, SERVICE_TYPE.RMS_SVC.toString(), HTTPMethods.POST, payLoad, getRMSHeader());
		ReturnResponse returnResponse =(ReturnResponse) APIUtilities.getJsontoObject(service.getResponseBody(), new ReturnResponse());
		return returnResponse;
	}
	
	/**
	 * Change Bulk Status of Return for a given Return ID
	 * @param returnID,toState,trackingNumber,dcCode
	 * @return {@link ReturnResponse}
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public ReturnResponse bulk_statuschange(String returnID, ReturnStatus toState,String trackingNumber,String dcCode) throws JsonParseException, JsonMappingException, IOException {
		
		List<ReturnUpdateRequestEntry> bulkReturnEntry = new ArrayList<>();
		ReturnUpdateRequestEntry returnUpdate= new ReturnUpdateRequestEntry();
		returnUpdate.setToState(toState);
		returnUpdate.setReturnId(Long.parseLong(returnID));
		returnUpdate.setTrackingNumber(trackingNumber);
		returnUpdate.setDcCode(dcCode);
		bulkReturnEntry.add(returnUpdate);
		
		String payLoad = APIUtilities.getObjectToJSON(bulkReturnEntry);
		Svc service = HttpExecutorService.executeHttpService(Constants.RMS_PATH.RETURN_BULK_STATUS_PROCESS, null, SERVICE_TYPE.RMS_SVC.toString(), HTTPMethods.POST, payLoad, getRMSHeader());
		ReturnResponse returnResponse =(ReturnResponse) APIUtilities.getJsontoObject(service.getResponseBody(), new ReturnResponse());
		return returnResponse;
	}
	
	/**
	 * Change Bulk Status of Return for a given Return ID with Warehouse and Quality
	 * @param returnID,toState,trackingNumber,dcCode,warehouseId,quality
	 * @return {@link ReturnResponse}
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public ReturnResponse bulk_statuschange(Long returnID, ReturnStatus toState,String trackingNumber,String dcCode,Integer warehouseId,Boolean quality) throws JsonParseException, JsonMappingException, IOException {
		
		List<ReturnUpdateRequestEntry> bulkReturnEntry = new ArrayList<>();
		ReturnUpdateRequestEntry returnUpdate= new ReturnUpdateRequestEntry();
		returnUpdate.setToState(toState);
		returnUpdate.setReturnId(returnID);
		returnUpdate.setTrackingNumber(trackingNumber);
		returnUpdate.setDcCode(dcCode);
		bulkReturnEntry.add(returnUpdate);
		returnUpdate.setUserComment("Rejected");
		returnUpdate.setCancelReasonId(1L);
		returnUpdate.setQcDone(quality);
		returnUpdate.setWarehouseId(warehouseId);
		returnUpdate.setCourierService("TB");
		returnUpdate.setCreatedBy("rms_s");
		
		String payLoad = APIUtilities.getObjectToJSON(bulkReturnEntry);
		Svc service = HttpExecutorService.executeHttpService(Constants.RMS_PATH.RETURN_BULK_STATUS_PROCESS, null, SERVICE_TYPE.RMS_SVC.toString(), HTTPMethods.POST, payLoad, getRMSHeader());
		ReturnResponse returnResponse =(ReturnResponse) APIUtilities.getJsontoObject(service.getResponseBody(), new ReturnResponse());
		return returnResponse;
	}
	
	/**
	 * Change Bulk Status of Return for a given Return ID with Warehouse and Quality
	 * @param returnID,toState,trackingNumber,dcCode,warehouseId,quality
	 * @return {@link ReturnResponse}
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public ReturnResponse bulk_statuschangePurePlay(String returnID, ReturnStatus toState,String trackingNumber,String dcCode,Integer warehouseId,String couriercode,Boolean quality) throws JsonParseException, JsonMappingException, IOException {
		
		List<ReturnUpdateRequestEntry> bulkReturnEntry = new ArrayList<>();
		ReturnUpdateRequestEntry returnUpdate= new ReturnUpdateRequestEntry();
		returnUpdate.setToState(toState);
		returnUpdate.setReturnId(Long.parseLong(returnID));
		returnUpdate.setTrackingNumber(trackingNumber);
		returnUpdate.setDcCode(dcCode);
		bulkReturnEntry.add(returnUpdate);
		returnUpdate.setUserComment("Rejected");
		returnUpdate.setCancelReasonId(1L);
		returnUpdate.setQcDone(quality);
		returnUpdate.setWarehouseId(warehouseId);
		returnUpdate.setCourierService(couriercode);
		returnUpdate.setCreatedBy("rms_s");
		
		String payLoad = APIUtilities.getObjectToJSON(bulkReturnEntry);
		Svc service = HttpExecutorService.executeHttpService(Constants.RMS_PATH.RETURN_BULK_STATUS_PROCESS, null, SERVICE_TYPE.RMS_SVC.toString(), HTTPMethods.POST, payLoad, getRMSHeader());
		ReturnResponse returnResponse =(ReturnResponse) APIUtilities.getJsontoObject(service.getResponseBody(), new ReturnResponse());
		return returnResponse;
	}
	
	/**
	 * Change Bulk Status of Return for a given Return ID with ReshipCourier and ReshipTrackingNumber
	 * @param returnID,toState,trackingNumber,dcCode,reshipCourier,ReshipTrackingNumber
	 * @return {@link ReturnResponse}
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public ReturnResponse bulk_statuschange(String returnID, ReturnStatus toState,String trackingNumber,String dcCode,String reshipCourier,String reshipTrackingNumber) throws JsonParseException, JsonMappingException, IOException {
		
		List<ReturnUpdateRequestEntry> bulkReturnEntry = new ArrayList<>();
		ReturnUpdateRequestEntry returnUpdate= new ReturnUpdateRequestEntry();
		returnUpdate.setToState(toState);
		returnUpdate.setReturnId(Long.parseLong(returnID));
		returnUpdate.setTrackingNumber(trackingNumber);
		returnUpdate.setDcCode(dcCode);
		returnUpdate.setReshipCourier(reshipCourier);
		returnUpdate.setReshipTrackingNumber(reshipTrackingNumber);
		bulkReturnEntry.add(returnUpdate);
		
		String payLoad = APIUtilities.getObjectToJSON(bulkReturnEntry);
		Svc service = HttpExecutorService.executeHttpService(Constants.RMS_PATH.RETURN_BULK_STATUS_PROCESS, null, SERVICE_TYPE.RMS_SVC.toString(), HTTPMethods.POST, payLoad, getRMSHeader());
		ReturnResponse returnResponse =(ReturnResponse) APIUtilities.getJsontoObject(service.getResponseBody(), new ReturnResponse());
		return returnResponse;
	}
	
	/**
	 * Change Bulk Status of Return for a given Return ID with ReshipCourier and ReshipTrackingNumber
	 * @param returnID,toState,trackingNumber,dcCode,reshipCourier,ReshipTrackingNumber
	 * @return {@link ReturnResponse}
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public ReturnResponse bulk_statuschange(String returnID,String userComment,String trackingNumber,long cancelReasonId,ReturnStatus toState,String courierService,String createdBy) throws JsonParseException, JsonMappingException, IOException {
		
		List<ReturnUpdateRequestEntry> bulkReturnEntry = new ArrayList<>();
		ReturnUpdateRequestEntry returnUpdate= new ReturnUpdateRequestEntry();
		
		returnUpdate.setReturnId(Long.parseLong(returnID));
		returnUpdate.setUserComment(userComment);
		returnUpdate.setTrackingNumber(trackingNumber);
		returnUpdate.setCancelReasonId(cancelReasonId);
		returnUpdate.setToState(toState);
		returnUpdate.setCourierService(courierService);
		returnUpdate.setCreatedBy(createdBy);
		bulkReturnEntry.add(returnUpdate);
		
		String payLoad = APIUtilities.getObjectToJSON(bulkReturnEntry);
		Svc service = HttpExecutorService.executeHttpService(Constants.RMS_PATH.RETURN_BULK_STATUS_PROCESS, null, SERVICE_TYPE.RMS_SVC.toString(), HTTPMethods.POST, payLoad, getRMSHeader());
		ReturnResponse returnResponse =(ReturnResponse) APIUtilities.getJsontoObject(service.getResponseBody(), new ReturnResponse());
		return returnResponse;
	}
	
	/**
	 * Issue Bulk Return Refund for a given Return IDs
	 * @param returnID
	 * @return {@link ReturnResponse}
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public ReturnResponse bulk_issueRefund(Long returnID) throws JsonParseException, JsonMappingException, IOException {
		
		List<ReturnUpdateRequestEntry> bulkReturnEntry = new ArrayList<>();
		ReturnUpdateRequestEntry returnUpdate= new ReturnUpdateRequestEntry();
		returnUpdate.setReturnId(returnID);
		bulkReturnEntry.add(returnUpdate);
		
		String payLoad = APIUtilities.getObjectToJSON(bulkReturnEntry);
		Svc service = HttpExecutorService.executeHttpService(Constants.RMS_PATH.RETURN_BULK_ISSUE_REFUNDS, null, SERVICE_TYPE.RMS_SVC.toString(), HTTPMethods.POST, payLoad, getRMSHeader());
		ReturnResponse returnResponse =(ReturnResponse) APIUtilities.getJsontoObject(service.getResponseBody(), new ReturnResponse());
		return returnResponse;
	}
	
	/**
	 * Issue Bulk Return Refund for a given Return IDs with Courier Details
	 * @param returnID,trackingNumber,courierService,warehouseId,createdBy
	 * @return {@link ReturnResponse}
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public ReturnResponse bulk_issueRefund(String returnID,String trackingNumber,String courierService,Integer warehouseId,String createdBy) throws JsonParseException, JsonMappingException, IOException {
		
		List<ReturnUpdateRequestEntry> bulkReturnEntry = new ArrayList<>();
		ReturnUpdateRequestEntry returnUpdate= new ReturnUpdateRequestEntry();
		returnUpdate.setReturnId(Long.parseLong(returnID));
		returnUpdate.setTrackingNumber(trackingNumber);
		returnUpdate.setCourierService(courierService);
		returnUpdate.setWarehouseId(warehouseId);
		returnUpdate.setCreatedBy(createdBy);
		
		bulkReturnEntry.add(returnUpdate);
		
		String payLoad = APIUtilities.getObjectToJSON(bulkReturnEntry);
		Svc service = HttpExecutorService.executeHttpService(Constants.RMS_PATH.RETURN_BULK_ISSUE_REFUNDS, null, SERVICE_TYPE.RMS_SVC.toString(), HTTPMethods.POST, payLoad, getRMSHeader());
		ReturnResponse returnResponse =(ReturnResponse) APIUtilities.getJsontoObject(service.getResponseBody(), new ReturnResponse());
		return returnResponse;
	}
	
	/**
	 * Mark Return Picked up for a given Return ID
	 * @param returnID,fromState
	 * @return {@link ReturnResponse}
	 * @throws IOException
	 */
	public ReturnResponse returnPickupProcessNew(String returnID, ReturnStatus fromState) throws IOException {
		
		ReturnUpdateRequestEntry returnUpdate= new ReturnUpdateRequestEntry();
		returnUpdate.setFromState(fromState);
		returnUpdate.setPickupStatus(PickupStatus.SUCCESS);
		returnUpdate.setReturnId(Long.parseLong(returnID));
		
		
		String payLoad = APIUtilities.getObjectToJSON(returnUpdate);
		Svc service = HttpExecutorService.executeHttpService(Constants.RMS_PATH.RETURN_PICKUP_PROCESS, null, SERVICE_TYPE.RMS_SVC.toString(), HTTPMethods.POST, payLoad, getRMSHeader());
		ReturnResponse returnResponse =(ReturnResponse) APIUtilities.getJsontoObject(service.getResponseBody(), new ReturnResponse());
		return returnResponse;
	}
	
	/**
	 * Change Return Line level status for a given Return ID
	 * @param returnID,fromState,toState,orderLineId,returnLineId,itemBarcode,trackingNo,createdBy,warehouseId,quality,QcReason,QcDescription
	 * @return {@link ReturnLineResponse}
	 * @throws IOException
	 */
	public ReturnLineResponse returnLineStatusProcessNew(String returnID, ReturnLineStatus fromState, ReturnLineStatus toState,String orderLineId,String returnLineId,String itemBarcode,String trackingNo,String createdBy,int warehouseId,String quality,String QcReason,String qcDescription) throws IOException {
		ReturnLineUpdateRequestEntry returnUpdate2= new ReturnLineUpdateRequestEntry();
		returnUpdate2.setFromState(fromState);
		returnUpdate2.setToState(toState);
		returnUpdate2.setReturnId(Long.parseLong(returnID));
		//returnUpdate2.setCourierService("");
		returnUpdate2.setTrackingNumber(trackingNo);
		returnUpdate2.setReturnLineId(Long.parseLong(returnID));
		returnUpdate2.setOrderLineId(Long.parseLong(orderLineId));
		//returnUpdate2.setId(Long.parseLong(returnLineId));
		returnUpdate2.setCreatedBy(createdBy);
		returnUpdate2.setWarehouseId(warehouseId);
		returnUpdate2.setItembarcode(itemBarcode);
		returnUpdate2.setQuality(quality);
		returnUpdate2.setQcReason(QcReason);
		returnUpdate2.setQcDescription(qcDescription);
		//returnUpdate2.setDcCode("");
		
		String payLoad = APIUtilities.getObjectToJSON(returnUpdate2);
		Svc service = HttpExecutorService.executeHttpService(Constants.RMS_PATH.RETURNLINE_STATUS_PROCESS, null, SERVICE_TYPE.RMS_SVC.toString(), HTTPMethods.POST, payLoad, getRMSHeader());
		ReturnLineResponse returnResponse =(ReturnLineResponse) APIUtilities.getJsontoObject(service.getResponseBody(), new ReturnLineResponse());
		return returnResponse;
	}
	
	/**
	 * Change Return Line level status for a given Return ID E2E
	 * @param returnID,fromState,toState,orderLineId,returnLineId,itemBarcode,trackingNo,createdBy,warehouseId,quality,QcReason,QcDescription
	 * @return {@link ReturnLineResponse}
	 * @throws IOException
	 */
	public ReturnLineResponse returnLineStatusProcessNewE2E(String returnID, ReturnLineStatus fromState, ReturnLineStatus toState,Long orderLineId,Long returnLineId,String itemBarcode,String trackingNo,String createdBy,int warehouseId,String quality,String QcReason,String qcDescription) throws IOException {
		ReturnLineUpdateRequestEntry returnUpdate2= new ReturnLineUpdateRequestEntry();
		returnUpdate2.setFromState(fromState);
		returnUpdate2.setToState(toState);
		returnUpdate2.setReturnId(Long.parseLong(returnID));
		returnUpdate2.setCourierService("ML");
		returnUpdate2.setTrackingNumber(trackingNo);
		returnUpdate2.setReturnLineId(returnLineId);
		returnUpdate2.setOrderLineId(orderLineId);
		//returnUpdate2.setId(returnLineId);
		returnUpdate2.setCreatedBy(createdBy);
		returnUpdate2.setWarehouseId(warehouseId);
		returnUpdate2.setItembarcode(itemBarcode);
		returnUpdate2.setQuality(quality);
		returnUpdate2.setQcReason(QcReason);
		returnUpdate2.setQcDescription(qcDescription);
		returnUpdate2.setDcCode("MYQ");
		
		String payLoad = APIUtilities.getObjectToJSON(returnUpdate2);
		Svc service = HttpExecutorService.executeHttpService(Constants.RMS_PATH.RETURNLINE_STATUS_PROCESS, null, SERVICE_TYPE.RMS_SVC.toString(), HTTPMethods.POST, payLoad, getRMSHeader());
		ReturnLineResponse returnResponse =(ReturnLineResponse) APIUtilities.getJsontoObject(service.getResponseBody(), new ReturnLineResponse());
		return returnResponse;
	}
	
	/**
	 * Change Return Line level status for a given Return ID
	 * @param returnID,fromState,toState,orderLineId,returnLineId,itemBarcode,courier,createdBy,trackingNo,createdBy,warehouseId,quality,QcReason,QcDescription,dcCode
	 * @return {@link ReturnLineResponse}
	 * @throws IOException
	 */
	public ReturnLineResponse returnLineStatusProcessNewDC(String returnID, ReturnLineStatus fromState, ReturnLineStatus toState,Long orderLineId,Long returnLineId,String itemBarcode,String courier,String trackingNo,String createdBy,int warehouseId,String quality,String QcReason,String qcDescription,String dcCode) throws IOException {
		ReturnLineUpdateRequestEntry returnUpdate2= new ReturnLineUpdateRequestEntry();
		returnUpdate2.setFromState(fromState);
		returnUpdate2.setToState(toState);
		returnUpdate2.setReturnId(Long.parseLong(returnID));
		returnUpdate2.setCourierService(courier);
		returnUpdate2.setTrackingNumber(trackingNo);
		returnUpdate2.setReturnLineId(Long.parseLong(returnID));
		returnUpdate2.setOrderLineId(orderLineId);
		//returnUpdate2.setId(returnLineId);
		returnUpdate2.setCreatedBy(createdBy);
		returnUpdate2.setWarehouseId(warehouseId);
		returnUpdate2.setItembarcode(itemBarcode);
		returnUpdate2.setQuality(quality);
		returnUpdate2.setQcReason(QcReason);
		returnUpdate2.setQcDescription(qcDescription);
		returnUpdate2.setDcCode(dcCode);
		
		String payLoad = APIUtilities.getObjectToJSON(returnUpdate2);
		Svc service = HttpExecutorService.executeHttpService(Constants.RMS_PATH.RETURNLINE_STATUS_PROCESS, null, SERVICE_TYPE.RMS_SVC.toString(), HTTPMethods.POST, payLoad, getRMSHeader());
		ReturnLineResponse returnResponse =(ReturnLineResponse) APIUtilities.getJsontoObject(service.getResponseBody(), new ReturnLineResponse());
		return returnResponse;
	}
	
	/**
	 * Change Return Line level status for a given Return ID
	 * @return {@link ReturnLineResponse}
	 * @throws IOException
	 **/
	public ReturnLineResponse returnLineStatusProcessNewDC(ReturnLineStatus fromState, ReturnLineStatus toState,Long returnId,Long returnLineId,String QcReason,String itemBarcode,String courier,String trackingNo,String createdBy,String dcCode, long dcId, String locationType) throws IOException {
		ReturnLineUpdateRequestEntry returnUpdate2= new ReturnLineUpdateRequestEntry();
		returnUpdate2.setFromState(fromState);
		returnUpdate2.setToState(toState);
		returnUpdate2.setReturnId(returnId);
		returnUpdate2.setReturnLineId(returnLineId);
		returnUpdate2.setQcReason(QcReason);
		returnUpdate2.setItembarcode(itemBarcode);
		returnUpdate2.setCourierService(courier);
		returnUpdate2.setTrackingNumber(trackingNo);
		returnUpdate2.setCreatedBy(createdBy);
		returnUpdate2.setDcCode(dcCode);
		returnUpdate2.setDcId(dcId);
		returnUpdate2.setLocationType(locationType);
		
		String payLoad = APIUtilities.getObjectToJSON(returnUpdate2);
		Svc service = HttpExecutorService.executeHttpService(Constants.RMS_PATH.RETURNLINE_STATUS_PROCESS, null, SERVICE_TYPE.RMS_SVC.toString(), HTTPMethods.POST, payLoad, getRMSHeader());
		ReturnLineResponse returnResponse =(ReturnLineResponse) APIUtilities.getJsontoObject(service.getResponseBody(), new ReturnLineResponse());
		return returnResponse;
	}
	
	/**
	 * returnLineStatusProcessNewWarehouse
	 * @param fromState
	 * @param toState
	 * @param returnId
	 * @param returnLineId
	 * @param QcReason
	 * @param itemBarcode
	 * @param courier
	 * @param trackingNo
	 * @param createdBy
	 * @param whId
	 * @return
	 * @throws IOException
	 */
	public ReturnLineResponse returnLineStatusProcessNewWarehouse(ReturnLineStatus fromState, ReturnLineStatus toState,Long returnId,Long returnLineId,String QcReason,String itemBarcode,String courier,String trackingNo,String createdBy, int whId) throws IOException {
		ReturnLineUpdateRequestEntry returnUpdate2= new ReturnLineUpdateRequestEntry();
		returnUpdate2.setFromState(fromState);
		returnUpdate2.setToState(toState);
		returnUpdate2.setReturnId(returnId);
		returnUpdate2.setReturnLineId(returnLineId);
		returnUpdate2.setQcReason(QcReason);
		returnUpdate2.setItembarcode(itemBarcode);
		returnUpdate2.setCourierService(courier);
		returnUpdate2.setTrackingNumber(trackingNo);
		returnUpdate2.setCreatedBy(createdBy);
		returnUpdate2.setWarehouseId(whId);
		returnUpdate2.setLocationType("WH");
		
		String payLoad = APIUtilities.getObjectToJSON(returnUpdate2);
		Svc service = HttpExecutorService.executeHttpService(Constants.RMS_PATH.RETURNLINE_STATUS_PROCESS, null, SERVICE_TYPE.RMS_SVC.toString(), HTTPMethods.POST, payLoad, getRMSHeader());
		ReturnLineResponse returnResponse =(ReturnLineResponse) APIUtilities.getJsontoObject(service.getResponseBody(), new ReturnLineResponse());
		return returnResponse;
	}
	
	/**
	 * Change Return Line level status for a given Return ID
	 * @param ,fromState,toState,orderLineId,returnLineId,itemBarcode,courier,createdBy,trackingNo,createdBy,warehouseId,quality,QcReason,QcDescription,dcCode
	 * @return {@link ReturnLineResponse}
	 * @throws IOException
	 **/
	public ReturnLineResponse returnLineStatusProcessNewWH(ReturnLineStatus fromState, ReturnLineStatus toState,Long returnId,Long returnLineId,String QcReason,String itemBarcode,String courier,String trackingNo,String createdBy, Integer whId, String locationType) throws IOException {
		ReturnLineUpdateRequestEntry returnUpdate2= new ReturnLineUpdateRequestEntry();
		returnUpdate2.setFromState(fromState);
		returnUpdate2.setToState(toState);
		returnUpdate2.setReturnId(returnId);
		returnUpdate2.setReturnLineId(returnLineId);
		returnUpdate2.setQcReason(QcReason);
		returnUpdate2.setItembarcode(itemBarcode);
		returnUpdate2.setCourierService(courier);
		returnUpdate2.setTrackingNumber(trackingNo);
		returnUpdate2.setCreatedBy(createdBy);
		returnUpdate2.setWarehouseId(whId);
		returnUpdate2.setLocationType(locationType);
		
		String payLoad = APIUtilities.getObjectToJSON(returnUpdate2);
		Svc service = HttpExecutorService.executeHttpService(Constants.RMS_PATH.RETURNLINE_STATUS_PROCESS, null, SERVICE_TYPE.RMS_SVC.toString(), HTTPMethods.POST, payLoad, getRMSHeader());
		ReturnLineResponse returnResponse =(ReturnLineResponse) APIUtilities.getJsontoObject(service.getResponseBody(), new ReturnLineResponse());
		return returnResponse;
	}
	/**
	 * Get Return Details for Return ID
	 *
	 * @param returnID
	 * @return {@link ReturnResponse}
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 */
	public ReturnResponse getReturnDetailsNew(String returnID) throws IOException {
		Svc service = HttpExecutorService.executeHttpService(Constants.RMS_PATH.GET_RETURN_DETAILS, new String[] { returnID }, SERVICE_TYPE.RMS_SVC.toString(), HTTPMethods.GET, null, getRMSHeader());
		ReturnResponse returnResponse =(ReturnResponse) APIUtilities.getJsontoObject(service.getResponseBody(), new ReturnResponse());
		return returnResponse;
	}
	
	/**
	 * Received returned Item in WareHouse
	 *
	 * @param returnID
	 * @param wareHouseID
	 */
	public void receiveAtWareHouse(String returnID, String wareHouseID) throws IOException {
		ReturnResponse returnResponse = getReturnDetailsNew(returnID);
		Long orderID = returnResponse.getData().get(0).getOrderId();
		System.out.println("OrderID" + orderID);
		HashMap<String, Object> getOrderLineId = (HashMap<String, Object>) DBUtilities.exSelectQueryForSingleRecord("select id from order_line where order_id_fk = "+orderID, "oms");
		String orderLineId = getOrderLineId.get("id").toString();
		
		String trackingNumber = returnResponse.getData().get(0).getReturnTrackingDetailsEntry().getTrackingNo();
		System.out.println("Tracking No :-" + trackingNumber);
		if(trackingNumber == null){
			DBUtilities.exUpdateQuery("update returns set `tracking_no`= 'ML"+System.currentTimeMillis() +"' where id = "+returnID, "rms");
		}
		String courierCode = returnResponse.getData().get(0).getReturnTrackingDetailsEntry().getCourierCode();
		System.out.println("Courier Code :-" + courierCode);
		
		List<ReturnLineEntry> lines = returnResponse.getData().get(0).getReturnLineEntries();
		System.out.println("Quantity present in the Return Request: "
				+ lines.size());
		
		for (ReturnLineEntry lineEntry:lines) {
			String returnReasonID = ""+lineEntry.getReturnReasonId();
			System.out.println("Return Reason ID :-" + returnReasonID);
			String itemBarcode = lineEntry.getItemBarcode();
			System.out.println("Item BarCode :-" + itemBarcode);
			Long lineID = lineEntry.getId();
			ReturnLineResponse returnLineResponse = returnLineStatusProcessNewE2E(returnID, ReturnLineStatus.RPU, ReturnLineStatus.RRC, Long.parseLong(orderLineId), lineID, itemBarcode, trackingNumber, "erpadmin", Integer.parseInt(wareHouseID), "Q1", null, null);
			
			if(!(returnLineResponse.getStatus().getStatusType() == StatusResponse.Type.SUCCESS)){
				SlackMessenger.send("scm_e2e_order_sanity", "`Return Quality Check Failed, Reason : " + returnLineResponse.getStatus().getStatusMessage(), 4);
				Assert.fail("Return Quality Check Failed");
			}
			SlackMessenger.send("scm_e2e_order_sanity", "Return Quality Check Passed");
		}
	}
	
	/**
	 * Mark QA Pass for Return
	 *
	 * @param returnID
	 * @param quality
	 */
	public void markQAPassForReturn(String returnID, String quality) throws IOException {
		ReturnResponse getReturnDetails = getReturnDetailsNew(returnID);
		
		Long orderID = getReturnDetails.getData().get(0).getOrderId();
		List<ReturnLineEntry> lines = getReturnDetails.getData().get(0).getReturnLineEntries();
		
		for (ReturnLineEntry lineEntry:lines) {
			Long orderReleseID = lineEntry.getOrderReleaseId();
			String lineID = ""+lineEntry.getId();
			String itemBarcode = lineEntry.getItemBarcode();
			
			MyntraService getreturnstatechange = Myntra.getService(
					ServiceType.ERP_RMS, APINAME.RETURNQUALITYCHECK,
					init.Configurations, new String[] { lineID, ""+returnID,
							""+orderReleseID, itemBarcode, quality },
					PayloadType.JSON, PayloadType.JSON);
			
			System.out.println("\nPrinting RMSService state change API URL : "
					+ getreturnstatechange.URL);
			log.info("\nPrinting RMSService state change API URL : "
					+ getreturnstatechange.URL);
			
			System.out
					.println("\nPrinting RMSService state change API Payload : "
							+ getreturnstatechange.Payload);
			
			log.info("\nPrinting RMSService state change API Payload : "
					+ getreturnstatechange.Payload);
			
			RequestGenerator requestGenerator = new RequestGenerator(
					getreturnstatechange, getRMSHeader());
			String getReturnQualityCheckResponse = requestGenerator
					.returnresponseasstring();
			System.out.println("Response of Return Quality Check for Item "
					+ requestGenerator.returnresponseasstring());
			
			if (APIUtilities.getElement(getReturnQualityCheckResponse,
					"status.statusType", "json").equalsIgnoreCase("ERROR")) {
				System.out.println("Quality Pass Response for Line ID : "
						+ lineID + "  Response :- "
						+ getReturnQualityCheckResponse);
				log.error("Quality Pass Response for Line ID : " + lineID
						+ "  Response :- " + getReturnQualityCheckResponse);
				Assert.fail("Quality Pass Response for Line ID : " + lineID
						+ "  Response :- " + getReturnQualityCheckResponse);
			}
			
		}
		
	}
	
	/**
	 * Get RMS Headers to Make API Call
	 *
	 * @return HashMap
	 */
	private static HashMap<String, String> getRMSHeader() {
		HashMap<String, String> createOrderHeaders = new HashMap<String, String>();
		createOrderHeaders.put("Authorization",
				"Basic bW9iaWxlfm1vYmlsZTptb2JpbGU");
		createOrderHeaders.put("Content-Type", "Application/json");
		createOrderHeaders.put("Accept", "Application/json");
		return createOrderHeaders;
	}
	
	public static void main(String[] args) {
		String getReturnDetailsResponse = "{\"status\":{\"statusCode\":9552,\"statusMessage\":\"Return Release retrieved successfully\",\"statusType\":\"SUCCESS\",\"totalCount\":1},\"data\":[{\"id\":10001165,\"createdBy\":\"RMS-SYSTEM\",\"createdOn\":1450275556000,\"lastModifiedOn\":1450359486000,\"version\":0,\"orderId\":70036971,\"statusCode\":\"RPU\",\"returnMode\":\"PICKUP\",\"comment\":\"Return created from MyMyntra\",\"login\":\"androidorder6@myntra.com\",\"mobile\":\"9916205202\",\"email\":\"androidorder6@myntra.com\",\"customerName\":\"Siva subramanian\",\"returnLineEntries\":[{\"id\":996187397,\"createdBy\":\"RMS-SYSTEM\",\"createdOn\":1450275556000,\"lastModifiedOn\":1450359486000,\"version\":0,\"returnId\":10001165,\"orderId\":70036971,\"orderReleaseId\":1122332125,\"orderLineId\":52692252,\"skuId\":3828,\"optionId\":4532,\"returnReasonId\":13,\"quantity\":1,\"statusCode\":\"RPU\",\"itemBarcode\":\"6000011\",\"warehouseId\":28,\"supplyType\":\"ON_HAND\",\"groupKey\":\"0\",\"styleId\":1530,\"skuCode\":\"PUMAJKWU00207\",\"unitPrice\":\"2.00\",\"sellerId\":1,\"returnLineRefundDetailsEntry\":{\"refundAmount\":\"2.00\",\"cashRedeemed\":\"0.00\",\"refunded\":false},\"returnLineTrackingDetailsEntry\":{},\"returnLineAdditionalDetailsEntry\":{\"version\":0}}],\"returnAddressDetailsEntry\":{\"addressId\":6128473,\"address\":\"Flat no &#x3a; 306 ,Roopen Comforts, roopena Agrahara&#xa;3rd Main Street, Madiwala Post,Bangalore\",\"city\":\"Bangalore\",\"state\":\"KA\",\"country\":\"India\",\"zipcode\":\"560068\"},\"returnTrackingDetailsEntry\":{\"courierCode\":\"EK\",\"trackingNo\":\"MYNP6555922036\",\"pickupInitOn\":1450275556000,\"pickupOn\":1450359486000},\"returnRefundDetailsEntry\":{\"deliveryCredit\":\"0.00\",\"pickupCharges\":\"0.00\",\"refundAmount\":\"2.00\",\"refunded\":false,\"refundMode\":\"CASHBACK\",\"refundAccountId\":\"\"},\"returnAdditionalDetailsEntry\":{\"version\":0}}]}";
		String orderID = APIUtilities.getElement(getReturnDetailsResponse,
				"data.orderId", "json");
		String lineID = APIUtilities.getElement(getReturnDetailsResponse,
				"data.returnLineEntries[0].id", "json");
		List<String> issueIds = JsonPath.read(getReturnDetailsResponse,
				"$..returnLineEntries[*].id");
		String orderReleseID = APIUtilities.getElement(
				getReturnDetailsResponse,
				"data.returnLineEntries[0].orderReleaseId[0]", "json");
		String returnReasonID = APIUtilities.getElement(
				getReturnDetailsResponse,
				"data.returnLineEntries[0].returnReasonId[0]", "json");
		String trackingNumber = APIUtilities.getElement(
				getReturnDetailsResponse,
				"data.returnTrackingDetailsEntry.trackingNo", "json");
		String courierCode = APIUtilities.getElement(getReturnDetailsResponse,
				"data.returnTrackingDetailsEntry.courierCode", "json");
		System.out.println("Line ID" + lineID);
	}
	
	public RequestGenerator invokeReceiveShipment(String OrderReleaseId,
	                                              String itemid) {
		
		MyntraService orderitemAsociation = Myntra.getService(
				ServiceType.ERP_RMS, APINAME.RMSSHIPMENTRECEIVED,
				
				init.Configurations,
				
				new String[] { OrderReleaseId, itemid }, PayloadType.JSON,
				PayloadType.JSON);
		
		System.out.println("\nPrinting  rms shippment received API URL : "
				+ orderitemAsociation.URL);
		
		log.info("\nPrinting  rms shippment received   API URL : "
				+ orderitemAsociation.URL);
		
		System.out.println("\nPrinting rms shippment received  API Payload : "
				+ orderitemAsociation.Payload);
		
		log.info("\nPrinting rms shippment received API Payload : "
				+ orderitemAsociation.Payload);
		
		HashMap<String, String> getOrderHeaders = new HashMap<String, String>();
		
		getOrderHeaders.put("Authorization",
				
				"Basic bW9iaWxlfm1vYmlsZTptb2JpbGU");
		
		getOrderHeaders.put("Content-Type", "Application/xml");
		
		return new RequestGenerator(orderitemAsociation, getOrderHeaders);
		
	}
	
	// returnId, eventType, source, timestamp, status,returnId,orderItemId,
	// source, status, action, quantity, orderDate,
	// createdDate,updatedDate,courierName, reason, subReason, trackingId,
	// shipmentId, comments, completionDate, replacementOrderItemId, productId,
	// listingId
	public Svc invokeSilkRouteServiceCreateReturnAPI(String fkEnv,String returnId, String eventType,
	                                                 String source, String status, String orderItemId, String sourceline, String statusline,
	                                                 String action, String quantity,String tracking)
			throws ParseException, UnsupportedEncodingException, JAXBException {
		FlipkartReturnEnteries flipkartReturnEntries=new FlipkartReturnEnteries(returnId, eventType, source, status, orderItemId, sourceline, statusline, action, quantity, tracking);
		String payloadJson=flipkartReturnEntries.getPayload();
		Svc service = HttpExecutorService.executeHttpService(Constants.SILKROUTE_PATH.PROCESS_RETURN, new String[] {fkEnv, "v0/returns/process/"},SERVICE_TYPE.SILKR_SVC.toString(), HTTPMethods.POST, payloadJson, (HashMap<String, String>)silkRouteServiceHelper.getHeaaderForFKOauthReturn(fkEnv));
		return service;
	}
	public Svc invokeSilkRouteServiceCompleteReturnAPI(String fkEnv, String returnId, String eventType,
	                                                   String source, String status, String orderItemId) throws ParseException, UnsupportedEncodingException {
		FlipkartReturnEnteries flipkartReturnEntries=new FlipkartReturnEnteries(returnId, eventType, source, status, orderItemId);
		String payloadJson=flipkartReturnEntries.getPayload();
		Svc service = HttpExecutorService.executeHttpService(Constants.SILKROUTE_PATH.PROCESS_RETURN, new String[] {fkEnv, "v0/returns/process/"},SERVICE_TYPE.SILKR_SVC.toString(), HTTPMethods.POST, payloadJson, (HashMap<String, String>)silkRouteServiceHelper.getHeaaderForFKOauthReturn(fkEnv));
		return service;
	}
	
	
	
	// get oms RTO enrty
	public static HashMap<String, Object> getRTOEntry(
			String store_order_release_id) {
		System.out.println("Entered the OMS query execution entry for RTO");
		End2EndHelper.sleep(10000L);
		HashMap<String, Object> hm = null;
		String query = "select * from order_release where store_release_id="
				+ store_order_release_id;
		hm = (HashMap<String, Object>) DBUtilities.exSelectQueryForSingleRecord(query, "myntra_oms");
		return hm;
	}
	
	// rms return entry
	public static HashMap<String, Object> getRmsReturnEntry(
			String store_returnid) {
		System.out.println("Entered the RMS query execution entry for Return");
		
		End2EndHelper.sleep(10000L);
		
		HashMap<String, Object> hm = null;
		String query = "select * from returns where store_return_id="
				+ store_returnid;
		hm = (HashMap<String, Object>) DBUtilities.exSelectQueryForSingleRecord(query, "myntra_rms");
		
		return hm;
	}
	
	// rms return line entry
	public static HashMap<String, Object> getRmsReturnLineEntry(
			String store_returnid) {
		System.out
				.println("Entered the RMS query execution entry for Return_line");
		HashMap<String, Object> hmline=null;
		End2EndHelper.sleep(10000L);
		String query = "select * from return_line where return_id="
				+ store_returnid;
		
		hmline = (HashMap<String, Object>) DBUtilities.exSelectQueryForSingleRecord(query, "myntra_rms");
		
		return hmline;
	}
	
	// silkroute return entry
	public static HashMap<String, Object> getSilkrouteReturnEntry(
			String store_returnid) {
		log.info("Entered the Silkroute query execution entry for Return");
		
		End2EndHelper.sleep(10000L);
		
		HashMap<String, Object> hm = null;
		String query = "select * from returns where store_return_id="
				+ store_returnid;
		hm = (HashMap<String, Object>) DBUtilities.exSelectQueryForSingleRecord(query, "myntra_silkroute");
		
		return hm;
	}
	
	// silkroute rto entry
	public static HashMap<String, Object> getSilkrouteRTOEntry(
			String store_returnid) {
		System.out.println("Entered the RMS query execution entry for RTO");
		End2EndHelper.sleep(10000L);
		HashMap<String, Object> hm = null;
		String query = "select * from rto where store_return_id="
				+ store_returnid;
		hm = (HashMap<String, Object>) DBUtilities.exSelectQueryForSingleRecord(query, "myntra_silkroute");
		return hm;
	}
	
	// silkroute return_line entry
	public static HashMap<String, Object> getSilkrouteReturnLineEntry(
			String store_returnid) {
		log.info("Entered the Silkroute query execution entry for Return_line");
		End2EndHelper.sleep(10000L);
		HashMap<String, Object> hm = null;
		String query = "select * from return_lines where store_return_line_id="
				+ store_returnid;
		hm = (HashMap<String, Object>) DBUtilities.exSelectQueryForSingleRecord(query, "myntra_silkroute");
		
		return hm;
	}
	
	//Data Insert in OMS
	
	public void InsertOrder(String orderId,String payment_method, String mrp_total,
	                        String final_amount, String shippingCharges, String taxAmount,
	                        String discount, String cartDiscount, String couponDiscount,
	                        String giftCharges, String loyaltyPoint, String store_id,String cash_redeemed) throws SQLException {
		String orders = "INSERT INTO `orders` (`id`, `invoice_id`, `login`, `user_contact_no`, `customer_name`, `payment_method`, `status_code`, `coupon_code`, "
				+ "`cash_coupon_code`, `mrp_total`, `discount`, `cart_discount`, `coupon_discount`, `cash_redeemed`, `pg_discount`, `final_amount`, `shipping_charge`, "
				+ "`cod_charge`, `emi_charge`, `gift_charge`, `tax_amount`, `cashback_offered`, `request_server`, `response_server`, `is_on_hold`, `is_gift`, `notes`, "
				+ "`billing_address_id_fk`, `cancellation_reason_id_fk`, `on_hold_reason_id_fk`,`cancelled_on`, `created_by`,"
				+ " `version`, `order_type`, `loyalty_points_used`, `store_id`, `store_order_id`) VALUES("+ orderId+ ", NULL, 'pawell.soni@myntra.com', '9823888800', "
				+ "'RMS Automation', '"+payment_method+"', NULL, '', '', "+ mrp_total+ ","+ discount+ ", "+ cartDiscount+ ", "+ couponDiscount+ ", "+cash_redeemed+", 0.0,"+ final_amount+ " , "+ shippingCharges
				+ ", 0.00, 0.00, "+ giftCharges+ ", "+ taxAmount+ ", 0.00, NULL, NULL, 0, 0, '', 1036, NULL, NULL, NULL, 'pawell.soni@myntra.com', 2, 'on', "
				+ loyaltyPoint + ", "+store_id+", NULL)";
		DBUtilities.exUpdateQuery(orders, "myntra_oms");
	}
	
	public void InsertOrderRelease(String OrderReleaseId, String orderId,String payment_method,
	                               String mrp_total, String final_amount, String shippingCharges,
	                               String taxAmount, String discount, String cartDiscount,
	                               String couponDiscount, String giftCharges, String loyaltyPoint,String courier_code,String tracking_no,String store_id,
	                               String wareHouseId, String cash_redeemed) throws SQLException {
		String orderRelease = "INSERT INTO `order_release` (`id`, `order_id_fk`, `login`, `status_code`, `payment_method`, `mrp_total`, `discount`, `cart_discount`, "
				+ "`coupon_discount`, `cash_redeemed`, `pg_discount`, `final_amount`, `shipping_charge`, `cod_charge`, `emi_charge`, `gift_charge`, `tax_amount`, "
				+ "`cashback_offered`, `receiver_name`, `address`, `city`, `locality`, `state`, `country`, `zipcode`, `mobile`, `email`, `courier_code`, `tracking_no`, "
				+ "`warehouse_id`, `is_refunded`, `cod_pay_status`, `cancellation_reason_id_fk`, `packed_on`, `shipped_on`, `delivered_on`, `completed_on`, "
				+ "`cancelled_on`, `created_by`, `version`, `is_on_hold`, `invoice_id`, `cheque_no`, `exchange_release_id`, "
				+ "`user_contact_no`, `shipping_method`, `on_hold_reason_id_fk`, `loyalty_points_used`, `store_id`, `store_release_id`) VALUES ("+ OrderReleaseId+ ","+ orderId
				+ ", "+ "'pawell.soni@myntra.com', 'DL', '"+payment_method+"', "+ mrp_total+ ", "+ discount+ ", "+ cartDiscount+ ", "+ couponDiscount+ ", "+cash_redeemed+", 0.00, "+ final_amount+ ", "+ shippingCharges
				+ ","+ " 0.00, 0.00, "+ giftCharges+ ", "+ taxAmount+ ", 0.00, 'RMS Automation ', "
				+ "'HSR Club', 'Bangalore', 'Singasandra', 'KA', 'India', '560068', '9823888800', 'pawell.soni&#x40;myntra.com',"
				+ " '"+courier_code+"', '"+tracking_no+"', "+ wareHouseId+ ", 0, 'pending', NULL, NULL, NULL, NULL, NULL, NULL, 'pawell.soni@myntra.com',"
				+ " 8, 0, NULL, NULL, NULL, '9823888800', 'NORMAL', NULL, "+ loyaltyPoint + ", "+store_id+", NULL)";
		DBUtilities.exUpdateQuery(orderRelease, "myntra_oms");
	}
	
	public void InsertOrderLine(String lineId, String orderId,
	                            String orderReleaseId, String styleId, String optionId,
	                            String skuId, String unitPrice, String quantity,
	                            String finalAmount, String taxAmount, String taxRate,
	                            String SellerId, String discount, String cartDiscount,
	                            String couponDiscount, String cancellationReasonId,
	                            String exchangeOrderLineId, String loyaltyPoint, String supplyType, String cash_redeemed)
			throws SQLException {
		String orderLine = "INSERT INTO `order_line` (`id`,`order_id_fk`, `order_release_id_fk`, `style_id`, `option_id`, `sku_id`, `status_code`, `unit_price`, `quantity`, "
				+ "`discounted_quantity`, `discount`, `cart_discount`, `cash_redeemed`, `coupon_discount`, `pg_discount`, `final_amount`, `tax_amount`, `tax_rate`, "
				+ "`cashback_offered`, `disocunt_rule_id`, `discount_rule_rev_id`, `promotion_id`, `is_discounted`, `is_returnable`, `cancellation_reason_id_fk`, `cancelled_on`, "
				+ "`created_by`, `version`, `exchange_orderline_id`, `loyalty_points_used`, `seller_id`, `supply_type`, `vendor_id`, "
				+ "`store_line_id`, `po_status`) VALUES("
				+ lineId
				+ ","
				+ orderId
				+ ","
				+ orderReleaseId
				+ ", "
				+ styleId
				+ ", "
				+ optionId
				+ ", "
				+ skuId
				+ ", 'D', "
				+ unitPrice
				+ ", "
				+ quantity
				+ ", 0, "
				+ discount
				+ ", "
				+ ""
				+ cartDiscount
				+ ", "+cash_redeemed+","
				+ couponDiscount
				+ ", 0.00,"
				+ finalAmount
				+ ","
				+ taxAmount
				+ ","
				+ taxRate
				+ ", 0.00, 0, 0, NULL, 0, 1, "
				+ cancellationReasonId
				+ ", NULL, 'pawell.soni@myntra.com', 2, "
				+ ""
				+ exchangeOrderLineId
				+ ", "
				+ loyaltyPoint
				+ ", "
				+ SellerId
				+ ", " + " '"+ supplyType +"', NULL, NULL, 'UNUSED')";
		DBUtilities.exUpdateQuery(orderLine, "myntra_oms");
	}
	
	public void InsertOrderAdditionalInfo(String orderId, String ppsId)
			throws SQLException {
		String orderAdditionalInfo = "INSERT INTO `order_additional_info` (`order_id_fk`, `key`, `value`, `created_by`, `version`) VALUES ("
				+ orderId
				+ ", "
				+ "'ORDER_PROCESSING_FLOW', 'OMS', 'pps-admin', 0), ("
				+ orderId
				+ ", 'CHANNEL', 'web', 'pps-admin',  0),("
				+ orderId
				+ ", 'LOYALTY_CONVERSION_FACTOR', '0.0', "
				+ "'pps-admin',  0),("
				+ orderId
				+ ", 'GIFT_CARD_AMOUNT', '0.0', 'pps-admin',  0),("
				+ orderId
				+ ", 'PAYMENT_PPS_ID', '"
				+ ppsId
				+ "', "
				+ "'pps-admin',  0),("
				+ orderId
				+ ", 'STORED_CREDIT_USAGE', '0.0', 'pps-admin', 0),("
				+ orderId
				+ ", 'EARNED_CREDIT_USAGE', '0.0', 'pps-admin', 0)";
		DBUtilities.exUpdateQuery(orderAdditionalInfo, "myntra_oms");
	}
	
	//Data Insert in PPS
	
	public void InsertPaymentPlan(String ppsId, String orderId,
	                              String actionType, String clientTransectionId, String sourceId,
	                              String sessionId, String amount, String returnId)
			throws SQLException {
		String paymentPlan = "INSERT INTO `payment_plan` (`id`, `comments`, `updatedBy`, `updatedTimestamp`, `actionType`, `clientTransactionId`, `crmRefId`, `login`, "
				+ "`orderId`, `ppsType`, `sourceId`, `state`, `sessionId`, `cartContext`, `totalAmount`, `mobile`, `returnId`, `userAgent`, `clientIP`) "
				+ "VALUES('"
				+ ppsId
				+ "', 'PPS Plan created', 'SYSTEM', 1452168967721, '"
				+ actionType
				+ "', "
				+ "NULL, NULL, 'pawell.soni@myntra.com', "
				+ orderId
				+ ", 'ORDER', '2a25036d-dc0c-45fa-a8df-fa4a8f00aa11--s3', "
				+ "'PPFSM Order Taking done', '"+sessionId+"', 'DEFAULT', "
				+ amount
				+ ", 9823888800, "
				+ returnId
				+ ", "
				+ "'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.106 Safari/537.36', '27.251.249.86')";
		DBUtilities.exUpdateQuery(paymentPlan, "pps");
	}
	
	public void InsertPaymentPlanItem(String ppsId, String paymentPlanItemId,
	                                  String itemType, String pricePerUnit, String qty, String sellerId,
	                                  String skuId) throws SQLException {
		String paymentPlanItem = "INSERT INTO `payment_plan_item` (`id`, `comments`, `updatedBy`, `updatedTimestamp`, `itemType`, `pricePerUnit`, `quantity`, "
				+ "`sellerId`, `skuId`, `pps_Id`) VALUES("
				+ paymentPlanItemId
				+ ", 'Payment Plan Item created', 'SYSTEM', 1452168967883, '"
				+ itemType
				+ "', "
				+ pricePerUnit
				+ ", "
				+ qty
				+ ", '"
				+ sellerId + "', '" + skuId + "', " + "'" + ppsId + "')";
		DBUtilities.exUpdateQuery(paymentPlanItem, "pps");
	}
	
	public void InsertPaymentPlanItemInstrument(String amount,
	                                            String paymentPlanItemId, String paymentInstrumentType) throws SQLException {
		String paymentPlanItemInstrument = "INSERT INTO `payment_plan_item_instrument` (`comments`, `updatedBy`, `updatedTimestamp`, `amount`, `paymentInstrumentType`, "
				+ "`ppsItemId`, `actionType`) VALUES('Payment Plan Item Instrument Detail created', 'SYSTEM', 1452168967895, "
				+ amount
				+ ", "+paymentInstrumentType+", "
				+ paymentPlanItemId
				+ ", NULL)";
		DBUtilities.exUpdateQuery(paymentPlanItemInstrument, "pps");
	}
	
	public void InsertPaymentPlanInstrumentDetail(String ppsId,
	                                              String totalAmount, String actionType, String paymentInstrumentType,String paymentPlanExecutionStatusId) throws SQLException {
		String paymentPlanInstrumentDetail = "INSERT INTO `payment_plan_instrument_details` (`comments`, `updatedBy`, `updatedTimestamp`, `paymentInstrumentType`, "
				+ "`totalPrice`, `pps_Id`, `paymentPlanExecutionStatus_id`, `actionType`, `parentInstrumentDetailId`) VALUES('PPS Plan Instrument Details created', "
				+ "'SYSTEM', 1452168967889, "+paymentInstrumentType+", "
				+ totalAmount
				+ ", '"
				+ ppsId
				+ "', "+paymentPlanExecutionStatusId+", '" + actionType + "', NULL)";
		DBUtilities.exUpdateQuery(paymentPlanInstrumentDetail, "pps");
	}
	
	
	public void InsertPaymentPlanExecutionStatus() throws SQLException {
		String paymentPlanExecutionStatus = "Insert into `payment_plan_execution_status` (`id`, `comments`, "
				+ "`updatedBy`, `updatedTimestamp`, `actionType`,"
				+ " `instrumentTransactionId`, `invoker`, `invokerTransactionId`,"
				+ " `numOfRetriesDone`, `ppsActionType`, `state`, `status`, `paymentPlanInstrumentDetailId`)"
				+ " values('19679','Payment Plan Execution Status created','SYSTEM','1446730759342','DEBIT','13701767','pps','50151a96-79cf-41d9-9f61-d6553f1dc637','0','SALE','PIFSM Payment Successful','0','23064')";
		DBUtilities.exUpdateQuery(paymentPlanExecutionStatus, "pps");
	}
	
	public void DeleteReturn(int orderId) throws SQLException {
		String DeleteReturn = "DELETE FROM returns WHERE order_id="+orderId+"" ;
		String DeleteReturnLine = "DELETE FROM return_line WHERE order_id="+orderId+"" ;
		
		DBUtilities.exUpdateQuery(DeleteReturn, "rms");
		DBUtilities.exUpdateQuery(DeleteReturnLine, "rms");
		DBUtilities.exUpdateQuery("DELETE FROM pps_transactions where order_id="+orderId+" and pps_action_type='RETURN'", "myntra");
	}
	
	public void ItemBarcodeStamp(String returnId,String itembarcode) throws SQLException {
		String ItemBarcodeStamp = "UPDATE return_line SET item_barcode = "+itembarcode+" WHERE return_id="+returnId+";" ;
		DBUtilities.exUpdateQuery(ItemBarcodeStamp, "rms");
		
	}
	
	public String selfshipProcesstoDC(String orderLineId,String dcName, String courier) throws IOException, JAXBException{
		LmsServiceHelper lms= new LmsServiceHelper();
		//ReturnResponse returnResponse = createReturn(orderLineId, 1, ReturnMode.SELF_SHIP, 27L);
		ReturnResponse returnResponse = createReturnSelf(orderLineId, 1, ReturnMode.SELF_SHIP, 27L, RefundMode.CASHBACK, "", "");
		ReturnEntry returnEntry = returnResponse.getData().get(0);
		String returnId = returnEntry.getId().toString();
		ReturnResponse returnResponse2 = getReturnDetailsNew(returnId);
		ReturnEntry returnEntry2 = returnResponse2.getData().get(0);
		ReturnLineEntry entry2 = returnEntry2.getReturnLineEntries().get(0);
		returnLineStatusProcessNewDC(""+entry2.getId(), ReturnLineStatus.RRQS, ReturnLineStatus.RADC, entry2.getOrderId(), returnEntry.getId(), "100026329239", courier,"BD"+ LMSUtils.randomGenn(10), "Pawell", 1,"","","",dcName);
		return entry2.getReturnId().toString();
		
	}
	
	/**
	 * Create Return without Giving Address
	 * @param
	 * @throws
	 */
	
	public ReturnResponse createReturnWithoutAddress(String lineID, int quantity, ReturnMode returnMethod, Long returnReasonID,String missingField) throws JAXBException, JsonGenerationException, JsonMappingException, IOException{
		
		
		orderLineEntry = omsServiceHelper.getOrderLineEntry(""+lineID);
		orderEntry = omsServiceHelper.getOrderEntry(orderLineEntry.getOrderId().toString());
		orderReleaseEntry = omsServiceHelper.getOrderReleaseEntry(orderLineEntry.getOrderReleaseId().toString());
		
		setReturnEntry(lineID,quantity,returnMethod);
		
		//Set ReturnAddressDetailsEntry  Replace with switch
		switch(missingField){
			case "ALL":
				setReturnAddressDetailEntry("",null,"","","","","");
				break;
			case "RETURN_ADDRESS":
				setReturnAddressDetailEntry("",6118982L,"Bangalore","India","KA","560067","Myntra Design, AKR B, 3rd Floor");
				break;
			case "ID":
				setReturnAddressDetailEntry("Myntra Design, AKR B, 3rd Floor",null,"Bangalore","India","KA","560067","Myntra Design, AKR B, 3rd Floor");
				break;
			case "CITY":
				setReturnAddressDetailEntry("Myntra Design, AKR B, 3rd Floor",6118982L,"","India","KA","560067","Myntra Design, AKR B, 3rd Floor");
				break;
			case "COUNTRY":
				setReturnAddressDetailEntry("Myntra Design, AKR B, 3rd Floor",6118982L,"Bangalore","","KA","560067","Myntra Design, AKR B, 3rd Floor");
				break;
			case "STATE":
				setReturnAddressDetailEntry("Myntra Design, AKR B, 3rd Floor",6118982L,"Bangalore","India","","560067","Myntra Design, AKR B, 3rd Floor");
				break;
			case "ZIPCODE":
				setReturnAddressDetailEntry("Myntra Design, AKR B, 3rd Floor",6118982L,"Bangalore","India","KA","","Myntra Design, AKR B, 3rd Floor");
				break;
			case "":
				setReturnAddressDetailEntry("Myntra Design, AKR B, 3rd Floor",6118982L,"Bangalore","India","KA","560067","");
				break;
		}
		
		//Set Return Line Entries
		setReturnLineEntry(lineID,quantity,returnReasonID,1);
		
		ReturnRefundDetailsEntry returnRefundDetailsEntry = new ReturnRefundDetailsEntry();
		returnRefundDetailsEntry.setRefundMode(RefundMode.CASHBACK);
		//returnRefundDetailsEntry.setRefundAccountId();
		
		ReturnTrackingDetailsEntry returnTrackingDetailsEntry = new ReturnTrackingDetailsEntry();
		returnTrackingDetailsEntry.setCourierCode("ML");
		
		returnEntry.setReturnTrackingDetailsEntry(returnTrackingDetailsEntry);
		returnEntry.setReturnRefundDetailsEntry(returnRefundDetailsEntry);
		String payLoad = APIUtilities.getObjectToJSON(returnEntry);
		Svc service = HttpExecutorService.executeHttpService(Constants.RMS_PATH.CREATE_RETURN, null, SERVICE_TYPE.RMS_SVC.toString(), HTTPMethods.POST, payLoad, getRMSHeader());
		ReturnResponse returnResponse =(ReturnResponse) APIUtilities.getJsontoObject(service.getResponseBody(), new ReturnResponse());
		return returnResponse;
	}
	
	/**
	 * Create Return for Exchange without Giving Address
	 * @param
	 * @throws
	 */
	
	public ReturnResponse createReturnExchangeWithoutAddress(String origOrderId,String lineID, int quantity, ReturnMode returnMethod, Long returnReasonID,String exchangeId) throws JAXBException, JsonGenerationException, JsonMappingException, IOException{
		
		orderLineEntry = omsServiceHelper.getOrderLineEntry(""+lineID);
		orderEntry = omsServiceHelper.getOrderEntry(orderLineEntry.getOrderId().toString());
		orderReleaseEntry = omsServiceHelper.getOrderReleaseEntry(orderLineEntry.getOrderReleaseId().toString());
		
		setReturnEntry(lineID,quantity,returnMethod);
		returnEntry.setExchangeId(Long.parseLong(exchangeId));
		//Set ReturnAddressDetailsEntry
		setReturnAddressDetailEntry("",null,"","","","","");
		
		//Set Return Line Entries
		setReturnLineEntry(lineID,quantity,returnReasonID,1);
		
		
		ReturnRefundDetailsEntry returnRefundDetailsEntry = new ReturnRefundDetailsEntry();
		returnRefundDetailsEntry.setRefundMode(RefundMode.CASHBACK);
		returnRefundDetailsEntry.setRefundAccountId("418");
		
		ReturnTrackingDetailsEntry returnTrackingDetailsEntry = new ReturnTrackingDetailsEntry();
		returnTrackingDetailsEntry.setCourierCode("ML");
		
		returnEntry.setReturnTrackingDetailsEntry(returnTrackingDetailsEntry);
		returnEntry.setReturnRefundDetailsEntry(returnRefundDetailsEntry);
		String payLoad = APIUtilities.getObjectToJSON(returnEntry);
		Svc service = HttpExecutorService.executeHttpService(Constants.RMS_PATH.CREATE_RETURN_EXCHANGE, null, SERVICE_TYPE.RMS_SVC.toString(), HTTPMethods.POST, payLoad, getRMSHeader());
		ReturnResponse returnResponse =(ReturnResponse) APIUtilities.getJsontoObject(service.getResponseBody(), new ReturnResponse());
		return returnResponse;
	}
	
	/**
	 * Create Return for TryAndBuy Order without Giving Address
	 * @param
	 * @throws
	 */
	
	public ReturnResponse createReturnTnBV2(String lineID, int quantity, ReturnMode returnMethod,Integer warehouseId, Long returnReasonID, RefundMode refundMode, String courier, String trackingNo) throws JAXBException, JsonGenerationException, JsonMappingException, IOException{
		
		orderLineEntry = omsServiceHelper.getOrderLineEntry(""+lineID);
		orderEntry = omsServiceHelper.getOrderEntry(orderLineEntry.getOrderId().toString());
		orderReleaseEntry = omsServiceHelper.getOrderReleaseEntry(orderLineEntry.getOrderReleaseId().toString());
		
		setReturnEntry(lineID,quantity,returnMethod);
		
		//Set ReturnAddressDetailsEntry
		setReturnAddressDetailEntry("",null,"","","","","");
		
		//Set Return Line Entries
		setReturnLineEntry(lineID,quantity,returnReasonID,warehouseId);
		
		ReturnRefundDetailsEntry returnRefundDetailsEntry = new ReturnRefundDetailsEntry();
		returnRefundDetailsEntry.setRefundMode(refundMode);
		returnRefundDetailsEntry.setRefundAccountId("418");
		//returnRefundDetailsEntry.setRefundAccountId();
		
		ReturnTrackingDetailsEntry returnTrackingDetailsEntry = new ReturnTrackingDetailsEntry();
		returnTrackingDetailsEntry.setCourierCode(courier);
		returnTrackingDetailsEntry.setTrackingNo(trackingNo);
		returnTrackingDetailsEntry.setWarehouseId(1);
		
		//ReturnAdditionalDetailsEntry returnAdditionalDetailsEntry = new ReturnAdditionalDetailsEntry();
		//returnAdditionalDetailsEntry.setIdealReturnWarehouse(1L);
		
		//returnEntry.setReturnAdditionalDetailsEntry(returnAdditionalDetailsEntry);
		
		returnEntry.setReturnTrackingDetailsEntry(returnTrackingDetailsEntry);
		returnEntry.setReturnRefundDetailsEntry(returnRefundDetailsEntry);
		String payLoad = APIUtilities.getObjectToJSON(returnEntry);
		Svc service = HttpExecutorService.executeHttpService(Constants.RMS_PATH.CREATE_RETURN_TNB, null, SERVICE_TYPE.RMS_SVC.toString(), HTTPMethods.POST, payLoad, getRMSHeader());
		ReturnResponse returnResponse = (ReturnResponse) APIUtilities.getJsontoObject(service.getResponseBody(), new ReturnResponse());
		return returnResponse;
	}
	
	/**
	 * Receive RTO in Rejoy
	 * @param releaseId
	 * @param warehouseId
	 * @return
	 * @throws JAXBException
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public RtoResponse recieveShipmentInRejoy(String releaseId, Integer warehouseId) throws JAXBException, JsonGenerationException, JsonMappingException, IOException{
		RtoEntry rtoEntry = new RtoEntry();
		rtoEntry.setReleaseId(Long.parseLong(releaseId));
		rtoEntry.setReceiveLocationId(warehouseId);
		rtoEntry.setLoginId("rms_s");
		
		String payLoad = APIUtilities.getObjectToJSON(rtoEntry);
		Svc service = HttpExecutorService.executeHttpService(Constants.RMS_PATH.RECIEVE_SHIPMENT, null, SERVICE_TYPE.RMS_SVC.toString(), HTTPMethods.PUT, payLoad, getRMSHeader());
		RtoResponse rtoResponse = (RtoResponse) APIUtilities.getJsontoObject(service.getResponseBody(), new RtoResponse());
		return rtoResponse;
	}
	
	public RtoResponse reStockItemInRejoy(String barcode, Integer warehouseId) throws JAXBException, JsonGenerationException, JsonMappingException, IOException{
		
		String payLoad = " {\"itemBarCode\":\""+barcode+"\",\"restockLocationId\":\""+warehouseId+"\",\"loginId\":\"rms_s\"}";
		Svc service = HttpExecutorService.executeHttpService(Constants.RMS_PATH.RESTOCK_ITEM, null, SERVICE_TYPE.RMS_SVC.toString(), HTTPMethods.PUT, payLoad, getRMSHeader());
		RtoResponse rtoResponse = (RtoResponse) APIUtilities.getJsontoObject(service.getResponseBody(), new RtoResponse());
		return rtoResponse;
	}
	
	
	public void setReturnEntry(String lineID,int quantity,ReturnMode returnMethod){
		returnEntry.setOrderId(orderLineEntry.getOrderId());
		returnEntry.setComment("Return Creattion Entry For Line ID: "+ lineID + " Quantity : " + quantity);
		returnEntry.setLogin(orderEntry.getLogin());
		returnEntry.setCustomerName(orderReleaseEntry.getReceiverName());
		returnEntry.setEmail(orderReleaseEntry.getEmail());
		returnEntry.setReturnMode(returnMethod);
		returnEntry.setMobile("9823888800");
	}
	
	public void setReturnAddressDetailEntry(String returnAddress,Long id,String city,String country,String state,String zipCode,String address){
		
		//returnAddressDetailsEntry.setReturnAddress(returnAddress);
		returnAddressDetailsEntry.setAddressId(id);
		returnAddressDetailsEntry.setCity(city);
		returnAddressDetailsEntry.setCountry(country);
		returnAddressDetailsEntry.setAddress(address);
		returnAddressDetailsEntry.setState(state);
		returnAddressDetailsEntry.setZipcode(zipCode);
		returnEntry.setReturnAddressDetailsEntry(returnAddressDetailsEntry);
	}
	
	public void setReturnLineEntry(String lineID,int quantity,Long returnReasonID,Integer warehouseId){
		
		returnLineEntry.setComment("Line Entry Comment For Line ID "+lineID);
		returnLineEntry.setOrderId(orderLineEntry.getOrderId());
		returnLineEntry.setOrderLineId(Long.parseLong(lineID));
		returnLineEntry.setOrderReleaseId(orderLineEntry.getOrderReleaseId());
		returnLineEntry.setQuantity(quantity);
		returnLineEntry.setSupplyType(orderLineEntry.getSupplyType());
		returnLineEntry.setReturnReasonId(returnReasonID);
		returnLineEntry.setSkuId(orderLineEntry.getSkuId());
		returnLineEntry.setOptionId(orderLineEntry.getOptionId());
		returnLineEntry.setStyleId(orderLineEntry.getStyleId());
		returnLineEntry.setWarehouseId(warehouseId);
		returnlineEntries.add(returnLineEntry);
		returnEntry.setReturnLineEntries(returnlineEntries);
	}
	
	
	public String getReturnStatus(String return_id){
		Map<String, Object> getReturn = DBUtilities.exSelectQueryForSingleRecord("select shipment_status from return_shipment where source_return_id = "+return_id, "lms");
		return getReturn.get("shipment_status").toString();
	}
	
	public int getReturnDC(String return_id){
		Map<String, Object> getReturn = DBUtilities.exSelectQueryForSingleRecord("select delivery_center_id from return_shipment where source_return_id = "+return_id, "lms");
		return (int)(long)getReturn.get("delivery_center_id");
	}
	
	public int getReturnWH(String return_id){
		Map<String, Object> getReturn = DBUtilities.exSelectQueryForSingleRecord("select return_warehouse_id from return_shipment where source_return_id = "+return_id, "lms");
		return (int)(long) getReturn.get("return_warehouse_id");
	}
	
	public boolean getReturnStatusfromLMS(String status,String return_id, int delaytoCheck){
		//return getReturn.get("shipment_status").toString();
		log.info("Validate return Status in LMS "+status+"");
		boolean validateStatus = false;
		try {
			for (int i = 0; i < delaytoCheck; i++) {
				Map<String, Object> getReturn = DBUtilities.exSelectQueryForSingleRecord("select shipment_status from return_shipment where source_return_id = "+return_id, "lms");
				if (getReturn.get("shipment_status").toString().equalsIgnoreCase(status) ) {
					validateStatus = true;
					break;
				} else {
					Thread.sleep(4000);
					validateStatus = false;
				}
				
				log.info("waiting for return status in LMS" + status + " .current status=" + getReturn.get("shipment_status") + "\t " + i);
			}
		} catch (Exception e) {
			e.printStackTrace();
			validateStatus = false;
		}
		return validateStatus;
	}
	/**
	 * validateReturnStatusInRMS
	 * @param returnId
	 * @param status
	 * @param delaytoCheck
	 * @return
	 */
	public boolean validateReturnStatusInRMS(String returnId, String status, int delaytoCheck) {
		log.info("Validate Order Status in LMS order_to_ship table");
		boolean validateStatus = false;
		try {
			for (int i = 0; i < delaytoCheck; i++) {
				String status_code = getReturnStatusFromRMS(returnId);
				if (status_code.equalsIgnoreCase(status) || status_code.equalsIgnoreCase(status)) {
					validateStatus = true;
					break;
				} else {
					Thread.sleep(4000);
					validateStatus = false;
				}
				
				log.info("waiting for Order Status in LMS" + status + " .current status=" + status_code + "\t " + i);
			}
		} catch (Exception e) {
			e.printStackTrace();
			validateStatus = false;
		}
		return validateStatus;
	}
	
	/**
	 * getReturnStatusFromRMS
	 * @param returnId
	 * @return
	 */
	public String getReturnStatusFromRMS(String returnId) {
		String status="false";
		try {
			List list = DBUtilities.exSelectQuery("select status_code from returns where id=" + returnId, "rms");
			if (list == null) {
				return "false";
			}
			Map<String, Object> hm = (Map<String, Object>) list.get(0);
			status = hm.get("status_code").toString();
		} catch (Exception e) {
			log.error("Error in getReturnStatusFromRMS :- " + e.getMessage());
			return "false";
		}
		return status;
	}
	
	/**
	 * validateReturnStatusInWMS
	 * @param itemBarcode
	 * @param delaytoCheck
	 * @return
	 */
	public boolean validateReturnStatusInWMS(String itemBarcode, int delaytoCheck) {
		log.info("Validate Return Status in WMS item table");
		boolean validateStatus = false;
		try {
			for (int i = 0; i < delaytoCheck; i++) {
				List list = DBUtilities.exSelectQuery("Select item_status from item WHERE barcode = "+itemBarcode+";", "wms");
				if (list == null) {
					log.info("Return not restocked in WMS yet");
					return false;
					
				}
				HashMap<String, Object> hm = (HashMap<String, Object>) list.get(0);
				if (hm.get("item_status").toString().equalsIgnoreCase("CUSTOMER_RETURNED")) {
					validateStatus = true;
					log.info("Return has been succesfully Restocked");
					break;
				} else {
					Thread.sleep(4000);
					validateStatus = false;
				}
				
				log.info("waiting for Return Status in WMS ,current status=" + hm.get("item_status").toString() + "\t " + i);
			}
		} catch (Exception e) {
			e.printStackTrace();
			validateStatus = false;
		}
		return validateStatus;
	}
	
	/**
	 * WaitReturnStatus
	 * @param status
	 * @param returnId
	 * @param delaytoCheck
	 * @return
	 */
	public boolean WaitReturnStatus(ReturnStatus status,String returnId, int delaytoCheck) {
		log.info("Wait for Return Status");
		boolean validateStatus = false;
		try {
			for (int i = 0; i < delaytoCheck; i++) {
				ReturnResponse returnResponse = getReturnDetailsNew(returnId);
				ReturnEntry returnEntry = returnResponse.getData().get(0);
				
				if (returnEntry.getStatusCode() == status) {
					validateStatus = true;
					break;
				} else {
					Thread.sleep(5000);
					validateStatus = false;
				}
				
				log.info("waiting for Return Status in RMS ,current status=" + returnEntry.getStatusCode() + "\t " + i);
			}
		} catch (Exception e) {
			e.printStackTrace();
			validateStatus = false;
		}
		return validateStatus;
	}
	
	/**
	 * WaitReturnLineStatus
	 * @param status
	 * @param returnId
	 * @param delaytoCheck
	 * @return
	 */
	public boolean WaitReturnLineStatus(ReturnLineStatus status,String returnId, int delaytoCheck) {
		log.info("Wait for Return Line Status");
		boolean validateStatus = false;
		try {
			for (int i = 0; i < delaytoCheck; i++) {
				ReturnResponse returnResponse = getReturnDetailsNew(returnId);
				ReturnEntry returnEntry = returnResponse.getData().get(0);
				if (returnEntry.getReturnLineEntries().get(0).getStatusCode() == status) {
					validateStatus = true;
					break;
				} else {
					Thread.sleep(3000);
					validateStatus = false;
				}
				
				log.info("waiting for Return Line Status in RMS ,current status=" + returnEntry.getReturnLineEntries().get(0).getStatusCode() + "\t " + i);
			}
		} catch (Exception e) {
			e.printStackTrace();
			validateStatus = false;
		}
		return validateStatus;
	}
	
	/**
	 * WaitRefundStatus
	 * @param returnId
	 * @param delaytoCheck
	 * @return
	 *//*
			public boolean WaitRefundStatus(Long returnId, int delaytoCheck) {
				log.info("Wait for Refund Status");
				boolean validateStatus = false;
				try {
					for (int i = 0; i < delaytoCheck; i++) {
						ReturnResponse returnResponse = getReturnDetailsNew(returnId);
						ReturnEntry returnEntry = returnResponse.getData().get(0);
						if (returnEntry.getReturnRefundDetailsEntry().getRefunded()== true){
							validateStatus = true;
							break;
						}else {
							Thread.sleep(3000);
							validateStatus = false;
						}

						log.info("waiting for Refund Status in RMS ,current status=" + returnEntry.getStatusCode() +"\t " + i);
					}
				} catch (Exception e) {
					e.printStackTrace();
					validateStatus = false;
				}
				return validateStatus;
			}
*/
	/**
	 * WaitRefundStatus
	 * @param returnId
	 * @param delaytoCheck
	 * @return
	 */
	public boolean WaitRefundStatus(String returnId, int delaytoCheck) {
		log.info("Wait for Refund Status");
		boolean validateStatus = false;
		try {
			for (int i = 0; i < delaytoCheck; i++) {
				ReturnResponse returnResponse = getReturnDetailsNew(returnId);
				ReturnEntry returnEntry = returnResponse.getData().get(0);
				if (returnEntry.getReturnRefundDetailsEntry().getRefundPPSId()!=null & returnEntry.getReturnRefundDetailsEntry().getRefunded()== true){
					validateStatus = true;
					break;
				}else {
					Thread.sleep(5000);
					validateStatus = false;
				}
				
				log.info("waiting for Refund Status in RMS ,refund pps id=" + returnEntry.getReturnRefundDetailsEntry().getRefundPPSId()+"\t " + i);
			}
		} catch (Exception e) {
			e.printStackTrace();
			validateStatus = false;
		}
		return validateStatus;
	}
	
	/**
	 * ProcessReturnRevamped
	 * @param returnId
	 * @return
	 */
	public void ProcessReturnRevamped(String returnId) {
		log.info("Processing Return in RMS");
		
		try {
			
			ReturnResponse returnResponse = getReturnDetailsNew(returnId);
			ReturnEntry returnEntry = returnResponse.getData().get(0);
			
			String status = returnEntry.getStatusCode().toString();
			
			switch (status) {
				case "RPI" :
					returnPickupProcessNew(returnEntry.getId().toString(), ReturnStatus.RPI);
					Assert.assertTrue(WaitReturnStatus(ReturnStatus.RPU, returnId, 10));
					ReturnResponse returnResponse2 = getReturnDetailsNew(returnId);
					ReturnEntry returnEntry2 = returnResponse2.getData().get(0);
					ReturnLineEntry entry = returnEntry2.getReturnLineEntries().get(0);
					returnLineStatusProcessNewWH(ReturnLineStatus.RPU, ReturnLineStatus.RRC, entry.getId(), returnEntry.getReturnLineEntries().get(0).getId(), "", "", "", "", "Pawell", (int)(long)returnEntry2.getReturnAdditionalDetailsEntry().getIdealReturnWarehouse(), "WH");
					Assert.assertTrue(WaitReturnStatus(ReturnStatus.RRC, returnId, 10));
					OMSServiceHelper omsHelper = new OMSServiceHelper();
					OrderEntry orderEntry = omsServiceHelper.getOrderEntry(returnEntry2.getOrderId().toString());
					OrderReleaseEntry releaseEntry = omsServiceHelper.getOrderReleaseEntry(omsServiceHelper.getReleaseId(orderEntry.getId().toString()));
					OrderLineEntry lineEntry = omsHelper.getOrderLineEntry(releaseEntry.getOrderLines().get(0).getId().toString());
					returnLineStatusProcessNew(entry.getId().toString(), ReturnLineStatus.RRC, ReturnLineStatus.RQP, lineEntry.getId().toString(), returnEntry.getId().toString(), "", "", "Pawell", 1,"Q1","QC Reason","QC Description");
					Assert.assertTrue(WaitReturnLineStatus(ReturnLineStatus.RIS, returnId, 10));
					Assert.assertTrue(WaitRefundStatus(returnId, 15));
					log.info("Return Successfully processed in RMS");
					log.info("Checking WMS Re-stock");
					Assert.assertTrue(validateReturnStatusInWMS(returnEntry2.getReturnLineEntries().get(0).getItemBarcode(), 15));
					
					break;
				
				case "RRQP":
					ReturnResponse returnResponse3 = getReturnDetailsNew(returnId);
					ReturnEntry returnEntry3 = returnResponse3.getData().get(0);
					bulk_statuschange(returnEntry.getId().toString(), "", "123", 2L, ReturnStatus.RPI, returnEntry3.getReturnTrackingDetailsEntry().getCourierCode(), "Pawell");
					Assert.assertTrue(WaitReturnStatus(ReturnStatus.RPI, returnId, 10));
					bulk_statuschange(returnEntry.getId().toString(), "", "123", 2L, ReturnStatus.RDU, returnEntry3.getReturnTrackingDetailsEntry().getCourierCode(), "Pawell");
					Assert.assertTrue(WaitReturnStatus(ReturnStatus.RDU, returnId, 10));
					returnLineStatusProcessNewDC(ReturnLineStatus.RDU, ReturnLineStatus.RADC, returnEntry3.getId(), returnEntry3.getReturnLineEntries().get(0).getId(), "Pass", "", "IP", "12345", "Pawell", "MYQ", 23L, "DC");
						/*returnLineStatusProcessNewWH(ReturnLineStatus.RADC, ReturnLineStatus.RRC,returnEntry3.getId(), returnEntry3.getReturnLineEntries().get(0).getId(), "", "", "", "", "Pawell", (int)(long)returnEntry3.getReturnAdditionalDetailsEntry().getIdealReturnWarehouse(), "WH");
						Assert.assertTrue(WaitReturnStatus(ReturnStatus.RRC, returnId, 10));
						OMSServiceHelper omsHelper3 = new OMSServiceHelper();
		        		OrderEntry orderEntry3 = omsServiceHelper.getOrderEntry(returnEntry3.getOrderId());
		        		OrderReleaseEntry releaseEntry3 = omsServiceHelper.getOrderReleaseEntry(omsServiceHelper.getReleaseId(orderEntry3.getId()));
		        		OrderLineEntry lineEntry3 = omsHelper3.getOrderLineEntry(releaseEntry3.getOrderLines().get(0).getId().toString());
						returnLineStatusProcessNewE2E(returnEntry3.getId(), ReturnLineStatus.RRC, ReturnLineStatus.RQP, lineEntry3.getId(), returnEntry3.getReturnLineEntries().get(0).getId(), "", "", "Pawell", (int)(long)returnEntry3.getReturnAdditionalDetailsEntry().getIdealReturnWarehouse(), "Q1", "QC Reason", "QC description");
						Assert.assertTrue(WaitReturnLineStatus(ReturnLineStatus.RIS, returnId, 10));*/
					Assert.assertTrue(WaitRefundStatus(returnId, 15));
					log.info("Return Successfully processed in RMS");
					break;
				
				case "RRQS":
					ReturnResponse returnResponse4 = getReturnDetailsNew(returnId);
					ReturnEntry returnEntry4 = returnResponse4.getData().get(0);
					returnStatusProcessNew(returnEntry4.getId().toString(), ReturnStatus.RRQS, ReturnStatus.RDU);
					Assert.assertTrue(WaitReturnStatus(ReturnStatus.RDU,returnId, 10));
					returnLineStatusProcessNewDC(ReturnLineStatus.RDU, ReturnLineStatus.RADC, returnEntry4.getId(), returnEntry4.getReturnLineEntries().get(0).getId(), "Pass", "", "IP", "12345", "Pawell", "MYQ", 23L, "DC");
					Assert.assertTrue(WaitRefundStatus(returnId, 15));
					/*	returnLineStatusProcessNewWH(ReturnLineStatus.RADC, ReturnLineStatus.RRC,returnEntry4.getId(), returnEntry4.getReturnLineEntries().get(0).getId(), "", "", "", "", "Pawell", (int)(long)returnEntry4.getReturnAdditionalDetailsEntry().getIdealReturnWarehouse(), "WH");
						Assert.assertTrue(WaitReturnStatus(ReturnStatus.RRC, returnId, 10));
						OMSServiceHelper omsHelper4 = new OMSServiceHelper();
		        		OrderEntry orderEntry4 = omsServiceHelper.getOrderEntry(returnEntry4.getOrderId());
		        		OrderReleaseEntry releaseEntry4 = omsServiceHelper.getOrderReleaseEntry(omsServiceHelper.getReleaseId(orderEntry4.getId()));
		        		OrderLineEntry lineEntry4 = omsHelper4.getOrderLineEntry(releaseEntry4.getOrderLines().get(0).getId().toString());
						returnLineStatusProcessNewE2E(returnEntry4.getId(), ReturnLineStatus.RRC, ReturnLineStatus.RQP, lineEntry4.getId(), returnEntry4.getReturnLineEntries().get(0).getId(), "", "", "Pawell", (int)(long)returnEntry4.getReturnAdditionalDetailsEntry().getIdealReturnWarehouse(), "Q1", RMS_STATUS.QC_REASON, RMS_STATUS.QC_DESCRIPTION);
						Assert.assertTrue(WaitReturnLineStatus(ReturnLineStatus.RIS, returnId, 10));*/
					log.info("Return Successfully processed in RMS");
					break;
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		
	}
	
	
	
	
	
	
	
}


