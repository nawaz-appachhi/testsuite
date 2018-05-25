package com.myntra.apiTests.portalservices.devapis;

import java.util.ArrayList;
import java.util.List;

import com.myntra.apiTests.dataproviders.devapis.StyleForumTestsDP;
import com.myntra.apiTests.portalservices.devapiservice.StyleForumTestsHelper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import com.jayway.jsonpath.JsonPath;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.Toolbox;
import com.myntra.lordoftherings.gandalf.RequestGenerator;

import net.minidev.json.JSONArray;

public class StyleForumTests extends StyleForumTestsDP
{
	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(DevAPITests.class);
	StyleForumTestsHelper Helper = new StyleForumTestsHelper();
	
	@Test(groups={"Sanity", "Regression"},dataProvider="DevAPI_StyleForum_GetUserFeeds_DP_Sanity",description="Get Style Forum feeds - Sanity")
	public void DevAPI_StyleForum_GetUserForumFeeds_VerifySuccess(String email, String password)
	{
		RequestGenerator request = Helper.getStyleForumUserFeeds(email, password);
		System.out.println("Get Style Forum Feeds Response : "+request.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("Get Style Forum Feeds API is down",200,request.response.getStatus());
	}
	
	@Test(groups={"SchemaValidation"},dataProvider="DevAPI_StyleForum_GetUserFeeds_DP_Sanity",description="Get Style Forum feeds - Schema Validation")
	public void DevAPI_StyleForum_GetUserForumFeeds_VerifySchema(String email, String password)
	{
		RequestGenerator request = Helper.getStyleForumUserFeeds(email, password);
		String response = request.returnresponseasstring();
		System.out.println("Get Style Forum Feeds Response : "+request.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("Get Style Forum Feeds API is down",200,request.response.getStatus());
		try {
			String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/DevAPIs/StyleForum_GetForumFeeds_Schema.txt");
			List < String > missingNodeList = validateServiceResponseNodesUsingSchemaValidator(response, jsonschema);
			AssertJUnit.assertTrue(missingNodeList + " nodes are missing in DevAPI Style Forum API response",
					CollectionUtils.isEmpty(missingNodeList));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test(groups={"Sanity", "Regression"},dataProvider="DevAPI_StyleForum_GetForumFeeds_DP_Sanity",description="Get Style Forum feeds - Sanity")
	public void DevAPI_StyleForum_GetForumFeeds_VerifySuccess(String email, String password, String feedType)
	{
		RequestGenerator request = Helper.getStyleForumFeeds(email, password,feedType,"1");
		System.out.println("Get Style Forum Feeds Response : "+request.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("Get Style Forum Feeds API is down",200,request.response.getStatus());
	}
	
	@Test(groups={"SchemaValidation"},dataProvider="DevAPI_StyleForum_GetForumFeeds_DP_Sanity",description="Get Style Forum feeds - Schema Validation")
	public void DevAPI_StyleForum_GetForumFeeds_VerifySchema(String email, String password, String feedType)
	{
		RequestGenerator request = Helper.getStyleForumFeeds(email, password,feedType,"1");
		String response = request.returnresponseasstring();
		System.out.println("Get Style Forum Feeds Response : "+request.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("Get Style Forum Feeds API is down",200,request.response.getStatus());
		try {
			String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/DevAPIs/StyleForum_GetForumFeeds_Schema.txt");
			List < String > missingNodeList = validateServiceResponseNodesUsingSchemaValidator(response, jsonschema);
			AssertJUnit.assertTrue(missingNodeList + " nodes are missing in DevAPI Style Forum API response",
					CollectionUtils.isEmpty(missingNodeList));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test(groups={"Sanity", "Regression"},description="Get Style Forum Topics - Sanity")
	public void DevAPI_StyleForum_GetForumTopics_VerifySuccess()
	{
		RequestGenerator request = Helper.getStyleForumTopics();
		System.out.println("Get Style Forum Topics Response : "+request.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("Get Style Forum Topics API is down",200,request.response.getStatus());
	}
	
	@Test(groups={"SchemaValidation"},description="Get Style Forum Topics - Schema Validation")
	public void DevAPI_StyleForum_GetForumTopics_VerifySchema()
	{
		RequestGenerator request = Helper.getStyleForumTopics();
		String response = request.returnresponseasstring();
		System.out.println("Get Style Forum Topics Response : "+request.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("Get Style Forum Topics API is down",200,request.response.getStatus());
		try {
			String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/DevAPIs/StyleForum_GetForumTopics_Schema.txt");
			List < String > missingNodeList = validateServiceResponseNodesUsingSchemaValidator(response, jsonschema);
			AssertJUnit.assertTrue(missingNodeList + " nodes are missing in DevAPI Style Forum API response",
					CollectionUtils.isEmpty(missingNodeList));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test(groups={"Sanity", "Regression"},dataProvider="DevAPI_StyleForum_GetForumFeedsForAllTopics_DP_Sanity",description="Get Style Forum feeds - Sanity")
	public void DevAPI_StyleForum_GetForumFeedsForAllTopics_VerifySuccess(String email, String password, String feedType)
	{
		ArrayList<String> FailedURLs = new ArrayList<String>();
		RequestGenerator TopicRequest = Helper.getStyleForumTopics();
		String topicResponse = TopicRequest.returnresponseasstring();
		boolean allPass = true;
		JSONArray Topics = JsonPath.read(topicResponse,"$.data.topics[*].topicId");
		
		for(int i=0; i<Topics.size(); i++)
		{
			RequestGenerator request = Helper.getStyleForumFeeds(email, password,feedType,Topics.get(i).toString());
			System.out.println("Get Style Forum Feeds Response for Topic : "+JsonPath.read(topicResponse,"$.data.topics["+i+"].topicTitle").toString()+"\n"+request.respvalidate.returnresponseasstring());
			if(!(request.response.getStatus()==200))
			{
				allPass = false;
				FailedURLs.add(request.getServiceundertest().URL);
			}
		}
		
		if(!allPass)
		{
			System.out.println("Style Forum Feed Failures");
			for(String url : FailedURLs)
			{
				System.out.println(url);
			}
			Assert.fail("Style Forum Feed API failure");
		}
		
		
	}
	
	@Test(groups={"Sanity", "Regression"},dataProvider="DevAPI_StyleForum_GetUserFeeds_DP_Sanity",description="Get Style Forum Personalized user Feeds - Sanity")
	public void DevAPI_StyleForum_GetPersonalizedFeeds_VerifySuccess(String email, String password)
	{
		RequestGenerator request = Helper.getStyleForumUserPersonalizedFeeds(email,password);
		System.out.println("Get Style Forum Personalized Feeds Response : "+request.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("Get Style Forum Personalized Feeds API is down",200,request.response.getStatus());
	}
	

}
