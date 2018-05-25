package com.myntra.apiTests.portalservices.appservices;

import java.util.List;

import com.myntra.apiTests.portalservices.commons.IServiceConstants;
import com.myntra.apiTests.portalservices.mobileappservices.MobileIdpServiceDataNodes;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import com.myntra.apiTests.InitializeFramework;
import com.myntra.apiTests.dataproviders.MobileIdpDP;
import com.myntra.apiTests.portalservices.mobileappservices.MobileIdpServiceHelper;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.MyntAssert;
import com.myntra.lordoftherings.Toolbox;
import com.myntra.lordoftherings.gandalf.RequestGenerator;

/**
 * @author shankara.c
 *
 */
public class MobileIdpTests extends MobileIdpDP 
{
	static Initialize init = InitializeFramework.init;
	static Logger log = Logger.getLogger(MobileIdpTests.class);
	static MobileIdpServiceHelper mobileIdpServiceHelper = new MobileIdpServiceHelper();
	
	/**
	 * This TestCase is used to invoke MobileIDPService signIn API and verification for success response
	 * 
	 * @param userName
	 * @param password
	 * @param action
	 * @param successRespCode
	 */
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression", "ExhaustiveRegression", "MiniRegression" }, 
			dataProvider = "MobileIdpDP_signInDataProvider_verifySuccessResponse")
	public void MobileIdpService_signIn_verifySuccessResponse(String userName, String password, String action, String successRespCode) 
	{
		RequestGenerator mobileIdpServiceSignInReqGen = mobileIdpServiceHelper.invokeMobileIdpSignInService(userName, password, action);
		String mobileIdpServiceSignInResponse = mobileIdpServiceSignInReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting MobileIDPService signIn API response :\n\n"+mobileIdpServiceSignInResponse);
		log.info("\nPrinting MobileIDPService signIn API response :\n\n"+mobileIdpServiceSignInResponse);
		 
		AssertJUnit.assertEquals("MobileIDPService signIn API is not working",  Integer.parseInt(successRespCode), mobileIdpServiceSignInReqGen.response.getStatus());
		
		MyntAssert.setJsonResponse(mobileIdpServiceSignInResponse);
		MyntAssert.setExpectedAndActualForMyntListen(true);
		
	}
		
	/**
	 * This TestCase is used to invoke MobileIDPService signIn API and verification for meta tag nodes in the response
	 * 
	 * @param userName
	 * @param password
	 * @param action
	 * @param successRespCode
	 */
	@Test(groups = { "Sanity", "Regression", "ExhaustiveRegression", "MiniRegression" }, 
			dataProvider = "MobileIdpDP_signInDataProvider_verifyMetaTagNodes")
	public void MobileIdpService_signIn_verifyMetaTagNodes(String userName, String password, String action, String successRespCode) 
	{
		RequestGenerator mobileIdpServiceSignInReqGen = mobileIdpServiceHelper.invokeMobileIdpSignInService(userName, password, action);
		String mobileIdpServiceSignInResponse = mobileIdpServiceSignInReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting MobileIDPService signIn API response :\n\n"+mobileIdpServiceSignInResponse);
		log.info("\nPrinting MobileIDPService signIn API response :\n\n"+mobileIdpServiceSignInResponse);
			
		AssertJUnit.assertTrue("Invalid MetaTag data nodes in MobileIDPService signIn API response", 
				mobileIdpServiceSignInReqGen.respvalidate.DoesNodesExists(MobileIdpServiceDataNodes.getMobileIdpSignInResponseMetaTagNodes()));
		
		AssertJUnit.assertTrue("MetaTag code data value should be 200 in MobileIDPService signIn API response", 
				mobileIdpServiceSignInReqGen.respvalidate.GetNodeValue(MobileIdpServiceDataNodes.MOBILE_IDP_RESP_META_CODE.getNodePath(), 
						mobileIdpServiceSignInResponse).equalsIgnoreCase(successRespCode));
		
		AssertJUnit.assertNotNull("MetaTag token data should not be null in MobileIDPService signIn API response",
				mobileIdpServiceSignInReqGen.respvalidate.GetNodeValue(MobileIdpServiceDataNodes.MOBILE_IDP_RESP_META_TOKEN.getNodePath(), 
						mobileIdpServiceSignInResponse));
		
		AssertJUnit.assertNotNull("MetaTag xsrfToken data should not be null in MobileIDPService signIn API response",
				mobileIdpServiceSignInReqGen.respvalidate.GetNodeValue(MobileIdpServiceDataNodes.MOBILE_IDP_RESP_META_XSRF_TOKEN.getNodePath(), 
						mobileIdpServiceSignInResponse));
		
		MyntAssert.setJsonResponse(mobileIdpServiceSignInResponse);
		MyntAssert.setExpectedAndActualForMyntListen(true);
	}
		
	
	/**
	 * This TestCase is used to invoke MobileIDPService signIn API and verification for notification tag nodes in the response
	 * 
	 * @param userName
	 * @param password
	 * @param action
	 * @param successRespCode
	 */
	@Test(groups = { "Sanity", "Regression", "ExhaustiveRegression", "MiniRegression" }, 
			dataProvider = "MobileIdpDP_signInDataProvider_verifyNotificationTagNodes")
	public void MobileIdpService_signIn_verifyNotificationTagNodes(String userName, String password, String action, String successRespCode) 
	{
		RequestGenerator mobileIdpServiceSignInReqGen = mobileIdpServiceHelper.invokeMobileIdpSignInService(userName, password, action);
		String mobileIdpServiceSignInResponse = mobileIdpServiceSignInReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting MobileIDPService signIn API response :\n\n"+mobileIdpServiceSignInResponse);
		log.info("\nPrinting MobileIDPService signIn API response :\n\n"+mobileIdpServiceSignInResponse);
			
		AssertJUnit.assertTrue("Error while invoking MobileIDPService signIn API", 
				mobileIdpServiceSignInReqGen.respvalidate.GetNodeValue(MobileIdpServiceDataNodes.MOBILE_IDP_RESP_META_CODE.getNodePath(), 
						mobileIdpServiceSignInResponse).equalsIgnoreCase(successRespCode));
		
		AssertJUnit.assertTrue("Invalid MetaTag data nodes in MobileIDPService signIn API response", 
				mobileIdpServiceSignInReqGen.respvalidate.DoesNodesExists(MobileIdpServiceDataNodes.getMobileIdpSignInResponseNotificationTagNodes()));
		
		MyntAssert.setJsonResponse(mobileIdpServiceSignInResponse);
		MyntAssert.setExpectedAndActualForMyntListen(true);
	}
	
	/**
	 * This TestCase is used to invoke MobileIDPService signIn API and verification for data tag nodes in the response
	 * 
	 * @param userName
	 * @param password
	 * @param action
	 * @param successRespCode
	 */
	@Test(groups = { "Sanity", "Regression", "ExhaustiveRegression", "MiniRegression" }, 
			dataProvider = "MobileIdpDP_signInDataProvider_verifyDataTagNodes")
	public void MobileIdpService_signIn_verifyDataTagNodes(String userName, String password, String action, String successRespCode) 
	{
		RequestGenerator mobileIdpServiceSignInReqGen = mobileIdpServiceHelper.invokeMobileIdpSignInService(userName, password, action);
		String mobileIdpServiceSignInResponse = mobileIdpServiceSignInReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting MobileIDPService signIn API response :\n\n"+mobileIdpServiceSignInResponse);
		log.info("\nPrinting MobileIDPService signIn API response :\n\n"+mobileIdpServiceSignInResponse);
			
		AssertJUnit.assertTrue("Error while invoking MobileIDPService signIn API", 
				mobileIdpServiceSignInReqGen.respvalidate.GetNodeValue(MobileIdpServiceDataNodes.MOBILE_IDP_RESP_META_CODE.getNodePath(), 
						mobileIdpServiceSignInResponse).equalsIgnoreCase(successRespCode));
		
		AssertJUnit.assertTrue("Invalid MetaTag data nodes in MobileIDPService signIn API response", 
				mobileIdpServiceSignInReqGen.respvalidate.DoesNodesExists(MobileIdpServiceDataNodes.getMobileIdpSignInResponseDataTagNodes()));
		
		AssertJUnit.assertTrue("Value of data.login should be same as UserName",
				mobileIdpServiceSignInReqGen.respvalidate.GetNodeValue(MobileIdpServiceDataNodes.MOBILE_IDP_RESP_DATA_LOGIN.getNodePath(), 
						mobileIdpServiceSignInResponse).equalsIgnoreCase("\"" + userName.trim() + "\""));
		
		AssertJUnit.assertTrue("Value of data.email should be same as UserName",
				mobileIdpServiceSignInReqGen.respvalidate.GetNodeValue(MobileIdpServiceDataNodes.MOBILE_IDP_RESP_DATA_EMAIL.getNodePath(),
						mobileIdpServiceSignInResponse).equalsIgnoreCase("\"" + userName.trim() + "\""));
		
		MyntAssert.setJsonResponse(mobileIdpServiceSignInResponse);
		MyntAssert.setExpectedAndActualForMyntListen(true);
		
	}
	
	/**
	 * This TestCase is used to invoke MobileIDPService signIn API and verification for failure response
	 * 
	 * @param username
	 * @param password
	 * @param action
	 * @param errorCode
	 * @param errorDetail
	 * @param errorType
	 */
	@Test(groups = { "Sanity", "Regression", "ExhaustiveRegression", "MiniRegression" }, 
			dataProvider = "MobileIdpDP_signInDataProvider_verifyFailureResponse")
	public void MobileIdpService_signIn_verifyFailureResponse(String userName, String password, String action, String errorCode, String errorDetail, String errorType)
	{
		RequestGenerator mobileIdpServiceSignInReqGen = mobileIdpServiceHelper.invokeMobileIdpSignInService(userName, password, action);
		String mobileIdpServiceSignInResponse = mobileIdpServiceSignInReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting MobileIDPService signIn API response :\n\n"+mobileIdpServiceSignInResponse);
		log.info("\nPrinting MobileIDPService signIn API response :\n\n"+mobileIdpServiceSignInResponse);
		
		AssertJUnit.assertEquals("MobileIDPService signIn API is not working", Integer.parseInt(errorCode), mobileIdpServiceSignInReqGen.response.getStatus());
		
		AssertJUnit.assertTrue("Invalid meta notification and data nodes",
				mobileIdpServiceSignInReqGen.respvalidate.DoesNodesExists(MobileIdpServiceDataNodes.getMobileIdpSignInResponseErrorMetaTagNodes()));
		
		AssertJUnit.assertTrue("MetaTag code data value should be " + errorCode + " in MobileIDPService signIn API response",
				mobileIdpServiceSignInReqGen.respvalidate.GetNodeValue(MobileIdpServiceDataNodes.MOBILE_IDP_RESP_META_CODE.getNodePath(), 
						mobileIdpServiceSignInResponse).matches(errorCode));
		
		AssertJUnit.assertTrue("MetaTag errorDetails data value should be "+errorDetail+" in MobileIDPService signIn API response",
				mobileIdpServiceSignInReqGen.respvalidate.GetNodeValue(MobileIdpServiceDataNodes.MOBILE_IDP_RESP_META_ERROR_DETAIL.getNodePath(), 
						mobileIdpServiceSignInResponse).matches("\"" + errorDetail + "\""));
		
		AssertJUnit.assertTrue("MobileIDPService signIn API response MetaTag errorType data value is not a failure type",
				mobileIdpServiceSignInReqGen.respvalidate.GetNodeValue(MobileIdpServiceDataNodes.MOBILE_IDP_RESP_META_ERROR_TYPE.getNodePath(), 
						mobileIdpServiceSignInResponse).contains(IServiceConstants.FAILURE_STATUS_TYPE));
		
		MyntAssert.setJsonResponse(mobileIdpServiceSignInResponse);
		MyntAssert.setExpectedAndActualForMyntListen(true);
	}

	/**
	 * This TestCase is used to invoke MobileIDPService signUp API and verification for success response
	 * 
	 * @param username
	 * @param password
	 * @param action
	 * @param errorCode
	 */
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression", "ExhaustiveRegression", "MiniRegression" }, 
			dataProvider = "MobileIdpDP_signUpDataProvider_verifySuccessResponse")
	public void MobileIdpService_signUp_verifySuccessResponse(String userName, String password, String action, String successRespCode)
	{
		RequestGenerator mobileIdpServiceSignUpReqGen = mobileIdpServiceHelper.invokeMobileIdpSignUpService(userName, password, action);
		String mobileIdpServiceSignUpResponse = mobileIdpServiceSignUpReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting MobileIDPService signUp API response :\n\n"+mobileIdpServiceSignUpResponse);
		log.info("\nPrinting MobileIDPService signUp API response :\n\n"+mobileIdpServiceSignUpResponse);

		AssertJUnit.assertEquals("MobileIDPService signIn API is not working",  Integer.parseInt(successRespCode), mobileIdpServiceSignUpReqGen.response.getStatus());
	
		MyntAssert.setJsonResponse(mobileIdpServiceSignUpResponse);
		MyntAssert.setExpectedAndActualForMyntListen(true);
	}
		
	/**
	 * This TestCase is used to invoke MobileIDPService signIn API and verification for meta tag nodes in the response
	 * 
	 * @param userName
	 * @param password
	 * @param action
	 * @param successRespCode
	 */
	@Test(groups = { "Smoke", "Sanity", "Regression", "ExhaustiveRegression", "MiniRegression" }, 
			dataProvider = "MobileIdpDP_signUpDataProvider_verifyMetaTagNodes")
	public void MobileIdpService_signUp_verifyMetaTagNodes(String userName, String password, String action, String successRespCode)
	{
		RequestGenerator mobileIdpServiceSignUpReqGen = mobileIdpServiceHelper.invokeMobileIdpSignUpService(userName, password, action);
		String mobileIdpServiceSignUpResponse = mobileIdpServiceSignUpReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting MobileIDPService signUp API response :\n\n"+mobileIdpServiceSignUpResponse);
		log.info("\nPrinting MobileIDPService signUp API response :\n\n"+mobileIdpServiceSignUpResponse);

		AssertJUnit.assertTrue("Invalid MetaTag data nodes in MobileIDPService signIn API response", 
				mobileIdpServiceSignUpReqGen.respvalidate.DoesNodesExists(MobileIdpServiceDataNodes.getMobileIdpSignUpResponseMetaTagNodes()));
		
		AssertJUnit.assertTrue("MetaTag code data value should be 200 in MobileIDPService signUp API response", 
				mobileIdpServiceSignUpReqGen.respvalidate.GetNodeValue(MobileIdpServiceDataNodes.MOBILE_IDP_RESP_META_CODE.getNodePath(), 
						mobileIdpServiceSignUpResponse).equalsIgnoreCase(successRespCode));
		
		AssertJUnit.assertNotNull("MetaTag token data should not be null in MobileIDPService signUp API response",
				mobileIdpServiceSignUpReqGen.respvalidate.GetNodeValue(MobileIdpServiceDataNodes.MOBILE_IDP_RESP_META_TOKEN.getNodePath(), 
						mobileIdpServiceSignUpResponse));
		
		AssertJUnit.assertNotNull("MetaTag xsrfToken data should not be null in MobileIDPService signUp API response",
				mobileIdpServiceSignUpReqGen.respvalidate.GetNodeValue(MobileIdpServiceDataNodes.MOBILE_IDP_RESP_META_XSRF_TOKEN.getNodePath(), 
						mobileIdpServiceSignUpResponse));
		
		MyntAssert.setJsonResponse(mobileIdpServiceSignUpResponse);
		MyntAssert.setExpectedAndActualForMyntListen(true);
		
	}
	
	
	/**
	 * This TestCase is used to invoke MobileIDPService signIn API and verification for notification tag nodes in the response
	 * 
	 * @param userName
	 * @param password
	 * @param action
	 * @param successRespCode
	 */
	@Test(groups = { "Smoke", "Sanity", "Regression", "ExhaustiveRegression", "MiniRegression" }, 
			dataProvider = "MobileIdpDP_signUpDataProvider_verifyNotificationTagNodes")
	public void MobileIdpService_signUp_verifyNotificationTagNodes(String userName, String password, String action, String successRespCode)
	{
		RequestGenerator mobileIdpServiceSignUpReqGen = mobileIdpServiceHelper.invokeMobileIdpSignUpService(userName, password, action);
		String mobileIdpServiceSignUpResponse = mobileIdpServiceSignUpReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting MobileIDPService signUp API response :\n\n"+mobileIdpServiceSignUpResponse);
		log.info("\nPrinting MobileIDPService signUp API response :\n\n"+mobileIdpServiceSignUpResponse);

		AssertJUnit.assertTrue("Error while invoking MobileIDPService signUp API", 
				mobileIdpServiceSignUpReqGen.respvalidate.GetNodeValue(MobileIdpServiceDataNodes.MOBILE_IDP_RESP_META_CODE.getNodePath(), 
						mobileIdpServiceSignUpResponse).equalsIgnoreCase(successRespCode));
		
		AssertJUnit.assertTrue("Invalid MetaTag data nodes in MobileIDPService signUp API response", 
				mobileIdpServiceSignUpReqGen.respvalidate.DoesNodesExists(MobileIdpServiceDataNodes.getMobileIdpSignUpResponseNotificationTagNodes()));
		
		MyntAssert.setJsonResponse(mobileIdpServiceSignUpResponse);
		MyntAssert.setExpectedAndActualForMyntListen(true);
	}
	
	/**
	 * This TestCase is used to invoke MobileIDPService signIn API and verification for data tag nodes in the response
	 * 
	 * @param userName
	 * @param password
	 * @param action
	 * @param successRespCode
	 */
	@Test(groups = { "Sanity", "Regression", "ExhaustiveRegression", "MiniRegression" }, 
			dataProvider = "MobileIdpDP_signUpDataProvider_verifyDataTagNodes")
	public void MobileIdpService_signUp_verifyDataTagNodes(String userName, String password, String action, String successRespCode)
	{
		RequestGenerator mobileIdpServiceSignUpReqGen = mobileIdpServiceHelper.invokeMobileIdpSignUpService(userName, password, action);
		String mobileIdpServiceSignUpResponse = mobileIdpServiceSignUpReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting MobileIDPService signUp API response :\n\n"+mobileIdpServiceSignUpResponse);
		log.info("\nPrinting MobileIDPService signUp API response :\n\n"+mobileIdpServiceSignUpResponse);

		AssertJUnit.assertTrue("Error while invoking MobileIDPService signUp API", 
				mobileIdpServiceSignUpReqGen.respvalidate.GetNodeValue(MobileIdpServiceDataNodes.MOBILE_IDP_RESP_META_CODE.getNodePath(), 
						mobileIdpServiceSignUpResponse).equalsIgnoreCase(successRespCode));
		
		AssertJUnit.assertTrue("Invalid MetaTag data nodes in MobileIDPService signUp API response", 
				mobileIdpServiceSignUpReqGen.respvalidate.DoesNodesExists(MobileIdpServiceDataNodes.getMobileIdpSignInResponseDataTagNodes()));
		
		AssertJUnit.assertTrue("Value of data.login should be same as UserName",
				mobileIdpServiceSignUpReqGen.respvalidate.GetNodeValue(MobileIdpServiceDataNodes.MOBILE_IDP_RESP_DATA_LOGIN.getNodePath(), 
						mobileIdpServiceSignUpResponse).equalsIgnoreCase("\"" + userName.trim() + "\""));
		
		AssertJUnit.assertTrue("Value of data.email should be same as UserName",
				mobileIdpServiceSignUpReqGen.respvalidate.GetNodeValue(MobileIdpServiceDataNodes.MOBILE_IDP_RESP_DATA_EMAIL.getNodePath(),
						mobileIdpServiceSignUpResponse).equalsIgnoreCase("\"" + userName.trim() + "\""));
		
		MyntAssert.setJsonResponse(mobileIdpServiceSignUpResponse);
		MyntAssert.setExpectedAndActualForMyntListen(true);
	}
	
	/**
	 * This TestCase is used to invoke MobileIDPService signIn API and verification for failure response
	 * 
	 * @param userName
	 * @param password
	 * @param action
	 * @param errorCode
	 * @param errorDetail
	 * @param errorType
	 */
	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression" }, 
			dataProvider = "MobileIdpDP_signUpDataProvider_verifyFailureResponse")
	public void MobileIdpService_signUp_verifyFailureResponse(String userName, String password, String action, String errorCode, String errorDetail, String errorType) 
	{
		RequestGenerator mobileIdpServiceSignUpReqGen = mobileIdpServiceHelper.invokeMobileIdpSignUpService(userName, password, action);
		String mobileIdpServiceSignUpResponse = mobileIdpServiceSignUpReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting MobileIDPService signUp API response :\n\n"+mobileIdpServiceSignUpResponse);
		log.info("\nPrinting MobileIDPService signUp API response :\n\n"+mobileIdpServiceSignUpResponse);
		
		AssertJUnit.assertEquals("MobileIDPService signUp API is not working", Integer.parseInt(errorCode), mobileIdpServiceSignUpReqGen.response.getStatus());
		
		AssertJUnit.assertTrue("Invalid meta notification and data nodes",
				mobileIdpServiceSignUpReqGen.respvalidate.DoesNodesExists(MobileIdpServiceDataNodes.getMobileIdpSignUpResponseErrorMetaTagNodes()));
		
		AssertJUnit.assertTrue("MetaTag code data value should be " + errorCode + " in MobileIDPService signUp API response",
				mobileIdpServiceSignUpReqGen.respvalidate.GetNodeValue(MobileIdpServiceDataNodes.MOBILE_IDP_RESP_META_CODE.getNodePath(), 
						mobileIdpServiceSignUpResponse).matches(errorCode));
		
		AssertJUnit.assertTrue("MetaTag errorDetails data value should be "+errorDetail+" in MobileIDPService signUp API response",
				mobileIdpServiceSignUpReqGen.respvalidate.GetNodeValue(MobileIdpServiceDataNodes.MOBILE_IDP_RESP_META_ERROR_DETAIL.getNodePath(), 
						mobileIdpServiceSignUpResponse).matches("\"" + errorDetail + "\""));
		
		AssertJUnit.assertTrue("MobileIDPService signUp API response MetaTag errorType data value is not a failure type",
				mobileIdpServiceSignUpReqGen.respvalidate.GetNodeValue(MobileIdpServiceDataNodes.MOBILE_IDP_RESP_META_ERROR_TYPE.getNodePath(), 
						mobileIdpServiceSignUpResponse).contains(IServiceConstants.FAILURE_STATUS_TYPE));
		
		MyntAssert.setJsonResponse(mobileIdpServiceSignUpResponse);
		MyntAssert.setExpectedAndActualForMyntListen(true);
		
	}

	/**
	 * This TestCase is used to invoke MobileIDPService signIn API and verification for existing user
	 * 
	 * @param userName
	 * @param password
	 * @param action
	 * @param errorCode
	 * @param errorDetail
	 * @param errorType
	 */
	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression" }, 
			dataProvider = "MobileIdpDP_signUpDataProvider_verifyForExistingUser")
	public void MobileIDP_signUp_verifyForExistingUser(String userName, String password, String action, String errorCode, String errorDetail, String errorType)
	{
		RequestGenerator mobileIdpServiceSignUpReqGen = mobileIdpServiceHelper.invokeMobileIdpSignUpService(userName, password, action);
		String mobileIdpServiceSignUpResponse = mobileIdpServiceSignUpReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting MobileIDPService signUp API response :\n\n"+mobileIdpServiceSignUpResponse);
		log.info("\nPrinting MobileIDPService signUp API response :\n\n"+mobileIdpServiceSignUpResponse);
		
		AssertJUnit.assertEquals("MobileIDPService signUp API is not working", Integer.parseInt(errorCode), mobileIdpServiceSignUpReqGen.response.getStatus());
		
		AssertJUnit.assertTrue("Invalid meta notification and data nodes",
				mobileIdpServiceSignUpReqGen.respvalidate.DoesNodesExists(MobileIdpServiceDataNodes.getMobileIdpSignUpResponseErrorMetaTagNodes()));
		
		AssertJUnit.assertTrue("MetaTag code data value should be " + errorCode + " in MobileIDPService signUp API response",
				mobileIdpServiceSignUpReqGen.respvalidate.GetNodeValue(MobileIdpServiceDataNodes.MOBILE_IDP_RESP_META_CODE.getNodePath(), 
						mobileIdpServiceSignUpResponse).matches(errorCode));
		
		AssertJUnit.assertTrue("MetaTag errorDetails data value should be "+errorDetail+" in MobileIDPService signUp API response",
				mobileIdpServiceSignUpReqGen.respvalidate.GetNodeValue(MobileIdpServiceDataNodes.MOBILE_IDP_RESP_META_ERROR_DETAIL.getNodePath(), 
						mobileIdpServiceSignUpResponse).matches("\"" + errorDetail + "\""));
		
		AssertJUnit.assertTrue("MobileIDPService signUp API response MetaTag errorType data value is not a failure type",
				mobileIdpServiceSignUpReqGen.respvalidate.GetNodeValue(MobileIdpServiceDataNodes.MOBILE_IDP_RESP_META_ERROR_TYPE.getNodePath(), 
						mobileIdpServiceSignUpResponse).contains(IServiceConstants.FAILURE_STATUS_TYPE));
		
		MyntAssert.setJsonResponse(mobileIdpServiceSignUpResponse);
		MyntAssert.setExpectedAndActualForMyntListen(true);
	}
	
	@Test(groups = { "SchemaValidation" }, dataProvider = "MobileIdpDP_signInDataProvider_verifyResponseDataNodesUsingSchemaValidations")
	public void MobileIdpService_signIn_verifyResponseDataNodesUsingSchemaValidations(String userName, String password, String action, String successRespCode) 
	{
		RequestGenerator mobileIdpServiceSignInReqGen = mobileIdpServiceHelper.invokeMobileIdpSignInService(userName, password, action);
		String mobileIdpServiceSignInResponse = mobileIdpServiceSignInReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting MobileIDPService signIn API response :\n\n"+mobileIdpServiceSignInResponse);
		log.info("\nPrinting MobileIDPService signIn API response :\n\n"+mobileIdpServiceSignInResponse);
		 
		AssertJUnit.assertEquals("MobileIDPService signIn API is not working",  Integer.parseInt(successRespCode), mobileIdpServiceSignInReqGen.response.getStatus());
		
		try {
            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/mobileidpservice-signin-schema.txt");
            List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(mobileIdpServiceSignInResponse, jsonschema);
            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in MobileIDPService signIn API response", CollectionUtils.isEmpty(missingNodeList));
        } catch (Exception e) {
            e.printStackTrace();
        }
		
		MyntAssert.setJsonResponse(mobileIdpServiceSignInResponse);
		MyntAssert.setExpectedAndActualForMyntListen(true);
		
	}
	
	@Test(groups = { "SchemaValidation" }, dataProvider = "MobileIdpDP_signUpDataProvider_verifyResponseDataNodesUsingSchemaValidations")
	public void MobileIdpService_signUp_verifyResponseDataNodesUsingSchemaValidations(String userName, String password, String action, String successRespCode)
	{
		RequestGenerator mobileIdpServiceSignUpReqGen = mobileIdpServiceHelper.invokeMobileIdpSignUpService(userName, password, action);
		String mobileIdpServiceSignUpResponse = mobileIdpServiceSignUpReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting MobileIDPService signUp API response :\n\n"+mobileIdpServiceSignUpResponse);
		log.info("\nPrinting MobileIDPService signUp API response :\n\n"+mobileIdpServiceSignUpResponse);

		AssertJUnit.assertEquals("MobileIDPService signIn API is not working",  Integer.parseInt(successRespCode), mobileIdpServiceSignUpReqGen.response.getStatus());
	
		try {
            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/mobileidpservice-signup-schema.txt");
            List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(mobileIdpServiceSignUpResponse, jsonschema);
            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in MobileIDPService signUp API response", CollectionUtils.isEmpty(missingNodeList));
        } catch (Exception e) {
            e.printStackTrace();
        }
		
		MyntAssert.setJsonResponse(mobileIdpServiceSignUpResponse);
		MyntAssert.setExpectedAndActualForMyntListen(true);
	}
	
}
