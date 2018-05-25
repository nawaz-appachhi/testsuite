package com.myntra.apiTests.erpservices.rms.Test;

import com.myntra.apiTests.common.Constants.ReleaseStatus;
import com.myntra.apiTests.common.ProcessOrder.Service.ProcessRelease;
import com.myntra.apiTests.common.entries.ReleaseEntry;
import com.myntra.apiTests.common.entries.ReleaseEntryList;
import com.myntra.apiTests.end2end.End2EndHelper;
import com.myntra.apiTests.common.Constants.EnumSCM;
import com.myntra.apiTests.erpservices.lms.Helper.LmsServiceHelper;
import com.myntra.apiTests.erpservices.oms.OMSServiceHelper;
import com.myntra.apiTests.erpservices.rms.RMSServiceHelper;
import com.myntra.lordoftherings.Initialize;
import com.myntra.oms.client.entry.OrderEntry;
import com.myntra.oms.client.entry.OrderLineEntry;
import com.myntra.oms.client.entry.OrderReleaseEntry;
import com.myntra.qnotifications.EndToEndNotifications;
import com.myntra.returns.common.enums.RefundMode;
import com.myntra.returns.common.enums.ReturnMode;
import com.myntra.returns.common.enums.code.ReturnLineStatus;
import com.myntra.returns.common.enums.code.ReturnStatus;
import com.myntra.returns.entry.ReturnEntry;
import com.myntra.returns.response.ReturnResponse;
import com.myntra.test.commons.testbase.BaseTest;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class RMS_E2E extends BaseTest {

	static Initialize init = new Initialize("/Data/configuration");
	static org.slf4j.Logger log = LoggerFactory.getLogger(EndToEndNotifications.class);
	End2EndHelper e2e = new End2EndHelper();
	LmsServiceHelper lmsServiceHelper = new LmsServiceHelper();
	OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
	RMSServiceHelper rmsServiceHelper = new RMSServiceHelper();
	ProcessRelease processRelease = new ProcessRelease();
	final String emailId = "end2endautomation4@gmail.com";
	final String password = "myntra@123";
	final String mobilenumber = "9823888800";
	private String BLRAddressId = "6136170";
	
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression", "MiniRegression", "ExhaustiveRegression"},
			enabled=true,priority=1,description = "Create OpenBox pickup return and process till Pickup")
    public void OpenBoxPickup() throws Exception {
		
		String orderID = e2e.createOrder(emailId, password, BLRAddressId, new String[] {RMS_Constants.RMS_STATUS.SKU_QUANTITY}, "", false, false, false, "", false);

		Thread.sleep(10000);
		omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");

		List<ReleaseEntry> releaseEntries = new ArrayList<>();
		releaseEntries.add(new ReleaseEntry.Builder(omsServiceHelper.getReleaseId(orderID), ReleaseStatus.DL).build());
		ReleaseEntryList releaseEntryList= new ReleaseEntryList(releaseEntries);
		processRelease.processReleaseToStatus(releaseEntryList);

		//processRelease.processReleaseToStatus(new ReleaseEntry.Builder(omsServiceHelper.getReleaseId(orderID), ReleaseStatus.DL).build());
		Thread.sleep(20000);
		
		OMSServiceHelper omsHelper = new OMSServiceHelper();
		OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
		OrderReleaseEntry releaseEntry = omsServiceHelper.getOrderReleaseEntry(omsServiceHelper.getReleaseId(orderEntry.getId().toString()));
		OrderLineEntry lineEntry = omsHelper.getOrderLineEntry(releaseEntry.getOrderLines().get(0).getId().toString()); 
    
		RMSServiceHelper returnHelper = new RMSServiceHelper();
		ReturnResponse returnResponse = returnHelper.createReturn(lineEntry.getId().toString(), 1, ReturnMode.PICKUP, 27L, RefundMode.NEFT, "9823888800", "Nagpur", "Nagpur", "MH", "560068", "");
		ReturnEntry returnEntry = returnResponse.getData().get(0);
		
		String returnId = returnEntry.getId().toString();
		ReturnResponse returnResponse2 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry2 = returnResponse2.getData().get(0);
		
	    Thread.sleep(15000);
		//Assert.assertEquals(ReturnStatus.RPI, returnEntry2.getStatusCode());
		Assert.assertEquals(ReturnMode.OPEN_BOX_PICKUP, returnEntry2.getReturnMode());
		Assert.assertNotNull(returnEntry2.getReturnTrackingDetailsEntry().getCourierCode());
		Assert.assertNotNull(returnEntry2.getReturnTrackingDetailsEntry().getTrackingNo());
		Assert.assertNotNull(returnEntry2.getReturnAdditionalDetailsEntry().getIdealReturnWarehouse());
	
		log.info("Courier Code : " +returnEntry2.getReturnTrackingDetailsEntry().getCourierCode());
		log.info("Tracking no : " +returnEntry2.getReturnTrackingDetailsEntry().getTrackingNo());
		log.info("Ideal warehouse : " +returnEntry2.getReturnAdditionalDetailsEntry().getIdealReturnWarehouse());
        Assert.assertEquals(RMS_Constants.LMS_STATUS.PICKUP_CREATED, returnHelper.getReturnStatus(returnId));
		
		lmsServiceHelper.processReturnInLMS(""+returnId, EnumSCM.PICKED_UP_SUCCESSFULLY);
		Thread.sleep(12000);
		Assert.assertEquals(ReturnStatus.RPU, returnEntry2.getStatusCode());
        Assert.assertEquals(RMS_Constants.LMS_STATUS.RETURN_RECEIVED, returnHelper.getReturnStatus(returnId));
        ReturnResponse returnResponse3 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry3 = returnResponse3.getData().get(0);
		
		Assert.assertTrue(returnEntry3.getReturnRefundDetailsEntry().getRefunded());

		
		//rmsServiceHelper.validateReturnStatusInWMS(returnEntry2.getReturnLineEntries().get(0).getItemBarcode(), 3);
		
		}
	
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression", "MiniRegression", "ExhaustiveRegression"},
			enabled=true,priority=2,description = "Create OpenBox pickup return and process till re-stock in WMS")
    public void OpenBoxPickupRestock() throws Exception {
		
		String orderID = e2e.createOrder(emailId, password, BLRAddressId, new String[] {RMS_Constants.RMS_STATUS.SKU_QUANTITY}, "", false, false, false, "", false);

		Thread.sleep(10000);
		omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");

		List<ReleaseEntry> releaseEntries = new ArrayList<>();
		releaseEntries.add(new ReleaseEntry.Builder(omsServiceHelper.getReleaseId(orderID), ReleaseStatus.DL).build());
		ReleaseEntryList releaseEntryList= new ReleaseEntryList(releaseEntries);
		processRelease.processReleaseToStatus(releaseEntryList);

		//processRelease.processReleaseToStatus(new ReleaseEntry.Builder(omsServiceHelper.getReleaseId(orderID), ReleaseStatus.DL).build());
		Thread.sleep(20000);
		
		OMSServiceHelper omsHelper = new OMSServiceHelper();
		OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
		OrderReleaseEntry releaseEntry = omsServiceHelper.getOrderReleaseEntry(omsServiceHelper.getReleaseId(orderEntry.getId().toString()));
		OrderLineEntry lineEntry = omsHelper.getOrderLineEntry(releaseEntry.getOrderLines().get(0).getId().toString()); 
    
		RMSServiceHelper returnHelper = new RMSServiceHelper();
		ReturnResponse returnResponse = returnHelper.createReturn(lineEntry.getId().toString(), 1, ReturnMode.PICKUP, 27L, RefundMode.NEFT, "9823888800", "Nagpur", "Nagpur", "MH", "560068", "");
		ReturnEntry returnEntry = returnResponse.getData().get(0);
		
		String returnId = returnEntry.getId().toString();
		ReturnResponse returnResponse2 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry2 = returnResponse2.getData().get(0);
		
	    Thread.sleep(15000);
		//Assert.assertEquals(ReturnStatus.RPI, returnEntry2.getStatusCode());
		Assert.assertEquals(ReturnMode.OPEN_BOX_PICKUP, returnEntry2.getReturnMode());
		Assert.assertNotNull(returnEntry2.getReturnTrackingDetailsEntry().getCourierCode());
		Assert.assertNotNull(returnEntry2.getReturnTrackingDetailsEntry().getTrackingNo());
		Assert.assertNotNull(returnEntry2.getReturnAdditionalDetailsEntry().getIdealReturnWarehouse());
	
		log.info("Courier Code : " +returnEntry2.getReturnTrackingDetailsEntry().getCourierCode());
		log.info("Tracking no : " +returnEntry2.getReturnTrackingDetailsEntry().getTrackingNo());
		log.info("Ideal warehouse : " +returnEntry2.getReturnAdditionalDetailsEntry().getIdealReturnWarehouse());
        Assert.assertEquals(RMS_Constants.LMS_STATUS.PICKUP_CREATED, returnHelper.getReturnStatus(returnId));
		
		lmsServiceHelper.processReturnInLMS(""+returnId, EnumSCM.PICKED_UP_SUCCESSFULLY);
		Thread.sleep(2000);
		ReturnResponse returnResponse3 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry3 = returnResponse3.getData().get(0);
		Assert.assertEquals(ReturnStatus.RPU, returnEntry3.getStatusCode());
        Assert.assertEquals(RMS_Constants.LMS_STATUS.RETURN_RECEIVED, returnHelper.getReturnStatus(returnId));
		
		returnHelper.returnLineStatusProcessNewWH(ReturnLineStatus.RPU, ReturnLineStatus.RRC, returnEntry3.getId(), returnEntry3.getReturnLineEntries().get(0).getId(), "", "", "", "", "Pawell", (int)(long)returnEntry2.getReturnAdditionalDetailsEntry().getIdealReturnWarehouse(), "WH");
		returnHelper.returnLineStatusProcessNewE2E(returnEntry3.getId().toString(), ReturnLineStatus.RRC, ReturnLineStatus.RQP, lineEntry.getId(), returnEntry3.getReturnLineEntries().get(0).getId(), "", "", "Pawell", (int)(long)returnEntry2.getReturnAdditionalDetailsEntry().getIdealReturnWarehouse(), "Q1", RMS_Constants.RMS_STATUS.QC_REASON, RMS_Constants.RMS_STATUS.QC_DESCRIPTION);
		Assert.assertTrue(rmsServiceHelper.validateReturnStatusInWMS(returnEntry3.getReturnLineEntries().get(0).getItemBarcode(), 7));

		
		}
	
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression", "MiniRegression", "ExhaustiveRegression"},
			enabled=true,priority=3,description = "Create ClosedBox pickup ")
    public void ClosedBoxPickup() throws Exception {
		
		String orderID = e2e.createOrder(emailId, password, BLRAddressId, new String[] {RMS_Constants.RMS_STATUS.SKU_QUANTITY}, "", false, false, false, "", false);

		Thread.sleep(10000);
		omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");

		List<ReleaseEntry> releaseEntries = new ArrayList<>();
		releaseEntries.add(new ReleaseEntry.Builder(omsServiceHelper.getReleaseId(orderID), ReleaseStatus.DL).build());
		ReleaseEntryList releaseEntryList= new ReleaseEntryList(releaseEntries);
		processRelease.processReleaseToStatus(releaseEntryList);

		//processRelease.processReleaseToStatus(new ReleaseEntry.Builder(omsServiceHelper.getReleaseId(orderID), ReleaseStatus.DL).build());
		Thread.sleep(20000);
		
		OMSServiceHelper omsHelper = new OMSServiceHelper();
		OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
		OrderReleaseEntry releaseEntry = omsServiceHelper.getOrderReleaseEntry(omsServiceHelper.getReleaseId(orderEntry.getId().toString()));
		OrderLineEntry lineEntry = omsHelper.getOrderLineEntry(releaseEntry.getOrderLines().get(0).getId().toString()); 
    
		RMSServiceHelper returnHelper = new RMSServiceHelper();
		ReturnResponse returnResponse = returnHelper.createReturn(lineEntry.getId().toString(), 1, ReturnMode.PICKUP, 27L, RefundMode.NEFT, "9823888800", "Nagpur", "Nagpur", "MH", "180001", "");
		ReturnEntry returnEntry = returnResponse.getData().get(0);
		
		String returnId = returnEntry.getId().toString();
		ReturnResponse returnResponse2 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry2 = returnResponse2.getData().get(0);
		
	    Thread.sleep(15000);
		Assert.assertTrue(returnHelper.WaitReturnStatus(ReturnStatus.RRQP, returnId, 10));
		Assert.assertEquals(ReturnMode.CLOSED_BOX_PICKUP, returnEntry2.getReturnMode());

		log.info("Courier Code : " +returnEntry2.getReturnTrackingDetailsEntry().getCourierCode());
		log.info("Ideal warehouse : " +returnEntry2.getReturnAdditionalDetailsEntry().getIdealReturnWarehouse());
		Assert.assertNotNull(returnEntry2.getReturnTrackingDetailsEntry().getCourierCode());
		Assert.assertNotNull(returnEntry2.getReturnAdditionalDetailsEntry().getIdealReturnWarehouse());
	

		log.info("Courier Code : " +returnEntry2.getReturnTrackingDetailsEntry().getCourierCode());
		log.info("Ideal warehouse : " +returnEntry2.getReturnAdditionalDetailsEntry().getIdealReturnWarehouse());
        Assert.assertEquals(RMS_Constants.LMS_STATUS.PICKUP_CREATED, returnHelper.getReturnStatus(returnId));
		}
	

	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression", "MiniRegression", "ExhaustiveRegression"},
			enabled=true,priority=4,description = "Create ClosedBox pickup and process through RADC")
    public void ClosedBoxPickupRADC() throws Exception {
		
		String orderID = e2e.createOrder(emailId, password, BLRAddressId, new String[] {RMS_Constants.RMS_STATUS.SKU_QUANTITY}, "", false, false, false, "", false);

		Thread.sleep(10000);
		omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");

		List<ReleaseEntry> releaseEntries = new ArrayList<>();
		releaseEntries.add(new ReleaseEntry.Builder(omsServiceHelper.getReleaseId(orderID), ReleaseStatus.DL).build());
		ReleaseEntryList releaseEntryList= new ReleaseEntryList(releaseEntries);
		processRelease.processReleaseToStatus(releaseEntryList);

	//	processRelease.processReleaseToStatus(new ReleaseEntry.Builder(omsServiceHelper.getReleaseId(orderID), ReleaseStatus.DL).build());
		Thread.sleep(20000);
		
		OMSServiceHelper omsHelper = new OMSServiceHelper();
		OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
		OrderReleaseEntry releaseEntry = omsServiceHelper.getOrderReleaseEntry(omsServiceHelper.getReleaseId(orderEntry.getId().toString()));
		OrderLineEntry lineEntry = omsHelper.getOrderLineEntry(releaseEntry.getOrderLines().get(0).getId().toString()); 
    
		RMSServiceHelper returnHelper = new RMSServiceHelper();
		ReturnResponse returnResponse = returnHelper.createReturn(lineEntry.getId().toString(), 1, ReturnMode.PICKUP, 27L, RefundMode.NEFT, "9823888800", "Nagpur", "Nagpur", "MH", "180001", "");
		ReturnEntry returnEntry = returnResponse.getData().get(0);
		
		String returnId = returnEntry.getId().toString();
		ReturnResponse returnResponse2 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry2 = returnResponse2.getData().get(0);
		
	    Thread.sleep(15000);
		Assert.assertEquals(ReturnStatus.RRQP, returnEntry2.getStatusCode());
		Assert.assertEquals(ReturnMode.CLOSED_BOX_PICKUP, returnEntry2.getReturnMode());
		Assert.assertNotNull(returnEntry2.getReturnTrackingDetailsEntry().getCourierCode());
		Assert.assertNotNull(returnEntry2.getReturnAdditionalDetailsEntry().getIdealReturnWarehouse());
	
		log.info("Courier Code : " +returnEntry2.getReturnTrackingDetailsEntry().getCourierCode());
		log.info("Ideal warehouse : " +returnEntry2.getReturnAdditionalDetailsEntry().getIdealReturnWarehouse());
        Assert.assertEquals(RMS_Constants.LMS_STATUS.PICKUP_CREATED, returnHelper.getReturnStatus(returnId));
        
        Integer warehouseid = ((Number)returnEntry2.getReturnAdditionalDetailsEntry().getIdealReturnWarehouse()).intValue();
		
		returnHelper.bulk_statuschange(returnEntry.getId().toString(), "", "123", 2L, ReturnStatus.RPI, returnEntry2.getReturnTrackingDetailsEntry().getCourierCode(), "Pawell");
		returnHelper.bulk_statuschange(returnEntry.getId().toString(), "", "123", 2L, ReturnStatus.RDU, returnEntry2.getReturnTrackingDetailsEntry().getCourierCode(), "Pawell");

		log.info("Return id : " +returnEntry2.getId());	
		
		returnHelper.returnLineStatusProcessNewDC(ReturnLineStatus.RDU, ReturnLineStatus.RADC, returnEntry2.getId(), returnEntry2.getReturnLineEntries().get(0).getId(), "Pass", "", "IP", "12345", "Pawell", "MYQ", 23L, "DC");
		
		Thread.sleep(12000);
		ReturnResponse returnResponse3 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry3 = returnResponse3.getData().get(0);
		
		Assert.assertEquals(warehouseid, returnEntry3.getReturnLineEntries().get(0).getWarehouseId());
		boolean refundFlag=returnEntry3.getReturnRefundDetailsEntry().getRefunded();
		boolean refundFlagLine=returnEntry3.getReturnLineEntries().get(0).getReturnLineRefundDetailsEntry().getRefunded();
        Assert.assertEquals(true, refundFlag);
        Assert.assertEquals(true, refundFlagLine);
        
        Assert.assertNotNull(returnEntry3.getReturnLineEntries().get(0).getReturnLineRefundDetailsEntry().getRefundedOn());
        Assert.assertNotNull(returnEntry3.getReturnRefundDetailsEntry().getRefundedOn());
        Assert.assertNotNull(returnEntry3.getReturnRefundDetailsEntry().getRefundPPSId());
        Assert.assertNotNull(returnEntry3.getReturnRefundDetailsEntry().getRefundTxRefId());
        
        System.out.println("Return PPS id : "+returnEntry3.getReturnRefundDetailsEntry().getRefundPPSId());
        Assert.assertEquals(RMS_Constants.LMS_STATUS.PICKUP_SUCCESS, returnHelper.getReturnStatus(returnId));
        
		returnHelper.returnLineStatusProcessNewWH(ReturnLineStatus.RADC, ReturnLineStatus.RRC,returnEntry3.getId(), returnEntry3.getReturnLineEntries().get(0).getId(), "", "", "", "", "Pawell", (int)(long)returnEntry3.getReturnAdditionalDetailsEntry().getIdealReturnWarehouse(), "WH");		
		returnHelper.returnLineStatusProcessNewE2E(returnEntry3.getId().toString(), ReturnLineStatus.RRC, ReturnLineStatus.RQP, lineEntry.getId(), returnEntry3.getReturnLineEntries().get(0).getId(), "", "", "Pawell", (int)(long)returnEntry2.getReturnAdditionalDetailsEntry().getIdealReturnWarehouse(), "Q1", RMS_Constants.RMS_STATUS.QC_REASON, RMS_Constants.RMS_STATUS.QC_DESCRIPTION);
		Assert.assertTrue(rmsServiceHelper.validateReturnStatusInWMS(returnEntry3.getReturnLineEntries().get(0).getItemBarcode(), 7));

		}
	
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression", "MiniRegression", "ExhaustiveRegression"},
			enabled=true,priority=5,description = "Create ClosedBox pickup and process through RJDC")
    public void ClosedBoxPickupRJDC() throws Exception {
		
		String orderID = e2e.createOrder(emailId, password, BLRAddressId, new String[] {RMS_Constants.RMS_STATUS.SKU_QUANTITY}, "", false, false, false, "", false);

		Thread.sleep(10000);
		omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");


		List<ReleaseEntry> releaseEntries = new ArrayList<>();
		releaseEntries.add(new ReleaseEntry.Builder(omsServiceHelper.getReleaseId(orderID), ReleaseStatus.DL).build());
		ReleaseEntryList releaseEntryList= new ReleaseEntryList(releaseEntries);
		processRelease.processReleaseToStatus(releaseEntryList);

		//processRelease.processReleaseToStatus(new ReleaseEntry.Builder(omsServiceHelper.getReleaseId(orderID), ReleaseStatus.DL).build());
		Thread.sleep(20000);
		
		OMSServiceHelper omsHelper = new OMSServiceHelper();
		OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
		OrderReleaseEntry releaseEntry = omsServiceHelper.getOrderReleaseEntry(omsServiceHelper.getReleaseId(orderEntry.getId().toString()));
		OrderLineEntry lineEntry = omsHelper.getOrderLineEntry(releaseEntry.getOrderLines().get(0).getId().toString()); 
    
		RMSServiceHelper returnHelper = new RMSServiceHelper();
		ReturnResponse returnResponse = returnHelper.createReturn(lineEntry.getId().toString(), 1, ReturnMode.PICKUP, 27L, RefundMode.NEFT, "9823888800", "Nagpur", "Nagpur", "MH", "180001", "");
		ReturnEntry returnEntry = returnResponse.getData().get(0);
		
		String returnId = returnEntry.getId().toString();
		ReturnResponse returnResponse2 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry2 = returnResponse2.getData().get(0);
		
	    Thread.sleep(15000);
		Assert.assertEquals(ReturnStatus.RRQP, returnEntry2.getStatusCode());
		Assert.assertEquals(ReturnMode.CLOSED_BOX_PICKUP, returnEntry2.getReturnMode());
		Assert.assertNotNull(returnEntry2.getReturnTrackingDetailsEntry().getCourierCode());
		Assert.assertNotNull(returnEntry2.getReturnAdditionalDetailsEntry().getIdealReturnWarehouse());
	
		log.info("Courier Code : " +returnEntry2.getReturnTrackingDetailsEntry().getCourierCode());
		log.info("Ideal warehouse : " +returnEntry2.getReturnAdditionalDetailsEntry().getIdealReturnWarehouse());
        Assert.assertEquals(RMS_Constants.LMS_STATUS.PICKUP_CREATED, returnHelper.getReturnStatus(returnId));
        
        Integer warehouseid = ((Number)returnEntry2.getReturnAdditionalDetailsEntry().getIdealReturnWarehouse()).intValue();
		returnHelper.bulk_statuschange(returnEntry.getId().toString(), "", "123", 2L, ReturnStatus.RPI, returnEntry2.getReturnTrackingDetailsEntry().getCourierCode(), "Pawell");
		returnHelper.bulk_statuschange(returnEntry.getId().toString(), "", "123", 2L, ReturnStatus.RDU, returnEntry2.getReturnTrackingDetailsEntry().getCourierCode(), "Pawell");
		log.info("Return id : " +returnEntry2.getId());	
		
		returnHelper.returnLineStatusProcessNewDC(ReturnLineStatus.RDU, ReturnLineStatus.RJDC, returnEntry2.getId(), returnEntry2.getReturnLineEntries().get(0).getId(), "Pass", "", "IP", "12345", "Pawell", "MYQ", 23L, "DC");
		
		Thread.sleep(8000);
		ReturnResponse returnResponse3 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry3 = returnResponse3.getData().get(0);
		
		Assert.assertEquals(warehouseid, returnEntry3.getReturnLineEntries().get(0).getWarehouseId());
		Assert.assertEquals(RMS_Constants.LMS_STATUS.PICKUP_ONHOLD, returnHelper.getReturnStatus(returnId));
        
		returnHelper.returnLineStatusProcessNewDC(ReturnLineStatus.RJDC, ReturnLineStatus.CPDC, returnEntry3.getId(), returnEntry3.getReturnLineEntries().get(0).getId(), RMS_Constants.RMS_STATUS.QC_DESCRIPTION, "", "IP", "12345", RMS_Constants.RMS_STATUS.QA_PERSON, "MHP", RMS_Constants.RMS_STATUS.RMS_DC, "DC");
		Thread.sleep(15000);

		ReturnResponse returnResponse4 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry4 = returnResponse4.getData().get(0);
		
		Assert.assertEquals(warehouseid, returnEntry4.getReturnLineEntries().get(0).getWarehouseId());
		boolean refundFlag=returnEntry4.getReturnRefundDetailsEntry().getRefunded();
		boolean refundFlagLine=returnEntry4.getReturnLineEntries().get(0).getReturnLineRefundDetailsEntry().getRefunded();
        Assert.assertEquals(true, refundFlag);
        Assert.assertEquals(true, refundFlagLine);
        
        Assert.assertNotNull(returnEntry4.getReturnLineEntries().get(0).getReturnLineRefundDetailsEntry().getRefundedOn());
        Assert.assertNotNull(returnEntry4.getReturnRefundDetailsEntry().getRefundedOn());
        Assert.assertNotNull(returnEntry4.getReturnRefundDetailsEntry().getRefundPPSId());
        Assert.assertNotNull(returnEntry4.getReturnRefundDetailsEntry().getRefundTxRefId());
        
		Assert.assertEquals(RMS_Constants.LMS_STATUS.PICKUP_SUCCESS, returnHelper.getReturnStatus(returnId));

		returnHelper.returnLineStatusProcessNewWH(ReturnLineStatus.CPDC, ReturnLineStatus.RRC,returnEntry3.getId(), returnEntry3.getReturnLineEntries().get(0).getId(), "", "", "", "", "Pawell", (int)(long)returnEntry3.getReturnAdditionalDetailsEntry().getIdealReturnWarehouse(), "WH");		
		returnHelper.returnLineStatusProcessNewE2E(returnEntry3.getId().toString(), ReturnLineStatus.RRC, ReturnLineStatus.RQP, lineEntry.getId(), returnEntry3.getReturnLineEntries().get(0).getId(), "", "", "Pawell", (int)(long)returnEntry2.getReturnAdditionalDetailsEntry().getIdealReturnWarehouse(), "Q1", RMS_Constants.RMS_STATUS.QC_REASON, RMS_Constants.RMS_STATUS.QC_DESCRIPTION);
		
		Assert.assertTrue(rmsServiceHelper.validateReturnStatusInWMS(returnEntry4.getReturnLineEntries().get(0).getItemBarcode(), 7));
		 
		}
	
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression", "MiniRegression", "ExhaustiveRegression"},
			enabled=true,priority=6,description = "Create ClosedBox pickup and process through CFDC")
    public void ClosedBoxPickupCFDC() throws Exception {
		
		String orderID = e2e.createOrder(emailId, password, BLRAddressId, new String[] {RMS_Constants.RMS_STATUS.SKU_QUANTITY}, "", false, false, false, "", false);

		Thread.sleep(10000);
		omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");

		List<ReleaseEntry> releaseEntries = new ArrayList<>();
		releaseEntries.add(new ReleaseEntry.Builder(omsServiceHelper.getReleaseId(orderID), ReleaseStatus.DL).build());
		ReleaseEntryList releaseEntryList= new ReleaseEntryList(releaseEntries);
		processRelease.processReleaseToStatus(releaseEntryList);

	//	processRelease.processReleaseToStatus(new ReleaseEntry.Builder(omsServiceHelper.getReleaseId(orderID), ReleaseStatus.DL).build());
		Thread.sleep(20000);
		
		OMSServiceHelper omsHelper = new OMSServiceHelper();
		OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
		OrderReleaseEntry releaseEntry = omsServiceHelper.getOrderReleaseEntry(omsServiceHelper.getReleaseId(orderEntry.getId().toString()));
		OrderLineEntry lineEntry = omsHelper.getOrderLineEntry(releaseEntry.getOrderLines().get(0).getId().toString()); 
    
		RMSServiceHelper returnHelper = new RMSServiceHelper();
		ReturnResponse returnResponse = returnHelper.createReturn(lineEntry.getId().toString(), 1, ReturnMode.PICKUP, 27L, RefundMode.NEFT, "9823888800", "Nagpur", "Nagpur", "MH", "180001", "");
		ReturnEntry returnEntry = returnResponse.getData().get(0);
		
		String returnId = returnEntry.getId().toString();
		ReturnResponse returnResponse2 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry2 = returnResponse2.getData().get(0);
		
	    Thread.sleep(15000);
		Assert.assertEquals(ReturnStatus.RRQP, returnEntry2.getStatusCode());
		Assert.assertEquals(ReturnMode.CLOSED_BOX_PICKUP, returnEntry2.getReturnMode());
		Assert.assertNotNull(returnEntry2.getReturnTrackingDetailsEntry().getCourierCode());
		Assert.assertNotNull(returnEntry2.getReturnAdditionalDetailsEntry().getIdealReturnWarehouse());
	
		log.info("Courier Code : " +returnEntry2.getReturnTrackingDetailsEntry().getCourierCode());
		log.info("Ideal warehouse : " +returnEntry2.getReturnAdditionalDetailsEntry().getIdealReturnWarehouse());
        Assert.assertEquals(RMS_Constants.LMS_STATUS.PICKUP_CREATED, returnHelper.getReturnStatus(returnId));
        
        Integer warehouseid = ((Number)returnEntry2.getReturnAdditionalDetailsEntry().getIdealReturnWarehouse()).intValue();
		
		returnHelper.bulk_statuschange(returnEntry.getId().toString(), "", "123", 2L, ReturnStatus.RPI, returnEntry2.getReturnTrackingDetailsEntry().getCourierCode(), "Pawell");
		returnHelper.bulk_statuschange(returnEntry.getId().toString(), "", "123", 2L, ReturnStatus.RDU, returnEntry2.getReturnTrackingDetailsEntry().getCourierCode(), "Pawell");

		log.info("Return id : " +returnEntry2.getId());	
		
		returnHelper.returnLineStatusProcessNewDC(ReturnLineStatus.RDU, ReturnLineStatus.RJDC, returnEntry2.getId(), returnEntry2.getReturnLineEntries().get(0).getId(), "Pass", "", "IP", "12345", "Pawell", "MYQ", 23L, "DC");
		
		Thread.sleep(8000);
		ReturnResponse returnResponse3 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry3 = returnResponse3.getData().get(0);
		
		Assert.assertEquals(warehouseid, returnEntry3.getReturnLineEntries().get(0).getWarehouseId());
		Assert.assertEquals(RMS_Constants.LMS_STATUS.PICKUP_ONHOLD, returnHelper.getReturnStatus(returnId));
        
		returnHelper.returnLineStatusProcessNewDC(ReturnLineStatus.RJDC, ReturnLineStatus.CFDC, returnEntry3.getId(), returnEntry3.getReturnLineEntries().get(0).getId(), RMS_Constants.RMS_STATUS.QC_DESCRIPTION, "", "IP", "12345", RMS_Constants.RMS_STATUS.QA_PERSON, "MHP", RMS_Constants.RMS_STATUS.RMS_DC, "DC");
		Thread.sleep(15000);

		Assert.assertEquals(RMS_Constants.LMS_STATUS.RETURN_REJECTED, returnHelper.getReturnStatus(returnId));
		returnHelper.returnStatusProcessNew(returnEntry3.getId().toString(), ReturnStatus.CFDC, ReturnStatus.RRRS);
		returnHelper.returnStatusProcessNew(returnEntry3.getId().toString(), ReturnStatus.RRRS, ReturnStatus.RRS);
		returnHelper.returnStatusProcessNew(returnEntry3.getId().toString(), ReturnStatus.RRS, ReturnStatus.RSD);
		ReturnResponse returnResponse4 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry4 = returnResponse4.getData().get(0);
		Assert.assertEquals(ReturnStatus.RSD, returnEntry4.getStatusCode());

        Assert.assertFalse(returnEntry4.getReturnRefundDetailsEntry().getRefunded());
        Assert.assertFalse(returnEntry4.getReturnLineEntries().get(0).getReturnLineRefundDetailsEntry().getRefunded());

		}
	
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression", "MiniRegression", "ExhaustiveRegression"},
			enabled=true,priority=4,description = "Create ClosedBox pickup and refund at RDU")
    public void ClosedBoxPickupRDURefund() throws Exception {
		
		String orderID = e2e.createOrder(emailId, password, BLRAddressId, new String[] {RMS_Constants.RMS_STATUS.SKU_QUANTITY}, "", false, false, false, "", false);

		Thread.sleep(10000);
		omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");

		List<ReleaseEntry> releaseEntries = new ArrayList<>();
		releaseEntries.add(new ReleaseEntry.Builder(omsServiceHelper.getReleaseId(orderID), ReleaseStatus.DL).build());
		ReleaseEntryList releaseEntryList= new ReleaseEntryList(releaseEntries);
		processRelease.processReleaseToStatus(releaseEntryList);

		//processRelease.processReleaseToStatus(new ReleaseEntry.Builder(omsServiceHelper.getReleaseId(orderID), ReleaseStatus.DL).build());
		Thread.sleep(20000);
		
		OMSServiceHelper omsHelper = new OMSServiceHelper();
		OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
		OrderReleaseEntry releaseEntry = omsServiceHelper.getOrderReleaseEntry(omsServiceHelper.getReleaseId(orderEntry.getId().toString()));
		OrderLineEntry lineEntry = omsHelper.getOrderLineEntry(releaseEntry.getOrderLines().get(0).getId().toString()); 
    
		RMSServiceHelper returnHelper = new RMSServiceHelper();
		ReturnResponse returnResponse = returnHelper.createReturn(lineEntry.getId().toString(), 1, ReturnMode.PICKUP, 27L, RefundMode.NEFT, "9823888800", "Nagpur", "Nagpur", "MH", "180001", "");
		ReturnEntry returnEntry = returnResponse.getData().get(0);
		
		String returnId = returnEntry.getId().toString();
		ReturnResponse returnResponse2 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry2 = returnResponse2.getData().get(0);
		
	    Thread.sleep(15000);
		Assert.assertEquals(ReturnStatus.RRQP, returnEntry2.getStatusCode());
		Assert.assertEquals(ReturnMode.CLOSED_BOX_PICKUP, returnEntry2.getReturnMode());
		Assert.assertNotNull(returnEntry2.getReturnTrackingDetailsEntry().getCourierCode());
		Assert.assertNotNull(returnEntry2.getReturnAdditionalDetailsEntry().getIdealReturnWarehouse());
	
		log.info("Courier Code : " +returnEntry2.getReturnTrackingDetailsEntry().getCourierCode());
		log.info("Ideal warehouse : " +returnEntry2.getReturnAdditionalDetailsEntry().getIdealReturnWarehouse());
        Assert.assertEquals(RMS_Constants.LMS_STATUS.PICKUP_CREATED, returnHelper.getReturnStatus(returnId));
        
        Integer warehouseid = ((Number)returnEntry2.getReturnAdditionalDetailsEntry().getIdealReturnWarehouse()).intValue();
		returnHelper.bulk_statuschange(returnEntry.getId().toString(), "", "123", 2L, ReturnStatus.RPI, returnEntry2.getReturnTrackingDetailsEntry().getCourierCode(), "Pawell");
		returnHelper.bulk_statuschange(returnEntry.getId().toString(), "", "123", 2L, ReturnStatus.RDU, returnEntry2.getReturnTrackingDetailsEntry().getCourierCode(), "Pawell");
		log.info("Return id : " +returnEntry2.getId());	
		
		returnHelper.bulk_issueRefund(returnEntry2.getId().toString(), "", "",returnEntry2.getReturnAdditionalDetailsEntry().getIdealReturnWarehouse().intValue(), "Pawell");
		Thread.sleep(8000);
		ReturnResponse returnResponse5 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry5 = returnResponse5.getData().get(0);
		Assert.assertTrue(returnEntry5.getReturnRefundDetailsEntry().getRefunded());
		
		returnHelper.returnLineStatusProcessNewDC(ReturnLineStatus.RDU, ReturnLineStatus.RADC, returnEntry2.getId(), returnEntry2.getReturnLineEntries().get(0).getId(), "Pass", "", "IP", "12345", "Pawell", "MYQ", 23L, "DC");
		
		Thread.sleep(8000);
		ReturnResponse returnResponse3 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry3 = returnResponse3.getData().get(0);

        Assert.assertEquals(RMS_Constants.LMS_STATUS.PICKUP_SUCCESS, returnHelper.getReturnStatus(returnId));
        
		returnHelper.returnLineStatusProcessNewWH(ReturnLineStatus.RADC, ReturnLineStatus.RRC,returnEntry3.getId(), returnEntry3.getReturnLineEntries().get(0).getId(), "", "", "", "", "Pawell", (int)(long)returnEntry3.getReturnAdditionalDetailsEntry().getIdealReturnWarehouse(), "WH");		
		returnHelper.returnLineStatusProcessNewE2E(returnEntry3.getId().toString(), ReturnLineStatus.RRC, ReturnLineStatus.RQP, lineEntry.getId(), returnEntry3.getReturnLineEntries().get(0).getId(), "", "", "Pawell", (int)(long)returnEntry2.getReturnAdditionalDetailsEntry().getIdealReturnWarehouse(), "Q1", RMS_Constants.RMS_STATUS.QC_REASON, RMS_Constants.RMS_STATUS.QC_DESCRIPTION);
		Assert.assertTrue(rmsServiceHelper.validateReturnStatusInWMS(returnEntry3.getReturnLineEntries().get(0).getItemBarcode(), 7));
		}
	
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression", "MiniRegression", "ExhaustiveRegression"},
			enabled=true,priority=5,description = "Create Selfship return and restock through RADC")
    public void SelfshipRestockRADC() throws Exception {
		
		String orderID = e2e.createOrder(emailId, password, BLRAddressId, new String[] {RMS_Constants.RMS_STATUS.SKU_QUANTITY}, "", false, false, false, "", false);

		Thread.sleep(10000);
		omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");

		List<ReleaseEntry> releaseEntries = new ArrayList<>();
		releaseEntries.add(new ReleaseEntry.Builder(omsServiceHelper.getReleaseId(orderID), ReleaseStatus.DL).build());
		ReleaseEntryList releaseEntryList= new ReleaseEntryList(releaseEntries);
		processRelease.processReleaseToStatus(releaseEntryList);

		//processRelease.processReleaseToStatus(new ReleaseEntry.Builder(omsServiceHelper.getReleaseId(orderID), ReleaseStatus.DL).build());
		Thread.sleep(20000);
		
		OMSServiceHelper omsHelper = new OMSServiceHelper();
		OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
		OrderReleaseEntry releaseEntry = omsServiceHelper.getOrderReleaseEntry(omsServiceHelper.getReleaseId(orderEntry.getId().toString()));
		OrderLineEntry lineEntry = omsHelper.getOrderLineEntry(releaseEntry.getOrderLines().get(0).getId().toString()); 
    
		RMSServiceHelper returnHelper = new RMSServiceHelper();
		ReturnResponse returnResponse = returnHelper.createReturn(lineEntry.getId().toString(), 1, ReturnMode.SELF_SHIP, 27L, RefundMode.NEFT, "9823888800", "Nagpur", "Nagpur", "MH", "180001", "");
		ReturnEntry returnEntry = returnResponse.getData().get(0);
		
		String returnId = returnEntry.getId().toString();
		ReturnResponse returnResponse2 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry2 = returnResponse2.getData().get(0);
		
	    Thread.sleep(15000);
		Assert.assertEquals(ReturnStatus.RRQS, returnEntry2.getStatusCode());
		Assert.assertEquals(ReturnMode.SELF_SHIP, returnEntry2.getReturnMode());
		Assert.assertNotNull(returnEntry2.getReturnAdditionalDetailsEntry().getIdealReturnWarehouse());
		log.info("Ideal warehouse : " +returnEntry2.getReturnAdditionalDetailsEntry().getIdealReturnWarehouse());
        Assert.assertEquals(RMS_Constants.LMS_STATUS.PICKUP_CREATED, returnHelper.getReturnStatus(returnId));
        
        Integer warehouseid = ((Number)returnEntry2.getReturnAdditionalDetailsEntry().getIdealReturnWarehouse()).intValue();
		log.info("Return id : " +returnEntry2.getId());	
		returnHelper.returnStatusProcessNew(returnEntry2.getId().toString(), ReturnStatus.RRQS, ReturnStatus.RDU);

		returnHelper.returnLineStatusProcessNewDC(ReturnLineStatus.RDU, ReturnLineStatus.RADC, returnEntry2.getId(), returnEntry2.getReturnLineEntries().get(0).getId(), "Pass", "", "BD", "BD12345", "Pawell", "MYQ", 23L, "DC");
		Thread.sleep(8000);
		ReturnResponse returnResponse5 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry5 = returnResponse5.getData().get(0);
		Assert.assertTrue(returnEntry5.getReturnRefundDetailsEntry().getRefunded());

        Assert.assertEquals(RMS_Constants.LMS_STATUS.PICKUP_SUCCESS, returnHelper.getReturnStatus(returnId));
        
		returnHelper.returnLineStatusProcessNewWH(ReturnLineStatus.RADC, ReturnLineStatus.RRC,returnEntry5.getId(), returnEntry5.getReturnLineEntries().get(0).getId(), "", "", "", "", "Pawell", (int)(long)returnEntry5.getReturnAdditionalDetailsEntry().getIdealReturnWarehouse(), "WH");		
		returnHelper.returnLineStatusProcessNewE2E(returnEntry5.getId().toString(), ReturnLineStatus.RRC, ReturnLineStatus.RQP, lineEntry.getId(), returnEntry5.getReturnLineEntries().get(0).getId(), "", "", "Pawell", (int)(long)returnEntry5.getReturnAdditionalDetailsEntry().getIdealReturnWarehouse(), "Q1", RMS_Constants.RMS_STATUS.QC_REASON, RMS_Constants.RMS_STATUS.QC_DESCRIPTION);
		Assert.assertTrue(rmsServiceHelper.validateReturnStatusInWMS(returnEntry5.getReturnLineEntries().get(0).getItemBarcode(), 7));
		
		}
	
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression", "MiniRegression", "ExhaustiveRegression"},
			enabled=true,priority=6,description = "Create SelfShip return and process through RJDC")
    public void SelfShipRJDC() throws Exception {
		
		String orderID = e2e.createOrder(emailId, password, BLRAddressId, new String[] {RMS_Constants.RMS_STATUS.SKU_QUANTITY}, "", false, false, false, "", false);

		Thread.sleep(10000);
		omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");

		List<ReleaseEntry> releaseEntries = new ArrayList<>();
		releaseEntries.add(new ReleaseEntry.Builder(omsServiceHelper.getReleaseId(orderID), ReleaseStatus.DL).build());
		ReleaseEntryList releaseEntryList= new ReleaseEntryList(releaseEntries);
		processRelease.processReleaseToStatus(releaseEntryList);

		//processRelease.processReleaseToStatus(new ReleaseEntry.Builder(omsServiceHelper.getReleaseId(orderID), ReleaseStatus.DL).build());
		Thread.sleep(20000);
		
		OMSServiceHelper omsHelper = new OMSServiceHelper();
		OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
		OrderReleaseEntry releaseEntry = omsServiceHelper.getOrderReleaseEntry(omsServiceHelper.getReleaseId(orderEntry.getId().toString()));
		OrderLineEntry lineEntry = omsHelper.getOrderLineEntry(releaseEntry.getOrderLines().get(0).getId().toString()); 
    
		RMSServiceHelper returnHelper = new RMSServiceHelper();
		ReturnResponse returnResponse = returnHelper.createReturn(lineEntry.getId().toString(), 1, ReturnMode.SELF_SHIP, 27L, RefundMode.NEFT, "9823888800", "Nagpur", "Nagpur", "MH", "180001", "");
		ReturnEntry returnEntry = returnResponse.getData().get(0);
		
		String returnId = returnEntry.getId().toString();
		ReturnResponse returnResponse2 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry2 = returnResponse2.getData().get(0);
		
	    Thread.sleep(15000);
		Assert.assertEquals(ReturnStatus.RRQS, returnEntry2.getStatusCode());
		Assert.assertEquals(ReturnMode.SELF_SHIP, returnEntry2.getReturnMode());
		Assert.assertNotNull(returnEntry2.getReturnAdditionalDetailsEntry().getIdealReturnWarehouse());
	
		log.info("Ideal warehouse : " +returnEntry2.getReturnAdditionalDetailsEntry().getIdealReturnWarehouse());
        Assert.assertEquals(RMS_Constants.LMS_STATUS.PICKUP_CREATED, returnHelper.getReturnStatus(returnId));
        Integer warehouseid = ((Number)returnEntry2.getReturnAdditionalDetailsEntry().getIdealReturnWarehouse()).intValue();
		returnHelper.returnStatusProcessNew(returnEntry2.getId().toString(), ReturnStatus.RRQS, ReturnStatus.RDU);

		returnHelper.returnLineStatusProcessNewDC(ReturnLineStatus.RDU, ReturnLineStatus.RJDC, returnEntry2.getId(), returnEntry2.getReturnLineEntries().get(0).getId(), "Pass", "", "IP", "12345", "Pawell", "MYQ", 23L, "DC");
		
		Thread.sleep(8000);
		ReturnResponse returnResponse3 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry3 = returnResponse3.getData().get(0);
		
		Assert.assertEquals(warehouseid, returnEntry3.getReturnLineEntries().get(0).getWarehouseId());
		Assert.assertEquals(RMS_Constants.LMS_STATUS.PICKUP_ONHOLD, returnHelper.getReturnStatus(returnId));
        
		returnHelper.returnLineStatusProcessNewDC(ReturnLineStatus.RJDC, ReturnLineStatus.CPDC, returnEntry3.getId(), returnEntry3.getReturnLineEntries().get(0).getId(), RMS_Constants.RMS_STATUS.QC_DESCRIPTION, "", "IP", "12345", RMS_Constants.RMS_STATUS.QA_PERSON, "MHP", RMS_Constants.RMS_STATUS.RMS_DC, "DC");
		Thread.sleep(15000);

		ReturnResponse returnResponse4 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry4 = returnResponse4.getData().get(0);
		
		Assert.assertEquals(warehouseid, returnEntry4.getReturnLineEntries().get(0).getWarehouseId());
		boolean refundFlag=returnEntry4.getReturnRefundDetailsEntry().getRefunded();
		boolean refundFlagLine=returnEntry4.getReturnLineEntries().get(0).getReturnLineRefundDetailsEntry().getRefunded();
        Assert.assertEquals(true, refundFlag);
        Assert.assertEquals(true, refundFlagLine);
        
        Assert.assertNotNull(returnEntry4.getReturnLineEntries().get(0).getReturnLineRefundDetailsEntry().getRefundedOn());
        Assert.assertNotNull(returnEntry4.getReturnRefundDetailsEntry().getRefundedOn());
        Assert.assertNotNull(returnEntry4.getReturnRefundDetailsEntry().getRefundPPSId());
        Assert.assertNotNull(returnEntry4.getReturnRefundDetailsEntry().getRefundTxRefId());
        
		Assert.assertEquals(RMS_Constants.LMS_STATUS.PICKUP_SUCCESS, returnHelper.getReturnStatus(returnId));

		returnHelper.returnLineStatusProcessNewWH(ReturnLineStatus.CPDC, ReturnLineStatus.RRC,returnEntry3.getId(), returnEntry3.getReturnLineEntries().get(0).getId(), "", "", "", "", "Pawell", (int)(long)returnEntry3.getReturnAdditionalDetailsEntry().getIdealReturnWarehouse(), "WH");		
		returnHelper.returnLineStatusProcessNewE2E(returnEntry3.getId().toString(), ReturnLineStatus.RRC, ReturnLineStatus.RQP, lineEntry.getId(), returnEntry3.getReturnLineEntries().get(0).getId(), "", "", "Pawell", (int)(long)returnEntry2.getReturnAdditionalDetailsEntry().getIdealReturnWarehouse(), "Q1", RMS_Constants.RMS_STATUS.QC_REASON, RMS_Constants.RMS_STATUS.QC_DESCRIPTION);
		
		Assert.assertTrue(rmsServiceHelper.validateReturnStatusInWMS(returnEntry4.getReturnLineEntries().get(0).getItemBarcode(), 7));
		 
		}
	
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression", "MiniRegression", "ExhaustiveRegression"},
			enabled=true,priority=7,description = "Create SelfShip return and reshipped back through CFDC")
    public void SelfShipCFDC() throws Exception {
		
		String orderID = e2e.createOrder(emailId, password, BLRAddressId, new String[] {RMS_Constants.RMS_STATUS.SKU_QUANTITY}, "", false, false, false, "", false);

		Thread.sleep(10000);
		omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");

		List<ReleaseEntry> releaseEntries = new ArrayList<>();
		releaseEntries.add(new ReleaseEntry.Builder(omsServiceHelper.getReleaseId(orderID), ReleaseStatus.DL).build());
		ReleaseEntryList releaseEntryList= new ReleaseEntryList(releaseEntries);
		processRelease.processReleaseToStatus(releaseEntryList);

		//processRelease.processReleaseToStatus(new ReleaseEntry.Builder(omsServiceHelper.getReleaseId(orderID), ReleaseStatus.DL).build());
		Thread.sleep(20000);
		
		OMSServiceHelper omsHelper = new OMSServiceHelper();
		OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
		OrderReleaseEntry releaseEntry = omsServiceHelper.getOrderReleaseEntry(omsServiceHelper.getReleaseId(orderEntry.getId().toString()));
		OrderLineEntry lineEntry = omsHelper.getOrderLineEntry(releaseEntry.getOrderLines().get(0).getId().toString()); 
    
		RMSServiceHelper returnHelper = new RMSServiceHelper();
		ReturnResponse returnResponse = returnHelper.createReturn(lineEntry.getId().toString(), 1, ReturnMode.SELF_SHIP, 27L, RefundMode.NEFT, "9823888800", "Nagpur", "Nagpur", "MH", "180001", "");
		ReturnEntry returnEntry = returnResponse.getData().get(0);
		
		String returnId = returnEntry.getId().toString();
		ReturnResponse returnResponse2 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry2 = returnResponse2.getData().get(0);
		
	    Thread.sleep(15000);
		Assert.assertEquals(ReturnStatus.RRQS, returnEntry2.getStatusCode());
		Assert.assertEquals(ReturnMode.SELF_SHIP, returnEntry2.getReturnMode());
		Assert.assertNotNull(returnEntry2.getReturnAdditionalDetailsEntry().getIdealReturnWarehouse());
	
		log.info("Ideal warehouse : " +returnEntry2.getReturnAdditionalDetailsEntry().getIdealReturnWarehouse());
        Assert.assertEquals(RMS_Constants.LMS_STATUS.PICKUP_CREATED, returnHelper.getReturnStatus(returnId));
        Integer warehouseid = ((Number)returnEntry2.getReturnAdditionalDetailsEntry().getIdealReturnWarehouse()).intValue();
		returnHelper.returnStatusProcessNew(returnEntry2.getId().toString(), ReturnStatus.RRQS, ReturnStatus.RDU);
		returnHelper.returnLineStatusProcessNewDC(ReturnLineStatus.RDU, ReturnLineStatus.RJDC, returnEntry2.getId(), returnEntry2.getReturnLineEntries().get(0).getId(), "Pass", "", "IP", "12345", "Pawell", "MYQ", 23L, "DC");
		
		Thread.sleep(8000);
		ReturnResponse returnResponse3 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry3 = returnResponse3.getData().get(0);
		
		Assert.assertEquals(warehouseid, returnEntry3.getReturnLineEntries().get(0).getWarehouseId());
		Assert.assertEquals(RMS_Constants.LMS_STATUS.PICKUP_ONHOLD, returnHelper.getReturnStatus(returnId));
        
		returnHelper.returnLineStatusProcessNewDC(ReturnLineStatus.RJDC, ReturnLineStatus.CFDC, returnEntry3.getId(), returnEntry3.getReturnLineEntries().get(0).getId(), RMS_Constants.RMS_STATUS.QC_DESCRIPTION, "", "IP", "12345", RMS_Constants.RMS_STATUS.QA_PERSON, "MHP", RMS_Constants.RMS_STATUS.RMS_DC, "DC");
		Thread.sleep(15000);

		Assert.assertEquals(RMS_Constants.LMS_STATUS.RETURN_REJECTED, returnHelper.getReturnStatus(returnId));
		returnHelper.returnStatusProcessNew(returnEntry3.getId().toString(), ReturnStatus.CFDC, ReturnStatus.RRRS);
		returnHelper.returnStatusProcessNew(returnEntry3.getId().toString(), ReturnStatus.RRRS, ReturnStatus.RRS);
		returnHelper.returnStatusProcessNew(returnEntry3.getId().toString(), ReturnStatus.RRS, ReturnStatus.RSD);
		ReturnResponse returnResponse4 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry4 = returnResponse4.getData().get(0);
		Assert.assertEquals(ReturnStatus.RSD, returnEntry4.getStatusCode());

        Assert.assertFalse(returnEntry4.getReturnRefundDetailsEntry().getRefunded());
        Assert.assertFalse(returnEntry4.getReturnLineEntries().get(0).getReturnLineRefundDetailsEntry().getRefunded());

		}
	
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression", "MiniRegression", "ExhaustiveRegression"},
			enabled=true,priority=8,description = "ClosedBox pickup RDU to Receive at Warehouse")
    public void ClosedBoxPickupRDUToReceiveWH() throws Exception {
		
		String orderID = e2e.createOrder(emailId, password, BLRAddressId, new String[] {RMS_Constants.RMS_STATUS.SKU_QUANTITY}, "", false, false, false, "", false);

		Thread.sleep(10000);
		omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");

		List<ReleaseEntry> releaseEntries = new ArrayList<>();
		releaseEntries.add(new ReleaseEntry.Builder(omsServiceHelper.getReleaseId(orderID), ReleaseStatus.DL).build());
		ReleaseEntryList releaseEntryList= new ReleaseEntryList(releaseEntries);
		processRelease.processReleaseToStatus(releaseEntryList);

		//processRelease.processReleaseToStatus(new ReleaseEntry.Builder(omsServiceHelper.getReleaseId(orderID), ReleaseStatus.DL).build());
		Thread.sleep(20000);
		
		OMSServiceHelper omsHelper = new OMSServiceHelper();
		OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
		OrderReleaseEntry releaseEntry = omsServiceHelper.getOrderReleaseEntry(omsServiceHelper.getReleaseId(orderEntry.getId().toString()));
		OrderLineEntry lineEntry = omsHelper.getOrderLineEntry(releaseEntry.getOrderLines().get(0).getId().toString()); 
    
		RMSServiceHelper returnHelper = new RMSServiceHelper();
		ReturnResponse returnResponse = returnHelper.createReturn(lineEntry.getId().toString(), 1, ReturnMode.PICKUP, 27L, RefundMode.NEFT, "9823888800", "Nagpur", "Nagpur", "MH", "180001", "");
		ReturnEntry returnEntry = returnResponse.getData().get(0);
		
		String returnId = returnEntry.getId().toString();
		ReturnResponse returnResponse2 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry2 = returnResponse2.getData().get(0);
		
	    Thread.sleep(15000);
		Assert.assertEquals(ReturnStatus.RRQP, returnEntry2.getStatusCode());
		Assert.assertEquals(ReturnMode.CLOSED_BOX_PICKUP, returnEntry2.getReturnMode());
		Assert.assertNotNull(returnEntry2.getReturnTrackingDetailsEntry().getCourierCode());
		Assert.assertNotNull(returnEntry2.getReturnAdditionalDetailsEntry().getIdealReturnWarehouse());
	
		log.info("Courier Code : " +returnEntry2.getReturnTrackingDetailsEntry().getCourierCode());
		log.info("Ideal warehouse : " +returnEntry2.getReturnAdditionalDetailsEntry().getIdealReturnWarehouse());
        Assert.assertEquals(RMS_Constants.LMS_STATUS.PICKUP_CREATED, returnHelper.getReturnStatus(returnId));
        
        Integer warehouseid = ((Number)returnEntry2.getReturnAdditionalDetailsEntry().getIdealReturnWarehouse()).intValue();
		returnHelper.bulk_statuschange(returnEntry.getId().toString(), "", "123", 2L, ReturnStatus.RDU, returnEntry2.getReturnTrackingDetailsEntry().getCourierCode(), "Pawell");
		log.info("Return id : " +returnEntry2.getId());	
		
		returnHelper.bulk_issueRefund(returnEntry2.getId().toString(), "", "",returnEntry2.getReturnAdditionalDetailsEntry().getIdealReturnWarehouse().intValue(), "Pawell");
		Thread.sleep(15000);
		ReturnResponse returnResponse5 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry5 = returnResponse5.getData().get(0);
		Assert.assertTrue(returnEntry5.getReturnRefundDetailsEntry().getRefunded());
				
		Thread.sleep(15000);
		ReturnResponse returnResponse3 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry3 = returnResponse3.getData().get(0);
        
		returnHelper.returnLineStatusProcessNewWH(ReturnLineStatus.RDU, ReturnLineStatus.RRC,returnEntry3.getId(), returnEntry3.getReturnLineEntries().get(0).getId(), "", "", "", "", "Pawell", (int)(long)returnEntry3.getReturnAdditionalDetailsEntry().getIdealReturnWarehouse(), "WH");		
        Assert.assertEquals(RMS_Constants.LMS_STATUS.PICKUP_SUCCESS, returnHelper.getReturnStatus(returnId));
		returnHelper.returnLineStatusProcessNewE2E(returnEntry3.getId().toString(), ReturnLineStatus.RRC, ReturnLineStatus.RQP, lineEntry.getId(), returnEntry3.getReturnLineEntries().get(0).getId(), "", "", "Pawell", (int)(long)returnEntry2.getReturnAdditionalDetailsEntry().getIdealReturnWarehouse(), "Q1", RMS_Constants.RMS_STATUS.QC_REASON, RMS_Constants.RMS_STATUS.QC_DESCRIPTION);
		Assert.assertTrue(rmsServiceHelper.validateReturnStatusInWMS(returnEntry3.getReturnLineEntries().get(0).getItemBarcode(), 7));
		}
	
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression", "MiniRegression", "ExhaustiveRegression"},
			enabled=true,priority=9,description = "SelfShip RDU to Receive at Warehouse")
    public void SelfShipRDUToReceiveWH() throws Exception {
		
		String orderID = e2e.createOrder(emailId, password, BLRAddressId, new String[] {RMS_Constants.RMS_STATUS.SKU_QUANTITY}, "", false, false, false, "", false);

		Thread.sleep(10000);
		omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");

		List<ReleaseEntry> releaseEntries = new ArrayList<>();
		releaseEntries.add(new ReleaseEntry.Builder(omsServiceHelper.getReleaseId(orderID), ReleaseStatus.DL).build());
		ReleaseEntryList releaseEntryList= new ReleaseEntryList(releaseEntries);
		processRelease.processReleaseToStatus(releaseEntryList);

		//processRelease.processReleaseToStatus(new ReleaseEntry.Builder(omsServiceHelper.getReleaseId(orderID), ReleaseStatus.DL).build());
		Thread.sleep(20000);
		
		OMSServiceHelper omsHelper = new OMSServiceHelper();
		OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
		OrderReleaseEntry releaseEntry = omsServiceHelper.getOrderReleaseEntry(omsServiceHelper.getReleaseId(orderEntry.getId().toString()));
		OrderLineEntry lineEntry = omsHelper.getOrderLineEntry(releaseEntry.getOrderLines().get(0).getId().toString()); 
    
		RMSServiceHelper returnHelper = new RMSServiceHelper();
		ReturnResponse returnResponse = returnHelper.createReturn(lineEntry.getId().toString(), 1, ReturnMode.SELF_SHIP, 27L, RefundMode.NEFT, "9823888800", "Nagpur", "Nagpur", "MH", "180001", "");
		ReturnEntry returnEntry = returnResponse.getData().get(0);
		Thread.sleep(15000);
		String returnId = returnEntry.getId().toString();
		ReturnResponse returnResponse2 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry2 = returnResponse2.getData().get(0);
		
	    
		Assert.assertEquals(ReturnStatus.RRQS, returnEntry2.getStatusCode());
		Assert.assertEquals(ReturnMode.SELF_SHIP, returnEntry2.getReturnMode());
		//Assert.assertNotNull(returnEntry2.getReturnAdditionalDetailsEntry().getIdealReturnWarehouse());
		log.info("Ideal warehouse : " +returnEntry2.getReturnAdditionalDetailsEntry().getIdealReturnWarehouse());
        Assert.assertEquals(RMS_Constants.LMS_STATUS.PICKUP_CREATED, returnHelper.getReturnStatus(returnId));
        
        Integer warehouseid = ((Number)returnEntry2.getReturnAdditionalDetailsEntry().getIdealReturnWarehouse()).intValue();
		returnHelper.returnStatusProcessNew(returnEntry2.getId().toString(), ReturnStatus.RRQS, ReturnStatus.RDU);
		log.info("Return id : " +returnEntry2.getId());	
				
		Thread.sleep(15000);
		ReturnResponse returnResponse3 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry3 = returnResponse3.getData().get(0);
        
		returnHelper.returnLineStatusProcessNewWH(ReturnLineStatus.RDU, ReturnLineStatus.RRC,returnEntry3.getId(), returnEntry3.getReturnLineEntries().get(0).getId(), "", "", "BD", "BD12345", "Pawell", (int)(long)returnEntry3.getReturnAdditionalDetailsEntry().getIdealReturnWarehouse(), "WH");	
		Thread.sleep(5000);
        Assert.assertEquals(RMS_Constants.LMS_STATUS.PICKUP_SUCCESS, returnHelper.getReturnStatus(returnId));
		returnHelper.returnLineStatusProcessNewE2E(returnEntry3.getId().toString(), ReturnLineStatus.RRC, ReturnLineStatus.RQP, lineEntry.getId(), returnEntry3.getReturnLineEntries().get(0).getId(), "", "", "Pawell", (int)(long)returnEntry2.getReturnAdditionalDetailsEntry().getIdealReturnWarehouse(), "Q1", RMS_Constants.RMS_STATUS.QC_REASON, RMS_Constants.RMS_STATUS.QC_DESCRIPTION);
		
		Thread.sleep(15000);
		ReturnResponse returnResponse5 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry5 = returnResponse5.getData().get(0);
		Assert.assertTrue(returnEntry5.getReturnRefundDetailsEntry().getRefunded());
		Assert.assertTrue(rmsServiceHelper.validateReturnStatusInWMS(returnEntry3.getReturnLineEntries().get(0).getItemBarcode(), 7));
		}
	
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression", "MiniRegression", "ExhaustiveRegression"},
			enabled=true,priority=10,description = "CloseBox declined and verified in LMS")
    public void CloseBoxDeclined() throws Exception {
		
		String orderID = e2e.createOrder(emailId, password, BLRAddressId, new String[] {RMS_Constants.RMS_STATUS.SKU_QUANTITY}, "", false, false, false, "", false);

		Thread.sleep(10000);
		omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");

		List<ReleaseEntry> releaseEntries = new ArrayList<>();
		releaseEntries.add(new ReleaseEntry.Builder(omsServiceHelper.getReleaseId(orderID), ReleaseStatus.DL).build());
		ReleaseEntryList releaseEntryList= new ReleaseEntryList(releaseEntries);
		processRelease.processReleaseToStatus(releaseEntryList);

		//processRelease.processReleaseToStatus(new ReleaseEntry.Builder(omsServiceHelper.getReleaseId(orderID), ReleaseStatus.DL).build());
		Thread.sleep(20000);
		
		OMSServiceHelper omsHelper = new OMSServiceHelper();
		OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
		OrderReleaseEntry releaseEntry = omsServiceHelper.getOrderReleaseEntry(omsServiceHelper.getReleaseId(orderEntry.getId().toString()));
		OrderLineEntry lineEntry = omsHelper.getOrderLineEntry(releaseEntry.getOrderLines().get(0).getId().toString()); 
    
		RMSServiceHelper returnHelper = new RMSServiceHelper();
		ReturnResponse returnResponse = returnHelper.createReturn(lineEntry.getId().toString(), 1, ReturnMode.PICKUP, 27L, RefundMode.NEFT, "9823888800", "Nagpur", "Nagpur", "MH", "180001", "");
		ReturnEntry returnEntry = returnResponse.getData().get(0);
		
		String returnId = returnEntry.getId().toString();
		ReturnResponse returnResponse2 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry2 = returnResponse2.getData().get(0);
		
	    Thread.sleep(15000);
		Assert.assertEquals(ReturnStatus.RRQP, returnEntry2.getStatusCode());
		Assert.assertEquals(ReturnMode.CLOSED_BOX_PICKUP, returnEntry2.getReturnMode());
		Assert.assertNotNull(returnEntry2.getReturnAdditionalDetailsEntry().getIdealReturnWarehouse());
		log.info("Ideal warehouse : " +returnEntry2.getReturnAdditionalDetailsEntry().getIdealReturnWarehouse());
        Assert.assertEquals(RMS_Constants.LMS_STATUS.PICKUP_CREATED, returnHelper.getReturnStatus(returnId));
        
        Integer warehouseid = ((Number)returnEntry2.getReturnAdditionalDetailsEntry().getIdealReturnWarehouse()).intValue();
		returnHelper.bulk_statuschange(returnEntry.getId().toString(), "", "123", 2L, ReturnStatus.RDU, returnEntry2.getReturnTrackingDetailsEntry().getCourierCode(), "Pawell");
		log.info("Return id : " +returnEntry2.getId());	
				
		Thread.sleep(15000);
		ReturnResponse returnResponse3 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry3 = returnResponse3.getData().get(0);
        
		returnHelper.returnStatusProcessNew(returnEntry.getId().toString(), ReturnStatus.RDU, ReturnStatus.RRD);
        Assert.assertEquals(RMS_Constants.LMS_STATUS.RETURN_REJECTED, returnHelper.getReturnStatus(returnId));
	
		}
	
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression", "MiniRegression", "ExhaustiveRegression"},
			enabled=true,priority=11,description = "OpenBox declined and verified in LMS")
    public void OpenBoxDeclined() throws Exception {
		
		String orderID = e2e.createOrder(emailId, password, BLRAddressId, new String[] {RMS_Constants.RMS_STATUS.SKU_QUANTITY}, "", false, false, false, "", false);

		Thread.sleep(10000);
		omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");


		List<ReleaseEntry> releaseEntries = new ArrayList<>();
		releaseEntries.add(new ReleaseEntry.Builder(omsServiceHelper.getReleaseId(orderID), ReleaseStatus.DL).build());
		ReleaseEntryList releaseEntryList= new ReleaseEntryList(releaseEntries);
		processRelease.processReleaseToStatus(releaseEntryList);


		//processRelease.processReleaseToStatus(new ReleaseEntry.Builder(omsServiceHelper.getReleaseId(orderID), ReleaseStatus.DL).build());
		Thread.sleep(20000);
		
		OMSServiceHelper omsHelper = new OMSServiceHelper();
		OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
		OrderReleaseEntry releaseEntry = omsServiceHelper.getOrderReleaseEntry(omsServiceHelper.getReleaseId(orderEntry.getId().toString()));
		OrderLineEntry lineEntry = omsHelper.getOrderLineEntry(releaseEntry.getOrderLines().get(0).getId().toString()); 
    
		RMSServiceHelper returnHelper = new RMSServiceHelper();
		ReturnResponse returnResponse = returnHelper.createReturn(lineEntry.getId().toString(), 1, ReturnMode.PICKUP, 27L, RefundMode.NEFT, "9823888800", "Nagpur", "Nagpur", "MH", "560068", "");
		ReturnEntry returnEntry = returnResponse.getData().get(0);
		
		String returnId = returnEntry.getId().toString();
		ReturnResponse returnResponse2 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry2 = returnResponse2.getData().get(0);
		
	    Thread.sleep(15000);
		Assert.assertEquals(ReturnStatus.RPI, returnEntry2.getStatusCode());
		Assert.assertEquals(ReturnMode.OPEN_BOX_PICKUP, returnEntry2.getReturnMode());
		Assert.assertNotNull(returnEntry2.getReturnAdditionalDetailsEntry().getIdealReturnWarehouse());
		log.info("Ideal warehouse : " +returnEntry2.getReturnAdditionalDetailsEntry().getIdealReturnWarehouse());
        Assert.assertEquals(RMS_Constants.LMS_STATUS.PICKUP_CREATED, returnHelper.getReturnStatus(returnId));
        
		ReturnResponse returnResponse3 = returnHelper.getReturnDetailsNew(returnId);
		ReturnEntry returnEntry3 = returnResponse3.getData().get(0);
        
		returnHelper.returnStatusProcessNew(returnEntry3.getId().toString(), ReturnStatus.RPI, ReturnStatus.RRD);
        Assert.assertEquals(RMS_Constants.LMS_STATUS.RETURN_REJECTED, returnHelper.getReturnStatus(returnId));
	
		}
	
	/*@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression", "MiniRegression", "ExhaustiveRegression"},
			enabled=true,priority=10,description = "CloseBox declined and verified in LMS")
    public void Deletelater() throws Exception {
		
		Long orderID = e2e.createOrder(emailId, password, BLRAddressId, new String[] {RMS_STATUS.SKU_QUANTITY}, "", false, false, false, "", false);

		Thread.sleep(10000);
		omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
		e2e.markOrderDelivered(orderID, "DL", "ORDER");
		Thread.sleep(20000);
		
		
	
		}
	*/
}
