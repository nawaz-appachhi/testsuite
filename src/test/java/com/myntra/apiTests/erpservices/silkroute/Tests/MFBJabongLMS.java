package com.myntra.apiTests.erpservices.silkroute.Tests;

import java.util.Random;

import org.testng.Assert;

import com.myntra.apiTests.common.Constants.EnumSCM;
import com.myntra.apiTests.erpservices.lms.Helper.LmsServiceHelper;
import com.myntra.apiTests.erpservices.oms.OMSServiceHelper;
import com.myntra.apiTests.erpservices.silkroute.SilkRouteConstants;
import com.myntra.apiTests.erpservices.silkroute.SilkRouteServiceHelper;
import com.myntra.lms.client.status.ShipmentType;
import com.myntra.lms.client.status.ShippingMethod;
import com.myntra.lordoftherings.boromir.DBUtilities;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.test.commons.testbase.BaseTest;

public class MFBJabongLMS extends BaseTest{
	
	private SilkRouteServiceHelper silkRouteServiceHelper = new SilkRouteServiceHelper();
	private OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
	private LmsServiceHelper lmsServiceHelper = new LmsServiceHelper();

	public void jabongMarketPlaceOrderTillSH() throws Exception {
//        Random random = new Random();
//        String orderId = String.valueOf(System.currentTimeMillis() / 1000 + random.nextInt(100));
//		String orderResponse = silkRouteServiceHelper.invokeSilkRouteServiceCreateOrderAPI(SilkRouteConstants.JB, orderId, "order_item_created",
//                "APPROVED", "false", SilkRouteConstants.DISPATCH_DATE, SilkRouteConstants.DISPATCH_DATE, "1", "0",
//                SilkRouteConstants.FK_LISTING_ID, SilkRouteConstants.SKU_CODE_NIKE_CREATEORDER, "200", "560068").getResponseBody();
//        String packetId = omsServiceHelper.getReleaseIdFromStoreOrderId(APIUtilities.getElement(orderResponse, "orderItemId", "json"));
//       
//        
//        //DBUtilities.exUpdateQuery("update order_line set status_code = 'QD' where order_release_id_fk = '"+packetId+"'","oms");
//        //DBUtilities.exUpdateQuery("update order_release set status_code = 'PK' where id = '"+packetId+"'","oms");
//        omsServiceHelper.pushReleaseToLms(packetId);
		String packetId = "";
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
}
