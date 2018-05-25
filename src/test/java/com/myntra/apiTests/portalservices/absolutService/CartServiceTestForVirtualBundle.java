package com.myntra.apiTests.portalservices.absolutService;

import org.apache.log4j.Logger;
import java.util.Calendar;
import java.util.List;
import com.jayway.jsonpath.JsonPath;
import com.myntra.apiTests.common.APINAME;
import com.myntra.apiTests.common.Myntra;
import com.myntra.apiTests.common.ServiceType;
import com.myntra.lordoftherings.Initialize;
import com.myntra.test.commons.testbase.BaseTest;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.PayloadType;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;
import java.util.HashMap;

public class CartServiceTestForVirtualBundle extends BaseTest implements AbsolutConstants {

	public CartServiceTestForVirtualBundle(){
		new CartServiceTestsDP();
	}
	static Logger log = Logger.getLogger(CartServiceTestSuite.class);
	static AbsolutHelper absolutHelper = new AbsolutHelper();
	static Initialize init = new Initialize("./Data/configuration");

	/**
	 * This Test Case used to invoke CheckoutService addItemToCart API and the following:
	 * verify Success response code(200) for Add Item to Cart API
	 * verify Total Cart Count and Total Display Count in Default and Payment Context for Fetch Cart
	 * Verify Success response code(200) for Remove Item from Cart API and Item is Removed
	 * 
	 * @param userName
	 * @param password
	 * @param virtualBundleSkuId
	 * @param physicalSkuIds
	 * @param totalItemsInVB
	 * @param successRespCode
	 * @param successStatusMsg
	 */
	@Test (groups = {"Sanity", "Regression", "MiniRegression", "ExhaustiveRegression", "VirtualBundle"},
			dataProvider = "addVirtualBundleItemToCart",dataProviderClass= CartServiceTestsDP.class)
	public void addAndRemoveVirtualBundle_verifyItemAddedAndRemoved(String userName,String password,String virtualBundleSkuId, 
			String physicalSkuIds, String totalItemsInVB, String successStatusCode,String successStatusMsg) {

		long startTime = Calendar.getInstance().getTimeInMillis();

		// Sign In
		String tokens[] = absolutHelper.idp_GetTokens(userName, password);
		AssertJUnit.assertEquals(successStatusMsg, tokens[2]);
		System.out.println("xid is:"+tokens[0]);

		//clear Cart
		RequestGenerator clearCartReqGen = absolutHelper.clearCart(tokens[0]);
		AssertJUnit.assertEquals("CheckoutService clearCart API is not working", Integer.parseInt(SUCCESS_STATUS_RESP),
				clearCartReqGen.response.getStatus());
		AssertJUnit.assertEquals("Cart is not Empty", "0",
				clearCartReqGen.respvalidate.NodeValue(AbsolutNodes.CART_TOTAL_COUNT.toString(), true));

		// Add a Virtual Bundle Item to cart
		RequestGenerator addItemToCartReqGen = absolutHelper.addItemToCart(tokens[0], virtualBundleSkuId, "1", "ADD");
		System.out.println("\nPrinting CheckoutService addItemToCart API response :\n\n"
				+ addItemToCartReqGen.respvalidate.returnresponseasstring());

		//Validate Total Cart Count, Total Display count and Item added in Default Context
		RequestGenerator fetchCartDefaultReqGen1 = absolutHelper.fetchCartByContext(tokens[0], "default");
		String totalCartCountInDefaultContext = fetchCartDefaultReqGen1.respvalidate.NodeValue(AbsolutNodes.CART_TOTAL_COUNT.toString(), false);
		String totalDisplayCountInDefaultContext = fetchCartDefaultReqGen1.respvalidate.NodeValue(AbsolutNodes.CART_TOTAL_DISPLAY_COUNT.toString(), false);
		String vbItemId = absolutHelper.getItemIdFromSKUId(fetchCartDefaultReqGen1, virtualBundleSkuId);
		AssertJUnit.assertEquals("Total Cart Count is not correct", "1", totalCartCountInDefaultContext);
		AssertJUnit.assertEquals("Total Display Cart Count is not correct", "1", totalDisplayCountInDefaultContext);
		AssertJUnit.assertEquals("SkuId "+virtualBundleSkuId+" is not added in cart",
				"Exists", absolutHelper.doesSkuIDExists(fetchCartDefaultReqGen1, virtualBundleSkuId));

		//Validate Total Cart Count, Total Display count and Child Items in Payment Context
		RequestGenerator fetchCartPaymentReqGen = absolutHelper.fetchCartByContext(tokens[0], "payment/default");
		String totalCartCountInPaymentContext = fetchCartPaymentReqGen.respvalidate.NodeValue(AbsolutNodes.CART_TOTAL_COUNT.toString(), true);
		String totalDisplayCountInPaymentContext = fetchCartPaymentReqGen.respvalidate.NodeValue(AbsolutNodes.CART_TOTAL_DISPLAY_COUNT.toString(), false);
		AssertJUnit.assertEquals("Total Cart Count is not correct", totalItemsInVB, totalCartCountInPaymentContext);
		AssertJUnit.assertEquals("Total Display Cart Count is not correct", "1", totalDisplayCountInPaymentContext);

		String[] physicalSkuIdList = physicalSkuIds.split("/");
		for (String physicalSkuId  : physicalSkuIdList) {
			AssertJUnit.assertEquals("SkuId "+physicalSkuId+" is not added in cart",
					"Exists", absolutHelper.doesSkuIDExists(fetchCartPaymentReqGen, physicalSkuId));
		}
		String virtualSkuId = fetchCartPaymentReqGen.respvalidate.NodeValue(AbsolutNodes.CART_ITEM_ENTRIES_SELSIZE_VIRTUAL_SKUID.toString(), false);
		AssertJUnit.assertEquals("Virtual Bundle doest not exist", virtualBundleSkuId, virtualSkuId);

		// Remove Virtual Bundle Item from Cart
		RequestGenerator removeItemFromCartReqGen = absolutHelper.removeItemFromCart(tokens[0], vbItemId, "DELETE");
		System.out.println("\nPrinting removeItemFromCart API response :\n\n"
				+ removeItemFromCartReqGen.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("removeItemFromCart API is not working",
				Integer.parseInt(SUCCESS_STATUS_RESP), removeItemFromCartReqGen.response.getStatus());
		AssertJUnit.assertTrue("SkuId "+virtualSkuId+" is still in cart,should have been removed",
				removeItemFromCartReqGen.respvalidate.NodeValue(AbsolutNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));

		// Validate Item is removed from cart	
		RequestGenerator fetchCartDefaultReqGen2 = absolutHelper.fetchCartByContext(tokens[0], "default");
		String totalCartCount = fetchCartDefaultReqGen2.respvalidate.NodeValue(AbsolutNodes.CART_TOTAL_COUNT.toString(), false);
		String totalDisplayCount = fetchCartDefaultReqGen2.respvalidate.NodeValue(AbsolutNodes.CART_TOTAL_DISPLAY_COUNT.toString(), false);
		AssertJUnit.assertEquals("Total Cart Count is not correct", "0", totalCartCount);
		AssertJUnit.assertEquals("Total Display Cart Count is not correct", "0", totalDisplayCount);
		AssertJUnit.assertEquals("SkuId "+virtualBundleSkuId+" is not added in cart",
				"Not Exists", absolutHelper.doesSkuIDExists(fetchCartDefaultReqGen2, virtualBundleSkuId));

		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;
		System.out.println("\nTime taken to execute - TestCase - updateItemInCart_verifyItemQtyIsUpdated : "
				+ timeTaken + " seconds\n");
	}

	/**
	 * This Test Case used to invoke CheckoutService updateItemToCart API and the following:
	 * verify Success response code(200) for Update Item to Cart API
	 * verify Updated Quantity
	 * verify Total Cart Count and Total Display Count in Default and Payment Context for Fetch Cart Before and After Updating cart
	 * Verify Success response code(200) for Remove Item from Cart API and Item is Removed 
	 * 
	 * @param userName
	 * @param password
	 * @param virtualBundleSkuId
	 * @param totalItemsInVB
	 * @param itemQtyToUpdate
	 * @param updatedTotalCartCount
	 * @param successRespCode
	 * @param successStatusMsg
	 */
	@Test (groups = {"Sanity", "Regression", "MiniRegression", "ExhaustiveRegression","VirtualBundle"},
			dataProvider = "updateVirtualBundleItem_verifyQuantityUpdated",dataProviderClass= CartServiceTestsDP.class)
	public void UpdateVBItemToCart_verifyItemQuantityUpdated(String userName,String password,String virtualBundleSkuId, 
			String totalItemsInVB, String itemQtyToUpdate, String updatedTotalCartCount, String successStatusCode,String successStatusMsg) {

		long startTime = Calendar.getInstance().getTimeInMillis();

		// Sign In
		String tokens[] = absolutHelper.idp_GetTokens(userName, password);
		AssertJUnit.assertEquals(successStatusMsg, tokens[2]);
		System.out.println("xid is:"+tokens[0]);

		//clear Cart
		RequestGenerator clearCartReqGen = absolutHelper.clearCart(tokens[0]);
		AssertJUnit.assertEquals("CheckoutService clearCart API is not working", Integer.parseInt(SUCCESS_STATUS_RESP),
				clearCartReqGen.response.getStatus());
		AssertJUnit.assertEquals("Cart is not Empty", "0",
				clearCartReqGen.respvalidate.NodeValue(AbsolutNodes.CART_TOTAL_COUNT.toString(), true));

		// Add a Virtual Bundle Item to cart
		RequestGenerator addItemToCartReqGen = absolutHelper.addItemToCart(tokens[0], virtualBundleSkuId, "1", "ADD");
		System.out.println("\nPrinting CheckoutService addItemToCart API response :\n\n"
				+ addItemToCartReqGen.respvalidate.returnresponseasstring());

		//Validate Total Cart Count, Total Display count and Item added in Default Context
		RequestGenerator fetchCartDefaultReqGen1 = absolutHelper.fetchCartByContext(tokens[0], "default");
		String totalCartCountInDefaultContext = fetchCartDefaultReqGen1.respvalidate.NodeValue(AbsolutNodes.CART_TOTAL_COUNT.toString(), false);
		String totalDisplayCountInDefaultContext = fetchCartDefaultReqGen1.respvalidate.NodeValue(AbsolutNodes.CART_TOTAL_DISPLAY_COUNT.toString(), false);
		String vbItemId = absolutHelper.getItemIdFromSKUId(fetchCartDefaultReqGen1, virtualBundleSkuId);
		AssertJUnit.assertEquals("Total Cart Count is not correct", "1", totalCartCountInDefaultContext);
		AssertJUnit.assertEquals("Total Display Cart Count is not correct", "1", totalDisplayCountInDefaultContext);
		AssertJUnit.assertEquals("SkuId "+virtualBundleSkuId+" is not added in cart",
				"Exists", absolutHelper.doesSkuIDExists(fetchCartDefaultReqGen1, virtualBundleSkuId));

		//Validate Total Cart Count, Total Display count and Child Items in Payment Context
		RequestGenerator fetchCartPaymentReqGen = absolutHelper.fetchCartByContext(tokens[0], "payment/default");
		String totalCartCountInPaymentContext = fetchCartPaymentReqGen.respvalidate.NodeValue(AbsolutNodes.CART_TOTAL_COUNT.toString(), true);
		String totalDisplayCountInPaymentContext = fetchCartPaymentReqGen.respvalidate.NodeValue(AbsolutNodes.CART_TOTAL_DISPLAY_COUNT.toString(), false);
		AssertJUnit.assertEquals("Total Cart Count is not correct", totalItemsInVB, totalCartCountInPaymentContext);
		AssertJUnit.assertEquals("Total Display Cart Count is not correct", "1", totalDisplayCountInPaymentContext);

		// Update Item Quantity
		RequestGenerator updateItemInCartReqGen = absolutHelper.updateItemInCart(tokens[0],vbItemId,
				virtualBundleSkuId, String.valueOf(itemQtyToUpdate), "UPDATE");
		System.out.println("\nPrinting updateItemInCart API response :\n\n"
				+ updateItemInCartReqGen.respvalidate.returnresponseasstring());

		AssertJUnit.assertEquals("CheckoutService updateItemInCart API is not working",
				Integer.parseInt(successStatusCode), updateItemInCartReqGen.response.getStatus());

		// Validate Item Quantity updated on Default Context
		fetchCartDefaultReqGen1 = absolutHelper.fetchCartByContext(tokens[0], "default");
		String updatedTotalCartCountInDefaultContext = fetchCartDefaultReqGen1.respvalidate.NodeValue(AbsolutNodes.CART_TOTAL_COUNT.toString(), false);
		String updatedTotalDisplayCountInDefaultContext = fetchCartDefaultReqGen1.respvalidate.NodeValue(AbsolutNodes.CART_TOTAL_DISPLAY_COUNT.toString(), false);
		AssertJUnit.assertEquals("Total Cart Count is not correct", itemQtyToUpdate, updatedTotalCartCountInDefaultContext);
		AssertJUnit.assertEquals("Total Display Cart Count is not correct", itemQtyToUpdate, updatedTotalDisplayCountInDefaultContext);
		AssertJUnit.assertEquals("SkuId "+virtualBundleSkuId+" is not present in cart",
				"Exists", absolutHelper.doesSkuIDExists(fetchCartDefaultReqGen1, virtualBundleSkuId));

		//Validate Child Item Quantities updated in Payment Context
		fetchCartPaymentReqGen = absolutHelper.fetchCartByContext(tokens[0], "payment/default");
		String updatedtotalCartCountInPaymentContext = fetchCartPaymentReqGen.respvalidate.NodeValue(AbsolutNodes.CART_TOTAL_COUNT.toString(), true);
		String updatedtotalDisplayCountInPaymentContext = fetchCartPaymentReqGen.respvalidate.NodeValue(AbsolutNodes.CART_TOTAL_DISPLAY_COUNT.toString(), false);
		AssertJUnit.assertEquals("Total Cart Count is not correct", updatedTotalCartCount, updatedtotalCartCountInPaymentContext);
		AssertJUnit.assertEquals("Total Display Cart Count is not correct", itemQtyToUpdate, updatedtotalDisplayCountInPaymentContext);

		// Remove Virtual Bundle Item from Cart
		RequestGenerator removeItemFromCartReqGen = absolutHelper.removeItemFromCart(tokens[0], vbItemId, "DELETE");
		System.out.println("\nPrinting removeItemFromCart API response :\n\n"
				+ removeItemFromCartReqGen.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("removeItemFromCart API is not working",
				Integer.parseInt(SUCCESS_STATUS_RESP), removeItemFromCartReqGen.response.getStatus());
		AssertJUnit.assertTrue("SkuId "+virtualBundleSkuId+" is still in cart,should have been removed",
				removeItemFromCartReqGen.respvalidate.NodeValue(AbsolutNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));

		// Validate Item is removed from cart	
		RequestGenerator fetchCartDefaultReqGen2 = absolutHelper.fetchCartByContext(tokens[0], "default");
		String totalCartCount = fetchCartDefaultReqGen2.respvalidate.NodeValue(AbsolutNodes.CART_TOTAL_COUNT.toString(), false);
		String totalDisplayCount = fetchCartDefaultReqGen2.respvalidate.NodeValue(AbsolutNodes.CART_TOTAL_DISPLAY_COUNT.toString(), false);
		AssertJUnit.assertEquals("Total Cart Count is not correct", "0", totalCartCount);
		AssertJUnit.assertEquals("Total Display Cart Count is not correct", "0", totalDisplayCount);
		AssertJUnit.assertEquals("SkuId "+virtualBundleSkuId+" is not added in cart",
				"Not Exists", absolutHelper.doesSkuIDExists(fetchCartDefaultReqGen2, virtualBundleSkuId));		

		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;
		System.out.println("\nTime taken to execute - TestCase - UpdateVBItemToCart_verifyItemIsAdded : "
				+ timeTaken + " seconds\n");

	}

	/**
	 * This Test Case used to invoke CheckoutService updateItemToCart API and the following:
	 * verify Success response code(200) for Update Item to Cart API
	 * verify Updated SKU
	 * verify Total Cart Count and Total Display Count in Default and Payment Context for Fetch Cart Before and After Updating cart
	 * Verify Success response code(200) for Remove Item from Cart API and Item is Removed 
	 * 
	 * @param userName
	 * @param password
	 * @param virtualBundleSkuId
	 * @param physicalSkuIds
	 * @param totalItemsInVB
	 * @param updatedSKU
	 * @param updatedPhysicalSkuIds
	 * @param successRespCode
	 * @param successStatusMsg
	 */
	@Test (groups = {"Sanity", "Regression", "MiniRegression", "ExhaustiveRegression","VirtualBundle"},
			dataProvider = "updateVirtualBundleItem_verifySkuUpdated",dataProviderClass= CartServiceTestsDP.class)
	public void UpdateVBItemToCart_verifyItemSkuUpdated(String userName,String password,String virtualBundleSkuId, String physicalSkuIds, String totalItemsInVB, String updatedSKU, String updatedPhysicalSkuIds, String successStatusCode,String successStatusMsg) {

		long startTime = Calendar.getInstance().getTimeInMillis();

		// Sign In
		String tokens[] = absolutHelper.idp_GetTokens(userName, password);
		AssertJUnit.assertEquals(successStatusMsg, tokens[2]);
		System.out.println("xid is:"+tokens[0]);

		//clear Cart
		RequestGenerator clearCartReqGen = absolutHelper.clearCart(tokens[0]);
		AssertJUnit.assertEquals("CheckoutService clearCart API is not working", Integer.parseInt(SUCCESS_STATUS_RESP),
				clearCartReqGen.response.getStatus());
		AssertJUnit.assertEquals("Cart is not Empty", "0",
				clearCartReqGen.respvalidate.NodeValue(AbsolutNodes.CART_TOTAL_COUNT.toString(), true));

		// Add a Virtual Bundle Item to cart
		RequestGenerator addItemToCartReqGen = absolutHelper.addItemToCart(tokens[0], virtualBundleSkuId, "1", "ADD");
		System.out.println("\nPrinting CheckoutService addItemToCart API response :\n\n"
				+ addItemToCartReqGen.respvalidate.returnresponseasstring());

		//Validate Total Cart Count, Total Display count and Item added in Default Context
		RequestGenerator fetchCartDefaultReqGen1 = absolutHelper.fetchCartByContext(tokens[0], "default");
		String totalCartCountInDefaultContext = fetchCartDefaultReqGen1.respvalidate.NodeValue(AbsolutNodes.CART_TOTAL_COUNT.toString(), false);
		String totalDisplayCountInDefaultContext = fetchCartDefaultReqGen1.respvalidate.NodeValue(AbsolutNodes.CART_TOTAL_DISPLAY_COUNT.toString(), false);
		String vbItemId = absolutHelper.getItemIdFromSKUId(fetchCartDefaultReqGen1, virtualBundleSkuId);
		AssertJUnit.assertEquals("Total Cart Count is not correct", "1", totalCartCountInDefaultContext);
		AssertJUnit.assertEquals("Total Display Cart Count is not correct", "1", totalDisplayCountInDefaultContext);
		AssertJUnit.assertEquals("SkuId "+virtualBundleSkuId+" is not added in cart",
				"Exists", absolutHelper.doesSkuIDExists(fetchCartDefaultReqGen1, virtualBundleSkuId));

		//Validate Total Cart Count, Total Display count and Child Items in Payment Context
		RequestGenerator fetchCartPaymentReqGen1 = absolutHelper.fetchCartByContext(tokens[0], "payment/default");
		String totalCartCountInPaymentContext = fetchCartPaymentReqGen1.respvalidate.NodeValue(AbsolutNodes.CART_TOTAL_COUNT.toString(), true);
		String totalDisplayCountInPaymentContext = fetchCartPaymentReqGen1.respvalidate.NodeValue(AbsolutNodes.CART_TOTAL_DISPLAY_COUNT.toString(), false);
		AssertJUnit.assertEquals("Total Cart Count is not correct", totalItemsInVB, totalCartCountInPaymentContext);
		AssertJUnit.assertEquals("Total Display Cart Count is not correct", "1", totalDisplayCountInPaymentContext);

		String[] physicalSkuIdList = physicalSkuIds.split("/");
		for (String physicalSkuId  : physicalSkuIdList) {
			AssertJUnit.assertEquals("SkuId "+physicalSkuId+" is not added in cart",
					"Exists", absolutHelper.doesSkuIDExists(fetchCartPaymentReqGen1, physicalSkuId));
		}
		String virtualSkuId = fetchCartPaymentReqGen1.respvalidate.NodeValue(AbsolutNodes.CART_ITEM_ENTRIES_SELSIZE_VIRTUAL_SKUID.toString(), false);
		AssertJUnit.assertEquals("Virtual Bundle doest not exist", virtualBundleSkuId, virtualSkuId);

		// Update Item SKU
		RequestGenerator updateItemInCartReqGen = absolutHelper.updateItemInCart(tokens[0],vbItemId,
				updatedSKU, "1", "UPDATE");
		System.out.println("\nPrinting updateItemInCart API response :\n\n"
				+ updateItemInCartReqGen.respvalidate.returnresponseasstring());

		AssertJUnit.assertEquals("CheckoutService updateItemInCart API is not working",
				Integer.parseInt(successStatusCode), updateItemInCartReqGen.response.getStatus());

		// Validate Item SKU updated on Default Context
		RequestGenerator fetchCartDefaultReqGen2 = absolutHelper.fetchCartByContext(tokens[0], "default");
		String updatedTotalCartCountInDefaultContext = fetchCartDefaultReqGen2.respvalidate.NodeValue(AbsolutNodes.CART_TOTAL_COUNT.toString(), false);
		String updatedTotalDisplayCountInDefaultContext = fetchCartDefaultReqGen2.respvalidate.NodeValue(AbsolutNodes.CART_TOTAL_DISPLAY_COUNT.toString(), false);
		AssertJUnit.assertEquals("Total Cart Count is not correct", "1", updatedTotalCartCountInDefaultContext);
		AssertJUnit.assertEquals("Total Display Cart Count is not correct", "1", updatedTotalDisplayCountInDefaultContext);
		AssertJUnit.assertEquals("SkuId "+updatedSKU+" is not present in cart",
				"Exists", absolutHelper.doesSkuIDExists(fetchCartDefaultReqGen2, updatedSKU));
		AssertJUnit.assertEquals("SkuId "+updatedSKU+" is still present in cart",
				"Not Exists", absolutHelper.doesSkuIDExists(fetchCartDefaultReqGen2, virtualBundleSkuId));

		//Validate Child Item SKUs updated in Payment Context
		RequestGenerator fetchCartPaymentReqGen2 = absolutHelper.fetchCartByContext(tokens[0], "payment/default");
		String updatedtotalCartCountInPaymentContext = fetchCartPaymentReqGen2.respvalidate.NodeValue(AbsolutNodes.CART_TOTAL_COUNT.toString(), true);
		String updatedtotalDisplayCountInPaymentContext = fetchCartPaymentReqGen2.respvalidate.NodeValue(AbsolutNodes.CART_TOTAL_DISPLAY_COUNT.toString(), false);
		AssertJUnit.assertEquals("Total Cart Count is not correct", totalItemsInVB, updatedtotalCartCountInPaymentContext);
		AssertJUnit.assertEquals("Total Display Cart Count is not correct", "1", updatedtotalDisplayCountInPaymentContext);

		String[] updatedPhysicalSkuIdList = updatedPhysicalSkuIds.split("/");
		for (int i = 0; i < updatedPhysicalSkuIdList.length; i++) {
			AssertJUnit.assertEquals("SkuId "+updatedPhysicalSkuIdList[i]+" is not added in cart",
					"Exists", absolutHelper.doesSkuIDExists(fetchCartPaymentReqGen2, updatedPhysicalSkuIdList[i]));
			AssertJUnit.assertEquals("SkuId "+physicalSkuIdList[i]+" is Still present in cart",
					"Not Exists", absolutHelper.doesSkuIDExists(fetchCartPaymentReqGen2, physicalSkuIdList[i]));

		}
		String updatedVirtualSkuId = fetchCartPaymentReqGen2.respvalidate.NodeValue(AbsolutNodes.CART_ITEM_ENTRIES_SELSIZE_VIRTUAL_SKUID.toString(), false);
		AssertJUnit.assertEquals("Virtual Bundle not updated", updatedSKU, updatedVirtualSkuId);


		// Remove Virtual Bundle Item from Cart
		RequestGenerator removeItemFromCartReqGen = absolutHelper.removeItemFromCart(tokens[0], vbItemId, "DELETE");
		System.out.println("\nPrinting removeItemFromCart API response :\n\n"
				+ removeItemFromCartReqGen.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("removeItemFromCart API is not working",
				Integer.parseInt(SUCCESS_STATUS_RESP), removeItemFromCartReqGen.response.getStatus());
		AssertJUnit.assertTrue("SkuId "+virtualBundleSkuId+" is still in cart,should have been removed",
				removeItemFromCartReqGen.respvalidate.NodeValue(AbsolutNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));

		// Validate Item is removed from cart	
		RequestGenerator fetchCartDefaultReqGen3 = absolutHelper.fetchCartByContext(tokens[0], "default");
		String totalCartCount = fetchCartDefaultReqGen3.respvalidate.NodeValue(AbsolutNodes.CART_TOTAL_COUNT.toString(), false);
		String totalDisplayCount = fetchCartDefaultReqGen3.respvalidate.NodeValue(AbsolutNodes.CART_TOTAL_DISPLAY_COUNT.toString(), false);
		AssertJUnit.assertEquals("Total Cart Count is not correct", "0", totalCartCount);
		AssertJUnit.assertEquals("Total Display Cart Count is not correct", "0", totalDisplayCount);
		AssertJUnit.assertEquals("SkuId "+virtualBundleSkuId+" is not added in cart",
				"Not Exists", absolutHelper.doesSkuIDExists(fetchCartDefaultReqGen3, updatedSKU));		

		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;
		System.out.println("\nTime taken to execute - TestCase - UpdateVBItemToCart_verifyItemIsAdded : "
				+ timeTaken + " seconds\n");

	}

	@Test(dataProvider = "applyCoupon_VerifyProratedCouponDiscountForVb",dataProviderClass= CartServiceTestsDP.class)
	public void applyCouponOnVB_VerifyFetchCartCalculation(String userName, String password,String virtualBundleSkuId, String physicalSkuIds, 
			String couponId, String successRespCode) {
		long startTime = Calendar.getInstance().getTimeInMillis();

		String tokens[] = absolutHelper.idp_GetTokens(userName, password);
		AssertJUnit.assertEquals(SUCCESS_STATUS_MSG, tokens[2]);

		RequestGenerator clearCartReqGen = absolutHelper.clearCart(tokens[0]);
		AssertJUnit.assertEquals("CheckoutService clearCart API is not working", Integer.parseInt(SUCCESS_STATUS_RESP),
				clearCartReqGen.response.getStatus());

		RequestGenerator addItemToCartReqGen = absolutHelper.addItemToCart(tokens[0], virtualBundleSkuId, "1", "ADD");
		AssertJUnit.assertEquals("addItemToCart API is not working",
				Integer.parseInt(successRespCode), addItemToCartReqGen.response.getStatus());

		//Validate Item added in Cart: Default Context
		RequestGenerator fetchCartDefaultReqGen = absolutHelper.fetchCartByContext(tokens[0], "default");
		AssertJUnit.assertEquals("SkuId "+virtualBundleSkuId+" is not added in cart",
				"Exists", absolutHelper.doesSkuIDExists(fetchCartDefaultReqGen, virtualBundleSkuId));

		// Apply Coupon and validate coupon applied
		RequestGenerator applyCouponReqGen = absolutHelper.applyCoupon(tokens[0],couponId);
		AssertJUnit.assertEquals("applyCoupon API is not working", Integer.parseInt(successRespCode),
				applyCouponReqGen.response.getStatus());
		System.out.println("\nPrinting applyCoupon API response :\n\n"
				+ applyCouponReqGen.respvalidate.returnresponseasstring());	
		fetchCartDefaultReqGen = absolutHelper.fetchCartByContext(tokens[0], "default");
		String response1 = fetchCartDefaultReqGen.respvalidate.returnresponseasstring();
		String couponAppliedState = (fetchCartDefaultReqGen.respvalidate.NodeValue(AbsolutNodes.CART_APPLIED_COUPONS_APPSTATE_STATE.toString(), false)).replace("\"", "");
		String couponAppliedMessage =(fetchCartDefaultReqGen.respvalidate.NodeValue(AbsolutNodes.CART_APPLIED_COUPONS_APPSTATE_MSG.toString(), false)).replace("\"", "");

		AssertJUnit.assertEquals("Coupon Applied state was not Successful", SUCCESS_STATUS_TYPE, couponAppliedState);
		AssertJUnit.assertEquals("Coupon was not applied Successfully", "Applied Successfully", couponAppliedMessage);

		double totalCouponDiscountDecimal = JsonPath.read(response1, "$.data[0].totalCouponDiscountDecimal");	
		double totalMrpDecimal =  JsonPath.read(response1, "$.data[0].totalMrpDecimal");

		// Fetching Child items from payments context
		RequestGenerator fetchCartPaymentReqGen = absolutHelper.fetchCartByContext(tokens[0], "payment/default");
		String response2 = fetchCartPaymentReqGen.respvalidate.returnresponseasstring();		
		String[] physicalSkuIdList = physicalSkuIds.split("/");
		double expectedTotalCouponDiscount = 0;
		for(int i = 0; i<physicalSkuIdList.length ; i++) {
			String childQuantityNodePath =  "$.data[0].cartItemEntries["+ i +"].quantity";
			String unitCouponDiscountNodePath = "$.data[0].cartItemEntries["+ i +"].unitCouponDiscount";
			String couponDiscountNodePath = "$.data[0].cartItemEntries["+ i +"].totalCouponDiscountDecimal";
			String childTotalMrpNodePath =  "$.data[0].cartItemEntries["+ i +"].totalMrpDecimal";

			int childQuantity = JsonPath.read(response2, childQuantityNodePath);
			double childUnitCouponDiscountDecimal = JsonPath.read(response2, unitCouponDiscountNodePath);
			double childCouponDiscountDecimal = JsonPath.read(response2, couponDiscountNodePath);
			double childTotalMrpDecimal = JsonPath.read(response2, childTotalMrpNodePath);

			double expectedChildCouponDiscount = ((childTotalMrpDecimal/totalMrpDecimal)*totalCouponDiscountDecimal);
			double expectedChildUnitCouponDiscount = expectedChildCouponDiscount/childQuantity;
			expectedTotalCouponDiscount = expectedTotalCouponDiscount + childCouponDiscountDecimal;
			AssertJUnit.assertEquals("Prorated Coupon Discount for Child is not Correct", expectedChildCouponDiscount , childCouponDiscountDecimal);						
			AssertJUnit.assertEquals("Prorated Unit Coupon Discount for Child is not Correct", expectedChildUnitCouponDiscount , childUnitCouponDiscountDecimal);						
		}

		AssertJUnit.assertEquals("Total Coupon Discount should be sum of prorated Coupon Discounts",
				expectedTotalCouponDiscount , totalCouponDiscountDecimal);	

		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;
		System.out.println("\nTime taken to execute - TestCase - UpdateVBItemToCart_verifyItemIsAdded : "
				+ timeTaken + " seconds\n");
	}	


	@Test(dataProvider = "discounts_VerifyProratedDiscountForVb",dataProviderClass= CartServiceTestsDP.class)
	public void dicountsOnVB_VerifyFetchCartCalculation(String userName, String password,String virtualBundleSkuId, String physicalSkuIds, 
			String successRespCode) {
		long startTime = Calendar.getInstance().getTimeInMillis();

		String tokens[] = absolutHelper.idp_GetTokens(userName, password);
		AssertJUnit.assertEquals(SUCCESS_STATUS_MSG, tokens[2]);

		RequestGenerator clearCartReqGen = absolutHelper.clearCart(tokens[0]);
		AssertJUnit.assertEquals("CheckoutService clearCart API is not working", Integer.parseInt(SUCCESS_STATUS_RESP),
				clearCartReqGen.response.getStatus());

		RequestGenerator addItemToCartReqGen = absolutHelper.addItemToCart(tokens[0], virtualBundleSkuId, "1", "ADD");
		AssertJUnit.assertEquals("addItemToCart API is not working",
				Integer.parseInt(successRespCode), addItemToCartReqGen.response.getStatus());

		//Validate Item added in cart: Default Context
		RequestGenerator fetchCartDefaultReqGen = absolutHelper.fetchCartByContext(tokens[0], "default");
		String totalCartCountInDefaultContext = fetchCartDefaultReqGen.respvalidate.NodeValue(AbsolutNodes.CART_TOTAL_COUNT.toString(), false);
		String totalDisplayCountInDefaultContext = fetchCartDefaultReqGen.respvalidate.NodeValue(AbsolutNodes.CART_TOTAL_DISPLAY_COUNT.toString(), false);
		AssertJUnit.assertEquals("Total Cart Count is not correct", "1", totalCartCountInDefaultContext);
		AssertJUnit.assertEquals("Total Display Cart Count is not correct", "1", totalDisplayCountInDefaultContext);
		AssertJUnit.assertEquals("SkuId "+virtualBundleSkuId+" is not added in cart",
				"Exists", absolutHelper.doesSkuIDExists(fetchCartDefaultReqGen, virtualBundleSkuId));


		String response1 = fetchCartDefaultReqGen.respvalidate.returnresponseasstring();
		double totalDiscountDecimal = JsonPath.read(response1, "$.data[0].totalDiscountDecimal");	
		double totalPriceDecimal =  JsonPath.read(response1, "$.data[0].totalMrpDecimal");

		// Fetching Child items in payments context
		RequestGenerator fetchCartPaymentReqGen = absolutHelper.fetchCartByContext(tokens[0], "payment/default");
		String response2 = fetchCartPaymentReqGen.respvalidate.returnresponseasstring();		

		String[] physicalSkuIdList = physicalSkuIds.split("/");
		double expectedTotalDiscount = 0;
		for(int i = 0; i<physicalSkuIdList.length ; i++) {
			String childQuantityNodePath =  "$.data[0].cartItemEntries[" +i +"].quantity";
			String unitDiscountNodePath = "$.data[0].cartItemEntries[" +i +"].unitDiscount";
			String discountNodePath = "$.data[0].cartItemEntries[" +i +"].totalDiscountDecimal";
			String childTotalPriceNodePath =  "$.data[0].cartItemEntries[" +i +"].totalMrpDecimal";

			int childQuantity = JsonPath.read(response2, childQuantityNodePath);
			double childUnitDiscountDecimal = JsonPath.read(response2, unitDiscountNodePath);
			double childDiscountDecimal = JsonPath.read(response2, discountNodePath);
			double childTotalPriceDecimal = JsonPath.read(response2, childTotalPriceNodePath);

			double expectedChildDiscount = ((childTotalPriceDecimal/totalPriceDecimal)*totalDiscountDecimal);
			double expectedChildUnitDiscount = expectedChildDiscount/childQuantity;
			expectedTotalDiscount = expectedTotalDiscount + expectedChildDiscount;

			AssertJUnit.assertEquals("Prorated Discount for Child is not Correct", expectedChildDiscount , childDiscountDecimal);						
			AssertJUnit.assertEquals("Prorated Unit Discount for Child is not Correct", expectedChildUnitDiscount , childUnitDiscountDecimal);						
		}

		AssertJUnit.assertEquals("Total Discount should be sum of prorated Coupon Discounts",
				expectedTotalDiscount , totalDiscountDecimal);	

		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;
		System.out.println("\nTime taken to execute - TestCase - UpdateVBItemToCart_verifyItemIsAdded : "
				+ timeTaken + " seconds\n");

	}		

	@Test
	public void countTestCases()
	{
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("Authorization", "Basic c2Z0ZXN0ZW5naW5lZXJpbmdAbXludHJhLmNvbTpteW50cmFAMTIz");
		headers.put("Content-Type", "application/json");

		// .................. update test cases reference column...............
		/*		MyntraService service_fetchTestCases = Myntra.getService(ServiceType.PORTAL_TESTRAIL,
				APINAME.FETCHTESTCASESWITHSECTION, init.Configurations, PayloadType.JSON,PayloadType.JSON);

		System.out.println("\nPrinting CheckoutService fetch test cases API URL : "
				+ service_fetchTestCases.URL);

		RequestGenerator req_fetchTestCases = new RequestGenerator (service_fetchTestCases,headers);

		String response = req_fetchTestCases.respvalidate.returnresponseasstring();

		List<Integer> testCaseID = JsonPath.read(response, "$..id");
		Double TotalTestCases=(double) testCaseID.size();
		System.out.println("Total test cases = "+testCaseID.size());

		for (Integer i :testCaseID )
		{
			System.out.println("i=="+i);

			MyntraService service_getTestCase = Myntra.getService(ServiceType.PORTAL_TESTRAIL,
					APINAME.FETCHTESTCASE, init.Configurations,PayloadType.JSON,new String [] {i.toString()},PayloadType.JSON);
			RequestGenerator req_fetchTestCase = new RequestGenerator (service_getTestCase,headers);

			String statusMessage = req_fetchTestCase.respvalidate
					.NodeValue("refs", true).replaceAll("\"", "")
					.trim();
			System.out.println("node value="+statusMessage);

			if (statusMessage.equalsIgnoreCase("null"))
			{
				MyntraService service_updateTestCases = Myntra.getService(ServiceType.PORTAL_TESTRAIL,
						APINAME.UPDATETESTCASE, init.Configurations, new String [] {"NA"},new String [] {i.toString()},PayloadType.JSON,PayloadType.JSON);

				RequestGenerator req_updateTestCase = new RequestGenerator (service_updateTestCases,headers);
				System.out.println("response="+req_updateTestCase.returnresponseasstring());
			}

		}
		 */		

		// ..................... calculate automation coverage.............................		
		MyntraService service_fetchTestCases = Myntra.getService(ServiceType.PORTAL_TESTRAIL,
				APINAME.FETCHALLTESTCASES, init.Configurations, PayloadType.JSON,PayloadType.JSON);

		System.out
		.println("\nPrinting CheckoutService fetch test cases API URL : "
				+ service_fetchTestCases.URL);

		RequestGenerator req_fetchTestCases = new RequestGenerator (service_fetchTestCases,headers);

		String response = req_fetchTestCases.respvalidate.returnresponseasstring();

		List<Integer> totalTestCases = JsonPath.read(response, "$..id");
		Double TotalTestCases=(double) totalTestCases.size();
		System.out.println("Total test cases (Manual + Automated))  = "+totalTestCases.size());

		Integer automatedCount=0;
		Integer notAutomatedCount=0;
		Integer nonAutomatableCount=0;
		Integer statusNotGivenCount = 0;
		Double totalPossibleAutomation=0.0;
		List<String> automationColumn = JsonPath.read(response, "$..refs");

		for (String s :automationColumn)
		{
			if(s!= null) {
				if (s.equals("Automated"))
				{
					automatedCount++;
				}
				else if (s.equals("Not Automated"))
				{
					notAutomatedCount++;
				}
				else if (s.equals("NA"))
				{
					nonAutomatableCount++;
				}
				else
				{
					System.out.println("invalid column value:"+s);
				}
			} else {
				statusNotGivenCount++;
			}


		}

		System.out.println("Total test cases automated : " +automatedCount);
		System.out.println("Total test cases not automated but are automatable : " +notAutomatedCount);
		System.out.println("Total test cases for which status is not given : " +statusNotGivenCount);
		System.out.println("Total test cases which are not possible to automate: " +nonAutomatableCount);

		Float percentageCoverage=(float) ((automatedCount*100/TotalTestCases));
		System.out.println("Automation percentage coverage based on total test cases (manual+automated)) : "+percentageCoverage);

		totalPossibleAutomation = (double) (automatedCount + notAutomatedCount);
		System.out.println("Total test cases which are possible to automate:" +totalPossibleAutomation);
		Float percentageCoverage1=(float) ((automatedCount*100/totalPossibleAutomation));
		System.out.println("Automation percentage coverage based on total test cases which are possible to automate : "+percentageCoverage1);


	}
}
