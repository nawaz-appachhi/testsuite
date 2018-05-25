package com.myntra.apiTests.portalservices.atsService;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;
import org.codehaus.jettison.json.JSONException;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.jayway.jsonpath.JsonPath;
import com.myntra.apiTests.end2end.End2EndHelper;
import com.myntra.apiTests.erpservices.CustomExceptions.SCMExceptions;
import com.myntra.commons.exception.ManagerException;
import com.myntra.test.commons.testbase.BaseTest;

import argo.saj.InvalidSyntaxException;

public class TestATSCCODAPI extends BaseTest implements ATSConstants {
	static End2EndHelper e2e = new End2EndHelper();
	static ATSHelper atsHelper = new ATSHelper();

	@Test(priority = 0, alwaysRun = true, dataProvider = "users_ToCreateAndVerifyCCOD", dataProviderClass = ATSDataProvider.class)
	public void testCheckOpenOrdersvalueAtInitialStage(String uidx, String label, String codLimit,
			String actuallimitCheck, String actMinCOD, String actMaxCOD) throws UnsupportedEncodingException {
		String jsonResp = atsHelper.getOpenOrdersValue(uidx);
		String openOrdersAmount = JsonPath.read(jsonResp, "$.amount").toString();
		String login = JsonPath.read(jsonResp, "$.login").toString();
		Assert.assertEquals(uidx, login);
		Assert.assertEquals(openOrdersAmount, "0.0");
	}

	@Test(priority = 1, alwaysRun = true, dataProvider = "users_ToCreateAndVerifyCCOD", dataProviderClass = ATSDataProvider.class)
	public void testCheckOutstandingCODLimitAtInitialStage(String uidx, String label, String codLimit,
			String actuallimitCheck, String actMinCOD, String actMaxCOD) throws UnsupportedEncodingException {
		String jsonResp = atsHelper.getOutstandingCCOD(uidx);
		String outstandingCODLimit = JsonPath.read(jsonResp, "$.outstandingCODLimit").toString();
		String login = JsonPath.read(jsonResp, "$.userid").toString();
		String limitCheck = JsonPath.read(jsonResp, "$.limitCheck").toString();
		Assert.assertEquals(uidx, login);
		Assert.assertEquals(limitCheck, actuallimitCheck);
		Assert.assertEquals(outstandingCODLimit, "20000.0");
		atsHelper.post_ATSUserDetails(uidx, label,false);
		String jsonRespAfter = atsHelper.getOutstandingCCOD(uidx);
		String outstandingCODLimitAfter = JsonPath.read(jsonRespAfter, "$.outstandingCODLimit").toString();
		Assert.assertEquals(outstandingCODLimitAfter, codLimit);
	}

	@Test(priority = 2, alwaysRun = true, dataProvider = "users_ToCreateAndVerifyCCOD", dataProviderClass = ATSDataProvider.class)
	public void testGETATSUserDetailsAtInitialStage(String uidx, String label, String codLimit, String actuallimitCheck,
			String actMinCOD, String actMaxCOD) throws UnsupportedEncodingException {
		String jsonResp = atsHelper.getATSUserDetails(uidx);
		String login = JsonPath.read(jsonResp, "$.login").toString();
		String reasonCOD = JsonPath.read(jsonResp, "$.reasonCOD").toString();
		String minCOD = JsonPath.read(jsonResp, "$.minCOD").toString();
		String maxCOD = JsonPath.read(jsonResp, "$.maxCOD").toString();
		boolean isReturnAbuser = JsonPath.read(jsonResp, "$.isReturnAbuser");
		boolean isFakeEmail = JsonPath.read(jsonResp, "$.isFakeEmail");
		Assert.assertEquals(uidx, login);
		Assert.assertFalse(isReturnAbuser);
		Assert.assertEquals("COD102", reasonCOD);
		Assert.assertEquals(actMinCOD, minCOD);
		Assert.assertEquals(actMaxCOD, maxCOD);
		Assert.assertFalse(isFakeEmail);
	}

	@Test(priority = 3, alwaysRun = true, dataProvider = "users_ToOrderandVerify", dataProviderClass = ATSDataProvider.class)
	public void testDoOrderandDeliverlVerifyATSAPIS(String userName, String uidx, String actuallimitCheck,
			String aoutstandingCODLimit, String actMinCOD, String actMaxCOD, String isPrime)
			throws ManagerException, IOException, JAXBException, InvalidSyntaxException, JSONException, ParseException,
			SCMExceptions, InterruptedException {
		String orderId = e2e.createOrder(userName, Password, Pincode, new String[] { "3831:1" }, "", false, false, false, "", false);
		// After Making an Order
		String finalAmount = atsHelper.getFinalAmountFromOrderId(orderId);
		System.out.println(finalAmount);
		verifyOpenOrdersValue(uidx, finalAmount);
		verifyOutstandingCODLimit(uidx, actuallimitCheck, aoutstandingCODLimit);
		float afterMaxCOD = Boolean.valueOf(isPrime) ? Float.parseFloat(actMaxCOD)
				: Float.parseFloat(actMaxCOD) - Float.parseFloat(finalAmount);
		verifyATSUserDetails(uidx, actMinCOD, Float.toString(afterMaxCOD));
		atsHelper.changeOrderStatus(orderId, "D");
		// After Making Order Delivered
		verifyOpenOrdersValue(uidx, "0.0");
		if (aoutstandingCODLimit == "100000.0")
			actuallimitCheck = "false";
		verifyOutstandingCODLimit(uidx, actuallimitCheck, aoutstandingCODLimit);
		verifyATSUserDetails(uidx, actMinCOD, actMaxCOD);
	}

	@Test(priority = 4, dataProvider = "users_ToOrderandVerify", dataProviderClass = ATSDataProvider.class)
	public void testDoOrderandLostlVerifyATSAPIS(String userName, String uidx, String actuallimitCheck,
			String aoutstandingCODLimit, String actMinCOD, String actMaxCOD, String isPrime) throws ManagerException,
			IOException, JAXBException, InvalidSyntaxException, JSONException, ParseException, SCMExceptions {
		String orderId = e2e.createOrder(userName, Password, Pincode, new String[] { "3831:1" }, "", false, false,
				false, "", false);
		// After Making an Order
		String finalAmount = atsHelper.getFinalAmountFromOrderId(orderId);
		System.out.println(finalAmount);
		verifyOpenOrdersValue(uidx, finalAmount);
		verifyOutstandingCODLimit(uidx, actuallimitCheck, aoutstandingCODLimit);
		float afterMaxCOD = Boolean.valueOf(isPrime) ? Float.parseFloat(actMaxCOD)
				: Float.parseFloat(actMaxCOD) - Float.parseFloat(finalAmount);
		verifyATSUserDetails(uidx, actMinCOD, Float.toString(afterMaxCOD));
		atsHelper.changeOrderStatus(orderId, "L");
		// After Making Order Lost
		verifyOpenOrdersValue(uidx, "0.0");
		verifyOutstandingCODLimit(uidx, actuallimitCheck, aoutstandingCODLimit);
		verifyATSUserDetails(uidx, actMinCOD, actMaxCOD);
	}

	@Test(priority = 5, dataProvider = "users_ToCreateAndVerifyCCOD", dataProviderClass = ATSDataProvider.class)
	public void testDoOrderandCancelVerifyATSAPIS(String userName, String uidx, String actuallimitCheck,
			String aoutstandingCODLimit, String actMinCOD, String actMaxCOD, String isPrime) throws ManagerException,
			IOException, JAXBException, InvalidSyntaxException, JSONException, ParseException, SCMExceptions {
		String orderId = e2e.createOrder(userName, Password, Pincode, new String[] { "3831:1" }, "", false, false,
				false, "", false);
		// After Making an Order
		String finalAmount = atsHelper.getFinalAmountFromOrderId(orderId);
		System.out.println(finalAmount);
		verifyOpenOrdersValue(uidx, finalAmount);
		verifyOutstandingCODLimit(uidx, actuallimitCheck, aoutstandingCODLimit);
		float afterMaxCOD = Boolean.valueOf(isPrime) ? Float.parseFloat(actMaxCOD)
				: Float.parseFloat(actMaxCOD) - Float.parseFloat(finalAmount);
		verifyATSUserDetails(uidx, actMinCOD, Float.toString(afterMaxCOD));
		atsHelper.changeOrderStatus(orderId, "IC");
		// After Making Order Cancelled
		verifyOpenOrdersValue(uidx, "0.0");
		verifyOutstandingCODLimit(uidx, actuallimitCheck, aoutstandingCODLimit);
		verifyATSUserDetails(uidx, actMinCOD, actMaxCOD);
	}

	@Test(priority = 6, dataProvider = "users_ToCreateAndVerifyCCOD", dataProviderClass = ATSDataProvider.class)
	public void testDoOrderandRTOVerifyATSAPIS(String userName, String uidx, String actuallimitCheck,
			String aoutstandingCODLimit, String actMinCOD, String actMaxCOD, String isPrime) throws ManagerException,
			IOException, JAXBException, InvalidSyntaxException, JSONException, ParseException, SCMExceptions {
		String orderId = e2e.createOrder(userName, Password, Pincode, new String[] { "3831:1" }, "", false, false,
				false, "", false);
		// After Making an Order
		String finalAmount = atsHelper.getFinalAmountFromOrderId(orderId);
		System.out.println(finalAmount);
		verifyOpenOrdersValue(uidx, finalAmount);
		verifyOutstandingCODLimit(uidx, actuallimitCheck, aoutstandingCODLimit);
		float afterMaxCOD = Boolean.valueOf(isPrime) ? Float.parseFloat(actMaxCOD)
				: Float.parseFloat(actMaxCOD) - Float.parseFloat(finalAmount);
		verifyATSUserDetails(uidx, actMinCOD, Float.toString(afterMaxCOD));
		atsHelper.changeOrderStatus(orderId, "RTO");
		// After Making Order Cancelled
		verifyOpenOrdersValue(uidx, "0.0");
		verifyOutstandingCODLimit(uidx, actuallimitCheck, aoutstandingCODLimit);
		verifyATSUserDetails(uidx, actMinCOD, actMaxCOD);
	}

	@Test(priority = 7, alwaysRun = true, dataProvider = "user_ToOrderandVerifyForPrimeUser", dataProviderClass = ATSDataProvider.class)
	public void testDoOrderandDeliverlVerifyATSAPISForPrimeUser(String userName, String uidx, String actuallimitCheck,
			String aoutstandingCODLimit, String actMinCOD, String actMaxCOD, String status)
			throws ManagerException, IOException, JAXBException, InvalidSyntaxException, JSONException, ParseException,
			SCMExceptions, InterruptedException {
		String orderId1 = e2e.createOrder(userName, Password, Pincode, new String[] { "11889469:1" }, "", false, false,
				false, "", false);
		String orderId2 = e2e.createOrder(userName, Password, Pincode, new String[] { "11889469:1" }, "", false, false,
				false, "", false);
		// After Making an Order
		String finalAmount = Float.toString(Float.parseFloat(atsHelper.getFinalAmountFromOrderId(orderId1))
				+ Float.parseFloat(atsHelper.getFinalAmountFromOrderId(orderId2)));
		System.out.println(finalAmount);
		verifyOpenOrdersValue(uidx, finalAmount);
		verifyOutstandingCODLimit(uidx, actuallimitCheck, aoutstandingCODLimit);
		float afterMaxCOD = Float.parseFloat(aoutstandingCODLimit) - Float.parseFloat(finalAmount);
		verifyATSUserDetails(uidx, actMinCOD, Float.toString(afterMaxCOD));
		atsHelper.changeOrderStatus(orderId1, status);
		// After Making Order Status Change
		verifyOpenOrdersValue(uidx, atsHelper.getFinalAmountFromOrderId(orderId2));
		verifyOutstandingCODLimit(uidx, actuallimitCheck, aoutstandingCODLimit);
		verifyATSUserDetails(uidx, actMinCOD, actMaxCOD);
	}

	@Test(priority = 8, dataProvider = "user_ToOrderandVerifyForPrimeUser", dataProviderClass = ATSDataProvider.class)
	public void testDoMultiOrderwithQuantityandDeliverlEachLineandVerifyATSAPIS(String userName, String uidx,
			String actuallimitCheck, String aoutstandingCODLimit, String actMinCOD, String actMaxCOD, String status)
			throws ManagerException, IOException, JAXBException, InvalidSyntaxException, JSONException, ParseException,
			SCMExceptions {
		String orderId = e2e.createOrder(userName, Password, Pincode, new String[] { "3831:2", "3866:2" }, "", false,
				false, false, "", false);
		List<String> listids = new ArrayList<>();
		List<String> listfinal_amount = new ArrayList<>();
		listids = atsHelper.getlineidsFromOrderId(orderId);
		System.out.println(listids);

		// //After Making an Order
		// String finalAmount = atsHelper.getFinalAmountFromOrderId(orderId);
		// System.out.println(finalAmount);
		// verifyOpenOrdersValue(uidx, finalAmount);
		// verifyOutstandingCODLimit(uidx, actuallimitCheck, aoutstandingCODLimit);
		// float afterMaxCOD = Boolean.valueOf(isPrime) ? Float.parseFloat(actMaxCOD) :
		// Float.parseFloat(actMaxCOD) - Float.parseFloat(finalAmount);
		// verifyATSUserDetails(uidx, actMinCOD, Float.toString(afterMaxCOD));
		// atsHelper.changeOrderStatus(orderId, "L");
		// //After Making Order Lost
		// verifyOpenOrdersValue(uidx, "0.0");
		// verifyOutstandingCODLimit(uidx, actuallimitCheck, aoutstandingCODLimit);
		// verifyATSUserDetails(uidx, actMinCOD, actMaxCOD);
	}

	private void verifyATSUserDetails(String uidx, String actMinCOD, String actMaxCOD) throws UnsupportedEncodingException {
		String jsonResp = atsHelper.getATSUserDetails(uidx);
		String login = JsonPath.read(jsonResp, "$.login").toString();
		String reasonCOD = JsonPath.read(jsonResp, "$.reasonCOD").toString();
		String minCOD = JsonPath.read(jsonResp, "$.minCOD").toString();
		String maxCOD = JsonPath.read(jsonResp, "$.maxCOD").toString();
		boolean isReturnAbuser = JsonPath.read(jsonResp, "$.isReturnAbuser");
		boolean isFakeEmail = JsonPath.read(jsonResp, "$.isFakeEmail");
		Assert.assertEquals(uidx, login);
		Assert.assertFalse(isReturnAbuser);
		Assert.assertEquals("COD102", reasonCOD);
		Assert.assertEquals(actMinCOD, minCOD);
		Assert.assertEquals(actMaxCOD, maxCOD);
		Assert.assertFalse(isFakeEmail);
	}

	private void verifyOutstandingCODLimit(String uidx, String actuallimitCheck, String aoutstandingCODLimit) throws UnsupportedEncodingException {
		String jsonResp = atsHelper.getOutstandingCCOD(uidx);
		String outstandingCODLimit = JsonPath.read(jsonResp, "$.outstandingCODLimit").toString();
		String login = JsonPath.read(jsonResp, "$.userid").toString();
		String limitCheck = JsonPath.read(jsonResp, "$.limitCheck").toString();
		Assert.assertEquals(uidx, login);
		Assert.assertEquals(actuallimitCheck, limitCheck);
		Assert.assertEquals(aoutstandingCODLimit, outstandingCODLimit);
	}

	private void verifyOpenOrdersValue(String uidx, String finalAmount) throws UnsupportedEncodingException {
		String jsonResp = atsHelper.getOpenOrdersValue(uidx);
		String openOrdersAmount = JsonPath.read(jsonResp, "$.amount").toString();
		String login = JsonPath.read(jsonResp, "$.login").toString();
		Assert.assertEquals(login, uidx);
		Assert.assertEquals(openOrdersAmount, "-" + finalAmount);
	}
}