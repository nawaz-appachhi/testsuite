package com.myntra.apiTests.portalservices.StyleForumHelper;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.MultivaluedMap;

import com.myntra.apiTests.portalservices.AppReferral.AppReferralServiceHelper;
import com.myntra.apiTests.portalservices.commons.CommonUtils;
import com.myntra.apiTests.portalservices.devapiservice.DevApiServiceHelper;
import org.apache.log4j.Logger;

import com.jayway.jsonpath.JsonPath;

import java.util.Random;
import com.myntra.lordoftherings.Initialize;
import com.myntra.apiTests.common.APINAME;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.apiTests.common.ServiceType;
import com.myntra.apiTests.common.Myntra;

public class StyleForumServiceHelper extends CommonUtils
{

	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(AppReferralServiceHelper.class);
	static DevApiServiceHelper devApiServiceHelper = new DevApiServiceHelper();
	static String xId, sXid, uidx, xsrf, xmyntraapp,ppid;
	APIUtilities apiUtil=new APIUtilities();

	private String randomize(String Data)
	{
		String RandomizedData = Data;
		
		int RangeStart = 1000;
		int RangeEnd = 9999;
		Random obj = new Random();
		RandomizedData = RandomizedData+((obj.nextInt(RangeEnd - RangeStart)+RangeStart));
		
		return RandomizedData;	
	}
	
	public void createXMyntraAppHeader(String deviceId, String InstallationId, String CustomerID)
	{
		String DeviceID = randomize(deviceId);
		String InstallationID = randomize(InstallationId);
		xmyntraapp = "appFamily=MyntraRetailAndroid; appVersion=3.1.0-Fox; appBuild=110103; deviceCategory=Phone; osVersion=4.4.4; sdkVersion=19; deviceID="+DeviceID+"; installationID="+InstallationID+"; sessionID=3e86975d-350c-40ba-bae4-071cff6471dd-54ffe1a7dc0547be; customerID="+CustomerID+";";
	}
		
	private static void printAndLog(String Message, String Param)
	{
		System.out.println(Message+Param);
		log.info(Message+Param);
	}
	
	public void getAndSetTokensWithPPID(String userName, String password)
	{
		System.out.println("\nUsername : "+userName+"\nPassword : "+password);
		MyntraService signInService = Myntra.getService(ServiceType.PORTAL_DEVAPISHTTPS, APINAME.DEVAPISIGNIN, init.Configurations, new String[] { userName, password });
		System.out.println("\nSign In Service URL : "+signInService.URL);
		HashMap<String, String> headers = new HashMap<String,String>();
		headers.put("Content-Type", "application/json");
		RequestGenerator signInRequest = new RequestGenerator(signInService,headers);
		
		String Response = signInRequest.respvalidate.returnresponseasstring();
		System.out.println("\nSign In Response : "+Response);
		uidx = JsonPath.read(Response, "$.data.uidx").toString();
		xId = JsonPath.read(Response, "$.meta.token").toString();
		ppid= JsonPath.read(Response, "$.data.profile.publicProfileId");
		
		System.out.println("\nXID : "+xId);
		System.out.println("\nUIDX : "+uidx);
		System.out.println("\nPPID : "+ppid);
	}
	
	
	private void tamperXID()
	{
		xId = xId.concat("qwerty");
		printAndLog("\nTampered XID : ",xId);
	}
	
	private void getAndSetTokens(String userName, String password)
	{
		System.out.println("\nUsername : "+userName+"\nPassword : "+password);
		MyntraService signInService = Myntra.getService(ServiceType.PORTAL_IDP, APINAME.SIGNIN, init.Configurations, new String[] { userName, password });
		printAndLog("\nSign In Service URL : ",signInService.URL);
		
		RequestGenerator signInRequest = new RequestGenerator(signInService);
		MultivaluedMap<String, Object> map = signInRequest.response.getHeaders();
		for (Map.Entry entry : map.entrySet())
		{
			if (entry.getKey().toString().equalsIgnoreCase("xid"))
			{
				xId = entry.getValue().toString();
			}
		}
		String Response = signInRequest.respvalidate.returnresponseasstring();
		printAndLog("\nSign In Response : ",Response);
		uidx = JsonPath.read(Response, "$.user.uidx");
		xsrf = signInRequest.respvalidate.GetNodeTextUsingIndex("xsrfToken");
		xId = xId.substring((xId.indexOf("[") + 1), xId.lastIndexOf("]"));
		sXid = signInRequest.respvalidate.GetNodeTextUsingIndex("xsrfToken");
		if (sXid.contains("'"))
		{
			sXid = sXid.substring(sXid.indexOf("'") + 1, sXid.lastIndexOf("'"));
		}
		else
		{
			sXid = sXid.substring(sXid.indexOf("[") + 1, sXid.lastIndexOf("]"));
		}
		printAndLog("\nXID : ",xId);
		//printAndLog("\nSXID : ",sXid);
		//printAndLog("\nUIDX : ",uidx);
		//printAndLog("\nXSRF : ",xsrf);
	}
	
	
	
	//------------------------- New Changes --------------//
	public RequestGenerator getUserFeeds(String username, String password)
	{
		getAndSetTokens(username, password);
		MyntraService service = Myntra.getService(ServiceType.PORTAL_DEVAPISTYLEFORUM, APINAME.GETUSERFEEDSWITHUIDX,init.Configurations);
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("xid", xId);
		service.URL = apiUtil.prepareparameterizedURL(service.URL, new String[] {uidx});
		printAndLog("Get User Feeds URL : ",service.URL);
		return new RequestGenerator(service,headers);
	
	}
	
	//Feed Helper Service
	public RequestGenerator invokeFeedService(String userName, String password, String topicId, String filter)
	{
		getAndSetTokens(userName, password);
		MyntraService StyleForum_MyProfileService = Myntra.getService(ServiceType.PORTAL_DEVAPISTYLEFORUM, APINAME.GETFEEDS, init.Configurations);
		HashMap<String, String> StyleForum_MyProfileServiceHeaders = new HashMap<String, String>();
		StyleForum_MyProfileServiceHeaders.put("xid",xId);
		StyleForum_MyProfileService.URL = apiUtil.prepareparameterizedURL(StyleForum_MyProfileService.URL, new String[] {topicId,filter});
		printAndLog("Style Forum - Get Feeds Service URL : ",StyleForum_MyProfileService.URL);
		return new RequestGenerator(StyleForum_MyProfileService,StyleForum_MyProfileServiceHeaders);
		
	}
	
	//Scroll Feeds
	public RequestGenerator invokeFeedScroll(String userName, String password, String initialId, String topicId, String filter)
	{
		getAndSetTokens(userName, password);
		MyntraService StyleForum_MyProfileService = Myntra.getService(ServiceType.PORTAL_DEVAPISTYLEFORUM, APINAME.SCROLLFEEDSDOWN, init.Configurations);
		HashMap<String, String> StyleForum_MyProfileServiceHeaders = new HashMap<String, String>();
		StyleForum_MyProfileServiceHeaders.put("xid",xId);
		StyleForum_MyProfileService.URL = apiUtil.prepareparameterizedURL(StyleForum_MyProfileService.URL, new String[] {initialId,filter});
		printAndLog("Style Forum - Scroll Down Feeds API URL : ",StyleForum_MyProfileService.URL);
		return new RequestGenerator(StyleForum_MyProfileService,StyleForum_MyProfileServiceHeaders);
	
	}
	
	//Poll Helper Service
	public RequestGenerator invokeCreateTextPollService(String userName, String password, String Question, String Option1, String Option2)
	{
		getAndSetTokens(userName, password);
		MyntraService StyleForum_PollService = Myntra.getService(ServiceType.PORTAL_DEVAPISTYLEFORUM, APINAME.CREATETEXTPOLL, init.Configurations, new String[] {Question, Option1, Option2});
		createXMyntraAppHeader("hfgdjfgf", "0599-cba5-3747-a47a-64fadc1e", uidx);
		HashMap<String, String> StyleForum_PollServiceHeaders = new HashMap<String, String>();
		StyleForum_PollServiceHeaders.put("xid", xId);
		StyleForum_PollServiceHeaders.put("X-MYNTRA-APP", xmyntraapp);
		
		printAndLog("Style Forum - Poll Service URL : ",StyleForum_PollService.URL);	
		printAndLog("Style Forum - Poll Service Payload : ",StyleForum_PollService.Payload);
		System.out.println("Style Forum - Poll Service Headers : "+StyleForum_PollServiceHeaders);
		return new RequestGenerator(StyleForum_PollService, StyleForum_PollServiceHeaders);		
	}
		
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//UserProfile Helper Service
	public RequestGenerator invokeUserProfileService(String userName, String password, String testType)
	{
		getAndSetTokens(userName, password);
		if (testType =="N") tamperXID();
		
		MyntraService StyleForum_MyProfileService = Myntra.getService(ServiceType.PORTAL_DEVAPISTYLEFORUM, APINAME.GETMYPROFILE, init.Configurations);
		HashMap<String, String> StyleForum_MyProfileServiceHeaders = new HashMap<String, String>();
		StyleForum_MyProfileServiceHeaders.put("xid",xId);
		
		printAndLog("Style Forum - Get My Profile Service URL : \n",StyleForum_MyProfileService.URL);
		return new RequestGenerator(StyleForum_MyProfileService,StyleForum_MyProfileServiceHeaders);
		
	}
	
	
	
	public RequestGenerator invokeScrollService(String userName, String password, String LastCardId,  String topicId)
	{
		getAndSetTokens(userName, password);
		MyntraService StyleForum_MyProfileService = Myntra.getService(ServiceType.PORTAL_DEVAPISTYLEFORUM, APINAME.SCROLLFEEDSDOWN, init.Configurations);
		HashMap<String, String> StyleForum_MyProfileServiceHeaders = new HashMap<String, String>();
		StyleForum_MyProfileServiceHeaders.put("xid",xId);
		StyleForum_MyProfileService.URL = apiUtil.prepareparameterizedURL(StyleForum_MyProfileService.URL, new String[] {LastCardId,topicId});
		printAndLog("Style Forum - Scroll Down Feeds API URL : ",StyleForum_MyProfileService.URL);
		return new RequestGenerator(StyleForum_MyProfileService,StyleForum_MyProfileServiceHeaders);
		
	}
	
	
	
	
	
	
	//Poll Helper Service
	public RequestGenerator invokePollService_CreatePoll(String userName, String password, APINAME apiName, String testType, String Question, String Option1, String Option2)
	{
		getAndSetTokens(userName, password);
		MyntraService StyleForum_PollService = Myntra.getService(ServiceType.PORTAL_DEVAPISTYLEFORUM, apiName, init.Configurations, new String[] {Question,"TEXT",Option1,"TEXT",Option2});
		HashMap<String, String> StyleForum_PollServiceHeaders = new HashMap<String, String>();
		StyleForum_PollServiceHeaders.put("xid", xId);
		
		printAndLog("Style Forum - Poll Service URL : ",StyleForum_PollService.URL);	
		printAndLog("Style Forum - Poll Service Payload : ",StyleForum_PollService.Payload);
		return new RequestGenerator(StyleForum_PollService, StyleForum_PollServiceHeaders);		
	}
	
	
	
	
	
	
	
	public RequestGenerator invokePollService_UpdatePoll(String userName,String password, String PollID, String Question, String Option1, String Option2, String Category)
	{
		getAndSetTokens(userName, password);
		MyntraService StyleForum_PollService = Myntra.getService(ServiceType.PORTAL_DEVAPISTYLEFORUM, APINAME.UPDATEPOLL, init.Configurations, new String[] {Question,"A",Option1,"B",Option2,Category});
		HashMap<String, String> StyleForum_PollServiceHeaders = new HashMap<String, String>();
		StyleForum_PollServiceHeaders.put("xid", xId);
		StyleForum_PollService.URL = apiUtil.prepareparameterizedURL(StyleForum_PollService.URL, PollID);
		
		printAndLog("Style Forum - Update Poll Service URL : ",StyleForum_PollService.URL);	
		printAndLog("Style Forum - Update Poll Service Payload : ",StyleForum_PollService.Payload);
		return new RequestGenerator(StyleForum_PollService, StyleForum_PollServiceHeaders);	
		
	}
	
	public RequestGenerator invokePollService_GetPollDetails(String userName, String password, String PollID)
	{
		getAndSetTokens(userName, password);
		MyntraService StyleForum_PollService = Myntra.getService(ServiceType.PORTAL_DEVAPISTYLEFORUM, APINAME.GETPOLLDETAILS, init.Configurations);
		HashMap<String, String>StyleForum_PollService_Headers= new HashMap<String, String>();
		StyleForum_PollService_Headers.put("xid", xId);
		StyleForum_PollService_Headers.put("xsrf", sXid);
		StyleForum_PollService.URL = apiUtil.prepareparameterizedURL(StyleForum_PollService.URL, PollID);
		   
		printAndLog("Style Forum - Poll Service - Get Poll Details URL : ",StyleForum_PollService.URL);
		return new RequestGenerator(StyleForum_PollService, StyleForum_PollService_Headers);
		
	}
	
	public RequestGenerator invokePollService_CommentOnPoll(String userName, String password, String PollID, String Comment)
	{
		getAndSetTokens(userName, password);
		MyntraService StyleForum_PollService = Myntra.getService(ServiceType.PORTAL_DEVAPISTYLEFORUM, APINAME.COMMENTONPOLL,init.Configurations, new String[] {Comment});
		HashMap<String, String> StyleForum_PollServiceHeaders = new HashMap<String, String>();
		StyleForum_PollServiceHeaders.put("xid", xId);
		StyleForum_PollService.URL = apiUtil.prepareparameterizedURL(StyleForum_PollService.URL, PollID);
		
		printAndLog("Style Forum Poll Service - Comment On Poll - URL : ",StyleForum_PollService.URL);
		printAndLog("Style Forum Poll Service - Comment on Poll - Payload : ",StyleForum_PollService.Payload);
		return new RequestGenerator(StyleForum_PollService, StyleForum_PollServiceHeaders);
	}
	
	public RequestGenerator invokePollService_VoteForPoll(String userName, String password, String PollID, String OptionID)
	{
		getAndSetTokens(userName, password);
		MyntraService StyleForum_PollService = Myntra.getService(ServiceType.PORTAL_DEVAPISTYLEFORUM, APINAME.VOTEFORPOLL,init.Configurations);
		HashMap<String, String> StyleForum_PollServiceHeaders = new HashMap<String, String>();
		StyleForum_PollServiceHeaders.put("xid", xId);
		StyleForum_PollService.URL = apiUtil.prepareparameterizedURL(StyleForum_PollService.URL, new String[] {PollID, OptionID});
		
		printAndLog("Style Forum Poll Service - Vote for Poll - URL : ",StyleForum_PollService.URL);
		return new RequestGenerator(StyleForum_PollService, StyleForum_PollServiceHeaders);
		
	}
	
	
	//Style Forum Questions
	public RequestGenerator invokeStyleForumService_CreateAQuestion(String userName, String password, String Question)
	{
		getAndSetTokens(userName, password);
		MyntraService StyleForumService = Myntra.getService(ServiceType.PORTAL_DEVAPISTYLEFORUM, APINAME.CREATEQUESTION,init.Configurations,new String[] {Question});
		HashMap<String, String> StyleForumServiceHeaders = new HashMap<String, String>();
		StyleForumServiceHeaders.put("xid", xId);
		printAndLog("Style Forum - Create a Question - URL : ",StyleForumService.URL);
		printAndLog("Stlye Forum - Create a Question - Payload : ",StyleForumService.Payload);
		return new RequestGenerator(StyleForumService, StyleForumServiceHeaders);
		
	}
	
	
	public RequestGenerator invokeStyleForumService_updateAQuestion(String userName, String password, String QuestionID, String Update, String Category)
	{
		getAndSetTokens(userName, password);
		MyntraService StyleForumService = Myntra.getService(ServiceType.PORTAL_DEVAPISTYLEFORUM, APINAME.UPDATEQUESTION,init.Configurations,new String[] {Update, Category});
		HashMap<String, String> StyleForumServiceHeaders = new HashMap<String, String>();
		StyleForumServiceHeaders.put("xid", xId);
		StyleForumService.URL = apiUtil.prepareparameterizedURL(StyleForumService.URL, QuestionID);
		printAndLog("Style Forum Poll Service - Update a Question - URL : ",StyleForumService.URL);
		printAndLog("Style Forum Poll Service - Update a Question - Payload : ",StyleForumService.Payload);

		return new RequestGenerator(StyleForumService, StyleForumServiceHeaders);
		
	}
	
	public RequestGenerator invokeStyleForumService_answerAQuestion(String userName, String password, String QuestionID, String Answer)
	{
		getAndSetTokens(userName, password);
		MyntraService StyleForumService = Myntra.getService(ServiceType.PORTAL_DEVAPISTYLEFORUM, APINAME.ANSWERAQUESTION,init.Configurations,new String[] {Answer});
		HashMap<String, String> StyleForumServiceHeaders = new HashMap<String, String>();
		StyleForumServiceHeaders.put("xid", xId);
		StyleForumService.URL = apiUtil.prepareparameterizedURL(StyleForumService.URL, QuestionID);
		printAndLog("Style Forum Poll Service - Answer a Question - URL : ",StyleForumService.URL);
		printAndLog("Style Forum Poll Service - Answer a Question - Payload : ",StyleForumService.Payload);

		return new RequestGenerator(StyleForumService, StyleForumServiceHeaders);
		
	}
	
	
	public RequestGenerator invokeStyleForumService_updateAnAnswer(String userName, String password, String QuestionID, String AnswerID, String AnswerUpdate)
	{
		getAndSetTokens(userName, password);
		MyntraService StyleForumService = Myntra.getService(ServiceType.PORTAL_DEVAPISTYLEFORUM, APINAME.UPDATEANSWER,init.Configurations,new String[] {AnswerUpdate});
		HashMap<String, String> StyleForumServiceHeaders = new HashMap<String, String>();
		StyleForumServiceHeaders.put("xid", xId);
		StyleForumService.URL = apiUtil.prepareparameterizedURL(StyleForumService.URL, new String[] {QuestionID, AnswerID});
		printAndLog("Style Forum Poll Service - Update an Answer - URL : ",StyleForumService.URL);
		printAndLog("Style Forum Poll Service - Update an Answer - Payload : ",StyleForumService.Payload);

		return new RequestGenerator(StyleForumService, StyleForumServiceHeaders);
		
	}
	
	public RequestGenerator invokeStyleForumService_GetAnswers(String userName, String password, String QuestionID)
	{
		getAndSetTokens(userName, password);
		MyntraService StyleForumService = Myntra.getService(ServiceType.PORTAL_DEVAPISTYLEFORUM, APINAME.GETANSWERSFORQUESTION,init.Configurations);
		HashMap<String, String> StyleForumServiceHeaders = new HashMap<String, String>();
		StyleForumServiceHeaders.put("xid", xId);
		StyleForumService.URL = apiUtil.prepareparameterizedURL(StyleForumService.URL, QuestionID);
		printAndLog("Style Forum Poll Service - Get All answers for a Question - URL : ",StyleForumService.URL);
		return new RequestGenerator(StyleForumService, StyleForumServiceHeaders);
		
		
		
	}
	
	public RequestGenerator invokeStyleForumService_MarkAsSpam(String userName, String password, String QuestionID)
	{
		getAndSetTokens(userName, password);
		MyntraService StyleForumService = Myntra.getService(ServiceType.PORTAL_DEVAPISTYLEFORUM, APINAME.MARKQUESTIONASSPAM,init.Configurations);
		HashMap<String, String> StyleForumServiceHeaders = new HashMap<String, String>();
		StyleForumServiceHeaders.put("xid", xId);
		StyleForumService.URL = apiUtil.prepareparameterizedURL(StyleForumService.URL, QuestionID);
		printAndLog("Style Forum Poll Service - Mark Question as Spam - URL : ",StyleForumService.URL);
		return new RequestGenerator(StyleForumService, StyleForumServiceHeaders);
	}
	
	public RequestGenerator invokeStyleForumService_UnMarkAsSpam(String userName, String password, String QuestionID)
	{
		getAndSetTokens(userName, password);
		MyntraService StyleForumService = Myntra.getService(ServiceType.PORTAL_DEVAPISTYLEFORUM, APINAME.UNMARKQUESTIONASSPAM,init.Configurations);
		HashMap<String, String> StyleForumServiceHeaders = new HashMap<String, String>();
		StyleForumServiceHeaders.put("xid", xId);
		StyleForumService.URL = apiUtil.prepareparameterizedURL(StyleForumService.URL, QuestionID);
		printAndLog("Style Forum Poll Service - Unmark Question as Spam API - URL : ",StyleForumService.URL);
		return new RequestGenerator(StyleForumService, StyleForumServiceHeaders);
	}
	
	public RequestGenerator invokeStyleForumService_GetPollComments(String userName, String password, String PollId, String displayLimit)
	{
		
		getAndSetTokens(userName, password);
		MyntraService StyleForumService = Myntra.getService(ServiceType.PORTAL_DEVAPISTYLEFORUM, APINAME.GETPOLLCOMMENTS,init.Configurations);
		HashMap<String, String> StyleForumServiceHeaders = new HashMap<String, String>();
		StyleForumServiceHeaders.put("xid", xId);
		StyleForumService.URL = apiUtil.prepareparameterizedURL(StyleForumService.URL, new String[] {PollId,displayLimit});
		printAndLog("Style Forum Poll Service - Get All Comments of a Poll - URL : ",StyleForumService.URL);
		return new RequestGenerator(StyleForumService, StyleForumServiceHeaders);		
	}
	
	public RequestGenerator invokeStyleForumService_SpamAction(String userName, String password, APINAME apiName,String ID, String CommentId, String displayLimit)
	{
		getAndSetTokens(userName, password);
		MyntraService StyleForumService = Myntra.getService(ServiceType.PORTAL_DEVAPISTYLEFORUM, apiName ,init.Configurations);
		HashMap<String, String> StyleForumServiceHeaders = new HashMap<String, String>();
		StyleForumServiceHeaders.put("xid", xId);
		StyleForumService.URL = apiUtil.prepareparameterizedURL(StyleForumService.URL, ID);
		printAndLog("Style Forum Poll Service - Spam/UnSpam Activity - URL : ",StyleForumService.URL);
		return new RequestGenerator(StyleForumService, StyleForumServiceHeaders);
		
	}
	
	public RequestGenerator invokeStyleForumService_Comment(String userName, String password, APINAME apiName, String Id, String Comment)
	{
		getAndSetTokens(userName, password);
		MyntraService StyleForumService = Myntra.getService(ServiceType.PORTAL_DEVAPISTYLEFORUM, apiName ,init.Configurations, new String[] {Comment});
		HashMap<String, String> StyleForumServiceHeaders = new HashMap<String, String>();
		StyleForumServiceHeaders.put("xid", xId);
		StyleForumService.URL = apiUtil.prepareparameterizedURL(StyleForumService.URL, Id);
		printAndLog("Style Forum Comment API - Payload", StyleForumService.Payload);
		printAndLog("Style Forum Comment API - URL : ",StyleForumService.URL);
		return new RequestGenerator(StyleForumService, StyleForumServiceHeaders);
	}
	
	public RequestGenerator invokeStyleForumService_CommentSpamUnSpam(String userName, String password, APINAME apiName, String Id)
	{
		getAndSetTokens(userName, password);
		MyntraService StyleForumService = Myntra.getService(ServiceType.PORTAL_DEVAPISTYLEFORUM, apiName ,init.Configurations);
		HashMap<String, String> StyleForumServiceHeaders = new HashMap<String, String>();
		StyleForumServiceHeaders.put("xid", xId);
		StyleForumService.URL = apiUtil.prepareparameterizedURL(StyleForumService.URL, Id);
		printAndLog("Style Forum - Spam/UnSpam Activity - URL : ",StyleForumService.URL);
		return new RequestGenerator(StyleForumService, StyleForumServiceHeaders);
	}
	
	public RequestGenerator invokeStyleForumService_getComments(String userName, String password, APINAME apiName, String Id)
	{
		getAndSetTokens(userName, password);
		MyntraService StyleForumService = Myntra.getService(ServiceType.PORTAL_DEVAPISTYLEFORUM, apiName ,init.Configurations);
		HashMap<String, String> StyleForumServiceHeaders = new HashMap<String, String>();
		StyleForumServiceHeaders.put("xid", xId);
		StyleForumService.URL = apiUtil.prepareparameterizedURL(StyleForumService.URL, Id);
		printAndLog("Style Forum Poll Service - Get All Comments - URL : ",StyleForumService.URL);
		return new RequestGenerator(StyleForumService, StyleForumServiceHeaders);
	}
	
	public RequestGenerator invokeStyleForumService_LikeActivity(String userName, String password, APINAME apiName, String Id)
	{
		getAndSetTokens(userName, password);
		MyntraService StyleForumService = Myntra.getService(ServiceType.PORTAL_DEVAPISTYLEFORUM, apiName ,init.Configurations);
		HashMap<String, String> StyleForumServiceHeaders = new HashMap<String, String>();
		StyleForumServiceHeaders.put("xid", xId);
		StyleForumService.URL = apiUtil.prepareparameterizedURL(StyleForumService.URL, Id);
		printAndLog("Style Forum- Like/UnLike Activity - URL : ",StyleForumService.URL);
		return new RequestGenerator(StyleForumService, StyleForumServiceHeaders);
	}
	
	public RequestGenerator invokeStyleForumService_FollowActivity(String userName, String password, APINAME apiName, String Id)
	{
		getAndSetTokens(userName, password);
		MyntraService StyleForumService = Myntra.getService(ServiceType.PORTAL_DEVAPISTYLEFORUM, apiName ,init.Configurations);
		HashMap<String, String> StyleForumServiceHeaders = new HashMap<String, String>();
		StyleForumServiceHeaders.put("xid", xId);
		StyleForumService.URL = apiUtil.prepareparameterizedURL(StyleForumService.URL, Id);
		printAndLog("Style Forum Service - Follow/UnFollow Activity - URL : ",StyleForumService.URL);
		return new RequestGenerator(StyleForumService, StyleForumServiceHeaders);
	}

	
	//Service Helpers to manage Delete/Spam/Like activities
	
	public RequestGenerator invokeStyleForumService_Delete(String userName, String password, APINAME apiName, String ID)
	{
		getAndSetTokens(userName, password);
		MyntraService service = Myntra.getService(ServiceType.PORTAL_DEVAPISTYLEFORUM, apiName,init.Configurations);
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("xid", xId);
		headers.put("xsrf", xsrf);
		service.URL=apiUtil.prepareparameterizedURL(service.URL, ID);
		printAndLog("Style Forum Delete Action URL : ", service.URL);
		return new RequestGenerator(service,headers);
	}
	
	//Feature/UnFeature Activity
	
	public RequestGenerator invokeStyleForumService_FeatureActivity(String userName, String password, APINAME apiName, String ID)
	{
		getAndSetTokens(userName, password);
		MyntraService service = Myntra.getService(ServiceType.PORTAL_DEVAPISTYLEFORUM, apiName,init.Configurations);
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("xid", xId);
		headers.put("xsrf", xsrf);
		service.URL=apiUtil.prepareparameterizedURL(service.URL, ID);
		printAndLog("Style Feature Action URL : ", service.URL);
		return new RequestGenerator(service,headers);
	}
	
	public RequestGenerator invokeStyleForumService_GetUserFeeds(String userName, String password)
	{
		getAndSetTokens(userName, password);
		MyntraService service = Myntra.getService(ServiceType.PORTAL_DEVAPISTYLEFORUM, APINAME.GETUSERFEEDS,init.Configurations);
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("xid", xId);
		headers.put("xsrf", xsrf);
		printAndLog("Style Feature Action URL : ", service.URL);
		return new RequestGenerator(service,headers);
	}
	
	public RequestGenerator invokeStyleForumService_GetPersonalizedData(String userName, String password, String PostId, String PostType)
	{
		getAndSetTokens(userName, password);
		MyntraService service = Myntra.getService(ServiceType.PORTAL_DEVAPISTYLEFORUM, APINAME.GETPERSONALIZEDDATA,init.Configurations,new String[] {PostId, PostType});
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("xid", xId);
		headers.put("xsrf", xsrf);
		printAndLog("Get Personalized Data URL : ", service.URL);
		printAndLog("Get Personalized Data PAYLOAD : ",service.Payload);
		return new RequestGenerator(service,headers);
	}
	
	
		
	
	
}