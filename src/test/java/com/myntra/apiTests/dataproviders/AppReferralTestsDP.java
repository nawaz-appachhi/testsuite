package com.myntra.apiTests.dataproviders;

import com.myntra.apiTests.portalservices.commons.CommonUtils;
import com.myntra.apiTests.portalservices.devapiservice.DevApiServiceHelper;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import com.myntra.lordoftherings.Configuration;
import com.myntra.lordoftherings.Toolbox;

public class AppReferralTestsDP extends CommonUtils

{
	String Env;
	Configuration con = new Configuration("./Data/configuration");
	static DevApiServiceHelper devApiServiceHelper = new DevApiServiceHelper();

	public AppReferralTestsDP() 
	{
		if (System.getenv("ENVIRONMENT") == null)
		Env = con.GetTestEnvironemnt().toString();
		else
		Env = System.getenv("ENVIRONMENT");
	}
	
	
	//Data  Providers
	@DataProvider
	public Object[][] AppReferral_GetReferralCode_Success(ITestContext testContext) 
	{
		String[] UserData1 = { "Fox7","aravind.raj@myntra.com" ,"welcome"};
		String[] UserData2 = { "Fox7","aravind.raj@myntra.com" ,"welcome"};
		String[] UserData3 = { "Production","aravind.raj@myntra.com" ,"welcome"};
		String[] UserData4 = { "Production","aravind.raj@myntra.com" ,"welcome"};
		String[] UserData5 = { "Functional","aravind.raj@myntra.com" ,"welcome"};
		String[] UserData6 = { "Functional","aravind.raj@myntra.com" ,"welcome"};
		Object[][] dataSet = new Object[][] {UserData1,UserData2,UserData3,UserData4,UserData5,UserData6};
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	}	
	
	@DataProvider
	public Object[][] AppReferral_GetDetails(ITestContext testContext) 
	{
		String[] UserData1 = { "Fox7","aravind.raj@myntra.com" ,"welcome"};
		String[] UserData2 = { "Production","aravind.raj@myntra.com" ,"welcome"};
		String[] UserData3 = { "Functional","aravind.raj@myntra.com" ,"welcome"};
		Object[][] dataSet = new Object[][] {UserData1,UserData2,UserData3};
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] AppReferral_Welcome(ITestContext testContext) 
	{
		String[] UserData1 = { "Fox7","aravind.raj@myntra.com" ,"welcome","ermb0h"};
		String[] UserData2 = { "Production","aravind.raj@myntra.com" ,"welcome","ermb0h"};
		String[] UserData3 = { "Functional","aravind.raj@myntra.com" ,"welcome","ermb0h"};
		Object[][] dataSet = new Object[][] {UserData1,UserData2,UserData3};
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] AppReferral_Welcome_Failure(ITestContext testContext) 
	{
		String[] UserData1 = { "Fox7","aravind.raj@myntra.com" ,"welcome","ermb0h","N"};
		String[] UserData2 = { "Fox7","aravind.raj@myntra.com" ,"welcome","ermb0ha","P"};
		String[] UserData3 = { "Production","aravind.raj@myntra.com" ,"welcome","ermb0h","N"};
		String[] UserData4 = { "Production","aravind.raj@myntra.com" ,"welcome","ermb0ha","P"};
		String[] UserData5 = { "Functional","aravind.raj@myntra.com" ,"welcome","ermb0h","N"};
		String[] UserData6 = { "Functional","aravind.raj@myntra.com" ,"welcome","ermb0ha","P"};
		Object[][] dataSet = new Object[][] {UserData1,UserData2,UserData3,UserData4,UserData5,UserData6};
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public Object[][] AppReferral_GetOTP_Success(ITestContext testContext) 
	{
		String[] UserData1 = { "Fox7","aravind.raj@myntra.com" ,"welcome","9741284454","P"};
		String[] UserData2 = { "Production","aravind.raj@myntra.com" ,"welcome","9741284454","P"};
		String[] UserData3 = { "Functional","aravind.raj@myntra.com" ,"welcome","9741284454","P" };
		Object[][] dataSet = new Object[][] {UserData1,UserData2,UserData3};
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] AppReferral_GetOTP_Success_DifferentNumbers(ITestContext testContext) 
	{
		String[] UserData1 = { "Fox7","aravind.raj@myntra.com" ,"welcome","9741284454","9916205202","9741284454","P"};
		String[] UserData2 = { "Production","aravind.raj@myntra.com" ,"welcome","9741284454","9916205202","9741284454","P"};
		String[] UserData3 = { "Functional","aravind.raj@myntra.com" ,"welcome","9741284454","P" };
		Object[][] dataSet = new Object[][] {UserData1,UserData2,UserData3};
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] AppReferral_GetOTP_Fail(ITestContext testContext) 
	{
		String[] UserData1 = { "Fox7","aravind.raj@myntra.com" ,"welcome","9741284454","N"};
		String[] UserData2 = { "Fox7","aravind.raj@myntra.com" ,"welcome","9741284454ABCD","P"};
		String[] UserData3 = { "Production","aravind.raj@myntra.com" ,"welcome","9741284454","N"};
		String[] UserData4 = { "Production","aravind.raj@myntra.com" ,"welcome","9741284454ABCD","P"};
		String[] UserData5 = { "Production","aravind.raj@myntra.com" ,"welcome","0741284454","P"};
		String[] UserData6 = { "Production","aravind.raj@myntra.com" ,"welcome","1741284454","P"};
		String[] UserData7 = { "Production","aravind.raj@myntra.com" ,"welcome","2741284454","P"};
		String[] UserData8 = { "Production","aravind.raj@myntra.com" ,"welcome","3741284454","P"};
		String[] UserData9 = { "Production","aravind.raj@myntra.com" ,"welcome","4741284454","P"};
		String[] UserData10 = { "Production","aravind.raj@myntra.com" ,"welcome","5741284454","P"};
		String[] UserData11 = { "Production","aravind.raj@myntra.com" ,"welcome","6741284454","P"};
		String[] UserData12 = { "Production","aravind.raj@myntra.com" ,"welcome","6741284454","P"};
		String[] UserData13= { "Functional","aravind.raj@myntra.com" ,"welcome","9741284454","N"};
		String[] UserData14 = { "Functional","aravind.raj@myntra.com" ,"welcome","9741284454ABCD","P"};
		Object[][] dataSet = new Object[][] {UserData1,UserData2,UserData3,UserData4,UserData5,UserData6,UserData7,UserData8,UserData9,UserData10,UserData11,UserData12,UserData13,UserData14};
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] AppReferral_UnRegisteredDeviceFlow_Success(ITestContext testContext)
	{
		String[] UserData3 = { "Production","aravind.referral.Automation01@myntra.com" ,"randompass","zqxmdm","h9mdtuyfa7kzv6wa","4JnFqbP4XqGiTjkpCimBOx4n8IsSNBEkQa","84745963217","false"};
		Object[][] dataSet = new Object[][] {UserData3};
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] AppReferral_RegisteredDeviceData_Success(ITestContext testContext)
	{
		String[] UserData3 = { "Production","aravind.referral.Automation06@myntra.com" ,"randompass","h9mdtuyfa7kzv6wa","4JnFqbP4XqGiTjkpCimBOx4n8IsSNBEkQa","84745963217","false"};
		Object[][] dataSet = new Object[][] {UserData3};
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] AppReferral_UpdateDeviceData_Success(ITestContext testContext)
	{
		String[] UserData3 = { "Production","aravind.referral.Automation06@myntra.com" ,"randompass","h9mdtuyfa7kzv6wa","4JnFqbP4XqGiTjkpCimBOx4n8IsSNBEkQa","84745963217","false"};
		Object[][] dataSet = new Object[][] {UserData3};
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] AppReferral_DeviceInEligibleFlow_RootedDevice(ITestContext testContext)
	{
		String[] UserData3 = { "Production","aravind.referral.Automation01@myntra.com" ,"randompass","zqxmdm","6qzgjdvy-b3sxmiw","4KDDoL7yxOgmiqZfXeD9jJeAsULpdLWu2a","893961999689118","true"};
		Object[][] dataSet = new Object[][] {UserData3};
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] AppReferral_DeviceInEligibleFlow_AlreadyRegisteredDevice(ITestContext testContext)
	{
		String[] UserData3 = { "Production","aravind.referral.Automation12@myntra.com" ,"randompass","zqxmdm","6qzgjdvy-b3sxmiw","4KDDoL7yxOgmiqZfXeD9jJeAsULpdLWu2a","893961999689118","false","aravind.referral.Automation20@myntra.com","ermb0h"};
		Object[][] dataSet = new Object[][] {UserData3};
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] AppReferral_FreshInstallFlow(ITestContext testContext)
	{
		String[] UserData3 = { "Production","aravind.referral.Automation16@myntra.com" ,"randompass","gnaco2","6qzgjdvy-b3sxmiw","4KDDoL7yxOgmiqZfXeD9jJeAsULpdLWu2a","893961999689118","false"};
		Object[][] dataSet = new Object[][] {UserData3};
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] AppReferral_FreshInstall_VerifyReferCode_Success(ITestContext testContext)
	{
		String[] UserData3 = { "Production","aravind.referral.Automation16@myntra.com","aravind.referral.Automation_S" ,"randompass","gnaco2","6qzgjdvy-b3sxmiw","4KDDoL7yxOgmiqZfXeD9jJeAsULpdLWu2a","893961999689118","false"};
		Object[][] dataSet = new Object[][] {UserData3};
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] AppReferral_FreshInstall_VerifyReferCode_Retiral(ITestContext testContext)
	{
		String[] UserData3 = { "Production","aravind.referral.Automation16@myntra.com","aravind.referral.Automation_R" ,"randompass","gnaco2","6qzgjdvy-b3sxmiw","4KDDoL7yxOgmiqZfXeD9jJeAsULpdLWu2a","893961999689118","false"};
		Object[][] dataSet = new Object[][] {UserData3};
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] AppReferral_FreshInstall_VerifyReferCode_Failure(ITestContext testContext)
	{
		String[] UserData3 = { "Production","aravind.referral.Automation16@myntra.com","aravind.referral.Automation_F" ,"randompass","gnaco2","6qzgjdvy-b3sxmiw","4KDDoL7yxOgmiqZfXeD9jJeAsULpdLWu2a","893961999689118","false"};
		Object[][] dataSet = new Object[][] {UserData3};
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	}
}
