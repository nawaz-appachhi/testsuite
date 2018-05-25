package com.myntra.apiTests.dataproviders.devapis;

import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import com.myntra.lordoftherings.Configuration;
import com.myntra.lordoftherings.Toolbox;

public class StyleForumTestsDP  extends GeneralDevAPITestsDP  {
	
	String Env;
	Configuration con = new Configuration("./Data/configuration");

	public StyleForumTestsDP() 
	{
		if (System.getenv("ENVIRONMENT") == null)
			Env = con.GetTestEnvironemnt().toString();
		else
			Env = System.getenv("ENVIRONMENT");
	}
	
	@DataProvider
	public Object[][] DevAPI_StyleForum_GetUserFeeds_DP_Sanity(ITestContext testContext)
	{
		String[] param1 = { "Fox7", "aravind.styleforum.fox1@myntra.com", "randompass"};
		Object[][] dataSet = new Object[][] { param1};
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 1);
	}
	
	@DataProvider
	public Object[][] DevAPI_StyleForum_GetForumFeeds_DP_Sanity(ITestContext testContext)
	{
		String[] param1 = { "Fox7", "aravind.styleforum.fox1@myntra.com", "randompass","featured"};
		String[] param2 = { "Fox7", "aravind.styleforum.fox1@myntra.com", "randompass","new"};
		String[] param3 = { "Fox7", "aravind.styleforum.fox1@myntra.com", "randompass","unanswered"};

		Object[][] dataSet = new Object[][] { param1,param2,param3};
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 3, 3);
	}
	
	@DataProvider
	public Object[][] DevAPI_StyleForum_GetForumFeedsForAllTopics_DP_Sanity(ITestContext testContext)
	{
		String[] param1 = { "Fox", "aravind.styleforum.fox1@myntra.com", "randompass","featured"};
		String[] param2 = { "Fox", "aravind.styleforum.fox1@myntra.com", "randompass","new"};
		String[] param3 = { "Fox", "aravind.styleforum.fox1@myntra.com", "randompass","unanswered"};

		Object[][] dataSet = new Object[][] { param1,param2,param3};
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 3, 3);
	}

	@DataProvider
	public Object[][] DevAPI_StyleForum_GetPersonalizedFeeds_DP_Sanity(ITestContext testContext)
	{
		String[] param1 = { "Fox7", "aravind.styleforum.fox1@myntra.com", "randompass"};
		Object[][] dataSet = new Object[][] { param1};
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 1);
	}
}
