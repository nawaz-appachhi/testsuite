
package com.myntra.apiTests.portalservices.all;

import java.util.Calendar;
import java.util.List;

import com.myntra.apiTests.dataproviders.WishListServiceDP;
import com.myntra.apiTests.portalservices.checkoutservice.CartNodes;
import com.myntra.apiTests.portalservices.checkoutservice.CheckoutTestsHelper;
import com.myntra.apiTests.portalservices.checkoutservice.WishListNodes;
import com.myntra.apiTests.portalservices.checkoutservice.CheckoutServiceConstants;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import com.jayway.jsonpath.JsonPath;
import com.myntra.apiTests.portalservices.checkoutservice.CheckoutDataProviderEnum;
import com.myntra.apiTests.portalservices.commons.StatusNodes;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.Toolbox;
import com.myntra.lordoftherings.gandalf.RequestGenerator;

public class WishListServiceTests extends WishListServiceDP {
	
	
	static Logger log = Logger.getLogger(WishListServiceTests.class);
	static CheckoutTestsHelper checkoutTestsHelper = new CheckoutTestsHelper();
	static Initialize init = new Initialize("/Data/configuration");
	
	
	
	/**
	 * This Test Case used to invoke CheckoutService moveItemToWishList API and verify for success response
	 * 
	 * @param userName
	 * @param password
	 * @param skuId
	 * @param EMPTY_VALUE.name()))
	 * @param successRespCode
	 */
	@Test(groups = { "Sanity","FoxSanity", "Regression", "MiniRegression", "ExhaustiveRegression" }, 
			dataProvider="WishListServiceDP_moveToWishListDataProvider_validateAPIResponse",description="1. hit IDP service and login the user. "
					+ "\n 2. hit fetchCart API. \n 3. if user's Cart is empty then first add items to Cart(\n a.hit Search Service API and extract some skuIDs"
					+ "\n b. C. hit addItemToCart API giving skuId which doesnt exists in Cart)"
					+ "\n 4. hit moveItemToWishlist API"
					+ "\n 5. Check the service response must be 200")
	public void WishListServiceTests_moveItemToWishList_verifySuccessResponse(String userName, String password,  String successRespCode)
	{
		long startTime = Calendar.getInstance().getTimeInMillis();
		String addItemQty ="1";
		Boolean itemAdded = false;
		RequestGenerator fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
		System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"+fetchCartReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"+fetchCartReqGen.respvalidate.returnresponseasstring());
	
		AssertJUnit.assertTrue("Error while invoking operationFetchCart API",
				fetchCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(CheckoutServiceConstants.SUCCESS_STATUS_TYPE));
		
		if(fetchCartReqGen.respvalidate.NodeValue(CartNodes.CART_TOTAL_COUNT.toString(), true).equals(CheckoutServiceConstants.EMPTY_VALUE))
		{
			List<Integer> styleIdList = checkoutTestsHelper.performSeachServiceToGetStyleIds(CheckoutServiceConstants.MWL_SEARCH_PROD, CheckoutServiceConstants.MWL_SEARCH_QTY, CheckoutServiceConstants.MWL_SEARCH_RET_DOCS, CheckoutServiceConstants.MWL_SEARCH_IS_FACET);
			
			if(!CollectionUtils.isEmpty(styleIdList))
			{
				for(Integer styleId : styleIdList)
				{	if(itemAdded)
						break;
					System.out.println("\nPrinting the StyleId : "+styleId);
					log.info("\nPrinting the StyleId : "+styleId);
					
					RequestGenerator getStyleDataReqGen = checkoutTestsHelper.performGetStyleDataOperation(String.valueOf(styleId));
					System.out.println("\nPrinting StyleService getStyleData API response :\n\n"+getStyleDataReqGen.respvalidate.returnresponseasstring());
					log.info("\nPrinting StyleService getStyleDataAPI response :\n\n"+getStyleDataReqGen.respvalidate.returnresponseasstring());
					
					AssertJUnit.assertTrue("Error while getting style data",
							getStyleDataReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(CheckoutServiceConstants.SUCCESS_STATUS_TYPE));
					
					String response = getStyleDataReqGen.respvalidate.returnresponseasstring();
					List<Integer> ids = JsonPath.read(response, "$.data..styleOptions[*].id");
					
					for(int i = 0 ; i < ids.size(); i++)
					{
						String skuId = JsonPath.read(response, "$.data..styleOptions["+i+"].skuId").toString();
						String inventoryCount = JsonPath.read(response, "$.data..styleOptions["+i+"].inventoryCount").toString();
						String available = JsonPath.read(response, "$.data..styleOptions["+i+"].available").toString();
						
						if(!inventoryCount.equals(CheckoutServiceConstants.EMPTY_VALUE) && available.equals(CheckoutServiceConstants.TRUE_VALUE))
						{
							fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
							System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"+fetchCartReqGen.respvalidate.returnresponseasstring());
							log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"+fetchCartReqGen.respvalidate.returnresponseasstring());
							
							if(StringUtils.isBlank(checkoutTestsHelper.getItemIdFromSKUId(fetchCartReqGen, skuId, CheckoutDataProviderEnum.CART.name())))
							{
								RequestGenerator addItemToCartReqGen = checkoutTestsHelper.addItemToCart(userName, password, skuId, String.valueOf(addItemQty), CheckoutServiceConstants.ADD_OPERATION);
								System.out.println("\nPrinting addItemToCart API response :\n\n"+addItemToCartReqGen.respvalidate.returnresponseasstring());
								log.info("\nPrinting addItemToCart API response :\n\n"+addItemToCartReqGen.respvalidate.returnresponseasstring());
								itemAdded = true;
								AssertJUnit.assertTrue("Error while invoking CheckoutService addItemToCart API",
										addItemToCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(CheckoutServiceConstants.SUCCESS_STATUS_TYPE));
								
								System.out.println("\n"+skuId+" is available and it doesn't exists in cart so added to cart");
								log.info("\n"+skuId+" is available and it doesn't exists in cart so added to cart");
								break;
								
							} else {
								System.out.println("\n"+skuId + " is not added because this item already exists in cart");
								log.error("\n"+skuId + " is not added because this item already exists in cart");
							}
							
						} else {
							System.out.println("\n"+skuId+" is not added because this item is currently out of stock");
							log.info("\n"+skuId+" is not added because this item is currently out of stock");
						}
					}
				}
				
			} else {
				System.out.println("\nError while invoking SearchService getStyleData API");
				log.error("\nError while invoking SearchService getStyleData API");
				AssertJUnit.fail("Error while invoking SearchService getStyleData API");
			}
			
			fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
			System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"+fetchCartReqGen.respvalidate.returnresponseasstring());
			log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"+fetchCartReqGen.respvalidate.returnresponseasstring());
		
			AssertJUnit.assertTrue("Error while invoking CheckoutService operationFetchCart API",
					fetchCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(CheckoutServiceConstants.SUCCESS_STATUS_TYPE));
		}

		String skuId = fetchCartReqGen.respvalidate.NodeValue(CartNodes.CART_ITEM_ENTRIES_SELSIZE_SKUID.toString(), false);
		String itemId = checkoutTestsHelper.getItemIdFromSKUId(fetchCartReqGen, skuId, CheckoutDataProviderEnum.CART.name());
		
		RequestGenerator fetchWishlistReqGen = checkoutTestsHelper.operationFetchWishList(userName, password);
		System.out.println("\nPrinting CheckoutService operationFetchWishlist API response :\n\n"+fetchWishlistReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService operationFetchWishlist API response :\n\n"+fetchWishlistReqGen.respvalidate.returnresponseasstring());
		
		AssertJUnit.assertTrue("Error while invoking CheckoutService operationFetchWishlist API",
				fetchWishlistReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(CheckoutServiceConstants.SUCCESS_STATUS_TYPE));
		
		if(StringUtils.isBlank(checkoutTestsHelper.getItemIdFromSKUId(fetchCartReqGen, skuId, CheckoutDataProviderEnum.WISHLIST.name())))
		{
			System.out.println("\nPrinting skuId : "+skuId+" & itemId : "+itemId+" is moving to wishlist from cart\n");
			log.info("\nPrinting skuId : "+skuId+" & itemId : "+itemId+" is going is moving to wishlist from cart\n");
			
			RequestGenerator moveItemToWishListReqGen = checkoutTestsHelper.moveItemToWishList(userName, password, itemId);
			System.out.println("\nPrinting CheckoutService moveItemToWishList API response :\n\n"+moveItemToWishListReqGen.respvalidate.returnresponseasstring());
			log.info("\nPrinting CheckoutService moveItemToWishList API response :\n\n"+moveItemToWishListReqGen.respvalidate.returnresponseasstring());

			AssertJUnit.assertEquals("CheckoutService moveItemToWishList API is not working", 
						Integer.parseInt(successRespCode), moveItemToWishListReqGen.response.getStatus());
			
		} else {
			System.out.println("\nPrinting skuId : "+skuId+" & itemId : "+itemId+" is already present in wishlist\n");
			log.info("\nPrinting skuId : "+skuId+" & itemId : "+itemId+" is already present in wishlist\n");
		}
			
		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime)/1000.0;
		
		System.out.println("\nTime taken to execute - TestCase - CheckoutServiceTests_moveItemToWishList_verifySuccessResponse : "+timeTaken+" seconds\n");
		log.info("\nTime taken to execute - TestCase - CheckoutServiceTests_moveItemToWishList_verifySuccessResponse : "+timeTaken+" seconds\n");
	}
	
	/**
	 * This Test Case used to invoke CheckoutService moveItemToWishList API and verify for success status response
	 * 
	 * @param userName
	 * @param password
	 * @param successStatusCode
	 * @param successStatusMsg
	 * @param successStatusType
	 */
	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression" }, dataProvider="WishListServiceDP_moveToWishListDataProvider_positiveCases",description="1. hit IDP service and login the user. "
			+ "\n 2. hit fetchCart API. \n 3. if user's Cart is empty then first add items to Cart(\n a.hit Search Service API and extract some skuIDs"
			+ "\n b. C. hit addItemToCart API giving skuId which doesnt exists in Cart)"
			+ "\n 4. hit moveItemToWishlist API"
			+ "\n 5. Verify statusCode, statusMessage & statusType must be of success.")
	public void WishListServiceTests_moveItemToWishList_verifySuccessStatusResponse(String userName, String password, String successStatusCode, String successStatusMsg, 
			String successStatusType)
	{
		long startTime = Calendar.getInstance().getTimeInMillis();
		String addItemQty ="1";
		Boolean itemAdded = false;
		RequestGenerator fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
		System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"+fetchCartReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"+fetchCartReqGen.respvalidate.returnresponseasstring());
	
		AssertJUnit.assertTrue("Error while invoking CheckoutService operationFetchCart API",
				fetchCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(CheckoutServiceConstants.SUCCESS_STATUS_TYPE));
		
		if(fetchCartReqGen.respvalidate.NodeValue(CartNodes.CART_TOTAL_COUNT.toString(), true).equals(CheckoutServiceConstants.EMPTY_VALUE))
		{
			List<Integer> styleIdList = checkoutTestsHelper.performSeachServiceToGetStyleIds(CheckoutServiceConstants.MWL_SEARCH_PROD, CheckoutServiceConstants.MWL_SEARCH_QTY, CheckoutServiceConstants.MWL_SEARCH_RET_DOCS, CheckoutServiceConstants.MWL_SEARCH_IS_FACET);
			
			if(!CollectionUtils.isEmpty(styleIdList))
			{
				for(Integer styleId : styleIdList)
				{	if(itemAdded)
						break;
					System.out.println("\nPrinting the StyleId : "+styleId);
					log.info("\nPrinting the StyleId : "+styleId);
					
					RequestGenerator getStyleDataReqGen = checkoutTestsHelper.performGetStyleDataOperation(String.valueOf(styleId));
					System.out.println("\nPrinting StyleService getStyleData API response :\n\n"+getStyleDataReqGen.respvalidate.returnresponseasstring());
					log.info("\nPrinting StyleService getStyleData API response :\n\n"+getStyleDataReqGen.respvalidate.returnresponseasstring());
					
					AssertJUnit.assertTrue("Error while getting style data",
							getStyleDataReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(CheckoutServiceConstants.SUCCESS_STATUS_TYPE.toString()));
					
					String response = getStyleDataReqGen.respvalidate.returnresponseasstring();
					List<Integer> ids = JsonPath.read(response, "$.data..styleOptions[*].id");
					
					for(int i = 0 ; i < ids.size(); i++)
					{
						String skuId = JsonPath.read(response, "$.data..styleOptions["+i+"].skuId").toString();
						String inventoryCount = JsonPath.read(response, "$.data..styleOptions["+i+"].inventoryCount").toString();
						String available = JsonPath.read(response, "$.data..styleOptions["+i+"].available").toString();
						
						if(!inventoryCount.equals(CheckoutServiceConstants.EMPTY_VALUE) && available.equals(CheckoutServiceConstants.TRUE_VALUE))
						{
							fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
							System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"+fetchCartReqGen.respvalidate.returnresponseasstring());
							log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"+fetchCartReqGen.respvalidate.returnresponseasstring());
							
							if(StringUtils.isBlank(checkoutTestsHelper.getItemIdFromSKUId(fetchCartReqGen, skuId, CheckoutDataProviderEnum.CART.name())))
							{
								RequestGenerator addItemToCartReqGen = checkoutTestsHelper.addItemToCart(userName, password, skuId, String.valueOf(addItemQty), CheckoutServiceConstants.ADD_OPERATION);
								System.out.println("\nPrinting CheckoutService addItemToCart API response :\n\n"+addItemToCartReqGen.respvalidate.returnresponseasstring());
								log.info("\nPrinting CheckoutService addItemToCart API response :\n\n"+addItemToCartReqGen.respvalidate.returnresponseasstring());
								itemAdded = true;
								AssertJUnit.assertTrue("Error while invoking CheckoutService addItemToCart API",
										addItemToCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(CheckoutServiceConstants.SUCCESS_STATUS_TYPE));
								
								System.out.println("\n"+skuId+" is available and it doesn't exists in cart so added to cart");
								log.info("\n"+skuId+" is available and it doesn't exists in cart so added to cart");
								break;
								
							} else {
								System.out.println("\n"+skuId + " is not added because this item already exists in cart");
								log.error("\n"+skuId + " is not added because this item already exists in cart");
							}
							
						} else {
							System.out.println("\n"+skuId+" is not added because this item is currently out of stock");
							log.info("\n"+skuId+" is not added because this item is currently out of stock");
						}
					}
				}
				
			} else {
				System.out.println("\nError while invoking SearchService getStyleData API");
				log.error("\nError while invoking SearchService getStyleData API");
				AssertJUnit.fail("Error while invoking SearchService getStyleData API");
			}
			
			fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
			System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"+fetchCartReqGen.respvalidate.returnresponseasstring());
			log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"+fetchCartReqGen.respvalidate.returnresponseasstring());
		
			AssertJUnit.assertTrue("Error while invoking CheckoutService operationFetchCart API",
					fetchCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(CheckoutServiceConstants.SUCCESS_STATUS_TYPE));
		}
		
		String skuId = fetchCartReqGen.respvalidate.NodeValue(CartNodes.CART_ITEM_ENTRIES_SELSIZE_SKUID.toString(), false);
		String itemId = checkoutTestsHelper.getItemIdFromSKUId(fetchCartReqGen, skuId, CheckoutDataProviderEnum.CART.name());
		
		RequestGenerator fetchWishlistReqGen = checkoutTestsHelper.operationFetchWishList(userName, password);
		System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"+fetchWishlistReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"+fetchWishlistReqGen.respvalidate.returnresponseasstring());
		
		AssertJUnit.assertTrue("Error while invoking CheckoutService operationFetchWishlist API",
				fetchWishlistReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(CheckoutServiceConstants.SUCCESS_STATUS_TYPE));
		
		if(StringUtils.isBlank(checkoutTestsHelper.getItemIdFromSKUId(fetchCartReqGen, skuId, CheckoutDataProviderEnum.WISHLIST.name())))
		{
			System.out.println("\nPrinting skuId : "+skuId+" & itemId : "+itemId+" is going to wishlist from cart\n");
			log.info("\nPrinting skuId : "+skuId+" & itemId : "+itemId+" is going is going to wishlist from cart\n");
			
			RequestGenerator moveItemToWishListReqGen = checkoutTestsHelper.moveItemToWishList(userName, password, itemId);
			System.out.println("\nPrinting CheckoutService moveItemToWishList API response :\n\n"+moveItemToWishListReqGen.respvalidate.returnresponseasstring());
			log.info("\nPrinting CheckoutService moveItemToWishList API response :\n\n"+moveItemToWishListReqGen.respvalidate.returnresponseasstring());

			AssertJUnit.assertTrue("CheckoutService moveItemToWishList API response status code is not a success status code", 
					moveItemToWishListReqGen.respvalidate.NodeValue(StatusNodes.STATUS_CODE.toString(), true).contains(successStatusCode));	
			
			AssertJUnit.assertTrue("CheckoutService moveItemToWishList API response status message is not a success status message",
					moveItemToWishListReqGen.respvalidate.NodeValue(StatusNodes.STATUS_MESSAGE.toString(), true).contains(successStatusMsg));
			 
			AssertJUnit.assertTrue("CheckoutService moveItemToWishList API response status type is not a success status type",
					moveItemToWishListReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(successStatusType));
			
		} else {
			System.out.println("\nPrinting skuId : "+skuId+" & itemId : "+itemId+" is already present in wishlist\n");
			log.info("\nPrinting skuId : "+skuId+" & itemId : "+itemId+" is already present in wishlist\n");
		}
			
		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime)/1000.0;
		
		System.out.println("\nTime taken to execute - TestCase - CheckoutServiceTests_moveItemToWishList_verifySuccessStatusResponse : "+timeTaken+" seconds\n");
		log.info("\nTime taken to execute - TestCase - CheckoutServiceTests_moveItemToWishList_verifySuccessStatusResponse : "+timeTaken+" seconds\n");
	}

	
	/**
	 * This Test Case used to invoke CheckoutService operationFetchWishList API and verify for success response
	 * 
	 * @param userName
	 * @param password
	 * @param successRespCode
	 */
	@Test(groups = { "Smoke", "FoxSanity","Sanity", "ProdSanity", "Regression", "MiniRegression", "ExhaustiveRegression" }, 
			dataProvider="WishListServiceDP_operationFetchWishListDataProvider_validateAPIResponse", description="1. Hit IDP service & login as user."
					+ "\n 2. Hit fetchWishlist API. \n 3. Verify respone of API must be success.")
	public void WishListServiceTests_operationFetchWishList_verifySuccessResponse(String userName, String password, String successRespCode)
	{
		long startTime = Calendar.getInstance().getTimeInMillis();
		//String addItemQty = "1";
		RequestGenerator fetchWishlistReqGen = checkoutTestsHelper.operationFetchWishList(userName, password);
		System.out.println("\nPrinting CheckoutService operationFetchWishList API response :\n\n"+fetchWishlistReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService operationFetchWishList API response :\n\n"+fetchWishlistReqGen.respvalidate.returnresponseasstring());
		
		
		AssertJUnit.assertEquals("CheckoutService operationFetchWishlist API is not working", 
				Integer.parseInt(successRespCode), fetchWishlistReqGen.response.getStatus());
		
		String wishlistCount = fetchWishlistReqGen.respvalidate.NodeValue(WishListNodes.WISHLIST_TOTAL_COUNT.toString(), true);
		System.out.println("total count of wishist is "+ wishlistCount);
		
//		
//		if(fetchWishlistReqGen.respvalidate.NodeValue(WishListNodes.WISHLIST_TOTAL_COUNT.toString(), true).equals(EMPTY_VALUE))
//		{	
//			System.out.println("As no items are present in user's wishlist , adding new items to wishlist");
//			List<Integer> styleIdList = checkoutTestsHelper.performSeachServiceToGetStyleIds(AWL_SEARCH_PROD, AWL_SEARCH_QTY, AWL_SEARCH_RET_DOCS, AWL_SEARCH_IS_FACET);
//			
//			if(!CollectionUtils.isEmpty(styleIdList))
//			{
//				for(Integer styleId : styleIdList)
//				{
//					System.out.println("\nPrinting the StyleId : "+styleId);
//					log.info("\nPrinting the StyleId : "+styleId);
//				
//					RequestGenerator getStyleDataReqGen = checkoutTestsHelper.performGetStyleDataOperation(String.valueOf(styleId));
//					System.out.println("\nPrinting StyleService getStyleData API response :\n\n"+getStyleDataReqGen.respvalidate.returnresponseasstring());
//					log.info("\nPrinting StyleService getStyleData API response :\n\n"+getStyleDataReqGen.respvalidate.returnresponseasstring());
//				
//					AssertJUnit.assertTrue("Error while getting style data",
//						getStyleDataReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));
//				
//					String response = getStyleDataReqGen.respvalidate.returnresponseasstring();
//					List<Integer> ids = JsonPath.read(response, "$.data..styleOptions[*].id");
//				
//					for(int i = 0 ; i < ids.size(); i++)
//					{
//						String skuId = JsonPath.read(response, "$.data..styleOptions["+i+"].skuId").toString();
//						String inventoryCount = JsonPath.read(response, "$.data..styleOptions["+i+"].inventoryCount").toString();
//						String available = JsonPath.read(response, "$.data..styleOptions["+i+"].available").toString();
//					
//						if(!inventoryCount.equals(EMPTY_VALUE) && available.equals(TRUE_VALUE))
//						{	
//							int invCount = Integer.parseInt(inventoryCount);
//							int qty = Integer.parseInt(addItemQty);
//							int itemQtyToAdd = invCount <= qty  ? invCount : qty;
//							
//							RequestGenerator addItemToWishListReqGen = checkoutTestsHelper.addItemToWishList(userName, password, skuId, null, 
//								String.valueOf(itemQtyToAdd), "ADD");
//							
//							System.out.println("\nPrinting CheckoutService addItemToWishList API response :\n\n"+addItemToWishListReqGen.respvalidate.returnresponseasstring());
//							log.info("\nPrinting CheckoutService addItemToWishList API response :\n\n"+addItemToWishListReqGen.respvalidate.returnresponseasstring());
//							
//							AssertJUnit.assertEquals("CheckoutService addItemToWishList API is not working",
//								Integer.parseInt(successRespCode), addItemToWishListReqGen.response.getStatus());
//							
//							System.out.println("\n"+skuId+" is available but not in wishlist so added to wishlist");
//							log.info("\n"+skuId+" is available but not in wishlist so added to wishlist");
//							break;
//						
//						} else {
//							System.out.println("\n"+skuId+" is not added because this item is currently out of stock");
//							log.info("\n"+skuId+" is not added because this item is currently out of stock");
//							}
//					
//					} 
//				 }
//			   }else {
//					System.out.println("\nError while invoking SearchService getStyleData API");
//					log.error("\nError while invoking SearchService getStyleData API");
//					AssertJUnit.fail("Error while invoking SearchService getStyleData API");
//				}
//			
//			RequestGenerator fetchWishlistReqGen1 = checkoutTestsHelper.operationFetchWishList(userName, password);
//			System.out.println("\nPrinting CheckoutService operationFetchWishList API response :\n\n"+fetchWishlistReqGen1.respvalidate.returnresponseasstring());
//			log.info("\nPrinting CheckoutService operationFetchWishList API response :\n\n"+fetchWishlistReqGen1.respvalidate.returnresponseasstring());
//				
//			AssertJUnit.assertEquals("CheckoutService operationFetchWishlist API is not working", 
//					Integer.parseInt(successRespCode), fetchWishlistReqGen1.response.getStatus());
//			
//			String count = fetchWishlistReqGen.respvalidate.NodeValue(WishListNodes.WISHLIST_TOTAL_COUNT.toString(), true);
//			System.out.println("total count of wishist is "+ count);
//			
//			}
							
		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime)/1000.0;
		
		System.out.println("\nTime taken to execute - TestCase - CheckoutServiceTests_operationFetchWishList_verifySuccessResponse : "+timeTaken+" seconds\n");
		log.info("\nTime taken to execute - TestCase - CheckoutServiceTests_operationFetchWishList_verifySuccessResponse : "+timeTaken+" seconds\n");

	}
	
	/**
	 * This Test Case used to invoke CheckoutService operationFetchWishList API and verify for success status response
	 * 
	 * @param userName
	 * @param password
	 * @param successStatusCode
	 * @param successStatusMsg
	 * @param successStatusType
	 */
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression", "MiniRegression", "ExhaustiveRegression" }, 
			dataProvider="WishListServiceDP_operationFetchWishListDataProvider_positiveCases", description="1. Hit IDP service & login as user."
					+ "\n 2. Hit fetchWishlist API. \n 3. Verify statusCode, statusMessage & statusType of API must be of success")
	public void WishListServiceTests_operationFetchWishList_verifySuccessStatusResponse(String userName, String password, String successStatusCode, String successStatusMsg, 
			String successStatusType)
	{
		long startTime = Calendar.getInstance().getTimeInMillis();
		String addItemQty ="1";
		RequestGenerator fetchWishlistReqGen = checkoutTestsHelper.operationFetchWishList(userName, password);
		System.out.println("\nPrinting CheckoutService operationFetchWishList API response :\n\n"+fetchWishlistReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService operationFetchWishList API response :\n\n"+fetchWishlistReqGen.respvalidate.returnresponseasstring());
		
		if(fetchWishlistReqGen.respvalidate.NodeValue(WishListNodes.WISHLIST_TOTAL_COUNT.toString(), true).equals(CheckoutServiceConstants.EMPTY_VALUE))
		{
			List<Integer> styleIdList = checkoutTestsHelper.performSeachServiceToGetStyleIds(CheckoutServiceConstants.AWL_SEARCH_PROD, CheckoutServiceConstants.AWL_SEARCH_QTY, CheckoutServiceConstants.AWL_SEARCH_RET_DOCS, CheckoutServiceConstants.AWL_SEARCH_IS_FACET);
			if(!CollectionUtils.isEmpty(styleIdList))
			{
				for(Integer styleId : styleIdList)
				{
					System.out.println("\nPrinting the StyleId : "+styleId);
					log.info("\nPrinting the StyleId : "+styleId);
				
					RequestGenerator getStyleDataReqGen = checkoutTestsHelper.performGetStyleDataOperation(String.valueOf(styleId));
					System.out.println("\nPrinting StyleService getStyleData API response :\n\n"+getStyleDataReqGen.respvalidate.returnresponseasstring());
					log.info("\nPrinting StyleService getStyleData API response :\n\n"+getStyleDataReqGen.respvalidate.returnresponseasstring());
				
					AssertJUnit.assertTrue("Error while getting style data",
						getStyleDataReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(CheckoutServiceConstants.SUCCESS_STATUS_TYPE));
				
					String response = getStyleDataReqGen.respvalidate.returnresponseasstring();
					List<Integer> ids = JsonPath.read(response, "$.data..styleOptions[*].id");
				
					for(int i = 0 ; i < ids.size(); i++)
					{
						String skuId = JsonPath.read(response, "$.data..styleOptions["+i+"].skuId").toString();
						String inventoryCount = JsonPath.read(response, "$.data..styleOptions["+i+"].inventoryCount").toString();
						String available = JsonPath.read(response, "$.data..styleOptions["+i+"].available").toString();
					
						if(!inventoryCount.equals(CheckoutServiceConstants.EMPTY_VALUE) && available.equals(CheckoutServiceConstants.TRUE_VALUE))
						{	
							int invCount = Integer.parseInt(inventoryCount);
							int qty = Integer.parseInt(addItemQty);
							int itemQtyToAdd = invCount <= qty  ? invCount : qty;
							
							RequestGenerator addItemToWishListReqGen = checkoutTestsHelper.addItemToWishList(userName, password, skuId, null, 
								String.valueOf(itemQtyToAdd), "ADD");
							
							System.out.println("\nPrinting CheckoutService addItemToWishList API response :\n\n"+addItemToWishListReqGen.respvalidate.returnresponseasstring());
							log.info("\nPrinting CheckoutService addItemToWishList API response :\n\n"+addItemToWishListReqGen.respvalidate.returnresponseasstring());
							
							AssertJUnit.assertEquals("CheckoutService addItemToWishList API is not working",
								200, addItemToWishListReqGen.response.getStatus());
							
							System.out.println("\n"+skuId+" is available but not in wishlist so added to wishlist");
							log.info("\n"+skuId+" is available but not in wishlist so added to wishlist");
							break;
						
						} else {
							System.out.println("\n"+skuId+" is not added because this item is currently out of stock");
							log.info("\n"+skuId+" is not added because this item is currently out of stock");
						}
					}
				}
			}else {
				System.out.println("\nError while invoking SearchService getStyleData API");
				log.error("\nError while invoking SearchService getStyleData API");
				AssertJUnit.fail("Error while invoking SearchService getStyleData API");
			}
		
			RequestGenerator fetchWishlistReqGen1 = checkoutTestsHelper.operationFetchWishList(userName, password);
			System.out.println("\nPrinting CheckoutService operationFetchWishList API response :\n\n"+fetchWishlistReqGen1.respvalidate.returnresponseasstring());
			log.info("\nPrinting CheckoutService operationFetchWishList API response :\n\n"+fetchWishlistReqGen1.respvalidate.returnresponseasstring());
				
				
			AssertJUnit.assertTrue("CheckoutService operationFetchWishList API response status code is not a success status code", 
						fetchWishlistReqGen1.respvalidate.NodeValue(StatusNodes.STATUS_CODE.toString(), true).contains(successStatusCode));	
				
			AssertJUnit.assertTrue("CheckoutService operationFetchWishList API response status message is not a success status message",
						fetchWishlistReqGen1.respvalidate.NodeValue(StatusNodes.STATUS_MESSAGE.toString(), true).contains(successStatusMsg));
				 
			AssertJUnit.assertTrue("CheckoutService operationFetchWishList API response status type is not a success status type",
						fetchWishlistReqGen1.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(successStatusType));
				
				
		} else {
			AssertJUnit.assertTrue("CheckoutService operationFetchWishList API response status code is not a success status code", 
					fetchWishlistReqGen.respvalidate.NodeValue(StatusNodes.STATUS_CODE.toString(), true).contains(successStatusCode));	
			
			AssertJUnit.assertTrue("CheckoutService operationFetchWishList API response status message is not a success status message",
					fetchWishlistReqGen.respvalidate.NodeValue(StatusNodes.STATUS_MESSAGE.toString(), true).contains(successStatusMsg));
			 
			AssertJUnit.assertTrue("CheckoutService operationFetchWishList API response status type is not a success status type",
					fetchWishlistReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(successStatusType));
			
		}
		
		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime)/1000.0;
		
		System.out.println("\nTime taken to execute - TestCase - CheckoutServiceTests_operationFetchWishList_verifySuccessStatusResponse : "+timeTaken+" seconds\n");
		log.info("\nTime taken to execute - TestCase - CheckoutServiceTests_operationFetchWishList_verifySuccessStatusResponse : "+timeTaken+" seconds\n");
		
	}
	

	/**
	 * This Test Case used to invoke CheckoutService addItemToWishListUsingSkuId API and verify for success response
	 * 
	 * @param userName
	 * @param password
	 * @param addItemQty
	 * @param addOperation
	 * @param successRespCode
	 */
	@Test(groups = { "Sanity","FoxSanity", "Regression", "MiniRegression", "ExhaustiveRegression" }, 
			dataProvider="WishListServiceDP_addItemToWishListUsingSkuIdDataProvider_validateAPIResponse", description="1. Perform search and extract styleIds. \n2.Check if searched styleIds exists in user's cart or not. "
					+ "\n3. login user using IDP service call. \n4.hit addItemToWishlist API giving skuId which doesnt exists in wishlist"
					+ "\n5.Check the service response must be 200")
	public void WishListServiceTests_addItemToWishListUsingSkuId_verifySuccessResponse(String userName, String password, String addItemQty, String addOperation, 
			String successRespCode)
	{
		long startTime = Calendar.getInstance().getTimeInMillis();
		List<Integer> styleIdList = checkoutTestsHelper.performSeachServiceToGetStyleIds(CheckoutServiceConstants.AWL_SEARCH_PROD, CheckoutServiceConstants.AWL_SEARCH_QTY, CheckoutServiceConstants.AWL_SEARCH_RET_DOCS, CheckoutServiceConstants.AWL_SEARCH_IS_FACET);
		
		if(!CollectionUtils.isEmpty(styleIdList))
		{
			for(Integer styleId : styleIdList)
			{
				System.out.println("\nPrinting the StyleId : "+styleId);
				log.info("\nPrinting the StyleId : "+styleId);
				
				RequestGenerator getStyleDataReqGen = checkoutTestsHelper.performGetStyleDataOperation(String.valueOf(styleId));
				System.out.println("\nPrinting StyleService getStyleData API response :\n\n"+getStyleDataReqGen.respvalidate.returnresponseasstring());
				log.info("\nPrinting StyleService getStyleData API response :\n\n"+getStyleDataReqGen.respvalidate.returnresponseasstring());
				
				AssertJUnit.assertTrue("Error while getting style data",
						getStyleDataReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(CheckoutServiceConstants.SUCCESS_STATUS_TYPE));
				
				String response = getStyleDataReqGen.respvalidate.returnresponseasstring();
				List<Integer> ids = JsonPath.read(response, "$.data..styleOptions[*].id");
				
				for(int i = 0 ; i < ids.size(); i++)
				{
					String skuId = JsonPath.read(response, "$.data..styleOptions["+i+"].skuId").toString();
					String inventoryCount = JsonPath.read(response, "$.data..styleOptions["+i+"].inventoryCount").toString();
					String available = JsonPath.read(response, "$.data..styleOptions["+i+"].available").toString();
					
					if(!inventoryCount.equals(CheckoutServiceConstants.EMPTY_VALUE) && available.equals(CheckoutServiceConstants.TRUE_VALUE))
					{
						RequestGenerator fetchWishlistReqGen = checkoutTestsHelper.operationFetchWishList(userName, password);
						System.out.println("\nPrinting CheckoutService operationFetchWishList API response :\n\n"+fetchWishlistReqGen.respvalidate.returnresponseasstring());
						log.info("\nPrinting CheckoutService operationFetchWishList API response :\n\n"+fetchWishlistReqGen.respvalidate.returnresponseasstring());
						
						if(StringUtils.isBlank(checkoutTestsHelper.getItemIdFromSKUId(fetchWishlistReqGen, skuId, CheckoutDataProviderEnum.WISHLIST.name())))
						{
							int invCount = Integer.parseInt(inventoryCount);
							int qty = Integer.parseInt(addItemQty);
							int itemQtyToAdd = invCount <= qty  ? invCount : qty;
							
							RequestGenerator addItemToWishListReqGen = checkoutTestsHelper.addItemToWishList(userName, password, skuId, null, 
									String.valueOf(itemQtyToAdd), addOperation);
							
							System.out.println("\nPrinting CheckoutService addItemToWishList API response :\n\n"+addItemToWishListReqGen.respvalidate.returnresponseasstring());
							log.info("\nPrinting CheckoutService addItemToWishList API response :\n\n"+addItemToWishListReqGen.respvalidate.returnresponseasstring());
							
							AssertJUnit.assertEquals("CheckoutService addItemToWishList API is not working",
									Integer.parseInt(successRespCode), addItemToWishListReqGen.response.getStatus());
							
							System.out.println("\n"+skuId+" is available but not in wishlist so added to wishlist");
							log.info("\n"+skuId+" is available but not in wishlist so added to wishlist");
							break;
							
						} else {
							System.out.println("\n"+skuId + " is not added because this item already exists in wishlist");
							log.error("\n"+skuId + " is not added because this item already exists in wishlist");
						}
						
					} else {
						System.out.println("\n"+skuId+" is not added because this item is currently out of stock");
						log.info("\n"+skuId+" is not added because this item is currently out of stock");
					}
				}
			}
			
		} else {
			System.out.println("\nError while invoking SearchService getStyleData API");
			log.error("\nError while invoking SearchService getStyleData API");
			AssertJUnit.fail("Error while invoking SearchService getStyleData API");
		}
		
		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime)/1000.0;
		
		System.out.println("\nTime taken to execute - TestCase - CheckoutServiceTests_addItemToWishListUsingSkuId_verifySuccessResponse : "+timeTaken+" seconds\n");
		log.info("\nTime taken to execute - TestCase - CheckoutServiceTests_addItemToWishListUsingSkuId_verifySuccessResponse : "+timeTaken+" seconds\n");
	}
	
	/**
	 * This Test Case used to invoke CheckoutService addItemToWishListUsingSkuId API and verify for success status response
	 * 
	 * @param userName
	 * @param password
	 * @param addItemQty
	 * @param addOperation
	 * @param successStatusCode
	 * @param successStatusMsg
	 * @param successStatusType
	 */
	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression" }, 
			dataProvider="WishListServiceDP_addItemToWishListUsingSkuIdDataProvider_positiveCases", description="1.Perform search and extract styleIds. \n2.Check if searched styleIds exists in user's cart or not. "
					+ "\n3.login user using IDP service call. \n4.hit addItemToWishlist API giving skuId which doesnt exists in wishlist"
					+ "\n5.Verify statusCode, statusMessage & statusType of response must be of success.")
	public void WishListServiceTests_addItemToWishListUsingSkuId_verifySuccessStatusResponse(String userName, String password, String addItemQty, String addOperation, 
			String successStatusCode, String successStatusMsg, String successStatusType)
	{
		long startTime = Calendar.getInstance().getTimeInMillis();
		List<Integer> styleIdList = checkoutTestsHelper.performSeachServiceToGetStyleIds(CheckoutServiceConstants.AWL_SEARCH_PROD, CheckoutServiceConstants.AWL_SEARCH_QTY, CheckoutServiceConstants.AWL_SEARCH_RET_DOCS, CheckoutServiceConstants.AWL_SEARCH_IS_FACET);
		
		if(!CollectionUtils.isEmpty(styleIdList))
		{
			for(Integer styleId : styleIdList)
			{
				System.out.println("\nPrinting the StyleId : "+styleId);
				log.info("\nPrinting the StyleId : "+styleId);
				
				RequestGenerator getStyleDataReqGen = checkoutTestsHelper.performGetStyleDataOperation(String.valueOf(styleId));
				System.out.println("\nPrinting StyleService getStyleData API response :\n\n"+getStyleDataReqGen.respvalidate.returnresponseasstring());
				log.info("\nPrinting StyleService getStyleData API response :\n\n"+getStyleDataReqGen.respvalidate.returnresponseasstring());
				
				AssertJUnit.assertTrue("Error while getting style data",
						getStyleDataReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(CheckoutServiceConstants.SUCCESS_STATUS_TYPE));
				
				String response = getStyleDataReqGen.respvalidate.returnresponseasstring();
				List<Integer> ids = JsonPath.read(response, "$.data..styleOptions[*].id");
				
				for(int i = 0; i < ids.size(); i++)
				{
					String skuId = JsonPath.read(response, "$.data..styleOptions["+i+"].skuId").toString();
					String inventoryCount = JsonPath.read(response, "$.data..styleOptions["+i+"].inventoryCount").toString();
					String available = JsonPath.read(response, "$.data..styleOptions["+i+"].available").toString();
					
					if(!inventoryCount.equals(CheckoutServiceConstants.EMPTY_VALUE) && available.equals(CheckoutServiceConstants.TRUE_VALUE))
					{
						RequestGenerator fetchWishlistReqGen = checkoutTestsHelper.operationFetchWishList(userName, password);
						System.out.println("\nPrinting CheckoutService operationFetchWishList API response :\n\n"+fetchWishlistReqGen.respvalidate.returnresponseasstring());
						log.info("\nPrinting CheckoutService operationFetchWishList API response :\n\n"+fetchWishlistReqGen.respvalidate.returnresponseasstring());
						
						if(StringUtils.isBlank(checkoutTestsHelper.getItemIdFromSKUId(fetchWishlistReqGen, skuId, CheckoutDataProviderEnum.WISHLIST.name())))
						{
							int invCount = Integer.parseInt(inventoryCount);
							int qty = Integer.parseInt(addItemQty);
							int itemQtyToAdd = invCount <= qty  ? invCount : qty;
							
							RequestGenerator addItemToWishListReqGen = checkoutTestsHelper.addItemToWishList(userName, password, skuId, null, 
									String.valueOf(itemQtyToAdd), addOperation);
							System.out.println("\nPrinting CheckoutService addItemToWishList API response :\n\n"+addItemToWishListReqGen.respvalidate.returnresponseasstring());
							log.info("\nPrinting CheckoutService addItemToWishList API response :\n\n"+addItemToWishListReqGen.respvalidate.returnresponseasstring());
							
							AssertJUnit.assertTrue("CheckoutService addItemToWishList API response status code is not a success status code", 
									addItemToWishListReqGen.respvalidate.NodeValue(StatusNodes.STATUS_CODE.toString(), true).contains(successStatusCode));	
							
							AssertJUnit.assertTrue("CheckoutService addItemToWishList API response status message is not a success status message",
									addItemToWishListReqGen.respvalidate.NodeValue(StatusNodes.STATUS_MESSAGE.toString(), true).contains(successStatusMsg));
							 
							AssertJUnit.assertTrue("CheckoutService addItemToWishList API response status type is not a success status type",
									addItemToWishListReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(successStatusType));
							
							System.out.println("\n"+skuId+" is available but not in wishlist so added to wishlist");
							log.info("\n"+skuId+" is available but not in wishlist so added to wishlist");
							break;
							
						} else {
							System.out.println("\n"+skuId + " is not added because this item already exists in wishlist");
							log.error("\n"+skuId + " is not added because this item already exists in wishlist");
						}
						
					} else {
						System.out.println("\n"+skuId+" is not added because this item is currently out of stock");
						log.info("\n"+skuId+" is not added because this item is currently out of stock");
					}
				}
			}
			
		} else {
			System.out.println("\nError while invoking SearchService API");
			log.error("\nError while invoking SearchService API");
			AssertJUnit.fail("Error while invoking SearchService API");
		}
		
		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime)/1000.0;
		
		System.out.println("\nTime taken to execute - TestCase - CheckoutServiceTests_addItemToWishListUsingSkuId_verifySuccessStatusResponse : "+timeTaken+" seconds\n");
		log.info("\nTime taken to execute - TestCase - CheckoutServiceTests_addItemToWishListUsingSkuId_verifySuccessStatusResponse : "+timeTaken+" seconds\n");
	}
	
	/**
	 * This Test Case used to invoke CheckoutService addItemToWishListUsingStyleId API and verify for success response
	 * 
	 * @param userName
	 * @param password
	 * @param addItemQty
	 * @param addOperation
	 * @param successRespCode
	 */
	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression" }, 
			dataProvider="WishListServiceDP_addItemToWishListUsingStyleIdDataProvider_validateAPIResponse", description="1.Perform search and extract styleIds."
					+ "\n2.login user using IDP service call. \n3.hit addItemToWishlist API giving styleID which doesnt exists in wishlist"
					+ "\n4.Verify statusCode must be 200 in response.")
	public void WishListServiceTests_addItemToWishListUsingStyleId_verifySuccessResponse(String userName, String password, String addItemQty, String addOperation, 
			 String successRespCode)
	{
		long startTime = Calendar.getInstance().getTimeInMillis();
		List<Integer> styleIdList = checkoutTestsHelper.performSeachServiceToGetStyleIds(CheckoutServiceConstants.AWL_SEARCH_PROD, CheckoutServiceConstants.AWL_SEARCH_QTY, CheckoutServiceConstants.AWL_SEARCH_RET_DOCS, CheckoutServiceConstants.AWL_SEARCH_IS_FACET);
		
		if(!CollectionUtils.isEmpty(styleIdList))
		{
			for(Integer styleId : styleIdList)
			{
				System.out.println("\nPrinting the StyleId : "+styleId);
				log.info("\nPrinting the StyleId : "+styleId);
				
				RequestGenerator getStyleDataReqGen = checkoutTestsHelper.performGetStyleDataOperation(String.valueOf(styleId));
				System.out.println("\nPrinting StyleService getStyleData API response :\n\n"+getStyleDataReqGen.respvalidate.returnresponseasstring());
				log.info("\nPrinting StyleService getStyleData API response :\n\n"+getStyleDataReqGen.respvalidate.returnresponseasstring());
				
				AssertJUnit.assertTrue("Error while getting style data",
						getStyleDataReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(CheckoutServiceConstants.SUCCESS_STATUS_TYPE));
				
				String response = getStyleDataReqGen.respvalidate.returnresponseasstring();
				List<Integer> ids = JsonPath.read(response, "$.data..styleOptions[*].id");
				
				for(int i = 0 ; i < ids.size(); i++)
				{
					String skuId = JsonPath.read(response, "$.data..styleOptions["+i+"].skuId").toString();
					String inventoryCount = JsonPath.read(response, "$.data..styleOptions["+i+"].inventoryCount").toString();
					String available = JsonPath.read(response, "$.data..styleOptions["+i+"].available").toString();
					
					if(!inventoryCount.equals(CheckoutServiceConstants.EMPTY_VALUE) && available.equals(CheckoutServiceConstants.TRUE_VALUE))
					{
						RequestGenerator fetchWishlistReqGen = checkoutTestsHelper.operationFetchWishList(userName, password);
						System.out.println("\nPrinting CheckoutService operationFetchWishList API response :\n\n"+fetchWishlistReqGen.respvalidate.returnresponseasstring());
						log.info("\nPrinting CheckoutService operationFetchWishList API response :\n\n"+fetchWishlistReqGen.respvalidate.returnresponseasstring());
						
						if(StringUtils.isBlank(checkoutTestsHelper.getItemIdFromSKUId(fetchWishlistReqGen, skuId, CheckoutDataProviderEnum.WISHLIST.name())))
						{
							int invCount = Integer.parseInt(inventoryCount);
							int qty = Integer.parseInt(addItemQty);
							int itemQtyToAdd = invCount <= qty  ? invCount : qty;
							
							RequestGenerator addItemToWishListReqGen = checkoutTestsHelper.addItemToWishList(userName, password, null, String.valueOf(styleId), String.valueOf(itemQtyToAdd), addOperation);
							System.out.println("\nPrinting CheckoutService addItemToWishList API response :\n\n"+addItemToWishListReqGen.respvalidate.returnresponseasstring());
							log.info("\nPrinting CheckoutService addItemToWishList API response :\n\n"+addItemToWishListReqGen.respvalidate.returnresponseasstring());
							
							AssertJUnit.assertEquals("CheckoutService addItemToWishListUsingStyleId API is not working",
									Integer.parseInt(successRespCode), addItemToWishListReqGen.response.getStatus());
							
							System.out.println("\n"+skuId+" is available but not in wishlist so added to wishlist");
							log.info("\n"+skuId+" is available but not in wishlist so added to wishlist");
							break;
							
						} else {
							System.out.println("\n"+skuId + " is not added because this item already exists in wishlist");
							log.error("\n"+skuId + " is not added because this item already exists in wishlist");
						}
						
					} else {
						System.out.println("\n"+skuId+" is not added because this item is currently out of stock");
						log.info("\n"+skuId+" is not added because this item is currently out of stock");
					}
				}
			}
			
		} else {
			System.out.println("\nError while invoking SearchService API");
			log.error("\nError while invoking SearchService API");
			AssertJUnit.fail("Error while invoking SearchService API");
		}
		
		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime)/1000.0;
		
		System.out.println("\nTime taken to execute - TestCase - CheckoutServiceTests_addItemToWishListUsingStyleId_verifySuccessResponse : "+timeTaken+" seconds\n");
		log.info("\nTime taken to execute - TestCase - CheckoutServiceTests_addItemToWishListUsingStyleId_verifySuccessResponse : "+timeTaken+" seconds\n");
		
	}

	
	/**
	 * This Test Case used to invoke CheckoutService addItemToWishListUsingStyleId API and verify for success status response
	 * 
	 * @param userName
	 * @param password
	 * @param addItemQty
	 * @param addOperation
	 * @param successStatusCode
	 * @param successStatusMsg
	 * @param successStatusType
	 */
	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression" }, 
			dataProvider="WishListServiceDP_addItemToWishListUsingStyleIdDataProvider_positiveCases", description="1.Perform search and extract styleIds."
					+ "\n2.login user using IDP service call. \n3.hit addItemToWishlist API giving styleID which doesnt exists in wishlist"
					+ "\n4.Verify statusCode, statusMessage & statusType must be of type success.")
	public void WishListServiceTests_addItemToWishListUsingStyleId_verifySuccessStatusResponse(String userName, String password, String addItemQty, String addOperation, 
			String successStatusCode, String successStatusMsg, String successStatusType)
	{
		long startTime = Calendar.getInstance().getTimeInMillis();
		List<Integer> styleIdList = checkoutTestsHelper.performSeachServiceToGetStyleIds(CheckoutServiceConstants.AWL_SEARCH_PROD, CheckoutServiceConstants.AWL_SEARCH_QTY, CheckoutServiceConstants.AWL_SEARCH_RET_DOCS, CheckoutServiceConstants.AWL_SEARCH_IS_FACET);
		
		if(!CollectionUtils.isEmpty(styleIdList))
		{
			for(Integer styleId : styleIdList)
			{
				System.out.println("\nPrinting the StyleId : "+styleId);
				log.info("\nPrinting the StyleId : "+styleId);
				
				RequestGenerator getStyleDataReqGen = checkoutTestsHelper.performGetStyleDataOperation(String.valueOf(styleId));
				System.out.println("\nPrinting StyleService getStyleData API response :\n\n"+getStyleDataReqGen.respvalidate.returnresponseasstring());
				log.info("\nPrinting StyleService API getStyleData response :\n\n"+getStyleDataReqGen.respvalidate.returnresponseasstring());
				
				AssertJUnit.assertTrue("Error while getting style data",
						getStyleDataReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(CheckoutServiceConstants.SUCCESS_STATUS_TYPE));
				
				String response = getStyleDataReqGen.respvalidate.returnresponseasstring();
				List<Integer> ids = JsonPath.read(response, "$.data..styleOptions[*].id");
				
				for(int i = 0 ; i < ids.size(); i++)
				{
					String skuId = JsonPath.read(response, "$.data..styleOptions["+i+"].skuId").toString();
					String inventoryCount = JsonPath.read(response, "$.data..styleOptions["+i+"].inventoryCount").toString();
					String available = JsonPath.read(response, "$.data..styleOptions["+i+"].available").toString();
					
					if(!inventoryCount.equals(CheckoutServiceConstants.EMPTY_VALUE) && available.equals(CheckoutServiceConstants.TRUE_VALUE))
					{
						RequestGenerator fetchWishlistReqGen = checkoutTestsHelper.operationFetchWishList(userName, password);
						System.out.println("\nPrinting CheckoutService operationFetchWishList API response :\n\n"+fetchWishlistReqGen.respvalidate.returnresponseasstring());
						log.info("\nPrinting CheckoutService operationFetchWishList API response :\n\n"+fetchWishlistReqGen.respvalidate.returnresponseasstring());
						
						if(StringUtils.isBlank(checkoutTestsHelper.getItemIdFromSKUId(fetchWishlistReqGen, skuId, CheckoutDataProviderEnum.WISHLIST.name())))
						{
							int invCount = Integer.parseInt(inventoryCount);
							int qty = Integer.parseInt(addItemQty);
							int itemQtyToAdd = invCount <= qty  ? invCount : qty;
							
							RequestGenerator addItemToWishListReqGen = checkoutTestsHelper.addItemToWishList(userName, password, null, String.valueOf(styleId), String.valueOf(itemQtyToAdd), addOperation);
							System.out.println("\nPrinting addItemToWishList API response :\n\n"+addItemToWishListReqGen.respvalidate.returnresponseasstring());
							log.info("\nPrinting addItemToWishList API response :\n\n"+addItemToWishListReqGen.respvalidate.returnresponseasstring());
							
							AssertJUnit.assertTrue("CheckoutService addItemToWishList API response status code is not a success status code", 
									addItemToWishListReqGen.respvalidate.NodeValue(StatusNodes.STATUS_CODE.toString(), true).contains(successStatusCode));	
							
							AssertJUnit.assertTrue("CheckoutService addItemToWishList API response status message is not a success status message",
									addItemToWishListReqGen.respvalidate.NodeValue(StatusNodes.STATUS_MESSAGE.toString(), true).contains(successStatusMsg));
							 
							AssertJUnit.assertTrue("CheckoutService addItemToWishList API response status type is not a success status type",
									addItemToWishListReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(successStatusType));
							
							System.out.println("\n"+skuId+" is available but not in wishlist so added to wishlist");
							log.info("\n"+skuId+" is available but not in wishlist so added to wishlist");
							break;
							
						} else {
							System.out.println("\n"+skuId + " is not added because this item already exists in wishlist");
							log.error("\n"+skuId + " is not added because this item already exists in wishlist");
						}
						
					} else {
						System.out.println("\n"+skuId+" is not added because this item is currently out of stock");
						log.info("\n"+skuId+" is not added because this item is currently out of stock");
					}
				}
			}
			
		} else {
			System.out.println("\nError while invoking SearchService API");
			log.error("\nError while invoking SearchService API");
			AssertJUnit.fail("Error while invoking SearchService API");
		}
		
		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime)/1000.0;
		
		System.out.println("\nTime taken to execute - TestCase - CheckoutServiceTests_addItemToWishListUsingStyleId_verifySuccessStatusResponse : "+timeTaken+" seconds\n");
		log.info("\nTime taken to execute - TestCase - CheckoutServiceTests_addItemToWishListUsingStyleId_verifySuccessStatusResponse : "+timeTaken+" seconds\n");
		
	}
	
	/**
	 *  This Test Case used to invoke CheckoutService updateItemInWishList API and verify for success response
	 * 
	 * @param userName
	 * @param password
	 * @param itemQtyToUpdate
	 * @param updateOpn
	 * @param successRespCode
	 */
	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression" }, 
			dataProvider="WishListServiceDP_updateItemInWishListDataProvider_validateAPIResponse", description="1. Hit IDP service & login as User."
					+ "\n2.Hit fetchWishlist API. \n3.If user's wishlist is empty then("
					+ "\na.Perform search and extract styleIds."
					+ "\nb.hit addItemToWishlist API giving styleID which doesnt exists in wishlist.)"
					+ "\n4.Hit updateItemInwishlist API giving itemID, skuID & the updateQuantity to update the quantity of item in wishlist."
					+ "\n5.Verify statusCode must be 200 in response.")
	public void WishListServiceTests_updateItemInWishList_verifySuccessResponse(String userName, String password, String itemQtyToUpdate, String updateOpn, 
			String successRespCode)
	{
		long startTime = Calendar.getInstance().getTimeInMillis();
		RequestGenerator fetchWishlistReqGen = checkoutTestsHelper.operationFetchWishList(userName, password);
		System.out.println("\nPrinting CheckoutService operationFetchWishList API response :\n\n"+fetchWishlistReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService operationFetchWishList API response :\n\n"+fetchWishlistReqGen.respvalidate.returnresponseasstring());
	
		AssertJUnit.assertTrue("Error while invoking CheckoutService operationFetchWishList API",
				fetchWishlistReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(CheckoutServiceConstants.SUCCESS_STATUS_TYPE));
		
		if(fetchWishlistReqGen.respvalidate.NodeValue(WishListNodes.WISHLIST_TOTAL_COUNT.toString(), true).equals(CheckoutServiceConstants.EMPTY_VALUE))
		{
			List<Integer> styleIdList = checkoutTestsHelper.performSeachServiceToGetStyleIds(CheckoutServiceConstants.UWL_SEARCH_PROD, CheckoutServiceConstants.UWL_SEARCH_QTY, CheckoutServiceConstants.UWL_SEARCH_RET_DOCS, CheckoutServiceConstants.UWL_SEARCH_IS_FACET);
			System.out.println("style id list :"+styleIdList);
			if(!CollectionUtils.isEmpty(styleIdList))
			{
				for(Integer styleId : styleIdList)
				{
					System.out.println("\nPrinting the StyleId : "+styleId);
					log.info("\nPrinting the StyleId : "+styleId);
					
					RequestGenerator getStyleDataReqGen = checkoutTestsHelper.performGetStyleDataOperation(String.valueOf(styleId));
					System.out.println("\nPrinting StyleService getStyleData API response :\n\n"+getStyleDataReqGen.respvalidate.returnresponseasstring());
					log.info("\nPrinting StyleService getStyleData API response :\n\n"+getStyleDataReqGen.respvalidate.returnresponseasstring());
					
					AssertJUnit.assertTrue("Error while getting style data",
							getStyleDataReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(CheckoutServiceConstants.SUCCESS_STATUS_TYPE));
					
					String response = getStyleDataReqGen.respvalidate.returnresponseasstring();
					List<Integer> ids = JsonPath.read(response, "$.data..styleOptions[*].id");
					
					for(int i = 0 ; i < ids.size(); i++)
					{
						String skuId = JsonPath.read(response, "$.data..styleOptions["+i+"].skuId").toString();
						String inventoryCount = JsonPath.read(response, "$.data..styleOptions["+i+"].inventoryCount").toString();
						String available = JsonPath.read(response, "$.data..styleOptions["+i+"].available").toString();
						
						if(!inventoryCount.equals(CheckoutServiceConstants.EMPTY_VALUE) && available.equals(CheckoutServiceConstants.TRUE_VALUE))
						{
							fetchWishlistReqGen = checkoutTestsHelper.operationFetchWishList(userName, password);
							System.out.println("\nPrinting CheckoutService operationFetchWishList API response :\n\n"+fetchWishlistReqGen.respvalidate.returnresponseasstring());
							log.info("\nPrinting CheckoutService operationFetchWishList API response :\n\n"+fetchWishlistReqGen.respvalidate.returnresponseasstring());
							
							AssertJUnit.assertTrue("Error while invoking CheckoutService operationFetchWishList API",
									fetchWishlistReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(CheckoutServiceConstants.SUCCESS_STATUS_TYPE));
							
							if(StringUtils.isBlank(checkoutTestsHelper.getItemIdFromSKUId(fetchWishlistReqGen, skuId, CheckoutDataProviderEnum.WISHLIST.name())))
							{
								RequestGenerator addItemToWishlistReqGen = checkoutTestsHelper.addItemToWishList(userName, password, skuId, null, 
										"1", CheckoutServiceConstants.ADD_OPERATION);
								System.out.println("\nPrinting CheckoutService addItemToCart API response :\n\n"+addItemToWishlistReqGen.respvalidate.returnresponseasstring());
								log.info("\nPrinting CheckoutService addItemToCart API response :\n\n"+addItemToWishlistReqGen.respvalidate.returnresponseasstring());
								
								AssertJUnit.assertTrue("Error while invoking CheckoutService addItemToWishList API",
										addItemToWishlistReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(CheckoutServiceConstants.SUCCESS_STATUS_TYPE));
								
								System.out.println("\n"+skuId+" is available but not in cart so added to cart");
								log.info("\n"+skuId+" is available but not in cart so added to cart");
								break;
								
							} else {
								System.out.println("\n"+skuId + " is not added because this item already exists in cart");
								log.error("\n"+skuId + " is not added because this item already exists in cart");
							}
							
						} else {
							System.out.println("\n"+skuId+" is not added because this item is currently out of stock");
							log.info("\n"+skuId+" is not added because this item is currently out of stock");
						}
					}
				}
				
			} else {
				System.out.println("\nError while invoking SearchService API");
				log.error("\nError while invoking SearchService API");
				AssertJUnit.fail("Error while invoking SearchService API");
			}
			
			fetchWishlistReqGen = checkoutTestsHelper.operationFetchWishList(userName, password);
			System.out.println("\nPrinting operationFetchWishList API response :\n\n"+fetchWishlistReqGen.respvalidate.returnresponseasstring());
			log.info("\nPrinting operationFetchWishList API response :\n\n"+fetchWishlistReqGen.respvalidate.returnresponseasstring());
		
			AssertJUnit.assertTrue("Error while invoking CheckoutService operationFetchWishList API",
					fetchWishlistReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(CheckoutServiceConstants.SUCCESS_STATUS_TYPE));
			
		}
			String skuId = fetchWishlistReqGen.respvalidate.NodeValue(WishListNodes.WISHLIST_ITEM_ENTRIES_AVAILSIZES_SKUID.toString(), false);
			String itemId = checkoutTestsHelper.getItemIdFromSKUId(fetchWishlistReqGen, skuId, CheckoutDataProviderEnum.WISHLIST.name());
			
			System.out.println("\nPrinting skuId : "+skuId+" & itemId : "+itemId+" & quantity : "+itemQtyToUpdate+" is going for updation\n");
			log.info("\nPrinting skuId : "+skuId+" & itemId : "+itemId+" & quantity : "+itemQtyToUpdate+" is going for updation\n");
			
			RequestGenerator updateItemInWishlistReqGen = checkoutTestsHelper.updateItemInWishList(userName, password, itemId, skuId, String.valueOf(itemQtyToUpdate), updateOpn);
			System.out.println("\nPrinting CheckoutService updateItemInWishList API response :\n\n"+updateItemInWishlistReqGen.respvalidate.returnresponseasstring());
			log.info("\nPrinting CheckoutService updateItemInWishList API response :\n\n"+updateItemInWishlistReqGen.respvalidate.returnresponseasstring());

			AssertJUnit.assertEquals("CheckoutService updateItemInWishList API is not working",
					Integer.parseInt(successRespCode), updateItemInWishlistReqGen.response.getStatus());
			
			long endTime = Calendar.getInstance().getTimeInMillis();
			double timeTaken = (endTime - startTime)/1000.0;
			
			System.out.println("\nTime taken to execute - TestCase - CheckoutServiceTests_updateItemInWishList_verifySuccessResponse : "+timeTaken+" seconds\n");
			log.info("\nTime taken to execute - TestCase - CheckoutServiceTests_updateItemInWishList_verifySuccessResponse : "+timeTaken+" seconds\n");
	}
	
	
	/**
	 * This Test Case used to invoke CheckoutService updateItemInWishList API and verify for success status response
	 * 
	 * @param userName
	 * @param password
	 * @param itemQtyToUpdate
	 * @param updateOpn
	 * @param successStatusCode
	 * @param successStatusMsg
	 * @param successStatusType
	 */
	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression" }, 
			dataProvider="WishListServiceDP_updateItemInWishListDataProvider_positiveCases", description="1. Hit IDP service & login as User."
					+ "\n2.Hit fetchWishlist API. \n3.If user's wishlist is empty then("
					+ "\na.Perform search and extract styleIds."
					+ "\nb.hit addItemToWishlist API giving styleID which doesnt exists in wishlist.)"
					+ "\n4.Hit updateItemInwishlist API giving itemID, skuID & the updateQuantity to update the quantity of item in wishlist."
					+ "\n5.Verify statusCode, statusMessage & statusType must be of success in APIs response.")
	public void WishListServiceTests_updateItemInWishList_verifySuccessStatusResponse(String userName, String password, String itemQtyToUpdate, String updateOpn, 
			String successStatusCode, String successStatusMsg, String successStatusType)
	{
		long startTime = Calendar.getInstance().getTimeInMillis();
		RequestGenerator fetchWishlistReqGen = checkoutTestsHelper.operationFetchWishList(userName, password);
		System.out.println("\nPrinting CheckoutService operationFetchWishList API response :\n\n"+fetchWishlistReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService operationFetchWishList API response :\n\n"+fetchWishlistReqGen.respvalidate.returnresponseasstring());
	
		AssertJUnit.assertTrue("Error while invoking CheckoutService operationFetchWishList API",
				fetchWishlistReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(CheckoutServiceConstants.SUCCESS_STATUS_TYPE));
		
		if(fetchWishlistReqGen.respvalidate.NodeValue(WishListNodes.WISHLIST_TOTAL_COUNT.toString(), true).equals(CheckoutServiceConstants.EMPTY_VALUE))
		{
			List<Integer> styleIdList = checkoutTestsHelper.performSeachServiceToGetStyleIds(CheckoutServiceConstants.UWL_SEARCH_PROD, CheckoutServiceConstants.UWL_SEARCH_QTY, CheckoutServiceConstants.UWL_SEARCH_RET_DOCS, CheckoutServiceConstants.UWL_SEARCH_IS_FACET);
			
			if(!CollectionUtils.isEmpty(styleIdList))
			{
				for(Integer styleId : styleIdList)
				{
					System.out.println("\nPrinting the StyleId : "+styleId);
					log.info("\nPrinting the StyleId : "+styleId);
					
					RequestGenerator getStyleDataReqGen = checkoutTestsHelper.performGetStyleDataOperation(String.valueOf(styleId));
					System.out.println("\nPrinting StyleService getStyleData API response :\n\n"+getStyleDataReqGen.respvalidate.returnresponseasstring());
					log.info("\nPrinting StyleService API getStyleData response :\n\n"+getStyleDataReqGen.respvalidate.returnresponseasstring());
					
					AssertJUnit.assertTrue("Error while getting style data",
							getStyleDataReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(CheckoutServiceConstants.SUCCESS_STATUS_TYPE));
					
					String response = getStyleDataReqGen.respvalidate.returnresponseasstring();
					List<Integer> ids = JsonPath.read(response, "$.data..styleOptions[*].id");
					
					for(int i = 0; i < ids.size(); i++)
					{
						String skuId = JsonPath.read(response, "$.data..styleOptions["+i+"].skuId").toString();
						String inventoryCount = JsonPath.read(response, "$.data..styleOptions["+i+"].inventoryCount").toString();
						String available = JsonPath.read(response, "$.data..styleOptions["+i+"].available").toString();
						
						if(!inventoryCount.equals(CheckoutServiceConstants.EMPTY_VALUE) && available.equals(CheckoutServiceConstants.TRUE_VALUE))
						{
							fetchWishlistReqGen = checkoutTestsHelper.operationFetchWishList(userName, password);
							System.out.println("\nPrinting CheckoutService operationFetchWishList API response :\n\n"+fetchWishlistReqGen.respvalidate.returnresponseasstring());
							log.info("\nPrinting CheckoutService operationFetchWishList API response :\n\n"+fetchWishlistReqGen.respvalidate.returnresponseasstring());
							
							AssertJUnit.assertTrue("Error while invoking CheckoutService operationFetchWishList API",
									fetchWishlistReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(CheckoutServiceConstants.SUCCESS_STATUS_TYPE));
							
							if(StringUtils.isBlank(checkoutTestsHelper.getItemIdFromSKUId(fetchWishlistReqGen, skuId, CheckoutDataProviderEnum.WISHLIST.name())))
							{
								RequestGenerator addItemToWishlistReqGen = checkoutTestsHelper.addItemToWishList(userName, password, skuId, null, "1",
										CheckoutServiceConstants.ADD_OPERATION);
								System.out.println("\nPrinting CheckoutService addItemToCart API response :\n\n"+addItemToWishlistReqGen.respvalidate.returnresponseasstring());
								log.info("\nPrinting CheckoutService addItemToCart API response :\n\n"+addItemToWishlistReqGen.respvalidate.returnresponseasstring());
								
								AssertJUnit.assertTrue("Error while invoking CheckoutService addItemToWishList API",
										addItemToWishlistReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(CheckoutServiceConstants.SUCCESS_STATUS_TYPE));
								
								System.out.println("\n"+skuId+" is available but not in cart so added to cart");
								log.info("\n"+skuId+" is available but not in cart so added to cart");
								break;
								
							} else {
								System.out.println("\n"+skuId + " is not added because this item already exists in cart");
								log.error("\n"+skuId + " is not added because this item already exists in cart");
							}
							
						} else {
							System.out.println("\n"+skuId+" is not added because this item is currently out of stock");
							log.info("\n"+skuId+" is not added because this item is currently out of stock");
						}
					}
				}
				
			} else {
				System.out.println("\nError while invoking SearchService API");
				log.error("\nError while invoking SearchService API");
				AssertJUnit.fail("Error while invoking SearchService API");
			}
			
			fetchWishlistReqGen = checkoutTestsHelper.operationFetchWishList(userName, password);
			System.out.println("\nPrinting CheckoutService operationFetchWishList API response :\n\n"+fetchWishlistReqGen.respvalidate.returnresponseasstring());
			log.info("\nPrinting CheckoutService operationFetchWishList API response :\n\n"+fetchWishlistReqGen.respvalidate.returnresponseasstring());
		
			AssertJUnit.assertTrue("Error while invoking CheckoutService operationFetchWishList API",
					fetchWishlistReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(CheckoutServiceConstants.SUCCESS_STATUS_TYPE));
			
		}
			String skuId = fetchWishlistReqGen.respvalidate.NodeValue(WishListNodes.WISHLIST_ITEM_ENTRIES_AVAILSIZES_SKUID.toString(), false);
			String itemId = checkoutTestsHelper.getItemIdFromSKUId(fetchWishlistReqGen, skuId, CheckoutDataProviderEnum.WISHLIST.name());
			
			System.out.println("\nPrinting skuId : "+skuId+" & itemId : "+itemId+" & quantity : "+itemQtyToUpdate+" is going for updation\n");
			log.info("\nPrinting skuId : "+skuId+" & itemId : "+itemId+" & quantity : "+itemQtyToUpdate+" is going for updation\n");
			
			RequestGenerator updateItemInWishlistReqGen = checkoutTestsHelper.updateItemInWishList(userName, password, itemId, skuId, String.valueOf(itemQtyToUpdate), updateOpn);
			System.out.println("\nPrinting CheckoutService updateItemInWishList API response :\n\n"+updateItemInWishlistReqGen.respvalidate.returnresponseasstring());
			log.info("\nPrinting CheckoutService updateItemInWishList API response :\n\n"+updateItemInWishlistReqGen.respvalidate.returnresponseasstring());

			AssertJUnit.assertTrue("CheckoutService updateItemInWishList API response status code is not a success status code", 
					updateItemInWishlistReqGen.respvalidate.NodeValue(StatusNodes.STATUS_CODE.toString(), true).contains(successStatusCode));	
			
			AssertJUnit.assertTrue("CheckoutService updateItemInWishList API response status message is not a success status message",
					updateItemInWishlistReqGen.respvalidate.NodeValue(StatusNodes.STATUS_MESSAGE.toString(), true).contains(successStatusMsg));
			 
			AssertJUnit.assertTrue("CheckoutService updateItemInWishList API response status type is not a success status type",
					updateItemInWishlistReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(successStatusType));
			
			long endTime = Calendar.getInstance().getTimeInMillis();
			double timeTaken = (endTime - startTime)/1000.0;
			
			System.out.println("\nTime taken to execute - TestCase - CheckoutServiceTests_updateItemInWishList_verifySuccessStatusResponse : "+timeTaken+" seconds\n");
			log.info("\nTime taken to execute - TestCase - CheckoutServiceTests_updateItemInWishList_verifySuccessStatusResponse : "+timeTaken+" seconds\n");
	}
	
	/**
	 * This Test Case used to invoke CheckoutService removeItemFromWishList API and verify for success response
	 * 
	 * @param userName
	 * @param password
	 * @param delOperation
	 * @param successRespCode
	 */
	@Test(groups = { "Sanity", "FoxSanity","Regression", "MiniRegression", "ExhaustiveRegression" }, 
			dataProvider="WishListServiceDP_removeItemFromWishListDataProvider_validateAPIResponse", description="1. Hit IDP service & login as User."
					+ "\n2.Hit fetchWishlist API. \n3.If user's wishlist is empty then("
					+ "\na.Perform search and extract styleIds."
					+ "\nb.hit addItemToWishlist API giving styleID which doesnt exists in wishlist.)"
					+ "\n4.Hit removeItemInwishlist API giving itemID to be removed from wishlist."
					+ "\n5.Verify statusCode must be 200 in response.")
	public void WishListServiceTests_removeItemFromWishList_verifySuccessResponse(String userName, String password, String delOperation, String successRespCode)
	{
		long startTime = Calendar.getInstance().getTimeInMillis();
		String addItemQty ="1";
		Boolean itemAdded = false;
		RequestGenerator fetchWishlistReqGen = checkoutTestsHelper.operationFetchWishList(userName, password);
		System.out.println("\nPrinting CheckoutService operationFetchWishList API response :\n\n"+fetchWishlistReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService operationFetchWishList API response :\n\n"+fetchWishlistReqGen.respvalidate.returnresponseasstring());
	
		AssertJUnit.assertTrue("Error while invoking CheckoutService operationFetchWishList API",
				fetchWishlistReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(CheckoutServiceConstants.SUCCESS_STATUS_TYPE));
		
		if(fetchWishlistReqGen.respvalidate.NodeValue(WishListNodes.WISHLIST_TOTAL_COUNT.toString(), true).equals(CheckoutServiceConstants.EMPTY_VALUE))
		{
			List<Integer> styleIdList = checkoutTestsHelper.performSeachServiceToGetStyleIds(CheckoutServiceConstants.RWL_SEARCH_PROD, CheckoutServiceConstants.RWL_SEARCH_QTY, CheckoutServiceConstants.RWL_SEARCH_RET_DOCS, CheckoutServiceConstants.RWL_SEARCH_IS_FACET);
			
			if(!CollectionUtils.isEmpty(styleIdList))
			{
				for(Integer styleId : styleIdList)
				{	if(itemAdded)
						break;
					System.out.println("\nPrinting the StyleId : "+styleId);
					log.info("\nPrinting the StyleId : "+styleId);
					
					RequestGenerator getStyleDataReqGen = checkoutTestsHelper.performGetStyleDataOperation(String.valueOf(styleId));
					System.out.println("\nPrinting StyleService getStyleData API response :\n\n"+getStyleDataReqGen.respvalidate.returnresponseasstring());
					log.info("\nPrinting StyleService getStyleData API response :\n\n"+getStyleDataReqGen.respvalidate.returnresponseasstring());
					
					AssertJUnit.assertTrue("Error while getting style data",
							getStyleDataReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(CheckoutServiceConstants.SUCCESS_STATUS_TYPE));
					
					String response = getStyleDataReqGen.respvalidate.returnresponseasstring();
					List<Integer> ids = JsonPath.read(response, "$.data..styleOptions[*].id");
					
					for(int i = 0 ; i < ids.size(); i++)
					{
						String skuId = JsonPath.read(response, "$.data..styleOptions["+i+"].skuId").toString();
						String inventoryCount = JsonPath.read(response, "$.data..styleOptions["+i+"].inventoryCount").toString();
						String available = JsonPath.read(response, "$.data..styleOptions["+i+"].available").toString();
						
						if(!inventoryCount.equals(CheckoutServiceConstants.EMPTY_VALUE) && available.equals(CheckoutServiceConstants.TRUE_VALUE))
						{
							fetchWishlistReqGen = checkoutTestsHelper.operationFetchWishList(userName, password);
							System.out.println("\nPrinting CheckoutService operationFetchWishList API response :\n\n"+fetchWishlistReqGen.respvalidate.returnresponseasstring());
							log.info("\nPrinting CheckoutService operationFetchWishList API response :\n\n"+fetchWishlistReqGen.respvalidate.returnresponseasstring());
							
							AssertJUnit.assertTrue("Error while invoking CheckoutService operationFetchWishList API",
									fetchWishlistReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(CheckoutServiceConstants.SUCCESS_STATUS_TYPE));
							
							if(StringUtils.isBlank(checkoutTestsHelper.getItemIdFromSKUId(fetchWishlistReqGen, skuId, CheckoutDataProviderEnum.WISHLIST.name())))
							{
								RequestGenerator addItemToWishlistReqGen = checkoutTestsHelper.addItemToWishList(userName, password, skuId, null,String.valueOf(addItemQty), CheckoutServiceConstants.ADD_OPERATION);
								System.out.println("\nPrinting CheckoutService addItemToWishlist API response :\n\n"+addItemToWishlistReqGen.respvalidate.returnresponseasstring());
								log.info("\nPrinting CheckoutService addItemToCart API response :\n\n"+addItemToWishlistReqGen.respvalidate.returnresponseasstring());
								
								AssertJUnit.assertTrue("Error while invoking CheckoutService addItemToWishList API",
										addItemToWishlistReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(CheckoutServiceConstants.SUCCESS_STATUS_TYPE));
								itemAdded = true;
								System.out.println("\n"+skuId+" is available but not in cart so added to cart");
								log.info("\n"+skuId+" is available but not in cart so added to cart");
								break;
								
							} else {
								System.out.println("\n"+skuId + " is not added because this item already exists in cart");
								log.error("\n"+skuId + " is not added because this item already exists in cart");
							}
							
						} else {
							System.out.println("\n"+skuId+" is not added because this item is currently out of stock");
							log.info("\n"+skuId+" is not added because this item is currently out of stock");
						}
					}
				}
				
			} else {
				System.out.println("\nError while invoking SearchService API");
				log.error("\nError while invoking SearchService API");
				AssertJUnit.fail("Error while invoking SearchService API");
			}
			
			fetchWishlistReqGen = checkoutTestsHelper.operationFetchWishList(userName, password);
			System.out.println("\nPrinting CheckoutService operationFetchWishList API response :\n\n"+fetchWishlistReqGen.respvalidate.returnresponseasstring());
			log.info("\nPrinting CheckoutService operationFetchWishList API response :\n\n"+fetchWishlistReqGen.respvalidate.returnresponseasstring());
		
			AssertJUnit.assertTrue("Error while invoking CheckoutService operationFetchWishList API",
					fetchWishlistReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(CheckoutServiceConstants.SUCCESS_STATUS_TYPE));
		}
			String skuId = fetchWishlistReqGen.respvalidate.NodeValue(WishListNodes.WISHLIST_ITEM_ENTRIES_AVAILSIZES_SKUID.toString(), false);
			String itemId = checkoutTestsHelper.getItemIdFromSKUId(fetchWishlistReqGen, skuId, CheckoutDataProviderEnum.WISHLIST.name());
			
			System.out.println("\nPrinting skuId : "+skuId+" & itemId : "+itemId+" is going to remove item from wishlist\n");
			log.info("\nPrinting skuId : "+skuId+" & itemId : "+itemId+" is going to remove item from wishlist\n");
			
			RequestGenerator removeItemFromWishlist = checkoutTestsHelper.removeItemFromWishList(userName, password, itemId, delOperation);
			System.out.println("\nPrinting CheckoutService removeItemFromWishList API response :\n\n"+removeItemFromWishlist.respvalidate.returnresponseasstring());
			log.info("\nPrinting CheckoutService removeItemFromWishList API response :\n\n"+removeItemFromWishlist.respvalidate.returnresponseasstring());

			AssertJUnit.assertEquals("CheckoutService removeItemFromWishList API is not working",
					Integer.parseInt(successRespCode), removeItemFromWishlist.response.getStatus());
			
			long endTime = Calendar.getInstance().getTimeInMillis();
			double timeTaken = (endTime - startTime)/1000.0;
			
			System.out.println("\nTime taken to execute - TestCase - CheckoutServiceTests_removeItemFromWishList_verifySuccessResponse : "+timeTaken+" seconds\n");
			log.info("\nTime taken to execute - TestCase - CheckoutServiceTests_removeItemFromWishList_verifySuccessResponse : "+timeTaken+" seconds\n");
	}
	
	/**
	 * This Test Case used to invoke CheckoutService removeItemFromWishList API and verify for success status response
	 * 
	 * @param userName
	 * @param password
	 * @param delOperation
	 * @param successStatusCode
	 * @param successStatusMsg
	 * @param successStatusType
	 */
	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression" }, 
			dataProvider="WishListServiceDP_removeItemFromWishListDataProvider_positiveCases", description="1. Hit IDP service & login as User."
					+ "\n2.Hit fetchWishlist API. \n3.If user's wishlist is empty then("
					+ "\na.Perform search and extract styleIds."
					+ "\nb.hit addItemToWishlist API giving styleID which doesnt exists in wishlist.)"
					+ "\n4.Hit removeItemInwishlist API giving itemID to be removed from wishlist."
					+ "\n5.Verify statusCode, statusMessage & statusType must be of success.")
	public void WishListServiceTests_removeItemFromWishList_verifySuccessStatusResponse(String userName, String password, String delOperation, String successStatusCode, 
			String successStatusMsg, String successStatusType)
	{
		long startTime = Calendar.getInstance().getTimeInMillis();
		String addItemQty ="1";
		Boolean itemAdded = false;
		RequestGenerator fetchWishlistReqGen = checkoutTestsHelper.operationFetchWishList(userName, password);
		System.out.println("\nPrinting CheckoutService operationFetchWishList API response :\n\n"+fetchWishlistReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService operationFetchWishList API response :\n\n"+fetchWishlistReqGen.respvalidate.returnresponseasstring());
	
		AssertJUnit.assertTrue("Error while invoking CheckoutService operationFetchWishList API",
				fetchWishlistReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(CheckoutServiceConstants.SUCCESS_STATUS_TYPE));
		
		if(fetchWishlistReqGen.respvalidate.NodeValue(WishListNodes.WISHLIST_TOTAL_COUNT.toString(), true).equals(CheckoutServiceConstants.EMPTY_VALUE))
		{
			List<Integer> styleIdList = checkoutTestsHelper.performSeachServiceToGetStyleIds(CheckoutServiceConstants.RWL_SEARCH_PROD, CheckoutServiceConstants.RWL_SEARCH_QTY, CheckoutServiceConstants.RWL_SEARCH_RET_DOCS, CheckoutServiceConstants.RWL_SEARCH_IS_FACET);
			
			if(!CollectionUtils.isEmpty(styleIdList))
			{
				for(Integer styleId : styleIdList)
				{	if(itemAdded)
						break;
					System.out.println("\nPrinting the StyleId : "+styleId);
					log.info("\nPrinting the StyleId : "+styleId);
					
					RequestGenerator getStyleDataReqGen = checkoutTestsHelper.performGetStyleDataOperation(String.valueOf(styleId));
					System.out.println("\nPrinting StyleService getStyleData API response :\n\n"+getStyleDataReqGen.respvalidate.returnresponseasstring());
					log.info("\nPrinting StyleService getStyleData API response :\n\n"+getStyleDataReqGen.respvalidate.returnresponseasstring());
					
					AssertJUnit.assertTrue("Error while getting style data",
							getStyleDataReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(CheckoutServiceConstants.SUCCESS_STATUS_TYPE));
					
					String response = getStyleDataReqGen.respvalidate.returnresponseasstring();
					List<Integer> ids = JsonPath.read(response, "$.data..styleOptions[*].id");
					
					for(int i = 0 ; i < ids.size(); i++)
					{
						String skuId = JsonPath.read(response, "$.data..styleOptions["+i+"].skuId").toString();
						String inventoryCount = JsonPath.read(response, "$.data..styleOptions["+i+"].inventoryCount").toString();
						String available = JsonPath.read(response, "$.data..styleOptions["+i+"].available").toString();
						
						if(!inventoryCount.equals(CheckoutServiceConstants.EMPTY_VALUE) && available.equals(CheckoutServiceConstants.TRUE_VALUE))
						{
							fetchWishlistReqGen = checkoutTestsHelper.operationFetchWishList(userName, password);
							System.out.println("\nPrinting CheckoutService operationFetchWishList API response :\n\n"+fetchWishlistReqGen.respvalidate.returnresponseasstring());
							log.info("\nPrinting CheckoutService operationFetchWishList API response :\n\n"+fetchWishlistReqGen.respvalidate.returnresponseasstring());
							
							AssertJUnit.assertTrue("Error while invoking CheckoutService operationFetchWishList API",
									fetchWishlistReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(CheckoutServiceConstants.SUCCESS_STATUS_TYPE));
							
							if(StringUtils.isBlank(checkoutTestsHelper.getItemIdFromSKUId(fetchWishlistReqGen, skuId, CheckoutDataProviderEnum.WISHLIST.name())))
							{
								RequestGenerator addItemToWishlistReqGen = checkoutTestsHelper.addItemToWishList(userName, password, skuId, null, String.valueOf(addItemQty), CheckoutServiceConstants.ADD_OPERATION);
								System.out.println("\nPrinting CheckoutService addItemToCart API response :\n\n"+addItemToWishlistReqGen.respvalidate.returnresponseasstring());
								log.info("\nPrinting CheckoutService addItemToCart API response :\n\n"+addItemToWishlistReqGen.respvalidate.returnresponseasstring());
								
								AssertJUnit.assertTrue("Error while invoking CheckoutService addItemToWishList API",
										addItemToWishlistReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(CheckoutServiceConstants.SUCCESS_STATUS_TYPE));
								itemAdded = true;
								System.out.println("\n"+skuId+" is available but not in cart so added to cart");
								log.info("\n"+skuId+" is available but not in cart so added to cart");
								break;
								
							} else {
								System.out.println("\n"+skuId + " is not added because this item already exists in cart");
								log.error("\n"+skuId + " is not added because this item already exists in cart");
							}
							
						} else {
							System.out.println("\n"+skuId+" is not added because this item is currently out of stock");
							log.info("\n"+skuId+" is not added because this item is currently out of stock");
						}
					}
				}
				
			} else {
				System.out.println("\nError while invoking SearchService API");
				log.error("\nError while invoking SearchService API");
				AssertJUnit.fail("Error while invoking SearchService API");
			}
			
			fetchWishlistReqGen = checkoutTestsHelper.operationFetchWishList(userName, password);
			System.out.println("\nPrinting CheckoutService operationFetchWishList API response :\n\n"+fetchWishlistReqGen.respvalidate.returnresponseasstring());
			log.info("\nPrinting CheckoutService operationFetchWishList API response :\n\n"+fetchWishlistReqGen.respvalidate.returnresponseasstring());
		
			AssertJUnit.assertTrue("Error while invoking CheckoutService operationFetchWishList API",
					fetchWishlistReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(CheckoutServiceConstants.SUCCESS_STATUS_TYPE));
		}
			String skuId = fetchWishlistReqGen.respvalidate.NodeValue(WishListNodes.WISHLIST_ITEM_ENTRIES_AVAILSIZES_SKUID.toString(), false);
			String itemId = checkoutTestsHelper.getItemIdFromSKUId(fetchWishlistReqGen, skuId, CheckoutDataProviderEnum.WISHLIST.name());
			
			System.out.println("\nPrinting skuId : "+skuId+" & itemId : "+itemId+" is going to remove item from wishlist\n");
			log.info("\nPrinting skuId : "+skuId+" & itemId : "+itemId+" is going to remove item from wishlist\n");
			
			RequestGenerator removeItemFromWishlist = checkoutTestsHelper.removeItemFromWishList(userName, password, itemId, delOperation);
			System.out.println("\nPrinting CheckoutService removeItemFromWishList API response :\n\n"+removeItemFromWishlist.respvalidate.returnresponseasstring());
			log.info("\nPrinting CheckoutService removeItemFromWishList API response :\n\n"+removeItemFromWishlist.respvalidate.returnresponseasstring());

			AssertJUnit.assertTrue("CheckoutService removeItemFromWishList API response status code is not a success status code", 
					removeItemFromWishlist.respvalidate.NodeValue(StatusNodes.STATUS_CODE.toString(), true).contains(successStatusCode));	
			
			AssertJUnit.assertTrue("CheckoutService removeItemFromWishList API response status message is not a success status message",
					removeItemFromWishlist.respvalidate.NodeValue(StatusNodes.STATUS_MESSAGE.toString(), true).contains(successStatusMsg));
			 
			AssertJUnit.assertTrue("CheckoutService removeItemFromWishList API response status type is not a success status type",
					removeItemFromWishlist.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(successStatusType));
			
			long endTime = Calendar.getInstance().getTimeInMillis();
			double timeTaken = (endTime - startTime)/1000.0;
			
			System.out.println("\nTime taken to execute - TestCase - CheckoutServiceTests_removeItemFromWishList_verifySuccessStatusResponse : "+timeTaken+" seconds\n");
			log.info("\nTime taken to execute - TestCase - CheckoutServiceTests_removeItemFromWishList_verifySuccessStatusResponse : "+timeTaken+" seconds\n");
	}
	
	/**
	 *  This Test Case used to invoke CheckoutService moveItemToCartFromWishlist API and verify for success response
	 * 
	 * @param user
	 * @param pass
	 * @param skuId
	 * @param EMPTY_VALUE.name()))
	 * @param respCode
	 */
	@Test(groups = { "Sanity","FoxSanity", "Regression", "MiniRegression", "ExhaustiveRegression" }, 
			dataProvider="WishListServiceDP_moveItemToCartDataProvider_validateAPIResponse",description ="1. hit IDP service and login the user. "
					+ "\n 2. hit fetch wishlist API. \n 3. if user's wishlist is empty then first add items to Wishlist(\n a.hit Search Service API and extract some skuIDs"
					+ "\n b. C. hit addItemToWishlist API giving skuId which doesnt exists in wishlist)"
					+ "\n 4. hit moveItemToCart API"
					+ "\n 5.Check the service response must be 200")
	public void WishListServiceTests_moveItemToCart_verifySuccessResponse(String userName, String password, String successRespCode)
	{
		long startTime = Calendar.getInstance().getTimeInMillis();
		String addItemQty = "1";
		Boolean itemAdded = false;
		RequestGenerator fetchWishlistReqGen = checkoutTestsHelper.operationFetchWishList(userName, password);
		System.out.println("\nPrinting CheckoutService operationFetchWishList API response :\n\n"+fetchWishlistReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService operationFetchWishList API response :\n\n"+fetchWishlistReqGen.respvalidate.returnresponseasstring());
	
		AssertJUnit.assertTrue("Error while invoking CheckoutService operationFetchWishList API",
				fetchWishlistReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(CheckoutServiceConstants.SUCCESS_STATUS_TYPE));
		
		if(fetchWishlistReqGen.respvalidate.NodeValue(WishListNodes.WISHLIST_TOTAL_COUNT.toString(), true).equals(CheckoutServiceConstants.EMPTY_VALUE))
		{
			List<Integer> styleIdList = checkoutTestsHelper.performSeachServiceToGetStyleIds(CheckoutServiceConstants.MC_SEARCH_PROD, CheckoutServiceConstants.MC_SEARCH_QTY, CheckoutServiceConstants.MC_SEARCH_RET_DOCS, CheckoutServiceConstants.MC_SEARCH_IS_FACET);
			
			if(!CollectionUtils.isEmpty(styleIdList))
			{
				for(Integer styleId : styleIdList)
				{	
					if(itemAdded)
						break;
					System.out.println("\nPrinting the StyleId : "+styleId);
					log.info("\nPrinting the StyleId : "+styleId);
					
					RequestGenerator getStyleDataReqGen = checkoutTestsHelper.performGetStyleDataOperation(String.valueOf(styleId));
					System.out.println("\nPrinting StyleService getStyleData API response :\n\n"+getStyleDataReqGen.respvalidate.returnresponseasstring());
					log.info("\nPrinting StyleService getStyleData API response :\n\n"+getStyleDataReqGen.respvalidate.returnresponseasstring());
					
					AssertJUnit.assertTrue("Error while getting style data",
							getStyleDataReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(CheckoutServiceConstants.SUCCESS_STATUS_TYPE));
					
					String response = getStyleDataReqGen.respvalidate.returnresponseasstring();
					List<Integer> ids = JsonPath.read(response, "$.data..styleOptions[*].id");
					
					for(int i = 0 ; i < ids.size(); i++)
					{
						String skuId = JsonPath.read(response, "$.data..styleOptions["+i+"].skuId").toString();
						String inventoryCount = JsonPath.read(response, "$.data..styleOptions["+i+"].inventoryCount").toString();
						String available = JsonPath.read(response, "$.data..styleOptions["+i+"].available").toString();
						
						if(!inventoryCount.equals(CheckoutServiceConstants.EMPTY_VALUE) && available.equals(CheckoutServiceConstants.TRUE_VALUE))
						{
							RequestGenerator addItemToWishlistReqGen = checkoutTestsHelper.addItemToWishList(userName, password, skuId, null, String.valueOf(addItemQty), CheckoutServiceConstants.ADD_OPERATION);
							System.out.println("\nPrinting CheckoutService addItemToWishList API response :\n\n"+addItemToWishlistReqGen.respvalidate.returnresponseasstring());
							log.info("\nPrinting CheckoutService addItemToWishList API response :\n\n"+addItemToWishlistReqGen.respvalidate.returnresponseasstring());
								
							AssertJUnit.assertTrue("Error while invoking CheckoutService addItemToWishList API",
									addItemToWishlistReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(CheckoutServiceConstants.SUCCESS_STATUS_TYPE));
							itemAdded = true;
							System.out.println("\n"+skuId+" is available but not in wishlist so added to wishlist");
							log.info("\n"+skuId+" is available but not in wishlist so added to wishlist");
							break;
							
						} else {
							System.out.println("\n"+skuId+" is not added because this item is currently out of stock");
							log.info("\n"+skuId+" is not added because this item is currently out of stock");
						}
					}
				}
				
			} else {
				System.out.println("\nError while invoking SearchService API");
				log.error("\nError while invoking SearchService API");
				AssertJUnit.fail("Error while invoking SearchService API");
			}
			
			fetchWishlistReqGen = checkoutTestsHelper.operationFetchWishList(userName, password);
			System.out.println("\nPrinting CheckoutService operationFetchWishList API response :\n\n"+fetchWishlistReqGen.respvalidate.returnresponseasstring());
			log.info("\nPrinting CheckoutService operationFetchWishList API response :\n\n"+fetchWishlistReqGen.respvalidate.returnresponseasstring());
		
			AssertJUnit.assertTrue("Error while invoking CheckoutService operationFetchWishList API",
					fetchWishlistReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(CheckoutServiceConstants.SUCCESS_STATUS_TYPE));
		}

		String skuId = fetchWishlistReqGen.respvalidate.NodeValue(WishListNodes.WISHLIST_ITEM_ENTRIES_SELSIZE_SKUID.toString(), false);
		String itemId = checkoutTestsHelper.getItemIdFromSKUId(fetchWishlistReqGen, skuId, CheckoutDataProviderEnum.WISHLIST.name());
		
		RequestGenerator fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
		System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"+fetchCartReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"+fetchCartReqGen.respvalidate.returnresponseasstring());
		
		AssertJUnit.assertTrue("Error while invoking CheckoutService operationFetchCart API",
				fetchCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(CheckoutServiceConstants.SUCCESS_STATUS_TYPE));
		
		if(StringUtils.isBlank(checkoutTestsHelper.getItemIdFromSKUId(fetchCartReqGen, skuId, CheckoutDataProviderEnum.CART.name())))
		{
			System.out.println("\nPrinting skuId : "+skuId+" & itemId : "+itemId+" is moving to cart from wishlist\n");
			log.info("\nPrinting skuId : "+skuId+" & itemId : "+itemId+" is going is moving to cart from wishlist\n");
			
			RequestGenerator moveItemToCartReqGen = checkoutTestsHelper.moveItemToCart(userName, password, itemId);
			System.out.println("\nPrinting CheckoutService moveItemToCart API response :\n\n"+moveItemToCartReqGen.respvalidate.returnresponseasstring());
			log.info("\nPrinting CheckoutService moveItemToCart API response :\n\n"+moveItemToCartReqGen.respvalidate.returnresponseasstring());

			AssertJUnit.assertEquals("CheckoutService moveItemToCart API is not working", 
						Integer.parseInt(successRespCode), moveItemToCartReqGen.response.getStatus());
		} else {
			System.out.println("\nPrinting skuId : "+skuId+" & itemId : "+itemId+" is already present in cart\n");
			log.info("\nPrinting skuId : "+skuId+" & itemId : "+itemId+" is already present in cart\n");
			
		}
			
		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime)/1000.0;
		
		System.out.println("\nTime taken to execute - TestCase - CheckoutServiceTests_moveItemToCart_verifySuccessResponse : "+timeTaken+" seconds\n");
		log.info("\nTime taken to execute - TestCase - CheckoutServiceTests_moveItemToCart_verifySuccessResponse : "+timeTaken+" seconds\n");

	}
	
	
	/**
	 * This Test Case used to invoke CheckoutService moveItemToCartFromWishlist API and verify for success status response
	 * 
	 * @param userName
	 * @param password
	 * @param successStatusCode
	 * @param successStatusMsg
	 * @param successStatusType
	 */
	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression" }, 
			dataProvider="WishListServiceDP_moveItemToCartDataProvider_positiveCases",description ="1. hit IDP service and login the user. "
					+ "\n 2. hit fetch wishlist API. \n 3. if user's wishlist is empty then first add items to Wishlist(\n a.hit Search Service API and extract some skuIDs"
					+ "\n b. C. hit addItemToWishlist API giving skuId which doesnt exists in wishlist)"
					+ "\n 4. hit moveItemToCart API"
					+ "\n 5. Verify statusCode, statusMessage & statusType must be of success")
	public void WishListServiceTests_moveItemToCart_verifySuccessStatusResponse(String userName, String password, String successStatusCode, String successStatusMsg, 
			String successStatusType)
	{
		long startTime = Calendar.getInstance().getTimeInMillis();
		String addItemQty = "1";
		Boolean itemAdded = false;
		RequestGenerator fetchWishlistReqGen = checkoutTestsHelper.operationFetchWishList(userName, password);
		System.out.println("\nPrinting CheckoutService operationFetchWishList API response :\n\n"+fetchWishlistReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService operationFetchWishList API response :\n\n"+fetchWishlistReqGen.respvalidate.returnresponseasstring());
	
		AssertJUnit.assertTrue("Error while invoking CheckoutService operationFetchWishList API",
				fetchWishlistReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(CheckoutServiceConstants.SUCCESS_STATUS_TYPE));
		
		if(fetchWishlistReqGen.respvalidate.NodeValue(WishListNodes.WISHLIST_TOTAL_COUNT.toString(), true).equals(CheckoutServiceConstants.EMPTY_VALUE))
		{
			List<Integer> styleIdList = checkoutTestsHelper.performSeachServiceToGetStyleIds(CheckoutServiceConstants.MC_SEARCH_PROD, CheckoutServiceConstants.MC_SEARCH_QTY, CheckoutServiceConstants.MC_SEARCH_RET_DOCS, CheckoutServiceConstants.MC_SEARCH_IS_FACET);
			
			if(!CollectionUtils.isEmpty(styleIdList))
			{
				for(Integer styleId : styleIdList)
				{	
					if(itemAdded)
						break;
					System.out.println("\nPrinting the StyleId : "+styleId);
					log.info("\nPrinting the StyleId : "+styleId);
					
					RequestGenerator getStyleDataReqGen = checkoutTestsHelper.performGetStyleDataOperation(String.valueOf(styleId));
					System.out.println("\nPrinting StyleService getStyleData API response :\n\n"+getStyleDataReqGen.respvalidate.returnresponseasstring());
					log.info("\nPrinting StyleService getStyleData API response :\n\n"+getStyleDataReqGen.respvalidate.returnresponseasstring());
					
					AssertJUnit.assertTrue("Error while getting style data",
							getStyleDataReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(CheckoutServiceConstants.SUCCESS_STATUS_TYPE));
					
					String response = getStyleDataReqGen.respvalidate.returnresponseasstring();
					List<Integer> ids = JsonPath.read(response, "$.data..styleOptions[*].id");
					
					for(int i = 0; i < ids.size(); i++)
					{
						String skuId = JsonPath.read(response, "$.data..styleOptions["+i+"].skuId").toString();
						String inventoryCount = JsonPath.read(response, "$.data..styleOptions["+i+"].inventoryCount").toString();
						String available = JsonPath.read(response, "$.data..styleOptions["+i+"].available").toString();
						
						if(!inventoryCount.equals(CheckoutServiceConstants.EMPTY_VALUE) && available.equals(CheckoutServiceConstants.TRUE_VALUE))
						{
							RequestGenerator addItemToWishlistReqGen = checkoutTestsHelper.addItemToWishList(userName, password, skuId, null, String.valueOf(addItemQty), CheckoutServiceConstants.ADD_OPERATION);
								System.out.println("\nPrinting CheckoutService addItemToWishList API response :\n\n"+addItemToWishlistReqGen.respvalidate.returnresponseasstring());
								log.info("\nPrinting CheckoutService addItemToWishList API response :\n\n"+addItemToWishlistReqGen.respvalidate.returnresponseasstring());
								
								AssertJUnit.assertTrue("Error while invoking CheckoutService addItemToWishList API",
										addItemToWishlistReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(CheckoutServiceConstants.SUCCESS_STATUS_TYPE));
								itemAdded = true;
								System.out.println("\n"+skuId+" is available but not in wishlist so added to wishlist");
								log.info("\n"+skuId+" is available but not in wishlist so added to wishlist");
								break;
								
							
							
						} else {
							System.out.println("\n"+skuId+" is not added because this item is currently out of stock");
							log.info("\n"+skuId+" is not added because this item is currently out of stock");
						}
					}
					
				}
				
			} else {
				System.out.println("\nError while invoking SearchService API");
				log.error("\nError while invoking SearchService API");
				AssertJUnit.fail("Error while invoking SearchService API");
			}
			
			fetchWishlistReqGen = checkoutTestsHelper.operationFetchWishList(userName, password);
			System.out.println("\nPrinting CheckoutService operationFetchWishList API response :\n\n"+fetchWishlistReqGen.respvalidate.returnresponseasstring());
			log.info("\nPrinting CheckoutService operationFetchWishList API response :\n\n"+fetchWishlistReqGen.respvalidate.returnresponseasstring());
		
			AssertJUnit.assertTrue("Error while invoking CheckoutService operationFetchWishList API",
					fetchWishlistReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(CheckoutServiceConstants.SUCCESS_STATUS_TYPE));
		}

		String skuId = fetchWishlistReqGen.respvalidate.NodeValue(WishListNodes.WISHLIST_ITEM_ENTRIES_SELSIZE_SKUID.toString(), false);
		String itemId = checkoutTestsHelper.getItemIdFromSKUId(fetchWishlistReqGen, skuId, CheckoutDataProviderEnum.WISHLIST.name());
		
		RequestGenerator fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
		System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"+fetchCartReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"+fetchCartReqGen.respvalidate.returnresponseasstring());
		
		AssertJUnit.assertTrue("Error while invoking CheckoutService operationFetchCart API",
				fetchCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(CheckoutServiceConstants.SUCCESS_STATUS_TYPE));
		
		if(StringUtils.isBlank(checkoutTestsHelper.getItemIdFromSKUId(fetchCartReqGen, skuId, CheckoutDataProviderEnum.CART.name())))
		{
			System.out.println("\nPrinting skuId : "+skuId+" & itemId : "+itemId+" is moving to cart from wishlist\n");
			log.info("\nPrinting skuId : "+skuId+" & itemId : "+itemId+" is going is moving to cart from wishlist\n");
			
			RequestGenerator moveItemToCartReqGen = checkoutTestsHelper.moveItemToCart(userName, password, itemId);
			System.out.println("\nPrinting CheckoutService moveItemToCart API response :\n\n"+moveItemToCartReqGen.respvalidate.returnresponseasstring());
			log.info("\nPrinting CheckoutService moveItemToCart API response :\n\n"+moveItemToCartReqGen.respvalidate.returnresponseasstring());

			AssertJUnit.assertTrue("CheckoutService moveItemToCart API response status code is not a success status code", 
					moveItemToCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_CODE.toString(), true).contains(successStatusCode));	
			
			AssertJUnit.assertTrue("CheckoutService moveItemToCart API response status message is not a success status message",
					moveItemToCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_MESSAGE.toString(), true).contains(successStatusMsg));
			 
			AssertJUnit.assertTrue("CheckoutService moveItemToCart API response status type is not a success status type",
					moveItemToCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(successStatusType));
			
		} else {
			System.out.println("\nPrinting skuId : "+skuId+" & itemId : "+itemId+" is already present in cart\n");
			log.info("\nPrinting skuId : "+skuId+" & itemId : "+itemId+" is already present in cart\n");
		}
			
		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime)/1000.0;
		
		System.out.println("\nTime taken to execute - TestCase - CheckoutServiceTests_moveItemToCart_verifySuccessStatusResponse : "+timeTaken+" seconds\n");
		log.info("\nTime taken to execute - TestCase - CheckoutServiceTests_moveItemToCart_verifySuccessStatusResponse : "+timeTaken+" seconds\n");
	}
	
	
	@Test(groups = { "SchemaValidation", "CartAPIsSchemaValidation"  }, dataProvider="WishListServiceDP_moveToWishListDataProvider_verifyResponseDataNodesUsingSchemaValidations")
	public void WishListServiceTests_moveItemToWishList_verifyResponseDataNodesUsingSchemaValidations(String userName, String password,  String successRespCode)
	{
		RequestGenerator fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
		System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"+fetchCartReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"+fetchCartReqGen.respvalidate.returnresponseasstring());
	
		AssertJUnit.assertTrue("Error while invoking operationFetchCart API",
				fetchCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(CheckoutServiceConstants.SUCCESS_STATUS_TYPE));
		
		if(fetchCartReqGen.respvalidate.NodeValue(CartNodes.CART_TOTAL_COUNT.toString(), true).equals(CheckoutServiceConstants.EMPTY_VALUE))
		{
			List<Integer> styleIdList = checkoutTestsHelper.performSeachServiceToGetStyleIds(CheckoutServiceConstants.MWL_SEARCH_PROD, CheckoutServiceConstants.MWL_SEARCH_QTY, CheckoutServiceConstants.MWL_SEARCH_RET_DOCS, CheckoutServiceConstants.MWL_SEARCH_IS_FACET);
			
			if(!CollectionUtils.isEmpty(styleIdList))
			{
				for(Integer styleId : styleIdList)
				{
					System.out.println("\nPrinting the StyleId : "+styleId);
					log.info("\nPrinting the StyleId : "+styleId);
					
					RequestGenerator getStyleDataReqGen = checkoutTestsHelper.performGetStyleDataOperation(String.valueOf(styleId));
					System.out.println("\nPrinting StyleService getStyleData API response :\n\n"+getStyleDataReqGen.respvalidate.returnresponseasstring());
					log.info("\nPrinting StyleService getStyleDataAPI response :\n\n"+getStyleDataReqGen.respvalidate.returnresponseasstring());
					
					AssertJUnit.assertTrue("Error while getting style data",
							getStyleDataReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(CheckoutServiceConstants.SUCCESS_STATUS_TYPE));
					
					String response = getStyleDataReqGen.respvalidate.returnresponseasstring();
					List<Integer> ids = JsonPath.read(response, "$.data..styleOptions[*].id");
					
					for(int i = 0 ; i < ids.size(); i++)
					{
						String skuId = JsonPath.read(response, "$.data..styleOptions["+i+"].skuId").toString();
						String inventoryCount = JsonPath.read(response, "$.data..styleOptions["+i+"].inventoryCount").toString();
						String available = JsonPath.read(response, "$.data..styleOptions["+i+"].available").toString();
						
						if(!inventoryCount.equals(CheckoutServiceConstants.EMPTY_VALUE) && available.equals(CheckoutServiceConstants.TRUE_VALUE))
						{
							fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
							System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"+fetchCartReqGen.respvalidate.returnresponseasstring());
							log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"+fetchCartReqGen.respvalidate.returnresponseasstring());
							
							if(StringUtils.isBlank(checkoutTestsHelper.getItemIdFromSKUId(fetchCartReqGen, skuId, CheckoutDataProviderEnum.CART.name())))
							{
								RequestGenerator addItemToCartReqGen = checkoutTestsHelper.addItemToCart(userName, password, skuId, inventoryCount, CheckoutServiceConstants.ADD_OPERATION);
								System.out.println("\nPrinting addItemToCart API response :\n\n"+addItemToCartReqGen.respvalidate.returnresponseasstring());
								log.info("\nPrinting addItemToCart API response :\n\n"+addItemToCartReqGen.respvalidate.returnresponseasstring());
								
								AssertJUnit.assertTrue("Error while invoking CheckoutService addItemToCart API",
										addItemToCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(CheckoutServiceConstants.SUCCESS_STATUS_TYPE));
								
								System.out.println("\n"+skuId+" is available and it doesn't exists in cart so added to cart");
								log.info("\n"+skuId+" is available and it doesn't exists in cart so added to cart");
								break;
								
							} else {
								System.out.println("\n"+skuId + " is not added because this item already exists in cart");
								log.error("\n"+skuId + " is not added because this item already exists in cart");
							}
							
						} else {
							System.out.println("\n"+skuId+" is not added because this item is currently out of stock");
							log.info("\n"+skuId+" is not added because this item is currently out of stock");
						}
					}
				}
				
			} else {
				System.out.println("\nError while invoking SearchService getStyleData API");
				log.error("\nError while invoking SearchService getStyleData API");
				AssertJUnit.fail("Error while invoking SearchService getStyleData API");
			}
			
			fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
			System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"+fetchCartReqGen.respvalidate.returnresponseasstring());
			log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"+fetchCartReqGen.respvalidate.returnresponseasstring());
		
			AssertJUnit.assertTrue("Error while invoking CheckoutService operationFetchCart API",
					fetchCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(CheckoutServiceConstants.SUCCESS_STATUS_TYPE));
		}

		String skuId = fetchCartReqGen.respvalidate.NodeValue(CartNodes.CART_ITEM_ENTRIES_SELSIZE_SKUID.toString(), false);
		String itemId = checkoutTestsHelper.getItemIdFromSKUId(fetchCartReqGen, skuId, CheckoutDataProviderEnum.CART.name());
		
		RequestGenerator fetchWishlistReqGen = checkoutTestsHelper.operationFetchWishList(userName, password);
		System.out.println("\nPrinting CheckoutService operationFetchWishlist API response :\n\n"+fetchWishlistReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService operationFetchWishlist API response :\n\n"+fetchWishlistReqGen.respvalidate.returnresponseasstring());
		
		AssertJUnit.assertTrue("Error while invoking CheckoutService operationFetchWishlist API",
				fetchWishlistReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(CheckoutServiceConstants.SUCCESS_STATUS_TYPE));
		
		if(StringUtils.isBlank(checkoutTestsHelper.getItemIdFromSKUId(fetchCartReqGen, skuId, CheckoutDataProviderEnum.WISHLIST.name())))
		{
			System.out.println("\nPrinting skuId : "+skuId+" & itemId : "+itemId+" is moving to wishlist from cart\n");
			log.info("\nPrinting skuId : "+skuId+" & itemId : "+itemId+" is going is moving to wishlist from cart\n");
			
			RequestGenerator moveItemToWishListReqGen = checkoutTestsHelper.moveItemToWishList(userName, password, itemId);
			String moveToWishlistResponse = moveItemToWishListReqGen.respvalidate.returnresponseasstring();
			System.out.println("\nPrinting CheckoutService moveItemToWishList API response :\n\n"+moveToWishlistResponse);
			log.info("\nPrinting CheckoutService moveItemToWishList API response :\n\n"+moveToWishlistResponse);

			AssertJUnit.assertEquals("CheckoutService moveItemToWishList API is not working", 
						Integer.parseInt(successRespCode), moveItemToWishListReqGen.response.getStatus());
			
			try {
	            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/checkoutservice-movetowishlist-schema.txt");
	            List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(moveToWishlistResponse, jsonschema);
	            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in CheckoutService moveItemToWishList API response", CollectionUtils.isEmpty(missingNodeList));
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
			
		} else {
			System.out.println("\nPrinting skuId : "+skuId+" & itemId : "+itemId+" is already present in wishlist\n");
			log.info("\nPrinting skuId : "+skuId+" & itemId : "+itemId+" is already present in wishlist\n");
		}
			
	}
	
	@Test(groups = { "SchemaValidation" }, dataProvider="WishListServiceDP_operationFetchWishListDataProvider_verifyResponseDataNodesUsingSchemaValidations")
	public void WishListServiceTests_operationFetchWishList_verifyResponseDataNodesUsingSchemaValidations(String userName, String password, String successRespCode)
	{
		RequestGenerator fetchWishlistReqGen = checkoutTestsHelper.operationFetchWishList(userName, password);
		String fetchWishlistResponse = fetchWishlistReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting CheckoutService operationFetchWishList API response :\n\n"+fetchWishlistResponse);
		log.info("\nPrinting CheckoutService operationFetchWishList API response :\n\n"+fetchWishlistResponse);
	
		AssertJUnit.assertEquals("CheckoutService operationFetchCart API is not working", 
				Integer.parseInt(successRespCode), fetchWishlistReqGen.response.getStatus());
		
		try {
            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/checkoutservice-fetchwishlist-schema.txt");
            List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(fetchWishlistResponse, jsonschema);
            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in CheckoutService operationFetchWishList API response", CollectionUtils.isEmpty(missingNodeList));
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	@Test(groups = { "SchemaValidation" }, dataProvider="WishListServiceDP_addItemToWishListUsingSkuIdDataProvider_verifyResponseDataNodesUsingSchemaValidations")
	public void WishListServiceTests_addItemToWishListUsingSkuId_verifyResponseDataNodesUsingSchemaValidations(String userName, String password, String addItemQty, String addOperation, 
			String successRespCode)
	{
		List<Integer> styleIdList = checkoutTestsHelper.performSeachServiceToGetStyleIds(CheckoutServiceConstants.AWL_SEARCH_PROD, CheckoutServiceConstants.AWL_SEARCH_QTY, CheckoutServiceConstants.AWL_SEARCH_RET_DOCS, CheckoutServiceConstants.AWL_SEARCH_IS_FACET);
		
		if(!CollectionUtils.isEmpty(styleIdList))
		{
			for(Integer styleId : styleIdList)
			{
				System.out.println("\nPrinting the StyleId : "+styleId);
				log.info("\nPrinting the StyleId : "+styleId);
				
				RequestGenerator getStyleDataReqGen = checkoutTestsHelper.performGetStyleDataOperation(String.valueOf(styleId));
				System.out.println("\nPrinting StyleService getStyleData API response :\n\n"+getStyleDataReqGen.respvalidate.returnresponseasstring());
				log.info("\nPrinting StyleService getStyleData API response :\n\n"+getStyleDataReqGen.respvalidate.returnresponseasstring());
				
				AssertJUnit.assertTrue("Error while getting style data",
						getStyleDataReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(CheckoutServiceConstants.SUCCESS_STATUS_TYPE));
				
				String response = getStyleDataReqGen.respvalidate.returnresponseasstring();
				List<Integer> ids = JsonPath.read(response, "$.data..styleOptions[*].id");
				
				for(int i = 0 ; i < ids.size(); i++)
				{
					String skuId = JsonPath.read(response, "$.data..styleOptions["+i+"].skuId").toString();
					String inventoryCount = JsonPath.read(response, "$.data..styleOptions["+i+"].inventoryCount").toString();
					String available = JsonPath.read(response, "$.data..styleOptions["+i+"].available").toString();
					
					if(!inventoryCount.equals(CheckoutServiceConstants.EMPTY_VALUE) && available.equals(CheckoutServiceConstants.TRUE_VALUE))
					{
						RequestGenerator fetchWishlistReqGen = checkoutTestsHelper.operationFetchWishList(userName, password);
						System.out.println("\nPrinting CheckoutService operationFetchWishList API response :\n\n"+fetchWishlistReqGen.respvalidate.returnresponseasstring());
						log.info("\nPrinting CheckoutService operationFetchWishList API response :\n\n"+fetchWishlistReqGen.respvalidate.returnresponseasstring());
						
						if(StringUtils.isBlank(checkoutTestsHelper.getItemIdFromSKUId(fetchWishlistReqGen, skuId, CheckoutDataProviderEnum.WISHLIST.name())))
						{
							int invCount = Integer.parseInt(inventoryCount);
							int qty = Integer.parseInt(addItemQty);
							int itemQtyToAdd = invCount <= qty  ? invCount : qty;
							
							RequestGenerator addItemToWishListReqGen = checkoutTestsHelper.addItemToWishList(userName, password, skuId, null, 
									String.valueOf(itemQtyToAdd), addOperation);
							
							String addItemToWishListUsingSkuIdResponse = addItemToWishListReqGen.respvalidate.returnresponseasstring();
							
							System.out.println("\nPrinting CheckoutService addItemToWishList API response :\n\n"+addItemToWishListUsingSkuIdResponse);
							log.info("\nPrinting CheckoutService addItemToWishList API response :\n\n"+addItemToWishListUsingSkuIdResponse);
							
							AssertJUnit.assertEquals("CheckoutService addItemToWishList API is not working",
									Integer.parseInt(successRespCode), addItemToWishListReqGen.response.getStatus());
							
							try {
					            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/checkoutservice-additemtowishlistusingskuid-schema.txt");
					            List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(addItemToWishListUsingSkuIdResponse, jsonschema);
					            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in CheckoutService addItemToWishList API response", CollectionUtils.isEmpty(missingNodeList));
					        } catch (Exception e) {
					            e.printStackTrace();
					        }
							
							System.out.println("\n"+skuId+" is available but not in wishlist so added to wishlist");
							log.info("\n"+skuId+" is available but not in wishlist so added to wishlist");
							break;
							
						} else {
							System.out.println("\n"+skuId + " is not added because this item already exists in wishlist");
							log.error("\n"+skuId + " is not added because this item already exists in wishlist");
						}
						
					} else {
						System.out.println("\n"+skuId+" is not added because this item is currently out of stock");
						log.info("\n"+skuId+" is not added because this item is currently out of stock");
					}
				}
			}
			
		} else {
			System.out.println("\nError while invoking SearchService getStyleData API");
			log.error("\nError while invoking SearchService getStyleData API");
			AssertJUnit.fail("Error while invoking SearchService getStyleData API");
		}
	}
	
	@Test(groups = { "SchemaValidation" }, dataProvider="WishListServiceDP_addItemToWishListUsingStyleIdDataProvider_verifyResponseDataNodesUsingSchemaValidations")
	public void WishListServiceTests_addItemToWishListUsingStyleId_verifyResponseDataNodesUsingSchemaValidations(String userName, String password, String addItemQty, 
			String addOperation, String successRespCode)
	{
		List<Integer> styleIdList = checkoutTestsHelper.performSeachServiceToGetStyleIds(CheckoutServiceConstants.AWL_SEARCH_PROD, CheckoutServiceConstants.AWL_SEARCH_QTY, CheckoutServiceConstants.AWL_SEARCH_RET_DOCS, CheckoutServiceConstants.AWL_SEARCH_IS_FACET);
		
		if(!CollectionUtils.isEmpty(styleIdList))
		{
			for(Integer styleId : styleIdList)
			{
				System.out.println("\nPrinting the StyleId : "+styleId);
				log.info("\nPrinting the StyleId : "+styleId);
				
				RequestGenerator getStyleDataReqGen = checkoutTestsHelper.performGetStyleDataOperation(String.valueOf(styleId));
				System.out.println("\nPrinting StyleService getStyleData API response :\n\n"+getStyleDataReqGen.respvalidate.returnresponseasstring());
				log.info("\nPrinting StyleService getStyleData API response :\n\n"+getStyleDataReqGen.respvalidate.returnresponseasstring());
				
				AssertJUnit.assertTrue("Error while getting style data",
						getStyleDataReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(CheckoutServiceConstants.SUCCESS_STATUS_TYPE));
				
				String response = getStyleDataReqGen.respvalidate.returnresponseasstring();
				List<Integer> ids = JsonPath.read(response, "$.data..styleOptions[*].id");
				
				for(int i = 0 ; i < ids.size(); i++)
				{
					String skuId = JsonPath.read(response, "$.data..styleOptions["+i+"].skuId").toString();
					String inventoryCount = JsonPath.read(response, "$.data..styleOptions["+i+"].inventoryCount").toString();
					String available = JsonPath.read(response, "$.data..styleOptions["+i+"].available").toString();
					
					if(!inventoryCount.equals(CheckoutServiceConstants.EMPTY_VALUE) && available.equals(CheckoutServiceConstants.TRUE_VALUE))
					{
						RequestGenerator fetchWishlistReqGen = checkoutTestsHelper.operationFetchWishList(userName, password);
						System.out.println("\nPrinting CheckoutService operationFetchWishList API response :\n\n"+fetchWishlistReqGen.respvalidate.returnresponseasstring());
						log.info("\nPrinting CheckoutService operationFetchWishList API response :\n\n"+fetchWishlistReqGen.respvalidate.returnresponseasstring());
						
						if(StringUtils.isBlank(checkoutTestsHelper.getItemIdFromSKUId(fetchWishlistReqGen, skuId, CheckoutDataProviderEnum.WISHLIST.name())))
						{
							int invCount = Integer.parseInt(inventoryCount);
							int qty = Integer.parseInt(addItemQty);
							int itemQtyToAdd = invCount <= qty  ? invCount : qty;
							
							RequestGenerator addItemToWishListReqGen = checkoutTestsHelper.addItemToWishList(userName, password, null, String.valueOf(styleId), String.valueOf(itemQtyToAdd), addOperation);
							String addItemToWishlistUsingStyleIdResponse = addItemToWishListReqGen.respvalidate.returnresponseasstring();
							System.out.println("\nPrinting CheckoutService addItemToWishList API response :\n\n"+addItemToWishlistUsingStyleIdResponse);
							log.info("\nPrinting CheckoutService addItemToWishList API response :\n\n"+addItemToWishlistUsingStyleIdResponse);
							
							AssertJUnit.assertEquals("CheckoutService addItemToWishListUsingStyleId API is not working",
									Integer.parseInt(successRespCode), addItemToWishListReqGen.response.getStatus());
							
							try {
					            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/checkoutservice-additemtowishlistusingstyleid-schema.txt");
					            List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(addItemToWishlistUsingStyleIdResponse, jsonschema);
					            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in CheckoutService addItemToWishList API response", CollectionUtils.isEmpty(missingNodeList));
					        } catch (Exception e) {
					            e.printStackTrace();
					        }
							
							System.out.println("\n"+skuId+" is available but not in wishlist so added to wishlist");
							log.info("\n"+skuId+" is available but not in wishlist so added to wishlist");
							break;
							
						} else {
							System.out.println("\n"+skuId + " is not added because this item already exists in wishlist");
							log.error("\n"+skuId + " is not added because this item already exists in wishlist");
						}
						
					} else {
						System.out.println("\n"+skuId+" is not added because this item is currently out of stock");
						log.info("\n"+skuId+" is not added because this item is currently out of stock");
					}
				}
			}
			
		} else {
			System.out.println("\nError while invoking SearchService API");
			log.error("\nError while invoking SearchService API");
			AssertJUnit.fail("Error while invoking SearchService API");
		}
	}
	
	@Test(groups = { "SchemaValidation" }, dataProvider="WishListServiceDP_updateItemInWishListDataProvider_verifyResponseDataNodesUsingSchemaValidations")
	public void WishListServiceTests_updateItemInWishList_verifyResponseDataNodesUsingSchemaValidations(String userName, String password, String itemQtyToUpdate, 
			String updateOpn, String successRespCode)
	{
		RequestGenerator fetchWishlistReqGen = checkoutTestsHelper.operationFetchWishList(userName, password);
		System.out.println("\nPrinting CheckoutService operationFetchWishList API response :\n\n"+fetchWishlistReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService operationFetchWishList API response :\n\n"+fetchWishlistReqGen.respvalidate.returnresponseasstring());
	
		AssertJUnit.assertTrue("Error while invoking CheckoutService operationFetchWishList API",
				fetchWishlistReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(CheckoutServiceConstants.SUCCESS_STATUS_TYPE));
		
		if(fetchWishlistReqGen.respvalidate.NodeValue(WishListNodes.WISHLIST_TOTAL_COUNT.toString(), true).equals(CheckoutServiceConstants.EMPTY_VALUE))
		{
			List<Integer> styleIdList = checkoutTestsHelper.performSeachServiceToGetStyleIds(CheckoutServiceConstants.UWL_SEARCH_PROD, CheckoutServiceConstants.UWL_SEARCH_QTY, CheckoutServiceConstants.UWL_SEARCH_RET_DOCS, CheckoutServiceConstants.UWL_SEARCH_IS_FACET);
			
			if(!CollectionUtils.isEmpty(styleIdList))
			{
				for(Integer styleId : styleIdList)
				{
					System.out.println("\nPrinting the StyleId : "+styleId);
					log.info("\nPrinting the StyleId : "+styleId);
					
					RequestGenerator getStyleDataReqGen = checkoutTestsHelper.performGetStyleDataOperation(String.valueOf(styleId));
					System.out.println("\nPrinting StyleService getStyleData API response :\n\n"+getStyleDataReqGen.respvalidate.returnresponseasstring());
					log.info("\nPrinting StyleService getStyleData API response :\n\n"+getStyleDataReqGen.respvalidate.returnresponseasstring());
					
					AssertJUnit.assertTrue("Error while getting style data",
							getStyleDataReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(CheckoutServiceConstants.SUCCESS_STATUS_TYPE));
					
					String response = getStyleDataReqGen.respvalidate.returnresponseasstring();
					List<Integer> ids = JsonPath.read(response, "$.data..styleOptions[*].id");
					
					for(int i = 0 ; i < ids.size(); i++)
					{
						String skuId = JsonPath.read(response, "$.data..styleOptions["+i+"].skuId").toString();
						String inventoryCount = JsonPath.read(response, "$.data..styleOptions["+i+"].inventoryCount").toString();
						String available = JsonPath.read(response, "$.data..styleOptions["+i+"].available").toString();
						
						if(!inventoryCount.equals(CheckoutServiceConstants.EMPTY_VALUE) && available.equals(CheckoutServiceConstants.TRUE_VALUE))
						{
							fetchWishlistReqGen = checkoutTestsHelper.operationFetchWishList(userName, password);
							System.out.println("\nPrinting CheckoutService operationFetchWishList API response :\n\n"+fetchWishlistReqGen.respvalidate.returnresponseasstring());
							log.info("\nPrinting CheckoutService operationFetchWishList API response :\n\n"+fetchWishlistReqGen.respvalidate.returnresponseasstring());
							
							AssertJUnit.assertTrue("Error while invoking CheckoutService operationFetchWishList API",
									fetchWishlistReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(CheckoutServiceConstants.SUCCESS_STATUS_TYPE));
							
							if(StringUtils.isBlank(checkoutTestsHelper.getItemIdFromSKUId(fetchWishlistReqGen, skuId, CheckoutDataProviderEnum.WISHLIST.name())))
							{
								RequestGenerator addItemToWishlistReqGen = checkoutTestsHelper.addItemToWishList(userName, password, skuId, null, 
										inventoryCount, CheckoutServiceConstants.ADD_OPERATION);
								System.out.println("\nPrinting CheckoutService addItemToCart API response :\n\n"+addItemToWishlistReqGen.respvalidate.returnresponseasstring());
								log.info("\nPrinting CheckoutService addItemToCart API response :\n\n"+addItemToWishlistReqGen.respvalidate.returnresponseasstring());
								
								AssertJUnit.assertTrue("Error while invoking CheckoutService addItemToWishList API",
										addItemToWishlistReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(CheckoutServiceConstants.SUCCESS_STATUS_TYPE));
								
								System.out.println("\n"+skuId+" is available but not in cart so added to cart");
								log.info("\n"+skuId+" is available but not in cart so added to cart");
								break;
								
							} else {
								System.out.println("\n"+skuId + " is not added because this item already exists in cart");
								log.error("\n"+skuId + " is not added because this item already exists in cart");
							}
							
						} else {
							System.out.println("\n"+skuId+" is not added because this item is currently out of stock");
							log.info("\n"+skuId+" is not added because this item is currently out of stock");
						}
					}
				}
				
			} else {
				System.out.println("\nError while invoking SearchService API");
				log.error("\nError while invoking SearchService API");
				AssertJUnit.fail("Error while invoking SearchService API");
			}
			
			fetchWishlistReqGen = checkoutTestsHelper.operationFetchWishList(userName, password);
			System.out.println("\nPrinting operationFetchWishList API response :\n\n"+fetchWishlistReqGen.respvalidate.returnresponseasstring());
			log.info("\nPrinting operationFetchWishList API response :\n\n"+fetchWishlistReqGen.respvalidate.returnresponseasstring());
		
			AssertJUnit.assertTrue("Error while invoking CheckoutService operationFetchWishList API",
					fetchWishlistReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(CheckoutServiceConstants.SUCCESS_STATUS_TYPE));
			
		}
			String skuId = fetchWishlistReqGen.respvalidate.NodeValue(WishListNodes.WISHLIST_ITEM_ENTRIES_AVAILSIZES_SKUID.toString(), false);
			String itemId = checkoutTestsHelper.getItemIdFromSKUId(fetchWishlistReqGen, skuId, CheckoutDataProviderEnum.WISHLIST.name());
			
			System.out.println("\nPrinting skuId : "+skuId+" & itemId : "+itemId+" & quantity : "+itemQtyToUpdate+" is going for updation\n");
			log.info("\nPrinting skuId : "+skuId+" & itemId : "+itemId+" & quantity : "+itemQtyToUpdate+" is going for updation\n");
			
			RequestGenerator updateItemInWishlistReqGen = checkoutTestsHelper.updateItemInWishList(userName, password, itemId, skuId, String.valueOf(itemQtyToUpdate), updateOpn);
			String updateItemInWishlistResponse = updateItemInWishlistReqGen.respvalidate.returnresponseasstring();
			System.out.println("\nPrinting CheckoutService updateItemInWishList API response :\n\n"+updateItemInWishlistResponse);
			log.info("\nPrinting CheckoutService updateItemInWishList API response :\n\n"+updateItemInWishlistResponse);

			AssertJUnit.assertEquals("CheckoutService updateItemInWishList API is not working",
					Integer.parseInt(successRespCode), updateItemInWishlistReqGen.response.getStatus());
			
			try {
	            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/checkoutservice-updateiteminwishlist-schema.txt");
	            List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(updateItemInWishlistResponse, jsonschema);
	            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in CheckoutService updateItemInWishList API response", CollectionUtils.isEmpty(missingNodeList));
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
			
	}
	
	@Test(groups = { "SchemaValidation" }, dataProvider="WishListServiceDP_removeItemFromWishListDataProvider_verifyResponseDataNodesUsingSchemaValidations")
	public void WishListServiceTests_removeItemFromWishList_verifyResponseDataNodesUsingSchemaValidations(String userName, String password, String delOperation, 
			String successRespCode)
	{
		RequestGenerator fetchWishlistReqGen = checkoutTestsHelper.operationFetchWishList(userName, password);
		System.out.println("\nPrinting CheckoutService operationFetchWishList API response :\n\n"+fetchWishlistReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService operationFetchWishList API response :\n\n"+fetchWishlistReqGen.respvalidate.returnresponseasstring());
	
		AssertJUnit.assertTrue("Error while invoking CheckoutService operationFetchWishList API",
				fetchWishlistReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(CheckoutServiceConstants.SUCCESS_STATUS_TYPE));
		
		if(fetchWishlistReqGen.respvalidate.NodeValue(WishListNodes.WISHLIST_TOTAL_COUNT.toString(), true).equals(CheckoutServiceConstants.EMPTY_VALUE))
		{
			List<Integer> styleIdList = checkoutTestsHelper.performSeachServiceToGetStyleIds(CheckoutServiceConstants.RWL_SEARCH_PROD, CheckoutServiceConstants.RWL_SEARCH_QTY, CheckoutServiceConstants.RWL_SEARCH_RET_DOCS, CheckoutServiceConstants.RWL_SEARCH_IS_FACET);
			
			if(!CollectionUtils.isEmpty(styleIdList))
			{
				for(Integer styleId : styleIdList)
				{
					System.out.println("\nPrinting the StyleId : "+styleId);
					log.info("\nPrinting the StyleId : "+styleId);
					
					RequestGenerator getStyleDataReqGen = checkoutTestsHelper.performGetStyleDataOperation(String.valueOf(styleId));
					System.out.println("\nPrinting StyleService getStyleData API response :\n\n"+getStyleDataReqGen.respvalidate.returnresponseasstring());
					log.info("\nPrinting StyleService getStyleData API response :\n\n"+getStyleDataReqGen.respvalidate.returnresponseasstring());
					
					AssertJUnit.assertTrue("Error while getting style data",
							getStyleDataReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(CheckoutServiceConstants.SUCCESS_STATUS_TYPE));
					
					String response = getStyleDataReqGen.respvalidate.returnresponseasstring();
					List<Integer> ids = JsonPath.read(response, "$.data..styleOptions[*].id");
					
					for(int i = 0 ; i < ids.size(); i++)
					{
						String skuId = JsonPath.read(response, "$.data..styleOptions["+i+"].skuId").toString();
						String inventoryCount = JsonPath.read(response, "$.data..styleOptions["+i+"].inventoryCount").toString();
						String available = JsonPath.read(response, "$.data..styleOptions["+i+"].available").toString();
						
						if(!inventoryCount.equals(CheckoutServiceConstants.EMPTY_VALUE) && available.equals(CheckoutServiceConstants.TRUE_VALUE))
						{
							fetchWishlistReqGen = checkoutTestsHelper.operationFetchWishList(userName, password);
							System.out.println("\nPrinting CheckoutService operationFetchWishList API response :\n\n"+fetchWishlistReqGen.respvalidate.returnresponseasstring());
							log.info("\nPrinting CheckoutService operationFetchWishList API response :\n\n"+fetchWishlistReqGen.respvalidate.returnresponseasstring());
							
							AssertJUnit.assertTrue("Error while invoking CheckoutService operationFetchWishList API",
									fetchWishlistReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(CheckoutServiceConstants.SUCCESS_STATUS_TYPE));
							
							if(StringUtils.isBlank(checkoutTestsHelper.getItemIdFromSKUId(fetchWishlistReqGen, skuId, CheckoutDataProviderEnum.WISHLIST.name())))
							{
								RequestGenerator addItemToWishlistReqGen = checkoutTestsHelper.addItemToWishList(userName, password, skuId, null, inventoryCount, CheckoutServiceConstants.ADD_OPERATION);
								System.out.println("\nPrinting CheckoutService addItemToCart API response :\n\n"+addItemToWishlistReqGen.respvalidate.returnresponseasstring());
								log.info("\nPrinting CheckoutService addItemToCart API response :\n\n"+addItemToWishlistReqGen.respvalidate.returnresponseasstring());
								
								AssertJUnit.assertTrue("Error while invoking CheckoutService addItemToWishList API",
										addItemToWishlistReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(CheckoutServiceConstants.SUCCESS_STATUS_TYPE));
								
								System.out.println("\n"+skuId+" is available but not in cart so added to cart");
								log.info("\n"+skuId+" is available but not in cart so added to cart");
								break;
								
							} else {
								System.out.println("\n"+skuId + " is not added because this item already exists in cart");
								log.error("\n"+skuId + " is not added because this item already exists in cart");
							}
							
						} else {
							System.out.println("\n"+skuId+" is not added because this item is currently out of stock");
							log.info("\n"+skuId+" is not added because this item is currently out of stock");
						}
					}
				}
				
			} else {
				System.out.println("\nError while invoking SearchService API");
				log.error("\nError while invoking SearchService API");
				AssertJUnit.fail("Error while invoking SearchService API");
			}
			
			fetchWishlistReqGen = checkoutTestsHelper.operationFetchWishList(userName, password);
			System.out.println("\nPrinting CheckoutService operationFetchWishList API response :\n\n"+fetchWishlistReqGen.respvalidate.returnresponseasstring());
			log.info("\nPrinting CheckoutService operationFetchWishList API response :\n\n"+fetchWishlistReqGen.respvalidate.returnresponseasstring());
		
			AssertJUnit.assertTrue("Error while invoking CheckoutService operationFetchWishList API",
					fetchWishlistReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(CheckoutServiceConstants.SUCCESS_STATUS_TYPE));
		}
			String skuId = fetchWishlistReqGen.respvalidate.NodeValue(WishListNodes.WISHLIST_ITEM_ENTRIES_AVAILSIZES_SKUID.toString(), false);
			String itemId = checkoutTestsHelper.getItemIdFromSKUId(fetchWishlistReqGen, skuId, CheckoutDataProviderEnum.WISHLIST.name());
			
			System.out.println("\nPrinting skuId : "+skuId+" & itemId : "+itemId+" is going to remove item from wishlist\n");
			log.info("\nPrinting skuId : "+skuId+" & itemId : "+itemId+" is going to remove item from wishlist\n");
			
			RequestGenerator removeItemFromWishlist = checkoutTestsHelper.removeItemFromWishList(userName, password, itemId, delOperation);
			String removeItemFromWishlistResponse = removeItemFromWishlist.respvalidate.returnresponseasstring();
			System.out.println("\nPrinting CheckoutService removeItemFromWishList API response :\n\n"+removeItemFromWishlistResponse);
			log.info("\nPrinting CheckoutService removeItemFromWishList API response :\n\n"+removeItemFromWishlistResponse);

			AssertJUnit.assertEquals("CheckoutService removeItemFromWishList API is not working",
					Integer.parseInt(successRespCode), removeItemFromWishlist.response.getStatus());
			
			try {
	            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/checkoutservice-removeitemfromwishlist-schema.txt");
	            List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(removeItemFromWishlistResponse, jsonschema);
	            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in CheckoutService removeItemFromWishList API response", CollectionUtils.isEmpty(missingNodeList));
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
			
	}
	
	@Test(groups = { "SchemaValidation" }, dataProvider="WishListServiceDP_moveItemToCartDataProvider_verifyResponseDataNodesUsingSchemaValidations")
	public void WishListServiceTests_moveItemToCart_verifyResponseDataNodesUsingSchemaValidations(String userName, String password, String successRespCode)
	{
		RequestGenerator fetchWishlistReqGen = checkoutTestsHelper.operationFetchWishList(userName, password);
		System.out.println("\nPrinting CheckoutService operationFetchWishList API response :\n\n"+fetchWishlistReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService operationFetchWishList API response :\n\n"+fetchWishlistReqGen.respvalidate.returnresponseasstring());
	
		AssertJUnit.assertTrue("Error while invoking CheckoutService operationFetchWishList API",
				fetchWishlistReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(CheckoutServiceConstants.SUCCESS_STATUS_TYPE));
		
		if(fetchWishlistReqGen.respvalidate.NodeValue(WishListNodes.WISHLIST_TOTAL_COUNT.toString(), true).equals(CheckoutServiceConstants.EMPTY_VALUE))
		{
			List<Integer> styleIdList = checkoutTestsHelper.performSeachServiceToGetStyleIds(CheckoutServiceConstants.MC_SEARCH_PROD, CheckoutServiceConstants.MC_SEARCH_QTY, CheckoutServiceConstants.MC_SEARCH_RET_DOCS, CheckoutServiceConstants.MC_SEARCH_IS_FACET);
			
			if(!CollectionUtils.isEmpty(styleIdList))
			{
				for(Integer styleId : styleIdList)
				{
					System.out.println("\nPrinting the StyleId : "+styleId);
					log.info("\nPrinting the StyleId : "+styleId);
					
					RequestGenerator getStyleDataReqGen = checkoutTestsHelper.performGetStyleDataOperation(String.valueOf(styleId));
					System.out.println("\nPrinting StyleService getStyleData API response :\n\n"+getStyleDataReqGen.respvalidate.returnresponseasstring());
					log.info("\nPrinting StyleService getStyleData API response :\n\n"+getStyleDataReqGen.respvalidate.returnresponseasstring());
					
					AssertJUnit.assertTrue("Error while getting style data",
							getStyleDataReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(CheckoutServiceConstants.SUCCESS_STATUS_TYPE));
					
					String response = getStyleDataReqGen.respvalidate.returnresponseasstring();
					List<Integer> ids = JsonPath.read(response, "$.data..styleOptions[*].id");
					
					for(int i = 0 ; i < ids.size(); i++)
					{
						String skuId = JsonPath.read(response, "$.data..styleOptions["+i+"].skuId").toString();
						String inventoryCount = JsonPath.read(response, "$.data..styleOptions["+i+"].inventoryCount").toString();
						String available = JsonPath.read(response, "$.data..styleOptions["+i+"].available").toString();
						
						if(!inventoryCount.equals(CheckoutServiceConstants.EMPTY_VALUE) && available.equals(CheckoutServiceConstants.TRUE_VALUE))
						{
							fetchWishlistReqGen = checkoutTestsHelper.operationFetchWishList(userName, password);
							System.out.println("\nPrinting CheckoutService operationFetchWishList API response :\n\n"+fetchWishlistReqGen.respvalidate.returnresponseasstring());
							log.info("\nPrinting CheckoutService operationFetchWishList API response :\n\n"+fetchWishlistReqGen.respvalidate.returnresponseasstring());
						
							AssertJUnit.assertTrue("Error while invoking CheckoutService operationFetchWishList API",
									fetchWishlistReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(CheckoutServiceConstants.SUCCESS_STATUS_TYPE));
							
							if(StringUtils.isBlank(checkoutTestsHelper.getItemIdFromSKUId(fetchWishlistReqGen, skuId, CheckoutDataProviderEnum.WISHLIST.name())))
							{
								RequestGenerator addItemToWishlistReqGen = checkoutTestsHelper.addItemToWishList(userName, password, skuId, null, inventoryCount, CheckoutServiceConstants.ADD_OPERATION);
								System.out.println("\nPrinting CheckoutService addItemToWishList API response :\n\n"+addItemToWishlistReqGen.respvalidate.returnresponseasstring());
								log.info("\nPrinting CheckoutService addItemToWishList API response :\n\n"+addItemToWishlistReqGen.respvalidate.returnresponseasstring());
								
								AssertJUnit.assertTrue("Error while invoking CheckoutService addItemToWishList API",
										addItemToWishlistReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(CheckoutServiceConstants.SUCCESS_STATUS_TYPE));
								
								System.out.println("\n"+skuId+" is available but not in wishlist so added to wishlist");
								log.info("\n"+skuId+" is available but not in wishlist so added to wishlist");
								break;
								
							} else {
								System.out.println("\n"+skuId + " is not added because this item already exists in wishlist");
								log.error("\n"+skuId + " is not added because this item already exists in wishlist");
							}
							
						} else {
							System.out.println("\n"+skuId+" is not added because this item is currently out of stock");
							log.info("\n"+skuId+" is not added because this item is currently out of stock");
						}
					}
				}
				
			} else {
				System.out.println("\nError while invoking SearchService API");
				log.error("\nError while invoking SearchService API");
				AssertJUnit.fail("Error while invoking SearchService API");
			}
			
			fetchWishlistReqGen = checkoutTestsHelper.operationFetchWishList(userName, password);
			System.out.println("\nPrinting CheckoutService operationFetchWishList API response :\n\n"+fetchWishlistReqGen.respvalidate.returnresponseasstring());
			log.info("\nPrinting CheckoutService operationFetchWishList API response :\n\n"+fetchWishlistReqGen.respvalidate.returnresponseasstring());
		
			AssertJUnit.assertTrue("Error while invoking CheckoutService operationFetchWishList API",
					fetchWishlistReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(CheckoutServiceConstants.SUCCESS_STATUS_TYPE));
		}

		String skuId = fetchWishlistReqGen.respvalidate.NodeValue(WishListNodes.WISHLIST_ITEM_ENTRIES_SELSIZE_SKUID.toString(), false);
		String itemId = checkoutTestsHelper.getItemIdFromSKUId(fetchWishlistReqGen, skuId, CheckoutDataProviderEnum.WISHLIST.name());
		
		RequestGenerator fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
		System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"+fetchCartReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"+fetchCartReqGen.respvalidate.returnresponseasstring());
		
		AssertJUnit.assertTrue("Error while invoking CheckoutService operationFetchCart API",
				fetchCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(CheckoutServiceConstants.SUCCESS_STATUS_TYPE));
		
		if(StringUtils.isBlank(checkoutTestsHelper.getItemIdFromSKUId(fetchCartReqGen, skuId, CheckoutDataProviderEnum.CART.name())))
		{
			System.out.println("\nPrinting skuId : "+skuId+" & itemId : "+itemId+" is moving to cart from wishlist\n");
			log.info("\nPrinting skuId : "+skuId+" & itemId : "+itemId+" is going is moving to cart from wishlist\n");
			
			RequestGenerator moveItemToCartReqGen = checkoutTestsHelper.moveItemToCart(userName, password, itemId);
			String moveItemToCartResponse = moveItemToCartReqGen.respvalidate.returnresponseasstring();
			System.out.println("\nPrinting CheckoutService moveItemToCart API response :\n\n"+moveItemToCartResponse);
			log.info("\nPrinting CheckoutService moveItemToCart API response :\n\n"+moveItemToCartResponse);

			AssertJUnit.assertEquals("CheckoutService moveItemToCart API is not working", 
						Integer.parseInt(successRespCode), moveItemToCartReqGen.response.getStatus());
			
			try {
	            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/checkoutservice-moveitemtocart-schema.txt");
	            List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(moveItemToCartResponse, jsonschema);
	            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in CheckoutService moveItemToCart API response", CollectionUtils.isEmpty(missingNodeList));
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
			
		} else {
			System.out.println("\nPrinting skuId : "+skuId+" & itemId : "+itemId+" is already present in cart\n");
			log.info("\nPrinting skuId : "+skuId+" & itemId : "+itemId+" is already present in cart\n");
			
		}
	}
	
	
}
