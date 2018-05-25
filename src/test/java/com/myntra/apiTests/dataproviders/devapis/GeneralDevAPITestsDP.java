package com.myntra.apiTests.dataproviders.devapis;

import com.myntra.apiTests.portalservices.commons.CommonUtils;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import com.myntra.lordoftherings.Configuration;
import com.myntra.lordoftherings.Toolbox;

public class GeneralDevAPITestsDP extends CommonUtils
{
	String Env;
	Configuration con = new Configuration("./Data/configuration");

	public GeneralDevAPITestsDP() 
	{
		if (System.getenv("ENVIRONMENT") == null)
			Env = con.GetTestEnvironemnt().toString();
		else
			Env = System.getenv("ENVIRONMENT");
	}
	
	//Nav API DPs
	@DataProvider
	public Object[][] DevAPI_Nav_AndroidRightNav_User_DP(ITestContext testContext)
	{
		String[] param1 = { "Fox7", "aravind.navtestUserFox@myntra.com","randompass"};
		String[] param2 = { "Production", "aravind.navtestUserProd@myntra.com","randompass"};
		Object[][] dataSet = new Object[][] { param1, param2};
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 1);
	}
	
	//Android Layouts API DPs
	@DataProvider
	public Object[][] DevAPI_Android_GetLayouts_DP(ITestContext testContext)
	{
		String[] param1 = { "Fox7", "men"};
		String[] param2 = { "Fox7", "women"};
		String[] param3 = { "Fox7", "kids"};
		String[] param5 = { "Production", "men"};
		String[] param6 = { "Production", "women"};
		String[] param7 = { "Production", "kids"};	
		
		Object[][] dataSet = new Object[][] { param1, param2,param3,param5,param6,param7};
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 3, 3);
	}
	
	@DataProvider
	public Object[][] GetUserCoupons_Success(ITestContext testContext)
	{
		//Last parameter decides whether to use Mail to get the coupons
		String[] case1 = { "Production", "aravind.coupons@myntra.com","randompass","true"};
		String[] case2 = { "Production", "aravind.coupons@myntra.com","randompass","false"};
		Object[][] dataSet = new Object[][] { case1,case2};
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 1);
	
	}
	
	@DataProvider
	public Object[][] GetUserCoupons_ByEmail(ITestContext testContext)
	{
		//Last parameter decides whether to use Mail to get the coupons
		String[] case1 = { "Production", "aravind.coupons@myntra.com","randompass","true"};
		Object[][] dataSet = new Object[][] { case1};
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 1);
	
	}
	
	@DataProvider
	public Object[][] GetUserCoupons_ByUIDX(ITestContext testContext)
	{
		//Last parameter decides whether to use Mail to get the coupons
		String[] case1 = { "Production", "aravind.coupons@myntra.com","randompass","false"};
		Object[][] dataSet = new Object[][] { case1};
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 1);
	
	}
	
	@DataProvider
	public Object[][] GetLGPReferral(ITestContext testContext)
	{
		String[] case1 = { "Production", "aravind.coupons@myntra.com","randompass","2.8","false"};
		String[] case2 = { "Production", "aravind.coupons@myntra.com","randompass","2.8","true"};

		Object[][] dataSet = new Object[][] { case1, case2};
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 2, 2);
	
	}
}
