package com.myntra.apiTests.dataproviders.devapis;

import com.myntra.apiTests.portalservices.commons.CommonUtils;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import com.myntra.lordoftherings.Configuration;
import com.myntra.lordoftherings.Toolbox;

public class SearchTestsDP extends CommonUtils
{
	String Env;
	Configuration con = new Configuration("./Data/configuration");

	public SearchTestsDP() 
	{
		if (System.getenv("ENVIRONMENT") == null)
			Env = con.GetTestEnvironemnt().toString();
		else
			Env = System.getenv("ENVIRONMENT");
	}
	
	//AutoSuggest
	@DataProvider
	public Object[][] DevAPI_Search_GetAutoSuggest_Sanity_DP(ITestContext testContext)
	{
		String[] param1 = { "Fox7", "nike","5"};
		String[] param2 = { "Production", "nike","5"};
		
		Object[][] dataSet = new Object[][] { param1, param2};
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 1);
	}
	
	@DataProvider
	public Object[][] DevAPI_Search_GetAutoSuggest_Regression_DP(ITestContext testContext)
	{
		String[] param1 = { "Fox7", "nik","5"};
		String[] param2 = { "Fox7", "nike","5"};
		String[] param3 = { "Fox7", "nik","5"};
		String[] param4 = { "Fox7", "nike","5"};
		String[] param5 = { "Fox7", "Ni","5"};
		String[] param6 = { "Fox7", "Ni**","5"};
		String[] param7 = { "Fox7", "NiKe","5"};
		String[] param8 = { "Fox7", "NIKE","5"};
		String[] param9 = { "Fox7", "Nike%20Sh**","5"};
		String[] param10 = { "Fox7", "Ni**%20Shoe","5"};
		String[] param11 = { "Production", "nik","5"};
		String[] param12 = { "Production", "nike","5"};
		String[] param13= { "Production", "nik","5"};
		String[] param14= { "Production", "nike","5"};
		String[] param15= { "Production", "Ni","5"};
		String[] param16= { "Production", "Ni**","5"};
		String[] param17= { "Production", "NiKe","5"};
		String[] param18= { "Production", "NIKE","5"};
		String[] param19 = { "Production", "Nike%20Sh**","5"};
		String[] param20 = { "Production", "Ni**%20Shoe","5"};
		Object[][] dataSet = new Object[][] { param1,param2,param3,param4,param5,param6,param7,param8,param9,param10,param11,param12,param13,param14,param15,param16,param17,param18,param19,param20};
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 8, 8);
	}

	@DataProvider
	public Object[][] DevAPI_Search_GetAutoSuggest_FailCases_DP(ITestContext testContext)
	{
		String[] param1 = { "Fox7", "%2525*8%2525","0"};
		String[] param2 = { "Fox7", "sdfdsfdsfdsf","-1"};
		String[] param3 = { "Fox7", "WREWREWWERWWE","5"};
		String[] param4 = { "Production", "%2525*8%2525","0"};
		String[] param5 = { "Production", "sdfdsfdsfdsf","-1"};
		String[] param6 = { "Production", "WREWREWWERWWE","5"};
		
		Object[][] dataSet = new Object[][] { param1,param2,param3,param4,param5,param6};
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 3, 3);
	}
	
	@DataProvider
	public Object[][] DevAPI_Search_KeyWordSearch_Sanity_DP(ITestContext testContext)
	{
		String[] param1 = { "Fox7", "tshirts"};
		String[] param2 = { "Fox7", "nike"};
		String[] param3 = { "Fox7", "men"};
		String[] param4 = { "Production", "tshirts"};
		String[] param5 = { "Production", "nike"};
		String[] param6 = { "Production", "women"};
		
		Object[][] dataSet = new Object[][] { param1,param2,param3,param4,param5,param6};
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 3, 3);
	}
	
	@DataProvider
	public Object[][] DevAPI_Search_KeyWordSearch_NodeSchemaValidations_DP(ITestContext testContext)
	{
		String[] param1 = { "Fox7", "tshirts"};
		String[] param2 = { "Production", "tshirts"};
		
		Object[][] dataSet = new Object[][] { param1,param2};
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 1);
	}
	
	@DataProvider
	public Object[][] DevAPI_Search_KeyWordSearch_Regression_DP(ITestContext testContext)
	{
		String[] param1 = { "Fox7", "men%20tshirts"};
		String[] param2 = { "Fox7", "women%20short%20pants"};
		String[] param3 = { "Fox7", "kids"};
		String[] param4 = { "Fox7", "nike%20tshirts"};
		String[] param5 = { "Fox7", "reebok%20black%20pshoes"};
		String[] param6 = { "Fox7", "puma"};
		String[] param7 = { "Fox7", "black%20tshirts"};
		String[] param8 = { "Fox7", "casual%20black%20pshoes"};
		String[] param9 = { "Fox7", "sunglasses"};
		
		String[] param10 = { "Production", "men%20tshirts"};
		String[] param11 = { "Production", "women%20pants"};
		String[] param12 = { "Production", "kids"};
		String[] param13 = { "Production", "nike%20tshirts"};
		String[] param14 = { "Production", "reebok%20black%20pshoes"};
		String[] param15 = { "Production", "puma"};
		String[] param16 = { "Production", "black%20tshirts"};
		String[] param17 = { "Production", "casual%20black%20pshoes"};
		String[] param18 = { "Production", "sunglasses"};
		
		Object[][] dataSet = new Object[][] { param1,param2,param3,param4,param5,param6,param7,param8,param9,param10,param11,param12,param13,param14,param15,param16,param17,param18};
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 9, 9);
	}
	
	@DataProvider
	public Object[][] DevAPI_Search_KeyWordSearchWithFacets_Sanity_DP(ITestContext testContext)
	{
		String[] param1 = { "Fox7", "jeans%3FuserQuery%3Dtrue%26f%3Dgender%3Aboys%26rows%3D48%26request_id%3Dnull"};
		String[] param2 = { "Fox7", "nike%3FuserQuery%3Dtrue%26f%3Dgender%3Amen%26rows%3D48%26request_id%3Dnull"};
		String[] param3 = { "Production", "tshirts%3FuserQuery%3Dtrue%26f%3Dgender%3Agirls%26rows%3D48%26request_id%3Dnull"};
		String[] param4 = { "Production", "puma%3FuserQuery%3Dtrue%26f%3Dgender%3Awomen%26rows%3D48%26request_id%3Dnull"};
		
		Object[][] dataSet = new Object[][] { param1,param2,param3,param4};
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 2, 2);
	}
	
	@DataProvider
	public Object[][] DevAPI_Search_KeyWordSearchWithFacets_Regression_DP(ITestContext testContext)
	{
		String[] param1 = { "Fox7", "tshirts%3FuserQuery%3Dtrue%26f%3Dgender%3Aboys%3A%3Acolor%3Ablack%26rows%3D48%26request_id%3Dnull"};
		String[] param2 = { "Fox7", "tshirts%3FuserQuery%3Dtrue%26f%3Dgender%3Aboys%3A%3Acolor%3Ablack%3A%3Aage_facet%3A10y-12y%26sort%3Dhigh%26rows%3D48%26request_id%3Dnull"};
		String[] param3 = { "Fox7", "tshirts%3FuserQuery%3Dtrue%26f%3Dgender%3Aboys%3A%3Acolor%3Ablack%3A%3Aage_facet%3A10y-12y%3A%3Abrand%3AAllen%2520Solly%2520Junior%26sort%3Dhigh%26rows%3D48%26request_id%3Dnull"};
		String[] param4 = { "Fox7", "jeans%3FuserQuery%3Dtrue%26f%3Dsize_facet%3A32%26rows%3D48%26request_id%3Dnull"};
		String[] param5 = { "Production", "tshirts%3FuserQuery%3Dtrue%26f%3Dgender%3Aboys%3A%3Acolor%3Ablack%26rows%3D48%26request_id%3Dnull"};
		String[] param6 = { "Production", "tshirts%3FuserQuery%3Dtrue%26f%3Dgender%3Aboys%3A%3Acolor%3Ablack%3A%3Aage_facet%3A10y-12y%26sort%3Dhigh%26rows%3D48%26request_id%3Dnull"};
		String[] param7 = { "Production", "tshirts%3FuserQuery%3Dtrue%26f%3Dgender%3Aboys%3A%3Acolor%3Ablack%3A%3Aage_facet%3A10y-12y%3A%3Abrand%3AAllen%2520Solly%2520Junior%26sort%3Dhigh%26rows%3D48%26request_id%3Dnull"};
		String[] param8 = { "Production", "jeans%3FuserQuery%3Dtrue%26f%3Dsize_facet%3A32%26rows%3D48%26request_id%3Dnull"};
		Object[][] dataSet = new Object[][] { param1,param2,param3,param4,param5,param6,param7,param8};
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 4,4);
	}
	
	@DataProvider
	public Object[][] DevAPI_Search_KeyWordSearchSort_Regression_DP(ITestContext testContext)
	{
		String[] param1 = { "Fox7", "jeans%3FuserQuery%3Dtrue%26rows%3D48","PRICE-HIGH"};
		String[] param2 = { "Fox7", "jeans%3FuserQuery%3Dtrue%26rows%3D48","PRICE-LOW"};
		String[] param3 = { "Fox7", "jeans%3FuserQuery%3Dtrue%26rows%3D48","POPULARITY"};
		String[] param4 = { "Fox7", "jeans%3FuserQuery%3Dtrue%26rows%3D48","DISCOUNT"};
		String[] param5 = { "Fox7", "jeans%3FuserQuery%3Dtrue%26rows%3D48","WHATSNEW"};
		String[] param6 = { "Fox7", "puma%3FuserQuery%3Dtrue%26rows%3D48","PRICE-HIGH"};
		String[] param7 = { "Fox7", "puma%3FuserQuery%3Dtrue%26rows%3D48","PRICE-LOW"};
		String[] param8 = { "Fox7", "puma%3FuserQuery%3Dtrue%26rows%3D48","POPULARITY"};
		String[] param9 = { "Fox7", "puma%3FuserQuery%3Dtrue%26rows%3D48","DISCOUNT"};
		String[] param10 = { "Fox7", "puma%3FuserQuery%3Dtrue%26rows%3D48","WHATSNEW"};

		String[] param11 = { "Production", "tshirts?userQuery=true&rows=48","PRICE-HIGH"};
		String[] param12 = { "Production", "tshirts?userQuery=true&rows=48","PRICE-LOW"};
		String[] param13 = { "Production", "tshirts?userQuery=true&rows=48","POPULARITY"};
		String[] param14 = { "Production", "tshirts?userQuery=true&rows=48","DISCOUNT"};
		String[] param15 = { "Production", "tshirts?userQuery=true&rows=48","WHATSNEW"};
		String[] param16 = { "Production", "nike?userQuery=true&rows=48","PRICE-HIGH"};
		String[] param17 = { "Production", "nike?userQuery=true&rows=48","PRICE-LOW"};
		String[] param18 = { "Production", "nike?userQuery=true&rows=48","POPULARITY"};
		String[] param19 = { "Production", "nike?userQuery=true&rows=48","DISCOUNT"};
		String[] param20 = { "Production", "nike?userQuery=true&rows=48","WHATSNEW"};
		
		Object[][] dataSet = new Object[][] { param1,param2,param3,param4,param5,param6,param7,param8,param9,param10,param11,param12,param13,param14,param15,param16,param17,param18,param19,param20};
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 1);
	}
	
	@DataProvider
	public Object[][] DevAPI_Search_KeyWordSearchSort_NodeSchemaValidation_DP(ITestContext testContext)
	{
		String[] param1 = { "Fox7", "jeans%3FuserQuery%3Dtrue%26rows%3D48%26request_id%3Dnull","PRICE-HIGH"};
		String[] param2 = { "Production", "tshirts%3FuserQuery%3Dtrue%26rows%3D48%26request_id%3Dnull","PRICE-HIGH"};
			
		Object[][] dataSet = new Object[][] { param1,param2};
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 1);
	}
	
}
