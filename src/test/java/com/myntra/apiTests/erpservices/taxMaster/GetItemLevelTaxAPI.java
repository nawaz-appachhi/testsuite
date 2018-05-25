package com.myntra.apiTests.erpservices.taxMaster;

import com.myntra.apiTests.erpservices.oms.Test.GovtTaxCalculationTest;
import com.myntra.apiTests.erpservices.oms.OMSServiceHelper;
import com.myntra.apiTests.erpservices.oms.TaxMasterServiceHelper;
import com.myntra.client.notification.response.StatusResponse;
import com.myntra.client.notification.response.StatusResponse.Type;
import com.myntra.oms.client.entry.TaxEntry;
import com.myntra.taxmaster.client.entry.OmsTaxEntry;
import com.myntra.taxmaster.client.entry.request.CessRequestEntry;
import com.myntra.taxmaster.client.entry.request.CustomerGSTRequestEntry;
import com.myntra.taxmaster.client.entry.request.OmsAdditionalChargesTaxRequestEntry;
import com.myntra.taxmaster.client.entry.request.OmsDiscountEntry;
import com.myntra.taxmaster.client.response.BulkCessResponse;
import com.myntra.taxmaster.client.response.BulkGSTTaxResponse;
import com.myntra.taxmaster.client.response.BulkOmsTaxResponse;
import com.myntra.taxmaster.client.response.MultiRuleTaxResponse;
import com.myntra.taxmaster.client.response.MultiRuleTaxResponse.TaxEntries;
import com.myntra.taxmaster.client.response.OmsTaxResponse;
import com.myntra.taxmaster.client.response.TaxResponse;
import com.myntra.test.commons.testbase.BaseTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by 16553 on 06/04/17.
 */
public class GetItemLevelTaxAPI extends BaseTest {
	GovtTaxCalculationTest govttax = new GovtTaxCalculationTest();
	Boolean isGSTLive = true;
	String expectedTaxType = null;
	Long storeId = 1L;
	
	public enum TaxType{
		CGST,SGST,IGST,CESS
	}
	
	@Test(groups = { "regression", "taxCalculation",
			"Sanity" }, dataProvider = "itemLevelTaxCouponLessThan20DP", dataProviderClass = GetItemLevelTaxAPI_DP.class)
	public void getItemLevelTaxVATForCouponLessThan20(long styleId, long destinationPincode, long sourceWarehouseId,
			String courierCode, int quantity, double unitMrp,
			List<OmsAdditionalChargesTaxRequestEntry> unitLevelProratedAdditionalCharges,
			List<OmsDiscountEntry> unitLevelProratedDiscounts, long sellerId, Date orderDate)
					throws JAXBException, IOException {
		SoftAssert sft = new SoftAssert();

		expectedTaxType = "CGST:SGST:CESS"; // For GST

		

		TaxMasterServiceHelper taxMasterServiceHelper = new TaxMasterServiceHelper();

		OmsTaxResponse taxResponse = taxMasterServiceHelper.getItemLevelTaxPositive(styleId, destinationPincode,
				sourceWarehouseId, courierCode, quantity, unitMrp, unitLevelProratedAdditionalCharges,
				unitLevelProratedDiscounts, sellerId, orderDate, false,storeId);
		sft.assertEquals(taxResponse.getStatus().getStatusType().toString(), StatusResponse.Type.SUCCESS.toString(),
				"Verify success response from getTax API");
		validateTaxForDiscountLessThan20(taxResponse, unitLevelProratedAdditionalCharges, unitLevelProratedDiscounts,
				unitMrp, expectedTaxType);
		sft.assertAll();
	}

	@Test(groups = { "regression", "taxCalculation",
			"Sanity" }, dataProvider = "itemLevelTaxCouponLessThan20OtherStateDP", dataProviderClass = GetItemLevelTaxAPI_DP.class)
	public void getItemLevelTaxVATForCouponLessThan20ForOtherState(long styleId, long destinationPincode,
			long sourceWarehouseId, String courierCode, int quantity, double unitMrp,
			List<OmsAdditionalChargesTaxRequestEntry> unitLevelProratedAdditionalCharges,
			List<OmsDiscountEntry> unitLevelProratedDiscounts, long sellerId, Date orderDate)
					throws JAXBException, IOException {
		SoftAssert sft = new SoftAssert();
		
		if(isGSTLive){
			 expectedTaxType = "IGST:CESS"; // For GST
		}else{
			 expectedTaxType = "CST"; //For VAT
		}
		

		TaxMasterServiceHelper taxMasterServiceHelper = new TaxMasterServiceHelper();

		OmsTaxResponse taxResponse = taxMasterServiceHelper.getItemLevelTaxPositive(styleId, destinationPincode,
				sourceWarehouseId, courierCode, quantity, unitMrp, unitLevelProratedAdditionalCharges,
				unitLevelProratedDiscounts, sellerId, orderDate, false,storeId);
		sft.assertEquals(taxResponse.getStatus().getStatusType().toString(), StatusResponse.Type.SUCCESS.toString(),
				"Verify success response from getTax API");
		validateTaxForDiscountLessThan20(taxResponse, unitLevelProratedAdditionalCharges, unitLevelProratedDiscounts,
				unitMrp, expectedTaxType);
		sft.assertAll();
	}

	@Test(groups = { "regression",
			"taxCalculation" }, dataProvider = "itemLevelTaxOtherSellerDP", dataProviderClass = GetItemLevelTaxAPI_DP.class)
	public void getItemLevelTaxVATForOtherSeller(long styleId, long destinationPincode, long sourceWarehouseId,
			String courierCode, int quantity, double unitMrp,
			List<OmsAdditionalChargesTaxRequestEntry> unitLevelProratedAdditionalCharges,
			List<OmsDiscountEntry> unitLevelProratedDiscounts, long sellerId, Date orderDate)
					throws JAXBException, IOException {
		SoftAssert sft = new SoftAssert();
		
		if(isGSTLive){
			 expectedTaxType = "CGST:SGST"; // For GST
		}else{
			 expectedTaxType = "VAT"; //For VAT
		}

		TaxMasterServiceHelper taxMasterServiceHelper = new TaxMasterServiceHelper();

		OmsTaxResponse taxResponse = taxMasterServiceHelper.getItemLevelTaxPositive(styleId, destinationPincode,
				sourceWarehouseId, courierCode, quantity, unitMrp, unitLevelProratedAdditionalCharges,
				unitLevelProratedDiscounts, sellerId, orderDate, false,storeId);
		sft.assertEquals(taxResponse.getStatus().getStatusType().toString(), StatusResponse.Type.SUCCESS.toString(),
				"Verify success response from getTax API");
		validateTaxForDiscountLessThan20(taxResponse, unitLevelProratedAdditionalCharges, unitLevelProratedDiscounts,
				unitMrp, expectedTaxType);

		sft.assertAll();
	}

	@Test(groups = { "regression",
			"taxCalculation" }, dataProvider = "itemLevelTaxCouponGreaterThan20DP", dataProviderClass = GetItemLevelTaxAPI_DP.class)
	public void getItemLevelTaxVATForCouponGreaterThan20(long styleId, long destinationPincode, long sourceWarehouseId,
			String courierCode, int quantity, double unitMrp,
			List<OmsAdditionalChargesTaxRequestEntry> unitLevelProratedAdditionalCharges,
			List<OmsDiscountEntry> unitLevelProratedDiscounts, long sellerId, Date orderDate)
					throws JAXBException, IOException {
		SoftAssert sft = new SoftAssert();
		
		if(isGSTLive){
			 expectedTaxType = "CGST:SGST:CESS"; // For GST
		}else{
			 expectedTaxType = "VAT"; //For VAT
		}
		
		TaxMasterServiceHelper taxMasterServiceHelper = new TaxMasterServiceHelper();

		OmsTaxResponse taxResponse = taxMasterServiceHelper.getItemLevelTaxPositive(styleId, destinationPincode,
				sourceWarehouseId, courierCode, quantity, unitMrp, unitLevelProratedAdditionalCharges,
				unitLevelProratedDiscounts, sellerId, orderDate, true,storeId);
		sft.assertEquals(taxResponse.getStatus().getStatusType().toString(), StatusResponse.Type.SUCCESS.toString(),
				"Verify success response from getTax API");
		validateTaxForDiscountGreaterThan20(taxResponse, unitLevelProratedAdditionalCharges, unitLevelProratedDiscounts,
				unitMrp, expectedTaxType);

		sft.assertAll();
	}

	@Test(groups = { "regression",
			"taxCalculation" }, dataProvider = "itemLevelTaxNegativeDP", dataProviderClass = GetItemLevelTaxAPI_DP.class)
	public void getItemLevelTaxNegative(Long styleId, Long destinationPincode, Long sourceWarehouseId,
			String courierCode, Integer quantity, Double unitMrp,
			List<OmsAdditionalChargesTaxRequestEntry> unitLevelProratedAdditionalCharges,
			List<OmsDiscountEntry> unitLevelProratedDiscounts, Long sellerId, Date orderDate)
					throws JAXBException, IOException {
		SoftAssert sft = new SoftAssert();

		TaxMasterServiceHelper taxMasterServiceHelper = new TaxMasterServiceHelper();

		OmsTaxResponse taxResponse = taxMasterServiceHelper.getItemLevelTaxPositive(styleId, destinationPincode,
				sourceWarehouseId, courierCode, quantity, unitMrp, unitLevelProratedAdditionalCharges,
				unitLevelProratedDiscounts, sellerId, orderDate, false,storeId);

		sft.assertEquals(taxResponse.getStatus().getStatusType().toString(), StatusResponse.Type.ERROR.toString(),
				"Verify Error response from getTax API");
		sft.assertAll();
	}

	@Test(groups = { "regression",
			"taxCalculation" }, dataProvider = "bulkItemLevelTaxDP", dataProviderClass = GetItemLevelTaxAPI_DP.class)
	public void getBulkItemLevelTaxVAT(long styleId, long destinationPincode, long sourceWarehouseId,
			String courierCode, int quantity, double unitMrp,
			List<OmsAdditionalChargesTaxRequestEntry> unitLevelProratedAdditionalCharges,
			List<OmsDiscountEntry> unitLevelProratedDiscounts, long sellerId, Date orderDate, long entityId,
			String entityType,Boolean vatRecoveredFromCustomer) throws JAXBException, IOException {
		SoftAssert sft = new SoftAssert();

		TaxMasterServiceHelper taxMasterServiceHelper = new TaxMasterServiceHelper();

		BulkOmsTaxResponse taxResponse = taxMasterServiceHelper.getBulkItemTax(styleId, destinationPincode,
				sourceWarehouseId, courierCode, quantity, unitMrp, unitLevelProratedAdditionalCharges,
				unitLevelProratedDiscounts, sellerId, orderDate, entityId, entityType,vatRecoveredFromCustomer,storeId);
		sft.assertEquals(taxResponse.getStatus().getStatusType().toString(), StatusResponse.Type.SUCCESS.toString(),
				"Verify success response from getTax API");
		sft.assertEquals(taxResponse.getData().size(), 4,
				"There should be 4 entries but found: " + taxResponse.getData().size());
		sft.assertAll();
	}

	@Test(groups = { "regression",
			"taxCalculation" }, dataProvider = "bulkItemLevelTaxNegativeDP", dataProviderClass = GetItemLevelTaxAPI_DP.class)
	public void getBulkItemLevelTaxVATNegative(Long styleId, Long destinationPincode, Long sourceWarehouseId,
			String courierCode, Integer quantity, Double unitMrp,
			List<OmsAdditionalChargesTaxRequestEntry> unitLevelProratedAdditionalCharges,
			List<OmsDiscountEntry> unitLevelProratedDiscounts, Long sellerId, Date orderDate, Long entityId,
			String entityType,Boolean vatRecoveredFromCustomer) throws JAXBException, IOException {
		SoftAssert sft = new SoftAssert();

		TaxMasterServiceHelper taxMasterServiceHelper = new TaxMasterServiceHelper();

		BulkOmsTaxResponse taxResponse = taxMasterServiceHelper.getBulkItemTax(styleId, destinationPincode,
				sourceWarehouseId, courierCode, quantity, unitMrp, unitLevelProratedAdditionalCharges,
				unitLevelProratedDiscounts, sellerId, orderDate, entityId, entityType,vatRecoveredFromCustomer,storeId);
		sft.assertEquals(taxResponse.getStatus().getStatusType().toString(), StatusResponse.Type.ERROR.toString(),
				"Verify success response from getTax API");
		sft.assertAll();
	}

	@Test(groups = { "regression",
			"taxCalculation" }, dataProvider = "getBulkTaxCustomerGSTDP", dataProviderClass = GetItemLevelTaxAPI_DP.class)
	public void getBulkTaxCustomerGSTSameInput(List<HashMap<String, String>> inputList,int successCount,int errorCount)
			throws JAXBException, IOException {

		TaxMasterServiceHelper taxMasterServiceHelper = new TaxMasterServiceHelper();
		BulkGSTTaxResponse response = taxMasterServiceHelper.getBulkTaxCustomerGST(inputList);
		validateBulkGSTResponse(response, successCount, errorCount);
	}

	@Test(groups = { "regression",
			"taxCalculation" }, dataProvider = "getBulkTaxCustomerGSTDifferentInputDP", dataProviderClass = GetItemLevelTaxAPI_DP.class)
	public void getBulkTaxCustomerGSTDifferentInput(List<HashMap<String, String>> inputList,int successCount,int errorCount)
			throws JAXBException, IOException {

		TaxMasterServiceHelper taxMasterServiceHelper = new TaxMasterServiceHelper();
		BulkGSTTaxResponse response = taxMasterServiceHelper.getBulkTaxCustomerGST(inputList);
		validateBulkGSTResponse(response, successCount, errorCount);

	}

	@Test(groups = { "regression",
			"taxCalculation" }, dataProvider = "getBulkTaxCustomerGSTForThresHoldLimitDP", dataProviderClass = GetItemLevelTaxAPI_DP.class)
	public void getBulkTaxCustomerGSTThresHoldLimitInput(List<HashMap<String, String>> inputList,
			Boolean isThreasHoldLimitCrossed,int successCount,int errorCount) throws JAXBException, IOException {

		TaxMasterServiceHelper taxMasterServiceHelper = new TaxMasterServiceHelper();
		BulkGSTTaxResponse response = taxMasterServiceHelper.getBulkTaxCustomerGST(inputList);
		getBulkTaxCustomerGSTThresHoldLimitInput(response,isThreasHoldLimitCrossed,successCount,errorCount);

	}
	


	@Test(groups = { "regression",
			"taxCalculation" }, dataProvider = "getBulkTaxCustomerGSTNegativeDP", dataProviderClass = GetItemLevelTaxAPI_DP.class)
	public void getBulkTaxCustomerGSTNegative(List<HashMap<String, String>> inputList,int successCount,int errorCount)
			throws JAXBException, IOException {

		TaxMasterServiceHelper taxMasterServiceHelper = new TaxMasterServiceHelper();
		BulkGSTTaxResponse response = taxMasterServiceHelper.getBulkTaxCustomerGST(inputList);
		validateBulkGSTResponse(response, successCount, errorCount);
	}
	
	@Test(groups = { "regression",
			"taxCalculation" }, dataProvider = "getBulkTaxCessDP", dataProviderClass = GetItemLevelTaxAPI_DP.class,enabled=false)
	public void getBulkTaxCessSameInput(List<HashMap<String, String>> inputList,int successCountWithData,int successCountWithoutData,int errorCount) throws JAXBException, IOException {

		TaxMasterServiceHelper taxMasterServiceHelper = new TaxMasterServiceHelper();
		BulkCessResponse response = taxMasterServiceHelper.getBulkTaxCess(inputList);
		validateBulkCessResponse(response,successCountWithData,successCountWithoutData,errorCount);
	}

	@Test(groups = { "regression",
			"taxCalculation" }, dataProvider = "getBulkTaxCessDifferentInputDP", dataProviderClass = GetItemLevelTaxAPI_DP.class,enabled=false)
	public void getBulkTaxCessDifferentInput(List<HashMap<String, String>> inputList,int successCountWithData,int successCountWithoutData,int errorCount)
			throws JAXBException, IOException {

		TaxMasterServiceHelper taxMasterServiceHelper = new TaxMasterServiceHelper();
		BulkCessResponse response = taxMasterServiceHelper.getBulkTaxCess(inputList);
		validateBulkCessResponse(response,successCountWithData,successCountWithoutData,errorCount);
		
	}

	@Test(groups = { "regression",
			"taxCalculation" }, dataProvider = "getBulkTaxCessForThresHoldLimitDP", dataProviderClass = GetItemLevelTaxAPI_DP.class,enabled=false)
	public void getBulkTaxCessThresHoldLimitInput(List<HashMap<String, String>> inputList,
			Boolean isThreasHoldLimitCrossed,int successCountWithData,int successCountWithoutData,int errorCount) throws JAXBException, IOException {
		
		TaxMasterServiceHelper taxMasterServiceHelper = new TaxMasterServiceHelper();
		BulkCessResponse response = taxMasterServiceHelper.getBulkTaxCess(inputList);
		validateBulkCessResponseThresHoldLimitInput(isThreasHoldLimitCrossed,response,successCountWithData,successCountWithoutData,errorCount);
		
	}

	
	@Test(groups = { "regression",
			"taxCalculation" }, dataProvider = "getBulkTaxCessNegativeDP", dataProviderClass = GetItemLevelTaxAPI_DP.class,enabled=false)
	public void getBulkTaxCessNegative(List<HashMap<String, String>> inputList,int successCountWithData,int successCountWithoutData,int errorCount)
			throws JAXBException, IOException {
		
		TaxMasterServiceHelper taxMasterServiceHelper = new TaxMasterServiceHelper();
		BulkCessResponse response = taxMasterServiceHelper.getBulkTaxCess(inputList);
		validateBulkCessResponse(response,successCountWithData,successCountWithoutData,errorCount);
	}
	
	
	@Test(groups = { "regression",
			"taxCalculation" }, dataProvider = "getTaxCessPositiveDP", dataProviderClass = GetItemLevelTaxAPI_DP.class,enabled=false)
	public void getTaxCessPositive(String hsnCode,String material,Double mrpValue,Date date,Double expectedCessValue,Boolean isTaxConfigured) throws JAXBException, IOException {
		TaxMasterServiceHelper taxMasterServiceHelper = new TaxMasterServiceHelper();
		TaxResponse responseMap = taxMasterServiceHelper.getTaxCess(hsnCode,material,mrpValue,date);
		validateGetTaxForCess(responseMap,isTaxConfigured,expectedCessValue);
	}
	
	@Test(groups = { "regression",
			"taxCalculation" }, dataProvider = "getTaxCessNegativeDP", dataProviderClass = GetItemLevelTaxAPI_DP.class)
	public void getTaxCessNegative(String hsnCode, String material, Double mrpValue, Date date) throws JAXBException, IOException {

		TaxMasterServiceHelper taxMasterServiceHelper = new TaxMasterServiceHelper();
		TaxResponse responseMap = taxMasterServiceHelper.getTaxCess(hsnCode, material, mrpValue, date);
		validateGetTaxCessErrorResponse(responseMap);
	}

	@Test(groups = { "regression",
			"taxCalculation" }, dataProvider = "getAllTaxCessDP", dataProviderClass = GetItemLevelTaxAPI_DP.class,enabled=false)
	public void getAllTaxCess(String hsnCode, String material, Double mrpValue, Date date,Boolean isTaxConfigured,ArrayList<Double> expectedCessValues )
			throws JAXBException, IOException {
		
		TaxMasterServiceHelper taxMasterServiceHelper = new TaxMasterServiceHelper();
		MultiRuleTaxResponse responseMap = taxMasterServiceHelper.getAllTaxCess(hsnCode,material,mrpValue,date);
		validateGetAllTaxForCess(isTaxConfigured,responseMap,expectedCessValues);
	}
	
	/**
	 * Validate error response for GetTaxCes
	 * @param responseMap
	 */
	public void validateGetTaxCessErrorResponse(TaxResponse responseMap){
		SoftAssert sft = new SoftAssert();
		sft.assertEquals(responseMap.getStatus().getStatusType().toString(), Type.ERROR.toString());
		sft.assertAll();
	}
	
	/**
	 * Validate response for validateBulkCessResponseThresHoldLimitInput
	 * @param isThreasHoldLimitCrossed
	 * @param response
	 * @param successCountWithData
	 * @param successCountWithoutData
	 * @param errorCount
	 */
	private void validateBulkCessResponseThresHoldLimitInput(Boolean isThreasHoldLimitCrossed, BulkCessResponse response,
			int successCountWithData, int successCountWithoutData, int errorCount) {
		SoftAssert sft = new SoftAssert();
		if (isThreasHoldLimitCrossed) {
			sft.assertEquals(response.getStatus().getStatusType().name(), Type.ERROR.toString(),
					"There should be error after exceeding payload limit.");
		} else {
			validateBulkCessResponse(response, successCountWithData, successCountWithoutData, errorCount);
		}
		sft.assertAll();
	}

	
	/**
	 * Validate function for getTaxForCess response
	 * @param responseMap
	 * @param isTaxConfigured
	 * @param expectedCessValue
	 */
	public void validateGetTaxForCess(TaxResponse responseMap,Boolean isTaxConfigured,Double expectedCessValue){
		SoftAssert sft = new SoftAssert();
		if(isTaxConfigured==false){
			sft.assertEquals(responseMap.getStatus().getStatusType().toString(), Type.SUCCESS.toString(),"There should be success in response");
			sft.assertTrue(responseMap.getData()==null,"There should not be taxEntry for invalid hsncode");
		}else{
			sft.assertTrue(responseMap.getData().size()>0,"There should be taxEntry for configured hsncode");
			sft.assertEquals(responseMap.getStatus().getStatusType().toString(), Type.SUCCESS.toString(),"There should be success in response");
			validateGetTaxForCessHelper(responseMap, expectedCessValue);
		}
		sft.assertAll();
	}
	
	
	/**
	 * Validate function for response for threshold limit
	 * @param response
	 * @param isThreasHoldLimitCrossed
	 * @param successCount
	 * @param errorCount
	 */
	public void getBulkTaxCustomerGSTThresHoldLimitInput(BulkGSTTaxResponse response,Boolean isThreasHoldLimitCrossed,int successCount,int errorCount){
		SoftAssert sft = new SoftAssert();
		
		if (isThreasHoldLimitCrossed) {
			 sft.assertEquals(response.getStatus().getStatusType().name(), Type.ERROR.toString(),"There should be error in response");

		} else {
			 sft.assertEquals(response.getStatus().getStatusType().name(), Type.SUCCESS.toString(),"There should be success in response");
			 validateBulkGSTResponse(response, successCount, errorCount);

		}
		sft.assertAll();
	}
	
	/**
	 * Validate function for response
	 * @param isTaxConfigured
	 * @param responseMap
	 * @param expectedCessValues
	 */
	public void validateGetAllTaxForCess(Boolean isTaxConfigured,MultiRuleTaxResponse responseMap,ArrayList<Double> expectedCessValues){
		SoftAssert sft = new SoftAssert();
		if(isTaxConfigured==false){
			sft.assertEquals(responseMap.getStatus().getStatusType().toString(), Type.SUCCESS.toString(),"There should be success in response");
			sft.assertTrue(responseMap.getData()==null,"There should not be taxEntry for invalid hsncode");
		}else{
			
			sft.assertTrue(responseMap.getData().size()>0,"There should be taxEntry for configured hsncode");
			sft.assertEquals(responseMap.getStatus().getStatusType().toString(), Type.SUCCESS.toString(),"There should be success in response");
			validateGetAllTaxForCessHelper(responseMap,expectedCessValues);
		}
		sft.assertAll();
	}

	
	/**
	 * Validate Tax for Cess
	 * @param responseMap
	 * @param expectedResult
	 */
	public void validateGetTaxForCessHelper(TaxResponse responseMap,Double expectedCessValue){
		SoftAssert sft = new SoftAssert();

		List<com.myntra.taxmaster.client.entry.TaxEntry> cesstaxList = responseMap.getData();
		for(com.myntra.taxmaster.client.entry.TaxEntry taxEntry:cesstaxList){
			sft.assertEquals(taxEntry.getTaxType().name(), TaxType.CESS.name());
			sft.assertEquals(taxEntry.getTaxRate(), expectedCessValue);
		}
		sft.assertAll();
	}
	
	/**
	 * Validate BulkCess for Cess
	 * @param response
	 * @param successCountWithData
	 * @param successCountWithoutData
	 * @param errorCount
	 */
	public void validateBulkCessResponse(BulkCessResponse response,int successCountWithData,int successCountWithoutData,int errorCount){
		SoftAssert sft = new SoftAssert();
		
		sft.assertEquals(response.getStatus().getStatusType().name(), Type.SUCCESS.toString(),"There should be success in response");
		
		List<CessRequestEntry> cessRequestEntry = response.getData();
		int actualSuccessCountWithData = 0;
		int actualSuccessCountWithoutData = 0;
		int actualErrorCount = 0;
		for(CessRequestEntry cessEntry:cessRequestEntry){
			if(cessEntry.getStatusType().name().equalsIgnoreCase("SUCCESS")&&cessEntry.getTaxEntryList()==null){
				actualSuccessCountWithoutData++;
			}else if(cessEntry.getStatusType().name().equalsIgnoreCase("SUCCESS") && cessEntry.getTaxEntryList()!=null){
				actualSuccessCountWithData++;
			}else if(cessEntry.getStatusType().name().equalsIgnoreCase("ERROR")){
				actualErrorCount++;
			}
		}
		
		sft.assertEquals(successCountWithData, actualSuccessCountWithData,"Total successCount response is incorrect");
		sft.assertEquals(successCountWithoutData, actualSuccessCountWithoutData,"Total successCountWithoutData response is incorrect");
		sft.assertEquals(errorCount, actualErrorCount,"Total errorCount response is incorrect");
		sft.assertAll();
	}
	
	/**
	 * Validate BulkTax for CustomerGST
	 * @param response
	 * @param successCount
	 * @param errorCount
	 */
	public void validateBulkGSTResponse(BulkGSTTaxResponse response,int successCount,int errorCount){
		SoftAssert sft = new SoftAssert();
		int actualSuccessCount = 0;
		int actualErrorCount = 0;
		sft.assertEquals(response.getStatus().getStatusType().name(), Type.SUCCESS.toString(),"There should be success in response");
		List<CustomerGSTRequestEntry> customerGSTResponseList = response.getData();
		for(CustomerGSTRequestEntry customerGSTResponse:customerGSTResponseList){
			String status = customerGSTResponse.getStatusType().name();
			List<com.myntra.taxmaster.client.entry.TaxEntry> taxEntryList = customerGSTResponse.getTaxEntryList();
			if(status.equalsIgnoreCase(Type.ERROR.toString()) && taxEntryList.isEmpty()){
				actualErrorCount++;
			}else if(status.equalsIgnoreCase(Type.SUCCESS.toString()) && !taxEntryList.isEmpty()){
				actualSuccessCount++;
			}
		}
		
		sft.assertEquals(successCount, actualSuccessCount,"Total successCount response is incorrect Actual:"+actualSuccessCount);
		sft.assertEquals(errorCount, actualErrorCount,"Total errorCount response is incorrect Actual:"+actualErrorCount);
		sft.assertAll();
	}

	
	/**
	 * Validate GetALLTax for Cess
	 * @param responseMap
	 * @param expectedResult
	 */
	public void validateGetAllTaxForCessHelper(MultiRuleTaxResponse responseMap,ArrayList<Double> expectedCessValues){
		SoftAssert sft = new SoftAssert();
		
		ArrayList<Double> taxRateActual = new ArrayList<>();
		
		List<TaxEntries> cesstaxEntriesList = responseMap.getData();
		for(TaxEntries taxEntries:cesstaxEntriesList){
			com.myntra.taxmaster.client.entry.TaxEntry taxEntry = taxEntries.getTaxEntries().get(0) ;
			sft.assertEquals((taxEntry).getTaxType().name(), TaxType.CESS.name());
			taxRateActual.add(taxEntry.getTaxRate());
		}
		
		sft.assertTrue(checkIfListIsSame(expectedCessValues,taxRateActual),"TaxRates should be same");
		sft.assertAll();
	}

	/**
	 * Compare Two List
	 * @param expectedList
	 * @param actualList
	 * @return
	 */
	public  boolean checkIfListIsSame(List<Double> expectedList, List<Double> actualList){     
	    if (expectedList == null && actualList == null){
	        return true;
	    }

	    if((expectedList == null && actualList != null) 
	      || expectedList != null && actualList == null
	      || expectedList.size() != actualList.size()){
	        return false;
	    }

	    Collections.sort(expectedList);
	    Collections.sort(actualList);      
	    return expectedList.equals(actualList);
	}

	/**
	 * Validate Tax Data for discount applied less than 20 percent
	 * @param taxResponse
	 * @param unitLevelProratedAdditionalCharges
	 * @param unitLevelProratedDiscounts
	 * @param unitMrp
	 * @param expectedTaxType
	 */
	public void validateTaxForDiscountLessThan20(OmsTaxResponse taxResponse,
			List<OmsAdditionalChargesTaxRequestEntry> unitLevelProratedAdditionalCharges,
			List<OmsDiscountEntry> unitLevelProratedDiscounts, double unitMrp, String expectedTaxType) {
		SoftAssert sft = new SoftAssert();

		Double totalTaxRate = 0.0;

		for (OmsTaxEntry omsTaxEntry : taxResponse.getData()) {
			totalTaxRate += omsTaxEntry.getTaxRate();
		}
		String[] taxTypes = expectedTaxType.split(":");
		int i = 0;
		for (OmsTaxEntry omsTaxEntry : taxResponse.getData()) {

			Double actualTaxRate = omsTaxEntry.getTaxRate();
			System.out.println("Actual TaxRate:" + actualTaxRate);
			Double additionalCharge = unitLevelProratedAdditionalCharges.get(0).getAmount();
			Double discount = unitLevelProratedDiscounts.get(0).getAmount();
			System.out.println("Discount:" + discount);
			Double taxableAmountExpected = govttax.getTaxableAmountForDiscountLessThan20(unitMrp, additionalCharge,
					discount, totalTaxRate);
			Double taxableAmountActual = omsTaxEntry.getUnitTaxableAmount();
			System.out.println("Taxable Amount:" + taxableAmountExpected + " " + taxableAmountActual);
			sft.assertEquals(Math.round(taxableAmountActual), Math.round(taxableAmountExpected),
					"Verify Taxable amount should be same Expected:" + taxableAmountExpected + " but Actual:"
							+ taxableAmountActual);
			String taxTypeActual = omsTaxEntry.getTaxType().name().toString();
			sft.assertEquals(taxTypeActual, taxTypes[i], "verify TaxType in response");
			Double unitTaxAmountActual = omsTaxEntry.getUnitTaxAmount();
			Double unitTaxAmountExpected = ((taxableAmountExpected * actualTaxRate / 100) * 100) / 100.00;
			System.out.println("unitTaxAmount: " + unitTaxAmountActual + " " + unitTaxAmountExpected);
			sft.assertEquals(Math.round(unitTaxAmountActual), Math.round(unitTaxAmountExpected),
					"Verify unitTaxAmount is correct");
			i++;
			sft.assertAll();

		}

	}

	/**
	 * Validate Tax Data for discount applied greater than 20 percent
	 * @param taxResponse
	 * @param unitLevelProratedAdditionalCharges
	 * @param unitLevelProratedDiscounts
	 * @param unitMrp
	 * @param expectedTaxType
	 */
	public void validateTaxForDiscountGreaterThan20(OmsTaxResponse taxResponse,
			List<OmsAdditionalChargesTaxRequestEntry> unitLevelProratedAdditionalCharges,
			List<OmsDiscountEntry> unitLevelProratedDiscounts, double unitMrp, String expectedTaxType) {
		SoftAssert sft = new SoftAssert();

		Double totalTaxRate = 0.0;

		for (OmsTaxEntry omsTaxEntry : taxResponse.getData()) {
			totalTaxRate += omsTaxEntry.getTaxRate();
		}
		String[] taxTypes = expectedTaxType.split(":");
		int i = 0;
		for (OmsTaxEntry omsTaxEntry : taxResponse.getData()) {

			Double actualTaxRate = omsTaxEntry.getTaxRate();
			System.out.println("Actual TaxRate:" + actualTaxRate);
			Double additionalCharge = unitLevelProratedAdditionalCharges.get(0).getAmount();
			Double discount = unitLevelProratedDiscounts.get(0).getAmount();
			System.out.println("Discount:" + discount);
			Double taxableAmountExpected = govttax.getTaxableAmountForDiscountGreaterThan20(unitMrp, additionalCharge,
					discount, totalTaxRate);
			Double taxableAmountActual = omsTaxEntry.getUnitTaxableAmount();
			System.out.println("Taxable Amount:" + taxableAmountExpected + " " + taxableAmountActual);
			sft.assertEquals(Math.round(taxableAmountActual), Math.round(taxableAmountExpected),
					"Verify Taxable amount should be same Expected:" + taxableAmountExpected + " but Actual:"
							+ taxableAmountActual);
			String taxTypeActual = omsTaxEntry.getTaxType().name().toString();
			sft.assertEquals(taxTypeActual, taxTypes[i], "verify TaxType in response");
			Double unitTaxAmountActual = omsTaxEntry.getUnitTaxAmount();
			Double unitTaxAmountExpected = ((taxableAmountExpected * actualTaxRate / 100) * 100) / 100.00;
			System.out.println("unitTaxAmount: " + unitTaxAmountActual + " " + unitTaxAmountExpected);
			sft.assertEquals(Math.round(unitTaxAmountActual), Math.round(unitTaxAmountExpected),
					"Verify unitTaxAmount is correct");
			i++;
			sft.assertAll();

		}

	}

}
