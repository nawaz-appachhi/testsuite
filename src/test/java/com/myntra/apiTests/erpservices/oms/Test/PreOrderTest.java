package com.myntra.apiTests.erpservices.oms.Test;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.apache.log4j.Logger;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.myntra.apiTests.end2end.End2EndHelper;
import com.myntra.apiTests.erpservices.atp.ATPServiceHelper;
import com.myntra.apiTests.erpservices.bounty.BountyServiceHelper;
import com.myntra.apiTests.erpservices.ims.IMSServiceHelper;
import com.myntra.apiTests.erpservices.oms.OMSServiceHelper;
import com.myntra.apiTests.erpservices.sellerapis.SellerConfig;
import com.myntra.apiTests.portalservices.pps.PPSServiceHelper;
import com.myntra.commons.codes.StatusResponse.Type;
import com.myntra.lordoftherings.Initialize;
import com.myntra.oms.client.entry.OrderEntry;
import com.myntra.oms.client.entry.OrderLineEntry;
import com.myntra.oms.client.response.OrderReleaseResponse;
import com.myntra.oms.client.response.OrderResponse;
import com.myntra.paymentplan.domain.response.ExchangeOrderResponse;
import com.myntra.test.commons.testbase.BaseTest;

public class PreOrderTest extends BaseTest{
	static Initialize init = new Initialize("/Data/configuration");
    private String login = "preordertest@gmail.com";
	private String password = "123456";
    private static Long vectorSellerID;
    private static Logger log = Logger.getLogger(PreOrderTest.class);
	
	End2EndHelper end2EndHelper = new End2EndHelper();
	OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
	ATPServiceHelper atpServiceHelper = new ATPServiceHelper();
	IMSServiceHelper imsServiceHelper = new IMSServiceHelper();
	BountyServiceHelper bountyServiceHelper = new BountyServiceHelper();
	
	private SoftAssert sft;
	private PPSServiceHelper ppsServiceHelper = new PPSServiceHelper();
	
	public enum skuType{
		PREORDER,
		NORMAL;
	}
	
	@BeforeClass(alwaysRun = true)
	public void testBeforeClass() throws SQLException, UnsupportedEncodingException, JAXBException {
		omsServiceHelper.refreshOMSApplicationPropertyCache();
        omsServiceHelper.refreshOMSJVMCache();
        vectorSellerID = SellerConfig.getSellerID(SellerConfig.SellerName.HANDH);
	}
	
	
	/**
	 * @param storeOrderId
	 * @param skuId
	 * @return
	 */
	public HashMap<String, String> getLaunchDateAndFulfillmentTypeForItemInBounty(String storeOrderId, String skuId){
		HashMap<String, String> hm = new HashMap<String, String>();
		List SelectedRecords = bountyServiceHelper.getxCartOrderDetailsTableDBEntry(storeOrderId);
		for(int i=0;i<SelectedRecords.size();i++){
			HashMap<String, Integer> txResult = (HashMap<String, Integer>) SelectedRecords.get(i);
			
			if(txResult.get("sku_id").toString().equals(skuId)){
				hm.put("fulfillmentType", ""+txResult.get("fulfillment_type"));
				hm.put("LaunchDate", ""+txResult.get("seller_processing_start_time"));
			}
		}
		return hm;
	}
	
	/**
	 * @param orderId
	 * @param skuId
	 * @return
	 */
	public HashMap<String, String> getLaunchDateAndFulfillmentTypeForItemInOMS(String orderId, String skuId){
		HashMap<String, String> hm = new HashMap<String, String>();
		List SelectedRecords = omsServiceHelper.getOrderLineDBEntry(orderId);
		for(int i=0;i<SelectedRecords.size();i++){
			HashMap<String, Integer> txResult = (HashMap<String, Integer>) SelectedRecords.get(i);
			if(txResult.get("sku_id").toString().equals(skuId)){
				String lineId = ""+txResult.get("id");
				String fullfillmentType = omsServiceHelper.getOrderLineAdditionalInfoDBEntry(lineId, "FULFILLMENT_TYPE").get("value").toString();
				hm.put("fulfillmentType",fullfillmentType);
				hm.put("LaunchDate",omsServiceHelper.getOrderLineAdditionalInfoDBEntry(lineId, "SELLER_PROCESSING_START_TIME").get("value").toString());				
			}
		}
		
		return hm;
	}
	
	/**
	 * @param storeOrderId
	 * @param skuIds
	 */
	public void verifyPreOrderFieldsInBounty(String storeOrderId,String[]skuIds){
		HashMap<String, String> hm = null;
		for(String skuIdItems: skuIds){
			String []skuIdList = skuIdItems.split(":");
			String skuId = skuIdList[0];
			String ispreorder = skuIdList[1];
			hm = getLaunchDateAndFulfillmentTypeForItemInBounty(storeOrderId, skuId);
			sft.assertNotNull(hm.get("LaunchDate"),"Launch date should not be null but Actual:"+hm.get("LaunchDate"));
			
			if(ispreorder.equalsIgnoreCase("Preorder")){	
				sft.assertEquals(hm.get("fulfillmentType"), "PREORDER","Fulfillment Type should be PREORDER but Actual:"+hm.get("fulfillmentType"));
				
			}else if(ispreorder.equalsIgnoreCase("Normal")){
				sft.assertEquals(hm.get("fulfillmentType"), "IMMEDIATE","Fulfillment Type should be IMMEDIATE but Actual:"+hm.get("fulfillmentType"));
								
			}
		}
	}
	
	/**
	 * @param storeOrderId
	 * @param skuIds
	 * LaunchDate and FulfillmentType in OMS
	 */
	public void verifyPreOrderFieldsInOMS(String storeOrderId,String[]skuIds ){
		String orderId = ""+omsServiceHelper.getOrderId(storeOrderId);
		
		for(String skuIdItems: skuIds){
			String []skuIdList = skuIdItems.split(":");
			String skuId = skuIdList[0];
			String ispreorder = skuIdList[1];
			if(ispreorder.equalsIgnoreCase("Preorder")){
				HashMap<String, String> hm = getLaunchDateAndFulfillmentTypeForItemInOMS(orderId, skuId);
				sft.assertEquals(hm.get("fulfillmentType"), "PREORDER");
				sft.assertNotNull(hm.get("LaunchDate"));
				
			}else if(ispreorder.equalsIgnoreCase("Normal")){
				HashMap<String, String> hm = getLaunchDateAndFulfillmentTypeForItemInOMS(orderId, skuId);
				sft.assertEquals(hm.get("fulfillmentType"), "IMMEDIATE");
				sft.assertNull(hm.get("LaunchDate"));				
			}
		}
	}
	
	/**
	 * @param storeOrderId
	 * Check if launchDate is same in OMS and Bounty
	 */
	public void checkLaunchDateIsSameInOMSAndBounty(String storeOrderId){
		String orderId = ""+omsServiceHelper.getOrderId(storeOrderId);
		
		List SelectedRecords = bountyServiceHelper.getxCartOrderDetailsTableDBEntry(storeOrderId);
		for(int i=0;i<SelectedRecords.size();i++){
			HashMap<String, Integer> txResult = (HashMap<String, Integer>) SelectedRecords.get(i);
			String skuId = txResult.get("sku_id").toString();
			String launchDateInBounty = getEpocTimeFromTimestamp(""+txResult.get("seller_processing_start_time"));
			String launchDateInOMS = getLaunchDateAndFulfillmentTypeForItemInOMS(orderId,skuId).get("LaunchDate");
			log.info("LaunchDate in Bounty: "+launchDateInBounty+" in OMS: "+launchDateInOMS);
			sft.assertEquals(launchDateInBounty, launchDateInOMS,"Launch Date should be same in OMS and Bounty Actual:"
					+ " InBounty:"+launchDateInBounty+" and InOMS: "+launchDateInOMS);
		}
		
	}
	
	public String getEpocTimeFromTimestamp(String launchDate){
		
		long epoch = Long.parseLong( launchDate );
        Date expiry = new Date( epoch * 1000 );
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return ""+format.format(expiry);
	}
	
	//Passed
	@Test(enabled = true,groups={"regression","preorder"},description="TC001:Place singleLine order with future Launch Date and verify fulfilment Type and Launch Date field for preorder in bounty sent from cart.")
	public void singleLinePreOrderWithFutureLaunchDate() throws Exception{
		sft = new SoftAssert();
		
		String skuId[] = { OMSTCConstants.PreOrderSkus.preorderSkuId1Seller21+":1"};
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.PreOrderSkus.preorderSkuId1Seller21+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID+":1"},"ON_HAND");
        imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.PreOrderSkus.preorderSkuId1Seller21+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID},"ON_HAND");
        
        String orderID =  end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false,"",false);
	    omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
	    OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
	    String storeOrderId = orderEntry.getStoreOrderId();
	    verifyPreOrderFieldsInBounty(storeOrderId, new String[]{ OMSTCConstants.PreOrderSkus.preorderSkuId1Seller21+":"+skuType.PREORDER });
		checkLaunchDateIsSameInOMSAndBounty(storeOrderId);
		sft.assertAll();
	}
	
	//Passed
	@Test(enabled = true,groups={"regression","preorder"},description="TC002:Place singleLine order and verify fulfilment Type field for normal order in bounty sent from cart.")
	public void singleLineOrderWithNormalItem() throws Exception{
		sft = new SoftAssert();
		
		String skuId[] = { OMSTCConstants.PreOrderSkus.normalSkuId2Seller21+":1"};
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.PreOrderSkus.normalSkuId2Seller21+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID+":1"},"ON_HAND");
        imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.PreOrderSkus.normalSkuId2Seller21+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID},"ON_HAND");
        
        String orderID =  end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false,"",false);
	    omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
	    OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
	    String storeOrderId = orderEntry.getStoreOrderId();
	    verifyPreOrderFieldsInBounty(storeOrderId, new String[]{OMSTCConstants.PreOrderSkus.normalSkuId2Seller21+":"+skuType.NORMAL});
	    checkLaunchDateIsSameInOMSAndBounty(storeOrderId);
	    sft.assertAll();
	}

	@Test(enabled = true,groups={"regression","preorder"},description="TC003:Place Multiple pre-order items with same launch date.")
	public void multiLinePreOrderItemWithSameFutureLaunchDate() throws Exception{
		sft = new SoftAssert();
		
		String skuId[] = { OMSTCConstants.PreOrderSkus.preorderSkuId1Seller21+":1",OMSTCConstants.PreOrderSkus.preorderSkuId2Seller21+":1"};
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.PreOrderSkus.preorderSkuId1Seller21+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID+":1",
				OMSTCConstants.PreOrderSkus.preorderSkuId2Seller21+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID+":1"},"ON_HAND");
        imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.PreOrderSkus.preorderSkuId1Seller21+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID,
				OMSTCConstants.PreOrderSkus.preorderSkuId2Seller21+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID},"ON_HAND");
        
        String orderID =  end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false,"",true);
	    omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
	    OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
	    String storeOrderId = orderEntry.getStoreOrderId();
	    verifyPreOrderFieldsInBounty(storeOrderId, new String[]{OMSTCConstants.PreOrderSkus.preorderSkuId1Seller21+":"+skuType.PREORDER,
	    		OMSTCConstants.PreOrderSkus.preorderSkuId2Seller21+":"+skuType.PREORDER});
	    checkLaunchDateIsSameInOMSAndBounty(storeOrderId);
	    sft.assertAll();
	}

	//Passed
	@Test(enabled = true,groups={"regression","preorder"},description="TC004:Place Multiple pre-order items with different launch date.")
	public void multiLinePreOrderItemWithDifferentFutureLaunchDate() throws Exception{
		sft = new SoftAssert();
		
		String skuId[] = { OMSTCConstants.PreOrderSkus.preorderSkuId3Seller21+":1",OMSTCConstants.PreOrderSkus.preorderSkuId4Seller21+":1"};
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.PreOrderSkus.preorderSkuId3Seller21+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID+":1",
				OMSTCConstants.PreOrderSkus.preorderSkuId4Seller21+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID+":1"},"ON_HAND");
        imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.PreOrderSkus.preorderSkuId3Seller21+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID,
				OMSTCConstants.PreOrderSkus.preorderSkuId4Seller21+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID},"ON_HAND");
        
        String orderID =  end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false,"",true);
	    omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
	    OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
	    String storeOrderId = orderEntry.getStoreOrderId();
	    verifyPreOrderFieldsInBounty(storeOrderId, new String[]{OMSTCConstants.PreOrderSkus.preorderSkuId3Seller21+":"+skuType.PREORDER,OMSTCConstants.PreOrderSkus.preorderSkuId4Seller21+":"+skuType.PREORDER});
	    checkLaunchDateIsSameInOMSAndBounty(storeOrderId);
	    sft.assertAll();
	}

	@Test(enabled = true,groups={"regression","preorder"},description="TC005:Place Combination of pre-order and normal items.")
	public void orderWithPreOrderAndNormalItemWithFutureLaunchDate() throws Exception{
		sft = new SoftAssert();
		
		String skuId[] = { OMSTCConstants.PreOrderSkus.preorderSkuId1Seller21+":1",OMSTCConstants.PreOrderSkus.normalSkuId2Seller21+":1"};
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.PreOrderSkus.preorderSkuId1Seller21+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID+":1",
				OMSTCConstants.PreOrderSkus.normalSkuId1Seller21+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID+":1"},"ON_HAND");
        imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.PreOrderSkus.preorderSkuId1Seller21+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID,
				OMSTCConstants.PreOrderSkus.normalSkuId1Seller21+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID},"ON_HAND");
        
        String orderID =  end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false,"",true);
	    omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
	    OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
	    String storeOrderId = orderEntry.getStoreOrderId();
	    verifyPreOrderFieldsInBounty(storeOrderId, new String[]{OMSTCConstants.PreOrderSkus.preorderSkuId1Seller21+":"+skuType.PREORDER,OMSTCConstants.PreOrderSkus.normalSkuId2Seller21+":"+skuType.NORMAL});
	}

	@Test(enabled = true,groups={"regression","preorder"},description="TC006:Place singleLine order with future Launch Date and verify fulfilment Type and Launch Date field for preorder in OMS sent from Bounty.")
	public void singleLineOrderOfPreOrderItemWithFutureLaunchDateOMS() throws Exception{
		sft = new SoftAssert();
		
		String skuId[] = { OMSTCConstants.PreOrderSkus.preorderSkuId1Seller21+":1"};
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.PreOrderSkus.preorderSkuId1Seller21+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID+":1"},"ON_HAND");
        imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.PreOrderSkus.preorderSkuId1Seller21+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID},"ON_HAND");
        
        String orderID =  end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false,"",true);
	    omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");	
	    OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
	    String storeOrderId = orderEntry.getStoreOrderId();
	    verifyPreOrderFieldsInOMS(storeOrderId, new String[]{OMSTCConstants.PreOrderSkus.preorderSkuId1Seller21+":"+skuType.PREORDER});
	}

	@Test(enabled = true,groups={"regression","preorder"},description="TC007:Place singleLine order and verify fulfilment Type field for normal order in OMS sent from Bounty.")
	public void singleLineOrderWithNormalItemOMS() throws Exception{
		sft = new SoftAssert();
		
		String skuId[] = { OMSTCConstants.PreOrderSkus.normalSkuId1Seller21+":1"};
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.PreOrderSkus.normalSkuId1Seller21+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID+":1"},"ON_HAND");
        imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.PreOrderSkus.normalSkuId1Seller21+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID},"ON_HAND");
        
        String orderID =  end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false,"",true);
	    omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
	    OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
	    String storeOrderId = orderEntry.getStoreOrderId();
	    verifyPreOrderFieldsInOMS(storeOrderId, new String[]{OMSTCConstants.PreOrderSkus.normalSkuId1Seller21+":"+skuType.NORMAL});
	}

	@Test(enabled = true,groups={"regression","preorder"},description="TC008:Place Multiple pre-order items with same launch date.")
	public void multiLinePreOrderWithSameFutureLaunchDateOMS() throws Exception{
		sft = new SoftAssert();
		
		String skuId[] = { OMSTCConstants.PreOrderSkus.preorderSkuId1Seller21+":1",OMSTCConstants.PreOrderSkus.preorderSkuId2Seller21+":1"};
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.PreOrderSkus.preorderSkuId1Seller21+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID+":1",
				OMSTCConstants.PreOrderSkus.preorderSkuId2Seller21+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID+":1"},"ON_HAND");
        imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.PreOrderSkus.preorderSkuId1Seller21+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID,
        		OMSTCConstants.PreOrderSkus.preorderSkuId2Seller21+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID},"ON_HAND");
        
        String orderID =  end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false,"",true);
	    omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
	    OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
	    String storeOrderId = orderEntry.getStoreOrderId();
	    verifyPreOrderFieldsInOMS(storeOrderId, new String[]{OMSTCConstants.PreOrderSkus.preorderSkuId1Seller21,OMSTCConstants.PreOrderSkus.preorderSkuId2Seller21});
	}

	@Test(enabled = true,groups={"regression","preorder"},description="TC009:Place Multiple pre-order items with different launch date.")
	public void multiLinePreOrderWithDifferentFutureLaunchDateOMS() throws Exception{
		sft = new SoftAssert();
		
		String skuId[] = { OMSTCConstants.PreOrderSkus.preorderSkuId3Seller21+":1",OMSTCConstants.PreOrderSkus.preorderSkuId4Seller21+":1"};
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.PreOrderSkus.preorderSkuId3Seller21+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID+":1",
				OMSTCConstants.PreOrderSkus.preorderSkuId4Seller21+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID+":1"},"ON_HAND");
        imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.PreOrderSkus.preorderSkuId3Seller21+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID,
        		OMSTCConstants.PreOrderSkus.preorderSkuId4Seller21+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID+":1"},"ON_HAND");
        
        String orderID =  end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false,"",true);
	    omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
	    OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
	    String storeOrderId = orderEntry.getStoreOrderId();
	    verifyPreOrderFieldsInOMS(storeOrderId, new String[]{OMSTCConstants.PreOrderSkus.preorderSkuId3Seller21+":"+skuType.PREORDER,OMSTCConstants.PreOrderSkus.preorderSkuId4Seller21+":"+skuType.PREORDER});
	}

	@Test(enabled = true,groups={"regression","preorder"},description="TC010:Place Combination of pre-order and normal items.")
	public void orderWithPreOrderItemAndNormalItemFutureLaunchDateOMS() throws Exception{
		sft = new SoftAssert();
		
		String skuId[] = { OMSTCConstants.PreOrderSkus.preorderSkuId1Seller21+":1", OMSTCConstants.PreOrderSkus.normalSkuId1Seller21+":1"};
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.PreOrderSkus.preorderSkuId1Seller21+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID+":1",
				OMSTCConstants.PreOrderSkus.normalSkuId1Seller21+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID+":1"},"ON_HAND");
        imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.PreOrderSkus.preorderSkuId1Seller21+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID,
        		OMSTCConstants.PreOrderSkus.normalSkuId1Seller21+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID},"ON_HAND");
        
        String orderID =  end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false,"",true);
	    omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
	    OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
	    String storeOrderId = orderEntry.getStoreOrderId();
	    verifyPreOrderFieldsInOMS(storeOrderId, new String[]{OMSTCConstants.PreOrderSkus.preorderSkuId1Seller21+":"+skuType.PREORDER,OMSTCConstants.PreOrderSkus.normalSkuId1Seller21+":"+skuType.NORMAL});
	}

	@Test(enabled = true,groups={"regression","preorder"},description="TC011:Place order with preorderItem, Normal Item and TnB items with multiple quantities.")
	public void orderWithPreOrderItemNormalItemAndTnBItemMultiquantity() throws Exception{
		sft = new SoftAssert();
		
		String skuId[] = { OMSTCConstants.PreOrderSkus.preorderSkuId1Seller21+":2",OMSTCConstants.PreOrderSkus.normalSkuId1Seller21+":2",OMSTCConstants.PreOrderSkus.normalSkuId1TnBSeller21+":2"};
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.PreOrderSkus.preorderSkuId1Seller21+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID+":1",
				OMSTCConstants.PreOrderSkus.normalSkuId1Seller21+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID+":1",
				OMSTCConstants.PreOrderSkus.normalSkuId1TnBSeller21+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID+":1"},"ON_HAND");
        imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.PreOrderSkus.preorderSkuId1Seller21+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID,
				OMSTCConstants.PreOrderSkus.normalSkuId1Seller21+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID,
				OMSTCConstants.PreOrderSkus.normalSkuId1TnBSeller21+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID},"ON_HAND");
        
        String orderID =  end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false,"",true);
	    omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
	    //check number of releases
	    OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
	    sft.assertEquals(3,orderEntry.getOrderReleases().size(),"The order release should be three as there are 3 types of items: Actual :" +orderEntry.getOrderReleases().size());
	    sft.assertAll();
	}
	
	@Test(enabled = true,groups={"regression","preorder"},description="TC012:Place order with multiple preorderItem of same seller with different Launch Date.")
	public void orderWithMultiplePreOrderItemSameSellerDifferentLaunchDate() throws Exception{
		sft = new SoftAssert();
		
		String skuId[] = { OMSTCConstants.PreOrderSkus.preorderSkuId3Seller21+":1",OMSTCConstants.PreOrderSkus.preorderSkuId4Seller21+":1"};
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.PreOrderSkus.preorderSkuId3Seller21+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID+":1",
				OMSTCConstants.PreOrderSkus.preorderSkuId4Seller21+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID+":1"},"ON_HAND");
        imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.PreOrderSkus.preorderSkuId3Seller21+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID,
        		OMSTCConstants.PreOrderSkus.preorderSkuId4Seller21+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID},"ON_HAND");
        
        String orderID =  end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false,"",true);
	    omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
	    //check number of releases
	    OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
	    sft.assertEquals(2,orderEntry.getOrderReleases().size(),"The order release should be two as there are 3 types of items: Actual :" +orderEntry.getOrderReleases().size());
	    sft.assertAll();
	}

	@Test(enabled = true,groups={"regression","preorder"},description="TC013:Place order with multiple preorderItem of different seller with different Launch Date.")
	public void orderWithMultiplePreOrderItemDifferentSellerDifferentLaunchDate() throws Exception{
		sft = new SoftAssert();
		
		String skuId[] = { OMSTCConstants.PreOrderSkus.preorderSkuId1Seller21+":1",OMSTCConstants.PreOrderSkus.preorderSkuId3Seller25+":1"};
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.PreOrderSkus.preorderSkuId3Seller25+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID+":1",
				OMSTCConstants.PreOrderSkus.preorderSkuId1Seller21+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID+":1"},"ON_HAND");
        imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.PreOrderSkus.preorderSkuId1Seller21+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID,
        		OMSTCConstants.PreOrderSkus.preorderSkuId3Seller25+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID},"ON_HAND");
        
        String orderID =  end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false,"",true);
	    omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");	
	  //check number of releases
	    OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
	    sft.assertEquals(2,orderEntry.getOrderReleases().size(),"The order release should be two as there are 3 types of items: Actual :" +orderEntry.getOrderReleases().size());
	    sft.assertAll();
	}
	
	@Test(enabled = true,groups={"regression","preorder"},description="TC014:Place order with multiple preorderItem of same seller with same Launch Date.")
	public void orderWithMultiplePreOrderItemSameSellerSameLaunchDate() throws Exception{
		sft = new SoftAssert();
		
		String skuId[] = { OMSTCConstants.PreOrderSkus.preorderSkuId1Seller21+":1",OMSTCConstants.PreOrderSkus.preorderSkuId2Seller21+":1"};
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.PreOrderSkus.preorderSkuId1Seller21+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID+":1",
				OMSTCConstants.PreOrderSkus.preorderSkuId2Seller21+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID+":1"},"ON_HAND");
        imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.PreOrderSkus.preorderSkuId1Seller21+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID,
        		OMSTCConstants.PreOrderSkus.preorderSkuId2Seller21+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID},"ON_HAND");
        
        String orderID =  end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false,"",true);
	    omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
	    //check number of releases
	    OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
	    sft.assertEquals(1,orderEntry.getOrderReleases().size(),"The order release should be two as there are 3 types of items: Actual :" +orderEntry.getOrderReleases().size());
	    sft.assertAll();
	}
	
	@Test(enabled = true,groups={"regression","preorder"},description="TC015:Place order with multiple preorderItem of different seller with same Launch Date.")
	public void orderWithMultiplePreOrderItemDifferentSellerSameLaunchDate() throws Exception{
		sft = new SoftAssert();
		
		String skuId[] = { OMSTCConstants.PreOrderSkus.preorderSkuId1Seller21+":1", OMSTCConstants.PreOrderSkus.preorderSkuId3Seller25+":1"};
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.PreOrderSkus.preorderSkuId1Seller21+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID+":1",
				OMSTCConstants.PreOrderSkus.preorderSkuId3Seller25+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID+":1"},"ON_HAND");
        imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.PreOrderSkus.preorderSkuId1Seller21+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID,
        		OMSTCConstants.PreOrderSkus.preorderSkuId3Seller25+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID},"ON_HAND");
        
        String orderID =  end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false,"",true);
	    omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");	
	    //check number of releases
	    OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
	    sft.assertEquals(1,orderEntry.getOrderReleases().size(),"The order release should be two as there are 3 types of items: Actual :" +orderEntry.getOrderReleases().size());
	    sft.assertAll();
	}
	
	@Test(enabled = true,groups={"regression","preorder"},description="TC016:Place order with multiple preorderItem assigned to different warehouseIds.")
	public void orderWithMultiplePreOrderItemDifferentWarehouseIds() throws Exception{
		sft = new SoftAssert();
		
		String skuId[] = { OMSTCConstants.PreOrderSkus.preorderSkuId1Seller21+":1",OMSTCConstants.PreOrderSkus.preorderSkuId2Seller21+":1"};
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.PreOrderSkus.preorderSkuId1Seller21+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID+":1",
				OMSTCConstants.PreOrderSkus.preorderSkuId2Seller21+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":1000:0:"+vectorSellerID+":1"},"ON_HAND");
        imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.PreOrderSkus.preorderSkuId1Seller21+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID,
        		OMSTCConstants.PreOrderSkus.preorderSkuId2Seller21+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":1000:0:"+vectorSellerID},"ON_HAND");
        
        String orderID =  end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false,"",true);
	    omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
	    //check number of releases
	    OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
	    sft.assertEquals(1,orderEntry.getOrderReleases().size(),"The order release should be two as there are 3 types of items: Actual :" +orderEntry.getOrderReleases().size());
	    sft.assertAll();
	}
	
	@Test(enabled = true,groups={"regression","preorder"},description="TC017:Place order with multiple preorderItem assigned to same warehouseIds.")
	public void orderWithMultiplePreOrderItemSameWarehouseIds() throws Exception{
		sft = new SoftAssert();
		
		String skuId[] = { OMSTCConstants.PreOrderSkus.preorderSkuId1Seller21+":1",OMSTCConstants.PreOrderSkus.preorderSkuId2Seller21+":1"};
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.PreOrderSkus.preorderSkuId1Seller21+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID+":1",
				OMSTCConstants.PreOrderSkus.preorderSkuId2Seller21+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":1000:0:"+vectorSellerID+":1"},"ON_HAND");
        imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.PreOrderSkus.preorderSkuId1Seller21+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID,
        		OMSTCConstants.PreOrderSkus.preorderSkuId2Seller21+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":1000:0:"+vectorSellerID},"ON_HAND");
        
        String orderID =  end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false,"",true);
	    omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
	    //check number of releases
	    OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
	    sft.assertEquals(1,orderEntry.getOrderReleases().size(),"The order release should be two as there are 3 types of items: Actual :" +orderEntry.getOrderReleases().size());
	    sft.assertAll();
	}
	
	@Test(enabled = true,groups={"regression","preorder"},description="TC018:Place order with preorderItem and Normal Item and verify courierwise split.")
	public void checkCourierWiseSplitWithPreOrderItemAndNormalItem() throws Exception{
		sft = new SoftAssert();
		
		String skuId[] = { OMSTCConstants.PreOrderSkus.preorderSkuId1Seller21+":1",OMSTCConstants.PreOrderSkus.preorderSkuId2Seller21+":1"};
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.PreOrderSkus.preorderSkuId1Seller21+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID+":1",
				OMSTCConstants.PreOrderSkus.preorderSkuId2Seller21+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":1000:0:"+vectorSellerID+":1"},"ON_HAND");
        imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.PreOrderSkus.preorderSkuId1Seller21+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID,
        		OMSTCConstants.PreOrderSkus.preorderSkuId2Seller21+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":1000:0:"+vectorSellerID},"ON_HAND");
        
        String orderID =  end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false,"",true);
	    omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
	    //check number of releases
	    OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
	    sft.assertEquals(1,orderEntry.getOrderReleases().size(),"The order release should be two as there are 3 types of items: Actual :" +orderEntry.getOrderReleases().size());
	    sft.assertAll();
	}
	
	@Test(enabled = true,groups={"regression","preorder"},description="TC019:Place order with multiple Preorder items and verify statewise split.")
	public void checkStateWiseSplitWithMultiplePreOrderItems() throws Exception{
		sft = new SoftAssert();
		
		String skuId[] = { OMSTCConstants.PreOrderSkus.preorderSkuId1Seller21+":1",OMSTCConstants.PreOrderSkus.preorderSkuId2Seller21+":1"};
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.PreOrderSkus.preorderSkuId1Seller21+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID+":1",
				OMSTCConstants.PreOrderSkus.preorderSkuId2Seller21+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":1000:0:"+vectorSellerID+":1"},"ON_HAND");
        imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.PreOrderSkus.preorderSkuId1Seller21+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID,
        		OMSTCConstants.PreOrderSkus.preorderSkuId2Seller21+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":1000:0:"+vectorSellerID},"ON_HAND");
        
        String orderID =  end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false,"",true);
	    omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
	    //check number of releases
	    OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
	    sft.assertEquals(1,orderEntry.getOrderReleases().size(),"The order release should be two as there are 3 types of items: Actual :" +orderEntry.getOrderReleases().size());
	    sft.assertAll();
	}
	
	@Test(enabled = true,groups={"regression","preorder"},description="TC020:Place order with preorderItem and NormalItem with multipleqty and cancel both Lines.")
	public void cancleLineWithPreOrderItemAndNormalItem() throws Exception{
		
		String skuId[] = { OMSTCConstants.PreOrderSkus.preorderSkuId1Seller21+":1",OMSTCConstants.PreOrderSkus.normalSkuId1Seller21+":1"};
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.PreOrderSkus.preorderSkuId1Seller21+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID+":1",
				OMSTCConstants.PreOrderSkus.normalSkuId1Seller21+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":1000:0:"+vectorSellerID+":1"},"ON_HAND");
        imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.PreOrderSkus.preorderSkuId1Seller21+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID,
        		OMSTCConstants.PreOrderSkus.normalSkuId1Seller21+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":1000:0:"+vectorSellerID},"ON_HAND");
        
        String orderID =  end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false,"",true);
	    omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
	    OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
	    //cancel first Line
	    String orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();
	    List<OrderLineEntry> orderLineEntries = omsServiceHelper.getOrderLineEntries(orderReleaseId);
        String lineID = ""+orderLineEntries.get(0).getId();

        OrderReleaseResponse cancellationRes = omsServiceHelper.cancelLine(orderReleaseId, login, new String[] {lineID +":1"}, 39);
        verifyLineCancellationStatus(omsServiceHelper.getOrderLineEntries(orderReleaseId).get(0),cancellationRes);
        
        //cancel second Line
        orderReleaseId = orderEntry.getOrderReleases().get(1).getId().toString();
	    orderLineEntries = omsServiceHelper.getOrderLineEntries(orderReleaseId);
	    System.out.println(orderLineEntries.get(0));
        lineID = ""+orderLineEntries.get(0).getId();

        cancellationRes = omsServiceHelper.cancelLine(orderReleaseId, login, new String[] {lineID +":1"}, 39);
        verifyLineCancellationStatus(omsServiceHelper.getOrderLineEntries(orderReleaseId).get(0),cancellationRes);
	}
	
	@Test(enabled = true,groups={"regression","preorder"},description="TC021:Place order with multiple preItem with different Launch data and cancel first Line and fulfill second Line.")
	public void cancleOneLineWithMultiplePreOrderItems() throws Exception{
		sft = new SoftAssert();
		
		String skuId[] = { OMSTCConstants.PreOrderSkus.preorderSkuId1Seller21+":1",OMSTCConstants.PreOrderSkus.preorderSkuId2Seller21+":1"};
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.PreOrderSkus.preorderSkuId1Seller21+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID+":1",
				OMSTCConstants.PreOrderSkus.preorderSkuId2Seller21+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":1000:0:"+vectorSellerID+":1"},"ON_HAND");
        imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.PreOrderSkus.preorderSkuId1Seller21+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID,
        		OMSTCConstants.PreOrderSkus.preorderSkuId2Seller21+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":1000:0:"+vectorSellerID},"ON_HAND");
        
        String orderID =  end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false,"",true);
	    omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
	    OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
	    //cancel first Line
	    String orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();
	    List<OrderLineEntry> orderLineEntries = omsServiceHelper.getOrderLineEntries(orderReleaseId);
        String lineID = ""+orderLineEntries.get(0).getId();

        OrderReleaseResponse cancellationRes = omsServiceHelper.cancelLine(orderReleaseId, login, new String[] {lineID +":1"}, 39);
        verifyLineCancellationStatus(omsServiceHelper.getOrderLineEntries(orderReleaseId).get(0),cancellationRes);
	}

	@Test(enabled = true,groups={"regression","preorder"},description="TC022:Place order with multiple preOrder items with multiple qty and do partial cancellation for both Lines.")
	public void partialCancleBothLineWithMultiplePreOrderItems() throws Exception{
		sft = new SoftAssert();
		
		String skuId[] = { OMSTCConstants.PreOrderSkus.preorderSkuId1Seller21+":2",OMSTCConstants.PreOrderSkus.preorderSkuId2Seller21+":2"};
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.PreOrderSkus.preorderSkuId1Seller21+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID+":1",
				OMSTCConstants.PreOrderSkus.preorderSkuId2Seller21+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":1000:0:"+vectorSellerID+":1"},"ON_HAND");
        imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.PreOrderSkus.preorderSkuId1Seller21+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID,
        		OMSTCConstants.PreOrderSkus.preorderSkuId2Seller21+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":1000:0:"+vectorSellerID},"ON_HAND");
        
        String orderID =  end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false,"",true);
	    omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
	    OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
	    //cancel first Line
	    String orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();
	    List<OrderLineEntry> orderLineEntries = omsServiceHelper.getOrderLineEntries(orderReleaseId);
        String lineID = ""+orderLineEntries.get(0).getId();

        OrderReleaseResponse cancellationRes = omsServiceHelper.cancelLine(orderReleaseId, login, new String[] {lineID +":1"}, 39);
        verifyLineCancellationStatus(omsServiceHelper.getOrderLineEntries(orderReleaseId).get(0),cancellationRes);
        //cancel second Line
        orderReleaseId = orderEntry.getOrderReleases().get(1).getId().toString();
	    orderLineEntries = omsServiceHelper.getOrderLineEntries(orderReleaseId);
        lineID = ""+orderLineEntries.get(0).getId();

        cancellationRes = omsServiceHelper.cancelLine(orderReleaseId, login, new String[] {lineID +":1"}, 39);
        verifyLineCancellationStatus(omsServiceHelper.getOrderLineEntries(orderReleaseId).get(0),cancellationRes);
	}
	
	@Test(enabled = true,groups={"regression","preorder"},description="TC023:Place order with preorderItem and NormalItem with multipleqty and cancel both releases.")
	public void cancleReleaseWithPreOrderItemAndNormalItem() throws Exception{
		String skuId[] = { OMSTCConstants.PreOrderSkus.preorderSkuId1Seller21+":2",OMSTCConstants.PreOrderSkus.normalSkuId1Seller21+":2"};
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.PreOrderSkus.preorderSkuId1Seller21+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID+":1",
				OMSTCConstants.PreOrderSkus.normalSkuId1Seller21+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":1000:0:"+vectorSellerID+":1"},"ON_HAND");
        imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.PreOrderSkus.preorderSkuId1Seller21+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID,
        		OMSTCConstants.PreOrderSkus.normalSkuId1Seller21+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":1000:0:"+vectorSellerID},"ON_HAND");
        
        String orderID =  end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false,"",true);
	    omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
	    OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
	    
	    //cancel first release
	    String orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();
	    
	    OrderReleaseResponse cancellationRes = omsServiceHelper.cancelOrderRelease(orderReleaseId, "1", login, "TestOrder Cancellation");
	    verifyReleaseCancellationStatus(cancellationRes,orderReleaseId);
        
        //cancel second release
        orderReleaseId = orderEntry.getOrderReleases().get(1).getId().toString();
        cancellationRes = omsServiceHelper.cancelOrderRelease(orderReleaseId, "1", login, "TestOrder Cancellation");
        verifyReleaseCancellationStatus(cancellationRes,orderReleaseId);
	}
	
	@Test(enabled = true,groups={"regression","preorder"},description="TC024:Place order with multiple preorderItem with different Launch Date. and cancel both releases.")
	public void cancleReleaseWithMultiplePreOrderItems() throws Exception{
		
		String skuId[] = { OMSTCConstants.PreOrderSkus.preorderSkuId1Seller21+":2",OMSTCConstants.PreOrderSkus.preorderSkuId2Seller21+":2"};
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.PreOrderSkus.preorderSkuId1Seller21+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID+":1",
				OMSTCConstants.PreOrderSkus.preorderSkuId2Seller21+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":1000:0:"+vectorSellerID+":1"},"ON_HAND");
        imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.PreOrderSkus.preorderSkuId1Seller21+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID,
        		OMSTCConstants.PreOrderSkus.preorderSkuId2Seller21+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":1000:0:"+vectorSellerID},"ON_HAND");
        
        String orderID =  end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false,"",true);
	    omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
	    OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
	    
	    //cancel first release
	    String orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();
	    
	    OrderReleaseResponse cancellationRes = omsServiceHelper.cancelOrderRelease(orderReleaseId, "1", login, "TestOrder Cancellation");
	    verifyReleaseCancellationStatus(cancellationRes,orderReleaseId);
        
        //cancel second release
        orderReleaseId = orderEntry.getOrderReleases().get(1).getId().toString();
        cancellationRes = omsServiceHelper.cancelOrderRelease(orderReleaseId, "1", login, "TestOrder Cancellation");
        verifyReleaseCancellationStatus(cancellationRes,orderReleaseId);
        
	}
	
	
	@Test(enabled = true,groups={"regression","preorder"},description="TC025:Place order with multiple preorderItem with different Launch Date. and cancel one releases.")
	public void cancleOneReleaseWithMultiplePreOrderItems() throws Exception{
		String skuId[] = { OMSTCConstants.PreOrderSkus.preorderSkuId1Seller21+":2",OMSTCConstants.PreOrderSkus.preorderSkuId2Seller21+":2"};
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.PreOrderSkus.preorderSkuId1Seller21+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID+":1",
				OMSTCConstants.PreOrderSkus.preorderSkuId2Seller21+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":1000:0:"+vectorSellerID+":1"},"ON_HAND");
        imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.PreOrderSkus.preorderSkuId1Seller21+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID,
        		OMSTCConstants.PreOrderSkus.preorderSkuId2Seller21+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":1000:0:"+vectorSellerID},"ON_HAND");
        
        String orderID =  end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false,"",true);
	    omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
	    OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
	    
	    //cancel first release
	    String orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();
	    
	    OrderReleaseResponse cancellationRes = omsServiceHelper.cancelOrderRelease(orderReleaseId, "1", login, "TestOrder Cancellation");
	    verifyReleaseCancellationStatus(cancellationRes,orderReleaseId);
	}

	@Test(enabled = true,groups={"regression","preorder"},description="TC026:Place order with multiple preorderItem with different Launch Date. and cancel whole order.")
	public void cancleOrderWithMultiplePreOrderItems() throws Exception{
		String skuId[] = { OMSTCConstants.PreOrderSkus.preorderSkuId1Seller21+":2",OMSTCConstants.PreOrderSkus.preorderSkuId2Seller21+":2"};
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.PreOrderSkus.preorderSkuId1Seller21+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID+":1",
				OMSTCConstants.PreOrderSkus.preorderSkuId2Seller21+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":1000:0:"+vectorSellerID+":1"},"ON_HAND");
        imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.PreOrderSkus.preorderSkuId1Seller21+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID,
        		OMSTCConstants.PreOrderSkus.preorderSkuId2Seller21+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":1000:0:"+vectorSellerID},"ON_HAND");
        
        String orderID =  end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false,"",true);
	    omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
	    
	    //cancel order
	    OrderResponse cancellationRes = omsServiceHelper.cancelOrder(orderID, "1", login, "TestOrder Cancellation");
	    verifyOrderCancellationStatus(cancellationRes);
	}

	@Test(enabled = true,groups={"regression","preorder"},description="TC027:Place order with preorderItem and NormalItem with multipleqty and cancel order.")
	public void cancleOrderWithPreOrderItemAndNormalItem() throws Exception{
		sft = new SoftAssert();
		
		String skuId[] = { OMSTCConstants.PreOrderSkus.preorderSkuId1Seller21+":2",OMSTCConstants.PreOrderSkus.normalSkuId1Seller21+":2"};
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.PreOrderSkus.preorderSkuId1Seller21+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID+":1",
				OMSTCConstants.PreOrderSkus.normalSkuId1Seller21+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":1000:0:"+vectorSellerID+":1"},"ON_HAND");
        imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.PreOrderSkus.preorderSkuId1Seller21+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID,
        		OMSTCConstants.PreOrderSkus.normalSkuId1Seller21+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":1000:0:"+vectorSellerID},"ON_HAND");
        
        String orderID =  end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false,"",true);
	    omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
	    
	    //cancel order
	    OrderResponse cancellationRes = omsServiceHelper.cancelOrder(orderID, "1", login, "TestOrder Cancellation");
	    verifyOrderCancellationStatus(cancellationRes);
	}
	
	@Test(enabled = true,groups={"regression","preorder"},description="TC028:Place order with Madura items and PreorderItems and verify cancel Line and cancel Order.")
	public void cancleOrderFollowedByCancelLineWithPreOrderItemAndMaduraItem() throws Exception{
		sft = new SoftAssert();
		
		String skuId[] = { OMSTCConstants.PreOrderSkus.preorderSkuId1Seller21+":2",OMSTCConstants.PreOrderSkus.normalSkuId1Seller21+":2"};
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.PreOrderSkus.preorderSkuId1Seller21+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID+":1",
				OMSTCConstants.PreOrderSkus.normalSkuId1Seller21+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":1000:0:"+vectorSellerID+":1"},"ON_HAND");
        imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.PreOrderSkus.preorderSkuId1Seller21+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID,
        		OMSTCConstants.PreOrderSkus.normalSkuId1Seller21+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":1000:0:"+vectorSellerID},"ON_HAND");
        
        String orderID =  end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false,"",true);
	    omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
	    OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
	    
	    //cancel first Line
	    String orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();
	    List<OrderLineEntry> orderLineEntries = omsServiceHelper.getOrderLineEntries(orderReleaseId);
        String lineID = ""+orderLineEntries.get(0).getId();

        OrderReleaseResponse cancellationRes = omsServiceHelper.cancelLine(orderReleaseId, login, new String[] {lineID +":1"}, 39);
        verifyLineCancellationStatus(omsServiceHelper.getOrderLineEntries(orderReleaseId).get(0),cancellationRes);
	    //cancel order
	    OrderResponse cancellationOrderRes = omsServiceHelper.cancelOrder(orderID, "1", login, "TestOrder Cancellation");
	    verifyOrderCancellationStatus(cancellationOrderRes);
	}


	@Test(enabled = true,groups={"regression","preorder"},description="TC029:Place order with Madura items and PreorderItems and verify cancel release.")
	public void cancleReleaseWithPreOrderItemAndMaduraItem() throws Exception{
		String skuId[] = { OMSTCConstants.PreOrderSkus.preorderSkuId1Seller21+":2",OMSTCConstants.PreOrderSkus.normalSkuId1Seller21+":2"};
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.PreOrderSkus.preorderSkuId1Seller21+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID+":1",
				OMSTCConstants.PreOrderSkus.normalSkuId1Seller21+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":1000:0:"+vectorSellerID+":1"},"ON_HAND");
        imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.PreOrderSkus.preorderSkuId1Seller21+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID,
        		OMSTCConstants.PreOrderSkus.normalSkuId1Seller21+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":1000:0:"+vectorSellerID},"ON_HAND");
        
        String orderID =  end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false,"",true);
	    omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
	    OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
	    
	    //cancel first release
	    String orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();
	    
	    OrderReleaseResponse cancellationRes = omsServiceHelper.cancelOrderRelease(orderReleaseId, "1", login, "TestOrder Cancellation");
	    verifyReleaseCancellationStatus(cancellationRes,orderReleaseId);
        
        //cancel second release
        orderReleaseId = orderEntry.getOrderReleases().get(1).getId().toString();
        cancellationRes = omsServiceHelper.cancelOrderRelease(orderReleaseId, "1", login, "TestOrder Cancellation");
        verifyReleaseCancellationStatus(cancellationRes,orderReleaseId);
     }
	
	@Test(enabled = true,groups={"regression","preorder"},description="TC030:Place order with TnB,JIT and PreorderItems and verify cancel Line and cancelOrder.")
	public void cancleOrderFollowedbyCancelLineWithPreOrderItemTnBItemAndJITItem() throws Exception{
		sft = new SoftAssert();
		
		String skuId[] = { OMSTCConstants.PreOrderSkus.preorderSkuId1Seller21+":2",
				OMSTCConstants.PreOrderSkus.normalSkuId1TnBSeller21+":2",
				OMSTCConstants.PreOrderSkus.normalSkuId1JITSeller21+":2"};
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.PreOrderSkus.preorderSkuId1Seller21+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID+":1",
				OMSTCConstants.PreOrderSkus.normalSkuId1TnBSeller21+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":1000:0:"+vectorSellerID+":1",
				OMSTCConstants.PreOrderSkus.normalSkuId1JITSeller21+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":1000:0:"+vectorSellerID+":1"},"ON_HAND");
        imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.PreOrderSkus.preorderSkuId1Seller21+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID,
        		OMSTCConstants.PreOrderSkus.normalSkuId1TnBSeller21+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":1000:0:"+vectorSellerID,
        		OMSTCConstants.PreOrderSkus.normalSkuId1JITSeller21+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":1000:0:"+vectorSellerID},"ON_HAND");
        
        String orderID =  end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false,"",true);
	    omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
	    OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
	    
	    //cancel first Line
	    String orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();
	    List<OrderLineEntry> orderLineEntries = omsServiceHelper.getOrderLineEntries(orderReleaseId);
        String lineID = ""+orderLineEntries.get(0).getId();

        OrderReleaseResponse cancellationRes = omsServiceHelper.cancelLine(orderReleaseId, login, new String[] {lineID +":1"}, 39);
        verifyLineCancellationStatus(omsServiceHelper.getOrderLineEntries(orderReleaseId).get(0),cancellationRes);

	    //cancel order
	    OrderResponse cancellationOrderRes = omsServiceHelper.cancelOrder(orderID, "1", login, "TestOrder Cancellation");
        verifyOrderCancellationStatus(cancellationOrderRes);
     }
	
	@Test(enabled = true,groups={"regression","preorder"},description="TC031:Place order with TnB,JIT and PreorderItems and verify cancel release.")
	public void cancleReleaseWithPreOrderItemTnBItemAndJITItem() throws Exception{
		sft = new SoftAssert();
		
		String skuId[] = { OMSTCConstants.PreOrderSkus.preorderSkuId1Seller21+":2",
				OMSTCConstants.PreOrderSkus.normalSkuId1TnBSeller21+":2",
				OMSTCConstants.PreOrderSkus.normalSkuId1JITSeller21+":2"};
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.PreOrderSkus.preorderSkuId1Seller21+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID+":1",
				OMSTCConstants.PreOrderSkus.normalSkuId1TnBSeller21+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":1000:0:"+vectorSellerID+":1",
				OMSTCConstants.PreOrderSkus.normalSkuId1JITSeller21+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":1000:0:"+vectorSellerID+":1"},"ON_HAND");
        imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.PreOrderSkus.preorderSkuId1Seller21+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID,
        		OMSTCConstants.PreOrderSkus.normalSkuId1TnBSeller21+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":1000:0:"+vectorSellerID,
        		OMSTCConstants.PreOrderSkus.normalSkuId1JITSeller21+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":1000:0:"+vectorSellerID},"ON_HAND");
        
        String orderID =  end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false,"",true);
	    omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
	    OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
	    
	  //cancel first release
	    String orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();
	    
	    OrderReleaseResponse cancellationRes = omsServiceHelper.cancelOrderRelease(orderReleaseId, "1", login, "TestOrder Cancellation");
        verifyReleaseCancellationStatus(cancellationRes, orderReleaseId);
        
        //cancel second release
        orderReleaseId = orderEntry.getOrderReleases().get(1).getId().toString();
        cancellationRes = omsServiceHelper.cancelOrderRelease(orderReleaseId, "1", login, "TestOrder Cancellation");
        verifyReleaseCancellationStatus(cancellationRes, orderReleaseId);
        
        //cancel third release
        orderReleaseId = orderEntry.getOrderReleases().get(2).getId().toString();
        cancellationRes = omsServiceHelper.cancelOrderRelease(orderReleaseId, "1", login, "TestOrder Cancellation");
        verifyReleaseCancellationStatus(cancellationRes, orderReleaseId);
	}
	
	@Test(enabled = true,groups={"regression","preorder"},description="TC032:Place order with preorderItem and NormalItem with multipleqty and mark order DL.")
	public void markorderDLWithPreOrderItemAndNormalItem() throws Exception{
		sft = new SoftAssert();
		
		String skuId[] = { OMSTCConstants.PreOrderSkus.preorderSkuId1Seller21+":2",OMSTCConstants.PreOrderSkus.normalSkuId1Seller21+":2"};
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.PreOrderSkus.preorderSkuId1Seller21+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID+":1",
				OMSTCConstants.PreOrderSkus.normalSkuId1Seller21+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":1000:0:"+vectorSellerID+":1"},"ON_HAND");
        imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.PreOrderSkus.preorderSkuId1Seller21+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID,
        		OMSTCConstants.PreOrderSkus.normalSkuId1Seller21+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":1000:0:"+vectorSellerID},"ON_HAND");
        
        String orderID =  end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false,"",true);
	    omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
	    OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
	    
	    //mark order DL
	    
        sft.assertAll();
     }
	
	@Test(enabled = true,groups={"regression","preorder"},description="TC033:Place order with preorderItem and NormalItem with multipleqty and mark order LOST.")
	public void markorderLOSTWithPreOrderItemAndNormalItem() throws Exception{
		sft = new SoftAssert();
		
		String skuId[] = { OMSTCConstants.PreOrderSkus.preorderSkuId1Seller21+":2",OMSTCConstants.PreOrderSkus.normalSkuId1Seller21+":2"};
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.PreOrderSkus.preorderSkuId1Seller21+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID+":1",
				OMSTCConstants.PreOrderSkus.normalSkuId1Seller21+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":1000:0:"+vectorSellerID+":1"},"ON_HAND");
        imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.PreOrderSkus.preorderSkuId1Seller21+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID,
        		OMSTCConstants.PreOrderSkus.normalSkuId1Seller21+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":1000:0:"+vectorSellerID},"ON_HAND");
        
        String orderID =  end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false,"",true);
	    omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
	    OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
	    
	    //mark order LOST
	        
        sft.assertAll();
     }
	
	@Test(enabled = true,groups={"regression","preorder"},description="TC034:Place order with preorderItem and NormalItem with multipleqty and mark order RTO.")
	public void markorderRTOWithPreOrderItemAndNormalItem() throws Exception{
		sft = new SoftAssert();
		
		String skuId[] = { OMSTCConstants.PreOrderSkus.preorderSkuId1Seller21+":2",OMSTCConstants.PreOrderSkus.normalSkuId1Seller21+":2"};
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.PreOrderSkus.preorderSkuId1Seller21+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID+":1",
				OMSTCConstants.PreOrderSkus.normalSkuId1Seller21+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":1000:0:"+vectorSellerID+":1"},"ON_HAND");
        imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.PreOrderSkus.preorderSkuId1Seller21+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID,
        		OMSTCConstants.PreOrderSkus.normalSkuId1Seller21+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":1000:0:"+vectorSellerID},"ON_HAND");
        
        String orderID =  end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false,"",true);
	    omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
	    OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
	    
	    //mark order RTO
	        
        sft.assertAll();
     }
	
	@Test(enabled = true,groups={"regression","preorder"},description="TC035:Place order with preorderItem multipleqty and create exchange for the order.")
	public void createExchangeWithPreOrderItem() throws Exception{
		sft = new SoftAssert();
		
		String skuId[] = { OMSTCConstants.PreOrderSkus.preorderSkuId1Seller21+":2"};
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.PreOrderSkus.preorderSkuId1Seller21+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID+":1"},"ON_HAND");
        imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.PreOrderSkus.preorderSkuId1Seller21+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID},"ON_HAND");
        
        String orderID =  end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false,"",true);
	    omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
	    OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
	    String orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();
	    OrderLineEntry orderLineEntry = omsServiceHelper.getOrderLineEntries(orderReleaseId).get(0);
        String lineID = ""+orderLineEntry.getId();
	    //create Exchange
	    ExchangeOrderResponse exchangeOrderResponse =(ExchangeOrderResponse) ppsServiceHelper.createExchange(""+orderID, lineID, "DNL", 2, OMSTCConstants.PreOrderSkus.preorderSkuId1Seller21);
        sft.assertTrue(exchangeOrderResponse.getSuccess(),"There should be success but Actual:"+exchangeOrderResponse.getSuccess());
        sft.assertNotNull(exchangeOrderResponse.getExchangeOrderId(),"Exchange order should not be null but Actual:"+exchangeOrderResponse.getExchangeOrderId());
        sft.assertNotEquals(exchangeOrderResponse.getExchangeOrderId(), orderID,"Exchange order should not be same as orderId");
        sft.assertAll();
	        
        sft.assertAll();
     }
	
	@Test(enabled = true,groups={"regression","preorder"},description="TC036:Place order with preorderItem multipleqty and create exchange for already exchanged order.")
	public void createExchangeForAlreadyCreatedExchangeOrderWithPreOrderItem() throws Exception{
		sft = new SoftAssert();
		
		String skuId[] = { OMSTCConstants.PreOrderSkus.preorderSkuId1Seller21+":2"};
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.PreOrderSkus.preorderSkuId1Seller21+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID+":1"},"ON_HAND");
        imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.PreOrderSkus.preorderSkuId1Seller21+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID},"ON_HAND");
        
        String orderID =  end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false,"",true);
	    omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
	    OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
	    
	    //create Exchange
	    
	    //create exchange for already created exchange order
	        
        sft.assertAll();
     }
	
	@Test(enabled = true,groups={"regression","preorder"},description="TC037:Place order with preorderItem multipleqty and create return for the order.")
	public void createReturnWithPreOrderItem() throws Exception{
		sft = new SoftAssert();
		
		String skuId[] = { OMSTCConstants.PreOrderSkus.preorderSkuId1Seller21+":2"};
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.PreOrderSkus.preorderSkuId1Seller21+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID+":1"},"ON_HAND");
        imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.PreOrderSkus.preorderSkuId1Seller21+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID},"ON_HAND");
        
        String orderID =  end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false,"",true);
	    omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
	    OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
	    
	    //create return
	        
        sft.assertAll();
     }
	
	@Test(enabled = true,groups={"regression","preorder"},description="TC038:Place order with preorderItem multipleqty and create return for already returned order.")
	public void createReturnForAlreadyCreatedReturnOrderWithPreOrderItem() throws Exception{
		sft = new SoftAssert();
		
		String skuId[] = { OMSTCConstants.PreOrderSkus.preorderSkuId1Seller21+":2"};
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.PreOrderSkus.preorderSkuId1Seller21+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID+":1"},"ON_HAND");
        imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.PreOrderSkus.preorderSkuId1Seller21+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID},"ON_HAND");
        
        String orderID =  end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false,"",true);
	    omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
	    OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
	    
	    //create Return
	    
	    //create return for already created return order
	        
        sft.assertAll();
     }
	
	@Test(enabled = true,groups={"regression","preorder"},description="TC039:Create exchange for preorderItem and Normal Item.")
	public void createExchangeWithPreOrderItemAndNormalItem() throws Exception{
		sft = new SoftAssert();
		
		String skuId[] = { OMSTCConstants.PreOrderSkus.preorderSkuId1Seller21+":2",OMSTCConstants.PreOrderSkus.normalSkuId1Seller21+":2"};
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.PreOrderSkus.preorderSkuId1Seller21+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID+":1",
				OMSTCConstants.PreOrderSkus.normalSkuId1Seller21+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":1000:0:"+vectorSellerID+":1"},"ON_HAND");
        imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.PreOrderSkus.preorderSkuId1Seller21+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID,
        		OMSTCConstants.PreOrderSkus.normalSkuId1Seller21+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":1000:0:"+vectorSellerID},"ON_HAND");
        
        String orderID =  end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false,"",true);
	    omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
	    OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
	    
	    //create Exchange
	        
        sft.assertAll();
     }
	
	@Test(enabled = true,groups={"regression","preorder"},description="TC040:Create return for preorderItem and Normal Item.")
	public void createReturnWithPreOrderItemAndNormalItem() throws Exception{
		sft = new SoftAssert();
		
		String skuId[] = { OMSTCConstants.PreOrderSkus.preorderSkuId1Seller21+":2",OMSTCConstants.PreOrderSkus.normalSkuId1Seller21+":2"};
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.PreOrderSkus.preorderSkuId1Seller21+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID+":1",
				OMSTCConstants.PreOrderSkus.normalSkuId1Seller21+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":1000:0:"+vectorSellerID+":1"},"ON_HAND");
        imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.PreOrderSkus.preorderSkuId1Seller21+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID,
        		OMSTCConstants.PreOrderSkus.normalSkuId1Seller21+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":1000:0:"+vectorSellerID},"ON_HAND");
        
        String orderID =  end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false,"",true);
	    omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
	    OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
	    
	    //create return
	        
        sft.assertAll();
     }
	
	@Test(enabled = true,groups={"regression","preorder"},description="TC041:Place order with JIT normal items and PreorderItems and verify order is in WP status.")
	public void orderWithPreOrderAndJITItem() throws Exception{
		sft = new SoftAssert();
		
		String skuId[] = { OMSTCConstants.PreOrderSkus.preorderSkuId1Seller21+":1",OMSTCConstants.PreOrderSkus.normalSkuId1JITSeller21+":1"};
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.PreOrderSkus.preorderSkuId1Seller21+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID+":1",
				OMSTCConstants.PreOrderSkus.normalSkuId1JITSeller21+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID+":1"},"ON_HAND");
        imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.PreOrderSkus.preorderSkuId1Seller21+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID,
				OMSTCConstants.PreOrderSkus.normalSkuId1JITSeller21+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID},"ON_HAND");
        
        String orderID =  end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false,"",true);
	    omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
	    OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
	    sft.assertEquals(2,orderEntry.getOrderReleases().size(),"The order release should be two as there are 3 types of items: Actual :" +orderEntry.getOrderReleases().size());
	    sft.assertAll();
	}

	@Test(enabled = true,groups={"regression","preorder"},description="TC042:Place order with Madura items and PreorderItems and verify order is in WP status.")
	public void orderWithPreOrderAndMaduraItem() throws Exception{
		sft = new SoftAssert();
		
		String skuId[] = { OMSTCConstants.PreOrderSkus.preorderSkuId1Seller21+":1",OMSTCConstants.PreOrderSkus.maduraSKu1+":1"};
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.PreOrderSkus.preorderSkuId1Seller21+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID+":1",
				OMSTCConstants.PreOrderSkus.maduraSKu1+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID+":1"},"ON_HAND");
        imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.PreOrderSkus.preorderSkuId1Seller21+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID,
				OMSTCConstants.PreOrderSkus.maduraSKu1+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID},"ON_HAND");
        
        String orderID =  end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false,"",true);
	    omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
	    OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
	    sft.assertEquals(2,orderEntry.getOrderReleases().size(),"The order release should be two as there are 3 types of items: Actual :" +orderEntry.getOrderReleases().size());
	    sft.assertAll();
	}

	@Test(enabled = true,groups={"regression","preorder"},description="TC043:Place order with Tnb items and PreorderItems and verify order is in WP status.")
	public void orderWithPreOrderAndTnBItem() throws Exception{
		sft = new SoftAssert();
		
		String skuId[] = { OMSTCConstants.PreOrderSkus.preorderSkuId1Seller21+":1",OMSTCConstants.PreOrderSkus.normalSkuId1TnBSeller21+":1"};
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.PreOrderSkus.preorderSkuId1Seller21+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID+":1",
				OMSTCConstants.PreOrderSkus.normalSkuId1TnBSeller21+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID+":1"},"ON_HAND");
        imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.PreOrderSkus.preorderSkuId1Seller21+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID,
				OMSTCConstants.PreOrderSkus.normalSkuId1TnBSeller21+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID},"ON_HAND");
        
        String orderID =  end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false,"",true);
	    omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
	    OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
	    sft.assertEquals(2,orderEntry.getOrderReleases().size(),"The order release should be two as there are 3 types of items: Actual :" +orderEntry.getOrderReleases().size());
	    sft.assertAll();
	}
	
	//Passed
	@Test(enabled = true, groups = { "regression",
			"preorder" }, description = "TC044:Place singleLine order with Past Launch Date and verify fulfilment Type and Launch Date field for preorder in bounty sent from cart.")
	public void singleLinePreOrderWithPastLaunchDate() throws Exception {
		sft = new SoftAssert();

		String skuId[] = { OMSTCConstants.PreOrderSkus.preorderSkuId5Seller21 + ":1" };
		atpServiceHelper.updateInventoryDetailsForSeller(
				new String[] { OMSTCConstants.PreOrderSkus.preorderSkuId5Seller21 + ":"
						+ OMSTCConstants.WareHouseIds.warehouseId36_BW + ":1000:0:" + vectorSellerID + ":1" },
				"ON_HAND");
		imsServiceHelper
				.updateInventoryForSeller(
						new String[] { OMSTCConstants.PreOrderSkus.preorderSkuId5Seller21 + ":"
								+ OMSTCConstants.WareHouseIds.warehouseId36_BW + ":1000:0:" + vectorSellerID },
						"ON_HAND");

		String orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "",
				false, false, false, "", false);
		omsServiceHelper.checkReleaseStatusForOrder(orderID, "WP");
		OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
		String storeOrderId = orderEntry.getStoreOrderId();
		verifyPreOrderFieldsInBounty(storeOrderId,
				new String[] { OMSTCConstants.PreOrderSkus.preorderSkuId5Seller21 + ":" + skuType.NORMAL });
		checkLaunchDateIsSameInOMSAndBounty(storeOrderId);
		sft.assertAll();
	}
	private void verifyLineCancellationStatus(OrderLineEntry orderLineEntry, OrderReleaseResponse cancellationRes) {
		sft = new SoftAssert();
		sft.assertEquals(cancellationRes.getStatus().getStatusType(), Type.SUCCESS,"There should be success Actual:"+cancellationRes.getStatus().getStatusType());
        sft.assertEquals(orderLineEntry.getStatus(), "IC","Verify first line should be cancelled but Actual:"+orderLineEntry.getStatus());
        sft.assertAll();
	}
	private void verifyReleaseCancellationStatus(OrderReleaseResponse cancellationRes,String orderReleaseId){
		sft = new SoftAssert();
		sft.assertEquals(cancellationRes.getStatus().getStatusCode(), 1006,"verify status code after cancel");
        sft.assertEquals(cancellationRes.getStatus().getStatusType(), Type.SUCCESS,"verify response after cancel");
        sft.assertEquals(cancellationRes.getData().get(0).getStatus(), "F","veiry release status after cancel");
        sft.assertTrue(omsServiceHelper.validateReleaseStatusInOMS(orderReleaseId, "F", 10),"Release is not in F status");
        sft.assertAll();   
	}
	private void verifyOrderCancellationStatus(OrderResponse cancellationRes){
		sft = new SoftAssert();
		sft.assertEquals(cancellationRes.getStatus().getStatusCode(), 1002,"Verify status code after cancel");
		sft.assertEquals(cancellationRes.getStatus().getStatusType(), Type.SUCCESS,"Verify response message after cancel");
		sft.assertEquals(cancellationRes.getData().get(0).getOrderReleases().get(0).getStatus(), "F","Verify release status after cancel");
		sft.assertAll();
	}
}
