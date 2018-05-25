package com.myntra.apiTests.portalservices.lgpservices;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;

import com.myntra.apiTests.common.Myntra;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.myntra.apiTests.dataproviders.LgpServDP;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.Toolbox;
import com.myntra.apiTests.common.APINAME;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.apiTests.common.ServiceType;

public class LgpActionsTest extends LgpServDP {
	
	public static Initialize init = new Initialize("/Data/configuration");
	String env = init.Configurations.GetTestEnvironemnt().toString();
	static Logger log = Logger.getLogger(LgpActionsTest.class);
	static APIUtilities apiUtil = new APIUtilities();
	static LgpServicesHelper lgpServiceHelper=new LgpServicesHelper();
	static APIUtilities utilities = new APIUtilities();
	
	HashMap<String,String> snsMap;
	
	public static String versionSpecification;
	private String username;
	private String password;
	
					
	/**************************************************************************************/
	
	private String globalCaseValue;
	
	HashMap<String, String> headers;
	HashMap<String, String> instanceHeaders;
	HashMap<String, String> headersForSns;
	
	@BeforeClass(alwaysRun=true)
	public void init(){
		
		headers = new HashMap<String,String>();
		headers.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0=");
		headers.put("Accept", "application/json");
		headers.put("Content-Type", "application/json");
		
		headersForSns = new HashMap<String,String>();
		headersForSns.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0=");
		headersForSns.put("Accept", "application/json");
		headersForSns.put("Content-Type", "application/json");
		
		instanceHeaders = new HashMap<String,String>();
		instanceHeaders.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0=");
		instanceHeaders.put("Accept", "application/json");
		instanceHeaders.put("Content-Type", "application/json");
		
		versionSpecification=System.getenv("API_VERSION");
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
		System.out.println("=====================================================================================================");
	}

	
	public void lgpActionsHeaderUpdateHelper(boolean foxSignInResult, String user, String pass) throws IOException{
		
		System.out.println("Dev Api Sign In Result id -> "+foxSignInResult);
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
	
	public void lgpActionDelete(String whom, String whomType, String verb){
		
		String[] actions = {whom,whomType,verb};
		MyntraService feedService = Myntra.getService(ServiceType.LGP_LGPSERVICE, APINAME.LGPACTIONDELETE, init.Configurations,actions);
		
		RequestGenerator requestGen;
		if(feedService.BaseURL.contains("lgp.myntra.com")){
			
			requestGen = new RequestGenerator(feedService,instanceHeaders);
			
		}else{
			
			requestGen = new RequestGenerator(feedService,headers);
			
		}
		
		
		String response = requestGen.respvalidate.returnresponseasstring();
		System.out.println("Action Delete --- ");
		System.out.println("Action Delete Resonse --- "+response);
		System.out.println("Action Delete Status Code --- "+requestGen.response.getStatus());
		System.out.println("------------------");
		Assert.assertTrue(requestGen.response.getStatus() == 200, "LGP Action Api Fail for : ");
		Assert.assertEquals(response, "OK", " LGP Action Response Mis-Match for ");
	}
	
	
	@Test(groups = {"Sanity"}, dataProvider = "lgpActionsDP", priority = 1)
	public void lgpActionTest(String whom, String whomType, String verb, String caseValue) throws IOException
	{
		
		globalCaseValue = caseValue;
		String[] actions = {whom,whomType,verb};
		
		MyntraService feedService = Myntra.getService(ServiceType.LGP_LGPSERVICE, APINAME.LGPACTION, init.Configurations,actions);
		
	
		snsMap  = new HashMap<String,String>();
		
		String from = instanceHeaders.get("uidx");
		String to = whom;
		
		snsMap.put("from", from);
		snsMap.put("to", to);
		snsMap.put("toType", whomType);
		snsMap.put("verb", verb);
		
		if(caseValue.equalsIgnoreCase("case1") ){
			
			/*LgpSupports.snsSupport(snsMap, "initForLikeCase",headersForSns);*/
			
			RequestGenerator requestGenForPositiveCase1 = null;
			
			if(feedService.BaseURL.contains("lgp.myntra.com")){
				
				requestGenForPositiveCase1 = new RequestGenerator(feedService,instanceHeaders);
				
				
			}else {
				
				requestGenForPositiveCase1 = new RequestGenerator(feedService,headers);
				
			}
			
			
			String responseForPositiveCase1 = requestGenForPositiveCase1.respvalidate.returnresponseasstring();
			System.out.println(responseForPositiveCase1);
	
			Assert.assertTrue(requestGenForPositiveCase1.response.getStatus() == 200, "LGP Action Api Fail for : "+caseValue);
			Assert.assertEquals(responseForPositiveCase1, "OK", " LGP Action Response Mis-Match for --> "+caseValue);
			
			/*LgpSupports.snsSupport(snsMap, "postLikeCase",headersForSns);
			lgpActionDelete(whom, whomType, "unlike");
			LgpSupports.snsSupport(snsMap, "postUnLikeCase",headersForSns);*/
		}
		
		else if(caseValue.equalsIgnoreCase("case3") || caseValue.equalsIgnoreCase("case4")
				|| caseValue.equalsIgnoreCase("case6Spaces")|| caseValue.equalsIgnoreCase("case7Special")){
			
			RequestGenerator requestGen = null;
			
			if(feedService.BaseURL.contains("lgp.myntra.com")){
				
				requestGen = new RequestGenerator(feedService,instanceHeaders);
				
			}else {
				
				requestGen = new RequestGenerator(feedService,headers);
				
			}
			
			
			String response = requestGen.respvalidate.returnresponseasstring();
			System.out.println(response);
			System.out.println(requestGen.response.getStatus());
			Assert.assertTrue(requestGen.response.getStatus() == 200, "LGP Action Api Fail for : "+caseValue);
			Assert.assertEquals(response, "OK", " LGP Action Response Mis-Match for --> "+caseValue);
			
		}
		
		
		else if(caseValue.equalsIgnoreCase("case2") || caseValue.equalsIgnoreCase("case5")|| caseValue.equalsIgnoreCase("case8Tabs")){
			
			RequestGenerator requestGen = null;
			
			if(feedService.BaseURL.contains("lgp.myntra.com")){
				
				requestGen = new RequestGenerator(feedService,instanceHeaders);
				
			}else {
				
				requestGen = new RequestGenerator(feedService,headers);
				
			}
		
			
			String response = requestGen.respvalidate.returnresponseasstring();
			System.out.println(response);
			Assert.assertTrue(requestGen.response.getStatus() == 400, "LGP Action Api Fail for: "+caseValue);
		}
		
	}
	
	@Test(groups = {"Sanity"}, dataProvider = "lgpBulkActionsDP", priority = 2)
	public void lgpBulkActionsTest(String whom, String whomType, String verb,String whom2, String whomType2, String verb2, String caseValue) throws IOException
	{
		
		globalCaseValue = caseValue;
		String[] bulkActions = {whom, whomType, verb, whom2, whomType2, verb2};
		
		MyntraService feedService = Myntra.getService(ServiceType.LGP_LGPSERVICE, APINAME.LGPBULKACTIONS, init.Configurations,bulkActions);
		
		RequestGenerator requestGen = null;
		if(feedService.BaseURL.contains("lgp.myntra.com")){
			
			requestGen = new RequestGenerator(feedService,instanceHeaders);
			
		}else{
			
			requestGen = new RequestGenerator(feedService,headers);
			
		}
		
		
		String response = requestGen.respvalidate.returnresponseasstring();
		System.out.println(response);
		
		if(caseValue.equalsIgnoreCase("case1") || caseValue.equalsIgnoreCase("case2") ||
				caseValue.equalsIgnoreCase("case5") || caseValue.equalsIgnoreCase("case6") ||
				caseValue.equalsIgnoreCase("case7") || caseValue.equalsIgnoreCase("case8")){
			
			Assert.assertTrue(requestGen.response.getStatus() == 200, "LGP Bulk Actions Api Fail for : "+caseValue);
			Assert.assertEquals(response, "OK", "LGP Bulk Actions Response Mis-Match");
			
		}else if(caseValue.equalsIgnoreCase("case3") || caseValue.equalsIgnoreCase("case4") ||
				caseValue.equalsIgnoreCase("case9") || caseValue.equalsIgnoreCase("case10")){
			
			Assert.assertTrue(requestGen.response.getStatus() == 400, "LGP Bulk Actions Api Fail for: "+caseValue);
			
		}
		
	}
	
	@Test(groups = {"Sanity"},dataProvider = "lgpFollowAndUnFollowDp",priority = 3)
	public void lgpFollowTest(String uidx1,String uidx2,String caseValue) throws IOException
	{
		
		globalCaseValue = caseValue;
		String[] followList = {uidx1,uidx2};
		
		MyntraService feedService = Myntra.getService(ServiceType.LGP_LGPSERVICE, APINAME.LGPFOLLOW, init.Configurations,followList);
		
		if(caseValue.equalsIgnoreCase("case1")){
			
			/*String from = instanceHeaders.get("uidx");*/
			
			/*for(int followListIndex = 0; followListIndex < followList.length;followListIndex++){
				
				snsMap  = new HashMap<String,String>();
				String to = followList[followListIndex];
				snsMap.put("from", from);
				snsMap.put("to", to);
				snsMap.put("toType", "User");
				snsMap.put("verb", "follow");
				LgpSupports.snsSupport(snsMap, "initForFollowCase",headersForSns);
			}*/
			
			
			RequestGenerator requestGenForPositiveCase1 = null;
			
			if(feedService.BaseURL.contains("lgp.myntra.com")){
				
				requestGenForPositiveCase1 = new RequestGenerator(feedService,instanceHeaders);
				
				
			}else {
				
				requestGenForPositiveCase1 = new RequestGenerator(feedService,headers);
				
			}
			String responseForPositiveCase1 = requestGenForPositiveCase1.respvalidate.returnresponseasstring();
			System.out.println(responseForPositiveCase1);
			

			Assert.assertTrue(requestGenForPositiveCase1.response.getStatus() == 200, "LGP Follow Api Fail for : "+caseValue);
			Assert.assertEquals(responseForPositiveCase1, "OK", "LGP LGP Follow Response Mis-Match");
			
			
			/*for(int followListIndex = 0; followListIndex < followList.length;followListIndex++){
				
				snsMap  = new HashMap<String,String>();
				String to = followList[followListIndex];
				snsMap.put("from", from);
				snsMap.put("to", to);
				snsMap.put("toType", "User");
				snsMap.put("verb", "follow");
				LgpSupports.snsSupport(snsMap, "postFollowCase",headersForSns);
			}*/
			
		}
		
		
		else if(caseValue.equalsIgnoreCase("case2")|| caseValue.equalsIgnoreCase("case4Spaces") ||
				caseValue.equalsIgnoreCase("case6Special")){
			
			RequestGenerator requestGen = null;
			if(feedService.BaseURL.contains("lgp.myntra.com")){
				
				requestGen = new RequestGenerator(feedService,instanceHeaders);
				
			}else{
				
				requestGen = new RequestGenerator(feedService,headers);
				
			}
			String response = requestGen.respvalidate.returnresponseasstring();
			System.out.println(response);
			
			Assert.assertTrue(requestGen.response.getStatus() == 200, "LGP Follow Api Fail for : "+caseValue);
			Assert.assertEquals(response, "OK", "LGP LGP Follow Response Mis-Match");
			
		}else if(caseValue.equalsIgnoreCase("case3") ||caseValue.equalsIgnoreCase("case5Tabs")){
			
			RequestGenerator requestGen = null;
			if(feedService.BaseURL.contains("lgp.myntra.com")){
				
				requestGen = new RequestGenerator(feedService,instanceHeaders);
				
			}else{
				
				requestGen = new RequestGenerator(feedService,headers);
				
			}
			String response = requestGen.respvalidate.returnresponseasstring();
			System.out.println(response);
			
			Assert.assertTrue(requestGen.response.getStatus() == 400, "LGP Follow Api Fail for: "+caseValue);
			
		}
		
	}
	
	@Test(groups = {"Sanity"},dataProvider = "lgpFollowAndUnFollowDp",priority = 4)
	public void lgpUnFollowTest(String uidx1,String uidx2,String caseValue) throws IOException, InterruptedException
	{
		
		globalCaseValue = caseValue;
		String[] unFollowList = {uidx1,uidx2};
		
		MyntraService feedService = Myntra.getService(ServiceType.LGP_LGPSERVICE, APINAME.LGPUNFOLLOW, init.Configurations,unFollowList);
		
		
		if(caseValue.equalsIgnoreCase("case1")){
			
			/*String from = instanceHeaders.get("uidx");*/
			
			/*for(int followListIndex = 0; followListIndex < unFollowList.length;followListIndex++){
				
				snsMap  = new HashMap<String,String>();
				String to = unFollowList[followListIndex];
				snsMap.put("from", from);
				snsMap.put("to", to);
				snsMap.put("toType", "User");
				snsMap.put("verb", "follow");
				LgpSupports.snsSupport(snsMap, "initUnFollowCase",headersForSns);
			}*/
			
			
			RequestGenerator requestGenForPositiveCase1 = null;
			
			if(feedService.BaseURL.contains("lgp.myntra.com")){
				
				requestGenForPositiveCase1 = new RequestGenerator(feedService,instanceHeaders);
				
				
			}else {
				
				requestGenForPositiveCase1 = new RequestGenerator(feedService,headers);
				
			}

			String responseForPositiveCase1 = requestGenForPositiveCase1.respvalidate.returnresponseasstring();
			System.out.println(responseForPositiveCase1);
			

			Assert.assertTrue(requestGenForPositiveCase1.response.getStatus() == 200, "LGP Follow Api Fail for : "+caseValue);
			Assert.assertEquals(responseForPositiveCase1, "OK", "LGP LGP Follow Response Mis-Match");
			
			/*Thread.sleep(2000);
			
			for(int followListIndex = 0; followListIndex < unFollowList.length;followListIndex++){
				
				snsMap  = new HashMap<String,String>();
				String to = unFollowList[followListIndex];
				snsMap.put("from", from);
				snsMap.put("to", to);
				snsMap.put("toType", "User");
				snsMap.put("verb", "follow");
				LgpSupports.snsSupport(snsMap, "postUnFollowCase",headersForSns);
			}*/
			
		}

		
		
		if(caseValue.equalsIgnoreCase("case2")|| caseValue.equalsIgnoreCase("case4Spaces") ||
				caseValue.equalsIgnoreCase("case6Special")){
			
			RequestGenerator requestGen = null;
			if(feedService.BaseURL.contains("lgp.myntra.com")){
				
				requestGen = new RequestGenerator(feedService,instanceHeaders);
				
			}else{
				
				requestGen = new RequestGenerator(feedService,headers);
				
			}
			String response = requestGen.respvalidate.returnresponseasstring();
			System.out.println(response);
			
			Assert.assertTrue(requestGen.response.getStatus() == 200, "LGP Follow Api Fail for : "+caseValue);
			Assert.assertEquals(response, "OK", "LGP LGP Follow Response Mis-Match");
			
		}else if(caseValue.equalsIgnoreCase("case3") ||caseValue.equalsIgnoreCase("case5Tabs")){
			
			RequestGenerator requestGen = null;
			if(feedService.BaseURL.contains("lgp.myntra.com")){
				
				requestGen = new RequestGenerator(feedService,instanceHeaders);
				
			}else{
				
				requestGen = new RequestGenerator(feedService,headers);
				
			}
			String response = requestGen.respvalidate.returnresponseasstring();
			System.out.println(response);
			
			Assert.assertTrue(requestGen.response.getStatus() == 400, "LGP Follow Api Fail for: "+caseValue);
			
		}
		
	}
	
	@Test(groups = {"Sanity"},priority = 5)
	public void lgpFollowAllTest() throws IOException
	{
		
		MyntraService feedService = Myntra.getService(ServiceType.LGP_LGPSERVICE, APINAME.LGPFOLLOWALL, init.Configurations);
		RequestGenerator requestGen = null;
		if(feedService.BaseURL.contains("lgp.myntra.com")){
			
			requestGen = new RequestGenerator(feedService,instanceHeaders);
			
		}else{
			
			requestGen = new RequestGenerator(feedService,headers);
			
		}
		String response = requestGen.respvalidate.returnresponseasstring();
		System.out.println(response);
		
		Assert.assertTrue(requestGen.response.getStatus() == 200, "LGP Follow All Api Fail");
		Assert.assertEquals(response, "OK", "LGP Follow All Response Mis-Match");
	}

}








