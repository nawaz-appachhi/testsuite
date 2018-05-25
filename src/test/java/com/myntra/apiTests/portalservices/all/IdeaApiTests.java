package com.myntra.apiTests.portalservices.all;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import com.myntra.apiTests.common.Myntra;
import com.myntra.apiTests.dataproviders.IdeaApiDP;
import com.myntra.apiTests.portalservices.ideaapi.IdeaApiHelper;
import com.myntra.apiTests.portalservices.ideaapi.IdeaDataNodes;
import com.myntra.apiTests.portalservices.ideaapi.IdeaErrorCodes;
import net.minidev.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import com.jayway.jsonpath.JsonPath;
import com.myntra.apiTests.portalservices.commons.StatusNodes;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.Toolbox;
import com.myntra.apiTests.common.APINAME;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.apiTests.common.ServiceType;

/**
 * @author shankara.c
 *
 */
public class IdeaApiTests extends IdeaApiDP
{
	static Logger log = Logger.getLogger(IdeaApiTests.class);
	static IdeaApiHelper ideaApiHelper = new IdeaApiHelper();
	static Initialize init = new Initialize("/Data/configuration");
	
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression", "MiniRegression", "ExhaustiveRegression" }, 
			dataProvider = "IdeaApiDP_getProfileByEmail_validateAPIResponse")
	public void IdeaApiTests_getProfileByEmail_verifySuccessResponse(String paramAppName, String paramEmailId, String paramResponseCode)
	{
		RequestGenerator getProfileByEmailReqGen = ideaApiHelper.invokeIdeaGetProfileByEmail(paramAppName, paramEmailId);
		String getProfileByEmailResponse = getProfileByEmailReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting IdeaApi getProfileByEmail API response :\n\n"+getProfileByEmailResponse);
		log.info("\nPrinting IdeaApi getProfileByEmail API response :\n\n"+getProfileByEmailResponse);
		 
		AssertJUnit.assertEquals("IdeaApi getProfileByEmail API is not working", Integer.parseInt(paramResponseCode), getProfileByEmailReqGen.response.getStatus());
	}
	
	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression" }, dataProvider = "IdeaApiDP_getProfileByEmail_verifySuccessStatusInformation")
	public void IdeaApiTests_getProfileByEmail_verifySuccessStatusInformation(String paramAppName, String paramEmailId, String paramStatusCode, String paramStatusMsg, 
			String paramStatusType)
	{
		RequestGenerator getProfileByEmailReqGen = ideaApiHelper.invokeIdeaGetProfileByEmail(paramAppName, paramEmailId);
		String getProfileByEmailResponse = getProfileByEmailReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting IdeaApi getProfileByEmail API response :\n\n"+getProfileByEmailResponse);
		log.info("\nPrinting IdeaApi getProfileByEmail API response :\n\n"+getProfileByEmailResponse);
		
		String responseStatusCode = getProfileByEmailReqGen.respvalidate.NodeValue(StatusNodes.STATUS_CODE.toString(), false).replace("\"", "");
		String responseStatusMsg = getProfileByEmailReqGen.respvalidate.NodeValue(StatusNodes.STATUS_MESSAGE.toString(), false).replace("\"", "");
		String responseStatusType = getProfileByEmailReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), false).replace("\"", "");
		
		AssertJUnit.assertTrue("IdeaApi getProfileByEmail API statusCode is invalid, Expected: <"+paramStatusCode+"> but Actual: <"+responseStatusCode+">", 
				responseStatusCode.equals(paramStatusCode));
		
		AssertJUnit.assertTrue("IdeaApi getProfileByEmail API statusMessage is invalid, Expected: <"+paramStatusMsg+"> but Actual: <"+responseStatusMsg+">", 
				responseStatusMsg.equals(paramStatusMsg));
		
		AssertJUnit.assertTrue("IdeaApi getProfileByEmail API statusType is invalid, Expected: <"+paramStatusType+"> but Actual: <"+responseStatusType+">", 
				responseStatusType.equals(paramStatusType));
	}
	
	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression" }, dataProvider = "IdeaApiDP_getProfileByEmail_verifyFailureStatusInformation")
	public void IdeaApiTests_getProfileByEmail_verifyFailureStatusInformation(String paramAppName, String paramEmailId, String paramStatusCode, String paramStatusMsg, 
			String paramStatusType)
	{
		RequestGenerator getProfileByEmailReqGen = ideaApiHelper.invokeIdeaGetProfileByEmail(paramAppName, paramEmailId);
		String getProfileByEmailResponse = getProfileByEmailReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting IdeaApi getProfileByEmail API response :\n\n"+getProfileByEmailResponse);
		log.info("\nPrinting IdeaApi getProfileByEmail API response :\n\n"+getProfileByEmailResponse);
		
		String responseStatusCode = getProfileByEmailReqGen.respvalidate.NodeValue(StatusNodes.STATUS_CODE.toString(), false).replace("\"", "");
		String responseStatusMsg = getProfileByEmailReqGen.respvalidate.NodeValue(StatusNodes.STATUS_MESSAGE.toString(), false).replace("\"", "");
		String responseStatusType = getProfileByEmailReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), false).replace("\"", "");
		
		AssertJUnit.assertTrue("IdeaApi getProfileByEmail API statusCode is invalid, Expected: <"+paramStatusCode+"> but Actual: <"+responseStatusCode+">", 
				responseStatusCode.equals(paramStatusCode));
		
		AssertJUnit.assertTrue("IdeaApi getProfileByEmail API statusMessage is invalid, Expected: <"+paramStatusMsg+"> but Actual: <"+responseStatusMsg+">", 
				responseStatusMsg.equals(paramStatusMsg));
		
		AssertJUnit.assertTrue("IdeaApi getProfileByEmail API statusType is invalid, Expected: <"+paramStatusType+"> but Actual: <"+responseStatusType+">", 
				responseStatusType.equals(paramStatusType));
	}
	
	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression" }, dataProvider = "IdeaApiDP_getProfileByEmail_verifyNodeValues")
	public void IdeaApiTests_getProfileByEmail_verifyNodeValues(String paramAppName, String paramAccessKey, String paramFirstName, String paramLastName, 
			String paramEmailId, String paramPhoneNumber, String paramGender, String paramDateOfBirth, String paramStatusType)
	{
		RequestGenerator getProfileByEmailReqGen = ideaApiHelper.invokeIdeaGetProfileByEmail(paramAppName, paramEmailId);
		String getProfileByEmailResponse = getProfileByEmailReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting IdeaApi getProfileByEmail API response :\n\n"+getProfileByEmailResponse);
		log.info("\nPrinting IdeaApi getProfileByEmail API response :\n\n"+getProfileByEmailResponse);
		
		String responseStatusType = getProfileByEmailReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), false).replace("\"", "");
		AssertJUnit.assertTrue("IdeaApi getProfileByEmail API statusType is invalid, Expected: <"+paramStatusType+"> but Actual: <"+responseStatusType+">", 
				responseStatusType.equals(paramStatusType));
		
		if(!getProfileByEmailReqGen.respvalidate.DoesNodeExists(IdeaDataNodes.IDEA_ENTRY_NODE.getIdeaDataNode()))
		{
			RequestGenerator signUpReqGen = ideaApiHelper.invokeIdeaSignUp(paramAppName, paramAccessKey, paramFirstName, paramLastName, paramEmailId, paramPhoneNumber, 
					paramGender, paramDateOfBirth);
			String signUpResponse = signUpReqGen.respvalidate.returnresponseasstring();
			System.out.println("\nPrinting IdeaApi signUp API response :\n\n"+signUpResponse);
			log.info("\nPrinting IdeaApi signUp API response :\n\n"+signUpResponse);
			
			responseStatusType = signUpReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), false).replace("\"", "");
			AssertJUnit.assertTrue("IdeaApi signUp API statusType is invalid, Expected: <"+paramStatusType+"> but Actual: <"+responseStatusType+">", 
					responseStatusType.equals(paramStatusType));
			
			getProfileByEmailReqGen = ideaApiHelper.invokeIdeaGetProfileByEmail(paramAppName, paramEmailId);
			getProfileByEmailResponse = getProfileByEmailReqGen.respvalidate.returnresponseasstring();
			System.out.println("\nPrinting IdeaApi getProfileByEmail API response :\n\n"+getProfileByEmailResponse);
			log.info("\nPrinting IdeaApi getProfileByEmail API response :\n\n"+getProfileByEmailResponse);
			
			responseStatusType = getProfileByEmailReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), false).replace("\"", "");
			AssertJUnit.assertTrue("IdeaApi getProfileByEmail API statusType is invalid, Expected: <"+paramStatusType+"> but Actual: <"+responseStatusType+">", 
					responseStatusType.equals(paramStatusType));
		}
		 
		List<JSONObject> emailDetails = JsonPath.read(getProfileByEmailResponse, "$.entry.emailDetails[*]");
		AssertJUnit.assertTrue("IdeaApi getProfileByEmail API [entry.emailDetails] data is empty", !emailDetails.isEmpty());
		List<String> emailList = new ArrayList<String>();
		for(int i=0; i<emailDetails.size(); i++)
		{
			String respEmail = JsonPath.read(getProfileByEmailResponse, "$.entry.emailDetails["+i+"].email");
			emailList.add(respEmail);
		}
		
		AssertJUnit.assertTrue(paramEmailId+" doesn't exists in [entry.emailDetails["+emailList+"]]", emailList.contains(paramEmailId));
	}
	
	@Test(groups = { "SchemaValidation" }, dataProvider = "IdeaApiDP_getProfileByEmail_verifyResponseDataNodesUsingSchemaValidations")
	public void IdeaApiTests_getProfileByEmail_verifyResponseDataNodesUsingSchemaValidations(String paramAppName, String paramAccessKey, String paramFirstName, 
			String paramLastName, String paramEmailId, String paramPhoneNumber, String paramGender, String paramDateOfBirth, String paramStatusType) throws InterruptedException
	{
		RequestGenerator getProfileByEmailReqGen = ideaApiHelper.invokeIdeaGetProfileByEmail(paramAppName, paramEmailId);
		String getProfileByEmailResponse = getProfileByEmailReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting IdeaApi getProfileByEmail API response :\n\n"+getProfileByEmailResponse);
		log.info("\nPrinting IdeaApi getProfileByEmail API response :\n\n"+getProfileByEmailResponse);
		 
		String responseStatusType = getProfileByEmailReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), false).replace("\"", "");
		AssertJUnit.assertTrue("IdeaApi getProfileByEmail API statusType is invalid, Expected: <"+paramStatusType+"> but Actual: <"+responseStatusType+">", 
				responseStatusType.equals(paramStatusType));
		
		if(!getProfileByEmailReqGen.respvalidate.DoesNodeExists(IdeaDataNodes.IDEA_ENTRY_NODE.getIdeaDataNode()))
		{
			RequestGenerator signUpReqGen = ideaApiHelper.invokeIdeaSignUp(paramAppName, paramAccessKey, paramFirstName, paramLastName, paramEmailId, paramPhoneNumber, 
					paramGender, paramDateOfBirth);
			String signUpResponse = signUpReqGen.respvalidate.returnresponseasstring();
			System.out.println("\nPrinting IdeaApi signUp API response :\n\n"+signUpResponse);
			log.info("\nPrinting IdeaApi signUp API response :\n\n"+signUpResponse);
			
			responseStatusType = signUpReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), false).replace("\"", "");
			AssertJUnit.assertTrue("IdeaApi signUp API statusType is invalid, Expected: <"+paramStatusType+"> but Actual: <"+responseStatusType+">", 
					responseStatusType.equals(paramStatusType));
			
			getProfileByEmailReqGen = ideaApiHelper.invokeIdeaGetProfileByEmail(paramAppName, paramEmailId);
			getProfileByEmailResponse = getProfileByEmailReqGen.respvalidate.returnresponseasstring();
			System.out.println("\nPrinting IdeaApi getProfileByEmail API response :\n\n"+getProfileByEmailResponse);
			log.info("\nPrinting IdeaApi getProfileByEmail API response :\n\n"+getProfileByEmailResponse);
			 
			responseStatusType = getProfileByEmailReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), false).replace("\"", "");
			AssertJUnit.assertTrue("IdeaApi getProfileByEmail API statusType is invalid, Expected: <"+paramStatusType+"> but Actual: <"+responseStatusType+">", 
					responseStatusType.equals(paramStatusType));
		}
		
		try {
            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/ideaapi-getprofilebyemail-schema.txt");
            List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(getProfileByEmailResponse, jsonschema);
            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in IdeaApi getProfileByEmail API response", CollectionUtils.isEmpty(missingNodeList));
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	@Test(groups = { "Smoke","FoxSanity", "Sanity", "ProdSanity", "Regression", "MiniRegression", "ExhaustiveRegression" }, 
			dataProvider = "IdeaApiDP_getProfileByUserId_validateAPIResponse")
	public void IdeaApiTests_getProfileByUserId_verifySuccessResponse(String paramAppName, String paramUserId, String successRespCode)
	{
		RequestGenerator getProfileByUserIdReqGen = ideaApiHelper.invokeIdeaGetProfileByUserId(paramAppName, paramUserId);
		String getProfileByUserIdResponse = getProfileByUserIdReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting IdeaApi getProfileByUserId API response :\n\n"+getProfileByUserIdResponse);
		log.info("\nPrinting IdeaApi getProfileByUserId API response :\n\n"+getProfileByUserIdResponse);
		 
		AssertJUnit.assertEquals("IdeaApi getProfileByUserId API is not working", Integer.parseInt(successRespCode), getProfileByUserIdReqGen.response.getStatus());
	}
	
	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression" }, dataProvider = "IdeaApiDP_getProfileByUserId_verifySuccessStatusInformation")
	public void IdeaApiTests_getProfileByUserId_verifySuccessStatusInformation(String paramAppName, String paramUserId, String paramStatusCode, String paramStatusMsg,
			String paramStatusType)
	{
		RequestGenerator getProfileByUserIdReqGen = ideaApiHelper.invokeIdeaGetProfileByUserId(paramAppName, paramUserId);
		String getProfileByUserIdResponse = getProfileByUserIdReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting IdeaApi getProfileByUserId API response :\n\n"+getProfileByUserIdResponse);
		log.info("\nPrinting IdeaApi getProfileByUserId API response :\n\n"+getProfileByUserIdResponse);
		 
		String responseStatusCode = getProfileByUserIdReqGen.respvalidate.NodeValue(StatusNodes.STATUS_CODE.toString(), true).replace("\"", "");
		String responseStatusMsg = getProfileByUserIdReqGen.respvalidate.NodeValue(StatusNodes.STATUS_MESSAGE.toString(), true).replace("\"", "");
		String responseStatusType = getProfileByUserIdReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).replace("\"", "");
		
		AssertJUnit.assertTrue("IdeaApi getProfileByUserId API statusCode is invalid, Expected: <"+paramStatusCode+"> but Actual: <"+responseStatusCode+">", 
				responseStatusCode.equals(paramStatusCode));
		
		AssertJUnit.assertTrue("IdeaApi getProfileByUserId API statusMessage is invalid, Expected: <"+paramStatusMsg+"> but Actual: <"+responseStatusMsg+">", 
				responseStatusMsg.equals(paramStatusMsg));
		
		AssertJUnit.assertTrue("IdeaApi getProfileByUserId API statusType is invalid, Expected: <"+paramStatusType+"> but Actual: <"+responseStatusType+">", 
				responseStatusType.equals(paramStatusType));
	}
	
	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression" }, dataProvider = "IdeaApiDP_getProfileByUserId_verifyFailureStatusInformation")
	public void IdeaApiTests_getProfileByUserId_verifyFailureStatusInformation(String paramAppName, String paramUserId, String paramStatusCode, String paramStatusMsg,
			String paramStatusType)
	{
		RequestGenerator getProfileByUserIdReqGen = ideaApiHelper.invokeIdeaGetProfileByUserId(paramAppName, paramUserId);
		String getProfileByUserIdResponse = getProfileByUserIdReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting IdeaApi getProfileByUserId API response :\n\n"+getProfileByUserIdResponse);
		log.info("\nPrinting IdeaApi getProfileByUserId API response :\n\n"+getProfileByUserIdResponse);
		 
		String responseStatusCode = getProfileByUserIdReqGen.respvalidate.NodeValue(StatusNodes.STATUS_CODE.toString(), true).replace("\"", "");
		String responseStatusMsg = getProfileByUserIdReqGen.respvalidate.NodeValue(StatusNodes.STATUS_MESSAGE.toString(), true).replace("\"", "");
		String responseStatusType = getProfileByUserIdReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).replace("\"", "");
		
		AssertJUnit.assertTrue("IdeaApi getProfileByUserId API statusCode is invalid, Expected: <"+paramStatusCode+"> but Actual: <"+responseStatusCode+">", 
				responseStatusCode.equals(paramStatusCode));
		
		AssertJUnit.assertTrue("IdeaApi getProfileByUserId API statusMessage is invalid, Expected: <"+paramStatusMsg+"> but Actual: <"+responseStatusMsg+">", 
				responseStatusMsg.equals(paramStatusMsg));
		
		AssertJUnit.assertTrue("IdeaApi getProfileByUserId API statusType is invalid, Expected: <"+paramStatusType+"> but Actual: <"+responseStatusType+">", 
				responseStatusType.equals(paramStatusType));
	}
	
	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression" }, dataProvider = "IdeaApiDP_getProfileByUserId_verifyNodeValues")
	public void IdeaApiTests_getProfileByUserId_verifyNodeValues(String paramAppName, String paramAccessKey, String paramFirstName, String paramLastName, 
			String paramUserId, String paramPhoneNumber, String paramGender, String paramDateOfBirth, String paramStatusType)
	{
		RequestGenerator getProfileByUserIdReqGen = ideaApiHelper.invokeIdeaGetProfileByUserId(paramAppName, paramUserId);
		String getProfileByUserIdResponse = getProfileByUserIdReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting IdeaApi getProfileByUserId API response :\n\n"+getProfileByUserIdResponse);
		log.info("\nPrinting IdeaApi getProfileByUserId API response :\n\n"+getProfileByUserIdResponse);
		 
		String responseStatusType = getProfileByUserIdReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), false).replace("\"", "");
		AssertJUnit.assertTrue("IdeaApi getProfileByUserId API statusType is invalid, Expected: <"+paramStatusType+"> but Actual: <"+responseStatusType+">", 
				responseStatusType.equals(paramStatusType));
		
		if(!getProfileByUserIdReqGen.respvalidate.DoesNodeExists(IdeaDataNodes.IDEA_ENTRY_NODE.getIdeaDataNode()))
		{
			RequestGenerator signUpReqGen = ideaApiHelper.invokeIdeaSignUp(paramAppName, paramAccessKey, paramFirstName, paramLastName, paramUserId, paramPhoneNumber, 
					paramGender, paramDateOfBirth);
			String signUpResponse = signUpReqGen.respvalidate.returnresponseasstring();
			System.out.println("\nPrinting IdeaApi signUp API response :\n\n"+signUpResponse);
			log.info("\nPrinting IdeaApi signUp API response :\n\n"+signUpResponse);
			
			responseStatusType = signUpReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), false).replace("\"", "");
			AssertJUnit.assertTrue("IdeaApi signUp API statusType is invalid, Expected: <"+paramStatusType+"> but Actual: <"+responseStatusType+">", 
					responseStatusType.equals(paramStatusType));
			
			getProfileByUserIdReqGen = ideaApiHelper.invokeIdeaGetProfileByUserId(paramAppName, paramUserId);
			getProfileByUserIdResponse = getProfileByUserIdReqGen.respvalidate.returnresponseasstring();
			System.out.println("\nPrinting IdeaApi getProfileByUserId API response :\n\n"+getProfileByUserIdResponse);
			log.info("\nPrinting IdeaApi getProfileByUserId API response :\n\n"+getProfileByUserIdResponse);
			 
			responseStatusType = getProfileByUserIdReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), false).replace("\"", "");
			AssertJUnit.assertTrue("IdeaApi getProfileByUserId API statusType is invalid, Expected: <"+paramStatusType+"> but Actual: <"+responseStatusType+">", 
					responseStatusType.equals(paramStatusType));
		}
		 
		List<JSONObject> emailDetails = JsonPath.read(getProfileByUserIdResponse, "$.entry.emailDetails[*]");
		AssertJUnit.assertTrue("IdeaApi getProfileByUserId API [entry.emailDetails] data is empty", !emailDetails.isEmpty());
		List<String> emailList = new ArrayList<String>();
		for(int i=0; i<emailDetails.size(); i++)
		{
			String respEmail = JsonPath.read(getProfileByUserIdResponse, "$.entry.emailDetails["+i+"].email");
			emailList.add(respEmail);
		}
		AssertJUnit.assertTrue(paramUserId+" doesn't exists in [entry.emailDetails["+emailList+"]]", emailList.contains(paramUserId));
	//	String uidx = JsonPath.read(getProfileByUserIdResponse, "$.entry.uidx");
	//	AssertJUnit.assertTrue(paramUserId+" is not equals to response uidx["+uidx+"]", uidx.equals(paramUserId));
	}
	
	@Test(groups = { "SchemaValidation" }, dataProvider = "IdeaApiDP_getProfileByUserId_verifyResponseDataNodesUsingSchemaValidations")
	public void IdeaApiTests_getProfileByUserId_verifyResponseDataNodesUsingSchemaValidations(String paramAppName, String paramAccessKey, String paramFirstName, 
			String paramLastName, String paramUserId, String paramPhoneNumber, String paramGender, String paramDateOfBirth, String paramStatusType)
	{
		RequestGenerator getProfileByUserIdReqGen = ideaApiHelper.invokeIdeaGetProfileByUserId(paramAppName, paramUserId);
		String getProfileByUserIdResponse = getProfileByUserIdReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting IdeaApi getProfileByUserId API response :\n\n"+getProfileByUserIdResponse);
		log.info("\nPrinting IdeaApi getProfileByUserId API response :\n\n"+getProfileByUserIdResponse);
		 
		String responseStatusType = getProfileByUserIdReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), false).replace("\"", "");
		AssertJUnit.assertTrue("IdeaApi getProfileByUserId API statusType is invalid, Expected: <"+paramStatusType+"> but Actual: <"+responseStatusType+">", 
				responseStatusType.equals(paramStatusType));
		
		if(!getProfileByUserIdReqGen.respvalidate.DoesNodeExists(IdeaDataNodes.IDEA_ENTRY_NODE.getIdeaDataNode()))
		{
			RequestGenerator signUpReqGen = ideaApiHelper.invokeIdeaSignUp(paramAppName, paramAccessKey, paramFirstName, paramLastName, paramUserId, paramPhoneNumber, 
					paramGender, paramDateOfBirth);
			String signUpResponse = signUpReqGen.respvalidate.returnresponseasstring();
			System.out.println("\nPrinting IdeaApi signUp API response :\n\n"+signUpResponse);
			log.info("\nPrinting IdeaApi signUp API response :\n\n"+signUpResponse);
			
			responseStatusType = signUpReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), false).replace("\"", "");
			AssertJUnit.assertTrue("IdeaApi signUp API statusType is invalid, Expected: <"+paramStatusType+"> but Actual: <"+responseStatusType+">", 
					responseStatusType.equals(paramStatusType));
			
			getProfileByUserIdReqGen = ideaApiHelper.invokeIdeaGetProfileByUserId(paramAppName, paramUserId);
			getProfileByUserIdResponse = getProfileByUserIdReqGen.respvalidate.returnresponseasstring();
			System.out.println("\nPrinting IdeaApi getProfileByUserId API response :\n\n"+getProfileByUserIdResponse);
			log.info("\nPrinting IdeaApi getProfileByUserId API response :\n\n"+getProfileByUserIdResponse);
			 
			responseStatusType = getProfileByUserIdReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), false).replace("\"", "");
			AssertJUnit.assertTrue("IdeaApi getProfileByUserId API statusType is invalid, Expected: <"+paramStatusType+"> but Actual: <"+responseStatusType+">", 
					responseStatusType.equals(paramStatusType));
		}
		 
		try {
            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/ideaapi-getprofilebyuserid-schema.txt");
            List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(getProfileByUserIdResponse, jsonschema);
            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in IdeaApi getProfileByUserId API response", CollectionUtils.isEmpty(missingNodeList));
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression", "MiniRegression", "ExhaustiveRegression" }, 
			dataProvider = "IdeaApiDP_addEmailToUserProfile_validateAPIResponse")
	public void IdeaApiTests_addEmailToUserProfile_verifySuccessResponse(String paramAppName, String paramEmailId, String paramUserId, String successRespCode)
	{
		RequestGenerator addEmailToUserProfileReqGen = ideaApiHelper.invokeIdeaAddEmailToUserProfile(paramAppName, paramEmailId, paramUserId);
		String addEmailToUserProfileResponse = addEmailToUserProfileReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting IdeaApi addEmailToUserProfile API response :\n\n"+addEmailToUserProfileResponse);
		log.info("\nPrinting IdeaApi addEmailToUserProfile API response :\n\n"+addEmailToUserProfileResponse);
		 
		AssertJUnit.assertEquals("IdeaApi addEmailToUserProfile API is not working", Integer.parseInt(successRespCode), addEmailToUserProfileReqGen.response.getStatus());
	}
	
	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression" }, dataProvider = "IdeaApiDP_addEmailToUserProfile_verifySuccessStatusInformation")
	public void IdeaApiTests_addEmailToUserProfile_verifySuccessStatusInformation(String paramAppName, String paramEmailId, String paramAccessKey, String paramFirstName, 
			String paramLastName, String paramUserId, String paramPhoneNumber, String paramGender, String paramDateOfBirth, String paramStatusCode, String paramStatusMsg, 
			String paramStatusType)
	{
		RequestGenerator addEmailToUserProfileReqGen = ideaApiHelper.invokeIdeaAddEmailToUserProfile(paramAppName, paramEmailId, getUidx(paramUserId));
		String addEmailToUserProfileResponse = addEmailToUserProfileReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting IdeaApi addEmailToUserProfile API response :\n\n"+addEmailToUserProfileResponse);
		log.info("\nPrinting IdeaApi addEmailToUserProfile API response :\n\n"+addEmailToUserProfileResponse);
		 
		String responseStatusCode = addEmailToUserProfileReqGen.respvalidate.NodeValue(StatusNodes.STATUS_CODE.toString(), true).replace("\"", "");
		String responseStatusMsg = addEmailToUserProfileReqGen.respvalidate.NodeValue(StatusNodes.STATUS_MESSAGE.toString(), true).replace("\"", "");
		String responseStatusType = addEmailToUserProfileReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).replace("\"", "");
		
		if(responseStatusCode.equals(IdeaErrorCodes.ACCOUNT_DOES_NOT_EXISTS.getIdeaErrorCode()))
		{
			RequestGenerator signUpReqGen = ideaApiHelper.invokeIdeaSignUp(paramAppName, paramAccessKey, paramFirstName, paramLastName, paramUserId, paramPhoneNumber, 
					paramGender, paramDateOfBirth);
			String signUpResponse = signUpReqGen.respvalidate.returnresponseasstring();
			System.out.println("\nPrinting IdeaApi signUp API response :\n\n"+signUpResponse);
			log.info("\nPrinting IdeaApi signUp API response :\n\n"+signUpResponse);
			
			responseStatusType = signUpReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), false).replace("\"", "");
			AssertJUnit.assertTrue("IdeaApi signUp API statusType is invalid, Expected: <"+paramStatusType+"> but Actual: <"+responseStatusType+">", 
					responseStatusType.equals(paramStatusType));
			
			addEmailToUserProfileReqGen = ideaApiHelper.invokeIdeaAddEmailToUserProfile(paramAppName, paramEmailId, paramUserId);
			addEmailToUserProfileResponse = addEmailToUserProfileReqGen.respvalidate.returnresponseasstring();
			System.out.println("\nPrinting IdeaApi addEmailToUserProfile API response :\n\n"+addEmailToUserProfileResponse);
			log.info("\nPrinting IdeaApi addEmailToUserProfile API response :\n\n"+addEmailToUserProfileResponse);
			
			responseStatusCode = addEmailToUserProfileReqGen.respvalidate.NodeValue(StatusNodes.STATUS_CODE.toString(), true).replace("\"", "");
			responseStatusMsg = addEmailToUserProfileReqGen.respvalidate.NodeValue(StatusNodes.STATUS_MESSAGE.toString(), true).replace("\"", "");
			responseStatusType = addEmailToUserProfileReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).replace("\"", "");
		}
		
		AssertJUnit.assertTrue("IdeaApi addEmailToUserProfile API statusCode is invalid, Expected: <"+paramStatusCode+"> but Actual: <"+responseStatusCode+">", 
				responseStatusCode.equals(paramStatusCode));
		AssertJUnit.assertTrue("IdeaApi addEmailToUserProfile API statusMessage is invalid, Expected: <"+paramStatusMsg+"> but Actual: <"+responseStatusMsg+">", 
				responseStatusMsg.equals(paramStatusMsg));
		AssertJUnit.assertTrue("IdeaApi addEmailToUserProfile API statusType is invalid, Expected: <"+paramStatusType+"> but Actual: <"+responseStatusCode+">",  
				responseStatusType.equals(paramStatusType));
	}
	
	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression" }, dataProvider = "IdeaApiDP_addEmailToUserProfile_verifyFailureStatusInformation")
	public void IdeaApiTests_addEmailToUserProfile_verifyFailureStatusInformation(String paramAppName, String paramEmailId, String paramUserId, String paramStatusCode, 
			String paramStatusMsg, String paramStatusType)
	{
		RequestGenerator addEmailToUserProfileReqGen = ideaApiHelper.invokeIdeaAddEmailToUserProfile(paramAppName, paramEmailId, paramUserId);
		System.out.println("\nPrinting IdeaApi addEmailToUserProfile API response :\n\n"+addEmailToUserProfileReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting IdeaApi addEmailToUserProfile API response :\n\n"+addEmailToUserProfileReqGen.respvalidate.returnresponseasstring());
		 
		String responseStatusCode = addEmailToUserProfileReqGen.respvalidate.NodeValue(StatusNodes.STATUS_CODE.toString(), true).replace("\"", "");
		String responseStatusMsg = addEmailToUserProfileReqGen.respvalidate.NodeValue(StatusNodes.STATUS_MESSAGE.toString(), true).replace("\"", "");
		String responseStatusType = addEmailToUserProfileReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).replace("\"", "");
		
		AssertJUnit.assertTrue("IdeaApi addEmailToUserProfile API statusCode is invalid, Expected: <"+paramStatusCode+"> but Actual: <"+responseStatusCode+">",  
				responseStatusCode.equals(paramStatusCode));
		AssertJUnit.assertTrue("IdeaApi addEmailToUserProfile API statusMessage is invalid, Expected: <"+paramStatusMsg+"> but Actual: <"+responseStatusMsg+">",  
				responseStatusMsg.equals(paramStatusMsg));
		AssertJUnit.assertTrue("IdeaApi addEmailToUserProfile API statusType is invalid, Expected: <"+paramStatusType+"> but Actual: <"+responseStatusType+">", 
				responseStatusType.equals(paramStatusType));
	}
	
	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression" }, dataProvider = "IdeaApiDP_addEmailToUserProfile_verifyNodeValues")
	public void IdeaApiTests_addEmailToUserProfile_verifyNodeValues(String paramAppName, String paramEmailId, String paramAccessKey, String paramFirstName, 
			String paramLastName, String paramUserId, String paramPhoneNumber, String paramGender, String paramDateOfBirth, String paramStatusType)
	{
		RequestGenerator addEmailToUserProfileReqGen = ideaApiHelper.invokeIdeaAddEmailToUserProfile(paramAppName, paramEmailId, paramUserId);
		String addEmailToUserProfileResponse = addEmailToUserProfileReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting IdeaApi addEmailToUserProfile API response :\n\n"+addEmailToUserProfileResponse);
		log.info("\nPrinting IdeaApi addEmailToUserProfile API response :\n\n"+addEmailToUserProfileResponse);
		 
		String responseStatusCode = addEmailToUserProfileReqGen.respvalidate.NodeValue(StatusNodes.STATUS_CODE.toString(), true).replace("\"", "");
		String responseStatusType = addEmailToUserProfileReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).replace("\"", "");
		
		if(responseStatusCode.equals(IdeaErrorCodes.ACCOUNT_DOES_NOT_EXISTS.getIdeaErrorCode()))
		{
			RequestGenerator signUpReqGen = ideaApiHelper.invokeIdeaSignUp(paramAppName, paramAccessKey, paramFirstName, paramLastName, paramUserId, paramPhoneNumber, 
					paramGender, paramDateOfBirth);
			String signUpResponse = signUpReqGen.respvalidate.returnresponseasstring();
			System.out.println("\nPrinting IdeaApi signUp API response :\n\n"+signUpResponse);
			log.info("\nPrinting IdeaApi signUp API response :\n\n"+signUpResponse);
			
			responseStatusType = signUpReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), false).replace("\"", "");
			AssertJUnit.assertTrue("IdeaApi signUp API statusType is invalid, Expected: <"+paramStatusType+"> but Actual: <"+responseStatusType+">", 
					responseStatusType.equals(paramStatusType));
			
			addEmailToUserProfileReqGen = ideaApiHelper.invokeIdeaAddEmailToUserProfile(paramAppName, paramEmailId, paramUserId);
			addEmailToUserProfileResponse = addEmailToUserProfileReqGen.respvalidate.returnresponseasstring();
			System.out.println("\nPrinting IdeaApi addEmailToUserProfile API response :\n\n"+addEmailToUserProfileResponse);
			log.info("\nPrinting IdeaApi addEmailToUserProfile API response :\n\n"+addEmailToUserProfileResponse);
			
			responseStatusType = addEmailToUserProfileReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).replace("\"", "");
		}
		
		AssertJUnit.assertTrue("IdeaApi addEmailToUserProfile API statusType is invalid, Expected: <"+paramStatusType+"> but Actual: <"+responseStatusCode+">",  
				responseStatusType.equals(paramStatusType));
		
		List<JSONObject> emailDetails = JsonPath.read(addEmailToUserProfileResponse, "$.entry.emailDetails[*]");
		AssertJUnit.assertTrue("IdeaApi addEmailToUserProfile API [entry.emailDetails] data is empty", !emailDetails.isEmpty());
		List<String> emailList = new ArrayList<String>();
		for(int i=0; i<emailDetails.size(); i++)
		{
			String respEmail = JsonPath.read(addEmailToUserProfileResponse, "$.entry.emailDetails["+i+"].email");
			emailList.add(respEmail);
		}
		
		AssertJUnit.assertTrue(paramEmailId+" doesn't exists in [entry.emailDetails["+emailList+"]]", emailList.contains(paramEmailId));
	//	String uidx = JsonPath.read(addEmailToUserProfileResponse, "$.entry.uidx");
	//	AssertJUnit.assertTrue(paramUserId+" is not equals to response uidx["+uidx+"]", uidx.equals(paramUserId));
	}
	
	@Test(groups = { "SchemaValidation" }, dataProvider = "IdeaApiDP_addEmailToUserProfile_verifyResponseDataNodesUsingSchemaValidations")
	public void IdeaApiTests_addEmailToUserProfile_verifyResponseDataNodesUsingSchemaValidations(String paramAppName, String paramEmailId, String paramAccessKey, 
			String paramFirstName, String paramLastName, String paramUserId, String paramPhoneNumber, String paramGender, String paramDateOfBirth, String paramStatusType)
	{
		RequestGenerator addEmailToUserProfileReqGen = ideaApiHelper.invokeIdeaAddEmailToUserProfile(paramAppName, paramEmailId, getUidx(paramUserId));
		String addEmailToUserProfileResponse = addEmailToUserProfileReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting IdeaApi addEmailToUserProfile API response :\n\n"+addEmailToUserProfileResponse);
		log.info("\nPrinting IdeaApi addEmailToUserProfile API response :\n\n"+addEmailToUserProfileResponse);
		 
		String responseStatusCode = addEmailToUserProfileReqGen.respvalidate.NodeValue(StatusNodes.STATUS_CODE.toString(), true).replace("\"", "");
		String responseStatusType = addEmailToUserProfileReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).replace("\"", "");
		
		if(responseStatusCode.equals(IdeaErrorCodes.ACCOUNT_DOES_NOT_EXISTS.getIdeaErrorCode()))
		{
			RequestGenerator signUpReqGen = ideaApiHelper.invokeIdeaSignUp(paramAppName, paramAccessKey, paramFirstName, paramLastName, paramUserId, paramPhoneNumber, 
					paramGender, paramDateOfBirth);
			String signUpResponse = signUpReqGen.respvalidate.returnresponseasstring();
			System.out.println("\nPrinting IdeaApi signUp API response :\n\n"+signUpResponse);
			log.info("\nPrinting IdeaApi signUp API response :\n\n"+signUpResponse);
			
			responseStatusType = signUpReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), false).replace("\"", "");
			AssertJUnit.assertTrue("IdeaApi signUp API statusType is invalid, Expected: <"+paramStatusType+"> but Actual: <"+responseStatusType+">", 
					responseStatusType.equals(paramStatusType));
			
			addEmailToUserProfileReqGen = ideaApiHelper.invokeIdeaAddEmailToUserProfile(paramAppName, paramEmailId, paramUserId);
			addEmailToUserProfileResponse = addEmailToUserProfileReqGen.respvalidate.returnresponseasstring();
			System.out.println("\nPrinting IdeaApi addEmailToUserProfile API response :\n\n"+addEmailToUserProfileResponse);
			log.info("\nPrinting IdeaApi addEmailToUserProfile API response :\n\n"+addEmailToUserProfileResponse);
			
			responseStatusType = addEmailToUserProfileReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).replace("\"", "");
		}
		
		AssertJUnit.assertTrue("IdeaApi addEmailToUserProfile API statusType is invalid, Expected: <"+paramStatusType+"> but Actual: <"+responseStatusCode+">",  
				responseStatusType.equals(paramStatusType));
		
		try {
            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/ideaapi-addemailtouserprofile-schema.txt");
            List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(addEmailToUserProfileResponse, jsonschema);
            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in IdeaApi addEmailToUserProfile API response", CollectionUtils.isEmpty(missingNodeList));
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression", "MiniRegression", "ExhaustiveRegression" }, 
			dataProvider = "IdeaApiDP_addPhoneNumberToUserProfile_validateAPIResponse")
	public void IdeaApiTests_addPhoneNumberToUserProfile_verifySuccessResponse(String paramAppName, String paramPhoneNumber, String paramUserId, String successRespCode)
	{
		RequestGenerator addPhoneNumberToUserProfileReqGen = ideaApiHelper.invokeIdeaAddPhoneNumberToUserProfile(paramAppName, paramPhoneNumber, paramUserId);
		String addPhoneNumberToUserProfileResponse = addPhoneNumberToUserProfileReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting IdeaApi addPhoneNumberToUserProfile API response :\n\n"+addPhoneNumberToUserProfileResponse);
		log.info("\nPrinting IdeaApi addPhoneNumberToUserProfile API response :\n\n"+addPhoneNumberToUserProfileResponse);
		 
		AssertJUnit.assertEquals("IdeaApi addPhoneNumberToUserProfile API is not working", Integer.parseInt(successRespCode), 
				addPhoneNumberToUserProfileReqGen.response.getStatus());
	}
	
	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression" }, 
			dataProvider = "IdeaApiDP_addPhoneNumberToUserProfile_verifySuccessStatusInformation")
	public void IdeaApiTests_addPhoneNumberToUserProfile_verifySuccessStatusInformation(String paramAppName, String paramAddingPhoneNumber, String paramAccessKey, 
			String paramFirstName, String paramLastName, String paramUserId, String paramPhoneNumber, String paramGender, String paramDateOfBirth, String paramStatusCode,
			String paramStatusMsg, String paramStatusType)
	{
		RequestGenerator addPhoneNumberToUserProfileReqGen = ideaApiHelper.invokeIdeaAddPhoneNumberToUserProfile(paramAppName, paramAddingPhoneNumber, getUidx(paramUserId));
		String addPhoneNumberToUserProfileResponse = addPhoneNumberToUserProfileReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting IdeaApi addPhoneNumberToUserProfile API response :\n\n"+addPhoneNumberToUserProfileResponse);
		log.info("\nPrinting IdeaApi addPhoneNumberToUserProfile API response :\n\n"+addPhoneNumberToUserProfileResponse);
		 
		String responseStatusCode = addPhoneNumberToUserProfileReqGen.respvalidate.NodeValue(StatusNodes.STATUS_CODE.toString(), true).replace("\"", "");
		String responseStatusMsg = addPhoneNumberToUserProfileReqGen.respvalidate.NodeValue(StatusNodes.STATUS_MESSAGE.toString(), true).replace("\"", "");
		String responseStatusType = addPhoneNumberToUserProfileReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).replace("\"", "");
		
		if(responseStatusCode.equals(IdeaErrorCodes.ACCOUNT_DOES_NOT_EXISTS.getIdeaErrorCode()))
		{
			RequestGenerator signUpReqGen = ideaApiHelper.invokeIdeaSignUp(paramAppName, paramAccessKey, paramFirstName, paramLastName, paramUserId, paramPhoneNumber, 
					paramGender, paramDateOfBirth);
			String signUpResponse = signUpReqGen.respvalidate.returnresponseasstring();
			System.out.println("\nPrinting IdeaApi signUp API response :\n\n"+signUpResponse);
			log.info("\nPrinting IdeaApi signUp API response :\n\n"+signUpResponse);
			
			responseStatusType = signUpReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), false).replace("\"", "");
			AssertJUnit.assertTrue("IdeaApi signUp API statusType is invalid, Expected: <"+paramStatusType+"> but Actual: <"+responseStatusType+">", 
					responseStatusType.equals(paramStatusType));
			
			addPhoneNumberToUserProfileReqGen = ideaApiHelper.invokeIdeaAddPhoneNumberToUserProfile(paramAppName, paramAddingPhoneNumber, paramUserId);
			addPhoneNumberToUserProfileResponse = addPhoneNumberToUserProfileReqGen.respvalidate.returnresponseasstring();
			System.out.println("\nPrinting IdeaApi addPhoneNumberToUserProfile API response :\n\n"+addPhoneNumberToUserProfileResponse);
			log.info("\nPrinting IdeaApi addPhoneNumberToUserProfile API response :\n\n"+addPhoneNumberToUserProfileResponse);
			
			responseStatusCode = addPhoneNumberToUserProfileReqGen.respvalidate.NodeValue(StatusNodes.STATUS_CODE.toString(), true).replace("\"", "");
			responseStatusMsg = addPhoneNumberToUserProfileReqGen.respvalidate.NodeValue(StatusNodes.STATUS_MESSAGE.toString(), true).replace("\"", "");
			responseStatusType = addPhoneNumberToUserProfileReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).replace("\"", "");
		}
		
		AssertJUnit.assertTrue("IdeaApi addPhoneNumberToUserProfile API statusCode is invalid, Expected: <"+paramStatusCode+"> but Actual: <"+responseStatusCode+">",  
				responseStatusCode.equals(paramStatusCode));
		AssertJUnit.assertTrue("IdeaApi addPhoneNumberToUserProfile API statusMessage is invalid, Expected: <"+paramStatusMsg+"> but Actual: <"+responseStatusMsg+">",  
				responseStatusMsg.equals(paramStatusMsg));
		AssertJUnit.assertTrue("IdeaApi addPhoneNumberToUserProfile API statusType is invalid, Expected: <"+paramStatusType+"> but Actual: <"+responseStatusType+">", 
				responseStatusType.equals(paramStatusType));
	}
	
	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression" }, 
			dataProvider = "IdeaApiDP_addPhoneNumberToUserProfile_verifyFailureStatusInformation")
	public void IdeaApiTests_addPhoneNumberToUserProfile_verifyFailureStatusInformation(String paramAppName, String paramPhoneNumber, String paramUserId, 
			String paramStatusCode, String paramStatusMsg, String paramStatusType)
	{
		RequestGenerator addPhoneNumberToUserProfileReqGen = ideaApiHelper.invokeIdeaAddPhoneNumberToUserProfile(paramAppName, paramPhoneNumber, paramUserId);
		String addPhoneNumberToUserProfileResponse = addPhoneNumberToUserProfileReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting IdeaApi addPhoneNumberToUserProfile API response :\n\n"+addPhoneNumberToUserProfileResponse);
		log.info("\nPrinting IdeaApi addPhoneNumberToUserProfile API response :\n\n"+addPhoneNumberToUserProfileResponse);
		 
		String responseStatusCode = addPhoneNumberToUserProfileReqGen.respvalidate.NodeValue(StatusNodes.STATUS_CODE.toString(), true).replace("\"", "");
		String responseStatusMsg = addPhoneNumberToUserProfileReqGen.respvalidate.NodeValue(StatusNodes.STATUS_MESSAGE.toString(), true).replace("\"", "");
		String responseStatusType = addPhoneNumberToUserProfileReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).replace("\"", "");
		
		AssertJUnit.assertTrue("IdeaApi addPhoneNumberToUserProfile API statusCode is invalid, Expected: <"+paramStatusCode+"> but Actual: <"+responseStatusCode+">", 
				responseStatusCode.equals(paramStatusCode));
		AssertJUnit.assertTrue("IdeaApi addPhoneNumberToUserProfile API statusMessage is invalid, Expected: <"+paramStatusMsg+"> but Actual: <"+responseStatusMsg+">",
				responseStatusMsg.equals(paramStatusMsg));
		AssertJUnit.assertTrue("IdeaApi addPhoneNumberToUserProfile API statusType is invalid, Expected: <"+paramStatusType+"> but Actual: <"+responseStatusType+">",
				responseStatusType.equals(paramStatusType));
	}
	
	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression" }, dataProvider = "IdeaApiDP_addPhoneNumberToUserProfile_verifyNodeValues")
	public void IdeaApiTests_addPhoneNumberToUserProfile_verifyNodeValues(String paramAppName, String paramAddingPhoneNumber, String paramAccessKey, 
			String paramFirstName, String paramLastName, String paramUserId, String paramPhoneNumber, String paramGender, String paramDateOfBirth, String paramStatusType)
	{
		RequestGenerator addPhoneNumberToUserProfileReqGen = ideaApiHelper.invokeIdeaAddPhoneNumberToUserProfile(paramAppName, paramAddingPhoneNumber, getUidx(paramUserId));
		String addPhoneNumberToUserProfileResponse = addPhoneNumberToUserProfileReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting IdeaApi addPhoneNumberToUserProfile API response :\n\n"+addPhoneNumberToUserProfileResponse);
		log.info("\nPrinting IdeaApi addPhoneNumberToUserProfile API response :\n\n"+addPhoneNumberToUserProfileResponse);
		 
		String responseStatusCode = addPhoneNumberToUserProfileReqGen.respvalidate.NodeValue(StatusNodes.STATUS_CODE.toString(), true).replace("\"", "");
		String responseStatusType = addPhoneNumberToUserProfileReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).replace("\"", "");
		
		if(responseStatusCode.equals(IdeaErrorCodes.ACCOUNT_DOES_NOT_EXISTS.getIdeaErrorCode()))
		{
			RequestGenerator signUpReqGen = ideaApiHelper.invokeIdeaSignUp(paramAppName, paramAccessKey, paramFirstName, paramLastName, paramUserId, paramPhoneNumber, 
					paramGender, paramDateOfBirth);
			String signUpResponse = signUpReqGen.respvalidate.returnresponseasstring();
			System.out.println("\nPrinting IdeaApi signUp API response :\n\n"+signUpResponse);
			log.info("\nPrinting IdeaApi signUp API response :\n\n"+signUpResponse);
			
			responseStatusType = signUpReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), false).replace("\"", "");
			AssertJUnit.assertTrue("IdeaApi signUp API statusType is invalid, Expected: <"+paramStatusType+"> but Actual: <"+responseStatusType+">", 
					responseStatusType.equals(paramStatusType));
			
			addPhoneNumberToUserProfileReqGen = ideaApiHelper.invokeIdeaAddPhoneNumberToUserProfile(paramAppName, paramAddingPhoneNumber, paramUserId);
			addPhoneNumberToUserProfileResponse = addPhoneNumberToUserProfileReqGen.respvalidate.returnresponseasstring();
			System.out.println("\nPrinting IdeaApi addPhoneNumberToUserProfile API response :\n\n"+addPhoneNumberToUserProfileResponse);
			log.info("\nPrinting IdeaApi addPhoneNumberToUserProfile API response :\n\n"+addPhoneNumberToUserProfileResponse);
			
			responseStatusType = addPhoneNumberToUserProfileReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).replace("\"", "");
		}
		
		AssertJUnit.assertTrue("IdeaApi addPhoneNumberToUserProfile API statusType is invalid, Expected: <"+paramStatusType+"> but Actual: <"+responseStatusType+">", 
				responseStatusType.equals(paramStatusType));

		List<JSONObject> phoneDetails = JsonPath.read(addPhoneNumberToUserProfileResponse, "$.entry.phoneDetails[*]");
		AssertJUnit.assertTrue("IdeaApi addPhoneNumberToUserProfile API [entry.phoneDetails] data is empty", !phoneDetails.isEmpty());
		List<String> phoneList = new ArrayList<String>();
		for(int i=0; i<phoneDetails.size(); i++)
		{
			String respPhoneNumber = JsonPath.read(addPhoneNumberToUserProfileResponse, "$.entry.phoneDetails["+i+"].phone");
			phoneList.add(respPhoneNumber);
		}
		
		AssertJUnit.assertTrue(paramAddingPhoneNumber+" doesn't exists in [entry.phoneDetails["+phoneList+"]]", phoneList.contains(paramAddingPhoneNumber));
	//	String uidx = JsonPath.read(addPhoneNumberToUserProfileResponse, "$.entry.uidx");
	//	AssertJUnit.assertTrue(paramUserId+" is not equals to response uidx["+uidx+"]", uidx.equals(paramUserId));
	}
	
	@Test(groups = { "SchemaValidation" }, dataProvider = "IdeaApiDP_addPhoneNumberToUserProfile_verifyResponseDataNodesUsingSchemaValidations")
	public void IdeaApiTests_addPhoneNumberToUserProfile_verifyResponseDataNodesUsingSchemaValidations(String paramAppName, String paramAddingPhoneNumber, 
			String paramAccessKey, String paramFirstName, String paramLastName, String paramUserId, String paramPhoneNumber, String paramGender, String paramDateOfBirth,
			String paramStatusType)
	{
		RequestGenerator addPhoneNumberToUserProfileReqGen = ideaApiHelper.invokeIdeaAddPhoneNumberToUserProfile(paramAppName, paramAddingPhoneNumber, getUidx(paramUserId));
		String addPhoneNumberToUserProfileResponse = addPhoneNumberToUserProfileReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting IdeaApi addPhoneNumberToUserProfile API response :\n\n"+addPhoneNumberToUserProfileResponse);
		log.info("\nPrinting IdeaApi addPhoneNumberToUserProfile API response :\n\n"+addPhoneNumberToUserProfileResponse);
		 
		String responseStatusCode = addPhoneNumberToUserProfileReqGen.respvalidate.NodeValue(StatusNodes.STATUS_CODE.toString(), true).replace("\"", "");
		String responseStatusType = addPhoneNumberToUserProfileReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).replace("\"", "");
		
		if(responseStatusCode.equals(IdeaErrorCodes.ACCOUNT_DOES_NOT_EXISTS.getIdeaErrorCode()))
		{
			RequestGenerator signUpReqGen = ideaApiHelper.invokeIdeaSignUp(paramAppName, paramAccessKey, paramFirstName, paramLastName, paramUserId, paramPhoneNumber, 
					paramGender, paramDateOfBirth);
			String signUpResponse = signUpReqGen.respvalidate.returnresponseasstring();
			System.out.println("\nPrinting IdeaApi signUp API response :\n\n"+signUpResponse);
			log.info("\nPrinting IdeaApi signUp API response :\n\n"+signUpResponse);
			
			responseStatusType = signUpReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), false).replace("\"", "");
			AssertJUnit.assertTrue("IdeaApi signUp API statusType is invalid, Expected: <"+paramStatusType+"> but Actual: <"+responseStatusType+">", 
					responseStatusType.equals(paramStatusType));
			
			addPhoneNumberToUserProfileReqGen = ideaApiHelper.invokeIdeaAddPhoneNumberToUserProfile(paramAppName, paramAddingPhoneNumber, paramUserId);
			addPhoneNumberToUserProfileResponse = addPhoneNumberToUserProfileReqGen.respvalidate.returnresponseasstring();
			System.out.println("\nPrinting IdeaApi addPhoneNumberToUserProfile API response :\n\n"+addPhoneNumberToUserProfileResponse);
			log.info("\nPrinting IdeaApi addPhoneNumberToUserProfile API response :\n\n"+addPhoneNumberToUserProfileResponse);
			
			responseStatusType = addPhoneNumberToUserProfileReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).replace("\"", "");
		}
		
		AssertJUnit.assertTrue("IdeaApi addPhoneNumberToUserProfile API statusType is invalid, Expected: <"+paramStatusType+"> but Actual: <"+responseStatusType+">", 
				responseStatusType.equals(paramStatusType));

		try {
            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/ideaapi-addphonetouserprofile-schema.txt");
            List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(addPhoneNumberToUserProfileResponse, jsonschema);
            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in IdeaApi addPhoneNumberToUserProfile API response", CollectionUtils.isEmpty(missingNodeList));
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	@Test(groups = { "Smoke","FoxSanity", "Sanity", "ProdSanity", "Regression", "MiniRegression", "ExhaustiveRegression" }, dataProvider = "IdeaApiDP_signUp_validateAPIResponse")
	public void IdeaApiTests_signUp_verifySuccessResponse(String paramAppName, String paramAccessKey, String paramFirstName, String paramLastName, String paramEmailId, 
			String paramPhoneNumber, String paramGender, String paramDateOfBirth, String successRespCode)
	{
		RequestGenerator signUpReqGen = ideaApiHelper.invokeIdeaSignUp(paramAppName, paramAccessKey, paramFirstName, paramLastName, paramEmailId, paramPhoneNumber,
				paramGender, paramDateOfBirth);
		String signUpResponse = signUpReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting IdeaApi signUp API response :\n\n"+signUpResponse);
		log.info("\nPrinting IdeaApi signUp API response :\n\n"+signUpResponse);
		 
		AssertJUnit.assertEquals("IdeaApi signUp API is not working", Integer.parseInt(successRespCode), signUpReqGen.response.getStatus());
	}
	
	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression" }, dataProvider = "IdeaApiDP_signUp_verifySuccessStatusInformation")
	public void IdeaApiTests_signUp_verifySuccessStatusInformation(String paramAppName, String paramAccessKey, String paramFirstName, String paramLastName, 
			String paramEmailId, String paramPhoneNumber, String paramGender, String paramDateOfBirth, String paramStatusCode, String paramStatusMsg,
			String paramStatusType)
	{
		RequestGenerator signUpReqGen = ideaApiHelper.invokeIdeaSignUp(paramAppName, paramAccessKey, paramFirstName, paramLastName, paramEmailId, paramPhoneNumber,
				paramGender, paramDateOfBirth);
		String signUpResponse = signUpReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting IdeaApi signUp API response :\n\n"+signUpResponse);
		log.info("\nPrinting IdeaApi signUp API response :\n\n"+signUpResponse);
		 
		String responseStatusCode = signUpReqGen.respvalidate.NodeValue(StatusNodes.STATUS_CODE.toString(), true).replace("\"", "");
		String responseStatusMsg = signUpReqGen.respvalidate.NodeValue(StatusNodes.STATUS_MESSAGE.toString(), true).replace("\"", "");
		String responseStatusType = signUpReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).replace("\"", "");
		
		AssertJUnit.assertTrue("IdeaApi signUp API statusCode is invalid, Expected: <"+paramStatusCode+"> but Actual: <"+responseStatusCode+">", 
				responseStatusCode.equals(paramStatusCode));
		AssertJUnit.assertTrue("IdeaApi signUp API statusMessage is invalid, Expected: <"+paramStatusMsg+"> but Actual: <"+responseStatusMsg+">", 
				responseStatusMsg.equals(paramStatusMsg));
		AssertJUnit.assertTrue("IdeaApi signUp API statusType is invalid, Expected: <"+paramStatusType+"> but Actual: <"+responseStatusType+">",
				responseStatusType.equals(paramStatusType));
	}
	
	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression" }, dataProvider = "IdeaApiDP_signUp_verifyFailureStatusInformation")
	public void IdeaApiTests_signUp_verifyFailureStatusInformation(String paramAppName, String paramAccessKey, String paramFirstName, String paramLastName, 
			String paramEmailId, String paramPhoneNumber, String paramGender, String paramDateOfBirth, String paramStatusCode, String paramStatusMsg, 
			String paramStatusType, String paramCauseErrorCode)
	{
		RequestGenerator signUpReqGen = ideaApiHelper.invokeIdeaSignUp(paramAppName, paramAccessKey, paramFirstName, paramLastName, paramEmailId, paramPhoneNumber,
				paramGender, paramDateOfBirth);
		String signUpResponse = signUpReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting IdeaApi signUp API response :\n\n"+signUpResponse);
		log.info("\nPrinting IdeaApi signUp API response :\n\n"+signUpResponse);
		 
		String responseStatusCode = signUpReqGen.respvalidate.NodeValue(StatusNodes.STATUS_CODE.toString(), true).replace("\"", "");
		String responseStatusMsg = signUpReqGen.respvalidate.NodeValue(StatusNodes.STATUS_MESSAGE.toString(), true).replace("\"", "");
		String responseStatusType = signUpReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).replace("\"", "");
		
		AssertJUnit.assertTrue("IdeaApi signUp API statusCode is invalid, Expected: <"+paramStatusCode+"> but Actual: <"+responseStatusCode+">",  
				responseStatusCode.equals(paramStatusCode));
		AssertJUnit.assertTrue("IdeaApi signUp API statusMessage is invalid, Expected: <"+paramStatusMsg+"> but Actual: <"+responseStatusMsg+">",  
				responseStatusMsg.equals(paramStatusMsg));
		AssertJUnit.assertTrue("IdeaApi signUp API statusType is invalid, Expected: <"+paramStatusType+"> but Actual: <"+responseStatusType+">", 
				responseStatusType.equals(paramStatusType));
		
		if(signUpReqGen.respvalidate.DoesNodeExists(IdeaDataNodes.IDEA_CAUSE_ERROR_CODES_NODE.getIdeaDataNode()))
		{
			String responseCauserErrorCode = JsonPath.read(signUpResponse, "$.causeErrorCodes").toString().replace("[", "").replace("]", ""); 
		    AssertJUnit.assertEquals("ErrorCode Mismatch", paramCauseErrorCode, responseCauserErrorCode);
		} 
	}
	
	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression" }, dataProvider = "IdeaApiDP_signUp_verifyNodeValues")
	public void IdeaApiTests_signUp_verifyNodeValues(String paramAppName, String paramAccessKey, String paramFirstName, String paramLastName, String paramEmailId, 
			String paramPhoneNumber, String paramGender, String paramDateOfBirth, String paramStatusType)
	{
		RequestGenerator signUpReqGen = ideaApiHelper.invokeIdeaSignUp(paramAppName, paramAccessKey, paramFirstName, paramLastName, paramEmailId, paramPhoneNumber,
				paramGender, paramDateOfBirth);
		String signUpResponse = signUpReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting IdeaApi signUp API response :\n\n"+signUpResponse);
		log.info("\nPrinting IdeaApi signUp API response :\n\n"+signUpResponse);
		 
		String responseStatusType = signUpReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).replace("\"", "");
		AssertJUnit.assertTrue("IdeaApi signUp API statusType is invalid, Expected: <"+paramStatusType+"> but Actual: <"+responseStatusType+">", 
				responseStatusType.equals(paramStatusType));
		
		if(signUpReqGen.respvalidate.DoesNodeExists(IdeaDataNodes.IDEA_ENTRY_NODE.getIdeaDataNode()))
		{
			String responseFirstName = signUpReqGen.respvalidate.NodeValue(IdeaDataNodes.IDEA_ENTRY_FIRST_NAME_NODE.getIdeaDataNode(), false).replace("\"", "");
		    AssertJUnit.assertEquals(IdeaDataNodes.IDEA_ENTRY_FIRST_NAME_NODE.getIdeaDataNode()+" Mismatch: ", paramFirstName, responseFirstName);
		    
		    String responseLastName = signUpReqGen.respvalidate.NodeValue(IdeaDataNodes.IDEA_ENTRY_LAST_NAME_NODE.getIdeaDataNode(), false).replace("\"", "");
		    AssertJUnit.assertEquals(IdeaDataNodes.IDEA_ENTRY_LAST_NAME_NODE.getIdeaDataNode()+" Mismatch: ", paramLastName, responseLastName);
		    
		    String responseGender = signUpReqGen.respvalidate.NodeValue(IdeaDataNodes.IDEA_ENTRY_GENDER_NODE.getIdeaDataNode(), false).replace("\"", "");
		    AssertJUnit.assertEquals(IdeaDataNodes.IDEA_ENTRY_GENDER_NODE.getIdeaDataNode()+" Mismatch: ", paramGender, responseGender);
		    
//		    String responseUidx = signUpReqGen.respvalidate.NodeValue(IdeaDataNodes.IDEA_ENTRY_UIDX_NODE.getIdeaDataNode(), false).replace("\"", "");
//		    AssertJUnit.assertEquals(IdeaDataNodes.IDEA_ENTRY_UIDX_NODE.getIdeaDataNode()+" Mismatch: ", paramEmailId, responseUidx);
		    
		    String responseUserType = signUpReqGen.respvalidate.NodeValue(IdeaDataNodes.IDEA_ENTRY_USER_TYPE_NODE.getIdeaDataNode(), false).replace("\"", "");
		    AssertJUnit.assertEquals(IdeaDataNodes.IDEA_ENTRY_USER_TYPE_NODE.getIdeaDataNode()+" Mismatch: ", "REGULAR", responseUserType);
		    
	    	String responseStatus = signUpReqGen.respvalidate.NodeValue(IdeaDataNodes.IDEA_ENTRY_STATUS_NODE.getIdeaDataNode(), false).replace("\"", "");
		    AssertJUnit.assertEquals(IdeaDataNodes.IDEA_ENTRY_STATUS_NODE.getIdeaDataNode()+" Mismatch: ", "ACTIVE", responseStatus);
		    
	    	String responseChannel = signUpReqGen.respvalidate.NodeValue(IdeaDataNodes.IDEA_ENTRY_CHANNEL_NODE.getIdeaDataNode(), false).replace("\"", "");
		    AssertJUnit.assertEquals(IdeaDataNodes.IDEA_ENTRY_CHANNEL_NODE.getIdeaDataNode()+" Mismatch: ", "EMAIL", responseChannel);
		    
		    //String responseNewNode = signUpReqGen.respvalidate.NodeValue(IdeaDataNodes.IDEA_ENTRY_NEW_NODE.getIdeaDataNode(), false).replace("\"", "");
		    //AssertJUnit.assertEquals(IdeaDataNodes.IDEA_ENTRY_NEW_NODE.getIdeaDataNode()+" Mismatch: ", "true", responseNewNode);
		    	
		} else {
			System.out.println(IdeaDataNodes.IDEA_ENTRY_NODE.getIdeaDataNode()+" node doesn't exists");
			log.info(IdeaDataNodes.IDEA_ENTRY_NODE.getIdeaDataNode()+" node doesn't exists");
		}
	}	
	
	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression" }, dataProvider = "IdeaApiDP_signUp_verifyDOBNodeValues")
	public void IdeaApiTests_signUp_verifyDOBNodeValues(String paramAppName, String paramAccessKey, String paramFirstName, String paramLastName, String paramEmailId, 
			String paramPhoneNumber, String paramGender, String paramDateOfBirth, String paramStatusType)
	{
		RequestGenerator signUpReqGen = ideaApiHelper.invokeIdeaSignUp(paramAppName, paramAccessKey, paramFirstName, paramLastName, paramEmailId, paramPhoneNumber,
				paramGender, paramDateOfBirth);
		String signUpResponse = signUpReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting IdeaApi signUp API response :\n\n"+signUpResponse);
		log.info("\nPrinting IdeaApi signUp API response :\n\n"+signUpResponse);
		 
		String responseStatusType = signUpReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).replace("\"", "");
		AssertJUnit.assertTrue("IdeaApi signUp API statusType is invalid, Expected: <"+paramStatusType+"> but Actual: <"+responseStatusType+">", 
				responseStatusType.equals(paramStatusType));
		
		if(signUpReqGen.respvalidate.DoesNodeExists(IdeaDataNodes.IDEA_ENTRY_NODE.getIdeaDataNode()))
		{
			if(signUpReqGen.respvalidate.DoesNodeExists(IdeaDataNodes.IDEA_ENTRY_DATE_OF_BIRTH_NODE.getIdeaDataNode()))
			{
				String dob = signUpReqGen.respvalidate.NodeValue(IdeaDataNodes.IDEA_ENTRY_DATE_OF_BIRTH_NODE.getIdeaDataNode(), false).replace("\"", "");
				
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				try {
					paramDateOfBirth = String.valueOf(df.parse(paramDateOfBirth).getTime()+19800000).replace("\"", "");
					AssertJUnit.assertEquals(IdeaDataNodes.IDEA_ENTRY_DATE_OF_BIRTH_NODE.getIdeaDataNode()+" Mismatch:", paramDateOfBirth, dob);
				} catch (ParseException e) {
					log.error(e);
				}
			} else {
				System.out.println(IdeaDataNodes.IDEA_ENTRY_DATE_OF_BIRTH_NODE.getIdeaDataNode()+" node doesn't exists");
				log.info(IdeaDataNodes.IDEA_ENTRY_DATE_OF_BIRTH_NODE.getIdeaDataNode()+" node doesn't exists");
			}
			
		} else {
			System.out.println(IdeaDataNodes.IDEA_ENTRY_NODE.getIdeaDataNode()+" node doesn't exists");
			log.info(IdeaDataNodes.IDEA_ENTRY_NODE.getIdeaDataNode()+" node doesn't exists");
		}
	}
	
	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression" }, dataProvider = "IdeaApiDP_signUp_verifyEmailDetailsNodeValues")
	public void IdeaApiTests_signUp_verifyEmailDetailsNodeValues(String paramAppName, String paramAccessKey, String paramFirstName, String paramLastName, 
			String paramEmailId, String paramPhoneNumber, String paramGender, String paramDateOfBirth, String paramStatusType)
	{
		RequestGenerator signUpReqGen = ideaApiHelper.invokeIdeaSignUp(paramAppName, paramAccessKey, paramFirstName, paramLastName, paramEmailId, paramPhoneNumber,
				paramGender, paramDateOfBirth);
		String signUpResponse = signUpReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting IdeaApi signUp API response :\n\n"+signUpResponse);
		log.info("\nPrinting IdeaApi signUp API response :\n\n"+signUpResponse);
		 
		String responseStatusType = signUpReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).replace("\"", "");
		AssertJUnit.assertTrue("IdeaApi signUp API statusType is invalid, Expected: <"+paramStatusType+"> but Actual: <"+responseStatusType+">", 
				responseStatusType.equals(paramStatusType));
		
		if(signUpReqGen.respvalidate.DoesNodeExists(IdeaDataNodes.IDEA_ENTRY_NODE.getIdeaDataNode()))
		{
		    List<JSONObject> emailDetails = JsonPath.read(signUpResponse, "$.entry.emailDetails[*]");
			AssertJUnit.assertTrue("[entry.emailDetails] data is empty", !emailDetails.isEmpty());
			List<String> emailList = new ArrayList<String>();
			for(int i=0; i<emailDetails.size(); i++)
			{
				String respEmailId = JsonPath.read(signUpResponse, "$.entry.emailDetails["+i+"].email");
				emailList.add(respEmailId);
			}
			
			AssertJUnit.assertTrue(paramEmailId+" doesn't exists in [entry.emailDetails["+emailList+"]]", emailList.contains(paramEmailId));
			
		} else {
			System.out.println(IdeaDataNodes.IDEA_ENTRY_NODE.getIdeaDataNode()+" node doesn't exists");
			log.info(IdeaDataNodes.IDEA_ENTRY_NODE.getIdeaDataNode()+" node doesn't exists");
		}
	}
	
	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression" }, dataProvider = "IdeaApiDP_signUp_verifyPhoneDetailsNodeValues")
	public void IdeaApiTests_signUp_verifyPhoneDetailsNodeValues(String paramAppName, String paramAccessKey, String paramFirstName, String paramLastName, 
			String paramEmailId, String paramPhoneNumber, String paramGender, String paramDateOfBirth, String paramStatusType)
	{
		RequestGenerator signUpReqGen = ideaApiHelper.invokeIdeaSignUp(paramAppName, paramAccessKey, paramFirstName, paramLastName, paramEmailId, paramPhoneNumber,
				paramGender, paramDateOfBirth);
		String signUpResponse = signUpReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting IdeaApi signUp API response :\n\n"+signUpResponse);
		log.info("\nPrinting IdeaApi signUp API response :\n\n"+signUpResponse);
		 
		String responseStatusType = signUpReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).replace("\"", "");
		AssertJUnit.assertTrue("IdeaApi signUp API statusType is invalid, Expected: <"+paramStatusType+"> but Actual: <"+responseStatusType+">",
				responseStatusType.equals(paramStatusType));
		
		if(signUpReqGen.respvalidate.DoesNodeExists(IdeaDataNodes.IDEA_ENTRY_NODE.getIdeaDataNode()))
		{
		    List<JSONObject> phoneDetails = JsonPath.read(signUpResponse, "$.entry.phoneDetails[*]");
			AssertJUnit.assertTrue("IdeaApi signUp API [entry.phoneDetails] data is empty", !phoneDetails.isEmpty());
			List<String> phoneList = new ArrayList<String>();
			for(int i=0; i<phoneDetails.size(); i++)
			{
				String respPhoneNumber = JsonPath.read(signUpResponse, "$.entry.phoneDetails["+i+"].phone");
				phoneList.add(respPhoneNumber);
			}
			
			AssertJUnit.assertTrue(paramPhoneNumber+" doesn't exists in [entry.phoneDetails["+phoneList+"]]", phoneList.contains(paramPhoneNumber));
			
		} else {
			System.out.println(IdeaDataNodes.IDEA_ENTRY_NODE.getIdeaDataNode()+" node doesn't exists");
			log.info(IdeaDataNodes.IDEA_ENTRY_NODE.getIdeaDataNode()+" node doesn't exists");
		}
	}
	
	@Test(groups = { "SchemaValidation" }, dataProvider = "IdeaApiDP_signUp_verifyResponseDataNodesUsingSchemaValidations")
	public void IdeaApiTests_signUp_verifyResponseDataNodesUsingSchemaValidations(String paramAppName, String paramAccessKey, String paramFirstName, String paramLastName, 
			String paramEmailId, String paramPhoneNumber, String paramGender, String paramDateOfBirth, String paramStatusType)
	{
		RequestGenerator signUpReqGen = ideaApiHelper.invokeIdeaSignUp(paramAppName, paramAccessKey, paramFirstName, paramLastName, paramEmailId, paramPhoneNumber,
				paramGender, paramDateOfBirth);
		String signUpResponse = signUpReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting IdeaApi signUp API response :\n\n"+signUpResponse);
		log.info("\nPrinting IdeaApi signUp API response :\n\n"+signUpResponse);
		 
		String responseStatusType = signUpReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).replace("\"", "");
		AssertJUnit.assertTrue("IdeaApi signUp API statusType is invalid, Expected: <"+paramStatusType+"> but Actual: <"+responseStatusType+">", 
				responseStatusType.equals(paramStatusType));

		try {
            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/ideaapi-signup-schema.txt");
            List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(signUpResponse, jsonschema);
            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in IdeaApi signUp API response", CollectionUtils.isEmpty(missingNodeList));
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	/*
	 * commenting the code as we are not using the signin using phonenumber API. 
	 *
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression", "MiniRegression", "ExhaustiveRegression" }, 
			dataProvider = "IdeaApiDP_signInUsingPhoneNumber_validateAPIResponse")
	public void IdeaApiTests_signInUsingPhoneNumber_verifySuccessResponse(String paramAppName, String paramAccessKey, String paramPhoneNumber, String successRespCode)
	{
		RequestGenerator signInUsingPhoneNumberReqGen = ideaApiHelper.invokeIdeaSignInUsingPhoneNumber(paramAppName, paramAccessKey, paramPhoneNumber);
		String signInUsingPhoneNumberResponse = signInUsingPhoneNumberReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting IdeaApi signInUsingPhoneNumber API response :\n\n"+signInUsingPhoneNumberResponse);
		log.info("\nPrinting IdeaApi signInUsingPhoneNumber API response :\n\n"+signInUsingPhoneNumberResponse);
		 
		AssertJUnit.assertEquals("IdeaApi signInUsingPhoneNumber API is not working", Integer.parseInt(successRespCode), signInUsingPhoneNumberReqGen.response.getStatus());
	}
	
	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression" },
			dataProvider = "IdeaApiDP_signInUsingPhoneNumber_verifySuccessStatusInformation")
	public void IdeaApiTests_signInUsingPhoneNumber_verifySuccessStatusInformation(String paramAppName, String paramAccessKey, String paramFirstName,
			String paramLastName, String paramUserId, String paramPhoneNumber, String paramGender, String paramDateOfBirth, String paramStatusCode, String paramStatusMsg, 
			String paramStatusType)
	{
		RequestGenerator signInUsingPhoneNumberReqGen = ideaApiHelper.invokeIdeaSignInUsingPhoneNumber(paramAppName, paramAccessKey, paramPhoneNumber);
		String signInUsingPhoneNumberResponse = signInUsingPhoneNumberReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting IdeaApi signInUsingPhoneNumber API response :\n\n"+signInUsingPhoneNumberResponse);
		log.info("\nPrinting IdeaApi signInUsingPhoneNumber API response :\n\n"+signInUsingPhoneNumberResponse);
		
		String responseStatusCode = signInUsingPhoneNumberReqGen.respvalidate.NodeValue(StatusNodes.STATUS_CODE.toString(), true).replace("\"", "");
		System.out.println(responseStatusCode);
		String responseStatusMsg = signInUsingPhoneNumberReqGen.respvalidate.NodeValue(StatusNodes.STATUS_MESSAGE.toString(), true).replace("\"", "");
		System.out.println(responseStatusMsg);
		String responseStatusType = signInUsingPhoneNumberReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).replace("\"", "");
		System.out.println(responseStatusType);
		
		//if(responseStatusCode.equals(IdeaErrorCodes.ACCOUNT_DOES_NOT_EXISTS.getIdeaErrorCode()))
		if(responseStatusMsg.contains("Account Doesn't exists"))
		{
			RequestGenerator signUpReqGen = ideaApiHelper.invokeIdeaSignUp(paramAppName, paramAccessKey, paramFirstName, paramLastName, paramUserId, paramPhoneNumber, 
					paramGender, paramDateOfBirth);
			String signUpResponse = signUpReqGen.respvalidate.returnresponseasstring();
			System.out.println("\nPrinting IdeaApi signUp API response :\n\n"+signUpResponse);
			log.info("\nPrinting IdeaApi signUp API response :\n\n"+signUpResponse);
			
			responseStatusType = signUpReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), false).replace("\"", "");
			AssertJUnit.assertTrue("IdeaApi signUp API statusType is invalid, Expected: <"+paramStatusType+"> but Actual: <"+responseStatusType+">", 
					responseStatusType.equals(paramStatusType));
			
			signInUsingPhoneNumberReqGen = ideaApiHelper.invokeIdeaSignInUsingPhoneNumber(paramAppName, paramAccessKey, paramPhoneNumber);
			signInUsingPhoneNumberResponse = signInUsingPhoneNumberReqGen.respvalidate.returnresponseasstring();
			System.out.println("\nPrinting IdeaApi signInUsingPhoneNumber API response :\n\n"+signInUsingPhoneNumberResponse);
			log.info("\nPrinting IdeaApi signInUsingPhoneNumber API response :\n\n"+signInUsingPhoneNumberResponse);
			
			responseStatusCode = signInUsingPhoneNumberReqGen.respvalidate.NodeValue(StatusNodes.STATUS_CODE.toString(), true).replace("\"", "");
			responseStatusMsg = signInUsingPhoneNumberReqGen.respvalidate.NodeValue(StatusNodes.STATUS_MESSAGE.toString(), true).replace("\"", "");
			responseStatusType = signInUsingPhoneNumberReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).replace("\"", "");
		}
		
		AssertJUnit.assertTrue("IdeaApi signInUsingPhoneNumber API statusCode is invalid, Expected: <"+paramStatusCode+"> but Actual: <"+responseStatusCode+">",  
				responseStatusCode.equals(paramStatusCode));
		AssertJUnit.assertTrue("IdeaApi signInUsingPhoneNumber API statusMessage is invalid, Expected: <"+paramStatusMsg+"> but Actual: <"+responseStatusMsg+">",  
				responseStatusMsg.equals(paramStatusMsg));
		AssertJUnit.assertTrue("IdeaApi signInUsingPhoneNumber API statusType is invalid, Expected: <"+paramStatusType+"> but Actual: <"+responseStatusType+">", 
				responseStatusType.equals(paramStatusType));
	}
	
	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression" }, dataProvider = "IdeaApiDP_signInUsingPhoneNumber_verifyFailureStatusInformation")
	public void IdeaApiTests_signInUsingPhoneNumber_verifyFailureStatusInformation(String paramAppName, String paramAccessKey, String paramPhoneNumber, 
			String paramStatusCode, String paramStatusMsg, String paramStatusType)
	{
		RequestGenerator signInUsingPhoneNumberReqGen = ideaApiHelper.invokeIdeaSignInUsingPhoneNumber(paramAppName, paramAccessKey, paramPhoneNumber);
		String signInUsingPhoneNumberResponse = signInUsingPhoneNumberReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting IdeaApi signInUsingPhoneNumber API response :\n\n"+signInUsingPhoneNumberResponse);
		log.info("\nPrinting IdeaApi signInUsingPhoneNumber API response :\n\n"+signInUsingPhoneNumberResponse);
		 
		String responseStatusCode = signInUsingPhoneNumberReqGen.respvalidate.NodeValue(StatusNodes.STATUS_CODE.toString(), true).replace("\"", "");
		String responseStatusMsg = signInUsingPhoneNumberReqGen.respvalidate.NodeValue(StatusNodes.STATUS_MESSAGE.toString(), true).replace("\"", "");
		String responseStatusType = signInUsingPhoneNumberReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).replace("\"", "");
		
		AssertJUnit.assertTrue("IdeaApi signInUsingPhoneNumber API statusCode is invalid, Expected: <"+paramStatusCode+"> but Actual: <"+responseStatusCode+">",  
				responseStatusCode.equals(paramStatusCode));
		AssertJUnit.assertTrue("IdeaApi signInUsingPhoneNumber API statusMessage is invalid, Expected: <"+paramStatusMsg+"> but Actual: <"+responseStatusMsg+">",  
				responseStatusMsg.equals(paramStatusMsg));
		AssertJUnit.assertTrue("IdeaApi signInUsingPhoneNumber API statusType is invalid, Expected: <"+paramStatusType+"> but Actual: <"+responseStatusType+">", 
				responseStatusType.equals(paramStatusType));
	}
	
	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression" }, dataProvider = "IdeaApiDP_signInUsingPhoneNumber_verifyNodeValues")
	public void IdeaApiTests_signInUsingPhoneNumber_verifyNodeValues(String paramAppName, String paramAccessKey, String paramFirstName, String paramLastName,
			String paramUserId, String paramPhoneNumber, String paramGender, String paramDateOfBirth, String paramStatusType)
	{
		RequestGenerator signInUsingPhoneNumberReqGen = ideaApiHelper.invokeIdeaSignInUsingPhoneNumber(paramAppName, paramAccessKey, paramPhoneNumber);
		String signInUsingPhoneNumberResponse = signInUsingPhoneNumberReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting IdeaApi signInUsingPhoneNumber API response :\n\n"+signInUsingPhoneNumberResponse);
		log.info("\nPrinting IdeaApi signInUsingPhoneNumber API response :\n\n"+signInUsingPhoneNumberResponse);
		
		String responseStatusCode = signInUsingPhoneNumberReqGen.respvalidate.NodeValue(StatusNodes.STATUS_CODE.toString(), true).replace("\"", "");
		String responseStatusType = signInUsingPhoneNumberReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).replace("\"", "");
		
		if(responseStatusCode.equals(String.valueOf(IdeaErrorCodes.ACCOUNT_DOES_NOT_EXISTS.getIdeaErrorCode())))
		{
			RequestGenerator signUpReqGen = ideaApiHelper.invokeIdeaSignUp(paramAppName, paramAccessKey, paramFirstName, paramLastName, paramUserId, paramPhoneNumber, 
					paramGender, paramDateOfBirth);
			String signUpResponse = signUpReqGen.respvalidate.returnresponseasstring();
			System.out.println("\nPrinting IdeaApi signUp API response :\n\n"+signUpResponse);
			log.info("\nPrinting IdeaApi signUp API response :\n\n"+signUpResponse);
			
			responseStatusType = signUpReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), false).replace("\"", "");
			AssertJUnit.assertTrue("IdeaApi signUp API statusType is invalid, Expected: <"+paramStatusType+"> but Actual: <"+responseStatusType+">", 
					responseStatusType.equals(paramStatusType));
			
			signInUsingPhoneNumberReqGen = ideaApiHelper.invokeIdeaSignInUsingPhoneNumber(paramAppName, paramAccessKey, paramPhoneNumber);
			signInUsingPhoneNumberResponse = signInUsingPhoneNumberReqGen.respvalidate.returnresponseasstring();
			System.out.println("\nPrinting IdeaApi signInUsingPhoneNumber API response :\n\n"+signInUsingPhoneNumberResponse);
			log.info("\nPrinting IdeaApi signInUsingPhoneNumber API response :\n\n"+signInUsingPhoneNumberResponse);
			
			responseStatusType = signInUsingPhoneNumberReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).replace("\"", "");
		}
		
		AssertJUnit.assertTrue("IdeaApi signInUsingPhoneNumber API statusType is invalid, Expected: <"+paramStatusType+"> but Actual: <"+responseStatusType+">", 
				responseStatusType.equals(paramStatusType));

		List<JSONObject> phoneDetails = JsonPath.read(signInUsingPhoneNumberResponse, "$.entry.phoneDetails[*]");
		AssertJUnit.assertTrue("IdeaApi signInUsingPhoneNumber API [entry.phoneDetails] data is empty", !phoneDetails.isEmpty());
		List<String> phoneList = new ArrayList<String>();
		for(int i=0; i<phoneDetails.size(); i++)
		{
			String respPhoneNumber = JsonPath.read(signInUsingPhoneNumberResponse, "$.entry.phoneDetails["+i+"].phone");
			phoneList.add(respPhoneNumber);
		}
		
		AssertJUnit.assertTrue(paramPhoneNumber+" doesn't exists in [entry.phoneDetails["+phoneList+"]]", phoneList.contains(paramPhoneNumber));
	}
	
	@Test(groups = { "SchemaValidation" }, dataProvider = "IdeaApiDP_signInUsingPhoneNumber_verifyResponseDataNodesUsingSchemaValidations")
	public void IdeaApiTests_signInUsingPhoneNumber_verifyResponseDataNodesUsingSchemaValidations(String paramAppName, String paramAccessKey, String paramFirstName, 
			String paramLastName, String paramUserId, String paramPhoneNumber, String paramGender, String paramDateOfBirth, String paramStatusType)
	{
		RequestGenerator signInUsingPhoneNumberReqGen = ideaApiHelper.invokeIdeaSignInUsingPhoneNumber(paramAppName, paramAccessKey, paramPhoneNumber);
		String signInUsingPhoneNumberResponse = signInUsingPhoneNumberReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting IdeaApi signInUsingPhoneNumber API response :\n\n"+signInUsingPhoneNumberResponse);
		log.info("\nPrinting IdeaApi signInUsingPhoneNumber API response :\n\n"+signInUsingPhoneNumberResponse);
		
		String responseStatusCode = signInUsingPhoneNumberReqGen.respvalidate.NodeValue(StatusNodes.STATUS_CODE.toString(), true).replace("\"", "");
		String responseStatusType = signInUsingPhoneNumberReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).replace("\"", "");
		
		if(responseStatusCode.equals(IdeaErrorCodes.ACCOUNT_DOES_NOT_EXISTS.getIdeaErrorCode()))
		{
			RequestGenerator signUpReqGen = ideaApiHelper.invokeIdeaSignUp(paramAppName, paramAccessKey, paramFirstName, paramLastName, paramUserId, paramPhoneNumber, 
					paramGender, paramDateOfBirth);
			String signUpResponse = signUpReqGen.respvalidate.returnresponseasstring();
			System.out.println("\nPrinting IdeaApi signUp API response :\n\n"+signUpResponse);
			log.info("\nPrinting IdeaApi signUp API response :\n\n"+signUpResponse);
			
			responseStatusType = signUpReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), false).replace("\"", "");
			AssertJUnit.assertTrue("IdeaApi signUp API statusType is invalid, Expected: <"+paramStatusType+"> but Actual: <"+responseStatusType+">", 
					responseStatusType.equals(paramStatusType));
			
			signInUsingPhoneNumberReqGen = ideaApiHelper.invokeIdeaSignInUsingPhoneNumber(paramAppName, paramAccessKey, paramPhoneNumber);
			signInUsingPhoneNumberResponse = signInUsingPhoneNumberReqGen.respvalidate.returnresponseasstring();
			System.out.println("\nPrinting IdeaApi signInUsingPhoneNumber API response :\n\n"+signInUsingPhoneNumberResponse);
			log.info("\nPrinting IdeaApi signInUsingPhoneNumber API response :\n\n"+signInUsingPhoneNumberResponse);
			
			responseStatusType = signInUsingPhoneNumberReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).replace("\"", "");
		}
		
		AssertJUnit.assertTrue("IdeaApi signInUsingPhoneNumber API statusType is invalid, Expected: <"+paramStatusType+"> but Actual: <"+responseStatusType+">", 
				responseStatusType.equals(paramStatusType));

		try {
            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/ideaapi-signinusingphonenumber-schema.txt");
            List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(signInUsingPhoneNumberResponse, jsonschema);
            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in IdeaApi signInUsingPhoneNumber API response", CollectionUtils.isEmpty(missingNodeList));
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	*/
	
	@Test(groups = { "Smoke", "FoxSanity","Sanity", "ProdSanity", "Regression", "MiniRegression", "ExhaustiveRegression" }, 
			dataProvider = "IdeaApiDP_signInUsingEmail_validateAPIResponse")
	public void IdeaApiTests_signInUsingEmail_verifySuccessResponse(String paramAppName, String paramAccessKey, String paramEmail, String successRespCode)
	{
		RequestGenerator signInUsingEmailReqGen = ideaApiHelper.invokeIdeaSignInUsingEmail(paramAppName, paramAccessKey, paramEmail);
		String signInUsingEmailResponse = signInUsingEmailReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting IdeaApi signInUsingEmail API response :\n\n"+signInUsingEmailResponse);
		log.info("\nPrinting IdeaApi signInUsingEmail API response :\n\n"+signInUsingEmailResponse);
		 
		AssertJUnit.assertEquals("IdeaApi signInUsingEmail API is not working", Integer.parseInt(successRespCode), signInUsingEmailReqGen.response.getStatus());
	}
	
	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression" }, dataProvider = "IdeaApiDP_signInUsingEmail_verifySuccessStatusInformation")
	public void IdeaApiTests_signInUsingEmail_verifySuccessStatusInformation(String paramAppName, String paramAccessKey, String paramFirstName, String paramLastName, 
			String paramEmailId, String paramPhoneNumber, String paramGender, String paramDateOfBirth, String paramStatusCode, String paramStatusMsg, 
			String paramStatusType)
	{
		RequestGenerator signInUsingEmailReqGen = ideaApiHelper.invokeIdeaSignInUsingEmail(paramAppName, paramAccessKey, paramEmailId);
		String signInUsingEmailResponse = signInUsingEmailReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting IdeaApi signInUsingEmail API response :\n\n"+signInUsingEmailResponse);
		log.info("\nPrinting IdeaApi signInUsingEmail API response :\n\n"+signInUsingEmailResponse);
		 
		String responseStatusCode = signInUsingEmailReqGen.respvalidate.NodeValue(StatusNodes.STATUS_CODE.toString(), true).replace("\"", "");
		String responseStatusMsg = signInUsingEmailReqGen.respvalidate.NodeValue(StatusNodes.STATUS_MESSAGE.toString(), true).replace("\"", "");
		String responseStatusType = signInUsingEmailReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).replace("\"", "");
		
		if(responseStatusCode.equals(IdeaErrorCodes.ACCOUNT_DOES_NOT_EXISTS.getIdeaErrorCode()))
		{
			RequestGenerator signUpReqGen = ideaApiHelper.invokeIdeaSignUp(paramAppName, paramAccessKey, paramFirstName, paramLastName, paramEmailId, paramPhoneNumber, 
					paramGender, paramDateOfBirth);
			String signUpResponse = signUpReqGen.respvalidate.returnresponseasstring();
			System.out.println("\nPrinting IdeaApi signUp API response :\n\n"+signUpResponse);
			log.info("\nPrinting IdeaApi signUp API response :\n\n"+signUpResponse);
			
			responseStatusType = signUpReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), false).replace("\"", "");
			AssertJUnit.assertTrue("IdeaApi signUp API statusType is invalid, Expected: <"+paramStatusType+"> but Actual: <"+responseStatusType+">", 
					responseStatusType.equals(paramStatusType));
			
			signInUsingEmailReqGen = ideaApiHelper.invokeIdeaSignInUsingEmail(paramAppName, paramAccessKey, paramEmailId);
			signInUsingEmailResponse = signInUsingEmailReqGen.respvalidate.returnresponseasstring();
			System.out.println("\nPrinting IdeaApi signInUsingEmail API response :\n\n"+signInUsingEmailResponse);
			log.info("\nPrinting IdeaApi signInUsingEmail API response :\n\n"+signInUsingEmailResponse);
			
			responseStatusCode = signInUsingEmailReqGen.respvalidate.NodeValue(StatusNodes.STATUS_CODE.toString(), true).replace("\"", "");
			responseStatusMsg = signInUsingEmailReqGen.respvalidate.NodeValue(StatusNodes.STATUS_MESSAGE.toString(), true).replace("\"", "");
			responseStatusType = signInUsingEmailReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).replace("\"", "");
		}
		
		AssertJUnit.assertTrue("IdeaApi signInUsingEmail API statusCode is invalid, Expected: <"+paramStatusCode+"> but Actual: <"+responseStatusCode+">",  
				responseStatusCode.equals(paramStatusCode));
		AssertJUnit.assertTrue("IdeaApi signInUsingEmail API statusMessage is invalid, Expected: <"+paramStatusMsg+"> but Actual: <"+responseStatusMsg+">",  
				responseStatusMsg.equals(paramStatusMsg));
		AssertJUnit.assertTrue("IdeaApi signInUsingEmail API statusType is invalid, Expected: <"+paramStatusType+"> but Actual: <"+responseStatusType+">", 
				responseStatusType.equals(paramStatusType));
	}

	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression" }, dataProvider = "IdeaApiDP_signInUsingEmail_verifyFailureStatusInformation")
	public void IdeaApiTests_signInUsingEmail_verifyFailureStatusInformation(String paramAppName, String paramAccessKey, String paramEmailId, String paramStatusCode, 
			String paramStatusMsg, String paramStatusType)
	{
		RequestGenerator signInUsingEmailReqGen = ideaApiHelper.invokeIdeaSignInUsingEmail(paramAppName, paramAccessKey, paramEmailId);
		String signInUsingEmailResponse = signInUsingEmailReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting IdeaApi signInUsingEmail API response :\n\n"+signInUsingEmailResponse);
		log.info("\nPrinting IdeaApi signInUsingEmail API response :\n\n"+signInUsingEmailResponse);
		 
		String responseStatusCode = signInUsingEmailReqGen.respvalidate.NodeValue(StatusNodes.STATUS_CODE.toString(), true).replace("\"", "");
		String responseStatusMsg = signInUsingEmailReqGen.respvalidate.NodeValue(StatusNodes.STATUS_MESSAGE.toString(), true).replace("\"", "");
		String responseStatusType = signInUsingEmailReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).replace("\"", "");

		AssertJUnit.assertTrue("IdeaApi signInUsingEmail API statusCode is invalid, Expected: <"+paramStatusCode+"> but Actual: <"+responseStatusCode+">",  
				responseStatusCode.equals(paramStatusCode));
		AssertJUnit.assertTrue("IdeaApi signInUsingEmail API statusMessage is invalid, Expected: <"+paramStatusMsg+"> but Actual: <"+responseStatusMsg+">",  
				responseStatusMsg.equals(paramStatusMsg));
		AssertJUnit.assertTrue("IdeaApi signInUsingEmail API statusType is invalid, Expected: <"+paramStatusType+"> but Actual: <"+responseStatusType+">", 
				responseStatusType.equals(paramStatusType));
	}
	
	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression" }, dataProvider = "IdeaApiDP_signInUsingEmail_verifyNodeValues")
	public void IdeaApiTests_signInUsingEmail_verifyNodeValues(String paramAppName, String paramAccessKey, String paramFirstName, String paramLastName, 
			String paramEmailId, String paramPhoneNumber, String paramGender, String paramDateOfBirth, String paramStatusType)
	{
		RequestGenerator signInUsingEmailReqGen = ideaApiHelper.invokeIdeaSignInUsingEmail(paramAppName, paramAccessKey, paramEmailId);
		String signInUsingEmailResponse = signInUsingEmailReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting IdeaApi signInUsingEmail API response :\n\n"+signInUsingEmailResponse);
		log.info("\nPrinting IdeaApi signInUsingEmail API response :\n\n"+signInUsingEmailResponse);
		 
		String responseStatusCode = signInUsingEmailReqGen.respvalidate.NodeValue(StatusNodes.STATUS_CODE.toString(), true).replace("\"", "");
		String responseStatusType = signInUsingEmailReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).replace("\"", "");
		
		if(responseStatusCode.equals(IdeaErrorCodes.ACCOUNT_DOES_NOT_EXISTS.getIdeaErrorCode()))
		{
			RequestGenerator signUpReqGen = ideaApiHelper.invokeIdeaSignUp(paramAppName, paramAccessKey, paramFirstName, paramLastName, paramEmailId, paramPhoneNumber, 
					paramGender, paramDateOfBirth);
			String signUpResponse = signUpReqGen.respvalidate.returnresponseasstring();
			System.out.println("\nPrinting IdeaApi signUp API response :\n\n"+signUpResponse);
			log.info("\nPrinting IdeaApi signUp API response :\n\n"+signUpResponse);
			
			responseStatusType = signUpReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), false).replace("\"", "");
			AssertJUnit.assertTrue("IdeaApi signUp API statusType is invalid, Expected: <"+paramStatusType+"> but Actual: <"+responseStatusType+">", 
					responseStatusType.equals(paramStatusType));
			
			signInUsingEmailReqGen = ideaApiHelper.invokeIdeaSignInUsingEmail(paramAppName, paramAccessKey, paramEmailId);
			signInUsingEmailResponse = signInUsingEmailReqGen.respvalidate.returnresponseasstring();
			System.out.println("\nPrinting IdeaApi signInUsingEmail API response :\n\n"+signInUsingEmailResponse);
			log.info("\nPrinting IdeaApi signInUsingEmail API response :\n\n"+signInUsingEmailResponse);
			
			responseStatusType = signInUsingEmailReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).replace("\"", "");
		}
		
		AssertJUnit.assertTrue("IdeaApi signInUsingEmail API statusType is invalid, Expected: <"+paramStatusType+"> but Actual: <"+responseStatusType+">", 
				responseStatusType.equals(paramStatusType));
		
		List<JSONObject> emailDetails = JsonPath.read(signInUsingEmailResponse, "$.entry.emailDetails[*]");
		AssertJUnit.assertTrue("IdeaApi signInUsingEmail API [entry.emailDetails] data is empty", !emailDetails.isEmpty());
		List<String> emailList = new ArrayList<String>();
		for(int i=0; i<emailDetails.size(); i++)
		{
			String respEmail = JsonPath.read(signInUsingEmailResponse, "$.entry.emailDetails["+i+"].email");
			emailList.add(respEmail);
		}
		
		AssertJUnit.assertTrue(paramEmailId+" doesn't exists in [entry.emailDetails["+emailList+"]]", emailList.contains(paramEmailId));
	}
	
	@Test(groups = { "SchemaValidation" }, dataProvider = "IdeaApiDP_signInUsingEmail_verifyResponseDataNodesUsingSchemaValidations")
	public void IdeaApiTests_signInUsingEmail_verifyResponseDataNodesUsingSchemaValidations(String paramAppName, String paramAccessKey, String paramFirstName, 
			String paramLastName, String paramEmailId, String paramPhoneNumber, String paramGender, String paramDateOfBirth, String paramStatusType)
	{
		RequestGenerator signInUsingEmailReqGen = ideaApiHelper.invokeIdeaSignInUsingEmail(paramAppName, paramAccessKey, paramEmailId);
		String signInUsingEmailResponse = signInUsingEmailReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting IdeaApi signInUsingEmail API response :\n\n"+signInUsingEmailResponse);
		log.info("\nPrinting IdeaApi signInUsingEmail API response :\n\n"+signInUsingEmailResponse);
		 
		String responseStatusCode = signInUsingEmailReqGen.respvalidate.NodeValue(StatusNodes.STATUS_CODE.toString(), true).replace("\"", "");
		String responseStatusType = signInUsingEmailReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).replace("\"", "");
		
		if(responseStatusCode.equals(IdeaErrorCodes.ACCOUNT_DOES_NOT_EXISTS.getIdeaErrorCode()))
		{
			RequestGenerator signUpReqGen = ideaApiHelper.invokeIdeaSignUp(paramAppName, paramAccessKey, paramFirstName, paramLastName, paramEmailId, paramPhoneNumber, 
					paramGender, paramDateOfBirth);
			String signUpResponse = signUpReqGen.respvalidate.returnresponseasstring();
			System.out.println("\nPrinting IdeaApi signUp API response :\n\n"+signUpResponse);
			log.info("\nPrinting IdeaApi signUp API response :\n\n"+signUpResponse);
			
			responseStatusType = signUpReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), false).replace("\"", "");
			AssertJUnit.assertTrue("IdeaApi signUp API statusType is invalid, Expected: <"+paramStatusType+"> but Actual: <"+responseStatusType+">", 
					responseStatusType.equals(paramStatusType));
			
			signInUsingEmailReqGen = ideaApiHelper.invokeIdeaSignInUsingEmail(paramAppName, paramAccessKey, paramEmailId);
			signInUsingEmailResponse = signInUsingEmailReqGen.respvalidate.returnresponseasstring();
			System.out.println("\nPrinting IdeaApi signInUsingEmail API response :\n\n"+signInUsingEmailResponse);
			log.info("\nPrinting IdeaApi signInUsingEmail API response :\n\n"+signInUsingEmailResponse);
			
			responseStatusType = signInUsingEmailReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).replace("\"", "");
		}
		
		AssertJUnit.assertTrue("IdeaApi signInUsingEmail API statusType is invalid, Expected: <"+paramStatusType+"> but Actual: <"+responseStatusType+">", 
				responseStatusType.equals(paramStatusType));
		
		try {
            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/ideaapi-signinusingemail-schema.txt");
            List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(signInUsingEmailResponse, jsonschema);
            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in IdeaApi signInUsingEmail API response", CollectionUtils.isEmpty(missingNodeList));
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression", "MiniRegression", "ExhaustiveRegression" }, 
			dataProvider = "IdeaApiDP_mobileSignInUsingEmail_validateAPIResponse")
	public void IdeaApiTests_mobileSignInUsingEmail_verifySuccessResponse(String paramAppName, String paramAccessKey, String paramEmail, String successRespCode)
	{
		RequestGenerator mobileSignInUsingEmailReqGen = ideaApiHelper.invokeIdeaMobileSignInUsingEmail(paramAppName, paramAccessKey, paramEmail);
		String mobileSignInUsingEmailResponse = mobileSignInUsingEmailReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting IdeaApi mobileSignInUsingEmail API response :\n\n"+mobileSignInUsingEmailResponse);
		log.info("\nPrinting IdeaApi mobileSignInUsingEmail API response :\n\n"+mobileSignInUsingEmailResponse);
		 
		AssertJUnit.assertEquals("IdeaApi mobileSignInUsingEmail API is not working", Integer.parseInt(successRespCode), mobileSignInUsingEmailReqGen.response.getStatus());
	}
	
	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression" }, dataProvider = "IdeaApiDP_mobileSignInUsingEmail_verifySuccessStatusInformation")
	public void IdeaApiTests_mobileSignInUsingEmail_verifySuccessStatusInformation(String paramAppName, String paramAccessKey, String paramFirstName, String paramLastName, 
			String paramEmailId, String paramPhoneNumber, String paramGender, String paramDateOfBirth, String paramStatusCode, String paramStatusMsg, 
			String paramStatusType)
	{
		RequestGenerator mobileSignInUsingEmailReqGen = ideaApiHelper.invokeIdeaMobileSignInUsingEmail(paramAppName, paramAccessKey, paramEmailId);
		String mobileSignInUsingEmailResponse = mobileSignInUsingEmailReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting IdeaApi mobileSignInUsingEmail API response :\n\n"+mobileSignInUsingEmailResponse);
		log.info("\nPrinting IdeaApi mobileSignInUsingEmail API response :\n\n"+mobileSignInUsingEmailResponse);
		 
		String responseStatusCode = mobileSignInUsingEmailReqGen.respvalidate.NodeValue(StatusNodes.STATUS_CODE.toString(), true).replace("\"", "");
		String responseStatusMsg = mobileSignInUsingEmailReqGen.respvalidate.NodeValue(StatusNodes.STATUS_MESSAGE.toString(), true).replace("\"", "");
		String responseStatusType = mobileSignInUsingEmailReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).replace("\"", "");
		
		if(responseStatusCode.equals(IdeaErrorCodes.ACCOUNT_DOES_NOT_EXISTS.getIdeaErrorCode()))
		{
			RequestGenerator signUpReqGen = ideaApiHelper.invokeIdeaSignUp(paramAppName, paramAccessKey, paramFirstName, paramLastName, paramEmailId, paramPhoneNumber, 
					paramGender, paramDateOfBirth);
			String signUpResponse = signUpReqGen.respvalidate.returnresponseasstring();
			System.out.println("\nPrinting IdeaApi signUp API response :\n\n"+signUpResponse);
			log.info("\nPrinting IdeaApi signUp API response :\n\n"+signUpResponse);
			
			responseStatusType = signUpReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), false).replace("\"", "");
			AssertJUnit.assertTrue("IdeaApi signUp API statusType is invalid, Expected: <"+paramStatusType+"> but Actual: <"+responseStatusType+">", 
					responseStatusType.equals(paramStatusType));
			
			mobileSignInUsingEmailReqGen = ideaApiHelper.invokeIdeaMobileSignInUsingEmail(paramAppName, paramAccessKey, paramEmailId);
			mobileSignInUsingEmailResponse = mobileSignInUsingEmailReqGen.respvalidate.returnresponseasstring();
			System.out.println("\nPrinting IdeaApi mobileSignInUsingEmail API response :\n\n"+mobileSignInUsingEmailResponse);
			log.info("\nPrinting IdeaApi mobileSignInUsingEmail API response :\n\n"+mobileSignInUsingEmailResponse);
			
			responseStatusCode = mobileSignInUsingEmailReqGen.respvalidate.NodeValue(StatusNodes.STATUS_CODE.toString(), true).replace("\"", "");
			responseStatusMsg = mobileSignInUsingEmailReqGen.respvalidate.NodeValue(StatusNodes.STATUS_MESSAGE.toString(), true).replace("\"", "");
			responseStatusType = mobileSignInUsingEmailReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).replace("\"", "");
		}
		
		AssertJUnit.assertTrue("IdeaApi mobileSignInUsingEmail API statusCode is invalid, Expected: <"+paramStatusCode+"> but Actual: <"+responseStatusCode+">",  
				responseStatusCode.equals(paramStatusCode));
		AssertJUnit.assertTrue("IdeaApi mobileSignInUsingEmail API statusMessage is invalid, Expected: <"+paramStatusMsg+"> but Actual: <"+responseStatusMsg+">",  
				responseStatusMsg.equals(paramStatusMsg));
		AssertJUnit.assertTrue("IdeaApi mobileSignInUsingEmail API statusType is invalid, Expected: <"+paramStatusType+"> but Actual: <"+responseStatusType+">", 
				responseStatusType.equals(paramStatusType));
	}
	
	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression" }, dataProvider = "IdeaApiDP_mobileSignInUsingEmail_verifyFailureStatusInformation")
	public void IdeaApiTests_mobileSignInUsingEmail_verifyFailureStatusInformation(String paramAppName, String paramAccessKey, String paramEmailId, String paramStatusCode, 
			String paramStatusMsg, String paramStatusType)
	{
		RequestGenerator mobileSignInUsingEmailReqGen = ideaApiHelper.invokeIdeaMobileSignInUsingEmail(paramAppName, paramAccessKey, paramEmailId);
		String mobileSignInUsingEmailResponse = mobileSignInUsingEmailReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting IdeaApi mobileSignInUsingEmail API response :\n\n"+mobileSignInUsingEmailResponse);
		log.info("\nPrinting IdeaApi mobileSignInUsingEmail API response :\n\n"+mobileSignInUsingEmailResponse);
		 
		String responseStatusCode = mobileSignInUsingEmailReqGen.respvalidate.NodeValue(StatusNodes.STATUS_CODE.toString(), true).replace("\"", "");
		String responseStatusMsg = mobileSignInUsingEmailReqGen.respvalidate.NodeValue(StatusNodes.STATUS_MESSAGE.toString(), true).replace("\"", "");
		String responseStatusType = mobileSignInUsingEmailReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).replace("\"", "");

		AssertJUnit.assertTrue("IdeaApi mobileSignInUsingEmail API statusCode is invalid, Expected: <"+paramStatusCode+"> but Actual: <"+responseStatusCode+">",  
				responseStatusCode.equals(paramStatusCode));
		AssertJUnit.assertTrue("IdeaApi mobileSignInUsingEmail API statusMessage is invalid, Expected: <"+paramStatusMsg+"> but Actual: <"+responseStatusMsg+">",  
				responseStatusMsg.equals(paramStatusMsg));
		AssertJUnit.assertTrue("IdeaApi mobileSignInUsingEmail API statusType is invalid, Expected: <"+paramStatusType+"> but Actual: <"+responseStatusType+">", 
				responseStatusType.equals(paramStatusType));
	}
	
	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression" }, dataProvider = "IdeaApiDP_mobileSignInUsingEmail_verifyNodeValues")
	public void IdeaApiTests_mobileSignInUsingEmail_verifyNodeValues(String paramAppName, String paramAccessKey, String paramFirstName, String paramLastName, 
			String paramEmailId, String paramPhoneNumber, String paramGender, String paramDateOfBirth, String paramStatusType)
	{
		RequestGenerator mobileSignInUsingEmailReqGen = ideaApiHelper.invokeIdeaMobileSignInUsingEmail(paramAppName, paramAccessKey, paramEmailId);
		String mobileSignInUsingEmailResponse = mobileSignInUsingEmailReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting IdeaApi mobileSignInUsingEmail API response :\n\n"+mobileSignInUsingEmailResponse);
		log.info("\nPrinting IdeaApi mobileSignInUsingEmail API response :\n\n"+mobileSignInUsingEmailResponse);
		 
		String responseStatusCode = mobileSignInUsingEmailReqGen.respvalidate.NodeValue(StatusNodes.STATUS_CODE.toString(), true).replace("\"", "");
		String responseStatusType = mobileSignInUsingEmailReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).replace("\"", "");
		
		if(responseStatusCode.equals(IdeaErrorCodes.ACCOUNT_DOES_NOT_EXISTS.getIdeaErrorCode()))
		{
			RequestGenerator signUpReqGen = ideaApiHelper.invokeIdeaSignUp(paramAppName, paramAccessKey, paramFirstName, paramLastName, paramEmailId, paramPhoneNumber, 
					paramGender, paramDateOfBirth);
			String signUpResponse = signUpReqGen.respvalidate.returnresponseasstring();
			System.out.println("\nPrinting IdeaApi signUp API response :\n\n"+signUpResponse);
			log.info("\nPrinting IdeaApi signUp API response :\n\n"+signUpResponse);
			
			responseStatusType = signUpReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), false).replace("\"", "");
			AssertJUnit.assertTrue("IdeaApi signUp API statusType is invalid, Expected: <"+paramStatusType+"> but Actual: <"+responseStatusType+">", 
					responseStatusType.equals(paramStatusType));
			
			mobileSignInUsingEmailReqGen = ideaApiHelper.invokeIdeaMobileSignInUsingEmail(paramAppName, paramAccessKey, paramEmailId);
			mobileSignInUsingEmailResponse = mobileSignInUsingEmailReqGen.respvalidate.returnresponseasstring();
			System.out.println("\nPrinting IdeaApi mobileSignInUsingEmail API response :\n\n"+mobileSignInUsingEmailResponse);
			log.info("\nPrinting IdeaApi mobileSignInUsingEmail API response :\n\n"+mobileSignInUsingEmailResponse);
			
			responseStatusType = mobileSignInUsingEmailReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).replace("\"", "");
		}
		
		AssertJUnit.assertTrue("IdeaApi mobileSignInUsingEmail API statusType is invalid, Expected: <"+paramStatusType+"> but Actual: <"+responseStatusType+">", 
				responseStatusType.equals(paramStatusType));
		
		List<JSONObject> emailDetails = JsonPath.read(mobileSignInUsingEmailResponse, "$.entry.emailDetails[*]");
		AssertJUnit.assertTrue("IdeaApi mobileSignInUsingEmail API [entry.emailDetails] data is empty", !emailDetails.isEmpty());
		List<String> emailList = new ArrayList<String>();
		for(int i=0; i<emailDetails.size(); i++)
		{
			String respEmail = JsonPath.read(mobileSignInUsingEmailResponse, "$.entry.emailDetails["+i+"].email");
			emailList.add(respEmail);
		}
		
		AssertJUnit.assertTrue(paramEmailId+" doesn't exists in [entry.emailDetails["+emailList+"]]", emailList.contains(paramEmailId));
	}
	
	@Test(groups = { "SchemaValidation" }, dataProvider = "IdeaApiDP_mobileSignInUsingEmail_verifyResponseDataNodesUsingSchemaValidations")
	public void IdeaApiTests_mobileSignInUsingEmail_verifyResponseDataNodesUsingSchemaValidations(String paramAppName, String paramAccessKey, String paramFirstName,
			String paramLastName, String paramEmailId, String paramPhoneNumber, String paramGender, String paramDateOfBirth, String paramStatusType)
	{
		RequestGenerator mobileSignInUsingEmailReqGen = ideaApiHelper.invokeIdeaMobileSignInUsingEmail(paramAppName, paramAccessKey, paramEmailId);
		String mobileSignInUsingEmailResponse = mobileSignInUsingEmailReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting IdeaApi mobileSignInUsingEmail API response :\n\n"+mobileSignInUsingEmailResponse);
		log.info("\nPrinting IdeaApi mobileSignInUsingEmail API response :\n\n"+mobileSignInUsingEmailResponse);
		 
		String responseStatusCode = mobileSignInUsingEmailReqGen.respvalidate.NodeValue(StatusNodes.STATUS_CODE.toString(), true).replace("\"", "");
		String responseStatusType = mobileSignInUsingEmailReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).replace("\"", "");
		
		if(responseStatusCode.equals(IdeaErrorCodes.ACCOUNT_DOES_NOT_EXISTS.getIdeaErrorCode()))
		{
			RequestGenerator signUpReqGen = ideaApiHelper.invokeIdeaSignUp(paramAppName, paramAccessKey, paramFirstName, paramLastName, paramEmailId, paramPhoneNumber, 
					paramGender, paramDateOfBirth);
			String signUpResponse = signUpReqGen.respvalidate.returnresponseasstring();
			System.out.println("\nPrinting IdeaApi signUp API response :\n\n"+signUpResponse);
			log.info("\nPrinting IdeaApi signUp API response :\n\n"+signUpResponse);
			
			responseStatusType = signUpReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), false).replace("\"", "");
			AssertJUnit.assertTrue("IdeaApi signUp API statusType is invalid, Expected: <"+paramStatusType+"> but Actual: <"+responseStatusType+">", 
					responseStatusType.equals(paramStatusType));
			
			mobileSignInUsingEmailReqGen = ideaApiHelper.invokeIdeaMobileSignInUsingEmail(paramAppName, paramAccessKey, paramEmailId);
			mobileSignInUsingEmailResponse = mobileSignInUsingEmailReqGen.respvalidate.returnresponseasstring();
			System.out.println("\nPrinting IdeaApi mobileSignInUsingEmail API response :\n\n"+mobileSignInUsingEmailResponse);
			log.info("\nPrinting IdeaApi mobileSignInUsingEmail API response :\n\n"+mobileSignInUsingEmailResponse);
			
			responseStatusType = mobileSignInUsingEmailReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).replace("\"", "");
		}
		
		AssertJUnit.assertTrue("IdeaApi mobileSignInUsingEmail API statusType is invalid, Expected: <"+paramStatusType+"> but Actual: <"+responseStatusType+">", 
				responseStatusType.equals(paramStatusType));
		
		try {
            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/ideaapi-mobilesigninusingemail-schema.txt");
            List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(mobileSignInUsingEmailResponse, jsonschema);
            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in IdeaApi mobileSignInUsingEmail API response", CollectionUtils.isEmpty(missingNodeList));
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression", "MiniRegression", "ExhaustiveRegression" }, 
			dataProvider = "IdeaApiDP_changeProfilePassword_validateAPIResponse")
	public void IdeaApiTests_changeProfilePassword_verifySuccessResponse(String paramAppName, String paramNewAccessKey, String paramAccessKey, String paramUserId, 
			String successRespCode)
	{
		RequestGenerator changeProfilePasswordReqGen = ideaApiHelper.invokeIdeaChangeProfilePassword(paramAppName, paramNewAccessKey, paramAccessKey, paramUserId);
		String mobileSignInUsingEmailResponse = changeProfilePasswordReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting IdeaApi changeProfilePassword API response :\n\n"+mobileSignInUsingEmailResponse);
		log.info("\nPrinting IdeaApi changeProfilePassword API response :\n\n"+mobileSignInUsingEmailResponse);
		 
		AssertJUnit.assertEquals("IdeaApi changeProfilePassword API is not working", Integer.parseInt(successRespCode), changeProfilePasswordReqGen.response.getStatus());
		
		changeProfilePasswordReqGen = ideaApiHelper.invokeIdeaChangeProfilePassword(paramAppName, paramAccessKey, paramNewAccessKey, paramUserId);
		mobileSignInUsingEmailResponse = changeProfilePasswordReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting IdeaApi changeProfilePassword API response :\n\n"+mobileSignInUsingEmailResponse);
		log.info("\nPrinting IdeaApi changeProfilePassword API response :\n\n"+mobileSignInUsingEmailResponse);
		 
		AssertJUnit.assertEquals("IdeaApi changeProfilePassword API is not working", Integer.parseInt(successRespCode), changeProfilePasswordReqGen.response.getStatus());
	}
	
	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression" }, 
			dataProvider = "IdeaApiDP_changeProfilePassword_verifySuccessStatusInformation")
	public void IdeaApiTests_changeProfilePassword_verifySuccessStatusInformation(String paramAppName, String paramNewAccessKey, String paramAccessKey, 
			String paramFirstName, String paramLastName, String paramEmailId, String paramPhoneNumber, String paramGender, String paramDateOfBirth, 
			String paramStatusCode, String paramStatusMsg, String paramStatusType)
	{
		RequestGenerator changeProfilePasswordReqGen = ideaApiHelper.invokeIdeaChangeProfilePassword(paramAppName, paramNewAccessKey, paramAccessKey, paramEmailId);
		String changeProfilePasswordResponse = changeProfilePasswordReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting IdeaApi changeProfilePassword API response :\n\n"+changeProfilePasswordResponse);
		log.info("\nPrinting IdeaApi changeProfilePassword API response :\n\n"+changeProfilePasswordResponse);
		 
		String responseStatusCode = changeProfilePasswordReqGen.respvalidate.NodeValue(StatusNodes.STATUS_CODE.toString(), true).replace("\"", "");
		String responseStatusMsg = changeProfilePasswordReqGen.respvalidate.NodeValue(StatusNodes.STATUS_MESSAGE.toString(), true).replace("\"", "");
		String responseStatusType = changeProfilePasswordReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).replace("\"", "");
		
		if(responseStatusCode.equals(String.valueOf(IdeaErrorCodes.ACCOUNT_DOES_NOT_EXISTS.getIdeaErrorCode())))
		{
			RequestGenerator signUpReqGen = ideaApiHelper.invokeIdeaSignUp(paramAppName, paramAccessKey, paramFirstName, paramLastName, paramEmailId, paramPhoneNumber, 
					paramGender, paramDateOfBirth);
			String signUpResponse = signUpReqGen.respvalidate.returnresponseasstring();
			System.out.println("\nPrinting IdeaApi signUp API response :\n\n"+signUpResponse);
			log.info("\nPrinting IdeaApi signUp API response :\n\n"+signUpResponse);
			
			responseStatusType = signUpReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), false).replace("\"", "");
			AssertJUnit.assertTrue("IdeaApi signUp API statusType is invalid, Expected: <"+paramStatusType+"> but Actual: <"+responseStatusType+">", 
					responseStatusType.equals(paramStatusType));
			
			changeProfilePasswordReqGen = ideaApiHelper.invokeIdeaChangeProfilePassword(paramAppName, paramNewAccessKey, paramAccessKey, paramEmailId);
			changeProfilePasswordResponse = changeProfilePasswordReqGen.respvalidate.returnresponseasstring();
			System.out.println("\nPrinting IdeaApi changeProfilePassword API response :\n\n"+changeProfilePasswordResponse);
			log.info("\nPrinting IdeaApi changeProfilePassword API response :\n\n"+changeProfilePasswordResponse);
			
			responseStatusCode = changeProfilePasswordReqGen.respvalidate.NodeValue(StatusNodes.STATUS_CODE.toString(), true).replace("\"", "");
			responseStatusMsg = changeProfilePasswordReqGen.respvalidate.NodeValue(StatusNodes.STATUS_MESSAGE.toString(), true).replace("\"", "");
			responseStatusType = changeProfilePasswordReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).replace("\"", "");
		}
		
		AssertJUnit.assertTrue("IdeaApi changeProfilePassword API statusCode is invalid, Expected: <"+paramStatusCode+"> but Actual: <"+responseStatusCode+">",  
				responseStatusCode.equals(paramStatusCode));
		AssertJUnit.assertTrue("IdeaApi changeProfilePassword API statusMessage is invalid, Expected: <"+paramStatusMsg+"> but Actual: <"+responseStatusMsg+">",  
				responseStatusMsg.equals(paramStatusMsg));
		AssertJUnit.assertTrue("IdeaApi changeProfilePassword API statusType is invalid, Expected: <"+paramStatusType+"> but Actual: <"+responseStatusType+">", 
				responseStatusType.equals(paramStatusType));
		
		changeProfilePasswordReqGen = ideaApiHelper.invokeIdeaChangeProfilePassword(paramAppName, paramAccessKey, paramNewAccessKey, paramEmailId);
		changeProfilePasswordResponse = changeProfilePasswordReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting IdeaApi changeProfilePassword API response :\n\n"+changeProfilePasswordResponse);
		log.info("\nPrinting IdeaApi changeProfilePassword API response :\n\n"+changeProfilePasswordResponse);
		
		responseStatusCode = changeProfilePasswordReqGen.respvalidate.NodeValue(StatusNodes.STATUS_CODE.toString(), true).replace("\"", "");
		responseStatusMsg = changeProfilePasswordReqGen.respvalidate.NodeValue(StatusNodes.STATUS_MESSAGE.toString(), true).replace("\"", "");
		responseStatusType = changeProfilePasswordReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).replace("\"", "");

		AssertJUnit.assertTrue("IdeaApi changeProfilePassword API statusCode is invalid, Expected: <"+paramStatusCode+"> but Actual: <"+responseStatusCode+">",  
				responseStatusCode.equals(paramStatusCode));
		AssertJUnit.assertTrue("IdeaApi changeProfilePassword API statusMessage is invalid, Expected: <"+paramStatusMsg+"> but Actual: <"+responseStatusMsg+">",  
				responseStatusMsg.equals(paramStatusMsg));
		AssertJUnit.assertTrue("IdeaApi changeProfilePassword API statusType is invalid, Expected: <"+paramStatusType+"> but Actual: <"+responseStatusType+">", 
				responseStatusType.equals(paramStatusType));
	}
	
	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression" }, 
			dataProvider = "IdeaApiDP_changeProfilePassword_verifyFailureStatusInformation")
	public void IdeaApiTests_changeProfilePassword_verifyFailureStatusInformation(String paramAppName, String paramNewAccessKey, String paramAccessKey, 
			String paramEmailId, String paramStatusCode, String paramStatusMsg, String paramStatusType)
	{
		RequestGenerator changeProfilePasswordReqGen = ideaApiHelper.invokeIdeaChangeProfilePassword(paramAppName, paramNewAccessKey, paramAccessKey, paramEmailId);
		String changeProfilePasswordResponse = changeProfilePasswordReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting IdeaApi changeProfilePassword API response :\n\n"+changeProfilePasswordResponse);
		log.info("\nPrinting IdeaApi changeProfilePassword API response :\n\n"+changeProfilePasswordResponse);
		 
		String responseStatusCode = changeProfilePasswordReqGen.respvalidate.NodeValue(StatusNodes.STATUS_CODE.toString(), true).replace("\"", "");
		String responseStatusMsg = changeProfilePasswordReqGen.respvalidate.NodeValue(StatusNodes.STATUS_MESSAGE.toString(), true).replace("\"", "");
		String responseStatusType = changeProfilePasswordReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).replace("\"", "");
		
		AssertJUnit.assertTrue("IdeaApi changeProfilePassword API statusCode is invalid, Expected: <"+paramStatusCode+"> but Actual: <"+responseStatusCode+">",  
				responseStatusCode.equals(paramStatusCode));
		AssertJUnit.assertTrue("IdeaApi changeProfilePassword API statusMessage is invalid, Expected: <"+paramStatusMsg+"> but Actual: <"+responseStatusMsg+">",  
				responseStatusMsg.equals(paramStatusMsg));
		AssertJUnit.assertTrue("IdeaApi changeProfilePassword API statusType is invalid, Expected: <"+paramStatusType+"> but Actual: <"+responseStatusType+">", 
				responseStatusType.equals(paramStatusType));
	}
	
	@Test(groups = { "SchemaValidation" }, dataProvider = "IdeaApiDP_changeProfilePassword_verifyResponseDataNodesUsingSchemaValidations")
	public void IdeaApiTests_changeProfilePassword_verifyResponseDataNodesUsingSchemaValidations(String paramAppName, String paramNewAccessKey, String paramAccessKey, 
			String paramFirstName, String paramLastName, String paramEmailId, String paramPhoneNumber, String paramGender, String paramDateOfBirth, 
			String paramStatusType)
	{
		RequestGenerator changeProfilePasswordReqGen = ideaApiHelper.invokeIdeaChangeProfilePassword(paramAppName, paramNewAccessKey, paramAccessKey, paramEmailId);
		String changeProfilePasswordResponse = changeProfilePasswordReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting IdeaApi changeProfilePassword API response :\n\n"+changeProfilePasswordResponse);
		log.info("\nPrinting IdeaApi changeProfilePassword API response :\n\n"+changeProfilePasswordResponse);
		 
		String responseStatusCode = changeProfilePasswordReqGen.respvalidate.NodeValue(StatusNodes.STATUS_CODE.toString(), true).replace("\"", "");
		String responseStatusType = changeProfilePasswordReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).replace("\"", "");
		
		if(responseStatusCode.equals(String.valueOf(IdeaErrorCodes.ACCOUNT_DOES_NOT_EXISTS.getIdeaErrorCode())))
		{
			RequestGenerator signUpReqGen = ideaApiHelper.invokeIdeaSignUp(paramAppName, paramAccessKey, paramFirstName, paramLastName, paramEmailId, paramPhoneNumber, 
					paramGender, paramDateOfBirth);
			String signUpResponse = signUpReqGen.respvalidate.returnresponseasstring();
			System.out.println("\nPrinting IdeaApi signUp API response :\n\n"+signUpResponse);
			log.info("\nPrinting IdeaApi signUp API response :\n\n"+signUpResponse);
			
			responseStatusType = signUpReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), false).replace("\"", "");
			AssertJUnit.assertTrue("IdeaApi signUp API statusType is invalid, Expected: <"+paramStatusType+"> but Actual: <"+responseStatusType+">", 
					responseStatusType.equals(paramStatusType));
			
			changeProfilePasswordReqGen = ideaApiHelper.invokeIdeaChangeProfilePassword(paramAppName, paramNewAccessKey, paramAccessKey, paramEmailId);
			changeProfilePasswordResponse = changeProfilePasswordReqGen.respvalidate.returnresponseasstring();
			System.out.println("\nPrinting IdeaApi changeProfilePassword API response :\n\n"+changeProfilePasswordResponse);
			log.info("\nPrinting IdeaApi changeProfilePassword API response :\n\n"+changeProfilePasswordResponse);
			
			responseStatusType = changeProfilePasswordReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).replace("\"", "");
		}
		
		AssertJUnit.assertTrue("IdeaApi changeProfilePassword API statusType is invalid, Expected: <"+paramStatusType+"> but Actual: <"+responseStatusType+">", 
				responseStatusType.equals(paramStatusType));
		
		changeProfilePasswordReqGen = ideaApiHelper.invokeIdeaChangeProfilePassword(paramAppName, paramAccessKey, paramNewAccessKey, paramEmailId);
		changeProfilePasswordResponse = changeProfilePasswordReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting IdeaApi changeProfilePassword API response :\n\n"+changeProfilePasswordResponse);
		log.info("\nPrinting IdeaApi changeProfilePassword API response :\n\n"+changeProfilePasswordResponse);
		
		responseStatusType = changeProfilePasswordReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).replace("\"", "");
		AssertJUnit.assertTrue("IdeaApi changeProfilePassword API statusType is invalid, Expected: <"+paramStatusType+"> but Actual: <"+responseStatusType+">", 
				responseStatusType.equals(paramStatusType));
		
		try {
            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/ideaapi-changeprofilepassword-schema.txt");
            List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(changeProfilePasswordResponse, jsonschema);
            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in IdeaApi changeProfilePassword API response", CollectionUtils.isEmpty(missingNodeList));
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression", "MiniRegression", "ExhaustiveRegression" }, 
			dataProvider = "IdeaApiDP_updateUserProfileInfo_validateAPIResponse")
	public void IdeaApiTests_updateUserProfileInfo_verifySuccessResponse(String paramAppName, String paramAccessKey, String paramFirstName, String paramLastName, 
			String paramUserId, String paramPhoneNumber, String paramGender, String paramDateOfBirth, String paramRespCode, String paramStatus, 
			String paramAppNameToUpdate, String paramFirstNameToUpdate, String paramLastNameToUpdate, String paramGenderToUpdate, String paramDOBToUpdate, 
			String paramStatusToUpdate)
	{
		RequestGenerator updateUserProfileInfoReqGen = ideaApiHelper.invokeIdeaUpdateProfileInfo(paramAppNameToUpdate, paramFirstNameToUpdate, paramLastNameToUpdate, 
				paramGenderToUpdate, paramDOBToUpdate, paramStatusToUpdate, paramUserId);
		String updateUserProfileInfoResponse = updateUserProfileInfoReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting IdeaApi updateUserProfileInfo API response :\n\n"+updateUserProfileInfoResponse);
		log.info("\nPrinting IdeaApi updateUserProfileInfo API response :\n\n"+updateUserProfileInfoResponse);
		 
		AssertJUnit.assertEquals("IdeaApi updateUserProfileInfo API is not working", Integer.parseInt(paramRespCode), updateUserProfileInfoReqGen.response.getStatus());

		updateUserProfileInfoReqGen = ideaApiHelper.invokeIdeaUpdateProfileInfo(paramAppName, paramFirstName, paramLastName, paramGender, paramDateOfBirth, paramStatus, 
				paramUserId);
		updateUserProfileInfoResponse = updateUserProfileInfoReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting IdeaApi updateUserProfileInfo API response :\n\n"+updateUserProfileInfoResponse);
		log.info("\nPrinting IdeaApi updateUserProfileInfo API response :\n\n"+updateUserProfileInfoResponse);
		 
		AssertJUnit.assertEquals("IdeaApi updateUserProfileInfo API is not working", Integer.parseInt(paramRespCode), updateUserProfileInfoReqGen.response.getStatus());
	}
	
	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression" }, dataProvider = "IdeaApiDP_updateUserProfileInfo_verifySuccessStatusInformation")
	public void IdeaApiTests_updateUserProfileInfo_verifySuccessStatusInformation(String paramAppName, String paramAccessKey, String paramFirstName, String paramLastName, 
			String paramUserId, String paramPhoneNumber, String paramGender, String paramDateOfBirth, String paramStatusCode, String paramStatusMsg, 
			String paramStatusType, String paramStatus, String paramAppNameToUpdate, String paramFirstNameToUpdate, String paramLastNameToUpdate, 
			String paramGenderToUpdate, String paramDOBToUpdate, String paramStatusToUpdate)
	{
		RequestGenerator updateUserProfileInfoReqGen = ideaApiHelper.invokeIdeaUpdateProfileInfo(paramAppNameToUpdate, paramFirstNameToUpdate, paramLastNameToUpdate, 
				paramGenderToUpdate, paramDOBToUpdate, paramStatusToUpdate, paramUserId);
		String updateUserProfileInfoResponse = updateUserProfileInfoReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting IdeaApi updateUserProfileInfo API response :\n\n"+updateUserProfileInfoResponse);
		log.info("\nPrinting IdeaApi updateUserProfileInfo API response :\n\n"+updateUserProfileInfoResponse);
		 
		String responseStatusCode = updateUserProfileInfoReqGen.respvalidate.NodeValue(StatusNodes.STATUS_CODE.toString(), true).replace("\"", "");
		String responseStatusMsg = updateUserProfileInfoReqGen.respvalidate.NodeValue(StatusNodes.STATUS_MESSAGE.toString(), false).replace("\"", "");
		String responseStatusType = updateUserProfileInfoReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).replace("\"", "");
		
		if(responseStatusCode.equals(String.valueOf(IdeaErrorCodes.ACCOUNT_DOES_NOT_EXISTS.getIdeaErrorCode())))
		{
			RequestGenerator signUpReqGen = ideaApiHelper.invokeIdeaSignUp(paramAppName, paramAccessKey, paramFirstName, paramLastName, paramUserId, paramPhoneNumber, 
					paramGender, paramDateOfBirth);
			String signUpResponse = signUpReqGen.respvalidate.returnresponseasstring();
			System.out.println("\nPrinting IdeaApi signUp API response :\n\n"+signUpResponse);
			log.info("\nPrinting IdeaApi signUp API response :\n\n"+signUpResponse);
			
			responseStatusType = signUpReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), false).replace("\"", "");
			AssertJUnit.assertTrue("IdeaApi signUp API statusType is invalid, Expected: <"+paramStatusType+"> but Actual: <"+responseStatusType+">", 
					responseStatusType.equals(paramStatusType));
			
			updateUserProfileInfoReqGen = ideaApiHelper.invokeIdeaUpdateProfileInfo(paramAppName, paramFirstNameToUpdate, paramLastNameToUpdate, paramGenderToUpdate, 
					paramDOBToUpdate, paramStatusToUpdate, paramUserId);
			updateUserProfileInfoResponse = updateUserProfileInfoReqGen.respvalidate.returnresponseasstring();
			System.out.println("\nPrinting IdeaApi updateUserProfileInfo API response :\n\n"+updateUserProfileInfoResponse);
			log.info("\nPrinting IdeaApi updateUserProfileInfo API response :\n\n"+updateUserProfileInfoResponse);
			
			responseStatusCode = updateUserProfileInfoReqGen.respvalidate.NodeValue(StatusNodes.STATUS_CODE.toString(), true).replace("\"", "");
			responseStatusMsg = updateUserProfileInfoReqGen.respvalidate.NodeValue(StatusNodes.STATUS_MESSAGE.toString(), false).replace("\"", "");
			responseStatusType = updateUserProfileInfoReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).replace("\"", "");
		}
		
		AssertJUnit.assertTrue("IdeaApi updateUserProfileInfo API statusCode is invalid, Expected: <"+paramStatusCode+"> but Actual: <"+responseStatusCode+">", 
				responseStatusCode.equals(paramStatusCode));
		
		AssertJUnit.assertTrue("IdeaApi updateUserProfileInfo API statusMessage is invalid, Expected: <"+paramStatusMsg+"> but Actual: <"+responseStatusMsg+">", 
				responseStatusMsg.equals(paramStatusMsg));
		
		AssertJUnit.assertTrue("IdeaApi updateUserProfileInfo API statusType is invalid, Expected: <"+paramStatusType+"> but Actual: <"+responseStatusType+">", 
				responseStatusType.equals(paramStatusType));
		
		updateUserProfileInfoReqGen = ideaApiHelper.invokeIdeaUpdateProfileInfo(paramAppName, paramFirstName, paramLastName, paramGender, paramDateOfBirth, paramStatus, 
				paramUserId);
		updateUserProfileInfoResponse = updateUserProfileInfoReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting IdeaApi updateUserProfileInfo API response :\n\n"+updateUserProfileInfoResponse);
		log.info("\nPrinting IdeaApi updateUserProfileInfo API response :\n\n"+updateUserProfileInfoResponse);
		 
		AssertJUnit.assertTrue("IdeaApi updateUserProfileInfo API statusType is invalid, Expected: <"+paramStatusType+"> but Actual: <"+responseStatusType+">", 
				responseStatusType.equals(paramStatusType));
	}
	
	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression" }, dataProvider = "IdeaApiDP_updateUserProfileInfo_verifyFailureStatusInformation")
	public void IdeaApiTests_updateUserProfileInfo_verifyFailureStatusInformation(String paramAppNameToUpdate, String paramFirstNameToUpdate, String paramLastNameToUpdate, 
			String paramGenderToUpdate, String paramDOBToUpdate, String paramStatusToUpdate, String paramUserId, String paramStatusCode, String paramStatusMsg, 
			String paramStatusType)
	{
		RequestGenerator updateUserProfileInfoReqGen = ideaApiHelper.invokeIdeaUpdateProfileInfo(paramAppNameToUpdate, paramFirstNameToUpdate, paramLastNameToUpdate, 
				paramGenderToUpdate, paramDOBToUpdate, paramStatusToUpdate, paramUserId);
		String updateUserProfileInfoResponse = updateUserProfileInfoReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting IdeaApi updateUserProfileInfo API response :\n\n"+updateUserProfileInfoResponse);
		log.info("\nPrinting IdeaApi updateUserProfileInfo API response :\n\n"+updateUserProfileInfoResponse);
		 
		String responseStatusCode = updateUserProfileInfoReqGen.respvalidate.NodeValue(StatusNodes.STATUS_CODE.toString(), true).replace("\"", "");
		String responseStatusMsg = updateUserProfileInfoReqGen.respvalidate.NodeValue(StatusNodes.STATUS_MESSAGE.toString(), false).replace("\"", "");
		String responseStatusType = updateUserProfileInfoReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).replace("\"", "");
		
		AssertJUnit.assertTrue("IdeaApi updateUserProfileInfo API statusCode is invalid, Expected: <"+paramStatusCode+"> but Actual: <"+responseStatusCode+">", 
				responseStatusCode.equals(paramStatusCode));
		
		AssertJUnit.assertTrue("IdeaApi updateUserProfileInfo API statusMessage is invalid, Expected: <"+paramStatusMsg+"> but Actual: <"+responseStatusMsg+">", 
				responseStatusMsg.equals(paramStatusMsg));
		
		AssertJUnit.assertTrue("IdeaApi updateUserProfileInfo API statusType is invalid, Expected: <"+paramStatusType+"> but Actual: <"+responseStatusType+">", 
				responseStatusType.equals(paramStatusType));
	}
	
	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression" }, dataProvider = "IdeaApiDP_updateUserProfileInfo_verifyNodeValues")
	public void IdeaApiTests_updateUserProfileInfo_verifyNodeValues(String paramAppName, String paramAccessKey, String paramFirstName, String paramLastName, 
			String paramUserId, String paramPhoneNumber, String paramGender, String paramDateOfBirth, String paramStatusCode, String paramStatusMsg, 
			String paramStatusType, String paramStatus, String paramAppNameToUpdate, String paramFirstNameToUpdate, String paramLastNameToUpdate, 
			String paramGenderToUpdate, String paramDOBToUpdate, String paramStatusToUpdate)
	{
		RequestGenerator updateUserProfileInfoReqGen = ideaApiHelper.invokeIdeaUpdateProfileInfo(paramAppNameToUpdate, paramFirstNameToUpdate, paramLastNameToUpdate, 
				paramGenderToUpdate, paramDOBToUpdate, paramStatusToUpdate, paramUserId);
		String updateUserProfileInfoResponse = updateUserProfileInfoReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting IdeaApi updateUserProfileInfo API response :\n\n"+updateUserProfileInfoResponse);
		log.info("\nPrinting IdeaApi updateUserProfileInfo API response :\n\n"+updateUserProfileInfoResponse);
		 
		String responseStatusCode = updateUserProfileInfoReqGen.respvalidate.NodeValue(StatusNodes.STATUS_CODE.toString(), true).replace("\"", "");
		String responseStatusType = updateUserProfileInfoReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).replace("\"", "");
		
		if(responseStatusCode.equals(String.valueOf(IdeaErrorCodes.ACCOUNT_DOES_NOT_EXISTS.getIdeaErrorCode())))
		{
			RequestGenerator signUpReqGen = ideaApiHelper.invokeIdeaSignUp(paramAppName, paramAccessKey, paramFirstName, paramLastName, paramUserId, paramPhoneNumber, 
					paramGender, paramDateOfBirth);
			String signUpResponse = signUpReqGen.respvalidate.returnresponseasstring();
			System.out.println("\nPrinting IdeaApi signUp API response :\n\n"+signUpResponse);
			log.info("\nPrinting IdeaApi signUp API response :\n\n"+signUpResponse);
			
			responseStatusType = signUpReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), false).replace("\"", "");
			AssertJUnit.assertTrue("IdeaApi signUp API statusType is invalid, Expected: <"+paramStatusType+"> but Actual: <"+responseStatusType+">", 
					responseStatusType.equals(paramStatusType));
			
			updateUserProfileInfoReqGen = ideaApiHelper.invokeIdeaUpdateProfileInfo(paramAppName, paramFirstNameToUpdate, paramLastNameToUpdate, paramGenderToUpdate, 
					paramDOBToUpdate, paramStatusToUpdate, paramUserId);
			updateUserProfileInfoResponse = updateUserProfileInfoReqGen.respvalidate.returnresponseasstring();
			System.out.println("\nPrinting IdeaApi updateUserProfileInfo API response :\n\n"+updateUserProfileInfoResponse);
			log.info("\nPrinting IdeaApi updateUserProfileInfo API response :\n\n"+updateUserProfileInfoResponse);
			
			responseStatusCode = updateUserProfileInfoReqGen.respvalidate.NodeValue(StatusNodes.STATUS_CODE.toString(), true).replace("\"", "");
			responseStatusType = updateUserProfileInfoReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).replace("\"", "");
		}
		
		AssertJUnit.assertTrue("IdeaApi updateUserProfileInfo API statusType is invalid, Expected: <"+paramStatusType+"> but Actual: <"+responseStatusType+">", 
				responseStatusType.equals(paramStatusType));
		
		String responseEntryFirstNameNodeValue = updateUserProfileInfoReqGen.respvalidate.NodeValue(IdeaDataNodes.IDEA_ENTRY_FIRST_NAME_NODE.getIdeaDataNode(), true).replace("\"", "");
		String responseEntryLastNameNodeValue = updateUserProfileInfoReqGen.respvalidate.NodeValue(IdeaDataNodes.IDEA_ENTRY_LAST_NAME_NODE.getIdeaDataNode(), true).replace("\"", "");
		String responseEntryGenderNodeValue = updateUserProfileInfoReqGen.respvalidate.NodeValue(IdeaDataNodes.IDEA_ENTRY_GENDER_NODE.getIdeaDataNode(), true).replace("\"", "");
		String responseEntryDOBNodeValue = updateUserProfileInfoReqGen.respvalidate.NodeValue(IdeaDataNodes.IDEA_ENTRY_DATE_OF_BIRTH_NODE.getIdeaDataNode(), true).replace("\"", "");
		String responseEntryStatusNodeValue = updateUserProfileInfoReqGen.respvalidate.NodeValue(IdeaDataNodes.IDEA_ENTRY_STATUS_NODE.getIdeaDataNode(), true).replace("\"", "");
		
		AssertJUnit.assertEquals(IdeaDataNodes.IDEA_ENTRY_FIRST_NAME_NODE.getIdeaDataNode()+" Mismatch:", paramFirstNameToUpdate,  responseEntryFirstNameNodeValue);
		AssertJUnit.assertEquals(IdeaDataNodes.IDEA_ENTRY_LAST_NAME_NODE.getIdeaDataNode()+" Mismatch:", paramLastNameToUpdate,  responseEntryLastNameNodeValue);
		AssertJUnit.assertEquals(IdeaDataNodes.IDEA_ENTRY_GENDER_NODE.getIdeaDataNode()+" Mismatch:", paramGenderToUpdate,  responseEntryGenderNodeValue);
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try {
			paramDOBToUpdate = String.valueOf(df.parse(paramDOBToUpdate).getTime()+19800000).replace("\"", "");
			AssertJUnit.assertEquals(IdeaDataNodes.IDEA_ENTRY_DATE_OF_BIRTH_NODE.getIdeaDataNode()+" Mismatch:", paramDOBToUpdate, responseEntryDOBNodeValue);
		} catch (ParseException e) {
			log.error(e);
		}
		
		//AssertJUnit.assertEquals(IdeaDataNodes.IDEA_ENTRY_STATUS_NODE.getIdeaDataNode()+" Mismatch:", paramStatusToUpdate,  responseEntryStatusNodeValue);
		
		updateUserProfileInfoReqGen = ideaApiHelper.invokeIdeaUpdateProfileInfo(paramAppName, paramFirstName, paramLastName, paramGender, paramDateOfBirth, paramStatus, 
				paramUserId);
		updateUserProfileInfoResponse = updateUserProfileInfoReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting IdeaApi updateUserProfileInfo API response :\n\n"+updateUserProfileInfoResponse);
		log.info("\nPrinting IdeaApi updateUserProfileInfo API response :\n\n"+updateUserProfileInfoResponse);
		 
		AssertJUnit.assertTrue("IdeaApi updateUserProfileInfo API statusType is invalid, Expected: <"+paramStatusType+"> but Actual: <"+responseStatusType+">", 
				responseStatusType.equals(paramStatusType));
	}
	
	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression" }, dataProvider = "IdeaApiDP_updateUserProfileInfo_verifyResponseDataNodesUsingSchemaValidations")
	public void IdeaApiTests_updateUserProfileInfo_verifyResponseDataNodesUsingSchemaValidations(String paramAppName, String paramAccessKey, String paramFirstName, String paramLastName, 
			String paramUserId, String paramPhoneNumber, String paramGender, String paramDateOfBirth, String paramStatusCode, String paramStatusMsg, 
			String paramStatusType, String paramStatus, String paramAppNameToUpdate, String paramFirstNameToUpdate, String paramLastNameToUpdate, 
			String paramGenderToUpdate, String paramDOBToUpdate, String paramStatusToUpdate)
	{
		RequestGenerator updateUserProfileInfoReqGen = ideaApiHelper.invokeIdeaUpdateProfileInfo(paramAppNameToUpdate, paramFirstNameToUpdate, paramLastNameToUpdate, 
				paramGenderToUpdate, paramDOBToUpdate, paramStatusToUpdate, paramUserId);
		String updateUserProfileInfoResponse = updateUserProfileInfoReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting IdeaApi updateUserProfileInfo API response :\n\n"+updateUserProfileInfoResponse);
		log.info("\nPrinting IdeaApi updateUserProfileInfo API response :\n\n"+updateUserProfileInfoResponse);
		 
		String responseStatusCode = updateUserProfileInfoReqGen.respvalidate.NodeValue(StatusNodes.STATUS_CODE.toString(), true).replace("\"", "");
		String responseStatusType = updateUserProfileInfoReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).replace("\"", "");
		
		if(responseStatusCode.equals(String.valueOf(IdeaErrorCodes.ACCOUNT_DOES_NOT_EXISTS.getIdeaErrorCode())))
		{
			RequestGenerator signUpReqGen = ideaApiHelper.invokeIdeaSignUp(paramAppName, paramAccessKey, paramFirstName, paramLastName, paramUserId, paramPhoneNumber, 
					paramGender, paramDateOfBirth);
			String signUpResponse = signUpReqGen.respvalidate.returnresponseasstring();
			System.out.println("\nPrinting IdeaApi signUp API response :\n\n"+signUpResponse);
			log.info("\nPrinting IdeaApi signUp API response :\n\n"+signUpResponse);
			
			responseStatusType = signUpReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), false).replace("\"", "");
			AssertJUnit.assertTrue("IdeaApi signUp API statusType is invalid, Expected: <"+paramStatusType+"> but Actual: <"+responseStatusType+">", 
					responseStatusType.equals(paramStatusType));
			
			updateUserProfileInfoReqGen = ideaApiHelper.invokeIdeaUpdateProfileInfo(paramAppName, paramFirstNameToUpdate, paramLastNameToUpdate, paramGenderToUpdate, 
					paramDOBToUpdate, paramStatusToUpdate, paramUserId);
			updateUserProfileInfoResponse = updateUserProfileInfoReqGen.respvalidate.returnresponseasstring();
			System.out.println("\nPrinting IdeaApi updateUserProfileInfo API response :\n\n"+updateUserProfileInfoResponse);
			log.info("\nPrinting IdeaApi updateUserProfileInfo API response :\n\n"+updateUserProfileInfoResponse);
			
			responseStatusCode = updateUserProfileInfoReqGen.respvalidate.NodeValue(StatusNodes.STATUS_CODE.toString(), true).replace("\"", "");
			responseStatusType = updateUserProfileInfoReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).replace("\"", "");
		}
		
		AssertJUnit.assertTrue("IdeaApi updateUserProfileInfo API statusType is invalid, Expected: <"+paramStatusType+"> but Actual: <"+responseStatusType+">", 
				responseStatusType.equals(paramStatusType));
		
		updateUserProfileInfoReqGen = ideaApiHelper.invokeIdeaUpdateProfileInfo(paramAppName, paramFirstName, paramLastName, paramGender, paramDateOfBirth, paramStatus, 
				paramUserId);
		updateUserProfileInfoResponse = updateUserProfileInfoReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting IdeaApi updateUserProfileInfo API response :\n\n"+updateUserProfileInfoResponse);
		log.info("\nPrinting IdeaApi updateUserProfileInfo API response :\n\n"+updateUserProfileInfoResponse);
		 
		AssertJUnit.assertTrue("IdeaApi updateUserProfileInfo API statusType is invalid, Expected: <"+paramStatusType+"> but Actual: <"+responseStatusType+">", 
				responseStatusType.equals(paramStatusType));
		try {
            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/ideaapi-updateuserprofileinfo-schema.txt");
            List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(updateUserProfileInfoResponse, jsonschema);
            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in IdeaApi updateUserProfileInfo API response", CollectionUtils.isEmpty(missingNodeList));
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression", "MiniRegression", "ExhaustiveRegression" }, 
			dataProvider = "IdeaApiDP_passwordRecoveryByEmail_validateAPIResponse")
	public void IdeaApiTests_passwordRecoveryByEmail_verifySuccessResponse(String paramAppName, String paramEmailId, String successRespCode)
	{
		RequestGenerator passwordRecoveryByEmailReqGen = ideaApiHelper.invokeIdeaPasswordRecoveryByEmail(paramAppName, paramEmailId);
		String passwordRecoveryByEmailResponse = passwordRecoveryByEmailReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting IdeaApi passwordRecoveryByEmail API response :\n\n"+passwordRecoveryByEmailResponse);
		log.info("\nPrinting IdeaApi passwordRecoveryByEmail API response :\n\n"+passwordRecoveryByEmailResponse);
		 
		AssertJUnit.assertEquals("IdeaApi passwordRecoveryByEmail API is not working", Integer.parseInt(successRespCode), passwordRecoveryByEmailReqGen.response.getStatus());
	}
	
	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression" },
			dataProvider = "IdeaApiDP_passwordRecoveryByEmail_verifySuccessStatusInformation")
	public void IdeaApiTests_passwordRecoveryByEmail_verifySuccessStatusInformation(String paramAppName, String paramEmailId, String paramStatusCode, 
			String paramStatusMsg, String paramStatusType)
	{
		RequestGenerator passwordRecoveryByEmailReqGen = ideaApiHelper.invokeIdeaPasswordRecoveryByEmail(paramAppName, paramEmailId);
		String passwordRecoveryByEmailResponse = passwordRecoveryByEmailReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting IdeaApi passwordRecoveryByEmail API response :\n\n"+passwordRecoveryByEmailResponse);
		log.info("\nPrinting IdeaApi passwordRecoveryByEmail API response :\n\n"+passwordRecoveryByEmailResponse);
		 
		String responseStatusCode = passwordRecoveryByEmailReqGen.respvalidate.NodeValue(StatusNodes.STATUS_CODE.toString(), false).replace("\"", "");
		String responseStatusMsg = passwordRecoveryByEmailReqGen.respvalidate.NodeValue(StatusNodes.STATUS_MESSAGE.toString(), false).replace("\"", "");
		String responseStatusType = passwordRecoveryByEmailReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), false).replace("\"", "");
		
		AssertJUnit.assertTrue("IdeaApi passwordRecoveryByEmail API statusCode is invalid, Expected: <"+paramStatusCode+"> but Actual: <"+responseStatusCode+">", 
				responseStatusCode.equals(paramStatusCode));
		
		AssertJUnit.assertTrue("IdeaApi passwordRecoveryByEmail API statusMessage is invalid, Expected: <"+paramStatusMsg+"> but Actual: <"+responseStatusMsg+">", 
				responseStatusMsg.equals(paramStatusMsg));
		
		AssertJUnit.assertTrue("IdeaApi passwordRecoveryByEmail API statusType is invalid, Expected: <"+paramStatusType+"> but Actual: <"+responseStatusType+">", 
				responseStatusType.equals(paramStatusType));
	}
	
	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression" },
			dataProvider = "IdeaApiDP_passwordRecoveryByEmail_verifyFailureStatusInformation")
	public void IdeaApiTests_passwordRecoveryByEmail_verifyFailureStatusInformation(String paramAppName, String paramEmailId, String paramStatusCode, String paramStatusMsg,
			String paramStatusType)
	{
		RequestGenerator passwordRecoveryByEmailReqGen = ideaApiHelper.invokeIdeaPasswordRecoveryByEmail(paramAppName, paramEmailId);
		String passwordRecoveryByEmailResponse = passwordRecoveryByEmailReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting IdeaApi passwordRecoveryByEmail API response :\n\n"+passwordRecoveryByEmailResponse);
		log.info("\nPrinting IdeaApi passwordRecoveryByEmail API response :\n\n"+passwordRecoveryByEmailResponse);
		 
		String responseStatusCode = passwordRecoveryByEmailReqGen.respvalidate.NodeValue(StatusNodes.STATUS_CODE.toString(), false).replace("\"", "");
		String responseStatusMsg = passwordRecoveryByEmailReqGen.respvalidate.NodeValue(StatusNodes.STATUS_MESSAGE.toString(), false).replace("\"", "");
		String responseStatusType = passwordRecoveryByEmailReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), false).replace("\"", "");
		
		AssertJUnit.assertTrue("IdeaApi passwordRecoveryByEmail API statusCode is invalid, Expected: <"+paramStatusCode+"> but Actual: <"+responseStatusCode+">", 
				responseStatusCode.equals(paramStatusCode));
		
		AssertJUnit.assertTrue("IdeaApi passwordRecoveryByEmail API statusMessage is invalid, Expected: <"+paramStatusMsg+"> but Actual: <"+responseStatusMsg+">", 
				responseStatusMsg.equals(paramStatusMsg));
		
		AssertJUnit.assertTrue("IdeaApi passwordRecoveryByEmail API statusType is invalid, Expected: <"+paramStatusType+"> but Actual: <"+responseStatusType+">", 
				responseStatusType.equals(paramStatusType));
	}
	
	@Test(groups = { "SchemaValidation" }, dataProvider = "IdeaApiDP_passwordRecoveryByEmail_verifyResponseDataNodesUsingSchemaValidations")
	public void IdeaApiTests_passwordRecoveryByEmail_verifyResponseDataNodesUsingSchemaValidations(String paramAppName, String paramEmailId, String paramStatusType)
	{
		RequestGenerator passwordRecoveryByEmailReqGen = ideaApiHelper.invokeIdeaPasswordRecoveryByEmail(paramAppName, paramEmailId);
		String passwordRecoveryByEmailResponse = passwordRecoveryByEmailReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting IdeaApi passwordRecoveryByEmail API response :\n\n"+passwordRecoveryByEmailResponse);
		log.info("\nPrinting IdeaApi passwordRecoveryByEmail API response :\n\n"+passwordRecoveryByEmailResponse);
		 
		String responseStatusType = passwordRecoveryByEmailReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), false).replace("\"", "");
		AssertJUnit.assertTrue("IdeaApi passwordRecoveryByEmail API statusType is invalid, Expected: <"+paramStatusType+"> but Actual: <"+responseStatusType+">", 
				responseStatusType.equals(paramStatusType));

		try {
            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/ideaapi-passwordrecoverybyemail-schema.txt");
            List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(passwordRecoveryByEmailResponse, jsonschema);
            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in IdeaApi passwordRecoveryByEmail API response", CollectionUtils.isEmpty(missingNodeList));
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression", "MiniRegression", "ExhaustiveRegression" }, 
			dataProvider = "IdeaApiDP_resetPasswordByEmail_validateAPIResponse")
	public void IdeaApiTests_resetPasswordByEmail_verifySuccessResponse(String paramAppName, String paramNewAccessKey, String paramAccessKey, String paramFirstName, 
			String paramLastName, String paramEmailId, String paramPhoneNumber, String paramGender, String paramDateOfBirth, String paramEnv, String paramRespCode, 
			String paramStatusType)
	{
		RequestGenerator passwordRecoveryByEmailReqGen = ideaApiHelper.invokeIdeaPasswordRecoveryByEmail(paramAppName, paramEmailId);
		String passwordRecoveryByEmailResponse = passwordRecoveryByEmailReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting IdeaApi passwordRecoveryByEmail API response :\n\n"+passwordRecoveryByEmailResponse);
		log.info("\nPrinting IdeaApi passwordRecoveryByEmail API response :\n\n"+passwordRecoveryByEmailResponse);
		 
		String responseStatusType = passwordRecoveryByEmailReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), false).replace("\"", "");
		AssertJUnit.assertTrue("IdeaApi passwordRecoveryByEmail API statusType is invalid, Expected: <"+paramStatusType+"> but Actual: <"+responseStatusType+">", 
				responseStatusType.equals(paramStatusType));
		
		String resetKey = ideaApiHelper.getResetTokenFromDatabase(paramEmailId, paramEnv);
		System.out.println("resetKey : "+resetKey);
		log.info("resetKey : "+resetKey);
		
		if(StringUtils.isEmpty(resetKey) || StringUtils.isBlank(resetKey)){
			
			RequestGenerator signUpReqGen = ideaApiHelper.invokeIdeaSignUp(paramAppName, paramAccessKey, paramFirstName, paramLastName, paramEmailId, paramPhoneNumber, 
					paramGender, paramDateOfBirth);
			String signUpResponse = signUpReqGen.respvalidate.returnresponseasstring();
			System.out.println("\nPrinting IdeaApi signUp API response :\n\n"+signUpResponse);
			log.info("\nPrinting IdeaApi signUp API response :\n\n"+signUpResponse);
			
			responseStatusType = signUpReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), false).replace("\"", "");
			AssertJUnit.assertTrue("IdeaApi signUp API statusType is invalid, Expected: <"+paramStatusType+"> but Actual: <"+responseStatusType+">", 
					responseStatusType.equals(paramStatusType));
			
			passwordRecoveryByEmailReqGen = ideaApiHelper.invokeIdeaPasswordRecoveryByEmail(paramAppName, paramEmailId);
			passwordRecoveryByEmailResponse = passwordRecoveryByEmailReqGen.respvalidate.returnresponseasstring();
			System.out.println("\nPrinting IdeaApi passwordRecoveryByEmail API response :\n\n"+passwordRecoveryByEmailResponse);
			log.info("\nPrinting IdeaApi passwordRecoveryByEmail API response :\n\n"+passwordRecoveryByEmailResponse);
			 
			responseStatusType = passwordRecoveryByEmailReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), false).replace("\"", "");
			AssertJUnit.assertTrue("IdeaApi passwordRecoveryByEmail API statusType is invalid, Expected: <"+paramStatusType+"> but Actual: <"+responseStatusType+">", 
					responseStatusType.equals(paramStatusType));
			
			resetKey = ideaApiHelper.getResetTokenFromDatabase(paramEmailId, paramEnv);
			System.out.println("resetKey : "+resetKey);
			log.info("resetKey : "+resetKey);
		}
		
		RequestGenerator resetPasswordByEmailReqGen = ideaApiHelper.invokeIdeaResetPasswordByEmail(paramAppName, paramEmailId, resetKey, paramAccessKey);
		String resetPasswordByEmailResponse = resetPasswordByEmailReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting IdeaApi resetPasswordByEmail API response :\n\n"+resetPasswordByEmailResponse);
		log.info("\nPrinting IdeaApi resetPasswordByEmail API response :\n\n"+resetPasswordByEmailResponse);
		 
		AssertJUnit.assertEquals("IdeaApi resetPasswordByEmail API is not working", Integer.parseInt(paramRespCode), resetPasswordByEmailReqGen.response.getStatus());
	}
	
	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression" }, 
			dataProvider = "IdeaApiDP_resetPasswordByEmail_verifySuccessStatusInformation")
	public void IdeaApiTests_resetPasswordByEmail_verifySuccessStatusInformation(String paramAppName, String paramNewAccessKey, String paramAccessKey, 
			String paramFirstName, String paramLastName, String paramEmailId, String paramPhoneNumber, String paramGender, String paramDateOfBirth, String envName, 
			String paramStatusCode, String paramStatusMsg, String paramStatusType)
	{
		RequestGenerator passwordRecoveryByEmailReqGen = ideaApiHelper.invokeIdeaPasswordRecoveryByEmail(paramAppName, paramEmailId);
		String passwordRecoveryByEmailResponse = passwordRecoveryByEmailReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting IdeaApi passwordRecoveryByEmail API response :\n\n"+passwordRecoveryByEmailResponse);
		log.info("\nPrinting IdeaApi passwordRecoveryByEmail API response :\n\n"+passwordRecoveryByEmailResponse);
		 
		String responseStatusType = passwordRecoveryByEmailReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), false).replace("\"", "");
		AssertJUnit.assertTrue("IdeaApi passwordRecoveryByEmail API statusType is invalid, Expected: <"+paramStatusType+"> but Actual: <"+responseStatusType+">", 
				responseStatusType.equals(paramStatusType));
		
		/*
		 *  Commenting the code to validate the reset key is proper by hitting the reset password by email api. Since we started using the encryption mechanism
		 * we can not able to know the encrypted token unless we lookup the email or use the same logic which is used to encrypt. Either of the mentioned methods
		 * will not work out because we are using the non verified email ids and we can not relay on the logic which is used in the development, Probability of code change is often.
		 * However this is verified in myntraInitPasswordReset API. which gives the encrypted token in the response.
		 *
		String resetKey = ideaApiHelper.getResetTokenFromDatabase(paramEmailId, envName);
		System.out.println("resetKey : "+resetKey);
		log.info("resetKey : "+resetKey);
		
		if(StringUtils.isEmpty(resetKey) || StringUtils.isBlank(resetKey)){
			
			RequestGenerator signUpReqGen = ideaApiHelper.invokeIdeaSignUp(paramAppName, paramAccessKey, paramFirstName, paramLastName, paramEmailId, paramPhoneNumber, 
					paramGender, paramDateOfBirth);
			String signUpResponse = signUpReqGen.respvalidate.returnresponseasstring();
			System.out.println("\nPrinting IdeaApi signUp API response :\n\n"+signUpResponse);
			log.info("\nPrinting IdeaApi signUp API response :\n\n"+signUpResponse);
			
			responseStatusType = signUpReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), false).replace("\"", "");
			AssertJUnit.assertTrue("IdeaApi signUp API statusType is invalid, Expected: <"+paramStatusType+"> but Actual: <"+responseStatusType+">", 
					responseStatusType.equals(paramStatusType));
			
			passwordRecoveryByEmailReqGen = ideaApiHelper.invokeIdeaPasswordRecoveryByEmail(paramAppName, paramEmailId);
			passwordRecoveryByEmailResponse = passwordRecoveryByEmailReqGen.respvalidate.returnresponseasstring();
			System.out.println("\nPrinting IdeaApi passwordRecoveryByEmail API response :\n\n"+passwordRecoveryByEmailResponse);
			log.info("\nPrinting IdeaApi passwordRecoveryByEmail API response :\n\n"+passwordRecoveryByEmailResponse);
			 
			responseStatusType = passwordRecoveryByEmailReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), false).replace("\"", "");
			AssertJUnit.assertTrue("IdeaApi passwordRecoveryByEmail API statusType is invalid, Expected: <"+paramStatusType+"> but Actual: <"+responseStatusType+">", 
					responseStatusType.equals(paramStatusType));
			
			resetKey = ideaApiHelper.getResetTokenFromDatabase(paramEmailId, envName);
			System.out.println("resetKey : "+resetKey);
			log.info("resetKey : "+resetKey);
		}
		
		RequestGenerator resetPasswordByEmailReqGen = ideaApiHelper.invokeIdeaResetPasswordByEmail(paramAppName, paramEmailId, resetKey, paramNewAccessKey);
		String resetPasswordByEmailResponse = resetPasswordByEmailReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting IdeaApi resetPasswordByEmail API response :\n\n"+resetPasswordByEmailResponse);
		log.info("\nPrinting IdeaApi resetPasswordByEmail API response :\n\n"+resetPasswordByEmailResponse);
		 
		String responseStatusCode = resetPasswordByEmailReqGen.respvalidate.NodeValue(StatusNodes.STATUS_CODE.toString(), false).replace("\"", "");
		String responseStatusMsg = resetPasswordByEmailReqGen.respvalidate.NodeValue(StatusNodes.STATUS_MESSAGE.toString(), false).replace("\"", "");
		responseStatusType = resetPasswordByEmailReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), false).replace("\"", "");
		
		AssertJUnit.assertTrue("IdeaApi resetPasswordByEmail API statusCode is invalid, Expected: <"+paramStatusCode+"> but Actual: <"+responseStatusCode+">", 
				responseStatusCode.equals(paramStatusCode));
		
		AssertJUnit.assertTrue("IdeaApi resetPasswordByEmail API statusMessage is invalid, Expected: <"+paramStatusMsg+"> but Actual: <"+responseStatusMsg+">", 
				responseStatusMsg.equals(paramStatusMsg));
		
		AssertJUnit.assertTrue("IdeaApi resetPasswordByEmail API statusType is invalid, Expected: <"+paramStatusType+"> but Actual: <"+responseStatusType+">", 
				responseStatusType.equals(paramStatusType));
		
		*/
	}
	
	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression" }, dataProvider = "IdeaApiDP_resetPasswordByEmail_verifyFailureStatusInformation")
	public void IdeaApiTests_resetPasswordByEmail_verifyFailureStatusInformation(String paramAppName, String paramEmailId, String paramResetKey, String paramAccessKey,
			String paramStatusCode, String paramStatusMsg, String paramStatusType)
	{
		RequestGenerator resetPasswordByEmailReqGen = ideaApiHelper.invokeIdeaResetPasswordByEmail(paramAppName, paramEmailId, paramResetKey, paramAccessKey);
		String resetPasswordByEmailResponse = resetPasswordByEmailReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting IdeaApi resetPasswordByEmail API response :\n\n"+resetPasswordByEmailResponse);
		log.info("\nPrinting IdeaApi resetPasswordByEmail API response :\n\n"+resetPasswordByEmailResponse);
		
		String responseStatusCode = resetPasswordByEmailReqGen.respvalidate.NodeValue(StatusNodes.STATUS_CODE.toString(), false).replace("\"", "");
		String responseStatusMsg = resetPasswordByEmailReqGen.respvalidate.NodeValue(StatusNodes.STATUS_MESSAGE.toString(), false).replace("\"", "");
		String responseStatusType = resetPasswordByEmailReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), false).replace("\"", "");
		
		AssertJUnit.assertTrue("IdeaApi resetPasswordByEmail API statusCode is invalid, Expected: <"+paramStatusCode+"> but Actual: <"+responseStatusCode+">", 
				responseStatusCode.equals(paramStatusCode));
		
		AssertJUnit.assertTrue("IdeaApi resetPasswordByEmail API statusMessage is invalid, Expected: <"+paramStatusMsg+"> but Actual: <"+responseStatusMsg+">", 
				responseStatusMsg.equals(paramStatusMsg));
		
		AssertJUnit.assertTrue("IdeaApi resetPasswordByEmail API statusType is invalid, Expected: <"+paramStatusType+"> but Actual: <"+responseStatusType+">", 
				responseStatusType.equals(paramStatusType));
	}
	
	@Test(groups = { "SchemaValidation" }, dataProvider = "IdeaApiDP_resetPasswordByEmail_verifyResponseDataNodesUsingSchemaValidations")
	public void IdeaApiTests_resetPasswordByEmail_verifyResponseDataNodesUsingSchemaValidations(String paramAppName, String paramNewAccessKey, String paramAccessKey, 
			String paramFirstName, String paramLastName, String paramEmailId, String paramPhoneNumber, String paramGender, String paramDateOfBirth, String envName, 
			String paramStatusType)
	{
		RequestGenerator passwordRecoveryByEmailReqGen = ideaApiHelper.invokeIdeaPasswordRecoveryByEmail(paramAppName, paramEmailId);
		String passwordRecoveryByEmailResponse = passwordRecoveryByEmailReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting IdeaApi passwordRecoveryByEmail API response :\n\n"+passwordRecoveryByEmailResponse);
		log.info("\nPrinting IdeaApi passwordRecoveryByEmail API response :\n\n"+passwordRecoveryByEmailResponse);
		 
		String responseStatusType = passwordRecoveryByEmailReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), false).replace("\"", "");
		AssertJUnit.assertTrue("IdeaApi passwordRecoveryByEmail API statusType is invalid, Expected: <"+paramStatusType+"> but Actual: <"+responseStatusType+">", 
				responseStatusType.equals(paramStatusType));
		
		/*
		 * Commenting the code to validate the reset key is proper by hitting the reset password by email api. Since we started using the encryption mechanism
		 * we can not able to know the encrypted token unless we lookup the email or use the same logic which is used to encrypt. Either of the mentioned methods
		 * will not work out because we are using the non verified email ids and we can not relay on the logic which is used in the development, Probability of code change is often.
		 * However this is verified in myntraInitPasswordReset API. which gives the encrypted token in the response.
		 * 
		String resetKey = ideaApiHelper.getResetTokenFromDatabase(paramEmailId, envName);
		System.out.println("resetKey : "+resetKey);
		log.info("resetKey : "+resetKey);
		
		
		if(StringUtils.isEmpty(resetKey) || StringUtils.isBlank(resetKey)){
			
			RequestGenerator signUpReqGen = ideaApiHelper.invokeIdeaSignUp(paramAppName, paramAccessKey, paramFirstName, paramLastName, paramEmailId, paramPhoneNumber, 
					paramGender, paramDateOfBirth);
			String signUpResponse = signUpReqGen.respvalidate.returnresponseasstring();
			System.out.println("\nPrinting IdeaApi signUp API response :\n\n"+signUpResponse);
			log.info("\nPrinting IdeaApi signUp API response :\n\n"+signUpResponse);
			
			responseStatusType = signUpReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), false).replace("\"", "");
			AssertJUnit.assertTrue("IdeaApi signUp API statusType is invalid, Expected: <"+paramStatusType+"> but Actual: <"+responseStatusType+">", 
					responseStatusType.equals(paramStatusType));
			
			passwordRecoveryByEmailReqGen = ideaApiHelper.invokeIdeaPasswordRecoveryByEmail(paramAppName, paramEmailId);
			passwordRecoveryByEmailResponse = passwordRecoveryByEmailReqGen.respvalidate.returnresponseasstring();
			System.out.println("\nPrinting IdeaApi passwordRecoveryByEmail API response :\n\n"+passwordRecoveryByEmailResponse);
			log.info("\nPrinting IdeaApi passwordRecoveryByEmail API response :\n\n"+passwordRecoveryByEmailResponse);
			 
			responseStatusType = passwordRecoveryByEmailReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), false).replace("\"", "");
			AssertJUnit.assertTrue("IdeaApi passwordRecoveryByEmail API statusType is invalid, Expected: <"+paramStatusType+"> but Actual: <"+responseStatusType+">", 
					responseStatusType.equals(paramStatusType));
			
			resetKey = ideaApiHelper.getResetTokenFromDatabase(paramEmailId, envName);
			System.out.println("resetKey : "+resetKey);
			log.info("resetKey : "+resetKey);
		}
		
		RequestGenerator resetPasswordByEmailReqGen = ideaApiHelper.invokeIdeaResetPasswordByEmail(paramAppName, paramEmailId, resetKey, paramNewAccessKey);
		String resetPasswordByEmailResponse = resetPasswordByEmailReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting IdeaApi resetPasswordByEmail API response :\n\n"+resetPasswordByEmailResponse);
		log.info("\nPrinting IdeaApi resetPasswordByEmail API response :\n\n"+resetPasswordByEmailResponse);
		 
		responseStatusType = resetPasswordByEmailReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), false).replace("\"", "");
		AssertJUnit.assertTrue("IdeaApi resetPasswordByEmail API statusType is invalid, Expected: <"+paramStatusType+"> but Actual: <"+responseStatusType+">", 
				responseStatusType.equals(paramStatusType));
		
		try {
            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/ideaapi-resetpasswordbyemail-schema.txt");
            List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(resetPasswordByEmailResponse, jsonschema);
            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in IdeaApi resetPasswordByEmail API response", CollectionUtils.isEmpty(missingNodeList));
        } catch (Exception e) {
            e.printStackTrace();
        }
        */
	}
	
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression", "MiniRegression", "ExhaustiveRegression" }, 
			dataProvider = "IdeaApiDP_mobileSecureRefresh_validateAPIResponse")
	public void IdeaApiTests_mobileSecureRefresh_verifySuccessResponse(String paramAppName, String paramAccessKey, String paramFirstName, String paramLastName, 
			String paramEmailId, String paramPhoneNumber, String paramGender, String paramDateOfBirth, String paramRespCode, String paramStatusType)
	{
		RequestGenerator mobileSignInUsingEmailReqGen = ideaApiHelper.invokeIdeaMobileSignInUsingEmail(paramAppName, paramAccessKey, paramEmailId);
		String mobileSignInUsingEmailResponse = mobileSignInUsingEmailReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting IdeaApi mobileSignInUsingEmail API response :\n\n"+mobileSignInUsingEmailResponse);
		log.info("\nPrinting IdeaApi mobileSignInUsingEmail API response :\n\n"+mobileSignInUsingEmailResponse);
		 
		String responseStatusCode = mobileSignInUsingEmailReqGen.respvalidate.NodeValue(StatusNodes.STATUS_CODE.toString(), true).replace("\"", "");
		String responseStatusType = mobileSignInUsingEmailReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).replace("\"", "");
		
		if(responseStatusCode.equals(String.valueOf(IdeaErrorCodes.ACCOUNT_DOES_NOT_EXISTS.getIdeaErrorCode())))
		{
			RequestGenerator signUpReqGen = ideaApiHelper.invokeIdeaSignUp(paramAppName, paramAccessKey, paramFirstName, paramLastName, paramEmailId, paramPhoneNumber, 
					paramGender, paramDateOfBirth);
			String signUpResponse = signUpReqGen.respvalidate.returnresponseasstring();
			System.out.println("\nPrinting IdeaApi signUp API response :\n\n"+signUpResponse);
			log.info("\nPrinting IdeaApi signUp API response :\n\n"+signUpResponse);
			
			responseStatusType = signUpReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), false).replace("\"", "");
			AssertJUnit.assertTrue("IdeaApi signUp API statusType is invalid, Expected: <"+paramStatusType+"> but Actual: <"+responseStatusType+">", 
					responseStatusType.equals(paramStatusType));
			
			mobileSignInUsingEmailReqGen = ideaApiHelper.invokeIdeaMobileSignInUsingEmail(paramAppName, paramAccessKey, paramEmailId);
			mobileSignInUsingEmailResponse = mobileSignInUsingEmailReqGen.respvalidate.returnresponseasstring();
			System.out.println("\nPrinting IdeaApi mobileSignInUsingEmail API response :\n\n"+mobileSignInUsingEmailResponse);
			log.info("\nPrinting IdeaApi mobileSignInUsingEmail API response :\n\n"+mobileSignInUsingEmailResponse);
			
			responseStatusType = mobileSignInUsingEmailReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).replace("\"", "");
		}
		
		AssertJUnit.assertTrue("IdeaApi mobileSignInUsingEmail API statusType is invalid, Expected: <"+paramStatusType+"> but Actual: <"+responseStatusType+">", 
				responseStatusType.equals(paramStatusType));
		
		if(mobileSignInUsingEmailReqGen.respvalidate.DoesNodeExists(IdeaDataNodes.IDEA_TOKEN_ENTRY_NODE.getIdeaDataNode())){
			
			String authToken = mobileSignInUsingEmailReqGen.respvalidate.NodeValue(IdeaDataNodes.IDEA_TOKEN_ENTRY_AUTH_TOKEN_NODE.getIdeaDataNode(), true).replace("\"", "");
			String refreshToken = mobileSignInUsingEmailReqGen.respvalidate.NodeValue(IdeaDataNodes.IDEA_TOKEN_ENTRY_REFRESH_TOKEN_NODE.getIdeaDataNode(), true).replace("\"", "");
			
			RequestGenerator mobileSecureRefreshReqGen = ideaApiHelper.invokeIdeaMobileSecureRefresh(authToken, refreshToken);
			String updateUserProfileInfoResponse = mobileSecureRefreshReqGen.respvalidate.returnresponseasstring();
			System.out.println("\nPrinting IdeaApi mobileSecureRefresh API response :\n\n"+updateUserProfileInfoResponse);
			log.info("\nPrinting IdeaApi mobileSecureRefresh API response :\n\n"+updateUserProfileInfoResponse);
			 
			AssertJUnit.assertEquals("IdeaApi mobileSecureRefresh API is not working", Integer.parseInt(paramRespCode), 
					mobileSecureRefreshReqGen.response.getStatus());
			
		} else {
			System.out.println(IdeaDataNodes.IDEA_TOKEN_ENTRY_NODE.getIdeaDataNode()+" node doesn't exists");
			log.info(IdeaDataNodes.IDEA_TOKEN_ENTRY_NODE.getIdeaDataNode()+" node doesn't exists");
		}
	}
	
	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression" }, dataProvider = "IdeaApiDP_mobileSecureRefresh_verifySuccessStatusInformation")
	public void IdeaApiTests_mobileSecureRefresh_verifySuccessStatusInformation(String paramAppName, String paramAccessKey, String paramFirstName, String paramLastName, 
			String paramEmailId, String paramPhoneNumber, String paramGender, String paramDateOfBirth, String paramStatusCode, String paramStatusMsg, 
			String paramStatusType)
	{
		RequestGenerator mobileSignInUsingEmailReqGen = ideaApiHelper.invokeIdeaMobileSignInUsingEmail(paramAppName, paramAccessKey, paramEmailId);
		String mobileSignInUsingEmailResponse = mobileSignInUsingEmailReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting IdeaApi mobileSignInUsingEmail API response :\n\n"+mobileSignInUsingEmailResponse);
		log.info("\nPrinting IdeaApi mobileSignInUsingEmail API response :\n\n"+mobileSignInUsingEmailResponse);
		 
		String responseStatusCode = mobileSignInUsingEmailReqGen.respvalidate.NodeValue(StatusNodes.STATUS_CODE.toString(), true).replace("\"", "");
		String responseStatusType = mobileSignInUsingEmailReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).replace("\"", "");
		
		if(responseStatusCode.equals(String.valueOf(IdeaErrorCodes.ACCOUNT_DOES_NOT_EXISTS.getIdeaErrorCode())))
		{
			RequestGenerator signUpReqGen = ideaApiHelper.invokeIdeaSignUp(paramAppName, paramAccessKey, paramFirstName, paramLastName, paramEmailId, paramPhoneNumber, 
					paramGender, paramDateOfBirth);
			String signUpResponse = signUpReqGen.respvalidate.returnresponseasstring();
			System.out.println("\nPrinting IdeaApi signUp API response :\n\n"+signUpResponse);
			log.info("\nPrinting IdeaApi signUp API response :\n\n"+signUpResponse);
			
			responseStatusType = signUpReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), false).replace("\"", "");
			AssertJUnit.assertTrue("IdeaApi signUp API statusType is invalid, Expected: <"+paramStatusType+"> but Actual: <"+responseStatusType+">", 
					responseStatusType.equals(paramStatusType));
			
			mobileSignInUsingEmailReqGen = ideaApiHelper.invokeIdeaMobileSignInUsingEmail(paramAppName, paramAccessKey, paramEmailId);
			mobileSignInUsingEmailResponse = mobileSignInUsingEmailReqGen.respvalidate.returnresponseasstring();
			System.out.println("\nPrinting IdeaApi mobileSignInUsingEmail API response :\n\n"+mobileSignInUsingEmailResponse);
			log.info("\nPrinting IdeaApi mobileSignInUsingEmail API response :\n\n"+mobileSignInUsingEmailResponse);
			
			responseStatusType = mobileSignInUsingEmailReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), true).replace("\"", "");
		}
		
		AssertJUnit.assertTrue("IdeaApi mobileSignInUsingEmail API statusType is invalid, Expected: <"+paramStatusType+"> but Actual: <"+responseStatusType+">", 
				responseStatusType.equals(paramStatusType));
		
		if(mobileSignInUsingEmailReqGen.respvalidate.DoesNodeExists(IdeaDataNodes.IDEA_TOKEN_ENTRY_NODE.getIdeaDataNode())){
			
			String authToken = mobileSignInUsingEmailReqGen.respvalidate.NodeValue(IdeaDataNodes.IDEA_TOKEN_ENTRY_AUTH_TOKEN_NODE.getIdeaDataNode(), true).replace("\"", "");
			String refreshToken = mobileSignInUsingEmailReqGen.respvalidate.NodeValue(IdeaDataNodes.IDEA_TOKEN_ENTRY_REFRESH_TOKEN_NODE.getIdeaDataNode(), true).replace("\"", "");
			
			RequestGenerator mobileSecureRefreshReqGen = ideaApiHelper.invokeIdeaMobileSecureRefresh(authToken, refreshToken);
			String updateUserProfileInfoResponse = mobileSecureRefreshReqGen.respvalidate.returnresponseasstring();
			System.out.println("\nPrinting IdeaApi mobileSecureRefresh API response :\n\n"+updateUserProfileInfoResponse);
			log.info("\nPrinting IdeaApi mobileSecureRefresh API response :\n\n"+updateUserProfileInfoResponse);
			 
			responseStatusCode = mobileSecureRefreshReqGen.respvalidate.NodeValue(StatusNodes.STATUS_CODE.toString(), false).replace("\"", "");
			String responseStatusMsg = mobileSecureRefreshReqGen.respvalidate.NodeValue(StatusNodes.STATUS_MESSAGE.toString(), false).replace("\"", "");
			responseStatusType = mobileSecureRefreshReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), false).replace("\"", "");
			
			AssertJUnit.assertTrue("IdeaApi mobileSecureRefresh API statusCode is invalid, Expected: <"+paramStatusCode+"> but Actual: <"+responseStatusCode+">", 
					responseStatusCode.equals(paramStatusCode));
			
			AssertJUnit.assertTrue("IdeaApi mobileSecureRefresh API statusMessage is invalid, Expected: <"+paramStatusMsg+"> but Actual: <"+responseStatusMsg+">", 
					responseStatusMsg.equals(paramStatusMsg));
			
			AssertJUnit.assertTrue("IdeaApi mobileSecureRefresh API statusType is invalid, Expected: <"+paramStatusType+"> but Actual: <"+responseStatusType+">", 
					responseStatusType.equals(paramStatusType));
			
		} else {
			System.out.println(IdeaDataNodes.IDEA_TOKEN_ENTRY_NODE.getIdeaDataNode()+" node doesn't exists");
			log.info(IdeaDataNodes.IDEA_TOKEN_ENTRY_NODE.getIdeaDataNode()+" node doesn't exists");
		}
	}
	
	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression" }, dataProvider = "IdeaApiDP_mobileSecureRefresh_verifyFailureStatusInformation")
	public void IdeaApiTests_mobileSecureRefresh_verifyFailureStatusInformation(String paramAuthToken, String paramRefreshToken, String paramStatusCode, 
			String paramStatusMsg, String paramStatusType)
	{
		RequestGenerator mobileSecureRefreshReqGen = ideaApiHelper.invokeIdeaMobileSecureRefresh(paramAuthToken, paramRefreshToken);
		String updateUserProfileInfoResponse = mobileSecureRefreshReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting IdeaApi mobileSecureRefresh API response :\n\n"+updateUserProfileInfoResponse);
		log.info("\nPrinting IdeaApi mobileSecureRefresh API response :\n\n"+updateUserProfileInfoResponse);
		 
		String responseStatusCode = mobileSecureRefreshReqGen.respvalidate.NodeValue(StatusNodes.STATUS_CODE.toString(), false).replace("\"", "");
		String responseStatusMsg = mobileSecureRefreshReqGen.respvalidate.NodeValue(StatusNodes.STATUS_MESSAGE.toString(), false).replace("\"", "");
		String responseStatusType = mobileSecureRefreshReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), false).replace("\"", "");
		
		AssertJUnit.assertTrue("IdeaApi mobileSecureRefresh API statusCode is invalid, Expected: <"+paramStatusCode+"> but Actual: <"+responseStatusCode+">", 
				responseStatusCode.equals(paramStatusCode));
		
		AssertJUnit.assertTrue("IdeaApi mobileSecureRefresh API statusMessage is invalid, Expected: <"+paramStatusMsg+"> but Actual: <"+responseStatusMsg+">", 
				responseStatusMsg.equals(paramStatusMsg));
		
		AssertJUnit.assertTrue("IdeaApi mobileSecureRefresh API statusType is invalid, Expected: <"+paramStatusType+"> but Actual: <"+responseStatusType+">", 
				responseStatusType.equals(paramStatusType));
	}
	
	/*@Test
	public void databaseConnection() throws SQLException
	{
		Connection connection = null;
		PreparedStatement preparedstatement = null;
		ResultSet resultset = null;
		String id = null;
		
		String sql_query = "select fuidx from user_contact_relation where uid = ?";
		connection = getDataBaseConnection();
		preparedstatement = connection.prepareStatement(sql_query);
		preparedstatement.setString(1,"1006291");
		resultset = preparedstatement.executeQuery();	
		if(resultset.next()) id = resultset.getString(1);
		System.out.println("id = " +id);
	}*/
	
	private static Connection getDataBaseConnection(String environment) throws InterruptedException
	{
		Connection con = null;
		String url = "";
		
		if (environment.equals("QA"))
		{
		url = "jdbc:mysql://"+"54.251.97.209"+"/idea?"+"user=myntraAppDBUser&password=9eguCrustuBR&port=3306";
		Thread.sleep(2000);
		}
		
		else if (environment.equals("Fox7"))
		{
		url = "jdbc:mysql://"+"54.179.37.12"+"/idea?"+"user=myntraAppDBUser&password=9eguCrustuBR&port=3306";
		Thread.sleep(2000);
		}
		
		try {
			con = DriverManager.getConnection(url);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		return con;
	}
	/*
	@Test
	public void contactGraphAPI()
	{
        String CustomPayload = "{\"appName\": \"myntra\",\"uidx\": \"main_user_101@myntra.com\",\"contactEntryList\":[{\"firstName\": \"main\",\"lastName\": \"user\",\"phones\":[{\"type\": \"MOBILE\",\"number\": \"5511223344\"}],\"emails\": [{\"emailType\": \"WORK\",\"email\": \"sub_user_100@myntra.com\"}]},{\"firstName\": \"Sub\",\"lastName\": \"user\",\"phones\": [{\"type\": \"WORK\",\"number\": \"6611223344\"}],\"emails\": [{\"emailType\": \"HOME\",\"email\": \"sub_user_101@myntra.com\"}]},{\"firstName\": \"fname3\",\"lastName\": \"lname3\",\"emails\": [{\"emailType\": \"OTHER\",\"email\": \"sub_user_102@myntra.com\"}]},{\"firstName\": \"fname7\",\"lastName\": \"lname7\",\"phones\": [{\"type\": \"HOME\",\"number\": \"8888888889\"}]}]}";
		MyntraService service = Myntra.getService(ServiceType.PORTAL_IDEA, APINAME.CONTACTGRAPH, init.Configurations,CustomPayload);
		RequestGenerator req = new RequestGenerator(service);
		int code = req.response.getStatus();
		System.out.println("code = " +code);
		System.out.println("response :" +req.respvalidate.returnresponseasstring());
		
	}
	*/

	private String generateRandomId1()
	{
		String uuid = UUID.randomUUID().toString();
		return uuid.substring(uuid.lastIndexOf("-") + 1, uuid.length());
	}
	
	public static String generateRandomMobileNumber(){
	    String val = String.valueOf(new Random(System.currentTimeMillis()).nextLong()+20007890);
	    return val.substring(1, 11).replace(""+val.charAt(1), "9");
	}
	
	private String signup_getUID (String loginID, String paramPhoneNumber)
	{
		String paramAppName = "myntra";
		String paramAccessKey = "rahulj12";
		String paramFirstName = "test"; 
		String paramLastName ="user";
		String paramEmailId = loginID;
		String paramGender ="MALE";
		String paramDateOfBirth = "1991-03-06";
		
	    RequestGenerator signUpReqGen = ideaApiHelper.invokeIdeaSignUp(paramAppName, paramAccessKey, paramFirstName, paramLastName, paramEmailId, paramPhoneNumber,
				paramGender, paramDateOfBirth);
		String signUpResponse = signUpReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting IdeaApi signUp API response :\n\n"+signUpResponse);
		log.info("\nPrinting IdeaApi signUp API response :\n\n"+signUpResponse);
		
		String UID = signUpReqGen.respvalidate
				.NodeValue("entry.id", true).replaceAll("\"", "")
				.trim();
		return UID;
		
	}
	
	/*
	// Mapping of Myntra user with non registered email ids without child contacts
	@Test(groups = "Sanity",dataProvider = "ContactGraph_APIOnlyNonRegisteredEmails")
	public void contactGraphAPI_onlyNonRegisteredEmails(String environment) throws SQLException, InterruptedException
	{
		
		String main_user = "\"main_user_" +generateRandomId1()+"@myntra.com\"";
		System.out.println("main user = " +main_user);
		String sub_user1 = "\"sub_user1_" +generateRandomId1()+"@myntra.com\"";
		System.out.println("sub user1 = " +sub_user1);
		String sub_user2 = "\"sub_user2_" +generateRandomId1()+"@myntra.com\"";
		System.out.println("sub user2 = " +sub_user2);
		String sub_user3 = "\"sub_user3_" +generateRandomId1()+"@myntra.com\"";
		System.out.println("sub user3 = " +sub_user3);
		
		String main_user_phone = generateRandomMobileNumber();
		String UID = signup_getUID (main_user.replaceAll("\"", ""),main_user_phone);
		
		System.out.println("UID for main user = " +UID);
	    	
      // String CustomPayload = "{\"appName\": \"myntra\",\"uidx\": \"main_user_101@myntra.com\",\"contactEntryList\":[{\"firstName\": \"main\",\"lastName\": \"user\",\"phones\":[{\"type\": \"MOBILE\",\"number\": \"5511223344\"}],\"emails\": [{\"emailType\": \"WORK\",\"email\": \"sub_user_100@myntra.com\"}]},{\"firstName\": \"Sub\",\"lastName\": \"user\",\"phones\": [{\"type\": \"WORK\",\"number\": \"6611223344\"}],\"emails\": [{\"emailType\": \"HOME\",\"email\": \"sub_user_101@myntra.com\"}]},{\"firstName\": \"fname3\",\"lastName\": \"lname3\",\"emails\": [{\"emailType\": \"OTHER\",\"email\": \"sub_user_102@myntra.com\"}]},{\"firstName\": \"fname7\",\"lastName\": \"lname7\",\"phones\": [{\"type\": \"HOME\",\"number\": \"8888888889\"}]}]}";
		String CustomPayload = "{\"appName\": \"myntra\",\"uidx\":"+main_user+",\"contactEntryList\":[{\"firstName\": \"main\",\"lastName\": \"user\",\"emails\": [{\"emailType\": \"WORK\",\"email\":"+sub_user1+"}]},{\"firstName\": \"Sub\",\"lastName\": \"user\",\"emails\": [{\"emailType\": \"HOME\",\"email\":"+sub_user2+"}]},{\"firstName\": \"fname3\",\"lastName\": \"lname3\",\"emails\": [{\"emailType\": \"OTHER\",\"email\":"+sub_user3+"}]}]}";
		System.out.println("Payload for contact graph api =" +CustomPayload);
		MyntraService service = Myntra.getService(ServiceType.PORTAL_IDEA, APINAME.CONTACTGRAPH, init.Configurations,CustomPayload);
		System.out.println("Service url for contact graph = " +service.URL);
		RequestGenerator req = new RequestGenerator(service);
		System.out.println("response :" +req.respvalidate.returnresponseasstring());
		
		AssertJUnit.assertEquals("response code for contact graph api is not 200", 200, req.response.getStatus());
		
		Connection connection = null;
		PreparedStatement preparedstatement = null;
		ResultSet resultset = null;
		
		System.out.println(".... executing user contact relation... ");
		String sql_query = "select email,linked from user_contact_email where uid = ?";
		connection = getDataBaseConnection(environment);
		preparedstatement = connection.prepareStatement(sql_query);
		preparedstatement.setString(1,UID);
		resultset = preparedstatement.executeQuery();
		LinkedHashMap<String,String> hm = new LinkedHashMap<String,String>();
		while(resultset.next()) 
		{
			String userName = resultset.getString(1);
			String linked = resultset.getString(2);
			hm.put(userName, linked);
		}
		
		System.out.println("map = " +hm);
		for (Entry<String,String> entry :hm.entrySet())
		{
			AssertJUnit.assertEquals("mismatch in linked type","0", entry.getValue().trim());
		}
	
	}
	
	// Mapping of Myntra user with non registered email ids without child contacts. 
	//Later one among non registered email signs up
		@Test(groups = "Sanity",dataProvider = "onlyNonRegisteredEmails_signup")
		public void contactGraphAPI_onlyNonRegisteredEmails_signup(String environment) throws SQLException, InterruptedException
		{
			
			String main_user = "\"main_user_" +generateRandomId1()+"@myntra.com\"";
			System.out.println("main user = " +main_user);
			String sub_user1 = "\"sub_user1_" +generateRandomId1()+"@myntra.com\"";
			System.out.println("sub user1 = " +sub_user1);
			String sub_user2 = "\"sub_user2_" +generateRandomId1()+"@myntra.com\"";
			System.out.println("sub user2 = " +sub_user2);
			String sub_user3 = "\"sub_user3_" +generateRandomId1()+"@myntra.com\"";
			System.out.println("sub user3 = " +sub_user3);
			
			String main_user_phone = generateRandomMobileNumber();
			String UID = signup_getUID (main_user.replaceAll("\"", ""),main_user_phone);
			System.out.println("UID for main user = " +UID);
		    	
	      // String CustomPayload = "{\"appName\": \"myntra\",\"uidx\": \"main_user_101@myntra.com\",\"contactEntryList\":[{\"firstName\": \"main\",\"lastName\": \"user\",\"phones\":[{\"type\": \"MOBILE\",\"number\": \"5511223344\"}],\"emails\": [{\"emailType\": \"WORK\",\"email\": \"sub_user_100@myntra.com\"}]},{\"firstName\": \"Sub\",\"lastName\": \"user\",\"phones\": [{\"type\": \"WORK\",\"number\": \"6611223344\"}],\"emails\": [{\"emailType\": \"HOME\",\"email\": \"sub_user_101@myntra.com\"}]},{\"firstName\": \"fname3\",\"lastName\": \"lname3\",\"emails\": [{\"emailType\": \"OTHER\",\"email\": \"sub_user_102@myntra.com\"}]},{\"firstName\": \"fname7\",\"lastName\": \"lname7\",\"phones\": [{\"type\": \"HOME\",\"number\": \"8888888889\"}]}]}";
			String CustomPayload = "{\"appName\": \"myntra\",\"uidx\":"+main_user+",\"contactEntryList\":[{\"firstName\": \"main\",\"lastName\": \"user\",\"emails\": [{\"emailType\": \"WORK\",\"email\":"+sub_user1+"}]},{\"firstName\": \"Sub\",\"lastName\": \"user\",\"emails\": [{\"emailType\": \"HOME\",\"email\":"+sub_user2+"}]},{\"firstName\": \"fname3\",\"lastName\": \"lname3\",\"emails\": [{\"emailType\": \"OTHER\",\"email\":"+sub_user3+"}]}]}";
			System.out.println("payload for contact graph api is =" +CustomPayload);
			MyntraService service = Myntra.getService(ServiceType.PORTAL_IDEA, APINAME.CONTACTGRAPH, init.Configurations,CustomPayload);
			RequestGenerator req = new RequestGenerator(service);
			System.out.println("response :" +req.respvalidate.returnresponseasstring());
			AssertJUnit.assertEquals("response code for contact graph api is not 200", 200, req.response.getStatus());
			
			Connection connection = null;
			PreparedStatement preparedstatement = null;
			ResultSet resultset = null;			
			
			System.out.println(".... executing main user contact relation... ");
			
			String sql_query = "select email,linked from user_contact_email where uid = ?";
			connection = getDataBaseConnection(environment);
			preparedstatement = connection.prepareStatement(sql_query);
			preparedstatement.setString(1,UID);
			resultset = preparedstatement.executeQuery();
			LinkedHashMap<String,String> hm = new LinkedHashMap<String,String>();
			while(resultset.next()) 
			{
				String userName = resultset.getString(1);
				String linked = resultset.getString(2);
				hm.put(userName, linked);
			}
			
			System.out.println("map = " +hm);
			for (Entry<String,String> entry :hm.entrySet())
			{
				AssertJUnit.assertEquals("mismatch in linked type","0", entry.getValue().trim());
			}
			
			
			System.out.println("... Sub user email sign up...");
			String sub_user_phone = generateRandomMobileNumber();
			String UID1 = signup_getUID (sub_user2.replaceAll("\"", ""),sub_user_phone);
			System.out.println("UID for sub user = "+UID1);
			
			
			// Main User - email query
			System.out.println(".....main user email query....");
			LinkedHashMap<String,String> hm1 = executeEmailQuery_getMap(UID,environment);
			for (Entry<String,String> entry :hm1.entrySet())
			{
				if (entry.getKey().equals(sub_user2.replaceAll("\"", "")))
				{
				AssertJUnit.assertEquals("mismatch in linked type","1", entry.getValue().trim());
				}
				else
				{
					AssertJUnit.assertEquals("mismatch in linked type","0", entry.getValue().trim());
				}
			}
			
			System.out.println("... main user relation query...");
			// Main User - relation query
			ArrayList<String> arr = executeRelationQuery_getString(UID,environment);
			for(int i =0; i< arr.size();i++)
			{
				System.out.println("main user - "+main_user+ "is friend contact of : " +arr.get(i));
				AssertJUnit.assertEquals("mismatch in relation user types", sub_user2.replaceAll("\"", ""), arr.get(i));
			}
			
			System.out.println("... Sub user relation query...");
			// Sub User - relation query
			ArrayList<String> arr1 = executeRelationQuery_getString(UID1,environment);
			for(int i =0; i< arr1.size();i++)
			{
				System.out.println("Sub user - "+sub_user2 + "is friend contact of : " +arr1.get(i));
				AssertJUnit.assertEquals("mismatch in relation user types", main_user.replaceAll("\"", ""), arr1.get(i));
			}
		
		}
		
		// Mapping of Myntra user with non registered phones without child contacts
		@Test(groups = "Sanity",dataProvider ="contactGraphAPI_onlyNonRegisteredPhones")
		public void contactGraphAPI_onlyNonRegisteredPhones(String environment) throws SQLException, InterruptedException
		{
			
			String main_user = "\"main_user_" +generateRandomId1()+"@myntra.com\"";
			System.out.println("main user = " +main_user);
			String sub_user1_phone1 = "\"" +generateRandomMobileNumber()+"\"";
			System.out.println("sub user1 phone = " +sub_user1_phone1);
			Thread.sleep(2000);
			String sub_user2_phone2 = "\"" +generateRandomMobileNumber()+"\"";
			System.out.println("sub user2 phone = " +sub_user2_phone2);
			Thread.sleep(2000);
			String sub_user3_phone3 = "\"" +generateRandomMobileNumber()+"\"";
			System.out.println("sub user3 phone = " +sub_user3_phone3);
			
			// Signup Main User
			System.out.println(".. Main user sign up...");
			String main_user_phone = generateRandomMobileNumber();
			String UID_main_user = signup_getUID (main_user.replaceAll("\"", ""),main_user_phone);
			System.out.println("UID for main user = " +UID_main_user);
		   
	      // String CustomPayload = "{\"appName\": \"myntra\",\"uidx\": \"main_user_101@myntra.com\",\"contactEntryList\":[{\"firstName\": \"main\",\"lastName\": \"user\",\"phones\":[{\"type\": \"MOBILE\",\"number\": \"5511223344\"}],\"emails\": [{\"emailType\": \"WORK\",\"email\": \"sub_user_100@myntra.com\"}]},{\"firstName\": \"Sub\",\"lastName\": \"user\",\"phones\": [{\"type\": \"WORK\",\"number\": \"6611223344\"}],\"emails\": [{\"emailType\": \"HOME\",\"email\": \"sub_user_101@myntra.com\"}]},{\"firstName\": \"fname3\",\"lastName\": \"lname3\",\"emails\": [{\"emailType\": \"OTHER\",\"email\": \"sub_user_102@myntra.com\"}]},{\"firstName\": \"fname7\",\"lastName\": \"lname7\",\"phones\": [{\"type\": \"HOME\",\"number\": \"8888888889\"}]}]}";
			String CustomPayload = "{\"appName\": \"myntra\",\"uidx\":"+main_user+",\"contactEntryList\":[{\"firstName\": \"main\",\"lastName\": \"user\",\"phones\": [{\"type\": \"MOBILE\",\"number\":"+sub_user1_phone1+"}]},{\"firstName\": \"Sub\",\"lastName\": \"user\",\"phones\": [{\"type\": \"MOBILE\",\"number\":"+sub_user2_phone2+"}]},{\"firstName\": \"fname3\",\"lastName\": \"lname3\",\"phones\": [{\"type\": \"WORK\",\"number\":"+sub_user3_phone3+"}]}]}";
			System.out.println(CustomPayload);
			MyntraService service = Myntra.getService(ServiceType.PORTAL_IDEA, APINAME.CONTACTGRAPH, init.Configurations,CustomPayload);
			RequestGenerator req = new RequestGenerator(service);
			
			// Main User - phone query
			System.out.println(".. Main user phone query...");
			LinkedHashMap<String,String> hm1 = executePhoneQuery_getMap(UID_main_user,environment);
			for (Entry<String,String> entry :hm1.entrySet())
			{
				System.out.println("For phone number :" + entry.getKey()+ " "+ "the value of linked flag is = " +entry.getValue());
				AssertJUnit.assertEquals("mismatch in linked type","0", entry.getValue().trim());
			}
		
		}
		
		// Mapping of Myntra user with non registered phones without child contacts - One phone number signs up
		@Test(groups = "Sanity",dataProvider = "contactGraphAPI_onlyNonRegisteredPhones_signup")
		public void contactGraphAPI_onlyNonRegisteredPhones_signup(String environment) throws SQLException, InterruptedException
		{
			
			String main_user = "\"main_user_" +generateRandomId1()+"@myntra.com\"";
			System.out.println("main user = " +main_user);
			String sub_user1_phone1 = "\"" +generateRandomMobileNumber()+"\"";
			System.out.println("sub user1 phone1 = " +sub_user1_phone1);
			Thread.sleep(2000);
			String sub_user2_phone2 = "\"" +generateRandomMobileNumber()+"\"";
			System.out.println("sub user2 phone2 = " +sub_user2_phone2);
			Thread.sleep(2000);
			String sub_user3_phone3 = "\"" +generateRandomMobileNumber()+"\"";
			System.out.println("sub user3 phone3 = " +sub_user3_phone3);
			Thread.sleep(2000);
			
			// Signup Main User
			System.out.println("... main user sign up .... ");
			String main_user_phone = generateRandomMobileNumber();
			System.out.println("random phone number for main user = " +main_user_phone);
			String UID_main_user = signup_getUID (main_user.replaceAll("\"", ""),main_user_phone);
			System.out.println("UID for main user = " +UID_main_user);
		    
	      // String CustomPayload = "{\"appName\": \"myntra\",\"uidx\": \"main_user_101@myntra.com\",\"contactEntryList\":[{\"firstName\": \"main\",\"lastName\": \"user\",\"phones\":[{\"type\": \"MOBILE\",\"number\": \"5511223344\"}],\"emails\": [{\"emailType\": \"WORK\",\"email\": \"sub_user_100@myntra.com\"}]},{\"firstName\": \"Sub\",\"lastName\": \"user\",\"phones\": [{\"type\": \"WORK\",\"number\": \"6611223344\"}],\"emails\": [{\"emailType\": \"HOME\",\"email\": \"sub_user_101@myntra.com\"}]},{\"firstName\": \"fname3\",\"lastName\": \"lname3\",\"emails\": [{\"emailType\": \"OTHER\",\"email\": \"sub_user_102@myntra.com\"}]},{\"firstName\": \"fname7\",\"lastName\": \"lname7\",\"phones\": [{\"type\": \"HOME\",\"number\": \"8888888889\"}]}]}";
			String CustomPayload = "{\"appName\": \"myntra\",\"uidx\":"+main_user+",\"contactEntryList\":[{\"firstName\": \"main\",\"lastName\": \"user\",\"phones\": [{\"type\": \"MOBILE\",\"number\":"+sub_user1_phone1+"}]},{\"firstName\": \"Sub\",\"lastName\": \"user\",\"phones\": [{\"type\": \"MOBILE\",\"number\":"+sub_user2_phone2+"}]},{\"firstName\": \"fname3\",\"lastName\": \"lname3\",\"phones\": [{\"type\": \"MOBILE\",\"number\":"+sub_user3_phone3+"}]}]}";
			System.out.println(CustomPayload);
			MyntraService service = Myntra.getService(ServiceType.PORTAL_IDEA, APINAME.CONTACTGRAPH, init.Configurations,CustomPayload);
			RequestGenerator req = new RequestGenerator(service);
		
			
			// Main User - phone query
			System.out.println("... main user phone query .... ");
			LinkedHashMap<String,String> hm1 = executePhoneQuery_getMap(UID_main_user,environment);
			for (Entry<String,String> entry :hm1.entrySet())
			{
				System.out.println("For phone number :" + entry.getKey()+ " "+ "the value of linked flag is = " +entry.getValue());
				AssertJUnit.assertEquals("mismatch in linked type","0", entry.getValue().trim());
			}
			
			// one phone number - signup
			System.out.println(".. one phone user sign up .... ");
			System.out.println("sign up - phone number :" + sub_user1_phone1.replaceAll("\"", ""));
			String sub_user_email = "sub_user_" +generateRandomId1()+"@myntra.com";
			String UID_sub_user = signup_getUID (sub_user_email,sub_user1_phone1.replaceAll("\"", ""));
			System.out.println("UID for sub user phone = " +UID_sub_user);
			
			// Main User - Phone query
			System.out.println("... main user phone query .... ");
			LinkedHashMap<String,String> hm2 = executePhoneQuery_getMap(UID_main_user,environment);
			for (Entry<String,String> entry :hm2.entrySet())
				{
				   if (entry.getKey().equals(sub_user1_phone1.replaceAll("\"", "")))
						{
						 AssertJUnit.assertEquals("mismatch in linked type","1", entry.getValue().trim());
						}
				   else
						{
						 AssertJUnit.assertEquals("mismatch in linked type","0", entry.getValue().trim());
						}
					}
			 
			System.out.println("... main user relation query .... ");
			// Main User - Relation Query
			ArrayList<String> arr = executeRelationQuery_getString(UID_main_user,environment);
			for(int i =0; i< arr.size();i++)
			{
				System.out.println("sub user = " +arr.get(i));
				AssertJUnit.assertEquals("mismatch in relation user types", sub_user_email, arr.get(i));
			}
			
			System.out.println("... sub user relation query .... ");
			// Sub User - relation query
			ArrayList<String> arr1 = executeRelationQuery_getString(UID_sub_user,environment);
			for(int i =0; i< arr1.size();i++)
			{
				System.out.println("main user = " +arr1.get(i));
				AssertJUnit.assertEquals("mismatch in relation user types", main_user.replaceAll("\"", ""), arr1.get(i));
			}
		
		}
		
		// Mapping of Myntra user with non registered groups of emails and phones (separate) - no child contacts
		@Test(groups = "Sanity",dataProvider ="contactGraphAPI_groupNonRegisteredPhonesAndEmails")
		public void contactGraphAPI_groupNonRegisteredPhonesAndEmails(String environment) throws SQLException, InterruptedException
		{
			String main_user = "\"main_user_" +generateRandomId1()+"@myntra.com\"";
			System.out.println("main user = " +main_user);
			
			String sub_user1_phone1 = "\"" +generateRandomMobileNumber()+"\"";
			System.out.println("sub user1 phone = " +sub_user1_phone1);
			Thread.sleep(2000);
			
			String sub_user2_phone2 = "\"" +generateRandomMobileNumber()+"\"";
			System.out.println("sub user2 phone = " +sub_user2_phone2);
			Thread.sleep(2000);
			
			String sub_user1_email1 = "\"sub_user1_" +generateRandomId1()+"@myntra.com\"";
			System.out.println("sub user1 email = " +sub_user1_email1);
			
			String sub_user2_email2 = "\"sub_user2_" +generateRandomId1()+"@myntra.com\"";
			System.out.println("sub user2 email = " +sub_user2_email2);
			
			// signup main user
			System.out.println(".........Main user sign up..........");
			String main_user_phone = generateRandomMobileNumber();
			System.out.println("main user phone number = " +main_user_phone);
			String UID_main_user = signup_getUID (main_user.replaceAll("\"", ""),main_user_phone);
			System.out.println("UID for main user = " +UID_main_user);
			
			// Run contact graph	
		    // String CustomPayload = "{\"appName\": \"myntra\",\"uidx\": \"main_user_101@myntra.com\",\"contactEntryList\":[{\"firstName\": \"main\",\"lastName\": \"user\",\"phones\":[{\"type\": \"MOBILE\",\"number\": \"5511223344\"}],\"emails\": [{\"emailType\": \"WORK\",\"email\": \"sub_user_100@myntra.com\"}]},{\"firstName\": \"Sub\",\"lastName\": \"user\",\"phones\": [{\"type\": \"WORK\",\"number\": \"6611223344\"}],\"emails\": [{\"emailType\": \"HOME\",\"email\": \"sub_user_101@myntra.com\"}]},{\"firstName\": \"fname3\",\"lastName\": \"lname3\",\"emails\": [{\"emailType\": \"OTHER\",\"email\": \"sub_user_102@myntra.com\"}]},{\"firstName\": \"fname7\",\"lastName\": \"lname7\",\"phones\": [{\"type\": \"HOME\",\"number\": \"8888888889\"}]}]}";
			System.out.println("............Contact Graph Run.........");
			String CustomPayload = "{\"appName\": \"myntra\",\"uidx\":"+main_user+",\"contactEntryList\":[{\"firstName\": \"main\",\"lastName\": \"user\",\"phones\": [{\"type\": \"MOBILE\",\"number\":"+sub_user1_phone1+"}]},{\"firstName\": \"Sub\",\"lastName\": \"user\",\"phones\": [{\"type\": \"MOBILE\",\"number\":"+sub_user2_phone2+"}]},{\"firstName\": \"fname3\",\"lastName\": \"lname3\",\"emails\": [{\"emailType\": \"WORK\",\"email\":"+sub_user1_email1+"}]},{\"firstName\": \"fname7\",\"lastName\": \"lname7\",\"emails\": [{\"emailType\": \"WORK\",\"email\": "+sub_user2_email2+"}]}]}";
			
			System.out.println(CustomPayload);
			MyntraService service = Myntra.getService(ServiceType.PORTAL_IDEA, APINAME.CONTACTGRAPH, init.Configurations,CustomPayload);
			RequestGenerator req = new RequestGenerator(service);
			
			System.out.println("...............Executing Main User Email Query...........");
			// Main User - email query
			LinkedHashMap<String,String> hm1 = executeEmailQuery_getMap(UID_main_user,environment);
			for (Entry<String,String> entry :hm1.entrySet())
				{
				  System.out.println("For email: " + entry.getKey() + " the value of linked flag is :" +entry.getValue());
				  AssertJUnit.assertEquals("mismatch in linked type","0", entry.getValue().trim());
				}
			
			System.out.println(".............Executing Main User Phone Query..........");
			// Main User - phone query
			LinkedHashMap<String,String> hm2 = executePhoneQuery_getMap(UID_main_user,environment);
			System.out.println("map = " +hm2);
			for (Entry<String,String> entry :hm2.entrySet())
			{
				System.out.println("For phone number :" + entry.getKey()+ " "+ "the value of linked flag is = " +entry.getValue());
				AssertJUnit.assertEquals("mismatch in linked type","0", entry.getValue().trim());
			}
			
			System.out.println(".............. First Email user sign up...............");
			
			String sub_user_phone = generateRandomMobileNumber();
			System.out.println("Phone number for first email user sign up = " +sub_user_phone);
			String UID_sub_user1_email = signup_getUID (sub_user1_email1.replaceAll("\"", ""),sub_user_phone);
			System.out.println("UID for sub user email = "+UID_sub_user1_email);
			
			// Main User - email query
			System.out.println(".......... Executing Main User Email Query..........");
			LinkedHashMap<String,String> hm3 = executeEmailQuery_getMap(UID_main_user,environment);
			for (Entry<String,String> entry :hm3.entrySet())
			{
				if (entry.getKey().equals(sub_user1_email1.replaceAll("\"", "")))
				{
				System.out.println("For key - "+entry.getKey() + " the value of linked flag is :" + entry.getValue());
				AssertJUnit.assertEquals("mismatch in linked type","1", entry.getValue().trim());
				}
				else
				{
					System.out.println("For key - "+entry.getKey() + " the value of linked flag is :" + entry.getValue());
					AssertJUnit.assertEquals("mismatch in linked type","0", entry.getValue().trim());
				}
			}
			
			// Main User - relation query
			System.out.println("..........\n Executing Main User relation Query..........");
			
			ArrayList<String> arr = executeRelationQuery_getString(UID_main_user,environment);
			for(int i =0; i< arr.size();i++)
			{
				System.out.println("Friend user for :" +main_user+ " is : " +arr.get(i));
				AssertJUnit.assertEquals("mismatch in relation user types", sub_user1_email1.replaceAll("\"", ""), arr.get(i));
			}
			
			// Sub User - relation query
			System.out.println("..........\n Executing Sub User relation Query..........");
			ArrayList<String> arr1 = executeRelationQuery_getString(UID_sub_user1_email,environment);
			for(int i =0; i< arr1.size();i++)
			{
				System.out.println("Friend user for :" +sub_user1_email1+ " is : " +arr.get(i));
				AssertJUnit.assertEquals("mismatch in relation user types", main_user.replaceAll("\"", ""), arr1.get(i));
			}
		
			
			System.out.println(".............. \n First Phone user sign up...............");
			
			System.out.println("sign up - phone number :" + sub_user1_phone1.replaceAll("\"", ""));
			String sub_phoneUser_randomEmail = "sub_user_" +generateRandomId1()+"@myntra.com";
			System.out.println("random email id for sub user - phone is :" +sub_phoneUser_randomEmail);
			String UID_sub_user_phone1 = signup_getUID (sub_phoneUser_randomEmail,sub_user1_phone1.replaceAll("\"", ""));
			System.out.println("UID for sub user phone = " +UID_sub_user_phone1);
			
			
			// Main User - Phone query
			System.out.println("...........\n Executing Main User phone query............");
			LinkedHashMap<String,String> hm4 = executePhoneQuery_getMap(UID_main_user,environment);
			for (Entry<String,String> entry :hm4.entrySet())
				{
				   
				   if (entry.getKey().equals(sub_user1_phone1.replaceAll("\"", "")))
						{
					     System.out.println("For key - "+entry.getKey() + " the value of linked flag is :" + entry.getValue());
						 AssertJUnit.assertEquals("mismatch in linked type","1", entry.getValue().trim());
						}
				   else
						{ 
					   System.out.println("For key - "+entry.getKey() + " the value of linked flag is :" + entry.getValue());
						 AssertJUnit.assertEquals("mismatch in linked type","0", entry.getValue().trim());
						}
					}
			 
			// Main User - Relation Query
			System.out.println("...........\n Executing Main User Relation query............");
			ArrayList<String> arr2 = executeRelationQuery_getString(UID_main_user,environment);
			for(int i =0; i< arr2.size();i++)
			{
				System.out.println("Friend user for :" +main_user+ " is : " +arr2.get(i));
				if (arr2.get(i).equals(sub_phoneUser_randomEmail))
				AssertJUnit.assertEquals("mismatch in relation user types", sub_phoneUser_randomEmail, arr2.get(i));
			}
			
			// Sub User - relation query
			System.out.println("...........\n Executing Sub User phone Relation query............");
			ArrayList<String> arr3 = executeRelationQuery_getString(UID_sub_user_phone1,environment);
			for(int i =0; i< arr3.size();i++)
			{
				System.out.println("Friend user for :" +sub_phoneUser_randomEmail+ " is : " +arr3.get(i));
				AssertJUnit.assertEquals("mismatch in relation user types", main_user.replaceAll("\"", ""), arr3.get(i));
			}
		}
		
		// Mapping of Myntra user with non registered groups of emails and phones (combi) - no child contacts
		@Test(groups = "Sanity",dataProvider ="contactGraphAPI_combiNonRegisteredPhonesAndEmails")
		public void contactGraphAPI_combiNonRegisteredPhonesAndEmails(String environment) throws SQLException, InterruptedException
		{
		String main_user = "\"main_user_" +generateRandomId1()+"@myntra.com\"";
		System.out.println("main user = " +main_user);
		
		String sub_user1_phone1 = "\"" +generateRandomMobileNumber()+"\"";
		System.out.println("sub user1 phone = " +sub_user1_phone1);
		Thread.sleep(2000);
		
		String sub_user2_phone2 = "\"" +generateRandomMobileNumber()+"\"";
		System.out.println("sub user2 phone = " +sub_user2_phone2);
		Thread.sleep(2000);
		
		String sub_user3_phone3 = "\"" +generateRandomMobileNumber()+"\"";
		System.out.println("sub user3 phone = " +sub_user3_phone3);
		Thread.sleep(2000);
		
		String sub_user1_email1 = "\"sub_user1_" +generateRandomId1()+"@myntra.com\"";
		System.out.println("sub user1 email = " +sub_user1_email1);
		
		String sub_user2_email2 = "\"sub_user2_" +generateRandomId1()+"@myntra.com\"";
		System.out.println("sub user2 email = " +sub_user2_email2);
		
		String sub_user3_email3 = "\"sub_user3_" +generateRandomId1()+"@myntra.com\"";
		System.out.println("sub user3 email = " +sub_user3_email3);
		
		// signup main user
		System.out.println(".........Main user sign up..........");
		String main_user_phone = generateRandomMobileNumber();
		System.out.println("main user phone number = " +main_user_phone);
		String UID_main_user = signup_getUID (main_user.replaceAll("\"", ""),main_user_phone);
		System.out.println("UID for main user = " +UID_main_user);
		
		// String CustomPayload = "{\"appName\": \"myntra\",\"uidx\": \"main_user_101@myntra.com\",\"contactEntryList\":[{\"firstName\": \"main\",\"lastName\": \"user\",\"phones\":[{\"type\": \"MOBILE\",\"number\": \"5511223344\"}],\"emails\": [{\"emailType\": \"WORK\",\"email\": \"sub_user_100@myntra.com\"}]},{\"firstName\": \"Sub\",\"lastName\": \"user\",\"phones\": [{\"type\": \"WORK\",\"number\": \"6611223344\"}],\"emails\": [{\"emailType\": \"HOME\",\"email\": \"sub_user_101@myntra.com\"}]},{\"firstName\": \"fname3\",\"lastName\": \"lname3\",\"emails\": [{\"emailType\": \"OTHER\",\"email\": \"sub_user_102@myntra.com\"}]},{\"firstName\": \"fname7\",\"lastName\": \"lname7\",\"phones\": [{\"type\": \"HOME\",\"number\": \"8888888889\"}]}]}";
		System.out.println("............Contact Graph Run.........");
		String CustomPayload = "{\"appName\": \"myntra\",\"uidx\":"+main_user+",\"contactEntryList\":[{\"firstName\": \"Sub\",\"lastName\": \"user1\",\"phones\": [{\"type\": \"MOBILE\",\"number\":"+sub_user1_phone1+"}],\"emails\": [{\"emailType\": \"WORK\",\"email\":" +sub_user1_email1+"}]},{\"firstName\": \"Sub\",\"lastName\": \"user2\",\"phones\": [{\"type\": \"MOBILE\",\"number\":"+sub_user2_phone2+"}],\"emails\": [{\"emailType\": \"WORK\",\"email\":" +sub_user2_email2+"}]},{\"firstName\": \"fname3\",\"lastName\": \"lname3\",\"emails\": [{\"emailType\": \"WORK\",\"email\":"+sub_user3_email3+"}]},{\"firstName\": \"fname7\",\"lastName\": \"lname7\",\"phones\": [{\"type\": \"HOME\",\"number\": "+sub_user3_phone3+"}]}]}";
		
		System.out.println(CustomPayload);
		MyntraService service = Myntra.getService(ServiceType.PORTAL_IDEA, APINAME.CONTACTGRAPH, init.Configurations,CustomPayload);
		RequestGenerator req = new RequestGenerator(service);
		int code = req.response.getStatus();
		System.out.println("code = " +code);
		System.out.println("response :" +req.respvalidate.returnresponseasstring());
		
		System.out.println("...............Executing Main User Email Query...........");
		// Main User - email query
		LinkedHashMap<String,String> hm1 = executeEmailQuery_getMap(UID_main_user,environment);
		for (Entry<String,String> entry :hm1.entrySet())
			{
			  System.out.println("For email: " + entry.getKey() + " the value of linked flag is :" +entry.getValue());
			  AssertJUnit.assertEquals("mismatch in linked type","0", entry.getValue().trim());
			}
		
		System.out.println(".............Executing Main User Phone Query..........");
		// Main User - phone query
		LinkedHashMap<String,String> hm2 = executePhoneQuery_getMap(UID_main_user,environment);
		System.out.println("map = " +hm2);
		for (Entry<String,String> entry :hm2.entrySet())
		{
			System.out.println("For phone number :" + entry.getKey()+ " "+ "the value of linked flag is = " +entry.getValue());
			AssertJUnit.assertEquals("mismatch in linked type","0", entry.getValue().trim());
		}
		
		System.out.println(".............. First Email user sign up...............");
		
		String sub_user_phone = generateRandomMobileNumber();
		System.out.println("Random Phone number for first email user sign up = " +sub_user_phone);
		String UID_sub_user1_email = signup_getUID (sub_user1_email1.replaceAll("\"", ""),sub_user_phone);
		System.out.println("UID for sub user email = "+UID_sub_user1_email);
		
		// Main User - email query
		System.out.println(".......... Executing Main User Email Query..........");
		LinkedHashMap<String,String> hm3 = executeEmailQuery_getMap(UID_main_user,environment);
		for (Entry<String,String> entry :hm3.entrySet())
		{
			if (entry.getKey().equals(sub_user1_email1.replaceAll("\"", "")))
			{
			System.out.println("For key - "+entry.getKey() + " the value of linked flag is :" + entry.getValue());
			AssertJUnit.assertEquals("mismatch in linked type","1", entry.getValue().trim());
			}
			else
			{
				System.out.println("For key - "+entry.getKey() + " the value of linked flag is :" + entry.getValue());
				AssertJUnit.assertEquals("mismatch in linked type","0", entry.getValue().trim());
			}
		}
		
		// Main User - relation query
		System.out.println("..........\n Executing Main User relation Query..........");
		
		ArrayList<String> arr = executeRelationQuery_getString(UID_main_user,environment);
		for(int i =0; i< arr.size();i++)
		{
			System.out.println("Friend user for :" +main_user+ " is : " +arr.get(i));
			AssertJUnit.assertEquals("mismatch in relation user types", sub_user1_email1.replaceAll("\"", ""), arr.get(i));
		}
		
		// Sub User - relation query
		System.out.println("..........\n Executing Sub User relation Query..........");
		ArrayList<String> arr1 = executeRelationQuery_getString(UID_sub_user1_email,environment);
		for(int i =0; i< arr1.size();i++)
		{
			System.out.println("Friend user for :" +sub_user1_email1+ " is : " +arr1.get(i));
			AssertJUnit.assertEquals("mismatch in relation user types", main_user.replaceAll("\"", ""), arr1.get(i));
		}
	
		
		System.out.println(".............. \n First Phone user sign up...............");
		
		System.out.println("sign up - phone number :" + sub_user1_phone1.replaceAll("\"", ""));
		String sub_phoneUser_randomEmail = "sub_user_" +generateRandomId1()+"@myntra.com";
		System.out.println("random email id for sub user - phone is :" +sub_phoneUser_randomEmail);
		String UID_sub_user_phone1 = signup_getUID (sub_phoneUser_randomEmail,sub_user1_phone1.replaceAll("\"", ""));
		System.out.println("UID for sub user phone = " +UID_sub_user_phone1);
		
		
		// Main User - Phone query
		System.out.println("...........\n Executing Main User phone query............");
		LinkedHashMap<String,String> hm4 = executePhoneQuery_getMap(UID_main_user,environment);
		for (Entry<String,String> entry :hm4.entrySet())
			{
			   
			   if (entry.getKey().equals(sub_user1_phone1.replaceAll("\"", "")))
					{
				     System.out.println("For key - "+entry.getKey() + " the value of linked flag is :" + entry.getValue());
					 AssertJUnit.assertEquals("mismatch in linked type","1", entry.getValue().trim());
					}
			   else
					{ 
				   System.out.println("For key - "+entry.getKey() + " the value of linked flag is :" + entry.getValue());
					 AssertJUnit.assertEquals("mismatch in linked type","0", entry.getValue().trim());
					}
				}
		 
		// Main User - Relation Query
		System.out.println("...........\n Executing Main User Relation query............");
		ArrayList<String> arr2 = executeRelationQuery_getString(UID_main_user,environment);
		for(int i =0; i< arr2.size();i++)
		{
			System.out.println("Friend user for :" +main_user+ " is : " +arr2.get(i));
			if (arr2.get(i).equals(sub_phoneUser_randomEmail))
			AssertJUnit.assertEquals("mismatch in relation user types", sub_phoneUser_randomEmail, arr2.get(i));
		}
		
		// Sub User - relation query
		System.out.println("...........\n Executing Sub User phone Relation query............");
		ArrayList<String> arr3 = executeRelationQuery_getString(UID_sub_user_phone1,environment);
		for(int i =0; i< arr3.size();i++)
		{
			System.out.println("Friend user for :" +sub_phoneUser_randomEmail+ " is : " +arr3.get(i));
			AssertJUnit.assertEquals("mismatch in relation user types", main_user.replaceAll("\"", ""), arr3.get(i));
		}
		
		
		}
		*/
	
		private LinkedHashMap<String,String> executeEmailQuery_getMap(String UID,String environment) throws SQLException, InterruptedException
		{
			Connection connection1 = null;
			PreparedStatement preparedstatement1 = null;
			ResultSet resultset1 = null;
			
			String sql_query1 = "select email,linked from user_contact_email where uid = ?";
			connection1 = getDataBaseConnection(environment);
			preparedstatement1 = connection1.prepareStatement(sql_query1);
			preparedstatement1.setString(1,UID);
			resultset1 = preparedstatement1.executeQuery();
			LinkedHashMap<String,String> hm1 = new LinkedHashMap<String,String>();
			while(resultset1.next()) 
			{
				String userName = resultset1.getString(1);
			//	System.out.println(userName);
				String linked = resultset1.getString(2);
			//	System.out.println(linked);
				hm1.put(userName, linked);
			}
			
			System.out.println("map = " +hm1);
			
			return hm1;
			
		}
		
		private LinkedHashMap<String,String> executePhoneQuery_getMap(String UID,String environment) throws SQLException, InterruptedException
		{
			Connection connection1 = null;
			PreparedStatement preparedstatement1 = null;
			ResultSet resultset1 = null;
			
			String sql_query1 = "select phone,linked from user_contact_phone where uid = ?";
			connection1 = getDataBaseConnection(environment);
			preparedstatement1 = connection1.prepareStatement(sql_query1);
			preparedstatement1.setString(1,UID);
			resultset1 = preparedstatement1.executeQuery();
			LinkedHashMap<String,String> hm1 = new LinkedHashMap<String,String>();
			while(resultset1.next()) 
			{
				String phone = resultset1.getString(1);
			//	System.out.println(phone);
				String linked = resultset1.getString(2);
			//	System.out.println(linked);
				hm1.put(phone, linked);
			}
			
			System.out.println("map = " +hm1);
			
			return hm1;
			
		}
		
		private ArrayList<String> executeRelationQuery_getString(String UID,String environment) throws SQLException, InterruptedException
		{
			Connection connection1 = null;
			PreparedStatement preparedstatement1 = null;
			ResultSet resultset1 = null;
			
			String sql_query1 = "select fuidx from user_contact_relation where uid = ?";
			connection1 = getDataBaseConnection(environment);
			preparedstatement1 = connection1.prepareStatement(sql_query1);
			preparedstatement1.setString(1,UID);
			resultset1 = preparedstatement1.executeQuery();
			
			ArrayList<String>hm1 = new ArrayList<String>();
			while(resultset1.next()) 
			{
				String userName = resultset1.getString(1);
				System.out.println(userName);
				hm1.add(userName);
			}
			
			return hm1;
		}
		
		public String getUidx(String email){
			String uidx = null;
			MyntraService service = Myntra.getService(ServiceType.PORTAL_IDEA,APINAME.GETUIDX,init.Configurations, new String[] {null}, new String[]{email});
			RequestGenerator req = new RequestGenerator(service);
			String resp = req.respvalidate.returnresponseasstring();
			uidx = JsonPath.read(resp, "$..uidx").toString().replace("[\"", "").replace("\"]", "");
			return uidx;
		}
}
