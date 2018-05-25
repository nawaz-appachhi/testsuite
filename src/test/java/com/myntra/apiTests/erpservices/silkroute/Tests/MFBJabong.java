package com.myntra.apiTests.erpservices.silkroute.Tests;

import com.myntra.apiTests.common.Constants.EnumSCM;
import com.myntra.apiTests.common.Constants.ReleaseStatus;
import com.myntra.apiTests.common.ExceptionHandler;
import com.myntra.apiTests.common.ProcessOrder.Service.ProcessRelease;
import com.myntra.apiTests.common.entries.ReleaseEntry;
import com.myntra.apiTests.common.entries.ReleaseEntryList;
import com.myntra.apiTests.erpservices.atp.ATPServiceHelper;
import com.myntra.apiTests.erpservices.ims.IMSServiceHelper;
import com.myntra.apiTests.erpservices.oms.OMSServiceHelper;
import com.myntra.apiTests.erpservices.silkroute.SilkRouteJabongServiceHelper;
import com.myntra.apiTests.erpservices.silkroute.SilkRouteServiceHelper;
import com.myntra.apiTests.erpservices.silkroute.dp.MFBJabongDP;
import com.myntra.commons.codes.StatusResponse;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.silkroute.client.response.OrderReleaseResponse;
import com.myntra.silkroute.client.response.jabong.BaseSilkrouteRespForJabong;
import com.myntra.test.commons.service.Svc;
import com.myntra.test.commons.testbase.BaseTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/* Created by Sneha.Agarwal on 07/11/17.*/


public class MFBJabong extends BaseTest{

    SilkRouteJabongServiceHelper silkRouteJabongServiceHelper = new SilkRouteJabongServiceHelper();
    OMSServiceHelper omsserviceHelper = new OMSServiceHelper();
    ATPServiceHelper atpServiceHelper = new ATPServiceHelper();
    IMSServiceHelper imsServiceHelper = new IMSServiceHelper();
    static Logger log = LoggerFactory.getLogger(MFBJabong.class);
    ProcessRelease processRelease = new ProcessRelease();


    @Test(description = "Create Order Jabong with Various scenarios", dataProvider = "jabong_CreateOrder", dataProviderClass = MFBJabongDP.class)
    public void createOrderJabong(HashMap<String,String> attributeList, double totalAmount, String shippingPincode, String processingStartDate, String processingEndDate, String type, String paymentMode,int errorMessage) throws Exception {
        imsServiceHelper.updateInventory(new String[] { attributeList.get("skuId")+":"+attributeList.get("warehouseId")+":1000:0"},"ON_HAND");
        Svc svc = silkRouteJabongServiceHelper.createOrderMFBJabong(attributeList, totalAmount, shippingPincode, processingStartDate, processingEndDate, type,paymentMode);
        BaseSilkrouteRespForJabong baseSilkrouteRespForJabong = (BaseSilkrouteRespForJabong) APIUtilities.getJsontoObject(svc.getResponseBody(), new BaseSilkrouteRespForJabong());
        ExceptionHandler.handleEquals(baseSilkrouteRespForJabong.getErrCode(), errorMessage, "Error Code : ");
    }


    @Test(description = "Create Order Jabong and verify the inventory", dataProvider = "jabongCreateOderOther", dataProviderClass = MFBJabongDP.class)
    public void createOrderInventoryBlockJabong(HashMap<String,String> attributeList, double totalAmount, String shippingPincode, String processingStartDate, String processingEndDate, String type, String paymentMode,int errorMessage) throws Exception {
        imsServiceHelper.updateInventory(new String[] { attributeList.get("skuId")+":"+attributeList.get("warehouseId")+":1000:0"},"ON_HAND");
        int imscountBeforeOrderPlacement = SilkRouteServiceHelper.getIMSboc(Integer.parseInt(attributeList.get("skuId")), EnumSCM.MFB_Jabong_StoreID, attributeList.get("warehouseId"));
        Svc svc = silkRouteJabongServiceHelper.createOrderMFBJabong(attributeList, totalAmount, shippingPincode, processingStartDate, processingEndDate, type,paymentMode);
        BaseSilkrouteRespForJabong baseSilkrouteRespForJabong = (BaseSilkrouteRespForJabong) APIUtilities.getJsontoObject(svc.getResponseBody(), new BaseSilkrouteRespForJabong());

        ExceptionHandler.handleEquals(baseSilkrouteRespForJabong.getErrCode(), errorMessage, "Error Message : ");
        String storeOrderId=silkRouteJabongServiceHelper.getStore_order_id(attributeList.get("itemId"));
        ExceptionHandler.handleEquals(silkRouteJabongServiceHelper.validateOnHoldStatus(storeOrderId),"false","Order is on hold");
        log.info(storeOrderId);
        String orderReleaseId=omsserviceHelper.getReleaseIdFromStoreOrderId(storeOrderId);
        int imscountAfterOrderPlacement = SilkRouteServiceHelper.getIMSboc(Integer.parseInt(attributeList.get("skuId")), EnumSCM.MFB_Jabong_StoreID, attributeList.get("warehouseId"));
        ExceptionHandler.handleEquals(imscountBeforeOrderPlacement,imscountAfterOrderPlacement+1,"Inventory block count matches");
    }

    @Test(description = "Create Order Jabong and verify the wareHouse stamping in OMS", dataProvider = "jabongCreateOderOther", dataProviderClass = MFBJabongDP.class)
    public void createOrderWHStampJabong(HashMap<String,String> attributeList, double totalAmount, String shippingPincode, String processingStartDate, String processingEndDate, String type,String paymentMode, int errorMessage) throws Exception {
        imsServiceHelper.updateInventory(new String[] { attributeList.get("skuId")+":"+attributeList.get("warehouseId")+":1000:0"},"ON_HAND");
        Svc svc = silkRouteJabongServiceHelper.createOrderMFBJabong(attributeList, totalAmount, shippingPincode, processingStartDate, processingEndDate, type,paymentMode);
        BaseSilkrouteRespForJabong baseSilkrouteRespForJabong = (BaseSilkrouteRespForJabong) APIUtilities.getJsontoObject(svc.getResponseBody(), new BaseSilkrouteRespForJabong());

        ExceptionHandler.handleEquals(baseSilkrouteRespForJabong.getErrCode(), errorMessage, "Error Message : ");
        String storeOrderId=silkRouteJabongServiceHelper.getStore_order_id(attributeList.get("itemId"));
        ExceptionHandler.handleEquals(silkRouteJabongServiceHelper.validateOnHoldStatus(storeOrderId),"false","Order is on hold");
        log.info(storeOrderId);
        String orderReleaseId=omsserviceHelper.getReleaseIdFromStoreOrderId(storeOrderId);

        String wareHouseId = omsserviceHelper.getOneColumnValue("warehouse_id","store_order_id",storeOrderId,"order_release","oms");
        ExceptionHandler.handleEquals(wareHouseId,attributeList.get("warehouseId"),"WareHouse Id correctly stamped");
    }

    @Test(description = "Create Order Jabong And Mark it till PK", dataProvider = "jabongCreateOderOther", dataProviderClass = MFBJabongDP.class)
    public void createOrderAndMarkTillPKJabong(HashMap<String,String> attributeList, double totalAmount, String shippingPincode, String processingStartDate, String processingEndDate, String type,String paymentMode, int errorMessage) throws Exception {
        imsServiceHelper.updateInventory(new String[] { attributeList.get("skuId")+":"+attributeList.get("warehouseId")+":1000:0"},"ON_HAND");
        Svc svc = silkRouteJabongServiceHelper.createOrderMFBJabong(attributeList, totalAmount, shippingPincode, processingStartDate, processingEndDate, type,paymentMode);
        BaseSilkrouteRespForJabong baseSilkrouteRespForJabong = (BaseSilkrouteRespForJabong) APIUtilities.getJsontoObject(svc.getResponseBody(), new BaseSilkrouteRespForJabong());

        ExceptionHandler.handleEquals(baseSilkrouteRespForJabong.getErrCode(), errorMessage, "Error Message : ");
        String storeOrderId=silkRouteJabongServiceHelper.getStore_order_id(attributeList.get("itemId"));
        ExceptionHandler.handleEquals(silkRouteJabongServiceHelper.validateOnHoldStatus(storeOrderId),"false","Order is on hold");
        log.info(storeOrderId);
        String orderReleaseId=omsserviceHelper.getReleaseIdFromStoreOrderId(storeOrderId);

        List<ReleaseEntry> releaseEntries = new ArrayList<>();
        releaseEntries.add(new ReleaseEntry.Builder(orderReleaseId, ReleaseStatus.PK).build());
        ReleaseEntryList releaseEntryList= new ReleaseEntryList(releaseEntries);
        processRelease.processReleaseToStatus(releaseEntryList);

       // processRelease.processReleaseToStatus(new ReleaseEntry.Builder(orderReleaseId, ReleaseStatus.PK).build());

    }

    @Test (description = "Create Order Jabong And Resolve On Hold Order", dataProvider = "jabongCreateOderOther", dataProviderClass = MFBJabongDP.class)
    public void createOrderAndResolveOnHold(HashMap<String,String> attributeList, double totalAmount, String shippingPincode, String processingStartDate, String processingEndDate, String type,String paymentMode, int errorMessage) throws Exception {
        imsServiceHelper.updateInventory(new String[] { attributeList.get("skuId")+":"+attributeList.get("warehouseId")+":0:0"},"ON_HAND");
        Svc svc = silkRouteJabongServiceHelper.createOrderMFBJabong(attributeList, totalAmount, shippingPincode, processingStartDate, processingEndDate, type,paymentMode);
        BaseSilkrouteRespForJabong baseSilkrouteRespForJabong = (BaseSilkrouteRespForJabong) APIUtilities.getJsontoObject(svc.getResponseBody(), new BaseSilkrouteRespForJabong());

        ExceptionHandler.handleEquals(baseSilkrouteRespForJabong.getErrCode(), errorMessage, "Error Message : ");
        String storeOrderId=silkRouteJabongServiceHelper.getStore_order_id(attributeList.get("itemId"));
        ExceptionHandler.handleEquals(silkRouteJabongServiceHelper.validateOnHoldStatus(storeOrderId),"true","Order is on hold");
        log.info(storeOrderId);
        String orderReleaseId=omsserviceHelper.getReleaseIdFromStoreOrderId(storeOrderId);
        imsServiceHelper.updateInventory(new String[] { attributeList.get("skuId")+":"+attributeList.get("warehouseId")+":1000:1"},"ON_HAND");

        svc= silkRouteJabongServiceHelper.resolveOnHoldOrders("21576282452");
        StatusResponse statusResponse = (StatusResponse) APIUtilities.getJsontoObject(svc.getResponseBody(),new StatusResponse());
        ExceptionHandler.handleEquals(statusResponse.getStatusType().toString(),"SUCCESS", "Response for onhold Resolution");


        //this should not be required.
        svc = silkRouteJabongServiceHelper.repushToOMS(storeOrderId);
        OrderReleaseResponse orderReleaseResponse = (OrderReleaseResponse) APIUtilities.getJsontoObject(svc.getResponseBody(),new OrderReleaseResponse());
        ExceptionHandler.handleEquals(orderReleaseResponse.getStatus().getStatusType(),"SUCCESS", "Repush to OMS");
        ExceptionHandler.handleEquals(silkRouteJabongServiceHelper.validateOnHoldStatus(storeOrderId),"false","Order is on hold");
    }


    @Test(description = "Create Order Jabong and cancel it before processing", dataProvider = "jabongCreateOderOther", dataProviderClass = MFBJabongDP.class)
    public void createOrderJabongCancelOrder(HashMap<String,String> attributeList, double totalAmount, String shippingPincode, String processingStartDate, String processingEndDate, String type, String paymentMode,int errorMessage) throws Exception {
        imsServiceHelper.updateInventory(new String[] { attributeList.get("skuId")+":"+attributeList.get("warehouseId")+":1000:0"},"ON_HAND");
        Svc svc = silkRouteJabongServiceHelper.createOrderMFBJabong(attributeList, totalAmount, shippingPincode, processingStartDate, processingEndDate, type,paymentMode);
        BaseSilkrouteRespForJabong baseSilkrouteRespForJabong = (BaseSilkrouteRespForJabong) APIUtilities.getJsontoObject(svc.getResponseBody(), new BaseSilkrouteRespForJabong());
        ExceptionHandler.handleEquals(baseSilkrouteRespForJabong.getErrCode(), errorMessage, "Error Code : ");
    }

}
