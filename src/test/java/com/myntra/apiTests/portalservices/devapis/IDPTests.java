package com.myntra.apiTests.portalservices.devapis;

import java.io.IOException;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonProcessingException;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import com.jayway.jsonpath.JsonPath;
import com.myntra.apiTests.dataproviders.devapis.IDPTestsDP;
import com.myntra.apiTests.portalservices.devapiservice.DevAPICommonMethods;
import com.myntra.apiTests.portalservices.devapiservice.IDPTestsHelper;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.Toolbox;
import com.myntra.lordoftherings.gandalf.RequestGenerator;

public class IDPTests extends IDPTestsDP
{
	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(IDPTests.class);
	static IDPTestsHelper IDPHelper = new IDPTestsHelper();
	static DevAPICommonMethods DevAPIHelper = new DevAPICommonMethods();
	
	//Verify SignUp
	@Test(groups={"Sanity"},dataProvider="DevAPI_IDP_SignUp_DP_Sanity",description="Signup Sanity test case. Verify Response Code")
	public void DevAPI_IDP_SignUp_VerifySuccess(String userName, String password, String name, String gender, String mobile)
	{
		RequestGenerator request = IDPHelper.emailSignUp(userName, password, name,gender,mobile);
		System.out.println("Sign Up Response : "+request.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("Sign Up API is down",200,request.response.getStatus());
	}
	
	@Test(groups={"Regression"},dataProvider="DevAPI_IDP_SignUp_DP_Regression",description="Signup Sanity test case. Verify Response Code")
	public void DevAPI_IDP_SignUp_VerifyData(String userName, String password, String name, String gender, String mobile) throws JsonProcessingException, IOException
	{
		RequestGenerator request = IDPHelper.emailSignUp(userName, password, name,gender,mobile);
		System.out.println("Sign Up Response : "+request.respvalidate.returnresponseasstring());
		String response = request.respvalidate.returnresponseasstring();
		if (gender.trim().length()==0) gender="F";
			
		AssertJUnit.assertEquals("Sign Up API is down",200,request.response.getStatus());
		AssertJUnit.assertEquals("Sign Up Data Validation - Meta Code Invalid",200,(int)JsonPath.read(response, "$.meta.code"));
		if(!(name.trim().length()==0))
		AssertJUnit.assertEquals("Sign Up Data Validation - Username mismatch",name,JsonPath.read(response, "$.data.firstname").toString());
		AssertJUnit.assertEquals("Sign Up Data Validation - Email mismatch",userName.toLowerCase(),JsonPath.read(response, "$.data.email").toString().toLowerCase());
		AssertJUnit.assertEquals("Sign Up Data Validation - Mobile mismatch",mobile,JsonPath.read(response, "$.data.mobile").toString());
		AssertJUnit.assertEquals("Sign Up Data Validation - Gender mismatch",gender,JsonPath.read(response, "$.data.gender").toString());

		
	}
	
	@Test(groups={"NodeValidation"},dataProvider="DevAPI_IDP_SignUp_DP_Sanity",description="Signup Sanity test case. Verify Nodes are not empty or null")
	public void DevAPI_IDP_SignUp_VerifyNodes(String userName, String password, String name, String gender, String mobile) throws JsonProcessingException, IOException
	{
		RequestGenerator request = IDPHelper.emailSignUp(userName, password, name,gender,mobile);
		System.out.println("Sign Up Response : "+request.respvalidate.returnresponseasstring());
		String response = request.respvalidate.returnresponseasstring();
		AssertJUnit.assertEquals("Sign Up API is down",200,request.response.getStatus());
		boolean containsEmptyNodes = DevAPIHelper.doesContainEmptyNodes(response);
		AssertJUnit.assertEquals("Found Empty Nodes in Response", false,containsEmptyNodes);
		
		
	}
	
	@Test(groups={"SchemaValidation"},dataProvider="DevAPI_IDP_SignUp_DP_Sanity",description="Signup Sanity test case. Verify JSON Schema")
	public void DevAPI_IDP_SignUp_VerifySchema(String userName, String password, String name, String gender, String mobile) throws JsonProcessingException, IOException
	{
		RequestGenerator request = IDPHelper.emailSignUp(userName, password, name,gender,mobile);
		System.out.println("Sign Up Response : "+request.respvalidate.returnresponseasstring());
		String response = request.respvalidate.returnresponseasstring();
		AssertJUnit.assertEquals("Sign Up API is down",200,request.response.getStatus());
		boolean containsEmptyNodes = DevAPIHelper.doesContainEmptyNodes(response);
		AssertJUnit.assertEquals("Found Empty Nodes in Response", false,containsEmptyNodes);
		try {
			String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/DevAPIs/IDP_SignUp_Schema.txt");
			List < String > missingNodeList = validateServiceResponseNodesUsingSchemaValidator(response, jsonschema);
			AssertJUnit.assertTrue(missingNodeList + " nodes are missing in DevAPI SignUp API response",
					CollectionUtils.isEmpty(missingNodeList));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test(groups={"Regression"},dataProvider="DevAPI_IDP_SignUp_DP_NegativeCases",description="Signup Negative test cases. Verify Response Code")
	public void DevAPI_IDP_SignUp_VerifyFailures(String userName, String password, String name, String gender, String mobile) throws JsonProcessingException, IOException
	{
		RequestGenerator request = IDPHelper.emailSignUp(userName, password, name,gender,mobile);
		System.out.println("Sign Up Response : "+request.respvalidate.returnresponseasstring());
		String response = request.respvalidate.returnresponseasstring();
		AssertJUnit.assertEquals("Sign Up API is down - Gives 200 for invalid data",400,request.response.getStatus());
		AssertJUnit.assertEquals("Sign Up API is down - Message Is Not Valid","Invalid input",JsonPath.read(response, "$.meta.errorDetail").toString());

	}
	
	@Test(groups={"Sanity"},dataProvider="DevAPI_IDP_SignUp_DP_Sanity",description="Signup Sanity test case. Verify Response Code")
	public void DevAPI_IDP_DuplicateSignUp_VerifyFailure(String userName, String password, String name, String gender, String mobile)
	{
		String userEmail = userName;
		RequestGenerator request = IDPHelper.emailSignUp(userEmail, password, name,gender,mobile);
		System.out.println("Sign Up Response : "+request.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("Sign Up API is down",200,request.response.getStatus());
		
		request = IDPHelper.emailSignUp(userEmail, password, name,gender,mobile);
		String response = request.respvalidate.returnresponseasstring();
		int resCode = request.response.getStatus();
		boolean status = (resCode==405 || resCode==400);
		boolean message = ((JsonPath.read(response, "$.meta.errorDetail").toString().equals("Invalid input"))||JsonPath.read(response, "$.meta.errorDetail").toString().equals("The email you entered already exists"));
		
		AssertJUnit.assertEquals("Sign Up API is down - Allows Duplicate SignUp",true,status);
		AssertJUnit.assertEquals("Sign Up API is down - Message Is Not Valid",true,message);

	}
	
	//Verify SignIn API
	@Test(groups={"Sanity"},dataProvider="DevAPI_IDP_SignIn_DP_Sanity",description="SignIn Sanity test case. Verify Response Code")
	public void DevAPI_IDP_SignIn_VerifySuccess(String userName, String password)
	{
		RequestGenerator request = IDPHelper.signIn(userName, password);
		System.out.println("Sign In Response : "+request.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("Sign In API is down",200,request.response.getStatus());
	}
	
	@Test(groups={"Regression"},dataProvider="DevAPI_IDP_SignIn_DP_Sanity",description="SignIn Sanity test case. Verify Response Code")
	public void DevAPI_IDP_SignIn_VerifyData(String userName, String password)
	{
		RequestGenerator request = IDPHelper.signIn(userName, password);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println("Sign In Response : "+response);
		AssertJUnit.assertEquals("Sign In API is down",200,request.response.getStatus());
		AssertJUnit.assertEquals("Sign In API is down - XID is empty",false,JsonPath.read(response, "$.meta.token").toString().isEmpty());
		AssertJUnit.assertEquals("Sign In API is down - XID is empty",userName,JsonPath.read(response, "$.data.email").toString());

	}
	
	@Test(groups={"NodeValidation"},dataProvider="DevAPI_IDP_SignIn_DP_Sanity",description="SignIn Sanity test case. Verify Nodes does not contain null or empty values")
	public void DevAPI_IDP_SignIn_VerifyNodes(String userName, String password) throws JsonProcessingException, IOException
	{
		RequestGenerator request = IDPHelper.signIn(userName, password);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println("Sign In Response : "+response);
		AssertJUnit.assertEquals("Sign In API is down",200,request.response.getStatus());
		boolean containsEmptyNodes = DevAPIHelper.doesContainEmptyNodes(response);
		AssertJUnit.assertEquals("Found Empty Nodes in Response", false,containsEmptyNodes);
	}
	
	@Test(groups={"SchemaValidation"},dataProvider="DevAPI_IDP_SignIn_DP_Sanity",description="SignIn Sanity test case. Verify Schema")
	public void DevAPI_IDP_SignIn_VerifySchema(String userName, String password) throws JsonProcessingException, IOException
	{
		RequestGenerator request = IDPHelper.signIn(userName, password);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println("Sign In Response : "+response);
		AssertJUnit.assertEquals("Sign In API is down",200,request.response.getStatus());
		boolean containsEmptyNodes = DevAPIHelper.doesContainEmptyNodes(response);
		AssertJUnit.assertEquals("Found Empty Nodes in Response", false,containsEmptyNodes);
		try {
			String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/DevAPIs/IDP_SignIn_Schema.txt");
			List < String > missingNodeList = validateServiceResponseNodesUsingSchemaValidator(response, jsonschema);
			AssertJUnit.assertTrue(missingNodeList + " nodes are missing in DevAPI SignIp API response",
					CollectionUtils.isEmpty(missingNodeList));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test(groups={"Regression"},dataProvider="DevAPI_IDP_SignIn_DP_NegativeCases",description="SignIn test case. Verify Negative Scenarios")
	public void DevAPI_IDP_SignIn_VerifyNegativeCases(String userName, String password)
	{
		String errorDetail = "The username or password you entered is incorrect";
		RequestGenerator request = IDPHelper.signIn(userName, password);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println("Sign In Response : "+response);
		AssertJUnit.assertEquals("Sign In API is down",401,request.response.getStatus());
		AssertJUnit.assertEquals("Sign In API is down - Code Invalid",401,(int)JsonPath.read(response, "$.meta.code"));
		AssertJUnit.assertEquals("Sign In API is down - Error Message is invalid",errorDetail,JsonPath.read(response, "$.meta.errorDetail").toString());

	}
	
	//Verify API Refresh call
	@Test(groups={"Sanity"},dataProvider="DevAPI_IDP_SignIn_DP_Sanity",description="API Refresh test case. Verify Response Code")
	public void DevAPI_IDP_Refresh_VerifySuccess(String userName, String password)
	{
		RequestGenerator request = IDPHelper.idpRefresh(userName, password, false, false);
		System.out.println("IDP Refresh Response : "+request.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("IDP Refresh API is down",200,request.response.getStatus());
	}
	
	@Test(groups={"NodeValidation"},dataProvider="DevAPI_IDP_SignIn_DP_Sanity",description="API Refresh test case. Verify Response Code")
	public void DevAPI_IDP_Refresh_VerifyNodes(String userName, String password) throws JsonProcessingException, IOException
	{
		RequestGenerator request = IDPHelper.idpRefresh(userName, password, false, false);
		System.out.println("IDP Refresh Response : "+request.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("IDP Refresh API is down",200,request.response.getStatus());
		boolean containsEmptyNodes = DevAPIHelper.doesContainEmptyNodes(request.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("Found Empty Nodes in Response", false,containsEmptyNodes);
	}
	
	@Test(groups={"SchemaValidation"},dataProvider="DevAPI_IDP_SignIn_DP_Sanity",description="SignIn Sanity test case. Verify Schema")
	public void DevAPI_IDP_APIRefresh_VerifySchema(String userName, String password) throws JsonProcessingException, IOException
	{
		RequestGenerator request = IDPHelper.idpRefresh(userName, password, false, false);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println("IDP Refresh In Response : "+response);
		AssertJUnit.assertEquals("IDP Refresh API is down",200,request.response.getStatus());
		boolean containsEmptyNodes = DevAPIHelper.doesContainEmptyNodes(response);
		AssertJUnit.assertEquals("Found Empty Nodes in Response", false,containsEmptyNodes);
		try {
			String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/DevAPIs/IDP_Refresh_Schema.txt");
			List < String > missingNodeList = validateServiceResponseNodesUsingSchemaValidator(response, jsonschema);
			AssertJUnit.assertTrue(missingNodeList + " nodes are missing in DevAPI Refresh API response",
					CollectionUtils.isEmpty(missingNodeList));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	@Test(groups={"Regression"},dataProvider="DevAPI_IDP_SignIn_DP_Sanity",description="API Refresh test case. Verify SXID update")
	public void DevAPI_IDP_Refresh_VerifyData(String userName, String password)
	{
		RequestGenerator request = IDPHelper.signIn(userName, password);
		String response = request.respvalidate.returnresponseasstring();
		String CurrentSXID = request.response.getHeaderString("sxid");
		request = IDPHelper.idpRefresh(userName, password,false,false);
	    response = request.respvalidate.returnresponseasstring();
	    System.out.println("IDP Refresh API Response :\n "+response);
		AssertJUnit.assertEquals("IDP Refresh API is down",200,request.response.getStatus());
		AssertJUnit.assertEquals("IDP Refresh API is down - XID is changed",IDPHelper.XID,JsonPath.read(response, "$.meta.token").toString());
		AssertJUnit.assertEquals("IDP Refresh API is down - SXID is not changed",false,CurrentSXID.equals(request.response.getHeaderString("sxid")));

	}
	
	@Test(groups={"Regression"},dataProvider="DevAPI_IDP_Refresh_DP_Regression_TamperIDs",description="API Refresh test case. Verify 401 error for tampered sessions update")
	public void DevAPI_IDP_Refresh_VerifyTamperedSessions(String userName, String password, String tamperSXID, String tamperXID, String responseCode)
	{
		RequestGenerator request = IDPHelper.signIn(userName, password);
		String response = request.respvalidate.returnresponseasstring();
	    int code = Integer.parseInt(responseCode);
		request = IDPHelper.idpRefresh(userName, password,Boolean.parseBoolean(tamperSXID),Boolean.parseBoolean(tamperXID));
	    response = request.respvalidate.returnresponseasstring();
	    System.out.println("IDP Refresh API Response :\n "+response);
		AssertJUnit.assertEquals("IDP Refresh API is down",code,request.response.getStatus());
		
	}
	
	//Verify Signout
	@Test(groups={"Sanity"},dataProvider="DevAPI_IDP_SignOut_DP_Sanity",description="SignOut Sanity test case. Verify Response Code")
	public void DevAPI_IDP_SignOut_VerifySuccess(String userName, String password)
	{
		RequestGenerator request = IDPHelper.SignOut(userName, password);
		System.out.println("Sign Out Response : "+request.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("Sign Out API is down",200,request.response.getStatus());
	}
	
	@Test(groups={"Regression"},dataProvider="DevAPI_IDP_SignOut_DP_Sanity",description="SignOut Regression test case. Verify Response Data")
	public void DevAPI_IDP_SignOut_VerifyData(String userName, String password)
	{
		RequestGenerator request = IDPHelper.SignOut(userName, password);
		System.out.println("Sign Out Response : "+request.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("Sign Out API is down",200,request.response.getStatus());
		AssertJUnit.assertEquals("Sign Out API is down - XID is different",IDPHelper.XID,JsonPath.read(request.respvalidate.returnresponseasstring(), "$.meta.token").toString());

	}
	
	@Test(groups={"NodeValidation"},dataProvider="DevAPI_IDP_SignOut_DP_Sanity",description="SignOut Regression test case. Verify Node Data")
	public void DevAPI_IDP_SignOut_VerifyNodes(String userName, String password) throws JsonProcessingException, IOException
	{
		RequestGenerator request = IDPHelper.SignOut(userName, password);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println("Sign Out Response : "+response);
		AssertJUnit.assertEquals("Sign Out API is down",200,request.response.getStatus());
		AssertJUnit.assertEquals("Sign Out API is down - XID is different",IDPHelper.XID,JsonPath.read(response, "$.meta.token").toString());
		boolean containsEmptyNodes = DevAPIHelper.doesContainEmptyNodes(response);
		AssertJUnit.assertEquals("Found Empty Nodes in Response", false,containsEmptyNodes);
	
	}
	
	//Forgot Password
	@Test(groups={"Sanity","Regression"},dataProvider="DevAPI_IDP_PasswordReset_DP_Sanity",description="Reset Sanity test case. Verify Response Code")
	public void DevAPI_IDP_ForgotPassword_VerifySuccess(String email)
	{
		RequestGenerator request = IDPHelper.ResetPassword(email);
		System.out.println("Forgot Password Response : "+request.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("Forgot Password is down",200,request.response.getStatus());
	}
	
	@Test(groups={"NodeValidation"},dataProvider="DevAPI_IDP_PasswordReset_DP_Sanity",description="Reset Sanity test case. Verify Response Code")
	public void DevAPI_IDP_ForgotPassword_VerifyNodes(String email) throws JsonProcessingException, IOException
	{
		RequestGenerator request = IDPHelper.ResetPassword(email);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println("Forgot Password Response : "+response);
		AssertJUnit.assertEquals("Forgot Password is down",200,request.response.getStatus());
		boolean containsEmptyNodes = DevAPIHelper.doesContainEmptyNodes(response);
		AssertJUnit.assertEquals("Found Empty Nodes in Response", false,containsEmptyNodes);
	}
	
	@Test(groups={"Regression"},dataProvider="DevAPI_IDP_PasswordReset_DP_FailCases",description="Reset Sanity test case. Verify Response Code")
	public void DevAPI_IDP_ForgotPassword_VerifyFailureCases(String email)
	{
		RequestGenerator request = IDPHelper.ResetPassword(email);
		System.out.println("Forgot Password Response : "+request.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("Forgot Password is down",500,request.response.getStatus());
	}
	
}
