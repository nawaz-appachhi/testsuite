package com.myntra.apiTests.erpservices.lms.tests;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.myntra.apiTests.common.ExceptionHandler;
import com.myntra.apiTests.common.Constants.EnumSCM;
import com.myntra.apiTests.common.Constants.ReleaseStatus;
import com.myntra.apiTests.common.ProcessOrder.Service.ProcessRelease;
import com.myntra.apiTests.common.entries.ReleaseEntry;
import com.myntra.apiTests.common.entries.TryNBuyEntry;
import com.myntra.apiTests.end2end.End2EndHelper;
import com.myntra.apiTests.erpservices.lms.Constants.LMS_LOGIN;
import com.myntra.apiTests.erpservices.lms.Constants.LMS_PINCODE;
import com.myntra.apiTests.erpservices.lms.Constants.LMS_SKU;
import com.myntra.apiTests.erpservices.lms.Helper.LMSHelper;
import com.myntra.apiTests.erpservices.lms.Helper.LmsServiceHelper;
import com.myntra.apiTests.erpservices.oms.OMSServiceHelper;
import com.myntra.apiTests.erpservices.oms.OMSHelpersEnums.ReadyToDispatchType;
import com.myntra.apiTests.erpservices.rms.RMSServiceHelper;
import com.myntra.apiTests.portalservices.pps.PPSServiceHelper;
import com.myntra.lordoftherings.boromir.DBUtilities;
import com.myntra.oms.client.entry.PacketEntry;
import com.myntra.paymentplan.domain.response.ExchangeOrderResponse;
import com.myntra.returns.common.enums.RefundMode;
import com.myntra.returns.common.enums.ReturnMode;
import com.myntra.returns.response.ReturnResponse;
import com.myntra.test.commons.testbase.BaseTest;

/**
 * Created by Shubham Gupta on 12/16/16.
 */
public class LMS_TOD extends BaseTest {
	
	private static org.slf4j.Logger log = LoggerFactory.getLogger(LMS_TOD.class);
	private OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
	private End2EndHelper end2EndHelper = new End2EndHelper();
	private LMSHelper lmsHepler = new LMSHelper();
	private LmsServiceHelper lmsServiceHelper = new LmsServiceHelper();
	private RMSServiceHelper rmsServiceHelper = new RMSServiceHelper();
	private PPSServiceHelper ppsServiceHelper = new PPSServiceHelper();
	private ProcessRelease processRelease = new ProcessRelease();
	private static long rms_wait = 8000L;

    @Test(groups = {"TOD", "P0", "Smoke","Regression"}, description = "ID: C97, TryOnDelivery flow with single sku delivered, Bought", priority = 1)
    public void E2E_LMS_TOD_01_B() throws Exception{
        String login = LMS_LOGIN.LogIn34;
        String orderId = end2EndHelper.createOrder(login, LMS_LOGIN.PASSWORD, LMS_PINCODE.ML_BLR, new String[] {LMS_SKU.S1541_36_21_0+":1"}, "", false, false, false,"", true);
        log.info("Order ID : " + orderId);
        String releaseId = omsServiceHelper.getReleaseId(orderId);
		omsServiceHelper.markReadyToDispatchV3ForMyntraSeller(releaseId,ReadyToDispatchType.POSITIVE.name());
		String packetId = omsServiceHelper.getPacketId(orderId);
		ExceptionHandler.handleEquals(omsServiceHelper.pushPacketToLms(packetId).getStatus().getStatusType().toString(),EnumSCM.SUCCESS);
        Assert.assertTrue(omsServiceHelper.validatePacketStatusInOMS(packetId, EnumSCM.PK, 15), "Unable to validate order status 'WP' in OMS");
        Assert.assertTrue(omsServiceHelper.validateOrderOFFHoldStatusInOMS(orderId, 15), "Unable to validate order status 'WP' in OMS");
        List<TryNBuyEntry> tryNBuyEntries = new ArrayList<>();
        tryNBuyEntries.add(new TryNBuyEntry(Long.parseLong(LMS_SKU.S1541_36_21_0),EnumSCM.TRIED_AND_BOUGHT));
        
        List<ReleaseEntry> releaseEntries = new ArrayList<>();
        releaseEntries.add(new ReleaseEntry.Builder(releaseId, ReleaseStatus.DL).tryNBuyEntries(tryNBuyEntries).build());
        processRelease.processReleaseToStatus(processRelease.getReleaseEntryList(releaseEntries));
        
        Map<String, String> itemIdAndStatus = lmsServiceHelper.getTODItemAndStatusMap(packetId, tryNBuyEntries);
        String trackingNumber = lmsHepler.getTrackingNumber(omsServiceHelper.getPacketId(orderId));
        lmsHepler.todValidation(packetId, trackingNumber, EnumSCM.NORMAL, itemIdAndStatus);
    }

    @Test(groups = {"TOD",  "Smoke","Regression"}, description = "ID: C98, TryOnDelivery flow with single sku delivered, Not Bought", priority = 1)
    public void E2E_LMS_TOD_01_NB() throws Exception{
        String login = LMS_LOGIN.LogIn35;
        String orderID = end2EndHelper.createOrder(login, LMS_LOGIN.PASSWORD, LMS_PINCODE.ML_BLR, new String[]{LMS_SKU.S1541_36_21_0+":1"}, "", false, false, false, "", true);
        log.info("Order ID : " + orderID);
        String packetId = omsServiceHelper.getPacketId(orderID);
        String releaseId = omsServiceHelper.getReleaseId(orderID);
        Assert.assertTrue(omsServiceHelper.validatePacketStatusInOMS(packetId, EnumSCM.WP, 15), "Unable to validate order status 'WP' in OMS");
        Assert.assertTrue(omsServiceHelper.validateOrderOFFHoldStatusInOMS(orderID, 15), "Unable to validate order status 'WP' in OMS");
        List<TryNBuyEntry> tryNBuyEntries = new ArrayList<>();
        tryNBuyEntries.add(new TryNBuyEntry(Long.parseLong(LMS_SKU.S1541_36_21_0),EnumSCM.TRIED_AND_NOT_BOUGHT));
        
        List<ReleaseEntry> releaseEntries = new ArrayList<>();
        releaseEntries.add(new ReleaseEntry.Builder(releaseId, ReleaseStatus.DL).tryNBuyEntries(tryNBuyEntries).build());
        processRelease.processReleaseToStatus(processRelease.getReleaseEntryList(releaseEntries));

        Map<String, String> itemIdAndStatus = lmsServiceHelper.getTODItemAndStatusMap(packetId, tryNBuyEntries);
        String trackingNumber = lmsHepler.getTrackingNumber(omsServiceHelper.getPacketId(orderID));
        lmsHepler.todValidation(packetId, trackingNumber, EnumSCM.NORMAL, itemIdAndStatus);
        String returnId = lmsHepler.getReturnIdFromOrderId(""+omsServiceHelper.getPacketId(orderID));
        lmsServiceHelper.validateRmsStatusAndRefund(returnId,EnumSCM.RPU,true, rms_wait);
        lmsServiceHelper.transferShipmentBackToWH(returnId,36,5,"DC","WH");
    }

    @Test(groups = {"TOD",  "Smoke","Regression"}, description = "ID: C99, TryOnDelivery flow with three sku LOST", priority = 1)
    public void E2E_LMS_TOD_03_LOST() throws Exception{
        String orderID = lmsHepler.createMockOrder(EnumSCM.SH, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.NORMAL,"cod",true, true);
        String releaseId = omsServiceHelper.getReleaseId(orderID);
        List<TryNBuyEntry> tryNBuyEntries = new ArrayList<>();
        tryNBuyEntries.add(new TryNBuyEntry(Long.parseLong(LMS_SKU.S1543_36_19_2),EnumSCM.TRIED_AND_BOUGHT+","+EnumSCM.TRIED_AND_BOUGHT));
        tryNBuyEntries.add(new TryNBuyEntry(Long.parseLong(LMS_SKU.S1542_36_25_4),EnumSCM.TRIED_AND_BOUGHT));
        
        List<ReleaseEntry> releaseEntries = new ArrayList<>();
        releaseEntries.add(new ReleaseEntry.Builder(releaseId, ReleaseStatus.LOST).force(true).tryNBuyEntries(tryNBuyEntries).build());
        processRelease.processReleaseToStatus(processRelease.getReleaseEntryList(releaseEntries));
     }

    @Test(groups = {"TOD",  "Smoke","Regression"}, description = "ID: C100, TryOnDelivery flow with three sku RTO", priority = 1)
    public void E2E_LMS_TOD_03_RTO() throws Exception{
        String orderID = lmsHepler.createMockOrder(EnumSCM.SH, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.NORMAL,"cod",true, true);
        log.info("Order ID : " + orderID);
        String packetId = omsServiceHelper.getPacketId(orderID);
        String releaseId = omsServiceHelper.getReleaseId(orderID);
        List<TryNBuyEntry> tryNBuyEntries = new ArrayList<>();
        tryNBuyEntries.add(new TryNBuyEntry(Long.parseLong(LMS_SKU.S1543_36_19_2),EnumSCM.TRIED_AND_BOUGHT+","+EnumSCM.TRIED_AND_BOUGHT));
        tryNBuyEntries.add(new TryNBuyEntry(Long.parseLong(LMS_SKU.S1542_36_25_4),EnumSCM.TRIED_AND_BOUGHT));
        
        List<ReleaseEntry> releaseEntries = new ArrayList<>();
        releaseEntries.add(new ReleaseEntry.Builder(releaseId, ReleaseStatus.RTO).force(true).tryNBuyEntries(tryNBuyEntries).build());
        processRelease.processReleaseToStatus(processRelease.getReleaseEntryList(releaseEntries));
        lmsServiceHelper.transferShipmentBackToWHRTO(packetId);
    }

    @Test(groups = {"TOD",  "Smoke","Regression"}, description = "ID: C101, 3 SKU With EXPRESS, Bought, Bought, Not Bought", priority = 1)
    public void E2E_LMS_TOD_03_EXPRESS_B_B_NB() throws Exception{
        String orderID = lmsHepler.createMockOrder(EnumSCM.SH, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.XPRESS,"cod",true, true);
        log.info("Order ID : " + orderID);
        String packetId = omsServiceHelper.getPacketId(orderID);
        String releaseId = omsServiceHelper.getReleaseId(orderID);
        List<TryNBuyEntry> tryNBuyEntries = new ArrayList<>();
        tryNBuyEntries.add(new TryNBuyEntry(Long.parseLong(LMS_SKU.S1543_36_19_2),EnumSCM.TRIED_AND_BOUGHT+","+EnumSCM.TRIED_AND_BOUGHT));
        tryNBuyEntries.add(new TryNBuyEntry(Long.parseLong(LMS_SKU.S1542_36_25_4),EnumSCM.TRIED_AND_NOT_BOUGHT));
        
        List<ReleaseEntry> releaseEntries = new ArrayList<>();
        releaseEntries.add(new ReleaseEntry.Builder(releaseId, ReleaseStatus.DL).force(true).tryNBuyEntries(tryNBuyEntries).build());
        processRelease.processReleaseToStatus(processRelease.getReleaseEntryList(releaseEntries));

        Map<String, String> itemIdAndStatus = lmsServiceHelper.getTODItemAndStatusMap(packetId, tryNBuyEntries);
        String trackingNumber = lmsHepler.getTrackingNumber(packetId);
        lmsHepler.todValidation(packetId, trackingNumber, EnumSCM.EXPRESS, itemIdAndStatus);
    }

    @Test(groups = {"TOD",  "Smoke","Regression"}, description = "ID: C102, SDD all Bought", priority = 1)
    public void E2E_LMS_TOD_03_SDD_B_B_B() throws Exception{
        String orderID = lmsHepler.createMockOrder(EnumSCM.SH, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.SDD,"cod",true, true);
        log.info("Order ID : " + orderID);
        String packetId = omsServiceHelper.getPacketId(orderID);
        String releaseId = omsServiceHelper.getReleaseId(orderID);
        List<TryNBuyEntry> tryNBuyEntries = new ArrayList<>();
        tryNBuyEntries.add(new TryNBuyEntry(Long.parseLong(LMS_SKU.S1543_36_19_2),EnumSCM.TRIED_AND_BOUGHT+","+EnumSCM.TRIED_AND_BOUGHT));
        tryNBuyEntries.add(new TryNBuyEntry(Long.parseLong(LMS_SKU.S1542_36_25_4),EnumSCM.TRIED_AND_NOT_BOUGHT));
        
        List<ReleaseEntry> releaseEntries = new ArrayList<>();
        releaseEntries.add(new ReleaseEntry.Builder(releaseId, ReleaseStatus.DL).force(true).tryNBuyEntries(tryNBuyEntries).build());
        processRelease.processReleaseToStatus(processRelease.getReleaseEntryList(releaseEntries));       
        
        Map<String, String> itemIdAndStatus = lmsServiceHelper.getTODItemAndStatusMap(packetId, tryNBuyEntries);
        String trackingNumber = lmsHepler.getTrackingNumber(packetId);
        lmsHepler.todValidation(packetId, trackingNumber, EnumSCM.SDD, itemIdAndStatus);
    }

    @Test(groups = {"TOD",  "Smoke","Regression"}, description = "ID: C121, TryOnDelivery flow with single sku With shipping, Bought", priority = 1)
    public void E2E_LMS_TOD_01_SHIPPING_B() throws Exception{
        String login = LMS_LOGIN.LogIn40;
        String orderID = end2EndHelper.createOrder(login, LMS_LOGIN.PASSWORD, LMS_PINCODE.ML_BLR, new String[] {LMS_SKU.S1541_36_21_0+":1"}, "", false, false, false,"", true);
        String packetId = omsServiceHelper.getPacketId(orderID);
        String releaseId = omsServiceHelper.getReleaseId(orderID);
        Assert.assertTrue(omsServiceHelper.validatePacketStatusInOMS(packetId, EnumSCM.WP, 15), "Unable to validate order status 'WP' in OMS");
        Assert.assertTrue(omsServiceHelper.validateOrderOFFHoldStatusInOMS(orderID, 15), "Unable to validate order status 'WP' in OMS");
        List<TryNBuyEntry> tryNBuyEntries = new ArrayList<>();
        tryNBuyEntries.add(new TryNBuyEntry(Long.parseLong(LMS_SKU.S1541_36_21_0),EnumSCM.TRIED_AND_BOUGHT));
         
        List<ReleaseEntry> releaseEntries = new ArrayList<>();
        releaseEntries.add(new ReleaseEntry.Builder(releaseId, ReleaseStatus.DL).force(true).tryNBuyEntries(tryNBuyEntries).build());
        processRelease.processReleaseToStatus(processRelease.getReleaseEntryList(releaseEntries));   
        
        Map<String, String> itemIdAndStatus = lmsServiceHelper.getTODItemAndStatusMap(packetId, tryNBuyEntries);
        String trackingNumber = lmsHepler.getTrackingNumber(packetId);
        lmsHepler.todValidation(packetId, trackingNumber, EnumSCM.NORMAL, itemIdAndStatus);
    }

    @Test(groups = {"TOD",  "Smoke","Regression"}, description = "ID: C104, 3Sku ,Wallet,{Bought,Bought, Bought}", priority = 1)
    public void E2E_LMS_TOD_02_03_B_B_B_WALLET() throws Exception{
        String orderID = lmsHepler.createMockOrder(EnumSCM.SH, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.NORMAL,"WALLET",true, true);
        log.info("Order ID : " + orderID);
        String packetId = omsServiceHelper.getPacketId(orderID);
        String releaseId = omsServiceHelper.getReleaseId(orderID);
        List<TryNBuyEntry> tryNBuyEntries = new ArrayList<>();
        tryNBuyEntries.add(new TryNBuyEntry(Long.parseLong(LMS_SKU.S1543_36_19_2),EnumSCM.TRIED_AND_BOUGHT+","+EnumSCM.TRIED_AND_BOUGHT));
        tryNBuyEntries.add(new TryNBuyEntry(Long.parseLong(LMS_SKU.S1542_36_25_4),EnumSCM.TRIED_AND_BOUGHT));
        
        List<ReleaseEntry> releaseEntries = new ArrayList<>();
        releaseEntries.add(new ReleaseEntry.Builder(releaseId, ReleaseStatus.DL).force(true).tryNBuyEntries(tryNBuyEntries).build());
        processRelease.processReleaseToStatus(processRelease.getReleaseEntryList(releaseEntries));
        
        
        Map<String, String> itemIdAndStatus = lmsServiceHelper.getTODItemAndStatusMap(packetId, tryNBuyEntries);
        String trackingNumber = lmsHepler.getTrackingNumber(packetId);
        lmsHepler.todValidation(packetId, trackingNumber, EnumSCM.NORMAL, itemIdAndStatus);
    }

    @Test(groups = {"TOD",  "Smoke","Regression"}, description = "ID: C105, 3Sku ,Credit card, {Bought, Bought, Not Bought}", priority = 1)
    public void E2E_LMS_TOD_02_03_B_B_NB_CC() throws Exception{
       
    		String orderID = lmsHepler.createMockOrder(EnumSCM.SH, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.NORMAL,"CC",true, true);
        String packetId = omsServiceHelper.getPacketId(orderID);
        String releaseId = omsServiceHelper.getReleaseId(orderID);
        List<TryNBuyEntry> tryNBuyEntries = new ArrayList<>();
        tryNBuyEntries.add(new TryNBuyEntry(Long.parseLong(LMS_SKU.S1543_36_19_2),EnumSCM.TRIED_AND_BOUGHT+","+EnumSCM.TRIED_AND_BOUGHT));
        tryNBuyEntries.add(new TryNBuyEntry(Long.parseLong(LMS_SKU.S1542_36_25_4),EnumSCM.TRIED_AND_NOT_BOUGHT));
        
        List<ReleaseEntry> releaseEntries = new ArrayList<>();
        releaseEntries.add(new ReleaseEntry.Builder(releaseId, ReleaseStatus.DL).force(true).tryNBuyEntries(tryNBuyEntries).build());
        processRelease.processReleaseToStatus(processRelease.getReleaseEntryList(releaseEntries));

        Map<String, String> itemIdAndStatus = lmsServiceHelper.getTODItemAndStatusMap(packetId, tryNBuyEntries);
        String trackingNumber = lmsHepler.getTrackingNumber(omsServiceHelper.getPacketId(orderID));
        lmsHepler.todValidation(packetId, trackingNumber, EnumSCM.NORMAL, itemIdAndStatus);
        String returnID = lmsHepler.getReturnIdFromOrderId(""+packetId);
        lmsServiceHelper.validateRmsStatusAndRefund(""+returnID,EnumSCM.RPU,true, rms_wait);
        lmsServiceHelper.transferShipmentBackToWH(returnID,36,5,"DC","WH");
    }

    @SuppressWarnings("unchecked")
	@Test(groups = {"TOD", "P0", "Smoke","Regression"}, description = "ID: C106, 3Sku ,{B,NB,NB}", priority = 1)
    public void E2E_LMS_TOD_03_03_B_NB_NB() throws Exception{
        String login = LMS_LOGIN.LogIn43;
        String orderID = end2EndHelper.createOrder(login, LMS_LOGIN.PASSWORD, LMS_PINCODE.ML_BLR, new String[] {LMS_SKU.S1541_36_21_0+":1",LMS_SKU.S1541_36_21_1+":1",LMS_SKU.S1541_36_21_2+":1"}, "", false, false, false,"", true);
        log.info("Order ID : " + orderID);
        String packetId = omsServiceHelper.getPacketId(orderID);
        String releaseId = omsServiceHelper.getReleaseId(orderID);
        Assert.assertTrue(omsServiceHelper.validatePacketStatusInOMS(packetId, EnumSCM.WP, 15), "Unable to validate order status 'WP' in OMS");
        Assert.assertTrue(omsServiceHelper.validateOrderOFFHoldStatusInOMS(orderID, 15), "Unable to validate order status 'WP' in OMS");
        List<TryNBuyEntry> tryNBuyEntries = new ArrayList<>();
        tryNBuyEntries.add(new TryNBuyEntry(Long.parseLong(LMS_SKU.S1541_36_21_0),EnumSCM.TRIED_AND_BOUGHT));
        tryNBuyEntries.add(new TryNBuyEntry(Long.parseLong(LMS_SKU.S1541_36_21_1),EnumSCM.TRIED_AND_NOT_BOUGHT));
        tryNBuyEntries.add(new TryNBuyEntry(Long.parseLong(LMS_SKU.S1541_36_21_2),EnumSCM.TRIED_AND_NOT_BOUGHT));

        List<ReleaseEntry> releaseEntries = new ArrayList<>();
        releaseEntries.add(new ReleaseEntry.Builder(releaseId, ReleaseStatus.DL).force(true).tryNBuyEntries(tryNBuyEntries).build());
        processRelease.processReleaseToStatus(processRelease.getReleaseEntryList(releaseEntries));
        
        Map<String, String> itemIdAndStatus = lmsServiceHelper.getTODItemAndStatusMap(packetId, tryNBuyEntries);
        String trackingNumber = lmsHepler.getTrackingNumber(packetId);
        lmsHepler.todValidation(packetId, trackingNumber, EnumSCM.NORMAL, itemIdAndStatus);
        List<Map<String,Object>> returns = DBUtilities.exSelectQuery("select source_return_id from return_shipment where order_id = "+packetId,"lms");
        lmsServiceHelper.validateRmsStatusAndRefund(returns.get(0).get("source_return_id").toString(),EnumSCM.RPU,true, rms_wait);
        lmsServiceHelper.validateRmsStatusAndRefund(returns.get(1).get("source_return_id").toString(),EnumSCM.RPU,true, rms_wait);
        lmsServiceHelper.transferShipmentBackToWH(returns.get(0).get("source_return_id").toString(),36,5,"DC","WH");
    }

    @SuppressWarnings("unchecked")
    @Test(groups = {"TOD",  "Smoke","Regression"}, description = "ID: C107, 3Sku ,{NB,NB,NB}", priority = 1)
    public void E2E_LMS_TOD_02_03_NB_NB_NB() throws Exception{
        String orderID = lmsHepler.createMockOrder(EnumSCM.SH, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.NORMAL,"cod",true, true);
        log.info("Order ID : " + orderID);
        String packetId = omsServiceHelper.getPacketId(orderID);
        String releaseId = omsServiceHelper.getReleaseId(orderID);
        List<TryNBuyEntry> tryNBuyEntries = new ArrayList<>();
        tryNBuyEntries.add(new TryNBuyEntry(Long.parseLong(LMS_SKU.S1543_36_19_2),EnumSCM.TRIED_AND_NOT_BOUGHT+","+EnumSCM.TRIED_AND_NOT_BOUGHT));
        tryNBuyEntries.add(new TryNBuyEntry(Long.parseLong(LMS_SKU.S1542_36_25_4),EnumSCM.TRIED_AND_NOT_BOUGHT));
        
        List<ReleaseEntry> releaseEntries = new ArrayList<>();
        releaseEntries.add(new ReleaseEntry.Builder(releaseId, ReleaseStatus.DL).force(true).tryNBuyEntries(tryNBuyEntries).build());
        processRelease.processReleaseToStatus(processRelease.getReleaseEntryList(releaseEntries));
        
        Map<String, String> itemIdAndStatus = lmsServiceHelper.getTODItemAndStatusMap(packetId, tryNBuyEntries);
        String trackingNumber = lmsHepler.getTrackingNumber(packetId);
        lmsHepler.todValidation(packetId, trackingNumber, EnumSCM.NORMAL, itemIdAndStatus);
        List<Map<String,Object>> returns = DBUtilities.exSelectQuery("select source_return_id from return_shipment where order_id = "+packetId,"lms");
        lmsServiceHelper.validateRmsStatusAndRefund(returns.get(0).get("source_return_id").toString(),EnumSCM.RPU,true, rms_wait);
        lmsServiceHelper.validateRmsStatusAndRefund(returns.get(1).get("source_return_id").toString(),EnumSCM.RPU,true, rms_wait);
        lmsServiceHelper.validateRmsStatusAndRefund(returns.get(2).get("source_return_id").toString(),EnumSCM.RPU,true, rms_wait);
    }

    @Test(groups = {"TOD",  "Smoke","Regression"}, description = "ID: C108, 3Sku , 1cancel,{B,NB}", priority = 1)
    public void E2E_LMS_TOD_03_03_1F_B_NB() throws Exception{
        LmsServiceHelper lmsServiceHelper = new LmsServiceHelper();
        String login = LMS_LOGIN.LogIn44;
        String orderID = end2EndHelper.createOrder(login, LMS_LOGIN.PASSWORD, LMS_PINCODE.ML_BLR, new String[] {LMS_SKU.S1541_36_21_0+":1",LMS_SKU.S1541_36_21_1+":1",LMS_SKU.S1541_36_21_2+":1"}, "", false, false, false,"", true);
        log.info("Order ID : " + orderID);
        String packetId = omsServiceHelper.getPacketId(orderID);
        String releaseId = omsServiceHelper.getReleaseId(orderID);
        Assert.assertTrue(omsServiceHelper.validatePacketStatusInOMS(packetId, EnumSCM.WP, 15), "Unable to validate order status 'WP' in OMS");
        Assert.assertTrue(omsServiceHelper.validateOrderOFFHoldStatusInOMS(orderID, 15), "Unable to validate order status 'WP' in OMS");
        Map<String, Object> lineId = (Map<String, Object>) DBUtilities.exSelectQueryForSingleRecord("select id from order_line where order_id_fk = "+orderID+" and sku_id = Long.parseLong(LMS_SKU.S1541_36_21_2) ", "oms");
        String cancellationLineId = lineId.get("id").toString();
        omsServiceHelper.cancelLine(packetId, LMS_LOGIN.LogIn63, new String[] {cancellationLineId+":1"}, 41);
        Thread.sleep(3000);
        List<TryNBuyEntry> tryNBuyEntries = new ArrayList<>();
        tryNBuyEntries.add(new TryNBuyEntry(Long.parseLong(LMS_SKU.S1541_36_21_0),EnumSCM.TRIED_AND_BOUGHT));
        tryNBuyEntries.add(new TryNBuyEntry(Long.parseLong(LMS_SKU.S1541_36_21_1),EnumSCM.TRIED_AND_NOT_BOUGHT));
        
        List<ReleaseEntry> releaseEntries = new ArrayList<>();
        releaseEntries.add(new ReleaseEntry.Builder(releaseId, ReleaseStatus.DL).force(true).tryNBuyEntries(tryNBuyEntries).build());
        processRelease.processReleaseToStatus(processRelease.getReleaseEntryList(releaseEntries));
        
        Map<String, String> itemIdAndStatus = lmsServiceHelper.getTODItemAndStatusMap(packetId, tryNBuyEntries);
        String trackingNumber = lmsHepler.getTrackingNumber(packetId);
        lmsHepler.todValidation(packetId, trackingNumber, EnumSCM.NORMAL, itemIdAndStatus);
        String returnID = lmsHepler.getReturnIdFromOrderId(""+packetId);
        lmsServiceHelper.transferShipmentBackToWH(returnID, 36, 5,"DC","WH");
    }

    @Test(groups = {"TOD",  "Smoke","Regression"}, description = "ID: C109, 3Sku ,{SNATCHED,B,NB}", priority = 1)
    public void E2E_LMS_TOD_02_03_S_B_NB() throws Exception{
        String orderID = lmsHepler.createMockOrder(EnumSCM.SH, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.NORMAL,"cod",true, true);
        String packetId = omsServiceHelper.getPacketId(orderID);
        String releaseId = omsServiceHelper.getReleaseId(orderID);
        List<TryNBuyEntry> tryNBuyEntries = new ArrayList<>();
        tryNBuyEntries.add(new TryNBuyEntry(Long.parseLong(LMS_SKU.S1543_36_19_2),EnumSCM.SNATCHED+","+EnumSCM.TRIED_AND_BOUGHT));
        tryNBuyEntries.add(new TryNBuyEntry(Long.parseLong(LMS_SKU.S1542_36_25_4),EnumSCM.TRIED_AND_NOT_BOUGHT));
        
        List<ReleaseEntry> releaseEntries = new ArrayList<>();
        releaseEntries.add(new ReleaseEntry.Builder(releaseId, ReleaseStatus.DL).force(true).tryNBuyEntries(tryNBuyEntries).build());
        processRelease.processReleaseToStatus(processRelease.getReleaseEntryList(releaseEntries));
        
        Map<String, String> itemIdAndStatus = lmsServiceHelper.getTODItemAndStatusMap(packetId, tryNBuyEntries);
        String trackingNumber = lmsHepler.getTrackingNumber(packetId);
        lmsHepler.todValidation(packetId, trackingNumber, EnumSCM.NORMAL, itemIdAndStatus);
        String returnID = lmsHepler.getReturnIdFromOrderId(""+packetId);
        lmsServiceHelper.validateRmsStatusAndRefund(returnID,EnumSCM.RPU,true, rms_wait);
        lmsServiceHelper.transferShipmentBackToWH(returnID,36,5,"DC","WH");
    }

    @Test(groups = {"TOD",  "Smoke","Regression"}, description = "ID: C110, 2Sku ,{SNATCHED,SNATCHED}", priority = 1)
    public void E2E_LMS_TOD_03_03_S_S_S() throws Exception{
        String orderID = lmsHepler.createMockOrder(EnumSCM.SH, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.NORMAL,"cod",true, true);
        String packetId = omsServiceHelper.getPacketId(orderID);
        String releaseId = omsServiceHelper.getReleaseId(orderID);
        List<TryNBuyEntry> tryNBuyEntries = new ArrayList<>();
        tryNBuyEntries.add(new TryNBuyEntry(Long.parseLong(LMS_SKU.S1543_36_19_2),EnumSCM.SNATCHED+","+EnumSCM.SNATCHED));
        tryNBuyEntries.add(new TryNBuyEntry(Long.parseLong(LMS_SKU.S1542_36_25_4),EnumSCM.SNATCHED));
        
        List<ReleaseEntry> releaseEntries = new ArrayList<>();
        releaseEntries.add(new ReleaseEntry.Builder(releaseId, ReleaseStatus.DL).force(true).tryNBuyEntries(tryNBuyEntries).build());
        processRelease.processReleaseToStatus(processRelease.getReleaseEntryList(releaseEntries));
        
        Map<String, String> itemIdAndStatus = lmsServiceHelper.getTODItemAndStatusMap(packetId, tryNBuyEntries);
        String trackingNumber = lmsHepler.getTrackingNumber(packetId);
        lmsHepler.todValidation(packetId, trackingNumber, EnumSCM.NORMAL, itemIdAndStatus);
    }

    @Test(groups = {"TOD", "P0", "Smoke","Regression"}, description = "ID: C111, 2 sku B,NB", priority = 1)
    public void E2E_LMS_TOD_02_02_B_NB() throws Exception{
        String login = LMS_LOGIN.LogIn45;
        String orderID = end2EndHelper.createOrder(login, LMS_LOGIN.PASSWORD, LMS_PINCODE.ML_BLR, new String[] {LMS_SKU.S1541_36_21_1+":1",LMS_SKU.S1541_36_21_0+":1"}, "", false, false, false,"", true);
        String packetId = omsServiceHelper.getPacketId(orderID);
        String releaseId = omsServiceHelper.getReleaseId(orderID);
        Assert.assertTrue(omsServiceHelper.validatePacketStatusInOMS(packetId, EnumSCM.WP, 15), "Unable to validate order status 'WP' in OMS");
        Assert.assertTrue(omsServiceHelper.validateOrderOFFHoldStatusInOMS(orderID, 15), "Unable to validate order status 'WP' in OMS");
        List<TryNBuyEntry> tryNBuyEntries = new ArrayList<>();
        tryNBuyEntries.add(new TryNBuyEntry(Long.parseLong(LMS_SKU.S1541_36_21_1),EnumSCM.TRIED_AND_BOUGHT));
        tryNBuyEntries.add(new TryNBuyEntry(Long.parseLong(LMS_SKU.S1541_36_21_0),EnumSCM.TRIED_AND_NOT_BOUGHT));
        
        List<ReleaseEntry> releaseEntries = new ArrayList<>();
        releaseEntries.add(new ReleaseEntry.Builder(releaseId, ReleaseStatus.DL).force(true).tryNBuyEntries(tryNBuyEntries).build());
        processRelease.processReleaseToStatus(processRelease.getReleaseEntryList(releaseEntries));
        
        Map<String, String> itemIdAndStatus = lmsServiceHelper.getTODItemAndStatusMap(packetId, tryNBuyEntries);
        String trackingNumber = lmsHepler.getTrackingNumber(packetId);
        lmsHepler.todValidation(packetId, trackingNumber, EnumSCM.NORMAL, itemIdAndStatus);
        String returnID = lmsHepler.getReturnIdFromOrderId(""+packetId);
        lmsServiceHelper.transferShipmentBackToWH(returnID, 36, 5,"DC","WH");
    }

    @Test(groups = {"TOD", "P0", "Smoke","Regression"}, description = "ID: C112, 1sku 3qty, Normal, partial CB, {B,B,NB} And first mark Failed delivery and then delivered", priority = 1)
    public void E2E_LMS_TOD_02_03_B_B_NB_FDDL() throws Exception{
        String orderID = lmsHepler.createMockOrder(EnumSCM.SH, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.NORMAL,"cod",true, true);
        String packetId = omsServiceHelper.getPacketId(orderID);
        String releaseId = omsServiceHelper.getReleaseId(orderID);
        List<TryNBuyEntry> tryNBuyEntries = new ArrayList<>();
        tryNBuyEntries.add(new TryNBuyEntry(Long.parseLong(LMS_SKU.S1543_36_19_2),EnumSCM.TRIED_AND_BOUGHT+","+EnumSCM.TRIED_AND_BOUGHT));
        tryNBuyEntries.add(new TryNBuyEntry(Long.parseLong(LMS_SKU.S1542_36_25_4), EnumSCM.TRIED_AND_NOT_BOUGHT));
       
        List<ReleaseEntry> releaseEntries = new ArrayList<>();
        releaseEntries.add(new ReleaseEntry.Builder(releaseId, ReleaseStatus.FDDL).force(true).tryNBuyEntries(tryNBuyEntries).build());
        processRelease.processReleaseToStatus(processRelease.getReleaseEntryList(releaseEntries));
        
        String returnID = lmsHepler.getReturnIdFromOrderId(""+packetId);
        lmsServiceHelper.transferShipmentBackToWH(returnID, 36, 5,"DC","WH");
    }

    @Test(groups = {"TOD",  "Smoke","Regression"}, description = "ID: C113, 1sku 3qty, Normal, partial CB, {B,B,NB}", priority = 1)
    public void E2E_LMS_TOD_01_03_B_B_NB() throws Exception{
        String login = LMS_LOGIN.LogIn50;
        String orderID = end2EndHelper.createOrder(login, LMS_LOGIN.PASSWORD, LMS_PINCODE.ML_BLR, new String[] {LMS_SKU.S1541_36_21_0+":3"}, "", false, false, false,"", true);
        String packetId = omsServiceHelper.getPacketId(orderID);
        String releaseId = omsServiceHelper.getReleaseId(orderID);
        Assert.assertTrue(omsServiceHelper.validatePacketStatusInOMS(packetId, EnumSCM.WP, 15), "Unable to validate order status 'WP' in OMS");
        Assert.assertTrue(omsServiceHelper.validateOrderOFFHoldStatusInOMS(orderID, 15), "Unable to validate order status 'WP' in OMS");
        List<TryNBuyEntry> tryNBuyEntries = new ArrayList<>();
        tryNBuyEntries.add(new TryNBuyEntry(Long.parseLong(LMS_SKU.S1541_36_21_0),EnumSCM.TRIED_AND_BOUGHT+","+EnumSCM.TRIED_AND_BOUGHT+","+EnumSCM.TRIED_AND_NOT_BOUGHT));
       
        List<ReleaseEntry> releaseEntries = new ArrayList<>();
        releaseEntries.add(new ReleaseEntry.Builder(releaseId, ReleaseStatus.FDDL).force(true).tryNBuyEntries(tryNBuyEntries).build());
        processRelease.processReleaseToStatus(processRelease.getReleaseEntryList(releaseEntries));
        
        Map<String, String> itemIdAndStatus = lmsServiceHelper.getTODItemAndStatusMap(packetId, tryNBuyEntries);
        String trackingNumber = lmsHepler.getTrackingNumber(packetId);
        lmsHepler.todValidation(packetId, trackingNumber, EnumSCM.NORMAL, itemIdAndStatus);
        String returnID = lmsHepler.getReturnIdFromOrderId(""+packetId);
        lmsServiceHelper.transferShipmentBackToWH(returnID, 36, 5,"DC","WH");
    }

    @Test(groups = {"TOD",  "Smoke","Regression"}, description = "ID: C114, 1sku 3qty {NB,NB,NB}", priority = 1)
    public void E2E_LMS_TOD_01_03_NB_NB_NB() throws Exception{
        String login = LMS_LOGIN.LogIn53;
        String orderID = end2EndHelper.createOrder(login, LMS_LOGIN.PASSWORD, LMS_PINCODE.ML_BLR, new String[] {LMS_SKU.S1541_36_21_1+":3"}, "", false, false, false,"", true);
        String packetId = omsServiceHelper.getPacketId(orderID);
        String releaseId = omsServiceHelper.getReleaseId(orderID);
        Assert.assertTrue(omsServiceHelper.validatePacketStatusInOMS(packetId, EnumSCM.WP, 15), "Unable to validate order status 'WP' in OMS");
        Assert.assertTrue(omsServiceHelper.validateOrderOFFHoldStatusInOMS(orderID, 15), "Unable to validate order status 'WP' in OMS");
        List<TryNBuyEntry> tryNBuyEntries = new ArrayList<>();
        tryNBuyEntries.add(new TryNBuyEntry(Long.parseLong(LMS_SKU.S1541_36_21_0),EnumSCM.TRIED_AND_BOUGHT+","+EnumSCM.TRIED_AND_BOUGHT+","+EnumSCM.TRIED_AND_NOT_BOUGHT));
       
        List<ReleaseEntry> releaseEntries = new ArrayList<>();
        releaseEntries.add(new ReleaseEntry.Builder(releaseId, ReleaseStatus.DL).force(true).tryNBuyEntries(tryNBuyEntries).build());
        processRelease.processReleaseToStatus(processRelease.getReleaseEntryList(releaseEntries));
        
        Map<String, String> itemIdAndStatus = lmsServiceHelper.getTODItemAndStatusMap(packetId, tryNBuyEntries);
        String trackingNumber = lmsHepler.getTrackingNumber(packetId);
        lmsHepler.todValidation(packetId, trackingNumber, EnumSCM.NORMAL,	 itemIdAndStatus);
    }
 
    @Test(groups = {"TOD",  "Smoke","Regression"}, description = "ID: C115, TryOnDelivery flow with single sku delivered, Bought, Than return", priority = 1)
    public void E2E_LMS_TOD_02_03_B_B_B_1Return() throws Exception{
        String orderID = lmsHepler.createMockOrder(EnumSCM.SH, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.NORMAL,"cod",true, true);
        String packetId = omsServiceHelper.getPacketId(orderID);
        String releaseId = omsServiceHelper.getReleaseId(orderID);
        List<TryNBuyEntry> tryNBuyEntries = new ArrayList<>();
        tryNBuyEntries.add(new TryNBuyEntry(Long.parseLong(LMS_SKU.S1543_36_19_2),EnumSCM.TRIED_AND_BOUGHT+","+EnumSCM.TRIED_AND_BOUGHT));
        tryNBuyEntries.add(new TryNBuyEntry(Long.parseLong(LMS_SKU.S1542_36_25_4),EnumSCM.TRIED_AND_BOUGHT));
        
        List<ReleaseEntry> releaseEntries = new ArrayList<>();
        releaseEntries.add(new ReleaseEntry.Builder(releaseId, ReleaseStatus.DL).force(true).tryNBuyEntries(tryNBuyEntries).build());
        processRelease.processReleaseToStatus(processRelease.getReleaseEntryList(releaseEntries));
        
        PacketEntry orderReleaseEntry = omsServiceHelper.getPacketEntry(packetId);
        @SuppressWarnings("deprecation")
		ReturnResponse returnResponse = rmsServiceHelper.createReturn(orderReleaseEntry.getOrderLines().get(0).getId().toString(), 1, ReturnMode.PICKUP, 27L, RefundMode.CASHBACK, "1234567890", "Myntra test lms automation", "Bangalore", "KA", LMS_PINCODE.ML_BLR, "ML");
        Assert.assertEquals(returnResponse.getStatus().getStatusType().toString(), "SUCCESS", "Return creation failed");
        Long returnID = returnResponse.getData().get(0).getId();
        log.info("Wait for 5 sec to process return in LMS");
        Thread.sleep(5000L);
        lmsServiceHelper.processReturnInLMS(""+returnID, EnumSCM.PICKED_UP_SUCCESSFULLY);
    }

    @Test(groups = {"TOD",  "Smoke","Regression"}, description = "ID: C116, TryOnDelivery flow with single sku delivered, Bought, Than Exchange", priority = 1)
    public void E2E_LMS_TOD_01_B_EXCHANGE() throws Exception{
        String login = LMS_LOGIN.LogIn56;
        String orderID = end2EndHelper.createOrder(login, LMS_LOGIN.PASSWORD, LMS_PINCODE.ML_BLR, new String[] {LMS_SKU.S1541_36_21_0+":1"}, "", false, false, false,"", true);
        log.info("Order ID : " + orderID);
        String packetId = omsServiceHelper.getPacketId(orderID);
        String releaseId = omsServiceHelper.getReleaseId(orderID);
        Assert.assertTrue(omsServiceHelper.validatePacketStatusInOMS(packetId, EnumSCM.WP, 15), "Unable to validate order status 'WP' in OMS");
        Assert.assertTrue(omsServiceHelper.validateOrderOFFHoldStatusInOMS(orderID, 15), "Unable to validate order status 'WP' in OMS");
        List<TryNBuyEntry> tryNBuyEntries = new ArrayList<>();
        tryNBuyEntries.add(new TryNBuyEntry(Long.parseLong(LMS_SKU.S1541_36_21_0),EnumSCM.TRIED_AND_BOUGHT));
        
        List<ReleaseEntry> releaseEntries = new ArrayList<>();
        releaseEntries.add(new ReleaseEntry.Builder(releaseId, ReleaseStatus.DL).force(true).tryNBuyEntries(tryNBuyEntries).build());
        processRelease.processReleaseToStatus(processRelease.getReleaseEntryList(releaseEntries));

        
        com.myntra.oms.client.entry.OrderReleaseEntry orderReleaseEntry = omsServiceHelper.getOrderReleaseEntry(packetId);
        Long lineID = orderReleaseEntry.getOrderLines().get(0).getId();
        ExchangeOrderResponse exchangeOrderResponse =(ExchangeOrderResponse) ppsServiceHelper.createExchange(""+orderID, ""+lineID, "DNL", 1, "Long.parseLong(LMS_SKU.S1541_36_21_1)");
        Assert.assertNotNull(exchangeOrderResponse.getSuccess(), "Unable to create exchange");
        String exchangeOrderID = omsServiceHelper.getOrderEntry(exchangeOrderResponse.getExchangeOrderId()).getId().toString();
        String exchnageReleaseId = omsServiceHelper.getPacketId(exchangeOrderID);
        
        List<ReleaseEntry> releaseEntries2 = new ArrayList<>();
        releaseEntries2.add(new ReleaseEntry.Builder(exchnageReleaseId, ReleaseStatus.DL).force(true).build());
        processRelease.processReleaseToStatus(processRelease.getReleaseEntryList(releaseEntries2));

        String returnID = lmsHepler.getReturnIdFromOrderId(""+packetId);
        Map<String, Object> returnShipment = DBUtilities.exSelectQueryForSingleRecord("select * from return_shipment where source_return_id = "+returnID, "lms");
        long destWarehouseId = (Long) returnShipment.get("return_warehouse_id");
        lmsServiceHelper.transferShipmentBackToWH(returnID, destWarehouseId, 5,"DC","WH");
    }

    @Test(groups = {"TOD",  "Smoke","Regression"}, description = "ID: C117, TryOnDelivery flow with single sku delivered, Unassigned to RTO", priority = 1)
    public void E2E_LMS_TOD_01_UNRTO() throws Exception{
        String orderId = lmsHepler.createMockOrder(EnumSCM.UNRTO, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.NORMAL,"cod",true, true);
        String releaseId = omsServiceHelper.getPacketId(orderId);
        lmsServiceHelper.transferShipmentBackToWHRTO(releaseId);
        Assert.assertEquals(rmsServiceHelper.recieveShipmentInRejoy(releaseId,36).getStatus().getStatusType().toString(),EnumSCM.SUCCESS,"RTO_RECEIVED call failed for RMS");
        Assert.assertTrue(lmsServiceHelper.validateOrderStatusInLMS(releaseId,EnumSCM.RECEIVED_IN_RETURNS_HUB,5),"RTO order is not in RTO_RECEIVED state");
    }

    @Test(groups = {"TOD",  "Smoke","Regression"}, description = "ID: C118, 2Sku ,{NOT_TRIED,NOT_TRIED}", priority = 1)
    public void E2E_LMS_TOD_02_03_NT_NT_NT() throws Exception{
        String orderID = lmsHepler.createMockOrder(EnumSCM.SH, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.NORMAL,"cod",true, true);
        log.info("Order ID : " + orderID);
        String packetId = omsServiceHelper.getPacketId(orderID);    
        String releaseId = omsServiceHelper.getReleaseId(orderID);
        List<TryNBuyEntry> tryNBuyEntries = new ArrayList<>();
        tryNBuyEntries.add(new TryNBuyEntry(Long.parseLong(LMS_SKU.S1543_36_19_2),EnumSCM.NOT_TRIED+","+EnumSCM.NOT_TRIED));
        tryNBuyEntries.add(new TryNBuyEntry(Long.parseLong(LMS_SKU.S1542_36_25_4),EnumSCM.NOT_TRIED));
              
        List<ReleaseEntry> releaseEntries = new ArrayList<>();
        releaseEntries.add(new ReleaseEntry.Builder(releaseId, ReleaseStatus.DL).force(true).tryNBuyEntries(tryNBuyEntries).build());
        processRelease.processReleaseToStatus(processRelease.getReleaseEntryList(releaseEntries));
        
        Map<String, String> itemIdAndStatus = lmsServiceHelper.getTODItemAndStatusMap(packetId, tryNBuyEntries);
        String trackingNumber = lmsHepler.getTrackingNumber(omsServiceHelper.getPacketId(orderID));
        lmsHepler.todValidation(packetId, trackingNumber, EnumSCM.NORMAL, itemIdAndStatus);
    }

    @SuppressWarnings("unchecked")
    @Test(groups = {"TOD", "Smoke", "Regression"}, description = "ID: C119, LMS E2E flow from order placement to LOST on PK then Mark RTO", priority = 1)
    public void ADMIN_LMS_TOD_PK_LOST_RTO() throws Exception {
        String releaseId = omsServiceHelper.getPacketId(lmsHepler.createMockOrder(EnumSCM.PK, LMS_PINCODE.NORTH_CGH, "ML", "36", EnumSCM.NORMAL,"cod",true, true));
        Assert.assertEquals(lmsServiceHelper.markOrderLOSTINHUB(releaseId),EnumSCM.SUCCESS);
        Assert.assertTrue(lmsServiceHelper.validateOrderStatusInLMS(releaseId,EnumSCM.LOST_IN_HUB,5));
        Assert.assertTrue(omsServiceHelper.validatePacketStatusInOMS(releaseId,"L",5));
        Assert.assertEquals(lmsServiceHelper.forceMarkRTO.apply(releaseId).toString(),EnumSCM.SUCCESS);
        Assert.assertTrue(lmsServiceHelper.validateOrderStatusInLMS(releaseId,EnumSCM.RTO_CONFIRMED,5));
        Assert.assertTrue(omsServiceHelper.validatePacketStatusInOMS(releaseId,EnumSCM.RTO,3));
        Assert.assertEquals(rmsServiceHelper.recieveShipmentInRejoy(releaseId,28).getStatus().getStatusType().toString(),EnumSCM.SUCCESS,"RTO_RECEIVED call failed for RMS");
        Assert.assertTrue(lmsServiceHelper.validateOrderStatusInLMS(releaseId,EnumSCM.RECEIVED_IN_RETURNS_HUB,5),"RTO order is not in RTO_RECEIVED state");
    }

    @SuppressWarnings("unchecked")
    @Test(groups = {"TOD", "Smoke", "Regression"}, description = "ID: C120, LMS E2E flow from order placement to LOST on IS then Mark RTO", priority = 1)
    public void ADMIN_LMS_TOD_SH_LOST_RTO() throws Exception {
        String releaseId = omsServiceHelper.getPacketId(lmsHepler.createMockOrder(EnumSCM.SH, LMS_PINCODE.NORTH_CGH, "ML", "36", EnumSCM.NORMAL,"cod",true, true));
        Assert.assertEquals(lmsServiceHelper.markOrderLOSTINTRANSIT(releaseId),EnumSCM.SUCCESS);
        Assert.assertTrue(lmsServiceHelper.validateOrderStatusInLMS(releaseId,EnumSCM.LOST,5));
        Assert.assertTrue(omsServiceHelper.validatePacketStatusInOMS(releaseId,"L",5));
        Assert.assertEquals(lmsServiceHelper.forceMarkRTO.apply(releaseId).toString(),EnumSCM.SUCCESS);
        Assert.assertTrue(lmsServiceHelper.validateOrderStatusInLMS(releaseId,EnumSCM.RTO_CONFIRMED,5));
        Assert.assertTrue(omsServiceHelper.validatePacketStatusInOMS(releaseId,EnumSCM.RTO,3));
    }

}
