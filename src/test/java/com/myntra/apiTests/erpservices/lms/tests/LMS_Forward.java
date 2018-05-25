package com.myntra.apiTests.erpservices.lms.tests;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.myntra.apiTests.common.Constants.EnumSCM;
import com.myntra.apiTests.common.Constants.ReleaseStatus;
import com.myntra.apiTests.common.ProcessOrder.Service.ProcessRelease;
import com.myntra.apiTests.common.entries.ReleaseEntry;
import com.myntra.apiTests.end2end.End2EndHelper;
import com.myntra.apiTests.erpservices.lms.Constants.CourierCode;
import com.myntra.apiTests.erpservices.lms.Constants.LMS_LOGIN;
import com.myntra.apiTests.erpservices.lms.Constants.LMS_PINCODE;
import com.myntra.apiTests.erpservices.lms.Constants.LMS_SKU;
import com.myntra.apiTests.erpservices.lms.Helper.LMSHelper;
import com.myntra.apiTests.erpservices.lms.Helper.LmsServiceHelper;
import com.myntra.apiTests.erpservices.lms.Helper.TMSServiceHelper;
import com.myntra.apiTests.erpservices.lms.dp.LMSTestsDP;
import com.myntra.apiTests.erpservices.oms.OMSServiceHelper;
import com.myntra.apiTests.erpservices.rms.RMSServiceHelper;
import com.myntra.apiTests.erpservices.silkroute.SilkRouteConstants;
import com.myntra.apiTests.erpservices.silkroute.SilkRouteServiceHelper;
import com.myntra.apiTests.portalservices.pps.PPSServiceHelper;
import com.myntra.lms.client.response.OrderEntry;
import com.myntra.lms.client.response.OrderResponse;
import com.myntra.lms.client.response.ReturnAddress;
import com.myntra.lms.client.status.ShipmentType;
import com.myntra.lms.client.status.ShippingMethod;
import com.myntra.logistics.platform.domain.ShipmentUpdateActivityTypeSource;
import com.myntra.lordoftherings.boromir.DBUtilities;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.oms.client.entry.PacketEntry;
import com.myntra.paymentplan.domain.response.ExchangeOrderResponse;
import com.myntra.returns.common.enums.RefundMode;
import com.myntra.returns.common.enums.ReturnMode;
import com.myntra.returns.common.enums.ReturnType;
import com.myntra.returns.response.ReturnResponse;
import com.myntra.test.commons.testbase.BaseTest;

/**
 * Created by Shubham Gupta on 12/16/16.
 */
public class LMS_Forward extends BaseTest {

    private static org.slf4j.Logger log = LoggerFactory.getLogger(LMS_Forward.class);
    private OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
    private LmsServiceHelper lmsServiceHelper = new LmsServiceHelper();
    private RMSServiceHelper rmsServiceHelper = new RMSServiceHelper();
    private TMSServiceHelper tmsServiceHelper = new TMSServiceHelper();
    private SilkRouteServiceHelper silkRouteServiceHelper = new SilkRouteServiceHelper();
    private LMSHelper lmsHepler = new LMSHelper();
    private ProcessRelease processRelease = new ProcessRelease();

    @Test(enabled = false)
    public void tempToCreateMultipleOrder() throws Exception {
        // End2EndHelper end2EndHelper = new End2EndHelper();
        LMSHelper lmsHepler = new LMSHelper();
        List<String> orders = new ArrayList<>();
        try {
            for (int i = 0; i < 10; i++) {
                /*String orderID = end2EndHelper.createOrder(LMS_LOGIN.LogIn6, LMS_LOGIN.PASSWORD, LMS_ADDRESSID.MLNORTHAddressId, new String[]{"3867:1"}, "", false, false, false, "", false);
                Assert.assertTrue(omsServiceHelper.validatePacketStatusInOMS(orderID, EnumSCM.WP, 15));
                Assert.assertTrue(omsServiceHelper.validateOrderOFFHoldStatusInOMS(orderID, 15));
                log.info("Order ID : " + orderID);
                end2EndHelper.markOrderDelivered(orderID, EnumSCM.PK, EnumSCM.ORDER);
                String status = lmsHepler.getOrderReleaseStatus(orderID);
                Assert.assertEquals(status, EnumSCM.PK, "Status in oms is not SH");
                */
                /*String orderID = lmsHepler.createMockOrder(EnumSCM.DL, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.NORMAL,"DC",false, true);
                com.myntra.oms.client.entry.OrderReleaseEntry orderReleaseEntry = omsServiceHelper.getPacketEntry(omsServiceHelper.getPacketId(orderID));
                ReturnResponse returnResponse = rmsServiceHelper.createReturn(orderReleaseEntry.getOrderLines().get(0).getId(), 1, ReturnMode.PICKUP, 11L, RefundMode.OR, "1234567890", "Myntra test lms automation", "Bangalore", "KA", LMS_PINCODE.ML_BLR, "ML");
                Assert.assertEquals(returnResponse.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Return creation failed");
                String returnID = ""+returnResponse.getData().get(0).getId();
                */
                String orderID = lmsHepler.createMockOrder(EnumSCM.IS, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.NORMAL, "cod", true, true);
                /*com.myntra.oms.client.entry.OrderReleaseEntry orderReleaseEntry = omsServiceHelper.getPacketEntry(omsServiceHelper.getPacketId(orderID));
                ReturnResponse returnResponse = rmsServiceHelper.createReturn(orderReleaseEntry.getOrderLines().get(0).getId(), 1, ReturnMode.PICKUP,
                        27L, RefundMode.CASHBACK, "1234567890", "Myntra test lms automation", "Bangalore", "KA", LMS_PINCODE.JAMMU_IP, "IP");
                Assert.assertEquals(returnResponse.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Return creation failed");
                String returnID = ""+returnResponse.getData().get(0).getId();*/
                orders.add(orderID);
            }
        } finally {
            log.info("" + orders);
        }
    }


    @Test(groups = {"P0","Smoke", "Regression"}, description = "ID: C453 , LMS E2E flow from order placement to DL", enabled = true, priority = 0)
    public void E2E_DL() throws Exception {
        String[] sku = {LMS_SKU.S1541_36_21_1+":1"};
        lmsServiceHelper.createAndMarkOrderToStatus(LMS_LOGIN.LogIn1, sku, ReleaseStatus.DL, EnumSCM.DL, LMS_PINCODE.ML_BLR);
    }

    @Test(groups = {"Smoke", "Regression"}, description = "ID: C469 , LMS E2E flow from order placement to DL", priority = 0)
    public void E2E_LMS_PARTIAL_CANCEL_DL() throws Exception {

        String orderId = lmsHepler.createMockOrder(EnumSCM.WP, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.XPRESS, "cod", false, true);
        String releaseId = omsServiceHelper.getReleaseId(orderId);
        String packetId = omsServiceHelper.getPacketId(orderId);
        Long cancellineID = omsServiceHelper.getPacketEntry(packetId).getOrderLines().get(0).getId();
        omsServiceHelper.cancelLine(releaseId, "lmsautomation7@myntra.com", new String[]{cancellineID + ":1"}, 41);
        List<ReleaseEntry> releaseEntries = new ArrayList<>();
        releaseEntries.add(new ReleaseEntry.Builder(releaseId, ReleaseStatus.DL).force(true).build());
        processRelease.processReleaseToStatus(processRelease.getReleaseEntryList(releaseEntries));
        Assert.assertEquals(omsServiceHelper.getPacketEntry(packetId).getStatus().toString(), EnumSCM.D, "Status in oms is not DL");
    }

    @Test(groups = {"Smoke", "Regression"}, description = "ID: C468 , LMS E2E flow from order placement to DL", priority = 0)
    public void E2E_LMS_MADURA_DL() throws Exception {
        String[] sku = {"895234:1"};
        lmsServiceHelper.createAndMarkOrderToStatus(LMS_LOGIN.LogIn8, sku, ReleaseStatus.DL, EnumSCM.DL, LMS_PINCODE.ML_BLR);
    }

    @Test(groups = {"Smoke", "Regression"}, description = "ID: C473 , LMS E2E flow from order placement to DL", priority = 0)
    public void E2E_LMS_SELLER_DL() throws Exception {
        String[] sku = {"1251868:1"};
        lmsServiceHelper.createAndMarkOrderToStatus(LMS_LOGIN.LogIn9, sku, ReleaseStatus.DL, EnumSCM.DL, LMS_PINCODE.ML_BLR);
    }

    @Test(groups = {"Smoke", "Regression"}, description = "ID: C455 , LMS E2E flow from order placement to DL With multiSku MultiQty and MultiRelease", priority = 0)
    public void E2E_LMS_DL_MultiQty_MultiRelease() throws Exception {
        String[] sku = {"3867:2", "3877:2", "3868:1"};
        lmsServiceHelper.createAndMarkOrderToStatus(LMS_LOGIN.LogIn10, sku, ReleaseStatus.DL, EnumSCM.DL, LMS_PINCODE.ML_BLR);
    }

    @Test(groups = {"P0","Smoke", "Regression"}, description = "ID: C458 , LMS E2E flow from order placement to DL", priority = 0)
    public void E2E_LMS_DL_SDD() throws Exception {
        lmsHepler.createMockOrder(EnumSCM.DL, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.XPRESS, "cod", false, true);
    }

    @Test(groups = {"P0","Smoke", "Regression"}, description = "ID: C456 , LMS E2E flow from order placement to DL with Express order", priority = 0)
    public void E2E_LMS_DL_NDD() throws Exception {
        lmsHepler.createMockOrder(EnumSCM.DL, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.SDD, "cod", false, true);
    }

    @Test(groups = {"Smoke", "Regression"}, description = "ID: C457 , LMS E2E flow from order placement to DL", priority = 0)
    public void E2E_LMS_DL_NDD_CC() throws Exception {
        lmsHepler.createMockOrder(EnumSCM.DL, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.XPRESS, "CC", false, true);
    }

    @Test(groups = {"P0", "Smoke", "Regression"}, description = "ID: C454 , LMS E2E flow from order placement to DL", priority = 0)
    public void E2E_LMS_DL_CC() throws Exception {
        lmsHepler.createMockOrder(EnumSCM.DL, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.NORMAL, "CC", false, true);
    }

    @Test(groups = {"Smoke", "Regression"}, description = "ID: C459 , LMS E2E flow from order placement to DL", priority = 0)
    public void E2E_LMS_DL_Wallet() throws Exception {
        lmsHepler.createMockOrder(EnumSCM.DL, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.NORMAL, "WALLET", false, true);
    }

    @Test(groups = {"P0", "Smoke", "Regression"}, description = "ID: C460 , LMS E2E flow from order placement and FD->DL", priority = 0)
    public void E2E_LMS_FDDL() throws Exception {
    	
    		String orderId = lmsHepler.createMockOrder(EnumSCM.PK, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.NORMAL, "cod", false, true);
        String releaseId = omsServiceHelper.getReleaseId(orderId);
        List<ReleaseEntry> releaseEntries = new ArrayList<>();
        releaseEntries.add(new ReleaseEntry.Builder(releaseId, ReleaseStatus.FDDL).force(true).build());
        processRelease.processReleaseToStatus(processRelease.getReleaseEntryList(releaseEntries));
    }

    @Test(groups = {"P0", "Smoke", "Regression"}, description = "ID: C462 , In the same trip mark order \"Failed\" first and than mark \"Delivered\" on same Trip", priority = 0)
    public void E2E_LMS_FDTODL() throws Exception {
        
    		String releaseId = omsServiceHelper.getReleaseId(lmsHepler.createMockOrder(EnumSCM.PK, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.NORMAL, "cod", false, true));     
        List<ReleaseEntry> releaseEntries = new ArrayList<>();
        releaseEntries.add(new ReleaseEntry.Builder(releaseId, ReleaseStatus.FDTODL).force(true).build());
        processRelease.processReleaseToStatus(processRelease.getReleaseEntryList(releaseEntries));
    }

    @Test(groups = {"Smoke", "Regression"}, description = "ID: C461 , LMS E2E flow from order placement to Failed delivered->Failed Delivered->Delivered", priority = 0)
    public void E2E_LMS_FDFDDL() throws Exception {
    	
        String releaseId = omsServiceHelper.getReleaseId(lmsHepler.createMockOrder(EnumSCM.PK, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.NORMAL, "cod", false, true));
        List<ReleaseEntry> releaseEntries = new ArrayList<>();
        releaseEntries.add(new ReleaseEntry.Builder(releaseId, ReleaseStatus.FDFDDL).force(true).build());
        processRelease.processReleaseToStatus(processRelease.getReleaseEntryList(releaseEntries));
    }

    @Test(groups = {"P0","Smoke", "Regression"}, description = "ID: C471 , LMS E2E flow from order placement and marking delivered by Customer after trip creation", priority = 0)
    public void E2E_LMS_SELF_MARK_DL() throws Exception {
        String releaseId = omsServiceHelper.getReleaseId(lmsHepler.createMockOrder(EnumSCM.PK, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.NORMAL, "cod", false, true));
        List<ReleaseEntry> releaseEntries = new ArrayList<>();
        releaseEntries.add(new ReleaseEntry.Builder(releaseId, ReleaseStatus.SMDL).force(true).build());
        processRelease.processReleaseToStatus(processRelease.getReleaseEntryList(releaseEntries));
    }

    @Test(groups = {"Smoke", "Regression"}, dataProviderClass = LMSTestsDP.class,dataProvider = "selfMarkDL" ,description = "ID: C655, LMS E2E flow from order placement and marking delivered by Customer with different statuses of order", priority = 0)
    public void E2E_LMS_SELF_MARK_DL_AT_DIFFERENT_statue(String toStatus, String pincode, String courierCode, String shippingMethod, boolean istrynBuy, String statusType) throws Exception {
        String packetId = omsServiceHelper.getPacketId(lmsHepler.createMockOrder(toStatus, pincode, courierCode, "36", shippingMethod, "cod", istrynBuy, true));
        Assert.assertEquals(lmsServiceHelper.selfMarkDL(packetId).getStatus().getStatusType().toString(), statusType, "Unable to mark delivered via self mark delivered api");
        if (statusType.equals(EnumSCM.SUCCESS)) {
            Assert.assertTrue(lmsServiceHelper.validateOrderStatusInLMS(packetId, EnumSCM.DELIVERED, 2), "Order not delivered in LMS");
            Assert.assertTrue(omsServiceHelper.validatePacketStatusInOMS(packetId, EnumSCM.D, 2), "Order not DL in OMS");
        }else {
            Assert.assertFalse(lmsServiceHelper.validateOrderStatusInLMS(packetId, EnumSCM.DELIVERED, 2), "Order not delivered in LMS");
            Assert.assertFalse(omsServiceHelper.validatePacketStatusInOMS(packetId, EnumSCM.D, 2), "Order not DL in OMS");
        }
    }

    @SuppressWarnings("unchecked")
    @Test(groups = {"Smoke", "Regression"}, description = "ID: C656, LMS E2E flow from order placement and marking delivered by Customer after shipping the container for ML", priority = 0)
    public void E2E_LMS_SELF_MARK_ON_IN_TRANSIT() throws Exception {
        String packetId = omsServiceHelper.getPacketId(lmsHepler.createMockOrder(EnumSCM.ADDED_TO_MB, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.NORMAL, "cod", false, true));
        long masterBagId = Long.parseLong(lmsHepler.getMasterBagId(""+packetId));
        Assert.assertEquals(lmsServiceHelper.closeMasterBag(masterBagId).getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to close masterBag");
        tmsServiceHelper.processInTMSFromClosedShipped.accept(masterBagId);
        Assert.assertTrue(omsServiceHelper.validatePacketStatusInOMS(packetId,EnumSCM.S,5));
        Assert.assertEquals(lmsServiceHelper.selfMarkDL(""+packetId).getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to mark delivered via self mark delivered api");
        Assert.assertTrue(lmsServiceHelper.validateOrderStatusInLMS(""+packetId, EnumSCM.DELIVERED, 2), "Order not delivered in LMS");
        Assert.assertTrue(omsServiceHelper.validatePacketStatusInOMS(packetId, EnumSCM.D, 2), "Order not DL in OMS");
    }

    @Test(groups = {"P0", "Smoke", "Regression"}, description = "ID: 467C , LMS E2E flow from order placement to LOST", priority = 1)
    public void E2E_LMS_LOST_ON_TRIP() throws Exception {
        String packetId = omsServiceHelper.getPacketId(lmsHepler.createMockOrder(EnumSCM.LOST, LMS_PINCODE.NORTH_CGH, "ML", "36", EnumSCM.NORMAL, "cod", false, true));
        Assert.assertTrue(omsServiceHelper.validatePacketStatusInOMS(packetId, "L", 5));
    }

    @Test(groups = {"Smoke", "Regression"}, dataProviderClass = LMSTestsDP.class, dataProvider = "lmsLost", description = "ID: C739, LMS E2E flow from order placement to LOST for Different paymentMode, Status, CourierCode etc", priority = 1)
    public void E2E_LMS_LOST(String status, String pincode, String courierCode, String warehouse, String shippingMethod, String paymentMode, boolean isTnB, String statusType) throws Exception {
        
    		String orderId = lmsHepler.createMockOrder(status, pincode, courierCode, warehouse, shippingMethod, paymentMode, isTnB, true);
    		String packetId = omsServiceHelper.getPacketId(orderId);
    		String releaseId = omsServiceHelper.getReleaseId(orderId);
    		
        if (status.equals(EnumSCM.PK)||status.equals(EnumSCM.IS)||status.equals(EnumSCM.ADDED_TO_MB)){
            Assert.assertEquals(lmsServiceHelper.markOrderLOSTINTRANSIT(packetId), EnumSCM.SUCCESS);
        }else if (status.equals(EnumSCM.RECEIVE_IN_DC)){
        	
        		List<ReleaseEntry> releaseEntries = new ArrayList<>();
            releaseEntries.add(new ReleaseEntry.Builder(releaseId, ReleaseStatus.LOST).force(true).build());
            processRelease.processReleaseToStatus(processRelease.getReleaseEntryList(releaseEntries));
        }else {
            Assert.assertEquals(lmsServiceHelper.markOrderLOSTINTRANSIT(packetId), EnumSCM.SUCCESS);
        }
        if (statusType.equals(EnumSCM.SUCCESS))
            Assert.assertTrue(omsServiceHelper.validatePacketStatusInOMS(packetId, "L", 5));
    }

    @Test(groups = {"P0", "Smoke", "Regression"}, description = "ID: C470 , LMS E2E flow from order placement to RTO", priority = 1)
    public void E2E_LMS_RTO() throws Exception {
        String orderID = null;
        try {
//          	orderID = lmsHepler.getOrderFromTestDB("'SH','PK','WP'", "ML", LMS_PINCODE.ML_BLR, EnumSCM.DL, EnumSCM.NORMAL, "cod");
//            String packetId = omsServiceHelper.getPacketId(orderID);
//            processRelease.processReleaseToStatus(new ReleaseEntry.Builder(packetId, ReleaseStatus.RTO).force(true).build());
//            lmsServiceHelper.transferShipmentBackToWHRTO(packetId);
//            Assert.assertEquals(rmsServiceHelper.recieveShipmentInRejoy(packetId, 36).getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "RTO_RECEIVED call failed for RMS");
//            Assert.assertTrue(lmsServiceHelper.validateOrderStatusInLMS(packetId, EnumSCM.RECEIVED_IN_RETURNS_HUB, 5), "RTO order is not in RTO_RECEIVED state");
//        } catch (Exception e) {
            String[] sku = {"371389:1"};
            orderID = lmsServiceHelper.createAndMarkOrderToStatus(LMS_LOGIN.LogIn25, sku, ReleaseStatus.RTO, EnumSCM.RTO, LMS_PINCODE.ML_BLR);
            String packetId = omsServiceHelper.getPacketId(orderID);
            lmsServiceHelper.transferShipmentBackToWHRTO(orderID);
            Assert.assertEquals(rmsServiceHelper.recieveShipmentInRejoy(packetId, 36).getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "RTO_RECEIVED call failed for RMS");
            Assert.assertTrue(lmsServiceHelper.validateOrderStatusInLMS(packetId, EnumSCM.RECEIVED_IN_RETURNS_HUB, 5), "RTO order is not in RTO_RECEIVED state");
        } finally {
            lmsHepler.deleteTestOrder(orderID);
        }
    }

    @Test(groups = {"P0","Smoke", "Regression"}, dataProviderClass = LMSTestsDP.class, dataProvider = "unrto" ,description = "ID: C474 , LMS E2E flow from order placement to DL", priority = 1)
    public void E2E_LMS_UNRTO( String pincode, String courierCode, String warehouse, String shippingMethod, String paymentMode, boolean isTnB, String statusType) throws Exception {
        String packetId = omsServiceHelper.getPacketId(lmsHepler.createMockOrder(EnumSCM.UNRTO, pincode, courierCode, warehouse, shippingMethod, paymentMode, isTnB, true));
        lmsServiceHelper.transferShipmentBackToWHRTO(packetId);
        Assert.assertEquals(rmsServiceHelper.recieveShipmentInRejoy(packetId, 36).getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "RTO_RECEIVED call failed for RMS");
        Assert.assertTrue(lmsServiceHelper.validateOrderStatusInLMS(packetId, EnumSCM.RECEIVED_IN_RETURNS_HUB, 5), "RTO order is not in RTO_RECEIVED state");
    }

    @SuppressWarnings("unchecked")//NEED TO FIX
    @Test(groups = {"P0","Smoke", "Regression"}, description = "ID: C446 , LMS E2E flow from order placement to LOST on PK then Mark RTO", priority = 1)
    public void ADMIN_LMS_PK_LOST_RTO() throws Exception {

        String orderId =lmsHepler.createMockOrder(EnumSCM.PK, LMS_PINCODE.NORTH_CGH, "ML", "36", EnumSCM.NORMAL, "cod", false, true);
        String packetId = omsServiceHelper.getPacketId(orderId);
        //String releaseId = omsServiceHelper.getReleaseId(orderId);
        Assert.assertEquals(lmsServiceHelper.markOrderLOSTINTRANSIT(packetId), EnumSCM.SUCCESS);
        Assert.assertTrue(lmsServiceHelper.validateOrderStatusInLMS(packetId, EnumSCM.LOST, 5));
        Assert.assertTrue(omsServiceHelper.validatePacketStatusInOMS(packetId, "L", 5));
        Assert.assertEquals(lmsServiceHelper.forceMarkRTO.apply(packetId).toString(), EnumSCM.SUCCESS);
        Assert.assertTrue(lmsServiceHelper.validateOrderStatusInLMS(packetId, EnumSCM.RTO_CONFIRMED, 5));
        Assert.assertTrue(omsServiceHelper.validatePacketStatusInOMS(packetId, EnumSCM.RTO, 3));
//        Assert.assertEquals(rmsServiceHelper.recieveShipmentInRejoy(releaseId, 36).getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "RTO_RECEIVED call failed for RMS");
//       Assert.assertTrue(lmsServiceHelper.validateOrderStatusInLMS(packetId, EnumSCM.RECEIVED_IN_RETURNS_HUB, 5), "RTO order is not in RTO_RECEIVED state");
    }

    @SuppressWarnings("unchecked")//
    @Test(groups = {"Smoke", "Regression"}, description = "ID: C444 , LMS E2E flow from order placement to LOST on IS then Mark RTO", priority = 1)
    public void ADMIN_LMS_IS_LOST_RTO() throws Exception {
        String packetId = omsServiceHelper.getPacketId(lmsHepler.createMockOrder(EnumSCM.IS, LMS_PINCODE.NORTH_CGH, "ML", "36", EnumSCM.NORMAL, "cod", false, true));
        Assert.assertEquals(lmsServiceHelper.markOrderLOSTINTRANSIT(packetId), EnumSCM.SUCCESS);
        Assert.assertTrue(lmsServiceHelper.validateOrderStatusInLMS(packetId, EnumSCM.LOST_IN_HUB, 5));
        Assert.assertTrue(omsServiceHelper.validatePacketStatusInOMS(packetId, "L", 5));
        Assert.assertEquals(lmsServiceHelper.forceMarkRTO.apply(packetId).toString(), EnumSCM.SUCCESS);
        Assert.assertTrue(lmsServiceHelper.validateOrderStatusInLMS(packetId, EnumSCM.RTO_CONFIRMED, 5));
        Assert.assertTrue(omsServiceHelper.validatePacketStatusInOMS(packetId, EnumSCM.RTO, 3));
    }

    @SuppressWarnings("unchecked")//
    @Test(groups = {"Smoke", "Regression"}, description = "ID: C445 , LMS E2E flow from order placement to LOST on IS then Mark RTO", priority = 1)
    public void ADMIN_LMS_IS_LOST_RTO_EK() throws Exception {
        String packetId = omsServiceHelper.getPacketId(lmsHepler.createMockOrder(EnumSCM.IS, LMS_PINCODE.PUNE_EK, "EK", "36", EnumSCM.NORMAL, "cod", false, true));
        Assert.assertEquals(lmsServiceHelper.markOrderLOSTINTRANSIT(packetId), EnumSCM.SUCCESS);
        Assert.assertTrue(lmsServiceHelper.validateOrderStatusInLMS(packetId, EnumSCM.LOST_IN_HUB, 5));
        Assert.assertTrue(omsServiceHelper.validatePacketStatusInOMS(packetId, "L", 5));
        Assert.assertEquals(lmsServiceHelper.forceMarkRTO.apply(packetId).toString(), EnumSCM.SUCCESS);
        Assert.assertTrue(lmsServiceHelper.validateOrderStatusInLMS(packetId, EnumSCM.RTO_CONFIRMED, 5));
        Assert.assertTrue(omsServiceHelper.validatePacketStatusInOMS(packetId, EnumSCM.RTO, 3));
    }

    @SuppressWarnings("unchecked")//
    @Test(groups = {"P0","Smoke", "Regression"}, description = "ID: C448 , LMS E2E flow from order placement to LOST on IS then Mark RTO", priority = 1)
    public void ADMIN_LMS_SH_LOST_RTO() throws Exception {
        String packetId = omsServiceHelper.getPacketId(lmsHepler.createMockOrder(EnumSCM.SH, LMS_PINCODE.NORTH_CGH, "ML", "36", EnumSCM.NORMAL, "cod", false, true));
        Assert.assertEquals(lmsServiceHelper.markOrderLOSTINTRANSIT(packetId), EnumSCM.SUCCESS);
        Assert.assertTrue(lmsServiceHelper.validateOrderStatusInLMS(packetId, EnumSCM.LOST, 5));
        Assert.assertTrue(omsServiceHelper.validatePacketStatusInOMS(packetId, "L", 5));
        Assert.assertEquals(lmsServiceHelper.forceMarkRTO.apply(packetId).toString(), EnumSCM.SUCCESS);
        Assert.assertTrue(lmsServiceHelper.validateOrderStatusInLMS(packetId, EnumSCM.RTO_CONFIRMED, 5));
        Assert.assertTrue(omsServiceHelper.validatePacketStatusInOMS(packetId, EnumSCM.RTO, 3));
    }

    @SuppressWarnings("unchecked")//
    @Test(groups = {"Smoke", "Regression"}, description = "ID: C447 , LMS E2E flow from order placement to LOST on IS then Mark RTO", priority = 1)
    public void ADMIN_LMS_RECEIVED_IN_DC_LOST_RTO() throws Exception {
        String packetId = omsServiceHelper.getPacketId(lmsHepler.createMockOrder(EnumSCM.RECEIVE_IN_DC, LMS_PINCODE.NORTH_CGH, "ML", "36", EnumSCM.NORMAL, "cod", false, true));
        Assert.assertEquals(lmsServiceHelper.markOrderLOSTINDC(packetId), EnumSCM.SUCCESS);
        Assert.assertTrue(lmsServiceHelper.validateOrderStatusInLMS(packetId, EnumSCM.LOST, 5));
        Assert.assertTrue(omsServiceHelper.validatePacketStatusInOMS(packetId, "L", 5));
        Assert.assertEquals(lmsServiceHelper.forceMarkRTO.apply(packetId).toString(), EnumSCM.SUCCESS);
        Assert.assertTrue(lmsServiceHelper.validateOrderStatusInLMS(packetId, EnumSCM.RTO_CONFIRMED, 5));
        Assert.assertTrue(lmsServiceHelper.validateOrderStatusInML(packetId, EnumSCM.RTO_CONFIRMED, 5));
        Assert.assertTrue(omsServiceHelper.validatePacketStatusInOMS(packetId, EnumSCM.RTO, 3));
    }

    @SuppressWarnings("unchecked")
    @Test(groups = {"Smoke", "Regression"}, description = "ID: C443 , LMS E2E flow from order placement to DL Mark RTO", priority = 1)
    public void ADMIN_DL_RTO() throws Exception {
        String packetId = omsServiceHelper.getPacketId(lmsHepler.createMockOrder(EnumSCM.DL, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.NORMAL, "cod", false, true));
        Assert.assertTrue(lmsServiceHelper.validateOrderStatusInLMS(packetId, EnumSCM.DELIVERED, 5));
        Assert.assertEquals(lmsServiceHelper.forceMarkRTO.apply(packetId).toString(), EnumSCM.SUCCESS);
        Assert.assertTrue(lmsServiceHelper.validateOrderStatusInLMS(packetId, EnumSCM.RTO_CONFIRMED, 5));
        Assert.assertTrue(lmsServiceHelper.validateOrderStatusInML(packetId, EnumSCM.RTO_CONFIRMED, 5));
        Assert.assertTrue(omsServiceHelper.validatePacketStatusInOMS(packetId, EnumSCM.RTO, 3));
    }

    @SuppressWarnings("unchecked")
    @Test(groups = {"P0","Smoke", "Regression"}, description = "ID: C450 , ADMIN_RTO->LOST", priority = 1)
    public void ADMIN_RTOL() throws Exception {
        String packetId = omsServiceHelper.getPacketId(lmsHepler.createMockOrder(EnumSCM.RTO, LMS_PINCODE.NORTH_CGH, "ML", "36", EnumSCM.NORMAL, "cod", false, true));
        Assert.assertTrue(lmsServiceHelper.validateOrderStatusInLMS(packetId, EnumSCM.RTO_CONFIRMED, 5));
        Assert.assertEquals(lmsServiceHelper.forceMarkRTOL.apply(packetId).toString(), EnumSCM.SUCCESS);
        Assert.assertTrue(lmsServiceHelper.validateOrderStatusInLMS(packetId, EnumSCM.RTO_LOST, 4));
    }

    @SuppressWarnings("unchecked")
    @Test(groups = {"Smoke", "Regression"}, description = "ID: C449 , ADMIN_LOST->DL", priority = 1)
    public void ADMIN_LOST_DL() throws Exception {
        String packetId = omsServiceHelper.getPacketId(lmsHepler.createMockOrder(EnumSCM.LOST, LMS_PINCODE.NORTH_CGH, "ML", "36", EnumSCM.NORMAL, "cod", false, true));
        Assert.assertTrue(lmsServiceHelper.validateOrderStatusInLMS(packetId, EnumSCM.LOST, 5));
        Assert.assertEquals(lmsServiceHelper.forceMarkDL.apply(packetId).toString(), EnumSCM.SUCCESS);
        Assert.assertTrue(lmsServiceHelper.validateOrderStatusInLMS(packetId, EnumSCM.DELIVERED, 4));
        Assert.assertTrue(omsServiceHelper.validatePacketStatusInOMS(packetId, EnumSCM.D, 3));
    }

    @Test(groups = {"Smoke", "Regression"}, description = "ID: C442 , ADMIN_DL->LOST", priority = 1)
    public void ADMIN_DL_LOST() throws Exception {
        String packetId = omsServiceHelper.getPacketId(lmsHepler.createMockOrder(EnumSCM.DL, LMS_PINCODE.NORTH_CGH, "ML", "36", EnumSCM.NORMAL, "cod", false, true));
        Assert.assertTrue(lmsServiceHelper.validateOrderStatusInLMS(packetId, EnumSCM.DELIVERED, 5));
        Assert.assertEquals(lmsServiceHelper.markOrderLOSTINTRANSIT(packetId), EnumSCM.SUCCESS);
        Assert.assertTrue(lmsServiceHelper.validateOrderStatusInLMS(packetId, EnumSCM.LOST, 4));
        Assert.assertTrue(lmsServiceHelper.validateOrderStatusInML(packetId, EnumSCM.LOST, 4));
        Assert.assertTrue(omsServiceHelper.validatePacketStatusInOMS(packetId, "L", 5));
    }

    @SuppressWarnings("unchecked")
    @Test(groups = {"Smoke", "Regression"}, description = "ID: C441 , ADMIN_DL->FD", priority = 1)
    public void ADMIN_DL_FD() throws Exception {
        String packetId = omsServiceHelper.getPacketId(lmsHepler.createMockOrder(EnumSCM.DL, LMS_PINCODE.NORTH_CGH, "ML", "36", EnumSCM.NORMAL, "cod", false, true));
        Assert.assertTrue(lmsServiceHelper.validateOrderStatusInLMS(packetId, EnumSCM.DELIVERED, 5));
        Assert.assertEquals(lmsServiceHelper.forceMarkDLFD.apply(packetId), EnumSCM.SUCCESS);
        Assert.assertTrue(lmsServiceHelper.validateOrderStatusInLMS(packetId, EnumSCM.FAILED_DELIVERY, 2));
        Assert.assertTrue(lmsServiceHelper.validateOrderStatusInML(packetId, EnumSCM.RECEIVED_IN_DC, 2));
        Assert.assertTrue(omsServiceHelper.validatePacketStatusInOMS(packetId, EnumSCM.S, 3));
    }

    @Test(groups = {"Smoke", "Regression"}, description = "ID: C452 , ADMIN_RT->LOST : ERROR", priority = 1)
    public void ADMIN_RT_LOST_ERROR() throws Exception {
        String packetId = omsServiceHelper.getPacketId(lmsHepler.createMockOrder(EnumSCM.DL, LMS_PINCODE.NORTH_CGH, "ML", "36", EnumSCM.NORMAL, "cod", false, true));
        //long returnId = Long.parseLong(lmsServiceHelper.createReturn.apply(packetId, 560068).toString());
        Assert.assertEquals(lmsServiceHelper.markOrderLOSTINTRANSIT(packetId), EnumSCM.SUCCESS);
    }

    @Test(groups = {"Smoke", "Regression"}, description = "ID: C440 , LMS E2E flow from order placement to LOST on IS then Mark RTO", priority = 1)
    public void ADMIN_CANCELLED_IN_HUB_LOST() throws Exception {
        String packetId = omsServiceHelper.getPacketId(lmsHepler.createMockOrder(EnumSCM.CANCELLED_IN_HUB, LMS_PINCODE.NORTH_CGH, "ML", "36", EnumSCM.NORMAL, "cod", false, true));
        Assert.assertTrue(lmsServiceHelper.validateOrderStatusInLMS(packetId, EnumSCM.CANCELLED_IN_HUB, 3));
        Assert.assertEquals(lmsServiceHelper.markOrderLOSTINHUB(packetId), EnumSCM.SUCCESS);
        Assert.assertTrue(lmsServiceHelper.validateOrderStatusInLMS(packetId, EnumSCM.LOST_IN_HUB, 5));
        Assert.assertTrue(omsServiceHelper.validatePacketStatusInOMS(packetId, "L", 5));
    }

    @Test(groups = {"Smoke", "Regression"}, priority = 0, dataProviderClass = LMSTestsDP.class, dataProvider = "createOrderInLMSForwardML", description = "ID: C479 , Create order in lms forward "
            + " different skus and payment methods and pack that order. After that validate the LMS tables on order creation in lms after pack release event", enabled = true)
    public void createOrderInLMSForwardML(boolean isTod, String paymentMode) throws Exception {

        String orderID = lmsHepler.createMockOrder(EnumSCM.IS, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.NORMAL, paymentMode, isTod, true);
        lmsHepler.createOrderInLMSValidation(orderID, "" + isTod);
    }


    @Test(groups = {"Smoke", "Regression"}, priority = 0, dataProviderClass = LMSTestsDP.class, dataProvider = "createOrderInLMSForwardMLTODandXpress", description = "ID: C480 , Create order in lms with TOD and express"
            + "TOD and different shipping methods and pack that order. After that validate the LMS tables on order creation in lms after pack release event", enabled = true)
    public void createOrderInLMSForwardMLTODandXpress(boolean isTod, String shippingMethod) throws Exception {
        String orderID = lmsHepler.createMockOrder(EnumSCM.IS, LMS_PINCODE.ML_BLR, "ML", "36", shippingMethod, "cod", isTod, true);
        lmsHepler.createOrderInLMSValidation(orderID, "" + isTod);
    }





    @Deprecated
    @Test(groups = {"Smoke", "Regression"}, priority = 0, dataProviderClass = LMSTestsDP.class, dataProvider = "createOrderInLMSForwardWithCancellation", description = "ID: C , Create order in lms with cancellation"
            + " with at least 2 skus, Than cancel 1 sku and than pack the orther sku. After that validate the LMS tables on order creation in lms after pack release event", enabled = false)
    public void createOrderInLMSForwardWithCancellation(String lp, String cb, String sku, String isCheckCOD, String isTod, String shippingMethod) throws Exception {
        LMSHelper lmsHepler = new LMSHelper();
        End2EndHelper end2EndHelper = new End2EndHelper();
        LmsServiceHelper lmsServiceHelper = new LmsServiceHelper();
        OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
        Boolean cashBack = false, loyaltyPoint = false;
        if (!lp.equalsIgnoreCase("false") && !cb.equalsIgnoreCase("false")) {
            end2EndHelper.updateloyalityAndCashBack(LMS_LOGIN.LogIn29, Integer.parseInt(lp), Integer.parseInt(cb));
            cashBack = true;
            loyaltyPoint = true;
        } else if (!lp.equalsIgnoreCase("false") && cb.equalsIgnoreCase("false")) {
            end2EndHelper.updateloyalityAndCashBack(LMS_LOGIN.LogIn29, Integer.parseInt(lp), 0);
            cashBack = false;
            loyaltyPoint = true;
        } else if (lp.equalsIgnoreCase("false") && !cb.equalsIgnoreCase("false")) {
            end2EndHelper.updateloyalityAndCashBack(LMS_LOGIN.LogIn29, 0, Integer.parseInt(cb));
            cashBack = true;
            loyaltyPoint = false;
        }
        String[] skus = sku.split(",");
        String orderID = end2EndHelper.createOrder(LMS_LOGIN.LogIn29, LMS_LOGIN.PASSWORD, LMS_PINCODE.ML_BLR, skus, "", cashBack, loyaltyPoint, false, "", Boolean.parseBoolean(isTod));
        log.info(orderID);
        String packetId = omsServiceHelper.getPacketId(orderID);
        String releaseId = omsServiceHelper.getReleaseId(orderID);
        Assert.assertTrue(omsServiceHelper.validatePacketStatusInOMS(packetId, EnumSCM.PK, 15));
        Assert.assertTrue(omsServiceHelper.validateOrderOFFHoldStatusInOMS(orderID, 15));
        log.info("Order ID : " + orderID);
        if (!shippingMethod.equalsIgnoreCase(EnumSCM.NORMAL)) {//SDD, XPRESS, NORMAL
            DBUtilities.exUpdateQuery("update order_release set shipping_method = '" + shippingMethod + "' where id = " + packetId, "oms");
        }
        PacketEntry orderReleaseEntry = omsServiceHelper.getPacketEntry(packetId);
        Long cancellineID = orderReleaseEntry.getOrderLines().get(0).getId();
        omsServiceHelper.cancelLine(omsServiceHelper.getPacketId(orderID), LMS_LOGIN.LogIn29, new String[]{cancellineID + ":1"}, 41);
        
        List<ReleaseEntry> releaseEntries = new ArrayList<>();
        releaseEntries.add(new ReleaseEntry.Builder(releaseId, ReleaseStatus.PK).force(true).build());
        processRelease.processReleaseToStatus(processRelease.getReleaseEntryList(releaseEntries));
        String status = lmsHepler.getOrderReleaseStatus(packetId);
        Assert.assertEquals(status, EnumSCM.PK, "Status in oms is not PK");
        //Map<String, Object> tn = (HashMap<String, Object>) DBUtilities.exSelectQueryForSingleRecord("select tracking_no from order_release where id = " + packetId, "oms");
        //String trackingNumber = tn.get("tracking_no").toString();
        if (shippingMethod.equalsIgnoreCase("XPRESS")) {
            shippingMethod = "EXPRESS";	
        }

        Assert.assertEquals(lmsServiceHelper.orderInScanNew(packetId, "36"), EnumSCM.SUCCESS, "Unable to inscan");
        lmsHepler.createOrderInLMSValidation(orderID, isTod);
    }

    @Deprecated
    @Test(groups = {"Smoke", "Regression"}, priority = 0, dataProviderClass = LMSTestsDP.class, dataProvider = "createOrderInLMSReturns", description = "ID: C , Create order in lms return "
            + "Post that create return and validate the lms db for all the entries", enabled = false)
    public void createOrderInLMSReturns(String lp, String cb, String sku, String shipmentValue, String isCheckCOD, String codAmount, String isTod, String shippingMethod) throws Exception {
        LMSHelper lmsHepler = new LMSHelper();
        End2EndHelper end2EndHelper = new End2EndHelper();
        RMSServiceHelper rmsServiceHelper = new RMSServiceHelper();
        OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
        Boolean cashBack = false, loyaltyPoint = false;
        if (!lp.equalsIgnoreCase("false") && !cb.equalsIgnoreCase("false")) {
            end2EndHelper.updateloyalityAndCashBack(LMS_LOGIN.LogIn30, Integer.parseInt(lp), Integer.parseInt(cb));
            cashBack = true;
            loyaltyPoint = true;
        } else if (!lp.equalsIgnoreCase("false") && cb.equalsIgnoreCase("false")) {
            end2EndHelper.updateloyalityAndCashBack(LMS_LOGIN.LogIn30, Integer.parseInt(lp), 0);
            cashBack = false;
            loyaltyPoint = true;
        } else if (lp.equalsIgnoreCase("false") && !cb.equalsIgnoreCase("false")) {
            end2EndHelper.updateloyalityAndCashBack(LMS_LOGIN.LogIn30, 0, Integer.parseInt(cb));
            cashBack = true;
            loyaltyPoint = false;
        }
        String[] skus = sku.split(",");
        String orderID = end2EndHelper.createOrder(LMS_LOGIN.LogIn30, LMS_LOGIN.PASSWORD, LMS_PINCODE.ML_BLR, skus, "", cashBack, loyaltyPoint, false, "", Boolean.parseBoolean(isTod));
        log.info(orderID);
        String packetId = omsServiceHelper.getPacketId(orderID);
        String releaseId = omsServiceHelper.getReleaseId(orderID);
        Assert.assertTrue(omsServiceHelper.validatePacketStatusInOMS(packetId, EnumSCM.PK
                , 15));
        Assert.assertTrue(omsServiceHelper.validateOrderOFFHoldStatusInOMS(orderID, 15));
        log.info("Order ID : " + orderID);
        List<ReleaseEntry> releaseEntries = new ArrayList<>();
        releaseEntries.add(new ReleaseEntry.Builder(releaseId, ReleaseStatus.DL).force(true).build());
        processRelease.processReleaseToStatus(processRelease.getReleaseEntryList(releaseEntries));
        String status = lmsHepler.getOrderReleaseStatus(packetId);
        Assert.assertEquals(status, EnumSCM.DL, "Status in oms is not DL");
        PacketEntry orderReleaseEntry = omsServiceHelper.getPacketEntry(packetId);
        ReturnResponse returnResponse = rmsServiceHelper.createReturn(orderReleaseEntry.getOrderLines().get(0).getId().toString(), 1, ReturnMode.PICKUP, 27L, RefundMode.CASHBACK, "1234567890", "Myntra test lms automation", "Bangalore", "KA", LMS_PINCODE.ML_BLR, "ML");
        Assert.assertEquals(returnResponse.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Return creation failed");
        Long returnID = returnResponse.getData().get(0).getId();
        //  lmsServiceHelper.validatePickupStatusInLMS("" + returnID, "PICKUP_CREATED", 15);
        lmsHepler.createOrderReturnInLMSValidation("" + omsServiceHelper.getPacketId(orderID), returnID.toString(), LMS_PINCODE.ML_BLR);
    }

    @SuppressWarnings({ "deprecation", "unchecked" })
	@Test(groups = {"Smoke", "Regression"}, priority = 0, dataProviderClass = LMSTestsDP.class, dataProvider = "createOrderInLMSValidateRTORTWH", description = "ID: C481 , Create order in lms forward "
            + " and change the pincodes and check the return WH config for all", enabled = true)
    public void createOrderInLMSValidateRTORTWH(String zipcode, String warehouse_id) throws Exception {
        String orderID = lmsHepler.createMockOrder(EnumSCM.DL, zipcode, "ML", warehouse_id, EnumSCM.NORMAL, "cod", false, true);
        String packetId = omsServiceHelper.getPacketId(orderID);
        Assert.assertEquals(lmsHepler.getRtoWarehouseFromOrderToShip(packetId), lmsServiceHelper.getRtoAddress(zipcode, "ML", warehouse_id).getId().toString(), "RTO warehouse is not correct");
        
        //LMS_ReturnHelper lmsReturnHelper = new LMS_ReturnHelper();
        
        ReturnResponse returnResponse = rmsServiceHelper.createReturn(omsServiceHelper.getPacketEntry(packetId).getOrderLines().get(0).getId(), ReturnType.NORMAL, ReturnMode.OPEN_BOX_PICKUP, 1, 49L, RefundMode.NEFT, "418", "test Open box address ", "6135071", "560068", "Bangalore", "Karnataka", "India","8149881455");
        Assert.assertEquals(returnResponse.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Return creation failed");
        Long returnId = returnResponse.getData().get(0).getId();
        log.info("________ReturnId: " + returnId);
        //lmsReturnHelper.processOpenBoxReturn(returnId.toString(), EnumSCM.PICKED_UP_SUCCESSFULLY);
       //lmsServiceHelper.validateRmsLmsReturnCreation("" + returnId);
        String x = lmsHepler.getRtWarehouseFromReturn.apply(returnId).toString();
        String y = lmsServiceHelper.getReturnAddress(zipcode, "ML", Long.parseLong(warehouse_id)).getId().toString();
        Assert.assertEquals(x, y,"return warehouse is not correct");
        
       // Assert.assertEquals(lmsHepler.getRtWarehouseFromReturn.apply(returnId).toString(), lmsServiceHelper.getReturnAddress(zipcode, "ML", Long.parseLong(warehouse_id)).getId().toString(), "return warehouse is not correct");  
    }

    @Deprecated
    @Test(groups = {"Smoke", "Regression"}, priority = 0, dataProviderClass = LMSTestsDP.class, dataProvider = "createOrderInLMSwithEXValidateRTORTWH", description = "ID: C , Create order in lms with Exchange "
            + " and change the pincodes, check the return WH config for all", enabled = false)
    public void createOrderInLMSwithEXValidateRTORTWH(String[] sku, String exSku, String zipcode, String rtZipcode) throws Exception {
        LMSHelper lmsHepler = new LMSHelper();
        OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
        PPSServiceHelper ppsServiceHelper = new PPSServiceHelper();
        End2EndHelper end2EndHelper = new End2EndHelper();
        LmsServiceHelper lmsServiceHelper = new LmsServiceHelper();
        String orderId = end2EndHelper.createOrder(LMS_LOGIN.LogIn32, LMS_LOGIN.PASSWORD, LMS_PINCODE.ML_BLR, sku, "", false, false, false, "", false);
        String packetId = omsServiceHelper.getPacketId(orderId);
        String releaseId = omsServiceHelper.getReleaseId(orderId);
        List<ReleaseEntry> releaseEntries = new ArrayList<>();
        log.info(orderId);
        Assert.assertTrue(omsServiceHelper.validatePacketStatusInOMS(packetId, EnumSCM.PK, 15));
        Assert.assertTrue(omsServiceHelper.validateOrderOFFHoldStatusInOMS(orderId, 15));
        log.info("Order ID : " + orderId);
        DBUtilities.exUpdateQuery("update order_release set zipcode = " + zipcode + " where order_id_fk = " + orderId, "oms");
        releaseEntries.add(new ReleaseEntry.Builder(releaseId, ReleaseStatus.PK).force(true).build());
        processRelease.processReleaseToStatus(processRelease.getReleaseEntryList(releaseEntries));
        String status = lmsHepler.getOrderReleaseStatus(packetId);
        Assert.assertEquals(status, EnumSCM.PK, "Status in oms is not PK");
        Map<String, Object> orderRelease = (Map<String, Object>) DBUtilities.exSelectQueryForSingleRecord("select * from order_release where order_id_fk = " + orderId, "oms");
        String warehouse_id = orderRelease.get("warehouse_id").toString();
        ReturnAddress raResponse = lmsServiceHelper.getRtoAddress(zipcode, "ML", warehouse_id);
        Assert.assertEquals(raResponse.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to retrieve return address and warehouse for that pincode and WH");
        String rto_warehouse_id = lmsHepler.getRtoWarehouseFromOrderToShip(packetId);
        Assert.assertEquals(rto_warehouse_id, raResponse.getId().toString(), "RTO warehouse is not correct");
        releaseEntries.add(new ReleaseEntry.Builder(releaseId, ReleaseStatus.DL).force(true).build());
        processRelease.processReleaseToStatus(processRelease.getReleaseEntryList(releaseEntries));
        String statusDL = lmsHepler.getOrderReleaseStatus(packetId);
        Assert.assertEquals(statusDL, EnumSCM.DL, "Status in oms is not DL");
        PacketEntry orderReleaseEntry = omsServiceHelper.getPacketEntry(packetId);
        Long lineID = orderReleaseEntry.getOrderLines().get(0).getId();
        ExchangeOrderResponse exchangeOrderResponse = (ExchangeOrderResponse) ppsServiceHelper.createExchange(orderId, "" + lineID, "DNL", 1, exSku);
        Assert.assertNotNull(exchangeOrderResponse.getSuccess(), "Unable to create exchange");
        String exOrderId = omsServiceHelper.getOrderEntry(exchangeOrderResponse.getExchangeOrderId()).getId().toString();
        if (rtZipcode != null) {
            DBUtilities.exUpdateQuery("update order_release set zipcode = " + rtZipcode + " where order_id_fk = " + exOrderId, "oms");
        }
        releaseEntries.add(new ReleaseEntry.Builder(releaseId, ReleaseStatus.SH).force(true).build());
        processRelease.processReleaseToStatus(processRelease.getReleaseEntryList(releaseEntries));
        Map<String, Object> returnShipment = DBUtilities.exSelectQueryForSingleRecord("select return_warehouse_id from return_shipment where order_id = " + omsServiceHelper.getPacketId(orderId), "lms");
        String rtWarehouseId = returnShipment.get("return_warehouse_id").toString();
        ReturnAddress rtResponse = new ReturnAddress();
        if (rtZipcode == null) {
            rtResponse = lmsServiceHelper.getReturnAddress(zipcode, "ML", Long.parseLong(warehouse_id));
        } else {
            rtResponse = lmsServiceHelper.getReturnAddress(rtZipcode, "ML", Long.parseLong(warehouse_id));
        }
        Assert.assertEquals(rtWarehouseId, rtResponse.getId().toString(), "return warehouse is not correct for the pickup of return");
        Map<String, Object> orderReleaseEx = (Map<String, Object>) DBUtilities.exSelectQueryForSingleRecord("select * from order_release where order_id_fk = " + omsServiceHelper.getPacketId(exOrderId), "oms");
        String Exwarehouse_id = orderReleaseEx.get("warehouse_id").toString();
        ReturnAddress exrtoResponse = lmsServiceHelper.getRtoAddress(zipcode, "ML", Exwarehouse_id);
        Assert.assertEquals(exrtoResponse.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to retrieve return address and warehouse for that pincode and WH or WH incorrect ");
    }


    @Test(groups = {"P0","Smoke", "Regression", "P0"}, priority = 0, description = "ID: C477 , validation of all tracking details: Hub activity details, order tracking, ml shipment tracking, masterbag tracking", enabled = true)
    public void ML_ALLTracking_Validation() throws Exception {
        String orderID = lmsHepler.createMockOrder(EnumSCM.DL, LMS_PINCODE.NORTH_CGH, "ML", "36", EnumSCM.NORMAL, "cod", false, true);
        String packetId = omsServiceHelper.getPacketId(orderID);
        lmsHepler.hubActivityDetailValidation(packetId);
        lmsHepler.orderTrackingDetailValidation(packetId);
        lmsHepler.mlShipmentTrackingDetailValidation(packetId);
        lmsHepler.tmsMasterBagTrackingValidation(packetId);
    }

    @SuppressWarnings("unchecked")
    @Test(groups = {"Smoke", "Regression", "P0"}, priority = 0, description = "ID: C478 , Payments modes & instruments used: MYNTS, CC, LP, GC, WALLET", enabled = true)
    public void createMockOrderWithMultiPaymantMode() throws Exception {
    	
        String orderId = lmsHepler.createMockOrderWithMultiPaymentMode(EnumSCM.PK, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.NORMAL, false);
        String packetId = omsServiceHelper.getPacketId(orderId);
        String releaseId = omsServiceHelper.getReleaseId(orderId);
        
        List<ReleaseEntry> releaseEntries = new ArrayList<>();
        releaseEntries.add(new ReleaseEntry.Builder(releaseId, ReleaseStatus.DL).force(true).build());
        processRelease.processReleaseToStatus(processRelease.getReleaseEntryList(releaseEntries));
        lmsHepler.createOrderValidationsForPayments.accept(packetId);
    }

    @Test
    public void markOrderDeliveredLMS() throws Exception {

        lmsHepler.createMockOrder(EnumSCM.DL, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.NORMAL, "cod", false, true);
    }

    @SuppressWarnings("unchecked")
    @Test(groups = {"P0","Smoke", "Regression", "P0"}, priority = 0, description = "ID: C475 , ", enabled = true)
    public void GOR_E2E() throws Exception {
    	
    		String orderId = lmsHepler.createMockOrder(EnumSCM.PK, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.NORMAL, "cod", false, true);
        String packetId = omsServiceHelper.getPacketId(orderId);
        String releaseId = omsServiceHelper.getReleaseId(orderId);
        Assert.assertEquals(lmsServiceHelper.orderInScanGOR(packetId, "36").getStatus().getStatusType().toString(), EnumSCM.SUCCESS);
        String hubCode = lmsServiceHelper.getHubConfig("36", "DL");
        String consolidationBagId = "" + lmsServiceHelper.generateBagLabelForHub(hubCode);
        Assert.assertEquals(lmsServiceHelper.closeConsolidationBag(packetId, "ML", EnumSCM.NORMAL, "ELC", consolidationBagId).getStatus().getStatusType().toString(), EnumSCM.SUCCESS);
        long masterBagId = Long.parseLong(lmsServiceHelper.getMasterBagIdForConsolidationBag.apply(consolidationBagId).toString());
        tmsServiceHelper.processInTMSFromClosedToLastMileInTransit.accept(masterBagId);
        Assert.assertEquals(lmsServiceHelper.receiveShipmentFromMasterbag(masterBagId).
                getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to receive shipment in DC");
        Assert.assertEquals(lmsServiceHelper.masterBagInScanUpdate(masterBagId, packetId, "Bangalore", 5, "DC", 36)
                .getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to receive master bag in DC");
        Assert.assertEquals(lmsHepler.getMasterBagStatus(masterBagId), EnumSCM.RECEIVED, "Masterbag status is not update in DB to `RECEIVED`");
        Assert.assertEquals(lmsHepler.getMLShipmentStatus(packetId), EnumSCM.UNASSIGNED, "Shipment Staus is not update to UNASSIGENED in ML shipment2");
        
        List<ReleaseEntry> releaseEntries = new ArrayList<>();
        releaseEntries.add(new ReleaseEntry.Builder(releaseId, ReleaseStatus.DL).force(true).build());
        processRelease.processReleaseToStatus(processRelease.getReleaseEntryList(releaseEntries));
    }

    @SuppressWarnings("unchecked")
    @Test(groups = {"P0","Smoke", "Regression", "P0"}, priority = 0, description = "ID: C476 , ", enabled = true)
    public void GOR_E2E_MultiOrder() throws Exception {
    	
    		String orderId = lmsHepler.createMockOrder(EnumSCM.PK, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.NORMAL, "cod", false, true);
        String packetId = omsServiceHelper.getPacketId(orderId);
        String releaseId = omsServiceHelper.getReleaseId(orderId);
        String packetId1 = omsServiceHelper.getPacketId(lmsHepler.createMockOrder(EnumSCM.PK, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.NORMAL, "cod", false, true));
        String packetId2 = omsServiceHelper.getPacketId(lmsHepler.createMockOrder(EnumSCM.PK, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.NORMAL, "cod", false, true));
        Assert.assertEquals(lmsServiceHelper.orderInScanGOR(packetId, "36").getStatus().getStatusType().toString(), EnumSCM.SUCCESS);
        Assert.assertEquals(lmsServiceHelper.orderInScanGOR(packetId1, "36").getStatus().getStatusType().toString(), EnumSCM.SUCCESS);
        Assert.assertEquals(lmsServiceHelper.orderInScanGOR(packetId2, "36").getStatus().getStatusType().toString(), EnumSCM.SUCCESS);
        List<String> orders = new ArrayList<>();
        orders.add(packetId);
        orders.add(packetId1);
        orders.add(packetId2);
        String hubCode = lmsServiceHelper.getHubConfig("36", "DL");
        String consolidationBagId = "" + lmsServiceHelper.generateBagLabelForHub(hubCode);
        Assert.assertEquals(lmsServiceHelper.closeConsolidationBag(orders, "ML", EnumSCM.NORMAL, "ELC", consolidationBagId).getStatus().getStatusType().toString(), EnumSCM.SUCCESS);
        Thread.sleep(8000);
        long masterBagId = Long.parseLong(lmsServiceHelper.getMasterBagIdForConsolidationBag.apply(consolidationBagId).toString());
        tmsServiceHelper.processInTMSFromClosedToLastMileInTransit.accept(masterBagId);
        Assert.assertEquals(lmsServiceHelper.receiveShipmentFromMasterbag(masterBagId).
                getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to receive shipment in DC");
        Assert.assertEquals(lmsServiceHelper.masterBagInScanUpdate(masterBagId, new String[]{packetId + ":RECEIVED", packetId1 + ":RECEIVED", packetId2 + ":RECEIVED"},
                5, "DC").getStatus().getStatusType().toString(), EnumSCM.SUCCESS);
        Assert.assertEquals(lmsHepler.getMasterBagStatus(masterBagId), EnumSCM.RECEIVED, "Masterbag status is not update in DB to `RECEIVED`");
        Assert.assertEquals(lmsHepler.getMLShipmentStatus(packetId), EnumSCM.UNASSIGNED, "Shipment Staus is not update to UNASSIGENED in ML shipment2");
        Assert.assertEquals(lmsHepler.getMLShipmentStatus(packetId1), EnumSCM.UNASSIGNED, "Shipment Staus is not update to UNASSIGENED in ML shipment2");
        Assert.assertEquals(lmsHepler.getMLShipmentStatus(packetId2), EnumSCM.UNASSIGNED, "Shipment Staus is not update to UNASSIGENED in ML shipment2");

        List<ReleaseEntry> releaseEntries = new ArrayList<>();
        releaseEntries.add(new ReleaseEntry.Builder(releaseId, ReleaseStatus.DL).force(true).build());
        processRelease.processReleaseToStatus(processRelease.getReleaseEntryList(releaseEntries));
    }

    //Need to fix
    @Test(groups = {"Smoke", "Regression", "P0"}, priority = 0, description = "ID: C782, place flipkart marketPlace(SilkRoute) order and " +
            "process the same in LMS till SH (We only process this till SH only in prod as well) ", enabled = true)
    public void flipkartMarketPlaceOrderTillSH() throws Exception {
        Random random = new Random();
        String orderId = String.valueOf(System.currentTimeMillis() / 1000 + random.nextInt(100));
        String orderResponse = silkRouteServiceHelper.invokeSilkRouteServiceCreateOrderAPI(SilkRouteConstants.FK3, orderId, "order_item_created",
                "APPROVED", "false", SilkRouteConstants.DISPATCH_DATE, SilkRouteConstants.DISPATCH_DATE, "1", "0",
                SilkRouteConstants.FK_LISTING_ID, SilkRouteConstants.SKU_CODE_NIKE_CREATEORDER, "200", "560068").getResponseBody();
        String packetId = omsServiceHelper.getReleaseIdFromStoreOrderId(APIUtilities.getElement(orderResponse, "orderItemId", "json"));
        DBUtilities.exUpdateQuery("update order_line set status_code = 'QD' where order_release_id_fk = '"+packetId+"'","oms");
        DBUtilities.exUpdateQuery("update order_release set status_code = 'PK' where id = '"+packetId+"'","oms");
        omsServiceHelper.pushReleaseToLms(packetId);
        lmsServiceHelper.validateOrderStatusInLMS(packetId, EnumSCM.PACKED,10);
        Assert.assertEquals(lmsServiceHelper.orderInScanNew(packetId), EnumSCM.SUCCESS, "Unable to inscan order in the dispatch hub");
        Assert.assertTrue(lmsServiceHelper.validateOrderStatusInLMS(packetId, EnumSCM.INSCANNED, 3), "Order status is not INSCANNED");
        long deliveryCenterId = (long)DBUtilities.exSelectQueryForSingleRecord("select id from delivery_center where code = 'EKMP'","lms").get("id");
        long masterbagId = lmsServiceHelper.createMasterBag(deliveryCenterId, "36", ShippingMethod.NORMAL).getEntries().get(0).getId();
        Assert.assertEquals(lmsServiceHelper.addAndSaveMasterBag(packetId, ""+masterbagId, ShipmentType.DL), EnumSCM.SUCCESS,
                "Unable to add order to masterBag");
        Assert.assertTrue(lmsServiceHelper.validateOrderStatusInLMS(packetId, EnumSCM.ADDED_TO_MB, 3), "Order is not added to masterbag");
        Assert.assertEquals(lmsServiceHelper.closeMasterBag(masterbagId).getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to close masterbag");
        Assert.assertEquals(lmsServiceHelper.shipMasterBag(masterbagId).getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to ship masterbag");
        Assert.assertTrue(lmsServiceHelper.validateOrderStatusInLMS(packetId, EnumSCM.SHIPPED, 3), "Status in LMS is not SHIPPED");
        Assert.assertTrue(omsServiceHelper.validatePacketStatusInOMS(packetId, EnumSCM.S, 3), "Status in OMS is not SH");
    }

    @Test(groups = {"Smoke", "Regression"}, description = "", priority = 1)
    public void testRulesEngine() throws Exception {

        String releaseId = omsServiceHelper.getReleaseId(lmsHepler.createMockOrder(EnumSCM.PK, LMS_PINCODE.PUNE_EK, CourierCode.EK.toString(),
                "36", EnumSCM.NORMAL, "cod", false, true));
        
        List<ReleaseEntry> releaseEntries = new ArrayList<>();
        releaseEntries.add(new ReleaseEntry.Builder(releaseId, ReleaseStatus.DL).force(true).build());
        processRelease.processReleaseToStatus(processRelease.getReleaseEntryList(releaseEntries));

    }

    @SuppressWarnings("unchecked")
    @Test(groups = {"Smoke", "Regression"}, description = "ID: 957, New admin status correction ", priority = 1)
    public void NEW_ADMIN_DL_RTO() throws Exception {
        String packetId = omsServiceHelper.getPacketId(lmsHepler.createMockOrder(EnumSCM.DL, LMS_PINCODE.NORTH_CGH, "ML",
                "36", EnumSCM.NORMAL, "cod", false, true));
        Assert.assertTrue(lmsServiceHelper.validateOrderStatusInLMS(packetId, EnumSCM.DELIVERED, 5));
        OrderEntry response = ((OrderResponse)lmsServiceHelper.getOrderLMS.apply(packetId)).getOrders().get(0);
        Assert.assertEquals(APIUtilities.getElement(lmsServiceHelper.newAdminUpdateShipmentStatus(packetId,response.getTrackingNumber(),
                EnumSCM.RTO_CONFIRMED,response.getShipmentType(), ShipmentUpdateActivityTypeSource.LogisticsAdmin), "PlatformMlSharedResponse.status.statusType","json"), EnumSCM.SUCCESS);
        Assert.assertTrue(lmsServiceHelper.validateOrderStatusInLMS(packetId, EnumSCM.RTO_CONFIRMED, 5));
        Assert.assertTrue(lmsServiceHelper.validateOrderStatusInML(packetId, EnumSCM.RTO_CONFIRMED, 5));
        Assert.assertTrue(omsServiceHelper.validatePacketStatusInOMS(packetId, EnumSCM.RTO, 3));
    }

    @SuppressWarnings("unchecked")
    @Test(groups = {"Smoke", "Regression"}, dataProviderClass = LMSTestsDP.class, dataProvider = "newAdminStatusCorrection",
            description = "ID: 958, New admin status correction for mltiple different scenarios", priority = 1)
    public void NEW_ADMIN_STATUS_CORRECTION(String status, String pincode, String courierCode, String warehouseId, String shippingMethod, String correctionStatus,
                                            String validateLMSStatus, String validateOMSStatus, ShipmentUpdateActivityTypeSource updateSource) throws Exception {
        String packetId = omsServiceHelper.getPacketId(lmsHepler.createMockOrder(status, pincode, courierCode, warehouseId, shippingMethod, "cod", false, true));
        OrderEntry response = ((OrderResponse)lmsServiceHelper.getOrderLMS.apply(packetId)).getOrders().get(0);
        Assert.assertEquals(APIUtilities.getElement(lmsServiceHelper.newAdminUpdateShipmentStatus(packetId,response.getTrackingNumber(),
                correctionStatus,response.getShipmentType(), updateSource), "PlatformMlSharedResponse.status.statusType","json"), EnumSCM.SUCCESS);
        Assert.assertTrue(lmsServiceHelper.validateOrderStatusInLMS(packetId, validateLMSStatus, 5));
        Assert.assertTrue(omsServiceHelper.validatePacketStatusInOMS(packetId, validateOMSStatus, 3));
    }

    @SuppressWarnings("unchecked")
    @Test(groups = {"Smoke", "Regression"}, dataProviderClass = LMSTestsDP.class, dataProvider = "newAdminStatusCorrectionStatusLostRto",
            description = "ID: 958, New admin status correction From a specific status to LOST then RTO for multiple different Couriers and shippingMethod", priority = 1)
    public void NEW_ADMIN_STATUS_CORRECTION_STATUS_LOST_RTO(String status, String pincode, String courierCode, String warehouseId, String shippingMethod, ShipmentUpdateActivityTypeSource updateSource) throws Exception {
        String packetId = omsServiceHelper.getPacketId(lmsHepler.createMockOrder(status, pincode, courierCode, warehouseId, shippingMethod, "cod", false, true));
        OrderEntry response = ((OrderResponse)lmsServiceHelper.getOrderLMS.apply(packetId)).getOrders().get(0);

        if(status.equals("IS")) {

            Assert.assertEquals(lmsServiceHelper.markOrderLOSTORDERSEIZED(packetId), EnumSCM.SUCCESS, "");
            Assert.assertTrue(lmsServiceHelper.validateOrderStatusInLMS(packetId, EnumSCM.LOST_IN_HUB, 5));
        } else {

            Assert.assertEquals(APIUtilities.getElement(lmsServiceHelper.newAdminUpdateShipmentStatus(packetId,response.getTrackingNumber(),
                    EnumSCM.LOST, response.getShipmentType(), updateSource), "PlatformMlSharedResponse.status.statusType","json"), EnumSCM.SUCCESS);
            Assert.assertTrue(lmsServiceHelper.validateOrderStatusInLMS(packetId, EnumSCM.LOST, 5));
        }

        Assert.assertTrue(omsServiceHelper.validatePacketStatusInOMS(packetId, EnumSCM.L, 3));
        Assert.assertEquals(APIUtilities.getElement(lmsServiceHelper.newAdminUpdateShipmentStatus(packetId,response.getTrackingNumber(),
                EnumSCM.RTO_CONFIRMED, response.getShipmentType(), updateSource), "PlatformMlSharedResponse.status.statusType","json"), EnumSCM.SUCCESS);
        Assert.assertTrue(lmsServiceHelper.validateOrderStatusInLMS(packetId, EnumSCM.RTO_CONFIRMED, 5));
        Assert.assertTrue(omsServiceHelper.validatePacketStatusInOMS(packetId, EnumSCM.RTO, 3));
    }

    @Test
    public void markOrderDeliveredLMSJabong() throws Exception {

        lmsHepler.createMockOrder(EnumSCM.PK, LMS_PINCODE.ML_BLR, "JB", "36", EnumSCM.NORMAL, "cod", false, true);
    }

}
