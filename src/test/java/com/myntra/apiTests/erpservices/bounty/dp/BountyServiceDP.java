package com.myntra.apiTests.erpservices.bounty.dp;

import com.myntra.absolut.cart.client.response.entry.CartEntry;
import com.myntra.apiTests.end2end.End2EndHelper;
import com.myntra.apiTests.erpservices.bounty.Test.BountyTestcasesConstants;
import com.myntra.apiTests.erpservices.bounty.BountyServiceHelper;
import com.myntra.bounty.client.response.OrderCreationResponse;
import com.myntra.commons.codes.StatusResponse;
import com.myntra.lordoftherings.Toolbox;
import com.myntra.lordoftherings.boromir.DBUtilities;
import com.myntra.oms.client.response.OrderResponse;

import edu.emory.mathcs.backport.java.util.Arrays;

import org.apache.commons.lang3.RandomStringUtils;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jettison.json.JSONException;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import javax.xml.bind.JAXBException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Random;

/**
 * @author santwana.samantray
 *
 */
public class BountyServiceDP {

    static BountyServiceHelper bountyServiceHelper = new BountyServiceHelper();

    @DataProvider
    public static Object[][] queueAlreadyQueuedOrderDP(ITestContext testContext) throws IOException, JAXBException {
        //long orderID, String sessionID, String login, String cartContext, String paymentStatus, boolean isOnHold
        String login = BountyTestcasesConstants.BountyServiceTest.LOGIN_URL;
        String skuId1[] = {BountyTestcasesConstants.BountyServiceTest.queueAlreadyQueuedOrderDP_ITEM1+":2"};

        HashMap<String, Object> orderEntry1 = bountyServiceHelper.getCreateOrderEntry(login, BountyTestcasesConstants.BountyServiceTest.PASSWORD, skuId1, "ADD","", "200", "50", "");
        OrderResponse orderResponse1 = bountyServiceHelper.createOrder((CartEntry) orderEntry1.get("cart"), "12345", login, ""+orderEntry1.get("xid"), "0", "0", "0.0", BountyTestcasesConstants.Pincodes.PINCODE_560068,
                                                                       "responsive", "DEFAULT", "on");

        OrderCreationResponse orderQueueResponse1 = bountyServiceHelper.queueOrder(orderResponse1.getData().get(0).getStoreOrderId(), ""+orderEntry1.get("xid"), login, "DEFAULT", "SUCCESS", false);
        Object[] arr1 = {orderResponse1.getData().get(0).getStoreOrderId(), ""+orderEntry1.get("xid"), login, "DEFAULT", "SUCCESS", false };
        Object[] arr2 = {orderResponse1.getData().get(0).getStoreOrderId(), ""+orderEntry1.get("xid"), login, "DEFAULT", "SUCCESS", true };


        HashMap<String, Object> orderEntry2 = bountyServiceHelper.getCreateOrderEntry(login, BountyTestcasesConstants.BountyServiceTest.PASSWORD, skuId1, "ADD","", "200", "50", "");
        OrderResponse orderResponse2 = bountyServiceHelper.createOrder((CartEntry) orderEntry2.get("cart"), "12345", login, ""+orderEntry2.get("xid"), "0", "0", "0.0", BountyTestcasesConstants.Pincodes.PINCODE_560068,
                                                                       "responsive", "DEFAULT", "cod");

        OrderCreationResponse orderQueueResponse2 = bountyServiceHelper.queueOrder(orderResponse2.getData().get(0).getStoreOrderId(), ""+orderEntry2.get("xid"), login, "DEFAULT", "SUCCESS", true);

        Object[] arr3 = {orderResponse2.getData().get(0).getStoreOrderId(), ""+orderEntry2.get("xid"), login, "DEFAULT", "SUCCESS", true };
        Object[] arr4 = {orderResponse2.getData().get(0).getStoreOrderId(), ""+orderEntry2.get("xid"), login, "DEFAULT", "SUCCESS", false };

        HashMap<String, Object> orderEntry3 = bountyServiceHelper.getCreateOrderEntry(login, BountyTestcasesConstants.BountyServiceTest.PASSWORD, skuId1, "ADD","", "200", "50", "");
        OrderResponse orderResponse3 = bountyServiceHelper.createOrder((CartEntry) orderEntry3.get("cart"), "12345", login, ""+orderEntry3.get("xid"), "0", "0", "0.0", BountyTestcasesConstants.Pincodes.PINCODE_560068,
                                                                       "responsive", "DEFAULT", "cod");

        OrderCreationResponse orderQueueResponse3 = bountyServiceHelper.queueOrder(orderResponse3.getData().get(0).getStoreOrderId(), ""+orderEntry3.get("xid"), login, "DEFAULT", "SUCCESS", true);

        Object[] arr5 = {orderResponse3.getData().get(0).getStoreOrderId(), ""+orderEntry3.get("xid"), login, "DEFAULT", "SUCCESS", false };
        Object[] arr6 = {orderResponse3.getData().get(0).getStoreOrderId(), ""+orderEntry3.get("xid"), login, "DEFAULT", "SUCCESS", true };


        Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6 };
        return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 6, 6);
    }

    @DataProvider
    public static Object[][] confirmOrderFailedDP(ITestContext testContext) throws IOException, JAXBException {
        //long orderID, String sessionID, String login, String cartContext, String paymentStatus, boolean isOnHold
        String login = BountyTestcasesConstants.BountyServiceTest.LOGIN_URL;
        String skuId1[] = {BountyTestcasesConstants.BountyServiceTest.confirmOrderFailedDP_ITEM1+":2"};

        HashMap<String, Object> orderEntry1 = bountyServiceHelper.getCreateOrderEntry(login, BountyTestcasesConstants.BountyServiceTest.PASSWORD, skuId1, "ADD","", "200", "50", "");
        OrderResponse orderResponse1 = bountyServiceHelper.createOrder((CartEntry) orderEntry1.get("cart"), "12345", login, ""+orderEntry1.get("xid"), "0", "0", "0.0", BountyTestcasesConstants.Pincodes.PINCODE_560068,
                                                                       "responsive", "DEFAULT", "on");
        String orderID = orderResponse1.getData().get(0).getStoreOrderId();

        OrderCreationResponse orderQueueResponse = bountyServiceHelper.queueOrder(orderID, ""+orderEntry1.get("xid"), login, "DEFAULT", "SUCCESS", true);
        Assert.assertEquals(orderQueueResponse.getStatus().getStatusType(), StatusResponse.Type.SUCCESS);

        OrderCreationResponse orderConfirmationResponse = bountyServiceHelper.confirmOnHoldOrder(orderID,"SUCCESS",BountyTestcasesConstants.BountyServiceTest.PASSWORD,false);
        Assert.assertEquals(orderConfirmationResponse.getStatus().getStatusType(), StatusResponse.Type.SUCCESS);

        Object[] arr1 = {orderID,"SUCCESS",BountyTestcasesConstants.BountyServiceTest.PASSWORD, false};
        Object[] arr2 = {orderID,"SUCCESS",BountyTestcasesConstants.BountyServiceTest.PASSWORD, true};

        Object[][] dataSet = new Object[][] { arr1, arr2 };
        return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 6, 6);
    }

    @DataProvider
    public static Object[][] declineOrderSuccessDP(ITestContext testContext) throws IOException, JAXBException {
        String login = BountyTestcasesConstants.BountyServiceTest.LOGIN_URL;
        String skuId1[] = {BountyTestcasesConstants.BountyServiceTest.declineOrderSuccessDP_ITEM1+":2"};

        HashMap<String, Object> orderEntry1 = bountyServiceHelper.getCreateOrderEntry(login, BountyTestcasesConstants.BountyServiceTest.PASSWORD, skuId1, "ADD","", "200", "50", "");
        OrderResponse orderResponse1 = bountyServiceHelper.createOrder((CartEntry) orderEntry1.get("cart"), "12345", login, ""+orderEntry1.get("xid"), "0", "0", "0.0", BountyTestcasesConstants.Pincodes.PINCODE_560068,
                                                                       "responsive", "DEFAULT", "cod");
        String orderID = orderResponse1.getData().get(0).getStoreOrderId();

        OrderCreationResponse orderQueueResponse = bountyServiceHelper.queueOrder(orderID, ""+orderEntry1.get("xid"), login, "DEFAULT", "SUCCESS", true);
        Assert.assertEquals(orderQueueResponse.getStatus().getStatusType(), StatusResponse.Type.SUCCESS);

        HashMap<String, Object> orderEntry2 = bountyServiceHelper.getCreateOrderEntry(login, BountyTestcasesConstants.BountyServiceTest.PASSWORD, skuId1, "ADD","", "200", "50", "");
        OrderResponse orderResponse2 = bountyServiceHelper.createOrder((CartEntry) orderEntry2.get("cart"), "12345", login, ""+orderEntry2.get("xid"), "0", "0", "0.0", BountyTestcasesConstants.Pincodes.PINCODE_560068,
                                                                       "responsive", "DEFAULT", "on");
        String orderID2 = orderResponse2.getData().get(0).getStoreOrderId();

        OrderCreationResponse orderQueueResponse2 = bountyServiceHelper.queueOrder(orderID2, ""+orderEntry2.get("xid"), login, "DEFAULT", "SUCCESS", true);
        Assert.assertEquals(orderQueueResponse2.getStatus().getStatusType(), StatusResponse.Type.SUCCESS);

        Object[] arr1 = {orderID, login, ""+orderEntry1.get("xid"), "DEFAULT", "FAILURE", 2, false};
        Object[] arr2 = {orderID2, login, ""+orderEntry2.get("xid"), "DEFAULT", "FAILURE", 69, false};

        Object[][] dataSet = new Object[][] { arr1, arr2 };
        return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 6, 6);
    }
    
    @DataProvider
    public static Object[][] declineOrderSuccessFromPPStatus(ITestContext testContext) throws IOException, JAXBException {
        String login = BountyTestcasesConstants.BountyServiceTest.LOGIN_URL;
        String skuId1[] = {BountyTestcasesConstants.BountyServiceTest.declineOrderSuccessFromPPStatus_ITEM1+":2"};

        HashMap<String, Object> orderEntry1 = bountyServiceHelper.getCreateOrderEntry(login, BountyTestcasesConstants.BountyServiceTest.PASSWORD, skuId1, "ADD","", "200", "50", "");
        OrderResponse orderResponse1 = bountyServiceHelper.createOrder((CartEntry) orderEntry1.get("cart"), "12345", login, ""+orderEntry1.get("xid"), "0", "0", "0.0", BountyTestcasesConstants.Pincodes.PINCODE_560068,
                                                                       "responsive", "DEFAULT", "on");
        long orderID = orderResponse1.getData().get(0).getId();

     //   OrderCreationResponse orderQueueResponse = bountyServiceHelper.queueOrder(orderID, ""+orderEntry1.get("xid"), login, "DEFAULT", "SUCCESS", true);
     //   Assert.assertEquals(orderQueueResponse.getStatus().getStatusType(), StatusResponse.Type.SUCCESS);

        HashMap<String, Object> orderEntry2 = bountyServiceHelper.getCreateOrderEntry(login, BountyTestcasesConstants.BountyServiceTest.PASSWORD, skuId1, "ADD","", "200", "50", "");
        OrderResponse orderResponse2 = bountyServiceHelper.createOrder((CartEntry) orderEntry2.get("cart"), "12345", login, ""+orderEntry2.get("xid"), "0", "0", "0.0", BountyTestcasesConstants.Pincodes.PINCODE_560068,
                                                                       "responsive", "DEFAULT", "on");
        long orderID2 = orderResponse2.getData().get(0).getId();

     //   OrderCreationResponse orderQueueResponse2 = bountyServiceHelper.queueOrder(orderID, ""+orderEntry2.get("xid"), login, "DEFAULT", "SUCCESS", true);
     //   Assert.assertEquals(orderQueueResponse2.getStatus().getStatusType(), StatusResponse.Type.SUCCESS);

        Object[] arr1 = {orderID, login, ""+orderEntry1.get("xid"), "FAILURE", 2, false};
        Object[] arr2 = {orderID2, login, ""+orderEntry2.get("xid"), "FAILURE", 69, false};

        Object[][] dataSet = new Object[][] { arr1,arr2};
        return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 6, 6);
    }
    
    @DataProvider
    public static Object[][] declineOrderSuccessFromPVStatus(ITestContext testContext) throws IOException, JAXBException {
        String login = BountyTestcasesConstants.BountyServiceTest.LOGIN_URL;
        String skuId1[] = {BountyTestcasesConstants.BountyServiceTest.declineOrderSuccessFromPVStatus_ITEM1+":2"};

        HashMap<String, Object> orderEntry1 = bountyServiceHelper.getCreateOrderEntry(login, BountyTestcasesConstants.BountyServiceTest.PASSWORD, skuId1, "ADD","", "200", "50", "");
        OrderResponse orderResponse1 = bountyServiceHelper.createOrder((CartEntry) orderEntry1.get("cart"), "12345", login, ""+orderEntry1.get("xid"), "0", "0", "0.0", BountyTestcasesConstants.Pincodes.PINCODE_560068,
                                                                       "responsive", "DEFAULT", "cod");
        long orderID = orderResponse1.getData().get(0).getId();

     //   OrderCreationResponse orderQueueResponse = bountyServiceHelper.queueOrder(orderID, ""+orderEntry1.get("xid"), login, "DEFAULT", "SUCCESS", true);
    //    Assert.assertEquals(orderQueueResponse.getStatus().getStatusType(), StatusResponse.Type.SUCCESS);
        
        HashMap<String, Object> orderEntry2 = bountyServiceHelper.getCreateOrderEntry(login, BountyTestcasesConstants.BountyServiceTest.PASSWORD, skuId1, "ADD","", "200", "50", "");
        OrderResponse orderResponse2 = bountyServiceHelper.createOrder((CartEntry) orderEntry2.get("cart"), "12345", login, ""+orderEntry2.get("xid"), "0", "0", "0.0", BountyTestcasesConstants.Pincodes.PINCODE_560068,
                                                                       "responsive", "DEFAULT", "cod");
        long orderID2 = orderResponse2.getData().get(0).getId();

     //   OrderCreationResponse orderQueueResponse2 = bountyServiceHelper.queueOrder(orderID, ""+orderEntry2.get("xid"), login, "DEFAULT", "SUCCESS", true);
     //   Assert.assertEquals(orderQueueResponse2.getStatus().getStatusType(), StatusResponse.Type.SUCCESS);

        
        Object[] arr1 = {orderID, login, ""+orderEntry1.get("xid"), "FAILURE", 2, false};
        Object[] arr2 = {orderID2, login, ""+orderEntry2.get("xid"), "FAILURE", 69, false};

        Object[][] dataSet = new Object[][] { arr1,arr2};
        return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 6, 6);
    }
    
    

    @DataProvider
    public static Object[][] declineOrderSuccessFromPPStatusDP(ITestContext testContext) throws IOException, JAXBException {
        String login = BountyTestcasesConstants.BountyServiceTest.LOGIN_URL;
        String skuId1[] = {BountyTestcasesConstants.BountyServiceTest.declineOrderSuccessFromPPStatusDP_ITEM1+":2"};

        HashMap<String, Object> orderEntry1 = bountyServiceHelper.getCreateOrderEntry(login, BountyTestcasesConstants.BountyServiceTest.PASSWORD, skuId1, "ADD","", "200", "50", "");
        OrderResponse orderResponse1 = bountyServiceHelper.createOrder((CartEntry) orderEntry1.get("cart"), "12345", login, ""+orderEntry1.get("xid"), "0", "0", "0.0", BountyTestcasesConstants.Pincodes.PINCODE_560068,
                                                                       "responsive", "DEFAULT", "on");
        String orderID = orderResponse1.getData().get(0).getStoreOrderId();

        //   OrderCreationResponse orderQueueResponse = bountyServiceHelper.queueOrder(orderID, ""+orderEntry1.get("xid"), login, "DEFAULT", "SUCCESS", true);
        //   Assert.assertEquals(orderQueueResponse.getStatus().getStatusType(), StatusResponse.Type.SUCCESS);

        HashMap<String, Object> orderEntry2 = bountyServiceHelper.getCreateOrderEntry(login, BountyTestcasesConstants.BountyServiceTest.PASSWORD, skuId1, "ADD","", "200", "50", "");
        OrderResponse orderResponse2 = bountyServiceHelper.createOrder((CartEntry) orderEntry2.get("cart"), "12345", login, ""+orderEntry2.get("xid"), "0", "0", "0.0", BountyTestcasesConstants.Pincodes.PINCODE_560068,
                                                                       "responsive", "DEFAULT", "on");
        String orderID2 = orderResponse2.getData().get(0).getStoreOrderId();

        //   OrderCreationResponse orderQueueResponse2 = bountyServiceHelper.queueOrder(orderID, ""+orderEntry2.get("xid"), login, "DEFAULT", "SUCCESS", true);
        //   Assert.assertEquals(orderQueueResponse2.getStatus().getStatusType(), StatusResponse.Type.SUCCESS);

        Object[] arr1 = {orderID,login, ""+orderEntry1.get("xid"),"DEFAULT", "FAILURE", 2, false};
        Object[] arr2 = {orderID2,login, ""+orderEntry2.get("xid"),"DEFAULT", "FAILURE", 69, false};

        Object[][] dataSet = new Object[][] { arr1,arr2};
        return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 6, 6);
    }

    @DataProvider
    public static Object[][] declineOrderSuccessFromPVStatusDP(ITestContext testContext) throws IOException, JAXBException {
        String login = BountyTestcasesConstants.BountyServiceTest.LOGIN_URL;
        String skuId1[] = {BountyTestcasesConstants.BountyServiceTest.declineOrderSuccessFromPVStatusDP_ITEM1+":2"};

        HashMap<String, Object> orderEntry1 = bountyServiceHelper.getCreateOrderEntry(login, BountyTestcasesConstants.BountyServiceTest.PASSWORD, skuId1, "ADD","", "200", "50", "");
        OrderResponse orderResponse1 = bountyServiceHelper.createOrder((CartEntry) orderEntry1.get("cart"), "12345", login, ""+orderEntry1.get("xid"), "0", "0", "0.0", BountyTestcasesConstants.Pincodes.PINCODE_560068,
                                                                       "responsive", "DEFAULT", "cod");
        String orderID = orderResponse1.getData().get(0).getStoreOrderId();


        HashMap<String, Object> orderEntry2 = bountyServiceHelper.getCreateOrderEntry(login, BountyTestcasesConstants.BountyServiceTest.PASSWORD, skuId1, "ADD","", "200", "50", "");
        OrderResponse orderResponse2 = bountyServiceHelper.createOrder((CartEntry) orderEntry2.get("cart"), "12345", login, ""+orderEntry2.get("xid"), "0", "0", "0.0", BountyTestcasesConstants.Pincodes.PINCODE_560068,
                                                                       "responsive", "DEFAULT", "cod");
        String orderID2 = orderResponse2.getData().get(0).getStoreOrderId();


        Assert.assertEquals(orderResponse2.getStatus().getStatusType(), StatusResponse.Type.SUCCESS);


        Object[] arr1 = {orderID, login, ""+orderEntry1.get("xid"),"DEFAULT", "FAILURE", 2, false};
        Object[] arr2 = {orderID2, login, ""+orderEntry2.get("xid"),"DEFAULT", "FAILURE", 69, false};

        Object[][] dataSet = new Object[][] { arr1,arr2};
        return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 6, 6);
    }

    @DataProvider
    public static Object[][] declineOrderSuccessFromPVToQOnHold39StatusDP(ITestContext testContext) throws IOException, JAXBException {
        String login = BountyTestcasesConstants.BountyServiceTest.LOGIN_URL;
        String skuId1[] = {BountyTestcasesConstants.BountyServiceTest.declineOrderSuccessFromPVToQOnHold39StatusDP_ITEM1+":2"};

        HashMap<String, Object> orderEntry1 = bountyServiceHelper.getCreateOrderEntry(login, BountyTestcasesConstants.BountyServiceTest.PASSWORD, skuId1, "ADD","", "200", "50", "");
        OrderResponse orderResponse1 = bountyServiceHelper.createOrder((CartEntry) orderEntry1.get("cart"), "12345", login, ""+orderEntry1.get("xid"), "0", "0", "0.0", BountyTestcasesConstants.Pincodes.PINCODE_560068,
                                                                       "responsive", "DEFAULT", "cod");
        String orderID = orderResponse1.getData().get(0).getStoreOrderId();

        Assert.assertEquals(bountyServiceHelper.getxCartOrderTableDBEntry(orderID).get("status"),"PV");
        OrderCreationResponse orderQueueResponse = bountyServiceHelper.queueOrder(orderID, ""+orderEntry1.get("xid"), login, "DEFAULT", "SUCCESS", true);
        Assert.assertEquals(orderQueueResponse.getStatus().getStatusType(), StatusResponse.Type.SUCCESS);


        HashMap<String, Object> orderEntry2 = bountyServiceHelper.getCreateOrderEntry(login, BountyTestcasesConstants.BountyServiceTest.PASSWORD, skuId1, "ADD","", "200", "50", "");
        OrderResponse orderResponse2 = bountyServiceHelper.createOrder((CartEntry) orderEntry2.get("cart"), "12345", login, ""+orderEntry2.get("xid"), "0", "0", "0.0", BountyTestcasesConstants.Pincodes.PINCODE_560068,
                                                                       "responsive", "DEFAULT", "cod");
        String orderID2 = orderResponse2.getData().get(0).getStoreOrderId();

        Assert.assertEquals(bountyServiceHelper.getxCartOrderTableDBEntry(orderID2).get("status"),"PV");
        OrderCreationResponse orderQueueResponse2 = bountyServiceHelper.queueOrder(orderID2, ""+orderEntry2.get("xid"), login, "DEFAULT", "SUCCESS", true);
        Assert.assertEquals(orderQueueResponse2.getStatus().getStatusType(), StatusResponse.Type.SUCCESS);


        Object[] arr1 = {orderID, login, ""+orderEntry1.get("xid"),"DEFAULT", "FAILURE", 2, false};
        Object[] arr2 = {orderID2, login, ""+orderEntry2.get("xid"),"DEFAULT", "FAILURE", 69, false};

        Object[][] dataSet = new Object[][] { arr1,arr2};
        return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 6, 6);
    }


    @DataProvider
    public static Object[][] declineOrderSuccessFromPPToQOnHold39StatusDP(ITestContext testContext) throws IOException, JAXBException {
        String login = BountyTestcasesConstants.BountyServiceTest.LOGIN_URL;
        String skuId1[] = {BountyTestcasesConstants.BountyServiceTest.declineOrderSuccessFromPPToQOnHold39StatusDP_ITEM1+":2"};

        HashMap<String, Object> orderEntry1 = bountyServiceHelper.getCreateOrderEntry(login, BountyTestcasesConstants.BountyServiceTest.PASSWORD, skuId1, "ADD","", "200", "50", "");
        OrderResponse orderResponse1 = bountyServiceHelper.createOrder((CartEntry) orderEntry1.get("cart"), "12345", login, ""+orderEntry1.get("xid"), "0", "0", "0.0", BountyTestcasesConstants.Pincodes.PINCODE_560068,
                                                                       "responsive", "DEFAULT", "on");
        String orderID = orderResponse1.getData().get(0).getStoreOrderId();

        Assert.assertEquals(bountyServiceHelper.getxCartOrderTableDBEntry(orderID).get("status"),"PP");
        OrderCreationResponse orderQueueResponse = bountyServiceHelper.queueOrder(orderID, ""+orderEntry1.get("xid"), login, "DEFAULT", "SUCCESS", true);
        Assert.assertEquals(orderQueueResponse.getStatus().getStatusType(), StatusResponse.Type.SUCCESS);


        HashMap<String, Object> orderEntry2 = bountyServiceHelper.getCreateOrderEntry(login, BountyTestcasesConstants.BountyServiceTest.PASSWORD, skuId1, "ADD","", "200", "50", "");
        OrderResponse orderResponse2 = bountyServiceHelper.createOrder((CartEntry) orderEntry2.get("cart"), "12345", login, ""+orderEntry2.get("xid"), "0", "0", "0.0", BountyTestcasesConstants.Pincodes.PINCODE_560068,
                                                                       "responsive", "DEFAULT", "on");
        String orderID2 = orderResponse2.getData().get(0).getStoreOrderId();

        Assert.assertEquals(bountyServiceHelper.getxCartOrderTableDBEntry(orderID2).get("status"),"PP");
        OrderCreationResponse orderQueueResponse2 = bountyServiceHelper.queueOrder(orderID2, ""+orderEntry2.get("xid"), login, "DEFAULT", "SUCCESS", true);
        Assert.assertEquals(orderQueueResponse2.getStatus().getStatusType(), StatusResponse.Type.SUCCESS);


        Object[] arr1 = {orderID, login, ""+orderEntry1.get("xid"),"DEFAULT", "FAILURE", 2, false};
        Object[] arr2 = {orderID2, login, ""+orderEntry2.get("xid"),"DEFAULT", "FAILURE", 69, false};

        Object[][] dataSet = new Object[][] { arr1,arr2};
        return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 6, 6);
    }


    @DataProvider
    public static Object[][] declineBountyQueueOrderSuccessDP(ITestContext testContext) throws IOException, JAXBException {
        String login = BountyTestcasesConstants.BountyServiceTest.LOGIN_URL;
        String skuId1[] = {BountyTestcasesConstants.BountyServiceTest.declineBountyQueueOrderSuccessDP_ITEM1+":2"};

        HashMap<String, Object> orderEntry1 = bountyServiceHelper.getCreateOrderEntry(login, BountyTestcasesConstants.BountyServiceTest.PASSWORD, skuId1, "ADD","", "200", "50", "");
        OrderResponse orderResponse1 = bountyServiceHelper.createOrder((CartEntry) orderEntry1.get("cart"), "12345", login, ""+orderEntry1.get("xid"), "0", "0", "0.0", BountyTestcasesConstants.Pincodes.PINCODE_560068,
                                                                       "responsive", "DEFAULT", "cod");
        String orderID = orderResponse1.getData().get(0).getStoreOrderId();
        Assert.assertEquals(bountyServiceHelper.getxCartOrderTableDBEntry(orderID).get("status"),"PV");
        OrderCreationResponse orderQueueResponse = bountyServiceHelper.queueOrder(orderID, ""+orderEntry1.get("xid"), login, "DEFAULT", "SUCCESS", false);
        Assert.assertEquals(orderQueueResponse.getStatus().getStatusType(), StatusResponse.Type.SUCCESS);

        HashMap<String, Object> orderEntry2 = bountyServiceHelper.getCreateOrderEntry(login, BountyTestcasesConstants.BountyServiceTest.PASSWORD, skuId1, "ADD","", "200", "50", "");
        OrderResponse orderResponse2 = bountyServiceHelper.createOrder((CartEntry) orderEntry2.get("cart"), "12345", login, ""+orderEntry2.get("xid"), "0", "0", "0.0", BountyTestcasesConstants.Pincodes.PINCODE_560068,
                                                                       "responsive", "DEFAULT", "on");
        String orderID2 = orderResponse2.getData().get(0).getStoreOrderId();

        Assert.assertEquals(bountyServiceHelper.getxCartOrderTableDBEntry(orderID2).get("status"),"PP");
        OrderCreationResponse orderQueueResponse2 = bountyServiceHelper.queueOrder(orderID2, ""+orderEntry2.get("xid"), login, "DEFAULT", "SUCCESS", false);
        Assert.assertEquals(orderQueueResponse2.getStatus().getStatusType(), StatusResponse.Type.SUCCESS);

        Object[] arr1 = {orderID, login,""+orderEntry1.get("xid"), "DEFAULT","FAILURE", 2, false};
        Object[] arr2 = {orderID2, login, ""+orderEntry2.get("xid"),"DEFAULT","FAILURE", 69, false};

        Object[][] dataSet = new Object[][] { arr1,arr2};
        return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 6, 6);
    }



    @DataProvider
    public static Object[][] declineAlreadyDeclinedOrderDP(ITestContext testContext) throws IOException, JAXBException {
        String login = BountyTestcasesConstants.BountyServiceTest.LOGIN_URL;
        String skuId1[] = {BountyTestcasesConstants.BountyServiceTest.declineAlreadyDeclinedOrderDP_ITEM1+":2"};

        HashMap<String, Object> orderEntry1 = bountyServiceHelper.getCreateOrderEntry(login, BountyTestcasesConstants.BountyServiceTest.PASSWORD, skuId1, "ADD","", "200", "50", "");
        OrderResponse orderResponse1 = bountyServiceHelper.createOrder((CartEntry) orderEntry1.get("cart"), "12345", login, ""+orderEntry1.get("xid"), "0", "0", "0.0", BountyTestcasesConstants.Pincodes.PINCODE_560068,
                                                                       "responsive", "DEFAULT", "cod");
        String orderID = orderResponse1.getData().get(0).getStoreOrderId();

        OrderCreationResponse orderQueueResponse = bountyServiceHelper.queueOrder(orderID, ""+orderEntry1.get("xid"), login, "DEFAULT", "SUCCESS", true);
        Assert.assertEquals(orderQueueResponse.getStatus().getStatusType(), StatusResponse.Type.SUCCESS);

        OrderCreationResponse orderDeclineResponse = bountyServiceHelper.declineOrder(orderID, login, ""+orderEntry1.get("xid"), "DEFAULT", "FAILURE", 2, false);
        Assert.assertEquals(orderDeclineResponse.getStatus().getStatusType(), StatusResponse.Type.SUCCESS);
        Assert.assertEquals(orderDeclineResponse.getStatus().getStatusCode(), 1001);
        Assert.assertEquals(orderDeclineResponse.getStatus().getStatusMessage(), "Order added successfully");
//        HashMap<String, Object> orderEntry2 = bountyServiceHelper.getCreateOrderEntry(login, BountyTestcasesConstants.BountyServiceTest.PASSWORD, skuId_3827, "ADD","", "200", "50", "");
//        OrderResponse orderResponse2 = bountyServiceHelper.createOrder((CartEntry) orderEntry2.get("cart"), "12345", login, ""+orderEntry2.get("xid"), "0", "0", "0.0", BountyTestcasesConstants.Address.ADDRESS1,
//                                                                       "responsive", "DEFAULT", "on");
//        long orderID2 = orderResponse2.getData().get(0).getId();
//
//        OrderCreationResponse orderQueueResponse2 = bountyServiceHelper.queueOrder(orderID, ""+orderEntry2.get("xid"), login, "DEFAULT", "SUCCESS", true);
//        Assert.assertEquals(orderQueueResponse2.getStatus().getStatusType(), StatusResponse.Type.SUCCESS);
//
        Object[] arr1 = {orderID, login, ""+orderEntry1.get("xid"),"DEFAULT","FAILURE", 2, false};
        //  Object[] arr2 = {orderID2, login, ""+orderEntry2.get("xid"),"FAILURE", 69, false};

        Object[][] dataSet = new Object[][] { arr1};
        return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 6, 6);
    }


    @DataProvider
    public static Object[][] confirmOrderNeverOnHoldDP(ITestContext testContext) throws IOException, JAXBException {
        //long orderID, String sessionID, String login, String cartContext, String paymentStatus, boolean isOnHold
        String login = BountyTestcasesConstants.BountyServiceTest.LOGIN_URL;
        String skuId1[] = {BountyTestcasesConstants.BountyServiceTest.confirmOrderNeverOnHoldDP_ITEM1+":2"};

        HashMap<String, Object> orderEntry1 = bountyServiceHelper.getCreateOrderEntry(login, BountyTestcasesConstants.BountyServiceTest.PASSWORD, skuId1, "ADD","", "200", "50", "");
        OrderResponse orderResponse1 = bountyServiceHelper.createOrder((CartEntry) orderEntry1.get("cart"), "12345", login, ""+orderEntry1.get("xid"), "0", "0", "0.0", BountyTestcasesConstants.Pincodes.PINCODE_560068,
                                                                       "responsive", "DEFAULT", "on");
        String orderID = orderResponse1.getData().get(0).getStoreOrderId();

        OrderCreationResponse orderQueueResponse = bountyServiceHelper.queueOrder(orderID, ""+orderEntry1.get("xid"), login, "DEFAULT", "SUCCESS", false);
        Assert.assertEquals(orderQueueResponse.getStatus().getStatusType(), StatusResponse.Type.SUCCESS);

        // OrderCreationResponse orderConfirmationResponse = bountyServiceHelper.confirmOnHoldOrder(orderID,"SUCCESS",BountyTestcasesConstants.BountyServiceTest.PASSWORD,false);
        //  Assert.assertEquals(orderConfirmationResponse.getStatus().getStatusType(), StatusResponse.Type.SUCCESS);

        Object[] arr1 = {orderID,"SUCCESS",BountyTestcasesConstants.BountyServiceTest.PASSWORD, false};
        // Object[] arr2 = {orderID,"SUCCESS",BountyTestcasesConstants.BountyServiceTest.PASSWORD, true};

        Object[][] dataSet = new Object[][] { arr1};
        return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 6, 6);
    }










    @DataProvider
    public static Object[][] BountyServiceDP_getOrder_verifySuccessResponse(ITestContext testContext) {
        // paramOrderId, paramRespCode
        String[] arr1 = { "70001305", "200" };

        Object[][] dataSet = new Object[][] { arr1 };
        return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 2);
    }

    @DataProvider
    public static Object[][] BountyServiceDP_getOrder_verifyStatus(ITestContext testContext) {
        // paramOrderId, paramRespCode
        String[] arr1 = { "70001305", "200" };

        Object[][] dataSet = new Object[][] { arr1 };
        return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 2);
    }

    @DataProvider
    public static Object[][] BountyServiceDP_getOrder_verifySuccessStatusValues(ITestContext testContext) {
        // paramOrderId, paramStatusCode, paramStatusMsg, paramStatusType,
        // paramLoginId, paramPassword, paramBinNumber, paramPGDiscount,
        // paramEMICharge, paramClientContext,
        // paramCartContext, paramPaymentMethod
        String[] arr1 = { getRandomOrderId(), "1003", "Order(s) retrieved successfully", "SUCCESS",
                BountyTestcasesConstants.BountyServiceTest.LOGIN_URL, BountyTestcasesConstants.BountyServiceTest.PASSWORD, "0", "0", "0.0", "responsive", "DEFAULT", "on" };
        Object[][] dataSet = new Object[][] { arr1 };
        return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 2);
    }

    @DataProvider
    public static Object[][] BountyServiceDP_getOrder_verifyFailureStatusInformation(ITestContext testContext) {
        // paramOrderId, paramRespcode, paramStatusCode, paramStatusMsg,
        // paramStatusType
        String[] arr1 = { "", "404", "", "", "" };
        String[] arr2 = { "abc$$$123", "404", "", "", "" };

        Object[][] dataSet = new Object[][] { arr1, arr2 };
        return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 2, 2);
    }

    @DataProvider
    public static Object[][] BountyServiceDP_getOrder_verifyFailureStatusInformation_pps(ITestContext testContext) {
        // paramOrderId, paramRespcode, paramStatusCode, paramStatusMsg,
        // paramStatusType
        String[] arr1 = { "", "404", "", "", "" };
        String[] arr2 = { "abc$$$123", "404", "", "", "" };

        Object[][] dataSet = new Object[][] { arr1, arr2 };
        return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 2, 2);
    }

    @DataProvider
    public static Object[][] BountyServiceDP_createOrder_verifySuccessResponse(ITestContext testContext) {
        BountyServiceHelper bountyServiceHelper = new BountyServiceHelper();
        String[] skuEntries = { BountyTestcasesConstants.BountyServiceTest.BountyServiceDP_createOrder_verifySuccessResponse_ITEM1+":1" };
        String[] arr1 = { BountyTestcasesConstants.BountyServiceTest.LOGIN_URL,
                bountyServiceHelper.getXID(BountyTestcasesConstants.BountyServiceTest.LOGIN_URL, BountyTestcasesConstants.BountyServiceTest.PASSWORD, skuEntries, "ADD"), "0", "0",
                "0.0", BountyTestcasesConstants.Pincodes.PINCODE_560068, "responsive", "DEFAULT", "on", "200" };

        Object[][] dataSet = new Object[][] { arr1 };
        return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 2);
    }

    @DataProvider
    public static Object[][] BountyServiceDP_createOrder_verifyStatus(ITestContext testContext) throws SQLException {
        // paramLoginId, paramSessionId, paramBinNumber, paramPGDiscount,
        // paramEMICharge, paramAddressId, paramClientContext, paramCartContext,
        // paramPaymentMethod,
        // paramRespCode
        String expectedResultarr1 = "status.statusCode==1001::status.statusType=='SUCCESS'::status.statusMessage=='Order added successfully'";
        BountyServiceHelper bountyServiceHelper = new BountyServiceHelper();
        String[] skuEntries = { BountyTestcasesConstants.BountyServiceTest.BountyServiceDP_createOrder_verifyStatus_ITEM1+":1" };
        DBUtilities.exUpdateQuery("update inventory set inventory_count=1000, available_in_warehouses='1' where sku_id=987669;", "atp");
        DBUtilities.exUpdateQuery("update wh_inventory set inventory_count=1000 where sku_id=987669;", "ims");
        String[] arr1 = { BountyTestcasesConstants.BountyServiceTest.LOGIN_URL,
                bountyServiceHelper.getXID(BountyTestcasesConstants.BountyServiceTest.LOGIN_URL, BountyTestcasesConstants.BountyServiceTest.PASSWORD, skuEntries, "ADD"), "0", "0",
                "0.0", BountyTestcasesConstants.Pincodes.PINCODE_560068, "responsive", "DEFAULT", "on", "200",expectedResultarr1 };

        Object[][] dataSet = new Object[][] { arr1 };
        return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 7, 2);
    }

    @DataProvider
    public static Object[][] BountyServiceDP_createOrder_verifyStatus_pps(ITestContext testContext) {
        // paramLoginId, paramSessionId, paramBinNumber, paramPGDiscount,
        // paramEMICharge, paramAddressId, paramClientContext, paramCartContext,
        // paramPaymentMethod,
        // paramRespCode
        BountyServiceHelper bountyServiceHelper = new BountyServiceHelper();
        String[] skuEntries = { BountyTestcasesConstants.BountyServiceTest.BountyServiceDP_createOrder_verifyStatus_pps_ITEM1+":1" };
        String[] arr1 = { BountyTestcasesConstants.BountyServiceTest.LOGIN_URL,
                bountyServiceHelper.getXID(BountyTestcasesConstants.BountyServiceTest.LOGIN_URL, BountyTestcasesConstants.BountyServiceTest.PASSWORD, skuEntries, "ADD"), "0", "0",
                "0.0", BountyTestcasesConstants.Pincodes.PINCODE_560068, "responsive", "DEFAULT", "on", "200", "12345" };

        Object[][] dataSet = new Object[][] { arr1 };
        return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 7, 2);
    }

    @DataProvider
    public static Object[][] BountyServiceDP_createOrder_verifySuccessStatusValues(ITestContext testContext) {
        // paramLoginId, paramPassword, paramBinNumber, paramPGDiscount,
        // paramEMICharge, paramClientContext, paramCartContext,
        // paramPaymentMethod, paramRespCode,
        // paramStatusCode, paramStatusMsg, paramStatusType
        String expectedResultarr1 = "status.statusCode==1001::status.statusType=='SUCCESS'::status.statusMessage=='Order added successfully'::data.finalAmount is not null";
        BountyServiceHelper bountyServiceHelper = new BountyServiceHelper();
        String[] skuEntries = { BountyTestcasesConstants.BountyServiceTest.BountyServiceDP_createOrder_verifySuccessStatusValues_ITEM1+":1" };
        String[] arr1 = { BountyTestcasesConstants.BountyServiceTest.LOGIN_URL,
                bountyServiceHelper.getXID(BountyTestcasesConstants.BountyServiceTest.LOGIN_URL, BountyTestcasesConstants.BountyServiceTest.PASSWORD, skuEntries, "ADD"), "0", "0",
                "0.0", BountyTestcasesConstants.Pincodes.PINCODE_560068, "responsive", "DEFAULT", "on", "200",  expectedResultarr1};

        Object[][] dataSet = new Object[][] { arr1 };
        return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 7, 2);
    }

    @DataProvider
    public static Object[][] BountyServiceDP_createOrder_verifyDataValues(ITestContext testContext) {
        String[] arr1 = { BountyTestcasesConstants.BountyServiceTest.LOGIN_URL, BountyTestcasesConstants.BountyServiceTest.PASSWORD, "0", "0", "0.0", "responsive", "DEFAULT", "on",
                "200", "1001", "Order added successfully", "SUCCESS" };

        Object[][] dataSet = new Object[][] { arr1 };
        return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 7, 2);
    }

    @DataProvider
    public static Object[][] BountyServiceDP_createOrder_verifyFailureStatusInformation(ITestContext testContext) {
        // paramLoginId, paramSessionId, paramBinNumber, paramPGDiscount,
        // paramEMICharge, paramAddressId, paramClientContext, paramCartContext,
        // paramPaymentMethod,
        // paramRespCode
		/*
		 * String[] skuEntries = { "987669:1" }; BountyServiceHelper
		 * bountyServiceHelper = new BountyServiceHelper();
		 * bountyServiceHelper.getXID(BountyTestcasesConstants.BountyServiceTest.LOGIN_URL, BountyTestcasesConstants.BountyServiceTest.PASSWORD,
		 * skuEntries, "ADD");
		 */
        String expectedResultarr1 = "status.statusCode==8007::status.statusType=='ERROR'::status.statusMessage=='Empty cart. Not creating order'";
        String[] arr1 = { BountyTestcasesConstants.BountyServiceTest.LOGIN_URL, "JJN006f413959599494", "0", "0", "0.0", BountyTestcasesConstants.Pincodes.PINCODE_560068,
                "responsive", "DEFAULT", "on", "200", expectedResultarr1 };

        String expectedResultarr2 = "status.statusCode==8007::status.statusType=='ERROR'::status.statusMessage=='Empty cart. Not creating order'";
        String[] arr2 = { "", "JJN0023e147056195746788705ca9225fab69e1417594886M", "0", "0", "0.0", BountyTestcasesConstants.Pincodes.PINCODE_560068,
                "responsive", "DEFAULT", "on", "200",  expectedResultarr2};

        String expectedResultarr3 = "status.statusCode==8007::status.statusType=='ERROR'::status.statusMessage=='Empty cart. Not creating order'";
        String[] arr3 = { BountyTestcasesConstants.BountyServiceTest.LOGIN_URL, "", "0", "0", "0.0", BountyTestcasesConstants.Pincodes.PINCODE_560068, "responsive", "DEFAULT", "on",
                "200", expectedResultarr3};

        String expectedResultarr4 = "status.statusCode==8007::status.statusType=='ERROR'::status.statusMessage=='Empty cart. Not creating order'";
        String[] arr4 = { "", "", "0", "0", "0.0", BountyTestcasesConstants.Pincodes.PINCODE_560068, "responsive", "DEFAULT", "on", "200", expectedResultarr4 };

        Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4 };
        return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 6, 2);
    }

    @DataProvider
    public static Object[][] BountyServiceDP_queueOrder_verifyStatus(ITestContext testContext) {
        // paramOrderId, paramSessionId, paramGiftCardAmount, paramLogin,
        // paramCartContext, paramBinNumber, paramPaymentMethod,
        // paramPGDiscount, paramPaymentStatus,
        // paramRespCode
        // String [] arr1 =
        // {BountyServiceHelper.getOrderID().get(2),BountyServiceHelper.getOrderID().get(1)
        // , "1", "ssamantray16@gmail.com", "DEFAULT", "12345", "on", "0",
        // "success", "200" };
        String[] arr2 = { "70001305", "JJN0023e147056195746788705ca9225fab69e1417594886M", "1",
                "ssamantray16@gmail.com", "DEFAULT", "12345", "on", "0", "success", "200" };

        Object[][] dataSet = new Object[][] { arr2 };
        return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 7, 2);
    }

    @DataProvider
    public static Object[][] BountyServiceDP_queueOrder_verifyFailureStatusInformation(ITestContext testContext) {
        // paramOrderId, paramSessionId, paramGiftCardAmount, paramLogin,
        // paramCartContext, paramBinNumber, paramPaymentMethod,
        // paramPGDiscount, paramPaymentStatus,
        // paramRespCode, paramStatusCode, paramStatusMsg, paramStatusType
        String orderId = getRandomOrderId();
        String[] arr1 = { orderId, "JJN0023e147056195746788705ca9225fab69e1417594886M", "1", "ssamantray16@gmail.com",
                "DEFAULT", "12345", "on", "0", "success", "200", "8001", "Order with id " + orderId + " not found.",
                "ERROR" };
        String[] arr2 = { "70001305", "JJN0023e147056195746788705ca9225fab69e1417594886M", "1",
                "ssamantray16@gmail.com", "DEFAULT", "12345", "on", "0", "success", "200", "8010",
                "Order id 70001305 is neither PP not PV, so it cant be queued", "ERROR" };
        String[] arr3 = { "", "JJN0023e147056195746788705ca9225fab69e1417594886M", "1", "ssamantray16@gmail.com",
                "DEFAULT", "12345", "on", "0", "success", "200", "54", "id to load is required for loading", "ERROR" };

        Object[][] dataSet = new Object[][] { arr1, arr2, arr3 };
        return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 2);
    }

    // Emptycart
    @DataProvider
    public static Object[][] createOrder_verifyFailure_Emptycart(ITestContext testContext) {
        String expectedResultarr1 = "status.statusCode==8007::status.statusType=='ERROR'::status.statusMessage=='Empty cart. Not creating order'";
        String[] arr1 = { BountyTestcasesConstants.BountyServiceTest.LOGIN_URL,
                BountyServiceHelper.Emptycart("emptycart@myntra.com", BountyTestcasesConstants.BountyServiceTest.PASSWORD), "0", "0", "0.0", BountyTestcasesConstants.Pincodes.PINCODE_560068,
                "responsive", "DEFAULT", "on", "200", expectedResultarr1 };
        String[] arr2 = { "bountytestuser002@myntra.com",
                BountyServiceHelper.Emptycart("emptycart@myntra.com", BountyTestcasesConstants.BountyServiceTest.PASSWORD), "0", "0", "0.0", BountyTestcasesConstants.Pincodes.PINCODE_560068,
                "responsive", "DEFAULT", "on", "200", expectedResultarr1 };
        String[] arr3 = { "bountytestuser003@myntra.com",
                BountyServiceHelper.Emptycart("emptycart@myntra.com", BountyTestcasesConstants.BountyServiceTest.PASSWORD), "0", "0", "0.0", BountyTestcasesConstants.Pincodes.PINCODE_560068,
                "responsive", "DEFAULT", "on", "200", expectedResultarr1 };

        Object[][] dataSet = new Object[][] { arr1, arr2, arr3 };
        return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 2);
    }

    // invalidcart
    @DataProvider
    public static Object[][] createOrder_verifyFailure_invalidcart(ITestContext testContext) {
        String expectedResultarr1 = "status.statusCode==8007::status.statusType=='ERROR'::status.statusMessage=='Empty cart. Not creating order'";
        String[] arr1 = { BountyTestcasesConstants.BountyServiceTest.LOGIN_URL, "gjsdjkasgdkjagdjsd", "0", "0", "0.0", BountyTestcasesConstants.Pincodes.PINCODE_560068,
                "responsive", "DEFAULT", "on", "200", expectedResultarr1 };
        String[] arr2 = { "bountytestuser002@myntra.com", "gjsdjkasgdk111jagdjsd", "0", "0", "0.0", BountyTestcasesConstants.Pincodes.PINCODE_560068,
                "responsive", "DEFAULT", "on", "200", expectedResultarr1 };

        Object[][] dataSet = new Object[][] { arr1, arr2 };
        return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 2);
    }

    // SkuOOS
    @DataProvider
    public static Object[][] createOrder_verifyFailure_SkuOOS(ITestContext testContext) throws SQLException {
        String skuId[] = { BountyTestcasesConstants.BountyServiceTest.createOrder_verifyFailure_SkuOOS_ITEM1+":1" };
        String expectedResultarr1 = "status.statusCode==8002::status.statusType=='ERROR'::status.statusMessage=='SKU id 852767 in the order is OOS.'";
        DBUtilities.exUpdateQuery("update inventory set inventory_count=0 where sku_id='852767'", "ATP");
        BountyServiceHelper bountyServiceHelper = new BountyServiceHelper();
        String[] arr1 = { BountyTestcasesConstants.BountyServiceTest.LOGIN_URL,
                bountyServiceHelper.getXID("ooscart@myntra.com", BountyTestcasesConstants.BountyServiceTest.PASSWORD, skuId, "ADD"), "0", "0", "0.0", BountyTestcasesConstants.Pincodes.PINCODE_560068,
                "responsive", "DEFAULT", "on", "200", expectedResultarr1 };

        Object[][] dataSet = new Object[][] { arr1 };
        return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 2);
    }

    // Order not in PP/PV
    @DataProvider
    public static Object[][] queueOrder_verifyFailure_ImproperOrderStatus(ITestContext testContext) {

        String[] arr1 = { "75511898", "JJN0023e147056195746788705ca9225fab69e1417594886M", "100",
                "bountytestuser002@myntra.com", "DEFAULT", "12345", "on", "0", "success", "200", "8001",
                "Order id 75511898 is neither PP not PV, so it cant be queued", "ERROR" };

        String[] arr2 = { "1222222212", "JJN0023e147056195746788705ca9225fab69e1417594886M", "100",
                "bountytestuser002@myntra.com", "DEFAULT", "12345", "on", "0", "success", "200", "8001",
                "Order with id 1222222212 not found.", "ERROR" };

        Object[][] dataSet = new Object[][] { arr1, arr2 };
        return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 2, 2);
    }

    // create order with gift card amount
    @DataProvider
    public static Object[][] queueOrder_GiftcardAmount(ITestContext testContext) throws JsonParseException, JsonMappingException, IOException, JAXBException {
        BountyServiceHelper bountyServiceHelper = new BountyServiceHelper();
        String skuId[] = { BountyTestcasesConstants.BountyServiceTest.queueOrder_GiftcardAmount_ITEM1+":1" }; // sku_code = MSMRFHSR00014

        String login = "bountytestuser002@myntra.com";
        HashMap<String, String> createOrderEntry = bountyServiceHelper.getOrderEntryFromCreateOrderResponse(skuId, null,
                                                                                                            login, BountyTestcasesConstants.BountyServiceTest.PASSWORD, "123", "0.2", "0.0", "6115221", "responsive", "DEFAULT", "on");

        String[] arr1 = { createOrderEntry.get("orderID"), createOrderEntry.get("xid"), "100",
                "bountytestuser002@myntra.com", "DEFAULT", "123", "on", "0", "success", "200", "1001",
                "Order added successfully", "SUCCESS", "987669" };

        Object[][] dataSet = new Object[][] { arr1 };
        return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 2);
    }

    // create online order
    @DataProvider
    public static Object[][] createOrder_OnlineOrder(ITestContext testContext) throws SQLException {

        BountyServiceHelper bountyServiceHelper = new BountyServiceHelper();
        DBUtilities.exUpdateQuery("update inventory set inventory_count=1000, available_in_warehouses='1' where sku_id=987669;", "atp");
        DBUtilities.exUpdateQuery("update wh_inventory set inventory_count=1000 where sku_id=987669;", "ims");
        String skuId[] = { BountyTestcasesConstants.BountyServiceTest.createOrder_OnlineOrder_ITEM1+":1" }; // sku_code = MSMRFHSR00014
        String expectedResultarr1 = "status.statusCode==1001::status.statusType=='SUCCESS'::status.statusMessage=='Order added successfully'";
        String[] arr1 = { BountyTestcasesConstants.BountyServiceTest.LOGIN_URL,
                bountyServiceHelper.getXID(BountyTestcasesConstants.BountyServiceTest.LOGIN_URL, BountyTestcasesConstants.BountyServiceTest.PASSWORD, skuId, "ADD"), "0", "0", "0.0",
                BountyTestcasesConstants.Pincodes.PINCODE_560068, "responsive", "DEFAULT", "on", "200",
                "987669",expectedResultarr1 };

        Object[][] dataSet = new Object[][] { arr1 };
        return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 2);
    }

    // create cod order
    @DataProvider
    public static Object[][] createOrder_CODorder(ITestContext testContext) throws IOException, JAXBException {
        BountyServiceHelper bountyServiceHelper = new BountyServiceHelper();
        String skuId1[] = { BountyTestcasesConstants.BountyServiceTest.createOrder_CODorder_ITEM1+":1" };
        String skuId2[] = { BountyTestcasesConstants.BountyServiceTest.createOrder_CODorder_ITEM2+":5" };
        DBUtilities.exUpdateQuery("update myntra_tools.core_application_properties set value='20-20000' where name='cod.limit.range';","myntra_oms");
        bountyServiceHelper.refreshBountyApplicationPropertyCache();

        String expectedResultarr1 = "status.statusCode==1001::status.statusType=='SUCCESS'::status.statusMessage=='Order added successfully'";
        String[] arr1 = { BountyTestcasesConstants.BountyServiceTest.LOGIN_URL,
                bountyServiceHelper.getXID(BountyTestcasesConstants.BountyServiceTest.LOGIN_URL, BountyTestcasesConstants.BountyServiceTest.PASSWORD, skuId1, "ADD"), "0", "0", "0.0",
                BountyTestcasesConstants.Pincodes.PINCODE_560068, "responsive", "DEFAULT", "cod", "200",
                "987669", "1", expectedResultarr1};

        String[] arr2 = { "bountytestuser002@myntra.com",
                bountyServiceHelper.getXID("bountytestuser002@myntra.com", BountyTestcasesConstants.BountyServiceTest.PASSWORD, skuId2, "ADD"), "0", "0", "0.0",
                BountyTestcasesConstants.Pincodes.PINCODE_560068, "responsive", "DEFAULT", "cod", "200",
                "987669", "5", expectedResultarr1 };

        Object[][] dataSet = new Object[][] { arr1, arr2 };
        return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 2, 2);
    }

    // create multiple skus order

    @DataProvider
    public static Object[][] createOrder_multipleskus(ITestContext testContext) {
        BountyServiceHelper bountyServiceHelper = new BountyServiceHelper();
        String skuId[] = { BountyTestcasesConstants.BountyServiceTest.createOrder_multipleskus_ITEM1+":2", BountyTestcasesConstants.BountyServiceTest.createOrder_multipleskus_ITEM2+":2" }; // sku_code =
        // MSMRFHSR00014
        String[] arr1 = { BountyTestcasesConstants.BountyServiceTest.LOGIN_URL,
                bountyServiceHelper.getXID(BountyTestcasesConstants.BountyServiceTest.LOGIN_URL, BountyTestcasesConstants.BountyServiceTest.PASSWORD, skuId, "ADD"), "0", "0", "0.0",
                BountyTestcasesConstants.Pincodes.PINCODE_560068, "responsive", "DEFAULT", "on", "200",
                Arrays.toString(skuId) };

        Object[][] dataSet = new Object[][] { arr1 };
        return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 2);
    }

    // create multiple skus one onhand one JIT
    @DataProvider
    public static Object[][] createOrder_typeOnhandAndJIT(ITestContext testContext) {
        BountyServiceHelper bountyServiceHelper = new BountyServiceHelper();
        String skuId[] = { BountyTestcasesConstants.BountyServiceTest.createOrder_typeOnhandAndJIT_ITEM1, BountyTestcasesConstants.BountyServiceTest.createOrder_typeOnhandAndJIT_ITEM2 }; // sku_code = MSMRFHSR00014
        String[] arr1 = { "bountytestuser004@myntra.com",
                bountyServiceHelper.getXID("bountytestuser004@myntra.com", BountyTestcasesConstants.BountyServiceTest.PASSWORD, skuId, "ADD"), "0", "0", "0.0",
                BountyTestcasesConstants.Pincodes.PINCODE_560068, "responsive", "DEFAULT", "on", "200", "1001", "Order added successfully", "SUCCESS" };

        Object[][] dataSet = new Object[][] { arr1 };
        return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 2);
    }

    public static String getRandomOrderId() {
        String orderId = String.valueOf(new Random().nextInt(98764321));
        orderId = orderId.replace(String.valueOf(orderId.charAt(0)), "9");
        return orderId;
    }

    @DataProvider
    public static Object[][] SingleItemOrder(ITestContext testContext) {
        BountyServiceHelper bountyServiceHelper = new BountyServiceHelper();
        String skuId1[] = { BountyTestcasesConstants.BountyServiceTest.SingleItemOrder_ITEM1+":1" };
        String skuId2[] = { BountyTestcasesConstants.BountyServiceTest.SingleItemOrder_ITEM2+":1" };
        String[] arr1 = { "marketplace@myntra.com",
                bountyServiceHelper.getXID("marketplace@myntra.com", BountyTestcasesConstants.BountyServiceTest.PASSWORD, skuId1, "ADD"), "0", "0", "0.0",
                BountyTestcasesConstants.Pincodes.PINCODE_560068, "responsive", "DEFAULT", "on", "200", "1001", "Order added successfully", "SUCCESS" };

        String[] arr2 = { "marketplace@myntra.com",
                bountyServiceHelper.getXID("marketplace@myntra.com", BountyTestcasesConstants.BountyServiceTest.PASSWORD, skuId2, "ADD"), "0", "0", "0.0",
                BountyTestcasesConstants.Pincodes.PINCODE_560068, "responsive", "DEFAULT", "on", "200", "1001", "Order added successfully", "SUCCESS" };
        Object[][] dataSet = new Object[][] { arr1, arr2 };
        return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 2);
    }

    @DataProvider
    public static Object[][] twoItemSingleQty(ITestContext testContext) {
        BountyServiceHelper bountyServiceHelper = new BountyServiceHelper();
        String skuId[] = { BountyTestcasesConstants.BountyServiceTest.twoItemSingleQty_ITEM1+":1", BountyTestcasesConstants.BountyServiceTest.twoItemSingleQty_ITEM2+":1" };// sku_code =
        // MSMRFHSR00015 ,
        // MSMRFHSR00003

        String[] arr1 = { "marketplace@myntra.com",
                bountyServiceHelper.getXID("marketplace@myntra.com", BountyTestcasesConstants.BountyServiceTest.PASSWORD, skuId, "ADD"), "0", "0", "0.0",
                BountyTestcasesConstants.Pincodes.PINCODE_560068, "responsive", "DEFAULT", "on", "200", "1001", "Order added successfully", "SUCCESS" };

        Object[][] dataSet = new Object[][] { arr1 };
        return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 2);
    }

    @DataProvider
    public static Object[][] twoItemMultipleQty(ITestContext testContext) {
        BountyServiceHelper bountyServiceHelper = new BountyServiceHelper();
        String skuId[] = { BountyTestcasesConstants.BountyServiceTest.twoItemMultipleQty_ITEM1, BountyTestcasesConstants.BountyServiceTest.twoItemMultipleQty_ITEM2 }; // sku_code = MSMRFHSR00005 ,
        // MSMRFHSR00006

        String[] arr1 = { "marketplace@myntra.com",
                bountyServiceHelper.getXID("marketplace@myntra.com", BountyTestcasesConstants.BountyServiceTest.PASSWORD, skuId, "2"), "0", "0", "0.0", BountyTestcasesConstants.Pincodes.PINCODE_560068,
                "responsive", "DEFAULT", "on", "200", "1001", "Order added successfully", "SUCCESS" };

        Object[][] dataSet = new Object[][] { arr1 };
        return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 2);
    }

    @DataProvider
    public static Object[][] singleQtyDiffVendor(ITestContext testContext) {
        BountyServiceHelper bountyServiceHelper = new BountyServiceHelper();
        String skuId[] = { BountyTestcasesConstants.BountyServiceTest.singleQtyDiffVendor_ITEM1, BountyTestcasesConstants.BountyServiceTest.singleQtyDiffVendor_ITEM2 }; // sku_code = MSMRFHSR00008 ,
        // MSMRFHSR00009

        String[] arr1 = { "marketplace@myntra.com",
                bountyServiceHelper.getXID("marketplace@myntra.com", BountyTestcasesConstants.BountyServiceTest.PASSWORD, skuId, "1"), "0", "0", "0.0", BountyTestcasesConstants.Pincodes.PINCODE_560068,
                "responsive", "DEFAULT", "on", "200", "1001", "Order added successfully", "SUCCESS" };

        Object[][] dataSet = new Object[][] { arr1 };
        return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 2);
    }

    @DataProvider
    public static Object[][] multipleQtyDiffVendor(ITestContext testContext) {
        BountyServiceHelper bountyServiceHelper = new BountyServiceHelper();
        String skuId[] = { BountyTestcasesConstants.BountyServiceTest.multipleQtyDiffVendor_ITEM1, BountyTestcasesConstants.BountyServiceTest.multipleQtyDiffVendor_ITEM2 }; // sku_code = MSMRFHSR00010 ,
        // MSMRFHSR00011

        String[] arr1 = { "marketplace@myntra.com",
                bountyServiceHelper.getXID("marketplace@myntra.com", BountyTestcasesConstants.BountyServiceTest.PASSWORD, skuId, "2"), "0", "0", "0.0", BountyTestcasesConstants.Pincodes.PINCODE_560068,
                "responsive", "DEFAULT", "on", "200", "1001", "Order added successfully", "SUCCESS" };

        Object[][] dataSet = new Object[][] { arr1 };
        return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 2);
    }

    @DataProvider
    public static Object[][] multipleQtyDiffWH(ITestContext testContext) {
        BountyServiceHelper bountyServiceHelper = new BountyServiceHelper();
        String skuId[] = { BountyTestcasesConstants.BountyServiceTest.multipleQtyDiffWH_ITEM1, BountyTestcasesConstants.BountyServiceTest.multipleQtyDiffWH_ITEM2 }; // sku_code = MSMRFHSR00012 ,
        // MSMRFHSR00013

        String[] arr1 = { "marketplace@myntra.com",
                bountyServiceHelper.getXID("marketplace@myntra.com", BountyTestcasesConstants.BountyServiceTest.PASSWORD, skuId, "2"), "0", "0", "0.0", BountyTestcasesConstants.Pincodes.PINCODE_560068,
                "responsive", "DEFAULT", "on", "200", "1001", "Order added successfully", "SUCCESS" };

        Object[][] dataSet = new Object[][] { arr1 };
        return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 2);
    }

    @DataProvider
    public static Object[][] OnHandAndJit(ITestContext testContext) {
        BountyServiceHelper bountyServiceHelper = new BountyServiceHelper();
        String skuId[] = { BountyTestcasesConstants.BountyServiceTest.OnHandAndJit_ITEM1, BountyTestcasesConstants.BountyServiceTest.OnHandAndJit_ITEM2 }; // sku_code = MSMRFHSR00014

        String[] arr1 = { "marketplace@myntra.com",
                bountyServiceHelper.getXID("marketplace@myntra.com", BountyTestcasesConstants.BountyServiceTest.PASSWORD, skuId, "2"), "0", "0", "0.0", BountyTestcasesConstants.Pincodes.PINCODE_560068,
                "responsive", "DEFAULT", "on", "200", "1001", "Order added successfully", "SUCCESS" };

        Object[][] dataSet = new Object[][] { arr1 };
        return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 2);
    }

    @DataProvider
    public static Object[][] BountyServiceDP_confirmOrder_pps(ITestContext testContext) throws JsonParseException, JsonMappingException, IOException, JAXBException {
		/*
		 * String orderId, String loginId, String sessionId, String binNumber,
		 * String pgDiscount, String giftCartAmount, String paymentStatus,
		 * String cartContext, String paymentMethod, String putOnHold, String
		 * expectedData
		 */
        BountyServiceHelper bountyServiceHelper = new BountyServiceHelper();
        String skuId[] = { BountyTestcasesConstants.BountyServiceTest.BountyServiceDP_confirmOrder_pps_ITEM1+":2" };
        String login1 = BountyTestcasesConstants.BountyServiceTest.LOGIN_URL;
        String login2 = "bountytestuser002@myntra.com";

        HashMap<String, String> createOrderEntry = bountyServiceHelper.getOrderEntryFromCreateOrderResponse(skuId,
                                                                                                            RandomStringUtils.randomNumeric(6), login1, BountyTestcasesConstants.BountyServiceTest.PASSWORD, "0", "0", "0.0", BountyTestcasesConstants.Pincodes.PINCODE_560068, "responsive",
                                                                                                            "default", "on");

        // Valid Queue Order
        String expectedResultarr1 = "status.statusCode==1004::status.statusType=='SUCCESS'::status.statusMessage=='Order updated successfully'";
        Object[] arr1 = {  Long.parseLong(createOrderEntry.get("orderID")), login1, createOrderEntry.get("xid"), "0", "0", "0",
                "SUCCESS", "DEFAULT", "on", "true", "200", expectedResultarr1, "1" };

        Object[][] dataSet = new Object[][] { arr1 };
        return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 6, 6);
    }

    @DataProvider
    public static Object[][] BountyServiceDP_queueOrder_pps(ITestContext testContext) throws JsonParseException, JsonMappingException, IOException, JAXBException {
		/*
		 * String orderId, String loginId, String sessionId, String binNumber,
		 * String pgDiscount, String giftCartAmount, String paymentStatus,
		 * String cartContext, String paymentMethod, String putOnHold, String
		 * expectedData
		 */
        BountyServiceHelper bountyServiceHelper = new BountyServiceHelper();
        String skuId[] = { BountyTestcasesConstants.BountyServiceTest.BountyServiceDP_queueOrder_pps_ITEM1+":2" };
        String login1 = BountyTestcasesConstants.BountyServiceTest.LOGIN_URL;
        String login2 = "bountytestuser002@myntra.com";

        HashMap<String, String> createOrderEntry = bountyServiceHelper.getOrderEntryFromCreateOrderResponse(skuId,
                                                                                                            RandomStringUtils.randomNumeric(6), login1, BountyTestcasesConstants.BountyServiceTest.PASSWORD, "0", "0", "0.0", BountyTestcasesConstants.Pincodes.PINCODE_560068, "responsive",
                                                                                                            "default", "on");

        HashMap<String, String> createOrderEntry1 = bountyServiceHelper.getOrderEntryFromCreateOrderResponse(skuId,
                                                                                                             RandomStringUtils.randomNumeric(6), login2, BountyTestcasesConstants.BountyServiceTest.PASSWORD, "0", "0", "0.0", BountyTestcasesConstants.Pincodes.PINCODE_560068, "responsive",
                                                                                                             "default", "on");

        // Valid Queue Order
        String expectedResultarr1 = "status.statusCode==1001::status.statusType=='SUCCESS'::status.statusMessage=='Order added successfully'";
        Object[] arr1 = {  Long.parseLong(createOrderEntry.get("orderID")), login1, createOrderEntry.get("xid"), "0", "0", "0",
                "SUCCESS", "DEFAULT", "on", "true", "1001", expectedResultarr1, "1" };

        // Invalid or Empty OrderID id to load is required for loading
        String expectedResultarr2 = "status.statusCode==54::status.statusType=='ERROR'::status.statusMessage=='id to load is required for loading'";
        Object[] arr2 = { Long.parseLong(createOrderEntry.get("orderID")), login1, createOrderEntry.get("xid"), "0", "0", "0", "SUCCESS", "DEFAULT", "on", "true",
                "8034", expectedResultarr2, "0" };

        // Invalid Payment Status
        String expectedResultarr3 = "status.statusCode==8011::status.statusType=='ERROR'::status.statusMessage=='Payment status SUCCESS123 is invalid'";
        Object[] arr3 = { Long.parseLong(createOrderEntry.get("orderID")), login1, createOrderEntry.get("xid"), "0", "0", "0",
                "SUCCESS123", "DEFAULT", "on", "true", "8011", expectedResultarr3, "0" };

        // Invalid Payment Status
        String expectedResultarr4 = "status.statusCode==8011::status.statusType=='ERROR'::status.statusMessage=='Payment status  is invalid'";
        Object[] arr4 = {  Long.parseLong(createOrderEntry.get("orderID")), login1, createOrderEntry.get("xid"), "0", "0", "0", "",
                "DEFAULT", "on", "true", "8011", expectedResultarr4, "0" };

        // Queue Order Which is not PP/PV status
        String expectedResultarr5 = "status.statusCode==8010::status.statusType=='ERROR'::status.statusMessage=='Order id "+ createOrderEntry.get("orderID") + " must be in PP or PV status for this operation'";

        Object[] arr5 = {  Long.parseLong(createOrderEntry.get("orderID")), login1, createOrderEntry.get("xid"), "0", "0", "0",
                "SUCCESS", "DEFAULT", "on", "true", "8034", expectedResultarr5, "0" };

        String expectedResultarr6 = "status.statusCode==1001::status.statusType=='SUCCESS'::status.statusMessage=='Order added successfully'";
        Object[] arr6 = { Long.parseLong(createOrderEntry1.get("orderID")), login2, createOrderEntry1.get("xid"), "0", "0", "0",
                "SUCCESS", "DEFAULT", "on", "false", "1001", expectedResultarr6, "1" };

        Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6 };
        return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 6, 6);
    }

    @DataProvider
    public static Object[][] BountyServiceDP_declineOrder_pps(ITestContext testContext) throws JsonParseException, JsonMappingException, IOException, JAXBException {
		/*
		 * String orderId, String loginId, String sessionId, String binNumber,
		 * String pgDiscount, String giftCartAmount, String paymentStatus,
		 * String cartContext, String paymentMethod, String declineReasonId,
		 * String statusCode, String expectedData
		 */

        BountyServiceHelper bountyServiceHelper = new BountyServiceHelper();
        String skuId[] = { BountyTestcasesConstants.BountyServiceTest.BountyServiceDP_declineOrder_pps_ITEM1+":2" };

        String login = BountyTestcasesConstants.BountyServiceTest.LOGIN_URL;

        String expectedResult = "status.statusCode==1001::status.statusType=='SUCCESS'::status.statusMessage=='Order added successfully'";
        HashMap<String, String> createOrderEntry = bountyServiceHelper.getOrderEntryFromCreateOrderResponse(skuId,RandomStringUtils.randomAlphanumeric(10), login, BountyTestcasesConstants.BountyServiceTest.PASSWORD, "0", "0", "0.0", BountyTestcasesConstants.Pincodes.PINCODE_560068, "responsive","DEFAULT", "on");

        Object[] arr1 = { Long.parseLong(createOrderEntry.get("orderID")), login, createOrderEntry.get("xid"), "0", "0", "0", "SUCCESS","DEFAULT", "on", "50", "1001", expectedResult, "1" };

        String expectedResultarr2 = "status.statusCode==8010::status.statusType=='ERROR'::status.statusMessage=='Order id "+ createOrderEntry.get("orderID") + " must be in PP or PV status for this operation'";

        Object[] arr2 = { Long.parseLong(createOrderEntry.get("orderID")), login, createOrderEntry.get("xid"), "0", "0", "0", "SUCCESS","DEFAULT", "on", "50", "8010", expectedResultarr2, "0" };

        Object[][] dataSet = new Object[][] { arr1,arr2 };
        return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 11, 11);
    }

    @DataProvider
    public static Object[][] declineOrderAfterPPSOnHoldPPS(ITestContext testContext) throws Exception {

        End2EndHelper end2EndHelper =  new End2EndHelper();
        String skuId[] = { BountyTestcasesConstants.BountyServiceTest.declineOrderAfterPPSOnHoldPPS_ITEM1+":2" };
        String skuIdVector[] = { BountyTestcasesConstants.BountyServiceTest.declineOrderAfterPPSOnHoldPPS_ITEM2+":2" };

        String login = BountyTestcasesConstants.BountyServiceTest.LOGIN_URL;
        String expectedResult = "status.statusCode==1001::status.statusType=='SUCCESS'::status.statusMessage=='Order added successfully'";

        end2EndHelper.updateloyalityAndCashBack(login, 100, 100);
        String orderID1 = end2EndHelper.createOrder(login, BountyTestcasesConstants.BountyServiceTest.PASSWORD, "6132352", skuId, "", true, true, false,"", false);
        String orderID2 = end2EndHelper.createOrder(login, BountyTestcasesConstants.BountyServiceTest.PASSWORD, "6132352", skuIdVector, "", true, true, false,"", false);

        Object[] arr1 = { orderID1, "SUCCESS", "828703", "69", "1001", expectedResult, "0" };

        Object[] arr2 = { orderID2, "SUCCESS", "3831", "69", "1001", expectedResult, "1" };

        Object[][] dataSet = new Object[][] { arr1, arr2};
        return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 11, 11);
    }


    @DataProvider(name="BountyServiceDP_createOrder_ppsDP")
    public static Object[][] BountyServiceDP_createOrder_ppsDP(ITestContext testContext) throws SQLException, IOException, JAXBException {

        /*CartEntry cartEntry, String ppsID, String login, String sessionId,
        String binNumber, String pgDiscount, String emiCharge, String address, String clientContext,
                String cartContext, String paymentMethod, String statusCode, String expectedData*/

        String login = BountyTestcasesConstants.BountyServiceTest.LOGIN_URL;

        String skuId1[] = {BountyTestcasesConstants.BountyServiceTest.BountyServiceDP_createOrder_ppsDP_ITEM1+":2", BountyTestcasesConstants.BountyServiceTest.BountyServiceDP_createOrder_ppsDP_ITEM2+":3" };
        String expectedData = "status.statusCode==1001::status.statusType=='SUCCESS'::data[0].paymentPpsId==12345";

        String expectedData_blankPPSID = "status.statusCode==8031::status.statusType=='ERROR'::status.statusMessage=='PPS Id missing'";
        String expectedData_InvalidSessionID = "status.statusCode==8022::status.statusType=='ERROR'::status.statusMessage=='Invalid session'";
        String expectedData_emptySessionID = "status.statusCode==8022::status.statusType=='ERROR'::status.statusMessage=='Invalid session'";
        String expectedData_emptyLoginID = "status.statusCode==8031::status.statusType=='ERROR'::status.statusMessage=='PPS Id missing'";

        HashMap<String, Object> orderEntry = bountyServiceHelper.getCreateOrderEntry(login, BountyTestcasesConstants.BountyServiceTest.PASSWORD, skuId1, "ADD","", "200", "50", "");

        Object[] arr2 = { orderEntry.get("cart"), null, login, orderEntry.get("xid"), "0", "0", "0.0", BountyTestcasesConstants.Pincodes.PINCODE_560068,
                "responsive", "DEFAULT", "on", "8031", expectedData_blankPPSID };

        Object[] arr4 = { orderEntry.get("\"\""), "12345", login, orderEntry.get("xid"), "0", "0", "0.0", BountyTestcasesConstants.Pincodes.PINCODE_560068, "responsive","DEFAULT", "on", "8007", "" };

        Object[] arr5 = { orderEntry.get("cartadasdasdasd"), "12345", login, orderEntry.get("xid"), "0", "0", "0.0", BountyTestcasesConstants.Pincodes.PINCODE_560068,"responsive", "DEFAULT", "on", "8007", "" };

        Object[] arr6 = { orderEntry.get("cart"), "12345", login, "JJMSJDIDJSSSKKKSK8JS98S", "0", "0", "0.0", BountyTestcasesConstants.Pincodes.PINCODE_560068,
                "responsive", "DEFAULT", "on", "500", expectedData_InvalidSessionID };

        Object[] arr7 = { orderEntry.get("cart"), "12345", login, "", "0", "0", "0.0", BountyTestcasesConstants.Pincodes.PINCODE_560068, "responsive",
                "DEFAULT", "on", "500", expectedData_emptySessionID };

        Object[] arr8 = { orderEntry.get("cart"), "12345", "", orderEntry.get("xid"), "0", "0", "0.0", BountyTestcasesConstants.Pincodes.PINCODE_560068,
                "responsive", "DEFAULT", "on", "500", expectedData_emptyLoginID };

        Object[] arr1 = { orderEntry.get("cart"), "12345", login, orderEntry.get("xid"), "0", "0", "0.0", BountyTestcasesConstants.Pincodes.PINCODE_560068,
                "responsive", "DEFAULT", "on", "1001", expectedData };

        //Object[][] dataSet = new Object[][] { arr1, arr2, arr4, arr5, arr6, arr7,arr8 };
        Object[][] dataSet = new Object[][] { arr2};
        return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 11, 11);
    }

    @DataProvider
    public static Object[][] orderCreationShouldFailIfTheIfTheOrderValueIsLessThanCODMinValue_PPSDP(ITestContext testContext) throws SQLException, UnsupportedEncodingException, JAXBException {

        String login = BountyTestcasesConstants.BountyServiceTest.LOGIN_URL;

        BountyServiceHelper bountyServiceHelper = new BountyServiceHelper();

        String skuId1[] = {BountyTestcasesConstants.BountyServiceTest.orderCreationShouldFailIfTheIfTheOrderValueIsLessThanCODMinValue_PPSDP_ITEM1+":9"};
        String expectedData_cod = "status.statusCode==8009::status.statusType=='ERROR'::status.statusMessage=='Cash on Delivery is currently available for order values between Rs.20-20000'";
        HashMap<String, Object> orderEntry = bountyServiceHelper.getCreateOrderEntry(login, BountyTestcasesConstants.BountyServiceTest.PASSWORD, skuId1, "ADD","", "5", "2", "");

        Object[] arr1 = { orderEntry.get("cart"), "12345", login, orderEntry.get("xid"), "0", "0", "0.0", "10518129",
                "responsive", "DEFAULT", "cod", "8009", expectedData_cod };

        Object[][] dataSet = new Object[][] { arr1 };
        return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 11, 11);
    }


    @DataProvider(name="orderCreationShouldFailIfTheIfTheOrderValueIsLessThanCODMinValue_CBLPCODDP")
    public static Object[][] orderCreationShouldFailIfTheIfTheOrderValueIsLessThanCODMinValue_CBLPCODDP(ITestContext testContext) throws SQLException, IOException, JAXBException, JSONException {

        String login = BountyTestcasesConstants.BountyServiceTest.LOGIN_URL;


        BountyServiceHelper bountyServiceHelper = new BountyServiceHelper();
        End2EndHelper end2EndHelper = new End2EndHelper();

        String skuId1[] = {BountyTestcasesConstants.BountyServiceTest.orderCreationShouldFailIfTheIfTheOrderValueIsLessThanCODMinValue_CBLPCODDP_ITEM1+":9"};

        end2EndHelper.updateloyalityAndCashBack(login, 2, 5);
        String expectedData_cod = "status.statusCode==8009::status.statusType=='ERROR'::status.statusMessage=='Cash on Delivery is currently available for order values between Rs.20-20000'";
        HashMap<String, Object> orderEntry = bountyServiceHelper.getCreateOrderEntry(login, BountyTestcasesConstants.BountyServiceTest.PASSWORD, skuId1, "ADD","", "5", "2", "");

        Object[] arr1 = { orderEntry.get("cart"), "12345", login, orderEntry.get("xid"), "0", "0", "0.0", "10518129",
                "responsive", "DEFAULT", "cod", "8009", expectedData_cod };

        Object[][] dataSet = new Object[][] { arr1 };
        return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 11, 11);
    }

    @DataProvider(name="orderCreationShouldPassIfTheIfTheOrderValueIsMoreThanCODMinValue_CBLPCODDP")
    public static Object[][] orderCreationShouldPassIfTheIfTheOrderValueIsMoreThanCODMinValue_CBLPCODDP(ITestContext testContext) throws SQLException, IOException, JAXBException, JSONException {

        String login = BountyTestcasesConstants.BountyServiceTest.LOGIN_URL;

        BountyServiceHelper bountyServiceHelper = new BountyServiceHelper();
        End2EndHelper end2EndHelper = new End2EndHelper();

        String skuId1[] = {BountyTestcasesConstants.BountyServiceTest.orderCreationShouldPassIfTheIfTheOrderValueIsMoreThanCODMinValue_CBLPCODDP_ITEM1+":12"};

        end2EndHelper.updateloyalityAndCashBack(login, 2, 5);
        String expectedData_cod = "status.statusCode==1001::status.statusType=='SUCCESS'";
        HashMap<String, Object> orderEntry = bountyServiceHelper.getCreateOrderEntry(login, BountyTestcasesConstants.BountyServiceTest.PASSWORD, skuId1, "ADD","", "5", "2", "");

        Object[] arr1 = { orderEntry.get("cart"), "12345", login, orderEntry.get("xid"), "0", "0", "0.0", "10518129",
                "responsive", "DEFAULT", "cod", "1001", expectedData_cod };

        Object[][] dataSet = new Object[][] { arr1 };
        return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 11, 11);
    }


    @DataProvider(name="orderCreationShouldPassIfTheIfTheOrderValueIsMoreThanCODMinValue_CODDP")
    public static Object[][] orderCreationShouldPassIfTheIfTheOrderValueIsMoreThanCODMinValue_CODDP(ITestContext testContext) throws SQLException, UnsupportedEncodingException, JAXBException {

        String login = BountyTestcasesConstants.BountyServiceTest.LOGIN_URL;
        BountyServiceHelper bountyServiceHelper = new BountyServiceHelper();

        String skuId1[] = {BountyTestcasesConstants.BountyServiceTest.orderCreationShouldPassIfTheIfTheOrderValueIsMoreThanCODMinValue_CODDP_ITEM1+":11"};

        String expectedData_cod = "status.statusCode==1001::status.statusType=='SUCCESS'";
        HashMap<String, Object> orderEntry = bountyServiceHelper.getCreateOrderEntry(login, BountyTestcasesConstants.BountyServiceTest.PASSWORD, skuId1, "ADD","", "", "", "");

        Object[] arr1 = { orderEntry.get("cart"), "12345", login, orderEntry.get("xid"), "0", "0", "0.0", "6129277",
                "responsive", "DEFAULT", "cod", "200", expectedData_cod };

        Object[][] dataSet = new Object[][] { arr1 };
        return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 11, 11);
    }


    @DataProvider(name="orderCreationShouldFailIfTheOrderValueIsMoreThanCODMaxValue_CODDP")
    public static Object[][] orderCreationShouldFailIfTheOrderValueIsMoreThanCODMaxValue_CODDP(ITestContext testContext) throws SQLException, UnsupportedEncodingException, JAXBException {

        String login = BountyTestcasesConstants.BountyServiceTest.LOGIN_URL;

        BountyServiceHelper bountyServiceHelper = new BountyServiceHelper();


        String skuId1[] = {BountyTestcasesConstants.BountyServiceTest.orderCreationShouldFailIfTheOrderValueIsMoreThanCODMaxValue_CODDP_ITEM1+":7"};

        String expectedData_cod = "status.statusCode==8009::status.statusType=='ERROR'::status.statusMessage=='Cash on Delivery is currently available for order values between Rs.20-20000'";
        HashMap<String, Object> orderEntry = bountyServiceHelper.getCreateOrderEntry(login, BountyTestcasesConstants.BountyServiceTest.PASSWORD, skuId1, "ADD","", "", "", "");

        Object[] arr1 = { orderEntry.get("cart"), "12345", login, orderEntry.get("xid"), "0", "0", "0.0", BountyTestcasesConstants.Pincodes.PINCODE_560068,
                "responsive", "DEFAULT", "cod", "8009", expectedData_cod };

        Object[][] dataSet = new Object[][] { arr1 };
        return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 11, 11);
    }


    @DataProvider(name="orderCreationShouldFailIfTheOrderValueIsMoreThanCODMaxValue_CBLPCODDP")
    public static Object[][] orderCreationShouldFailIfTheOrderValueIsMoreThanCODMaxValue_CBLPCODDP(ITestContext testContext) throws SQLException, IOException, JAXBException, JSONException {

        String login = BountyTestcasesConstants.BountyServiceTest.LOGIN_URL;

        DBUtilities.exUpdateQuery("update myntra_tools.core_application_properties set value='20-2999' where name='cod.limit.range';","myntra_oms");

        BountyServiceHelper bountyServiceHelper = new BountyServiceHelper();
        End2EndHelper end2EndHelper = new End2EndHelper();
        bountyServiceHelper.refreshBountyApplicationPropertyCache();
        End2EndHelper.sleep(120000L);

        String skuId1[] = {BountyTestcasesConstants.BountyServiceTest.orderCreationShouldFailIfTheOrderValueIsMoreThanCODMaxValue_CBLPCODDP_ITEM1+":1"};

        end2EndHelper.updateloyalityAndCashBack(login, 100, 200);
        String expectedData_cod = "status.statusCode==1001::status.statusType=='SUCCESS'";
        HashMap<String, Object> orderEntry = bountyServiceHelper.getCreateOrderEntry(login, BountyTestcasesConstants.BountyServiceTest.PASSWORD, skuId1, "ADD","", "200", "50", "");

        Object[] arr1 = { orderEntry.get("cart"), "12345", login, orderEntry.get("xid"), "0", "0", "0.0", BountyTestcasesConstants.Pincodes.PINCODE_560068,
                "responsive", "DEFAULT", "cod", "1001", expectedData_cod };

        Object[][] dataSet = new Object[][] { arr1 };
        return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 11, 11);
    }

    @DataProvider(name="VerifyOrderStatusShouldBePVWhenPaymentIsVerifiedOnCoDOrderInBountyDP")
    public static Object[][] VerifyOrderStatusShouldBePVWhenPaymentIsVerifiedOnCoDOrderInBountyDP(ITestContext testContext) throws SQLException, IOException, JAXBException, JSONException {

        String login = BountyTestcasesConstants.BountyServiceTest.LOGIN_URL;

        DBUtilities.exUpdateQuery("update myntra_tools.core_application_properties set value='20-2999' where name='cod.limit.range';","myntra_oms");

        BountyServiceHelper bountyServiceHelper = new BountyServiceHelper();
        End2EndHelper end2EndHelper = new End2EndHelper();
        bountyServiceHelper.refreshBountyApplicationPropertyCache();
        End2EndHelper.sleep(120000L);

        String skuId1[] = {BountyTestcasesConstants.BountyServiceTest.VerifyOrderStatusShouldBePVWhenPaymentIsVerifiedOnCoDOrderInBountyDP_ITEM1+":2"};

        end2EndHelper.updateloyalityAndCashBack(login, 100, 200);
        String expectedData_cod = "status.statusCode==1001::status.statusType=='SUCCESS'";
        HashMap<String, Object> orderEntry = bountyServiceHelper.getCreateOrderEntry(login, BountyTestcasesConstants.BountyServiceTest.PASSWORD, skuId1, "ADD","", "200", "50", "");

        Object[] arr1 = { orderEntry.get("cart"), "12345", login, orderEntry.get("xid"), "0", "0", "0.0", BountyTestcasesConstants.Pincodes.PINCODE_560068,
                "responsive", "DEFAULT", "cod", "1001", expectedData_cod };

        Object[][] dataSet = new Object[][] { arr1 };
        return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 11, 11);
    }

    @DataProvider(name="VerifyOrderStatusShouldBePVWhenPaymentIsVerifiedOnCoDOrderInBountyDP1")
    public static Object[][] VerifyOrderStatusShouldBePVWhenPaymentIsVerifiedOnCoDOrderInBountyDP1(ITestContext testContext) throws SQLException, IOException, JAXBException, JSONException {

        String login = BountyTestcasesConstants.BountyServiceTest.LOGIN_URL;

        DBUtilities.exUpdateQuery("update myntra_tools.core_application_properties set value='20-2999' where name='cod.limit.range';","myntra_oms");

        BountyServiceHelper bountyServiceHelper = new BountyServiceHelper();
        End2EndHelper end2EndHelper = new End2EndHelper();
        bountyServiceHelper.refreshBountyApplicationPropertyCache();
        End2EndHelper.sleep(120000L);

        String skuId1[] = {BountyTestcasesConstants.BountyServiceTest.VerifyOrderStatusShouldBePVWhenPaymentIsVerifiedOnCoDOrderInBountyDP1_ITEM1+":2"};

        end2EndHelper.updateloyalityAndCashBack(login, 100, 200);
        String expectedData_cod = "status.statusCode==1001::status.statusType=='SUCCESS'";
        HashMap<String, Object> orderEntry = bountyServiceHelper.getCreateOrderEntry(login, BountyTestcasesConstants.BountyServiceTest.PASSWORD, skuId1, "ADD","", "200", "50", "");

        Object[] arr1 = { orderEntry.get("cart"), "12345", login, orderEntry.get("xid"), "0", "0", "0.0", BountyTestcasesConstants.Pincodes.PINCODE_560068,
                "responsive", "DEFAULT", "ON", "200", expectedData_cod };

        Object[][] dataSet = new Object[][] { arr1 };
        return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 11, 11);
    }
    @DataProvider(name="VerifyOrderStatusShouldBePPWhenPaymentIsVerifiedOnCoDOrderInBountyDP")
    public static Object[][] VerifyOrderStatusShouldBePPWhenPaymentIsVerifiedOnCoDOrderInBountyDP(ITestContext testContext) throws SQLException, IOException, JAXBException, JSONException {

        String login = BountyTestcasesConstants.BountyServiceTest.LOGIN_URL;

        DBUtilities.exUpdateQuery("update myntra_tools.core_application_properties set value='20-2999' where name='cod.limit.range';","myntra_oms");

        BountyServiceHelper bountyServiceHelper = new BountyServiceHelper();
        End2EndHelper end2EndHelper = new End2EndHelper();
        bountyServiceHelper.refreshBountyApplicationPropertyCache();
        End2EndHelper.sleep(120000L);

        String skuId1[] = {BountyTestcasesConstants.BountyServiceTest.VerifyOrderStatusShouldBePPWhenPaymentIsVerifiedOnCoDOrderInBountyDP_ITEM1+":2"};

        end2EndHelper.updateloyalityAndCashBack(login, 100, 200);
        String expectedData_cod = "status.statusCode==1001::status.statusType=='SUCCESS'";
        HashMap<String, Object> orderEntry = bountyServiceHelper.getCreateOrderEntry(login, BountyTestcasesConstants.BountyServiceTest.PASSWORD, skuId1, "ADD","", "200", "50", "");

        Object[] arr1 = { orderEntry.get("cart"), "12345", login, orderEntry.get("xid"), "0", "0", "0.0", BountyTestcasesConstants.Pincodes.PINCODE_560068,
                "responsive", "DEFAULT", "cod", "200", expectedData_cod };

        Object[][] dataSet = new Object[][] { arr1 };
        return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 11, 11);
    }

    @DataProvider
    public static Object[][] verifyTheErrorCodeWhenAlreadyQueuedOrderResultInErrorResponseWithErrorCodeDP(ITestContext testContext) throws JsonParseException, JsonMappingException, IOException, JAXBException {
		/*
		 * String orderId, String loginId, String sessionId, String binNumber,
		 * String pgDiscount, String giftCartAmount, String paymentStatus,
		 * String cartContext, String paymentMethod, String putOnHold, String
		 * expectedData
		 */
        BountyServiceHelper bountyServiceHelper = new BountyServiceHelper();
        String skuId[] = { BountyTestcasesConstants.BountyServiceTest.verifyTheErrorCodeWhenAlreadyQueuedOrderResultInErrorResponseWithErrorCodeDP_ITEM1+":2" };
        String login1 = BountyTestcasesConstants.BountyServiceTest.LOGIN_URL;
        String login2 = "bountytestuser002@myntra.com";

        HashMap<String, String> createOrderEntry = bountyServiceHelper.getOrderEntryFromCreateOrderResponse(skuId,
                                                                                                            RandomStringUtils.randomNumeric(6), login1, BountyTestcasesConstants.BountyServiceTest.PASSWORD, "0", "0", "0.0", BountyTestcasesConstants.Pincodes.PINCODE_560068, "responsive",
                                                                                                            "default", "on");

        HashMap<String, String> createOrderEntry1 = bountyServiceHelper.getOrderEntryFromCreateOrderResponse(skuId,
                                                                                                             RandomStringUtils.randomNumeric(6), login2, BountyTestcasesConstants.BountyServiceTest.PASSWORD, "0", "0", "0.0", BountyTestcasesConstants.Pincodes.PINCODE_560068, "responsive",
                                                                                                             "default", "on");

        // Valid Queue Order
        String expectedResultarr1 = "status.statusCode==1001::status.statusType=='SUCCESS'::status.statusMessage=='Order added successfully'";
        Object[] arr1 = {  Long.parseLong(createOrderEntry.get("orderID")), login1, createOrderEntry.get("xid"), "0", "0", "0",
                "SUCCESS", "DEFAULT", "on", "true", "1001", expectedResultarr1, "1" };

        // Invalid or Empty OrderID id to load is required for loading
        String expectedResultarr2 = "status.statusCode==54::status.statusType=='ERROR'::status.statusMessage=='id to load is required for loading'";
        Object[] arr2 = { Long.parseLong(createOrderEntry.get("orderID")), login1, createOrderEntry.get("xid"), "0", "0", "0", "SUCCESS", "DEFAULT", "on", "true",
                "8034", expectedResultarr2, "0" };


        Object[][] dataSet = new Object[][] { arr2};
        return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 6, 6);
    }

    @DataProvider
    public static Object[][] verifyQWithoutOnHoldWhenSuccessfullyDebitingAllThePaymentInstrumentsDP(ITestContext testContext) throws JsonParseException, JsonMappingException, IOException, JAXBException {
		/*
		 * String orderId, String loginId, String sessionId, String binNumber,
		 * String pgDiscount, String giftCartAmount, String paymentStatus,
		 * String cartContext, String paymentMethod, String putOnHold, String
		 * expectedData
		 */
        BountyServiceHelper bountyServiceHelper = new BountyServiceHelper();
        String skuId[] = { BountyTestcasesConstants.BountyServiceTest.verifyQWithoutOnHoldWhenSuccessfullyDebitingAllThePaymentInstrumentsDP_ITEM1+":2" };
        String login1 = BountyTestcasesConstants.BountyServiceTest.LOGIN_URL;
        String login2 = "bountytestuser002@myntra.com";

        HashMap<String, String> createOrderEntry = bountyServiceHelper.getOrderEntryFromCreateOrderResponse(skuId,
                                                                                                            RandomStringUtils.randomNumeric(6), login1, BountyTestcasesConstants.BountyServiceTest.PASSWORD, "0", "0", "0.0", BountyTestcasesConstants.Pincodes.PINCODE_560068, "responsive",
                                                                                                            "default", "on");

        HashMap<String, String> createOrderEntry1 = bountyServiceHelper.getOrderEntryFromCreateOrderResponse(skuId,
                                                                                                             RandomStringUtils.randomNumeric(6), login2, BountyTestcasesConstants.BountyServiceTest.PASSWORD, "0", "0", "0.0", BountyTestcasesConstants.Pincodes.PINCODE_560068, "responsive",
                                                                                                             "default", "on");

        // Valid Queue Order
        String expectedResultarr1 = "status.statusCode==1001::status.statusType=='SUCCESS'::status.statusMessage=='Order added successfully'";
        Object[] arr1 = {  Long.parseLong(createOrderEntry.get("orderID")), login1, createOrderEntry.get("xid"), "0", "0", "0",
                "SUCCESS", "DEFAULT", "on", "true", "1001", expectedResultarr1, "1" };

        // Invalid or Empty OrderID id to load is required for loading
        String expectedResultarr2 = "status.statusCode==54::status.statusType=='ERROR'::status.statusMessage=='id to load is required for loading'";
        Object[] arr2 = { Long.parseLong(createOrderEntry.get("orderID")), login1, createOrderEntry.get("xid"), "0", "0", "0", "SUCCESS", "DEFAULT", "on", "true",
                "1001", expectedResultarr2, "0" };


        Object[][] dataSet = new Object[][] { arr2};
        return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 6, 6);
    }

    @DataProvider
    public static Object[][] verifyOrderStatusIfPaymentIsPPOrderStatusIsQWithOnhold39WhenPreConfirmationStateWhileDebitingOfAsyncPaymentInstrumentsDP(ITestContext testContext) throws JsonParseException, JsonMappingException, IOException, JAXBException, SQLException, JSONException {
		/*
		 * String orderId, String loginId, String sessionId, String binNumber,
		 * String pgDiscount, String giftCartAmount, String paymentStatus,
		 * String cartContext, String paymentMethod, String putOnHold, String
		 * expectedData
		 */
        BountyServiceHelper bountyServiceHelper = new BountyServiceHelper();
        String skuId[] = { BountyTestcasesConstants.BountyServiceTest.verifyOrderStatusIfPaymentIsPPOrderStatusIsQWithOnhold39WhenPreConfirmationStateWhileDebitingOfAsyncPaymentInstrumentsDP_ITEM1+":2" };
        String login1 = BountyTestcasesConstants.BountyServiceTest.LOGIN_URL;
        End2EndHelper end2EndHelper = new End2EndHelper();
        //end2EndHelper.updateloyalityAndCashBack(login1, 1000, 2000);
        HashMap<String, String> createOrderEntry = bountyServiceHelper.getOrderEntryFromCreateOrderResponse(skuId,
                                                                                                            RandomStringUtils.randomNumeric(6), login1, BountyTestcasesConstants.BountyServiceTest.PASSWORD, "0", "0", "0.0", BountyTestcasesConstants.Pincodes.PINCODE_560068, "responsive",
                                                                                                            "default", "on");

        // Valid Queue Order
        String expectedResultarr1 = "status.statusCode==1004::status.statusType=='SUCCESS'::status.statusMessage=='Order updated successfully'";
        Object[] arr1 = {  Long.parseLong(createOrderEntry.get("orderID")), login1, createOrderEntry.get("xid"), "0", "0", "0",
                "SUCCESS", "DEFAULT", "ON", "true", "200", expectedResultarr1, "1" };

        Object[][] dataSet = new Object[][] { arr1 };
        return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 6, 6);
    }

    @DataProvider(name="verifyOrderStatusIfPaymentIsPVOrderStatusIsQOnhold39DP")
    public static Object[][] verifyOrderStatusIfPaymentIsPVOrderStatusIsQOnhold39DP(ITestContext testContext) throws SQLException, IOException, JAXBException, JSONException {

        String login = BountyTestcasesConstants.BountyServiceTest.LOGIN_URL;

        DBUtilities.exUpdateQuery("update myntra_tools.core_application_properties set value='20-2999' where name='cod.limit.range';","myntra_oms");

        BountyServiceHelper bountyServiceHelper = new BountyServiceHelper();
        End2EndHelper end2EndHelper = new End2EndHelper();
        bountyServiceHelper.refreshBountyApplicationPropertyCache();
        End2EndHelper.sleep(120000L);

        String skuId1[] = {BountyTestcasesConstants.BountyServiceTest.verifyOrderStatusIfPaymentIsPVOrderStatusIsQOnhold39DP_ITEM1+":2"};

        end2EndHelper.updateloyalityAndCashBack(login, 1000, 2000);
        String expectedData_cod = "status.statusCode==1001::status.statusType=='SUCCESS'";
        HashMap<String, Object> orderEntry = bountyServiceHelper.getCreateOrderEntry(login, BountyTestcasesConstants.BountyServiceTest.PASSWORD, skuId1, "ADD","", "200", "50", "");

        Object[] arr1 = { orderEntry.get("cart"), "12345", login, orderEntry.get("xid"), "0", "0", "0.0", BountyTestcasesConstants.Pincodes.PINCODE_560068,
                "responsive", "DEFAULT", "ON", "1001", expectedData_cod };

        Object[][] dataSet = new Object[][] { arr1 };
        return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 11, 11);
    }



    @DataProvider(name="verifyOrderStatusIfPaymentIsPPOrderStatusIsQOnhold5DP")
    public static Object[][] verifyOrderStatusIfPaymentIsPVOrderStatusIsQOnhold5DP(ITestContext testContext) throws SQLException, IOException, JAXBException, JSONException {
        End2EndHelper end2EndHelper = new End2EndHelper();
        String login = BountyTestcasesConstants.BountyServiceTest.LOGIN_URL;

        DBUtilities.exUpdateQuery("update myntra_tools.core_application_properties set value='20-2999' where name='cod.limit.range';","myntra_oms");

        BountyServiceHelper bountyServiceHelper = new BountyServiceHelper();
        bountyServiceHelper.refreshBountyApplicationPropertyCache();
        End2EndHelper.sleep(120000L);

        String skuId1[] = {BountyTestcasesConstants.BountyServiceTest.verifyOrderStatusIfPaymentIsPVOrderStatusIsQOnhold5DP_ITEM1+":2"};

        //end2EndHelper.updateloyalityAndCashBack(login, 1000, 2000);
        String expectedData_cod = "status.statusCode==1001::status.statusType=='SUCCESS'";
        HashMap<String, Object> orderEntry = bountyServiceHelper.getCreateOrderEntry(login, BountyTestcasesConstants.BountyServiceTest.PASSWORD, skuId1, "ADD","", "200", "50", "");

        Object[] arr1 = { orderEntry.get("cart"), "12345", login, orderEntry.get("xid"), "0", "0", "0.0", BountyTestcasesConstants.Pincodes.PINCODE_560068,"responsive", "DEFAULT", "COD", "1001", expectedData_cod };

        Object[][] dataSet = new Object[][] { arr1 };
        return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 11, 11);
    }

    @DataProvider
    public static Object[][] verifyOrderStatusIfPaymentIsPPOrderStatusIsQAndOnhold5DP(ITestContext testContext) throws JsonParseException, JsonMappingException, IOException, JAXBException, SQLException, JSONException {
		/*
		 * String orderId, String loginId, String sessionId, String binNumber,
		 * String pgDiscount, String giftCartAmount, String paymentStatus,
		 * String cartContext, String paymentMethod, String putOnHold, String
		 * expectedData
		 */
        BountyServiceHelper bountyServiceHelper = new BountyServiceHelper();
        String skuId[] = { BountyTestcasesConstants.BountyServiceTest.verifyOrderStatusIfPaymentIsPPOrderStatusIsQAndOnhold5DP_ITEM1+":2" };
        String login1 = BountyTestcasesConstants.BountyServiceTest.LOGIN_URL;
        End2EndHelper end2EndHelper = new End2EndHelper();
        //end2EndHelper.updateloyalityAndCashBack(login1, 100, 200);
        HashMap<String, String> createOrderEntry = bountyServiceHelper.getOrderEntryFromCreateOrderResponse(skuId,
                                                                                                            RandomStringUtils.randomNumeric(6), login1, BountyTestcasesConstants.BountyServiceTest.PASSWORD, "0", "0", "0.0", BountyTestcasesConstants.Pincodes.PINCODE_560068, "responsive",
                                                                                                            "default", "on");

        // Valid Queue Order
        String expectedResultarr1 = "status.statusCode==1004::status.statusType=='SUCCESS'::status.statusMessage=='Order updated successfully'";
        Object[] arr1 = {  Long.parseLong(createOrderEntry.get("orderID")), login1, createOrderEntry.get("xid"), "0", "0", "0",
                "SUCCESS", "DEFAULT", "ON", "true", "200", expectedResultarr1, "1" };

        Object[][] dataSet = new Object[][] { arr1 };
        return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 6, 6);
    }
    @DataProvider
    public static Object[][] verifyOrderStatusIfPaymentIsPVOrderStatusIsQAndOnhold5DP(ITestContext testContext) throws JsonParseException, JsonMappingException, IOException, JAXBException, SQLException, JSONException {
		/*
		 * String orderId, String loginId, String sessionId, String binNumber,
		 * String pgDiscount, String giftCartAmount, String paymentStatus,
		 * String cartContext, String paymentMethod, String putOnHold, String
		 * expectedData
		 */
        BountyServiceHelper bountyServiceHelper = new BountyServiceHelper();
        String skuId[] = { BountyTestcasesConstants.BountyServiceTest.verifyOrderStatusIfPaymentIsPVOrderStatusIsQAndOnhold5DP_ITEM1+":2" };
        String login1 = BountyTestcasesConstants.BountyServiceTest.LOGIN_URL;
        End2EndHelper end2EndHelper = new End2EndHelper();
        //end2EndHelper.updateloyalityAndCashBack(login1, 10000, 20000);
        HashMap<String, String> createOrderEntry = bountyServiceHelper.getOrderEntryFromCreateOrderResponse(skuId,
                                                                                                            RandomStringUtils.randomNumeric(6), login1, BountyTestcasesConstants.BountyServiceTest.PASSWORD, "0", "0", "0.0", BountyTestcasesConstants.Pincodes.PINCODE_560068, "responsive",
                                                                                                            "default", "COD");

        // Valid Queue Order
        String expectedResultarr1 = "status.statusCode==1004::status.statusType=='SUCCESS'::status.statusMessage=='Order updated successfully'";
        Object[] arr1 = {  Long.parseLong(createOrderEntry.get("orderID")), login1, createOrderEntry.get("xid"), "0", "0", "0",
                "SUCCESS", "DEFAULT", "COD", "true", "200", expectedResultarr1, "1" };

        Object[][] dataSet = new Object[][] { arr1 };
        return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 6, 6);
    }

    @DataProvider(name="verifyOrderStatusIfPaymentIsPVOrderStatusWithoutOnHoldDP")
    public static Object[][] verifyOrderStatusIfPaymentIsPVOrderStatusWithoutOnHoldDP(ITestContext testContext) throws SQLException, IOException, JAXBException, JSONException {

        String login = BountyTestcasesConstants.BountyServiceTest.LOGIN_URL;

        DBUtilities.exUpdateQuery("update myntra_tools.core_application_properties set value='20-2999' where name='cod.limit.range';","myntra_oms");

        BountyServiceHelper bountyServiceHelper = new BountyServiceHelper();
        End2EndHelper end2EndHelper = new End2EndHelper();
        bountyServiceHelper.refreshBountyApplicationPropertyCache();
        End2EndHelper.sleep(120000L);

        String skuId1[] = {BountyTestcasesConstants.BountyServiceTest.verifyOrderStatusIfPaymentIsPVOrderStatusWithoutOnHoldDP_ITEM1+":2"};

        //end2EndHelper.updateloyalityAndCashBack(login, 100, 200);
        String expectedData_cod = "status.statusCode==1001::status.statusType=='SUCCESS'";
        HashMap<String, Object> orderEntry = bountyServiceHelper.getCreateOrderEntry(login, BountyTestcasesConstants.BountyServiceTest.PASSWORD, skuId1, "ADD","", "200", "50", "");

        Object[] arr1 = { orderEntry.get("cart"), "12345", login, orderEntry.get("xid"), "0", "0", "0.0", BountyTestcasesConstants.Pincodes.PINCODE_560068,
                "responsive", "DEFAULT", "cod", "200", expectedData_cod };

        Object[][] dataSet = new Object[][] { arr1 };
        return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 11, 11);
    }

    @DataProvider
    public static Object[][] verifyOrderStatusIfPaymentIsPVOrderStatusIsQAndOnholdDP(ITestContext testContext) throws JsonParseException, JsonMappingException, IOException, JAXBException, SQLException, JSONException {
		/*
		 * String orderId, String loginId, String sessionId, String binNumber,
		 * String pgDiscount, String giftCartAmount, String paymentStatus,
		 * String cartContext, String paymentMethod, String putOnHold, String
		 * expectedData
		 */
        BountyServiceHelper bountyServiceHelper = new BountyServiceHelper();
        String skuId[] = { BountyTestcasesConstants.BountyServiceTest.verifyOrderStatusIfPaymentIsPVOrderStatusIsQAndOnholdDP_ITEM1+":2" };
        String login1 = BountyTestcasesConstants.BountyServiceTest.LOGIN_URL;
        End2EndHelper end2EndHelper = new End2EndHelper();
        //end2EndHelper.updateloyalityAndCashBack(login1, 100, 200);
        HashMap<String, String> createOrderEntry = bountyServiceHelper.getOrderEntryFromCreateOrderResponse(skuId,
                                                                                                            RandomStringUtils.randomNumeric(6), login1, BountyTestcasesConstants.BountyServiceTest.PASSWORD, "0", "0", "0.0", BountyTestcasesConstants.Pincodes.PINCODE_560068, "responsive",
                                                                                                            "default", "cod");

        // Valid Queue Order
        String expectedResultarr1 = "status.statusCode==1004::status.statusType=='SUCCESS'::status.statusMessage=='Order updated successfully'";
        Object[] arr1 = {  Long.parseLong(createOrderEntry.get("orderID")), login1, createOrderEntry.get("xid"), "0", "0", "0",
                "SUCCESS", "DEFAULT", "cod", "true", "200", expectedResultarr1, "1" };

        Object[][] dataSet = new Object[][] { arr1 };
        return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 6, 6);
    }
}