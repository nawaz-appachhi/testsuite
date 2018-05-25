package com.myntra.apiTests.erpservices.silkroute.dp;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import javax.xml.bind.JAXBException;

import org.testng.ITestContext;
import org.testng.annotations.DataProvider;
import org.testng.asserts.SoftAssert;

import com.myntra.apiTests.end2end.End2EndHelper;
import com.myntra.apiTests.erpservices.ims.IMSServiceHelper;
import com.myntra.apiTests.erpservices.oms.OMSServiceHelper;
import com.myntra.apiTests.erpservices.silkroute.SilkRouteConstants;
import com.myntra.apiTests.erpservices.silkroute.SilkRouteServiceHelper;
import com.myntra.lordoftherings.Toolbox;
import com.myntra.test.commons.service.Svc;

public class SilkRouteServiceDP {

    static SilkRouteServiceHelper silkRouteServiceHelper = new SilkRouteServiceHelper();
    static OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
    IMSServiceHelper imsServiceHelper = new IMSServiceHelper();

	@DataProvider
	public static Object[][] SilkrouteServiceDP_createOrder_verifyFailureforMissingPincode(
			ITestContext testContext) {
		// orderItemId(releaseid),orderEvent,orderitemid(lineid),Dispatchbydate,"quantity",ListingID,"sku","price","pincode",response,statusCode,statusMessage,statusType,totalCount 
		String randomOrderID1 = getRandomOrderId();
		String randomOrderID2 = getRandomOrderId();

		String[] arr1 = { "flipkart3", randomOrderID1, "order_item_created",
				randomOrderID1, "APPROVED", "false",
                SilkRouteConstants.DISPATCH_DATE, SilkRouteConstants.DISPATCH_DATE, "1", "0",
                SilkRouteConstants.FK_LISTING_ID, SilkRouteConstants.SKU_CODE_NIKE, "200", "",
				"500", "Field Pincode is empty/missing in the input" };
		String[] arr2 = { "flipkart4", randomOrderID2,
				"order_item_created", randomOrderID2, "APPROVED", "false",
                SilkRouteConstants.DISPATCH_DATE, SilkRouteConstants.DISPATCH_DATE, "2", "0",
                SilkRouteConstants.FK_LISTING_ID, SilkRouteConstants.SKU_CODE_NIKE, "100", "",
				"500", "Field Pincode is empty/missing in the input" };
		Object[][] dataSet = new Object[][] { arr1, arr2 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 2, 2);
	}
	
    @DataProvider
    public static Object[][] SilkrouteServiceDP_createOrder_verifySuccessResponse(
            ITestContext testContext) {
        /*String environment, String OrderReleaseId, String OrderEventType,
                String status, String Hold,
                String DispatchDate, String DispatchAfter, String quantity,
                String cancelledQuantity, String ListingID, String SKU_barcode,
                String price, String pincode, String warehouse,
                String store_id*/

        String randomOrderID1 = getRandomOrderId();
        String[] arr1 = { SilkRouteConstants.FK3, randomOrderID1, "order_item_created", "APPROVED", "false",
                SilkRouteConstants.DISPATCH_DATE, SilkRouteConstants.DISPATCH_DATE, "1", "0",
                SilkRouteConstants.FK_LISTING_ID, SilkRouteConstants.SKU_CODE_NIKE_CREATEORDER, "200", "560001", "36", "2" };

        String randomOrderID2 = getRandomOrderId();
        String[] arr2 = { SilkRouteConstants.FK4, randomOrderID2,
                "order_item_created", "APPROVED", "false",
                SilkRouteConstants.DISPATCH_DATE, SilkRouteConstants.DISPATCH_DATE, "1", "0",
                SilkRouteConstants.FK_LISTING_ID, SilkRouteConstants.SKU_CODE_NIKE_CREATEORDER, "200", "560001", "28", "3" };

        String randomOrderID3 = getRandomOrderId();
        String[] arr3 = { SilkRouteConstants.FK5, randomOrderID3,
                "order_item_created", "APPROVED", "false",
                SilkRouteConstants.DISPATCH_DATE, SilkRouteConstants.DISPATCH_DATE, "1", "0",
                SilkRouteConstants.FK_LISTING_ID, SilkRouteConstants.SKU_CODE_NIKE_CREATEORDER, "200", "560001", "81", "4" };

        Object[][] dataSet = new Object[][] { arr1, arr2, arr3 };
        return Toolbox.returnReducedDataSet(dataSet,
                testContext.getIncludedGroups(), 3, 3);
    }

    

    @DataProvider
    public static Object[][] SilkRouteServiceDP_createOrder_verifyFailureForSkuIdMissing(
            ITestContext testContext) {
        String randomOrderID1 = getRandomOrderId();
        String randomOrderID2 = getRandomOrderId();
        String randomOrderID3 = getRandomOrderId();
        String randomOrderID4 = getRandomOrderId();
        String randomOrderID5 = getRandomOrderId();
        String randomOrderID6 = getRandomOrderId();
        String randomOrderID7 = getRandomOrderId();
        String randomOrderID8 = getRandomOrderId();

        String[] arr1 = { SilkRouteConstants.FK3, randomOrderID1, "order_item_created",
                "APPROVED", "false",
                SilkRouteConstants.DISPATCH_DATE, SilkRouteConstants.DISPATCH_DATE, "1", "0",
                SilkRouteConstants.FK_LISTING_ID, "", "200", "560001", "500",
                "Field SKU is empty/missing in the input" };

        String[] arr2 = { SilkRouteConstants.FK4, randomOrderID2,
                "order_item_created", "APPROVED", "false",
                SilkRouteConstants.DISPATCH_DATE, SilkRouteConstants.DISPATCH_DATE, "1", "0",
                SilkRouteConstants.SKU_CODE_NIKE, "", "100", "560001", "500",
                "Field SKU is empty/missing in the input" };

        String[] arr3 = { SilkRouteConstants.FK3, randomOrderID3, "order_item_created", "APPROVED", "false",
                "", "2000-07-18T10:00:00Z", "1", "0",
                SilkRouteConstants.FK_LISTING_ID, SilkRouteConstants.SKU_CODE_NIKE, "200", "560001",
                "500", "Field Dispatched by date is empty/missing in the input" };

        String[] arr4 = { SilkRouteConstants.FK4, randomOrderID4,
                "order_item_created", "APPROVED", "false",
                "", SilkRouteConstants.DISPATCH_DATE, "2", "0",
                SilkRouteConstants.FK_LISTING_ID, SilkRouteConstants.SKU_CODE_NIKE, "100", "560001",
                "500", "Field Dispatched by date is empty/missing in the input" };

        String[] arr5 = { SilkRouteConstants.FK3, randomOrderID5, "order_item_created", "APPROVED", "false",
                SilkRouteConstants.DISPATCH_DATE, SilkRouteConstants.DISPATCH_DATE, "1", "0",
                SilkRouteConstants.FK_LISTING_ID, SilkRouteConstants.SKU_CODE_NIKE, "200", "",
                "500", "Field Pincode is empty/missing in the input" };

        String[] arr6 = { SilkRouteConstants.FK4, randomOrderID6,
                "order_item_created", "APPROVED", "false",
                SilkRouteConstants.DISPATCH_DATE, SilkRouteConstants.DISPATCH_DATE, "2", "0",
                SilkRouteConstants.FK_LISTING_ID, SilkRouteConstants.SKU_CODE_NIKE, "100", "",
                "500", "Field Pincode is empty/missing in the input" };

        String[] arr7 = { SilkRouteConstants.FK3, randomOrderID7, "order_item_created", "APPROVED", "false",
                SilkRouteConstants.DISPATCH_DATE, SilkRouteConstants.DISPATCH_DATE, "0", "0",
                SilkRouteConstants.FK_LISTING_ID, SilkRouteConstants.SKU_CODE_NIKE, "200", "560001",
                "500", "Field Quantity is empty/missing in the input" };

        String[] arr8 = { SilkRouteConstants.FK4, randomOrderID8,
                "order_item_created", "APPROVED", "false",
                SilkRouteConstants.DISPATCH_DATE, SilkRouteConstants.DISPATCH_DATE, "0", "0",
                SilkRouteConstants.FK_LISTING_ID, SilkRouteConstants.SKU_CODE_NIKE, "100", "560001",
                "500", "Field Quantity is empty/missing in the input" };

        String[] arr9 = { SilkRouteConstants.FK3, getRandomOrderId(), "order_item_created", "APPROVED", "false",
                SilkRouteConstants.DISPATCH_DATE, SilkRouteConstants.DISPATCH_DATE, "1", "0",
                SilkRouteConstants.FK_LISTING_ID, SilkRouteConstants.SKU_CODE_NIKE, "",
                "560001", "500", "Field Price is empty/missing in the input" };

        String[] arr10 = { SilkRouteConstants.FK4, getRandomOrderId(),
                "order_item_created", "APPROVED", "false",
                SilkRouteConstants.DISPATCH_DATE, SilkRouteConstants.DISPATCH_DATE, "3", "0",
                SilkRouteConstants.FK_LISTING_ID, SilkRouteConstants.SKU_CODE_NIKE, "",
                "560001", "500", "Field Price is empty/missing in the input" };

        String[] arr11 = { SilkRouteConstants.FK3, getRandomOrderId(), "order_item_created", "APPROVED", "false",
                SilkRouteConstants.DISPATCH_DATE, SilkRouteConstants.DISPATCH_DATE, "1", "0", "",
                SilkRouteConstants.SKU_CODE_NIKE, "200", "560001", "500",
                "Field Listing id is empty/missing in the input" };

        String[] arr12 = { SilkRouteConstants.FK4, getRandomOrderId(),
                "order_item_created", "APPROVED", "false",
                SilkRouteConstants.DISPATCH_DATE, SilkRouteConstants.DISPATCH_DATE, "2", "0", "",
                SilkRouteConstants.SKU_CODE_NIKE, "100", "560001", "500",
                "Field Listing id is empty/missing in the input" };

        String[] arr13 = { SilkRouteConstants.FK3, "NA", "order_item_created", "APPROVED", "false",
                SilkRouteConstants.DISPATCH_DATE, SilkRouteConstants.DISPATCH_DATE, "1", "0",
                SilkRouteConstants.FK_LISTING_ID, SilkRouteConstants.SKU_CODE_NIKE, "200", "560001",
                "500", "Field Order item id is empty/missing in the input" };

        String[] arr14 = { SilkRouteConstants.FK4, "", "order_item_created", "APPROVED", "false",
                SilkRouteConstants.DISPATCH_DATE, SilkRouteConstants.DISPATCH_DATE, "2", "0",
                SilkRouteConstants.FK_LISTING_ID, SilkRouteConstants.SKU_CODE_NIKE, "100", "560001",
                "500", "Field Order item id is empty/missing in the input" };

        String[] arr15 = { SilkRouteConstants.FK3, getRandomOrderId(), "order_item_created", "APPROVED", "false",
                SilkRouteConstants.DISPATCH_DATE, SilkRouteConstants.DISPATCH_DATE, "1", "0",
                SilkRouteConstants.FK_LISTING_ID, "123abclk", "200", "560001",
                "500", "SKU Code(s) retrieved successfully" };

        String[] arr16 = { SilkRouteConstants.FK4, getRandomOrderId(),
                "order_item_created", "APPROVED", "false",
                SilkRouteConstants.DISPATCH_DATE, SilkRouteConstants.DISPATCH_DATE, "2", "0",
                SilkRouteConstants.FK_LISTING_ID, "123abclk", "100", "560001",
                "500", "SKU Code(s) retrieved successfully" };


        String[] arr19 = { SilkRouteConstants.FK3, getRandomOrderId(), "order_item_created", "APPROVED", "false", SilkRouteConstants.DISPATCH_DATE,
                "", "1", "4", SilkRouteConstants.FK_LISTING_ID,
                SilkRouteConstants.SKU_CODE_NIKE, "200", "560001", "500",
                "Field Dispatch After Date is empty/missing in the input" };

        String[] arr20 = { SilkRouteConstants.FK4, getRandomOrderId(), "order_item_created", "APPROVED", "false", SilkRouteConstants.DISPATCH_DATE,
                "", "2", "4", SilkRouteConstants.FK_LISTING_ID,
                SilkRouteConstants.SKU_CODE_NIKE, "100", "560001", "500",
                "Field Dispatch After Date is empty/missing in the input" };

        Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9, arr10, arr11, arr12, arr13, arr14, arr15, arr16, arr19, arr20};
        return Toolbox.returnReducedDataSet(dataSet,
                testContext.getIncludedGroups(), 30, 30);
    }


    @DataProvider
    public static Object[][] SilkrouteServiceDP_createOrder_verifySuccessWithOrderEventHold(
            ITestContext testContext) {
        // orderItemId(releaseid),orderEvent,orderitemid(lineid),Dispatchbydate,"quantity",ListingID,"sku","price","pincode",response,statusCode,statusMessage,statusType,totalCount
        String randomOrderID1 = getRandomOrderId();
        String[] arr1 = { SilkRouteConstants.FK3, randomOrderID1, "order_item_created", "APPROVED", "true", SilkRouteConstants.DISPATCH_DATE,
                SilkRouteConstants.DISPATCH_DATE, "1", "0", SilkRouteConstants.FK_LISTING_ID,
                SilkRouteConstants.SKU_CODE_NIKE_ORDER_EVENT_HOLD, "200", "560001", "200", "36", "2" };

        String randomOrderID2 = getRandomOrderId();
        String[] arr2 = { SilkRouteConstants.FK4, randomOrderID2, "order_item_created", "APPROVED", "true", SilkRouteConstants.DISPATCH_DATE,
                SilkRouteConstants.DISPATCH_DATE, "1", "0", SilkRouteConstants.FK_LISTING_ID,
                SilkRouteConstants.SKU_CODE_NIKE_ORDER_EVENT_HOLD, "200", "560001", "200", "28", "3" };

        String randomOrderID3 = getRandomOrderId();
        String[] arr3 = { SilkRouteConstants.FK5, randomOrderID3, "order_item_created", "APPROVED", "true", SilkRouteConstants.DISPATCH_DATE,
                SilkRouteConstants.DISPATCH_DATE, "1", "0", SilkRouteConstants.FK_LISTING_ID,
                SilkRouteConstants.SKU_CODE_NIKE_ORDER_EVENT_HOLD, "200", "560001", "200", "81", "4" };

        Object[][] dataSet = new Object[][] { arr1, arr2, arr3 };
        return Toolbox.returnReducedDataSet(dataSet,
                testContext.getIncludedGroups(), 3, 3);
    }

    @DataProvider
    public static Object[][] SilkrouteServiceDP_createOrder_verifySuccessWithOrderEventHold1(
            ITestContext testContext) {
        // orderItemId(releaseid),orderEvent,orderitemid(lineid),Dispatchbydate,"quantity",ListingID,"sku","price","pincode",response,statusCode,statusMessage,statusType,totalCount

        SilkRouteServiceHelper silkRouteServiceHelper=new SilkRouteServiceHelper();
        //silkRouteServiceHelper.invokeSilkRouteServiceCreateOrderAPI(fkEnv, OrderReleaseId, OrderEventType, OrderlineId, status, Hold, DispatchDate, DispatchAfterDate, quantity, cancelledQuantity, ListingID, SKU_barcode, price, pincode)

        String randomOrderID1 = getRandomOrderId();
        String[] arr1 = { SilkRouteConstants.FK4, randomOrderID1,
                "order_item_created", randomOrderID1, "APPROVED", "true",
                SilkRouteConstants.DISPATCH_DATE, SilkRouteConstants.DISPATCH_DATE, "1", "0",
                SilkRouteConstants.FK_LISTING_ID, SilkRouteConstants.SKU_CODE_NIKE, "100", "560001",
                "200", "28", "3" };
        Object[][] dataSet = new Object[][] {arr1 };
        return Toolbox.returnReducedDataSet(dataSet,
                testContext.getIncludedGroups(), 2, 2);
    }
    @DataProvider
    public static Object[][] SilkrouteServiceDP_updateOrder_verifySuccessWithOrderEventHold(
            ITestContext testContext) throws IOException, JAXBException, ParseException {

        String randomOrderID1 = getRandomOrderId();
        String randomOrderID2 = getRandomOrderId();

        String[] arr1 = {
                SilkRouteConstants.FK3,
                silkRouteServiceHelper.store_release_id(SilkRouteConstants.FK3,randomOrderID1, "order_item_created", "APPROVED", "false",
                        SilkRouteConstants.DISPATCH_DATE, SilkRouteConstants.DISPATCH_DATE, "1",
                        "0", SilkRouteConstants.FK_LISTING_ID, SilkRouteConstants.SKU_CODE_NIKE,
                        "200", "560001").get(0), "order_item_hold", "APPROVED", "true", SilkRouteConstants.DISPATCH_DATE,
                SilkRouteConstants.DISPATCH_DATE, "1", "0", SilkRouteConstants.FK_LISTING_ID,
                SilkRouteConstants.SKU_CODE_NIKE, "200", "560001", "200"};

        String[] arr2 = {
                SilkRouteConstants.FK4,
                silkRouteServiceHelper.store_release_id(SilkRouteConstants.FK4,randomOrderID2, "order_item_created", "APPROVED", "false",
                        SilkRouteConstants.DISPATCH_DATE, SilkRouteConstants.DISPATCH_DATE, "1",
                        "0", SilkRouteConstants.FK_LISTING_ID, SilkRouteConstants.SKU_CODE_NIKE,
                        "200", "560001").get(0), "order_item_hold", "APPROVED", "false", SilkRouteConstants.DISPATCH_DATE,
                SilkRouteConstants.DISPATCH_DATE, "1", "0", SilkRouteConstants.FK_LISTING_ID,
                SilkRouteConstants.SKU_CODE_7709, "200", "560001", "200"};

        Object[][] dataSet = new Object[][] { arr1, arr2 };
        return Toolbox.returnReducedDataSet(dataSet,
                testContext.getIncludedGroups(), 2, 2);
    }

    // update dispatch after time
    @DataProvider
    public static Object[][] SilkrouteServiceDP_updateDispatchAfterDate(
            ITestContext testContext) throws IOException, JAXBException, ParseException {
        // orderEvent, response
        String randomOrderID1 = getRandomOrderId();
        String randomOrderID2 = getRandomOrderId();

        String[] arr1 = {
                SilkRouteConstants.FK3,
                silkRouteServiceHelper.store_release_id(SilkRouteConstants.FK3,randomOrderID1, "order_item_created", "APPROVED", "false",
                        SilkRouteConstants.DISPATCH_DATE, SilkRouteConstants.DISPATCH_DATE, "1",
                        "0", SilkRouteConstants.FK_LISTING_ID, SilkRouteConstants.SKU_CODE_NIKE,
                        "200", "560001").get(0),
                "order_item_dispatch_dates_changed", randomOrderID1,
                "APPROVED", "true", "2016-07-18T10:00:00Z",
                SilkRouteConstants.DISPATCH_DATE, "1", "0", SilkRouteConstants.FK_LISTING_ID,
                SilkRouteConstants.SKU_CODE_NIKE, "200", "560001", "200", "1", "2" };
        String[] arr2 = {
                SilkRouteConstants.FK4,
                silkRouteServiceHelper.store_release_id(SilkRouteConstants.FK4,randomOrderID2, "order_item_created", "APPROVED", "false",
                        SilkRouteConstants.DISPATCH_DATE, SilkRouteConstants.DISPATCH_DATE, "1",
                        "0", SilkRouteConstants.FK_LISTING_ID, SilkRouteConstants.SKU_CODE_NIKE,
                        "200", "560001").get(0),
                "order_item_dispatch_dates_changed", randomOrderID2,
                "APPROVED", "true", "2014-07-18T10:00:00Z",
                SilkRouteConstants.DISPATCH_DATE, "1", "0", SilkRouteConstants.FK_LISTING_ID,
                SilkRouteConstants.SKU_CODE_7709, "200", "560001", "200", "28", "3" };
        Object[][] dataSet = new Object[][] { arr1, arr2 };
        return Toolbox.returnReducedDataSet(dataSet,
                testContext.getIncludedGroups(), 2, 2);
    }

    @DataProvider
    public static Object[][] SilkrouteServiceDP_updateOrder_verifySuccessWithOrderEventUnHold(
            ITestContext testContext) throws IOException, JAXBException, ParseException {

        String[] arr1 = {
                SilkRouteConstants.FK3,
                silkRouteServiceHelper.store_release_id(SilkRouteConstants.FK3, getRandomOrderId(), "order_item_created",
                        "APPROVED", "true",
                        SilkRouteConstants.DISPATCH_DATE, SilkRouteConstants.DISPATCH_DATE, "1",
                        "0", SilkRouteConstants.FK_LISTING_ID, SilkRouteConstants.SKU_CODE_NIKE,
                        "200", "560001").get(0), "order_item_un_hold", "PACKED", "false", SilkRouteConstants.DISPATCH_DATE,
                SilkRouteConstants.DISPATCH_DATE, "1", "0", SilkRouteConstants.FK_LISTING_ID,
                SilkRouteConstants.SKU_CODE_NIKE, "200", "560001", "200"};

        String[] arr2 = {
                SilkRouteConstants.FK4,
                silkRouteServiceHelper.store_release_id(SilkRouteConstants.FK4, getRandomOrderId(), "order_item_created", "APPROVED", "true",
                        SilkRouteConstants.DISPATCH_DATE, SilkRouteConstants.DISPATCH_DATE, "1",
                        "0", SilkRouteConstants.FK_LISTING_ID, SilkRouteConstants.SKU_CODE_NIKE,
                        "200", "560001").get(0), "order_item_un_hold", "PACKED", "false", SilkRouteConstants.DISPATCH_DATE,
                SilkRouteConstants.DISPATCH_DATE, "1", "0", SilkRouteConstants.FK_LISTING_ID,
                SilkRouteConstants.SKU_CODE_NIKE, "200", "560001", "200"};
        Object[][] dataSet = new Object[][] { arr1, arr2 };
        return Toolbox.returnReducedDataSet(dataSet,
                testContext.getIncludedGroups(), 2, 2);
    }

    @DataProvider
    public static Object[][] SilkRouteServiceDP_updateOrder_MarkOrderHold_inPackedStatus(
            ITestContext testContext) throws ParseException, JAXBException, IOException {

        String storeReleaseID1 = getRandomOrderId();
        String storeReleaseID2 = getRandomOrderId();
        IMSServiceHelper imsServiceHelper = new IMSServiceHelper();
        imsServiceHelper.updateInventory(new String[] {SilkRouteConstants.SKU_ID_UpdateOrderToHoldForOOSSku+":36:100:0", SilkRouteConstants.SKU_ID_UpdateOrderToHoldForOOSSku+":28:100:0"});

        Svc svcFk3 = silkRouteServiceHelper.invokeSilkRouteServiceCreateOrderAPI(SilkRouteConstants.FK3, storeReleaseID1, "order_item_created", "APPROVED", "false",
                SilkRouteConstants.DISPATCH_DATE, SilkRouteConstants.DISPATCH_DATE, "5", "0",
                SilkRouteConstants.FK_LISTING_ID, SilkRouteConstants.SKU_CODE_66373, "200", "560001");
        //Assert.assertEquals(svcFk3.getResponseStatus(), 200);
        Svc svcFk4 = silkRouteServiceHelper.invokeSilkRouteServiceCreateOrderAPI(SilkRouteConstants.FK4, storeReleaseID2, "order_item_created", "APPROVED", "false",
                SilkRouteConstants.DISPATCH_DATE, SilkRouteConstants.DISPATCH_DATE, "5", "0",
                SilkRouteConstants.FK_LISTING_ID, SilkRouteConstants.SKU_CODE_66373, "200", "560001");
        //Assert.assertEquals(svcFk4.getResponseStatus(), 200);



        omsServiceHelper.updateOrderReleaseStatusInDBByStoreReleaseID(storeReleaseID1, "PK");
        omsServiceHelper.updateOrderReleaseStatusInDBByStoreReleaseID(storeReleaseID2, "PK");


        String[] arr1 = { SilkRouteConstants.FK3, storeReleaseID1, "order_item_hold",
                "APPROVED", "true", "2014-07-18T10:00:00Z",
                SilkRouteConstants.DISPATCH_DATE, "1", "0", SilkRouteConstants.FK_LISTING_ID,
                SilkRouteConstants.SKU_CODE_66373, "200", "560001", "200",
                "Order is already packed and/or shipped" };

        String[] arr2 = { SilkRouteConstants.FK4, storeReleaseID2, "order_item_hold",
                "APPROVED", "true", "2014-07-18T10:00:00Z",
                SilkRouteConstants.DISPATCH_DATE, "1", "0", SilkRouteConstants.FK_LISTING_ID,
                SilkRouteConstants.SKU_CODE_66373, "200", "560001", "200",
                "Order is already packed and/or shipped" };


        Object[][] dataSet = new Object[][] { arr1, arr2 };
        return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 2, 2);
    }

    @DataProvider
    public static Object[][] SilkrouteServiceDP_PartialCancelOrder_verifySuccessWithOrderEventHold(
            ITestContext testContext) throws IOException, JAXBException, ParseException {
        String randomOrderID1 = getRandomOrderId();

        String[] arr1 = {
                SilkRouteConstants.FK3,
                silkRouteServiceHelper.store_release_id(SilkRouteConstants.FK3,randomOrderID1, "order_item_created", "APPROVED", "true",
                        SilkRouteConstants.DISPATCH_DATE, SilkRouteConstants.DISPATCH_DATE, "2",
                        "0", SilkRouteConstants.FK_LISTING_ID, SilkRouteConstants.SKU_CODE_NIKE_PARTIAL_CANCEL,
                        "200", "560001").get(0), "order_item_cancelled", "APPROVED", "false",
                SilkRouteConstants.DISPATCH_DATE, SilkRouteConstants.DISPATCH_DATE, "2", "1",
                SilkRouteConstants.FK_LISTING_ID, SilkRouteConstants.SKU_CODE_NIKE_PARTIAL_CANCEL, "200", "560001",
                "200", "36", "2" };

        String randomOrderID2 = getRandomOrderId();
        String[] arr2 = {
                SilkRouteConstants.FK4,
                silkRouteServiceHelper.store_release_id(SilkRouteConstants.FK4,randomOrderID2, "order_item_created", "APPROVED", "true",
                        SilkRouteConstants.DISPATCH_DATE, SilkRouteConstants.DISPATCH_DATE, "2",
                        "0", SilkRouteConstants.FK_LISTING_ID, SilkRouteConstants.SKU_CODE_NIKE_PARTIAL_CANCEL,
                        "200", "560001").get(0), "order_item_cancelled", "APPROVED", "false",
                SilkRouteConstants.DISPATCH_DATE, SilkRouteConstants.DISPATCH_DATE, "2", "1",
                SilkRouteConstants.FK_LISTING_ID, SilkRouteConstants.SKU_CODE_NIKE_PARTIAL_CANCEL, "200", "560001",
                "200", "28", "3" };

        String randomOrderID3 = getRandomOrderId();
        String[] arr3 = {
                SilkRouteConstants.FK5,
                silkRouteServiceHelper.store_release_id(SilkRouteConstants.FK5,randomOrderID3, "order_item_created", "APPROVED", "true",
                        SilkRouteConstants.DISPATCH_DATE, SilkRouteConstants.DISPATCH_DATE, "2",
                        "0", SilkRouteConstants.FK_LISTING_ID, SilkRouteConstants.SKU_CODE_NIKE_PARTIAL_CANCEL,
                        "200", "560001").get(0), "order_item_cancelled", "APPROVED", "false",
                SilkRouteConstants.DISPATCH_DATE, SilkRouteConstants.DISPATCH_DATE, "2", "1",
                SilkRouteConstants.FK_LISTING_ID, SilkRouteConstants.SKU_CODE_NIKE_PARTIAL_CANCEL, "200", "560001",
                "200", "81", "4" };

        Object[][] dataSet = new Object[][] { arr1, arr2, arr3 };
        return Toolbox.returnReducedDataSet(dataSet,
                testContext.getIncludedGroups(), 3, 3);
    }

    @DataProvider
    public static Object[][] SilkrouteServiceDP_PartialCancelOrder_verifySuccess(
            ITestContext testContext) throws IOException, JAXBException, ParseException {
        // orderEvent, response
        String randomOrderID1 = getRandomOrderId();

        String[] arr1 = {
                SilkRouteConstants.FK3,
                silkRouteServiceHelper.store_release_id(SilkRouteConstants.FK3,randomOrderID1, "order_item_created", "APPROVED", "false",SilkRouteConstants.DISPATCH_DATE, SilkRouteConstants.DISPATCH_DATE, "2",
                        "0", SilkRouteConstants.FK_LISTING_ID, SilkRouteConstants.SKU_CODE_NIKE_PARTIAL_CANCEL1,"200", "560001").get(0), "order_item_cancelled", "APPROVED", "false",
                SilkRouteConstants.DISPATCH_DATE, SilkRouteConstants.DISPATCH_DATE, "2", "1",
                SilkRouteConstants.FK_LISTING_ID, SilkRouteConstants.SKU_CODE_NIKE_PARTIAL_CANCEL1, "200", "560001",
                "200", "36", "2" };

        String randomOrderID2 = getRandomOrderId();
        String[] arr2 = {
                SilkRouteConstants.FK4,
                silkRouteServiceHelper.store_release_id(SilkRouteConstants.FK4,randomOrderID2, "order_item_created", "APPROVED", "false",
                        SilkRouteConstants.DISPATCH_DATE, SilkRouteConstants.DISPATCH_DATE, "2",
                        "0", SilkRouteConstants.FK_LISTING_ID, SilkRouteConstants.SKU_CODE_NIKE_PARTIAL_CANCEL1,
                        "200", "560001").get(0), "order_item_cancelled", "APPROVED", "false",
                SilkRouteConstants.DISPATCH_DATE, SilkRouteConstants.DISPATCH_DATE, "2", "1",
                SilkRouteConstants.FK_LISTING_ID, SilkRouteConstants.SKU_CODE_NIKE_PARTIAL_CANCEL1, "200", "560001",
                "200", "28", "3" };

        String randomOrderID3 = getRandomOrderId();
        String[] arr3 = {
                SilkRouteConstants.FK5,
                silkRouteServiceHelper.store_release_id(SilkRouteConstants.FK5,randomOrderID3, "order_item_created", "APPROVED", "false",
                		SilkRouteConstants.DISPATCH_DATE, SilkRouteConstants.DISPATCH_DATE, "2",
                        "0",  SilkRouteConstants.FK_LISTING_ID, SilkRouteConstants.SKU_CODE_NIKE_PARTIAL_CANCEL1,
                        "200", "560001").get(0), "order_item_cancelled", "APPROVED", "false",
                SilkRouteConstants.DISPATCH_DATE, SilkRouteConstants.DISPATCH_DATE, "2", "1",
                SilkRouteConstants.FK_LISTING_ID, SilkRouteConstants.SKU_CODE_NIKE_PARTIAL_CANCEL1, "200", "560001",
                "200", "81", "4" };

        Object[][] dataSet = new Object[][] { arr1, arr2, arr3 };
        return Toolbox.returnReducedDataSet(dataSet,
                testContext.getIncludedGroups(), 3, 3);
    }

    @DataProvider
    public static Object[][] SilkrouteServiceDP_PartialCancelOrder_verifyFailureOrderPacked(
            ITestContext testContext) throws ParseException, JAXBException, IOException {

        String storeReleaseID1 = getRandomOrderId();
        String storeReleaseID2 = getRandomOrderId();
        String storeReleaseID3 = getRandomOrderId();
        SoftAssert sft = new SoftAssert();

        Svc svcFk3 = silkRouteServiceHelper.invokeSilkRouteServiceCreateOrderAPI(SilkRouteConstants.FK3, storeReleaseID1, "order_item_created", "APPROVED", "false",
                SilkRouteConstants.DISPATCH_DATE, SilkRouteConstants.DISPATCH_DATE, "5", "0",
                SilkRouteConstants.FK_LISTING_ID, SilkRouteConstants.SKU_CODE_NIKE, "200", "560001");
        sft.assertEquals(svcFk3.getResponseStatus(), 200);

        Svc svcFk4 = silkRouteServiceHelper.invokeSilkRouteServiceCreateOrderAPI(SilkRouteConstants.FK4, storeReleaseID2, "order_item_created", "APPROVED", "false",
                SilkRouteConstants.DISPATCH_DATE, SilkRouteConstants.DISPATCH_DATE, "5", "0",
                SilkRouteConstants.FK_LISTING_ID, SilkRouteConstants.SKU_CODE_NIKE, "200", "560001");
        sft.assertEquals(svcFk4.getResponseStatus(), 200);
        Svc svcFk5 = silkRouteServiceHelper.invokeSilkRouteServiceCreateOrderAPI(SilkRouteConstants.FK5, storeReleaseID3, "order_item_created", "APPROVED", "false",
                SilkRouteConstants.DISPATCH_DATE, SilkRouteConstants.DISPATCH_DATE, "5", "0",
                SilkRouteConstants.FK_LISTING_ID, SilkRouteConstants.SKU_CODE_NIKE, "200", "560001");
        sft.assertEquals(svcFk5.getResponseStatus(), 200);




        omsServiceHelper.updateOrderReleaseStatusInDBByStoreReleaseID(storeReleaseID1, "PK");
        omsServiceHelper.updateOrderReleaseStatusInDBByStoreReleaseID(storeReleaseID2, "PK");
        omsServiceHelper.updateOrderReleaseStatusInDBByStoreReleaseID(storeReleaseID3, "PK");
        String[] arr1 = { SilkRouteConstants.FK3, storeReleaseID1, "order_item_cancelled", "APPROVED", "false",
                SilkRouteConstants.DISPATCH_DATE, SilkRouteConstants.DISPATCH_DATE, "5", "5",
                SilkRouteConstants.FK_LISTING_ID, SilkRouteConstants.SKU_CODE_NIKE, "200", "560001",
                "200", "Order is already packed and/or shipped" };
        String[] arr2 = { SilkRouteConstants.FK4, storeReleaseID2, "order_item_cancelled", "APPROVED", "false",
                SilkRouteConstants.DISPATCH_DATE, SilkRouteConstants.DISPATCH_DATE, "5", "5",
                SilkRouteConstants.FK_LISTING_ID, SilkRouteConstants.SKU_CODE_NIKE, "200", "560001",
                "200", "Order is already packed and/or shipped" };

        String[] arr3 = { SilkRouteConstants.FK5, storeReleaseID3, "order_item_cancelled", "APPROVED", "false",
                SilkRouteConstants.DISPATCH_DATE, SilkRouteConstants.DISPATCH_DATE, "5", "5",
                SilkRouteConstants.FK_LISTING_ID, SilkRouteConstants.SKU_CODE_NIKE, "200", "560001",
                "200", "Order is already packed and/or shipped" };

        End2EndHelper.sleep(15000);
        Object[][] dataSet = new Object[][] { arr1, arr2, arr3 };
        return Toolbox.returnReducedDataSet(dataSet,
                testContext.getIncludedGroups(), 3, 3);
    }


    @DataProvider
    public static Object[][] SilkrouteServiceDP_cancelOrder_verifySuccessResponse(
            ITestContext testContext) throws IOException, JAXBException, ParseException {
        // orderEvent, response
        String randomOrderID1 = getRandomOrderId();
        String randomOrderID2 = getRandomOrderId();

        String[] arr1 = {
                SilkRouteConstants.FK3,
                silkRouteServiceHelper.store_release_id(
                        SilkRouteConstants.FK3, randomOrderID1, "order_item_created", "APPROVED", "false",
                        SilkRouteConstants.DISPATCH_DATE, SilkRouteConstants.DISPATCH_DATE, "5",
                        "0", SilkRouteConstants.FK_LISTING_ID, SilkRouteConstants.SKU_CODE_NIKE,
                        "200", "560001").get(0), "order_item_cancelled",
                randomOrderID1, "APPROVED", "false",
                SilkRouteConstants.DISPATCH_DATE, SilkRouteConstants.DISPATCH_DATE, "5", "5",
                SilkRouteConstants.FK_LISTING_ID, SilkRouteConstants.SKU_CODE_NIKE, "200", "560001",
                "200", "36", "2" };
        String[] arr2 = {
                SilkRouteConstants.FK4,
                silkRouteServiceHelper.store_release_id(
                        SilkRouteConstants.FK4, randomOrderID2, "order_item_created", "APPROVED", "false",
                        SilkRouteConstants.DISPATCH_DATE, SilkRouteConstants.DISPATCH_DATE, "5",
                        "0", SilkRouteConstants.FK_LISTING_ID, SilkRouteConstants.SKU_CODE_NIKE,
                        "200", "560001").get(0), "order_item_cancelled",
                randomOrderID2, "APPROVED", "false",
                "2014-07-18T10:00:00Z", SilkRouteConstants.DISPATCH_DATE, "5", "5",
                SilkRouteConstants.FK_LISTING_ID, SilkRouteConstants.SKU_CODE_NIKE, "200", "560001",
                "200", "28", "3" };
        Object[][] dataSet = new Object[][] { arr1, arr2 };
        return Toolbox.returnReducedDataSet(dataSet,
                testContext.getIncludedGroups(), 2, 2);
    }

    @DataProvider
    public static Object[][] SilkrouteServiceDP_CancelOrder_verifySuccess_Orderonhold(
            ITestContext testContext) throws IOException, JAXBException, ParseException {

        String randomOrderID1 = getRandomOrderId();
        String randomOrderID2 = getRandomOrderId();

        String[] arr1 = {
                SilkRouteConstants.FK3,
                silkRouteServiceHelper.store_release_id(SilkRouteConstants.FK3,randomOrderID1, "order_item_created", "APPROVED", "true",
                        SilkRouteConstants.DISPATCH_DATE, SilkRouteConstants.DISPATCH_DATE, "2",
                        "0", SilkRouteConstants.FK_LISTING_ID, SilkRouteConstants.SKU_CODE_NIKE_CANCEL_ORDER,
                        "200", "560001").get(0), "order_item_cancelled", "APPROVED", "true",
                SilkRouteConstants.DISPATCH_DATE, SilkRouteConstants.DISPATCH_DATE, "2", "2",
                SilkRouteConstants.FK_LISTING_ID, SilkRouteConstants.SKU_CODE_NIKE_CANCEL_ORDER, "200", "560001",
                "200", "36", "2" };

        String[] arr2 = {
                SilkRouteConstants.FK4,
                silkRouteServiceHelper.store_release_id(SilkRouteConstants.FK4,randomOrderID2, "order_item_created", "APPROVED", "true",
                        SilkRouteConstants.DISPATCH_DATE, SilkRouteConstants.DISPATCH_DATE, "2",
                        "0", SilkRouteConstants.FK_LISTING_ID, SilkRouteConstants.SKU_CODE_NIKE_CANCEL_ORDER,
                        "200", "560001").get(0), "order_item_cancelled", "APPROVED", "false",
                "2014-07-18T10:00:00Z", SilkRouteConstants.DISPATCH_DATE, "2", "2",
                SilkRouteConstants.FK_LISTING_ID, SilkRouteConstants.SKU_CODE_NIKE_CANCEL_ORDER, "200", "560001",
                "200", "28", "3" };

        String[] arr3 = {
                SilkRouteConstants.FK5,
                silkRouteServiceHelper.store_release_id(
                        SilkRouteConstants.FK5, getRandomOrderId(), "order_item_created", "APPROVED", "false",
                        SilkRouteConstants.DISPATCH_DATE, SilkRouteConstants.DISPATCH_DATE, "5",
                        "0", SilkRouteConstants.FK_LISTING_ID, SilkRouteConstants.SKU_CODE_NIKE_CANCEL_ORDER,
                        "200", "560001").get(0), "order_item_cancelled", "APPROVED", "false",
                SilkRouteConstants.DISPATCH_DATE, SilkRouteConstants.DISPATCH_DATE, "5", "5",
                SilkRouteConstants.FK_LISTING_ID, SilkRouteConstants.SKU_CODE_NIKE_CANCEL_ORDER, "200", "560001",
                "200", "81", "4" };

        Object[][] dataSet = new Object[][] { arr1, arr2, arr3 };
        return Toolbox.returnReducedDataSet(dataSet,
                testContext.getIncludedGroups(), 4, 4);
    }

    @DataProvider
    public static Object[][] SilkrouteServiceDP_Shipped_verifySuccessResponse(
            ITestContext testContext) throws IOException, JAXBException, ParseException {
    	String fkOrderId1 = silkRouteServiceHelper.store_release_id(
                SilkRouteConstants.FK3, getRandomOrderId(), "order_item_created", "APPROVED", "false",
                SilkRouteConstants.DISPATCH_DATE, SilkRouteConstants.DISPATCH_DATE, "5",
                "0", SilkRouteConstants.FK_LISTING_ID, SilkRouteConstants.SKU_CODE_NIKE,
                "200", "560001").get(0);
        // orderItemId(releaseid),orderEvent,orderitemid(lineid),Hold,Dispatchbydate,"quantity",cancelledQuantity,ListingID,"sku","price","pincode",response,statusCode,statusMessage,statusType,totalCount
        String[] arr1 = { SilkRouteConstants.FK3,"1503947699" , "order_item_shipped", "APPROVED", "false",
                SilkRouteConstants.DISPATCH_DATE, SilkRouteConstants.DISPATCH_DATE, "1", "0",
                SilkRouteConstants.FK_LISTING_ID, SilkRouteConstants.SKU_CODE_NIKE, "200", "560001",
                "200" };
    	String fkOrderId2 = silkRouteServiceHelper.store_release_id(
                SilkRouteConstants.FK4, getRandomOrderId(), "order_item_created", "APPROVED", "false",
                SilkRouteConstants.DISPATCH_DATE, SilkRouteConstants.DISPATCH_DATE, "5",
                "0", SilkRouteConstants.FK_LISTING_ID, SilkRouteConstants.SKU_CODE_NIKE,
                "200", "560001").get(0);
        String[] arr2 = { SilkRouteConstants.FK4,fkOrderId2, "order_item_shipped",
                 "APPROVED", "false",
                SilkRouteConstants.DISPATCH_DATE, SilkRouteConstants.DISPATCH_DATE, "1", "0",
                SilkRouteConstants.FK_LISTING_ID, SilkRouteConstants.SKU_CODE_NIKE, "100", "560001",
                "200" };
        Object[][] dataSet = new Object[][] { arr1, arr2 };
        return Toolbox.returnReducedDataSet(dataSet,
                testContext.getIncludedGroups(), 2, 2);
    }

    @DataProvider
    public static Object[][] SilkrouteServiceDP_Sla_Changed_verifySuccessResponse(
            ITestContext testContext) throws IOException, JAXBException, ParseException {
       	String fkOrderId1 = silkRouteServiceHelper.store_release_id(
                SilkRouteConstants.FK3, getRandomOrderId(), "order_item_created", "APPROVED", "false",
                SilkRouteConstants.DISPATCH_DATE, SilkRouteConstants.DISPATCH_DATE, "5",
                "0", SilkRouteConstants.FK_LISTING_ID, SilkRouteConstants.SKU_CODE_NIKE,
                "200", "560001").get(0);
       	End2EndHelper.sleep(10000);
        String[] arr1 = { SilkRouteConstants.FK3, fkOrderId1, "order_item_dispatch_dates_changed", "APPROVED", "false",
                SilkRouteConstants.DISPATCH_DATE_SLA, SilkRouteConstants.DISPATCH_DATE_SLA, "1", "0",
                SilkRouteConstants.FK_LISTING_ID, SilkRouteConstants.SKU_CODE_NIKE, "200", "560001",
                "200" };
       	String fkOrderId2 = silkRouteServiceHelper.store_release_id(
                SilkRouteConstants.FK4, getRandomOrderId(), "order_item_created", "APPROVED", "false",
                SilkRouteConstants.DISPATCH_DATE, SilkRouteConstants.DISPATCH_DATE, "5",
                "0", SilkRouteConstants.FK_LISTING_ID, SilkRouteConstants.SKU_CODE_NIKE,
                "200", "560001").get(0);
       	End2EndHelper.sleep(10000);
        String[] arr2 = { SilkRouteConstants.FK4, fkOrderId2, "order_item_dispatch_dates_changed", "APPROVED", "false",
                SilkRouteConstants.DISPATCH_DATE_SLA, SilkRouteConstants.DISPATCH_DATE_SLA, "1", "0",
                SilkRouteConstants.FK_LISTING_ID, SilkRouteConstants.SKU_CODE_NIKE, "100", "560001",
                "200" };
        Object[][] dataSet = new Object[][] { arr1, arr2 };
        return Toolbox.returnReducedDataSet(dataSet,
                testContext.getIncludedGroups(), 2, 2);
    }

    @DataProvider
    public static Object[][] SilkrouteServiceDP_PickUP_Complete_verifySuccessResponse(
            ITestContext testContext) throws IOException, JAXBException, ParseException {
        // orderItemId(releaseid),orderEvent,orderitemid(lineid),Hold,Dispatchbydate,"quantity",cancelledQuantity,ListingID,"sku","price","pincode",response,statusCode,statusMessage,statusType,totalCount
       	String fkOrderId1 = silkRouteServiceHelper.store_release_id(
                SilkRouteConstants.FK3, getRandomOrderId(), "order_item_created", "APPROVED", "false",
                SilkRouteConstants.DISPATCH_DATE, SilkRouteConstants.DISPATCH_DATE, "5",
                "0", SilkRouteConstants.FK_LISTING_ID, SilkRouteConstants.SKU_CODE_NIKE,
                "200", "560001").get(0);
       	
    	String[] arr1 = { SilkRouteConstants.FK3, fkOrderId1, "order_item_pickup_complete", "APPROVED", "false",
                SilkRouteConstants.DISPATCH_DATE, SilkRouteConstants.DISPATCH_DATE, "1", "0",
                SilkRouteConstants.FK_LISTING_ID, SilkRouteConstants.SKU_CODE_NIKE, "200", "560001",
                "200" };
       	String fkOrderId2 = silkRouteServiceHelper.store_release_id(
                SilkRouteConstants.FK4, getRandomOrderId(), "order_item_created", "APPROVED", "false",
                SilkRouteConstants.DISPATCH_DATE, SilkRouteConstants.DISPATCH_DATE, "5",
                "0", SilkRouteConstants.FK_LISTING_ID, SilkRouteConstants.SKU_CODE_NIKE,
                "200", "560001").get(0);
        String[] arr2 = { SilkRouteConstants.FK4, fkOrderId2, "order_item_pickup_complete", "APPROVED", "false",
                SilkRouteConstants.DISPATCH_DATE, SilkRouteConstants.DISPATCH_DATE, "1", "0",
                SilkRouteConstants.FK_LISTING_ID, SilkRouteConstants.SKU_CODE_NIKE, "100", "560001",
                "200" };
        Object[][] dataSet = new Object[][] { arr1, arr2 };
        return Toolbox.returnReducedDataSet(dataSet,
                testContext.getIncludedGroups(), 2, 2);
    }

    @DataProvider
    public static Object[][] SilkrouteServiceDP_Delivered_verifySuccessResponse(
            ITestContext testContext) {
        // orderItemId(releaseid),orderEvent,orderitemid(lineid),Hold,Dispatchbydate,"quantity",cancelledQuantity,ListingID,"sku","price","pincode",response,statusCode,statusMessage,statusType,totalCount
        String[] arr1 = { SilkRouteConstants.FK3, "509", "order_item_delivered", "APPROVED", "false",
                SilkRouteConstants.DISPATCH_DATE, SilkRouteConstants.DISPATCH_DATE, "1", "0",
                SilkRouteConstants.FK_LISTING_ID, SilkRouteConstants.SKU_CODE_NIKE, "200", "560001",
                "200" };
        String[] arr2 = { SilkRouteConstants.FK4, "509", "order_item_delivered", "APPROVED", "false",
                SilkRouteConstants.DISPATCH_DATE, SilkRouteConstants.DISPATCH_DATE, "1", "0",
                SilkRouteConstants.FK_LISTING_ID, SilkRouteConstants.SKU_CODE_NIKE, "100", "560001",
                "200" };
        Object[][] dataSet = new Object[][] { arr1, arr2 };
        return Toolbox.returnReducedDataSet(dataSet,
                testContext.getIncludedGroups(), 2, 2);
    }

    @DataProvider
    public static Object[][] SilkrouteServiceDP_CancelMoreThanOrdered(
            ITestContext testContext) {
        String[] arr1 = { SilkRouteConstants.FK3, "567892307", "order_item_cancelled", "APPROVED", "false",
                SilkRouteConstants.DISPATCH_DATE, SilkRouteConstants.DISPATCH_DATE, "1", "4",
                SilkRouteConstants.FK_LISTING_ID, SilkRouteConstants.SKU_CODE_NIKE, "200", "560001",
                "200",
                "quantity to move is greater than the quantity available for lineId: 200002566" };
        String[] arr2 = { SilkRouteConstants.FK4, "1438266205", "order_item_cancelled", "APPROVED", "false",
                SilkRouteConstants.DISPATCH_DATE, SilkRouteConstants.DISPATCH_DATE, "2", "4",
                SilkRouteConstants.FK_LISTING_ID, SilkRouteConstants.SKU_CODE_NIKE, "100", "560001",
                "200",
                "quantity to move is greater than the quantity available for lineId: 200002566" };
        Object[][] dataSet = new Object[][] { arr1, arr2 };
        return Toolbox.returnReducedDataSet(dataSet,
                testContext.getIncludedGroups(), 2, 2);
    }


    // create a order with DispactchAfterDate null
    @DataProvider
    public static Object[][] OrderwithDispatchAfterDatenull(ITestContext testContext) {
        // orderItemId(releaseid),orderEvent,orderitemid(lineid),Hold,Dispatchbydate,"quantity",cancelledQuantity,ListingID,"sku","price","pincode",response,statusCode,statusMessage,statusType,totalCount
        String[] arr1 = { SilkRouteConstants.FK3, "567892307", "order_item_created", "APPROVED", "false", "NULL",
                SilkRouteConstants.DISPATCH_DATE, "1", "4", SilkRouteConstants.FK_LISTING_ID,
                SilkRouteConstants.SKU_CODE_NIKE, "200", "560001", "500",
                "Field Dispatched by date is empty/missing in the input" };
        String[] arr2 = { SilkRouteConstants.FK4, "1438266205", "order_item_created", "APPROVED", "false", "NULL",
                SilkRouteConstants.DISPATCH_DATE, "2", "4", SilkRouteConstants.FK_LISTING_ID,
                SilkRouteConstants.SKU_CODE_NIKE, "100", "560001", "500",
                "Field Dispatched by date is empty/missing in the input" };
        Object[][] dataSet = new Object[][] { arr1, arr2 };
        return Toolbox.returnReducedDataSet(dataSet,
                testContext.getIncludedGroups(), 2, 2);
    }

    @DataProvider
    public static Object[][] checkOnHoldResolutionInCaseFKAndOOSOnHold(
            ITestContext testContext) throws IOException, JAXBException, ParseException {
        // orderEvent, response
        String randomOrderID1 = getRandomOrderId();
        String randomOrderID2 = getRandomOrderId();

        String[] arr1 = {
                SilkRouteConstants.FK3,
                silkRouteServiceHelper.store_release_id(SilkRouteConstants.FK3,randomOrderID1, "order_item_created", "APPROVED", "true",
                        SilkRouteConstants.DISPATCH_DATE, SilkRouteConstants.DISPATCH_DATE, "1",
                        "0", SilkRouteConstants.FK_LISTING_ID, SilkRouteConstants.SKU_CODE_OOS_ONHOLD,
                        "200", "560001").get(0), "order_item_un_hold", "APPROVED", "false",
                SilkRouteConstants.DISPATCH_DATE, SilkRouteConstants.DISPATCH_DATE, "1", "0",
                SilkRouteConstants.FK_LISTING_ID, SilkRouteConstants.SKU_CODE_OOS_ONHOLD, "200", "560001",
                "200", "36", "2" };

        String[] arr2 = {
                SilkRouteConstants.FK4,
                silkRouteServiceHelper.store_release_id(SilkRouteConstants.FK4,randomOrderID2, "order_item_created", "APPROVED", "true",
                        SilkRouteConstants.DISPATCH_DATE, SilkRouteConstants.DISPATCH_DATE, "1",
                        "0", SilkRouteConstants.FK_LISTING_ID, SilkRouteConstants.SKU_CODE_OOS_ONHOLD,
                        "200", "560001").get(0), "order_item_un_hold", "APPROVED", "false",
                SilkRouteConstants.DISPATCH_DATE, SilkRouteConstants.DISPATCH_DATE, "1", "0",
                SilkRouteConstants.FK_LISTING_ID, SilkRouteConstants.SKU_CODE_OOS_ONHOLD, "200", "560001",
                "200", "28", "3" };

        String[] arr3 = {
                SilkRouteConstants.FK5,
                silkRouteServiceHelper.store_release_id(SilkRouteConstants.FK5,randomOrderID2, "order_item_created", "APPROVED", "true",
                        SilkRouteConstants.DISPATCH_DATE, SilkRouteConstants.DISPATCH_DATE, "1",
                        "0", SilkRouteConstants.FK_LISTING_ID, SilkRouteConstants.SKU_CODE_OOS_ONHOLD,
                        "200", "560001").get(0), "order_item_un_hold", "APPROVED", "false",
                SilkRouteConstants.DISPATCH_DATE, SilkRouteConstants.DISPATCH_DATE, "1", "0",
                SilkRouteConstants.FK_LISTING_ID, SilkRouteConstants.SKU_CODE_OOS_ONHOLD, "200", "560001",
                "200", "81", "4" };

        Object[][] dataSet = new Object[][] { arr1,arr2};
        return Toolbox.returnReducedDataSet(dataSet,
                testContext.getIncludedGroups(), 1, 1);
    }

    @DataProvider
    public static Object[][] createorderForOOSSku(ITestContext testContext) {
        // orderItemId(releaseid),orderEvent,orderitemid(lineid),Hold,Dispatchbydate,"quantity",cancelledQuantity,ListingID,"sku","price","pincode",response,statusCode,statusMessage,statusType,totalCount

        IMSServiceHelper imsServiceHelper = new IMSServiceHelper();
        imsServiceHelper.updateInventory(new String[] {SilkRouteConstants.SKU_ID_CREATE_ORDER_OOS+":36:0:0", SilkRouteConstants.SKU_ID_CREATE_ORDER_OOS+":28:0:0", SilkRouteConstants.SKU_ID_CREATE_ORDER_OOS+":81:0:0"});

        String randomOrderID1 = getRandomOrderId();
        String[] arr1 = { SilkRouteConstants.FK3, randomOrderID1, "order_item_created", "APPROVED", "false",
                SilkRouteConstants.DISPATCH_DATE, SilkRouteConstants.DISPATCH_DATE, "1", "0",
                SilkRouteConstants.FK_LISTING_ID, SilkRouteConstants.SKU_CODE_CREATE_ORDER_OOS, "200", "560001",
                "200", "36", "2" };

        String randomOrderID2 = getRandomOrderId();
        String[] arr2 = { SilkRouteConstants.FK4, randomOrderID2,
                "order_item_created", "APPROVED", "false",
                SilkRouteConstants.DISPATCH_DATE, SilkRouteConstants.DISPATCH_DATE, "1", "0",
                SilkRouteConstants.FK_LISTING_ID, SilkRouteConstants.SKU_CODE_CREATE_ORDER_OOS, "100", "560001",
                "200", "28", "3" };

        String randomOrderID3 = getRandomOrderId();
        String[] arr3 = { SilkRouteConstants.FK5, randomOrderID3,
                "order_item_created", "APPROVED", "false",
                SilkRouteConstants.DISPATCH_DATE, SilkRouteConstants.DISPATCH_DATE, "1", "0",
                SilkRouteConstants.FK_LISTING_ID, SilkRouteConstants.SKU_CODE_CREATE_ORDER_OOS, "100", "560001",
                "200", "81", "4" };

        Object[][] dataSet = new Object[][] { arr1, arr2 };
        return Toolbox.returnReducedDataSet(dataSet,
                testContext.getIncludedGroups(), 2, 2);
    }

    @DataProvider
    public static Object[][] holdOrderForOOSSku(ITestContext testContext) {
        String randomOrderID1 = getRandomOrderId();



        IMSServiceHelper imsServiceHelper = new IMSServiceHelper();
        imsServiceHelper.updateInventory(new String[] {SilkRouteConstants.SKU_ID_HOLD_OOS+":36:0:0", SilkRouteConstants.SKU_ID_HOLD_OOS+":28:0:0", SilkRouteConstants.SKU_ID_HOLD_OOS+":81:0:0"});

        String[] arr1 = { SilkRouteConstants.FK3, randomOrderID1, "order_item_created", "APPROVED", "true", SilkRouteConstants.DISPATCH_DATE,
                SilkRouteConstants.DISPATCH_DATE, "1", "0", SilkRouteConstants.FK_LISTING_ID,
                SilkRouteConstants.SKU_CODE_HOLD_OOS, "200", "560001", "200", "36", "2" };

        String randomOrderID2 = getRandomOrderId();
        String[] arr2 = { SilkRouteConstants.FK4, randomOrderID2,
                "order_item_created", "APPROVED", "true",
                SilkRouteConstants.DISPATCH_DATE, SilkRouteConstants.DISPATCH_DATE, "1", "0",
                SilkRouteConstants.FK_LISTING_ID, SilkRouteConstants.SKU_CODE_HOLD_OOS, "100", "560001",
                "200", "28", "3" };

        String randomOrderID3 = getRandomOrderId();
        String[] arr3 = { SilkRouteConstants.FK5, randomOrderID3,
                "order_item_created", "APPROVED", "true",
                SilkRouteConstants.DISPATCH_DATE, SilkRouteConstants.DISPATCH_DATE, "1", "0",
                SilkRouteConstants.FK_LISTING_ID, SilkRouteConstants.SKU_CODE_HOLD_OOS, "100", "560001",
                "200", "81", "4" };

        Object[][] dataSet = new Object[][] { arr1, arr2, arr3 };
        return Toolbox.returnReducedDataSet(dataSet,
                testContext.getIncludedGroups(), 3, 3);
    }

    @DataProvider
    public static Object[][] updateOrderToHoldForOOSSku(ITestContext testContext) throws IOException, JAXBException, ParseException {
        String randomOrderID1 = getRandomOrderId();



        IMSServiceHelper imsServiceHelper = new IMSServiceHelper();
        imsServiceHelper.updateInventory(new String[] {SilkRouteConstants.SKU_ID_UPDATE_ORDER_TO_HOLD_FOR_OOSSKU+":36:0:0", SilkRouteConstants.SKU_ID_UPDATE_ORDER_TO_HOLD_FOR_OOSSKU+":28:0:0", SilkRouteConstants.SKU_ID_UPDATE_ORDER_TO_HOLD_FOR_OOSSKU+":81:0:0"});

        String[] arr1 = {
                SilkRouteConstants.FK3,
                silkRouteServiceHelper.store_release_id(SilkRouteConstants.FK3,randomOrderID1, "order_item_created", "APPROVED", "false",
                        SilkRouteConstants.DISPATCH_DATE, SilkRouteConstants.DISPATCH_DATE, "1",
                        "0", SilkRouteConstants.FK_LISTING_ID, SilkRouteConstants.SKU_CODE_UPDATE_ORDER_TO_HOLD_FOR_OOSSKU,
                        "200", "560001").get(0), "order_item_hold",
                "APPROVED", "true", SilkRouteConstants.DISPATCH_DATE,
                SilkRouteConstants.DISPATCH_DATE, "1", "0", SilkRouteConstants.FK_LISTING_ID,
                SilkRouteConstants.SKU_CODE_UPDATE_ORDER_TO_HOLD_FOR_OOSSKU, "200", "560001", "200", "36", "2" };

        String randomOrderID2 = getRandomOrderId();

        String[] arr2 = {
                SilkRouteConstants.FK4,
                silkRouteServiceHelper.store_release_id(SilkRouteConstants.FK4,randomOrderID2, "order_item_created", "APPROVED", "false",
                        SilkRouteConstants.DISPATCH_DATE, SilkRouteConstants.DISPATCH_DATE, "1",
                        "0", SilkRouteConstants.FK_LISTING_ID, SilkRouteConstants.SKU_CODE_UPDATE_ORDER_TO_HOLD_FOR_OOSSKU,
                        "200", "560001").get(0), "order_item_hold",
                "APPROVED", "true", SilkRouteConstants.DISPATCH_DATE,
                SilkRouteConstants.DISPATCH_DATE, "1", "0", SilkRouteConstants.FK_LISTING_ID,
                SilkRouteConstants.SKU_CODE_UPDATE_ORDER_TO_HOLD_FOR_OOSSKU, "200", "560001", "200", "28", "3" };

        String randomOrderID3 = getRandomOrderId();

        String[] arr3 = {
                SilkRouteConstants.FK5,
                silkRouteServiceHelper.store_release_id(SilkRouteConstants.FK5,randomOrderID3, "order_item_created", "APPROVED", "false",
                        SilkRouteConstants.DISPATCH_DATE, SilkRouteConstants.DISPATCH_DATE, "1",
                        "0", SilkRouteConstants.FK_LISTING_ID, SilkRouteConstants.SKU_CODE_UPDATE_ORDER_TO_HOLD_FOR_OOSSKU,
                        "200", "560001").get(0), "order_item_hold",
                "APPROVED", "true", SilkRouteConstants.DISPATCH_DATE,
                SilkRouteConstants.DISPATCH_DATE, "1", "0", SilkRouteConstants.FK_LISTING_ID,
                SilkRouteConstants.SKU_CODE_UPDATE_ORDER_TO_HOLD_FOR_OOSSKU, "200", "560001", "200", "81", "4" };

        Object[][] dataSet = new Object[][] { arr1, arr2, arr3 };
        return Toolbox.returnReducedDataSet(dataSet,
                testContext.getIncludedGroups(), 3, 3);
    }

    @DataProvider
    public static Object[][] mojoInitiatedCancellationSingleQty(
            ITestContext testContext) {
        // orderItemId(releaseid),orderEvent,orderitemid(lineid),Hold,Dispatchbydate,"quantity",cancelledQuantity,ListingID,"sku","price","pincode",response,statusCode,statusMessage,statusType,totalCount
        String randomOrderID1 = getRandomOrderId();


        String[] arr1 = { SilkRouteConstants.FK3, randomOrderID1, "order_item_created",
                randomOrderID1, "APPROVED", "false",
                SilkRouteConstants.DISPATCH_DATE, SilkRouteConstants.DISPATCH_DATE, "1", "0",
                SilkRouteConstants.FK_LISTING_ID, SilkRouteConstants.SKU_CODE_NIKE, "200", "560001",
                "200", "2", "36" };
        End2EndHelper.sleep(10L);

        String randomOrderID2 = getRandomOrderId();
        String[] arr2 = { SilkRouteConstants.FK4, randomOrderID2,
                "order_item_created", randomOrderID2, "APPROVED", "false",
                SilkRouteConstants.DISPATCH_DATE, SilkRouteConstants.DISPATCH_DATE, "1", "0",
                SilkRouteConstants.FK_LISTING_ID, SilkRouteConstants.SKU_CODE_NIKE, "200", "560001",
                "200", "3", "28" };
        Object[][] dataSet = new Object[][] { arr1, arr2};

        return Toolbox.returnReducedDataSet(dataSet,
                testContext.getIncludedGroups(), 2, 2);
    }

    @DataProvider
    public static Object[][] mojoInitiatedCancellationMultiQty(ITestContext testContext) {
        String randomOrderID1 = getRandomOrderId();

        String[] arr1 = { SilkRouteConstants.FK3, randomOrderID1, "order_item_created",
                "APPROVED", "false",
                SilkRouteConstants.DISPATCH_DATE, SilkRouteConstants.DISPATCH_DATE, "2", "0",
                SilkRouteConstants.FK_LISTING_ID, SilkRouteConstants.SKU_CODE_MOJO_INITIATED_CANCELATION, "200", "560001",
                "200", "2", "36" };

        String randomOrderID2 = getRandomOrderId();
        String[] arr2 = { SilkRouteConstants.FK4, randomOrderID2,
                "order_item_created", "APPROVED", "false",
                SilkRouteConstants.DISPATCH_DATE, SilkRouteConstants.DISPATCH_DATE, "2", "0",
                SilkRouteConstants.FK_LISTING_ID, SilkRouteConstants.SKU_CODE_MOJO_INITIATED_CANCELATION, "200", "560001",
                "200", "3", "28" };

        String randomOrderID3 = getRandomOrderId();
        String[] arr3 = { SilkRouteConstants.FK5, randomOrderID3,
                "order_item_created", "APPROVED", "false",
                SilkRouteConstants.DISPATCH_DATE, SilkRouteConstants.DISPATCH_DATE, "2", "0",
                "LSTMOBDACGHGSMVG9VSIQLWV5", SilkRouteConstants.SKU_CODE_MOJO_INITIATED_CANCELATION, "200", "560001",
                "200", "4", "81" };

        Object[][] dataSet = new Object[][] { arr1, arr2, arr3 };

        return Toolbox.returnReducedDataSet(dataSet,
                testContext.getIncludedGroups(), 3, 3);
    }


    public static String getRandomOrderId() {
        Random random = new Random();
        long epoch = System.currentTimeMillis() / 1000 + random.nextInt(100);
        String orderId = String.valueOf(epoch);
        return orderId;
    }


    @DataProvider
    public static Object[][] SilkrouteEndToEnd(ITestContext testContext) {
        String randomOrderID1 = getRandomOrderId();
        String randomOrderID2 = getRandomOrderId();
        String[] arr1 = { SilkRouteConstants.FK3, randomOrderID1, "order_item_created",
                randomOrderID1, "APPROVED", "false", "2015-12-8T10:00:00Z",
                "2015-12-10T10:00:00Z", "1", "0", SilkRouteConstants.FK_LISTING_ID,
                SilkRouteConstants.SKU_CODE_NIKE, "200", "560001", "200", "1", "2" };
        String[] arr2 = { SilkRouteConstants.FK4, randomOrderID2,
                "order_item_created", randomOrderID2, "APPROVED", "false",
                "2015-12-8T10:00:00Z", "2015-12-10T10:00:00Z", "1", "0",
                SilkRouteConstants.FK_LISTING_ID, SilkRouteConstants.SKU_CODE_NIKE, "200", "560001",
                "200", "28", "3" };
        Object[][] dataSet = new Object[][] { arr1, arr2 };
        return Toolbox.returnReducedDataSet(dataSet,
                testContext.getIncludedGroups(), 2, 2);
    }
    
    @DataProvider
    public static Object[][] cancelFlipkartOrderDP(
            ITestContext testContext) throws IOException, JAXBException, ParseException {
        String randomOrderID1 = getRandomOrderId();
        String randomOrderID2 = getRandomOrderId();
        
        String[] arr1 = { SilkRouteConstants.FK3,
        		silkRouteServiceHelper.store_release_id(SilkRouteConstants.FK3,randomOrderID1, "order_item_created", "APPROVED", "false",
                        SilkRouteConstants.DISPATCH_DATE, SilkRouteConstants.DISPATCH_DATE, "2",
                        "0", SilkRouteConstants.FK_LISTING_ID, SilkRouteConstants.SKU_CODE_NIKE,
                        "200", "560001").get(0), "order_item_cancelled", "APPROVED", "false",
                SilkRouteConstants.DISPATCH_DATE, SilkRouteConstants.DISPATCH_DATE, "2", "2",
                SilkRouteConstants.FK_LISTING_ID, SilkRouteConstants.SKU_CODE_NIKE, "200", "560001",
                "200",
                "quantity to move is greater than the quantity available for lineId: 200002566" };
        String[] arr2 = { SilkRouteConstants.FK4,
        		silkRouteServiceHelper.store_release_id(SilkRouteConstants.FK4,randomOrderID2, "order_item_created", "APPROVED", "false",
                        SilkRouteConstants.DISPATCH_DATE, SilkRouteConstants.DISPATCH_DATE, "2",
                        "0", SilkRouteConstants.FK_LISTING_ID, SilkRouteConstants.SKU_CODE_NIKE,
                        "200", "560001").get(0)
        		, "order_item_cancelled", "APPROVED", "false",
                SilkRouteConstants.DISPATCH_DATE, SilkRouteConstants.DISPATCH_DATE, "2", "2",
                SilkRouteConstants.FK_LISTING_ID, SilkRouteConstants.SKU_CODE_NIKE, "100", "560001",
                "200",
                "quantity to move is greater than the quantity available for lineId: 200002566" };
        Object[][] dataSet = new Object[][] { arr1, arr2 };
        return Toolbox.returnReducedDataSet(dataSet,
                testContext.getIncludedGroups(), 2, 2);
    }



    @DataProvider
    public static Object[][] jabong_CreateOrder(
            ITestContext testContext) {
	    String ldt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String ldt3 = LocalDateTime.now().plusDays(3).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        String randomOrderID1 = getRandomOrderId();
        String randomOrderID2 = getRandomOrderId();
        String randomOrderID3 = getRandomOrderId();
        String randomOrderID4 = getRandomOrderId();
        String randomOrderID5 = getRandomOrderId();

        //"attributeList" - "String itemID, double itemMRP, double itemAmountBeforeTax, int quantity, String skuID, String supplyType"
        /*String[] attributeList, double totalAmount, String shippingPincode, String processingStartDate, String processingEndDate, String type*/

        Object[] arr1 = {new String[] {randomOrderID1+":"+"400.01:+500.01:1:"+SilkRouteConstants.SKU_ID_JABONG_CREATEORDER+":JUST_IN_TIME:"+SilkRouteConstants.SELLER_ID_JABONG}, 500.01, "560068", ldt, ldt3, "normal", ""};
        Object[] arr2 = {new String[] {randomOrderID2+":"+"400.01:+500.01:1:"+SilkRouteConstants.SKU_ID_JABONG_CREATEORDER+":JUST_IN_TIME:"+SilkRouteConstants.SELLER_ID_JABONG}, 500.01, "560068", ldt, ldt3, "", ""}; //Type is missing
        Object[] arr3 = {new String[] {randomOrderID3+":"+"400.01:+500.01:1:"+SilkRouteConstants.SKU_ID_JABONG_CREATEORDER+":JUST_IN_TIME:"+SilkRouteConstants.SELLER_ID_JABONG}, 500.01, "", ldt, ldt3, "normal", ""}; //Shipping Pin code is missing
        Object[] arr4 = {new String[] {randomOrderID4+":"+"400.01:+500.01:1:"+SilkRouteConstants.SKU_ID_JABONG_CREATEORDER+":JUST_IN_TIME:"+SilkRouteConstants.SELLER_ID_JABONG}, 500.01, "560068", "", ldt3, "normal", ""}; //Processing StartDate is missing
        Object[] arr5 = {new String[] {randomOrderID5+":"+"400.01:+500.01:1:"+SilkRouteConstants.SKU_ID_JABONG_CREATEORDER+":JUST_IN_TIME:"+SilkRouteConstants.SELLER_ID_JABONG}, 500.01, "560068", ldt3, "", "normal", ""}; //Processing EndDate is missing
        Object[] arr6 = {new String[] {"\"\":"+"400.01:+500.01:1:"+SilkRouteConstants.SKU_ID_JABONG_CREATEORDER+":JUST_IN_TIME:"+SilkRouteConstants.SELLER_ID_JABONG}, 500.01, "560068", ldt, ldt, "", ""}; //Item ID is Missing
        Object[] arr7 = {new String[] {getRandomOrderId()+":"+"400.01:+500.01:1:"+SilkRouteConstants.SKU_ID_JABONG_CREATEORDER+":JUST_IN_TIME:"+SilkRouteConstants.SELLER_ID_JABONG}, 500.01, "560068", ldt, ldt3, "normal", ""}; //SkuID is Missing
        Object[] arr8 = {new String[] {getRandomOrderId()+":"+"400.01:+500.01:1:"+SilkRouteConstants.SKU_ID_JABONG_CREATEORDER+":JUST_IN_TIME:"+SilkRouteConstants.SELLER_ID_JABONG}, 500.01, "560068", ldt, ldt3, "normal", ""}; //Quantity is Missing
        Object[] arr9 = {new String[] {getRandomOrderId()+":"+"400.01:+500.01:1:"+SilkRouteConstants.SKU_ID_JABONG_CREATEORDER+":\"\":"+SilkRouteConstants.SELLER_ID_JABONG}, 500.01, "560068", ldt, ldt3, "normal", ""}; //SupplyType is Missing
        Object[] arr10 = {new String[] {getRandomOrderId()+":"+"400.01:+500.01:1:"+SilkRouteConstants.SKU_ID_JABONG_CREATEORDER+":JUST_IN_TIME:"+SilkRouteConstants.SELLER_ID_JABONG}, 500.01, "560068", ldt, ldt3, "normal", ""}; //Item MRP is Missing
        Object[] arr11 = {new String[] {getRandomOrderId()+":"+"400.01:+500.01:1:"+SilkRouteConstants.SKU_ID_JABONG_CREATEORDER+":JUST_IN_TIME:"+SilkRouteConstants.SELLER_ID_JABONG}, 500.01, "560068", ldt, ldt3, "normal", ""}; //Item MRP Before Tax is Missing
        Object[] arr12 = {new String[] {getRandomOrderId()+":"+"400.01:+500.01:1:"+SilkRouteConstants.SKU_ID_JABONG_CREATEORDER+":JUST_IN_TIME:"+SilkRouteConstants.SELLER_ID_JABONG}, 500.01, "560068", ldt, ldt3, "normal", ""}; //Item MRP Before Tax is More than Item MRP is Missing
        Object[] arr13 = {new String[] {getRandomOrderId()+":"+"400.01:+500.01:1:"+SilkRouteConstants.SKU_ID_JABONG_CREATEORDER+":JUST_IN_TIME:"+SilkRouteConstants.SELLER_ID_JABONG}, 500.01, "560068", ldt, ldt3, "normal", ""}; //Seller ID  missing
        Object[] arr14 = {new String[] {getRandomOrderId()+":"+"400.01:+500.01:1:"+SilkRouteConstants.SKU_ID_JABONG_CREATEORDER+":JUST_IN_TIME:"+SilkRouteConstants.SELLER_ID_JABONG}, 500.01, "560068", ldt, ldt3, "normal", ""}; //Quantity is more than one


        Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr5, arr6, arr7, arr8, arr9, arr10, arr11, arr12, arr13};
        return Toolbox.returnReducedDataSet(dataSet,
                testContext.getIncludedGroups(), 13, 13);
    }
    @DataProvider
    public static Object[][] singleItemReleaseTestForJabong(ITestContext testContext) {
	    String ldt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String ldt3 = LocalDateTime.now().plusDays(3).format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String randomOrderID1 = getRandomOrderId();
        Object[] arr1 = {new String[] {randomOrderID1+":"+"400.0:+500.01:2:"+SilkRouteConstants.SKU_ID_JABONG_CREATEORDER+":JUST_IN_TIME:"+SilkRouteConstants.SELLER_ID_JABONG}, 500.01, "560068", ldt, ldt3, "normal", ""};
        Object[][] dataSet = new Object[][] {arr1};
        return dataSet;
    }
    @DataProvider
    public static Object[][] singleItemReleaseTestForFlipkart(ITestContext testContext) throws UnsupportedOperationException, IOException {
        String randomOrderID1 = silkRouteServiceHelper.getOrderItemId(SilkRouteConstants.FK_LISTING_ID,1);
        String randomOrderID2 = silkRouteServiceHelper.getOrderItemId(SilkRouteConstants.FK_LISTING_ID,2);
        String[] arr1 = { SilkRouteConstants.FK3, randomOrderID1, "order_item_created", "APPROVED", "false",
                SilkRouteConstants.DISPATCH_DATE, SilkRouteConstants.DISPATCH_DATE, "1", "0",
                SilkRouteConstants.FK_LISTING_ID, SilkRouteConstants.SKU_CODE_NIKE_CREATEORDER1, "200", "560001", "36", "2" };
        String[] arr2 = { SilkRouteConstants.FK3, randomOrderID2, "order_item_created", "APPROVED", "false",
                SilkRouteConstants.DISPATCH_DATE, SilkRouteConstants.DISPATCH_DATE, "2", "0",
                SilkRouteConstants.FK_LISTING_ID, SilkRouteConstants.SKU_CODE_NIKE_CREATEORDER1, "200", "560001", "36", "2" };
        Object[][] dataSet = new Object[][] { arr1,arr2};
        return dataSet;
    }


}