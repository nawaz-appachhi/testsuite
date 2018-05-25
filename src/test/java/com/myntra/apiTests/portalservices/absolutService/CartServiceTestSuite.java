package com.myntra.apiTests.portalservices.absolutService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import org.apache.log4j.Logger;
import org.testng.AssertJUnit;
import org.testng.annotations.AfterGroups;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import com.jayway.jsonpath.JsonPath;
import com.myntra.apiTests.common.APINAME;
import com.myntra.apiTests.common.Myntra;
import com.myntra.apiTests.common.ServiceType;
import com.myntra.apiTests.end2end.End2EndHelper;
import com.myntra.apiTests.erpservices.atp.ATPServiceHelper;
import com.myntra.apiTests.erpservices.bounty.BountyServiceHelper;
import com.myntra.apiTests.erpservices.ims.IMSServiceHelper;
import com.myntra.apiTests.erpservices.notificaton.NotificationServiceHelper;
import com.myntra.apiTests.erpservices.oms.Test.AdjustInventory;
import com.myntra.apiTests.portalservices.loyalitypointsservice.LoyalityPointsServiceHelper;
import com.myntra.apiTests.portalservices.myntcashservice.MyntCashServiceHelper;
import com.myntra.apiTests.portalservices.styleservice.MaximusHelper;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.PayloadType;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.qnotifications.Notifications_validation;
import com.myntra.test.commons.testbase.BaseTest;
import net.minidev.json.JSONArray;


public class CartServiceTestSuite extends BaseTest implements AbsolutConstants{
	public CartServiceTestSuite(){
		new CartServiceTestsDP();
	}
	static Logger log = Logger.getLogger(CartServiceTestSuite.class);
	static AbsolutHelper absolutHelper = new AbsolutHelper();
	static LoyalityPointsServiceHelper loyalityPointsServiceHelper = new LoyalityPointsServiceHelper();
	static MyntCashServiceHelper myntrCashServiceHelper = new MyntCashServiceHelper();
	NotificationServiceHelper notificationServiceHelper = new NotificationServiceHelper();
    End2EndHelper end2EndHelper = new End2EndHelper();
    BountyServiceHelper bountyServiceHelper = new BountyServiceHelper();
    ATPServiceHelper atpServiceHelper = new ATPServiceHelper();
    IMSServiceHelper imsServiceHelper = new IMSServiceHelper();
    Notifications_validation notif = new Notifications_validation();
	static Initialize init = new Initialize("./Data/configuration");
	//int[] styles ={1566};
	
	String shippingChargesAmount, shippingChargesCartLimit, 
	fraudUserShippingCharges,giftwrapCharges, loyaltyMaxPerOff,valueShippingDiscount;
	
	/*
	@BeforeSuite(alwaysRun = true)
	private void checkAndUpdateSwitchConfig(){
		RequestGenerator getSwitchConfigReqGen = absolutHelper.getSwitchConfigValues("configuration-widget");
		AssertJUnit.assertEquals("getSwitchConfigAPI is not working", "200", getSwitchConfigReqGen.response.getStatus());
		double shippingChargesAmount = JsonPath.read(getSwitchConfigReqGen.respvalidate.returnresponseasstring(), 
				"$.data[\"shipping.charges.amount\"]");
		if(shippingChargesAmount <=0){
			RequestGenerator updateConfigReqGen = absolutHelper.updateSwitchConfigValues("configuration-widget", "shipping.charges.amount");
			System.out.println("\ngetConfig API response :\n\n"+updateConfigReqGen.respvalidate.returnresponseasstring());
			AssertJUnit.assertEquals("switch getConfig API is not working",
				Integer.parseInt("200"), updateConfigReqGen.response.getStatus());
			
			
			
		}
	}*/
	
//	@BeforeSuite(alwaysRun = true)
//	public void prepData_updateStylesInIMSATPAndReindex() throws Exception{
//		String [] inventorydetails={"4953:36:12:0:21"};
////		String [] inventorydetails={"5092:36:12:0:21","5093:36:12:0:21","5094:36:12:0:21","5095:36:12:0:21",
////				"4953:36:12:0:21","4954:36:12:0:21","5265:36:12:0:21","5266:36:12:0:21"};
////        //skuid:warehouse_id1,warehouse_id2,warehouse_id3:inventory_count:block_order_count:sellerId
////		String[] inventorydetails = {"3913:36:10000:0:21","3914:36:10000:0:21","3915:36:10000:0:21","3916:36:10000:0:21","3917:36:10000:0:1","3918:36:10000:0:21",
////				"3919:36:10000:0:21","3920:36:10000:0:1","3870:28:10000:0:21","3869:36:10000:0:21","3868:36:10000:0:21","3867:36:10000:0:21","3866:36:10000:0:21",
////				"3876:28:10000:0:25","3875:36:10000:0:25","3874:28:10000:0:25","3873:36:10000:0:25","3872:36:10000:0:25","3871:28:10000:0:25","3881:36:10000:0:19","3880:19:10000:0:19",
////				"3879:36:10000:0:19","3878:28:10000:0:19","3877:36:10000:0:19","895234:46:10000:0:18","1251868:45:10000:0:5"};
//        
//		AdjustInventory adjustInventory = new AdjustInventory();
//        adjustInventory.updateInventoryInIMSAndATP(inventorydetails,"ON_HAND");
//	}


//    @BeforeSuite(alwaysRun = true)
//    public void testBeforeSuite() throws JAXBException, IOException {
//    	StyleServiceApiHelper styleServiceApiHelper = new StyleServiceApiHelper();
//        end2EndHelper.updateToolsApplicationProperties("cod.limit.range", "1-200000", "oms");
//        end2EndHelper.updateToolsApplicationProperties("bounty.get_default_address", "true", "oms");
//        atpServiceHelper.updateInventoryDetails(new String[] { "4953:28,36:100:0"},"ON_HAND");
//        imsServiceHelper.updateInventory(new String[] { "4953:28:100:0"},"ON_HAND");
//        bountyServiceHelper.refreshBountyApplicationPropertyCache();
//        styleServiceApiHelper.styleReindexForStyleIDsPost(styles);
//        
//    }
		
	    @BeforeSuite(alwaysRun = true)
		public void setUp(){
			RequestGenerator getSwitchData = absolutHelper.getSwitchConfigValues("cart");
			AssertJUnit.assertEquals("Error fetching config data", "200", 
					getSwitchData.respvalidate.NodeValue(AbsolutNodes.META_CODE.toString(), true));
			int shippingChargesCartLimitFromSwitch = JsonPath.read(getSwitchData.respvalidate.returnresponseasstring(), 
					"$.data[\"shipping.charges.cartlimit\"]");
			shippingChargesCartLimit = String.valueOf(shippingChargesCartLimitFromSwitch);
			int shippingChargesAmountFromSwitch = JsonPath.read(getSwitchData.respvalidate.returnresponseasstring(), 
					"$.data[\"shipping.charges.amount\"]");
			shippingChargesCartLimit = String.valueOf(shippingChargesAmountFromSwitch);
			int fraudUserShippingChargesFromSwitch =  JsonPath.read(getSwitchData.respvalidate.returnresponseasstring(), 
					"$.data[\"shipping.charges.returnabuser\"]");
			fraudUserShippingCharges = String.valueOf(fraudUserShippingChargesFromSwitch);
			int valueShippingDiscountFromSwitch = JsonPath.read(getSwitchData.respvalidate.returnresponseasstring(), 
					"$.data[\"myntra.valueShipping.charges\"]");
			valueShippingDiscount = String.valueOf(valueShippingDiscountFromSwitch);
			int giftwrapChargesFromSwitch = JsonPath.read(getSwitchData.respvalidate.returnresponseasstring(),
					"$.data[\"giftwrap.charges\"]");
			giftwrapCharges = String.valueOf(giftwrapChargesFromSwitch);
			int loyaltyMaxPerOffFromSwitch = JsonPath.read(getSwitchData.respvalidate.returnresponseasstring(), 
					"$.data[\"loyaltypoints.maxPercentOfCart\"]");
			loyaltyMaxPerOff = String.valueOf(loyaltyMaxPerOffFromSwitch);
		}
	    
	/**
	 * This Test Case used to invoke CheckoutService addItemToCart API and
	 * verify for success response code(200)
	 * 
	 * @param userName
	 * @param password
	 * @param addItemQty
	 * @param addOperation
	 * @param successRespCode
	 */
	@Test(groups = { "San","Sanity", "FoxSanity", "Regression", "MiniRegression",
			"ExhaustiveRegression" }, dataProvider = "addItemToCart_verifyAPIIsUpDP",dataProviderClass= CartServiceTestsDP.class, description = "1. Perform search and extract styleIds 2.Check if searched styleIds exists in user's cart or not. "
					+ "\n 3. login user using IDP service call. 4.hit addItemToCart API giving skuId which doesnt exists in cart"
					+ "\n 5.Check the service response must be 200")
	public void addItemToCart_verifyAPIIsUp(String userName, String password,
			String skuId, String successRespCode, String successStatusMsg, String successStatusType) {
		long startTime = Calendar.getInstance().getTimeInMillis();
		String tokens[] = absolutHelper.idp_GetTokens(userName, password);
		AssertJUnit.assertEquals(successStatusMsg, tokens[2]);
		RequestGenerator addItemToCartReqGen = absolutHelper.addItemToCart(tokens[0],skuId, "1", "ADD");
		System.out.println("\nPrinting CheckoutService addItemToCart API response:\n\n"
			+addItemToCartReqGen.respvalidate.returnresponseasstring());
	    
	    AssertJUnit.assertEquals("addItemToCart API is not working",
			Integer.parseInt(successRespCode), addItemToCartReqGen.response.getStatus());
	    AssertJUnit.assertTrue("addItemToCart API response status message is not a success status message",
				addItemToCartReqGen.respvalidate.NodeValue(AbsolutNodes.STATUS_MESSAGE.toString(), true).contains(successStatusMsg));
		AssertJUnit.assertTrue("addItemToCart API response status type is not a success status type",
			addItemToCartReqGen.respvalidate.NodeValue(AbsolutNodes.STATUS_TYPE.toString(), true).contains(successStatusType));
			
		absolutHelper.performSignOutOperation(userName, tokens[1]);
        long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;

		System.out.println("\nTime taken to execute - TestCase - addItemToCart_verifyAPIIsUp : "+ timeTaken + " seconds\n");
		
	}
	
	/**
	 * This Test Case used to invoke fetchCart API and
	 * verify for success response(200)
	 * 
	 * @param userName
	 * @param password
	 * @param successRespCode
	 */
	@Test(groups = {"San", "Smoke", "Sanity", "FoxSanity", "ProdSanity", "Regression", "MiniRegression",
			"ExhaustiveRegression" }, dataProvider = "fetchCart_verifyAPIIsUpDP",dataProviderClass= CartServiceTestsDP.class
			, description = "1. hit IDP service to login"
					+ "\n 2. hit fetchCart API" + "\n 3.Check the service response must be 200")
	public void fetchCart_verifyAPIIsUp(String userName, String password,
			String successRespCode, String successStatusMsg, String successStatusType) 
	{
		long startTime = Calendar.getInstance().getTimeInMillis();
		String tokens[] = absolutHelper.idp_GetTokens(userName, password);
		AssertJUnit.assertEquals(successStatusMsg, tokens[2]);
		System.out.println("xid is:"+tokens[0]);
		RequestGenerator fetchCartReqGen = absolutHelper.fetchCart(tokens[0]);
		System.out.println("\nPrinting CheckoutService fetchCart API response :\n\n"
			+ fetchCartReqGen.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("fetchCart API is not working",
			Integer.parseInt(successRespCode), fetchCartReqGen.response.getStatus());
		AssertJUnit.assertTrue("fetchCart API response status message is not a success status message",
			fetchCartReqGen.respvalidate.NodeValue(AbsolutNodes.STATUS_MESSAGE.toString(), true).contains(successStatusMsg));
		AssertJUnit.assertTrue("fetchCart API response status type is not a success status type",
			fetchCartReqGen.respvalidate.NodeValue(AbsolutNodes.STATUS_TYPE.toString(), true).contains(successStatusType));
		absolutHelper.performSignOutOperation(userName, tokens[1]);
		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;

		System.out.println("\nTime taken to execute - TestCase - fetchCart_verifyAPIIsUp : "
			+ timeTaken + " seconds\n");

	}
	/**
	 * This Test Case used to invoke CheckoutService clearCart API and verify
	 * for success response(200)
	 * 
	 * @param userName
	 * @param password
	 * @param successRespCode
	 */
	@Test(groups = { "San","Sanity", "FoxSanity", "Regression", "MiniRegression",
			"ExhaustiveRegression" }, dataProvider = "clearCart_verifyAPIIsUpDP",dataProviderClass= CartServiceTestsDP.class, description = "1. hit IDP service and login the user. "
					+ "\n 2. hit fetchCart API. \n 3. if user's cart is empty then first add items to cart(\n a.hit Search Service API and extract some skuIDs"
					+ "\n b. Check if searched styleIds exists in user's cart or not. \n c. hit addItemToCart API giving skuId which doesnt exists in cart)"
					+ "\n 4. hit ClearCart API " + "\n 5.Check the service response must be 200")
	public void clearCart_verifyAPIIsUp(String userName, String password) 
	{
		//userName = "NegCartAutoUser4@myntra.com";
		long startTime = Calendar.getInstance().getTimeInMillis();
		String tokens[] = absolutHelper.idp_GetTokens(userName, password);
		AssertJUnit.assertEquals(SUCCESS_STATUS_MSG, tokens[2]);
		System.out.println("xid is:"+tokens[0]);
		RequestGenerator clearCartReqGen = absolutHelper.clearCart(tokens[0]);
		System.out.println("\nPrinting clearCart API response :\n\n"
				+ clearCartReqGen.respvalidate.returnresponseasstring());
		
		AssertJUnit.assertEquals("clearCart API is not working", Integer.parseInt(SUCCESS_STATUS_RESP),
				clearCartReqGen.response.getStatus());
		absolutHelper.performSignOutOperation(userName, tokens[1]);
		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;

		System.out.println("\nTime taken to execute - TestCase - clearCart_verifyAPIIsUp : "
			+ timeTaken + " seconds\n");
	}
	

	/**
	 * This Test Case used to invoke CheckoutService updateItemInCart API and
	 * verify for success response(200)
	 * 
	 * @param userName
	 * @param password
	 * @param itemQtyToUpdate
	 * @param updateOpn
	 * @param successRespCode
	 */
	@Test(groups = { "San","Sanity", "FoxSanity", "Regression", "MiniRegression",
			"ExhaustiveRegression" }, dataProvider = "updateItemInCart_verifyAPIIsUpDP",dataProviderClass= CartServiceTestsDP.class, description = "1. hit IDP service and login the user. "
					+ "\n 2. hit fetchCart API. \n 3. if user's cart is empty then first add items to cart(\n a.hit Search Service API and extract some skuIDs"
					+ "\n b. Check if searched styleIds exists in user's cart or not. \n c. hit addItemToCart API giving skuId which doesnt exists in cart)"
					+ "\n 4. hit updateCart API" + "\n 5.Check the service response must be 200")
	public void updateItemInCart_verifyAPIIsUp(String userName, String password,String skuId, 
			String itemId, String successRespCode) {
		long startTime = Calendar.getInstance().getTimeInMillis();
		
		String tokens[] = absolutHelper.idp_GetTokens(userName, password);
		AssertJUnit.assertEquals(SUCCESS_STATUS_MSG, tokens[2]);
		System.out.println("xid is:"+tokens[0]);
		RequestGenerator updateItemInCartReqGen = absolutHelper.updateItemInCart(tokens[0],itemId,
			skuId, "2", "UPDATE");
		System.out.println("\nPrinting updateItemInCart API response :\n\n"
			+ updateItemInCartReqGen.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("updateItemInCart API is not working",
			Integer.parseInt(successRespCode), updateItemInCartReqGen.response.getStatus());
		absolutHelper.performSignOutOperation(userName, tokens[1]);
		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;

		System.out.println("\nTime taken to execute - TestCase - updateItemInCart_verifyAPIIsUp : "
			+ timeTaken + " seconds\n");
		
	}
	/**
	 * This Test Case used to invoke CheckoutService removeItemFrom API and
	 * verify for success response(200)
	 * 
	 * @param userName
	 * @param password
	 * @param deleteOpn
	 * @param successRespCode
	 */
	@Test(groups = {"San", "Sanity", "FoxSanity", "Regression", "MiniRegression",
			"ExhaustiveRegression" }, dataProvider = "removeItemFromCart_verifySuccessResponseDP",dataProviderClass= CartServiceTestsDP.class, description = "1. hit IDP service and login the user. "
					+ "\n 2. hit fetchCart API. \n 3. if user's cart is empty then first add items to cart(\n a.hit Search Service API and extract some skuIDs"
					+ "\n b. Check if searched styleIds exists in user's cart or not. \n c. hit addItemToCart API giving skuId which doesnt exists in cart)"
					+ "\n 4. hit removeItemFromCart API giving it itemId to be removed"
					+ "\n 5. Verify statusCode must be 200 in APIs response")
	public void removeItemFromCart_verifySuccessResponse(String userName, String password,
			String deleteOpn, String successRespCode) 
	{
		long startTime = Calendar.getInstance().getTimeInMillis();
		String tokens[] = absolutHelper.idp_GetTokens(userName, password);
		AssertJUnit.assertEquals(SUCCESS_STATUS_MSG, tokens[2]);
		System.out.println("xid is:"+tokens[0]);
		RequestGenerator fetchCartReqGen = absolutHelper.fetchCart(tokens[0]);
		System.out.println("\nPrinting CheckoutService  fetchCart API response :\n\n"
			+ fetchCartReqGen.respvalidate.returnresponseasstring());
		
		AssertJUnit.assertTrue("Error while invoking CheckoutService fetchCart API",
			fetchCartReqGen.respvalidate.NodeValue(AbsolutNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));

		if (fetchCartReqGen.respvalidate.NodeValue(AbsolutNodes.CART_TOTAL_COUNT.toString(), true).equals(EMPTY_VALUE)) {

			RequestGenerator addItemToCartReqGen = absolutHelper.addItemToCart(tokens[0],"3828", "1", ADD_OPERATION);
			System.out.println("\nPrinting CheckoutService addItemToCart API response :\n\n"
				+ addItemToCartReqGen.respvalidate.returnresponseasstring());

		}
		
		fetchCartReqGen = absolutHelper.fetchCart(tokens[0]);
		String skuId = fetchCartReqGen.respvalidate.NodeValue(AbsolutNodes.CART_ITEM_ENTRIES_SELSIZE_SKUID.toString(),
				false);
		String itemId = absolutHelper.getItemIdFromSKUId(fetchCartReqGen, skuId);

		System.out.println("\nPrinting skuId : " + skuId + " & itemId : " + itemId + " is going to removeItemFromCart\n");
		
		RequestGenerator removeItemFromCartReqGen = absolutHelper.removeItemFromCart(tokens[0],itemId,deleteOpn);
		System.out.println("\nPrinting CheckoutService removeItemFromCart API response :\n\n"
			+ removeItemFromCartReqGen.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("CheckoutService removeItemFromCart API is not working",
			Integer.parseInt(successRespCode), removeItemFromCartReqGen.response.getStatus());
		absolutHelper.performSignOutOperation(userName, tokens[1]);
		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;

		System.out.println("\nTime taken to execute - TestCase - removeItemFromCart_verifySuccessResponse : "
			+ timeTaken + " seconds\n");
	}

	/**
	 * This Test Case used to invoke CheckoutService addItemToCart API and
	 * verify for success response code(200)
	 * 
	 * @param userName
	 * @param password
	 * @param addItemQty
	 * @param addOperation
	 * @param successRespCode
	 */
	@Test(groups = { "San","Sanity", "FoxSanity", "Regression", "MiniRegression",
			"ExhaustiveRegression" }, dataProvider = "addEGiftCardToCart_verifyAPIIsUpDP",dataProviderClass= CartServiceTestsDP.class, description = "1. Perform search and extract styleIds 2.Check if searched styleIds exists in user's cart or not. "
					+ "\n 3. login user using IDP service call. 4.hit addItemToCart API giving skuId which doesnt exists in cart"
					+ "\n 5.Check the service response must be 200")
	public void addEGiftCardToCart_verifyAPIIsUp(String userName, String password,
			String successRespCode, String successStatusMsg, String successStatusType) {
		long startTime = Calendar.getInstance().getTimeInMillis();
		String tokens[] = absolutHelper.idp_GetTokens(userName, password);
		AssertJUnit.assertEquals(successStatusMsg, tokens[2]);
		System.out.println("xid is:"+tokens[0]);
		//skuId = "3828";
		RequestGenerator addItemToCartReqGen = absolutHelper.addEGiftCardToCart(tokens[0]);
		System.out.println("\nPrinting addEGiftCardToCart API response:\n\n"
			+addItemToCartReqGen.respvalidate.returnresponseasstring());
	    
	    AssertJUnit.assertEquals("addEGiftCardToCart API is not working",
			Integer.parseInt(successRespCode), addItemToCartReqGen.response.getStatus());
	    AssertJUnit.assertTrue("addEGiftCardToCart API response status message is not a success status message",
				addItemToCartReqGen.respvalidate.NodeValue(AbsolutNodes.STATUS_MESSAGE.toString(), true).contains(successStatusMsg));
		AssertJUnit.assertTrue("addEGiftCardToCart API response status type is not a success status type",
			addItemToCartReqGen.respvalidate.NodeValue(AbsolutNodes.STATUS_TYPE.toString(), true).contains(successStatusType));
			
		absolutHelper.performSignOutOperation(userName, tokens[1]);
        long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;

		System.out.println(
				"\nTime taken to execute - TestCase - addItemToCart_verifyAPIIsUp : "
						+ timeTaken + " seconds\n");
		
	}
	
	/**
	 * testcase to verify Item is actually added to cart
	 * 
	 * @param userName
	 * @param password
	 * @param addItemQty
	 * @param addOperation
	 * @param successStatusCode
	 * @param successStatusMsg
	 * @param successStatusType
	 */
	@Test(groups = {"San", "Sanity", "Regression", "MiniRegression",
			"ExhaustiveRegression" }, dataProvider = "addItemToCart_verifyItemIsAddedDP",dataProviderClass= CartServiceTestsDP.class, description = "1. Perform search and extract styleIds 2.Check if searched styleIds exists in user's cart or not. "
					+ "\n 3. login user using IDP service call. 4.hit addItemToCart API giving skuId which doesnt exists in cart"
					+ "\n 5.verify statusCode, statusMessage & statusType of API response must be of success")
	public void addItemToCart_verifyItemIsAdded(String userName,String password,String skuId,
			String successStatusCode,String successStatusMsg,String successStatusType) {
		long startTime = Calendar.getInstance().getTimeInMillis();
		String tokens[] = absolutHelper.idp_GetTokens(userName, password);
		AssertJUnit.assertEquals(successStatusMsg, tokens[2]);
		System.out.println("xid is:"+tokens[0]);
//		RequestGenerator clearCartReqGen = absolutHelper.clearCart(tokens[0]);
//		AssertJUnit.assertEquals("CheckoutService clearCart API is not working", Integer.parseInt(SUCCESS_STATUS_RESP),
//			clearCartReqGen.response.getStatus());
//		RequestGenerator fetchCart = absolutHelper.fetchCart(tokens[0]);
//		AssertJUnit.assertEquals("Items are not cleared from cart", "0",
//				fetchCart.respvalidate.NodeValue(AbsolutNodes.CART_TOTAL_COUNT.toString(), true));
//		
		RequestGenerator addItemToCartReqGen = absolutHelper.addItemToCart(tokens[0],skuId, "1", "ADD");
		System.out.println("\nPrinting CheckoutService addItemToCart API response :\n\n"
			+ addItemToCartReqGen.respvalidate.returnresponseasstring());
		RequestGenerator fetchCartReqGen = absolutHelper.fetchCart(tokens[0]);
		AssertJUnit.assertEquals("SkuId "+skuId+" is not added in cart",
				 "Exists", absolutHelper.doesSkuIDExists(fetchCartReqGen, skuId));
//		AssertJUnit.assertEquals("Item is not added to cart", "1",
//				fetchCartReqGen.respvalidate.NodeValue(AbsolutNodes.CART_TOTAL_COUNT.toString(), true));
		absolutHelper.performSignOutOperation(userName, tokens[1]);
		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;

		System.out.println("\nTime taken to execute - TestCase - addItemToCart_verifyItemIsAdded : "
			+ timeTaken + " seconds\n");
	}
	
	/**
	 * This Test Case used to invoke CheckoutService updateItemInCart API and
	 * verify for success response(200)
	 * 
	 * @param userName
	 * @param password
	 * @param itemQtyToUpdate
	 * @param updateOpn
	 * @param successRespCode
	 */
	@Test(groups = { "San","Sanity", "FoxSanity", "Regression", "MiniRegression",
			"ExhaustiveRegression" }, dataProvider = "updateItemInCart_verifyItemQtyIsUpdatedDP",dataProviderClass= CartServiceTestsDP.class, description = "1. hit IDP service and login the user. "
					+ "\n 2. hit fetchCart API. \n 3. if user's cart is empty then first add items to cart(\n a.hit Search Service API and extract some skuIDs"
					+ "\n b. Check if searched styleIds exists in user's cart or not. \n c. hit addItemToCart API giving skuId which doesnt exists in cart)"
					+ "\n 4. hit updateCart API" + "\n 5.Check the service response must be 200")
	public void updateItemInCart_verifyItemQtyIsUpdated(String userName, String password,String skuId1, String skuId2,
			String skuIdToUpd,String itemQtyToUpdate, String updateOpn, String successRespCode) {
		long startTime = Calendar.getInstance().getTimeInMillis();
		ArrayList<String> skuSToAdd = new ArrayList<String>();
		skuSToAdd.add(skuId1);
		skuSToAdd.add(skuId2);
		
		String tokens[] = absolutHelper.idp_GetTokens(userName, password);
		AssertJUnit.assertEquals(SUCCESS_STATUS_MSG, tokens[2]);
		System.out.println("xid is:"+tokens[0]);
		RequestGenerator clearCartReqGen = absolutHelper.clearCart(tokens[0]);
		System.out.println("\nPrinting CheckoutService clearCart API response :\n\n"
				+ clearCartReqGen.respvalidate.returnresponseasstring());
		
		AssertJUnit.assertEquals("CheckoutService clearCart API is not working", Integer.parseInt(SUCCESS_STATUS_RESP),
				clearCartReqGen.response.getStatus());
		AssertJUnit.assertEquals("Items are not cleared from cart", "0",
				clearCartReqGen.respvalidate.NodeValue(AbsolutNodes.CART_TOTAL_COUNT.toString(), true));
			
		for(String arr: skuSToAdd){
			RequestGenerator addItemToCartReqGen = absolutHelper.addItemToCart(tokens[0],arr, "1", "ADD");
			System.out.println("\nPrinting CheckoutService addItemToCart API response:\n\n"
			   +addItemToCartReqGen.respvalidate.returnresponseasstring());

			AssertJUnit.assertEquals("addItemToCart API is not working",
				Integer.parseInt(SUCCESS_STATUS_RESP), addItemToCartReqGen.response.getStatus());
		}
		RequestGenerator fetchCartReqGen = absolutHelper.fetchCart(tokens[0]);
		System.out.println("\nPrinting CheckoutService fetchCart API response :\n\n"
				+ fetchCartReqGen.respvalidate.returnresponseasstring());
		
		AssertJUnit.assertTrue("Error while invoking fetchCart API",
			fetchCartReqGen.respvalidate.NodeValue(AbsolutNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));
		String itemId = absolutHelper.getItemIdFromSKUId(fetchCartReqGen, skuIdToUpd);
		
		System.out.println("\nPrinting skuId : " + skuIdToUpd + " & itemId : " + itemId + " & quantity : " + itemQtyToUpdate
			+ " is going for updation\n");
		
		RequestGenerator updateItemInCartReqGen = absolutHelper.updateItemInCart(tokens[0],itemId,
				skuIdToUpd, String.valueOf(itemQtyToUpdate), updateOpn);
		System.out.println("\nPrinting updateItemInCart API response :\n\n"
			+ updateItemInCartReqGen.respvalidate.returnresponseasstring());
		
		AssertJUnit.assertEquals("CheckoutService updateItemInCart API is not working",
			Integer.parseInt(successRespCode), updateItemInCartReqGen.response.getStatus());
		
		fetchCartReqGen = absolutHelper.fetchCart(tokens[0]);
		AssertJUnit.assertEquals("ItemQuantity is not updated correctly", itemQtyToUpdate, 
				absolutHelper.getItemQtyFromSKUId(fetchCartReqGen, skuIdToUpd));
		absolutHelper.performSignOutOperation(userName, tokens[1]);
		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;

		System.out.println("\nTime taken to execute - TestCase - updateItemInCart_verifyItemQtyIsUpdated : "
			+ timeTaken + " seconds\n");

	}
	/**
	 * This Test Case used to invoke CheckoutService removeItemFrom API and
	 * verify for success response(200)
	 * 
	 * @param userName
	 * @param password
	 * @param deleteOpn
	 * @param successRespCode
	 */
	@Test(groups = { "Sanity", "FoxSanity", "Regression", "MiniRegression",
			"ExhaustiveRegression" }, dataProvider = "removeItemFromCartAPI_verifyItemIsRemovedDP",dataProviderClass= CartServiceTestsDP.class, description = "1. hit IDP service and login the user. "
					+ "\n 2. hit fetchCart API. \n 3. if user's cart is empty then first add items to cart(\n a.hit Search Service API and extract some skuIDs"
					+ "\n b. Check if searched styleIds exists in user's cart or not. \n c. hit addItemToCart API giving skuId which doesnt exists in cart)"
					+ "\n 4. hit removeItemFromCart API giving it itemId to be removed"
					+ "\n 5. Verify statusCode must be 200 in APIs response")
	public void removeItemFromCartAPI_verifyItemIsRemoved(String userName, String password,
			String skuId1,String skuId2, String skuIdtoRemove) {
		long startTime = Calendar.getInstance().getTimeInMillis();
		ArrayList<String> skuSToAdd = new ArrayList<String>();
		skuSToAdd.add(skuId1);
		skuSToAdd.add(skuId2);
		
		String tokens[] = absolutHelper.idp_GetTokens(userName, password);
		AssertJUnit.assertEquals(SUCCESS_STATUS_MSG, tokens[2]);
		System.out.println("xid is:"+tokens[0]);
		RequestGenerator clearCartReqGen = absolutHelper.clearCart(tokens[0]);
		System.out.println("\nPrinting CheckoutService clearCart API response :\n\n"
				+ clearCartReqGen.respvalidate.returnresponseasstring());
		
		AssertJUnit.assertEquals("CheckoutService clearCart API is not working", Integer.parseInt(SUCCESS_STATUS_RESP),
				clearCartReqGen.response.getStatus());
		
		for(String arr: skuSToAdd){
			RequestGenerator addItemToCartReqGen = absolutHelper.addItemToCart(tokens[0],arr, "1", "ADD");
			System.out.println("\nPrinting CheckoutService addItemToCart API response:\n\n"
			   +addItemToCartReqGen.respvalidate.returnresponseasstring());

			AssertJUnit.assertEquals("addItemToCart API is not working",
				Integer.parseInt(SUCCESS_STATUS_RESP), addItemToCartReqGen.response.getStatus());
		}
		
		RequestGenerator fetchCartReqGen = absolutHelper.fetchCart(tokens[0]);
		System.out.println("\nPrinting CheckoutService  fetchCart API response :\n\n"
				+ fetchCartReqGen.respvalidate.returnresponseasstring());
		AssertJUnit.assertTrue("Error while invoking CheckoutService fetchCart API",
			fetchCartReqGen.respvalidate.NodeValue(AbsolutNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));

		String itemId = absolutHelper.getItemIdFromSKUId(fetchCartReqGen, skuIdtoRemove);
		System.out.println("\nskuId:" + skuIdtoRemove + " & itemId : " + itemId + " is going to removeItemFromCart\n");
		
		
		RequestGenerator removeItemFromCartReqGen = absolutHelper.removeItemFromCart(tokens[0],itemId,
			"DELETE");
		System.out.println("\nPrinting removeItemFromCart API response :\n\n"
				+ removeItemFromCartReqGen.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("removeItemFromCart API is not working",
			Integer.parseInt(SUCCESS_STATUS_RESP), removeItemFromCartReqGen.response.getStatus());
		
		AssertJUnit.assertTrue("SkuId "+skuIdtoRemove+" is still in cart,should have been removed",
			removeItemFromCartReqGen.respvalidate.NodeValue(AbsolutNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));
		
		fetchCartReqGen = absolutHelper.fetchCart(tokens[0]);
		AssertJUnit.assertEquals("SkuId "+skuIdtoRemove+" is still in cart,should have been removed",
			 "Not Exists", absolutHelper.doesSkuIDExists(fetchCartReqGen, skuIdtoRemove) );
		AssertJUnit.assertEquals("No of items in cart should be 1", "1",
				fetchCartReqGen.respvalidate.NodeValue(AbsolutNodes.CART_TOTAL_COUNT.toString(), true));
			
		absolutHelper.performSignOutOperation(userName, tokens[1]);
		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;

		System.out.println("\nTime taken to execute - TestCase - removeItemFromCartAPI_verifyItemIsRemoved : "
						+ timeTaken + " seconds\n");
		
	}

	/**
	 * This Test Case used to invoke CheckoutService clearCart API and verify
	 * all items are cleared from cart.
	 * 
	 * @param userName
	 * @param password
	 * @param successRespCode
	 */
	@Test(groups = { "Sanity", "FoxSanity", "Regression", "MiniRegression",
			"ExhaustiveRegression" }, dataProvider = "clearCartAPI_verifyCartIsClearedDP",dataProviderClass= CartServiceTestsDP.class, description = "1. hit IDP service and login the user. "
					+ "\n 2. hit fetchCart API. \n 3. if user's cart is empty then first add items to cart(\n a.hit Search Service API and extract some skuIDs"
					+ "\n b. Check if searched styleIds exists in user's cart or not. \n c. hit addItemToCart API giving skuId which doesnt exists in cart)"
					+ "\n 4. hit ClearCart API " + "\n 5.Check the service response must be 200")
	public void clearCartAPI_verifyCartIsCleared(String userName, String password) {
		long startTime = Calendar.getInstance().getTimeInMillis();
		String tokens[] = absolutHelper.idp_GetTokens(userName, password);
		AssertJUnit.assertEquals(SUCCESS_STATUS_MSG, tokens[2]);
		System.out.println("xid is:"+tokens[0]);
		RequestGenerator fetchCartReqGen = absolutHelper.fetchCart(tokens[0]);
		System.out.println("First fetch cart response!!!....");
		System.out.println("\nPrinting CheckoutService fetchCart API response :\n\n"
				+ fetchCartReqGen.respvalidate.returnresponseasstring());
		AssertJUnit.assertTrue("Error while fetchCart API",
			fetchCartReqGen.respvalidate.NodeValue(AbsolutNodes.STATUS_TYPE.toString(), true)
						.contains(SUCCESS_STATUS_TYPE));

		if (fetchCartReqGen.respvalidate.NodeValue(AbsolutNodes.CART_TOTAL_COUNT.toString(), true).equals(EMPTY_VALUE)) 
		{
			String skuId = "3828";
			RequestGenerator addItemToCartReqGen = absolutHelper.addItemToCart(tokens[0],skuId, "1", "ADD");
			System.out.println("\nPrinting addItemToCart API response:\n\n" +addItemToCartReqGen.respvalidate.returnresponseasstring());
		    
		    AssertJUnit.assertEquals("addItemToCart API is not working",
				Integer.parseInt(SUCCESS_STATUS_RESP), addItemToCartReqGen.response.getStatus());
		}

		RequestGenerator clearCartReqGen = absolutHelper.clearCart(tokens[0]);
		System.out.println("\nPrinting clearCart API response :\n\n"
				+ clearCartReqGen.respvalidate.returnresponseasstring());
		
		AssertJUnit.assertEquals("clearCart API is not working", Integer.parseInt(SUCCESS_STATUS_RESP),
				clearCartReqGen.response.getStatus());

		AssertJUnit.assertEquals("Cart still has items,should be empty", "0",
				clearCartReqGen.respvalidate.NodeValue(AbsolutNodes.CART_TOTAL_COUNT.toString(), true));
		absolutHelper.performSignOutOperation(userName, tokens[1]);	
		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;

		System.out.println("\nTime taken to execute - TestCase - clearCartAPI_verifyCartIsCleared : "
						+ timeTaken + " seconds\n");
	}


	/**
	 * This Test Case used to invoke fetchCart API with different contexts
	 * (default , address, payment , realtime )
	 * verify for success response(200)
	 * 
	 * @param userName
	 * @param password
	 * @param successRespCode
	 */
	@Test(groups = {"San", "Smoke", "Sanity", "FoxSanity", "ProdSanity", "Regression", "MiniRegression",
			"ExhaustiveRegression" }, dataProvider = "fetchCartByContextDP",dataProviderClass= CartServiceTestsDP.class
			, description = "1. hit IDP service to login"
					+ "\n 2. hit fetchCart API" + "\n 3.Check the service response must be 200")
	public void fetchCartByContext(String userName, String password, String context,
			String successRespCode, String successStatusMsg, String successStatusType) 
	{
		long startTime = Calendar.getInstance().getTimeInMillis();
		String tokens[] = absolutHelper.idp_GetTokens(userName, password);
		AssertJUnit.assertEquals(successStatusMsg, tokens[2]);
		System.out.println("xid is:"+tokens[0]);
		RequestGenerator fetchCartReqGen = absolutHelper.fetchCartByContext(tokens[0],context);
		System.out.println("\nPrinting fetchCart API response with:: "+context +" context"
			+ fetchCartReqGen.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("fetchCart API is not working",
			Integer.parseInt(successRespCode), fetchCartReqGen.response.getStatus());
		AssertJUnit.assertTrue("fetchCart API response status message is not a success status message",
			fetchCartReqGen.respvalidate.NodeValue(AbsolutNodes.STATUS_MESSAGE.toString(), true).contains(successStatusMsg));
		AssertJUnit.assertTrue("fetchCart API response status type is not a success status type",
			fetchCartReqGen.respvalidate.NodeValue(AbsolutNodes.STATUS_TYPE.toString(), true).contains(successStatusType));
		
		absolutHelper.performSignOutOperation(userName, tokens[1]);
		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;

		System.out.println("\nTime taken to execute - TestCase - fetchCart_verifyAPIIsUp : "
			+ timeTaken + " seconds\n");

	}
	
	/**
	 * This Test Case used to invoke CheckoutService addGiftWrapAndMessage API
	 * and verify for success response(200)
	 * 
	 * @param userName
	 * @param password
	 * @param senderName
	 * @param giftMsg
	 * @param recipientName
	 * @param respCode
	 */
	@Test(groups = { "Sanity", "Regression", "MiniRegression",
			"ExhaustiveRegression" }, dataProvider = "addGiftWrapAndMessage_verifyAPIIsUpDP",dataProviderClass= CartServiceTestsDP.class, description = "1. hit IDP service and login the user. "
					+ "\n 2. hit fetchCart API. \n 3. if user's cart is empty then first add items to cart(\n a.hit Search Service API and extract some skuIDs"
					+ "\n b. Check if searched styleIds exists in user's cart or not. \n c. hit addItemToCart API giving skuId which doesnt exists in cart)"
					+ "\n 4. Check if giftwrap is already applied to cart remove it"
					+ "\n 5. hit addGiftWrap API passing valid data"
					+ "\n 6. Verify statusCode must be 200 in APIs response")
	public void addGiftWrapAndMessage_verifyAPIIsUp(String userName, String password,
			String senderName, String giftMsg, String recipientName, String respCode) {
		long startTime = Calendar.getInstance().getTimeInMillis();
		String tokens[] = absolutHelper.idp_GetTokens(userName, password);
		AssertJUnit.assertEquals(SUCCESS_STATUS_MSG, tokens[2]);
		System.out.println("xid is:"+tokens[0]);
		RequestGenerator addGiftWrapAndMessageReqGen = absolutHelper.addGiftWrapAndMessage(tokens[0],senderName, giftMsg, recipientName);
		System.out.println("\nPrinting addGiftWrapAndMessage API response :\n\n"
			+ addGiftWrapAndMessageReqGen.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("addGiftWrapAndMessage API is not working", Integer.parseInt(respCode),
			addGiftWrapAndMessageReqGen.response.getStatus());
		AssertJUnit.assertEquals("GiftWrap is Not Applied to cart",TRUE_VALUE,
			addGiftWrapAndMessageReqGen.respvalidate.NodeValue(AbsolutNodes.CART_IS_GIFT_ORDER.toString(),true));
		absolutHelper.performSignOutOperation(userName, tokens[1]);	
		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;

		System.out.println("\nTime taken to execute - TestCase - addGiftWrapAndMessage_verifyAPIIsUp : "
			+ timeTaken + " seconds\n");
		
	}

	/**
	 * This Test Case used to invoke CheckoutService addGiftWrapAndMessage API
	 * and verify for success response(200)
	 * 
	 * @param userName
	 * @param password
	 * @param senderName
	 * @param giftMsg
	 * @param recipientName
	 * @param respCode
	 */
	@Test(groups = { "Sanity", "Regression", "MiniRegression",
			"ExhaustiveRegression" }, dataProvider = "addGiftWrapAndMessage_verifyGiftWrapIsAppliedDP",dataProviderClass= CartServiceTestsDP.class, description = "1. hit IDP service and login the user. "
					+ "\n 2. hit fetchCart API. \n 3. if user's cart is empty then first add items to cart(\n a.hit Search Service API and extract some skuIDs"
					+ "\n b. Check if searched styleIds exists in user's cart or not. \n c. hit addItemToCart API giving skuId which doesnt exists in cart)"
					+ "\n 4. Check if giftwrap is already applied to cart remove it"
					+ "\n 5. hit addGiftWrap API passing valid data"
					+ "\n 6. Verify statusCode must be 200 in APIs response")
	public void addGiftWrapAndMessage_verifyGiftWrapIsApplied(String userName, String password,
			String skuId, String senderName, String giftMsg, String recipientName, String respCode) {
		long startTime = Calendar.getInstance().getTimeInMillis();
		String tokens[] = absolutHelper.idp_GetTokens(userName, password);
		AssertJUnit.assertEquals(SUCCESS_STATUS_MSG, tokens[2]);
		System.out.println("xid is:"+tokens[0]);
		RequestGenerator fetchCartReqGen = absolutHelper.fetchCart(tokens[0]);
		System.out.println("\nPrinting fetchCart API response :\n\n"
			+ fetchCartReqGen.respvalidate.returnresponseasstring());
		
		AssertJUnit.assertTrue("Error while invoking fetchCart API",
			fetchCartReqGen.respvalidate.NodeValue(AbsolutNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));

		if (fetchCartReqGen.respvalidate.NodeValue(AbsolutNodes.CART_TOTAL_COUNT.toString(), true).equals(EMPTY_VALUE)) 
		{
			RequestGenerator addItemToCartReqGen = absolutHelper.addItemToCart(tokens[0],skuId, "1", "ADD");
			System.out.println("\nPrinting addItemToCart API response:\n\n"
			   +addItemToCartReqGen.respvalidate.returnresponseasstring());

			AssertJUnit.assertEquals("addItemToCart API is not working",
				Integer.parseInt(respCode), addItemToCartReqGen.response.getStatus());

		} else if (fetchCartReqGen.respvalidate.NodeValue(AbsolutNodes.CART_IS_GIFT_ORDER.toString(), true)
				.equals(TRUE_VALUE)) {
			RequestGenerator removeGiftWrapAndMsgReqGen = absolutHelper.removeGiftWrapAndMessage(tokens[0]);
			System.out.println("\nPrinting removeGiftWrapAndMessage API response :\n\n"
				+ removeGiftWrapAndMsgReqGen.respvalidate.returnresponseasstring());
			
			AssertJUnit.assertTrue("Error while invoking removeGiftWrapAndMessage API",
				removeGiftWrapAndMsgReqGen.respvalidate.NodeValue(AbsolutNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));
		}

		RequestGenerator addGiftWrapAndMessageReqGen = absolutHelper.addGiftWrapAndMessage(tokens[0],senderName, giftMsg, recipientName);
		System.out.println("\nPrinting addGiftWrapAndMessage API response :\n\n"
			+ addGiftWrapAndMessageReqGen.respvalidate.returnresponseasstring());
		
		AssertJUnit.assertEquals("addGiftWrapAndMessage API is not working", Integer.parseInt(respCode),
			addGiftWrapAndMessageReqGen.response.getStatus());
		absolutHelper.performSignOutOperation(userName, tokens[1]);
		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;

		System.out.println("\nTime taken to execute - TestCase - addGiftWrapAndMessage_verifyGiftWrapIsApplied : "
			+ timeTaken + " seconds\n");
	}

	
	/**
	 * This Test Case used to invoke CheckoutService addGiftWrapAndMessage API
	 * and verify for gift nodes
	 * 
	 * @param userName
	 * @param password
	 * @param senderName
	 * @param giftMsg
	 * @param recipientName
	 */
	@Test(groups = { "Sanity", "Regression", "MiniRegression",
			"ExhaustiveRegression" }, dataProvider = "addGiftWrapAndMessage_validateGiftWrapNodesDP",dataProviderClass= CartServiceTestsDP.class, description = "1. hit IDP service and login the user. "
					+ "\n 2. hit fetchCart API. \n 3. if user's cart is empty then first add items to cart(\n a.hit Search Service API and extract some skuIDs"
					+ "\n b. Check if searched styleIds exists in user's cart or not. \n c. hit addItemToCart API giving skuId which doesnt exists in cart)"
					+ "\n 4. Check if giftwrap is already applied to cart remove it"
					+ "\n 5. hit addGiftWrap API passing valid data" + "\n 6. Validate GiftWrap Nodes in APIs response")
	public void addGiftWrapAndMessage_validateGiftWrapNodes(String userName, String password,
			String skuId, String senderName, String giftMsg, String recipientName) {
		long startTime = Calendar.getInstance().getTimeInMillis();
		String tokens[] = absolutHelper.idp_GetTokens(userName, password);
		AssertJUnit.assertEquals(SUCCESS_STATUS_MSG, tokens[2]);
		System.out.println("xid is:"+tokens[0]);
		RequestGenerator clearCartReqGen = absolutHelper.clearCart(tokens[0]);
		AssertJUnit.assertEquals("CheckoutService clearCart API is not working", Integer.parseInt(SUCCESS_STATUS_RESP),
			clearCartReqGen.response.getStatus());
		AssertJUnit.assertEquals("Items are not cleared from cart", "0",
			clearCartReqGen.respvalidate.NodeValue(AbsolutNodes.CART_TOTAL_COUNT.toString(), true));
		
		RequestGenerator addItemToCartReqGen = absolutHelper.addItemToCart(tokens[0],skuId, "1", "ADD");
		System.out.println("\nPrinting CheckoutService addItemToCart API response :\n\n"
			+ addItemToCartReqGen.respvalidate.returnresponseasstring());
		
		AssertJUnit.assertEquals("Item is not added to cart", "1",
			addItemToCartReqGen.respvalidate.NodeValue(AbsolutNodes.CART_TOTAL_COUNT.toString(), true));
		
		RequestGenerator fetchCartReqGen = absolutHelper.fetchCart(tokens[0]);
		System.out.println("fetchCart response:"+fetchCartReqGen.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("Item is not added to cart", "1",
			fetchCartReqGen.respvalidate.NodeValue(AbsolutNodes.CART_TOTAL_COUNT.toString(), true));
		RequestGenerator addGiftWrapAndMessageReqGen = absolutHelper.addGiftWrapAndMessage(tokens[0],senderName, giftMsg, recipientName);
		System.out.println("\nPrinting CheckoutService addGiftWrapAndMessage API response :\n\n"
			+ addGiftWrapAndMessageReqGen.respvalidate.returnresponseasstring());
		fetchCartReqGen = absolutHelper.fetchCart(tokens[0]);
		AssertJUnit.assertTrue("Failed to add the gift wrap", !fetchCartReqGen.respvalidate
			.NodeValue(AbsolutNodes.CART_GIFT_CHARGE.toString(), true).equals(EMPTY_VALUE));

		AssertJUnit.assertTrue("addGiftWrapAndMessage nodes does not exists",
				fetchCartReqGen.respvalidate.DoesNodesExists(AbsolutNodes.getGiftWrapMessageNodes()));

		AssertJUnit.assertTrue("isGiftOrder is invalid", fetchCartReqGen.respvalidate
			.NodeValue(AbsolutNodes.CART_IS_GIFT_ORDER.toString(), true).equals(TRUE_VALUE));

		AssertJUnit.assertTrue("gift sender is different", fetchCartReqGen.respvalidate
			.NodeValue(AbsolutNodes.CART_GIFT_MSG_SENDER.toString(), true).contains(senderName));

		AssertJUnit.assertTrue("gift message is different", fetchCartReqGen.respvalidate
			.NodeValue(AbsolutNodes.CART_GIFT_MSG_INFO.toString(), true).contains(giftMsg));

		AssertJUnit.assertTrue("gift recipient is different", fetchCartReqGen.respvalidate
			.NodeValue(AbsolutNodes.CART_GIFT_MSG_RECIPIENT.toString(), true).contains(recipientName));
		absolutHelper.performSignOutOperation(userName, tokens[1]);
		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;

		System.out.println("\nTime taken to execute - TestCase - addGiftWrapAndMessage_validateGiftWrapNodes : "
			+ timeTaken + " seconds\n");
		
	}
	/**
	 * This Test Case used to invoke CheckoutService removeGiftWrapAndMessage
	 * API and verify for success response
	 * 
	 * @param userName
	 * @param password
	 * @param successRespCode
	 */
	@Test(groups = { "Sanity", "Regression", "MiniRegression",
			"ExhaustiveRegression" }, dataProvider = "removeGiftWrapAndMessage_verifyAPIIsUpDP",dataProviderClass= CartServiceTestsDP.class, description = "1. hit IDP service and login the user. "
					+ "\n 2. hit fetchCart API. \n 3. if user's cart is empty then first add items to cart(\n a.hit Search Service API and extract some skuIDs"
					+ "\n b. Check if searched styleIds exists in user's cart or not. \n c. hit addItemToCart API giving skuId which doesnt exists in cart)"
					+ "\n 4. Check if giftwrap is not applied to cart, first apply GiftWrap"
					+ "\n 5. hit removeGiftWrap API" + "\n 6. Verify statusCode must be 200 in APIs response")
	public void removeGiftWrapAndMessage_verifyAPIIsUp(String userName, String password,
			String successRespCode) {
		long startTime = Calendar.getInstance().getTimeInMillis();
		String tokens[] = absolutHelper.idp_GetTokens(userName, password);
		AssertJUnit.assertEquals(SUCCESS_STATUS_MSG, tokens[2]);
		System.out.println("xid is:"+tokens[0]);
		RequestGenerator removeGiftWrapAndMessageReqGen = absolutHelper.removeGiftWrapAndMessage(tokens[0]);
		System.out.println("\nPrinting removeGiftWrapAndMessage API response :\n\n"
			+ removeGiftWrapAndMessageReqGen.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("removeGiftWrapAndMessage API is not working",
			Integer.parseInt(successRespCode), removeGiftWrapAndMessageReqGen.response.getStatus());
		AssertJUnit.assertEquals("GiftWrap is still not removed from cart",FALSE_VALUE,
			removeGiftWrapAndMessageReqGen.respvalidate.NodeValue(AbsolutNodes.CART_IS_GIFT_ORDER.toString(),true));
		absolutHelper.performSignOutOperation(userName, tokens[1]);	
		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;

		System.out.println("\nTime taken to execute - TestCase - removeGiftWrapAndMessage_verifyAPIIsUp : "
						+ timeTaken + " seconds\n");
		
	}
	
	/**
	 * This Test Case used to invoke CheckoutService removeGiftWrapAndMessage
	 * API and verify GiftWrap is Removed
	 * 
	 * @param userName
	 * @param password
	 * @param successStatusCode
	 * @param successStatusMsg
	 * @param successStatusType
	 */
	@Test(groups = { "Sanity", "Regression", "MiniRegression",
			"ExhaustiveRegression" }, dataProvider = "removeGiftWrapAndMessage_verifyGiftWrapIsRemovedDP",dataProviderClass= CartServiceTestsDP.class, description = "1. hit IDP service and login the user. "
					+ "\n 2. hit fetchCart API. \n 3. if user's cart is empty then first add items to cart(\n a.hit Search Service API and extract some skuIDs"
					+ "\n b. Check if searched styleIds exists in user's cart or not. \n c. hit addItemToCart API giving skuId which doesnt exists in cart)"
					+ "\n 4. Check if giftwrap is not applied to cart, first apply GiftWrap"
					+ "\n 5. hit removeGiftWrap API"
					+ "\n 6. Verify statusCode, statusMessage & statusType must be of Success")
	public void removeGiftWrapAndMessage_verifyGiftWrapIsRemoved(String userName, String password,
			String skuId, String successStatusCode, String successStatusMsg, String successStatusType) {
		long startTime = Calendar.getInstance().getTimeInMillis();
		String tokens[] = absolutHelper.idp_GetTokens(userName, password);
		AssertJUnit.assertEquals(successStatusMsg, tokens[2]);
		System.out.println("xid is:"+tokens[0]);
		RequestGenerator fetchCartReqGen = absolutHelper.fetchCart(tokens[0]);
		System.out.println("\nPrinting CheckoutService fetchCart API response :\n\n"
				+ fetchCartReqGen.respvalidate.returnresponseasstring());
		
		AssertJUnit.assertTrue("Error while invoking CheckoutService fetchCart API",
			fetchCartReqGen.respvalidate.NodeValue(AbsolutNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));

		if (fetchCartReqGen.respvalidate.NodeValue(AbsolutNodes.CART_TOTAL_COUNT.toString(), true).equals(EMPTY_VALUE)) 
		{
			RequestGenerator addItemToCartReqGen = absolutHelper.addItemToCart(tokens[0],skuId, "1", "ADD");
			System.out.println("\nPrinting addItemToCart API response:\n\n"
			   +addItemToCartReqGen.respvalidate.returnresponseasstring());

			AssertJUnit.assertEquals("addItemToCart API is not working",
				Integer.parseInt(successStatusCode), addItemToCartReqGen.response.getStatus());
		} else if (fetchCartReqGen.respvalidate.NodeValue(AbsolutNodes.CART_IS_GIFT_ORDER.toString(), true).equals(FALSE_VALUE)) 
		{
			RequestGenerator addGiftWrapAndMessageReqGen = absolutHelper.addGiftWrapAndMessage(tokens[0],"shrinkhala", "test giftwrap", userName);
			System.out.println("\nPrinting CheckoutService addGiftWrapAndMessage API response :\n\n"
				+ addGiftWrapAndMessageReqGen.respvalidate.returnresponseasstring());
			AssertJUnit.assertTrue("Error while invoking CheckoutService addGiftWrapAndMessage API",
				addGiftWrapAndMessageReqGen.respvalidate.NodeValue(AbsolutNodes.STATUS_TYPE.toString(), true)
					.contains(SUCCESS_STATUS_TYPE));
		}

		RequestGenerator removeGiftWrapAndMessageReqGen = absolutHelper.removeGiftWrapAndMessage(tokens[0]);
		System.out.println("\nPrinting CheckoutService removeGiftWrapAndMessage API response :\n\n"
			+ removeGiftWrapAndMessageReqGen.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("removeGiftWrapAndMessage API is not working", Integer.parseInt(successStatusCode),
				removeGiftWrapAndMessageReqGen.response.getStatus());	
		fetchCartReqGen = absolutHelper.fetchCart(tokens[0]);
		AssertJUnit.assertEquals("GiftWrap is still not removed from cart",FALSE_VALUE,
				fetchCartReqGen.respvalidate.NodeValue(AbsolutNodes.CART_IS_GIFT_ORDER.toString(),true));
		

		AssertJUnit.assertTrue("CheckoutService removeGiftWrapAndMessage API response status message is not a success status message",
				fetchCartReqGen.respvalidate.NodeValue(AbsolutNodes.STATUS_MESSAGE.toString(), true).contains(successStatusMsg));

		AssertJUnit.assertTrue("CheckoutService removeGiftWrapAndMessage API response status type is not a success status type",
				fetchCartReqGen.respvalidate.NodeValue(AbsolutNodes.STATUS_TYPE.toString(), true).contains(successStatusType));
		absolutHelper.performSignOutOperation(userName, tokens[1]);
		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;

		System.out.println("\nTime taken to execute - TestCase - removeGiftWrapAndMessage_verifyGiftWrapIsRemoved : "
			+ timeTaken + " seconds\n");
		

	}
	
	/**
	 * This Test Case used to invoke CheckoutService removeGiftWrapAndMessage
	 * API and verify for gift nodes
	 * 
	 * @param userName
	 * @param password
	 */
	@Test(groups = { "Sanity", "Regression", "MiniRegression",
			"ExhaustiveRegression" }, dataProvider = "removeGiftWrapAndMessage_validateGiftWrapNodesDP",dataProviderClass= CartServiceTestsDP.class, description = "1. hit IDP service and login the user. "
					+ "\n 2. hit fetchCart API. \n 3. if user's cart is empty then first add items to cart(\n a.hit Search Service API and extract some skuIDs"
					+ "\n b. Check if searched styleIds exists in user's cart or not. \n c. hit addItemToCart API giving skuId which doesnt exists in cart)"
					+ "\n 4. Check if giftwrap is not applied to cart, first apply GiftWrap"
					+ "\n 5. hit removeGiftWrap API" + "\n 6. Validate GiftWrap Nodes in APIs response")
	public void removeGiftWrapAndMessage_validateGiftWrapNodes(String userName, String password, String skuId) {
		long startTime = Calendar.getInstance().getTimeInMillis();
		String tokens[] = absolutHelper.idp_GetTokens(userName, password);
		AssertJUnit.assertEquals(SUCCESS_STATUS_MSG, tokens[2]);
		System.out.println("xid is:"+tokens[0]);
		RequestGenerator fetchCartReqGen = absolutHelper.fetchCart(tokens[0]);
		System.out.println("\nPrinting CheckoutService fetchCart API response :\n\n"
				+ fetchCartReqGen.respvalidate.returnresponseasstring());
		AssertJUnit.assertTrue("Error while invoking CheckoutService fetchCart API",
			fetchCartReqGen.respvalidate.NodeValue(AbsolutNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));

		if (fetchCartReqGen.respvalidate.NodeValue(AbsolutNodes.CART_TOTAL_COUNT.toString(), true).equals(EMPTY_VALUE))
		{
			RequestGenerator addItemToCartReqGen = absolutHelper.addItemToCart(tokens[0],skuId, "1", "ADD");
			System.out.println("\nPrinting addItemToCart API response:\n\n"
			   +addItemToCartReqGen.respvalidate.returnresponseasstring());

			AssertJUnit.assertEquals("addItemToCart API is not working",
				SUCCESS_STATUS_RESP, addItemToCartReqGen.response.getStatus());
			
		} else if (fetchCartReqGen.respvalidate.NodeValue(AbsolutNodes.CART_IS_GIFT_ORDER.toString(), true)
				.equals(FALSE_VALUE)) {
			RequestGenerator addGiftWrapAndMessageReqGen = absolutHelper.addGiftWrapAndMessage(tokens[0],"shrinkhala", "test GiftWrap", userName);
			System.out.println("\nPrinting CheckoutService addGiftWrapAndMessage API response :\n\n"
				+ addGiftWrapAndMessageReqGen.respvalidate.returnresponseasstring());
			
			AssertJUnit.assertTrue("Error while invoking CheckoutService addGiftWrapAndMessage API",
				addGiftWrapAndMessageReqGen.respvalidate.NodeValue(AbsolutNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));
		}

		RequestGenerator removeGiftWrapAndMessageReqGen = absolutHelper.removeGiftWrapAndMessage(tokens[0]);
		System.out.println("\nPrinting CheckoutService removeGiftWrapAndMessage API response :\n\n"
			+ removeGiftWrapAndMessageReqGen.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("removeGiftWrapAndMessage API is not working", Integer.parseInt(SUCCESS_STATUS_RESP),
				removeGiftWrapAndMessageReqGen.response.getStatus());	
		fetchCartReqGen = absolutHelper.fetchCart(tokens[0]);
		AssertJUnit.assertEquals("GiftWrap is still not removed from cart",FALSE_VALUE,
				fetchCartReqGen.respvalidate.NodeValue(AbsolutNodes.CART_IS_GIFT_ORDER.toString(),true));
		AssertJUnit.assertTrue("GiftWrap Charge is still not set to zero", fetchCartReqGen.respvalidate
			.NodeValue(AbsolutNodes.CART_GIFT_CHARGE.toString(), true).equals(EMPTY_VALUE));
		
		//need to check below condition
		AssertJUnit.assertTrue("isGiftOrder is invalid", fetchCartReqGen.respvalidate
				.NodeValue(AbsolutNodes.CART_IS_GIFT_ORDER.toString(), true).equals(FALSE_VALUE));
		absolutHelper.performSignOutOperation(userName, tokens[1]);
		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;

		System.out.println("\nTime taken to execute - TestCase - removeGiftWrapAndMessage_validateGiftWrapNodes : "
			+ timeTaken + " seconds\n");

	}
	
	/**
	 * This Test Case used to invoke CheckoutService getMyntCredit API and
	 * verify for success response
	 * 
	 * @param userName
	 * @param password
	 * @param successRespCode
	 */
	@Test(dataProvider = "getUsersCashback_verifyAPIIsUpDP",dataProviderClass= CartServiceTestsDP.class, description = "1. Hit IDP service & login as User"
					+ "\n 2. Hit getMyntCredit API. \n3. Verify API response statusCode must be of success type.")
	public void getUsersCashback_verifyAPIIsUp(String userName, String password,
			String successRespCode) {
		long startTime = Calendar.getInstance().getTimeInMillis();
		String tokens[] = absolutHelper.idp_GetTokens(userName, password);
		AssertJUnit.assertEquals(SUCCESS_STATUS_MSG, tokens[2]);
		System.out.println("xid is:"+tokens[0]);
		RequestGenerator getMyntCreditReqGen = absolutHelper.getMyntCredit(tokens[0]);
		System.out.println("\nPrinting getUser'sCashback API response :\n\n"
			+ getMyntCreditReqGen.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("getUser'sCashback API is not working", Integer.parseInt(successRespCode),
			getMyntCreditReqGen.response.getStatus());
		absolutHelper.performSignOutOperation(userName, tokens[1]);
		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;

		System.out.println("\nTime taken to execute - TestCase - getUsersCashback_verifyAPIIsUp : "
			+ timeTaken + " seconds\n");
	}
	

	/**
	 * This Test Case used to invoke CheckoutService getMyntCredit API and
	 * verify for success status response
	 * 
	 * @param userName
	 * @param password
	 * @param successStatusCode
	 * @param successStatusMsg
	 * @param successStatusType
	 */
	@Test(dataProvider = "getUsersCashback_verifySuccessAbsolutNodesDP",dataProviderClass= CartServiceTestsDP.class, description = "1. Hit IDP service & login as User"
					+ "\n 2. Hit getMyntCredit API. \n3. Verify API response statusCode, statusMessage & statusType must be of success type.")
	public void getUsersCashback_verifySuccessAbsolutNodes(String userName, String password,
			String successStatusCode, String successStatusMsg, String successStatusType) {
		long startTime = Calendar.getInstance().getTimeInMillis();
		String tokens[] = absolutHelper.idp_GetTokens(userName, password);
		AssertJUnit.assertEquals(successStatusMsg, tokens[2]);
		System.out.println("xid is:"+tokens[0]);
		RequestGenerator getMyntCreditReqGen = absolutHelper.getMyntCredit(tokens[0]);
		System.out.println("\nPrinting CheckoutService getMyntCredit API response :\n\n"
			+ getMyntCreditReqGen.respvalidate.returnresponseasstring());
		AssertJUnit.assertTrue("CheckoutService getMyntCredit API response status code is not a success status code",
			getMyntCreditReqGen.respvalidate.NodeValue(AbsolutNodes.STATUS_CODE.toString(), true).contains(successStatusCode));

		AssertJUnit.assertTrue("getMyntCredit API response status message is not a success status message",
			getMyntCreditReqGen.respvalidate.NodeValue(AbsolutNodes.STATUS_MESSAGE.toString(), true).contains(successStatusMsg));

		AssertJUnit.assertTrue("getMyntCredit API response status type is not a success status type",
			getMyntCreditReqGen.respvalidate.NodeValue(AbsolutNodes.STATUS_TYPE.toString(), true).contains(successStatusType));
		absolutHelper.performSignOutOperation(userName, tokens[1]);
		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;

		System.out.println("\nTime taken to execute - TestCase - getUsersCashback_verifySuccessAbsolutNodes : "
			+ timeTaken + " seconds\n");
	}

	/**
	 * This Test Case used to invoke CheckoutService getMyntCredit API and
	 * verify for credit nodes
	 * 
	 * @param userName
	 * @param password
	 */
	@Test(dataProvider = "getMyntCredit_verifyMyntCreditNodesDP",dataProviderClass= CartServiceTestsDP.class, description = "1. Hit IDP service & login as User"
					+ "\n 2. Hit getMyntCredit API. \n3. Verify myntCreditNodes in API response.")
	public void getMyntCredit_verifyMyntCreditNodes(String userName, String password) {
		long startTime = Calendar.getInstance().getTimeInMillis();
		String tokens[] = absolutHelper.idp_GetTokens(userName, password);
		AssertJUnit.assertEquals(SUCCESS_STATUS_MSG, tokens[2]);
		System.out.println("xid is:"+tokens[0]);
		RequestGenerator getMyntCreditReqGen = absolutHelper.getMyntCredit(tokens[0]);
		System.out.println("\nPrinting CheckoutService getMyntCredit API response :\n\n"
				+ getMyntCreditReqGen.respvalidate.returnresponseasstring());
		AssertJUnit.assertTrue("Error while invoking CheckoutService getMyntCredit API",
			getMyntCreditReqGen.respvalidate.NodeValue(AbsolutNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));

		if (!getMyntCreditReqGen.respvalidate.NodeValue(AbsolutNodes.STATUS_TOTAL_COUNT.toString(), true)
				.equals(EMPTY_VALUE)) {
			AssertJUnit.assertTrue("mynt credit information is not complete",
					getMyntCreditReqGen.respvalidate.DoesNodesExists(AbsolutNodes.getMyntCreditNodes()));
		} else {
			System.out.println("\nUnable to validate mynt credit information");
			
		}
		absolutHelper.performSignOutOperation(userName, tokens[1]);
		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;

		System.out.println("\nTime taken to execute - TestCase - getMyntCredit_verifyMyntCreditNodes : "
						+ timeTaken + " seconds\n");

	}
	
	/**
	 * @author Shrinkhala - modified testCases of applyMyntCredit This Test Case
	 *         used to invoke CheckoutService applyMyntCredit API and verify for
	 *         success response
	 * 
	 * @param userName
	 * @param password
	 * @param successRespCode
	 */
	@Test(dataProvider = "applyMyntCredit_verifySuccessResponseDP",dataProviderClass= CartServiceTestsDP.class, description = "1. hit IDP service and login the user. "
					+ "\n 2. hit fetchCart API. \n 3. if user's cart is empty then first add items to cart(\n a.hit Search Service API and extract some skuIDs"
					+ "\n b. Check if searched styleIds exists in user's cart or not. \n c. hit addItemToCart API giving skuId which doesnt exists in cart)"
					+ "\n 4. if cashback is already applied to cart, remove it"
					+ "\n 5. hit getCashback API to fetch the active cashback amount of user"
					+ "\n 6. hit applyCashback API giving the fetched cashback amount to be applied on cart"
					+ "\n 7. Verify statusCode must be 200 in APIs response")
	public void applyMyntCredit_verifySuccessResponse(String userName, String password,
			String successRespCode) {
		long startTime = Calendar.getInstance().getTimeInMillis();
		String tokens[] = absolutHelper.idp_GetTokens(userName, password);
		AssertJUnit.assertEquals(SUCCESS_STATUS_MSG, tokens[2]);
		System.out.println("xid is:"+tokens[0]);
		RequestGenerator fetchCartReqGen =absolutHelper.fetchCart(tokens[0]);
		System.out.println("\nPrinting CheckoutService fetchCart API response :\n\n"
			+ fetchCartReqGen.respvalidate.returnresponseasstring());
		
		AssertJUnit.assertTrue("Error while invoking fetchCart API",
			fetchCartReqGen.respvalidate.NodeValue(AbsolutNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));

		if (fetchCartReqGen.respvalidate.NodeValue(AbsolutNodes.CART_TOTAL_COUNT.toString(), true).equals(EMPTY_VALUE)) {
			RequestGenerator addItemToCartReqGen = absolutHelper.addItemToCart(tokens[0],"3828", "1", "ADD");
			AssertJUnit.assertEquals("Item not added to cart", "1", 
			  addItemToCartReqGen.respvalidate.NodeValue(AbsolutNodes.CART_TOTAL_COUNT.toString(), true));
		}

		if (fetchCartReqGen.respvalidate.NodeValue(AbsolutNodes.CART_USE_MYNT_CASH.toString(), false).equals(TRUE_VALUE)) {
			RequestGenerator removeMyntCredit = absolutHelper.removeMyntCredit(tokens[0]);
			System.out.println("\nPrinting CheckoutService removeMyntCredit API response :\n\n"
				+ removeMyntCredit.respvalidate.returnresponseasstring());

			AssertJUnit.assertTrue("Error while invoking CheckoutService removeMyntCredit API",
				removeMyntCredit.respvalidate.NodeValue(AbsolutNodes.STATUS_TYPE.toString(), true)
					.contains(SUCCESS_STATUS_TYPE));
		}

		RequestGenerator getMyntCreditReqGen = absolutHelper.getMyntCredit(tokens[0]);
		System.out.println("\nPrinting CheckoutService getMyntCredit API response :\n\n"
			+ getMyntCreditReqGen.respvalidate.returnresponseasstring());
		
		System.out.println("balance in user's cashback : " + getMyntCreditReqGen.respvalidate.NodeValue("data.balance", true));

		String useAmount = getMyntCreditReqGen.respvalidate.NodeValue("data.balance", true);

		RequestGenerator applyMyntCreditReqGen = absolutHelper.applyMyntCredit(tokens[0],useAmount);
		System.out.println("\nPrinting CheckoutService applyMyntCredit API response :\n\n"
			+ applyMyntCreditReqGen.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("CheckoutService applyMyntCredit API is not working",
			Integer.parseInt(successRespCode), applyMyntCreditReqGen.response.getStatus());
		absolutHelper.performSignOutOperation(userName, tokens[1]);
		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;

		System.out.println("\nTime taken to execute - TestCase - CheckoutServiceTests_applyMyntCredit_verifySuccessResponse : "
			+ timeTaken + " seconds\n");
		
	}

	/**
	 * This Test Case used to invoke CheckoutService applyMyntCredit API and
	 * verify for success status response
	 * 
	 * @param userName
	 * @param password
	 * @param useAmount
	 * @param successStatusCode
	 * @param successStatusMsg
	 * @param successStatusType
	 */
	@Test(dataProvider = "applyMyntCredit_verifySuccessStatusResponseDP",dataProviderClass= CartServiceTestsDP.class, description = "1. hit IDP service and login the user. "
					+ "\n 2. hit fetchCart API. \n 3. if user's cart is empty then first add items to cart(\n a.hit Search Service API and extract some skuIDs"
					+ "\n b. Check if searched styleIds exists in user's cart or not. \n c. hit addItemToCart API giving skuId which doesnt exists in cart)"
					+ "\n 4. if cashback is already applied to cart, remove it"
					+ "\n 5. hit getCashback API to fetch the active cashback amount of user"
					+ "\n 6. hit applyCashback API giving the fetched cashback amount to be applied on cart"
					+ "\n 7. Verify statusCode, statusMessage & statusType of API response must be of Success")
	public void applyMyntCredit_verifySuccessStatusResponse(String userName, String password,
			String useAmount,String successStatusCode, String successStatusMsg, String successStatusType) {
		long startTime = Calendar.getInstance().getTimeInMillis();
		String tokens[] = absolutHelper.idp_GetTokens(userName, password);
		AssertJUnit.assertEquals(successStatusMsg, tokens[2]);
		System.out.println("xid is:"+tokens[0]);
		RequestGenerator applyMyntCreditReqGen = absolutHelper.applyMyntCredit(tokens[0],useAmount);
		System.out.println("\nPrinting CheckoutService applyMyntCredit API response :\n\n"
			+ applyMyntCreditReqGen.respvalidate.returnresponseasstring());
		
		AssertJUnit.assertTrue("CheckoutService applyMyntCredit API response status code is not a success status code",
			applyMyntCreditReqGen.respvalidate.NodeValue(AbsolutNodes.STATUS_CODE.toString(), true).contains(successStatusCode));

		AssertJUnit.assertTrue("CheckoutService applyMyntCredit API response status message is not a success status message",
			applyMyntCreditReqGen.respvalidate.NodeValue(AbsolutNodes.STATUS_MESSAGE.toString(), true).contains(successStatusMsg));

		AssertJUnit.assertTrue("CheckoutService applyMyntCredit API response status type is not a success status type",
			applyMyntCreditReqGen.respvalidate.NodeValue(AbsolutNodes.STATUS_TYPE.toString(), true).contains(successStatusType));
		absolutHelper.performSignOutOperation(userName, tokens[1]);
		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;

		System.out.println("\nTime taken to execute - TestCase - CheckoutServiceTests_applyMyntCredit_verifySuccessStatusResponse : "
			+ timeTaken + " seconds\n");
	}

	/**
	 * This Test Case used to invoke CheckoutService applyMyntCredit API and
	 * verify for credit nodes
	 * 
	 * @param userName
	 * @param password
	 * @param useAmount
	 */
	@Test(dataProvider = "applyMyntCredit_verifyMyntCreditNodesDP",dataProviderClass= CartServiceTestsDP.class, description = "1. hit IDP service and login the user. "
					+ "\n 2. hit fetchCart API. \n 3. if user's cart is empty then first add items to cart(\n a.hit Search Service API and extract some skuIDs"
					+ "\n b. Check if searched styleIds exists in user's cart or not. \n c. hit addItemToCart API giving skuId which doesnt exists in cart)"
					+ "\n 4. if cashback is already applied to cart, remove it"
					+ "\n 5. hit getCashback API to fetch the active cashback amount of user"
					+ "\n 6. hit applyCashback API giving the fetched cashback amount to be applied on cart"
					+ "\n 7. Validate CashBca Nodes in APIs response")
	public void applyMyntCredit_verifyMyntCreditNodes(String userName, String password, String skuId) {
		long startTime = Calendar.getInstance().getTimeInMillis();
		String tokens[] = absolutHelper.idp_GetTokens(userName, password);
		AssertJUnit.assertEquals(SUCCESS_STATUS_MSG, tokens[2]);
		System.out.println("xid is:"+tokens[0]);
		RequestGenerator fetchCartReqGen = absolutHelper.fetchCart(tokens[0]);
		System.out.println("\nPrinting CheckoutService fetchCart API response :\n\n"
				+ fetchCartReqGen.respvalidate.returnresponseasstring());
		AssertJUnit.assertTrue("Error while invoking CheckoutService fetchCart API",
			fetchCartReqGen.respvalidate.NodeValue(AbsolutNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));

		if (fetchCartReqGen.respvalidate.NodeValue(AbsolutNodes.CART_TOTAL_COUNT.toString(), true).equals(EMPTY_VALUE)) 
		{
			RequestGenerator addItemToCartReqGen = absolutHelper.addItemToCart(tokens[0],skuId, "1", "ADD");
			System.out.println("\nPrinting addItemToCart API response:\n\n"
			   +addItemToCartReqGen.respvalidate.returnresponseasstring());

			AssertJUnit.assertEquals("addItemToCart API is not working",
				SUCCESS_STATUS_RESP, addItemToCartReqGen.response.getStatus());
		}

		if (fetchCartReqGen.respvalidate.NodeValue(AbsolutNodes.CART_USE_MYNT_CASH.toString(), false).equals(TRUE_VALUE)) {
			RequestGenerator removeMyntCreditReqGen = absolutHelper.removeMyntCredit(tokens[0]);
			System.out.println("\nPrinting removeMyntCredit API response :\n\n"
					+ removeMyntCreditReqGen.respvalidate.returnresponseasstring());
			AssertJUnit.assertTrue("Error while invoking removeMyntCredit API",
					removeMyntCreditReqGen.respvalidate.NodeValue(AbsolutNodes.STATUS_TYPE.toString(), true)
							.contains(SUCCESS_STATUS_TYPE));
		}

		RequestGenerator getMyntCreditReqGen = absolutHelper.getMyntCredit(tokens[0]);
		System.out.println("\nPrinting CheckoutService getMyntCredit API response :\n\n"
			+ getMyntCreditReqGen.respvalidate.returnresponseasstring());
		
		System.out.println("balance in user's cashback : " + getMyntCreditReqGen.respvalidate.NodeValue("data.balance", true));

		String useAmount = getMyntCreditReqGen.respvalidate.NodeValue("data.balance", true);

		RequestGenerator applyMyntCreditReqGen = absolutHelper.applyMyntCredit(tokens[0],useAmount);
		System.out.println("\nPrinting CheckoutService applyMyntCredit API response :\n\n"
			+ applyMyntCreditReqGen.respvalidate.returnresponseasstring());
		AssertJUnit.assertTrue("Cashback is not applied", applyMyntCreditReqGen.respvalidate
			.NodeValue(AbsolutNodes.CART_USE_MYNT_CASH.toString(), true).equals(TRUE_VALUE));
		absolutHelper.performSignOutOperation(userName, tokens[1]);
		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;

		System.out.println("\nTime taken to execute - TestCase - CheckoutServiceTests_applyMyntCredit_verifyMyntCreditNodes : "
						+ timeTaken + " seconds\n");
	
	}
	
	/**
	 * This Test Case used to invoke absolut applyCoupon API and verify
	 * for success status response
	 * 
	 * @param userName
	 * @param password
	 * @param successStatusCode
	 * @param successStatusMsg
	 * @param successStatusType
	 */
	@Test(groups = { "Sanity", "Regression", "MiniRegression",
			"ExhaustiveRegression" }, dataProvider = "applyCoupon_verifyAPIIsUpDP",dataProviderClass= CartServiceTestsDP.class, description = "1. hit IDP service and login the user. "
					+ "\n 2. hit fetchCart API. \n 3. if user's cart is empty then first add items to cart(\n a.hit Search Service API and extract some skuIDs"
					+ "\n b. Check if searched styleIds exists in user's cart or not. \n c. hit addItemToCart API giving skuId which doesnt exists in cart)"
					+ "\n 4. hit fetchAllCoupons API and fetch the coupons applicable to user's cart"
					+ "\n 5. hit applyCoupon API by giving correct coupon name fetched"
					+ "\n 6. Verify statusCode, statusMessage & statusType in APIs response must be of Success")
	public void applyCoupon_verifyAPIIsUp(String userName, String password, String skuId,
			String couponId, String successStatusCode, String successStatusMsg, String successStatusType) {
		long startTime = Calendar.getInstance().getTimeInMillis();
		String tokens[] = absolutHelper.idp_GetTokens(userName, password);
		AssertJUnit.assertEquals(successStatusMsg, tokens[2]);
		
		RequestGenerator fetchCartReqGen = absolutHelper.fetchCart(tokens[0]);
		System.out.println("\nPrinting CheckoutService fetchCart API response :\n\n"
				+ fetchCartReqGen.respvalidate.returnresponseasstring());
		
		AssertJUnit.assertTrue("fetchCart API response status type is not a success status type",
			fetchCartReqGen.respvalidate.NodeValue(AbsolutNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));

		if (fetchCartReqGen.respvalidate.NodeValue(AbsolutNodes.CART_TOTAL_COUNT.toString(), true).equals(EMPTY_VALUE)) {
			RequestGenerator addItemToCartReqGen = absolutHelper.addItemToCart(tokens[0], skuId, "1", "ADD");
			AssertJUnit.assertEquals("Item not added to cart", "1", 
			  addItemToCartReqGen.respvalidate.NodeValue(AbsolutNodes.CART_TOTAL_COUNT.toString(), true));
		}

		RequestGenerator applyCouponReqGen = absolutHelper.applyCoupon(tokens[0],couponId);
		System.out.println("\nPrinting applyCoupon API response :\n\n"
			+ applyCouponReqGen.respvalidate.returnresponseasstring());

		AssertJUnit.assertEquals("applyCoupon API is not working",
				Integer.parseInt(successStatusCode), applyCouponReqGen.response.getStatus());
		AssertJUnit.assertTrue("applyCoupon API response status message is not a success status message",
			applyCouponReqGen.respvalidate.NodeValue(AbsolutNodes.STATUS_MESSAGE.toString(), true).contains(successStatusMsg));
		AssertJUnit.assertTrue("applyCoupon API response status type is not a success status type",
			applyCouponReqGen.respvalidate.NodeValue(AbsolutNodes.STATUS_TYPE.toString(), true).contains(successStatusType));
		absolutHelper.performSignOutOperation(userName, tokens[1]);
		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;

		System.out.println(
				"\nTime taken to execute - TestCase - CheckoutServiceTests_applyCoupon_verifySuccessStatusResponse : "
						+ timeTaken + " seconds\n");
		log.info("\nTime taken to execute - TestCase - CheckoutServiceTests_applyCoupon_verifySuccessStatusResponse : "
				+ timeTaken + " seconds\n");
	}
	/**
	 * This Test Case used to invoke absolut applyCoupon API and verify
	 * coupon is applied to cart
	 * 
	 * @param userName
	 * @param password
	 * @param successStatusCode
	 * @param successStatusMsg
	 * @param successStatusType
	 */
	@Test(groups = { "Sanity", "Regression", "MiniRegression",
			"ExhaustiveRegression" }, dataProvider = "applyCoupon_verifyCouponIsAppliedDP",dataProviderClass= CartServiceTestsDP.class, description = "1. hit IDP service and login the user. "
					+ "\n 2. hit fetchCart API. \n 3. if user's cart is empty then first add items to cart(\n a.hit Search Service API and extract some skuIDs"
					+ "\n b. Check if searched styleIds exists in user's cart or not. \n c. hit addItemToCart API giving skuId which doesnt exists in cart)"
					+ "\n 4. hit fetchAllCoupons API and fetch the coupons applicable to user's cart"
					+ "\n 5. hit applyCoupon API by giving correct coupon name fetched"
					+ "\n 6. Verify statusCode, statusMessage & statusType in APIs response must be of Success")
	public void applyCoupon_verifyCouponIsApplied(String userName, String password, String skuId,
			String couponId, String successStatusCode, String couponStatusMsg) {
		long startTime = Calendar.getInstance().getTimeInMillis();
		String tokens[] = absolutHelper.idp_GetTokens(userName, password);
		AssertJUnit.assertEquals(SUCCESS_STATUS_MSG, tokens[2]);
		RequestGenerator clearCartReqGen = absolutHelper.clearCart(tokens[0]);
		AssertJUnit.assertEquals("CheckoutService clearCart API is not working", Integer.parseInt(SUCCESS_STATUS_RESP),
				clearCartReqGen.response.getStatus());
		AssertJUnit.assertEquals("Items are not cleared from cart", "0",
				clearCartReqGen.respvalidate.NodeValue(AbsolutNodes.CART_TOTAL_COUNT.toString(), true));
		
		RequestGenerator addItemToCartReqGen = absolutHelper.addItemToCart(tokens[0], skuId, "1", "ADD");
			AssertJUnit.assertEquals("Item not added to cart", "1", 
			  addItemToCartReqGen.respvalidate.NodeValue(AbsolutNodes.CART_TOTAL_COUNT.toString(), true));
		
		RequestGenerator applyCouponReqGen = absolutHelper.applyCoupon(tokens[0],couponId);
		System.out.println("\nPrinting applyCoupon API response :\n\n"
			+ applyCouponReqGen.respvalidate.returnresponseasstring());

		AssertJUnit.assertEquals("applyCoupon API is not working",
			Integer.parseInt(successStatusCode), applyCouponReqGen.response.getStatus());

		RequestGenerator fetchCartReqGen = absolutHelper.fetchCart(tokens[0]);
		AssertJUnit.assertTrue("CheckoutService fetchCart API response status message is not a success status message",
			fetchCartReqGen.respvalidate.NodeValue(AbsolutNodes.STATUS_MESSAGE.toString(), true).contains(SUCCESS_STATUS_MSG));
		AssertJUnit.assertTrue("appliedCoupons nodes does not exists",
			fetchCartReqGen.respvalidate.DoesNodeExists(AbsolutNodes.CART_APPLIED_COUPONS_APPSTATE.toString()));
		AssertJUnit.assertEquals("Coupon doesn't applied successfully",couponStatusMsg, 
			fetchCartReqGen.respvalidate.NodeValue(AbsolutNodes.CART_APPLIED_COUPONS_APPSTATE_STATE.toString(),true));
		
		absolutHelper.performSignOutOperation(userName, tokens[1]);
		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;

		System.out.println("\nTime taken to execute - TestCase - applyCoupon_verifyCouponIsApplied : "
			+ timeTaken + " seconds\n");
	}
	
	/**
	 * This Test Case used to invoke absolut applyCoupon API and verify
	 * coupon is applied to cart
	 * 
	 * @param userName
	 * @param password
	 * @param successStatusCode
	 * @param successStatusMsg
	 * @param successStatusType
	 */
	@Test(groups = { "Sanity", "Regression", "MiniRegression",
			"ExhaustiveRegression" }, dataProvider = "applyCoupon_verifyCouponNodesDP",dataProviderClass= CartServiceTestsDP.class, description = "1. hit IDP service and login the user. "
					+ "\n 2. hit fetchCart API. \n 3. if user's cart is empty then first add items to cart(\n a.hit Search Service API and extract some skuIDs"
					+ "\n b. Check if searched styleIds exists in user's cart or not. \n c. hit addItemToCart API giving skuId which doesnt exists in cart)"
					+ "\n 4. hit fetchAllCoupons API and fetch the coupons applicable to user's cart"
					+ "\n 5. hit applyCoupon API by giving correct coupon name fetched"
					+ "\n 6. Verify statusCode, statusMessage & statusType in APIs response must be of Success")
	public void applyCoupon_verifyCouponNodes(String userName, String password, String skuId,
			String couponId, String successStatusCode, String couponStatusMsg) {
		long startTime = Calendar.getInstance().getTimeInMillis();
		String tokens[] = absolutHelper.idp_GetTokens(userName, password);
		AssertJUnit.assertEquals(SUCCESS_STATUS_MSG, tokens[2]);
		RequestGenerator clearCartReqGen = absolutHelper.clearCart(tokens[0]);
		AssertJUnit.assertEquals("CheckoutService clearCart API is not working", Integer.parseInt(SUCCESS_STATUS_RESP),
				clearCartReqGen.response.getStatus());
		AssertJUnit.assertEquals("Items are not cleared from cart", "0",
				clearCartReqGen.respvalidate.NodeValue(AbsolutNodes.CART_TOTAL_COUNT.toString(), true));
		
		RequestGenerator addItemToCartReqGen = absolutHelper.addItemToCart(tokens[0],skuId, "1", "ADD");
			AssertJUnit.assertEquals("Item not added to cart", "1", 
			  addItemToCartReqGen.respvalidate.NodeValue(AbsolutNodes.CART_TOTAL_COUNT.toString(), true));
		
		RequestGenerator applyCouponReqGen = absolutHelper.applyCoupon(tokens[0],couponId);
		System.out.println("\nPrinting applyCoupon API response :\n\n"
			+ applyCouponReqGen.respvalidate.returnresponseasstring());

		AssertJUnit.assertEquals("applyCoupon API is not working",
			Integer.parseInt(successStatusCode), applyCouponReqGen.response.getStatus());

		RequestGenerator fetchCartReqGen = absolutHelper.fetchCart(tokens[0]);
		AssertJUnit.assertTrue("fetchCart API response status message is not a success status message",
			fetchCartReqGen.respvalidate.NodeValue(AbsolutNodes.STATUS_MESSAGE.toString(), true).contains(SUCCESS_STATUS_MSG));
		AssertJUnit.assertTrue("appliedCoupons nodes does not exists",
			fetchCartReqGen.respvalidate.DoesNodesExists(AbsolutNodes.getCouponApplicabilityStatusNodes()));
		AssertJUnit.assertEquals("applied coupon is incorrect", couponId, 
			fetchCartReqGen.respvalidate.NodeValue(AbsolutNodes.CART_APPLIED_COUPONS_COUPONID.toString(),true));
		AssertJUnit.assertEquals("Coupon doesn't applied successfully","\"SUCCESS\"" , 
			fetchCartReqGen.respvalidate.NodeValue(AbsolutNodes.CART_APPLIED_COUPONS_APPSTATE_STATE.toString(),true));
		AssertJUnit.assertEquals("Coupon applicability message is incorrect", couponStatusMsg, 
			fetchCartReqGen.respvalidate.NodeValue(AbsolutNodes.CART_APPLIED_COUPONS_APPSTATE_MSG.toString(),true));
		
		absolutHelper.performSignOutOperation(userName, tokens[1]);
		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;

		System.out.println("\nTime taken to execute - TestCase - applyCoupon_verifyCouponIsApplied : "
			+ timeTaken + " seconds\n");
	}
	/**
	 * This Test Case used to invoke CheckoutService fetchAllCoupons API and
	 * verify for success response
	 * 
	 * @param userName
	 * @param password
	 * @param successRespCode
	 */
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression", "MiniRegression",
			"ExhaustiveRegression" }, dataProvider = "fetchAllCoupons_verifyAPIIsUpDP",dataProviderClass= CartServiceTestsDP.class, description = "1. hit IDP service and login the user. "
					+ "\n 2. hit fetchAllCoupons API and fetch the coupons applicable to user's cart"
					+ "\n 3. Verify APIs response statusCode must be 200")
	public void fetchAllCoupons_verifyAPIIsUp(String userName, String password,
			String successRespCode, String successStatusMsg, String successStatusType) {
		long startTime = Calendar.getInstance().getTimeInMillis();
		String tokens[] = absolutHelper.idp_GetTokens(userName, password);
		AssertJUnit.assertEquals(SUCCESS_STATUS_MSG, tokens[2]);
		
		RequestGenerator fetchAllCouponsReqGen = absolutHelper.fetchAllCoupons(tokens[0]);
		System.out.println("\nPrinting fetchAllCoupons API response :\n\n"
			+ fetchAllCouponsReqGen.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("fetchAllCoupons API is not working",
			Integer.parseInt(successRespCode), fetchAllCouponsReqGen.response.getStatus());
		AssertJUnit.assertTrue("fetchAllCoupons API response status message is not a success status message",
			fetchAllCouponsReqGen.respvalidate.NodeValue(AbsolutNodes.STATUS_MESSAGE.toString(), true).contains(successStatusMsg));
		AssertJUnit.assertTrue("fetchAllCoupons API response status type is not a success status type",
			fetchAllCouponsReqGen.respvalidate.NodeValue(AbsolutNodes.STATUS_TYPE.toString(), true).contains(successStatusType));
		absolutHelper.performSignOutOperation(userName, tokens[1]);
		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;

		System.out.println("\nTime taken to execute - TestCase - CheckoutServiceTests_fetchAllCoupons_verifySuccessResponse : "
			+ timeTaken + " seconds\n");
		
	}
//DDD to check
	/**
	 * This Test Case used to invoke CheckoutService fetchAllCoupons API and
	 * verify for success status response
	 * 
	 * @param userName
	 * @param password
	 * @param successStatusCode
	 * @param successStatusMsg
	 * @param successStatusType
	 */
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression", "MiniRegression",
			"ExhaustiveRegression" }, dataProvider = "fetchAllCouponsDataProvider_positiveCases",dataProviderClass= CartServiceTestsDP.class, description = "1. hit IDP service and login the user. "
					+ "\n 2. hit fetchAllCoupons API and fetch the coupons applicable to user's cart"
					+ "\n 3. Verify APIs response statusCode, statusMessage & statusType must be of Success")
	public void fetchAllCoupons_verifySuccessStatusResponse(String userName, String password,
			String successStatusCode, String successStatusMsg, String successStatusType) {
		long startTime = Calendar.getInstance().getTimeInMillis();
		String tokens[] = absolutHelper.idp_GetTokens(userName, password);
		AssertJUnit.assertEquals(successStatusMsg, tokens[2]);
		System.out.println("xid is:"+tokens[0]);
		
		RequestGenerator fetchAllCouponsReqGen = absolutHelper.fetchAllCoupons(tokens[0]);
		System.out.println("\nPrinting CheckoutService fetchAllCoupons API response :\n\n"
			+ fetchAllCouponsReqGen.respvalidate.returnresponseasstring());
		
		AssertJUnit.assertTrue("CheckoutService fetchAllCoupons API response status code is not a success status code",
			fetchAllCouponsReqGen.respvalidate.NodeValue(AbsolutNodes.STATUS_CODE.toString(), true).contains(successStatusCode));

		AssertJUnit.assertTrue("CheckoutService fetchAllCoupons API response status message is not a success status message",
			fetchAllCouponsReqGen.respvalidate.NodeValue(AbsolutNodes.STATUS_MESSAGE.toString(), true).contains(successStatusMsg));

		AssertJUnit.assertTrue("CheckoutService fetchAllCoupons API response status type is not a success status type",
			fetchAllCouponsReqGen.respvalidate.NodeValue(AbsolutNodes.STATUS_TYPE.toString(), true).contains(successStatusType));
		absolutHelper.performSignOutOperation(userName, tokens[1]);
		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;

		System.out.println("\nTime taken to execute - TestCase - CheckoutServiceTests_fetchAllCoupons_verifySuccessStatusResponse : "
			+ timeTaken + " seconds\n");
		
	}
	

	/**
	 * 
	 * @param userName
	 * @param password
	 * @param successRespCode
	 */
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression", "MiniRegression",
			"ExhaustiveRegression" }, dataProvider = "getLoyaltyPoints_verifyAPIIsUpDP",dataProviderClass= CartServiceTestsDP.class)
	public void getLoyaltyPoints_verifyAPIIsUp(String userName, String password,
			String uIdx,String successRespCode,String successStatusType) {
		long startTime = Calendar.getInstance().getTimeInMillis();

		RequestGenerator getLoyaltyPointsReqGen = absolutHelper.getLoyaltyPoints(uIdx);
		System.out.println("\nPrinting getLoyaltyPoints API response :\n\n"
			+getLoyaltyPointsReqGen.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("getLoyaltyPoints API is not working",
			Integer.parseInt(successRespCode), getLoyaltyPointsReqGen.response.getStatus());
		AssertJUnit.assertTrue("fetchAllCoupons API response status type is not a success status type",
			getLoyaltyPointsReqGen.respvalidate.NodeValue(AbsolutNodes.STATUS_TYPE.toString(), true).contains(successStatusType));
		String getLoyaltyPointsResponse = getLoyaltyPointsReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting getLoyaltyPoints API response :\n\n" + getLoyaltyPointsResponse + "\n");

		String availableLoyaltyPoints = JsonPath.read(getLoyaltyPointsResponse, "$.userAccountEntry..activePointsBalance").toString().replace("[", "")
			.replace("]", "").trim();
		System.out.println("For user " + userName + " the available Loyalty Points are " + availableLoyaltyPoints);
	
		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;

		System.out.println("\nTime taken to execute - TestCase - getLoyaltyPoints_verifyAPIIsUp : "
			+ timeTaken + " seconds\n");
		
	}
	
	/**
	 * @author This Test Case used to invoke 
	 *         applyLoyaltyPoints API and verify 
	 * 
	 * @param userName
	 * @param password
	 * @param successRespCode
	 */
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression", "MiniRegression",
			"ExhaustiveRegression" }, dataProvider = "applyLoyaltyPoints_verifyAPIIsUpDP",dataProviderClass= CartServiceTestsDP.class, description = "1. hit IDP service and login the user. "
					+ "\n 2. hit fetchCart API. \n 3. if user's cart is empty then first add items to cart(\n a.hit Search Service API and extract some skuIDs"
					+ "\n b. Check if searched styleIds exists in user's cart or not. \n c. hit addItemToCart API giving skuId which doesnt exists in cart)"
					+ "\n 4. if loyaltyPoints are already applied to cart, remove it"
					+ "\n 5. hit getLoyaltyPoints API to fetch the available LP of user"
					+ "\n 6. hit applyLoyaltyPoints API giving the fetched LP to be applied on cart"
					+ "\n 7. Verify statusCode must be 200 in APIs response")
	public void applyLoyaltyPoints_verifyAPIIsUp(String userName, String password,String successRespCode,
			String successStatusMsg) {
		long startTime = Calendar.getInstance().getTimeInMillis();
		
		String tokens[] = absolutHelper.idp_GetTokens(userName, password);
		AssertJUnit.assertEquals(SUCCESS_STATUS_MSG, tokens[2]);
		String availableLoyaltyPoints = "2000";
		RequestGenerator applyLoyaltyPointsReqGen = absolutHelper.applyLoyaltyPoints(tokens[0],availableLoyaltyPoints);
		System.out.println("\nPrinting applyLoyaltyPoints API response :\n\n"+applyLoyaltyPointsReqGen.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("applyLoyaltyPoints API is not working",
			Integer.parseInt(successRespCode), applyLoyaltyPointsReqGen.response.getStatus());

		AssertJUnit.assertEquals("applyLp APIs response is not of success",successStatusMsg,
				applyLoyaltyPointsReqGen.respvalidate.NodeValue(AbsolutNodes.STATUS_MESSAGE.toString(), true));
		absolutHelper.performSignOutOperation(userName, tokens[1]);
		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;

		System.out.println("\nTime taken to execute - TestCase - applyLoyaltyPoints_verifyAPIIsUp : "
			+ timeTaken + " seconds\n");
		
	}

	/**
	 * @author This Test Case used to invoke 
	 *         applyLoyaltyPoints API and verify LoyaltyPoints are applied to Cart
	 * 
	 * @param userName
	 * @param password
	 * @param successRespCode
	 */
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression", "MiniRegression",
			"ExhaustiveRegression" }, dataProvider = "applyLoyaltyPoints_verifyLPIsAppliedDP",dataProviderClass= CartServiceTestsDP.class, description = "1. hit IDP service and login the user. "
					+ "\n 2. hit fetchCart API. \n 3. if user's cart is empty then first add items to cart(\n a.hit Search Service API and extract some skuIDs"
					+ "\n b. Check if searched styleIds exists in user's cart or not. \n c. hit addItemToCart API giving skuId which doesnt exists in cart)"
					+ "\n 4. if loyaltyPoints are already applied to cart, remove it"
					+ "\n 5. hit getLoyaltyPoints API to fetch the available LP of user"
					+ "\n 6. hit applyLoyaltyPoints API giving the fetched LP to be applied on cart"
					+ "\n 7. Verify statusCode must be 200 in APIs response")
	public void applyLoyaltyPoints_verifyLPIsApplied(String userName, String password,String uIdx,
			String skuId, String successRespCode) {
		long startTime = Calendar.getInstance().getTimeInMillis();
		
		String tokens[] = absolutHelper.idp_GetTokens(userName, password);
		AssertJUnit.assertEquals(SUCCESS_STATUS_MSG, tokens[2]);
		System.out.println("xid is:"+tokens[0]);
		RequestGenerator fetchCartReqGen = absolutHelper.fetchCart(tokens[0]);
		System.out.println("\nPrinting fetchCart API response :\n\n"
				+ fetchCartReqGen.respvalidate.returnresponseasstring());
		
		AssertJUnit.assertTrue("Error while invoking fetchCart API",
			fetchCartReqGen.respvalidate.NodeValue(AbsolutNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));

		if (fetchCartReqGen.respvalidate.NodeValue(AbsolutNodes.CART_TOTAL_COUNT.toString(), true).equals(EMPTY_VALUE)) 
		{
			RequestGenerator addItemToCartReqGen = absolutHelper.addItemToCart(tokens[0],skuId, "1", "ADD");
			System.out.println("\nPrinting CheckoutService addItemToCart API response:\n\n"
			   +addItemToCartReqGen.respvalidate.returnresponseasstring());
			AssertJUnit.assertEquals("addItemToCart API is not working",
				Integer.parseInt(successRespCode), addItemToCartReqGen.response.getStatus());
		}

		if (fetchCartReqGen.respvalidate.NodeValue(AbsolutNodes.CART_USE_LOYALTY_POINTS.toString(), false).equals(TRUE_VALUE)) 
		{
			RequestGenerator removeLoyaltyPoints = absolutHelper.removeLoyaltyPoints(tokens[0]);
			System.out.println("\nPrinting removeLoyaltyPoints API response :\n\n"
				+ removeLoyaltyPoints.respvalidate.returnresponseasstring());
			AssertJUnit.assertTrue("Error while invoking removeLoyaltyPoints API",
				removeLoyaltyPoints.respvalidate.NodeValue(AbsolutNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));
		}

		RequestGenerator getLoyaltyPointsReqGen = absolutHelper.getLoyaltyPoints(uIdx);
		String getLoyaltyPointsResponse = getLoyaltyPointsReqGen.respvalidate.returnresponseasstring();
		//System.out.println("\nPrinting getLoyaltyPoints API response :\n\n" + getLoyaltyPointsResponse + "\n");

		String availableLoyaltyPoints = JsonPath.read(getLoyaltyPointsResponse, "$.userAccountEntry..activePointsBalance").toString().replace("[", "")
			.replace("]", "").trim();

		System.out.println("For user " + userName + " the available Loyalty Points are " + availableLoyaltyPoints);
		
		RequestGenerator applyLoyaltyPointsReqGen = absolutHelper.applyLoyaltyPoints(tokens[0],availableLoyaltyPoints);
		//System.out.println("\nPrinting applyLoyaltyPoints API response :\n\n"+applyLoyaltyPointsReqGen.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("CheckoutService applyLoyaltyPoints API is not working",
			Integer.parseInt(successRespCode), applyLoyaltyPointsReqGen.response.getStatus());
		fetchCartReqGen = absolutHelper.fetchCart(tokens[0]);
		AssertJUnit.assertEquals("Loyalty Points Not Applied",TRUE_VALUE,
			fetchCartReqGen.respvalidate.NodeValue(AbsolutNodes.CART_USE_LOYALTY_POINTS.toString(), true));
		absolutHelper.performSignOutOperation(userName, tokens[1]);
		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;

		System.out.println("\nTime taken to execute - TestCase - applyLoyaltyPoints_verifyLPIsApplied : "
			+ timeTaken + " seconds\n");
		
	}
	
	/**
	 * @author This Test Case used to invoke 
	 *         applyLoyaltyPoints API and verify LoyaltyPoints are applied to Cart
	 * 
	 * @param userName
	 * @param password
	 * @param successRespCode
	 */
	@Test(groups = {  }, dataProvider = "applyLoyaltyPoints_verifyMaxUsagePercentageDP",dataProviderClass= CartServiceTestsDP.class, description = "1. hit IDP service and login the user. "
					+ "\n 2. hit fetchCart API. \n 3. if user's cart is empty then first add items to cart(\n a.hit Search Service API and extract some skuIDs"
					+ "\n b. Check if searched styleIds exists in user's cart or not. \n c. hit addItemToCart API giving skuId which doesnt exists in cart)"
					+ "\n 4. if loyaltyPoints are already applied to cart, remove it"
					+ "\n 5. hit getLoyaltyPoints API to fetch the available LP of user"
					+ "\n 6. hit applyLoyaltyPoints API giving the fetched LP to be applied on cart"
					+ "\n 7. Verify statusCode must be 200 in APIs response")
	public void applyLoyaltyPoints_verifyMaxUsagePercentage(String userName, String password,String uIdx,
			String skuId, String successRespCode) {
		long startTime = Calendar.getInstance().getTimeInMillis();
//		userName ="testing_wallet@myntra.com";
//		password = "123456";
//		uIdx = "f9e3c26b.b605.4604.acac.5b3bd46599168MZdAUd9dX";
		String tokens[] = absolutHelper.idp_GetTokens(userName, password);
		AssertJUnit.assertEquals(SUCCESS_STATUS_MSG, tokens[2]);
		System.out.println("xid is:"+tokens[0]);
		RequestGenerator fetchCartReqGen = absolutHelper.fetchCart(tokens[0]);
		System.out.println("\nPrinting fetchCart API response :\n\n"
				+ fetchCartReqGen.respvalidate.returnresponseasstring());
		
		AssertJUnit.assertTrue("Error while invoking fetchCart API",
			fetchCartReqGen.respvalidate.NodeValue(AbsolutNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));

		if (fetchCartReqGen.respvalidate.NodeValue(AbsolutNodes.CART_TOTAL_COUNT.toString(), true).equals(EMPTY_VALUE)) 
		{
			RequestGenerator addItemToCartReqGen = absolutHelper.addItemToCart(tokens[0],skuId, "1", "ADD");
			System.out.println("\nPrinting CheckoutService addItemToCart API response:\n\n"
			   +addItemToCartReqGen.respvalidate.returnresponseasstring());
			AssertJUnit.assertEquals("addItemToCart API is not working",
				Integer.parseInt(successRespCode), addItemToCartReqGen.response.getStatus());
		}

		if (fetchCartReqGen.respvalidate.NodeValue(AbsolutNodes.CART_USE_LOYALTY_POINTS.toString(), false).equals(TRUE_VALUE)) 
		{
			RequestGenerator removeLoyaltyPoints = absolutHelper.removeLoyaltyPoints(tokens[0]);
			System.out.println("\nPrinting removeLoyaltyPoints API response :\n\n"
				+ removeLoyaltyPoints.respvalidate.returnresponseasstring());
			AssertJUnit.assertTrue("Error while invoking removeLoyaltyPoints API",
				removeLoyaltyPoints.respvalidate.NodeValue(AbsolutNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));
		}

		RequestGenerator getLoyaltyPointsReqGen = absolutHelper.getLoyaltyPoints(uIdx);
		String getLoyaltyPointsResponse = getLoyaltyPointsReqGen.respvalidate.returnresponseasstring();
		//System.out.println("\nPrinting getLoyaltyPoints API response :\n\n" + getLoyaltyPointsResponse + "\n");

		String availableLoyaltyPoints = JsonPath.read(getLoyaltyPointsResponse, "$.userAccountEntry..activePointsBalance").toString().replace("[", "")
			.replace("]", "").trim();

		System.out.println("For user " + userName + " the available Loyalty Points are " + availableLoyaltyPoints);
		
		int toUseLP = (Integer.parseInt(availableLoyaltyPoints) * Integer.parseInt(loyaltyMaxPerOff))/100;
		RequestGenerator applyLoyaltyPointsReqGen = absolutHelper.applyLoyaltyPoints(tokens[0],availableLoyaltyPoints);
		//System.out.println("\nPrinting applyLoyaltyPoints API response :\n\n"+applyLoyaltyPointsReqGen.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("CheckoutService applyLoyaltyPoints API is not working",
			Integer.parseInt(successRespCode), applyLoyaltyPointsReqGen.response.getStatus());
		fetchCartReqGen = absolutHelper.fetchCart(tokens[0]);
		double vatCharge,shippingCharge,totalMrpDecimal,bagDiscountDecimal,totalCouponDiscountDecimal,totalDiscountDecimal
		,totalPriceDecimal, calculatedTotalPriceDecimal;	
		String response = fetchCartReqGen.respvalidate.returnresponseasstring();
		
		vatCharge = JsonPath.read(response, "$.data[0].cartItemEntries[0].unitAdditionalCharge");
		shippingCharge = (double)(int)JsonPath.read(response, "$.data[0].shippingCharge");
		bagDiscountDecimal = JsonPath.read(response, "$.data[0].bagDiscountDecimal");
		totalDiscountDecimal = JsonPath.read(response, "$.data[0].totalDiscountDecimal");
		totalMrpDecimal = JsonPath.read(response, "$.data[0].totalMrpDecimal");
		totalCouponDiscountDecimal = JsonPath.read(response, "$.data[0].totalCouponDiscountDecimal");
		totalPriceDecimal =  JsonPath.read(response, "$.data[0].totalPriceDecimal");
		System.out.println("value of vat charge is :"+vatCharge);	
		System.out.println("value of shippingCharge is :"+shippingCharge);
		System.out.println("value of bagDiscountDecimal is :"+bagDiscountDecimal);
		System.out.println("value of totalDiscountDecimal is :"+totalDiscountDecimal);
		System.out.println("value of totalCouponDiscountDecimal is :"+totalCouponDiscountDecimal);
		System.out.println("value of totalMrpDecimal is :"+totalMrpDecimal);
		System.out.println("value of totalPriceDecimal is :"+totalPriceDecimal);

		calculatedTotalPriceDecimal = totalMrpDecimal + shippingCharge + vatCharge - bagDiscountDecimal - totalDiscountDecimal-totalCouponDiscountDecimal;
		System.out.println("value of calculatedTotalPriceDecimal is :"+calculatedTotalPriceDecimal);
		AssertJUnit.assertEquals("Loyalty Points Not Applied",TRUE_VALUE,
			fetchCartReqGen.respvalidate.NodeValue(AbsolutNodes.CART_USE_LOYALTY_POINTS.toString(), true));
//		AssertJUnit.assertEquals("MaxUsage Limit is not as per the widget-key value",loyaltyMaxPerOff, 
//			Integer.parseInt(fetchCartReqGen.respvalidate.NodeValue(AbsolutNodes.LOYALTY_MAXUSAGE_PER.toString(),true)));
		AssertJUnit.assertEquals("No of loyalty points used are incorrect",toUseLP,
			fetchCartReqGen.respvalidate.NodeValue(AbsolutNodes.CART_LOYALTY_POINTS_USED.toString(), true));
		absolutHelper.performSignOutOperation(userName, tokens[1]);
		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;

		System.out.println("\nTime taken to execute - TestCase - applyLoyaltyPoints_verifyMaxUsagePercentage : "
			+ timeTaken + " seconds\n");
		
	}

	/**
	 * @author This Test Case used to invoke 
	 *         applyLoyaltyPoints API and verify 
	 * 
	 * @param userName
	 * @param password
	 * @param successRespCode
	 */
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression", "MiniRegression",
			"ExhaustiveRegression" }, dataProvider = "removeLoyaltyPoints_verifyAPIIsUpDP",dataProviderClass= CartServiceTestsDP.class, description = "1. hit IDP service and login the user. "
					+ "\n 2. hit fetchCart API. \n 3. if user's cart is empty then first add items to cart(\n a.hit Search Service API and extract some skuIDs"
					+ "\n b. Check if searched styleIds exists in user's cart or not. \n c. hit addItemToCart API giving skuId which doesnt exists in cart)"
					+ "\n 4. if loyaltyPoints are already applied to cart, remove it"
					+ "\n 5. hit getLoyaltyPoints API to fetch the available LP of user"
					+ "\n 6. hit applyLoyaltyPoints API giving the fetched LP to be applied on cart"
					+ "\n 7. Verify statusCode must be 200 in APIs response")
	public void removeLoyaltyPoints_verifyAPIIsUp(String userName, String password,String successRespCode, 
			String successStatusMsg) {
		long startTime = Calendar.getInstance().getTimeInMillis();
		
		String tokens[] = absolutHelper.idp_GetTokens(userName, password);
		AssertJUnit.assertEquals(SUCCESS_STATUS_MSG, tokens[2]);
		RequestGenerator removeLoyaltyPointsReqGen = absolutHelper.removeLoyaltyPoints(tokens[0]);
		System.out.println("\nPrinting removeLoyaltyPoints API response :\n\n"+removeLoyaltyPointsReqGen.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("removeLoyaltyPoints API is not working",
			Integer.parseInt(successRespCode), removeLoyaltyPointsReqGen.response.getStatus());
		AssertJUnit.assertEquals("removeLoyaltyPoints APIs response is not of success",successStatusMsg,
				removeLoyaltyPointsReqGen.respvalidate.NodeValue(AbsolutNodes.STATUS_MESSAGE.toString(), true));
		absolutHelper.performSignOutOperation(userName, tokens[1]);
		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;

		System.out.println("\nTime taken to execute - TestCase - applyLoyaltyPoints_verifyAPIIsUp : "
			+ timeTaken + " seconds\n");
		
	}
	/**
	 * This Test Case used to invoke CheckoutService removeLoyaltyPoints API and
	 * verify for success response
	 * 
	 * @param userName
	 * @param password
	 * @param successRespCode
	 */
	@Test(groups = { "Sanity", "Regression", "MiniRegression",
			"ExhaustiveRegression" }, dataProvider = "removeLoyaltyPointst_verifyLPISRemovedDP",dataProviderClass= CartServiceTestsDP.class, description = "1. hit IDP service and login the user. "
					+ "\n 2. hit fetchCart API. \n 3. if user's cart is empty then first add items to cart(\n a.hit Search Service API and extract some skuIDs"
					+ "\n b. Check if searched styleIds exists in user's cart or not. \n c. hit addItemToCart API giving skuId which doesnt exists in cart)"
					+ "\n 4. if loyaltyPoints are not applied to cart, apply it."
					+ "\n (a. hit getLoyaltyPoints API to fetch the available LP of user"
					+ "\n b. hit applyLoyaltyPoints API giving the fetched LP to be applied on cart)"
					+ "\n 5. hit removeLoyaltyPoints API to remove applied loyaltyPoints from cart"
					+ "\n 6. Verify statusCode must be 200 in APIs response")
	public void removeLoyaltyPointst_verifyLPISRemoved(String userName, String password,String uIdx, String skuId,
			String successRespCode) {
		long startTime = Calendar.getInstance().getTimeInMillis();
		String tokens[] = absolutHelper.idp_GetTokens(userName, password);
		AssertJUnit.assertEquals(SUCCESS_STATUS_MSG, tokens[2]);
		System.out.println("xid is:"+tokens[0]);
		RequestGenerator fetchCartReqGen = absolutHelper.fetchCart(tokens[0]);
		System.out.println("\nPrinting CheckoutService fetchCart API response :\n\n"
			+ fetchCartReqGen.respvalidate.returnresponseasstring());
		
		AssertJUnit.assertTrue("fetchCart API response status type is not a success status type",
			fetchCartReqGen.respvalidate.NodeValue(AbsolutNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));

		if (fetchCartReqGen.respvalidate.NodeValue(AbsolutNodes.CART_TOTAL_COUNT.toString(), true).equals(EMPTY_VALUE)) 
		{
			RequestGenerator addItemtoCartReqGen = absolutHelper.addItemToCart(tokens[0],skuId, "1", "ADD");
			AssertJUnit.assertEquals("item not added to cart", "1", 
				addItemtoCartReqGen.respvalidate.NodeValue(AbsolutNodes.CART_TOTAL_COUNT.toString(), true));
		}

		if (fetchCartReqGen.respvalidate.NodeValue(AbsolutNodes.CART_USE_LOYALTY_POINTS.toString(), false)
				.equals(FALSE_VALUE)) {
			RequestGenerator getLoyaltyPointsReqGen = absolutHelper.getLoyaltyPoints(uIdx);
			String getLoyaltyPointsResponse = getLoyaltyPointsReqGen.respvalidate.returnresponseasstring();
			//System.out.println("\nPrinting getLoyaltyPoints API response :\n\n" + getLoyaltyPointsResponse + "\n");

			String availableLoyaltyPoints = JsonPath.read(getLoyaltyPointsResponse, "$.userAccountEntry..activePointsBalance").toString()
					.replace("[", "").replace("]", "").trim();

			System.out.println("For user " + userName + " the available Loyalty Points are " + availableLoyaltyPoints);
			
			RequestGenerator applyLoyaltyPointsReqGen = absolutHelper.applyLoyaltyPoints(tokens[0],availableLoyaltyPoints);
			System.out.println("\nPrinting CheckoutService applyLoyaltyPoints API response :\n\n"
				+ applyLoyaltyPointsReqGen.respvalidate.returnresponseasstring());
			
			AssertJUnit.assertTrue("Error while invoking applyLoyaltyPoints API",
				applyLoyaltyPointsReqGen.respvalidate.NodeValue(AbsolutNodes.STATUS_TYPE.toString(), true)
			    	.contains(SUCCESS_STATUS_TYPE));
		}

		RequestGenerator removeLoyaltyPointsReqGen = absolutHelper.removeLoyaltyPoints(tokens[0]);
		System.out.println("\nPrinting CheckoutService removeLoyaltyPoints API response :\n\n"
			+ removeLoyaltyPointsReqGen.respvalidate.returnresponseasstring());

		AssertJUnit.assertEquals("CheckoutService removeLoyaltyPoints API is not working",
			Integer.parseInt(successRespCode), removeLoyaltyPointsReqGen.response.getStatus());
		fetchCartReqGen = absolutHelper.fetchCart(tokens[0]);
		AssertJUnit.assertEquals("Loyalty Points are still applied",FALSE_VALUE,
				fetchCartReqGen.respvalidate.NodeValue(AbsolutNodes.CART_USE_LOYALTY_POINTS.toString(), true));
		absolutHelper.performSignOutOperation(userName, tokens[1]);
		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;

		System.out.println("\nTime taken to execute - TestCase - CheckoutServiceTests_removeLoyaltyPoints_verifySuccessResponse : "
			+ timeTaken + " seconds\n");
	}

	
	
	/**
	 * This test case is to calculation of FinalMrp in FetchCart API response
	 * 
	 * @param userName
	 * @param password
	 * @param successRespCode
	 */
	
	@Test(groups = { "Negative Cases","Smoke", "Sanity", "FoxSanity", "ProdSanity", "Regression", "MiniRegression",
			"ExhaustiveRegression" }, dataProvider = "fetchCart_VerifyShippingChargesDP",dataProviderClass= CartServiceTestsDP.class, description = "1. hit IDP service to login"
					+ "\n 2. hit fetchCart API" + "\n 3.Check the service response must be 200")
	public void fetchCart_VerifyShippingCharges(String userName, String password,
			String skuId,String successRespCode) {
		long startTime = Calendar.getInstance().getTimeInMillis();
		String tokens[] = absolutHelper.idp_GetTokens(userName, password);
		AssertJUnit.assertEquals(SUCCESS_STATUS_MSG, tokens[2]);
		System.out.println("xid is:"+tokens[0]);
		
		RequestGenerator clearCartReqGen = absolutHelper.clearCart(tokens[0]);
		AssertJUnit.assertEquals("CheckoutService clearCart API is not working", 
			Integer.parseInt(successRespCode),clearCartReqGen.response.getStatus());

		RequestGenerator addItemToCartReqGen = absolutHelper.addItemToCart(tokens[0],skuId, "1", "ADD");
		AssertJUnit.assertEquals("addItemToCart API is not working ",
			Integer.parseInt(successRespCode), addItemToCartReqGen.response.getStatus());
			
		RequestGenerator fetchCartReqGen = absolutHelper.fetchCart(tokens[0]);
		System.out.println("\nPrinting fetchCart API response :\n\n"+fetchCartReqGen.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("fetchCart API is not working",
			Integer.parseInt("200"), fetchCartReqGen.response.getStatus());
			
		int shippingCharge;			
		String response = fetchCartReqGen.respvalidate.returnresponseasstring();
		shippingCharge =JsonPath.read(response, "$.data[0].shippingCharge");
		//cart price before applying shipping
		double totalMRPDecimal = JsonPath.read(response, "$.data[0].totalMrpDecimal");
		
		//String key ="shipping.charges.cartlimit";
		System.out.println("carts shipping charge"+shippingCharge);		
		System.out.println("value of shippingCartLimit  :"+shippingChargesCartLimit);
		if(totalMRPDecimal < Integer.parseInt(shippingChargesCartLimit))
			AssertJUnit.assertEquals("Cart final MRP is less than "+shippingChargesCartLimit+", shipping charges should be applied", Integer.parseInt(shippingChargesAmount), shippingCharge);
		else
			AssertJUnit.assertEquals("Cart final MRP is more than "+shippingChargesCartLimit+", shipping charges should be applied", Integer.parseInt("0"), shippingCharge);
				
		absolutHelper.performSignOutOperation(userName,tokens[1]);
		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;

		System.out.println("\nTime taken to execute - TestCase - fetchCart_VerifyCalculation : "
			+ timeTaken + " seconds\n");
	}
	
	/**
	 * This test case is to calculation of FinalMrp in FetchCart API response
	 * 
	 * @param userName
	 * @param password
	 * @param successRespCode
	 */
	@Test(groups = { "Negative Cases","Smoke", "Sanity", "FoxSanity", "ProdSanity", "Regression", "MiniRegression",
			"ExhaustiveRegression" }, dataProvider = "applyCouponDiscount_VerifyVATChargesDP",dataProviderClass= CartServiceTestsDP.class, description = "1. hit IDP service to login"
					+ "\n 2. hit fetchCart API" + "\n 3.Check the service response must be 200")
	public void applyCouponDiscount_VerifyVATCharges(String userName, String password,
			String skuId,String couponId, String expected, String successRespCode) {
		long startTime = Calendar.getInstance().getTimeInMillis();
		String tokens[] = absolutHelper.idp_GetTokens(userName, password);
		AssertJUnit.assertEquals(SUCCESS_STATUS_MSG, tokens[2]);
		System.out.println("xid is:"+tokens[0]);
		RequestGenerator clearCartReqGen = absolutHelper.clearCart(tokens[0]);
		AssertJUnit.assertEquals("CheckoutService clearCart API is not working", 
			Integer.parseInt(successRespCode),clearCartReqGen.response.getStatus());
		
		RequestGenerator addItemToCartReqGen = absolutHelper.addItemToCart(tokens[0],skuId, "1", "ADD");
		System.out.println("\nPrinting addToCart API response :\n\n"+addItemToCartReqGen.respvalidate.returnresponseasstring());
		
		AssertJUnit.assertEquals("addItemToCart API is not working ",
			Integer.parseInt(successRespCode), addItemToCartReqGen.response.getStatus());
		RequestGenerator fetchCartReq = absolutHelper.fetchCart(tokens[0]);
		
		AssertJUnit.assertEquals("Item is not added to cart", "1",
			fetchCartReq.respvalidate.NodeValue(AbsolutNodes.CART_TOTAL_COUNT.toString(), true));
			
		//String CouponId ="\"myntraemp20\"";
		RequestGenerator applyCouponReqGen = absolutHelper.applyCoupon(tokens[0],couponId);
		
		AssertJUnit.assertEquals("applyCoupon API is not working", Integer.parseInt(successRespCode),
			applyCouponReqGen.response.getStatus());
		
		RequestGenerator fetchCartReqGen = absolutHelper.fetchCart(tokens[0]);
		System.out.println("\nPrinting fetchCart API response :\n\n"+fetchCartReqGen.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("fetchCart API is not working",
			Integer.parseInt("200"), fetchCartReqGen.response.getStatus());
		
		
		double vatCharge;	
		String response = fetchCartReqGen.respvalidate.returnresponseasstring();
		vatCharge =JsonPath.read(response, "$.data[0].vatCharge");
		if(expected.equals("NO")){
			if(vatCharge != 0)
				AssertJUnit.assertTrue("Less than 20 percent coupon applied, vat should be 0", vatCharge==0);
		}else
			if(expected.equals("YES")){
				AssertJUnit.assertTrue("More than 20 percent coupon applied, vat should be more than 0", vatCharge!=0);
			}
		
		absolutHelper.performSignOutOperation(userName,tokens[1]);
		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;

		System.out.println("\nTime taken to execute - TestCase - fetchCart_VerifyCalculation : "
			+ timeTaken + " seconds\n");
	}
	
	
	/**
	 * This test case is to calculation of FinalMrp in FetchCart API response
	 * 
	 * @param userName
	 * @param password
	 * @param successRespCode
	 */
	@Test(groups = { "Negative Cases","Smoke", "Sanity", "FoxSanity", "ProdSanity", "Regression", "MiniRegression",
			"ExhaustiveRegression" }, dataProvider = "fetchCart_VerifyCalculationDP",dataProviderClass= CartServiceTestsDP.class, description = "1. hit IDP service to login"
					+ "\n 2. hit fetchCart API" + "\n 3.Check the service response must be 200")
	public void fetchCart_VerifyCalculation(String userName, String password,String skuId,
			String successRespCode) {
		long startTime = Calendar.getInstance().getTimeInMillis();
		userName = "virbundle@myntra.com";
		password = "123456";
		String tokens[] = absolutHelper.idp_GetTokens(userName, password);
		AssertJUnit.assertEquals(SUCCESS_STATUS_MSG, tokens[2]);
		System.out.println("xid is:"+tokens[0]);
		
		AssertJUnit.assertEquals(SUCCESS_STATUS_MSG, tokens[2]);
		RequestGenerator clearCartReqGen = absolutHelper.clearCart(tokens[0]);
		AssertJUnit.assertEquals("CheckoutService clearCart API is not working", 
			Integer.parseInt(successRespCode),clearCartReqGen.response.getStatus());

		RequestGenerator addItemToCartReqGen = absolutHelper.addItemToCart(tokens[0],skuId, "1", "ADD");
			
		AssertJUnit.assertEquals("addItemToCart API is not working ",
			Integer.parseInt(successRespCode), addItemToCartReqGen.response.getStatus());
			
		RequestGenerator fetchCartReqGen = absolutHelper.fetchCart(tokens[0]);
		System.out.println("\nPrinting fetchCart API response :\n\n"+fetchCartReqGen.respvalidate.returnresponseasstring());
		
		AssertJUnit.assertEquals("fetchCart API is not working",
			Integer.parseInt("200"), fetchCartReqGen.response.getStatus());
		

		double vatCharge,shippingCharge,totalMrpDecimal,bagDiscountDecimal,totalCouponDiscountDecimal,totalDiscountDecimal
		,totalPriceDecimal, calculatedTotalPriceDecimal;	
		String response = fetchCartReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nfetchCart response :\n\n"+ fetchCartReqGen.respvalidate.returnresponseasstring());
		
		vatCharge = JsonPath.read(response, "$.data[0].vatCharge");
		shippingCharge = (double)(int)JsonPath.read(response, "$.data[0].shippingCharge");
		bagDiscountDecimal = JsonPath.read(response, "$.data[0].bagDiscountDecimal");
		totalDiscountDecimal = JsonPath.read(response, "$.data[0].totalDiscountDecimal");
		totalMrpDecimal = JsonPath.read(response, "$.data[0].totalMrpDecimal");
		totalCouponDiscountDecimal = JsonPath.read(response, "$.data[0].totalCouponDiscountDecimal");
		totalPriceDecimal =  JsonPath.read(response, "$.data[0].totalPriceDecimal");
		System.out.println("value of vat charge is :"+vatCharge);	
		System.out.println("value of shippingCharge is :"+shippingCharge);
		System.out.println("value of bagDiscountDecimal is :"+bagDiscountDecimal);
		System.out.println("value of totalDiscountDecimal is :"+totalDiscountDecimal);
		System.out.println("value of totalCouponDiscountDecimal is :"+totalCouponDiscountDecimal);
		System.out.println("value of totalMrpDecimal is :"+totalMrpDecimal);
		System.out.println("value of totalPriceDecimal is :"+totalPriceDecimal);

		calculatedTotalPriceDecimal = totalMrpDecimal + shippingCharge + vatCharge - bagDiscountDecimal - totalDiscountDecimal-totalCouponDiscountDecimal;
		System.out.println("value of calculatedTotalPriceDecimal is :"+calculatedTotalPriceDecimal);
		absolutHelper.performSignOutOperation(userName,tokens[1]);
		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;

		System.out.println("\nTime taken to execute - TestCase - fetchCart_VerifyCalculation : "
			+ timeTaken + " seconds\n");
		

	}
	
	/**
	 * This test case is to calculation of FinalMrp with Coupon Application in FetchCart API response
	 * 
	 * @param userName
	 * @param password
	 * @param successRespCode
	 */
	@Test(groups = { "Negative Cases","Smoke", "Sanity", "FoxSanity", "ProdSanity", "Regression", "MiniRegression",
			"ExhaustiveRegression" }, dataProvider = "applyCoupon_VerifyFetchCartCalculationDP",dataProviderClass= CartServiceTestsDP.class, description = "1. hit IDP service to login"
					+ "\n 2. hit fetchCart API" + "\n 3.Check the service response must be 200")
	public void applyCoupon_VerifyFetchCartCalculation(String userName, String password,String skuId,
			String couponId,String successRespCode) {
		long startTime = Calendar.getInstance().getTimeInMillis();
		
		String tokens[] = absolutHelper.idp_GetTokens(userName, password);
		AssertJUnit.assertEquals(SUCCESS_STATUS_MSG, tokens[2]);
				
		RequestGenerator clearCartReqGen = absolutHelper.clearCart(tokens[0]);
		AssertJUnit.assertEquals("CheckoutService clearCart API is not working", Integer.parseInt(SUCCESS_STATUS_RESP),
			clearCartReqGen.response.getStatus());

		RequestGenerator addItemToCartReqGen = absolutHelper.addItemToCart(tokens[0],skuId, "1", "ADD");
		  AssertJUnit.assertEquals("addItemToCart API is not working",
					Integer.parseInt(successRespCode), addItemToCartReqGen.response.getStatus());
		
		RequestGenerator fetchCartReqGen = absolutHelper.fetchCart(tokens[0]);
		AssertJUnit.assertEquals("fetchCart API is not working",
			Integer.parseInt("200"), fetchCartReqGen.response.getStatus());
		AssertJUnit.assertEquals("Item not added to cart", "1",
			fetchCartReqGen.respvalidate.NodeValue(AbsolutNodes.CART_TOTAL_COUNT.toString(), true) );
		System.out.println("\nPrinting CheckoutService fetchCart API response :\n\n"+fetchCartReqGen.respvalidate.returnresponseasstring());

		RequestGenerator applyCouponReqGen = absolutHelper.applyCoupon(tokens[0],couponId);
		AssertJUnit.assertEquals("applyCoupon API is not working", Integer.parseInt(successRespCode),
			applyCouponReqGen.response.getStatus());
		System.out.println("\nPrinting applyCoupon API response :\n\n"
			+ applyCouponReqGen.respvalidate.returnresponseasstring());

		fetchCartReqGen = absolutHelper.fetchCart(tokens[0]);
		double vatCharge,shippingCharge,totalMrpDecimal,bagDiscountDecimal,totalCouponDiscountDecimal,totalDiscountDecimal
		,totalPriceDecimal, calculatedTotalPriceDecimal;	
		String response = fetchCartReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nfetchCart response :\n\n"+ fetchCartReqGen.respvalidate.returnresponseasstring());
		
		vatCharge = JsonPath.read(response, "$.data[0].cartItemEntries[0].unitAdditionalCharge");
		shippingCharge = (double)(int)JsonPath.read(response, "$.data[0].shippingCharge");
		bagDiscountDecimal = JsonPath.read(response, "$.data[0].bagDiscountDecimal");
		totalDiscountDecimal = JsonPath.read(response, "$.data[0].totalDiscountDecimal");
		totalMrpDecimal = JsonPath.read(response, "$.data[0].totalMrpDecimal");
		totalCouponDiscountDecimal = JsonPath.read(response, "$.data[0].totalCouponDiscountDecimal");
		totalPriceDecimal =  JsonPath.read(response, "$.data[0].totalPriceDecimal");
		System.out.println("value of vat charge is :"+vatCharge);	
		System.out.println("value of shippingCharge is :"+shippingCharge);
		System.out.println("value of bagDiscountDecimal is :"+bagDiscountDecimal);
		System.out.println("value of totalDiscountDecimal is :"+totalDiscountDecimal);
		System.out.println("value of totalCouponDiscountDecimal is :"+totalCouponDiscountDecimal);
		System.out.println("value of totalMrpDecimal is :"+totalMrpDecimal);
		System.out.println("value of totalPriceDecimal is :"+totalPriceDecimal);

		calculatedTotalPriceDecimal = totalMrpDecimal + shippingCharge + vatCharge - bagDiscountDecimal - totalDiscountDecimal-totalCouponDiscountDecimal;
		System.out.println("value of calculatedTotalPriceDecimal is :"+calculatedTotalPriceDecimal);

		int calcTotalPrice, totalPrice;
		calcTotalPrice = (int)calculatedTotalPriceDecimal;
		totalPrice = (int)totalPriceDecimal;
	
		System.out.println("totalCalculatedMrp is :"+totalPriceDecimal);
		AssertJUnit.assertEquals("Calculations of TotalMrp is not correct in FetchCart response",
			calcTotalPrice,totalPrice);
		
		absolutHelper.performSignOutOperation(userName,tokens[1]);
		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;

		System.out.println("\nTime taken to execute - TestCase - applyCoupon_VerifyFetchCartCalculation : "
			+ timeTaken + " seconds\n");
		

	}
	
	/**
	 * This test case is to calculation of FinalMrp with Coupon Application in FetchCart API response
	 * 
	 * @param userName
	 * @param password
	 * @param successRespCode
	 */
	@Test(groups = { "Negative Cases","Smoke", "Sanity", "FoxSanity", "ProdSanity", "Regression", "MiniRegression",
			"ExhaustiveRegression" }, dataProvider = "applyLP_VerifyFetchCartCalculationDP",dataProviderClass= CartServiceTestsDP.class, description = "1. hit IDP service to login"
					+ "\n 2. hit fetchCart API" + "\n 3.Check the service response must be 200")
	public void applyLP_VerifyFetchCartCalculation(String userName, String password,String uIdx,String skuId,
			String successRespCode) {
		long startTime = Calendar.getInstance().getTimeInMillis();
		String tokens[] = absolutHelper.idp_GetTokens(userName, password);
		AssertJUnit.assertEquals(SUCCESS_STATUS_MSG, tokens[2]);
		RequestGenerator fetchCartReqGen = absolutHelper.fetchCart(tokens[0]);
		if(fetchCartReqGen.respvalidate.NodeValue(AbsolutNodes.CART_TOTAL_COUNT.toString(), false)=="0")
		{
			RequestGenerator addItemToCartReqGen = absolutHelper.addItemToCart(tokens[0],skuId, "1", "ADD");
			AssertJUnit.assertEquals("addItemToCart API is not working",
			Integer.parseInt(successRespCode), addItemToCartReqGen.response.getStatus());
		}
				
		fetchCartReqGen = absolutHelper.fetchCart(tokens[0]);
		AssertJUnit.assertEquals("fetchCart API is not working",
			Integer.parseInt("200"), fetchCartReqGen.response.getStatus());
		System.out.println("\nPrinting CheckoutService fetchCart API response :\n\n"+fetchCartReqGen.respvalidate.returnresponseasstring());
		
		RequestGenerator getLoyaltyPointsReqGen = absolutHelper.getLoyaltyPoints(uIdx);
		String getLoyaltyPointsResponse = getLoyaltyPointsReqGen.respvalidate.returnresponseasstring();
		//System.out.println("\nPrinting getLoyaltyPoints API response :\n\n" + getLoyaltyPointsResponse + "\n");

		String availableLoyaltyPoints = JsonPath.read(getLoyaltyPointsResponse, "$.userAccountEntry..activePointsBalance").toString().replace("[", "")
			.replace("]", "").trim();

		System.out.println("For user " + userName + " the available Loyalty Points are " + availableLoyaltyPoints);
		
		RequestGenerator applyLoyaltyPointsReqGen = absolutHelper.applyLoyaltyPoints(tokens[0],availableLoyaltyPoints);
		//System.out.println("\nPrinting applyLoyaltyPoints API response :\n\n"+applyLoyaltyPointsReqGen.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("CheckoutService applyLoyaltyPoints API is not working",
			Integer.parseInt(successRespCode), applyLoyaltyPointsReqGen.response.getStatus());
		fetchCartReqGen = absolutHelper.fetchCart(tokens[0]);
		AssertJUnit.assertEquals("Loyalty Points Not Applied",TRUE_VALUE,
			fetchCartReqGen.respvalidate.NodeValue(AbsolutNodes.CART_USE_LOYALTY_POINTS.toString(), true));
		
		double vatCharge,shippingCharge,totalMrpDecimal,bagDiscountDecimal,totalCouponDiscountDecimal,totalDiscountDecimal
		,totalPriceDecimal, calculatedTotalPriceDecimal,loyaltyPointsRedeemable;	
		String response = fetchCartReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nfetchCart response :\n\n"+ fetchCartReqGen.respvalidate.returnresponseasstring());
		
		vatCharge = JsonPath.read(response, "$.data[0].cartItemEntries[0].unitAdditionalCharge");
		shippingCharge = (double)(int)JsonPath.read(response, "$.data[0].shippingCharge");
		bagDiscountDecimal = JsonPath.read(response, "$.data[0].bagDiscountDecimal");
		totalDiscountDecimal = JsonPath.read(response, "$.data[0].totalDiscountDecimal");
		loyaltyPointsRedeemable = JsonPath.read(response,"$.data[0].loyaltyPointsRedeemable");
		totalMrpDecimal = JsonPath.read(response, "$.data[0].totalMrpDecimal");
		totalCouponDiscountDecimal = JsonPath.read(response, "$.data[0].totalCouponDiscountDecimal");
		totalPriceDecimal =  JsonPath.read(response, "$.data[0].totalPriceDecimal");
		System.out.println("value of vat charge is :"+vatCharge);	
		System.out.println("value of shippingCharge is :"+shippingCharge);
		System.out.println("value of bagDiscountDecimal is :"+bagDiscountDecimal);
		System.out.println("value of totalDiscountDecimal is :"+totalDiscountDecimal);
		System.out.println("value of totalCouponDiscountDecimal is :"+totalCouponDiscountDecimal);
		System.out.println("value of loyaltyPointsRedeemable is :"+loyaltyPointsRedeemable);
		
		System.out.println("value of totalMrpDecimal is :"+totalMrpDecimal);
		System.out.println("value of totalPriceDecimal is :"+totalPriceDecimal);

		calculatedTotalPriceDecimal = totalMrpDecimal + shippingCharge + vatCharge - loyaltyPointsRedeemable-bagDiscountDecimal - totalDiscountDecimal-totalCouponDiscountDecimal;
		System.out.println("value of calculatedTotalPriceDecimal is :"+calculatedTotalPriceDecimal);

		int calcTotalPrice, totalPrice;
		calcTotalPrice = (int)calculatedTotalPriceDecimal;
		totalPrice = (int)totalPriceDecimal;
	
		System.out.println("totalCalculatedMrp is :"+totalPriceDecimal);
		AssertJUnit.assertEquals("Calculations of TotalMrp is not correct in FetchCart response",
			calcTotalPrice,totalPrice);
		
		absolutHelper.performSignOutOperation(userName,tokens[1]);
		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;

		System.out.println("\nTime taken to execute - TestCase - applyCoupon_VerifyFetchCartCalculation : "
			+ timeTaken + " seconds\n");
		

	}
	
	/**
	 * This Test Case used to invoke CheckoutService OptTryNBuy API and verify
	 * for success response(200)
	 * 
	 * @param userName
	 * @param password
	 * @param successRespCode
	 */
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression", "MiniRegression",
			"ExhaustiveRegression" }, dataProvider = "optTryNBuyOnDelivery_verifyAPIIsUpDP",dataProviderClass= CartServiceTestsDP.class, description = "1. hit IDP service to login"
					+ "\n 2. hit OptTryAndBuy API" + "\n 3.Check the service response must be 200")
	public void optTryNBuyOnDelivery_verifyAPIIsUp(String userName, String password,
			String itemId, String successRespCode) {
		long startTime = Calendar.getInstance().getTimeInMillis();
		
		String tokens[] = absolutHelper.idp_GetTokens(userName, password);
		AssertJUnit.assertEquals(SUCCESS_STATUS_MSG, tokens[2]);
		
		System.out.println("xid is:"+tokens[0]);
		RequestGenerator OptTNBReqGen = absolutHelper.optTryNBuyOnDelivery(tokens[0],itemId);
		
		AssertJUnit.assertEquals("optTryAndBuy API is not working", Integer.parseInt(successRespCode),
				OptTNBReqGen.response.getStatus());
		
		System.out.println("response:"+OptTNBReqGen.respvalidate.returnresponseasstring());
		absolutHelper.performSignOutOperation(userName,tokens[1]);
		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;

		System.out.println("\nTime taken to execute - TestCase - optTryNBuyOnDelivery_verifySuccessResponse : "
			+ timeTaken + " seconds\n");
		
	}
	
	/**
	 * This test case is to calculation of FinalMrp in FetchCart API response
	 * 
	 * @param userName
	 * @param password
	 * @param successRespCode
	 */
	
	@Test(groups = { "Smoke", "Sanity", "FoxSanity", "ProdSanity", "Regression", "MiniRegression",
			"ExhaustiveRegression" }, dataProvider = "fraudUserVerifyShippingDP",dataProviderClass= CartServiceTestsDP.class, description = "1. hit IDP service to login"
					+ "\n 2. hit fetchCart API" + "\n 3.Check the service response must be 200")
	public void fraudUserVerifyShipping(String userName, String password,
			String skuId,String successRespCode) {
		long startTime = Calendar.getInstance().getTimeInMillis();
		String tokens[] = absolutHelper.idp_GetTokens(userName, password);
		AssertJUnit.assertEquals(SUCCESS_STATUS_MSG, tokens[2]);
		System.out.println("xid is:"+tokens[0]);
		
		RequestGenerator addItemToCartReqGen = absolutHelper.addItemToCart(tokens[0],skuId, "1", "ADD");
		AssertJUnit.assertEquals("addItemToCart API is not working ",
			Integer.parseInt(successRespCode), addItemToCartReqGen.response.getStatus());
			
		RequestGenerator fetchCartReqGen = absolutHelper.fetchCart(tokens[0]);
		System.out.println("\nPrinting fetchCart API response :\n\n"+fetchCartReqGen.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("fetchCart API is not working",
			Integer.parseInt("200"), fetchCartReqGen.response.getStatus());
		
		
		int shippingCharge;	
		
		String response = fetchCartReqGen.respvalidate.returnresponseasstring();
		shippingCharge =JsonPath.read(response, "$.data[0].shippingCharge");
		//double totalPriceDecimal = JsonPath.read(response, "$.data[0].totalPriceDecimal");
		
		//String key ="shipping.charges.cartlimit";
		
		AssertJUnit.assertEquals("Fraud User, shipping charges should be applied", Integer.parseInt(fraudUserShippingCharges), shippingCharge);
		
		absolutHelper.performSignOutOperation(userName,tokens[1]);
		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;

		System.out.println("\nTime taken to execute - TestCase - fetchCart_VerifyCalculation : "
			+ timeTaken + " seconds\n");
	}
	
	//Negative Cases-- should be run ONLY on demand
	
	//@BeforeGroups(groups = "NEGATIVE")
	@Test
	public void setUpData(){
		//set shipping and giftwrap charges to -ve values.
		RequestGenerator getConfigData = absolutHelper.getSwitchConfigValues("configuration-widget");
		AssertJUnit.assertEquals("Error fetching config data", "200", 
			getConfigData.respvalidate.NodeValue(AbsolutNodes.META_CODE.toString(), true));
		String activeVariant = getConfigData.respvalidate.NodeValue(AbsolutNodes.META_ACTIVEVARIANT.toString(), true);
		activeVariant = activeVariant.split("\"")[1];
		System.out.println("active variant is:"+activeVariant);
		String key = "\"shipping.charges.amount\": \"4\",\"giftwrap.charges\":\"25\"";
		RequestGenerator updateReq = absolutHelper.updateSwitchConfigValues("configuration-widget", key, activeVariant);
		AssertJUnit.assertEquals("Error fetching config data", "200", 
			updateReq.respvalidate.NodeValue(AbsolutNodes.META_CODE.toString(), true));
		//modify coupon percent to 120 && set value of widget key "Global-Product-Level-Cap" as 120
		
		//update discount of style to more than MRP && reindex style
		
		//refresh widget & feature gate caches
		//sleep for 30 mins
		
		/*try {
            Thread.sleep(1800000);
        } catch (InterruptedException ie) {
            log.error("delay sleep interrupted of setUpData() method, negative test-cases might fail", ie);
        }*/
	}
	
	
	@Test()
	public void updateConfig(){
		String key = "\"shipping.charges.amount\": \"4\",\"giftwrap.charges\":\"25\"";
		RequestGenerator updateReq = absolutHelper.updateSwitchConfigValues("configuration-widget", key, "default");
		AssertJUnit.assertEquals("Error fetching config data", "200", 
			updateReq.respvalidate.NodeValue(AbsolutNodes.META_CODE.toString(), true));
		}
	
	//Constraint 1: Cart item Quantity >= 0 && TotalCartCount >= 0
	
	/** Case 1: addCart API try to add -ve quantity
	 * Negative Cases
	 * This test case is to verify constraints Cart item Quantity >= 0
        TotalCartCount >= 0 is always >=0
	 * 
	 * @param userName
	 * @param password
	 * @param successRespCode
	 */
	@Test(groups = { "NEGATIVE","Smoke", "Sanity", "FoxSanity", "ProdSanity", "Regression", "MiniRegression",
			"ExhaustiveRegression" }, priority=1, dataProvider = "addItemToCartAPI_VerifyFailureCasesDP",dataProviderClass= CartServiceTestsDP.class, description = "1. hit IDP service to login"
					+ "\n 2. hit fetchCart API" + "\n 3.Check the service response must be 200")
	public void addItemToCartAPI_VerifyFailureCases(String userName, String password,String skuId,String itemQtyToAdd,
			String failureRespCode) {
		long startTime = Calendar.getInstance().getTimeInMillis();
		String cartTotalCount = "0";
		String tokens[] = absolutHelper.idp_GetTokens(userName, password);
		AssertJUnit.assertEquals(SUCCESS_STATUS_MSG, tokens[2]);
		System.out.println("xid is:"+tokens[0]);
		RequestGenerator fetchCart = absolutHelper.fetchCart(tokens[0]);
		AssertJUnit.assertEquals("fetchCart API is not working", Integer.parseInt("200"), 
			fetchCart.response.getStatus());
		if(!fetchCart.respvalidate.NodeValue(AbsolutNodes.CART_TOTAL_COUNT.toString(), true).equals(EMPTY_VALUE)){
			cartTotalCount = fetchCart.respvalidate.NodeValue(AbsolutNodes.CART_TOTAL_COUNT.toString(), true);
		}
		RequestGenerator addItemToCartReqGen = absolutHelper.addItemToCart(tokens[0],skuId, String.valueOf(itemQtyToAdd), "ADD");
		
		AssertJUnit.assertEquals("addItemToCart API response should be of failure",
			Integer.parseInt(failureRespCode), addItemToCartReqGen.response.getStatus());
		
		RequestGenerator fetchCartReqGen = absolutHelper.fetchCart(tokens[0]);
		System.out.println("\nPrinting fetchCart API response :\n\n"
			+ fetchCartReqGen.respvalidate.returnresponseasstring());
		
		AssertJUnit.assertEquals("fetchCart API is not working",Integer.parseInt(SUCCESS_STATUS_RESP), 
			fetchCartReqGen.response.getStatus());
		AssertJUnit.assertEquals("Cart TotalCart shouldnt have updated",cartTotalCount,
			fetchCartReqGen.respvalidate.NodeValue(AbsolutNodes.CART_TOTAL_COUNT.toString(),true));
		absolutHelper.performSignOutOperation(userName, tokens[1]);
		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;

		System.out.println("\nTime taken to execute - TestCase - CheckoutServiceTests_fetchCart_verifySuccessResponse : "
			+ timeTaken + " seconds\n");

	}
	
	
	
	/**Case 2:updateCart API try to add -ve quantity
	 * Negative Cases
	 * This test case is to verify constraints Cart item Quantity >= 0
        TotalCartCount >= 0 is always >=0
	 * 
	 * @param userName
	 * @param password
	 * @param successRespCode
	 */
	@Test(groups = { "NEGATIVE","Smoke", "Sanity", "FoxSanity", "ProdSanity", "Regression", "MiniRegression",
			"ExhaustiveRegression" }, priority=1,dataProvider = "updateItemToCartAPI_VerifyFailureCasesDP",dataProviderClass= CartServiceTestsDP.class, description = "1. hit IDP service to login"
					+ "\n 2. hit fetchCart API" + "\n 3.Check the service response must be 200")
	public void updateItemToCartAPI_VerifyFailureCases(String userName,String password, String skuId,String itemQtyToAdd, String itemQtyToUpdate,
			String failureRespCode) {
		long startTime = Calendar.getInstance().getTimeInMillis();
		String qty ="1", itemId;
		String tokens[] = absolutHelper.idp_GetTokens(userName, password);
		AssertJUnit.assertEquals(SUCCESS_STATUS_MSG, tokens[2]);
		System.out.println("xid is:"+tokens[0]);
		RequestGenerator fetchCart = absolutHelper.fetchCart(tokens[0]);
		AssertJUnit.assertEquals("fetchCart API is not working", Integer.parseInt("200"), 
			fetchCart.response.getStatus());
		if(fetchCart.respvalidate.NodeValue(AbsolutNodes.CART_TOTAL_COUNT.toString(), true).equals(EMPTY_VALUE)){

			RequestGenerator addItemToCartReqGen = absolutHelper.addItemToCart(tokens[0],skuId, String.valueOf(itemQtyToAdd), "ADD");
			
			AssertJUnit.assertEquals("addItemToCart API is not working as expected",
				Integer.parseInt("200"), addItemToCartReqGen.response.getStatus());
			AssertJUnit.assertEquals("Item not added to cart", "1", 
					addItemToCartReqGen.respvalidate.NodeValue(AbsolutNodes.CART_TOTAL_COUNT.toString(), true));
			fetchCart = absolutHelper.fetchCart(tokens[0]);
			AssertJUnit.assertEquals("fetchCart API is not working", Integer.parseInt("200"), 
					fetchCart.response.getStatus());
			
		}else{
			skuId = fetchCart.respvalidate.NodeValue(AbsolutNodes.CART_ITEM_ENTRIES_SELSIZE_SKUID.toString(), true);
			qty = absolutHelper.getItemQtyFromSKUId(fetchCart, skuId);
		}
			
		itemId = absolutHelper.getItemIdFromSKUId(fetchCart, skuId);
		
		
		RequestGenerator updateItemInCartReqGen = absolutHelper.updateItemInCart(tokens[0],itemId,skuId, String.valueOf(itemQtyToUpdate), "UPDATE");
		
		AssertJUnit.assertEquals("updateItemToCart API response should be of failure",
			Integer.parseInt(failureRespCode), updateItemInCartReqGen.response.getStatus());
		
		RequestGenerator fetchCartReqGen = absolutHelper.fetchCart(tokens[0]);
		System.out.println("\nPrinting fetchCart API response :\n\n"
			+ fetchCartReqGen.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("fetchCart API is not working",
			Integer.parseInt("200"), fetchCartReqGen.response.getStatus());
		 
		AssertJUnit.assertEquals("qty shouldnt have updated", qty, 
			absolutHelper.getItemQtyFromSKUId(fetchCartReqGen, skuId));
		absolutHelper.performSignOutOperation(userName,tokens[1]);
		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;

		System.out.println("\nTime taken to execute - TestCase - CheckoutServiceTests_fetchCart_verifySuccessResponse : "
			+ timeTaken + " seconds\n");

	}
	
	//Constraint 3: GiftCharge >= 0
	//Negative value of GiftWrap Charges(changed value of widget key "giftwrap.charges" as -12)
	/** Case 1: application of giftwrap on cart should fail if giftwrap.charges value is negative
	 */
	
	@Test(groups = { "NEGATIVE" }, priority=1,dataProvider = "applyGiftWrapWithNVeGiftWrapChargesDP",dataProviderClass= CartServiceTestsDP.class, description = "1. hit IDP service and login the user. "
					+ "\n 2. hit fetchCart API. \n 3. if user's cart is empty then first add items to cart(\n a.hit Search Service API and extract some skuIDs"
					+ "\n b. Check if searched styleIds exists in user's cart or not. \n c. hit addItemToCart API giving skuId which doesnt exists in cart)"
					+ "\n 4. Check if giftwrap is already applied to cart remove it"
					+ "\n 5. hit addGiftWrap API passing valid data"
					+ "\n 6. Verify statusCode must be 200 in APIs response")
	public void applyGiftWrapWithNVeGiftWrapCharges(String userName, String password,
			String senderName, String giftMsg, String recipientName, String failureRespCode) {
		long startTime = Calendar.getInstance().getTimeInMillis();
		String tokens[] = absolutHelper.idp_GetTokens(userName, password);
		AssertJUnit.assertEquals(SUCCESS_STATUS_MSG, tokens[2]);
		System.out.println("xid is:"+tokens[0]);
		RequestGenerator addGiftWrapAndMessageReqGen = absolutHelper.addGiftWrapAndMessage(tokens[0],senderName, giftMsg, recipientName);
		System.out.println("\nPrinting addGiftWrapAndMessage API response :\n\n"
			+ addGiftWrapAndMessageReqGen.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("applyGiftWrap APIs response should be of failure", Integer.parseInt(failureRespCode),
			addGiftWrapAndMessageReqGen.response.getStatus());
		
		absolutHelper.performSignOutOperation(userName, tokens[1]);	
		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;

		System.out.println("\nTime taken to execute - TestCase - applyGiftWrapWithNVeGiftWrapCharges : "
			+ timeTaken + " seconds\n");
		
	}
	
	/** Case 2: fetchCart should fail if giftwrap was already applied to cart and then the 
	 * giftwrap.charges value is changes to -ve value
	 */
	@Test(groups = { "NEGATIVE" }, priority=1,dataProvider = "fetchCartWithNVeGiftWrapChargesDP",dataProviderClass= CartServiceTestsDP.class
			, description = "1. hit IDP service to login"
					+ "\n 2. hit fetchCart API" + "\n 3.Check the service response must be 200")
	public void fetchCartWithNVeGiftWrapCharges(String userName, String password,
			String failureRespCode) 
	{
		long startTime = Calendar.getInstance().getTimeInMillis();
		String tokens[] = absolutHelper.idp_GetTokens(userName, password);
		AssertJUnit.assertEquals(SUCCESS_STATUS_MSG, tokens[2]);
		System.out.println("xid is:"+tokens[0]);
		RequestGenerator fetchCartReqGen = absolutHelper.fetchCart(tokens[0]);
		System.out.println("\nPrinting fetchCart API response :\n\n"
			+ fetchCartReqGen.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("fetchCart API response should be of failure",
			Integer.parseInt(failureRespCode), fetchCartReqGen.response.getStatus());
		absolutHelper.performSignOutOperation(userName, tokens[1]);
		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;

		System.out.println("\nTime taken to execute - TestCase - fetchCartWithNVeGiftWrapCharges : "
			+ timeTaken + " seconds\n");

	}
	//Done
	//Constraint 4: TotalMrp of item >= 0
	/**
	 * Case 1: apply coupon of 100%  on cart, then check in fetchCart MRP >=0
	 * changed value of "mk_widget_key_value_pairs" of key "Global-Product-Level-Cap" as 100
	 * fetchCart should Pass and final MRP should be 0
	 */
	@Test(groups = { "NEGATIVE", "Regression" }, priority=1,dataProvider = "apply100PerCouponVerifyFinalMRPDP",dataProviderClass= CartServiceTestsDP.class, description = "1. hit IDP service and login the user. "
					+ "\n 2. hit fetchCart API. \n 3. if user's cart is empty then first add items to cart(\n a.hit Search Service API and extract some skuIDs"
					+ "\n b. Check if searched styleIds exists in user's cart or not. \n c. hit addItemToCart API giving skuId which doesnt exists in cart)"
					+ "\n 4. hit fetchAllCoupons API and fetch the coupons applicable to user's cart"
					+ "\n 5. hit applyCoupon API by giving correct coupon name fetched"
					+ "\n 6. Verify statusCode, statusMessage & statusType in APIs response must be of Success")
	public void apply100PerCouponVerifyFinalMRP(String userName, String password, String skuId,
			String couponId, String successStatusCode, String successStatusMsg, String successStatusType) {
		long startTime = Calendar.getInstance().getTimeInMillis();
		String tokens[] = absolutHelper.idp_GetTokens(userName, password);
		AssertJUnit.assertEquals(successStatusMsg, tokens[2]);
		
		RequestGenerator fetchCart = absolutHelper.fetchCart(tokens[0]);
		AssertJUnit.assertEquals("fetchCart API failed", Integer.parseInt("200"),
			fetchCart.response.getStatus());
		if(fetchCart.respvalidate.NodeValue(AbsolutNodes.CART_TOTAL_COUNT.toString(),true).equals(EMPTY_VALUE)){
			RequestGenerator addItemToCartReqGen = absolutHelper.addItemToCart(tokens[0],skuId, "1", "ADD");
			AssertJUnit.assertEquals("Item not added to cart", "1", 
			  addItemToCartReqGen.respvalidate.NodeValue(AbsolutNodes.CART_TOTAL_COUNT.toString(), true));

		}
		
		RequestGenerator applyCouponReqGen = absolutHelper.applyCoupon(tokens[0],couponId);
		System.out.println("\nPrinting applyCoupon API response :\n\n"
			+ applyCouponReqGen.respvalidate.returnresponseasstring());

		AssertJUnit.assertEquals("applyCoupon API is not working",
				Integer.parseInt(successStatusCode), applyCouponReqGen.response.getStatus());
		
		RequestGenerator fetchCartReqGen = absolutHelper.fetchCart(tokens[0]);
		AssertJUnit.assertEquals("fetchCart API is not working",
			Integer.parseInt(successStatusCode), fetchCartReqGen.response.getStatus());
		
		AssertJUnit.assertEquals("Final MRP should be >=0", "0",
			fetchCartReqGen.respvalidate.NodeValue(AbsolutNodes.CART_ITEM_ENTRIES_TOT_PRICE.toString(), true));
		absolutHelper.performSignOutOperation(userName, tokens[1]);
		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;

		System.out.println(
				"\nTime taken to execute - TestCase - apply100PerCouponVerifyFinalMRP : "
						+ timeTaken + " seconds\n");
	}
	
	//DDD
	/**
	 * Case 2: apply coupon of 120%  on cart, applyCoupon should fail
	 * changed value of widget key "Global-Product-Level-Cap" as 120
	 */
	@Test(groups = { "NEGATIVE" ,"Regression"}, priority=1,dataProvider = "apply120PerCouponDP",dataProviderClass= CartServiceTestsDP.class, description = "1. hit IDP service and login the user. "
					+ "\n 2. hit fetchCart API. \n 3. if user's cart is empty then first add items to cart(\n a.hit Search Service API and extract some skuIDs"
					+ "\n b. Check if searched styleIds exists in user's cart or not. \n c. hit addItemToCart API giving skuId which doesnt exists in cart)"
					+ "\n 4. hit fetchAllCoupons API and fetch the coupons applicable to user's cart"
					+ "\n 5. hit applyCoupon API by giving correct coupon name fetched"
					+ "\n 6. Verify statusCode, statusMessage & statusType in APIs response must be of Success")
	public void apply120PerCoupon(String userName, String password, String skuId,
			String couponId, String failureRespCode) {
		long startTime = Calendar.getInstance().getTimeInMillis();
		String tokens[] = absolutHelper.idp_GetTokens(userName, password);
		AssertJUnit.assertEquals(SUCCESS_STATUS_MSG, tokens[2]);
		
		RequestGenerator addItemToCartReqGen = absolutHelper.addItemToCart(tokens[0],skuId, "1", "ADD");
			AssertJUnit.assertEquals("Item not added to cart", "1", 
			  addItemToCartReqGen.respvalidate.NodeValue(AbsolutNodes.CART_TOTAL_COUNT.toString(), true));
		
		RequestGenerator applyCouponReqGen = absolutHelper.applyCoupon(tokens[0],couponId);
		System.out.println("\nPrinting applyCoupon API response :\n\n"
			+ applyCouponReqGen.respvalidate.returnresponseasstring());

		AssertJUnit.assertEquals("applyCoupon API response should be of failure",
				Integer.parseInt(failureRespCode), applyCouponReqGen.response.getStatus());
		absolutHelper.performSignOutOperation(userName, tokens[1]);
		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;

		System.out.println("\nTime taken to execute - TestCase - apply120PerCoupon : "
			+ timeTaken + " seconds\n");
	}
	
	/**
	 * Case 3: trying to fetch a user's cart which already has a coupon applied after changing the coupon
	 * percentage to 120% -- Verify fetchCart should fail as it would make cart Final Mrp as -ve
	 * changed value of widget key  "Global-Product-Level-Cap" as 120
	 */
	@Test(groups = { "NEGATIVE","Regression" },priority=1, dataProvider = "fetchCartHaving120PerCouponAppliedDP",dataProviderClass= CartServiceTestsDP.class, description = "1. hit IDP service and login the user. "
					+ "\n 2. hit fetchCart API. \n 3. if user's cart is empty then first add items to cart(\n a.hit Search Service API and extract some skuIDs"
					+ "\n b. Check if searched styleIds exists in user's cart or not. \n c. hit addItemToCart API giving skuId which doesnt exists in cart)"
					+ "\n 4. hit fetchAllCoupons API and fetch the coupons applicable to user's cart"
					+ "\n 5. hit applyCoupon API by giving correct coupon name fetched"
					+ "\n 6. Verify statusCode, statusMessage & statusType in APIs response must be of Success")
	public void fetchCartHaving120PerCouponApplied(String userName, String password, String skuId,String couponId,
			String failureRespCode, String failureStatusMsg) {
		long startTime = Calendar.getInstance().getTimeInMillis();
		String tokens[] = absolutHelper.idp_GetTokens(userName, password);
		AssertJUnit.assertEquals(SUCCESS_STATUS_MSG, tokens[2]);
		// modify coupon to 30%
		MyntraService service1 = Myntra.getService(ServiceType.PORTAL_COUPONSERVICE, APINAME.MODIFYCOUPON,
			init.Configurations, new String[] { couponId,"30"},PayloadType.JSON, PayloadType.JSON);
		System.out.println("json paylod---- >" + service1.Payload);
		RequestGenerator req1 = new RequestGenerator(service1);

		System.out.println(req1.respvalidate.returnresponseasstring());
			AssertJUnit.assertEquals(200, req1.response.getStatus());
		RequestGenerator addItemToCartReqGen = absolutHelper.addItemToCart(tokens[0],skuId, "1", "ADD");
		AssertJUnit.assertEquals("addToCart API is not working",
			Integer.parseInt("200"), addItemToCartReqGen.response.getStatus());
		RequestGenerator applyCouponReqGen = absolutHelper.applyCoupon(tokens[0],couponId);
		System.out.println("\nPrinting applyCoupon API response :\n\n"
			+ applyCouponReqGen.respvalidate.returnresponseasstring());

		AssertJUnit.assertEquals("applyCoupon API is not working",
				Integer.parseInt("200"), applyCouponReqGen.response.getStatus());
		RequestGenerator fetchCart = absolutHelper.fetchCart(tokens[0]);
		AssertJUnit.assertEquals("fetchCart API failed", Integer.parseInt("200"),
				fetchCart.response.getStatus());
		// modify coupon to 120%
		MyntraService service = Myntra.getService(ServiceType.PORTAL_COUPONSERVICE, APINAME.MODIFYCOUPON,
			init.Configurations, new String[] { couponId,"120"},
			PayloadType.JSON, PayloadType.JSON);
		System.out.println("json paylod---- >" + service.Payload);
		RequestGenerator req = new RequestGenerator(service);

		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());
		
		RequestGenerator fetchCartReqGen = absolutHelper.fetchCart(tokens[0]);
		AssertJUnit.assertEquals("fetchCart API response should be of failure",
				Integer.parseInt(failureRespCode), fetchCartReqGen.response.getStatus());
		AssertJUnit.assertTrue("fetchCart API response statusType should be of ERROR",
				fetchCartReqGen.respvalidate.NodeValue(AbsolutNodes.STATUS_TYPE.toString(), true).contains(failureStatusMsg));
		
		
		absolutHelper.performSignOutOperation(userName, tokens[1]);
		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;

		System.out.println("\nTime taken to execute - TestCase - fetchCartHaving120PerCouponApplied : "
			+ timeTaken + " seconds\n");
	}
	
	//Constraint 5: Unit Discount >= 0
		/**
		 * Case 1: find a sku which has discount > MRP
		 * say item MRP is 3599 Discount Amount 4000 so Final MRP is -401
		 * Now try to add this item to Cart
		 * Verify AddToCart should FAIL as finalMRP would be < 0
		 */
	@Test(groups = {"NEGATIVE","Regression"},priority=1, dataProvider = "discountMoreThanMRPAddToCartDP",dataProviderClass= CartServiceTestsDP.class, description = "1. Perform search and extract styleIds 2.Check if searched styleIds exists in user's cart or not. "
					+ "\n 3. login user using IDP service call. 4.hit addItemToCart API giving skuId which doesnt exists in cart"
					+ "\n 5.Check the service response must be 200")
	public void discountMoreThanMRPAddToCart(String userName, String password,
			String skuId, String failureRespCode, String failureStatusType) {
		long startTime = Calendar.getInstance().getTimeInMillis();
		String tokens[] = absolutHelper.idp_GetTokens(userName, password);
		AssertJUnit.assertEquals(SUCCESS_STATUS_MSG, tokens[2]);
		RequestGenerator addItemToCartReqGen = absolutHelper.addItemToCart(tokens[0],skuId, "1", "ADD");
		System.out.println("\nPrinting CheckoutService addItemToCart API response:\n\n"
			+addItemToCartReqGen.respvalidate.returnresponseasstring());
	    
	    AssertJUnit.assertEquals("addItemToCart API response should be of failure",
			Integer.parseInt(failureRespCode), addItemToCartReqGen.response.getStatus());
	    AssertJUnit.assertTrue("addItemToCart API response status type is not a success status type",
			addItemToCartReqGen.respvalidate.NodeValue(AbsolutNodes.STATUS_TYPE.toString(), true).contains(failureStatusType));
			
		absolutHelper.performSignOutOperation(userName, tokens[1]);
        long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;

		System.out.println("\nTime taken to execute - TestCase - discountMoreThanMRPAddToCart : "+ timeTaken + " seconds\n");
		
	}
	
	/**
	 * Case 2: user already has a discounted item in his cart
	 * because of some reason the discount gets updated to more than 100% making sku price a negative value
	 * (Updating sku discount through API for test-case)
	 * Now perform fetchCart operation
	 * Verify fetchCart should fail making it impossible to place an order with negative value of discount
	 * and satisfying the constraint finalMRP >=0 & Unit Discount >= 0
	 */

	@Test(groups = { "NEGATIVE","Regression" },priority=1, dataProvider = "fetchCartWithDiscountMoreThanMRPDP",dataProviderClass= CartServiceTestsDP.class
			, description = "1. hit IDP service to login"
					+ "\n 2. hit fetchCart API" + "\n 3.Check the service response must be 200")
	public void fetchCartWithDiscountMoreThanMRP(String userName, String password,
			String skuId,String failureRespCode) 
	{
		long startTime = Calendar.getInstance().getTimeInMillis();
		String tokens[] = absolutHelper.idp_GetTokens(userName, password);
		AssertJUnit.assertEquals(SUCCESS_STATUS_MSG, tokens[2]);
		System.out.println("xid is:"+tokens[0]);
		RequestGenerator fetchCartReqGen = absolutHelper.fetchCart(tokens[0]);
		System.out.println("\nPrinting fetchCart API response :\n\n"
			+ fetchCartReqGen.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("fetchCart API response should be of failure",
			Integer.parseInt(failureRespCode), fetchCartReqGen.response.getStatus());
//		AssertJUnit.assertTrue("fetchCart API response status message should be a failure status message",
//			fetchCartReqGen.respvalidate.NodeValue(AbsolutNodes.STATUS_MESSAGE.toString(), true).contains(failureStatusMsg));
//		AssertJUnit.assertTrue("fetchCart API response status type should be a failure status type",
//			fetchCartReqGen.respvalidate.NodeValue(AbsolutNodes.STATUS_TYPE.toString(), true).contains(failureStatusType));
//			
		absolutHelper.performSignOutOperation(userName, tokens[1]);
		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;

		System.out.println("\nTime taken to execute - TestCase - fetchCartWithDiscountMoreThanMRP : "
			+ timeTaken + " seconds\n");

	}

	//Constraint 6: TotalAdditionalCharge >= 0
	/**
	* Case 1: add some items to cart such that shipping charges gets applied
	* now change shipping.charges to a negative value
	* perform fetchCart API verify API should fail
	* as it makes totalAdditionalCharge < 0
	*/
	@Test(groups = { "NEGATIVE" }, priority=1,dataProvider = "fetchCartWithNVeShippingChargesDP",dataProviderClass= CartServiceTestsDP.class
			, description = "1. hit IDP service to login"
					+ "\n 2. hit fetchCart API" + "\n 3.Check the service response must be 500")
	public void fetchCartWithNVeShippingCharges(String userName, String password,
			String failureRespCode) 
	{
		long startTime = Calendar.getInstance().getTimeInMillis();
		String tokens[] = absolutHelper.idp_GetTokens(userName, password);
		AssertJUnit.assertEquals(SUCCESS_STATUS_MSG, tokens[2]);
		System.out.println("xid is:"+tokens[0]);
		RequestGenerator fetchCartReqGen = absolutHelper.fetchCart(tokens[0]);
		System.out.println("\nPrinting fetchCart API response :\n\n"
			+ fetchCartReqGen.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("fetchCart API response should be of failure",
			Integer.parseInt(failureRespCode), fetchCartReqGen.response.getStatus());
//		AssertJUnit.assertTrue("fetchCart API response status message should be a failure status message",
//			fetchCartReqGen.respvalidate.NodeValue(AbsolutNodes.STATUS_MESSAGE.toString(), true).contains(failureStatusMsg));
//		AssertJUnit.assertTrue("fetchCart API response status type should be a failure status type",
//			fetchCartReqGen.respvalidate.NodeValue(AbsolutNodes.STATUS_TYPE.toString(), true).contains(failureStatusType));
		absolutHelper.performSignOutOperation(userName, tokens[1]);
		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;

		System.out.println("\nTime taken to execute - TestCase - fetchCartWithNVeShippingCharges : "
			+ timeTaken + " seconds\n");

	}

	
	
	//Integration Tests
	
	/**
	 * testcase to verify single item cart flags with seller config flags
	 * 
	 * @param userName
	 * @param password
	 * @param addItemQty
	 * @param addOperation
	 * @param successStatusCode
	 * @param successStatusMsg
	 * @param successStatusType
	 */
	@Test(groups = {"San", "Sanity", "Regression", "MiniRegression",
			"ExhaustiveRegression" }, dataProvider = "singleItemCart_crossVerifyCartDataWithSellerConfigDP",dataProviderClass= CartServiceTestsDP.class, description = "1. Perform search and extract styleIds 2.Check if searched styleIds exists in user's cart or not. "
					+ "\n 3. login user using IDP service call. 4.hit addItemToCart API giving skuId which doesnt exists in cart"
					+ "\n 5.verify statusCode, statusMessage & statusType of API response must be of success")
	public void singleItemCart_crossVerifyCartDataWithSellerConfig(String userName,String password,String skuId,
			String successStatusCode,String successStatusMsg,String successStatusType) {
		long startTime = Calendar.getInstance().getTimeInMillis();
		String tokens[] = absolutHelper.idp_GetTokens(userName, password);
		AssertJUnit.assertEquals(successStatusMsg, tokens[2]);
		RequestGenerator clearCartReqGen = absolutHelper.clearCart(tokens[0]);
		System.out.println("\nPrinting clearCart API response :\n\n"
				+ clearCartReqGen.respvalidate.returnresponseasstring());
		
		AssertJUnit.assertEquals("clearCart API is not working", Integer.parseInt(SUCCESS_STATUS_RESP),
				clearCartReqGen.response.getStatus());

		RequestGenerator addItemToCartReqGen = absolutHelper.addItemToCart(tokens[0],skuId, "1", "ADD");
		System.out.println("\nPrinting addItemToCart API response :\n\n"
			+ addItemToCartReqGen.respvalidate.returnresponseasstring());
		RequestGenerator fetchCartReqGen = absolutHelper.fetchCart(tokens[0]);
		AssertJUnit.assertEquals("SkuId "+skuId+" is not added in cart",
				 "Exists", absolutHelper.doesSkuIDExists(fetchCartReqGen, skuId));

		String sellerId = fetchCartReqGen.respvalidate.NodeValue(AbsolutNodes.CART_ITEM_ENTRIES_SELLER_ID.toString(), false);
		System.out.println("seller Id is:"+sellerId);
		
		
		RequestGenerator getSellerConfigReqGen = absolutHelper.getSellerConfig(sellerId);
		AssertJUnit.assertEquals("getSellerConfig API is not working",
			Integer.parseInt("200"), getSellerConfigReqGen.response.getStatus());
		AssertJUnit.assertEquals("\"Partner configuration retrieved successfully\"", getSellerConfigReqGen.respvalidate.NodeValue(AbsolutNodes.STATUS_MESSAGE.toString(), false));    
		System.out.println(getSellerConfigReqGen.returnresponseasstring());
		HashMap <String,String> keys = new HashMap<String,String>();
		JSONArray data,configAttributes;
		data = JsonPath.read(getSellerConfigReqGen.respvalidate.returnresponseasstring(), "$.data");
		configAttributes =JsonPath.read(data.get(0), "$.configurationAttributes");
		for(int i=0;i<configAttributes.size();i++){
			String key = JsonPath.read(configAttributes.get(i), "$.configurationKey");
			String value = JsonPath.read(configAttributes.get(i), "$.configurationValue");
			keys.put(key,value);
		}
		System.out.println("printtinnng:::"+keys.entrySet());
		
//		AssertJUnit.assertEquals("key IS_EXCHANGEABLE value not as per Seller Confg",keys.get("IS_EXCHANGEABLE"),fetchCartReqGen.respvalidate.NodeValue(AbsolutNodes.CART_ITEM_ENTRIES_IS_EXCHANGEABLE.toString(),false));
//		AssertJUnit.assertEquals("key IS_RETURNABLE value not as per Seller Confg",keys.get("IS_RETURNABLE"),fetchCartReqGen.respvalidate.NodeValue(AbsolutNodes.CART_ITEM_ENTRIES_IS_RETURNABLE.toString(),false));
//		AssertJUnit.assertEquals("key IS_MYNTS_ENABLED value not as per Seller Confg",keys.get("IS_MYNTS_ENABLED"),fetchCartReqGen.respvalidate.NodeValue(AbsolutNodes.CART_ITEM_LEVEL_MYNTS_ENABLED.toString(),false));
		AssertJUnit.assertEquals("key GIFT_CARD_ALLOWED value not as per Seller Confg",keys.get("GIFT_CARD_ALLOWED"),(fetchCartReqGen.respvalidate.NodeValue(AbsolutNodes.CART_ITEM_GIFTCARD_APPLICABLE.toString(),false)).toLowerCase().replace("\"", ""));
		AssertJUnit.assertEquals("key IS_GIFT_WRAP_ENABLED value not as per Seller Confg",keys.get("IS_GIFT_WRAP_ENABLED"),(fetchCartReqGen.respvalidate.NodeValue(AbsolutNodes.CART_ITEM_GIFTWRAP_APPLICABLE.toString(),false)).toLowerCase().replace("\"", ""));
		AssertJUnit.assertEquals("key LOYALTY_POINTS_ALLOWED value not as per Seller Confg",keys.get("LOYALTY_POINTS_ALLOWED"),(fetchCartReqGen.respvalidate.NodeValue(AbsolutNodes.CART_ITEM_LOYALTY_POINTS_APPLICABLE.toString(),false)).toLowerCase().replace("\"", ""));
		//not in use anymore
		//AssertJUnit.assertEquals("key IS_WALLET_APPLICABLE value not as per Seller Confg",keys.get("IS_WALLET_APPLICABLE"),(fetchCartReqGen.respvalidate.NodeValue(AbsolutNodes.CART_ITEM_WALLET_AVAILABLE.toString(),false)).toLowerCase().replace("\"", ""));
		if(Double.parseDouble(valueShippingDiscount)>Double.parseDouble(fetchCartReqGen.respvalidate.NodeValue(AbsolutNodes.CART_TOTAL_PRICE_DECIMAL.toString(),false)))
		{
			System.out.println("inside IF");
			AssertJUnit.assertEquals("key IS_VALUE_SHIPPING_ENABLED value not correct","false",fetchCartReqGen.respvalidate.NodeValue(AbsolutNodes.CART_VALUE_SHIPPING_APPLICABLE.toString(),false));
			
		}	
			else
			{		System.out.println("inside elses");
				AssertJUnit.assertEquals("key IS_VALUE_SHIPPING_ENABLED value not as per Seller Confg",keys.get("IS_VALUE_SHIPPING_ENABLED"),fetchCartReqGen.respvalidate.NodeValue(AbsolutNodes.CART_VALUE_SHIPPING_APPLICABLE.toString(),false));
					
			}
				
		absolutHelper.performSignOutOperation(userName, tokens[1]);
		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;

		System.out.println("\nTime taken to execute - TestCase - addItemToCart_verifyItemIsAdded : "
			+ timeTaken + " seconds\n");
	}
	

	/**
	 * testcase to cross verify cart flags with seller config flags
	 * 
	 * @param userName
	 * @param password
	 * @param addItemQty
	 * @param addOperation
	 * @param successStatusCode
	 * @param successStatusMsg
	 * @param successStatusType
	 */
	@Test(groups = {"San", "Sanity", "Regression", "MiniRegression",
			"ExhaustiveRegression" }, dataProvider = "multiItemCart_crossVerifyCartDataLevelFlagsWithSellerConfigDP",dataProviderClass= CartServiceTestsDP.class, description = "1. Perform search and extract styleIds 2.Check if searched styleIds exists in user's cart or not. "
					+ "\n 3. login user using IDP service call. 4.hit addItemToCart API giving skuId which doesnt exists in cart"
					+ "\n 5.verify statusCode, statusMessage & statusType of API response must be of success")
	public void multiItemCart_crossVerifyCartDataLevelFlagsWithSellerConfig(String userName,String password,String skuId,
			String successStatusCode,String successStatusMsg,String successStatusType) {
		long startTime = Calendar.getInstance().getTimeInMillis();
		String tokens[] = absolutHelper.idp_GetTokens(userName, password);
		RequestGenerator clearCartReqGen = absolutHelper.clearCart(tokens[0]);
		System.out.println("\nPrinting clearCart API response :\n\n"
				+ clearCartReqGen.respvalidate.returnresponseasstring());
		
		AssertJUnit.assertEquals("clearCart API is not working", Integer.parseInt(SUCCESS_STATUS_RESP),
				clearCartReqGen.response.getStatus());

		AssertJUnit.assertEquals(successStatusMsg, tokens[2]);
		RequestGenerator fetchCartReqGen;
		ArrayList<String> sellerIds = new ArrayList<String>();
		for(String sku : skuId.split(","))
		{
		RequestGenerator addItemToCartReqGen = absolutHelper.addItemToCart(tokens[0],sku, "1", "ADD");
		System.out.println("\nPrinting addItemToCart API response :\n\n"
			+ addItemToCartReqGen.respvalidate.returnresponseasstring());
		fetchCartReqGen = absolutHelper.fetchCart(tokens[0]);
		AssertJUnit.assertEquals("SkuId "+sku+" is not added in cart",
				 "Exists", absolutHelper.doesSkuIDExists(fetchCartReqGen, sku));
		sellerIds.add(absolutHelper.getSellerIdFromSKUId(fetchCartReqGen, sku));
		}
		fetchCartReqGen = absolutHelper.fetchCart(tokens[0]);
		String cartItemCount = fetchCartReqGen.respvalidate.NodeValue(AbsolutNodes.CART_TOTAL_COUNT.toString(), false);

		List<HashMap<String,String>> keysSet = new ArrayList<HashMap<String,String>>();
		int count=0;
		for(String seller: sellerIds){
			System.out.println("seller id "+seller);
			RequestGenerator getSellerConfigReqGen = absolutHelper.getSellerConfig(seller);
			HashMap <String,String> keys = new HashMap<String,String>();
			JSONArray data,configAttributes;
			data = JsonPath.read(getSellerConfigReqGen.respvalidate.returnresponseasstring(), "$.data");
			configAttributes =JsonPath.read(data.get(0), "$.configurationAttributes");
			for(int i=0;i<configAttributes.size();i++){
				String key = JsonPath.read(configAttributes.get(i), "$.configurationKey");
				String value = JsonPath.read(configAttributes.get(i), "$.configurationValue");
				keys.put(key, value);
				keysSet.add(count, keys);
				}
			count++;
			}
		String isGiftWrapApplicable = absolutHelper.getExpectedCartLevelFlags(keysSet, "IS_GIFT_WRAP_ENABLED", Integer.parseInt(cartItemCount), 1);
		String isLPApplicable = absolutHelper.getExpectedCartLevelFlags(keysSet, "LOYALTY_POINTS_ALLOWED", Integer.parseInt(cartItemCount), 1);
		//not in use anymore
		//String isWalletApplicable = absolutHelper.getExpectedCartLevelFlags(keysSet, "IS_WALLET_APPLICABLE", Integer.parseInt(cartItemCount), 1);
		String isGiftCardApplicable = absolutHelper.getExpectedCartLevelFlags(keysSet, "GIFT_CARD_ALLOWED", Integer.parseInt(cartItemCount), 1);
		String isValueShippingApplicable = absolutHelper.getExpectedCartLevelFlags(keysSet, "IS_VALUE_SHIPPING_ENABLED", Integer.parseInt(cartItemCount), 0);
		System.out.println("IS_VALUE_SHIPPING_ENABLED" + isValueShippingApplicable);
		AssertJUnit.assertEquals("key GIFT_CARD_ALLOWED value not as per Seller Confg",isGiftCardApplicable,(fetchCartReqGen.respvalidate.NodeValue(AbsolutNodes.CART_ITEM_GIFTCARD_APPLICABLE.toString(),false)).replace("\"", ""));
		AssertJUnit.assertEquals("key IS_GIFT_WRAP_ENABLED value not as per Seller Confg",isGiftWrapApplicable,(fetchCartReqGen.respvalidate.NodeValue(AbsolutNodes.CART_ITEM_GIFTWRAP_APPLICABLE.toString(),false)).replace("\"", ""));
		AssertJUnit.assertEquals("key LOYALTY_POINTS_ALLOWED value not as per Seller Confg",isLPApplicable,(fetchCartReqGen.respvalidate.NodeValue(AbsolutNodes.CART_ITEM_LOYALTY_POINTS_APPLICABLE.toString(),false)).replace("\"", ""));
		//AssertJUnit.assertEquals("key IS_WALLET_APPLICABLE value not as per Seller Confg",isWalletApplicable,(fetchCartReqGen.respvalidate.NodeValue(AbsolutNodes.CART_ITEM_WALLET_AVAILABLE.toString(),false)).replace("\"", ""));
		
		if(Double.parseDouble(valueShippingDiscount)>Double.parseDouble(fetchCartReqGen.respvalidate.NodeValue(AbsolutNodes.CART_TOTAL_PRICE_DECIMAL.toString(),false)))
			AssertJUnit.assertEquals("key IS_VALUE_SHIPPING_ENABLED value not correct","false",fetchCartReqGen.respvalidate.NodeValue(AbsolutNodes.CART_VALUE_SHIPPING_APPLICABLE.toString(),false));
		else
			AssertJUnit.assertEquals("key IS_VALUE_SHIPPING_ENABLED value not as per Seller Confg",isValueShippingApplicable,fetchCartReqGen.respvalidate.NodeValue(AbsolutNodes.CART_VALUE_SHIPPING_APPLICABLE.toString(),false));
		
		absolutHelper.performSignOutOperation(userName, tokens[1]);
		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;

		System.out.println("\nTime taken to execute - TestCase - addItemToCart_verifyItemIsAdded : "
			+ timeTaken + " seconds\n");
	}
	

	@AfterGroups()
	public void resetData(){
		
	}
	
}
