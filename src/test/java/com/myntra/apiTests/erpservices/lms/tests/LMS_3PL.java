package com.myntra.apiTests.erpservices.lms.tests;

import com.myntra.apiTests.common.Constants.EnumSCM;
import com.myntra.apiTests.common.Constants.ReleaseStatus;
import com.myntra.apiTests.common.Constants.ShipmentSource;
import com.myntra.apiTests.common.ProcessOrder.Service.ProcessRelease;
import com.myntra.apiTests.common.entries.ReleaseEntry;
import com.myntra.apiTests.erpservices.lms.Constants.LMS_LOGIN;
import com.myntra.apiTests.erpservices.lms.Constants.LMS_PINCODE;
import com.myntra.apiTests.erpservices.lms.Constants.PaymentMode;
import com.myntra.apiTests.erpservices.lms.Helper.DelhiveryHelper;
import com.myntra.apiTests.erpservices.lms.Helper.LMSDBHelper;
import com.myntra.apiTests.erpservices.lms.Helper.LMSHelper;
import com.myntra.apiTests.erpservices.lms.Helper.LmsServiceHelper;
import com.myntra.apiTests.erpservices.oms.OMSServiceHelper;
import com.myntra.apiTests.erpservices.rms.RMSServiceHelper;
import com.myntra.apiTests.portalservices.pps.PPSServiceHelper;
import com.myntra.cts.entries.CourierTrackingResponse;
import com.myntra.lordoftherings.boromir.DBUtilities;
import com.myntra.oms.client.entry.OrderReleaseEntry;
import com.myntra.paymentplan.domain.response.ExchangeOrderResponse;
import com.myntra.returns.common.enums.RefundMode;
import com.myntra.returns.common.enums.ReturnMode;
import com.myntra.returns.response.ReturnResponse;
import com.myntra.test.commons.testbase.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Shubham Gupta on 12/16/16.
 */
public class LMS_3PL extends BaseTest {
	
	//private static org.slf4j.Logger log = LoggerFactory.getLogger(LMS_3PL.class);
	private DelhiveryHelper delhiveryHelper = new DelhiveryHelper();
	private OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
	private LmsServiceHelper lmsServiceHelper = new LmsServiceHelper();
	private RMSServiceHelper rmsServiceHelper = new RMSServiceHelper();
	private PPSServiceHelper ppsServiceHelper = new PPSServiceHelper();
	private LMSHelper lmsHepler = new LMSHelper();
	private ProcessRelease processRelease = new ProcessRelease();

    @Test(groups = {"P0","Smoke", "Regression"}, description = "ID: 123, LMS E2E flow from order placement to DL", priority = 3)
    public void E2E_LMS_CRETE_ORDER_DE() throws Exception {
     
            String[] sku = {"3837:1"};
            String orderId = lmsServiceHelper.createAndMarkOrderToStatus(LMS_LOGIN.LogIn64, sku, ReleaseStatus.DL, "DL", LMS_PINCODE.NORTH_DE);
        
    }

    @Test(groups = {"P0","Smoke", "Regression"}, description = "ID: 124, LMS E2E flow from order placement to DL",priority = 3)
    public void E2E_LMS_CRETE_ORDER_EK() throws Exception {
            String[] sku = {"3837:1"};
            String orderId = lmsServiceHelper.createAndMarkOrderToStatus(LMS_LOGIN.LogIn64, sku, ReleaseStatus.DL, "DL", LMS_PINCODE.PUNE_EK);
            
        
        
    }

    //@Test(groups = {"Smoke", "Regression"}, description = "ID: 125, EK PICKUP_SUCCESSFUL flow",priority = 3)
//    public void E2E_LMS_EK_processRetrun_PICKUP_SUCCESSFUL() throws Exception {
//        try {
//        	String orderId = lmsHepler.getOrderFromTestDB( "'DL','SH','PK'", "ML", "560068", "DL", "NORMAL", "cod");
//        	String releaseId = omsServiceHelper.getPacketId(orderId);
//            if (!omsServiceHelper.getOrderReleaseEntry(releaseId).getStatus().equals("DL")) {
//                processRelease.processReleaseToStatus(new ReleaseEntry.Builder(releaseId, ReleaseStatus.DL).force(true).build());
//            }
//            com.myntra.oms.client.entry.OrderReleaseEntry orderReleaseEntry = omsServiceHelper.getOrderReleaseEntry(releaseId);
//            ReturnResponse returnResponse = rmsServiceHelper.createReturn(orderReleaseEntry.getOrderLines().get(0).getId().toString(), 1, ReturnMode.PICKUP, 27L, RefundMode.CASHBACK, "1234567890", "Myntra test lms automation", "Pune", "MH", LMS_PINCODE.PUNE_EK, "EK");
//            Assert.assertEquals(returnResponse.getStatus().getStatusType().toString(), "SUCCESS", "Return creation failed");
//            String returnID = returnResponse.getData().get(0).getId().toString();
//            lmsServiceHelper.validatePickupStatusInLMS(""+returnID, "PICKUP_CREATED", 15);
//            lmsServiceHelper.processReturnInLMS(returnID.toString(), "PICKED_UP_SUCCESSFULLY");
//            lmsHepler.updateTestOrder(orderId, "RT");
//        } catch (AssertionError | Exception e) {
//            String[] sku = {"3867:1"};
//            String orderId = lmsServiceHelper.createAndMarkOrderToStatus(LMS_LOGIN.LogIn65, sku, ReleaseStatus.DL, "DL", LMS_PINCODE.PUNE_EK);
//            com.myntra.oms.client.entry.OrderReleaseEntry orderReleaseEntry = omsServiceHelper.getOrderReleaseEntry(omsServiceHelper.getPacketId(orderId));
//            ReturnResponse returnResponse = rmsServiceHelper.createReturn(orderReleaseEntry.getOrderLines().get(0).getId().toString(), 1, ReturnMode.PICKUP, 27L, RefundMode.CASHBACK, "1234567890", "Myntra test lms automation", "Pune", "MH", LMS_PINCODE.PUNE_EK, "EK");
//            Assert.assertEquals(returnResponse.getStatus().getStatusType().toString(), "SUCCESS", "Return creation failed");
//            Long returnID = returnResponse.getData().get(0).getId();
//            lmsServiceHelper.validatePickupStatusInLMS(""+returnID, "PICKUP_CREATED", 15);
//            lmsServiceHelper.processReturnInLMS(returnID.toString(), "PICKED_UP_SUCCESSFULLY");
//        }
//    }

    @Test(groups = {"Smoke", "Regression"}, description = "ID: 126, LMS E2E flow for exchange", priority = 7)
    public void E2E_LMS_ProcessExchangeEK() throws Exception {
        String[] sku = {"3878:1"};
        String orderId = lmsServiceHelper.createAndMarkOrderToStatus(LMS_LOGIN.LogIn65, sku, ReleaseStatus.DL, "DL", LMS_PINCODE.PUNE_EK);
        String packetId = omsServiceHelper.getPacketId(orderId);
        String releaseId = omsServiceHelper.getReleaseId(orderId);
        OrderReleaseEntry orderReleaseEntry = omsServiceHelper.getOrderReleaseEntry(releaseId);
        Long lineID = orderReleaseEntry.getOrderLines().get(0).getId();
        ExchangeOrderResponse exchangeOrderResponse = (ExchangeOrderResponse) ppsServiceHelper.createExchange("" + orderId, "" + lineID, "DNL", 1, "3879");
        Assert.assertNotNull(exchangeOrderResponse.getSuccess(), "Unable to create exchange");

        //String exchangeOrderID = omsServiceHelper.getOrderEntry(exchangeOrderResponse.getExchangeOrderId()).getId().toString();
        List<ReleaseEntry> releaseEntries = new ArrayList<>();
        releaseEntries.add(new ReleaseEntry.Builder(releaseId, ReleaseStatus.DL).shipmentSource(ShipmentSource.MYNTRA).build());
        processRelease.processReleaseToStatus(processRelease.getReleaseEntryList(releaseEntries));
        String returnID = lmsHepler.getReturnIdFromOrderId(""+releaseId);
        Map<String, Object> returnShipment = DBUtilities.exSelectQueryForSingleRecord("select * from return_shipment where source_return_id = " + returnID, "lms");
        long destWarehouseId = (long) returnShipment.get("return_warehouse_id");
        lmsServiceHelper.transferShipmentBackToWH(returnID, destWarehouseId, 5, "DC", "WH");
    }

    @Test(groups = {"Smoke", "Regression"}, description = "ID: 127, LMS E2E flow from order placement to DL", priority = 3)
    public void E2E_LMS_CRETE_ORDER_EK_NDD() throws Exception {
        String[] sku = {"3913:1"};
        lmsServiceHelper.createAndMarkOrderToStatus(LMS_LOGIN.LogIn66, sku, ReleaseStatus.DL, "DL", LMS_PINCODE.PUNE_EK, false, PaymentMode.COD, "EXPRESS");
    }

    @Test(groups = {"Smoke", "Regression"}, description = "ID: 128, LMS E2E flow from order placement to DL", priority = 3)
    public void E2E_LMS_CRETE_ORDER_EK_SDD() throws Exception {
        String[] sku = {"3867:1"};
        lmsServiceHelper.createAndMarkOrderToStatus(LMS_LOGIN.LogIn67, sku, ReleaseStatus.DL, "DL", LMS_PINCODE.PUNE_EK, false, PaymentMode.COD, "SDD");
    }

    //To run this test success fully one need to make sure regional handover is enabled for 400053 pincode and defined DC: 2281 as DC-Delhi as RH-DC, and 2 intermediate hubs Hub:1 & 2 in Networks, Courier code is DE
    @Test(groups = {"P0","Smoke", "Regression"}, description = "ID: 129, Process order through regional handover. First place the order with normal way " +
            "for a regional courier pincode. Then process the entire flow of RHD till Delivered including the CTS updates", priority = 3)
    public void LMS_Regional_handover() throws Exception {
        
    		try {      	
	        	String orderId = lmsHepler.getOrderFromTestDB( "'PK','WP'", "DE", "400053", "DL");  
	        	List<ReleaseEntry> releaseEntries = new ArrayList<>();
	        	releaseEntries.add(new ReleaseEntry.Builder(orderId, ReleaseStatus.DL).shipmentSource(ShipmentSource.MYNTRA).build());
	        	processRelease.processReleaseToStatus(processRelease.getReleaseEntryList(releaseEntries));
	        Assert.assertEquals(omsServiceHelper.getOrderReleaseEntry(omsServiceHelper.getPacketId(orderId)).getStatus().toString(), "DL", "Status in oms is not PK");
	        lmsHepler.updateTestOrder(""+orderId);
        } catch (Exception e) {
	        String[] sku = {"3867:2"};
	        String orderId = lmsServiceHelper.createAndMarkOrderToStatus(LMS_LOGIN.LogIn68, sku, ReleaseStatus.DL, "DL", LMS_PINCODE.MUMBAI_DE_RHD);
	        System.out.println("orderId : "+orderId);
        }
    }

    @Test(groups = {"P0","Smoke", "Regression"}, description = "ID: 130, Process order through regional handover. First place the order with mock " +
            "for a regional courier pincode. Then process the entire flow of RHD till Delivered including the CTS updates", priority = 3)
    public void LMS_Regional_handover_mock() throws Exception {
        lmsHepler.createMockOrder(EnumSCM.DL, LMS_PINCODE.MUMBAI_DE_RHD, "DE", "36", EnumSCM.NORMAL,"cod",false, false);
    }

    @Test(groups = {"P0","Smoke", "Regression"}, description = "ID: 131, LMS E2E flow from order placement to DL for BD", priority = 3)
    public void LMS_BD() throws Exception {
        lmsHepler.createMockOrder(EnumSCM.DL,LMS_PINCODE.ODISHA_BD,"BD","36",EnumSCM.NORMAL,"cod",false,true);
    }

    @Test(groups = {"P0","Smoke", "Regression"}, description = "ID: 132, LMS E2E flow from order placement to DL", priority = 3)
    public void LMS_IP() throws Exception {
        lmsHepler.createMockOrder(EnumSCM.DL,LMS_PINCODE.JAMMU_IP,"IP","36",EnumSCM.NORMAL,"cod",false,true);
    }

    @Test(groups = {"P0","Smoke", "Regression"}, priority = 6, description = "ID: 133, LMS E2E flow from order placement to DL and than create return and process return till receive back in WH", enabled = false)
    public void E2E_LMS_IP_PICKUP_SUCCESSFUL() throws Exception {
    	String orderID = lmsHepler.createMockOrder(EnumSCM.DL,LMS_PINCODE.ML_BLR,"ML","36",EnumSCM.NORMAL,"cod",false,true);
        com.myntra.oms.client.entry.OrderReleaseEntry orderReleaseEntry = omsServiceHelper.getOrderReleaseEntry(omsServiceHelper.getPacketId(orderID));
        @SuppressWarnings("deprecation")
		ReturnResponse returnResponse = rmsServiceHelper.createReturn(orderReleaseEntry.getOrderLines().get(0).getId().toString(), 1, ReturnMode.PICKUP, 27L, RefundMode.CASHBACK, "1234567890", "Myntra test lms automation", "Jammu", "JAMMU", LMS_PINCODE.JAMMU_IP, "IP");
        Assert.assertEquals(returnResponse.getStatus().getStatusType().toString(), "SUCCESS", "Return creation failed");
        String returnID = returnResponse.getData().get(0).getId().toString();
        lmsServiceHelper.validateRmsLmsReturnCreation(""+returnID);
    }

    @Test(groups = { "Smoke","Regression"}, priority = 3,description = "ID: 134, 1. One order is already present in oms and LMS.\n2. We are changing in DB the status to AWAITED.\n3. Fetch a unique tracking number delhivery"
            + "\n4. create a payload containing the new tracking number and push it to the packReleaseEventsV2 queue. \n5. check the status in db after 10 sec delay that it changes from AWAITED to ACCEPTED")
    public void LMS_delhiveryE2EDL() throws Exception{
    
    	    LMSDBHelper lmsdbhelper = new LMSDBHelper();
    	    String orderId = lmsHepler.createMockOrder(EnumSCM.SH, LMS_PINCODE.NORTH_DE, "DE", "36", EnumSCM.NORMAL,"cod",false, true);
        String releaseId = omsServiceHelper.getPacketId(orderId);
        String trackingNumber  = lmsHepler.getTrackingNumber(releaseId);

        CourierTrackingResponse ctResponse = delhiveryHelper.updateDE_CTS(trackingNumber, ""+releaseId, "Bangalore", "UD", "Manifested", "Shipped from bangalore");
        Assert.assertEquals(ctResponse.getStatus().getStatusCode() , 3);
        Assert.assertEquals(ctResponse.getStatus().getStatusType().toString(), "SUCCESS");
        Assert.assertEquals(lmsdbhelper.getShipmentStatus(""+releaseId), "SHIPPED");

        ctResponse = delhiveryHelper.updateDE_CTS(trackingNumber, ""+releaseId, "Bangalore", "UD", "In Transit", "reached Delhi");
        Assert.assertEquals(ctResponse.getStatus().getStatusCode() , 3);
        Assert.assertEquals(ctResponse.getStatus().getStatusType().toString(), "SUCCESS");
        Assert.assertTrue(lmsServiceHelper.validateOrderStatusInLMS(""+releaseId,EnumSCM.SHIPPED,5));

        ctResponse = delhiveryHelper.updateDE_CTS(trackingNumber, ""+releaseId, "Bangalore", "UD", "Pending", "At delhi hub");
        Assert.assertEquals(ctResponse.getStatus().getStatusCode() , 3);
        Assert.assertEquals(ctResponse.getStatus().getStatusType().toString(), "SUCCESS");

        ctResponse = delhiveryHelper.updateDE_CTS(trackingNumber, ""+releaseId, "Bangalore", "UD", "Dispatched", "Out for delivery");
        Assert.assertEquals(ctResponse.getStatus().getStatusCode() , 3);
        Assert.assertEquals(ctResponse.getStatus().getStatusType().toString(), "SUCCESS");
        Assert.assertTrue(lmsServiceHelper.validateOrderStatusInLMS(""+releaseId,EnumSCM.OUT_FOR_DELIVERY,5));

        ctResponse = delhiveryHelper.updateDE_CTS(trackingNumber, ""+releaseId, "Bangalore", "DL", "Delivered", "Shipment Delivered");
        Assert.assertEquals(ctResponse.getStatus().getStatusCode() , 3);
        Assert.assertEquals(ctResponse.getStatus().getStatusType().toString(), "SUCCESS");
        Assert.assertTrue(lmsServiceHelper.validateOrderStatusInLMS(""+releaseId,EnumSCM.DELIVERED,5));
    }

    @Test(groups = { "Smoke","Regression"}, priority = 3, description = "ID: 135, 1. One order is already present in oms and LMS.\n2. We are changing in DB the status to AWAITED.\n3. Fetch a unique tracking number delhivery"
            + "\n4. create a payload containing the new tracking number and push it to the packReleaseEventsV2 queue. \n5. check the status in db after 10 sec delay that it changes from AWAITED to ACCEPTED")
    public void LMS_delhiveryE2ERTO() throws Exception {
    	String orderId = lmsHepler.createMockOrder(EnumSCM.SH, LMS_PINCODE.NORTH_DE, "DE", "36", EnumSCM.NORMAL,"cod",false, true);
    	String releaseId = omsServiceHelper.getPacketId(orderId);
        String trackingNumber  = lmsHepler.getTrackingNumber(releaseId);

        CourierTrackingResponse ctResponse = delhiveryHelper.updateDE_CTS(trackingNumber, ""+releaseId, "Bangalore", "UD", "Manifested", "Shipped from bangalore");
        Assert.assertEquals(ctResponse.getStatus().getStatusCode() , 3);
        Assert.assertEquals(ctResponse.getStatus().getStatusType().toString(), "SUCCESS");
        Assert.assertTrue(lmsServiceHelper.validateOrderStatusInLMS(""+releaseId,EnumSCM.SHIPPED,5));

        ctResponse = delhiveryHelper.updateDE_CTS(trackingNumber, ""+releaseId, "Bangalore", "UD", "In Transit", "reached Delhi");
        Assert.assertEquals(ctResponse.getStatus().getStatusCode() , 3);
        Assert.assertEquals(ctResponse.getStatus().getStatusType().toString(), "SUCCESS");

        ctResponse = delhiveryHelper.updateDE_CTS(trackingNumber, ""+releaseId, "Bangalore", "UD", "Pending", "At delhi hub");
        Assert.assertEquals(ctResponse.getStatus().getStatusCode() , 3);
        Assert.assertEquals(ctResponse.getStatus().getStatusType().toString(), "SUCCESS");

        ctResponse = delhiveryHelper.updateDE_CTS(trackingNumber, ""+releaseId, "Bangalore", "UD", "Dispatched", "Out for delivery");
        Assert.assertEquals(ctResponse.getStatus().getStatusCode() , 3);
        Assert.assertEquals(ctResponse.getStatus().getStatusType().toString(), "SUCCESS");
        Assert.assertTrue(lmsServiceHelper.validateOrderStatusInLMS(""+releaseId,EnumSCM.OUT_FOR_DELIVERY,5));

        ctResponse = delhiveryHelper.updateDE_CTS(trackingNumber, ""+releaseId, "Bangalore", "RT", "In Transit", "Shipment Delivered", "RT-101");
        Assert.assertEquals(ctResponse.getStatus().getStatusCode(), 3);
        Assert.assertEquals(ctResponse.getStatus().getStatusType().toString(), "SUCCESS");
        Assert.assertTrue(lmsServiceHelper.validateOrderStatusInLMS(""+releaseId,EnumSCM.RTO_CONFIRMED,5));

        ctResponse = delhiveryHelper.updateDE_CTS(trackingNumber, ""+releaseId, "Bangalore", "RT", "Pending", "Shipment Delivered");
        Assert.assertEquals(ctResponse.getStatus().getStatusCode() , 3);
        Assert.assertEquals(ctResponse.getStatus().getStatusType().toString(), "SUCCESS");
        Assert.assertTrue(lmsServiceHelper.validateOrderStatusInLMS(""+releaseId,EnumSCM.RTO_IN_TRANSIT,5));

        ctResponse = delhiveryHelper.updateDE_CTS(trackingNumber, ""+releaseId, "Bangalore", "RT", "Dispatched", "Shipment Delivered");
        Assert.assertEquals(ctResponse.getStatus().getStatusCode() , 3);
        Assert.assertEquals(ctResponse.getStatus().getStatusType().toString(), "SUCCESS");
        Assert.assertTrue(lmsServiceHelper.validateOrderStatusInLMS(""+releaseId,EnumSCM.RTO_DISPATCHED,5));

        ctResponse = delhiveryHelper.updateDE_CTS(trackingNumber, ""+releaseId, "Bangalore", "DL", "RTO", "Shipment Delivered");
        Assert.assertEquals(ctResponse.getStatus().getStatusCode() , 3);
        Assert.assertEquals(ctResponse.getStatus().getStatusType().toString(), "SUCCESS");
        Assert.assertTrue(lmsServiceHelper.validateOrderStatusInLMS(""+releaseId,EnumSCM.RTO_DISPATCHED,5));
    }

}
