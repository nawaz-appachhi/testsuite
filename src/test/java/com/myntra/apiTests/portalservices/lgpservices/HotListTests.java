package com.myntra.apiTests.portalservices.lgpservices;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.myntra.apiTests.common.Myntra;
import com.myntra.apiTests.portalservices.lgpServe.feed.FeedObjectHelper;
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

public class HotListTests {

		static Initialize init = new Initialize("/Data/configuration");
		static Logger log = Logger.getLogger(HotListTests.class);
		static APIUtilities apiUtil = new APIUtilities();
		static APIUtilities utilities = new APIUtilities();
		static FeedObjectHelper feedHelper= new FeedObjectHelper();
		static LgpServicesHelper lgpServiceHelper=new LgpServicesHelper();
		
		static String xid;
		public static String versionSpecification;

		
		@BeforeClass(alwaysRun=true)
		public void signInAndGetXid()
		{
			versionSpecification=System.getenv("API_VERSION");
			if(null==versionSpecification)
			{


			 versionSpecification = "v2.8";


			}
		}
		
		@Test (groups ="Sanity")
		public void hotListTestsForAndroid() throws IOException
		{
			ArrayList<HashMap<String, Object>> allResults = new ArrayList<>();
			HashMap<String, Object> results = new HashMap<>();
			MyntraService feedservice = Myntra.getService(ServiceType.LGP_LGPSERVICE, APINAME.GETHOTLISTANDROID, init.Configurations);
			feedservice.URL=utilities.prepareparameterizedURL(feedservice.URL, new String[]{versionSpecification});
			System.out.println(versionSpecification);
			System.out.println(feedservice.URL);
			RequestGenerator feedreq = new RequestGenerator(feedservice);
			String response = feedreq.returnresponseasstring();
			System.out.println(response);
			results=lgpServiceHelper.validateFeedCardsIndividually(response, versionSpecification,"hotlist");
			int count = Integer.parseInt(JsonPath.read(response,"$.count").toString().trim());
			results.put("Response_Code",String.valueOf(feedreq.response.getStatus()));
			results.put("Actual_Count", String.valueOf(count));
			allResults.add(results);
			if(count==1)
			{
				boolean result = feedHelper.hotListValidations(response, versionSpecification);
				Assert.assertEquals(true, result,"The HotList Validations failed.");
			    Assert.assertEquals(1,count,"The count should be 1 as feed is enabled");	
			}
			
			Assert.assertEquals(true,count>0,"The test failed as hotlist-Android count was zero");
			for(int i=0;i<allResults.size();i++)
			{
				HashMap<String,Object> map = allResults.get(i);
				ArrayList<String> results1 = (ArrayList<String>) map.get("cardValidationResult");
				ArrayList<String> results2 = (ArrayList<String>) map.get("componentValidationResult");
				Assert.assertEquals(true,checkCardResults(results1), i+ "Feed calls made and card validations got failed at this point");
				Assert.assertEquals(true,checkCardResults(results2),i+ "Feed calls made and component validations got failed at this point");
				Assert.assertEquals(Integer.parseInt(map.get("Response_Code").toString()), 200,"The response code is not as expected");
				Assert.assertEquals(Integer.parseInt(map.get("TotalCntofCards").toString()),Integer.parseInt(map.get("Actual_Count").toString()),"Count doesnt match with the number of cards retrieved");
			}
     	}
		
		
		@Test (groups ="Sanity")
		public void hotListTestsForIOS() throws IOException
		{
			ArrayList<HashMap<String, Object>> allResults = new ArrayList<>();
			HashMap<String, Object> results = new HashMap<>();
			MyntraService feedservice = Myntra.getService(ServiceType.LGP_LGPSERVICE, APINAME.GETHOTLISTIOS, init.Configurations);
			feedservice.URL=utilities.prepareparameterizedURL(feedservice.URL, new String[]{versionSpecification});
			System.out.println(versionSpecification);
			System.out.println(feedservice.URL);
			RequestGenerator feedreq = new RequestGenerator(feedservice);
			String response = feedreq.returnresponseasstring();
			System.out.println(response);
			results=lgpServiceHelper.validateFeedCardsIndividually(response, versionSpecification,"hotlist");
			int count = Integer.parseInt(JsonPath.read(response,"$.count").toString().trim());
			results.put("Response_Code",String.valueOf(feedreq.response.getStatus()));
			results.put("Actual_Count", String.valueOf(count));
			allResults.add(results);
			if(count==1)
			{
				boolean result = feedHelper.hotListValidations(response, versionSpecification);
				Assert.assertEquals(true, result,"The HotList Validations failed.");
			    Assert.assertEquals(1,count,"The count should be 1 as feed is enabled");	
			}
			
			Assert.assertEquals(true,count>0,"The test failed as hotlist-Android count was zero");
			for(int i=0;i<allResults.size();i++)
			{
				HashMap<String,Object> map = allResults.get(i);
				ArrayList<String> results1 = (ArrayList<String>) map.get("cardValidationResult");
				ArrayList<String> results2 = (ArrayList<String>) map.get("componentValidationResult");
				Assert.assertEquals(true,checkCardResults(results1), i+ "Feed calls made and card validations got failed at this point");
				Assert.assertEquals(true,checkCardResults(results2),i+ "Feed calls made and component validations got failed at this point");
				Assert.assertEquals(Integer.parseInt(map.get("Response_Code").toString()), 200,"The response code is not as expected");
				Assert.assertEquals(Integer.parseInt(map.get("TotalCntofCards").toString()),Integer.parseInt(map.get("Actual_Count").toString()),"Count doesnt match with the number of cards retrieved");
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

}
