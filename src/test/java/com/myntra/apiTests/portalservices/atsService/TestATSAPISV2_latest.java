package com.myntra.apiTests.portalservices.atsService;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.testng.annotations.Test;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import com.jayway.jsonpath.JsonPath;
import com.myntra.apiTests.end2end.End2EndHelper;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.test.commons.service.Svc;
import com.myntra.test.commons.testbase.BaseTest;

public class TestATSAPISV2 extends BaseTest implements ATSConstants {
	static End2EndHelper e2e = new End2EndHelper();
	static ATSHelper atsHelper = new ATSHelper();
	static ATSHelperV2 atsHelperv2 = new ATSHelperV2();
	static APIUtilities utilities = new APIUtilities();
	
	@Test(priority = 1, groups = { "v1", "v2",
			"Regression", "testreportcheck"  }, dataProvider = "ats_AddressScoreVerify", dataProviderClass = ATSDataProvider.class)
	public void testAddressScoreAPIValidatation(String address, Boolean isGoodScore, Boolean isValidAddress)
			throws UnsupportedEncodingException {
		Object[] strPayload = { address };
		String jsonResp = atsHelperv2.getATSAddresscore(strPayload, withTenentId).getResponseBody();
		Double score = JsonPath.read(jsonResp, "$.score");
		String addressValue = JsonPath.read(jsonResp, "$.address");
		Boolean valid_address = JsonPath.read(jsonResp, "$.valid_address");
		if (isGoodScore)
			assertTrue(score > 0 && score <= 0.5);
		else
			assertTrue(score > 0.5 && score < 1.0);
		assertEquals(addressValue, address);
		assertEquals(valid_address, isValidAddress);
	}

	@Test(priority = 2, groups = { "appachhi", "v1", "v2",
			"Regression" }, dataProvider = "ats_SetandGetUserDetails", dataProviderClass = ATSDataProvider.class)
	public void testSetandGetATSUserDetailsVerification(Boolean isReturnAbuser, String reasonReturnAbuser,
			Boolean isCODThrottled, String reasonRTOAbuser, Boolean isFakeEmail, String reasonFakeEmail,
			String maxCODLable, String amount, String actMinCOD, String actMaxCOD)
			throws JsonParseException, JsonMappingException, IOException {
		String userEmail = signupPrefix.concat("1@myntra.com");
		String uidx = atsHelper.performSignup(userEmail).getEntry().getUidx();
		Svc service = atsHelperv2.postATSUserDetails(uidx, reasonReturnAbuser, isCODThrottled, reasonRTOAbuser,
				isFakeEmail, reasonFakeEmail, maxCODLable, isReturnAbuser, amount, withTenentId);
		assertEquals(VALID_RESP_CODE, service.getResponseStatus());
		String jsonResp = atsHelperv2.getATSUserDetails(uidx, withTenentId).getResponseBody();
		assertEquals(uidx, JsonPath.read(jsonResp, "$.login"));
		assertEquals(isReturnAbuser, JsonPath.read(jsonResp, "$.isReturnAbuser"));
		assertEquals(actMinCOD, JsonPath.read(jsonResp, "$.minCOD").toString());
		assertEquals(actMaxCOD, JsonPath.read(jsonResp, "$.maxCOD").toString());
		assertEquals(isFakeEmail, JsonPath.read(jsonResp, "$.isFakeEmail"));
		assertEquals(reasonRTOAbuser, JsonPath.read(jsonResp, "$.reasonCOD").toString());
	}

	@Test(priority = 3, groups = { "v1", "v2",
			"Regression" }, dataProvider = "ats_SetandGetOpenOrdersValue", dataProviderClass = ATSDataProvider.class)
	public void testSetandGetATSUserOpenOrdersValue(Double amount)
			throws JsonParseException, JsonMappingException, IOException {
		String userEmail = signupPrefix.concat("2@myntra.com");
		String uidx = atsHelper.performSignup(userEmail).getEntry().getUidx();
		Double beforeAmount = JsonPath.read(atsHelperv2.getATSOpenOrdersValue(uidx, withTenentId).getResponseBody(),
				"$.amount");
		assertEquals(VALID_RESP_CODE, atsHelperv2.putOpenOrdersValue(uidx, amount, withTenentId).getResponseStatus());
		String jsonResp = atsHelperv2.getATSOpenOrdersValue(uidx, withTenentId).getResponseBody();
		assertEquals(uidx, JsonPath.read(jsonResp, "$.login").toString());
		Double finalAmount = amount + beforeAmount;
		assertEquals(finalAmount, JsonPath.read(jsonResp, "$.amount"));
	}

	@Test(priority = 4, groups = { "v1", "v2",
			"Regression" }, dataProvider = "ats_SetandGetOutstandingLimit", dataProviderClass = ATSDataProvider.class)
	public void testPostandGetOutstandingCODLimit(String lable, Double outstandingCODLimit, Boolean limitCheck)
			throws JsonParseException, JsonMappingException, IOException {
		String userEmail = signupPrefix.concat("3@myntra.com");
		String uidx = atsHelper.performSignup(userEmail).getEntry().getUidx();
		Svc service = atsHelperv2.postATSUserDataForOutstandingLimit(uidx, lable, withTenentId);
		assertEquals(VALID_RESP_CODE, service.getResponseStatus());
		String jsonResp = atsHelperv2.getATSUserDataForOutstandingLimit(uidx, withTenentId).getResponseBody();
		assertEquals(uidx, JsonPath.read(jsonResp, "$.userid").toString());
		assertEquals(outstandingCODLimit, JsonPath.read(jsonResp, "$.outstandingCODLimit"));
		assertEquals(limitCheck, JsonPath.read(jsonResp, "$.limitCheck"));
	}

	@Test(priority = 5, groups = { "v1", "v2",
			"Regression" }, dataProvider = "ats_PutandGetAbuserDetails", dataProviderClass = ATSDataProvider.class)
	public void testPutandGetReturnAbuserDetails(Boolean isReturnAbuser)
			throws JsonParseException, JsonMappingException, IOException {
		String userEmail = signupPrefix.concat("4@myntra.com");
		String uidx = atsHelper.performSignup(userEmail).getEntry().getUidx();
		Svc service = atsHelperv2.putReturnAbuserDetails(uidx, isReturnAbuser, withTenentId);
		assertEquals(VALID_RESP_CODE, service.getResponseStatus());
		Svc svc = atsHelperv2.getReturnAbuserDetails(uidx, withTenentId);
		String jsonResp = svc.getResponseBody();
		assertEquals(uidx, JsonPath.read(jsonResp, "$.login"));
		assertEquals(isReturnAbuser, JsonPath.read(jsonResp, "$.isReturnAbuser"));
	}

	@Test(priority = 6, groups = { "v1", "v2",
			"Regression" }, dataProvider = "ats_PutandGetAbuserDetails", dataProviderClass = ATSDataProvider.class)
	public void testPutandGetFakeEmailDetails(Boolean isFakeEmail)
			throws JsonParseException, JsonMappingException, IOException {
		String userEmail = signupPrefix.concat("5@myntra.com");
		String uidx = atsHelper.performSignup(userEmail).getEntry().getUidx();
		Svc service = atsHelperv2.putFakeEmailDetails(uidx, isFakeEmail, withTenentId);
		assertEquals(VALID_RESP_CODE, service.getResponseStatus());
		String jsonResp = atsHelperv2.getFakeEmailDetails(uidx, withTenentId).getResponseBody();
		assertEquals(uidx, JsonPath.read(jsonResp, "$.login"));
		assertEquals(isFakeEmail, JsonPath.read(jsonResp, "$.isFakeEmail"));
	}

	@Test(priority = 7, groups = { "v1", "v2", "Regression" })
	public void testPutandGetLinkedPhone() throws JsonParseException, JsonMappingException, IOException {
		String uidx = " ";
		List<String> uidxList = new ArrayList<String>();
		String mobileNum = atsHelperv2.randomNumGenerate();
		for (int i = 1; i <= 10; i++) {
			String userEmail = signupPrefix + String.valueOf(i) + "6@myntra.com";
			uidx= atsHelper.performSignup(userEmail, mobileNum).getEntry().getUidx();
			atsHelperv2.putLinkedPhone(uidx, mobileNum, withTenentId);
			uidxList.add(uidx);
		}
		Collections.sort(uidxList);
		String jsonResp = atsHelperv2.getLinkedCustomerByPhone(mobileNum, withTenentId).getResponseBody();
		List<String> myList = new ArrayList<String>(JsonPath.read(jsonResp, "$.uidx"));
		Collections.sort(myList);
		assertEquals(myList, uidxList);
	}

	@Test(priority = 8, enabled = false)
	public void testPutandGetLinkedDevice() throws JsonParseException, JsonMappingException, IOException {
		String uidx = " ";
		List<String> uidxList = new ArrayList<String>();
		for (int i = 1; i <= 10; i++) {
			String userEmail = signupPrefix + String.valueOf(i) + "8@myntra.com";
			uidx = atsHelper.performSignup(userEmail).getEntry().getUidx();
			// Need to modify
			atsHelperv2.putLinkedPhone(uidx, SIGNUP_MOBILE_NUMBER, withTenentId);
			uidxList.add(uidx);
		}
		String jsonResp = atsHelperv2.getLinkedCustomerByDevice(SIGNUP_DEVICEID, withTenentId).getResponseBody();
		assertTrue(uidxList.containsAll(JsonPath.read(jsonResp, "$.uidx")));
	}

	@Test(priority = 9, groups = { "v1", "v2", "Regression" })
	public void testPutandGetLinkedEmail() throws JsonParseException, JsonMappingException, IOException {
		String uidx = " ";
		String userEmail = "";
		List<String> uidxList = new ArrayList<String>();
		String mobileNum = atsHelperv2.randomNumGenerate();
		for (int i = 1; i <= 10; i++) {
			userEmail = signupPrefix + String.valueOf(i) + "7@myntra.com";
			uidx = atsHelper.performSignup(userEmail, mobileNum).getEntry().getUidx();
			atsHelperv2.putLinkedPhone(uidx, mobileNum, withTenentId);
			uidxList.add(uidx);
		}
		Collections.sort(uidxList);
		String jsonResp = atsHelperv2.getLinkedCustomerByEmail(userEmail, withTenentId).getResponseBody();
		List<String> myList = new ArrayList<String>(JsonPath.read(jsonResp, "$.uidx"));
		myList.add(uidx);
		Collections.sort(myList);
		assertEquals(uidxList, myList);
	}

	@Test(priority = 10, groups = { "v1", "v2", "Regression" })
	public void testPutandGetIsCustomerBlocked() throws JsonParseException, JsonMappingException, IOException {
		String userEmail = signupPrefix.concat("9@myntra.com");
		atsHelper.performSignup(userEmail);
		String jsonRespInitial = atsHelperv2.getIsCustomerBlocked(userEmail, withTenentId).getResponseBody();
		System.out.println("jsonRespInitial : " + jsonRespInitial);
		assertFalse(JsonPath.read(jsonRespInitial, "$.blocked"));
		Svc svc = atsHelperv2.putCustomerBlocked(userEmail, true, 1, "blockedLogin", withTenentId);
		assertEquals(svc.getResponseStatus(), VALID_RESP_CODE);
		String jsonRespAfterBlock = atsHelperv2.getIsCustomerBlocked(userEmail, withTenentId).getResponseBody();
		System.out.println("jsonRespAfterBlock +" + jsonRespAfterBlock);
		assertTrue(JsonPath.read(jsonRespAfterBlock, "$.blocked"));
		Svc svcUnblock = atsHelperv2.putCustomerBlocked(userEmail, false, 1, "blockedLogin", withTenentId);
		assertEquals(svcUnblock.getResponseStatus(), VALID_RESP_CODE);
		String jsonRespUnblock = atsHelperv2.getIsCustomerBlocked(userEmail, withTenentId).getResponseBody();
		assertFalse(JsonPath.read(jsonRespUnblock, "$.blocked"));
	}

	@Test(priority = 11, groups = { "v1", "v2", "Regression" })
	public void testPutandGetBlockedCustomerByLogin() throws JsonParseException, JsonMappingException, IOException {
		String userEmail = signupPrefix.concat("10@myntra.com");
		atsHelper.performSignup(userEmail);
		String jsonRespInitial = atsHelperv2.getBlockedCustomerBy("login", userEmail, withTenentId).getResponseBody();
		assertFalse(Boolean.valueOf(jsonRespInitial));
		Svc svc = atsHelperv2.putCustomerBlocked(userEmail, true, 1, "blockedLogin", withTenentId);
		assertEquals(svc.getResponseStatus(), VALID_RESP_CODE);
		String jsonRespAfterBlock = atsHelperv2.getBlockedCustomerBy("login", userEmail, withTenentId)
				.getResponseBody();
		assertTrue(Boolean.valueOf(jsonRespAfterBlock));
		Svc svcUnblock = atsHelperv2.putCustomerBlocked(userEmail, false, 1, "blockedLogin", withTenentId);
		assertEquals(svcUnblock.getResponseStatus(), VALID_RESP_CODE);
		String jsonRespUnBlock = atsHelperv2.getBlockedCustomerBy("login", userEmail, withTenentId).getResponseBody();
		assertFalse(Boolean.valueOf(jsonRespUnBlock));
	}

	@Test(priority = 12, groups = { "v1", "v2", "Regression" })
	public void testPutandGetBlockedCustomerByPhone() throws JsonParseException, JsonMappingException, IOException {
		String userEmail = signupPrefix.concat("11@myntra.com");
		atsHelper.performSignup(userEmail);
		String jsonRespInitial = atsHelperv2.getBlockedCustomerBy("mobile", SIGNUP_MOBILE_NUMBER, withTenentId)
				.getResponseBody();
		assertFalse(Boolean.valueOf(jsonRespInitial));
		Svc svc = atsHelperv2.putCustomerBlocked(SIGNUP_MOBILE_NUMBER, true, 1, "blockedMobile", withTenentId);
		assertEquals(svc.getResponseStatus(), VALID_RESP_CODE);
		String jsonRespAfterBlock = atsHelperv2.getBlockedCustomerBy("mobile", SIGNUP_MOBILE_NUMBER, withTenentId)
				.getResponseBody();
		assertTrue(Boolean.valueOf(jsonRespAfterBlock));
		Svc svcUnblock = atsHelperv2.putCustomerBlocked(SIGNUP_MOBILE_NUMBER, false, 1, "blockedMobile", withTenentId);
		assertEquals(svcUnblock.getResponseStatus(), VALID_RESP_CODE);
		String jsonRespUnBlock = atsHelperv2.getBlockedCustomerBy("mobile", SIGNUP_MOBILE_NUMBER, withTenentId)
				.getResponseBody();
		assertFalse(Boolean.valueOf(jsonRespUnBlock));
	}

	@Test(priority = 13, groups = { "v1", "v2", "Regression" })
	public void testPutandGetBlockedCustomerByDevice() throws JsonParseException, JsonMappingException, IOException {
		String userEmail = signupPrefix.concat("12@myntra.com");
		atsHelper.performSignup(userEmail);
		String jsonRespInitial = atsHelperv2.getBlockedCustomerBy("deviceid", SIGNUP_DEVICEID, withTenentId)
				.getResponseBody();
		assertFalse(Boolean.valueOf(jsonRespInitial));
		Svc svc = atsHelperv2.putCustomerBlocked(SIGNUP_DEVICEID, true, 1, "blockedImei", withTenentId);
		assertEquals(svc.getResponseStatus(), VALID_RESP_CODE);
		String jsonRespAfterBlock = atsHelperv2.getBlockedCustomerBy("deviceid", SIGNUP_DEVICEID, withTenentId)
				.getResponseBody();
		assertTrue(Boolean.valueOf(jsonRespAfterBlock));
		Svc svcUnblock = atsHelperv2.putCustomerBlocked(SIGNUP_DEVICEID, false, 1, "blockedImei", withTenentId);
		assertEquals(svcUnblock.getResponseStatus(), VALID_RESP_CODE);
		String jsonRespUnBlock = atsHelperv2.getBlockedCustomerBy("deviceid", SIGNUP_DEVICEID, withTenentId)
				.getResponseBody();
		assertFalse(Boolean.valueOf(jsonRespUnBlock));
	}

	@Test(priority = 14, groups = { "v2", "Regression" })
	public void testGetATSDetailsWithoutClientID() throws JsonParseException, JsonMappingException, IOException {
		String userEmail = signupPrefix.concat("13@myntra.com");
		String uidx = atsHelper.performSignup(userEmail).getEntry().getUidx();
		Svc svc = atsHelperv2.getATSUserDetails(uidx, false);
		assertEquals(svc.getResponseStatus(), BADREQ_RESP_CODE);
	}

	@Test(priority = 15, groups = { "v2", "Regression" })
	public void testPostATSDetailsWithoutClientID() throws JsonParseException, JsonMappingException, IOException {
		String userEmail = signupPrefix.concat("14@myntra.com");
		String uidx= atsHelper.performSignup(userEmail).getEntry().getUidx();
		Svc service = atsHelperv2.postATSUserDetails(uidx, "HIGH_RETURNS", false, "COD102", false, "DEFAULT", "4",
				false, "0.0", false);
		assertEquals(service.getResponseStatus(), BADREQ_RESP_CODE);
	}

	@Test(priority = 16, groups = { "v2", "Regression" })
	public void testGetOutstandingCODLimitWithoutClientID()
			throws JsonParseException, JsonMappingException, IOException {
		String userEmail = signupPrefix.concat("15@myntra.com");
		String uidx = atsHelper.performSignup(userEmail).getEntry().getUidx();
		Svc service = atsHelperv2.getATSUserDataForOutstandingLimit(uidx, false);
		assertEquals(service.getResponseStatus(), BADREQ_RESP_CODE);
	}

	@Test(priority = 17, groups = { "v2", "Regression" })
	public void testGetOpenOrdersValueWithoutClientID() throws JsonParseException, JsonMappingException, IOException {
		String userEmail = signupPrefix.concat("16@myntra.com");
		String uidx = atsHelper.performSignup(userEmail).getEntry().getUidx();
		Svc service = atsHelperv2.getATSOpenOrdersValue(uidx, false);
		assertEquals(service.getResponseStatus(), BADREQ_RESP_CODE);
	}

	@Test(priority = 18, groups = { "v2", "Regression" })
	public void testPutOpenOrdersValueWithoutClientID() throws JsonParseException, JsonMappingException, IOException {
		String userEmail = signupPrefix.concat("17@myntra.com");
		String uidx = atsHelper.performSignup(userEmail).getEntry().getUidx();
		Svc service = atsHelperv2.putOpenOrdersValue(uidx, -1000.0, false);
		assertEquals(service.getResponseStatus(), BADREQ_RESP_CODE);
	}

	@Test(priority = 19, groups = { "v2", "Regression" })
	public void testGetReturnAbuserWithoutClientID() throws JsonParseException, JsonMappingException, IOException {
		String userEmail = signupPrefix.concat("18@myntra.com");
		String uidx = atsHelper.performSignup(userEmail).getEntry().getUidx();
		Svc service = atsHelperv2.getReturnAbuserDetails(uidx, false);
		assertEquals(service.getResponseStatus(), BADREQ_RESP_CODE);
	}

	@Test(priority = 20, groups = { "v2", "Regression" })
	public void testPutReturnAbuserWithoutClientID() throws JsonParseException, JsonMappingException, IOException {
		String userEmail = signupPrefix.concat("19@myntra.com");
		String uidx = atsHelper.performSignup(userEmail).getEntry().getUidx();
		Svc service = atsHelperv2.putReturnAbuserDetails(uidx, false, false);
		assertEquals(service.getResponseStatus(), BADREQ_RESP_CODE);
	}

	@Test(priority = 21, groups = { "v2", "Regression" })
	public void testGetBlockedCustomerByLoginWithoutClientID()
			throws JsonParseException, JsonMappingException, IOException {
		String userEmail = signupPrefix.concat("20@myntra.com");
		atsHelper.performSignup(userEmail);
		Svc service = atsHelperv2.getBlockedCustomerBy("login", userEmail, false);
		assertEquals(service.getResponseStatus(), BADREQ_RESP_CODE);
	}

	@Test(priority = 22, groups = { "v2", "Regression" })
	public void testGetBlockedCustomerByPhoneWithoutClientID()
			throws JsonParseException, JsonMappingException, IOException {
		Svc service = atsHelperv2.getBlockedCustomerBy("mobile", SIGNUP_MOBILE_NUMBER, false);
		assertEquals(service.getResponseStatus(), BADREQ_RESP_CODE);
	}

	@Test(priority = 23, groups = { "v2", "Regression" })
	public void testGetBlockedCustomerByDeviceIDWithoutClientID()
			throws JsonParseException, JsonMappingException, IOException {
		Svc service = atsHelperv2.getBlockedCustomerBy("deviceid", SIGNUP_DEVICEID, false);
		assertEquals(service.getResponseStatus(), BADREQ_RESP_CODE);
	}

	@Test(priority = 24, groups = { "v2", "Regression" })
	public void testIsCustomerBlockedWithoutClientID() throws JsonParseException, JsonMappingException, IOException {
		String userEmail = signupPrefix.concat("23@myntra.com");
		atsHelper.performSignup(userEmail);
		Svc service = atsHelperv2.getIsCustomerBlocked(userEmail, false);
		assertEquals(service.getResponseStatus(), BADREQ_RESP_CODE);
	}

	@Test(priority = 25, groups = { "v2", "Regression" })
	public void testPutCustomerBlockedWithoutClientID() throws JsonParseException, JsonMappingException, IOException {
		String userEmail = signupPrefix.concat("23@myntra.com");
		Svc service = atsHelperv2.putCustomerBlocked(userEmail, true, 1, "blockedLogin", false);
		assertEquals(service.getResponseStatus(), BADREQ_RESP_CODE);
	}

	@Test(priority = 26, groups = { "v2", "Regression" })
	public void testGetLinkedAccbyEmailWithoutClientID() throws JsonParseException, JsonMappingException, IOException {
		String userEmail = signupPrefix.concat("23@myntra.com");
		Svc service = atsHelperv2.getLinkedCustomerByEmail(userEmail, false);
		assertEquals(service.getResponseStatus(), BADREQ_RESP_CODE);
	}

	@Test(priority = 27, groups = { "v2", "Regression" })
	public void testGetLinkedAccbyDeviceWithoutClientID() throws JsonParseException, JsonMappingException, IOException {
		Svc service = atsHelperv2.getLinkedCustomerByDevice(SIGNUP_DEVICEID, false);
		assertEquals(service.getResponseStatus(), BADREQ_RESP_CODE);
	}

	@Test(priority = 28, groups = { "v2", "Regression" })
	public void testGetLinkedAccbyPhoneWithoutClientID() throws JsonParseException, JsonMappingException, IOException {
		Svc service = atsHelperv2.getLinkedCustomerByPhone(SIGNUP_MOBILE_NUMBER, false);
		assertEquals(service.getResponseStatus(), BADREQ_RESP_CODE);
	}

	@Test(priority = 29, groups = { "v2", "Regression" })
	public void testPutLinkedAccbyPhoneWithoutClientID() throws JsonParseException, JsonMappingException, IOException {
		String userEmail = signupPrefix.concat("24@myntra.com");
		String uidx = atsHelper.performSignup(userEmail).getEntry().getUidx();
		Svc service = atsHelperv2.putLinkedPhone(uidx, SIGNUP_MOBILE_NUMBER, false);
		assertEquals(service.getResponseStatus(), BADREQ_RESP_CODE);
	}

	@Test(priority = 30, groups = { "v2", "Regression" })
	public void testGetFakeEmailWithoutClientID() throws JsonParseException, JsonMappingException, IOException {
		String userEmail = signupPrefix.concat("25@myntra.com");
		String uidx = atsHelper.performSignup(userEmail).getEntry().getUidx();
		Svc service = atsHelperv2.getFakeEmailDetails(uidx, false);
		assertEquals(service.getResponseStatus(), BADREQ_RESP_CODE);
	}

	@Test(priority = 31, groups = { "v2", "Regression" })
	public void testPutFakeEmailWithoutClientID() throws JsonParseException, JsonMappingException, IOException {
		String userEmail = signupPrefix.concat("26@myntra.com");
		String uidx = atsHelper.performSignup(userEmail).getEntry().getUidx();
		Svc service = atsHelperv2.putFakeEmailDetails(uidx, false, false);
		assertEquals(service.getResponseStatus(), BADREQ_RESP_CODE);
	}

	@Test(priority = 32, groups = { "v2", "Regression" })
	public void testAddressScoreWithoutClientID() throws JsonParseException, JsonMappingException, IOException {
		Object[] strPayload = { address };
		Svc service = atsHelperv2.getATSAddresscore(strPayload, false);
		assertEquals(service.getResponseStatus(), BADREQ_RESP_CODE);
	}
}
