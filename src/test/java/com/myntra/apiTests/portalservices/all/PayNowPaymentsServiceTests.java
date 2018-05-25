package com.myntra.apiTests.portalservices.all;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;

import com.myntra.apiTests.dataproviders.PayNowPaymentsServiceDP;
import com.myntra.apiTests.portalservices.checkoutservice.CartNodes;
import com.myntra.apiTests.portalservices.checkoutservice.CheckoutTestsHelper;
import com.myntra.apiTests.portalservices.commons.StatusNodes;
import com.myntra.apiTests.portalservices.loyalitypointsservice.LoyalityPointsServiceHelper;
import com.myntra.apiTests.portalservices.myntcashservice.MyntCashServiceHelper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import com.jayway.jsonpath.JsonPath;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.apiTests.portalservices.checkoutservice.CheckoutDataProviderEnum;


public class PayNowPaymentsServiceTests extends PayNowPaymentsServiceDP
{
	static Logger log = Logger.getLogger(PayNowPaymentsServiceTests.class);
	static CheckoutTestsHelper checkoutTestsHelper = new CheckoutTestsHelper();
	static LoyalityPointsServiceHelper loyalityPointsServiceHelper = new LoyalityPointsServiceHelper();
	static MyntCashServiceHelper myntrCashServiceHelper = new MyntCashServiceHelper();

	static Initialize init = new Initialize("./Data/configuration");
	
	//method to create custom payload
	private String getPayloadAsString(String payloadName,
			String[] payloadparams) {
		String customPayloadDir = System.getProperty("user.dir")
				+ File.separator + "Data" + File.separator + "payloads"
				+ File.separator + "JSON";
		StringBuffer sb = new StringBuffer();
		String finalPayload = "";
		Scanner sc;
		try {
			sc = new Scanner(new File(customPayloadDir + File.separator
					+ payloadName));
			while (sc.hasNextLine())
				sb.append(sc.nextLine() + "\n");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		finalPayload = sb.toString();
		for (int i = 0; i < payloadparams.length; i++) {
			finalPayload = finalPayload.replace("${" + i + "}",
					payloadparams[i]);
		}
		return finalPayload;
	}
	
	
	@Test(groups = { "Smoke", "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression", "PPS" }, 
			dataProvider = "PayNowPaymentsServiceTests_PlaceOrderUsingPayNowAPI_VerifyAPIIsUpDP")
	public void PayNowPaymentsServiceTests_PlaceOrderUsingPayNowAPI_VerifyAPIIsUp(String userName, String password, String amount)
	{
		long startTime = Calendar.getInstance().getTimeInMillis();
		amount = amount +"00"; //converting rupee to paise
		String payloadParams[] = new String[] {"${0}",amount};
		String customPayload = getPayloadAsString("paynowusingcod", payloadParams);
		System.out.println("final payload created is:" +customPayload);
				
		// place order using PayNowAPI as COD order
		RequestGenerator placeOrdersUsingPayNowReqGen = checkoutTestsHelper.placeOrderUsingPayNowAPI(userName, password, customPayload);
		System.out.println("\nPrinting CheckoutService placeOrdersUsingPayNowReqGen API response :\n\n"+placeOrdersUsingPayNowReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService placeOrdersUsingPayNowReqGen API response :\n\n"+placeOrdersUsingPayNowReqGen.respvalidate.returnresponseasstring());
				
	
		AssertJUnit.assertTrue("payNow API is not working as expected, No PPSID generated", 
				placeOrdersUsingPayNowReqGen.respvalidate.DoesNodeExists("ppsId".toString()));
			
		AssertJUnit.assertTrue("payNow API is not working as expected, No orderId generated", 
				placeOrdersUsingPayNowReqGen.respvalidate.DoesNodeExists("orderId".toString()));
		
		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime)/1000.0;
		
		System.out.println("\nTime taken to execute - TestCase - PayNowPaymentsServiceTests_PlaceOrderUsingPayNowAPI_VerifyAPIIsUp : "+timeTaken+" seconds\n");
		log.info("\nTime taken to execute - TestCase - PayNowPaymentsServiceTests_PlaceOrderUsingPayNowAPI_VerifyAPIIsUp : "+timeTaken+" seconds\n");
		
	}
	
	@Test(groups = { "Smoke", "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression","PPS" }, 
			dataProvider = "PayNowPaymentsServiceTests_PlaceOrderAsCOD_VerifySuccessResponseDP")
	public void PayNowPaymentsServiceTests_PlaceOrderAsCOD_VerifySuccessResponse(String userName, String password, String addItemQty, String addOperation,String successRespCode)
	{	
		long startTime = Calendar.getInstance().getTimeInMillis();
		
		//clear cart
		RequestGenerator clearCartReqGen = checkoutTestsHelper.clearCart(userName, password);
		String clearCartResponse = clearCartReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting CheckoutService clearCart API response :\n\n"+clearCartResponse);
		log.info("\nPrinting CheckoutService clearCart API response :\n\n"+clearCartResponse);
					
						
		//prepare cart
		List<Integer> styleIdList = checkoutTestsHelper.performSeachServiceToGetStyleIds(AC_SEARCH_PROD, AC_SEARCH_QTY, AC_SEARCH_RET_DOCS, AC_SEARCH_IS_FACET);
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
					getStyleDataReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));
							
				String response = getStyleDataReqGen.respvalidate.returnresponseasstring();
				List<Integer> ids = JsonPath.read(response, "$.data..styleOptions[*].id");
							
				for(int i=0 ; i< ids.size(); i++){
					String skuId = JsonPath.read(response, "$.data..styleOptions["+i+"].skuId").toString();							String inventoryCount = JsonPath.read(response, "$.data..styleOptions["+i+"].inventoryCount").toString();
					String available = JsonPath.read(response, "$.data..styleOptions["+i+"].available").toString();
								
					if(!inventoryCount.equals(EMPTY_VALUE) && available.equals(TRUE_VALUE))
					{
						RequestGenerator fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
						System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"+fetchCartReqGen.respvalidate.returnresponseasstring());
						log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"+fetchCartReqGen.respvalidate.returnresponseasstring());
									
						if(StringUtils.isBlank(checkoutTestsHelper.getItemIdFromSKUId(fetchCartReqGen, skuId, CheckoutDataProviderEnum.CART.name())))
						{
							RequestGenerator addItemToCartReqGen = checkoutTestsHelper.addItemToCart(userName, password, skuId,String.valueOf(addItemQty) , ADD_OPERATION);
							System.out.println("\nPrinting CheckoutService addItemToCart API response :\n\n"+addItemToCartReqGen.respvalidate.returnresponseasstring());
							log.info("\nPrinting CheckoutService addItemToCart API response :\n\n"+addItemToCartReqGen.respvalidate.returnresponseasstring());
										
							AssertJUnit.assertTrue("Error while invoking CheckoutService addItemToCart API",
								addItemToCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));
										
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
				System.out.println("\nError while invoking SearchService API");
				log.error("\nError while invoking SearchService API");
				AssertJUnit.fail("Error while invoking SearchService API");
		}
				
					
		//fetch user's cart, get the total amount to be paid
		RequestGenerator fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
		System.out.println("First fetch cart response!!!....");
		System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"+fetchCartReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"+fetchCartReqGen.respvalidate.returnresponseasstring());
			
		String value = fetchCartReqGen.respvalidate.NodeValue(CartNodes.CART_TOTAL_PRICE_DECIMAL.toString(), false);
		System.out.println("total amount to be paid is "+value);
		String value1 = value.substring(0, value.indexOf("."));
		
		int amount = Integer.valueOf(value1);
		amount = (amount+1)*100;
		
		value1 = String.valueOf(amount);
		System.out.println("amount in value1 is:"+value1);

		String payloadParams[] = new String[] {"${0}",value1};
		String customPayload = getPayloadAsString("paynowusingcod", payloadParams);
		System.out.println("final payload created is:" +customPayload);
				
		// place order using PayNowAPI as COD order
		RequestGenerator placeOrdersUsingPayNowReqGen = checkoutTestsHelper.placeOrderUsingPayNowAPI(userName, password, customPayload);
		System.out.println("\nPrinting CheckoutService placeOrdersUsingPayNowReqGen API response :\n\n"+placeOrdersUsingPayNowReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService placeOrdersUsingPayNowReqGen API response :\n\n"+placeOrdersUsingPayNowReqGen.respvalidate.returnresponseasstring());
				
	
		AssertJUnit.assertTrue("payNow API ppsID does not exists", 
				placeOrdersUsingPayNowReqGen.respvalidate.DoesNodeExists("ppsId".toString()));
			
		AssertJUnit.assertTrue("payNow API orderID does not exists", 
				placeOrdersUsingPayNowReqGen.respvalidate.DoesNodeExists("orderId".toString()));
				
		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime)/1000.0;
		
		System.out.println("\nTime taken to execute - TestCase - PayNowPaymentsServiceTests_PlaceOrderAsCOD_VerifySuccessResponse : "+timeTaken+" seconds\n");
		log.info("\nTime taken to execute - TestCase - PayNowPaymentsServiceTests_PlaceOrderAsCOD_VerifySuccessResponse : "+timeTaken+" seconds\n");

	}
	
	@Test(groups = { "Smoke", "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression","PPS" }, 
			dataProvider = "PayNowPaymentsServiceTests_ApplyCouponPlaceOrderAsCOD_VerifySuccessResponseDP")
	public void PayNowPaymentsServiceTests_ApplyCouponPlaceOrderAsCOD_VerifySuccessResponse(String userName, String password, String addItemQty, String addOperation,String successRespCode)
	{	
		long startTime = Calendar.getInstance().getTimeInMillis();
		//userName ="testing420@myntra.com";
		//clear cart
		RequestGenerator clearCartReqGen = checkoutTestsHelper.clearCart(userName, password);
		String clearCartResponse = clearCartReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting CheckoutService clearCart API response :\n\n"+clearCartResponse);
		log.info("\nPrinting CheckoutService clearCart API response :\n\n"+clearCartResponse);
					
						
		//prepare cart
		List<Integer> styleIdList = checkoutTestsHelper.performSeachServiceToGetStyleIds(AC_SEARCH_PROD, AC_SEARCH_QTY, AC_SEARCH_RET_DOCS, AC_SEARCH_IS_FACET);
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
					getStyleDataReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));
							
				String response = getStyleDataReqGen.respvalidate.returnresponseasstring();
				List<Integer> ids = JsonPath.read(response, "$.data..styleOptions[*].id");
							
				for(int i=0 ; i< ids.size(); i++){
					String skuId = JsonPath.read(response, "$.data..styleOptions["+i+"].skuId").toString();							String inventoryCount = JsonPath.read(response, "$.data..styleOptions["+i+"].inventoryCount").toString();
					String available = JsonPath.read(response, "$.data..styleOptions["+i+"].available").toString();
								
					if(!inventoryCount.equals(EMPTY_VALUE) && available.equals(TRUE_VALUE))
					{
						RequestGenerator fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
						System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"+fetchCartReqGen.respvalidate.returnresponseasstring());
						log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"+fetchCartReqGen.respvalidate.returnresponseasstring());
									
						if(StringUtils.isBlank(checkoutTestsHelper.getItemIdFromSKUId(fetchCartReqGen, skuId, CheckoutDataProviderEnum.CART.name())))
						{
							RequestGenerator addItemToCartReqGen = checkoutTestsHelper.addItemToCart(userName, password, skuId,String.valueOf(addItemQty) , ADD_OPERATION);
							System.out.println("\nPrinting CheckoutService addItemToCart API response :\n\n"+addItemToCartReqGen.respvalidate.returnresponseasstring());
							log.info("\nPrinting CheckoutService addItemToCart API response :\n\n"+addItemToCartReqGen.respvalidate.returnresponseasstring());
										
							AssertJUnit.assertTrue("Error while invoking CheckoutService addItemToCart API",
								addItemToCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));
										
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
				System.out.println("\nError while invoking SearchService API");
				log.error("\nError while invoking SearchService API");
				AssertJUnit.fail("Error while invoking SearchService API");
		}
				
		RequestGenerator fetchAllCouponsReqGen = checkoutTestsHelper.fetchAllCoupons(userName, password);
		System.out.println("\nPrinting CheckoutService fetchAllCoupons API response :\n\n"+fetchAllCouponsReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService fetchAllCoupons API response :\n\n"+fetchAllCouponsReqGen.respvalidate.returnresponseasstring());
		
		AssertJUnit.assertTrue("Error while invoking CheckoutService fetchAllCoupons API",
				fetchAllCouponsReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));
		
		RequestGenerator applyCouponReqGen = checkoutTestsHelper.applyCoupon(userName, password, fetchAllCouponsReqGen.respvalidate.NodeValue("data.coupon", false));
		System.out.println("\nPrinting CheckoutService applyCoupon API response :\n\n"+applyCouponReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService applyCoupon API response :\n\n"+applyCouponReqGen.respvalidate.returnresponseasstring());
		
		AssertJUnit.assertEquals("CheckoutService applyCoupon API is not working",
				Integer.parseInt(successRespCode), applyCouponReqGen.response.getStatus());
		
		//fetch user's cart, get the total amount to be paid
		RequestGenerator fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
		System.out.println("First fetch cart response!!!....");
		System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"+fetchCartReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"+fetchCartReqGen.respvalidate.returnresponseasstring());
			
		String value = fetchCartReqGen.respvalidate.NodeValue(CartNodes.CART_TOTAL_PRICE_DECIMAL.toString(), false);
		System.out.println("total amount to be paid is "+value);
		String value1 = value.substring(0, value.indexOf("."));
		
		int amount = Integer.valueOf(value1);
		amount = (amount+1)*100;
		
		value1 = String.valueOf(amount);
		System.out.println("amount in value1 is:"+value1);

		String payloadParams[] = new String[] {"${0}",value1};
		String customPayload = getPayloadAsString("paynowusingcod", payloadParams);
		System.out.println("final payload created is:" +customPayload);
				
		// place order using PayNowAPI as COD order
		RequestGenerator placeOrdersUsingPayNowReqGen = checkoutTestsHelper.placeOrderUsingPayNowAPI(userName, password, customPayload);
		System.out.println("\nPrinting CheckoutService placeOrdersUsingPayNowReqGen API response :\n\n"+placeOrdersUsingPayNowReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService placeOrdersUsingPayNowReqGen API response :\n\n"+placeOrdersUsingPayNowReqGen.respvalidate.returnresponseasstring());
				
	
		AssertJUnit.assertTrue("payNow API ppsID does not exists", 
				placeOrdersUsingPayNowReqGen.respvalidate.DoesNodeExists("ppsId".toString()));
			
		AssertJUnit.assertTrue("payNow API orderID does not exists", 
				placeOrdersUsingPayNowReqGen.respvalidate.DoesNodeExists("orderId".toString()));
				
		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime)/1000.0;
		
		System.out.println("\nTime taken to execute - TestCase - PayNowPaymentsServiceTests_PlaceOrderAsCOD_VerifySuccessResponse : "+timeTaken+" seconds\n");
		log.info("\nTime taken to execute - TestCase - PayNowPaymentsServiceTests_PlaceOrderAsCOD_VerifySuccessResponse : "+timeTaken+" seconds\n");

	}
	
	
	@Test(groups = { "Smoke", "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression" }, 
			dataProvider = "PayNowPaymentsServiceTests_PlaceOrderUsingCashBack_VerifyPaymentPlanInstrumentDP")
	public void PayNowPaymentsServiceTests_PlaceOrderUsingCashBack_VerifyPaymentPlanInstrument(String userName, String password, String addItemQty, String addOperation,String successRespCode)
	{	//userName ="testing420@myntra.com";
		long startTime = Calendar.getInstance().getTimeInMillis();
		//clear cart
		RequestGenerator clearCartReqGen = checkoutTestsHelper.clearCart(userName, password);
		String clearCartResponse = clearCartReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting CheckoutService clearCart API response :\n\n"+clearCartResponse);
		log.info("\nPrinting CheckoutService clearCart API response :\n\n"+clearCartResponse);
						
		//prepare cart
		List<Integer> styleIdList = checkoutTestsHelper.performSeachServiceToGetStyleIds(AC_SEARCH_PROD, AC_SEARCH_QTY, AC_SEARCH_RET_DOCS, AC_SEARCH_IS_FACET);
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
					getStyleDataReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));
					
				String response = getStyleDataReqGen.respvalidate.returnresponseasstring();
				List<Integer> ids = JsonPath.read(response, "$.data..styleOptions[*].id");
					
				for(int i=0 ; i< ids.size(); i++){
					String skuId = JsonPath.read(response, "$.data..styleOptions["+i+"].skuId").toString();
					String inventoryCount = JsonPath.read(response, "$.data..styleOptions["+i+"].inventoryCount").toString();
					String available = JsonPath.read(response, "$.data..styleOptions["+i+"].available").toString();
						
					if(!inventoryCount.equals(EMPTY_VALUE) && available.equals(TRUE_VALUE))
					{
						RequestGenerator fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
						System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"+fetchCartReqGen.respvalidate.returnresponseasstring());
						log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"+fetchCartReqGen.respvalidate.returnresponseasstring());
							
						if(StringUtils.isBlank(checkoutTestsHelper.getItemIdFromSKUId(fetchCartReqGen, skuId, CheckoutDataProviderEnum.CART.name())))
						{
							RequestGenerator addItemToCartReqGen = checkoutTestsHelper.addItemToCart(userName, password, skuId,String.valueOf(addItemQty) , ADD_OPERATION);
							System.out.println("\nPrinting CheckoutService addItemToCart API response :\n\n"+addItemToCartReqGen.respvalidate.returnresponseasstring());
							log.info("\nPrinting CheckoutService addItemToCart API response :\n\n"+addItemToCartReqGen.respvalidate.returnresponseasstring());
								
							AssertJUnit.assertTrue("Error while invoking CheckoutService addItemToCart API",
								addItemToCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));
								
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
				System.out.println("\nError while invoking SearchService API");
				log.error("\nError while invoking SearchService API");
				AssertJUnit.fail("Error while invoking SearchService API");
			}
			
		//fetch user's cart, get the total amount to be paid
		RequestGenerator fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
		System.out.println("First fetch cart response!!!....");
		System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"+fetchCartReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"+fetchCartReqGen.respvalidate.returnresponseasstring());
	
		String value = fetchCartReqGen.respvalidate.NodeValue(CartNodes.CART_TOTAL_PRICE_DECIMAL.toString(), false);
		System.out.println("total amount to be paid is "+value);
		
		String value1 = value.substring(0, value.indexOf("."));
		
		//credit cashback to user's account
		RequestGenerator creditCashBackReqGen = myntrCashServiceHelper.invokeCreditCashBack(userName, value1, "1", "", "description", 
				"GOOD_WILL", "GOOD_WILL", "001");
		String creditCashBackResponse = creditCashBackReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting MyntraCashService creditCashBack API response:\n\n"+creditCashBackResponse+"\n");
		log.info("\nPrinting MyntraCashService creditCashBack API response:\n\n"+creditCashBackResponse+"\n");
		
		AssertJUnit.assertEquals("MyntraCashService creditCashBack API is not working", creditCashBackReqGen.response.getStatus(), Integer.parseInt("200"));
		
		//apply cashback to cart
		RequestGenerator applyMyntCreditReqGen = checkoutTestsHelper.applyMyntCredit(userName, password, value1);
		System.out.println("\nPrinting CheckoutService applyMyntCredit API response :\n\n"+applyMyntCreditReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService applyMyntCredit API response :\n\n"+applyMyntCreditReqGen.respvalidate.returnresponseasstring());
		
		AssertJUnit.assertEquals("CheckoutService applyMyntCredit API is not working",
				Integer.parseInt(successRespCode), applyMyntCreditReqGen.response.getStatus());
		
		int amount = Integer.valueOf(value1);
		amount = (amount+1)*100;
		System.out.println("total paise to be given as Cashback are "+amount);
		
		value1 = String.valueOf(amount);
		System.out.println("amount in value1 is:"+value1);

		String payloadParams[] = new String[] {"${0}",value1};
		String customPayload = getPayloadAsString("paynowusingcashback", payloadParams);
		System.out.println("final payload created is:" +customPayload);
		
		// place order using PayNowAPI via CashBack
		RequestGenerator placeOrdersUsingPayNowReqGen = checkoutTestsHelper.placeOrderUsingPayNowAPI(userName, password, customPayload);
		System.out.println("\nPrinting CheckoutService placeOrdersUsingPayNowReqGen API response :\n\n"+placeOrdersUsingPayNowReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService placeOrdersUsingPayNowReqGen API response :\n\n"+placeOrdersUsingPayNowReqGen.respvalidate.returnresponseasstring());
					
		AssertJUnit.assertTrue("payNow API ppsID does not exists", 
				placeOrdersUsingPayNowReqGen.respvalidate.DoesNodeExists("ppsId".toString()));
					
		AssertJUnit.assertTrue("payNow API orderID does not exists", 
				placeOrdersUsingPayNowReqGen.respvalidate.DoesNodeExists("orderId".toString()));
						
		//get payment plan, check payment is done using cashback
		String ppsID = placeOrdersUsingPayNowReqGen.respvalidate.NodeValue("ppsId".toString(), false);
		RequestGenerator getPaymentPlanReqGen = checkoutTestsHelper.getPaymentPlan(ppsID);
		System.out.println("\nPrinting CheckoutService getPaymentPlanReqGen API response :\n\n"+getPaymentPlanReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService getPaymentPlanReqGen API response :\n\n"+getPaymentPlanReqGen.respvalidate.returnresponseasstring());
				
		AssertJUnit.assertTrue("Instrument type is not Cashback in getPaymentPlan",
			getPaymentPlanReqGen.respvalidate.NodeValue("paymentPlanEntry.paymentPlanInstrumentDetails.instrumentType".toString(), true).contains("CASHBACK"));
				
		
		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime)/1000.0;
		
		System.out.println("\nTime taken to execute - TestCase - PayNowPaymentsServiceTests_PlaceOrderUsingCashBack_VerifySuccessResponse : "+timeTaken+" seconds\n");
		log.info("\nTime taken to execute - TestCase - PayNowPaymentsServiceTests_PlaceOrderUsingCashBack_VerifySuccessResponse : "+timeTaken+" seconds\n");

	}

	
	@Test(groups = { "Smoke", "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression" }, 
			dataProvider = "PayNowPaymentsServiceTests_PlaceOrderUsingLoyaltyPoints_VerifySuccessResponseDP")
	public void PayNowPaymentsServiceTests_PlaceOrderUsingLoyaltyPoints_VerifySuccessResponse(String userName, String password, String addItemQty, String addOperation,String successRespCode)
	{	
		long startTime = Calendar.getInstance().getTimeInMillis();
		userName = "testing123@myntra.com";
		//clear cart
		RequestGenerator clearCartReqGen = checkoutTestsHelper.clearCart(userName, password);
		String clearCartResponse = clearCartReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting CheckoutService clearCart API response :\n\n"+clearCartResponse);
		log.info("\nPrinting CheckoutService clearCart API response :\n\n"+clearCartResponse);
		
		//prepare cart
		List<Integer> styleIdList = checkoutTestsHelper.performSeachServiceToGetStyleIds(CC_SEARCH_PROD, CC_SEARCH_QTY, CC_SEARCH_RET_DOCS, CC_SEARCH_IS_FACET);
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
					getStyleDataReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));
					
				String response = getStyleDataReqGen.respvalidate.returnresponseasstring();
				List<Integer> ids = JsonPath.read(response, "$.data..styleOptions[*].id");
					
				for(int i=0 ; i< ids.size(); i++)
				{
					String skuId = JsonPath.read(response, "$.data..styleOptions["+i+"].skuId").toString();
					String inventoryCount = JsonPath.read(response, "$.data..styleOptions["+i+"].inventoryCount").toString();
					String available = JsonPath.read(response, "$.data..styleOptions["+i+"].available").toString();
						
					if(!inventoryCount.equals(EMPTY_VALUE) && available.equals(TRUE_VALUE))
					{
						RequestGenerator fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
						System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"+fetchCartReqGen.respvalidate.returnresponseasstring());
						log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"+fetchCartReqGen.respvalidate.returnresponseasstring());
							
						if(StringUtils.isBlank(checkoutTestsHelper.getItemIdFromSKUId(fetchCartReqGen, skuId, CheckoutDataProviderEnum.CART.name())))
						{
							RequestGenerator addItemToCartReqGen = checkoutTestsHelper.addItemToCart(userName, password, skuId,String.valueOf(addItemQty) , ADD_OPERATION);
							System.out.println("\nPrinting CheckoutService addItemToCart API response :\n\n"+addItemToCartReqGen.respvalidate.returnresponseasstring());
							log.info("\nPrinting CheckoutService addItemToCart API response :\n\n"+addItemToCartReqGen.respvalidate.returnresponseasstring());
								
							AssertJUnit.assertTrue("Error while invoking CheckoutService addItemToCart API",
								addItemToCartReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(SUCCESS_STATUS_TYPE));
								
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
				System.out.println("\nError while invoking SearchService API");
				log.error("\nError while invoking SearchService API");
				AssertJUnit.fail("Error while invoking SearchService API");
			}
		
		// place order using paynow API, if some amount is remained pay using cod 
		RequestGenerator fetchCartReqGen = checkoutTestsHelper.operationFetchCart(userName, password);
		System.out.println("First fetch cart response!!!....");
		System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"+fetchCartReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"+fetchCartReqGen.respvalidate.returnresponseasstring());
			
		//get the total cart amount to be paid
		String value = fetchCartReqGen.respvalidate.NodeValue(CartNodes.CART_TOTAL_PRICE_DECIMAL.toString().trim(), false);
		System.out.println("total amount to be paid is "+value);
		value = value.substring(0, value.indexOf("."));

		//credit loyalty_points to user's account
		RequestGenerator creditLoyaltyPointsReqGen = loyalityPointsServiceHelper.invokeCreditLoyaltyPoints(userName,value,"0", "0", "0","automation","ORDER", "123", "GOOD_WILL");
		System.out.println("\nPrinting LoyalityPointsService creditLotaltyPoints API response :\n\n"+creditLoyaltyPointsReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting creditLotaltyPoints API response :\n\n"+creditLoyaltyPointsReqGen.respvalidate.returnresponseasstring());
					
		//apply user's LoyaltyPoints
		RequestGenerator applyLoyaltyPointsReqGen = checkoutTestsHelper.applyLoyaltyPoints(userName, password, value);
		System.out.println("\nPrinting CheckoutService applyLoyaltyPoints API response :\n\n"+applyLoyaltyPointsReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService applyLoyaltyPoints API response :\n\n"+applyLoyaltyPointsReqGen.respvalidate.returnresponseasstring());
		
		AssertJUnit.assertEquals("CheckoutService applyLoyaltyPoints API is not working",
				Integer.parseInt(successRespCode), applyLoyaltyPointsReqGen.response.getStatus());

		
		// place order using paynow API, if some amount is remained pay using cod 
		RequestGenerator fetchCartReqGen2 = checkoutTestsHelper.operationFetchCart(userName, password);
		System.out.println("First fetch cart response!!!....");
		System.out.println("\nPrinting CheckoutService operationFetchCart API response :\n\n"+fetchCartReqGen2.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService operationFetchCart API response :\n\n"+fetchCartReqGen2.respvalidate.returnresponseasstring());
	
		//get the remaining amount to be paid after applying loyalty points
		String amountRemained = fetchCartReqGen2.respvalidate.NodeValue(CartNodes.CART_TOTAL_PRICE_DECIMAL.toString(), false);
		System.out.println("amount to be paid in Cash "+amountRemained);
		
		//place order using PayNowForm API
		String payloadParams[] = new String[] {"${0}",amountRemained};
		String customPayload = getPayloadAsString("paynowusingcod", payloadParams);
		System.out.println("final payload created is:" +customPayload);
				
		// place order using PayNowAPI as COD order
		RequestGenerator placeOrdersUsingPayNowReqGen = checkoutTestsHelper.placeOrderUsingPayNowAPI(userName, password, customPayload);
		System.out.println("\nPrinting CheckoutService placeOrdersUsingPayNowReqGen API response :\n\n"+placeOrdersUsingPayNowReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService placeOrdersUsingPayNowReqGen API response :\n\n"+placeOrdersUsingPayNowReqGen.respvalidate.returnresponseasstring());
				
	
		AssertJUnit.assertTrue("payNow API is not working as expected, No PPSID generated", 
				placeOrdersUsingPayNowReqGen.respvalidate.DoesNodeExists("ppsId".toString()));
			
		AssertJUnit.assertTrue("payNow API is not working as expected, No orderId generated", 
				placeOrdersUsingPayNowReqGen.respvalidate.DoesNodeExists("orderId".toString()));
				

		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime)/1000.0;
		
		System.out.println("\nTime taken to execute - TestCase - PayNowPaymentsServiceTests_PlaceOrderUsingLoyaltyPoints_VerifySuccessResponse : "+timeTaken+" seconds\n");
		log.info("\nTime taken to execute - TestCase - PayNowPaymentsServiceTests_PlaceOrderUsingLoyaltyPoints_VerifySuccessResponse : "+timeTaken+" seconds\n");

	}
	
	@Test(groups = { "Smoke", "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression", "PPS" }, 
			dataProvider = "PayNowPaymentsServiceTests_GetPaymentPlanGivingPPSID_VerifyAPIIsUpDP")
	public void PayNowPaymentsServiceTests_GetPaymentPlanGivingPPSID_VerifyAPIIsUp(String PPSID, String successRespCode)
	{
		long startTime = Calendar.getInstance().getTimeInMillis();
		
		String PPSId = "\"" + PPSID +"\"";
		// get payment plan of given PPSID
		RequestGenerator getPaymentPlanReqGen = checkoutTestsHelper.getPaymentPlan(PPSId);
		System.out.println("\nPrinting CheckoutService placeOrdersUsingPayNowReqGen API response :\n\n"+getPaymentPlanReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService placeOrdersUsingPayNowReqGen API response :\n\n"+getPaymentPlanReqGen.respvalidate.returnresponseasstring());
		
		AssertJUnit.assertEquals("GetPaymentPlan API is not working as expected",
				Integer.parseInt(successRespCode), getPaymentPlanReqGen.response.getStatus());		
		
		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime)/1000.0;
		
		System.out.println("\nTime taken to execute - TestCase - PayNowPaymentsServiceTests_PlaceOrderUsingPayNowAPI_VerifyAPIIsUp : "+timeTaken+" seconds\n");
		log.info("\nTime taken to execute - TestCase - PayNowPaymentsServiceTests_PlaceOrderUsingPayNowAPI_VerifyAPIIsUp : "+timeTaken+" seconds\n");
		
	}

	@Test(groups = { "Smoke", "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression", "PPS" }, 
			dataProvider = "PayNowPaymentsServiceTests_GetPaymentPlanGivingPPSID_VerifyNegativeCasesDP")
	public void PayNowPaymentsServiceTests_GetPaymentPlanGivingPPSID_VerifyNegativeCases(String PPSID, String successRespCode)
	{
		long startTime = Calendar.getInstance().getTimeInMillis();
		
		String PPSId = "\"" + PPSID +"\"";
		// get payment plan of given PPSID
		RequestGenerator getPaymentPlanReqGen = checkoutTestsHelper.getPaymentPlan(PPSId);
		System.out.println("\nPrinting CheckoutService placeOrdersUsingPayNowReqGen API response :\n\n"+getPaymentPlanReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService placeOrdersUsingPayNowReqGen API response :\n\n"+getPaymentPlanReqGen.respvalidate.returnresponseasstring());
		
		AssertJUnit.assertEquals("GetPaymentPlan API is not working as expected",
				Integer.parseInt(successRespCode), getPaymentPlanReqGen.response.getStatus());		
	
		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime)/1000.0;
		
		System.out.println("\nTime taken to execute - TestCase - PayNowPaymentsServiceTests_PlaceOrderUsingPayNowAPI_VerifyAPIIsUp : "+timeTaken+" seconds\n");
		log.info("\nTime taken to execute - TestCase - PayNowPaymentsServiceTests_PlaceOrderUsingPayNowAPI_VerifyAPIIsUp : "+timeTaken+" seconds\n");
		
	}
}

