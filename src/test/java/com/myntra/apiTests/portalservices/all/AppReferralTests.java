package com.myntra.apiTests.portalservices.all;

import com.jayway.jsonpath.JsonPath;
import com.myntra.apiTests.dataproviders.AppReferralTestsDP;
import com.myntra.apiTests.portalservices.AppReferral.AppReferralServiceHelper;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.Toolbox;
import com.myntra.apiTests.common.APINAME;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.glassfish.jersey.client.ClientConfig;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import java.util.List;


public class AppReferralTests extends AppReferralTestsDP
{
	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(AppReferralTests.class);
	static APIUtilities apiUtil = new APIUtilities();
	static AppReferralServiceHelper appReferralServiceHelper = new AppReferralServiceHelper();
	
	WebTarget target;
	ClientConfig config;
	Client client;
	Invocation.Builder invBuilder;
	
	
	
	//Test Cases
	//Get Referral Code	
	@Test(groups = {"Smoke", "ProdSanity", "Sanity", "MiniRegression", "ExhaustiveRegression"},dataProvider = "AppReferral_GetReferralCode_Success")
	public void AppReferral_GetReferralCode_VerifySuccess(String userName, String password)
	{
		RequestGenerator request = appReferralServiceHelper.invokeGetReferralCodeService(userName, password, "P");
		String response = request.respvalidate.returnresponseasstring();
		System.out.println(response);
		AssertJUnit.assertEquals("App Referral - Get Referral Code API is not working",200,request.response.getStatus());
	}
	
	@Test(groups = {"Smoke","ProdSanity","Sanity","MiniRegression","ExhaustiveRegression"}, dataProvider="AppReferral_GetReferralCode_Success")
	public void AppReferral_GetReferralCode_VerifyDataNodes(String userName, String password)
	{
		RequestGenerator request = appReferralServiceHelper.invokeGetReferralCodeService(userName, password, "P");
		String response = request.respvalidate.returnresponseasstring();
		System.out.println(response);
		AssertJUnit.assertEquals("App Referral - Get Referral Code API is not working",200,request.response.getStatus());
		AssertJUnit.assertEquals("App Referral - Get Referral Code API is not returning referral code", false,JsonPath.read(response, "$.data.code").equals(null));
	}
	
	@Test(groups = {"Smoke", "ProdSanity", "Sanity", "MiniRegression", "ExhaustiveRegression"},dataProvider = "AppReferral_GetReferralCode_Success")
	public void AppReferral_GetReferralCode_VerifySameCodeGeneration(String userName, String password)
	{
		RequestGenerator request = appReferralServiceHelper.invokeGetReferralCodeService(userName, password, "P");
		String response = request.respvalidate.returnresponseasstring();
		System.out.println(response);
		String referralCode = JsonPath.read(response, "$.data.code");
		for(int i=0; i<3; i++)
		{
			RequestGenerator requestCheck = appReferralServiceHelper.invokeGetReferralCodeService(userName, password, "P");
			String responseCheck = requestCheck.respvalidate.returnresponseasstring();
			System.out.println("\nPrinting Response No-"+(i+1)+" : "+responseCheck);
			AssertJUnit.assertEquals("App Referral - Get Referral Code API is not working",200,request.response.getStatus());
			AssertJUnit.assertEquals("App Referral - Get Referral Code API is Returning Different Code For Same User", referralCode,JsonPath.read(response, "$.data.code").toString().trim());
		}
		
	}
	
	@Test(groups = {"Schema Valdiation"},dataProvider = "AppReferral_GetReferralCode_Success")
	public void AppReferral_GetReferralCode_VerifySchema(String userName, String password)
	{
		RequestGenerator request = appReferralServiceHelper.invokeGetReferralCodeService(userName, password, "P");
		String response = request.respvalidate.returnresponseasstring();
		System.out.println(response);
		AssertJUnit.assertEquals("App Referral - Get Referral Code API is not working",200,request.response.getStatus());

		try {
	        String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/DevApi-AppReferral-GetReferralCode-Schema.txt");
	        List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(response, jsonschema);
	        AssertJUnit.assertTrue(missingNodeList+" nodes are missing in App Referral - Get Referral Code API response",CollectionUtils.isEmpty(missingNodeList));
	    	} catch (Exception e)
	    		{
	    		e.printStackTrace();
	    		}
	
	}
	
	@Test(groups = {"Smoke", "ProdSanity", "Sanity", "MiniRegression", "ExhaustiveRegression"},dataProvider = "AppReferral_GetReferralCode_Success")
	public void AppReferral_GetReferralCode_VerifyFailure(String userName, String password)
	{
		RequestGenerator request = appReferralServiceHelper.invokeGetReferralCodeService(userName, password, "N");
		String response = request.respvalidate.returnresponseasstring();
		System.out.println(response);
		AssertJUnit.assertEquals("App Referral - Get Referral Code API is not working",401,request.response.getStatus());
	}
		
	//Get Referrer Details - Includes Earnings and other details
	@Test(groups = {"Smoke", "ProdSanity", "Sanity", "MiniRegression", "ExhaustiveRegression"},dataProvider = "AppReferral_GetDetails")
	public void AppReferral_GetDetails_VerifySuccess(String userName, String password)
	{
		RequestGenerator request = appReferralServiceHelper.invokeGetMyEarningsService(userName, password, "P");
		String response = request.respvalidate.returnresponseasstring();
		System.out.println(response);
		AssertJUnit.assertEquals("App Referral - Get My Details API is not working",200,request.response.getStatus());
	}
	
	@Test(groups = {"Smoke", "ProdSanity", "Sanity", "MiniRegression", "ExhaustiveRegression"},dataProvider = "AppReferral_GetDetails")
	public void AppReferral_GetDetails_VerifyDataNodes(String userName, String password)
	{
		RequestGenerator request = appReferralServiceHelper.invokeGetMyEarningsService(userName, password, "P");
		String response = request.respvalidate.returnresponseasstring();
		System.out.println(response);
		AssertJUnit.assertEquals("App Referral - Get My Details API is not working",200,request.response.getStatus());
		AssertJUnit.assertEquals("App Referral - Get My Details API is not working", true,JsonPath.read(response, "$.data.success").toString().equals("true"));
		AssertJUnit.assertEquals("App Referral - Get My Details API is not returning earnings", false,JsonPath.read(response, "$.data.totalEarnings").equals(null));

	}
	
	@Test(groups = {"Schema Valdiation"},dataProvider = "AppReferral_GetDetails")
	public void AppReferral_GetDetails_VerifySchema(String userName, String password)
	{
		RequestGenerator request = appReferralServiceHelper.invokeGetMyEarningsService(userName, password, "P");
		String response = request.respvalidate.returnresponseasstring();
		System.out.println(response);
		try {
		      String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/DevApi-AppReferral-GetMyDetails-Schema.txt");
		      List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(response, jsonschema);
		      AssertJUnit.assertTrue(missingNodeList+" nodes are missing in App Referral - Get My details API response",CollectionUtils.isEmpty(missingNodeList));
		  } catch (Exception e)
		  	{
		    e.printStackTrace();
		  	}
		}
	
	@Test(groups = {"Smoke", "ProdSanity", "Sanity", "MiniRegression", "ExhaustiveRegression"},dataProvider = "AppReferral_GetDetails")
	public void AppReferral_GetDetails_VerifyFailure(String userName, String password)
	{
		RequestGenerator request = appReferralServiceHelper.invokeGetMyEarningsService(userName, password, "N");
		String response = request.respvalidate.returnresponseasstring();
		System.out.println(response);
		AssertJUnit.assertEquals("App Referral - Get My Details API is working with inValid XID",401,request.response.getStatus());
	}
	
	//Welcome API for Referee.
	@Test(groups = {"Smoke", "ProdSanity", "Sanity", "MiniRegression", "ExhaustiveRegression"},dataProvider = "AppReferral_Welcome")
	public void AppReferral_Welcome_VerifySuccess(String userName, String password, String referCode)
	{
		RequestGenerator request = appReferralServiceHelper.invokeWelcomeAPIService(userName, password, referCode,"P");
		String response = request.respvalidate.returnresponseasstring();
		System.out.println(response);
		AssertJUnit.assertEquals("App Referral - Welcome User API is not working",200,request.response.getStatus());
	}

	@Test(groups = {"Schema Valdiation"},dataProvider = "AppReferral_Welcome")
	public void AppReferral_Welcome_VerifySchema(String userName, String password, String referCode)
	{
		RequestGenerator request = appReferralServiceHelper.invokeWelcomeAPIService(userName, password, referCode,"P");
		String response = request.respvalidate.returnresponseasstring();
		System.out.println(response);
		AssertJUnit.assertEquals("App Referral - Welcome User API is not working",200,request.response.getStatus());

		try {
	      String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/DevApi-AppReferral-Welcome-Schema.txt");
	      List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(response, jsonschema);
	      AssertJUnit.assertTrue(missingNodeList+" nodes are missing in App Referral - Welcome API response", 
	      		CollectionUtils.isEmpty(missingNodeList));
	  } catch (Exception e) {
	      e.printStackTrace();
	  }
	}

	@Test(groups = {"Smoke", "ProdSanity", "Sanity", "MiniRegression", "ExhaustiveRegression"},dataProvider = "AppReferral_Welcome")
	public void AppReferral_Welcome_VerifyDataNodes(String userName, String password, String referCode)
	{
		RequestGenerator request = appReferralServiceHelper.invokeWelcomeAPIService(userName, password, referCode,"P");
		String response = request.respvalidate.returnresponseasstring();
		System.out.println(response);
		AssertJUnit.assertEquals("App Referral - Welcome User API is not working",200,request.response.getStatus());
		AssertJUnit.assertEquals("App Referral - Welcome : Referral Code is not proper",referCode,JsonPath.read(response,"$.data.code").toString().trim());
		AssertJUnit.assertEquals("App Referral - Welcome : Referrer Mail is not proper",userName,JsonPath.read(response,"$.data.referrer").toString().trim());
		AssertJUnit.assertEquals("App Referral - Welcome : Success Status is not proper","true",JsonPath.read(response,"$.data.success").toString().trim());

	}

	//GET OTP API
	@Test(groups = {"Smoke", "ProdSanity", "Sanity", "MiniRegression", "ExhaustiveRegression"},dataProvider = "AppReferral_GetOTP_Success")
	public void AppReferral_GetOTP_VerifySuccessResponse(String userName, String password, String Mobile, String Mode) throws Exception
	{
		RequestGenerator request = appReferralServiceHelper.invokeGetOTPService(userName, password, Mobile, "P");
		String response = request.respvalidate.returnresponseasstring();
		System.out.println(response);
		AssertJUnit.assertEquals("App Referral - Get OTP API is not working",200,request.response.getStatus());
	}

	@Test(groups = {"Schema Validation"},dataProvider = "AppReferral_GetOTP_Success")
	public void AppReferral_GetOTP_VerifySchema(String userName, String password, String Mobile, String Mode) throws Exception
	{
		RequestGenerator request = appReferralServiceHelper.invokeGetOTPService(userName, password, Mobile, "P");
		String response = request.respvalidate.returnresponseasstring();
		System.out.println(response);
		AssertJUnit.assertEquals("App Referral - Get OTP API is not working",200,request.response.getStatus());
		try{
		      String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/DevApi-Referral-GetOTP-Schema.txt");
		      List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(response, jsonschema);
		      AssertJUnit.assertTrue(missingNodeList+" nodes are missing in App Referral - Get OTP API response", 
		      		CollectionUtils.isEmpty(missingNodeList));
		  }catch (Exception e) 
		  {
		      e.printStackTrace();
		  }
	}

	@Test(groups = {"Schema Validation"},dataProvider = "AppReferral_GetOTP_Success")
	public void AppReferral_GetOTP_VerifyDataNodes(String userName, String password, String Mobile, String Mode) throws Exception
	{
		RequestGenerator request = appReferralServiceHelper.invokeGetOTPService(userName, password, Mobile, "P");
		String response = request.respvalidate.returnresponseasstring();
		System.out.println(response);
		AssertJUnit.assertEquals("App Referral - Get OTP API is not working",200,request.response.getStatus());
		AssertJUnit.assertEquals("App Referral - Get OTP API is not working", "true",JsonPath.read(response, "$.data.success").toString().trim());
		AssertJUnit.assertEquals("App Referral - Get OTP API is not working", "OTP Sent successfully",JsonPath.read(response, "$.data.message").toString().trim());
	}
	
	//Need new phone number each time or Have to unblock the number
	@Test(groups = {"Smoke", "ProdSanity", "Sanity", "MiniRegression", "ExhaustiveRegression"},dataProvider = "AppReferral_GetOTP_Success")
	public void AppReferral_GetOTP_RetrialCheck(String userName, String password, String Mobile, String Mode) throws Exception
	{
		int retrialCount = 3;
		
		for(int i = 0; i<3; i++)
		{
			--retrialCount;
			RequestGenerator request = appReferralServiceHelper.invokeGetOTPService(userName, password, Mobile, "P");
			String response = request.respvalidate.returnresponseasstring();
			System.out.println(response);
			AssertJUnit.assertEquals("App Referral - Get OTP API is not working",200,request.response.getStatus());
			AssertJUnit.assertEquals("App Referral - Get OTP API is not working-Retry", "true",JsonPath.read(response, "$.data.success").toString().trim());
			AssertJUnit.assertEquals("App Referral - Get OTP API is not working", "OTP Sent successfully",JsonPath.read(response, "$.data.message").toString().trim());
			AssertJUnit.assertEquals("App Referral - Get OTP API is not working", retrialCount,Integer.parseInt(JsonPath.read(response, "$.data.retrials")));

		}
		RequestGenerator request = appReferralServiceHelper.invokeGetOTPService(userName, password, Mobile, "P");
		String response = request.respvalidate.returnresponseasstring();
		System.out.println(response);
		AssertJUnit.assertEquals("App Referral - Get OTP API is not working",200,request.response.getStatus());
		AssertJUnit.assertEquals("App Referral - Get OTP API is not working-exhausted", "false",JsonPath.read(response, "$.data.success").toString().trim());
		AssertJUnit.assertEquals("App Referral - Get OTP API is not working", "Attempts exhausted",JsonPath.read(response, "$.data.message").toString().trim());
	}
		
	@Test(groups = {"Smoke", "ProdSanity", "Sanity", "MiniRegression", "ExhaustiveRegression"},dataProvider = "AppReferral_GetOTP_Fail")
	public void AppReferral_GetOTP_VerifyFailure(String userName, String password, String Mobile, String Mode) throws Exception
	{
		RequestGenerator request = appReferralServiceHelper.invokeGetOTPService(userName, password, Mobile, "P");
		String response = request.respvalidate.returnresponseasstring();
		System.out.println(response);
		
		if (Mode.equals("N"))
		{
			AssertJUnit.assertEquals("App Referral - Get OTP API is not working",200,request.response.getStatus());
		}
		else
		{
		AssertJUnit.assertEquals("App Referral - Get OTP API is not working",200,request.response.getStatus());
		AssertJUnit.assertEquals("App Referral - Get OTP API is not working", "false",JsonPath.read(response, "$.data.success").toString().trim());
		AssertJUnit.assertEquals("App Referral - Get OTP API is not working", "Not a valid mobile number.",JsonPath.read(response, "$.data.message").toString().trim());
		}

	}
	
	//Need new phone number each time or Have to unblock the number
	@Test(groups = {"Smoke", "ProdSanity", "Sanity", "MiniRegression", "ExhaustiveRegression"},dataProvider = "AppReferral_GetOTP_Success_DifferentNumbers")
	public void AppReferral_GetOTP_RetrialCheck_DifferentNumbers(String userName, String password, String Mobile1, String Mobile2, String Mobile3, String Mode) throws Exception
	{
		int retrialCount = 3;
		String[] MobileNumber = {Mobile1, Mobile2, Mobile3};
		
		for(int i = 0; i<3; i++)
		{
			--retrialCount;
			RequestGenerator request = appReferralServiceHelper.invokeGetOTPService(userName, password, MobileNumber[i], "P");
			String response = request.respvalidate.returnresponseasstring();
			System.out.println(response);
			AssertJUnit.assertEquals("App Referral - Get OTP API is not working",200,request.response.getStatus());
			AssertJUnit.assertEquals("App Referral - Get OTP API is not working", "true",JsonPath.read(response, "$.data.success").toString().trim());
			AssertJUnit.assertEquals("App Referral - Get OTP API is not working", "OTP Sent successfully",JsonPath.read(response, "$.data.message").toString().trim());
			AssertJUnit.assertEquals("App Referral - Get OTP API is not working", retrialCount,Integer.parseInt(JsonPath.read(response, "$.data.retrials")));
		}
		RequestGenerator request = appReferralServiceHelper.invokeGetOTPService(userName, password, MobileNumber[1], "P");
		String response = request.respvalidate.returnresponseasstring();
		System.out.println(response);
		AssertJUnit.assertEquals("App Referral - Get OTP API is not working",200,request.response.getStatus());
		AssertJUnit.assertEquals("App Referral - Get OTP API is not working", "false",JsonPath.read(response, "$.data.success").toString().trim());
		AssertJUnit.assertEquals("App Referral - Get OTP API is not working", "Attempts exhausted",JsonPath.read(response, "$.data.message").toString().trim());
	}		
	
	
	
	
	
	
	
	//Device Registration in Magasin - Need new device ID, Installation ID, IMEI to run successfully
	@Test(groups = {"Smoke", "ProdSanity", "Sanity", "MiniRegression", "ExhaustiveRegression"},dataProvider = "AppReferral_UnRegisteredDeviceFlow_Success")
	public void AppReferral_UnRegisteredDeviceFlow_Success(String userName, String password, String referCode, String DeviceID, String InstallationID, String IMEI, String isRooted)
	{
		RequestGenerator request = appReferralServiceHelper.invokeMagasinService_UnRegisteredDevice(userName, password, referCode, DeviceID, InstallationID, IMEI, isRooted);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println(response);
		AssertJUnit.assertEquals("Magasin - UnRegisteredDevice - API is not working",200,request.response.getStatus());
		AssertJUnit.assertEquals("Magasin - UnRegisteredDevice - API is not working", "10002",JsonPath.read(response, "$.data.code").toString().trim());
		AssertJUnit.assertEquals("Magasin - UnRegisteredDevice - API is not working", "UNREGISTERED_DEVICE",JsonPath.read(response, "$.data.status").toString().trim());
		AssertJUnit.assertEquals("Magasin - UnRegisteredDevice - API is not working", "Sorry! Your device is not registered with us",JsonPath.read(response, "$.data.message").toString().trim());

	}

	//Device Registration in Magasin - Need new device ID, Installation ID, IMEI to run successfully
	@Test(groups = {"Smoke", "ProdSanity", "Sanity", "MiniRegression", "ExhaustiveRegression"},dataProvider = "AppReferral_RegisteredDeviceData_Success")
	public void AppReferral_RegisterDeviceFlow_Success(String userName, String password, String DeviceID, String InstallationID, String IMEI, String isRooted)
	{

		RequestGenerator request = appReferralServiceHelper.invokeMagasinService_CreateAndUpdateDeviceData(userName, password, APINAME.CREATEDEVICEDATA, DeviceID, InstallationID, IMEI, isRooted, "createFlow","");
		String response = request.respvalidate.returnresponseasstring();
		System.out.println(response);
		AssertJUnit.assertEquals("Magasin - Register New Device - API is not working",200,request.response.getStatus());
		AssertJUnit.assertEquals("Magasin - Register New Device - API is not working [Device ID is not proper in Response]",true,(JsonPath.read(response, "$.deviceId").toString().contains(DeviceID)));
		AssertJUnit.assertEquals("Magasin - Register New Device - API is not working [IMEI is not proper in Response]",true,(JsonPath.read(response, "$.imei").toString().contains(IMEI)));
		AssertJUnit.assertEquals("Magasin - Register New Device - API is not working [Installation ID is not proper in Response]",true,(JsonPath.read(response, "$.installationId").toString().contains(InstallationID)));

	}
	
	//Device Registration in Magasin - Need new device ID, Installation ID, IMEI to run successfully
	@Test(groups = {"Smoke", "ProdSanity", "Sanity", "MiniRegression", "ExhaustiveRegression"},dataProvider = "AppReferral_RegisteredDeviceData_Success")
	public void AppReferral_RegisterDeviceFlow_Failure_AlreadyExistingDevice(String userName, String password, String DeviceID, String InstallationID, String IMEI, String isRooted)
	{
		RequestGenerator request = appReferralServiceHelper.invokeMagasinService_CreateAndUpdateDeviceData(userName, password, APINAME.CREATEDEVICEDATA, DeviceID, InstallationID, IMEI, isRooted, "createFlow","");
		String response = request.respvalidate.returnresponseasstring();
		System.out.println(response);
		AssertJUnit.assertEquals("Magasin - Register New Device - API is not working",200,request.response.getStatus());
		AssertJUnit.assertEquals("Magasin - Register New Device - API is not working [Device ID is not proper in Response]",true,(JsonPath.read(response, "$.deviceId").toString().contains(DeviceID)));
		AssertJUnit.assertEquals("Magasin - Register New Device - API is not working [IMEI is not proper in Response]",true,(JsonPath.read(response, "$.imei").toString().contains(IMEI)));
		AssertJUnit.assertEquals("Magasin - Register New Device - API is not working [Installation ID is not proper in Response]",true,(JsonPath.read(response, "$.installationId").toString().contains(InstallationID)));
		
		RequestGenerator duplicateRequest = appReferralServiceHelper.invokeMagasinService_CreateAndUpdateDeviceData(userName, password, APINAME.CREATEDEVICEDATA, DeviceID, InstallationID, IMEI, isRooted, "duplicateFlow","");
		String finalResponse = duplicateRequest.respvalidate.returnresponseasstring();
		System.out.println("\nDuplicate Registration Response");
		System.out.println(finalResponse);
		AssertJUnit.assertEquals("Magasin - Register New Device - API is not working",200,request.response.getStatus());
			
		AssertJUnit.assertEquals("Magasin - Duplicate Device check is not working [Status code is not proper]","409",(JsonPath.read(finalResponse, "$.meta.code")).toString());
		AssertJUnit.assertEquals("Magasin - Duplicate Device check is not working [Error Message is not proper in Response]","Duplicate Installation Id. Please try again",(JsonPath.read(finalResponse, "$.meta.errorDetail")).toString());
		
		
	}
	
	@Test(groups = {"Smoke", "ProdSanity", "Sanity", "MiniRegression", "ExhaustiveRegression"},dataProvider = "AppReferral_UpdateDeviceData_Success")
	public void AppReferral_UpdateDeviceFlow_Success(String userName, String password, String DeviceID, String InstallationID, String IMEI, String isRooted)
	{
		String Data = "Update";
		RequestGenerator CreateRequest = appReferralServiceHelper.invokeMagasinService_CreateAndUpdateDeviceData(userName, password, APINAME.CREATEDEVICEDATA, DeviceID, InstallationID, IMEI, isRooted, "createFlow",Data);
		String CreateResponse = CreateRequest.respvalidate.returnresponseasstring();
		System.out.println(CreateResponse);
		AssertJUnit.assertEquals("Magasin - Register New Device - API is not working",200,CreateRequest.response.getStatus());
		AssertJUnit.assertEquals("Magasin - Register New Device - API is not working [Device ID is not proper in Response]",true,(JsonPath.read(CreateResponse, "$.deviceId").toString().contains(DeviceID)));
		AssertJUnit.assertEquals("Magasin - Register New Device - API is not working [IMEI is not proper in Response]",true,(JsonPath.read(CreateResponse, "$.imei").toString().contains(IMEI)));
		AssertJUnit.assertEquals("Magasin - Register New Device - API is not working [Installation ID is not proper in Response]",true,(JsonPath.read(CreateResponse, "$.installationId").toString().contains(InstallationID)));
		
		
		RequestGenerator UpdateRequest = appReferralServiceHelper.invokeMagasinService_CreateAndUpdateDeviceData(userName, password, APINAME.UPDATEDEVICEDATA, DeviceID, InstallationID, IMEI, isRooted, "updateFlow",Data);
		String UpdateResponse = UpdateRequest.respvalidate.returnresponseasstring();
		System.out.println(UpdateResponse);
		IMEI.concat(Data);
		InstallationID.concat(Data);
		AssertJUnit.assertEquals("Magasin - Update New Device - API is not working",200,UpdateRequest.response.getStatus());
	}
	
	@Test(groups = {"Smoke", "ProdSanity", "Sanity", "MiniRegression", "ExhaustiveRegression"},dataProvider = "AppReferral_DeviceInEligibleFlow_RootedDevice")
	public void AppReferral_DeviceInEligibleFlow_RootedDevice(String userName, String password, String referCode, String DeviceID, String InstallationID, String IMEI, String isRooted)
	{
		RequestGenerator CreateRootedDeviceRequest = appReferralServiceHelper.invokeMagasinService_CreateAndUpdateDeviceData(userName, password, APINAME.CREATEDEVICEDATA, DeviceID, InstallationID, IMEI, isRooted, "createFlow","");
		String CreateRootedDeviceResponse = CreateRootedDeviceRequest.respvalidate.returnresponseasstring();
		System.out.println(CreateRootedDeviceResponse);
		AssertJUnit.assertEquals("Magasin - Register New Device - API is not working",200,CreateRootedDeviceRequest.response.getStatus());
		RequestGenerator VerifyRootedDeviceRequest = appReferralServiceHelper.invokeVerifyReferralCodeService_Magasin(userName, password, referCode, isRooted);
		String VerifyRootedDeviceResponse = VerifyRootedDeviceRequest.respvalidate.returnresponseasstring();
		System.out.println(VerifyRootedDeviceResponse);
		
		AssertJUnit.assertEquals("Magasin - RootedDevice Check - API is not working",200,VerifyRootedDeviceRequest.response.getStatus());
		AssertJUnit.assertEquals("Magasin - RootedDevice Check - API is not working", "11111",JsonPath.read(VerifyRootedDeviceResponse, "$.data.code").toString().trim());
		AssertJUnit.assertEquals("Magasin - RootedDevice Check - API is not working", "DEVICE_INELIGIBLE",JsonPath.read(VerifyRootedDeviceResponse, "$.data.status").toString().trim());
		AssertJUnit.assertEquals("Magasin - RootedDevice Check - API is not working", "Sorry, the Signup Reward is only for new users, on new devices. You can still get rewards by referring your friends.",JsonPath.read(VerifyRootedDeviceResponse, "$.data.message").toString().trim());

	}	
	
	@Test(groups = {"Smoke", "ProdSanity", "Sanity", "MiniRegression", "ExhaustiveRegression"},dataProvider = "AppReferral_FreshInstallFlow")
	public void AppReferral_FreshInstallFlow_Success(String userName, String password, String referCode, String DeviceID, String InstallationID, String IMEI, String isRooted)
	{
		RequestGenerator CreateDeviceRequest = appReferralServiceHelper.invokeMagasinService_CreateAndUpdateDeviceData(userName, password, APINAME.CREATEDEVICEDATA, DeviceID, InstallationID, IMEI, isRooted, "createFlow","");
		String CreateDeviceResponse = CreateDeviceRequest.respvalidate.returnresponseasstring();
		System.out.println(CreateDeviceResponse);
		AssertJUnit.assertEquals("Magasin - Register New Device - API is not working",200,CreateDeviceRequest.response.getStatus());
		RequestGenerator request = appReferralServiceHelper.invokeVerifyReferralCodeService_Magasin(userName, password, referCode, isRooted);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println(response);
		
		AssertJUnit.assertEquals("AppReferral- Verify Referral Code - API is not working",200,request.response.getStatus());
		AssertJUnit.assertEquals("AppReferral- Verify Referral Code - API is not working", "10000",JsonPath.read(response, "$.data.code").toString().trim());
		AssertJUnit.assertEquals("AppReferral- Verify Referral Code - API is not working", "FRESH_INSTALL",JsonPath.read(response, "$.data.status").toString().trim());
		AssertJUnit.assertEquals("AppReferral- Verify Referral Code - API is not working", "Fresh Install",JsonPath.read(response, "$.data.message").toString().trim());
	}
	
	
	@Test(groups = {"Smoke", "ProdSanity", "Sanity", "MiniRegression", "ExhaustiveRegression"},dataProvider = "AppReferral_FreshInstall_VerifyReferCode_Success")
	public void AppReferral_FreshInstallFlow_VerifyCode_Success(String userName, String randomUserName, String password, String referCode, String DeviceID, String InstallationID, String IMEI, String isRooted)
	{
		//Create New Device Data
		RequestGenerator CreateDeviceRequest = appReferralServiceHelper.invokeMagasinService_CreateAndUpdateDeviceData(userName, password, APINAME.CREATEDEVICEDATA, DeviceID, InstallationID, IMEI, isRooted, "createFlow","");
		String CreateDeviceResponse = CreateDeviceRequest.respvalidate.returnresponseasstring();
		System.out.println(CreateDeviceResponse);
		AssertJUnit.assertEquals("Magasin - Register New Device - API is not working",200,CreateDeviceRequest.response.getStatus());
		
		//Fraud Check
		RequestGenerator Magasin_request = appReferralServiceHelper.invokeVerifyReferralCodeService_Magasin(userName, password, referCode, isRooted);
		String Magasin_response = Magasin_request.respvalidate.returnresponseasstring();
		System.out.println(Magasin_response);
		
		AssertJUnit.assertEquals("AppReferral- Verify Referral Code[Fraud Check] - API is not working",200,Magasin_request.response.getStatus());
		AssertJUnit.assertEquals("AppReferral- Verify Referral Code[Fraud Check] - API is not working", "10000",JsonPath.read(Magasin_response, "$.data.code").toString().trim());
		AssertJUnit.assertEquals("AppReferral- Verify Referral Code[Fraud Check] - API is not working", "FRESH_INSTALL",JsonPath.read(Magasin_response, "$.data.status").toString().trim());
		AssertJUnit.assertEquals("AppReferral- Verify Referral Code[Fraud Check] - API is not working", "Fresh Install",JsonPath.read(Magasin_response, "$.data.message").toString().trim());
		
		//Verify Refer Code	
		RequestGenerator verifyRequest = appReferralServiceHelper.invokeVerifyReferralCodeService_AppReferral(randomUserName, password, referCode, isRooted,0);
		String verifyResponse = verifyRequest.respvalidate.returnresponseasstring();
		System.out.println("\n VERIFY REFERRAL CODE RESPONSE\n");
		System.out.println(verifyResponse);
		
		AssertJUnit.assertEquals("AppReferral- Verify Referral Code[Referral] - API is not working",200,verifyRequest.response.getStatus());
		AssertJUnit.assertEquals("AppReferral- Verify Referral Code[Referral] - API is not working [Referral Code is not proper]",referCode,JsonPath.read(verifyResponse, "$.data.code").toString().trim());
		AssertJUnit.assertEquals("AppReferral- Verify Referral Code[Referral] - API is not working [Retrials Count is not proper]","0",JsonPath.read(verifyResponse, "$.data.retrials").toString().trim());
		AssertJUnit.assertEquals("AppReferral- Verify Referral Code[Referral] - API is not working [Success status is not proper]","true",JsonPath.read(verifyResponse, "$.data.success").toString().trim());
		AssertJUnit.assertEquals("AppReferral- Verify Referral Code[Referral] - API is not working [Message is not proper]","One Last step to get your reward. Please provide your Mobile number to receive the One-Time Password (OTP)",JsonPath.read(verifyResponse, "$.data.message").toString().trim());
	
	}
	
	@Test(groups = {"Smoke", "ProdSanity", "Sanity", "MiniRegression", "ExhaustiveRegression"},dataProvider = "AppReferral_FreshInstall_VerifyReferCode_Retiral")
	public void AppReferral_FreshInstallFlow_VerifyCode_Retrial_Failure(String userName, String randomUserName, String password, String referCode, String DeviceID, String InstallationID, String IMEI, String isRooted)
	{
		//Create New Device Data
		RequestGenerator CreateDeviceRequest = appReferralServiceHelper.invokeMagasinService_CreateAndUpdateDeviceData(userName, password, APINAME.CREATEDEVICEDATA, DeviceID, InstallationID, IMEI, isRooted, "createFlow","");
		String CreateDeviceResponse = CreateDeviceRequest.respvalidate.returnresponseasstring();
		System.out.println(CreateDeviceResponse);
		AssertJUnit.assertEquals("Magasin - Register New Device - API is not working",200,CreateDeviceRequest.response.getStatus());
		
		//Fraud Check
		RequestGenerator Magasin_request = appReferralServiceHelper.invokeVerifyReferralCodeService_Magasin(userName, password, referCode, isRooted);
		String Magasin_response = Magasin_request.respvalidate.returnresponseasstring();
		System.out.println(Magasin_response);
		
		AssertJUnit.assertEquals("AppReferral- Verify Referral Code[Fraud Check] - API is not working",200,Magasin_request.response.getStatus());
		AssertJUnit.assertEquals("AppReferral- Verify Referral Code[Fraud Check] - API is not working", "10000",JsonPath.read(Magasin_response, "$.data.code").toString().trim());
		AssertJUnit.assertEquals("AppReferral- Verify Referral Code[Fraud Check] - API is not working", "FRESH_INSTALL",JsonPath.read(Magasin_response, "$.data.status").toString().trim());
		AssertJUnit.assertEquals("AppReferral- Verify Referral Code[Fraud Check] - API is not working", "Fresh Install",JsonPath.read(Magasin_response, "$.data.message").toString().trim());
		int checker = 0;
		int retrialCount = 3;
		for(int i=0; i<=3; i++ )
		{
			--retrialCount;
			String Message = "";
			
			switch (i)
			{
			case 0:
				Message = "The Code is not valid. Please try again with a Valid Code.";
				break;
			case 1:
				Message = "Ouch. Thats twice now. This is your last chance to enter a valid referral Code.";
				break;
			case 2:
				Message = "Sorry. You\'ve run out of your referral attempts. But hey, you can still get rewards by referring your friends.";
				break;
			case 3:
				Message = "Sorry. You\'ve run out of your referral attempts. But hey, you can still get rewards by referring your friends.";
				break;
			}
			
			if(retrialCount==-1)
			{
				checker = 0;
			}
			else
			{
				checker = retrialCount;
			}
			
			referCode = referCode.concat("#$%^&asd*").substring(i+2, 6);
			RequestGenerator verifyRequest = appReferralServiceHelper.invokeVerifyReferralCodeService_AppReferral(randomUserName, password, referCode, isRooted,i);
			String verifyResponse = verifyRequest.respvalidate.returnresponseasstring();
			System.out.println("\n VERIFY REFERRAL CODE RESPONSE\n");
			System.out.println(verifyResponse);
			
			
			
			AssertJUnit.assertEquals("AppReferral- Verify Referral Code[Referral] - API is not working",200,verifyRequest.response.getStatus());
			AssertJUnit.assertEquals("AppReferral- Verify Referral Code[Referral] - API is not working [Referral Code is not proper] in Trial"+(i+1),referCode,JsonPath.read(verifyResponse, "$.data.code").toString().trim());
			AssertJUnit.assertEquals("AppReferral- Verify Referral Code[Referral] - API is not working [Retrials Count is not proper]in Trial"+(i+1),checker,Integer.parseInt(JsonPath.read(verifyResponse, "$.data.retrials")));
			AssertJUnit.assertEquals("AppReferral- Verify Referral Code[Referral] - API is not working [Success status is not proper]in Trial"+(i+1),"false",JsonPath.read(verifyResponse, "$.data.success").toString().trim());
			AssertJUnit.assertEquals("AppReferral- Verify Referral Code[Referral] - API is not working [Message is not proper]in Trial"+(i+1),Message,JsonPath.read(verifyResponse, "$.data.message").toString().trim());
		
		}
				
	}	
	
	@Test(groups = {"Smoke", "ProdSanity", "Sanity", "MiniRegression", "ExhaustiveRegression"},dataProvider = "AppReferral_DeviceInEligibleFlow_AlreadyRegisteredDevice")
	public void AppReferral_DeviceInEligibleFlow_AlreadyRegisteredDevice(String userName, String password, String referCode, String DeviceID, String InstallationID, String IMEI, String isRooted, String SecondUserName, String SecondReferralCode)
	{
		RequestGenerator CreateDeviceRequest = appReferralServiceHelper.invokeMagasinService_CreateAndUpdateDeviceData(userName, password, APINAME.CREATEDEVICEDATA, DeviceID, InstallationID, IMEI, isRooted, "createFlow","");
		String CreateDeviceResponse = CreateDeviceRequest.respvalidate.returnresponseasstring();
		System.out.println("\nREGISTER DEVICE RESPONSE FROM MAGASIN : \n");
		System.out.println(CreateDeviceResponse);
		AssertJUnit.assertEquals("Magasin - Register New Device - API is not working",200,CreateDeviceRequest.response.getStatus());
	
		RequestGenerator request = appReferralServiceHelper.invokeVerifyReferralCodeService_Magasin(userName, password, referCode, isRooted);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println("\nFIRST REFERRAL CHECK RESPONSE : \n");
		System.out.println(response);
		
		RequestGenerator FinalRequest = appReferralServiceHelper.invokeVerifyReferralCodeService_Magasin(SecondUserName, password, SecondReferralCode, isRooted);
		String FinalResponse = FinalRequest.respvalidate.returnresponseasstring();
		System.out.println("\nSECOND REFERRAL CHECK WITH DIFFERENT USER AND SAME DEVICE DATA - RESPONSE : \n");
		System.out.println(FinalResponse);
		
	}
	
	
		
}
