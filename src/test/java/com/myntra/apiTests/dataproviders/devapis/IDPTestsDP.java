package com.myntra.apiTests.dataproviders.devapis;

import java.util.Random;

import com.myntra.apiTests.portalservices.commons.CommonUtils;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import com.myntra.lordoftherings.Configuration;
import com.myntra.lordoftherings.Toolbox;


//Data provider for all IDP related test cases.
public class IDPTestsDP extends CommonUtils
{
	String Env;
	Configuration con = new Configuration("./Data/configuration");

	public IDPTestsDP() 
	{
		if (System.getenv("ENVIRONMENT") == null)
			Env = con.GetTestEnvironemnt().toString();
		else
			Env = System.getenv("ENVIRONMENT");
	}
	
	//Generate Random Sign Up data
	public String getRandomEmailId(int length) {
		String prefix = "DevApiSignup_";
		String randomString = "abcdefghijlkmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZqwerty7890";
		char[] c = randomString.toCharArray();
		String toReturn = "";
		for (int i = 0; i < length; i++) {
			toReturn += c[randomNumberUptoLimit(c.length)];
		}
		return prefix + toReturn + getEmaildSuffix();

	}

	private int randomNumberUptoLimit(int limit) {
		Random random = new Random();
		int index = random.nextInt(limit);
		return index;
	}

	private String getEmaildSuffix() {
		String[] suffix = { "@gmail.com", "@rediffmail.com", "@myntra.com",
				"@yahoo.com", "@hotmail.com" };
		return suffix[randomNumberUptoLimit(suffix.length)];
	}
	
	//SignUp Data Providers
	
	@DataProvider
	public Object[][] DevAPI_IDP_SignUp_DP_Sanity(ITestContext testContext)
	{
		String[] param1 = { "Fox7", getRandomEmailId(10), "qwerty","FoxUser","M","1234567890"};
		String[] param2 = { "Production", getRandomEmailId(15), "qwerty","ProdUser","M","1234567890"};
		Object[][] dataSet = new Object[][] { param1, param2};
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 1);
	}
	
	@DataProvider
	public Object[][] DevAPI_IDP_SignUp_DP_Regression(ITestContext testContext)
	{
		String[] param1 = { "Fox7", getRandomEmailId(10), "qwerty","FoxUser","M","1234567890"};
		String[] param2 = { "Fox7", getRandomEmailId(10), "qwerty","","M","1234567890"};
		String[] param3 = { "Fox7", getRandomEmailId(10), "qwerty","FoxUser"," ","1234567890"};
		String[] param5 = { "Fox7", getRandomEmailId(10), "qwerty","FoxUser","M","1234567890"};
		String[] param6 = { "Fox7", getRandomEmailId(10), "123456","FoxUser","M","1234567890"};
		String[] param7 = { "Fox7", getRandomEmailId(10), " 123456","FoxUser","M","1234567890"};
		String[] param8 = { "Fox7", getRandomEmailId(10), "123456 ","FoxUser","M","1234567890"};
		String[] param9 = { "Fox7", getRandomEmailId(10), "qwerty 123456","FoxUser","M","1234567890"};
		String[] param10 = { "Fox7", getRandomEmailId(10), "$56@#42341","FoxUser","M","1234567890"};
		String[] param11= { "Production", getRandomEmailId(15), "qwerty","ProdUser","M","1234567890" };
		String[] param12 = { "Production", getRandomEmailId(10), "qwerty","","M","1234567890"};
		String[] param13 = { "Production", getRandomEmailId(10), "qwerty","ProdUser"," ","1234567890"};
		String[] param15 = { "Production", getRandomEmailId(10), "qwerty","ProdUser","M","1234567890"};
		String[] param16= { "Production", getRandomEmailId(10), "123456","ProdUser","M","1234567890" };
		String[] param17= { "Production", getRandomEmailId(10), " 123456","ProdUser","M","1234567890" };
		String[] param18 = { "Production", getRandomEmailId(10), "123456 ","ProdUser","M","1234567890" };
		String[] param19 = { "Production", getRandomEmailId(10), "qwerty 123456","ProdUser","M","1234567890" };
		String[] param20 = { "Production", getRandomEmailId(10), "$56@#42341","ProdUser","M","1234567890" };
		Object[][] dataSet = new Object[][] { param1, param2, param3, param5, param6, param7, param8, param9, param10, param11, param12, param13, param15, param16, param17, param18, param19, param20};
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 10, 10);
	}
	
	@DataProvider
	public Object[][] DevAPI_IDP_SignUp_DP_NegativeCases(ITestContext testContext)
	{
		String[] param1 = { "Fox7", "", "qwerty","FoxUser","M","1234567890"};
		String[] param2 = { "Fox7", "mailwithoutdomain", "qwerty","","M","1234567890"};
		String[] param3 = { "Fox7", "@myntra.com", "qwerty","FoxUser","","1234567890"};
		String[] param4 = { "Fox7", getRandomEmailId(10), "","FoxUser","M",""};
		String[] param5 = { "Fox7", getRandomEmailId(10), "qwerty","FoxUser","%","1234567890"};
		String[] param6 = { "Fox7", getRandomEmailId(10), "123456","FoxUser","E","1234567890"};
		String[] param7 = { "Fox7", getRandomEmailId(10), " 123456","FoxUser","M","mobilenumber"};
		String[] param8 = { "Fox7", getRandomEmailId(10), "123456 ","FoxUser","M","54321     "};
		String[] param9 = { "Fox7", getRandomEmailId(10), "qwerty 123456","FoxUser","M","     12345"};
		String[] param10 = { "Fox7", getRandomEmailId(10), "$56@#42341","FoxUser","M","1   56 890"};
		String[] param11 = { "Production", "", "qwerty","ProdUser","M","1234567890"};
		String[] param12 = { "Production", "mailwithoutdomain", "qwerty","","M","1234567890"};
		String[] param13 = { "Production", "@myntra.com", "qwerty","ProdUser","","1234567890"};
		String[] param14 = { "Production", getRandomEmailId(10), "","ProdUser","M",""};
		String[] param15 = { "Production", getRandomEmailId(10), "qwerty","ProdUser","%","1234567890"};
		String[] param16 = { "Production", getRandomEmailId(10), "123456","ProdUser","E","1234567890"};
		String[] param17 = { "Production", getRandomEmailId(10), " 123456","ProdUser","M","mobilenumber"};
		String[] param18 = { "Production", getRandomEmailId(10), "123456 ","ProdUser","M","54321     "};
		String[] param19 = { "Production", getRandomEmailId(10), "qwerty 123456","ProdUser","M","     12345"};
		String[] param20 = { "Production", getRandomEmailId(10), "$56@#42341","ProdUser","M","1   56 890"};
		Object[][] dataSet = new Object[][] { param1, param2, param3, param4, param5, param6, param7, param8, param9, param10, param11, param12, param13, param14, param15, param16, param17, param18, param19, param20};
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 10, 10);
	}
	
	//Sign In data Proviers
	@DataProvider
	public Object[][] DevAPI_IDP_SignIn_DP_Sanity(ITestContext testContext)
	{
		String[] param1 = { "Fox7", "devapi_suite_foxuser1@myntra.com", "foxwelcome"};
		String[] param2 = { "Production", "devapi_suite_produser1@myntra.com", "prodwelcome"};
		Object[][] dataSet = new Object[][] { param1, param2};
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 1);
	}
	
	@DataProvider
	public Object[][] DevAPI_IDP_SignIn_DP_Regression(ITestContext testContext)
	{
		String[] param1 = { "Fox7", "devapi_suite_foxuser1@myntra.com", "foxwelcome"};
		String[] param2 = { "Fox7", "devapi_suite_FoxUser1@myntra.com", "foxwelcome"};
		String[] param3 = { "Production", "devapi_suite_produser1@myntra.com", "prodwelcome"};
		String[] param4 = { "Production", "Devapi_suite_produser1@myntra.com", "prodwelcome"};
		Object[][] dataSet = new Object[][] { param1, param2,param3,param4};
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 2, 2);
	}
	
	@DataProvider
	public Object[][] DevAPI_IDP_SignIn_DP_NegativeCases(ITestContext testContext)
	{
		String[] param1 = { "Fox7", "", "foxwelcome"};
		String[] param2 = { "Fox7", "devapi_suite_FoxUser1@myntra.com", ""};
		String[] param3 = { "Fox7", "1=1' or '1=1", "8bwlE2bET9RnXBj"};
		String[] param4 = { "Fox7", "devapi_suite_FoxUser1@myntra.com", "1=1' or '1=1"};
		String[] param5 = { "Fox7", "    devapi_suite_foxuser1@myntra.com", "foxwelcome"};
		String[] param6 = { "Fox7", "devapi_suite_foxuser1@myntra.com   m", "foxwelcome"};
		String[] param7 = { "Fox7", "devapi_suite_foxuser1@myntra.com", "   foxwelcome"};
		String[] param8 = { "Fox7", "devapi_suite_foxuser1@myntra.com", "foxwelcome   "};
		String[] param9 = { "Fox7", "devapi_suite_  foxuser1@myntra.com", "foxwelcome"};
		String[] param10 = { "Fox7", "devapi_suite_foxuser1@myntra.com", "fox  welcome"};
		String[] param11= { "Fox7", null, "foxwelcome"};
		String[] param12 = { "Fox7", "devapi_suite_foxuser1@myntra.com", null};
		String[] param13 = { "Production", "", "prodwelcome"};
		String[] param14 = { "Production", "devapi_suite_produser1@myntra.com", ""};
		String[] param15 = { "Production", "1=1' or '1=1", "8bwlE2bET9RnXBj"};
		String[] param16 = { "Production", "devapi_suite_produser1@myntra.com", "1=1' or '1=1"};
		String[] param17= { "Production", "    devapi_suite_produser1@myntra.com", "prodwelcome"};
		String[] param18 = { "Production", "devapi_suite_produser1@myntra.com   m", "prodwelcome"};
		String[] param19 = { "Production", "devapi_suite_produser1@myntra.com", "   prodwelcome"};
		String[] param20= { "Production", "devapi_suite_produser1@myntra.com", "prodwelcome   "};
		String[] param21= { "Production", "devapi_suite_  foxuser1@myntra.com", "prodwelcome"};
		String[] param22= { "Production", "devapi_suite_produser1@myntra.com", "fox  welcome"};
		String[] param23= { "Production", null, "prodwelcome"};
		String[] param24= { "Production", "devapi_suite_produser1@myntra.com", null};
		Object[][] dataSet = new Object[][] { param1, param2, param3, param4, param5, param6, param7, param8, param9, param10, param11, param12, param13, param14, param15, param16, param17, param18, param19, param20,param21,param22,param23,param24};
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 12, 12);
	}
	
	//DEVAPI REFRESH
	@DataProvider
	public Object[][] DevAPI_IDP_Refresh_DP_Regression_TamperIDs(ITestContext testContext)
	{
		String[] param1 = { "Fox7", "devapi_suite_foxuser1@myntra.com", "foxwelcome","false","true","200"};
		String[] param2 = { "Fox7", "devapi_suite_foxuser1@myntra.com", "foxwelcome","true","false","401"};
		String[] param3 = { "Fox7", "devapi_suite_foxuser1@myntra.com", "foxwelcome","true","true","401"};
		String[] param4 = { "Production", "devapi_suite_produser1@myntra.com", "prodwelcome","false","true","200"};
		String[] param5 = { "Production", "devapi_suite_produser1@myntra.com", "prodwelcome","true","false","401"};
		String[] param6 = { "Production", "devapi_suite_produser1@myntra.com", "prodwelcome","true","true","401"};

		Object[][] dataSet = new Object[][] { param1, param2,param3,param4,param5,param6};
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 3, 3);
	}
	
	//SignOut API
	@DataProvider
	public Object[][] DevAPI_IDP_SignOut_DP_Sanity(ITestContext testContext)
	{
		String[] param1 = { "Fox7", "devapi_suite_foxuser1@myntra.com", "foxwelcome"};
		String[] param2 = { "Production", "devapi_suite_produser1@myntra.com", "prodwelcome"};
		Object[][] dataSet = new Object[][] { param1, param2};
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 1);
	}
	
	//SignOut API
	@DataProvider
	public Object[][] DevAPI_IDP_PasswordReset_DP_Sanity(ITestContext testContext)
	{
		String[] param1 = { "Fox7", "devapi_suite_foxuser1@myntra.com"};
		String[] param2 = { "Production", "devapi_suite_produser1@myntra.com"};
		Object[][] dataSet = new Object[][] { param1, param2};
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 1);
	}
	
	@DataProvider
	public Object[][] DevAPI_IDP_PasswordReset_DP_FailCases(ITestContext testContext)
	{
		String[] param1 = { "Fox7", "devapi_suite_foxuser1"};
		String[] param2 = { "Fox7", "@myntra.com"};
		String[] param3 = { "Fox7", null};
		String[] param4 = { "Fox7", "123123123312"};
		String[] param5 = { "Fox7", "@$@#$@#$#@@#$@"};
		String[] param6 = { "Production", "devapi_suite_produser1"};
		String[] param7 = { "Production", "@myntra.com"};
		String[] param8 = { "Production", null};
		String[] param9 = { "Production", "123123123312"};
		String[] param10 = { "Production", "@$@#$@#$#@@#$@"};

		Object[][] dataSet = new Object[][] { param1, param2,param3,param4,param5,param6,param7,param8,param9,param10 };
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 5, 5);
	}
}
