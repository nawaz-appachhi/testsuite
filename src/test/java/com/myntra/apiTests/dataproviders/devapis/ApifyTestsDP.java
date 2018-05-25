package com.myntra.apiTests.dataproviders.devapis;

import com.myntra.apiTests.portalservices.commons.CommonUtils;
import com.myntra.apiTests.portalservices.devapiservice.DevApiServiceHelper;
import com.myntra.apiTests.portalservices.devapiservice.SearchTestsHelper;
import com.myntra.lordoftherings.Configuration;
import com.myntra.lordoftherings.Toolbox;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import java.util.ArrayList;
import java.util.Random;

public class ApifyTestsDP extends CommonUtils {

	String Env;
	Configuration con = new Configuration("./Data/configuration");
	static DevApiServiceHelper devApiServiceHelper = new DevApiServiceHelper();
	static SearchTestsHelper Helper = new SearchTestsHelper();
	ArrayList<String> NikeStyles, PumaStyles, Jeans, Shoes, Sarees, HandBags, Innerwears, Brief, Trunks;
	Random rnd = new Random();

	public ApifyTestsDP() {
		if (System.getenv("ENVIRONMENT") == null)
			Env = con.GetTestEnvironemnt().toString();
		else
			Env = System.getenv("ENVIRONMENT");

		NikeStyles = Helper.performKeywordSearchAndReturnResultsAsList("Nike%20Shirts");
		PumaStyles = Helper.performKeywordSearchAndReturnResultsAsList("Puma%20Shirts");
		Jeans = Helper.performKeywordSearchAndReturnResultsAsList("jeans");
		Shoes = Helper.performKeywordSearchAndReturnResultsAsList("shoes");
		Sarees = Helper.performKeywordSearchAndReturnResultsAsList("sarees");
		HandBags = Helper.performKeywordSearchAndReturnResultsAsList("handbags");
	}

	public String getRandomStyle(ArrayList<String> Styles) {
		String Style = Styles.get(rnd.nextInt(Styles.size())).toString();
		return Style;
	}

	@DataProvider
	public Object[][] ApifyDataProvider_ValidStyleId(ITestContext testContext) {
		
		String[] styleid1 = { "Production", getRandomStyle(NikeStyles)};
		String[] styleid2 = { "Production", getRandomStyle(PumaStyles)};
		String[] styleid3 = { "Production", getRandomStyle(Shoes)};
		String[] styleid4 = { "Production", getRandomStyle(Sarees)};
		String[] styleid5 = { "Production", getRandomStyle(HandBags)};
		String[] styleid6 = { "Production", getRandomStyle(Jeans)};
		
		String[] styleid7 = { "M7", getRandomStyle(NikeStyles)};
		String[] styleid8 = { "M7", getRandomStyle(PumaStyles)};
		String[] styleid9 = { "M7", getRandomStyle(Shoes)};
		String[] styleid10 = { "M7", getRandomStyle(Sarees)};
		String[] styleid11 = { "M7", getRandomStyle(HandBags)};
		String[] styleid12 = { "M7", getRandomStyle(Jeans)};

		Object[][] dataSet = new Object[][] { styleid1, styleid2, styleid3, styleid4, styleid5, styleid6, styleid7,
				styleid8, styleid9, styleid10, styleid11, styleid12 };
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 6, 6);
	}
	
	@DataProvider
	public Object[][] ApifyDataProvider_ValidStyleIdForCollapseState(ITestContext testContext) {
		String[] styleid1 = { "Production", getRandomStyle(NikeStyles), "connectionClass=UNKNOWN;", "false" };
		String[] styleid2 = { "Production", getRandomStyle(PumaStyles), "connectionClass=WORST; bandwidth=78.2;", "true" };
		String[] styleid3 = { "Production", getRandomStyle(Shoes), "connectionClass=UNKNOWN;", "false" };
		String[] styleid4 = { "Production", getRandomStyle(Sarees), "connectionClass=WORST; bandwidth=65.00;", "true" };
		String[] styleid5 = { "Production", getRandomStyle(HandBags), "connectionClass=UNKNOWN;", "false" };
		String[] styleid6 = { "Production", getRandomStyle(Jeans), "connectionClass=WORST; bandwidth=40;", "true" };

		String[] styleid7 = { "Production", getRandomStyle(NikeStyles),
				"connectionClass=UNKNOWN;", "false" };
		String[] styleid8 = { "Production", getRandomStyle(PumaStyles),
				"connectionClass=WORST; bandwidth=2.0;", "true" };
		String[] styleid9 = { "Production", getRandomStyle(Shoes), "connectionClass=UNKNOWN;", "false"  };
		
		String[] styleid10 = { "M7", getRandomStyle(NikeStyles), "connectionClass=UNKNOWN;", "false" };
		String[] styleid11 = { "M7", getRandomStyle(PumaStyles), "connectionClass=WORST; bandwidth=78.2;", "true" };
		String[] styleid12 = { "M7", getRandomStyle(Shoes), "connectionClass=UNKNOWN;", "false" };
		String[] styleid13 = { "M7", getRandomStyle(Sarees), "connectionClass=WORST; bandwidth=65.00;", "true" };
		String[] styleid14 = { "M7", getRandomStyle(HandBags), "connectionClass=UNKNOWN;", "false" };
		String[] styleid15 = { "M7", getRandomStyle(Jeans), "connectionClass=WORST; bandwidth=40;", "true" };

		String[] styleid16 = { "M7", getRandomStyle(NikeStyles),
				"connectionClass=UNKNOWN;", "false" };
		String[] styleid17 = { "M7", getRandomStyle(PumaStyles),
				"connectionClass=WORST; bandwidth=2.0;", "true" };
		String[] styleid18 = { "M7", getRandomStyle(Shoes), "connectionClass=UNKNOWN;", "false"  };

		Object[][] dataSet = new Object[][] { styleid1, styleid2, styleid3, styleid4, styleid5, styleid6, styleid7,
			styleid8, styleid9, styleid10, styleid11, styleid12, styleid13, styleid14, styleid15, styleid16, styleid17, styleid18 };
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 9, 9);
	}
	
	@DataProvider
	public Object[][] ApifyDataProvider_InvalidStyleId(ITestContext testContext) {

		String[] styleid1 = { "Production", "aaaaaaa", "404" };
		String[] styleid2 = { "Production", "00000000", "500" };
		String[] styleid3 = { "Production", "", "404" };
		String[] styleid4 = { "Production", "123aaaa", "404" };
		String[] styleid5 = { "Production", null, "404" };

		String[] styleid6 = { "M7", "aaaaaaa", "404" };
		String[] styleid7 = { "M7", "00000000", "500" };
		String[] styleid8 = { "M7", "", "404" };
		String[] styleid9 = { "M7", "123aaaa", "404" };
		String[] styleid10 = { "M7", null, "404" };

		Object[][] dataSet = new Object[][] { styleid1, styleid2, styleid3, styleid4, styleid5, styleid6, styleid7,
				styleid8, styleid9, styleid10 };
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 5, 5);
	}

	@DataProvider
	public Object[][] ApifyDataProvider_InvalidBrandStyleId(ITestContext testContext) {

		String[] styleid1 = { "Production", "aaaaaaa", "404" };
		String[] styleid2 = { "Production", "00000000", "200" };
		String[] styleid3 = { "Production", "", "404" };
		String[] styleid4 = { "Production", "123aaaa", "404" };
		String[] styleid5 = { "Production", null, "404" };

		String[] styleid6 = { "M7", "aaaaaaa", "404" };
		String[] styleid7 = { "M7", "00000000", "200" };
		String[] styleid8 = { "M7", "", "404" };
		String[] styleid9 = { "M7", "123aaaa", "404" };
		String[] styleid10 = { "M7", null, "404" };

		Object[][] dataSet = new Object[][] { styleid1, styleid2, styleid3, styleid4, styleid5, styleid6, styleid7,
				styleid8, styleid9, styleid10 };
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 5, 5);
	}

	@DataProvider
	public Object[][] ApifyDataProvider_InvalidRelatedStyleId(ITestContext testContext) {

		String[] styleid1 = { "Production", "aaaaaaa", "404" };
		String[] styleid2 = { "Production", "00000000", "200" };
		String[] styleid3 = { "Production", "", "404" };
		String[] styleid4 = { "Production", "123aaaa", "404" };
		String[] styleid5 = { "Production", null, "404" };

		String[] styleid6 = { "M7", "aaaaaaa", "404" };
		String[] styleid7 = { "M7", "00000000", "200" };
		String[] styleid8 = { "M7", "", "404" };
		String[] styleid9 = { "M7", "123aaaa", "404" };
		String[] styleid10 = { "M7", null, "404" };

		Object[][] dataSet = new Object[][] { styleid1, styleid2, styleid3, styleid4, styleid5, styleid6, styleid7,
				styleid8, styleid9, styleid10 };
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 5, 5);
	}

	@DataProvider
	public Object[][] ApifyDataProvider_InvalidOffersStyleId(ITestContext testContext) {

		String[] styleid1 = { "Production", "aaaaaaa", "404" };
		String[] styleid2 = { "Production", "00000000", "200" };
		String[] styleid3 = { "Production", "", "404" };
		String[] styleid4 = { "Production", "123aaaa", "404" };
		String[] styleid5 = { "Production", null, "404" };

		String[] styleid6 = { "M7", "aaaaaaa", "404" };
		String[] styleid7 = { "M7", "00000000", "200" };
		String[] styleid8 = { "M7", "", "404" };
		String[] styleid9 = { "M7", "123aaaa", "404" };
		String[] styleid10 = { "M7", null, "404" };

		Object[][] dataSet = new Object[][] { styleid1, styleid2, styleid3, styleid4, styleid5, styleid6, styleid7,
				styleid8, styleid9, styleid10 };
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 2, 5);
	}

	@DataProvider
	public Object[][] ApifyDataProvider_InvalidLikeSummaryStyleId(ITestContext testContext) {

		String[] styleid1 = { "Production", "aaaaaaa", "404" };
		String[] styleid2 = { "Production", "00000000", "200" };
		String[] styleid3 = { "Production", "", "404" };
		String[] styleid4 = { "Production", "123aaaa", "404" };
		String[] styleid5 = { "Production", null, "404" };

		String[] styleid6 = { "M7", "aaaaaaa", "404" };
		String[] styleid7 = { "M7", "00000000", "200" };
		String[] styleid8 = { "M7", "", "404" };
		String[] styleid9 = { "M7", "123aaaa", "404" };
		String[] styleid10 = { "M7", null, "404" };

		Object[][] dataSet = new Object[][] { styleid1, styleid2, styleid3, styleid4, styleid5, styleid6, styleid7,
				styleid8, styleid9, styleid10 };
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 5, 5);
	}

	@DataProvider
	public Object[][] ApifyDataProvider_outOfStockStyleId(ITestContext testContext) {

		String[] styleid1 = { "Production", "123458"};

		String[] styleid2 = { "M7", "123458"};

		Object[][] dataSet = new Object[][] { styleid1, styleid2 };
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 1);
	}

	@DataProvider
	public Object[][] ApifyDataProvider_ValidInnerWearStyleId(ITestContext testContext) {
		Innerwears = Helper.performKeywordSearchAndReturnResultsAsList("innerwear");
		Brief = Helper.performKeywordSearchAndReturnResultsAsList("brief");
		Trunks = Helper.performKeywordSearchAndReturnResultsAsList("trunks");
		String[] styleid1 = { "Production", getRandomStyle(Innerwears)};
		String[] styleid2 = { "Production", getRandomStyle(Brief)};
		String[] styleid3 = { "Production", getRandomStyle(Trunks)};

		String[] styleid4 = { "M7", getRandomStyle(Innerwears)};
		String[] styleid5 = { "M7", getRandomStyle(Brief)};
		String[] styleid6 = { "M7", getRandomStyle(Trunks)};

		Object[][] dataSet = new Object[][] { styleid1, styleid2, styleid3, styleid4, styleid5, styleid6 };
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 3, 3);
	}

	@DataProvider
	public Object[][] Apify_ServiceiabilityStyleId(ITestContext testContext) {
		String[] Data1 = { "Production", getRandomStyle(PumaStyles), "560068" };
		String[] Data2 = { "Production", getRandomStyle(Jeans), "110110" };
		String[] Data3 = { "M7", getRandomStyle(Shoes), "560068" };
		String[] Data4 = { "M7", getRandomStyle(NikeStyles), "110110" };

		Object DataSet[][] = new Object[][] { Data1, Data2, Data3, Data4 };
		return Toolbox.returnReducedDataSet(Env, DataSet, testContext.getIncludedGroups(), 2, 2);
	}

	@DataProvider
	public Object[][] Apify_InvalidPincodeServiceiabilityStyleId(ITestContext testContext) {
		String[] styleid1 = { "Production", "aaaaaaa", "400" };
		String[] styleid2 = { "Production", "", "400" };
		String[] styleid3 = { "Production", "123aaaa", "400" };
		String[] styleid4 = { "Production", null, "400" };

		String[] styleid5 = { "M7", "aaaaaaa", "400" };
		String[] styleid6 = { "M7", "", "400" };
		String[] styleid7 = { "M7", "123aaaa", "400" };
		String[] styleid8 = { "M7", null, "400" };

		Object[][] dataSet = new Object[][] { styleid1, styleid2, styleid3, styleid4, styleid5, styleid6, styleid7,
				styleid8 };
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 4, 4);
	}

	@DataProvider
	public Object[][] ApifyDataProvider_NonServiceableStyleId(ITestContext testContext) {
		String[] Data1 = { "Production", getRandomStyle(PumaStyles), "12345" };
		String[] Data2 = { "Production", getRandomStyle(Jeans), "12345" };
		String[] Data3 = { "M7", getRandomStyle(PumaStyles), "12345" };
		String[] Data4 = { "M7", getRandomStyle(Jeans), "12345" };

		Object DataSet[][] = new Object[][] { Data1, Data2, Data3, Data4 };
		return Toolbox.returnReducedDataSet(Env, DataSet, testContext.getIncludedGroups(), 2, 2);
	}

	@DataProvider
	public Object[][] ApifyDataProvider_InvalidRecommendationStyleId(ITestContext testContext) {

		String[] styleid1 = { "Production", "aaaaaaa", "404" };
		String[] styleid2 = { "Production", "00000000", "200" };
		String[] styleid3 = { "Production", "", "404" };
		String[] styleid4 = { "Production", "123aaaa", "404" };
		String[] styleid5 = { "Production", null, "404" };

		String[] styleid6 = { "M7", "aaaaaaa", "404" };
		String[] styleid7 = { "M7", "00000000", "200" };
		String[] styleid8 = { "M7", "", "404" };
		String[] styleid9 = { "M7", "123aaaa", "404" };
		String[] styleid10 = { "M7", null, "404" };

		Object[][] dataSet = new Object[][] { styleid1, styleid2, styleid3, styleid4, styleid5, styleid6, styleid7,
				styleid8, styleid9, styleid10 };
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 5, 5);
	}
	
	@DataProvider
	public Object[][] ApifyDataProvider_ValidSizeRelatedRecommendationEnabledState(ITestContext testContext) {
		String[] Data1 = { "Production", getRandomStyle(PumaStyles), "default" };
		String[] Data2 = { "Production", getRandomStyle(PumaStyles), "enabled" };
		String[] Data3 = { "Production", getRandomStyle(Jeans), "disabled" };
		String[] Data4 = { "M7", getRandomStyle(Shoes), "default" };
		String[] Data5 = { "M7", getRandomStyle(PumaStyles), "enabled" };
		String[] Data6 = { "M7", getRandomStyle(NikeStyles), "disabled" };

		Object DataSet[][] = new Object[][] { Data1, Data2, Data3, Data4, Data5, Data6 };
		return Toolbox.returnReducedDataSet(Env, DataSet, testContext.getIncludedGroups(), 3, 3);
	}
	
	@DataProvider
	public Object[][] ApifyDataProvider_ValidSizeRecommendation(ITestContext testContext) {
		String[] Data1 = { "Production", getRandomStyle(PumaStyles), "default", "null" };
		String[] Data2 = { "Production", getRandomStyle(PumaStyles), "enabled", "null" };
		String[] Data3 = { "Production", getRandomStyle(PumaStyles), "enabled", "f630073e.dadd.4497.84fb.a10279033f9bnHIyWGEgt6" };
		String[] Data4 = { "Production", getRandomStyle(Jeans), "disabled", "f630073e.dadd.4497.84fb.a10279033f9bnHIyWGEgt6" };
		String[] Data5 = { "Production", getRandomStyle(Jeans), "disabled", "null" };
		String[] Data6 = { "M7", getRandomStyle(Shoes), "default", "null" };
		String[] Data7 = { "M7", getRandomStyle(PumaStyles), "enabled", "null" };
		String[] Data8 = { "M7", getRandomStyle(PumaStyles), "enabled", "f630073e.dadd.4497.84fb.a10279033f9bnHIyWGEgt6" };
		String[] Data9 = { "M7", getRandomStyle(NikeStyles), "disabled", "f630073e.dadd.4497.84fb.a10279033f9bnHIyWGEgt6" };
		String[] Data10 = { "M7", getRandomStyle(NikeStyles), "disabled", "null" };

		Object DataSet[][] = new Object[][] { Data1, Data2, Data3, Data4, Data5, Data6, Data7, Data8, Data9, Data10 };
		return Toolbox.returnReducedDataSet(Env, DataSet, testContext.getIncludedGroups(), 5, 5);
	}

	@DataProvider
	public Object[][] ApifyDataProvider_InValidSizeRecommendation(ITestContext testContext) {
		String[] Data1 = { "Production", getRandomStyle(PumaStyles), "" };
		String[] Data2 = { "Production", getRandomStyle(Jeans), null };
		String[] Data3 = { "M7", getRandomStyle(Shoes), "" };
		String[] Data4 = { "M7", getRandomStyle(NikeStyles), null };

		Object DataSet[][] = new Object[][] { Data1, Data2, Data3, Data4 };
		return Toolbox.returnReducedDataSet(Env, DataSet, testContext.getIncludedGroups(), 2, 2);
	}
	
	@DataProvider
	public Object[][] ApifyDataProvider_ValidPersonalCareStyleId(ITestContext testContext) {
		//Innerwears = Helper.performKeywordSearchAndReturnResultsAsList("lips");
		String lipsStyleId  = "1638668";
		String lipsStyleId1  = "1638669";
		String lipsStyleId2  = "1638670";
		String[] styleid1 = { "Production", lipsStyleId};
		String[] styleid2 = { "Production", lipsStyleId1};
		String[] styleid3 = { "Production", lipsStyleId2};

		String[] styleid4 = { "M7", lipsStyleId};
		String[] styleid5 = { "M7", lipsStyleId1};
		String[] styleid6 = { "M7", lipsStyleId2};

		Object[][] dataSet = new Object[][] { styleid1, styleid2, styleid3, styleid4, styleid5, styleid6 };
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 3, 3);
	}
	
	@DataProvider
	public Object[][] ApifyDataProvider_ValidStyleIdForSbp(ITestContext testContext) {
		
		String[] styleid1 = { "Production", getRandomStyle(HandBags), "false"};
		String[] styleid2 = { "Production", "1600930", "true"};
		
		String[] styleid3 = { "M7", getRandomStyle(HandBags)};
		String[] styleid4 = { "M7", "1600930", "true", "false"};
		

		Object[][] dataSet = new Object[][] { styleid1, styleid2, styleid3, styleid4, };
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 2, 2);
	}
}
