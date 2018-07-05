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

}
