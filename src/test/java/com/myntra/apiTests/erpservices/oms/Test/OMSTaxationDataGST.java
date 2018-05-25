package com.myntra.apiTests.erpservices.oms.Test;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.bind.JAXBException;

import com.myntra.apiTests.common.Constants.ReleaseStatus;
import com.myntra.apiTests.common.ProcessOrder.Service.ProcessRelease;
import com.myntra.apiTests.common.entries.ReleaseEntry;
import com.myntra.apiTests.common.entries.ReleaseEntryList;
import com.myntra.apiTests.erpservices.oms.OMSServiceHelper;
import com.myntra.apiTests.erpservices.oms.dp.GovtTaxDP;
import com.myntra.apiTests.erpservices.atp.ATPServiceHelper;
import com.myntra.apiTests.erpservices.sellerapis.SellerConfig;
import org.apache.log4j.Logger;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.myntra.apiTests.end2end.End2EndHelper;
import com.myntra.apiTests.erpservices.ims.IMSServiceHelper;
import com.myntra.lordoftherings.Initialize;
import com.myntra.oms.client.entry.OrderEntry;
import com.myntra.oms.client.entry.OrderLineEntry;
import com.myntra.oms.client.entry.OrderReleaseEntry;
import com.myntra.oms.client.response.OrderReleaseResponse;
import com.myntra.oms.common.enums.LineMovementAction;
import com.myntra.test.commons.testbase.BaseTest;

public class OMSTaxationDataGST extends BaseTest {

	static Initialize init = new Initialize("/Data/configuration");
	String login = OMSTCConstants.LoginAndPassword.GovtTaxCalculationTestLogin;
	String password = OMSTCConstants.LoginAndPassword.GovtTaxCalculationTestPassword;
	String addressId = OMSTCConstants.Pincodes.PINCODE_560068;
	String coupon_More_Than_20="govttax";
	String coupon_Less_Than_20="govttax1";
	String coupon_More_Than_20_Fox8="vattax12";
    private static Long vectorSellerID;
    private static Logger log = Logger.getLogger(OMSTaxationDataGST.class);
	
	End2EndHelper end2EndHelper = new End2EndHelper();
	OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
	ATPServiceHelper atpServiceHelper = new ATPServiceHelper();
	IMSServiceHelper imsServiceHelper = new IMSServiceHelper();	
	String orderID;
	Boolean isGSTLive = false; //feature gate for Tax make false after GST is live
	//Boolean isGSTLive = false; //feature gate for Tax make false after GST is live
	private SoftAssert sft;
	private String supplyTypeOnHand="ON_HAND";
	private OrderEntry orderEntry;
	private String orderReleaseId;
	private enum entityType{
		ORDER_LINE,
		ORDER_RELEASE,
		ORDER
	}
	
	@BeforeClass(alwaysRun = true)
	public void testBeforeClass() throws SQLException, UnsupportedEncodingException, JAXBException {
		omsServiceHelper.refreshOMSApplicationPropertyCache();
        omsServiceHelper.refreshOMSJVMCache();
        vectorSellerID = SellerConfig.getSellerID(SellerConfig.SellerName.HANDH);
	}
	
	
	
	@Test(enabled = true,groups={"regression","taxCalculation"},description="TC039:Create single Line order assigned to Bangalore (same state) and Verify GST(CGST+SGST) is applied and available in Taxation Data store")
	public void CreateSingleLineOrderAssignedToBangaloreAndVerifyGST_CGSTandSGST() throws Exception{
		ProcessRelease processRelease = new ProcessRelease();
		sft = new SoftAssert();
		
		HashMap<String,String> expectedResult = new HashMap<String, String>();
		expectedResult.put("taxType", "CGST:SGST");
		expectedResult.put("taxRate", "9.000:9.000");
		
		String skuId[] = { OMSTCConstants.OtherSkus.skuId_3831+":2" };
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3831+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":10000:0:"+vectorSellerID+":1"}, supplyTypeOnHand);
		imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3831+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":10000:0:"+vectorSellerID},
				supplyTypeOnHand);

		orderID = end2EndHelper.createOrder(login, password, addressId, skuId, coupon_Less_Than_20, false, false, false, "", false);
		log.info("OrderID: "+orderID);
		omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
		orderEntry = omsServiceHelper.getOrderEntry(orderID);
		orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();
		omsServiceHelper.stampGovtTaxForVectorSuccess(orderReleaseId);
		String lineId = orderEntry.getOrderReleases().get(0).getOrderLines().get(0).getId().toString();
		OrderLineEntry orderLineEntry = omsServiceHelper.getOrderLineEntry(lineId);
		
		
		//This function checks if Tax is applied correctly in Taxation Data store Table
		validateTaxDataInDBGSTLessThan20(expectedResult,lineId);
		//This function checkes if Tax data returened from service is same as available in Taxation Data store
		validateTaxData(entityType.ORDER_LINE.toString(),lineId);

		List<ReleaseEntry> releaseEntries = new ArrayList<>();
		releaseEntries.add(new ReleaseEntry.Builder(orderReleaseId, ReleaseStatus.DL).build());
		ReleaseEntryList releaseEntryList= new ReleaseEntryList(releaseEntries);
		processRelease.processReleaseToStatus(releaseEntryList);


		omsServiceHelper.checkReleaseStatusForOrder(orderID, "DL");
		sft.assertAll();
		
	}
	
	@Test(enabled = true,groups={"regression","taxCalculation"},description="TC040:Create single Line order assigned to Gurgaon(different state) and Verify GST(IGST) is applied and available in Taxation Data store")
	public void CreateSingleLineOrderAssignedToGurgaonAndVerifyGST_IGST() throws Exception{
		sft = new SoftAssert();
		
		HashMap<String,String> expectedResult = new HashMap<String, String>();
		expectedResult.put("taxType", "IGST");
		expectedResult.put("taxRate", "18.000");
		
		String skuId[] = { OMSTCConstants.OtherSkus.skuId_3832+":1" };
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3832+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":10000:0:"+vectorSellerID+":1"}, supplyTypeOnHand);
		imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3832+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":10000:0:"+vectorSellerID},
				supplyTypeOnHand);

		orderID = end2EndHelper.createOrder(login, password, addressId, skuId, coupon_Less_Than_20, false, false, false, "", false);
		log.info("OrderID: "+orderID);
		omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
		orderEntry = omsServiceHelper.getOrderEntry(orderID);
		orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();
		omsServiceHelper.stampGovtTaxForVectorSuccess(orderReleaseId);
		String lineId = orderEntry.getOrderReleases().get(0).getOrderLines().get(0).getId().toString();
		OrderLineEntry orderLineEntry = omsServiceHelper.getOrderLineEntry(lineId);
		
		
		//This function checks if Tax is applied correctly in Taxation Data store Table
		validateTaxDataInDBGSTLessThan20(expectedResult,lineId);
		//This function checkes if Tax data returened from service is same as available in Taxation Data store
		validateTaxData(entityType.ORDER_LINE.toString(),lineId);
		sft.assertAll();
		
	}
	
	@Test(enabled = true,groups={"regression","taxCalculation"},description="TC041:Create multiple Line order assigned to Gurgaon(different state) and Bangalore(same state) both and verify GST applied and available in Taxation Data store")
	public void CreateMultiLineOrderAssignedToBangaloreAndGurGaonAndVerifyGST() throws Exception{
		sft = new SoftAssert();
		
		String skuId[] = { OMSTCConstants.OtherSkus.skuId_3843+":2", OMSTCConstants.OtherSkus.skuId_3844+":2" };
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3843+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":10000:0:"+vectorSellerID+":1",
				OMSTCConstants.OtherSkus.skuId_3844+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":10000:0:"+vectorSellerID+":1"}, supplyTypeOnHand);
		imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3843+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":10000:0:"+vectorSellerID,
				OMSTCConstants.OtherSkus.skuId_3844+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":10000:0:"+vectorSellerID},
				supplyTypeOnHand);

		orderID = end2EndHelper.createOrder(login, password, addressId, skuId, "", false, false, false, "", false);
		log.info("OrderID: "+orderID);
		omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
		orderEntry = omsServiceHelper.getOrderEntry(orderID);
		
		for(OrderReleaseEntry orderReleaseEntry:orderEntry.getOrderReleases()){
			HashMap<String,String> expectedResult = new HashMap<String, String>();
			orderReleaseId = orderReleaseEntry.getId().toString();
			omsServiceHelper.stampGovtTaxForVectorSuccess(orderReleaseId);
			String lineId = orderReleaseEntry.getOrderLines().get(0).getId().toString();
			OrderLineEntry orderLineEntry = omsServiceHelper.getOrderLineEntry(lineId);
			if(orderReleaseEntry.getWarehouseId()==36){
				expectedResult.put("taxType", "CGST:SGST");
				expectedResult.put("taxRate", "9.000:9.000");
			}else if(orderReleaseEntry.getWarehouseId()==28){
				expectedResult.put("taxType", "IGST");
				expectedResult.put("taxRate", "18.000");
			}
			validateTaxDataInDBGSTLessThan20(expectedResult,lineId);
			//This function checkes if Tax data returened from service is same as available in Taxation Data store
			validateTaxData(entityType.ORDER_LINE.toString(),lineId);
						
		}
		
		sft.assertAll();
		
	}
	
	@Test(enabled = true,groups={"regression","taxCalculation"},description="TC042:1 :Create single Line order assigned to Bangalore(same state) and Verify GST(CGST+SGST) is applied and available in Taxation Data store and Line additional Details.Reassign warehouse to Gurgaon(different state) and verify GST(IGST) is applied.")
	public void CreateSingleLineOrderBangaloreAndVerifyGSTAfterReassignment() throws Exception{
		sft = new SoftAssert();

		HashMap<String,String> expectedResult = new HashMap<String, String>();
		expectedResult.put("taxType", "CGST:SGST");
		expectedResult.put("taxRate", "9.000:9.000");

		String skuId[] = { OMSTCConstants.OtherSkus.skuId_1243741+":1" };
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_1243741+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":10000:0:"+vectorSellerID+":1"}, supplyTypeOnHand);
		imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_1243741+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":10000:0:"+vectorSellerID},
				supplyTypeOnHand);

		orderID = end2EndHelper.createOrder(login, password, addressId, skuId, coupon_Less_Than_20, false, false, false, "", false);
		log.info("OrderID: "+orderID);
		omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
		orderEntry = omsServiceHelper.getOrderEntry(orderID);
		OrderReleaseEntry orderReleaseEntry = orderEntry.getOrderReleases().get(0);
		orderReleaseId = orderReleaseEntry.getId().toString();
		omsServiceHelper.stampGovtTaxForVectorSuccess(orderReleaseId);
		String lineId = orderReleaseEntry.getOrderLines().get(0).getId().toString();

		//This function checks if Tax is applied correctly in Taxation Data store Table
		validateTaxDataInDBGSTLessThan20(expectedResult,lineId);
		//This function checkes if Tax data returened from service is same as available in Taxation Data store
		validateTaxData(entityType.ORDER_LINE.toString(),lineId);

		//Reassign to new warehouse 36
		Integer wareHouseId = 28;
		String corrierCode = String.valueOf(orderReleaseEntry.getCourierCode());
		String LineIDAndQuantity[] = { "" + lineId + ":1"};
		imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_1243741+":"+wareHouseId+":10000:0:"+vectorSellerID},
				supplyTypeOnHand);


		OrderReleaseResponse orderReleaseResponse=omsServiceHelper.splitOrder(orderReleaseId, wareHouseId.intValue(), LineIDAndQuantity, corrierCode, LineMovementAction.REASSIGN_WAREHOUSE);
		sft.assertEquals(orderReleaseResponse.getStatus().getStatusType().toString(), "SUCCESS","Status code should be the same.");

		orderReleaseEntry = orderEntry.getOrderReleases().get(0);
		lineId = orderReleaseEntry.getOrderLines().get(0).getId().toString();

		omsServiceHelper.stampGovtTaxForVectorSuccess(orderReleaseId);

		expectedResult.put("taxType", "IGST");
		expectedResult.put("taxRate", "18.000");

		//This function checks if Tax is applied correctly in Taxation Data store Table
		validateTaxDataInDBGSTLessThan20(expectedResult,lineId);
		//This function checkes if Tax data returened from service is same as available in Taxation Data store
		validateTaxData(entityType.ORDER_LINE.toString(),lineId);


		sft.assertAll();

	}
	
	@Test(enabled = true,groups={"regression","taxCalculation"},description="TC042:2 :Create single Line order assigned to Bangalore(same state) and Verify GST(CGST+SGST) is applied and available in Taxation Data store and Line additional Details.Reassign warehouse to Gurgaon(different state) and verify GST(IGST) is applied for coupon more than 20.")
	public void CreateSingleLineOrderBangaloreAndVerifyGSTAfterReassignmentCouponGreaterThan20() throws Exception{
		sft = new SoftAssert();

		HashMap<String,String> expectedResult = new HashMap<String, String>();
		expectedResult.put("taxType", "CGST:SGST");
		expectedResult.put("taxRate", "9.000:9.000");

		String skuId[] = { OMSTCConstants.OtherSkus.skuId_1243742+":1" };
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_1243742+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":10000:0:"+vectorSellerID+":1"}, supplyTypeOnHand);
		imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_1243742+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":10000:0:"+vectorSellerID},
				supplyTypeOnHand);

		orderID = end2EndHelper.createOrder(login, password, addressId, skuId, coupon_More_Than_20, false, false, false, "", false);
		log.info("OrderID: "+orderID);
		omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
		orderEntry = omsServiceHelper.getOrderEntry(orderID);
		OrderReleaseEntry orderReleaseEntry = orderEntry.getOrderReleases().get(0);
		orderReleaseId = orderReleaseEntry.getId().toString();
		omsServiceHelper.stampGovtTaxForVectorSuccess(orderReleaseId);
		String lineId = orderReleaseEntry.getOrderLines().get(0).getId().toString();

		//This function checks if Tax is applied correctly in Taxation Data store Table
		validateTaxDataInDBGSTLessThan20(expectedResult,lineId);
		//This function checkes if Tax data returened from service is same as available in Taxation Data store
		validateTaxData(entityType.ORDER_LINE.toString(),lineId);

		//Reassign to new warehouse 36
		Integer wareHouseId = 28;
		String corrierCode = String.valueOf(orderReleaseEntry.getCourierCode());
		String LineIDAndQuantity[] = { "" + lineId + ":1"};
		imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_1243742+":"+wareHouseId+":10000:0:"+vectorSellerID},
				supplyTypeOnHand);


		OrderReleaseResponse orderReleaseResponse=omsServiceHelper.splitOrder(orderReleaseId, wareHouseId.intValue(), LineIDAndQuantity, corrierCode, LineMovementAction.REASSIGN_WAREHOUSE);
		sft.assertEquals(orderReleaseResponse.getStatus().getStatusType().toString(), "SUCCESS","Status code should be the same.");

		orderReleaseEntry = orderEntry.getOrderReleases().get(0);
		lineId = orderReleaseEntry.getOrderLines().get(0).getId().toString();

		omsServiceHelper.stampGovtTaxForVectorSuccess(orderReleaseId);

		expectedResult.put("taxType", "IGST");
		expectedResult.put("taxRate", "18.000");

		//This function checks if Tax is applied correctly in Taxation Data store Table
		validateTaxDataInDBGSTMoreThan20(expectedResult,lineId);
		//This function checkes if Tax data returened from service is same as available in Taxation Data store
		validateTaxData(entityType.ORDER_LINE.toString(),lineId);


		sft.assertAll();

	}


	
	@Test(enabled = true,groups={"regression","taxCalculation"},description="TC043:Create single Line order assigned to Gurgaon(different state) and Verify GST(IGST) is applied and available in Taxation Data store and Line additional Details.Reassign warehouse to Bangalore(same state) and verify GST(CGST+SGST) is applied.")
	public void CreateSingleLineOrderAssignedToGurgaonAndVerifyGSTAfterReassignment() throws Exception{
		sft = new SoftAssert();
		
		HashMap<String,String> expectedResult = new HashMap<String, String>();
		expectedResult.put("taxType", "IGST");
		expectedResult.put("taxRate", "18.000");
		
		String skuId[] = { OMSTCConstants.OtherSkus.skuId_3845+":2" };
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3845+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":10000:0:"+vectorSellerID+":1"}, supplyTypeOnHand);
		imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3845+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":10000:0:"+vectorSellerID},
				supplyTypeOnHand);

		orderID = end2EndHelper.createOrder(login, password, addressId, skuId, "", false, false, false, "", false);
		log.info("OrderID: "+orderID);
		omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
		orderEntry = omsServiceHelper.getOrderEntry(orderID);
		OrderReleaseEntry orderReleaseEntry = orderEntry.getOrderReleases().get(0);
		orderReleaseId = orderReleaseEntry.getId().toString();
		omsServiceHelper.stampGovtTaxForVectorSuccess(orderReleaseId);
		String lineId = orderReleaseEntry.getOrderLines().get(0).getId().toString();
		
		//This function checks if Tax is applied correctly in Taxation Data store Table
		validateTaxDataInDBGSTLessThan20(expectedResult,lineId);
		//This function checkes if Tax data returened from service is same as available in Taxation Data store
		validateTaxData(entityType.ORDER_LINE.toString(),lineId);
		
		//Reassign to new warehouse 36
		Integer wareHouseId = 36;
		String corrierCode = String.valueOf(orderReleaseEntry.getCourierCode());
		String LineIDAndQuantity[] = { "" + lineId + ":2"};
		imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3845+":"+wareHouseId+":10000:0:"+vectorSellerID},
				supplyTypeOnHand);
		OrderReleaseResponse orderReleaseResponse=omsServiceHelper.splitOrder(orderReleaseId, wareHouseId.intValue(), LineIDAndQuantity, corrierCode, LineMovementAction.REASSIGN_WAREHOUSE);
		sft.assertEquals(orderReleaseResponse.getStatus().getStatusType().toString(), "SUCCESS","Status code should be the same.");
		orderReleaseEntry = orderEntry.getOrderReleases().get(0);
		lineId = orderReleaseEntry.getOrderLines().get(0).getId().toString();
		omsServiceHelper.stampGovtTaxForVectorSuccess(orderReleaseId);
		expectedResult.put("taxType", "CGST:SGST");
		expectedResult.put("taxRate", "9.000:9.000");
		//This function checks if Tax is applied correctly in Taxation Data store Table
		validateTaxDataInDBGSTLessThan20(expectedResult,lineId);
		//This function checkes if Tax data returened from service is same as available in Taxation Data store
		validateTaxData(entityType.ORDER_LINE.toString(),lineId);
		sft.assertAll();
		
	}
	
	@Test(enabled=true, groups={"smoke","regression","taxCalculation","sanity"}, dataProvider = "GovtTaxData", dataProviderClass = GovtTaxDP.class)
	public void validateGovtTaxableAmountAndGovtTaxRateWithAndWihoutShippingCharge(String TestScenario,String sku,Double TaxRate) throws Exception{
		SoftAssert sft = new SoftAssert();
		String skuId[]={sku};
		sku = sku.split(":")[0];
		
		HashMap<String,String> expectedResult = new HashMap<String, String>();
		expectedResult.put("taxType", "IGST");
		expectedResult.put("taxRate", "10.000");
		
		//to update the warehouse inventory
		imsServiceHelper.updateInventoryForSeller(new String[] {sku+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":10000:0:"+vectorSellerID},supplyTypeOnHand);
		String orderID = end2EndHelper.createOrder(login, password, addressId, skuId, "", false, false, false, "",false);
		omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
        orderEntry = omsServiceHelper.getOrderEntry(orderID);
		OrderReleaseEntry orderReleaseEntry = orderEntry.getOrderReleases().get(0);
		orderReleaseId = orderReleaseEntry.getId().toString();
		omsServiceHelper.stampGovtTaxForVectorSuccess(orderReleaseId);
		String lineId = orderReleaseEntry.getOrderLines().get(0).getId().toString();
		
        //This function checks if Tax is applied correctly in Taxation Data store Table
		validateTaxDataInDBGSTLessThan20(expectedResult,lineId);
      	//This function checkes if Tax data returened from service is same as available in Taxation Data store
      	validateTaxData(entityType.ORDER_LINE.toString(),lineId);
      		
		sft.assertAll();
	}

	@Test(enabled=true, groups={"smoke","regression","taxCalculation"}, dataProvider = "GovtTaxData", dataProviderClass = GovtTaxDP.class)
	public void validateGovtTaxableAmountAndGovtTaxRateWithGiftCharges_And_GIFTPLUSShippingCharges(String TestScenario,String sku,Double TaxRate) throws Exception{
		SoftAssert sft = new SoftAssert();
		
		HashMap<String,String> expectedResult = new HashMap<String, String>();
		expectedResult.put("taxType", "IGST");
		expectedResult.put("taxRate", "10.000");
		
		String skuId[]={sku};
		sku = sku.split(":")[0];
		imsServiceHelper.updateInventoryForSeller(new String[] {sku+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":10000:0:"+vectorSellerID},supplyTypeOnHand);
		String orderID = end2EndHelper.createOrder(login, password, addressId, skuId, "", false, false, true, "",false);
		omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
        orderEntry = omsServiceHelper.getOrderEntry(orderID);
        OrderReleaseEntry orderReleaseEntry = orderEntry.getOrderReleases().get(0);
		orderReleaseId = orderReleaseEntry.getId().toString();
		omsServiceHelper.stampGovtTaxForVectorSuccess(orderReleaseId);
		String lineId = orderReleaseEntry.getOrderLines().get(0).getId().toString();
		
        //This function checks if Tax is applied correctly in Taxation Data store Table
		validateTaxDataInDBGSTLessThan20(expectedResult,lineId);
      	//This function checkes if Tax data returened from service is same as available in Taxation Data store
      	validateTaxData(entityType.ORDER_LINE.toString(),lineId);
		sft.assertAll();
	}











	/**
	 * @param expectedResult
	 * @param lineId
	 * @throws UnsupportedEncodingException
	 * @throws JAXBException
	 */
	private void validateTaxDataInDBGSTMoreThan20(
			HashMap<String, String> expectedResult, String lineId) throws UnsupportedEncodingException, JAXBException {
		// TODO Auto-generated method stub
		String[] taxTypes = expectedResult.get("taxType").split(":");
		String[] taxRates = expectedResult.get("taxRate").split(":");
		log.info(taxTypes.length);
		int i = 0;
		for(String taxType: taxTypes){
			String orderReleaseId = omsServiceHelper.getOrderLineEntry(lineId).getOrderReleaseId().toString();
			double taxRateExpected = Double.parseDouble(taxRates[i]);
			//Adding constant Values unless Calculation Logic is not defined
			double unitTaxableAmountExpected = getTaxableAmount(orderReleaseId,taxRateExpected);
			double unitTaxAmountExpected= Math.round(((unitTaxableAmountExpected*taxRateExpected/100.00)*100.0))/100.0;
			unitTaxableAmountExpected = Math.round(unitTaxableAmountExpected*100.00)/100.00;

			HashMap<String,Object> taxationDataEntry = omsServiceHelper.gettaxationDataInfoDBEntry("ORDER_LINE", lineId, taxType);
			double taxRateDB = Double.parseDouble(taxationDataEntry.get("tax_rate").toString());
			double unitTaxableAmountDB = Double.parseDouble(taxationDataEntry.get("unit_taxable_amount").toString());
			double unitTaxAmountDB = Double.parseDouble(taxationDataEntry.get("unit_tax_amount").toString());
			log.info(taxRateExpected+" "+(unitTaxableAmountExpected)+" "+unitTaxAmountExpected);
			log.info(taxRateDB +" "+unitTaxableAmountDB+" "+unitTaxAmountDB);

			sft.assertEquals(taxRateExpected, taxRateDB,"Tax Rate is not correct in Taxation DB");
			sft.assertEquals(unitTaxableAmountExpected, unitTaxableAmountDB,"UnitTaxableAmount is not correct in Taxation DB");
			sft.assertEquals(unitTaxAmountExpected, unitTaxAmountDB,"UnitTaxAmount is not correct in Taxation DB");
			i++;
		}


		
	}

	/**
	 * @param expectedResult
	 * @param string
	 * @throws JAXBException 
	 * @throws UnsupportedEncodingException 
	 */
	private void validateTaxDataInDBGSTLessThan20(HashMap<String, String> expectedResult,
			String lineId) throws UnsupportedEncodingException, JAXBException {
		// TODO Auto-generated method stub
		String[] taxTypes = expectedResult.get("taxType").split(":");
		String[] taxRates = expectedResult.get("taxRate").split(":");
		Double taxRateTotal = 0.0;
		for(String taxRate: taxRates){
			taxRateTotal+=Double.parseDouble(taxRate);
		}
		
		log.info("TaxRateTotal : "+taxRateTotal);
		int qty = omsServiceHelper.getOrderLineEntry(lineId).getQuantity();
		int i =0;
		for(String taxType: taxTypes){
			String orderReleaseId = omsServiceHelper.getOrderLineEntry(lineId).getOrderReleaseId().toString();
			double taxRateExpected = Double.parseDouble(taxRates[i]);
			//Adding constant Values unless Calculation Logic is not defined
			double unitTaxableAmountExpected = getTaxableAmount(orderReleaseId,taxRateTotal);
			double unitTaxAmountExpected= Math.round(((unitTaxableAmountExpected*taxRateExpected/100.00)*100.0))/100.0;
			unitTaxableAmountExpected = Math.round(unitTaxableAmountExpected*100.00)/100.00;

			HashMap<String,Object> taxationDataEntry = omsServiceHelper.gettaxationDataInfoDBEntry("ORDER_LINE", lineId, taxType);
			double taxRateDB = Double.parseDouble(taxationDataEntry.get("tax_rate").toString());
			double unitTaxableAmountDB = Double.parseDouble(taxationDataEntry.get("unit_taxable_amount").toString());
			double unitTaxAmountDB = Double.parseDouble(taxationDataEntry.get("unit_tax_amount").toString());
			//log.info(taxRateExpected+" "+(unitTaxableAmountExpected)+" "+unitTaxAmountExpected);
			//log.info(taxRateDB +" "+unitTaxableAmountDB+" "+unitTaxAmountDB);

			sft.assertEquals(taxRateExpected, taxRateDB,"Tax Rate is not correct in Taxation DB");
			sft.assertEquals(unitTaxableAmountExpected, unitTaxableAmountDB*qty,0.02,"UnitTaxableAmount is not correct in Taxation DB");
			sft.assertEquals(unitTaxAmountExpected, unitTaxAmountDB*qty,0.02,"UnitTaxAmount is not correct in Taxation DB");
			i++;
		}

		
	}

	
	/**
	 * @param entityType
	 * @param lineId
	 * @throws UnsupportedEncodingException
	 * @throws JAXBException
	 * This is comman function to check VAT/GST data in DB Values and Values returned from Service
	 */
	public void validateTaxData( String entityType, String lineId ) throws UnsupportedEncodingException, JAXBException{
		if(entityType.equalsIgnoreCase("ORDER_LINE")){
			OrderLineEntry orderLineEntry = omsServiceHelper.getOrderLineEntry(lineId);
			int qty = orderLineEntry.getQuantity();
			 List<com.myntra.oms.client.entry.TaxEntry> taxEntries = orderLineEntry.getTaxEntries();
			for(com.myntra.oms.client.entry.TaxEntry taxEntry: taxEntries){
				String taxTypeOMS = taxEntry.getTaxType();
				double taxRateOMS = Double.parseDouble(""+taxEntry.getTaxRate());
				double unitTaxableAmountOMS = Double.parseDouble(""+taxEntry.getUnitTaxableAmount());
				double unitTaxAmountOMS = Double.parseDouble(""+taxEntry.getUnitTaxAmount());
				String taxType = taxEntry.getTaxType();
				
				//Validate DB details for Tax in Line additional details
				//Comment this function once GST is Live
				//validateTaxEntryInLineAdditionalDetailsTable(lineId,taxTypeOMS,taxRateOMS,unitTaxableAmountOMS,unitTaxAmountOMS,qty);					
				
				validateTaxEntryInTaxationDataTable(entityType,lineId,taxType,taxTypeOMS,taxRateOMS,unitTaxableAmountOMS,unitTaxAmountOMS,qty);
				
			}
			
		}else if(entityType.equalsIgnoreCase("ORDER_RELEASE")){
			//This is currently not used
			
		}else if(entityType.equalsIgnoreCase("ORDER")){
			//This is currently not used
			
		}else{
			
			log.info("Please provide correct entity type");
		}
		
	}

	
	/**
	 * @param entityType
	 * @param lineId
	 * @param taxType
	 * @param taxTypeOMS
	 * @param taxRateOMS
	 * @param unitTaxableAmountOMS
	 * @param unitTaxAmountOMS
	 */
	private void validateTaxEntryInTaxationDataTable(String entityType,
			String lineId, String taxType, String taxTypeOMS,
			double taxRateOMS, double unitTaxableAmountOMS,
			double unitTaxAmountOMS,int qty) {
		// TODO Auto-generated method stub
		 HashMap<String,Object> taxationDataEntry = omsServiceHelper.gettaxationDataInfoDBEntry(entityType, lineId, taxType);
		 
		 double taxRateDB = Double.parseDouble(taxationDataEntry.get("tax_rate").toString());
		 double unitTaxableAmountDB = Double.parseDouble(taxationDataEntry.get("unit_taxable_amount").toString());
		 double unitTaxAmountDB = Double.parseDouble(taxationDataEntry.get("unit_tax_amount").toString());
		 
		 sft.assertEquals(taxRateOMS, taxRateDB,"validateTaxEntryInTaxationDataTable:Taxrate is not correct in OMS "+taxRateOMS+" in DB"+taxRateDB);
		 sft.assertEquals(unitTaxableAmountOMS, unitTaxableAmountDB,"validateTaxEntryInTaxationDataTable:UnitTaxable Amount is not correct in OMS "+unitTaxableAmountOMS+" in DB "+unitTaxableAmountDB);
		 sft.assertEquals(unitTaxAmountOMS, unitTaxAmountDB,"validateTaxEntryInTaxationDataTable:UnitTaxAmount is not correct in OMS "+unitTaxAmountOMS+" in DB"+unitTaxAmountDB);
		
	}

	/**
	 * @param lineId
	 * @param taxTypeOMS
	 * @param taxRateOMS
	 * @param unitTaxableAmountOMS
	 * @param unitTaxAmountOMS
	 */
	private void validateTaxEntryInLineAdditionalDetailsTable(String lineId, String taxTypeOMS, double taxRateOMS,
			double unitTaxableAmountOMS, double unitTaxAmountOMS,int qty) {
		// TODO Auto-generated method stub
		
		String taxTypeDB = omsServiceHelper.getOrderLineAdditionalInfoDBEntry(lineId, "GOVT_TAX_TYPE").get("value").toString();
		double taxRateDB = Double.parseDouble(omsServiceHelper.getOrderLineAdditionalInfoDBEntry(lineId, "GOVT_TAX_RATE").get("value").toString());
		double unitTaxableAmountDB = Double.parseDouble(omsServiceHelper.getOrderLineAdditionalInfoDBEntry(lineId, "GOVT_TAXABLE_AMOUNT").get("value").toString());
		double unitTaxAmountDB = Double.parseDouble(omsServiceHelper.getOrderLineAdditionalInfoDBEntry(lineId, "GOVT_UNIT_TAX_AMOUNT").get("value").toString());
		
		sft.assertEquals(taxTypeDB, taxTypeOMS,"validateTaxEntryInLineAdditionalDetailsTable:TaxType is not correct Actual:"+taxTypeDB+" Expected:"+taxTypeOMS);
		sft.assertEquals(taxRateDB, taxRateOMS,"validateTaxEntryInLineAdditionalDetailsTable:TaxRate is not correct Actual:"+taxRateDB+" Expected:"+taxRateOMS);
		sft.assertEquals(unitTaxableAmountDB, unitTaxableAmountOMS*qty,0.02,"validateTaxEntryInLineAdditionalDetailsTable:UnitTaxableAmount is not correct Actual:"+unitTaxableAmountDB+" Expected:"+unitTaxableAmountOMS);
		sft.assertEquals(unitTaxAmountDB, unitTaxAmountOMS,0.02,"validateTaxEntryInLineAdditionalDetailsTable:UnitTaxAmount is not correct Actual:"+unitTaxAmountDB+" Expected:"+unitTaxAmountOMS);
		
	}
	
	
	private double getTaxableAmount(String releaseId, double taxrate) throws UnsupportedEncodingException, JAXBException{
		double taxrateFinal = 0.0;
		OrderLineEntry orderLineEntry = omsServiceHelper.getOrderLineEntries(releaseId).get(0);
		
		if(orderLineEntry.getTaxAmount()>0.0 && orderLineEntry.getTaxRate() > 0.0){
			taxrateFinal = getTaxableAmountForDiscountGreaterThan20(releaseId);
		}else{
			taxrateFinal = getTaxableAmountForDiscountLessThan20(releaseId,taxrate);
		}
		
		return taxrateFinal;
		
	}
/**
 * @param releaseId
 * @param taxrate
 * @return
 * @throws UnsupportedEncodingException
 * @throws JAXBException
 */
private double getTaxableAmountForDiscountLessThan20(String releaseId, double taxrate) throws UnsupportedEncodingException, JAXBException{
		OrderReleaseEntry orderReleaseEntry = omsServiceHelper.getOrderReleaseEntry(releaseId);
		double mrpTotal = orderReleaseEntry.getMrpTotal();
		double additionalCharge = orderReleaseEntry.getShippingCharge() + orderReleaseEntry.getGiftCardAmount();
		double discount = orderReleaseEntry.getCartDiscount()+orderReleaseEntry.getCouponDiscount()+orderReleaseEntry.getDiscount();
		log.info(mrpTotal+" "+additionalCharge+" "+discount+" "+taxrate);
		return (mrpTotal + additionalCharge -discount)*100/(100+taxrate);
		
		
	}

	/**
	 * @param releaseId
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws JAXBException
	 */
	private double getTaxableAmountForDiscountGreaterThan20(String releaseId) throws UnsupportedEncodingException, JAXBException{
		OrderReleaseEntry orderReleaseEntry = omsServiceHelper.getOrderReleaseEntry(releaseId);
		double mrpTotal = orderReleaseEntry.getMrpTotal();
		double additionalCharge = orderReleaseEntry.getShippingCharge() + orderReleaseEntry.getGiftCardAmount();
		double discount = orderReleaseEntry.getCartDiscount()+orderReleaseEntry.getCouponDiscount()+orderReleaseEntry.getDiscount();
		
		return (mrpTotal + additionalCharge -discount);
		
	}


}
