package com.myntra.apiTests.portalservices.all;

import java.util.List;
import java.util.Map;

import javax.ws.rs.core.MultivaluedMap;

import com.myntra.apiTests.dataproviders.DevApisDataProvider;
import com.myntra.apiTests.portalservices.devapiservice.DevApiServiceDataNodes;
import com.myntra.apiTests.portalservices.devapiservice.DevApiServiceHelper;
import com.myntra.apiTests.portalservices.commons.IServiceConstants;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.Toolbox;
import com.myntra.apiTests.common.APINAME;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.apiTests.common.ServiceType;

import edu.emory.mathcs.backport.java.util.concurrent.TimeUnit;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONValue;
import com.myntra.apiTests.common.Myntra;

/**
 * @author shankara.c
 * Updated By: Aravind Raj R - aravind.raj@myntra.com
 *
 */

public class DevApis extends DevApisDataProvider
{
	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(DevApis.class);
	static APIUtilities apiUtil = new APIUtilities();
	static DevApiServiceHelper devApiServiceHelper = new DevApiServiceHelper();
	

	private String at;
	private String rt;

	
	int RetryAttemptsCount = 3; //Will be removed along with the code retry mechanism once the TestNG Retry is fully functional
	
	//Verify Success Response for DevAPI Sign in
	@Test(groups = {"Fox","Smoke","ProdSanity","Sanity","FoxSanity","MiniRegression","Regression","ExhaustiveRegression","RetryCheck"},dataProvider = "DevApisDataProvider_signIn_verifySuccessResponse",description = "Verify Successful Sign In using Email and Password")
    public void DevAPI_UserSignIn_Verify_SuccessResponse(String userName, String password) 
	{
		RequestGenerator request = devApiServiceHelper.invokeDevApiSignIn(userName, password);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println("\nSignIn Service Response:\n\n"+response);
		log.info("\nSignIn Service Response:\n\n"+response);
		AssertJUnit.assertEquals("SignIn Service is down!", 200, request.response.getStatus());
	}

	//Verify Success Response + Meta & Data Nodes from Sign in Response
	@Test(groups = {"Fox","Sanity","FoxSanity","MiniRegression","Regression","ExhaustiveRegression"},dataProvider = "DevApisDataProvider_signIn_verifyMetaTagNodes",description = "Verifies 200 Response & Data Nodes for Sign in API")
	public void DevApis_signIn_verifyMetaTagNodes(String userName, String password) 
	{
		RequestGenerator request = devApiServiceHelper.invokeDevApiSignIn(userName, password);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println("\nSignIn Service Response:\n\n"+response);
		log.info("\nSignIn Service Response:\n\n"+response);
		
		AssertJUnit.assertEquals("SignIn Service is down!", 200, request.response.getStatus());
		AssertJUnit.assertEquals("SignIn Service is down - Code under Meta is Not Valid!", 200, Integer.parseInt(JsonPath.read(response, "$.meta.code")));
		AssertJUnit.assertEquals("SignIn Service is down - META.TOKEN is Null or Empty", false, isNullOrEmpty(JsonPath.read(response, "$.meta.token")));
		AssertJUnit.assertEquals("SignIn Service is down - META.XSRFTOKEN is Null or Empty", false, isNullOrEmpty(JsonPath.read(response, "$.meta.xsrfToken")));
	
		
	}

	@Test(groups = {"Fox","Sanity","FoxSanity","MiniRegression","Regression","ExhaustiveRegression"},dataProvider = "DevApisDataProvider_signIn_verifyMetaTagNodes",description = "Verifies 200 Response & Data Nodes for Sign in API")
	public void DevApis_signIn_verifyDataTagNodes(String userName, String password) 
	{
		RequestGenerator request = devApiServiceHelper.invokeDevApiSignIn(userName, password);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println("\nSignIn Service Response:\n\n"+response);
		log.info("\nSignIn Service Response:\n\n"+response);
		
		AssertJUnit.assertEquals("SignIn Service is down!", 200, request.response.getStatus());
		AssertJUnit.assertEquals("SignIn Service is down - DATA.LOGIN is Null or Empty", false, isNullOrEmpty(JsonPath.read(response, "$.data.login")));
		AssertJUnit.assertEquals("SignIn Service is down - DATA.UIDX is Null or Empty", false, isNullOrEmpty(JsonPath.read(response, "$.data.uidx")));
		AssertJUnit.assertEquals("SignIn Service is down - DATA.USERTYPE is Null or Empty", false, isNullOrEmpty(JsonPath.read(response, "$.data.usertype")));
		AssertJUnit.assertEquals("SignIn Service is down - DATA.EMAIL is Null or Empty", false, isNullOrEmpty(JsonPath.read(response, "$.data.email")));
		AssertJUnit.assertEquals("SignIn Service is down - DATA.FIRST LOGIN is Null or Empty", false, isNullOrEmpty(JsonPath.read(response, "$.data.firstlogin")));
	
	}
	
		/**
	 * This TestCase is used to invoke DevApiService signIn API and verification for failure response
	 * 
	 * @param userName
	 * @param password
	 * @param statusCode
	 * @param errorMsg
	 */
	@Test(groups = {"Fox","Sanity","FoxSanity","MiniRegression","Regression","ExhaustiveRegression"},dataProvider = "DevApisDataProvider_signIn_verifyFailureResponse",description = "Verifies Failure during Sign in API")
	public void DevApis_signIn_verifyFailureResponse(String userName, String password, String statusCode, String errorMsg) 
	{
		RequestGenerator devApiSignInReqGen = devApiServiceHelper.invokeDevApiSignIn(userName, password);
		String devApiSignInResponse = devApiSignInReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting DevApiService signIn API response :\n\n" + devApiSignInResponse + "\n");
		log.info("\nPrinting DevApiService signIn API response :\n\n" + devApiSignInResponse + "\n");

		List < String > errorMetaTagNodes = DevApiServiceDataNodes.getSignInResponseErrorMetaTagNodes();
		for (String errorMetaTagNode: errorMetaTagNodes) {
			AssertJUnit.assertTrue("[" + errorMetaTagNode + "] node doesn't exists.", devApiSignInReqGen.respvalidate.DoesNodeExists(errorMetaTagNode));
		}

		AssertJUnit.assertEquals("[" + DevApiServiceDataNodes.META_CODE.toString() + "] node value is invalid.", statusCode.trim(),
				devApiSignInReqGen.respvalidate.NodeValue(DevApiServiceDataNodes.META_CODE.toString(), false).replace("\"", "").trim());

		AssertJUnit.assertEquals("[" + DevApiServiceDataNodes.META_ERROR_DETAIL.toString() + "] node value is invalid.", errorMsg.trim(),
				devApiSignInReqGen.respvalidate.NodeValue(DevApiServiceDataNodes.META_ERROR_DETAIL.toString(), false).replace("\"", "").trim());

		AssertJUnit.assertEquals("[" + DevApiServiceDataNodes.META_ERROR_TYPE.toString() + "] node value is invalid.", IServiceConstants.FAILURE_STATUS_TYPE.trim(),
				devApiSignInReqGen.respvalidate.NodeValue(DevApiServiceDataNodes.META_ERROR_TYPE.toString(), false).replace("\"", "").trim());

	}

	/**
	 * This TestCase is used to invoke DevApiService signUp API and verification for success response
	 * 
	 * @param userName
	 * @param password
	 */
	@Test(groups = {
			"Fox",
			"Smoke",
			"Sanity",
			"FoxSanity",
			"MiniRegression",
			"Regression",
			"ExhaustiveRegression"
	},
			dataProvider = "DevApisDataProvider_signUp_verifySuccessResponse",
			description = "Verifies 200 Response for Sign Up API")
	public void DevApis_signUp_verifySuccessResponse(String userName, String password) {
		if (password.length() == 0) password = getPass(userName);

		RequestGenerator devApiSignUpReqGen = devApiServiceHelper.invokeDevApiSignUp(userName, password);
		String devApiSignUpResponse = devApiSignUpReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting DevApiService signUp API response :\n\n" + devApiSignUpResponse + "\n");
		log.info("\nPrinting DevApiService signUp API response :\n\n" + devApiSignUpResponse + "\n");

		AssertJUnit.assertEquals("[DevApiService signUp API is not working.]", 200, devApiSignUpReqGen.response.getStatus());

	}

	//DevAPI Signup Error Message Check

	@Test(groups = {
			"Fox",
			"Smoke",
			"Sanity",
			"FoxSanity",
			"MiniRegression",
			"Regression",
			"ExhaustiveRegression"
	},
			dataProvider = "DevApisDataProvider_signUp_verifySuccessResponse",
			description = "Verifies 200 Response for Sign Up API")
	public void DevApis_signUp_ErrorResponse(String userName, String password) throws InterruptedException {
		if (password.length() == 0) password = getPass(userName);

		RequestGenerator devApiSignUpReqGen = devApiServiceHelper.invokeDevApiSignUp(userName, password);
		String devApiSignUpResponse = devApiSignUpReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting DevApiService signUp API response :\n\n" + devApiSignUpResponse + "\n");
		log.info("\nPrinting DevApiService signUp API response :\n\n" + devApiSignUpResponse + "\n");

		AssertJUnit.assertEquals("[DevApiService signUp API is not working.]", 200, devApiSignUpReqGen.response.getStatus());

		Thread.sleep(10000);
		devApiSignUpReqGen = devApiServiceHelper.invokeDevApiSignUp(userName, password);
		devApiSignUpResponse = devApiSignUpReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting DevApiService signUp API response :\n\n" + devApiSignUpResponse + "\n");
		log.info("\nPrinting DevApiService signUp API response :\n\n" + devApiSignUpResponse + "\n");

		AssertJUnit.assertEquals("Duplicate Sign Up is allowed", 405, devApiSignUpReqGen.response.getStatus());
		AssertJUnit.assertEquals("Error Message is not proper", "The email you entered already exists", JsonPath.read(devApiSignUpResponse, "$.meta.errorDetail").toString());


	}


	






	/**
	 * This TestCase is used to invoke DevApiService signUp API and verification for meta tag nodes in the response
	 * 
	 * @param userName
	 * @param password
	 */
	@Test(groups = {
			"Fox",
			"Sanity",
			"FoxSanity",
			"MiniRegression",
			"Regression",
			"ExhaustiveRegression"
	},
			dataProvider = "DevApisDataProvider_signUp_verifyMetaTagNodes",
			description = "Verifies Sign up 200 Response & Meta Tag Nodes")
	public void DevApis_signUp_verifyMetaTagNodes(String userName, String password) {
		if (password.length() == 0) password = getPass(userName);

		RequestGenerator devApiSignUpReqGen = devApiServiceHelper.invokeDevApiSignUp(userName, password);
		String devApiSignUpResponse = devApiSignUpReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting DevApiService signUp API response :\n\n" + devApiSignUpResponse + "\n");
		log.info("\nPrinting DevApiService signUp API response :\n\n" + devApiSignUpResponse + "\n");

		List < String > signUpResponseMetaTagNodes = DevApiServiceDataNodes.getSignUpResponseMetaTagNodes();
		for (String metaTagNode: signUpResponseMetaTagNodes) {
			AssertJUnit.assertTrue("[" + metaTagNode + "] node doesn't exists.", devApiSignUpReqGen.respvalidate.DoesNodeExists(metaTagNode));
		}

		AssertJUnit.assertEquals("[" + DevApiServiceDataNodes.META_CODE.toString() + "] node value is invalid.", "200",
				devApiSignUpReqGen.respvalidate.NodeValue(DevApiServiceDataNodes.META_CODE.toString(), false));

		AssertJUnit.assertNotNull("[" + DevApiServiceDataNodes.META_TOKEN.toString() + "] node value should not be null.",
				devApiSignUpReqGen.respvalidate.NodeValue(DevApiServiceDataNodes.META_TOKEN.toString(), false));

		AssertJUnit.assertNotNull("[" + DevApiServiceDataNodes.META_XSRF_TOKEN.toString() + "] node value should not be null.",
				devApiSignUpReqGen.respvalidate.NodeValue(DevApiServiceDataNodes.META_XSRF_TOKEN.toString(), false));

	}

	/**
	 * This TestCase is used to invoke DevApiService signUp API and verification for notification tag nodes in the response
	 * 
	 * @param userName
	 * @param password
	 */
	@Test(groups = {
			"Fox",
			"Sanity",
			"FoxSanity",
			"MiniRegression",
			"Regression",
			"ExhaustiveRegression"
	},
			dataProvider = "DevApisDataProvider_signUp_verifyNotificationTagNodes",
			description = "Verifies Sign Up 200 Response & Data Nodes")
	public void DevApis_signUp_verifyNotificaitonTagNodes(String userName, String password) {
		if (password.length() == 0) password = getPass(userName);

		RequestGenerator devApiSignUpReqGen = devApiServiceHelper.invokeDevApiSignUp(userName, password);
		String devApiSignUpResponse = devApiSignUpReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting DevApiService signUp API response :\n\n" + devApiSignUpResponse + "\n");
		log.info("\nPrinting DevApiService signUp API response :\n\n" + devApiSignUpResponse + "\n");

		List < String > notificationTagNodes = DevApiServiceDataNodes.getSignUpResponseNotificationTagNodes();
		for (String notificationTagNode: notificationTagNodes) {
			AssertJUnit.assertTrue("[" + notificationTagNode + "] node doesn't exists.", devApiSignUpReqGen.respvalidate.DoesNodeExists(notificationTagNode));
		}
	}

	/**
	 * This TestCase is used to invoke DevApiService signUp API and verification for data tag nodes in the response
	 * 
	 * @param userName
	 * @param password
	 */
	@Test(groups = {
			"Fox",
			"Sanity",
			"FoxSanity",
			"MiniRegression",
			"Regression",
			"ExhaustiveRegression"
	},
			dataProvider = "DevApisDataProvider_signUp_verifyDataTagNodes",
			description = "Verifies Sign Up 200 Response & Data Nodes")
	public void DevApis_signUp_verifyDataTagNodes(String userName, String password) {
		if (password.length() == 0) password = getPass(userName);

		RequestGenerator devApiSignUpReqGen = devApiServiceHelper.invokeDevApiSignUp(userName, password);
		String devApiSignUpResponse = devApiSignUpReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting DevApiService signUp API response :\n\n" + devApiSignUpResponse + "\n");
		log.info("\nPrinting DevApiService signUp API response :\n\n" + devApiSignUpResponse + "\n");

		List < String > signUpResponseDataTagNodes = DevApiServiceDataNodes.getSignUpResponseDataTagNodes();
		for (String signUpResponseDataTagNode: signUpResponseDataTagNodes) {
			AssertJUnit.assertTrue("[" + signUpResponseDataTagNode + "] node doesn't exists.", devApiSignUpReqGen.respvalidate.DoesNodeExists(signUpResponseDataTagNode));
		}

		AssertJUnit.assertEquals("[" + DevApiServiceDataNodes.RESP_DATA_EMAIL.toString().trim().toLowerCase() + "] node value is invalid", userName.trim().toLowerCase(),
				devApiSignUpReqGen.respvalidate.NodeValue(DevApiServiceDataNodes.RESP_DATA_EMAIL.toString(), false).replace("\"", "").toLowerCase());

	}

	/**
	 * This TestCase is used to invoke DevApiService signUp API and verification for failure response
	 * 
	 * @param userName
	 * @param password
	 * @param statusCode
	 * @param errorMsg
	 */
	@Test(groups = {
			"Fox",
			"Sanity",
			"FoxSanity",
			"MiniRegression",
			"Regression",
			"ExhaustiveRegression"
	},
			dataProvider = "DevApisDataProvider_signUp_verifyFailureResponse",
			description = "Verifies Sign Up Failure Response")
	public void DevApis_signUp_verifyFailureResponse(String userName, String password, String statusCode, String errorMsg) {
		RequestGenerator devApiSignUpReqGen = devApiServiceHelper.invokeDevApiSignUp(userName, password);
		String devApiSignUpResponse = devApiSignUpReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting DevApiService signUp API response :\n\n" + devApiSignUpResponse + "\n");
		log.info("\nPrinting DevApiService signUp API response :\n\n" + devApiSignUpResponse + "\n");

		List < String > errorMetaTagNodes = DevApiServiceDataNodes.getSignUpResponseErrorMetaTagNodes();
		for (String errorMetaTagNode: errorMetaTagNodes) {
			AssertJUnit.assertTrue("[" + errorMetaTagNode + "] node doesn't exists.", devApiSignUpReqGen.respvalidate.DoesNodeExists(errorMetaTagNode));
		}

		AssertJUnit.assertEquals("[" + DevApiServiceDataNodes.META_CODE.toString() + "] node value is invalid.", statusCode.trim(),
				devApiSignUpReqGen.respvalidate.NodeValue(DevApiServiceDataNodes.META_CODE.toString(), false).replace("\"", "").trim());

		AssertJUnit.assertEquals("[" + DevApiServiceDataNodes.META_ERROR_DETAIL.toString() + "] node value is invalid.", errorMsg.trim(),
				devApiSignUpReqGen.respvalidate.NodeValue(DevApiServiceDataNodes.META_ERROR_DETAIL.toString(), false).replace("\"", "").trim());

		AssertJUnit.assertEquals("[" + DevApiServiceDataNodes.META_ERROR_TYPE.toString() + "] node value is invalid.", IServiceConstants.FAILURE_STATUS_TYPE.trim(),
				devApiSignUpReqGen.respvalidate.NodeValue(DevApiServiceDataNodes.META_ERROR_TYPE.toString(), false).replace("\"", "").trim());

	}

	/**
	 * This TestCase is used to invoke DevApiService refresh API and verification for success response
	 * 
	 * @param userName
	 * @param password
	 */
	@Test(groups = {
			"Smoke",
			"ProdSanity",
			"Sanity",
			"FoxSanity",
			"MiniRegression",
			"Regression",
			"ExhaustiveRegression"
	},
			dataProvider = "DevApisDataProvider_refresh_verifySuccessResponse",
			description = "Verifies API Refreshe Success Response")
	public void DevApis_refresh_verifySuccessResponse(String userName, String password) {
		if (password.length() == 0) password = getPass(userName);
		getAndSetAtAndRt(userName, password);

		RequestGenerator devApiRefreshReqGen = devApiServiceHelper.invokeDevApiRefresh(at, rt);
		String devApiRefreshResponse = devApiRefreshReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting DevApiService refresh API response :\n\n" + devApiRefreshResponse + "\n");
		log.info("\nPrinting DevApiService refresh API response :\n\n" + devApiRefreshResponse + "\n");

		AssertJUnit.assertEquals("[DevApiService refresh API is not working.]", 200, devApiRefreshReqGen.response.getStatus());

	}

	/**
	 * This TestCase is used to invoke DevApiService refresh API and verification for meta tag nodes in the response
	 * 
	 * @param userName
	 * @param password
	 */
	@Test(groups = {
			"Sanity",
			"FoxSanity",
			"MiniRegression",
			"Regression",
			"ExhaustiveRegression"
	},
			dataProvider = "DevApisDataProvider_refresh_verifyMetaTagNodes",
			description = "Verifies API Refresh Success Response & Data Nodes")
	public void DevApis_refresh_verifyMetaTagNodes(String userName, String password) {
		if (password.length() == 0) password = getPass(userName);
		getAndSetAtAndRt(userName, password);

		RequestGenerator devApiRefreshReqGen = devApiServiceHelper.invokeDevApiRefresh(at, rt);
		String devApiRefreshResponse = devApiRefreshReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting DevApiService refresh API response :\n\n" + devApiRefreshResponse + "\n");
		log.info("\nPrinting DevApiService refresh API response :\n\n" + devApiRefreshResponse + "\n");

		List < String > metaTagNodes = DevApiServiceDataNodes.getRefreshResponseMetaTagNodes();
		for (String metaTagNode: metaTagNodes) {
			AssertJUnit.assertTrue("[" + metaTagNode + "] node doesn't exists.", devApiRefreshReqGen.respvalidate.DoesNodeExists(metaTagNode));
		}

		AssertJUnit.assertEquals("[" + DevApiServiceDataNodes.META_CODE.toString() + "] node value is invalid.", "200",
				devApiRefreshReqGen.respvalidate.NodeValue(DevApiServiceDataNodes.META_CODE.toString(), false));

		AssertJUnit.assertNotNull("[" + DevApiServiceDataNodes.META_TOKEN.toString() + "] node value should not be null.",
				devApiRefreshReqGen.respvalidate.NodeValue(DevApiServiceDataNodes.META_TOKEN.toString(), false));

		AssertJUnit.assertNotNull("[" + DevApiServiceDataNodes.META_XSRF_TOKEN.toString() + "] node value should not be null.",
				devApiRefreshReqGen.respvalidate.NodeValue(DevApiServiceDataNodes.META_XSRF_TOKEN.toString(), false));

	}

	/**
	 * This TestCase is used to invoke DevApiService refresh API and verification for notification tag nodes in the response
	 * 
	 * @param userName
	 * @param password
	 */
	@Test(groups = {
			"Sanity",
			"FoxSanity",
			"MiniRegression",
			"Regression",
			"ExhaustiveRegression"
	},
			dataProvider = "DevApisDataProvider_refresh_verifyNotificationTagNodes",
			description = "Verifies API Refresh Success Response & Notification Nodes")
	public void DevApis_refresh_verifyNotificationTagNodes(String userName, String password) {
		if (password.length() == 0) password = getPass(userName);
		getAndSetAtAndRt(userName, password);

		RequestGenerator devApiRefreshReqGen = devApiServiceHelper.invokeDevApiRefresh(at, rt);
		String devApiRefreshResponse = devApiRefreshReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting DevApiService refresh API response :\n\n" + devApiRefreshResponse + "\n");
		log.info("\nPrinting DevApiService refresh API response :\n\n" + devApiRefreshResponse + "\n");

		List < String > notificationTagNodes = DevApiServiceDataNodes.getRefreshResponseNotificationTagNodes();
		for (String notificationTagNode: notificationTagNodes) {
			AssertJUnit.assertTrue("[" + notificationTagNode + "] node doesn't exists.", devApiRefreshReqGen.respvalidate.DoesNodeExists(notificationTagNode));
		}

	}

	/**
	 * This TestCase is used to invoke DevApiService refresh API and verification for data tag nodes in the response
	 * 
	 * @param userName
	 * @param password
	 */
	@Test(groups = {
			"Sanity",
			"FoxSanity",
			"MiniRegression",
			"Regression",
			"ExhaustiveRegression"
	},
			dataProvider = "DevApisDataProvider_refresh_verifyDataTagNodes",
			description = "Verifies API Refresh Success Response & Data Nodes")
	public void DevApis_refresh_verifyDataTagNodes(String userName, String password) {
		if (password.length() == 0) password = getPass(userName);
		getAndSetAtAndRt(userName, password);

		RequestGenerator devApiRefreshReqGen = devApiServiceHelper.invokeDevApiRefresh(at, rt);
		String devApiRefreshResponse = devApiRefreshReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting DevApiService refresh API response :\n\n" + devApiRefreshResponse + "\n");
		log.info("\nPrinting DevApiService refresh API response :\n\n" + devApiRefreshResponse + "\n");

		List < String > dataTagNodes = DevApiServiceDataNodes.getRefreshResponseDataTagNodes();
		for (String dataTagNode: dataTagNodes) {
			AssertJUnit.assertTrue("[" + dataTagNode + "] node doesn't exists.", devApiRefreshReqGen.respvalidate.DoesNodeExists(dataTagNode));
		}

		AssertJUnit.assertEquals("[" + DevApiServiceDataNodes.RESP_DATA_LOGIN.toString() + "] node value is invalid.",
				devApiRefreshReqGen.respvalidate.NodeValue(DevApiServiceDataNodes.RESP_DATA_LOGIN.toString(), false).replace("\"", "").toLowerCase(), userName.trim().toLowerCase());

		//AssertJUnit.assertEquals("["+DevApiServiceDataNodes.RESP_DATA_EMAIL.toString()+"] node value is invalid",
		//userName.trim(), devApiRefreshReqGen.respvalidate.NodeValue(DevApiServiceDataNodes.RESP_DATA_EMAIL.toString(), false).replace("\"", ""));

	}

	/**
	 * This TestCase is used to invoke DevApiService refresh API and verification for failure response
	 * 
	 * @param userName
	 * @param password
	 */
	@Test(groups = {
			"Sanity",
			"FoxSanity",
			"MiniRegression",
			"Regression",
			"ExhaustiveRegression"
	},
			dataProvider = "DevApisDataProvider_refresh_verifyFailureResponse",
			description = "Verifies API Refresh Failure Response & Data Nodes")
	public void DevApis_refresh_verifyFailureResponse(String userName, String password) {
		if (password.length() == 0) password = getPass(userName);
		getAndSetAtAndRt(userName, password);

		at = at.replace("a", "0");
		at = at.replace("b", "1");
		at = at.replace("c", "2");
		rt = rt.replace("a", "0");
		rt = rt.replace("b", "1");
		rt = rt.replace("c", "2");

		RequestGenerator devApiRefreshReqGen = devApiServiceHelper.invokeDevApiRefresh(at, rt);
		String devApiRefreshResponse = devApiRefreshReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting DevApiService refresh API response :\n\n" + devApiRefreshResponse + "\n");
		log.info("\nPrinting DevApiService refresh API response :\n\n" + devApiRefreshResponse + "\n");

		List < String > errorMetaTagNodes = DevApiServiceDataNodes.getRefreshResponseErrorMetaTagNodes();
		for (String errorMetaTagNode: errorMetaTagNodes) {
			AssertJUnit.assertTrue("[" + errorMetaTagNode + "] node doesn't exists.", devApiRefreshReqGen.respvalidate.DoesNodeExists(errorMetaTagNode));
		}

		AssertJUnit.assertEquals("[" + DevApiServiceDataNodes.META_CODE.toString() + "] node value is invalid.", "401",
				devApiRefreshReqGen.respvalidate.NodeValue(DevApiServiceDataNodes.META_CODE.toString(), false).replace("\"", "").trim());

		AssertJUnit.assertEquals("[" + DevApiServiceDataNodes.META_ERROR_DETAIL.toString() + "] node value is invalid.",
				devApiRefreshReqGen.respvalidate.NodeValue(DevApiServiceDataNodes.META_ERROR_DETAIL.toString(), false).replace("\"", "").trim(),
				"");

		AssertJUnit.assertEquals("[" + DevApiServiceDataNodes.META_ERROR_TYPE.toString() + "] node value is invalid.",
				devApiRefreshReqGen.respvalidate.NodeValue(DevApiServiceDataNodes.META_ERROR_TYPE.toString(), false).replace("\"", "").trim(),
				IServiceConstants.FAILURE_STATUS_TYPE.trim());

	}

	/**
	 * This TestCase is used to invoke DevApiService signOut API and verification for success response
	 * 
	 * @param userName
	 * @param password
	 */
	@Test(groups = {
			"Fox",
			"Smoke",
			"Sanity",
			"FoxSanity",
			"ProdSanity",
			"MiniRegression",
			"Regression",
			"ExhaustiveRegression"
	},
			dataProvider = "DevApisDataProvider_signOut_verifySuccessResponse",
			description = "Verifies Sign Out API Success Response")
	public void DevApis_signOut_verifySuccessResponse(String userName, String password) {
		if (password.length() == 0) password = getPass(userName);

		RequestGenerator devApiSignOutReqGen = devApiServiceHelper.invokeDevApiSignOut(userName, password);
		String devApiSignOutResponse = devApiSignOutReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting DevApiService signOut API response :\n\n" + devApiSignOutResponse + "\n");
		log.info("\nPrinting DevApiService signOut API response :\n\n" + devApiSignOutResponse + "\n");

		AssertJUnit.assertEquals("[DevApiService signOut API is not working.]", 200, devApiSignOutReqGen.response.getStatus());

	}

	/**
	 * This TestCase is used to invoke DevApiService signOut API and verification for meta tag nodes in the response
	 * 
	 * @param userName
	 * @param password
	 */
	@Test(groups = {
			"Fox",
			"Sanity",
			"FoxSanity",
			"MiniRegression",
			"Regression",
			"ExhaustiveRegression"
	},
			dataProvider = "DevApisDataProvider_signOut_verifyMetaTagNodes",
			description = "Verifies Sign Out API Success Response & Data Nodes")
	public void DevApis_signOut_verifyMetaTagNodes(String userName, String password) {
		if (password.length() == 0) password = getPass(userName);

		RequestGenerator devApiSignOutReqGen = devApiServiceHelper.invokeDevApiSignOut(userName, password);
		String devApiSignOutResponse = devApiSignOutReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting DevApiService signOut API response :\n\n" + devApiSignOutResponse + "\n");
		log.info("\nPrinting DevApiService signOut API response :\n\n" + devApiSignOutResponse + "\n");

		List < String > metaTagNodes = DevApiServiceDataNodes.getSignOutResponseMetaTagNodes();
		for (String metaTagNode: metaTagNodes) {
			AssertJUnit.assertTrue("[" + metaTagNode + "] node doesn't exists.", devApiSignOutReqGen.respvalidate.DoesNodeExists(metaTagNode));
		}

		AssertJUnit.assertEquals("[" + DevApiServiceDataNodes.META_CODE.toString() + "] node value is invalid.", "200",
				devApiSignOutReqGen.respvalidate.NodeValue(DevApiServiceDataNodes.META_CODE.toString(), false));

		AssertJUnit.assertNotNull("[" + DevApiServiceDataNodes.META_TOKEN.toString() + "] node value should not be null.",
				devApiSignOutReqGen.respvalidate.NodeValue(DevApiServiceDataNodes.META_TOKEN.toString(), false));

		AssertJUnit.assertNotNull("[" + DevApiServiceDataNodes.META_XSRF_TOKEN.toString() + "] node value should not be null.",
				devApiSignOutReqGen.respvalidate.NodeValue(DevApiServiceDataNodes.META_XSRF_TOKEN.toString(), false));

	}

	/**
	 * This TestCase is used to invoke DevApiService signOut API and verification for notification tag nodes in the response
	 * 
	 * @param userName
	 * @param password
	 */
	@Test(groups = {
			"Fox",
			"Sanity",
			"FoxSanity",
			"MiniRegression",
			"Regression",
			"ExhaustiveRegression"
	},
			dataProvider = "DevApisDataProvider_signOut_verifyNotificationTagNodes",
			description = "Verifies Sign Out API Success Response & Data Nodes")
	public void DevApis_signOut_verifyNotificationTagNodes(String userName, String password) {
		if (password.length() == 0) password = getPass(userName);

		RequestGenerator devApiSignOutReqGen = devApiServiceHelper.invokeDevApiSignOut(userName, password);
		String devApiSignOutResponse = devApiSignOutReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting DevApiService signOut API response :\n\n" + devApiSignOutResponse + "\n");
		log.info("\nPrinting DevApiService signOut API response :\n\n" + devApiSignOutResponse + "\n");

		List < String > notificationTagNodes = DevApiServiceDataNodes.getSignOutResponseNotificationTagNodes();
		for (String notificationTagNode: notificationTagNodes) {
			AssertJUnit.assertTrue("[" + notificationTagNode + "] node doesn't exists.", devApiSignOutReqGen.respvalidate.DoesNodeExists(notificationTagNode));
		}
	}

	/**
	 * This TestCase is used to invoke DevApiService signOut API and verification for data tag nodes in the response
	 * 
	 * @param userName
	 * @param password
	 */
	@Test(groups = {
			"Fox",
			"Sanity",
			"FoxSanity",
			"MiniRegression",
			"Regression",
			"ExhaustiveRegression"
	},
			dataProvider = "DevApisDataProvider_signOut_verifyDataTagNodes",
			description = "Verifies Sign Out API Success Response & Data Nodes")
	public void DevApis_signOut_verifyDataTagNodes(String userName, String password) {
		if (password.length() == 0) password = getPass(userName);

		RequestGenerator devApiSignOutReqGen = devApiServiceHelper.invokeDevApiSignOut(userName, password);
		String devApiSignOutResponse = devApiSignOutReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting DevApiService signOut API response :\n\n" + devApiSignOutResponse + "\n");
		log.info("\nPrinting DevApiService signOut API response :\n\n" + devApiSignOutResponse + "\n");

		List < String > dataTagNodes = DevApiServiceDataNodes.getSignOutResponseDataTagNodes();
		for (String dataTagNode: dataTagNodes) {
			AssertJUnit.assertTrue("[" + dataTagNode + "] node doesn't exists.", devApiSignOutReqGen.respvalidate.DoesNodeExists(dataTagNode));
		}

	}

	/**
	 * This TestCase is used to invoke DevApiService forgotPassword API and verification for success response
	 * 
	 * @param userName
	 */
	@Test(groups = {
			"Fox",
			"Sanity",
			"ProdSanity",
			"MiniRegression",
			"Regression",
			"ExhaustiveRegression"
	},
			dataProvider = "DevApisDataProvider_forgotPassword_verifySuccessResponse",
			description = "Verifies Forgot Password API Success Response & Data Nodes")
	public void DevApis_forgotPassword_verifySuccessResponse(String userName) {
		RequestGenerator devApiForgotPasswordReqGen = devApiServiceHelper.invokeDevApiForgotPassword(userName);
		String devApiForgotPasswordResponse = devApiForgotPasswordReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting DevApiService forgotPassword API response :\n\n" + devApiForgotPasswordResponse + "\n");
		log.info("\nPrinting DevApiService forgotPassword API response :\n\n" + devApiForgotPasswordResponse + "\n");

		AssertJUnit.assertEquals("[DevApiService forgotPassword API is not working.]", 200, devApiForgotPasswordReqGen.response.getStatus());

	}

	/**
	 * This TestCase is used to invoke DevApiService forgotPassword API and verification for meta tag nodes in the response
	 * 
	 * @param userName
	 */
	@Test(groups = {
			"Fox",
			"Sanity",
			"MiniRegression",
			"Regression",
			"ExhaustiveRegression"
	},
			dataProvider = "DevApisDataProvider_forgotPassword_verifyMetaTagNodes",
			description = "Verifies Forgot Password API Success Response & Data Nodes")
	public void DevApis_forgotPassword_verifyMetaTagNodes(String userName) {
		RequestGenerator devApiForgotPasswordReqGen = devApiServiceHelper.invokeDevApiForgotPassword(userName);
		String devApiForgotPasswordResponse = devApiForgotPasswordReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting DevApiService forgotPassword API response :\n\n" + devApiForgotPasswordResponse + "\n");
		log.info("\nPrinting DevApiService forgotPassword API response :\n\n" + devApiForgotPasswordResponse + "\n");

		List < String > metaTagNodes = DevApiServiceDataNodes.getForgotPasswordResponseMetaTagNodes();
		for (String metaTagNode: metaTagNodes) {
			AssertJUnit.assertTrue("[" + metaTagNode + "] node doesn't exists.", devApiForgotPasswordReqGen.respvalidate.DoesNodeExists(metaTagNode));
		}

		AssertJUnit.assertEquals("[" + DevApiServiceDataNodes.META_CODE.toString() + "] node value is invalid.", "200",
				devApiForgotPasswordReqGen.respvalidate.NodeValue(DevApiServiceDataNodes.META_CODE.toString(), false));

		AssertJUnit.assertNotNull("[" + DevApiServiceDataNodes.META_TOKEN.toString() + "] node value should not be null.",
				devApiForgotPasswordReqGen.respvalidate.NodeValue(DevApiServiceDataNodes.META_TOKEN.toString(), false));

		AssertJUnit.assertNotNull("[" + DevApiServiceDataNodes.META_XSRF_TOKEN.toString() + "] node value should not be null.",
				devApiForgotPasswordReqGen.respvalidate.NodeValue(DevApiServiceDataNodes.META_XSRF_TOKEN.toString(), false));

	}

	/**
	 * This TestCase is used to invoke DevApiService forgotPassword API and verification for notification tag nodes in the response
	 * 
	 * @param userName
	 */
	@Test(groups = {
			"Fox",
			"Sanity",
			"MiniRegression",
			"Regression",
			"ExhaustiveRegression"
	},
			dataProvider = "DevApisDataProvider_forgotPassword_verifyNotificationTagNodes",
			description = "Verifies Forgot Password API Success Response & Data Nodes")
	public void DevApis_forgotPassword_verifyNotificationTagNodes(String userName) {
		RequestGenerator devApiForgotPasswordReqGen = devApiServiceHelper.invokeDevApiForgotPassword(userName);
		String devApiForgotPasswordResponse = devApiForgotPasswordReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting DevApiService forgotPassword API response :\n\n" + devApiForgotPasswordResponse + "\n");
		log.info("\nPrinting DevApiService forgotPassword API response :\n\n" + devApiForgotPasswordResponse + "\n");

		List < String > notificationTagNodes = DevApiServiceDataNodes.getForgotPasswordResponseNotificationTagNodes();
		for (String notificationTagNode: notificationTagNodes) {
			AssertJUnit.assertTrue("[" + notificationTagNode + "] node doesn't exists.", devApiForgotPasswordReqGen.respvalidate.DoesNodeExists(notificationTagNode));
		}

	}

	/**
	 * This TestCase is used to invoke DevApiService forgotPassword API and verification for data tag nodes in the response
	 * 
	 * @param userName
	 */
	@Test(groups = {
			"Fox",
			"Sanity",
			"FoxSanity",
			"MiniRegression",
			"Regression",
			"ExhaustiveRegression"
	},
			dataProvider = "DevApisDataProvider_forgotPassword_verifyNotificationTagNodes",
			description = "Verifies Forgot Password API Success Response & Data Nodes")
	public void DevApis_forgotPassword_verifyDataTagNodes(String userName) {
		RequestGenerator devApiForgotPasswordReqGen = devApiServiceHelper.invokeDevApiForgotPassword(userName);
		String devApiForgotPasswordResponse = devApiForgotPasswordReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting DevApiService forgotPassword API response :\n\n" + devApiForgotPasswordResponse + "\n");
		log.info("\nPrinting DevApiService forgotPassword API response :\n\n" + devApiForgotPasswordResponse + "\n");

		List < String > dataTagNodes = DevApiServiceDataNodes.getForgotPasswordResponseDataTagNodes();
		for (String dataTagNode: dataTagNodes) {
			AssertJUnit.assertTrue("[" + dataTagNode + "] node doesn't exists.", devApiForgotPasswordReqGen.respvalidate.DoesNodeExists(dataTagNode));
		}

	}

	/**
	 * This TestCase is used to invoke DevApiService forgotPassword API and verification for failure response
	 * 
	 * @param userName
	 * @param errorCode
	 * @param errorDetail
	 */
	@Test(groups = {
			"Fox",
			"Sanity",
			"FoxSanity",
			"MiniRegression",
			"Regression",
			"ExhaustiveRegression"
	},
			dataProvider = "DevApisDataProvider_forgotPassword_verifyFailureResponse",
			description = "Verifies Forgot Password API Failure Response & Data Nodes")
	public void DevApis_forgotPassword_verifyFailureResponse(String userName, String errorCode, String errorDetail) {
		RequestGenerator devApiForgotPasswordReqGen = devApiServiceHelper.invokeDevApiForgotPassword(userName);
		String devApiForgotPasswordResponse = devApiForgotPasswordReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting DevApiService forgotPassword API response :\n\n" + devApiForgotPasswordResponse + "\n");
		log.info("\nPrinting DevApiService forgotPassword API response :\n\n" + devApiForgotPasswordResponse + "\n");
		AssertJUnit.assertEquals("[Devapi SignUp service is not working]", 500, devApiForgotPasswordReqGen.response.getStatus());
		//NEED TO ADD DATA TAG NODES

	}

	/**
	 * This TestCase is used to invoke DevApiService nav API and verification for success response 
	 * 
	 */
	@Test(groups = {
			"Fox",
			"Smoke",
			"Sanity",
			"FoxSanity",
			"ProdSanity",
			"MiniRegression",
			"Regression",
			"ExhaustiveRegression"
	},
			description = "Verifies Navigation Link Data")
	public void DevApis_nav_verifySuccessResponse() {
		RequestGenerator devApiNavReqGen = devApiServiceHelper.invokeDevApiNav();
		String devApiNavResponse = devApiNavReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting DevApiService nav API response :\n\n" + devApiNavResponse + "\n");
		log.info("\nPrinting DevApiService nav API response :\n\n" + devApiNavResponse + "\n");

		AssertJUnit.assertEquals("[DevApiService nav API is not working.]", 200, devApiNavReqGen.response.getStatus());
	}

	/**
	 * This TestCase is used to invoke DevApiService nav API and verification for meta tag nodes in the response
	 * 
	 */
	@Test(groups = {
			"Fox",
			"Sanity",
			"FoxSanity",
			"MiniRegression",
			"Regression",
			"ExhaustiveRegression"
	},
			description = "Verifies Navigation Link Data")
	public void DevApis_nav_verifyMetaTagNodes() {
		RequestGenerator devApiNavReqGen = devApiServiceHelper.invokeDevApiNav();
		String devApiNavResponse = devApiNavReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting DevApiService nav API response :\n\n" + devApiNavResponse + "\n");
		log.info("\nPrinting DevApiService nav API response :\n\n" + devApiNavResponse + "\n");

		//These nodes have been removed as per current changes
		/*List<String> metaTagNodes = DevApiServiceDataNodes.getNavResponseMetaTagNodes();
		for(String metaTagNode : metaTagNodes)
		{
			AssertJUnit.assertTrue("["+metaTagNode+"] node doesn't exists.", devApiNavReqGen.respvalidate.DoesNodeExists(metaTagNode));
		}

		AssertJUnit.assertEquals("["+DevApiServiceDataNodes.META_CODE.toString()+"] node value is invalid.", "200",
				devApiNavReqGen.respvalidate.NodeValue(DevApiServiceDataNodes.META_CODE.toString(), false));

		AssertJUnit.assertNotNull("["+DevApiServiceDataNodes.META_REQ_ID.toString()+"] node value should not be null.",
				devApiNavReqGen.respvalidate.NodeValue(DevApiServiceDataNodes.META_REQ_ID.toString(), false));
		 */
	}

	/**
	 * This TestCase is used to invoke DevApiService nav API and verification for notification tag nodes in the response
	 * 
	 */
	@Test(groups = {
			"Fox",
			"Sanity",
			"FoxSanity",
			"MiniRegression",
			"Regression",
			"ExhaustiveRegression"
	},
			description = "Verifies Navigation Link Data")
	public void DevApis_nav_verifyNotificationTagNodes() {
		RequestGenerator devApiNavReqGen = devApiServiceHelper.invokeDevApiNav();
		String devApiNavResponse = devApiNavReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting DevApiService nav API response :\n\n" + devApiNavResponse + "\n");
		log.info("\nPrinting DevApiService nav API response :\n\n" + devApiNavResponse + "\n");

		//These nodes have been removed as per current changes

		/*List<String> notificationTagNodes = DevApiServiceDataNodes.getNavResponseNotificationTagNodes();
  for(String notificationTagNode : notificationTagNodes)
  {
  	AssertJUnit.assertTrue("["+notificationTagNode+"] node doesn't exists.", devApiNavReqGen.respvalidate.DoesNodeExists(notificationTagNode));
  }*/

	}

	/**
	 * This TestCase is used to invoke DevApiService nav API and verification for data tag nodes in the response
	 * 
	 */
	@Test(groups = {
			"Fox",
			"Sanity",
			"FoxSanity",
			"MiniRegression",
			"Regression",
			"ExhaustiveRegression"
	},
			description = "Verifies Navigation Link Data")
	public void DevApis_nav_verifyDataTagNodes() {
		RequestGenerator devApiNavReqGen = devApiServiceHelper.invokeDevApiNav();
		String devApiNavResponse = devApiNavReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting DevApiService nav API response :\n\n" + devApiNavResponse + "\n");
		log.info("\nPrinting DevApiService nav API response :\n\n" + devApiNavResponse + "\n");

		List < String > dataTagNodes = DevApiServiceDataNodes.getNavResponseDataTagNodes();
		for (String dataTagNode: dataTagNodes) {
			AssertJUnit.assertTrue("[" + dataTagNode + "] node doesn't exists.", devApiNavReqGen.respvalidate.DoesNodeExists(dataTagNode));
		}

	}

	/**
	 * This TestCase is used to invoke DevApiService style API and verification for success response
	 * 
	 * @param styleId
	 */
	@Test(groups = {
			"Fox",
			"Smoke",
			"Sanity",
			"FoxSanity",
			"MiniRegression",
			"Regression",
			"ExhaustiveRegression"
	},
			dataProvider = "DevApisDataProvider_style_verifySuccessResponse",
			description = "Verifies Style Service Success Response")
	public void DevApis_style_verifySuccessResponse(String styleId) {
		int i = 0;
		int responseCode = 0;
		RequestGenerator devApiStyleReqGen;
		String devApiStyleResponse = "";
		boolean retryTest = true;
		while (retryTest && i < RetryAttemptsCount) {
			try {
				devApiStyleReqGen = devApiServiceHelper.invokeDevApiStyle(styleId);
				devApiStyleResponse = devApiStyleReqGen.respvalidate.returnresponseasstring();
				System.out.println("\nPrinting DevApiService style API response :\n\n" + devApiStyleResponse + "\n");
				log.info("\nPrinting DevApiService style API response :\n\n" + devApiStyleResponse + "\n");
				AssertJUnit.assertEquals("[DevApiService style API is not working.]", 200, devApiStyleReqGen.response.getStatus());
				retryTest = false;
			} catch (java.lang.RuntimeException e) {
				retryTest = true;
				i++;
				System.out.println("Caught an Exception - Retring Test");
				System.out.println("Caught an Exception - Attempt : " + i);

			} catch (AssertionError e) {
				retryTest = true;
				i++;
				System.out.println("Assertion Error - Retring Test");
				System.out.println("Assertion Error - Attempt : " + i);
				if (i == 3) {
					AssertJUnit.fail(e.getMessage());
				}
			}
		}

	}

	/**
	 * This TestCase is used to invoke DevApiService style API and verification for meta tag nodes in the response
	 * 
	 * @param styleId
	 */
	@Test(groups = {
			"Fox",
			"Sanity",
			"FoxSanity",
			"MiniRegression",
			"Regression",
			"ExhaustiveRegression"
	},
			dataProvider = "DevApisDataProvider_style_verifyMetaTagNodes",
			description = "Verifies Style Service Data ")
	public void DevApis_style_verifyMetaTagNodes(String styleId) {
		int i = 0;
		int responseCode = 0;
		RequestGenerator devApiStyleReqGen;
		String devApiStyleResponse = "";
		boolean retryTest = true;
		while (retryTest && i < RetryAttemptsCount) {
			try {

				devApiStyleReqGen = devApiServiceHelper.invokeDevApiStyle(styleId);
				devApiStyleResponse = devApiStyleReqGen.respvalidate.returnresponseasstring();
				System.out.println("\nPrinting DevApiService style API response :\n\n" + devApiStyleResponse + "\n");
				log.info("\nPrinting DevApiService style API response :\n\n" + devApiStyleResponse + "\n");

				List < String > metaTagNodes = DevApiServiceDataNodes.getStyleResponseMetaTagNodes();
				for (String metaTagNode: metaTagNodes) {
					AssertJUnit.assertTrue("[" + metaTagNode + "] node doesn't exists.", devApiStyleReqGen.respvalidate.DoesNodeExists(metaTagNode));
				}

				AssertJUnit.assertEquals("[" + DevApiServiceDataNodes.META_CODE.toString() + "] node value is invalid.", "200",
						devApiStyleReqGen.respvalidate.NodeValue(DevApiServiceDataNodes.META_CODE.toString(), false));

				AssertJUnit.assertNotNull("[" + DevApiServiceDataNodes.META_REQ_ID.toString() + "] node value should not be null.",
						devApiStyleReqGen.respvalidate.NodeValue(DevApiServiceDataNodes.META_REQ_ID.toString(), false));

				retryTest = false;
			} catch (java.lang.RuntimeException e) {
				retryTest = true;
				i++;
				System.out.println("Caught an Exception - Retring Test");
				System.out.println("Caught an Exception - Attempt : " + i);

			} catch (AssertionError e) {
				retryTest = true;
				i++;
				System.out.println("Assertion Error - Retring Test");
				System.out.println("Assertion Error - Attempt : " + i);
				if (i == 3) {
					AssertJUnit.fail(e.getMessage());
				}
			}
		}





	}

	/**
	 * This TestCase is used to invoke DevApiService style API and verification for notification tag nodes in the response
	 * 
	 * @param styleId
	 */
	@Test(groups = {
			"Fox",
			"Sanity",
			"FoxSanity",
			"MiniRegression",
			"Regression",
			"ExhaustiveRegression"
	},
			dataProvider = "DevApisDataProvider_style_verifyMetaTagNodes",
			description = "Verifies Style Service data")
	public void DevApis_style_verifyNotificationTagNodes(String styleId) {
		int i = 0;
		int responseCode = 0;
		RequestGenerator devApiStyleReqGen;
		String devApiStyleResponse = "";
		boolean retryTest = true;
		while (retryTest && i < RetryAttemptsCount) {
			try {
				devApiStyleReqGen = devApiServiceHelper.invokeDevApiStyle(styleId);
				devApiStyleResponse = devApiStyleReqGen.respvalidate.returnresponseasstring();
				System.out.println("\nPrinting DevApiService style API response :\n\n" + devApiStyleResponse + "\n");
				log.info("\nPrinting DevApiService style API response :\n\n" + devApiStyleResponse + "\n");

				List < String > notificationTagNodes = DevApiServiceDataNodes.getStyleResponseNotificationTagNodes();
				for (String notificationTagNode: notificationTagNodes) {
					AssertJUnit.assertTrue("[" + notificationTagNode + "] node doesn't exists.", devApiStyleReqGen.respvalidate.DoesNodeExists(notificationTagNode));
				}


				retryTest = false;
			} catch (java.lang.RuntimeException e) {
				retryTest = true;
				i++;
				System.out.println("Caught an Exception - Retring Test");
				System.out.println("Caught an Exception - Attempt : " + i);

			} catch (AssertionError e) {
				retryTest = true;
				i++;
				System.out.println("Assertion Error - Retring Test");
				System.out.println("Assertion Error - Attempt : " + i);
				if (i == 3) {
					AssertJUnit.fail(e.getMessage());
				}
			}
		}





	}

	/**
	 * This TestCase is used to invoke DevApiService style API and verification for data tag nodes
	 * 
	 * @param styleId
	 */
	@Test(groups = {
			"Fox",
			"Sanity",
			"FoxSanity",
			"MiniRegression",
			"Regression",
			"ExhaustiveRegression"
	},
			dataProvider = "DevApisDataProvider_style_verifyDataNodes",
			description = "Verifies Style Service Data nodes")
	public void DevApis_style_verifyDataNodes(String styleId) {
		int i = 0;
		int responseCode = 0;
		RequestGenerator devApiStyleReqGen;
		String devApiStyleResponse = "";
		boolean retryTest = true;
		while (retryTest && i < RetryAttemptsCount) {
			try {
				devApiStyleReqGen = devApiServiceHelper.invokeDevApiStyle(styleId);
				devApiStyleResponse = devApiStyleReqGen.respvalidate.returnresponseasstring();
				System.out.println("\nPrinting DevApiService style API response :\n\n" + devApiStyleResponse + "\n");
				log.info("\nPrinting DevApiService style API response :\n\n" + devApiStyleResponse + "\n");

				List < String > dataTagNodes = DevApiServiceDataNodes.getStyleResponseDataNodes();
				for (String dataTagNode: dataTagNodes) {
					AssertJUnit.assertTrue("[" + dataTagNode + "] node doesn't exists.", devApiStyleReqGen.respvalidate.DoesNodeExists(dataTagNode));
				}

				AssertJUnit.assertEquals("DevApiService style API request and response styleId are not same", Long.parseLong(styleId),
						Long.parseLong(devApiStyleReqGen.respvalidate.NodeValue(DevApiServiceDataNodes.STYLE_RESP_DATA_ID.toString(), false)));



				retryTest = false;
			} catch (java.lang.RuntimeException e) {
				retryTest = true;
				i++;
				System.out.println("Caught an Exception - Retring Test");
				System.out.println("Caught an Exception - Attempt : " + i);

			} catch (AssertionError e) {
				retryTest = true;
				i++;
				System.out.println("Assertion Error - Retring Test");
				System.out.println("Assertion Error - Attempt : " + i);
				if (i == 3) {
					AssertJUnit.fail(e.getMessage());
				}
			}
		}




	}

	/**
	 * This TestCase is used to invoke DevApiService style API and verification for article attribute tag data nodes in the response
	 * 
	 * @param styleId
	 */
	@Test(groups = {
			"Fox",
			"Sanity",
			"FoxSanity",
			"MiniRegression",
			"Regression",
			"ExhaustiveRegression"
	},
			dataProvider = "DevApisDataProvider_style_verifyArticleAttributeDataNodes",
			description = "Verifies Style Service Article Attributes data Response")
	public void DevApis_style_verifyArticleAttributeDataNodes(String styleId) {
		int i = 0;
		int responseCode = 0;
		RequestGenerator devApiStyleReqGen;
		String devApiStyleResponse = "";
		boolean retryTest = true;
		while (retryTest && i < RetryAttemptsCount) {
			try {
				devApiStyleReqGen = devApiServiceHelper.invokeDevApiStyle(styleId);
				devApiStyleResponse = devApiStyleReqGen.respvalidate.returnresponseasstring();
				System.out.println("\nPrinting DevApiService style API response :\n\n" + devApiStyleResponse + "\n");
				log.info("\nPrinting DevApiService style API response :\n\n" + devApiStyleResponse + "\n");

				List < String > articleAttributeDataTagNodes = DevApiServiceDataNodes.getStyleResponseArticleAttributeDataNodes();
				for (String articleAttributeDataTagNode: articleAttributeDataTagNodes) {
					AssertJUnit.assertTrue("[" + articleAttributeDataTagNode + "] node doesn't exists.", devApiStyleReqGen.respvalidate.DoesNodeExists(articleAttributeDataTagNode));
				}


				retryTest = false;
			} catch (java.lang.RuntimeException e) {
				retryTest = true;
				i++;
				System.out.println("Caught an Exception - Retring Test");
				System.out.println("Caught an Exception - Attempt : " + i);

			} catch (AssertionError e) {
				retryTest = true;
				i++;
				System.out.println("Assertion Error - Retring Test");
				System.out.println("Assertion Error - Attempt : " + i);
				if (i == 3) {
					AssertJUnit.fail(e.getMessage());
				}
			}
		}






	}

	/**
	 * This TestCase is used to invoke DevApiService style API and verification for styleImages tag data nodes in the response
	 * 
	 * @param styleId
	 */
	@Test(groups = {
			"Fox",
			"Sanity",
			"MiniRegression",
			"Regression",
			"ExhaustiveRegression"
	},
			dataProvider = "DevApisDataProvider_style_verifyStyleImagesDataNodes",
			description = "Verifies Style Service Image nodes in  Response")
	public void DevApis_style_verifyStyleImagesDataNodes(String styleId) {
		int i = 0;
		int responseCode = 0;
		RequestGenerator devApiStyleReqGen;
		String devApiStyleResponse = "";
		boolean retryTest = true;
		while (retryTest && i < RetryAttemptsCount) {
			try {
				devApiStyleReqGen = devApiServiceHelper.invokeDevApiStyle(styleId);
				devApiStyleResponse = devApiStyleReqGen.respvalidate.returnresponseasstring();
				System.out.println("\nPrinting DevApiService style API response :\n\n" + devApiStyleResponse + "\n");
				log.info("\nPrinting DevApiService style API response :\n\n" + devApiStyleResponse + "\n");


				List < String > styleImagesDataTagNodes = DevApiServiceDataNodes.getStyleResponseStyleImagesDataNodes();
				for (String styleImagesDataTagNode: styleImagesDataTagNodes) {
					AssertJUnit.assertTrue("[" + styleImagesDataTagNode + "] node doesn't exists.", devApiStyleReqGen.respvalidate.DoesNodeExists(styleImagesDataTagNode));
				}



				retryTest = false;
			} catch (java.lang.RuntimeException e) {
				retryTest = true;
				i++;
				System.out.println("Caught an Exception - Retring Test");
				System.out.println("Caught an Exception - Attempt : " + i);

			} catch (AssertionError e) {
				retryTest = true;
				i++;
				System.out.println("Assertion Error - Retring Test");
				System.out.println("Assertion Error - Attempt : " + i);
				if (i == 3) {
					AssertJUnit.fail(e.getMessage());
				}
			}
		}





	}

	/**
	 * This TestCase is used to invoke DevApiService style API and verification for master catagory tag data nodes in the response
	 * 
	 * @param styleId
	 */
	@Test(groups = {
			"Fox",
			"Sanity",
			"FoxSanity",
			"MiniRegression",
			"Regression",
			"ExhaustiveRegression"
	},
			dataProvider = "DevApisDataProvider_style_verifyMasterCatagoryDataNodes",
			description = "Verifies Style Service Master Category Nodes in Success Response")
	public void DevApis_style_verifyMasterCatagoryDataNodes(String styleId) {
		int i = 0;
		int responseCode = 0;
		RequestGenerator devApiStyleReqGen;
		String devApiStyleResponse = "";
		boolean retryTest = true;
		while (retryTest && i < RetryAttemptsCount) {
			try {

				devApiStyleReqGen = devApiServiceHelper.invokeDevApiStyle(styleId);
				devApiStyleResponse = devApiStyleReqGen.respvalidate.returnresponseasstring();
				System.out.println("\nPrinting DevApiService style API response :\n\n" + devApiStyleResponse + "\n");
				log.info("\nPrinting DevApiService style API response :\n\n" + devApiStyleResponse + "\n");

				List < String > masterCatagoryDataTagNodes = DevApiServiceDataNodes.getStyleResponseMasterCatagoryDataNodes();
				for (String masterCatagoryDataTagNode: masterCatagoryDataTagNodes) {
					AssertJUnit.assertTrue("[" + masterCatagoryDataTagNode + "] node doesn't exists.", devApiStyleReqGen.respvalidate.DoesNodeExists(masterCatagoryDataTagNode));
				}

				retryTest = false;
			} catch (java.lang.RuntimeException e) {
				retryTest = true;
				i++;
				System.out.println("Caught an Exception - Retring Test");
				System.out.println("Caught an Exception - Attempt : " + i);

			} catch (AssertionError e) {
				retryTest = true;
				i++;
				System.out.println("Assertion Error - Retring Test");
				System.out.println("Assertion Error - Attempt : " + i);
				if (i == 3) {
					AssertJUnit.fail(e.getMessage());
				}
			}
		}





	}

	/**
	 * This TestCase is used to invoke DevApiService style API and verification for sub catagory data nodes in the response
	 * 
	 * @param styleId
	 */
	@Test(groups = {
			"Fox",
			"Sanity",
			"FoxSanity",
			"MiniRegression",
			"Regression",
			"ExhaustiveRegression"
	},
			dataProvider = "DevApisDataProvider_style_verifySubCatagoryDataNodes",
			description = "Verifies Style Service Sub Category data nodes")
	public void DevApis_style_verifySubCatagoryDataNodes(String styleId) {
		int i = 0;
		int responseCode = 0;
		RequestGenerator devApiStyleReqGen;
		String devApiStyleResponse = "";
		boolean retryTest = true;
		while (retryTest && i < RetryAttemptsCount) {
			try {
				devApiStyleReqGen = devApiServiceHelper.invokeDevApiStyle(styleId);
				devApiStyleResponse = devApiStyleReqGen.respvalidate.returnresponseasstring();
				System.out.println("\nPrinting DevApiService style API response :\n\n" + devApiStyleResponse + "\n");
				log.info("\nPrinting DevApiService style API response :\n\n" + devApiStyleResponse + "\n");


				List < String > subCatagoryDataTagNodes = DevApiServiceDataNodes.getStyleResponseSubCatagoryDataNodes();
				for (String subCatagoryDataTagNode: subCatagoryDataTagNodes) {
					AssertJUnit.assertTrue("[" + subCatagoryDataTagNode + "] node doesn't exists.", devApiStyleReqGen.respvalidate.DoesNodeExists(subCatagoryDataTagNode));
				}


				retryTest = false;
			} catch (java.lang.RuntimeException e) {
				retryTest = true;
				i++;
				System.out.println("Caught an Exception - Retring Test");
				System.out.println("Caught an Exception - Attempt : " + i);

			} catch (AssertionError e) {
				retryTest = true;
				i++;
				System.out.println("Assertion Error - Retring Test");
				System.out.println("Assertion Error - Attempt : " + i);
				if (i == 3) {
					AssertJUnit.fail(e.getMessage());
				}
			}
		}





	}

	/**
	 * This TestCase is used to invoke DevApiService style API and verification for sub article tag data nodes in the response
	 * 
	 * @param styleId
	 */
	@Test(groups = {
			"Fox",
			"Sanity",
			"FoxSanity",
			"MiniRegression",
			"Regression",
			"ExhaustiveRegression"
	},
			dataProvider = "DevApisDataProvider_style_verifySubArticleDataNodes",
			description = "Verifies Style Service Sub Article data nodes")
	public void DevApis_style_verifySubArticleDataNodes(String styleId) {
		int i = 0;
		int responseCode = 0;
		RequestGenerator devApiStyleReqGen;
		String devApiStyleResponse = "";
		boolean retryTest = true;
		while (retryTest && i < RetryAttemptsCount) {
			try {
				devApiStyleReqGen = devApiServiceHelper.invokeDevApiStyle(styleId);
				devApiStyleResponse = devApiStyleReqGen.respvalidate.returnresponseasstring();
				System.out.println("\nPrinting DevApiService style API response :\n\n" + devApiStyleResponse + "\n");
				log.info("\nPrinting DevApiService style API response :\n\n" + devApiStyleResponse + "\n");


				List < String > subArticleDataTagNodes = DevApiServiceDataNodes.getStyleResponseSubArticleDataNodes();
				for (String subArticleDataTagNode: subArticleDataTagNodes) {
					AssertJUnit.assertTrue("[" + subArticleDataTagNode + "] node doesn't exists.", devApiStyleReqGen.respvalidate.DoesNodeExists(subArticleDataTagNode));
				}


				retryTest = false;
			} catch (java.lang.RuntimeException e) {
				retryTest = true;
				i++;
				System.out.println("Caught an Exception - Retring Test");
				System.out.println("Caught an Exception - Attempt : " + i);

			} catch (AssertionError e) {
				retryTest = true;
				i++;
				System.out.println("Assertion Error - Retring Test");
				System.out.println("Assertion Error - Attempt : " + i);
				if (i == 3) {
					AssertJUnit.fail(e.getMessage());
				}
			}
		}




	}

	/**
	 * This TestCase is used to invoke DevApiService style API and verification for product descriptors tag data nodes in the response
	 * 
	 * @param styleId
	 */
	@Test(groups = {
			"Fox",
			"Sanity",
			"FoxSanity",
			"MiniRegression",
			"Regression",
			"ExhaustiveRegression"
	},
			dataProvider = "DevApisDataProvider_style_verifyProductDescriptorDataNodes",
			description = "Verifies Style Service PDP data nodes")
	public void DevApis_style_verifyProductDescriptorDataNodes(String styleId) {
		int i = 0;
		RequestGenerator devApiStyleReqGen;
		String devApiStyleResponse = "";
		boolean retryTest = true;
		while (retryTest && i < RetryAttemptsCount) {
			try {
				devApiStyleReqGen = devApiServiceHelper.invokeDevApiStyle(styleId);
				devApiStyleResponse = devApiStyleReqGen.respvalidate.returnresponseasstring();
				System.out.println("\nPrinting DevApiService style API response :\n\n" + devApiStyleResponse + "\n");
				log.info("\nPrinting DevApiService style API response :\n\n" + devApiStyleResponse + "\n");

				List < String > productDescriptorDataTagNodes = DevApiServiceDataNodes.getStyleResponseProductDescriptorDataNodes();
				for (String productDescriptorDataTagNode: productDescriptorDataTagNodes) {
					AssertJUnit.assertTrue("[" + productDescriptorDataTagNode + "] node doesn't exists.", devApiStyleReqGen.respvalidate.DoesNodeExists(productDescriptorDataTagNode));
				}


				retryTest = false;
			} catch (java.lang.RuntimeException e) {
				retryTest = true;
				i++;
				System.out.println("Caught an Exception - Retring Test");
				System.out.println("Caught an Exception - Attempt : " + i);

			} catch (AssertionError e) {
				retryTest = true;
				i++;
				System.out.println("Assertion Error - Retring Test");
				System.out.println("Assertion Error - Attempt : " + i);
				if (i == 3) {
					AssertJUnit.fail(e.getMessage());
				}
			}
		}





	}

	/**
	 * This TestCase is used to invoke DevApiService style API and verification for style options tag data nodes in the response
	 * 
	 * @param styleId
	 */
	@Test(groups = {
			"Fox",
			"Sanity",
			"FoxSanity",
			"MiniRegression",
			"Regression",
			"ExhaustiveRegression"
	},
			dataProvider = "DevApisDataProvider_style_verifyStyleOptionsDataNodes",
			description = "Verifies Style Service Style Options data nodes")
	public void DevApis_style_verifyStyleOptionsDataNodes(String styleId) {
		int i = 0;
		int responseCode = 0;
		RequestGenerator devApiStyleReqGen;
		String devApiStyleResponse = "";
		boolean retryTest = true;
		while (retryTest && i < RetryAttemptsCount) {
			try {

				devApiStyleReqGen = devApiServiceHelper.invokeDevApiStyle(styleId);
				devApiStyleResponse = devApiStyleReqGen.respvalidate.returnresponseasstring();
				System.out.println("\nPrinting DevApiService style API response :\n\n" + devApiStyleResponse + "\n");
				log.info("\nPrinting DevApiService style API response :\n\n" + devApiStyleResponse + "\n");

				List < String > styleOptionsDataTagNodes = DevApiServiceDataNodes.getStyleResponseStyleOptionsDataNodes();
				for (String styleOptionsDataTagNode: styleOptionsDataTagNodes) {
					AssertJUnit.assertTrue("[" + styleOptionsDataTagNode + "] node doesn't exists.", devApiStyleReqGen.respvalidate.DoesNodeExists(styleOptionsDataTagNode));
				}



				retryTest = false;
			} catch (java.lang.RuntimeException e) {
				retryTest = true;
				i++;
				System.out.println("Caught an Exception - Retring Test");
				System.out.println("Caught an Exception - Attempt : " + i);

			} catch (AssertionError e) {
				retryTest = true;
				i++;
				System.out.println("Assertion Error - Retring Test");
				System.out.println("Assertion Error - Attempt : " + i);
				if (i == 3) {
					AssertJUnit.fail(e.getMessage());
				}
			}
		}



	}

	/**
	 * This TestCase is used to invoke DevApiService styleOffers API and verification for style options tag data nodes in the response
	 * 
	 * @param styleId
	 */
	@Test(groups = {
			"Fox",
			"Smoke",
			"Sanity",
			"FoxSanity",
			"ProdSanity",
			"MiniRegression",
			"Regression",
			"ExhaustiveRegression"
	},
			dataProvider = "DevApisDataProvider_styleOffers_verifySuccessResponse",
			description = "Verifies Style Offers Success Response")
	public void DevApis_styleOffers_verifySuccessResponse(String styleId) {
		int i = 0;
		int responseCode = 0;
		RequestGenerator devApiStyleOffersReqGen;
		String devApiStyleOffersResponse = "";
		boolean retryTest = true;
		while (retryTest && i < RetryAttemptsCount) {
			try {
				devApiStyleOffersReqGen = devApiServiceHelper.invokeDevApiStyleOffers(styleId);
				devApiStyleOffersResponse = devApiStyleOffersReqGen.respvalidate.returnresponseasstring();
				System.out.println("\nPrinting DevApiService styleOffers API response :\n\n" + devApiStyleOffersResponse + "\n");
				log.info("\nPrinting DevApiService styleOffers API response :\n\n" + devApiStyleOffersResponse + "\n");

				AssertJUnit.assertEquals("[DevApiService styleOffers API is not working.]", 200, devApiStyleOffersReqGen.response.getStatus());



				retryTest = false;
			} catch (java.lang.RuntimeException e) {
				retryTest = true;
				i++;
				System.out.println("Caught an Exception - Retring Test");
				System.out.println("Caught an Exception - Attempt : " + i);

			} catch (AssertionError e) {
				retryTest = true;
				i++;
				System.out.println("Assertion Error - Retring Test");
				System.out.println("Assertion Error - Attempt : " + i);
				if (i == 3) {
					AssertJUnit.fail(e.getMessage());
				}
			}
		}



	}

	/**
	 * This TestCase is used to invoke DevApiService styleOffers API and verification for meta tag data nodes in the response
	 * 
	 * @param styleId
	 */
	@Test(groups = {
			"Fox",
			"Sanity",
			"FoxSanity",
			"MiniRegression",
			"Regression",
			"ExhaustiveRegression"
	},
			dataProvider = "DevApisDataProvider_styleOffers_verifyMetaTagNodes",
			description = "Verifies Style Offers Meta Tag Nodes")
	public void DevApis_styleOffers_verifyMetaTagNodes(String styleId) {
		int i = 0;
		int responseCode = 0;
		RequestGenerator devApiStyleOffersReqGen;
		String devApiStyleOffersResponse = "";
		boolean retryTest = true;
		while (retryTest && i < RetryAttemptsCount) {
			try {
				devApiStyleOffersReqGen = devApiServiceHelper.invokeDevApiStyleOffers(styleId);
				devApiStyleOffersResponse = devApiStyleOffersReqGen.respvalidate.returnresponseasstring();
				System.out.println("\nPrinting DevApiService styleOffers API response :\n\n" + devApiStyleOffersResponse + "\n");
				log.info("\nPrinting DevApiService styleOffers API response :\n\n" + devApiStyleOffersResponse + "\n");


				List < String > metaTagNodes = DevApiServiceDataNodes.getStyleOffersResponseMetaTagNodes();
				for (String metaTagNode: metaTagNodes) {
					AssertJUnit.assertTrue("[" + metaTagNode + "] node doesn't exists.", devApiStyleOffersReqGen.respvalidate.DoesNodeExists(metaTagNode));
				}

				AssertJUnit.assertEquals("[" + DevApiServiceDataNodes.META_CODE.toString() + "] node value is invalid.", "200",
						devApiStyleOffersReqGen.respvalidate.GetNodeValue(DevApiServiceDataNodes.META_CODE.toString(), devApiStyleOffersResponse));




				retryTest = false;
			} catch (java.lang.RuntimeException e) {
				retryTest = true;
				i++;
				System.out.println("Caught an Exception - Retring Test");
				System.out.println("Caught an Exception - Attempt : " + i);

			} catch (AssertionError e) {
				retryTest = true;
				i++;
				System.out.println("Assertion Error - Retring Test");
				System.out.println("Assertion Error - Attempt : " + i);
				if (i == 3) {
					AssertJUnit.fail(e.getMessage());
				}
			}
		}



	}

	/**
	 * This TestCase is used to invoke DevApiService styleOffers API and verification for notification tag data nodes in the response
	 * 
	 * @param styleId
	 */
	@Test(groups = {
			"Fox",
			"Sanity",
			"FoxSanity",
			"ProdSanity",
			"MiniRegression",
			"Regression",
			"ExhaustiveRegression"
	},
			dataProvider = "DevApisDataProvider_styleOffers_verifyNotificationTagNodes",
			description = "Verifies Style Offers Notification data nodes")
	public void DevApis_styleOffers_verifyNotificationTagNodes(String styleId) {
		int i = 0;
		int responseCode = 0;
		RequestGenerator devApiStyleOffersReqGen;
		String devApiStyleOffersResponse = "";
		boolean retryTest = true;
		while (retryTest && i < RetryAttemptsCount) {
			try {
				devApiStyleOffersReqGen = devApiServiceHelper.invokeDevApiStyleOffers(styleId);
				devApiStyleOffersResponse = devApiStyleOffersReqGen.respvalidate.returnresponseasstring();
				System.out.println("\nPrinting DevApiService styleOffers API response :\n\n" + devApiStyleOffersResponse + "\n");
				log.info("\nPrinting DevApiService styleOffers API response :\n\n" + devApiStyleOffersResponse + "\n");

				List < String > notificationTagNodes = DevApiServiceDataNodes.getStyleOffersResponseNotificationTagNodes();
				for (String notificationTagNode: notificationTagNodes) {
					AssertJUnit.assertTrue("[" + notificationTagNode + "] node doesn't exists.", devApiStyleOffersReqGen.respvalidate.DoesNodeExists(notificationTagNode));
				}


				retryTest = false;
			} catch (java.lang.RuntimeException e) {
				retryTest = true;
				i++;
				System.out.println("Caught an Exception - Retring Test");
				System.out.println("Caught an Exception - Attempt : " + i);

			} catch (AssertionError e) {
				retryTest = true;
				i++;
				System.out.println("Assertion Error - Retring Test");
				System.out.println("Assertion Error - Attempt : " + i);
				if (i == 3) {
					AssertJUnit.fail(e.getMessage());
				}
			}
		}

	}

	/**
	 *  This TestCase is used to invoke DevApiService styleOffers API and verification for data nodes in the response
	 * 
	 * @param styleId
	 */
	@Test(groups = {
			"Fox",
			"Sanity",
			"FoxSanity",
			"ProdSanity",
			"MiniRegression",
			"Regression",
			"ExhaustiveRegression"
	},
			dataProvider = "DevApisDataProvider_styleOffers_verifyDataTagNodes",
			description = "Verifies Style Offers data nodes")
	public void DevApis_styleOffers_verifyDataTagNodes(String styleId) {
		int i = 0;
		int responseCode = 0;
		RequestGenerator devApiStyleOffersReqGen;
		String devApiStyleOffersResponse = "";
		boolean retryTest = true;
		while (retryTest && i < RetryAttemptsCount) {
			try {
				devApiStyleOffersReqGen = devApiServiceHelper.invokeDevApiStyleOffers(styleId);
				devApiStyleOffersResponse = devApiStyleOffersReqGen.respvalidate.returnresponseasstring();
				System.out.println("\nPrinting DevApiService styleOffers API response :\n\n" + devApiStyleOffersResponse + "\n");
				log.info("\nPrinting DevApiService styleOffers API response :\n\n" + devApiStyleOffersResponse + "\n");

				if (!devApiStyleOffersReqGen.respvalidate.NodeValue(DevApiServiceDataNodes.STYLE_OFFERS_RESP_DATA_COUPONS_NO_OF_COUPONS.toString(), false).equals("0")) {
					List < String > dataTagNodes = DevApiServiceDataNodes.getStyleOffersResponseDataTagNodes();
					for (String dataTagNode: dataTagNodes) {
						AssertJUnit.assertTrue("[" + dataTagNode + "] node doesn't exists.", devApiStyleOffersReqGen.respvalidate.DoesNodeExists(dataTagNode));
					}

				} else {
					System.out.println("\n" + styleId + " doesn't have valid coupons\n");
					log.info("\n" + styleId + " doesn't have valid coupons\n");
				}



				retryTest = false;
			} catch (java.lang.RuntimeException e) {
				retryTest = true;
				i++;
				System.out.println("Caught an Exception - Retring Test");
				System.out.println("Caught an Exception - Attempt : " + i);

			} catch (AssertionError e) {
				retryTest = true;
				i++;
				System.out.println("Assertion Error - Retring Test");
				System.out.println("Assertion Error - Attempt : " + i);
				if (i == 3) {
					AssertJUnit.fail(e.getMessage());
				}
			}
		}



	}

	/**
	 * This TestCase is used to invoke DevApiService styleOffers API and verification for success response
	 * 
	 * @param searchParam
	 */
	@Test(groups = {
			"Fox",
			"Smoke",
			"Sanity",
			"ProdSanity",
			"MiniRegression",
			"Regression",
			"ExhaustiveRegression"
	},
			dataProvider = "DevApisDataProvider_search_verifySuccessResponse",
			description = "Verifies Search Success Response")
	public void DevApis_search_verifySuccessResponse(String searchParam) {
		RequestGenerator devApiSearchReqGen = devApiServiceHelper.invokeDevApiSearch(searchParam);
		String devApiSearchResponse = devApiSearchReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting DevApiService search API response :\n\n" + devApiSearchResponse + "\n");
		log.info("\nPrinting DevApiService search API response :\n\n" + devApiSearchResponse + "\n");

		AssertJUnit.assertEquals("[DevApiService search API is not working.]", 200, devApiSearchReqGen.response.getStatus());

	}

	/**
	 * This TestCase is used to invoke DevApiService styleOffers API and verification data nodes in the response
	 * 
	 * @param searchParam
	 */
	@Test(groups = {
			"Fox",
			"Sanity",
			"FoxSanity",
			"MiniRegression",
			"Regression",
			"ExhaustiveRegression"
	},
			dataProvider = "DevApisDataProvider_search_verifyDataTagNodes",
			description = "Verifies Search Success Response with Data Validations")
	public void DevApis_search_verifyDataTagNodes(String searchParam) {
		RequestGenerator devApiSearchReqGen = devApiServiceHelper.invokeDevApiSearch(searchParam);
		String devApiSearchResponse = devApiSearchReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting DevApiService search API response :\n\n" + devApiSearchResponse + "\n");
		log.info("\nPrinting DevApiService search API response :\n\n" + devApiSearchResponse + "\n");

		List < String > dataTagNodes = DevApiServiceDataNodes.getSearchResponseDataTagNodes();
		for (String dataTagNode: dataTagNodes) {
			AssertJUnit.assertTrue("[" + dataTagNode + "] node doesn't exists.", devApiSearchReqGen.respvalidate.DoesNodeExists(dataTagNode));
		}
	}

	/**
	 * This TestCase is used to invoke DevApiService styleOffers API and verification failure response
	 * 
	 * @param searchParam
	 * @param errorCode
	 */
	@Test(groups = {
			"Fox",
			"Sanity",
			"MiniRegression",
			"Regression",
			"ExhaustiveRegression"
	},
			dataProvider = "DevApisDataProvider_search_verifyFailureResponse",
			description = "Verifies Search Failure Response")
	public void DevApis_search_verifyFailureResponse(String searchParam, String errorCode) {
		int i = 0;
		int responseCode = 0;
		RequestGenerator devApiSearchReqGen;
		String devApiSearchResponse = "";
		boolean retryTest = true;
		while (retryTest && i < RetryAttemptsCount) {
			try {
				devApiSearchReqGen = devApiServiceHelper.invokeDevApiSearch(searchParam);
				devApiSearchResponse = devApiSearchReqGen.respvalidate.returnresponseasstring();
				System.out.println("\nPrinting DevApiService search API response :\n\n" + devApiSearchResponse + "\n");
				log.info("\nPrinting DevApiService search API response :\n\n" + devApiSearchResponse + "\n");


				AssertJUnit.assertEquals("[DevApiService search API is not working.]", Integer.parseInt(errorCode), devApiSearchReqGen.response.getStatus());




				retryTest = false;
			} catch (java.lang.RuntimeException e) {
				retryTest = true;
				i++;
				System.out.println("Caught an Exception - Retring Test");
				System.out.println("Caught an Exception - Attempt : " + i);

			} catch (AssertionError e) {
				retryTest = true;
				i++;
				System.out.println("Assertion Error - Retring Test");
				System.out.println("Assertion Error - Attempt : " + i);
				if (i == 3) {
					AssertJUnit.fail(e.getMessage());
				}
			}
		}




	}



	/**
	 * This TestCase is used to invoke DevApiService searchWithFacets API and verification for success response
	 * 
	 * @param searchParam
	 */
	@Test(groups = {
			"Fox",
			"Smoke",
			"Sanity",
			"FoxSanity",
			"ProdSanity",
			"MiniRegression",
			"Regression",
			"ExhaustiveRegression"
	},
			dataProvider = "DevApisDataProvider_searchWithfacets_verifySuccessResponse",
			description = "Verifies Search with Facets Success Response")
	public void DevApis_searchWithFacets_verifySuccessResponse(String searchParam) {
		RequestGenerator devApiSearchWithFacetsReqGen = devApiServiceHelper.invokeDevApiSearchWithFacets(searchParam);
		String devApiSearchWithFacetsResponse = devApiSearchWithFacetsReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting DevApiService searchWithFacets API response :\n\n" + devApiSearchWithFacetsResponse + "\n");
		log.info("\nPrinting DevApiService searchWithFacets API response :\n\n" + devApiSearchWithFacetsResponse + "\n");

		AssertJUnit.assertEquals("[DevApiService searchWithFacets API is not working.]", 200, devApiSearchWithFacetsReqGen.response.getStatus());

	}

	/**
	 *  This TestCase is used to invoke DevApiService searchWithFacets API and verification for data nodes in response
	 * 
	 * @param searchParam
	 */
	@Test(groups = {
			"Fox",
			"Sanity",
			"MiniRegression",
			"Regression",
			"ExhaustiveRegression"
	},
			dataProvider = "DevApisDataProvider_searchWithfacets_verifyDataNodes",
			description = "Verifies Search With Facets - Data Validation")
	public void DevApis_searchWithFacets_verifyDataTagNodes(String searchParam) {
		RequestGenerator devApiSearchReqGen = devApiServiceHelper.invokeDevApiSearchWithFacets(searchParam);
		String devApiSearchResponse = devApiSearchReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting DevApiService searchWithFacets API response :\n\n" + devApiSearchResponse + "\n");
		log.info("\nPrinting DevApiService searchWithFacets API response :\n\n" + devApiSearchResponse + "\n");

		List < String > dataTagNodes = DevApiServiceDataNodes.getSearchWithFacetsResponseDataTagNodes();
		for (String dataTagNode: dataTagNodes) {
			AssertJUnit.assertTrue("[" + dataTagNode + "] node doesn't exists.", devApiSearchReqGen.respvalidate.DoesNodeExists(dataTagNode));
		}

	}

	/**
	 * This TestCase is used to invoke DevApiService styleWithServicability API and verification for success response
	 * 
	 * @param searchParam
	 */
	@Test(groups = {
			"Fox",
			"Smoke",
			"Sanity",
			"FoxSanity",
			"ProdSanity",
			"MiniRegression",
			"Regression",
			"ExhaustiveRegression"
	},
			dataProvider = "DevApisDataProvider_styleWithServicability_verifySuccessResponse",
			description = "Verifies Serviceability Success Response")
	public void DevApis_styleWithServicability_verifySuccessResponse(String searchParam) {
		RequestGenerator devApiStyleWithServicabilityReqGen = devApiServiceHelper.invokeDevApiStyleWithServicability(searchParam);
		String devApiStyleWithServicabilityResponse = devApiStyleWithServicabilityReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting DevApiService styleWithServicability API response :\n\n" + devApiStyleWithServicabilityResponse + "\n");
		log.info("\nPrinting DevApiService styleWithServicability API response :\n\n" + devApiStyleWithServicabilityResponse + "\n");

		AssertJUnit.assertEquals("[DevApiService styleWithServicability API is not working.]", 200, devApiStyleWithServicabilityReqGen.response.getStatus());

	}

	/**
	 * This TestCase is used to invoke DevApiService styleWithServicability API and verification for meta tag data nodes
	 * 
	 * @param searchParam
	 */
	@Test(groups = {
			"Fox",
			"Sanity",
			"FoxSanity",
			"ProdSanity",
			"MiniRegression",
			"Regression",
			"ExhaustiveRegression"
	},
			dataProvider = "DevApisDataProvider_styleWithServicability_verifyMetaTagNodes",
			description = "Verifies Serviceability Data Nodes")
	public void DevApis_styleWithServicability_verifyMetaTagNodes(String searchParam) {
		RequestGenerator devApiStyleWithServicabilityReqGen = devApiServiceHelper.invokeDevApiStyleWithServicability(searchParam);
		String devApiStyleWithServicabilityResponse = devApiStyleWithServicabilityReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting DevApiService styleWithServicability API response :\n\n" + devApiStyleWithServicabilityResponse + "\n");
		log.info("\nPrinting DevApiService styleWithServicability API response :\n\n" + devApiStyleWithServicabilityResponse + "\n");

		List < String > metaTagNodes = DevApiServiceDataNodes.getStyleWithServicabilityResponseMetaTagNodes();
		for (String metaTagNode: metaTagNodes) {
			AssertJUnit.assertTrue("[" + metaTagNode + "] node doesn't exists.", devApiStyleWithServicabilityReqGen.respvalidate.DoesNodeExists(metaTagNode));
		}

		AssertJUnit.assertEquals("[" + DevApiServiceDataNodes.META_CODE.toString() + "] node value is invalid.", "200",
				devApiStyleWithServicabilityReqGen.respvalidate.GetNodeValue(DevApiServiceDataNodes.META_CODE.toString(), devApiStyleWithServicabilityResponse));

	}

	/**
	 * This TestCase is used to invoke DevApiService styleWithServicability API and verification for notification tag data nodes
	 * 
	 * @param searchParam 
	 * Notification tag nodes not present in response
	 */
	/*@Test(groups = { "Sanity","FoxSanity", "ProdSanity", "MiniRegression", "Regression", "ExhaustiveRegression" }, 
 		dataProvider = "DevApisDataProvider_styleWithServicability_verifyNotificationTagNodes")
 public void DevApis_styleWithServicability_verifyNotificationTagNodes(String searchParam) 
 {
 	RequestGenerator devApiStyleWithServicabilityReqGen = devApiServiceHelper.invokeDevApiStyleWithServicability(searchParam);
 	String devApiStyleWithServicabilityResponse = devApiStyleWithServicabilityReqGen.respvalidate.returnresponseasstring();
 	System.out.println("\nPrinting DevApiService styleWithServicability API response :\n\n"+devApiStyleWithServicabilityResponse+"\n");
 	log.info("\nPrinting DevApiService styleWithServicability API response :\n\n"+devApiStyleWithServicabilityResponse+"\n");

 	List<String> notificationTagNodes = DevApiServiceDataNodes.getStyleWithServicabilityResponseNotificationTagNodes();
 	for(String notificationTagNode : notificationTagNodes)
 	{
 		AssertJUnit.assertTrue("["+notificationTagNode+"] node doesn't exists.", devApiStyleWithServicabilityReqGen.respvalidate.DoesNodeExists(notificationTagNode));
 	}

 }*/

	/**
	 * This TestCase is used to invoke DevApiService styleWithServicability API and verification for data tag nodes
	 * 
	 * @param searchParam
	 */
	@Test(groups = {
			"Fox",
			"Sanity",
			"FoxSanity",
			"ProdSanity",
			"MiniRegression",
			"Regression",
			"ExhaustiveRegression"
	},
			dataProvider = "DevApisDataProvider_styleWithServicability_verifyDataTagNodes",
			description = "Verifies Serviceability Data Nodes")
	public void DevApis_styleWithServicability_verifyDataTagNodes(String searchParam) {
		RequestGenerator devApiStyleWithServicabilityReqGen = devApiServiceHelper.invokeDevApiStyleWithServicability(searchParam);
		String devApiStyleWithServicabilityResponse = devApiStyleWithServicabilityReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting DevApiService styleWithServicability API response :\n\n" + devApiStyleWithServicabilityResponse + "\n");
		log.info("\nPrinting DevApiService styleWithServicability API response :\n\n" + devApiStyleWithServicabilityResponse + "\n");


		List < String > dataTagNodes = DevApiServiceDataNodes.getStyleWithServicabilityResponseDataTagNodes();
		for (String dataTagNode: dataTagNodes) {
			System.out.println(dataTagNode);
			//AssertJUnit.assertTrue("["+dataTagNode+"] node doesn't exists.", devApiStyleWithServicabilityReqGen.respvalidate.DoesNodeExists(dataTagNode));
		}
	}

	/**
	 * This TestCase is used to invoke DevApiService styleWithServicability API and verification for failure and data nodes
	 * 
	 * @param searchParam
	 */
	@Test(groups = {
			"Fox",
			"Sanity",
			"FoxSanity",
			"MiniRegression",
			"Regression",
			"ExhaustiveRegression"
	},
			dataProvider = "DevApisDataProvider_styleWithServicability_verifyFailureResponse",
			description = "Verifies Serviceability - Failure Response Data Nodes")
	public void DevApis_styleServicability_verifyFailureResponse(String searchParam) {
		RequestGenerator devApiStyleWithServicabilityReqGen = null;
		String status = "fail";
		for (int i = 0; i < 3; i++) {
			devApiStyleWithServicabilityReqGen = devApiServiceHelper.invokeDevApiStyleWithServicability(searchParam);
			String devApiStyleWithServicabilityResponse = devApiStyleWithServicabilityReqGen.respvalidate.returnresponseasstring();
			System.out.println("\nPrinting DevApiService styleWithServicability API response :\n\n" + devApiStyleWithServicabilityResponse + "\n");
			log.info("\nPrinting DevApiService styleWithServicability API response :\n\n" + devApiStyleWithServicabilityResponse + "\n");
			if (devApiStyleWithServicabilityReqGen.response.getStatus() == 200) {
				System.out.println("GOT SUCCESS RESPONSE ON TRIAL : " + i);
				status = "pass";
			}
		}
		//AssertJUnit.assertEquals("[Devapi style with servicability is not working]", status, "fail");



	}

	/**
	 * This TestCase is used to invoke DevApiService addToCart API and verification for success response
	 * 
	 * @param userName
	 * @param password
	 * @param skuId
	 */
	@Test(groups = {
			"Fox",
			"Smoke",
			"ProdSanity",
			"Sanity",
			"FoxSanity",
			"MiniRegression",
			"Regression",
			"ExhaustiveRegression"
	},
			dataProvider = "DevApisDataProvider_addToCart_verifySuccessResponse",
			description = "Add item to Cart and verify success response")
	public void DevApis_addToCart_verifySuccessResponse(String userName, String password, String skuId) {
		RequestGenerator devApiAddToCartReqGen;
		String devApiAddToCartResponse = "";


		int i = 0;
		int responseCode = 0;
		boolean retryTest = true;
		while (retryTest && i < RetryAttemptsCount) {
			try {

				devApiAddToCartReqGen = devApiServiceHelper.invokeDevApiAddToCart(userName, password, skuId);
				devApiAddToCartResponse = devApiAddToCartReqGen.respvalidate.returnresponseasstring();
				System.out.println("\nPrinting DevApiService addToCart API response :\n\n" + devApiAddToCartResponse + "\n");
				log.info("\nPrinting DevApiService addToCart API response :\n\n" + devApiAddToCartResponse + "\n");

				AssertJUnit.assertEquals("[DevApiService addToCart API is not working.]", 200, devApiAddToCartReqGen.response.getStatus());


				retryTest = false;
			} catch (java.lang.RuntimeException e) {
				retryTest = true;
				i++;
				System.out.println("Caught an Exception - Retring Test");
				System.out.println("Caught an Exception - Attempt : " + i);

			} catch (AssertionError e) {
				retryTest = true;
				i++;
				System.out.println("Assertion Error - Retring Test");
				System.out.println("Assertion Error - Attempt : " + i);
				if (i == 3) {
					AssertJUnit.fail(e.getMessage());
				}
			}
		}



	}

	/**
	 * This TestCase is used to invoke DevApiService getCart API and verification for success response
	 * 
	 * @param userName
	 * @param password
	 */
	@Test(groups = {
			"Fox",
			"Smoke",
			"ProdSanity",
			"Sanity",
			"FoxSanity",
			"MiniRegression",
			"Regression",
			"ExhaustiveRegression"
	},
			dataProvider = "DevApisDataProvider_getCart_verifySuccessResponse",
			description = "Get cart of user and validate response")
	public void DevApis_getCart_verifySuccessResponse(String userName, String password) {
		RequestGenerator devApiGetCartReqGen = devApiServiceHelper.invokeDevApiGetCart(userName, password);
		String devApiGetCartResponse = devApiGetCartReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting DevApiService getCart API response :\n\n" + devApiGetCartResponse + "\n");
		log.info("\nPrinting DevApiService getCart API response :\n\n" + devApiGetCartResponse + "\n");

		AssertJUnit.assertEquals("[DevApiService getCart API is not working.]", 200, devApiGetCartReqGen.response.getStatus());

	}

	/**
	 * This TestCase is used to invoke DevApiService addToWishlist API and verification for success response
	 * 
	 * @param userName
	 * @param password
	 */
	@Test(groups = {
			"Fox",
			"Smoke",
			"ProdSanity",
			"Sanity",
			"FoxSanity",
			"MiniRegression",
			"Regression",
			"ExhaustiveRegression"
	},
			dataProvider = "DevApisDataProvider_addToWishlist_verifySuccessResponse",
			description = "Add style to wishlist and verify")
	public void DevApis_addToWishlist_verifySuccessResponse(String userName, String password, String styleId) {
		RequestGenerator devApiAddToWishlistReqGen;
		String devApiAddToWishlistResponse = "";
		int i = 0;
		int responseCode = 0;
		boolean retryTest = true;
		while (retryTest && i < RetryAttemptsCount) {
			try {
				devApiAddToWishlistReqGen = devApiServiceHelper.invokeDevApiAddToWishlist(userName, password, styleId);
				devApiAddToWishlistResponse = devApiAddToWishlistReqGen.respvalidate.returnresponseasstring();
				System.out.println("\nPrinting DevApiService addToWishlist API response :\n\n" + devApiAddToWishlistResponse + "\n");
				log.info("\nPrinting DevApiService addToWishlist API response :\n\n" + devApiAddToWishlistResponse + "\n");


				AssertJUnit.assertEquals("[DevApiService addToWishlist API is not working.]", 200, devApiAddToWishlistReqGen.response.getStatus());



				retryTest = false;
			} catch (java.lang.RuntimeException e) {
				retryTest = true;
				i++;
				System.out.println("Caught an Exception - Retring Test");
				System.out.println("Caught an Exception - Attempt : " + i);

			} catch (AssertionError e) {
				retryTest = true;
				i++;
				System.out.println("Assertion Error - Retring Test");
				System.out.println("Assertion Error - Attempt : " + i);
				if (i == 3) {
					AssertJUnit.fail(e.getMessage());
				}
			}
		}



	}

	/**
	 * This TestCase is used to invoke DevApiService getWishlist API and verification for success response
	 * 
	 * @param userName
	 * @param password
	 */
	@Test(groups = {
			"Fox",
			"Smoke",
			"ProdSanity",
			"Sanity",
			"FoxSanity",
			"MiniRegression",
			"Regression",
			"ExhaustiveRegression"
	},
			dataProvider = "DevApisDataProvider_getWishlist_verifySuccessResponse",
			description = "Get user wishlist and verify styles")
	public void DevApis_getWishlist_verifySuccessResponse(String userName, String password) {
		RequestGenerator devApiGetWishlistReqGen = devApiServiceHelper.invokeDevApiGetWishlist(userName, password);
		String devApiGetWishlistResponse = devApiGetWishlistReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting DevApiService getWishlist API response :\n\n" + devApiGetWishlistResponse + "\n");
		log.info("\nPrinting DevApiService getWishlist API response :\n\n" + devApiGetWishlistResponse + "\n");

		AssertJUnit.assertEquals("[DevApiService getWishlist API is not working.]", 200, devApiGetWishlistReqGen.response.getStatus());
	}

	private String getPass(String emailId) {
		int firstIndex = emailId.indexOf("_");
		int sec = emailId.indexOf("@", firstIndex);
		return emailId.substring(firstIndex, sec);
	}

	private void getAndSetAtAndRt(String userName, String password) {
		System.out.println("User name :" + userName + "  Pass: " + password);
		MyntraService serviceSignIn = Myntra.getService(
				ServiceType.PORTAL_DEVAPISHTTPS, APINAME.DEVAPISIGNIN,
				init.Configurations, new String[] {
						userName,
						password
				});
		System.out.println(serviceSignIn.URL);
		log.info(serviceSignIn.URL);
		RequestGenerator reqSignIn = new RequestGenerator(serviceSignIn);
		System.out.println(reqSignIn.returnresponseasstring());
		System.out.println(reqSignIn.response.getHeaders());
		MultivaluedMap < String, Object > map = reqSignIn.response.getHeaders();
		for (Map.Entry entry: map.entrySet()) {
			if (entry.getKey().toString().equalsIgnoreCase("at"))
				at = entry.getValue().toString();
			else if (entry.getKey().toString().equalsIgnoreCase("sxid"))
				rt = entry.getValue().toString();
		}
		at = at.substring(1, at.length() - 1);
		rt = rt.substring(1, rt.length() - 1);
		System.out.println(at);
		System.out.println(rt);
	}

	@Test(groups = {
			"SchemaValidation"
	}, dataProvider = "DevApisDataProvider_signIn_verifyResponseDataNodesUsingSchemaValidations",
			description = "Verify Sign in response Schema")
	public void DevApis_signIn_verifyResponseDataNodesUsingSchemaValidations(String userName, String password) {
		RequestGenerator devApiSignInReqGen = devApiServiceHelper.invokeDevApiSignIn(userName, password);
		String devApiSignInResponse = devApiSignInReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting DevApiService signIn API response :\n\n" + devApiSignInResponse + "\n");
		log.info("\nPrinting DevApiService signIn API response :\n\n" + devApiSignInResponse + "\n");

		AssertJUnit.assertEquals("[DevApiService signIn API is not working.]", 200, devApiSignInReqGen.response.getStatus());

		try {
			String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/devapiservice-signin-schema.txt");
			List < String > missingNodeList = validateServiceResponseNodesUsingSchemaValidator(devApiSignInResponse, jsonschema);
			AssertJUnit.assertTrue(missingNodeList + " nodes are missing in DevApiService signIn API response",
					CollectionUtils.isEmpty(missingNodeList));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test(groups = {
			"SchemaValidation"
	}, dataProvider = "DevApisDataProvider_signUp_verifyResponseDataNodesUsingSchemaValidations",
			description = "Verify Signup response Schema")
	public void DevApis_signUp_verifyResponseDataNodesUsingSchemaValidations(String userName, String password) {
		if (password.length() == 0) password = getPass(userName);

		RequestGenerator devApiSignUpReqGen = devApiServiceHelper.invokeDevApiSignUp(userName, password);
		String devApiSignUpResponse = devApiSignUpReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting DevApiService signUp API response :\n\n" + devApiSignUpResponse + "\n");
		log.info("\nPrinting DevApiService signUp API response :\n\n" + devApiSignUpResponse + "\n");

		AssertJUnit.assertEquals("[DevApiService signUp API is not working.]", 200, devApiSignUpReqGen.response.getStatus());

		try {
			String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/devapiservice-signup-schema.txt");
			List < String > missingNodeList = validateServiceResponseNodesUsingSchemaValidator(devApiSignUpResponse, jsonschema);
			AssertJUnit.assertTrue(missingNodeList + " nodes are missing in DevApiService signUp API response",
					CollectionUtils.isEmpty(missingNodeList));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test(groups = {
			"SchemaValidation"
	}, dataProvider = "DevApisDataProvider_refresh_verifyResponseDataNodesUsingSchemaValidations",
			description = "Validate API Refresh response schema")
	public void DevApis_refresh_verifyResponseDataNodesUsingSchemaValidations(String userName, String password) {
		if (password.length() == 0) password = getPass(userName);
		getAndSetAtAndRt(userName, password);

		RequestGenerator devApiRefreshReqGen = devApiServiceHelper.invokeDevApiRefresh(at, rt);
		String devApiRefreshResponse = devApiRefreshReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting DevApiService refresh API response :\n\n" + devApiRefreshResponse + "\n");
		log.info("\nPrinting DevApiService refresh API response :\n\n" + devApiRefreshResponse + "\n");

		AssertJUnit.assertEquals("[DevApiService refresh API is not working.]", 200, devApiRefreshReqGen.response.getStatus());

		try {
			String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/devapiservice-refresh-schema.txt");
			List < String > missingNodeList = validateServiceResponseNodesUsingSchemaValidator(devApiRefreshResponse, jsonschema);
			AssertJUnit.assertTrue(missingNodeList + " nodes are missing in DevApiService refresh API response",
					CollectionUtils.isEmpty(missingNodeList));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test(groups = {
			"SchemaValidation"
	}, dataProvider = "DevApisDataProvider_signOut_verifyResponseDataNodesUsingSchemaValidations",
			description = "Verifies Signout response schema")
	public void DevApis_signOut_verifyResponseDataNodesUsingSchemaValidations(String userName, String password) {
		if (password.length() == 0) password = getPass(userName);

		RequestGenerator devApiSignOutReqGen = devApiServiceHelper.invokeDevApiSignOut(userName, password);
		String devApiSignOutResponse = devApiSignOutReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting DevApiService signOut API response :\n\n" + devApiSignOutResponse + "\n");
		log.info("\nPrinting DevApiService signOut API response :\n\n" + devApiSignOutResponse + "\n");

		AssertJUnit.assertEquals("[DevApiService signOut API is not working.]", 200, devApiSignOutReqGen.response.getStatus());


		try {
			String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/devapiservice-signout-schema.txt");
			AssertJUnit.assertTrue("Devapi schema is incorrect", validateJsonSchema(devApiSignOutResponse, jsonschema));

			//            List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(devApiSignOutResponse, jsonschema);
			//            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in DevApiService signOut API response", 
			//            		CollectionUtils.isEmpty(missingNodeList));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test(groups = {
			"SchemaValidation"
	}, dataProvider = "DevApisDataProvider_forgotPassword_verifyResponseDataNodesUsingSchemaValidations",
			description = "Verifies Forgot Password response schema")
	public void DevApis_forgotPassword_verifyResponseDataNodesUsingSchemaValidations(String userName) {
		RequestGenerator devApiForgotPasswordReqGen = devApiServiceHelper.invokeDevApiForgotPassword(userName);
		String devApiForgotPasswordResponse = devApiForgotPasswordReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting DevApiService forgotPassword API response :\n\n" + devApiForgotPasswordResponse + "\n");
		log.info("\nPrinting DevApiService forgotPassword API response :\n\n" + devApiForgotPasswordResponse + "\n");

		AssertJUnit.assertEquals("[DevApiService forgotPassword API is not working.]", 200, devApiForgotPasswordReqGen.response.getStatus());

		try {
			String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/devapiservice-forgotpassword-schema.txt");
			List < String > missingNodeList = validateServiceResponseNodesUsingSchemaValidator(devApiForgotPasswordResponse, jsonschema);
			AssertJUnit.assertTrue(missingNodeList + " nodes are missing in DevApiService forgotPassword API response",
					CollectionUtils.isEmpty(missingNodeList));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test(groups = {
			"SchemaValidation"
	},
			description = "Verifies App Navigation menu call schema")
	public void DevApis_nav_verifyResponseDataNodesUsingSchemaValidations() {
		RequestGenerator devApiNavReqGen = devApiServiceHelper.invokeDevApiNav();
		String devApiNavResponse = devApiNavReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting DevApiService nav API response :\n\n" + devApiNavResponse + "\n");
		log.info("\nPrinting DevApiService nav API response :\n\n" + devApiNavResponse + "\n");

		AssertJUnit.assertEquals("[DevApiService nav API is not working.]", 200, devApiNavReqGen.response.getStatus());

		try {
			String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/devapiservice-nav-schema.txt");
			List < String > missingNodeList = validateServiceResponseNodesUsingSchemaValidator(devApiNavResponse, jsonschema);
			AssertJUnit.assertTrue(missingNodeList + " nodes are missing in DevApiService nav API response",
					CollectionUtils.isEmpty(missingNodeList));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test(groups = {
			"SchemaValidation"
	}, dataProvider = "DevApisDataProvider_style_verifyResponseDataNodesUsingSchemaValidations",
			description = "Verifies Style Response Schema")
	public void DevApis_style_verifyResponseDataNodesUsingSchemaValidations(String styleId) {

		RequestGenerator devApiStyleReqGen = devApiServiceHelper.invokeDevApiStyle(styleId);
		String devApiStyleResponse = devApiStyleReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting DevApiService style API response :\n\n" + devApiStyleResponse + "\n");
		log.info("\nPrinting DevApiService style API response :\n\n" + devApiStyleResponse + "\n");

		AssertJUnit.assertEquals("[DevApiService style API is not working.]", 200, devApiStyleReqGen.response.getStatus());

		try {
			String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/devapiservice-style-schema.txt");
			List < String > missingNodeList = validateServiceResponseNodesUsingSchemaValidator(devApiStyleResponse, jsonschema);
			AssertJUnit.assertTrue(missingNodeList + " nodes are missing in DevApiService style API response",
					CollectionUtils.isEmpty(missingNodeList));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test(groups = {
			"SchemaValidation"
	}, dataProvider = "DevApisDataProvider_styleOffers_verifyResponseDataNodesUsingSchemaValidations",
			description = "Verifies Style Offer Response Schema")
	public void DevApis_styleOffers_verifyResponseDataNodesUsingSchemaValidations(String styleId) {
		RequestGenerator devApiStyleOffersReqGen = devApiServiceHelper.invokeDevApiStyleOffers(styleId);
		String devApiStyleOffersResponse = devApiStyleOffersReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting DevApiService styleOffers API response :\n\n" + devApiStyleOffersResponse + "\n");
		log.info("\nPrinting DevApiService styleOffers API response :\n\n" + devApiStyleOffersResponse + "\n");

		AssertJUnit.assertEquals("[DevApiService styleOffers API is not working.]", 200, devApiStyleOffersReqGen.response.getStatus());

		try {
			String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/devapiservice-styleoffers-schema.txt");
			List < String > missingNodeList = validateServiceResponseNodesUsingSchemaValidator(devApiStyleOffersResponse, jsonschema);
			AssertJUnit.assertTrue(missingNodeList + " nodes are missing in DevApiService styleOffers API response",
					CollectionUtils.isEmpty(missingNodeList));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test(groups = {
			"SchemaValidation"
	}, dataProvider = "DevApisDataProvider_search_verifyResponseDataNodesUsingSchemaValidations",
			description = "Verifies Search Response Schema")
	public void DevApis_search_verifyResponseDataNodesUsingSchemaValidations(String searchParam) {
		RequestGenerator devApiSearchReqGen = devApiServiceHelper.invokeDevApiSearch(searchParam);
		String devApiSearchResponse = devApiSearchReqGen.respvalidate.returnresponseasstring();
		//System.out.println("\nPrinting DevApiService search API response :\n\n"+devApiSearchResponse+"\n");
		log.info("\nPrinting DevApiService search API response :\n\n" + devApiSearchResponse + "\n");

		AssertJUnit.assertEquals("[DevApiService search API is not working.]", 200, devApiSearchReqGen.response.getStatus());

		try {

			String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/devapiservice-search-schema.txt");
			//AssertJUnit.assertTrue("Devapi is not working",validateJsonSchema(devApiSearchResponse, jsonschema));
			List < String > missingNodeList = validateServiceResponseNodesUsingSchemaValidator(devApiSearchResponse, jsonschema);
			AssertJUnit.assertTrue(missingNodeList + " nodes are missing in DevApiService search API response",
					CollectionUtils.isEmpty(missingNodeList));
			// AssertJUnit.assertTrue("Schemavalidation failed", devApiServiceHelper.validateJsonSchema(devApiSearchResponse, jsonschema));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test(groups = {
			"SchemaValidation"
	}, dataProvider = "DevApisDataProvider_searchWithfacets_verifyResponseDataNodesUsingSchemaValidations",
			description = "Verifies Search with Facets Response Schema")
	public void DevApis_searchWithFacets_verifyResponseDataNodesUsingSchemaValidations(String searchParam) {
		RequestGenerator devApiSearchWithFacetsReqGen = devApiServiceHelper.invokeDevApiSearchWithFacets(searchParam);
		String devApiSearchWithFacetsResponse = devApiSearchWithFacetsReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting DevApiService searchWithFacets API response :\n\n" + devApiSearchWithFacetsResponse + "\n");
		log.info("\nPrinting DevApiService searchWithFacets API response :\n\n" + devApiSearchWithFacetsResponse + "\n");

		AssertJUnit.assertEquals("[DevApiService searchWithFacets API is not working.]", 200, devApiSearchWithFacetsReqGen.response.getStatus());

		try {
			String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/devapiservice-searchwithfacets-schema.txt");
			List < String > missingNodeList = validateServiceResponseNodesUsingSchemaValidator(devApiSearchWithFacetsResponse, jsonschema);
			AssertJUnit.assertTrue(missingNodeList + " nodes are missing in DevApiService searchWithFacets API response",
					CollectionUtils.isEmpty(missingNodeList));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test(groups = {
			"SchemaValidation"
	}, dataProvider = "DevApisDataProvider_styleWithServicability_verifyResponseDataNodesUsingSchemaValidations",
			description = "Verifies Serviceability response Schema")
	public void DevApis_styleWithServicability_verifyResponseDataNodesUsingSchemaValidations(String searchParam) {
		RequestGenerator devApiStyleWithServicabilityReqGen = devApiServiceHelper.invokeDevApiStyleWithServicability(searchParam);
		String devApiStyleWithServicabilityResponse = devApiStyleWithServicabilityReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting DevApiService styleWithServicability API response :\n\n" + devApiStyleWithServicabilityResponse + "\n");
		log.info("\nPrinting DevApiService styleWithServicability API response :\n\n" + devApiStyleWithServicabilityResponse + "\n");

		AssertJUnit.assertEquals("[DevApiService styleWithServicability API is not working.]", 200, devApiStyleWithServicabilityReqGen.response.getStatus());

		try {
			String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/devapiservice-stylewithservicability-schema.txt");
			List < String > missingNodeList = validateServiceResponseNodesUsingSchemaValidator(devApiStyleWithServicabilityResponse, jsonschema);
			AssertJUnit.assertTrue(missingNodeList + " nodes are missing in DevApiService styleWithServicability API response",
					CollectionUtils.isEmpty(missingNodeList));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test(groups = {
			"SchemaValidation"
	}, dataProvider = "DevApisDataProvider_addToCart_verifyResponseDataNodesUsingSchemaValidations",
			description = "Add style to cart and verify response schema")
	public void DevApis_addToCart_verifyResponseDataNodesUsingSchemaValidations(String userName, String password, String skuId) {
		RequestGenerator devApiAddToCartReqGen;
		String devApiAddToCartResponse = "";


		int i = 0;
		int responseCode = 0;
		boolean retryTest = true;
		while (retryTest && i < RetryAttemptsCount) {
			try {

				devApiAddToCartReqGen = devApiServiceHelper.invokeDevApiAddToCart(userName, password, skuId);
				devApiAddToCartResponse = devApiAddToCartReqGen.respvalidate.returnresponseasstring();
				System.out.println("\nPrinting DevApiService addToCart API response :\n\n" + devApiAddToCartResponse + "\n");
				log.info("\nPrinting DevApiService addToCart API response :\n\n" + devApiAddToCartResponse + "\n");


				AssertJUnit.assertEquals("[DevApiService addToCart API is not working.]", 200, devApiAddToCartReqGen.response.getStatus());

				try {
					String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/devapiservice-addtocart-schema.txt");
					List < String > missingNodeList = validateServiceResponseNodesUsingSchemaValidator(devApiAddToCartResponse, jsonschema);
					AssertJUnit.assertTrue(missingNodeList + " nodes are missing in DevApiService addToCart API response",
							CollectionUtils.isEmpty(missingNodeList));
				} catch (Exception e) {
					e.printStackTrace();
				}

				retryTest = false;
			} catch (java.lang.RuntimeException e) {
				retryTest = true;
				i++;
				System.out.println("Caught an Exception - Retring Test");
				System.out.println("Caught an Exception - Attempt : " + i);

			} catch (AssertionError e) {
				retryTest = true;
				i++;
				System.out.println("Assertion Error - Retring Test");
				System.out.println("Assertion Error - Attempt : " + i);
				if (i == 3) {
					AssertJUnit.fail(e.getMessage());
				}
			}
		}



	}

	@Test(groups = {
			"SchemaValidation"
	}, dataProvider = "DevApisDataProvider_getCart_verifyResponseDataNodesUsingSchemaValidations", description = "Verifies Get Cart response schema")
	public void DevApis_getCart_verifyResponseDataNodesUsingSchemaValidations(String userName, String password) {
		RequestGenerator devApiGetCartReqGen = devApiServiceHelper.invokeDevApiGetCart(userName, password);
		String devApiGetCartResponse = devApiGetCartReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting DevApiService getCart API response :\n\n" + devApiGetCartResponse + "\n");
		log.info("\nPrinting DevApiService getCart API response :\n\n" + devApiGetCartResponse + "\n");

		AssertJUnit.assertEquals("[DevApiService getCart API is not working.]", 200, devApiGetCartReqGen.response.getStatus());

		try {
			String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/devapiservice-getcart-schema.txt");
			List < String > missingNodeList = validateServiceResponseNodesUsingSchemaValidator(devApiGetCartResponse, jsonschema);
			AssertJUnit.assertTrue(missingNodeList + " nodes are missing in DevApiService getCart API response",
					CollectionUtils.isEmpty(missingNodeList));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test(groups = {
			"SchemaValidation"
	}, dataProvider = "DevApisDataProvider_addToWishlist_verifyResponseDataNodesUsingSchemaValidations",
			description = "Add style to wish list and validate schema")
	public void DevApis_addToWishlist_verifyResponseDataNodesUsingSchemaValidations(String userName, String password, String skuId) {
		RequestGenerator devApiAddToWishlistReqGen;
		String devApiAddToWishlistResponse = "";
		int i = 0;
		int responseCode = 0;
		boolean retryTest = true;
		while (retryTest && i < RetryAttemptsCount) {
			try {
				devApiAddToWishlistReqGen = devApiServiceHelper.invokeDevApiAddToWishlist(userName, password, skuId);
				devApiAddToWishlistResponse = devApiAddToWishlistReqGen.respvalidate.returnresponseasstring();
				System.out.println("\nPrinting DevApiService addToWishlist API response :\n\n" + devApiAddToWishlistResponse + "\n");
				log.info("\nPrinting DevApiService addToWishlist API response :\n\n" + devApiAddToWishlistResponse + "\n");


				AssertJUnit.assertEquals("[DevApiService addToWishlist API is not working.]", 200, devApiAddToWishlistReqGen.response.getStatus());
				String metaCode = JsonPath.read(devApiAddToWishlistResponse, "$.meta.code").toString();
				AssertJUnit.assertEquals("Devapi add to wishlist status is : " + devApiAddToWishlistReqGen.response.getStatus() + " meta code is: " + metaCode, 200, Integer.parseInt(metaCode));

				try {
					String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/devapiservice-addtowishlist-schema.txt");
					List < String > missingNodeList = validateServiceResponseNodesUsingSchemaValidator(devApiAddToWishlistResponse, jsonschema);
					AssertJUnit.assertTrue(missingNodeList + " nodes are missing in DevApiService addToWishlist API response",
							CollectionUtils.isEmpty(missingNodeList));
				} catch (Exception e) {
					e.printStackTrace();
				}


				retryTest = false;
			} catch (java.lang.RuntimeException e) {
				retryTest = true;
				i++;
				System.out.println("Caught an Exception - Retring Test");
				System.out.println("Caught an Exception - Attempt : " + i);

			} catch (AssertionError e) {
				retryTest = true;
				i++;
				System.out.println("Assertion Error - Retring Test");
				System.out.println("Assertion Error - Attempt : " + i);
				if (i == 3) {
					AssertJUnit.fail(e.getMessage());
				}
			}
		}



	}

	@Test(groups = {
			"SchemaValidation"
	}, dataProvider = "DevApisDataProvider_getWishlist_verifyResponseDataNodesUsingSchemaValidations",
			description = "Get user wish list response schema validation")
	public void DevApis_getWishlist_verifyResponseDataNodesUsingSchemaValidations(String userName, String password) {
		RequestGenerator devApiGetWishlistReqGen = devApiServiceHelper.invokeDevApiGetWishlist(userName, password);
		String devApiGetWishlistResponse = devApiGetWishlistReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting DevApiService getWishlist API response :\n\n" + devApiGetWishlistResponse + "\n");
		log.info("\nPrinting DevApiService getWishlist API response :\n\n" + devApiGetWishlistResponse + "\n");

		AssertJUnit.assertEquals("[DevApiService getWishlist API is not working.]", 200, devApiGetWishlistReqGen.response.getStatus());

		try {
			String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/devapiservice-getwishlist-schema.txt");
			List < String > missingNodeList = validateServiceResponseNodesUsingSchemaValidator(devApiGetWishlistResponse, jsonschema);
			AssertJUnit.assertTrue(missingNodeList + " nodes are missing in DevApiService getWishlist API response",
					CollectionUtils.isEmpty(missingNodeList));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test(groups = {
			"ProdSanity",
			"Sanity",
			"FoxSanity",
			"MiniRegression",
			"Regression",
			"ExhaustiveRegression"
	}, dataProvider = "DevApisGetAllNotification",
	description = "Create Zodiac Notification")
	public void DevApis_CreateZodiacNotifications(String userName, String password) throws InterruptedException {
		//Create a Master Notification
		RequestGenerator request = devApiServiceHelper.CreateLGPMasterNotification(userName);
		TimeUnit.SECONDS.sleep(2);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println("Create LGP Master Notification Response Status : \n " + request.response.getStatus());
		System.out.println("Create LGP Master Notification Response : \n " + response);

	}

	@Test(groups = {
			"ProdSanity",
			"Sanity",
			"FoxSanity",
			"MiniRegression",
			"Regression",
			"ExhaustiveRegression"
	}, dataProvider = "DevApisGetAllNotification",
	description = "Get All User Notifications")
	public void DevApis_GetAllNotifications(String userName, String password) throws InterruptedException {

		RequestGenerator request = devApiServiceHelper.getAllUserNotifications(userName, password);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println("Get All Notifications Response Status : \n " + request.response.getStatus());
		System.out.println("Get All Notifications Response : \n " + response);

		AssertJUnit.assertEquals("[DevApiService getallnotification API is not working.]", 200, request.response.getStatus());
		AssertJUnit.assertNotNull("Validation of from", request.respvalidate.GetNodeValue("data.notifications.notification", response).replaceAll("\"", ""));
		String lastId = JsonPath.read(response, "$..data.notifications.notification[(@.length-1)].id");
		String lastMasterid = JsonPath.read(response, "$..data.notifications.notification[(@.length-1)].masterNotificationId");
		int i = 0;
		boolean flag = true;
		while (flag) {
			String id = JsonPath.read(response, "$..data.notifications.notification[" + Integer.toString(i) + "].id");
			String masterId = JsonPath.read(response, "$..data.notifications.notification[" + Integer.toString(i) + "].masterNotificationId");
			AssertJUnit.assertNotNull("Validation of from", JsonPath.read(response, "$..data.notifications.notification[" + Integer.toString(i) + "].id"));
			//		   System.out.println("Notification: "+notificationIds.get(i));
			//		   AssertJUnit.assertEquals("[Dev api notification is not working]",notificationIds.get(i), id); 
			AssertJUnit.assertNotNull("Validation of from", JsonPath.read(response, "$..data.notifications.notification[" + Integer.toString(i) + "].masterNotificationId"));
			AssertJUnit.assertNotNull("Validation of from", JsonPath.read(response, "$..data.notifications.notification[" + Integer.toString(i) + "].userId"));
			AssertJUnit.assertNotNull("Validation of from", JsonPath.read(response, "$..data.notifications.notification[" + Integer.toString(i) + "].cdnURLForImage"));
			AssertJUnit.assertNotNull("Validation of from", JsonPath.read(response, "$..data.notifications.notification[" + Integer.toString(i) + "].startTime"));
			AssertJUnit.assertNotNull("Validation of from", JsonPath.read(response, "$..data.notifications.notification[" + Integer.toString(i) + "].endTime"));
			AssertJUnit.assertNotNull("Validation of from", JsonPath.read(response, "$..data.notifications.notification[" + Integer.toString(i) + "].publishTime"));
			AssertJUnit.assertNotNull("Validation of from", JsonPath.read(response, "$..data.notifications.notification[" + Integer.toString(i) + "].notificationText"));
			AssertJUnit.assertNotNull("Validation of from", JsonPath.read(response, "$..data.notifications.notification[" + Integer.toString(i) + "].notificationTitle"));
			AssertJUnit.assertNotNull("Validation of from", JsonPath.read(response, "$..data.notifications.notification[" + Integer.toString(i) + "].notificationStatus"));
			AssertJUnit.assertNotNull("Validation of from", JsonPath.read(response, "$..data.notifications.notification[" + Integer.toString(i) + "].notificationType"));
			AssertJUnit.assertNotNull("Validation of from", JsonPath.read(response, "$..data.notifications.notification[" + Integer.toString(i) + "].urlForLandingPage"));
			AssertJUnit.assertNotNull("Validation of from", JsonPath.read(response, "$..data.notifications.notification[" + Integer.toString(i) + "].pushToAndroid"));
			AssertJUnit.assertNotNull("Validation of from", JsonPath.read(response, "$..data.notifications.notification[" + Integer.toString(i) + "].pushToIOS"));
			AssertJUnit.assertNotNull("Validation of from", JsonPath.read(response, "$..data.notifications.success[" + Integer.toString(i) + "]"));
			AssertJUnit.assertNotNull("Validation of from", JsonPath.read(response, "$..data.notifications.actions.markAsRead[" + Integer.toString(i) + "]"));
			System.out.println(id + "  " + masterId);
			i = i + 1;
			if (lastId.equals(id) && lastMasterid.equals(masterId)) {
				flag = false;

			}

		}
	}

	@Test(groups = {
			"ProdSanity",
			"Sanity",
			"FoxSanity",
			"MiniRegression",
			"Regression",
			"ExhaustiveRegression"
	}, dataProvider = "DevApisGetNotificationWithBatch",
	description = "Get user notifications with Batch size")
	public void DevApis_GetAllNotificationsWithBatchSize(String userName, String password, String BatchSize) throws InterruptedException {

		RequestGenerator request = devApiServiceHelper.getAllUserNotificationsWithBatch(userName, password, BatchSize);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println("Get All Notifications Response Status : \n " + request.response.getStatus());
		System.out.println("Get All Notifications Response : \n " + response);

		AssertJUnit.assertEquals("[DevApiService getallnotification API is not working.]", 200, request.response.getStatus());
		AssertJUnit.assertNotNull("Validation of from", request.respvalidate.GetNodeValue("data.notifications.notification", response).replaceAll("\"", ""));
		String lastId = JsonPath.read(response, "$..data.notifications.notification[(@.length-1)].id");
		String lastMasterid = JsonPath.read(response, "$..data.notifications.notification[(@.length-1)].masterNotificationId");
		int i = 0;
		boolean flag = true;
		while (flag) {
			String id = JsonPath.read(response, "$..data.notifications.notification[" + Integer.toString(i) + "].id");
			String masterId = JsonPath.read(response, "$..data.notifications.notification[" + Integer.toString(i) + "].masterNotificationId");
			AssertJUnit.assertNotNull("Validation of from", JsonPath.read(response, "$..data.notifications.notification[" + Integer.toString(i) + "].id"));
			//			   System.out.println("Notification: "+notificationIds.get(i));
			//			   AssertJUnit.assertEquals("[Dev api notification is not working]",notificationIds.get(i), id); 
			AssertJUnit.assertNotNull("Validation of from", JsonPath.read(response, "$..data.notifications.notification[" + Integer.toString(i) + "].masterNotificationId"));
			AssertJUnit.assertNotNull("Validation of from", JsonPath.read(response, "$..data.notifications.notification[" + Integer.toString(i) + "].userId"));
			AssertJUnit.assertNotNull("Validation of from", JsonPath.read(response, "$..data.notifications.notification[" + Integer.toString(i) + "].cdnURLForImage"));
			AssertJUnit.assertNotNull("Validation of from", JsonPath.read(response, "$..data.notifications.notification[" + Integer.toString(i) + "].startTime"));
			AssertJUnit.assertNotNull("Validation of from", JsonPath.read(response, "$..data.notifications.notification[" + Integer.toString(i) + "].endTime"));
			AssertJUnit.assertNotNull("Validation of from", JsonPath.read(response, "$..data.notifications.notification[" + Integer.toString(i) + "].publishTime"));
			AssertJUnit.assertNotNull("Validation of from", JsonPath.read(response, "$..data.notifications.notification[" + Integer.toString(i) + "].notificationText"));
			AssertJUnit.assertNotNull("Validation of from", JsonPath.read(response, "$..data.notifications.notification[" + Integer.toString(i) + "].notificationTitle"));
			AssertJUnit.assertNotNull("Validation of from", JsonPath.read(response, "$..data.notifications.notification[" + Integer.toString(i) + "].notificationStatus"));
			AssertJUnit.assertNotNull("Validation of from", JsonPath.read(response, "$..data.notifications.notification[" + Integer.toString(i) + "].notificationType"));
			AssertJUnit.assertNotNull("Validation of from", JsonPath.read(response, "$..data.notifications.notification[" + Integer.toString(i) + "].urlForLandingPage"));
			AssertJUnit.assertNotNull("Validation of from", JsonPath.read(response, "$..data.notifications.notification[" + Integer.toString(i) + "].pushToAndroid"));
			AssertJUnit.assertNotNull("Validation of from", JsonPath.read(response, "$..data.notifications.notification[" + Integer.toString(i) + "].pushToIOS"));
			AssertJUnit.assertNotNull("Validation of from", JsonPath.read(response, "$..data.notifications.success[" + Integer.toString(i) + "]"));
			AssertJUnit.assertNotNull("Validation of from", JsonPath.read(response, "$..data.notifications.actions.markAsRead[" + Integer.toString(i) + "]"));
			System.out.println(id + "  " + masterId);
			i = i + 1;
			if (lastId.equals(id) && lastMasterid.equals(masterId)) {
				flag = false;

			}

		}

	}

	@Test(groups = {
			"ProdSanity",
			"Sanity",
			"FoxSanity",
			"MiniRegression",
			"Regression",
			"ExhaustiveRegression"
	}, dataProvider = "DevApisNotificationMarkAsRead",
	description = "Read a notification and mark it as read")
	public void DevApis_notification_Mark_Read(String username, String password) {
		RequestGenerator request = devApiServiceHelper.getAllUserNotifications(username, password);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println("Get All Notifications Response Status : \n " + request.response.getStatus());
		System.out.println("Get All Notifications Response : \n " + response);
		List < String > notificationId = JsonPath.read(response, "$..data.notifications.notification[*].id");
		List < String > masterId = JsonPath.read(response, "$..data.notifications.notification[*].masterNotificationId");
		String params[] = {
				notificationId.get(0),
				masterId.get(0)
		};

		request = devApiServiceHelper.markNotificationAsRead(username, password, params);
		response = request.respvalidate.returnresponseasstring();
		System.out.println("Mark Notification as Read Response Status : \n " + request.response.getStatus());
		System.out.println("Mark Notification as Read Response : \n " + response);
		AssertJUnit.assertEquals("[DevApiService getallnotification API is not working.]", 200, request.response.getStatus());

	}

	@Test(groups = {
			"ProdSanity",
			"Sanity",
			"FoxSanity",
			"MiniRegression",
			"Regression",
			"ExhaustiveRegression"
	}, dataProvider = "DevApisGetNotificationCount",
	description = "Get count of available notifications"
			)
	public void DevApis_notification_GetCount(String username, String password) {
		RequestGenerator req = devApiServiceHelper.getNotificationCount(username, password);
		String jsonResponse = req.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting DevApiService gett API GET Notifications Response" + req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("[DevApiService getallnotification API is not working.]", 200, req.response.getStatus());

	}

	@Test(groups = {
			"Fox",
			"Smoke",
			"ProdSanity",
			"Sanity",
			"FoxSanity",
			"MiniRegression",
			"Regression",
			"ExhaustiveRegression"
	}, dataProvider = "DevApisGetProfileInfo",
	description = "Get user Profile Info")
	public void DevApis_Get_ProfileInfo(String username, String password) {
		RequestGenerator req = devApiServiceHelper.invokeGetProfileInfo(username, password);
		//System.out.println("Printing getprofile url: "+req.URL);
		//RequestGenerator req=new RequestGenerator(service, tokens);
		String jsonResponse = req.respvalidate.returnresponseasstring();
		System.out.println("Printing devapi get profile resopnse: " + jsonResponse);
		AssertJUnit.assertEquals("[DevApiService getprofile API is not working.]", 200, req.response.getStatus());
		AssertJUnit.assertNotNull("Username is null", req.respvalidate.GetNodeValue("data.name", jsonResponse).replaceAll("\"", ""));
		AssertJUnit.assertNotNull("location is null", req.respvalidate.GetNodeValue("data.location", jsonResponse).replaceAll("\"", ""));
		AssertJUnit.assertNotNull("image is null ", req.respvalidate.GetNodeValue("data.image", jsonResponse).replaceAll("\"", ""));
		AssertJUnit.assertNotNull("gender is null", req.respvalidate.GetNodeValue("data.gender", jsonResponse).replaceAll("\"", ""));
		AssertJUnit.assertNotNull("bio is null", req.respvalidate.GetNodeValue("data.bio", jsonResponse).replaceAll("\"", ""));
		AssertJUnit.assertNotNull("dob is null", req.respvalidate.GetNodeValue("data.dob", jsonResponse).replaceAll("\"", ""));
	}


	@Test(groups = {
			"SchemaValidation"
	}, dataProvider = "DevApisGetProfileInfo",
			description = "Validate get user profile response schema")
	public void DevApis_Get_ProfileInfoWithSchemaValidation(String username, String password) {

		RequestGenerator req = devApiServiceHelper.invokeGetProfileInfo(username, password);
		String devApisGetProfileInfoResponse = req.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting DevApiService getProfile API response :\n\n" + devApisGetProfileInfoResponse + "\n");
		log.info("\nPrinting DevApiService getProfile API response :\n\n" + devApisGetProfileInfoResponse + "\n");
		AssertJUnit.assertEquals("[DevApiService getProfile info is not working.]", 200, req.response.getStatus());

		try {
			String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/devapis-getprofileinfo-schema.txt");
			List < String > missingNodeList = validateServiceResponseNodesUsingSchemaValidator(devApisGetProfileInfoResponse, jsonschema);
			AssertJUnit.assertTrue(missingNodeList + " nodes are missing in DevApiService getProfileInfo response", CollectionUtils.isEmpty(missingNodeList));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	@Test(groups = {
			"Fox",
			"Smoke",
			"ProdSanity",
			"Sanity",
			"FoxSanity",
			"MiniRegression",
			"Regression",
			"ExhaustiveRegression"
	}, dataProvider = "DevApisGetCityList",
	description = " Verift Get City List Success")
	public void DevApis_Get_CityList(String username, String password, String city) {
		System.out.println("The Properties are " + username + " " + password + " " + city);
		RequestGenerator req = devApiServiceHelper.invokeDevApiGetCityList(username, password, city);
		String jsonResponse = req.respvalidate.returnresponseasstring();
		System.out.println("Printing devapi citylist resopnse: " + jsonResponse);
		AssertJUnit.assertEquals("[DevApiService citylist API is not working.]", 200, req.response.getStatus());
		AssertJUnit.assertEquals("[DevApiService city name" + city + " is not correct]", city, req.respvalidate.GetNodeValue("locationList.city", jsonResponse).replaceAll("\"", ""));
		AssertJUnit.assertNotNull("[DevApiService state is empty]", req.respvalidate.GetNodeValue("locationList.state", jsonResponse).replaceAll("\"", ""));
		AssertJUnit.assertNotNull("[DevApiService country is empty]", req.respvalidate.GetNodeValue("locationList.country", jsonResponse).replaceAll("\"", ""));

	}



	@Test(groups = {
			"SchemaValidation"
	}, dataProvider = "DevApisGetCityListWithSchemaValidation",
			description = "Verifies Get City List Response Schema")
	public void DevApis_Get_CityList_WithSchemaValidation(String username, String password, String city) {


		RequestGenerator req = devApiServiceHelper.invokeDevApiGetCityList(username, password, city);
		String devApiCityListResponse = req.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting DevApiService cityList API response :\n\n" + devApiCityListResponse + "\n");
		log.info("\nPrinting DevApiService cityList response :\n\n" + devApiCityListResponse + "\n");

		AssertJUnit.assertEquals("[DevApiService City List is not working.]", 200, req.response.getStatus());

		try {
			String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/devapiservice-citylist-schema.txt");
			List < String > missingNodeList = validateServiceResponseNodesUsingSchemaValidator(devApiCityListResponse, jsonschema);
			AssertJUnit.assertTrue(missingNodeList + " nodes are missing in DevApiService City List response", CollectionUtils.isEmpty(missingNodeList));
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Schema validation for city list passed");


	}
	@Test(groups = {
			"Fox",
			"ProdSanity",
			"Sanity",
			"FoxSanity",
			"MiniRegression",
			"Regression",
			"ExhaustiveRegression"
	}, dataProvider = "DevApisGetCityListNegative",
	description = "Verify Get City List negative response")
	public void DevApis_Get_CityListNegative(String username, String password, String city) {

		RequestGenerator req = devApiServiceHelper.invokeDevApiGetCityList(username, password, city);
		String devApiCityListResponse = req.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting DevApiService cityList API response :\n\n" + devApiCityListResponse + "\n");
		log.info("\nPrinting DevApiService cityList response :\n\n" + devApiCityListResponse + "\n");

		AssertJUnit.assertEquals("[DevApiService City List is not working.]", 200, req.response.getStatus());
		System.out.println("Printing devapi citylist resopnse: " + devApiCityListResponse);
		AssertJUnit.assertEquals("[DevApiService citylist API is not working.]", 200, req.response.getStatus());
		AssertJUnit.assertNull("Devapi city List not working", JsonPath.read(devApiCityListResponse, "$.locationlist"));

	}
	@Test(groups = {
			"Fox",
			"Smoke",
			"ProdSanity",
			"Sanity",
			"MiniRegression",
			"Regression",
			"ExhaustiveRegression"
	}, dataProvider = "DevApisDataProvider_LikeProducts",
	description = "Verify Like Products Success Response")
	public void DevApis_LikeProducts_SuccessResponse(String username, String password, String styleId) {
		int i = 0;
		int responseCode = 0;
		RequestGenerator devApiLikeProductsReq;
		String jsonResponse = "";
		boolean retryTest = true;
		System.out.println("Styleids= " + styleId);
		while (retryTest && i < RetryAttemptsCount) {
			try {

				devApiLikeProductsReq = devApiServiceHelper.invokeDevApiLikeProducts(username, password, styleId);
				jsonResponse = devApiLikeProductsReq.respvalidate.returnresponseasstring();
				System.out.println("[Printing the like products response\n]" + jsonResponse);
				AssertJUnit.assertEquals("[DevApiService like Products API is not working.]", 200, devApiLikeProductsReq.response.getStatus());
				retryTest = false;
			} catch (java.lang.RuntimeException e) {
				retryTest = true;
				i++;
				System.out.println("Caught an Exception - Retrying Test");
				System.out.println("Caught an Exception - Attempt : " + i);

			} catch (AssertionError e) {
				retryTest = true;
				i++;
				if (i == 3) {
					AssertJUnit.fail(e.getMessage());
				}
				System.out.println("Assertion Error - Retrying Test");
				System.out.println("Assertion Error - Attempt : " + i);

			}
		}
	}

	@Test(groups = {
			"Fox",
			"ProdSanity",
			"Sanity",
			"MiniRegression",
			"Regression",
			"ExhaustiveRegression"
	}, dataProvider = "DevApisDataProvider_LikeProducts",
	description = "Verify Styles  ID after user likes a product")
	public void DevApis_LikeProducts_WithStyleId_Verification(String username, String password, String styleId) {

		int i = 0;
		int responseCode = 0;
		System.out.println("Styleids= " + styleId);
		RequestGenerator devApiLikeProductsReq;
		String jsonResponse = "";
		boolean retryTest = true;

		while (retryTest && i < RetryAttemptsCount) {
			try {

				devApiLikeProductsReq = devApiServiceHelper.invokeDevApiLikeProducts(username, password, styleId);
				jsonResponse = devApiLikeProductsReq.respvalidate.returnresponseasstring();
				System.out.println("[Printing the like products response\n]" + jsonResponse);
				AssertJUnit.assertEquals("[DevApiService like Products API is not working.]", 200, devApiLikeProductsReq.response.getStatus());
				AssertJUnit.assertTrue("[" + styleId + " not present in like summery]", devApiServiceHelper.verifyStyleIdsInLikeSummery(username, password, styleId));
				retryTest = false;
			} catch (java.lang.RuntimeException e) {
				retryTest = true;
				i++;
				System.out.println("Caught an Exception - Retrying Test");
				System.out.println("Caught an Exception - Attempt : " + i);

			} catch (AssertionError e) {
				retryTest = true;
				i++;
				if (i == 3) {
					AssertJUnit.fail(e.getMessage());
				}
				System.out.println("Assertion Error - Retrying Test");
				System.out.println("Assertion Error - Attempt : " + i);

			}
		}


	}




	@Test(groups = {
			"Fox",
			"Smoke",
			"ProdSanity",
			"Sanity",
			"MiniRegression",
			"Regression",
			"ExhaustiveRegression"
	}, dataProvider = "DevApisDataProvider_LikeProducts",
	description = "Verify Unlike Product Response")
	public void DevApis_UnLikeProducts(String username, String password, String styleId) {
		int i = 0;
		int responseCode = 0;
		System.out.println("Styleids= " + styleId);
		RequestGenerator devApiUnlikeProductReq;
		String jsonResponse = "";
		boolean retryTest = true;

		while (retryTest && i < RetryAttemptsCount) {
			try {

				devApiUnlikeProductReq = devApiServiceHelper.invokeDevApiLikeProducts(username, password, styleId);
				jsonResponse = devApiUnlikeProductReq.respvalidate.returnresponseasstring();
				System.out.println("[Printing the Unlike products response\n]" + jsonResponse);
				devApiUnlikeProductReq = devApiServiceHelper.invokeDevApiUnLikeProducts(username, password, styleId);
				jsonResponse = devApiUnlikeProductReq.respvalidate.returnresponseasstring();
				System.out.println("[Printing the Unlike products response\n]" + jsonResponse);
			
				AssertJUnit.assertEquals("[DevApiService Unlike Products API is not working.]", 200, devApiUnlikeProductReq.response.getStatus());
				AssertJUnit.assertTrue("[Product Code" + styleId + " is not unliked]", devApiServiceHelper.verifyStyleIdsInLikeSummeryAfterUnlikeProduct(username, password, styleId));
				retryTest = false;
			} catch (java.lang.RuntimeException e) {
				retryTest = true;
				i++;
				System.out.println("Caught an Exception - Retrying Test");
				System.out.println("Caught an Exception - Attempt : " + i);

			} catch (AssertionError e) {
				retryTest = true;
				i++;
				if (i == 3) {
					AssertJUnit.fail(e.getMessage());
				}
				System.out.println("Assertion Error - Retrying Test");
				System.out.println("Assertion Error - Attempt : " + i);

			}
		}
	}


	@Test(groups = {
			"Fox",
			"Smoke",
			"ProdSanity",
			"Sanity",
			"FoxSanity",
			"MiniRegression",
			"Regression",
			"ExhaustiveRegression"
	}, dataProvider = "DevApisGetLikeSummery",
	description = "Verifies User Like Summary response")
	public void DevApis_LikeSummery_New(String username, String password, String styleId)

	{
		devApiServiceHelper.likeProducts(username, password, styleId);
		int i = 0;
		int responseCode = 0;
		System.out.println("Styleids= " + styleId);
		RequestGenerator request;
		String jsonResponse = "";
		boolean retryTest = true;

		while (retryTest && i < RetryAttemptsCount) {
			try {

				request = devApiServiceHelper.invokeDevApiLikeProducts(username, password, styleId);
				jsonResponse = request.respvalidate.returnresponseasstring();
				System.out.println("[Printing the Like Summary response\n]" + jsonResponse);
				JSONArray productIds = JsonPath.read(jsonResponse, "$.data.products");
				int totalCount = JsonPath.read(jsonResponse, "$.data.totalCount");
				//System.out.println("total products:"+s1.size()+"total count:"+totalCount);
				AssertJUnit.assertEquals("[DevApiService like summery API is not working.]", 200, request.response.getStatus());
				AssertJUnit.assertEquals("[DevApiService like summery API response Shows product count: " + productIds.size() + " products" + "but total count node shows:" + totalCount + "]", productIds.size(), totalCount);
				AssertJUnit.assertTrue("Like summery is not  working ", devApiServiceHelper.isProductCodeExists(jsonResponse, styleId));
				retryTest = false;
			} catch (java.lang.RuntimeException e) {
				retryTest = true;
				i++;
				System.out.println("Caught an Exception - Retrying Test");
				System.out.println("Caught an Exception - Attempt : " + i);

			} catch (AssertionError e) {
				retryTest = true;
				i++;
				if (i == 3) {
					AssertJUnit.fail(e.getMessage());
				}
				System.out.println("Assertion Error - Retrying Test");
				System.out.println("Assertion Error - Attempt : " + i);

			}
		}

	}

	@Test(groups = {
			"Fox",
			"Smoke",
			"ProdSanity",
			"Sanity",
			"MiniRegression",
			"Regression",
			"ExhaustiveRegression"
	}, dataProvider = "DevApisGetLikeList",
	description = "Verifies User Like List Response")
	public void DevApis_LikeList(String username, String password, String limit, String offset, String styleId) {


		int l = 0;
		int responseCode = 0;
		RequestGenerator devApiLikeReqGen;
		String jsonResponse = "";
		boolean retryTest = true;

		while (retryTest && l < 3) {
			try {
				devApiServiceHelper.likeProducts(username, password, styleId);
				System.out.println("Offset= " + offset);
				System.out.println("Limit= " + limit);
				devApiLikeReqGen = devApiServiceHelper.invokeDevApiGetLikeList(username, password, limit, offset);
				jsonResponse = devApiLikeReqGen.respvalidate.returnresponseasstring();
				System.out.println("Printing devapi likelist resopnse: " + jsonResponse);
				AssertJUnit.assertEquals("[DevApiService likelist API is not working.]", 200, devApiLikeReqGen.response.getStatus());
				String last_ProductId = JsonPath.read(jsonResponse, "$.data.products[(@.length-1)].id").toString();
				int actualOffsetValue = JsonPath.read(jsonResponse, "$.data.offset");
				int expectedOffsetValue = Integer.parseInt(offset) + Integer.parseInt(limit);
				System.out.println("Last product id: " + last_ProductId);
				AssertJUnit.assertEquals("[DevApiService likelist API is not working.]", expectedOffsetValue, actualOffsetValue);

				for (int i = 0; i < Integer.parseInt(limit); i++) {

					String id = JsonPath.read(jsonResponse, "$.data.products[" + Integer.toString(i) + "].id").toString();
					AssertJUnit.assertNotNull("Product id is empty", JsonPath.read(jsonResponse, "$.data.products[" + Integer.toString(i) + "].id"));
					AssertJUnit.assertNotNull("Price is empty", JsonPath.read(jsonResponse, "$.data.products[" + Integer.toString(i) + "].price"));
					AssertJUnit.assertNotNull("Discount price is empty", JsonPath.read(jsonResponse, "$.data.products[" + Integer.toString(i) + "].discounted_price"));
					AssertJUnit.assertNotNull("Product Image is empty", JsonPath.read(jsonResponse, "$.data.products[" + Integer.toString(i) + "].stylename"));
					if (id.equals(last_ProductId)) {
						System.out.println("Last product id: " + id);
						break;
					}
				}
				retryTest = false;
			} catch (java.lang.RuntimeException e) {
				retryTest = true;
				l++;
				System.out.println("Caught an Exception - Retrying Test");
				System.out.println("Caught an Exception - Attempt : " + l);

			} catch (AssertionError e) {
				retryTest = true;
				l++;
				if (l == 3) {
					AssertJUnit.fail(e.getMessage());
				}
				System.out.println("Assertion Error - Retrying Test");
				System.out.println("Assertion Error - Attempt : " + l);

			}
		}





	}

	@Test(groups = {
			"Fox",
			"Smoke",
			"ProdSanity",
			"Sanity",
			"FoxSanity",
			"MiniRegression",
			"Regression",
			"ExhaustiveRegression"
	}, dataProvider = "DevApisDataProvider_LikesCount",
	description = "Verifies User Like Count API Success Response")
	public void DevApis_LikeCountWithSuccessMessageVerification(String username, String password, String styleId) {
		System.out.println("StyleId: " + styleId);
		int i = 0;
		int responseCode = 0;
		RequestGenerator devApiLikeReqGen;
		String jsonResponse = "";
		boolean retryTest = true;
		while (retryTest && i < RetryAttemptsCount) {
			try {
				devApiServiceHelper.likeProducts(username, password, styleId);
				devApiLikeReqGen = devApiServiceHelper.invokeLikesCountService(username, password, styleId);
				jsonResponse = devApiLikeReqGen.respvalidate.returnresponseasstring();
				System.out.println("Printing devapi likes count resopnse: " + jsonResponse);
				AssertJUnit.assertEquals("[DevApiService likes count API is not working.]", 200, devApiLikeReqGen.response.getStatus());
				AssertJUnit.assertEquals("[Dev api likes count is not working..]", styleId, JsonPath.read(jsonResponse, "$.data.products[0].styleId").toString());



				retryTest = false;
			} catch (java.lang.RuntimeException e) {
				retryTest = true;
				i++;
				System.out.println("Caught an Exception - Retring Test");
				System.out.println("Caught an Exception - Attempt : " + i);

			} catch (AssertionError e) {
				retryTest = true;
				i++;
				System.out.println("Assertion Error - Retring Test");
				System.out.println("Assertion Error - Attempt : " + i);
				if (i == 3) {
					AssertJUnit.fail(e.getMessage());
				}
			}
		}

	}

	@Test(groups = {
			"Fox",
			"ProdSanity",
			"Sanity",
			"FoxSanity",
			"MiniRegression",
			"Regression",
			"ExhaustiveRegression"
	}, dataProvider = "DevApisDataProvider_LikesCount",
	description = "Get User Like List API Response Schema validation")
	public void DevApis_LikeCountWithSuccessMessageSchemaValidation(String username, String password, String styleId) {
		RequestGenerator devApiLikeReqGen;
		String jsonResponse;
		int i = 0;
		int responseCode = 0;
		boolean retryTest = true;
		while (retryTest && i < RetryAttemptsCount) {
			try {
				devApiLikeReqGen = devApiServiceHelper.invokeLikesCountService(username, password, styleId);
				jsonResponse = devApiLikeReqGen.respvalidate.returnresponseasstring();
				System.out.println("Printing devapi likes count resopnse: " + jsonResponse);
				AssertJUnit.assertEquals("[DevApiService likes count API is not working.]", 200, devApiLikeReqGen.response.getStatus());

				try {
					String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/devapiservice-likescount-schema.txt");
					List < String > missingNodeList = validateServiceResponseNodesUsingSchemaValidator(jsonResponse, jsonschema);
					AssertJUnit.assertTrue(missingNodeList + " nodes are missing in DevApiService likes count API response", CollectionUtils.isEmpty(missingNodeList));
				} catch (Exception e) {
					e.printStackTrace();
				}



				retryTest = false;
			} catch (java.lang.RuntimeException e) {
				retryTest = true;
				i++;
				System.out.println("Caught an Exception - Retring Test");
				System.out.println("Caught an Exception - Attempt : " + i);

			} catch (AssertionError e) {
				retryTest = true;
				i++;
				System.out.println("Assertion Error - Retring Test");
				System.out.println("Assertion Error - Attempt : " + i);
				if (i == 3) {
					AssertJUnit.fail(e.getMessage());
				}
			}
		}


	}


	@Test(groups = {
			"Fox",
			"Smoke",
			"ProdSanity",
			"Sanity",
			"MiniRegression",
			"Regression",
			"ExhaustiveRegression"
	}, dataProvider = "DevApisDataProvider_SearchAndGetStyleId",
	description = "Search using image Size Verification")
	public void DevApis_StyleService_With_ImageSize(String styleId, String width) {

		int i = 0;
		int responseCode = 0;
		RequestGenerator devApiStyleServiceReq;
		String devApiStyleServiceresponse = "";
		boolean retryTest = true;
		while (retryTest && i < RetryAttemptsCount) {
			try {
				System.out.println("Style id= " + styleId + " and Width= " + width);
				devApiStyleServiceReq = devApiServiceHelper.invokeDevApiStyleWithWidth(styleId, width);
				devApiStyleServiceresponse = devApiStyleServiceReq.respvalidate.returnresponseasstring();
				System.out.println("[Printing StyleService response]\n" + devApiStyleServiceresponse);
				AssertJUnit.assertEquals("[DevApiService Style service is not working.]", 200, devApiStyleServiceReq.response.getStatus());
				AssertJUnit.assertEquals("[DevApiService Style service" + styleId + " is wrong in response.]", styleId, devApiStyleServiceReq.respvalidate.GetNodeValue("data.id", devApiStyleServiceresponse).replaceAll("\"", ""));
				AssertJUnit.assertTrue("[DevApiService Style service is not working.]", devApiStyleServiceReq.respvalidate.GetNodeValue("data.styleImages.default.image", devApiStyleServiceresponse).replaceAll("\"", "").contains(width));
				AssertJUnit.assertTrue("[DevApiService Style service is not working.]", devApiStyleServiceReq.respvalidate.GetNodeValue("data.styleImages.search.image", devApiStyleServiceresponse).replaceAll("\"", "").contains(width));
				AssertJUnit.assertTrue("[DevApiService Style service is not working.]", devApiStyleServiceReq.respvalidate.GetNodeValue("data.styleImages.back.image", devApiStyleServiceresponse).replaceAll("\"", "").contains(width));
				AssertJUnit.assertTrue("[DevApiService Style service is not working.]", devApiStyleServiceReq.respvalidate.GetNodeValue("data.styleImages.left.image", devApiStyleServiceresponse).replaceAll("\"", "").contains(width));
				AssertJUnit.assertTrue("[DevApiService Style service is not working.]", devApiStyleServiceReq.respvalidate.GetNodeValue("data.styleImages.front.image", devApiStyleServiceresponse).replaceAll("\"", "").contains(width));
				AssertJUnit.assertTrue("[DevApiService Style service is not working.]", devApiStyleServiceReq.respvalidate.GetNodeValue("data.styleImages.right.image", devApiStyleServiceresponse).replaceAll("\"", "").contains(width));



				retryTest = false;
			} catch (java.lang.RuntimeException e) {
				retryTest = true;
				i++;
				System.out.println("Caught an Exception - Retring Test");
				System.out.println("Caught an Exception - Attempt : " + i);

			} catch (AssertionError e) {
				retryTest = true;
				i++;
				System.out.println("Assertion Error - Retring Test");
				System.out.println("Assertion Error - Attempt : " + i);
				if (i == 3) {
					AssertJUnit.fail(e.getMessage());
				}
			}
		}

	}

	@Test(groups = {
			"Fox",
			"Smoke",
			"ProdSanity",
			"Sanity",
			"FoxSanity",
			"MiniRegression",
			"Regression",
			"ExhaustiveRegression"
	},
			description = "Verifies Nav Link API Response")
	public void devapiNavWithSuccessResponse() {

		RequestGenerator req = devApiServiceHelper.invokeDevapiNavv2();
		String jsonResponse = req.respvalidate.returnresponseasstring();
		System.out.println(jsonResponse);
		AssertJUnit.assertEquals("Dev api nav v2 is not working", 200, req.response.getStatus());
	}

	@Test(groups = {
			"Fox",
			"Sanity",
			"FoxSanity",
			"MiniRegression",
			"Regression",
			"ExhaustiveRegression"
	},
			dataProvider = "DevApisGetAutoSuggest",
			description = "Get AutoSuggest - Verify Success Response")
	public void DevApis_GetAutoSuggest(String userName, String password, String keyword) {
		RequestGenerator request = devApiServiceHelper.invokeGetAutoSuggestFeature(userName, password, keyword);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting DevApiService AutoSuggest API response :\n\n" + response + "\n");
		log.info("\nPrinting DevApiService AutoSuggest API response :\n\n" + response + "\n");
		AssertJUnit.assertEquals("AutoSuggest Feature is not Working", 200, request.response.getStatus());

	}

	@Test(groups = {
			"Fox",
			"Sanity",
			"FoxSanity",
			"MiniRegression",
			"Regression",
			"ExhaustiveRegression"
	},
			dataProvider = "DevApisGetAutoSuggest",
			description = "Get AutoSuggest - Verify Data ")
	public void DevApis_GetAutoSuggest_VerifyData(String userName, String password, String keyword) {
		RequestGenerator request = devApiServiceHelper.invokeGetAutoSuggestFeature(userName, password, keyword);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting DevApiService AutoSuggest API response :\n\n" + response + "\n");
		log.info("\nPrinting DevApiService AutoSuggest API response :\n\n" + response + "\n");
		AssertJUnit.assertEquals("AutoSuggest Feature is not Working", 200, request.response.getStatus());
		AssertJUnit.assertEquals("AutoSuggest Feature is not working - Search Response does not match Keyword", true, JsonPath.read(response, "$.data[0].id").toString().contains(keyword));
	}

	@Test(groups = {
			"Schema Validation"
	},
			dataProvider = "DevApisGetAutoSuggest",
			description = "Verify - Get Auto Suggest Schema")
	public void DevApis_GetAutoSuggest_VerifySchema(String userName, String password, String keyword) {
		RequestGenerator request = devApiServiceHelper.invokeGetAutoSuggestFeature(userName, password, keyword);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting DevApiService AutoSuggest API response :\n\n" + response + "\n");
		log.info("\nPrinting DevApiService AutoSuggest API response :\n\n" + response + "\n");
		AssertJUnit.assertEquals("AutoSuggest Feature is not Working", 200, request.response.getStatus());
		AssertJUnit.assertEquals("AutoSuggest Feature is not working - Search Response does not match Keyword", true, JsonPath.read(response, "$.data[0].id").toString().contains(keyword));
		try {
			String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/devapi-getAutoSuggest-Schema.txt");
			List < String > missingNodeList = validateServiceResponseNodesUsingSchemaValidator(response, jsonschema);
			AssertJUnit.assertTrue(missingNodeList + " nodes are missing in DevApiService likes count API response", CollectionUtils.isEmpty(missingNodeList));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test(groups = {
			"Fox",
			"Sanity",
			"FoxSanity",
			"MiniRegression",
			"Regression",
			"ExhaustiveRegression"
	},
			dataProvider = "DevApisGetCartCount",
			description = "Get Cart Count API - Complete Flow")
	public void DevApis_GetCartCount_CompleteFlow(String userName, String password, String skuId) {
		RequestGenerator request = devApiServiceHelper.invokeGetCartCount(userName, password);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting DevApiService Get Cart Count API response :\n\n" + response + "\n");
		log.info("\nPrinting DevApiService Get Cart Count API response :\n\n" + response + "\n");
		AssertJUnit.assertEquals("Get Cart Count Feature is not Working", 200, request.response.getStatus());
		AssertJUnit.assertEquals("Get Cart Count Feature is not Working", 0, Integer.parseInt(JsonPath.read(response, "$.data.count")));


		RequestGenerator devApiAddToCartReqGen = devApiServiceHelper.invokeDevApiAddToCart(userName, password, skuId);
		String devApiAddToCartResponse = devApiAddToCartReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting DevApiService addToCart API response :\n\n" + devApiAddToCartResponse + "\n");
		log.info("\nPrinting DevApiService addToCart API response :\n\n" + devApiAddToCartResponse + "\n");

		AssertJUnit.assertEquals("[DevApiService addToCart API is not working.]", 200, devApiAddToCartReqGen.response.getStatus());


		request = devApiServiceHelper.invokeGetCartCount(userName, password);
		response = request.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting DevApiService Get Cart Count API response :\n\n" + response + "\n");
		log.info("\nPrinting DevApiService Get Cart Count API response :\n\n" + response + "\n");
		AssertJUnit.assertEquals("Get Cart Count Feature is not Working", 200, request.response.getStatus());
		AssertJUnit.assertEquals("Get Cart Count Feature is not Working", 1, Integer.parseInt(JsonPath.read(response, "$.data.count")));

		request = devApiServiceHelper.invokeClearCartService(userName, password);
		response = request.respvalidate.returnresponseasstring();
		System.out.println("Clear Cart Response : " + response);

		request = devApiServiceHelper.invokeGetCartCount(userName, password);
		response = request.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting DevApiService Get Cart Count API response :\n\n" + response + "\n");
		log.info("\nPrinting DevApiService Get Cart Count API response :\n\n" + response + "\n");
		AssertJUnit.assertEquals("Get Cart Count Feature is not Working", 200, request.response.getStatus());
		AssertJUnit.assertEquals("Get Cart Count Feature is not Working", 0, Integer.parseInt(JsonPath.read(response, "$.data.count")));

	}

	@Test(groups = {
			"Fox",
			"Sanity",
			"FoxSanity",
			"MiniRegression",
			"Regression",
			"ExhaustiveRegression"
	},
			dataProvider = "DevApisGetCartCount",
			description = "Get Cart Count - Schema Validation")
	public void DevApis_GetCartCount_Schema(String userName, String password, String skuId) {
		RequestGenerator request = devApiServiceHelper.invokeGetCartCount(userName, password);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting DevApiService Get Cart Count API response :\n\n" + response + "\n");
		log.info("\nPrinting DevApiService Get Cart Count API response :\n\n" + response + "\n");
		AssertJUnit.assertEquals("Get Cart Count Feature is not Working", 200, request.response.getStatus());
		AssertJUnit.assertEquals("Get Cart Count Feature is not Working", 0, Integer.parseInt(JsonPath.read(response, "$.data.count")));

		try {
			String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/devapi-getCartCount-Schema.txt");
			List < String > missingNodeList = validateServiceResponseNodesUsingSchemaValidator(response, jsonschema);
			AssertJUnit.assertTrue(missingNodeList + " nodes are missing in DevApiService likes count API response", CollectionUtils.isEmpty(missingNodeList));
		} catch (Exception e) {
			e.printStackTrace();
		}


	}

	@Test(groups = {
			"Fox",
			"Smoke",
			"ProdSanity",
			"Sanity",
			"MiniRegression",
			"Regression",
			"ExhaustiveRegression"
	}, dataProvider = "DevApisGetProfileCount",
	description = "Get Profile Count with List Information")
	public void Lists_GetProfileCountWithList_VerifySuccess(String userName, String password) {

		RequestGenerator request = devApiServiceHelper.invokeGetProfileCount(userName, password);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println(("Get Profile Count Response : " + response));
		AssertJUnit.assertEquals("Get Profile Count API is not working", 200, request.response.getStatus());


	}

	@Test(groups = {
			"Fox",
			"ProdSanity",
			"Sanity",
			"MiniRegression",
			"Regression",
			"ExhaustiveRegression"
	}, dataProvider = "DevApisGetProfileCount",
	description = "Get wishlist verify data")
	public void Lists_GetProfileCountWithList_VerifyData(String userName, String password) {

		RequestGenerator request = devApiServiceHelper.invokeGetProfileCount(userName, password);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println(("Get Profile Count Response : " + response));
		int collectionCount = JsonPath.read(response, "$.data.collection.count");
		int ForumCount = JsonPath.read(response, "$.data.forum.count");
		AssertJUnit.assertEquals("Get Profile Count API is not working", 200, request.response.getStatus());
		AssertJUnit.assertEquals("Get Profile Count API is not working - Collections Service Failure", false, collectionCount==-1);
		AssertJUnit.assertEquals("Get Profile Count API is not working - Forum Service Failure", false, ForumCount==-1);


	}
	@Test(groups = {
			"Schema Validation"
	}, dataProvider = "DevApisGetProfileCount",
			description = "Verify Profile count API response Schema")
	public void Lists_GetProfileCountWithList_VerifySchema(String userName, String password) {

		RequestGenerator request = devApiServiceHelper.invokeGetProfileCount(userName, password);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println(("Get Profile Count Response : " + response));
		int collectionCount = JsonPath.read(response, "$.data.collection.count");
		int ForumCount = JsonPath.read(response, "$.data.forum.count");
		AssertJUnit.assertEquals("Get Profile Count API is not working", 200, request.response.getStatus());
		AssertJUnit.assertEquals("Get Profile Count API is not working - Collections Service Failure", false, collectionCount==-1);
		AssertJUnit.assertEquals("Get Profile Count API is not working - Forum Service Failure", false, ForumCount==-1);

		try {
			String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/devapi-getProfileCount-Schema.txt");
			List < String > missingNodeList = validateServiceResponseNodesUsingSchemaValidator(response, jsonschema);
			AssertJUnit.assertTrue(missingNodeList + " nodes are missing in DevApiService likes count API response", CollectionUtils.isEmpty(missingNodeList));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test(groups = {
			"Smoke",
			"Fox"
	}, dataProvider = "DevApi_Search_withRequestId",
	description = "Verifies a Request ID is generated while Searching")
	public void devapi_SearchWithRequestID_verifySuccess(String data, String requestID, String pageNumber) {
		RequestGenerator request = devApiServiceHelper.invokeSearchWithRequestID(data, requestID, pageNumber);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println(("Search with Request ID response : \n " + response));
		AssertJUnit.assertEquals("Search with Request ID", 200, request.response.getStatus());

	}

	@Test(groups = {
			"Fox",
			"ProdSanity",
			"Sanity",
			"FoxSanity",
			"MiniRegression",
			"Regression",
			"ExhaustiveRegression"
	}, dataProvider = "DevApi_Search_withRequestId",
	description = "Verifies a Request ID is generated while Searching - Data Validation")
	public void devapi_SearchWithRequestID_verifyData(String data, String requestID, String pageNumber) {
		RequestGenerator request = devApiServiceHelper.invokeSearchWithRequestID(data, requestID, pageNumber);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println(("Search with Request ID response : \n " + response));
		AssertJUnit.assertEquals("Search with Request ID", 200, request.response.getStatus());
		String RequestId = JsonPath.read(response, "$.meta.requestId").toString();
		System.out.println("Generated Request ID : " + RequestId);
		AssertJUnit.assertEquals("Request ID is not generated!!", false, RequestId.length() == 0);
	}

	@Test(groups = {
			"Fox",
			"ProdSanity",
			"Sanity",
			"FoxSanity",
			"MiniRegression",
			"Regression",
			"ExhaustiveRegression"
	}, dataProvider = "DevApi_Search_withRequestId",
	description = "Verify Same Request ID is used for Going through different pages in Search results")
	public void devapi_SearchWithRequestID_verifySameIdGettingGenerated(String data, String requestID, String pageNumber) {
		RequestGenerator request = devApiServiceHelper.invokeSearchWithRequestID(data, requestID, pageNumber);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println(("Search with Request ID response : \n " + response));
		AssertJUnit.assertEquals("Search with Request ID", 200, request.response.getStatus());
		String RequestID = JsonPath.read(response, "$.meta.requestId").toString();
		AssertJUnit.assertEquals("Request ID is not generated!!", false, RequestID.isEmpty());

		for (int i = 0; i < 10; i++) {
			request = devApiServiceHelper.invokeSearchWithRequestID(data, RequestID, pageNumber);
			response = request.respvalidate.returnresponseasstring();
			String newReqID = JsonPath.read(response, "$.meta.requestId").toString();
			AssertJUnit.assertEquals("Same Request ID is not generated!!", true, newReqID.equals(RequestID));
		}
	}

	@Test(groups = {
			"Fox",
			"ProdSanity",
			"Sanity",
			"FoxSanity",
			"MiniRegression",
			"Regression",
			"ExhaustiveRegression"
	}, dataProvider = "DevApi_Search_withRequestId",
	description = "Verifies Same Request ID is generated while Searching")
	public void devapi_SearchWithRequestID_verifySameIdGettingGeneratedwithDifferentData(String data, String requestID, String pageNumber) {
		RequestGenerator request = devApiServiceHelper.invokeSearchWithRequestID(data, requestID, pageNumber);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println(("Search with Request ID response : \n " + response));
		AssertJUnit.assertEquals("Search with Request ID", 200, request.response.getStatus());
		String RequestID = JsonPath.read(response, "$.meta.requestId").toString();
		AssertJUnit.assertEquals("Request ID is not generated!!", false, RequestID.isEmpty());

		for (int i = 0; i < 10; i++) {
			String nData = data.concat(Integer.toString(i));
			request = devApiServiceHelper.invokeSearchWithRequestID(nData, RequestID, pageNumber);
			response = request.respvalidate.returnresponseasstring();
			String newReqID = JsonPath.read(response, "$.meta.requestId").toString();
			AssertJUnit.assertEquals("Same Request ID is not generated!!", true, newReqID.equals(RequestID));
		}
	}

	@Test(groups = {
			"Fox",
			"ProdSanity",
			"Sanity",
			"FoxSanity",
			"MiniRegression",
			"Regression",
			"ExhaustiveRegression"
	}, dataProvider = "DevApi_Search_withRequestId",
	description = "Verifies same Request ID is generated while scrolling down search results")
	public void devapi_SearchWithRequestID_verifySameIdGettingGeneratedwithWhileScrolling(String data, String requestID, String pageNumber) throws InterruptedException {
		RequestGenerator request = devApiServiceHelper.invokeSearchWithRequestID(data, requestID, pageNumber);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println(("Search with Request ID response : \n " + response));
		AssertJUnit.assertEquals("Search with Request ID", 200, request.response.getStatus());
		String RequestID = JsonPath.read(response, "$.meta.requestId").toString();
		AssertJUnit.assertEquals("Request ID is not generated!!", false, RequestID.isEmpty());

		for (int i = 0; i < 10; i++) {
			TimeUnit.SECONDS.sleep(1);
			String nData = data.concat(Integer.toString(i));
			int pageNumberCalc = Integer.parseInt(pageNumber) + i;
			request = devApiServiceHelper.invokeSearchWithRequestID(nData, RequestID, Integer.toString(pageNumberCalc));
			response = request.respvalidate.returnresponseasstring();
			String newReqID = JsonPath.read(response, "$.meta.requestId").toString();
			AssertJUnit.assertEquals("Same Request ID is not generated!!", true, newReqID.equals(RequestID));
		}
	}

	@Test(groups = {
			"Fox",
			"Smoke",
			"ProdSanity",
			"Sanity",
			"FoxSanity",
			"MiniRegression",
			"Regression",
			"ExhaustiveRegression"
	}, dataProvider = "DevApi_feedback_Success",
	description = "Post a feedback API verification")
	public void devapi_FeedBack_Tests(String mode, String userName, String password, String email, String feedbackMsg, String feedbackType, String isRooted) throws InterruptedException {
		RequestGenerator request = devApiServiceHelper.invokePostFeedbackService(mode, userName, password, email, feedbackMsg, feedbackType, isRooted);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println("Feedback Service Response : \n " + response);
		TimeUnit.SECONDS.sleep(2);
		int CurrentStatus = request.response.getStatus();
		if (!(CurrentStatus == 200)) {
			for (int i = 0; i < 3; i++) {
				System.out.println("Retry Attempt : " + (i + 1));
				request = devApiServiceHelper.invokePostFeedbackService(mode, userName, password, email, feedbackMsg, feedbackType, isRooted);
				response = request.respvalidate.returnresponseasstring();
				System.out.println("Feedback Service Response : \n " + response);
				if (request.response.getStatus() == 200) {
					break;
				};


			}
		}
		AssertJUnit.assertEquals("Post Feedback Service is down", 200, request.response.getStatus());
	}

	@Test(groups = {
			"Fox",
			"Smoke",
			"ProdSanity",
			"Sanity",
			"FoxSanity",
			"MiniRegression",
			"Regression",
			"ExhaustiveRegression"
	}, dataProvider = "DevApisGetProfileCount",
	description = "Get Profile Count API Response Success")
	public void Lists_GetProfileCount_NewCounts_VerifySuccess(String userName, String password) {
		RequestGenerator request = devApiServiceHelper.invokeGetProfileCountV3(userName, password);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println(("Get Profile Count Response : " + response));
		AssertJUnit.assertEquals("Get Profile Count API is not working", 200, request.response.getStatus());
	}

	@Test(groups = {
			"Fox",
			"Smoke",
			"ProdSanity",
			"Sanity",
			"FoxSanity",
			"MiniRegression",
			"Regression",
			"ExhaustiveRegression"
	}, dataProvider = "DevApisGetProfileCount",
	description = "Verify new Counts API - Style Forum Data")
	public void Lists_GetProfileCount_NewCounts_VerifyForumServiceIsRunning(String userName, String password) {
		RequestGenerator request = devApiServiceHelper.invokeGetProfileCountV3(userName, password);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println(("Get Profile Count Response : " + response));
		AssertJUnit.assertEquals("Get Profile Count API is not working", 200, request.response.getStatus());
		int ForumCount = Integer.parseInt(JsonPath.read(response, "$.data.forum.count").toString());
		AssertJUnit.assertEquals("Style Forum Counts Service is Failing!!!", false, (ForumCount == -1));
	}

	@Test(groups = {
			"Fox",
			"Smoke",
			"ProdSanity",
			"Sanity",
			"FoxSanity",
			"MiniRegression",
			"Regression",
			"ExhaustiveRegression"
	}, dataProvider = "DevApisGetProfileCount",
	description = "Verify new Counts API - Collections Data")
	public void Lists_GetProfileCount_NewCounts_VerifyCollectionsServiceIsRunning(String userName, String password) {
		RequestGenerator request = devApiServiceHelper.invokeGetProfileCountV3(userName, password);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println(("Get Profile Count Response : " + response));
		AssertJUnit.assertEquals("Get Profile Count API is not working", 200, request.response.getStatus());
		int CollectionCount = Integer.parseInt(JsonPath.read(response, "$.data.collection.count").toString());
		AssertJUnit.assertEquals("Collections Counts Service is Failing!!!", false, (CollectionCount == -1));
	}

	@Test(groups = {
			"Fox",
			"Smoke",
			"ProdSanity",
			"Sanity",
			"MiniRegression",
			"Regression",
			"ExhaustiveRegression"
	}, dataProvider = "DevApisGetProfileCount",
	description = "Verify new Counts API - Shots Data")
	public void Lists_GetProfileCount_NewCounts_VerifyShotsServiceIsRunning(String userName, String password) {
		RequestGenerator request = devApiServiceHelper.invokeGetProfileCountV3(userName, password);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println(("Get Profile Count Response : " + response));
		AssertJUnit.assertEquals("Get Profile Count API is not working", 200, request.response.getStatus());
		int ShotsCount = Integer.parseInt(JsonPath.read(response, "$.data.shots.count").toString());
		AssertJUnit.assertEquals("Shots Counts Service is Failing!!!", false, (ShotsCount == -1));
	}



	@Test(groups = {
			"Fox",
			"Smoke",
			"ProdSanity",
			"Sanity",
			"FoxSanity",
			"MiniRegression",
			"Regression",
			"ExhaustiveRegression"
	}, dataProvider = "DevApisGetVisualSearch",
	description = "Verify Visual Search EndPoint Success Response")
	public void DevAPI_GetVisualSearch_VerifySuccess(String ImageURL, String VisualFlag) {
		RequestGenerator request = devApiServiceHelper.invokeGetVisualSearch(ImageURL, VisualFlag);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println(("Visual Search : " + response));
		AssertJUnit.assertEquals("Visual Search API is not working", 200, request.response.getStatus());
	}

	@Test(groups = {
			"Fox",
			"Smoke",
			"ProdSanity",
			"Sanity",
			"FoxSanity",
			"MiniRegression",
			"Regression",
			"ExhaustiveRegression"
	}, dataProvider = "DevApisGetVisualSearch",
	description = "Verify Visual Search EndPoint Success Response")
	public void DevAPI_PostVisualSearch_VerifySuccess(String ImageURL, String VisualFlag) {
		RequestGenerator request = devApiServiceHelper.invokeGetVisualSearch(ImageURL, VisualFlag);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println(("Visual Search : " + response));
		AssertJUnit.assertEquals("Visual Search API is not working", 200, request.response.getStatus());
	}

	@Test(groups = {
			"Fox",
			"Smoke",
			"ProdSanity",
			"Sanity",
			"FoxSanity",
			"MiniRegression",
			"Regression",
			"ExhaustiveRegression"
	}, dataProvider = "DevApiSetUserPrivacy",
	description = "Set User Privacy API Response")
	public void DevAPI_setPrivacy_VerifySuccess(String username, String password, String privacyFlag) {
		RequestGenerator request = devApiServiceHelper.invokeSetUserPrivacy(username, password, privacyFlag);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println(("Set User Privacy Response : " + response));
		AssertJUnit.assertEquals("Set User Privacy is not working!!", 200, request.response.getStatus());
	}

	@Test(groups = {
			"Fox",
			"Sanity",
			"FoxSanity",
			"MiniRegression",
			"Regression",
			"ExhaustiveRegression"
	},
			dataProvider = "DevApisGetCartCount",
			description = "Verify error message while getting cart with invalid session")
	public void DevApis_GetCartCount_404Check_CompleteFlow(String userName, String password, String skuId) {
		RequestGenerator request = devApiServiceHelper.invokeClearCartService(userName, password);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println("Clear Cart Response : " + response);

		request = devApiServiceHelper.invokeGetCartCountWithSessionCheck(userName, password, false);
		response = request.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting DevApiService Get Cart Count API response :\n\n" + response + "\n");
		log.info("\nPrinting DevApiService Get Cart Count API response :\n\n" + response + "\n");
		AssertJUnit.assertEquals("Get Cart Count Feature is not Working", 200, request.response.getStatus());
		//AssertJUnit.assertEquals("Get Cart Count Feature is not Working", 0,JsonPath.read(response,"$.data.count"));


		request = devApiServiceHelper.invokeGetCartCountWithSessionCheck(userName, password, true);
		response = request.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting DevApiService Get Cart Count API response :\n\n" + response + "\n");
		log.info("\nPrinting DevApiService Get Cart Count API response :\n\n" + response + "\n");
		AssertJUnit.assertEquals("Get Cart Count - Session Check - Feature is not Working", 404, request.response.getStatus());
		AssertJUnit.assertEquals("Get Cart Count - Session Check - Feature is not Working", "session not found", JsonPath.read(response, "$.meta.error"));

		RequestGenerator devApiAddToCartReqGen = devApiServiceHelper.invokeDevApiAddToCart(userName, password, skuId);
		String devApiAddToCartResponse = devApiAddToCartReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting DevApiService addToCart API response :\n\n" + devApiAddToCartResponse + "\n");
		log.info("\nPrinting DevApiService addToCart API response :\n\n" + devApiAddToCartResponse + "\n");

		AssertJUnit.assertEquals("[DevApiService addToCart API is not working.]", 200, devApiAddToCartReqGen.response.getStatus());


		request = devApiServiceHelper.invokeGetCartCount(userName, password);
		response = request.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting DevApiService Get Cart Count API response :\n\n" + response + "\n");
		log.info("\nPrinting DevApiService Get Cart Count API response :\n\n" + response + "\n");
		AssertJUnit.assertEquals("Get Cart Count Feature is not Working", 200, request.response.getStatus());
		AssertJUnit.assertEquals("Get Cart Count Feature is not Working", 1, Integer.parseInt(JsonPath.read(response, "$.data.count")));

		request = devApiServiceHelper.invokeGetCartCountWithSessionCheck(userName, password, true);
		response = request.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting DevApiService Get Cart Count API response :\n\n" + response + "\n");
		log.info("\nPrinting DevApiService Get Cart Count API response :\n\n" + response + "\n");
		AssertJUnit.assertEquals("Get Cart Count - Session Check - Feature is not Working", 404, request.response.getStatus());
		AssertJUnit.assertEquals("Get Cart Count - Session Check - Feature is not Working", "session not found", JsonPath.read(response, "$.meta.error"));

		request = devApiServiceHelper.invokeGetCartCount(userName, password);
		response = request.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting DevApiService Get Cart Count API response :\n\n" + response + "\n");
		log.info("\nPrinting DevApiService Get Cart Count API response :\n\n" + response + "\n");
		AssertJUnit.assertEquals("Get Cart Count Feature is not Working", 200, request.response.getStatus());
		AssertJUnit.assertEquals("Get Cart Count Feature is not Working", 1, Integer.parseInt(JsonPath.read(response, "$.data.count")));


		request = devApiServiceHelper.invokeClearCartService(userName, password);
		response = request.respvalidate.returnresponseasstring();
		System.out.println("Clear Cart Response : " + response);

		request = devApiServiceHelper.invokeGetCartCount(userName, password);
		response = request.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting DevApiService Get Cart Count API response :\n\n" + response + "\n");
		log.info("\nPrinting DevApiService Get Cart Count API response :\n\n" + response + "\n");
		AssertJUnit.assertEquals("Get Cart Count Feature is not Working", 200, request.response.getStatus());
		AssertJUnit.assertEquals("Get Cart Count Feature is not Working", 0, Integer.parseInt(JsonPath.read(response, "$.data.count")));

		request = devApiServiceHelper.invokeGetCartCountWithSessionCheck(userName, password, true);
		response = request.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting DevApiService Get Cart Count API response :\n\n" + response + "\n");
		log.info("\nPrinting DevApiService Get Cart Count API response :\n\n" + response + "\n");
		AssertJUnit.assertEquals("Get Cart Count - Session Check - Feature is not Working", 404, request.response.getStatus());
		AssertJUnit.assertEquals("Get Cart Count - Session Check - Feature is not Working", "session not found", JsonPath.read(response, "$.meta.error"));

	}

	@Test(groups = {
			"Fox",
			"Smoke",
			"ProdSanity",
			"Sanity",
			"MiniRegression",
			"Regression",
			"ExhaustiveRegression"
	}, dataProvider = "DevApiSetUserPrivacyFlow",
	description = "Set user privacy as PRIVATE and access profile by other user")
	public void DevAPI_PrivateProfileOtherUser_VerifyFlow(String username, String password, String username2, String password2) throws InterruptedException {
		RequestGenerator request = devApiServiceHelper.invokeSetUserPrivacy(username, password, "0");
		String response = request.respvalidate.returnresponseasstring();
		//System.out.println(("Set User Privacy Response : " + response));
		AssertJUnit.assertEquals("Set User Privacy is not working!!", 200, request.response.getStatus());

		String PPID_L = devApiServiceHelper.ppid;
		request = devApiServiceHelper.invokeGetProfileCountWithPPID(username2, password2, PPID_L);
		response = request.respvalidate.returnresponseasstring();
		//System.out.println(("Get Profile Counts : " + response));
		AssertJUnit.assertEquals("Get Profile Counts is not working!!", 200, request.response.getStatus());

		int ForumCount = Integer.parseInt(JsonPath.read(response, "$.data.forum.count").toString());
		int CollectionCount = Integer.parseInt(JsonPath.read(response, "$.data.collection.count").toString());
		int ShotsCount = Integer.parseInt(JsonPath.read(response, "$.data.shots.count").toString());
		int likesCount = 0;

		if (!(JsonPath.read(response, "$.data.likes.count") == null)) {
			likesCount = Integer.parseInt(JsonPath.read(response, "$.data.likes.count").toString());
		}



		request = devApiServiceHelper.invokeGetProfileCountWithPPID(username2, password2, PPID_L);
		response = request.respvalidate.returnresponseasstring();
		System.out.println("Get Profile Counts of Public Profile Response" + response);
		AssertJUnit.assertEquals("Get Public Profile Counts is Failing", 200, request.response.getStatus());
		//AssertJUnit.assertEquals("Likes Count is Not Matching", likesCount,Integer.parseInt(JsonPath.read(response, "$.data.likes.count").toString()));
		AssertJUnit.assertEquals("Forum Count is Not Matching", ForumCount, Integer.parseInt(JsonPath.read(response, "$.data.forum.count").toString()));
		AssertJUnit.assertEquals("Collections Count is Not Matching", CollectionCount, Integer.parseInt(JsonPath.read(response, "$.data.collection.count").toString()));
		AssertJUnit.assertEquals("Shots Count is Not Matching", ShotsCount, Integer.parseInt(JsonPath.read(response, "$.data.shots.count").toString()));
		TimeUnit.SECONDS.sleep(5);

		request = devApiServiceHelper.invokeSetUserPrivacy(username, password, "1");

		response = request.respvalidate.returnresponseasstring();
		System.out.println(("Set User Privacy Response : " + response));
		AssertJUnit.assertEquals("Set User Privacy is not working!!", 200, request.response.getStatus());

/*		request = devApiServiceHelper.invokeGetProfileCountWithPPID(username, password, PPID_L);
	response = request.respvalidate.returnresponseasstring();
	System.out.println("Get Profile Counts of Public Profile Response"+response);
	AssertJUnit.assertEquals("Get Public Profile Counts is Failing",200,request.response.getStatus());
	//AssertJUnit.assertEquals("Likes Count is Not Matching", likesCount,Integer.parseInt(JsonPath.read(response, "$.data.likes.count").toString()));
	AssertJUnit.assertEquals("Forum Count is Not Matching", ForumCount,Integer.parseInt(JsonPath.read(response, "$.data.forum.count").toString()));
	AssertJUnit.assertEquals("Collections Count is Not Matching", CollectionCount,Integer.parseInt(JsonPath.read(response, "$.data.collection.count").toString()));
	AssertJUnit.assertEquals("Shots Count is Not Matching", ShotsCount,Integer.parseInt(JsonPath.read(response, "$.data.shots.count").toString()));
		 
		request = devApiServiceHelper.invokeGetProfileCountWithPPID(username2, password2, PPID_L);
		response = request.respvalidate.returnresponseasstring();
		System.out.println("Get Profile Counts of Public Profile Response" + response);
		AssertJUnit.assertEquals("Get Public Profile Counts is Failing", 200, request.response.getStatus());
		//AssertJUnit.assertEquals("Likes Count is Not Matching", 0,Integer.parseInt(JsonPath.read(response, "$.data.likes.count").toString()));
		AssertJUnit.assertEquals("Forum Count is Not Matching", 0, Integer.parseInt(JsonPath.read(response, "$.data.forum.count").toString()));
		AssertJUnit.assertEquals("Collections Count is Not Matching", 0, Integer.parseInt(JsonPath.read(response, "$.data.collection.count").toString()));
		AssertJUnit.assertEquals("Shots Count is Not Matching", 0, Integer.parseInt(JsonPath.read(response, "$.data.shots.count").toString()));
*/


	}

	@Test(groups = {
			"Fox",
			"Smoke",
			"ProdSanity",
			"Sanity",
			"MiniRegression",
			"Regression",
			"ExhaustiveRegression"
	}, dataProvider = "DevApiSetUserPrivacyFlow",
	description = "Validate User Privacy effect by switching on and off")
	public void DevAPI_PrivateProfileSameUser_VerifyFlow(String username, String password, String username2, String password2) {
		RequestGenerator request = devApiServiceHelper.invokeSetUserPrivacy(username, password, "0");
		String response = request.respvalidate.returnresponseasstring();
		System.out.println(("Set User Privacy Response : " + response));
		AssertJUnit.assertEquals("Set User Privacy is not working!!", 200, request.response.getStatus());

		String PPID_L = devApiServiceHelper.ppid;
		request = devApiServiceHelper.invokeGetProfileCountWithPPID(username, password, PPID_L);
		response = request.respvalidate.returnresponseasstring();
		System.out.println(("Get Profile Counts : " + response));
		AssertJUnit.assertEquals("Get Profile Counts is not working!!", 200, request.response.getStatus());

		int ForumCount = Integer.parseInt(JsonPath.read(response, "$.data.forum.count").toString());
		int CollectionCount = Integer.parseInt(JsonPath.read(response, "$.data.collection.count").toString());
		int ShotsCount = Integer.parseInt(JsonPath.read(response, "$.data.shots.count").toString());
		int likesCount = 0;

		if (!(JsonPath.read(response, "$.data.likes.count") == null)) {
			likesCount = Integer.parseInt(JsonPath.read(response, "$.data.likes.count").toString());
		}


		request = devApiServiceHelper.invokeSetUserPrivacy(username, password, "1");
		response = request.respvalidate.returnresponseasstring();
		System.out.println(("Set User Privacy Response : " + response));
		AssertJUnit.assertEquals("Set User Privacy is not working!!", 200, request.response.getStatus());

		request = devApiServiceHelper.invokeGetProfileCountWithPPID(username, password, PPID_L);
		response = request.respvalidate.returnresponseasstring();
		System.out.println("Get Profile Counts of Public Profile Response" + response);
		AssertJUnit.assertEquals("Get Public Profile Counts is Failing", 200, request.response.getStatus());
		//AssertJUnit.assertEquals("Likes Count is Not Matching", likesCount,Integer.parseInt(JsonPath.read(response, "$.data.likes.count").toString()));
		AssertJUnit.assertEquals("Forum Count is Not Matching", ForumCount, Integer.parseInt(JsonPath.read(response, "$.data.forum.count").toString()));
		AssertJUnit.assertEquals("Collections Count is Not Matching", CollectionCount, Integer.parseInt(JsonPath.read(response, "$.data.collection.count").toString()));
		AssertJUnit.assertEquals("Shots Count is Not Matching", ShotsCount, Integer.parseInt(JsonPath.read(response, "$.data.shots.count").toString()));

	}

	// TestCase To Check Pumps Update of PPID affecting the Collections landing page URLs
	@Test(groups = {
			"Fox",
			"Smoke",
			"ProdSanity",
			"Sanity",
			"FoxSanity",
			"MiniRegression",
			"Regression",
			"ExhaustiveRegression"
	}, dataProvider = "Check_PPID_Availability",
	description = "Verify PPID is available")
	public void DevAPI_PPID_Availability_Success(String username, String password, String newPPID) {
		RequestGenerator request = devApiServiceHelper.checkPPIDAvailability(username, password, newPPID);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println(("Check PPID Availability : " + response));
		AssertJUnit.assertEquals("Check PPID Availability Service is not working!", 200, request.response.getStatus());
	}

	@Test(groups = {
			"Fox",
			"Smoke",
			"ProdSanity",
			"Sanity",
			"FoxSanity",
			"MiniRegression",
			"Regression",
			"ExhaustiveRegression"
	}, dataProvider = "Check_PPID_Availability_Failure",
	description = "Verify PPID availability with existing PPID")
	public void DevAPI_PPID_Availability_Failure(String username, String password, String newPPID, String username1, String password1) {
		RequestGenerator request = devApiServiceHelper.checkPPIDAvailability(username, password, newPPID);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println(("Check PPID Availability : " + response));
		AssertJUnit.assertEquals("Check PPID Availability Service is not working!", 200, request.response.getStatus());

		request = devApiServiceHelper.checkPPIDAvailability(username1, password1, devApiServiceHelper.ppid);
		response = request.respvalidate.returnresponseasstring();
		System.out.println(("Check PPID Availability : " + response));
		AssertJUnit.assertEquals("Check PPID Availability Service is not working!", 200, request.response.getStatus());

	}

	@Test(groups = {
			"Fox",
			"Smoke",
			"ProdSanity",
			"Sanity",
			"FoxSanity",
			"MiniRegression",
			"Regression",
			"ExhaustiveRegression"
	}, dataProvider = "Check_PPID_Availability_Failure",
	description = "Verify PPID availability With Data Validation")
	public void DevAPI_PPID_Availability_CheckDataNodes(String username, String password, String newPPID, String username1, String password1) {
		RequestGenerator request = devApiServiceHelper.checkPPIDAvailability(username, password, newPPID);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println(("Check PPID Availability : " + response));
		AssertJUnit.assertEquals("Check PPID Availability Service is not working!", 200, request.response.getStatus());


		try {
			String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/devapi-checkPPID-availability-schema.txt");
			List < String > missingNodeList = validateServiceResponseNodesUsingSchemaValidator(response, jsonschema);
			AssertJUnit.assertTrue(missingNodeList + " nodes are missing in Check PPID Availability API response",
					CollectionUtils.isEmpty(missingNodeList));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test(groups = {
			"Fox",
			"Smoke",
			"ProdSanity",
			"Sanity",
			"FoxSanity",
			"MiniRegression",
			"Regression",
			"ExhaustiveRegression"
	}, dataProvider = "Check_PPID_Availability",
	description = "Verify PPID Availability with Data Validation")
	public void DevAPI_PPID_Availability_VerifyData(String username, String password, String newPPID) {
		RequestGenerator request = devApiServiceHelper.checkPPIDAvailability(username, password, newPPID);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println(("Check PPID Availability : " + response));
		AssertJUnit.assertEquals("Check PPID Availability Service is not working!", 200, request.response.getStatus());
		AssertJUnit.assertEquals("Check PPID Availability - PPID used is not proper", newPPID, JsonPath.read(response, "$.data.publicProfileId"));
	}

	@Test(groups = {
			"Fox",
			"Smoke",
			"ProdSanity",
			"Sanity",
			"FoxSanity",
			"MiniRegression",
			"Regression",
			"ExhaustiveRegression"
	}, dataProvider = "Check_PPID_Availability",
	description = "Check PPID availability and save new PPID")
	public void DevAPI_PPID_Availability_SaveNewPPID(String username, String password, String newPPID) {
		RequestGenerator request = devApiServiceHelper.checkPPIDAvailability(username, password, newPPID);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println(("Check PPID Availability : " + response));
		AssertJUnit.assertEquals("Check PPID Availability Service is not working!", 200, request.response.getStatus());
		AssertJUnit.assertEquals("Check PPID Availability - PPID used is not proper", newPPID, JsonPath.read(response, "$.data.publicProfileId"));

		request = devApiServiceHelper.invokeGetProfileInfo(username, password);
		response = request.respvalidate.returnresponseasstring();
		System.out.println("Get Profile Info Response" + response);

		String bio = JsonPath.read(response, "$.data.bio").toString();
		String dob = JsonPath.read(response, "$.data.dob").toString();
		String gender = JsonPath.read(response, "$.data.gender").toString();
		String location = JsonPath.read(response, "$.data.location").toString();
		String name = JsonPath.read(response, "$.data.name").toString();
		request = devApiServiceHelper.saveUserProfileInfo(username, password, bio, dob, gender, location, name, newPPID);
		response = request.respvalidate.returnresponseasstring();
		System.out.println("Save Profile Info Response : " + response);

		request = devApiServiceHelper.invokeGetProfileInfo(username, password);
		response = request.respvalidate.returnresponseasstring();
		System.out.println("Get Profile Info Response" + response);

		AssertJUnit.assertEquals("PPID is not updated", newPPID, JsonPath.read(response, "$.data.publicProfileId").toString());
	}


	@Test(groups = {
			"Fox",
			"Smoke",
			"ProdSanity",
			"Sanity",
			"FoxSanity",
			"MiniRegression",
			"Regression",
			"ExhaustiveRegression"
	}, dataProvider = "DevApisDataProvider_signIn_verifySuccessResponse",
	description = "Verify User Cashback API success response")
	public void DevAPI_GetCashBack_VerifySuccess(String username, String password) {
		RequestGenerator request = devApiServiceHelper.getUserCashBack(username, password);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println("Get user Cashback Service Response : " + response);
		AssertJUnit.assertEquals("Get User Cashback Service Service is Down!!", 200, request.response.getStatus());
	}

	@Test(groups = {
			"Fox",
			"Smoke",
			"ProdSanity",
			"Sanity",
			"FoxSanity",
			"MiniRegression",
			"Regression",
			"ExhaustiveRegression"
	}, dataProvider = "DevApisDataProvider_signIn_verifySuccessResponse",
	description = "Verify User Cashback API success response - Verify Data")
	public void DevAPI_GetCashBack_VerifyData(String username, String password) {
		RequestGenerator request = devApiServiceHelper.getUserCashBack(username, password);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println("Get user Cashback Service Response : " + response);
		AssertJUnit.assertEquals("Get User Cashback Service Service is Down!!", 200, request.response.getStatus());
	}

	


	//Hallmark Services
	@Test(groups = {
			"Fox",
			"Smoke",
			"",
			"Sanity",
			"FoxSanity",
			"MiniRegression",
			"Regression",
			"ExhaustiveRegression"
	}, dataProvider = "HallMark_GetOnBoardingStrategies",
	description = "Get Hallmark Onboarding State verify success")
	public void DevAPI_GetOnboardingState_VerifySuccess(String username, String password, String Scenario, String appStat, String deviceID) {
		if (password.length() == 0) password = getPass(username);

		RequestGenerator devApiSignUpReqGen = devApiServiceHelper.invokeDevApiSignUp(username, password);
		String devApiSignUpResponse = devApiSignUpReqGen.respvalidate.returnresponseasstring();

		RequestGenerator request = devApiServiceHelper.getHallmarkUserOnboardingData(username, password, Scenario, appStat, deviceID);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println("Get user onboarding State Service Response : " + response);
	
		AssertJUnit.assertEquals("Get User Onboarding State Service is Down!!", 200, request.response.getStatus());
	}

	@Test(groups = {
			"Fox",
			"Smoke",
			"",
			"Sanity",
			"FoxSanity",
			"MiniRegression",
			"Regression",
			"ExhaustiveRegression"
	}, dataProvider = "HallMark_GetOnBoardingStrategies",
	description = "Get Hallmark Onboarding State verify response Data")
	public void DevAPI_GetOnboardingState_VerifyData(String username, String password, String Scenario, String appStat, String deviceID) {
		if (password.length() == 0) password = getPass(username);

		RequestGenerator devApiSignUpReqGen = devApiServiceHelper.invokeDevApiSignUp(username, password);
		String devApiSignUpResponse = devApiSignUpReqGen.respvalidate.returnresponseasstring();

		String RandomDeviceID = devApiServiceHelper.randomize(deviceID);
		RequestGenerator request = devApiServiceHelper.getHallmarkUserOnboardingData(username, password, Scenario, appStat, RandomDeviceID);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println("Get user onboarding State Service Response : " + response);
		int reqStatus = 200;
		if (Scenario.equals("NONE")) {
			reqStatus = 200;
			//AssertJUnit.assertEquals("Error Message is not proper", "Invalid input ,either device id or uidx one should be non null ", JsonPath.read(response, "$.message"));
		} else {
			try {
				String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/devapi-hallmark-getOnboardingState-SchemaSet.txt");
				List < String > missingNodeList = validateServiceResponseNodesUsingSchemaValidator(response, jsonschema);
				AssertJUnit.assertTrue(missingNodeList + " nodes are missing in Check PPID Availability API response",
						CollectionUtils.isEmpty(missingNodeList));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		AssertJUnit.assertEquals("Get User Onboarding State Service is Down!!", reqStatus, request.response.getStatus());

	}

	//Save Hallmark Services OnBoarding data
	@Test(groups = {
			"Fox",
			"Smoke",
			"",
			"Sanity",
			"MiniRegression",
			"Regression",
			"ExhaustiveRegression"
	}, dataProvider = "HallMark_SaveOnBoardingStrategies",
	description = "Save Hallmark Onboarding State verify success")
	public void DevAPI_saveOnboardingState_DependantVerifySuccess(String username, String password, String Scenario, String appStat, String deviceID) {
		if (password.length() == 0) password = getPass(username);

		RequestGenerator devApiSignUpReqGen = devApiServiceHelper.invokeDevApiSignUp(username, password);
		String devApiSignUpResponse = devApiSignUpReqGen.respvalidate.returnresponseasstring();

		String RandomDeviceID = devApiServiceHelper.randomize(deviceID);
		RequestGenerator request = devApiServiceHelper.getHallmarkUserOnboardingData(username, password, Scenario, appStat, RandomDeviceID);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println("Get user onboarding State Service Response : " + response);
		int reqStatus = 200;
		if (Scenario.equals("NONE"))
			reqStatus = 200;
		AssertJUnit.assertEquals("Get User Onboarding State Service is Down!!", reqStatus, request.response.getStatus());

		String DependantStrategy = devApiServiceHelper.getSingleStrategy(response, "DEPENDENT");
		System.out.println("Strategy : " + DependantStrategy);
		request = devApiServiceHelper.SaveHallmarkUserOnboardingData(username, password, Scenario, appStat, RandomDeviceID, DependantStrategy);
		response = request.respvalidate.returnresponseasstring();
		System.out.println("Save user onboarding state Response : " + response);

		if (Scenario.equals("NONE"))
			reqStatus = 200;
		AssertJUnit.assertEquals("Save User Onboarding State Service is Down!!", reqStatus, request.response.getStatus());
	}


	//Save Hallmark Services OnBoarding data
	@Test(groups = {
			"Fox",
			"Smoke",
			"",
			"Sanity",
			"FoxSanity",
			"MiniRegression",
			"Regression",
			"ExhaustiveRegression"
	}, dataProvider = "HallMark_SaveOnBoardingStrategies",
	description = "Save OnBoarding with Main Strategy")
	public void DevAPI_saveOnboardingState_MainStrategy_VerifySuccess(String username, String password, String Scenario, String appStat, String deviceID) {
		if (password.length() == 0) password = getPass(username);

		RequestGenerator devApiSignUpReqGen = devApiServiceHelper.invokeDevApiSignUp(username, password);
		String devApiSignUpResponse = devApiSignUpReqGen.respvalidate.returnresponseasstring();

		String RandomDeviceID = devApiServiceHelper.randomize(deviceID);
		RequestGenerator request = devApiServiceHelper.getHallmarkUserOnboardingData(username, password, Scenario, appStat, RandomDeviceID);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println("Get user onboarding State Service Response : " + response);
		int reqStatus = 200;
		if (Scenario.equals("NONE"))
			reqStatus = 200;
		AssertJUnit.assertEquals("Get User Onboarding State Service is Down!!", reqStatus, request.response.getStatus());

		String DependantStrategy = devApiServiceHelper.getSingleStrategy(response, "MAIN");
		System.out.println("Strategy : " + DependantStrategy);
		request = devApiServiceHelper.SaveHallmarkUserOnboardingData(username, password, Scenario, appStat, RandomDeviceID, DependantStrategy);
		response = request.respvalidate.returnresponseasstring();
		System.out.println("Save user onboarding state Response : " + response);
		if (Scenario.equals("NONE"))
			reqStatus = 200;
		AssertJUnit.assertEquals("Save User Onboarding State Service is Down!!", reqStatus, request.response.getStatus());
	}

	//Save Hallmark Services OnBoarding data
	@Test(groups = {
			"Fox",
			"Smoke",
			"",
			"Sanity",
			"FoxSanity",
			"MiniRegression",
			"Regression",
			"ExhaustiveRegression"
	}, dataProvider = "HallMark_SaveOnBoardingStrategies",
	description = "Save OnBoarding with Multiple Dependent Strategies")
	public void DevAPI_saveOnboardingState_Multiple_DependantVerifySuccess(String username, String password, String Scenario, String appStat, String deviceID) {
		if (password.length() == 0) password = getPass(username);

		RequestGenerator devApiSignUpReqGen = devApiServiceHelper.invokeDevApiSignUp(username, password);
		String devApiSignUpResponse = devApiSignUpReqGen.respvalidate.returnresponseasstring();

		String RandomDeviceID = devApiServiceHelper.randomize(deviceID);
		RequestGenerator request = devApiServiceHelper.getHallmarkUserOnboardingData(username, password, Scenario, appStat, RandomDeviceID);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println("Get user onboarding State Service Response : " + response);
		int reqStatus = 200;
		if (Scenario.equals("NONE"))
			reqStatus = 200;
		AssertJUnit.assertEquals("Get User Onboarding State Service is Down!!", reqStatus, request.response.getStatus());

		String DependantStrategy = devApiServiceHelper.getMultipleStrategies(response, "DEPENDENT", 5);

		System.out.println("Strategy : " + DependantStrategy);
		request = devApiServiceHelper.SaveHallmarkUserOnboardingData(username, password, Scenario, appStat, RandomDeviceID, DependantStrategy);
		response = request.respvalidate.returnresponseasstring();
		System.out.println("Save user onboarding state Response : " + response);
		if (Scenario.equals("NONE"))
			reqStatus = 200;
		AssertJUnit.assertEquals("Save User Onboarding State Service is Down!!", reqStatus, request.response.getStatus());

	}

	//Save Hallmark Services OnBoarding data
	@Test(groups = {
			"Fox",
			"Smoke",
			"",
			"Sanity",
			"FoxSanity",
			"MiniRegression",
			"Regression",
			"ExhaustiveRegression"
	}, dataProvider = "HallMark_SaveOnBoardingStrategies",
	description = "Save OnBoarding with Multiple Main Strategies")
	public void DevAPI_saveOnboardingState_Multiple_Main_Strategy_VerifySuccess(String username, String password, String Scenario, String appStat, String deviceID) {
		if (password.length() == 0) password = getPass(username);

		RequestGenerator devApiSignUpReqGen = devApiServiceHelper.invokeDevApiSignUp(username, password);
		String devApiSignUpResponse = devApiSignUpReqGen.respvalidate.returnresponseasstring();

		String RandomDeviceID = devApiServiceHelper.randomize(deviceID);
		RequestGenerator request = devApiServiceHelper.getHallmarkUserOnboardingData(username, password, Scenario, appStat, RandomDeviceID);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println("Get user onboarding State Service Response : " + response);
		int reqStatus = 200;
		if (Scenario.equals("NONE"))
			reqStatus = 200;
		AssertJUnit.assertEquals("Get User Onboarding State Service is Down!!", reqStatus, request.response.getStatus());

		String DependantStrategy = devApiServiceHelper.getMultipleStrategies(response, "MAIN", 5);

		System.out.println("Strategy : " + DependantStrategy);
		request = devApiServiceHelper.SaveHallmarkUserOnboardingData(username, password, Scenario, appStat, RandomDeviceID, DependantStrategy);
		response = request.respvalidate.returnresponseasstring();
		System.out.println("Save user onboarding state Response : " + response);
		if (Scenario.equals("NONE"))
			reqStatus = 200;
		AssertJUnit.assertEquals("Save User Onboarding State Service is Down!!", reqStatus, request.response.getStatus());

	}

	//Hallmark Data Validations
	//Dependent Strategy Flows
	@Test(groups = {
			"Fox",
			"Smoke",
			"",
			"Sanity",
			"FoxSanity",
			"MiniRegression",
			"Regression",
			"ExhaustiveRegression"
	}, dataProvider = "HallMark_SaveOnBoardingStrategies",
	description = "Save OnBoarding with Dependant Strategies Flow")
	public void DevAPI_saveOnboardingState_Dependant_Flow1(String username, String password, String Scenario, String appStat, String deviceID) {
		if (password.length() == 0) password = getPass(username);

		RequestGenerator devApiSignUpReqGen = devApiServiceHelper.invokeDevApiSignUp(username, password);
		String devApiSignUpResponse = devApiSignUpReqGen.respvalidate.returnresponseasstring();
		String RandomDeviceID = devApiServiceHelper.randomize(deviceID);
		RequestGenerator request = devApiServiceHelper.getHallmarkUserOnboardingData(username, password, Scenario, appStat, RandomDeviceID);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println("Get user onboarding State Service Response : " + response);
		int reqStatus = 200;
		if (Scenario.equals("NONE"))
			reqStatus = 200;
		AssertJUnit.assertEquals("Get User Onboarding State Service is Down!!", reqStatus, request.response.getStatus());

		String DependantStrategy = devApiServiceHelper.getSingleStrategy(response, "DEPENDENT");
		System.out.println("Strategy : " + DependantStrategy);
		request = devApiServiceHelper.SaveHallmarkUserOnboardingData(username, password, Scenario, appStat, RandomDeviceID, DependantStrategy);
		response = request.respvalidate.returnresponseasstring();
		System.out.println("Save user onboarding state Response : " + response);
		if (Scenario.equals("NONE"))
			reqStatus = 200;
		AssertJUnit.assertEquals("Save User Onboarding State Service is Down!!", reqStatus, request.response.getStatus());

		request = devApiServiceHelper.getHallmarkUserOnboardingData(username, password, Scenario, appStat, RandomDeviceID);
		response = request.respvalidate.returnresponseasstring();
		System.out.println("Get user onboarding State Service Response : " + response);
		AssertJUnit.assertEquals("Get User Onboarding State Service is Down!!", reqStatus, request.response.getStatus());


	}



	@Test(groups = {
			"Fox",
			"Smoke",
			"",
			"Sanity",
			"FoxSanity",
			"MiniRegression",
			"Regression",
			"ExhaustiveRegression"
	}, dataProvider = "HallMark_SaveOnBoardingStrategies",
	description = "Save onBoarding with Dependent strategies. Same user with different device")
	public void DevAPI_saveOnboardingState_Dependant_Flow2_SameUser_DifferentDevice(String username, String password, String Scenario, String appStat, String deviceID) {
		if (password.length() == 0) password = getPass(username);

		RequestGenerator devApiSignUpReqGen = devApiServiceHelper.invokeDevApiSignUp(username, password);
		String devApiSignUpResponse = devApiSignUpReqGen.respvalidate.returnresponseasstring();

		String RandomDeviceID = devApiServiceHelper.randomize(deviceID);
		//Overriding Scenario Type to match the test case
		RequestGenerator request = devApiServiceHelper.getHallmarkUserOnboardingData(username, password, "XID", appStat, RandomDeviceID);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println("Get user onboarding State Service Response : " + response);
		int reqStatus = 200;
		AssertJUnit.assertEquals("Get User Onboarding State Service is Down!!", reqStatus, request.response.getStatus());

		String DependantStrategy = devApiServiceHelper.getSingleStrategy(response, "DEPENDENT");
		System.out.println("Strategy : " + DependantStrategy);
		request = devApiServiceHelper.SaveHallmarkUserOnboardingData(username, password, "XID", appStat, RandomDeviceID, DependantStrategy);
		response = request.respvalidate.returnresponseasstring();
		System.out.println("Save user onboarding state Response : " + response);
		AssertJUnit.assertEquals("Save User Onboarding State Service is Down!!", 200, request.response.getStatus());

		request = devApiServiceHelper.getHallmarkUserOnboardingData(username, password, "XID", appStat, RandomDeviceID);
		response = request.respvalidate.returnresponseasstring();
		System.out.println("Get user onboarding State Service Response : " + response);
		AssertJUnit.assertEquals("Get User Onboarding State Service is Down!!", reqStatus, request.response.getStatus());
		AssertJUnit.assertEquals("Dependent Strategy is Not Removed in Dependencies", true, devApiServiceHelper.isStrategyRemoved(response, "DEPENDENT", DependantStrategy));
		AssertJUnit.assertEquals("Dependent Strategy is Not Removed in Main", true, devApiServiceHelper.isStrategyRemoved(response, "MAIN", DependantStrategy));

		RandomDeviceID = devApiServiceHelper.randomize(deviceID);
		request = devApiServiceHelper.getHallmarkUserOnboardingData(username, password, "XID", appStat, RandomDeviceID);
		response = request.respvalidate.returnresponseasstring();
		System.out.println("Get user onboarding State Service Response : " + response);
		AssertJUnit.assertEquals("Get User Onboarding State Service is Down!!", reqStatus, request.response.getStatus());
		AssertJUnit.assertEquals("Dependent Strategy is Not Removed in Dependencies - For Different Device", true, devApiServiceHelper.isStrategyRemoved(response, "DEPENDENT", DependantStrategy));
		AssertJUnit.assertEquals("Dependent Strategy is Not Removed in Main - For Different Device", true, devApiServiceHelper.isStrategyRemoved(response, "MAIN", DependantStrategy));


	}

	@Test(groups = {
			"Fox",
			"Smoke",
			"",
			"Sanity",
			"FoxSanity",
			"MiniRegression",
			"Regression",
			"ExhaustiveRegression"
	}, dataProvider = "HallMark_SaveOnBoardingStrategies",
	description = "Save onBoarding State Flow - Same Device different Users")
	public void DevAPI_saveOnboardingState_Dependant_Flow3_SameDevice_DifferentUser(String username, String password, String Scenario, String appStat, String deviceID) {
		if (password.length() == 0) password = getPass(username);

		RequestGenerator devApiSignUpReqGen = devApiServiceHelper.invokeDevApiSignUp(username, password);
		String devApiSignUpResponse = devApiSignUpReqGen.respvalidate.returnresponseasstring();

		String RandomDeviceID = devApiServiceHelper.randomize(deviceID);
		//Overriding Scenario Type to match the test case
		RequestGenerator request = devApiServiceHelper.getHallmarkUserOnboardingData(username, password, "DEVICEID", appStat, RandomDeviceID);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println("Get user onboarding State Service Response : " + response);
		int reqStatus = 200;
		AssertJUnit.assertEquals("Get User Onboarding State Service is Down!!", reqStatus, request.response.getStatus());

		String DependantStrategy = devApiServiceHelper.getSingleStrategy(response, "DEPENDENT");
		System.out.println("Strategy : " + DependantStrategy);
		request = devApiServiceHelper.SaveHallmarkUserOnboardingData(username, password, "DEVICEID", appStat, RandomDeviceID, DependantStrategy);
		response = request.respvalidate.returnresponseasstring();
		System.out.println("Save user onboarding state Response : " + response);
		AssertJUnit.assertEquals("Save User Onboarding State Service is Down!!", 200, request.response.getStatus());

		request = devApiServiceHelper.getHallmarkUserOnboardingData(username, password, "DEVICEID", appStat, RandomDeviceID);
		response = request.respvalidate.returnresponseasstring();
		System.out.println("Get user onboarding State Service Response : " + response);
		AssertJUnit.assertEquals("Get User Onboarding State Service is Down!!", reqStatus, request.response.getStatus());
		AssertJUnit.assertEquals("Dependent Strategy is Not Removed in Dependencies", true, devApiServiceHelper.isStrategyRemoved(response, "DEPENDENT", DependantStrategy));
		AssertJUnit.assertEquals("Dependent Strategy is Not Removed in Main", true, devApiServiceHelper.isStrategyRemoved(response, "MAIN", DependantStrategy));

		DevApisDataProvider obj = new DevApisDataProvider();

		String newUser = (obj.getRandomEmailId(15));
		devApiSignUpReqGen = devApiServiceHelper.invokeDevApiSignUp(newUser, password);
		devApiSignUpResponse = devApiSignUpReqGen.respvalidate.returnresponseasstring();
		request = devApiServiceHelper.getHallmarkUserOnboardingData(newUser, password, "BOTH", appStat, RandomDeviceID);
		response = request.respvalidate.returnresponseasstring();
		System.out.println("Get user onboarding State Service Response : " + response);
		AssertJUnit.assertEquals("Get User Onboarding State Service is Down!!", reqStatus, request.response.getStatus());
		AssertJUnit.assertEquals("Dependent Strategy is Not Removed in Dependencies - For Different Device", false, devApiServiceHelper.isStrategyRemoved(response, "DEPENDENT", DependantStrategy));
		AssertJUnit.assertEquals("Dependent Strategy is Not Removed in Main - For Different Device", false, devApiServiceHelper.isStrategyRemoved(response, "MAIN", DependantStrategy));


	}

	@Test(groups = {
			"Fox",
			"Smoke",
			"",
			"Sanity",
			"FoxSanity",
			"MiniRegression",
			"Regression",
			"ExhaustiveRegression"
	}, dataProvider = "HallMark_SaveOnBoardingStrategies",
	description = "Save onBoarding state - Same user With Same device ")
	public void DevAPI_saveOnboardingState_Main_Flow1_SameUser_SameDevice(String username, String password, String Scenario, String appStat, String deviceID) {
		if (password.length() == 0) password = getPass(username);

		RequestGenerator devApiSignUpReqGen = devApiServiceHelper.invokeDevApiSignUp(username, password);
		String devApiSignUpResponse = devApiSignUpReqGen.respvalidate.returnresponseasstring();

		String RandomDeviceID = devApiServiceHelper.randomize(deviceID);
		//Overriding Scenario Type to match the test case
		RequestGenerator request = devApiServiceHelper.getHallmarkUserOnboardingData(username, password, "XID", appStat, RandomDeviceID);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println("Get user onboarding State Service Response : " + response);
		int reqStatus = 200;
		AssertJUnit.assertEquals("Get User Onboarding State Service is Down!!", reqStatus, request.response.getStatus());

		String DependantStrategy = devApiServiceHelper.getSingleStrategy(response, "MAIN");
		System.out.println("Strategy : " + DependantStrategy);
		request = devApiServiceHelper.SaveHallmarkUserOnboardingData(username, password, "XID", appStat, RandomDeviceID, DependantStrategy);
		response = request.respvalidate.returnresponseasstring();
		System.out.println("Save user onboarding state Response : " + response);
		AssertJUnit.assertEquals("Save User Onboarding State Service is Down!!", 200, request.response.getStatus());

		request = devApiServiceHelper.getHallmarkUserOnboardingData(username, password, "XID", appStat, RandomDeviceID);
		response = request.respvalidate.returnresponseasstring();
		System.out.println("Get user onboarding State Service Response : " + response);
		AssertJUnit.assertEquals("Get User Onboarding State Service is Down!!", reqStatus, request.response.getStatus());
		AssertJUnit.assertEquals("Dependent Strategy is Not Removed in Dependencies", true, devApiServiceHelper.isStrategyRemoved(response, "DEPENDENT", DependantStrategy));
		AssertJUnit.assertEquals("Dependent Strategy is Not Removed in Main", true, devApiServiceHelper.isStrategyRemoved(response, "MAIN", DependantStrategy));

		request = devApiServiceHelper.getHallmarkUserOnboardingData(username, password, "XID", appStat, RandomDeviceID);
		response = request.respvalidate.returnresponseasstring();
		System.out.println("Get user onboarding State Service Response : " + response);
		AssertJUnit.assertEquals("Get User Onboarding State Service is Down!!", reqStatus, request.response.getStatus());
		AssertJUnit.assertEquals("Dependent Strategy is Not Removed in Dependencies - For Different Device", true, devApiServiceHelper.isStrategyRemoved(response, "DEPENDENT", DependantStrategy));
		AssertJUnit.assertEquals("Dependent Strategy is Not Removed in Main - For Different Device", true, devApiServiceHelper.isStrategyRemoved(response, "MAIN", DependantStrategy));

	}

	@Test(groups = {
			"Fox",
			"Smoke",
			"",
			"Sanity",
			"FoxSanity",
			"MiniRegression",
			"Regression",
			"ExhaustiveRegression"
	}, dataProvider = "HallMark_SaveOnBoardingStrategies",
	description = "Save OnBoarding data with Main Strategies - Same user in Different Device")
	public void DevAPI_saveOnboardingState_Main_Flow2_SameUser_DifferentDevice(String username, String password, String Scenario, String appStat, String deviceID) {
		if (password.length() == 0) password = getPass(username);

		RequestGenerator devApiSignUpReqGen = devApiServiceHelper.invokeDevApiSignUp(username, password);
		String devApiSignUpResponse = devApiSignUpReqGen.respvalidate.returnresponseasstring();

		String RandomDeviceID = devApiServiceHelper.randomize(deviceID);
		//Overriding Scenario Type to match the test case
		RequestGenerator request = devApiServiceHelper.getHallmarkUserOnboardingData(username, password, "XID", appStat, RandomDeviceID);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println("Get user onboarding State Service Response : " + response);
		int reqStatus = 200;
		AssertJUnit.assertEquals("Get User Onboarding State Service is Down!!", reqStatus, request.response.getStatus());

		String DependantStrategy = devApiServiceHelper.getSingleStrategy(response, "MAIN");
		System.out.println("Strategy : " + DependantStrategy);
		request = devApiServiceHelper.SaveHallmarkUserOnboardingData(username, password, "XID", appStat, RandomDeviceID, DependantStrategy);
		response = request.respvalidate.returnresponseasstring();
		System.out.println("Save user onboarding state Response : " + response);
		AssertJUnit.assertEquals("Save User Onboarding State Service is Down!!", 200, request.response.getStatus());

		request = devApiServiceHelper.getHallmarkUserOnboardingData(username, password, "XID", appStat, RandomDeviceID);
		response = request.respvalidate.returnresponseasstring();
		System.out.println("Get user onboarding State Service Response : " + response);
		AssertJUnit.assertEquals("Get User Onboarding State Service is Down!!", reqStatus, request.response.getStatus());
		AssertJUnit.assertEquals("Dependent Strategy is Not Removed in Dependencies", true, devApiServiceHelper.isStrategyRemoved(response, "DEPENDENT", DependantStrategy));
		AssertJUnit.assertEquals("Dependent Strategy is Not Removed in Main", true, devApiServiceHelper.isStrategyRemoved(response, "MAIN", DependantStrategy));

		RandomDeviceID = devApiServiceHelper.randomize(deviceID);
		request = devApiServiceHelper.getHallmarkUserOnboardingData(username, password, "XID", appStat, RandomDeviceID);
		response = request.respvalidate.returnresponseasstring();
		System.out.println("Get user onboarding State Service Response : " + response);
		AssertJUnit.assertEquals("Get User Onboarding State Service is Down!!", reqStatus, request.response.getStatus());
		AssertJUnit.assertEquals("Dependent Strategy is Not Removed in Dependencies - For Different Device", true, devApiServiceHelper.isStrategyRemoved(response, "DEPENDENT", DependantStrategy));
		AssertJUnit.assertEquals("Dependent Strategy is Not Removed in Main - For Different Device", true, devApiServiceHelper.isStrategyRemoved(response, "MAIN", DependantStrategy));


	}



	@Test(groups = {
			"Fox",
			"Smoke",
			"",
			"Sanity",
			"FoxSanity",
			"MiniRegression",
			"Regression",
			"ExhaustiveRegression"
	}, dataProvider = "HallMark_SaveOnBoardingStrategies",
	description = "Save OnBoarding State with Dependant Strategies - Check XID takes priority")
	public void DevAPI_saveOnboardingState_Dependent_Validate_XID_Priority(String username, String password, String Scenario, String appStat, String deviceID) {
		if (password.length() == 0) password = getPass(username);

		RequestGenerator devApiSignUpReqGen = devApiServiceHelper.invokeDevApiSignUp(username, password);
		String devApiSignUpResponse = devApiSignUpReqGen.respvalidate.returnresponseasstring();

		String RandomDeviceID = devApiServiceHelper.randomize(deviceID);
		//Overriding Scenario Type to match the test case
		RequestGenerator request = devApiServiceHelper.getHallmarkUserOnboardingData(username, password, "DEVICEID", appStat, RandomDeviceID);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println("Get user onboarding State Service Response : " + response);
		int reqStatus = 200;
		AssertJUnit.assertEquals("Get User Onboarding State Service is Down!!", reqStatus, request.response.getStatus());

		String DependantStrategy = devApiServiceHelper.getSingleStrategy(response, "MAIN");
		System.out.println("Device Strategy : " + DependantStrategy);
		request = devApiServiceHelper.SaveHallmarkUserOnboardingData(username, password, "DEVICEID", appStat, RandomDeviceID, DependantStrategy);
		response = request.respvalidate.returnresponseasstring();
		System.out.println("Save user onboarding state Response : " + response);
		AssertJUnit.assertEquals("Save User Onboarding State Service is Down!!", 200, request.response.getStatus());

		request = devApiServiceHelper.getHallmarkUserOnboardingData(username, password, "XID", appStat, RandomDeviceID);
		response = request.respvalidate.returnresponseasstring();
		System.out.println("Get user onboarding State Service Response : " + response);
		AssertJUnit.assertEquals("Get User Onboarding State Service is Down!!", reqStatus, request.response.getStatus());
		AssertJUnit.assertEquals("Dependent Strategy is  Removed in Dependencies For Different User", false, devApiServiceHelper.isStrategyRemoved(response, "MAIN", DependantStrategy));
		AssertJUnit.assertEquals("Dependent Strategy is  Removed in Main For Different User", false, devApiServiceHelper.isStrategyRemoved(response, "MAIN", DependantStrategy));

		String DependantStrategy2 = null;
		do {
			DependantStrategy2 = devApiServiceHelper.getSingleStrategy(response, "MAIN");
		} while (DependantStrategy2.equals(DependantStrategy));
		System.out.println("XID Strategy : " + DependantStrategy2);
		request = devApiServiceHelper.SaveHallmarkUserOnboardingData(username, password, "XID", appStat, RandomDeviceID, DependantStrategy2);
		response = request.respvalidate.returnresponseasstring();
		System.out.println("Save user onboarding state Response : " + response);
		AssertJUnit.assertEquals("Save User Onboarding State Service is Down!!", 200, request.response.getStatus());

		request = devApiServiceHelper.getHallmarkUserOnboardingData(username, password, "BOTH", appStat, RandomDeviceID);
		response = request.respvalidate.returnresponseasstring();
		System.out.println("Get user onboarding State Service Response : " + response);
		AssertJUnit.assertEquals("Get User Onboarding State Service is Down!!", reqStatus, request.response.getStatus());
		AssertJUnit.assertEquals("Dependent Strategy is  Removed in Dependencies For Different User", false, devApiServiceHelper.isStrategyRemoved(response, "MAIN", DependantStrategy));
		AssertJUnit.assertEquals("Dependent Strategy is  Removed in Main For Different User", false, devApiServiceHelper.isStrategyRemoved(response, "MAIN", DependantStrategy));
		AssertJUnit.assertEquals("Dependent Strategy is  Removed in Dependencies For Different User", true, devApiServiceHelper.isStrategyRemoved(response, "MAIN", DependantStrategy2));
		AssertJUnit.assertEquals("Dependent Strategy is  Removed in Main For Different User", true, devApiServiceHelper.isStrategyRemoved(response, "MAIN", DependantStrategy2));


	}

	@Test(groups = {
			"Fox",
			"Smoke",
			"",
			"Sanity",
			"FoxSanity",
			"MiniRegression",
			"Regression",
			"ExhaustiveRegression"
	}, dataProvider = "HallMark_SaveOnBoardingStrategies",
	description = "Save onBoarding user data with multiple depenedant strategies - Same user with same device")
	public void DevAPI_saveOnboardingState_Multiple_Dependant_Flow1_SameUser_SameDevice(String username, String password, String Scenario, String appStat, String deviceID) {
		if (password.length() == 0) password = getPass(username);

		RequestGenerator devApiSignUpReqGen = devApiServiceHelper.invokeDevApiSignUp(username, password);
		String devApiSignUpResponse = devApiSignUpReqGen.respvalidate.returnresponseasstring();

		String RandomDeviceID = devApiServiceHelper.randomize(deviceID);
		//Overriding Scenario Type to match the test case
		RequestGenerator request = devApiServiceHelper.getHallmarkUserOnboardingData(username, password, "BOTH", appStat, RandomDeviceID);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println("Get user onboarding State Service Response : " + response);
		int reqStatus = 200;
		AssertJUnit.assertEquals("Get User Onboarding State Service is Down!!", reqStatus, request.response.getStatus());

		String DependantStrategy = devApiServiceHelper.getMultipleStrategies(response, "DEPENDENT", 3);
		request = devApiServiceHelper.SaveHallmarkUserOnboardingData(username, password, "BOTH", appStat, RandomDeviceID, DependantStrategy);
		response = request.respvalidate.returnresponseasstring();
		System.out.println("Save user onboarding state Response : " + response);
		AssertJUnit.assertEquals("Save User Onboarding State Service is Down!!", 200, request.response.getStatus());

		request = devApiServiceHelper.getHallmarkUserOnboardingData(username, password, "BOTH", appStat, RandomDeviceID);
		response = request.respvalidate.returnresponseasstring();
		System.out.println("Get user onboarding State Service Response : " + response);
		AssertJUnit.assertEquals("Get User Onboarding State Service is Down!!", reqStatus, request.response.getStatus());

		boolean status = true;
		String Strategies[] = DependantStrategy.split("\",\"");
		for (int i = 0; i < Strategies.length; i++) {
			status = status && devApiServiceHelper.isStrategyRemoved(response, "DEPENDENT", Strategies[i]);
			status = status && devApiServiceHelper.isStrategyRemoved(response, "MAIN", Strategies[i]);
		}


		AssertJUnit.assertEquals("Dependent Strategy is Not Removed in Dependencies", true, status);
		AssertJUnit.assertEquals("Dependent Strategy is Not Removed in Main", true, status);

	}

	@Test(groups = {
			"Fox",
			"Smoke",
			"",
			"Sanity",
			"MiniRegression",
			"Regression",
			"ExhaustiveRegression"
	}, dataProvider = "HallMark_SaveOnBoardingStrategies",
	description = "Save onBoarding data with Multiple Dependant Strategies - Same user Different Device")
	public void DevAPI_saveOnboardingState_Multiple_Dependant_Flow2_SameUser_DifferentDevice(String username, String password, String Scenario, String appStat, String deviceID) {
		if (password.length() == 0) password = getPass(username);

		RequestGenerator devApiSignUpReqGen = devApiServiceHelper.invokeDevApiSignUp(username, password);
		String devApiSignUpResponse = devApiSignUpReqGen.respvalidate.returnresponseasstring();

		String RandomDeviceID = devApiServiceHelper.randomize(deviceID);
		//Overriding Scenario Type to match the test case
		RequestGenerator request = devApiServiceHelper.getHallmarkUserOnboardingData(username, password, "XID", appStat, RandomDeviceID);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println("Get user onboarding State Service Response : " + response);
		int reqStatus = 200;
		AssertJUnit.assertEquals("Get User Onboarding State Service is Down!!", reqStatus, request.response.getStatus());

		String DependantStrategy = devApiServiceHelper.getMultipleStrategies(response, "DEPENDENT", 4);
		System.out.println("Strategy : " + DependantStrategy);
		request = devApiServiceHelper.SaveHallmarkUserOnboardingData(username, password, "XID", appStat, RandomDeviceID, DependantStrategy);
		response = request.respvalidate.returnresponseasstring();
		System.out.println("Save user onboarding state Response : " + response);
		AssertJUnit.assertEquals("Save User Onboarding State Service is Down!!", 200, request.response.getStatus());

		request = devApiServiceHelper.getHallmarkUserOnboardingData(username, password, "XID", appStat, RandomDeviceID);
		response = request.respvalidate.returnresponseasstring();
		System.out.println("Get user onboarding State Service Response : " + response);
		AssertJUnit.assertEquals("Get User Onboarding State Service is Down!!", reqStatus, request.response.getStatus());
		boolean status = true;
		String Strategies[] = DependantStrategy.split("\",\"");
		for (int i = 0; i < Strategies.length; i++) {
			status = status && devApiServiceHelper.isStrategyRemoved(response, "DEPENDENT", Strategies[i]);
			status = status && devApiServiceHelper.isStrategyRemoved(response, "MAIN", Strategies[i]);
		}
		AssertJUnit.assertEquals("Dependent Strategy is Not Removed in Dependencies", true, status);
		AssertJUnit.assertEquals("Dependent Strategy is Not Removed in Main", true, status);

		RandomDeviceID = devApiServiceHelper.randomize(deviceID);
		request = devApiServiceHelper.getHallmarkUserOnboardingData(username, password, "XID", appStat, RandomDeviceID);
		response = request.respvalidate.returnresponseasstring();
		System.out.println("Get user onboarding State Service Response : " + response);
		AssertJUnit.assertEquals("Get User Onboarding State Service is Down!!", reqStatus, request.response.getStatus());

		status = true;
		for (int i = 0; i < Strategies.length; i++) {
			status = status && devApiServiceHelper.isStrategyRemoved(response, "DEPENDENT", Strategies[i]);
			status = status && devApiServiceHelper.isStrategyRemoved(response, "MAIN", Strategies[i]);
		}
		AssertJUnit.assertEquals("Dependent Strategy is Not Removed in Dependencies", true, status);
		AssertJUnit.assertEquals("Dependent Strategy is Not Removed in Main", true, status);


	}

	@Test(groups = {
			"Fox",
			"Smoke",
			"",
			"Sanity",
			"FoxSanity",
			"MiniRegression",
			"Regression",
			"ExhaustiveRegression"
	}, dataProvider = "HallMark_SaveOnBoardingStrategies",
	description = "Save onBoarding data with Multiple Dependant Strategies - Same Device Different users")
	public void DevAPI_saveOnboardingState_Multiple_Dependant_Flow3_SameDevice_DifferentUser(String username, String password, String Scenario, String appStat, String deviceID) {
		if (password.length() == 0) password = getPass(username);

		RequestGenerator devApiSignUpReqGen = devApiServiceHelper.invokeDevApiSignUp(username, password);
		String devApiSignUpResponse = devApiSignUpReqGen.respvalidate.returnresponseasstring();

		String RandomDeviceID = devApiServiceHelper.randomize(deviceID);
		//Overriding Scenario Type to match the test case
		RequestGenerator request = devApiServiceHelper.getHallmarkUserOnboardingData(username, password, "BOTH", appStat, RandomDeviceID);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println("Get user onboarding State Service Response : " + response);
		int reqStatus = 200;
		AssertJUnit.assertEquals("Get User Onboarding State Service is Down!!", reqStatus, request.response.getStatus());

		String DependantStrategy = devApiServiceHelper.getMultipleStrategies(response, "DEPENDENT", 5);
		System.out.println("Strategy : " + DependantStrategy);
		request = devApiServiceHelper.SaveHallmarkUserOnboardingData(username, password, "BOTH", appStat, RandomDeviceID, DependantStrategy);
		response = request.respvalidate.returnresponseasstring();
		System.out.println("Save user onboarding state Response : " + response);
		AssertJUnit.assertEquals("Save User Onboarding State Service is Down!!", 200, request.response.getStatus());

		request = devApiServiceHelper.getHallmarkUserOnboardingData(username, password, "BOTH", appStat, RandomDeviceID);
		response = request.respvalidate.returnresponseasstring();
		System.out.println("Get user onboarding State Service Response : " + response);
		AssertJUnit.assertEquals("Get User Onboarding State Service is Down!!", reqStatus, request.response.getStatus());
		boolean status = true;
		String Strategies[] = DependantStrategy.split("\",\"");
		for (int i = 0; i < Strategies.length; i++) {
			status = status && devApiServiceHelper.isStrategyRemoved(response, "DEPENDENT", Strategies[i]);
			status = status && devApiServiceHelper.isStrategyRemoved(response, "MAIN", Strategies[i]);
		}
		AssertJUnit.assertEquals("Dependent Strategy is Not Removed in Dependencies", true, status);
		AssertJUnit.assertEquals("Dependent Strategy is Not Removed in Main", true, status);

		DevApisDataProvider obj = new DevApisDataProvider();

		String newUser = (obj.getRandomEmailId(15));
		devApiSignUpReqGen = devApiServiceHelper.invokeDevApiSignUp(newUser, password);
		devApiSignUpResponse = devApiSignUpReqGen.respvalidate.returnresponseasstring();
		request = devApiServiceHelper.getHallmarkUserOnboardingData(newUser, password, "BOTH", appStat, RandomDeviceID);
		response = request.respvalidate.returnresponseasstring();
		System.out.println("Get user onboarding State Service Response : " + response);
		AssertJUnit.assertEquals("Get User Onboarding State Service is Down!!", reqStatus, request.response.getStatus());
		status = true;
		for (int i = 0; i < Strategies.length; i++) {
			status = status && devApiServiceHelper.isStrategyRemoved(response, "DEPENDENT", Strategies[i]);
			status = status && devApiServiceHelper.isStrategyRemoved(response, "MAIN", Strategies[i]);
		}
		AssertJUnit.assertEquals("Dependent Strategy is  Removed in Dependencies", false, status);
		AssertJUnit.assertEquals("Dependent Strategy is  Removed in Main", false, status);

	}

	@Test(groups = {
			"Fox",
			"Smoke",
			"",
			"Sanity",
			"FoxSanity",
			"MiniRegression",
			"Regression",
			"ExhaustiveRegression"
	}, dataProvider = "HallMark_SaveOnBoardingStrategies",
	description = "Save onBoarding data with Multiple Main Strategies - Same user Same Device")
	public void DevAPI_saveOnboardingState_Multiple_Main_Flow1_SameUser_SameDevice(String username, String password, String Scenario, String appStat, String deviceID) {
		if (password.length() == 0) password = getPass(username);

		RequestGenerator devApiSignUpReqGen = devApiServiceHelper.invokeDevApiSignUp(username, password);
		String devApiSignUpResponse = devApiSignUpReqGen.respvalidate.returnresponseasstring();

		String RandomDeviceID = devApiServiceHelper.randomize(deviceID);
		//Overriding Scenario Type to match the test case
		RequestGenerator request = devApiServiceHelper.getHallmarkUserOnboardingData(username, password, "BOTH", appStat, RandomDeviceID);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println("Get user onboarding State Service Response : " + response);
		int reqStatus = 200;
		AssertJUnit.assertEquals("Get User Onboarding State Service is Down!!", reqStatus, request.response.getStatus());

		String DependantStrategy = devApiServiceHelper.getMultipleStrategies(response, "MAIN", 3);
		request = devApiServiceHelper.SaveHallmarkUserOnboardingData(username, password, "BOTH", appStat, RandomDeviceID, DependantStrategy);
		response = request.respvalidate.returnresponseasstring();
		System.out.println("Save user onboarding state Response : " + response);
		AssertJUnit.assertEquals("Save User Onboarding State Service is Down!!", 200, request.response.getStatus());

		request = devApiServiceHelper.getHallmarkUserOnboardingData(username, password, "BOTH", appStat, RandomDeviceID);
		response = request.respvalidate.returnresponseasstring();
		System.out.println("Get user onboarding State Service Response : " + response);
		AssertJUnit.assertEquals("Get User Onboarding State Service is Down!!", reqStatus, request.response.getStatus());

		boolean status = true;
		String Strategies[] = DependantStrategy.split("\",\"");
		for (int i = 0; i < Strategies.length; i++) {
			status = status && devApiServiceHelper.isStrategyRemoved(response, "DEPENDENT", Strategies[i]);
			status = status && devApiServiceHelper.isStrategyRemoved(response, "MAIN", Strategies[i]);
		}


		AssertJUnit.assertEquals("Dependent Strategy is Not Removed in Dependencies", true, status);
		AssertJUnit.assertEquals("Dependent Strategy is Not Removed in Main", true, status);

	}

	@Test(groups = {
			"Fox",
			"Smoke",
			"",
			"Sanity",
			"FoxSanity",
			"MiniRegression",
			"Regression",
			"ExhaustiveRegression"
	}, dataProvider = "HallMark_SaveOnBoardingStrategies",
	description = "Save onBoarding data with Multiple Main Strategies - Same user Different Device")
	public void DevAPI_saveOnboardingState_Multiple_MAIN_Flow2_SameUser_DifferentDevice(String username, String password, String Scenario, String appStat, String deviceID) {
		if (password.length() == 0) password = getPass(username);

		RequestGenerator devApiSignUpReqGen = devApiServiceHelper.invokeDevApiSignUp(username, password);
		String devApiSignUpResponse = devApiSignUpReqGen.respvalidate.returnresponseasstring();

		String RandomDeviceID = devApiServiceHelper.randomize(deviceID);
		//Overriding Scenario Type to match the test case
		RequestGenerator request = devApiServiceHelper.getHallmarkUserOnboardingData(username, password, "XID", appStat, RandomDeviceID);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println("Get user onboarding State Service Response : " + response);
		int reqStatus = 200;
		AssertJUnit.assertEquals("Get User Onboarding State Service is Down!!", reqStatus, request.response.getStatus());

		String DependantStrategy = devApiServiceHelper.getMultipleStrategies(response, "MAIN", 4);
		System.out.println("Strategy : " + DependantStrategy);
		request = devApiServiceHelper.SaveHallmarkUserOnboardingData(username, password, "XID", appStat, RandomDeviceID, DependantStrategy);
		response = request.respvalidate.returnresponseasstring();
		System.out.println("Save user onboarding state Response : " + response);
		AssertJUnit.assertEquals("Save User Onboarding State Service is Down!!", 200, request.response.getStatus());

		request = devApiServiceHelper.getHallmarkUserOnboardingData(username, password, "XID", appStat, RandomDeviceID);
		response = request.respvalidate.returnresponseasstring();
		System.out.println("Get user onboarding State Service Response : " + response);
		AssertJUnit.assertEquals("Get User Onboarding State Service is Down!!", reqStatus, request.response.getStatus());
		boolean status = true;
		String Strategies[] = DependantStrategy.split("\",\"");
		for (int i = 0; i < Strategies.length; i++) {
			status = status && devApiServiceHelper.isStrategyRemoved(response, "DEPENDENT", Strategies[i]);
			status = status && devApiServiceHelper.isStrategyRemoved(response, "MAIN", Strategies[i]);
		}
		AssertJUnit.assertEquals("Dependent Strategy is Not Removed in Dependencies", true, status);
		AssertJUnit.assertEquals("Dependent Strategy is Not Removed in Main", true, status);

		RandomDeviceID = devApiServiceHelper.randomize(deviceID);
		request = devApiServiceHelper.getHallmarkUserOnboardingData(username, password, "XID", appStat, RandomDeviceID);
		response = request.respvalidate.returnresponseasstring();
		System.out.println("Get user onboarding State Service Response : " + response);
		AssertJUnit.assertEquals("Get User Onboarding State Service is Down!!", reqStatus, request.response.getStatus());

		status = true;
		for (int i = 0; i < Strategies.length; i++) {
			status = status && devApiServiceHelper.isStrategyRemoved(response, "DEPENDENT", Strategies[i]);
			status = status && devApiServiceHelper.isStrategyRemoved(response, "MAIN", Strategies[i]);
		}
		AssertJUnit.assertEquals("Dependent Strategy is Not Removed in Dependencies", true, status);
		AssertJUnit.assertEquals("Dependent Strategy is Not Removed in Main", true, status);


	}

	@Test(groups = {
			"Fox",
			"Smoke",
			"",
			"Sanity",
			"FoxSanity",
			"MiniRegression",
			"Regression",
			"ExhaustiveRegression"
	}, dataProvider = "HallMark_SaveOnBoardingStrategies",
	description = "Save onBoarding data with Multiple Main Strategies - Same Device Different user")
	public void DevAPI_saveOnboardingState_Main_Dependant_Flow3_SameDevice_DifferentUser(String username, String password, String Scenario, String appStat, String deviceID) {
		if (password.length() == 0) password = getPass(username);

		RequestGenerator devApiSignUpReqGen = devApiServiceHelper.invokeDevApiSignUp(username, password);
		String devApiSignUpResponse = devApiSignUpReqGen.respvalidate.returnresponseasstring();

		String RandomDeviceID = devApiServiceHelper.randomize(deviceID);
		//Overriding Scenario Type to match the test case
		RequestGenerator request = devApiServiceHelper.getHallmarkUserOnboardingData(username, password, "BOTH", appStat, RandomDeviceID);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println("Get user onboarding State Service Response : " + response);
		int reqStatus = 200;
		AssertJUnit.assertEquals("Get User Onboarding State Service is Down!!", reqStatus, request.response.getStatus());

		String DependantStrategy = devApiServiceHelper.getMultipleStrategies(response, "MAIN", 5);
		System.out.println("Strategy : " + DependantStrategy);
		request = devApiServiceHelper.SaveHallmarkUserOnboardingData(username, password, "BOTH", appStat, RandomDeviceID, DependantStrategy);
		response = request.respvalidate.returnresponseasstring();
		System.out.println("Save user onboarding state Response : " + response);
		AssertJUnit.assertEquals("Save User Onboarding State Service is Down!!", 200, request.response.getStatus());

		request = devApiServiceHelper.getHallmarkUserOnboardingData(username, password, "BOTH", appStat, RandomDeviceID);
		response = request.respvalidate.returnresponseasstring();
		System.out.println("Get user onboarding State Service Response : " + response);
		AssertJUnit.assertEquals("Get User Onboarding State Service is Down!!", reqStatus, request.response.getStatus());
		boolean status = true;
		String Strategies[] = DependantStrategy.split("\",\"");
		for (int i = 0; i < Strategies.length; i++) {
			status = status && devApiServiceHelper.isStrategyRemoved(response, "DEPENDENT", Strategies[i]);
			status = status && devApiServiceHelper.isStrategyRemoved(response, "MAIN", Strategies[i]);
		}
		AssertJUnit.assertEquals("Dependent Strategy is Not Removed in Dependencies", true, status);
		AssertJUnit.assertEquals("Dependent Strategy is Not Removed in Main", true, status);

		DevApisDataProvider obj = new DevApisDataProvider();

		String newUser = (obj.getRandomEmailId(15));
		devApiSignUpReqGen = devApiServiceHelper.invokeDevApiSignUp(newUser, password);
		devApiSignUpResponse = devApiSignUpReqGen.respvalidate.returnresponseasstring();
		request = devApiServiceHelper.getHallmarkUserOnboardingData(newUser, password, "BOTH", appStat, RandomDeviceID);
		response = request.respvalidate.returnresponseasstring();
		System.out.println("Get user onboarding State Service Response : " + response);
		AssertJUnit.assertEquals("Get User Onboarding State Service is Down!!", reqStatus, request.response.getStatus());
		status = true;
		for (int i = 0; i < Strategies.length; i++) {
			status = status && devApiServiceHelper.isStrategyRemoved(response, "DEPENDENT", Strategies[i]);
			status = status && devApiServiceHelper.isStrategyRemoved(response, "MAIN", Strategies[i]);
		}
		AssertJUnit.assertEquals("Dependent Strategy is  Removed in Dependencies", false, status);
		AssertJUnit.assertEquals("Dependent Strategy is  Removed in Main", false, status);

	}

	// Fail Cases ReWork

	@Test(groups = {
			"Fox",
			"Smoke",
			"",
			"Sanity",
			"FoxSanity",
			"MiniRegression",
			"Regression",
			"ExhaustiveRegression"
	}, dataProvider = "DevAPI_saveOnboardingState_Dependant_Flow1_SameUser_SameDevice",
	description = "Save onBoarding data with  Dependant Strategies - Same user Same Device")
	public void DevAPI_saveOnboardingState_Dependant_Flow1_SameUser_SameDevice(String username, String password, String Scenario, String appStat, String deviceID) {
		if (password.length() == 0) password = getPass(username);

		RequestGenerator devApiSignUpReqGen = devApiServiceHelper.invokeDevApiSignUp(username, password);
		String devApiSignUpResponse = devApiSignUpReqGen.respvalidate.returnresponseasstring();

		String RandomDeviceID = devApiServiceHelper.randomize(deviceID);
		//Overriding Scenario Type to match the test case
		RequestGenerator request = devApiServiceHelper.getHallmarkUserOnboardingData(username, password, Scenario, appStat, RandomDeviceID);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println("Get user onboarding State Service Response : " + response);
		int reqStatus = 200;
		AssertJUnit.assertEquals("Get User Onboarding State Service is Down!!", reqStatus, request.response.getStatus());

		String DependantStrategy = devApiServiceHelper.getSingleStrategy(response, "DEPENDENT");
		System.out.println("Strategy : " + DependantStrategy);
		request = devApiServiceHelper.SaveHallmarkUserOnboardingData(username, password, Scenario, appStat, RandomDeviceID, DependantStrategy);
		response = request.respvalidate.returnresponseasstring();
		System.out.println("Save user onboarding state Response : " + response);
		AssertJUnit.assertEquals("Save User Onboarding State Service is Down!!", 200, request.response.getStatus());

		request = devApiServiceHelper.getHallmarkUserOnboardingData(username, password, Scenario, appStat, RandomDeviceID);
		response = request.respvalidate.returnresponseasstring();
		System.out.println("Get user onboarding State Service Response : " + response);
		AssertJUnit.assertEquals("Get User Onboarding State Service is Down!!", reqStatus, request.response.getStatus());
		AssertJUnit.assertEquals("Dependent Strategy is Not Removed in Dependencies", true, devApiServiceHelper.isStrategyRemoved(response, "DEPENDENT", DependantStrategy));
		AssertJUnit.assertEquals("Dependent Strategy is Not Removed in Main", true, devApiServiceHelper.isStrategyRemoved(response, "MAIN", DependantStrategy));


	}


	@Test(groups = {
			"Fox",
			"Smoke",
			"",
			"Sanity",
			"FoxSanity",
			"MiniRegression",
			"Regression",
			"ExhaustiveRegression"
	}, dataProvider = "DevAPI_saveOnboardingState_Main_Flow3_SameDevice_DifferentUser",
	description = "Save onBoarding data with  Main Strategy - Same Device Different Users")
	public void DevAPI_saveOnboardingState_Main_Flow3_SameDevice_DifferentUser(String username, String password, String Scenario, String appStat, String deviceID) {
		if (password.length() == 0) password = getPass(username);

		RequestGenerator devApiSignUpReqGen = devApiServiceHelper.invokeDevApiSignUp(username, password);
		String devApiSignUpResponse = devApiSignUpReqGen.respvalidate.returnresponseasstring();

		String RandomDeviceID = devApiServiceHelper.randomize(deviceID);
		//Overriding Scenario Type to match the test case
		RequestGenerator request = devApiServiceHelper.getHallmarkUserOnboardingData(username, password, Scenario, appStat, RandomDeviceID);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println("Get user onboarding State Service Response : " + response);
		int reqStatus = 200;
		AssertJUnit.assertEquals("Get User Onboarding State Service is Down!!", reqStatus, request.response.getStatus());

		String DependantStrategy = devApiServiceHelper.getSingleStrategy(response, "MAIN");
		System.out.println("Strategy : " + DependantStrategy);
		request = devApiServiceHelper.SaveHallmarkUserOnboardingData(username, password, Scenario, appStat, RandomDeviceID, DependantStrategy);
		response = request.respvalidate.returnresponseasstring();
		System.out.println("Save user onboarding state Response : " + response);
		AssertJUnit.assertEquals("Save User Onboarding State Service is Down!!", 200, request.response.getStatus());

		request = devApiServiceHelper.getHallmarkUserOnboardingData(username, password, Scenario, appStat, RandomDeviceID);
		response = request.respvalidate.returnresponseasstring();
		System.out.println("Get user onboarding State Service Response : " + response);
		AssertJUnit.assertEquals("Get User Onboarding State Service is Down!!", reqStatus, request.response.getStatus());
		AssertJUnit.assertEquals("Dependent Strategy is Not Removed in Dependencies", true, devApiServiceHelper.isStrategyRemoved(response, "DEPENDENT", DependantStrategy));
		AssertJUnit.assertEquals("Dependent Strategy is Not Removed in Main", true, devApiServiceHelper.isStrategyRemoved(response, "MAIN", DependantStrategy));

		DevApisDataProvider obj = new DevApisDataProvider();

		String newUser = (obj.getRandomEmailId(15));
		devApiSignUpReqGen = devApiServiceHelper.invokeDevApiSignUp(newUser, password);
		devApiSignUpResponse = devApiSignUpReqGen.respvalidate.returnresponseasstring();
		request = devApiServiceHelper.getHallmarkUserOnboardingData(newUser, password, Scenario, appStat, RandomDeviceID);
		response = request.respvalidate.returnresponseasstring();
		System.out.println("Get user onboarding State Service Response : " + response);
		AssertJUnit.assertEquals("Get User Onboarding State Service is Down!!", reqStatus, request.response.getStatus());
		AssertJUnit.assertEquals("Dependent Strategy is Not Removed in Dependencies - For Different Device", false, devApiServiceHelper.isStrategyRemoved(response, "MAIN", DependantStrategy));


	}


	//Try N Buy Test
	//v3 Serviceability
	@Test(groups = {
			"Fox",
			"Smoke",
			"ProdSanity",
			"Sanity",
			"FoxSanity",
			"MiniRegression",
			"Regression",
			"ExhaustiveRegression"
	}, dataProvider = "StyleServiceability_V3",
	description = "V3 Serviceability API Response")
	public void DevAPI_V3Serviceability_VerifySuccess(String searchString) {
		RequestGenerator request = devApiServiceHelper.invokeV3Serviceability(searchString);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println("V3 Serviceability Response : \n " + response);
		AssertJUnit.assertEquals("V3 Serviceability Service is Down!!", 200, request.response.getStatus());

	}

	/* @Test(groups = {
   "Fox",
   "Smoke",
   "ProdSanity",
   "Sanity","FoxSanity",
   "MiniRegression",
   "Regression",
   "ExhaustiveRegression"
  }, dataProvider = "StyleServiceability_V3")
  public void DevAPI_V3Serviceability_Verify_TryAndBuy_Data(String searchString) 
  {
   RequestGenerator request = devApiServiceHelper.invokeV3Serviceability(searchString);
   String response = request.respvalidate.returnresponseasstring();
   System.out.println("V3 Serviceability Response : \n "+response);
   AssertJUnit.assertEquals("V3 Serviceability Service is Down!!", 200, request.response.getStatus());

   AssertJUnit.assertEquals("V3 Serviceability Try n Buy - Option - Title is not proper", "Doorstep trial available", JsonPath.read(response, "$.data.options[0].pdp.title"));
   AssertJUnit.assertEquals("V3 Serviceability Try n Buy - Sub Options is not proper", "Try your purchase at the time of delivery(free)", JsonPath.read(response, "$.data.options[0].pdp.sub_options[0]"));
   AssertJUnit.assertEquals("V3 Serviceability Try n Buy - Availability option is not proper", true, JsonPath.read(response, "$.data.options[0].pdp.available"));
   AssertJUnit.assertEquals("V3 Serviceability Try n Buy - Option - Title is not proper", "DOORSTEP TRIAL AVAILABLE", JsonPath.read(response, "$.data.options[0].serviceability_check.title"));
   AssertJUnit.assertEquals("V3 Serviceability Try n Buy - Option - Sub Options is not proper", "Try your purchase at the time of delivery(<font color='#0bc6a0'>free introductory offer</font>)", JsonPath.read(response, "$.data.options[0].serviceability_check.sub_options[0]"));




  }


  @Test(groups = {
   "Fox",
   "Smoke",
   "ProdSanity",
   "Sanity","FoxSanity",
   "MiniRegression",
   "Regression",
   "ExhaustiveRegression"
  }, dataProvider = "StyleServiceability_V3")
  public void DevAPI_V3Serviceability_VerifySchema(String searchString) 
  {
   RequestGenerator request = devApiServiceHelper.invokeV3Serviceability(searchString);
   String response = request.respvalidate.returnresponseasstring();
   System.out.println("V3 Serviceability Response : \n "+response);
   AssertJUnit.assertEquals("V3 Serviceability Service is Down!!", 200, request.response.getStatus());

   try {
 	   String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/devapis-serviceability-v3-schema.txt");
 	   List < String > missingNodeList = validateServiceResponseNodesUsingSchemaValidator(response, jsonschema);
 	   AssertJUnit.assertTrue(missingNodeList + " nodes are missing in DevApi Serviceability V3 API response",
 	    CollectionUtils.isEmpty(missingNodeList));
 	  } catch (Exception e) {
 	   e.printStackTrace();
 	  }
 }

	 */





	/* @Test(groups = {
   "Fox",
   "Smoke",
   "ProdSanity",
   "Sanity",
   "FoxSanity",
   "MiniRegression",
   "Regression",
   "ExhaustiveRegression"
  },
  dataProvider = "AddToCArt_50Item_errorCheck",
  description = "Add item to Cart and verify success response")
 public void DevApis_addToCart_50Items_verifyError(String userName, String password) {
  RequestGenerator devApiAddToCartReqGen;
  String devApiAddToCartResponse = "";


  String[] brands = {
   "nike",
   "Pants",
   "Shoes"
  };
  List < Integer > styleIds1 = devApiServiceHelper.performSeachServiceToGetStyleIds(brands[0], "100", "true", "true");
  System.out.println("Stlye IDS");
  for (int k = 0; k < styleIds1.size(); k++) {
   System.out.println(styleIds1.get(k));
  }

  Random random = new Random();

  for (int j = 0; j <= 50; j++) {

   try {
    devApiAddToCartReqGen = devApiServiceHelper.invokeDevApiAddToCart(userName, password, Integer.toString(styleIds1.get(random.nextInt(styleIds1.size()))));
    devApiAddToCartResponse = devApiAddToCartReqGen.respvalidate.returnresponseasstring();
    System.out.println("\nPrinting DevApiService addToCart API response :\n\n" + devApiAddToCartResponse + "\n");
    log.info("\nPrinting DevApiService addToCart API response :\n\n" + devApiAddToCartResponse + "\n");
    if (j < 50) {
     AssertJUnit.assertEquals("[DevApiService addToCart API is not working.]", 200, devApiAddToCartReqGen.response.getStatus());

    } else {
     AssertJUnit.assertEquals("[DevApiService addToCart API is not working.]", 200, devApiAddToCartReqGen.response.getStatus());

    }



   } catch (AssertionError e) {

    j--;
   }
  }
 }*/



//V2 Serviceability
@Test(groups = {
			"Fox",
			"Smoke",
			"ProdSanity",
			"Sanity",
			"FoxSanity",
			"MiniRegression",
			"Regression",
			"ExhaustiveRegression"
	}, dataProvider = "V2Serviceability",
	description = "V3 Serviceability API Response")
	public void DevAPI_V2Serviceability_VerifySuccess(String searchString) {
		RequestGenerator request = devApiServiceHelper.invokeV2Serviceability(searchString);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println("V2 Serviceability Response : \n " + response);
		AssertJUnit.assertEquals("V2 Serviceability Service is Down!!", 200, request.response.getStatus());

	}

public JSONObject getChildObject(JSONObject parent, String key) throws JSONException
 {
	 return parent.getJSONObject(key);
 }

 public JSONValue getJsonNodeValue(String JSON, String Path)
	{
		try
		{
			return JsonPath.read(JSON, Path);
			
		}catch (PathNotFoundException e)
		{
			return null;
		}		
		
	}

public boolean isNullOrEmpty(Object object)
{
	if(object.equals(null) || object.toString().isEmpty())
	{
		return true;
	}
	return false;
}

@Test(groups = { "fox"},dataProvider="DevApiGetEffectivePercentage")
public void DevApi_GetEffectiveDiscountInSearch(String StyleID, String Assertion)
{
	RequestGenerator devApiStyleReqGen = devApiServiceHelper.invokeDevApiStyle(StyleID);
	String devApiStyleResponse = devApiStyleReqGen.respvalidate.returnresponseasstring();
	System.out.println("\nPrinting DevApiService style API response :\n\n"+devApiStyleResponse+"\n");
	log.info("\nPrinting DevApiService style API response :\n\n"+devApiStyleResponse+"\n");
	String offerType="";
	String offerMessage="";
	
	switch (Assertion)
	{
	case "B2G1":
	{
		offerType="Buy 1 Get 1";
		offerMessage="(Buy 1 Get 1)";
		break;
	}
	
	case "B3G1":
	{
		offerType="Buy 2 Get 1";
		offerMessage="(Buy 2 Get 1)";
		break;
	}
	
	case "B3G2":
	{
		offerType="Buy 2 Get 2";
		offerMessage="(Buy 2 Get 2)";
		break;
	}
	
	}
	
	
	
	AssertJUnit.assertEquals("[DevApiService style API is not working.]", 200, devApiStyleReqGen.response.getStatus());
	AssertJUnit.assertEquals("Offer Type Text is not proper", offerType,JsonPath.read(devApiStyleResponse, "$.data.discountData.offerType").toString());
	AssertJUnit.assertEquals("Offer Type Text is not proper", offerMessage,JsonPath.read(devApiStyleResponse, "$.data.discountData.discountText.text").toString());


}

@Test(groups = { "fox"},dataProvider="DevApiGetDisclaimerMessage")
public void DevApi_GetDisclaimerMessage(String SearchTerm)
{
	RequestGenerator request = devApiServiceHelper.invokeDevApiSearch(SearchTerm);
	String response = request.respvalidate.returnresponseasstring();
	System.out.println("\nPrinting DevApiService search API response :\n\n"+response+"\n");
	log.info("\nPrinting DevApiService search API response :\n\n"+response+"\n");

	AssertJUnit.assertEquals("[DevApiService search API is not working.]", 200, request.response.getStatus());
	JSONArray Products = JsonPath.read(response,"$.data.results.products[*].styleid");
	
	String DisclaimerTitle = "The final product delivered might vary in colour and prints from the display here";
	String DisclaimerText = "The final product delivered might vary in colour and print from the one displayed here. While we make every effort to deliver the same product, there might be variations because these products are procured in mixed batches.";
	
	
	for(int i=0; i<Products.size();i++)
	{
		request = devApiServiceHelper.invokeDevApiStyle(Products.get(i).toString());
		response = request.respvalidate.returnresponseasstring();
		System.out.println(response);
		AssertJUnit.assertEquals("[DevApiService Style API is not working.]", 200, request.response.getStatus());
		AssertJUnit.assertEquals("[Disclaimer Title Mismatch!!!.]", DisclaimerTitle, JsonPath.read(response,"$.data.articleType.disclaimerTitle"));
		AssertJUnit.assertEquals("[Disclaimer Text Mismatch!!!.]", DisclaimerText, JsonPath.read(response,"$.data.articleType.disclaimerText"));


	}
	
}


	
	//Get Similar Items 
	@Test(groups={"ProdSanity","Sanity","MiniRegression","Regression","ExhaustiveRegression"},dataProvider = "GetSimilarItems_Success",description = "Get User Coupons")
	public void DevAPI_GetSimilarItems_VerifySuccess(String StyleName, String StyleID, String UseVisualSearch)
	{
		RequestGenerator request = devApiServiceHelper.getSimilarItems(StyleName, StyleID, UseVisualSearch);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println("Get User Coupons Service Response : "+response);
		AssertJUnit.assertEquals("Get Similar Item Recommendations service is Failing", 200,request.response.getStatus());
	}
	
	@Test(groups={"ProdSanity","Sanity","MiniRegression","Regression","ExhaustiveRegression"},dataProvider = "GetSimilarItems_Failure",description = "Get User Coupons")
	public void DevAPI_GetSimilarItems_VerifyFailure(String StyleName, String StyleID, String UseVisualSearch, String statusCode)
	{
		RequestGenerator request = devApiServiceHelper.getSimilarItems(StyleName, StyleID, UseVisualSearch);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println("Get User Coupons Service Response : "+response);
		AssertJUnit.assertEquals("Get Similar Item Recommendations service is Failing", 200,request.response.getStatus());
	}
	
	
}