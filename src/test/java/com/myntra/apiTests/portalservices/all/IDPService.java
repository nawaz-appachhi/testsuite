package com.myntra.apiTests.portalservices.all;

import java.util.Calendar;
import java.util.List;

import com.myntra.apiTests.dataproviders.IDPServiceDP;
import com.myntra.apiTests.portalservices.idpservice.IDPServiceHelper;
import com.myntra.apiTests.portalservices.idpservice.UserLoginNodes;
import com.myntra.apiTests.portalservices.commons.StatusNodes;
import com.myntra.apiTests.portalservices.idpservice.IDPServiceConstants;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import com.myntra.apiTests.portalservices.idpservice.IDPServiceDataProviderEnum;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.Toolbox;
import com.myntra.lordoftherings.gandalf.RequestGenerator;

/**
 * @author shankara.c
 *
 */
public class IDPService extends IDPServiceDP
{
	static Logger log = Logger.getLogger(IDPService.class);
	static Initialize init = new Initialize("/Data/configuration");
	static IDPServiceHelper idpServiceHelper = new IDPServiceHelper();
	
	/**
	 * This Test Case is used to invoke signIn API and verification for success response (200)
	 * 
	 * @param userName
	 * @param password
	 * @param successRespCode
	 */
	@Test(groups = { "Smoke","FoxSanity", "Sanity", "ProdSanity", "Regression", "MiniRegression", "ExhaustiveRegression" }, 
			dataProvider = "IDPServiceDP_signInDataProvider_verifyAPIResponse",description="1. Hit IDP Service Sign in API with valid combinations of username and password. \n 2. Verify 200 response from service")
	public void IDPService_signIn_verifySuccessResponse(String userName, String password, String successRespCode)
	{
		long startTime = Calendar.getInstance().getTimeInMillis();
		RequestGenerator signInReqGen = idpServiceHelper.performSignInOperation(userName, password);
		System.out.println("\nPrinting IDPService signIn API response :\n\n"+signInReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting IDPService signIn API response :\n\n"+signInReqGen.respvalidate.returnresponseasstring());
		 
		AssertJUnit.assertEquals("IDPService signIn API is not working", 
				Integer.parseInt(successRespCode), signInReqGen.response.getStatus());
		
		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime)/1000.0;
		
		System.out.println("\nTime taken to execute - TestCase - IDPService_signIn_verifySuccessResponse : "+timeTaken+" seconds\n");
		log.info("\nTime taken to execute - TestCase - IDPService_signIn_verifySuccessResponse : "+timeTaken+" seconds\n");
	}
	
	/**
	 * This TestCase is used to invoke signIn API and verification for success status response
	 * 
	 * @param userName
	 * @param password
	 * @param successStatusCode
	 * @param successStatusMsg
	 * @param successStatusType
	 */
	@Test(groups = { "Sanity", "ProdSanity", "Regression", "MiniRegression", "ExhaustiveRegression" }, 
			dataProvider = "IDPServiceDP_signInDataProvider_positiveCases",description="1. Hit IDP Service Sign in API with valid combinations of username and password. \n 2. Verify status code, status message and status response")
	public void IDPService_signIn_verifySuccessStatusResponse(String userName, String password, String successStatusCode, String successStatusMsg, 
			String successStatusType)
	{
		long startTime = Calendar.getInstance().getTimeInMillis();
		RequestGenerator signInReqGen = idpServiceHelper.performSignInOperation(userName, password);
		System.out.println("\nPrinting IDPService signIn API response :\n\n"+signInReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting IDPService signIn API response :\n\n"+signInReqGen.respvalidate.returnresponseasstring());
		
		AssertJUnit.assertTrue("IDPService signIn API response status code is not a success code", 
				signInReqGen.respvalidate.NodeValue(StatusNodes.STATUS_CODE.toString(), true).contains(successStatusCode));
		
		AssertJUnit.assertTrue("IDPService signIn API response status message is not a success message",
				signInReqGen.respvalidate.NodeValue(StatusNodes.STATUS_MESSAGE.toString(), true).contains(successStatusMsg));
		 
		AssertJUnit.assertTrue("IDPService signIn API response status type is not a success type",
				signInReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(successStatusType));
		 
		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime)/1000.0;
		
		System.out.println("\nTime taken to execute - TestCase - IDPService_signIn_verifySuccessStatusResponse : "+timeTaken+" seconds\n");
		log.info("\nTime taken to execute - TestCase - IDPService_signIn_verifySuccessStatusResponse : "+timeTaken+" seconds\n");
	}
	
	/**
	 * This TestCase is used to invoke signIn API and verification for failure status response
	 * 
	 * @param userName
	 * @param password
	 * @param failureStatusCode
	 * @param failureStatusMsg
	 * @param failureStatusType
	 */
	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression" }, 
			dataProvider = "IDPServiceDP_signInDataProvider_negetiveCases",description="1. Hit IDP Service Sign in API with invalid combinations of username and password. \n 2. Verify failure status code, failure status message and failure status types")
	public void IDPService_signIn_verifyFailureStatusResponse(String userName, String password, String failureStatusCode, String failureStatusMsg,
			String failureStatusType)
	{
		long startTime = Calendar.getInstance().getTimeInMillis();
		RequestGenerator signInReqGen = idpServiceHelper.performSignInOperation(userName, password);
		System.out.println("\nPrinting IDPService signIn API response :\n\n"+signInReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting IDPService signIn API response :\n\n"+signInReqGen.respvalidate.returnresponseasstring());
		
		AssertJUnit.assertTrue("IDPService signIn API response status code is not a failure code", 
				signInReqGen.respvalidate.NodeValue(StatusNodes.STATUS_CODE.toString(), true).contains(failureStatusCode));	
		
		AssertJUnit.assertTrue("IDPService signIn API response status message is not a failure message",
				signInReqGen.respvalidate.NodeValue(StatusNodes.STATUS_MESSAGE.toString(), true).contains(failureStatusMsg));
		 
		AssertJUnit.assertTrue("IDPService signIn API response status type is not a failure type",
				signInReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(failureStatusType));
		 
		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime)/1000.0;
		
		System.out.println("\nTime taken to execute - TestCase - IDPService_signIn_verifyFailureStatusResponse : "+timeTaken+" seconds\n");
		log.info("\nTime taken to execute - TestCase - IDPService_signIn_verifyFailureStatusResponse : "+timeTaken+" seconds\n");
	}

	
	/**
	 * 	This TestCase is used to invoke signIn API and verification for user login nodes
	 * 
	 * @param userName
	 * @param password
	 * @param failureStatusCode
	 * @param failureStatusMsg
	 * @param failureStatusType
	 */
	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression" }, 
			dataProvider = "IDPServiceDP_signInDataProvider_verifyUserLoginNodes",description="1. Hit IDP Service Sign in API with valid combinations of username and password. \n 2. Verify user email and user mobile nodes in response")
	public void IDPService_signIn_verifyUserLoginNodes(String userName, String password, String validMobLength)
	{
		long startTime = Calendar.getInstance().getTimeInMillis();
		RequestGenerator signInReqGen = idpServiceHelper.performSignInOperation(userName, password);
		System.out.println("\nPrinting IDPService signIn API response :\n\n"+signInReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting IDPService signIn API response :\n\n"+signInReqGen.respvalidate.returnresponseasstring());
		
		AssertJUnit.assertTrue("Error while invoking IDPService signIn API",
				signInReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(),
						true).contains(IDPServiceConstants.PROPERTY_READER.getPropertyValue(IDPServiceDataProviderEnum.SUCCESS_STATUS_TYPE.name())));
		
		System.out.println(UserLoginNodes.getUserLoginNodes());
		AssertJUnit.assertTrue("UserLogin nodes are incomplete", 
				signInReqGen.respvalidate.DoesNodesExists(UserLoginNodes.getUserLoginNodes()));
		
		AssertJUnit.assertEquals("user email and login are not same", 
				signInReqGen.respvalidate.NodeValue(UserLoginNodes.USER_EMAIL.getNodePath(), false).replace("\"", "").trim(),userName);
		
		AssertJUnit.assertEquals("Invalid user mobile number", 
				signInReqGen.respvalidate.NodeValue(UserLoginNodes.USER_MOBILE.getNodePath(), false).length(),  Integer.parseInt(validMobLength));
		
		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime)/1000.0;
		
		System.out.println("\nTime taken to execute - TestCase - IDPService_signIn_verifyUserLoginNodes : "+timeTaken+" seconds\n");
		log.info("\nTime taken to execute - TestCase - IDPService_signIn_verifyUserLoginNodes : "+timeTaken+" seconds\n");
	}
	
	/**
	 * 	This Test Case is used to invoke signUp API and verification for success response (200) 
	 * 
	 * @param referer
	 * @param login
	 * @param password
	 * @param userType
	 * @param gender
	 * @param title
	 * @param firstName
	 * @param lastName
	 * @param emailId
	 * @param mobileNumber
	 * @param successRespCode
	 */
	@Test(groups = { "Smoke", "FoxSanity","Sanity", "ProdSanity", "Regression", "MiniRegression", "ExhaustiveRegression" }, 
			dataProvider = "IDPServiceDP_signUpDataProvider_verifyAPIResponse",description="1. Hit IDP Service Sign up API with valid combinations of username and password. \n 2. Verify 200 response from service")
	public void IDPService_signUp_verifySuccessResponse(String login, String password, String userType, String gender, String title, String firstName, 
			String lastName, String emailId, String mobileNumber, String successRespCode)
	{
		long startTime = Calendar.getInstance().getTimeInMillis();
		RequestGenerator signUpReqGen = idpServiceHelper.performSignUpOperation(login, password, userType, gender, title, firstName, lastName, emailId, mobileNumber);
		System.out.println("\nPrinting IDPService signUp API response :\n\n"+signUpReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting IDPService signUp API response :\n\n"+signUpReqGen.respvalidate.returnresponseasstring());
		
		AssertJUnit.assertEquals("IDPService signUp API is not working", 
				Integer.parseInt(successRespCode), signUpReqGen.response.getStatus());
		
		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime)/1000.0;
		
		System.out.println("\nTime taken to execute - TestCase - IDPService_signUp_verifySuccessResponse : "+timeTaken+" seconds\n");
		log.info("\nTime taken to execute - TestCase - IDPService_signUp_verifySuccessResponse : "+timeTaken+" seconds\n");
	}
	
	/**
	 * This TestCase is used to invoke signUp API and verification for success status response
	 * 
	 * @param referer
	 * @param login
	 * @param password
	 * @param userType
	 * @param gender
	 * @param title
	 * @param firstName
	 * @param lastName
	 * @param emailId
	 * @param mobileNumber
	 * @param successStatusCode
	 * @param successStatusMsg
	 * @param successStatusType
	 */
	@Test(groups = { "Sanity", "ProdSanity", "Regression", "MiniRegression", "ExhaustiveRegression" }, 
			dataProvider = "IDPServiceDP_signUpDataProvider_positiveCases",description="1. Hit IDP Service Sign up API with valid combinations of username and password. \n 2. Verify success staus code, success status message, success status type")
	public void IDPService_signUp_verifySuccessStatusResponse(String login, String password, String userType, String gender, String title, String firstName, 
			String lastName, String emailId, String mobileNumber, String successStatusCode, String successStatusMsg, String successStatusType)
	{
		long startTime = Calendar.getInstance().getTimeInMillis();
		RequestGenerator signUpReqGen = idpServiceHelper.performSignUpOperation(login, password, userType, gender, title, firstName, lastName, emailId, mobileNumber);
		System.out.println("\nPrinting IDPService signUp API response :\n\n"+signUpReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting IDPService signUp API response :\n\n"+signUpReqGen.respvalidate.returnresponseasstring());
		
		AssertJUnit.assertTrue("IDPService signUp API response status code is not a success code", 
				signUpReqGen.respvalidate.NodeValue(StatusNodes.STATUS_CODE.toString(), true).contains(successStatusCode));	
		
		AssertJUnit.assertTrue("IDPService signUp API response status message is not a success message",
				signUpReqGen.respvalidate.NodeValue(StatusNodes.STATUS_MESSAGE.toString(), true).contains(successStatusMsg));
		 
		AssertJUnit.assertTrue("IDPService signUp API response status type is not a success type",
				signUpReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(successStatusType));
		
		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime)/1000.0;
		
		System.out.println("\nTime taken to execute - TestCase - IDPService_signUp_verifySuccessStatusResponse : "+timeTaken+" seconds\n");
		log.info("\nTime taken to execute - TestCase - IDPService_signUp_verifySuccessStatusResponse : "+timeTaken+" seconds\n");
	}

	
	/**
	 * This TestCase is used to invoke signUp API and verification for failure status response
	 * 
	 * @param referer
	 * @param login
	 * @param password
	 * @param userType
	 * @param gender
	 * @param title
	 * @param firstName
	 * @param lastName
	 * @param emailId
	 * @param mobileNumber
	 * @param failureStatusCode
	 * @param failureStatusMsg
	 * @param failureStatusType
	 */
	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression", "NULLWORKS" }, 
			dataProvider = "IDPServiceDP_signUpDataProvider_negetiveCases",description="1. Hit IDP Service Sign up API with invalid combinations of username and password. \n 2. Verify failure staus code, failure status message, failure status type")
	public void IDPService_signUp_verifyFailureStatusResponse(String login, String password, String userType, String gender, String title, String firstName, 
			String lastName, String emailId, String mobileNumber, String failureStatusCode, String failureStatusMsg, String failureStatusType)
	{
		long startTime = Calendar.getInstance().getTimeInMillis();
		RequestGenerator signUpReqGen = idpServiceHelper.performSignUpOperation(login, password, userType, gender, title, firstName, lastName, 
				emailId, mobileNumber);
		System.out.println("\nPrinting IDPService signUp API response :\n\n"+signUpReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting IDPService signUp API response :\n\n"+signUpReqGen.respvalidate.returnresponseasstring());
		
		AssertJUnit.assertTrue("IDPService signUp API response status code is not a failure code", 
				signUpReqGen.respvalidate.NodeValue(StatusNodes.STATUS_CODE.toString(), true).contains(failureStatusCode));	
		
		AssertJUnit.assertTrue("IDPService signUp API response status message is not a failure message",
				signUpReqGen.respvalidate.NodeValue(StatusNodes.STATUS_MESSAGE.toString(), true).contains(failureStatusMsg));
		 
		AssertJUnit.assertTrue("IDPService signUp API response status type is not a failure type",
				signUpReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(failureStatusType));
		
		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime)/1000.0;
		
		System.out.println("\nTime taken to execute - TestCase - IDPService_signUp_verifyFailureStatusResponse : "+timeTaken+" seconds\n");
		log.info("\nTime taken to execute - TestCase - IDPService_signUp_verifyFailureStatusResponse : "+timeTaken+" seconds\n");
	}
	
	/**
	 * This TestCase is used to invoke signUp API and verification for user login nodes
	 * 
	 * @param referer
	 * @param login
	 * @param password
	 * @param userType
	 * @param gender
	 * @param title
	 * @param firstName
	 * @param lastName
	 * @param emailId
	 * @param mobileNumber
	 * @param successStatusType
	 * @param validMobLength
	 */
	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression" }, 
			dataProvider = "IDPServiceDP_signUpDataProvider_verifyUserLoginNodes",description="1. Hit IDP Service Sign up API with valid combinations of username and password. \n 2. Verify user login and user mobile fields in response")
	public void IDPService_signUp_verifyUserLoginNodes(String login, String password, String userType, String gender, String title, String firstName, 
			String lastName, String emailId, String mobileNumber, String successStatusType, String validMobLength)
	{
		long startTime = Calendar.getInstance().getTimeInMillis();
		RequestGenerator signUpReqGen = idpServiceHelper.performSignUpOperation(login, password, userType, gender, title, firstName, lastName, emailId, mobileNumber);
		System.out.println("\nPrinting IDPService signUp API response :\n\n"+signUpReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting IDPService signUp API response :\n\n"+signUpReqGen.respvalidate.returnresponseasstring());
		
		AssertJUnit.assertTrue("Error while invoking IDPService signUp API",
				signUpReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(successStatusType));
		
		AssertJUnit.assertTrue("UserLogin nodes are incomplete", signUpReqGen.respvalidate.DoesNodesExists(UserLoginNodes.getUserLoginNodes()));
		
		AssertJUnit.assertEquals("user email and login are not same", 
				signUpReqGen.respvalidate.NodeValue(UserLoginNodes.USER_EMAIL.getNodePath(), false).replace("\"", "").trim(),emailId);
		
		AssertJUnit.assertEquals("Invalid user mobile number", 
				signUpReqGen.respvalidate.NodeValue(UserLoginNodes.USER_MOBILE.getNodePath(), false).length(), Integer.parseInt(validMobLength));
		
		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime)/1000.0;
		
		System.out.println("\nTime taken to execute - TestCase - IDPService_signUp_verifyUserLoginNodes : "+timeTaken+" seconds\n");
		log.info("\nTime taken to execute - TestCase - IDPService_signUp_verifyUserLoginNodes : "+timeTaken+" seconds\n");
	}
	
	/**
	 * This TestCase is used to invoke signOut API and verification for success response code(200)
	 * 
	 * @param userName
	 * @param password
	 * @param successRespCode
	 */
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression", "MiniRegression", "ExhaustiveRegression" }, 
			dataProvider = "IDPServiceDP_signOutDataProvider_verifyAPIResponse",description="1.Peform signin operation with valid user id and passsword. \n 2.Hit IDP Service Sign out API \n 3. Verify 200 response code")
	public void IDPService_signOut_verifySuccessResponse(String userName, String password, String successRespCode)
	{
		long startTime = Calendar.getInstance().getTimeInMillis();
		RequestGenerator signInReqGen = idpServiceHelper.performSignInOperation(userName, password);
		System.out.println("\nPrinting IDPService signIn API response :\n\n"+signInReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting IDPService signIn API response :\n\n"+signInReqGen.respvalidate.returnresponseasstring());
		
		AssertJUnit.assertTrue("Error while invoking IDPService signIn API",
				signInReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), false).contains(IDPServiceConstants.SUCCESS_STATUS_TYPE));
		
		RequestGenerator signOutReqGen = idpServiceHelper.performSignOutOperation(userName, signInReqGen);
		System.out.println("\nPrinting IDPService signOut API response :\n\n"+signOutReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting IDPService signOut API response :\n\n"+signOutReqGen.respvalidate.returnresponseasstring());
		 
		AssertJUnit.assertEquals("IDPService signOut API is not working", 
				Integer.parseInt(successRespCode), signOutReqGen.response.getStatus());
		
		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime)/1000.0;
		
		System.out.println("\nTime taken to execute - TestCase - IDPService_signOut_verifySuccessResponse : "+timeTaken+" seconds\n");
		log.info("\nTime taken to execute - TestCase - IDPService_signOut_verifySuccessResponse : "+timeTaken+" seconds\n");
	}
	
	/**
	 * This TestCase is used to invoke signOut API and verification for success status response
	 * 
	 * @param userName
	 * @param password
	 * @param successStatusCode
	 * @param successStatusMsg
	 * @param successStatusType
	 */
	@Test(groups = { "Sanity", "ProdSanity", "Regression", "MiniRegression", "ExhaustiveRegression" }, 
			dataProvider = "IDPServiceDP_signOutDataProvider_positiveCases",description="1.Peform signin operation with valid user id and passsword. \n 2.Hit IDP Service Sign out API \n 3. Verify success status code, success status message and success status type")
	public void IDPService_signOut_verifySuccessStatusResponse(String userName, String password, String successStatusCode, String successStatusMsg, String successStatusType)
	{
		long startTime = Calendar.getInstance().getTimeInMillis();
		RequestGenerator signInReqGen = idpServiceHelper.performSignInOperation(userName, password);
		System.out.println("\nPrinting IDPService signIn API response :\n\n"+signInReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting IDPService signIn API response :\n\n"+signInReqGen.respvalidate.returnresponseasstring());
		
		AssertJUnit.assertTrue("Error while invoking IDPService signIn API",
				signInReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), false).contains(IDPServiceConstants.SUCCESS_STATUS_TYPE));
		
		RequestGenerator signOutReqGen = idpServiceHelper.performSignOutOperation(userName, signInReqGen);
		System.out.println("\nPrinting IDPService signOut API response :\n\n"+signOutReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting IDPService signOut API response :\n\n"+signOutReqGen.respvalidate.returnresponseasstring());
		 
		AssertJUnit.assertTrue("IDPService signOut API response status code is not a success code", 
				signOutReqGen.respvalidate.NodeValue(StatusNodes.STATUS_CODE.toString(), true).contains(successStatusCode));	
		
		AssertJUnit.assertTrue("IDPService signOut API response status message is not a success message",
				signOutReqGen.respvalidate.NodeValue(StatusNodes.STATUS_MESSAGE.toString(),	true).contains(successStatusMsg));
		 
		AssertJUnit.assertTrue("IDPService signOut API response status type is not a success type",
				signOutReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(successStatusType));
		
		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime)/1000.0;
		
		System.out.println("\nTime taken to execute - TestCase - IDPService_signOut_verifySuccessStatusResponse : "+timeTaken+" seconds\n");
		log.info("\nTime taken to execute - TestCase - IDPService_signOut_verifySuccessStatusResponse : "+timeTaken+" seconds\n");
	}
	
	/**
	 * This TestCase is used to invoke forgotPassword API and verification for success response(200)
	 * 
	 * @param login
	 * @param successRespCode
	 */
	@Test(groups = { "" }, 
			dataProvider = "IDPServiceDP_forgotPasswordDataProvider_verifyAPIResponse")
	public void IDPService_forgotPassword_verifySuccessResponse(String login, String successRespCode)
	{
		long startTime = Calendar.getInstance().getTimeInMillis();
		RequestGenerator forgotPasswordReqGen = idpServiceHelper.performForgotPasswordOperation(login);
		System.out.println("\nPrinting IDPService forgotPassword API response :\n\n"+forgotPasswordReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting IDPService forgotPassword API response :\n\n"+forgotPasswordReqGen.respvalidate.returnresponseasstring());
		 
		AssertJUnit.assertEquals("IDPService forgotPassword API is not working", 
				Integer.parseInt(successRespCode), forgotPasswordReqGen.response.getStatus());
		
		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime)/1000.0;
		
		System.out.println("\nTime taken to execute - TestCase - IDPService_forgotPassword_verifySuccessResponse : "+timeTaken+" seconds\n");
		log.info("\nTime taken to execute - TestCase - IDPService_forgotPassword_verifySuccessResponse : "+timeTaken+" seconds\n");
	}
	
	/**
	 * This TestCase is used to invoke forgotPassword API and verification for success status response
	 * 
	 * @param login
	 * @param successStatusCode
	 * @param successStatusMsg
	 * @param successStatusType
	 */
	@Test(groups = { "" }, 
			dataProvider = "IDPServiceDP_forgotPasswordDataProvider_positiveCases")
	public void IDPService_forgotPassword_verifySuccessStatusResponse(String login, String successStatusCode, String successStatusMsg, String successStatusType)
	{
		long startTime = Calendar.getInstance().getTimeInMillis();
		RequestGenerator forgotPasswordReqGen = idpServiceHelper.performForgotPasswordOperation(login);
		System.out.println("\nPrinting IDPService forgotPassword API response :\n\n"+forgotPasswordReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting IDPService forgotPassword API response :\n\n"+forgotPasswordReqGen.respvalidate.returnresponseasstring());
		 
		AssertJUnit.assertTrue("IDPService forgotPassword API response status code is not a success code", 
				forgotPasswordReqGen.respvalidate.NodeValue(StatusNodes.STATUS_CODE.toString(), true).contains(successStatusCode));	
		
		AssertJUnit.assertTrue("IDPService forgotPassword API response status message is not a success message",
				forgotPasswordReqGen.respvalidate.NodeValue(StatusNodes.STATUS_MESSAGE.toString(), true).contains(successStatusMsg));
		 
		AssertJUnit.assertTrue("IDPService forgotPassword API response status type is not a success type",
				forgotPasswordReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(successStatusType));
		
		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime)/1000.0;
		
		System.out.println("\nTime taken to execute - TestCase - IDPService_forgotPassword_verifySuccessStatusResponse : "+timeTaken+" seconds\n");
		log.info("\nTime taken to execute - TestCase - IDPService_forgotPassword_verifySuccessStatusResponse : "+timeTaken+" seconds\n");
	}
	
	/**
	 * This TestCase is used to invoke forgotPassword API and verification for failure status response
	 * 
	 * @param login
	 * @param failureStatusCode
	 * @param failureStatusMsg
	 * @param failureStatusType
	 */
	@Test(groups = { "" }, 
			dataProvider = "IDPServiceDP_forgotPasswordDataProvider_negetiveCases")
	public void IDPService_forgotPassword_verifyFailureStatusResponse(String login, String failureStatusCode, String failureStatusMsg, String failureStatusType)
	{
		long startTime = Calendar.getInstance().getTimeInMillis();
		RequestGenerator forgotPasswordReqGen = idpServiceHelper.performForgotPasswordOperation(login);
		System.out.println("\nPrinting IDPService forgotPassword API response :\n\n"+forgotPasswordReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting IDPService forgotPassword API response :\n\n"+forgotPasswordReqGen.respvalidate.returnresponseasstring());
		 
		AssertJUnit.assertTrue("IDPService forgotPassword API response status code is not a failure code", 
				forgotPasswordReqGen.respvalidate.NodeValue(StatusNodes.STATUS_CODE.toString(), true).contains(failureStatusCode));	
		
		AssertJUnit.assertTrue("IDPService forgotPassword API response status message is not a failure message",
				forgotPasswordReqGen.respvalidate.NodeValue(StatusNodes.STATUS_MESSAGE.toString(), true).contains(failureStatusMsg));
		 
		AssertJUnit.assertTrue("IDPService forgotPassword API response status type is not a failure type",
				forgotPasswordReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(failureStatusType));
		
		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime)/1000.0;
		
		System.out.println("\nTime taken to execute - TestCase - IDPService_forgotPassword_verifySuccessStatusResponse : "+timeTaken+" seconds\n");
		log.info("\nTime taken to execute - TestCase - IDPService_forgotPassword_verifySuccessStatusResponse : "+timeTaken+" seconds\n");
	}
	
	
	/**
	 * This TestCase is used to invoke resetPassword API and verification for success response(200)
	 * 
	 * @param login
	 * @param password
	 * @param resetToken
	 * @param successRespCode
	 */
	@Test(groups = { "" }, 
			dataProvider = "IDPServiceDP_resetPasswordDataProvider_verifyAPIResponse")
	public void IDPService_resetPassword_verifySuccessResponse(String login, String password, String envName, String successRespCode)
	{
		long startTime = Calendar.getInstance().getTimeInMillis();
		RequestGenerator forgotPasswordReqGen = idpServiceHelper.performForgotPasswordOperation(login);
		System.out.println("\nPrinting IDPService forgotPassword API response :\n\n"+forgotPasswordReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting IDPService forgotPassword API response :\n\n"+forgotPasswordReqGen.respvalidate.returnresponseasstring());
		 
		AssertJUnit.assertTrue("Error while invoking IDPService forgotPassword API",
				forgotPasswordReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(IDPServiceConstants.SUCCESS_STATUS_TYPE));
		
		String resetToken = idpServiceHelper.getResetTokenFromDatabase(login, envName);
		
		RequestGenerator resetPasswordReqGen = idpServiceHelper.performResetPasswordOperation(login, password, resetToken);
		System.out.println("\nPrinting IDPService resetPassword API response :\n\n"+resetPasswordReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting IDPService resetPassword API response :\n\n"+resetPasswordReqGen.respvalidate.returnresponseasstring());
		
		AssertJUnit.assertEquals("IDPService resetPassword API is not working", Integer.parseInt(successRespCode), resetPasswordReqGen.response.getStatus());
		
		RequestGenerator signInReqGen = idpServiceHelper.performSignInOperation(login, password);
		System.out.println("\nPrinting IDPService signIn API response :\n\n"+signInReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting IDPService signIn API response :\n\n"+signInReqGen.respvalidate.returnresponseasstring());
		 
		AssertJUnit.assertTrue("Error while invoking IDPService signIn API",
				signInReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(IDPServiceConstants.SUCCESS_STATUS_TYPE));
		
		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime)/1000.0;
		
		System.out.println("\nTime taken to execute - TestCase - IDPService_resetPassword_verifySuccessResponse : "+timeTaken+" seconds\n");
		log.info("\nTime taken to execute - TestCase - IDPService_resetPassword_verifySuccessResponse : "+timeTaken+" seconds\n");
	}
	
	
	/**
	 * This TestCase is used to invoke resetPassword API and verification for success status response
	 * 
	 * @param login
	 * @param password
	 * @param resetToken
	 * @param successStatusCode
	 * @param successStatusMsg
	 * @param successStatusType
	 */
	@Test(groups = { "" }, 
			dataProvider = "IDPServiceDP_resetPasswordDataProvider_positiveCases")
	public void IDPService_resetPassword_verifySuccessStatusResponse(String login, String password, String envName, String successStatusCode, String successStatusMsg, 
			String successStatusType)
	{
		long startTime = Calendar.getInstance().getTimeInMillis();
		RequestGenerator forgotPasswordReqGen = idpServiceHelper.performForgotPasswordOperation(login);
		System.out.println("\nPrinting IDPService forgotPassword API response :\n\n"+forgotPasswordReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting IDPService forgotPassword API response :\n\n"+forgotPasswordReqGen.respvalidate.returnresponseasstring());
		 
		AssertJUnit.assertTrue("Error while invoking IDPService forgotPassword API",
				forgotPasswordReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(IDPServiceConstants.SUCCESS_STATUS_TYPE));
		
		String resetToken = idpServiceHelper.getResetTokenFromDatabase(login, envName);
		
		RequestGenerator resetPasswordReqGen = idpServiceHelper.performResetPasswordOperation(login, password, resetToken);
		System.out.println("\nPrinting IDPService resetPassword API response :\n\n"+resetPasswordReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting IDPService resetPassword API response :\n\n"+resetPasswordReqGen.respvalidate.returnresponseasstring());
		
		AssertJUnit.assertTrue("IDPService resetPassword API response status code is not a success code", 
				resetPasswordReqGen.respvalidate.NodeValue(StatusNodes.STATUS_CODE.toString(), true).contains(successStatusCode));	
		
		AssertJUnit.assertTrue("IDPService resetPassword API response status message is not a success message",
				resetPasswordReqGen.respvalidate.NodeValue(StatusNodes.STATUS_MESSAGE.toString(), true).contains(successStatusMsg));
		 
		AssertJUnit.assertTrue("IDPService resetPassword API response status type is not a success type",
				resetPasswordReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(successStatusType));
		
		RequestGenerator signInReqGen = idpServiceHelper.performSignInOperation(login, password);
		System.out.println("\nPrinting IDPService signIn API response :\n\n"+signInReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting IDPService signIn API response :\n\n"+signInReqGen.respvalidate.returnresponseasstring());
		 
		AssertJUnit.assertTrue("Error while invoking IDPService signIn API",
				signInReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(IDPServiceConstants.SUCCESS_STATUS_TYPE));
		
		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime)/1000.0;
		
		System.out.println("\nTime taken to execute - TestCase - IDPService_resetPassword_verifySuccessStatusResponse : "+timeTaken+" seconds\n");
		log.info("\nTime taken to execute - TestCase - IDPService_resetPassword_verifySuccessStatusResponse : "+timeTaken+" seconds\n");
	}
	
	/**
	 * This TestCase is used to invoke resetPassword API and verification for failure status response
	 * 
	 * @param login
	 * @param password
	 * @param resetToken
	 * @param failureStatusCode
	 * @param failureStatusMsg
	 * @param failureStatusType
	 */
	@Test(groups = { "" }, 
			dataProvider = "IDPServiceDP_resetPasswordDataProvider_negetiveCases")
	public void IDPService_resetPassword_verifyFailureStatusResponse(String login, String password, String resetToken,  String failureStatusCode, 
			String failureStatusMsg, String failureStatusType)
	{
		long startTime = Calendar.getInstance().getTimeInMillis();
		RequestGenerator resetPasswordReqGen = idpServiceHelper.performResetPasswordOperation(login, password, resetToken);
		System.out.println("\nPrinting IDPService resetPassword API response :\n\n"+resetPasswordReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting IDPService resetPassword API response :\n\n"+resetPasswordReqGen.respvalidate.returnresponseasstring());
		
		AssertJUnit.assertTrue("IDPService resetPassword API response status code is not a failure code", 
				resetPasswordReqGen.respvalidate.NodeValue(StatusNodes.STATUS_CODE.toString(), true).contains(failureStatusCode));	
		
		AssertJUnit.assertTrue("IDPService resetPassword API response status message is not a failure message",
				resetPasswordReqGen.respvalidate.NodeValue(StatusNodes.STATUS_MESSAGE.toString(), true).contains(failureStatusMsg));
		 
		AssertJUnit.assertTrue("IDPService resetPassword API response status type is not a failure type",
				resetPasswordReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(failureStatusType));
		
		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime)/1000.0;
		
		System.out.println("\nTime taken to execute - TestCase - IDPService_resetPassword_verifyFailureStatusResponse : "+timeTaken+" seconds\n");
		log.info("\nTime taken to execute - TestCase - IDPService_resetPassword_verifyFailureStatusResponse : "+timeTaken+" seconds\n");
	}
	
	@Test(groups = { "SchemaValidation" }, dataProvider = "IDPServiceDP_signInDataProvider_verifyResponseDataNodesUsingSchemaValidations")
	public void IDPService_signIn_verifyResponseDataNodesUsingSchemaValidations(String userName, String password, String successRespCode)
	{
		RequestGenerator signInReqGen = idpServiceHelper.performSignInOperation(userName, password);
		String signInResponse = signInReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting IDPService signIn API response :\n\n"+signInResponse);
		log.info("\nPrinting IDPService signIn API response :\n\n"+signInResponse);
		
		AssertJUnit.assertEquals("IDPService signIn API is not working", Integer.parseInt(successRespCode), signInReqGen.response.getStatus());
		
		try {
            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/idpservice-signin-schema.txt");
            List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(signInResponse, jsonschema);
            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in IDPService signIn API response", CollectionUtils.isEmpty(missingNodeList));
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	@Test(groups = { "SchemaValidation" }, dataProvider = "IDPServiceDP_signUpDataProvider_verifyResponseDataNodesUsingSchemaValidations")
	public void IDPService_signUp_verifyResponseDataNodesUsingSchemaValidations(String login, String password, String userType, String gender, String title, 
			String firstName, String lastName, String emailId, String mobileNumber, String successRespCode)
	{
		RequestGenerator signUpReqGen = idpServiceHelper.performSignUpOperation(login, password, userType, gender, title, firstName, lastName, emailId, mobileNumber);
		String signUpResponse = signUpReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting IDPService signUp API response :\n\n"+signUpResponse);
		log.info("\nPrinting IDPService signUp API response :\n\n"+signUpResponse);
		
		AssertJUnit.assertEquals("IDPService signUp API is not working", Integer.parseInt(successRespCode), signUpReqGen.response.getStatus());
		
		try {
            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/idpservice-signup-schema.txt");
            List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(signUpResponse, jsonschema);
            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in IDPService signUp API response", CollectionUtils.isEmpty(missingNodeList));
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	@Test(groups = { "SchemaValidation" }, dataProvider = "IDPServiceDP_signOutDataProvider_verifyResponseDataNodesUsingSchemaValidations")
	public void IDPService_signOut_verifyResponseDataNodesUsingSchemaValidations(String userName, String password, String successRespCode)
	{
		RequestGenerator signInReqGen = idpServiceHelper.performSignInOperation(userName, password);
		System.out.println("\nPrinting IDPService signIn API response :\n\n"+signInReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting IDPService signIn API response :\n\n"+signInReqGen.respvalidate.returnresponseasstring());
		
		AssertJUnit.assertTrue("Error while invoking IDPService signIn API",
				signInReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), false).contains(IDPServiceConstants.SUCCESS_STATUS_TYPE));
		
		RequestGenerator signOutReqGen = idpServiceHelper.performSignOutOperation(userName, signInReqGen);
		String signOutResponse = signOutReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting IDPService signOut API response :\n\n"+signOutResponse);
		log.info("\nPrinting IDPService signOut API response :\n\n"+signOutResponse);
		 
		AssertJUnit.assertEquals("IDPService signOut API is not working",  Integer.parseInt(successRespCode), signOutReqGen.response.getStatus());
		
		try {
            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/idpservice-signout-schema.txt");
            List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(signOutResponse, jsonschema);
            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in IDPService signOut API response", CollectionUtils.isEmpty(missingNodeList));
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	@Test(groups = { "" }, dataProvider = "IDPServiceDP_forgotPasswordDataProvider_verifyResponseDataNodesUsingSchemaValidations")
	public void IDPService_forgotPassword_verifyResponseDataNodesUsingSchemaValidations(String login, String successRespCode)
	{
		RequestGenerator forgotPasswordReqGen = idpServiceHelper.performForgotPasswordOperation(login);
		String forgotPasswordResponse = forgotPasswordReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting IDPService forgotPassword API response :\n\n"+forgotPasswordResponse);
		log.info("\nPrinting IDPService forgotPassword API response :\n\n"+forgotPasswordResponse);
		 
		AssertJUnit.assertEquals("IDPService forgotPassword API is not working", Integer.parseInt(successRespCode), forgotPasswordReqGen.response.getStatus());
		
		try {
            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/idpservice-forgotpassword-schema.txt");
            List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(forgotPasswordResponse, jsonschema);
            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in IDPService forgotPassword API response", CollectionUtils.isEmpty(missingNodeList));
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	@Test(groups = { "" }, dataProvider = "IDPServiceDP_resetPasswordDataProvider_verifyResponseDataNodesUsingSchemaValidations")
	public void IDPService_resetPassword_verifyResponseDataNodesUsingSchemaValidations(String login, String password, String envName, String successRespCode)
	{
		RequestGenerator forgotPasswordReqGen = idpServiceHelper.performForgotPasswordOperation(login);
		System.out.println("\nPrinting IDPService forgotPassword API response :\n\n"+forgotPasswordReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting IDPService forgotPassword API response :\n\n"+forgotPasswordReqGen.respvalidate.returnresponseasstring());
		 
		AssertJUnit.assertTrue("Error while invoking IDPService forgotPassword API",
				forgotPasswordReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(IDPServiceConstants.SUCCESS_STATUS_TYPE));
		
		String resetToken = idpServiceHelper.getResetTokenFromDatabase(login, envName);
		
		RequestGenerator resetPasswordReqGen = idpServiceHelper.performResetPasswordOperation(login, password, resetToken);
		String resetPasswordResponse = resetPasswordReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting IDPService resetPassword API response :\n\n"+resetPasswordResponse);
		log.info("\nPrinting IDPService resetPassword API response :\n\n"+resetPasswordResponse);
		
		AssertJUnit.assertEquals("IDPService resetPassword API is not working", Integer.parseInt(successRespCode), resetPasswordReqGen.response.getStatus());
		
		try {
            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/idpservice-resetpassword-schema.txt");
            List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(resetPasswordResponse, jsonschema);
            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in IDPService resetPassword API response", CollectionUtils.isEmpty(missingNodeList));
        } catch (Exception e) {
            e.printStackTrace();
        }
		
		RequestGenerator signInReqGen = idpServiceHelper.performSignInOperation(login, password);
		System.out.println("\nPrinting IDPService signIn API response :\n\n"+signInReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting IDPService signIn API response :\n\n"+signInReqGen.respvalidate.returnresponseasstring());
		 
		AssertJUnit.assertTrue("Error while invoking IDPService signIn API",
				signInReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).contains(IDPServiceConstants.SUCCESS_STATUS_TYPE));
		
	}
}
