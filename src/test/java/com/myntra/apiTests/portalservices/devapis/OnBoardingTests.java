package com.myntra.apiTests.portalservices.devapis;

import java.util.List;

import com.myntra.apiTests.dataproviders.DevApisDataProvider;
import com.myntra.apiTests.dataproviders.devapis.OnBoardingTestsDP;
import com.myntra.apiTests.portalservices.devapiservice.IDPTestsHelper;
import com.myntra.apiTests.portalservices.devapiservice.OnBoardingTestsHelper;
import org.apache.commons.collections.CollectionUtils;
import org.junit.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import com.myntra.lordoftherings.Toolbox;
import com.myntra.lordoftherings.gandalf.RequestGenerator;

public class OnBoardingTests extends OnBoardingTestsDP
{
	static IDPTestsHelper IDPHelper = new IDPTestsHelper();
	OnBoardingTestsHelper OnBoardHelper = new OnBoardingTestsHelper();

@Test(groups = {"Sanity","Regression"}, dataProvider = "HallMark_GetOnBoardingStrategies",description = "Get Hallmark Onboarding State verify success")
public void OnBoarding_GetOnboardingState_VerifySuccess(String username, String password, String Scenario, String appStat, String deviceID) 
{
		RequestGenerator SignUpRequest = IDPHelper.emailSignUp(username, password,"OnBoardUser","M","1234567890");
		AssertJUnit.assertEquals("SignUPError", 200,SignUpRequest.response.getStatus());
		
		RequestGenerator request = OnBoardHelper.getHallmarkUserOnboardingData(username, password, Scenario, appStat, deviceID);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println("Get user onboarding State Service Response : " + response);
	
		AssertJUnit.assertEquals("Get User Onboarding State Service is Down!!", 200, request.response.getStatus());
}

@Test(groups = {"SchemaValidation"}, dataProvider = "HallMark_GetOnBoardingStrategies",description = "Get Hallmark Onboarding State verify response Data")
public void OnBoarding_GetOnboardingState_VerifyData(String username, String password, String Scenario, String appStat, String deviceID) 
{


	RequestGenerator SignUpRequest = IDPHelper.emailSignUp(username, password,"OnBoardUser","M","1234567890");
	AssertJUnit.assertEquals("SignUPError", 200,SignUpRequest.response.getStatus());
	
	String RandomDeviceID = OnBoardHelper.randomize(deviceID);
	
	RequestGenerator request = OnBoardHelper.getHallmarkUserOnboardingData(username, password, Scenario, appStat, RandomDeviceID);
	String response = request.respvalidate.returnresponseasstring();
	System.out.println("Get user onboarding State Service Response : " + response);
	
	
		try {
			String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/devapi-hallmark-getOnboardingState-SchemaSet.txt");
			List < String > missingNodeList = validateServiceResponseNodesUsingSchemaValidator(response, jsonschema);
			AssertJUnit.assertTrue(missingNodeList + " nodes are missing in Check PPID Availability API response",
					CollectionUtils.isEmpty(missingNodeList));
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	AssertJUnit.assertEquals("Get User Onboarding State Service is Down!!", 200, request.response.getStatus());

}

@Test(groups = {"Regression"}, dataProvider = "HallMark_SaveOnBoardingStrategies",description = "Save Hallmark Onboarding State verify success")
public void OnBoarding_saveOnboardingState_DependantVerifySuccess(String username, String password, String Scenario, String appStat, String deviceID)
{

	RequestGenerator SignUpRequest = IDPHelper.emailSignUp(username, password,"OnBoardUser","M","1234567890");
	AssertJUnit.assertEquals("SignUPError", 200,SignUpRequest.response.getStatus());
	
	String RandomDeviceID = OnBoardHelper.randomize(deviceID);
	
	RequestGenerator request = OnBoardHelper.getHallmarkUserOnboardingData(username, password, Scenario, appStat, RandomDeviceID);
	String response = request.respvalidate.returnresponseasstring();
	System.out.println("Get user onboarding State Service Response : " + response);
	AssertJUnit.assertEquals("Get User Onboarding State Service is Down!!", 200, request.response.getStatus());
	String Strategy="";	
	
	if(!Scenario.equals("NONE"))
	{
		Strategy = OnBoardHelper.getSingleStrategy(response, "DEPENDENT");
		System.out.println("Strategy : " + Strategy);
	}
		request = OnBoardHelper.SaveHallmarkUserOnboardingData(username, password, Scenario, appStat, RandomDeviceID, Strategy);
		response = request.respvalidate.returnresponseasstring();
		System.out.println("Save user onboarding state Response : " + response);
		AssertJUnit.assertEquals("Save User Onboarding State Service is Down!!", 200, request.response.getStatus());
	
	}

@Test(groups = {"Regression"}, dataProvider = "HallMark_SaveOnBoardingStrategies",description = "Save OnBoarding with Main Strategy")
public void OnBoarding_saveOnboardingState_MainStrategy_VerifySuccess(String username, String password, String Scenario, String appStat, String deviceID) 
{

	RequestGenerator SignUpRequest = IDPHelper.emailSignUp(username, password,"OnBoardUser","M","1234567890");
	AssertJUnit.assertEquals("SignUPError", 200,SignUpRequest.response.getStatus());
	
	String RandomDeviceID = OnBoardHelper.randomize(deviceID);
	
	RequestGenerator request = OnBoardHelper.getHallmarkUserOnboardingData(username, password, Scenario, appStat, RandomDeviceID);
	String response = request.respvalidate.returnresponseasstring();
	System.out.println("Get user onboarding State Service Response : " + response);
	AssertJUnit.assertEquals("Get User Onboarding State Service is Down!!", 200, request.response.getStatus());
	String Strategy="";	
	
	if(!Scenario.equals("NONE"))
	{
		Strategy = OnBoardHelper.getSingleStrategy(response, "MAIN");
		System.out.println("Strategy : " + Strategy);
	}
		request = OnBoardHelper.SaveHallmarkUserOnboardingData(username, password, Scenario, appStat, RandomDeviceID, Strategy);
		response = request.respvalidate.returnresponseasstring();
		System.out.println("Save user onboarding state Response : " + response);
		AssertJUnit.assertEquals("Save User Onboarding State Service is Down!!", 200, request.response.getStatus());
}


@Test(groups = {"Regression"}, dataProvider = "HallMark_SaveOnBoardingStrategies",description = "Save OnBoarding with Multiple Dependent Strategies")
public void OnBoarding_saveOnboardingState_Multiple_DependantVerifySuccess(String username, String password, String Scenario, String appStat, String deviceID) {
	RequestGenerator SignUpRequest = IDPHelper.emailSignUp(username, password,"OnBoardUser","M","1234567890");
	AssertJUnit.assertEquals("SignUPError", 200,SignUpRequest.response.getStatus());
	
	String RandomDeviceID = OnBoardHelper.randomize(deviceID);
	
	RequestGenerator request = OnBoardHelper.getHallmarkUserOnboardingData(username, password, Scenario, appStat, RandomDeviceID);
	String response = request.respvalidate.returnresponseasstring();
	System.out.println("Get user onboarding State Service Response : " + response);
	AssertJUnit.assertEquals("Get User Onboarding State Service is Down!!", 200, request.response.getStatus());
	String Strategies="";
	if(!Scenario.equals("NONE"))
	{
		Strategies = OnBoardHelper.getMultipleStrategies(response, "DEPENDENT", 5);
		System.out.println("Strategy : " + Strategies);
	}

	request = OnBoardHelper.SaveHallmarkUserOnboardingData(username, password, Scenario, appStat, RandomDeviceID, Strategies);
	response = request.respvalidate.returnresponseasstring();
	System.out.println("Save user onboarding state Response : " + response);
	AssertJUnit.assertEquals("Save User Onboarding State Service is Down!!", 200, request.response.getStatus());
}

@Test(groups = {"Regression"}, dataProvider = "HallMark_SaveOnBoardingStrategies",description = "Save OnBoarding with Multiple MIn Strategies")
public void OnBoarding_saveOnboardingState_Multiple_MainStrategiesVerifySuccess(String username, String password, String Scenario, String appStat, String deviceID) {
	RequestGenerator SignUpRequest = IDPHelper.emailSignUp(username, password,"OnBoardUser","M","1234567890");
	AssertJUnit.assertEquals("SignUPError", 200,SignUpRequest.response.getStatus());
	
	String RandomDeviceID = OnBoardHelper.randomize(deviceID);
	
	RequestGenerator request = OnBoardHelper.getHallmarkUserOnboardingData(username, password, Scenario, appStat, RandomDeviceID);
	String response = request.respvalidate.returnresponseasstring();
	System.out.println("Get user onboarding State Service Response : " + response);
	AssertJUnit.assertEquals("Get User Onboarding State Service is Down!!", 200, request.response.getStatus());
	String Strategies="";
	if(!Scenario.equals("NONE"))
	{
		Strategies = OnBoardHelper.getMultipleStrategies(response, "MAIN", 5);
		System.out.println("Strategy : " + Strategies);
	}

	request = OnBoardHelper.SaveHallmarkUserOnboardingData(username, password, Scenario, appStat, RandomDeviceID, Strategies);
	response = request.respvalidate.returnresponseasstring();
	System.out.println("Save user onboarding state Response : " + response);
	AssertJUnit.assertEquals("Save User Onboarding State Service is Down!!", 200, request.response.getStatus());
}

//Hallmark Data Validations
//Dependent Strategy Flows
@Test(groups = {"Regression"}, dataProvider = "HallMark_SaveOnBoardingStrategies",description = "Save OnBoarding with Dependant Strategies Flow")
public void OnBoarding_saveOnboardingState_Dependant_Flow1(String username, String password, String Scenario, String appStat, String deviceID) 
{

	RequestGenerator SignUpRequest = IDPHelper.emailSignUp(username, password,"OnBoardUser","M","1234567890");
	AssertJUnit.assertEquals("SignUPError", 200,SignUpRequest.response.getStatus());
	
	String RandomDeviceID = OnBoardHelper.randomize(deviceID);
	
	RequestGenerator request = OnBoardHelper.getHallmarkUserOnboardingData(username, password, Scenario, appStat, RandomDeviceID);
	String response = request.respvalidate.returnresponseasstring();
	System.out.println("Get user onboarding State Service Response : " + response);
	AssertJUnit.assertEquals("Get User Onboarding State Service is Down!!", 200, request.response.getStatus());
	String Strategy="";	
	
	if(!Scenario.equals("NONE"))
	{
		Strategy = OnBoardHelper.getSingleStrategy(response, "MAIN");
		System.out.println("Strategy : " + Strategy);
	}
		request = OnBoardHelper.SaveHallmarkUserOnboardingData(username, password, Scenario, appStat, RandomDeviceID, Strategy);
		response = request.respvalidate.returnresponseasstring();
		System.out.println("Save user onboarding state Response : " + response);
		AssertJUnit.assertEquals("Save User Onboarding State Service is Down!!", 200, request.response.getStatus());

	request = OnBoardHelper.getHallmarkUserOnboardingData(username, password, Scenario, appStat, RandomDeviceID);
	response = request.respvalidate.returnresponseasstring();
	System.out.println("Get user onboarding State Service Response : " + response);
	AssertJUnit.assertEquals("Get User Onboarding State Service is Down!!", 200, request.response.getStatus());


}

@Test(groups = {"Regression"}, dataProvider = "HallMark_SaveOnBoardingStrategies",description = "Save onBoarding with Dependent strategies. Same user with different device")
public void OnBoarding_saveOnboardingState_Dependant_Flow2_SameUser_DifferentDevice(String username, String password, String Scenario, String appStat, String deviceID) 
{

	RequestGenerator SignUpRequest = IDPHelper.emailSignUp(username, password,"OnBoardUser","M","1234567890");
	AssertJUnit.assertEquals("SignUPError", 200,SignUpRequest.response.getStatus());
	
	String RandomDeviceID = OnBoardHelper.randomize(deviceID);
	
	RequestGenerator request = OnBoardHelper.getHallmarkUserOnboardingData(username, password, Scenario, appStat, RandomDeviceID);
	String response = request.respvalidate.returnresponseasstring();
	System.out.println("Get user onboarding State Service Response : " + response);
	AssertJUnit.assertEquals("Get User Onboarding State Service is Down!!", 200, request.response.getStatus());
	String Strategy="";	

	if(!Scenario.equals("NONE"))
	{
		Strategy = OnBoardHelper.getSingleStrategy(response, "DEPENDENT");
		System.out.println("Strategy : " + Strategy);
	}
	
	request = OnBoardHelper.SaveHallmarkUserOnboardingData(username, password, "XID", appStat, RandomDeviceID, Strategy);
	response = request.respvalidate.returnresponseasstring();
	System.out.println("Save user onboarding state Response : " + response);
	AssertJUnit.assertEquals("Save User Onboarding State Service is Down!!", 200, request.response.getStatus());

	request = OnBoardHelper.getHallmarkUserOnboardingData(username, password, "XID", appStat, RandomDeviceID);
	response = request.respvalidate.returnresponseasstring();
	System.out.println("Get user onboarding State Service Response : " + response);
	AssertJUnit.assertEquals("Get User Onboarding State Service is Down!!", 200, request.response.getStatus());
	AssertJUnit.assertEquals("Dependent Strategy is Not Removed in Dependencies", true, OnBoardHelper.isStrategyRemoved(response, "DEPENDENT", Strategy));
	AssertJUnit.assertEquals("Dependent Strategy is Not Removed in Main", true, OnBoardHelper.isStrategyRemoved(response, "MAIN", Strategy));

	RandomDeviceID = OnBoardHelper.randomize(deviceID);
	request = OnBoardHelper.getHallmarkUserOnboardingData(username, password, "XID", appStat, RandomDeviceID);
	response = request.respvalidate.returnresponseasstring();
	System.out.println("Get user onboarding State Service Response : " + response);
	AssertJUnit.assertEquals("Get User Onboarding State Service is Down!!", 200, request.response.getStatus());
	AssertJUnit.assertEquals("Dependent Strategy is Not Removed in Dependencies - For Different Device", true, OnBoardHelper.isStrategyRemoved(response, "DEPENDENT", Strategy));
	AssertJUnit.assertEquals("Dependent Strategy is Not Removed in Main - For Different Device", true, OnBoardHelper.isStrategyRemoved(response, "MAIN", Strategy));


}

@Test(groups = {"Regression"}, dataProvider = "HallMark_SaveOnBoardingStrategies",description = "Save onBoarding State Flow - Same Device different Users")
public void OnBoarding_saveOnboardingState_Dependant_Flow3_SameDevice_DifferentUser(String username, String password, String Scenario, String appStat, String deviceID) 
{
	RequestGenerator SignUpRequest = IDPHelper.emailSignUp(username, password,"OnBoardUser","M","1234567890");
	AssertJUnit.assertEquals("SignUPError", 200,SignUpRequest.response.getStatus());
	if(!(SignUpRequest.response.getStatus()==200))
	{
		Assert.fail("Signup Error!");
	}
	else
	{
		String RandomDeviceID = OnBoardHelper.randomize(deviceID);
		
		RequestGenerator request = OnBoardHelper.getHallmarkUserOnboardingData(username, password, "DEVICEID", appStat, RandomDeviceID);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println("Get user onboarding State Service Response : " + response);
		AssertJUnit.assertEquals("Get User Onboarding State Service is Down!!", 200, request.response.getStatus());

		String DependantStrategy = OnBoardHelper.getSingleStrategy(response, "DEPENDENT");
		System.out.println("Strategy : " + DependantStrategy);
		request = OnBoardHelper.SaveHallmarkUserOnboardingData(username, password, "DEVICEID", appStat, RandomDeviceID, DependantStrategy);
		response = request.respvalidate.returnresponseasstring();
		System.out.println("Save user onboarding state Response : " + response);
		AssertJUnit.assertEquals("Save User Onboarding State Service is Down!!", 200, request.response.getStatus());

		request = OnBoardHelper.getHallmarkUserOnboardingData(username, password, "DEVICEID", appStat, RandomDeviceID);
		response = request.respvalidate.returnresponseasstring();
		System.out.println("Get user onboarding State Service Response : " + response);
		AssertJUnit.assertEquals("Get User Onboarding State Service is Down!!", 200, request.response.getStatus());
		AssertJUnit.assertEquals("Dependent Strategy is Not Removed in Dependencies", true, OnBoardHelper.isStrategyRemoved(response, "DEPENDENT", DependantStrategy));
		AssertJUnit.assertEquals("Dependent Strategy is Not Removed in Main", true, OnBoardHelper.isStrategyRemoved(response, "MAIN", DependantStrategy));

		DevApisDataProvider obj = new DevApisDataProvider();

		String newUser = (obj.getRandomEmailId(15));
		SignUpRequest = IDPHelper.emailSignUp(newUser, password,"OnBoardUser","M","1234567890");
		request = OnBoardHelper.getHallmarkUserOnboardingData(newUser, password, "BOTH", appStat, RandomDeviceID);
		response = request.respvalidate.returnresponseasstring();
		System.out.println("Get user onboarding State Service Response : " + response);
		AssertJUnit.assertEquals("Get User Onboarding State Service is Down!!", 200, request.response.getStatus());
		AssertJUnit.assertEquals("Dependent Strategy is Removed in Dependencies - For Different User", false, OnBoardHelper.isStrategyRemoved(response, "DEPENDENT", DependantStrategy));
		AssertJUnit.assertEquals("Dependent Strategy is Removed in Main - For Different User", false, OnBoardHelper.isStrategyRemoved(response, "MAIN", DependantStrategy));

	}
	

}

@Test(groups = {"Regression"}, dataProvider = "HallMark_SaveOnBoardingStrategies",description = "Save onBoarding state - Same user With Same device ")
public void OnBoarding_saveOnboardingState_Main_Flow1_SameUser_SameDevice(String username, String password, String Scenario, String appStat, String deviceID) 
{
	RequestGenerator SignUpRequest = IDPHelper.emailSignUp(username, password,"OnBoardUser","M","1234567890");
	AssertJUnit.assertEquals("SignUPError", 200,SignUpRequest.response.getStatus());
	if(!(SignUpRequest.response.getStatus()==200))
	{
		Assert.fail("Signup Error!");
	}
	else
	{
		String RandomDeviceID = OnBoardHelper.randomize(deviceID);
		
		RequestGenerator request = OnBoardHelper.getHallmarkUserOnboardingData(username, password, "XID", appStat, RandomDeviceID);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println("Get user onboarding State Service Response : " + response);
		AssertJUnit.assertEquals("Get User Onboarding State Service is Down!!", 200, request.response.getStatus());

		String DependantStrategy = OnBoardHelper.getSingleStrategy(response, "MAIN");
		System.out.println("Strategy : " + DependantStrategy);
		request = OnBoardHelper.SaveHallmarkUserOnboardingData(username, password, "XID", appStat, RandomDeviceID, DependantStrategy);
		response = request.respvalidate.returnresponseasstring();
		System.out.println("Save user onboarding state Response : " + response);
		AssertJUnit.assertEquals("Save User Onboarding State Service is Down!!", 200, request.response.getStatus());

		request = OnBoardHelper.getHallmarkUserOnboardingData(username, password, "BOTH", appStat, RandomDeviceID);
		response = request.respvalidate.returnresponseasstring();
		System.out.println("Get user onboarding State Service Response : " + response);
		AssertJUnit.assertEquals("Get User Onboarding State Service is Down!!", 200, request.response.getStatus());
		AssertJUnit.assertEquals("Dependent Strategy is Removed in Dependencies - For Different User", true, OnBoardHelper.isStrategyRemoved(response, "DEPENDENT", DependantStrategy));
		AssertJUnit.assertEquals("Dependent Strategy is Removed in Main - For Different User", true, OnBoardHelper.isStrategyRemoved(response, "MAIN", DependantStrategy));

}
}

@Test(groups = {"Regression"}, dataProvider = "HallMark_SaveOnBoardingStrategies",description = "Save OnBoarding data with Main Strategies - Same user in Different Device")
public void OnBoarding_saveOnboardingState_Main_Flow2_SameUser_DifferentDevice(String username, String password, String Scenario, String appStat, String deviceID) 
{
	RequestGenerator SignUpRequest = IDPHelper.emailSignUp(username, password,"OnBoardUser","M","1234567890");
	AssertJUnit.assertEquals("SignUPError", 200,SignUpRequest.response.getStatus());
	
	String RandomDeviceID = OnBoardHelper.randomize(deviceID);
	
	RequestGenerator request = OnBoardHelper.getHallmarkUserOnboardingData(username, password, Scenario, appStat, RandomDeviceID);
	String response = request.respvalidate.returnresponseasstring();
	System.out.println("Get user onboarding State Service Response : " + response);
	AssertJUnit.assertEquals("Get User Onboarding State Service is Down!!", 200, request.response.getStatus());
	String Strategy="";	
	
	if(!Scenario.equals("NONE"))
	{
		Strategy = OnBoardHelper.getSingleStrategy(response, "MAIN");
		System.out.println("Strategy : " + Strategy);
	}
	
	request = OnBoardHelper.SaveHallmarkUserOnboardingData(username, password, "XID", appStat, RandomDeviceID, Strategy);
	response = request.respvalidate.returnresponseasstring();
	System.out.println("Save user onboarding state Response : " + response);
	AssertJUnit.assertEquals("Save User Onboarding State Service is Down!!", 200, request.response.getStatus());

	request = OnBoardHelper.getHallmarkUserOnboardingData(username, password, "XID", appStat, RandomDeviceID);
	response = request.respvalidate.returnresponseasstring();
	System.out.println("Get user onboarding State Service Response : " + response);
	AssertJUnit.assertEquals("Get User Onboarding State Service is Down!!", 200, request.response.getStatus());
	AssertJUnit.assertEquals("Dependent Strategy is Not Removed in Dependencies", true, OnBoardHelper.isStrategyRemoved(response, "DEPENDENT", Strategy));
	AssertJUnit.assertEquals("Dependent Strategy is Not Removed in Main", true, OnBoardHelper.isStrategyRemoved(response, "MAIN", Strategy));

	RandomDeviceID = OnBoardHelper.randomize(deviceID);
	request = OnBoardHelper.getHallmarkUserOnboardingData(username, password, "XID", appStat, RandomDeviceID);
	response = request.respvalidate.returnresponseasstring();
	System.out.println("Get user onboarding State Service Response : " + response);
	AssertJUnit.assertEquals("Get User Onboarding State Service is Down!!", 200, request.response.getStatus());
	AssertJUnit.assertEquals("Dependent Strategy is Not Removed in Dependencies - For Different Device", true, OnBoardHelper.isStrategyRemoved(response, "DEPENDENT", Strategy));
	AssertJUnit.assertEquals("Dependent Strategy is Not Removed in Main - For Different Device", true, OnBoardHelper.isStrategyRemoved(response, "MAIN", Strategy));

}

@Test(groups = {"Regression"}, dataProvider = "HallMark_SaveOnBoardingStrategies_Flows",description = "Save OnBoarding State with Dependant Strategies - Check XID takes priority")
public void OnBoarding_saveOnboardingState_Dependent_Validate_XID_Priority(String username, String password, String Scenario, String appStat, String deviceID) 
{
	RequestGenerator SignUpRequest = IDPHelper.emailSignUp(username, password,"OnBoardUser","M","1234567890");
	AssertJUnit.assertEquals("SignUPError", 200,SignUpRequest.response.getStatus());
	
	String RandomDeviceID = OnBoardHelper.randomize(deviceID);
	
	RequestGenerator request = OnBoardHelper.getHallmarkUserOnboardingData(username, password, Scenario, appStat, RandomDeviceID);
	String response = request.respvalidate.returnresponseasstring();
	System.out.println("Get user onboarding State Service Response : " + response);
	AssertJUnit.assertEquals("Get User Onboarding State Service is Down!!", 200, request.response.getStatus());
	String Strategy="";	
	
	if(!Scenario.equals("NONE"))
	{
		Strategy = OnBoardHelper.getSingleStrategy(response, "DEPENDENT");
		System.out.println("Strategy : " + Strategy);
	}
	

	Strategy = OnBoardHelper.getSingleStrategy(response, "DEPENDENT");
	System.out.println("Strategy : " + Strategy);
	request = OnBoardHelper.SaveHallmarkUserOnboardingData(username, password, "DEVICEID", appStat, RandomDeviceID, Strategy);
	response = request.respvalidate.returnresponseasstring();
	System.out.println("Save user onboarding state Response : " + response);
	AssertJUnit.assertEquals("Save User Onboarding State Service is Down!!", 200, request.response.getStatus());

	request = OnBoardHelper.getHallmarkUserOnboardingData(username, password, "XID", appStat, RandomDeviceID);
	response = request.respvalidate.returnresponseasstring();
	System.out.println("Get user onboarding State Service Response : " + response);
	AssertJUnit.assertEquals("Get User Onboarding State Service is Down!!", 200, request.response.getStatus());
	AssertJUnit.assertEquals("Dependent Strategy is  Removed in Dependencies For Different User", false, OnBoardHelper.isStrategyRemoved(response, "DEPENDENT", Strategy));
	AssertJUnit.assertEquals("Dependent Strategy is  Removed in Main For Different User", false, OnBoardHelper.isStrategyRemoved(response, "DEPENDENT", Strategy));

	String Strategy2 = "";
	if(!Scenario.equals("NONE"))
	{
		do {
			Strategy2 = OnBoardHelper.getSingleStrategy(response, "MAIN");
		} while (Strategy2.equals(Strategy));
	}
	
	System.out.println("XID Strategy : " + Strategy2);
	request = OnBoardHelper.SaveHallmarkUserOnboardingData(username, password, "XID", appStat, RandomDeviceID, Strategy2);
	response = request.respvalidate.returnresponseasstring();
	System.out.println("Save user onboarding state Response : " + response);
	AssertJUnit.assertEquals("Save User Onboarding State Service is Down!!", 200, request.response.getStatus());
	

	request = OnBoardHelper.getHallmarkUserOnboardingData(username, password, "BOTH", appStat, RandomDeviceID);
	response = request.respvalidate.returnresponseasstring();
	System.out.println("Get user onboarding State Service Response : " + response);
	AssertJUnit.assertEquals("Get User Onboarding State Service is Down!!", 200, request.response.getStatus());
	AssertJUnit.assertEquals("Dependent Strategy is  Removed in Dependencies For Different User", false, OnBoardHelper.isStrategyRemoved(response, "DEPENDENT", Strategy));
	AssertJUnit.assertEquals("Dependent Strategy is  Removed in Main For Different User", false, OnBoardHelper.isStrategyRemoved(response, "MAIN", Strategy));
	AssertJUnit.assertEquals("Dependent Strategy is  Removed in Dependencies For Different User", true, OnBoardHelper.isStrategyRemoved(response, "DEPENDENT", Strategy2));
	AssertJUnit.assertEquals("Dependent Strategy is  Removed in Main For Different User", true, OnBoardHelper.isStrategyRemoved(response, "MAIN", Strategy2));


}

@Test(groups = {"Regression"}, dataProvider = "HallMark_SaveOnBoardingStrategies_UserDependent_Flows",description = "Save onBoarding user data with multiple depenedant strategies - Same user with same device")
public void DevAPI_saveOnboardingState_Multiple_Dependant_Flow1_SameUser_SameDevice(String username, String password, String Scenario, String appStat, String deviceID) {

	RequestGenerator SignUpRequest = IDPHelper.emailSignUp(username, password,"OnBoardUser","M","1234567890");
	AssertJUnit.assertEquals("SignUPError", 200,SignUpRequest.response.getStatus());
	
	String RandomDeviceID = OnBoardHelper.randomize(deviceID);
	
	RequestGenerator request = OnBoardHelper.getHallmarkUserOnboardingData(username, password, Scenario, appStat, RandomDeviceID);
	String response = request.respvalidate.returnresponseasstring();
	System.out.println("Get user onboarding State Service Response : " + response);
	AssertJUnit.assertEquals("Get User Onboarding State Service is Down!!", 200, request.response.getStatus());

	String Strategy = OnBoardHelper.getMultipleStrategies(response, "DEPENDENT", 3);
	request = OnBoardHelper.SaveHallmarkUserOnboardingData(username, password, "BOTH", appStat, RandomDeviceID, Strategy);
	response = request.respvalidate.returnresponseasstring();
	System.out.println("Save user onboarding state Response : " + response);
	AssertJUnit.assertEquals("Save User Onboarding State Service is Down!!", 200, request.response.getStatus());

	request = OnBoardHelper.getHallmarkUserOnboardingData(username, password, "BOTH", appStat, RandomDeviceID);
	response = request.respvalidate.returnresponseasstring();
	System.out.println("Get user onboarding State Service Response : " + response);
	AssertJUnit.assertEquals("Get User Onboarding State Service is Down!!", 200, request.response.getStatus());

	boolean status = true;
	String Strategies[] = Strategy.split("\",\"");
	for (int i = 0; i < Strategies.length; i++) {
		status = status && OnBoardHelper.isStrategyRemoved(response, "DEPENDENT", Strategies[i]);
		status = status && OnBoardHelper.isStrategyRemoved(response, "MAIN", Strategies[i]);
	}


	AssertJUnit.assertEquals("Dependent Strategy is Not Removed in Dependencies", true, status);
	AssertJUnit.assertEquals("Dependent Strategy is Not Removed in Main", true, status);

}

@Test(groups = {"Regression"}, dataProvider = "HallMark_SaveOnBoardingStrategies_UserDependent_Flows",description = "Save onBoarding data with Multiple Dependant Strategies - Same user Different Device")
public void OnBoarding_saveOnboardingState_Multiple_Dependant_Flow2_SameUser_DifferentDevice(String username, String password, String Scenario, String appStat, String deviceID) 
{


	RequestGenerator SignUpRequest = IDPHelper.emailSignUp(username, password,"OnBoardUser","M","1234567890");
	AssertJUnit.assertEquals("SignUPError", 200,SignUpRequest.response.getStatus());
	
	String RandomDeviceID = OnBoardHelper.randomize(deviceID);
	
	RequestGenerator request = OnBoardHelper.getHallmarkUserOnboardingData(username, password, Scenario, appStat, RandomDeviceID);
	String response = request.respvalidate.returnresponseasstring();
	System.out.println("Get user onboarding State Service Response : " + response);
	AssertJUnit.assertEquals("Get User Onboarding State Service is Down!!", 200, request.response.getStatus());


	String DependantStrategy = OnBoardHelper.getMultipleStrategies(response, "DEPENDENT", 4);
	System.out.println("Strategy : " + DependantStrategy);
	request = OnBoardHelper.SaveHallmarkUserOnboardingData(username, password, Scenario, appStat, RandomDeviceID, DependantStrategy);
	response = request.respvalidate.returnresponseasstring();
	System.out.println("Save user onboarding state Response : " + response);
	AssertJUnit.assertEquals("Save User Onboarding State Service is Down!!", 200, request.response.getStatus());

	request = OnBoardHelper.getHallmarkUserOnboardingData(username, password, Scenario, appStat, RandomDeviceID);
	response = request.respvalidate.returnresponseasstring();
	System.out.println("Get user onboarding State Service Response : " + response);
	AssertJUnit.assertEquals("Get User Onboarding State Service is Down!!", 200, request.response.getStatus());
	boolean status = true;
	String Strategies[] = DependantStrategy.split("\",\"");
	for (int i = 0; i < Strategies.length; i++) {
		status = status && OnBoardHelper.isStrategyRemoved(response, "DEPENDENT", Strategies[i]);
		status = status && OnBoardHelper.isStrategyRemoved(response, "MAIN", Strategies[i]);
	}
	AssertJUnit.assertEquals("Dependent Strategy is Not Removed in Dependencies", true, status);
	AssertJUnit.assertEquals("Dependent Strategy is Not Removed in Main", true, status);

	RandomDeviceID = OnBoardHelper.randomize(deviceID);
	request = OnBoardHelper.getHallmarkUserOnboardingData(username, password, Scenario, appStat, RandomDeviceID);
	response = request.respvalidate.returnresponseasstring();
	System.out.println("Get user onboarding State Service Response : " + response);
	AssertJUnit.assertEquals("Get User Onboarding State Service is Down!!", 200, request.response.getStatus());

	status = true;
	for (int i = 0; i < Strategies.length; i++) {
		status = status && OnBoardHelper.isStrategyRemoved(response, "DEPENDENT", Strategies[i]);
		status = status && OnBoardHelper.isStrategyRemoved(response, "MAIN", Strategies[i]);
	}
	AssertJUnit.assertEquals("Dependent Strategy is Not Removed in Dependencies", true, status);
	AssertJUnit.assertEquals("Dependent Strategy is Not Removed in Main", true, status);

}

@Test(groups = {"Regression"}, dataProvider = "HallMark_SaveOnBoardingStrategies_UserAndDeviceDependent_Flows",description = "Save onBoarding data with Multiple Dependant Strategies - Same Device Different users")
public void OnBoarding_saveOnboardingState_Multiple_Dependant_Flow3_SameDevice_DifferentUser(String username, String password, String Scenario, String appStat, String deviceID) 
{
	RequestGenerator SignUpRequest = IDPHelper.emailSignUp(username, password,"OnBoardUser","M","1234567890");
	AssertJUnit.assertEquals("SignUPError", 200,SignUpRequest.response.getStatus());
	
	String RandomDeviceID = OnBoardHelper.randomize(deviceID);
	
	RequestGenerator request = OnBoardHelper.getHallmarkUserOnboardingData(username, password, Scenario, appStat, RandomDeviceID);
	String response = request.respvalidate.returnresponseasstring();
	System.out.println("Get user onboarding State Service Response : " + response);
	AssertJUnit.assertEquals("Get User Onboarding State Service is Down!!", 200, request.response.getStatus());

	String DependantStrategy = OnBoardHelper.getMultipleStrategies(response, "DEPENDENT", 5);
	System.out.println("Strategy : " + DependantStrategy);
	request = OnBoardHelper.SaveHallmarkUserOnboardingData(username, password, Scenario, appStat, RandomDeviceID, DependantStrategy);
	response = request.respvalidate.returnresponseasstring();
	System.out.println("Save user onboarding state Response : " + response);
	AssertJUnit.assertEquals("Save User Onboarding State Service is Down!!", 200, request.response.getStatus());

	request = OnBoardHelper.getHallmarkUserOnboardingData(username, password, Scenario, appStat, RandomDeviceID);
	response = request.respvalidate.returnresponseasstring();
	System.out.println("Get user onboarding State Service Response : " + response);
	AssertJUnit.assertEquals("Get User Onboarding State Service is Down!!", 200, request.response.getStatus());
	boolean status = true;
	String Strategies[] = DependantStrategy.split("\",\"");
	for (int i = 0; i < Strategies.length; i++) {
		status = status && OnBoardHelper.isStrategyRemoved(response, "DEPENDENT", Strategies[i]);
		status = status && OnBoardHelper.isStrategyRemoved(response, "MAIN", Strategies[i]);
	}
	AssertJUnit.assertEquals("Dependent Strategy is Not Removed in Dependencies", true, status);
	AssertJUnit.assertEquals("Dependent Strategy is Not Removed in Main", true, status);

	DevApisDataProvider obj = new DevApisDataProvider();

	String newUser = (obj.getRandomEmailId(15));
	SignUpRequest = IDPHelper.emailSignUp(newUser, password,"OnBoardUser","M","1234567890");
	
	request = OnBoardHelper.getHallmarkUserOnboardingData(newUser, password, Scenario, appStat, RandomDeviceID);
	response = request.respvalidate.returnresponseasstring();
	System.out.println("Get user onboarding State Service Response : " + response);
	AssertJUnit.assertEquals("Get User Onboarding State Service is Down!!", 200, request.response.getStatus());
	status = true;
	for (int i = 0; i < Strategies.length; i++) {
		status = status && OnBoardHelper.isStrategyRemoved(response, "DEPENDENT", Strategies[i]);
		status = status && OnBoardHelper.isStrategyRemoved(response, "MAIN", Strategies[i]);
	}
	AssertJUnit.assertEquals("Dependent Strategy is  Removed in Dependencies", false, status);
	AssertJUnit.assertEquals("Dependent Strategy is  Removed in Main", false, status);

}

@Test(groups = {"Regression"}, dataProvider = "HallMark_SaveOnBoardingStrategies_UserAndDeviceDependent_Flows",description = "Save onBoarding data with Multiple Main Strategies - Same user Same Device")
public void OnBoarding_saveOnboardingState_Multiple_Main_Flow1_SameUser_SameDevice(String username, String password, String Scenario, String appStat, String deviceID) 

{
	RequestGenerator SignUpRequest = IDPHelper.emailSignUp(username, password,"OnBoardUser","M","1234567890");
	AssertJUnit.assertEquals("SignUPError", 200,SignUpRequest.response.getStatus());
	
	String RandomDeviceID = OnBoardHelper.randomize(deviceID);
	
	//Overriding Scenario Type to match the test case
	RequestGenerator request = OnBoardHelper.getHallmarkUserOnboardingData(username, password, Scenario, appStat, RandomDeviceID);
	String response = request.respvalidate.returnresponseasstring();
	System.out.println("Get user onboarding State Service Response : " + response);
	AssertJUnit.assertEquals("Get User Onboarding State Service is Down!!", 200, request.response.getStatus());

	String DependantStrategy = OnBoardHelper.getMultipleStrategies(response, "MAIN", 3);
	request = OnBoardHelper.SaveHallmarkUserOnboardingData(username, password, Scenario, appStat, RandomDeviceID, DependantStrategy);
	response = request.respvalidate.returnresponseasstring();
	System.out.println("Save user onboarding state Response : " + response);
	AssertJUnit.assertEquals("Save User Onboarding State Service is Down!!", 200, request.response.getStatus());

	request = OnBoardHelper.getHallmarkUserOnboardingData(username, password,Scenario, appStat, RandomDeviceID);
	response = request.respvalidate.returnresponseasstring();
	System.out.println("Get user onboarding State Service Response : " + response);
	AssertJUnit.assertEquals("Get User Onboarding State Service is Down!!", 200, request.response.getStatus());

	boolean status = true;
	String Strategies[] = DependantStrategy.split("\",\"");
	for (int i = 0; i < Strategies.length; i++) {
		status = status && OnBoardHelper.isStrategyRemoved(response, "DEPENDENT", Strategies[i]);
		status = status && OnBoardHelper.isStrategyRemoved(response, "MAIN", Strategies[i]);
	}


	AssertJUnit.assertEquals("Dependent Strategy is Not Removed in Dependencies", true, status);
	AssertJUnit.assertEquals("Dependent Strategy is Not Removed in Main", true, status);

}

@Test(groups = {"Regression"}, dataProvider = "HallMark_SaveOnBoardingStrategies_UserDependent_Flows",description = "Save onBoarding data with Multiple Main Strategies - Same user Different Device")
public void OnBoarding_saveOnboardingState_Multiple_MAIN_Flow2_SameUser_DifferentDevice(String username, String password, String Scenario, String appStat, String deviceID) 
{
	RequestGenerator SignUpRequest = IDPHelper.emailSignUp(username, password,"OnBoardUser","M","1234567890");
	AssertJUnit.assertEquals("SignUPError", 200,SignUpRequest.response.getStatus());
	
	String RandomDeviceID = OnBoardHelper.randomize(deviceID);
		//Overriding Scenario Type to match the test case
	RequestGenerator request = OnBoardHelper.getHallmarkUserOnboardingData(username, password, Scenario, appStat, RandomDeviceID);
	String response = request.respvalidate.returnresponseasstring();
	System.out.println("Get user onboarding State Service Response : " + response);
	int reqStatus = 200;
	AssertJUnit.assertEquals("Get User Onboarding State Service is Down!!", reqStatus, request.response.getStatus());

	String DependantStrategy = OnBoardHelper.getMultipleStrategies(response, "MAIN", 4);
	System.out.println("Strategy : " + DependantStrategy);
	request = OnBoardHelper.SaveHallmarkUserOnboardingData(username, password, Scenario, appStat, RandomDeviceID, DependantStrategy);
	response = request.respvalidate.returnresponseasstring();
	System.out.println("Save user onboarding state Response : " + response);
	AssertJUnit.assertEquals("Save User Onboarding State Service is Down!!", 200, request.response.getStatus());

	request = OnBoardHelper.getHallmarkUserOnboardingData(username, password, Scenario, appStat, RandomDeviceID);
	response = request.respvalidate.returnresponseasstring();
	System.out.println("Get user onboarding State Service Response : " + response);
	AssertJUnit.assertEquals("Get User Onboarding State Service is Down!!", reqStatus, request.response.getStatus());
	boolean status = true;
	String Strategies[] = DependantStrategy.split("\",\"");
	for (int i = 0; i < Strategies.length; i++) {
		status = status && OnBoardHelper.isStrategyRemoved(response, "DEPENDENT", Strategies[i]);
		status = status && OnBoardHelper.isStrategyRemoved(response, "MAIN", Strategies[i]);
	}
	AssertJUnit.assertEquals("Dependent Strategy is Not Removed in Dependencies", true, status);
	AssertJUnit.assertEquals("Dependent Strategy is Not Removed in Main", true, status);

	RandomDeviceID = OnBoardHelper.randomize(deviceID);
	request = OnBoardHelper.getHallmarkUserOnboardingData(username, password, Scenario, appStat, RandomDeviceID);
	response = request.respvalidate.returnresponseasstring();
	System.out.println("Get user onboarding State Service Response : " + response);
	AssertJUnit.assertEquals("Get User Onboarding State Service is Down!!", reqStatus, request.response.getStatus());

	status = true;
	for (int i = 0; i < Strategies.length; i++) {
		status = status && OnBoardHelper.isStrategyRemoved(response, "DEPENDENT", Strategies[i]);
		status = status && OnBoardHelper.isStrategyRemoved(response, "MAIN", Strategies[i]);
	}
	AssertJUnit.assertEquals("Dependent Strategy is Not Removed in Dependencies", true, status);
	AssertJUnit.assertEquals("Dependent Strategy is Not Removed in Main", true, status);


}

@Test(groups = {"Regression"}, dataProvider = "HallMark_SaveOnBoardingStrategies_UserAndDeviceDependent_Flows",description = "Save onBoarding data with Multiple Main Strategies - Same Device Different user")
public void OnBoarding_saveOnboardingState_Main_Dependant_Flow3_SameDevice_DifferentUser(String username, String password, String Scenario, String appStat, String deviceID) {
	RequestGenerator SignUpRequest = IDPHelper.emailSignUp(username, password,"OnBoardUser","M","1234567890");
	AssertJUnit.assertEquals("SignUPError", 200,SignUpRequest.response.getStatus());
	
	String RandomDeviceID = OnBoardHelper.randomize(deviceID);
		//Overriding Scenario Type to match the test case
	RequestGenerator request = OnBoardHelper.getHallmarkUserOnboardingData(username, password, "BOTH", appStat, RandomDeviceID);
	String response = request.respvalidate.returnresponseasstring();
	System.out.println("Get user onboarding State Service Response : " + response);
	int reqStatus = 200;
	AssertJUnit.assertEquals("Get User Onboarding State Service is Down!!", reqStatus, request.response.getStatus());

	String DependantStrategy = OnBoardHelper.getMultipleStrategies(response, "MAIN", 5);
	System.out.println("Strategy : " + DependantStrategy);
	request = OnBoardHelper.SaveHallmarkUserOnboardingData(username, password, "BOTH", appStat, RandomDeviceID, DependantStrategy);
	response = request.respvalidate.returnresponseasstring();
	System.out.println("Save user onboarding state Response : " + response);
	AssertJUnit.assertEquals("Save User Onboarding State Service is Down!!", 200, request.response.getStatus());

	request = OnBoardHelper.getHallmarkUserOnboardingData(username, password, "BOTH", appStat, RandomDeviceID);
	response = request.respvalidate.returnresponseasstring();
	System.out.println("Get user onboarding State Service Response : " + response);
	AssertJUnit.assertEquals("Get User Onboarding State Service is Down!!", reqStatus, request.response.getStatus());
	boolean status = true;
	String Strategies[] = DependantStrategy.split("\",\"");
	for (int i = 0; i < Strategies.length; i++) {
		status = status && OnBoardHelper.isStrategyRemoved(response, "DEPENDENT", Strategies[i]);
		status = status && OnBoardHelper.isStrategyRemoved(response, "MAIN", Strategies[i]);
	}
	AssertJUnit.assertEquals("Dependent Strategy is Not Removed in Dependencies", true, status);
	AssertJUnit.assertEquals("Dependent Strategy is Not Removed in Main", true, status);

	DevApisDataProvider obj = new DevApisDataProvider();

	String newUser = (obj.getRandomEmailId(15));
	SignUpRequest = IDPHelper.emailSignUp(newUser, password,"OnBoardUser","M","1234567890");
	AssertJUnit.assertEquals("SignUPError", 200,SignUpRequest.response.getStatus());
	request = OnBoardHelper.getHallmarkUserOnboardingData(newUser, password, "BOTH", appStat, RandomDeviceID);
	response = request.respvalidate.returnresponseasstring();
	System.out.println("Get user onboarding State Service Response : " + response);
	AssertJUnit.assertEquals("Get User Onboarding State Service is Down!!", reqStatus, request.response.getStatus());
	status = true;
	for (int i = 0; i < Strategies.length; i++) {
		status = status && OnBoardHelper.isStrategyRemoved(response, "DEPENDENT", Strategies[i]);
		status = status && OnBoardHelper.isStrategyRemoved(response, "MAIN", Strategies[i]);
	}
	AssertJUnit.assertEquals("Dependent Strategy is  Removed in Dependencies", false, status);
	AssertJUnit.assertEquals("Dependent Strategy is  Removed in Main", false, status);

}


@Test(groups = {"Regression"}, dataProvider = "HallMark_SaveOnBoardingStrategies_UserAndDeviceDependent_Flows",description = "Save onBoarding data with  Dependant Strategies - Same user Same Device")
public void OnBoarding_saveOnboardingState_Dependant_Flow1_SameUser_SameDevice(String username, String password, String Scenario, String appStat, String deviceID) 
{
	RequestGenerator SignUpRequest = IDPHelper.emailSignUp(username, password,"OnBoardUser","M","1234567890");
	AssertJUnit.assertEquals("SignUPError", 200,SignUpRequest.response.getStatus());
	
	String RandomDeviceID = OnBoardHelper.randomize(deviceID);
		//Overriding Scenario Type to match the test case
	RequestGenerator request = OnBoardHelper.getHallmarkUserOnboardingData(username, password, Scenario, appStat, RandomDeviceID);
	String response = request.respvalidate.returnresponseasstring();
	System.out.println("Get user onboarding State Service Response : " + response);
	int reqStatus = 200;
	AssertJUnit.assertEquals("Get User Onboarding State Service is Down!!", reqStatus, request.response.getStatus());

	String DependantStrategy = OnBoardHelper.getSingleStrategy(response, "DEPENDENT");
	System.out.println("Strategy : " + DependantStrategy);
	request = OnBoardHelper.SaveHallmarkUserOnboardingData(username, password, Scenario, appStat, RandomDeviceID, DependantStrategy);
	response = request.respvalidate.returnresponseasstring();
	System.out.println("Save user onboarding state Response : " + response);
	AssertJUnit.assertEquals("Save User Onboarding State Service is Down!!", 200, request.response.getStatus());

	request = OnBoardHelper.getHallmarkUserOnboardingData(username, password, Scenario, appStat, RandomDeviceID);
	response = request.respvalidate.returnresponseasstring();
	System.out.println("Get user onboarding State Service Response : " + response);
	AssertJUnit.assertEquals("Get User Onboarding State Service is Down!!", reqStatus, request.response.getStatus());
	AssertJUnit.assertEquals("Dependent Strategy is Not Removed in Dependencies", true, OnBoardHelper.isStrategyRemoved(response, "DEPENDENT", DependantStrategy));
	AssertJUnit.assertEquals("Dependent Strategy is Not Removed in Main", true, OnBoardHelper.isStrategyRemoved(response, "MAIN", DependantStrategy));


}


@Test(groups = {"Regression"}, dataProvider = "HallMark_SaveOnBoardingStrategies_UserAndDeviceDependent_Flows",
description = "Save onBoarding data with  Main Strategy - Same Device Different Users")
public void OnBoarding_saveOnboardingState_Main_Flow3_SameDevice_DifferentUser(String username, String password, String Scenario, String appStat, String deviceID) {
	RequestGenerator SignUpRequest = IDPHelper.emailSignUp(username, password,"OnBoardUser","M","1234567890");
	AssertJUnit.assertEquals("SignUPError", 200,SignUpRequest.response.getStatus());
	
	String RandomDeviceID = OnBoardHelper.randomize(deviceID);
		//Overriding Scenario Type to match the test case
	RequestGenerator request = OnBoardHelper.getHallmarkUserOnboardingData(username, password, Scenario, appStat, RandomDeviceID);
	String response = request.respvalidate.returnresponseasstring();
	System.out.println("Get user onboarding State Service Response : " + response);
	int reqStatus = 200;
	AssertJUnit.assertEquals("Get User Onboarding State Service is Down!!", reqStatus, request.response.getStatus());

	String DependantStrategy = OnBoardHelper.getSingleStrategy(response, "MAIN");
	System.out.println("Strategy : " + DependantStrategy);
	request = OnBoardHelper.SaveHallmarkUserOnboardingData(username, password, Scenario, appStat, RandomDeviceID, DependantStrategy);
	response = request.respvalidate.returnresponseasstring();
	System.out.println("Save user onboarding state Response : " + response);
	AssertJUnit.assertEquals("Save User Onboarding State Service is Down!!", 200, request.response.getStatus());

	request = OnBoardHelper.getHallmarkUserOnboardingData(username, password, Scenario, appStat, RandomDeviceID);
	response = request.respvalidate.returnresponseasstring();
	System.out.println("Get user onboarding State Service Response : " + response);
	AssertJUnit.assertEquals("Get User Onboarding State Service is Down!!", reqStatus, request.response.getStatus());
	AssertJUnit.assertEquals("Dependent Strategy is Not Removed in Dependencies", true, OnBoardHelper.isStrategyRemoved(response, "DEPENDENT", DependantStrategy));
	AssertJUnit.assertEquals("Dependent Strategy is Not Removed in Main", true, OnBoardHelper.isStrategyRemoved(response, "MAIN", DependantStrategy));

	DevApisDataProvider obj = new DevApisDataProvider();

	String newUser = (obj.getRandomEmailId(15));
	SignUpRequest = IDPHelper.emailSignUp(newUser, password,"OnBoardUser","M","1234567890");	request = OnBoardHelper.getHallmarkUserOnboardingData(newUser, password, Scenario, appStat, RandomDeviceID);
	response = request.respvalidate.returnresponseasstring();
	System.out.println("Get user onboarding State Service Response : " + response);
	AssertJUnit.assertEquals("Get User Onboarding State Service is Down!!", reqStatus, request.response.getStatus());
	AssertJUnit.assertEquals("Dependent Strategy is Not Removed in Dependencies - For Different Device", false, OnBoardHelper.isStrategyRemoved(response, "MAIN", DependantStrategy));


}


}

