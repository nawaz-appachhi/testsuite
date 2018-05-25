package com.myntra.apiTests.portalservices.lgpservices;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.myntra.apiTests.common.Myntra;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.jayway.jsonpath.JsonPath;
import com.myntra.lordoftherings.Initialize;
import com.myntra.apiTests.common.APINAME;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.apiTests.common.ServiceType;

public class TimeLineTests {

	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(StreamFlowTests.class);
	static APIUtilities apiUtil = new APIUtilities();
	static LgpServicesHelper lgpServiceHelper=new LgpServicesHelper();
	static APIUtilities utilities = new APIUtilities();
	String env = init.Configurations.GetTestEnvironemnt().toString();
	
	private String username;
	private String password;
	static String xid;
	public static String versionSpecification;
	public static HashMap<String, String> headers=new HashMap<String, String>();
	
	
	
	@BeforeClass(alwaysRun=true)
	public void signInAndGetXidandUidx()
	{
		
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
		
		try {
			
			headers=lgpServiceHelper.getXidandUidxForCredential(username, password);
			System.out.println("Username : "+username);
			System.out.println("Password : "+password);
			
		} catch (IOException e) {
			
			e.printStackTrace();

		}
		
		if(null==versionSpecification)
		{
			versionSpecification = "v2.6";
		}
	}
	
	@SuppressWarnings("unchecked")
	@Test (groups ="Sanity", priority=1)
	public void timeLineTests() throws IOException
	{
		System.out.println(versionSpecification);
		int cnt=1;
		boolean checkforNull = false;
		ArrayList<HashMap<String, Object>> allResults = new ArrayList<>();
		HashMap<String, Object> results = new HashMap<>();
		RequestGenerator timelinereq =null;
		MyntraService timelineservice = Myntra.getService(ServiceType.LGP_LGPSERVICE, APINAME.GETTIMELINECARDS, init.Configurations);
		timelineservice.URL=utilities.prepareparameterizedURL(timelineservice.URL, new String[]{versionSpecification});
		System.out.println(versionSpecification);
		System.out.println(timelineservice.URL);
		if(headers!=null)
		{
		  timelinereq = new RequestGenerator(timelineservice,headers);
		}

		String response = timelinereq.returnresponseasstring();
		System.out.println(response);
		log.info(response);
		int count = Integer.parseInt(JsonPath.read(response,"$.count").toString().trim());
		if(count>0)
		{
		 String checkPreviousFeeds= JsonPath.read(response,"$page.previous");
		 results=lgpServiceHelper.validateFeedCardsIndividually(response, versionSpecification,"timelines");
		 results.put("Actual_Count", String.valueOf(count));
		 results.put("Response_Code",String.valueOf(timelinereq.response.getStatus()));
		 allResults.add(results);
		 while(checkPreviousFeeds!=null)
		 {
			cnt++;
			System.out.println(cnt);
			checkPreviousFeeds=checkPreviousFeeds.substring(1);
			timelineservice = Myntra.getService(ServiceType.LGP_LGPSERVICE, APINAME.GETPREVIOUSTIMELINECARDS, init.Configurations);
			timelineservice.URL=utilities.prepareparameterizedURL(timelineservice.URL, new String[]{checkPreviousFeeds});
			if(headers!=null)
			{
				timelinereq = new RequestGenerator(timelineservice,headers);
			}
			response = timelinereq.returnresponseasstring();
			System.out.println(response);
			log.info(response);
			System.out.println(timelineservice.URL);
			count = Integer.parseInt(JsonPath.read(response,"$.count").toString().trim());
			checkPreviousFeeds= JsonPath.read(response,"$page.previous");
			if(checkPreviousFeeds== null)
			{
				break;
			}
			if(count>0)
			{
			 results=lgpServiceHelper.validateFeedCardsIndividually(response, versionSpecification,"timelines");
			 results.put("Actual_Count", String.valueOf(count));
			 results.put("Response_Code",String.valueOf(timelinereq.response.getStatus()));
			 allResults.add(results);
			}
			else
			{
				checkforNull=true;
			}
		  }
		}
		else
		{
			checkforNull=true;
		}
		
		System.out.println(checkforNull);
        System.out.println("Final Results :"+allResults);
		for(int i=0;i<allResults.size();i++)
		{
			HashMap<String,Object> map = allResults.get(i);
			
			ArrayList<String> results1 = null;
			ArrayList<String> results2 = null;
			
			try {
				
				results1 = (ArrayList<String>) map.get("cardValidationResult");
				results2 = (ArrayList<String>) map.get("componentValidationResult");
				
			} catch (ClassCastException e) {
				
				e.printStackTrace();
			}
			Assert.assertEquals(true,checkCardResults(results1), i+" TimeLine calls made and card validations got failed at this point");
			Assert.assertEquals(true,checkCardResults(results2),i+" TimeLine calls made and component validations got failed at this point");
			Assert.assertEquals(Integer.parseInt(map.get("Response_Code").toString()), 200,"The response code is not as expected");
			Assert.assertEquals(Integer.parseInt(map.get("TotalCntofCards").toString()),Integer.parseInt(map.get("Actual_Count").toString()),"Count doesnt match with the number of cards retrieved");
		}
		
		// Assertions for Feeds
		if(checkforNull)
		{
			Assert.fail("TimeLine call No: "+ cnt +"doesnt give any response.Feature Gate might be turned off or it may be a failure");
		}
		
			
	}
	
	private Object checkCardResults(ArrayList<String> results) {
		for(int i =0;i<results.size();i++)
		{
			if(results.get(i).contains("false"))
			{
				return false;
			}
		}
		return true;
	}
	
	@SuppressWarnings("unchecked")
	@Test (groups ="Sanity",priority=2)
	public void OthersTimeLineTests() throws IOException
	{
		System.out.println(versionSpecification);
		int cnt=1;
		boolean checkforNull = false;
		ArrayList<HashMap<String, Object>> allResults = new ArrayList<>();
		HashMap<String, Object> results = new HashMap<>();
		RequestGenerator timelinereq =null;
		MyntraService timelineservice = Myntra.getService(ServiceType.LGP_LGPSERVICE, APINAME.GETOTHERTIMELINECARDS, init.Configurations);
		timelineservice.URL=utilities.prepareparameterizedURL(timelineservice.URL, new String[]{versionSpecification,headers.get("uidx")});
		System.out.println(versionSpecification);
		System.out.println(timelineservice.URL);
		if(headers!=null)
		{
		  timelinereq = new RequestGenerator(timelineservice);
		}

		String response = timelinereq.returnresponseasstring();
		System.out.println(response);
		log.info(response);
		int count = Integer.parseInt(JsonPath.read(response,"$.count").toString().trim());
		if(count>0)
		{
		 String checkPreviousFeeds= JsonPath.read(response,"$page.previous");
		 results=lgpServiceHelper.validateFeedCardsIndividually(response, versionSpecification,"timelines");
		 results.put("Actual_Count", String.valueOf(count));
		 results.put("Response_Code",String.valueOf(timelinereq.response.getStatus()));
		 allResults.add(results);
		 while(checkPreviousFeeds!=null)
		  {
			cnt++;
			System.out.println(cnt);
			checkPreviousFeeds=checkPreviousFeeds.substring(1);
			timelineservice = Myntra.getService(ServiceType.LGP_LGPSERVICE, APINAME.GETPREVIOUSTIMELINECARDS, init.Configurations);
			timelineservice.URL=utilities.prepareparameterizedURL(timelineservice.URL, new String[]{checkPreviousFeeds,headers.get("uidx")});
			if(headers!=null)
			{
				timelinereq = new RequestGenerator(timelineservice);
			}
			response = timelinereq.returnresponseasstring();
			System.out.println(response);
			log.info(response);
			System.out.println(timelineservice.URL);
			count = Integer.parseInt(JsonPath.read(response,"$.count").toString().trim());
			checkPreviousFeeds= JsonPath.read(response,"$page.previous");
			if(checkPreviousFeeds== null)
			{
				break;
			}
			if(count>0)
			{
			 results=lgpServiceHelper.validateFeedCardsIndividually(response, versionSpecification,"timelines");
			 results.put("Actual_Count", String.valueOf(count));
			 results.put("Response_Code",String.valueOf(timelinereq.response.getStatus()));
			 allResults.add(results);
			}
			else
			{
				checkforNull=true;
			}
		  }
		}
		else
		{
			checkforNull=true;
		}
		
		System.out.println(checkforNull);
        System.out.println("Final Results :"+allResults);
		for(int i=0;i<allResults.size();i++)
		{
			HashMap<String,Object> map = allResults.get(i);
			
			
			ArrayList<String> results1 = null;
			ArrayList<String> results2 = null;
			try {
				
				results1 = (ArrayList<String>) map.get("cardValidationResult");
				results2 = (ArrayList<String>) map.get("componentValidationResult");
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			Assert.assertEquals(true,checkCardResults(results1), i+" <<<<<<<  The object id of failure card >>>>>>>> :"+results1.toString()+ " TimeLine calls made and card validations got failed at this point");
			Assert.assertEquals(true,checkCardResults(results2),i+" <<<<<<<< Component failure instance : >>>>>>>>>> :"+results2.toString()+  " TimeLine calls made and component validations got failed at this point");
			Assert.assertEquals(Integer.parseInt(map.get("Response_Code").toString()), 200,"The response code is not as expected");
			Assert.assertEquals(Integer.parseInt(map.get("TotalCntofCards").toString()),Integer.parseInt(map.get("Actual_Count").toString()),"Count doesnt match with the number of cards retrieved");
		}
		
		// Assertions for Feeds
		if(checkforNull)
		{
			Assert.fail("Others TimeLine call No: "+ cnt +" doesnt give any response.Feature Gate might be turned off or it may be a failure");
		}
		
			
	}

}
