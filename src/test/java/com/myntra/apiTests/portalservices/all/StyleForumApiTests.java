package com.myntra.apiTests.portalservices.all;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;

import com.myntra.apiTests.dataproviders.StyleForumApiTestsDP;
import com.myntra.apiTests.portalservices.devapiservice.DevApiServiceHelper;
import com.myntra.apiTests.portalservices.StyleForumHelper.StyleForumServiceHelper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.glassfish.jersey.client.ClientConfig;
import net.minidev.json.JSONArray;
import org.json.JSONException;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;


import com.jayway.jsonpath.JsonPath;

import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.Toolbox;
import com.myntra.apiTests.common.APINAME;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.RequestGenerator;


public class StyleForumApiTests extends StyleForumApiTestsDP

{
	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(StyleForumServiceHelper.class);
	static StyleForumServiceHelper styleForumServiceHelper = new StyleForumServiceHelper();
	static DevApiServiceHelper devApiServiceHelper = new DevApiServiceHelper();
	static APIUtilities apiUtil=new APIUtilities();
	
	WebTarget target;
	ClientConfig config;
	Client client;
	Invocation.Builder invBuilder;
	
	//Get User Feeds
	@Test(groups = { "Smoke", "ProdSanity", "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },dataProvider = "GetUserFeeds")
	public void GetUserFeeds_Success(String userName, String password)
	{
		RequestGenerator request= styleForumServiceHelper.getUserFeeds(userName, password);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println("Get User Feeds Response : "+response);
		AssertJUnit.assertEquals("Get User Feeds API is not working!", 200,request.response.getStatus());
	}
	
	@Test(groups = { "Smoke", "ProdSanity", "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },dataProvider = "GetUserFeeds")
	public void GetUserFeeds_EmptyFeedsCheck(String userName, String password) throws JSONException
	{
		RequestGenerator request= styleForumServiceHelper.getUserFeeds(userName, password);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println("Get User Feeds Response : "+response);
		AssertJUnit.assertEquals("Get User Feeds API is not working!", 200,request.response.getStatus());
		
		JSONArray Arr = JsonPath.read(response,"$.data.feed");
		int FeedLength = Arr.size();
		AssertJUnit.assertEquals("Get User Feeds Service Does not return any feed for the user!!!",false,FeedLength<=0);
	}
	
	@Test(groups = { "Smoke", "ProdSanity", "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },dataProvider = "GetUserFeeds")
	public void GetUserFeeds_ValidateSchema(String userName, String password) throws JSONException
	{
		RequestGenerator request= styleForumServiceHelper.getUserFeeds(userName, password);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println("Get User Feeds Response : "+response);
		AssertJUnit.assertEquals("Get User Feeds API is not working!", 200,request.response.getStatus());
		
		JSONArray Arr = JsonPath.read(response,"$.data.feed");
		int FeedLength = Arr.size();
		AssertJUnit.assertEquals("Get User Feeds Service Does not return any feed for the user!!!",false,FeedLength<=0);
		
		try {
            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/styleforum_profile_feeds_getuserfeeds_schema.txt");
            List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(response, jsonschema);
            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in StyleForum Create poll API response", 
            		CollectionUtils.isEmpty(missingNodeList));
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	//GET Feeds
	@Test(groups = {"Smoke", "ProdSanity", "Sanity", "MiniRegression", "ExhaustiveRegression"},dataProvider = "GetFeeds")
	public void StyleForum_Profile_GetFeeds_1_SuccessResponse(String userName, String password, String topicId, String filter)
	{
		RequestGenerator request = styleForumServiceHelper.invokeFeedService(userName, password, topicId,filter);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println("\n Get Feed service response :\n"+response);
		
		AssertJUnit.assertEquals("StyleForum - Get Feed API is not working.",200,request.response.getStatus());
		AssertJUnit.assertEquals("Feeds are null", false,JsonPath.read(response,"$.data.feed[0]").toString().equals(null));
	}
	
	
	//Scroll Page
	@Test(groups = {"Smoke", "ProdSanity", "Sanity", "MiniRegression", "ExhaustiveRegression"},dataProvider = "GetUserFeeds_ScrollFeeds")
	public void Feeds_PageScrollDown(String userName, String password, String topicId, String filter)
	{
		RequestGenerator request= styleForumServiceHelper.getUserFeeds(userName, password);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println("Get User Feeds Response : "+response);
		AssertJUnit.assertEquals("Get User Feeds API is not working!", 200,request.response.getStatus());
		
		JSONArray Arr = JsonPath.read(response,"$.data.feed");
		int FeedLength = Arr.size();
		AssertJUnit.assertEquals("Get User Feeds Service Does not return any feed for the user!!!",false,FeedLength<=0);
	
		String InitialCardId = JsonPath.read(response, "$.data.feed[0].cardId").toString();
		request = styleForumServiceHelper.invokeFeedScroll(userName, password, InitialCardId, topicId,filter);
		response = request.respvalidate.returnresponseasstring();
		System.out.println("Feed Scroll Response : "+response);
		AssertJUnit.assertEquals("Scroll Feeds API is not working!", 200,request.response.getStatus());
		
		InitialCardId = JsonPath.read(response, "$.data.feed[0].cardId").toString();
		request = styleForumServiceHelper.invokeFeedScroll(userName, password, InitialCardId, topicId,filter);
		response = request.respvalidate.returnresponseasstring();
		System.out.println("Feed Scroll Response : "+response);
		AssertJUnit.assertEquals("Scroll Feeds API is not working!", 200,request.response.getStatus());
	
	}
	
	//Create Text Based Poll
	@Test(groups = {"Smoke", "ProdSanity", "Sanity", "MiniRegression", "ExhaustiveRegression"},dataProvider = "Create_TextPoll")
	public void Create_TextPoll_Flow_Success(String userName, String password, String description, String option1, String option2)
	{
		RequestGenerator request = styleForumServiceHelper.invokeCreateTextPollService(userName, password, description, option1, option2);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println("Create Poll API Response : "+response);
		String PollID = JsonPath.read(response,"$.data.id");
		AssertJUnit.assertEquals("Create Poll API is not working!", 200,request.response.getStatus());
		
		request = styleForumServiceHelper.invokePollService_GetPollDetails(userName, password, PollID);
		response = request.respvalidate.returnresponseasstring();
		System.out.println("View Created Poll Response : "+response);
		AssertJUnit.assertEquals("Get Poll details API is not working!", 200,request.response.getStatus());
		AssertJUnit.assertEquals("Get Poll Details API is not working! [Poll ID is different]",PollID,JsonPath.read(response,"$.data.id"));
	
	
	}
	
	@Test(groups = {"Smoke", "ProdSanity", "Sanity", "MiniRegression", "ExhaustiveRegression"},dataProvider = "Create_ProductPoll")
	public void Create_ProductPoll_Flow_Success(String userName, String password, String description, String option1, String option2)
	{
		RequestGenerator request = styleForumServiceHelper.invokeCreateTextPollService(userName, password, description, option1, option2);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println("Create Poll API Response : "+response);
		String PollID = JsonPath.read(response,"$.data.id");
		AssertJUnit.assertEquals("Create Poll API is not working!", 200,request.response.getStatus());
	
	}
	
	
	//[1] Create Poll
	@Test(groups = { "Smoke", "ProdSanity", "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },dataProvider = "CreatePollFlow")
	public void Create_Poll_Flow(String userName, String password, String Question, String Option1, String Option2, String Category)
	{
		RequestGenerator request = styleForumServiceHelper.invokeCreateTextPollService(userName, password, Question, Option1, Option2);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println("Create Poll API Response : "+response);
		String PollID = JsonPath.read(response,"$.data.id");
		AssertJUnit.assertEquals("Create Poll API is not working!", 200,request.response.getStatus());
		
		//View Created Poll
		request = styleForumServiceHelper.invokePollService_GetPollDetails(userName, password, PollID);
		response = request.respvalidate.returnresponseasstring();
		System.out.println("View Created Poll Response : "+response);
		AssertJUnit.assertEquals("Get Poll details API is not working!", 200,request.response.getStatus());
		AssertJUnit.assertEquals("Get Poll Details API is not working! [Poll ID is different]",PollID,JsonPath.read(response,"$.data.id"));
		
		//Update Category
		request = styleForumServiceHelper.invokePollService_UpdatePoll(userName, password, PollID, Question, Option1, Option2, Category);
		response = request.respvalidate.returnresponseasstring();
		System.out.println("Update Category Poll Response : "+response);
		AssertJUnit.assertEquals("Get Poll details API is not working!", 200,request.response.getStatus());
		AssertJUnit.assertEquals("Get Poll Details API is not working! [Poll ID is different]",PollID,JsonPath.read(response,"$.data.id"));
		AssertJUnit.assertEquals("Stlye Forum - Create Poll API is not working. [Description is not proper]", Question,JsonPath.read(response, "$.data.description[0].value"));
		//AssertJUnit.assertEquals("Style Forum - Create Poll API is not working. [First Option is not proper]", Option1,JsonPath.read(response, "$.data.options[0].text"));
		//AssertJUnit.assertEquals("Style Forum - Create Poll API is not working. [Second Option is not proper]", Option2,JsonPath.read(response, "$.data.options[1].text"));
		
		//TearDown [Delete Poll]
		request = styleForumServiceHelper.invokeStyleForumService_Delete(userName, password, APINAME.DELETEPOLL, PollID);
		response = request.respvalidate.returnresponseasstring();
		System.out.println("Delete Poll Response : "+response);
		AssertJUnit.assertEquals("TearDown Activity [ERROR] - Delete Poll Service is not working!",200,request.response.getStatus());
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//Poll Flows
	//Create Poll Flow
	
	
	//Update Poll Flow
	@Test(groups = { "Smoke", "ProdSanity", "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },dataProvider = "UpdatePollFlow")
	public void Update_Poll_Flow(String userName, String password, String Question1A, String Option1A, String Option2A, String Category, String Question1B, String Option1B, String Option2B)
	{
		//Create Poll
		RequestGenerator request = styleForumServiceHelper.invokeCreateTextPollService(userName, password, Question1A, Option1A, Option2A);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println("Create Poll API Response : "+response);
		String PollID = JsonPath.read(response,"$.data.id");
		
		//View Created Poll
		request = styleForumServiceHelper.invokePollService_GetPollDetails(userName, password, PollID);
		response = request.respvalidate.returnresponseasstring();
		System.out.println("View Created Poll Response : "+response);
		AssertJUnit.assertEquals("Get Poll details API is not working!", 200,request.response.getStatus());
		AssertJUnit.assertEquals("Get Poll Details API is not working! [Poll ID is different]",PollID,JsonPath.read(response,"$.data.id"));
		
		//Update Created Poll
		request = styleForumServiceHelper.invokePollService_UpdatePoll(userName, password, PollID, Question1B, Option1B, Option2B, Category);
		response = request.respvalidate.returnresponseasstring();
		System.out.println("Update Category Poll Response : "+response);
		AssertJUnit.assertEquals("Get Poll details API is not working!", 200,request.response.getStatus());
		AssertJUnit.assertEquals("Get Poll Details API is not working! [Poll ID is different]",PollID,JsonPath.read(response,"$.data.id"));
		AssertJUnit.assertEquals("Stlye Forum - Create Poll API is not working. [Description is not proper]", Question1B,JsonPath.read(response, "$.data.description[0].value"));
		//AssertJUnit.assertEquals("Style Forum - Create Poll API is not working. [First Option is not proper]", Option1B,JsonPath.read(response, "$.data.options[0].text"));
		//AssertJUnit.assertEquals("Style Forum - Create Poll API is not working. [Second Option is not proper]", Option2B,JsonPath.read(response, "$.data.options[1].text"));
		
		//View Created Poll
		request = styleForumServiceHelper.invokePollService_GetPollDetails(userName, password, PollID);
		response = request.respvalidate.returnresponseasstring();
		System.out.println("View Created Poll Response : "+response);
		AssertJUnit.assertEquals("Get Poll details API is not working!", 200,request.response.getStatus());
		AssertJUnit.assertEquals("Get Poll Details API is not working! [Poll ID is different]",PollID,JsonPath.read(response,"$.data.id"));
		
		//TearDown [Delete Poll]
		request = styleForumServiceHelper.invokeStyleForumService_Delete(userName, password, APINAME.DELETEPOLL, PollID);
		response = request.respvalidate.returnresponseasstring();
		System.out.println("Delete Poll Response : "+response);
		AssertJUnit.assertEquals("TearDown Activity [ERROR] - Delete Poll Service is not working!",200,request.response.getStatus());
		
	}

	//Vote For Poll Flow
	@Test(groups = { "Smoke", "ProdSanity", "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },dataProvider = "VoteForPollFlow")
	public void Vote_Poll_Flow(String userName1, String password1, String userName2, String password2, String userName3, String Question, String Option1, String Option2, String Category)
	{
		//Create Poll
		RequestGenerator request = styleForumServiceHelper.invokeCreateTextPollService(userName1, password1, Question, Option1, Option2);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println("Create Poll API Response : "+response);
		String PollID = JsonPath.read(response,"$.data.id");
		AssertJUnit.assertEquals("Create Poll API is not working!", 200,request.response.getStatus());
		
		//View Created Poll
		request = styleForumServiceHelper.invokePollService_GetPollDetails(userName1, password1, PollID);
		response = request.respvalidate.returnresponseasstring();
		System.out.println("View Created Poll Response : "+response);
		AssertJUnit.assertEquals("Get Poll details API is not working!", 200,request.response.getStatus());
		AssertJUnit.assertEquals("Get Poll Details API is not working! [Poll ID is different]",PollID,JsonPath.read(response,"$.data.id"));
		
		//Update Category
		request = styleForumServiceHelper.invokePollService_UpdatePoll(userName1, password1, PollID, Question, Option1, Option2, Category);
		response = request.respvalidate.returnresponseasstring();
		System.out.println("Update Category Poll Response : "+response);
		AssertJUnit.assertEquals("Get Poll details API is not working!", 200,request.response.getStatus());
		AssertJUnit.assertEquals("Get Poll Details API is not working! [Poll ID is different]",PollID,JsonPath.read(response,"$.data.id"));
		AssertJUnit.assertEquals("Stlye Forum - Create Poll API is not working. [Description is not proper]", Question,JsonPath.read(response, "$.data.description[0].value"));
		String OptionID1 = JsonPath.read(response,"$.data.options[0].id");
        String OptionID2 = JsonPath.read(response,"$.data.options[1].id");
		//AssertJUnit.assertEquals("Style Forum - Create Poll API is not working. [First Option is not proper]", Option1,JsonPath.read(response, "$.data.options[0].text"));
		//AssertJUnit.assertEquals("Style Forum - Create Poll API is not working. [Second Option is not proper]", Option2,JsonPath.read(response, "$.data.options[1].text"));
	
		//Vote Option1 Check Count
		request = styleForumServiceHelper.invokePollService_VoteForPoll(userName2, password2, PollID, OptionID1);
		response = request.respvalidate.returnresponseasstring();
		System.out.println("Vote For Poll Response : "+response);
		AssertJUnit.assertEquals("Vote Poll API is not working!", 200,request.response.getStatus());
		AssertJUnit.assertEquals("Create Poll Data Validation Error - Poll Type is Null!", false,JsonPath.read(response,"$.data.pollType").toString().equals(null));
		AssertJUnit.assertEquals("Create Poll Data Validation Error - Poll ID is Null!", false,JsonPath.read(response,"$.data.id").toString().equals(null));
		AssertJUnit.assertEquals("Create Poll Data Validation Error - Time Left is Null!", false,JsonPath.read(response,"$.data.timeLeft").toString().equals(null));
		AssertJUnit.assertEquals("Create Poll Data Validation Error - Comment Count is Null!", false,JsonPath.read(response,"$.data.commentCount").toString().equals(null));
		AssertJUnit.assertEquals("Create Poll Data Validation Error - Option[1] ID is Null!", false,JsonPath.read(response,"$.data.options[0].label").toString().equals(null));
		AssertJUnit.assertEquals("Create Poll Data Validation Error - Option[1] Label is Null!", false,JsonPath.read(response,"$.data.options[0].id").toString().equals(null));
		AssertJUnit.assertEquals("Create Poll Data Validation Error - Option[1] VotesCount is Null!", false,JsonPath.read(response,"$.data.options[0].votes").toString().equals(null));

		AssertJUnit.assertEquals("Create Poll Data Validation Error - Option[2] ID is Null!", false,JsonPath.read(response,"$.data.options[1].label").toString().equals(null));
		AssertJUnit.assertEquals("Create Poll Data Validation Error - Option[2] Label is Null!", false,JsonPath.read(response,"$.data.options[1].id").toString().equals(null));
		AssertJUnit.assertEquals("Create Poll Data Validation Error - Option[2] VotesCount is Null!", false,JsonPath.read(response,"$.data.options[1].votes").toString().equals(null));
		AssertJUnit.assertEquals("Create Poll Data Validation Error - Total VotesCount is Null!", false,JsonPath.read(response,"$.data.totalVotes").toString().equals(null));
		
		//Check Vote Count
		request = styleForumServiceHelper.invokePollService_GetPollDetails(userName1, password1, PollID);
		response = request.respvalidate.returnresponseasstring();
		System.out.println("View Poll Details Response : "+response);
		AssertJUnit.assertEquals("Get Poll details API is not working!", 200,request.response.getStatus());
		AssertJUnit.assertEquals("Vote for Poll API is not working!-[Vote Count is not proper]", 1,Integer.parseInt(JsonPath.read(response,"$.data.options[0].votes")));
		
		//Vote Option2 Check Count
		request = styleForumServiceHelper.invokePollService_VoteForPoll(userName3, "randompass", PollID, OptionID2);
		response = request.respvalidate.returnresponseasstring();
		System.out.println("Vote For Poll Response : "+response);
		AssertJUnit.assertEquals("Vote Poll API is not working!", 200,request.response.getStatus());
		AssertJUnit.assertEquals("Create Poll Data Validation Error - Poll ID is Null!", false,JsonPath.read(response,"$.data.id").toString().equals(null));
		AssertJUnit.assertEquals("Create Poll Data Validation Error - Time Left is Null!", false,JsonPath.read(response,"$.data.timeLeft").toString().equals(null));
		AssertJUnit.assertEquals("Create Poll Data Validation Error - Comment Count is Null!", false,JsonPath.read(response,"$.data.commentCount").toString().equals(null));
		AssertJUnit.assertEquals("Create Poll Data Validation Error - Option[1] ID is Null!", false,JsonPath.read(response,"$.data.options[0].label").toString().equals(null));
		AssertJUnit.assertEquals("Create Poll Data Validation Error - Option[1] Label is Null!", false,JsonPath.read(response,"$.data.options[0].id").toString().equals(null));
		AssertJUnit.assertEquals("Create Poll Data Validation Error - Option[1] VotesCount is Null!", false,JsonPath.read(response,"$.data.options[0].votes").toString().equals(null));

		AssertJUnit.assertEquals("Create Poll Data Validation Error - Option[2] ID is Null!", false,JsonPath.read(response,"$.data.options[1].label").toString().equals(null));
		AssertJUnit.assertEquals("Create Poll Data Validation Error - Option[2] Label is Null!", false,JsonPath.read(response,"$.data.options[1].id").toString().equals(null));
		AssertJUnit.assertEquals("Create Poll Data Validation Error - Option[2] VotesCount is Null!", false,JsonPath.read(response,"$.data.options[1].votes").toString().equals(null));
		AssertJUnit.assertEquals("Create Poll Data Validation Error - Total VotesCount is Null!", false,JsonPath.read(response,"$.data.totalVotes").toString().equals(null));

		
		//Check Vote Count
		request = styleForumServiceHelper.invokePollService_GetPollDetails(userName1, password1, PollID);
		response = request.respvalidate.returnresponseasstring();
		AssertJUnit.assertEquals("Get Poll details API is not working!", 200,request.response.getStatus());
		AssertJUnit.assertEquals("Vote for Poll API is not working!-[Vote Count is not proper]", 1,Integer.parseInt(JsonPath.read(response,"$.data.options[1].votes")));
				
		
		//Tear Down Activity
		request = styleForumServiceHelper.invokeStyleForumService_Delete(userName1, password1, APINAME.DELETEPOLL, PollID);
		response = request.respvalidate.returnresponseasstring();
		System.out.println("Delete Poll Response : "+response);
		AssertJUnit.assertEquals("TearDown Activity [ERROR] - Delete Poll Service is not working!",200,request.response.getStatus());
		
	}
	
	
	//Comment on Poll Flow
	@Test(groups = { "Smoke", "ProdSanity", "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },dataProvider = "CommentonPollFlow")
	public void Comment_On_Poll_Flow(String userName1, String password1, String userName2, String password2, String Question, String Option1, String Option2, String Category, String Comment, String returnLimit)
	{
		RequestGenerator request = styleForumServiceHelper.invokeCreateTextPollService(userName1, password1, Question, Option1, Option2);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println("Create Poll API Response : "+response);
		String PollID = JsonPath.read(response,"$.data.id");
		AssertJUnit.assertEquals("Create Poll API is not working!", 200,request.response.getStatus());
		
		//View Created Poll
		request = styleForumServiceHelper.invokePollService_GetPollDetails(userName1, password1, PollID);
		response = request.respvalidate.returnresponseasstring();
		System.out.println("View Created Poll Response : "+response);
		AssertJUnit.assertEquals("Get Poll details API is not working!", 200,request.response.getStatus());
		AssertJUnit.assertEquals("Get Poll Details API is not working! [Poll ID is different]",PollID,JsonPath.read(response,"$.data.id"));
		
		//Update Category
		request = styleForumServiceHelper.invokePollService_UpdatePoll(userName1, password1, PollID, Question, Option1, Option2, Category);
		response = request.respvalidate.returnresponseasstring();
		System.out.println("Update Category Poll Response : "+response);
		AssertJUnit.assertEquals("Get Poll details API is not working!", 200,request.response.getStatus());
		AssertJUnit.assertEquals("Get Poll Details API is not working! [Poll ID is different]",PollID,JsonPath.read(response,"$.data.id").toString());
		AssertJUnit.assertEquals("Stlye Forum - Create Poll API is not working. [Description is not proper]", Question,JsonPath.read(response, "$.data.description[0].value"));
		String OptionID1 = JsonPath.read(response,"$.data.options[0].id");
        String OptionID2 = JsonPath.read(response,"$.data.options[1].id");
		//AssertJUnit.assertEquals("Style Forum - Create Poll API is not working. [First Option is not proper]", Option1,JsonPath.read(response, "$.data.options[0].text"));
		//AssertJUnit.assertEquals("Style Forum - Create Poll API is not working. [Second Option is not proper]", Option2,JsonPath.read(response, "$.data.options[1].text"));
        
        //Comment on Poll
        request = styleForumServiceHelper.invokePollService_CommentOnPoll(userName2, password2, PollID, Comment);
        response = request.respvalidate.returnresponseasstring();
        System.out.println("Comment on Poll API Response : "+response);
        AssertJUnit.assertEquals("StyleForum - Comment on Poll API is not working.",200,request.response.getStatus());
        AssertJUnit.assertEquals("StyleForum -Comment on Poll API is not working. [Poll ID is different or invalid]",PollID,JsonPath.read(response,"$.data.parentId").toString());
        AssertJUnit.assertEquals("StyleForum - Comment on Poll API is not working. [Comment is different or invalid]",Comment,JsonPath.read(response,"$.data.description[0].value"));
        AssertJUnit.assertEquals("StyleForum - Comment on Poll API is not working. [ID is null]",false,JsonPath.read(response,"$.data.id").toString().equals(null));
        AssertJUnit.assertEquals("StyleForum - Comment on Poll API is not working. [Type is null]",false,JsonPath.read(response,"$.data.type").toString().equals(null));
        AssertJUnit.assertEquals("StyleForum - Comment on Poll API is not working. [Parent ID is null]",false,JsonPath.read(response,"$.data.parentId").toString().equals(null));
        AssertJUnit.assertEquals("StyleForum - Comment on Poll API is not working. [Parent Type is null]",false,JsonPath.read(response,"$.data.parentType").toString().equals(null));

        //Get Poll Comments
      	request = styleForumServiceHelper.invokeStyleForumService_GetPollComments(userName1, password1, PollID, returnLimit);
      	response = request.respvalidate.returnresponseasstring();
      	System.out.println("View Created Poll Response : "+response);
      	AssertJUnit.assertEquals("Get Poll Comments API is not working!", 200,request.response.getStatus());
      	AssertJUnit.assertEquals("Get Poll Comments API is not working! [Poll ID is different]",PollID,JsonPath.read(response,"$.data.comments[0].parentId").toString());
      	AssertJUnit.assertEquals("Get Poll Comments API is not working! [User Comment is not returned in poll]!",Comment,JsonPath.read(response,"$.data.comments[0].description[0].value" ));
      	
      	//Tear Down Activity
      	request = styleForumServiceHelper.invokeStyleForumService_Delete(userName1, password1, APINAME.DELETEPOLL, PollID);
      	response = request.respvalidate.returnresponseasstring();
      	System.out.println("Delete Poll Response : "+response);
      	AssertJUnit.assertEquals("TearDown Activity [ERROR] - Delete Poll Service is not working!",200,request.response.getStatus());
      		
	}

	//Comment on Poll Flow
	@Test(groups = { "Smoke", "ProdSanity", "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },dataProvider = "CommentonPollFlow")
	public void MarkCommentasSpamandUnSpamFlow(String userName1, String password1, String userName2, String password2, String Question, String Option1, String Option2, String Category, String Comment, String returnLimit)
	{
		//Create Poll
		RequestGenerator request = styleForumServiceHelper.invokeCreateTextPollService(userName1, password1, Question, Option1, Option2);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println("Create Poll API Response : "+response);
		String PollID = JsonPath.read(response,"$.data.id").toString();
		AssertJUnit.assertEquals("Create Poll API is not working!", 200,request.response.getStatus());
		
		//View Created Poll
		request = styleForumServiceHelper.invokePollService_GetPollDetails(userName1, password1, PollID);
		response = request.respvalidate.returnresponseasstring();
		System.out.println("View Created Poll Response : "+response);
		AssertJUnit.assertEquals("Get Poll details API is not working!", 200,request.response.getStatus());
		AssertJUnit.assertEquals("Get Poll Details API is not working! [Poll ID is different]",PollID,JsonPath.read(response,"$.data.id").toString());
		
		//Update Category
		request = styleForumServiceHelper.invokePollService_UpdatePoll(userName1, password1, PollID, Question, Option1, Option2, Category);
		response = request.respvalidate.returnresponseasstring();
		System.out.println("Update Category Poll Response : "+response);
		AssertJUnit.assertEquals("Get Poll details API is not working!", 200,request.response.getStatus());
		AssertJUnit.assertEquals("Get Poll Details API is not working! [Poll ID is different]",PollID,JsonPath.read(response,"$.data.id").toString());
		AssertJUnit.assertEquals("Stlye Forum - Create Poll API is not working. [Description is not proper]", Question,JsonPath.read(response, "$.data.description[0].value"));
		String OptionID1 = JsonPath.read(response,"$.data.options[0].id".toString());
        String OptionID2 = JsonPath.read(response,"$.data.options[1].id".toString());
		//AssertJUnit.assertEquals("Style Forum - Create Poll API is not working. [First Option is not proper]", Option1,JsonPath.read(response, "$.data.options[0].text"));
		//AssertJUnit.assertEquals("Style Forum - Create Poll API is not working. [Second Option is not proper]", Option2,JsonPath.read(response, "$.data.options[1].text"));
        
        //Comment on Poll
        request = styleForumServiceHelper.invokePollService_CommentOnPoll(userName2, password2, PollID, Comment);
        response = request.respvalidate.returnresponseasstring();
        System.out.println("Comment on Poll API Response : "+response);
        AssertJUnit.assertEquals("StyleForum - Comment on Poll API is not working.",200,request.response.getStatus());
        AssertJUnit.assertEquals("StyleForum -Comment on Poll API is not working. [Poll ID is different or invalid]",PollID,JsonPath.read(response,"$.data.parentId").toString());
        AssertJUnit.assertEquals("StyleForum - Comment on Poll API is not working. [Comment is different or invalid]",Comment,JsonPath.read(response,"$.data.description[0].value"));
        
        //Get Poll Comments
      	request = styleForumServiceHelper.invokeStyleForumService_GetPollComments(userName1, password1, PollID, returnLimit);
      	response = request.respvalidate.returnresponseasstring();
      	System.out.println("View Created Poll Response : "+response);
      	String CommentID = JsonPath.read(response, "$.data.comments[0].id").toString();  
      	System.out.println("Comment ID : "+CommentID);
      	AssertJUnit.assertEquals("Get Poll Comments API is not working!", 200,request.response.getStatus());
      	AssertJUnit.assertEquals("Get Poll Comments API is not working! [Poll ID is different]",PollID,JsonPath.read(response,"$.data.comments[0].parentId").toString());
      	AssertJUnit.assertEquals("Get Poll Comments API is not working! [User Comment is not returned in poll]!",Comment,JsonPath.read(response,"$.data.comments[0].description[0].value" ));
      	
      	//Mark Comment as Spam
      	System.out.println("MARKING COMMENT AS SPAM");
      	request = styleForumServiceHelper.invokeStyleForumService_SpamAction(userName1, password1, APINAME.MARKCOMMENTASSPAM, CommentID, CommentID, returnLimit);
      	response = request.respvalidate.returnresponseasstring();
      	System.out.println("Mark Comment As Spam Response : "+response);
      	AssertJUnit.assertEquals("Mark as Spam API is not working", 200,request.response.getStatus());
      	System.out.println("Comment Marked AS SPAM");
      	
      	//Get Personalized Data
		/*request =  styleForumServiceHelper.invokeStyleForumService_GetPersonalizedData(userName1, password1, PollID, "POLL");
		response = request.respvalidate.returnresponseasstring();
		System.out.println(response);
	*/	//AssertJUnit.assertEquals("Like an Answer API is not working",true,JsonPath.read(response,"$.data.post[0].isLiked" ));
		//AssertJUnit.assertEquals("Is Owner Property is not returning expected data",true,JsonPath.read(response,"$.data.post[0].isOwner" ));

      	
      	
      	
      	//UnMark Comment as Spam
      	request = styleForumServiceHelper.invokeStyleForumService_SpamAction(userName1, password1, APINAME.UNMARKCOMMENTASSPAM, "1", CommentID, returnLimit);
      	response = request.respvalidate.returnresponseasstring();
      	System.out.println("UnMark Comment As Spam Response : "+response);
      	AssertJUnit.assertEquals("UnMark as Spam API is not working", 200,request.response.getStatus());
      	
      	//Get Poll Comments
      	request = styleForumServiceHelper.invokeStyleForumService_GetPollComments(userName1, password1, PollID, returnLimit);
      	response = request.respvalidate.returnresponseasstring();
      	System.out.println("View Created Poll Response : "+response);
      	AssertJUnit.assertEquals("Get Poll Comments API is not working!", 200,request.response.getStatus());
      	AssertJUnit.assertEquals("Get Poll Comments API is not working! [Poll ID is different]",PollID,JsonPath.read(response,"$.data.comments[0].parentId").toString());

      //Get Personalized Data
       /*request =  styleForumServiceHelper.invokeStyleForumService_GetPersonalizedData(userName1, password1, CommentID, "COMMENT");
       response = request.respvalidate.returnresponseasstring();
       System.out.println(response);
       AssertJUnit.assertEquals("Like an Answer API is not working",true,JsonPath.read(response,"$.data.post[0].isLiked" ));
       AssertJUnit.assertEquals("Is Owner Property is not returning expected data",true,JsonPath.read(response,"$.data.post[0].isOwner" ));
*/
      	
      	//Tear Down Activity
      	request = styleForumServiceHelper.invokeStyleForumService_Delete(userName1, password1, APINAME.DELETEPOLL, PollID);
      	response = request.respvalidate.returnresponseasstring();
      	System.out.println("Delete Poll Response : "+response);
      	AssertJUnit.assertEquals("TearDown Activity [ERROR] - Delete Poll Service is not working!",200,request.response.getStatus());
      		
		}	
	
	
	//Comment on Poll Flow
	@Test(groups = { "Smoke", "ProdSanity", "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },dataProvider = "CommentonPollFlow")
	public void PollCommentsDisplayLimit(String userName1, String password1, String userName2, String password2, String Question, String Option1, String Option2, String Category, String Comment, String returnLimit)
	{
		//Create Poll
		RequestGenerator request = styleForumServiceHelper.invokeCreateTextPollService(userName1, password1, Question, Option1, Option2);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println("Create Poll API Response : "+response);
		String PollID = JsonPath.read(response,"$.data.id");
		AssertJUnit.assertEquals("Create Poll API is not working!", 200,request.response.getStatus());
		
		//View Created Poll
		request = styleForumServiceHelper.invokePollService_GetPollDetails(userName1, password1, PollID);
		response = request.respvalidate.returnresponseasstring();
		System.out.println("View Created Poll Response : "+response);
		AssertJUnit.assertEquals("Get Poll details API is not working!", 200,request.response.getStatus());
		AssertJUnit.assertEquals("Get Poll Details API is not working! [Poll ID is different]",PollID,JsonPath.read(response,"$.data.id"));
		
		//Update Category
		request = styleForumServiceHelper.invokePollService_UpdatePoll(userName1, password1, PollID, Question, Option1, Option2, Category);
		response = request.respvalidate.returnresponseasstring();
		System.out.println("Update Category Poll Response : "+response);
		AssertJUnit.assertEquals("Get Poll details API is not working!", 200,request.response.getStatus());
		AssertJUnit.assertEquals("Get Poll Details API is not working! [Poll ID is different]",PollID,JsonPath.read(response,"$.data.id"));
		AssertJUnit.assertEquals("Stlye Forum - Create Poll API is not working. [Description is not proper]", Question,JsonPath.read(response, "$.data.description[0].value"));
		String OptionID1 = JsonPath.read(response,"$.data.options[0].id");
        String OptionID2 = JsonPath.read(response,"$.data.options[1].id");
		//AssertJUnit.assertEquals("Style Forum - Create Poll API is not working. [First Option is not proper]", Option1,JsonPath.read(response, "$.data.options[0].text"));
		//AssertJUnit.assertEquals("Style Forum - Create Poll API is not working. [Second Option is not proper]", Option2,JsonPath.read(response, "$.data.options[1].text"));
        
       String RawComment = Comment;
        for (int i=0; i<=Integer.parseInt(returnLimit);i++)
        
        {
        	//Comment on Poll
        	Comment=Comment.concat(Integer.toString(i+1));
        	System.out.println("\nComment USED : "+Comment);
        	request = styleForumServiceHelper.invokePollService_CommentOnPoll(userName2, password2, PollID, Comment);
        	response = request.respvalidate.returnresponseasstring();
        	System.out.println("Comment on Poll API Response : "+response);
        	AssertJUnit.assertEquals("StyleForum - Comment on Poll API is not working.",200,request.response.getStatus());
        	AssertJUnit.assertEquals("StyleForum -Comment on Poll API is not working. [Poll ID is different or invalid]",PollID,JsonPath.read(response,"$.data.parentId").toString());
        //	AssertJUnit.assertEquals("StyleForum - Comment on Poll API is not working. [Comment is different or invalid]",Comment,JsonPath.read(response,"$.data.description["+i+"].value").toString());
        	Comment = RawComment;
        }
        
        request=styleForumServiceHelper.invokeStyleForumService_GetPollComments(userName1, password1, PollID, "1");
        response = request.respvalidate.returnresponseasstring();
        System.out.println("Get Poll Details Response : "+response);
        
        String CheckComment = "No Comments!!!"+Integer.toString((Integer.parseInt(returnLimit)+1));
        AssertJUnit.assertEquals("Style Forum - Get Poll Details API is not working.", CheckComment, JsonPath.read(response, "$.data.comments[0].description[0].value"));
	
	}
	
	
	//Create a Poll
			@Test(groups = {"Smoke", "ProdSanity", "Sanity", "MiniRegression", "ExhaustiveRegression"},dataProvider = "CreatePollFlow")
			public void StyleForum_Poll_CreatePoll_verifySuccess(String userName, String password, String Question, String Option1, String Option2, String Category)
			{
				RequestGenerator request = styleForumServiceHelper.invokeCreateTextPollService(userName, password, Question, Option1, Option2);
				String response = request.respvalidate.returnresponseasstring();
				System.out.println("Create Poll API Response : "+response);
				String PollID = JsonPath.read(response,"$.data.id");
				AssertJUnit.assertEquals("Create Poll API is not working!", 200,request.response.getStatus());
				
				request=styleForumServiceHelper.invokeStyleForumService_Delete(userName, password, APINAME.DELETEPOLL, PollID);
				AssertJUnit.assertEquals("Style Forum Tests - TearDown Activity [ERROR] - Delete Poll Service is not working!",200,request.response.getStatus());
				
			}
			
			@Test(groups = {"Smoke", "ProdSanity", "Sanity", "MiniRegression", "ExhaustiveRegression"},dataProvider = "CreatePollFlow")
			public void StyleForum_Poll_CreatePoll_verifyData(String userName, String password, String Question, String Option1, String Option2, String Category)
			{
				RequestGenerator request = styleForumServiceHelper.invokeCreateTextPollService(userName, password, Question, Option1, Option2);
				String response = request.respvalidate.returnresponseasstring();
				System.out.println("Create Poll API Response : "+response);
				String PollID = JsonPath.read(response,"$.data.id");
				AssertJUnit.assertEquals("Create Poll API is not working!", 200,request.response.getStatus());
				AssertJUnit.assertEquals("Stlye Forum - Create Poll API is not working. [Description is not proper]", Question,JsonPath.read(response, "$.data.description[0].value"));
				AssertJUnit.assertEquals("Stlye Forum - Create Poll API is not working. [First Option is not proper]", Option1,JsonPath.read(response, "$.data.options[0].text"));
				AssertJUnit.assertEquals("Stlye Forum - Create Poll API is not working. [Second Option is not proper]", Option2,JsonPath.read(response, "$.data.options[1].text"));
				
				PollID = JsonPath.read(response, "$.data.id");
				request=styleForumServiceHelper.invokeStyleForumService_Delete(userName, password, APINAME.DELETEPOLL, PollID);
				AssertJUnit.assertEquals("Style Forum Tests - TearDown Activity [ERROR] - Delete Poll Service is not working!",200,request.response.getStatus());
				
			}
			
			@Test(groups = {"Schema Validation"},dataProvider = "CreatePollFlow")
			public void StyleForum_Poll_CreatePoll_schemaValidation(String userName, String password, String Question, String Option1, String Option2, String Category)
			{
				RequestGenerator request = styleForumServiceHelper.invokeCreateTextPollService(userName, password, Question, Option1, Option2);
				String response = request.respvalidate.returnresponseasstring();
				System.out.println("Create Poll API Response : "+response);
				
				AssertJUnit.assertEquals("StyleForum - Create Poll API is not working.",200,request.response.getStatus());
				try {
		            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/styleforum_profile_createpoll_schema.txt");
		            List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(response, jsonschema);
		            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in StyleForum Create poll API response", 
		            		CollectionUtils.isEmpty(missingNodeList));
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
				
				String PollID = JsonPath.read(response, "$.data.id");
				request=styleForumServiceHelper.invokeStyleForumService_Delete(userName, password, APINAME.DELETEPOLL, PollID);
				AssertJUnit.assertEquals("Style Forum Tests - TearDown Activity [ERROR] - Delete Poll Service is not working!",200,request.response.getStatus());
				
				}
			
			
			//Update a Poll
			@Test(groups = {"Smoke", "ProdSanity", "Sanity", "MiniRegression", "ExhaustiveRegression"},dataProvider = "StyleForum_Poll_UpdatePoll")
			public void StyleForum_Poll_UpdatePoll_verifySuccess(String userName, String password, String Question, String Option1, String Option2, String UpdateQuestion, String UpdatedOption1, String UpdatedOption2)
			{
				RequestGenerator request = styleForumServiceHelper.invokeCreateTextPollService(userName, password, Question, Option1, Option2);				String response = request.respvalidate.returnresponseasstring();
				System.out.println("\n Create Poll Response :\n"+response);
				AssertJUnit.assertEquals("StyleForum - Create Poll API is not working.",200,request.response.getStatus());
				
				String PollID = JsonPath.read(response, "$.data.id");
				RequestGenerator updateRequest = styleForumServiceHelper.invokePollService_UpdatePoll(userName, password,PollID,UpdateQuestion, UpdatedOption1, UpdatedOption2,"1");
				String updateResponse = updateRequest.respvalidate.returnresponseasstring();
				System.out.println("\n Update Poll Respone :\n"+updateResponse);
				AssertJUnit.assertEquals("StyleForum - Update Poll API is not working.",200,updateRequest.response.getStatus());
				
				request=styleForumServiceHelper.invokeStyleForumService_Delete(userName, password, APINAME.DELETEPOLL, PollID);
				AssertJUnit.assertEquals("Style Forum Tests - TearDown Activity [ERROR] - Delete Poll Service is not working!",200,request.response.getStatus());
				
			}
			
			@Test(groups = {"Smoke", "ProdSanity", "Sanity", "MiniRegression", "ExhaustiveRegression"},dataProvider = "StyleForum_Poll_UpdatePoll")
			public void StyleForum_Poll_UpdatePoll_verifyData(String userName, String password, String Question, String Option1, String Option2, String UpdateQuestion, String UpdatedOption1, String UpdatedOption2)
			{
				RequestGenerator request = styleForumServiceHelper.invokeCreateTextPollService(userName, password, Question, Option1, Option2);
				String response = request.respvalidate.returnresponseasstring();
				System.out.println("Create Poll API Response : "+response);
				String PollID = JsonPath.read(response,"$.data.id");
				AssertJUnit.assertEquals("Create Poll API is not working!", 200,request.response.getStatus());
				
		
				RequestGenerator updateRequest = styleForumServiceHelper.invokePollService_UpdatePoll(userName, password,PollID,UpdateQuestion, UpdatedOption1, UpdatedOption2,"1");
				String updateResponse = updateRequest.respvalidate.returnresponseasstring();
				System.out.println("\n Update Poll Respone :\n"+updateResponse);
				AssertJUnit.assertEquals("StyleForum - Update Poll API is not working.",200,updateRequest.response.getStatus());
				AssertJUnit.assertEquals("Stlye Forum - Get Poll Details API is not working. [Poll Question is not proper]", UpdateQuestion,JsonPath.read(updateResponse, "$.data.description[0].value"));
				
				request=styleForumServiceHelper.invokeStyleForumService_Delete(userName, password, APINAME.DELETEPOLL, PollID);
				AssertJUnit.assertEquals("Style Forum Tests - TearDown Activity [ERROR] - Delete Poll Service is not working!",200,request.response.getStatus());
				
			}
			
			@Test(groups = {"Schema Validation"},dataProvider = "StyleForum_Poll_UpdatePoll")
			public void StyleForum_Poll_UpdatePoll_verifySchema (String userName, String password, String Question, String Option1, String Option2, String UpdateQuestion, String UpdatedOption1, String UpdatedOption2)
			{
				RequestGenerator request = styleForumServiceHelper.invokeCreateTextPollService(userName, password, Question, Option1, Option2);
				String response = request.respvalidate.returnresponseasstring();
				System.out.println("Create Poll API Response : "+response);
				String PollID = JsonPath.read(response,"$.data.id");
				AssertJUnit.assertEquals("Create Poll API is not working!", 200,request.response.getStatus());

				
				RequestGenerator updateRequest = styleForumServiceHelper.invokePollService_UpdatePoll(userName, password,PollID,UpdateQuestion, UpdatedOption1, UpdatedOption2,"1");
				String updateResponse = updateRequest.respvalidate.returnresponseasstring();
				System.out.println("\n Update Poll Respone :\n"+updateResponse);
				AssertJUnit.assertEquals("StyleForum - Update Poll API is not working.",200,updateRequest.response.getStatus());
				
				try {
		            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/styleforum-poll-updatepollresponse-schema.txt");
		            List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(response, jsonschema);
		            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in StyleForum Create poll API response", 
		            		CollectionUtils.isEmpty(missingNodeList));
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
				
				request=styleForumServiceHelper.invokeStyleForumService_Delete(userName, password, APINAME.DELETEPOLL, PollID);
				AssertJUnit.assertEquals("Style Forum Tests - TearDown Activity [ERROR] - Delete Poll Service is not working!",200,request.response.getStatus());
				
			}
				
			@Test(groups = {"Smoke", "ProdSanity", "Sanity", "MiniRegression", "ExhaustiveRegression"},dataProvider = "StyleForum_Poll_UpdatePoll_Failure")
			public void StyleForum_Poll_UpdateInvalidPoll_verifyFailure(String userName, String password, String UpdateQuestion, String UpdatedOption1, String UpdatedOption2)
			{
				RequestGenerator updateRequest = styleForumServiceHelper.invokePollService_UpdatePoll(userName, password,"wer34",UpdateQuestion, UpdatedOption1, UpdatedOption2,"1");
				String updateResponse = updateRequest.respvalidate.returnresponseasstring();
				System.out.println("\n Update Poll Respone :\n"+updateResponse);
				AssertJUnit.assertEquals("StyleForum - Update Poll API is not working.",500,updateRequest.response.getStatus());
				
				
			}
			
			
			
			//GET Poll Details
			@Test(groups = {"Smoke", "ProdSanity", "Sanity", "MiniRegression", "ExhaustiveRegression"},dataProvider = "CreatePollFlow")
			public void StyleForum_Poll_GetPollDetails_verifySuccess(String userName, String password, String Question, String Option1, String Option2, String Category)
			{
				RequestGenerator request = styleForumServiceHelper.invokeCreateTextPollService(userName, password, Question, Option1, Option2);
				String response = request.respvalidate.returnresponseasstring();
				System.out.println("Create Poll API Response : "+response);
				String PollID = JsonPath.read(response,"$.data.id");
				AssertJUnit.assertEquals("Create Poll API is not working!", 200,request.response.getStatus());

				RequestGenerator getRequest = styleForumServiceHelper.invokePollService_GetPollDetails(userName, password, PollID);
				String PollResponse = getRequest.respvalidate.returnresponseasstring();
				System.out.println("\n Get Poll Details Response : \n\n"+PollResponse);
				AssertJUnit.assertEquals("StyleForum - Get Poll Details API is not working.",200,getRequest.response.getStatus());
				
				PollID = JsonPath.read(response, "$.data.id");
				request=styleForumServiceHelper.invokeStyleForumService_Delete(userName, password, APINAME.DELETEPOLL, PollID);
				AssertJUnit.assertEquals("Style Forum Tests - TearDown Activity [ERROR] - Delete Poll Service is not working!",200,request.response.getStatus());
				
			}
			
			@Test(groups = {"Smoke", "ProdSanity", "Sanity", "MiniRegression", "ExhaustiveRegression"},dataProvider = "CreatePollFlow")
			public void StyleForum_Poll_GetPollDetails_verifyData(String userName, String password, String Question, String Option1, String Option2, String Category)
			{
				RequestGenerator request = styleForumServiceHelper.invokeCreateTextPollService(userName, password, Question, Option1, Option2);
				String response = request.respvalidate.returnresponseasstring();
				System.out.println("Create Poll API Response : "+response);
				String PollID = JsonPath.read(response,"$.data.id");
				AssertJUnit.assertEquals("Create Poll API is not working!", 200,request.response.getStatus());		
				RequestGenerator getRequest = styleForumServiceHelper.invokePollService_GetPollDetails(userName, password, PollID);
				String PollResponse = getRequest.respvalidate.returnresponseasstring();
				System.out.println("\n Get Poll Details Response : \n\n"+PollResponse);
				AssertJUnit.assertEquals("StyleForum - Get Poll Details API is not working.",200,getRequest.response.getStatus());
				AssertJUnit.assertEquals("Stlye Forum - Get Poll Details API is not working. [Entity Type is not proper]", "POLL",JsonPath.read(PollResponse, "$.data.type"));
				AssertJUnit.assertEquals("Stlye Forum - Get Poll Details API is not working. [Poll ID is not proper]", PollID,JsonPath.read(PollResponse, "$.data.id"));
				AssertJUnit.assertEquals("Stlye Forum - Get Poll Details API is not working. [Poll Question is not proper]", Question,JsonPath.read(PollResponse, "$.data.description[0].value"));

				 PollID = JsonPath.read(response, "$.data.id");
				request=styleForumServiceHelper.invokeStyleForumService_Delete(userName, password, APINAME.DELETEPOLL, PollID);
				AssertJUnit.assertEquals("Style Forum Tests - TearDown Activity [ERROR] - Delete Poll Service is not working!",200,request.response.getStatus());
				
			}
			
			@Test(groups = {"Schema Validation"},dataProvider = "CreatePollFlow")
			public void StyleForum_Poll_GetPollDetails_verifySchema(String userName, String password, String Question, String Option1, String Option2, String Category)
			{
				RequestGenerator request = styleForumServiceHelper.invokeCreateTextPollService(userName, password, Question, Option1, Option2);
				String response = request.respvalidate.returnresponseasstring();
				System.out.println("Create Poll API Response : "+response);
				String PollID = JsonPath.read(response,"$.data.id");
				AssertJUnit.assertEquals("Create Poll API is not working!", 200,request.response.getStatus());
				   
				RequestGenerator getRequest = styleForumServiceHelper.invokePollService_GetPollDetails(userName, password, PollID);
				String PollResponse = getRequest.respvalidate.returnresponseasstring();
				System.out.println("\n Get Poll Details Response : \n\n"+PollResponse);
				AssertJUnit.assertEquals("StyleForum - Get Poll Details API is not working.",200,getRequest.response.getStatus());
				try {
		            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/styleforum_poll_getpolldetails_schema.txt");
		            List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(response, jsonschema);
		            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in StyleForum Create poll API response", 
		            		CollectionUtils.isEmpty(missingNodeList));
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
				
				PollID = JsonPath.read(response, "$.data.id");
				request=styleForumServiceHelper.invokeStyleForumService_Delete(userName, password, APINAME.DELETEPOLL, PollID);
				AssertJUnit.assertEquals("Style Forum Tests - TearDown Activity [ERROR] - Delete Poll Service is not working!",200,request.response.getStatus());
				
			}
			
			//Comment on a Poll
			@Test(groups = {"Smoke", "ProdSanity", "Sanity", "MiniRegression", "ExhaustiveRegression"},dataProvider = "StyleForum_Poll_CommentonPoll_SameUser")
			public void StyleForum_Poll_CommentOnPoll_ByAuthor_VerifySuccessAndData(String userName, String password, String Question, String Option1, String Option2,String Comment)
			{
				RequestGenerator request = styleForumServiceHelper.invokeCreateTextPollService(userName, password, Question, Option1, Option2);
				String response = request.respvalidate.returnresponseasstring();
				System.out.println("Create Poll API Response : "+response);
				String PollID = JsonPath.read(response,"$.data.id");
				AssertJUnit.assertEquals("Create Poll API is not working!", 200,request.response.getStatus());
				
				RequestGenerator CommentRequest = styleForumServiceHelper.invokePollService_CommentOnPoll(userName, password, PollID, Comment);
				String commentResponse = CommentRequest.respvalidate.returnresponseasstring();
				System.out.println("\n Comment on Poll Response : \n"+commentResponse);
				AssertJUnit.assertEquals("StyleForum - Comment on Poll API is not working.",200,CommentRequest.response.getStatus());
				AssertJUnit.assertEquals("StyleForum - Comment on Poll API is not working. [Poll ID is different or invalid]",PollID,JsonPath.read(commentResponse,"$.data.parentId").toString());
				AssertJUnit.assertEquals("StyleForum - Comment on Poll API is not working. [Comment is different or invalid]",Comment,JsonPath.read(commentResponse,"$.data.description[0].value"));
			
				PollID = JsonPath.read(response, "$.data.id").toString();
				request=styleForumServiceHelper.invokeStyleForumService_Delete(userName, password, APINAME.DELETEPOLL, PollID);
				AssertJUnit.assertEquals("Style Forum Tests - TearDown Activity [ERROR] - Delete Poll Service is not working!",200,request.response.getStatus());
				
			}
			
			@Test(groups = {"Smoke", "ProdSanity", "Sanity", "MiniRegression", "ExhaustiveRegression"},dataProvider = "StyleForum_Poll_CommentonPoll_DifferentUser")
			public void StyleForum_Poll_CommentOnPoll_DifferentUser_VerifySuccessAndData(String userName, String password, String Question, String Option1, String Option2,String NewUserName, String NewPassword, String Comment)
			{
				RequestGenerator request = styleForumServiceHelper.invokeCreateTextPollService(userName, password, Question, Option1, Option2);
				String response = request.respvalidate.returnresponseasstring();
				System.out.println("Create Poll API Response : "+response);
				String PollID = JsonPath.read(response,"$.data.id");
				AssertJUnit.assertEquals("Create Poll API is not working!", 200,request.response.getStatus());
				
				RequestGenerator CommentRequest = styleForumServiceHelper.invokePollService_CommentOnPoll(NewUserName, NewPassword, PollID, Comment);
				String commentResponse = CommentRequest.respvalidate.returnresponseasstring();
				System.out.println("\n Comment on Poll Response : \n"+commentResponse);
				AssertJUnit.assertEquals("StyleForum - Comment on Poll API is not working.",200,CommentRequest.response.getStatus());
				AssertJUnit.assertEquals("StyleForum - Comment on Poll API is not working. [Poll ID is different or invalid]",PollID,JsonPath.read(commentResponse,"$.data.parentId").toString());
				AssertJUnit.assertEquals("StyleForum - Comment on Poll API is not working. [Comment is different or invalid]",Comment,JsonPath.read(commentResponse,"$.data.description[0].value"));
				
				PollID = JsonPath.read(response, "$.data.id");
				request=styleForumServiceHelper.invokeStyleForumService_Delete(userName, password, APINAME.DELETEPOLL, PollID);
				AssertJUnit.assertEquals("Style Forum Tests - TearDown Activity [ERROR] - Delete Poll Service is not working!",200,request.response.getStatus());
				
			}
			
			
			//Validate Comments Count
			@Test(groups = {"Smoke", "ProdSanity", "Sanity", "MiniRegression", "ExhaustiveRegression"},dataProvider = "StyleForum_Poll_CommentonPoll_DifferentUser")
			public void StyleForum_Poll_CommentOnPoll_DifferentUser_VerifyCommentCount(String userName, String password, String Question, String Option1, String Option2,String NewUserName, String NewPassword, String Comment)
			{
				RequestGenerator request = styleForumServiceHelper.invokeCreateTextPollService(userName, password, Question, Option1, Option2);
				String response = request.respvalidate.returnresponseasstring();
				System.out.println("Create Poll API Response : "+response);
				String PollID = JsonPath.read(response,"$.data.id");
				AssertJUnit.assertEquals("Create Poll API is not working!", 200,request.response.getStatus());
				int CommentCount = 3;
				
				for (int i = 0; i< CommentCount; i++)
				{
					RequestGenerator CommentRequest = styleForumServiceHelper.invokePollService_CommentOnPoll(NewUserName, NewPassword, PollID, Comment);
					String commentResponse = CommentRequest.respvalidate.returnresponseasstring();
					System.out.println("\n Comment on Poll Response : \n"+commentResponse);
					AssertJUnit.assertEquals("StyleForum - Comment on Poll API is not working.",200,CommentRequest.response.getStatus());
					AssertJUnit.assertEquals("StyleForum - Comment on Poll API is not working. [Poll ID is different or invalid]",PollID,JsonPath.read(commentResponse,"$.data.parentId").toString());
					AssertJUnit.assertEquals("StyleForum - Comment on Poll API is not working. [Comment is different or invalid]",Comment,JsonPath.read(commentResponse,"$.data.description[0].value"));
				
				}
				
				RequestGenerator CommentDetailRequest = styleForumServiceHelper.invokePollService_GetPollDetails(NewUserName, NewPassword, PollID);
				String CommentDetailsResponse = CommentDetailRequest.respvalidate.returnresponseasstring();
				System.out.println("\n Get Poll details Response : \n\n"+CommentDetailsResponse);
				AssertJUnit.assertEquals("StyleForum - Get Poll details API is not working.",200,CommentDetailRequest.response.getStatus());
				AssertJUnit.assertEquals("StyleForum - Get Poll details API is not working. [Total Number of Counts is different or invalid]",CommentCount,Integer.parseInt(JsonPath.read(CommentDetailsResponse,"$.data.commentCount")));
				
				PollID = JsonPath.read(response, "$.data.id").toString();
				request=styleForumServiceHelper.invokeStyleForumService_Delete(userName, password, APINAME.DELETEPOLL, PollID);
				AssertJUnit.assertEquals("Style Forum Tests - TearDown Activity [ERROR] - Delete Poll Service is not working!",200,request.response.getStatus());
				
			}
			
			//Vote For a Poll
			@Test(groups = {"Smoke", "ProdSanity", "Sanity", "MiniRegression", "ExhaustiveRegression"},dataProvider = "StyleForum_Poll_VoteForPoll_SameUser_VerifyVoteisAllowedOnce")
			public void StyleForum_Poll_VoteForPoll_SameUser_VerifyVoteisAllowedOnce(String userName, String password, String Question, String Option1, String Option2)
			{
				RequestGenerator request = styleForumServiceHelper.invokeCreateTextPollService(userName, password, Question, Option1, Option2);
				String response = request.respvalidate.returnresponseasstring();
				System.out.println("Create Poll API Response : "+response);
				String PollID = JsonPath.read(response,"$.data.id");
				AssertJUnit.assertEquals("Create Poll API is not working!", 200,request.response.getStatus());
				String OptionID1 = JsonPath.read(response,"$.data.options[0].id").toString();
				String OptionID2 = JsonPath.read(response,"$.data.options[1].id").toString();
				
				RequestGenerator voteRequest1 = styleForumServiceHelper.invokePollService_VoteForPoll(userName, password, PollID, OptionID1);
				String voteResponse1 = voteRequest1.respvalidate.returnresponseasstring();
				System.out.println("\n Vote for a Poll Response : \n"+voteResponse1);
				AssertJUnit.assertEquals("StyleForum - Vote for a Poll API is not working.",200,voteRequest1.response.getStatus());
				AssertJUnit.assertEquals("StyleForum - Vote for a Poll API is not working. [Vote Count is not updated]",1,Integer.parseInt(JsonPath.read(voteResponse1, "$.data.options[0].votes")));
				AssertJUnit.assertEquals("StyleForum - Vote for a Poll API is not working. [Total Vote Count is not updated]",1,Integer.parseInt(JsonPath.read(voteResponse1, "$.data.totalVotes")));
				
				RequestGenerator voteRequest2 = styleForumServiceHelper.invokePollService_VoteForPoll(userName, password, PollID, OptionID2);
				String voteResponse2 = voteRequest2.respvalidate.returnresponseasstring();
				System.out.println("\n Vote for a Poll Response : \n"+voteResponse2);
				AssertJUnit.assertEquals("StyleForum - Vote for a Poll API is not working.",500,voteRequest2.response.getStatus());

				PollID = JsonPath.read(response, "$.data.id").toString();
				request=styleForumServiceHelper.invokeStyleForumService_Delete(userName, password, APINAME.DELETEPOLL, PollID);
				AssertJUnit.assertEquals("Style Forum Tests - TearDown Activity [ERROR] - Delete Poll Service is not working!",200,request.response.getStatus());
				
			}
			
			@Test(groups = {"Smoke", "ProdSanity", "Sanity", "MiniRegression", "ExhaustiveRegression"},dataProvider = "StyleForum_Poll_VoteForPoll_DifferentUser_VerifyVoteisAllowed")
			public void StyleForum_Poll_VoteForPoll_DifferentUser_VerifyVoteisAllowed(String userName, String password, String Question, String Option1, String Option2, String newUserName, String newPassword)
			{
				RequestGenerator request = styleForumServiceHelper.invokeCreateTextPollService(userName, password, Question, Option1, Option2);
				String response = request.respvalidate.returnresponseasstring();
				System.out.println("Create Poll API Response : "+response);
				String PollID = JsonPath.read(response,"$.data.id");
				AssertJUnit.assertEquals("Create Poll API is not working!", 200,request.response.getStatus());
				String OptionID1 = JsonPath.read(response,"$.data.options[0].id").toString();
				String OptionID2 = JsonPath.read(response,"$.data.options[1].id").toString();
				
				RequestGenerator voteRequest1 = styleForumServiceHelper.invokePollService_VoteForPoll(userName, password, PollID, OptionID1);
				String voteResponse1 = voteRequest1.respvalidate.returnresponseasstring();
				System.out.println("\n Vote for a Poll Response : \n"+voteResponse1);
				AssertJUnit.assertEquals("StyleForum - Vote for a Poll API is not working.",200,voteRequest1.response.getStatus());
				AssertJUnit.assertEquals("StyleForum - Vote for a Poll API is not working. [Vote Count is not updated]",1,Integer.parseInt(JsonPath.read(voteResponse1, "$.data.options[0].votes")));
				AssertJUnit.assertEquals("StyleForum - Vote for a Poll API is not working. [Total Vote Count is not updated]",1,Integer.parseInt(JsonPath.read(voteResponse1, "$.data.totalVotes")));
				
				RequestGenerator voteRequest2 = styleForumServiceHelper.invokePollService_VoteForPoll(newUserName, newPassword, PollID, OptionID2);
				String voteResponse2 = voteRequest2.respvalidate.returnresponseasstring();
				System.out.println("\n Vote for a Poll Response : \n"+voteResponse2);
				AssertJUnit.assertEquals("StyleForum - Vote for a Poll API is not working.",200,voteRequest2.response.getStatus());
				AssertJUnit.assertEquals("StyleForum - Vote for a Poll API is not working. [Vote Count is not updated]",1,Integer.parseInt(JsonPath.read(voteResponse2, "$.data.options[1].votes")));
				AssertJUnit.assertEquals("StyleForum - Vote for a Poll API is not working. [Total Vote Count is not updated]",2,Integer.parseInt(JsonPath.read(voteResponse2, "$.data.totalVotes")));
				
				PollID = JsonPath.read(response, "$.data.id").toString();
				request=styleForumServiceHelper.invokeStyleForumService_Delete(userName, password, APINAME.DELETEPOLL, PollID);
				AssertJUnit.assertEquals("Style Forum Tests - TearDown Activity [ERROR] - Delete Poll Service is not working!",200,request.response.getStatus());
				
			}
			
			@Test(groups = {"Schema Validation"},dataProvider = "StyleForum_Poll_VoteForPoll_SameUser_VerifyVoteisAllowedOnce")
			public void StyleForum_Poll_VoteForPoll_Schema(String userName, String password, String Question, String Option1, String Option2)
			{
				//Create Poll
				RequestGenerator request = styleForumServiceHelper.invokeCreateTextPollService(userName, password, Question, Option1, Option2);
				String response = request.respvalidate.returnresponseasstring();
				System.out.println("Create Poll API Response : "+response);
				String PollID = JsonPath.read(response,"$.data.id");
				AssertJUnit.assertEquals("Create Poll API is not working!", 200,request.response.getStatus());
					
				
				String OptionID1 = JsonPath.read(response,"$.data.options[0].id").toString();
			
				RequestGenerator voteRequest1 = styleForumServiceHelper.invokePollService_VoteForPoll(userName, password, PollID, OptionID1);
				String voteResponse1 = voteRequest1.respvalidate.returnresponseasstring();
				System.out.println("\n Vote for a Poll Response : \n"+voteResponse1);
				AssertJUnit.assertEquals("StyleForum - Vote for a Poll API is not working.",200,voteRequest1.response.getStatus());
				AssertJUnit.assertEquals("StyleForum - Vote for a Poll API is not working. [Vote Count is not updated]",1,Integer.parseInt(JsonPath.read(voteResponse1, "$.data.options[0].votes")));
				AssertJUnit.assertEquals("StyleForum - Vote for a Poll API is not working. [Total Vote Count is not updated]",1,Integer.parseInt(JsonPath.read(voteResponse1, "$.data.totalVotes")));
			
				try 
				{
					String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/styleforum_poll_voteforpoll_schema.txt.txt");
					List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(voteResponse1, jsonschema);
					AssertJUnit.assertTrue(missingNodeList+" nodes are missing in Style Forum - Get My Profile API response",CollectionUtils.isEmpty(missingNodeList));
		    	}catch (Exception e)
		    		{
		    			e.printStackTrace();
		    		}
			
				PollID = JsonPath.read(response, "$.data.id").toString();
				request=styleForumServiceHelper.invokeStyleForumService_Delete(userName, password, APINAME.DELETEPOLL, PollID);
				AssertJUnit.assertEquals("Style Forum Tests - TearDown Activity [ERROR] - Delete Poll Service is not working!",200,request.response.getStatus());
				
			}


				
		//Question Related Flows
		
		//Create Question Flow
		
			@Test(groups = {"Smoke", "ProdSanity", "Sanity", "MiniRegression", "ExhaustiveRegression"},dataProvider = "CreateQuestionFlow")
			public void Create_Question_Flow(String userName, String password, String Question, String Category)
			{
				//Post Question
				RequestGenerator request = styleForumServiceHelper.invokeStyleForumService_CreateAQuestion(userName, password,Question);
				String response = request.respvalidate.returnresponseasstring();
				System.out.println(response);
				String QuestionID = JsonPath.read(response, "$.data.id").toString();
				AssertJUnit.assertEquals("Style Forum - Create new question API is not working.", 200,request.response.getStatus());
				
				//Update Category
				request = styleForumServiceHelper.invokeStyleForumService_updateAQuestion(userName, password, QuestionID, Question, Category);
				response = request.respvalidate.returnresponseasstring();
				System.out.println(response);
				AssertJUnit.assertEquals("Style Forum - Update Category API is not working.", 200,request.response.getStatus());
				AssertJUnit.assertEquals("Style Forum - Update Category API is not working.[Question ID is not proper]",QuestionID,JsonPath.read(response, "$.data.id").toString());
				
				QuestionID = JsonPath.read(response, "$.data.id").toString();
				request=styleForumServiceHelper.invokeStyleForumService_Delete(userName, password, APINAME.DELETEQUESTION, QuestionID);
				AssertJUnit.assertEquals("Style Forum Tests - TearDown Activity [ERROR] - Delete Question Service is not working!",200,request.response.getStatus());
				
			}
			
			@Test(groups = {"Smoke", "ProdSanity", "Sanity", "MiniRegression", "ExhaustiveRegression"},dataProvider = "UpdateQuestionFlow")
			public void Update_Question_Flow(String userName, String password, String Question, String Category, String Update)
			{
				//Post Question
				RequestGenerator request = styleForumServiceHelper.invokeStyleForumService_CreateAQuestion(userName, password,Question);
				String response = request.respvalidate.returnresponseasstring();
				System.out.println(response);
				String QuestionID = JsonPath.read(response, "$.data.id").toString();
				AssertJUnit.assertEquals("Style Forum - Create new question API is not working.", 200,request.response.getStatus());
				
				//Update Category
				request = styleForumServiceHelper.invokeStyleForumService_updateAQuestion(userName, password, QuestionID, Update, Category);
				response = request.respvalidate.returnresponseasstring();
				System.out.println(response);
				AssertJUnit.assertEquals("Style Forum - Update Question API is not working.", 200,request.response.getStatus());
				AssertJUnit.assertEquals("Style Forum - Update Question API is not working.[Question ID is not proper]",QuestionID,JsonPath.read(response, "$.data.id").toString());
				AssertJUnit.assertEquals("Style Forum - Update Question API is not working.[Question is not updated]",Update,JsonPath.read(response, "$.data.description[0].value"));
				
				
				QuestionID = JsonPath.read(response, "$.data.id").toString();
				request=styleForumServiceHelper.invokeStyleForumService_Delete(userName, password, APINAME.DELETEQUESTION, QuestionID);
				AssertJUnit.assertEquals("Style Forum Tests - TearDown Activity [ERROR] - Delete Question Service is not working!",200,request.response.getStatus());
				
			}
			
			//Answer a Question Flow
			@Test(groups = {"Smoke", "ProdSanity", "Sanity", "MiniRegression", "ExhaustiveRegression"},dataProvider = "AnswerAQuestionFlow")
			public void Answer_Question_Flow(String userName, String password, String Question, String Category, String Answer)
			{
				//Post Question
				RequestGenerator request = styleForumServiceHelper.invokeStyleForumService_CreateAQuestion(userName, password,Question);
				String response = request.respvalidate.returnresponseasstring();
				System.out.println(response);
				String QuestionID = JsonPath.read(response, "$.data.id").toString();
				AssertJUnit.assertEquals("Style Forum - Create new question API is not working.", 200,request.response.getStatus());
				
				//Update Category
				request = styleForumServiceHelper.invokeStyleForumService_updateAQuestion(userName, password, QuestionID, Question, Category);
				response = request.respvalidate.returnresponseasstring();
				System.out.println(response);
				QuestionID = JsonPath.read(response, "$.data.id").toString();
				AssertJUnit.assertEquals("Style Forum - Update Question API is not working.", 200,request.response.getStatus());
			
				//Answer a Question
				request = styleForumServiceHelper.invokeStyleForumService_answerAQuestion(userName, password, QuestionID, Answer);
				response = request.respvalidate.returnresponseasstring();
				System.out.println(response);
				AssertJUnit.assertEquals("Answer a Question API is not working!",200,request.response.getStatus());
				AssertJUnit.assertEquals("Answer a Question API is not working!-[Answer is not added]",Answer,JsonPath.read(response, "$.data.description[0].value"));
				
				//Tear Down Activity
				request=styleForumServiceHelper.invokeStyleForumService_Delete(userName, password, APINAME.DELETEQUESTION, QuestionID);
				AssertJUnit.assertEquals("Style Forum Tests - TearDown Activity [ERROR] - Delete Question Service is not working!",200,request.response.getStatus());
			}
		
			
			//Update an Answer Flow
			@Test(groups = {"Smoke", "ProdSanity", "Sanity", "MiniRegression", "ExhaustiveRegression"},dataProvider = "UpdateAnswerFlow")
			public void Update_Answer_Flow(String userName, String password, String Question, String Category, String Answer, String AnswerUpdate)
			{
				//Post Question
				RequestGenerator request = styleForumServiceHelper.invokeStyleForumService_CreateAQuestion(userName, password,Question);
				String response = request.respvalidate.returnresponseasstring();
				System.out.println(response);
				String QuestionID = JsonPath.read(response, "$.data.id").toString();
				AssertJUnit.assertEquals("Style Forum - Create new question API is not working.", 200,request.response.getStatus());
				
				//Update Category
				request = styleForumServiceHelper.invokeStyleForumService_updateAQuestion(userName, password, QuestionID, Question, Category);
				response = request.respvalidate.returnresponseasstring();
				System.out.println(response);
				QuestionID = JsonPath.read(response, "$.data.id").toString();
				AssertJUnit.assertEquals("Style Forum - Update Question API is not working.", 200,request.response.getStatus());
			
				//Answer a Question
				request = styleForumServiceHelper.invokeStyleForumService_answerAQuestion(userName, password, QuestionID, Answer);
				response = request.respvalidate.returnresponseasstring();
				System.out.println(response);
				String AnswerID= JsonPath.read(response, "$.data.id").toString();
				AssertJUnit.assertEquals("Answer a Question API is not working!",200,request.response.getStatus());
				AssertJUnit.assertEquals("Answer a Question API is not working!-[Answer is not added]",Answer,JsonPath.read(response, "$.data.description[0].value"));
				
				//Update the Answer
				request = styleForumServiceHelper.invokeStyleForumService_updateAnAnswer(userName, password, QuestionID, AnswerID, AnswerUpdate);
				response = request.respvalidate.returnresponseasstring();
				System.out.println(response);
				AssertJUnit.assertEquals("Update an Answer API is not working", 200, request.response.getStatus());
				AssertJUnit.assertEquals("Update an Answer API is not working",AnswerUpdate,JsonPath.read(response, "$.data.description[0].value"));
		
				//Tear Down Activity
				request=styleForumServiceHelper.invokeStyleForumService_Delete(userName, password, APINAME.DELETEQUESTION, QuestionID);
				AssertJUnit.assertEquals("Style Forum Tests - TearDown Activity [ERROR] - Delete Question Service is not working!",200,request.response.getStatus());
				
			}
		
			
			//Delete an Answer Flow
			@Test(groups = {"Smoke", "ProdSanity", "Sanity", "MiniRegression", "ExhaustiveRegression"},dataProvider = "AnswerAQuestionFlow")
			public void Delete_Answer_Flow(String userName, String password, String Question, String Category, String Answer)
			{
				//Post Question
				RequestGenerator request = styleForumServiceHelper.invokeStyleForumService_CreateAQuestion(userName, password,Question);
				String response = request.respvalidate.returnresponseasstring();
				System.out.println(response);
				String QuestionID = JsonPath.read(response, "$.data.id").toString();
				AssertJUnit.assertEquals("Style Forum - Create new question API is not working.", 200,request.response.getStatus());
				
				//Update Category
				request = styleForumServiceHelper.invokeStyleForumService_updateAQuestion(userName, password, QuestionID, Question, Category);
				response = request.respvalidate.returnresponseasstring();
				System.out.println(response);
				QuestionID = JsonPath.read(response, "$.data.id").toString();
				AssertJUnit.assertEquals("Style Forum - Update Question API is not working.", 200,request.response.getStatus());
			
				//Answer a Question
				request = styleForumServiceHelper.invokeStyleForumService_answerAQuestion(userName, password, QuestionID, Answer);
				response = request.respvalidate.returnresponseasstring();
				System.out.println(response);
				String AnswerID= JsonPath.read(response, "$.data.id").toString();
				AssertJUnit.assertEquals("Answer a Question API is not working!",200,request.response.getStatus());
				AssertJUnit.assertEquals("Answer a Question API is not working!-[Answer is not added]",Answer,JsonPath.read(response, "$.data.description[0].value"));
				
				//Delete the Answer
				request = styleForumServiceHelper.invokeStyleForumService_Delete(userName, password, APINAME.DELETEANSWER, AnswerID);
				response = request.respvalidate.returnresponseasstring();
				System.out.println(response);
				AssertJUnit.assertEquals("Delete an Answer API is not working", 200, request.response.getStatus());
		
				//Tear Down Activity
				request=styleForumServiceHelper.invokeStyleForumService_Delete(userName, password, APINAME.DELETEQUESTION, QuestionID);
				AssertJUnit.assertEquals("Style Forum Tests - TearDown Activity [ERROR] - Delete Question Service is not working!",200,request.response.getStatus());
				
			}
		
			@Test(groups = {"Smoke", "ProdSanity", "Sanity", "MiniRegression", "ExhaustiveRegression"},dataProvider = "StyleForum_Questions_CreateQuestion")
			public void StyleForum_Questions_CreateQuestion_VerifySuccess(String userName, String password, String Question)
			{
				RequestGenerator request = styleForumServiceHelper.invokeStyleForumService_CreateAQuestion(userName, password,Question);
				String response = request.respvalidate.returnresponseasstring();
				System.out.println(response);
				AssertJUnit.assertEquals("Style Forum - Create new question API is not working.", 200,request.response.getStatus());
				
				String QuestionID = JsonPath.read(response, "$.data.id").toString();
				request=styleForumServiceHelper.invokeStyleForumService_Delete(userName, password, APINAME.DELETEQUESTION, QuestionID);
				AssertJUnit.assertEquals("Style Forum Tests - TearDown Activity [ERROR] - Delete Question Service is not working!",200,request.response.getStatus());
				
			}
			
			@Test(groups = {"Smoke", "ProdSanity", "Sanity", "MiniRegression", "ExhaustiveRegression"},dataProvider = "StyleForum_Questions_CreateQuestion")
			public void StyleForum_Questions_CreateQuestion_VerifyData(String userName, String password, String Question)
			{
				RequestGenerator request = styleForumServiceHelper.invokeStyleForumService_CreateAQuestion(userName, password,Question);
				String response = request.respvalidate.returnresponseasstring();
				System.out.println(response);
				AssertJUnit.assertEquals("Style Forum - Create new question API is not working.", 200,request.response.getStatus());
				AssertJUnit.assertEquals("Style Forum - Create new question API is not working. [Type is not proper]","QUESTION",JsonPath.read(response, "$.data.type"));
				AssertJUnit.assertEquals("Style Forum - Create new question API is not working. [Question is not proper or invalid!]",Question,JsonPath.read(response,"$.data.description[0].value"));
				
				String QuestionID = JsonPath.read(response, "$.data.id").toString();
				request=styleForumServiceHelper.invokeStyleForumService_Delete(userName, password, APINAME.DELETEQUESTION, QuestionID);
				AssertJUnit.assertEquals("Style Forum Tests - TearDown Activity [ERROR] - Delete Question Service is not working!",200,request.response.getStatus());
				
			}
			
			@Test(groups = {"Schema Validation"},dataProvider = "StyleForum_Questions_CreateQuestion")
			public void StyleForum_Questions_CreateQuestion_VerifySchema(String userName, String password, String Question)
			{
				RequestGenerator request = styleForumServiceHelper.invokeStyleForumService_CreateAQuestion(userName, password,Question);
				String response = request.respvalidate.returnresponseasstring();
				System.out.println(response);
				AssertJUnit.assertEquals("Style Forum - Create new question API is not working.", 200,request.response.getStatus());
				try 
				{
					String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/styleforum-question-createnewquestion-schema.txt");
					List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(response, jsonschema);
					AssertJUnit.assertTrue(missingNodeList+" nodes are missing in Style Forum - Get My Profile API response",CollectionUtils.isEmpty(missingNodeList));
		    	}catch (Exception e)
		    		{
		    			e.printStackTrace();
		    	
		    		}
				String QuestionID = JsonPath.read(response, "$.data.id").toString();
				request=styleForumServiceHelper.invokeStyleForumService_Delete(userName, password, APINAME.DELETEQUESTION, QuestionID);
				AssertJUnit.assertEquals("Style Forum Tests - TearDown Activity [ERROR] - Delete Question Service is not working!",200,request.response.getStatus());
				
			}
			
			
			//Update Question API test cases
			@Test(groups = {"Smoke", "ProdSanity", "Sanity", "MiniRegression", "ExhaustiveRegression"},dataProvider = "StyleForum_Questions_UpdateQuestion")
			public void StyleForum_Questions_UpdateQuestion_VerifySuccess(String userName, String password, String Question, String UpdatedQuestion)
			{
				RequestGenerator request = styleForumServiceHelper.invokeStyleForumService_CreateAQuestion(userName, password,Question);
				String response = request.respvalidate.returnresponseasstring();
				System.out.println(response);
				AssertJUnit.assertEquals("Style Forum - Create new question API is not working.", 200,request.response.getStatus());
				String QuestionID = JsonPath.read(response, "$.data.id").toString();
				
				RequestGenerator updateRequest = styleForumServiceHelper.invokeStyleForumService_updateAQuestion(userName, password, QuestionID, UpdatedQuestion, QuestionID);
				String updateResponse = updateRequest.respvalidate.returnresponseasstring();
				System.out.println(updateResponse);
				AssertJUnit.assertEquals("Style Forum - Update a question API is not working.", 200, updateRequest.response.getStatus());
				
				request=styleForumServiceHelper.invokeStyleForumService_Delete(userName, password, APINAME.DELETEQUESTION, QuestionID);
				AssertJUnit.assertEquals("Style Forum Tests - TearDown Activity [ERROR] - Delete Question Service is not working!",200,request.response.getStatus());
				
			}
			
			@Test(groups = {"Smoke", "ProdSanity", "Sanity", "MiniRegression", "ExhaustiveRegression"},dataProvider = "StyleForum_Questions_UpdateQuestion")
			public void StyleForum_Questions_UpdateQuestion_VerifyData(String userName, String password, String Question, String UpdatedQuestion)
			{
				RequestGenerator request = styleForumServiceHelper.invokeStyleForumService_CreateAQuestion(userName, password,Question);
				String response = request.respvalidate.returnresponseasstring();
				System.out.println(response);
				AssertJUnit.assertEquals("Style Forum - Create new question API is not working.", 200,request.response.getStatus());
				String QuestionID = JsonPath.read(response, "$.data.id").toString();
				
				RequestGenerator updateRequest = styleForumServiceHelper.invokeStyleForumService_updateAQuestion(userName, password, QuestionID, UpdatedQuestion,"1");
				String updateResponse = updateRequest.respvalidate.returnresponseasstring();
				System.out.println(updateResponse);
				AssertJUnit.assertEquals("Style Forum - Update a question API is not working.", 200, updateRequest.response.getStatus());
				AssertJUnit.assertEquals("Style Forum - Update a question API is not working. [Question id is not proper]", QuestionID, JsonPath.read(updateResponse, "$.data.id").toString());
				AssertJUnit.assertEquals("Style Forum - Update a question API is not working. [Question is not properly updated]",UpdatedQuestion,JsonPath.read(updateResponse,"$.data.description[0].value" ));
				
				request=styleForumServiceHelper.invokeStyleForumService_Delete(userName, password, APINAME.DELETEQUESTION, QuestionID);
				AssertJUnit.assertEquals("Style Forum Tests - TearDown Activity [ERROR] - Delete Question Service is not working!",200,request.response.getStatus());
				
			}
			
			
			@Test(groups = {"Schema Validation"},dataProvider = "StyleForum_Questions_UpdateQuestion")
			public void StyleForum_Questions_UpdateQuestion_VerifySchema(String userName, String password, String Question, String UpdatedQuestion)
			{
				RequestGenerator request = styleForumServiceHelper.invokeStyleForumService_CreateAQuestion(userName, password,Question);
				String response = request.respvalidate.returnresponseasstring();
				System.out.println(response);
				AssertJUnit.assertEquals("Style Forum - Create new question API is not working.", 200,request.response.getStatus());
				String QuestionID = JsonPath.read(response, "$.data.id").toString();
				
				RequestGenerator updateRequest = styleForumServiceHelper.invokeStyleForumService_updateAQuestion(userName, password, QuestionID, UpdatedQuestion,"1");
				String updateResponse = updateRequest.respvalidate.returnresponseasstring();
				System.out.println(updateResponse);
				AssertJUnit.assertEquals("Style Forum - Update a question API is not working.", 200, updateRequest.response.getStatus());
				try 
				{
					String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/styleforum-questions-updatequestion-schema.txt");
					List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(updateResponse, jsonschema);
					AssertJUnit.assertTrue(missingNodeList+" nodes are missing in Style Forum - Get My Profile API response",CollectionUtils.isEmpty(missingNodeList));
		    	}catch (Exception e)
		    		{
		    			e.printStackTrace();
		    		}
				request=styleForumServiceHelper.invokeStyleForumService_Delete(userName, password, APINAME.DELETEQUESTION, QuestionID);
				AssertJUnit.assertEquals("Style Forum Tests - TearDown Activity [ERROR] - Delete Question Service is not working!",200,request.response.getStatus());
				
			}
			
			
			
			//Answer a Question
			@Test(groups = {"Smoke", "ProdSanity", "Sanity", "MiniRegression", "ExhaustiveRegression"},dataProvider = "StyleForum_Questions_AnswerAQuestion")
			public void StyleForum_Questions_AnswerQuestion_VerifySuccess(String userName, String password, String Question, String Answer)
			{
				RequestGenerator request = styleForumServiceHelper.invokeStyleForumService_CreateAQuestion(userName, password,Question);
				String response = request.respvalidate.returnresponseasstring();
				System.out.println(response);
				AssertJUnit.assertEquals("Style Forum - Create new question API is not working.", 200,request.response.getStatus());
				String QuestionID = JsonPath.read(response, "$.data.id").toString();
				
				request=styleForumServiceHelper.invokeStyleForumService_answerAQuestion(userName, password, QuestionID, Answer);
				response = request.respvalidate.returnresponseasstring();
				System.out.println(response);
				AssertJUnit.assertEquals("Style Forum - Answer a question API is not working.", 200,request.response.getStatus());
			
				request=styleForumServiceHelper.invokeStyleForumService_Delete(userName, password, APINAME.DELETEQUESTION, QuestionID);
				AssertJUnit.assertEquals("Style Forum Tests - TearDown Activity [ERROR] - Delete Question Service is not working!",200,request.response.getStatus());
				
			}
			
			@Test(groups = {"Smoke", "ProdSanity", "Sanity", "MiniRegression", "ExhaustiveRegression"},dataProvider = "StyleForum_Questions_AnswerAQuestion")
			public void StyleForum_Questions_AnswerQuestion_VerifyData(String userName, String password, String Question, String Answer)
			{
				RequestGenerator request = styleForumServiceHelper.invokeStyleForumService_CreateAQuestion(userName, password,Question);
				String response = request.respvalidate.returnresponseasstring();
				System.out.println(response);
				AssertJUnit.assertEquals("Style Forum - Create new question API is not working.", 200,request.response.getStatus());
				String QuestionID = JsonPath.read(response, "$.data.id").toString();
				
				request=styleForumServiceHelper.invokeStyleForumService_answerAQuestion(userName, password, QuestionID, Answer);
				response = request.respvalidate.returnresponseasstring();
				System.out.println(response);
				AssertJUnit.assertEquals("Style Forum - Answer a question API is not working.", 200,request.response.getStatus());
				AssertJUnit.assertEquals("Style Forum - Answer a question API is not working.[Type is not proper]","ANSWER",JsonPath.read(response, "$.data.type"));
				AssertJUnit.assertEquals("Style Forum - Answer a question API is not working.[Question ID is not proper]",QuestionID,JsonPath.read(response, "$.data.questionId").toString());
				AssertJUnit.assertEquals("Style Forum - Answer a question API is not working.[Answer is not proper]",Answer,JsonPath.read(response, "$.data.description[0].value"));

				request=styleForumServiceHelper.invokeStyleForumService_Delete(userName, password, APINAME.DELETEQUESTION, QuestionID);
				AssertJUnit.assertEquals("Style Forum Tests - TearDown Activity [ERROR] - Delete Question Service is not working!",200,request.response.getStatus());
				
			}
			
			@Test(groups = {"Smoke", "ProdSanity", "Sanity", "MiniRegression", "ExhaustiveRegression"},dataProvider = "StyleForum_Questions_AnswerAQuestion")
			public void StyleForum_Questions_AnswerQuestion_VerifySchema(String userName, String password, String Question, String Answer)
			{
				RequestGenerator request = styleForumServiceHelper.invokeStyleForumService_CreateAQuestion(userName, password,Question);
				String response = request.respvalidate.returnresponseasstring();
				System.out.println(response);
				AssertJUnit.assertEquals("Style Forum - Create new question API is not working.", 200,request.response.getStatus());
				String QuestionID = JsonPath.read(response, "$.data.id").toString();
				
				request=styleForumServiceHelper.invokeStyleForumService_answerAQuestion(userName, password, QuestionID, Answer);
				response = request.respvalidate.returnresponseasstring();
				System.out.println("Answer Question Response \n ");
				System.out.println(response);
				AssertJUnit.assertEquals("Style Forum - Answer a question API is not working.", 200,request.response.getStatus());
				
				try 
				{
					String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/styleForum-AnswerAQuestion-Schema.txt");
					List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(response, jsonschema);
					AssertJUnit.assertTrue(missingNodeList+" nodes are missing in Style Forum - Get My Profile API response",CollectionUtils.isEmpty(missingNodeList));
		    	}catch (Exception e)
		    		{
		    			e.printStackTrace();
		    		}
				
				request=styleForumServiceHelper.invokeStyleForumService_Delete(userName, password, APINAME.DELETEQUESTION, QuestionID);
				AssertJUnit.assertEquals("Style Forum Tests - TearDown Activity [ERROR] - Delete Poll Service is not working!",200,request.response.getStatus());
				
			}
			
			@Test(groups = {"Smoke", "ProdSanity", "Sanity", "MiniRegression", "ExhaustiveRegression"},dataProvider = "StyleForum_Questions_UpdateAnAnswer")
			public void StyleForum_Questions_updateAnswer_VerifySuccess(String userName, String password, String Question, String Answer, String UpdateAnswer)
			{
				RequestGenerator request = styleForumServiceHelper.invokeStyleForumService_CreateAQuestion(userName, password,Question);
				String response = request.respvalidate.returnresponseasstring();
				System.out.println(response);
				AssertJUnit.assertEquals("Style Forum - Create new question API is not working.", 200,request.response.getStatus());
				String QuestionID = JsonPath.read(response, "$.data.id").toString();
				
				request=styleForumServiceHelper.invokeStyleForumService_answerAQuestion(userName, password, QuestionID, Answer);
				response = request.respvalidate.returnresponseasstring();
				System.out.println(response);
				AssertJUnit.assertEquals("Style Forum - Answer a question API is not working.", 200,request.response.getStatus());
				String AnswerID = JsonPath.read(response, "$.data.id").toString();
				
				request=styleForumServiceHelper.invokeStyleForumService_updateAnAnswer(userName, password, QuestionID, AnswerID, UpdateAnswer);
				response = request.respvalidate.returnresponseasstring();
				System.out.println(response);
				AssertJUnit.assertEquals("Style Forum - Update an Answer for a question API is not working.", 200,request.response.getStatus());
				
				request=styleForumServiceHelper.invokeStyleForumService_Delete(userName, password, APINAME.DELETEQUESTION, QuestionID);
				AssertJUnit.assertEquals("Style Forum Tests - TearDown Activity [ERROR] - Delete Poll Service is not working!",200,request.response.getStatus());
				
				
			
			}
			
			@Test(groups = {"Smoke", "ProdSanity", "Sanity", "MiniRegression", "ExhaustiveRegression"},dataProvider = "StyleForum_Questions_UpdateAnAnswer")
			public void StyleForum_Questions_updateAnswer_VerifyData(String userName, String password, String Question, String Answer, String UpdateAnswer)
			{
				RequestGenerator request = styleForumServiceHelper.invokeStyleForumService_CreateAQuestion(userName, password,Question);
				String response = request.respvalidate.returnresponseasstring();
				System.out.println(response);
				AssertJUnit.assertEquals("Style Forum - Create new question API is not working.", 200,request.response.getStatus());
				String QuestionID = JsonPath.read(response, "$.data.id").toString();
				
				request=styleForumServiceHelper.invokeStyleForumService_answerAQuestion(userName, password, QuestionID, Answer);
				response = request.respvalidate.returnresponseasstring();
				System.out.println(response);
				AssertJUnit.assertEquals("Style Forum - Answer a question API is not working.", 200,request.response.getStatus());
				String AnswerID = JsonPath.read(response, "$.data.id").toString();
				
				request=styleForumServiceHelper.invokeStyleForumService_updateAnAnswer(userName, password, QuestionID, AnswerID, UpdateAnswer);
				response = request.respvalidate.returnresponseasstring();
				System.out.println(response);
				AssertJUnit.assertEquals("Style Forum - Update an Answer for a question API is not working.", 200,request.response.getStatus());
				AssertJUnit.assertEquals("Style Forum - Update an Answer for a question API is not working.[Answer ID is different]",AnswerID,JsonPath.read(response, "$.data.id").toString());
				AssertJUnit.assertEquals("Style Forum - Update an Answer for a question API is not working.[Question ID is different]",QuestionID,JsonPath.read(response, "$.data.questionId").toString());
				AssertJUnit.assertEquals("Style Forum - Update an Answer for a question API is not working.[Answer is not updated]",UpdateAnswer,JsonPath.read(response, "$.data.description[0].value"));

				request=styleForumServiceHelper.invokeStyleForumService_Delete(userName, password, APINAME.DELETEQUESTION, QuestionID);
				AssertJUnit.assertEquals("Style Forum Tests - TearDown Activity [ERROR] - Delete Poll Service is not working!",200,request.response.getStatus());
				
			
			}
			
			@Test(groups = {"Schema Validation"},dataProvider = "StyleForum_Questions_UpdateAnAnswer")
			public void StyleForum_Questions_updateAnswer_VerifySchema(String userName, String password, String Question, String Answer, String UpdateAnswer)
			{
				RequestGenerator request = styleForumServiceHelper.invokeStyleForumService_CreateAQuestion(userName, password,Question);
				String response = request.respvalidate.returnresponseasstring();
				System.out.println(response);
				AssertJUnit.assertEquals("Style Forum - Create new question API is not working.", 200,request.response.getStatus());
				String QuestionID = JsonPath.read(response, "$.data.id").toString();
				
				request=styleForumServiceHelper.invokeStyleForumService_answerAQuestion(userName, password, QuestionID, Answer);
				response = request.respvalidate.returnresponseasstring();
				System.out.println(response);
				AssertJUnit.assertEquals("Style Forum - Answer a question API is not working.", 200,request.response.getStatus());
				String AnswerID = JsonPath.read(response, "$.data.id").toString();
				
				request=styleForumServiceHelper.invokeStyleForumService_updateAnAnswer(userName, password, QuestionID, AnswerID, UpdateAnswer);
				response = request.respvalidate.returnresponseasstring();
				System.out.println("Answer Update Response : \n\n ");
				System.out.println(response);
				AssertJUnit.assertEquals("Style Forum - Update an Answer for a question API is not working.", 200,request.response.getStatus());
				try 
				{
					String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/styleforum-questions-updateananswer-schema.txt");
					List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(response, jsonschema);
					AssertJUnit.assertTrue(missingNodeList+" nodes are missing in Style Forum - Get My Profile API response",CollectionUtils.isEmpty(missingNodeList));
		    	}catch (Exception e)
		    		{
		    			e.printStackTrace();
		    		}
			
				request=styleForumServiceHelper.invokeStyleForumService_Delete(userName, password, APINAME.DELETEQUESTION, QuestionID);
				AssertJUnit.assertEquals("Style Forum Tests - TearDown Activity [ERROR] - Delete Poll Service is not working!",200,request.response.getStatus());
				
			}
			
			//Get Answers for all questions
			@Test(groups = {"Smoke", "ProdSanity", "Sanity", "MiniRegression", "ExhaustiveRegression"},dataProvider = "StyleForum_Questions_AnswerAQuestion")
			public void StyleForum_Questions_GetAnswers_VerifySuccess(String userName, String password, String Question, String Answer)
			{
				RequestGenerator request = styleForumServiceHelper.invokeStyleForumService_CreateAQuestion(userName, password,Question);
				String response = request.respvalidate.returnresponseasstring();
				System.out.println(response);
				AssertJUnit.assertEquals("Style Forum - Create new question API is not working.", 200,request.response.getStatus());
				String QuestionID = JsonPath.read(response, "$.data.id").toString();
				
				request=styleForumServiceHelper.invokeStyleForumService_answerAQuestion(userName, password, QuestionID, Answer);
				response = request.respvalidate.returnresponseasstring();
				System.out.println(response);
				
				request = styleForumServiceHelper.invokeStyleForumService_GetAnswers(userName, password, QuestionID);
				response = request.respvalidate.returnresponseasstring();
				System.out.println(response);
				
				AssertJUnit.assertEquals("Style Forum - Get Answers for a question API is not working. ",200,request.response.getStatus());
			
				request=styleForumServiceHelper.invokeStyleForumService_Delete(userName, password, APINAME.DELETEQUESTION, QuestionID);
				AssertJUnit.assertEquals("Style Forum Tests - TearDown Activity [ERROR] - Delete Poll Service is not working!",200,request.response.getStatus());
				
			}
			
			@Test(groups = {"Smoke", "ProdSanity", "Sanity", "MiniRegression", "ExhaustiveRegression"},dataProvider = "StyleForum_Questions_GetAnswers_Text_VerifySuccess")
			public void StyleForum_Questions_GetAnswers_verifyData(String userName, String password, String Question, String Answer, String Update)
			{
				RequestGenerator request = styleForumServiceHelper.invokeStyleForumService_CreateAQuestion(userName, password,Question);
				String response = request.respvalidate.returnresponseasstring();
				System.out.println(response);
				AssertJUnit.assertEquals("Style Forum - Create new question API is not working.", 200,request.response.getStatus());
				String QuestionID = JsonPath.read(response, "$.data.id").toString();
				
				request=styleForumServiceHelper.invokeStyleForumService_answerAQuestion(userName, password, QuestionID, Answer);
				response = request.respvalidate.returnresponseasstring();
				System.out.println(response);
				
				request = styleForumServiceHelper.invokeStyleForumService_GetAnswers(userName, password, QuestionID);
				response = request.respvalidate.returnresponseasstring();
				System.out.println(response);
				
				AssertJUnit.assertEquals("Style Forum - Get Answers for a question API is not working. ",200,request.response.getStatus());
				AssertJUnit.assertEquals("Style Forum - Get Answers for a question API is not working. [Answer type is not proper] ","ANSWER",JsonPath.read(response, "$.data.answers[0].type"));
				AssertJUnit.assertEquals("Style Forum - Get Answers for a question API is not working. [Answer type is not proper] ","TEXT",JsonPath.read(response, "$.data.answers[0].description[0].type"));
				AssertJUnit.assertEquals("Style Forum - Get Answers for a question API is not working. [Answer value is not proper] ",Answer,JsonPath.read(response, "$.data.answers[0].description[0].value"));	
			
				request=styleForumServiceHelper.invokeStyleForumService_Delete(userName, password, APINAME.DELETEQUESTION, QuestionID);
				AssertJUnit.assertEquals("Style Forum Tests - TearDown Activity [ERROR] - Delete Poll Service is not working!",200,request.response.getStatus());
				
			
			}
			
			
			@Test(groups = {"Schema Validation"},dataProvider = "StyleForum_Questions_GetAnswers_Text_VerifySuccess")
			public void StyleForum_Questions_GetAnswers_verifySchema(String userName, String password, String Question, String Answer, String Update)
			{
				RequestGenerator request = styleForumServiceHelper.invokeStyleForumService_CreateAQuestion(userName, password,Question);
				String response = request.respvalidate.returnresponseasstring();
				System.out.println(response);
				AssertJUnit.assertEquals("Style Forum - Create new question API is not working.", 200,request.response.getStatus());
				String QuestionID = JsonPath.read(response, "$.data.id").toString();
				
				request=styleForumServiceHelper.invokeStyleForumService_answerAQuestion(userName, password, QuestionID, Answer);
				response = request.respvalidate.returnresponseasstring();
				System.out.println(response);
				
				request = styleForumServiceHelper.invokeStyleForumService_GetAnswers(userName, password, QuestionID);
				response = request.respvalidate.returnresponseasstring();
				System.out.println(response);
				
				AssertJUnit.assertEquals("Style Forum - Get Answers for a question API is not working. ",200,request.response.getStatus());
				try 
				{
					String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/styleforum-questions-getanswers-schema.txt");
					List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(response, jsonschema);
					AssertJUnit.assertTrue(missingNodeList+" nodes are missing in Style Forum - Get My Profile API response",CollectionUtils.isEmpty(missingNodeList));
		    	}catch (Exception e)
		    		{
		    			e.printStackTrace();
		    		}
				
				request=styleForumServiceHelper.invokeStyleForumService_Delete(userName, password, APINAME.DELETEQUESTION, QuestionID);
				AssertJUnit.assertEquals("Style Forum Tests - TearDown Activity [ERROR] - Delete Poll Service is not working!",200,request.response.getStatus());
				
		}
			@Test(groups = {"Smoke", "ProdSanity", "Sanity", "MiniRegression", "ExhaustiveRegression"},dataProvider = "StyleForum_Questions_GetMulitpleAnswersByDifferentUsers_verifyData")
			public void StyleForum_Questions_GetMulitpleAnswersByDifferentUsers_verifyData(String userName1,String userName2,String userName3, String password, String Question)
			{
				RequestGenerator request = styleForumServiceHelper.invokeStyleForumService_CreateAQuestion(userName1, password,Question);
				String response = request.respvalidate.returnresponseasstring();
				System.out.println(response);
				AssertJUnit.assertEquals("Style Forum - Create new question API is not working.", 200,request.response.getStatus());
				String QuestionID = JsonPath.read(response, "$.data.id").toString();
				
				request=styleForumServiceHelper.invokeStyleForumService_answerAQuestion(userName1, password, QuestionID, "Answer 1");
				response = request.respvalidate.returnresponseasstring();
				System.out.println(response);
				
				request=styleForumServiceHelper.invokeStyleForumService_answerAQuestion(userName2, password, QuestionID, "Answer 2");
				response = request.respvalidate.returnresponseasstring();
				System.out.println(response);
				
				request=styleForumServiceHelper.invokeStyleForumService_answerAQuestion(userName3, password, QuestionID, "Answer 3");
				response = request.respvalidate.returnresponseasstring();
				System.out.println(response);
				
				request = styleForumServiceHelper.invokeStyleForumService_GetAnswers(userName3, password, QuestionID);
				response = request.respvalidate.returnresponseasstring();
				System.out.println(response);
				
				AssertJUnit.assertEquals("Style Forum - Get Answers for a question API is not working. ",200,request.response.getStatus());
				AssertJUnit.assertEquals("Style Forum - Get Answers for a question API is not working. [Answer type is not proper] ","ANSWER",JsonPath.read(response, "$.data.answers[0].type"));
				AssertJUnit.assertEquals("Style Forum - Get Answers for a question API is not working. [Answer type is not proper] ","TEXT",JsonPath.read(response, "$.data.answers[0].description[0].type"));
				AssertJUnit.assertEquals("Style Forum - Get Answers for a question API is not working. [Answer value is not proper] ","Answer 3",JsonPath.read(response, "$.data.answers[0].description[0].value"));
				
				AssertJUnit.assertEquals("Style Forum - Get Answers for a question API is not working. [Answer type is not proper] ","TEXT",JsonPath.read(response, "$.data.answers[1].description[0].type"));
				AssertJUnit.assertEquals("Style Forum - Get Answers for a question API is not working. [Answer value is not proper] ","Answer 2",JsonPath.read(response, "$.data.answers[1].description[0].value"));
				
				AssertJUnit.assertEquals("Style Forum - Get Answers for a question API is not working. [Answer type is not proper] ","TEXT",JsonPath.read(response, "$.data.answers[2].description[0].type"));
				AssertJUnit.assertEquals("Style Forum - Get Answers for a question API is not working. [Answer value is not proper] ","Answer 1",JsonPath.read(response, "$.data.answers[2].description[0].value"));
				
				request=styleForumServiceHelper.invokeStyleForumService_Delete(userName1, password, APINAME.DELETEQUESTION, QuestionID);
				AssertJUnit.assertEquals("Style Forum Tests - TearDown Activity [ERROR] - Delete Poll Service is not working!",200,request.response.getStatus());
				
			}
			
			// Mark Question As Spam
			@Test(groups = {"Smoke", "ProdSanity", "Sanity", "MiniRegression", "ExhaustiveRegression"},dataProvider = "StyleForum_Questions_GetMulitpleAnswersByDifferentUsers_verifyData")
			public void StyleForum_Questions_MarkQuestionAsSpam_SameUser_verifySucess(String userName1,String userName2,String userName3, String password, String Question)
			{
				RequestGenerator request = styleForumServiceHelper.invokeStyleForumService_CreateAQuestion(userName1, password,Question);
				String response = request.respvalidate.returnresponseasstring();
				System.out.println(response);
				AssertJUnit.assertEquals("Style Forum - Create new question API is not working.", 200,request.response.getStatus());
				String QuestionID = JsonPath.read(response, "$.data.id").toString();
			
				request = styleForumServiceHelper.invokeStyleForumService_MarkAsSpam(userName1, password, QuestionID);
				response = request.respvalidate.returnresponseasstring();
				System.out.println(response);
				AssertJUnit.assertEquals("Style Forum - Mark Question as Spam API is not working",200,request.response.getStatus());
			
				request=styleForumServiceHelper.invokeStyleForumService_Delete(userName1, password, APINAME.DELETEQUESTION, QuestionID);
				AssertJUnit.assertEquals("Style Forum Tests - TearDown Activity [ERROR] - Delete Poll Service is not working!",200,request.response.getStatus());
				
		}
			
			@Test(groups = {"Smoke", "ProdSanity", "Sanity", "MiniRegression", "ExhaustiveRegression"},dataProvider = "StyleForum_Questions_GetMulitpleAnswersByDifferentUsers_verifyData")
			public void StyleForum_Questions_MarkQuestionAsSpam_DifferentUser_verifySucess(String userName1,String userName2,String userName3, String password, String Question)
			{
				RequestGenerator request = styleForumServiceHelper.invokeStyleForumService_CreateAQuestion(userName1, password,Question);
				String response = request.respvalidate.returnresponseasstring();
				System.out.println(response);
				AssertJUnit.assertEquals("Style Forum - Create new question API is not working.", 200,request.response.getStatus());
				String QuestionID = JsonPath.read(response, "$.data.id").toString();
			
				request = styleForumServiceHelper.invokeStyleForumService_MarkAsSpam(userName2, password, QuestionID);
				response = request.respvalidate.returnresponseasstring();
				System.out.println(response);
				AssertJUnit.assertEquals("Style Forum - Mark Question as Spam API is not working",200,request.response.getStatus());
			
				request=styleForumServiceHelper.invokeStyleForumService_Delete(userName1, password, APINAME.DELETEQUESTION, QuestionID);
				AssertJUnit.assertEquals("Style Forum Tests - TearDown Activity [ERROR] - Delete Poll Service is not working!",200,request.response.getStatus());
				
			}
			
			@Test(groups = {"Smoke", "ProdSanity", "Sanity", "MiniRegression", "ExhaustiveRegression"},dataProvider = "StyleForum_Questions_GetMulitpleAnswersByDifferentUsers_verifyData")
			public void StyleForum_Questions_MarkQuestionAsSpam_DifferentUser_MultipleTimes_verifyFailure(String userName1,String userName2,String userName3, String password, String Question)
			{
				RequestGenerator request = styleForumServiceHelper.invokeStyleForumService_CreateAQuestion(userName1, password,Question);
				String response = request.respvalidate.returnresponseasstring();
				System.out.println(response);
				AssertJUnit.assertEquals("Style Forum - Create new question API is not working.", 200,request.response.getStatus());
				String QuestionID = JsonPath.read(response, "$.data.id").toString();
			
				request = styleForumServiceHelper.invokeStyleForumService_MarkAsSpam(userName2, password, QuestionID);
				response = request.respvalidate.returnresponseasstring();
				System.out.println(response);
				AssertJUnit.assertEquals("Style Forum - Mark Question as Spam API is not working",200,request.response.getStatus());
				AssertJUnit.assertEquals("Style Forum - Mark Question as Spam API is not working. [User is able to mark a question as spam multiple times","true",JsonPath.read(response, "$.data").toString());

				
				request = styleForumServiceHelper.invokeStyleForumService_MarkAsSpam(userName2, password, QuestionID);
				response = request.respvalidate.returnresponseasstring();
				System.out.println(response);
				AssertJUnit.assertEquals("Style Forum - Mark Question as Spam API is not working",200,request.response.getStatus());
				AssertJUnit.assertEquals("Style Forum - Mark Question as Spam API is not working. [User is able to mark a question as spam multiple times",null,JsonPath.read(response, "$.data").toString());
				
				request=styleForumServiceHelper.invokeStyleForumService_Delete(userName1, password, APINAME.DELETEQUESTION, QuestionID);
				AssertJUnit.assertEquals("Style Forum Tests - TearDown Activity [ERROR] - Delete Poll Service is not working!",200,request.response.getStatus());
				
			
			}
			
			@Test(groups = {"Smoke", "ProdSanity", "Sanity", "MiniRegression", "ExhaustiveRegression"},dataProvider = "StyleForum_Questions_AnswerSpamQuestion_VerifyFailure")
			public void StyleForum_Questions_AnswerSpamQuestion_VerifyFailure(String userName, String password, String Question, String Answer)
			{
				RequestGenerator request = styleForumServiceHelper.invokeStyleForumService_CreateAQuestion(userName, password,Question);
				String response = request.respvalidate.returnresponseasstring();
				System.out.println(response);
				String QuestionID = JsonPath.read(response, "$.data.id").toString();
				AssertJUnit.assertEquals("Style Forum - Create a Question - API is not working",200,request.response.getStatus());
				
				request = styleForumServiceHelper.invokeStyleForumService_MarkAsSpam(userName, password, QuestionID);
				response = request.respvalidate.returnresponseasstring();
				System.out.println(response);
				AssertJUnit.assertEquals("Style Forum - Mark Question as Spam API is not working",200,request.response.getStatus());
				
				request=styleForumServiceHelper.invokeStyleForumService_answerAQuestion(userName, password, QuestionID, Answer);
				response = request.respvalidate.returnresponseasstring();
				System.out.println(response);
				AssertJUnit.assertEquals("Style Forum - Mark Question as Spam API is not working.",200,request.response.getStatus());
			
				request=styleForumServiceHelper.invokeStyleForumService_Delete(userName, password, APINAME.DELETEQUESTION, QuestionID);
				AssertJUnit.assertEquals("Style Forum Tests - TearDown Activity [ERROR] - Delete  Service is not working!",200,request.response.getStatus());
				

			}
		
			@Test(groups = {"Smoke", "ProdSanity", "Sanity", "MiniRegression", "ExhaustiveRegression"},dataProvider = "StyleForum_Questions_GetMulitpleAnswersByDifferentUsers_verifyData")
			public void StyleForum_Questions_UnMarkQuestionAsSpam_DifferentUser_verifySucess(String userName1,String userName2,String userName3, String password, String Question)
			{
				RequestGenerator request = styleForumServiceHelper.invokeStyleForumService_CreateAQuestion(userName1, password,Question);
				String response = request.respvalidate.returnresponseasstring();
				System.out.println(response);
				AssertJUnit.assertEquals("Style Forum - Create new question API is not working.", 200,request.response.getStatus());
				String QuestionID = JsonPath.read(response, "$.data.id").toString();
			
				request = styleForumServiceHelper.invokeStyleForumService_MarkAsSpam(userName2, password, QuestionID);
				response = request.respvalidate.returnresponseasstring();
				System.out.println(response);
				AssertJUnit.assertEquals("Style Forum - UnMark Question as Spam API is not working",200,request.response.getStatus());
			
				request = styleForumServiceHelper.invokeStyleForumService_GetAnswers(userName1, password, QuestionID);
				response = request.respvalidate.returnresponseasstring();
				System.out.println(response);
			
				request=styleForumServiceHelper.invokeStyleForumService_Delete(userName1, password, APINAME.DELETEQUESTION, QuestionID);
				AssertJUnit.assertEquals("Style Forum Tests - TearDown Activity [ERROR] - Delete Poll Service is not working!",200,request.response.getStatus());
				
			}
		
			//Comment on Question Flow
			@Test(groups = {"Smoke", "ProdSanity", "Sanity", "MiniRegression", "ExhaustiveRegression"},dataProvider = "CommentOnQuestion")
			public void Comment_On_Question_Flow(String userName, String password, String Question, String Category, String Comment)
			{
				//Post Question
				RequestGenerator request = styleForumServiceHelper.invokeStyleForumService_CreateAQuestion(userName, password,Question);
				String response = request.respvalidate.returnresponseasstring();
				System.out.println(response);
				String QuestionID = JsonPath.read(response, "$.data.id").toString();
				AssertJUnit.assertEquals("Style Forum - Create new question API is not working.", 200,request.response.getStatus());
				
				//Update Category
				request = styleForumServiceHelper.invokeStyleForumService_updateAQuestion(userName, password, QuestionID, Question, Category);
				response = request.respvalidate.returnresponseasstring();
				QuestionID = JsonPath.read(response, "$.data.id").toString();
				System.out.println(response);
				AssertJUnit.assertEquals("Style Forum - Update Category API is not working.", 200,request.response.getStatus());
				AssertJUnit.assertEquals("Style Forum - Update Category API is not working.[Question ID is not proper]",QuestionID,JsonPath.read(response, "$.data.id").toString());
				
				//Comment on Question
				request = styleForumServiceHelper.invokeStyleForumService_Comment(userName, password, APINAME.COMMENTONQUESTION, QuestionID, Comment);
				response = request.respvalidate.returnresponseasstring();
				System.out.println(response);
				AssertJUnit.assertEquals("Comment on Question is not working",200,request.response.getStatus());
				
				
				request=styleForumServiceHelper.invokeStyleForumService_Delete(userName, password, APINAME.DELETEQUESTION, QuestionID);
				AssertJUnit.assertEquals("Style Forum Tests - TearDown Activity [ERROR] - Delete Question Service is not working!",200,request.response.getStatus());
				
			}
		
			//Comment on Question Flow
			@Test(groups = {"Smoke", "ProdSanity", "Sanity", "MiniRegression", "ExhaustiveRegression"},dataProvider = "CommentOnQuestion")
			public void Mark_QuestionComment_as_Spam(String userName, String password, String Question, String Category, String Comment)
			{
				//Post Question
				RequestGenerator request = styleForumServiceHelper.invokeStyleForumService_CreateAQuestion(userName, password,Question);
				String response = request.respvalidate.returnresponseasstring();
				System.out.println(response);
				String QuestionID = JsonPath.read(response, "$.data.id").toString();
				AssertJUnit.assertEquals("Style Forum - Create new question API is not working.", 200,request.response.getStatus());
				
				//Update Category
				request = styleForumServiceHelper.invokeStyleForumService_updateAQuestion(userName, password, QuestionID, Question, Category);
				response = request.respvalidate.returnresponseasstring();
				System.out.println(response);
				AssertJUnit.assertEquals("Style Forum - Update Category API is not working.", 200,request.response.getStatus());
				AssertJUnit.assertEquals("Style Forum - Update Category API is not working.[Question ID is not proper]",QuestionID,JsonPath.read(response, "$.data.id").toString());
				
				//Comment on Question
				request = styleForumServiceHelper.invokeStyleForumService_Comment(userName, password, APINAME.COMMENTONQUESTION, QuestionID, Comment);
				response = request.respvalidate.returnresponseasstring();
				System.out.println(response);
				AssertJUnit.assertEquals("Comment on Question is not working",200,request.response.getStatus());
				String CommentID = JsonPath.read(response, "$.data.id").toString();
				
				//Mark Comment as Spam
				request = styleForumServiceHelper.invokeStyleForumService_CommentSpamUnSpam(userName, password, APINAME.MARKCOMMENTASSPAM, CommentID);
				response = request.respvalidate.returnresponseasstring();
				System.out.println(response);
				AssertJUnit.assertEquals("Mark Comment as Spam API is not working",200,request.response.getStatus());
				
				//Get Question Comments
				request = styleForumServiceHelper.invokeStyleForumService_getComments(userName, password, APINAME.GETCOMMENTSFORQUESTION, QuestionID);
				response = request.respvalidate.returnresponseasstring();
				System.out.println(response);
				//AssertJUnit.assertEquals("Mark as Spam - Commen API is not working",true,JsonPath.read(response,"$.data.comments[0].isSpammed" ));
				
				//UnMark Comment as Spam
				request = styleForumServiceHelper.invokeStyleForumService_CommentSpamUnSpam(userName, password, APINAME.UNMARKCOMMENTASSPAM, CommentID);
				response = request.respvalidate.returnresponseasstring();
				System.out.println(response);
				AssertJUnit.assertEquals("UnMark Comment as Spam API is not working",200,request.response.getStatus());
				
				//Get Question Comments
				request = styleForumServiceHelper.invokeStyleForumService_getComments(userName, password, APINAME.GETCOMMENTSFORQUESTION, QuestionID);
				response = request.respvalidate.returnresponseasstring();
				System.out.println(response);
				AssertJUnit.assertEquals("UnMark as Spam - Comment API is not working",false,Boolean.parseBoolean(JsonPath.read(response,"$.data.comments[0].isSpammed" )));
				
				
				request=styleForumServiceHelper.invokeStyleForumService_Delete(userName, password, APINAME.DELETEQUESTION, QuestionID);
				AssertJUnit.assertEquals("Style Forum Tests - TearDown Activity [ERROR] - Delete Question Service is not working!",200,request.response.getStatus());
				
			}
			//Answer a Question Flow
			@Test(groups = {"Smoke", "ProdSanity", "Sanity", "MiniRegression", "ExhaustiveRegression"},dataProvider = "CommentonAnswerFlow")
			public void Comment_on_Answer_Flow(String userName, String password, String Question, String Category, String Answer, String Comment)
			{
				//Post Question
				RequestGenerator request = styleForumServiceHelper.invokeStyleForumService_CreateAQuestion(userName, password,Question);
				String response = request.respvalidate.returnresponseasstring();
				System.out.println(response);
				String QuestionID = JsonPath.read(response, "$.data.id").toString();
				AssertJUnit.assertEquals("Style Forum - Create new question API is not working.", 200,request.response.getStatus());
				
				//Update Category
				request = styleForumServiceHelper.invokeStyleForumService_updateAQuestion(userName, password, QuestionID, Question, Category);
				response = request.respvalidate.returnresponseasstring();
				System.out.println(response);
				QuestionID = JsonPath.read(response, "$.data.id").toString();
				AssertJUnit.assertEquals("Style Forum - Update Question API is not working.", 200,request.response.getStatus());
			
				//Answer a Question
				request = styleForumServiceHelper.invokeStyleForumService_answerAQuestion(userName, password, QuestionID, Answer);
				response = request.respvalidate.returnresponseasstring();
				System.out.println(response);
				String AnswerID = JsonPath.read(response, "$.data.id").toString();
				AssertJUnit.assertEquals("Answer a Question API is not working!",200,request.response.getStatus());
				AssertJUnit.assertEquals("Answer a Question API is not working!-[Answer is not added]",Answer,JsonPath.read(response, "$.data.description[0].value"));
				
				//Comment on Answer
				request = styleForumServiceHelper.invokeStyleForumService_Comment(userName, password, APINAME.COMMENTONANSWER, AnswerID, Comment);
				response = request.respvalidate.returnresponseasstring();  
				AssertJUnit.assertEquals("Comment on Answer API is not working",200,request.response.getStatus());
				
				//Get Answers of Question
				request = styleForumServiceHelper.invokeStyleForumService_GetAnswers(userName, password, QuestionID);
				response = request.respvalidate.returnresponseasstring();
				System.out.println(response);
				
				
				//Tear Down Activity
				request=styleForumServiceHelper.invokeStyleForumService_Delete(userName, password, APINAME.DELETEQUESTION, QuestionID);
				AssertJUnit.assertEquals("Style Forum Tests - TearDown Activity [ERROR] - Delete Question Service is not working!",200,request.response.getStatus());
			}
		
			//Answer a Question Flow
			@Test(groups = {"Smoke", "ProdSanity", "Sanity", "MiniRegression", "ExhaustiveRegression"},dataProvider = "CommentonAnswerFlow")
			public void AnswerCommentSpamUnSpam(String userName, String password, String Question, String Category, String Answer, String Comment)
			{
				//Post Question
				RequestGenerator request = styleForumServiceHelper.invokeStyleForumService_CreateAQuestion(userName, password,Question);
				String response = request.respvalidate.returnresponseasstring();
				System.out.println(response);
				String QuestionID = JsonPath.read(response, "$.data.id").toString();
				AssertJUnit.assertEquals("Style Forum - Create new question API is not working.", 200,request.response.getStatus());
				
				//Update Category
				request = styleForumServiceHelper.invokeStyleForumService_updateAQuestion(userName, password, QuestionID, Question, Category);
				response = request.respvalidate.returnresponseasstring();
				System.out.println(response);
				QuestionID = JsonPath.read(response, "$.data.id").toString();
				AssertJUnit.assertEquals("Style Forum - Update Question API is not working.", 200,request.response.getStatus());
			
				//Answer a Question
				request = styleForumServiceHelper.invokeStyleForumService_answerAQuestion(userName, password, QuestionID, Answer);
				response = request.respvalidate.returnresponseasstring();
				System.out.println(response);
				String AnswerID = JsonPath.read(response, "$.data.id").toString();
				AssertJUnit.assertEquals("Answer a Question API is not working!",200,request.response.getStatus());
				AssertJUnit.assertEquals("Answer a Question API is not working!-[Answer is not added]",Answer,JsonPath.read(response, "$.data.description[0].value"));
				
				//Comment on Answer
				request = styleForumServiceHelper.invokeStyleForumService_Comment(userName, password, APINAME.COMMENTONANSWER, AnswerID, Comment);
				response = request.respvalidate.returnresponseasstring();
				System.out.println(response);
				String CommentID = JsonPath.read(response, "$.data.id").toString();
				AssertJUnit.assertEquals("Comment on Answer API is not working",200,request.response.getStatus());
				
				//Mark Comment as Spam
				request = styleForumServiceHelper.invokeStyleForumService_CommentSpamUnSpam(userName, password, APINAME.MARKCOMMENTASSPAM, CommentID);
				response = request.respvalidate.returnresponseasstring();
				System.out.println(response);
				AssertJUnit.assertEquals("Mark Comment as Spam API is not working",200,request.response.getStatus());
				
				
				/*//Get Answer Comments
				request = styleForumServiceHelper.invokeStyleForumService_getComments(userName, password, APINAME.GETCOMMENTSFORANSWER, AnswerID);
				response = request.respvalidate.returnresponseasstring();
				System.out.println(response);
				AssertJUnit.assertEquals("Mark as Spam - Commen API is not working",true,JsonPath.read(response,"$.data.comments[0].isSpammed" ));
				 */	
				
				//UnMark Comment as Spam
				request = styleForumServiceHelper.invokeStyleForumService_CommentSpamUnSpam(userName, password, APINAME.UNMARKCOMMENTASSPAM, CommentID);
				response = request.respvalidate.returnresponseasstring();
				System.out.println(response);
				AssertJUnit.assertEquals("UnMark Comment as Spam API is not working",200,request.response.getStatus());
				
				
				/*//Get Answer Comments
				request = styleForumServiceHelper.invokeStyleForumService_getComments(userName, password, APINAME.GETCOMMENTSFORANSWER, AnswerID);
				response = request.respvalidate.returnresponseasstring();
				System.out.println(response);
				AssertJUnit.assertEquals("UnMark as Spam - Comment API is not working",false,JsonPath.read(response,"$.data.comments[0].isSpammed" ));
				
				QuestionID = JsonPath.read(response, "$.data.id").toString();
				request=styleForumServiceHelper.invokeStyleForumService_Delete(userName, password, APINAME.DELETEQUESTION, QuestionID);
				AssertJUnit.assertEquals("Style Forum Tests - TearDown Activity [ERROR] - Delete Question Service is not working!",200,request.response.getStatus());
				*/
				}
		
			//Answer a Question Flow
			@Test(groups = {"Smoke", "ProdSanity", "Sanity", "MiniRegression", "ExhaustiveRegression"},dataProvider = "AnswerAQuestionFlow")
			public void AnswerLikeUnLike(String userName, String password, String Question, String Category, String Answer)
			{
				//Post Question
				RequestGenerator request = styleForumServiceHelper.invokeStyleForumService_CreateAQuestion(userName, password,Question);
				String response = request.respvalidate.returnresponseasstring();
				System.out.println(response);
				String QuestionID = JsonPath.read(response, "$.data.id").toString();
				AssertJUnit.assertEquals("Style Forum - Create new question API is not working.", 200,request.response.getStatus());
				
				//Update Category
				request = styleForumServiceHelper.invokeStyleForumService_updateAQuestion(userName, password, QuestionID, Question, Category);
				response = request.respvalidate.returnresponseasstring();
				System.out.println(response);
				QuestionID = JsonPath.read(response, "$.data.id").toString();
				AssertJUnit.assertEquals("Style Forum - Update Question API is not working.", 200,request.response.getStatus());
			
				//Answer a Question
				request = styleForumServiceHelper.invokeStyleForumService_answerAQuestion(userName, password, QuestionID, Answer);
				response = request.respvalidate.returnresponseasstring();
				System.out.println(response);
				String AnswerID = JsonPath.read(response, "$.data.id").toString();
				AssertJUnit.assertEquals("Answer a Question API is not working!",200,request.response.getStatus());
				AssertJUnit.assertEquals("Answer a Question API is not working!-[Answer is not added]",Answer,JsonPath.read(response, "$.data.description[0].value"));
				
				//Like An Answer
				request= styleForumServiceHelper.invokeStyleForumService_LikeActivity(userName, password, APINAME.LIKEANSWER, AnswerID);
				response = request.respvalidate.returnresponseasstring();
				System.out.println(response);
				AssertJUnit.assertEquals("Like an Answer API is not working",200,request.response.getStatus());
				
				/*//Get Personalized Data
				request =  styleForumServiceHelper.invokeStyleForumService_GetPersonalizedData(userName, password, AnswerID, "ANSWER");
				response = request.respvalidate.returnresponseasstring();
				System.out.println(response);
				AssertJUnit.assertEquals("Like an Answer API is not working",true,JsonPath.read(response,"$.data.post[0].isLiked" ));
				AssertJUnit.assertEquals("Is Owner Property is not returning expected data",true,JsonPath.read(response,"$.data.post[0].isOwner" ));
*/

				//UnLike An Answer
				request= styleForumServiceHelper.invokeStyleForumService_LikeActivity(userName, password, APINAME.UNLIKEANSWER, AnswerID);
				response = request.respvalidate.returnresponseasstring();
				System.out.println(response);
				AssertJUnit.assertEquals("Like an Answer API is not working",200,request.response.getStatus());
				
				//Get Personalized Data
				/*request =  styleForumServiceHelper.invokeStyleForumService_GetPersonalizedData(userName, password, AnswerID, "ANSWER");
				response = request.respvalidate.returnresponseasstring();
				System.out.println(response);
				AssertJUnit.assertEquals("Like an Answer API is not working",false,JsonPath.read(response,"$.data.post[0].isLiked" ));
				AssertJUnit.assertEquals("Is Owner Property is not returning expected data",true,JsonPath.read(response,"$.data.post[0].isOwner" ));
	*/
				request=styleForumServiceHelper.invokeStyleForumService_Delete(userName, password, APINAME.DELETEQUESTION, QuestionID);
				AssertJUnit.assertEquals("Style Forum Tests - TearDown Activity [ERROR] - Delete Question Service is not working!",200,request.response.getStatus());
			
				}
			
			//Follow & UnFollow Question
			//Comment on Question Flow
			@Test(groups = {"Smoke", "ProdSanity", "Sanity", "MiniRegression", "ExhaustiveRegression"},dataProvider = "CommentOnQuestion")
			public void FollowAndUnFollowQuestion(String userName, String password, String Question, String Category, String Comment)
			{
				//Post Question
				RequestGenerator request = styleForumServiceHelper.invokeStyleForumService_CreateAQuestion(userName, password,Question);
				String response = request.respvalidate.returnresponseasstring();
				System.out.println(response);
				String QuestionID = JsonPath.read(response, "$.data.id").toString();
				AssertJUnit.assertEquals("Style Forum - Create new question API is not working.", 200,request.response.getStatus());
				
				//Update Category
				request = styleForumServiceHelper.invokeStyleForumService_updateAQuestion(userName, password, QuestionID, Question, Category);
				response = request.respvalidate.returnresponseasstring();
				System.out.println(response);
				AssertJUnit.assertEquals("Style Forum - Update Category API is not working.", 200,request.response.getStatus());
				AssertJUnit.assertEquals("Style Forum - Update Category API is not working.[Question ID is not proper]",QuestionID,JsonPath.read(response, "$.data.id").toString());
				
				//Follow Question
				request = styleForumServiceHelper.invokeStyleForumService_FollowActivity(userName, password, APINAME.FOLLOWQUESTION, QuestionID);
				response = request.respvalidate.returnresponseasstring();
				System.out.println(response);
				AssertJUnit.assertEquals("Follow Question API is not working",200,request.response.getStatus());
				
				//Get Answers of Question
				request = styleForumServiceHelper.invokeStyleForumService_GetAnswers(userName, password, QuestionID);
				response = request.respvalidate.returnresponseasstring();
				System.out.println(response);
				//AssertJUnit.assertEquals("Get Answers API is not working", true, JsonPath.read(response, "$.data.answers[0].isLiked"));
				
				//UnFollow Question
				request = styleForumServiceHelper.invokeStyleForumService_FollowActivity(userName, password, APINAME.UNFOLLOWQUESTION, QuestionID);
				response = request.respvalidate.returnresponseasstring();
				System.out.println(response);
				AssertJUnit.assertEquals("Follow Question API is not working",200,request.response.getStatus());
				
				//Get Answers of Question
				request = styleForumServiceHelper.invokeStyleForumService_GetAnswers(userName, password, QuestionID);
				response = request.respvalidate.returnresponseasstring();
				System.out.println(response);
				//AssertJUnit.assertEquals("Get Answers API is not working", true, JsonPath.read(response, "$.data.answers[0].isLiked"));
				
				
				
				request=styleForumServiceHelper.invokeStyleForumService_Delete(userName, password, APINAME.DELETEQUESTION, QuestionID);
				AssertJUnit.assertEquals("Style Forum Tests - TearDown Activity [ERROR] - Delete Question Service is not working!",200,request.response.getStatus());
				
			}
			
		
		
		
		
		//Other Tests
		//USER PROFILE TESTS
		//GET User Profile
		
		@Test(groups = {"Smoke", "ProdSanity", "Sanity", "MiniRegression", "ExhaustiveRegression"},dataProvider = "StyleForum_Profile_GetUserProfile_SuccessResponse")
		public void StyleForum_Profile_GetUserProfile_SuccessResponse(String userName, String password)
		{
			RequestGenerator request = styleForumServiceHelper.invokeUserProfileService(userName, password,"P");
			String response = request.respvalidate.returnresponseasstring();
			System.out.println("\n Get my profile service response :\n"+response);
			AssertJUnit.assertEquals("StyleForum - Get My Profile API is not working.",200,request.response.getStatus());
		}
		
		//GET User Profile - Verify Schema Set
		@Test(groups = {"Schema Validation"},dataProvider = "StyleForum_Profile_GetUserProfile_SuccessResponse")
		public void StyleForum_Profile_GetUserProfile_verifySchema(String userName, String password)
		{
			RequestGenerator request = styleForumServiceHelper.invokeUserProfileService(userName, password,"P");
			String response = request.respvalidate.returnresponseasstring();
			System.out.println("\n Get my profile service response :\n"+response);
			AssertJUnit.assertEquals("StyleForum - Get My Profile API is not working.",200,request.response.getStatus());
			try 
			{
				String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/styleforum_profile_getmyprofile_schema.txt");
				List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(response, jsonschema);
				AssertJUnit.assertTrue(missingNodeList+" nodes are missing in Style Forum - Get My Profile API response",CollectionUtils.isEmpty(missingNodeList));
	    	}catch (Exception e)
	    		{
	    			e.printStackTrace();
	    		}
		}
		
		//GET User Profile - Invalid User ID - Verify Failure
		@Test(groups = {"Smoke", "ProdSanity", "Sanity", "MiniRegression", "ExhaustiveRegression"},dataProvider = "StyleForum_Profile_GetUserProfile_SuccessResponse")
		public void StyleForum_Profile_GetUserProfile_verifyFailure(String userName, String password)
		{
			RequestGenerator request = styleForumServiceHelper.invokeUserProfileService(userName, password,"N");
			String response = request.respvalidate.returnresponseasstring();
			System.out.println("\n Get my profile service response :\n"+response);
			AssertJUnit.assertEquals("StyleForum - Get My Profile API is not working.",400,request.response.getStatus());
		}
		
		
		
		
		
		
		//Feature & UnFeature Tests
		
		@Test(groups = {"Smoke", "ProdSanity", "Sanity", "MiniRegression", "ExhaustiveRegression"},dataProvider = "StyleForum_Questions_AnswerAQuestion")
		public void StyleForum_QuestionsAnswer_FeatureQuestionAndAnswer(String userName, String password, String Question, String Answer)
		{
			RequestGenerator request = styleForumServiceHelper.invokeStyleForumService_CreateAQuestion(userName, password,Question);
			String response = request.respvalidate.returnresponseasstring();
			System.out.println(response);
			AssertJUnit.assertEquals("Style Forum - Create new question API is not working.", 200,request.response.getStatus());
			String QuestionID = JsonPath.read(response, "$.data.id").toString();
			
			request = styleForumServiceHelper.invokeStyleForumService_FeatureActivity(userName, password, APINAME.FEATUREQUESTION, QuestionID);
			response = request.respvalidate.returnresponseasstring();
			System.out.println(response);
			AssertJUnit.assertEquals("Feature Question API is not working",200,request.response.getStatus());			
			
			/*request = styleForumServiceHelper.invokeStyleForumService_GetPersonalizedData(userName, password, QuestionID, "QUESTION");
			response = request.respvalidate.returnresponseasstring();
			System.out.println(response);
			AssertJUnit.assertEquals("Feature Answer API is not working", "true",JsonPath.read(response,"$.data.answers[0].isFeatured").toString());
		*/
			
			request=styleForumServiceHelper.invokeStyleForumService_answerAQuestion(userName, password, QuestionID, Answer);
			response = request.respvalidate.returnresponseasstring();
			System.out.println(response);
			String AnswerID = JsonPath.read(response, "$.data.id").toString();
			AssertJUnit.assertEquals("Style Forum - Answer a question API is not working.", 200,request.response.getStatus());
			AssertJUnit.assertEquals("Style Forum - Answer a question API is not working.[Type is not proper]","ANSWER",JsonPath.read(response, "$.data.type"));
			AssertJUnit.assertEquals("Style Forum - Answer a question API is not working.[Question ID is not proper]",QuestionID,JsonPath.read(response, "$.data.questionId").toString());
			AssertJUnit.assertEquals("Style Forum - Answer a question API is not working.[Answer is not proper]",Answer,JsonPath.read(response, "$.data.description[0].value"));

			request = styleForumServiceHelper.invokeStyleForumService_FeatureActivity(userName, password, APINAME.FEATUREANSWER, AnswerID);
			response = request.respvalidate.returnresponseasstring();
			System.out.println(response);
			AssertJUnit.assertEquals("Feature Question API is not working",200,request.response.getStatus());		
			
			/*request = styleForumServiceHelper.invokeStyleForumService_GetPersonalizedData(userName, password, AnswerID, "ANSWER");
			response = request.respvalidate.returnresponseasstring();
			System.out.println(response);
			AssertJUnit.assertEquals("Feature Answer API is not working", "true",JsonPath.read(response,"$.data.answers[0].isFeatured").toString());
			*/
			request=styleForumServiceHelper.invokeStyleForumService_Delete(userName, password, APINAME.DELETEQUESTION, QuestionID);
			AssertJUnit.assertEquals("Style Forum Tests - TearDown Activity [ERROR] - Delete Question Service is not working!",200,request.response.getStatus());
			
		}
		
		@Test(groups = {"Smoke", "ProdSanity", "Sanity", "MiniRegression", "ExhaustiveRegression"},dataProvider = "StyleForum_Questions_AnswerAQuestion")
		public void StyleForum_QuestionsAnswer_UnFeatureQuestionAndAnswer(String userName, String password, String Question, String Answer)
		{
			RequestGenerator request = styleForumServiceHelper.invokeStyleForumService_CreateAQuestion(userName, password,Question);
			String response = request.respvalidate.returnresponseasstring();
			System.out.println(response);
			AssertJUnit.assertEquals("Style Forum - Create new question API is not working.", 200,request.response.getStatus());
			String QuestionID = JsonPath.read(response, "$.data.id").toString();
			
			request = styleForumServiceHelper.invokeStyleForumService_FeatureActivity(userName, password, APINAME.FEATUREQUESTION, QuestionID);
			response = request.respvalidate.returnresponseasstring();
			System.out.println(response);
			AssertJUnit.assertEquals("Feature Question API is not working",200,request.response.getStatus());			
			
			request = styleForumServiceHelper.invokeStyleForumService_GetUserFeeds(userName, password);
			response = request.respvalidate.returnresponseasstring();
			System.out.println(response);
			AssertJUnit.assertEquals("Feature Question API is not working","true",JsonPath.read(response,"$.data.feed[0].postEntries[0].isFeatured").toString());			
		
			request = styleForumServiceHelper.invokeStyleForumService_FeatureActivity(userName, password, APINAME.UNFEATUREQUESTION, QuestionID);
			response = request.respvalidate.returnresponseasstring();
			System.out.println(response);
			AssertJUnit.assertEquals("Feature Question API is not working",200,request.response.getStatus());			
			
			request = styleForumServiceHelper.invokeStyleForumService_GetUserFeeds(userName, password);
			response = request.respvalidate.returnresponseasstring();
			System.out.println(response);
			AssertJUnit.assertEquals("Feature Question API is not working","false",JsonPath.read(response,"$.data.feed[0].postEntries[0].isFeatured").toString());			

			
			
			request=styleForumServiceHelper.invokeStyleForumService_answerAQuestion(userName, password, QuestionID, Answer);
			response = request.respvalidate.returnresponseasstring();
			System.out.println(response);
			String AnswerID = JsonPath.read(response, "$.data.id").toString();
			AssertJUnit.assertEquals("Style Forum - Answer a question API is not working.", 200,request.response.getStatus());
			AssertJUnit.assertEquals("Style Forum - Answer a question API is not working.[Type is not proper]","ANSWER",JsonPath.read(response, "$.data.type"));
			AssertJUnit.assertEquals("Style Forum - Answer a question API is not working.[Question ID is not proper]",QuestionID,JsonPath.read(response, "$.data.questionId").toString());
			AssertJUnit.assertEquals("Style Forum - Answer a question API is not working.[Answer is not proper]",Answer,JsonPath.read(response, "$.data.description[0].value"));

			request = styleForumServiceHelper.invokeStyleForumService_FeatureActivity(userName, password, APINAME.FEATUREANSWER, AnswerID);
			response = request.respvalidate.returnresponseasstring();
			System.out.println(response);
			AssertJUnit.assertEquals("Feature Question API is not working",200,request.response.getStatus());		
			
			request = styleForumServiceHelper.invokeStyleForumService_GetAnswers(userName, password, QuestionID);
			response = request.respvalidate.returnresponseasstring();
			System.out.println(response);
			AssertJUnit.assertEquals("Feature Answer API is not working", "true",JsonPath.read(response,"$.data.answers[0].isFeatured").toString());
			
			request = styleForumServiceHelper.invokeStyleForumService_FeatureActivity(userName, password, APINAME.UNFEATUREANSWER, AnswerID);
			response = request.respvalidate.returnresponseasstring();
			System.out.println(response);
			AssertJUnit.assertEquals("Feature Question API is not working",200,request.response.getStatus());		
			
			request = styleForumServiceHelper.invokeStyleForumService_GetAnswers(userName, password, QuestionID);
			response = request.respvalidate.returnresponseasstring();
			System.out.println(response);
			AssertJUnit.assertEquals("Feature Answer API is not working", "false",JsonPath.read(response,"$.data.answers[0].isFeatured").toString());
			
			
			request=styleForumServiceHelper.invokeStyleForumService_Delete(userName, password, APINAME.DELETEQUESTION, QuestionID);
			AssertJUnit.assertEquals("Style Forum Tests - TearDown Activity [ERROR] - Delete Question Service is not working!",200,request.response.getStatus());
			
		}
		
		@Test(groups = { "Smoke", "ProdSanity", "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },dataProvider = "CreatePollFlow")
		public void FeatureUnFeaturePoll(String userName, String password, String Question, String Option1, String Option2, String Category)
		{
			//Create Poll
			RequestGenerator request = styleForumServiceHelper.invokeCreateTextPollService(userName, password, Question, Option1, Option2);
			String response = request.respvalidate.returnresponseasstring();
			System.out.println("Create Poll API Response : "+response);
			String PollID = JsonPath.read(response,"$.data.id");
			AssertJUnit.assertEquals("Create Poll API is not working!", 200,request.response.getStatus());
			
			//Feature Poll
			request = styleForumServiceHelper.invokeStyleForumService_FeatureActivity(userName, password, APINAME.FEATUREPOLL, PollID);
			response = request.respvalidate.returnresponseasstring();
			System.out.println("Mark Poll as Featured  API Response : "+response);
			AssertJUnit.assertEquals("Mark Poll as Featured  API is not working", 200,request.response.getStatus());
						
			
			//View Created Poll
			request = styleForumServiceHelper.invokePollService_GetPollDetails(userName, password, PollID);
			response = request.respvalidate.returnresponseasstring();
			System.out.println("View Created Poll Response : "+response);
			AssertJUnit.assertEquals("Get Poll details API is not working!", 200,request.response.getStatus());
			AssertJUnit.assertEquals("Get Poll Details API is not working! [Poll ID is different]",PollID,JsonPath.read(response,"$.data.id"));
			AssertJUnit.assertEquals("Mark as Feature option is not working in Poll", "true",JsonPath.read(response,"$.data.isFeatured").toString());
			
			//UnFeature Poll
			request = styleForumServiceHelper.invokeStyleForumService_FeatureActivity(userName, password, APINAME.UNFEATUREPOLL, PollID);
			response = request.respvalidate.returnresponseasstring();
			System.out.println("Mark Poll as UnFeatured  API Response : "+response);
			AssertJUnit.assertEquals("Mark Poll as UnFeatured  API is not working", 200,request.response.getStatus());
			
			//View Created Poll
			request = styleForumServiceHelper.invokePollService_GetPollDetails(userName, password, PollID);
			response = request.respvalidate.returnresponseasstring();
			System.out.println("View Created Poll Response : "+response);
			AssertJUnit.assertEquals("Get Poll details API is not working!", 200,request.response.getStatus());
			AssertJUnit.assertEquals("Get Poll Details API is not working! [Poll ID is different]",PollID,JsonPath.read(response,"$.data.id"));
			AssertJUnit.assertEquals("Mark as Feature option is not working in Poll", "false",JsonPath.read(response,"$.data.isFeatured").toString());
			
			
			
			//TearDown [Delete Poll]
			request = styleForumServiceHelper.invokeStyleForumService_Delete(userName, password, APINAME.DELETEPOLL, PollID);
			response = request.respvalidate.returnresponseasstring();
			System.out.println("Delete Poll Response : "+response);
			AssertJUnit.assertEquals("TearDown Activity [ERROR] - Delete Poll Service is not working!",200,request.response.getStatus());
			
		}
		
		@Test(groups = { "Smoke", "ProdSanity", "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },dataProvider = "CreatePollFlow")
		public void ProfileCount_For_Poll(String userName, String password, String Question, String Option1, String Option2, String Category)
		{
			RequestGenerator request = devApiServiceHelper.invokeGetProfileCount(userName, password);
			String response = request.respvalidate.returnresponseasstring();
			System.out.println("Get Profile Count Response : "+response);
			AssertJUnit.assertEquals("Get Profile Count API is not working", 200,request.response.getStatus());
			int ForumCount = JsonPath.read(response, "$.data.forum.count");
					
			//Create Poll
			RequestGenerator request1 = styleForumServiceHelper.invokeCreateTextPollService(userName, password, Question, Option1, Option2);
			String response1 = request1.respvalidate.returnresponseasstring();
			System.out.println("Create Poll API Response : "+response1);
			String PollID = JsonPath.read(response1,"$.data.id");
			AssertJUnit.assertEquals("Create Poll API is not working!", 200,request1.response.getStatus());
	
			request = devApiServiceHelper.invokeGetProfileCount(userName, password);
			response = request.respvalidate.returnresponseasstring();
			System.out.println("Get Profile Count Response : "+response);
			AssertJUnit.assertEquals("Get Profile Count API is not working", 200,request.response.getStatus());
			AssertJUnit.assertEquals("Get Profile Count API is not working",ForumCount+1,Integer.parseInt(JsonPath.read(response, "$.data.forum.count")));
		
			//TearDown [Delete Poll]
			request = styleForumServiceHelper.invokeStyleForumService_Delete(userName, password, APINAME.DELETEPOLL, PollID);
			response = request.respvalidate.returnresponseasstring();
			System.out.println("Delete Poll Response : "+response);
			AssertJUnit.assertEquals("TearDown Activity [ERROR] - Delete Poll Service is not working!",200,request.response.getStatus());

			request = devApiServiceHelper.invokeGetProfileCount(userName, password);
			response = request.respvalidate.returnresponseasstring();
			System.out.println("Get Profile Count Response : "+response);
			AssertJUnit.assertEquals("Get Profile Count API is not working", 200,request.response.getStatus());
			AssertJUnit.assertEquals("Get Profile Count API is not working", ForumCount,Integer.parseInt(JsonPath.read(response, "$.data.forum.count")));

		}
	
		@Test(groups = { "Smoke", "ProdSanity", "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },dataProvider = "CreateQuestionFlow_ProfileCount")
		public void ProfileCount_For_Question(String userName, String password, String Question, String Category)
		{
			RequestGenerator request = devApiServiceHelper.invokeGetProfileCount(userName, password);
			String response = request.respvalidate.returnresponseasstring();
			System.out.println("Get Profile Count Response : "+response);
			AssertJUnit.assertEquals("Get Profile Count API is not working", 200,request.response.getStatus());
			int CurrentCount = JsonPath.read(response, "$.data.forum.count");
			//AssertJUnit.assertEquals("Get Profile Count API is not working", 0,JsonPath.read(response, "$.data.forum.count"));
					
			//Post Question
			request = styleForumServiceHelper.invokeStyleForumService_CreateAQuestion(userName, password,Question);
			response = request.respvalidate.returnresponseasstring();
			System.out.println(response);
			String QuestionID = JsonPath.read(response, "$.data.id").toString();
			AssertJUnit.assertEquals("Style Forum - Create new question API is not working.", 200,request.response.getStatus());
			
			
			request = devApiServiceHelper.invokeGetProfileCount(userName, password);
			response = request.respvalidate.returnresponseasstring();
			System.out.println("Get Profile Count Response : "+response);
			AssertJUnit.assertEquals("Get Profile Count API is not working", 200,request.response.getStatus());
			AssertJUnit.assertEquals("Get Profile Count API is not working", CurrentCount+1,Integer.parseInt(JsonPath.read(response, "$.data.forum.count")));
		

			request=styleForumServiceHelper.invokeStyleForumService_Delete(userName, password, APINAME.DELETEQUESTION, QuestionID);
			AssertJUnit.assertEquals("Style Forum Tests - TearDown Activity [ERROR] - Delete Question Service is not working!",200,request.response.getStatus());
		
			
			request = devApiServiceHelper.invokeGetProfileCount(userName, password);
			response = request.respvalidate.returnresponseasstring();
			System.out.println("Get Profile Count Response : "+response);
			AssertJUnit.assertEquals("Get Profile Count API is not working", 200,request.response.getStatus());
			AssertJUnit.assertEquals("Get Profile Count API is not working", CurrentCount,Integer.parseInt(JsonPath.read(response, "$.data.forum.count")));

		}
	
		
		@Test(groups = { "Smoke", "ProdSanity", "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },dataProvider = "UpdatePollFlow")
		public void Poll_DATAVALIDATIONS(String userName, String password, String Question1A, String Option1A, String Option2A, String Category, String Question1B, String Option1B, String Option2B)
		{
			//Create Poll
			RequestGenerator request = styleForumServiceHelper.invokeCreateTextPollService(userName, password, Question1A, Option1A, Option2A);
			String response = request.respvalidate.returnresponseasstring();
			System.out.println("Create Poll API Response : "+response);
			String PollID = JsonPath.read(response,"$.data.id");	AssertJUnit.assertEquals("Create Poll Data Validation Error - Poll Type is Null!", false,JsonPath.read(response,"$.data.pollType").toString().equals(null));
			AssertJUnit.assertEquals("Create Poll Data Validation Error - Poll ID is Null!", false,JsonPath.read(response,"$.data.id").toString().equals(null));
			AssertJUnit.assertEquals("Create Poll Data Validation Error - Time Left is Null!", false,JsonPath.read(response,"$.data.timeLeft").toString().equals(null));
			AssertJUnit.assertEquals("Create Poll Data Validation Error - Comment Count is Null!", false,JsonPath.read(response,"$.data.commentCount").toString().equals(null));
			AssertJUnit.assertEquals("Create Poll Data Validation Error - Option[1] Text is Null!", false,JsonPath.read(response,"$.data.options[0].text").toString().equals(null));
			AssertJUnit.assertEquals("Create Poll Data Validation Error - Option[1] ID is Null!", false,JsonPath.read(response,"$.data.options[0].label").toString().equals(null));
			AssertJUnit.assertEquals("Create Poll Data Validation Error - Option[1] Label is Null!", false,JsonPath.read(response,"$.data.options[0].id").toString().equals(null));
			AssertJUnit.assertEquals("Create Poll Data Validation Error - Option[1] VotesCount is Null!", false,JsonPath.read(response,"$.data.options[0].votes").toString().equals(null));

			AssertJUnit.assertEquals("Create Poll Data Validation Error - Option[2] Text is Null!", false,JsonPath.read(response,"$.data.options[1].text").toString().equals(null));
			AssertJUnit.assertEquals("Create Poll Data Validation Error - Option[2] ID is Null!", false,JsonPath.read(response,"$.data.options[1].label").toString().equals(null));
			AssertJUnit.assertEquals("Create Poll Data Validation Error - Option[2] Label is Null!", false,JsonPath.read(response,"$.data.options[1].id").toString().equals(null));
			AssertJUnit.assertEquals("Create Poll Data Validation Error - Option[2] VotesCount is Null!", false,JsonPath.read(response,"$.data.options[1].votes").toString().equals(null));
			AssertJUnit.assertEquals("Create Poll Data Validation Error - Total VotesCount is Null!", false,JsonPath.read(response,"$.data.totalVotes").toString().equals(null));

			//View Created Poll
			request = styleForumServiceHelper.invokePollService_GetPollDetails(userName, password, PollID);
			response = request.respvalidate.returnresponseasstring();
			System.out.println("View Created Poll Response : "+response);
			AssertJUnit.assertEquals("Get Poll details API is not working!", 200,request.response.getStatus());
			AssertJUnit.assertEquals("Get Poll Details API is not working! [Poll ID is different]",PollID,JsonPath.read(response,"$.data.id"));
			AssertJUnit.assertEquals("Create Poll Data Validation Error - Poll Type is Null!", false,JsonPath.read(response,"$.data.pollType").toString().equals(null));
			AssertJUnit.assertEquals("Create Poll Data Validation Error - Poll ID is Null!", false,JsonPath.read(response,"$.data.id").toString().equals(null));
			AssertJUnit.assertEquals("Create Poll Data Validation Error - Time Left is Null!", false,JsonPath.read(response,"$.data.timeLeft").toString().equals(null));
			AssertJUnit.assertEquals("Create Poll Data Validation Error - Comment Count is Null!", false,JsonPath.read(response,"$.data.commentCount").toString().equals(null));
			AssertJUnit.assertEquals("Create Poll Data Validation Error - Option[1] Text is Null!", false,JsonPath.read(response,"$.data.options[0].text").toString().equals(null));
			AssertJUnit.assertEquals("Create Poll Data Validation Error - Option[1] ID is Null!", false,JsonPath.read(response,"$.data.options[0].label").toString().equals(null));
			AssertJUnit.assertEquals("Create Poll Data Validation Error - Option[1] Label is Null!", false,JsonPath.read(response,"$.data.options[0].id").toString().equals(null));
			AssertJUnit.assertEquals("Create Poll Data Validation Error - Option[1] VotesCount is Null!", false,JsonPath.read(response,"$.data.options[0].votes").toString().equals(null));

			AssertJUnit.assertEquals("Create Poll Data Validation Error - Option[2] Text is Null!", false,JsonPath.read(response,"$.data.options[1].text").toString().equals(null));
			AssertJUnit.assertEquals("Create Poll Data Validation Error - Option[2] ID is Null!", false,JsonPath.read(response,"$.data.options[1].label").toString().equals(null));
			AssertJUnit.assertEquals("Create Poll Data Validation Error - Option[2] Label is Null!", false,JsonPath.read(response,"$.data.options[1].id").toString().equals(null));
			AssertJUnit.assertEquals("Create Poll Data Validation Error - Option[2] VotesCount is Null!", false,JsonPath.read(response,"$.data.options[1].votes").toString().equals(null));
			AssertJUnit.assertEquals("Create Poll Data Validation Error - Total VotesCount is Null!", false,JsonPath.read(response,"$.data.totalVotes").toString().equals(null));

				
			
			//Update Created Poll
			request = styleForumServiceHelper.invokePollService_UpdatePoll(userName, password, PollID, Question1B, Option1B, Option2B, Category);
			response = request.respvalidate.returnresponseasstring();
			System.out.println("Update Category Poll Response : "+response);
			AssertJUnit.assertEquals("Get Poll details API is not working!", 200,request.response.getStatus());
			AssertJUnit.assertEquals("Get Poll Details API is not working! [Poll ID is different]",PollID,JsonPath.read(response,"$.data.id"));
			AssertJUnit.assertEquals("Stlye Forum - Create Poll API is not working. [Description is not proper]", Question1B,JsonPath.read(response, "$.data.description[0].value"));
			//AssertJUnit.assertEquals("Style Forum - Create Poll API is not working. [First Option is not proper]", Option1B,JsonPath.read(response, "$.data.options[0].text"));
			//AssertJUnit.assertEquals("Style Forum - Create Poll API is not working. [Second Option is not proper]", Option2B,JsonPath.read(response, "$.data.options[1].text"));
			AssertJUnit.assertEquals("Create Poll Data Validation Error - Poll Type is Null!", false,JsonPath.read(response,"$.data.pollType").toString().equals(null));
			AssertJUnit.assertEquals("Create Poll Data Validation Error - Poll ID is Null!", false,JsonPath.read(response,"$.data.id").toString().equals(null));
			AssertJUnit.assertEquals("Create Poll Data Validation Error - Time Left is Null!", false,JsonPath.read(response,"$.data.timeLeft").toString().equals(null));
			AssertJUnit.assertEquals("Create Poll Data Validation Error - Comment Count is Null!", false,JsonPath.read(response,"$.data.commentCount").toString().equals(null));

			AssertJUnit.assertEquals("Create Poll Data Validation Error - Topic ID is Null!", false,JsonPath.read(response,"$.data.topics[0].topicId").toString().equals(null));
			AssertJUnit.assertEquals("Create Poll Data Validation Error - Topic Title is Null!", false,JsonPath.read(response,"$.data.topics[0].topicTitle").toString().equals(null));
			
			AssertJUnit.assertEquals("Create Poll Data Validation Error - Option[1] ID is Null!", false,JsonPath.read(response,"$.data.options[0].label").toString().equals(null));
			AssertJUnit.assertEquals("Create Poll Data Validation Error - Option[1] Label is Null!", false,JsonPath.read(response,"$.data.options[0].id").toString().equals(null));
			AssertJUnit.assertEquals("Create Poll Data Validation Error - Option[1] VotesCount is Null!", false,JsonPath.read(response,"$.data.options[0].votes").toString().equals(null));

			AssertJUnit.assertEquals("Create Poll Data Validation Error - Option[2] ID is Null!", false,JsonPath.read(response,"$.data.options[1].label").toString().equals(null));
			AssertJUnit.assertEquals("Create Poll Data Validation Error - Option[2] Label is Null!", false,JsonPath.read(response,"$.data.options[1].id").toString().equals(null));
			AssertJUnit.assertEquals("Create Poll Data Validation Error - Option[2] VotesCount is Null!", false,JsonPath.read(response,"$.data.options[1].votes").toString().equals(null));
			AssertJUnit.assertEquals("Create Poll Data Validation Error - Total VotesCount is Null!", false,JsonPath.read(response,"$.data.totalVotes").toString().equals(null));

			
			//View Created Poll
			request = styleForumServiceHelper.invokePollService_GetPollDetails(userName, password, PollID);
			response = request.respvalidate.returnresponseasstring();
			System.out.println("View Created Poll Response : "+response);
			AssertJUnit.assertEquals("Get Poll details API is not working!", 200,request.response.getStatus());
			AssertJUnit.assertEquals("Get Poll Details API is not working! [Poll ID is different]",PollID,JsonPath.read(response,"$.data.id"));
			AssertJUnit.assertEquals("Create Poll Data Validation Error - Poll Type is Null!", false,JsonPath.read(response,"$.data.pollType").toString().equals(null));
			AssertJUnit.assertEquals("Create Poll Data Validation Error - Poll ID is Null!", false,JsonPath.read(response,"$.data.id").toString().equals(null));
			AssertJUnit.assertEquals("Create Poll Data Validation Error - Time Left is Null!", false,JsonPath.read(response,"$.data.timeLeft").toString().equals(null));
			AssertJUnit.assertEquals("Create Poll Data Validation Error - Comment Count is Null!", false,JsonPath.read(response,"$.data.commentCount").toString().equals(null));
			AssertJUnit.assertEquals("Create Poll Data Validation Error - Option[1] ID is Null!", false,JsonPath.read(response,"$.data.options[0].label").toString().equals(null));
			AssertJUnit.assertEquals("Create Poll Data Validation Error - Option[1] Label is Null!", false,JsonPath.read(response,"$.data.options[0].id").toString().equals(null));
			AssertJUnit.assertEquals("Create Poll Data Validation Error - Option[1] VotesCount is Null!", false,JsonPath.read(response,"$.data.options[0].votes").toString().equals(null));

			AssertJUnit.assertEquals("Create Poll Data Validation Error - Option[2] ID is Null!", false,JsonPath.read(response,"$.data.options[1].label").toString().equals(null));
			AssertJUnit.assertEquals("Create Poll Data Validation Error - Option[2] Label is Null!", false,JsonPath.read(response,"$.data.options[1].id").toString().equals(null));
			AssertJUnit.assertEquals("Create Poll Data Validation Error - Option[2] VotesCount is Null!", false,JsonPath.read(response,"$.data.options[1].votes").toString().equals(null));
			AssertJUnit.assertEquals("Create Poll Data Validation Error - Total VotesCount is Null!", false,JsonPath.read(response,"$.data.totalVotes").toString().equals(null));

			//TearDown [Delete Poll]
			request = styleForumServiceHelper.invokeStyleForumService_Delete(userName, password, APINAME.DELETEPOLL, PollID);
			response = request.respvalidate.returnresponseasstring();
			System.out.println("Delete Poll Response : "+response);
			AssertJUnit.assertEquals("TearDown Activity [ERROR] - Delete Poll Service is not working!",200,request.response.getStatus());
			
		}

		@Test(groups = { "Smoke", "ProdSanity", "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },dataProvider = "CreatePollFlow")
		public void FeatureUnFeaturePoll_DATAVALIDATIONS(String userName, String password, String Question, String Option1, String Option2, String Category)
		{
			//Create Poll
			RequestGenerator request = styleForumServiceHelper.invokeCreateTextPollService(userName, password, Question, Option1, Option2);
			String response = request.respvalidate.returnresponseasstring();
			System.out.println("Create Poll API Response : "+response);
			String PollID = JsonPath.read(response,"$.data.id");
			AssertJUnit.assertEquals("Create Poll API is not working!", 200,request.response.getStatus());

			
			//Feature Poll
			request = styleForumServiceHelper.invokeStyleForumService_FeatureActivity(userName, password, APINAME.FEATUREPOLL, PollID);
			response = request.respvalidate.returnresponseasstring();
			System.out.println("Mark Poll as Featured  API Response : "+response);
			AssertJUnit.assertEquals("Mark Poll as Featured  API is not working", 200,request.response.getStatus());
			AssertJUnit.assertEquals("Mark Poll as Featured  API Data Validation Error - Success Field is null!", false,JsonPath.read(response,"$.data").toString().equals(null));
			AssertJUnit.assertEquals("Mark Poll as Featured  API Data Validation Error - Meta Field is null!", false,JsonPath.read(response,"$.meta.status").toString().equals(null));

			
			//View Created Poll
			request = styleForumServiceHelper.invokePollService_GetPollDetails(userName, password, PollID);
			response = request.respvalidate.returnresponseasstring();
			System.out.println("View Created Poll Response : "+response);
			AssertJUnit.assertEquals("Get Poll details API is not working!", 200,request.response.getStatus());
			AssertJUnit.assertEquals("Get Poll Details API is not working! [Poll ID is different]",PollID,JsonPath.read(response,"$.data.id"));
			AssertJUnit.assertEquals("Mark as Feature option is not working in Poll", "true",JsonPath.read(response,"$.data.isFeatured").toString());
			
			//UnFeature Poll
			request = styleForumServiceHelper.invokeStyleForumService_FeatureActivity(userName, password, APINAME.UNFEATUREPOLL, PollID);
			response = request.respvalidate.returnresponseasstring();
			System.out.println("Mark Poll as UnFeatured  API Response : "+response);
			AssertJUnit.assertEquals("Mark Poll as UnFeatured  API is not working", 200,request.response.getStatus());
			AssertJUnit.assertEquals("Mark Poll as Featured  API Data Validation Error - Success Field is null!", false,JsonPath.read(response,"$.data").toString().equals(null));
			AssertJUnit.assertEquals("Mark Poll as Featured  API Data Validation Error - Meta Field is null!", false,JsonPath.read(response,"$.meta.status").toString().equals(null));

			
			//View Created Poll
			request = styleForumServiceHelper.invokePollService_GetPollDetails(userName, password, PollID);
			response = request.respvalidate.returnresponseasstring();
			System.out.println("View Created Poll Response : "+response);
			AssertJUnit.assertEquals("Get Poll details API is not working!", 200,request.response.getStatus());
			AssertJUnit.assertEquals("Get Poll Details API is not working! [Poll ID is different]",PollID,JsonPath.read(response,"$.data.id"));
			AssertJUnit.assertEquals("Mark as Feature option is not working in Poll", "false",JsonPath.read(response,"$.data.isFeatured").toString());
			
			
			
			//TearDown [Delete Poll]
			request = styleForumServiceHelper.invokeStyleForumService_Delete(userName, password, APINAME.DELETEPOLL, PollID);
			response = request.respvalidate.returnresponseasstring();
			System.out.println("Delete Poll Response : "+response);
			AssertJUnit.assertEquals("TearDown Activity [ERROR] - Delete Poll Service is not working!",200,request.response.getStatus());
			AssertJUnit.assertEquals("Delete Poll API Data Validation Error - Success Field is null!", false,JsonPath.read(response,"$.data").toString().equals(null));
			AssertJUnit.assertEquals("Delete Poll API Data Validation Error - Meta Field is null!", false,JsonPath.read(response,"$.meta.status").toString().equals(null));

		}
		
		@Test(groups = {"Smoke", "ProdSanity", "Sanity", "MiniRegression", "ExhaustiveRegression"},dataProvider = "UpdateAnswerFlow")
		public void Questions_DATAVALIDATION(String userName, String password, String Question, String Category, String Answer, String AnswerUpdate)
		{
			//Post Question
			RequestGenerator request = styleForumServiceHelper.invokeStyleForumService_CreateAQuestion(userName, password,Question);
			String response = request.respvalidate.returnresponseasstring();
			System.out.println(response);
			String QuestionID = JsonPath.read(response, "$.data.id").toString();
			AssertJUnit.assertEquals("Style Forum - Create new question API is not working.", 200,request.response.getStatus());
			AssertJUnit.assertEquals("Style Forum Data Validation Error - entity type is null",false,JsonPath.read(response, "$.data.type").toString().equals(null));
			AssertJUnit.assertEquals("Style Forum Data Validation Error - Question ID is null",false,JsonPath.read(response, "$.data.id").toString().equals(null));
			AssertJUnit.assertEquals("Style Forum Data Validation Error - Comment Count is null",false,JsonPath.read(response, "$.data.commentCount").toString().equals(null));
			AssertJUnit.assertEquals("Style Forum Data Validation Error - Answer Count is null",false,JsonPath.read(response, "$.data.answerCount").toString().equals(null));
			AssertJUnit.assertEquals("Style Forum Data Validation Error - is Featured Field is null",false,JsonPath.read(response, "$.data.isFeatured").toString().equals(null));
			AssertJUnit.assertEquals("Style Forum Data Validation Error - is Spammed Field is null",false,JsonPath.read(response, "$.data.isSpammed").toString().equals(null));
			AssertJUnit.assertEquals("Style Forum Data Validation Error - isLiked Field ID is null",false,JsonPath.read(response, "$.data.isLiked").toString().equals(null));
			AssertJUnit.assertEquals("Style Forum Data Validation Error - isFollowed Field is null",false,JsonPath.read(response, "$.data.isFollowed").toString().equals(null));
			AssertJUnit.assertEquals("Style Forum Data Validation Error - has Answered Field is null",false,JsonPath.read(response, "$.data.hasAnswered").toString().equals(null));
			AssertJUnit.assertEquals("Style Forum Data Validation Error - Followers Count is null",false,JsonPath.read(response, "$.data.followersCount").toString().equals(null));
			AssertJUnit.assertEquals("Style Forum Data Validation Error - Question Type is null",false,JsonPath.read(response, "$.data.description[0].type").toString().equals(null));
			AssertJUnit.assertEquals("Style Forum Data Validation Error - Question Description is null",false,JsonPath.read(response, "$.data.description[0].value").toString().equals(null));

			//Update Category
			request = styleForumServiceHelper.invokeStyleForumService_updateAQuestion(userName, password, QuestionID, Question, Category);
			response = request.respvalidate.returnresponseasstring();
			System.out.println(response);
			QuestionID = JsonPath.read(response, "$.data.id").toString();
			AssertJUnit.assertEquals("Style Forum - Update Question API is not working.", 200,request.response.getStatus());
			AssertJUnit.assertEquals("Style Forum Data Validation Error - entity type is null",false,JsonPath.read(response, "$.data.type").toString().equals(null));
			AssertJUnit.assertEquals("Style Forum Data Validation Error - Question ID is null",false,JsonPath.read(response, "$.data.id").toString().equals(null));
			AssertJUnit.assertEquals("Style Forum Data Validation Error - Comment Count is null",false,JsonPath.read(response, "$.data.commentCount").toString().equals(null));
			AssertJUnit.assertEquals("Style Forum Data Validation Error - Answer Count is null",false,JsonPath.read(response, "$.data.answerCount").toString().equals(null));
			AssertJUnit.assertEquals("Style Forum Data Validation Error - is Featured Field is null",false,JsonPath.read(response, "$.data.isFeatured").toString().equals(null));
			AssertJUnit.assertEquals("Style Forum Data Validation Error - is Spammed Field is null",false,JsonPath.read(response, "$.data.isSpammed").toString().equals(null));
			AssertJUnit.assertEquals("Style Forum Data Validation Error - isLiked Field ID is null",false,JsonPath.read(response, "$.data.isLiked").toString().equals(null));
			AssertJUnit.assertEquals("Style Forum Data Validation Error - isFollowed Field is null",false,JsonPath.read(response, "$.data.isFollowed").toString().equals(null));
			AssertJUnit.assertEquals("Style Forum Data Validation Error - has Answered Field is null",false,JsonPath.read(response, "$.data.hasAnswered").toString().equals(null));
			AssertJUnit.assertEquals("Style Forum Data Validation Error - Followers Count is null",false,JsonPath.read(response, "$.data.followersCount").toString().equals(null));
			AssertJUnit.assertEquals("Style Forum Data Validation Error - Question Type is null",false,JsonPath.read(response, "$.data.description[0].type").toString().equals(null));
			AssertJUnit.assertEquals("Style Forum Data Validation Error - Question Description is null",false,JsonPath.read(response, "$.data.description[0].value").toString().equals(null));
			AssertJUnit.assertEquals("Style Forum Data Validation Error - Question TopicID is null",false,JsonPath.read(response, "$.data.topics[0].topicId").toString().equals(null));
			AssertJUnit.assertEquals("Style Forum Data Validation Error - Question Topic Description is null",false,JsonPath.read(response, "$.data.topics[0].topicTitle").toString().equals(null));
			AssertJUnit.assertEquals("Style Forum Data Validation Error - Question Topic IS DELETED FIELD is null",false,JsonPath.read(response, "$.data.topics[0].isDeleted").toString().equals(null));

			//Answer a Question
			request = styleForumServiceHelper.invokeStyleForumService_answerAQuestion(userName, password, QuestionID, Answer);
			response = request.respvalidate.returnresponseasstring();
			System.out.println(response);
			String AnswerID= JsonPath.read(response, "$.data.id").toString();
			AssertJUnit.assertEquals("Answer a Question API is not working!",200,request.response.getStatus());
			AssertJUnit.assertEquals("Answer a Question API is not working!-[Answer is not added]",Answer,JsonPath.read(response, "$.data.description[0].value"));
			AssertJUnit.assertEquals("Style Forum Data Validation Error - Answer Type is null",false,JsonPath.read(response, "$.data.type").toString().equals(null));
			AssertJUnit.assertEquals("Style Forum Data Validation Error - Answer ID is null",false,JsonPath.read(response, "$.data.id").toString().equals(null));
			AssertJUnit.assertEquals("Style Forum Data Validation Error - Comment Count is null",false,JsonPath.read(response, "$.data.commentCount").toString().equals(null));
			AssertJUnit.assertEquals("Style Forum Data Validation Error - ISFEATURED FIELD is null",false,JsonPath.read(response, "$.data.isFeatured").toString().equals(null));
			AssertJUnit.assertEquals("Style Forum Data Validation Error - ISSPAMMED FIELD is null",false,JsonPath.read(response, "$.data.isSpammed").toString().equals(null));
			AssertJUnit.assertEquals("Style Forum Data Validation Error - ISOWNER FIELD is null",false,JsonPath.read(response, "$.data.isOwner").toString().equals(null));
			AssertJUnit.assertEquals("Style Forum Data Validation Error - ISLIKED FIELD is null",false,JsonPath.read(response, "$.data.isLiked").toString().equals(null));
			AssertJUnit.assertEquals("Style Forum Data Validation Error - UPVOTES FIELD is null",false,JsonPath.read(response, "$.data.upVotes").toString().equals(null));
			AssertJUnit.assertEquals("Style Forum Data Validation Error - ISANONYMOUS FIELD is null",false,JsonPath.read(response, "$.data.isAnonymous").toString().equals(null));
			AssertJUnit.assertEquals("Style Forum Data Validation Error - Answer Type is null",false,JsonPath.read(response, "$.data.description[0].type").toString().equals(null));
			AssertJUnit.assertEquals("Style Forum Data Validation Error - Answer Description is null",false,JsonPath.read(response, "$.data.description[0].value").toString().equals(null));
			
			//Update the Answer
			request = styleForumServiceHelper.invokeStyleForumService_updateAnAnswer(userName, password, QuestionID, AnswerID, AnswerUpdate);
			response = request.respvalidate.returnresponseasstring();
			System.out.println(response);
			AssertJUnit.assertEquals("Update an Answer API is not working", 200, request.response.getStatus());
			AssertJUnit.assertEquals("Update an Answer API is not working",AnswerUpdate,JsonPath.read(response, "$.data.description[0].value"));
			AssertJUnit.assertEquals("Style Forum Data Validation Error - Answer Type is null",false,JsonPath.read(response, "$.data.type").toString().equals(null));
			AssertJUnit.assertEquals("Style Forum Data Validation Error - Answer ID is null",false,JsonPath.read(response, "$.data.id").toString().equals(null));
			AssertJUnit.assertEquals("Style Forum Data Validation Error - Comment Count is null",false,JsonPath.read(response, "$.data.commentCount").toString().equals(null));
			AssertJUnit.assertEquals("Style Forum Data Validation Error - ISFEATURED FIELD is null",false,JsonPath.read(response, "$.data.isFeatured").toString().equals(null));
			AssertJUnit.assertEquals("Style Forum Data Validation Error - ISSPAMMED FIELD is null",false,JsonPath.read(response, "$.data.isSpammed").toString().equals(null));
			AssertJUnit.assertEquals("Style Forum Data Validation Error - ISOWNER FIELD is null",false,JsonPath.read(response, "$.data.isOwner").toString().equals(null));
			AssertJUnit.assertEquals("Style Forum Data Validation Error - ISLIKED FIELD is null",false,JsonPath.read(response, "$.data.isLiked").toString().equals(null));
			AssertJUnit.assertEquals("Style Forum Data Validation Error - UPVOTES FIELD is null",false,JsonPath.read(response, "$.data.upVotes").toString().equals(null));
			AssertJUnit.assertEquals("Style Forum Data Validation Error - ISANONYMOUS FIELD is null",false,JsonPath.read(response, "$.data.isAnonymous").toString().equals(null));
			AssertJUnit.assertEquals("Style Forum Data Validation Error - Answer Type is null",false,JsonPath.read(response, "$.data.description[0].type").toString().equals(null));
			AssertJUnit.assertEquals("Style Forum Data Validation Error - Answer Description is null",false,JsonPath.read(response, "$.data.description[0].value").toString().equals(null));
			
			//Tear Down Activity
			request=styleForumServiceHelper.invokeStyleForumService_Delete(userName, password, APINAME.DELETEQUESTION, QuestionID);
			AssertJUnit.assertEquals("Style Forum Tests - TearDown Activity [ERROR] - Delete Question Service is not working!",200,request.response.getStatus());
			
		}
}