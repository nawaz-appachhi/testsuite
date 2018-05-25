package com.myntra.apiTests.dataproviders.devapis;

import java.util.ArrayList;
import java.util.Random;

import com.myntra.apiTests.portalservices.commons.CommonUtils;
import com.myntra.apiTests.portalservices.devapiservice.StyleTestsHelper;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import com.myntra.apiTests.portalservices.devapiservice.SearchTestsHelper;
import com.myntra.lordoftherings.Configuration;
import com.myntra.lordoftherings.Toolbox;


//Data provider for all IDP related test cases.
public class StyleTestsDP extends CommonUtils
{
	String Env;
	Configuration con = new Configuration("./Data/configuration");
	static SearchTestsHelper Helper = new SearchTestsHelper();
	static StyleTestsHelper StyleHelper = new StyleTestsHelper();
	Random rnd = new Random();
	ArrayList<String> NikeStyles, PumaStyles, Jeans, Shoes;
	ArrayList<Integer> sku;

	public StyleTestsDP() 
	{
		if (System.getenv("ENVIRONMENT") == null)
			Env = con.GetTestEnvironemnt().toString();
		else
			Env = System.getenv("ENVIRONMENT");
		
		NikeStyles = Helper.performKeywordSearchAndReturnResultsAsList("Nike");
		PumaStyles = Helper.performKeywordSearchAndReturnResultsAsList("puma");
		Jeans = Helper.performKeywordSearchAndReturnResultsAsList("jeans");
		Shoes = Helper.performKeywordSearchAndReturnResultsAsList("shoes");

	}
	
	public String getRandomStyle(ArrayList<String> Styles)
	{
		String Style = Styles.get(rnd.nextInt(Styles.size())).toString();
		return Style;
	}
	
	public String getRandomSkuForStyle(String StyleID)
	{
		sku = StyleHelper.getStyleSKUInfo(StyleID);
		int SKU = sku.get(rnd.nextInt(sku.size()));
		return Integer.toString(SKU);

	}
	
	@DataProvider
	public Object[][] DevAPI_Style_GetStyleInfo_Sanity(ITestContext testContext)
	{
		String[] StyleID1 = { "Fox7",getRandomStyle(PumaStyles)};
		String[] StyleID2 = { "Fox7",getRandomStyle(NikeStyles)};
		String[] StyleID3 = { "Fox7",getRandomStyle(Shoes)};
		String[] StyleID4 = { "Production",getRandomStyle(PumaStyles)};
		String[] StyleID5 = { "Production",getRandomStyle(NikeStyles)};
		String[] StyleID6 = { "Production",getRandomStyle(Shoes)};
		
		Object DataSet[][]= new Object[][] {StyleID1,StyleID2,StyleID3,StyleID4,StyleID5,StyleID6};
		return Toolbox.returnReducedDataSet(Env, DataSet, testContext.getIncludedGroups(), 3, 3);
	}
	
	@DataProvider
	public Object[][] DevAPI_Style_GetStyleInfo_NegativeCases(ITestContext testContext)
	{
		String[] StyleID1 = { "Fox7","","404"};
		String[] StyleID2 = { "Fox7","text","500"};
		String[] StyleID3 = { "Fox7","455ffff","500"};
		String[] StyleID4 = { "Fox7","null","500"};
		String[] StyleID5 = { "Production","","404"};
		String[] StyleID6 = { "Production","text","500"};
		String[] StyleID7 = { "Production","455ffff","500"};
		String[] StyleID8 = { "Production","null","500"};
		Object DataSet[][]= new Object[][] {StyleID1,StyleID2,StyleID3,StyleID4,StyleID5,StyleID6,StyleID7,StyleID8};
		return Toolbox.returnReducedDataSet(Env, DataSet, testContext.getIncludedGroups(), 4, 4);
	}
	
	@DataProvider
	public Object[][] DevAPI_Style_GetNormalStyleRecommendations_Sanity(ITestContext testContext)
	{
		//Param 3 is for 'Use Visual Search' & Param 4 is for 'Use Color Recommendations'
		String[] StyleID1 = { "Fox7",getRandomStyle(PumaStyles),"false","false"};
		String[] StyleID2 = { "Fox",getRandomStyle(NikeStyles),"false","false"};
		String[] StyleID3 = { "Fox",getRandomStyle(Jeans),"false","false"};
		String[] StyleID4 = { "Production",getRandomStyle(NikeStyles),"false","false"};
		String[] StyleID5 = { "Production",getRandomStyle(Shoes),"false","false"};
		String[] StyleID6 = { "Production",getRandomStyle(Jeans),"false","false"};
		
		Object DataSet[][]= new Object[][] {StyleID1,StyleID2,StyleID3,StyleID4,StyleID5,StyleID6};
		return Toolbox.returnReducedDataSet(Env, DataSet, testContext.getIncludedGroups(), 3, 3);
	}
	
	@DataProvider
	public Object[][] DevAPI_Style_GetNormalStyleRecommendations_Regression(ITestContext testContext)
	{
		//Param 3 is for 'Use Visual Search' & Param 4 is for 'Use Color Recommendations'
		String[] StyleID1 = { "Fox7","295440","false","false"};
		String[] StyleID2 = { "Fox","295440","false","true"};
		String[] StyleID3 = { "Fox","295440","true","false"};
		String[] StyleID4 = { "Fox7","295440","true","true"};
		String[] StyleID5 = { "Production","1304562","false","false"};
		String[] StyleID6 = { "Production","1304562","false","true"};
		String[] StyleID7 = { "Production","1304562","ture","false"};
		String[] StyleID8 = { "Production","1304562","true","true"};

		
		Object DataSet[][]= new Object[][] {StyleID1,StyleID2,StyleID3,StyleID4,StyleID5,StyleID6,StyleID7,StyleID8};
		return Toolbox.returnReducedDataSet(Env, DataSet, testContext.getIncludedGroups(), 4, 4);
	}
	
	@DataProvider
	public Object[][] DevAPI_Style_V1Serviceiability_Sanity(ITestContext testContext)
	{
		String[] Data1={"Fox7","pincode=999999&styleid="+getRandomStyle(NikeStyles)+"&availableinwarehouses=36"};
		
		Object DataSet[][]= new Object[][] {Data1};
		return Toolbox.returnReducedDataSet(Env, DataSet, testContext.getIncludedGroups(), 1, 1);
	}
	
	@DataProvider
	public Object[][] DevAPI_Style_V1Serviceability_Regression(ITestContext testContext)
	{
		String[] Data1={"Fox7","pincode=999999&styleid="+getRandomStyle(NikeStyles)+"&availableinwarehouses=36"};
		String[] Data2={"Fox7","pincode=560068&styleid="+getRandomStyle(NikeStyles)+"&availableinwarehouses=36"};
		String[] Data3={"Fox7","pincode=560068&styleid="+getRandomStyle(NikeStyles)+"&availableinwarehouses=1"};
		String[] Data4={"Production","pincode=999999&styleid="+getRandomStyle(NikeStyles)+"&availableinwarehouses=36"};
		String[] Data5={"Production","pincode=560068&styleid="+getRandomStyle(NikeStyles)+"&availableinwarehouses=36"};
		String[] Data6={"Production","pincode=560068&styleid="+getRandomStyle(NikeStyles)+"&availableinwarehouses=1"};
		
		Object DataSet[][]= new Object[][] {Data1,Data2,Data3,Data4,Data5,Data6};
		return Toolbox.returnReducedDataSet(Env, DataSet, testContext.getIncludedGroups(), 3, 3);
	}
	
	
	
	@DataProvider
	public Object[][] DevAPI_Style_V2Serviceiability_Sanity(ITestContext testContext)
	{
		String[] Data1={"Fox7","pincode=999999&styleid="+getRandomStyle(NikeStyles)+"&availableinwarehouses=36"};
		
		Object DataSet[][]= new Object[][] {Data1};
		return Toolbox.returnReducedDataSet(Env, DataSet, testContext.getIncludedGroups(), 1, 1);
	}
	
	@DataProvider
	public Object[][] DevAPI_Style_V2Serviceability_Regression(ITestContext testContext)
	{
		String StyleID = getRandomStyle(NikeStyles);
		String[] Data1={"Fox7","styleid="+StyleID+"&skuid="+getRandomSkuForStyle(StyleID)+"&availableinwarehouses=28%2C36&leadtime=0&supplytype=ON_HAND&pincode=560068"};
		StyleID = getRandomStyle(Jeans);
		String[] Data2={"Fox7","styleid="+StyleID+"&skuid="+getRandomSkuForStyle(StyleID)+"&availableinwarehouses=28%2C36&leadtime=0&supplytype=ON_HAND&pincode=999999"};
		StyleID = getRandomStyle(Shoes);
		String[] Data3={"Fox7","styleid="+StyleID+"&skuid="+getRandomSkuForStyle(StyleID)+"&availableinwarehouses=28%2C36&leadtime=0&supplytype=ON_HAND&pincode=560068"};
		StyleID = getRandomStyle(Shoes);
		String[] Data4={"Production","styleid="+StyleID+"&skuid="+getRandomSkuForStyle(StyleID)+"&availableinwarehouses=28%2C36&leadtime=0&supplytype=ON_HAND&pincode=560068"};
		StyleID = getRandomStyle(NikeStyles);
		String[] Data5={"Production","styleid="+StyleID+"&skuid="+getRandomSkuForStyle(StyleID)+"&availableinwarehouses=28%2C36&leadtime=0&supplytype=ON_HAND&pincode=999999"};
		StyleID = getRandomStyle(PumaStyles);
		String[] Data6={"Production","sstyleid="+StyleID+"&skuid="+getRandomSkuForStyle(StyleID)+"&availableinwarehouses=28%2C36&leadtime=0&supplytype=ON_HAND&pincode=560068"};
		
		Object DataSet[][]= new Object[][] {Data1,Data2,Data3,Data4,Data5,Data6};
		return Toolbox.returnReducedDataSet(Env, DataSet, testContext.getIncludedGroups(), 3, 3);
	}
	
	@DataProvider
	public Object[][] DevAPI_Style_V3Serviceiability_Sanity(ITestContext testContext)
	{
		String StyleID = getRandomStyle(Shoes);
		String SKU = getRandomSkuForStyle(StyleID);
		String[] Data1={"Fox7","styleid="+StyleID+"&skuid="+SKU+"&availableinwarehouses=36%2C1&leadtime=0&supplytype=ON_HAND&pincode=560068"};
		String[] Data2={"Production","styleid="+StyleID+"&skuid="+SKU+"&availableinwarehouses=36%2C28&leadtime=0&supplytype=ON_HAND&pincode=560068"};
		Object DataSet[][]= new Object[][] {Data1, Data2};
		return Toolbox.returnReducedDataSet(Env, DataSet, testContext.getIncludedGroups(), 1, 1);
	}
	
	@DataProvider
	public Object[][] DevAPI_Style_V3Serviceiability_Regression(ITestContext testContext)
	{
		String StyleID = getRandomStyle(Jeans);
		String SKU = getRandomSkuForStyle(StyleID);
		
		String[] Data1={"Fox7","skuid="+SKU+"&availableinwarehouses=36%2C1&leadtime=0&supplytype=ON_HAND&pincode=560068"};
		String[] Data2={"Fox7","styleid="+StyleID+"&availableinwarehouses=36%2C1&leadtime=0&supplytype=ON_HAND&pincode=560068"};
		String[] Data3={"Fox7","styleid="+StyleID+"&skuid="+SKU+"&leadtime=0&supplytype=ON_HAND&pincode=560068"};
		String[] Data4={"Fox7","styleid="+StyleID+"&skuid="+SKU+"&availableinwarehouses=36%2C1&supplytype=ON_HAND&pincode=560068"};
		String[] Data5={"Fox7","styleid="+StyleID+"&skuid="+SKU+"&availableinwarehouses=36%2C1&leadtime=0&pincode=560068"};
		String[] Data6={"Fox7","skuid="+SKU+"&leadtime=0&supplytype=ON_HAND&pincode=560068"};
		String[] Data7={"Fox7","styleid="+StyleID+"&availableinwarehouses=36%2C1&supplytype=ON_HAND&pincode=560068"};
		String[] Data8={"Fox7","styleid="+StyleID+"&skuid="+SKU+"&leadtime=0&pincode=560068"};
		String[] Data9={"Fox7","styleid="+StyleID+"&skuid="+SKU+"&supplytype=ON_HAND&pincode=560068"};
		String[] Data10={"Fox7","styleid="+StyleID+"&pincode=560068"};
		String[] Data11={"Fox7","skuid="+SKU+"&pincode=560068"};
		String[] Data12={"Production","skuid="+SKU+"&availableinwarehouses=36%2C28&leadtime=0&supplytype=ON_HAND&pincode=560068"};
		String[] Data13={"DEMO","styleid="+StyleID+"&availableinwarehouses=36%2C28&leadtime=0&supplytype=ON_HAND&pincode=560068"};
		String[] Data14={"DEMO","styleid="+StyleID+"&skuid="+SKU+"&leadtime=0&supplytype=ON_HAND&pincode=560068"};
		String[] Data15={"DEMO","styleid="+StyleID+"&skuid="+SKU+"&availableinwarehouses=36%2C28&supplytype=ON_HAND&pincode=560068"};
		String[] Data16={"DEMO","styleid="+StyleID+"&skuid="+SKU+"&availableinwarehouses=36%2C28&leadtime=0&pincode=560068"};
		String[] Data17={"DEMO","skuid="+SKU+"&leadtime=0&supplytype=ON_HAND&pincode=560068"};
		String[] Data18={"DEMO","styleid="+StyleID+"&availableinwarehouses=36%2C28&supplytype=ON_HAND&pincode=560068"};
		String[] Data19={"DEMO","styleid="+StyleID+"&skuid="+SKU+"&leadtime=0&pincode=560068"};
		String[] Data20={"DEMO","styleid="+StyleID+"&skuid="+SKU+"&supplytype=ON_HAND&pincode=560068"};
		String[] Data21={"DEMO","styleid="+StyleID+"&pincode=560068"};
		String[] Data22={"DEMO","skuid="+SKU+"&pincode=560068"};

		Object DataSet[][]= new Object[][] {Data1,Data2,Data3,Data4,Data5,Data6,Data7,Data8,Data9,Data10,Data11,Data12,Data13,Data14,Data15,Data16,Data17,Data18,Data19,Data20,Data21,Data22};
		return Toolbox.returnReducedDataSet(Env, DataSet, testContext.getIncludedGroups(), 11, 11);
	}
	
	@DataProvider
	public Object[][] DevAPI_Style_V3Serviceiability_FailCases(ITestContext testContext)
	{
		String StyleID = getRandomStyle(PumaStyles);
		String SKU = getRandomSkuForStyle(StyleID);
		
		String[] Data1={"Fox7","skuid="+StyleID+"&skuid=null&availableinwarehouses=36%2C1&leadtime=0&supplytype=ON_HAND&pincode=560068"};
		String[] Data2={"Fox7","skuid="+StyleID+"&skuid="+SKU+"&availableinwarehouses=36%2C1&leadtime=0&supplytype=ON_HAND&pincode=null"};
		String[] Data3={"Fox7","skuid="+StyleID+"&skuid="+SKU+"&availableinwarehouses=36%2C1&leadtime=0&supplytype=ON_HAND&pincode=null"};
		String[] Data4={"Fox7","skuid="+StyleID+"&skuid="+SKU+"&availableinwarehouses=36%2C1&leadtime=0&supplytype=ON_HAND&pincode=abcedf"};
		String[] Data5={"Fox7","skuid="+StyleID+"&skuid="+SKU+"&availableinwarehouses=36%2C1&leadtime=0&supplytype=ON_HAND&pincode=*"};
		String[] Data6={"Fox7","skuid="+StyleID+"&skuid="+SKU+"&availableinwarehouses=36%2C1&leadtime=0&supplytype=ON_HAND&pincode=560ee8"};
		String[] Data7={"Fox7","skuid="+StyleID+"&skuid=abcdef&availableinwarehouses=36%2C1&leadtime=0&supplytype=ON_HAND&pincode=560ee8"};
		String[] Data8={"Fox7","skuid="+StyleID+"&skuid=*&availableinwarehouses=36%2C1&leadtime=0&supplytype=ON_HAND&pincode=560ee8"};
		String[] Data9={"Production","skuid="+StyleID+"&skuid=*&availableinwarehouses=36%2C28&leadtime=0&supplytype=ON_HAND&pincode=5600e8"};
		String[] Data10={"Production","skuid="+StyleID+"&skuid=abcdef&availableinwarehouses=36%2C28&leadtime=0&supplytype=ON_HAND&pincode=5600e8"};
		String[] Data11={"Production","skuid="+StyleID+"&skuid="+SKU+"&availableinwarehouses=36%2C28&leadtime=0&supplytype=ON_HAND&pincode=5600e8"};
		String[] Data12={"Production","skuid="+StyleID+"&skuid="+SKU+"&availableinwarehouses=36%2C28&leadtime=0&supplytype=ON_HAND&pincode=*"};
		String[] Data13={"Production","skuid="+StyleID+"&skuid=n9168062ull&availableinwarehouses=36%2C28&leadtime=0&supplytype=ON_HAND&pincode=abcdef"};
		String[] Data14={"Production","skuid="+StyleID+"&skuid=null&availableinwarehouses=36%2C28&leadtime=0&supplytype=ON_HAND&pincode=560068"};
		String[] Data15={"Production","skuid="+StyleID+"&skuid=null&availableinwarehouses=36%2C28&leadtime=0&supplytype=ON_HAND&pincode=560068"};
		String[] Data16={"Production","availableinwarehouses=36%2C28&leadtime=0&supplytype=ON_HAND&pincode=null"};
		
		Object DataSet[][]= new Object[][] {Data1, Data2,Data3,Data4,Data5,Data6,Data7,Data8,Data9,Data10,Data11,Data12,Data13,Data14,Data15,Data16};
		return Toolbox.returnReducedDataSet(Env, DataSet, testContext.getIncludedGroups(), 8, 8);
	}
	
	
	@DataProvider
	public Object[][] DevAPI_Style_V4Serviceiability_Sanity(ITestContext testContext)
	{
		String[] Data1={"Fox7",getRandomStyle(PumaStyles),"560068"};
		String[] Data2={"Fox7",getRandomStyle(Jeans),"560068"};
		String[] Data3={"Production",getRandomStyle(Shoes),"560068"};
		String[] Data4={"Production",getRandomStyle(NikeStyles),"560068"};		
		Object DataSet[][]= new Object[][] {Data1, Data2,Data3,Data4};
		return Toolbox.returnReducedDataSet(Env, DataSet, testContext.getIncludedGroups(), 2, 2);
	}
	
	@DataProvider
	public Object[][] DevAPI_Style_V4Serviceiability_NonServiceable(ITestContext testContext)
	{
		String[] Data1={"Fox7",getRandomStyle(PumaStyles),"12345"};
		String[] Data2={"Fox7",getRandomStyle(PumaStyles),"12345"};
		String[] Data3={"Production",getRandomStyle(PumaStyles),"12345"};
		String[] Data4={"Production",getRandomStyle(PumaStyles),"12345"};		
		Object DataSet[][]= new Object[][] {Data1, Data2,Data3,Data4};
		return Toolbox.returnReducedDataSet(Env, DataSet, testContext.getIncludedGroups(), 2, 2);
	}
	
	@DataProvider
	public Object[][] DevAPI_Style_GetStyleOffers_Sanity(ITestContext testContext)
	{
		String[] StyleID1 = { "Fox7",getRandomStyle(PumaStyles)};
		String[] StyleID2 = { "Fox7",getRandomStyle(NikeStyles)};
		String[] StyleID3 = { "Fox7",getRandomStyle(Shoes)};
		String[] StyleID4 = { "Production",getRandomStyle(NikeStyles)};
		String[] StyleID5 = { "Production",getRandomStyle(PumaStyles)};
		String[] StyleID6 = { "Production",getRandomStyle(Jeans)};
		
		Object DataSet[][]= new Object[][] {StyleID1,StyleID2,StyleID3,StyleID4,StyleID5,StyleID6};
		return Toolbox.returnReducedDataSet(Env, DataSet, testContext.getIncludedGroups(), 3, 3);
	}
	
	@DataProvider
	public Object[][] DevAPI_Style_GetStyleFromSku_Sanity(ITestContext testContext)
	{
		String[] StyleID1 = { "Fox7","RDTLTRSR00020"};
		String[] StyleID2 = { "Fox7","RDTLTRSR00022"};
		String[] StyleID3 = { "Fox7","RDTLTRSR00023"};
		String[] StyleID4 = { "Production","KNKHTSHT00005717"};
		String[] StyleID5 = { "Production","RDSTSHRT00010289"};
		String[] StyleID6 = { "Production","HBHRJENS00000445"};
		
		Object DataSet[][]= new Object[][] {StyleID1,StyleID2,StyleID3,StyleID4,StyleID5,StyleID6};
		return Toolbox.returnReducedDataSet(Env, DataSet, testContext.getIncludedGroups(), 3, 3);
	}
	
	@DataProvider
	public Object[][] DevAPI_Style_GetStyleFromSku_Data(ITestContext testContext)
	{
		String[] StyleID1 = { "Fox7","RDTLTRSR00020","12345"};
		String[] StyleID2 = { "Fox7","RDTLTRSR00022","12346"};
		String[] StyleID3 = { "Fox7","RDTLTRSR00023","12346"};
		String[] StyleID4 = { "Production","RDTLTRSR00020","12345"};
		String[] StyleID5 = { "Production","RDTLTRSR00052","70935"};
		String[] StyleID6 = { "Production","RDTLTRSR00042","39106"};
		
		Object DataSet[][]= new Object[][] {StyleID1,StyleID2,StyleID3,StyleID4,StyleID5,StyleID6};
		return Toolbox.returnReducedDataSet(Env, DataSet, testContext.getIncludedGroups(), 3, 3);
	}
	
	@DataProvider
	public Object[][] DevAPI_Style_GetStyleFromSku_FailCases(ITestContext testContext)
	{
		String[] StyleID1 = { "Fox7","RDTLTRSFSDFSDFDSFR00020","404"};
		String[] StyleID2 = { "Fox7","RDTLTRSFSFSDFDSFSDFSDR00022","404"};
		String[] StyleID3 = { "Fox7","RDTLTRSFSDFSDFSDFSR00023","404"};
		String[] StyleID4 = { "Fox7","","500"};
		String[] StyleID6 = { "Fox7","1","404"};
		String[] StyleID7 = { "Production","RDSTJENSSFSFDS00012748","404"};
		String[] StyleID8 = { "Production","RDSTJENSFSDFDSS00012718","404"};
		String[] StyleID9 = { "Production","KNKHTSSDFSFSDFSDFDSHT00005721","404"};
		String[] StyleID10 = { "Production","","500"};
		String[] StyleID12 = { "Production","1","404"};
		
		Object DataSet[][]= new Object[][] {StyleID1,StyleID2,StyleID3,StyleID4,StyleID6,StyleID7,StyleID8,StyleID9,StyleID10,StyleID12};
		return Toolbox.returnReducedDataSet(Env, DataSet, testContext.getIncludedGroups(), 6, 6);
	}
}

