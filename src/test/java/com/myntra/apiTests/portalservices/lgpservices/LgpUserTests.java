package com.myntra.apiTests.portalservices.lgpservices;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;

import com.myntra.apiTests.dataproviders.LgpServDP;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jayway.jsonpath.JsonPath;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.Toolbox;
import com.myntra.apiTests.common.APINAME;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.apiTests.common.ServiceType;
import com.myntra.apiTests.common.Myntra;

public class LgpUserTests extends LgpServDP {
	
	public static Initialize init = new Initialize("/Data/configuration");
	String env = init.Configurations.GetTestEnvironemnt().toString();
	static Logger log = Logger.getLogger(LgpUserTests.class);
	
	
	static LgpServicesHelper lgpServiceHelper=new LgpServicesHelper();
	static APIUtilities apiUtil = new APIUtilities();
	
	HashMap<String, String> headers = new HashMap<String,String>();
	HashMap<String, String> instanceHeaders = new HashMap<String,String>();
	
	private String username;
	private String password;
	
	public static String versionSpecification;
	private String globalCaseValue;
	
	@BeforeClass(alwaysRun=true)
	public void headerUpdates(){
		
		username=System.getenv("username");
		password=System.getenv("password");
		
		if(username == null && password == null){
			
			if(env.equalsIgnoreCase("fox7")){
				 
				 username = "iosapp@myntra.com";
				 password = "qwerty";
				 
			 }else if(env.equalsIgnoreCase("production")){
				 
				 username = "iosapp10@myntra.com";
				 password = "qwerty";
				 
			 }
			
		}
			
		boolean signInResult=false;
		try {
			
			signInResult = foxSignInResultProcess(username, password);
			lgpActionsHeaderUpdateHelper(signInResult, username, password);
				
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		if(null==versionSpecification)
		{
			versionSpecification = "v2.6";
		}
		
	}
	
	@AfterMethod 
	protected void endOfTestMethod(Method method) throws Exception { 
		
		String methodName = method.getName();
		System.out.println("End of method -------- "+methodName +" For --> "+ globalCaseValue);
		System.out.println("================================================================");
	}
	

	
	public void lgpActionsHeaderUpdateHelper(boolean foxSignInResult, String user, String pass) throws IOException{
		
		if(foxSignInResult == true){
			
			instanceHeaders = lgpServiceHelper.getXidandUidxForCredential(user, pass);
			String instanceXid = instanceHeaders.get("xid");
			headers.put("xid", instanceXid);
			
			System.out.println("======= Header Data =======");
			for(String key : headers.keySet()){

				System.out.println(key+" = "+headers.get(key));
				
			}
			System.out.println("---------------------------");
			
		}else{
			
			if(env.equalsIgnoreCase("fox7")){
				
				Assert.assertEquals(foxSignInResult, true, "FOX Env sign-in fail");
				
			}else if(env.equalsIgnoreCase("production")){
				
				Assert.assertEquals(foxSignInResult, true, "Production Env sign-in fail");
				
			}
		}
	}
	
	public boolean foxSignInResultProcess(String user, String pass) throws IOException{
		
		String payload=apiUtil.preparepayload(new Toolbox().readFileAsString("./Data/payloads/JSON/devapisignin"), new String[]{user,pass});
		MyntraService signInService = Myntra.getService(ServiceType.PORTAL_DEVAPISHTTPS,APINAME.DEVAPISIGNIN,init.Configurations,payload);
		RequestGenerator reqSignIn = new RequestGenerator(signInService);
		int result = reqSignIn.response.getStatus();
		System.out.println("Fox Sign-In Api URL : "+signInService.URL);
		
		if(result != 200){
			
			System.out.println("Fox Sign-In Response Code : "+result);
			return false;
		
		}
		
		return true;
	}
		
	@Test(groups = { "Sanity" }, dataProvider = "lgpUserPPIDProvider", priority = 1)
	public void lgpGetUserPPIDTest(String ppid,String caseValue) throws IOException
	{
		
		globalCaseValue = caseValue;
		RequestGenerator request = getQueryRequestSingleParam(APINAME.LGPGETPPID, ppid,instanceHeaders);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println(response);
		
		if(caseValue.equalsIgnoreCase("case1")){
			
			JsonParser parser = new JsonParser();
			JsonObject jsonObj = (JsonObject) parser.parse(response);
			String uidx = null;
			if(jsonObj.has("uidx")){
				
				uidx = JsonPath.read(response, "$.uidx");
			
			}
			Assert.assertNotNull(uidx, "UIDX is Null for : "+caseValue);
			Assert.assertTrue(request.response.getStatus() == 200, "LGP lgpGetUserPPIDTest Api Fail for : "+caseValue);
			
		}
		
		else if(caseValue.equalsIgnoreCase("case2")){
			
			Assert.assertTrue(request.response.getStatus() == 404, "LGP lgpGetUserPPIDTest Api Fail for : "+caseValue);
			
		}
	
	}

	@Test(groups = { "Sanity" },dataProvider = "lgpGetUserProfilesDP", priority = 2)
	public void lgpGetUserProfileTest(String wruidx, String caseValue) throws IOException
	{
		
		globalCaseValue = caseValue;
		if(caseValue.equalsIgnoreCase("case1")){
			
			RequestGenerator request = getQueryRequestSingleParam(APINAME.LGPGETPROFILE, wruidx,instanceHeaders);
			String response = request.respvalidate.returnresponseasstring();
			System.out.println(response);
			JsonParser parser = new JsonParser();	
			JsonObject jsonObj = (JsonObject) parser.parse(response);
			
					
				String retrievedUidx=null;
				if(jsonObj.has("uidx")){
							
					retrievedUidx = JsonPath.read(response, "$.uidx");
							
				}
				Assert.assertEquals(request.response.getStatus(), 200, "lgpGetUserProfileTest Status Code Fail for : "+caseValue);
				Assert.assertEquals(retrievedUidx, wruidx, "lgpGetUserProfileTest UIDX mis-match for : "+caseValue);
				
		}
		
		else if(caseValue.equalsIgnoreCase("case2")){
					
			RequestGenerator request = getQueryRequestSingleParam(APINAME.LGPGETPROFILE, wruidx,instanceHeaders);
			String response = request.respvalidate.returnresponseasstring();
			System.out.println(response);
			
			Assert.assertEquals(request.response.getStatus(), 400, "lgpGetUserProfileTest Status Code Fail for : "+caseValue);
					
		}
		
		else if(caseValue.equalsIgnoreCase("case3")){
			
			RequestGenerator request = getQueryRequestSingleParam(APINAME.LGPGETPROFILE, wruidx,instanceHeaders);
			String response = request.respvalidate.returnresponseasstring();
			System.out.println(response);
			
			Assert.assertEquals(request.response.getStatus(), 404, "lgpGetUserProfileTest Status Code Fail for : "+caseValue);
					
		}
			
	}
	
	@Test(groups = { "Sanity" }, dataProvider = "lgpGetUserProfilesByPPIDDP", priority = 3)
	public void lgpGetProfileByPPIDTest(String ppid,String caseValue) throws IOException
	{
		globalCaseValue = caseValue;
		RequestGenerator request = getQueryRequestSingleParam(APINAME.LGPGETPROFILEBYPPID, ppid,instanceHeaders);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println(response);
		
		if(caseValue == "case1"){
			
			Assert.assertEquals(request.response.getStatus(), 200, "lgpGetProfileByPPIDTest Status Code Fail");
			
		}else if(caseValue == "case2"){
			
			Assert.assertEquals(request.response.getStatus(), 404, "lgpGetProfileByPPIDTest Status Code Fail");
			
		}
		
	}
	
	@Test(groups = { "Sanity" }, priority = 4)
	public void lgpGetPrivateProfileTest() throws IOException
	{
		
		MyntraService feedService = Myntra.getService(ServiceType.LGP_LGPSERVICE, APINAME.LGPGETPRIVATEPROFILE, init.Configurations);
		
		RequestGenerator request=null;
		if(feedService.BaseURL.contains("lgp.myntra.com")){
			
			request = new RequestGenerator(feedService,instanceHeaders);
			
		}else{
			
			request = new RequestGenerator(feedService,headers);
		}
		
		
		System.out.println(feedService.URL);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println(response);
		
		String userTypeValue = JsonPath.read(response, "$.usertype");
		
		Assert.assertNotNull(userTypeValue, "userTypeValue is Null");
		Assert.assertEquals(request.response.getStatus(), 200, "lgpGetPrivateProfileTest Status Code Fail");
		
	}
	
	@Test(groups = { "Sanity" },dataProvider = "lgpSummaryAndDetailsDP", priority = 5)
	public void lgpUsersSummaryTest(String uidx1, String uidx2, String caseValue) throws IOException
	{
		
		globalCaseValue = caseValue;
		String[] uidxList = {uidx1,uidx2};
		MyntraService feedService = Myntra.getService(ServiceType.LGP_LGPSERVICE, APINAME.LGPUSERSSUMMARY, init.Configurations,uidxList);
		RequestGenerator request=null;
		if(feedService.BaseURL.contains("lgp.myntra.com")){
			
			request = new RequestGenerator(feedService,instanceHeaders);
			
		}else{
			
			request = new RequestGenerator(feedService,headers);
		}
		System.out.println(feedService.URL);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println(response);
		
		if(caseValue.equalsIgnoreCase("case1")||caseValue.equalsIgnoreCase("case2")||
		   caseValue.equalsIgnoreCase("case3")||caseValue.equalsIgnoreCase("case4Spaces")||
		   caseValue.equalsIgnoreCase("case6Special")){
			
			Assert.assertEquals(request.response.getStatus(), 200, "lgpUsersSummaryTest Status Code Fail");
			
		}
		else if(caseValue.equalsIgnoreCase("case5Tabs")){
			
			Assert.assertEquals(request.response.getStatus(), 400, "lgpUsersSummaryTest Status Code Fail");
			
		}
		
	}
	
	@Test(groups = { "Sanity" },dataProvider = "lgpSummaryAndDetailsDP",  priority = 6)
	public void lgpUsersDetailsTest(String uidx1, String uidx2,String caseValue) throws IOException
	{
		
		globalCaseValue = caseValue;
		String[] uidxList = {uidx1,uidx2};
		MyntraService feedService = Myntra.getService(ServiceType.LGP_LGPSERVICE, APINAME.LGPUSERSDETAILS, init.Configurations,uidxList);
		RequestGenerator request=null;
		if(feedService.BaseURL.contains("lgp.myntra.com")){
			
			request = new RequestGenerator(feedService,instanceHeaders);
			
		}else{
			
			request = new RequestGenerator(feedService,headers);
		}
		System.out.println(feedService.URL);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println(response);
		if(caseValue.equalsIgnoreCase("case1")||caseValue.equalsIgnoreCase("case2")||
		   caseValue.equalsIgnoreCase("case3")||caseValue.equalsIgnoreCase("case4Spaces")||
		   caseValue.equalsIgnoreCase("case6Special")){
				
			Assert.assertEquals(request.response.getStatus(), 200, "lgpUsersSummaryTest Status Code Fail");
		
		} 
		else if(caseValue.equalsIgnoreCase("case5Tabs")){
					
			Assert.assertEquals(request.response.getStatus(), 400, "lgpUsersSummaryTest Status Code Fail");
					
		}
	}
	
	@Test(groups = { "Sanity" },  dataProvider = "lgpUserFollowingAndFollowersProvider", priority = 7)
	public void lgpUserFollowingTest(String param,String caseValue) throws IOException
	{
		
		globalCaseValue = caseValue;
		
		MyntraService feedService = Myntra.getService(ServiceType.LGP_LGPSERVICE, APINAME.LGPUSERFOLLOWING, init.Configurations);
		APIUtilities utilities = new APIUtilities();
		feedService.URL = utilities.prepareparameterizedURL(feedService.URL, param);
		RequestGenerator request=null;
		if(feedService.BaseURL.contains("lgp.myntra.com")){
			
			request = new RequestGenerator(feedService,instanceHeaders);
			
		}else{
			
			request = new RequestGenerator(feedService,headers);
		}
		System.out.println(feedService.URL);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println(response);
		
		if(caseValue.equalsIgnoreCase("case1") || caseValue.equalsIgnoreCase("case2")){
			
			Assert.assertEquals(request.response.getStatus(), 200, "lgpUserFollowingTest Status Code Fail");
			
		}
		
		
	}
	
	@Test(groups = { "Sanity" },  dataProvider = "lgpUserFollowingAndFollowersProvider", priority = 8)
	public void lgpUserFollowersTest(String param, String caseValue) throws IOException
	{
		
		globalCaseValue = caseValue;
		MyntraService feedService = Myntra.getService(ServiceType.LGP_LGPSERVICE, APINAME.LGPUSERFOLLOWERS, init.Configurations);
		APIUtilities utilities = new APIUtilities();
		feedService.URL = utilities.prepareparameterizedURL(feedService.URL, param);
		RequestGenerator request=null;
		if(feedService.BaseURL.contains("lgp.myntra.com")){
			
			request = new RequestGenerator(feedService,instanceHeaders);
			
		}else{
			
			request = new RequestGenerator(feedService,headers);
		}
		System.out.println(feedService.URL);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println(response);
		
		if(caseValue.equalsIgnoreCase("case1") || caseValue.equalsIgnoreCase("case2")){
			
			Assert.assertEquals(request.response.getStatus(), 200, "lgpUserFollowersTest Status Code Fail");
			
		}
	}
	
	@Test(groups = { "Sanity" }, priority = 9)
	public void lgpUserInfluencersTest() throws IOException
	{
		
		MyntraService feedService = Myntra.getService(ServiceType.LGP_LGPSERVICE, APINAME.LGPUSERINFLUENCERS, init.Configurations);
		RequestGenerator request=null;
		if(feedService.BaseURL.contains("lgp.myntra.com")){
			
			request = new RequestGenerator(feedService,instanceHeaders);
			
		}else{
			
			request = new RequestGenerator(feedService,headers);
		}
		System.out.println(feedService.URL);
		
		String response = request.respvalidate.returnresponseasstring();
		System.out.println(response);
		
		Assert.assertEquals(request.response.getStatus(), 200, "lgpUserInfluencersTest Status Code Fail");
		
	}
	
	@Test(groups = { "Sanity" },  priority = 10)
	public void lgpUserContactsTest() throws IOException
	{
		
		MyntraService feedService = Myntra.getService(ServiceType.LGP_LGPSERVICE, APINAME.LGPUSERCONTACTS, init.Configurations);
		RequestGenerator request=null;
		if(feedService.BaseURL.contains("lgp.myntra.com")){
			
			request = new RequestGenerator(feedService,instanceHeaders);
			
		}else{
			
			request = new RequestGenerator(feedService,headers);
		}
		System.out.println(feedService.URL);
		
		String response = request.respvalidate.returnresponseasstring();
		System.out.println(response);
		
		Assert.assertEquals(request.response.getStatus(), 200, "lgpUserContactsTest Status Code Fail");
		
	}
	
}






















