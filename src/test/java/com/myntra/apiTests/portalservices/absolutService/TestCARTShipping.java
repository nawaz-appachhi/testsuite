//package com.myntra.apiTests.portalservices.absolutService;
//
//import org.testng.annotations.*;
//import static org.testng.Assert.assertEquals;
//
//import org.apache.log4j.Logger;
//import org.json.simple.parser.ParseException;
//
//import com.jayway.jsonpath.JsonPath;
//import com.myntra.lordoftherings.Initialize;
//import com.myntra.lordoftherings.gandalf.RequestGenerator;
//import com.myntra.test.commons.testbase.BaseTest;
//
//public class TestCARTShipping extends BaseTest implements AbsolutConstants {
//
//	public TestCARTShipping() {
//		new CartServiceTestsDP();
//	}
//
//	static Logger log = Logger.getLogger(CartServiceTestSuite.class);
//	static AbsolutHelper absolutHelper = new AbsolutHelper();
//	static AbsolutHelperV2 absolutHelperV2 = new AbsolutHelperV2();
//	static Initialize init = new Initialize("./Data/configuration");
//
//	static int shippingChargesAmountFromSwitch, shippingChargesCartLimitFromSwitch, fraudUserShippingChargesFromSwitch,
//			valueShippingDiscountFromSwitch;
//
//	static String[] tokens;
//
//	@BeforeTest(alwaysRun = true)
//	public static void beforeTestMethod() {
//		// signup
//
//		// Sign In
//	}
//
//	@BeforeClass()
//	public static void beforeClassMethod() {
//		// get tokens
//		tokens = absolutHelperV2.idp_GetTokens("userName", "password");
//	}
//
//	@Test(priority = 0, dataProvider = "fraudUserVerifyShippingDP", dataProviderClass = CartServiceTestsDPV2.class)
//	public void testVerifyStandardShippingChargesForNormalUser(String tenantId, String skuId, String styleId,
//			String qty) throws ParseException {
//		getSwitchValues();
//		absolutHelperV2.addItemToCart(tokens[0], tenantId, skuId, styleId, qty);
//		RequestGenerator getCartReqGen = absolutHelperV2.getCart(tenantId, tokens[0]);
//		assertEquals(Integer.parseInt(SUCCESS_STATUS_RESP), getCartReqGen.response.getStatus());
//		String response = getCartReqGen.respvalidate.returnresponseasstring();
//		String shippingCharge = absolutHelperV2.getCartLevelCharges(response, "shipping");
//		double cartMrp = JsonPath.read(response, "$.price.mrp");
//		if (cartMrp < shippingChargesCartLimitFromSwitch)
//			assertEquals(String.valueOf(shippingChargesAmountFromSwitch), shippingCharge);
//		else
//			assertEquals("0", shippingCharge);
//		absolutHelperV2.clearCartUsingSecuredApi(tenantId, tokens[0]);
//	}
//
//	@Test(priority = 1, dataProvider = "fraudUserVerifyShippingDP", dataProviderClass = CartServiceTestsDPV2.class)
//	public void testVerifyUpdatedStandardShippingChargesForNormalUser(String tenantId, String skuId, String styleId,
//			String qty) throws ParseException {
//		// updates
//		getSwitchValues();
//		absolutHelperV2.addItemToCart(tokens[0], tenantId, skuId, styleId, qty);
//		RequestGenerator getCartReqGen = absolutHelperV2.getCart(tenantId, tokens[0]);
//		assertEquals(Integer.parseInt(SUCCESS_STATUS_RESP), getCartReqGen.response.getStatus());
//		String response = getCartReqGen.respvalidate.returnresponseasstring();
//		String shippingCharge = absolutHelperV2.getCartLevelCharges(response, "shipping");
//		double cartMrp = JsonPath.read(response, "$.price.mrp");
//		if (cartMrp < shippingChargesCartLimitFromSwitch)
//			assertEquals(String.valueOf(shippingChargesAmountFromSwitch), shippingCharge);
//		else
//			assertEquals("0", shippingCharge);
//		absolutHelperV2.clearCartUsingSecuredApi(tenantId, tokens[0]);
//	}
//
//	@Test(priority = 2, dataProvider = "fraudUserVerifyShippingDP", dataProviderClass = CartServiceTestsDPV2.class)
//	public void testVerifyStandardShippingChargesForFraudUser(String tenantId, String skuId, String styleId, String qty)
//			throws ParseException {
//		getSwitchValues();
//		// Make User Fraud
//		absolutHelperV2.addItemToCart(tokens[0], tenantId, skuId, styleId, qty);
//		RequestGenerator getCartReqGen = absolutHelperV2.getCart(tenantId, tokens[0]);
//		assertEquals(Integer.parseInt(SUCCESS_STATUS_RESP), getCartReqGen.response.getStatus());
//		String response = getCartReqGen.respvalidate.returnresponseasstring();
//		String shippingCharge = absolutHelperV2.getCartLevelCharges(response, "shipping");
//		assertEquals(String.valueOf(fraudUserShippingChargesFromSwitch), shippingCharge);
//		absolutHelperV2.clearCartUsingSecuredApi(tenantId, tokens[0]);
//	}
//
//	@Test(priority = 3, dataProvider = "fraudUserVerifyShippingDP", dataProviderClass = CartServiceTestsDPV2.class)
//	public void testVerifyUpdatedStandardShippingChargesForFraudUser(String tenantId, String skuId, String styleId,
//			String qty) throws ParseException {
//		//updateSwitchConfig(qty, qty, qty, qty);
//		
//		getSwitchValues();
//		absolutHelperV2.addItemToCart(tokens[0], tenantId, skuId, styleId, qty);
//		RequestGenerator getCartReqGen = absolutHelperV2.getCart(tenantId, tokens[0]);
//		assertEquals(Integer.parseInt(SUCCESS_STATUS_RESP), getCartReqGen.response.getStatus());
//		String response = getCartReqGen.respvalidate.returnresponseasstring();
//		String shippingCharge = absolutHelperV2.getCartLevelCharges(response, "shipping");
//		assertEquals(String.valueOf(fraudUserShippingChargesFromSwitch), shippingCharge);
//		absolutHelperV2.clearCartUsingSecuredApi(tenantId, tokens[0]);
//	}
//
//	// Same Day Delivery Charges
//	@Test()
//	public void testVerifySDDShippingCharges() {
//
//	}
//
//	// Next Day Deliver Chanrges
//	@Test()
//	public void testVerifyNDDShippingCharges() {
//
//	}
//
//	// Next Day Deliver Chanrges
//	@Test()
//	public void testVerifyValueShippingCharges() {
//
//	}
//
//	public static void updateSwitchConfig(String shipChargesCartLimit, String shipChargesAmount,
//			String shipChargesFraud, String shipChargesValueShip) {
//		String key = "\"shipping.charges.cartlimit\":" + shipChargesCartLimit + ",\"shipping.charges.amount\":"
//				+ shipChargesAmount + ",\"shipping.charges.returnabuser\":" + shipChargesFraud
//				+ ",\"myntra.valueShipping.charges\":" + shipChargesValueShip;
//		absolutHelper.updateSwitchConfigValues("cart", key, "default");
//	}
//
//	public static void getSwitchValues() {
//		RequestGenerator getSwitchData = absolutHelper.getSwitchConfigValues("cart");
//
//		shippingChargesCartLimitFromSwitch = JsonPath.read(getSwitchData.respvalidate.returnresponseasstring(),
//				"$.data[\"shipping.charges.cartlimit\"]");
//
//		shippingChargesAmountFromSwitch = JsonPath.read(getSwitchData.respvalidate.returnresponseasstring(),
//				"$.data[\"shipping.charges.amount\"]");
//
//		fraudUserShippingChargesFromSwitch = JsonPath.read(getSwitchData.respvalidate.returnresponseasstring(),
//				"$.data[\"shipping.charges.returnabuser\"]");
//
//		valueShippingDiscountFromSwitch = JsonPath.read(getSwitchData.respvalidate.returnresponseasstring(),
//				"$.data[\"myntra.valueShipping.charges\"]");
//	}
//
//}
