//package com.myntra.apiTests.portalservices.atsService;
//
//import static org.junit.Assert.assertFalse;
//import static org.testng.Assert.assertEquals;
//import static org.testng.Assert.assertTrue;
//
//import java.io.IOException;
//import java.io.UnsupportedEncodingException;
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.xml.bind.JAXBException;
//import org.codehaus.jettison.json.JSONException;
//import org.json.simple.parser.ParseException;
//import org.testng.annotations.BeforeClass;
//import org.testng.annotations.Test;
//
//import com.jayway.jsonpath.JsonPath;
//import com.myntra.apiTests.end2end.End2EndHelper;
//import com.myntra.apiTests.erpservices.CustomExceptions.SCMExceptions;
//import com.myntra.commons.exception.ManagerException;
//import com.myntra.idea.response.ProfileResponse;
//import com.myntra.test.commons.testbase.BaseTest;
//
//import argo.saj.InvalidSyntaxException;
//
//public class TestATSCCODAPIV2 extends BaseTest implements ATSConstants {
//	static End2EndHelper e2e = new End2EndHelper();
//	static ATSHelper atsHelper = new ATSHelper();
//	static ATSHelperV2 atsHelper2 = new ATSHelperV2();
//
//	static List<String> userEmailList = new ArrayList<String>();
//	static List<String> uidxList = new ArrayList<String>();
//
//	@BeforeClass
//	public static void beforeClassMethod() {
//		for (int i = 0; i < 9; i++) {
//			String userEmail = signupPrefix + String.valueOf(i) + "7@myntra.com";
//			ProfileResponse profileResponse = null;
//			try {
//				profileResponse = atsHelper.performSignup(userEmail);
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//			String uidx = profileResponse.getEntry().getUidx();
//			userEmailList.add(userEmail);
//			uidxList.add(uidx);
//		}
//	}
//
//	@Test(priority = 0, alwaysRun = true)
//	public void testCheckOpenOrdersvalueAtInitialStage() throws UnsupportedEncodingException {
//		for (int j = 0; j < 9; j++) {
//			String uidx = uidxList.get(j);
//			verifyOpenOrdersValue(uidx, "0.0");
//		}
//	}
//
//	@Test(priority = 1, alwaysRun = true)
//	public void testCheckOutstandingCODLimitAtInitialStage() throws UnsupportedEncodingException {
//		for (int j = 0; j < 9; j++) {
//			String uidx = uidxList.get(j);
//			verifyOutstandingCODLimit(uidx,"20000.0", true);
//		}
//	}
//
//	@Test(priority = 2, alwaysRun = true)
//	public void testGETATSUserDetailsAtInitialStage() throws UnsupportedEncodingException {
//		for (int i = 0; i < 9; i++) {
//			String uidx = uidxList.get(i);
//			verifyATSUserDetails(uidx,"299.0","20000.0");
//		}
//	}
//
//	@Test(priority = 3, alwaysRun = true, dataProvider = "users_POSTATSUserDetails", dataProviderClass = ATSDataProvider.class)
//	public void testPOSTAndGETATSUserDetails(int label, String outstandingCODLimit, String minCoOD, String maxCOD, Boolean limitCheck)
//			throws UnsupportedEncodingException {
//		String uidx = uidxList.get(label);
//		atsHelper2.postATSUserDetails(uidx, String.valueOf(label), false, withTenentId);
//		// Outstanding Limit Verify
//		verifyOutstandingCODLimit(uidx,outstandingCODLimit, limitCheck);
//		// Get user Details Verify
//		verifyATSUserDetails(uidx, minCoOD, maxCOD);
//	}
//
//	@Test(priority = 4, alwaysRun = true, dataProvider = "users_POSTATSUserDetails1", dataProviderClass = ATSDataProvider.class)
//	public void testDoOrderAndVerifyCCODLevels(int label, String outstandingCODLimit, String minCoOD, String maxCOD, Boolean limitCheck, String status)
//			throws ManagerException, IOException, JAXBException, InvalidSyntaxException, JSONException, ParseException,
//			SCMExceptions {
//		String userName = userEmailList.get(label);
//		String uidx = uidxList.get(label);
//		String orderId = e2e.createOrder(userName, Password, Pincode, new String[] { "3831:1" }, "", false, false,
//				false, "", false);
//		String finalAmount = atsHelper.getFinalAmountFromOrderId(orderId);
//		//Verify Making an Order
//		verifyOpenOrdersValue(uidx, finalAmount);
//		verifyOutstandingCODLimit(uidx, outstandingCODLimit, limitCheck);
//		float afterMaxCOD = (label == 4 || label == 6) ? Float.parseFloat(maxCOD)
//				: Float.parseFloat(maxCOD) - Float.parseFloat(finalAmount);
//		verifyATSUserDetails(uidx, minCoOD, Float.toString(afterMaxCOD));
//		atsHelper.changeOrderStatus(orderId, status);
//		verifyOpenOrdersValue(uidx, "0.0");
//		verifyOutstandingCODLimit(uidx, outstandingCODLimit, limitCheck);
//		verifyATSUserDetails(uidx, minCoOD, maxCOD);
//	}
//	
//	@Test(priority = 5, alwaysRun = true, dataProvider = "users_POSTATSUserDetails1", dataProviderClass = ATSDataProvider.class)
//	public void testDoMultiOrderAndVerifyCCODLevels(int label, String outstandingCODLimit, String minCoOD, String maxCOD, Boolean limitCheck, String status)
//			throws ManagerException, IOException, JAXBException, InvalidSyntaxException, JSONException, ParseException,
//			SCMExceptions {
//		String userName = userEmailList.get(label);
//		String uidx = uidxList.get(label);
//		String orderId = e2e.createOrder(userName, Password, Pincode, new String[] { "3831:2", "3832:2" }, "", false, false,
//				false, "", false);
//		String finalAmount = atsHelper.getFinalAmountFromOrderId(orderId);
//		//Verify Making an Order
//		verifyOpenOrdersValue(uidx, finalAmount);
//		verifyOutstandingCODLimit(uidx, outstandingCODLimit, limitCheck);
//		float afterMaxCOD = (label == 4 || label == 6) ? Float.parseFloat(maxCOD)
//				: Float.parseFloat(maxCOD) - Float.parseFloat(finalAmount);
//		verifyATSUserDetails(uidx, minCoOD, Float.toString(afterMaxCOD));
//		atsHelper.changeOrderStatus(orderId, status);
//		verifyOpenOrdersValue(uidx, "0.0");
//		verifyOutstandingCODLimit(uidx, outstandingCODLimit, limitCheck);
//		verifyATSUserDetails(uidx, minCoOD, maxCOD);
//	}
//	
//	@Test(priority = 6, alwaysRun = true, dataProvider = "users_POSTATSUserDetails1", dataProviderClass = ATSDataProvider.class)
//	public void testShippingChargesAddedBackAfterLastLineDelivery(int label, String outstandingCODLimit, String minCoOD, String maxCOD, Boolean limitCheck, String status)
//			throws ManagerException, IOException, JAXBException, InvalidSyntaxException, JSONException, ParseException,
//			SCMExceptions {
//		String userName = userEmailList.get(label);
//		String uidx = uidxList.get(label);
//		String orderId = e2e.createOrder(userName, Password, Pincode, new String[] {"3831:2"}, "", false, false,
//				false, "", false);
//		String finalAmount = atsHelper.getFinalAmountFromOrderId(orderId);
//		//Verify Making an Order
//		verifyOpenOrdersValue(uidx, finalAmount);
//		verifyOutstandingCODLimit(uidx, outstandingCODLimit, limitCheck);
//		float afterMaxCOD = (label == 4 || label == 6) ? Float.parseFloat(maxCOD)
//				: Float.parseFloat(maxCOD) - Float.parseFloat(finalAmount);
//		verifyATSUserDetails(uidx, minCoOD, Float.toString(afterMaxCOD));
//		atsHelper.changeOrderStatus(orderId, status);
//		verifyOpenOrdersValue(uidx, "0.0");
//		verifyOutstandingCODLimit(uidx, outstandingCODLimit, limitCheck);
//		verifyATSUserDetails(uidx, minCoOD, maxCOD);
//	}
//	
//	
//	
//	
//
//	@Test(priority = 3, alwaysRun = true, dataProvider = "users_ToOrderandVerify", dataProviderClass = ATSDataProvider.class)
//	public void testDoOrderandDeliverlVerifyATSAPIS(String userName, String uidx, String actuallimitCheck,
//			String aoutstandingCODLimit, String actMinCOD, String actMaxCOD, String isPrime)
//			throws ManagerException, IOException, JAXBException, InvalidSyntaxException, JSONException, ParseException,
//			SCMExceptions, InterruptedException {
//		String orderId = e2e.createOrder(userName, Password, Pincode, new String[] { "3831:1" }, "", false, false,
//				false, "", false);
//		// After Making an Order
//		String finalAmount = atsHelper.getFinalAmountFromOrderId(orderId);
//		System.out.println(finalAmount);
//		verifyOpenOrdersValue(uidx, finalAmount);
//		verifyOutstandingCODLimit(uidx, actuallimitCheck, aoutstandingCODLimit);
//		float afterMaxCOD = Boolean.valueOf(isPrime) ? Float.parseFloat(actMaxCOD)
//				: Float.parseFloat(actMaxCOD) - Float.parseFloat(finalAmount);
//		verifyATSUserDetails(uidx, actMinCOD, Float.toString(afterMaxCOD));
//		atsHelper.changeOrderStatus(orderId, "D");
//		// After Making Order Delivered
//		verifyOpenOrdersValue(uidx, "0.0");
//		if (aoutstandingCODLimit == "100000.0")
//			actuallimitCheck = "false";
//		verifyOutstandingCODLimit(uidx, actuallimitCheck, aoutstandingCODLimit);
//		verifyATSUserDetails(uidx, actMinCOD, actMaxCOD);
//	}
//
//	@Test(priority = 7, alwaysRun = true, dataProvider = "user_ToOrderandVerifyForPrimeUser", dataProviderClass = ATSDataProvider.class)
//	public void testDoOrderandDeliverlVerifyATSAPISForPrimeUser(String userName, String uidx, String actuallimitCheck,
//			String aoutstandingCODLimit, String actMinCOD, String actMaxCOD, String status)
//			throws ManagerException, IOException, JAXBException, InvalidSyntaxException, JSONException, ParseException,
//			SCMExceptions, InterruptedException {
//		String orderId1 = e2e.createOrder(userName, Password, Pincode, new String[] { "11889469:1" }, "", false, false,
//				false, "", false);
//		String orderId2 = e2e.createOrder(userName, Password, Pincode, new String[] { "11889469:1" }, "", false, false,
//				false, "", false);
//		// After Making an Order
//		String finalAmount = Float.toString(Float.parseFloat(atsHelper.getFinalAmountFromOrderId(orderId1))
//				+ Float.parseFloat(atsHelper.getFinalAmountFromOrderId(orderId2)));
//		System.out.println(finalAmount);
//		verifyOpenOrdersValue(uidx, finalAmount);
//		verifyOutstandingCODLimit(uidx, actuallimitCheck, aoutstandingCODLimit);
//		float afterMaxCOD = Float.parseFloat(aoutstandingCODLimit) - Float.parseFloat(finalAmount);
//		verifyATSUserDetails(uidx, actMinCOD, Float.toString(afterMaxCOD));
//		atsHelper.changeOrderStatus(orderId1, status);
//		// After Making Order Status Change
//		verifyOpenOrdersValue(uidx, atsHelper.getFinalAmountFromOrderId(orderId2));
//		verifyOutstandingCODLimit(uidx, actuallimitCheck, aoutstandingCODLimit);
//		verifyATSUserDetails(uidx, actMinCOD, actMaxCOD);
//	}
//
//	@Test(priority = 8, dataProvider = "user_ToOrderandVerifyForPrimeUser", dataProviderClass = ATSDataProvider.class)
//	public void testDoMultiOrderwithQuantityandDeliverlEachLineandVerifyATSAPIS(String userName, String uidx,
//			String actuallimitCheck, String aoutstandingCODLimit, String actMinCOD, String actMaxCOD, String status)
//			throws ManagerException, IOException, JAXBException, InvalidSyntaxException, JSONException, ParseException,
//			SCMExceptions {
//		String orderId = e2e.createOrder(userName, Password, Pincode, new String[] { "3831:2", "3866:2" }, "", false,
//				false, false, "", false);
//		List<String> listids = new ArrayList<>();
//		List<String> listfinal_amount = new ArrayList<>();
//		listids = atsHelper.getlineidsFromOrderId(orderId);
//	}
//
//	private void verifyATSUserDetails(String uidx, String actMinCOD, String actMaxCOD)
//			throws UnsupportedEncodingException {
//		String jsonResp = atsHelper2.getATSUserDetails(uidx, withTenentId).getResponseBody();
//		assertEquals(uidx, JsonPath.read(jsonResp, "$.login").toString());
//		assertFalse(JsonPath.read(jsonResp, "$.isReturnAbuser"));
//		assertEquals("COD102", JsonPath.read(jsonResp, "$.reasonCOD").toString());
//		assertEquals(actMinCOD, JsonPath.read(jsonResp, "$.minCOD").toString());
//		assertEquals(actMaxCOD, JsonPath.read(jsonResp, "$.maxCOD").toString());
//		assertFalse(JsonPath.read(jsonResp, "$.isFakeEmail"));
//	}
//
//	private void verifyOutstandingCODLimit(String uidx, String aoutstandingCODLimit, Boolean actuallimitCheck)
//			throws UnsupportedEncodingException {	
//		String jsonResp = atsHelper2.getATSUserDataForOutstandingLimit(uidx, withTenentId).getResponseBody();
//		assertEquals(uidx, JsonPath.read(jsonResp, "$.userid"));
//		assertEquals(aoutstandingCODLimit, JsonPath.read(jsonResp, "$.outstandingCODLimit"));
//		if(actuallimitCheck)
//		  assertTrue(JsonPath.read(jsonResp, "$.limitCheck"));
//		else 
//			assertFalse(JsonPath.read(jsonResp, "$.limitCheck"));
//			
//	}
//
//	private void verifyOpenOrdersValue(String uidx, String finalAmount) throws UnsupportedEncodingException {
//		String jsonResp = atsHelper2.getATSOpenOrdersValue(uidx, withTenentId).getResponseBody();
//		assertEquals(uidx, JsonPath.read(jsonResp, "$.login").toString());
//		Double xyz = Double.valueOf(finalAmount) == 0.0 ? 0.0 : -Double.valueOf(finalAmount);
//		assertEquals(xyz, JsonPath.read(jsonResp, "$.amount").toString());
//	}
//}
