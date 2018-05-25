package com.myntra.apiTests.portalservices.atsService;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;

import com.jayway.jsonpath.JsonPath;
import com.myntra.apiTests.SERVICE_TYPE;
import com.myntra.apiTests.erpservices.Constants;
import com.myntra.apiTests.portalservices.ideaapi.IdeaApiHelper;
import com.myntra.idea.response.ProfileResponse;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.test.commons.service.HTTPMethods;
import com.myntra.test.commons.service.HttpExecutorService;
import com.myntra.test.commons.service.Svc;

public class SignupHelper implements ATSConstants{

	static ATSHelper atsHelper = new ATSHelper();
	
	static ATSHelperV2 atsHelperV2 = new ATSHelperV2();

	static APIUtilities utilities = new APIUtilities();
	
	static IdeaApiHelper ideaApiHelper = new IdeaApiHelper();
	
	public static void assertActiveState(String userStatus) {
		assertEquals(userStatus, "ACTIVE");
	}

	public static void assertBlacklistedState(String userStatus) {
		assertEquals(userStatus, "BLACKLISTED");
	}

	public static void assertNonReturnAbuser(Boolean isReturnAbuser) {
		assertFalse(isReturnAbuser);
	}

	public static void assertReturnAbuser(Boolean isReturnAbuser) {
		assertTrue(isReturnAbuser);
	}

	public static void assertMinCod(Double actual, Double expected) {
		assertEquals(actual, expected);
	}

	public static void assertMaxCod(Double actual, Double expected) {
		assertEquals(actual, expected);
	}
	
	public static HashMap<String, String> getHeaders() {
		HashMap<String, String> atsHeaders = new HashMap<String, String>();
		atsHeaders.put("Content-Type", "Application/json");
		atsHeaders.put("Accept", "Application/json");
		return atsHeaders;
	}
	
	public static void changeUserStatus(String uidx, String toStatus)
			throws JsonParseException, JsonMappingException, IOException {
		Object[] strPayload = { "myntra", toStatus, uidx };
		String payload = null;
		try {
			payload = utilities.readFileAsString("./Data/payloads/JSON/lgp/idea/changeUserStat");
		} catch (IOException e) {
			e.printStackTrace();
		}
		payload = utilities.preparepayload(payload, strPayload);

		Svc service = HttpExecutorService.executeHttpService(Constants.ATS_PATH.CHANGEUSER_STATUS, null,
				SERVICE_TYPE.IDEA_SVC.toString(), HTTPMethods.PUT, payload, getHeaders());
	}

	public static String getUserStatus(String userEmail) throws JsonParseException, JsonMappingException, IOException, InterruptedException {
		Thread.sleep(5000);
		RequestGenerator requestGeneratorProfile = ideaApiHelper.invokeIdeaGetProfileByEmail("myntra", userEmail);
		ProfileResponse profileResponseGet = (ProfileResponse) APIUtilities
				.getJsontoObject(requestGeneratorProfile.returnresponseasstring(), new ProfileResponse());
		return profileResponseGet.getEntry().getStatus();
	}

	public ProfileResponse performSignupwithDevice(String userEmail, String signupDeviceid, String phoneNum) throws JsonParseException, JsonMappingException, IOException {
		Object[] strPayload = { userEmail, signupDeviceid, phoneNum };
		String payload = null;
		try {
			payload = utilities.readFileAsString("./Data/payloads/JSON/ats/ideasignup");
		} catch (IOException e) {
			e.printStackTrace();
		}
		payload = utilities.preparepayload(payload, strPayload);

		Svc service = HttpExecutorService.executeHttpService(Constants.ATS_PATH.IDEASIGNUP, null,
				SERVICE_TYPE.IDEA_SVC.toString(), HTTPMethods.POST, payload, getHeaders());
		
		RequestGenerator requestGenerator = ideaApiHelper.invokeIdeaGetProfileByEmail("myntra", userEmail);
		ProfileResponse profileResponse = (ProfileResponse) APIUtilities
				.getJsontoObject(requestGenerator.returnresponseasstring(), new ProfileResponse());
		return profileResponse;
	}
	
	public static void verifyATSUserDetails(String uidx, Boolean isRAbuser, Double minCod, Double maxCod, Boolean withtenantId) throws UnsupportedEncodingException {
		String jsonResp = atsHelperV2.getATSUserDetails(uidx, withtenantId).getResponseBody();
		Double minCOD = JsonPath.read(jsonResp, "$.minCOD");
		Double maxCOD = JsonPath.read(jsonResp, "$.maxCOD");
		boolean isReturnAbuser = JsonPath.read(jsonResp, "$.isReturnAbuser");
		
		if(isRAbuser)
			assertReturnAbuser(isReturnAbuser);
		else
			assertNonReturnAbuser(isReturnAbuser);
		assertMinCod(minCOD, minCod);
		assertMaxCod(maxCOD, maxCod);	
	}
	
	public static void verifyLinkedAccountsByPhone(String phoneNum, List uidxList, Boolean withtenantId) throws UnsupportedEncodingException, InterruptedException {
		Thread.sleep(5000);
		String jsResp = atsHelperV2.getLinkedCustomerByPhone(phoneNum, withtenantId).getResponseBody();
		List<String> testList = new ArrayList<String>(JsonPath.read(jsResp, "$.uidx"));
		Collections.sort(testList);
		assertEquals(testList, uidxList);
	}
	
	public static void verifyLinkedAccountsByDevice(String deviceID, List uidxList) throws UnsupportedEncodingException {
		String jsResp = atsHelperV2.getLinkedCustomerByDevice(deviceID,false).getResponseBody();
		List<String> testList = new ArrayList<String>(JsonPath.read(jsResp, "$.uidx"));
		Collections.sort(testList);
		assertEquals(testList, uidxList);
	}

}
