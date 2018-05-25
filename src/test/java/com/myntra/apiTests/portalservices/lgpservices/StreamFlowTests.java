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

public class StreamFlowTests {

	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(StreamFlowTests.class);
	static APIUtilities apiUtil = new APIUtilities();
	static LgpServicesHelper lgpServiceHelper=new LgpServicesHelper();
	static APIUtilities utilities = new APIUtilities();
	
	private String username;
	private String password;
	static String xid;
	public static String versionSpecification;
	public static HashMap<String, String> headers=new HashMap<String, String>();
	
	@BeforeClass(alwaysRun=true)
	public void signInAndGetXid()
	{
		username=System.getenv("username");
		password=System.getenv("password");
		versionSpecification=System.getenv("API_VERSION");
		if(username!=null && password!=null)
		{
			try {
				headers=lgpServiceHelper.getXidandUidxForCredential(username, password);
				System.out.println("Username : "+username);
				System.out.println("Password : "+password);
				headers.put("x-myntra-abtest","lgp.stream.variant=variantC");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(null==versionSpecification)
		{
		 versionSpecification = "v2.8";
		}
	}
	
	@Test (groups ="Sanity")
	public void streamPreviousTests() throws IOException
	{
		System.out.println(versionSpecification);
		int cnt=1;
		String checkPreviousFeeds=null;
		boolean checkforNull = false;
		ArrayList<HashMap<String, Object>> allResults = new ArrayList<>();
		HashMap<String, Object> results = new HashMap<>();
		RequestGenerator feedreq =null;
		MyntraService feedservice = Myntra.getService(ServiceType.LGP_LGPSERVICE, APINAME.GETSTREAM, init.Configurations);
		feedservice.URL=utilities.prepareparameterizedURL(feedservice.URL, new String[]{versionSpecification});
		System.out.println(versionSpecification);
		System.out.println(feedservice.URL);
		if(headers!=null)
		{
		  feedreq = new RequestGenerator(feedservice,headers);
		}

		String response = feedreq.returnresponseasstring();
		System.out.println(response);
		int count = Integer.parseInt(JsonPath.read(response,"$.count").toString().trim());
		if(count>0)
		{
			try
			{
			   checkPreviousFeeds= JsonPath.read(response,"$page.previous");
			}
			catch(Exception e)
			{
				checkPreviousFeeds = null;
			}
		 
		 results=lgpServiceHelper.validateFeedCardsIndividually(response, versionSpecification,"streams");
		 results.put("Response_Code",String.valueOf(feedreq.response.getStatus()));
		 results.put("Actual_Count", String.valueOf(count));
		 allResults.add(results);
		 while(checkPreviousFeeds!=null)
		  {
			cnt++;
			System.out.println("Feed Call Count ------>  "+ cnt);
			checkPreviousFeeds=checkPreviousFeeds.substring(1);
			feedservice = Myntra.getService(ServiceType.LGP_LGPSERVICE, APINAME.GETPREVIOUSSTREAM, init.Configurations);
			feedservice.URL=utilities.prepareparameterizedURL(feedservice.URL, new String[]{checkPreviousFeeds});
			if(headers!=null)
			{
			  feedreq = new RequestGenerator(feedservice,headers);
			}
			response = feedreq.returnresponseasstring();
			System.out.println(response);
			System.out.println(feedservice.URL);
			count = Integer.parseInt(JsonPath.read(response,"$.count").toString().trim());
			try
			{
			   checkPreviousFeeds= JsonPath.read(response,"$page.previous");
			}
			catch(Exception e)
			{
				checkPreviousFeeds = null;
			}
			if(checkPreviousFeeds== null)
			{
				break;
			}
			if(cnt>50)
			{
				break;
			}
			if(count>0)
			{
			 results=lgpServiceHelper.validateFeedCardsIndividually(response, versionSpecification,"streams");
			 results.put("Response_Code",String.valueOf(feedreq.response.getStatus()));
			 results.put("Actual_Count", String.valueOf(count));
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
			ArrayList<String> results1 = (ArrayList<String>) map.get("cardValidationResult");
			ArrayList<String> results2 = (ArrayList<String>) map.get("componentValidationResult");
			Assert.assertEquals(true,checkCardResults(results1), i + " Feed calls made and card validations got failed at this point -- > "+results1.toString());
			Assert.assertEquals(true,checkCardResults(results2),i + allResults.get(i).toString() + " Feed calls made and component validations got failed at this point -- > "+results2.toString());
			Assert.assertEquals(Integer.parseInt(map.get("Response_Code").toString()), 200,"The response code is not as expected");
			Assert.assertEquals(Integer.parseInt(map.get("TotalCntofCards").toString()),Integer.parseInt(map.get("Actual_Count").toString()),"Count doesnt match with the number of cards retrieved");
		}
		
		// Assertions for Feeds
		if(checkforNull)
		{
			Assert.fail("Feed call No: "+ cnt +"doesnt give any response.Feature Gate might be turned off or it may be a failure");
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

	@Test (groups ="Sanity")
	public void streamNextTests() throws IOException
	{
		System.out.println(versionSpecification);
		int cnt=1;
		int cntForExhausted=0;
		boolean checkforNull = false;
		boolean checkforExhausted = false;
		String checkNextFeeds=null;
		ArrayList<HashMap<String, Object>> allResults = new ArrayList<>();
		HashMap<String, Object> results = new HashMap<>();
		RequestGenerator feedreq =null;
		MyntraService feedservice = Myntra.getService(ServiceType.LGP_LGPSERVICE, APINAME.GETSTREAM, init.Configurations);
		feedservice.URL=utilities.prepareparameterizedURL(feedservice.URL, new String[]{versionSpecification});
		System.out.println(versionSpecification);
		System.out.println(feedservice.URL);
		if(headers!=null)
		{
		  feedreq = new RequestGenerator(feedservice,headers);
		}

		String response = feedreq.returnresponseasstring();
		System.out.println(response);
		int count = Integer.parseInt(JsonPath.read(response,"$.count").toString().trim());
		if(count>0)
		{
			try
			{
			    checkNextFeeds= JsonPath.read(response,"$page.next");
		   	}
			catch(Exception e)
			{
				checkNextFeeds = null;
			}
		 
		 
		 results=lgpServiceHelper.validateFeedCardsIndividually(response, versionSpecification,"streams");
		 results.put("Actual_Count", String.valueOf(count));
		 results.put("Response_Code",String.valueOf(feedreq.response.getStatus()));
		 allResults.add(results);
		 while(checkNextFeeds!=null)
		  {
			cnt++;
			System.out.println("Feed Call Count ------>  "+ cnt);
			checkNextFeeds=checkNextFeeds.substring(1);
			feedservice = Myntra.getService(ServiceType.LGP_LGPSERVICE, APINAME.GETNEXTSTREAM, init.Configurations);
			feedservice.URL=utilities.prepareparameterizedURL(feedservice.URL, new String[]{checkNextFeeds});
			if(headers!=null)
			{
			  feedreq = new RequestGenerator(feedservice,headers);
			}
			response = feedreq.returnresponseasstring();
			System.out.println(response);
			System.out.println(feedservice.URL);
			count = Integer.parseInt(JsonPath.read(response,"$.count").toString().trim());
			try
			{
			    checkNextFeeds= JsonPath.read(response,"$page.next");
		   	}
			catch(Exception e)
			{
				checkNextFeeds = null;
			}
			if(checkNextFeeds== null)
			{
				break;
			}
			if(cnt>50)
			{
				break;
			}
			if(count>0)
			{
			    results=lgpServiceHelper.validateFeedCardsIndividually(response, versionSpecification,"streams");
			    results.put("Actual_Count", String.valueOf(count));
			    results.put("Response_Code",String.valueOf(feedreq.response.getStatus()));
			    allResults.add(results);
			    cntForExhausted=0;
			}
			else
			{
				checkforNull=true;
				cntForExhausted++;
				if(cntForExhausted==3)
				{
				 checkforExhausted=true;
				 checkforNull=false;
				 break;
				}
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
			ArrayList<String> results1 = (ArrayList<String>) map.get("cardValidationResult");
			ArrayList<String> results2 = (ArrayList<String>) map.get("componentValidationResult");
			Assert.assertEquals(true,checkCardResults(results1), i+ "Feed calls made and card validations got failed at this point --> "+results1.toString());
			Assert.assertEquals(true,checkCardResults(results2),i+"Feed calls made and component validations got failed at this point  --> "+results2.toString());
			Assert.assertEquals(Integer.parseInt(map.get("Response_Code").toString()), 200,"The response code is not as expected");
			Assert.assertEquals(Integer.parseInt(map.get("TotalCntofCards").toString()),Integer.parseInt(map.get("Actual_Count").toString()),"Count doesnt match with the number of cards retrieved");
		}
		
		// Assertions for Feeds
		if(checkforNull)
		{
			Assert.fail("Stream call No: "+ cnt +" doesnt give any response.Feature Gate might be turned off or it may be a failure");
		}
		
	}

}
