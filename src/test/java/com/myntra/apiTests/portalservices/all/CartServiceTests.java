package com.myntra.apiTests.portalservices.all;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Calendar;
import java.util.List;

import com.myntra.apiTests.common.Myntra;
import com.myntra.apiTests.dataproviders.CartServiceDP;
import com.myntra.apiTests.portalservices.checkoutservice.CartNodes;
import com.myntra.apiTests.portalservices.checkoutservice.CheckoutTestsHelper;
import com.myntra.apiTests.portalservices.checkoutservice.WishListNodes;
import com.myntra.apiTests.portalservices.checkoutservice.CheckoutDataProviderEnum;
import com.myntra.apiTests.portalservices.checkoutservice.MyntCreditNodes;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import com.jayway.jsonpath.JsonPath;
import com.myntra.apiTests.portalservices.commons.StatusNodes;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.Toolbox;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.PayloadType;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.apiTests.common.ServiceType;
import com.myntra.apiTests.common.APINAME;

/**
 * @author shankara.c
 *
 */
public class CartServiceTests extends CartServiceDP {
	static Logger log = Logger.getLogger(CartServiceTests.class);
	static CheckoutTestsHelper checkoutTestsHelper = new CheckoutTestsHelper();
	static Initialize init = new Initialize("/Data/configuration");

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
	@Test(groups = { "Sanity", "FoxSanity", "Regression", "MiniRegression",
			"ExhaustiveRegression" }, dataProvider = "CartServiceDP_addItemToCartDataProvider_validateAPIResponse", description = "1. Perform search and extract styleIds 2.Check if searched styleIds exists in user's cart or not. "
					+ "\n 3. login user using IDP service call. 4.hit addItemToCart API giving skuId which doesnt exists in cart"
					+ "\n 5.Check the service response must be 200")
	public void CartServiceTests_addItemToCart_verifySuccessResponse(String userName, String password,
			String addItemQty, String addOperation, String successRespCode) {
		long startTime = Calendar.getInstance().getTimeInMillis();
		List<Integer> styleIdList = checkoutTestsHelper.performSeachServiceToGetStyleIds(AC_SEARCH_PROD, AC_SEARCH_QTY,
				AC_SEARCH_RET_DOCS, AC_SEARCH_IS_FACET);

		outerloop: if (!CollectionUtils.isEmpty(styleIdList)) {
			for (Integer styleId : styleIdList) {
				System.out.println("\nPrinting the StyleId : " + styleId);
				log.info("\nPrinting the StyleId : " + styleId);

				RequestGenerator getStyleDataReqGen = checkoutTestsHelper
						.performGetStyleDataOperation(String.valueOf(styleId));
				// System.out.println("\nPrinting StyleService getStyleData API
				// response
				// :\n\n"+getStyleDataReqGen.respvalidate.returnresponseasstring());
				// log.info("\nPrinting StyleService getStyleData API response
				// :\n\n"+getStyleDataReqGen.respvalidate.returnresponseasstring());

				AssertJUnit.assertTrue("Error while getting style data", getStyleDataReqGen.respvalidate
						.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));
				System.out.println("Above inventory empty check:");
				String response = getStyleDataReqGen.respvalidate.returnresponseasstring();
				List<Integer> ids = JsonPath.read(response, "$.data..styleOptions[*].id");
				for (int i = 0; i < ids.size(); i++) {
					String skuId = JsonPath.read(response, "$.data..styleOptions[" + i + "].skuId").toString();
					String inventoryCount = JsonPath.read(response, "$.data..styleOptions[" + i + "].inventoryCount")
							.toString();
					String available = JsonPath.read(response, "$.data..styleOptions[" + i + "].available").toString();

					if (!inventoryCount.equals(EMPTY_VALUE) && available.equals(TRUE_VALUE)) {
						RequestGenerator fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
						// System.out.println("\nPrinting CheckoutService
						// operationFetchCart API response
						// :\n\n"+fetchCartReqGen.respvalidate.returnresponseasstring());
						// log.info("\nPrinting CheckoutService
						// operationFetchCart API response
						// :\n\n"+fetchCartReqGen.respvalidate.returnresponseasstring());

						if (StringUtils.isBlank(checkoutTestsHelper.getItemIdFromSKUId(fetchCartReqGen, skuId,
								CheckoutDataProviderEnum.CART.name()))) {
							int invCount = Integer.parseInt(inventoryCount);
							int qty = Integer.parseInt(addItemQty);
							int itemQtyToAdd = invCount <= qty ? invCount : qty;

							RequestGenerator addItemToCartReqGen = checkoutTestsHelper.addItemToCart(userName, password,
									skuId, String.valueOf(itemQtyToAdd), addOperation);
							// System.out.println("\nPrinting CheckoutService
							// addItemToCart API response
							// :\n\n"+addItemToCartReqGen.respvalidate.returnresponseasstring());
							// log.info("\nPrinting CheckoutService
							// addItemToCart API response
							// :\n\n"+addItemToCartReqGen.respvalidate.returnresponseasstring());

							AssertJUnit.assertEquals("CheckoutService addItemToCart API is not working",
									Integer.parseInt(successRespCode), addItemToCartReqGen.response.getStatus());

							// System.out.println("\n"+skuId+" is available and
							// it doesn't exists in cart so added to cart");
							// log.info("\n"+skuId+" is available and it doesn't
							// exists in cart so added to cart");
							break outerloop;

						} else {
							System.out.println("\n" + skuId + " is not added because this item already exists in cart");
							// log.error("\n"+skuId + " is not added because
							// this item already exists in cart");
						}

					} else {
						System.out.println("\n" + skuId + " is not added because this item is currently out of stock");
						// log.info("\n"+skuId+" is not added because this item
						// is currently out of stock");
					}
				}

			}

		} else {
			System.out.println("\nError while invoking SearchService getStyleData API");
			// log.error("\nError while invoking SearchService getStyleData
			// API");
			AssertJUnit.fail("Error while invoking SearchService getStyleData API");
		}

		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;

		System.out.println(
				"\nTime taken to execute - TestCase - CheckoutServiceTests_addItemToCart_verifySuccessResponse : "
						+ timeTaken + " seconds\n");
		log.info("\nTime taken to execute - TestCase - CheckoutServiceTests_addItemToCart_verifySuccessResponse : "
				+ timeTaken + " seconds\n");

	}

	/**
	 * This Test Case used to invoke CheckoutService addItemToCart API and
	 * verify for success status response
	 * 
	 * @param userName
	 * @param password
	 * @param addItemQty
	 * @param addOperation
	 * @param successStatusCode
	 * @param successStatusMsg
	 * @param successStatusType
	 */
	@Test(groups = { "Sanity", "Regression", "MiniRegression",
			"ExhaustiveRegression" }, dataProvider = "CartServiceDP_addItemToCartDataProvider_positiveCases", description = "1. Perform search and extract styleIds 2.Check if searched styleIds exists in user's cart or not. "
					+ "\n 3. login user using IDP service call. 4.hit addItemToCart API giving skuId which doesnt exists in cart"
					+ "\n 5.verify statusCode, statusMessage & statusType of API response must be of success")
	public void CartServiceTests_addItemToCart_verifySuccessStatusResponse(String userName, String password,
			String addItemQty, String addOperation, String successStatusCode, String successStatusMsg,
			String successStatusType) {
		long startTime = Calendar.getInstance().getTimeInMillis();
		List<Integer> styleIdList = checkoutTestsHelper.performSeachServiceToGetStyleIds(AC_SEARCH_PROD, AC_SEARCH_QTY,
				AC_SEARCH_RET_DOCS, AC_SEARCH_IS_FACET);

		outerloop: if (!CollectionUtils.isEmpty(styleIdList)) {
			for (Integer styleId : styleIdList) {
				System.out.println("\nPrinting the StyleId : " + styleId);
				log.info("\nPrinting the StyleId : " + styleId);

				RequestGenerator getStyleDataReqGen = checkoutTestsHelper
						.performGetStyleDataOperation(String.valueOf(styleId));
				System.out.println("\nPrinting StyleService getStyleData API response :\n\n"
						+ getStyleDataReqGen.respvalidate.returnresponseasstring());
				log.info("\nPrinting StyleService API getStyleData response :\n\n"
						+ getStyleDataReqGen.respvalidate.returnresponseasstring());

				AssertJUnit.assertTrue("Error while getting style data", getStyleDataReqGen.respvalidate
						.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));

				String response = getStyleDataReqGen.respvalidate.returnresponseasstring();
				List<Integer> ids = JsonPath.read(response, "$.data..styleOptions[*].id");

				for (int i = 0; i < ids.size(); i++) {
					String skuId = JsonPath.read(response, "$.data..styleOptions[" + i + "].skuId").toString();
					String inventoryCount = JsonPath.read(response, "$.data..styleOptions[" + i + "].inventoryCount")
							.toString();
					String available = JsonPath.read(response, "$.data..styleOptions[" + i + "].available").toString();

					if (!inventoryCount.equals(EMPTY_VALUE) && available.equals(TRUE_VALUE)) {
						RequestGenerator fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
						System.out.println("\nPrinting operationFetchCart API response :\n\n"
								+ fetchCartReqGen.respvalidate.returnresponseasstring());
						log.info("\nPrinting operationFetchCart API response :\n\n"
								+ fetchCartReqGen.respvalidate.returnresponseasstring());

						if (StringUtils.isBlank(checkoutTestsHelper.getItemIdFromSKUId(fetchCartReqGen, skuId,
								CheckoutDataProviderEnum.CART.name()))) {
							int invCount = Integer.parseInt(inventoryCount);
							int qty = Integer.parseInt(addItemQty);
							int itemQtyToAdd = invCount <= qty ? invCount : qty;

							RequestGenerator addItemToCartReqGen = checkoutTestsHelper.addItemToCart(userName, password,
									skuId, String.valueOf(itemQtyToAdd), addOperation);
							System.out.println("\nPrinting CheckoutService addItemToCart API response :\n\n"
									+ addItemToCartReqGen.respvalidate.returnresponseasstring());
							log.info("\nPrinting CheckoutService addItemToCart API response :\n\n"
									+ addItemToCartReqGen.respvalidate.returnresponseasstring());

							AssertJUnit.assertTrue(
									"CheckoutService addItemToCart API response status code is not a success status code",
									addItemToCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_CODE.toString(), true)
											.contains(successStatusCode));

							AssertJUnit.assertTrue(
									"CheckoutService addItemToCart API response status message is not a success status message",
									addItemToCartReqGen.respvalidate
											.NodeValue(StatusNodes.STATUS_MESSAGE.toString(), true)
											.contains(successStatusMsg));

							AssertJUnit.assertTrue(
									"CheckoutService addItemToCart API response status type is not a success status type",
									addItemToCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
											.contains(successStatusType));

							System.out.println(
									"\n" + skuId + " is available and it doesn't exists in cart so added to cart");
							log.info("\n" + skuId + " is available and it doesn't exists in cart so added to cart");
							break outerloop;

						} else {
							System.out.println("\n" + skuId + " is not added because this item already exists in cart");
							log.error("\n" + skuId + " is not added because this item already exists in cart");
						}

					} else {
						System.out.println("\n" + skuId + " is not added because this item is currently out of stock");
						log.info("\n" + skuId + " is not added because this item is currently out of stock");
					}
				}
			}

		} else {
			System.out.println("\nError while invoking SearchService getStyleData API");
			log.error("\nError while invoking SearchService getStyleData API");
			AssertJUnit.fail("Error while invoking SearchService getStyleData API");
		}

		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;

		System.out.println(
				"\nTime taken to execute - TestCase - CheckoutServiceTests_addItemToCart_verifySuccessStatusResponse : "
						+ timeTaken + " seconds\n");
		log.info(
				"\nTime taken to execute - TestCase - CheckoutServiceTests_addItemToCart_verifySuccessStatusResponse : "
						+ timeTaken + " seconds\n");
	}

	/**
	 * This Test Case used to invoke CheckoutService addItemToCart API and
	 * verify for failure status response
	 * 
	 * @param userName
	 * @param password
	 * @param skuID
	 * @param addItemQty
	 * @param addOperation
	 * @param successStatusCode
	 * @param successStatusMsg
	 * @param successStatusType
	 */
	@Test(groups = { "Sanity", "Regression", "MiniRegression",
			"ExhaustiveRegression" }, dataProvider = "CartServiceDP_addItemToCartDataProvider_negetiveCases", description = "1. provide some invalid combinations of data"
					+ "\n 3. login user using IDP service call. 4.hit addItemToCart API giving some invalid data"
					+ "\n 5.verify statusCode, statusMessage & statusType of API response must be of Failure")
	public void CartServiceTests_addItemToCart_verifyFailureStatusResponse(String userName, String password,
			String skuID, String addItemQty, String addOperation, String successStatusCode, String successStatusMsg,
			String successStatusType) {
		long startTime = Calendar.getInstance().getTimeInMillis();
		RequestGenerator addItemToCartReqGen = checkoutTestsHelper.addItemToCart(userName, password, skuID, addItemQty,
				addOperation);
		System.out.println("\nPrinting addItemToCart API response :\n\n"
				+ addItemToCartReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting addItemToCart API response :\n\n"
				+ addItemToCartReqGen.respvalidate.returnresponseasstring());

		AssertJUnit.assertTrue("CheckoutService addItemToCart API response status code is not a failure status code",
				addItemToCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_CODE.toString(), true)
						.contains(successStatusCode));

		AssertJUnit.assertTrue(
				"CheckoutService addItemToCart API response status message is not a failure status message",
				addItemToCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_MESSAGE.toString(), true)
						.contains(successStatusMsg));

		AssertJUnit.assertTrue("CheckoutService addItemToCart API response status type is not a failure status type",
				addItemToCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
						.contains(successStatusType));

		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;

		System.out.println(
				"\nTime taken to execute - TestCase - CheckoutServiceTests_addItemToCart_verifyFailureStatusResponse : "
						+ timeTaken + " seconds\n");
		log.info(
				"\nTime taken to execute - TestCase - CheckoutServiceTests_addItemToCart_verifyFailureStatusResponse : "
						+ timeTaken + " seconds\n");
	}

	/**
	 * This Test Case used to invoke CheckoutService operationFetchCart API and
	 * verify for success response(200)
	 * 
	 * @param userName
	 * @param password
	 * @param successRespCode
	 */
	@Test(groups = { "Smoke", "Sanity", "FoxSanity", "ProdSanity", "Regression", "MiniRegression",
			"ExhaustiveRegression" }, dataProvider = "CartServiceDP_operationFetchCartDataProvider_validateAPIResponse", description = "1. hit IDP service to login"
					+ "\n 2. hit fetchCart API" + "\n 3.Check the service response must be 200")
	public void CartServiceTests_operationFetchCart_verifySuccessResponse(String userName, String password,
			String successRespCode) {
		long startTime = Calendar.getInstance().getTimeInMillis();
		RequestGenerator fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
		System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"
				+ fetchCartReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"
				+ fetchCartReqGen.respvalidate.returnresponseasstring());

		AssertJUnit.assertEquals("CheckoutService operationFetchCart API is not working",
				Integer.parseInt(successRespCode), fetchCartReqGen.response.getStatus());

		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;

		System.out.println(
				"\nTime taken to execute - TestCase - CheckoutServiceTests_operationFetchCart_verifySuccessResponse : "
						+ timeTaken + " seconds\n");
		log.info("\nTime taken to execute - TestCase - CheckoutServiceTests_operationFetchCart_verifySuccessResponse : "
				+ timeTaken + " seconds\n");

	}

	/**
	 * This Test Case used to invoke CheckoutService operationFetchCart API and
	 * verify for success status response
	 * 
	 * @param userName
	 * @param password
	 * @param successStatusCode
	 * @param successStatusMsg
	 * @param successStatusType
	 */
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression", "MiniRegression",
			"ExhaustiveRegression" }, dataProvider = "CartServiceDP_operationFetchCartDataProvider_positiveCases", description = "1. hit IDP service to login"
					+ "\n 2. hit fetchCart API"
					+ "\n 3. verify statusCode, statusMessage & statusType of response must be of Success Type")
	public void CartServiceTests_operationFetchCart_verifySuccessStatusResponse(String userName, String password,
			String successStatusCode, String successStatusMsg, String successStatusType) {
		long startTime = Calendar.getInstance().getTimeInMillis();
		RequestGenerator fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
		System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"
				+ fetchCartReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"
				+ fetchCartReqGen.respvalidate.returnresponseasstring());

		AssertJUnit.assertTrue(
				"CheckoutService operationFetchCart API response status code is not a success status code",
				fetchCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_CODE.toString(), true)
						.contains(successStatusCode));

		AssertJUnit.assertTrue(
				"CheckoutService operationFetchCart API response status message is not a success status message",
				fetchCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_MESSAGE.toString(), true)
						.contains(successStatusMsg));

		AssertJUnit.assertTrue(
				"CheckoutService operationFetchCart API response status type is not a success status type",
				fetchCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
						.contains(successStatusType));

		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;

		System.out.println(
				"\nTime taken to execute - TestCase - CheckoutServiceTests_operationFetchCart_verifySuccessStatusResponse : "
						+ timeTaken + " seconds\n");
		log.info(
				"\nTime taken to execute - TestCase - CheckoutServiceTests_operationFetchCart_verifySuccessStatusResponse : "
						+ timeTaken + " seconds\n");

	}

	// modified test-case by Shrinkhala/
	/**
	 * This Test Case used to invoke CheckoutService clearCart API and verify
	 * for success response(200)
	 * 
	 * @param userName
	 * @param password
	 * @param successRespCode
	 */
	@Test(groups = { "Sanity", "FoxSanity", "Regression", "MiniRegression",
			"ExhaustiveRegression" }, dataProvider = "CartServiceDP_clearCartDataProvider_validateAPIResponse", description = "1. hit IDP service and login the user. "
					+ "\n 2. hit fetchCart API. \n 3. if user's cart is empty then first add items to cart(\n a.hit Search Service API and extract some skuIDs"
					+ "\n b. Check if searched styleIds exists in user's cart or not. \n c. hit addItemToCart API giving skuId which doesnt exists in cart)"
					+ "\n 4. hit ClearCart API " + "\n 5.Check the service response must be 200")
	public void CartServiceTests_clearCart_verifySuccessResponse(String userName, String password,
			String successRespCode) {
		long startTime = Calendar.getInstance().getTimeInMillis();
		String addItemQuantity = "1";
		//userName ="testing421@myntra.com";
		RequestGenerator fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
		System.out.println("First fetch cart response!!!....");
		System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"
				+ fetchCartReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"
				+ fetchCartReqGen.respvalidate.returnresponseasstring());

		AssertJUnit.assertTrue("Error while invoking CheckoutService operationFetchCart API",
				fetchCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
						.contains(SUCCESS_STATUS_TYPE));

		if (fetchCartReqGen.respvalidate.NodeValue(CartNodes.CART_TOTAL_COUNT.toString(), true).equals(EMPTY_VALUE)) {
			List<Integer> styleIdList = checkoutTestsHelper.performSeachServiceToGetStyleIds(AC_SEARCH_PROD,
					AC_SEARCH_QTY, AC_SEARCH_RET_DOCS, AC_SEARCH_IS_FACET);

			if (!CollectionUtils.isEmpty(styleIdList)) {
				for (Integer styleId : styleIdList) {
					System.out.println("\nPrinting the StyleId : " + styleId);
					log.info("\nPrinting the StyleId : " + styleId);

					RequestGenerator getStyleDataReqGen = checkoutTestsHelper
							.performGetStyleDataOperation(String.valueOf(styleId));
					System.out.println("\nPrinting StyleService getStyleData API response :\n\n"
							+ getStyleDataReqGen.respvalidate.returnresponseasstring());
					log.info("\nPrinting StyleService getStyleData API response :\n\n"
							+ getStyleDataReqGen.respvalidate.returnresponseasstring());

					AssertJUnit.assertTrue("Error while getting style data", getStyleDataReqGen.respvalidate
							.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));

					String response = getStyleDataReqGen.respvalidate.returnresponseasstring();
					List<Integer> ids = JsonPath.read(response, "$.data..styleOptions[*].id");

					for (int i = 0; i < ids.size(); i++) {

						String skuId = JsonPath.read(response, "$.data..styleOptions[" + i + "].skuId").toString();
						String inventoryCount = JsonPath
								.read(response, "$.data..styleOptions[" + i + "].inventoryCount").toString();
						String available = JsonPath.read(response, "$.data..styleOptions[" + i + "].available")
								.toString();

						if (!inventoryCount.equals(EMPTY_VALUE) && available.equals(TRUE_VALUE)) {
							fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
							System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"
									+ fetchCartReqGen.respvalidate.returnresponseasstring());
							log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"
									+ fetchCartReqGen.respvalidate.returnresponseasstring());

							if (StringUtils.isBlank(checkoutTestsHelper.getItemIdFromSKUId(fetchCartReqGen, skuId,
									CheckoutDataProviderEnum.CART.name()))) {
								RequestGenerator addItemToCartReqGen = checkoutTestsHelper.addItemToCart(userName,
										password, skuId, String.valueOf(addItemQuantity), ADD_OPERATION);
								System.out.println("\nPrinting CheckoutService addItemToCart API response :\n\n"
										+ addItemToCartReqGen.respvalidate.returnresponseasstring());
								log.info("\nPrinting CheckoutService addItemToCart API response :\n\n"
										+ addItemToCartReqGen.respvalidate.returnresponseasstring());

								AssertJUnit.assertTrue("Error while invoking CheckoutService addItemToCart API",
										addItemToCartReqGen.respvalidate
												.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
												.contains(SUCCESS_STATUS_TYPE));

								System.out.println(
										"\n" + skuId + " is available and it doesn't exists in cart so added to cart");
								log.info("\n" + skuId + " is available and it doesn't exists in cart so added to cart");
								break;

							} else {
								System.out.println(
										"\n" + skuId + " is not added because this item already exists in cart");
								log.error("\n" + skuId + " is not added because this item already exists in cart");
							}

						} else {
							System.out.println(
									"\n" + skuId + " is not added because this item is currently out of stock");
							log.info("\n" + skuId + " is not added because this item is currently out of stock");
						}
					}
				}

			} else {
				System.out.println("\nError while invoking SearchService API");
				log.error("\nError while invoking SearchService API");
				AssertJUnit.fail("Error while invoking SearchService API");
			}
		}

		RequestGenerator clearCartReqGen = checkoutTestsHelper.clearCart(userName, password);
		System.out.println("\nPrinting CheckoutService clearCart API response :\n\n"
				+ clearCartReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService clearCart API response :\n\n"
				+ clearCartReqGen.respvalidate.returnresponseasstring());

		AssertJUnit.assertEquals("CheckoutService clearCart API is not working", Integer.parseInt(successRespCode),
				clearCartReqGen.response.getStatus());

		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;

		System.out
				.println("\nTime taken to execute - TestCase - CheckoutServiceTests_clearCart_verifySuccessResponse : "
						+ timeTaken + " seconds\n");
		log.info("\nTime taken to execute - TestCase - CheckoutServiceTests_clearCart_verifySuccessResponse : "
				+ timeTaken + " seconds\n");
	}

	/**
	 * This Test Case used to invoke CheckoutService clearCart API and verify
	 * for success status response
	 * 
	 * @param userName
	 * @param password
	 * @param successStatusCode
	 * @param successStatusMsg
	 * @param successStatusType
	 */
	@Test(groups = { "Sanity", "Regression", "MiniRegression",
			"ExhaustiveRegression" }, dataProvider = "CartServiceDP_clearCartDataProvider_positiveCases", description = "1. hit IDP service and login the user. "
					+ "\n 2. hit fetchCart API. \n 3. if user's cart is empty then first add items to cart(\n a.hit Search Service API and extract some skuIDs"
					+ "\n b. Check if searched styleIds exists in user's cart or not. \n c. hit addItemToCart API giving skuId which doesnt exists in cart)"
					+ "\n 4. hit ClearCart API "
					+ "\n 5. Verify statusCode, statusMessage & statusType of API response must be of success type")
	public void CartServiceTests_clearCart_verifySuccessStatusResponse(String userName, String password,
			String successStatusCode, String successStatusMsg, String successStatusType) {
		long startTime = Calendar.getInstance().getTimeInMillis();
		String addItemQuantity = "1";
		RequestGenerator fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
		System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"
				+ fetchCartReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"
				+ fetchCartReqGen.respvalidate.returnresponseasstring());

		AssertJUnit.assertTrue("Error while invoking CheckoutService operationFetchCart API",
				fetchCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
						.contains(SUCCESS_STATUS_TYPE));

		if (fetchCartReqGen.respvalidate.NodeValue(CartNodes.CART_TOTAL_COUNT.toString(), true).equals(EMPTY_VALUE)) {
			List<Integer> styleIdList = checkoutTestsHelper.performSeachServiceToGetStyleIds(UC_SEARCH_PROD,
					UC_SEARCH_QTY, UC_SEARCH_RET_DOCS, UC_SEARCH_IS_FACET);

			if (!CollectionUtils.isEmpty(styleIdList)) {
				for (Integer styleId : styleIdList) {
					System.out.println("\nPrinting the StyleId : " + styleId);
					log.info("\nPrinting the StyleId : " + styleId);

					RequestGenerator getStyleDataReqGen = checkoutTestsHelper
							.performGetStyleDataOperation(String.valueOf(styleId));
					System.out.println("\nPrinting StyleService API response :\n\n"
							+ getStyleDataReqGen.respvalidate.returnresponseasstring());
					log.info("\nPrinting StyleService API response :\n\n"
							+ getStyleDataReqGen.respvalidate.returnresponseasstring());

					AssertJUnit.assertTrue("Error while getting style data", getStyleDataReqGen.respvalidate
							.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));

					String response = getStyleDataReqGen.respvalidate.returnresponseasstring();
					List<Integer> ids = JsonPath.read(response, "$.data..styleOptions[*].id");

					for (int i = 0; i < ids.size(); i++) {
						String skuId = JsonPath.read(response, "$.data..styleOptions[" + i + "].skuId").toString();
						String inventoryCount = JsonPath
								.read(response, "$.data..styleOptions[" + i + "].inventoryCount").toString();
						String available = JsonPath.read(response, "$.data..styleOptions[" + i + "].available")
								.toString();

						if (!inventoryCount.equals(EMPTY_VALUE) && available.equals(TRUE_VALUE)) {
							fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
							System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"
									+ fetchCartReqGen.respvalidate.returnresponseasstring());
							log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"
									+ fetchCartReqGen.respvalidate.returnresponseasstring());

							if (StringUtils.isBlank(checkoutTestsHelper.getItemIdFromSKUId(fetchCartReqGen, skuId,
									CheckoutDataProviderEnum.CART.name()))) {
								RequestGenerator addItemToCartReqGen = checkoutTestsHelper.addItemToCart(userName,
										password, skuId, String.valueOf(addItemQuantity), ADD_OPERATION);
								System.out.println("\nPrinting CheckoutService addItemToCart API response :\n\n"
										+ addItemToCartReqGen.respvalidate.returnresponseasstring());
								log.info("\nPrinting CheckoutService addItemToCart API response :\n\n"
										+ addItemToCartReqGen.respvalidate.returnresponseasstring());

								AssertJUnit.assertTrue("Error while invoking CheckoutService addItemToCart API",
										addItemToCartReqGen.respvalidate
												.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
												.contains(SUCCESS_STATUS_TYPE));

								System.out.println(
										"\n" + skuId + " is available and it doesn't exists in cart so added to cart");
								log.info("\n" + skuId + " is available and it doesn't exists in cart so added to cart");
								break;

							} else {
								System.out.println(
										"\n" + skuId + " is not added because this item already exists in cart");
								log.error("\n" + skuId + " is not added because this item already exists in cart");
							}

						} else {
							System.out.println(
									"\n" + skuId + " is not added because this item is currently out of stock");
							log.info("\n" + skuId + " is not added because this item is currently out of stock");
						}
					}
				}

			} else {
				System.out.println("\nError while invoking SearchService getStyleData API");
				log.error("\nError while invoking SearchService getStyleData API");
				AssertJUnit.fail("Error while invoking SearchService getStyleData API");
			}
		}

		RequestGenerator clearCartReqGen = checkoutTestsHelper.clearCart(userName, password);
		System.out.println("\nPrinting CheckoutService clearCart API response :\n\n"
				+ clearCartReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService clearCart API response :\n\n"
				+ clearCartReqGen.respvalidate.returnresponseasstring());

		AssertJUnit.assertTrue("CheckoutService clearCart API response status code is not a success status code",
				fetchCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_CODE.toString(), true)
						.contains(successStatusCode));

		AssertJUnit.assertTrue("CheckoutService clearCart API response status message is not a success status message",
				fetchCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_MESSAGE.toString(), true)
						.contains(successStatusMsg));

		AssertJUnit.assertTrue("CheckoutService clearCart API response status type is not a success status type",
				fetchCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
						.contains(successStatusType));

		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;

		System.out.println(
				"\nTime taken to execute - TestCase - CheckoutServiceTests_clearCart_verifySuccessStatusResponse : "
						+ timeTaken + " seconds\n");
		log.info("\nTime taken to execute - TestCase - CheckoutServiceTests_clearCart_verifySuccessStatusResponse : "
				+ timeTaken + " seconds\n");

	}

	/* ^^modified test-cases Shrinkhala */
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
	@Test(groups = { "Sanity", "FoxSanity", "Regression", "MiniRegression",
			"ExhaustiveRegression" }, dataProvider = "CartServiceDP_updateItemInCartDataProvider_validateAPIResponse", description = "1. hit IDP service and login the user. "
					+ "\n 2. hit fetchCart API. \n 3. if user's cart is empty then first add items to cart(\n a.hit Search Service API and extract some skuIDs"
					+ "\n b. Check if searched styleIds exists in user's cart or not. \n c. hit addItemToCart API giving skuId which doesnt exists in cart)"
					+ "\n 4. hit updateCart API" + "\n 5.Check the service response must be 200")
	public void CartServiceTests_updateItemInCart_verifySuccessResponse(String userName, String password,
			String itemQtyToUpdate, String updateOpn, String successRespCode) {
		long startTime = Calendar.getInstance().getTimeInMillis();
		String addItemQty = "1", styleID = "";
		Boolean itemAdded = false;
		int inv = 0;

		RequestGenerator fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
		System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"
				+ fetchCartReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"
				+ fetchCartReqGen.respvalidate.returnresponseasstring());

		AssertJUnit.assertTrue("Error while invoking CheckoutService operationFetchCart API",
				fetchCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
						.contains(SUCCESS_STATUS_TYPE));

		if (fetchCartReqGen.respvalidate.NodeValue(CartNodes.CART_TOTAL_COUNT.toString(), true).equals(EMPTY_VALUE)) {
			List<Integer> styleIdList = checkoutTestsHelper.performSeachServiceToGetStyleIds(UC_SEARCH_PROD,
					UC_SEARCH_QTY, UC_SEARCH_RET_DOCS, UC_SEARCH_IS_FACET);

			if (!CollectionUtils.isEmpty(styleIdList)) {
				for (Integer styleId : styleIdList) {
					if (itemAdded)
						break;
					System.out.println("\nPrinting the StyleId : " + styleId);
					log.info("\nPrinting the StyleId : " + styleId);

					RequestGenerator getStyleDataReqGen = checkoutTestsHelper
							.performGetStyleDataOperation(String.valueOf(styleId));
					System.out.println("\nTo Test inventory::Printing StyleService getStyleData API response :\n\n"
							+ getStyleDataReqGen.respvalidate.returnresponseasstring());
					log.info("\nPrinting StyleService getStyleData API response :\n\n"
							+ getStyleDataReqGen.respvalidate.returnresponseasstring());

					AssertJUnit.assertTrue("Error while getting style data", getStyleDataReqGen.respvalidate
							.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));

					String response = getStyleDataReqGen.respvalidate.returnresponseasstring();
					List<Integer> ids = JsonPath.read(response, "$.data..styleOptions[*].id");

					for (int i = 0; i < ids.size(); i++) {

						String skuId = JsonPath.read(response, "$.data..styleOptions[" + i + "].skuId").toString();
						styleID = JsonPath.read(response, "$.data..styleOptions[" + i + "].skuId").toString();
						String inventoryCount = JsonPath
								.read(response, "$.data..styleOptions[" + i + "].inventoryCount").toString();
						System.out.println("Actual inventory count:" + inventoryCount);
						String available = JsonPath.read(response, "$.data..styleOptions[" + i + "].available")
								.toString();
						inv = JsonPath.read(response, "$.data..styleOptions[" + i + "].inventoryCount");
						if (inv < 5)
							break;
						if (!inventoryCount.equals(EMPTY_VALUE) && available.equals(TRUE_VALUE)) {
							fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
							System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"
									+ fetchCartReqGen.respvalidate.returnresponseasstring());
							log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"
									+ fetchCartReqGen.respvalidate.returnresponseasstring());

							if (StringUtils.isBlank(checkoutTestsHelper.getItemIdFromSKUId(fetchCartReqGen, skuId,
									CheckoutDataProviderEnum.CART.name()))) {
								RequestGenerator addItemToCartReqGen = checkoutTestsHelper.addItemToCart(userName,
										password, skuId, String.valueOf(addItemQty), ADD_OPERATION);
								System.out.println("\nPrinting CheckoutService addItemToCart API response :\n\n"
										+ addItemToCartReqGen.respvalidate.returnresponseasstring());
								log.info("\nPrinting CheckoutService addItemToCart API response :\n\n"
										+ addItemToCartReqGen.respvalidate.returnresponseasstring());
								itemAdded = true;
								AssertJUnit.assertTrue("Error while invoking CheckoutService addItemToCart API",
										addItemToCartReqGen.respvalidate
												.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
												.contains(SUCCESS_STATUS_TYPE));

								System.out.println(
										"\n" + skuId + " is available and it doesn't exists in cart so added to cart");
								log.info("\n" + skuId + " is available and it doesn't exists in cart so added to cart");
								break;

							} else {
								System.out.println(
										"\n" + skuId + " is not added because this item already exists in cart");
								log.error("\n" + skuId + " is not added because this item already exists in cart");
							}

						} else {
							System.out.println(
									"\n" + skuId + " is not added because this item is currently out of stock");
							log.info("\n" + skuId + " is not added because this item is currently out of stock");
						}

					}
				}

			} else {
				System.out.println("\nError while invoking SearchService API");
				log.error("\nError while invoking SearchService API");
				AssertJUnit.fail("Error while invoking SearchService API");
			}

			// adding item to cart if none of the items have inventory >=5

			if (!itemAdded) {
				System.out.println("adding item to cart if none of the items have inventory >=5");
				System.out.println("adding item to cart with skuid:" + styleID + "of inventory count" + inv);
				RequestGenerator addItemToCartReqGen = checkoutTestsHelper.addItemToCart(userName, password, styleID,
						String.valueOf(addItemQty), ADD_OPERATION);
				System.out.println("\nPrinting CheckoutService addItemToCart API response :\n\n"
						+ addItemToCartReqGen.respvalidate.returnresponseasstring());
				log.info("\nPrinting CheckoutService addItemToCart API response :\n\n"
						+ addItemToCartReqGen.respvalidate.returnresponseasstring());
				itemAdded = true;
			}

			fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
			System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"
					+ fetchCartReqGen.respvalidate.returnresponseasstring());
			log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"
					+ fetchCartReqGen.respvalidate.returnresponseasstring());

			AssertJUnit.assertTrue("Error while invoking CheckoutService operationFetchCart API",
					fetchCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
							.contains(SUCCESS_STATUS_TYPE));
		}

		String skuId = fetchCartReqGen.respvalidate.NodeValue(CartNodes.CART_ITEM_ENTRIES_SELSIZE_SKUID.toString(),
				false);
		String itemId = checkoutTestsHelper.getItemIdFromSKUId(fetchCartReqGen, skuId,
				CheckoutDataProviderEnum.CART.name());
		String availableCount = fetchCartReqGen.respvalidate
				.NodeValue(CartNodes.CART_ITEM_ENTRIES_SELSIZE_AVAIL_QTY.toString(), false);

		inv = Integer.parseInt(availableCount);
		System.out.println("available count" + inv);
		int updateQty = Integer.parseInt(itemQtyToUpdate);

		if (inv < Integer.parseInt(itemQtyToUpdate)) {
			System.out.println("As the inventory count of styleId " + skuId + "is " + inv
					+ " which is less than ItemQtyToUpdate" + updateQty + "so updating item qty to inv count value");
		}

		updateQty = inv <= updateQty ? inv : updateQty;
		System.out.println("Update item qty is:" + updateQty);

		System.out.println("\nPrinting skuId : " + skuId + " & itemId : " + itemId + " & quantity : " + updateQty
				+ " is going for updation\n");
		log.info("\nPrinting skuId : " + skuId + " & itemId : " + itemId + " & quantity : " + updateQty
				+ " is going for updation\n");

		RequestGenerator updateItemInCartReqGen = checkoutTestsHelper.updateItemInCart(userName, password, itemId,
				skuId, String.valueOf(updateQty), updateOpn);
		System.out.println("\nPrinting CheckoutService updateItemInCart API response :\n\n"
				+ updateItemInCartReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService updateItemInCart API response :\n\n"
				+ updateItemInCartReqGen.respvalidate.returnresponseasstring());

		AssertJUnit.assertEquals("CheckoutService updateItemInCart API is not working",
				Integer.parseInt(successRespCode), updateItemInCartReqGen.response.getStatus());

		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;

		System.out.println(
				"\nTime taken to execute - TestCase - CheckoutServiceTests_updateItemInCart_verifySuccessResponse : "
						+ timeTaken + " seconds\n");
		log.info("\nTime taken to execute - TestCase - CheckoutServiceTests_updateItemInCart_verifySuccessResponse : "
				+ timeTaken + " seconds\n");

	}

	/**
	 * This Test Case used to invoke CheckoutService updateItemInCart API and
	 * verify for success status response
	 * 
	 * @param userName
	 * @param password
	 * @param itemQtyToUpdate
	 * @param updateOpn
	 * @param successStatusCode
	 * @param successStatusMsg
	 * @param successStatusType
	 */
	@Test(groups = { "Sanity", "Regression", "MiniRegression",
			"ExhaustiveRegression" }, dataProvider = "CartServiceDP_updateItemInCartDataProvider_positiveCases", description = "1. hit IDP service and login the user. "
					+ "\n 2. hit fetchCart API. \n 3. if user's cart is empty then first add items to cart(\n a.hit Search Service API and extract some skuIDs"
					+ "\n b. Check if searched styleIds exists in user's cart or not. \n c. hit addItemToCart API giving skuId which doesnt exists in cart)"
					+ "\n 4. hit updateCart API"
					+ "\n 5. Verify statusCode, statusMessage & statusType of response must be of SuccessType")
	public void CartServiceTests_updateItemInCart_verifySuccessStatusResponse(String userName, String password,
			String itemQtyToUpdate, String updateOpn, String successStatusCode, String successStatusMsg,
			String successStatusType) {
		long startTime = Calendar.getInstance().getTimeInMillis();
		RequestGenerator fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
		System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"
				+ fetchCartReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"
				+ fetchCartReqGen.respvalidate.returnresponseasstring());

		AssertJUnit.assertTrue("Error while invoking CheckoutService operationFetchCart API",
				fetchCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
						.contains(SUCCESS_STATUS_TYPE));

		if (fetchCartReqGen.respvalidate.NodeValue(CartNodes.CART_TOTAL_COUNT.toString(), true).equals(EMPTY_VALUE)) {
			List<Integer> styleIdList = checkoutTestsHelper.performSeachServiceToGetStyleIds(UC_SEARCH_PROD,
					UC_SEARCH_QTY, UC_SEARCH_RET_DOCS, UC_SEARCH_IS_FACET);

			if (!CollectionUtils.isEmpty(styleIdList)) {
				for (Integer styleId : styleIdList) {
					System.out.println("\nPrinting the StyleId : " + styleId);
					log.info("\nPrinting the StyleId : " + styleId);

					RequestGenerator getStyleDataReqGen = checkoutTestsHelper
							.performGetStyleDataOperation(String.valueOf(styleId));
					System.out.println("\nPrinting StyleService getStyleData API response :\n\n"
							+ getStyleDataReqGen.respvalidate.returnresponseasstring());
					log.info("\nPrinting StyleService getStyleData API response :\n\n"
							+ getStyleDataReqGen.respvalidate.returnresponseasstring());

					AssertJUnit.assertTrue("Error while getting style data", getStyleDataReqGen.respvalidate
							.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));

					String response = getStyleDataReqGen.respvalidate.returnresponseasstring();
					List<Integer> ids = JsonPath.read(response, "$.data..styleOptions[*].id");

					for (int i = 0; i < ids.size(); i++) {
						String skuId = JsonPath.read(response, "$.data..styleOptions[" + i + "].skuId").toString();
						String inventoryCount = JsonPath
								.read(response, "$.data..styleOptions[" + i + "].inventoryCount").toString();
						String available = JsonPath.read(response, "$.data..styleOptions[" + i + "].available")
								.toString();

						if (!inventoryCount.equals(EMPTY_VALUE) && available.equals(TRUE_VALUE)) {
							fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
							System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"
									+ fetchCartReqGen.respvalidate.returnresponseasstring());
							log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"
									+ fetchCartReqGen.respvalidate.returnresponseasstring());

							if (StringUtils.isBlank(checkoutTestsHelper.getItemIdFromSKUId(fetchCartReqGen, skuId,
									CheckoutDataProviderEnum.CART.name()))) {
								RequestGenerator addItemToCartReqGen = checkoutTestsHelper.addItemToCart(userName,
										password, skuId, inventoryCount, ADD_OPERATION);
								System.out.println("\nPrinting CheckoutService addItemToCart API response :\n\n"
										+ addItemToCartReqGen.respvalidate.returnresponseasstring());
								log.info("\nPrinting CheckoutService addItemToCart API response :\n\n"
										+ addItemToCartReqGen.respvalidate.returnresponseasstring());

								AssertJUnit.assertTrue("Error while invoking CheckoutService addItemToCart API",
										addItemToCartReqGen.respvalidate
												.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
												.contains(SUCCESS_STATUS_TYPE));

								System.out.println(
										"\n" + skuId + " is available and it doesn't exists in cart so added to cart");
								log.info("\n" + skuId + " is available and it doesn't exists in cart so added to cart");
								break;

							} else {
								System.out.println(
										"\n" + skuId + " is not added because this item already exists in cart");
								log.error("\n" + skuId + " is not added because this item already exists in cart");
							}

						} else {
							System.out.println(
									"\n" + skuId + " is not added because this item is currently out of stock");
							log.info("\n" + skuId + " is not added because this item is currently out of stock");
						}
					}
				}

			} else {
				System.out.println("\nError while invoking SearchService getStyleData API");
				log.error("\nError while invoking SearchService getStyleData API");
				AssertJUnit.fail("Error while invoking SearchService getStyleData API");
			}

			fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
			System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"
					+ fetchCartReqGen.respvalidate.returnresponseasstring());
			log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"
					+ fetchCartReqGen.respvalidate.returnresponseasstring());

			AssertJUnit.assertTrue("Error while invoking CheckoutService operationFetchCart API",
					fetchCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
							.contains(SUCCESS_STATUS_TYPE));
		}
		String skuId = fetchCartReqGen.respvalidate.NodeValue(CartNodes.CART_ITEM_ENTRIES_SELSIZE_SKUID.toString(),
				false);
		String itemId = checkoutTestsHelper.getItemIdFromSKUId(fetchCartReqGen, skuId,
				CheckoutDataProviderEnum.CART.name());

		System.out.println("\nPrinting skuId : " + skuId + " & itemId : " + itemId + " & quantity : " + itemQtyToUpdate
				+ " is going for updation\n");
		log.info("\nPrinting skuId : " + skuId + " & itemId : " + itemId + " & quantity : " + itemQtyToUpdate
				+ " is going for updation\n");

		RequestGenerator updateItemInCartReqGen = checkoutTestsHelper.updateItemInCart(userName, password, itemId,
				skuId, String.valueOf(itemQtyToUpdate), updateOpn);
		System.out.println("\nPrinting CheckoutService updateItemInCart API response :\n\n"
				+ updateItemInCartReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService updateItemInCart API response :\n\n"
				+ updateItemInCartReqGen.respvalidate.returnresponseasstring());

		AssertJUnit.assertTrue("CheckoutService updateItemInCart API response status code is not a success status code",
				updateItemInCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_CODE.toString(), true)
						.contains(successStatusCode));

		AssertJUnit.assertTrue(
				"CheckoutService updateItemInCart API response status message is not a success status message",
				updateItemInCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_MESSAGE.toString(), true)
						.contains(successStatusMsg));

		AssertJUnit.assertTrue("CheckoutService updateItemInCart API response status type is not a success status type",
				updateItemInCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
						.contains(successStatusType));

		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;

		System.out.println(
				"\nTime taken to execute - TestCase - CheckoutServiceTests_updateItemInCart_verifySuccessStatusResponse : "
						+ timeTaken + " seconds\n");
		log.info(
				"\nTime taken to execute - TestCase - CheckoutServiceTests_updateItemInCart_verifySuccessStatusResponse : "
						+ timeTaken + " seconds\n");
	}

	/**
	 * This Test Case used to invoke CheckoutService updateItemInCart API and
	 * verify for failure status response
	 * 
	 * @param userName
	 * @param password
	 * @param itemQtyToUpdate
	 * @param updateOpn
	 * @param failureStatusCode
	 * @param failureStatusMsg
	 * @param failureStatusType
	 */
	@Test(groups = { "Sanity", "Regression", "MiniRegression",
			"ExhaustiveRegression" }, dataProvider = "CartServiceDP_updateItemInCartDataProvider_negetiveCases", description = "1. hit IDP service and login the user. "
					+ "\n 2. hit fetchCart API. \n 3. if user's cart is empty then first add items to cart(\n a.hit Search Service API and extract some skuIDs"
					+ "\n b. Check if searched styleIds exists in user's cart or not. \n c. hit addItemToCart API giving skuId which doesnt exists in cart)"
					+ "\n 4. hit updateCart API"
					+ "\n 5. Verify statusCode, statusMessage & statusType of response must be of Failure Type")
	public void CartServiceTests_updateItemInCart_verifyFailureStatusResponse(String userName, String password,
			String itemQtyToUpdate, String updateOpn, String failureStatusCode, String failureStatusMsg,
			String failureStatusType) {
		long startTime = Calendar.getInstance().getTimeInMillis();
		RequestGenerator fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
		System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"
				+ fetchCartReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"
				+ fetchCartReqGen.respvalidate.returnresponseasstring());

		AssertJUnit.assertTrue("Error while invoking CheckoutService operationFetchCart API",
				fetchCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
						.contains(SUCCESS_STATUS_TYPE));

		if (fetchCartReqGen.respvalidate.NodeValue(CartNodes.CART_TOTAL_COUNT.toString(), true).equals(EMPTY_VALUE)) {
			List<Integer> styleIdList = checkoutTestsHelper.performSeachServiceToGetStyleIds(UC_SEARCH_PROD,
					UC_SEARCH_QTY, UC_SEARCH_RET_DOCS, UC_SEARCH_IS_FACET);

			if (!CollectionUtils.isEmpty(styleIdList)) {
				for (Integer styleId : styleIdList) {
					System.out.println("\nPrinting the StyleId : " + styleId);
					log.info("\nPrinting the StyleId : " + styleId);

					RequestGenerator getStyleDataReqGen = checkoutTestsHelper
							.performGetStyleDataOperation(String.valueOf(styleId));
					System.out.println("\nPrinting StyleService getStyleData API response :\n\n"
							+ getStyleDataReqGen.respvalidate.returnresponseasstring());
					log.info("\nPrinting StyleService API getStyleData response :\n\n"
							+ getStyleDataReqGen.respvalidate.returnresponseasstring());

					AssertJUnit.assertTrue("Error while getting style data", getStyleDataReqGen.respvalidate
							.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));

					String response = getStyleDataReqGen.respvalidate.returnresponseasstring();
					List<Integer> ids = JsonPath.read(response, "$.data..styleOptions[*].id");

					for (int i = 0; i < ids.size(); i++) {
						String skuId = JsonPath.read(response, "$.data..styleOptions[" + i + "].skuId").toString();
						String inventoryCount = JsonPath
								.read(response, "$.data..styleOptions[" + i + "].inventoryCount").toString();
						String available = JsonPath.read(response, "$.data..styleOptions[" + i + "].available")
								.toString();

						if (!inventoryCount.equals(EMPTY_VALUE) && available.equals(TRUE_VALUE)) {
							fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
							System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"
									+ fetchCartReqGen.respvalidate.returnresponseasstring());
							log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"
									+ fetchCartReqGen.respvalidate.returnresponseasstring());

							if (StringUtils.isBlank(checkoutTestsHelper.getItemIdFromSKUId(fetchCartReqGen, skuId,
									CheckoutDataProviderEnum.CART.name()))) {
								RequestGenerator addItemToCartReqGen = checkoutTestsHelper.addItemToCart(userName,
										password, skuId, inventoryCount, ADD_OPERATION);
								System.out.println("\nPrinting CheckoutService addItemToCart API response :\n\n"
										+ addItemToCartReqGen.respvalidate.returnresponseasstring());
								log.info("\nPrinting CheckoutService addItemToCart API response :\n\n"
										+ addItemToCartReqGen.respvalidate.returnresponseasstring());

								AssertJUnit.assertTrue("Error while invoking CheckoutService addItemToCart API",
										addItemToCartReqGen.respvalidate
												.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
												.contains(SUCCESS_STATUS_TYPE));

								System.out.println(
										"\n" + skuId + " is available and it doesn't exists in cart so added to cart");
								log.info("\n" + skuId + " is available and it doesn't exists in cart so added to cart");
								break;
							} else {
								System.out.println(
										"\n" + skuId + " is not added because this item already exists in cart");
								log.error("\n" + skuId + " is not added because this item already exists in cart");
							}

						} else {
							System.out.println(
									"\n" + skuId + " is not added because this item is currently out of stock");
							log.info("\n" + skuId + " is not added because this item is currently out of stock");
						}
					}
				}

			} else {
				System.out.println("\nError while invoking SearchService getStyleData API");
				log.error("\nError while invoking SearchService getStyleData API");
				AssertJUnit.fail("Error while invoking SearchService getStyleData API");
			}

			fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
			System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"
					+ fetchCartReqGen.respvalidate.returnresponseasstring());
			log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"
					+ fetchCartReqGen.respvalidate.returnresponseasstring());

			AssertJUnit.assertTrue("Error while invoking CheckoutService operationFetchCart API",
					fetchCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
							.contains(SUCCESS_STATUS_TYPE));

		}
		String skuId = fetchCartReqGen.respvalidate.NodeValue(CartNodes.CART_ITEM_ENTRIES_SELSIZE_SKUID.toString(),
				false);
		String itemId = checkoutTestsHelper.getItemIdFromSKUId(fetchCartReqGen, skuId,
				CheckoutDataProviderEnum.CART.name());

		System.out.println("\nPrinting skuId : " + skuId + " & itemId : " + itemId + " & quantity : " + itemQtyToUpdate
				+ " is going for updation\n");
		log.info("\nPrinting skuId : " + skuId + " & itemId : " + itemId + " & quantity : " + itemQtyToUpdate
				+ " is going for updation\n");

		RequestGenerator updateItemInCartReqGen = checkoutTestsHelper.updateItemInCart(userName, password, itemId,
				skuId, String.valueOf(itemQtyToUpdate), updateOpn);
		System.out.println("\nPrinting CheckoutService updateItemInCart API response :\n\n"
				+ updateItemInCartReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService updateItemInCart API response :\n\n"
				+ updateItemInCartReqGen.respvalidate.returnresponseasstring());

		AssertJUnit.assertTrue("CheckoutService updateItemInCart API response status code is not a failure status code",
				updateItemInCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_CODE.toString(), true)
						.contains(failureStatusCode));

		AssertJUnit.assertTrue(
				"CheckoutService updateItemInCart API response status message is not a failure status message",
				updateItemInCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_MESSAGE.toString(), true)
						.contains(failureStatusMsg));

		AssertJUnit.assertTrue("CheckoutService updateItemInCart API response status type is not a failure status type",
				updateItemInCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
						.contains(failureStatusType));

		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;

		System.out.println(
				"\nTime taken to execute - TestCase - CheckoutServiceTests_updateItemInCart_verifyFailureStatusResponse : "
						+ timeTaken + " seconds\n");
		log.info(
				"\nTime taken to execute - TestCase - CheckoutServiceTests_updateItemInCart_verifyFailureStatusResponse : "
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
			"ExhaustiveRegression" }, dataProvider = "CartServiceDP_removeItemFromCartDataProvider_validateAPIResponse", description = "1. hit IDP service and login the user. "
					+ "\n 2. hit fetchCart API. \n 3. if user's cart is empty then first add items to cart(\n a.hit Search Service API and extract some skuIDs"
					+ "\n b. Check if searched styleIds exists in user's cart or not. \n c. hit addItemToCart API giving skuId which doesnt exists in cart)"
					+ "\n 4. hit removeItemFromCart API giving it itemId to be removed"
					+ "\n 5. Verify statusCode must be 200 in APIs response")
	public void CartServiceTests_removeItemFromCart_verifySuccessResponse(String userName, String password,
			String deleteOpn, String successRespCode) {
		String addItemQty = "1";
		long startTime = Calendar.getInstance().getTimeInMillis();
		RequestGenerator fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
		System.out.println("\nPrinting CheckoutService  operationFetchCart API response :\n\n"
				+ fetchCartReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"
				+ fetchCartReqGen.respvalidate.returnresponseasstring());

		AssertJUnit.assertTrue("Error while invoking CheckoutService operationFetchCart API",
				fetchCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
						.contains(SUCCESS_STATUS_TYPE));

		if (fetchCartReqGen.respvalidate.NodeValue(CartNodes.CART_TOTAL_COUNT.toString(), true).equals(EMPTY_VALUE)) {
			List<Integer> styleIdList = checkoutTestsHelper.performSeachServiceToGetStyleIds(RC_SEARCH_PROD,
					RC_SEARCH_QTY, RC_SEARCH_RET_DOCS, RC_SEARCH_IS_FACET);

			if (!CollectionUtils.isEmpty(styleIdList)) {
				for (Integer styleId : styleIdList) {
					System.out.println("\nPrinting the StyleId : " + styleId);
					log.info("\nPrinting the StyleId : " + styleId);

					RequestGenerator getStyleDataReqGen = checkoutTestsHelper
							.performGetStyleDataOperation(String.valueOf(styleId));
					System.out.println("\nPrinting StyleService getStyleData API response :\n\n"
							+ getStyleDataReqGen.respvalidate.returnresponseasstring());
					log.info("\nPrinting StyleService getStyleData API response :\n\n"
							+ getStyleDataReqGen.respvalidate.returnresponseasstring());

					AssertJUnit.assertTrue("Error while getting style data", getStyleDataReqGen.respvalidate
							.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));

					String response = getStyleDataReqGen.respvalidate.returnresponseasstring();
					List<Integer> ids = JsonPath.read(response, "$.data..styleOptions[*].id");

					for (int i = 0; i < ids.size(); i++) {
						String skuId = JsonPath.read(response, "$.data..styleOptions[" + i + "].skuId").toString();
						String inventoryCount = JsonPath
								.read(response, "$.data..styleOptions[" + i + "].inventoryCount").toString();
						String available = JsonPath.read(response, "$.data..styleOptions[" + i + "].available")
								.toString();

						if (!inventoryCount.equals(EMPTY_VALUE) && available.equals(TRUE_VALUE)) {
							fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
							System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"
									+ fetchCartReqGen.respvalidate.returnresponseasstring());
							log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"
									+ fetchCartReqGen.respvalidate.returnresponseasstring());

							if (StringUtils.isBlank(checkoutTestsHelper.getItemIdFromSKUId(fetchCartReqGen, skuId,
									CheckoutDataProviderEnum.CART.name()))) {
								RequestGenerator addItemToCartReqGen = checkoutTestsHelper.addItemToCart(userName,
										password, skuId, String.valueOf(addItemQty), ADD_OPERATION);
								System.out.println("\nPrinting CheckoutService addItemToCart API response :\n\n"
										+ addItemToCartReqGen.respvalidate.returnresponseasstring());
								log.info("\nPrinting CheckoutService addItemToCart API response :\n\n"
										+ addItemToCartReqGen.respvalidate.returnresponseasstring());

								AssertJUnit.assertTrue("Error while invoking CheckoutService addItemToCart API",
										addItemToCartReqGen.respvalidate
												.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
												.contains(SUCCESS_STATUS_TYPE));

								System.out.println(
										"\n" + skuId + " is available and it doesn't exists in cart so added to cart");
								log.info("\n" + skuId + " is available and it doesn't exists in cart so added to cart");
								break;

							} else {
								System.out.println(
										"\n" + skuId + " is not added because this item already exists in cart");
								log.error("\n" + skuId + " is not added because this item already exists in cart");
							}

						} else {
							System.out.println(
									"\n" + skuId + " is not added because this item is currently out of stock");
							log.info("\n" + skuId + " is not added because this item is currently out of stock");
						}
					}
				}

			} else {
				System.out.println("\nError while invoking SearchService getStyleData API");
				log.error("\nError while invoking SearchService getStyleData API");
				AssertJUnit.fail("Error while invoking SearchService getStyleData API");
			}

			fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
			System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"
					+ fetchCartReqGen.respvalidate.returnresponseasstring());
			log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"
					+ fetchCartReqGen.respvalidate.returnresponseasstring());

			AssertJUnit.assertTrue("Error while invoking CheckoutService operationFetchCart API",
					fetchCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
							.contains(SUCCESS_STATUS_TYPE));

		}
		String skuId = fetchCartReqGen.respvalidate.NodeValue(CartNodes.CART_ITEM_ENTRIES_SELSIZE_SKUID.toString(),
				false);
		String itemId = checkoutTestsHelper.getItemIdFromSKUId(fetchCartReqGen, skuId,
				CheckoutDataProviderEnum.CART.name());

		System.out
				.println("\nPrinting skuId : " + skuId + " & itemId : " + itemId + " is going to removeItemFromCart\n");
		log.info("\nPrinting skuId : " + skuId + " & itemId : " + itemId + " is going for removeItemFromCart API\n");

		RequestGenerator removeItemFromCartReqGen = checkoutTestsHelper.removeItemFromCart(userName, password, itemId,
				deleteOpn);
		System.out.println("\nPrinting CheckoutService removeItemFromCart API response :\n\n"
				+ removeItemFromCartReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService removeItemFromCart API response :\n\n"
				+ removeItemFromCartReqGen.respvalidate.returnresponseasstring());

		AssertJUnit.assertEquals("CheckoutService removeItemFromCart API is not working",
				Integer.parseInt(successRespCode), removeItemFromCartReqGen.response.getStatus());

		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;

		System.out.println(
				"\nTime taken to execute - TestCase - CheckoutServiceTests_removeItemFromCart_verifySuccessResponse : "
						+ timeTaken + " seconds\n");
		log.info("\nTime taken to execute - TestCase - CheckoutServiceTests_removeItemFromCart_verifySuccessResponse : "
				+ timeTaken + " seconds\n");
	}

	/**
	 * This Test Case used to invoke CheckoutService removeItemFrom API and
	 * verify for success status response
	 * 
	 * @param userName
	 * @param password
	 * @param deleteOpn
	 * @param successStatusCode
	 * @param successStatusMsg
	 * @param successStatusType
	 */
	@Test(groups = { "Sanity", "Regression", "MiniRegression",
			"ExhaustiveRegression" }, dataProvider = "CartServiceDP_removeItemFromCartDataProvider_positiveCases", description = "1. hit IDP service and login the user. "
					+ "\n 2. hit fetchCart API. \n 3. if user's cart is empty then first add items to cart(\n a.hit Search Service API and extract some skuIDs"
					+ "\n b. Check if searched styleIds exists in user's cart or not. \n c. hit addItemToCart API giving skuId which doesnt exists in cart)"
					+ "\n 4. hit removeItemFromCart API giving it itemId to be removed"
					+ "\n 5. Verify statusCode, statusMessage & statusType of APIs response must be of success")
	public void CartServiceTests_removeItemFromCart_verifySuccessStatusResponse(String userName, String password,
			String deleteOpn, String successStatusCode, String successStatusMsg, String successStatusType) {
		long startTime = Calendar.getInstance().getTimeInMillis();
		String addItemQty = "1";
		RequestGenerator fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
		System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"
				+ fetchCartReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"
				+ fetchCartReqGen.respvalidate.returnresponseasstring());

		AssertJUnit.assertTrue("Error while invoking CheckoutService operationFetchCart API",
				fetchCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
						.contains(SUCCESS_STATUS_TYPE));

		if (fetchCartReqGen.respvalidate.NodeValue(CartNodes.CART_TOTAL_COUNT.toString(), true).equals(EMPTY_VALUE)) {
			List<Integer> styleIdList = checkoutTestsHelper.performSeachServiceToGetStyleIds(RC_SEARCH_PROD,
					RC_SEARCH_QTY, RC_SEARCH_RET_DOCS, RC_SEARCH_IS_FACET);

			if (!CollectionUtils.isEmpty(styleIdList)) {
				for (Integer styleId : styleIdList) {
					System.out.println("\nPrinting the StyleId : " + styleId);
					log.info("\nPrinting the StyleId : " + styleId);

					RequestGenerator getStyleDataReqGen = checkoutTestsHelper
							.performGetStyleDataOperation(String.valueOf(styleId));
					System.out.println("\nPrinting StyleService getStyleData API response :\n\n"
							+ getStyleDataReqGen.respvalidate.returnresponseasstring());
					log.info("\nPrinting StyleService getStyleData API response :\n\n"
							+ getStyleDataReqGen.respvalidate.returnresponseasstring());

					AssertJUnit.assertTrue("Error while getting style data", getStyleDataReqGen.respvalidate
							.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));

					String response = getStyleDataReqGen.respvalidate.returnresponseasstring();
					List<Integer> ids = JsonPath.read(response, "$.data..styleOptions[*].id");

					for (int i = 0; i < ids.size(); i++) {
						String skuId = JsonPath.read(response, "$.data..styleOptions[" + i + "].skuId").toString();
						String inventoryCount = JsonPath
								.read(response, "$.data..styleOptions[" + i + "].inventoryCount").toString();
						String available = JsonPath.read(response, "$.data..styleOptions[" + i + "].available")
								.toString();

						if (!inventoryCount.equals(EMPTY_VALUE) && available.equals(TRUE_VALUE)) {
							fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
							System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"
									+ fetchCartReqGen.respvalidate.returnresponseasstring());
							log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"
									+ fetchCartReqGen.respvalidate.returnresponseasstring());

							if (StringUtils.isBlank(checkoutTestsHelper.getItemIdFromSKUId(fetchCartReqGen, skuId,
									CheckoutDataProviderEnum.CART.name()))) {
								RequestGenerator addItemToCartReqGen = checkoutTestsHelper.addItemToCart(userName,
										password, skuId, String.valueOf(addItemQty), ADD_OPERATION);
								System.out.println("\nPrinting CheckoutService addItemToCart API response :\n\n"
										+ addItemToCartReqGen.respvalidate.returnresponseasstring());
								log.info("\nPrinting CheckoutService addItemToCart API response :\n\n"
										+ addItemToCartReqGen.respvalidate.returnresponseasstring());

								AssertJUnit.assertTrue("Error while invoking CheckoutService addItemToCart API",
										addItemToCartReqGen.respvalidate
												.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
												.contains(SUCCESS_STATUS_TYPE));

								System.out.println(
										"\n" + skuId + " is available and it doesn't exists in cart so added to cart");
								log.info("\n" + skuId + " is available and it doesn't exists in cart so added to cart");
								break;

							} else {
								System.out.println(
										"\n" + skuId + " is not added because this item already exists in cart");
								log.error("\n" + skuId + " is not added because this item already exists in cart");
							}

						} else {
							System.out.println(
									"\n" + skuId + " is not added because this item is currently out of stock");
							log.info("\n" + skuId + " is not added because this item is currently out of stock");
						}
					}
				}

			} else {
				System.out.println("\nError while invoking SearchService API");
				log.error("\nError while invoking SearchService API");
				AssertJUnit.fail("Error while invoking SearchService API");
			}

			fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
			System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"
					+ fetchCartReqGen.respvalidate.returnresponseasstring());
			log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"
					+ fetchCartReqGen.respvalidate.returnresponseasstring());

			AssertJUnit.assertTrue("Error while invoking CheckoutService operationFetchCart API",
					fetchCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
							.contains(SUCCESS_STATUS_TYPE));

		}
		String skuId = fetchCartReqGen.respvalidate.NodeValue(CartNodes.CART_ITEM_ENTRIES_SELSIZE_SKUID.toString(),
				false);
		String itemId = checkoutTestsHelper.getItemIdFromSKUId(fetchCartReqGen, skuId,
				CheckoutDataProviderEnum.CART.name());

		System.out
				.println("\nPrinting skuId : " + skuId + " & itemId : " + itemId + " is going to removeItemFromCart\n");
		log.info("\nPrinting skuId : " + skuId + " & itemId : " + itemId + " is going for removeItemFromCart API\n");

		RequestGenerator removeItemFromCartReqGen = checkoutTestsHelper.removeItemFromCart(userName, password, itemId,
				deleteOpn);
		System.out.println("\nPrinting removeItemFromCart API response :\n\n"
				+ removeItemFromCartReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting removeItemFromCart API response :\n\n"
				+ removeItemFromCartReqGen.respvalidate.returnresponseasstring());

		AssertJUnit.assertTrue("CheckoutService updateItemInCart API response status code is not a success status code",
				removeItemFromCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_CODE.toString(), true)
						.contains(successStatusCode));

		AssertJUnit.assertTrue(
				"CheckoutService updateItemInCart API response status message is not a success status message",
				removeItemFromCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_MESSAGE.toString(), true)
						.contains(successStatusMsg));

		AssertJUnit.assertTrue("CheckoutService updateItemInCart API response status type is not a success status type",
				removeItemFromCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
						.contains(successStatusType));

		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;

		System.out.println(
				"\nTime taken to execute - TestCase - CheckoutServiceTests_removeItemFromCart_verifySuccessStatusResponse : "
						+ timeTaken + " seconds\n");
		log.info(
				"\nTime taken to execute - TestCase - CheckoutServiceTests_removeItemFromCart_verifySuccessStatusResponse : "
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
			"ExhaustiveRegression" }, dataProvider = "CartServiceDP_addGiftWrapAndMessageDataProvider_validateAPIResponse", description = "1. hit IDP service and login the user. "
					+ "\n 2. hit fetchCart API. \n 3. if user's cart is empty then first add items to cart(\n a.hit Search Service API and extract some skuIDs"
					+ "\n b. Check if searched styleIds exists in user's cart or not. \n c. hit addItemToCart API giving skuId which doesnt exists in cart)"
					+ "\n 4. Check if giftwrap is already applied to cart remove it"
					+ "\n 5. hit addGiftWrap API passing valid data"
					+ "\n 6. Verify statusCode must be 200 in APIs response")
	public void CarttServiceTests_addGiftWrapAndMessage_verifySuccessResponse(String userName, String password,
			String senderName, String giftMsg, String recipientName, String respCode) {
		long startTime = Calendar.getInstance().getTimeInMillis();
		String addItemQty = "1";
		RequestGenerator fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
		System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"
				+ fetchCartReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"
				+ fetchCartReqGen.respvalidate.returnresponseasstring());

		AssertJUnit.assertTrue("Error while invoking CheckoutService operationFetchCart API",
				fetchCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
						.contains(SUCCESS_STATUS_TYPE));

		if (fetchCartReqGen.respvalidate.NodeValue(CartNodes.CART_TOTAL_COUNT.toString(), true).equals(EMPTY_VALUE)) {
			List<Integer> styleIdList = checkoutTestsHelper.performSeachServiceToGetStyleIds(AC_SEARCH_PROD,
					AC_SEARCH_QTY, AC_SEARCH_RET_DOCS, AC_SEARCH_IS_FACET);

			if (!CollectionUtils.isEmpty(styleIdList)) {
				for (Integer styleId : styleIdList) {
					System.out.println("\nPrinting the StyleId : " + styleId);
					log.info("\nPrinting the StyleId : " + styleId);

					RequestGenerator getStyleDataReqGen = checkoutTestsHelper
							.performGetStyleDataOperation(String.valueOf(styleId));
					System.out.println("\nPrinting StyleService getStyleData API response :\n\n"
							+ getStyleDataReqGen.respvalidate.returnresponseasstring());
					log.info("\nPrinting StyleService getStyleData API response :\n\n"
							+ getStyleDataReqGen.respvalidate.returnresponseasstring());

					AssertJUnit.assertTrue("Error while getting style data", getStyleDataReqGen.respvalidate
							.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));

					String response = getStyleDataReqGen.respvalidate.returnresponseasstring();
					List<Integer> ids = JsonPath.read(response, "$.data..styleOptions[*].id");

					for (int i = 0; i < ids.size(); i++) {
						String skuId = JsonPath.read(response, "$.data..styleOptions[" + i + "].skuId").toString();
						String inventoryCount = JsonPath
								.read(response, "$.data..styleOptions[" + i + "].inventoryCount").toString();
						String available = JsonPath.read(response, "$.data..styleOptions[" + i + "].available")
								.toString();

						if (!inventoryCount.equals(EMPTY_VALUE) && available.equals(TRUE_VALUE)) {
							fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
							System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"
									+ fetchCartReqGen.respvalidate.returnresponseasstring());
							log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"
									+ fetchCartReqGen.respvalidate.returnresponseasstring());

							if (StringUtils.isBlank(checkoutTestsHelper.getItemIdFromSKUId(fetchCartReqGen, skuId,
									CheckoutDataProviderEnum.CART.name()))) {
								RequestGenerator addItemToCartReqGen = checkoutTestsHelper.addItemToCart(userName,
										password, skuId, String.valueOf(addItemQty), ADD_OPERATION);
								System.out.println("\nPrinting CheckoutService addItemToCart API response :\n\n"
										+ addItemToCartReqGen.respvalidate.returnresponseasstring());
								log.info("\nPrinting CheckoutService addItemToCart API response :\n\n"
										+ addItemToCartReqGen.respvalidate.returnresponseasstring());

								AssertJUnit.assertTrue("Error while invoking CheckoutService addItemToCart API",
										addItemToCartReqGen.respvalidate
												.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
												.contains(SUCCESS_STATUS_TYPE));

								System.out.println(
										"\n" + skuId + " is available and it doesn't exists in cart so added to cart");
								log.info("\n" + skuId + " is available and it doesn't exists in cart so added to cart");
								break;

							} else {
								System.out.println(
										"\n" + skuId + " is not added because this item already exists in cart");
								log.error("\n" + skuId + " is not added because this item already exists in cart");
							}

						} else {
							System.out.println(
									"\n" + skuId + " is not added because this item is currently out of stock");
							log.info("\n" + skuId + " is not added because this item is currently out of stock");
						}
					}
				}

			} else {
				System.out.println("\nError while invoking SearchService getStyleData API");
				log.error("\nError while invoking SearchService getStyleData API");
				AssertJUnit.fail("Error while invoking SearchService getStyleData API");
			}

			fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
			System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"
					+ fetchCartReqGen.respvalidate.returnresponseasstring());
			log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"
					+ fetchCartReqGen.respvalidate.returnresponseasstring());

			AssertJUnit.assertTrue("Error while invoking CheckoutService operationFetchCart API",
					fetchCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
							.contains(SUCCESS_STATUS_TYPE));

		} else if (fetchCartReqGen.respvalidate.NodeValue(CartNodes.CART_IS_GIFT_ORDER.toString(), true)
				.equals(TRUE_VALUE)) {
			RequestGenerator removeGiftWrapAndMsgReqGen = checkoutTestsHelper.removeGiftWrapAndMessage(userName,
					password);
			System.out.println("\nPrinting CheckoutService removeGiftWrapAndMessage API response :\n\n"
					+ removeGiftWrapAndMsgReqGen.respvalidate.returnresponseasstring());
			log.info("\nPrinting CheckoutService removeGiftWrapAndMessage API response :\n\n"
					+ removeGiftWrapAndMsgReqGen.respvalidate.returnresponseasstring());

			AssertJUnit.assertTrue("Error while invoking CheckoutService removeGiftWrapAndMessage API",
					removeGiftWrapAndMsgReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
							.contains(SUCCESS_STATUS_TYPE));
		}

		RequestGenerator addGiftWrapAndMessageReqGen = checkoutTestsHelper.addGiftWrapAndMessage(userName, password,
				senderName, giftMsg, recipientName);
		System.out.println("\nPrinting CheckoutService addGiftWrapAndMessage API response :\n\n"
				+ addGiftWrapAndMessageReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService addGiftWrapAndMessage API response :\n\n"
				+ addGiftWrapAndMessageReqGen.respvalidate.returnresponseasstring());

		AssertJUnit.assertEquals("CheckoutService addGiftWrapAndMessage API is not working", Integer.parseInt(respCode),
				addGiftWrapAndMessageReqGen.response.getStatus());

		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;

		System.out.println(
				"\nTime taken to execute - TestCase - CheckoutServiceTests_addGiftWrapAndMessage_verifySuccessResponse : "
						+ timeTaken + " seconds\n");
		log.info(
				"\nTime taken to execute - TestCase - CheckoutServiceTests_addGiftWrapAndMessage_verifySuccessResponse : "
						+ timeTaken + " seconds\n");
	}

	/**
	 * This Test Case used to invoke CheckoutService addGiftWrapAndMessage API
	 * and verify for success status response
	 * 
	 * @param userName
	 * @param password
	 * @param senderName
	 * @param giftMsg
	 * @param recipientName
	 * @param successStatusCode
	 * @param successStatusMsg
	 * @param successStatusType
	 */
	@Test(groups = { "Sanity", "Regression", "MiniRegression",
			"ExhaustiveRegression" }, dataProvider = "CartServiceDP_addGiftWrapAndMessageDataProvider_positiveCases", description = "1. hit IDP service and login the user. "
					+ "\n 2. hit fetchCart API. \n 3. if user's cart is empty then first add items to cart(\n a.hit Search Service API and extract some skuIDs"
					+ "\n b. Check if searched styleIds exists in user's cart or not. \n c. hit addItemToCart API giving skuId which doesnt exists in cart)"
					+ "\n 4. Check if giftwrap is already applied to cart remove it"
					+ "\n 5. hit addGiftWrap API passing valid data"
					+ "\n 6. Verify statusCode. statusMessage & statusType must be of Success")
	public void CartServiceTests_addGiftWrapAndMessage_verifySuccessStatusResponse(String userName, String password,
			String senderName, String giftMsg, String recipientName, String successStatusCode, String successStatusMsg,
			String successStatusType) {
		long startTime = Calendar.getInstance().getTimeInMillis();
		String addItemQty = "1";
		RequestGenerator fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
		System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"
				+ fetchCartReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"
				+ fetchCartReqGen.respvalidate.returnresponseasstring());

		AssertJUnit.assertTrue("Error while invoking CheckoutService operationFetchCart API",
				fetchCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
						.contains(SUCCESS_STATUS_TYPE));

		if (fetchCartReqGen.respvalidate.NodeValue(CartNodes.CART_TOTAL_COUNT.toString(), true).equals(EMPTY_VALUE)) {
			List<Integer> styleIdList = checkoutTestsHelper.performSeachServiceToGetStyleIds(AC_SEARCH_PROD,
					AC_SEARCH_QTY, AC_SEARCH_RET_DOCS, AC_SEARCH_IS_FACET);

			if (!CollectionUtils.isEmpty(styleIdList)) {
				for (Integer styleId : styleIdList) {
					System.out.println("\nPrinting the StyleId : " + styleId);
					log.info("\nPrinting the StyleId : " + styleId);

					RequestGenerator getStyleDataReqGen = checkoutTestsHelper
							.performGetStyleDataOperation(String.valueOf(styleId));
					System.out.println("\nPrinting StyleService getStyleData API response :\n\n"
							+ getStyleDataReqGen.respvalidate.returnresponseasstring());
					log.info("\nPrinting StyleService getStyleData API response :\n\n"
							+ getStyleDataReqGen.respvalidate.returnresponseasstring());

					AssertJUnit.assertTrue("Error while getting style data", getStyleDataReqGen.respvalidate
							.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));

					String response = getStyleDataReqGen.respvalidate.returnresponseasstring();
					List<Integer> ids = JsonPath.read(response, "$.data..styleOptions[*].id");

					for (int i = 0; i < ids.size(); i++) {
						String skuId = JsonPath.read(response, "$.data..styleOptions[" + i + "].skuId").toString();
						String inventoryCount = JsonPath
								.read(response, "$.data..styleOptions[" + i + "].inventoryCount").toString();
						String available = JsonPath.read(response, "$.data..styleOptions[" + i + "].available")
								.toString();

						if (!inventoryCount.equals(EMPTY_VALUE) && available.equals(TRUE_VALUE)) {
							fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
							System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"
									+ fetchCartReqGen.respvalidate.returnresponseasstring());
							log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"
									+ fetchCartReqGen.respvalidate.returnresponseasstring());

							if (StringUtils.isBlank(checkoutTestsHelper.getItemIdFromSKUId(fetchCartReqGen, skuId,
									CheckoutDataProviderEnum.CART.name()))) {
								RequestGenerator addItemToCartReqGen = checkoutTestsHelper.addItemToCart(userName,
										password, skuId, String.valueOf(addItemQty), ADD_OPERATION);
								System.out.println("\nPrinting CheckoutService addItemToCart API response :\n\n"
										+ addItemToCartReqGen.respvalidate.returnresponseasstring());
								log.info("\nPrinting CheckoutService addItemToCart API response :\n\n"
										+ addItemToCartReqGen.respvalidate.returnresponseasstring());

								AssertJUnit.assertTrue("Error while invoking CheckoutService addItemToCart API",
										addItemToCartReqGen.respvalidate
												.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
												.contains(SUCCESS_STATUS_TYPE));

								System.out.println(
										"\n" + skuId + " is available and it doesn't exists in cart so added to cart");
								log.info("\n" + skuId + " is available and it doesn't exists in cart so added to cart");
								break;

							} else {
								System.out.println(
										"\n" + skuId + " is not added because this item already exists in cart");
								log.error("\n" + skuId + " is not added because this item already exists in cart");
							}

						} else {
							System.out.println(
									"\n" + skuId + " is not added because this item is currently out of stock");
							log.info("\n" + skuId + " is not added because this item is currently out of stock");
						}
					}
				}

			} else {
				System.out.println("\nError while invoking SearchService getStyleData API");
				log.error("\nError while invoking SearchService getStyleData API");
				AssertJUnit.fail("Error while invoking SearchService getStyleData API");
			}

			fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
			System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"
					+ fetchCartReqGen.respvalidate.returnresponseasstring());
			log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"
					+ fetchCartReqGen.respvalidate.returnresponseasstring());

			AssertJUnit.assertTrue("Error while invoking CheckoutService operationFetchCart API",
					fetchCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
							.contains(SUCCESS_STATUS_TYPE));

		} else if (fetchCartReqGen.respvalidate.NodeValue(CartNodes.CART_IS_GIFT_ORDER.toString(), true)
				.equals(TRUE_VALUE)) {
			RequestGenerator removeGiftWrapAndMsgReqGen = checkoutTestsHelper.removeGiftWrapAndMessage(userName,
					password);
			System.out.println("\nPrinting CheckoutService removeGiftWrapAndMessage API response :\n\n"
					+ removeGiftWrapAndMsgReqGen.respvalidate.returnresponseasstring());
			log.info("\nPrinting CheckoutService removeGiftWrapAndMessage API response :\n\n"
					+ removeGiftWrapAndMsgReqGen.respvalidate.returnresponseasstring());

			AssertJUnit.assertTrue("Error while invoking CheckoutService removeGiftWrapAndMessage API",
					removeGiftWrapAndMsgReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
							.contains(SUCCESS_STATUS_TYPE));
		}

		RequestGenerator addGiftWrapAndMessageReqGen = checkoutTestsHelper.addGiftWrapAndMessage(userName, password,
				senderName, giftMsg, recipientName);
		System.out.println("\nPrinting CheckoutService addGiftWrapAndMessage API response :\n\n"
				+ addGiftWrapAndMessageReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService addGiftWrapAndMessage API response :\n\n"
				+ addGiftWrapAndMessageReqGen.respvalidate.returnresponseasstring());

		AssertJUnit.assertTrue(
				"CheckoutService addGiftWrapAndMessage API response status code is not a success status code",
				addGiftWrapAndMessageReqGen.respvalidate.NodeValue(StatusNodes.STATUS_CODE.toString(), true)
						.contains(successStatusCode));

		AssertJUnit.assertTrue(
				"CheckoutService addGiftWrapAndMessage API response status message is not a success status message",
				addGiftWrapAndMessageReqGen.respvalidate.NodeValue(StatusNodes.STATUS_MESSAGE.toString(), true)
						.contains(successStatusMsg));

		AssertJUnit.assertTrue(
				"CheckoutService addGiftWrapAndMessage API response status type is not a success status type",
				addGiftWrapAndMessageReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
						.contains(successStatusType));

		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;

		System.out.println(
				"\nTime taken to execute - TestCase - CheckoutServiceTests_addGiftWrapAndMessage_verifySuccessStatusResponse : "
						+ timeTaken + " seconds\n");
		log.info(
				"\nTime taken to execute - TestCase - CheckoutServiceTests_addGiftWrapAndMessage_verifySuccessStatusResponse : "
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
			"ExhaustiveRegression" }, dataProvider = "CartServiceDP_addGiftWrapAndMessageDataProvider_validateGiftNodes", description = "1. hit IDP service and login the user. "
					+ "\n 2. hit fetchCart API. \n 3. if user's cart is empty then first add items to cart(\n a.hit Search Service API and extract some skuIDs"
					+ "\n b. Check if searched styleIds exists in user's cart or not. \n c. hit addItemToCart API giving skuId which doesnt exists in cart)"
					+ "\n 4. Check if giftwrap is already applied to cart remove it"
					+ "\n 5. hit addGiftWrap API passing valid data" + "\n 6. Validate GiftWrap Nodes in APIs response")
	public void CartServiceTests_addGiftWrapAndMessage_validateGiftNodes(String userName, String password,
			String senderName, String giftMsg, String recipientName) {
		long startTime = Calendar.getInstance().getTimeInMillis();
		String addItemQty = "1";
		RequestGenerator fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
		System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"
				+ fetchCartReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"
				+ fetchCartReqGen.respvalidate.returnresponseasstring());

		AssertJUnit.assertTrue("Error while invoking CheckoutService operationFetchCart API",
				fetchCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
						.contains(SUCCESS_STATUS_TYPE));

		if (fetchCartReqGen.respvalidate.NodeValue(CartNodes.CART_TOTAL_COUNT.toString(), true).equals(EMPTY_VALUE)) {
			List<Integer> styleIdList = checkoutTestsHelper.performSeachServiceToGetStyleIds(AC_SEARCH_PROD,
					AC_SEARCH_QTY, AC_SEARCH_RET_DOCS, AC_SEARCH_IS_FACET);

			if (!CollectionUtils.isEmpty(styleIdList)) {
				for (Integer styleId : styleIdList) {
					System.out.println("\nPrinting the StyleId : " + styleId);
					log.info("\nPrinting the StyleId : " + styleId);

					RequestGenerator getStyleDataReqGen = checkoutTestsHelper
							.performGetStyleDataOperation(String.valueOf(styleId));
					System.out.println("\nPrinting StyleService getStyleData API response :\n\n"
							+ getStyleDataReqGen.respvalidate.returnresponseasstring());
					log.info("\nPrinting StyleService getStyleData API response :\n\n"
							+ getStyleDataReqGen.respvalidate.returnresponseasstring());

					AssertJUnit.assertTrue("Error while getting style data", getStyleDataReqGen.respvalidate
							.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));

					String response = getStyleDataReqGen.respvalidate.returnresponseasstring();
					List<Integer> ids = JsonPath.read(response, "$.data..styleOptions[*].id");

					for (int i = 0; i < ids.size(); i++) {
						String skuId = JsonPath.read(response, "$.data..styleOptions[" + i + "].skuId").toString();
						String inventoryCount = JsonPath
								.read(response, "$.data..styleOptions[" + i + "].inventoryCount").toString();
						String available = JsonPath.read(response, "$.data..styleOptions[" + i + "].available")
								.toString();

						if (!inventoryCount.equals(EMPTY_VALUE) && available.equals(TRUE_VALUE)) {
							fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
							System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"
									+ fetchCartReqGen.respvalidate.returnresponseasstring());
							log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"
									+ fetchCartReqGen.respvalidate.returnresponseasstring());

							if (StringUtils.isBlank(checkoutTestsHelper.getItemIdFromSKUId(fetchCartReqGen, skuId,
									CheckoutDataProviderEnum.CART.name()))) {
								RequestGenerator addItemToCartReqGen = checkoutTestsHelper.addItemToCart(userName,
										password, skuId, String.valueOf(addItemQty), ADD_OPERATION);
								System.out.println("\nPrinting CheckoutService addItemToCart API response :\n\n"
										+ addItemToCartReqGen.respvalidate.returnresponseasstring());
								log.info("\nPrinting CheckoutService addItemToCart API response :\n\n"
										+ addItemToCartReqGen.respvalidate.returnresponseasstring());

								AssertJUnit.assertTrue("Error while invoking CheckoutService addItemToCart API",
										addItemToCartReqGen.respvalidate
												.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
												.contains(SUCCESS_STATUS_TYPE));

								System.out.println(
										"\n" + skuId + " is available and it doesn't exists in cart so added to cart");
								log.info("\n" + skuId + " is available and it doesn't exists in cart so added to cart");
								break;

							} else {
								System.out.println(
										"\n" + skuId + " is not added because this item already exists in cart");
								log.error("\n" + skuId + " is not added because this item already exists in cart");
							}

						} else {
							System.out.println(
									"\n" + skuId + " is not added because this item is currently out of stock");
							log.info("\n" + skuId + " is not added because this item is currently out of stock");
						}
					}

				}

			} else {
				System.out.println("\nError while invoking SearchService getStyleData API");
				log.error("\nError while invoking SearchService getStyleData API");
				AssertJUnit.fail("Error while invoking SearchService getStyleData API");
			}

			fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
			System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"
					+ fetchCartReqGen.respvalidate.returnresponseasstring());
			log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"
					+ fetchCartReqGen.respvalidate.returnresponseasstring());

			AssertJUnit.assertTrue("Error while invoking CheckoutService operationFetchCart API",
					fetchCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
							.contains(SUCCESS_STATUS_TYPE));

		} else if (fetchCartReqGen.respvalidate.NodeValue(CartNodes.CART_IS_GIFT_ORDER.toString(), true)
				.equals(TRUE_VALUE)) {
			RequestGenerator removeGiftWrapAndMsgReqGen = checkoutTestsHelper.removeGiftWrapAndMessage(userName,
					password);
			System.out.println("\nPrinting CheckoutService removeGiftWrapAndMessage API response :\n\n"
					+ removeGiftWrapAndMsgReqGen.respvalidate.returnresponseasstring());
			log.info("\nPrinting CheckoutService removeGiftWrapAndMessage API response :\n\n"
					+ removeGiftWrapAndMsgReqGen.respvalidate.returnresponseasstring());

			AssertJUnit.assertTrue("Error while invoking CheckoutService removeGiftWrapAndMessage API",
					removeGiftWrapAndMsgReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
							.contains(SUCCESS_STATUS_TYPE));
		}

		RequestGenerator addGiftWrapAndMessageReqGen = checkoutTestsHelper.addGiftWrapAndMessage(userName, password,
				senderName, giftMsg, recipientName);
		System.out.println("\nPrinting CheckoutService addGiftWrapAndMessage API response :\n\n"
				+ addGiftWrapAndMessageReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService addGiftWrapAndMessage API response :\n\n"
				+ addGiftWrapAndMessageReqGen.respvalidate.returnresponseasstring());

		AssertJUnit.assertTrue("Failed to add the gift wrap", !addGiftWrapAndMessageReqGen.respvalidate
				.NodeValue(CartNodes.CART_GIFT_CHARGE.toString(), true).equals(EMPTY_VALUE));

		AssertJUnit.assertTrue("addGiftWrapAndMessage nodes does not exists",
				addGiftWrapAndMessageReqGen.respvalidate.DoesNodesExists(CartNodes.getGiftWrapMessageNodes()));

		AssertJUnit.assertTrue("isGiftOrder is invalid", addGiftWrapAndMessageReqGen.respvalidate
				.NodeValue(CartNodes.CART_IS_GIFT_ORDER.toString(), true).equals(TRUE_VALUE));

		AssertJUnit.assertTrue("gift sender is different", addGiftWrapAndMessageReqGen.respvalidate
				.NodeValue(CartNodes.CART_GIFT_MSG_SENDER.toString(), true).contains(senderName));

		AssertJUnit.assertTrue("gift message is different", addGiftWrapAndMessageReqGen.respvalidate
				.NodeValue(CartNodes.CART_GIFT_MSG_INFO.toString(), true).contains(giftMsg));

		AssertJUnit.assertTrue("gift recipient is different", addGiftWrapAndMessageReqGen.respvalidate
				.NodeValue(CartNodes.CART_GIFT_MSG_RECIPIENT.toString(), true).contains(recipientName));

		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;

		System.out.println(
				"\nTime taken to execute - TestCase - CheckoutServiceTests_addGiftWrapAndMessage_validateGiftNodes : "
						+ timeTaken + " seconds\n");
		log.info("\nTime taken to execute - TestCase - CheckoutServiceTests_addGiftWrapAndMessage_validateGiftNodes : "
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
			"ExhaustiveRegression" }, dataProvider = "CartServiceDP_removeGiftWrapAndMessageDataProvider_validateAPIResponse", description = "1. hit IDP service and login the user. "
					+ "\n 2. hit fetchCart API. \n 3. if user's cart is empty then first add items to cart(\n a.hit Search Service API and extract some skuIDs"
					+ "\n b. Check if searched styleIds exists in user's cart or not. \n c. hit addItemToCart API giving skuId which doesnt exists in cart)"
					+ "\n 4. Check if giftwrap is not applied to cart, first apply GiftWrap"
					+ "\n 5. hit removeGiftWrap API" + "\n 6. Verify statusCode must be 200 in APIs response")
	public void CartServiceTests_removeGiftWrapAndMessage_verifySuccessResponse(String userName, String password,
			String successRespCode) {
		long startTime = Calendar.getInstance().getTimeInMillis();
		String addItemQty = "1";
		RequestGenerator fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
		System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"
				+ fetchCartReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"
				+ fetchCartReqGen.respvalidate.returnresponseasstring());

		AssertJUnit.assertTrue("Error while invoking CheckoutService operationFetchCart API",
				fetchCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
						.contains(SUCCESS_STATUS_TYPE));

		if (fetchCartReqGen.respvalidate.NodeValue(CartNodes.CART_TOTAL_COUNT.toString(), true).equals(EMPTY_VALUE)) {
			List<Integer> styleIdList = checkoutTestsHelper.performSeachServiceToGetStyleIds(RGW_SEARCH_PROD,
					RGW_SEARCH_QTY, RGW_SEARCH_RET_DOCS, RGW_SEARCH_IS_FACET);

			if (!CollectionUtils.isEmpty(styleIdList)) {
				for (Integer styleId : styleIdList) {
					System.out.println("\nPrinting the StyleId : " + styleId);
					log.info("\nPrinting the StyleId : " + styleId);

					RequestGenerator getStyleDataReqGen = checkoutTestsHelper
							.performGetStyleDataOperation(String.valueOf(styleId));
					System.out.println("\nPrinting StyleService getStyleData API response :\n\n"
							+ getStyleDataReqGen.respvalidate.returnresponseasstring());
					log.info("\nPrinting StyleService getStyleData API response :\n\n"
							+ getStyleDataReqGen.respvalidate.returnresponseasstring());

					AssertJUnit.assertTrue("Error while getting style data", getStyleDataReqGen.respvalidate
							.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));

					String response = getStyleDataReqGen.respvalidate.returnresponseasstring();
					List<Integer> ids = JsonPath.read(response, "$.data..styleOptions[*].id");

					for (int i = 0; i < ids.size(); i++) {

						String skuId = JsonPath.read(response, "$.data..styleOptions[" + i + "].skuId").toString();
						String inventoryCount = JsonPath
								.read(response, "$.data..styleOptions[" + i + "].inventoryCount").toString();
						String available = JsonPath.read(response, "$.data..styleOptions[" + i + "].available")
								.toString();

						if (!inventoryCount.equals(EMPTY_VALUE) && available.equals(TRUE_VALUE)) {
							fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
							System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"
									+ fetchCartReqGen.respvalidate.returnresponseasstring());
							log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"
									+ fetchCartReqGen.respvalidate.returnresponseasstring());

							if (StringUtils.isBlank(checkoutTestsHelper.getItemIdFromSKUId(fetchCartReqGen, skuId,
									CheckoutDataProviderEnum.CART.name()))) {
								RequestGenerator addItemToCartReqGen = checkoutTestsHelper.addItemToCart(userName,
										password, skuId, String.valueOf(addItemQty), ADD_OPERATION);
								System.out.println("\nPrinting CheckoutService addItemToCart API response :\n\n"
										+ addItemToCartReqGen.respvalidate.returnresponseasstring());
								log.info("\nPrinting CheckoutService addItemToCart API response :\n\n"
										+ addItemToCartReqGen.respvalidate.returnresponseasstring());

								AssertJUnit.assertTrue("Error while invoking CheckoutService addItemToCart API",
										addItemToCartReqGen.respvalidate
												.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
												.contains(SUCCESS_STATUS_TYPE));

								System.out.println(
										"\n" + skuId + " is available and it doesn't exists in cart so added to cart");
								log.info("\n" + skuId + " is available and it doesn't exists in cart so added to cart");
								break;

							} else {
								System.out.println(
										"\n" + skuId + " is not added because this item already exists in cart");
								log.error("\n" + skuId + " is not added because this item already exists in cart");
							}

						} else {
							System.out.println(
									"\n" + skuId + " is not added because this item is currently out of stock");
							log.info("\n" + skuId + " is not added because this item is currently out of stock");
						}
					}

				}

			} else {
				System.out.println("\nError while invoking SearchService getStyleData API");
				log.error("\nError while invoking SearchService getStyleData API");
				AssertJUnit.fail("Error while invoking SearchService getStyleData API");
			}

			fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
			System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"
					+ fetchCartReqGen.respvalidate.returnresponseasstring());
			log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"
					+ fetchCartReqGen.respvalidate.returnresponseasstring());

			AssertJUnit.assertTrue("Error while invoking CheckoutService operationFetchCart API",
					fetchCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
							.contains(SUCCESS_STATUS_TYPE));

		} else if (fetchCartReqGen.respvalidate.NodeValue(CartNodes.CART_IS_GIFT_ORDER.toString(), true)
				.equals(FALSE_VALUE)) {
			RequestGenerator addGiftWrapAndMessageReqGen = checkoutTestsHelper.addGiftWrapAndMessage(userName, password,
					RGW_GIFT_SENDER, RGW_GIFT_MSG, RGW_GIFT_RECEPIENT);
			System.out.println("\nPrinting CheckoutService addGiftWrapAndMessage API response :\n\n"
					+ addGiftWrapAndMessageReqGen.respvalidate.returnresponseasstring());
			log.info("\nPrinting CheckoutService addGiftWrapAndMessage API response :\n\n"
					+ addGiftWrapAndMessageReqGen.respvalidate.returnresponseasstring());

			AssertJUnit.assertTrue("Error while invoking CheckoutService addGiftWrapAndMessage API",
					addGiftWrapAndMessageReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
							.contains(SUCCESS_STATUS_TYPE));
		}

		RequestGenerator removeGiftWrapAndMessageReqGen = checkoutTestsHelper.removeGiftWrapAndMessage(userName,
				password);
		System.out.println("\nPrinting CheckoutService removeGiftWrapAndMessage API response :\n\n"
				+ removeGiftWrapAndMessageReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService removeGiftWrapAndMessage API response :\n\n"
				+ removeGiftWrapAndMessageReqGen.respvalidate.returnresponseasstring());

		AssertJUnit.assertEquals("CheckoutService removeGiftWrapAndMessage API is not working",
				Integer.parseInt(successRespCode), removeGiftWrapAndMessageReqGen.response.getStatus());

		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;

		System.out.println(
				"\nTime taken to execute - TestCase - CheckoutServiceTests_removeGiftWrapAndMessage_verifySuccessResponse : "
						+ timeTaken + " seconds\n");
		log.info(
				"\nTime taken to execute - TestCase - CheckoutServiceTests_removeGiftWrapAndMessage_verifySuccessResponse : "
						+ timeTaken + " seconds\n");
	}

	/**
	 * This Test Case used to invoke CheckoutService removeGiftWrapAndMessage
	 * API and verify for success status response
	 * 
	 * @param userName
	 * @param password
	 * @param successStatusCode
	 * @param successStatusMsg
	 * @param successStatusType
	 */
	@Test(groups = { "Sanity", "Regression", "MiniRegression",
			"ExhaustiveRegression" }, dataProvider = "CartServiceDP_removeGiftWrapAndMessageDataProvider_positiveCases", description = "1. hit IDP service and login the user. "
					+ "\n 2. hit fetchCart API. \n 3. if user's cart is empty then first add items to cart(\n a.hit Search Service API and extract some skuIDs"
					+ "\n b. Check if searched styleIds exists in user's cart or not. \n c. hit addItemToCart API giving skuId which doesnt exists in cart)"
					+ "\n 4. Check if giftwrap is not applied to cart, first apply GiftWrap"
					+ "\n 5. hit removeGiftWrap API"
					+ "\n 6. Verify statusCode, statusMessage & statusType must be of Success")
	public void CartServiceTests_removeGiftWrapAndMessage_verifySuccessStatusResponse(String userName, String password,
			String successStatusCode, String successStatusMsg, String successStatusType) {
		long startTime = Calendar.getInstance().getTimeInMillis();
		String addItemQty = "1";
		RequestGenerator fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
		System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"
				+ fetchCartReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"
				+ fetchCartReqGen.respvalidate.returnresponseasstring());

		AssertJUnit.assertTrue("Error while invoking CheckoutService operationFetchCart API",
				fetchCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
						.contains(SUCCESS_STATUS_TYPE));

		if (fetchCartReqGen.respvalidate.NodeValue(CartNodes.CART_TOTAL_COUNT.toString(), true).equals(EMPTY_VALUE)) {
			List<Integer> styleIdList = checkoutTestsHelper.performSeachServiceToGetStyleIds(RGW_SEARCH_PROD,
					RGW_SEARCH_QTY, RGW_SEARCH_RET_DOCS, RGW_SEARCH_IS_FACET);

			if (!CollectionUtils.isEmpty(styleIdList)) {
				for (Integer styleId : styleIdList) {
					System.out.println("\nPrinting the StyleId : " + styleId);
					log.info("\nPrinting the StyleId : " + styleId);

					RequestGenerator getStyleDataReqGen = checkoutTestsHelper
							.performGetStyleDataOperation(String.valueOf(styleId));
					System.out.println("\nPrinting StyleService getStyleData API response :\n\n"
							+ getStyleDataReqGen.respvalidate.returnresponseasstring());
					log.info("\nPrinting StyleService API getStyleData response :\n\n"
							+ getStyleDataReqGen.respvalidate.returnresponseasstring());

					AssertJUnit.assertTrue("Error while getting style data", getStyleDataReqGen.respvalidate
							.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));

					String response = getStyleDataReqGen.respvalidate.returnresponseasstring();
					System.out.println("Response string of search:" + response);
					List<Integer> ids = JsonPath.read(response, "$.data..styleOptions[*].id");

					for (int i = 0; i < ids.size(); i++) {

						String skuId = JsonPath.read(response, "$.data..styleOptions[" + i + "].skuId").toString();
						String inventoryCount = JsonPath
								.read(response, "$.data..styleOptions[" + i + "].inventoryCount").toString();
						String available = JsonPath.read(response, "$.data..styleOptions[" + i + "].available")
								.toString();

						if (!inventoryCount.equals(EMPTY_VALUE) && available.equals(TRUE_VALUE)) {
							fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
							System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"
									+ fetchCartReqGen.respvalidate.returnresponseasstring());
							log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"
									+ fetchCartReqGen.respvalidate.returnresponseasstring());

							if (StringUtils.isBlank(checkoutTestsHelper.getItemIdFromSKUId(fetchCartReqGen, skuId,
									CheckoutDataProviderEnum.CART.name()))) {
								RequestGenerator addItemToCartReqGen = checkoutTestsHelper.addItemToCart(userName,
										password, skuId, String.valueOf(addItemQty), ADD_OPERATION);
								System.out.println("\nPrinting CheckoutService addItemToCart API response :\n\n"
										+ addItemToCartReqGen.respvalidate.returnresponseasstring());
								log.info("\nPrinting CheckoutService addItemToCart API response :\n\n"
										+ addItemToCartReqGen.respvalidate.returnresponseasstring());

								AssertJUnit.assertTrue("Error while invoking CheckoutService addItemToCart API",
										addItemToCartReqGen.respvalidate
												.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
												.contains(SUCCESS_STATUS_TYPE));

								System.out.println(
										"\n" + skuId + " is available and it doesn't exists in cart so added to cart");
								log.info("\n" + skuId + " is available and it doesn't exists in cart so added to cart");
								break;

							} else {
								System.out.println(
										"\n" + skuId + " is not added because this item already exists in cart");
								log.error("\n" + skuId + " is not added because this item already exists in cart");
							}

						} else {
							System.out.println(
									"\n" + skuId + " is not added because this item is currently out of stock");
							log.info("\n" + skuId + " is not added because this item is currently out of stock");
						}
					}
				}

			} else {
				System.out.println("\nError while invoking SearchService API");
				log.error("\nError while invoking SearchService API");
				AssertJUnit.fail("Error while invoking SearchService API");
			}

			fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
			System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"
					+ fetchCartReqGen.respvalidate.returnresponseasstring());
			log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"
					+ fetchCartReqGen.respvalidate.returnresponseasstring());

			AssertJUnit.assertTrue("Error while invoking CheckoutService operationFetchCart API",
					fetchCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
							.contains(SUCCESS_STATUS_TYPE));

		} else if (fetchCartReqGen.respvalidate.NodeValue(CartNodes.CART_IS_GIFT_ORDER.toString(), true)
				.equals(FALSE_VALUE)) {
			RequestGenerator addGiftWrapAndMessageReqGen = checkoutTestsHelper.addGiftWrapAndMessage(userName, password,
					RGW_GIFT_SENDER, RGW_GIFT_MSG, RGW_GIFT_RECEPIENT);
			System.out.println("\nPrinting CheckoutService addGiftWrapAndMessage API response :\n\n"
					+ addGiftWrapAndMessageReqGen.respvalidate.returnresponseasstring());
			log.info("\nPrinting CheckoutService addGiftWrapAndMessage API response :\n\n"
					+ addGiftWrapAndMessageReqGen.respvalidate.returnresponseasstring());

			AssertJUnit.assertTrue("Error while invoking CheckoutService addGiftWrapAndMessage API",
					addGiftWrapAndMessageReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
							.contains(SUCCESS_STATUS_TYPE));
		}

		RequestGenerator removeGiftWrapAndMessageReqGen = checkoutTestsHelper.removeGiftWrapAndMessage(userName,
				password);
		System.out.println("\nPrinting CheckoutService removeGiftWrapAndMessage API response :\n\n"
				+ removeGiftWrapAndMessageReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService removeGiftWrapAndMessage API response :\n\n"
				+ removeGiftWrapAndMessageReqGen.respvalidate.returnresponseasstring());

		AssertJUnit.assertTrue(
				"CheckoutService removeGiftWrapAndMessage API response status code is not a success status code",
				removeGiftWrapAndMessageReqGen.respvalidate.NodeValue(StatusNodes.STATUS_CODE.toString(), true)
						.contains(successStatusCode));

		AssertJUnit.assertTrue(
				"CheckoutService removeGiftWrapAndMessage API response status message is not a success status message",
				removeGiftWrapAndMessageReqGen.respvalidate.NodeValue(StatusNodes.STATUS_MESSAGE.toString(), true)
						.contains(successStatusMsg));

		AssertJUnit.assertTrue(
				"CheckoutService removeGiftWrapAndMessage API response status type is not a success status type",
				removeGiftWrapAndMessageReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
						.contains(successStatusType));

		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;

		System.out.println(
				"\nTime taken to execute - TestCase - CheckoutServiceTests_removeGiftWrapAndMessage_verifySuccessStatusResponse : "
						+ timeTaken + " seconds\n");
		log.info(
				"\nTime taken to execute - TestCase - CheckoutServiceTests_removeGiftWrapAndMessage_verifySuccessStatusResponse : "
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
			"ExhaustiveRegression" }, dataProvider = "CartServiceDP_removeGiftWrapAndMessageDataProvider_validateGiftNodes", description = "1. hit IDP service and login the user. "
					+ "\n 2. hit fetchCart API. \n 3. if user's cart is empty then first add items to cart(\n a.hit Search Service API and extract some skuIDs"
					+ "\n b. Check if searched styleIds exists in user's cart or not. \n c. hit addItemToCart API giving skuId which doesnt exists in cart)"
					+ "\n 4. Check if giftwrap is not applied to cart, first apply GiftWrap"
					+ "\n 5. hit removeGiftWrap API" + "\n 6. Validate GiftWrap Nodes in APIs response")
	public void CartServiceTests_removeGiftWrapAndMessage_validateGiftNodes(String userName, String password) {
		long startTime = Calendar.getInstance().getTimeInMillis();
		String addItemQty = "1";
		RequestGenerator fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
		System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"
				+ fetchCartReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"
				+ fetchCartReqGen.respvalidate.returnresponseasstring());

		AssertJUnit.assertTrue("Error while invoking CheckoutService operationFetchCart API",
				fetchCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
						.contains(SUCCESS_STATUS_TYPE));

		if (fetchCartReqGen.respvalidate.NodeValue(CartNodes.CART_TOTAL_COUNT.toString(), true).equals(EMPTY_VALUE)) {
			List<Integer> styleIdList = checkoutTestsHelper.performSeachServiceToGetStyleIds(RGW_SEARCH_PROD,
					RGW_SEARCH_QTY, RGW_SEARCH_RET_DOCS, RGW_SEARCH_IS_FACET);

			if (!CollectionUtils.isEmpty(styleIdList)) {
				for (Integer styleId : styleIdList) {
					System.out.println("\nPrinting the StyleId : " + styleId);
					log.info("\nPrinting the StyleId : " + styleId);

					RequestGenerator getStyleDataReqGen = checkoutTestsHelper
							.performGetStyleDataOperation(String.valueOf(styleId));
					System.out.println("\nPrinting StyleService getStyleData API response :\n\n"
							+ getStyleDataReqGen.respvalidate.returnresponseasstring());
					log.info("\nPrinting StyleService getStyleData API response :\n\n"
							+ getStyleDataReqGen.respvalidate.returnresponseasstring());

					AssertJUnit.assertTrue("Error while getting style data", getStyleDataReqGen.respvalidate
							.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));

					String response = getStyleDataReqGen.respvalidate.returnresponseasstring();
					List<Integer> ids = JsonPath.read(response, "$.data..styleOptions[*].id");

					for (int i = 0; i < ids.size(); i++) {
						String skuId = JsonPath.read(response, "$.data..styleOptions[" + i + "].skuId").toString();
						String inventoryCount = JsonPath
								.read(response, "$.data..styleOptions[" + i + "].inventoryCount").toString();
						String available = JsonPath.read(response, "$.data..styleOptions[" + i + "].available")
								.toString();

						if (!inventoryCount.equals(EMPTY_VALUE) && available.equals(TRUE_VALUE)) {
							fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
							System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"
									+ fetchCartReqGen.respvalidate.returnresponseasstring());
							log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"
									+ fetchCartReqGen.respvalidate.returnresponseasstring());

							if (StringUtils.isBlank(checkoutTestsHelper.getItemIdFromSKUId(fetchCartReqGen, skuId,
									CheckoutDataProviderEnum.CART.name()))) {
								RequestGenerator addItemToCartReqGen = checkoutTestsHelper.addItemToCart(userName,
										password, skuId, String.valueOf(addItemQty), ADD_OPERATION);
								System.out.println("\nPrinting CheckoutService addItemToCart API response :\n\n"
										+ addItemToCartReqGen.respvalidate.returnresponseasstring());
								log.info("\nPrinting CheckoutService addItemToCart API response :\n\n"
										+ addItemToCartReqGen.respvalidate.returnresponseasstring());

								AssertJUnit.assertTrue("Error while invoking CheckoutService addItemToCart API",
										addItemToCartReqGen.respvalidate
												.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
												.contains(SUCCESS_STATUS_TYPE));

								System.out.println(
										"\n" + skuId + " is available and it doesn't exists in cart so added to cart");
								log.info("\n" + skuId + " is available and it doesn't exists in cart so added to cart");
								break;

							} else {
								System.out.println(
										"\n" + skuId + " is not added because this item already exists in cart");
								log.error("\n" + skuId + " is not added because this item already exists in cart");
							}

						} else {
							System.out.println(
									"\n" + skuId + " is not added because this item is currently out of stock");
							log.info("\n" + skuId + " is not added because this item is currently out of stock");
						}
					}
				}

			} else {
				System.out.println("\nError while invoking SearchService getStyleData API");
				log.error("\nError while invoking SearchService getStyleData API");
				AssertJUnit.fail("Error while invoking SearchService getStyleData API");
			}

			fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
			System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"
					+ fetchCartReqGen.respvalidate.returnresponseasstring());
			log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"
					+ fetchCartReqGen.respvalidate.returnresponseasstring());

			AssertJUnit.assertTrue("Error while invoking CheckoutService operationFetchCart API",
					fetchCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
							.contains(SUCCESS_STATUS_TYPE));

		} else if (fetchCartReqGen.respvalidate.NodeValue(CartNodes.CART_IS_GIFT_ORDER.toString(), true)
				.equals(FALSE_VALUE)) {
			RequestGenerator addGiftWrapAndMessageReqGen = checkoutTestsHelper.addGiftWrapAndMessage(userName, password,
					RGW_GIFT_SENDER, RGW_GIFT_MSG, RGW_GIFT_RECEPIENT);
			System.out.println("\nPrinting CheckoutService addGiftWrapAndMessage API response :\n\n"
					+ addGiftWrapAndMessageReqGen.respvalidate.returnresponseasstring());
			log.info("\nPrinting CheckoutService addGiftWrapAndMessage API response :\n\n"
					+ addGiftWrapAndMessageReqGen.respvalidate.returnresponseasstring());

			AssertJUnit.assertTrue("Error while invoking CheckoutService addGiftWrapAndMessage API",
					addGiftWrapAndMessageReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
							.contains(SUCCESS_STATUS_TYPE));
		}

		RequestGenerator removeGiftWrapAndMessageReqGen = checkoutTestsHelper.removeGiftWrapAndMessage(userName,
				password);
		System.out.println("\nPrinting CheckoutService removeGiftWrapAndMessage API response :\n\n"
				+ removeGiftWrapAndMessageReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService removeGiftWrapAndMessage API response :\n\n"
				+ removeGiftWrapAndMessageReqGen.respvalidate.returnresponseasstring());

		AssertJUnit.assertTrue("Failed to remove the gift wrap", removeGiftWrapAndMessageReqGen.respvalidate
				.NodeValue(CartNodes.CART_GIFT_CHARGE.toString(), true).equals(EMPTY_VALUE));

		AssertJUnit.assertTrue("isGiftOrder is invalid", removeGiftWrapAndMessageReqGen.respvalidate
				.NodeValue(CartNodes.CART_IS_GIFT_ORDER.toString(), true).equals(FALSE_VALUE));

		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;

		System.out.println(
				"\nTime taken to execute - TestCase - CheckoutServiceTests_removeGiftWrapAndMessage_validateGiftNodes : "
						+ timeTaken + " seconds\n");
		log.info(
				"\nTime taken to execute - TestCase - CheckoutServiceTests_removeGiftWrapAndMessage_validateGiftNodes : "
						+ timeTaken + " seconds\n");

	}

	/**
	 * This Test Case used to invoke CheckoutService moveItemToWishList API and
	 * verify for success response
	 * 
	 * @param userName
	 * @param password
	 * @param skuId
	 * @param EMPTY_VALUE.name()))
	 * @param successRespCode
	 */
	@Test(groups = { "Sanity", "FoxSanity", "Regression", "MiniRegression",
			"ExhaustiveRegression" }, dataProvider = "CartServiceDP_moveToWishListDataProvider_validateAPIResponse", description = "1. hit IDP service and login the user. "
					+ "\n 2. hit fetchCart API. \n 3. if user's cart is empty then first add items to cart(\n a.hit Search Service API and extract some skuIDs"
					+ "\n b. Check if searched styleIds exists in user's cart or not. \n c. hit addItemToCart API giving skuId which doesnt exists in cart)"
					+ "\n 4. hit moveItemToWishlistFromCart API giving it itemId to be moved"
					+ "\n 5. Verify statusCode must be 200 in APIs response")
	public void CartServiceTests_moveItemToWishList_verifySuccessResponse(String userName, String password,
			String successRespCode) {
		long startTime = Calendar.getInstance().getTimeInMillis();
		String addItemQty = "1";
		Boolean itemAdded = false;
		RequestGenerator fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
		System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"
				+ fetchCartReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"
				+ fetchCartReqGen.respvalidate.returnresponseasstring());

		AssertJUnit.assertTrue("Error while invoking operationFetchCart API", fetchCartReqGen.respvalidate
				.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));

		if (fetchCartReqGen.respvalidate.NodeValue(CartNodes.CART_TOTAL_COUNT.toString(), true).equals(EMPTY_VALUE)) {
			List<Integer> styleIdList = checkoutTestsHelper.performSeachServiceToGetStyleIds(MWL_SEARCH_PROD,
					MWL_SEARCH_QTY, MWL_SEARCH_RET_DOCS, MWL_SEARCH_IS_FACET);

			if (!CollectionUtils.isEmpty(styleIdList)) {
				for (Integer styleId : styleIdList) {
					if (itemAdded)
						break;
					System.out.println("\nPrinting the StyleId : " + styleId);
					log.info("\nPrinting the StyleId : " + styleId);

					RequestGenerator getStyleDataReqGen = checkoutTestsHelper
							.performGetStyleDataOperation(String.valueOf(styleId));
					System.out.println("\nPrinting StyleService getStyleData API response :\n\n"
							+ getStyleDataReqGen.respvalidate.returnresponseasstring());
					log.info("\nPrinting StyleService getStyleDataAPI response :\n\n"
							+ getStyleDataReqGen.respvalidate.returnresponseasstring());

					AssertJUnit.assertTrue("Error while getting style data", getStyleDataReqGen.respvalidate
							.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));

					String response = getStyleDataReqGen.respvalidate.returnresponseasstring();
					List<Integer> ids = JsonPath.read(response, "$.data..styleOptions[*].id");

					for (int i = 0; i < ids.size(); i++) {
						String skuId = JsonPath.read(response, "$.data..styleOptions[" + i + "].skuId").toString();
						String inventoryCount = JsonPath
								.read(response, "$.data..styleOptions[" + i + "].inventoryCount").toString();
						String available = JsonPath.read(response, "$.data..styleOptions[" + i + "].available")
								.toString();

						if (!inventoryCount.equals(EMPTY_VALUE) && available.equals(TRUE_VALUE)) {
							fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
							System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"
									+ fetchCartReqGen.respvalidate.returnresponseasstring());
							log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"
									+ fetchCartReqGen.respvalidate.returnresponseasstring());

							if (StringUtils.isBlank(checkoutTestsHelper.getItemIdFromSKUId(fetchCartReqGen, skuId,
									CheckoutDataProviderEnum.CART.name()))) {
								RequestGenerator addItemToCartReqGen = checkoutTestsHelper.addItemToCart(userName,
										password, skuId, String.valueOf(addItemQty), ADD_OPERATION);
								System.out.println("\nPrinting addItemToCart API response :\n\n"
										+ addItemToCartReqGen.respvalidate.returnresponseasstring());
								log.info("\nPrinting addItemToCart API response :\n\n"
										+ addItemToCartReqGen.respvalidate.returnresponseasstring());
								itemAdded = true;
								AssertJUnit.assertTrue("Error while invoking CheckoutService addItemToCart API",
										addItemToCartReqGen.respvalidate
												.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
												.contains(SUCCESS_STATUS_TYPE));

								System.out.println(
										"\n" + skuId + " is available and it doesn't exists in cart so added to cart");
								log.info("\n" + skuId + " is available and it doesn't exists in cart so added to cart");
								break;

							} else {
								System.out.println(
										"\n" + skuId + " is not added because this item already exists in cart");
								log.error("\n" + skuId + " is not added because this item already exists in cart");
							}

						} else {
							System.out.println(
									"\n" + skuId + " is not added because this item is currently out of stock");
							log.info("\n" + skuId + " is not added because this item is currently out of stock");
						}
					}
				}

			} else {
				System.out.println("\nError while invoking SearchService getStyleData API");
				log.error("\nError while invoking SearchService getStyleData API");
				AssertJUnit.fail("Error while invoking SearchService getStyleData API");
			}

			fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
			System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"
					+ fetchCartReqGen.respvalidate.returnresponseasstring());
			log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"
					+ fetchCartReqGen.respvalidate.returnresponseasstring());

			AssertJUnit.assertTrue("Error while invoking CheckoutService operationFetchCart API",
					fetchCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
							.contains(SUCCESS_STATUS_TYPE));
		}

		String skuId = fetchCartReqGen.respvalidate.NodeValue(CartNodes.CART_ITEM_ENTRIES_SELSIZE_SKUID.toString(),
				false);
		String itemId = checkoutTestsHelper.getItemIdFromSKUId(fetchCartReqGen, skuId,
				CheckoutDataProviderEnum.CART.name());

		RequestGenerator fetchWishlistReqGen = checkoutTestsHelper.operationFetchWishList(userName, password);
		System.out.println("\nPrinting CheckoutService operationFetchWishlist API response :\n\n"
				+ fetchWishlistReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService operationFetchWishlist API response :\n\n"
				+ fetchWishlistReqGen.respvalidate.returnresponseasstring());

		AssertJUnit.assertTrue("Error while invoking CheckoutService operationFetchWishlist API",
				fetchWishlistReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
						.contains(SUCCESS_STATUS_TYPE));

		if (StringUtils.isBlank(checkoutTestsHelper.getItemIdFromSKUId(fetchCartReqGen, skuId,
				CheckoutDataProviderEnum.WISHLIST.name()))) {
			System.out.println(
					"\nPrinting skuId : " + skuId + " & itemId : " + itemId + " is moving to wishlist from cart\n");
			log.info("\nPrinting skuId : " + skuId + " & itemId : " + itemId
					+ " is going is moving to wishlist from cart\n");

			RequestGenerator moveItemToWishListReqGen = checkoutTestsHelper.moveItemToWishList(userName, password,
					itemId);
			System.out.println("\nPrinting CheckoutService moveItemToWishList API response :\n\n"
					+ moveItemToWishListReqGen.respvalidate.returnresponseasstring());
			log.info("\nPrinting CheckoutService moveItemToWishList API response :\n\n"
					+ moveItemToWishListReqGen.respvalidate.returnresponseasstring());

			AssertJUnit.assertEquals("CheckoutService moveItemToWishList API is not working",
					Integer.parseInt(successRespCode), moveItemToWishListReqGen.response.getStatus());

		} else {
			System.out.println(
					"\nPrinting skuId : " + skuId + " & itemId : " + itemId + " is already present in wishlist\n");
			log.info("\nPrinting skuId : " + skuId + " & itemId : " + itemId + " is already present in wishlist\n");
		}

		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;

		System.out.println(
				"\nTime taken to execute - TestCase - CheckoutServiceTests_moveItemToWishList_verifySuccessResponse : "
						+ timeTaken + " seconds\n");
		log.info("\nTime taken to execute - TestCase - CheckoutServiceTests_moveItemToWishList_verifySuccessResponse : "
				+ timeTaken + " seconds\n");
	}

	/**
	 * This Test Case used to invoke CheckoutService moveItemToWishList API and
	 * verify for success status response
	 * 
	 * @param userName
	 * @param password
	 * @param successStatusCode
	 * @param successStatusMsg
	 * @param successStatusType
	 */
	@Test(groups = { "Sanity", "Regression", "MiniRegression",
			"ExhaustiveRegression" }, dataProvider = "CartServiceDP_moveToWishListDataProvider_positiveCases", description = "1. hit IDP service and login the user. "
					+ "\n 2. hit fetchCart API. \n 3. if user's cart is empty then first add items to cart(\n a.hit Search Service API and extract some skuIDs"
					+ "\n b. Check if searched styleIds exists in user's cart or not. \n c. hit addItemToCart API giving skuId which doesnt exists in cart)"
					+ "\n 4. hit moveItemToWishlistFromCart API giving it itemId to be moved"
					+ "\n 5. Verify statusCode, statusMessage & statusType of APIs response must be of Success")
	public void CartServiceTests_moveItemToWishList_verifySuccessStatusResponse(String userName, String password,
			String successStatusCode, String successStatusMsg, String successStatusType) {
		long startTime = Calendar.getInstance().getTimeInMillis();
		String addItemQty = "1";
		Boolean itemAdded = false;
		RequestGenerator fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
		System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"
				+ fetchCartReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"
				+ fetchCartReqGen.respvalidate.returnresponseasstring());

		AssertJUnit.assertTrue("Error while invoking CheckoutService operationFetchCart API",
				fetchCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
						.contains(SUCCESS_STATUS_TYPE));

		if (fetchCartReqGen.respvalidate.NodeValue(CartNodes.CART_TOTAL_COUNT.toString(), true).equals(EMPTY_VALUE)) {
			List<Integer> styleIdList = checkoutTestsHelper.performSeachServiceToGetStyleIds(MWL_SEARCH_PROD,
					MWL_SEARCH_QTY, MWL_SEARCH_RET_DOCS, MWL_SEARCH_IS_FACET);

			if (!CollectionUtils.isEmpty(styleIdList)) {
				for (Integer styleId : styleIdList) {
					if (itemAdded)
						break;
					System.out.println("\nPrinting the StyleId : " + styleId);
					log.info("\nPrinting the StyleId : " + styleId);

					RequestGenerator getStyleDataReqGen = checkoutTestsHelper
							.performGetStyleDataOperation(String.valueOf(styleId));
					System.out.println("\nPrinting StyleService getStyleData API response :\n\n"
							+ getStyleDataReqGen.respvalidate.returnresponseasstring());
					log.info("\nPrinting StyleService getStyleData API response :\n\n"
							+ getStyleDataReqGen.respvalidate.returnresponseasstring());

					AssertJUnit.assertTrue("Error while getting style data",
							getStyleDataReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
									.contains(SUCCESS_STATUS_TYPE.toString()));

					String response = getStyleDataReqGen.respvalidate.returnresponseasstring();
					List<Integer> ids = JsonPath.read(response, "$.data..styleOptions[*].id");

					for (int i = 0; i < ids.size(); i++) {
						String skuId = JsonPath.read(response, "$.data..styleOptions[" + i + "].skuId").toString();
						String inventoryCount = JsonPath
								.read(response, "$.data..styleOptions[" + i + "].inventoryCount").toString();
						String available = JsonPath.read(response, "$.data..styleOptions[" + i + "].available")
								.toString();

						if (!inventoryCount.equals(EMPTY_VALUE) && available.equals(TRUE_VALUE)) {
							fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
							System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"
									+ fetchCartReqGen.respvalidate.returnresponseasstring());
							log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"
									+ fetchCartReqGen.respvalidate.returnresponseasstring());

							if (StringUtils.isBlank(checkoutTestsHelper.getItemIdFromSKUId(fetchCartReqGen, skuId,
									CheckoutDataProviderEnum.CART.name()))) {
								RequestGenerator addItemToCartReqGen = checkoutTestsHelper.addItemToCart(userName,
										password, skuId, String.valueOf(addItemQty), ADD_OPERATION);
								System.out.println("\nPrinting CheckoutService addItemToCart API response :\n\n"
										+ addItemToCartReqGen.respvalidate.returnresponseasstring());
								log.info("\nPrinting CheckoutService addItemToCart API response :\n\n"
										+ addItemToCartReqGen.respvalidate.returnresponseasstring());
								itemAdded = true;
								AssertJUnit.assertTrue("Error while invoking CheckoutService addItemToCart API",
										addItemToCartReqGen.respvalidate
												.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
												.contains(SUCCESS_STATUS_TYPE));

								System.out.println(
										"\n" + skuId + " is available and it doesn't exists in cart so added to cart");
								log.info("\n" + skuId + " is available and it doesn't exists in cart so added to cart");
								break;

							} else {
								System.out.println(
										"\n" + skuId + " is not added because this item already exists in cart");
								log.error("\n" + skuId + " is not added because this item already exists in cart");
							}

						} else {
							System.out.println(
									"\n" + skuId + " is not added because this item is currently out of stock");
							log.info("\n" + skuId + " is not added because this item is currently out of stock");
						}
					}
				}

			} else {
				System.out.println("\nError while invoking SearchService getStyleData API");
				log.error("\nError while invoking SearchService getStyleData API");
				AssertJUnit.fail("Error while invoking SearchService getStyleData API");
			}

			fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
			System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"
					+ fetchCartReqGen.respvalidate.returnresponseasstring());
			log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"
					+ fetchCartReqGen.respvalidate.returnresponseasstring());

			AssertJUnit.assertTrue("Error while invoking CheckoutService operationFetchCart API",
					fetchCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
							.contains(SUCCESS_STATUS_TYPE));
		}

		String skuId = fetchCartReqGen.respvalidate.NodeValue(CartNodes.CART_ITEM_ENTRIES_SELSIZE_SKUID.toString(),
				false);
		String itemId = checkoutTestsHelper.getItemIdFromSKUId(fetchCartReqGen, skuId,
				CheckoutDataProviderEnum.CART.name());

		RequestGenerator fetchWishlistReqGen = checkoutTestsHelper.operationFetchWishList(userName, password);
		System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"
				+ fetchWishlistReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"
				+ fetchWishlistReqGen.respvalidate.returnresponseasstring());

		AssertJUnit.assertTrue("Error while invoking CheckoutService operationFetchWishlist API",
				fetchWishlistReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
						.contains(SUCCESS_STATUS_TYPE));

		if (StringUtils.isBlank(checkoutTestsHelper.getItemIdFromSKUId(fetchCartReqGen, skuId,
				CheckoutDataProviderEnum.WISHLIST.name()))) {
			System.out.println(
					"\nPrinting skuId : " + skuId + " & itemId : " + itemId + " is going to wishlist from cart\n");
			log.info("\nPrinting skuId : " + skuId + " & itemId : " + itemId
					+ " is going is going to wishlist from cart\n");

			RequestGenerator moveItemToWishListReqGen = checkoutTestsHelper.moveItemToWishList(userName, password,
					itemId);
			System.out.println("\nPrinting CheckoutService moveItemToWishList API response :\n\n"
					+ moveItemToWishListReqGen.respvalidate.returnresponseasstring());
			log.info("\nPrinting CheckoutService moveItemToWishList API response :\n\n"
					+ moveItemToWishListReqGen.respvalidate.returnresponseasstring());

			AssertJUnit.assertTrue(
					"CheckoutService moveItemToWishList API response status code is not a success status code",
					moveItemToWishListReqGen.respvalidate.NodeValue(StatusNodes.STATUS_CODE.toString(), true)
							.contains(successStatusCode));

			AssertJUnit.assertTrue(
					"CheckoutService moveItemToWishList API response status message is not a success status message",
					moveItemToWishListReqGen.respvalidate.NodeValue(StatusNodes.STATUS_MESSAGE.toString(), true)
							.contains(successStatusMsg));

			AssertJUnit.assertTrue(
					"CheckoutService moveItemToWishList API response status type is not a success status type",
					moveItemToWishListReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
							.contains(successStatusType));

		} else {
			System.out.println(
					"\nPrinting skuId : " + skuId + " & itemId : " + itemId + " is already present in wishlist\n");
			log.info("\nPrinting skuId : " + skuId + " & itemId : " + itemId + " is already present in wishlist\n");
		}

		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;

		System.out.println(
				"\nTime taken to execute - TestCase - CheckoutServiceTests_moveItemToWishList_verifySuccessStatusResponse : "
						+ timeTaken + " seconds\n");
		log.info(
				"\nTime taken to execute - TestCase - CheckoutServiceTests_moveItemToWishList_verifySuccessStatusResponse : "
						+ timeTaken + " seconds\n");
	}

	/**
	 * This Test Case used to invoke CheckoutService changeFreeGift API and
	 * verify for success response
	 * 
	 * @param userName
	 * @param password
	 * @param successRespCode
	 */
	@Test(groups = { "Sanity", "Regression", "MiniRegression",
			"ExhaustiveRegression" }, dataProvider = "CartServiceDP_changeFreeGiftDataProvider_validateAPIResponse")
	public void CartServiceTests_changeFreeGift_verifySuccessResponse(String userName, String password,
			String successRespCode) {
		long startTime = Calendar.getInstance().getTimeInMillis();
		RequestGenerator fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
		System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"
				+ fetchCartReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"
				+ fetchCartReqGen.respvalidate.returnresponseasstring());

		AssertJUnit.assertTrue("Error while invoking CheckoutService operationFetchCart API",
				fetchCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
						.contains(SUCCESS_STATUS_TYPE));

		if (fetchCartReqGen.respvalidate.NodeValue(CartNodes.CART_TOTAL_COUNT.toString(), true).equals(EMPTY_VALUE)) {
			List<Integer> styleIdList = checkoutTestsHelper.performSeachServiceToGetStyleIds(CFG_SEARCH_PROD,
					CFG_SEARCH_QTY, CFG_SEARCH_RET_DOCS, CFG_SEARCH_IS_FACET);

			if (!CollectionUtils.isEmpty(styleIdList)) {
				for (Integer styleId : styleIdList) {
					System.out.println("\nPrinting the StyleId : " + styleId);
					log.info("\nPrinting the StyleId : " + styleId);

					RequestGenerator getStyleDataReqGen = checkoutTestsHelper
							.performGetStyleDataOperation(String.valueOf(styleId));
					System.out.println("\nPrinting StyleService API response :\n\n"
							+ getStyleDataReqGen.respvalidate.returnresponseasstring());
					log.info("\nPrinting StyleService API response :\n\n"
							+ getStyleDataReqGen.respvalidate.returnresponseasstring());

					AssertJUnit.assertTrue("Error while getting style data", getStyleDataReqGen.respvalidate
							.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));

					String response = getStyleDataReqGen.respvalidate.returnresponseasstring();
					List<Integer> ids = JsonPath.read(response, "$.data..styleOptions[*].id");

					for (int i = 0; i < ids.size(); i++) {
						String skuId = JsonPath.read(response, "$.data..styleOptions[" + i + "].skuId").toString();
						String inventoryCount = JsonPath
								.read(response, "$.data..styleOptions[" + i + "].inventoryCount").toString();
						String available = JsonPath.read(response, "$.data..styleOptions[" + i + "].available")
								.toString();

						if (!inventoryCount.equals(EMPTY_VALUE) && available.equals(TRUE_VALUE)) {
							fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
							System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"
									+ fetchCartReqGen.respvalidate.returnresponseasstring());
							log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"
									+ fetchCartReqGen.respvalidate.returnresponseasstring());

							if (StringUtils.isBlank(checkoutTestsHelper.getItemIdFromSKUId(fetchCartReqGen, skuId,
									CheckoutDataProviderEnum.CART.name()))) {
								RequestGenerator addItemToCartReqGen = checkoutTestsHelper.addItemToCart(userName,
										password, skuId, inventoryCount, ADD_OPERATION);
								System.out.println("\nPrinting CheckoutService addItemToCart API response :\n\n"
										+ addItemToCartReqGen.respvalidate.returnresponseasstring());
								log.info("\nPrinting CheckoutService addItemToCart API response :\n\n"
										+ addItemToCartReqGen.respvalidate.returnresponseasstring());

								AssertJUnit.assertTrue("Error while invoking CheckoutService addItemToCart API",
										addItemToCartReqGen.respvalidate
												.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
												.contains(SUCCESS_STATUS_TYPE));

								System.out.println(
										"\n" + skuId + " is available and it doesn't exists in cart so added to cart");
								log.info("\n" + skuId + " is available and it doesn't exists in cart so added to cart");
								break;

							} else {
								System.out.println(
										"\n" + skuId + " is not added because this item already exists in cart");
								log.error("\n" + skuId + " is not added because this item already exists in cart");
							}

						} else {
							System.out.println(
									"\n" + skuId + " is not added because this item is currently out of stock");
							log.info("\n" + skuId + " is not added because this item is currently out of stock");
						}
					}

				}

			} else {
				System.out.println("\nError while invoking SearchService API");
				log.error("\nError while invoking SearchService API");
				AssertJUnit.fail("Error while invoking SearchService API");
			}

			fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
			System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"
					+ fetchCartReqGen.respvalidate.returnresponseasstring());
			log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"
					+ fetchCartReqGen.respvalidate.returnresponseasstring());

			AssertJUnit.assertTrue("Error while invoking CheckoutService operationFetchCart API",
					fetchCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
							.contains(SUCCESS_STATUS_TYPE));
		}
		String skuId = fetchCartReqGen.respvalidate.NodeValue(CartNodes.CART_ITEM_ENTRIES_AVAIL_SIZES_SKUID.toString(),
				false);
		String itemId = checkoutTestsHelper.getItemIdFromSKUId(fetchCartReqGen, skuId,
				CheckoutDataProviderEnum.CART.name());

		System.out.println("\nPrinting skuId : " + skuId + " & itemId : " + itemId + " is going for changeFreeGift\n");
		log.info("\nPrinting skuId : " + skuId + " & itemId : " + itemId + " is going for changeFreeGift\n");

		RequestGenerator changeFreeGiftReqGen = checkoutTestsHelper.changeFreeGift(userName, password, itemId, skuId);
		System.out.println("\nPrinting CheckoutService changeFreeGift API response :\n\n"
				+ changeFreeGiftReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService changeFreeGift API response :\n\n"
				+ changeFreeGiftReqGen.respvalidate.returnresponseasstring());

		AssertJUnit.assertEquals("CheckoutService changeFreeGift API is not working", Integer.parseInt(successRespCode),
				changeFreeGiftReqGen.response.getStatus());

		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;

		System.out.println(
				"\nTime taken to execute - TestCase - CheckoutServiceTests_changeFreeGift_verifySuccessResponse : "
						+ timeTaken + " seconds\n");
		log.info("\nTime taken to execute - TestCase - CheckoutServiceTests_changeFreeGift_verifySuccessResponse : "
				+ timeTaken + " seconds\n");

	}

	/**
	 * This Test Case used to invoke CheckoutService changeFreeGift API and
	 * verify for success status response
	 * 
	 * @param userName
	 * @param password
	 * @param successStatusCode
	 * @param successStatusMsg
	 * @param successStatusType
	 */
	@Test(groups = { "Sanity", "Regression", "MiniRegression",
			"ExhaustiveRegression" }, dataProvider = "CartServiceDP_changeFreeGiftDataProvider_positiveCases")
	public void CartServiceTests_changeFreeGift_verifySuccessStatusResponse(String userName, String password,
			String successStatusCode, String successStatusMsg, String successStatusType) {
		long startTime = Calendar.getInstance().getTimeInMillis();
		RequestGenerator fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
		System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"
				+ fetchCartReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"
				+ fetchCartReqGen.respvalidate.returnresponseasstring());

		AssertJUnit.assertTrue("Error while invoking CheckoutService operationFetchCart API",
				fetchCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
						.contains(SUCCESS_STATUS_TYPE));

		if (fetchCartReqGen.respvalidate.NodeValue(CartNodes.CART_TOTAL_COUNT.toString(), true).equals(EMPTY_VALUE)) {
			List<Integer> styleIdList = checkoutTestsHelper.performSeachServiceToGetStyleIds(CFG_SEARCH_PROD,
					CFG_SEARCH_QTY, CFG_SEARCH_RET_DOCS, CFG_SEARCH_IS_FACET);

			if (!CollectionUtils.isEmpty(styleIdList)) {
				for (Integer styleId : styleIdList) {
					System.out.println("\nPrinting the StyleId : " + styleId);
					log.info("\nPrinting the StyleId : " + styleId);

					RequestGenerator getStyleDataReqGen = checkoutTestsHelper
							.performGetStyleDataOperation(String.valueOf(styleId));
					System.out.println("\nPrinting StyleService getStyleData API response :\n\n"
							+ getStyleDataReqGen.respvalidate.returnresponseasstring());
					log.info("\nPrinting StyleService getStyleData API response :\n\n"
							+ getStyleDataReqGen.respvalidate.returnresponseasstring());

					AssertJUnit.assertTrue("Error while getting style data", getStyleDataReqGen.respvalidate
							.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));

					String response = getStyleDataReqGen.respvalidate.returnresponseasstring();
					List<Integer> ids = JsonPath.read(response, "$.data..styleOptions[*].id");

					for (int i = 0; i < ids.size(); i++) {
						String skuId = JsonPath.read(response, "$.data..styleOptions[" + i + "].skuId").toString();
						String inventoryCount = JsonPath
								.read(response, "$.data..styleOptions[" + i + "].inventoryCount").toString();
						String available = JsonPath.read(response, "$.data..styleOptions[" + i + "].available")
								.toString();

						if (!inventoryCount.equals(EMPTY_VALUE) && available.equals(TRUE_VALUE)) {
							fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
							System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"
									+ fetchCartReqGen.respvalidate.returnresponseasstring());
							log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"
									+ fetchCartReqGen.respvalidate.returnresponseasstring());

							if (StringUtils.isBlank(checkoutTestsHelper.getItemIdFromSKUId(fetchCartReqGen, skuId,
									CheckoutDataProviderEnum.CART.name()))) {
								RequestGenerator addItemToCartReqGen = checkoutTestsHelper.addItemToCart(userName,
										password, skuId, inventoryCount, ADD_OPERATION);
								System.out.println("\nPrinting CheckoutService addItemToCart API response :\n\n"
										+ addItemToCartReqGen.respvalidate.returnresponseasstring());
								log.info("\nPrinting CheckoutService addItemToCart API response :\n\n"
										+ addItemToCartReqGen.respvalidate.returnresponseasstring());

								AssertJUnit.assertTrue("Error while invoking CheckoutService addItemToCart API",
										addItemToCartReqGen.respvalidate
												.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
												.contains(SUCCESS_STATUS_TYPE));

								System.out.println(
										"\n" + skuId + " is available and it doesn't exists in cart so added to cart");
								log.info("\n" + skuId + " is available and it doesn't exists in cart so added to cart");
								break;

							} else {
								System.out.println(
										"\n" + skuId + " is not added because this item already exists in cart");
								log.error("\n" + skuId + " is not added because this item already exists in cart");
							}

						} else {
							System.out.println(
									"\n" + skuId + " is not added because this item is currently out of stock");
							log.info("\n" + skuId + " is not added because this item is currently out of stock");
						}
					}

				}

			} else {
				System.out.println("\nError while invoking SearchService API");
				log.error("\nError while invoking SearchService API");
				AssertJUnit.fail("Error while invoking SearchService API");
			}

			fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
			System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"
					+ fetchCartReqGen.respvalidate.returnresponseasstring());
			log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"
					+ fetchCartReqGen.respvalidate.returnresponseasstring());

			AssertJUnit.assertTrue("Error while invoking CheckoutService operationFetchCart API",
					fetchCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
							.contains(SUCCESS_STATUS_TYPE));
		}

		String skuId = fetchCartReqGen.respvalidate.NodeValue(CartNodes.CART_ITEM_ENTRIES_AVAIL_SIZES_SKUID.toString(),
				false);
		String itemId = checkoutTestsHelper.getItemIdFromSKUId(fetchCartReqGen, skuId,
				CheckoutDataProviderEnum.CART.name());

		System.out.println("\nPrinting skuId : " + skuId + " & itemId : " + itemId + " is going for changeFreeGift\n");
		log.info("\nPrinting skuId : " + skuId + " & itemId : " + itemId + " is going for changeFreeGift\n");

		RequestGenerator changeFreeGiftReqGen = checkoutTestsHelper.changeFreeGift(userName, password, itemId, skuId);
		System.out.println("\nPrinting changeFreeGift API response :\n\n"
				+ changeFreeGiftReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting changeFreeGift API response :\n\n"
				+ changeFreeGiftReqGen.respvalidate.returnresponseasstring());

		AssertJUnit.assertTrue("CheckoutService changeFreeGift API response status code is not a success status code",
				changeFreeGiftReqGen.respvalidate.NodeValue(StatusNodes.STATUS_CODE.toString(), true)
						.contains(successStatusCode));

		AssertJUnit.assertTrue(
				"CheckoutService changeFreeGift API response status message is not a success status message",
				changeFreeGiftReqGen.respvalidate.NodeValue(StatusNodes.STATUS_MESSAGE.toString(), true)
						.contains(successStatusMsg));

		AssertJUnit.assertTrue("CheckoutService changeFreeGift API response status type is not a success status type",
				changeFreeGiftReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
						.contains(successStatusType));

		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;

		System.out.println(
				"\nTime taken to execute - TestCase - CheckoutServiceTests_changeFreeGift_verifySuccessStatusResponse : "
						+ timeTaken + " seconds\n");
		log.info(
				"\nTime taken to execute - TestCase - CheckoutServiceTests_changeFreeGift_verifySuccessStatusResponse : "
						+ timeTaken + " seconds\n");

	}

	/**
	 * This Test Case used to invoke CheckoutService operationCustomize API and
	 * verify for success response
	 * 
	 * @param userName
	 * @param password
	 * @param customizeOpn
	 * @param customizeName
	 * @param customizeNumber
	 * @param doCustomize
	 * @param successRespCode
	 */
	@Test(groups = { "Sanity", "Regression", "MiniRegression",
			"ExhaustiveRegression" }, dataProvider = "CartServiceDP_operationCustomizeDataProvider_validateAPIResponse")
	public void CartServiceTests_operationCustomize_verifySuccessResponse(String userName, String password,
			String customizeOpn, String customizeName, String customizeNumber, String doCustomize,
			String successRespCode) {
		long startTime = Calendar.getInstance().getTimeInMillis();
		String addItemQty = "1";
		RequestGenerator fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
		System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"
				+ fetchCartReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"
				+ fetchCartReqGen.respvalidate.returnresponseasstring());

		AssertJUnit.assertTrue("Error while invoking CheckoutService operationFetchCart API",
				fetchCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
						.contains(SUCCESS_STATUS_TYPE));

		if (fetchCartReqGen.respvalidate.NodeValue(CartNodes.CART_TOTAL_COUNT.toString(), true).equals(EMPTY_VALUE)) {

			List<Integer> styleIdList = checkoutTestsHelper.performSeachServiceToGetStyleIds(OCC_SEARCH_PROD,
					OCC_SEARCH_QTY, OCC_SEARCH_RET_DOCS, OCC_SEARCH_IS_FACET);

			if (!CollectionUtils.isEmpty(styleIdList)) {
				for (Integer styleId : styleIdList) {
					System.out.println("\nPrinting the StyleId : " + styleId);
					log.info("\nPrinting the StyleId : " + styleId);

					RequestGenerator getStyleDataReqGen = checkoutTestsHelper
							.performGetStyleDataOperation(String.valueOf(styleId));
					System.out.println("\nPrinting StyleService getStyleData API response :\n\n"
							+ getStyleDataReqGen.respvalidate.returnresponseasstring());
					log.info("\nPrinting StyleService getStyleData API response :\n\n"
							+ getStyleDataReqGen.respvalidate.returnresponseasstring());

					AssertJUnit.assertTrue("Error while getting style data", getStyleDataReqGen.respvalidate
							.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));

					String response = getStyleDataReqGen.respvalidate.returnresponseasstring();
					List<Integer> ids = JsonPath.read(response, "$.data..styleOptions[*].id");

					for (int i = 0; i < ids.size(); i++) {
						String skuId = JsonPath.read(response, "$.data..styleOptions[" + i + "].skuId").toString();
						String inventoryCount = JsonPath
								.read(response, "$.data..styleOptions[" + i + "].inventoryCount").toString();
						String available = JsonPath.read(response, "$.data..styleOptions[" + i + "].available")
								.toString();

						if (!inventoryCount.equals(EMPTY_VALUE) && available.equals(TRUE_VALUE)) {
							fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
							System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"
									+ fetchCartReqGen.respvalidate.returnresponseasstring());
							log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"
									+ fetchCartReqGen.respvalidate.returnresponseasstring());

							if (StringUtils.isBlank(checkoutTestsHelper.getItemIdFromSKUId(fetchCartReqGen, skuId,
									CheckoutDataProviderEnum.CART.name()))) {
								RequestGenerator addItemToCartReqGen = checkoutTestsHelper.addItemToCart(userName,
										password, skuId, String.valueOf(addItemQty), ADD_OPERATION);
								System.out.println("\nPrinting CheckoutService addItemToCart API response :\n\n"
										+ addItemToCartReqGen.respvalidate.returnresponseasstring());
								log.info("\nPrinting CheckoutService addItemToCart API response :\n\n"
										+ addItemToCartReqGen.respvalidate.returnresponseasstring());

								AssertJUnit.assertTrue("Error while invoking CheckoutService addItemToCart API",
										addItemToCartReqGen.respvalidate
												.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
												.contains(SUCCESS_STATUS_TYPE));

								System.out.println(
										"\n" + skuId + " is available and it doesn't exists in cart so added to cart");
								log.info("\n" + skuId + " is available and it doesn't exists in cart so added to cart");
								break;

							} else {
								System.out.println(
										"\n" + skuId + " is not added because this item already exists in cart");
								log.error("\n" + skuId + " is not added because this item already exists in cart");
							}

						} else {
							System.out.println(
									"\n" + skuId + " is not added because this item is currently out of stock");
							log.info("\n" + skuId + " is not added because this item is currently out of stock");
						}
					}
				}

			} else {
				System.out.println("\nError while invoking SearchService getStyleData API");
				log.error("\nError while invoking SearchService getStyleData API");
				AssertJUnit.fail("Error while invoking SearchService getStyleData API");
			}

			fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
			System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"
					+ fetchCartReqGen.respvalidate.returnresponseasstring());
			log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"
					+ fetchCartReqGen.respvalidate.returnresponseasstring());

			AssertJUnit.assertTrue("Error while invoking CheckoutService operationFetchCart API",
					fetchCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
							.contains(SUCCESS_STATUS_TYPE));
		}

		String skuId = fetchCartReqGen.respvalidate.NodeValue(CartNodes.CART_ITEM_ENTRIES_AVAIL_SIZES_SKUID.toString(),
				false);
		String itemId = checkoutTestsHelper.getItemIdFromSKUId(fetchCartReqGen, skuId,
				CheckoutDataProviderEnum.CART.name());

		System.out.println(
				"\nPrinting skuId : " + skuId + " & itemId : " + itemId + " is going for operationCustomize\n");
		log.info("\nPrinting skuId : " + skuId + " & itemId : " + itemId + " is going for operationCustomize\n");

		RequestGenerator operationCustomizeReqGen = checkoutTestsHelper.operationCustomize(userName, password, itemId,
				customizeOpn, customizeName, customizeNumber, doCustomize);
		System.out.println("\nPrinting CheckoutService operationCustomize API response :\n\n"
				+ operationCustomizeReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService operationCustomize API response :\n\n"
				+ operationCustomizeReqGen.respvalidate.returnresponseasstring());

		AssertJUnit.assertEquals("CheckoutService operationCustomize API is not working",
				Integer.parseInt(successRespCode), operationCustomizeReqGen.response.getStatus());

		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;

		System.out.println(
				"\nTime taken to execute - TestCase - CheckoutServiceTests_operationCustomize_verifySuccessResponse : "
						+ timeTaken + " seconds\n");
		log.info("\nTime taken to execute - TestCase - CheckoutServiceTests_operationCustomize_verifySuccessResponse : "
				+ timeTaken + " seconds\n");
	}

	/**
	 * This Test Case used to invoke CheckoutService operationCustomize API and
	 * verify for success status response
	 * 
	 * @param userName
	 * @param password
	 * @param customizeOpn
	 * @param customizeName
	 * @param customizeNumber
	 * @param doCustomize
	 * @param successStatusCode
	 * @param successStatusMsg
	 * @param successStatusType
	 */
	@Test(groups = { "Sanity", "Regression", "MiniRegression",
			"ExhaustiveRegression" }, dataProvider = "CartServiceDP_operationCustomizeDataProvider_positiveCases")
	public void CartServiceTests_operationCustomize_verifySuccessStatusResponse(String userName, String password,
			String customizeOpn, String customizeName, String customizeNumber, String doCustomize,
			String successStatusCode, String successStatusMsg, String successStatusType) {
		long startTime = Calendar.getInstance().getTimeInMillis();
		String addItemQty = "1";
		RequestGenerator fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
		System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"
				+ fetchCartReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"
				+ fetchCartReqGen.respvalidate.returnresponseasstring());

		AssertJUnit.assertTrue("Error while invoking operationFetchCart API", fetchCartReqGen.respvalidate
				.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));

		if (fetchCartReqGen.respvalidate.NodeValue(CartNodes.CART_TOTAL_COUNT.toString(), true).equals(EMPTY_VALUE)) {
			List<Integer> styleIdList = checkoutTestsHelper.performSeachServiceToGetStyleIds(OCC_SEARCH_PROD,
					OCC_SEARCH_QTY, OCC_SEARCH_RET_DOCS, OCC_SEARCH_IS_FACET);

			if (!CollectionUtils.isEmpty(styleIdList)) {
				for (Integer styleId : styleIdList) {
					System.out.println("\nPrinting the StyleId : " + styleId);
					log.info("\nPrinting the StyleId : " + styleId);

					RequestGenerator getStyleDataReqGen = checkoutTestsHelper
							.performGetStyleDataOperation(String.valueOf(styleId));
					System.out.println("\nPrinting StyleService getStyleData API response :\n\n"
							+ getStyleDataReqGen.respvalidate.returnresponseasstring());
					log.info("\nPrinting StyleService getStyleData API response :\n\n"
							+ getStyleDataReqGen.respvalidate.returnresponseasstring());

					AssertJUnit.assertTrue("Error while getting style data", getStyleDataReqGen.respvalidate
							.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));

					String response = getStyleDataReqGen.respvalidate.returnresponseasstring();
					List<Integer> ids = JsonPath.read(response, "$.data..styleOptions[*].id");

					for (int i = 0; i < ids.size(); i++) {
						String skuId = JsonPath.read(response, "$.data..styleOptions[" + i + "].skuId").toString();
						String inventoryCount = JsonPath
								.read(response, "$.data..styleOptions[" + i + "].inventoryCount").toString();
						String available = JsonPath.read(response, "$.data..styleOptions[" + i + "].available")
								.toString();

						if (!inventoryCount.equals(EMPTY_VALUE) && available.equals(TRUE_VALUE)) {
							fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
							System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"
									+ fetchCartReqGen.respvalidate.returnresponseasstring());
							log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"
									+ fetchCartReqGen.respvalidate.returnresponseasstring());

							if (StringUtils.isBlank(checkoutTestsHelper.getItemIdFromSKUId(fetchCartReqGen, skuId,
									CheckoutDataProviderEnum.CART.name()))) {
								RequestGenerator addItemToCartReqGen = checkoutTestsHelper.addItemToCart(userName,
										password, skuId, String.valueOf(addItemQty), ADD_OPERATION);
								System.out.println("\nPrinting CheckoutService addItemToCart API response :\n\n"
										+ addItemToCartReqGen.respvalidate.returnresponseasstring());
								log.info("\nPrinting CheckoutService addItemToCart API response :\n\n"
										+ addItemToCartReqGen.respvalidate.returnresponseasstring());

								AssertJUnit.assertTrue("Error while invoking CheckoutService addItemToCart API",
										addItemToCartReqGen.respvalidate
												.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
												.contains(SUCCESS_STATUS_TYPE));

								System.out.println(
										"\n" + skuId + " is available and it doesn't exists in cart so added to cart");
								log.info("\n" + skuId + " is available and it doesn't exists in cart so added to cart");
								break;

							} else {
								System.out.println(
										"\n" + skuId + " is not added because this item already exists in cart");
								log.error("\n" + skuId + " is not added because this item already exists in cart");
							}

						} else {
							System.out.println(
									"\n" + skuId + " is not added because this item is currently out of stock");
							log.info("\n" + skuId + " is not added because this item is currently out of stock");
						}
					}
				}

			} else {
				System.out.println("\nError while invoking SearchService getStyleData API");
				log.error("\nError while invoking SearchService getStyleData API");
				AssertJUnit.fail("Error while invoking SearchService getStyleData API");
			}

			fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
			System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"
					+ fetchCartReqGen.respvalidate.returnresponseasstring());
			log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"
					+ fetchCartReqGen.respvalidate.returnresponseasstring());

			AssertJUnit.assertTrue("Error while invoking CheckoutService operationFetchCart API",
					fetchCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
							.contains(SUCCESS_STATUS_TYPE));
		}

		String skuId = fetchCartReqGen.respvalidate.NodeValue(CartNodes.CART_ITEM_ENTRIES_AVAIL_SIZES_SKUID.toString(),
				false);
		String itemId = checkoutTestsHelper.getItemIdFromSKUId(fetchCartReqGen, skuId,
				CheckoutDataProviderEnum.CART.name());

		System.out.println(
				"\nPrinting skuId : " + skuId + " & itemId : " + itemId + " is going for operationCustomize\n");
		log.info("\nPrinting skuId : " + skuId + " & itemId : " + itemId + " is going for operationCustomize\n");

		RequestGenerator operationCustomizeReqGen = checkoutTestsHelper.operationCustomize(userName, password, itemId,
				customizeOpn, customizeName, customizeNumber, doCustomize);
		System.out.println("\nPrinting CheckoutService operationCustomize API response :\n\n"
				+ operationCustomizeReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService operationCustomize API response :\n\n"
				+ operationCustomizeReqGen.respvalidate.returnresponseasstring());

		AssertJUnit.assertTrue(
				"CheckoutService operationCustomize API response status code is not a success status code",
				operationCustomizeReqGen.respvalidate.NodeValue(StatusNodes.STATUS_CODE.toString(), true)
						.contains(successStatusCode));

		AssertJUnit.assertTrue(
				"CheckoutService operationCustomize API response status message is not a success status message",
				operationCustomizeReqGen.respvalidate.NodeValue(StatusNodes.STATUS_MESSAGE.toString(), true)
						.contains(successStatusMsg));

		AssertJUnit.assertTrue(
				"CheckoutService operationCustomize API response status type is not a success status type",
				operationCustomizeReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
						.contains(successStatusType));

		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;

		System.out.println(
				"\nTime taken to execute - TestCase - CheckoutServiceTests_operationCustomize_verifySuccessStatusResponse : "
						+ timeTaken + " seconds\n");
		log.info(
				"\nTime taken to execute - TestCase - CheckoutServiceTests_operationCustomize_verifySuccessStatusResponse : "
						+ timeTaken + " seconds\n");
	}

	/**
	 * This Test Case used to invoke CheckoutService operationFetchWishList API
	 * and verify for success response
	 * 
	 * @param userName
	 * @param password
	 * @param successRespCode
	 *//*
		 * @Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression",
		 * "MiniRegression", "ExhaustiveRegression" }, dataProvider=
		 * "CartServiceDP_operationFetchWishListDataProvider_validateAPIResponse")
		 * public void
		 * CartServiceTests_operationFetchWishList_verifySuccessResponse(String
		 * userName, String password, String successRespCode) { long startTime =
		 * Calendar.getInstance().getTimeInMillis(); String addItemQty = "1";
		 * RequestGenerator fetchWishlistReqGen =
		 * checkoutTestsHelper.operationFetchWishList(userName, password);
		 * System.out.println(
		 * "\nPrinting CheckoutService operationFetchWishList API response :\n\n"
		 * +fetchWishlistReqGen.respvalidate.returnresponseasstring());
		 * log.info(
		 * "\nPrinting CheckoutService operationFetchWishList API response :\n\n"
		 * +fetchWishlistReqGen.respvalidate.returnresponseasstring());
		 * 
		 * 
		 * AssertJUnit.assertEquals(
		 * "CheckoutService operationFetchWishlist API is not working",
		 * Integer.parseInt(successRespCode),
		 * fetchWishlistReqGen.response.getStatus());
		 * 
		 * String wishlistCount =
		 * fetchWishlistReqGen.respvalidate.NodeValue(WishListNodes.
		 * WISHLIST_TOTAL_COUNT.toString(), true); System.out.println(
		 * "total count of wishist is "+ wishlistCount);
		 * 
		 * 
		 * if(fetchWishlistReqGen.respvalidate.NodeValue(WishListNodes.
		 * WISHLIST_TOTAL_COUNT.toString(), true).equals(EMPTY_VALUE)) {
		 * System.out.println(
		 * "As no items are present in user's wishlist , adding new items to wishlist"
		 * ); List<Integer> styleIdList =
		 * checkoutTestsHelper.performSeachServiceToGetStyleIds(AWL_SEARCH_PROD,
		 * AWL_SEARCH_QTY, AWL_SEARCH_RET_DOCS, AWL_SEARCH_IS_FACET);
		 * 
		 * if(!CollectionUtils.isEmpty(styleIdList)) { for(Integer styleId :
		 * styleIdList) { System.out.println("\nPrinting the StyleId : "
		 * +styleId); log.info("\nPrinting the StyleId : "+styleId);
		 * 
		 * RequestGenerator getStyleDataReqGen =
		 * checkoutTestsHelper.performGetStyleDataOperation(String.valueOf(
		 * styleId)); System.out.println(
		 * "\nPrinting StyleService getStyleData API response :\n\n"
		 * +getStyleDataReqGen.respvalidate.returnresponseasstring()); log.info(
		 * "\nPrinting StyleService getStyleData API response :\n\n"
		 * +getStyleDataReqGen.respvalidate.returnresponseasstring());
		 * 
		 * AssertJUnit.assertTrue("Error while getting style data",
		 * getStyleDataReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.
		 * toString(), true).contains(SUCCESS_STATUS_TYPE));
		 * 
		 * String response =
		 * getStyleDataReqGen.respvalidate.returnresponseasstring();
		 * List<Integer> ids = JsonPath.read(response,
		 * "$.data..styleOptions[*].id");
		 * 
		 * for(int i = 0 ; i < ids.size(); i++) { String skuId =
		 * JsonPath.read(response,
		 * "$.data..styleOptions["+i+"].skuId").toString(); String
		 * inventoryCount = JsonPath.read(response,
		 * "$.data..styleOptions["+i+"].inventoryCount").toString(); String
		 * available = JsonPath.read(response,
		 * "$.data..styleOptions["+i+"].available").toString();
		 * 
		 * if(!inventoryCount.equals(EMPTY_VALUE) &&
		 * available.equals(TRUE_VALUE)) { int invCount =
		 * Integer.parseInt(inventoryCount); int qty =
		 * Integer.parseInt(addItemQty); int itemQtyToAdd = invCount <= qty ?
		 * invCount : qty;
		 * 
		 * RequestGenerator addItemToWishListReqGen =
		 * checkoutTestsHelper.addItemToWishList(userName, password, skuId,
		 * null, String.valueOf(itemQtyToAdd), "ADD");
		 * 
		 * System.out.println(
		 * "\nPrinting CheckoutService addItemToWishList API response :\n\n"
		 * +addItemToWishListReqGen.respvalidate.returnresponseasstring());
		 * log.info(
		 * "\nPrinting CheckoutService addItemToWishList API response :\n\n"
		 * +addItemToWishListReqGen.respvalidate.returnresponseasstring());
		 * 
		 * AssertJUnit.assertEquals(
		 * "CheckoutService addItemToWishList API is not working",
		 * Integer.parseInt(successRespCode),
		 * addItemToWishListReqGen.response.getStatus());
		 * 
		 * System.out.println("\n"+skuId+
		 * " is available but not in wishlist so added to wishlist");
		 * log.info("\n"+skuId+
		 * " is available but not in wishlist so added to wishlist"); break;
		 * 
		 * } else { System.out.println("\n"+skuId+
		 * " is not added because this item is currently out of stock");
		 * log.info("\n"+skuId+
		 * " is not added because this item is currently out of stock"); }
		 * 
		 * } } }else { System.out.println(
		 * "\nError while invoking SearchService getStyleData API"); log.error(
		 * "\nError while invoking SearchService getStyleData API");
		 * AssertJUnit.fail(
		 * "Error while invoking SearchService getStyleData API"); }
		 * 
		 * RequestGenerator fetchWishlistReqGen1 =
		 * checkoutTestsHelper.operationFetchWishList(userName, password);
		 * System.out.println(
		 * "\nPrinting CheckoutService operationFetchWishList API response :\n\n"
		 * +fetchWishlistReqGen1.respvalidate.returnresponseasstring());
		 * log.info(
		 * "\nPrinting CheckoutService operationFetchWishList API response :\n\n"
		 * +fetchWishlistReqGen1.respvalidate.returnresponseasstring());
		 * 
		 * AssertJUnit.assertEquals(
		 * "CheckoutService operationFetchWishlist API is not working",
		 * Integer.parseInt(successRespCode),
		 * fetchWishlistReqGen1.response.getStatus());
		 * 
		 * String count =
		 * fetchWishlistReqGen.respvalidate.NodeValue(WishListNodes.
		 * WISHLIST_TOTAL_COUNT.toString(), true); System.out.println(
		 * "total count of wishist is "+ count);
		 * 
		 * }
		 * 
		 * long endTime = Calendar.getInstance().getTimeInMillis(); double
		 * timeTaken = (endTime - startTime)/1000.0;
		 * 
		 * System.out.println(
		 * "\nTime taken to execute - TestCase - CheckoutServiceTests_operationFetchWishList_verifySuccessResponse : "
		 * +timeTaken+" seconds\n"); log.info(
		 * "\nTime taken to execute - TestCase - CheckoutServiceTests_operationFetchWishList_verifySuccessResponse : "
		 * +timeTaken+" seconds\n");
		 * 
		 * }
		 * 
		 * /** This Test Case used to invoke CheckoutService
		 * operationFetchWishList API and verify for success status response
		 * 
		 * @param userName
		 * 
		 * @param password
		 * 
		 * @param successStatusCode
		 * 
		 * @param successStatusMsg
		 * 
		 * @param successStatusType
		 */
	/*
	 * @Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression",
	 * "MiniRegression", "ExhaustiveRegression" }, dataProvider=
	 * "CartServiceDP_operationFetchWishListDataProvider_positiveCases") public
	 * void CartServiceTests_operationFetchWishList_verifySuccessStatusResponse(
	 * String userName, String password, String successStatusCode, String
	 * successStatusMsg, String successStatusType) { long startTime =
	 * Calendar.getInstance().getTimeInMillis(); String addItemQty ="1";
	 * RequestGenerator fetchWishlistReqGen =
	 * checkoutTestsHelper.operationFetchWishList(userName, password);
	 * System.out.println(
	 * "\nPrinting CheckoutService operationFetchWishList API response :\n\n"
	 * +fetchWishlistReqGen.respvalidate.returnresponseasstring()); log.info(
	 * "\nPrinting CheckoutService operationFetchWishList API response :\n\n"
	 * +fetchWishlistReqGen.respvalidate.returnresponseasstring());
	 * 
	 * if(fetchWishlistReqGen.respvalidate.NodeValue(WishListNodes.
	 * WISHLIST_TOTAL_COUNT.toString(), true).equals(EMPTY_VALUE)) {
	 * List<Integer> styleIdList =
	 * checkoutTestsHelper.performSeachServiceToGetStyleIds(AWL_SEARCH_PROD,
	 * AWL_SEARCH_QTY, AWL_SEARCH_RET_DOCS, AWL_SEARCH_IS_FACET);
	 * if(!CollectionUtils.isEmpty(styleIdList)) { for(Integer styleId :
	 * styleIdList) { System.out.println("\nPrinting the StyleId : "+styleId);
	 * log.info("\nPrinting the StyleId : "+styleId);
	 * 
	 * RequestGenerator getStyleDataReqGen =
	 * checkoutTestsHelper.performGetStyleDataOperation(String.valueOf(styleId))
	 * ; System.out.println(
	 * "\nPrinting StyleService getStyleData API response :\n\n"
	 * +getStyleDataReqGen.respvalidate.returnresponseasstring()); log.info(
	 * "\nPrinting StyleService getStyleData API response :\n\n"
	 * +getStyleDataReqGen.respvalidate.returnresponseasstring());
	 * 
	 * AssertJUnit.assertTrue("Error while getting style data",
	 * getStyleDataReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.
	 * toString(), true).contains(SUCCESS_STATUS_TYPE));
	 * 
	 * String response =
	 * getStyleDataReqGen.respvalidate.returnresponseasstring(); List<Integer>
	 * ids = JsonPath.read(response, "$.data..styleOptions[*].id");
	 * 
	 * for(int i = 0 ; i < ids.size(); i++) { String skuId =
	 * JsonPath.read(response, "$.data..styleOptions["+i+"].skuId").toString();
	 * String inventoryCount = JsonPath.read(response,
	 * "$.data..styleOptions["+i+"].inventoryCount").toString(); String
	 * available = JsonPath.read(response,
	 * "$.data..styleOptions["+i+"].available").toString();
	 * 
	 * if(!inventoryCount.equals(EMPTY_VALUE) && available.equals(TRUE_VALUE)) {
	 * int invCount = Integer.parseInt(inventoryCount); int qty =
	 * Integer.parseInt(addItemQty); int itemQtyToAdd = invCount <= qty ?
	 * invCount : qty;
	 * 
	 * RequestGenerator addItemToWishListReqGen =
	 * checkoutTestsHelper.addItemToWishList(userName, password, skuId, null,
	 * String.valueOf(itemQtyToAdd), "ADD");
	 * 
	 * System.out.println(
	 * "\nPrinting CheckoutService addItemToWishList API response :\n\n"
	 * +addItemToWishListReqGen.respvalidate.returnresponseasstring());
	 * log.info(
	 * "\nPrinting CheckoutService addItemToWishList API response :\n\n"
	 * +addItemToWishListReqGen.respvalidate.returnresponseasstring());
	 * 
	 * AssertJUnit.assertEquals(
	 * "CheckoutService addItemToWishList API is not working", 200,
	 * addItemToWishListReqGen.response.getStatus());
	 * 
	 * System.out.println("\n"+skuId+
	 * " is available but not in wishlist so added to wishlist");
	 * log.info("\n"+skuId+
	 * " is available but not in wishlist so added to wishlist"); break;
	 * 
	 * } else { System.out.println("\n"+skuId+
	 * " is not added because this item is currently out of stock");
	 * log.info("\n"+skuId+
	 * " is not added because this item is currently out of stock"); } } } }else
	 * { System.out.println(
	 * "\nError while invoking SearchService getStyleData API"); log.error(
	 * "\nError while invoking SearchService getStyleData API");
	 * AssertJUnit.fail("Error while invoking SearchService getStyleData API");
	 * }
	 * 
	 * RequestGenerator fetchWishlistReqGen1 =
	 * checkoutTestsHelper.operationFetchWishList(userName, password);
	 * System.out.println(
	 * "\nPrinting CheckoutService operationFetchWishList API response :\n\n"
	 * +fetchWishlistReqGen1.respvalidate.returnresponseasstring()); log.info(
	 * "\nPrinting CheckoutService operationFetchWishList API response :\n\n"
	 * +fetchWishlistReqGen1.respvalidate.returnresponseasstring());
	 * 
	 * 
	 * AssertJUnit.assertTrue(
	 * "CheckoutService operationFetchWishList API response status code is not a success status code"
	 * , fetchWishlistReqGen1.respvalidate.NodeValue(StatusNodes.STATUS_CODE.
	 * toString(), true).contains(successStatusCode));
	 * 
	 * AssertJUnit.assertTrue(
	 * "CheckoutService operationFetchWishList API response status message is not a success status message"
	 * , fetchWishlistReqGen1.respvalidate.NodeValue(StatusNodes.STATUS_MESSAGE.
	 * toString(), true).contains(successStatusMsg));
	 * 
	 * AssertJUnit.assertTrue(
	 * "CheckoutService operationFetchWishList API response status type is not a success status type"
	 * , fetchWishlistReqGen1.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.
	 * toString(), true).contains(successStatusType));
	 * 
	 * 
	 * } else { AssertJUnit.assertTrue(
	 * "CheckoutService operationFetchWishList API response status code is not a success status code"
	 * , fetchWishlistReqGen.respvalidate.NodeValue(StatusNodes.STATUS_CODE.
	 * toString(), true).contains(successStatusCode));
	 * 
	 * AssertJUnit.assertTrue(
	 * "CheckoutService operationFetchWishList API response status message is not a success status message"
	 * , fetchWishlistReqGen.respvalidate.NodeValue(StatusNodes.STATUS_MESSAGE.
	 * toString(), true).contains(successStatusMsg));
	 * 
	 * AssertJUnit.assertTrue(
	 * "CheckoutService operationFetchWishList API response status type is not a success status type"
	 * , fetchWishlistReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.
	 * toString(), true).contains(successStatusType));
	 * 
	 * }
	 * 
	 * long endTime = Calendar.getInstance().getTimeInMillis(); double timeTaken
	 * = (endTime - startTime)/1000.0;
	 * 
	 * System.out.println(
	 * "\nTime taken to execute - TestCase - CheckoutServiceTests_operationFetchWishList_verifySuccessStatusResponse : "
	 * +timeTaken+" seconds\n"); log.info(
	 * "\nTime taken to execute - TestCase - CheckoutServiceTests_operationFetchWishList_verifySuccessStatusResponse : "
	 * +timeTaken+" seconds\n");
	 * 
	 * }
	 * 
	 * /** This Test Case used to invoke CheckoutService
	 * addItemToWishListUsingSkuId API and verify for success response
	 * 
	 * @param userName
	 * 
	 * @param password
	 * 
	 * @param addItemQty
	 * 
	 * @param addOperation
	 * 
	 * @param successRespCode
	 *//*
		 * @Test(groups = { "Sanity", "Regression", "MiniRegression",
		 * "ExhaustiveRegression" }, dataProvider=
		 * "CartServiceDP_addItemToWishListUsingSkuIdDataProvider_validateAPIResponse")
		 * public void
		 * CartServiceTests_addItemToWishListUsingSkuId_verifySuccessResponse(
		 * String userName, String password, String addItemQty, String
		 * addOperation, String successRespCode) { long startTime =
		 * Calendar.getInstance().getTimeInMillis(); List<Integer> styleIdList =
		 * checkoutTestsHelper.performSeachServiceToGetStyleIds(AWL_SEARCH_PROD,
		 * AWL_SEARCH_QTY, AWL_SEARCH_RET_DOCS, AWL_SEARCH_IS_FACET);
		 * 
		 * if(!CollectionUtils.isEmpty(styleIdList)) { for(Integer styleId :
		 * styleIdList) { System.out.println("\nPrinting the StyleId : "
		 * +styleId); log.info("\nPrinting the StyleId : "+styleId);
		 * 
		 * RequestGenerator getStyleDataReqGen =
		 * checkoutTestsHelper.performGetStyleDataOperation(String.valueOf(
		 * styleId)); System.out.println(
		 * "\nPrinting StyleService getStyleData API response :\n\n"
		 * +getStyleDataReqGen.respvalidate.returnresponseasstring()); log.info(
		 * "\nPrinting StyleService getStyleData API response :\n\n"
		 * +getStyleDataReqGen.respvalidate.returnresponseasstring());
		 * 
		 * AssertJUnit.assertTrue("Error while getting style data",
		 * getStyleDataReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.
		 * toString(), true).contains(SUCCESS_STATUS_TYPE));
		 * 
		 * String response =
		 * getStyleDataReqGen.respvalidate.returnresponseasstring();
		 * List<Integer> ids = JsonPath.read(response,
		 * "$.data..styleOptions[*].id");
		 * 
		 * for(int i = 0 ; i < ids.size(); i++) { String skuId =
		 * JsonPath.read(response,
		 * "$.data..styleOptions["+i+"].skuId").toString(); String
		 * inventoryCount = JsonPath.read(response,
		 * "$.data..styleOptions["+i+"].inventoryCount").toString(); String
		 * available = JsonPath.read(response,
		 * "$.data..styleOptions["+i+"].available").toString();
		 * 
		 * if(!inventoryCount.equals(EMPTY_VALUE) &&
		 * available.equals(TRUE_VALUE)) { RequestGenerator fetchWishlistReqGen
		 * = checkoutTestsHelper.operationFetchWishList(userName, password);
		 * System.out.println(
		 * "\nPrinting CheckoutService operationFetchWishList API response :\n\n"
		 * +fetchWishlistReqGen.respvalidate.returnresponseasstring());
		 * log.info(
		 * "\nPrinting CheckoutService operationFetchWishList API response :\n\n"
		 * +fetchWishlistReqGen.respvalidate.returnresponseasstring());
		 * 
		 * if(StringUtils.isBlank(checkoutTestsHelper.getItemIdFromSKUId(
		 * fetchWishlistReqGen, skuId,
		 * CheckoutDataProviderEnum.WISHLIST.name()))) { int invCount =
		 * Integer.parseInt(inventoryCount); int qty =
		 * Integer.parseInt(addItemQty); int itemQtyToAdd = invCount <= qty ?
		 * invCount : qty;
		 * 
		 * RequestGenerator addItemToWishListReqGen =
		 * checkoutTestsHelper.addItemToWishList(userName, password, skuId,
		 * null, String.valueOf(itemQtyToAdd), addOperation);
		 * 
		 * System.out.println(
		 * "\nPrinting CheckoutService addItemToWishList API response :\n\n"
		 * +addItemToWishListReqGen.respvalidate.returnresponseasstring());
		 * log.info(
		 * "\nPrinting CheckoutService addItemToWishList API response :\n\n"
		 * +addItemToWishListReqGen.respvalidate.returnresponseasstring());
		 * 
		 * AssertJUnit.assertEquals(
		 * "CheckoutService addItemToWishList API is not working",
		 * Integer.parseInt(successRespCode),
		 * addItemToWishListReqGen.response.getStatus());
		 * 
		 * System.out.println("\n"+skuId+
		 * " is available but not in wishlist so added to wishlist");
		 * log.info("\n"+skuId+
		 * " is available but not in wishlist so added to wishlist"); break;
		 * 
		 * } else { System.out.println("\n"+skuId +
		 * " is not added because this item already exists in wishlist");
		 * log.error("\n"+skuId +
		 * " is not added because this item already exists in wishlist"); }
		 * 
		 * } else { System.out.println("\n"+skuId+
		 * " is not added because this item is currently out of stock");
		 * log.info("\n"+skuId+
		 * " is not added because this item is currently out of stock"); } } }
		 * 
		 * } else { System.out.println(
		 * "\nError while invoking SearchService getStyleData API"); log.error(
		 * "\nError while invoking SearchService getStyleData API");
		 * AssertJUnit.fail(
		 * "Error while invoking SearchService getStyleData API"); }
		 * 
		 * long endTime = Calendar.getInstance().getTimeInMillis(); double
		 * timeTaken = (endTime - startTime)/1000.0;
		 * 
		 * System.out.println(
		 * "\nTime taken to execute - TestCase - CheckoutServiceTests_addItemToWishListUsingSkuId_verifySuccessResponse : "
		 * +timeTaken+" seconds\n"); log.info(
		 * "\nTime taken to execute - TestCase - CheckoutServiceTests_addItemToWishListUsingSkuId_verifySuccessResponse : "
		 * +timeTaken+" seconds\n"); }
		 * 
		 * /** This Test Case used to invoke CheckoutService
		 * addItemToWishListUsingSkuId API and verify for success status
		 * response
		 * 
		 * @param userName
		 * 
		 * @param password
		 * 
		 * @param addItemQty
		 * 
		 * @param addOperation
		 * 
		 * @param successStatusCode
		 * 
		 * @param successStatusMsg
		 * 
		 * @param successStatusType
		 *//*
		 * @Test(groups = { "Sanity", "Regression", "MiniRegression",
		 * "ExhaustiveRegression" }, dataProvider=
		 * "CartServiceDP_addItemToWishListUsingSkuIdDataProvider_positiveCases")
		 * public void
		 * CartServiceTests_addItemToWishListUsingSkuId_verifySuccessStatusResponse
		 * (String userName, String password, String addItemQty, String
		 * addOperation, String successStatusCode, String successStatusMsg,
		 * String successStatusType) { long startTime =
		 * Calendar.getInstance().getTimeInMillis(); List<Integer> styleIdList =
		 * checkoutTestsHelper.performSeachServiceToGetStyleIds(AWL_SEARCH_PROD,
		 * AWL_SEARCH_QTY, AWL_SEARCH_RET_DOCS, AWL_SEARCH_IS_FACET);
		 * 
		 * if(!CollectionUtils.isEmpty(styleIdList)) { for(Integer styleId :
		 * styleIdList) { System.out.println("\nPrinting the StyleId : "
		 * +styleId); log.info("\nPrinting the StyleId : "+styleId);
		 * 
		 * RequestGenerator getStyleDataReqGen =
		 * checkoutTestsHelper.performGetStyleDataOperation(String.valueOf(
		 * styleId)); System.out.println(
		 * "\nPrinting StyleService getStyleData API response :\n\n"
		 * +getStyleDataReqGen.respvalidate.returnresponseasstring()); log.info(
		 * "\nPrinting StyleService getStyleData API response :\n\n"
		 * +getStyleDataReqGen.respvalidate.returnresponseasstring());
		 * 
		 * AssertJUnit.assertTrue("Error while getting style data",
		 * getStyleDataReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.
		 * toString(), true).contains(SUCCESS_STATUS_TYPE));
		 * 
		 * String response =
		 * getStyleDataReqGen.respvalidate.returnresponseasstring();
		 * List<Integer> ids = JsonPath.read(response,
		 * "$.data..styleOptions[*].id");
		 * 
		 * for(int i = 0; i < ids.size(); i++) { String skuId =
		 * JsonPath.read(response,
		 * "$.data..styleOptions["+i+"].skuId").toString(); String
		 * inventoryCount = JsonPath.read(response,
		 * "$.data..styleOptions["+i+"].inventoryCount").toString(); String
		 * available = JsonPath.read(response,
		 * "$.data..styleOptions["+i+"].available").toString();
		 * 
		 * if(!inventoryCount.equals(EMPTY_VALUE) &&
		 * available.equals(TRUE_VALUE)) { RequestGenerator fetchWishlistReqGen
		 * = checkoutTestsHelper.operationFetchWishList(userName, password);
		 * System.out.println(
		 * "\nPrinting CheckoutService operationFetchWishList API response :\n\n"
		 * +fetchWishlistReqGen.respvalidate.returnresponseasstring());
		 * log.info(
		 * "\nPrinting CheckoutService operationFetchWishList API response :\n\n"
		 * +fetchWishlistReqGen.respvalidate.returnresponseasstring());
		 * 
		 * if(StringUtils.isBlank(checkoutTestsHelper.getItemIdFromSKUId(
		 * fetchWishlistReqGen, skuId,
		 * CheckoutDataProviderEnum.WISHLIST.name()))) { int invCount =
		 * Integer.parseInt(inventoryCount); int qty =
		 * Integer.parseInt(addItemQty); int itemQtyToAdd = invCount <= qty ?
		 * invCount : qty;
		 * 
		 * RequestGenerator addItemToWishListReqGen =
		 * checkoutTestsHelper.addItemToWishList(userName, password, skuId,
		 * null, String.valueOf(itemQtyToAdd), addOperation);
		 * System.out.println(
		 * "\nPrinting CheckoutService addItemToWishList API response :\n\n"
		 * +addItemToWishListReqGen.respvalidate.returnresponseasstring());
		 * log.info(
		 * "\nPrinting CheckoutService addItemToWishList API response :\n\n"
		 * +addItemToWishListReqGen.respvalidate.returnresponseasstring());
		 * 
		 * AssertJUnit.assertTrue(
		 * "CheckoutService addItemToWishList API response status code is not a success status code"
		 * , addItemToWishListReqGen.respvalidate.NodeValue(StatusNodes.
		 * STATUS_CODE.toString(), true).contains(successStatusCode));
		 * 
		 * AssertJUnit.assertTrue(
		 * "CheckoutService addItemToWishList API response status message is not a success status message"
		 * , addItemToWishListReqGen.respvalidate.NodeValue(StatusNodes.
		 * STATUS_MESSAGE.toString(), true).contains(successStatusMsg));
		 * 
		 * AssertJUnit.assertTrue(
		 * "CheckoutService addItemToWishList API response status type is not a success status type"
		 * , addItemToWishListReqGen.respvalidate.NodeValue(StatusNodes.
		 * STATUS_TYPE.toString(), true).contains(successStatusType));
		 * 
		 * System.out.println("\n"+skuId+
		 * " is available but not in wishlist so added to wishlist");
		 * log.info("\n"+skuId+
		 * " is available but not in wishlist so added to wishlist"); break;
		 * 
		 * } else { System.out.println("\n"+skuId +
		 * " is not added because this item already exists in wishlist");
		 * log.error("\n"+skuId +
		 * " is not added because this item already exists in wishlist"); }
		 * 
		 * } else { System.out.println("\n"+skuId+
		 * " is not added because this item is currently out of stock");
		 * log.info("\n"+skuId+
		 * " is not added because this item is currently out of stock"); } } }
		 * 
		 * } else { System.out.println(
		 * "\nError while invoking SearchService API"); log.error(
		 * "\nError while invoking SearchService API"); AssertJUnit.fail(
		 * "Error while invoking SearchService API"); }
		 * 
		 * long endTime = Calendar.getInstance().getTimeInMillis(); double
		 * timeTaken = (endTime - startTime)/1000.0;
		 * 
		 * System.out.println(
		 * "\nTime taken to execute - TestCase - CheckoutServiceTests_addItemToWishListUsingSkuId_verifySuccessStatusResponse : "
		 * +timeTaken+" seconds\n"); log.info(
		 * "\nTime taken to execute - TestCase - CheckoutServiceTests_addItemToWishListUsingSkuId_verifySuccessStatusResponse : "
		 * +timeTaken+" seconds\n"); }
		 * 
		 * /** This Test Case used to invoke CheckoutService
		 * addItemToWishListUsingStyleId API and verify for success response
		 * 
		 * @param userName
		 * 
		 * @param password
		 * 
		 * @param addItemQty
		 * 
		 * @param addOperation
		 * 
		 * @param successRespCode
		 *//*
			 * @Test(groups = { "Sanity", "Regression", "MiniRegression",
			 * "ExhaustiveRegression" }, dataProvider=
			 * "CartServiceDP_addItemToWishListUsingStyleIdDataProvider_validateAPIResponse")
			 * public void
			 * CartServiceTests_addItemToWishListUsingStyleId_verifySuccessResponse
			 * (String userName, String password, String addItemQty, String
			 * addOperation, String successRespCode) { long startTime =
			 * Calendar.getInstance().getTimeInMillis(); List<Integer>
			 * styleIdList =
			 * checkoutTestsHelper.performSeachServiceToGetStyleIds(
			 * AWL_SEARCH_PROD, AWL_SEARCH_QTY, AWL_SEARCH_RET_DOCS,
			 * AWL_SEARCH_IS_FACET);
			 * 
			 * if(!CollectionUtils.isEmpty(styleIdList)) { for(Integer styleId :
			 * styleIdList) { System.out.println("\nPrinting the StyleId : "
			 * +styleId); log.info("\nPrinting the StyleId : "+styleId);
			 * 
			 * RequestGenerator getStyleDataReqGen =
			 * checkoutTestsHelper.performGetStyleDataOperation(String.valueOf(
			 * styleId)); System.out.println(
			 * "\nPrinting StyleService getStyleData API response :\n\n"
			 * +getStyleDataReqGen.respvalidate.returnresponseasstring());
			 * log.info(
			 * "\nPrinting StyleService getStyleData API response :\n\n"
			 * +getStyleDataReqGen.respvalidate.returnresponseasstring());
			 * 
			 * AssertJUnit.assertTrue("Error while getting style data",
			 * getStyleDataReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE
			 * .toString(), true).contains(SUCCESS_STATUS_TYPE));
			 * 
			 * String response =
			 * getStyleDataReqGen.respvalidate.returnresponseasstring();
			 * List<Integer> ids = JsonPath.read(response,
			 * "$.data..styleOptions[*].id");
			 * 
			 * for(int i = 0 ; i < ids.size(); i++) { String skuId =
			 * JsonPath.read(response,
			 * "$.data..styleOptions["+i+"].skuId").toString(); String
			 * inventoryCount = JsonPath.read(response,
			 * "$.data..styleOptions["+i+"].inventoryCount").toString(); String
			 * available = JsonPath.read(response,
			 * "$.data..styleOptions["+i+"].available").toString();
			 * 
			 * if(!inventoryCount.equals(EMPTY_VALUE) &&
			 * available.equals(TRUE_VALUE)) { RequestGenerator
			 * fetchWishlistReqGen =
			 * checkoutTestsHelper.operationFetchWishList(userName, password);
			 * System.out.println(
			 * "\nPrinting CheckoutService operationFetchWishList API response :\n\n"
			 * +fetchWishlistReqGen.respvalidate.returnresponseasstring());
			 * log.info(
			 * "\nPrinting CheckoutService operationFetchWishList API response :\n\n"
			 * +fetchWishlistReqGen.respvalidate.returnresponseasstring());
			 * 
			 * if(StringUtils.isBlank(checkoutTestsHelper.getItemIdFromSKUId(
			 * fetchWishlistReqGen, skuId,
			 * CheckoutDataProviderEnum.WISHLIST.name()))) { int invCount =
			 * Integer.parseInt(inventoryCount); int qty =
			 * Integer.parseInt(addItemQty); int itemQtyToAdd = invCount <= qty
			 * ? invCount : qty;
			 * 
			 * RequestGenerator addItemToWishListReqGen =
			 * checkoutTestsHelper.addItemToWishList(userName, password, null,
			 * String.valueOf(styleId), String.valueOf(itemQtyToAdd),
			 * addOperation); System.out.println(
			 * "\nPrinting CheckoutService addItemToWishList API response :\n\n"
			 * +addItemToWishListReqGen.respvalidate.returnresponseasstring());
			 * log.info(
			 * "\nPrinting CheckoutService addItemToWishList API response :\n\n"
			 * +addItemToWishListReqGen.respvalidate.returnresponseasstring());
			 * 
			 * AssertJUnit.assertEquals(
			 * "CheckoutService addItemToWishListUsingStyleId API is not working"
			 * , Integer.parseInt(successRespCode),
			 * addItemToWishListReqGen.response.getStatus());
			 * 
			 * System.out.println("\n"+skuId+
			 * " is available but not in wishlist so added to wishlist");
			 * log.info("\n"+skuId+
			 * " is available but not in wishlist so added to wishlist"); break;
			 * 
			 * } else { System.out.println("\n"+skuId +
			 * " is not added because this item already exists in wishlist");
			 * log.error("\n"+skuId +
			 * " is not added because this item already exists in wishlist"); }
			 * 
			 * } else { System.out.println("\n"+skuId+
			 * " is not added because this item is currently out of stock");
			 * log.info("\n"+skuId+
			 * " is not added because this item is currently out of stock"); } }
			 * }
			 * 
			 * } else { System.out.println(
			 * "\nError while invoking SearchService API"); log.error(
			 * "\nError while invoking SearchService API"); AssertJUnit.fail(
			 * "Error while invoking SearchService API"); }
			 * 
			 * long endTime = Calendar.getInstance().getTimeInMillis(); double
			 * timeTaken = (endTime - startTime)/1000.0;
			 * 
			 * System.out.println(
			 * "\nTime taken to execute - TestCase - CheckoutServiceTests_addItemToWishListUsingStyleId_verifySuccessResponse : "
			 * +timeTaken+" seconds\n"); log.info(
			 * "\nTime taken to execute - TestCase - CheckoutServiceTests_addItemToWishListUsingStyleId_verifySuccessResponse : "
			 * +timeTaken+" seconds\n");
			 * 
			 * }
			 * 
			 * 
			 * /** This Test Case used to invoke CheckoutService
			 * addItemToWishListUsingStyleId API and verify for success status
			 * response
			 * 
			 * @param userName
			 * 
			 * @param password
			 * 
			 * @param addItemQty
			 * 
			 * @param addOperation
			 * 
			 * @param successStatusCode
			 * 
			 * @param successStatusMsg
			 * 
			 * @param successStatusType
			 *//*
			 * @Test(groups = { "Sanity", "Regression", "MiniRegression",
			 * "ExhaustiveRegression" }, dataProvider=
			 * "CartServiceDP_addItemToWishListUsingStyleIdDataProvider_positiveCases")
			 * public void
			 * CartServiceTests_addItemToWishListUsingStyleId_verifySuccessStatusResponse
			 * (String userName, String password, String addItemQty, String
			 * addOperation, String successStatusCode, String successStatusMsg,
			 * String successStatusType) { long startTime =
			 * Calendar.getInstance().getTimeInMillis(); List<Integer>
			 * styleIdList =
			 * checkoutTestsHelper.performSeachServiceToGetStyleIds(
			 * AWL_SEARCH_PROD, AWL_SEARCH_QTY, AWL_SEARCH_RET_DOCS,
			 * AWL_SEARCH_IS_FACET);
			 * 
			 * if(!CollectionUtils.isEmpty(styleIdList)) { for(Integer styleId :
			 * styleIdList) { System.out.println("\nPrinting the StyleId : "
			 * +styleId); log.info("\nPrinting the StyleId : "+styleId);
			 * 
			 * RequestGenerator getStyleDataReqGen =
			 * checkoutTestsHelper.performGetStyleDataOperation(String.valueOf(
			 * styleId)); System.out.println(
			 * "\nPrinting StyleService getStyleData API response :\n\n"
			 * +getStyleDataReqGen.respvalidate.returnresponseasstring());
			 * log.info(
			 * "\nPrinting StyleService API getStyleData response :\n\n"
			 * +getStyleDataReqGen.respvalidate.returnresponseasstring());
			 * 
			 * AssertJUnit.assertTrue("Error while getting style data",
			 * getStyleDataReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE
			 * .toString(), true).contains(SUCCESS_STATUS_TYPE));
			 * 
			 * String response =
			 * getStyleDataReqGen.respvalidate.returnresponseasstring();
			 * List<Integer> ids = JsonPath.read(response,
			 * "$.data..styleOptions[*].id");
			 * 
			 * for(int i = 0 ; i < ids.size(); i++) { String skuId =
			 * JsonPath.read(response,
			 * "$.data..styleOptions["+i+"].skuId").toString(); String
			 * inventoryCount = JsonPath.read(response,
			 * "$.data..styleOptions["+i+"].inventoryCount").toString(); String
			 * available = JsonPath.read(response,
			 * "$.data..styleOptions["+i+"].available").toString();
			 * 
			 * if(!inventoryCount.equals(EMPTY_VALUE) &&
			 * available.equals(TRUE_VALUE)) { RequestGenerator
			 * fetchWishlistReqGen =
			 * checkoutTestsHelper.operationFetchWishList(userName, password);
			 * System.out.println(
			 * "\nPrinting CheckoutService operationFetchWishList API response :\n\n"
			 * +fetchWishlistReqGen.respvalidate.returnresponseasstring());
			 * log.info(
			 * "\nPrinting CheckoutService operationFetchWishList API response :\n\n"
			 * +fetchWishlistReqGen.respvalidate.returnresponseasstring());
			 * 
			 * if(StringUtils.isBlank(checkoutTestsHelper.getItemIdFromSKUId(
			 * fetchWishlistReqGen, skuId,
			 * CheckoutDataProviderEnum.WISHLIST.name()))) { int invCount =
			 * Integer.parseInt(inventoryCount); int qty =
			 * Integer.parseInt(addItemQty); int itemQtyToAdd = invCount <= qty
			 * ? invCount : qty;
			 * 
			 * RequestGenerator addItemToWishListReqGen =
			 * checkoutTestsHelper.addItemToWishList(userName, password, null,
			 * String.valueOf(styleId), String.valueOf(itemQtyToAdd),
			 * addOperation); System.out.println(
			 * "\nPrinting addItemToWishList API response :\n\n"
			 * +addItemToWishListReqGen.respvalidate.returnresponseasstring());
			 * log.info("\nPrinting addItemToWishList API response :\n\n"
			 * +addItemToWishListReqGen.respvalidate.returnresponseasstring());
			 * 
			 * AssertJUnit.assertTrue(
			 * "CheckoutService addItemToWishList API response status code is not a success status code"
			 * , addItemToWishListReqGen.respvalidate.NodeValue(StatusNodes.
			 * STATUS_CODE.toString(), true).contains(successStatusCode));
			 * 
			 * AssertJUnit.assertTrue(
			 * "CheckoutService addItemToWishList API response status message is not a success status message"
			 * , addItemToWishListReqGen.respvalidate.NodeValue(StatusNodes.
			 * STATUS_MESSAGE.toString(), true).contains(successStatusMsg));
			 * 
			 * AssertJUnit.assertTrue(
			 * "CheckoutService addItemToWishList API response status type is not a success status type"
			 * , addItemToWishListReqGen.respvalidate.NodeValue(StatusNodes.
			 * STATUS_TYPE.toString(), true).contains(successStatusType));
			 * 
			 * System.out.println("\n"+skuId+
			 * " is available but not in wishlist so added to wishlist");
			 * log.info("\n"+skuId+
			 * " is available but not in wishlist so added to wishlist"); break;
			 * 
			 * } else { System.out.println("\n"+skuId +
			 * " is not added because this item already exists in wishlist");
			 * log.error("\n"+skuId +
			 * " is not added because this item already exists in wishlist"); }
			 * 
			 * } else { System.out.println("\n"+skuId+
			 * " is not added because this item is currently out of stock");
			 * log.info("\n"+skuId+
			 * " is not added because this item is currently out of stock"); } }
			 * }
			 * 
			 * } else { System.out.println(
			 * "\nError while invoking SearchService API"); log.error(
			 * "\nError while invoking SearchService API"); AssertJUnit.fail(
			 * "Error while invoking SearchService API"); }
			 * 
			 * long endTime = Calendar.getInstance().getTimeInMillis(); double
			 * timeTaken = (endTime - startTime)/1000.0;
			 * 
			 * System.out.println(
			 * "\nTime taken to execute - TestCase - CheckoutServiceTests_addItemToWishListUsingStyleId_verifySuccessStatusResponse : "
			 * +timeTaken+" seconds\n"); log.info(
			 * "\nTime taken to execute - TestCase - CheckoutServiceTests_addItemToWishListUsingStyleId_verifySuccessStatusResponse : "
			 * +timeTaken+" seconds\n");
			 * 
			 * }
			 * 
			 * /** This Test Case used to invoke CheckoutService
			 * updateItemInWishList API and verify for success response
			 * 
			 * @param userName
			 * 
			 * @param password
			 * 
			 * @param itemQtyToUpdate
			 * 
			 * @param updateOpn
			 * 
			 * @param successRespCode
			 *//*
				 * @Test(groups = { "Sanity", "Regression", "MiniRegression",
				 * "ExhaustiveRegression" }, dataProvider=
				 * "CartServiceDP_updateItemInWishListDataProvider_validateAPIResponse")
				 * public void
				 * CartServiceTests_updateItemInWishList_verifySuccessResponse(
				 * String userName, String password, String itemQtyToUpdate,
				 * String updateOpn, String successRespCode) { long startTime =
				 * Calendar.getInstance().getTimeInMillis(); RequestGenerator
				 * fetchWishlistReqGen =
				 * checkoutTestsHelper.operationFetchWishList(userName,
				 * password); System.out.println(
				 * "\nPrinting CheckoutService operationFetchWishList API response :\n\n"
				 * +fetchWishlistReqGen.respvalidate.returnresponseasstring());
				 * log.info(
				 * "\nPrinting CheckoutService operationFetchWishList API response :\n\n"
				 * +fetchWishlistReqGen.respvalidate.returnresponseasstring());
				 * 
				 * AssertJUnit.assertTrue(
				 * "Error while invoking CheckoutService operationFetchWishList API"
				 * , fetchWishlistReqGen.respvalidate.NodeValue(StatusNodes.
				 * STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));
				 * 
				 * if(fetchWishlistReqGen.respvalidate.NodeValue(WishListNodes.
				 * WISHLIST_TOTAL_COUNT.toString(), true).equals(EMPTY_VALUE)) {
				 * List<Integer> styleIdList =
				 * checkoutTestsHelper.performSeachServiceToGetStyleIds(
				 * UWL_SEARCH_PROD, UWL_SEARCH_QTY, UWL_SEARCH_RET_DOCS,
				 * UWL_SEARCH_IS_FACET); System.out.println("style id list :"
				 * +styleIdList); if(!CollectionUtils.isEmpty(styleIdList)) {
				 * for(Integer styleId : styleIdList) { System.out.println(
				 * "\nPrinting the StyleId : "+styleId); log.info(
				 * "\nPrinting the StyleId : "+styleId);
				 * 
				 * RequestGenerator getStyleDataReqGen =
				 * checkoutTestsHelper.performGetStyleDataOperation(String.
				 * valueOf(styleId)); System.out.println(
				 * "\nPrinting StyleService getStyleData API response :\n\n"
				 * +getStyleDataReqGen.respvalidate.returnresponseasstring());
				 * log.info(
				 * "\nPrinting StyleService getStyleData API response :\n\n"
				 * +getStyleDataReqGen.respvalidate.returnresponseasstring());
				 * 
				 * AssertJUnit.assertTrue("Error while getting style data",
				 * getStyleDataReqGen.respvalidate.NodeValue(StatusNodes.
				 * STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));
				 * 
				 * String response =
				 * getStyleDataReqGen.respvalidate.returnresponseasstring();
				 * List<Integer> ids = JsonPath.read(response,
				 * "$.data..styleOptions[*].id");
				 * 
				 * for(int i = 0 ; i < ids.size(); i++) { String skuId =
				 * JsonPath.read(response,
				 * "$.data..styleOptions["+i+"].skuId").toString(); String
				 * inventoryCount = JsonPath.read(response,
				 * "$.data..styleOptions["+i+"].inventoryCount").toString();
				 * String available = JsonPath.read(response,
				 * "$.data..styleOptions["+i+"].available").toString();
				 * 
				 * if(!inventoryCount.equals(EMPTY_VALUE) &&
				 * available.equals(TRUE_VALUE)) { fetchWishlistReqGen =
				 * checkoutTestsHelper.operationFetchWishList(userName,
				 * password); System.out.println(
				 * "\nPrinting CheckoutService operationFetchWishList API response :\n\n"
				 * +fetchWishlistReqGen.respvalidate.returnresponseasstring());
				 * log.info(
				 * "\nPrinting CheckoutService operationFetchWishList API response :\n\n"
				 * +fetchWishlistReqGen.respvalidate.returnresponseasstring());
				 * 
				 * AssertJUnit.assertTrue(
				 * "Error while invoking CheckoutService operationFetchWishList API"
				 * , fetchWishlistReqGen.respvalidate.NodeValue(StatusNodes.
				 * STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));
				 * 
				 * if(StringUtils.isBlank(checkoutTestsHelper.getItemIdFromSKUId
				 * (fetchWishlistReqGen, skuId,
				 * CheckoutDataProviderEnum.WISHLIST.name()))) {
				 * RequestGenerator addItemToWishlistReqGen =
				 * checkoutTestsHelper.addItemToWishList(userName, password,
				 * skuId, null, "1", ADD_OPERATION); System.out.println(
				 * "\nPrinting CheckoutService addItemToCart API response :\n\n"
				 * +addItemToWishlistReqGen.respvalidate.returnresponseasstring(
				 * )); log.info(
				 * "\nPrinting CheckoutService addItemToCart API response :\n\n"
				 * +addItemToWishlistReqGen.respvalidate.returnresponseasstring(
				 * ));
				 * 
				 * AssertJUnit.assertTrue(
				 * "Error while invoking CheckoutService addItemToWishList API",
				 * addItemToWishlistReqGen.respvalidate.NodeValue(StatusNodes.
				 * STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));
				 * 
				 * System.out.println("\n"+skuId+
				 * " is available but not in cart so added to cart");
				 * log.info("\n"+skuId+
				 * " is available but not in cart so added to cart"); break;
				 * 
				 * } else { System.out.println("\n"+skuId +
				 * " is not added because this item already exists in cart");
				 * log.error("\n"+skuId +
				 * " is not added because this item already exists in cart"); }
				 * 
				 * } else { System.out.println("\n"+skuId+
				 * " is not added because this item is currently out of stock");
				 * log.info("\n"+skuId+
				 * " is not added because this item is currently out of stock");
				 * } } }
				 * 
				 * } else { System.out.println(
				 * "\nError while invoking SearchService API"); log.error(
				 * "\nError while invoking SearchService API");
				 * AssertJUnit.fail("Error while invoking SearchService API"); }
				 * 
				 * fetchWishlistReqGen =
				 * checkoutTestsHelper.operationFetchWishList(userName,
				 * password); System.out.println(
				 * "\nPrinting operationFetchWishList API response :\n\n"
				 * +fetchWishlistReqGen.respvalidate.returnresponseasstring());
				 * log.info(
				 * "\nPrinting operationFetchWishList API response :\n\n"
				 * +fetchWishlistReqGen.respvalidate.returnresponseasstring());
				 * 
				 * AssertJUnit.assertTrue(
				 * "Error while invoking CheckoutService operationFetchWishList API"
				 * , fetchWishlistReqGen.respvalidate.NodeValue(StatusNodes.
				 * STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));
				 * 
				 * } String skuId =
				 * fetchWishlistReqGen.respvalidate.NodeValue(WishListNodes.
				 * WISHLIST_ITEM_ENTRIES_AVAILSIZES_SKUID.toString(), false);
				 * String itemId =
				 * checkoutTestsHelper.getItemIdFromSKUId(fetchWishlistReqGen,
				 * skuId, CheckoutDataProviderEnum.WISHLIST.name());
				 * 
				 * System.out.println("\nPrinting skuId : "+skuId+" & itemId : "
				 * +itemId+" & quantity : "+itemQtyToUpdate+
				 * " is going for updation\n"); log.info("\nPrinting skuId : "
				 * +skuId+" & itemId : "+itemId+" & quantity : "
				 * +itemQtyToUpdate+" is going for updation\n");
				 * 
				 * RequestGenerator updateItemInWishlistReqGen =
				 * checkoutTestsHelper.updateItemInWishList(userName, password,
				 * itemId, skuId, String.valueOf(itemQtyToUpdate), updateOpn);
				 * System.out.println(
				 * "\nPrinting CheckoutService updateItemInWishList API response :\n\n"
				 * +updateItemInWishlistReqGen.respvalidate.
				 * returnresponseasstring()); log.info(
				 * "\nPrinting CheckoutService updateItemInWishList API response :\n\n"
				 * +updateItemInWishlistReqGen.respvalidate.
				 * returnresponseasstring());
				 * 
				 * AssertJUnit.assertEquals(
				 * "CheckoutService updateItemInWishList API is not working",
				 * Integer.parseInt(successRespCode),
				 * updateItemInWishlistReqGen.response.getStatus());
				 * 
				 * long endTime = Calendar.getInstance().getTimeInMillis();
				 * double timeTaken = (endTime - startTime)/1000.0;
				 * 
				 * System.out.println(
				 * "\nTime taken to execute - TestCase - CheckoutServiceTests_updateItemInWishList_verifySuccessResponse : "
				 * +timeTaken+" seconds\n"); log.info(
				 * "\nTime taken to execute - TestCase - CheckoutServiceTests_updateItemInWishList_verifySuccessResponse : "
				 * +timeTaken+" seconds\n"); }
				 * 
				 * 
				 * /** This Test Case used to invoke CheckoutService
				 * updateItemInWishList API and verify for success status
				 * response
				 * 
				 * @param userName
				 * 
				 * @param password
				 * 
				 * @param itemQtyToUpdate
				 * 
				 * @param updateOpn
				 * 
				 * @param successStatusCode
				 * 
				 * @param successStatusMsg
				 * 
				 * @param successStatusType
				 *//*
				 * @Test(groups = { "Sanity", "Regression", "MiniRegression",
				 * "ExhaustiveRegression" }, dataProvider=
				 * "CartServiceDP_updateItemInWishListDataProvider_positiveCases")
				 * public void
				 * CartServiceTests_updateItemInWishList_verifySuccessStatusResponse
				 * (String userName, String password, String itemQtyToUpdate,
				 * String updateOpn, String successStatusCode, String
				 * successStatusMsg, String successStatusType) { long startTime
				 * = Calendar.getInstance().getTimeInMillis(); RequestGenerator
				 * fetchWishlistReqGen =
				 * checkoutTestsHelper.operationFetchWishList(userName,
				 * password); System.out.println(
				 * "\nPrinting CheckoutService operationFetchWishList API response :\n\n"
				 * +fetchWishlistReqGen.respvalidate.returnresponseasstring());
				 * log.info(
				 * "\nPrinting CheckoutService operationFetchWishList API response :\n\n"
				 * +fetchWishlistReqGen.respvalidate.returnresponseasstring());
				 * 
				 * AssertJUnit.assertTrue(
				 * "Error while invoking CheckoutService operationFetchWishList API"
				 * , fetchWishlistReqGen.respvalidate.NodeValue(StatusNodes.
				 * STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));
				 * 
				 * if(fetchWishlistReqGen.respvalidate.NodeValue(WishListNodes.
				 * WISHLIST_TOTAL_COUNT.toString(), true).equals(EMPTY_VALUE)) {
				 * List<Integer> styleIdList =
				 * checkoutTestsHelper.performSeachServiceToGetStyleIds(
				 * UWL_SEARCH_PROD, UWL_SEARCH_QTY, UWL_SEARCH_RET_DOCS,
				 * UWL_SEARCH_IS_FACET);
				 * 
				 * if(!CollectionUtils.isEmpty(styleIdList)) { for(Integer
				 * styleId : styleIdList) { System.out.println(
				 * "\nPrinting the StyleId : "+styleId); log.info(
				 * "\nPrinting the StyleId : "+styleId);
				 * 
				 * RequestGenerator getStyleDataReqGen =
				 * checkoutTestsHelper.performGetStyleDataOperation(String.
				 * valueOf(styleId)); System.out.println(
				 * "\nPrinting StyleService getStyleData API response :\n\n"
				 * +getStyleDataReqGen.respvalidate.returnresponseasstring());
				 * log.info(
				 * "\nPrinting StyleService API getStyleData response :\n\n"
				 * +getStyleDataReqGen.respvalidate.returnresponseasstring());
				 * 
				 * AssertJUnit.assertTrue("Error while getting style data",
				 * getStyleDataReqGen.respvalidate.NodeValue(StatusNodes.
				 * STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));
				 * 
				 * String response =
				 * getStyleDataReqGen.respvalidate.returnresponseasstring();
				 * List<Integer> ids = JsonPath.read(response,
				 * "$.data..styleOptions[*].id");
				 * 
				 * for(int i = 0; i < ids.size(); i++) { String skuId =
				 * JsonPath.read(response,
				 * "$.data..styleOptions["+i+"].skuId").toString(); String
				 * inventoryCount = JsonPath.read(response,
				 * "$.data..styleOptions["+i+"].inventoryCount").toString();
				 * String available = JsonPath.read(response,
				 * "$.data..styleOptions["+i+"].available").toString();
				 * 
				 * if(!inventoryCount.equals(EMPTY_VALUE) &&
				 * available.equals(TRUE_VALUE)) { fetchWishlistReqGen =
				 * checkoutTestsHelper.operationFetchWishList(userName,
				 * password); System.out.println(
				 * "\nPrinting CheckoutService operationFetchWishList API response :\n\n"
				 * +fetchWishlistReqGen.respvalidate.returnresponseasstring());
				 * log.info(
				 * "\nPrinting CheckoutService operationFetchWishList API response :\n\n"
				 * +fetchWishlistReqGen.respvalidate.returnresponseasstring());
				 * 
				 * AssertJUnit.assertTrue(
				 * "Error while invoking CheckoutService operationFetchWishList API"
				 * , fetchWishlistReqGen.respvalidate.NodeValue(StatusNodes.
				 * STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));
				 * 
				 * if(StringUtils.isBlank(checkoutTestsHelper.getItemIdFromSKUId
				 * (fetchWishlistReqGen, skuId,
				 * CheckoutDataProviderEnum.WISHLIST.name()))) {
				 * RequestGenerator addItemToWishlistReqGen =
				 * checkoutTestsHelper.addItemToWishList(userName, password,
				 * skuId, null, "1", ADD_OPERATION); System.out.println(
				 * "\nPrinting CheckoutService addItemToCart API response :\n\n"
				 * +addItemToWishlistReqGen.respvalidate.returnresponseasstring(
				 * )); log.info(
				 * "\nPrinting CheckoutService addItemToCart API response :\n\n"
				 * +addItemToWishlistReqGen.respvalidate.returnresponseasstring(
				 * ));
				 * 
				 * AssertJUnit.assertTrue(
				 * "Error while invoking CheckoutService addItemToWishList API",
				 * addItemToWishlistReqGen.respvalidate.NodeValue(StatusNodes.
				 * STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));
				 * 
				 * System.out.println("\n"+skuId+
				 * " is available but not in cart so added to cart");
				 * log.info("\n"+skuId+
				 * " is available but not in cart so added to cart"); break;
				 * 
				 * } else { System.out.println("\n"+skuId +
				 * " is not added because this item already exists in cart");
				 * log.error("\n"+skuId +
				 * " is not added because this item already exists in cart"); }
				 * 
				 * } else { System.out.println("\n"+skuId+
				 * " is not added because this item is currently out of stock");
				 * log.info("\n"+skuId+
				 * " is not added because this item is currently out of stock");
				 * } } }
				 * 
				 * } else { System.out.println(
				 * "\nError while invoking SearchService API"); log.error(
				 * "\nError while invoking SearchService API");
				 * AssertJUnit.fail("Error while invoking SearchService API"); }
				 * 
				 * fetchWishlistReqGen =
				 * checkoutTestsHelper.operationFetchWishList(userName,
				 * password); System.out.println(
				 * "\nPrinting CheckoutService operationFetchWishList API response :\n\n"
				 * +fetchWishlistReqGen.respvalidate.returnresponseasstring());
				 * log.info(
				 * "\nPrinting CheckoutService operationFetchWishList API response :\n\n"
				 * +fetchWishlistReqGen.respvalidate.returnresponseasstring());
				 * 
				 * AssertJUnit.assertTrue(
				 * "Error while invoking CheckoutService operationFetchWishList API"
				 * , fetchWishlistReqGen.respvalidate.NodeValue(StatusNodes.
				 * STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));
				 * 
				 * } String skuId =
				 * fetchWishlistReqGen.respvalidate.NodeValue(WishListNodes.
				 * WISHLIST_ITEM_ENTRIES_AVAILSIZES_SKUID.toString(), false);
				 * String itemId =
				 * checkoutTestsHelper.getItemIdFromSKUId(fetchWishlistReqGen,
				 * skuId, CheckoutDataProviderEnum.WISHLIST.name());
				 * 
				 * System.out.println("\nPrinting skuId : "+skuId+" & itemId : "
				 * +itemId+" & quantity : "+itemQtyToUpdate+
				 * " is going for updation\n"); log.info("\nPrinting skuId : "
				 * +skuId+" & itemId : "+itemId+" & quantity : "
				 * +itemQtyToUpdate+" is going for updation\n");
				 * 
				 * RequestGenerator updateItemInWishlistReqGen =
				 * checkoutTestsHelper.updateItemInWishList(userName, password,
				 * itemId, skuId, String.valueOf(itemQtyToUpdate), updateOpn);
				 * System.out.println(
				 * "\nPrinting CheckoutService updateItemInWishList API response :\n\n"
				 * +updateItemInWishlistReqGen.respvalidate.
				 * returnresponseasstring()); log.info(
				 * "\nPrinting CheckoutService updateItemInWishList API response :\n\n"
				 * +updateItemInWishlistReqGen.respvalidate.
				 * returnresponseasstring());
				 * 
				 * AssertJUnit.assertTrue(
				 * "CheckoutService updateItemInWishList API response status code is not a success status code"
				 * ,
				 * updateItemInWishlistReqGen.respvalidate.NodeValue(StatusNodes
				 * .STATUS_CODE.toString(), true).contains(successStatusCode));
				 * 
				 * AssertJUnit.assertTrue(
				 * "CheckoutService updateItemInWishList API response status message is not a success status message"
				 * ,
				 * updateItemInWishlistReqGen.respvalidate.NodeValue(StatusNodes
				 * .STATUS_MESSAGE.toString(),
				 * true).contains(successStatusMsg));
				 * 
				 * AssertJUnit.assertTrue(
				 * "CheckoutService updateItemInWishList API response status type is not a success status type"
				 * ,
				 * updateItemInWishlistReqGen.respvalidate.NodeValue(StatusNodes
				 * .STATUS_TYPE.toString(), true).contains(successStatusType));
				 * 
				 * long endTime = Calendar.getInstance().getTimeInMillis();
				 * double timeTaken = (endTime - startTime)/1000.0;
				 * 
				 * System.out.println(
				 * "\nTime taken to execute - TestCase - CheckoutServiceTests_updateItemInWishList_verifySuccessStatusResponse : "
				 * +timeTaken+" seconds\n"); log.info(
				 * "\nTime taken to execute - TestCase - CheckoutServiceTests_updateItemInWishList_verifySuccessStatusResponse : "
				 * +timeTaken+" seconds\n"); }
				 * 
				 * /** This Test Case used to invoke CheckoutService
				 * removeItemFromWishList API and verify for success response
				 * 
				 * @param userName
				 * 
				 * @param password
				 * 
				 * @param delOperation
				 * 
				 * @param successRespCode
				 *//*
					 * @Test(groups = { "Sanity", "Regression",
					 * "MiniRegression", "ExhaustiveRegression" }, dataProvider=
					 * "CartServiceDP_removeItemFromWishListDataProvider_validateAPIResponse")
					 * public void
					 * CartServiceTests_removeItemFromWishList_verifySuccessResponse
					 * (String userName, String password, String delOperation,
					 * String successRespCode) { long startTime =
					 * Calendar.getInstance().getTimeInMillis(); String
					 * addItemQty ="1"; Boolean itemAdded = false;
					 * RequestGenerator fetchWishlistReqGen =
					 * checkoutTestsHelper.operationFetchWishList(userName,
					 * password); System.out.println(
					 * "\nPrinting CheckoutService operationFetchWishList API response :\n\n"
					 * +fetchWishlistReqGen.respvalidate.returnresponseasstring(
					 * )); log.info(
					 * "\nPrinting CheckoutService operationFetchWishList API response :\n\n"
					 * +fetchWishlistReqGen.respvalidate.returnresponseasstring(
					 * ));
					 * 
					 * AssertJUnit.assertTrue(
					 * "Error while invoking CheckoutService operationFetchWishList API"
					 * , fetchWishlistReqGen.respvalidate.NodeValue(StatusNodes.
					 * STATUS_TYPE.toString(),
					 * true).contains(SUCCESS_STATUS_TYPE));
					 * 
					 * if(fetchWishlistReqGen.respvalidate.NodeValue(
					 * WishListNodes.WISHLIST_TOTAL_COUNT.toString(),
					 * true).equals(EMPTY_VALUE)) { List<Integer> styleIdList =
					 * checkoutTestsHelper.performSeachServiceToGetStyleIds(
					 * RWL_SEARCH_PROD, RWL_SEARCH_QTY, RWL_SEARCH_RET_DOCS,
					 * RWL_SEARCH_IS_FACET);
					 * 
					 * if(!CollectionUtils.isEmpty(styleIdList)) { for(Integer
					 * styleId : styleIdList) { if(itemAdded) break;
					 * System.out.println("\nPrinting the StyleId : "+styleId);
					 * log.info("\nPrinting the StyleId : "+styleId);
					 * 
					 * RequestGenerator getStyleDataReqGen =
					 * checkoutTestsHelper.performGetStyleDataOperation(String.
					 * valueOf(styleId)); System.out.println(
					 * "\nPrinting StyleService getStyleData API response :\n\n"
					 * +getStyleDataReqGen.respvalidate.returnresponseasstring()
					 * ); log.info(
					 * "\nPrinting StyleService getStyleData API response :\n\n"
					 * +getStyleDataReqGen.respvalidate.returnresponseasstring()
					 * );
					 * 
					 * AssertJUnit.assertTrue("Error while getting style data",
					 * getStyleDataReqGen.respvalidate.NodeValue(StatusNodes.
					 * STATUS_TYPE.toString(),
					 * true).contains(SUCCESS_STATUS_TYPE));
					 * 
					 * String response =
					 * getStyleDataReqGen.respvalidate.returnresponseasstring();
					 * List<Integer> ids = JsonPath.read(response,
					 * "$.data..styleOptions[*].id");
					 * 
					 * for(int i = 0 ; i < ids.size(); i++) { String skuId =
					 * JsonPath.read(response,
					 * "$.data..styleOptions["+i+"].skuId").toString(); String
					 * inventoryCount = JsonPath.read(response,
					 * "$.data..styleOptions["+i+"].inventoryCount").toString();
					 * String available = JsonPath.read(response,
					 * "$.data..styleOptions["+i+"].available").toString();
					 * 
					 * if(!inventoryCount.equals(EMPTY_VALUE) &&
					 * available.equals(TRUE_VALUE)) { fetchWishlistReqGen =
					 * checkoutTestsHelper.operationFetchWishList(userName,
					 * password); System.out.println(
					 * "\nPrinting CheckoutService operationFetchWishList API response :\n\n"
					 * +fetchWishlistReqGen.respvalidate.returnresponseasstring(
					 * )); log.info(
					 * "\nPrinting CheckoutService operationFetchWishList API response :\n\n"
					 * +fetchWishlistReqGen.respvalidate.returnresponseasstring(
					 * ));
					 * 
					 * AssertJUnit.assertTrue(
					 * "Error while invoking CheckoutService operationFetchWishList API"
					 * , fetchWishlistReqGen.respvalidate.NodeValue(StatusNodes.
					 * STATUS_TYPE.toString(),
					 * true).contains(SUCCESS_STATUS_TYPE));
					 * 
					 * if(StringUtils.isBlank(checkoutTestsHelper.
					 * getItemIdFromSKUId(fetchWishlistReqGen, skuId,
					 * CheckoutDataProviderEnum.WISHLIST.name()))) {
					 * RequestGenerator addItemToWishlistReqGen =
					 * checkoutTestsHelper.addItemToWishList(userName, password,
					 * skuId, null,String.valueOf(addItemQty), ADD_OPERATION);
					 * System.out.println(
					 * "\nPrinting CheckoutService addItemToWishlist API response :\n\n"
					 * +addItemToWishlistReqGen.respvalidate.
					 * returnresponseasstring()); log.info(
					 * "\nPrinting CheckoutService addItemToCart API response :\n\n"
					 * +addItemToWishlistReqGen.respvalidate.
					 * returnresponseasstring());
					 * 
					 * AssertJUnit.assertTrue(
					 * "Error while invoking CheckoutService addItemToWishList API"
					 * , addItemToWishlistReqGen.respvalidate.NodeValue(
					 * StatusNodes.STATUS_TYPE.toString(),
					 * true).contains(SUCCESS_STATUS_TYPE)); itemAdded = true;
					 * System.out.println("\n"+skuId+
					 * " is available but not in cart so added to cart");
					 * log.info("\n"+skuId+
					 * " is available but not in cart so added to cart"); break;
					 * 
					 * } else { System.out.println("\n"+skuId +
					 * " is not added because this item already exists in cart"
					 * ); log.error("\n"+skuId +
					 * " is not added because this item already exists in cart"
					 * ); }
					 * 
					 * } else { System.out.println("\n"+skuId+
					 * " is not added because this item is currently out of stock"
					 * ); log.info("\n"+skuId+
					 * " is not added because this item is currently out of stock"
					 * ); } } }
					 * 
					 * } else { System.out.println(
					 * "\nError while invoking SearchService API"); log.error(
					 * "\nError while invoking SearchService API");
					 * AssertJUnit.fail("Error while invoking SearchService API"
					 * ); }
					 * 
					 * fetchWishlistReqGen =
					 * checkoutTestsHelper.operationFetchWishList(userName,
					 * password); System.out.println(
					 * "\nPrinting CheckoutService operationFetchWishList API response :\n\n"
					 * +fetchWishlistReqGen.respvalidate.returnresponseasstring(
					 * )); log.info(
					 * "\nPrinting CheckoutService operationFetchWishList API response :\n\n"
					 * +fetchWishlistReqGen.respvalidate.returnresponseasstring(
					 * ));
					 * 
					 * AssertJUnit.assertTrue(
					 * "Error while invoking CheckoutService operationFetchWishList API"
					 * , fetchWishlistReqGen.respvalidate.NodeValue(StatusNodes.
					 * STATUS_TYPE.toString(),
					 * true).contains(SUCCESS_STATUS_TYPE)); } String skuId =
					 * fetchWishlistReqGen.respvalidate.NodeValue(WishListNodes.
					 * WISHLIST_ITEM_ENTRIES_AVAILSIZES_SKUID.toString(),
					 * false); String itemId =
					 * checkoutTestsHelper.getItemIdFromSKUId(
					 * fetchWishlistReqGen, skuId,
					 * CheckoutDataProviderEnum.WISHLIST.name());
					 * 
					 * System.out.println("\nPrinting skuId : "+skuId+
					 * " & itemId : "+itemId+
					 * " is going to remove item from wishlist\n"); log.info(
					 * "\nPrinting skuId : "+skuId+" & itemId : "+itemId+
					 * " is going to remove item from wishlist\n");
					 * 
					 * RequestGenerator removeItemFromWishlist =
					 * checkoutTestsHelper.removeItemFromWishList(userName,
					 * password, itemId, delOperation); System.out.println(
					 * "\nPrinting CheckoutService removeItemFromWishList API response :\n\n"
					 * +removeItemFromWishlist.respvalidate.
					 * returnresponseasstring()); log.info(
					 * "\nPrinting CheckoutService removeItemFromWishList API response :\n\n"
					 * +removeItemFromWishlist.respvalidate.
					 * returnresponseasstring());
					 * 
					 * AssertJUnit.assertEquals(
					 * "CheckoutService removeItemFromWishList API is not working"
					 * , Integer.parseInt(successRespCode),
					 * removeItemFromWishlist.response.getStatus());
					 * 
					 * long endTime = Calendar.getInstance().getTimeInMillis();
					 * double timeTaken = (endTime - startTime)/1000.0;
					 * 
					 * System.out.println(
					 * "\nTime taken to execute - TestCase - CheckoutServiceTests_removeItemFromWishList_verifySuccessResponse : "
					 * +timeTaken+" seconds\n"); log.info(
					 * "\nTime taken to execute - TestCase - CheckoutServiceTests_removeItemFromWishList_verifySuccessResponse : "
					 * +timeTaken+" seconds\n"); }
					 * 
					 * /** This Test Case used to invoke CheckoutService
					 * removeItemFromWishList API and verify for success status
					 * response
					 * 
					 * @param userName
					 * 
					 * @param password
					 * 
					 * @param delOperation
					 * 
					 * @param successStatusCode
					 * 
					 * @param successStatusMsg
					 * 
					 * @param successStatusType
					 *//*
					 * @Test(groups = { "Sanity", "Regression",
					 * "MiniRegression", "ExhaustiveRegression" }, dataProvider=
					 * "CartServiceDP_removeItemFromWishListDataProvider_positiveCases")
					 * public void
					 * CartServiceTests_removeItemFromWishList_verifySuccessStatusResponse
					 * (String userName, String password, String delOperation,
					 * String successStatusCode, String successStatusMsg, String
					 * successStatusType) { long startTime =
					 * Calendar.getInstance().getTimeInMillis(); String
					 * addItemQty ="1"; Boolean itemAdded = false;
					 * RequestGenerator fetchWishlistReqGen =
					 * checkoutTestsHelper.operationFetchWishList(userName,
					 * password); System.out.println(
					 * "\nPrinting CheckoutService operationFetchWishList API response :\n\n"
					 * +fetchWishlistReqGen.respvalidate.returnresponseasstring(
					 * )); log.info(
					 * "\nPrinting CheckoutService operationFetchWishList API response :\n\n"
					 * +fetchWishlistReqGen.respvalidate.returnresponseasstring(
					 * ));
					 * 
					 * AssertJUnit.assertTrue(
					 * "Error while invoking CheckoutService operationFetchWishList API"
					 * , fetchWishlistReqGen.respvalidate.NodeValue(StatusNodes.
					 * STATUS_TYPE.toString(),
					 * true).contains(SUCCESS_STATUS_TYPE));
					 * 
					 * if(fetchWishlistReqGen.respvalidate.NodeValue(
					 * WishListNodes.WISHLIST_TOTAL_COUNT.toString(),
					 * true).equals(EMPTY_VALUE)) { List<Integer> styleIdList =
					 * checkoutTestsHelper.performSeachServiceToGetStyleIds(
					 * RWL_SEARCH_PROD, RWL_SEARCH_QTY, RWL_SEARCH_RET_DOCS,
					 * RWL_SEARCH_IS_FACET);
					 * 
					 * if(!CollectionUtils.isEmpty(styleIdList)) { for(Integer
					 * styleId : styleIdList) { if(itemAdded) break;
					 * System.out.println("\nPrinting the StyleId : "+styleId);
					 * log.info("\nPrinting the StyleId : "+styleId);
					 * 
					 * RequestGenerator getStyleDataReqGen =
					 * checkoutTestsHelper.performGetStyleDataOperation(String.
					 * valueOf(styleId)); System.out.println(
					 * "\nPrinting StyleService getStyleData API response :\n\n"
					 * +getStyleDataReqGen.respvalidate.returnresponseasstring()
					 * ); log.info(
					 * "\nPrinting StyleService getStyleData API response :\n\n"
					 * +getStyleDataReqGen.respvalidate.returnresponseasstring()
					 * );
					 * 
					 * AssertJUnit.assertTrue("Error while getting style data",
					 * getStyleDataReqGen.respvalidate.NodeValue(StatusNodes.
					 * STATUS_TYPE.toString(),
					 * true).contains(SUCCESS_STATUS_TYPE));
					 * 
					 * String response =
					 * getStyleDataReqGen.respvalidate.returnresponseasstring();
					 * List<Integer> ids = JsonPath.read(response,
					 * "$.data..styleOptions[*].id");
					 * 
					 * for(int i = 0 ; i < ids.size(); i++) { String skuId =
					 * JsonPath.read(response,
					 * "$.data..styleOptions["+i+"].skuId").toString(); String
					 * inventoryCount = JsonPath.read(response,
					 * "$.data..styleOptions["+i+"].inventoryCount").toString();
					 * String available = JsonPath.read(response,
					 * "$.data..styleOptions["+i+"].available").toString();
					 * 
					 * if(!inventoryCount.equals(EMPTY_VALUE) &&
					 * available.equals(TRUE_VALUE)) { fetchWishlistReqGen =
					 * checkoutTestsHelper.operationFetchWishList(userName,
					 * password); System.out.println(
					 * "\nPrinting CheckoutService operationFetchWishList API response :\n\n"
					 * +fetchWishlistReqGen.respvalidate.returnresponseasstring(
					 * )); log.info(
					 * "\nPrinting CheckoutService operationFetchWishList API response :\n\n"
					 * +fetchWishlistReqGen.respvalidate.returnresponseasstring(
					 * ));
					 * 
					 * AssertJUnit.assertTrue(
					 * "Error while invoking CheckoutService operationFetchWishList API"
					 * , fetchWishlistReqGen.respvalidate.NodeValue(StatusNodes.
					 * STATUS_TYPE.toString(),
					 * true).contains(SUCCESS_STATUS_TYPE));
					 * 
					 * if(StringUtils.isBlank(checkoutTestsHelper.
					 * getItemIdFromSKUId(fetchWishlistReqGen, skuId,
					 * CheckoutDataProviderEnum.WISHLIST.name()))) {
					 * RequestGenerator addItemToWishlistReqGen =
					 * checkoutTestsHelper.addItemToWishList(userName, password,
					 * skuId, null, String.valueOf(addItemQty), ADD_OPERATION);
					 * System.out.println(
					 * "\nPrinting CheckoutService addItemToCart API response :\n\n"
					 * +addItemToWishlistReqGen.respvalidate.
					 * returnresponseasstring()); log.info(
					 * "\nPrinting CheckoutService addItemToCart API response :\n\n"
					 * +addItemToWishlistReqGen.respvalidate.
					 * returnresponseasstring());
					 * 
					 * AssertJUnit.assertTrue(
					 * "Error while invoking CheckoutService addItemToWishList API"
					 * , addItemToWishlistReqGen.respvalidate.NodeValue(
					 * StatusNodes.STATUS_TYPE.toString(),
					 * true).contains(SUCCESS_STATUS_TYPE)); itemAdded = true;
					 * System.out.println("\n"+skuId+
					 * " is available but not in cart so added to cart");
					 * log.info("\n"+skuId+
					 * " is available but not in cart so added to cart"); break;
					 * 
					 * } else { System.out.println("\n"+skuId +
					 * " is not added because this item already exists in cart"
					 * ); log.error("\n"+skuId +
					 * " is not added because this item already exists in cart"
					 * ); }
					 * 
					 * } else { System.out.println("\n"+skuId+
					 * " is not added because this item is currently out of stock"
					 * ); log.info("\n"+skuId+
					 * " is not added because this item is currently out of stock"
					 * ); } } }
					 * 
					 * } else { System.out.println(
					 * "\nError while invoking SearchService API"); log.error(
					 * "\nError while invoking SearchService API");
					 * AssertJUnit.fail("Error while invoking SearchService API"
					 * ); }
					 * 
					 * fetchWishlistReqGen =
					 * checkoutTestsHelper.operationFetchWishList(userName,
					 * password); System.out.println(
					 * "\nPrinting CheckoutService operationFetchWishList API response :\n\n"
					 * +fetchWishlistReqGen.respvalidate.returnresponseasstring(
					 * )); log.info(
					 * "\nPrinting CheckoutService operationFetchWishList API response :\n\n"
					 * +fetchWishlistReqGen.respvalidate.returnresponseasstring(
					 * ));
					 * 
					 * AssertJUnit.assertTrue(
					 * "Error while invoking CheckoutService operationFetchWishList API"
					 * , fetchWishlistReqGen.respvalidate.NodeValue(StatusNodes.
					 * STATUS_TYPE.toString(),
					 * true).contains(SUCCESS_STATUS_TYPE)); } String skuId =
					 * fetchWishlistReqGen.respvalidate.NodeValue(WishListNodes.
					 * WISHLIST_ITEM_ENTRIES_AVAILSIZES_SKUID.toString(),
					 * false); String itemId =
					 * checkoutTestsHelper.getItemIdFromSKUId(
					 * fetchWishlistReqGen, skuId,
					 * CheckoutDataProviderEnum.WISHLIST.name());
					 * 
					 * System.out.println("\nPrinting skuId : "+skuId+
					 * " & itemId : "+itemId+
					 * " is going to remove item from wishlist\n"); log.info(
					 * "\nPrinting skuId : "+skuId+" & itemId : "+itemId+
					 * " is going to remove item from wishlist\n");
					 * 
					 * RequestGenerator removeItemFromWishlist =
					 * checkoutTestsHelper.removeItemFromWishList(userName,
					 * password, itemId, delOperation); System.out.println(
					 * "\nPrinting CheckoutService removeItemFromWishList API response :\n\n"
					 * +removeItemFromWishlist.respvalidate.
					 * returnresponseasstring()); log.info(
					 * "\nPrinting CheckoutService removeItemFromWishList API response :\n\n"
					 * +removeItemFromWishlist.respvalidate.
					 * returnresponseasstring());
					 * 
					 * AssertJUnit.assertTrue(
					 * "CheckoutService removeItemFromWishList API response status code is not a success status code"
					 * ,
					 * removeItemFromWishlist.respvalidate.NodeValue(StatusNodes
					 * .STATUS_CODE.toString(),
					 * true).contains(successStatusCode));
					 * 
					 * AssertJUnit.assertTrue(
					 * "CheckoutService removeItemFromWishList API response status message is not a success status message"
					 * ,
					 * removeItemFromWishlist.respvalidate.NodeValue(StatusNodes
					 * .STATUS_MESSAGE.toString(),
					 * true).contains(successStatusMsg));
					 * 
					 * AssertJUnit.assertTrue(
					 * "CheckoutService removeItemFromWishList API response status type is not a success status type"
					 * ,
					 * removeItemFromWishlist.respvalidate.NodeValue(StatusNodes
					 * .STATUS_TYPE.toString(),
					 * true).contains(successStatusType));
					 * 
					 * long endTime = Calendar.getInstance().getTimeInMillis();
					 * double timeTaken = (endTime - startTime)/1000.0;
					 * 
					 * System.out.println(
					 * "\nTime taken to execute - TestCase - CheckoutServiceTests_removeItemFromWishList_verifySuccessStatusResponse : "
					 * +timeTaken+" seconds\n"); log.info(
					 * "\nTime taken to execute - TestCase - CheckoutServiceTests_removeItemFromWishList_verifySuccessStatusResponse : "
					 * +timeTaken+" seconds\n"); }
					 */

	/**
	 * This Test Case used to invoke CheckoutService moveItemToCart API and
	 * verify for success response
	 * 
	 * @param user
	 * @param pass
	 * @param skuId
	 * @param EMPTY_VALUE.name()))
	 * @param respCode
	 */
	@Test(groups = { "Sanity", "FoxSanity", "Regression", "MiniRegression",
			"ExhaustiveRegression" }, dataProvider = "CartServiceDP_moveItemToCartDataProvider_validateAPIResponse", description = "1. hit IDP service and login the user. "
					+ "\n 2. hit fetch wishlist API. \n 3. if user's wishlist is empty then first add items to Wishlist(\n a.hit Search Service API and extract some skuIDs"
					+ "\n b. C. hit addItemToWishlist API giving skuId which doesnt exists in wishlist)"
					+ "\n 4. hit moveItemToCart API" + "\n 5.Check the service response must be 200")
	public void CartServiceTests_moveItemToCart_verifySuccessResponse(String userName, String password,
			String successRespCode) {
		long startTime = Calendar.getInstance().getTimeInMillis();
		String addItemQty = "1";
		Boolean itemAdded = false;
		RequestGenerator fetchWishlistReqGen = checkoutTestsHelper.operationFetchWishList(userName, password);
		System.out.println("\nPrinting CheckoutService operationFetchWishList API response :\n\n"
				+ fetchWishlistReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService operationFetchWishList API response :\n\n"
				+ fetchWishlistReqGen.respvalidate.returnresponseasstring());

		AssertJUnit.assertTrue("Error while invoking CheckoutService operationFetchWishList API",
				fetchWishlistReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
						.contains(SUCCESS_STATUS_TYPE));

		if (fetchWishlistReqGen.respvalidate.NodeValue(WishListNodes.WISHLIST_TOTAL_COUNT.toString(), true)
				.equals(EMPTY_VALUE)) {
			List<Integer> styleIdList = checkoutTestsHelper.performSeachServiceToGetStyleIds(MC_SEARCH_PROD,
					MC_SEARCH_QTY, MC_SEARCH_RET_DOCS, MC_SEARCH_IS_FACET);

			if (!CollectionUtils.isEmpty(styleIdList)) {
				for (Integer styleId : styleIdList) {
					if (itemAdded)
						break;
					System.out.println("\nPrinting the StyleId : " + styleId);
					log.info("\nPrinting the StyleId : " + styleId);

					RequestGenerator getStyleDataReqGen = checkoutTestsHelper
							.performGetStyleDataOperation(String.valueOf(styleId));
					System.out.println("\nPrinting StyleService getStyleData API response :\n\n"
							+ getStyleDataReqGen.respvalidate.returnresponseasstring());
					log.info("\nPrinting StyleService getStyleData API response :\n\n"
							+ getStyleDataReqGen.respvalidate.returnresponseasstring());

					AssertJUnit.assertTrue("Error while getting style data", getStyleDataReqGen.respvalidate
							.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));

					String response = getStyleDataReqGen.respvalidate.returnresponseasstring();
					List<Integer> ids = JsonPath.read(response, "$.data..styleOptions[*].id");

					for (int i = 0; i < ids.size(); i++) {
						String skuId = JsonPath.read(response, "$.data..styleOptions[" + i + "].skuId").toString();
						String inventoryCount = JsonPath
								.read(response, "$.data..styleOptions[" + i + "].inventoryCount").toString();
						String available = JsonPath.read(response, "$.data..styleOptions[" + i + "].available")
								.toString();

						if (!inventoryCount.equals(EMPTY_VALUE) && available.equals(TRUE_VALUE)) {
							RequestGenerator addItemToWishlistReqGen = checkoutTestsHelper.addItemToWishList(userName,
									password, skuId, null, String.valueOf(addItemQty), ADD_OPERATION);
							System.out.println("\nPrinting CheckoutService addItemToWishList API response :\n\n"
									+ addItemToWishlistReqGen.respvalidate.returnresponseasstring());
							log.info("\nPrinting CheckoutService addItemToWishList API response :\n\n"
									+ addItemToWishlistReqGen.respvalidate.returnresponseasstring());

							AssertJUnit.assertTrue("Error while invoking CheckoutService addItemToWishList API",
									addItemToWishlistReqGen.respvalidate
											.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
											.contains(SUCCESS_STATUS_TYPE));
							itemAdded = true;
							System.out.println("\n" + skuId + " is available but not in wishlist so added to wishlist");
							log.info("\n" + skuId + " is available but not in wishlist so added to wishlist");
							break;

						} else {
							System.out.println(
									"\n" + skuId + " is not added because this item is currently out of stock");
							log.info("\n" + skuId + " is not added because this item is currently out of stock");
						}
					}
				}

			} else {
				System.out.println("\nError while invoking SearchService API");
				log.error("\nError while invoking SearchService API");
				AssertJUnit.fail("Error while invoking SearchService API");
			}

			fetchWishlistReqGen = checkoutTestsHelper.operationFetchWishList(userName, password);
			System.out.println("\nPrinting CheckoutService operationFetchWishList API response :\n\n"
					+ fetchWishlistReqGen.respvalidate.returnresponseasstring());
			log.info("\nPrinting CheckoutService operationFetchWishList API response :\n\n"
					+ fetchWishlistReqGen.respvalidate.returnresponseasstring());

			AssertJUnit.assertTrue("Error while invoking CheckoutService operationFetchWishList API",
					fetchWishlistReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
							.contains(SUCCESS_STATUS_TYPE));
		}

		String skuId = fetchWishlistReqGen.respvalidate
				.NodeValue(WishListNodes.WISHLIST_ITEM_ENTRIES_SELSIZE_SKUID.toString(), false);
		String itemId = checkoutTestsHelper.getItemIdFromSKUId(fetchWishlistReqGen, skuId,
				CheckoutDataProviderEnum.WISHLIST.name());

		RequestGenerator fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
		System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"
				+ fetchCartReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"
				+ fetchCartReqGen.respvalidate.returnresponseasstring());

		AssertJUnit.assertTrue("Error while invoking CheckoutService operationFetchCart API",
				fetchCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
						.contains(SUCCESS_STATUS_TYPE));

		if (StringUtils.isBlank(
				checkoutTestsHelper.getItemIdFromSKUId(fetchCartReqGen, skuId, CheckoutDataProviderEnum.CART.name()))) {
			System.out.println(
					"\nPrinting skuId : " + skuId + " & itemId : " + itemId + " is moving to cart from wishlist\n");
			log.info("\nPrinting skuId : " + skuId + " & itemId : " + itemId
					+ " is going is moving to cart from wishlist\n");

			RequestGenerator moveItemToCartReqGen = checkoutTestsHelper.moveItemToCart(userName, password, itemId);
			System.out.println("\nPrinting CheckoutService moveItemToCart API response :\n\n"
					+ moveItemToCartReqGen.respvalidate.returnresponseasstring());
			log.info("\nPrinting CheckoutService moveItemToCart API response :\n\n"
					+ moveItemToCartReqGen.respvalidate.returnresponseasstring());

			AssertJUnit.assertEquals("CheckoutService moveItemToCart API is not working",
					Integer.parseInt(successRespCode), moveItemToCartReqGen.response.getStatus());
		} else {
			System.out
					.println("\nPrinting skuId : " + skuId + " & itemId : " + itemId + " is already present in cart\n");
			log.info("\nPrinting skuId : " + skuId + " & itemId : " + itemId + " is already present in cart\n");

		}

		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;

		System.out.println(
				"\nTime taken to execute - TestCase - CheckoutServiceTests_moveItemToCart_verifySuccessResponse : "
						+ timeTaken + " seconds\n");
		log.info("\nTime taken to execute - TestCase - CheckoutServiceTests_moveItemToCart_verifySuccessResponse : "
				+ timeTaken + " seconds\n");

	}

	/**
	 * This Test Case used to invoke CheckoutService moveItemToCart API and
	 * verify for success status response
	 * 
	 * @param userName
	 * @param password
	 * @param successStatusCode
	 * @param successStatusMsg
	 * @param successStatusType
	 */
	@Test(groups = { "Sanity", "Regression", "MiniRegression",
			"ExhaustiveRegression" }, dataProvider = "CartServiceDP_moveItemToCartDataProvider_positiveCases", description = "1. hit IDP service and login the user. "
					+ "\n 2. hit fetch wishlist API. \n 3. if user's wishlist is empty then first add items to Wishlist(\n a.hit Search Service API and extract some skuIDs"
					+ "\n b. C. hit addItemToWishlist API giving skuId which doesnt exists in wishlist)"
					+ "\n 4. hit moveItemToCart API"
					+ "\n 5. Verify statusCode, statusMessage & statusType must be of success")
	public void CartServiceTests_moveItemToCart_verifySuccessStatusResponse(String userName, String password,
			String successStatusCode, String successStatusMsg, String successStatusType) {
		long startTime = Calendar.getInstance().getTimeInMillis();
		String addItemQty = "1";
		Boolean itemAdded = false;
		RequestGenerator fetchWishlistReqGen = checkoutTestsHelper.operationFetchWishList(userName, password);
		System.out.println("\nPrinting CheckoutService operationFetchWishList API response :\n\n"
				+ fetchWishlistReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService operationFetchWishList API response :\n\n"
				+ fetchWishlistReqGen.respvalidate.returnresponseasstring());

		AssertJUnit.assertTrue("Error while invoking CheckoutService operationFetchWishList API",
				fetchWishlistReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
						.contains(SUCCESS_STATUS_TYPE));

		if (fetchWishlistReqGen.respvalidate.NodeValue(WishListNodes.WISHLIST_TOTAL_COUNT.toString(), true)
				.equals(EMPTY_VALUE)) {
			List<Integer> styleIdList = checkoutTestsHelper.performSeachServiceToGetStyleIds(MC_SEARCH_PROD,
					MC_SEARCH_QTY, MC_SEARCH_RET_DOCS, MC_SEARCH_IS_FACET);

			if (!CollectionUtils.isEmpty(styleIdList)) {
				for (Integer styleId : styleIdList) {
					if (itemAdded)
						break;
					System.out.println("\nPrinting the StyleId : " + styleId);
					log.info("\nPrinting the StyleId : " + styleId);

					RequestGenerator getStyleDataReqGen = checkoutTestsHelper
							.performGetStyleDataOperation(String.valueOf(styleId));
					System.out.println("\nPrinting StyleService getStyleData API response :\n\n"
							+ getStyleDataReqGen.respvalidate.returnresponseasstring());
					log.info("\nPrinting StyleService getStyleData API response :\n\n"
							+ getStyleDataReqGen.respvalidate.returnresponseasstring());

					AssertJUnit.assertTrue("Error while getting style data", getStyleDataReqGen.respvalidate
							.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));

					String response = getStyleDataReqGen.respvalidate.returnresponseasstring();
					List<Integer> ids = JsonPath.read(response, "$.data..styleOptions[*].id");

					for (int i = 0; i < ids.size(); i++) {
						String skuId = JsonPath.read(response, "$.data..styleOptions[" + i + "].skuId").toString();
						String inventoryCount = JsonPath
								.read(response, "$.data..styleOptions[" + i + "].inventoryCount").toString();
						String available = JsonPath.read(response, "$.data..styleOptions[" + i + "].available")
								.toString();

						if (!inventoryCount.equals(EMPTY_VALUE) && available.equals(TRUE_VALUE)) {
							RequestGenerator addItemToWishlistReqGen = checkoutTestsHelper.addItemToWishList(userName,
									password, skuId, null, String.valueOf(addItemQty), ADD_OPERATION);
							System.out.println("\nPrinting CheckoutService addItemToWishList API response :\n\n"
									+ addItemToWishlistReqGen.respvalidate.returnresponseasstring());
							log.info("\nPrinting CheckoutService addItemToWishList API response :\n\n"
									+ addItemToWishlistReqGen.respvalidate.returnresponseasstring());

							AssertJUnit.assertTrue("Error while invoking CheckoutService addItemToWishList API",
									addItemToWishlistReqGen.respvalidate
											.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
											.contains(SUCCESS_STATUS_TYPE));
							itemAdded = true;
							System.out.println("\n" + skuId + " is available but not in wishlist so added to wishlist");
							log.info("\n" + skuId + " is available but not in wishlist so added to wishlist");
							break;

						} else {
							System.out.println(
									"\n" + skuId + " is not added because this item is currently out of stock");
							log.info("\n" + skuId + " is not added because this item is currently out of stock");
						}
					}

				}

			} else {
				System.out.println("\nError while invoking SearchService API");
				log.error("\nError while invoking SearchService API");
				AssertJUnit.fail("Error while invoking SearchService API");
			}

			fetchWishlistReqGen = checkoutTestsHelper.operationFetchWishList(userName, password);
			System.out.println("\nPrinting CheckoutService operationFetchWishList API response :\n\n"
					+ fetchWishlistReqGen.respvalidate.returnresponseasstring());
			log.info("\nPrinting CheckoutService operationFetchWishList API response :\n\n"
					+ fetchWishlistReqGen.respvalidate.returnresponseasstring());

			AssertJUnit.assertTrue("Error while invoking CheckoutService operationFetchWishList API",
					fetchWishlistReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
							.contains(SUCCESS_STATUS_TYPE));
		}

		String skuId = fetchWishlistReqGen.respvalidate
				.NodeValue(WishListNodes.WISHLIST_ITEM_ENTRIES_SELSIZE_SKUID.toString(), false);
		String itemId = checkoutTestsHelper.getItemIdFromSKUId(fetchWishlistReqGen, skuId,
				CheckoutDataProviderEnum.WISHLIST.name());

		RequestGenerator fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
		System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"
				+ fetchCartReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"
				+ fetchCartReqGen.respvalidate.returnresponseasstring());

		AssertJUnit.assertTrue("Error while invoking CheckoutService operationFetchCart API",
				fetchCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
						.contains(SUCCESS_STATUS_TYPE));

		if (StringUtils.isBlank(
				checkoutTestsHelper.getItemIdFromSKUId(fetchCartReqGen, skuId, CheckoutDataProviderEnum.CART.name()))) {
			System.out.println(
					"\nPrinting skuId : " + skuId + " & itemId : " + itemId + " is moving to cart from wishlist\n");
			log.info("\nPrinting skuId : " + skuId + " & itemId : " + itemId
					+ " is going is moving to cart from wishlist\n");

			RequestGenerator moveItemToCartReqGen = checkoutTestsHelper.moveItemToCart(userName, password, itemId);
			System.out.println("\nPrinting CheckoutService moveItemToCart API response :\n\n"
					+ moveItemToCartReqGen.respvalidate.returnresponseasstring());
			log.info("\nPrinting CheckoutService moveItemToCart API response :\n\n"
					+ moveItemToCartReqGen.respvalidate.returnresponseasstring());

			AssertJUnit.assertTrue(
					"CheckoutService moveItemToCart API response status code is not a success status code",
					moveItemToCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_CODE.toString(), true)
							.contains(successStatusCode));

			AssertJUnit.assertTrue(
					"CheckoutService moveItemToCart API response status message is not a success status message",
					moveItemToCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_MESSAGE.toString(), true)
							.contains(successStatusMsg));

			AssertJUnit.assertTrue(
					"CheckoutService moveItemToCart API response status type is not a success status type",
					moveItemToCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
							.contains(successStatusType));

		} else {
			System.out
					.println("\nPrinting skuId : " + skuId + " & itemId : " + itemId + " is already present in cart\n");
			log.info("\nPrinting skuId : " + skuId + " & itemId : " + itemId + " is already present in cart\n");
		}

		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;

		System.out.println(
				"\nTime taken to execute - TestCase - CheckoutServiceTests_moveItemToCart_verifySuccessStatusResponse : "
						+ timeTaken + " seconds\n");
		log.info(
				"\nTime taken to execute - TestCase - CheckoutServiceTests_moveItemToCart_verifySuccessStatusResponse : "
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
	@Test(groups = { "Sanity", "Regression", "MiniRegression",
			"ExhaustiveRegression" }, dataProvider = "CartServiceDP_applyMyntCreditDataProvider_validateAPIResponse", description = "1. hit IDP service and login the user. "
					+ "\n 2. hit fetchCart API. \n 3. if user's cart is empty then first add items to cart(\n a.hit Search Service API and extract some skuIDs"
					+ "\n b. Check if searched styleIds exists in user's cart or not. \n c. hit addItemToCart API giving skuId which doesnt exists in cart)"
					+ "\n 4. if cashback is already applied to cart, remove it"
					+ "\n 5. hit getCashback API to fetch the active cashback amount of user"
					+ "\n 6. hit applyCashback API giving the fetched cashback amount to be applied on cart"
					+ "\n 7. Verify statusCode must be 200 in APIs response")
	public void CartServiceTests_applyMyntCredit_verifySuccessResponse(String userName, String password,
			String successRespCode) {
		long startTime = Calendar.getInstance().getTimeInMillis();
		String addItemQty = "1";
		Boolean itemAdded = false;

		RequestGenerator fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
		System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"
				+ fetchCartReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"
				+ fetchCartReqGen.respvalidate.returnresponseasstring());

		AssertJUnit.assertTrue("Error while invoking CheckoutService operationFetchCart API",
				fetchCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
						.contains(SUCCESS_STATUS_TYPE));

		if (fetchCartReqGen.respvalidate.NodeValue(CartNodes.CART_TOTAL_COUNT.toString(), true).equals(EMPTY_VALUE)) {
			List<Integer> styleIdList = checkoutTestsHelper.performSeachServiceToGetStyleIds(UC_SEARCH_PROD,
					UC_SEARCH_QTY, UC_SEARCH_RET_DOCS, UC_SEARCH_IS_FACET);

			if (!CollectionUtils.isEmpty(styleIdList)) {
				for (Integer styleId : styleIdList) {
					if (itemAdded)
						break;
					System.out.println("\nPrinting the StyleId : " + styleId);
					log.info("\nPrinting the StyleId : " + styleId);
					RequestGenerator getStyleDataReqGen = checkoutTestsHelper
							.performGetStyleDataOperation(String.valueOf(styleId));
					System.out.println("\nPrinting StyleService getStyleData API response :\n\n"
							+ getStyleDataReqGen.respvalidate.returnresponseasstring());
					log.info("\nPrinting StyleService getStyleData API response :\n\n"
							+ getStyleDataReqGen.respvalidate.returnresponseasstring());

					AssertJUnit.assertTrue("Error while getting style data", getStyleDataReqGen.respvalidate
							.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));

					String response = getStyleDataReqGen.respvalidate.returnresponseasstring();
					List<Integer> ids = JsonPath.read(response, "$.data..styleOptions[*].id");

					for (int i = 0; i < ids.size(); i++) {

						String skuId = JsonPath.read(response, "$.data..styleOptions[" + i + "].skuId").toString();
						String inventoryCount = JsonPath
								.read(response, "$.data..styleOptions[" + i + "].inventoryCount").toString();
						String available = JsonPath.read(response, "$.data..styleOptions[" + i + "].available")
								.toString();

						if (!inventoryCount.equals(EMPTY_VALUE) && available.equals(TRUE_VALUE)) {
							RequestGenerator addItemToCartReqGen = checkoutTestsHelper.addItemToCart(userName, password,
									skuId, String.valueOf(addItemQty), ADD_OPERATION);
							System.out.println("\nPrinting CheckoutService addItemToCart API response :\n\n"
									+ addItemToCartReqGen.respvalidate.returnresponseasstring());
							log.info("\nPrinting CheckoutService addItemToCart API response :\n\n"
									+ addItemToCartReqGen.respvalidate.returnresponseasstring());

							AssertJUnit.assertTrue("Error while invoking CheckoutService addItemToCart API",
									addItemToCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
											.contains(SUCCESS_STATUS_TYPE));
							itemAdded = true;
							System.out.println(
									"\n" + skuId + " is available and it doesn't exists in cart so added to cart");
							log.info("\n" + skuId + " is available and it doesn't exists in cart so added to cart");
							break;

						} else {
							System.out.println(
									"\n" + skuId + " is not added because this item is currently out of stock");
							log.info("\n" + skuId + " is not added because this item is currently out of stock");
						}
					}
				}

			} else {
				System.out.println("\nError while invoking SearchService API");
				log.error("\nError while invoking SearchService API");
				AssertJUnit.fail("Error while invoking SearchService API");
			}
		}

		if (fetchCartReqGen.respvalidate.NodeValue(CartNodes.CART_USE_MYNT_CASH.toString(), false).equals(TRUE_VALUE)) {
			RequestGenerator removeMyntCredit = checkoutTestsHelper.removeMyntCredit(userName, password);
			System.out.println("\nPrinting CheckoutService removeMyntCredit API response :\n\n"
					+ removeMyntCredit.respvalidate.returnresponseasstring());
			log.info("\nPrinting CheckoutService removeMyntCredit API response :\n\n"
					+ removeMyntCredit.respvalidate.returnresponseasstring());

			AssertJUnit.assertTrue("Error while invoking CheckoutService removeMyntCredit API",
					removeMyntCredit.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
							.contains(SUCCESS_STATUS_TYPE));
		}

		RequestGenerator getMyntCreditReqGen = checkoutTestsHelper.getMyntCredit(userName, password);
		System.out.println("\nPrinting CheckoutService getMyntCredit API response :\n\n"
				+ getMyntCreditReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting getMyntCredit API response :\n\n"
				+ getMyntCreditReqGen.respvalidate.returnresponseasstring());
		System.out.println(
				"balance in user's cashback : " + getMyntCreditReqGen.respvalidate.NodeValue("data.balance", true));

		String useAmount = getMyntCreditReqGen.respvalidate.NodeValue("data.balance", true);

		RequestGenerator applyMyntCreditReqGen = checkoutTestsHelper.applyMyntCredit(userName, password, useAmount);
		System.out.println("\nPrinting CheckoutService applyMyntCredit API response :\n\n"
				+ applyMyntCreditReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService applyMyntCredit API response :\n\n"
				+ applyMyntCreditReqGen.respvalidate.returnresponseasstring());

		AssertJUnit.assertEquals("CheckoutService applyMyntCredit API is not working",
				Integer.parseInt(successRespCode), applyMyntCreditReqGen.response.getStatus());

		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;

		System.out.println(
				"\nTime taken to execute - TestCase - CheckoutServiceTests_applyMyntCredit_verifySuccessResponse : "
						+ timeTaken + " seconds\n");
		log.info("\nTime taken to execute - TestCase - CheckoutServiceTests_applyMyntCredit_verifySuccessResponse : "
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
	@Test(groups = { "Sanity", "Regression", "MiniRegression",
			"ExhaustiveRegression" }, dataProvider = "CartServiceDP_applyMyntCreditDataProvider_positiveCases", description = "1. hit IDP service and login the user. "
					+ "\n 2. hit fetchCart API. \n 3. if user's cart is empty then first add items to cart(\n a.hit Search Service API and extract some skuIDs"
					+ "\n b. Check if searched styleIds exists in user's cart or not. \n c. hit addItemToCart API giving skuId which doesnt exists in cart)"
					+ "\n 4. if cashback is already applied to cart, remove it"
					+ "\n 5. hit getCashback API to fetch the active cashback amount of user"
					+ "\n 6. hit applyCashback API giving the fetched cashback amount to be applied on cart"
					+ "\n 7. Verify statusCode, statusMessage & statusType of API response must be of Success")
	public void CartServiceTests_applyMyntCredit_verifySuccessStatusResponse(String userName, String password,
			String successStatusCode, String successStatusMsg, String successStatusType) {
		long startTime = Calendar.getInstance().getTimeInMillis();
		String addItemQty = "1";
		Boolean itemAdded = false;
		RequestGenerator fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
		System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"
				+ fetchCartReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"
				+ fetchCartReqGen.respvalidate.returnresponseasstring());

		AssertJUnit.assertTrue("Error while invoking CheckoutService operationFetchCart API",
				fetchCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
						.contains(SUCCESS_STATUS_TYPE));
		if (fetchCartReqGen.respvalidate.NodeValue(CartNodes.CART_TOTAL_COUNT.toString(), true).equals(EMPTY_VALUE)) {
			List<Integer> styleIdList = checkoutTestsHelper.performSeachServiceToGetStyleIds(UC_SEARCH_PROD,
					UC_SEARCH_QTY, UC_SEARCH_RET_DOCS, UC_SEARCH_IS_FACET);

			if (!CollectionUtils.isEmpty(styleIdList)) {
				for (Integer styleId : styleIdList) {
					if (itemAdded)
						break;
					System.out.println("\nPrinting the StyleId : " + styleId);
					log.info("\nPrinting the StyleId : " + styleId);
					RequestGenerator getStyleDataReqGen = checkoutTestsHelper
							.performGetStyleDataOperation(String.valueOf(styleId));
					System.out.println("\nPrinting StyleService getStyleData API response :\n\n"
							+ getStyleDataReqGen.respvalidate.returnresponseasstring());
					log.info("\nPrinting StyleService getStyleData API response :\n\n"
							+ getStyleDataReqGen.respvalidate.returnresponseasstring());

					AssertJUnit.assertTrue("Error while getting style data", getStyleDataReqGen.respvalidate
							.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));

					String response = getStyleDataReqGen.respvalidate.returnresponseasstring();
					List<Integer> ids = JsonPath.read(response, "$.data..styleOptions[*].id");

					for (int i = 0; i < ids.size(); i++) {

						String skuId = JsonPath.read(response, "$.data..styleOptions[" + i + "].skuId").toString();
						String inventoryCount = JsonPath
								.read(response, "$.data..styleOptions[" + i + "].inventoryCount").toString();
						String available = JsonPath.read(response, "$.data..styleOptions[" + i + "].available")
								.toString();

						if (!inventoryCount.equals(EMPTY_VALUE) && available.equals(TRUE_VALUE)) {
							RequestGenerator addItemToCartReqGen = checkoutTestsHelper.addItemToCart(userName, password,
									skuId, String.valueOf(addItemQty), ADD_OPERATION);
							System.out.println("\nPrinting CheckoutService addItemToCart API response :\n\n"
									+ addItemToCartReqGen.respvalidate.returnresponseasstring());
							log.info("\nPrinting CheckoutService addItemToCart API response :\n\n"
									+ addItemToCartReqGen.respvalidate.returnresponseasstring());

							AssertJUnit.assertTrue("Error while invoking CheckoutService addItemToCart API",
									addItemToCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
											.contains(SUCCESS_STATUS_TYPE));
							itemAdded = true;
							System.out.println(
									"\n" + skuId + " is available and it doesn't exists in cart so added to cart");
							log.info("\n" + skuId + " is available and it doesn't exists in cart so added to cart");
							break;

						} else {
							System.out.println(
									"\n" + skuId + " is not added because this item is currently out of stock");
							log.info("\n" + skuId + " is not added because this item is currently out of stock");
						}
					}
				}

			} else {
				System.out.println("\nError while invoking SearchService API");
				log.error("\nError while invoking SearchService API");
				AssertJUnit.fail("Error while invoking SearchService API");
			}
		}

		if (fetchCartReqGen.respvalidate.NodeValue(CartNodes.CART_USE_MYNT_CASH.toString(), false).equals(TRUE_VALUE)) {
			RequestGenerator removeMyntCredit = checkoutTestsHelper.removeMyntCredit(userName, password);
			System.out.println("\nPrinting CheckoutService removeMyntCredit API response :\n\n"
					+ removeMyntCredit.respvalidate.returnresponseasstring());
			log.info("\nPrinting CheckoutService removeMyntCredit API response :\n\n"
					+ removeMyntCredit.respvalidate.returnresponseasstring());

			AssertJUnit.assertTrue("Error while invoking CheckoutService removeMyntCredit API",
					removeMyntCredit.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
							.contains(SUCCESS_STATUS_TYPE));
		}

		RequestGenerator getMyntCreditReqGen = checkoutTestsHelper.getMyntCredit(userName, password);
		System.out.println("\nPrinting CheckoutService getMyntCredit API response :\n\n"
				+ getMyntCreditReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting getMyntCredit API response :\n\n"
				+ getMyntCreditReqGen.respvalidate.returnresponseasstring());
		System.out.println(
				"balance in user's cashback : " + getMyntCreditReqGen.respvalidate.NodeValue("data.balance", true));

		String useAmount = getMyntCreditReqGen.respvalidate.NodeValue("data.balance", true);

		RequestGenerator applyMyntCreditReqGen = checkoutTestsHelper.applyMyntCredit(userName, password, useAmount);
		System.out.println("\nPrinting CheckoutService applyMyntCredit API response :\n\n"
				+ applyMyntCreditReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService applyMyntCredit API response :\n\n"
				+ applyMyntCreditReqGen.respvalidate.returnresponseasstring());

		AssertJUnit.assertTrue("CheckoutService applyMyntCredit API response status code is not a success status code",
				applyMyntCreditReqGen.respvalidate.NodeValue(StatusNodes.STATUS_CODE.toString(), true)
						.contains(successStatusCode));

		AssertJUnit.assertTrue(
				"CheckoutService applyMyntCredit API response status message is not a success status message",
				applyMyntCreditReqGen.respvalidate.NodeValue(StatusNodes.STATUS_MESSAGE.toString(), true)
						.contains(successStatusMsg));

		AssertJUnit.assertTrue("CheckoutService applyMyntCredit API response status type is not a success status type",
				applyMyntCreditReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
						.contains(successStatusType));

		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;

		System.out.println(
				"\nTime taken to execute - TestCase - CheckoutServiceTests_applyMyntCredit_verifySuccessStatusResponse : "
						+ timeTaken + " seconds\n");
		log.info(
				"\nTime taken to execute - TestCase - CheckoutServiceTests_applyMyntCredit_verifySuccessStatusResponse : "
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
	@Test(groups = { "Sanity", "Regression", "MiniRegression",
			"ExhaustiveRegression" }, dataProvider = "CartServiceDP_applyMyntCreditDataProvider_verifyMyntCreditNodes", description = "1. hit IDP service and login the user. "
					+ "\n 2. hit fetchCart API. \n 3. if user's cart is empty then first add items to cart(\n a.hit Search Service API and extract some skuIDs"
					+ "\n b. Check if searched styleIds exists in user's cart or not. \n c. hit addItemToCart API giving skuId which doesnt exists in cart)"
					+ "\n 4. if cashback is already applied to cart, remove it"
					+ "\n 5. hit getCashback API to fetch the active cashback amount of user"
					+ "\n 6. hit applyCashback API giving the fetched cashback amount to be applied on cart"
					+ "\n 7. Validate CashBca Nodes in APIs response")
	public void CartServiceTests_applyMyntCredit_verifyMyntCreditNodes(String userName, String password) {
		long startTime = Calendar.getInstance().getTimeInMillis();
		String addItemQty = "1";
		Boolean itemAdded = false;
		RequestGenerator fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
		System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"
				+ fetchCartReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"
				+ fetchCartReqGen.respvalidate.returnresponseasstring());

		AssertJUnit.assertTrue("Error while invoking CheckoutService operationFetchCart API",
				fetchCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
						.contains(SUCCESS_STATUS_TYPE));

		if (fetchCartReqGen.respvalidate.NodeValue(CartNodes.CART_TOTAL_COUNT.toString(), true).equals(EMPTY_VALUE)) {
			List<Integer> styleIdList = checkoutTestsHelper.performSeachServiceToGetStyleIds(UC_SEARCH_PROD,
					UC_SEARCH_QTY, UC_SEARCH_RET_DOCS, UC_SEARCH_IS_FACET);

			if (!CollectionUtils.isEmpty(styleIdList)) {
				for (Integer styleId : styleIdList) {
					if (itemAdded)
						break;
					System.out.println("\nPrinting the StyleId : " + styleId);
					log.info("\nPrinting the StyleId : " + styleId);
					RequestGenerator getStyleDataReqGen = checkoutTestsHelper
							.performGetStyleDataOperation(String.valueOf(styleId));
					System.out.println("\nPrinting StyleService getStyleData API response :\n\n"
							+ getStyleDataReqGen.respvalidate.returnresponseasstring());
					log.info("\nPrinting StyleService getStyleData API response :\n\n"
							+ getStyleDataReqGen.respvalidate.returnresponseasstring());

					AssertJUnit.assertTrue("Error while getting style data", getStyleDataReqGen.respvalidate
							.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));

					String response = getStyleDataReqGen.respvalidate.returnresponseasstring();
					List<Integer> ids = JsonPath.read(response, "$.data..styleOptions[*].id");

					for (int i = 0; i < ids.size(); i++) {

						String skuId = JsonPath.read(response, "$.data..styleOptions[" + i + "].skuId").toString();
						String inventoryCount = JsonPath
								.read(response, "$.data..styleOptions[" + i + "].inventoryCount").toString();
						String available = JsonPath.read(response, "$.data..styleOptions[" + i + "].available")
								.toString();

						if (!inventoryCount.equals(EMPTY_VALUE) && available.equals(TRUE_VALUE)) {
							RequestGenerator addItemToCartReqGen = checkoutTestsHelper.addItemToCart(userName, password,
									skuId, String.valueOf(addItemQty), ADD_OPERATION);
							System.out.println("\nPrinting CheckoutService addItemToCart API response :\n\n"
									+ addItemToCartReqGen.respvalidate.returnresponseasstring());
							log.info("\nPrinting CheckoutService addItemToCart API response :\n\n"
									+ addItemToCartReqGen.respvalidate.returnresponseasstring());

							AssertJUnit.assertTrue("Error while invoking CheckoutService addItemToCart API",
									addItemToCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
											.contains(SUCCESS_STATUS_TYPE));
							itemAdded = true;
							System.out.println(
									"\n" + skuId + " is available and it doesn't exists in cart so added to cart");
							log.info("\n" + skuId + " is available and it doesn't exists in cart so added to cart");
							break;

						} else {
							System.out.println(
									"\n" + skuId + " is not added because this item is currently out of stock");
							log.info("\n" + skuId + " is not added because this item is currently out of stock");
						}
					}
				}

			} else {
				System.out.println("\nError while invoking SearchService API");
				log.error("\nError while invoking SearchService API");
				AssertJUnit.fail("Error while invoking SearchService API");
			}
		}

		if (fetchCartReqGen.respvalidate.NodeValue(CartNodes.CART_USE_MYNT_CASH.toString(), false).equals(TRUE_VALUE)) {
			RequestGenerator removeMyntCreditReqGen = checkoutTestsHelper.removeMyntCredit(userName, password);
			System.out.println("\nPrinting CheckoutService removeMyntCredit API response :\n\n"
					+ removeMyntCreditReqGen.respvalidate.returnresponseasstring());
			log.info("\nPrinting CheckoutService removeMyntCredit API response :\n\n"
					+ removeMyntCreditReqGen.respvalidate.returnresponseasstring());

			AssertJUnit.assertTrue("Error while invoking CheckoutService removeMyntCredit API",
					removeMyntCreditReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
							.contains(SUCCESS_STATUS_TYPE));
		}

		RequestGenerator getMyntCreditReqGen = checkoutTestsHelper.getMyntCredit(userName, password);
		System.out.println("\nPrinting CheckoutService getMyntCredit API response :\n\n"
				+ getMyntCreditReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting getMyntCredit API response :\n\n"
				+ getMyntCreditReqGen.respvalidate.returnresponseasstring());
		System.out.println(
				"balance in user's cashback : " + getMyntCreditReqGen.respvalidate.NodeValue("data.balance", true));

		String useAmount = getMyntCreditReqGen.respvalidate.NodeValue("data.balance", true);

		RequestGenerator applyMyntCreditReqGen = checkoutTestsHelper.applyMyntCredit(userName, password, useAmount);
		System.out.println("\nPrinting CheckoutService applyMyntCredit API response :\n\n"
				+ applyMyntCreditReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService applyMyntCredit API response :\n\n"
				+ applyMyntCreditReqGen.respvalidate.returnresponseasstring());

		AssertJUnit.assertTrue("Unable apply mynt credit", applyMyntCreditReqGen.respvalidate
				.NodeValue(CartNodes.CART_USE_MYNT_CASH.toString(), true).equals(TRUE_VALUE));

		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;

		System.out.println(
				"\nTime taken to execute - TestCase - CheckoutServiceTests_applyMyntCredit_verifyMyntCreditNodes : "
						+ timeTaken + " seconds\n");
		log.info("\nTime taken to execute - TestCase - CheckoutServiceTests_applyMyntCredit_verifyMyntCreditNodes : "
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
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression", "MiniRegression",
			"ExhaustiveRegression" }, dataProvider = "CartServiceDP_getMyntCreditDataProvider_validateAPIResponse", description = "1. Hit IDP service & login as User"
					+ "\n 2. Hit getMyntCredit API. \n3. Verify API response statusCode must be of success type.")
	public void CartServiceTests_getMyntCredit_verifySuccessResponse(String userName, String password,
			String successRespCode) {
		long startTime = Calendar.getInstance().getTimeInMillis();

		RequestGenerator getMyntCreditReqGen = checkoutTestsHelper.getMyntCredit(userName, password);
		System.out.println("\nPrinting CheckoutService getMyntCredit API response :\n\n"
				+ getMyntCreditReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting getMyntCredit API response :\n\n"
				+ getMyntCreditReqGen.respvalidate.returnresponseasstring());

		AssertJUnit.assertEquals("CheckoutService getMyntCredit API is not working", Integer.parseInt(successRespCode),
				getMyntCreditReqGen.response.getStatus());

		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;

		System.out.println(
				"\nTime taken to execute - TestCase - CheckoutServiceTests_getMyntCredit_verifySuccessResponse : "
						+ timeTaken + " seconds\n");
		log.info("\nTime taken to execute - TestCase - CheckoutServiceTests_getMyntCredit_verifySuccessResponse : "
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
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression", "MiniRegression",
			"ExhaustiveRegression" }, dataProvider = "CartServiceDP_getMyntCreditDataProvider_positiveCases", description = "1. Hit IDP service & login as User"
					+ "\n 2. Hit getMyntCredit API. \n3. Verify API response statusCode, statusMessage & statusType must be of success type.")
	public void CartServiceTests_getMyntCredit_verifySuccessStatusResponse(String userName, String password,
			String successStatusCode, String successStatusMsg, String successStatusType) {
		long startTime = Calendar.getInstance().getTimeInMillis();

		RequestGenerator getMyntCreditReqGen = checkoutTestsHelper.getMyntCredit(userName, password);
		System.out.println("\nPrinting CheckoutService getMyntCredit API response :\n\n"
				+ getMyntCreditReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService getMyntCredit API response :\n\n"
				+ getMyntCreditReqGen.respvalidate.returnresponseasstring());

		AssertJUnit.assertTrue("CheckoutService getMyntCredit API response status code is not a success status code",
				getMyntCreditReqGen.respvalidate.NodeValue(StatusNodes.STATUS_CODE.toString(), true)
						.contains(successStatusCode));

		AssertJUnit.assertTrue(
				"CheckoutService getMyntCredit API response status message is not a success status message",
				getMyntCreditReqGen.respvalidate.NodeValue(StatusNodes.STATUS_MESSAGE.toString(), true)
						.contains(successStatusMsg));

		AssertJUnit.assertTrue("CheckoutService getMyntCredit API response status type is not a success status type",
				getMyntCreditReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
						.contains(successStatusType));

		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;

		System.out.println(
				"\nTime taken to execute - TestCase - CheckoutServiceTests_getMyntCredit_verifySuccessStatusResponse : "
						+ timeTaken + " seconds\n");
		log.info(
				"\nTime taken to execute - TestCase - CheckoutServiceTests_getMyntCredit_verifySuccessStatusResponse : "
						+ timeTaken + " seconds\n");
	}

	/**
	 * This Test Case used to invoke CheckoutService getMyntCredit API and
	 * verify for credit nodes
	 * 
	 * @param userName
	 * @param password
	 */
	@Test(groups = { "Sanity", "Regression", "MiniRegression",
			"ExhaustiveRegression" }, dataProvider = "CartServiceDP_getMyntCreditDataProvider_validateCreditNodes", description = "1. Hit IDP service & login as User"
					+ "\n 2. Hit getMyntCredit API. \n3. Verify myntCreditNodes in API response.")
	public void CartServiceTests_getMyntCredit_verifyMyntCreditNodes(String userName, String password) {
		long startTime = Calendar.getInstance().getTimeInMillis();

		RequestGenerator getMyntCreditReqGen = checkoutTestsHelper.getMyntCredit(userName, password);
		System.out.println("\nPrinting CheckoutService getMyntCredit API response :\n\n"
				+ getMyntCreditReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService getMyntCredit API response :\n\n"
				+ getMyntCreditReqGen.respvalidate.returnresponseasstring());

		AssertJUnit.assertTrue("Error while invoking CheckoutService getMyntCredit API",
				getMyntCreditReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
						.contains(SUCCESS_STATUS_TYPE));

		if (!getMyntCreditReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TOTAL_COUNT.toString(), true)
				.equals(EMPTY_VALUE)) {
			AssertJUnit.assertTrue("mynt credit information is not complete",
					getMyntCreditReqGen.respvalidate.DoesNodesExists(MyntCreditNodes.getCreditNodes()));
		} else {
			System.out.println("\nUnable to validate mynt credit information");
			log.info("\nUnable to validate mynt credit information");
		}

		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;

		System.out.println(
				"\nTime taken to execute - TestCase - CheckoutServiceTests_getMyntCredit_verifyMyntCreditNodes : "
						+ timeTaken + " seconds\n");
		log.info("\nTime taken to execute - TestCase - CheckoutServiceTests_getMyntCredit_verifyMyntCreditNodes : "
				+ timeTaken + " seconds\n");

	}

	/**
	 * This Test Case used to invoke CheckoutService removeMyntCredit API and
	 * verify for success response
	 * 
	 * @param userName
	 * @param password
	 * @param successRespCode
	 */
	@Test(groups = { "Sanity", "Regression", "MiniRegression",
			"ExhaustiveRegression" }, dataProvider = "CartServiceDP_removeMyntCreditDataProvider_validateAPIResponse", description = "1. hit IDP service and login the user. "
					+ "\n 2. hit fetchCart API. \n 3. if user's cart is empty then first add items to cart(\n a.hit Search Service API and extract some skuIDs"
					+ "\n b. Check if searched styleIds exists in user's cart or not. \n c. hit addItemToCart API giving skuId which doesnt exists in cart)"
					+ "\n 4. if cashback is not applied to cart,first apply cashback( \n a. hit getCashback API to fetch the active cashback amount of user"
					+ "\n b. hit applyCashback API giving the fetched cashback amount to be applied on cart)"
					+ "\n 5. hit removeCashback API" + "\n 6. Verify statusCode must be 200 of APIs response")
	public void CartServiceTests_removeMyntCredit_verifySuccessResponse(String userName, String password,
			String successRespCode) {
		long startTime = Calendar.getInstance().getTimeInMillis();
		String addItemQty = "1";
		Boolean itemAdded = false;
		RequestGenerator fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
		System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"
				+ fetchCartReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"
				+ fetchCartReqGen.respvalidate.returnresponseasstring());

		AssertJUnit.assertTrue(
				"CheckoutService operationFetchCart API response status type is not a success status type",
				fetchCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
						.contains(SUCCESS_STATUS_TYPE));

		if (fetchCartReqGen.respvalidate.NodeValue(CartNodes.CART_TOTAL_COUNT.toString(), true).equals(EMPTY_VALUE)) {
			List<Integer> styleIdList = checkoutTestsHelper.performSeachServiceToGetStyleIds(UC_SEARCH_PROD,
					UC_SEARCH_QTY, UC_SEARCH_RET_DOCS, UC_SEARCH_IS_FACET);

			if (!CollectionUtils.isEmpty(styleIdList)) {
				for (Integer styleId : styleIdList) {
					if (itemAdded)
						break;
					System.out.println("\nPrinting the StyleId : " + styleId);
					log.info("\nPrinting the StyleId : " + styleId);
					RequestGenerator getStyleDataReqGen = checkoutTestsHelper
							.performGetStyleDataOperation(String.valueOf(styleId));
					System.out.println("\nPrinting StyleService getStyleData API response :\n\n"
							+ getStyleDataReqGen.respvalidate.returnresponseasstring());
					log.info("\nPrinting StyleService getStyleData API response :\n\n"
							+ getStyleDataReqGen.respvalidate.returnresponseasstring());

					AssertJUnit.assertTrue("Error while getting style data", getStyleDataReqGen.respvalidate
							.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));

					String response = getStyleDataReqGen.respvalidate.returnresponseasstring();
					List<Integer> ids = JsonPath.read(response, "$.data..styleOptions[*].id");

					for (int i = 0; i < ids.size(); i++) {

						String skuId = JsonPath.read(response, "$.data..styleOptions[" + i + "].skuId").toString();
						String inventoryCount = JsonPath
								.read(response, "$.data..styleOptions[" + i + "].inventoryCount").toString();
						String available = JsonPath.read(response, "$.data..styleOptions[" + i + "].available")
								.toString();

						if (!inventoryCount.equals(EMPTY_VALUE) && available.equals(TRUE_VALUE)) {
							RequestGenerator addItemToCartReqGen = checkoutTestsHelper.addItemToCart(userName, password,
									skuId, String.valueOf(addItemQty), ADD_OPERATION);
							System.out.println("\nPrinting CheckoutService addItemToCart API response :\n\n"
									+ addItemToCartReqGen.respvalidate.returnresponseasstring());
							log.info("\nPrinting CheckoutService addItemToCart API response :\n\n"
									+ addItemToCartReqGen.respvalidate.returnresponseasstring());

							AssertJUnit.assertTrue("Error while invoking CheckoutService addItemToCart API",
									addItemToCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
											.contains(SUCCESS_STATUS_TYPE));
							itemAdded = true;
							System.out.println(
									"\n" + skuId + " is available and it doesn't exists in cart so added to cart");
							log.info("\n" + skuId + " is available and it doesn't exists in cart so added to cart");
							break;

						} else {
							System.out.println(
									"\n" + skuId + " is not added because this item is currently out of stock");
							log.info("\n" + skuId + " is not added because this item is currently out of stock");
						}
					}
				}

			} else {
				System.out.println("\nError while invoking SearchService API");
				log.error("\nError while invoking SearchService API");
				AssertJUnit.fail("Error while invoking SearchService API");
			}
		}

		if (fetchCartReqGen.respvalidate.NodeValue(CartNodes.CART_USE_MYNT_CASH.toString(), false)
				.equals(FALSE_VALUE)) {
			RequestGenerator getMyntCreditReqGen = checkoutTestsHelper.getMyntCredit(userName, password);
			System.out.println("\nPrinting CheckoutService getMyntCredit API response :\n\n"
					+ getMyntCreditReqGen.respvalidate.returnresponseasstring());
			log.info("\nPrinting getMyntCredit API response :\n\n"
					+ getMyntCreditReqGen.respvalidate.returnresponseasstring());
			System.out.println(
					"balance in user's cashback : " + getMyntCreditReqGen.respvalidate.NodeValue("data.balance", true));

			String useAmount = getMyntCreditReqGen.respvalidate.NodeValue("data.balance", true);

			RequestGenerator applyMyntCreditReqGen = checkoutTestsHelper.applyMyntCredit(userName, password, useAmount);
			System.out.println("\nPrinting CheckoutService applyMyntCredit API response :\n\n"
					+ applyMyntCreditReqGen.respvalidate.returnresponseasstring());
			log.info("\nPrinting CheckoutService applyMyntCredit API response :\n\n"
					+ applyMyntCreditReqGen.respvalidate.returnresponseasstring());

			AssertJUnit.assertTrue(
					"Error while invoking CheckoutService applyMyntCredit API--As cart was initially empty",
					applyMyntCreditReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
							.contains(SUCCESS_STATUS_TYPE));
		}

		RequestGenerator removeMyntCreditReqGen = checkoutTestsHelper.removeMyntCredit(userName, password);
		System.out.println("\nPrinting CheckoutService removeMyntCredit API response :\n\n"
				+ removeMyntCreditReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService removeMyntCredit API response :\n\n"
				+ removeMyntCreditReqGen.respvalidate.returnresponseasstring());

		AssertJUnit.assertEquals("CheckoutService removeMyntCredit API is not working",
				Integer.parseInt(successRespCode), removeMyntCreditReqGen.response.getStatus());

		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;

		System.out.println(
				"\nTime taken to execute - TestCase - CheckoutServiceTests_removeMyntCredit_verifySuccessResponse : "
						+ timeTaken + " seconds\n");
		log.info("\nTime taken to execute - TestCase - CheckoutServiceTests_removeMyntCredit_verifySuccessResponse : "
				+ timeTaken + " seconds\n");
	}

	/**
	 * This Test Case used to invoke CheckoutService removeMyntCredit API and
	 * verify for success status response
	 * 
	 * @param userName
	 * @param password
	 * @param successStatusCode
	 * @param successStatusMsg
	 * @param successStatusType
	 */
	@Test(groups = { "Sanity", "Regression", "MiniRegression",
			"ExhaustiveRegression" }, dataProvider = "CartServiceDP_removeMyntCreditDataProvider_PositiveCases", description = "1. hit IDP service and login the user. "
					+ "\n 2. hit fetchCart API. \n 3. if user's cart is empty then first add items to cart(\n a.hit Search Service API and extract some skuIDs"
					+ "\n b. Check if searched styleIds exists in user's cart or not. \n c. hit addItemToCart API giving skuId which doesnt exists in cart)"
					+ "\n 4. if cashback is not applied to cart,first apply cashback( \n a. hit getCashback API to fetch the active cashback amount of user"
					+ "\n b. hit applyCashback API giving the fetched cashback amount to be applied on cart)"
					+ "\n 5. hit removeCashback API"
					+ "\n 6. Verify statusCode, statusMessage & statusType must be of Success in APIs response")
	public void CartServiceTests_removeMyntCredit_verifySuccessStatusResponse(String userName, String password,
			String successStatusCode, String successStatusMsg, String successStatusType) {
		long startTime = Calendar.getInstance().getTimeInMillis();
		String addItemQty = "1";
		Boolean itemAdded = false;
		RequestGenerator fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
		System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"
				+ fetchCartReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"
				+ fetchCartReqGen.respvalidate.returnresponseasstring());

		AssertJUnit.assertTrue("Error while invoking CheckoutService operationFetchCart API",
				fetchCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
						.contains(SUCCESS_STATUS_TYPE));

		if (fetchCartReqGen.respvalidate.NodeValue(CartNodes.CART_TOTAL_COUNT.toString(), true).equals(EMPTY_VALUE)) {
			List<Integer> styleIdList = checkoutTestsHelper.performSeachServiceToGetStyleIds(UC_SEARCH_PROD,
					UC_SEARCH_QTY, UC_SEARCH_RET_DOCS, UC_SEARCH_IS_FACET);

			if (!CollectionUtils.isEmpty(styleIdList)) {
				for (Integer styleId : styleIdList) {
					if (itemAdded)
						break;
					System.out.println("\nPrinting the StyleId : " + styleId);
					log.info("\nPrinting the StyleId : " + styleId);
					RequestGenerator getStyleDataReqGen = checkoutTestsHelper
							.performGetStyleDataOperation(String.valueOf(styleId));
					System.out.println("\nPrinting StyleService getStyleData API response :\n\n"
							+ getStyleDataReqGen.respvalidate.returnresponseasstring());
					log.info("\nPrinting StyleService getStyleData API response :\n\n"
							+ getStyleDataReqGen.respvalidate.returnresponseasstring());

					AssertJUnit.assertTrue("Error while getting style data", getStyleDataReqGen.respvalidate
							.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));

					String response = getStyleDataReqGen.respvalidate.returnresponseasstring();
					List<Integer> ids = JsonPath.read(response, "$.data..styleOptions[*].id");

					for (int i = 0; i < ids.size(); i++) {

						String skuId = JsonPath.read(response, "$.data..styleOptions[" + i + "].skuId").toString();
						String inventoryCount = JsonPath
								.read(response, "$.data..styleOptions[" + i + "].inventoryCount").toString();
						String available = JsonPath.read(response, "$.data..styleOptions[" + i + "].available")
								.toString();

						if (!inventoryCount.equals(EMPTY_VALUE) && available.equals(TRUE_VALUE)) {
							RequestGenerator addItemToCartReqGen = checkoutTestsHelper.addItemToCart(userName, password,
									skuId, String.valueOf(addItemQty), ADD_OPERATION);
							System.out.println("\nPrinting CheckoutService addItemToCart API response :\n\n"
									+ addItemToCartReqGen.respvalidate.returnresponseasstring());
							log.info("\nPrinting CheckoutService addItemToCart API response :\n\n"
									+ addItemToCartReqGen.respvalidate.returnresponseasstring());

							AssertJUnit.assertTrue("Error while invoking CheckoutService addItemToCart API",
									addItemToCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
											.contains(SUCCESS_STATUS_TYPE));
							itemAdded = true;
							System.out.println(
									"\n" + skuId + " is available and it doesn't exists in cart so added to cart");
							log.info("\n" + skuId + " is available and it doesn't exists in cart so added to cart");
							break;

						} else {
							System.out.println(
									"\n" + skuId + " is not added because this item is currently out of stock");
							log.info("\n" + skuId + " is not added because this item is currently out of stock");
						}
					}
				}

			} else {
				System.out.println("\nError while invoking SearchService API");
				log.error("\nError while invoking SearchService API");
				AssertJUnit.fail("Error while invoking SearchService API");
			}
		}

		if (fetchCartReqGen.respvalidate.NodeValue(CartNodes.CART_USE_MYNT_CASH.toString(), false)
				.equals(FALSE_VALUE)) {

			RequestGenerator getMyntCreditReqGen = checkoutTestsHelper.getMyntCredit(userName, password);
			System.out.println("\nPrinting CheckoutService getMyntCredit API response :\n\n"
					+ getMyntCreditReqGen.respvalidate.returnresponseasstring());
			log.info("\nPrinting getMyntCredit API response :\n\n"
					+ getMyntCreditReqGen.respvalidate.returnresponseasstring());
			System.out.println(
					"balance in user's cashback : " + getMyntCreditReqGen.respvalidate.NodeValue("data.balance", true));

			String useAmount = getMyntCreditReqGen.respvalidate.NodeValue("data.balance", true);

			RequestGenerator applyMyntCreditReqGen = checkoutTestsHelper.applyMyntCredit(userName, password, useAmount);
			System.out.println("\nPrinting CheckoutService applyMyntCredit API response :\n\n"
					+ applyMyntCreditReqGen.respvalidate.returnresponseasstring());
			log.info("\nPrinting CheckoutService applyMyntCredit API response :\n\n"
					+ applyMyntCreditReqGen.respvalidate.returnresponseasstring());

			AssertJUnit.assertTrue("Error while invoking CheckoutService applyMyntCredit API",
					applyMyntCreditReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
							.contains(SUCCESS_STATUS_TYPE));
		}

		RequestGenerator removeMyntCreditReqGen = checkoutTestsHelper.removeMyntCredit(userName, password);
		System.out.println("\nPrinting CheckoutService removeMyntCredit API response :\n\n"
				+ removeMyntCreditReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService removeMyntCredit API response :\n\n"
				+ removeMyntCreditReqGen.respvalidate.returnresponseasstring());

		AssertJUnit.assertTrue("CheckoutService removeMyntCredit API response status code is not a success status code",
				removeMyntCreditReqGen.respvalidate.NodeValue(StatusNodes.STATUS_CODE.toString(), true)
						.contains(successStatusCode));

		AssertJUnit.assertTrue(
				"CheckoutService removeMyntCredit API response status message is not a success status message",
				removeMyntCreditReqGen.respvalidate.NodeValue(StatusNodes.STATUS_MESSAGE.toString(), true)
						.contains(successStatusMsg));

		AssertJUnit.assertTrue("CheckoutService removeMyntCredit API response status type is not a success status type",
				removeMyntCreditReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
						.contains(successStatusType));

		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;

		System.out.println(
				"\nTime taken to execute - TestCase - CheckoutServiceTests_removeMyntCredit_verifySuccessStatusResponse : "
						+ timeTaken + " seconds\n");
		log.info(
				"\nTime taken to execute - TestCase - CheckoutServiceTests_removeMyntCredit_verifySuccessStatusResponse : "
						+ timeTaken + " seconds\n");

	}

	/**
	 * This Test Case used to invoke CheckoutService removeMyntCredit API and
	 * verify for credit nodes
	 * 
	 * @param userName
	 * @param password
	 */
	@Test(groups = { "Sanity", "Regression", "MiniRegression",
			"ExhaustiveRegression" }, dataProvider = "CartServiceDP_removeMyntCreditDataProvider_ValidateRemovedInfo", description = "1. hit IDP service and login the user. "
					+ "\n 2. hit fetchCart API. \n 3. if user's cart is empty then first add items to cart(\n a.hit Search Service API and extract some skuIDs"
					+ "\n b. Check if searched styleIds exists in user's cart or not. \n c. hit addItemToCart API giving skuId which doesnt exists in cart)"
					+ "\n 4. if cashback is not applied to cart,first apply cashback( \n a. hit getCashback API to fetch the active cashback amount of user"
					+ "\n b. hit applyCashback API giving the fetched cashback amount to be applied on cart)"
					+ "\n 5. hit removeCashback API" + "\n 6. Verify Cashback Nodes in APIs response")
	public void CartServiceTests_removeMyntCredit_verifyMyntCreditNodes(String userName, String password) {
		long startTime = Calendar.getInstance().getTimeInMillis();
		String addItemQty = "1";
		Boolean itemAdded = false;
		RequestGenerator fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
		System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"
				+ fetchCartReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"
				+ fetchCartReqGen.respvalidate.returnresponseasstring());

		AssertJUnit.assertTrue("Error while invoking CheckoutService operationFetchCart API",
				fetchCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
						.contains(SUCCESS_STATUS_TYPE));
		if (fetchCartReqGen.respvalidate.NodeValue(CartNodes.CART_TOTAL_COUNT.toString(), true).equals(EMPTY_VALUE)) {
			List<Integer> styleIdList = checkoutTestsHelper.performSeachServiceToGetStyleIds(UC_SEARCH_PROD,
					UC_SEARCH_QTY, UC_SEARCH_RET_DOCS, UC_SEARCH_IS_FACET);

			if (!CollectionUtils.isEmpty(styleIdList)) {
				for (Integer styleId : styleIdList) {
					if (itemAdded)
						break;
					System.out.println("\nPrinting the StyleId : " + styleId);
					log.info("\nPrinting the StyleId : " + styleId);
					RequestGenerator getStyleDataReqGen = checkoutTestsHelper
							.performGetStyleDataOperation(String.valueOf(styleId));
					System.out.println("\nPrinting StyleService getStyleData API response :\n\n"
							+ getStyleDataReqGen.respvalidate.returnresponseasstring());
					log.info("\nPrinting StyleService getStyleData API response :\n\n"
							+ getStyleDataReqGen.respvalidate.returnresponseasstring());

					AssertJUnit.assertTrue("Error while getting style data", getStyleDataReqGen.respvalidate
							.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));

					String response = getStyleDataReqGen.respvalidate.returnresponseasstring();
					List<Integer> ids = JsonPath.read(response, "$.data..styleOptions[*].id");

					for (int i = 0; i < ids.size(); i++) {

						String skuId = JsonPath.read(response, "$.data..styleOptions[" + i + "].skuId").toString();
						String inventoryCount = JsonPath
								.read(response, "$.data..styleOptions[" + i + "].inventoryCount").toString();
						String available = JsonPath.read(response, "$.data..styleOptions[" + i + "].available")
								.toString();

						if (!inventoryCount.equals(EMPTY_VALUE) && available.equals(TRUE_VALUE)) {
							RequestGenerator addItemToCartReqGen = checkoutTestsHelper.addItemToCart(userName, password,
									skuId, String.valueOf(addItemQty), ADD_OPERATION);
							System.out.println("\nPrinting CheckoutService addItemToCart API response :\n\n"
									+ addItemToCartReqGen.respvalidate.returnresponseasstring());
							log.info("\nPrinting CheckoutService addItemToCart API response :\n\n"
									+ addItemToCartReqGen.respvalidate.returnresponseasstring());

							AssertJUnit.assertTrue("Error while invoking CheckoutService addItemToCart API",
									addItemToCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
											.contains(SUCCESS_STATUS_TYPE));
							itemAdded = true;
							System.out.println(
									"\n" + skuId + " is available and it doesn't exists in cart so added to cart");
							log.info("\n" + skuId + " is available and it doesn't exists in cart so added to cart");
							break;

						} else {
							System.out.println(
									"\n" + skuId + " is not added because this item is currently out of stock");
							log.info("\n" + skuId + " is not added because this item is currently out of stock");
						}
					}
				}

			} else {
				System.out.println("\nError while invoking SearchService API");
				log.error("\nError while invoking SearchService API");
				AssertJUnit.fail("Error while invoking SearchService API");
			}
		}

		if (fetchCartReqGen.respvalidate.NodeValue(CartNodes.CART_USE_MYNT_CASH.toString(), false)
				.equals(FALSE_VALUE)) {

			RequestGenerator getMyntCreditReqGen = checkoutTestsHelper.getMyntCredit(userName, password);
			System.out.println("\nPrinting CheckoutService getMyntCredit API response :\n\n"
					+ getMyntCreditReqGen.respvalidate.returnresponseasstring());
			log.info("\nPrinting getMyntCredit API response :\n\n"
					+ getMyntCreditReqGen.respvalidate.returnresponseasstring());
			System.out.println(
					"balance in user's cashback : " + getMyntCreditReqGen.respvalidate.NodeValue("data.balance", true));

			String useAmount = getMyntCreditReqGen.respvalidate.NodeValue("data.balance", true);

			RequestGenerator applyMyntCreditReqGen = checkoutTestsHelper.applyMyntCredit(userName, password, useAmount);
			System.out.println("\nPrinting CheckoutService applyMyntCredit API response :\n\n"
					+ applyMyntCreditReqGen.respvalidate.returnresponseasstring());
			log.info("\nPrinting CheckoutService applyMyntCredit API response :\n\n"
					+ applyMyntCreditReqGen.respvalidate.returnresponseasstring());

			AssertJUnit.assertTrue("Error while invoking CheckoutService applyMyntCredit API",
					applyMyntCreditReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
							.contains(SUCCESS_STATUS_TYPE));
		}

		RequestGenerator removeMyntCreditReqGen = checkoutTestsHelper.removeMyntCredit(userName, password);
		System.out.println("\nPrinting CheckoutService removeMyntCredit API response :\n\n"
				+ removeMyntCreditReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService removeMyntCredit API response :\n\n"
				+ removeMyntCreditReqGen.respvalidate.returnresponseasstring());

		AssertJUnit.assertTrue("Error while invoking CheckoutService removeMyntCredit API",
				removeMyntCreditReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
						.contains(SUCCESS_STATUS_TYPE));

		AssertJUnit.assertTrue("Unable to remove the mynt credits", removeMyntCreditReqGen.respvalidate
				.NodeValue(CartNodes.CART_USE_MYNT_CASH.toString(), true).equals(FALSE_VALUE));

		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;

		System.out.println(
				"\nTime taken to execute - TestCase - CheckoutServiceTests_removeMyntCredit_verifyMyntCreditNodes : "
						+ timeTaken + " seconds\n");
		log.info("\nTime taken to execute - TestCase - CheckoutServiceTests_removeMyntCredit_verifyMyntCreditNodes : "
				+ timeTaken + " seconds\n");

	}

	/**
	 * This Test Case used to invoke CheckoutService applyCoupon API and verify
	 * for success response
	 * 
	 * @param userName
	 * @param password
	 * @param successRespCode
	 */
	@Test(groups = { "Sanity", "Regression", "MiniRegression",
			"ExhaustiveRegression" }, dataProvider = "CartServiceDP_applyCouponsDataProvider_validateAPIResponse", description = "1. hit IDP service and login the user. "
					+ "\n 2. hit fetchCart API. \n 3. if user's cart is empty then first add items to cart(\n a.hit Search Service API and extract some skuIDs"
					+ "\n b. Check if searched styleIds exists in user's cart or not. \n c. hit addItemToCart API giving skuId which doesnt exists in cart)"
					+ "\n 4. hit fetchAllCoupons API and fetch the coupons applicable to user's cart"
					+ "\n 5. hit applyCoupon API by giving correct coupon name fetched"
					+ "\n 6. Verify APIs response statusCode must be 200")
	public void CartServiceTests_applyCoupon_verifySuccessResponse(String userName, String password,
			String successRespCode) {
		long startTime = Calendar.getInstance().getTimeInMillis();
		String addItemQty = "1";
		Boolean itemAdded = false;
		userName ="testing_wallet@myntra.com";
		RequestGenerator fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
		System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"
				+ fetchCartReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"
				+ fetchCartReqGen.respvalidate.returnresponseasstring());

		AssertJUnit.assertTrue(
				"CheckoutService operationFetchCart API response status type is not a success status type",
				fetchCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
						.contains(SUCCESS_STATUS_TYPE));

		if (fetchCartReqGen.respvalidate.NodeValue(CartNodes.CART_TOTAL_COUNT.toString(), true).equals(EMPTY_VALUE)) {
			List<Integer> styleIdList = checkoutTestsHelper.performSeachServiceToGetStyleIds(AC_SEARCH_PROD,
					AC_SEARCH_QTY, AC_SEARCH_RET_DOCS, AC_SEARCH_IS_FACET);

			if (!CollectionUtils.isEmpty(styleIdList)) {
				for (Integer styleId : styleIdList) {
					if (itemAdded)
						break;
					System.out.println("\nPrinting the StyleId : " + styleId);
					log.info("\nPrinting the StyleId : " + styleId);
					RequestGenerator getStyleDataReqGen = checkoutTestsHelper
							.performGetStyleDataOperation(String.valueOf(styleId));
					System.out.println("\nPrinting StyleService getStyleData API response :\n\n"
							+ getStyleDataReqGen.respvalidate.returnresponseasstring());
					log.info("\nPrinting StyleService getStyleData API response :\n\n"
							+ getStyleDataReqGen.respvalidate.returnresponseasstring());

					AssertJUnit.assertTrue("Error while getting style data", getStyleDataReqGen.respvalidate
							.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));

					String response = getStyleDataReqGen.respvalidate.returnresponseasstring();
					List<Integer> ids = JsonPath.read(response, "$.data..styleOptions[*].id");

					for (int i = 0; i < ids.size(); i++) {

						String skuId = JsonPath.read(response, "$.data..styleOptions[" + i + "].skuId").toString();
						String inventoryCount = JsonPath
								.read(response, "$.data..styleOptions[" + i + "].inventoryCount").toString();
						String available = JsonPath.read(response, "$.data..styleOptions[" + i + "].available")
								.toString();

						if (!inventoryCount.equals(EMPTY_VALUE) && available.equals(TRUE_VALUE)) {
							RequestGenerator addItemToCartReqGen = checkoutTestsHelper.addItemToCart(userName, password,
									skuId, String.valueOf(addItemQty), ADD_OPERATION);
							System.out.println("\nPrinting CheckoutService addItemToCart API response :\n\n"
									+ addItemToCartReqGen.respvalidate.returnresponseasstring());
							log.info("\nPrinting CheckoutService addItemToCart API response :\n\n"
									+ addItemToCartReqGen.respvalidate.returnresponseasstring());

							AssertJUnit.assertTrue("Error while invoking CheckoutService addItemToCart API",
									addItemToCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
											.contains(SUCCESS_STATUS_TYPE));
							itemAdded = true;
							System.out.println(
									"\n" + skuId + " is available and it doesn't exists in cart so added to cart");
							log.info("\n" + skuId + " is available and it doesn't exists in cart so added to cart");
							break;

						} else {
							System.out.println(
									"\n" + skuId + " is not added because this item is currently out of stock");
							log.info("\n" + skuId + " is not added because this item is currently out of stock");
						}
					}
				}

			} else {
				System.out.println("\nError while invoking SearchService API");
				log.error("\nError while invoking SearchService API");
				AssertJUnit.fail("Error while invoking SearchService API");
			}
		}

		RequestGenerator fetchAllCouponsReqGen = checkoutTestsHelper.fetchAllCoupons(userName, password);
		System.out.println("\nPrinting CheckoutService fetchAllCoupons API response :\n\n"
				+ fetchAllCouponsReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService fetchAllCoupons API response :\n\n"
				+ fetchAllCouponsReqGen.respvalidate.returnresponseasstring());

		AssertJUnit.assertTrue("Error while invoking CheckoutService fetchAllCoupons API",
				fetchAllCouponsReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
						.contains(SUCCESS_STATUS_TYPE));

		RequestGenerator applyCouponReqGen = checkoutTestsHelper.applyCoupon(userName, password,
				fetchAllCouponsReqGen.respvalidate.NodeValue("data.coupon", false));
		System.out.println("\nPrinting CheckoutService applyCoupon API response :\n\n"
				+ applyCouponReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService applyCoupon API response :\n\n"
				+ applyCouponReqGen.respvalidate.returnresponseasstring());

		AssertJUnit.assertEquals("CheckoutService applyCoupon API is not working", Integer.parseInt(successRespCode),
				applyCouponReqGen.response.getStatus());

		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;

		System.out.println(
				"\nTime taken to execute - TestCase - CheckoutServiceTests_applyCoupon_verifySuccessResponse : "
						+ timeTaken + " seconds\n");
		log.info("\nTime taken to execute - TestCase - CheckoutServiceTests_applyCoupon_verifySuccessResponse : "
				+ timeTaken + " seconds\n");
	}

	/**
	 * This Test Case used to invoke CheckoutService applyCoupon API and verify
	 * for success status response
	 * 
	 * @param userName
	 * @param password
	 * @param successStatusCode
	 * @param successStatusMsg
	 * @param successStatusType
	 */
	@Test(groups = { "Sanity", "Regression", "MiniRegression",
			"ExhaustiveRegression" }, dataProvider = "CartServiceDP_applyCouponsDataProvider_positiveCases", description = "1. hit IDP service and login the user. "
					+ "\n 2. hit fetchCart API. \n 3. if user's cart is empty then first add items to cart(\n a.hit Search Service API and extract some skuIDs"
					+ "\n b. Check if searched styleIds exists in user's cart or not. \n c. hit addItemToCart API giving skuId which doesnt exists in cart)"
					+ "\n 4. hit fetchAllCoupons API and fetch the coupons applicable to user's cart"
					+ "\n 5. hit applyCoupon API by giving correct coupon name fetched"
					+ "\n 6. Verify statusCode, statusMessage & statusType in APIs response must be of Success")
	public void CartServiceTests_applyCoupon_verifySuccessStatusResponse(String userName, String password,
			String successStatusCode, String successStatusMsg, String successStatusType) {
		long startTime = Calendar.getInstance().getTimeInMillis();
		String addItemQty = "1";
		Boolean itemAdded = false;
		userName = "testing_wallet@myntra.com";
		RequestGenerator fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
		System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"
				+ fetchCartReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"
				+ fetchCartReqGen.respvalidate.returnresponseasstring());

		AssertJUnit.assertTrue(
				"CheckoutService operationFetchCart API response status type is not a success status type",
				fetchCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
						.contains(SUCCESS_STATUS_TYPE));

		if (fetchCartReqGen.respvalidate.NodeValue(CartNodes.CART_TOTAL_COUNT.toString(), true).equals(EMPTY_VALUE)) {
			List<Integer> styleIdList = checkoutTestsHelper.performSeachServiceToGetStyleIds(AC_SEARCH_PROD,
					AC_SEARCH_QTY, AC_SEARCH_RET_DOCS, AC_SEARCH_IS_FACET);

			if (!CollectionUtils.isEmpty(styleIdList)) {
				for (Integer styleId : styleIdList) {
					if (itemAdded)
						break;
					System.out.println("\nPrinting the StyleId : " + styleId);
					log.info("\nPrinting the StyleId : " + styleId);
					RequestGenerator getStyleDataReqGen = checkoutTestsHelper
							.performGetStyleDataOperation(String.valueOf(styleId));
					System.out.println("\nPrinting StyleService getStyleData API response :\n\n"
							+ getStyleDataReqGen.respvalidate.returnresponseasstring());
					log.info("\nPrinting StyleService getStyleData API response :\n\n"
							+ getStyleDataReqGen.respvalidate.returnresponseasstring());

					AssertJUnit.assertTrue("Error while getting style data", getStyleDataReqGen.respvalidate
							.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));

					String response = getStyleDataReqGen.respvalidate.returnresponseasstring();
					List<Integer> ids = JsonPath.read(response, "$.data..styleOptions[*].id");

					for (int i = 0; i < ids.size(); i++) {

						String skuId = JsonPath.read(response, "$.data..styleOptions[" + i + "].skuId").toString();
						String inventoryCount = JsonPath
								.read(response, "$.data..styleOptions[" + i + "].inventoryCount").toString();
						String available = JsonPath.read(response, "$.data..styleOptions[" + i + "].available")
								.toString();

						if (!inventoryCount.equals(EMPTY_VALUE) && available.equals(TRUE_VALUE)) {
							RequestGenerator addItemToCartReqGen = checkoutTestsHelper.addItemToCart(userName, password,
									skuId, String.valueOf(addItemQty), ADD_OPERATION);
							System.out.println("\nPrinting CheckoutService addItemToCart API response :\n\n"
									+ addItemToCartReqGen.respvalidate.returnresponseasstring());
							log.info("\nPrinting CheckoutService addItemToCart API response :\n\n"
									+ addItemToCartReqGen.respvalidate.returnresponseasstring());

							AssertJUnit.assertTrue("Error while invoking CheckoutService addItemToCart API",
									addItemToCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
											.contains(SUCCESS_STATUS_TYPE));
							itemAdded = true;
							System.out.println(
									"\n" + skuId + " is available and it doesn't exists in cart so added to cart");
							log.info("\n" + skuId + " is available and it doesn't exists in cart so added to cart");
							break;

						} else {
							System.out.println(
									"\n" + skuId + " is not added because this item is currently out of stock");
							log.info("\n" + skuId + " is not added because this item is currently out of stock");
						}
					}
				}

			} else {
				System.out.println("\nError while invoking SearchService API");
				log.error("\nError while invoking SearchService API");
				AssertJUnit.fail("Error while invoking SearchService API");
			}
		}

		RequestGenerator fetchAllCouponsReqGen = checkoutTestsHelper.fetchAllCoupons(userName, password);
		System.out.println("\nPrinting CheckoutService fetchAllCoupons API response :\n\n"
				+ fetchAllCouponsReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService fetchAllCoupons API response :\n\n"
				+ fetchAllCouponsReqGen.respvalidate.returnresponseasstring());

		AssertJUnit.assertTrue("Error while invoking CheckoutService fetchAllCoupons API",
				fetchAllCouponsReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
						.contains(SUCCESS_STATUS_TYPE));

		RequestGenerator applyCouponReqGen = checkoutTestsHelper.applyCoupon(userName, password,
				fetchAllCouponsReqGen.respvalidate.NodeValue("data.coupon", false));
		System.out.println("\nPrinting CheckoutService applyCoupon API response :\n\n"
				+ applyCouponReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService applyCoupon API response :\n\n"
				+ applyCouponReqGen.respvalidate.returnresponseasstring());

		AssertJUnit.assertTrue("CheckoutService applyCoupon API response status code is not a success status code",
				applyCouponReqGen.respvalidate.NodeValue(StatusNodes.STATUS_CODE.toString(), true)
						.contains(successStatusCode));

		AssertJUnit.assertTrue(
				"CheckoutService applyCoupon API response status message is not a success status message",
				applyCouponReqGen.respvalidate.NodeValue(StatusNodes.STATUS_MESSAGE.toString(), true)
						.contains(successStatusMsg));

		AssertJUnit.assertTrue("CheckoutService applyCoupon API response status type is not a success status type",
				applyCouponReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
						.contains(successStatusType));

		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;

		System.out.println(
				"\nTime taken to execute - TestCase - CheckoutServiceTests_applyCoupon_verifySuccessStatusResponse : "
						+ timeTaken + " seconds\n");
		log.info("\nTime taken to execute - TestCase - CheckoutServiceTests_applyCoupon_verifySuccessStatusResponse : "
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
			"ExhaustiveRegression" }, dataProvider = "CartServiceDP_fetchAllCouponsDataProvider_validateAPIResponse", description = "1. hit IDP service and login the user. "
					+ "\n 2. hit fetchAllCoupons API and fetch the coupons applicable to user's cart"
					+ "\n 3. Verify APIs response statusCode must be 200")
	public void CartServiceTests_fetchAllCoupons_verifySuccessResponse(String userName, String password,
			String successRespCode) {
		long startTime = Calendar.getInstance().getTimeInMillis();
		RequestGenerator fetchAllCouponsReqGen = checkoutTestsHelper.fetchAllCoupons(userName, password);
		System.out.println("\nPrinting CheckoutService fetchAllCoupons API response :\n\n"
				+ fetchAllCouponsReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService fetchAllCoupons API response :\n\n"
				+ fetchAllCouponsReqGen.respvalidate.returnresponseasstring());

		AssertJUnit.assertEquals("CheckoutService fetchAllCoupons API is not working",
				Integer.parseInt(successRespCode), fetchAllCouponsReqGen.response.getStatus());

		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;

		System.out.println(
				"\nTime taken to execute - TestCase - CheckoutServiceTests_fetchAllCoupons_verifySuccessResponse : "
						+ timeTaken + " seconds\n");
		log.info("\nTime taken to execute - TestCase - CheckoutServiceTests_fetchAllCoupons_verifySuccessResponse : "
				+ timeTaken + " seconds\n");
	}

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
			"ExhaustiveRegression" }, dataProvider = "CartServiceDP_fetchAllCouponsDataProvider_positiveCases", description = "1. hit IDP service and login the user. "
					+ "\n 2. hit fetchAllCoupons API and fetch the coupons applicable to user's cart"
					+ "\n 3. Verify APIs response statusCode, statusMessage & statusType must be of Success")
	public void CartServiceTests_fetchAllCoupons_verifySuccessStatusResponse(String userName, String password,
			String successStatusCode, String successStatusMsg, String successStatusType) {
		long startTime = Calendar.getInstance().getTimeInMillis();
		RequestGenerator fetchAllCouponsReqGen = checkoutTestsHelper.fetchAllCoupons(userName, password);
		System.out.println("\nPrinting CheckoutService fetchAllCoupons API response :\n\n"
				+ fetchAllCouponsReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService fetchAllCoupons API response :\n\n"
				+ fetchAllCouponsReqGen.respvalidate.returnresponseasstring());

		AssertJUnit.assertTrue("CheckoutService fetchAllCoupons API response status code is not a success status code",
				fetchAllCouponsReqGen.respvalidate.NodeValue(StatusNodes.STATUS_CODE.toString(), true)
						.contains(successStatusCode));

		AssertJUnit.assertTrue(
				"CheckoutService fetchAllCoupons API response status message is not a success status message",
				fetchAllCouponsReqGen.respvalidate.NodeValue(StatusNodes.STATUS_MESSAGE.toString(), true)
						.contains(successStatusMsg));

		AssertJUnit.assertTrue("CheckoutService fetchAllCoupons API response status type is not a success status type",
				fetchAllCouponsReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
						.contains(successStatusType));

		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;

		System.out.println(
				"\nTime taken to execute - TestCase - CheckoutServiceTests_fetchAllCoupons_verifySuccessStatusResponse : "
						+ timeTaken + " seconds\n");
		log.info(
				"\nTime taken to execute - TestCase - CheckoutServiceTests_fetchAllCoupons_verifySuccessStatusResponse : "
						+ timeTaken + " seconds\n");
	}

	/**
	 * This Test Case used to invoke CheckoutService RemoveCoupon API and
	 * verification for success response(200)
	 * 
	 * @param userName
	 * @param password
	 * @param successRespCode
	 */
	@Test(groups = { "" }, dataProvider = "CartServiceDP_removeCouponDataProvider_validateAPIResponse")
	public void CartServiceTests_removeCoupon_verifySuccessResponse(String userName, String password,
			String successRespCode) {
		long startTime = Calendar.getInstance().getTimeInMillis();
		RequestGenerator removeCouponReqGen = new CheckoutTestsHelper().removeCoupon(userName, password, "vat1");
		System.out.println("\nPrinting CheckoutService removeCoupon API response :\n\n"
				+ removeCouponReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService removeCoupon API response :\n\n"
				+ removeCouponReqGen.respvalidate.returnresponseasstring());

		AssertJUnit.assertEquals("CheckoutService removeCoupon API is not working", Integer.parseInt(successRespCode),
				removeCouponReqGen.response.getStatus());

		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;

		System.out.println(
				"\nTime taken to execute - TestCase - CheckoutServiceTests_removeCoupon_verifySuccessResponse : "
						+ timeTaken + " seconds\n");
		log.info("\nTime taken to execute - TestCase - CheckoutServiceTests_removeCoupon_verifySuccessResponse : "
				+ timeTaken + " seconds\n");
	}

	/**
	 * This Test Case used to invoke CheckoutService RemoveCoupon API and
	 * verification for success status response
	 * 
	 * @param userName
	 * @param password
	 * @param successStatusCode
	 * @param successStatusMsg
	 * @param successStatusType
	 */
	@Test(groups = { "" }, dataProvider = "CartServiceDP_removeCouponDataProvider_positiveCases")
	public void CartServiceTests_removeCoupon_verifySuccessStatusResponse(String userName, String password,
			String successStatusCode, String successStatusMsg, String successStatusType) {
		long startTime = Calendar.getInstance().getTimeInMillis();
		RequestGenerator removeCouponReqGen = new CheckoutTestsHelper().removeCoupon(userName, password,
				"promocoupon1");
		System.out.println("\nPrinting CheckoutService removeCoupon API response :\n\n"
				+ removeCouponReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService removeCoupon API response :\n\n"
				+ removeCouponReqGen.respvalidate.returnresponseasstring());

		AssertJUnit.assertTrue("CheckoutService removeCoupon API response status code is not a success status code",
				removeCouponReqGen.respvalidate.NodeValue(StatusNodes.STATUS_CODE.toString(), true)
						.contains(successStatusCode));

		AssertJUnit.assertTrue(
				"CheckoutService removeCoupon API response status message is not a success status message",
				removeCouponReqGen.respvalidate.NodeValue(StatusNodes.STATUS_MESSAGE.toString(), true)
						.contains(successStatusMsg));

		AssertJUnit.assertTrue("CheckoutService removeCoupon API response status type is not a success status type",
				removeCouponReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
						.contains(successStatusType));

		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;

		System.out.println(
				"\nTime taken to execute - TestCase - CheckoutServiceTests_removeCoupon_verifySuccessStatusResponse : "
						+ timeTaken + " seconds\n");
		log.info("\nTime taken to execute - TestCase - CheckoutServiceTests_removeCoupon_verifySuccessStatusResponse : "
				+ timeTaken + " seconds\n");

	}

	@Test(groups = { "SchemaValidation",
			"CartAPIsSchemaValidation" }, dataProvider = "CartServiceDP_addItemToCartDataProvider_verifyResponseDataNodesUsingSchemaValidations")
	public void CartServiceTests_addItemToCart_verifyResponseDataNodesUsingSchemaValidations(String userName,
			String password, String addItemQty, String addOperation, String successRespCode) {
		List<Integer> styleIdList = checkoutTestsHelper.performSeachServiceToGetStyleIds(AC_SEARCH_PROD, AC_SEARCH_QTY,
				AC_SEARCH_RET_DOCS, AC_SEARCH_IS_FACET);

		if (!CollectionUtils.isEmpty(styleIdList)) {
			for (Integer styleId : styleIdList) {
				System.out.println("\nPrinting the StyleId : " + styleId);
				log.info("\nPrinting the StyleId : " + styleId);

				RequestGenerator getStyleDataReqGen = checkoutTestsHelper
						.performGetStyleDataOperation(String.valueOf(styleId));
				System.out.println("\nPrinting StyleService getStyleData API response :\n\n"
						+ getStyleDataReqGen.respvalidate.returnresponseasstring());
				log.info("\nPrinting StyleService getStyleData API response :\n\n"
						+ getStyleDataReqGen.respvalidate.returnresponseasstring());

				AssertJUnit.assertTrue("Error while getting style data", getStyleDataReqGen.respvalidate
						.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));

				String response = getStyleDataReqGen.respvalidate.returnresponseasstring();
				List<Integer> ids = JsonPath.read(response, "$.data..styleOptions[*].id");

				for (int i = 0; i < ids.size(); i++) {
					String skuId = JsonPath.read(response, "$.data..styleOptions[" + i + "].skuId").toString();
					String inventoryCount = JsonPath.read(response, "$.data..styleOptions[" + i + "].inventoryCount")
							.toString();
					String available = JsonPath.read(response, "$.data..styleOptions[" + i + "].available").toString();

					if (!inventoryCount.equals(EMPTY_VALUE) && available.equals(TRUE_VALUE)) {
						RequestGenerator fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
						System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"
								+ fetchCartReqGen.respvalidate.returnresponseasstring());
						log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"
								+ fetchCartReqGen.respvalidate.returnresponseasstring());

						if (StringUtils.isBlank(checkoutTestsHelper.getItemIdFromSKUId(fetchCartReqGen, skuId,
								CheckoutDataProviderEnum.CART.name()))) {
							int invCount = Integer.parseInt(inventoryCount);
							int qty = Integer.parseInt(addItemQty);
							int itemQtyToAdd = invCount <= qty ? invCount : qty;

							RequestGenerator addItemToCartReqGen = checkoutTestsHelper.addItemToCart(userName, password,
									skuId, String.valueOf(itemQtyToAdd), addOperation);
							String addItemtoCartResponse = addItemToCartReqGen.respvalidate.returnresponseasstring();
							System.out.println("\nPrinting CheckoutService addItemToCart API response :\n\n"
									+ addItemtoCartResponse);
							log.info("\nPrinting CheckoutService addItemToCart API response :\n\n"
									+ addItemtoCartResponse);

							AssertJUnit.assertEquals("CheckoutService addItemToCart API is not working",
									Integer.parseInt(successRespCode), addItemToCartReqGen.response.getStatus());

							try {
								String jsonschema = new Toolbox()
										.readFileAsString("Data/SchemaSet/JSON/checkoutservice-addtocart-schema.txt");
								List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(
										addItemtoCartResponse, jsonschema);
								AssertJUnit.assertTrue(
										missingNodeList
												+ " nodes are missing in CheckoutService addItemToCart API response",
										CollectionUtils.isEmpty(missingNodeList));
							} catch (Exception e) {
								e.printStackTrace();
							}

							System.out.println(
									"\n" + skuId + " is available and it doesn't exists in cart so added to cart");
							log.info("\n" + skuId + " is available and it doesn't exists in cart so added to cart");
							break;

						} else {
							System.out.println("\n" + skuId + " is not added because this item already exists in cart");
							log.error("\n" + skuId + " is not added because this item already exists in cart");
						}

					} else {
						System.out.println("\n" + skuId + " is not added because this item is currently out of stock");
						log.info("\n" + skuId + " is not added because this item is currently out of stock");
					}
				}

			}

		} else {
			System.out.println("\nError while invoking SearchService getStyleData API");
			log.error("\nError while invoking SearchService getStyleData API");
			AssertJUnit.fail("Error while invoking SearchService getStyleData API");
		}
	}

	@Test(groups = { "SchemaValidation",
			"CartAPIsSchemaValidation" }, dataProvider = "CartServiceDP_operationFetchCartDataProvider_verifyResponseDataNodesUsingSchemaValidations")
	public void CartServiceTests_operationFetchCart_verifyResponseDataNodesUsingSchemaValidations(String userName,
			String password, String successRespCode) {
		RequestGenerator fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
		String fetchCartResponse = fetchCartReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n" + fetchCartResponse);
		log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n" + fetchCartResponse);

		AssertJUnit.assertEquals("CheckoutService operationFetchCart API is not working",
				Integer.parseInt(successRespCode), fetchCartReqGen.response.getStatus());

		try {
			String jsonschema = new Toolbox()
					.readFileAsString("Data/SchemaSet/JSON/checkoutservice-fetchcart-schema.txt");
			List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(fetchCartResponse,
					jsonschema);
			AssertJUnit.assertTrue(
					missingNodeList + " nodes are missing in CheckoutService operationFetchCart API response",
					CollectionUtils.isEmpty(missingNodeList));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test(groups = { "SchemaValidation",
			"CartAPIsSchemaValidation" }, dataProvider = "CartServiceDP_clearCartDataProvider_verifyResponseDataNodesUsingSchemaValidations")
	public void CartServiceTests_clearCart_verifyResponseDataNodesUsingSchemaValidations(String userName,
			String password, String successRespCode) {
		RequestGenerator fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
		String addItemQty = "1";
		System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"
				+ fetchCartReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"
				+ fetchCartReqGen.respvalidate.returnresponseasstring());

		AssertJUnit.assertTrue("Error while invoking CheckoutService operationFetchCart API",
				fetchCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
						.contains(SUCCESS_STATUS_TYPE));

		if (fetchCartReqGen.respvalidate.NodeValue(CartNodes.CART_TOTAL_COUNT.toString(), true).equals(EMPTY_VALUE)) {
			List<Integer> styleIdList = checkoutTestsHelper.performSeachServiceToGetStyleIds(UC_SEARCH_PROD,
					UC_SEARCH_QTY, UC_SEARCH_RET_DOCS, UC_SEARCH_IS_FACET);

			if (!CollectionUtils.isEmpty(styleIdList)) {
				for (Integer styleId : styleIdList) {
					System.out.println("\nPrinting the StyleId : " + styleId);
					log.info("\nPrinting the StyleId : " + styleId);

					RequestGenerator getStyleDataReqGen = checkoutTestsHelper
							.performGetStyleDataOperation(String.valueOf(styleId));
					System.out.println("\nPrinting StyleService getStyleData API response :\n\n"
							+ getStyleDataReqGen.respvalidate.returnresponseasstring());
					log.info("\nPrinting StyleService getStyleData API response :\n\n"
							+ getStyleDataReqGen.respvalidate.returnresponseasstring());

					AssertJUnit.assertTrue("Error while getting style data", getStyleDataReqGen.respvalidate
							.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));

					String response = getStyleDataReqGen.respvalidate.returnresponseasstring();
					List<Integer> ids = JsonPath.read(response, "$.data..styleOptions[*].id");

					for (int i = 0; i < ids.size(); i++) {

						String skuId = JsonPath.read(response, "$.data..styleOptions[" + i + "].skuId").toString();
						String inventoryCount = JsonPath
								.read(response, "$.data..styleOptions[" + i + "].inventoryCount").toString();
						String available = JsonPath.read(response, "$.data..styleOptions[" + i + "].available")
								.toString();

						if (!inventoryCount.equals(EMPTY_VALUE) && available.equals(TRUE_VALUE)) {
							fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
							System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"
									+ fetchCartReqGen.respvalidate.returnresponseasstring());
							log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"
									+ fetchCartReqGen.respvalidate.returnresponseasstring());

							if (StringUtils.isBlank(checkoutTestsHelper.getItemIdFromSKUId(fetchCartReqGen, skuId,
									CheckoutDataProviderEnum.CART.name()))) {
								RequestGenerator addItemToCartReqGen = checkoutTestsHelper.addItemToCart(userName,
										password, skuId, String.valueOf(addItemQty), ADD_OPERATION);
								System.out.println("\nPrinting CheckoutService addItemToCart API response :\n\n"
										+ addItemToCartReqGen.respvalidate.returnresponseasstring());
								log.info("\nPrinting CheckoutService addItemToCart API response :\n\n"
										+ addItemToCartReqGen.respvalidate.returnresponseasstring());

								AssertJUnit.assertTrue("Error while invoking CheckoutService addItemToCart API",
										addItemToCartReqGen.respvalidate
												.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
												.contains(SUCCESS_STATUS_TYPE));

								System.out.println(
										"\n" + skuId + " is available and it doesn't exists in cart so added to cart");
								log.info("\n" + skuId + " is available and it doesn't exists in cart so added to cart");
								break;

							} else {
								System.out.println(
										"\n" + skuId + " is not added because this item already exists in cart");
								log.error("\n" + skuId + " is not added because this item already exists in cart");
							}

						} else {
							System.out.println(
									"\n" + skuId + " is not added because this item is currently out of stock");
							log.info("\n" + skuId + " is not added because this item is currently out of stock");
						}
					}
				}

			} else {
				System.out.println("\nError while invoking SearchService API");
				log.error("\nError while invoking SearchService API");
				AssertJUnit.fail("Error while invoking SearchService API");
			}
		}

		RequestGenerator clearCartReqGen = checkoutTestsHelper.clearCart(userName, password);
		String clearCartResponse = clearCartReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting CheckoutService clearCart API response :\n\n" + clearCartResponse);
		log.info("\nPrinting CheckoutService clearCart API response :\n\n" + clearCartResponse);

		AssertJUnit.assertEquals("CheckoutService clearCart API is not working", Integer.parseInt(successRespCode),
				clearCartReqGen.response.getStatus());

		try {
			String jsonschema = new Toolbox()
					.readFileAsString("Data/SchemaSet/JSON/checkoutservice-clearcart-schema.txt");
			List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(clearCartResponse,
					jsonschema);
			AssertJUnit.assertTrue(missingNodeList + " nodes are missing in CheckoutService clearCart API response",
					CollectionUtils.isEmpty(missingNodeList));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test(groups = { "SchemaValidation",
			"CartAPIsSchemaValidation" }, dataProvider = "CartServiceDP_updateItemInCartDataProvider_verifyResponseDataNodesUsingSchemaValidations")
	public void CartServiceTests_updateItemInCart_verifyResponseDataNodesUsingSchemaValidations(String userName,
			String password, String itemQtyToUpdate, String updateOpn, String successRespCode) {
		RequestGenerator fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
		System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"
				+ fetchCartReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"
				+ fetchCartReqGen.respvalidate.returnresponseasstring());

		AssertJUnit.assertTrue("Error while invoking CheckoutService operationFetchCart API",
				fetchCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
						.contains(SUCCESS_STATUS_TYPE));

		if (fetchCartReqGen.respvalidate.NodeValue(CartNodes.CART_TOTAL_COUNT.toString(), true).equals(EMPTY_VALUE)) {
			List<Integer> styleIdList = checkoutTestsHelper.performSeachServiceToGetStyleIds(UC_SEARCH_PROD,
					UC_SEARCH_QTY, UC_SEARCH_RET_DOCS, UC_SEARCH_IS_FACET);

			if (!CollectionUtils.isEmpty(styleIdList)) {
				for (Integer styleId : styleIdList) {
					System.out.println("\nPrinting the StyleId : " + styleId);
					log.info("\nPrinting the StyleId : " + styleId);

					RequestGenerator getStyleDataReqGen = checkoutTestsHelper
							.performGetStyleDataOperation(String.valueOf(styleId));
					System.out.println("\nPrinting StyleService getStyleData API response :\n\n"
							+ getStyleDataReqGen.respvalidate.returnresponseasstring());
					log.info("\nPrinting StyleService getStyleData API response :\n\n"
							+ getStyleDataReqGen.respvalidate.returnresponseasstring());

					AssertJUnit.assertTrue("Error while getting style data", getStyleDataReqGen.respvalidate
							.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));

					String response = getStyleDataReqGen.respvalidate.returnresponseasstring();
					List<Integer> ids = JsonPath.read(response, "$.data..styleOptions[*].id");

					for (int i = 0; i < ids.size(); i++) {

						String skuId = JsonPath.read(response, "$.data..styleOptions[" + i + "].skuId").toString();
						String inventoryCount = JsonPath
								.read(response, "$.data..styleOptions[" + i + "].inventoryCount").toString();
						String available = JsonPath.read(response, "$.data..styleOptions[" + i + "].available")
								.toString();

						if (!inventoryCount.equals(EMPTY_VALUE) && available.equals(TRUE_VALUE)) {
							fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
							System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"
									+ fetchCartReqGen.respvalidate.returnresponseasstring());
							log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"
									+ fetchCartReqGen.respvalidate.returnresponseasstring());

							if (StringUtils.isBlank(checkoutTestsHelper.getItemIdFromSKUId(fetchCartReqGen, skuId,
									CheckoutDataProviderEnum.CART.name()))) {
								RequestGenerator addItemToCartReqGen = checkoutTestsHelper.addItemToCart(userName,
										password, skuId, inventoryCount, ADD_OPERATION);
								System.out.println("\nPrinting CheckoutService addItemToCart API response :\n\n"
										+ addItemToCartReqGen.respvalidate.returnresponseasstring());
								log.info("\nPrinting CheckoutService addItemToCart API response :\n\n"
										+ addItemToCartReqGen.respvalidate.returnresponseasstring());

								AssertJUnit.assertTrue("Error while invoking CheckoutService addItemToCart API",
										addItemToCartReqGen.respvalidate
												.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
												.contains(SUCCESS_STATUS_TYPE));

								System.out.println(
										"\n" + skuId + " is available and it doesn't exists in cart so added to cart");
								log.info("\n" + skuId + " is available and it doesn't exists in cart so added to cart");
								break;

							} else {
								System.out.println(
										"\n" + skuId + " is not added because this item already exists in cart");
								log.error("\n" + skuId + " is not added because this item already exists in cart");
							}

						} else {
							System.out.println(
									"\n" + skuId + " is not added because this item is currently out of stock");
							log.info("\n" + skuId + " is not added because this item is currently out of stock");
						}
					}
				}

			} else {
				System.out.println("\nError while invoking SearchService API");
				log.error("\nError while invoking SearchService API");
				AssertJUnit.fail("Error while invoking SearchService API");
			}

			fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
			System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"
					+ fetchCartReqGen.respvalidate.returnresponseasstring());
			log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"
					+ fetchCartReqGen.respvalidate.returnresponseasstring());

			AssertJUnit.assertTrue("Error while invoking CheckoutService operationFetchCart API",
					fetchCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
							.contains(SUCCESS_STATUS_TYPE));
		}
		String skuId = fetchCartReqGen.respvalidate.NodeValue(CartNodes.CART_ITEM_ENTRIES_SELSIZE_SKUID.toString(),
				false);
		String itemId = checkoutTestsHelper.getItemIdFromSKUId(fetchCartReqGen, skuId,
				CheckoutDataProviderEnum.CART.name());

		System.out.println("\nPrinting skuId : " + skuId + " & itemId : " + itemId + " & quantity : " + itemQtyToUpdate
				+ " is going for updation\n");
		log.info("\nPrinting skuId : " + skuId + " & itemId : " + itemId + " & quantity : " + itemQtyToUpdate
				+ " is going for updation\n");

		RequestGenerator updateItemInCartReqGen = checkoutTestsHelper.updateItemInCart(userName, password, itemId,
				skuId, String.valueOf(itemQtyToUpdate), updateOpn);
		String updateItemInCartResponse = updateItemInCartReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting CheckoutService updateItemInCart API response :\n\n" + updateItemInCartResponse);
		log.info("\nPrinting CheckoutService updateItemInCart API response :\n\n" + updateItemInCartResponse);

		AssertJUnit.assertEquals("CheckoutService updateItemInCart API is not working",
				Integer.parseInt(successRespCode), updateItemInCartReqGen.response.getStatus());

		try {
			String jsonschema = new Toolbox()
					.readFileAsString("Data/SchemaSet/JSON/checkoutservice-updateitemincart-schema.txt");
			List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(updateItemInCartResponse,
					jsonschema);
			AssertJUnit.assertTrue(
					missingNodeList + " nodes are missing in CheckoutService updateItemInCart API response",
					CollectionUtils.isEmpty(missingNodeList));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test(groups = { "SchemaValidation",
			"CartAPIsSchemaValidation" }, dataProvider = "CartServiceDP_removeItemFromCartDataProvider_verifyResponseDataNodesUsingSchemaValidations")
	public void CartServiceTests_removeItemFromCart_verifyResponseDataNodesUsingSchemaValidations(String userName,
			String password, String deleteOpn, String successRespCode) {
		RequestGenerator fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
		System.out.println("\nPrinting CheckoutService  operationFetchCart API response :\n\n"
				+ fetchCartReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"
				+ fetchCartReqGen.respvalidate.returnresponseasstring());

		AssertJUnit.assertTrue("Error while invoking CheckoutService operationFetchCart API",
				fetchCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
						.contains(SUCCESS_STATUS_TYPE));

		if (fetchCartReqGen.respvalidate.NodeValue(CartNodes.CART_TOTAL_COUNT.toString(), true).equals(EMPTY_VALUE)) {
			List<Integer> styleIdList = checkoutTestsHelper.performSeachServiceToGetStyleIds(RC_SEARCH_PROD,
					RC_SEARCH_QTY, RC_SEARCH_RET_DOCS, RC_SEARCH_IS_FACET);

			if (!CollectionUtils.isEmpty(styleIdList)) {
				for (Integer styleId : styleIdList) {
					System.out.println("\nPrinting the StyleId : " + styleId);
					log.info("\nPrinting the StyleId : " + styleId);

					RequestGenerator getStyleDataReqGen = checkoutTestsHelper
							.performGetStyleDataOperation(String.valueOf(styleId));
					System.out.println("\nPrinting StyleService getStyleData API response :\n\n"
							+ getStyleDataReqGen.respvalidate.returnresponseasstring());
					log.info("\nPrinting StyleService getStyleData API response :\n\n"
							+ getStyleDataReqGen.respvalidate.returnresponseasstring());

					AssertJUnit.assertTrue("Error while getting style data", getStyleDataReqGen.respvalidate
							.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));

					String response = getStyleDataReqGen.respvalidate.returnresponseasstring();
					List<Integer> ids = JsonPath.read(response, "$.data..styleOptions[*].id");

					for (int i = 0; i < ids.size(); i++) {
						String skuId = JsonPath.read(response, "$.data..styleOptions[" + i + "].skuId").toString();
						String inventoryCount = JsonPath
								.read(response, "$.data..styleOptions[" + i + "].inventoryCount").toString();
						String available = JsonPath.read(response, "$.data..styleOptions[" + i + "].available")
								.toString();

						if (!inventoryCount.equals(EMPTY_VALUE) && available.equals(TRUE_VALUE)) {
							fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
							System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"
									+ fetchCartReqGen.respvalidate.returnresponseasstring());
							log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"
									+ fetchCartReqGen.respvalidate.returnresponseasstring());

							if (StringUtils.isBlank(checkoutTestsHelper.getItemIdFromSKUId(fetchCartReqGen, skuId,
									CheckoutDataProviderEnum.CART.name()))) {
								RequestGenerator addItemToCartReqGen = checkoutTestsHelper.addItemToCart(userName,
										password, skuId, inventoryCount, ADD_OPERATION);
								System.out.println("\nPrinting CheckoutService addItemToCart API response :\n\n"
										+ addItemToCartReqGen.respvalidate.returnresponseasstring());
								log.info("\nPrinting CheckoutService addItemToCart API response :\n\n"
										+ addItemToCartReqGen.respvalidate.returnresponseasstring());

								AssertJUnit.assertTrue("Error while invoking CheckoutService addItemToCart API",
										addItemToCartReqGen.respvalidate
												.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
												.contains(SUCCESS_STATUS_TYPE));

								System.out.println(
										"\n" + skuId + " is available and it doesn't exists in cart so added to cart");
								log.info("\n" + skuId + " is available and it doesn't exists in cart so added to cart");
								break;

							} else {
								System.out.println(
										"\n" + skuId + " is not added because this item already exists in cart");
								log.error("\n" + skuId + " is not added because this item already exists in cart");
							}

						} else {
							System.out.println(
									"\n" + skuId + " is not added because this item is currently out of stock");
							log.info("\n" + skuId + " is not added because this item is currently out of stock");
						}
					}
				}

			} else {
				System.out.println("\nError while invoking SearchService getStyleData API");
				log.error("\nError while invoking SearchService getStyleData API");
				AssertJUnit.fail("Error while invoking SearchService getStyleData API");
			}

			fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
			System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"
					+ fetchCartReqGen.respvalidate.returnresponseasstring());
			log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"
					+ fetchCartReqGen.respvalidate.returnresponseasstring());

			AssertJUnit.assertTrue("Error while invoking CheckoutService operationFetchCart API",
					fetchCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
							.contains(SUCCESS_STATUS_TYPE));

		}
		String skuId = fetchCartReqGen.respvalidate.NodeValue(CartNodes.CART_ITEM_ENTRIES_SELSIZE_SKUID.toString(),
				false);
		String itemId = checkoutTestsHelper.getItemIdFromSKUId(fetchCartReqGen, skuId,
				CheckoutDataProviderEnum.CART.name());

		System.out
				.println("\nPrinting skuId : " + skuId + " & itemId : " + itemId + " is going to removeItemFromCart\n");
		log.info("\nPrinting skuId : " + skuId + " & itemId : " + itemId + " is going for removeItemFromCart API\n");

		RequestGenerator removeItemFromCartReqGen = checkoutTestsHelper.removeItemFromCart(userName, password, itemId,
				deleteOpn);
		String removeItemFromCartResponse = removeItemFromCartReqGen.respvalidate.returnresponseasstring();
		System.out.println(
				"\nPrinting CheckoutService removeItemFromCart API response :\n\n" + removeItemFromCartResponse);
		log.info("\nPrinting CheckoutService removeItemFromCart API response :\n\n" + removeItemFromCartResponse);

		AssertJUnit.assertEquals("CheckoutService removeItemFromCart API is not working",
				Integer.parseInt(successRespCode), removeItemFromCartReqGen.response.getStatus());

		try {
			String jsonschema = new Toolbox()
					.readFileAsString("Data/SchemaSet/JSON/checkoutservice-removeitemfromcart-schema.txt");
			List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(removeItemFromCartResponse,
					jsonschema);
			AssertJUnit.assertTrue(
					missingNodeList + " nodes are missing in CheckoutService removeItemFromCart API response",
					CollectionUtils.isEmpty(missingNodeList));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test(groups = { "SchemaValidation",
			"CartAPIsSchemaValidation" }, dataProvider = "CartServiceDP_addGiftWrapAndMessageDataProvider_verifyResponseDataNodesUsingSchemaValidations")
	public void CartServiceTests_addGiftWrapAndMessage_verifyResponseDataNodesUsingSchemaValidations(String userName,
			String password, String senderName, String giftMsg, String recipientName, String respCode) {
		RequestGenerator fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
		System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"
				+ fetchCartReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"
				+ fetchCartReqGen.respvalidate.returnresponseasstring());

		AssertJUnit.assertTrue("Error while invoking CheckoutService operationFetchCart API",
				fetchCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
						.contains(SUCCESS_STATUS_TYPE));

		if (fetchCartReqGen.respvalidate.NodeValue(CartNodes.CART_TOTAL_COUNT.toString(), true).equals(EMPTY_VALUE)) {
			List<Integer> styleIdList = checkoutTestsHelper.performSeachServiceToGetStyleIds(AC_SEARCH_PROD,
					AC_SEARCH_QTY, AC_SEARCH_RET_DOCS, AC_SEARCH_IS_FACET);

			if (!CollectionUtils.isEmpty(styleIdList)) {
				for (Integer styleId : styleIdList) {
					System.out.println("\nPrinting the StyleId : " + styleId);
					log.info("\nPrinting the StyleId : " + styleId);

					RequestGenerator getStyleDataReqGen = checkoutTestsHelper
							.performGetStyleDataOperation(String.valueOf(styleId));
					System.out.println("\nPrinting StyleService getStyleData API response :\n\n"
							+ getStyleDataReqGen.respvalidate.returnresponseasstring());
					log.info("\nPrinting StyleService getStyleData API response :\n\n"
							+ getStyleDataReqGen.respvalidate.returnresponseasstring());

					AssertJUnit.assertTrue("Error while getting style data", getStyleDataReqGen.respvalidate
							.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));

					String response = getStyleDataReqGen.respvalidate.returnresponseasstring();
					List<Integer> ids = JsonPath.read(response, "$.data..styleOptions[*].id");

					for (int i = 0; i < ids.size(); i++) {
						String skuId = JsonPath.read(response, "$.data..styleOptions[" + i + "].skuId").toString();
						String inventoryCount = JsonPath
								.read(response, "$.data..styleOptions[" + i + "].inventoryCount").toString();
						String available = JsonPath.read(response, "$.data..styleOptions[" + i + "].available")
								.toString();

						if (!inventoryCount.equals(EMPTY_VALUE) && available.equals(TRUE_VALUE)) {
							fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
							System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"
									+ fetchCartReqGen.respvalidate.returnresponseasstring());
							log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"
									+ fetchCartReqGen.respvalidate.returnresponseasstring());

							if (StringUtils.isBlank(checkoutTestsHelper.getItemIdFromSKUId(fetchCartReqGen, skuId,
									CheckoutDataProviderEnum.CART.name()))) {
								RequestGenerator addItemToCartReqGen = checkoutTestsHelper.addItemToCart(userName,
										password, skuId, inventoryCount, ADD_OPERATION);
								System.out.println("\nPrinting CheckoutService addItemToCart API response :\n\n"
										+ addItemToCartReqGen.respvalidate.returnresponseasstring());
								log.info("\nPrinting CheckoutService addItemToCart API response :\n\n"
										+ addItemToCartReqGen.respvalidate.returnresponseasstring());

								AssertJUnit.assertTrue("Error while invoking CheckoutService addItemToCart API",
										addItemToCartReqGen.respvalidate
												.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
												.contains(SUCCESS_STATUS_TYPE));

								System.out.println(
										"\n" + skuId + " is available and it doesn't exists in cart so added to cart");
								log.info("\n" + skuId + " is available and it doesn't exists in cart so added to cart");
								break;

							} else {
								System.out.println(
										"\n" + skuId + " is not added because this item already exists in cart");
								log.error("\n" + skuId + " is not added because this item already exists in cart");
							}

						} else {
							System.out.println(
									"\n" + skuId + " is not added because this item is currently out of stock");
							log.info("\n" + skuId + " is not added because this item is currently out of stock");
						}
					}
				}

			} else {
				System.out.println("\nError while invoking SearchService getStyleData API");
				log.error("\nError while invoking SearchService getStyleData API");
				AssertJUnit.fail("Error while invoking SearchService getStyleData API");
			}

			fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
			System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"
					+ fetchCartReqGen.respvalidate.returnresponseasstring());
			log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"
					+ fetchCartReqGen.respvalidate.returnresponseasstring());

			AssertJUnit.assertTrue("Error while invoking CheckoutService operationFetchCart API",
					fetchCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
							.contains(SUCCESS_STATUS_TYPE));

		} else if (fetchCartReqGen.respvalidate.NodeValue(CartNodes.CART_IS_GIFT_ORDER.toString(), true)
				.equals(TRUE_VALUE)) {
			RequestGenerator removeGiftWrapAndMsgReqGen = checkoutTestsHelper.removeGiftWrapAndMessage(userName,
					password);
			System.out.println("\nPrinting CheckoutService removeGiftWrapAndMessage API response :\n\n"
					+ removeGiftWrapAndMsgReqGen.respvalidate.returnresponseasstring());
			log.info("\nPrinting CheckoutService removeGiftWrapAndMessage API response :\n\n"
					+ removeGiftWrapAndMsgReqGen.respvalidate.returnresponseasstring());

			AssertJUnit.assertTrue("Error while invoking CheckoutService removeGiftWrapAndMessage API",
					removeGiftWrapAndMsgReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
							.contains(SUCCESS_STATUS_TYPE));
		}

		RequestGenerator addGiftWrapAndMessageReqGen = checkoutTestsHelper.addGiftWrapAndMessage(userName, password,
				senderName, giftMsg, recipientName);
		String addGiftWrapAndMessageResponse = addGiftWrapAndMessageReqGen.respvalidate.returnresponseasstring();
		System.out.println(
				"\nPrinting CheckoutService addGiftWrapAndMessage API response :\n\n" + addGiftWrapAndMessageResponse);
		log.info("\nPrinting CheckoutService addGiftWrapAndMessage API response :\n\n" + addGiftWrapAndMessageResponse);

		AssertJUnit.assertEquals("CheckoutService addGiftWrapAndMessage API is not working", Integer.parseInt(respCode),
				addGiftWrapAndMessageReqGen.response.getStatus());

		try {
			String jsonschema = new Toolbox()
					.readFileAsString("Data/SchemaSet/JSON/checkoutservice-addgiftwrapandmessage-schema.txt");
			List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(
					addGiftWrapAndMessageResponse, jsonschema);
			AssertJUnit.assertTrue(
					missingNodeList + " nodes are missing in CheckoutService addGiftWrapAndMessage API response",
					CollectionUtils.isEmpty(missingNodeList));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test(groups = { "SchemaValidation",
			"CartAPIsSchemaValidation" }, dataProvider = "CartServiceDP_removeGiftWrapAndMessageDataProvider_verifyResponseDataNodesUsingSchemaValidations")
	public void CartServiceTests_removeGiftWrapAndMessage_verifyResponseDataNodesUsingSchemaValidations(String userName,
			String password, String successRespCode) {
		RequestGenerator fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
		System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"
				+ fetchCartReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"
				+ fetchCartReqGen.respvalidate.returnresponseasstring());

		AssertJUnit.assertTrue("Error while invoking CheckoutService operationFetchCart API",
				fetchCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
						.contains(SUCCESS_STATUS_TYPE));

		if (fetchCartReqGen.respvalidate.NodeValue(CartNodes.CART_TOTAL_COUNT.toString(), true).equals(EMPTY_VALUE)) {
			List<Integer> styleIdList = checkoutTestsHelper.performSeachServiceToGetStyleIds(RGW_SEARCH_PROD,
					RGW_SEARCH_QTY, RGW_SEARCH_RET_DOCS, RGW_SEARCH_IS_FACET);

			if (!CollectionUtils.isEmpty(styleIdList)) {
				for (Integer styleId : styleIdList) {
					System.out.println("\nPrinting the StyleId : " + styleId);
					log.info("\nPrinting the StyleId : " + styleId);

					RequestGenerator getStyleDataReqGen = checkoutTestsHelper
							.performGetStyleDataOperation(String.valueOf(styleId));
					System.out.println("\nPrinting StyleService getStyleData API response :\n\n"
							+ getStyleDataReqGen.respvalidate.returnresponseasstring());
					log.info("\nPrinting StyleService getStyleData API response :\n\n"
							+ getStyleDataReqGen.respvalidate.returnresponseasstring());

					AssertJUnit.assertTrue("Error while getting style data", getStyleDataReqGen.respvalidate
							.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));

					String response = getStyleDataReqGen.respvalidate.returnresponseasstring();
					List<Integer> ids = JsonPath.read(response, "$.data..styleOptions[*].id");

					for (int i = 0; i < ids.size(); i++) {

						String skuId = JsonPath.read(response, "$.data..styleOptions[" + i + "].skuId").toString();
						String inventoryCount = JsonPath
								.read(response, "$.data..styleOptions[" + i + "].inventoryCount").toString();
						String available = JsonPath.read(response, "$.data..styleOptions[" + i + "].available")
								.toString();

						if (!inventoryCount.equals(EMPTY_VALUE) && available.equals(TRUE_VALUE)) {
							fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
							System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"
									+ fetchCartReqGen.respvalidate.returnresponseasstring());
							log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"
									+ fetchCartReqGen.respvalidate.returnresponseasstring());

							if (StringUtils.isBlank(checkoutTestsHelper.getItemIdFromSKUId(fetchCartReqGen, skuId,
									CheckoutDataProviderEnum.CART.name()))) {
								RequestGenerator addItemToCartReqGen = checkoutTestsHelper.addItemToCart(userName,
										password, skuId, inventoryCount, ADD_OPERATION);
								System.out.println("\nPrinting CheckoutService addItemToCart API response :\n\n"
										+ addItemToCartReqGen.respvalidate.returnresponseasstring());
								log.info("\nPrinting CheckoutService addItemToCart API response :\n\n"
										+ addItemToCartReqGen.respvalidate.returnresponseasstring());

								AssertJUnit.assertTrue("Error while invoking CheckoutService addItemToCart API",
										addItemToCartReqGen.respvalidate
												.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
												.contains(SUCCESS_STATUS_TYPE));

								System.out.println(
										"\n" + skuId + " is available and it doesn't exists in cart so added to cart");
								log.info("\n" + skuId + " is available and it doesn't exists in cart so added to cart");
								break;

							} else {
								System.out.println(
										"\n" + skuId + " is not added because this item already exists in cart");
								log.error("\n" + skuId + " is not added because this item already exists in cart");
							}

						} else {
							System.out.println(
									"\n" + skuId + " is not added because this item is currently out of stock");
							log.info("\n" + skuId + " is not added because this item is currently out of stock");
						}
					}

				}

			} else {
				System.out.println("\nError while invoking SearchService getStyleData API");
				log.error("\nError while invoking SearchService getStyleData API");
				AssertJUnit.fail("Error while invoking SearchService getStyleData API");
			}

			fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
			System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"
					+ fetchCartReqGen.respvalidate.returnresponseasstring());
			log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"
					+ fetchCartReqGen.respvalidate.returnresponseasstring());

			AssertJUnit.assertTrue("Error while invoking CheckoutService operationFetchCart API",
					fetchCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
							.contains(SUCCESS_STATUS_TYPE));

		} else if (fetchCartReqGen.respvalidate.NodeValue(CartNodes.CART_IS_GIFT_ORDER.toString(), true)
				.equals(FALSE_VALUE)) {
			RequestGenerator addGiftWrapAndMessageReqGen = checkoutTestsHelper.addGiftWrapAndMessage(userName, password,
					RGW_GIFT_SENDER, RGW_GIFT_MSG, RGW_GIFT_RECEPIENT);
			System.out.println("\nPrinting CheckoutService addGiftWrapAndMessage API response :\n\n"
					+ addGiftWrapAndMessageReqGen.respvalidate.returnresponseasstring());
			log.info("\nPrinting CheckoutService addGiftWrapAndMessage API response :\n\n"
					+ addGiftWrapAndMessageReqGen.respvalidate.returnresponseasstring());

			AssertJUnit.assertTrue("Error while invoking CheckoutService addGiftWrapAndMessage API",
					addGiftWrapAndMessageReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
							.contains(SUCCESS_STATUS_TYPE));
		}

		RequestGenerator removeGiftWrapAndMessageReqGen = checkoutTestsHelper.removeGiftWrapAndMessage(userName,
				password);
		String removeGiftWrapAndMessageResponse = removeGiftWrapAndMessageReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting CheckoutService removeGiftWrapAndMessage API response :\n\n"
				+ removeGiftWrapAndMessageResponse);
		log.info("\nPrinting CheckoutService removeGiftWrapAndMessage API response :\n\n"
				+ removeGiftWrapAndMessageResponse);

		AssertJUnit.assertEquals("CheckoutService removeGiftWrapAndMessage API is not working",
				Integer.parseInt(successRespCode), removeGiftWrapAndMessageReqGen.response.getStatus());

		try {
			String jsonschema = new Toolbox()
					.readFileAsString("Data/SchemaSet/JSON/checkoutservice-removegiftwrapandmessage-schema.txt");
			List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(
					removeGiftWrapAndMessageResponse, jsonschema);
			AssertJUnit.assertTrue(
					missingNodeList + " nodes are missing in CheckoutService removeGiftWrapAndMessage API response",
					CollectionUtils.isEmpty(missingNodeList));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test(groups = { "SchemaValidation",
			"CartAPIsSchemaValidation" }, dataProvider = "CartServiceDP_changeFreeGiftDataProvider_verifyResponseDataNodesUsingSchemaValidations")
	public void CartServiceTests_changeFreeGift_verifyResponseDataNodesUsingSchemaValidations(String userName,
			String password, String successRespCode) {
		RequestGenerator fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
		System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"
				+ fetchCartReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"
				+ fetchCartReqGen.respvalidate.returnresponseasstring());

		AssertJUnit.assertTrue("Error while invoking CheckoutService operationFetchCart API",
				fetchCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
						.contains(SUCCESS_STATUS_TYPE));

		if (fetchCartReqGen.respvalidate.NodeValue(CartNodes.CART_TOTAL_COUNT.toString(), true).equals(EMPTY_VALUE)) {
			List<Integer> styleIdList = checkoutTestsHelper.performSeachServiceToGetStyleIds(CFG_SEARCH_PROD,
					CFG_SEARCH_QTY, CFG_SEARCH_RET_DOCS, CFG_SEARCH_IS_FACET);

			if (!CollectionUtils.isEmpty(styleIdList)) {
				for (Integer styleId : styleIdList) {
					System.out.println("\nPrinting the StyleId : " + styleId);
					log.info("\nPrinting the StyleId : " + styleId);

					RequestGenerator getStyleDataReqGen = checkoutTestsHelper
							.performGetStyleDataOperation(String.valueOf(styleId));
					System.out.println("\nPrinting StyleService API response :\n\n"
							+ getStyleDataReqGen.respvalidate.returnresponseasstring());
					log.info("\nPrinting StyleService API response :\n\n"
							+ getStyleDataReqGen.respvalidate.returnresponseasstring());

					AssertJUnit.assertTrue("Error while getting style data", getStyleDataReqGen.respvalidate
							.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));

					String response = getStyleDataReqGen.respvalidate.returnresponseasstring();
					List<Integer> ids = JsonPath.read(response, "$.data..styleOptions[*].id");

					for (int i = 0; i < ids.size(); i++) {
						String skuId = JsonPath.read(response, "$.data..styleOptions[" + i + "].skuId").toString();
						String inventoryCount = JsonPath
								.read(response, "$.data..styleOptions[" + i + "].inventoryCount").toString();
						String available = JsonPath.read(response, "$.data..styleOptions[" + i + "].available")
								.toString();

						if (!inventoryCount.equals(EMPTY_VALUE) && available.equals(TRUE_VALUE)) {
							fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
							System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"
									+ fetchCartReqGen.respvalidate.returnresponseasstring());
							log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"
									+ fetchCartReqGen.respvalidate.returnresponseasstring());

							if (StringUtils.isBlank(checkoutTestsHelper.getItemIdFromSKUId(fetchCartReqGen, skuId,
									CheckoutDataProviderEnum.CART.name()))) {
								RequestGenerator addItemToCartReqGen = checkoutTestsHelper.addItemToCart(userName,
										password, skuId, inventoryCount, ADD_OPERATION);
								System.out.println("\nPrinting CheckoutService addItemToCart API response :\n\n"
										+ addItemToCartReqGen.respvalidate.returnresponseasstring());
								log.info("\nPrinting CheckoutService addItemToCart API response :\n\n"
										+ addItemToCartReqGen.respvalidate.returnresponseasstring());

								AssertJUnit.assertTrue("Error while invoking CheckoutService addItemToCart API",
										addItemToCartReqGen.respvalidate
												.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
												.contains(SUCCESS_STATUS_TYPE));

								System.out.println(
										"\n" + skuId + " is available and it doesn't exists in cart so added to cart");
								log.info("\n" + skuId + " is available and it doesn't exists in cart so added to cart");
								break;

							} else {
								System.out.println(
										"\n" + skuId + " is not added because this item already exists in cart");
								log.error("\n" + skuId + " is not added because this item already exists in cart");
							}

						} else {
							System.out.println(
									"\n" + skuId + " is not added because this item is currently out of stock");
							log.info("\n" + skuId + " is not added because this item is currently out of stock");
						}
					}

				}

			} else {
				System.out.println("\nError while invoking SearchService API");
				log.error("\nError while invoking SearchService API");
				AssertJUnit.fail("Error while invoking SearchService API");
			}

			fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
			System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"
					+ fetchCartReqGen.respvalidate.returnresponseasstring());
			log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"
					+ fetchCartReqGen.respvalidate.returnresponseasstring());

			AssertJUnit.assertTrue("Error while invoking CheckoutService operationFetchCart API",
					fetchCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
							.contains(SUCCESS_STATUS_TYPE));
		}
		String skuId = fetchCartReqGen.respvalidate.NodeValue(CartNodes.CART_ITEM_ENTRIES_AVAIL_SIZES_SKUID.toString(),
				false);
		String itemId = checkoutTestsHelper.getItemIdFromSKUId(fetchCartReqGen, skuId,
				CheckoutDataProviderEnum.CART.name());

		System.out.println("\nPrinting skuId : " + skuId + " & itemId : " + itemId + " is going for changeFreeGift\n");
		log.info("\nPrinting skuId : " + skuId + " & itemId : " + itemId + " is going for changeFreeGift\n");

		RequestGenerator changeFreeGiftReqGen = checkoutTestsHelper.changeFreeGift(userName, password, itemId, skuId);
		String changeFreeGiftResponse = changeFreeGiftReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting CheckoutService changeFreeGift API response :\n\n" + changeFreeGiftResponse);
		log.info("\nPrinting CheckoutService changeFreeGift API response :\n\n" + changeFreeGiftResponse);

		AssertJUnit.assertEquals("CheckoutService changeFreeGift API is not working", Integer.parseInt(successRespCode),
				changeFreeGiftReqGen.response.getStatus());

		try {
			String jsonschema = new Toolbox()
					.readFileAsString("Data/SchemaSet/JSON/checkoutservice-changefreegift-schema.txt");
			List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(changeFreeGiftResponse,
					jsonschema);
			AssertJUnit.assertTrue(
					missingNodeList + " nodes are missing in CheckoutService changeFreeGift API response",
					CollectionUtils.isEmpty(missingNodeList));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test(groups = { "SchemaValidation",
			"CartAPIsSchemaValidation" }, dataProvider = "CartServiceDP_moveToWishListDataProvider_verifyResponseDataNodesUsingSchemaValidations")
	public void CartServiceTests_moveItemToWishList_verifyResponseDataNodesUsingSchemaValidations(String userName,
			String password, String successRespCode) {
		RequestGenerator fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
		System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"
				+ fetchCartReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"
				+ fetchCartReqGen.respvalidate.returnresponseasstring());

		AssertJUnit.assertTrue("Error while invoking operationFetchCart API", fetchCartReqGen.respvalidate
				.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));

		if (fetchCartReqGen.respvalidate.NodeValue(CartNodes.CART_TOTAL_COUNT.toString(), true).equals(EMPTY_VALUE)) {
			List<Integer> styleIdList = checkoutTestsHelper.performSeachServiceToGetStyleIds(MWL_SEARCH_PROD,
					MWL_SEARCH_QTY, MWL_SEARCH_RET_DOCS, MWL_SEARCH_IS_FACET);

			if (!CollectionUtils.isEmpty(styleIdList)) {
				for (Integer styleId : styleIdList) {
					System.out.println("\nPrinting the StyleId : " + styleId);
					log.info("\nPrinting the StyleId : " + styleId);

					RequestGenerator getStyleDataReqGen = checkoutTestsHelper
							.performGetStyleDataOperation(String.valueOf(styleId));
					System.out.println("\nPrinting StyleService getStyleData API response :\n\n"
							+ getStyleDataReqGen.respvalidate.returnresponseasstring());
					log.info("\nPrinting StyleService getStyleDataAPI response :\n\n"
							+ getStyleDataReqGen.respvalidate.returnresponseasstring());

					AssertJUnit.assertTrue("Error while getting style data", getStyleDataReqGen.respvalidate
							.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));

					String response = getStyleDataReqGen.respvalidate.returnresponseasstring();
					List<Integer> ids = JsonPath.read(response, "$.data..styleOptions[*].id");

					for (int i = 0; i < ids.size(); i++) {
						String skuId = JsonPath.read(response, "$.data..styleOptions[" + i + "].skuId").toString();
						String inventoryCount = JsonPath
								.read(response, "$.data..styleOptions[" + i + "].inventoryCount").toString();
						String available = JsonPath.read(response, "$.data..styleOptions[" + i + "].available")
								.toString();

						if (!inventoryCount.equals(EMPTY_VALUE) && available.equals(TRUE_VALUE)) {
							fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
							System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"
									+ fetchCartReqGen.respvalidate.returnresponseasstring());
							log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"
									+ fetchCartReqGen.respvalidate.returnresponseasstring());

							if (StringUtils.isBlank(checkoutTestsHelper.getItemIdFromSKUId(fetchCartReqGen, skuId,
									CheckoutDataProviderEnum.CART.name()))) {
								RequestGenerator addItemToCartReqGen = checkoutTestsHelper.addItemToCart(userName,
										password, skuId, inventoryCount, ADD_OPERATION);
								System.out.println("\nPrinting addItemToCart API response :\n\n"
										+ addItemToCartReqGen.respvalidate.returnresponseasstring());
								log.info("\nPrinting addItemToCart API response :\n\n"
										+ addItemToCartReqGen.respvalidate.returnresponseasstring());

								AssertJUnit.assertTrue("Error while invoking CheckoutService addItemToCart API",
										addItemToCartReqGen.respvalidate
												.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
												.contains(SUCCESS_STATUS_TYPE));

								System.out.println(
										"\n" + skuId + " is available and it doesn't exists in cart so added to cart");
								log.info("\n" + skuId + " is available and it doesn't exists in cart so added to cart");
								break;

							} else {
								System.out.println(
										"\n" + skuId + " is not added because this item already exists in cart");
								log.error("\n" + skuId + " is not added because this item already exists in cart");
							}

						} else {
							System.out.println(
									"\n" + skuId + " is not added because this item is currently out of stock");
							log.info("\n" + skuId + " is not added because this item is currently out of stock");
						}
					}
				}

			} else {
				System.out.println("\nError while invoking SearchService getStyleData API");
				log.error("\nError while invoking SearchService getStyleData API");
				AssertJUnit.fail("Error while invoking SearchService getStyleData API");
			}

			fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
			System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"
					+ fetchCartReqGen.respvalidate.returnresponseasstring());
			log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"
					+ fetchCartReqGen.respvalidate.returnresponseasstring());

			AssertJUnit.assertTrue("Error while invoking CheckoutService operationFetchCart API",
					fetchCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
							.contains(SUCCESS_STATUS_TYPE));
		}

		String skuId = fetchCartReqGen.respvalidate.NodeValue(CartNodes.CART_ITEM_ENTRIES_SELSIZE_SKUID.toString(),
				false);
		String itemId = checkoutTestsHelper.getItemIdFromSKUId(fetchCartReqGen, skuId,
				CheckoutDataProviderEnum.CART.name());

		RequestGenerator fetchWishlistReqGen = checkoutTestsHelper.operationFetchWishList(userName, password);
		System.out.println("\nPrinting CheckoutService operationFetchWishlist API response :\n\n"
				+ fetchWishlistReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService operationFetchWishlist API response :\n\n"
				+ fetchWishlistReqGen.respvalidate.returnresponseasstring());

		AssertJUnit.assertTrue("Error while invoking CheckoutService operationFetchWishlist API",
				fetchWishlistReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
						.contains(SUCCESS_STATUS_TYPE));

		if (StringUtils.isBlank(checkoutTestsHelper.getItemIdFromSKUId(fetchCartReqGen, skuId,
				CheckoutDataProviderEnum.WISHLIST.name()))) {
			System.out.println(
					"\nPrinting skuId : " + skuId + " & itemId : " + itemId + " is moving to wishlist from cart\n");
			log.info("\nPrinting skuId : " + skuId + " & itemId : " + itemId
					+ " is going is moving to wishlist from cart\n");

			RequestGenerator moveItemToWishListReqGen = checkoutTestsHelper.moveItemToWishList(userName, password,
					itemId);
			String moveToWishlistResponse = moveItemToWishListReqGen.respvalidate.returnresponseasstring();
			System.out.println(
					"\nPrinting CheckoutService moveItemToWishList API response :\n\n" + moveToWishlistResponse);
			log.info("\nPrinting CheckoutService moveItemToWishList API response :\n\n" + moveToWishlistResponse);

			AssertJUnit.assertEquals("CheckoutService moveItemToWishList API is not working",
					Integer.parseInt(successRespCode), moveItemToWishListReqGen.response.getStatus());

			try {
				String jsonschema = new Toolbox()
						.readFileAsString("Data/SchemaSet/JSON/checkoutservice-movetowishlist-schema.txt");
				List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(moveToWishlistResponse,
						jsonschema);
				AssertJUnit.assertTrue(
						missingNodeList + " nodes are missing in CheckoutService moveItemToWishList API response",
						CollectionUtils.isEmpty(missingNodeList));
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {
			System.out.println(
					"\nPrinting skuId : " + skuId + " & itemId : " + itemId + " is already present in wishlist\n");
			log.info("\nPrinting skuId : " + skuId + " & itemId : " + itemId + " is already present in wishlist\n");
		}

	}

	@Test(groups = { "SchemaValidation",
			"CartAPIsSchemaValidation" }, dataProvider = "CartServiceDP_operationCustomizeDataProvider_verifyResponseDataNodesUsingSchemaValidations")
	public void CartServiceTests_operationCustomize_verifyResponseDataNodesUsingSchemaValidations(String userName,
			String password, String customizeOpn, String customizeName, String customizeNumber, String doCustomize,
			String successRespCode) {
		RequestGenerator fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
		System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"
				+ fetchCartReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"
				+ fetchCartReqGen.respvalidate.returnresponseasstring());

		AssertJUnit.assertTrue("Error while invoking CheckoutService operationFetchCart API",
				fetchCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
						.contains(SUCCESS_STATUS_TYPE));

		if (fetchCartReqGen.respvalidate.NodeValue(CartNodes.CART_TOTAL_COUNT.toString(), true).equals(EMPTY_VALUE)) {

			List<Integer> styleIdList = checkoutTestsHelper.performSeachServiceToGetStyleIds(OCC_SEARCH_PROD,
					OCC_SEARCH_QTY, OCC_SEARCH_RET_DOCS, OCC_SEARCH_IS_FACET);

			if (!CollectionUtils.isEmpty(styleIdList)) {
				for (Integer styleId : styleIdList) {
					System.out.println("\nPrinting the StyleId : " + styleId);
					log.info("\nPrinting the StyleId : " + styleId);

					RequestGenerator getStyleDataReqGen = checkoutTestsHelper
							.performGetStyleDataOperation(String.valueOf(styleId));
					System.out.println("\nPrinting StyleService getStyleData API response :\n\n"
							+ getStyleDataReqGen.respvalidate.returnresponseasstring());
					log.info("\nPrinting StyleService getStyleData API response :\n\n"
							+ getStyleDataReqGen.respvalidate.returnresponseasstring());

					AssertJUnit.assertTrue("Error while getting style data", getStyleDataReqGen.respvalidate
							.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));

					String response = getStyleDataReqGen.respvalidate.returnresponseasstring();
					List<Integer> ids = JsonPath.read(response, "$.data..styleOptions[*].id");

					for (int i = 0; i < ids.size(); i++) {
						String skuId = JsonPath.read(response, "$.data..styleOptions[" + i + "].skuId").toString();
						String inventoryCount = JsonPath
								.read(response, "$.data..styleOptions[" + i + "].inventoryCount").toString();
						String available = JsonPath.read(response, "$.data..styleOptions[" + i + "].available")
								.toString();

						if (!inventoryCount.equals(EMPTY_VALUE) && available.equals(TRUE_VALUE)) {
							fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
							System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"
									+ fetchCartReqGen.respvalidate.returnresponseasstring());
							log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"
									+ fetchCartReqGen.respvalidate.returnresponseasstring());

							if (StringUtils.isBlank(checkoutTestsHelper.getItemIdFromSKUId(fetchCartReqGen, skuId,
									CheckoutDataProviderEnum.CART.name()))) {
								RequestGenerator addItemToCartReqGen = checkoutTestsHelper.addItemToCart(userName,
										password, skuId, inventoryCount, ADD_OPERATION);
								System.out.println("\nPrinting CheckoutService addItemToCart API response :\n\n"
										+ addItemToCartReqGen.respvalidate.returnresponseasstring());
								log.info("\nPrinting CheckoutService addItemToCart API response :\n\n"
										+ addItemToCartReqGen.respvalidate.returnresponseasstring());

								AssertJUnit.assertTrue("Error while invoking CheckoutService addItemToCart API",
										addItemToCartReqGen.respvalidate
												.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
												.contains(SUCCESS_STATUS_TYPE));

								System.out.println(
										"\n" + skuId + " is available and it doesn't exists in cart so added to cart");
								log.info("\n" + skuId + " is available and it doesn't exists in cart so added to cart");
								break;

							} else {
								System.out.println(
										"\n" + skuId + " is not added because this item already exists in cart");
								log.error("\n" + skuId + " is not added because this item already exists in cart");
							}

						} else {
							System.out.println(
									"\n" + skuId + " is not added because this item is currently out of stock");
							log.info("\n" + skuId + " is not added because this item is currently out of stock");
						}
					}
				}

			} else {
				System.out.println("\nError while invoking SearchService getStyleData API");
				log.error("\nError while invoking SearchService getStyleData API");
				AssertJUnit.fail("Error while invoking SearchService getStyleData API");
			}

			fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
			System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"
					+ fetchCartReqGen.respvalidate.returnresponseasstring());
			log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"
					+ fetchCartReqGen.respvalidate.returnresponseasstring());

			AssertJUnit.assertTrue("Error while invoking CheckoutService operationFetchCart API",
					fetchCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
							.contains(SUCCESS_STATUS_TYPE));
		}

		String skuId = fetchCartReqGen.respvalidate.NodeValue(CartNodes.CART_ITEM_ENTRIES_AVAIL_SIZES_SKUID.toString(),
				false);
		String itemId = checkoutTestsHelper.getItemIdFromSKUId(fetchCartReqGen, skuId,
				CheckoutDataProviderEnum.CART.name());

		System.out.println(
				"\nPrinting skuId : " + skuId + " & itemId : " + itemId + " is going for operationCustomize\n");
		log.info("\nPrinting skuId : " + skuId + " & itemId : " + itemId + " is going for operationCustomize\n");

		RequestGenerator operationCustomizeReqGen = checkoutTestsHelper.operationCustomize(userName, password, itemId,
				customizeOpn, customizeName, customizeNumber, doCustomize);
		String operationCustomizeResponse = operationCustomizeReqGen.respvalidate.returnresponseasstring();
		System.out.println(
				"\nPrinting CheckoutService operationCustomize API response :\n\n" + operationCustomizeResponse);
		log.info("\nPrinting CheckoutService operationCustomize API response :\n\n" + operationCustomizeResponse);

		AssertJUnit.assertEquals("CheckoutService operationCustomize API is not working",
				Integer.parseInt(successRespCode), operationCustomizeReqGen.response.getStatus());

		try {
			String jsonschema = new Toolbox()
					.readFileAsString("Data/SchemaSet/JSON/checkoutservice-operationcustomize-schema.txt");
			List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(operationCustomizeResponse,
					jsonschema);
			AssertJUnit.assertTrue(
					missingNodeList + " nodes are missing in CheckoutService operationCustomize API response",
					CollectionUtils.isEmpty(missingNodeList));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test(groups = {
			"SchemaValidation" }, dataProvider = "CartServiceDP_operationFetchWishListDataProvider_verifyResponseDataNodesUsingSchemaValidations")
	public void CartServiceTests_operationFetchWishList_verifyResponseDataNodesUsingSchemaValidations(String userName,
			String password, String successRespCode) {
		RequestGenerator fetchWishlistReqGen = checkoutTestsHelper.operationFetchWishList(userName, password);
		String fetchWishlistResponse = fetchWishlistReqGen.respvalidate.returnresponseasstring();
		System.out.println(
				"\nPrinting CheckoutService operationFetchWishList API response :\n\n" + fetchWishlistResponse);
		log.info("\nPrinting CheckoutService operationFetchWishList API response :\n\n" + fetchWishlistResponse);

		AssertJUnit.assertEquals("CheckoutService operationFetchCart API is not working",
				Integer.parseInt(successRespCode), fetchWishlistReqGen.response.getStatus());

		try {
			String jsonschema = new Toolbox()
					.readFileAsString("Data/SchemaSet/JSON/checkoutservice-fetchwishlist-schema.txt");
			List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(fetchWishlistResponse,
					jsonschema);
			AssertJUnit.assertTrue(
					missingNodeList + " nodes are missing in CheckoutService operationFetchWishList API response",
					CollectionUtils.isEmpty(missingNodeList));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test(groups = {
			"SchemaValidation" }, dataProvider = "CartServiceDP_addItemToWishListUsingSkuIdDataProvider_verifyResponseDataNodesUsingSchemaValidations")
	public void CartServiceTests_addItemToWishListUsingSkuId_verifyResponseDataNodesUsingSchemaValidations(
			String userName, String password, String addItemQty, String addOperation, String successRespCode) {
		List<Integer> styleIdList = checkoutTestsHelper.performSeachServiceToGetStyleIds(AWL_SEARCH_PROD,
				AWL_SEARCH_QTY, AWL_SEARCH_RET_DOCS, AWL_SEARCH_IS_FACET);

		if (!CollectionUtils.isEmpty(styleIdList)) {
			for (Integer styleId : styleIdList) {
				System.out.println("\nPrinting the StyleId : " + styleId);
				log.info("\nPrinting the StyleId : " + styleId);

				RequestGenerator getStyleDataReqGen = checkoutTestsHelper
						.performGetStyleDataOperation(String.valueOf(styleId));
				System.out.println("\nPrinting StyleService getStyleData API response :\n\n"
						+ getStyleDataReqGen.respvalidate.returnresponseasstring());
				log.info("\nPrinting StyleService getStyleData API response :\n\n"
						+ getStyleDataReqGen.respvalidate.returnresponseasstring());

				AssertJUnit.assertTrue("Error while getting style data", getStyleDataReqGen.respvalidate
						.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));

				String response = getStyleDataReqGen.respvalidate.returnresponseasstring();
				List<Integer> ids = JsonPath.read(response, "$.data..styleOptions[*].id");

				for (int i = 0; i < ids.size(); i++) {
					String skuId = JsonPath.read(response, "$.data..styleOptions[" + i + "].skuId").toString();
					String inventoryCount = JsonPath.read(response, "$.data..styleOptions[" + i + "].inventoryCount")
							.toString();
					String available = JsonPath.read(response, "$.data..styleOptions[" + i + "].available").toString();

					if (!inventoryCount.equals(EMPTY_VALUE) && available.equals(TRUE_VALUE)) {
						RequestGenerator fetchWishlistReqGen = checkoutTestsHelper.operationFetchWishList(userName,
								password);
						System.out.println("\nPrinting CheckoutService operationFetchWishList API response :\n\n"
								+ fetchWishlistReqGen.respvalidate.returnresponseasstring());
						log.info("\nPrinting CheckoutService operationFetchWishList API response :\n\n"
								+ fetchWishlistReqGen.respvalidate.returnresponseasstring());

						if (StringUtils.isBlank(checkoutTestsHelper.getItemIdFromSKUId(fetchWishlistReqGen, skuId,
								CheckoutDataProviderEnum.WISHLIST.name()))) {
							int invCount = Integer.parseInt(inventoryCount);
							int qty = Integer.parseInt(addItemQty);
							int itemQtyToAdd = invCount <= qty ? invCount : qty;

							RequestGenerator addItemToWishListReqGen = checkoutTestsHelper.addItemToWishList(userName,
									password, skuId, null, String.valueOf(itemQtyToAdd), addOperation);

							String addItemToWishListUsingSkuIdResponse = addItemToWishListReqGen.respvalidate
									.returnresponseasstring();

							System.out.println("\nPrinting CheckoutService addItemToWishList API response :\n\n"
									+ addItemToWishListUsingSkuIdResponse);
							log.info("\nPrinting CheckoutService addItemToWishList API response :\n\n"
									+ addItemToWishListUsingSkuIdResponse);

							AssertJUnit.assertEquals("CheckoutService addItemToWishList API is not working",
									Integer.parseInt(successRespCode), addItemToWishListReqGen.response.getStatus());

							try {
								String jsonschema = new Toolbox().readFileAsString(
										"Data/SchemaSet/JSON/checkoutservice-additemtowishlistusingskuid-schema.txt");
								List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(
										addItemToWishListUsingSkuIdResponse, jsonschema);
								AssertJUnit.assertTrue(
										missingNodeList
												+ " nodes are missing in CheckoutService addItemToWishList API response",
										CollectionUtils.isEmpty(missingNodeList));
							} catch (Exception e) {
								e.printStackTrace();
							}

							System.out.println("\n" + skuId + " is available but not in wishlist so added to wishlist");
							log.info("\n" + skuId + " is available but not in wishlist so added to wishlist");
							break;

						} else {
							System.out.println(
									"\n" + skuId + " is not added because this item already exists in wishlist");
							log.error("\n" + skuId + " is not added because this item already exists in wishlist");
						}

					} else {
						System.out.println("\n" + skuId + " is not added because this item is currently out of stock");
						log.info("\n" + skuId + " is not added because this item is currently out of stock");
					}
				}
			}

		} else {
			System.out.println("\nError while invoking SearchService getStyleData API");
			log.error("\nError while invoking SearchService getStyleData API");
			AssertJUnit.fail("Error while invoking SearchService getStyleData API");
		}
	}

	@Test(groups = {
			"SchemaValidation" }, dataProvider = "CartServiceDP_addItemToWishListUsingStyleIdDataProvider_verifyResponseDataNodesUsingSchemaValidations")
	public void CartServiceTests_addItemToWishListUsingStyleId_verifyResponseDataNodesUsingSchemaValidations(
			String userName, String password, String addItemQty, String addOperation, String successRespCode) {
		List<Integer> styleIdList = checkoutTestsHelper.performSeachServiceToGetStyleIds(AWL_SEARCH_PROD,
				AWL_SEARCH_QTY, AWL_SEARCH_RET_DOCS, AWL_SEARCH_IS_FACET);

		if (!CollectionUtils.isEmpty(styleIdList)) {
			for (Integer styleId : styleIdList) {
				System.out.println("\nPrinting the StyleId : " + styleId);
				log.info("\nPrinting the StyleId : " + styleId);

				RequestGenerator getStyleDataReqGen = checkoutTestsHelper
						.performGetStyleDataOperation(String.valueOf(styleId));
				System.out.println("\nPrinting StyleService getStyleData API response :\n\n"
						+ getStyleDataReqGen.respvalidate.returnresponseasstring());
				log.info("\nPrinting StyleService getStyleData API response :\n\n"
						+ getStyleDataReqGen.respvalidate.returnresponseasstring());

				AssertJUnit.assertTrue("Error while getting style data", getStyleDataReqGen.respvalidate
						.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));

				String response = getStyleDataReqGen.respvalidate.returnresponseasstring();
				List<Integer> ids = JsonPath.read(response, "$.data..styleOptions[*].id");

				for (int i = 0; i < ids.size(); i++) {
					String skuId = JsonPath.read(response, "$.data..styleOptions[" + i + "].skuId").toString();
					String inventoryCount = JsonPath.read(response, "$.data..styleOptions[" + i + "].inventoryCount")
							.toString();
					String available = JsonPath.read(response, "$.data..styleOptions[" + i + "].available").toString();

					if (!inventoryCount.equals(EMPTY_VALUE) && available.equals(TRUE_VALUE)) {
						RequestGenerator fetchWishlistReqGen = checkoutTestsHelper.operationFetchWishList(userName,
								password);
						System.out.println("\nPrinting CheckoutService operationFetchWishList API response :\n\n"
								+ fetchWishlistReqGen.respvalidate.returnresponseasstring());
						log.info("\nPrinting CheckoutService operationFetchWishList API response :\n\n"
								+ fetchWishlistReqGen.respvalidate.returnresponseasstring());

						if (StringUtils.isBlank(checkoutTestsHelper.getItemIdFromSKUId(fetchWishlistReqGen, skuId,
								CheckoutDataProviderEnum.WISHLIST.name()))) {
							int invCount = Integer.parseInt(inventoryCount);
							int qty = Integer.parseInt(addItemQty);
							int itemQtyToAdd = invCount <= qty ? invCount : qty;

							RequestGenerator addItemToWishListReqGen = checkoutTestsHelper.addItemToWishList(userName,
									password, null, String.valueOf(styleId), String.valueOf(itemQtyToAdd),
									addOperation);
							String addItemToWishlistUsingStyleIdResponse = addItemToWishListReqGen.respvalidate
									.returnresponseasstring();
							System.out.println("\nPrinting CheckoutService addItemToWishList API response :\n\n"
									+ addItemToWishlistUsingStyleIdResponse);
							log.info("\nPrinting CheckoutService addItemToWishList API response :\n\n"
									+ addItemToWishlistUsingStyleIdResponse);

							AssertJUnit.assertEquals("CheckoutService addItemToWishListUsingStyleId API is not working",
									Integer.parseInt(successRespCode), addItemToWishListReqGen.response.getStatus());

							try {
								String jsonschema = new Toolbox().readFileAsString(
										"Data/SchemaSet/JSON/checkoutservice-additemtowishlistusingstyleid-schema.txt");
								List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(
										addItemToWishlistUsingStyleIdResponse, jsonschema);
								AssertJUnit.assertTrue(
										missingNodeList
												+ " nodes are missing in CheckoutService addItemToWishList API response",
										CollectionUtils.isEmpty(missingNodeList));
							} catch (Exception e) {
								e.printStackTrace();
							}

							System.out.println("\n" + skuId + " is available but not in wishlist so added to wishlist");
							log.info("\n" + skuId + " is available but not in wishlist so added to wishlist");
							break;

						} else {
							System.out.println(
									"\n" + skuId + " is not added because this item already exists in wishlist");
							log.error("\n" + skuId + " is not added because this item already exists in wishlist");
						}

					} else {
						System.out.println("\n" + skuId + " is not added because this item is currently out of stock");
						log.info("\n" + skuId + " is not added because this item is currently out of stock");
					}
				}
			}

		} else {
			System.out.println("\nError while invoking SearchService API");
			log.error("\nError while invoking SearchService API");
			AssertJUnit.fail("Error while invoking SearchService API");
		}
	}

	@Test(groups = {
			"SchemaValidation" }, dataProvider = "CartServiceDP_updateItemInWishListDataProvider_verifyResponseDataNodesUsingSchemaValidations")
	public void CartServiceTests_updateItemInWishList_verifyResponseDataNodesUsingSchemaValidations(String userName,
			String password, String itemQtyToUpdate, String updateOpn, String successRespCode) {
		RequestGenerator fetchWishlistReqGen = checkoutTestsHelper.operationFetchWishList(userName, password);
		System.out.println("\nPrinting CheckoutService operationFetchWishList API response :\n\n"
				+ fetchWishlistReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService operationFetchWishList API response :\n\n"
				+ fetchWishlistReqGen.respvalidate.returnresponseasstring());

		AssertJUnit.assertTrue("Error while invoking CheckoutService operationFetchWishList API",
				fetchWishlistReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
						.contains(SUCCESS_STATUS_TYPE));

		if (fetchWishlistReqGen.respvalidate.NodeValue(WishListNodes.WISHLIST_TOTAL_COUNT.toString(), true)
				.equals(EMPTY_VALUE)) {
			List<Integer> styleIdList = checkoutTestsHelper.performSeachServiceToGetStyleIds(UWL_SEARCH_PROD,
					UWL_SEARCH_QTY, UWL_SEARCH_RET_DOCS, UWL_SEARCH_IS_FACET);

			if (!CollectionUtils.isEmpty(styleIdList)) {
				for (Integer styleId : styleIdList) {
					System.out.println("\nPrinting the StyleId : " + styleId);
					log.info("\nPrinting the StyleId : " + styleId);

					RequestGenerator getStyleDataReqGen = checkoutTestsHelper
							.performGetStyleDataOperation(String.valueOf(styleId));
					System.out.println("\nPrinting StyleService getStyleData API response :\n\n"
							+ getStyleDataReqGen.respvalidate.returnresponseasstring());
					log.info("\nPrinting StyleService getStyleData API response :\n\n"
							+ getStyleDataReqGen.respvalidate.returnresponseasstring());

					AssertJUnit.assertTrue("Error while getting style data", getStyleDataReqGen.respvalidate
							.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));

					String response = getStyleDataReqGen.respvalidate.returnresponseasstring();
					List<Integer> ids = JsonPath.read(response, "$.data..styleOptions[*].id");

					for (int i = 0; i < ids.size(); i++) {
						String skuId = JsonPath.read(response, "$.data..styleOptions[" + i + "].skuId").toString();
						String inventoryCount = JsonPath
								.read(response, "$.data..styleOptions[" + i + "].inventoryCount").toString();
						String available = JsonPath.read(response, "$.data..styleOptions[" + i + "].available")
								.toString();

						if (!inventoryCount.equals(EMPTY_VALUE) && available.equals(TRUE_VALUE)) {
							fetchWishlistReqGen = checkoutTestsHelper.operationFetchWishList(userName, password);
							System.out.println("\nPrinting CheckoutService operationFetchWishList API response :\n\n"
									+ fetchWishlistReqGen.respvalidate.returnresponseasstring());
							log.info("\nPrinting CheckoutService operationFetchWishList API response :\n\n"
									+ fetchWishlistReqGen.respvalidate.returnresponseasstring());

							AssertJUnit.assertTrue("Error while invoking CheckoutService operationFetchWishList API",
									fetchWishlistReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
											.contains(SUCCESS_STATUS_TYPE));

							if (StringUtils.isBlank(checkoutTestsHelper.getItemIdFromSKUId(fetchWishlistReqGen, skuId,
									CheckoutDataProviderEnum.WISHLIST.name()))) {
								RequestGenerator addItemToWishlistReqGen = checkoutTestsHelper.addItemToWishList(
										userName, password, skuId, null, inventoryCount, ADD_OPERATION);
								System.out.println("\nPrinting CheckoutService addItemToCart API response :\n\n"
										+ addItemToWishlistReqGen.respvalidate.returnresponseasstring());
								log.info("\nPrinting CheckoutService addItemToCart API response :\n\n"
										+ addItemToWishlistReqGen.respvalidate.returnresponseasstring());

								AssertJUnit.assertTrue("Error while invoking CheckoutService addItemToWishList API",
										addItemToWishlistReqGen.respvalidate
												.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
												.contains(SUCCESS_STATUS_TYPE));

								System.out.println("\n" + skuId + " is available but not in cart so added to cart");
								log.info("\n" + skuId + " is available but not in cart so added to cart");
								break;

							} else {
								System.out.println(
										"\n" + skuId + " is not added because this item already exists in cart");
								log.error("\n" + skuId + " is not added because this item already exists in cart");
							}

						} else {
							System.out.println(
									"\n" + skuId + " is not added because this item is currently out of stock");
							log.info("\n" + skuId + " is not added because this item is currently out of stock");
						}
					}
				}

			} else {
				System.out.println("\nError while invoking SearchService API");
				log.error("\nError while invoking SearchService API");
				AssertJUnit.fail("Error while invoking SearchService API");
			}

			fetchWishlistReqGen = checkoutTestsHelper.operationFetchWishList(userName, password);
			System.out.println("\nPrinting operationFetchWishList API response :\n\n"
					+ fetchWishlistReqGen.respvalidate.returnresponseasstring());
			log.info("\nPrinting operationFetchWishList API response :\n\n"
					+ fetchWishlistReqGen.respvalidate.returnresponseasstring());

			AssertJUnit.assertTrue("Error while invoking CheckoutService operationFetchWishList API",
					fetchWishlistReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
							.contains(SUCCESS_STATUS_TYPE));

		}
		String skuId = fetchWishlistReqGen.respvalidate
				.NodeValue(WishListNodes.WISHLIST_ITEM_ENTRIES_AVAILSIZES_SKUID.toString(), false);
		String itemId = checkoutTestsHelper.getItemIdFromSKUId(fetchWishlistReqGen, skuId,
				CheckoutDataProviderEnum.WISHLIST.name());

		System.out.println("\nPrinting skuId : " + skuId + " & itemId : " + itemId + " & quantity : " + itemQtyToUpdate
				+ " is going for updation\n");
		log.info("\nPrinting skuId : " + skuId + " & itemId : " + itemId + " & quantity : " + itemQtyToUpdate
				+ " is going for updation\n");

		RequestGenerator updateItemInWishlistReqGen = checkoutTestsHelper.updateItemInWishList(userName, password,
				itemId, skuId, String.valueOf(itemQtyToUpdate), updateOpn);
		String updateItemInWishlistResponse = updateItemInWishlistReqGen.respvalidate.returnresponseasstring();
		System.out.println(
				"\nPrinting CheckoutService updateItemInWishList API response :\n\n" + updateItemInWishlistResponse);
		log.info("\nPrinting CheckoutService updateItemInWishList API response :\n\n" + updateItemInWishlistResponse);

		AssertJUnit.assertEquals("CheckoutService updateItemInWishList API is not working",
				Integer.parseInt(successRespCode), updateItemInWishlistReqGen.response.getStatus());

		try {
			String jsonschema = new Toolbox()
					.readFileAsString("Data/SchemaSet/JSON/checkoutservice-updateiteminwishlist-schema.txt");
			List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(
					updateItemInWishlistResponse, jsonschema);
			AssertJUnit.assertTrue(
					missingNodeList + " nodes are missing in CheckoutService updateItemInWishList API response",
					CollectionUtils.isEmpty(missingNodeList));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test(groups = {
			"SchemaValidation" }, dataProvider = "CartServiceDP_removeItemFromWishListDataProvider_verifyResponseDataNodesUsingSchemaValidations")
	public void CartServiceTests_removeItemFromWishList_verifyResponseDataNodesUsingSchemaValidations(String userName,
			String password, String delOperation, String successRespCode) {
		RequestGenerator fetchWishlistReqGen = checkoutTestsHelper.operationFetchWishList(userName, password);
		System.out.println("\nPrinting CheckoutService operationFetchWishList API response :\n\n"
				+ fetchWishlistReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService operationFetchWishList API response :\n\n"
				+ fetchWishlistReqGen.respvalidate.returnresponseasstring());

		AssertJUnit.assertTrue("Error while invoking CheckoutService operationFetchWishList API",
				fetchWishlistReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
						.contains(SUCCESS_STATUS_TYPE));

		if (fetchWishlistReqGen.respvalidate.NodeValue(WishListNodes.WISHLIST_TOTAL_COUNT.toString(), true)
				.equals(EMPTY_VALUE)) {
			List<Integer> styleIdList = checkoutTestsHelper.performSeachServiceToGetStyleIds(RWL_SEARCH_PROD,
					RWL_SEARCH_QTY, RWL_SEARCH_RET_DOCS, RWL_SEARCH_IS_FACET);

			if (!CollectionUtils.isEmpty(styleIdList)) {
				for (Integer styleId : styleIdList) {
					System.out.println("\nPrinting the StyleId : " + styleId);
					log.info("\nPrinting the StyleId : " + styleId);

					RequestGenerator getStyleDataReqGen = checkoutTestsHelper
							.performGetStyleDataOperation(String.valueOf(styleId));
					System.out.println("\nPrinting StyleService getStyleData API response :\n\n"
							+ getStyleDataReqGen.respvalidate.returnresponseasstring());
					log.info("\nPrinting StyleService getStyleData API response :\n\n"
							+ getStyleDataReqGen.respvalidate.returnresponseasstring());

					AssertJUnit.assertTrue("Error while getting style data", getStyleDataReqGen.respvalidate
							.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));

					String response = getStyleDataReqGen.respvalidate.returnresponseasstring();
					List<Integer> ids = JsonPath.read(response, "$.data..styleOptions[*].id");

					for (int i = 0; i < ids.size(); i++) {
						String skuId = JsonPath.read(response, "$.data..styleOptions[" + i + "].skuId").toString();
						String inventoryCount = JsonPath
								.read(response, "$.data..styleOptions[" + i + "].inventoryCount").toString();
						String available = JsonPath.read(response, "$.data..styleOptions[" + i + "].available")
								.toString();

						if (!inventoryCount.equals(EMPTY_VALUE) && available.equals(TRUE_VALUE)) {
							fetchWishlistReqGen = checkoutTestsHelper.operationFetchWishList(userName, password);
							System.out.println("\nPrinting CheckoutService operationFetchWishList API response :\n\n"
									+ fetchWishlistReqGen.respvalidate.returnresponseasstring());
							log.info("\nPrinting CheckoutService operationFetchWishList API response :\n\n"
									+ fetchWishlistReqGen.respvalidate.returnresponseasstring());

							AssertJUnit.assertTrue("Error while invoking CheckoutService operationFetchWishList API",
									fetchWishlistReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
											.contains(SUCCESS_STATUS_TYPE));

							if (StringUtils.isBlank(checkoutTestsHelper.getItemIdFromSKUId(fetchWishlistReqGen, skuId,
									CheckoutDataProviderEnum.WISHLIST.name()))) {
								RequestGenerator addItemToWishlistReqGen = checkoutTestsHelper.addItemToWishList(
										userName, password, skuId, null, inventoryCount, ADD_OPERATION);
								System.out.println("\nPrinting CheckoutService addItemToCart API response :\n\n"
										+ addItemToWishlistReqGen.respvalidate.returnresponseasstring());
								log.info("\nPrinting CheckoutService addItemToCart API response :\n\n"
										+ addItemToWishlistReqGen.respvalidate.returnresponseasstring());

								AssertJUnit.assertTrue("Error while invoking CheckoutService addItemToWishList API",
										addItemToWishlistReqGen.respvalidate
												.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
												.contains(SUCCESS_STATUS_TYPE));

								System.out.println("\n" + skuId + " is available but not in cart so added to cart");
								log.info("\n" + skuId + " is available but not in cart so added to cart");
								break;

							} else {
								System.out.println(
										"\n" + skuId + " is not added because this item already exists in cart");
								log.error("\n" + skuId + " is not added because this item already exists in cart");
							}

						} else {
							System.out.println(
									"\n" + skuId + " is not added because this item is currently out of stock");
							log.info("\n" + skuId + " is not added because this item is currently out of stock");
						}
					}
				}

			} else {
				System.out.println("\nError while invoking SearchService API");
				log.error("\nError while invoking SearchService API");
				AssertJUnit.fail("Error while invoking SearchService API");
			}

			fetchWishlistReqGen = checkoutTestsHelper.operationFetchWishList(userName, password);
			System.out.println("\nPrinting CheckoutService operationFetchWishList API response :\n\n"
					+ fetchWishlistReqGen.respvalidate.returnresponseasstring());
			log.info("\nPrinting CheckoutService operationFetchWishList API response :\n\n"
					+ fetchWishlistReqGen.respvalidate.returnresponseasstring());

			AssertJUnit.assertTrue("Error while invoking CheckoutService operationFetchWishList API",
					fetchWishlistReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
							.contains(SUCCESS_STATUS_TYPE));
		}
		String skuId = fetchWishlistReqGen.respvalidate
				.NodeValue(WishListNodes.WISHLIST_ITEM_ENTRIES_AVAILSIZES_SKUID.toString(), false);
		String itemId = checkoutTestsHelper.getItemIdFromSKUId(fetchWishlistReqGen, skuId,
				CheckoutDataProviderEnum.WISHLIST.name());

		System.out.println(
				"\nPrinting skuId : " + skuId + " & itemId : " + itemId + " is going to remove item from wishlist\n");
		log.info("\nPrinting skuId : " + skuId + " & itemId : " + itemId + " is going to remove item from wishlist\n");

		RequestGenerator removeItemFromWishlist = checkoutTestsHelper.removeItemFromWishList(userName, password, itemId,
				delOperation);
		String removeItemFromWishlistResponse = removeItemFromWishlist.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting CheckoutService removeItemFromWishList API response :\n\n"
				+ removeItemFromWishlistResponse);
		log.info("\nPrinting CheckoutService removeItemFromWishList API response :\n\n"
				+ removeItemFromWishlistResponse);

		AssertJUnit.assertEquals("CheckoutService removeItemFromWishList API is not working",
				Integer.parseInt(successRespCode), removeItemFromWishlist.response.getStatus());

		try {
			String jsonschema = new Toolbox()
					.readFileAsString("Data/SchemaSet/JSON/checkoutservice-removeitemfromwishlist-schema.txt");
			List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(
					removeItemFromWishlistResponse, jsonschema);
			AssertJUnit.assertTrue(
					missingNodeList + " nodes are missing in CheckoutService removeItemFromWishList API response",
					CollectionUtils.isEmpty(missingNodeList));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test(groups = {
			"SchemaValidation" }, dataProvider = "CartServiceDP_moveItemToCartDataProvider_verifyResponseDataNodesUsingSchemaValidations")
	public void CartServiceTests_moveItemToCart_verifyResponseDataNodesUsingSchemaValidations(String userName,
			String password, String successRespCode) {
		RequestGenerator fetchWishlistReqGen = checkoutTestsHelper.operationFetchWishList(userName, password);
		System.out.println("\nPrinting CheckoutService operationFetchWishList API response :\n\n"
				+ fetchWishlistReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService operationFetchWishList API response :\n\n"
				+ fetchWishlistReqGen.respvalidate.returnresponseasstring());

		AssertJUnit.assertTrue("Error while invoking CheckoutService operationFetchWishList API",
				fetchWishlistReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
						.contains(SUCCESS_STATUS_TYPE));

		if (fetchWishlistReqGen.respvalidate.NodeValue(WishListNodes.WISHLIST_TOTAL_COUNT.toString(), true)
				.equals(EMPTY_VALUE)) {
			List<Integer> styleIdList = checkoutTestsHelper.performSeachServiceToGetStyleIds(MC_SEARCH_PROD,
					MC_SEARCH_QTY, MC_SEARCH_RET_DOCS, MC_SEARCH_IS_FACET);

			if (!CollectionUtils.isEmpty(styleIdList)) {
				for (Integer styleId : styleIdList) {
					System.out.println("\nPrinting the StyleId : " + styleId);
					log.info("\nPrinting the StyleId : " + styleId);

					RequestGenerator getStyleDataReqGen = checkoutTestsHelper
							.performGetStyleDataOperation(String.valueOf(styleId));
					System.out.println("\nPrinting StyleService getStyleData API response :\n\n"
							+ getStyleDataReqGen.respvalidate.returnresponseasstring());
					log.info("\nPrinting StyleService getStyleData API response :\n\n"
							+ getStyleDataReqGen.respvalidate.returnresponseasstring());

					AssertJUnit.assertTrue("Error while getting style data", getStyleDataReqGen.respvalidate
							.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));

					String response = getStyleDataReqGen.respvalidate.returnresponseasstring();
					List<Integer> ids = JsonPath.read(response, "$.data..styleOptions[*].id");

					for (int i = 0; i < ids.size(); i++) {
						String skuId = JsonPath.read(response, "$.data..styleOptions[" + i + "].skuId").toString();
						String inventoryCount = JsonPath
								.read(response, "$.data..styleOptions[" + i + "].inventoryCount").toString();
						String available = JsonPath.read(response, "$.data..styleOptions[" + i + "].available")
								.toString();

						if (!inventoryCount.equals(EMPTY_VALUE) && available.equals(TRUE_VALUE)) {
							fetchWishlistReqGen = checkoutTestsHelper.operationFetchWishList(userName, password);
							System.out.println("\nPrinting CheckoutService operationFetchWishList API response :\n\n"
									+ fetchWishlistReqGen.respvalidate.returnresponseasstring());
							log.info("\nPrinting CheckoutService operationFetchWishList API response :\n\n"
									+ fetchWishlistReqGen.respvalidate.returnresponseasstring());

							AssertJUnit.assertTrue("Error while invoking CheckoutService operationFetchWishList API",
									fetchWishlistReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
											.contains(SUCCESS_STATUS_TYPE));

							if (StringUtils.isBlank(checkoutTestsHelper.getItemIdFromSKUId(fetchWishlistReqGen, skuId,
									CheckoutDataProviderEnum.WISHLIST.name()))) {
								RequestGenerator addItemToWishlistReqGen = checkoutTestsHelper.addItemToWishList(
										userName, password, skuId, null, inventoryCount, ADD_OPERATION);
								System.out.println("\nPrinting CheckoutService addItemToWishList API response :\n\n"
										+ addItemToWishlistReqGen.respvalidate.returnresponseasstring());
								log.info("\nPrinting CheckoutService addItemToWishList API response :\n\n"
										+ addItemToWishlistReqGen.respvalidate.returnresponseasstring());

								AssertJUnit.assertTrue("Error while invoking CheckoutService addItemToWishList API",
										addItemToWishlistReqGen.respvalidate
												.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
												.contains(SUCCESS_STATUS_TYPE));

								System.out.println(
										"\n" + skuId + " is available but not in wishlist so added to wishlist");
								log.info("\n" + skuId + " is available but not in wishlist so added to wishlist");
								break;

							} else {
								System.out.println(
										"\n" + skuId + " is not added because this item already exists in wishlist");
								log.error("\n" + skuId + " is not added because this item already exists in wishlist");
							}

						} else {
							System.out.println(
									"\n" + skuId + " is not added because this item is currently out of stock");
							log.info("\n" + skuId + " is not added because this item is currently out of stock");
						}
					}
				}

			} else {
				System.out.println("\nError while invoking SearchService API");
				log.error("\nError while invoking SearchService API");
				AssertJUnit.fail("Error while invoking SearchService API");
			}

			fetchWishlistReqGen = checkoutTestsHelper.operationFetchWishList(userName, password);
			System.out.println("\nPrinting CheckoutService operationFetchWishList API response :\n\n"
					+ fetchWishlistReqGen.respvalidate.returnresponseasstring());
			log.info("\nPrinting CheckoutService operationFetchWishList API response :\n\n"
					+ fetchWishlistReqGen.respvalidate.returnresponseasstring());

			AssertJUnit.assertTrue("Error while invoking CheckoutService operationFetchWishList API",
					fetchWishlistReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
							.contains(SUCCESS_STATUS_TYPE));
		}

		String skuId = fetchWishlistReqGen.respvalidate
				.NodeValue(WishListNodes.WISHLIST_ITEM_ENTRIES_SELSIZE_SKUID.toString(), false);
		String itemId = checkoutTestsHelper.getItemIdFromSKUId(fetchWishlistReqGen, skuId,
				CheckoutDataProviderEnum.WISHLIST.name());

		RequestGenerator fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
		System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"
				+ fetchCartReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"
				+ fetchCartReqGen.respvalidate.returnresponseasstring());

		AssertJUnit.assertTrue("Error while invoking CheckoutService operationFetchCart API",
				fetchCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
						.contains(SUCCESS_STATUS_TYPE));

		if (StringUtils.isBlank(
				checkoutTestsHelper.getItemIdFromSKUId(fetchCartReqGen, skuId, CheckoutDataProviderEnum.CART.name()))) {
			System.out.println(
					"\nPrinting skuId : " + skuId + " & itemId : " + itemId + " is moving to cart from wishlist\n");
			log.info("\nPrinting skuId : " + skuId + " & itemId : " + itemId
					+ " is going is moving to cart from wishlist\n");

			RequestGenerator moveItemToCartReqGen = checkoutTestsHelper.moveItemToCart(userName, password, itemId);
			String moveItemToCartResponse = moveItemToCartReqGen.respvalidate.returnresponseasstring();
			System.out.println("\nPrinting CheckoutService moveItemToCart API response :\n\n" + moveItemToCartResponse);
			log.info("\nPrinting CheckoutService moveItemToCart API response :\n\n" + moveItemToCartResponse);

			AssertJUnit.assertEquals("CheckoutService moveItemToCart API is not working",
					Integer.parseInt(successRespCode), moveItemToCartReqGen.response.getStatus());

			try {
				String jsonschema = new Toolbox()
						.readFileAsString("Data/SchemaSet/JSON/checkoutservice-moveitemtocart-schema.txt");
				List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(moveItemToCartResponse,
						jsonschema);
				AssertJUnit.assertTrue(
						missingNodeList + " nodes are missing in CheckoutService moveItemToCart API response",
						CollectionUtils.isEmpty(missingNodeList));
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {
			System.out
					.println("\nPrinting skuId : " + skuId + " & itemId : " + itemId + " is already present in cart\n");
			log.info("\nPrinting skuId : " + skuId + " & itemId : " + itemId + " is already present in cart\n");

		}
	}

	@Test(groups = { "SchemaValidation",
			"CartAPIsSchemaValidation" }, dataProvider = "CartServiceDP_applyMyntCreditDataProvider_verifyResponseDataNodesUsingSchemaValidations")
	public void CartServiceTests_applyMyntCredit_verifyResponseDataNodesUsingSchemaValidations(String userName,
			String password, String useAmount, String successRespCode) {
		//userName = "testing123@myntra.com";
		RequestGenerator fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
		System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"
				+ fetchCartReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"
				+ fetchCartReqGen.respvalidate.returnresponseasstring());

		AssertJUnit.assertTrue("Error while invoking CheckoutService operationFetchCart API",
				fetchCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
						.contains(SUCCESS_STATUS_TYPE));

		if (fetchCartReqGen.respvalidate.NodeValue(CartNodes.CART_USE_MYNT_CASH.toString(), false).equals(TRUE_VALUE)) {
			RequestGenerator removeMyntCredit = checkoutTestsHelper.removeMyntCredit(userName, password);
			System.out.println("\nPrinting CheckoutService removeMyntCredit API response :\n\n"
					+ removeMyntCredit.respvalidate.returnresponseasstring());
			log.info("\nPrinting CheckoutService removeMyntCredit API response :\n\n"
					+ removeMyntCredit.respvalidate.returnresponseasstring());

			AssertJUnit.assertTrue("Error while invoking CheckoutService removeMyntCredit API",
					removeMyntCredit.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
							.contains(SUCCESS_STATUS_TYPE));
		}

		RequestGenerator applyMyntCreditReqGen = checkoutTestsHelper.applyMyntCredit(userName, password, useAmount);
		String applyMyntCreditResponse = applyMyntCreditReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting CheckoutService applyMyntCredit API response :\n\n" + applyMyntCreditResponse);
		log.info("\nPrinting CheckoutService applyMyntCredit API response :\n\n" + applyMyntCreditResponse);

		AssertJUnit.assertEquals("CheckoutService applyMyntCredit API is not working",
				Integer.parseInt(successRespCode), applyMyntCreditReqGen.response.getStatus());

		try {
			String jsonschema = new Toolbox()
					.readFileAsString("Data/SchemaSet/JSON/checkoutservice-applymyntcredit-schema.txt");
			List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(applyMyntCreditResponse,
					jsonschema);
			AssertJUnit.assertTrue(
					missingNodeList + " nodes are missing in CheckoutService moveItemToCart API response",
					CollectionUtils.isEmpty(missingNodeList));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test(groups = {
			"SchemaValidation" }, dataProvider = "CartServiceDP_getMyntCreditDataProvider_verifyResponseDataNodesUsingSchemaValidations")
	public void CartServiceTests_getMyntCredit_verifyResponseDataNodesUsingSchemaValidations(String userName,
			String password, String successRespCode) {
		RequestGenerator getMyntCreditReqGen = checkoutTestsHelper.getMyntCredit(userName, password);
		String getMyntCreditResponse = getMyntCreditReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting CheckoutService getMyntCredit API response :\n\n" + getMyntCreditResponse);
		log.info("\nPrinting getMyntCredit API response :\n\n" + getMyntCreditResponse);

		AssertJUnit.assertEquals("CheckoutService getMyntCredit API is not working", Integer.parseInt(successRespCode),
				getMyntCreditReqGen.response.getStatus());

		try {
			String jsonschema = new Toolbox()
					.readFileAsString("Data/SchemaSet/JSON/checkoutservice-getmyntcredit-schema.txt");
			List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(getMyntCreditResponse,
					jsonschema);
			AssertJUnit.assertTrue(missingNodeList + " nodes are missing in CheckoutService getMyntCredit API response",
					CollectionUtils.isEmpty(missingNodeList));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test(groups = { "SchemaValidation",
			"CartAPIsSchemaValidation" }, dataProvider = "CartServiceDP_removeMyntCreditDataProvider_verifyResponseDataNodesUsingSchemaValidations")
	public void CartServiceTests_removeMyntCredit_verifyResponseDataNodesUsingSchemaValidations(String userName,
			String password, String successRespCode) {
		RequestGenerator fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
		System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"
				+ fetchCartReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"
				+ fetchCartReqGen.respvalidate.returnresponseasstring());

		AssertJUnit.assertTrue(
				"CheckoutService operationFetchCart API response status type is not a success status type",
				fetchCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
						.contains(SUCCESS_STATUS_TYPE));

		if (fetchCartReqGen.respvalidate.NodeValue(CartNodes.CART_USE_MYNT_CASH.toString(), false)
				.equals(FALSE_VALUE)) {
			RequestGenerator applyMyntCreditReqGen = checkoutTestsHelper.applyMyntCredit(userName, password,
					USE_CREDIT_AMT);
			System.out.println("\nPrinting CheckoutService applyMyntCredit API response :\n\n"
					+ applyMyntCreditReqGen.respvalidate.returnresponseasstring());
			log.info("\nPrinting CheckoutService applyMyntCredit API response :\n\n"
					+ applyMyntCreditReqGen.respvalidate.returnresponseasstring());

			AssertJUnit.assertTrue("Error while invoking CheckoutService applyMyntCredit API",
					applyMyntCreditReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
							.contains(SUCCESS_STATUS_TYPE));
		}

		RequestGenerator removeMyntCreditReqGen = checkoutTestsHelper.removeMyntCredit(userName, password);
		String removeMyntCreditResponse = removeMyntCreditReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting CheckoutService removeMyntCredit API response :\n\n" + removeMyntCreditResponse);
		log.info("\nPrinting CheckoutService removeMyntCredit API response :\n\n" + removeMyntCreditResponse);

		AssertJUnit.assertEquals("CheckoutService removeMyntCredit API is not working",
				Integer.parseInt(successRespCode), removeMyntCreditReqGen.response.getStatus());

		try {
			String jsonschema = new Toolbox()
					.readFileAsString("Data/SchemaSet/JSON/checkoutservice-removemyntcredit-schema.txt");
			List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(removeMyntCreditResponse,
					jsonschema);
			AssertJUnit.assertTrue(
					missingNodeList + " nodes are missing in CheckoutService removeMyntCredit API response",
					CollectionUtils.isEmpty(missingNodeList));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test(groups = { "SchemaValidation",
			"CartAPIsSchemaValidation" }, dataProvider = "CartServiceDP_applyCouponsDataProvider_verifyResponseDataNodesUsingSchemaValidations")
	public void CartServiceTests_applyCoupon_verifyResponseDataNodesUsingSchemaValidations(String userName,
			String password, String successRespCode) {
		RequestGenerator fetchAllCouponsReqGen = checkoutTestsHelper.fetchAllCoupons(userName, password);
		System.out.println("\nPrinting CheckoutService fetchAllCoupons API response :\n\n"
				+ fetchAllCouponsReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService fetchAllCoupons API response :\n\n"
				+ fetchAllCouponsReqGen.respvalidate.returnresponseasstring());

		AssertJUnit.assertTrue("Error while invoking CheckoutService fetchAllCoupons API",
				fetchAllCouponsReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
						.contains(SUCCESS_STATUS_TYPE));

		RequestGenerator applyCouponReqGen = checkoutTestsHelper.applyCoupon(userName, password,
				fetchAllCouponsReqGen.respvalidate.NodeValue("data.coupon", false));
		String applyCouponResponse = applyCouponReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting CheckoutService applyCoupon API response :\n\n" + applyCouponResponse);
		log.info("\nPrinting CheckoutService applyCoupon API response :\n\n" + applyCouponResponse);

		AssertJUnit.assertEquals("CheckoutService applyCoupon API is not working", Integer.parseInt(successRespCode),
				applyCouponReqGen.response.getStatus());

		try {
			String jsonschema = new Toolbox()
					.readFileAsString("Data/SchemaSet/JSON/checkoutservice-applycoupon-schema.txt");
			List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(applyCouponResponse,
					jsonschema);
			AssertJUnit.assertTrue(missingNodeList + " nodes are missing in CheckoutService applyCoupon API response",
					CollectionUtils.isEmpty(missingNodeList));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test(groups = {
			"SchemaValidation" }, dataProvider = "CartServiceDP_fetchAllCouponsDataProvider_verifyResponseDataNodesUsingSchemaValidations")
	public void CartServiceTests_fetchAllCoupons_verifyResponseDataNodesUsingSchemaValidations(String userName,
			String password, String successRespCode) {
		RequestGenerator fetchAllCouponsReqGen = checkoutTestsHelper.fetchAllCoupons(userName, password);
		String fetchAllCouponsResponse = fetchAllCouponsReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting CheckoutService fetchAllCoupons API response :\n\n" + fetchAllCouponsResponse);
		log.info("\nPrinting CheckoutService fetchAllCoupons API response :\n\n" + fetchAllCouponsResponse);

		AssertJUnit.assertEquals("CheckoutService fetchAllCoupons API is not working",
				Integer.parseInt(successRespCode), fetchAllCouponsReqGen.response.getStatus());

		try {
			String jsonschema = new Toolbox()
					.readFileAsString("Data/SchemaSet/JSON/checkoutservice-fetchallcoupons-schema.txt");
			List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(fetchAllCouponsResponse,
					jsonschema);
			AssertJUnit.assertTrue(
					missingNodeList + " nodes are missing in CheckoutService fetchAllCoupons API response",
					CollectionUtils.isEmpty(missingNodeList));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/* author : Shrinkhala */

	/*
	 * @Test(groups = { "" }, dataProvider=
	 * "CartServiceTests_applyNegativeGiftcharge_verifyFailureResponse") public
	 * void
	 * CartServiceTests_applyNegativeGiftcharge_verifyFailureResponse(String
	 * userName, String password, String failureRespCode) throws
	 * InterruptedException { userName ="testing421@myntra.com"; password
	 * ="123456"; failureRespCode ="200"; Connection con = null; //fetch cart
	 * and add item to cart long startTime =
	 * Calendar.getInstance().getTimeInMillis(); RequestGenerator
	 * fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName,
	 * password); System.out.println(
	 * "\nPrinting CheckoutService operationFetchCart API response :\n\n"
	 * +fetchCartReqGen.respvalidate.returnresponseasstring()); log.info(
	 * "\nPrinting CheckoutService operationFetchCart API response :\n\n"
	 * +fetchCartReqGen.respvalidate.returnresponseasstring());
	 * 
	 * AssertJUnit.assertTrue(
	 * "Error while invoking CheckoutService operationFetchCart API",
	 * fetchCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString()
	 * , true).contains(SUCCESS_STATUS_TYPE));
	 * 
	 * if(fetchCartReqGen.respvalidate.NodeValue(CartNodes.CART_TOTAL_COUNT.
	 * toString(), true).equals(EMPTY_VALUE)) { List<Integer> styleIdList =
	 * checkoutTestsHelper.performSeachServiceToGetStyleIds(AC_SEARCH_PROD,
	 * AC_SEARCH_QTY, AC_SEARCH_RET_DOCS, AC_SEARCH_IS_FACET);
	 * 
	 * if(!CollectionUtils.isEmpty(styleIdList)) { for(Integer styleId :
	 * styleIdList) { System.out.println("\nPrinting the StyleId : "+styleId);
	 * log.info("\nPrinting the StyleId : "+styleId);
	 * 
	 * RequestGenerator getStyleDataReqGen =
	 * checkoutTestsHelper.performGetStyleDataOperation(String.valueOf(styleId))
	 * ; System.out.println(
	 * "\nPrinting StyleService getStyleData API response :\n\n"
	 * +getStyleDataReqGen.respvalidate.returnresponseasstring()); log.info(
	 * "\nPrinting StyleService getStyleData API response :\n\n"
	 * +getStyleDataReqGen.respvalidate.returnresponseasstring());
	 * 
	 * AssertJUnit.assertTrue("Error while getting style data",
	 * getStyleDataReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.
	 * toString(), true).contains(SUCCESS_STATUS_TYPE));
	 * 
	 * String response =
	 * getStyleDataReqGen.respvalidate.returnresponseasstring(); List<Integer>
	 * ids = JsonPath.read(response, "$.data..styleOptions[*].id");
	 * 
	 * for(int i = 0 ; i < ids.size(); i++) { String skuId =
	 * JsonPath.read(response, "$.data..styleOptions["+i+"].skuId").toString();
	 * String inventoryCount = JsonPath.read(response,
	 * "$.data..styleOptions["+i+"].inventoryCount").toString(); String
	 * available = JsonPath.read(response,
	 * "$.data..styleOptions["+i+"].available").toString();
	 * 
	 * if(!inventoryCount.equals(EMPTY_VALUE) && available.equals(TRUE_VALUE)) {
	 * fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName,
	 * password); System.out.println(
	 * "\nPrinting CheckoutService operationFetchCart API response :\n\n"
	 * +fetchCartReqGen.respvalidate.returnresponseasstring()); log.info(
	 * "\nPrinting CheckoutService operationFetchCart API response :\n\n"
	 * +fetchCartReqGen.respvalidate.returnresponseasstring());
	 * 
	 * if(StringUtils.isBlank(checkoutTestsHelper.getItemIdFromSKUId(
	 * fetchCartReqGen, skuId, CheckoutDataProviderEnum.CART.name()))) {
	 * RequestGenerator addItemToCartReqGen =
	 * checkoutTestsHelper.addItemToCart(userName, password, skuId,
	 * inventoryCount, ADD_OPERATION); System.out.println(
	 * "\nPrinting CheckoutService addItemToCart API response :\n\n"
	 * +addItemToCartReqGen.respvalidate.returnresponseasstring()); log.info(
	 * "\nPrinting CheckoutService addItemToCart API response :\n\n"
	 * +addItemToCartReqGen.respvalidate.returnresponseasstring());
	 * 
	 * AssertJUnit.assertTrue(
	 * "Error while invoking CheckoutService addItemToCart API",
	 * addItemToCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.
	 * toString(), true).contains(SUCCESS_STATUS_TYPE));
	 * 
	 * System.out.println("\n"+skuId+
	 * " is available and it doesn't exists in cart so added to cart");
	 * log.info("\n"+skuId+
	 * " is available and it doesn't exists in cart so added to cart"); break;
	 * 
	 * } else { System.out.println("\n"+skuId +
	 * " is not added because this item already exists in cart");
	 * log.error("\n"+skuId +
	 * " is not added because this item already exists in cart"); }
	 * 
	 * } else { System.out.println("\n"+skuId+
	 * " is not added because this item is currently out of stock");
	 * log.info("\n"+skuId+
	 * " is not added because this item is currently out of stock"); } } }
	 * 
	 * } else { System.out.println(
	 * "\nError while invoking SearchService getStyleData API"); log.error(
	 * "\nError while invoking SearchService getStyleData API");
	 * AssertJUnit.fail("Error while invoking SearchService getStyleData API");
	 * }
	 * 
	 * fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName,
	 * password); System.out.println(
	 * "\nPrinting CheckoutService operationFetchCart API response :\n\n"
	 * +fetchCartReqGen.respvalidate.returnresponseasstring()); log.info(
	 * "\nPrinting CheckoutService operationFetchCart API response :\n\n"
	 * +fetchCartReqGen.respvalidate.returnresponseasstring());
	 * 
	 * AssertJUnit.assertTrue(
	 * "Error while invoking CheckoutService operationFetchCart API",
	 * fetchCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString()
	 * , true).contains(SUCCESS_STATUS_TYPE));
	 * 
	 * } else
	 * if(fetchCartReqGen.respvalidate.NodeValue(CartNodes.CART_IS_GIFT_ORDER.
	 * toString(), true).equals(TRUE_VALUE)) { RequestGenerator
	 * removeGiftWrapAndMsgReqGen =
	 * checkoutTestsHelper.removeGiftWrapAndMessage(userName, password);
	 * System.out.println(
	 * "\nPrinting CheckoutService removeGiftWrapAndMessage API response :\n\n"
	 * +removeGiftWrapAndMsgReqGen.respvalidate.returnresponseasstring());
	 * log.info(
	 * "\nPrinting CheckoutService removeGiftWrapAndMessage API response :\n\n"
	 * +removeGiftWrapAndMsgReqGen.respvalidate.returnresponseasstring());
	 * 
	 * AssertJUnit.assertTrue(
	 * "Error while invoking CheckoutService removeGiftWrapAndMessage API",
	 * removeGiftWrapAndMsgReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE
	 * .toString(), true).contains(SUCCESS_STATUS_TYPE)); }
	 * 
	 * //modify giftwrap.charges value to -2 try {
	 * Class.forName("com.mysql.jdbc.Driver"); con =
	 * DriverManager.getConnection("jdbc:mysql://54.251.205.116:3306/myntra",
	 * "myntraAppDBUser","9eguCrustuBR"); String query =
	 * "update `mk_widget_key_value_pairs` set `value` = ? where `key` = ?";
	 * 
	 * PreparedStatement ps = con.prepareStatement(query); ps.setString(1,
	 * "-2"); ps.setString(2, "giftwrap.charges");
	 * 
	 * int i = ps.executeUpdate(); if(i>0) System.out.println(
	 * "Gift wrap charges updated to -2 in db"); else System.out.println(
	 * "cant updated value of giftwarp charges in db"); }catch(Exception e){
	 * e.printStackTrace(); } //Thread.sleep(10*6*10000); //System.out.println(
	 * "after wait for 10 mins"); //apply giftwrap RequestGenerator
	 * addGiftWrapAndMessageReqGen =
	 * checkoutTestsHelper.addGiftWrapAndMessage(userName, password, "testing",
	 * "test -ve giftwrap charge", "test"); System.out.println(
	 * "\nPrinting CheckoutService addGiftWrapAndMessage API response :\n\n"
	 * +addGiftWrapAndMessageReqGen.respvalidate.returnresponseasstring());
	 * log.info(
	 * "\nPrinting CheckoutService addGiftWrapAndMessage API response :\n\n"
	 * +addGiftWrapAndMessageReqGen.respvalidate.returnresponseasstring());
	 * 
	 * AssertJUnit.assertEquals(
	 * "CheckoutService addGiftWrapAndMessage API is not working",
	 * Integer.parseInt(failureRespCode),
	 * addGiftWrapAndMessageReqGen.response.getStatus());
	 * 
	 * 
	 * 
	 * //fetchcart should fail as giftwrap charges are negative RequestGenerator
	 * fetchCartReqGen1 = checkoutTestsHelper.operationFetchCart(userName,
	 * password); System.out.println(
	 * "\nPrinting CheckoutService operationFetchCart API response :\n\n"
	 * +fetchCartReqGen1.respvalidate.returnresponseasstring()); log.info(
	 * "\nPrinting CheckoutService operationFetchCart API response :\n\n"
	 * +fetchCartReqGen1.respvalidate.returnresponseasstring());
	 * 
	 * AssertJUnit.assertEquals(
	 * "CheckoutService operationFetchCart API is not working",
	 * Integer.parseInt(failureRespCode),
	 * fetchCartReqGen1.response.getStatus());
	 * 
	 * 
	 * //update giftwrap.charges back to 25 try {
	 * Class.forName("com.mysql.jdbc.Driver"); con =
	 * DriverManager.getConnection("jdbc:mysql://54.251.205.116:3306/myntra",
	 * "myntraAppDBUser","9eguCrustuBR"); String query =
	 * "update `mk_widget_key_value_pairs` set `value` = ? where `key` = ?";
	 * 
	 * PreparedStatement ps = con.prepareStatement(query); ps.setString(1,
	 * "25"); ps.setString(2, "giftwrap.charges");
	 * 
	 * int i = ps.executeUpdate(); if(i>0) System.out.println(
	 * "Gift wrap charges updated to 25 in db"); else System.out.println(
	 * "cant updated value of giftwarp charges in db"); }catch(Exception e){
	 * e.printStackTrace(); }
	 * 
	 * long endTime = Calendar.getInstance().getTimeInMillis(); double timeTaken
	 * = (endTime - startTime)/1000.0;
	 * 
	 * System.out.println(
	 * "\nTime taken to execute - TestCase - CheckoutServiceTests_operationFetchCart_verifySuccessResponse : "
	 * +timeTaken+" seconds\n"); log.info(
	 * "\nTime taken to execute - TestCase - CheckoutServiceTests_operationFetchCart_verifySuccessResponse : "
	 * +timeTaken+" seconds\n");
	 * 
	 * 
	 * }
	 */
	/*
	 * @Test(groups = { "" }, dataProvider=
	 * "CartServiceTests_applyNegativeCashback_verifyFailureResponse") public
	 * void CartServiceTests_applyNegativeCashback_verifyFailureResponse(String
	 * userName, String password, String addItemQuantity, String
	 * failureRespCode) { Connection con = null; String successRespCode ="200";
	 * long startTime = Calendar.getInstance().getTimeInMillis();
	 * 
	 * 
	 * //fetch cart and add item to cart if empty RequestGenerator
	 * fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName,
	 * password); System.out.println(
	 * "\nPrinting CheckoutService operationFetchCart API response :\n\n"
	 * +fetchCartReqGen.respvalidate.returnresponseasstring()); log.info(
	 * "\nPrinting CheckoutService operationFetchCart API response :\n\n"
	 * +fetchCartReqGen.respvalidate.returnresponseasstring());
	 * 
	 * AssertJUnit.assertTrue(
	 * "Error while invoking CheckoutService operationFetchCart API",
	 * fetchCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString()
	 * , true).contains(SUCCESS_STATUS_TYPE));
	 * 
	 * if(fetchCartReqGen.respvalidate.NodeValue(CartNodes.CART_TOTAL_COUNT.
	 * toString(), true).equals(EMPTY_VALUE)) { List<Integer> styleIdList =
	 * checkoutTestsHelper.performSeachServiceToGetStyleIds(AC_SEARCH_PROD,
	 * AC_SEARCH_QTY, AC_SEARCH_RET_DOCS, AC_SEARCH_IS_FACET);
	 * 
	 * if(!CollectionUtils.isEmpty(styleIdList)) { for(Integer styleId :
	 * styleIdList) { System.out.println("\nPrinting the StyleId : "+styleId);
	 * log.info("\nPrinting the StyleId : "+styleId);
	 * 
	 * RequestGenerator getStyleDataReqGen =
	 * checkoutTestsHelper.performGetStyleDataOperation(String.valueOf(styleId))
	 * ; System.out.println(
	 * "\nPrinting StyleService getStyleData API response :\n\n"
	 * +getStyleDataReqGen.respvalidate.returnresponseasstring()); log.info(
	 * "\nPrinting StyleService getStyleData API response :\n\n"
	 * +getStyleDataReqGen.respvalidate.returnresponseasstring());
	 * 
	 * AssertJUnit.assertTrue("Error while getting style data",
	 * getStyleDataReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.
	 * toString(), true).contains(SUCCESS_STATUS_TYPE));
	 * 
	 * String response =
	 * getStyleDataReqGen.respvalidate.returnresponseasstring(); List<Integer>
	 * ids = JsonPath.read(response, "$.data..styleOptions[*].id");
	 * 
	 * for(int i = 0 ; i < ids.size(); i++) { String skuId =
	 * JsonPath.read(response, "$.data..styleOptions["+i+"].skuId").toString();
	 * String inventoryCount = JsonPath.read(response,
	 * "$.data..styleOptions["+i+"].inventoryCount").toString(); String
	 * available = JsonPath.read(response,
	 * "$.data..styleOptions["+i+"].available").toString();
	 * 
	 * if(!inventoryCount.equals(EMPTY_VALUE) && available.equals(TRUE_VALUE)) {
	 * fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName,
	 * password); System.out.println(
	 * "\nPrinting CheckoutService operationFetchCart API response :\n\n"
	 * +fetchCartReqGen.respvalidate.returnresponseasstring()); log.info(
	 * "\nPrinting CheckoutService operationFetchCart API response :\n\n"
	 * +fetchCartReqGen.respvalidate.returnresponseasstring());
	 * 
	 * if(StringUtils.isBlank(checkoutTestsHelper.getItemIdFromSKUId(
	 * fetchCartReqGen, skuId, CheckoutDataProviderEnum.CART.name()))) {
	 * RequestGenerator addItemToCartReqGen =
	 * checkoutTestsHelper.addItemToCart(userName, password, skuId,
	 * String.valueOf(addItemQuantity), ADD_OPERATION); System.out.println(
	 * "\nPrinting CheckoutService addItemToCart API response :\n\n"
	 * +addItemToCartReqGen.respvalidate.returnresponseasstring()); log.info(
	 * "\nPrinting CheckoutService addItemToCart API response :\n\n"
	 * +addItemToCartReqGen.respvalidate.returnresponseasstring());
	 * 
	 * AssertJUnit.assertTrue(
	 * "Error while invoking CheckoutService addItemToCart API",
	 * addItemToCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.
	 * toString(), true).contains(SUCCESS_STATUS_TYPE));
	 * 
	 * System.out.println("\n"+skuId+
	 * " is available and it doesn't exists in cart so added to cart");
	 * log.info("\n"+skuId+
	 * " is available and it doesn't exists in cart so added to cart"); break;
	 * 
	 * } else { System.out.println("\n"+skuId +
	 * " is not added because this item already exists in cart");
	 * log.error("\n"+skuId +
	 * " is not added because this item already exists in cart"); }
	 * 
	 * } else { System.out.println("\n"+skuId+
	 * " is not added because this item is currently out of stock");
	 * log.info("\n"+skuId+
	 * " is not added because this item is currently out of stock"); } } }
	 * 
	 * } else { System.out.println(
	 * "\nError while invoking SearchService getStyleData API"); log.error(
	 * "\nError while invoking SearchService getStyleData API");
	 * AssertJUnit.fail("Error while invoking SearchService getStyleData API");
	 * }
	 * 
	 * fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName,
	 * password); System.out.println(
	 * "\nPrinting CheckoutService operationFetchCart API response :\n\n"
	 * +fetchCartReqGen.respvalidate.returnresponseasstring()); log.info(
	 * "\nPrinting CheckoutService operationFetchCart API response :\n\n"
	 * +fetchCartReqGen.respvalidate.returnresponseasstring());
	 * 
	 * AssertJUnit.assertTrue(
	 * "Error while invoking CheckoutService operationFetchCart API",
	 * fetchCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString()
	 * , true).contains(SUCCESS_STATUS_TYPE));
	 * 
	 * } //recache user MyntraService recacheUser = new
	 * MyntraService(ServiceType.PORTAL_MYNTCASHSERVICE,
	 * APINAME.RECACHEUSER,init.Configurations, PayloadType.JSON, new
	 * String[]{userName}, PayloadType.JSON); RequestGenerator req = new
	 * RequestGenerator(recacheUser); log.info(recacheUser.URL);
	 * System.out.println("recache api"
	 * +req.respvalidate.returnresponseasstring());
	 * 
	 * 
	 * //get user's cashback RequestGenerator getMyntCreditReqGen =
	 * checkoutTestsHelper.getMyntCredit(userName, password);
	 * System.out.println(
	 * "\nPrinting CheckoutService getMyntCredit API response :\n\n"
	 * +getMyntCreditReqGen.respvalidate.returnresponseasstring()); log.info(
	 * "\nPrinting getMyntCredit API response :\n\n"
	 * +getMyntCreditReqGen.respvalidate.returnresponseasstring());
	 * System.out.println("balance in user's cashback : "
	 * +getMyntCreditReqGen.respvalidate.NodeValue("data.balance", true));
	 * 
	 * String useAmount =
	 * getMyntCreditReqGen.respvalidate.NodeValue("data.balance",true);
	 * System.out.println("balance in user's cashback"+useAmount);
	 * 
	 * //apply cashback RequestGenerator applyMyntCreditReqGen =
	 * checkoutTestsHelper.applyMyntCredit(userName, password, useAmount);
	 * System.out.println(
	 * "\nPrinting CheckoutService applyMyntCredit API response :::\n\n"
	 * +applyMyntCreditReqGen.respvalidate.returnresponseasstring()); log.info(
	 * "\nPrinting CheckoutService applyMyntCredit API response :\n\n"
	 * +applyMyntCreditReqGen.respvalidate.returnresponseasstring());
	 * 
	 * AssertJUnit.assertEquals(
	 * "CheckoutService applyMyntCredit API is not working",
	 * Integer.parseInt(successRespCode),
	 * applyMyntCreditReqGen.response.getStatus()); RequestGenerator
	 * fetchCartReqGen1 = checkoutTestsHelper.operationFetchCart(userName,
	 * password); System.out.println(
	 * "\nAfter applying cashback:::Printing CheckoutService operationFetchCart API response :\n\n"
	 * +fetchCartReqGen1.respvalidate.returnresponseasstring()); log.info(
	 * "\nPrinting CheckoutService operationFetchCart API response :\n\n"
	 * +fetchCartReqGen1.respvalidate.returnresponseasstring());
	 * 
	 * AssertJUnit.assertTrue(
	 * "Error while invoking CheckoutService operationFetchCart API",
	 * fetchCartReqGen1.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(
	 * ), true).contains(SUCCESS_STATUS_TYPE));
	 * 
	 * 
	 * //modify cashback value to -200 try {
	 * Class.forName("com.mysql.jdbc.Driver"); con =
	 * DriverManager.getConnection("jdbc:mysql://54.251.205.116:3306/myntra",
	 * "myntraAppDBUser","9eguCrustuBR"); String query =
	 * "update cashback_account set `balance` = ? where `login` = ? and `account_type` = ?"
	 * ;
	 * 
	 * PreparedStatement ps = con.prepareStatement(query); ps.setString(1,
	 * "-200"); ps.setString(2, userName); ps.setString(3, "1");
	 * 
	 * int i = ps.executeUpdate(); if(i>0) System.out.println(
	 * " User's cashback updated to -200 in db"); else System.out.println(
	 * "cant updated value of user's cashback in db"); }catch(Exception e){
	 * e.printStackTrace(); }
	 * 
	 * //fetchcart MyntraService service1 = new
	 * MyntraService(ServiceType.PORTAL_CART, APINAME.OPERATIONFETCHCART,
	 * init.Configurations); System.out.println(
	 * "\ntesting:::Printing CheckoutService operationFetchCart API URL : "
	 * +service1.URL); log.info(
	 * "\nPrinting CheckoutService operationFetchCart API URL : "+service1.URL);
	 * 
	 * //fetchcart should fail as cashback charges are negative RequestGenerator
	 * fetchCartReqGen2 = checkoutTestsHelper.operationFetchCart(userName,
	 * password); System.out.println(
	 * "\nPrinting CheckoutService operationFetchCart API response :\n\n"
	 * +fetchCartReqGen2.respvalidate.returnresponseasstring()); log.info(
	 * "\nPrinting CheckoutService operationFetchCart API response :\n\n"
	 * +fetchCartReqGen2.respvalidate.returnresponseasstring());
	 * 
	 * AssertJUnit.assertEquals(
	 * " 2 fetch cart CheckoutService operationFetchCart API is not working",
	 * Integer.parseInt(failureRespCode),
	 * fetchCartReqGen2.response.getStatus());
	 * if(fetchCartReqGen2.response.getStatus()==500) System.out.println(
	 * "Fetch Cart API is Failing, as expected");
	 * 
	 * //update cashback back to 200 try {
	 * Class.forName("com.mysql.jdbc.Driver"); con =
	 * DriverManager.getConnection("jdbc:mysql://54.251.205.116:3306/myntra",
	 * "myntraAppDBUser","9eguCrustuBR"); String query =
	 * "update cashback_account set `balance` = ? where `login` = ? and `account_type` = ?"
	 * ;
	 * 
	 * PreparedStatement ps = con.prepareStatement(query); ps.setString(1,
	 * "200"); ps.setString(2, userName); ps.setString(3, "1");
	 * 
	 * int i = ps.executeUpdate(); if(i>0) System.out.println(
	 * "user's cashback updated back to 200 in db"); else System.out.println(
	 * "cant updated value of user's cashback back to 200 in db");
	 * }catch(Exception e){ e.printStackTrace(); }
	 * 
	 * long endTime = Calendar.getInstance().getTimeInMillis(); double timeTaken
	 * = (endTime - startTime)/1000.0;
	 * 
	 * System.out.println(
	 * "\nTime taken to execute - TestCase - CheckoutServiceTests_operationFetchCart_verifySuccessResponse : "
	 * +timeTaken+" seconds\n"); log.info(
	 * "\nTime taken to execute - TestCase - CheckoutServiceTests_operationFetchCart_verifySuccessResponse : "
	 * +timeTaken+" seconds\n");
	 * 
	 * 
	 * }
	 */
	@Test(groups = { "" }, dataProvider = "CartServiceTests_apply120PerCoupons_verifyCartFailureResponse")
	public void CartServiceTests_apply120PerCoupons_verifyCartFailureResponse(String userName, String password,
			String successRespCode, String addItemQty, String failureRespCode) {
		Connection con = null;
		// fetch cart and add item to cart
		long startTime = Calendar.getInstance().getTimeInMillis();
		long currentTime = System.currentTimeMillis() / 1000;
		String startDate = String.valueOf(currentTime);
		String lastEdited = startDate;
		String CouponName = "test120coupon";

		// fetch cart and add item to cart if empty
		RequestGenerator fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
		System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"
				+ fetchCartReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"
				+ fetchCartReqGen.respvalidate.returnresponseasstring());

		AssertJUnit.assertTrue("Error while invoking CheckoutService operationFetchCart API",
				fetchCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
						.contains(SUCCESS_STATUS_TYPE));

		if (fetchCartReqGen.respvalidate.NodeValue(CartNodes.CART_TOTAL_COUNT.toString(), true).equals(EMPTY_VALUE)) {
			List<Integer> styleIdList = checkoutTestsHelper.performSeachServiceToGetStyleIds(AC_SEARCH_PROD,
					AC_SEARCH_QTY, AC_SEARCH_RET_DOCS, AC_SEARCH_IS_FACET);

			if (!CollectionUtils.isEmpty(styleIdList)) {
				for (Integer styleId : styleIdList) {
					System.out.println("\nPrinting the StyleId : " + styleId);
					log.info("\nPrinting the StyleId : " + styleId);

					RequestGenerator getStyleDataReqGen = checkoutTestsHelper
							.performGetStyleDataOperation(String.valueOf(styleId));
					System.out.println("\nPrinting StyleService getStyleData API response :\n\n"
							+ getStyleDataReqGen.respvalidate.returnresponseasstring());
					log.info("\nPrinting StyleService getStyleData API response :\n\n"
							+ getStyleDataReqGen.respvalidate.returnresponseasstring());

					AssertJUnit.assertTrue("Error while getting style data", getStyleDataReqGen.respvalidate
							.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));

					String response = getStyleDataReqGen.respvalidate.returnresponseasstring();
					List<Integer> ids = JsonPath.read(response, "$.data..styleOptions[*].id");

					for (int i = 0; i < ids.size(); i++) {
						String skuId = JsonPath.read(response, "$.data..styleOptions[" + i + "].skuId").toString();
						String inventoryCount = JsonPath
								.read(response, "$.data..styleOptions[" + i + "].inventoryCount").toString();
						String available = JsonPath.read(response, "$.data..styleOptions[" + i + "].available")
								.toString();

						if (!inventoryCount.equals(EMPTY_VALUE) && available.equals(TRUE_VALUE)) {
							fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
							System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"
									+ fetchCartReqGen.respvalidate.returnresponseasstring());
							log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"
									+ fetchCartReqGen.respvalidate.returnresponseasstring());

							if (StringUtils.isBlank(checkoutTestsHelper.getItemIdFromSKUId(fetchCartReqGen, skuId,
									CheckoutDataProviderEnum.CART.name()))) {
								RequestGenerator addItemToCartReqGen = checkoutTestsHelper.addItemToCart(userName,
										password, skuId, inventoryCount, ADD_OPERATION);
								System.out.println("\nPrinting CheckoutService addItemToCart API response :\n\n"
										+ addItemToCartReqGen.respvalidate.returnresponseasstring());
								log.info("\nPrinting CheckoutService addItemToCart API response :\n\n"
										+ addItemToCartReqGen.respvalidate.returnresponseasstring());

								AssertJUnit.assertTrue("Error while invoking CheckoutService addItemToCart API",
										addItemToCartReqGen.respvalidate
												.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
												.contains(SUCCESS_STATUS_TYPE));

								System.out.println(
										"\n" + skuId + " is available and it doesn't exists in cart so added to cart");
								log.info("\n" + skuId + " is available and it doesn't exists in cart so added to cart");
								break;

							} else {
								System.out.println(
										"\n" + skuId + " is not added because this item already exists in cart");
								log.error("\n" + skuId + " is not added because this item already exists in cart");
							}

						} else {
							System.out.println(
									"\n" + skuId + " is not added because this item is currently out of stock");
							log.info("\n" + skuId + " is not added because this item is currently out of stock");
						}
					}
				}

			} else {
				System.out.println("\nError while invoking SearchService getStyleData API");
				log.error("\nError while invoking SearchService getStyleData API");
				AssertJUnit.fail("Error while invoking SearchService getStyleData API");
			}

			fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
			System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"
					+ fetchCartReqGen.respvalidate.returnresponseasstring());
			log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"
					+ fetchCartReqGen.respvalidate.returnresponseasstring());

			AssertJUnit.assertTrue("Error while invoking CheckoutService operationFetchCart API",
					fetchCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
							.contains(SUCCESS_STATUS_TYPE));

		}
		// modify
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://54.251.205.116:3306/myntra", "myntraAppDBUser",
					"9eguCrustuBR");
			String query = "update `mk_widget_key_value_pairs` set `value` = ? where `key` = ?";

			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, "120");
			ps.setString(2, "Global-Product-Level-Cap");

			int i = ps.executeUpdate();
			if (i > 0)
				System.out.println("Global-Product-Level-Cap updated to 120 in db");
			else
				System.out.println("cant updated value of  in db");
		} catch (Exception e) {
			e.printStackTrace();
		}

		// modify coupon to 120%
		MyntraService service1 = Myntra.getService(ServiceType.PORTAL_COUPONSERVICE, APINAME.MODIFYPERCENTAGECOUPON,
				init.Configurations, new String[] { startDate, lastEdited, CouponName, "120", userName },
				PayloadType.JSON, PayloadType.JSON);
		System.out.println("json paylod---- >" + service1.Payload);
		RequestGenerator req1 = new RequestGenerator(service1);

		System.out.println(req1.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req1.response.getStatus());

		// apply coupon
		String couponId = "test120coupon";
		MyntraService service2 = Myntra.getService(ServiceType.PORTAL_CART, APINAME.APPLYCOUPON, init.Configurations,
				new String[] { couponId });
		System.out.println("\nPrinting CheckoutService applyCoupon API URL : " + service2.URL);
		log.info("\nPrinting CheckoutService applyCoupon API URL : " + service2.URL);

		System.out.println("\nPrinting CheckoutService applyCoupon API Payload : \n\n" + service2.Payload);
		log.info("\nPrinting CheckoutService applyCoupon API Payload : \n\n" + service2.Payload);

		// fetch cart should be failed as applied coupon is of 120%
		RequestGenerator fetchCartReqGen1 = checkoutTestsHelper.operationFetchCart(userName, password);
		System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"
				+ fetchCartReqGen1.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"
				+ fetchCartReqGen1.respvalidate.returnresponseasstring());

		AssertJUnit.assertEquals("CheckoutService operationFetchCart API is not working",
				Integer.parseInt(failureRespCode), fetchCartReqGen1.response.getStatus());

		if (fetchCartReqGen1.response.getStatus() == 500)
			System.out.println("Fetch cart api is not working as expected, as MRP of item < 0");
		else
			System.out.println("Error:Cart should not get loaded");

		//
		// modify
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://54.251.205.116:3306/myntra", "myntraAppDBUser",
					"9eguCrustuBR");
			String query = "update `mk_widget_key_value_pairs` set `value` = ? where `key` = ?";

			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, "100");
			ps.setString(2, "Global-Product-Level-Cap");

			int i = ps.executeUpdate();
			if (i > 0)
				System.out.println("Global-Product-Level-Cap updated to 100 in db");
			else
				System.out.println("cant updated value of  in db");
		} catch (Exception e) {
			e.printStackTrace();
		}

		// modifying coupon to 30%
		MyntraService service = Myntra.getService(ServiceType.PORTAL_COUPONSERVICE, APINAME.MODIFYPERCENTAGECOUPON,
				init.Configurations, new String[] { startDate, lastEdited, CouponName, "30", userName },
				PayloadType.JSON, PayloadType.JSON);
		System.out.println("json paylod---- >" + service.Payload);
		RequestGenerator req = new RequestGenerator(service);

		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());
		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;

		System.out.println(
				"\nTime taken to execute - TestCase - CheckoutServiceTests_operationFetchCart_verifySuccessResponse : "
						+ timeTaken + " seconds\n");
		log.info("\nTime taken to execute - TestCase - CheckoutServiceTests_operationFetchCart_verifySuccessResponse : "
				+ timeTaken + " seconds\n");

	}
/*
	@Test(groups = { "Sanity", "Regression", "ExhaustiveRegression",
			"PPS" }, dataProvider = "CartServiceTests_addSingleItemToCart_VerifyNodesPPSDP", description = "1. hit IDP service and login the user. "
					+ "\n 2. add items to cart(\n a.hit Search Service and extract SkuIDs for given styleId"
					+ "\n b. hit addItemToCart API giving skuId which doesnt exists in cart)"
					+ "\n 3. hit fetchCart API \n 4.Check the service response must be 200"
					+ "\n 4. Verify node values for added item as part of PPS changes")
	public void CartServiceTests_addSingleItemToCart_VerifyNodesPPS(String userName, String password, String addItemQty,
			String addOperation, String styleId, String vendorType, String expectedValue, String successRespCode) {
		long startTime = Calendar.getInstance().getTimeInMillis();

		RequestGenerator clearCartReqGen = checkoutTestsHelper.clearCart(userName, password);
		String clearCartResponse = clearCartReqGen.respvalidate.returnresponseasstring();
		// System.out.println("\nPrinting CheckoutService clearCart API response
		// :\n\n"+clearCartResponse);
		// log.info("\nPrinting CheckoutService clearCart API response
		// :\n\n"+clearCartResponse);

		AssertJUnit.assertEquals("CheckoutService clearCart API is not working", Integer.parseInt(successRespCode),
				clearCartReqGen.response.getStatus());

		System.out.println("\nPrinting the StyleId : " + styleId);
		log.info("\nPrinting the StyleId : " + styleId);

		RequestGenerator getStyleDataReqGen = checkoutTestsHelper.performGetStyleDataOperation(String.valueOf(styleId));
		// System.out.println("\nPrinting StyleService getStyleData API response
		// :\n\n"+getStyleDataReqGen.respvalidate.returnresponseasstring());
		// log.info("\nPrinting StyleService getStyleData API response
		// :\n\n"+getStyleDataReqGen.respvalidate.returnresponseasstring());

		AssertJUnit.assertTrue("Error while getting style data", getStyleDataReqGen.respvalidate
				.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));

		String response = getStyleDataReqGen.respvalidate.returnresponseasstring();
		List<Integer> ids = JsonPath.read(response, "$.data..styleOptions[*].id");

		for (int i = 0; i < ids.size(); i++) {
			String skuId = JsonPath.read(response, "$.data..styleOptions[" + i + "].skuId").toString();
			String inventoryCount = JsonPath.read(response, "$.data..styleOptions[" + i + "].inventoryCount")
					.toString();
			String available = JsonPath.read(response, "$.data..styleOptions[" + i + "].available").toString();

			if (!inventoryCount.equals(EMPTY_VALUE) && available.equals(TRUE_VALUE)) {
				int invCount = Integer.parseInt(inventoryCount);
				int qty = Integer.parseInt(addItemQty);
				int itemQtyToAdd = invCount <= qty ? invCount : qty;

				RequestGenerator addItemToCartReqGen = checkoutTestsHelper.addItemToCart(userName, password, skuId,
						String.valueOf(itemQtyToAdd), addOperation);
				// System.out.println("\nPrinting CheckoutService addItemToCart
				// API response
				// :\n\n"+addItemToCartReqGen.respvalidate.returnresponseasstring());
				// log.info("\nPrinting CheckoutService addItemToCart API
				// response
				// :\n\n"+addItemToCartReqGen.respvalidate.returnresponseasstring());

				AssertJUnit.assertEquals("CheckoutService addItemToCart API is not working",
						Integer.parseInt(successRespCode), addItemToCartReqGen.response.getStatus());

				System.out.println("\n" + skuId + " is available and it doesn't exists in cart so added to cart");
				log.info("\n" + skuId + " is available and it doesn't exists in cart so added to cart");
				break;

			} else {
				System.out.println("\n" + skuId + " is not added because this item is currently out of stock");
				log.info("\n" + skuId + " is not added because this item is currently out of stock");
			}

		}

		RequestGenerator fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
		System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"
				+ fetchCartReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"
				+ fetchCartReqGen.respvalidate.returnresponseasstring());

		AssertJUnit.assertTrue("Error while invoking CheckoutService operationFetchCart API",
				fetchCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
						.contains(SUCCESS_STATUS_TYPE));

		AssertJUnit.assertTrue("VALUE OF WALLET_APPLICABLE IS NOT AS EXPECTED", fetchCartReqGen.respvalidate
				.NodeValue(CartNodes.CART_ITEM_WALLET_AVAILABLE.toString(), true).contains(expectedValue));

		AssertJUnit.assertTrue("VALUE OF LP_APPLICABLE IS NOT AS EXPECTED", fetchCartReqGen.respvalidate
				.NodeValue(CartNodes.CART_ITEM_LOYALTY_POINTS_APPLICABLE.toString(), true).contains(expectedValue));

		AssertJUnit.assertTrue("VALUE OF GIFTWRAP_APPLICABLE IS NOT AS EXPECTED", fetchCartReqGen.respvalidate
				.NodeValue(CartNodes.CART_ITEM_GIFTWRAP_APPLICABLE.toString(), true).contains(expectedValue));

		AssertJUnit.assertTrue("VALUE OF GIFTCARD_APPLICABLE IS NOT AS EXPECTED", fetchCartReqGen.respvalidate
				.NodeValue(CartNodes.CART_ITEM_GIFTCARD_APPLICABLE.toString(), true).contains(expectedValue));
		System.out.println("For styleid " + styleId + " which is of " + vendorType + " value of parameters is "
				+ expectedValue + " as expected");

		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;

		System.out.println(
				"\nTime taken to execute - TestCase - CheckoutServiceTests_addItemToCart_verifySuccessResponse : "
						+ timeTaken + " seconds\n");
		log.info("\nTime taken to execute - TestCase - CheckoutServiceTests_addItemToCart_verifySuccessResponse : "
				+ timeTaken + " seconds\n");

	}

	@Test(groups = { "Sanity", "Regression", "ExhaustiveRegression",
			"PPS" }, dataProvider = "CartServiceTests_addMixedCartItems_VerifyAsyncNodesPPS", description = "1. hit IDP service and login the user. "
					+ "\n 2. add items to cart one of each Vector, Seller & JIT (\n a.hit Search Service and extract SkuIDs for given styleIds"
					+ "\n b. hit addItemToCart API giving skuId which doesnt exists in cart)"
					+ "\n 3. hit fetchCart API \n 4. Verify node values of mixed cart response as part of PPS changes")
	public void CartServiceTests_addMixedCartItems_VerifyAsyncNodesPPS(String userName, String password,
			String addItemQty, String addOperation, String expectedValue, String successRespCode) {
		long startTime = Calendar.getInstance().getTimeInMillis();

		List<String> array = new ArrayList<String>();
		array.add("1530");
		array.add("346104");
		array.add("373911");

		RequestGenerator clearCartReqGen = checkoutTestsHelper.clearCart(userName, password);
		String clearCartResponse = clearCartReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting CheckoutService clearCart API response :\n\n" + clearCartResponse);
		log.info("\nPrinting CheckoutService clearCart API response :\n\n" + clearCartResponse);

		AssertJUnit.assertEquals("CheckoutService clearCart API is not working", Integer.parseInt(successRespCode),
				clearCartReqGen.response.getStatus());

		for (int k = 0; k < array.size(); k++) {
			System.out.println("first styleid is:" + array.get(k));

			RequestGenerator getStyleDataReqGen = checkoutTestsHelper
					.performGetStyleDataOperation(String.valueOf(array.get(k)));
			System.out.println("\nPrinting StyleService getStyleData API response :\n\n"
					+ getStyleDataReqGen.respvalidate.returnresponseasstring());
			log.info("\nPrinting StyleService getStyleData API response :\n\n"
					+ getStyleDataReqGen.respvalidate.returnresponseasstring());

			AssertJUnit.assertTrue("Error while getting style data", getStyleDataReqGen.respvalidate
					.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));

			String response = getStyleDataReqGen.respvalidate.returnresponseasstring();
			List<Integer> ids = JsonPath.read(response, "$.data..styleOptions[*].id");

			for (int i = 0; i < ids.size(); i++) {
				String skuId = JsonPath.read(response, "$.data..styleOptions[" + i + "].skuId").toString();
				String inventoryCount = JsonPath.read(response, "$.data..styleOptions[" + i + "].inventoryCount")
						.toString();
				String available = JsonPath.read(response, "$.data..styleOptions[" + i + "].available").toString();

				if (!inventoryCount.equals(EMPTY_VALUE) && available.equals(TRUE_VALUE)) {
					int invCount = Integer.parseInt(inventoryCount);
					int qty = Integer.parseInt(addItemQty);
					int itemQtyToAdd = invCount <= qty ? invCount : qty;

					RequestGenerator addItemToCartReqGen = checkoutTestsHelper.addItemToCart(userName, password, skuId,
							String.valueOf(itemQtyToAdd), addOperation);
					System.out.println("\nPrinting CheckoutService addItemToCart API response :\n\n"
							+ addItemToCartReqGen.respvalidate.returnresponseasstring());
					log.info("\nPrinting CheckoutService addItemToCart API response :\n\n"
							+ addItemToCartReqGen.respvalidate.returnresponseasstring());

					AssertJUnit.assertEquals("CheckoutService addItemToCart API is not working",
							Integer.parseInt(successRespCode), addItemToCartReqGen.response.getStatus());

					System.out.println("\n" + skuId + " is available and it doesn't exists in cart so added to cart");
					log.info("\n" + skuId + " is available and it doesn't exists in cart so added to cart");
					break;

				} else {
					System.out.println("\n" + skuId + " is not added because this item is currently out of stock");
					log.info("\n" + skuId + " is not added because this item is currently out of stock");
				}

			}
		}

		RequestGenerator fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
		System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"
				+ fetchCartReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"
				+ fetchCartReqGen.respvalidate.returnresponseasstring());

		AssertJUnit.assertTrue("Error while invoking CheckoutService operationFetchCart API",
				fetchCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
						.contains(SUCCESS_STATUS_TYPE));

		AssertJUnit.assertTrue("VALUE OF WALLET_APPLICABLE IS NOT AS EXPECTED", fetchCartReqGen.respvalidate
				.NodeValue(CartNodes.CART_ITEM_WALLET_AVAILABLE.toString(), true).contains(expectedValue));

		AssertJUnit.assertTrue("VALUE OF LP_APPLICABLE IS NOT AS EXPECTED", fetchCartReqGen.respvalidate
				.NodeValue(CartNodes.CART_ITEM_LOYALTY_POINTS_APPLICABLE.toString(), true).contains(expectedValue));

		AssertJUnit.assertTrue("VALUE OF GIFTWRAP_APPLICABLE IS NOT AS EXPECTED", fetchCartReqGen.respvalidate
				.NodeValue(CartNodes.CART_ITEM_GIFTWRAP_APPLICABLE.toString(), true).contains(expectedValue));

		AssertJUnit.assertTrue("VALUE OF GIFTCARD_APPLICABLE IS NOT AS EXPECTED", fetchCartReqGen.respvalidate
				.NodeValue(CartNodes.CART_ITEM_GIFTCARD_APPLICABLE.toString(), true).contains("FALSE"));

		RequestGenerator clearCartReqGen1 = checkoutTestsHelper.clearCart(userName, password);
		String clearCartResponse1 = clearCartReqGen1.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting CheckoutService clearCart API response :\n\n" + clearCartResponse1);
		log.info("\nPrinting CheckoutService clearCart API response :\n\n" + clearCartResponse1);

		AssertJUnit.assertEquals("CheckoutService clearCart API is not working", Integer.parseInt(successRespCode),
				clearCartReqGen1.response.getStatus());
		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;

		System.out.println(
				"\nTime taken to execute - TestCase - CheckoutServiceTests_addItemToCart_verifySuccessResponse : "
						+ timeTaken + " seconds\n");
		log.info("\nTime taken to execute - TestCase - CheckoutServiceTests_addItemToCart_verifySuccessResponse : "
				+ timeTaken + " seconds\n");

	}

	@Test(groups = { "Sanity", "Regression", "ExhaustiveRegression",
			"PPS" }, dataProvider = "CartServiceTests_updateItemsInMixedCart_VerifyNodesPPSDP", description = "1. hit IDP service and login the user. "
					+ "\n 2. add items to cart one of each Vector, Seller & JIT (\n a.hit Search Service and extract SkuIDs for given styleIds"
					+ "\n b. hit addItemToCart API giving skuId which doesnt exists in cart)"
					+ "\n 3. hit fetchCart API \n 4. Verify node values of mixed cart response as part of PPS changes")
	public void CartServiceTests_updateItemsInMixedCart_VerifyAsyncNodesPPS(String userName, String password,
			String addItemQty, String addOperation, String expectedValue, String successRespCode) {
		long startTime = Calendar.getInstance().getTimeInMillis();

		List<String> array = new ArrayList<String>();
		array.add("1530");
		array.add("346104");
		array.add("373911");

		RequestGenerator clearCartReqGen = checkoutTestsHelper.clearCart(userName, password);
		String clearCartResponse = clearCartReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting CheckoutService clearCart API response :\n\n" + clearCartResponse);
		log.info("\nPrinting CheckoutService clearCart API response :\n\n" + clearCartResponse);

		AssertJUnit.assertEquals("CheckoutService clearCart API is not working", Integer.parseInt(successRespCode),
				clearCartReqGen.response.getStatus());

		for (int k = 0; k < array.size(); k++) {
			System.out.println("first styleid is:" + array.get(k));

			RequestGenerator getStyleDataReqGen = checkoutTestsHelper
					.performGetStyleDataOperation(String.valueOf(array.get(k)));
			System.out.println("\nPrinting StyleService getStyleData API response :\n\n"
					+ getStyleDataReqGen.respvalidate.returnresponseasstring());
			log.info("\nPrinting StyleService getStyleData API response :\n\n"
					+ getStyleDataReqGen.respvalidate.returnresponseasstring());

			AssertJUnit.assertTrue("Error while getting style data", getStyleDataReqGen.respvalidate
					.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));

			String response = getStyleDataReqGen.respvalidate.returnresponseasstring();
			List<Integer> ids = JsonPath.read(response, "$.data..styleOptions[*].id");

			for (int i = 0; i < ids.size(); i++) {
				String skuId = JsonPath.read(response, "$.data..styleOptions[" + i + "].skuId").toString();
				String inventoryCount = JsonPath.read(response, "$.data..styleOptions[" + i + "].inventoryCount")
						.toString();
				String available = JsonPath.read(response, "$.data..styleOptions[" + i + "].available").toString();

				if (!inventoryCount.equals(EMPTY_VALUE) && available.equals(TRUE_VALUE)) {
					int invCount = Integer.parseInt(inventoryCount);
					int qty = Integer.parseInt(addItemQty);
					int itemQtyToAdd = invCount <= qty ? invCount : qty;

					RequestGenerator addItemToCartReqGen = checkoutTestsHelper.addItemToCart(userName, password, skuId,
							String.valueOf(itemQtyToAdd), addOperation);
					System.out.println("\nPrinting CheckoutService addItemToCart API response :\n\n"
							+ addItemToCartReqGen.respvalidate.returnresponseasstring());
					log.info("\nPrinting CheckoutService addItemToCart API response :\n\n"
							+ addItemToCartReqGen.respvalidate.returnresponseasstring());

					AssertJUnit.assertEquals("CheckoutService addItemToCart API is not working",
							Integer.parseInt(successRespCode), addItemToCartReqGen.response.getStatus());

					System.out.println("\n" + skuId + " is available and it doesn't exists in cart so added to cart");
					log.info("\n" + skuId + " is available and it doesn't exists in cart so added to cart");
					break;

				} else {
					System.out.println("\n" + skuId + " is not added because this item is currently out of stock");
					log.info("\n" + skuId + " is not added because this item is currently out of stock");
				}

			}
		}

		RequestGenerator fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
		System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"
				+ fetchCartReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"
				+ fetchCartReqGen.respvalidate.returnresponseasstring());

		String skuId = fetchCartReqGen.respvalidate.NodeValue(CartNodes.CART_ITEM_ENTRIES_SELSIZE_SKUID.toString(),
				false);
		String itemId = checkoutTestsHelper.getItemIdFromSKUId(fetchCartReqGen, skuId,
				CheckoutDataProviderEnum.CART.name());

		RequestGenerator updateItemInCartReqGen = checkoutTestsHelper.updateItemInCart(userName, password, itemId,
				skuId, "2", UPDATE_OPERATION);
		System.out.println("\nPrinting CheckoutService updateItemInCart API response :\n\n"
				+ updateItemInCartReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService updateItemInCart API response :\n\n"
				+ updateItemInCartReqGen.respvalidate.returnresponseasstring());

		AssertJUnit.assertEquals("CheckoutService updateItemInCart API is not working",
				Integer.parseInt(successRespCode), updateItemInCartReqGen.response.getStatus());

		RequestGenerator fetchCartReqGen1 = checkoutTestsHelper.operationFetchCart(userName, password);
		System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"
				+ fetchCartReqGen1.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"
				+ fetchCartReqGen1.respvalidate.returnresponseasstring());

		AssertJUnit.assertTrue("Error while invoking CheckoutService operationFetchCart API",
				fetchCartReqGen1.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
						.contains(SUCCESS_STATUS_TYPE));

		AssertJUnit.assertTrue("VALUE OF WALLET_APPLICABLE IS NOT AS EXPECTED", fetchCartReqGen1.respvalidate
				.NodeValue(CartNodes.CART_ITEM_WALLET_AVAILABLE.toString(), true).contains(expectedValue));

		AssertJUnit.assertTrue("VALUE OF LP_APPLICABLE IS NOT AS EXPECTED", fetchCartReqGen.respvalidate
				.NodeValue(CartNodes.CART_ITEM_LOYALTY_POINTS_APPLICABLE.toString(), true).contains(expectedValue));

		AssertJUnit.assertTrue("VALUE OF GIFTWRAP_APPLICABLE IS NOT AS EXPECTED", fetchCartReqGen1.respvalidate
				.NodeValue(CartNodes.CART_ITEM_GIFTWRAP_APPLICABLE.toString(), true).contains(expectedValue));

		AssertJUnit.assertTrue("VALUE OF GIFTCARD_APPLICABLE IS NOT AS EXPECTED", fetchCartReqGen1.respvalidate
				.NodeValue(CartNodes.CART_ITEM_GIFTCARD_APPLICABLE.toString(), true).contains("FALSE"));

		RequestGenerator clearCartReqGen1 = checkoutTestsHelper.clearCart(userName, password);
		String clearCartResponse1 = clearCartReqGen1.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting CheckoutService clearCart API response :\n\n" + clearCartResponse1);
		log.info("\nPrinting CheckoutService clearCart API response :\n\n" + clearCartResponse1);

		AssertJUnit.assertEquals("CheckoutService clearCart API is not working", Integer.parseInt(successRespCode),
				clearCartReqGen1.response.getStatus());
		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;

		System.out.println(
				"\nTime taken to execute - TestCase - CheckoutServiceTests_addItemToCart_verifySuccessResponse : "
						+ timeTaken + " seconds\n");
		log.info("\nTime taken to execute - TestCase - CheckoutServiceTests_addItemToCart_verifySuccessResponse : "
				+ timeTaken + " seconds\n");

	}

	@Test(groups = { "Sanity", "ExhaustiveRegression",
			"PPS" }, dataProvider = "CartServiceTests_addItemToCart_VerifyItemSpecificNodesPPSDP", description = "1. hit IDP service and login the user. "
					+ "\n 2. hit clearCart API to clear the existing cart" + "\n b. hit search API giving styleID"
					+ "\n 3. hit addItemToCart API fetching the skuId for given StyleID"
					+ "\n 4. Verify node values for added item as part of PPS changes")
	public void CartServiceTests_addItemToCart_VerifyItemSpecificNodesPPS(String userName, String password,
			String addItemQty, String addOperation, String styleId, String vendorType, String expectedValue,
			String successRespCode) {
		long startTime = Calendar.getInstance().getTimeInMillis();

		RequestGenerator clearCartReqGen = checkoutTestsHelper.clearCart(userName, password);
		String clearCartResponse = clearCartReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting CheckoutService clearCart API response :\n\n" + clearCartResponse);
		log.info("\nPrinting CheckoutService clearCart API response :\n\n" + clearCartResponse);

		AssertJUnit.assertEquals("CheckoutService clearCart API is not working", Integer.parseInt(successRespCode),
				clearCartReqGen.response.getStatus());

		System.out.println("\nPrinting the StyleId : " + styleId);
		log.info("\nPrinting the StyleId : " + styleId);

		RequestGenerator getStyleDataReqGen = checkoutTestsHelper.performGetStyleDataOperation(String.valueOf(styleId));
		System.out.println("\nPrinting StyleService getStyleData API response :\n\n"
				+ getStyleDataReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting StyleService getStyleData API response :\n\n"
				+ getStyleDataReqGen.respvalidate.returnresponseasstring());

		AssertJUnit.assertTrue("Error while getting style data", getStyleDataReqGen.respvalidate
				.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));

		String response = getStyleDataReqGen.respvalidate.returnresponseasstring();
		List<Integer> ids = JsonPath.read(response, "$.data..styleOptions[*].id");
		for (int i = 0; i < ids.size(); i++) {
			String skuId = JsonPath.read(response, "$.data..styleOptions[" + i + "].skuId").toString();
			String inventoryCount = JsonPath.read(response, "$.data..styleOptions[" + i + "].inventoryCount")
					.toString();
			String available = JsonPath.read(response, "$.data..styleOptions[" + i + "].available").toString();

			if (!inventoryCount.equals(EMPTY_VALUE) && available.equals(TRUE_VALUE)) {
				int invCount = Integer.parseInt(inventoryCount);
				int qty = Integer.parseInt(addItemQty);
				int itemQtyToAdd = invCount <= qty ? invCount : qty;

				RequestGenerator addItemToCartReqGen = checkoutTestsHelper.addItemToCart(userName, password, skuId,
						String.valueOf(itemQtyToAdd), addOperation);
				System.out.println("\nPrinting CheckoutService addItemToCart API response :\n\n"
						+ addItemToCartReqGen.respvalidate.returnresponseasstring());
				log.info("\nPrinting CheckoutService addItemToCart API response :\n\n"
						+ addItemToCartReqGen.respvalidate.returnresponseasstring());

				AssertJUnit.assertEquals("CheckoutService addItemToCart API is not working",
						Integer.parseInt(successRespCode), addItemToCartReqGen.response.getStatus());

				System.out.println("\n" + skuId + " is available and it doesn't exists in cart so added to cart");
				log.info("\n" + skuId + " is available and it doesn't exists in cart so added to cart");
				break;

			} else {
				System.out.println("\n" + skuId + " is not added because this item is currently out of stock");
				log.info("\n" + skuId + " is not added because this item is currently out of stock");
			}

		}

		RequestGenerator fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
		System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"
				+ fetchCartReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"
				+ fetchCartReqGen.respvalidate.returnresponseasstring());

		AssertJUnit.assertTrue("Error while invoking CheckoutService operationFetchCart API",
				fetchCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
						.contains(SUCCESS_STATUS_TYPE));

		String isReturnable = fetchCartReqGen.respvalidate
				.NodeValue(CartNodes.CART_ITEM_ENTRIES_IS_RETURNABLE.toString(), true);
		System.out.println("value of is_returabel is:" + isReturnable);

		String isExchangeable = fetchCartReqGen.respvalidate
				.NodeValue(CartNodes.CART_ITEM_ENTRIES_IS_EXCHANGEABLE.toString(), true);
		System.out.println("value of is_exchangeable is:" + isExchangeable);

		AssertJUnit.assertEquals("VALUE OF IS_RETURNABLE IS NOT AS EXPECTED", TRUE_VALUE,
				fetchCartReqGen.respvalidate.NodeValue(CartNodes.CART_ITEM_ENTRIES_IS_RETURNABLE.toString(), true));

		AssertJUnit.assertEquals("VALUE OF IS_EXCHANGEABLE IS NOT AS EXPECTED", expectedValue,
				fetchCartReqGen.respvalidate.NodeValue(CartNodes.CART_ITEM_ENTRIES_IS_EXCHANGEABLE.toString(), true));

		System.out.println("For styleid " + styleId + " which is of " + vendorType
				+ " value of parameter is_exchangeable is " + expectedValue + " as expected");

		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;

		System.out.println(
				"\nTime taken to execute - TestCase - CheckoutServiceTests_addItemToCart_verifySuccessResponse : "
						+ timeTaken + " seconds\n");
		log.info("\nTime taken to execute - TestCase - CheckoutServiceTests_addItemToCart_verifySuccessResponse : "
				+ timeTaken + " seconds\n");

	}

	/**
	 * 
	 * @param userName
	 * @param password
	 * @param successRespCode
	 */
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression", "MiniRegression",
			"ExhaustiveRegression" }, dataProvider = "CartServiceDP_getLoyaltyPointsDataProvider_validateAPIResponse")
	public void CartServiceTests_getLoyaltyPoints_verifySuccessResponse(String userName, String password,
			String successRespCode) {
		long startTime = Calendar.getInstance().getTimeInMillis();

		RequestGenerator getLoyaltyPointsReqGen = checkoutTestsHelper.getLoyaltyPoints(userName);
		System.out.println("\nPrinting CheckoutService getLoyaltyPoints API response :\n\n"
				+ getLoyaltyPointsReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting getMyntCredit API response :\n\n"
				+ getLoyaltyPointsReqGen.respvalidate.returnresponseasstring());

		String getLoyaltyPointsResponse = getLoyaltyPointsReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting getLoyaltyPoints API response :\n\n" + getLoyaltyPointsResponse + "\n");

		String availableLoyaltyPoints = JsonPath
				.read(getLoyaltyPointsResponse, "$.userAccountEntry..activePointsBalance").toString().replace("[", "")
				.replace("]", "").trim();

		System.out.println("For user " + userName + " the available Loyalty Points are " + availableLoyaltyPoints);
		log.info("\nPrinting getLoyaltyPoints API response :\n\n" + getLoyaltyPointsResponse + "\n");

		AssertJUnit.assertEquals("CheckoutService getLoyaltyPoints API is not working",
				Integer.parseInt(successRespCode), getLoyaltyPointsReqGen.response.getStatus());

		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;

		System.out.println(
				"\nTime taken to execute - TestCase - CheckoutServiceTests_getMyntCredit_verifySuccessResponse : "
						+ timeTaken + " seconds\n");
		log.info("\nTime taken to execute - TestCase - CheckoutServiceTests_getMyntCredit_verifySuccessResponse : "
				+ timeTaken + " seconds\n");
	}

	/**
	 * @author Shrinkhala This Test Case used to invoke CheckoutService
	 *         applyLoyaltyPoints API and verify for success response
	 * 
	 * @param userName
	 * @param password
	 * @param successRespCode
	 */
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression", "MiniRegression",
			"ExhaustiveRegression" }, dataProvider = "CartServiceDP_applyLoyaltyPointsDataProvider_validateAPIResponse", description = "1. hit IDP service and login the user. "
					+ "\n 2. hit fetchCart API. \n 3. if user's cart is empty then first add items to cart(\n a.hit Search Service API and extract some skuIDs"
					+ "\n b. Check if searched styleIds exists in user's cart or not. \n c. hit addItemToCart API giving skuId which doesnt exists in cart)"
					+ "\n 4. if loyaltyPoints are already applied to cart, remove it"
					+ "\n 5. hit getLoyaltyPoints API to fetch the available LP of user"
					+ "\n 6. hit applyLoyaltyPoints API giving the fetched LP to be applied on cart"
					+ "\n 7. Verify statusCode must be 200 in APIs response")
	public void CartServiceTests_applyLoyaltyPoints_verifySuccessResponse(String userName, String password,
			String successRespCode) {
		long startTime = Calendar.getInstance().getTimeInMillis();
		String addItemQty = "1";
		Boolean itemAdded = false;

		RequestGenerator fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
		System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"
				+ fetchCartReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"
				+ fetchCartReqGen.respvalidate.returnresponseasstring());

		AssertJUnit.assertTrue("Error while invoking CheckoutService operationFetchCart API",
				fetchCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
						.contains(SUCCESS_STATUS_TYPE));

		if (fetchCartReqGen.respvalidate.NodeValue(CartNodes.CART_TOTAL_COUNT.toString(), true).equals(EMPTY_VALUE)) {
			List<Integer> styleIdList = checkoutTestsHelper.performSeachServiceToGetStyleIds(UC_SEARCH_PROD,
					UC_SEARCH_QTY, UC_SEARCH_RET_DOCS, UC_SEARCH_IS_FACET);

			if (!CollectionUtils.isEmpty(styleIdList)) {
				for (Integer styleId : styleIdList) {
					if (itemAdded)
						break;
					System.out.println("\nPrinting the StyleId : " + styleId);
					log.info("\nPrinting the StyleId : " + styleId);
					RequestGenerator getStyleDataReqGen = checkoutTestsHelper
							.performGetStyleDataOperation(String.valueOf(styleId));
					System.out.println("\nPrinting StyleService getStyleData API response :\n\n"
							+ getStyleDataReqGen.respvalidate.returnresponseasstring());
					log.info("\nPrinting StyleService getStyleData API response :\n\n"
							+ getStyleDataReqGen.respvalidate.returnresponseasstring());

					AssertJUnit.assertTrue("Error while getting style data", getStyleDataReqGen.respvalidate
							.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));

					String response = getStyleDataReqGen.respvalidate.returnresponseasstring();
					List<Integer> ids = JsonPath.read(response, "$.data..styleOptions[*].id");

					for (int i = 0; i < ids.size(); i++) {

						String skuId = JsonPath.read(response, "$.data..styleOptions[" + i + "].skuId").toString();
						String inventoryCount = JsonPath
								.read(response, "$.data..styleOptions[" + i + "].inventoryCount").toString();
						String available = JsonPath.read(response, "$.data..styleOptions[" + i + "].available")
								.toString();

						if (!inventoryCount.equals(EMPTY_VALUE) && available.equals(TRUE_VALUE)) {
							RequestGenerator addItemToCartReqGen = checkoutTestsHelper.addItemToCart(userName, password,
									skuId, String.valueOf(addItemQty), ADD_OPERATION);
							System.out.println("\nPrinting CheckoutService addItemToCart API response :\n\n"
									+ addItemToCartReqGen.respvalidate.returnresponseasstring());
							log.info("\nPrinting CheckoutService addItemToCart API response :\n\n"
									+ addItemToCartReqGen.respvalidate.returnresponseasstring());

							AssertJUnit.assertTrue("Error while invoking CheckoutService addItemToCart API",
									addItemToCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
											.contains(SUCCESS_STATUS_TYPE));
							itemAdded = true;
							System.out.println(
									"\n" + skuId + " is available and it doesn't exists in cart so added to cart");
							log.info("\n" + skuId + " is available and it doesn't exists in cart so added to cart");
							break;

						} else {
							System.out.println(
									"\n" + skuId + " is not added because this item is currently out of stock");
							log.info("\n" + skuId + " is not added because this item is currently out of stock");
						}
					}
				}

			} else {
				System.out.println("\nError while invoking SearchService API");
				log.error("\nError while invoking SearchService API");
				AssertJUnit.fail("Error while invoking SearchService API");
			}
		}

		if (fetchCartReqGen.respvalidate.NodeValue(CartNodes.CART_USE_LOYALTY_POINTS.toString(), false)
				.equals(TRUE_VALUE)) {
			RequestGenerator removeLoyaltyPoints = checkoutTestsHelper.removeLoyaltyPoints(userName, password);
			System.out.println("\nPrinting CheckoutService removeLoyaltyPoints API response :\n\n"
					+ removeLoyaltyPoints.respvalidate.returnresponseasstring());
			log.info("\nPrinting CheckoutService removeLoyaltyPoints API response :\n\n"
					+ removeLoyaltyPoints.respvalidate.returnresponseasstring());

			AssertJUnit.assertTrue("Error while invoking CheckoutService removeLoyaltyPoints API",
					removeLoyaltyPoints.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
							.contains(SUCCESS_STATUS_TYPE));
		}

		RequestGenerator getLoyaltyPointsReqGen = checkoutTestsHelper.getLoyaltyPoints(userName);
		System.out.println("\nPrinting CheckoutService getLoyaltyPoints API response :\n\n"
				+ getLoyaltyPointsReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting getMyntCredit API response :\n\n"
				+ getLoyaltyPointsReqGen.respvalidate.returnresponseasstring());

		String getLoyaltyPointsResponse = getLoyaltyPointsReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting getLoyaltyPoints API response :\n\n" + getLoyaltyPointsResponse + "\n");

		String availableLoyaltyPoints = JsonPath
				.read(getLoyaltyPointsResponse, "$.userAccountEntry..activePointsBalance").toString().replace("[", "")
				.replace("]", "").trim();

		System.out.println("For user " + userName + " the available Loyalty Points are " + availableLoyaltyPoints);
		log.info("\nPrinting getLoyaltyPoints API response :\n\n" + getLoyaltyPointsResponse + "\n");

		RequestGenerator applyLoyaltyPointsReqGen = checkoutTestsHelper.applyLoyaltyPoints(userName, password,
				availableLoyaltyPoints);
		System.out.println("\nPrinting CheckoutService applyLoyaltyPoints API response :\n\n"
				+ applyLoyaltyPointsReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService applyLoyaltyPoints API response :\n\n"
				+ applyLoyaltyPointsReqGen.respvalidate.returnresponseasstring());

		AssertJUnit.assertEquals("CheckoutService applyLoyaltyPoints API is not working",
				Integer.parseInt(successRespCode), applyLoyaltyPointsReqGen.response.getStatus());

		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;

		System.out.println(
				"\nTime taken to execute - TestCase - CheckoutServiceTests_applyMyntCredit_verifySuccessResponse : "
						+ timeTaken + " seconds\n");
		log.info("\nTime taken to execute - TestCase - CheckoutServiceTests_applyMyntCredit_verifySuccessResponse : "
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
			"ExhaustiveRegression" }, dataProvider = "CartServiceDP_removeLoyaltyPointsDataProvider_validateAPIResponse", description = "1. hit IDP service and login the user. "
					+ "\n 2. hit fetchCart API. \n 3. if user's cart is empty then first add items to cart(\n a.hit Search Service API and extract some skuIDs"
					+ "\n b. Check if searched styleIds exists in user's cart or not. \n c. hit addItemToCart API giving skuId which doesnt exists in cart)"
					+ "\n 4. if loyaltyPoints are not applied to cart, apply it."
					+ "\n (a. hit getLoyaltyPoints API to fetch the available LP of user"
					+ "\n b. hit applyLoyaltyPoints API giving the fetched LP to be applied on cart)"
					+ "\n 5. hit removeLoyaltyPoints API to remove applied loyaltyPoints from cart"
					+ "\n 6. Verify statusCode must be 200 in APIs response")
	public void CartServiceTests_removeLoyaltyPointst_verifySuccessResponse(String userName, String password,
			String successRespCode) {
		long startTime = Calendar.getInstance().getTimeInMillis();
		String addItemQty = "1";
		Boolean itemAdded = false;
		RequestGenerator fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
		System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"
				+ fetchCartReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"
				+ fetchCartReqGen.respvalidate.returnresponseasstring());

		AssertJUnit.assertTrue(
				"CheckoutService operationFetchCart API response status type is not a success status type",
				fetchCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
						.contains(SUCCESS_STATUS_TYPE));

		if (fetchCartReqGen.respvalidate.NodeValue(CartNodes.CART_TOTAL_COUNT.toString(), true).equals(EMPTY_VALUE)) {
			List<Integer> styleIdList = checkoutTestsHelper.performSeachServiceToGetStyleIds(UC_SEARCH_PROD,
					UC_SEARCH_QTY, UC_SEARCH_RET_DOCS, UC_SEARCH_IS_FACET);

			if (!CollectionUtils.isEmpty(styleIdList)) {
				for (Integer styleId : styleIdList) {
					if (itemAdded)
						break;
					System.out.println("\nPrinting the StyleId : " + styleId);
					log.info("\nPrinting the StyleId : " + styleId);
					RequestGenerator getStyleDataReqGen = checkoutTestsHelper
							.performGetStyleDataOperation(String.valueOf(styleId));
					System.out.println("\nPrinting StyleService getStyleData API response :\n\n"
							+ getStyleDataReqGen.respvalidate.returnresponseasstring());
					log.info("\nPrinting StyleService getStyleData API response :\n\n"
							+ getStyleDataReqGen.respvalidate.returnresponseasstring());

					AssertJUnit.assertTrue("Error while getting style data", getStyleDataReqGen.respvalidate
							.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));

					String response = getStyleDataReqGen.respvalidate.returnresponseasstring();
					List<Integer> ids = JsonPath.read(response, "$.data..styleOptions[*].id");

					for (int i = 0; i < ids.size(); i++) {

						String skuId = JsonPath.read(response, "$.data..styleOptions[" + i + "].skuId").toString();
						String inventoryCount = JsonPath
								.read(response, "$.data..styleOptions[" + i + "].inventoryCount").toString();
						String available = JsonPath.read(response, "$.data..styleOptions[" + i + "].available")
								.toString();

						if (!inventoryCount.equals(EMPTY_VALUE) && available.equals(TRUE_VALUE)) {
							RequestGenerator addItemToCartReqGen = checkoutTestsHelper.addItemToCart(userName, password,
									skuId, String.valueOf(addItemQty), ADD_OPERATION);
							System.out.println("\nPrinting CheckoutService addItemToCart API response :\n\n"
									+ addItemToCartReqGen.respvalidate.returnresponseasstring());
							log.info("\nPrinting CheckoutService addItemToCart API response :\n\n"
									+ addItemToCartReqGen.respvalidate.returnresponseasstring());

							AssertJUnit.assertTrue("Error while invoking CheckoutService addItemToCart API",
									addItemToCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
											.contains(SUCCESS_STATUS_TYPE));
							itemAdded = true;
							System.out.println(
									"\n" + skuId + " is available and it doesn't exists in cart so added to cart");
							log.info("\n" + skuId + " is available and it doesn't exists in cart so added to cart");
							break;

						} else {
							System.out.println(
									"\n" + skuId + " is not added because this item is currently out of stock");
							log.info("\n" + skuId + " is not added because this item is currently out of stock");
						}
					}
				}

			} else {
				System.out.println("\nError while invoking SearchService API");
				log.error("\nError while invoking SearchService API");
				AssertJUnit.fail("Error while invoking SearchService API");
			}
		}

		if (fetchCartReqGen.respvalidate.NodeValue(CartNodes.CART_USE_LOYALTY_POINTS.toString(), false)
				.equals(FALSE_VALUE)) {
			RequestGenerator getLoyaltyPointsReqGen = checkoutTestsHelper.getLoyaltyPoints(userName);
			System.out.println("\nPrinting CheckoutService getLoyaltyPoints API response :\n\n"
					+ getLoyaltyPointsReqGen.respvalidate.returnresponseasstring());
			log.info("\nPrinting getMyntCredit API response :\n\n"
					+ getLoyaltyPointsReqGen.respvalidate.returnresponseasstring());

			String getLoyaltyPointsResponse = getLoyaltyPointsReqGen.respvalidate.returnresponseasstring();
			System.out.println("\nPrinting getLoyaltyPoints API response :\n\n" + getLoyaltyPointsResponse + "\n");

			String availableLoyaltyPoints = JsonPath
					.read(getLoyaltyPointsResponse, "$.userAccountEntry..activePointsBalance").toString()
					.replace("[", "").replace("]", "").trim();

			System.out.println("For user " + userName + " the available Loyalty Points are " + availableLoyaltyPoints);
			log.info("\nPrinting getLoyaltyPoints API response :\n\n" + getLoyaltyPointsResponse + "\n");

			RequestGenerator applyLoyaltyPointsReqGen = checkoutTestsHelper.applyLoyaltyPoints(userName, password,
					availableLoyaltyPoints);
			System.out.println("\nPrinting CheckoutService applyLoyaltyPoints API response :\n\n"
					+ applyLoyaltyPointsReqGen.respvalidate.returnresponseasstring());
			log.info("\nPrinting CheckoutService applyLoyaltyPoints API response :\n\n"
					+ applyLoyaltyPointsReqGen.respvalidate.returnresponseasstring());

			AssertJUnit.assertTrue("Error while invoking CheckoutService applyLoyaltyPoints API",
					applyLoyaltyPointsReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
							.contains(SUCCESS_STATUS_TYPE));
		}

		RequestGenerator removeLoyaltyPointsReqGen = checkoutTestsHelper.removeLoyaltyPoints(userName, password);
		System.out.println("\nPrinting CheckoutService removeLoyaltyPoints API response :\n\n"
				+ removeLoyaltyPointsReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService removeLoyaltyPoints API response :\n\n"
				+ removeLoyaltyPointsReqGen.respvalidate.returnresponseasstring());

		AssertJUnit.assertEquals("CheckoutService removeLoyaltyPoints API is not working",
				Integer.parseInt(successRespCode), removeLoyaltyPointsReqGen.response.getStatus());

		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;

		System.out.println(
				"\nTime taken to execute - TestCase - CheckoutServiceTests_removeLoyaltyPoints_verifySuccessResponse : "
						+ timeTaken + " seconds\n");
		log.info(
				"\nTime taken to execute - TestCase - CheckoutServiceTests_removeLoyaltyPoints_verifySuccessResponse : "
						+ timeTaken + " seconds\n");
	}

	/**
	 * @author Shrinkhala This Test Case used to verifyTryAndBuy Nodes Exists in
	 *         fetchCart API response for tryNBuy Changes
	 * 
	 * @param userName
	 * @param password
	 * @param successRespCode
	 */
	@Test(groups = { "Sanity", "Regression",
			"ExhaustiveRegression" }, dataProvider = "CartServiceTests_addSingleItemToCart_VerifyTryAndBuyNodesExistsDP", description = "1. hit IDP service and login the user. "
					+ "\n 2. add items to cart(\n a.hit Search Service and extract SkuIDs for given styleId"
					+ "\n b. hit addItemToCart API giving skuId which doesnt exists in cart)"
					+ "\n 3. hit fetchCart API \n 4.Check the service response must be 200"
					+ "\n 4. Verify tryNbuy Nodes exists in response as part of TryAndBuy changes")
	public void CartServiceTests_addSingleItemToCart_VerifyTryAndBuyNodesExists(String userName, String password,
			String addItemQty, String addOperation, String styleId, String vendorType, String expectedValue,
			String successRespCode) {
		long startTime = Calendar.getInstance().getTimeInMillis();

		RequestGenerator clearCartReqGen = checkoutTestsHelper.clearCart(userName, password);
		String clearCartResponse = clearCartReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting CheckoutService clearCart API response :\n\n" + clearCartResponse);
		log.info("\nPrinting CheckoutService clearCart API response :\n\n" + clearCartResponse);

		AssertJUnit.assertEquals("CheckoutService clearCart API is not working", Integer.parseInt(successRespCode),
				clearCartReqGen.response.getStatus());

		System.out.println("\nPrinting the StyleId : " + styleId);
		log.info("\nPrinting the StyleId : " + styleId);

		RequestGenerator getStyleDataReqGen = checkoutTestsHelper.performGetStyleDataOperation(String.valueOf(styleId));
		System.out.println("\nPrinting StyleService getStyleData API response :\n\n"
				+ getStyleDataReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting StyleService getStyleData API response :\n\n"
				+ getStyleDataReqGen.respvalidate.returnresponseasstring());

		AssertJUnit.assertTrue("Error while getting style data", getStyleDataReqGen.respvalidate
				.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));

		String response = getStyleDataReqGen.respvalidate.returnresponseasstring();
		List<Integer> ids = JsonPath.read(response, "$.data..styleOptions[*].id");
		for (int i = 0; i < ids.size(); i++) {
			String skuId = JsonPath.read(response, "$.data..styleOptions[" + i + "].skuId").toString();
			String inventoryCount = JsonPath.read(response, "$.data..styleOptions[" + i + "].inventoryCount")
					.toString();
			String available = JsonPath.read(response, "$.data..styleOptions[" + i + "].available").toString();

			if (!inventoryCount.equals(EMPTY_VALUE) && available.equals(TRUE_VALUE)) {
				int invCount = Integer.parseInt(inventoryCount);
				int qty = Integer.parseInt(addItemQty);
				int itemQtyToAdd = invCount <= qty ? invCount : qty;

				RequestGenerator addItemToCartReqGen = checkoutTestsHelper.addItemToCart(userName, password, skuId,
						String.valueOf(itemQtyToAdd), addOperation);
				System.out.println("\nPrinting CheckoutService addItemToCart API response :\n\n"
						+ addItemToCartReqGen.respvalidate.returnresponseasstring());
				log.info("\nPrinting CheckoutService addItemToCart API response :\n\n"
						+ addItemToCartReqGen.respvalidate.returnresponseasstring());

				AssertJUnit.assertEquals("CheckoutService addItemToCart API is not working",
						Integer.parseInt(successRespCode), addItemToCartReqGen.response.getStatus());

				System.out.println("\n" + skuId + " is available and it doesn't exists in cart so added to cart");
				log.info("\n" + skuId + " is available and it doesn't exists in cart so added to cart");
				break;

			} else {
				System.out.println("\n" + skuId + " is not added because this item is currently out of stock");
				log.info("\n" + skuId + " is not added because this item is currently out of stock");
			}

		}

		RequestGenerator fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
		System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"
				+ fetchCartReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"
				+ fetchCartReqGen.respvalidate.returnresponseasstring());

		AssertJUnit.assertTrue("Error while invoking CheckoutService operationFetchCart API",
				fetchCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
						.contains(SUCCESS_STATUS_TYPE));

		AssertJUnit.assertTrue("TryAndBuyEnabled node does not exists",
				fetchCartReqGen.respvalidate.DoesNodeExists(CartNodes.CART_ITEM_ENTRIES_TNB_ENABLED.toString()));

		AssertJUnit.assertTrue("TryAndBuyOpted node does not exists",
				fetchCartReqGen.respvalidate.DoesNodeExists(CartNodes.CART_ITEM_ENTRIES_TNB_OPTED.toString()));

		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;

		System.out.println(
				"\nTime taken to execute - TestCase - CartServiceTests_addSingleItemToCart_VerifyTryAndBuyNodesExists : "
						+ timeTaken + " seconds\n");
		log.info(
				"\nTime taken to execute - TestCase - CartServiceTests_addSingleItemToCart_VerifyTryAndBuyNodesExists : "
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
			"ExhaustiveRegression" }, dataProvider = "CartServiceTests_optTryNBuyOnDelivery_verifySuccessResponseDP", description = "1. hit IDP service to login"
					+ "\n 2. hit OptTryAndBuy API" + "\n 3.Check the service response must be 200")
	public void CartServiceTests_optTryNBuyOnDelivery_verifySuccessResponse(String userName, String password,
			String itemId, String successRespCode) {
		long startTime = Calendar.getInstance().getTimeInMillis();
		RequestGenerator fetchCartReqGen = checkoutTestsHelper.optTryNBuyOnDelivery(userName, password, itemId);
		log.info("\nPrinting CheckoutService optTryAndBuy API response :\n\n"
				+ fetchCartReqGen.respvalidate.returnresponseasstring());

		AssertJUnit.assertEquals("CheckoutService optTryAndBuy API is not working", Integer.parseInt(successRespCode),
				fetchCartReqGen.response.getStatus());

		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;

		System.out.println(
				"\nTime taken to execute - TestCase - CartServiceTests_optTryNBuyOnDelivery_verifySuccessResponse : "
						+ timeTaken + " seconds\n");
		

	}

	/**
	 * @author Shrinkhala This Test Case used to Verify optTryAndBuy APIs flow
	 *         in fetchCart API response for tryNBuy Changes
	 * 
	 * @param userName
	 * @param password
	 * @param successRespCode
	 */
	/*
	@Test(groups = { "Sanity", "Regression",
			"ExhaustiveRegression" }, dataProvider = "CartServiceTests_addSingleItemToCart_OptTryNBuyVerifyOptNodesDP", description = "1. hit IDP service and login the user. "
					+ "\n 2. add items to cart(\n a.hit Search Service and extract SkuIDs for given styleId"
					+ "\n b. hit addItemToCart API giving skuId which doesnt exists in cart)"
					+ "\n 3. hit optTryAndBuy API \n 4.Check the service response must be 200"
					+ "\n 4. Verify node values for added item as part of TryAndBuy changes")
	public void CartServiceTests_addSingleItemToCart_OptTryNBuyVerifyOptNodes(String userName, String password,
			String addItemQty, String addOperation, String styleId, String successRespCode) {
		long startTime = Calendar.getInstance().getTimeInMillis();

		RequestGenerator clearCartReqGen = checkoutTestsHelper.clearCart(userName, password);
		String clearCartResponse = clearCartReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting CheckoutService clearCart API response :\n\n" + clearCartResponse);
		log.info("\nPrinting CheckoutService clearCart API response :\n\n" + clearCartResponse);

		AssertJUnit.assertEquals("CheckoutService clearCart API is not working", Integer.parseInt(successRespCode),
				clearCartReqGen.response.getStatus());

		System.out.println("\nPrinting the StyleId : " + styleId);
		log.info("\nPrinting the StyleId : " + styleId);

		RequestGenerator getStyleDataReqGen = checkoutTestsHelper.performGetStyleDataOperation(String.valueOf(styleId));
		System.out.println("\nPrinting StyleService getStyleData API response :\n\n"
				+ getStyleDataReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting StyleService getStyleData API response :\n\n"
				+ getStyleDataReqGen.respvalidate.returnresponseasstring());

		AssertJUnit.assertTrue("Error while getting style data", getStyleDataReqGen.respvalidate
				.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));

		String response = getStyleDataReqGen.respvalidate.returnresponseasstring();
		List<Integer> ids = JsonPath.read(response, "$.data..styleOptions[*].id");
		for (int i = 0; i < ids.size(); i++) {
			String skuId = JsonPath.read(response, "$.data..styleOptions[" + i + "].skuId").toString();
			String inventoryCount = JsonPath.read(response, "$.data..styleOptions[" + i + "].inventoryCount")
					.toString();
			String available = JsonPath.read(response, "$.data..styleOptions[" + i + "].available").toString();

			if (!inventoryCount.equals(EMPTY_VALUE) && available.equals(TRUE_VALUE)) {
				int invCount = Integer.parseInt(inventoryCount);
				int qty = Integer.parseInt(addItemQty);
				int itemQtyToAdd = invCount <= qty ? invCount : qty;

				RequestGenerator addItemToCartReqGen = checkoutTestsHelper.addItemToCart(userName, password, skuId,
						String.valueOf(itemQtyToAdd), addOperation);
				System.out.println("\nPrinting CheckoutService addItemToCart API response :\n\n"
						+ addItemToCartReqGen.respvalidate.returnresponseasstring());
				log.info("\nPrinting CheckoutService addItemToCart API response :\n\n"
						+ addItemToCartReqGen.respvalidate.returnresponseasstring());

				AssertJUnit.assertEquals("CheckoutService addItemToCart API is not working",
						Integer.parseInt(successRespCode), addItemToCartReqGen.response.getStatus());

				System.out.println("\n" + skuId + " is available and it doesn't exists in cart so added to cart");
				log.info("\n" + skuId + " is available and it doesn't exists in cart so added to cart");
				break;

			} else {
				System.out.println("\n" + skuId + " is not added because this item is currently out of stock");
				log.info("\n" + skuId + " is not added because this item is currently out of stock");
			}

		}

		RequestGenerator fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
		System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"
				+ fetchCartReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"
				+ fetchCartReqGen.respvalidate.returnresponseasstring());

		AssertJUnit.assertTrue("Error while invoking CheckoutService operationFetchCart API",
				fetchCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
						.contains(SUCCESS_STATUS_TYPE));

		String itemId = fetchCartReqGen.respvalidate.NodeValue(CartNodes.CART_ITEM_ENTRIES_ITEM_ID.toString(), true);
		RequestGenerator optTryANdBuyReqGen = checkoutTestsHelper.optTryNBuyOnDelivery(userName, password, itemId);
		log.info("\nPrinting CheckoutService optTryAndBuy API response :\n\n"
				+ optTryANdBuyReqGen.respvalidate.returnresponseasstring());

		AssertJUnit.assertEquals("CheckoutService optTryAndBuy API is not working", Integer.parseInt(successRespCode),
				optTryANdBuyReqGen.response.getStatus());

		RequestGenerator fetchCartReqGen1 = checkoutTestsHelper.operationFetchCart(userName, password);
		System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"
				+ fetchCartReqGen1.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"
				+ fetchCartReqGen1.respvalidate.returnresponseasstring());

		AssertJUnit.assertTrue("Error while invoking CheckoutService operationFetchCart API",
				fetchCartReqGen1.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true)
						.contains(SUCCESS_STATUS_TYPE));

		AssertJUnit.assertEquals("Value of tryAndBuyEnable is not as expected", TRUE_VALUE,
				fetchCartReqGen1.respvalidate.NodeValue(CartNodes.CART_ITEM_ENTRIES_TNB_ENABLED.toString(), true));

		AssertJUnit.assertEquals("Value of tryAndBuyOpted is not as expected", TRUE_VALUE,
				fetchCartReqGen1.respvalidate.NodeValue(CartNodes.CART_ITEM_ENTRIES_TNB_OPTED.toString(), true));

		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;

		System.out.println(
				"\nTime taken to execute - TestCase - CartServiceTests_addSingleItemToCart_OptTryNBuyVerifyOptNodes : "
						+ timeTaken + " seconds\n");
		log.info("\nTime taken to execute - TestCase - CartServiceTests_addSingleItemToCart_OptTryNBuyVerifyOptNodes : "
				+ timeTaken + " seconds\n");

	}*/

	@Test()
	public void dummy() {

	}

}
