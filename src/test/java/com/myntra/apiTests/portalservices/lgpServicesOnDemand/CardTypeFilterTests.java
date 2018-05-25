package com.myntra.apiTests.portalservices.lgpServicesOnDemand;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.myntra.apiTests.dataproviders.LgpServDP;
import com.myntra.apiTests.portalservices.lgpservices.LgpServicesHelper;
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
import com.myntra.apiTests.common.Myntra;


public class CardTypeFilterTests extends LgpServDP {

	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(CardTypeFilterTests.class);
	static APIUtilities apiUtil = new APIUtilities();
	static LgpServicesHelper lgpServiceHelper=new LgpServicesHelper();
	static APIUtilities utilities = new APIUtilities();
	
	public static String versionSpecification;
	
	
	@BeforeClass(alwaysRun=true)
	public void signInAndGetXid()
	{
		versionSpecification=System.getenv("API_VERSION");
		if(null==versionSpecification)
		{
		 versionSpecification = "v2.6";
		}
	}
	
	
	@SuppressWarnings("unchecked")
	@Test (groups ="Sanity",dataProvider="lgpGetCardTypeFilters")
	public void feedFlowTests(String cardType) throws IOException
	{
		System.out.println(versionSpecification);
		int cnt=1;
		boolean checkforNull = false;
		ArrayList<HashMap<String, Object>> allResults = new ArrayList<>();
		HashMap<String, Object> results = new HashMap<>();
		RequestGenerator feedreq =null;
		MyntraService feedservice = Myntra.getService(ServiceType.LGP_LGPSERVICE, APINAME.GETCARDTYPEFILTER, init.Configurations);
		feedservice.URL=utilities.prepareparameterizedURL(feedservice.URL, new String[]{versionSpecification,cardType});
		System.out.println(versionSpecification);
		System.out.println(feedservice.URL);
		feedreq = new RequestGenerator(feedservice);
		String response = feedreq.returnresponseasstring();
		System.out.println(response);
		log.info(response);
		int count = Integer.parseInt(JsonPath.read(response,"$.count").toString().trim());
		if(count>0)
		{
		 String checkPreviousFeeds= JsonPath.read(response,"$page.previous");
		 results=lgpServiceHelper.validateFeedCardsIndividually(response, versionSpecification,"feeds");
		 results.put("Actual_Count", String.valueOf(count));
		 results.put("Response_Code",String.valueOf(feedreq.response.getStatus()));
		 allResults.add(results);
		 while(checkPreviousFeeds!=null)
		 {
			cnt++;
			System.out.println("Feed Call Count ------>  "+ cnt);
			checkPreviousFeeds=checkPreviousFeeds.substring(1);
			feedservice = Myntra.getService(ServiceType.LGP_LGPSERVICE, APINAME.GETPREVIOUSFEEDS, init.Configurations);
			feedservice.URL=utilities.prepareparameterizedURL(feedservice.URL, new String[]{checkPreviousFeeds});
			feedreq = new RequestGenerator(feedservice);
			response = feedreq.returnresponseasstring();
			System.out.println(response);
			log.info(response);
			System.out.println(feedservice.URL);
			count = Integer.parseInt(JsonPath.read(response,"$.count").toString().trim());
			checkPreviousFeeds= JsonPath.read(response,"$page.previous");
			if(checkPreviousFeeds== null)
			{
				break;
			}
			if(cnt>4)
			{
				break;
			}
			if(count>0)
			{
			 results=lgpServiceHelper.validateFeedCardsIndividually(response, versionSpecification,"feeds");
			 results.put("Actual_Count", String.valueOf(count));
			 results.put("Response_Code",String.valueOf(feedreq.response.getStatus()));
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
			
			
			//System.out.println("Failed Object Id index "+results1.get(0));
			Assert.assertEquals(true,checkCardResults(results1), i+"<<<<<<<  The object id of failure card >>>>>>>> :"+results1.toString()+ "Feed calls made and card validations got failed at this point");
			Assert.assertEquals(true,checkCardResults(results2),i+"<<<<<<<< Component failure instance : >>>>>>>>>>"+results2.toString()+ "Feed calls made and component validations got failed at this point");
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
	
	
	@Test (groups ="Sanity",dataProvider="lgpGetCardTypNegativeeFilters")
	public void cardTypeNegativeTests(String cardType) throws IOException
	{
		System.out.println(versionSpecification);
		boolean checkforNull = false;
		ArrayList<HashMap<String, Object>> allResults = new ArrayList<>();
		HashMap<String, Object> results = new HashMap<>();
		RequestGenerator feedreq =null;
		MyntraService feedservice = Myntra.getService(ServiceType.LGP_LGPSERVICE, APINAME.GETCARDTYPEFILTER, init.Configurations);
		feedservice.URL=utilities.prepareparameterizedURL(feedservice.URL, new String[]{versionSpecification,cardType});
		System.out.println(versionSpecification);
		System.out.println(feedservice.URL);
		feedreq = new RequestGenerator(feedservice);
		String response = feedreq.returnresponseasstring();
		System.out.println(response);
		log.info(response);
		int count = Integer.parseInt(JsonPath.read(response,"$.count").toString().trim());
		if(count>0)
		{
		 JsonPath.read(response,"$page.previous");
		 results=lgpServiceHelper.validateFeedCardsIndividually(response, versionSpecification,"feeds");
		 results.put("Actual_Count", String.valueOf(count));
		 results.put("Response_Code",String.valueOf(feedreq.response.getStatus()));
		 allResults.add(results);
		 System.out.println(allResults);
		}
		else
		{
			checkforNull=true;
		}
		
		System.out.println(checkforNull);
		Assert.assertEquals(count, 0,"The given input "+cardType+" have more cards which is not as expected");
		Assert.assertEquals(checkforNull,true,"The given input "+cardType+"  have valid cards which is not as expected");
		
			
	}


}
