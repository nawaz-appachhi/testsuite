package com.myntra.apiTests.erpservices.silkroute.Tests;

import com.myntra.apiTests.end2end.End2EndHelper;
import com.myntra.apiTests.erpservices.oms.OMSServiceHelper;
import com.myntra.apiTests.erpservices.silkroute.SilkRouteConstants;
import com.myntra.apiTests.erpservices.silkroute.SilkRouteServiceHelper;
import com.myntra.apiTests.erpservices.silkroute.dp.SilkRouteServiceDP;
import com.myntra.commons.codes.StatusResponse.Type;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.boromir.DBUtilities;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.oms.client.entry.OrderEntry;
import com.myntra.oms.client.entry.OrderReleaseEntry;
import com.myntra.oms.client.response.OrderReleaseResponse;
import com.myntra.test.commons.service.Svc;
import com.myntra.test.commons.testbase.BaseTest;
import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;
import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONException;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.IOException;
import java.text.ParseException;

/**
 * @author santwana.samantray
 */
public class SilkRouteServiceTest extends BaseTest {

    Initialize init = new Initialize("/Data/configuration");
    Logger log = Logger.getLogger(SilkRouteServiceTest.class);
    SilkRouteServiceHelper silkRouteServiceHelper = new SilkRouteServiceHelper();
    private static long waitTime = 15000;
    OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
    private long waitTimeMax = 70000L;


    @Test(groups = {"Smoke", "Sanity", "ProdSanity", "Regression",
            "MiniRegression", "ExhaustiveRegression"}, dataProvider = "SilkrouteServiceDP_createOrder_verifySuccessResponse", dataProviderClass = SilkRouteServiceDP.class)
    public void SilkRouteServiceTest_createOrder_verifySuccessResponse(
            String environment, String OrderReleaseId, String OrderEventType,
            String status, String Hold,
            String DispatchDate, String DispatchAfter, String quantity,
            String cancelledQuantity, String ListingID, String SKU_barcode,
            String price, String pincode, String warehouse,
            String store_id) throws IOException, JAXBException, ParseException, JSONException, XMLStreamException {

        int imscount = SilkRouteServiceHelper.getIMSboc(SilkRouteConstants.SKU_ID_NIKE_CREATEORDER, store_id, warehouse);
        imscount++;

        Svc createOrderReqGen = silkRouteServiceHelper.invokeSilkRouteServiceCreateOrderAPI(environment, OrderReleaseId, OrderEventType, status, Hold, DispatchDate, DispatchAfter, quantity,
                cancelledQuantity, ListingID, SKU_barcode, price, pincode);
        String createOrderResponse = createOrderReqGen.getResponseBody();
        log.debug("CreateOrderResponse:-"+createOrderResponse);
        End2EndHelper.sleep(10000);
        int blockedOrderCount = SilkRouteServiceHelper.getIMSboc(SilkRouteConstants.SKU_ID_NIKE_CREATEORDER, store_id, warehouse);

        String orderid = APIUtilities.getElement(createOrderResponse, "orderItemId", "json");

        OrderReleaseEntry orderReleaseEntry = omsServiceHelper.getOrderEntryByStoreOrderID(orderid).getOrderReleases().get(0);

        Assert.assertTrue(omsServiceHelper.validateReleaseStatusInOMS(orderReleaseEntry.getId().toString(), "RFR:WP", 10),"Release is not in WP status");
        orderReleaseEntry = omsServiceHelper.getOrderEntryByStoreOrderID(orderid).getOrderReleases().get(0);
        Assert.assertEquals(blockedOrderCount, imscount, "IMS Block Order Count");
        Assert.assertEquals(orderReleaseEntry.getOnHold(), Boolean.FALSE, "ON-Hold Status should be false");
        Assert.assertTrue(orderReleaseEntry.getStatus().equalsIgnoreCase("WP")||orderReleaseEntry.getStatus().equalsIgnoreCase("RFR"), "Status Code should be WP in OMS");
    }

    // 2.checkSkuID Missing passed
    @Test(groups = {"Smoke", "Sanity", "ProdSanity", "Regression",
            "MiniRegression", "ExhaustiveRegression"}, dataProvider = "SilkRouteServiceDP_createOrder_verifyFailureForSkuIdMissing", dataProviderClass = SilkRouteServiceDP.class)
    public void SilkRouteServiceTest_createOrder_verifyFailures(
            String environment, String OrderReleaseId, String OrderEventType,
            String status, String Hold,
            String DispatchDate, String DispatchAfter, String quantity,
            String cancelledQuantity, String ListingID, String SKU_barcode,
            String price, String pincode, String response, String statusMessage) throws IOException, JAXBException, ParseException {
            Svc createOrderReqGen = silkRouteServiceHelper.invokeSilkRouteServiceCreateOrderAPI(environment,OrderReleaseId, OrderEventType, status,
                Hold, DispatchDate, DispatchAfter, quantity,
                cancelledQuantity, ListingID, SKU_barcode, price,
                pincode);
        Assert.assertEquals(createOrderReqGen.getResponseBody(), statusMessage, "Verify Response Message");
        Assert.assertEquals(""+createOrderReqGen.getResponseStatus(), response, "Verify Response Status Code");
    }


    // 11.Create order with event hold passed
    @Test(groups = {"Smoke", "Sanity", "ProdSanity", "Regression",
            "MiniRegression", "ExhaustiveRegression"}, dataProvider = "SilkrouteServiceDP_createOrder_verifySuccessWithOrderEventHold", dataProviderClass = SilkRouteServiceDP.class)
    public void SilkRouteServiceTest_createOrder_verifySuccessWithOrderEventHold(String environment, String OrderReleaseId, String OrderEventType,
                                                                                 String status, String Hold,
                                                                                 String DispatchDate, String DispatchAfter, String quantity,
                                                                                 String cancelledQuantity, String ListingID, String SKU_barcode,
                                                                                 String price, String pincode, String response, String warehouse,
                                                                                 String store_id) throws IOException, JAXBException, ParseException {
        End2EndHelper.sleep(waitTime);
        int imscount = SilkRouteServiceHelper.getIMSboc(SilkRouteConstants.SKU_ID_NIKE_ORDER_EVENT_HOLD, store_id,
                warehouse);
        imscount++;
        Svc createOrderReqGen = silkRouteServiceHelper
                .invokeSilkRouteServiceCreateOrderAPI(environment,OrderReleaseId, OrderEventType, status,
                        Hold, DispatchDate, DispatchAfter, quantity,
                        cancelledQuantity, ListingID, SKU_barcode, price,
                        pincode);

        String createOrderResponse = createOrderReqGen.getResponseBody();
        String orderid = APIUtilities.getElement(createOrderResponse, "orderItemId", "json");

        End2EndHelper.sleep(waitTime);
        OrderEntry orderEntry = omsServiceHelper.getOrderEntryByStoreOrderID(orderid);

        Assert.assertTrue(omsServiceHelper.validateReleaseStatusInOMS(orderEntry.getOrderReleases().iterator().next().getId().toString(), "RFR:WP", 10),"Release is not in WP status");
        orderEntry = omsServiceHelper.getOrderEntryByStoreOrderID(orderid);
        int blockedOrderCount = SilkRouteServiceHelper.getIMSboc(SilkRouteConstants.SKU_ID_NIKE_ORDER_EVENT_HOLD,
                store_id, warehouse);
        SoftAssert sft = new SoftAssert();
        sft.assertEquals(blockedOrderCount, imscount, "Verify IMS Block Order Count");
        sft.assertEquals(orderEntry.getOnHold(), Boolean.TRUE, "Order Should be in ON-HOLD status");
        sft.assertTrue(orderEntry.getOrderReleases().iterator().next().getStatus().equalsIgnoreCase("WP") || orderEntry.getOrderReleases().iterator().next().getStatus().equalsIgnoreCase("RFR"),  "Order Status should be in WP Status");
        sft.assertEquals(orderEntry.getOrderReleases().iterator().next().getOnHold(), Boolean.FALSE, "Release level ON_HOLD should be false");
        sft.assertAll();
    }

    // 12.Upadte a order to hold status
    @Test(groups = {"Smoke", "Sanity", "ProdSanity", "Regression",
            "MiniRegression", "ExhaustiveRegression"}, dataProvider = "SilkrouteServiceDP_updateOrder_verifySuccessWithOrderEventHold", dataProviderClass = SilkRouteServiceDP.class)
    public void updateOrderVerifySuccessWithOrderEventHold(String environment,
                                                           String OrderReleaseId, String OrderEventType,
                                                           String status, String Hold,
                                                           String DispatchDate, String DispatchAfter, String quantity,
                                                           String cancelledQuantity, String ListingID, String SKU_barcode,
                                                           String price, String pincode, String response) throws IOException, JAXBException, ParseException {

        Svc createOrderReqGen = silkRouteServiceHelper
                .invokeSilkRouteServiceCreateOrderAPI(environment,OrderReleaseId, OrderEventType, status,
                        Hold, DispatchDate, DispatchAfter, quantity,
                        cancelledQuantity, ListingID, SKU_barcode, price,
                        pincode);
        Assert.assertEquals(""+createOrderReqGen.getResponseStatus(), response, "Response Code should be 200");


        OrderEntry orderEntry = omsServiceHelper.getOrderEntryByStoreOrderID(OrderReleaseId);

        Assert.assertTrue(omsServiceHelper.validateReleaseStatusInOMS(orderEntry.getOrderReleases().iterator().next().getId().toString(), "RFR:WP", 15), "Order Status Should be in WP");

        Assert.assertTrue(omsServiceHelper.validateReleaseStatusInOMS(orderEntry.getOrderReleases().iterator().next().getId().toString(), "WP", 15), "Order Status Should be in WP");

        End2EndHelper.sleep(waitTime);
        orderEntry = omsServiceHelper.getOrderEntryByStoreOrderID(OrderReleaseId);
        SoftAssert sft = new SoftAssert();
        sft.assertTrue(orderEntry.getOnHold(), "Order Should go ON-HOLD");
        sft.assertEquals(orderEntry.getOnHoldReasonId().intValue(), 38, "Order Should go ON-HOLD with ON-HOLD ID 38");
        sft.assertAll();
    }


    @Test(groups = {"Smoke", "Sanity", "ProdSanity", "Regression",
            "MiniRegression", "ExhaustiveRegression"}, dataProvider = "SilkrouteServiceDP_updateOrder_verifySuccessWithOrderEventUnHold",
            dataProviderClass = SilkRouteServiceDP.class, description = "Unhold a hold order")
    public void SilkRouteService_updateOrder_verifySuccessWithOrderEventUnHold(
            String environment, String OrderReleaseId, String OrderEventType,
            String status, String Hold,
            String DispatchDate, String DispatchAfter, String quantity,
            String cancelledQuantity, String ListingID, String SKU_barcode,
            String price, String pincode, String response) throws IOException, JAXBException, ParseException {

        OrderEntry orderEntry = omsServiceHelper.getOrderEntryByStoreOrderID(OrderReleaseId);
        Assert.assertTrue(omsServiceHelper.validateReleaseStatusInOMS(orderEntry.getOrderReleases().iterator().next().getId().toString(), "RFR:WP", 15), "Order Status Should be in WP");
        Assert.assertTrue(orderEntry.getOnHold(), "Order Should go ON-HOLD");
        Assert.assertEquals(orderEntry.getOnHoldReasonId().intValue(), 38, "Order Should go ON-HOLD with ON-HOLD ID 38");

        Svc createOrderReqGen = silkRouteServiceHelper
                .invokeSilkRouteServiceCreateOrderAPI(environment,OrderReleaseId, OrderEventType, status,
                        Hold, DispatchDate, DispatchAfter, quantity,
                        cancelledQuantity, ListingID, SKU_barcode, price,
                        pincode);

        SoftAssert sft = new SoftAssert();
        sft.assertEquals(""+createOrderReqGen.getResponseStatus(), response, "Response Code should be 200");


        orderEntry = omsServiceHelper.getOrderEntryByStoreOrderID(OrderReleaseId);
        sft.assertTrue(omsServiceHelper.validateReleaseStatusInOMS(orderEntry.getOrderReleases().iterator().next().getId().toString(), "WP", 15), "Order Status Should be in WP");
        End2EndHelper.sleep(waitTime);
        orderEntry = omsServiceHelper.getOrderEntryByStoreOrderID(OrderReleaseId);
        sft.assertFalse(orderEntry.getOnHold(), "Order Should be OFF-HOLD");
        sft.assertAll();
    }


    @Test(description = "Mark a order on hold which is already packed", groups = {
            "Smoke", "Sanity", "ProdSanity", "Regression", "MiniRegression",
            "ExhaustiveRegression"}, dataProvider = "SilkRouteServiceDP_updateOrder_MarkOrderHold_inPackedStatus", dataProviderClass = SilkRouteServiceDP.class)
    public void updateOrderMarkOrderHoldInPackedStatus(String environment,
                                                       String OrderReleaseId, String OrderEventType,
                                                       String status, String Hold,
                                                       String DispatchDate, String DispatchAfter, String quantity,
                                                       String cancelledQuantity, String ListingID, String SKU_barcode,
                                                       String price, String pincode, String response, String statusMessage) throws IOException, JAXBException, ParseException {
        End2EndHelper.sleep(waitTime);
        Svc createOrderReqGen = silkRouteServiceHelper
                .invokeSilkRouteServiceCreateOrderAPI(environment, OrderReleaseId, OrderEventType, status,
                        Hold, DispatchDate, DispatchAfter, quantity,
                        cancelledQuantity, ListingID, SKU_barcode, price,
                        pincode);
        SoftAssert sft = new SoftAssert();
        sft.assertEquals(Integer.parseInt(response), createOrderReqGen.getResponseStatus());
        End2EndHelper.sleep(waitTime);
        OrderEntry orderEntry = omsServiceHelper.getOrderEntryByStoreOrderID(OrderReleaseId);
        sft.assertFalse(orderEntry.getOnHold(), "Order Should Not Go ON-HOLD after PK");
        sft.assertAll();
    }



    @Test(groups = {"Smoke", "Sanity", "ProdSanity", "Regression",
            "MiniRegression", "ExhaustiveRegression"}, dataProvider = "SilkrouteServiceDP_PartialCancelOrder_verifySuccessWithOrderEventHold",
            dataProviderClass = SilkRouteServiceDP.class, description = "Partial cancel a order on hold passed")
    public void partialCancelOrderVerifySuccessWithOrderEventHold(String environment,
                                                                  String OrderReleaseId, String OrderEventType,
                                                                  String status, String Hold,
                                                                  String DispatchDate, String DispatchAfter, String quantity,
                                                                  String cancelledQuantity, String ListingID, String SKU_barcode,
                                                                  String price, String pincode, String response, String warehouse,
                                                                  String store_id) throws IOException, JAXBException, ParseException {

        End2EndHelper.sleep(waitTime);
        int imscount = SilkRouteServiceHelper.getIMSboc(SilkRouteConstants.SKU_ID_NIKE_PARTIAL_CANCEL, store_id,
                warehouse);
        imscount = imscount - 1;

        Svc createOrderReqGen = silkRouteServiceHelper.invokeSilkRouteServiceCreateOrderAPI(environment,OrderReleaseId, OrderEventType, status,
                Hold, DispatchDate, DispatchAfter, quantity,
                cancelledQuantity, ListingID, SKU_barcode, price,
                pincode);
        SoftAssert sft = new SoftAssert();
        sft.assertEquals(Integer.parseInt(response), createOrderReqGen.getResponseStatus(), "Verify Response Status Code");

        OrderEntry orderEntry = omsServiceHelper.getOrderEntryByStoreOrderID(OrderReleaseId);

        sft.assertTrue(omsServiceHelper.validateReleaseStatusInOMS(orderEntry.getOrderReleases().iterator().next().getId().toString(), "RFR:WP", 5), "Order Status Should be in Q");
        orderEntry = omsServiceHelper.getOrderEntryByStoreOrderID(OrderReleaseId);
        End2EndHelper.sleep(waitTime);
        sft.assertTrue(orderEntry.getOnHold(), "Order Should be ON-HOLD");
        sft.assertEquals(orderEntry.getOnHoldReasonId().intValue(),38, "Order Should be ON-HOLD with Reason ID 38");
        int blockedOrderCount = SilkRouteServiceHelper.getIMSboc(SilkRouteConstants.SKU_ID_NIKE_PARTIAL_CANCEL, store_id, warehouse);
        sft.assertEquals(blockedOrderCount, imscount, "IMS Inventory count should reduce after FK Item Cancellation");
        sft.assertAll();
    }


    @Test(groups = {"Smoke", "Sanity", "ProdSanity", "Regression",
            "MiniRegression", "ExhaustiveRegression"}, dataProvider = "SilkrouteServiceDP_PartialCancelOrder_verifySuccess", dataProviderClass = SilkRouteServiceDP.class)
    public void partialOrderCancellation(String environment,
                                         String OrderReleaseId, String OrderEventType,
                                         String status, String Hold,
                                         String DispatchDate, String DispatchAfter, String quantity,
                                         String cancelledQuantity, String ListingID, String SKU_barcode,
                                         String price, String pincode, String response, String warehouse,
                                         String store_id) throws IOException, JAXBException, ParseException {

        End2EndHelper.sleep(waitTime);
        int imscount = SilkRouteServiceHelper.getIMSboc(SilkRouteConstants.SKU_ID_NIKE_PARTIAL_CANCEL1, store_id,
                warehouse);
        imscount = imscount - 1;
        Svc createOrderReqGen = silkRouteServiceHelper
                .invokeSilkRouteServiceCreateOrderAPI(environment,OrderReleaseId, OrderEventType, status,
                        Hold, DispatchDate, DispatchAfter, quantity,
                        cancelledQuantity, ListingID, SKU_barcode, price,
                        pincode);

        End2EndHelper.sleep(waitTime);
        SoftAssert sft = new SoftAssert();
        sft.assertEquals(Integer.parseInt(response), createOrderReqGen.getResponseStatus(), "Verify Response Status Code");

        OrderEntry orderEntry = omsServiceHelper.getOrderEntryByStoreOrderID(OrderReleaseId);
        sft.assertTrue(omsServiceHelper.validateReleaseStatusInOMS(orderEntry.getOrderReleases().iterator().next().getId().toString(), "RFR:WP", 15), "Order Status Should be in WP");
        int blockedOrderCount = SilkRouteServiceHelper.getIMSboc(SilkRouteConstants.SKU_ID_NIKE_PARTIAL_CANCEL1, store_id, warehouse);
        sft.assertEquals(blockedOrderCount, imscount, "IMS Inventory count should reduce after FK Item Cancellation");
        sft.assertAll();
    }

    // 17.Partial Cancel a order in packed status
    @Test(groups = {"Smoke", "Sanity", "ProdSanity", "Regression",
            "MiniRegression", "ExhaustiveRegression"}, dataProvider = "SilkrouteServiceDP_PartialCancelOrder_verifyFailureOrderPacked", dataProviderClass = SilkRouteServiceDP.class)
    public void SilkRouteService_PartialCancelOrder_verifyFailureOrderPacked(String environment,
                                                                             String OrderReleaseId, String OrderEventType,
                                                                             String status, String Hold,
                                                                             String DispatchDate, String DispatchAfter, String quantity,
                                                                             String cancelledQuantity, String ListingID, String SKU_barcode,
                                                                             String price, String pincode, String response, String statusMessage) throws IOException, JAXBException, ParseException {
        Svc createOrderReqGen = silkRouteServiceHelper
                .invokeSilkRouteServiceCreateOrderAPI(environment,OrderReleaseId, OrderEventType, status,
                        Hold, DispatchDate, DispatchAfter, quantity,
                        cancelledQuantity, ListingID, SKU_barcode, price,
                        pincode);
        Assert.assertEquals(response, ""+createOrderReqGen.getResponseStatus());
        String orderId = APIUtilities.getElement(createOrderReqGen.getResponseBody(),
                "orderItemId", "json");
        OrderEntry orderEntry= omsServiceHelper.getOrderEntryByStoreOrderID(orderId);

        Assert.assertFalse(omsServiceHelper.validateReleaseStatusInOMS(orderEntry.getOrderReleases().iterator().next().getId().toString(), "F", 15), "Packed Order Should not be cancelled");
    }


    @Test(groups = {"Smoke", "Sanity", "ProdSanity", "Regression",
            "MiniRegression", "ExhaustiveRegression"}, dataProvider = "SilkrouteServiceDP_CancelOrder_verifySuccess_Orderonhold", dataProviderClass = SilkRouteServiceDP.class,
            description = "cancel a ON-HOLD Order")
    // 1.check response
    public void cancelOnHoldOrders(String environment,
                                   String OrderReleaseId, String OrderEventType,
                                   String status, String Hold,
                                   String DispatchDate, String DispatchAfter, String quantity,
                                   String cancelledQuantity, String ListingID, String SKU_barcode,
                                   String price, String pincode, String response, String warehouse,
                                   String store_id) throws IOException, JAXBException, ParseException {

        End2EndHelper.sleep(waitTime);
        int imscount = SilkRouteServiceHelper.getIMSboc(SilkRouteConstants.SKU_ID_NIKE_CANCEL_ORDER, store_id,
                warehouse);

        imscount = imscount - Integer.parseInt(quantity);
        Svc createOrderReqGen = silkRouteServiceHelper
                .invokeSilkRouteServiceCreateOrderAPI(environment,OrderReleaseId, OrderEventType, status,
                        Hold, DispatchDate, DispatchAfter, quantity,
                        cancelledQuantity, ListingID, SKU_barcode, price,
                        pincode);


        SoftAssert sft = new SoftAssert();
        String orderId = APIUtilities.getElement(createOrderReqGen.getResponseBody(), "orderItemId", "json");
        OrderEntry orderEntry = omsServiceHelper.getOrderEntryByStoreOrderID(orderId);
        Assert.assertTrue(omsServiceHelper.validateReleaseStatusInOMS(orderEntry.getOrderReleases().iterator().next().getId().toString(), "F", 15), "Order Status Should be in F");
        End2EndHelper.sleep(waitTime);
        orderEntry = omsServiceHelper.getOrderEntryByStoreOrderID(orderId);
        sft.assertEquals(orderEntry.getOrderReleases().iterator().next().getStatus(),"F", "Order Release status should be F");
        sft.assertEquals(orderEntry.getOrderReleases().iterator().next().getCancellationReasonId().intValue(), 74, "Verify Cancellation Reason ID should be 74");
        int blockedOrderCount = SilkRouteServiceHelper.getIMSboc(SilkRouteConstants.SKU_ID_NIKE_CANCEL_ORDER,
                                                                 store_id, warehouse);
        sft.assertEquals(blockedOrderCount, imscount, "Inventory Count should reduce");
        sft.assertAll();
    }



    @Test(groups = {"Smoke", "Sanity", "ProdSanity", "Regression",
            "MiniRegression", "ExhaustiveRegression"}, dataProvider = "SilkrouteServiceDP_Sla_Changed_verifySuccessResponse", dataProviderClass = SilkRouteServiceDP.class,
            description = "OrderEvent SLA changed")
    public void slaChanged(String environment, String OrderReleaseId,
                           String OrderEventType, String status,
                           String Hold, String DispatchDate, String DispatchAfter,
                           String quantity, String cancelledQuantity, String ListingID,
                           String SKU_barcode, String price, String pincode, String response) throws IOException, JAXBException, ParseException {
        Svc createOrderReqGen = silkRouteServiceHelper
                .invokeSilkRouteServiceCreateOrderAPI(environment,OrderReleaseId, OrderEventType, status,
                        Hold, DispatchDate, DispatchAfter, quantity,
                        cancelledQuantity, ListingID, SKU_barcode, price,
                        pincode);
        String createOrderResponse = createOrderReqGen.getResponseBody();
        Assert.assertEquals(Integer.parseInt(response), createOrderReqGen.getResponseStatus(), "SilkRouteService createOrder API is not working");
    }


    @Test(groups = {"Smoke", "Sanity", "ProdSanity", "Regression",
            "MiniRegression", "ExhaustiveRegression"}, dataProvider = "SilkrouteServiceDP_Sla_Changed_verifySuccessResponse", dataProviderClass = SilkRouteServiceDP.class,
            description = "OrderEvent Shipped passed")
    public void eventShipped(String environment,String OrderReleaseId,
                             String OrderEventType, String status,
                             String Hold, String DispatchDate, String DispatchAfter,
                             String quantity, String cancelledQuantity, String ListingID,
                             String SKU_barcode, String price, String pincode, String response) throws IOException, JAXBException, ParseException {
        Svc createOrderReqGen = silkRouteServiceHelper
                .invokeSilkRouteServiceCreateOrderAPI(environment,OrderReleaseId, OrderEventType, status,
                        Hold, DispatchDate, DispatchAfter, quantity,
                        cancelledQuantity, ListingID, SKU_barcode, price,
                        pincode);

        Assert.assertEquals(Integer.parseInt(response), createOrderReqGen.getResponseStatus(), "Response Should be 200");
    }


    @Test(groups = {"Smoke", "Sanity", "ProdSanity", "Regression",
            "MiniRegression", "ExhaustiveRegression"}, dataProvider = "SilkrouteServiceDP_PickUP_Complete_verifySuccessResponse", dataProviderClass = SilkRouteServiceDP.class,
            description = "OrderEvent pickup complete")
    public void pickUpComplete(String environment,String OrderReleaseId,
                               String OrderEventType, String status,
                               String Hold, String DispatchDate, String DispatchAfter,
                               String quantity, String cancelledQuantity, String ListingID,
                               String SKU_barcode, String price, String pincode, String response) throws IOException, JAXBException, ParseException {
        Svc createOrderReqGen = silkRouteServiceHelper
                .invokeSilkRouteServiceCreateOrderAPI(environment,OrderReleaseId, OrderEventType, status,
                        Hold, DispatchDate, DispatchAfter, quantity,
                        cancelledQuantity, ListingID, SKU_barcode, price,
                        pincode);
        Assert.assertEquals(Integer.parseInt(response), createOrderReqGen.getResponseStatus(), "Response Should be 200");
    }


    @Test(groups = {"Smoke", "Sanity", "ProdSanity", "Regression",
            "MiniRegression", "ExhaustiveRegression"}, dataProvider = "SilkrouteServiceDP_Delivered_verifySuccessResponse", dataProviderClass = SilkRouteServiceDP.class,
            description = "OrderEvent delivered")
    public void eventDelivered(String environment,String OrderReleaseId,
                               String OrderEventType, String status,
                               String Hold, String DispatchDate, String DispatchAfter,
                               String quantity, String cancelledQuantity, String ListingID,
                               String SKU_barcode, String price, String pincode, String response) throws IOException, JAXBException, ParseException {

        Svc createOrderReqGen = silkRouteServiceHelper
                .invokeSilkRouteServiceCreateOrderAPI(environment,OrderReleaseId, OrderEventType, status,
                        Hold, DispatchDate, DispatchAfter, quantity,
                        cancelledQuantity, ListingID, SKU_barcode, price,
                        pincode);
        Assert.assertEquals(createOrderReqGen.getResponseStatus(), Integer.parseInt(response), "Response status is 200");
    }


    @Test(groups = {"Smoke", "Sanity", "ProdSanity", "Regression",
            "MiniRegression", "ExhaustiveRegression"}, dataProvider = "SilkrouteServiceDP_CancelMoreThanOrdered", dataProviderClass = SilkRouteServiceDP.class,
            description = "Cancelled Quantity greater than ordered quantity")
    public void cancelQuantityMoreThanOrderedQuantity(String environment,
                                                      String OrderReleaseId, String OrderEventType,
                                                      String status, String Hold, String DispatchDate,
                                                      String DispatchAfter, String quantity, String cancelledQuantity,
                                                      String ListingID, String SKU_barcode, String price, String pincode,
                                                      String response, String statusMessage) throws IOException, JAXBException, ParseException {
        Svc createOrderReqGen = silkRouteServiceHelper
                .invokeSilkRouteServiceCreateOrderAPI(environment,OrderReleaseId, OrderEventType, status,
                        Hold, DispatchDate, DispatchAfter, quantity,
                        cancelledQuantity, ListingID, SKU_barcode, price,
                        pincode);

        String createOrderResponse = createOrderReqGen.getResponseBody();
        Assert.assertEquals(createOrderReqGen.getResponseStatus(), Integer.parseInt(response), "Verify Response code should be 200");
    }


    @Test(groups = {"Smoke", "Sanity", "ProdSanity", "Regression",
            "MiniRegression", "ExhaustiveRegression"}, dataProvider = "checkOnHoldResolutionInCaseFKAndOOSOnHold", dataProviderClass = SilkRouteServiceDP.class,
            description = "Mark a hold order as unhold when the inventory is OOS")
    public void checkOnHoldResolutionInCaseFKAndOOSOnHold(String environment,
                                                          String OrderReleaseId, String OrderEventType,
                                                          String status, String Hold, String DispatchDate,
                                                          String DispatchAfter, String quantity, String cancelledQuantity,
                                                          String ListingID, String SKU_barcode, String price, String pincode,
                                                          String response, String warehouse, String store_id) throws IOException, JAXBException, ParseException {

        End2EndHelper.sleep(waitTime);
        int imscount = SilkRouteServiceHelper.getIMSboc(SilkRouteConstants.SKU_ID_OOS_ONHOLD, store_id, warehouse);

        Svc createOrderReqGen = silkRouteServiceHelper
                .invokeSilkRouteServiceCreateOrderAPI(environment,OrderReleaseId, OrderEventType, status,
                        Hold, DispatchDate, DispatchAfter, quantity,
                        cancelledQuantity, ListingID, SKU_barcode, price,
                        pincode);
        End2EndHelper.sleep(waitTime);
        SoftAssert sft = new SoftAssert();
        sft.assertEquals(createOrderReqGen.getResponseStatus(), Integer.parseInt(response), "Verify Response code should be 200");
        OrderEntry orderEntry = omsServiceHelper.getOrderEntryByStoreOrderID(OrderReleaseId);
        sft.assertTrue(omsServiceHelper.validateReleaseStatusInOMS(orderEntry.getOrderReleases().iterator().next().getId().toString(), "Q", 15), "Order Status Should be in Q status");
        orderEntry = omsServiceHelper.getOrderEntryByStoreOrderID(OrderReleaseId);
        sft.assertFalse(orderEntry.getOnHold(), "Order should be OFF-HOLD");
        sft.assertTrue(orderEntry.getOrderReleases().iterator().next().getOnHold(), "Order Releases should be ON-HOLD for OOS");
        sft.assertEquals(orderEntry.getOrderReleases().iterator().next().getOnHoldReasonId().intValue(), 34, "Order should go ON-HOLD with Reason ID 38");


        int blockedOrderCount = SilkRouteServiceHelper.getIMSboc(SilkRouteConstants.SKU_ID_OOS_ONHOLD, store_id, warehouse);
        sft.assertEquals(blockedOrderCount, imscount, "Inventory Count should reduce");
        sft.assertAll();
    }


    @Test(groups = {"Smoke", "Sanity", "ProdSanity", "Regression",
            "MiniRegression", "ExhaustiveRegression"}, dataProvider = "createorderForOOSSku", dataProviderClass = SilkRouteServiceDP.class, description = "create a order with inventory 0")
    public void createOrderForOOSSku(String environment,String OrderReleaseId,
                                     String OrderEventType, String status,
                                     String Hold, String DispatchDate, String DispatchAfter,
                                     String quantity, String cancelledQuantity, String ListingID,
                                     String SKU_barcode, String price, String pincode, String response,
                                     String warehouse, String store_id) throws IOException, JAXBException, ParseException {

        End2EndHelper.sleep(waitTime);
        int imscount = SilkRouteServiceHelper.getIMSboc(SilkRouteConstants.SKU_ID_CREATE_ORDER_OOS, store_id, warehouse);

        Svc createOrderReqGen = silkRouteServiceHelper
                .invokeSilkRouteServiceCreateOrderAPI(environment,OrderReleaseId, OrderEventType, status,
                        Hold, DispatchDate, DispatchAfter, quantity,
                        cancelledQuantity, ListingID, SKU_barcode, price,
                        pincode);

        SoftAssert sft = new SoftAssert();
        sft.assertEquals(Integer.parseInt(response), 200);
        End2EndHelper.sleep(waitTime);
        OrderEntry orderEntry = omsServiceHelper.getOrderEntryByStoreOrderID(OrderReleaseId);
        sft.assertTrue(omsServiceHelper.validateReleaseStatusInOMS(orderEntry.getOrderReleases().iterator().next().getId().toString(), "Q", 15), "Order Status Should be in Q status");
        sft.assertFalse(orderEntry.getOnHold(), "Order should be ON-HOLD");
        orderEntry = omsServiceHelper.getOrderEntryByStoreOrderID(OrderReleaseId);
        sft.assertTrue(orderEntry.getOrderReleases().iterator().next().getOnHold(), "Order Releases should be ON-HOLD for OOS");
        sft.assertEquals(orderEntry.getOrderReleases().iterator().next().getOnHoldReasonId().intValue(), 34, "Order should go ON-HOLD with Reason ID 38");

        int blockedOrderCount = SilkRouteServiceHelper.getIMSboc(SilkRouteConstants.SKU_ID_CREATE_ORDER_OOS, store_id, warehouse);
        sft.assertEquals(blockedOrderCount, imscount, "Inventory Count should reduce");
        sft.assertAll();
    }


    @Test(groups = {"Smoke", "Sanity", "ProdSanity", "Regression",
            "MiniRegression", "ExhaustiveRegression"}, dataProvider = "holdOrderForOOSSku", dataProviderClass = SilkRouteServiceDP.class,
            description = "create a ON_HOLD order with inventory 0")
    public void holdOrderForOOSSKU(String environment,String OrderReleaseId,
                                   String OrderEventType, String status,
                                   String Hold, String DispatchDate, String DispatchAfter,
                                   String quantity, String cancelledQuantity, String ListingID,
                                   String SKU_barcode, String price, String pincode, String response,
                                   String warehouse, String store_id) throws IOException, JAXBException, ParseException {

        End2EndHelper.sleep(waitTime);
        int imscount = SilkRouteServiceHelper.getIMSboc(SilkRouteConstants.SKU_ID_HOLD_OOS, store_id, warehouse);

        Svc createOrderReqGen = silkRouteServiceHelper
                .invokeSilkRouteServiceCreateOrderAPI(environment,OrderReleaseId, OrderEventType, status,
                        Hold, DispatchDate, DispatchAfter, quantity,
                        cancelledQuantity, ListingID, SKU_barcode, price,
                        pincode);

        SoftAssert sft = new SoftAssert();
        sft.assertEquals(Integer.parseInt(response), createOrderReqGen.getResponseStatus(), "Response Status Should be "+response);
        OrderEntry orderEntry = omsServiceHelper.getOrderEntryByStoreOrderID(OrderReleaseId);
        sft.assertTrue(omsServiceHelper.validateReleaseStatusInOMS(orderEntry.getOrderReleases().get(0).getId().toString(), "Q", 15), "Order Status Should be in Q status");
        End2EndHelper.sleep(waitTime);
        orderEntry = omsServiceHelper.getOrderEntryByStoreOrderID(OrderReleaseId);
        sft.assertTrue(orderEntry.getOnHold(), "Order should be ON-HOLD");
        sft.assertEquals(orderEntry.getOnHoldReasonId().intValue(), 38, "Order should go ON-HOLD with Reason ID 38");
        sft.assertTrue(orderEntry.getOrderReleases().iterator().next().getOnHold(), "Order Releases should be ON-HOLD for OOS");
        sft.assertEquals(orderEntry.getOrderReleases().iterator().next().getOnHoldReasonId().intValue(), 34, "Order should go ON-HOLD with Reason ID 38");

        int blockedOrderCount = SilkRouteServiceHelper.getIMSboc(SilkRouteConstants.SKU_ID_HOLD_OOS, store_id, warehouse);
        sft.assertEquals(blockedOrderCount, imscount, "Inventory Count should reduce");
        sft.assertAll();
    }


    @Test(groups = {"Smoke", "Sanity", "ProdSanity", "Regression",
            "MiniRegression", "ExhaustiveRegression"}, dataProvider = "updateOrderToHoldForOOSSku", dataProviderClass = SilkRouteServiceDP.class,
            description = "update a order to onhold when the sku is OOS")
    public void updateOrderToHoldForOOSSku(String environment,
                                           String OrderReleaseId, String OrderEventType,
                                           String status, String Hold, String DispatchDate,
                                           String DispatchAfter, String quantity, String cancelledQuantity,
                                           String ListingID, String SKU_barcode, String price, String pincode,
                                           String response, String warehouse, String store_id) throws IOException, JAXBException, ParseException {


        int imscount = SilkRouteServiceHelper.getIMSboc(SilkRouteConstants.SKU_ID_UPDATE_ORDER_TO_HOLD_FOR_OOSSKU, store_id,
                warehouse);
        Svc createOrderReqGen = silkRouteServiceHelper
                .invokeSilkRouteServiceCreateOrderAPI(environment,OrderReleaseId, OrderEventType, status,
                        Hold, DispatchDate, DispatchAfter, quantity,
                        cancelledQuantity, ListingID, SKU_barcode, price,
                        pincode);

        SoftAssert sft = new SoftAssert();
        Assert.assertEquals(Integer.parseInt(response), 200);
        End2EndHelper.sleep(waitTime);
        OrderEntry orderEntry = omsServiceHelper.getOrderEntryByStoreOrderID(OrderReleaseId);
        sft.assertTrue(omsServiceHelper.validateReleaseStatusInOMS(orderEntry.getOrderReleases().iterator().next().getId().toString(), "Q", 15), "Order Status Should be in Q status");
        orderEntry = omsServiceHelper.getOrderEntryByStoreOrderID(OrderReleaseId);
        sft.assertTrue(orderEntry.getOnHold(), "Order should be ON-HOLD");
        sft.assertEquals(orderEntry.getOnHoldReasonId().intValue(), 38, "Order should go ON-HOLD with Reason ID 38");
        sft.assertTrue(orderEntry.getOrderReleases().iterator().next().getOnHold(), "Order Releases should be ON-HOLD for OOS");
        sft.assertEquals(orderEntry.getOrderReleases().iterator().next().getOnHoldReasonId().intValue(), 34, "Order should go ON-HOLD with Reason ID 38");
        int blockedOrderCount = SilkRouteServiceHelper.getIMSboc(SilkRouteConstants.SKU_ID_UPDATE_ORDER_TO_HOLD_FOR_OOSSKU, store_id, warehouse);
        sft.assertEquals(blockedOrderCount, imscount, "Inventory Count should reduce");
        sft.assertAll();
    }

    @Test(groups = {"Smoke", "Sanity", "ProdSanity", "Regression",
            "MiniRegression", "ExhaustiveRegression"}, dataProvider = "mojoInitiatedCancellationMultiQty", dataProviderClass = SilkRouteServiceDP.class,
            description = "Seller initiated cancellation will cancel the whole order")
    public void sellerInitiatedCancellation_multiple_qty(String environment, String OrderReleaseId, String OrderEventType,
                                                         String status, String Hold, String DispatchDate,
                                                         String DispatchAfter, String quantity, String cancelledQuantity,
                                                         String ListingID, String SKU_barcode, String price, String pincode,
                                                         String response, String store_id, String warehouse) throws JAXBException, IOException, ParseException {
        Svc createOrderReqGen = silkRouteServiceHelper
                .invokeSilkRouteServiceCreateOrderAPI(environment,OrderReleaseId, OrderEventType, status,
                        Hold, DispatchDate, DispatchAfter, quantity,
                        cancelledQuantity, ListingID, SKU_barcode, price,
                        pincode);

        String createOrderResponse = createOrderReqGen.getResponseBody();

        End2EndHelper.sleep(waitTime);
        int imscount = SilkRouteServiceHelper.getIMSboc(SilkRouteConstants.SKU_ID_MOJO_INITIATED_CANCELATION, store_id, warehouse);
        imscount = imscount - 2;


        OrderEntry orderEntry = omsServiceHelper.getOrderEntryByStoreOrderID(OrderReleaseId);

        OrderReleaseResponse createOrderReqGenMojocancel = omsServiceHelper.cancelLine(orderEntry.getOrderReleases().iterator().next().getId().toString(), "FLIPKART", new String[]{
                        orderEntry.getOrderReleases().iterator().next().getOrderLines().iterator().next().getId()+ ":" + 2}, 39, false, false, false);

        Assert.assertEquals(createOrderReqGenMojocancel.getStatus().getStatusType(), Type.SUCCESS, "");
        Assert.assertTrue(omsServiceHelper.validateReleaseStatusInOMS(orderEntry.getOrderReleases().iterator().next().getId().toString(), "F", 15),
                          "Order Status Should be in F status");
        End2EndHelper.sleep(waitTime);
        int blockedOrderCount = SilkRouteServiceHelper.getIMSboc(SilkRouteConstants.SKU_ID_MOJO_INITIATED_CANCELATION,
                                                                 store_id, warehouse);
        Assert.assertEquals(blockedOrderCount, imscount, "Inventory Count should reduce");
    }


    @BeforeClass(groups = {"Smoke", "Sanity", "ProdSanity", "Regression",
            "MiniRegression", "ExhaustiveRegression"})
    public void insert_items() {
        String query1 = "update wh_inventory set inventory_count=1000 where sku_id in (66373,90603,66407,24318,1225025,3864,3869,3870,3871,3857,3858,3859,3860) and store_id =1 and warehouse_id in (36,28) ";
        String query2 = "update inventory set inventory_count=2000 where sku_id in (66373,90603,66407,24318,1225025,3864,3869,3870,3871,3857,3858,3859,3860)";
        String query3 = "update wh_inventory set blocked_order_count=100 where sku_code in ('RDTPFOSH79902','PUMATSRM01391','PUMALNGP01005')and store_id in(2,3) and warehouse_id in (36,28)";
        String query4 = "update wh_inventory set inventory_count=10 where sku_code='KNKHTSHT02241'and store_id =1 and warehouse_id in (36,28);";
        DBUtilities.exUpdateQuery(query1, "myntra_ims");
        DBUtilities.exUpdateQuery(query3, "myntra_ims");
        DBUtilities.exUpdateQuery(query2, "myntra_atp");
        DBUtilities.exUpdateQuery(query4, "myntra_ims");
    }

}