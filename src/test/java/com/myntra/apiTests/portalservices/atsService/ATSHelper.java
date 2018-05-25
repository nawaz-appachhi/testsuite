package com.myntra.apiTests.portalservices.atsService;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.List;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.testng.Assert;

import com.myntra.apiTests.SERVICE_TYPE;
import com.myntra.apiTests.common.APINAME;
import com.myntra.apiTests.common.Myntra;
import com.myntra.apiTests.common.ServiceType;
import com.myntra.apiTests.erpservices.Constants;
import com.myntra.apiTests.portalservices.checkoutservice.CheckoutTestsHelper;
import com.myntra.apiTests.portalservices.ideaapi.IdeaApiHelper;
import com.myntra.apiTests.portalservices.idpservice.IDPServiceHelper;
import com.myntra.idea.response.ProfileResponse;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.boromir.DBUtilities;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.PayloadType;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.test.commons.service.HTTPMethods;
import com.myntra.test.commons.service.HttpExecutorService;
import com.myntra.test.commons.service.Svc;

public class ATSHelper implements ATSConstants {

	static APIUtilities utilities = new APIUtilities();
	static CheckoutTestsHelper checkoutTestHelper = new CheckoutTestsHelper();
	static IDPServiceHelper idpServiceHelper = new IDPServiceHelper();
	static IdeaApiHelper ideaApiHelper = new IdeaApiHelper();

	static Initialize init = new Initialize("Data/configuration");
	static Document doc;

	public static HashMap<String, String> getATSHeader() {
		HashMap<String, String> atsHeaders = new HashMap<String, String>();
		atsHeaders.put("Content-Type", "Application/json");
		atsHeaders.put("Accept", "Application/json");
		return atsHeaders;
	}

	public String getATSUserDetails(String uidx) throws UnsupportedEncodingException {
		Svc service = HttpExecutorService.executeHttpService(Constants.ATS_PATH.GET_ATSUSERDETAILS,
				new String[] { "" + uidx }, SERVICE_TYPE.ATS_SVC.toString(), HTTPMethods.GET, null, getATSHeader());
		return service.getResponseBody();
	}

	public String getOpenOrdersValue(String uidx) throws UnsupportedEncodingException {
		Svc service = HttpExecutorService.executeHttpService(Constants.ATS_PATH.GET_OPENORDERS_VALUE,
				new String[] { "" + uidx }, SERVICE_TYPE.ATS_SVC.toString(), HTTPMethods.GET, null, getATSHeader());
		return service.getResponseBody();
	}

	public String getOutstandingCCOD(String uidx) throws UnsupportedEncodingException {
		Svc service = HttpExecutorService.executeHttpService(Constants.ATS_PATH.GET_OUTSTANDINGCCOD,
				new String[] { "" + uidx }, SERVICE_TYPE.ATS_SVC.toString(), HTTPMethods.GET, null, getATSHeader());
		return service.getResponseBody();
	}

	public String getLinkedAccounts(String uidx) throws UnsupportedEncodingException {
		Svc service = HttpExecutorService.executeHttpService(Constants.ATS_PATH.GET_LINKEDACCOUNTS,
				new String[] { "" + uidx }, SERVICE_TYPE.ATS_SVC.toString(), HTTPMethods.GET, null, getATSHeader());
		return service.getResponseBody();
	}
	
	public String getLinkedAccountsByPhone(String phoneNum) throws UnsupportedEncodingException {
		Svc service = HttpExecutorService.executeHttpService(Constants.ATS_PATH.GET_LINKEDPHONE,
				new String[] { "" + phoneNum }, SERVICE_TYPE.ATS_SVC.toString(), HTTPMethods.GET, null, getATSHeader());
		return service.getResponseBody();
	}

	public void post_ATSUserDetails(String uidx, String label, Boolean isReturnAbuser)
			throws UnsupportedEncodingException {
		Object[] strPayload = { uidx, label, isReturnAbuser };
		String payload = null;
		try {
			payload = utilities.readFileAsString("./Data/payloads/JSON/ats/postatsdetails");
		} catch (IOException e) {
			e.printStackTrace();
		}
		payload = utilities.preparepayload(payload, strPayload);
		Svc service = HttpExecutorService.executeHttpService(Constants.ATS_PATH.POST_ATSUSERDETAILS, null,
				SERVICE_TYPE.ATS_SVC.toString(), HTTPMethods.POST, payload, getATSHeader());
		Assert.assertEquals(service.getResponseStatus(), VALID_RESP_CODE);
	}

	public void postATSUserDetails(String uidx, String reasonReturnAbuser, Boolean isCODThrottled,
			String reasonRTOAbuser, Boolean isFakeEmail, String reasonFakeEmail, String maxcod, Boolean isReturnAbuser,
			String amount) throws UnsupportedEncodingException {
		Object[] strPayload = { uidx, reasonReturnAbuser, isCODThrottled, reasonRTOAbuser, isFakeEmail, reasonFakeEmail,
				maxcod, isReturnAbuser, amount };
		String payload = null;
		try {
			payload = utilities.readFileAsString("./Data/payloads/JSON/ats/postatsuserdetails");
		} catch (IOException e) {
			e.printStackTrace();
		}
		payload = utilities.preparepayload(payload, strPayload);
		Svc service = HttpExecutorService.executeHttpService(Constants.ATS_PATH.POST_ATSUSERDETAILS, null,
				SERVICE_TYPE.ATS_SVC.toString(), HTTPMethods.POST, payload, getATSHeader());
		Assert.assertEquals(service.getResponseStatus(), VALID_RESP_CODE);
	}

	
	/**
	 * changeOrderStatus
	 * 
	 * @param orderId
	 * @param orderStatus
	 */
	public void changeOrderStatus(String orderId, String orderStatus) {
		DBUtilities.exUpdateQuery(
				"update order_release set status_code = " + orderStatus + " where order_id_fk = " + orderId + " ",
				"oms");
		DBUtilities.exUpdateQuery("update order_release set delivered_on = now() where order_id_fk = " + orderId + "",
				"oms");
		DBUtilities.exUpdateQuery(
				"update order_line set status_code = " + orderStatus + " where order_id_fk = " + orderId + "", "oms");
	}

	public String getFinalAmountFromOrderId(String orderId) {
		Map<String, Object> map = DBUtilities.exSelectQueryForSingleRecord(
				"select final_amount from order_release where order_id_fk = " + orderId, "oms");
		return map.get("final_amount").toString();
	}

	public String getOrderID(String response) {
		doc = Jsoup.parse(response);
		String title = doc.title();
		System.out.println("Order status: " + title);
		String name = doc.select(".msg-num strong").get(0).text();

		String app2 = name.replace("-", "").trim();
		return app2.toString();
	}

	@SuppressWarnings("rawtypes")
	public List<String> getlineidsFromOrderId(String orderId) {
		List map = DBUtilities.exSelectQuery("select id from order_release where order_id_fk = " + orderId, "oms");
		System.out.println("map Entries: " + map);
		return null;
	}

	public ProfileResponse performSignup(String userEmail)
			throws JsonParseException, JsonMappingException, IOException {
		RequestGenerator devApiSignUpReqGen = idpServiceHelper.performSignUpOperation(userEmail, Password,
				SIGNUP_USERTYPE, SIGNUP_GENDER_MALE, SIGNUP_TITLE_MR, SIGNUP_FIRST_NAME, SIGNUP_LAST_NAME, userEmail,
				SIGNUP_MOBILE_NUMBER);
		RequestGenerator requestGenerator = ideaApiHelper.invokeIdeaGetProfileByEmail("myntra", userEmail);
		ProfileResponse profileResponse = (ProfileResponse) APIUtilities
				.getJsontoObject(requestGenerator.returnresponseasstring(), new ProfileResponse());
		return profileResponse;
	}
	
	public ProfileResponse performSignup(String userEmail, String mobileNum)
			throws JsonParseException, JsonMappingException, IOException {
		RequestGenerator devApiSignUpReqGen = idpServiceHelper.performSignUpOperation(userEmail, Password,
				SIGNUP_USERTYPE, SIGNUP_GENDER_MALE, SIGNUP_TITLE_MR, SIGNUP_FIRST_NAME, SIGNUP_LAST_NAME, userEmail,
				mobileNum);
		RequestGenerator requestGenerator = ideaApiHelper.invokeIdeaGetProfileByEmail("myntra", userEmail);
		ProfileResponse profileResponse = (ProfileResponse) APIUtilities
				.getJsontoObject(requestGenerator.returnresponseasstring(), new ProfileResponse());
		return profileResponse;
	}
}
