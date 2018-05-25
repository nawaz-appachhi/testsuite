package com.myntra.apiTests.dataproviders.devapis;

import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import com.myntra.lordoftherings.Toolbox;

public class OnBoardingTestsDP extends IDPTestsDP {
	
	@DataProvider
	public Object[][] HallMark_GetOnBoardingStrategies(ITestContext testContext) 
	{
		String[] UserData1 = new String[]{"Fox7",getRandomEmailId(15),"welcome","NONE","new","DeviceID123"};
		String[] UserData2 = new String[]{"Fox7",getRandomEmailId(15),"welcome","DEVICEID","new","DeviceID123"};
		String[] UserData3 = new String[]{"Fox7",getRandomEmailId(15),"welcome","XID","new","DeviceID123"};
		String[] UserData4 = new String[]{"Fox7",getRandomEmailId(15),"welcome","BOTH","new","DeviceID123"};
		String[] UserData5 = new String[]{"Fox7",getRandomEmailId(15),"welcome","NONE","upgrade","DeviceID123"};
		String[] UserData6 = new String[]{"Fox7",getRandomEmailId(15),"welcome","DEVICEID","upgrade","DeviceID123"};
		String[] UserData7 = new String[]{"Fox7",getRandomEmailId(15),"welcome","XID","upgrade","DeviceID123"};
		String[] UserData8 = new String[]{"Fox7",getRandomEmailId(15),"welcome","BOTH","upgrade","DeviceID123"};
		String[] UserData9 = new String[]{"Production",getRandomEmailId(15),"welcome","NONE","new","DeviceID123"};
		String[] UserData10 = new String[]{"Production",getRandomEmailId(15),"welcome","DEVICEID","new","DeviceID123"};
		String[] UserData11= new String[]{"Production",getRandomEmailId(15),"welcome","XID","new","DeviceID123"};
		String[] UserData12 = new String[]{"Production",getRandomEmailId(15),"welcome","BOTH","new","DeviceID123"};
		String[] UserData13= new String[]{"Production",getRandomEmailId(15),"welcome","NONE","upgrade","DeviceID123"};
		String[] UserData14 = new String[]{"Production",getRandomEmailId(15),"welcome","DEVICEID","upgrade","DeviceID123"};
		String[] UserData15 = new String[]{"Production",getRandomEmailId(15),"welcome","XID","upgrade","DeviceID123"};
		String[] UserData16 = new String[]{"Production",getRandomEmailId(15),"welcome","BOTH","upgrade","DeviceID123"};
   	Object[][] dataSet = new Object[][] {UserData1,UserData2,UserData3,UserData4,UserData5,UserData6,UserData7,UserData8,UserData9,UserData10,UserData11,UserData12,UserData13,UserData14,UserData15,UserData16};
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 8, 8);
	}
	
	@DataProvider
	public Object[][] HallMark_SaveOnBoardingStrategies(ITestContext testContext) 
	{
		String[] UserData1 = new String[]{"Fox7",getRandomEmailId(15),"welcome","NONE","new","DeviceID123"};
		String[] UserData2 = new String[]{"Fox7",getRandomEmailId(15),"welcome","DEVICEID","new","DeviceID123"};
		String[] UserData3 = new String[]{"Fox7",getRandomEmailId(15),"welcome","XID","new","DeviceID123"};
		String[] UserData4 = new String[]{"Fox7",getRandomEmailId(15),"welcome","BOTH","new","DeviceID123"};
		String[] UserData5 = new String[]{"Fox7",getRandomEmailId(15),"welcome","NONE","upgrade","DeviceID123"};
		String[] UserData6 = new String[]{"Fox7",getRandomEmailId(15),"welcome","DEVICEID","upgrade","DeviceID123"};
		String[] UserData7 = new String[]{"Fox7",getRandomEmailId(15),"welcome","XID","upgrade","DeviceID123"};
		String[] UserData8 = new String[]{"Fox7",getRandomEmailId(15),"welcome","BOTH","upgrade","DeviceID123"};
		String[] UserData9 = new String[]{"Production",getRandomEmailId(15),"welcome","NONE","new","DeviceID123"};
		String[] UserData10 = new String[]{"Production",getRandomEmailId(15),"welcome","DEVICEID","new","DeviceID123"};
		String[] UserData11= new String[]{"Production",getRandomEmailId(15),"welcome","XID","new","DeviceID123"};
		String[] UserData12 = new String[]{"Production",getRandomEmailId(15),"welcome","BOTH","new","DeviceID123"};
		String[] UserData13= new String[]{"Production",getRandomEmailId(15),"welcome","NONE","upgrade","DeviceID123"};
		String[] UserData14 = new String[]{"Production",getRandomEmailId(15),"welcome","DEVICEID","upgrade","DeviceID123"};
		String[] UserData15 = new String[]{"Production",getRandomEmailId(15),"welcome","XID","upgrade","DeviceID123"};
		String[] UserData16 = new String[]{"Production",getRandomEmailId(15),"welcome","BOTH","upgrade","DeviceID123"};
   	Object[][] dataSet = new Object[][] {UserData1,UserData2,UserData3,UserData4,UserData5,UserData6,UserData7,UserData8,UserData9,UserData10,UserData11,UserData12,UserData13,UserData14,UserData15,UserData16};
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 8, 8);
	}
	
	@DataProvider
	public Object[][] HallMark_SaveOnBoardingStrategies_UserDependent_Flows(ITestContext testContext) 
	{
		String[] UserData1 = new String[]{"Fox7",getRandomEmailId(15),"welcome","XID","new","DeviceID123"};
		String[] UserData2 = new String[]{"Fox7",getRandomEmailId(15),"welcome","XID","upgrade","DeviceID123"};
		String[] UserData3 = new String[]{"Production",getRandomEmailId(15),"welcome","XID","new","DeviceID123"};
		String[] UserData4 = new String[]{"Production",getRandomEmailId(15),"welcome","XID","upgrade","DeviceID123"};
		Object[][] dataSet = new Object[][] {UserData1,UserData2,UserData3,UserData4};
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 7, 7);
	}
	
	@DataProvider
	public Object[][] HallMark_SaveOnBoardingStrategies_UserAndDeviceDependent_Flows(ITestContext testContext) 
	{
		String[] UserData1 = new String[]{"Fox7",getRandomEmailId(15),"welcome","BOTH","new","DeviceID123"};
		String[] UserData2 = new String[]{"Fox7",getRandomEmailId(15),"welcome","BOTH","upgrade","DeviceID123"};
		String[] UserData3 = new String[]{"Production",getRandomEmailId(15),"welcome","BOTH","new","DeviceID123"};
		String[] UserData4 = new String[]{"Production",getRandomEmailId(15),"welcome","BOTH","upgrade","DeviceID123"};
		Object[][] dataSet = new Object[][] {UserData1,UserData2,UserData3,UserData4};
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 7, 7);
	}
	
	@DataProvider
	public Object[][] HallMark_SaveOnBoardingStrategies_DeviceDependent_Flows(ITestContext testContext) 
	{
		String[] UserData1 = new String[]{"Fox7",getRandomEmailId(15),"welcome","DEVICEID","new","DeviceID123"};
		String[] UserData2 = new String[]{"Fox7",getRandomEmailId(15),"welcome","DEVICEID","upgrade","DeviceID123"};
		String[] UserData3 = new String[]{"Production",getRandomEmailId(15),"welcome","DEVICEID","new","DeviceID123"};
		String[] UserData4 = new String[]{"Production",getRandomEmailId(15),"welcome","DEVICEID","upgrade","DeviceID123"};
		Object[][] dataSet = new Object[][] {UserData1,UserData2,UserData3,UserData4};
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 7, 7);
	}
	
	
	@DataProvider
	public Object[][] HallMark_SaveOnBoardingStrategies_Flows(ITestContext testContext) 
	{
		String[] UserData1 = new String[]{"Fox7",getRandomEmailId(15),"welcome","DEVICEID","new","DeviceID123"};
		String[] UserData2 = new String[]{"Fox7",getRandomEmailId(15),"welcome","XID","new","DeviceID123"};
		String[] UserData3 = new String[]{"Fox7",getRandomEmailId(15),"welcome","BOTH","new","DeviceID123"};
		String[] UserData4 = new String[]{"Fox7",getRandomEmailId(15),"welcome","DEVICEID","upgrade","DeviceID123"};
		String[] UserData5 = new String[]{"Fox7",getRandomEmailId(15),"welcome","XID","upgrade","DeviceID123"};
		String[] UserData6 = new String[]{"Fox7",getRandomEmailId(15),"welcome","BOTH","upgrade","DeviceID123"};
		String[] UserData7 = new String[]{"Production",getRandomEmailId(15),"welcome","DEVICEID","new","DeviceID123"};
		String[] UserData8= new String[]{"Production",getRandomEmailId(15),"welcome","XID","new","DeviceID123"};
		String[] UserData9 = new String[]{"Production",getRandomEmailId(15),"welcome","BOTH","new","DeviceID123"};
		String[] UserData10 = new String[]{"Production",getRandomEmailId(15),"welcome","DEVICEID","upgrade","DeviceID123"};
		String[] UserData11 = new String[]{"Production",getRandomEmailId(15),"welcome","XID","upgrade","DeviceID123"};
		String[] UserData12 = new String[]{"Production",getRandomEmailId(15),"welcome","BOTH","upgrade","DeviceID123"};
		Object[][] dataSet = new Object[][] {UserData1,UserData2,UserData3,UserData4,UserData5,UserData6,UserData7,UserData8,UserData9,UserData10,UserData11,UserData12};
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 7, 7);
	}
	
	@DataProvider
	public Object[][] DevAPI_saveOnboardingState_Dependant_Flow1_SameUser_SameDevice(ITestContext testContext) 
	{
		
		String[] UserData3 = new String[]{"Fox7",getRandomEmailId(15),"welcome","BOTH","new","DeviceID123"};
		String[] UserData7 = new String[]{"Fox7",getRandomEmailId(15),"welcome","BOTH","upgrade","DeviceID123"};
		String[] UserData11= new String[]{"Production",getRandomEmailId(15),"welcome","BOTH","new","DeviceID123"};
		String[] UserData15 = new String[]{"Production",getRandomEmailId(15),"welcome","BOTH","upgrade","DeviceID123"};
   	Object[][] dataSet = new Object[][] {UserData3,UserData7,UserData11,UserData15};
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 2, 2);
	}
	
	@DataProvider
	public Object[][] DevAPI_saveOnboardingState_Main_Flow3_SameDevice_DifferentUser(ITestContext testContext) 
	{
		
		String[] UserData3 = new String[]{"Fox7",getRandomEmailId(15),"welcome","BOTH","new","DeviceID123"};
		String[] UserData7 = new String[]{"Fox7",getRandomEmailId(15),"welcome","BOTH","upgrade","DeviceID123"};
		String[] UserData11= new String[]{"Production",getRandomEmailId(15),"welcome","BOTH","new","DeviceID123"};
		String[] UserData15 = new String[]{"Production",getRandomEmailId(15),"welcome","BOTH","upgrade","DeviceID123"};
   	Object[][] dataSet = new Object[][] {UserData3,UserData7,UserData11,UserData15};
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 2, 2);
	}

}
