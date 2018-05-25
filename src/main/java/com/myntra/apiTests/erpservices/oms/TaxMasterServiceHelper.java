package com.myntra.apiTests.erpservices.oms;

import com.myntra.apiTests.erpservices.Constants;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.taxmaster.client.entry.request.BulkGSTTaxRequestEntry;
import com.myntra.taxmaster.client.entry.request.BulkOmsItemTaxRequestEntry;
import com.myntra.taxmaster.client.entry.request.CessRequestEntry;
import com.myntra.taxmaster.client.entry.request.CustomerGSTRequestEntry;
import com.myntra.taxmaster.client.entry.request.OmsAdditionalChargesTaxRequestEntry;
import com.myntra.taxmaster.client.entry.request.OmsDiscountEntry;
import com.myntra.taxmaster.client.entry.request.OmsItemTaxRequestEntry;
import com.myntra.taxmaster.client.response.BulkCessResponse;
import com.myntra.taxmaster.client.response.BulkGSTTaxResponse;
import com.myntra.taxmaster.client.response.BulkOmsTaxResponse;
import com.myntra.taxmaster.client.response.MultiRuleTaxResponse;
import com.myntra.taxmaster.client.response.OmsTaxResponse;
import com.myntra.taxmaster.client.response.TaxResponse;
import com.myntra.test.commons.service.HTTPMethods;
import com.myntra.test.commons.service.HttpExecutorService;
import com.myntra.apiTests.SERVICE_TYPE;
import com.myntra.test.commons.service.Svc;

import javax.xml.bind.JAXBException;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by abhijit.pati on 24/01/17.
 */
public class TaxMasterServiceHelper {

	
	/**
	 * Get Bulk Item Level tax
	 * @param styleId
	 * @param destinationPincode
	 * @param sourceWarehouseId
	 * @param courierCode
	 * @param quantity
	 * @param unitMrp
	 * @param unitLevelProratedAdditionalCharges
	 * @param unitLevelProratedDiscounts
	 * @param sellerId
	 * @param orderDate
	 * @param entityId
	 * @param entityType
	 * @return
	 * @throws IOException
	 * @throws JAXBException
	 */
	public BulkOmsItemTaxRequestEntry getBulkItemLevelTaxEntry(Long styleId, Long destinationPincode, Long sourceWarehouseId, String courierCode, Integer quantity, Double unitMrp, List<OmsAdditionalChargesTaxRequestEntry> unitLevelProratedAdditionalCharges, List<OmsDiscountEntry> unitLevelProratedDiscounts, Long sellerId, Date orderDate, Long entityId, String entityType,Boolean vatRecoveredFromCustomer,Long storeId) throws IOException, JAXBException {

		OmsItemTaxRequestEntry omsItemTaxRequestEntry = getOmsItemTaxRequestEntry(styleId, destinationPincode, sourceWarehouseId, courierCode, quantity, unitMrp, unitLevelProratedAdditionalCharges, unitLevelProratedDiscounts, sellerId, orderDate, entityId, entityType, vatRecoveredFromCustomer,storeId);
		List<OmsItemTaxRequestEntry> omsItemTaxRequestEntryList = new ArrayList<>();
		omsItemTaxRequestEntryList.add(omsItemTaxRequestEntry);
		omsItemTaxRequestEntryList.add(omsItemTaxRequestEntry);
		omsItemTaxRequestEntryList.add(omsItemTaxRequestEntry);
		omsItemTaxRequestEntryList.add(omsItemTaxRequestEntry);

		BulkOmsItemTaxRequestEntry bulkOmsItemTaxRequestEntry = new BulkOmsItemTaxRequestEntry();
		bulkOmsItemTaxRequestEntry.setOmsItemTaxRequestEntryList(omsItemTaxRequestEntryList);

		return bulkOmsItemTaxRequestEntry;
	}

	/**
	 * To Get OmsItemTaxRequestEntry payload Request
	 * @param styleId
	 * @param destinationPincode
	 * @param sourceWarehouseId
	 * @param courierCode
	 * @param quantity
	 * @param unitMrp
	 * @param unitLevelProratedAdditionalCharges
	 * @param unitLevelProratedDiscounts
	 * @param sellerId
	 * @param orderDate
	 * @param entityId
	 * @param entityType
	 * @param vatRecoveredFromCustomer
	 * @return
	 */
	public OmsItemTaxRequestEntry getOmsItemTaxRequestEntry(Long styleId, Long destinationPincode, Long sourceWarehouseId, String courierCode, Integer quantity, Double unitMrp, List<OmsAdditionalChargesTaxRequestEntry> unitLevelProratedAdditionalCharges, List<OmsDiscountEntry> unitLevelProratedDiscounts, Long sellerId, Date orderDate, Long entityId, String entityType,Boolean vatRecoveredFromCustomer,Long storeId){
		OmsItemTaxRequestEntry omsItemTaxRequestEntry = new OmsItemTaxRequestEntry();
		omsItemTaxRequestEntry.setStyleId(styleId);
		omsItemTaxRequestEntry.setSellerId(sellerId);
		omsItemTaxRequestEntry.setDestinationPincode(destinationPincode);
		omsItemTaxRequestEntry.setSourceWarehouseId(sourceWarehouseId);
		omsItemTaxRequestEntry.setCourierCode(courierCode);
		omsItemTaxRequestEntry.setQuantity(quantity);
		omsItemTaxRequestEntry.setUnitMrp(unitMrp);
		omsItemTaxRequestEntry.setOrderDate(orderDate);
		omsItemTaxRequestEntry.setUnitLevelProratedAdditionalCharges(unitLevelProratedAdditionalCharges);
		omsItemTaxRequestEntry.setUnitLevelProratedDiscounts(unitLevelProratedDiscounts);
		omsItemTaxRequestEntry.setEntityId(entityId);
		omsItemTaxRequestEntry.setEntityType(entityType);
		omsItemTaxRequestEntry.setVatRecoveredFromCustomer(vatRecoveredFromCustomer);
		omsItemTaxRequestEntry.setStoreId(storeId);

		return omsItemTaxRequestEntry;
	}




	/**
	 * Get Item Level tax
	 * @param styleId
	 * @param destinationPincode
	 * @param sourceWarehouseId
	 * @param courierCode
	 * @param quantity
	 * @param unitMrp
	 * @param unitLevelProratedAdditionalCharges
	 * @param unitLevelProratedDiscounts
	 * @param sellerId
	 * @param orderDate
	 * @param isTaxRecovered
	 * @return
	 * @throws JAXBException
	 * @throws IOException
	 */
	public OmsTaxResponse getItemLevelTaxPositive(Long styleId, Long destinationPincode, Long sourceWarehouseId, String courierCode, Integer quantity, Double unitMrp, List<OmsAdditionalChargesTaxRequestEntry> unitLevelProratedAdditionalCharges, List<OmsDiscountEntry> unitLevelProratedDiscounts, Long sellerId, Date orderDate,Boolean isTaxRecovered,Long storeId) throws JAXBException, IOException {
		OmsItemTaxRequestEntry omsItemTaxRequestEntry = getOmsItemTaxRequestEntry(styleId, destinationPincode, sourceWarehouseId, courierCode, quantity, unitMrp, unitLevelProratedAdditionalCharges, unitLevelProratedDiscounts, sellerId, orderDate, null, null, isTaxRecovered,storeId);
		String payload = APIUtilities.convertXMLObjectToString(omsItemTaxRequestEntry);

		Svc service = HttpExecutorService.executeHttpService(Constants.TAXMASTER_PATH.GETTAXONITEM, null, SERVICE_TYPE.TAXMASTER_SVC.toString(), HTTPMethods.PUT, payload, getTaxMasterHeaderXML());
		OmsTaxResponse omsTaxResponse = (OmsTaxResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(), new OmsTaxResponse());
		return omsTaxResponse;
	}

	/**
	 * Get Bulk Item Level tax
	 * @param styleId
	 * @param destinationPincode
	 * @param sourceWarehouseId
	 * @param courierCode
	 * @param quantity
	 * @param unitMrp
	 * @param unitLevelProratedAdditionalCharges
	 * @param unitLevelProratedDiscounts
	 * @param sellerId
	 * @param orderDate
	 * @param entityId
	 * @param entityType
	 * @return
	 * @throws IOException
	 * @throws JAXBException
	 */
	public BulkOmsTaxResponse getBulkItemTax(Long styleId, Long destinationPincode, Long sourceWarehouseId, String courierCode, Integer quantity, Double unitMrp, List<OmsAdditionalChargesTaxRequestEntry> unitLevelProratedAdditionalCharges, List<OmsDiscountEntry> unitLevelProratedDiscounts, Long sellerId, Date orderDate, Long entityId,String entityType,Boolean vatRecoveredFromCustomer,Long storeId) throws IOException, JAXBException{
		BulkOmsItemTaxRequestEntry  bulkOmsItemTaxRequestEntry = getBulkItemLevelTaxEntry(styleId, destinationPincode, sourceWarehouseId, courierCode, quantity, unitMrp, unitLevelProratedAdditionalCharges, unitLevelProratedDiscounts, sellerId, orderDate,entityId,entityType,vatRecoveredFromCustomer,storeId);
		String payload = APIUtilities.convertXMLObjectToString(bulkOmsItemTaxRequestEntry);
		Svc service = HttpExecutorService.executeHttpService(Constants.TAXMASTER_PATH.GETBULKTAXONITEM, null, SERVICE_TYPE.TAXMASTER_SVC.toString(), HTTPMethods.PUT, payload, getTaxMasterHeaderXML());
		BulkOmsTaxResponse bulkOmsTaxResponse = (BulkOmsTaxResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(), new BulkOmsTaxResponse());
		return bulkOmsTaxResponse;
	}

	/**
	 * Set Discount Entry
	 * @param discountType
	 * @param amount
	 * @return
	 */
	public OmsDiscountEntry setOmsDiscountEntry(String discountType, double amount){
		OmsDiscountEntry omsDiscountEntry = new OmsDiscountEntry();
		omsDiscountEntry.setDiscountType(discountType);
		omsDiscountEntry.setAmount(amount);
		return omsDiscountEntry;
	}

	/**
	 * Set Additional charge Tax request Entry
	 * @param additionalCharges
	 * @return
	 */
	public List<OmsAdditionalChargesTaxRequestEntry> setOMSAdditionalChargesTaxRequestEntry(List<String> additionalCharges){
		List<OmsAdditionalChargesTaxRequestEntry> listOfAdditionalCharges = new ArrayList<>();
		for (String additionalCharge : additionalCharges) {
			String[] additionalChargesSplit = additionalCharge.split(":");
			OmsAdditionalChargesTaxRequestEntry omsAdditionalChargesTaxRequestEntry = new OmsAdditionalChargesTaxRequestEntry();
			omsAdditionalChargesTaxRequestEntry.setChargeType(additionalChargesSplit[0]);
			omsAdditionalChargesTaxRequestEntry.setAmount(Double.parseDouble(additionalChargesSplit[1]));
			listOfAdditionalCharges.add(omsAdditionalChargesTaxRequestEntry);
		}
		return listOfAdditionalCharges;
	}

	/**
	 * getCustomerGSTRequestEntries helper
	 * @param inputList
	 * @return
	 */
	public List<CustomerGSTRequestEntry> getCustomerGSTRequestEntries(List<HashMap<String,String>> inputList){
		Date date = new Date();

		List<CustomerGSTRequestEntry> customerGSTRequestEntries = new ArrayList<>();
		for(HashMap<String,String> hm :inputList ){
			CustomerGSTRequestEntry customerGSTRequestEntry = new CustomerGSTRequestEntry();
			if(!hm.get("mrpValue").isEmpty()){
				customerGSTRequestEntry.setMrpValue(Double.parseDouble(hm.get("mrpValue")));
			}else{
				customerGSTRequestEntry.setMrpValue(null);
			}

			customerGSTRequestEntry.setDestinationStateCode(hm.get("destStateCode"));
			customerGSTRequestEntry.setSourceStateCode(hm.get("sourceStateCode"));
			customerGSTRequestEntry.setMaterial(hm.get("material"));
			customerGSTRequestEntry.setDate(date);
			customerGSTRequestEntry.setHsnCode(hm.get("hsnCode"));
			customerGSTRequestEntries.add(customerGSTRequestEntry);
		}
		
		return customerGSTRequestEntries;
	}
	
	/**
	 * Update date in payload
	 * @param payload
	 * @return
	 */
	public String changeDateInPayload(String payload){
		String sourceDate = StringUtils.substringBetween(payload, "\"date\":", "}");

		Date currentDate = new Date();
		String replacedDate = new SimpleDateFormat("yyyy-MM-dd").format(currentDate);
		payload = payload.replaceAll(sourceDate, "\""+replacedDate+"\"");
		return payload;

	}
	
	/**
	 * Get Bulk Tax helper for CustomerGST
	 * @param inputList
	 * @return
	 * @throws IOException
	 * @throws JAXBException
	 */
	public String getBulkTaxCustomerGSTPayload(List<HashMap<String,String>> inputList) throws IOException, JAXBException {

		List<CustomerGSTRequestEntry> customerGSTRequestEntries = getCustomerGSTRequestEntries(inputList);

		BulkGSTTaxRequestEntry bulkGSTTaxRequestEntry = new BulkGSTTaxRequestEntry();
		bulkGSTTaxRequestEntry.setRequestEntries(customerGSTRequestEntries);
		String payload = APIUtilities.convertJavaObjectToJsonUsingGson(bulkGSTTaxRequestEntry);
		payload = changeDateInPayload(payload);		
		return payload;
	}

	/**
	 * Get BulkTax helper for Cess
	 * @param inputList
	 * @return
	 * @throws IOException
	 * @throws JAXBException
	 */
	public String getBulkTaxCessPayload(List<HashMap<String,String>> inputList) throws IOException, JAXBException {

		List<CustomerGSTRequestEntry> customerGSTRequestEntries = getCustomerGSTRequestEntries(inputList);
		BulkGSTTaxRequestEntry bulkGSTTaxRequestEntry = new BulkGSTTaxRequestEntry();
		bulkGSTTaxRequestEntry.setRequestEntries(customerGSTRequestEntries);
		String payload = APIUtilities.convertJavaObjectToJsonUsingGson(bulkGSTTaxRequestEntry);
		payload = changeDateInPayload(payload);
		return payload;
	}



	/**
	 * Get Bulktax for Customer GST
	 * @param inputList
	 * @return
	 * @throws IOException
	 * @throws JAXBException
	 */
	public BulkGSTTaxResponse getBulkTaxCustomerGST(List<HashMap<String,String>> inputList) throws IOException, JAXBException{
		String payload = getBulkTaxCustomerGSTPayload(inputList);
		Svc service = HttpExecutorService.executeHttpService(Constants.TAXMASTER_PATH.GET_BULK_TAX, null, SERVICE_TYPE.TAXMASTER_SVC.toString(), HTTPMethods.POST, payload, getTaxMasterHeaderJson());
		String response = service.getResponseBody().toString();
		BulkGSTTaxResponse bulkGSTTaxResponse = (BulkGSTTaxResponse) APIUtilities.getJsontoObjectUsingFasterXML(response, new BulkGSTTaxResponse());
		return bulkGSTTaxResponse;
	}




	/**
	 * Get Tax for Cess
	 * @param hsnCode
	 * @param material
	 * @param mrpValue
	 * @param date
	 * @return
	 * @throws JAXBException
	 * @throws IOException
	 */
	public TaxResponse getTaxCess(String hsnCode,String material,Double mrpValue,Date date) throws JAXBException, IOException {

		CessRequestEntry cessRequestEntry = new CessRequestEntry();
		cessRequestEntry.setHsnCode(hsnCode);
		cessRequestEntry.setMaterial(material);
		cessRequestEntry.setMrpValue(mrpValue);
		cessRequestEntry.setDate(date);
		String payload = APIUtilities.convertXMLObjectToString(cessRequestEntry);
		Svc service = HttpExecutorService.executeHttpService(Constants.TAXMASTER_PATH.GET_TAX_CESS, null, SERVICE_TYPE.TAXMASTER_SVC.toString(), HTTPMethods.POST, payload, getTaxMasterHeaderXML());
		TaxResponse cessTaxResponse = (TaxResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(), new TaxResponse());
		return cessTaxResponse;
	}

	/**
	 * get ALLTax for Cess
	 * @param hsnCode
	 * @param material
	 * @param mrpValue
	 * @param date
	 * @return
	 * @throws JAXBException
	 * @throws IOException
	 */
	public MultiRuleTaxResponse getAllTaxCess(String hsnCode,String material,Double mrpValue,Date date) throws JAXBException, IOException {

		CessRequestEntry cessRequestEntry = new CessRequestEntry();
		cessRequestEntry.setHsnCode(hsnCode);
		cessRequestEntry.setMaterial(material);
		cessRequestEntry.setMrpValue(mrpValue);
		cessRequestEntry.setDate(date);
		String payload = APIUtilities.convertXMLObjectToString(cessRequestEntry);
		Svc service = HttpExecutorService.executeHttpService(Constants.TAXMASTER_PATH.GET_ALLTAX_CESS, null, SERVICE_TYPE.TAXMASTER_SVC.toString(), HTTPMethods.POST, payload, getTaxMasterHeaderXML());
		MultiRuleTaxResponse cessTaxResponse = (MultiRuleTaxResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(), new MultiRuleTaxResponse());
		return cessTaxResponse;
	}

	/**
	 * Get BulkTax for Cess
	 * @param inputList
	 * @return
	 * @throws IOException
	 * @throws JAXBException
	 */
	public BulkCessResponse getBulkTaxCess(List<HashMap<String,String>> inputList) throws IOException, JAXBException{
		HashMap<String,Integer> responseMap = new HashMap<String, Integer>();
		String payload = getBulkTaxCessPayload(inputList);
		Svc service = HttpExecutorService.executeHttpService(Constants.TAXMASTER_PATH.GET_BULKTAX_CESS, null, SERVICE_TYPE.TAXMASTER_SVC.toString(), HTTPMethods.POST, payload, getTaxMasterHeaderJson());
		String response = service.getResponseBody().toString();
		BulkCessResponse bulkCessResponse = (BulkCessResponse) APIUtilities.getJsontoObjectUsingFasterXML(response, new BulkCessResponse());

		return bulkCessResponse;

	}



	/**
	 * TaxMaster Header for XML
	 * @return
	 */
	public HashMap<String, String> getTaxMasterHeaderXML(){
		HashMap<String, String> createOrderHeaders = new HashMap<String, String>();
		createOrderHeaders.put("Authorization", "Basic bG1zYWRtaW5+bG1zYWRtaW46dGVzdA==");
		createOrderHeaders.put("Content-Type", "application/xml");                                                                                                                                                                                                          
		createOrderHeaders.put("Accept", "application/xml");
		return createOrderHeaders;
	}

	/**
	 * TaxMaster Header for Json
	 * @return
	 */
	public HashMap<String, String> getTaxMasterHeaderJson(){
		HashMap<String, String> createOrderHeaders = new HashMap<String, String>();
		createOrderHeaders.put("Authorization", "Basic bG1zYWRtaW5+bG1zYWRtaW46dGVzdA==");
		createOrderHeaders.put("Content-Type", "application/json");                                                                                                                                                                                                          
		createOrderHeaders.put("Accept", "application/json");
		return createOrderHeaders;
	}

}
