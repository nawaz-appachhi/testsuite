package com.myntra.apiTests.dataproviders.devapis;

import com.myntra.apiTests.portalservices.commons.CommonUtils;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import com.myntra.lordoftherings.Configuration;
import com.myntra.lordoftherings.Toolbox;

public class CartTestsDP extends CommonUtils {
	String Env;
	Configuration con = new Configuration("./Data/configuration");
	StyleTestsDP Styles = new StyleTestsDP();
	public CartTestsDP() 
	{
		if (System.getenv("ENVIRONMENT") == null)
			Env = con.GetTestEnvironemnt().toString();
		else
			Env = System.getenv("ENVIRONMENT");
	}
	
	//Nav API DPs
	
	@DataProvider
	public Object[][] DevAPI_Cart_GetUserCart_Sanity_DP(ITestContext testContext)
	{
		String[] param1 = { "Fox7", "aravind.carttestUserFox@myntra.com","randompass"};
		String[] param2 = { "Production", "aravind.carttestUserProd@myntra.com","randompass"};
		Object[][] dataSet = new Object[][] { param1, param2};
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 1);
	}
	
	@DataProvider
	public Object[][] DevAPI_Cart_GetUserCart_Regression_DP(ITestContext testContext)
	{
		String[] param1 = { "Fox7", "aravind.carttestUserFox@myntra.com","randompass","true"};
		String[] param2 = { "Fox7", "aravind.carttestUserFox@myntra.com","randompass","false"};
		String[] param3 = { "Production", "aravind.carttestUserProd@myntra.com","randompass","true"};
		String[] param4 = { "Production", "aravind.carttestUserProd@myntra.com","randompass","false"};

		Object[][] dataSet = new Object[][] { param1, param2,param3,param4};
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 2, 2);
	}
	
	@DataProvider
	public Object[][] DevAPI_AddToCart_Sanity_DP(ITestContext testContext)
	{
		String sku = Styles.getRandomSkuForStyle(Styles.getRandomStyle(Styles.PumaStyles)).toString();
		String[] param1 = { "Fox7", "aravind.carttestUserFox@myntra.com","randompass",sku,"false"};
		Object[][] dataSet = new Object[][] { param1};
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 1);
	}
	
	@DataProvider
	public Object[][] DevAPI_AddToCartWithStyle_Sanity_DP(ITestContext testContext)
	{
		String StyleId = Styles.getRandomStyle(Styles.PumaStyles);
		String Sku = Styles.getRandomSkuForStyle(StyleId);
		String[] param1 = { "Fox7", "aravind.carttestUserFox@myntra.com","randompass",Sku,StyleId,"false"};
		Object[][] dataSet = new Object[][] { param1};
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 1);
	}
	
	@DataProvider
	public Object[][] DevAPI_AddToCartWithStyle_NegativeCase_DP(ITestContext testContext)
	{
		String StyleId = Styles.getRandomStyle(Styles.PumaStyles);
		String Sku = Styles.getRandomSkuForStyle(StyleId);
		String[] param1 = { "Fox7", "aravind.carttestUserFox@myntra.com","randompass","",StyleId,"false","400"};
		String[] param2 = { "Fox7", "aravind.carttestUserFox@myntra.com","randompass",null,StyleId,"false","400"};
		String[] param3 = { "Fox7", "aravind.carttestUserFox@myntra.com","randompass",Sku,null,"false","200"};
		String[] param4 = { "Fox7", "aravind.carttestUserFox@myntra.com","randompass",Sku,"","false","200"};
		String[] param5 = { "Fox7", "aravind.carttestUserFox@myntra.com","randompass","$^$%^$^%$^$^$%^%$^",StyleId,"false","400"};
		
		Object[][] dataSet = new Object[][] { param1, param2,param3,param4,param5};
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 5, 5);
	}
	
}
