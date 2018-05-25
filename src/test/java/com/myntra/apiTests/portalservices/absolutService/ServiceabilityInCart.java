package com.myntra.apiTests.portalservices.absolutService;

import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import com.jayway.jsonpath.JsonPath;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.test.commons.testbase.BaseTest;

public class ServiceabilityInCart extends BaseTest implements AbsolutConstants {

	public ServiceabilityInCart() {
		new CartServiceTestsDP();
	}

	static Logger log = Logger.getLogger(CartServiceTestSuite.class);
	static AbsolutHelper absolutHelper = new AbsolutHelper();
	static Initialize init = new Initialize("./Data/configuration");

	@Test(description = "Validating Serviceablity API is UP", dataProvider = "serviceabilityForSingleItem", dataProviderClass = CartServiceTestsDP.class)

	public void serviceabilityInCart_VerifyAPIIsUP(String userName, String password, String pincode, String styleId,
			String skuId, String normalDeliveryAvailablity, String expressDeliveryAvailability, String sddAvailability,
			String valueShippingAvailability, String cashOnDeliveryAvailability, String codDetails,
			String cardOnDeliveryAvailability, String tryNBuyAvailability, String successRespCode,
			String successStatusMsg, String successStatusType) {

		long startTime = Calendar.getInstance().getTimeInMillis();

		// Sign In
		String tokens[] = absolutHelper.idp_GetTokens(userName, password);
		AssertJUnit.assertEquals(successStatusMsg, tokens[2]);
		System.out.println("xid is:" + tokens[0]);

		// clear Cart
		RequestGenerator clearCartReqGen = absolutHelper.clearCart(tokens[0]);
		AssertJUnit.assertEquals("CheckoutService clearCart API is not working", Integer.parseInt(SUCCESS_STATUS_RESP),
				clearCartReqGen.response.getStatus());
		AssertJUnit.assertEquals("Cart is not Empty", "0",
				clearCartReqGen.respvalidate.NodeValue(AbsolutNodes.CART_TOTAL_COUNT.toString(), true));

		// Add an Item to cart
		RequestGenerator addItemToCartReqGen = absolutHelper.addItemToCart(tokens[0], skuId, "1", "ADD");
		System.out.println("\nPrinting CheckoutService addItemToCart API response :\n\n"
				+ addItemToCartReqGen.respvalidate.returnresponseasstring());

		// Hit Serviceability API
		RequestGenerator serviceabilityReqGen = absolutHelper.getServiceabilityForPincode(pincode, tokens[0]);
		System.out.println("\nPrinting Serviceability API response:\n\n"
				+ serviceabilityReqGen.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("Serviceability API is not working", Integer.parseInt(successRespCode),
				serviceabilityReqGen.response.getStatus());
		AssertJUnit.assertTrue("Serviceability API response status message is not a success status message",
				addItemToCartReqGen.respvalidate.NodeValue(AbsolutNodes.STATUS_MESSAGE.toString(), true)
				.contains(successStatusMsg));
		AssertJUnit.assertTrue("Serviceability API response status type is not a success status type",
				addItemToCartReqGen.respvalidate.NodeValue(AbsolutNodes.STATUS_TYPE.toString(), true)
				.contains(successStatusType));

		absolutHelper.performSignOutOperation(userName, tokens[1]);
		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;

		System.out.println("\nTime taken to execute - TestCase - serviceabilityInCart_verifyAPIIsUp : " + timeTaken
				+ " seconds\n");
	}

	@Test(description = "Validating Serviceablity for Pincode and Item combination having Single or Multiple Items in Cart", dataProvider = "serviceabilityForMultipleItems", dataProviderClass = CartServiceTestsDP.class)
	public void validatePincodeServiceability(String userName, String password, String pincode, String skuIds, 
			String normalDeliveryAvailablity, String expExpressDeliveryServiceability, String sddAvailability,
			String expValueShippingServiceability, String expCashOnDeliveryAvailability, String codDetails,
			String expCardOnDeliveryAvailable, String tryNBuyServiceable, String successRespCode,
			String successStatusMsg, String successStatusType) {

		long startTime = Calendar.getInstance().getTimeInMillis();

		// Sign In
		String tokens[] = absolutHelper.idp_GetTokens(userName, password);
		AssertJUnit.assertEquals(successStatusMsg, tokens[2]);
		System.out.println("xid is:" + tokens[0]);

		// clear Cart
		RequestGenerator clearCartReqGen = absolutHelper.clearCart(tokens[0]);
		AssertJUnit.assertEquals("CheckoutService clearCart API is not working", Integer.parseInt(SUCCESS_STATUS_RESP),
				clearCartReqGen.response.getStatus());
		AssertJUnit.assertEquals("Cart is not Empty", "0",
				clearCartReqGen.respvalidate.NodeValue(AbsolutNodes.CART_TOTAL_COUNT.toString(), true));

		// Add an Item to cart
		String[] skuIdList = skuIds.split(",");
		RequestGenerator addItemToCartReqGen;
		for(String skuId: skuIdList) {
			System.out.println("skuId = "+skuId);
			addItemToCartReqGen = absolutHelper.addItemToCart(tokens[0], skuId, "1", "ADD");
			System.out.println("\nPrinting CheckoutService addItemToCart API response :\n\n"
					+ addItemToCartReqGen.respvalidate.returnresponseasstring());
			AssertJUnit.assertEquals("addItemToCart API is not working",
					Integer.parseInt(successRespCode), addItemToCartReqGen.response.getStatus());
		}

		//Hit Serviceability API
		RequestGenerator serviceabilityReqGen = absolutHelper.getServiceabilityForPincode(pincode, tokens[0]);
		System.out.println("\nPrinting Serviceability API response:\n\n"
				+serviceabilityReqGen.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("Serviceability API is not working",
				Integer.parseInt(successRespCode), serviceabilityReqGen.response.getStatus());
		AssertJUnit.assertEquals("Pincode is wrong", pincode,
				(serviceabilityReqGen.respvalidate.NodeValue(AbsolutNodes.SERVICEABILITY_PINCODE.toString(), true)).replace("\"", ""));

		// Validate Serviceability for Express Delivery
		if(expExpressDeliveryServiceability.equalsIgnoreCase("NA")) {
			String expressServiceabilityNodeValue = serviceabilityReqGen.respvalidate.NodeValue(AbsolutNodes.SERVICEABILITY_EXPRESS_SERVICEABILITIES.toString(), true);
			AssertJUnit.assertEquals("Node should be empty", "[]", expressServiceabilityNodeValue);	
		} else {
			String expressDeliveryServiceabilityType = (serviceabilityReqGen.respvalidate.NodeValue(AbsolutNodes.SERVICEABILITY_EXPRESS_DELIVERY_SERVICEABILITY_TYPE.toString(), true)).replace("\"", "");
			String expressDeliveryServiceability = (serviceabilityReqGen.respvalidate.NodeValue(AbsolutNodes.SERVICEABILITY_EXPRESS_DELIVERY_SERVICEABILITY.toString(), true)).replace("\"", "");
			AssertJUnit.assertEquals("Express Delivery Serviceability Type is wrong", "EXPRESS", expressDeliveryServiceabilityType);
			AssertJUnit.assertEquals("Express Delivery is not Serviceable", expExpressDeliveryServiceability, expressDeliveryServiceability);			
		}

		// Validate Serviceability for Value Shipping
		if(expValueShippingServiceability.equalsIgnoreCase("NA")) {
			String valueShippingServiceabilityNodeValue = serviceabilityReqGen.respvalidate.NodeValue(AbsolutNodes.SERVICEABILITY_EXPRESS_SERVICEABILITIES.toString(), true);
			AssertJUnit.assertEquals("Node should be empty", "[]", valueShippingServiceabilityNodeValue);	
		} else {
			String valueShippingServiceabilityType = (serviceabilityReqGen.respvalidate.NodeValue(AbsolutNodes.SERVICEABILITY_VALUE_SHIPPING_SERVICEABILITY_TYPE.toString(), true)).replace("\"", "");
			String valueShippingServiceability = (serviceabilityReqGen.respvalidate.NodeValue(AbsolutNodes.SERVICEABILITY_VALUE_SHIPPING_SERVICEABILITY.toString(), true)).replace("\"", "");
			AssertJUnit.assertEquals("Value Shipping Serviceability Type is wrong", "VALUE_SHIPPING", valueShippingServiceabilityType);
			AssertJUnit.assertEquals("Value Shipping is not Serviceable", expValueShippingServiceability, valueShippingServiceability);		
		}

		//Validate Serviceability for Cash On Delivery
		String[] codDetailsList = codDetails.split(",");
		String expectedErrorCode = codDetailsList[0];
		String expectedErrorMessage = codDetailsList[1];
		String expectedCashOnly = codDetailsList[2];

		String codServiceability = (serviceabilityReqGen.respvalidate.NodeValue (AbsolutNodes.SERVICEABILITY_COD_SERVICEABILITY.toString(), true)).replace("\"", "");
		String codEntryServiceability = (serviceabilityReqGen.respvalidate.NodeValue(AbsolutNodes.SERVICEABILITY_COD_AVAILABILITY_SERVICEABILITY.toString(), true)).replace("\"", "");
		String errorCode = (serviceabilityReqGen.respvalidate.NodeValue(AbsolutNodes.SERVICEABILITY_COD_AVAILABILITY_ERROR_CODE.toString(), true)).replace("\"", "");
		String errorMessage = (serviceabilityReqGen.respvalidate.NodeValue(AbsolutNodes.SERVICEABILITY_COD_AVAILABILITY_ERROR_MSG.toString(), true)).replace("\"", "");
		String cashOnly = (serviceabilityReqGen.respvalidate.NodeValue(AbsolutNodes.SERVICEABILITY_COD_AVAILABILITY_CASH_ONLY.toString(), true)).replace("\"", "");

		AssertJUnit.assertEquals("Wrong COD Serviceability", expCashOnDeliveryAvailability, codServiceability);	
		AssertJUnit.assertEquals("Wrong COD Entry COD Serviceability", expCashOnDeliveryAvailability, codEntryServiceability);
		AssertJUnit.assertEquals("Wrong Error Code for COD Serviceability", expectedErrorCode, errorCode);
		AssertJUnit.assertEquals("Wrong Error Message for COD Serviceability", expectedErrorMessage, errorMessage);
		AssertJUnit.assertEquals("Wrong Cash Only Flag for COD Serviceability", expectedCashOnly, cashOnly);

		// Validate Serviceability for Card On Delivery
		String cardOnDeliveryServiceable = serviceabilityReqGen.respvalidate.NodeValue(AbsolutNodes.SERVICEABILITY_CARD_ON_DELIVERY_SERVICEABLE.toString(), true);
		if(expCardOnDeliveryAvailable.equalsIgnoreCase("Available")) {
			AssertJUnit.assertEquals("Card On Delivery should be true", "true", cardOnDeliveryServiceable);	
			AssertJUnit.assertEquals("Cash Only should be false", "false", cashOnly);	
		} else {
			AssertJUnit.assertEquals("Card On Delivery should be false", "false", cardOnDeliveryServiceable);	
			AssertJUnit.assertEquals("Cash Only should be true", "true", cashOnly);	
		}

		// Validate Serviceability for Try and Buy
		String tryAndBuyServiceable = serviceabilityReqGen.respvalidate.NodeValue(AbsolutNodes.SERVICEABILITY_TRY_AND_BUY_SERVICEABLE.toString(), true);
		AssertJUnit.assertEquals("Try and Buy is showing Un Serviceable", tryNBuyServiceable, tryAndBuyServiceable);
		
		absolutHelper.performSignOutOperation(userName, tokens[1]);
		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;

		System.out.println("\nTime taken to execute - TestCase - serviceabilityInCart_verifyAPIIsUp : " + timeTaken
				+ " seconds\n");
	}

}
