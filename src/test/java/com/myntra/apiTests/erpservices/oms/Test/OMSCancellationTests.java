package com.myntra.apiTests.erpservices.oms.Test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.JAXBException;

import org.apache.log4j.Logger;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.myntra.apiTests.common.Constants.EnumSCM;
import com.myntra.apiTests.common.Constants.ReleaseStatus;
import com.myntra.apiTests.common.ProcessOrder.Service.ProcessRelease;
import com.myntra.apiTests.common.entries.ReleaseEntry;
import com.myntra.apiTests.common.entries.ReleaseEntryList;
import com.myntra.apiTests.end2end.End2EndHelper;
import com.myntra.apiTests.erpservices.atp.ATPServiceHelper;
import com.myntra.apiTests.erpservices.bounty.BountyServiceHelper;
import com.myntra.apiTests.erpservices.ims.IMSServiceHelper;
import com.myntra.apiTests.erpservices.oms.OMSServiceHelper;
import com.myntra.apiTests.erpservices.oms.OMSHelpersEnums.ReadyToDispatchType;
import com.myntra.apiTests.erpservices.oms.dp.CancellationDP;
import com.myntra.apiTests.erpservices.sellerapis.SellerConfig;
import com.myntra.apiTests.erpservices.wms.WMSServiceHelper;
import com.myntra.apiTests.portalservices.ideaapi.IdeaApiHelper;
import com.myntra.apiTests.portalservices.pps.PPSServiceHelper;
import com.myntra.client.wms.codes.utils.ItemStatus;
import com.myntra.client.wms.codes.utils.OrderReleaseStatus;
import com.myntra.client.wms.response.ItemEntry;
import com.myntra.client.wms.response.ItemResponse;
import com.myntra.commons.codes.StatusResponse.Type;
import com.myntra.commons.exception.ManagerException;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.boromir.DBUtilities;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.oms.client.entry.OrderEntry;
import com.myntra.oms.client.entry.OrderLineEntry;
import com.myntra.oms.client.entry.OrderReleaseEntry;
import com.myntra.oms.client.response.OrderReleaseResponse;
import com.myntra.oms.client.response.OrderResponse;
import com.myntra.paymentplan.exception.ErrorResponse;
import com.myntra.test.commons.testbase.BaseTest;
import com.myntra.worms.client.entry.OrderCaptureReleaseEntry;

public class OMSCancellationTests extends BaseTest{
	
	static Initialize init = new Initialize("/Data/configuration");
    private Logger log = Logger.getLogger(OMSServiceHelper.class);
    private String login = OMSTCConstants.OMSLineCancellationTest.LOGIN_URL;
    private String uidx;
    private String password = OMSTCConstants.OMSLineCancellationTest.PASSWORD;
    private static Long vectorSellerID;
    
    End2EndHelper end2EndHelper = new End2EndHelper();
    OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
    ATPServiceHelper atpServiceHelper = new ATPServiceHelper();
    IMSServiceHelper imsServiceHelper = new IMSServiceHelper();
    WMSServiceHelper wmsServiceHelper = new WMSServiceHelper();
    PPSServiceHelper ppsServiceHelper = new PPSServiceHelper();
    IdeaApiHelper ideaApiHelper = new IdeaApiHelper();
    BountyServiceHelper bountyServiceHelper = new BountyServiceHelper();
	private OrderEntry orderEntry;
	private List<OrderReleaseEntry> orderReleaseEntries;
	private String orderReleaseId;
	private String orderID;
	private String supplyTypeOnHand = "ON_HAND";
	SoftAssert sft;
	private String storeId = "1";
	private OrderReleaseResponse orderReleaseResponse;
	Long delayedTime = 5000L;
	private OrderLineEntry orderLineEntry;

    @BeforeSuite(alwaysRun = true)
    public void testBeforeSuite() throws JAXBException, IOException {
		end2EndHelper.updateToolsApplicationProperties("cod.limit.range", "1-200000", "oms");
		end2EndHelper.updateToolsApplicationProperties("bounty.get_default_address", "true", "oms");
		bountyServiceHelper.refreshBountyApplicationPropertyCache();
    }

    
    @BeforeClass(alwaysRun = true)
    public void testBeforeClass() throws SQLException, IOException, JAXBException {
    	vectorSellerID = SellerConfig.getSellerID(SellerConfig.SellerName.HANDH);
        DBUtilities.exUpdateQuery("UPDATE on_hold_reasons_master SET is_enabled=0 where id in (35, 23,37);", "myntra_oms");
        omsServiceHelper.refreshOMSApplicationPropertyCache();
        omsServiceHelper.refreshOMSJVMCache();
        uidx = ideaApiHelper.getUIDXForLoginViaEmail("myntra", login);
    }
    
    public Boolean waitForOrderLevelOnHoldToResolve(String orderID) throws Exception{
    	Boolean isOnhold = omsServiceHelper.getOrderEntry(orderID).getOnHold();
    	for(int i=0;i<5;i++){
    		if(isOnhold==false){
    			return isOnhold;
    		}
    		End2EndHelper.sleep(delayedTime);
    		isOnhold = omsServiceHelper.getOrderEntry(orderID).getOnHold();
    	}
    	
    	return isOnhold;
    }
    
    //Passed
	@Test(enabled=true,groups={"regression","sanity","cancellation"}, description="Cancel Order Release which do not have warehouse assigned")
    public void cancelQuantityForWhichWHIsNotAssigned() throws Exception {
    	sft = new SoftAssert();
    	imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OMSLineCancellationTest.cancelQuantityForWhichWHIsNotAssigned_ITEM1+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":0:0:"+vectorSellerID},supplyTypeOnHand );

    	String orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, new String[] {OMSTCConstants.OMSLineCancellationTest.cancelQuantityForWhichWHIsNotAssigned_ITEM1+":1"}, "", false, false, false, "", false);

        omsServiceHelper.checkReleaseStatusForOrder(orderID,"Q");
        sft.assertEquals(waitForOrderLevelOnHoldToResolve(orderID), Boolean.FALSE,"Order LevelOnhold is not resolved");
        int iocbocbefore = atpServiceHelper.getAtpInvAndBlockOrderCount(new String[] { OMSTCConstants.OMSLineCancellationTest.cancelQuantityForWhichWHIsNotAssigned_ITEM1 }).get(OMSTCConstants.OMSLineCancellationTest.cancelQuantityForWhichWHIsNotAssigned_ITEM1)[1];
        orderEntry = omsServiceHelper.getOrderEntry(orderID);
        orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();
        
        List<OrderLineEntry> orderLineEntries = omsServiceHelper.getOrderLineEntries(orderReleaseId);
        String lineID = ""+orderLineEntries.get(0).getId();
        
        cancelLineSuccess(orderReleaseId, login, lineID, 1, 41);
        
        end2EndHelper.sleep(3*delayedTime);
        int iocbocafter = atpServiceHelper.getAtpInvAndBlockOrderCount(new String[]{OMSTCConstants.OMSLineCancellationTest.cancelQuantityForWhichWHIsNotAssigned_ITEM1}).get(OMSTCConstants.OMSLineCancellationTest.cancelQuantityForWhichWHIsNotAssigned_ITEM1)[1];
        sft.assertEquals(iocbocafter, iocbocbefore-1, "Inventory Count Should be Reduced after Order Cancellation ");
        sft.assertAll();
    }
	
	//Passed
	@Test(enabled = true, dataProvider="checkSingleItemLineCancellationTriggerReleaseCancellationDP", dataProviderClass=CancellationDP.class, groups={"regression","sanity","cancellation"}, description="Check cancellation when quantity to cancel is greater than the available qty.")
    public void checkLineCancellationWhenQuantityIsGreaterThanTheAvailableQuantity(String orderID, String login) throws UnsupportedEncodingException, JAXBException, ManagerException{
        sft = new SoftAssert();
        omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
        orderEntry = omsServiceHelper.getOrderEntry(orderID);
        orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();
        List<OrderLineEntry> orderLineEntries = omsServiceHelper.getOrderLineEntries(orderReleaseId);
        String lineID = ""+orderLineEntries.get(0).getId();
        
        cancelLineFailure(orderReleaseId, login, lineID, 6, 41,8065);
    	sft.assertAll();
    }
	
	//Passed
	@Test(enabled = true, dataProvider="checkSingleItemLineCancellationTriggerReleaseCancellationDP", dataProviderClass=CancellationDP.class, groups={"regression","sanity","cancellation"}, description="Check cancellation when quantity to cancel is Less than the available qty.")
    public void checkLineCancellationWhenQuantityIsLessThanTheAvailableQuantity(String orderID, String login) throws UnsupportedEncodingException, JAXBException, ManagerException{
    	sft = new SoftAssert();
    	omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
        orderEntry = omsServiceHelper.getOrderEntry(orderID);
        orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();
        List<OrderLineEntry> orderLineEntries = omsServiceHelper.getOrderLineEntries(orderReleaseId);
        String lineID = ""+orderLineEntries.get(0).getId();
        
        cancelLineSuccess(orderReleaseId, login, lineID, 1, 41);
        sft.assertAll();
    }
	
	//Passed
	@Test(enabled = true, dataProvider="checkSingleItemLineCancellationTriggerReleaseCancellationDP", dataProviderClass=CancellationDP.class, groups={"regression","sanity","cancellation"}, description="Check cancellation when quantity to cancel is negitive value than the available qty.")
    public void checkLineCancellationWhenQuantityIsNegitiveValueThanTheAvailableQuantity(String orderID, String login) throws UnsupportedEncodingException, JAXBException, ManagerException{
    	sft = new SoftAssert();
    	omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
        orderEntry = omsServiceHelper.getOrderEntry(orderID);
        orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();
        List<OrderLineEntry> orderLineEntries = omsServiceHelper.getOrderLineEntries(orderReleaseId);
        String lineID = ""+orderLineEntries.get(0).getId();
        
        cancelLineFailure(orderReleaseId, login, lineID, -1, 41,8065);
        sft.assertAll();
    }
	
	//Passed
	@Test(enabled = true, dataProvider="checkSingleItemLineCancellationTriggerReleaseCancellationDP", dataProviderClass=CancellationDP.class, groups={"regression","sanity","cancellation"}, description="Check cancellation when quantity to cancel is zero than the available qty.")
    public void checkLineCancellationWhenQuantityZeroThanTheAvailableQuantity(String orderID, String login) throws UnsupportedEncodingException, JAXBException, ManagerException{
    	sft = new SoftAssert();
    	omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
        orderEntry = omsServiceHelper.getOrderEntry(orderID);
        orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();
        List<OrderLineEntry> orderLineEntries = omsServiceHelper.getOrderLineEntries(orderReleaseId);
        String lineID = ""+orderLineEntries.get(0).getId();
        
        cancelLineFailure(orderReleaseId, login, lineID, 0, 41,8065);
        sft.assertAll();
    }
	
	//Passed
	@Test(enabled = true, dataProvider="checkSingleItemLineCancellationTriggerReleaseCancellationDP", dataProviderClass=CancellationDP.class, groups={"regression","sanity","cancellation"}, description="Check cancellation when item from a single item order is cancelled")
    public void checkSingleItemLineCancellationTriggerReleaseCancellation(String orderID, String login) throws UnsupportedEncodingException, JAXBException, ManagerException{
        sft = new SoftAssert();
        omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
        orderEntry = omsServiceHelper.getOrderEntry(orderID);
        orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();
        List<OrderLineEntry> orderLineEntries = omsServiceHelper.getOrderLineEntries(orderReleaseId);
        String lineID = ""+orderLineEntries.get(0).getId();
        
        cancelLineSuccess(orderReleaseId, login, lineID, 1, 41);
        verifyCancelDataAfterCancel(orderReleaseId,lineID,"IC",1,"F","Customer Request-Incorrect size ordered","41");
        sft.assertAll();
    }
	
	//Passed
	@Test(enabled = true, dataProvider="checkSingleItemLineCancellationTriggerReleaseCancellationDP", dataProviderClass=CancellationDP.class, groups={"regression","sanity","cancellation"}, description="Check Single Item Line Cancellation triggers Release and Order Cancellation")
    public void checkOOSLineCancellation(String orderID, String login) throws UnsupportedEncodingException, JAXBException, ManagerException{
        sft = new SoftAssert();
        omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
        orderEntry = omsServiceHelper.getOrderEntry(orderID);
        orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();
        List<OrderLineEntry> orderLineEntries = omsServiceHelper.getOrderLineEntries(orderReleaseId);
        String lineID = ""+orderLineEntries.get(0).getId();
        
        cancelLineSuccess(orderReleaseId, login, lineID, 1, 39);
        verifyCancelDataAfterCancel(orderReleaseId,lineID,"IC",1,"F","CC Cancellation-OOS Cancellation","39");
        sft.assertAll();
    }
	
	//passed
    @Test(enabled = true, dataProvider="cancelReleaseOfAPackOrderDP", dataProviderClass=CancellationDP.class, groups={"regression","sanity","cancellation"}, description="Check Single Item Line Cancellation triggers Release and Order Cancellation")
    public void cancelLineOfAPackOrder(String releaseID, String login) throws UnsupportedEncodingException, JAXBException{
        sft = new SoftAssert();
    	orderEntry = omsServiceHelper.getOrderEntry(releaseID);
        orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();
        String lineID = ""+orderEntry.getOrderReleases().get(0).getOrderLines().get(0).getId();
        
        cancelLineFailure(orderReleaseId, login, lineID, 1, 41,8076);
        sft.assertAll();
    }
    
    //passed
    @Test(enabled = true, dataProvider="cancelReleaseOfASHippedOrderDP", dataProviderClass=CancellationDP.class, groups={"regression","sanity","cancellation"}, description="Check Single Item Line Cancellation triggers Release and Order Cancellation")
    public void checkLineCancellationOfAShippedOrder(String releaseID, String login) throws UnsupportedEncodingException, JAXBException{
        sft = new SoftAssert();
    	orderEntry = omsServiceHelper.getOrderEntry(releaseID);
        orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();
        String lineID = ""+orderEntry.getOrderReleases().get(0).getOrderLines().get(0).getId();
        cancelLineFailure(orderReleaseId, login, lineID, 1, 41,8076);
        sft.assertAll();
    }

    //passed
    @Test(enabled = true, dataProvider="cancelReleaseOfADeliveredOrderDP", dataProviderClass=CancellationDP.class, groups={"regression","sanity","cancellation"}, description="Check Single Item Line Cancellation triggers Release and Order Cancellation")
    public void checkLineCancellationOfADeliveredOrder(String releaseID, String login) throws UnsupportedEncodingException, JAXBException{
    	sft = new SoftAssert();
    	orderEntry = omsServiceHelper.getOrderEntry(releaseID);
        orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();
        
        String lineID = ""+orderEntry.getOrderReleases().get(0).getOrderLines().get(0).getId();
        cancelLineFailure(orderReleaseId, login, lineID, 1, 41,8076);
        sft.assertAll();
    }

	
	//Passed
	@Test(enabled = true, dataProvider="checkSingleItemLineCancellationTriggerReleaseCancellationDP", dataProviderClass=CancellationDP.class, groups={"regression","sanity","cancellation"}, description="Check Single Item Line Cancellation triggers Release and Order Cancellation")
    public void checkLineCancellationForEachQuantity(String orderID, String login) throws UnsupportedEncodingException, JAXBException, ManagerException{
        sft = new SoftAssert();
        omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
        orderEntry = omsServiceHelper.getOrderEntry(orderID);
        
        for(OrderReleaseEntry orderReleaseEntry:orderEntry.getOrderReleases()){
        	String orderReleaseID = orderReleaseEntry.getId().toString();
        	List<OrderLineEntry> orderLineEntries = omsServiceHelper.getOrderLineEntries(orderReleaseID);
        	String lineID = ""+orderLineEntries.get(0).getId();
        	
            cancelLineSuccess(orderReleaseID, login, lineID, 1, 39);
        }
        
        End2EndHelper.sleep(3*delayedTime);
        verifyPPSIdsForCancelledLinesInTheOrder(orderID);
        sft.assertAll();
    }
	
	//Passed
	@Test(enabled = true, dataProvider="checkMultipleReleaseMultipleItemCancellationDP", dataProviderClass=CancellationDP.class, groups={"regression","sanity","cancellation"}, description="Check Single Item Line Cancellation triggers Release and Order Cancellation")
    public void checkMultipleReleaseMultipleItemCancellation(String orderID, String login) throws UnsupportedEncodingException, JAXBException, ManagerException{
    	sft = new SoftAssert();
    	omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
    	End2EndHelper.sleep(delayedTime);
    	
        orderEntry = omsServiceHelper.getOrderEntry(orderID);
        
        orderReleaseEntries = orderEntry.getOrderReleases();
        for(OrderReleaseEntry orderReleaseEntry: orderReleaseEntries){
        	
        	orderLineEntry = orderReleaseEntry.getOrderLines().get(0);
        	cancelLineSuccess(orderReleaseEntry.getId().toString(), login, ""+orderLineEntry.getId(), 1, 41);
        	End2EndHelper.sleep(delayedTime);
        	verifyPPSIdsForCancelledLinesInTheOrder(orderID);
        }

        verifyPPSIdsForCancelledLinesInTheOrder(orderID);
        sft.assertAll();
    }
	
	@Test(enabled = true, groups={"regression","sanity","cancellation"})
    public void cancelLineWithWMSAssociation() throws Exception {
        sft = new SoftAssert();
    	String skuId[] = { OMSTCConstants.OMSLineCancellationTest.cancelLineWithWMSAssociation_ITEM1+":1"};
        atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OMSLineCancellationTest.cancelLineWithWMSAssociation_ITEM1+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":10000:0:"+vectorSellerID+":1"}, "ON_HAND");
		imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OMSLineCancellationTest.cancelLineWithWMSAssociation_ITEM1+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":10000:0:"+vectorSellerID },"ON_HAND");

        String orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false,"", false);
        omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
        orderEntry = omsServiceHelper.getOrderEntry(orderID);
        orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();
        
        OrderReleaseEntry orderReleaseEntry = omsServiceHelper.getOrderReleaseEntry(orderReleaseId);
        long lineID = orderReleaseEntry.getOrderLines().get(0).getId();
        int warehouseID = orderReleaseEntry.getWarehouseId();
        Map hm = new HashMap();
        hm= DBUtilities.exSelectQueryForSingleRecord("select * from capture_order_release where portal_order_release_id= "+orderReleaseId, "wms");
        String pick_type=hm.get("pick_type").toString();
        // Push Order to Order wave for picking
        omsServiceHelper.pushOrderToWave(orderReleaseId,warehouseID);
        End2EndHelper.sleep(2*delayedTime);

        HashMap<String, Object> atpInventoryDetailsBeforeCancellingOrder = atpServiceHelper.getAtpInvDetails(new String[] { OMSTCConstants.OMSLineCancellationTest.cancelLineWithWMSAssociation_ITEM1 }).get(OMSTCConstants.OMSLineCancellationTest.cancelLineWithWMSAssociation_ITEM1);
        HashMap<String, Object> imsInventoryDetailsBeforeCancellingOrder = imsServiceHelper.getIMSInventoryDetails(new String[] { OMSTCConstants.OMSLineCancellationTest.cancelLineWithWMSAssociation_ITEM1+":"+warehouseID+":"+OMSTCConstants.WareHouseIds.warehouseId1_BA+":"+vectorSellerID }).get(OMSTCConstants.OMSLineCancellationTest.cancelLineWithWMSAssociation_ITEM1);
        //Check Item Flow to Core Order Release
        String[] checkoutItems = wmsServiceHelper.orderItemsCheckout(orderReleaseId, ""+orderReleaseEntry.getWarehouseId());
        omsServiceHelper.cancelLine(orderReleaseId,login, new String[]{""+lineID+":1"}, 41);
        End2EndHelper.sleep(25000);

        HashMap<String, Object> atpInventoryDetailsAfterCancellingOrder = atpServiceHelper.getAtpInvDetails(new String[] { OMSTCConstants.OMSLineCancellationTest.cancelLineWithWMSAssociation_ITEM1 }).get(OMSTCConstants.OMSLineCancellationTest.cancelLineWithWMSAssociation_ITEM1);
        HashMap<String, Object> imsInventoryDetailsAfterCancellingOrder = imsServiceHelper.getIMSInventoryDetails(new String[] { OMSTCConstants.OMSLineCancellationTest.cancelLineWithWMSAssociation_ITEM1+":"+warehouseID+":"+OMSTCConstants.WareHouseIds.warehouseId1_BA+":"+vectorSellerID }).get(OMSTCConstants.OMSLineCancellationTest.cancelLineWithWMSAssociation_ITEM1);

        sft.assertEquals(atpInventoryDetailsAfterCancellingOrder.get("blocked_order_count"), Integer.parseInt(""+atpInventoryDetailsBeforeCancellingOrder.get("blocked_order_count"))-1, "ATP Block Order Count After cancellation");
        sft.assertEquals(imsInventoryDetailsAfterCancellingOrder.get("blocked_order_count"), Integer.parseInt(""+imsInventoryDetailsBeforeCancellingOrder.get("blocked_order_count"))-1, "IMS Block Order Count After cancellation");

        sft.assertEquals( atpInventoryDetailsAfterCancellingOrder.get("inventory_count"), Integer.parseInt(""+atpInventoryDetailsBeforeCancellingOrder.get("inventory_count"))-1, "ATP Inventory Count After cancellation");
        sft.assertEquals(imsInventoryDetailsAfterCancellingOrder.get("inventory_count"), Integer.parseInt(""+imsInventoryDetailsBeforeCancellingOrder.get("inventory_count"))-1, "IMS Inventory Count After cancellation");

        List<HashMap<String, Object>> itemStatus = DBUtilities.exSelectQuery("select * from item where id in ( "+ Arrays.toString(checkoutItems).replace("[","").replace("]","") +" )", "wms");
        HashMap itemStatus1 = itemStatus.get(0);
        //HashMap itemStatus2 = itemStatus.get(1);
        sft.assertEquals(itemStatus1.get("item_status"), "RETURN_FROM_OPS","Verify item status in WMS item");
       // sft.assertEquals(itemStatus2.get("item_status"), "RETURN_FROM_OPS","Verify item status in WMS item");
        sft.assertAll();
    }
	
	@Test(enabled = true, dataProviderClass=CancellationDP.class, groups={"regression","sanity","cancellation"}, description="Replacement and GoodWill coupon should be generated for Line Cancellation")
    public void checkReplacementCouponGenerationOnLineCancellation() throws Exception {
    	sft = new SoftAssert();
        String skuId[] = { OMSTCConstants.OtherSkus.skuId_3832+":2", OMSTCConstants.OtherSkus.skuId_3837+":2" };
        atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3832+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":10000:0:"+vectorSellerID+":1",OMSTCConstants.OtherSkus.skuId_3837+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":10000:0:"+vectorSellerID+":1"},"ON_HAND");
        imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3832+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":10000:0:"+vectorSellerID,
        		OMSTCConstants.OtherSkus.skuId_3837+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":10000:0:"+vectorSellerID},"ON_HAND");
        uidx = ideaApiHelper.getUIDXForLoginViaEmail("myntra", login);
        DBUtilities.exUpdateQuery("Delete from xcart_discount_coupons where coupon not in ('pati1','pati2','pati3') and users='"+ uidx +"';", "myntra");
        String orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "MANISHPLATFORM444", false, false, false,"", false);
        omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
        orderEntry = omsServiceHelper.getOrderEntry(orderID);
        orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();
        Long lineID = null;
        
        List<OrderLineEntry> orderLineEntries = omsServiceHelper.getOrderReleaseEntry(orderReleaseId).getOrderLines();
        for(OrderLineEntry orderLineEntry: orderLineEntries){
        	if(orderLineEntry.getCouponDiscount()>0.0){
        		 lineID = orderLineEntry.getId();
        	}
        }
        OrderReleaseResponse cancellationRes = omsServiceHelper.cancelLine(orderReleaseId, "erpadmin", new String[] {lineID +":2"}, 39);
        sft.assertEquals(cancellationRes.getStatus().getStatusCode(), 1034,"Status code should be same");
        sft.assertEquals(cancellationRes.getStatus().getStatusType(), Type.SUCCESS,"Verify success response");
        end2EndHelper.sleep(20000);
        sft.assertEquals(cancellationRes.getStatus().getStatusMessage(), "Lines are canceled from the release successfully.");
        List coupon = DBUtilities.exSelectQuery("Select * from xcart_discount_coupons where coupon like 'CAN%' and MRPpercentage=30 and users='"+ uidx +"';", "myntra");
        List coupon1 = DBUtilities.exSelectQuery("Select * from xcart_discount_coupons where coupon like 'GW%' and MRPpercentage=40.00 and users='"+ uidx +"';", "myntra");
        sft.assertEquals(coupon.size(), 1, "Cancellation Replacement coupon is not generated");
        sft.assertEquals(coupon1.size(), 1, "Cancellation GoodWill coupon is not generated");
        sft.assertAll();
    }
	
	//Passed
    @Test(enabled=true,groups={"regression","sanity","cancellation"})
    public void cancelReleasesFollowedByLineCancellation_SingleQuantity() throws Exception {
    	sft = new SoftAssert();
        String skuId[] = { OMSTCConstants.OMSLineCancellationTest.cancelReleasesFollowedByLineCancellation_SingleQuantity_ITEM1+":2"};
        atpServiceHelper.updateInventoryDetailsForSeller(new String[] {OMSTCConstants.OMSLineCancellationTest.cancelReleasesFollowedByLineCancellation_SingleQuantity_ITEM1+":36:1000:0:"+vectorSellerID+":1"},supplyTypeOnHand);
        imsServiceHelper.updateInventoryForSeller(new String[] {OMSTCConstants.OMSLineCancellationTest.cancelReleasesFollowedByLineCancellation_SingleQuantity_ITEM1+":36:1000:0:"+vectorSellerID},supplyTypeOnHand);
		
        HashMap<String, int[]> inventoryCountInATPBeforePlacingOrder = atpServiceHelper
                .getAtpInvAndBlockOrderCount(new String[] { OMSTCConstants.OMSLineCancellationTest.cancelReleasesFollowedByLineCancellation_SingleQuantity_ITEM1 });

        HashMap<String, int[]> inventoryCountInIMSBeforePlacingOrder = imsServiceHelper
                .getIMSInvAndBlockOrderCount(new String[] { OMSTCConstants.OMSLineCancellationTest.cancelReleasesFollowedByLineCancellation_SingleQuantity_ITEM1 }, "36","1",""+vectorSellerID);

        int[] blockCountATP = inventoryCountInATPBeforePlacingOrder.get(OMSTCConstants.OMSLineCancellationTest.cancelReleasesFollowedByLineCancellation_SingleQuantity_ITEM1);
        int blockCountATPBefore = blockCountATP[1];

        int[] blockCountIMS = inventoryCountInIMSBeforePlacingOrder.get(OMSTCConstants.OMSLineCancellationTest.cancelReleasesFollowedByLineCancellation_SingleQuantity_ITEM1);
        int blockCountIMSBefore = blockCountIMS[1];


        orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false,"", false);
        omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
        orderEntry = omsServiceHelper.getOrderEntry(orderID);
        orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();
        
        end2EndHelper.sleep(2*delayedTime);

        HashMap<String, int[]> inventoryCountInATPAfterPlacingOrder = atpServiceHelper
                .getAtpInvAndBlockOrderCount(new String[] { OMSTCConstants.OMSLineCancellationTest.cancelReleasesFollowedByLineCancellation_SingleQuantity_ITEM1 });

        HashMap<String, int[]> inventoryCountInIMSAfterPlacingOrder = imsServiceHelper
                .getIMSInvAndBlockOrderCount(new String[] { OMSTCConstants.OMSLineCancellationTest.cancelReleasesFollowedByLineCancellation_SingleQuantity_ITEM1 }, "36","1",""+vectorSellerID);

        blockCountATP = inventoryCountInATPAfterPlacingOrder.get(OMSTCConstants.OMSLineCancellationTest.cancelReleasesFollowedByLineCancellation_SingleQuantity_ITEM1);
        int blockCountATPAfter = blockCountATP[1]-2;

        blockCountIMS = inventoryCountInIMSAfterPlacingOrder.get(OMSTCConstants.OMSLineCancellationTest.cancelReleasesFollowedByLineCancellation_SingleQuantity_ITEM1);
        int blockCountIMSAfter = blockCountIMS[1]-2;

        log.info("ATP Block Inventory Status Before: "+ blockCountATPBefore + " After :"+ blockCountATPAfter);
        log.info("IMS Block Inventory Status Before: "+ blockCountIMSBefore + " After :"+ blockCountIMSAfter);

        //VerifyInventory in IMS and ATP After Order Placement
        sft.assertEquals(blockCountATPAfter, blockCountATPBefore, "ATP Block Order Count After Order Place");
        sft.assertEquals(blockCountIMSAfter, blockCountIMSBefore, "IMS Block Order Count After Order Place");

        OrderLineEntry orderLineEntry = omsServiceHelper.getOrderReleaseEntry(orderReleaseId).getOrderLines().get(0);
        cancelLineSuccess(orderReleaseId, login, ""+orderLineEntry.getId(), 1, 39);
        end2EndHelper.sleep(delayedTime);
        
        orderReleaseId = orderEntry.getOrderReleases().get(1).getId().toString();
        
        cancelReleaseSuccess(orderReleaseId, login, "39", "Order Release Cancellation");
        end2EndHelper.sleep(2*delayedTime);
        //Verify Inventory After Cancellation
        HashMap<String, int[]> inventoryCountInATPAfterCancellation = atpServiceHelper
                .getAtpInvAndBlockOrderCount(new String[] { OMSTCConstants.OMSLineCancellationTest.cancelReleasesFollowedByLineCancellation_SingleQuantity_ITEM1 });
        HashMap<String, int[]> inventoryCountInIMSAfterCancellation = imsServiceHelper
                .getIMSInvAndBlockOrderCount(new String[] { OMSTCConstants.OMSLineCancellationTest.cancelReleasesFollowedByLineCancellation_SingleQuantity_ITEM1 },"36","1",""+vectorSellerID);

        blockCountATP = inventoryCountInATPAfterCancellation.get(OMSTCConstants.OMSLineCancellationTest.cancelReleasesFollowedByLineCancellation_SingleQuantity_ITEM1);
        int blockCountATPAfterCancellation = blockCountATP[1];

        blockCountIMS = inventoryCountInIMSAfterCancellation.get(OMSTCConstants.OMSLineCancellationTest.cancelReleasesFollowedByLineCancellation_SingleQuantity_ITEM1);
        int blockCountIMSAfterCancellation = blockCountIMS[1];

        sft.assertEquals(blockCountATPBefore, blockCountATPAfterCancellation,"ATP Block Order Count After Order Cancellation");
        sft.assertEquals(blockCountIMSBefore, blockCountIMSAfterCancellation, "IMS Block Order Count After Order Cancellation");
        sft.assertAll();
    }
    
    //Passed
    @Test(enabled = true, dataProvider="creatingExchangeOrderAgainstCancelledLinesShouldNotAllowedDP", dataProviderClass=CancellationDP.class, groups={"regression","sanity","cancellation"}, description="Should not allow creating exchange order against cancelled lines.")
    public void  creatingExchangeOrderAgainstCancelledLinesShouldNotAllowed(String orderID, String login) throws Exception{
    	sft = new SoftAssert();
        omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
        orderEntry = omsServiceHelper.getOrderEntry(orderID);

		List<OrderReleaseEntry> orderReleaseEntries = orderEntry.getOrderReleases();
		
		for(OrderReleaseEntry orderReleaseEntry:orderReleaseEntries){
			orderReleaseId = orderReleaseEntry.getId().toString();
			String lineID = orderReleaseEntry.getOrderLines().get(0).getId().toString();
			cancelLineSuccess(orderReleaseId, login, lineID, 1, 39);
		}
		
		end2EndHelper.sleep(delayedTime);
		
		orderEntry = omsServiceHelper.getOrderEntry(orderID);
        OrderLineEntry orderLineEntry1 = orderEntry.getOrderReleases().get(0).getOrderLines().get(0);
        OrderLineEntry orderLineEntry2 = orderEntry.getOrderReleases().get(1).getOrderLines().get(0);

        sft.assertNotEquals(orderLineEntry1.getCancellationPpsId(),orderLineEntry2.getCancellationPpsId(),"PPSID should not be same for two cancelled lines");
        log.info("PPS IDs Before LastLine Cancellation" + orderLineEntry1.getCancellationPpsId() +" : "+orderLineEntry2.getCancellationPpsId());


        ErrorResponse exchangeErrorResponse =(ErrorResponse) ppsServiceHelper.createExchange(""+orderID, ""+orderLineEntry2.getId(), "DNL", 1, ""+orderLineEntry1.getSkuId());
        log.debug("Status code :"+ exchangeErrorResponse.getErrorCode());
        log.debug("Error Reason code :"+ exchangeErrorResponse.getErrorReason());
        sft.assertEquals(exchangeErrorResponse.getErrorCode().intValue(), 2023, "Exchange Order Error Code is not same");
        sft.assertAll();

    }
    
    @Test(enabled = true,groups={"regression","sanity","cancellation"}, description="Packed Order After cancelling some quantity for an Item in OMS")
    public void packOrderAfterSomeQuantityCancellation() throws Exception {
        sft = new SoftAssert();
    	String[] skuID = {OMSTCConstants.OMSLineCancellationTest.packOrderAfterSomeQuantityCancellation_ITEM1+":2"};
        
        atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OMSLineCancellationTest.packOrderAfterSomeQuantityCancellation_ITEM1+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":10000:0:"+vectorSellerID+":1"},supplyTypeOnHand);
        imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OMSLineCancellationTest.packOrderAfterSomeQuantityCancellation_ITEM1+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":10000:0:"+vectorSellerID},supplyTypeOnHand);
        orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuID, "", false, false, false, "", false);
        omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
        orderEntry = omsServiceHelper.getOrderEntry(orderID);
        orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();
        String orderReleaseIdToBePacked = orderEntry.getOrderReleases().get(1).getId().toString();
        
        OrderReleaseEntry orderReleaseEntry = omsServiceHelper.getOrderReleaseEntry(orderReleaseId);
        OrderLineEntry orderLineEntry = orderReleaseEntry.getOrderLines().get(0);
        long lineID = orderLineEntry.getId();
        OrderReleaseResponse cancellationRes = omsServiceHelper.cancelLine(orderReleaseId, login, new String[] {lineID +":1"}, 39);
        sft.assertEquals(cancellationRes.getStatus().getStatusType(), Type.SUCCESS,"Verify response success after cancel Line Actual:"+cancellationRes.getStatus().getStatusType());
        End2EndHelper.sleep(delayedTime);
        HashMap<String, HashMap<String, Object>> blockProcessingCounts = imsServiceHelper.getIMSInventoryDetails(new String[] {OMSTCConstants.OMSLineCancellationTest.packOrderAfterSomeQuantityCancellation_ITEM1+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":"+storeId+":"+vectorSellerID});
        HashMap<String, HashMap<String, Object>> inventoryDetailsATP = atpServiceHelper.getAtpInvDetails(new String[] {OMSTCConstants.OMSLineCancellationTest.packOrderAfterSomeQuantityCancellation_ITEM1});
        int blockOrderCount3833 = (int) blockProcessingCounts.get(OMSTCConstants.OMSLineCancellationTest.packOrderAfterSomeQuantityCancellation_ITEM1).get("blocked_order_count");
        int inventoryCounty3833 = (int) blockProcessingCounts.get(OMSTCConstants.OMSLineCancellationTest.packOrderAfterSomeQuantityCancellation_ITEM1).get("inventory_count");

        int blockOrderCountATP3833 = (int) inventoryDetailsATP.get(OMSTCConstants.OMSLineCancellationTest.packOrderAfterSomeQuantityCancellation_ITEM1).get("blocked_order_count");
        int inventoryCountyATP3833 = (int) inventoryDetailsATP.get(OMSTCConstants.OMSLineCancellationTest.packOrderAfterSomeQuantityCancellation_ITEM1).get("inventory_count");
        
        omsServiceHelper.markReadyToDispatchV3ForMyntraSeller(orderReleaseIdToBePacked,ReadyToDispatchType.POSITIVE.name());
        sft.assertTrue(omsServiceHelper.validateReleaseStatusInOMS(orderReleaseIdToBePacked, EnumSCM.PK, 5), "Release should be in Packed status");
        End2EndHelper.sleep(delayedTime);
        blockProcessingCounts = imsServiceHelper.getIMSInventoryDetails(new String[] {OMSTCConstants.OMSLineCancellationTest.packOrderAfterSomeQuantityCancellation_ITEM1+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":"+storeId+":"+vectorSellerID});
        inventoryDetailsATP = atpServiceHelper.getAtpInvDetails(new String[] {OMSTCConstants.OMSLineCancellationTest.packOrderAfterSomeQuantityCancellation_ITEM1});

        int blockOrderCountAfterPK3833 = (int) blockProcessingCounts.get(OMSTCConstants.OMSLineCancellationTest.packOrderAfterSomeQuantityCancellation_ITEM1).get("blocked_order_count");
        int inventoryCountyAfterPK3833 = (int) blockProcessingCounts.get(OMSTCConstants.OMSLineCancellationTest.packOrderAfterSomeQuantityCancellation_ITEM1).get("inventory_count");

        int blockOrderCountATPAfter3833 = (int) inventoryDetailsATP.get(OMSTCConstants.OMSLineCancellationTest.packOrderAfterSomeQuantityCancellation_ITEM1).get("blocked_order_count");
        int inventoryCountyATPAfter3833 = (int) inventoryDetailsATP.get(OMSTCConstants.OMSLineCancellationTest.packOrderAfterSomeQuantityCancellation_ITEM1).get("inventory_count");

        sft.assertEquals(blockOrderCount3833-1, blockOrderCountAfterPK3833, "ATP - Packed Order Should not take IC Lines");
        sft.assertEquals(inventoryCounty3833-1, inventoryCountyAfterPK3833, "ATP - Packed Order Should not take IC Lines");

        sft.assertEquals(blockOrderCountATP3833-1, blockOrderCountATPAfter3833, "ATP - Packed Order Should not take IC Lines");
        sft.assertEquals(inventoryCountyATP3833-1, inventoryCountyATPAfter3833, "ATP - Packed Order Should not take IC Lines");
        sft.assertAll();

    }
    
    /**
     * @param orderReleaseId
     * @param login
     * @param lineId
     * @param qty
     * @param reasonId
     * @throws UnsupportedEncodingException
     * @throws JAXBException
     */
    public void cancelLineSuccess(String orderReleaseId,String login,String lineId,int qty,int reasonId) throws UnsupportedEncodingException, JAXBException{
    	sft = new SoftAssert();
    	OrderReleaseResponse cancellationLineRes = omsServiceHelper.cancelLine(orderReleaseId,login, new String[] {lineId +":"+qty}, reasonId);
        sft.assertEquals(cancellationLineRes.getStatus().getStatusType(), Type.SUCCESS,"Verify response after cancel Line Actual:"+cancellationLineRes.getStatus().getStatusType());
        sft.assertTrue(omsServiceHelper.validateReleaseStatusInOMS(orderReleaseId,EnumSCM.F, 10),"Release is not in F status");
        sft.assertTrue(omsServiceHelper.validateLineStatusInOMS(lineId,EnumSCM.IC, 10),"Release is not in F status");
        sft.assertAll();
    }
    
    /**
     * @param orderReleaseId
     * @param login
     * @param lineId
     * @param qty
     * @param reasonId
     * @param failureMessage
     * @throws UnsupportedEncodingException
     * @throws JAXBException
     */
    public void cancelLineFailure(String orderReleaseId,String login,String lineId,int qty,int reasonId,int statusCode) throws UnsupportedEncodingException, JAXBException{
    	sft = new SoftAssert();
    	OrderReleaseResponse cancellationLineRes = omsServiceHelper.cancelLine(orderReleaseId,login, new String[] {lineId +":"+qty}, reasonId);
        sft.assertEquals(cancellationLineRes.getStatus().getStatusType(), Type.ERROR,"Verify response after cancel Line Actual:"+cancellationLineRes.getStatus().getStatusType());
        sft.assertEquals(cancellationLineRes.getStatus().getStatusCode(), statusCode,"Verif statusCode is correct");
        sft.assertAll();
    }
    
    /**
     * @param orderReleaseId
     * @param login
     * @param reasonID
     * @param comment
     * @throws UnsupportedEncodingException
     * @throws JAXBException
     */
    public void cancelReleaseSuccess(String orderReleaseId,String login,String reasonID,String comment) throws UnsupportedEncodingException, JAXBException{
        OrderReleaseResponse cancellationRes = omsServiceHelper.cancelOrderRelease(orderReleaseId,reasonID, login, comment);
        sft = new SoftAssert();
        sft.assertEquals(cancellationRes.getStatus().getStatusType(), Type.SUCCESS,"verify response after cancel");
        sft.assertTrue(omsServiceHelper.validateReleaseStatusInOMS(orderReleaseId, EnumSCM.F, 10),"Release is not in F status");
        String lineId = ""+omsServiceHelper.getOrderReleaseEntry(orderReleaseId).getOrderLines().get(0).getId();
        sft.assertTrue(omsServiceHelper.validateLineStatusInOMS(lineId,EnumSCM.IC, 10),"Release is not in F status");
        sft.assertAll();
    }
    
    /**
     * @param orderReleaseId
     * @param login
     * @param reasonID
     * @param comment
     * @throws UnsupportedEncodingException
     * @throws JAXBException
     */
    public void cancelReleaseFailure(String orderReleaseId,String login,String reasonID,String comment,int statusCode ) throws UnsupportedEncodingException, JAXBException{
        OrderReleaseResponse cancellationRes = omsServiceHelper.cancelOrderRelease(orderReleaseId,reasonID, login, comment);
        sft = new SoftAssert();
        sft.assertEquals(cancellationRes.getStatus().getStatusType(), Type.ERROR,"verify response after cancel");
        sft.assertEquals(cancellationRes.getStatus().getStatusCode(), statusCode,"verify statuscode after cancel");
        sft.assertAll();
    }
    
    /**
     * @param orderReleaseId
     * @param login
     * @param reasonID
     * @param comment
     * @throws UnsupportedEncodingException
     * @throws JAXBException
     */
    public void cancelOrderSuccess(String orderId,String login,String reasonID,String comment) throws UnsupportedEncodingException, JAXBException{
        OrderResponse cancellationRes = omsServiceHelper.cancelOrder(orderId,reasonID, login, comment);
        sft = new SoftAssert();
        sft.assertEquals(cancellationRes.getStatus().getStatusType(), Type.SUCCESS,"verify response after cancel");
        orderReleaseEntries = omsServiceHelper.getOrderEntry(orderId).getOrderReleases();
        for(OrderReleaseEntry orderReleaseEntry:orderReleaseEntries){
        	orderReleaseId = orderReleaseEntry.getId().toString();
            sft.assertTrue(omsServiceHelper.validateReleaseStatusInOMS(orderReleaseId, EnumSCM.F, 10),"Release is not in F status");
            String lineId = ""+omsServiceHelper.getOrderReleaseEntry(orderReleaseId).getOrderLines().get(0).getId();
            sft.assertTrue(omsServiceHelper.validateLineStatusInOMS(lineId,EnumSCM.IC, 10),"Release is not in F status");
        }

        sft.assertAll();
    }
    
    /**
     * @param orderReleaseId
     * @param login
     * @param reasonID
     * @param comment
     * @throws UnsupportedEncodingException
     * @throws JAXBException
     */
    public void cancelOrderFailure(String orderId,String login,String reasonID,String comment,int statusCode) throws UnsupportedEncodingException, JAXBException{
    	OrderResponse cancellationRes = omsServiceHelper.cancelOrder(orderId,reasonID, login, comment);
        sft = new SoftAssert();
        sft.assertEquals(cancellationRes.getStatus().getStatusType(), Type.ERROR,"verify response after cancel");
        sft.assertEquals(cancellationRes.getStatus().getStatusCode(), statusCode,"verify statuscode after cancel");
        sft.assertAll();
    }

    
    
	/**
     * @param orderReleaseId
     * @param lineID
     * @param lineStatus

     * @param releaseStatus
     * @throws UnsupportedEncodingException
     * @throws JAXBException
     */
    private void verifyCancelDataAfterCancel(String orderReleaseId, String lineID, String lineStatus, int cancelQty,
		 String releaseStatus,String cancelReasonMessage,String cancelReasonId) throws UnsupportedEncodingException, JAXBException {
		sft = new SoftAssert();
		int cancelledLinesCount = 0;
		List<OrderLineEntry> orderLineEntries = omsServiceHelper.getOrderLineEntries(orderReleaseId);
		for(OrderLineEntry orderLineEntry : orderLineEntries){
			if(orderLineEntry.getStatus().equalsIgnoreCase("IC")){
				cancelledLinesCount++;
				sft.assertEquals(orderLineEntry.getStatus(), lineStatus,"Line status should be IC but Actual:"+orderLineEntry.getStatus());
		        sft.assertEquals(""+orderLineEntry.getQuantity(), cancelQty+"","Line quantity should be "+cancelQty+" but Actual:"+orderLineEntry.getQuantity());
		        OrderReleaseEntry orderReleaseEntry = omsServiceHelper.getOrderReleaseEntry(orderReleaseId);
		        sft.assertEquals(orderReleaseEntry.getStatus(), releaseStatus,"Release status should be F but Actual:"+orderReleaseEntry.getStatus());
		        sft.assertEquals(""+orderLineEntry.getCancellationReason(), cancelReasonMessage,"Reason should be same");
		        sft.assertEquals(""+orderLineEntry.getCancellationReasonId(), cancelReasonId,"Cancel reason should be same Actual:"+orderLineEntry.getCancellationReasonId());
		        
			}
			
		}
		sft.assertTrue(cancelledLinesCount>0,"There is no Line cancelled in the release");
    	sft.assertAll();
		
	}
    
    /**
     * @param orderID
     * @throws UnsupportedEncodingException
     * @throws JAXBException
     */
    public void verifyPPSIdsForCancelledLinesInTheOrder(String orderID) throws UnsupportedEncodingException, JAXBException{
    	sft = new SoftAssert();
    	ArrayList<String> listOfCancelledPPSIds = new ArrayList<>();
    	
    	OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
    	List<OrderReleaseEntry> orderReleaseEntries = orderEntry.getOrderReleases();
    	
    	for(OrderReleaseEntry orderReleaseEntry:orderReleaseEntries){
    		List<OrderLineEntry> orderLineEntries = orderReleaseEntry.getOrderLines();
        	
        	for(OrderLineEntry orderLineEntry: orderLineEntries){
        		if(orderLineEntry.getStatus().equalsIgnoreCase("IC")){
        			sft.assertNotNull(orderLineEntry.getCancellationPpsId(),"Verify PPSID should not be null");
        			listOfCancelledPPSIds.add(orderLineEntry.getCancellationPpsId());
        		}else{
        			sft.assertNull(orderLineEntry.getCancellationPpsId(),"Verify PPSID should be null");
        		}
        		
        	}
    	}
    	
    	sft.assertTrue(verifyPPSIdsAreNotSame(listOfCancelledPPSIds),"PPSIds should not be same");
    	sft.assertAll();
    }

	/**
	 * @param listOfCancelledPPSIds
	 * @return
	 */
	private Boolean verifyPPSIdsAreNotSame(ArrayList<String> listOfCancelledPPSIds) {
		// TODO Auto-generated method stub
		Set<String> setOfPPSIds = new HashSet<String>();
		setOfPPSIds.addAll(listOfCancelledPPSIds);
		
		if(listOfCancelledPPSIds.size()==setOfPPSIds.size()){
			return true;
		}
		
		return false;
	}
	
	
	//*******************************************Release Cancellation**************************************************

	//Passed
    @Test(enabled=true, description="Cancel Order Release which do not have warehouse assigned", groups={"regression","sanity","cancellation"})
    public void cancelReleaseForWhichWHIsNotAssigned() throws Exception {
    	sft = new SoftAssert();
    	atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OMSReleaseCancellation.cancelReleaseForWhichWHIsNotAssigned_ITEM1+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+","+OMSTCConstants.WareHouseIds.warehouseId36_BW+":10000:0:"+vectorSellerID+":1"},supplyTypeOnHand );
        imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OMSReleaseCancellation.cancelReleaseForWhichWHIsNotAssigned_ITEM1+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+","+OMSTCConstants.WareHouseIds.warehouseId36_BW+":0:0:"+vectorSellerID},supplyTypeOnHand);
		
        String orderID =  end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_123456, new String[] {OMSTCConstants.OMSReleaseCancellation.cancelReleaseForWhichWHIsNotAssigned_ITEM1+":1"}, "", false, false, false, "",false);
        omsServiceHelper.checkReleaseStatusForOrder(orderID,"Q");
        waitForOrderLevelOnHoldToResolve(orderID);
        
        orderEntry = omsServiceHelper.getOrderEntry(orderID);
        orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();
        
        int iocBocBefore = atpServiceHelper.getAtpInvAndBlockOrderCount(new String[] { OMSTCConstants.OMSReleaseCancellation.cancelReleaseForWhichWHIsNotAssigned_ITEM1 }).get(OMSTCConstants.OMSReleaseCancellation.cancelReleaseForWhichWHIsNotAssigned_ITEM1)[1];
        OrderReleaseEntry orderReleaseEntry = omsServiceHelper.getOrderReleaseEntry(orderReleaseId);
        cancelReleaseSuccess(orderReleaseId, login, "1", "TestOrder Cancellation");
        end2EndHelper.sleep(delayedTime);
        
        int iocBocAfter = atpServiceHelper.getAtpInvAndBlockOrderCount(new String[]{OMSTCConstants.OMSReleaseCancellation.cancelReleaseForWhichWHIsNotAssigned_ITEM1}).get(OMSTCConstants.OMSReleaseCancellation.cancelReleaseForWhichWHIsNotAssigned_ITEM1)[1];
        sft.assertEquals(iocBocAfter, iocBocBefore-1, "Inventory Count Should be Reduced after Order Cancellation ");
        sft.assertAll();
    }
    
    //Passed
    @Test(enabled=true, description="Cancel COD Order Release With Single Item And Single Quantity", groups={"regression","sanity","cancellation"})
    public void cancelReleaseWithSingleItemCOD() throws Exception {
    	sft = new SoftAssert();
        String skuId[] = { OMSTCConstants.OMSReleaseCancellation.cancelReleaseWithSingleItemCOD_ITEM1+":1" };


        String orderID;
        atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OMSReleaseCancellation.cancelReleaseWithSingleItemCOD_ITEM1+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":10000:0:"+vectorSellerID+":1"},supplyTypeOnHand );
        imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OMSReleaseCancellation.cancelReleaseWithSingleItemCOD_ITEM1+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":1000:0:"+vectorSellerID},supplyTypeOnHand);
		
        HashMap<String, int[]> inventoryCountInATPBeforePlacingOrder = atpServiceHelper
                .getAtpInvAndBlockOrderCount(new String[] { OMSTCConstants.OMSReleaseCancellation.cancelReleaseWithSingleItemCOD_ITEM1 });

        HashMap<String, int[]> inventoryCountInIMSBeforePlacingOrder = imsServiceHelper
                .getIMSInvAndBlockOrderCount(new String[] { OMSTCConstants.OMSReleaseCancellation.cancelReleaseWithSingleItemCOD_ITEM1 }, "28","1",""+vectorSellerID);

        int[] blockCountATP = inventoryCountInATPBeforePlacingOrder.get(OMSTCConstants.OMSReleaseCancellation.cancelReleaseWithSingleItemCOD_ITEM1);
        int blockCountATPBefore = blockCountATP[1];
        
        int[] blockCountIMS = inventoryCountInIMSBeforePlacingOrder.get(OMSTCConstants.OMSReleaseCancellation.cancelReleaseWithSingleItemCOD_ITEM1);
        int blockCountIMSBefore = blockCountIMS[1];
        
        orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false, "", false);
        omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
        orderEntry = omsServiceHelper.getOrderEntry(orderID);
        orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();

        HashMap<String, int[]> inventoryCountInATPAfterPlacingOrder = atpServiceHelper
                .getAtpInvAndBlockOrderCount(new String[] { OMSTCConstants.OMSReleaseCancellation.cancelReleaseWithSingleItemCOD_ITEM1 });

        HashMap<String, int[]> inventoryCountInIMSAfterPlacingOrder = imsServiceHelper
                .getIMSInvAndBlockOrderCount(new String[] { OMSTCConstants.OMSReleaseCancellation.cancelReleaseWithSingleItemCOD_ITEM1 }, "28","1",""+vectorSellerID);

        blockCountATP = inventoryCountInATPAfterPlacingOrder.get(OMSTCConstants.OMSReleaseCancellation.cancelReleaseWithSingleItemCOD_ITEM1);
        int blockCountATPAfter = blockCountATP[1]-1;
        
        blockCountIMS = inventoryCountInIMSAfterPlacingOrder.get(OMSTCConstants.OMSReleaseCancellation.cancelReleaseWithSingleItemCOD_ITEM1);
        int blockCountIMSAfter = blockCountIMS[1]-1;
        
        //VerifyInventory in IMS and ATP After Order Placement
        sft.assertEquals(blockCountATPBefore, blockCountATPAfter,"ATP inventory count before cancellation");
        sft.assertEquals(blockCountIMSBefore, blockCountIMSAfter,"IMS inventory count before cancellation");
        
        cancelReleaseSuccess(orderReleaseId, login, "1", "TestOrder Cancellation");
        
        End2EndHelper.sleep(20000L);
        OrderReleaseEntry orderReleaseEntry = omsServiceHelper.getOrderReleaseEntry(orderReleaseId);
        log.info(APIUtilities.convertXMLObjectToString(orderReleaseEntry));
        log.info(orderReleaseEntry.getCancellationPpsId());
        sft.assertNotNull(orderReleaseEntry.getCancellationPpsId(), "Order Cancellation PPS ID is not Null");

        OrderCaptureReleaseEntry orderCaptureReleaseEntry = wmsServiceHelper.getCaptureOrderReleaseData(orderReleaseId);
        sft.assertTrue(isReleaseStatusSameInCaptureOrderRelease(orderReleaseId,OrderReleaseStatus.CANCELLED.toString()),"veiry release status in worms after cancel");

        //Check For Inventory Count Reduction
        HashMap<String, int[]> inventoryCountInATPAfterCancellation = atpServiceHelper
                .getAtpInvAndBlockOrderCount(new String[] { OMSTCConstants.OMSReleaseCancellation.cancelReleaseWithSingleItemCOD_ITEM1 });
        HashMap<String, int[]> inventoryCountInIMSAfterCancellation = imsServiceHelper
                .getIMSInvAndBlockOrderCount(new String[] { OMSTCConstants.OMSReleaseCancellation.cancelReleaseWithSingleItemCOD_ITEM1 },"28","1",""+vectorSellerID);
        
        blockCountATP = inventoryCountInATPAfterPlacingOrder.get(OMSTCConstants.OMSReleaseCancellation.cancelReleaseWithSingleItemCOD_ITEM1);
        int blockCountATPCancel = blockCountATP[1]-1;
        
        blockCountIMS = inventoryCountInIMSAfterPlacingOrder.get(OMSTCConstants.OMSReleaseCancellation.cancelReleaseWithSingleItemCOD_ITEM1);
        int blockCountIMSCancel = blockCountIMS[1]-1;

        sft.assertEquals(blockCountATPBefore, blockCountATPCancel,"ATP inventory count after cancellation");
        sft.assertEquals(blockCountIMSBefore, blockCountIMSCancel,"IMS inventory count after cancellation");
        sft.assertAll();
    }
    
    //passed
    @Test(enabled = true, dataProvider="cancelReleaseOfAPackOrderDP", dataProviderClass=CancellationDP.class, groups={"regression","sanity","cancellation"}, description="Check Single Item Line Cancellation triggers Release and Order Cancellation")
    public void cancelReleaseOfAPackOrder(String releaseID, String login) throws UnsupportedEncodingException, JAXBException{
        sft = new SoftAssert();
    	orderEntry = omsServiceHelper.getOrderEntry(releaseID);
        orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();
        
        cancelReleaseFailure(orderReleaseId, login, "39", "Order Release Cancellation",8252);
        sft.assertAll();
    }
    
    //passed
    @Test(enabled = true, dataProvider="cancelReleaseOfASHippedOrderDP", dataProviderClass=CancellationDP.class, groups={"regression","sanity","cancellation"}, description="Check Single Item Line Cancellation triggers Release and Order Cancellation")
    public void checkReleaseCancellationOfAShippedOrder(String releaseID, String login) throws UnsupportedEncodingException, JAXBException{
        sft = new SoftAssert();
    	orderEntry = omsServiceHelper.getOrderEntry(releaseID);
        orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();
        
        cancelReleaseFailure(orderReleaseId, login, "39", "Order Release Cancellation",8252);
        sft.assertAll();
    }

    //passed
    @Test(enabled = true, dataProvider="cancelReleaseOfADeliveredOrderDP", dataProviderClass=CancellationDP.class, groups={"regression","sanity","cancellation"}, description="Check Single Item Line Cancellation triggers Release and Order Cancellation")
    public void checkReleaseCancellationOfADeliveredOrder(String releaseID, String login) throws UnsupportedEncodingException, JAXBException{
    	sft = new SoftAssert();
    	orderEntry = omsServiceHelper.getOrderEntry(releaseID);
        orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();
        
        cancelReleaseFailure(orderReleaseId, login, "39", "Order Release Cancellation",8252);
        sft.assertAll();
    }

    @Test(enabled = true, dataProvider="checkReleaseCancellationOfAnOrderWithLineAsQDDP", dataProviderClass=CancellationDP.class, groups={"regression","sanity","cancellation"}, description="Check Single Item Line Cancellation triggers Release and Order Cancellation")
    public void checkReleaseCancellationOfAnOrderWithLineAsQDInPackman(String orderID, String login) throws UnsupportedEncodingException, JAXBException{
    	sft = new SoftAssert();
    	orderEntry = omsServiceHelper.getOrderEntry(orderID);
        orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();
        updateReleaseInPackmanAsQD(orderReleaseId);
    	
        OrderReleaseResponse cancellationRes = omsServiceHelper.cancelOrderRelease(orderReleaseId, "39", login, "Order Release Cancellation");
    	sft.assertEquals(cancellationRes.getStatus().getStatusType(), Type.ERROR,"Verify response type as error after cancel release");
    	sft.assertAll();
    }
    
    @Test(enabled = true, dataProvider="checkReleaseCancellationOfAnOrderWithLineAsQDDP", dataProviderClass=CancellationDP.class, groups={"regression","sanity","cancellation"}, description="Check Single Item Line Cancellation triggers Release and Order Cancellation")
    public void checkLineCancellationOfAnOrderWithLineAsQDInPackman(String orderID, String login) throws UnsupportedEncodingException, JAXBException{
    	sft = new SoftAssert();
    	orderEntry = omsServiceHelper.getOrderEntry(orderID);
        orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();
        updateReleaseInPackmanAsQD(orderReleaseId);
        
        orderLineEntry = omsServiceHelper.getOrderReleaseEntry(orderReleaseId).getOrderLines().get(0);
        long lineId = orderLineEntry.getId();
        int qty = orderLineEntry.getQuantity();
        OrderReleaseResponse cancellationLineRes = omsServiceHelper.cancelLine(orderReleaseId,login, new String[] {lineId +":"+qty}, 1);
    	sft.assertEquals(cancellationLineRes.getStatus().getStatusType(), Type.ERROR,"Verify response type as error after cancel release");
    	sft.assertAll();
    }
    
    @Test(enabled = true, dataProvider="checkReleaseCancellationOfAnOrderWithLineAsQDDP", dataProviderClass=CancellationDP.class, groups={"regression","sanity","cancellation"}, description="Check Single Item Line Cancellation triggers Release and Order Cancellation")
    public void checkOrderCancellationOfAnOrderWithLineAsQDInPackman(String orderID, String login) throws UnsupportedEncodingException, JAXBException{
    	sft = new SoftAssert();
    	orderEntry = omsServiceHelper.getOrderEntry(orderID);
        orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();
        updateReleaseInPackmanAsQD(orderReleaseId);
		
        OrderResponse cancellationRes = omsServiceHelper.cancelOrder(orderID, "1", "erpadmin","TestOrder Cancellation");
		sft.assertEquals(cancellationRes.getStatus().getStatusType(), Type.ERROR, "Verify Response Status Type");
    	sft.assertAll();
    }
    
    /**
     * This is hack to get data as QD in packman
     * @param orderReleaseId2
     */
    private void updateReleaseInPackmanAsQD(String orderReleaseId) {
		// TODO Auto-generated method stub
        String query = "select `portal_order_release_id` from seller_packet_item where store_id=1 ORDER BY `created_on` limit 1;";
        Map<String,Object> resultSet = (Map<String, Object>) DBUtilities.exSelectQuery(query, "myntra_packman").get(0);
        String rleaseIdFetched = ""+resultSet.get("portal_order_release_id");
        query = "update seller_packet_item SET `status_code`='"+EnumSCM.QD+"',`portal_order_release_id`='"+orderReleaseId+"' where `portal_order_release_id` in ('"+rleaseIdFetched+"');";
        DBUtilities.exUpdateQuery(query, "myntra_packman");
        
	}


	//Passed
    @Test(enabled = true, dataProvider="checkReleaseCancellationOfACancelledOrderDP", dataProviderClass=CancellationDP.class, groups={"regression","sanity","cancellation"}, description="Check Single Item Line Cancellation triggers Release and Order Cancellation")
    public void checkReleaseCancellationOfACancelledOrder(String orderReleaseID, String login) throws UnsupportedEncodingException, JAXBException{
    	sft = new SoftAssert();
        orderEntry = omsServiceHelper.getOrderEntry(orderReleaseID);
        orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();
        
        cancelReleaseFailure(orderReleaseId, login, "39", "Order Release Cancellation",8249);
        sft.assertAll();
    }

    @Test(enabled=true,groups={"regression","ReleaseCancellation"})
    public void checkReplacementCouponGenerationOnReleaseCancellation() throws Exception {
    	sft = new SoftAssert();
        String skuId[] = { OMSTCConstants.OtherSkus.skuId_3832+":2" };
        String orderID;
        DBUtilities.exUpdateQuery("Delete from xcart_discount_coupons where coupon not in ('pati1','pati2','pati3') and users='"+ uidx +"';", "myntra");
        orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "pati3", false, false, false, "", false);
        omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
        orderEntry = omsServiceHelper.getOrderEntry(orderID);
        orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();

        OrderReleaseResponse cancellationRes = omsServiceHelper.cancelOrderRelease(orderReleaseId, "1", "erpadmin", "TestOrder Cancellation");
        sft.assertEquals(cancellationRes.getStatus().getStatusCode(), 1006,"verify status code after cancel release");
        sft.assertEquals(cancellationRes.getStatus().getStatusType(), Type.SUCCESS,"verify response type after cancel release");
        sft.assertEquals(cancellationRes.getData().get(0).getStatus(), "F","verify release status is F after cancel");
        sft.assertTrue(omsServiceHelper.validateReleaseStatusInOMS(orderReleaseId, "F", 10),"Release is not in F status");
        end2EndHelper.sleep(6000L);
        List coupon = DBUtilities.exSelectQuery("Select * from xcart_discount_coupons where coupon like 'CAN%' and MRPpercentage=40 and users='"+ uidx +"';", "myntra");
        List coupon1 = DBUtilities.exSelectQuery("Select * from xcart_discount_coupons where coupon like 'GW%' and MRPpercentage=40.00 and users='"+ uidx +"';", "myntra");
        sft.assertEquals(coupon.size(), 1, "Cancellation Replacement coupon is not generated");
        sft.assertEquals(coupon1.size(), 1, "Cancellation GoodWill coupon is not generated");
        sft.assertAll();
    }

    //Passed
    @Test(enabled=true, groups={"regression","sanity","cancellation"})
    public void cancelOrderFollowedByReleaseCancellation() throws Exception {
    	sft = new SoftAssert();
    	atpServiceHelper.updateInventoryDetails(new String[] {OMSTCConstants.OMSReleaseCancellation.cancelOrderFollowedByReleaseCancellation_ITEM1+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":10000:0:"+vectorSellerID+":1", 
    			OMSTCConstants.OMSReleaseCancellation.cancelOrderFollowedByReleaseCancellation_ITEM2+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":10000:0:"+vectorSellerID+":1"},supplyTypeOnHand);

        imsServiceHelper.updateInventoryForSeller(new String[] {OMSTCConstants.OMSReleaseCancellation.cancelOrderFollowedByReleaseCancellation_ITEM1+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:"+vectorSellerID, 
    			OMSTCConstants.OMSReleaseCancellation.cancelOrderFollowedByReleaseCancellation_ITEM2+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:"+vectorSellerID},supplyTypeOnHand);

        String skuId[] = { OMSTCConstants.OMSReleaseCancellation.cancelOrderFollowedByReleaseCancellation_ITEM1+":2", OMSTCConstants.OMSReleaseCancellation.cancelOrderFollowedByReleaseCancellation_ITEM2+":2" };
        // Get The ATP/IMS Data Before Cancellation
        HashMap<String, int[]> inventoryCountInATPBeforePlacingOrder = atpServiceHelper
                .getAtpInvAndBlockOrderCount(new String[] { OMSTCConstants.OMSReleaseCancellation.cancelOrderFollowedByReleaseCancellation_ITEM1, OMSTCConstants.OMSReleaseCancellation.cancelOrderFollowedByReleaseCancellation_ITEM2 });

        HashMap<String, int[]> inventoryCountInIMSBeforePlacingOrder_3831 = imsServiceHelper
                .getIMSInvAndBlockOrderCount(new String[] { OMSTCConstants.OMSReleaseCancellation.cancelOrderFollowedByReleaseCancellation_ITEM1 },""+OMSTCConstants.WareHouseIds.warehouseId36_BW,"1",""+vectorSellerID);

        HashMap<String, int[]> inventoryCountInIMSBeforePlacingOrder_3834 = imsServiceHelper
                .getIMSInvAndBlockOrderCount(new String[] { OMSTCConstants.OMSReleaseCancellation.cancelOrderFollowedByReleaseCancellation_ITEM2 },""+OMSTCConstants.WareHouseIds.warehouseId36_BW,"1",""+vectorSellerID);

        String orderID =  end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false, "", false);
        omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
        OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
        List<OrderReleaseEntry> orderReleaseEntries = orderEntry.getOrderReleases();
        String orderReleaseID1 = orderReleaseEntries.get(0).getId().toString();

        cancelReleaseSuccess(orderReleaseID1, login, "1", "Test Order Release Cancellation");
        
        cancelOrderSuccess(orderID, login, "1", "TestOrder Cancellation");
        End2EndHelper.sleep(delayedTime);
        
        HashMap<String, int[]> inventoryCountInATPAfterCancellation = atpServiceHelper
                .getAtpInvAndBlockOrderCount(new String[] { OMSTCConstants.OMSReleaseCancellation.cancelOrderFollowedByReleaseCancellation_ITEM1, OMSTCConstants.OMSReleaseCancellation.cancelOrderFollowedByReleaseCancellation_ITEM2 });

        HashMap<String, int[]> inventoryCountInIMSAfterCancellation_3831 = imsServiceHelper
                .getIMSInvAndBlockOrderCount(new String[] { OMSTCConstants.OMSReleaseCancellation.cancelOrderFollowedByReleaseCancellation_ITEM1 }, ""+OMSTCConstants.WareHouseIds.warehouseId36_BW,"1",""+vectorSellerID);

        HashMap<String, int[]> inventoryCountInIMSAfterCancellation_3834 = imsServiceHelper
                .getIMSInvAndBlockOrderCount(new String[] { OMSTCConstants.OMSReleaseCancellation.cancelOrderFollowedByReleaseCancellation_ITEM2 }, ""+OMSTCConstants.WareHouseIds.warehouseId36_BW,"1",""+vectorSellerID);

        sft.assertEquals(Arrays.toString(inventoryCountInATPAfterCancellation.get(OMSTCConstants.OMSReleaseCancellation.cancelOrderFollowedByReleaseCancellation_ITEM1)), Arrays.toString(inventoryCountInATPBeforePlacingOrder.get(OMSTCConstants.OMSReleaseCancellation.cancelOrderFollowedByReleaseCancellation_ITEM1)), "ATP Inventory and Block Order Count Not Reduced After Cancellation");
        sft.assertEquals(Arrays.toString(inventoryCountInIMSAfterCancellation_3831.get(OMSTCConstants.OMSReleaseCancellation.cancelOrderFollowedByReleaseCancellation_ITEM1)), Arrays.toString(inventoryCountInIMSBeforePlacingOrder_3831.get(OMSTCConstants.OMSReleaseCancellation.cancelOrderFollowedByReleaseCancellation_ITEM1)), "IMS Inventory and Block Order Count Not Reduced After Cancellation");
        sft.assertEquals(Arrays.toString(inventoryCountInIMSAfterCancellation_3834.get(OMSTCConstants.OMSReleaseCancellation.cancelOrderFollowedByReleaseCancellation_ITEM2)), Arrays.toString(inventoryCountInIMSBeforePlacingOrder_3834.get(OMSTCConstants.OMSReleaseCancellation.cancelOrderFollowedByReleaseCancellation_ITEM2)), "IMS Inventory and Block Order Count Not Reduced After Cancellation");
        sft.assertAll();
    }



    @Test(enabled = true, description = "Cancel Order After Item Association",groups={"regression","sanity","cancellation"})
    public void cancelOrderReleaseAfterItemAssociation() throws Exception {
    	sft = new SoftAssert();
        String skuId[] = { OMSTCConstants.OMSReleaseCancellation.cancelOrderReleaseAfterItemAssociation_ITEM1+":1", OMSTCConstants.OMSReleaseCancellation.cancelOrderReleaseAfterItemAssociation_ITEM2+":1" };
        
        atpServiceHelper.updateInventoryDetails(new String[] {OMSTCConstants.OMSReleaseCancellation.cancelOrderReleaseAfterItemAssociation_ITEM1+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":10000:0:"+vectorSellerID+":1", 
    			OMSTCConstants.OMSReleaseCancellation.cancelOrderReleaseAfterItemAssociation_ITEM2+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":10000:0:"+vectorSellerID+":1"},supplyTypeOnHand);

        imsServiceHelper.updateInventoryForSeller(new String[] {OMSTCConstants.OMSReleaseCancellation.cancelOrderReleaseAfterItemAssociation_ITEM1+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:"+vectorSellerID, 
    			OMSTCConstants.OMSReleaseCancellation.cancelOrderReleaseAfterItemAssociation_ITEM2+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:"+vectorSellerID},supplyTypeOnHand);

        String orderID =  end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false,"", false);
        omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
        orderEntry = omsServiceHelper.getOrderEntry(orderID);
        orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();
        
        OrderReleaseEntry orderReleaseEntry = omsServiceHelper.getOrderReleaseEntry(orderReleaseId);
        String wareHouseID = orderReleaseEntry.getWarehouseId().toString();

         // Push Order to Order wave for picking
        omsServiceHelper.pushOrderToWave(orderReleaseId,Integer.parseInt(wareHouseID));
        End2EndHelper.sleep(15000L);
        com.myntra.client.wms.response.OrderReleaseResponse orderReleaseResponse = wmsServiceHelper.getCoreOrderRelease(orderReleaseId);
        sft.assertTrue(orderReleaseResponse.getData().size() > 0, "Order Not Pushed to WMS");
        com.myntra.client.wms.response.OrderReleaseEntry orderReleaseWMSEntry = orderReleaseResponse.getData().get(0);
        wmsServiceHelper.createOrderItemAssociation(orderReleaseId);

        HashMap<String, int[]> inventoryCountInATPBeforeCancellingOrder = atpServiceHelper
                .getAtpInvAndBlockOrderCount(new String[] { OMSTCConstants.OMSReleaseCancellation.cancelOrderReleaseAfterItemAssociation_ITEM1, OMSTCConstants.OMSReleaseCancellation.cancelOrderReleaseAfterItemAssociation_ITEM2});

        HashMap<String, int[]> inventoryCountInIMSBeforeCancellingOrder = imsServiceHelper
                .getIMSInvAndBlockOrderCount(new String[] { OMSTCConstants.OMSReleaseCancellation.cancelOrderReleaseAfterItemAssociation_ITEM1, OMSTCConstants.OMSReleaseCancellation.cancelOrderReleaseAfterItemAssociation_ITEM2 }, wareHouseID, "1", ""+vectorSellerID);

        int[] blockCountATP3832 = inventoryCountInATPBeforeCancellingOrder.get(OMSTCConstants.OMSReleaseCancellation.cancelOrderReleaseAfterItemAssociation_ITEM1);
        int blockCountATPBefore3832 = blockCountATP3832[1];
        int inventoryCountATPBefore3832 = blockCountATP3832[0];

        int[] blockCountIMS3832 = inventoryCountInIMSBeforeCancellingOrder.get(OMSTCConstants.OMSReleaseCancellation.cancelOrderReleaseAfterItemAssociation_ITEM1);
        int blockCountIMSBefore3832 = blockCountIMS3832[1];
        int inventoryCountIMSBefore3832 = blockCountIMS3832[0];


        int[] blockCountATP3837 = inventoryCountInATPBeforeCancellingOrder.get(OMSTCConstants.OMSReleaseCancellation.cancelOrderReleaseAfterItemAssociation_ITEM2);
        int blockCountATPBefore3837 = blockCountATP3837[1];
        int inventoryCountATPBefore3837 = blockCountATP3837[0];

        int[] blockCountIMS3837 = inventoryCountInIMSBeforeCancellingOrder.get(OMSTCConstants.OMSReleaseCancellation.cancelOrderReleaseAfterItemAssociation_ITEM2);
        int blockCountIMSBefore3837 = blockCountIMS3837[1];
        int inventoryCountIMSBefore3837 = blockCountIMS3837[0];

        HashMap<String, HashMap> blockProcessingCounts = imsServiceHelper.getIMSCoreInvDataForSku(new String[] {OMSTCConstants.OMSReleaseCancellation.cancelOrderReleaseAfterItemAssociation_ITEM1+":"+wareHouseID, OMSTCConstants.OMSReleaseCancellation.cancelOrderReleaseAfterItemAssociation_ITEM2+":"+wareHouseID});
        int blockProcessingCountBefore3832 = (int) blockProcessingCounts.get(OMSTCConstants.OMSReleaseCancellation.cancelOrderReleaseAfterItemAssociation_ITEM1).get("blocked_processing_count");
        int blockProcessingCountBefore3837 = (int) blockProcessingCounts.get(OMSTCConstants.OMSReleaseCancellation.cancelOrderReleaseAfterItemAssociation_ITEM2).get("blocked_processing_count");

        String[] items = wmsServiceHelper.getItemsAssociatedWithOrder(orderReleaseId);

        OrderReleaseResponse cancellationRes = omsServiceHelper.cancelOrderRelease(orderReleaseId, "1", "erpadmin", "Test Order Release Cancellation");
        sft.assertEquals(cancellationRes.getStatus().getStatusCode(), 1006, "Verify Response Status Code ");
        sft.assertEquals(cancellationRes.getStatus().getStatusType(), Type.SUCCESS, "Verify Response Status Type ");
        sft.assertEquals(cancellationRes.getData().get(0).getStatus(), "F", "Verify Order Release Status is F");
        End2EndHelper.sleep(25000L);
        HashMap<String, int[]> inventoryCountInATPAftereCancellingOrder = atpServiceHelper
                .getAtpInvAndBlockOrderCount(new String[] { OMSTCConstants.OMSReleaseCancellation.cancelOrderReleaseAfterItemAssociation_ITEM1, OMSTCConstants.OMSReleaseCancellation.cancelOrderReleaseAfterItemAssociation_ITEM2});

        HashMap<String, int[]> inventoryCountInIMSAfterCancellingOrder = imsServiceHelper
                .getIMSInvAndBlockOrderCount(new String[] { OMSTCConstants.OMSReleaseCancellation.cancelOrderReleaseAfterItemAssociation_ITEM1, OMSTCConstants.OMSReleaseCancellation.cancelOrderReleaseAfterItemAssociation_ITEM2 }, wareHouseID, "1", ""+vectorSellerID);

        blockCountATP3832 = inventoryCountInATPAftereCancellingOrder.get(OMSTCConstants.OMSReleaseCancellation.cancelOrderReleaseAfterItemAssociation_ITEM1);
        int blockCountATPAfter3832 = blockCountATP3832[1] + 1;
        int inventoryCountATPAfter3832 = blockCountATP3832[0] + 1;

        blockCountIMS3832 = inventoryCountInIMSAfterCancellingOrder.get(OMSTCConstants.OMSReleaseCancellation.cancelOrderReleaseAfterItemAssociation_ITEM1);
        int blockCountIMSAfter3832 = blockCountIMS3832[1] + 1;
        int inventoryCountIMSAfter3832 = blockCountIMS3832[0] + 1;


        blockCountATP3837 = inventoryCountInATPAftereCancellingOrder.get(OMSTCConstants.OMSReleaseCancellation.cancelOrderReleaseAfterItemAssociation_ITEM2);
        int blockCountATPAfter3837 = blockCountATP3837[1] + 1;
        int inventoryCountATPAfter3837 = blockCountATP3837[0] + 1;

        blockCountIMS3837 = inventoryCountInIMSAfterCancellingOrder.get(OMSTCConstants.OMSReleaseCancellation.cancelOrderReleaseAfterItemAssociation_ITEM2);
        int blockCountIMSAfter3837 = blockCountIMS3837[1] + 1;
        int inventoryCountIMSAfter3837 = blockCountIMS3837[0] + 1;

        log.info(
                "ATP Block Inventory Status Before: " + blockCountATPBefore3832 + " After :" + blockCountATPAfter3832);
        log.info(
                "ATP Block Inventory Status Before: " + blockCountATPBefore3837 + " After :" + blockCountATPAfter3837);
        log.info(
                "ATP Inventory Count Status Before: " + inventoryCountATPBefore3832 + " After :" + inventoryCountATPAfter3832);

        log.info(
                "ATP Inventory Count Status Before: " + inventoryCountATPBefore3837 + " After :" + inventoryCountATPAfter3837);

        log.info(
                "IMS Block Inventory Status Before: " + blockCountIMSBefore3832 + " After :" + blockCountIMSAfter3832);
        log.info(
                "IMS Block Inventory Status Before: " + blockCountIMSBefore3837 + " After :" + blockCountIMSAfter3837);

        // Check Inventory Status
        sft.assertEquals(blockCountATPBefore3832, blockCountATPAfter3832,
                            "ATP Block Order Count After Order Cancellation For 3832");

        sft.assertEquals(blockCountATPBefore3837, blockCountATPAfter3837,
                            "ATP Block Order Count After Order Cancellation For 3837" );

        sft.assertEquals(inventoryCountATPBefore3832, inventoryCountATPAfter3832,
                            "ATP Inventory Count After Order Cancellation For 3832" );

        sft.assertEquals(inventoryCountATPBefore3837, inventoryCountATPAfter3837,
                            "ATP Inventory Count After Order Cancellation For 3837" );

        sft.assertEquals(blockCountATPBefore3832, blockCountATPAfter3832,
                            "ATP Block Order Count After Order Cancellation For 3832");

        sft.assertEquals(blockCountATPBefore3837, blockCountATPAfter3837,
                            "ATP Block Order Count After Order Cancellation For 3837" );

        sft.assertEquals(inventoryCountATPBefore3832, inventoryCountATPAfter3832,
                            "ATP Inventory Count After Order Cancellation For 3832" );

        sft.assertEquals(inventoryCountATPBefore3837, inventoryCountATPAfter3837,
                            "ATP Inventory Count After Order Cancellation For 3837" );

        sft.assertEquals(blockCountIMSBefore3832, blockCountIMSAfter3832,
                            "ATP Block Order Count After Order Cancellation For 3832");

        sft.assertEquals(blockCountIMSBefore3837, blockCountIMSAfter3837,
                            "ATP Block Order Count After Order Cancellation For 3837" );

        sft.assertEquals(inventoryCountIMSBefore3832, inventoryCountIMSAfter3832,
                            "ATP Inventory Count After Order Cancellation For 3832" );

        sft.assertEquals(inventoryCountIMSBefore3837, inventoryCountIMSAfter3837,
                            "ATP Inventory Count After Order Cancellation For 3837" );

        sft.assertEquals(blockCountIMSBefore3832, blockCountIMSAfter3832,
                            "ATP Block Order Count After Order Cancellation For 3832");

        sft.assertEquals(blockCountIMSBefore3837, blockCountIMSAfter3837,
                            "ATP Block Order Count After Order Cancellation For 3837" );

        sft.assertEquals(inventoryCountIMSBefore3832, inventoryCountIMSAfter3832,
                            "ATP Inventory Count After Order Cancellation For 3832" );

        sft.assertEquals(inventoryCountIMSBefore3837, inventoryCountIMSAfter3837,
                            "ATP Inventory Count After Order Cancellation For 3837" );

        blockProcessingCounts = imsServiceHelper.getIMSCoreInvDataForSku(new String[] {OMSTCConstants.OMSReleaseCancellation.cancelOrderReleaseAfterItemAssociation_ITEM1+":"+wareHouseID, OMSTCConstants.OMSReleaseCancellation.cancelOrderReleaseAfterItemAssociation_ITEM2+":"+wareHouseID});
        int blockProcessingCountAfter3832 = (int) blockProcessingCounts.get(OMSTCConstants.OMSReleaseCancellation.cancelOrderReleaseAfterItemAssociation_ITEM1).get("blocked_processing_count");
        int blockProcessingCountAfter3837 = (int) blockProcessingCounts.get(OMSTCConstants.OMSReleaseCancellation.cancelOrderReleaseAfterItemAssociation_ITEM2).get("blocked_processing_count");
        sft.assertEquals(blockProcessingCountAfter3832, blockProcessingCountBefore3832+1, "Block Processing count should increase");
        sft.assertEquals(blockProcessingCountAfter3837, blockProcessingCountBefore3837+1, "Block Processing count should increase");
        //Check Item Status
        String itemsString = Arrays.toString(items).replace("[", "").replace("]", "").replace(" ", "");
        ItemResponse itemSearchResponse = wmsServiceHelper.searchItemsByID("?q=id.in:"+itemsString);
        List<ItemEntry> itemsSearchEntries = itemSearchResponse.getData();
        for (ItemEntry itemEntry: itemsSearchEntries) {
        	sft.assertEquals(itemEntry.getItemStatus(), ItemStatus.RETURN_FROM_OPS,"Verify item status");
        }
        sft.assertAll();
    }
    
    //Passed
    @Test(enabled=true, description="Cancel Order Release which warehouse is assigned", groups={"regression","sanity","cancellation"})
    public void cancelReleaseForWhichWHIsAssigned() throws Exception {
    	sft = new SoftAssert();
    	atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OMSReleaseCancellation.cancelReleaseForWhichWHIsAssigned_ITEM1+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":1000:0:"+vectorSellerID+":1"}, supplyTypeOnHand);
		imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OMSReleaseCancellation.cancelReleaseForWhichWHIsAssigned_ITEM1+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":1000:0:"+vectorSellerID+":1"}, supplyTypeOnHand);
        
		String orderID =  end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, new String[] {OMSTCConstants.OMSReleaseCancellation.cancelReleaseForWhichWHIsAssigned_ITEM1+":1"}, "", false, false, false, "",false);
        int iocBocBefore = atpServiceHelper.getAtpInvAndBlockOrderCount(new String[] { OMSTCConstants.OMSReleaseCancellation.cancelReleaseForWhichWHIsAssigned_ITEM1 }).get(OMSTCConstants.OMSReleaseCancellation.cancelReleaseForWhichWHIsAssigned_ITEM1)[1];
        omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
        
        orderEntry = omsServiceHelper.getOrderEntry(orderID);
        orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();
        
        OrderReleaseEntry orderReleaseEntry = omsServiceHelper.getOrderReleaseEntry(orderReleaseId);
        sft.assertEquals(orderReleaseEntry.getStatus(), "WP","Verify release is in WP status");
        cancelReleaseSuccess(orderReleaseId, login, "1", "TestOrder Cancellation");
        
        End2EndHelper.sleep(2*delayedTime);
        int iocBocAfter = atpServiceHelper.getAtpInvAndBlockOrderCount(new String[]{OMSTCConstants.OMSReleaseCancellation.cancelReleaseForWhichWHIsAssigned_ITEM1}).get(OMSTCConstants.OMSReleaseCancellation.cancelReleaseForWhichWHIsAssigned_ITEM1)[1];
        sft.assertEquals(iocBocAfter, iocBocBefore-1, "Inventory Count Should be Reduced after Order Cancellation ");
        sft.assertAll();
    }
    
    /**
     * @param releaseId
     * @param status
     * @return
     */
    public Boolean isReleaseStatusSameInCaptureOrderRelease(String releaseId, String status){
    	HashMap<String,Object> captureOrderReleaseData = (HashMap<String, Object>) wmsServiceHelper.getCaptureOrderReleaseEntry(releaseId);
    	if(captureOrderReleaseData.get("order_release_status").toString().equalsIgnoreCase(status)){
    		return true;
    	}
    	
    	return false;
    }

    
    //***********************************************Order Cancellation********************************************************
    
    //Passed
	@Test(enabled = true, description = "Cancel Order for which WH is not assigned", groups = {"regression","sanity","cancellation"})
	public void cancelOrderForWhichWHIsNotAssigned() throws Exception {
		sft = new SoftAssert();
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OMSOrderCancellationServiceTest.cancelOrderForWhichWHIsNotAssigned_ITEM1+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:"+vectorSellerID+":1"},supplyTypeOnHand);
        imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OMSOrderCancellationServiceTest.cancelOrderForWhichWHIsNotAssigned_ITEM1+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":0:0:"+vectorSellerID},supplyTypeOnHand);
        String orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_123456, new String[] { OMSTCConstants.OMSOrderCancellationServiceTest.cancelOrderForWhichWHIsNotAssigned_ITEM1+":1" }, "", false,
				false, false, "", false);
        omsServiceHelper.checkReleaseStatusForOrder(orderID,"Q");
        waitForOrderLevelOnHoldToResolve(orderID);
        
        orderEntry = omsServiceHelper.getOrderEntry(orderID);
        orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();
        
		int iocBocBefore = atpServiceHelper.getAtpInvAndBlockOrderCount(new String[] { OMSTCConstants.OMSOrderCancellationServiceTest.cancelOrderForWhichWHIsNotAssigned_ITEM1 }).get(OMSTCConstants.OMSOrderCancellationServiceTest.cancelOrderForWhichWHIsNotAssigned_ITEM1)[1];
		OrderReleaseEntry orderReleaseEntry = omsServiceHelper.getOrderReleaseEntry(orderReleaseId);
		sft.assertEquals(orderReleaseEntry.getStatus(), "Q","Verify release is in Q status");
		
		cancelOrderSuccess(orderID, login, "1", "TestOrder Cancellation");
		end2EndHelper.sleep(2*delayedTime);
		
		int iocBocAfter = atpServiceHelper.getAtpInvAndBlockOrderCount(new String[] { OMSTCConstants.OMSOrderCancellationServiceTest.cancelOrderForWhichWHIsNotAssigned_ITEM1 }).get(OMSTCConstants.OMSOrderCancellationServiceTest.cancelOrderForWhichWHIsNotAssigned_ITEM1)[1];
		sft.assertEquals(iocBocAfter, iocBocBefore-1, "Inventory Count Should be Reduced after Order Cancellation ");
		sft.assertAll();

	}
	
	//Passed
	@Test(enabled = true, description = "Cancel COD Order With Single Item and Single Quantity", groups = {"regression","sanity","cancellation"}, priority = 0)
	public void cancelOrderWithSingleItemCOD() throws Exception {
		sft = new SoftAssert();
        String skuId[] = { OMSTCConstants.OMSOrderCancellationServiceTest.cancelOrderWithSingleItemCOD_ITEM1+":1" };
        
        atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OMSOrderCancellationServiceTest.cancelOrderWithSingleItemCOD_ITEM1+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":10000:0:"+vectorSellerID+":1"},supplyTypeOnHand);
        imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OMSOrderCancellationServiceTest.cancelOrderWithSingleItemCOD_ITEM1+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":10000:0:"+vectorSellerID},supplyTypeOnHand);
        HashMap<String, int[]> inventoryCountInATPBeforePlacingOrder = atpServiceHelper
                .getAtpInvAndBlockOrderCount(new String[] { OMSTCConstants.OMSOrderCancellationServiceTest.cancelOrderWithSingleItemCOD_ITEM1 });

        HashMap<String, int[]> inventoryCountInIMSBeforePlacingOrder = imsServiceHelper
                .getIMSInvAndBlockOrderCount(new String[] { OMSTCConstants.OMSOrderCancellationServiceTest.cancelOrderWithSingleItemCOD_ITEM1 }, "28", "1", ""+vectorSellerID);

        int[] blockCountATP = inventoryCountInATPBeforePlacingOrder.get(OMSTCConstants.OMSOrderCancellationServiceTest.cancelOrderWithSingleItemCOD_ITEM1);
        int blockCountATPBefore = blockCountATP[1];

        int[] blockCountIMS = inventoryCountInIMSBeforePlacingOrder.get(OMSTCConstants.OMSOrderCancellationServiceTest.cancelOrderWithSingleItemCOD_ITEM1);
        int blockCountIMSBefore = blockCountIMS[1];

        String orderID =  end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false, "", false);
        omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
        orderEntry = omsServiceHelper.getOrderEntry(orderID);
        orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();

        HashMap<String, int[]> inventoryCountInATPAfterPlacingOrder = atpServiceHelper
                .getAtpInvAndBlockOrderCount(new String[] { OMSTCConstants.OMSOrderCancellationServiceTest.cancelOrderWithSingleItemCOD_ITEM1 });

        HashMap<String, int[]> inventoryCountInIMSAfterPlacingOrder = imsServiceHelper
                .getIMSInvAndBlockOrderCount(new String[] { OMSTCConstants.OMSOrderCancellationServiceTest.cancelOrderWithSingleItemCOD_ITEM1 }, "28", "1", ""+vectorSellerID);

        blockCountATP = inventoryCountInATPAfterPlacingOrder.get(OMSTCConstants.OMSOrderCancellationServiceTest.cancelOrderWithSingleItemCOD_ITEM1);
        int blockCountATPAfter = blockCountATP[1] - 1;

        blockCountIMS = inventoryCountInIMSAfterPlacingOrder.get(OMSTCConstants.OMSOrderCancellationServiceTest.cancelOrderWithSingleItemCOD_ITEM1);
        int blockCountIMSAfter = blockCountIMS[1] - 1;

        log.info(
                "ATP Block Inventory Status Before: " + blockCountATPBefore + " After :" + blockCountATPAfter);
        log.info(
                "IMS Block Inventory Status Before: " + blockCountIMSBefore + " After :" + blockCountIMSAfter);

        // VerifyInventory in IMS and ATP After Order Placement
        sft.assertEquals(blockCountATPAfter, blockCountATPBefore, "ATP Count After Order Place");
        sft.assertEquals(blockCountIMSAfter, blockCountIMSBefore, "IMS Count After Order Place");
        
        cancelOrderSuccess(orderID, login, "1", "TestOrder Cancellation");
        End2EndHelper.sleep(5000L);
        
        OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
        log.info(orderEntry.getCancellationPpsId());
        sft.assertNotNull(orderEntry.getCancellationPpsId(), "Order Cancellation PPS ID is not Null");

        // Verify Status in Order Capture System
        sft.assertEquals(wmsServiceHelper.getCaptureOrderReleaseEntry(orderReleaseId).get("order_release_status").toString(), OrderReleaseStatus.CANCELLED.toString(),"Order Release status should be cancelled");

        // Verify Inventory After Cancellation
        HashMap<String, int[]> inventoryCountInATPAfterCancellation = atpServiceHelper
                .getAtpInvAndBlockOrderCount(new String[] { OMSTCConstants.OMSOrderCancellationServiceTest.cancelOrderWithSingleItemCOD_ITEM1 });
        HashMap<String, int[]> inventoryCountInIMSAfterCancellation = imsServiceHelper
                .getIMSInvAndBlockOrderCount(new String[] { OMSTCConstants.OMSOrderCancellationServiceTest.cancelOrderWithSingleItemCOD_ITEM1 }, "28", "1", ""+vectorSellerID);

        blockCountATP = inventoryCountInATPAfterCancellation.get(OMSTCConstants.OMSOrderCancellationServiceTest.cancelOrderWithSingleItemCOD_ITEM1);
        int blockCountATPAfterCancellation = blockCountATP[1];

        blockCountIMS = inventoryCountInIMSAfterCancellation.get(OMSTCConstants.OMSOrderCancellationServiceTest.cancelOrderWithSingleItemCOD_ITEM1);
        int blockCountIMSAfterCancellation = blockCountIMS[1];

        sft.assertEquals(blockCountATPBefore, blockCountATPAfterCancellation,
                            "ATP Count After Order Cancellation");
        sft.assertEquals(blockCountIMSBefore, blockCountIMSAfterCancellation,
                            "IMS Count After Order Cancellation");
        sft.assertAll();
	}

	@Test(enabled = true, description = "Replacement and Goodwill coupon should be generated On CCR Cancellation", groups = {"regression","sanity","cancellation"})
	public void checkReplacementAndGoodWillCouponGenerationOnOrderCancellation() throws Exception {
		sft = new SoftAssert();
		String skuId[] = { OMSTCConstants.OtherSkus.skuId_3832+":1" };
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3832+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+","+OMSTCConstants.WareHouseIds.warehouseId36_BW+":10000:0:"+vectorSellerID+":1"},supplyTypeOnHand);
        imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3832+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+","+OMSTCConstants.WareHouseIds.warehouseId36_BW+":10000:0:"+vectorSellerID},supplyTypeOnHand);
		
        String orderID;

		DBUtilities.exUpdateQuery(
				"Delete from xcart_discount_coupons where coupon not in ('pati1','pati2','pati3') and users='" + uidx
						+ "';",
				"myntra");
		orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "pati1", false, false, false, "", false);
        omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
        orderEntry = omsServiceHelper.getOrderEntry(orderID);
        orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();

		OrderResponse cancellationRes = omsServiceHelper.cancelOrder(orderID, "1", "erpadmin",
				"TestOrder Cancellation");
		sft.assertEquals(cancellationRes.getStatus().getStatusCode(), 1002,"Verify status code after cancel");
		sft.assertEquals(cancellationRes.getStatus().getStatusType(), Type.SUCCESS, "Verify Response Status Type");
		sft.assertEquals(cancellationRes.getData().get(0).getOrderReleases().get(0).getStatus(), "F",
				"Order Status should be F");
		omsServiceHelper.checkReleaseStatusForOrder(orderID,"F");
		End2EndHelper.sleep(6000L);
		List coupon = DBUtilities.exSelectQuery(
				"Select * from xcart_discount_coupons where coupon like 'CAN%' and MRPpercentage=20 and MRPAmount=159.80 and users='"
						+ uidx + "';",
				"myntra");
		List coupon1 = DBUtilities.exSelectQuery(
				"Select * from xcart_discount_coupons where coupon like 'GW%' and users='" + uidx + "';", "myntra");
		sft.assertEquals(coupon.size(), 1, "Cancellation Replacement coupon is not generated");
		sft.assertEquals(coupon1.size(), 1, "Cancellation GoodWill coupon is not generated");
		sft.assertAll();
	}

    @Test(enabled = true, description = "Cancel Order After Item Association",groups = {"regression","sanity","cancellation"})
    public void cancelOrderAfterItemAssociation() throws Exception {
    	sft = new SoftAssert();
        String skuId[] = { OMSTCConstants.OMSOrderCancellationServiceTest.cancelOrderAfterItemAssociation_ITEM1+":1",
        		OMSTCConstants.OMSOrderCancellationServiceTest.cancelOrderAfterItemAssociation_ITEM2+":1" };
        atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OMSOrderCancellationServiceTest.cancelOrderAfterItemAssociation_ITEM1+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":10000:0:"+vectorSellerID+":1",
        		OMSTCConstants.OMSOrderCancellationServiceTest.cancelOrderAfterItemAssociation_ITEM2+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":10000:0:"+vectorSellerID+":1"},supplyTypeOnHand);
        imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OMSOrderCancellationServiceTest.cancelOrderAfterItemAssociation_ITEM1+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":10000:0:"+vectorSellerID,
        		OMSTCConstants.OMSOrderCancellationServiceTest.cancelOrderAfterItemAssociation_ITEM2+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":10000:0:"+vectorSellerID},supplyTypeOnHand);
        
        String orderID =  end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false,"", false);
        omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
        orderEntry = omsServiceHelper.getOrderEntry(orderID);
        orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();
        
        OrderReleaseEntry orderReleaseEntry = omsServiceHelper.getOrderReleaseEntry(orderReleaseId);
        String wareHouseID = ""+orderReleaseEntry.getWarehouseId();

        wmsServiceHelper.pushOrderToWave(orderReleaseId);
        if (!wmsServiceHelper.validateOrderInCoreOrderRelease(orderReleaseId, 15)) {
        	sft.assertTrue(false, "Order not pushed to Order wave, Please check WMS/Worms log");
        }
        wmsServiceHelper.createOrderItemAssociation(orderReleaseId);

        HashMap<String, int[]> inventoryCountInATPBeforePlacingOrder = atpServiceHelper
                .getAtpInvAndBlockOrderCount(new String[] { OMSTCConstants.OMSOrderCancellationServiceTest.cancelOrderAfterItemAssociation_ITEM1, OMSTCConstants.OMSOrderCancellationServiceTest.cancelOrderAfterItemAssociation_ITEM2});

        HashMap<String, int[]> inventoryCountInIMSBeforePlacingOrder = imsServiceHelper
                .getIMSInvAndBlockOrderCount(new String[] { OMSTCConstants.OMSOrderCancellationServiceTest.cancelOrderAfterItemAssociation_ITEM1, OMSTCConstants.OMSOrderCancellationServiceTest.cancelOrderAfterItemAssociation_ITEM2 }, wareHouseID, "1", ""+vectorSellerID);

        int[] blockCountATP3832 = inventoryCountInATPBeforePlacingOrder.get(OMSTCConstants.OMSOrderCancellationServiceTest.cancelOrderAfterItemAssociation_ITEM1);
        int blockCountATPBefore3832 = blockCountATP3832[1];
        int inventoryCountATPBefore3832 = blockCountATP3832[0];

        int[] blockCountIMS3832 = inventoryCountInIMSBeforePlacingOrder.get(OMSTCConstants.OMSOrderCancellationServiceTest.cancelOrderAfterItemAssociation_ITEM1);
        int blockCountIMSBefore3832 = blockCountIMS3832[1];
        int inventoryCountIMSBefore3832 = blockCountIMS3832[0];


        int[] blockCountATP3837 = inventoryCountInATPBeforePlacingOrder.get(OMSTCConstants.OMSOrderCancellationServiceTest.cancelOrderAfterItemAssociation_ITEM2);
        int blockCountATPBefore3837 = blockCountATP3837[1];
        int inventoryCountATPBefore3837 = blockCountATP3837[0];

        int[] blockCountIMS3837 = inventoryCountInIMSBeforePlacingOrder.get(OMSTCConstants.OMSOrderCancellationServiceTest.cancelOrderAfterItemAssociation_ITEM2);
        int blockCountIMSBefore3837 = blockCountIMS3837[1];
        int inventoryCountIMSBefore3837 = blockCountIMS3837[0];

        HashMap<String, HashMap> blockProcessingCounts = imsServiceHelper.getIMSCoreInvDataForSku(new String[] {OMSTCConstants.OMSOrderCancellationServiceTest.cancelOrderAfterItemAssociation_ITEM1+":"+wareHouseID, OMSTCConstants.OMSOrderCancellationServiceTest.cancelOrderAfterItemAssociation_ITEM2+":"+wareHouseID});
        int blockProcessingCountBefore3832 = (int) blockProcessingCounts.get(OMSTCConstants.OMSOrderCancellationServiceTest.cancelOrderAfterItemAssociation_ITEM1).get("blocked_processing_count");
        int blockProcessingCountBefore3837 = (int) blockProcessingCounts.get(OMSTCConstants.OMSOrderCancellationServiceTest.cancelOrderAfterItemAssociation_ITEM2).get("blocked_processing_count");

        String[] items = wmsServiceHelper.getItemsAssociatedWithOrder(orderReleaseId);

        OrderResponse cancellationRes = omsServiceHelper.cancelOrder(orderID, "1", login, "TestOrder Cancellation");
        sft.assertEquals(cancellationRes.getStatus().getStatusCode(), 1002, "Verify Response Status Code ");
        sft.assertEquals(cancellationRes.getStatus().getStatusType(), Type.SUCCESS, "Verify Response Status Type ");
        sft.assertEquals(cancellationRes.getData().get(0).getOrderReleases().get(0).getStatus(), "F", "Verify Order Release Status is F");
        End2EndHelper.sleep(25000L);
        HashMap<String, int[]> inventoryCountInATPAfterePlacingOrder = atpServiceHelper
                .getAtpInvAndBlockOrderCount(new String[] { OMSTCConstants.OMSOrderCancellationServiceTest.cancelOrderAfterItemAssociation_ITEM1, OMSTCConstants.OMSOrderCancellationServiceTest.cancelOrderAfterItemAssociation_ITEM2});

        HashMap<String, int[]> inventoryCountInIMSAfterPlacingOrder = imsServiceHelper
                .getIMSInvAndBlockOrderCount(new String[] { OMSTCConstants.OMSOrderCancellationServiceTest.cancelOrderAfterItemAssociation_ITEM1, OMSTCConstants.OMSOrderCancellationServiceTest.cancelOrderAfterItemAssociation_ITEM2 }, wareHouseID, "1", ""+vectorSellerID);

        blockCountATP3832 = inventoryCountInATPAfterePlacingOrder.get(OMSTCConstants.OMSOrderCancellationServiceTest.cancelOrderAfterItemAssociation_ITEM1);
        int blockCountATPAfter3832 = blockCountATP3832[1] + 1;
        int inventoryCountATPAfter3832 = blockCountATP3832[0] + 1;

        blockCountIMS3832 = inventoryCountInIMSAfterPlacingOrder.get(OMSTCConstants.OMSOrderCancellationServiceTest.cancelOrderAfterItemAssociation_ITEM1);
        int blockCountIMSAfter3832 = blockCountIMS3832[1] + 1;
        int inventoryCountIMSAfter3832 = blockCountIMS3832[0] + 1;


        blockCountATP3837 = inventoryCountInATPAfterePlacingOrder.get(OMSTCConstants.OMSOrderCancellationServiceTest.cancelOrderAfterItemAssociation_ITEM2);
        int blockCountATPAfter3837 = blockCountATP3837[1] + 1;
        int inventoryCountATPAfter3837 = blockCountATP3837[0] + 1;

        blockCountIMS3837 = inventoryCountInIMSAfterPlacingOrder.get(OMSTCConstants.OMSOrderCancellationServiceTest.cancelOrderAfterItemAssociation_ITEM2);
        int blockCountIMSAfter3837 = blockCountIMS3837[1] + 1;
        int inventoryCountIMSAfter3837 = blockCountIMS3837[0] + 1;

        log.info(
                "ATP Block Inventory Status Before: " + blockCountATPBefore3832 + " After :" + blockCountATPAfter3832);
        log.info(
                "ATP Block Inventory Status Before: " + blockCountATPBefore3837 + " After :" + blockCountATPAfter3837);
        log.info(
                "ATP Inventory Count Status Before: " + inventoryCountATPBefore3832 + " After :" + inventoryCountATPAfter3832);

        log.info(
                "ATP Inventory Count Status Before: " + inventoryCountATPBefore3837 + " After :" + inventoryCountATPAfter3837);

        log.info(
                "IMS Block Inventory Status Before: " + blockCountIMSBefore3832 + " After :" + blockCountIMSAfter3832);
        log.info(
                "IMS Block Inventory Status Before: " + blockCountIMSBefore3837 + " After :" + blockCountIMSAfter3837);

        // Check Inventory Status
        sft.assertEquals(blockCountATPBefore3832, blockCountATPAfter3832,
                            "ATP Block Order Count After Order Cancellation For 3832");

        sft.assertEquals(blockCountATPBefore3837, blockCountATPAfter3837,
                            "ATP Block Order Count After Order Cancellation For 3837" );

        sft.assertEquals(inventoryCountATPBefore3832, inventoryCountATPAfter3832,
                            "ATP Inventory Count After Order Cancellation For 3832" );

        sft.assertEquals(inventoryCountATPBefore3837, inventoryCountATPAfter3837,
                            "ATP Inventory Count After Order Cancellation For 3837" );

        sft.assertEquals(blockCountATPBefore3832, blockCountATPAfter3832,
                            "ATP Block Order Count After Order Cancellation For 3832");

        sft.assertEquals(blockCountATPBefore3837, blockCountATPAfter3837,
                            "ATP Block Order Count After Order Cancellation For 3837" );

        sft.assertEquals(inventoryCountATPBefore3832, inventoryCountATPAfter3832,
                            "ATP Inventory Count After Order Cancellation For 3832" );

        sft.assertEquals(inventoryCountATPBefore3837, inventoryCountATPAfter3837,
                            "ATP Inventory Count After Order Cancellation For 3837" );

        sft.assertEquals(blockCountIMSBefore3832, blockCountIMSAfter3832,
                            "ATP Block Order Count After Order Cancellation For 3832");

        sft.assertEquals(blockCountIMSBefore3837, blockCountIMSAfter3837,
                            "ATP Block Order Count After Order Cancellation For 3837" );

        sft.assertEquals(inventoryCountIMSBefore3832, inventoryCountIMSAfter3832,
                            "ATP Inventory Count After Order Cancellation For 3832" );

        sft.assertEquals(inventoryCountIMSBefore3837, inventoryCountIMSAfter3837,
                            "ATP Inventory Count After Order Cancellation For 3837" );

        sft.assertEquals(blockCountIMSBefore3832, blockCountIMSAfter3832,
                            "ATP Block Order Count After Order Cancellation For 3832");

        sft.assertEquals(blockCountIMSBefore3837, blockCountIMSAfter3837,
                            "ATP Block Order Count After Order Cancellation For 3837" );

        sft.assertEquals(inventoryCountIMSBefore3832, inventoryCountIMSAfter3832,
                            "ATP Inventory Count After Order Cancellation For 3832" );

        sft.assertEquals(inventoryCountIMSBefore3837, inventoryCountIMSAfter3837,
                            "ATP Inventory Count After Order Cancellation For 3837" );

        blockProcessingCounts = imsServiceHelper.getIMSCoreInvDataForSku(new String[] {OMSTCConstants.OMSOrderCancellationServiceTest.cancelOrderAfterItemAssociation_ITEM1+":"+wareHouseID, OMSTCConstants.OMSOrderCancellationServiceTest.cancelOrderAfterItemAssociation_ITEM2+":"+wareHouseID});
        int blockProcessingCountAfter3832 = (int) blockProcessingCounts.get(OMSTCConstants.OMSOrderCancellationServiceTest.cancelOrderAfterItemAssociation_ITEM1).get("blocked_processing_count");
        int blockProcessingCountAfter3837 = (int) blockProcessingCounts.get(OMSTCConstants.OMSOrderCancellationServiceTest.cancelOrderAfterItemAssociation_ITEM2).get("blocked_processing_count");
        sft.assertEquals(blockProcessingCountAfter3832, blockProcessingCountBefore3832+1, "Block Processing count should increase");
        sft.assertEquals(blockProcessingCountAfter3837, blockProcessingCountBefore3837+1, "Block Processing count should increase");
        //Check Item Status
        String itemsString = Arrays.toString(items).replace("[","").replace("]","").replace(" ","");
        ItemResponse itemSearchResponse = wmsServiceHelper.searchItemsByID("?q=id.in:"+itemsString);
        List<ItemEntry> itemsSearchEntries = itemSearchResponse.getData();
        for (ItemEntry itemEntry: itemsSearchEntries) {
        	sft.assertEquals(itemEntry.getItemStatus(), ItemStatus.RETURN_FROM_OPS);
        }
        sft.assertAll();
    }

    @Test(description = "Cancel quantity and then Cancel Order",groups = {"regression","sanity","cancellation"})
    public void cancelOrderFollowedByItemCancellation() throws Exception {
    	sft = new SoftAssert();
        String skuId[] = { OMSTCConstants.OMSOrderCancellationServiceTest.cancelOrderFollowedByItemCancellation_ITEM1+":5" };
        
        atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OMSOrderCancellationServiceTest.cancelOrderFollowedByItemCancellation_ITEM1+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":10000:0:"+vectorSellerID+":1"},supplyTypeOnHand);
        imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OMSOrderCancellationServiceTest.cancelOrderFollowedByItemCancellation_ITEM1+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":10000:0:"+vectorSellerID},supplyTypeOnHand);
        String orderID =  end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false,"", false);
        omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
        orderEntry = omsServiceHelper.getOrderEntry(orderID);
        orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();
        
        HashMap<String, HashMap<String, Object>> blockProcessingCounts = imsServiceHelper.getIMSInventoryDetails(new String[] {OMSTCConstants.OMSOrderCancellationServiceTest.cancelOrderFollowedByItemCancellation_ITEM1+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":1:"+vectorSellerID});
        int blockOrderCount3833AfterPlacingOrder = (int) blockProcessingCounts.get(OMSTCConstants.OMSOrderCancellationServiceTest.cancelOrderFollowedByItemCancellation_ITEM1).get("blocked_order_count");
        int inventoryCounty3833AfterPlacingOrder = (int) blockProcessingCounts.get(OMSTCConstants.OMSOrderCancellationServiceTest.cancelOrderFollowedByItemCancellation_ITEM1).get("inventory_count");
        
        OrderReleaseEntry orderReleaseEntry = omsServiceHelper.getOrderReleaseEntry(orderReleaseId);
        OrderLineEntry orderLineEntry = orderReleaseEntry.getOrderLines().get(0);
        long lineID = orderLineEntry.getId();
        cancelLineSuccess(orderReleaseId, login, ""+lineID, 1, 39);
        End2EndHelper.sleep(delayedTime);
        
        blockProcessingCounts = imsServiceHelper.getIMSInventoryDetails(new String[] {OMSTCConstants.OMSOrderCancellationServiceTest.cancelOrderFollowedByItemCancellation_ITEM1+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":1:"+vectorSellerID});
        int blockOrderCount3833 = (int) blockProcessingCounts.get(OMSTCConstants.OMSOrderCancellationServiceTest.cancelOrderFollowedByItemCancellation_ITEM1).get("blocked_order_count");
        int inventoryCounty3833 = (int) blockProcessingCounts.get(OMSTCConstants.OMSOrderCancellationServiceTest.cancelOrderFollowedByItemCancellation_ITEM1).get("inventory_count");
        
        sft.assertEquals(blockOrderCount3833, blockOrderCount3833AfterPlacingOrder-1, "Block Inventory Count after Line Cancellation ");
        sft.assertEquals(inventoryCounty3833, inventoryCounty3833AfterPlacingOrder, "Inventory count after Line cancellation ");
        
        OrderResponse orderCancellationRes = omsServiceHelper.cancelOrder(orderID, "1", login, "TestOrder Cancellation");
        sft.assertEquals(orderCancellationRes.getStatus().getStatusType(), Type.SUCCESS,"Verify response after cancel");

        End2EndHelper.sleep(delayedTime);

        blockProcessingCounts = imsServiceHelper.getIMSInventoryDetails(new String[] {OMSTCConstants.OMSOrderCancellationServiceTest.cancelOrderFollowedByItemCancellation_ITEM1+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":1:"+vectorSellerID});
        int blockOrderCountAfter3833 = (int) blockProcessingCounts.get(OMSTCConstants.OMSOrderCancellationServiceTest.cancelOrderFollowedByItemCancellation_ITEM1).get("blocked_order_count");
        int inventoryCountyAfter3833 = (int) blockProcessingCounts.get(OMSTCConstants.OMSOrderCancellationServiceTest.cancelOrderFollowedByItemCancellation_ITEM1).get("inventory_count");
        sft.assertEquals(blockOrderCount3833-4, blockOrderCountAfter3833, "Block Inventory Count after Order Cancellation ");
        sft.assertEquals(inventoryCounty3833, inventoryCountyAfter3833, "Inventory count after Order cancellation ");
        sft.assertAll();
    }

    @Test(enabled = true, description = "Cancel COD Order With Multiple Item and Single Quantity", groups = {"regression","sanity","cancellation"})
	public void cancelOrderWithMultpleItemCOD() throws Exception {
    	sft = new SoftAssert();
    	String skuId[] = { OMSTCConstants.OMSOrderCancellationServiceTest.cancelOrderWithMultpleItemCOD_ITEM1+":1", OMSTCConstants.OMSOrderCancellationServiceTest.cancelOrderWithMultpleItemCOD_ITEM2+":1" };
        atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OMSOrderCancellationServiceTest.cancelOrderWithMultpleItemCOD_ITEM1+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":10000:0:"+vectorSellerID+":1",
        		OMSTCConstants.OMSOrderCancellationServiceTest.cancelOrderWithMultpleItemCOD_ITEM2+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":10000:0:"+vectorSellerID+":1"},supplyTypeOnHand);
        imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OMSOrderCancellationServiceTest.cancelOrderWithMultpleItemCOD_ITEM1+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":10000:0:"+vectorSellerID,
        		OMSTCConstants.OMSOrderCancellationServiceTest.cancelOrderWithMultpleItemCOD_ITEM2+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":10000:0:"+vectorSellerID},supplyTypeOnHand);
        
        HashMap<String, int[]> inventoryCountInATPBeforePlacingOrder = atpServiceHelper
                .getAtpInvAndBlockOrderCount(new String[] { OMSTCConstants.OMSOrderCancellationServiceTest.cancelOrderWithMultpleItemCOD_ITEM1 });

        HashMap<String, int[]> inventoryCountInATPBeforePlacingOrder3836 = atpServiceHelper
                .getAtpInvAndBlockOrderCount(new String[] { OMSTCConstants.OMSOrderCancellationServiceTest.cancelOrderWithMultpleItemCOD_ITEM2 });
        
        HashMap<String, int[]> inventoryCountInIMSBeforePlacingOrder = imsServiceHelper
                .getIMSInvAndBlockOrderCount(new String[] { OMSTCConstants.OMSOrderCancellationServiceTest.cancelOrderWithMultpleItemCOD_ITEM1 }, "28", "1", ""+vectorSellerID);
        
        HashMap<String, int[]> inventoryCountInIMSBeforePlacingOrder3836 = imsServiceHelper
                .getIMSInvAndBlockOrderCount(new String[] { OMSTCConstants.OMSOrderCancellationServiceTest.cancelOrderWithMultpleItemCOD_ITEM2 }, "28", "1", ""+vectorSellerID);


        int[] blockCountATP = inventoryCountInATPBeforePlacingOrder.get(OMSTCConstants.OMSOrderCancellationServiceTest.cancelOrderWithMultpleItemCOD_ITEM1);
        int blockCountATPBefore = blockCountATP[1];

        int[] blockCountATP3836 = inventoryCountInATPBeforePlacingOrder3836.get(OMSTCConstants.OMSOrderCancellationServiceTest.cancelOrderWithMultpleItemCOD_ITEM2);
        int blockCountATPBefore3836 = blockCountATP3836[1];
        
        int[] blockCountIMS = inventoryCountInIMSBeforePlacingOrder.get(OMSTCConstants.OMSOrderCancellationServiceTest.cancelOrderWithMultpleItemCOD_ITEM1);
        int blockCountIMSBefore = blockCountIMS[1];
        
        int[] blockCountIMS3836 = inventoryCountInIMSBeforePlacingOrder3836.get(OMSTCConstants.OMSOrderCancellationServiceTest.cancelOrderWithMultpleItemCOD_ITEM2);
        int blockCountIMSBefore3836 = blockCountIMS[1];

        String orderID =  end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false, "", false);
        omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
        orderEntry = omsServiceHelper.getOrderEntry(orderID);
        orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();
        OrderReleaseEntry orderReleaseEntry = omsServiceHelper.getOrderReleaseEntry(orderReleaseId);
        String warehouseId = ""+orderReleaseEntry.getWarehouseId();

        HashMap<String, int[]> inventoryCountInATPAfterPlacingOrder = atpServiceHelper
                .getAtpInvAndBlockOrderCount(new String[] { OMSTCConstants.OMSOrderCancellationServiceTest.cancelOrderWithMultpleItemCOD_ITEM1 });
        

        HashMap<String, int[]> inventoryCountInATPAfterPlacingOrder3836 = atpServiceHelper
                .getAtpInvAndBlockOrderCount(new String[] { OMSTCConstants.OMSOrderCancellationServiceTest.cancelOrderWithMultpleItemCOD_ITEM2 });

        HashMap<String, int[]> inventoryCountInIMSAfterPlacingOrder = imsServiceHelper
                .getIMSInvAndBlockOrderCount(new String[] { OMSTCConstants.OMSOrderCancellationServiceTest.cancelOrderWithMultpleItemCOD_ITEM1 }, warehouseId, "1", ""+vectorSellerID);
        
        HashMap<String, int[]> inventoryCountInIMSAfterPlacingOrder3836 = imsServiceHelper
                .getIMSInvAndBlockOrderCount(new String[] { OMSTCConstants.OMSOrderCancellationServiceTest.cancelOrderWithMultpleItemCOD_ITEM2 }, warehouseId, "1", ""+vectorSellerID);

        blockCountATP = inventoryCountInATPAfterPlacingOrder.get(OMSTCConstants.OMSOrderCancellationServiceTest.cancelOrderWithMultpleItemCOD_ITEM1);
        int blockCountATPAfter = blockCountATP[1] - 1;

        
        blockCountATP3836 = inventoryCountInATPAfterPlacingOrder3836.get(OMSTCConstants.OMSOrderCancellationServiceTest.cancelOrderWithMultpleItemCOD_ITEM2);
        int blockCountATPAfter3836 = blockCountATP3836[1] - 1;
        
        blockCountIMS = inventoryCountInIMSAfterPlacingOrder.get(OMSTCConstants.OMSOrderCancellationServiceTest.cancelOrderWithMultpleItemCOD_ITEM1);
        int blockCountIMSAfter = blockCountIMS[1]-1;
        
        blockCountIMS3836 = inventoryCountInIMSAfterPlacingOrder3836.get(OMSTCConstants.OMSOrderCancellationServiceTest.cancelOrderWithMultpleItemCOD_ITEM2);
        int blockCountIMSAfter3836 = blockCountIMS3836[1] - 1;

        log.info(
                "ATP Block Inventory Status Before: " + blockCountATPBefore + " After :" + blockCountATPAfter);
        
        log.info(
                "ATP Block Inventory Status Before: " + blockCountATPBefore3836 + " After :" + blockCountATPAfter3836);

        
        log.info(
                "IMS Block Inventory Status Before: " + blockCountIMSBefore + " After :" + blockCountIMSAfter);

        log.info(
                "IMS Block Inventory Status Before: " + blockCountIMSBefore3836 + " After :" + blockCountIMSAfter3836);


        // VerifyInventory in IMS and ATP After Order Placement
        sft.assertEquals(blockCountATPAfter, blockCountATPBefore, "ATP Count After Order Place");
        sft.assertEquals(blockCountATPAfter3836, blockCountATPBefore3836, "ATP Count After Order Place");
        sft.assertEquals(blockCountIMSAfter, blockCountIMSBefore, "IMS Count After Order Place");
        sft.assertEquals(blockCountIMSAfter3836, blockCountIMSBefore3836, "IMS Count After Order Place");

        OrderResponse cancellationRes = omsServiceHelper.cancelOrder(orderID, "1", login, "TestOrder Cancellation");
        sft.assertEquals(cancellationRes.getStatus().getStatusCode(), 1002,"verify status code after cancel");
        sft.assertEquals(cancellationRes.getStatus().getStatusType(), Type.SUCCESS,"verify response message after cancel");
        sft.assertEquals(cancellationRes.getData().get(0).getOrderReleases().get(0).getStatus(), "F","Verify order status after cancel");
        sft.assertTrue(omsServiceHelper.validateReleaseStatusInOMS(orderReleaseId, "F", 5),"Release is not in F status");

        End2EndHelper.sleep(6000L);
        OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
        log.info(APIUtilities.convertXMLObjectToString(orderEntry));
        log.info(orderEntry.getCancellationPpsId());
        sft.assertNotNull(orderEntry.getCancellationPpsId(), "Order Cancellation PPS ID is not Null");

        // Verify Status in Order Capture System
        HashMap<String,String> orderCaptureReleaseEntry = (HashMap<String, String>) wmsServiceHelper.getCaptureOrderReleaseEntry(orderReleaseId);
        sft.assertEquals(""+orderCaptureReleaseEntry.get("order_release_status"), OrderReleaseStatus.CANCELLED.toString(),"Verify order release in worms after cancel");

        // Verify Inventory After Cancellation
        HashMap<String, int[]> inventoryCountInATPAfterCancellation = atpServiceHelper
                .getAtpInvAndBlockOrderCount(new String[] { OMSTCConstants.OMSOrderCancellationServiceTest.cancelOrderWithMultpleItemCOD_ITEM1 });
        
        // Verify Inventory After Cancellation
        HashMap<String, int[]> inventoryCountInATPAfterCancellation3836 = atpServiceHelper
                .getAtpInvAndBlockOrderCount(new String[] { OMSTCConstants.OMSOrderCancellationServiceTest.cancelOrderWithMultpleItemCOD_ITEM2 });
        HashMap<String, int[]> inventoryCountInIMSAfterCancellation = imsServiceHelper
                .getIMSInvAndBlockOrderCount(new String[] { OMSTCConstants.OMSOrderCancellationServiceTest.cancelOrderWithMultpleItemCOD_ITEM1 }, warehouseId, "1", ""+vectorSellerID);
        HashMap<String, int[]> inventoryCountInIMSAfterCancellation3836 = imsServiceHelper
                .getIMSInvAndBlockOrderCount(new String[] { OMSTCConstants.OMSOrderCancellationServiceTest.cancelOrderWithMultpleItemCOD_ITEM2 }, warehouseId, "1", ""+vectorSellerID);


        blockCountATP = inventoryCountInATPAfterCancellation.get(OMSTCConstants.OMSOrderCancellationServiceTest.cancelOrderWithMultpleItemCOD_ITEM1);
        int blockCountATPAfterCancellation = blockCountATP[1];
        
        blockCountATP3836 = inventoryCountInATPAfterCancellation3836.get(OMSTCConstants.OMSOrderCancellationServiceTest.cancelOrderWithMultpleItemCOD_ITEM2);
        int blockCountATPAfterCancellation3836 = blockCountATP3836[1];

        blockCountIMS = inventoryCountInIMSAfterCancellation.get(OMSTCConstants.OMSOrderCancellationServiceTest.cancelOrderWithMultpleItemCOD_ITEM1);
        int blockCountIMSAfterCancellation = blockCountIMS[1];

        blockCountIMS3836 = inventoryCountInIMSAfterCancellation3836.get(OMSTCConstants.OMSOrderCancellationServiceTest.cancelOrderWithMultpleItemCOD_ITEM2);
        int blockCountIMSAfterCancellation3836 = blockCountIMS3836[1];
        
        sft.assertEquals(blockCountATPBefore, blockCountATPAfterCancellation,
                            "ATP Count After Order Cancellation");
        sft.assertEquals(blockCountIMSBefore, blockCountIMSAfterCancellation,
                            "IMS Count After Order Cancellation");
        sft.assertEquals(blockCountATPBefore3836, blockCountATPAfterCancellation3836,
                "ATP Count After Order Cancellation");
        sft.assertEquals(blockCountIMSBefore3836, blockCountIMSAfterCancellation3836,
                "IMS Count After Order Cancellation");
        sft.assertAll();
	}
    
    @Test(enabled=true, description="Cancel Order Release which do not have warehouse assigned", groups={"regression","sanity","cancellation"})
    public void verifyDoubleRefundFailureForRTO_LOST() throws Exception {
    	sft = new SoftAssert();
    	String[] skuId = new String[] {OMSTCConstants.OtherSkus.skuId_3831 +":1"};
        atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3831 +":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":1000:0:"+vectorSellerID+":1"}, supplyTypeOnHand);
        imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3831 +":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":1000:0:"+vectorSellerID}, supplyTypeOnHand);
        String orderID =  end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false, "",false);
        omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
        //end2EndHelper.markOrderDelivered(orderID, "LOST", "order");
        String releaseId = omsServiceHelper.getOrderEntry(orderID).getOrderReleases().get(0).getId().toString();
        
        omsServiceHelper.updateReleaseStatusDB(releaseId, "SH");
        omsServiceHelper.checkReleaseStatusForOrder(orderID,"SH");
        omsServiceHelper.updateDateInRelease(releaseId);
        orderReleaseResponse = omsServiceHelper.markOrderReleaseRTO(releaseId, login);
        sft.assertEquals(orderReleaseResponse.getStatus().getStatusCode(), 1008, "Verify  Release Response Status Code");
        sft.assertEquals(orderReleaseResponse.getStatus().getStatusType(), Type.SUCCESS, "Verify  Release Response Status Type");
        sft.assertEquals(orderReleaseResponse.getStatus().getStatusMessage(), "OrderRelease updated successfully");
        End2EndHelper.sleep(5000L);
        
        HashMap<String, Object> orderReleaseAdditionalEntry = omsServiceHelper.
        		getOrderReleaseAdditionalInfoDBEntry(releaseId, "RELEASE_CANCELLATION_PPS_ID");
        String cancellationPPSID1 = (String) orderReleaseAdditionalEntry.get("value");
        orderReleaseAdditionalEntry = omsServiceHelper.
        		getOrderReleaseAdditionalInfoDBEntry(releaseId, "RELEASE_CANCELLATION_TX_REF_ID");
        String releasetxRefId1 = (String) orderReleaseAdditionalEntry.get("value");
        
        End2EndHelper.sleep(5000L);
        omsServiceHelper.updateReleaseStatusDB(releaseId, "SH");
        omsServiceHelper.checkReleaseStatusForOrder(orderID,"SH");
        String packetId = omsServiceHelper.getPacketIdFromReleasId(releaseId);
        omsServiceHelper.updatePacketStatus(packetId, EnumSCM.S);
        
        orderReleaseResponse =omsServiceHelper.markOrderReleaseLost(releaseId, login, 23);
        sft.assertEquals(orderReleaseResponse.getStatus().getStatusCode(), 1008, "Verify  Release Response Status Code");
        sft.assertEquals(orderReleaseResponse.getStatus().getStatusType(), Type.SUCCESS, "Verify  Release Response Status Type");
        sft.assertEquals(orderReleaseResponse.getStatus().getStatusMessage(), "OrderRelease updated successfully");
        
        End2EndHelper.sleep(5000L);
        orderReleaseAdditionalEntry = omsServiceHelper.
        		getOrderReleaseAdditionalInfoDBEntry(releaseId, "RELEASE_CANCELLATION_PPS_ID");
        String cancellationPPSID2 = (String) orderReleaseAdditionalEntry.get("value");
        orderReleaseAdditionalEntry = omsServiceHelper.
        		getOrderReleaseAdditionalInfoDBEntry(releaseId, "RELEASE_CANCELLATION_TX_REF_ID");
        String releasetxRefId2 = (String) orderReleaseAdditionalEntry.get("value");
       // end2EndHelper.markOrderDelivered(orderID, "RTO", "order");
        sft.assertEquals(cancellationPPSID1, cancellationPPSID2,"There should not be double refund");
        sft.assertEquals(releasetxRefId1, releasetxRefId2,"RELEASE_CANCELLATION_TX_REF_ID should not update");
        sft.assertAll();
    }
    
    @Test(enabled=true, description="Cancel Order Release which do not have warehouse assigned", groups={"regression","sanity","cancellation"})
    public void verifyDoubleRefundFailureForLOST_RTO() throws Exception {
    	sft = new SoftAssert();
    	String[] skuId = new String[] {OMSTCConstants.OtherSkus.skuId_3831 +":1"};
        atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3831 +":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":1000:0:"+vectorSellerID+":1"}, supplyTypeOnHand);
        imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3831 +":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":1000:0:"+vectorSellerID}, supplyTypeOnHand );
        String orderID =  end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false, "",false);
        omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
        String releaseId = omsServiceHelper.getOrderEntry(orderID).getOrderReleases().get(0).getId().toString();
        omsServiceHelper.updateReleaseStatusDB(releaseId, "SH");
        omsServiceHelper.checkReleaseStatusForOrder(orderID,"SH");
        omsServiceHelper.updateDateInRelease(releaseId);
        orderReleaseResponse =omsServiceHelper.markOrderReleaseLost(releaseId, login, 23);
        sft.assertEquals(orderReleaseResponse.getStatus().getStatusCode(), 1008, "Verify  Release Response Status Code");
        sft.assertEquals(orderReleaseResponse.getStatus().getStatusType(), Type.SUCCESS, "Verify  Release Response Status Type");
        sft.assertEquals(orderReleaseResponse.getStatus().getStatusMessage(), "OrderRelease updated successfully");
        End2EndHelper.sleep(5000L);
        
        HashMap<String, Object> orderReleaseAdditionalEntry = omsServiceHelper.
        		getOrderReleaseAdditionalInfoDBEntry(releaseId, "RELEASE_CANCELLATION_PPS_ID");
        String cancellationPPSID1 = (String) orderReleaseAdditionalEntry.get("value");
        orderReleaseAdditionalEntry = omsServiceHelper.
        		getOrderReleaseAdditionalInfoDBEntry(releaseId, "RELEASE_CANCELLATION_TX_REF_ID");
        String releasetxRefId1 = (String) orderReleaseAdditionalEntry.get("value");
        
        End2EndHelper.sleep(5000L);
        omsServiceHelper.updateReleaseStatusDB(releaseId, "SH");
        omsServiceHelper.checkReleaseStatusForOrder(orderID,"SH");
        
        String packetId = omsServiceHelper.getPacketIdFromReleasId(releaseId);
        omsServiceHelper.updatePacketStatus(packetId, EnumSCM.S);
        orderReleaseResponse = omsServiceHelper.markOrderReleaseRTO(releaseId, login);
        sft.assertEquals(orderReleaseResponse.getStatus().getStatusCode(), 1008, "Verify  Release Response Status Code");
        sft.assertEquals(orderReleaseResponse.getStatus().getStatusType(), Type.SUCCESS, "Verify  Release Response Status Type");
        sft.assertEquals(orderReleaseResponse.getStatus().getStatusMessage(), "OrderRelease updated successfully");
        End2EndHelper.sleep(5000L);
        
        orderReleaseAdditionalEntry = omsServiceHelper.
        		getOrderReleaseAdditionalInfoDBEntry(releaseId, "RELEASE_CANCELLATION_PPS_ID");
        String cancellationPPSID2 = (String) orderReleaseAdditionalEntry.get("value");
        orderReleaseAdditionalEntry = omsServiceHelper.
        		getOrderReleaseAdditionalInfoDBEntry(releaseId, "RELEASE_CANCELLATION_TX_REF_ID");
        String releasetxRefId2 = (String) orderReleaseAdditionalEntry.get("value");
        
        sft.assertEquals(cancellationPPSID1, cancellationPPSID2,"There should not be double refund");
        sft.assertEquals(releasetxRefId1, releasetxRefId2,"RELEASE_CANCELLATION_TX_REF_ID should not update");
        sft.assertAll();
    }
    
  //passed
    @Test(enabled = true, dataProvider="cancelReleaseOfAPackOrderDP", dataProviderClass=CancellationDP.class, groups={"regression","sanity","cancellation"}, description="Check Single Item Line Cancellation triggers Release and Order Cancellation")
    public void cancelOrderOfAPackOrder(String orderID, String login) throws UnsupportedEncodingException, JAXBException{
        sft = new SoftAssert();
        
        cancelOrderFailure(orderID, login, "39", "Order Release Cancellation",8053);
        sft.assertAll();
    }
    
    //passed
    @Test(enabled = true, dataProvider="cancelReleaseOfASHippedOrderDP", dataProviderClass=CancellationDP.class, groups={"regression","sanity","cancellation"}, description="Check Single Item Line Cancellation triggers Release and Order Cancellation")
    public void checkOrderCancellationOfAShippedOrder(String orderID, String login) throws UnsupportedEncodingException, JAXBException{
        sft = new SoftAssert();
        cancelOrderFailure(orderID, login, "39", "Order Release Cancellation",8053);
        
        sft.assertAll();
    }

    //passed
    @Test(enabled = true, dataProvider="cancelReleaseOfADeliveredOrderDP", dataProviderClass=CancellationDP.class, groups={"regression","sanity","cancellation"}, description="Check Single Item Line Cancellation triggers Release and Order Cancellation")
    public void checkOrderCancellationOfADeliveredOrder(String orderID, String login) throws UnsupportedEncodingException, JAXBException{
    	sft = new SoftAssert();
    	cancelOrderFailure(orderID, login, "39", "Order Release Cancellation",8053);
    	sft.assertAll();
    }


}
