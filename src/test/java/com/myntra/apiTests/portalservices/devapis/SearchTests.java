package com.myntra.apiTests.portalservices.devapis;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonProcessingException;
import org.junit.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import net.minidev.json.JSONArray;
import com.jayway.jsonpath.JsonPath;
import com.myntra.apiTests.dataproviders.devapis.SearchTestsDP;
import com.myntra.apiTests.portalservices.devapiservice.DevAPICommonMethods;
import com.myntra.apiTests.portalservices.devapiservice.SearchTestsHelper;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.Toolbox;
import com.myntra.lordoftherings.gandalf.RequestGenerator;

import edu.emory.mathcs.backport.java.util.Collections;

public class SearchTests extends SearchTestsDP
{
	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(DevAPITests.class);
	static SearchTestsHelper Helper = new SearchTestsHelper();
	static DevAPICommonMethods DevAPIHelper = new DevAPICommonMethods();
	
	//Verify Get AutoSuggest API
	@Test(groups={"Sanity", "Regression"}, dataProvider="DevAPI_Search_GetAutoSuggest_Sanity_DP",description="Auto Suggest Search Terms API. Verify Response Code")
	public void DevAPI_Search_GetAutoSuggest_VerifySuccess(String keyword, String resultSize)
	{
		RequestGenerator request = Helper.getAutoSuggestData(keyword, resultSize);
		System.out.println("Get Auto Suggest Response : "+request.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("Get Auto Suggest API is down",200,request.response.getStatus());
	}
	
	@Test(groups={"Regression"}, dataProvider="DevAPI_Search_GetAutoSuggest_Regression_DP",description="Auto Suggest Search Terms API. Verify Data")
	public void DevAPI_Search_GetAutoSuggest_VerifyData(String keyword, String resultSize)
	{
		RequestGenerator request = Helper.getAutoSuggestData(keyword, resultSize);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println("Get Auto Suggest Response : "+response);
		AssertJUnit.assertEquals("Get Auto Suggest API is down",200,request.response.getStatus());
		boolean resultMatches = true;
		JSONArray Results = JsonPath.read(response, "$.data[*]");
		AssertJUnit.assertEquals("Result Size does not match the query",Integer.parseInt(resultSize),Results.size());
		
		String ResultId = null;
		String ResultLabel = null;
		for(int i=0; i<Results.size();i++)
		{
			ResultId = JsonPath.read(response, "$.data["+i+"].id").toString();
			ResultLabel = JsonPath.read(response, "$.data["+i+"].label").toString();
			
			if(!(ResultId.contains(keyword) && ResultLabel.contains(keyword)))
			{
				resultMatches = false;
			}
		}
		
		//AssertJUnit.assertEquals("Result Label/ID does not match the query",true,resultMatches);

	}
	
	@Test(groups={"NodeValidation"}, dataProvider="DevAPI_Search_GetAutoSuggest_Sanity_DP",description="Auto Suggest Search Terms API. Verify Response Code")
	public void DevAPI_Search_GetAutoSuggest_ValidateNodes(String keyword, String resultSize) throws JsonProcessingException, IOException
	{
		RequestGenerator request = Helper.getAutoSuggestData(keyword, resultSize);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println("Get Auto Suggest Response : "+request.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("Get Auto Suggest API is down",200,request.response.getStatus());
		boolean containsEmptyNodes = DevAPIHelper.doesContainEmptyNodes(response);
		AssertJUnit.assertEquals("Found Empty Nodes in Response", false,containsEmptyNodes);
	}
	
	@Test(groups={"SchemaValidation"}, dataProvider="DevAPI_Search_GetAutoSuggest_Sanity_DP",description="Auto Suggest Search Terms API. Verify Response Code")
	public void DevAPI_Search_GetAutoSuggest_VerifySchema(String keyword, String resultSize) throws JsonProcessingException, IOException
	{
		RequestGenerator request = Helper.getAutoSuggestData(keyword, resultSize);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println("Get Auto Suggest Response : "+request.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("Get Auto Suggest API is down",200,request.response.getStatus());
		try {
			String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/DevAPIs/Search_AutoSuggest_Schema.txt");
			List < String > missingNodeList = validateServiceResponseNodesUsingSchemaValidator(response, jsonschema);
			AssertJUnit.assertTrue(missingNodeList + " nodes are missing in DevAPI AutoSuggest response",
					CollectionUtils.isEmpty(missingNodeList));
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Schema Verification Failure");
		}
	}
	
	//Verify Search API
	@Test(groups={"Sanity", "Regression"}, dataProvider="DevAPI_Search_KeyWordSearch_Sanity_DP",description="Get Search Results based on Keywords. Verify Response Code")
	public void DevAPI_Search_KeywordSearch_VerifySuccess(String keyword)
	{
		RequestGenerator request = Helper.performKeywordSearch(keyword);
		AssertJUnit.assertEquals("Get Search results API is down",200,request.response.getStatus());
	}
	
	@Test(groups={"Regression"}, dataProvider="DevAPI_Search_KeyWordSearch_Regression_DP",description="Get Search Results based on Keywords. Verify Data Code")
	public void DevAPI_Search_KeywordSearch_VerifyData(String keyword)
	{
		RequestGenerator request = Helper.performKeywordSearch(keyword);
		String response = request.returnresponseasstring();
		AssertJUnit.assertEquals("Get Search results API is down",200,request.response.getStatus());
		try{
			AssertJUnit.assertEquals("Get Search results API is down",true,Integer.parseInt(JsonPath.read(response, "$.data.results.totalProductsCount").toString())>0);
			
		}
		catch (Exception e)
		{
			Assert.fail("Total Products count not available in the response!");
		}
	}
	
	@Test(groups={"NodeValidations"}, dataProvider="DevAPI_Search_KeyWordSearch_NodeSchemaValidations_DP",description="Verify nodes in search Results based on Keywords. Verify Response Code")
	public void DevAPI_Search_KeywordSearch_VerifyNodes(String keyword)
	{
		RequestGenerator request = Helper.performKeywordSearch(keyword);
		String response = request.returnresponseasstring();
		AssertJUnit.assertEquals("Get Search results API is down",200,request.response.getStatus());
		boolean containsEmptyNodes=true;
		try {
			containsEmptyNodes = DevAPIHelper.doesContainEmptyNodes(response);
		} catch (Exception e) {
			Assert.fail("Unable to Validate Nodes from response");
		}
		AssertJUnit.assertEquals("Found Empty Nodes in Response", false,containsEmptyNodes);
	}
	
	@Test(groups={"SchemaValidations"}, dataProvider="DevAPI_Search_KeyWordSearch_NodeSchemaValidations_DP",description="Verify Schema in search Results based on Keywords. Verify Response Code")
	public void DevAPI_Search_KeywordSearch_VerifySchema(String keyword)
	{
		RequestGenerator request = Helper.performKeywordSearch(keyword);
		String response = request.returnresponseasstring();
		AssertJUnit.assertEquals("Get Search results API is down",200,request.response.getStatus());
		try {
			String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/DevAPIs/Search_SearchResults_Schema.txt");
			List < String > missingNodeList = validateServiceResponseNodesUsingSchemaValidator(response, jsonschema);
			AssertJUnit.assertTrue(missingNodeList + " nodes are missing in DevAPI Search response",
					CollectionUtils.isEmpty(missingNodeList));
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Schema Verification Failure");
		}
	}
	
	//Verify Search Offers
	@Test(groups={"Sanity", "Regression"}, dataProvider="DevAPI_Search_KeyWordSearch_Sanity_DP",description="Get Search Results based on Keywords. Verify Response Code")
	public void DevAPI_Search_SearchOffers_VerifySuccess(String keyword)
	{
		RequestGenerator request = Helper.searchOffers(keyword);
		AssertJUnit.assertEquals("Get Search Offers API is down",200,request.response.getStatus());
	}
	
	@Test(groups={"NodeValidations"}, dataProvider="DevAPI_Search_KeyWordSearch_NodeSchemaValidations_DP",description="Get Search Offers based on Keywords. Verify Data Nodes")
	public void DevAPI_Search_searchOffers_VerifyData(String keyword)
	{
		RequestGenerator request = Helper.searchOffers(keyword);
		AssertJUnit.assertEquals("Get Search Offers API is down",200,request.response.getStatus());
		String response = request.returnresponseasstring();
		boolean containsEmptyNodes=true;
		try {
			containsEmptyNodes = DevAPIHelper.doesContainEmptyNodes(response);
		} catch (Exception e) {
			Assert.fail("Unable to Validate Nodes from response");
		}
		AssertJUnit.assertEquals("Found Empty Nodes in Response", false,containsEmptyNodes);
		
	}
	
	@Test(groups={"SchemaValidations"}, dataProvider="DevAPI_Search_KeyWordSearch_NodeSchemaValidations_DP",description="Get Search Offers based on Keywords. Verify Schema")
	public void DevAPI_Search_searchOffers_VerifySchema(String keyword)
	{
		RequestGenerator request = Helper.searchOffers(keyword);
		AssertJUnit.assertEquals("Get Search Offers API is down",200,request.response.getStatus());
		String response = request.returnresponseasstring();
		try {
			String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/DevAPIs/Search_SearchOffers_Schema.txt");
			List < String > missingNodeList = validateServiceResponseNodesUsingSchemaValidator(response, jsonschema);
			AssertJUnit.assertTrue(missingNodeList + " nodes are missing in DevAPI Search Offers response",
					CollectionUtils.isEmpty(missingNodeList));
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Schema Verification Failure");
		}
		
	}
	
	@Test(groups={"Sanity", "Regression"}, dataProvider="DevAPI_Search_KeyWordSearchWithFacets_Sanity_DP",description="Get Search Results based on Keywords and facets. Verify Response Code")
	public void DevAPI_Search_KeywordSearchWithFacets_VerifySuccess(String Data)
	{
		RequestGenerator request = Helper.performKeywordSearch(Data);
		AssertJUnit.assertEquals("Get Search results API is down",200,request.response.getStatus());
	}
	
	@Test(groups={"Regression"}, dataProvider="DevAPI_Search_KeyWordSearchWithFacets_Regression_DP",description="Get Search Results based on Keywords. Verify Response Code")
	public void DevAPI_Search_KeywordSearchWithFacets_VerifyData(String Data)
	{
		RequestGenerator request = Helper.performKeywordSearch(Data);
		String response = request.returnresponseasstring();
		AssertJUnit.assertEquals("Get Search results API is down",200,request.response.getStatus());
		try{
			int ProductCount = JsonPath.read(response, "$.data.results.totalProductsCount");
			AssertJUnit.assertEquals("Get Search results API is down",true,ProductCount>0);
			
		}
		catch (Exception e)
		{
			Assert.fail("Total Products count not available in the response!");
		}
	}
	
	//Sort Tests
	@Test(groups={"Regression"}, dataProvider="DevAPI_Search_KeyWordSearchSort_Regression_DP",description="Get Search Results based on Keywords and apply various sorts. Verify Response Code", enabled=false)
	public void DevAPI_Search_SortSearchResults_VerifyData(String Data, String SortType)
	{
		RequestGenerator request = Helper.performKeywordSearch(Data);
		String response = request.returnresponseasstring();
		AssertJUnit.assertEquals("Get Search results API is down",200,request.response.getStatus());
		try
		{
			ArrayList<Double> Price = JsonPath.read(response, "$.data.results.products[*].price");
			Double MaxPrice = ((Integer) Collections.max(Price)).doubleValue();
			System.out.println("MAXPRICE : "+MaxPrice);
			Double MinPrice = ((Integer) Collections.min(Price)).doubleValue();
			System.out.println("MINPRICE : "+MinPrice);

			String SortTypeOverride = "PRICE-HIGH";
			
			switch (SortTypeOverride)
			{
				case "PRICE-HIGH":
				{
					boolean isSorted = false;
					String sortFacet = Data.concat("&sort=high&request_id=null");
					RequestGenerator SortRequest = Helper.performKeywordSearch(sortFacet);
					String SortResponse = request.returnresponseasstring();
					AssertJUnit.assertEquals("Search API failure while sorting",200, SortRequest.response.getStatus());
					System.out.println("First Product Price : "+JsonPath.read(SortResponse, "$.data.results.products[0].price"));
					System.out.println("Last Product Price : "+JsonPath.read(SortResponse, "$.data.results.products[47].price"));

					if(JsonPath.read(SortResponse, "$.data.results.products[0].price")==MaxPrice && JsonPath.read(SortResponse, "$.data.results.products[47].discounted_price")==MinPrice)
					{
						isSorted = true;
					}
					AssertJUnit.assertEquals("Sort by Price High to Low is not applied",true, isSorted);
					break;
				}
				case "PRICE-LOW":
				{
					
					boolean isSorted = false;
					String sortFacet = Data.concat("%26sort%3Dlow");
					RequestGenerator SortRequest = Helper.performKeywordSearch(sortFacet);
					String SortResponse = request.returnresponseasstring();
					AssertJUnit.assertEquals("Search API failure while sorting",200, SortRequest.response.getStatus());

					if(JsonPath.read(SortResponse, "$.data.results.products[47].price")==MaxPrice && JsonPath.read(SortResponse, "$.data.results.products[0].discounted_price")==MinPrice)
					{
						isSorted = true;
					}
					AssertJUnit.assertEquals("Sort by Price Low to High is not applied",true, isSorted);
					break;
				}
				case "POPULARITY":
				{
					String sortFacet = Data.concat("%26sort%3Dpopularity");
					RequestGenerator SortRequest = Helper.performKeywordSearch(sortFacet);
					AssertJUnit.assertEquals("Search API failure while sorting",200, SortRequest.response.getStatus());

					
				}
				case "DISCOUNT":
				{
					String sortFacet = Data.concat("%26sort%3Ddiscount");
					RequestGenerator SortRequest = Helper.performKeywordSearch(sortFacet);
					String SortResponse = request.returnresponseasstring();
					AssertJUnit.assertEquals("Search API failure while sorting",200, SortRequest.response.getStatus());

					ArrayList<Double> Discount = JsonPath.read(response, "$.data.results.products[*].discount");
					Double MaxDiscount = (Double) Collections.max(Discount);
					Double MinDiscount = (Double) Collections.min(Discount);
					boolean isSorted = false;
					if(JsonPath.read(SortResponse, "$.data.results.products[0].discount")==MaxDiscount && JsonPath.read(SortResponse, "$.data.results.products[47].discount")==MinDiscount)
					{
						isSorted = true;
					}
					AssertJUnit.assertEquals("Sort by Discount High to Low is not applied",true, isSorted);
					break;
				}
				case "WHATSNEW":
				{
					String sortFacet = Data.concat("%26sort%3Ddiscount");
					RequestGenerator SortRequest = Helper.performKeywordSearch(sortFacet);
					AssertJUnit.assertEquals("Search API failure while sorting",200, SortRequest.response.getStatus());

				}
			}

			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			Assert.fail("EXCEPTION WHILE SORTING");
		}
		
	}
	
	@Test(groups={"SchemaValidation"}, dataProvider="DevAPI_Search_KeyWordSearchSort_NodeSchemaValidation_DP",description="Get Search Results based on Keywords and apply various sorts. Verify Response Code",enabled=false)
	public void DevAPI_Search_SortSearchResults_VerifySchema(String Data, String SortType)
	{
		RequestGenerator request = Helper.performKeywordSearch(Data);
		String response = request.returnresponseasstring();
		AssertJUnit.assertEquals("Get Search results API is down",200,request.response.getStatus());
		try
		{
			ArrayList<Double> Price = JsonPath.read(response, "$.data.results.products[*].discounted_price");
			Double MaxPrice = (Double) Collections.max(Price);
			Double MinPrice = (Double) Collections.min(Price);
			
			switch (SortType)
			{
				case "PRICE-HIGH":
				{
					String sortFacet = Data.concat("%26sort%3Dhigh");
					RequestGenerator SortRequest = Helper.performKeywordSearch(sortFacet);
					String SortResponse = request.returnresponseasstring();
					AssertJUnit.assertEquals("Search API failure while sorting",200, SortRequest.response.getStatus());
					AssertJUnit.assertEquals("Sort by Price High to Low- Schema Failure",true, verifyListPageSchema(SortResponse));
					break;
				}
				case "PRICE-LOW":
				{
					
					boolean isSorted = false;
					String sortFacet = Data.concat("%26sort%3Dlow");
					RequestGenerator SortRequest = Helper.performKeywordSearch(sortFacet);
					String SortResponse = request.returnresponseasstring();
					AssertJUnit.assertEquals("Search API failure while sorting",200, SortRequest.response.getStatus());
					AssertJUnit.assertEquals("Sort by Price Low to High - Schema Failure",true, verifyListPageSchema(SortResponse));
					break;
				}
				case "POPULARITY":
				{
					String sortFacet = Data.concat("%26sort%3Dpopularity");
					RequestGenerator SortRequest = Helper.performKeywordSearch(sortFacet);
					String SortResponse = request.returnresponseasstring();
					AssertJUnit.assertEquals("Search API failure while sorting",200, SortRequest.response.getStatus());
					AssertJUnit.assertEquals("Sort by Popularity - Schema Failure",true, verifyListPageSchema(SortResponse));
					break;
				}
				case "DISCOUNT":
				{
					String sortFacet = Data.concat("%26sort%3Ddiscount");
					RequestGenerator SortRequest = Helper.performKeywordSearch(sortFacet);
					String SortResponse = request.returnresponseasstring();
					AssertJUnit.assertEquals("Search API failure while sorting",200, SortRequest.response.getStatus());
					AssertJUnit.assertEquals("Sort by Discount - Schema Failure",true, verifyListPageSchema(SortResponse));
					break;
				}
				case "WHATSNEW":
				{
					String sortFacet = Data.concat("%26sort%3Ddiscount");
					RequestGenerator SortRequest = Helper.performKeywordSearch(sortFacet);
					String SortResponse = request.returnresponseasstring();
					AssertJUnit.assertEquals("Search API failure while sorting",200, SortRequest.response.getStatus());
					AssertJUnit.assertEquals("Sort by New - Schema Failure",true, verifyListPageSchema(SortResponse));
					break;
				}
			}

			
		}
		catch(Exception e)
		{
			Assert.fail("EXCEPTION WHILE SORTING");
		}
		
	}
	
	public boolean verifyListPageSchema(String response)
	{
		try {
			String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/DevAPIs/Search_SearchResultsSort_Schema.txt");
			List < String > missingNodeList = validateServiceResponseNodesUsingSchemaValidator(response, jsonschema);
			AssertJUnit.assertTrue(missingNodeList + " nodes are missing in DevAPI Search Sort response",
					CollectionUtils.isEmpty(missingNodeList));
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
		
	}
	//Search Trends
	@Test(groups={"Sanity", "Regression"}, description="Get Search Trends based. Verify Response Code")
	public void DevAPI_Search_Trends_VerifySuccess( )
	{
		RequestGenerator request = Helper.searchTrends();
		AssertJUnit.assertEquals("Get Search Offers API is down",200,request.response.getStatus());
	}
	
	@Test(groups={"Regression"}, description="Get Search Trends based. Verify Data Nodes")
	public void DevAPI_Search_searchTrends_VerifyData()
	{
		RequestGenerator request = Helper.searchTrends();
		AssertJUnit.assertEquals("Get Search Trends API is down",200,request.response.getStatus());
		String response = request.returnresponseasstring();
		boolean containsEmptyNodes=true;
		try {
			containsEmptyNodes = DevAPIHelper.doesContainEmptyNodes(response);
		} catch (Exception e) {
			Assert.fail("Unable to Validate Nodes from response");
		}
		AssertJUnit.assertEquals("Found Empty Nodes in Response", false,containsEmptyNodes);
		
	}
	
	@Test(groups={"Regression"}, description="Get Search Trends based. Verify whether terms are null")
	public void DevAPI_Search_searchTrends_VerifyNullData()
	{
		RequestGenerator request = Helper.searchTrends();
		AssertJUnit.assertEquals("Get Search Trends API is down",200,request.response.getStatus());
		String response = request.returnresponseasstring();
		JSONArray terms = JsonPath.read(response,"$.data.trendingTerms[*]");
		boolean isEmpty = terms.isEmpty();
		AssertJUnit.assertEquals("Trend terms are empty", false, isEmpty);
	}
	
	@Test(groups={"SchemaValidations"},description="Get Search Trends. Verify Schema")
	public void DevAPI_Search_searchTrends_VerifySchema()
	{
		RequestGenerator request = Helper.searchTrends();
		AssertJUnit.assertEquals("Get Search Offers API is down",200,request.response.getStatus());
		String response = request.returnresponseasstring();
		try {
			String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/devapiservice-search-searchtrendSchema.txt");
			List < String > missingNodeList = validateServiceResponseNodesUsingSchemaValidator(response, jsonschema);
			AssertJUnit.assertTrue(missingNodeList + " nodes are missing in DevAPI Search Trends response",
					CollectionUtils.isEmpty(missingNodeList));
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Schema Verification Failure");
		}
		
	}
}
