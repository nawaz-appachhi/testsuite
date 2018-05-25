package com.myntra.apiTests.erpservices.lms.tests;

import com.myntra.apiTests.common.Constants.EnumSCM;
import com.myntra.apiTests.common.Constants.ReleaseStatus;
import com.myntra.apiTests.common.Constants.ShipmentSource;
import com.myntra.apiTests.common.ProcessOrder.Service.ProcessRelease;
import com.myntra.apiTests.common.entries.ReleaseEntry;
import com.myntra.apiTests.erpservices.lms.Constants.LMS_PINCODE;
import com.myntra.apiTests.erpservices.lms.Helper.LMSHelper;
import com.myntra.apiTests.erpservices.lms.Helper.LMS_ReturnHelper;
import com.myntra.apiTests.erpservices.lms.Helper.LmsServiceHelper;
import com.myntra.apiTests.erpservices.oms.OMSServiceHelper;
import com.myntra.apiTests.erpservices.rms.RMSServiceHelper;
import com.myntra.apiTests.portalservices.pps.PPSServiceHelper;
import com.myntra.oms.client.entry.OrderLineEntry;
import com.myntra.oms.client.entry.OrderReleaseEntry;
import com.myntra.returns.common.enums.RefundMode;
import com.myntra.returns.common.enums.ReturnMode;
import com.myntra.returns.common.enums.ReturnType;
import com.myntra.returns.response.ReturnResponse;
import com.myntra.test.commons.testbase.BaseTest;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shubham Gupta on 12/16/16.
 */
public class LMS_Reverse_Exchange extends BaseTest {
	
	private static org.slf4j.Logger log = LoggerFactory.getLogger(LMS_Reverse_Exchange.class);
	private OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
	private RMSServiceHelper rmsServiceHelper = new RMSServiceHelper();
	private LmsServiceHelper lmsServiceHelper = new LmsServiceHelper();
	private LMSHelper lmsHepler = new LMSHelper();
	private PPSServiceHelper ppsServiceHelper = new PPSServiceHelper();
	private ProcessRelease processRelease = new ProcessRelease();
	LMS_ReturnHelper lmsReturnHelper = new LMS_ReturnHelper();
	
	
	@Test(groups = {"P0", "Smoke", "Regression"}, priority = 2, description = "ID: C496 , LMS E2E flow from order placement to DL and than create return and process return till receive back in WH", invocationCount = 1)
	public void LMS_Mock_OpenBoxPickUpSuccessful() throws Exception {
		
		String orderID = lmsHepler.createMockOrder(EnumSCM.DL, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.NORMAL, "cod", false, true);
        OrderReleaseEntry orderReleaseEntry = omsServiceHelper.getOrderReleaseEntry(omsServiceHelper.getReleaseId(orderID));
        OrderLineEntry lineEntry = omsServiceHelper.getOrderLineEntry(orderReleaseEntry.getOrderLines().get(0).getId().toString());
        ReturnResponse returnResponse = rmsServiceHelper.createReturn(lineEntry.getId(), ReturnType.NORMAL, ReturnMode.OPEN_BOX_PICKUP, 1, 49L, RefundMode.NEFT, "418", "test Open box address ", "6135071", "560068", "Bangalore", "Karnataka", "India","8149881455");
        Assert.assertEquals(returnResponse.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Return creation failed");
        Long returnId = returnResponse.getData().get(0).getId();
        log.info("________ReturnId: " + returnId);
		lmsReturnHelper.processOpenBoxReturn(returnId.toString(), EnumSCM.PICKED_UP_SUCCESSFULLY);
		
		
		
	}
	
	@Test(groups = {"P0", "Smoke", "Regression"}, priority = 2, description = "ID:  , LMS E2E flow from order placement to DL and than create return and process return till receive back in WH", invocationCount = 1)
	public void LMS_PQCP_Approved() throws Exception {
		
		String orderID = lmsHepler.createMockOrder(EnumSCM.DL, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.NORMAL, "cod", false, true);
		OrderReleaseEntry orderReleaseEntry = omsServiceHelper.getOrderReleaseEntry(omsServiceHelper.getReleaseId(orderID));
		OrderLineEntry lineEntry = omsServiceHelper.getOrderLineEntry(orderReleaseEntry.getOrderLines().get(0).getId().toString());
		ReturnResponse returnResponse = rmsServiceHelper.createReturn(lineEntry.getId(), ReturnType.NORMAL, ReturnMode.OPEN_BOX_PICKUP, 1, 49L, RefundMode.NEFT, "418", "test Open box address ", "6135071", "560068", "Bangalore", "Karnataka", "India","8149881455");
		Assert.assertEquals(returnResponse.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Return creation failed");
		Long returnId = returnResponse.getData().get(0).getId();
		log.info("________ReturnId: " + returnId);
		lmsReturnHelper.processOpenBoxReturn(returnId.toString(), EnumSCM.PICKUP_DONE_QC_PENDING);
		
		
		
	}
	
	@Test(groups = {"P0", "Smoke", "Regression"}, priority = 2, description = "ID: C496 , LMS E2E flow from order placement to DL and than create return and process return till receive back in WH", invocationCount = 1)
	public void LMS_Mock_OpenBoxPickUpOnHoldApprove() throws Exception {
		String orderID = lmsHepler.createMockOrder(EnumSCM.DL, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.NORMAL, "cod", false, true);
		OrderReleaseEntry orderReleaseEntry = omsServiceHelper.getOrderReleaseEntry(omsServiceHelper.getReleaseId(orderID));
		OrderLineEntry lineEntry = omsServiceHelper.getOrderLineEntry(orderReleaseEntry.getOrderLines().get(0).getId().toString());
		ReturnResponse returnResponse = rmsServiceHelper.createReturn(lineEntry.getId(), ReturnType.NORMAL, ReturnMode.OPEN_BOX_PICKUP, 1, 49L, RefundMode.NEFT, "418", "test Open box address ", "6135071", "560068", "Bangalore", "Karnataka", "India","8149881455");
		Assert.assertEquals(returnResponse.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Return creation failed");
		Long returnId = returnResponse.getData().get(0).getId();
		log.info("________ReturnId: " + returnId);
		lmsReturnHelper.processOpenBoxReturn(returnId.toString(), EnumSCM.PICKED_UP_QCP_APPROVED_After_trip_close);
		
		
	}
	@Test(groups = {"P0", "Smoke", "Regression"}, priority = 2, description = "ID: C496 , LMS E2E flow from order placement to DL and than create return and process return till receive back in WH", invocationCount = 1)
	public void LMS_Mock_OpenBoxPickUpOnHoldReject() throws Exception {
		String orderID = lmsHepler.createMockOrder(EnumSCM.DL, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.NORMAL, "cod", false, true);
		OrderReleaseEntry orderReleaseEntry = omsServiceHelper.getOrderReleaseEntry(omsServiceHelper.getReleaseId(orderID));
		OrderLineEntry lineEntry = omsServiceHelper.getOrderLineEntry(orderReleaseEntry.getOrderLines().get(0).getId().toString());
		ReturnResponse returnResponse = rmsServiceHelper.createReturn(lineEntry.getId(), ReturnType.NORMAL, ReturnMode.OPEN_BOX_PICKUP, 1, 49L, RefundMode.NEFT, "418", "test Open box address ", "6135071", "560068", "Bangalore", "Karnataka", "India", "8149881455");
		Assert.assertEquals(returnResponse.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Return creation failed");
		Long returnId = returnResponse.getData().get(0).getId();
		log.info("________ReturnId: " + returnId);
		lmsReturnHelper.processOpenBoxReturn(returnId.toString(), EnumSCM.PICKED_UP_QCP_REJECTED_After_trip_close);
		
		
	}
	@Test(groups = {"P0", "Smoke", "Regression"}, priority = 2, description = "ID: C496 , LMS E2E flow from order placement to DL and than create return and process return till receive back in WH", invocationCount = 1)
	public void test() throws Exception {
		
		
		String releaseId="2147613971311";
		//Creating 3rd order with different mobile number
		List<ReleaseEntry> releaseEntries = new ArrayList<>();
		releaseEntries.add(new ReleaseEntry.Builder(releaseId, ReleaseStatus.SH).shipmentSource(ShipmentSource.MYNTRA).build());
		processRelease.processReleaseToStatus(processRelease.getReleaseEntryList(releaseEntries));
		
		
	}
	
	
	@Test(groups = {"P0", "Smoke", "Regression"}, priority = 2, description = "ID: C496 , LMS E2E flow from order placement to DL and than create return and process return till receive back in WH", invocationCount = 1)
	public void LMS_Mock_ClosedBoxDEPickUpSuccessful() throws Exception {
		//TODO : upload DE tracking numbers , enable DE serviceability for 400053
		//TODO : manifest function should take courier and return type as a parameter
		//TODO : enable DE for another pincode, so create 2 more returns with the other pincode for DE
		//TODO : enable another pincode for IP and then create 2 more returns with same data
		//TODO : find an address ID existing in DB and then create return for the same return id - see function fetchAddressIdForLogin()
		//Before creating any returns, manifest any old Closed Box return so that it does not go into this same pickup
		lmsReturnHelper.manifestClosedBoxPickups();
		
		//Create 1st order, place 2 returns for both the items in the order, have same addressId, address, mobile number, return type,courier, pincode
		
		String orderID = lmsHepler.createMockOrder(EnumSCM.DL, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.NORMAL, "cod", false, true);
		OrderReleaseEntry orderReleaseEntry = omsServiceHelper.getOrderReleaseEntry(omsServiceHelper.getReleaseId(orderID));
		OrderLineEntry lineEntry = omsServiceHelper.getOrderLineEntry(orderReleaseEntry.getOrderLines().get(0).getId().toString());
		
		ReturnResponse returnResponse = rmsServiceHelper.createReturn(lineEntry.getId(), ReturnType.NORMAL, ReturnMode.CLOSED_BOX_PICKUP, 1, 49L, RefundMode.NEFT, "418", "test DE Closed box address ", "6135071", "400053", "Bangalore", "Karnataka", "India","8149881455");
		Assert.assertEquals(returnResponse.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Return creation failed");
		Long returnID = returnResponse.getData().get(0).getId();
		
		String orderID1A = lmsHepler.createMockOrder(EnumSCM.DL, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.NORMAL, "cod", false, true);
		OrderReleaseEntry orderReleaseEntry1A = omsServiceHelper.getOrderReleaseEntry(omsServiceHelper.getReleaseId(orderID1A));
		OrderLineEntry lineEntry1A = omsServiceHelper.getOrderLineEntry(orderReleaseEntry1A.getOrderLines().get(0).getId().toString());
		
		ReturnResponse returnResponse2 = rmsServiceHelper.createReturn(lineEntry1A.getId(), ReturnType.NORMAL, ReturnMode.CLOSED_BOX_PICKUP, 1, 49L, RefundMode.NEFT, "418", "test DE Closed box address ", "6135071", "400053", "Bangalore", "Karnataka", "India","8149881455");
		Assert.assertEquals(returnResponse.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Return creation failed");
		Long returnID2 = returnResponse2.getData().get(0).getId();
		
		lmsReturnHelper.manifestClosedBoxPickups();
			
			log.info("________ReturnId: " + returnID);
		log.info("________ReturnId: " + returnID2);
		lmsReturnHelper.validateClosedBoxRmsLmsReturnCreationV2(String.valueOf(returnID),"DE");
		lmsReturnHelper.validateClosedBoxRmsLmsReturnCreationV2(String.valueOf(returnID2),"DE");
		
		// Creating next order, placing 2 returns with same addres
		String orderID2 = lmsHepler.createMockOrder(EnumSCM.DL, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.NORMAL, "cod", false, true);
		OrderReleaseEntry orderReleaseEntry2 = omsServiceHelper.getOrderReleaseEntry(omsServiceHelper.getReleaseId(orderID2));
		OrderLineEntry lineEntry3 = omsServiceHelper.getOrderLineEntry(orderReleaseEntry2.getOrderLines().get(0).getId().toString());
		
		
		String orderID2A = lmsHepler.createMockOrder(EnumSCM.DL, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.NORMAL, "cod", false, true);
		OrderReleaseEntry orderReleaseEntry2A = omsServiceHelper.getOrderReleaseEntry(omsServiceHelper.getReleaseId(orderID2A));
		OrderLineEntry lineEntry4 = omsServiceHelper.getOrderLineEntry(orderReleaseEntry2A.getOrderLines().get(0).getId().toString());
		
		
		ReturnResponse returnResponse3 = rmsServiceHelper.createReturn(lineEntry3.getId(), ReturnType.NORMAL, ReturnMode.CLOSED_BOX_PICKUP, 1, 49L, RefundMode.NEFT, "418", "test DE Closed box address- 2 ", "6135071", "400053", "Bangalore", "Karnataka", "India","8149881455");
		Assert.assertEquals(returnResponse3.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Return creation failed");
		Long returnID3 = returnResponse3.getData().get(0).getId();
		
		ReturnResponse returnResponse4 = rmsServiceHelper.createReturn(lineEntry4.getId(), ReturnType.NORMAL, ReturnMode.CLOSED_BOX_PICKUP, 1, 49L, RefundMode.NEFT, "418", "test DE Closed box address -2", "6135071", "400053", "Bangalore", "Karnataka", "India","8149881455");
		Assert.assertEquals(returnResponse4.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Return creation failed");
		Long returnID4 = returnResponse4.getData().get(0).getId();
		
		log.info("________ReturnId: " + returnID3);
		log.info("________ReturnId: " + returnID4);
		lmsReturnHelper.validateClosedBoxRmsLmsReturnCreationV2(String.valueOf(returnID3),"DE");
		lmsReturnHelper.validateClosedBoxRmsLmsReturnCreationV2(String.valueOf(returnID4),"DE");
		
		//Creating 3rd order with different mobile number
		String orderID3 = lmsHepler.createMockOrder(EnumSCM.DL, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.NORMAL, "cod", false, true);
		OrderReleaseEntry orderReleaseEntry3 = omsServiceHelper.getOrderReleaseEntry(omsServiceHelper.getReleaseId(orderID3));
		OrderLineEntry lineEntry5 = omsServiceHelper.getOrderLineEntry(orderReleaseEntry3.getOrderLines().get(0).getId().toString());
		
		String orderID3A = lmsHepler.createMockOrder(EnumSCM.DL, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.NORMAL, "cod", false, true);
		OrderReleaseEntry orderReleaseEntry3A = omsServiceHelper.getOrderReleaseEntry(omsServiceHelper.getReleaseId(orderID3A));

		OrderLineEntry lineEntry6 = omsServiceHelper.getOrderLineEntry(orderReleaseEntry3A.getOrderLines().get(0).getId().toString());
		
		
		ReturnResponse returnResponse5 = rmsServiceHelper.createReturn(lineEntry5.getId(), ReturnType.NORMAL, ReturnMode.CLOSED_BOX_PICKUP, 1, 49L, RefundMode.NEFT, "418", "test DE Closed box address -2", "6135071", "400053", "Bangalore", "Karnataka", "India","9663328978");
		Assert.assertEquals(returnResponse5.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Return creation failed");
		Long returnID5 = returnResponse5.getData().get(0).getId();
		
		ReturnResponse returnResponse6 = rmsServiceHelper.createReturn(lineEntry6.getId(), ReturnType.NORMAL, ReturnMode.CLOSED_BOX_PICKUP, 1, 49L, RefundMode.NEFT, "418", "test DE Closed box address -2", "6135071", "400053", "Bangalore", "Karnataka", "India","9663328978");
		Assert.assertEquals(returnResponse6.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Return creation failed");
		Long returnID6 = returnResponse6.getData().get(0).getId();
		lmsReturnHelper.manifestClosedBoxPickups();
		
		log.info("________ReturnId: " + returnID5);
		log.info("________ReturnId: " + returnID6);
		lmsReturnHelper.validateClosedBoxRmsLmsReturnCreationV2(String.valueOf(returnID5),"DE");
		lmsReturnHelper.validateClosedBoxRmsLmsReturnCreationV2(String.valueOf(returnID6),"DE");
		
		Thread.sleep(5000);
		//Now manifest
		lmsReturnHelper.manifestClosedBoxPickups();
		Thread.sleep(5000);

  
		//Verify each pickup is manifested
		Assert.assertTrue(lmsReturnHelper.isPickupManifested(String.valueOf(returnID)),"Pickup associated with return - "+returnID+" is NOT manifested");
		Assert.assertTrue(lmsReturnHelper.isPickupManifested(String.valueOf(returnID2)),"Pickup associated with return - "+returnID2+" is NOT manifested");
		Assert.assertTrue(lmsReturnHelper.isPickupManifested(String.valueOf(returnID3)),"Pickup associated with return - "+returnID3+" is NOT manifested");
		Assert.assertTrue(lmsReturnHelper.isPickupManifested(String.valueOf(returnID4)),"Pickup associated with return - "+returnID4+" is NOT manifested");
		Assert.assertTrue(lmsReturnHelper.isPickupManifested(String.valueOf(returnID5)),"Pickup associated with return - "+returnID5+" is NOT manifested");
		Assert.assertTrue(lmsReturnHelper.isPickupManifested(String.valueOf(returnID6)),"Pickup associated with return - "+returnID6+" is NOT manifested");
		
		//Verify consolidation of return 1&2 , 3&4, 5&6 respectively
		
		Assert.assertTrue(lmsReturnHelper.verifyClosedBoxConsolidation(String.valueOf(returnID),String.valueOf(returnID2)),"The return - "+returnID+" , and return - "+returnID2+" are not consolidated into ONE pickup");
		Assert.assertTrue(lmsReturnHelper.verifyClosedBoxConsolidation(String.valueOf(returnID3),String.valueOf(returnID4)),"The return - "+returnID3+" , and return - "+returnID4+" are not consolidated into ONE pickup");
		Assert.assertTrue(lmsReturnHelper.verifyClosedBoxConsolidation(String.valueOf(returnID5),String.valueOf(returnID6)),"The return - "+returnID5+" , and return - "+returnID6+" are not consolidated into ONE pickup");
		Assert.assertFalse(lmsReturnHelper.verifyClosedBoxConsolidation(String.valueOf(returnID5),String.valueOf(returnID)),"The return - "+returnID5+" , and return - "+returnID+" are  consolidated into ONE pickup inspite of differences");
		Assert.assertFalse(lmsReturnHelper.verifyClosedBoxConsolidation(String.valueOf(returnID5),String.valueOf(returnID2)),"The return - "+returnID5+" , and return - "+returnID2+" are  consolidated into ONE pickup inspite of differences");
		Assert.assertFalse(lmsReturnHelper.verifyClosedBoxConsolidation(String.valueOf(returnID3),String.valueOf(returnID6)),"The return - "+returnID3+" , and return - "+returnID6+" are  consolidated into ONE pickup inspite of differences");
		lmsReturnHelper.processClosedBoxPickup(String.valueOf(returnID), EnumSCM.PICKED_UP_SUCCESSFULLY);
		
		
		
	}
	@Test(groups = {"Reverse", "Smoke", "Regression"}, priority = 2, description = "ID: C539 , ")
	public void E2E_LMS_processRetrun_ONHOLD_APPROVED_With_Mock() throws Exception {
		
		
		lmsReturnHelper.manifestClosedBoxPickups();
		
		//Create 1st order, place 2 returns for both the items in the order, have same addressId, address, mobile number, return type,courier, pincode
		
		String orderID = lmsHepler.createMockOrder(EnumSCM.DL, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.NORMAL, "cod", false, true);
		OrderReleaseEntry orderReleaseEntry = omsServiceHelper.getOrderReleaseEntry(omsServiceHelper.getReleaseId(orderID));
		OrderLineEntry lineEntry = omsServiceHelper.getOrderLineEntry(orderReleaseEntry.getOrderLines().get(0).getId().toString());
		
		ReturnResponse returnResponse = rmsServiceHelper.createReturn(lineEntry.getId(), ReturnType.NORMAL, ReturnMode.CLOSED_BOX_PICKUP, 1, 49L, RefundMode.NEFT, "418", "test DE Closed box address ", "6135071", "400053", "Bangalore", "Karnataka", "India","8149881455");
		Assert.assertEquals(returnResponse.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Return creation failed");
		Long returnID = returnResponse.getData().get(0).getId();
		
		lmsReturnHelper.manifestClosedBoxPickups();
		Assert.assertTrue(lmsReturnHelper.isPickupManifested(String.valueOf(returnID)),"Pickup associated with return - "+returnID+" is NOT manifested");
		
		
		lmsReturnHelper.processClosedBoxPickup(String.valueOf(returnID), EnumSCM.PICKUP_SUCCESSFUL_ONHOLD_APPROVE);
		
		
		
	}
	
	@Test(groups = {"Reverse", "Smoke", "Regression"}, priority = 2, description = "ID: C539 , ")
	public void E2E_LMS_processRetrun_ONHOLD_REJECTED_With_Mock() throws Exception {
		
		lmsReturnHelper.manifestClosedBoxPickups();
		
		//Create 1st order, place 2 returns for both the items in the order, have same addressId, address, mobile number, return type,courier, pincode
		
		String orderID = lmsHepler.createMockOrder(EnumSCM.DL, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.NORMAL, "cod", false, true);
		OrderReleaseEntry orderReleaseEntry = omsServiceHelper.getOrderReleaseEntry(omsServiceHelper.getReleaseId(orderID));
		OrderLineEntry lineEntry = omsServiceHelper.getOrderLineEntry(orderReleaseEntry.getOrderLines().get(0).getId().toString());
		
		ReturnResponse returnResponse = rmsServiceHelper.createReturn(lineEntry.getId(), ReturnType.NORMAL, ReturnMode.CLOSED_BOX_PICKUP, 1, 49L, RefundMode.NEFT, "418", "test DE Closed box address ", "6135071", "400053", "Bangalore", "Karnataka", "India","8149881455");
		Assert.assertEquals(returnResponse.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Return creation failed");
		Long returnID = returnResponse.getData().get(0).getId();
		
		lmsReturnHelper.manifestClosedBoxPickups();
		Assert.assertTrue(lmsReturnHelper.isPickupManifested(String.valueOf(returnID)),"Pickup associated with return - "+returnID+" is NOT manifested");
		
		
		lmsReturnHelper.processClosedBoxPickup(String.valueOf(returnID), EnumSCM.PICKUP_SUCCESSFUL_ONHOLD_REJECTED);
		
		
		
	}
	
	
	
	@Test(groups = {"Reverse", "P0", "Smoke", "Regression"}, description = "", priority = 2)
	public void E2E_LMS_ProcessExchangeWithMock() throws Exception {
		
		/*
		//Create Exchange
		String orderReleaseId="2147613898032";
		String orderID="2147491174";
		OrderLineEntry orderLineEntry = omsServiceHelper.getOrderLineEntries(orderReleaseId).get(0);
		String lineID = ""+orderLineEntry.getId();
		ExchangeOrderResponse exchangeOrderResponse =(ExchangeOrderResponse) ppsServiceHelper.createExchange(""+orderID, lineID, "DNL", 1, OMSTCConstants.OtherSkus.skuId_3831);
		String exchangeOrderID = omsServiceHelper.getOrderEntry(exchangeOrderResponse.getExchangeOrderId()).getId().toString();
		String exReleaseId = omsServiceHelper.getReleaseIdFromStoreOrderId(exchangeOrderResponse.getExchangeOrderId());
		Assert.assertTrue(omsServiceHelper.validatePacketStatusInOMS(exReleaseId,EnumSCM.WP,10));
		
		*/
		String exReleaseId="2147613898446";
		List<ReleaseEntry> releaseEntries = new ArrayList<>();
		releaseEntries.add(new ReleaseEntry.Builder(exReleaseId, ReleaseStatus.PK).shipmentSource(ShipmentSource.MYNTRA).build());
		processRelease.processReleaseToStatus(processRelease.getReleaseEntryList(releaseEntries));
		
		
		
		//processRelease.processReleaseToStatus(new ReleaseEntry.Builder(exReleaseId, ReleaseStatus.DL).force(true).build());
//        String returnID = lmsHepler.getReturnIdFromOrderId(""+packetId);
//        processRelease.processReleaseToStatus(new ReleaseEntry.Builder(exReleaseId, ReleaseStatus.SH).force(true).build());
//        Assert.assertTrue(lmsReturnHelper.validatePickupShipmentStatusInLMS(returnID,EnumSCM.PICKUP_SUCCESSFUL,3));
//        lmsServiceHelper.validateRmsStatusAndRefund(returnID,EnumSCM.RPU,false,0L);
//        Map<String, Object> returnShipment = DBUtilities.exSelectQueryForSingleRecord("select * from return_shipment where source_return_id = " + returnID, "lms");
//        long destWarehouseId = (long) returnShipment.get("return_warehouse_id");
//        lmsServiceHelper.transferShipmentBackToWH(returnID, destWarehouseId, 5, "DC", "WH");
	}
	
	@Test(groups = {"P0", "Smoke", "Regression"}, priority = 2, description = "ID: C496 , LMS E2E flow from order placement to DL and than create return and process return till receive back in WH", invocationCount = 1)
	public void testRTO() throws Exception {
		
		String orderID = lmsHepler.createMockOrder(EnumSCM.SH, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.NORMAL, "cod", false, true);
		OrderReleaseEntry orderReleaseEntry = omsServiceHelper.getOrderReleaseEntry(omsServiceHelper.getReleaseId(orderID));
		OrderLineEntry lineEntry = omsServiceHelper.getOrderLineEntry(orderReleaseEntry.getOrderLines().get(0).getId().toString());
		/*
		String exReleaseId="2147613898402";
		List<ReleaseEntry> releaseEntries = new ArrayList<>();
		releaseEntries.add(new ReleaseEntry.Builder(exReleaseId, ReleaseStatus.RTO).shipmentSource(ShipmentSource.MYNTRA).build());
		processRelease.processReleaseToStatus(processRelease.getReleaseEntryList(releaseEntries));
		*/
		
	}
	
	
/*
	@SuppressWarnings("deprecation")
	@Test(groups = {"Reverse", "Smoke", "Regression"}, priority = 2, description = "ID: C539 , ")
	public void E2E_LMS_processRetrun_PICKUP_SUCCESSFUL_With_Mock() throws Exception {
		String orderID = lmsHepler.createMockOrder(EnumSCM.DL, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.NORMAL, "DC", false, true);
		PacketEntry orderReleaseEntry = omsServiceHelper.getPacketEntry(omsServiceHelper.getPacketId(orderID));
		ReturnResponse returnResponse = rmsServiceHelper.createReturn(orderReleaseEntry.getOrderLines().get(0).getId().toString(), 1, ReturnMode.PICKUP, 11L, RefundMode.OR, "1234567890", "Myntra test lms automation", "Bangalore", "KA", LMS_PINCODE.ML_BLR, "ML");
		Assert.assertEquals(returnResponse.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Return creation failed");
		String returnID = "" + returnResponse.getData().get(0).getId();
		lmsServiceHelper.validateRmsLmsReturnCreationWithPlateformOnhold(returnID);
		lmsServiceHelper.returnApproveOrReject(returnID, EnumSCM.APPROVED);
		Thread.sleep(3000L);
		Assert.assertEquals(lmsHepler.getReturnApprovalStatus(returnID), EnumSCM.APPROVED, "Approval status is not Rejected");
		Assert.assertTrue(lmsServiceHelper.validateOrderStatusInML(returnID, EnumSCM.UNASSIGNED, 5), "Return still not in ML");
		lmsServiceHelper.processReturnInLMS("" + returnID, EnumSCM.PICKED_UP_SUCCESSFULLY);
	}
	
	@SuppressWarnings("deprecation")
	@Test(groups = {"Reverse", "Smoke", "Regression"}, priority = 2, description = "ID: C494 , E2E_LMS_processRetrun_ONHOLD_WITH_PLATEFORM_PICKUP_SUCCESSFUL")
	public void E2E_LMS_processRetrun_ONHOLD_WITH_PLATEFORM_PICKUP_SUCCESSFUL() throws Exception {
		String orderID = lmsHepler.createMockOrder(EnumSCM.DL, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.NORMAL,"DC",false, true);
		PacketEntry orderReleaseEntry = omsServiceHelper.getPacketEntry(omsServiceHelper.getPacketId(orderID));
		ReturnResponse returnResponse = rmsServiceHelper.createReturn(orderReleaseEntry.getOrderLines().get(0).getId().toString(), 1, ReturnMode.PICKUP, 11L, RefundMode.OR, "1234567890", "Myntra test lms automation", "Bangalore", "KA", LMS_PINCODE.ML_BLR, "ML");
		Assert.assertEquals(returnResponse.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Return creation failed");
		String returnID = ""+returnResponse.getData().get(0).getId();
		lmsServiceHelper.validateRmsLmsReturnCreationWithPlateformOnhold(returnID);
		lmsServiceHelper.returnApproveOrReject(returnID, EnumSCM.APPROVED);
		Thread.sleep(3000L);
		Assert.assertEquals(lmsHepler.getReturnApprovalStatus(returnID), EnumSCM.APPROVED, "Approval status is not Rejected");
		Assert.assertTrue(lmsServiceHelper.validateOrderStatusInML(returnID,EnumSCM.UNASSIGNED, 5),"Return still not in ML");
		lmsServiceHelper.processReturnInLMS(""+returnID, EnumSCM.PICKED_UP_SUCCESSFULLY);
	}
	
	@SuppressWarnings("deprecation")
	@Test(groups = {"Reverse", "Smoke", "Regression"}, priority = 2, description = "ID: C495 , E2E_LMS_processRetrun_ONHOLD_WITH_PLATEFORM_REJECT")
	public void E2E_LMS_processRetrun_ONHOLD_WITH_PLATEFORM_REJECT() throws Exception {
		String orderID = lmsHepler.createMockOrder(EnumSCM.DL, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.NORMAL,"DC",false, true);
		PacketEntry orderReleaseEntry = omsServiceHelper.getPacketEntry(omsServiceHelper.getPacketId(orderID));
		ReturnResponse returnResponse = rmsServiceHelper.createReturn(orderReleaseEntry.getOrderLines().get(0).getId().toString(), 1, ReturnMode.PICKUP, 11L, RefundMode.OR, "1234567890", "Myntra test lms automation", "Bangalore", "KA", LMS_PINCODE.ML_BLR, "ML");
		Assert.assertEquals(returnResponse.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Return creation failed");
		String returnID = ""+returnResponse.getData().get(0).getId();
		lmsServiceHelper.validateRmsLmsReturnCreationWithPlateformOnhold(returnID);
		lmsServiceHelper.returnApproveOrReject(returnID, EnumSCM.REJECTED);
		Thread.sleep(3000L);
		Assert.assertEquals(lmsHepler.getReturnApprovalStatus(returnID), EnumSCM.REJECTED, "Approval status is not Rejected");
		Assert.assertFalse(lmsServiceHelper.validateOrderStatusInML(returnID,EnumSCM.UNASSIGNED, 5),"Return still not in ML");
	}
	
	@SuppressWarnings("deprecation")
	@Test(groups = {"Reverse", "Smoke", "Regression"}, priority = 2, description = "ID: C497 , LMS E2E flow from order placement to DL and than create return and process return till receive back in WH")
	public void E2E_LMS_processRetrun_PICKUP_SUCCESSFUL_CC() throws Exception {
		String orderID = lmsHepler.createMockOrder(EnumSCM.DL, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.NORMAL,"DC",false, true);
		PacketEntry orderReleaseEntry = omsServiceHelper.getPacketEntry(omsServiceHelper.getPacketId(orderID));
		ReturnResponse returnResponse = rmsServiceHelper.createReturn(orderReleaseEntry.getOrderLines().get(0).getId().toString(), 1, ReturnMode.PICKUP, 27L, RefundMode.OR, "1234567890", "Myntra test lms automation", "Bangalore", "KA", LMS_PINCODE.ML_BLR, "ML");
		Assert.assertEquals(returnResponse.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Return creation failed");
		String returnID = ""+returnResponse.getData().get(0).getId();
		lmsServiceHelper.processReturnInLMS(returnID, EnumSCM.PICKED_UP_SUCCESSFULLY);
	}
	
	@SuppressWarnings("deprecation")
	@Test(groups = {"Reverse", "Smoke", "Regression"}, priority = 2, description = "ID: C502 , LMS E2E flow from order placement to DL and than create return and process return till receive back in WH")
	public void E2E_LMS_processRetrun_PICKUP_SUCCESSFUL_Wallet() throws Exception {
		String orderID = lmsHepler.createMockOrder(EnumSCM.DL, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.NORMAL,"WALLET",false, true);
		PacketEntry orderReleaseEntry = omsServiceHelper.getPacketEntry(omsServiceHelper.getPacketId(orderID));
		ReturnResponse returnResponse = rmsServiceHelper.createReturn(orderReleaseEntry.getOrderLines().get(0).getId().toString(), 1, ReturnMode.PICKUP, 27L, RefundMode.WALLET, "1234567890", "Myntra test lms automation", "Bangalore", "KA", LMS_PINCODE.ML_BLR, "ML");
		Assert.assertEquals(returnResponse.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Return creation failed");
		String returnID = ""+returnResponse.getData().get(0).getId();
		lmsServiceHelper.processReturnInLMS(returnID, EnumSCM.PICKED_UP_SUCCESSFULLY);
	}
	
	@SuppressWarnings("deprecation")
	@Test(groups = {"Reverse", "P0", "Smoke", "Regression"}, priority = 2, description = "ID: C503 , Return same sku with 3qty")
	public void E2E_LMS_processRetrun_PICKUP_SUCCESSFUL_With3qty() throws Exception {
		String[] sku = {"3866:3"};
		String orderID = lmsServiceHelper.createAndMarkOrderToStatus(LMS_LOGIN.LogIn94, sku, ReleaseStatus.DL, EnumSCM.DL, LMS_PINCODE.ML_BLR);
		PacketEntry orderReleaseEntry = omsServiceHelper.getPacketEntry(omsServiceHelper.getPacketId(orderID));
		ReturnResponse returnResponse = rmsServiceHelper.createReturn(orderReleaseEntry.getOrderLines().get(0).getId().toString(), 3, ReturnMode.PICKUP, 27L, RefundMode.CASHBACK, "1234567890", "Myntra test lms automation", "Bangalore", "KA", LMS_PINCODE.ML_BLR, "ML");
		Assert.assertEquals(returnResponse.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Return creation failed");
		String returnID = ""+returnResponse.getData().get(0).getId();
		lmsServiceHelper.processReturnInLMS(returnID, EnumSCM.PICKED_UP_SUCCESSFULLY);
	}
	
	@SuppressWarnings("deprecation")
	@Test(groups = {"Reverse", "Smoke", "Regression"}, priority = 2, description = "ID: C499 , E2E_LMS_processRetrun_PICKUP_SUCCESSFUL_QC_PENDING_APPROVED_Before_trip_close")
	public void E2E_LMS_processRetrun_PICKUP_SUCCESSFUL_QC_PENDING_APPROVED_Before_trip_close() throws Exception {
		String orderID = lmsHepler.createMockOrder(EnumSCM.DL, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.NORMAL,"cod",false, true);
		PacketEntry orderReleaseEntry = omsServiceHelper.getPacketEntry(omsServiceHelper.getPacketId(orderID));
		ReturnResponse returnResponse = rmsServiceHelper.createReturn(orderReleaseEntry.getOrderLines().get(0).getId().toString(), 1, ReturnMode.PICKUP, 27L, RefundMode.CASHBACK, "1234567890", "Myntra test lms automation", "Bangalore", "KA", LMS_PINCODE.ML_BLR, "ML");
		Assert.assertEquals(returnResponse.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Return creation failed");
		String returnID = ""+returnResponse.getData().get(0).getId();
		lmsServiceHelper.processReturnInLMS(returnID, EnumSCM.PICKED_UP_QCP_APPROVED_Before_trip_close);
	}
	
	@SuppressWarnings("deprecation")
	@Test(groups = {"Reverse", "Smoke", "Regression"}, priority = 2, description = "ID: C501 , E2E_LMS_processRetrun_PICKUP_SUCCESSFUL_QC_PENDING_REJECTED_Before_trip_close")
	public void E2E_LMS_processRetrun_PICKUP_SUCCESSFUL_QC_PENDING_REJECTED_Before_trip_close() throws Exception {
		String orderID = lmsHepler.createMockOrder(EnumSCM.DL, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.NORMAL,"cod",false, true);
		PacketEntry orderReleaseEntry = omsServiceHelper.getPacketEntry(omsServiceHelper.getPacketId(orderID));
		ReturnResponse returnResponse = rmsServiceHelper.createReturn(orderReleaseEntry.getOrderLines().get(0).getId().toString(), 1, ReturnMode.PICKUP, 27L, RefundMode.CASHBACK, "1234567890", "Myntra test lms automation", "Bangalore", "KA", LMS_PINCODE.ML_BLR, "ML");
		Assert.assertEquals(returnResponse.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Return creation failed");
		String returnID = ""+returnResponse.getData().get(0).getId();
		lmsServiceHelper.processReturnInLMS(returnID, EnumSCM.PICKED_UP_QCP_REJECTED_Before_trip_close);
	}
	
	@SuppressWarnings("deprecation")
	@Test(groups = {"Reverse", "P0", "Smoke", "Regression"}, priority = 2, description = "ID: C498 , ")
	public void E2E_LMS_processRetrun_PICKUP_SUCCESSFUL_QC_PENDING_APPROVED_After_trip_close() throws Exception {
		String orderID = lmsHepler.createMockOrder(EnumSCM.DL, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.NORMAL,"cod",false, true);
		PacketEntry orderReleaseEntry = omsServiceHelper.getPacketEntry(omsServiceHelper.getPacketId(orderID));
		ReturnResponse returnResponse = rmsServiceHelper.createReturn(orderReleaseEntry.getOrderLines().get(0).getId().toString(), 1, ReturnMode.PICKUP, 27L, RefundMode.CASHBACK, "1234567890", "Myntra test lms automation", "Bangalore", "KA", LMS_PINCODE.ML_BLR, "ML");
		Assert.assertEquals(returnResponse.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Return creation failed");
		String returnID = ""+returnResponse.getData().get(0).getId();
		lmsServiceHelper.processReturnInLMS(returnID, EnumSCM.PICKED_UP_QCP_APPROVED_After_trip_close);
	}
	
	@SuppressWarnings("deprecation")
	@Test(groups = {"Reverse", "P0", "Smoke", "Regression"}, priority = 2, description = "ID: C500 , ")
	public void E2E_LMS_processRetrun_PICKUP_SUCCESSFUL_QC_PENDING_REJECTED_After_trip_close() throws Exception {
		String orderID = lmsHepler.createMockOrder(EnumSCM.DL, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.NORMAL,"cod",false, true);
		PacketEntry orderReleaseEntry = omsServiceHelper.getPacketEntry(omsServiceHelper.getPacketId(orderID));
		ReturnResponse returnResponse = rmsServiceHelper.createReturn(orderReleaseEntry.getOrderLines().get(0).getId().toString(), 1, ReturnMode.PICKUP, 27L, RefundMode.CASHBACK, "1234567890", "Myntra test lms automation", "Bangalore", "KA", LMS_PINCODE.ML_BLR, "ML");
		Assert.assertEquals(returnResponse.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Return creation failed");
		String returnID = ""+returnResponse.getData().get(0).getId();
		lmsServiceHelper.processReturnInLMS(returnID, EnumSCM.PICKED_UP_QCP_REJECTED_After_trip_close);
	}
	
	@SuppressWarnings("deprecation")
	@Test(groups = {"Reverse", "P0", "Smoke", "Regression"}, priority = 2, description = "ID: C491 , ")
	public void E2E_LMS_processRetrun_ONHOLD_PICKUP_WITH_CUSTOMER_REJECTED() throws Exception {
		String orderID = lmsHepler.createMockOrder(EnumSCM.DL, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.NORMAL,"cod",false, true);
		PacketEntry orderReleaseEntry = omsServiceHelper.getPacketEntry(omsServiceHelper.getPacketId(orderID));
		ReturnResponse returnResponse = rmsServiceHelper.createReturn(orderReleaseEntry.getOrderLines().get(0).getId().toString(), 1, ReturnMode.PICKUP, 27L, RefundMode.CASHBACK, "1234567890", "Myntra test lms automation", "Bangalore", "KA", LMS_PINCODE.ML_BLR, "ML");
		Assert.assertEquals(returnResponse.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Return creation failed");
		String returnID = ""+returnResponse.getData().get(0).getId();
		lmsServiceHelper.processReturnInLMS(returnID, EnumSCM.ONHOLD_PICKUP_WITH_CUSTOMER_REJECT);
	}
	
	@SuppressWarnings("deprecation")
	@Test(groups = {"Reverse", "Smoke", "Regression"}, priority = 2, description = "ID: C490 , ")
	public void E2E_LMS_processRetrun_ONHOLD_PICKUP_WITH_CUSTOMER_APPROVED_PROCESS() throws Exception {
		String orderId = null;
		try {
			orderId = lmsHepler.getOrderFromTestDB("'DL','SH','PK'", "ML", LMS_PINCODE.ML_BLR, EnumSCM.DL, EnumSCM.NORMAL, "cod");
			String packetId = omsServiceHelper.getPacketId(orderId);
			String releaseId = omsServiceHelper.getReleaseId(orderId);
			if (!omsServiceHelper.getPacketEntry(packetId).getStatus().toString().equals(EnumSCM.DL.toString())) {
				
				List<ReleaseEntry> releaseEntries = new ArrayList<>();
		        releaseEntries.add(new ReleaseEntry.Builder(releaseId, ReleaseStatus.DL).force(true).build());
		        processRelease.processReleaseToStatus(processRelease.getReleaseEntryList(releaseEntries));
			}
			PacketEntry orderReleaseEntry = omsServiceHelper.getPacketEntry(omsServiceHelper.getPacketId(orderId));
			ReturnResponse returnResponse = rmsServiceHelper.createReturn(orderReleaseEntry.getOrderLines().get(0).getId().toString(), 1, ReturnMode.PICKUP, 27L, RefundMode.CASHBACK, "1234567890", "Myntra test lms automation", "Bangalore", "KA", LMS_PINCODE.ML_BLR, "ML");
			Assert.assertEquals(returnResponse.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Return creation failed");
			String returnID = ""+returnResponse.getData().get(0).getId();
			lmsServiceHelper.processReturnInLMS(returnID, EnumSCM.ONHOLD_PICKUP_WITH_CUSTOMER_APPROVE);
		} catch (Exception e) {
			String[] sku = {"3867:1"};
			String orderID = lmsServiceHelper.createAndMarkOrderToStatus(LMS_LOGIN.LogIn78, sku, ReleaseStatus.DL, EnumSCM.DL, LMS_PINCODE.ML_BLR);
			PacketEntry orderReleaseEntry = omsServiceHelper.getPacketEntry(omsServiceHelper.getPacketId(orderID));
			ReturnResponse returnResponse = rmsServiceHelper.createReturn(orderReleaseEntry.getOrderLines().get(0).getId().toString(), 1, ReturnMode.PICKUP, 27L, RefundMode.CASHBACK, "1234567890", "Myntra test lms automation", "Bangalore", "KA", LMS_PINCODE.ML_BLR, "ML");
			Assert.assertEquals(returnResponse.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Return creation failed");
			String returnID = ""+returnResponse.getData().get(0).getId();
			lmsServiceHelper.processReturnInLMS(returnID, EnumSCM.ONHOLD_PICKUP_WITH_CUSTOMER_APPROVE);
		} finally {
			lmsHepler.deleteTestOrder(orderId);
		}
	}
	
	@SuppressWarnings("deprecation")
	@Test(groups = {"Reverse", "Smoke", "Regression"}, priority = 2, description = "ID: C493 , ")
	public void E2E_LMS_processRetrun_ONHOLD_PICKUP_WITH_DC_REJECTED() throws Exception {
		String orderID = lmsHepler.createMockOrder(EnumSCM.DL, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.NORMAL,"cod",false, true);
		PacketEntry orderReleaseEntry = omsServiceHelper.getPacketEntry(omsServiceHelper.getPacketId(orderID));
		ReturnResponse returnResponse = rmsServiceHelper.createReturn(orderReleaseEntry.getOrderLines().get(0).getId().toString(), 1, ReturnMode.PICKUP, 27L, RefundMode.CASHBACK, "1234567890", "Myntra test lms automation", "Bangalore", "KA", LMS_PINCODE.ML_BLR, "ML");
		Assert.assertEquals(returnResponse.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Return creation failed");
		String returnID = ""+returnResponse.getData().get(0).getId();
		lmsServiceHelper.processReturnInLMS(returnID, EnumSCM.ONHOLD_PICKUP_WITH_DC_REJECT);
	}
	
	@SuppressWarnings("deprecation")
	@Test(groups = {"Reverse", "Smoke", "Regression"}, priority = 2, description = "ID: C492 , ")
	public void E2E_LMS_processRetrun_ONHOLD_PICKUP_WITH_DC_APPROVE_PROCESS() throws Exception {
		String orderID = lmsHepler.createMockOrder(EnumSCM.DL, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.NORMAL,"cod",false, true);
		PacketEntry orderReleaseEntry = omsServiceHelper.getPacketEntry(omsServiceHelper.getPacketId(orderID));
		ReturnResponse returnResponse = rmsServiceHelper.createReturn(orderReleaseEntry.getOrderLines().get(0).getId().toString(), 1, ReturnMode.PICKUP, 27L, RefundMode.CASHBACK, "1234567890", "Myntra test lms automation", "Bangalore", "KA", LMS_PINCODE.ML_BLR, "ML");
		Assert.assertEquals(returnResponse.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Return creation failed");
		String returnID = ""+returnResponse.getData().get(0).getId();
		lmsServiceHelper.processReturnInLMS(returnID, EnumSCM.ONHOLD_PICKUP_WITH_DC_APPROVE);
	}
	
	@SuppressWarnings("deprecation")
	@Test(groups = {"Reverse", "Smoke", "Regression"}, priority = 2, description = "ID: C487 , LMS E2E flow from order placement to DL and than create return and process return till receive back in WH")
	public void E2E_LMS_processRetrun_FAILED_PICKUP_AND_SUCCESS() throws Exception {
		String orderID = lmsHepler.createMockOrder(EnumSCM.DL, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.NORMAL,"cod",false, true);
		PacketEntry orderReleaseEntry = omsServiceHelper.getPacketEntry(omsServiceHelper.getPacketId(orderID));
		ReturnResponse returnResponse = rmsServiceHelper.createReturn(orderReleaseEntry.getOrderLines().get(0).getId().toString(), 1, ReturnMode.PICKUP, 27L, RefundMode.CASHBACK, "1234567890", "Myntra test lms automation", "Bangalore", "KA", LMS_PINCODE.ML_BLR, "ML");
		Assert.assertEquals(returnResponse.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Return creation failed");
		String returnID = ""+returnResponse.getData().get(0).getId();
		lmsServiceHelper.processReturnInLMS(returnID, EnumSCM.FAILED_PICKUP_AND_SUCCESS);
	}
	
	@SuppressWarnings("deprecation")
	@Test(groups = {"Reverse", "Smoke", "Regression"}, priority = 2, description = "ID: C488 , Failed pickup than PICKUP_DONE_QC_PENDING Than pickup success_pickup on Same Trip")
	public void E2E_LMS_processRetrun_FAILED_PICKUP_AND_SUCCESSON_ON_SAMETRIP() throws Exception {
		String orderID = lmsHepler.createMockOrder(EnumSCM.DL, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.NORMAL,"cod",false, true);
		PacketEntry orderReleaseEntry = omsServiceHelper.getPacketEntry(omsServiceHelper.getPacketId(orderID));
		ReturnResponse returnResponse = rmsServiceHelper.createReturn(orderReleaseEntry.getOrderLines().get(0).getId().toString(), 1, ReturnMode.PICKUP, 27L, RefundMode.CASHBACK, "1234567890", "Myntra test lms automation", "Bangalore", "KA", LMS_PINCODE.ML_BLR, "ML");
		Assert.assertEquals(returnResponse.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Return creation failed");
		String returnID = ""+returnResponse.getData().get(0).getId();
		lmsServiceHelper.processReturnInLMS(returnID, EnumSCM.FAILED_PICKUP_AND_SUCCESS_ON_SAMETRIP);
	}
	
	@SuppressWarnings("deprecation")
	@Test(groups = {"Reverse", "Smoke", "Regression"}, priority = 2, description = "ID: C486 , FAILED_PICKUP_AND_ONHOLD_PICKUP_WITH_CUSTOMER_REJECT")
	public void E2E_LMS_processRetrun_FAILED_PICKUP_AND_ONHOLD_PICKUP_WITH_CUSTOMER_REJECT() throws Exception {
		String orderID = lmsHepler.createMockOrder(EnumSCM.DL, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.NORMAL,"cod",false, true);
		PacketEntry orderReleaseEntry = omsServiceHelper.getPacketEntry(omsServiceHelper.getPacketId(orderID));
		ReturnResponse returnResponse = rmsServiceHelper.createReturn(orderReleaseEntry.getOrderLines().get(0).getId().toString(), 1, ReturnMode.PICKUP, 27L, RefundMode.CASHBACK, "1234567890", "Myntra test lms automation", "Bangalore", "KA", LMS_PINCODE.ML_BLR, "ML");
		Assert.assertEquals(returnResponse.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Return creation failed");
		String returnID = ""+returnResponse.getData().get(0).getId();
		lmsServiceHelper.processReturnInLMS(returnID, EnumSCM.FAILED_PICKUP_ONHOLD_PICKUP_WITH_CUSTOMER_REJECT);
	}
	
	@SuppressWarnings("deprecation")
	@Test(groups = {"Reverse", "Smoke", "Regression"}, priority = 2, description = "ID: C489 , ")
	public void E2E_LMS_processRetrun_FAILED_PICKUP_FAILED_PICKUP_AND_SUCCESS() throws Exception {
		String orderID = lmsHepler.createMockOrder(EnumSCM.DL, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.NORMAL,"cod",false, true);
		PacketEntry orderReleaseEntry = omsServiceHelper.getPacketEntry(omsServiceHelper.getPacketId(orderID));
		ReturnResponse returnResponse = rmsServiceHelper.createReturn(orderReleaseEntry.getOrderLines().get(0).getId().toString(), 1, ReturnMode.PICKUP, 27L, RefundMode.CASHBACK, "1234567890", "Myntra test lms automation", "Bangalore", "KA", LMS_PINCODE.ML_BLR, "ML");
		Assert.assertEquals(returnResponse.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Return creation failed");
		String returnID = ""+returnResponse.getData().get(0).getId();
		lmsServiceHelper.processReturnInLMS(returnID, EnumSCM.FAILED_PICKUP_FAILED_PICKUP_AND_SUCCESS);
	}
	
	@SuppressWarnings("deprecation")
	@Test(groups = {"Reverse", "Smoke", "Regression"}, priority = 2, description = "ID: C504 , ")
	public void E2E_LMS_processRetrun_RETURN_CANCELLED() throws Exception {
		String orderID = lmsHepler.createMockOrder(EnumSCM.DL, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.NORMAL,"cod",false, true);
		PacketEntry orderReleaseEntry = omsServiceHelper.getPacketEntry(omsServiceHelper.getPacketId(orderID));
		ReturnResponse returnResponse = rmsServiceHelper.createReturn(orderReleaseEntry.getOrderLines().get(0).getId().toString(), 1, ReturnMode.PICKUP, 27L, RefundMode.CASHBACK, "1234567890", "Myntra test lms automation", "Bangalore", "KA", LMS_PINCODE.ML_BLR, "ML");
		Assert.assertEquals(returnResponse.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Return creation failed");
		String returnID = ""+returnResponse.getData().get(0).getId();
		OrderResponse orderResponse = lmsServiceHelper.cancelPickup("" + returnID);
		Assert.assertEquals(orderResponse.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to update status to Cancel in lms");
		Thread.sleep(2000L);
		Assert.assertEquals(lmsHepler.getReturnStatus(returnID), EnumSCM.RETURN_REJECTED, "Return is not updated to 'RETURN_REJECTED' in Pickup");
	}
	
	@Test(groups = {"Reverse", "Smoke", "Regression"}, priority = 2, description = "ID: C512 , Self Ship and move back the return qty to configured warehouse")
	public void E2E_LMS_processReturn_SELF_SHIP_PICKUP_SUCCESSFUL() throws Exception {
		String orderID = lmsHepler.createMockOrder(EnumSCM.DL, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.NORMAL, "cod", false, true);*//*lmsServiceHelper.createAndMarkOrderToStatus(LMS_LOGIN.LogIn86, sku, EnumSCM.DL, EnumSCM.DL, LMS_PINCODE.ML_BLR);*//*
		PacketEntry orderReleaseEntry = omsServiceHelper.getPacketEntry(omsServiceHelper.getPacketId(orderID));
		lmsServiceHelper.processReturnInLMS(""+orderReleaseEntry.getOrderLines().get(0).getId(), EnumSCM.SELF_SHIP_PICKUP_SUCCESSFUL);
	}
	
	@Test(groups = {"Reverse", "Smoke", "Regression"}, priority = 2, description = "ID: C513 , Self Ship and move back the return qty to configured warehouse")
	public void E2E_LMS_processReturn_SELF_SHIP_REJECTED() throws Exception {
		String orderID = lmsHepler.createMockOrder(EnumSCM.DL, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.NORMAL, "cod", false, true);*//*lmsServiceHelper.createAndMarkOrderToStatus(LMS_LOGIN.LogIn86, sku, EnumSCM.DL, EnumSCM.DL, LMS_PINCODE.ML_BLR);*//*
		PacketEntry orderReleaseEntry = omsServiceHelper.getPacketEntry(omsServiceHelper.getPacketId(orderID));
		lmsServiceHelper.processReturnInLMS(""+orderReleaseEntry.getOrderLines().get(0).getId(), EnumSCM.SELF_SHIP_REJECT);
	}
	
	@Test(groups = {"Reverse", "Smoke", "Regression"}, priority = 2, description = "ID: C510 , Self Ship and move back the return qty to configured warehouse")
	public void E2E_LMS_processReturn_SELF_SHIP_ON_HOLD_APPROVE() throws Exception {
		String orderID = lmsHepler.createMockOrder(EnumSCM.DL, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.NORMAL, "cod", false, true);*//*lmsServiceHelper.createAndMarkOrderToStatus(LMS_LOGIN.LogIn86, sku, EnumSCM.DL, EnumSCM.DL, LMS_PINCODE.ML_BLR);*//*
		PacketEntry orderReleaseEntry = omsServiceHelper.getPacketEntry(omsServiceHelper.getPacketId(orderID));
		lmsServiceHelper.processReturnInLMS(""+orderReleaseEntry.getOrderLines().get(0).getId(), EnumSCM.SELF_SHIP_ON_HOLD_APPROVE);
	}
	
	@Test(groups = {"Reverse", "Smoke", "Regression"}, priority = 2, description = "ID: C511 , Self Ship and move back the return qty to configured warehouse")
	public void E2E_LMS_processReturn_SELF_SHIP_ON_HOLD_REJECT() throws Exception {
		String orderID = lmsHepler.createMockOrder(EnumSCM.DL, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.NORMAL, "cod", false, true);*//*lmsServiceHelper.createAndMarkOrderToStatus(LMS_LOGIN.LogIn86, sku, EnumSCM.DL, EnumSCM.DL, LMS_PINCODE.ML_BLR);*//*
		PacketEntry orderReleaseEntry = omsServiceHelper.getPacketEntry(omsServiceHelper.getPacketId(orderID));
		lmsServiceHelper.processReturnInLMS(""+orderReleaseEntry.getOrderLines().get(0).getId(), EnumSCM.SELF_SHIP_ON_HOLD_REJECT);
	}
	
	@SuppressWarnings("deprecation")
	@Test(groups = {"Reverse", "Smoke", "Regression"}, priority = 2, description = "ID: C505 , E2E_LMS_processReturn_PICKUP_SUCCESSFUL_CLOSEDBOX_ON_DC")
	public void E2E_LMS_processReturn_PICKUP_SUCCESSFUL_CLOSEDBOX_AT_DC() throws Exception {
		String orderID = lmsHepler.createMockOrder(EnumSCM.DL, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.NORMAL, "cod", false, false);
		PacketEntry orderReleaseEntry = omsServiceHelper.getPacketEntry(omsServiceHelper.getPacketId(orderID));
		ReturnResponse returnResponse = rmsServiceHelper.createReturn(orderReleaseEntry.getOrderLines().get(0).getId().toString(), 1, ReturnMode.PICKUP,
				27L, RefundMode.CASHBACK, "1234567890", "Myntra test lms automation", "Bangalore", "KA", LMS_PINCODE.JAMMU_IP, "IP");
		Assert.assertEquals(returnResponse.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Return creation failed");
		String returnID = ""+returnResponse.getData().get(0).getId();
		lmsServiceHelper.processReturnInLMS(returnID, EnumSCM.PICKUP_SUCCESSFUL_CB_AT_DC);
	}
	
	@SuppressWarnings("deprecation")
	@Test(groups = {"Reverse", "Smoke", "Regression"}, priority = 2, description = "ID: C507 , E2E_LMS_processReturn_PICKUP_SUCCESSFUL_CLOSEDBOX_ON_DC")
	public void E2E_LMS_processReturn_PICKUP_SUCCESSFUL_CLOSEDBOX_AT_WH() throws Exception {
		String orderID = lmsHepler.createMockOrder(EnumSCM.DL, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.NORMAL, "cod", false, false);
		PacketEntry orderReleaseEntry = omsServiceHelper.getPacketEntry(omsServiceHelper.getPacketId(orderID));
		ReturnResponse returnResponse = rmsServiceHelper.createReturn(orderReleaseEntry.getOrderLines().get(0).getId().toString(), 1, ReturnMode.PICKUP,
				27L, RefundMode.CASHBACK, "1234567890", "Myntra test lms automation", "Bangalore", "KA", LMS_PINCODE.JAMMU_IP, "IP");
		Assert.assertEquals(returnResponse.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Return creation failed");
		String returnID = ""+returnResponse.getData().get(0).getId();
		lmsServiceHelper.processReturnInLMS(returnID, EnumSCM.PICKUP_SUCCESSFUL_CB_AT_WH);
	}
	
	@SuppressWarnings("deprecation")
	@Test(groups = {"Reverse", "Smoke", "Regression"}, priority = 2, description = "ID: C506 , E2E_LMS_processReturn_PICKUP_SUCCESSFUL_CLOSEDBOX_ON_DC")
	public void E2E_LMS_processReturn_PICKUP_SUCCESSFUL_CLOSEDBOX_AT_RPU() throws Exception {
		String orderID = lmsHepler.createMockOrder(EnumSCM.DL, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.NORMAL, "cod", false, false);
		PacketEntry orderReleaseEntry = omsServiceHelper.getPacketEntry(omsServiceHelper.getPacketId(orderID));
		ReturnResponse returnResponse = rmsServiceHelper.createReturn(orderReleaseEntry.getOrderLines().get(0).getId().toString(), 1, ReturnMode.PICKUP,
				27L, RefundMode.CASHBACK, "1234567890", "Myntra test lms automation", "Bangalore", "KA", LMS_PINCODE.JAMMU_IP, "IP");
		Assert.assertEquals(returnResponse.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Return creation failed");
		String returnID = ""+returnResponse.getData().get(0).getId();
		lmsServiceHelper.processReturnInLMS(returnID, EnumSCM.PICKUP_SUCCESSFUL_CB_AT_RPU);
	}
	
	@SuppressWarnings("deprecation")
	@Test(groups = {"Reverse", "Smoke", "Regression"}, priority = 2, description = "ID: C508 , E2E_LMS_processReturn_PICKUP_SUCCESSFUL_CLOSEDBOX_ON_HOLD_APPROVE")
	public void E2E_LMS_processReturn_PICKUP_SUCCESSFUL_CLOSEDBOX_ON_HOLD_APPROVE() throws Exception {
		String orderID = lmsHepler.createMockOrder(EnumSCM.DL, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.NORMAL, "cod", false, false);
		PacketEntry orderReleaseEntry = omsServiceHelper.getPacketEntry(omsServiceHelper.getPacketId(orderID));
		ReturnResponse returnResponse = rmsServiceHelper.createReturn(orderReleaseEntry.getOrderLines().get(0).getId().toString(), 1, ReturnMode.PICKUP,
				27L, RefundMode.CASHBACK, "1234567890", "Myntra test lms automation", "Bangalore", "KA", LMS_PINCODE.JAMMU_IP, "IP");
		Assert.assertEquals(returnResponse.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Return creation failed");
		String returnID = ""+returnResponse.getData().get(0).getId();
		lmsServiceHelper.processReturnInLMS(returnID, EnumSCM.ONHOLD_PICKUP_WITH_DC_APPROVE);
	}
	
	@SuppressWarnings("deprecation")
	@Test(groups = {"Reverse", "Smoke", "Regression"}, priority = 2, description = "ID: C509 , E2E_LMS_processReturn_PICKUP_SUCCESSFUL_CLOSEDBOX_ON_HOLD_REJECT")
	public void E2E_LMS_processReturn_PICKUP_SUCCESSFUL_CLOSEDBOX_ON_HOLD_REJECT() throws Exception {
		String orderID = lmsHepler.createMockOrder(EnumSCM.DL, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.NORMAL, "cod", false, false);
		PacketEntry orderReleaseEntry = omsServiceHelper.getPacketEntry(omsServiceHelper.getPacketId(orderID));
		ReturnResponse returnResponse = rmsServiceHelper.createReturn(orderReleaseEntry.getOrderLines().get(0).getId().toString(), 1, ReturnMode.PICKUP,
				27L, RefundMode.CASHBACK, "1234567890", "Myntra test lms automation", "Bangalore", "KA", LMS_PINCODE.JAMMU_IP, "IP");
		Assert.assertEquals(returnResponse.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Return creation failed");
		String returnID = ""+returnResponse.getData().get(0).getId();
		lmsServiceHelper.processReturnInLMS(returnID, EnumSCM.ONHOLD_PICKUP_WITH_DC_REJECT);
	}
	
	@Test(groups = {"Reverse", "P0", "Smoke", "Regression"}, description = "ID: C482 , LMS E2E flow for exchange", priority = 2)
	public void E2E_LMS_ProcessExchange() throws Exception {
		String[] sku = {"3866:1"};
		String orderID = lmsServiceHelper.createAndMarkOrderToStatus(LMS_LOGIN.LogIn87, sku, ReleaseStatus.DL, EnumSCM.DL, LMS_PINCODE.ML_BLR);
		String packetId = omsServiceHelper.getPacketId(orderID);
		String releaseId = omsServiceHelper.getReleaseId(orderID);
		PacketEntry orderReleaseEntry = omsServiceHelper.getPacketEntry(packetId);
		Long lineID = orderReleaseEntry.getOrderLines().get(0).getId();
		ExchangeOrderResponse exchangeOrderResponse = (ExchangeOrderResponse) ppsServiceHelper.createExchange("" + orderID, "" + lineID, "DNL", 1, "3867");
		Assert.assertNotNull(exchangeOrderResponse.getSuccess(), "Unable to create exchange");
		String exReleaseId = omsServiceHelper.getReleaseIdFromStoreOrderId(exchangeOrderResponse.getExchangeOrderId());
		List<ReleaseEntry> releaseEntries = new ArrayList<>();
        releaseEntries.add(new ReleaseEntry.Builder(exReleaseId, ReleaseStatus.DL).force(true).build());
        processRelease.processReleaseToStatus(processRelease.getReleaseEntryList(releaseEntries));
		//processRelease.processReleaseToStatus(new ReleaseEntry.Builder(exReleaseId, ReleaseStatus.SH).force(true).build());
		String returnID = lmsHepler.getReturnIdFromOrderId(""+packetId);
		processRelease.processReleaseToStatus(processRelease.getReleaseEntryList(releaseEntries));
		//processRelease.processReleaseToStatus(new ReleaseEntry.Builder(exReleaseId, ReleaseStatus.SH).force(true).build());
		Assert.assertTrue(lmsReturnHelper.validatePickupShipmentStatusInLMS(returnID,EnumSCM.PICKUP_SUCCESSFUL,3));
		lmsServiceHelper.validateRmsStatusAndRefund(returnID,EnumSCM.RPU,false,0L);
		Map<String, Object> returnShipment = DBUtilities.exSelectQueryForSingleRecord("select * from return_shipment where source_return_id = " + returnID, "lms");
		long destWarehouseId = (long) returnShipment.get("return_warehouse_id");
		lmsServiceHelper.transferShipmentBackToWH(returnID, destWarehouseId, 5, "DC", "WH");
	}
	
	@Test(groups = {"Reverse", "P0", "Smoke", "Regression"}, description = "", priority = 2)
	public void E2E_LMS_ProcessExchangeWithMock() throws Exception {
		String orderID = lmsHepler.createMockOrder(EnumSCM.DL, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.NORMAL, "cod", false, true);
		String packetId = omsServiceHelper.getPacketId(orderID);
		String releaseId = omsServiceHelper.getReleaseId(orderID);
		PacketEntry orderReleaseEntry = omsServiceHelper.getPacketEntry(packetId);
		Long lineID = orderReleaseEntry.getOrderLines().get(0).getId();
		ExchangeOrderResponse exchangeOrderResponse = (ExchangeOrderResponse) ppsServiceHelper.createExchange("" + orderID, "" + lineID, "DNL", 1,
				orderReleaseEntry.getOrderLines().get(0).getSkuId().toString());
		Assert.assertNotNull(exchangeOrderResponse.getSuccess(), "Unable to create exchange");
		String exReleaseId = omsServiceHelper.getReleaseIdFromStoreOrderId(exchangeOrderResponse.getExchangeOrderId());
		Assert.assertTrue(omsServiceHelper.validatePacketStatusInOMS(exReleaseId,EnumSCM.WP,10));

		List<ReleaseEntry> releaseEntries = new ArrayList<>();
		releaseEntries.add(new ReleaseEntry.Builder(exReleaseId, ReleaseStatus.DL).force(true).build());
		ReleaseEntryList releaseEntryList= new ReleaseEntryList(releaseEntries);
		processRelease.processReleaseToStatus(releaseEntryList);

		//processRelease.processReleaseToStatus(new ReleaseEntry.Builder(exReleaseId, ReleaseStatus.DL).force(true).build());
//        String returnID = lmsHepler.getReturnIdFromOrderId(""+packetId);
//        processRelease.processReleaseToStatus(new ReleaseEntry.Builder(exReleaseId, ReleaseStatus.SH).force(true).build());
//        Assert.assertTrue(lmsReturnHelper.validatePickupShipmentStatusInLMS(returnID,EnumSCM.PICKUP_SUCCESSFUL,3));
//        lmsServiceHelper.validateRmsStatusAndRefund(returnID,EnumSCM.RPU,false,0L);
//        Map<String, Object> returnShipment = DBUtilities.exSelectQueryForSingleRecord("select * from return_shipment where source_return_id = " + returnID, "lms");
//        long destWarehouseId = (long) returnShipment.get("return_warehouse_id");
//        lmsServiceHelper.transferShipmentBackToWH(returnID, destWarehouseId, 5, "DC", "WH");
	}
	
	@Test(groups = {"Reverse", "Smoke", "Regression"}, description = "ID: C485 , LMS E2E flow for exchange", priority = 2)
	public void E2E_LMS_ProcessExchange_LOST() throws Exception {
		String[] sku = {"3878:1"};
		String orderID = lmsServiceHelper.createAndMarkOrderToStatus(LMS_LOGIN.LogIn96, sku, ReleaseStatus.DL, EnumSCM.DL, LMS_PINCODE.ML_BLR);
		String packetId = omsServiceHelper.getPacketId(orderID);
		String releaseId = omsServiceHelper.getReleaseId(orderID);
		PacketEntry orderReleaseEntry = omsServiceHelper.getPacketEntry(packetId);
		Long lineID = orderReleaseEntry.getOrderLines().get(0).getId();
		ExchangeOrderResponse exchangeOrderResponse = (ExchangeOrderResponse) ppsServiceHelper.createExchange("" + orderID, "" + lineID, "DNL", 1, "3879");
		Assert.assertNotNull(exchangeOrderResponse.getSuccess(), "Unable to create exchange");
		
		String exchangeReleaseId = omsServiceHelper.getReleaseIdFromStoreOrderId(exchangeOrderResponse.getExchangeOrderId());

		List<ReleaseEntry> releaseEntries = new ArrayList<>();
		releaseEntries.add(new ReleaseEntry.Builder(exchangeReleaseId, ReleaseStatus.LOST).force(true).build());
		ReleaseEntryList releaseEntryList= new ReleaseEntryList(releaseEntries);
		processRelease.processReleaseToStatus(releaseEntryList);

		//processRelease.processReleaseToStatus(new ReleaseEntry.Builder(exchangeReleaseId, ReleaseStatus.LOST).force(true).build());
		Assert.assertEquals(lmsHepler.getOrderToShipStatus(""+exchangeReleaseId), EnumSCM.LOST, "Order status is not `LOST` in LMS");
		Assert.assertEquals(omsServiceHelper.getPacketEntry(exchangeReleaseId).getStatus().toString(), EnumSCM.L, "Status in oms is not L");
	}
	
	@Test(groups = {"Reverse", "Smoke", "Regression"}, description = "ID: C484 , LMS E2E flow for exchange", priority = 2)
	public void E2E_LMS_ProcessExchange_CC() throws Exception {
		String[] sku = {"3878:1"};
		String orderID = lmsServiceHelper.createAndMarkOrderToStatus(LMS_LOGIN.LogIn88, sku, ReleaseStatus.DL, EnumSCM.C, LMS_PINCODE.ML_BLR, false, PaymentMode.CC);
		String packetId = omsServiceHelper.getPacketId(orderID);
		String releaseId = omsServiceHelper.getReleaseId(orderID);
		PacketEntry orderReleaseEntry = omsServiceHelper.getPacketEntry(packetId);
		Long lineID = orderReleaseEntry.getOrderLines().get(0).getId();
		ExchangeOrderResponse exchangeOrderResponse = (ExchangeOrderResponse) ppsServiceHelper.createExchange("" + orderID, "" + lineID, "DNL", 1, "3879");
		Assert.assertNotNull(exchangeOrderResponse.getSuccess(), "Unable to create exchange");
		String exReleaseId = omsServiceHelper.getReleaseIdFromStoreOrderId(exchangeOrderResponse.getExchangeOrderId());

		List<ReleaseEntry> releaseEntries = new ArrayList<>();
		releaseEntries.add(new ReleaseEntry.Builder(exReleaseId, ReleaseStatus.DL).build());
		ReleaseEntryList releaseEntryList= new ReleaseEntryList(releaseEntries);
		processRelease.processReleaseToStatus(releaseEntryList);

		//processRelease.processReleaseToStatus(new ReleaseEntry.Builder(exReleaseId, ReleaseStatus.DL).force(true).build());
		String returnID = lmsHepler.getReturnIdFromOrderId(""+packetId);
		Assert.assertTrue(lmsReturnHelper.validatePickupShipmentStatusInLMS(returnID,EnumSCM.PICKUP_SUCCESSFUL,3));
		lmsServiceHelper.validateRmsStatusAndRefund(returnID,EnumSCM.RPU,false,1L);
		Map<String, Object> returnShipment = DBUtilities.exSelectQueryForSingleRecord("select * from return_shipment where source_return_id = " + returnID, "lms");
		long destWarehouseId = (long) returnShipment.get("return_warehouse_id");
		lmsServiceHelper.transferShipmentBackToWH(returnID, destWarehouseId, 5, "DC", "WH");
	}
	
	@Test(groups = {"Reverse", "Smoke", "Regression"}, description = "ID: C483 , LMS E2E flow for exchange failed delivery then delivered", priority = 2)
	public void E2E_LMS_ProcessExchangeFD() throws Exception {
		String[] sku = {"3878:1"};
		String orderID = lmsServiceHelper.createAndMarkOrderToStatus(LMS_LOGIN.LogIn89, sku, ReleaseStatus.DL, EnumSCM.DL, LMS_PINCODE.ML_BLR);
		String packetId = omsServiceHelper.getPacketId(orderID);
		String releaseId = omsServiceHelper.getReleaseId(orderID);
		PacketEntry orderReleaseEntry = omsServiceHelper.getPacketEntry(packetId);
		String lineID = orderReleaseEntry.getOrderLines().get(0).getId().toString();
		ExchangeOrderResponse exchangeOrderResponse = (ExchangeOrderResponse) ppsServiceHelper.createExchange("" + orderID, "" + lineID, "DNL", 1, "3879");
		Assert.assertNotNull(exchangeOrderResponse.getSuccess(), "Unable to create exchange");
		String exReleaseId = omsServiceHelper.getReleaseIdFromStoreOrderId(exchangeOrderResponse.getExchangeOrderId());

		List<ReleaseEntry> releaseEntries = new ArrayList<>();
		releaseEntries.add(new ReleaseEntry.Builder(exReleaseId, ReleaseStatus.DL).force(true).build());
		ReleaseEntryList releaseEntryList= new ReleaseEntryList(releaseEntries);
		processRelease.processReleaseToStatus(releaseEntryList);

		//processRelease.processReleaseToStatus(new ReleaseEntry.Builder(exReleaseId, ReleaseStatus.FDDL).force(true).build());
		String returnID = lmsHepler.getReturnIdFromOrderId(""+packetId);
		Assert.assertTrue(lmsReturnHelper.validatePickupShipmentStatusInLMS(returnID,EnumSCM.PICKUP_SUCCESSFUL,3));
		lmsServiceHelper.validateRmsStatusAndRefund(returnID,EnumSCM.RPU,false,1L);
		Map<String, Object> returnShipment = DBUtilities.exSelectQueryForSingleRecord("select * from return_shipment where source_return_id = " + returnID, "lms");
		long destWarehouseId = (long) returnShipment.get("return_warehouse_id");
		lmsServiceHelper.transferShipmentBackToWH(returnID, destWarehouseId, 5, "DC", "WH");
	}
	
	@SuppressWarnings("deprecation")
	@Test(groups = {"Reverse", "Smoke", "Regression"}, description = "ID: C , LMS E2E flow from order placement to DL", enabled = false, priority = 8)
	public void E2E_LMS_MASTER_TRIP() throws Exception {
		String orderIdDL, exchangeOrderID, orderIdRT, returnIdRT, orderIdTOD;
		String[] sku = {"3868:1"};
		//Exchange
		String orderID = lmsServiceHelper.createAndMarkOrderToStatus(LMS_LOGIN.LogIn90, sku, ReleaseStatus.DL, EnumSCM.DL, LMS_PINCODE.ML_BLR);
		String packetId = omsServiceHelper.getPacketId(orderID);
		String releaseId = omsServiceHelper.getReleaseId(orderID);
		PacketEntry orderReleaseEntry = omsServiceHelper.getPacketEntry(packetId);
		String lineID = orderReleaseEntry.getOrderLines().get(0).getId().toString();
		ExchangeOrderResponse exchangeOrderResponse = (ExchangeOrderResponse) ppsServiceHelper.createExchange("" + orderID, "" + lineID, "DNL", 1, "3867");
		Assert.assertNotNull(exchangeOrderResponse.getSuccess(), "Unable to create exchange");
		exchangeOrderID = exchangeOrderResponse.getExchangeOrderId();
		String exReleaseId = omsServiceHelper.getReleaseIdFromStoreOrderId(exchangeOrderResponse.getExchangeOrderId());

		List<ReleaseEntry> releaseEntries = new ArrayList<>();
		releaseEntries.add(new ReleaseEntry.Builder(exReleaseId, ReleaseStatus.SH).force(true).build());
		ReleaseEntryList releaseEntryList= new ReleaseEntryList(releaseEntries);
		processRelease.processReleaseToStatus(releaseEntryList);

		//processRelease.processReleaseToStatus(new ReleaseEntry.Builder(exReleaseId, ReleaseStatus.SH).force(true).build());
		OrderResponse orderResponseEX = lmsServiceHelper.getLmsOrders(exReleaseId);
		lmsServiceHelper.processOrderFromSHtoReceiveInDC(Long.parseLong(lmsHepler.getMasterBagId(exReleaseId)), orderResponseEX.getOrders().get(0).getOrderId(), orderResponseEX.getOrders().get(0).getWarehouseId().toString(), orderResponseEX.getOrders().get(0).getZipcode());
		String returnID = lmsHepler.getReturnIdFromOrderId(""+omsServiceHelper.getPacketId(orderID));
		Map<String, Object> pickup = DBUtilities.exSelectQueryForSingleRecord("select * from return_shipment where source_return_id = " + returnID, "lms");
		//DL
		try {
			orderIdDL = lmsHepler.getOrderFromTestDB("'SH','PK','WP'", "ML", LMS_PINCODE.ML_BLR, EnumSCM.DL, EnumSCM.NORMAL, "cod");

			List<ReleaseEntry> releaseEntries_rt = new ArrayList<>();
			releaseEntries_rt.add(new ReleaseEntry.Builder(omsServiceHelper.getPacketId(orderIdDL), ReleaseStatus.SH).force(true).build());
			ReleaseEntryList releaseEntryList_rt= new ReleaseEntryList(releaseEntries_rt);
			processRelease.processReleaseToStatus(releaseEntryList_rt);

		//	processRelease.processReleaseToStatus(new ReleaseEntry.Builder(omsServiceHelper.getPacketId(orderIdDL), ReleaseStatus.SH).force(true).build());
			Assert.assertEquals(omsServiceHelper.getPacketEntry(omsServiceHelper.getPacketId(orderIdDL)).getStatus().toString(), EnumSCM.SH, "Status in oms is not DL");
			OrderResponse orderResponseDL = lmsServiceHelper.getLmsOrders(orderIdDL);
			lmsServiceHelper.processOrderFromSHtoReceiveInDC(Long.parseLong(lmsHepler.getMasterBagId(""+orderIdDL)), orderResponseDL.getOrders().get(0).getOrderId(), orderResponseDL.getOrders().get(0).getWarehouseId().toString(), orderResponseDL.getOrders().get(0).getZipcode());
			lmsHepler.updateTestOrder(""+ orderIdDL);
		} catch (Exception e) {
			orderIdDL = lmsServiceHelper.createAndMarkOrderToStatus(LMS_LOGIN.LogIn91, sku, ReleaseStatus.SH, EnumSCM.SH, LMS_PINCODE.ML_BLR);
			lmsHepler.insertTestOrder(orderIdDL, EnumSCM.SH, EnumSCM.SH);
			OrderResponse orderResponseDL = lmsServiceHelper.getLmsOrders(omsServiceHelper.getPacketId(orderIdDL));
			lmsServiceHelper.processOrderFromSHtoReceiveInDC(Long.parseLong(lmsHepler.getMasterBagId(""+omsServiceHelper.getPacketId(orderIdDL))), orderResponseDL.getOrders().get(0).getOrderId(), orderResponseDL.getOrders().get(0).getWarehouseId().toString(), orderResponseDL.getOrders().get(0).getZipcode());
		}
		//TOD
		orderIdTOD = lmsServiceHelper.createAndMarkOrderToStatus(LMS_LOGIN.LogIn92, sku, ReleaseStatus.SH, EnumSCM.SH, LMS_PINCODE.ML_BLR, true);
		lmsHepler.insertTestOrder(orderIdTOD, EnumSCM.SH, EnumSCM.SH);
		OrderResponse orderResponseTOD = lmsServiceHelper.getLmsOrders(omsServiceHelper.getPacketId(orderIdTOD));
		lmsServiceHelper.processOrderFromSHtoReceiveInDC(Long.parseLong(lmsHepler.getMasterBagId(""+omsServiceHelper.getPacketId(orderIdTOD))), orderResponseTOD.getOrders().get(0).getOrderId(), orderResponseTOD.getOrders().get(0).getWarehouseId().toString(), orderResponseTOD.getOrders().get(0).getZipcode());
		
		//Return
		try {
			orderIdRT = lmsHepler.getOrderFromTestDB("'DL','SH','PK'", "ML", LMS_PINCODE.ML_BLR, EnumSCM.DL, EnumSCM.NORMAL, "cod");
			String orderReleaseIDRT = omsServiceHelper.getPacketId(orderIdRT);
			if (!omsServiceHelper.getPacketEntry(orderReleaseIDRT).getStatus().equals(EnumSCM.DL)) {

				List<ReleaseEntry> releaseEntries_rt = new ArrayList<>();
				releaseEntries_rt.add(new ReleaseEntry.Builder(omsServiceHelper.getPacketId(orderIdRT),ReleaseStatus.DL).force(true).build());
				ReleaseEntryList releaseEntryList_rt= new ReleaseEntryList(releaseEntries_rt);
				processRelease.processReleaseToStatus(releaseEntryList_rt);

				//processRelease.processReleaseToStatus(new ReleaseEntry.Builder(omsServiceHelper.getPacketId(orderIdRT), ReleaseStatus.DL).force(true).build());
			}
			PacketEntry orderReleaseEntry1 = omsServiceHelper.getPacketEntry(orderReleaseIDRT);
			ReturnResponse returnResponse = rmsServiceHelper.createReturn(orderReleaseEntry1.getOrderLines().get(0).getId().toString(), 1, ReturnMode.PICKUP, 27L, RefundMode.CASHBACK, "1234567890", "Myntra test lms automation", "Bangalore", "KA", LMS_PINCODE.ML_BLR, "ML");
			Assert.assertEquals(returnResponse.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Return creation failed");
			returnIdRT = returnResponse.getData().get(0).getId().toString();
			lmsReturnHelper.validatePickupShipmentStatusInLMS(""+returnIdRT, EnumSCM.PICKUP_CREATED, 15);
			lmsHepler.updateTestOrder(orderIdRT, "RT");
		} catch (Exception e) {
			String orderIDRT = lmsServiceHelper.createAndMarkOrderToStatus(LMS_LOGIN.LogIn93, sku, ReleaseStatus.DL, EnumSCM.DL, LMS_PINCODE.ML_BLR);
			String orderReleaseIDRT = omsServiceHelper.getPacketId(orderIDRT);
			PacketEntry orderReleaseEntry2 = omsServiceHelper.getPacketEntry(orderReleaseIDRT);
			ReturnResponse returnResponse = rmsServiceHelper.createReturn(orderReleaseEntry2.getOrderLines().get(0).getId().toString(), 1, ReturnMode.PICKUP, 27L, RefundMode.CASHBACK, "1234567890", "Myntra test lms automation", "Bangalore", "KA", LMS_PINCODE.ML_BLR, "ML");
			Assert.assertEquals(returnResponse.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Return creation failed");
			returnIdRT = returnResponse.getData().get(0).getId().toString();
			lmsReturnHelper.validatePickupShipmentStatusInLMS(""+returnIdRT, EnumSCM.PICKUP_CREATED, 15);
		}
		
		//orderIdDL,exchangeOrderID,orderIdRT,returnIdRT,orderIdTOD;
		// Receive MasterBag
		@SuppressWarnings("unchecked")
		String deliveryCenterID = ((OrderResponse) lmsServiceHelper.getOrderLMS.apply(packetId)).getOrders().get(0).getDeliveryCenterId().toString();
		String deliveryStaffID = lmsServiceHelper.getDeliveryStaffID(deliveryCenterID);
		log.info("Delivery Center ID :" + deliveryStaffID + "  Delivery Center ID : " + deliveryCenterID);
		DBUtilities.exUpdateQuery("update trip set trip_status='COMPLETED' where delivery_staff_id=" + deliveryStaffID, "lms");
		
		TripResponse tripResponse = lmsServiceHelper.createTrip(Long.parseLong(deliveryCenterID), Long.parseLong(deliveryStaffID));
		Long tripId = tripResponse.getTrips().get(0).getId();
		//Get Tracking number for all orders:
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> trackingNumbers = (List<Map<String, Object>>) DBUtilities.exSelectQuery("select tracking_number from order_to_ship where in (" + orderIdDL + "," + exchangeOrderID + "," + orderIdTOD + ")", "lms");
		pickup = DBUtilities.exSelectQueryForSingleRecord("select * from return_shipment where source_return_id = " + returnIdRT, "lms");
		String trackingNoRT = pickup.get("tracking_number").toString();
		// scan tracking number in trip
		for (Map<String, Object> tn : trackingNumbers) {
			String trackingNo = tn.get("tracking_number").toString();
			log.info("Tracking ID := " + tripId);
			TripOrderAssignmentResponse addAndOutScanOrderToTrip = lmsServiceHelper.addAndOutscanNewOrderToTrip(tripId, trackingNo);
			Assert.assertEquals(addAndOutScanOrderToTrip.getStatus().getStatusType().toString(), EnumSCM.SUCCESS);
		}
		
		TripOrderAssignmentResponse scanTrackingNoInTripRes = lmsServiceHelper.assignOrderToTrip(tripId, trackingNoRT);
		Assert.assertEquals(scanTrackingNoInTripRes.getStatus().getStatusType().toString(), EnumSCM.SUCCESS);
		Thread.sleep(2000);
		// Start Trip
		TripOrderAssignmentResponse startTripRes = lmsServiceHelper.startTrip("" + tripId, "10");
		Assert.assertEquals(startTripRes.getStatus().getStatusType().toString(), EnumSCM.SUCCESS);
		
		Map<String, Object> TDL = (Map<String, Object>) DBUtilities.exSelectQueryForSingleRecord("select id from trip_order_assignment where trip_id = " + tripId + " and order_id =" + orderIdDL, "lms");
		String tripAssignDL = TDL.get("id").toString();
		Map<String, Object> TEX = (Map<String, Object>) DBUtilities.exSelectQueryForSingleRecord("select id from trip_order_assignment where trip_id = " + tripId + " and order_id =" + exchangeOrderID, "lms");
		String tripAssignEX = TEX.get("id").toString();
		Map<String, Object> TPU = (Map<String, Object>) DBUtilities.exSelectQueryForSingleRecord("select id from trip_order_assignment where trip_id = " + tripId + " and return_id =" + returnIdRT, "lms");
		String tripAssignPU = TPU.get("id").toString();
		Map<String, Object> TTOD = (Map<String, Object>) DBUtilities.exSelectQueryForSingleRecord("select id from trip_order_assignment where trip_id = " + tripId + " and order_id =" + orderIdTOD, "lms");
		String tripAssignTOD = TTOD.get("id").toString();
		
		TripOrderAssignmentResponse responseDL = lmsServiceHelper.updateOrderInTrip(Long.parseLong(tripAssignDL), EnumSCM.DELIVERED, EnumSCM.UPDATE);
		Assert.assertEquals(responseDL.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to update Delivery orders in  Trip.");
		TripOrderAssignmentResponse responseEX = lmsServiceHelper.updateOrderInTrip(Long.parseLong(tripAssignEX), EnumSCM.DELIVERED, EnumSCM.UPDATE);
		Assert.assertEquals(responseEX.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to update exchange orders in Trip.");
		
		TripOrderAssignmentResponse responsePU = lmsServiceHelper.updatePickupInTrip(Long.parseLong(tripAssignPU), "PICKUP_SUCCESSFUL_QC_PENDING", EnumSCM.UPDATE);
		Assert.assertEquals(responsePU.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to update pickup in Trip.");
		TripOrderAssignmentResponse responsePU1 = lmsServiceHelper.updatePickupInTrip(Long.parseLong(tripAssignPU), EnumSCM.PICKED_UP_SUCCESSFULLY, EnumSCM.UPDATE);
		Assert.assertEquals(responsePU1.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to update pickup in Trip.");
		Assert.assertEquals(lmsHepler.getReturnStatus(""+returnIdRT), EnumSCM.PICKED_UP_SUCCESSFULLY);
		Map<String, String> skuAndStatus = new HashMap<>();
		skuAndStatus.put("3913", EnumSCM.TRIED_AND_BOUGHT);
		skuAndStatus.put("3867", EnumSCM.TRIED_AND_NOT_BOUGHT);
		TripOrderAssignmentResponse responseTOD = lmsServiceHelper.updateTODOrderInTrip(Long.parseLong(tripAssignTOD), EnumSCM.DELIVERED, EnumSCM.UPDATE, ""+orderIdTOD, skuAndStatus);
		Assert.assertEquals(responseTOD.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to Update TOD Trip.");
		Map<String, Object> tripNoRecord = DBUtilities.exSelectQueryForSingleRecord("select trip_number from trip where id = " + tripId, "lms");
		String tripNumber = tripNoRecord.get("trip_number").toString();
		
		Assert.assertEquals(lmsServiceHelper.getTripOrder("findOrdersByTripNumber/" + tripNumber + "/DL").getData().get(0).getOrderId().toString(), "" + omsServiceHelper.getPacketId(orderIdDL), "Trip search for DL is not matching");
		Assert.assertEquals(lmsServiceHelper.getTripOrder("findOrdersByTripNumber/" + tripNumber + "/PU").getData().get(0).getSourceReturnId().toString(), "" + returnIdRT, "Trip search for Return is not matching");
		Assert.assertEquals(lmsServiceHelper.getTripOrder("findOrdersByTripNumber/" + tripNumber + "/TRY_AND_BUY").getData().get(0).getOrderId().toString(), "" + omsServiceHelper.getPacketId(orderIdTOD), "Trip search for DL is not matching");
		Assert.assertEquals(lmsServiceHelper.getTripOrder("findExchangesByTripNumber/" + tripNumber).getData().get(0).getOrderId().toString(), "" + omsServiceHelper.getPacketId(exchangeOrderID), "Trip search for EX is not matching");
		Assert.assertTrue(lmsServiceHelper.getTripOrder("getTripUpdateDashboardInfoByTripNumber/" + tripNumber).getData().size() > 0, "getTripUpdateDashboardInfoByTripNumber is equals to 0");
		Assert.assertTrue(lmsServiceHelper.getTripOrder("search?q=tripNumber.like:" + tripNumber + "&start=0&fetchSize=1&sortBy=id&sortOrder=DESC").getData().size() > 0, "getTripSearch is equals to 0");
		
		TripOrderAssignmentResponse tripCompleteResponse = lmsServiceHelper.masterTripUpdate(Long.parseLong(tripAssignTOD), ""+omsServiceHelper.getPacketId(orderIdTOD), skuAndStatus, Long.parseLong(tripAssignDL), Long.parseLong(tripAssignEX), Long.parseLong(tripAssignPU));
		Assert.assertEquals(tripCompleteResponse.getStatus().getStatusType().toString(), EnumSCM.SUCCESS);
		Assert.assertEquals(omsServiceHelper.getPacketEntry(omsServiceHelper.getPacketId(orderIdDL)).getStatus().toString(), EnumSCM.DL, "DL order is not in Delivered state in OMS");
		Assert.assertEquals(omsServiceHelper.getPacketEntry(omsServiceHelper.getPacketId(exchangeOrderID)).getStatus().toString(), EnumSCM.DL, "Exchange order is not in Delivered state in OMS");
		Assert.assertEquals(omsServiceHelper.getPacketEntry(omsServiceHelper.getPacketId(orderIdTOD)).getStatus().toString(), EnumSCM.DL, "Exchange order is not in Delivered state in OMS");
		Assert.assertEquals(lmsHepler.getReturnStatus(""+returnIdRT), EnumSCM.PICKUP_SUCCESSFUL, "Pickup not in PICKUP_SUCCESSFUL state in LMS");
	}
	*/
	
}
