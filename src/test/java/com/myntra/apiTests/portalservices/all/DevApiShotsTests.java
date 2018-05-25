package com.myntra.apiTests.portalservices.all;

import java.util.List;
import java.util.Random;

import com.myntra.apiTests.dataproviders.DevApiShotsDP;
import net.minidev.json.JSONArray;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.glassfish.jersey.client.ClientConfig;

import org.json.JSONException;
import org.json.JSONObject;
import org.testng.AssertJUnit;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.jayway.jsonpath.JsonPath;
import com.myntra.apiTests.portalservices.devapiservice.DevApiShotsServiceHelper;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.Toolbox;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.RequestGenerator;

public class DevApiShotsTests extends DevApiShotsDP {
	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(DevApis.class);
	static APIUtilities apiUtil = new APIUtilities();
	static DevApiShotsServiceHelper ShotsHelper = new DevApiShotsServiceHelper();
	static String[] TopicsArray;
	static boolean TopicsLoaded = false;
	WebTarget target;
	ClientConfig config;
	Client client;
	Invocation.Builder invBuilder;
	
	
	public String getRandomTopicForTesting(String[] Topics)
	{
		String RandomTopic="";
		int RangeStart = 0;
		int RangeEnd = Topics.length;
		Random obj = new Random();
		RandomTopic = Topics[((obj.nextInt(RangeEnd - RangeStart)+RangeStart))];
		return RandomTopic;
	
	}
	
	public String getOneTopic()
	{
		String DefaultTopic ="{\"thumbnailImageUrl\":\"http://assets.myntassets.com/assets/images/2015/11/3/11446530209402-casualwear.jpg\",\"followEnabled\":true,\"displayEnabled\":false,\"clickEnabled\":false,\"displayName\":\"casual\",\"name\":\"casual\",\"id\":3806,\"type\":\"occasion\",\"baseColor\":\"#604E44\"}";
		if(TopicsLoaded)
		DefaultTopic = getRandomTopicForTesting(TopicsArray);
		return DefaultTopic;
	}
	
	public void HoldExecution_Seconds(int seconds) throws InterruptedException
	{
		Thread.sleep(2000);
	}
	
	public String[] getOccassionFromTopics(String Topic) throws JSONException
	{
		System.out.println("TOPIC USED : "+Topic);
		JSONObject OBJ = new JSONObject(Topic);
		String[] Occassion = new String[2];
		Occassion[0] = OBJ.getString("type");
		Occassion[1] = OBJ.getString("name");
		return Occassion;
		
	}
	
	
	@BeforeClass
	public void LOADTOPICS () throws JSONException
	{
		TopicsArray = ShotsHelper.loadTopics("aravind.raj@myntra.com", "welcome");
		int TotalTopics = TopicsArray.length;
		if (TotalTopics > 0)
			TopicsLoaded = true;			
	}
	
	
	public void CLEANUP(String userName, String password)
	{
		boolean deletedAllShots = true;
		String ObjectID="";
		String Path=null;
		RequestGenerator request = ShotsHelper.getShotsByCriteria(userName,password, "BY_USER", "", "", "");
		String response = request.respvalidate.returnresponseasstring();
		JSONArray ShotsArray = JsonPath.read(response, "$.data.shots[*]");
		for(int i=0; i<ShotsArray.size();i++)
		{
			Path="$.data.shots["+i+"].objectId";
			ObjectID=JsonPath.read(response, Path).toString();
			request = ShotsHelper.deleteShots(userName, password, ObjectID);
			if(!(request.response.getStatus()==200))
			{
				deletedAllShots=false;
			}
		}
		
		if(deletedAllShots)
			System.out.println("All Shots Deleted!");
		else
			System.out.println("Deleting Shots encountered Error - Check the Delete Shots API & Related Testcases!!!");
	
	}
	
	
	@AfterClass
	public void CLEANUP_ALL()
	{
		String userName = "aravind.shots@myntra.com";
		String password="randompass";
		CLEANUP(userName,password);
	}
	
	public boolean retryTestforStatus(RequestGenerator request)
	{
		RequestGenerator requestNew = request;
		if(requestNew.response.getStatus()==200)
		{
			return true;
		}
		else
			return false;
	}
	
	
	public void TearDown_DeleteShot(String userName, String password, String objectId)
	{
		RequestGenerator request = ShotsHelper.deleteShots(userName, password, objectId);
		String response = request.respvalidate.returnresponseasstring();
		ShotsHelper.printAndLog("Delete Shots Service Response : \n ", response);
		
		if(!(request.response.getStatus()==200))
		{
			//Retry Test
			for(int i=0; i<3; i++)
			{
				System.out.println("Retry - "+i);
				boolean status = retryTestforStatus(request);
				if (status)
					break;
			}
		}
		AssertJUnit.assertEquals("[Tear Down Error] - Delete Shots Service is not working",200,request.response.getStatus());
	}
	
	@Test(groups = { "Smoke","Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },dataProvider = "DevApiShots_GetTopics")
	public void Shots_getTopics_Success (String userName, String password)
	{
		RequestGenerator request = ShotsHelper.getTopics(userName, password);
		String response = request.respvalidate.returnresponseasstring();
		ShotsHelper.printAndLog("Get Topics Service Response : \n ", response);
		AssertJUnit.assertEquals("Get Topics Service is not working",200,request.response.getStatus());

	}
	
	@Test(groups = { "Schema Validation" },dataProvider = "DevApiShots_GetTopics")
	public void Shots_getTopics_SchemaValidation (String userName, String password)
	{
		RequestGenerator request = ShotsHelper.getTopics(userName, password);
		String response = request.respvalidate.returnresponseasstring();
		ShotsHelper.printAndLog("Get Topics Service Response : \n ", response);
		AssertJUnit.assertEquals("Get Topics Service is not working",200,request.response.getStatus());
		
		try {
            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/devapi-shots-getTopics-schema.txt");
            List<String> missingNodeList = ShotsHelper.validateServiceResponseNodesUsingSchemaValidator(response, jsonschema);
            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in DevApiService signIn API response", 
            		CollectionUtils.isEmpty(missingNodeList));
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	
	@Test(groups = { "Smoke","Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },dataProvider = "DevApiShots_createShots")
	public void Shots_createShot_Success (String userName, String password, String shotDescription, String styleData) throws InterruptedException
	{
		
		String Topic = getOneTopic();
				
		RequestGenerator request = ShotsHelper.createShots(userName, password, shotDescription, styleData,Topic);
		String response = request.respvalidate.returnresponseasstring();
		String objectId = JsonPath.read(response, "$.data.objectId");
		ShotsHelper.printAndLog("Create Shots Service Response : \n ", response);
		AssertJUnit.assertEquals("Create Shots Service is not working",200,request.response.getStatus());
		
	
		HoldExecution_Seconds(5);
		//TearDown
		TearDown_DeleteShot(userName, password, objectId);
	}
	
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },dataProvider = "DevApiShots_createShots")
	public void Shots_createShot_VerifyData (String userName, String password, String shotDescription, String styleData) throws InterruptedException
	{
		String Topic = getOneTopic();
		
		RequestGenerator request = ShotsHelper.createShots(userName, password, shotDescription, styleData,Topic);
		String response = request.respvalidate.returnresponseasstring();
		String objectId = JsonPath.read(response, "$.data.objectId");
		AssertJUnit.assertEquals("Object ID is null",false,objectId.isEmpty());
		ShotsHelper.printAndLog("Create Shots Service Response : \n ", response);
		AssertJUnit.assertEquals("Create Shots Service is not working",200,request.response.getStatus());
		if(shotDescription=="")
			shotDescription=null;
		AssertJUnit.assertEquals("Description is Not proper",shotDescription, JsonPath.read(response, "$.data.description"));
		AssertJUnit.assertEquals("Shot ID is null",false,JsonPath.read(response,"$.data.id").toString().isEmpty());
		AssertJUnit.assertEquals("Shot Image URL is null",false,JsonPath.read(response,"$.data.image").toString().isEmpty());
		AssertJUnit.assertEquals("Shot Object ID is null",false,JsonPath.read(response,"$.data.objectId").toString().isEmpty());

		TearDown_DeleteShot(userName, password, objectId);
		
	}
	
	@Test(groups = { "SchemaValidation" },dataProvider = "DevApiShots_createShots")
	public void Shots_createShot_VerifySchema (String userName, String password, String shotDescription, String styleData) throws InterruptedException
	{
		String Topic = getOneTopic();
		
		RequestGenerator request = ShotsHelper.createShots(userName, password, shotDescription, styleData,Topic);
		String response = request.respvalidate.returnresponseasstring();
		String objectId = JsonPath.read(response, "$.data.objectId");
		ShotsHelper.printAndLog("Create Shots Service Response : \n ", response);
		AssertJUnit.assertEquals("Create Shots Service is not working",200,request.response.getStatus());

		try {
            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/devapi-shots-createshots-schema.txt");
            List<String> missingNodeList = ShotsHelper.validateServiceResponseNodesUsingSchemaValidator(response, jsonschema);
            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in DevApiService signIn API response", 
            		CollectionUtils.isEmpty(missingNodeList));
        } catch (Exception e) {
            e.printStackTrace();
        }
		
	
		TearDown_DeleteShot(userName, password, objectId);
	}
	
	
	
	@Test(groups = {"Smoke", "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },dataProvider = "DevApiShots_updateShots")
	public void Shots_updateShot_Success (String userName, String password, String shotDescription, String styleData, String updateDescription, String updateStyle) throws InterruptedException
	{
		String Topic = getOneTopic();
		
		RequestGenerator request = ShotsHelper.createShots(userName, password, shotDescription, styleData,Topic);
		String response = request.respvalidate.returnresponseasstring();
		String objectId = JsonPath.read(response, "$.data.objectId");
		ShotsHelper.printAndLog("Create Shots Service Response : \n ", response);
		AssertJUnit.assertEquals("Create Shots Service is not working",200,request.response.getStatus());
		
		request=ShotsHelper.updateShots(userName, password, objectId, updateDescription, updateStyle);
		response = request.respvalidate.returnresponseasstring();
		ShotsHelper.printAndLog("Update Shots Service Response : \n ", response);
		AssertJUnit.assertEquals("Update Shots Service is not working",200,request.response.getStatus());
		
		TearDown_DeleteShot(userName, password, objectId);
	}
	
	@Test(groups = {"Smoke", "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },dataProvider = "DevApiShots_updateShots")
	public void Shots_updateShot_VerifyData (String userName, String password, String shotDescription, String styleData, String updateDescription, String updateStyle) throws InterruptedException
	{
		String Topic = getOneTopic();
		
		RequestGenerator request = ShotsHelper.createShots(userName, password, shotDescription, styleData,Topic);
		String response = request.respvalidate.returnresponseasstring();
		String objectId = JsonPath.read(response, "$.data.objectId");
		ShotsHelper.printAndLog("Create Shots Service Response : \n ", response);
		AssertJUnit.assertEquals("Create Shots Service is not working",200,request.response.getStatus());
		if (shotDescription=="")
			shotDescription=null;
		AssertJUnit.assertEquals("Description is Not proper",shotDescription, JsonPath.read(response, "$.data.description"));

		request=ShotsHelper.updateShots(userName, password, objectId, updateDescription, updateStyle);
		response = request.respvalidate.returnresponseasstring();
		ShotsHelper.printAndLog("Update Shots Service Response : \n ", response);
		AssertJUnit.assertEquals("Update Shots Service is not working",200,request.response.getStatus());
		AssertJUnit.assertEquals("Description is Not proper",updateDescription, JsonPath.read(response, "$.data.description"));
		AssertJUnit.assertEquals("Shot ID is null",false,JsonPath.read(response,"$.data.id").toString().isEmpty());
		AssertJUnit.assertEquals("Shot Image URL is null",false,JsonPath.read(response,"$.data.image").toString().isEmpty());
		AssertJUnit.assertEquals("Shot Object ID is null",false,JsonPath.read(response,"$.data.objectId").toString().isEmpty());

		TearDown_DeleteShot(userName, password, objectId);
	}
	
	
	@Test(groups = {"Schema Validation" },dataProvider = "DevApiShots_updateShots")
	public void Shots_updateShot_verifySchema (String userName, String password, String shotDescription, String styleData, String updateDescription, String updateStyle) throws InterruptedException
	{
		String Topic = getOneTopic();
		
		RequestGenerator request = ShotsHelper.createShots(userName, password, shotDescription, styleData,Topic);
		String response = request.respvalidate.returnresponseasstring();
		String objectId = JsonPath.read(response, "$.data.objectId");
		ShotsHelper.printAndLog("Create Shots Service Response : \n ", response);
		AssertJUnit.assertEquals("Create Shots Service is not working",200,request.response.getStatus());
		
		request=ShotsHelper.updateShots(userName, password, objectId, updateDescription, updateStyle);
		response = request.respvalidate.returnresponseasstring();
		ShotsHelper.printAndLog("Update Shots Service Response : \n ", response);
		AssertJUnit.assertEquals("Update Shots Service is not working",200,request.response.getStatus());
		
		try {
            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/devapi-shots-updateShots-schema.txt");
            List<String> missingNodeList = ShotsHelper.validateServiceResponseNodesUsingSchemaValidator(response, jsonschema);
            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in DevApiService signIn API response", 
            		CollectionUtils.isEmpty(missingNodeList));
        } catch (Exception e) {
            e.printStackTrace();
        }
		
		//TearDown
		TearDown_DeleteShot(userName, password, objectId);
		}
	
	
	
	
	@Test(groups = { "Smoke","Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },dataProvider = "DevApiShots_createShots")
	public void Shots_deleteShot_Success (String userName, String password, String shotDescription, String styleData) throws InterruptedException
	{
		String Topic = getOneTopic();
		RequestGenerator request = ShotsHelper.createShots(userName, password, shotDescription, styleData, Topic);
		String response = request.respvalidate.returnresponseasstring();
		String objectId = JsonPath.read(response, "$.data.objectId");
		ShotsHelper.printAndLog("Create Shots Service Response : \n ", response);
		AssertJUnit.assertEquals("Create Shots Service is not working",200,request.response.getStatus());
		
		TearDown_DeleteShot(userName, password, objectId);
	}
	
	@Test(groups = { "Schema Validation" },dataProvider = "DevApiShots_createShots")
	public void Shots_deleteShot_verifySchema (String userName, String password, String shotDescription, String styleData) throws InterruptedException
	{
		String Topic = getOneTopic();
		RequestGenerator request = ShotsHelper.createShots(userName, password, shotDescription, styleData, Topic);
		String response = request.respvalidate.returnresponseasstring();
		String objectId = JsonPath.read(response, "$.data.objectId");
		ShotsHelper.printAndLog("Create Shots Service Response : \n ", response);
		AssertJUnit.assertEquals("Create Shots Service is not working",200,request.response.getStatus());
		
		HoldExecution_Seconds(3);
		
		//TearDown
		request = ShotsHelper.deleteShots(userName, password, objectId);
		response = request.respvalidate.returnresponseasstring();
		ShotsHelper.printAndLog("Delete Shots Service Response : \n ", response);
		AssertJUnit.assertEquals("[Tear Down Error] - Delete Shots Service is not working",200,request.response.getStatus());
		
		try {
            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/devapi-shots-deleteShots-Schema.txt");
            List<String> missingNodeList = ShotsHelper.validateServiceResponseNodesUsingSchemaValidator(response, jsonschema);
            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in DevApiService signIn API response", 
            		CollectionUtils.isEmpty(missingNodeList));
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	@Test(groups = { "Smoke","Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },dataProvider = "DevApiShots_createShots")
	public void Shots_getShotByObjectID_Success (String userName, String password, String shotDescription, String styleData) throws InterruptedException
	{
		String Topic = getOneTopic();
		RequestGenerator request = ShotsHelper.createShots(userName, password, shotDescription, styleData,Topic);
		String response = request.respvalidate.returnresponseasstring();
		String objectId = JsonPath.read(response, "$.data.objectId");
		ShotsHelper.printAndLog("Create Shots Service Response : \n ", response);
		AssertJUnit.assertEquals("Create Shots Service is not working",200,request.response.getStatus());
		
		request = ShotsHelper.getShotsByCriteria(userName, password, "BY_OBJECTID", objectId, "", "");
		response = request.respvalidate.returnresponseasstring();
		ShotsHelper.printAndLog("Get Shots by Object ID Response : \n ", response);
		AssertJUnit.assertEquals("Get Shots by Object ID is not working",200,request.response.getStatus());

		TearDown_DeleteShot(userName, password, objectId);
	}
	
	
	@Test(groups = { "Smoke","Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },dataProvider = "DevApiShots_createShots")
	public void Shots_getShotByOccassion_Success (String userName, String password, String shotDescription, String styleData) throws InterruptedException, JSONException
	{
		String Topic = getOneTopic();
		String[] Occassion = getOccassionFromTopics(Topic);
		RequestGenerator request = ShotsHelper.createShots(userName, password, shotDescription, styleData,Topic);
		String response = request.respvalidate.returnresponseasstring();
		String objectId = JsonPath.read(response, "$.data.objectId");
		ShotsHelper.printAndLog("Create Shots Service Response : \n ", response);
		AssertJUnit.assertEquals("Create Shots Service is not working",200,request.response.getStatus());
		
		HoldExecution_Seconds(5);
		request = ShotsHelper.getShotsByCriteria(userName, password, "BY_OCCASION", objectId, Occassion[0], Occassion[1]);
		response = request.respvalidate.returnresponseasstring();
		ShotsHelper.printAndLog("Get Shots by Object ID Response : \n ", response);
		AssertJUnit.assertEquals("Get Shots by Object ID is not working",200,request.response.getStatus());

		TearDown_DeleteShot(userName, password, objectId);
	}
	
	
	
	
	
	@Test(groups = { "Smoke","Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },dataProvider = "DevApiShots_createShots")
	public void Shots_getShotByObjectID_VerifyData (String userName, String password, String shotDescription, String styleData) throws InterruptedException
	{
		String Topic = getOneTopic();
		RequestGenerator request = ShotsHelper.createShots(userName, password, shotDescription, styleData,Topic);
		String response = request.respvalidate.returnresponseasstring();
		String objectId = JsonPath.read(response, "$.data.objectId");
		ShotsHelper.printAndLog("Create Shots Service Response : \n ", response);
		AssertJUnit.assertEquals("Create Shots Service is not working",200,request.response.getStatus());
		
		request = ShotsHelper.getShotsByCriteria(userName, password, "BY_OBJECTID", objectId, "", "");
		response = request.respvalidate.returnresponseasstring();
		ShotsHelper.printAndLog("Get Shots by Object ID Response : \n ", response);
		AssertJUnit.assertEquals("Get Shots by Object ID is not working",200,request.response.getStatus());
		AssertJUnit.assertEquals("Get Shots by Object ID is not working - Object ID is different",objectId,JsonPath.read(response, "$.data.objectId"));


		TearDown_DeleteShot(userName, password, objectId);
	}
	
	@Test(groups = { "Smoke","Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },dataProvider = "DevApiShots_createShots")
	public void Shots_getShotByUser_Success (String userName, String password, String shotDescription, String styleData) throws InterruptedException
	{
		String Topic = getOneTopic();
		RequestGenerator request = ShotsHelper.createShots(userName, password, shotDescription, styleData,Topic);
		String response = request.respvalidate.returnresponseasstring();
		String objectId = JsonPath.read(response, "$.data.objectId");
		ShotsHelper.printAndLog("Create Shots Service Response : \n ", response);
		AssertJUnit.assertEquals("Create Shots Service is not working",200,request.response.getStatus());
		
		HoldExecution_Seconds(5);
		request = ShotsHelper.getShotsByCriteria(userName, password, "BY_USER", "", "", "");
		response = request.respvalidate.returnresponseasstring();
		ShotsHelper.printAndLog("Get Shots by User Response : \n ", response);
		AssertJUnit.assertEquals("Get Shots by User is not working",200,request.response.getStatus());

		TearDown_DeleteShot(userName, password, objectId);
	}
	
	@Test(groups = { "Schema Validation" },dataProvider = "DevApiShots_createShots")
	public void Shots_getShotByUser_SchemaValidation (String userName, String password, String shotDescription, String styleData) throws InterruptedException
	{
		String Topic = getOneTopic();
		RequestGenerator request = ShotsHelper.createShots(userName, password, shotDescription, styleData,Topic);
		String response = request.respvalidate.returnresponseasstring();
		String objectId = JsonPath.read(response, "$.data.objectId");
		ShotsHelper.printAndLog("Create Shots Service Response : \n ", response);
		AssertJUnit.assertEquals("Create Shots Service is not working",200,request.response.getStatus());
		
		
		request = ShotsHelper.getShotsByCriteria(userName, password, "BY_USER", "", "", "");
		response = request.respvalidate.returnresponseasstring();
		ShotsHelper.printAndLog("Get Shots by User Response : \n ", response);
		AssertJUnit.assertEquals("Get Shots by User is not working",200,request.response.getStatus());

		try {
            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/devapishots-getshotsbyuser-schema.txt");
            List<String> missingNodeList = ShotsHelper.validateServiceResponseNodesUsingSchemaValidator(response, jsonschema);
            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in DevApiService signIn API response", 
            		CollectionUtils.isEmpty(missingNodeList));
        } catch (Exception e) {
            e.printStackTrace();
        }
		
		TearDown_DeleteShot(userName, password, objectId);
	}
	
	
	@Test(groups = { "Smoke","Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },dataProvider = "DevApiShots_createShots")
	public void Shots_getShotByUser_verifyData (String userName, String password, String shotDescription, String styleData) throws InterruptedException, JSONException
	{
		String Topic = getOneTopic();
		RequestGenerator request = ShotsHelper.createShots(userName, password, shotDescription, styleData,Topic);
		String response = request.respvalidate.returnresponseasstring();
		String userUIDX = ShotsHelper.getUIDX();
		String objectId = JsonPath.read(response, "$.data.objectId");
		ShotsHelper.printAndLog("Create Shots Service Response : \n ", response);
		AssertJUnit.assertEquals("Create Shots Service is not working",200,request.response.getStatus());
		
		
		request = ShotsHelper.getShotsByCriteria(userName, password, "BY_USER", "", "", "");
		response = request.respvalidate.returnresponseasstring();
		
		JSONArray shotsArray = JsonPath.read(response, "$.data.shots[*]");
		ShotsHelper.printAndLog("Get Shots by User Response : \n ", response);
		AssertJUnit.assertEquals("Get Shots by User is not working",200,request.response.getStatus());

		boolean AllShotsOfSameUser = true;
		int TotalShots = shotsArray.size();
		for(int i=0; i<TotalShots;i++)
		{
			String Path = "$.data.shots["+i+"].authoredBy.uidx";
			System.out.println("Current Path : "+Path);
			if(!userUIDX.equals(JsonPath.read(response,Path)))
				AllShotsOfSameUser=false;	
		}
		AssertJUnit.assertEquals("All the Shots does not belong to same user", true,AllShotsOfSameUser);
		TearDown_DeleteShot(userName, password, objectId);
	}
	
	@Test(groups = { "Smoke","Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },dataProvider = "DevApiShots_createShots")
	public void Shots_getShotByUidx_Success (String userName, String password, String shotDescription, String styleData) throws InterruptedException
	{
		String Topic = getOneTopic();
		RequestGenerator request = ShotsHelper.createShots(userName, password, shotDescription, styleData,Topic);
		String response = request.respvalidate.returnresponseasstring();
		String objectId = JsonPath.read(response, "$.data.objectId");
		ShotsHelper.printAndLog("Create Shots Service Response : \n ", response);
		AssertJUnit.assertEquals("Create Shots Service is not working",200,request.response.getStatus());
		
		
		request = ShotsHelper.getShotsByCriteria(userName, password, "BY_UIDX", "", "", "");
		response = request.respvalidate.returnresponseasstring();
		ShotsHelper.printAndLog("Get Shots by Uidx Response : \n ", response);
		AssertJUnit.assertEquals("Get Shots by Uidx is not working",200,request.response.getStatus());

		TearDown_DeleteShot(userName, password, objectId);
	}
	
	@Test(groups = { "Smoke","Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },dataProvider = "DevApiShots_createShots")
	public void Shots_getShotByPPID_Success (String userName, String password, String shotDescription, String styleData) throws InterruptedException
	{
		String Topic = getOneTopic();
		RequestGenerator request = ShotsHelper.createShots(userName, password, shotDescription, styleData,Topic);
		String response = request.respvalidate.returnresponseasstring();
		String objectId = JsonPath.read(response, "$.data.objectId");
		ShotsHelper.printAndLog("Create Shots Service Response : \n ", response);
		AssertJUnit.assertEquals("Create Shots Service is not working",200,request.response.getStatus());
		
		
		request = ShotsHelper.getShotsByCriteria(userName, password, "BY_PPID", "", "", "");
		response = request.respvalidate.returnresponseasstring();
		ShotsHelper.printAndLog("Get Shots by PPID Response : \n ", response);
		AssertJUnit.assertEquals("Get Shots by PPID is not working",200,request.response.getStatus());

		TearDown_DeleteShot(userName, password, objectId);
	}
	
	
	@Test(groups = { "Smoke","Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },dataProvider = "DevApiShots_getShots_AnotherUser")
	public void Shots_getShotByOtherUser_VerifyFailure (String userName, String password, String shotDescription, String styleData, String userName2) throws InterruptedException
	{
		String Topic = getOneTopic();
		RequestGenerator request = ShotsHelper.createShots(userName, password, shotDescription, styleData,Topic);
		String response = request.respvalidate.returnresponseasstring();
		String objectId = JsonPath.read(response, "$.data.objectId");
		ShotsHelper.printAndLog("Create Shots Service Response : \n ", response);
		AssertJUnit.assertEquals("Create Shots Service is not working",200,request.response.getStatus());
	
		request = ShotsHelper.getShotsByCriteria(userName2, password, "BY_OBJECTID", objectId, "", "");
		response = request.respvalidate.returnresponseasstring();
		ShotsHelper.printAndLog("Get Shots by Object ID Response : \n ", response);
		AssertJUnit.assertEquals("Get Shots by Object ID is not working - Able to Get Other User Shot",500,request.response.getStatus());

	}
	
	@Test(groups = {"Schema Validation" },dataProvider = "DevApiShots_updateShots_AnotherUser")
	public void Shots_updateShotofOtherUser_VerifyFailure(String userName, String password, String shotDescription, String styleData, String updateDescription, String updateStyle, String userName2) throws InterruptedException
	{
		String Topic = getOneTopic();
		
		RequestGenerator request = ShotsHelper.createShots(userName, password, shotDescription, styleData,Topic);
		String response = request.respvalidate.returnresponseasstring();
		String objectId = JsonPath.read(response, "$.data.objectId");
		ShotsHelper.printAndLog("Create Shots Service Response : \n ", response);
		AssertJUnit.assertEquals("Create Shots Service is not working",200,request.response.getStatus());
		
		request=ShotsHelper.updateShots(userName2, password, objectId, updateDescription, updateStyle);
		response = request.respvalidate.returnresponseasstring();
		ShotsHelper.printAndLog("Update Shots Service Response : \n ", response);
		AssertJUnit.assertEquals("Update Shots Service is not working - Able to EDIT Other user Shot",500,request.response.getStatus());
	}
	
	
	@Test(groups = { "Smoke","Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },dataProvider = "DevApiShots_getShots_AnotherUser")
	public void Shots_deleteShotByAnotherUser_verifyFailure (String userName, String password, String shotDescription, String styleData, String userName2) throws InterruptedException
	{
		String Topic = getOneTopic();
		RequestGenerator request = ShotsHelper.createShots(userName, password, shotDescription, styleData, Topic);
		String response = request.respvalidate.returnresponseasstring();
		String objectId = JsonPath.read(response, "$.data.objectId");
		ShotsHelper.printAndLog("Create Shots Service Response : \n ", response);
		AssertJUnit.assertEquals("Create Shots Service is not working",200,request.response.getStatus());
		
		//TearDown
		request = ShotsHelper.deleteShots(userName2, password, objectId);
		response = request.respvalidate.returnresponseasstring();
		ShotsHelper.printAndLog("Delete Shots Service Response : \n ", response);
		AssertJUnit.assertEquals("Delete Shots Service is not working - Able to delete shots of Other user",500,request.response.getStatus());
	}

	@Test(groups = { "Smoke","Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },dataProvider = "DevApiShots_createShots")
	public void Shots_getdeletedShot_VerifyFailure (String userName, String password, String shotDescription, String styleData) throws InterruptedException
	{
		String Topic = getOneTopic();
		RequestGenerator request = ShotsHelper.createShots(userName, password, shotDescription, styleData, Topic);
		String response = request.respvalidate.returnresponseasstring();
		String objectId = JsonPath.read(response, "$.data.objectId");
		ShotsHelper.printAndLog("Create Shots Service Response : \n ", response);
		AssertJUnit.assertEquals("Create Shots Service is not working",200,request.response.getStatus());
		
		TearDown_DeleteShot(userName, password, objectId);
		
		request = ShotsHelper.getShotsByCriteria(userName, password, "BY_OBJECTID", objectId, "", "");
		response = request.respvalidate.returnresponseasstring();
		ShotsHelper.printAndLog("Get Shots by Object ID Response : \n ", response);
		AssertJUnit.assertEquals("Get Shots by Object ID is not working",500,request.response.getStatus());

	}
	
	
	@Test(groups = { "Smoke","Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },dataProvider = "DevApiShots_createShots")
	public void Shots_getShots_withNoShots_VerifySuccess (String userName, String password, String shotDescription, String styleData) throws InterruptedException, JSONException
	{
		String Topic = getOneTopic();
		RequestGenerator request = ShotsHelper.createShots(userName, password, shotDescription, styleData, Topic);
		String response = request.respvalidate.returnresponseasstring();
		String objectId = JsonPath.read(response, "$.data.objectId");
		ShotsHelper.printAndLog("Create Shots Service Response : \n ", response);
		AssertJUnit.assertEquals("Create Shots Service is not working",200,request.response.getStatus());
		
		HoldExecution_Seconds(3);
		
		
		//Clean up all shots of user
		CLEANUP(userName,password);
		
		request = ShotsHelper.getShotsByCriteria(userName, password, "BY_USER", "", "", "");
		response = request.respvalidate.returnresponseasstring();
		JSONObject obj = new JSONObject(response);
		net.minidev.json.JSONArray shots = JsonPath.read(response,"$.data.shots[*]");
		
		ShotsHelper.printAndLog("Get Shots by User Response : \n ", response);
		AssertJUnit.assertEquals("Get Shots by User is not working",200,request.response.getStatus());
		AssertJUnit.assertEquals("Get Shots by User is not working - Shots Available after deleting",true,(shots.size()==0));


	}
}