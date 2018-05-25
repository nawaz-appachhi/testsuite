package com.myntra.apiTests.erpservices.oms.Test;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import javax.xml.bind.JAXBException;

import com.myntra.apiTests.erpservices.oms.OMSServiceHelper;
import org.apache.log4j.Logger;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.myntra.apiTests.end2end.End2EndHelper;
import com.myntra.apiTests.erpservices.atp.ATPServiceHelper;
import com.myntra.apiTests.erpservices.ims.IMSServiceHelper;
import com.myntra.apiTests.erpservices.sellerapis.SellerConfig;
import com.myntra.lordoftherings.Initialize;
import com.myntra.oms.client.entry.OrderEntry;
import com.myntra.oms.client.entry.OrderLineEntry;
import com.myntra.oms.client.entry.OrderReleaseEntry;
import com.myntra.oms.client.response.OrderReleaseResponse;
import com.myntra.oms.common.enums.LineMovementAction;
import com.myntra.test.commons.testbase.BaseTest;

public class OMSTaxationDataVAT extends BaseTest {

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
	
	@Test(enabled = true,groups={"regression","taxCalculation"},description="TC034:Create single Line order assigned to GurGaon(different state) and Verify CST is applied and available in Taxation Data store and Line additional Details.")
	public void CreateSingleLineOrderAssignedToGurGaonAndVerifyCST() throws Exception{
		sft = new SoftAssert();
		
		HashMap<String,String> expectedResult = new HashMap<String, String>();
		expectedResult.put("taxType", "CST");
		expectedResult.put("taxRate", "5.250");
		
		String skuId[] = { OMSTCConstants.OtherSkus.skuId_3831+":1" };
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3831+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":10000:0:"+vectorSellerID+":1"}, supplyTypeOnHand);
		imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3831+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":10000:0:"+vectorSellerID},
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
		validateTaxDataInDBVATorCST(expectedResult,lineId);
		//This function checkes if Tax data returened from service is same as available in Taxation Data store
		validateTaxData(entityType.ORDER_LINE.toString(),lineId);
		sft.assertAll();
		
	}
	
	@Test(enabled = true,groups={"regression","taxCalculation"},description="TC035:Create single Line order assigned to Bangalore(Same state) and Verify VAT is applied and available in Taxation Data store and Line additional Details.")
	public void CreateSingleLineOrderAssignedToBangaloreAndVerifyVAT() throws Exception{
		sft = new SoftAssert();
		
		HashMap<String,String> expectedResult = new HashMap<String, String>();
		expectedResult.put("taxType", "VAT");
		expectedResult.put("taxRate", "5.500");
		
		String skuId[] = { OMSTCConstants.OtherSkus.skuId_3832+":2" };
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3832+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":10000:0:"+vectorSellerID+":1"}, supplyTypeOnHand);
		imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3832+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":10000:0:"+vectorSellerID},
				supplyTypeOnHand);

		orderID = end2EndHelper.createOrder(login, password, addressId, skuId, "", false, false, false, "", false);
		log.info("OrderID: "+orderID);
		omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
		orderEntry = omsServiceHelper.getOrderEntry(orderID);
		orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();
		omsServiceHelper.stampGovtTaxForVectorSuccess(orderReleaseId);
		String lineId = orderEntry.getOrderReleases().get(0).getOrderLines().get(0).getId().toString();
		OrderLineEntry orderLineEntry = omsServiceHelper.getOrderLineEntry(lineId);
		
		
		//This function checks if Tax is applied correctly in Taxation Data store Table
		validateTaxDataInDBVATorCST(expectedResult,lineId);
		//This function checkes if Tax data returened from service is same as available in Taxation Data store
		validateTaxData(entityType.ORDER_LINE.toString(),lineId);
		sft.assertAll();
		
	}
	
	@Test(enabled = true,groups={"regression","taxCalculation"},description="TC036:Create single Line order assigned to Bangalore(Same state) and Verify VAT is applied and available in Taxation Data store and Line additional Details.")
	public void CreateMultiLineOrderAssignedToBangaloreAndGurGaonAndVerifyVATCST() throws Exception{
		sft = new SoftAssert();
		
		String skuId[] = { OMSTCConstants.OtherSkus.skuId_3831+":2", OMSTCConstants.OtherSkus.skuId_3832+":2" };
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3831+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":10000:0:"+vectorSellerID+":1",
				OMSTCConstants.OtherSkus.skuId_3832+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":10000:0:"+vectorSellerID+":1"}, supplyTypeOnHand);
		imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3831+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":10000:0:"+vectorSellerID,
				OMSTCConstants.OtherSkus.skuId_3832+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":10000:0:"+vectorSellerID},
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
				expectedResult.put("taxType", "VAT");
				expectedResult.put("taxRate", "5.500");
			}else if(orderReleaseEntry.getWarehouseId()==28){
				expectedResult.put("taxType", "CST");
				expectedResult.put("taxRate", "5.250");
			}
			validateTaxDataInDBVATorCST(expectedResult,lineId);
			//This function checkes if Tax data returened from service is same as available in Taxation Data store
			validateTaxData(entityType.ORDER_LINE.toString(),lineId);
						
		}
		
		sft.assertAll();
		
	}

	@Test(enabled = true,groups={"regression","taxCalculation"},description="TC037:Create single Line order assigned to GurGaon(different state) and reassign to Bangalore(Same state) and verify Tax is changed and available in Taxation Data store and Line additional Details.")
	public void CreateSingleLineOrderAssignedToGurgaonAndVerifyVATAfterReassignment() throws Exception{
		sft = new SoftAssert();
		
		HashMap<String,String> expectedResult = new HashMap<String, String>();
		expectedResult.put("taxType", "CST");
		expectedResult.put("taxRate", "5.250");
		
		String skuId[] = { OMSTCConstants.OtherSkus.skuId_3838+":2" };
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3838+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":10000:0:"+vectorSellerID+":1"}, supplyTypeOnHand);
		imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3838+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":10000:0:"+vectorSellerID},
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
		validateTaxDataInDBVATorCST(expectedResult,lineId);
		//This function checkes if Tax data returened from service is same as available in Taxation Data store
		validateTaxData(entityType.ORDER_LINE.toString(),lineId);
		
		//Reassign to new warehouse 36
		Integer wareHouseId = 36;
		String corrierCode = String.valueOf(orderReleaseEntry.getCourierCode());
		String LineIDAndQuantity[] = { "" + lineId + ":2"};
		imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3838+":"+wareHouseId+":10000:0:"+vectorSellerID},
				supplyTypeOnHand);
		OrderReleaseResponse orderReleaseResponse=omsServiceHelper.splitOrder(orderReleaseId, wareHouseId.intValue(), LineIDAndQuantity, corrierCode, LineMovementAction.REASSIGN_WAREHOUSE);
		sft.assertEquals(orderReleaseResponse.getStatus().getStatusType().toString(), "SUCCESS","Status code should be the same.");
		orderReleaseEntry = orderEntry.getOrderReleases().get(0);
		lineId = orderReleaseEntry.getOrderLines().get(0).getId().toString();
		omsServiceHelper.stampGovtTaxForVectorSuccess(orderReleaseId);
		expectedResult.put("taxType", "VAT");
		expectedResult.put("taxRate", "5.500");
		//This function checks if Tax is applied correctly in Taxation Data store Table
		validateTaxDataInDBVATorCST(expectedResult,lineId);
		//This function checkes if Tax data returened from service is same as available in Taxation Data store
		validateTaxData(entityType.ORDER_LINE.toString(),lineId);
		sft.assertAll();
		
	}
	
	@Test(enabled = true,groups={"regression","taxCalculation"},description="TC038:1 :Create single Line order assigned to Bangalore(same state) and reassign to Gurgaon(different state) and verify Tax is changed and available in Taxation Data store and Line additional Details.")
	public void CreateSingleLineOrderAssignedToBangaloreAndVerifyCSTAfterReassignment() throws Exception{
		sft = new SoftAssert();
		
		HashMap<String,String> expectedResult = new HashMap<String, String>();
		expectedResult.put("taxType", "VAT");
		expectedResult.put("taxRate", "5.500");
		
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
		validateTaxDataInDBVATorCST(expectedResult,lineId);
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
		
		expectedResult.put("taxType", "CST");
		expectedResult.put("taxRate", "5.250");
		
		//This function checks if Tax is applied correctly in Taxation Data store Table
		validateTaxDataInDBVATorCST(expectedResult,lineId);
		//This function checkes if Tax data returened from service is same as available in Taxation Data store
		validateTaxData(entityType.ORDER_LINE.toString(),lineId);
				
		
		sft.assertAll();
		
	}


	@Test(enabled = true,groups={"regression","taxCalculation"},description="TC038:2 :Create single Line order assigned to Bangalore(same state) and reassign to Gurgaon(different state) and verify Tax is changed and available in Taxation Data store and Line additional Details for coupon more than 20.")
	public void CreateSingleLineOrderBangaloreAndVerifyCSTAfterReassignment() throws Exception{
		sft = new SoftAssert();

		HashMap<String,String> expectedResult = new HashMap<String, String>();
		expectedResult.put("taxType", "VAT");
		expectedResult.put("taxRate", "5.500");

		String skuId[] = { OMSTCConstants.OtherSkus.skuId_1243742+":1" };
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_1243742+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":10000:0:"+vectorSellerID+":1"}, supplyTypeOnHand);
		imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_1243742+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":10000:0:"+vectorSellerID},
				supplyTypeOnHand);

		orderID = end2EndHelper.createOrder(login, password, addressId, skuId, coupon_More_Than_20_Fox8, false, false, false, "", false);
		log.info("OrderID: "+orderID);
		omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
		orderEntry = omsServiceHelper.getOrderEntry(orderID);
		OrderReleaseEntry orderReleaseEntry = orderEntry.getOrderReleases().get(0);
		orderReleaseId = orderReleaseEntry.getId().toString();
		omsServiceHelper.stampGovtTaxForVectorSuccess(orderReleaseId);
		String lineId = orderReleaseEntry.getOrderLines().get(0).getId().toString();

		//This function checks if Tax is applied correctly in Taxation Data store Table
		validateTaxDataInDBVATorCST(expectedResult,lineId);
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

		expectedResult.put("taxType", "CST");
		expectedResult.put("taxRate", "5.250");

		//This function checks if Tax is applied correctly in Taxation Data store Table
		validateTaxDataInDBVATorCSTMoreThan20(expectedResult,lineId);
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
	private void validateTaxDataInDBVATorCST(HashMap<String, String> expectedResult,
			String lineId) throws UnsupportedEncodingException, JAXBException {
		// TODO Auto-generated method stub
		String[] taxTypes = expectedResult.get("taxType").split(":");
		System.out.println(taxTypes.length);
		OrderLineEntry lineEntry = omsServiceHelper.getOrderLineEntry(lineId);
		int qty = lineEntry.getQuantity();
		for(String taxType: taxTypes){
			String orderReleaseId = omsServiceHelper.getOrderLineEntry(lineId).getOrderReleaseId().toString();
			double taxRateExpected = Double.parseDouble(expectedResult.get("taxRate"));
			double unitTaxableAmountExpected = getTaxableAmountForDiscountLessThan20(orderReleaseId, taxRateExpected);
			double unitTaxAmountExpected= Math.round(((unitTaxableAmountExpected*taxRateExpected/100.00)*100.0))/100.0;
			unitTaxableAmountExpected = Math.round(unitTaxableAmountExpected*100.00)/100.00;
			
			HashMap<String,Object> taxationDataEntry = omsServiceHelper.gettaxationDataInfoDBEntry("ORDER_LINE", lineId, taxType);
			double taxRateDB = Double.parseDouble(taxationDataEntry.get("tax_rate").toString());
			double unitTaxableAmountDB = Double.parseDouble(taxationDataEntry.get("unit_taxable_amount").toString());
			double unitTaxAmountDB = Double.parseDouble(taxationDataEntry.get("unit_tax_amount").toString());
			//System.out.println(taxRateExpected+" "+(unitTaxableAmountExpected)+" "+unitTaxAmountExpected);
			//System.out.println(taxRateDB +" "+unitTaxableAmountDB+" "+unitTaxAmountDB);
			
			sft.assertEquals(taxRateExpected, taxRateDB,"validateTaxDataInDBVATorCST:Tax Rate is not correct in Taxation DB");
			sft.assertEquals(unitTaxableAmountExpected, unitTaxableAmountDB*qty,0.02,"validateTaxDataInDBVATorCST:UnitTaxableAmount is not correct in Taxation DB Expected:"+unitTaxableAmountExpected+" Actual:"+unitTaxableAmountDB*qty);
			sft.assertEquals(unitTaxAmountExpected, unitTaxAmountDB*qty,0.02,"validateTaxDataInDBVATorCST:UnitTaxAmount is not correct in Taxation DB Expected:"+unitTaxAmountExpected+" Actual:"+unitTaxAmountDB*qty);
			 
		}
		
	}

	/**
	 * @param expectedResult
	 * @param lineId
	 * @throws UnsupportedEncodingException
	 * @throws JAXBException
	 */
	private void validateTaxDataInDBVATorCSTMoreThan20(HashMap<String, String> expectedResult,
											 String lineId) throws UnsupportedEncodingException, JAXBException {
		// TODO Auto-generated method stub
		String[] taxTypes = expectedResult.get("taxType").split(":");
		System.out.println(taxTypes.length);

		for(String taxType: taxTypes){
			String orderReleaseId = omsServiceHelper.getOrderLineEntry(lineId).getOrderReleaseId().toString();
			double taxRateExpected = Double.parseDouble(expectedResult.get("taxRate"));
			double unitTaxableAmountExpected = getTaxableAmountForDiscountGreaterThan20(orderReleaseId);
			double unitTaxAmountExpected= Math.round(((unitTaxableAmountExpected*taxRateExpected/100.00)*100.0))/100.0;
			unitTaxableAmountExpected = Math.round(unitTaxableAmountExpected*100.00)/100.00;

			HashMap<String,Object> taxationDataEntry = omsServiceHelper.gettaxationDataInfoDBEntry("ORDER_LINE", lineId, taxType);
			double taxRateDB = Double.parseDouble(taxationDataEntry.get("tax_rate").toString());
			double unitTaxableAmountDB = Double.parseDouble(taxationDataEntry.get("unit_taxable_amount").toString());
			double unitTaxAmountDB = Double.parseDouble(taxationDataEntry.get("unit_tax_amount").toString());
			//System.out.println(taxRateExpected+" "+(unitTaxableAmountExpected)+" "+unitTaxAmountExpected);
			//System.out.println(taxRateDB +" "+unitTaxableAmountDB+" "+unitTaxAmountDB);

			sft.assertEquals(taxRateExpected, taxRateDB,"Tax Rate is not correct in Taxation DB");
			sft.assertEquals(unitTaxableAmountExpected, unitTaxableAmountDB,"UnitTaxableAmount is not correct in Taxation DB");
			sft.assertEquals(unitTaxAmountExpected, unitTaxAmountDB,"UnitTaxAmount is not correct in Taxation DB");

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
				validateTaxEntryInLineAdditionalDetailsTable(lineId,taxTypeOMS,taxRateOMS,unitTaxableAmountOMS,unitTaxAmountOMS,qty);					
				
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
		System.out.println(mrpTotal+" "+additionalCharge+" "+discount+" "+taxrate);
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
