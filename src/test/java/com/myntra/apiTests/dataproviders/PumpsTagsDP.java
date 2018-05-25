package com.myntra.apiTests.dataproviders;

import com.myntra.apiTests.portalservices.commons.CommonUtils;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;
import com.myntra.lordoftherings.Configuration;
import com.myntra.lordoftherings.Toolbox;

public class PumpsTagsDP extends CommonUtils
{
	String Env;
	Configuration con = new Configuration("./Data/configuration");

	public PumpsTagsDP() {
		if (System.getenv("ENVIRONMENT") == null)
			Env = con.GetTestEnvironemnt().toString();
		else
			Env = System.getenv("ENVIRONMENT");

	}

	@DataProvider
	public Object[][] CreateSingleTag(ITestContext testContext) 
	{
		String[] tag1 = new String[]{"Fox7","RockStar","brand","1021","Tag added successfully","SUCCESS"};
		String[] tag2 = new String[]{"Fox7","1234","brand","1021","Tag added successfully","SUCCESS"};
		String[] tag3 = new String[]{"Fox7","@#$%^^^","brand","1021","Tag added successfully","SUCCESS"};
		String[] tag4 = new String[]{"Fox7","","","54","Error occurred while inserting/processing data","ERROR"};
		String[] tag5 = new String[]{"Fox7","RockStar","brand","1021","Tag added successfully","SUCCESS"};
		String[] tag6 = new String[]{"Fox7","RockStar","1234","1021","Tag added successfully","SUCCESS"};
		String[] tag7 = new String[]{"Fox7","RockStar","@#$%^^^","1021","Tag added successfully","SUCCESS"};
		String[] tag8 = new String[]{"Fox7","RockStar","","54","Error occurred while inserting/processing data","ERROR"};
		String[] tag9 = new String[]{"Fox7","","brand","54","Error occurred while inserting/processing data","ERROR"};
		String[] tag10= new String[]{"Fox7","1234","","54","Error occurred while inserting/processing data","ERROR"};
		String[] tag11 = new String[]{"Fox7",null,"brand","54","Error occurred while inserting/processing data","ERROR"};
		String[] tag12= new String[]{"Fox7","1234",null,"54","Error occurred while inserting/processing data","ERROR"};

		String[] tag13 = new String[]{"Stage","RockStar","brand","1021","Tag added successfully","SUCCESS"};
		String[] tag14 = new String[]{"Stage","1234","brand","1021","Tag added successfully","SUCCESS"};
		String[] tag15 = new String[]{"Stage","@#$%^^^","brand","1021","Tag added successfully","SUCCESS"};
		String[] tag16 = new String[]{"Stage","","","54","Error occurred while inserting/processing data","ERROR"};
		String[] tag17 = new String[]{"Stage","RockStar","brand","1021","Tag added successfully","SUCCESS"};
		String[] tag18 = new String[]{"Stage","RockStar","1234","1021","Tag added successfully","SUCCESS"};
		String[] tag19 = new String[]{"Stage","RockStar","@#$%^^^","1021","Tag added successfully","SUCCESS"};
		String[] tag20 = new String[]{"Stage","RockStar","","54","Error occurred while inserting/processing data","ERROR"};
		String[] tag21 = new String[]{"Stage","","brand","54","Error occurred while inserting/processing data","ERROR"};
		String[] tag22= new String[]{"Stage","1234","","54","Error occurred while inserting/processing data","ERROR"};
		String[] tag23 = new String[]{"Stage",null,"brand","54","Error occurred while inserting/processing data","ERROR"};
		String[] tag24= new String[]{"Stage","1234",null,"54","Error occurred while inserting/processing data","ERROR"};

		String[] tag25 = new String[]{"sfqa","RockStar","brand","1021","Tag added successfully","SUCCESS"};
		String[] tag26 = new String[]{"sfqa","1234","brand","1021","Tag added successfully","SUCCESS"};
		String[] tag27 = new String[]{"sfqa","@#$%^^^","brand","1021","Tag added successfully","SUCCESS"};
		String[] tag28 = new String[]{"sfqa","","","54","Error occurred while inserting/processing data","ERROR"};
		String[] tag29 = new String[]{"sfqa","RockStar","brand","1021","Tag added successfully","SUCCESS"};
		String[] tag30 = new String[]{"sfqa","RockStar","1234","1021","Tag added successfully","SUCCESS"};
		String[] tag31 = new String[]{"sfqa","RockStar","@#$%^^^","1021","Tag added successfully","SUCCESS"};
		String[] tag32 = new String[]{"sfqa","RockStar","","54","Error occurred while inserting/processing data","ERROR"};
		String[] tag33 = new String[]{"sfqa","","brand","54","Error occurred while inserting/processing data","ERROR"};
		String[] tag34 = new String[]{"sfqa","1234","","54","Error occurred while inserting/processing data","ERROR"};
		String[] tag35 = new String[]{"sfqa",null,"brand","54","Error occurred while inserting/processing data","ERROR"};
		String[] tag36 = new String[]{"sfqa","1234",null,"54","Error occurred while inserting/processing data","ERROR"};


		Object[][] dataSet = new Object[][] {tag1,tag2,tag3,tag4,tag5,tag6,tag7,tag8,tag9,tag10,tag11,tag12,tag13,tag14,tag15,tag16,tag17,tag18,tag19,tag20,tag21,tag22,tag23,tag24,
				tag25, tag26, tag28, tag29, tag30, tag32, tag33, tag34, tag35, tag36};
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 24, 24);
	}
	
	@DataProvider
	public Object[][] CreateSingleTag_DuplicateTagCreation(ITestContext testContext) 
	{
		String[] tag1 = new String[]{"Fox7","RockStar","brand","1021","Tag added successfully","SUCCESS"};
		String[] tag2 = new String[]{"Fox7","1234","brand","1021","Tag added successfully","SUCCESS"};
		String[] tag3 = new String[]{"Fox7","@#$%^^^","brand","1021","Tag added successfully","SUCCESS"};
		String[] tag5 = new String[]{"Fox7","RockStar","brand","1021","Tag added successfully","SUCCESS"};
		String[] tag6 = new String[]{"Fox7","RockStar","1234","1021","Tag added successfully","SUCCESS"};
		String[] tag7 = new String[]{"Fox7","RockStar","@#$%^^^","1021","Tag added successfully","SUCCESS"};

		String[] tag8 = new String[]{"stage","RockStar","brand","1021","Tag added successfully","SUCCESS"};
		String[] tag9 = new String[]{"stage","1234","brand","1021","Tag added successfully","SUCCESS"};
		String[] tag10 = new String[]{"stage","@#$%^^^","brand","1021","Tag added successfully","SUCCESS"};
		String[] tag11= new String[]{"stage","RockStar","brand","1021","Tag added successfully","SUCCESS"};
		String[] tag12= new String[]{"stage","RockStar","1234","1021","Tag added successfully","SUCCESS"};
		String[] tag13= new String[]{"stage","RockStar","@#$%^^^","1021","Tag added successfully","SUCCESS"};

		String[] tag14 = new String[]{"sfqa","RockStar","brand","1021","Tag added successfully","SUCCESS"};
		String[] tag15 = new String[]{"sfqa","1234","brand","1021","Tag added successfully","SUCCESS"};
		String[] tag16 = new String[]{"sfqa","@#$%^^^","brand","1021","Tag added successfully","SUCCESS"};
		String[] tag17= new String[]{"sfqa","RockStar","brand","1021","Tag added successfully","SUCCESS"};
		String[] tag18= new String[]{"sfqa","RockStar","1234","1021","Tag added successfully","SUCCESS"};
		String[] tag19= new String[]{"sfqa","RockStar","@#$%^^^","1021","Tag added successfully","SUCCESS"};

		Object[][] dataSet = new Object[][] {tag1,tag2,tag3,tag5,tag6,tag7,tag8,tag9,tag10,tag11,tag12,tag13, tag14, tag15, tag17, tag18};
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 12, 12);
	}
	@DataProvider
	public Object[][] FetchDuplicateTags(ITestContext testContext) 
	{
		String[] tag1 = new String[]{"Fox7","RockStar","brand","1021","Tag added successfully","SUCCESS"};
		String[] tag2 = new String[]{"Fox7","1234","brand","1021","Tag added successfully","SUCCESS"};
		String[] tag3 = new String[]{"Fox7","RockStar","brand","1021","Tag added successfully","SUCCESS"};
		String[] tag4 = new String[]{"Fox7","RockStar","1234","1021","Tag added successfully","SUCCESS"};
		String[] tag5 = new String[]{"Fox7","RockStar","@#$%^^^","1021","Tag added successfully","SUCCESS"};

		String[] tag6 = new String[]{"stage","RockStar","brand","1021","Tag added successfully","SUCCESS"};
		String[] tag7 = new String[]{"stage","1234","brand","1021","Tag added successfully","SUCCESS"};
		String[] tag8 = new String[]{"stage","RockStar","brand","1021","Tag added successfully","SUCCESS"};
		String[] tag9 = new String[]{"stage","RockStar","1234","1021","Tag added successfully","SUCCESS"};
		String[] tag10 = new String[]{"stage","RockStar","@#$%^^^","1021","Tag added successfully","SUCCESS"};

		String[] tag11 = new String[]{"sfqa","RockStar","brand","1021","Tag added successfully","SUCCESS"};
		String[] tag12 = new String[]{"sfqa","1234","brand","1021","Tag added successfully","SUCCESS"};
		String[] tag13 = new String[]{"sfqa","RockStar","brand","1021","Tag added successfully","SUCCESS"};
		String[] tag14 = new String[]{"sfqa","RockStar","1234","1021","Tag added successfully","SUCCESS"};
		String[] tag15 = new String[]{"sfqa","RockStar","@#$%^^^","1021","Tag added successfully","SUCCESS"};

		Object[][] dataSet = new Object[][] {tag1,tag2,tag3,tag4,tag5,tag6,tag7,tag8,tag9,tag10, tag11, tag12, tag13, tag14};
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 10, 10);
	}

	@DataProvider
	public Object[][] CreateSingleTag_Schema(ITestContext testContext) 
	{
		String[] tag1 = new String[]{"Fox7","RockStar","brand","1021","Tag added successfully","SUCCESS"};
		String[] tag2 = new String[]{"Fox7","1234","brand","1021","Tag added successfully","SUCCESS"};
		String[] tag3 = new String[]{"Fox7","@#$%^^^","brand","1021","Tag added successfully","SUCCESS"};
		String[] tag5 = new String[]{"Fox7","RockStar","brand","1021","Tag added successfully","SUCCESS"};
		String[] tag6 = new String[]{"Fox7","RockStar","1234","1021","Tag added successfully","SUCCESS"};
		String[] tag7 = new String[]{"Fox7","RockStar","@#$%^^^","1021","Tag added successfully","SUCCESS"};

		String[] tag8 = new String[]{"stage","RockStar","brand","1021","Tag added successfully","SUCCESS"};
		String[] tag9 = new String[]{"stage","1234","brand","1021","Tag added successfully","SUCCESS"};
		String[] tag10 = new String[]{"stage","@#$%^^^","brand","1021","Tag added successfully","SUCCESS"};
		String[] tag11= new String[]{"stage","RockStar","brand","1021","Tag added successfully","SUCCESS"};
		String[] tag12= new String[]{"stage","RockStar","1234","1021","Tag added successfully","SUCCESS"};
		String[] tag13= new String[]{"stage","RockStar","@#$%^^^","1021","Tag added successfully","SUCCESS"};

		String[] tag14 = new String[]{"sfqa","RockStar","brand","1021","Tag added successfully","SUCCESS"};
		String[] tag15 = new String[]{"sfqa","1234","brand","1021","Tag added successfully","SUCCESS"};
		String[] tag16 = new String[]{"sfqa","@#$%^^^","brand","1021","Tag added successfully","SUCCESS"};
		String[] tag17 = new String[]{"sfqa","RockStar","brand","1021","Tag added successfully","SUCCESS"};
		String[] tag18 = new String[]{"sfqa","RockStar","1234","1021","Tag added successfully","SUCCESS"};
		String[] tag19 = new String[]{"sfqa","RockStar","@#$%^^^","1021","Tag added successfully","SUCCESS"};

		Object[][] dataSet = new Object[][] {tag1,tag2,tag3,tag5,tag6,tag7,tag8,tag9,tag10,tag11,tag12,tag13, tag14, tag15, tag17, tag18};
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 12, 12);
	}
	
	
	@DataProvider
	public Object[][] FindTagByName(ITestContext testContext) 
	{
		String[] tag1 = new String[]{"Fox7","Benettonbrand","brand"};
		String[] tag2 = new String[]{"Fox7","WeddingOccassion","Occassion"};
		String[] tag3 = new String[]{"Fox7","ShirtsArticle","articletype"};
		String[] tag5 = new String[]{"Fox7","SportsCategory","Category"};
		String[] tag6 = new String[]{"Fox7","ethnicTheme","theme"};
		String[] tag7 = new String[]{"Fox7","EossSale","sale"};
		String[] tag8 = new String[]{"Fox7","FrontStore","Store"};
		String[] tag9 = new String[]{"Fox7","PromoOffer","promo"};
		String[] tag10 = new String[]{"Fox7","CustomerEngagement","Engagement"};
		String[] tag11 = new String[]{"Fox7","Game","game"};
		String[] tag12 = new String[]{"Fox7","SuperHeroTrend","trend"};

		String[] tag13 = new String[]{"stage","Benettonbrand","brand"};
		String[] tag14 = new String[]{"stage","WeddingOccassion","Occassion"};
		String[] tag15 = new String[]{"stage","ShirtsArticle","articletype"};
		String[] tag16 = new String[]{"stage","SportsCategory","Category"};
		String[] tag17 = new String[]{"stage","ethnicTheme","theme"};
		String[] tag18 = new String[]{"stage","EossSale","sale"};
		String[] tag19 = new String[]{"stage","FrontStore","Store"};
		String[] tag20 = new String[]{"stage","PromoOffer","promo"};
		String[] tag21 = new String[]{"stage","CustomerEngagement","Engagement"};
		String[] tag22 = new String[]{"stage","Game","game"};
		String[] tag23 = new String[]{"stage","SuperHeroTrend","trend"};

		String[] tag24 = new String[]{"sfqa","Benettonbrand","brand"};
		String[] tag25 = new String[]{"sfqa","WeddingOccassion","Occassion"};
		String[] tag26 = new String[]{"sfqa","ShirtsArticle","articletype"};
		String[] tag27 = new String[]{"sfqa","SportsCategory","Category"};
		String[] tag28 = new String[]{"sfqa","ethnicTheme","theme"};
		String[] tag29 = new String[]{"sfqa","EossSale","sale"};
		String[] tag30 = new String[]{"sfqa","FrontStore","Store"};
		String[] tag31 = new String[]{"sfqa","PromoOffer","promo"};
		String[] tag32 = new String[]{"sfqa","CustomerEngagement","Engagement"};
		String[] tag33 = new String[]{"sfqa","Game","game"};
		String[] tag34 = new String[]{"sfqa","SuperHeroTrend","trend"};

		Object[][] dataSet = new Object[][] {tag1,tag2,tag3,tag5,tag6,tag7,tag8,tag9,tag10,tag11,tag12,tag13,tag14,tag15,tag16,tag17,tag18,tag19,tag20,tag21,tag22,tag23,
				tag24, tag25, tag26, tag27, tag28, tag29, tag30, tag31, tag32, tag33, tag34};
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 22, 22);
	}
	
	@DataProvider
	public Object[][] FetchByType(ITestContext testContext) 
	{
		String[] tag1 = new String[]{"Fox7","Benettonbrand","brand"};
		String[] tag2 = new String[]{"Fox7","WeddingOccassion","Occassion"};
		String[] tag3 = new String[]{"Fox7","ShirtsArticle","articletype"};
		String[] tag5 = new String[]{"Fox7","SportsCategory","Category"};
		String[] tag6 = new String[]{"Fox7","ethnicTheme","theme"};
		String[] tag7 = new String[]{"Fox7","EossSale","sale"};
		String[] tag8 = new String[]{"Fox7","FrontStore","Store"};
		String[] tag9 = new String[]{"Fox7","PromoOffer","promo"};
		String[] tag10 = new String[]{"Fox7","CustomerEngagement","Engagement"};
		String[] tag11 = new String[]{"Fox7","Game","game"};
		String[] tag12 = new String[]{"Fox7","SuperHeroTrend","trend"};

		String[] tag13 = new String[]{"stage","Benettonbrand","brand"};
		String[] tag14 = new String[]{"stage","WeddingOccassion","Occassion"};
		String[] tag15= new String[]{"stage","ShirtsArticle","articletype"};
		String[] tag16= new String[]{"stage","SportsCategory","Category"};
		String[] tag17= new String[]{"stage","ethnicTheme","theme"};
		String[] tag18= new String[]{"stage","EossSale","sale"};
		String[] tag19= new String[]{"stage","FrontStore","Store"};
		String[] tag20= new String[]{"stage","PromoOffer","promo"};
		String[] tag21 = new String[]{"stage","CustomerEngagement","Engagement"};
		String[] tag22 = new String[]{"stage","Game","game"};
		String[] tag23 = new String[]{"stage","SuperHeroTrend","trend"};

		String[] tag24 = new String[]{"sfqa","Benettonbrand","brand"};
		String[] tag25 = new String[]{"sfqa","WeddingOccassion","Occassion"};
		String[] tag26 = new String[]{"sfqa","ShirtsArticle","articletype"};
		String[] tag27 = new String[]{"sfqa","SportsCategory","Category"};
		String[] tag28 = new String[]{"sfqa","ethnicTheme","theme"};
		String[] tag29 = new String[]{"sfqa","EossSale","sale"};
		String[] tag30 = new String[]{"sfqa","FrontStore","Store"};
		String[] tag31 = new String[]{"sfqa","PromoOffer","promo"};
		String[] tag32 = new String[]{"sfqa","CustomerEngagement","Engagement"};
		String[] tag33 = new String[]{"sfqa","Game","game"};
		String[] tag34 = new String[]{"sfqa","SuperHeroTrend","trend"};

		Object[][] dataSet = new Object[][] {tag1,tag2,tag3,tag5,tag6,tag7,tag8,tag9,tag10,tag11,tag12,tag13,tag14,tag15,tag16,tag17,tag18,tag19,tag20,tag21,tag22,tag23,
				tag24, tag25, tag26, tag27, tag28, tag29, tag30, tag31, tag32, tag33, tag34};
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 22, 22);
	}
	
	@DataProvider
	public Object[][] FindTagByName_InvalidNames(ITestContext testContext) 
	{
		String[] tag1 = new String[]{"Fox7","","400"};
		String[] tag2 = new String[]{"Fox7","?","400"};
		String[] tag3 = new String[]{"Fox7","&","200"};

		String[] tag4 = new String[]{"stage","","400"};
		String[] tag5 = new String[]{"stage","?","400"};
		String[] tag6 = new String[]{"stage","&","200"};

		String[] tag7 = new String[]{"sfqa","","400"};
		String[] tag8 = new String[]{"sfqa","?","400"};
		String[] tag9 = new String[]{"sfqa","&","200"};

		Object[][] dataSet = new Object[][] {tag1,tag2,tag3,tag4,tag5,tag6, tag7, tag8, tag9};
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 6, 6);
	}
	
	@DataProvider
	public Object[][] CreateMultipleTags(ITestContext testContext) 
	{
		String[] tag1 = new String[]{"Fox7","RockStar","brand","1021","Tag added successfully","SUCCESS"};
		String[] tag2 = new String[]{"Fox7","1234","brand","1021","Tag added successfully","SUCCESS"};
		String[] tag3 = new String[]{"Fox7","@#$%^^^","brand","1021","Tag added successfully","SUCCESS"};
		String[] tag4 = new String[]{"Fox","     ","brand","1021","Tag added successfully","SUCCESS"};
		String[] tag5 = new String[]{"Fox7","RockStar","brand","1021","Tag added successfully","SUCCESS"};
		String[] tag6 = new String[]{"Fox7","RockStar","1234","1021","Tag added successfully","SUCCESS"};
		String[] tag7 = new String[]{"Fox7","RockStar","@#$%^^^","1021","Tag added successfully","SUCCESS"};
		String[] tag8 = new String[]{"Fox","RockStar","      ","1021","Tag added successfully","SUCCESS"};
		String[] tag9 = new String[]{"Fox","","brand","54","Error occurred while inserting/processing data","ERROR"};
		String[] tag10= new String[]{"Fox","1234","","54","Error occurred while inserting/processing data","ERROR"};

		String[] tag11 = new String[]{"stage","RockStar","brand","1021","Tag added successfully","SUCCESS"};
		String[] tag12 = new String[]{"stage","1234","brand","1021","Tag added successfully","SUCCESS"};
		String[] tag13 = new String[]{"stage","@#$%^^^","brand","1021","Tag added successfully","SUCCESS"};
		String[] tag14 = new String[]{"stage","     ","brand","1021","Tag added successfully","SUCCESS"};
		String[] tag15 = new String[]{"stage","RockStar","brand","1021","Tag added successfully","SUCCESS"};
		String[] tag16 = new String[]{"stage","RockStar","1234","1021","Tag added successfully","SUCCESS"};
		String[] tag17 = new String[]{"stage","RockStar","@#$%^^^","1021","Tag added successfully","SUCCESS"};
		String[] tag18 = new String[]{"stage","RockStar","      ","1021","Tag added successfully","SUCCESS"};
		String[] tag19 = new String[]{"stage","","brand","54","Error occurred while inserting/processing data","ERROR"};
		String[] tag20= new String[]{"stage","1234","","54","Error occurred while inserting/processing data","ERROR"};

		String[] tag21 = new String[]{"sfqa","RockStar","brand","1021","Tag added successfully","SUCCESS"};
		String[] tag22 = new String[]{"sfqa","1234","brand","1021","Tag added successfully","SUCCESS"};
		//String[] tag23 = new String[]{"sfqa","@#$%^^^","brand","1021","Tag added successfully","SUCCESS"};
		String[] tag24 = new String[]{"sfqa","     ","brand","1021","Tag added successfully","SUCCESS"};
		String[] tag25 = new String[]{"sfqa","RockStar","brand","1021","Tag added successfully","SUCCESS"};
		String[] tag26 = new String[]{"sfqa","RockStar","1234","1021","Tag added successfully","SUCCESS"};
		//String[] tag27 = new String[]{"sfqa","RockStar","@#$%^^^","1021","Tag added successfully","SUCCESS"};
		String[] tag28 = new String[]{"sfqa","RockStar","      ","1021","Tag added successfully","SUCCESS"};
		String[] tag29 = new String[]{"sfqa","","brand","54","Error occurred while inserting/processing data","ERROR"};
		String[] tag30 = new String[]{"sfqa","1234","","54","Error occurred while inserting/processing data","ERROR"};
		
		Object[][] dataSet = new Object[][] {tag1,tag2,tag3,tag4,tag5,tag6,tag7,tag8,tag9,tag10,tag11,tag12,tag13,tag14,tag15,tag16,tag17,tag18,tag19,tag20,
				tag21, tag22, tag24, tag25, tag26, tag28, tag29, tag30};
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 20, 20);
	}

	@DataProvider
	public Object[][] validFetchTypeTag(ITestContext testContext) {
		String[] tag1 = new String[]{"Fox7", "brand"};
		String[] tag2 = new String[]{"Fox7", "123"};

		String[] tag3 = new String[]{"stage", "brand"};
		String[] tag4 = new String[]{"stage", "123"};

		String[] tag5 = new String[]{"sfqa", "brand"};
		String[] tag6 = new String[]{"sfqa", "123"};

		Object[][] dataSet = new Object[][] {tag1,tag2,tag3,tag4, tag5, tag6};
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 4, 4);
	}

	@DataProvider
	public Object[][] invalidFetchTypeTag(ITestContext testContext) {
		String[] tag1 = new String[] { "Fox7", "", "404" };
		String[] tag2 = new String[] { "Fox7", "null", "404" };
		String[] tag3 = new String[] { "Fox7", "?", "404" };
		String[] tag4 = new String[] { "Fox7", "&", "200" };
		String[] tag5 = new String[] { "Fox7", "^^^^", "200" };
		String[] tag6 = new String[] { "stage", "", "404" };
		String[] tag7 = new String[] { "stage", "null", "404" };
		String[] tag8 = new String[] { "stage", "?", "404" };
		String[] tag9 = new String[] { "stage", "&", "200" };
		String[] tag10 = new String[] { "stage", "^^^^", "200" };

		String[] tag11 = new String[] { "sfqa", "", "404" };
		String[] tag12 = new String[] { "sfqa", "null", "404" };
		String[] tag13 = new String[] { "sfqa", "?", "404" };
		String[] tag14 = new String[] { "sfqa", "&", "200" };
		String[] tag15 = new String[] { "sfqa", "^^^^", "200" };

		Object[][] dataSet = new Object[][] { tag1, tag2, tag3, tag4,tag6,tag7,tag8,tag9, tag11, tag12, tag13, tag14 };
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 3, 5);
	}

	@DataProvider
	public Object[][] validFetchKeyTag(ITestContext testContext) {
		String[] tag1 = new String[] { "Fox7", "RockStar", "brand" };
		String[] tag2 = new String[] { "Fox7", "1234", "brand" };
		String[] tag3 = new String[] { "Fox7", "RockStar", "1234" };


		String[] tag4 = new String[] { "stage", "RockStar", "brand" };
		String[] tag5 = new String[] { "stage", "1234", "brand" };
		String[] tag6 = new String[] { "stage", "RockStar", "1234" };

		String[] tag7 = new String[] { "sfqa", "RockStar", "brand" };
		String[] tag8 = new String[] { "sfqa", "1234", "brand" };
		String[] tag9 = new String[] { "sfqa", "RockStar", "1234" };

		Object[][] dataSet = new Object[][] { tag1, tag2, tag3, tag4, tag5, tag6, tag7, tag8, tag9 };
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 3);
	}

	@DataProvider
	public Object[][] invalidFetchKeyTag(ITestContext testContext) {
		String[] tag1 = new String[] { "Fox7", "", "", "404" };
		String[] tag2 = new String[] { "Fox7", "", "brand", "404" };
		String[] tag3 = new String[] { "Fox7", "RockStar", "", "200" };
		String[] tag4 = new String[] { "Fox7", "null", "null", "200" };
		String[] tag5 = new String[] { "Fox7", "null", "brand", "200" };
		String[] tag6 = new String[] { "Fox7", "RockStar", "null", "200" };
		String[] tag7 = new String[] { "Fox7", "?", "?", "404" };
		String[] tag8 = new String[] { "Fox7", "&", "&", "200" };
		String[] tag9 = new String[] { "Fox7", "^^^^", "^^^^", "200" };
		String[] tag10 = new String[] { "Fox7", "?", "brand", "404" };
		String[] tag11 = new String[] { "Fox7", "RockStar", "?", "200" };
		String[] tag12 = new String[] { "Fox7", "&", "brand", "200" };
		String[] tag13 = new String[] { "Fox7", "RockStar", "&", "200" };
		String[] tag14 = new String[] { "Fox7", "^^^^", "brand", "200" };

		String[] tag15 = new String[] { "stage", "", "", "404" };
		String[] tag16 = new String[] { "stage", "", "brand", "404" };
		String[] tag17= new String[] { "stage", "RockStar", "", "200" };
		String[] tag18= new String[] { "stage", "null", "null", "200" };
		String[] tag19= new String[] { "stage", "null", "brand", "200" };
		String[] tag20= new String[] { "stage", "RockStar", "null", "200" };
		String[] tag21= new String[] { "stage", "?", "?", "404" };
		String[] tag22= new String[] { "stage", "&", "&", "200" };
		String[] tag23= new String[] { "stage", "^^^^", "^^^^", "200" };
		String[] tag24 = new String[] { "stage", "?", "brand", "404" };
		String[] tag25 = new String[] { "stage", "RockStar", "?", "200" };
		String[] tag26 = new String[] { "stage", "&", "brand", "200" };
		String[] tag27 = new String[] { "stage", "RockStar", "&", "200" };
		String[] tag28 = new String[] { "stage", "^^^^", "brand", "200" };

		String[] tag29 = new String[] { "sfqa", "", "", "404" };
		String[] tag30 = new String[] { "sfqa", "", "brand", "404" };
		String[] tag31= new String[] { "sfqa", "RockStar", "", "200" };
		String[] tag32= new String[] { "sfqa", "null", "null", "200" };
		String[] tag33= new String[] { "sfqa", "null", "brand", "200" };
		String[] tag34= new String[] { "sfqa", "RockStar", "null", "200" };
		String[] tag35= new String[] { "sfqa", "?", "?", "404" };
		String[] tag36= new String[] { "sfqa", "&", "&", "200" };
		//String[] tag37= new String[] { "sfqa", "^^^^", "^^^^", "200" };
		String[] tag38 = new String[] { "sfqa", "?", "brand", "404" };
		String[] tag39 = new String[] { "sfqa", "RockStar", "?", "200" };
		String[] tag40 = new String[] { "sfqa", "&", "brand", "200" };
		String[] tag41 = new String[] { "sfqa", "RockStar", "&", "200" };
		//String[] tag42 = new String[] { "sfqa", "^^^^", "brand", "200" };

		Object[][] dataSet = new Object[][] { tag1, tag2, tag3, tag4, tag5, tag6, tag7, tag8, tag10, tag11, tag12,
				tag13 ,tag15,tag16,tag17,tag18,tag19,tag20,tag21,tag22,tag24,tag25,tag26,tag27, tag29,
				tag30, tag31, tag32, tag33, tag34, tag35, tag36, tag38, tag39, tag40, tag41};
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 10, 28);
	}

}
