/*package com.myntra.apiTests.erpservices.oms.dp;

import static org.testng.Assert.assertEquals;

import com.myntra.absolut.cart.client.response.entry.CartEntry;
import com.myntra.apiTests.end2end.End2EndHelper;
import com.myntra.apiTests.erpservices.atp.ATPServiceHelper;
import com.myntra.apiTests.erpservices.bounty.BountyServiceHelper;
import com.myntra.apiTests.erpservices.oms.OMSServiceHelper;
import com.myntra.apiTests.erpservices.oms.Test.OMSTCConstants;
import com.myntra.apiTests.erpservices.sellerapis.SellerConfig;
import com.myntra.apiTests.erpservices.ims.IMSServiceHelper;
import com.myntra.bounty.client.response.OrderCreationResponse;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.Toolbox;
import com.myntra.lordoftherings.boromir.DBUtilities;
import com.myntra.oms.client.entry.OrderEntry;
import com.myntra.oms.client.entry.OrderReleaseEntry;
import com.myntra.oms.client.response.OrderResponse;
import com.myntra.test.commons.testbase.BaseTest;

import org.apache.log4j.Logger;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import javax.xml.bind.JAXBException;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class CancellationTillHubDP extends BaseTest {
	
    static Initialize init = new Initialize("/Data/configuration");
    static OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
	static BountyServiceHelper bountyServiceHelper = new BountyServiceHelper();
	static End2EndHelper end2EndHelper = new End2EndHelper();
	static ATPServiceHelper atpServiceHelper = new ATPServiceHelper();
	static IMSServiceHelper imsServiceHelper = new IMSServiceHelper();
	private static Logger log = Logger.getLogger(OMSServiceHelper.class);
	static String login = OMSTCConstants.CancellationTillHubTest.LOGIN_URL;
	static String password = OMSTCConstants.CancellationTillHubTest.PASSWORD;
	private static String releaseStatus1= null;
	private static String orderReleaseID1,orderReleaseID2,orderReleaseID3,orderReleaseID4;
	private static String orderID,orderID1,orderID2,orderID3,orderID4,orderID5,orderID6,orderID7,orderID8;
	private static HashMap<String, Object> orderEntry1,orderEntry2,orderEntry3,orderEntry4,orderEntry5,orderEntry6,orderEntry7,orderEntry8;
	private static OrderResponse orderResponse1,orderResponse2,orderResponse3,orderResponse4,orderResponse5,orderResponse6,orderResponse7,orderResponse8;
	private static OrderCreationResponse orderQueueResponse;
	private static boolean orderStatus,orderStatus2,orderStatus3,orderStatus4,orderStatus5,orderStatus6,orderStatus7,orderStatus8;
	private static OrderCreationResponse orderQueueResponse2,orderQueueResponse3,orderQueueResponse4;
	private static OrderCreationResponse orderQueueResponse5,orderQueueResponse6,orderQueueResponse7,orderQueueResponse8;
	private static List<OrderReleaseEntry> orderReleaseEntries;
	private static OrderEntry orderEntry;
	private static String orderReleaseID5;
	private static String orderReleaseID6;
	private static String orderReleaseID7;
	private static String orderReleaseID8;
	private static String orderReleaseId;
	private static String orderReleaseID9;
	private static String orderReleaseID10;
	private static Long vectorSellerID = SellerConfig.getSellerID(SellerConfig.SellerName.HANDH);
	private static String supplyTypeOnHand = "ON_HAND";

	
	@DataProvider
    public static Object[][] checkIsCancellableOrderQOnHold39_6_7_9_24_25_26_28StatusDP(ITestContext testContext) throws IOException, JAXBException {

        String skuId1[] = {OMSTCConstants.OtherSkus.skuId_3832+":1"};
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3832+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":1000:0:"+vectorSellerID+":1"},supplyTypeOnHand);
		imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3832+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":1000:0:"+vectorSellerID},supplyTypeOnHand);

        orderEntry1 = bountyServiceHelper.getCreateOrderEntry(login, "123456", skuId1, "ADD","", "200", "50", "");
        orderResponse1 = bountyServiceHelper.createOrder((CartEntry) orderEntry1.get("cart"), "12345", login, ""+orderEntry1.get("xid"), "0", "0", "0.0", OMSTCConstants.Pincodes.PINCODE_560068,
                                                                       "responsive", "DEFAULT", "cod");
                
        orderEntry2 = bountyServiceHelper.getCreateOrderEntry(login, "123456", skuId1, "ADD","", "200", "50", "");
        orderResponse2 = bountyServiceHelper.createOrder((CartEntry) orderEntry2.get("cart"), "12345", login, ""+orderEntry2.get("xid"), "0", "0", "0.0", OMSTCConstants.Pincodes.PINCODE_560068,
                                                                       "responsive", "DEFAULT", "cod");
                
        orderEntry3 = bountyServiceHelper.getCreateOrderEntry(login, "123456", skuId1, "ADD","", "200", "50", "");
        orderResponse3 = bountyServiceHelper.createOrder((CartEntry) orderEntry3.get("cart"), "12345", login, ""+orderEntry3.get("xid"), "0", "0", "0.0", OMSTCConstants.Pincodes.PINCODE_560068,
                                                                       "responsive", "DEFAULT", "cod");
        
        orderEntry4 = bountyServiceHelper.getCreateOrderEntry(login, "123456", skuId1, "ADD","", "200", "50", "");
        orderResponse4 = bountyServiceHelper.createOrder((CartEntry) orderEntry4.get("cart"), "12345", login, ""+orderEntry4.get("xid"), "0", "0", "0.0", OMSTCConstants.Pincodes.PINCODE_560068,
                                                                       "responsive", "DEFAULT", "cod");
        
        orderEntry5 = bountyServiceHelper.getCreateOrderEntry(login, "123456", skuId1, "ADD","", "200", "50", "");
        orderResponse5 = bountyServiceHelper.createOrder((CartEntry) orderEntry5.get("cart"), "12345", login, ""+orderEntry5.get("xid"), "0", "0", "0.0", OMSTCConstants.Pincodes.PINCODE_560068,
                                                                       "responsive", "DEFAULT", "cod");
        
        orderEntry6 = bountyServiceHelper.getCreateOrderEntry(login, "123456", skuId1, "ADD","", "200", "50", "");
        orderResponse6 = bountyServiceHelper.createOrder((CartEntry) orderEntry6.get("cart"), "12345", login, ""+orderEntry6.get("xid"), "0", "0", "0.0", OMSTCConstants.Pincodes.PINCODE_560068,
                                                                       "responsive", "DEFAULT", "cod");
        
        orderEntry7 = bountyServiceHelper.getCreateOrderEntry(login, "123456", skuId1, "ADD","", "200", "50", "");
        orderResponse7 = bountyServiceHelper.createOrder((CartEntry) orderEntry7.get("cart"), "12345", login, ""+orderEntry7.get("xid"), "0", "0", "0.0", OMSTCConstants.Pincodes.PINCODE_560068,
                                                                       "responsive", "DEFAULT", "cod");
        
        orderEntry8 = bountyServiceHelper.getCreateOrderEntry(login, "123456", skuId1, "ADD","", "200", "50", "");
        orderResponse8 = bountyServiceHelper.createOrder((CartEntry) orderEntry8.get("cart"), "12345", login, ""+orderEntry8.get("xid"), "0", "0", "0.0", OMSTCConstants.Pincodes.PINCODE_560068,
                                                                       "responsive", "DEFAULT", "cod");
                
        Object[] arr1 = {orderResponse1, login, orderEntry1,"DEFAULT", "FAILURE", true,39};
        Object[] arr2 = {orderResponse2, login, orderEntry2,"DEFAULT", "FAILURE", true,6};
        Object[] arr3 = {orderResponse3, login,	orderEntry3,"DEFAULT", "FAILURE", true,7};
        Object[] arr4 = {orderResponse4, login, orderEntry4,"DEFAULT", "FAILURE", true,9};
        Object[] arr5 = {orderResponse5, login, orderEntry5,"DEFAULT", "FAILURE", true,24};
        Object[] arr6 = {orderResponse6, login, orderEntry6,"DEFAULT", "FAILURE", true,25};
        Object[] arr7 = {orderResponse7, login, orderEntry7,"DEFAULT", "FAILURE", true,26};
        Object[] arr8 = {orderResponse8, login, orderEntry8,"DEFAULT", "FAILURE", true,28};

        Object[][] dataSet = new Object[][] { arr1,arr2,arr3,arr4,arr5,arr6,arr7,arr8 };
        return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 8, 8);
    }
	
	@DataProvider
    public static Object[][] checkIsCancellableOrderInQStatusDP(ITestContext testContext) throws IOException, JAXBException {

        String skuId1[] = {OMSTCConstants.OtherSkus.skuId_3832+":1"};
        atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3832+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":1000:0:"+vectorSellerID+":1"},supplyTypeOnHand);
		imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3832+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":1000:0:"+vectorSellerID},supplyTypeOnHand);
		
        orderEntry1 = bountyServiceHelper.getCreateOrderEntry(login, "123456", skuId1, "ADD","", "200", "50", "");
        orderResponse1 = bountyServiceHelper.createOrder((CartEntry) orderEntry1.get("cart"), "12345", login, ""+orderEntry1.get("xid"), "0", "0", "0.0", OMSTCConstants.Pincodes.PINCODE_560068,
                                                                       "responsive", "DEFAULT", "cod");
        
        Object[] arr1 = {orderResponse1, login,orderEntry1,"DEFAULT", "FAILURE", false};

        Object[][] dataSet = new Object[][] { arr1};
        return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 6, 6);
    }
	
	@DataProvider
    public static Object[][] checkIsCancellableOrderInDL_C_L_RTOStatusDP(ITestContext testContext) throws Exception {

        String skuId[] = {OMSTCConstants.OtherSkus.skuId_3832+":1"};
        atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3832+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":1000:0:"+vectorSellerID+":1"},supplyTypeOnHand);
		imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3832+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":1000:0:"+vectorSellerID},supplyTypeOnHand);
		
        orderID1 = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false, "", false);
        
        orderID2 = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false, "", false);
        
        orderID3 = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false, "", false);
        
        orderID4 = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false, "", false);
         
        Object[] arr1 = {orderID1, login, "DL"};
        Object[] arr2 = {orderID2, login, "C"};
        Object[] arr3 = {orderID3, login, "L"};
        Object[] arr4 = {orderID4, login, "RTO"};

        Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4};
        return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 4, 4);
    }
	
	@DataProvider
    public static Object[][] checkIsCancellableOrderInWPStatusWithOnholdReason6_7_9_24_25_26_28_39DP(ITestContext testContext) throws Exception {

        String skuId[] = {OMSTCConstants.OtherSkus.skuId_3832+":1"};
        atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3832+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":1000:0:"+vectorSellerID+":1"},supplyTypeOnHand);
		imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3832+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":1000:0:"+vectorSellerID},supplyTypeOnHand);
		
        orderID1 = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false, "", false);
        
        orderID2 = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false, "", false);
        
        orderID3 = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false, "", false);
        
        orderID4 = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false, "", false);
        
        orderID5 = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false, "", false);
       
        orderID6 = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false, "", false);
        
        orderID7 = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false, "", false);
       
        orderID8 = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false, "", false);
        
        Object[] arr1 = {orderID1, login,6};
        Object[] arr2 = {orderID2, login,7};
        Object[] arr3 = {orderID3, login,9};
        Object[] arr4 = {orderID4, login,24};
        Object[] arr5 = {orderID5, login,25};
        Object[] arr6 = {orderID6, login,26};
        Object[] arr7 = {orderID7, login,28};
        Object[] arr8 = {orderID8, login,39};
        

        Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4,arr5,arr6,arr7,arr8};
        return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 8, 8);
    }
	
	@DataProvider
    public static Object[][] checkIsCancellableOrderInRFRStatusWithOnholdReason6_7_9_24_25_26_28_39DP(ITestContext testContext) throws Exception {

        String skuId[] = {OMSTCConstants.OtherSkus.skuId_3832+":1"};
        atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3832+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":1000:0:"+vectorSellerID+":1"},supplyTypeOnHand);
		imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3832+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":1000:0:"+vectorSellerID},supplyTypeOnHand);
		
        orderID1 = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false, "", false);
        
        orderID2 = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false, "", false);
        
        orderID3 = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false, "", false);
        
        orderID4 = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false, "", false);
        
        orderID5 = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false, "", false);
        
        orderID6 = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false, "", false);
        
        orderID7 = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false, "", false);
        
        orderID8 = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false, "", false);
        
        Object[] arr1 = {orderID1, login,6};
        Object[] arr2 = {orderID2, login,7};
        Object[] arr3 = {orderID3, login,9};
        Object[] arr4 = {orderID4, login,24};
        Object[] arr5 = {orderID5, login,25};
        Object[] arr6 = {orderID6, login,26};
        Object[] arr7 = {orderID7, login,28};
        Object[] arr8 = {orderID8, login,39};
        

        Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4,arr5,arr6,arr7,arr8};
        return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 8, 8);
    }

	@DataProvider
    public static Object[][] checkIsCancellableOrderInPKStatusDP(ITestContext testContext) throws Exception {
        
        String [] lmsShipmentStatus = {"INSCANNED","ADDED_TO_MB","FAILED_DELIVERY","CANCELLED_IN_HUB","SHIPPED"};
        String [] lmsStatus = {"IS","IS","FD","CANCELLED_IN_HUB","SH"};
        
       // orderReleaseID1 = getOrderReleaseFromLmsInParticularStatus(lmsStatus[0],"36,28");
        orderReleaseID2 = createOrderInOMS(OMSTCConstants.WareHouseIds.warehouseId36_BW);
        orderReleaseID3 = createOrderInOMS(OMSTCConstants.WareHouseIds.warehouseId36_BW);
        orderReleaseID4 = createOrderInOMS(OMSTCConstants.WareHouseIds.warehouseId36_BW);
        orderReleaseID5 = createOrderInOMS(OMSTCConstants.WareHouseIds.warehouseId36_BW);
        orderReleaseID6 = createOrderInOMS(OMSTCConstants.WareHouseIds.warehouseId36_BW);

       // Object[] arr1 = {orderReleaseID1,lmsStatus[0],"success"};
        Object[] arr1 = {orderReleaseID2,lmsStatus[0],lmsShipmentStatus[0],"success"};
        Object[] arr2 = {orderReleaseID3,lmsStatus[1],lmsShipmentStatus[1],"error"};
        Object[] arr3 = {orderReleaseID4,lmsStatus[2],lmsShipmentStatus[2],"error"};
        Object[] arr4 = {orderReleaseID5,lmsStatus[3],lmsShipmentStatus[3],"success"};
        Object[] arr5 = {orderReleaseID6,lmsStatus[4],lmsShipmentStatus[4],"error"};

        Object[][] dataSet = new Object[][] {arr1,arr2,arr3,arr4,arr5};

        return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 5);
    }
	
	@DataProvider
    public static Object[][] checkIsCancellableOrderInPKStatusForWarehouseNotAllowingDP(ITestContext testContext) throws Exception {
        
        String [] lmsShipmentStatus = {"PACKED","INSCANNED","ADDED_TO_MB","SHIPPED"};
        String [] lmsStatus = {"PK","IS","IS","SH"};
        
        orderReleaseID1 = createOrderInOMS(OMSTCConstants.WareHouseIds.warehouseId19_BG);
        orderReleaseID2 = createOrderInOMS(OMSTCConstants.WareHouseIds.warehouseId19_BG);
        orderReleaseID3 = createOrderInOMS(OMSTCConstants.WareHouseIds.warehouseId19_BG);
        orderReleaseID4 = createOrderInOMS(OMSTCConstants.WareHouseIds.warehouseId19_BG);
        
        
        Object[] arr1 = {orderReleaseID1,lmsStatus[0],lmsShipmentStatus[0],"error"};
        Object[] arr2 = {orderReleaseID2,lmsStatus[1],lmsShipmentStatus[1],"error"};
        Object[] arr3 = {orderReleaseID3,lmsStatus[2],lmsShipmentStatus[2],"error"};
        Object[] arr4 = {orderReleaseID4,lmsStatus[3],lmsShipmentStatus[3],"error"};

        Object[][] dataSet = new Object[][] { arr1,arr2,arr3,arr4};

        return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 6, 6);
    }

	
	
	public static Long getOrderReleaseFromLmsInParticularStatus(String shipment_status, String warehouseId ){
		HashMap<String, Object> resultSet = null;
		String query = "select * from order_to_ship where warehouse_id in ('"+warehouseId+"') "
				+ "and shipment_status = '"+shipment_status+"' and email = '"+login+"' order by last_modified_on DESC limit 1;";
		
		try {
			resultSet = (HashMap<String, Object>) DBUtilities.exSelectQueryForSingleRecord( query, "lms");
		} catch (Exception e) {
			e.printStackTrace();
			log.info("Order Entry in LMS : "+ e.getLocalizedMessage());
		}
		
		return (Long) resultSet.get("order_id");
	}
	
	public static String createOrderInOMS(int warehouseId) throws Exception{
        String skuId[] = { OMSTCConstants.CancellationTillHubTest.cancelMultipleReleasesInPKStatus_ITEM1+":1"};
        
        atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.CancellationTillHubTest.cancelMultipleReleasesInPKStatus_ITEM1+":"+warehouseId+":10000:0:"+vectorSellerID+":1"},supplyTypeOnHand);
		imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.CancellationTillHubTest.cancelMultipleReleasesInPKStatus_ITEM1+":"+warehouseId+":10000:0:"+vectorSellerID},supplyTypeOnHand);
		
        orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false, "", false);
        log.info("OrderID: "+orderID);
        orderEntry = omsServiceHelper.getOrderEntry(orderID);
        orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();
        //omsServiceHelper.updateReleaseStatusDB(orderReleaseId, "WP");
        omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");


		return orderReleaseId;
		
	}
	
	
}
*/