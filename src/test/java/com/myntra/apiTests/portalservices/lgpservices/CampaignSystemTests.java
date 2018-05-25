package com.myntra.apiTests.portalservices.lgpservices;

import com.jayway.jsonpath.JsonPath;
import com.myntra.apiTests.common.APINAME;
import com.myntra.apiTests.common.ServiceType;
import com.myntra.apiTests.dataproviders.CampaignSystemServices;
import com.myntra.apiTests.portalservices.lgpServe.feed.FeedObjectHelper;
import com.myntra.apiTests.portalservices.lgpservices.LgpServicesHelper;
import com.myntra.apiTests.portalservices.lgpservices.OnBoardingTests;
import com.myntra.lordoftherings.Initialize;

import com.myntra.lordoftherings.gandalf.PayloadType;
import org.apache.log4j.Logger;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Map;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.apiTests.common.Myntra;

public class CampaignSystemTests extends CampaignSystemServices{
	
	/**
	 * LGP- Campaign System tests.
	 * @author sukanya.agarwal
	 *
	 */
	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(OnBoardingTests.class);
	static APIUtilities apiUtil = new APIUtilities();
	static APIUtilities utilities = new APIUtilities();
	static FeedObjectHelper feedHelper= new FeedObjectHelper();
	static LgpServicesHelper lgpServiceHelper=new LgpServicesHelper();
	
	@Test (groups = {"Regression"}, dataProvider = "createAd")
	public void createAd(Map<String, Object> testData,ArrayList<String> output){
		String getAttributePayloadFilePath = "./Data/payloads/JSON/lgp/campaignSystem/lgpCreateAd";
		
		MyntraService getDeviceAttributeService = Myntra.getService(ServiceType.LGP_CAMPAIGNSYSTEMS, APINAME.CAMPAIGNCREATEAD,init.Configurations,new String[]{testData.get("name").toString(),testData.get("description").toString(),testData.get("objectId").toString(),testData.get("budget").toString(),testData.get("boost").toString(),testData.get("persona").toString(),testData.get("startTime").toString(),testData.get("endTime").toString(),testData.get("position").toString(),testData.get("costPerView").toString(),testData.get("frequencyCap").toString()},getAttributePayloadFilePath, PayloadType.JSON,PayloadType.JSON);
		  getDeviceAttributeService.URL=apiUtil.prepareparameterizedURL(getDeviceAttributeService.URL, new String[]{});
		  RequestGenerator getAttributeRequest= new RequestGenerator(getDeviceAttributeService);
		  String attributesResponse = getAttributeRequest.returnresponseasstring();
		  System.out.println(attributesResponse);
		  
		  if(output.get(0).equals("200")){
			  
			  AssertJUnit.assertEquals("Status not equal to "+output.get(0), Integer.parseInt(output.get(0)),getAttributeRequest.response.getStatus());
				AssertJUnit.assertEquals("Status message is not equal to "+output.get(1),output.get(1).toLowerCase(), JsonPath.read(attributesResponse,"$.status.statusMessage").toString().toLowerCase());
				AssertJUnit.assertEquals("Status Type is not equal to "+output.get(2),output.get(2).toLowerCase(), JsonPath.read(attributesResponse,"$.status.statusType").toString().toLowerCase());
				AssertJUnit.assertEquals("Status Code is not equal to "+output.get(3),output.get(3).toLowerCase(), JsonPath.read(attributesResponse,"$.status.statusCode").toString().toLowerCase());
			
		  }
		  else if(output.get(0).equals("400")){
			  
			  AssertJUnit.assertEquals("Status not equal to "+output.get(0), Integer.parseInt(output.get(0)),getAttributeRequest.response.getStatus());
				AssertJUnit.assertEquals("Status message is not equal to "+output.get(1),output.get(1).toLowerCase(), JsonPath.read(attributesResponse,"$.error").toString().toLowerCase());
				//AssertJUnit.assertEquals("Status Type is not equal to "+output.get(2),output.get(2).toLowerCase(), JsonPath.read(attributesResponse,"$.status").toString().toLowerCase());
		  }
		  	
	}
	
	@Test (groups = {"Regression"}, dataProvider = "getAd")
	public void getAd(ArrayList<String> testData,ArrayList<String> output){
		
		String objectId = testData.get(0);
		MyntraService getDeviceAttributeService = Myntra.getService(ServiceType.LGP_CAMPAIGNSYSTEMS,APINAME.CAMPAIGNGETAD,init.Configurations,"");
		  getDeviceAttributeService.URL=apiUtil.prepareparameterizedURL(getDeviceAttributeService.URL, new String[]{objectId});
		  
		  RequestGenerator getAttributeRequest= new RequestGenerator(getDeviceAttributeService);
		  String attributesResponse = getAttributeRequest.returnresponseasstring();
		  System.out.println(attributesResponse);
		  
		  if(output.get(0).equals("200")){
			  
			  AssertJUnit.assertEquals("Status not equal to "+output.get(0), Integer.parseInt(output.get(0)),getAttributeRequest.response.getStatus());
				AssertJUnit.assertEquals("Status message is not equal to "+output.get(1),output.get(1).toLowerCase(), JsonPath.read(attributesResponse,"$.status.statusMessage").toString().toLowerCase());
				AssertJUnit.assertEquals("Status Type is not equal to "+output.get(2),output.get(2).toLowerCase(), JsonPath.read(attributesResponse,"$.status.statusType").toString().toLowerCase());
				AssertJUnit.assertEquals("Status Code is not equal to "+output.get(3),output.get(3).toLowerCase(), JsonPath.read(attributesResponse,"$.status.statusCode").toString().toLowerCase());
			
		  }
		  else if(output.get(0).equals("400")){
			  
			  AssertJUnit.assertEquals("Status not equal to "+output.get(0), Integer.parseInt(output.get(0)),getAttributeRequest.response.getStatus());
				AssertJUnit.assertEquals("Status message is not equal to "+output.get(1),output.get(1).toLowerCase(), JsonPath.read(attributesResponse,"$.error").toString().toLowerCase());
				//AssertJUnit.assertEquals("Status Type is not equal to "+output.get(2),output.get(2).toLowerCase(), JsonPath.read(attributesResponse,"$.status").toString().toLowerCase());
		  }
		  	
	}
	@Test (groups = {"Regression"}, dataProvider = "getAdById")
	public void getAdById(ArrayList<String> testData,ArrayList<String> output){
		
		String objectId = testData.get(0);
		MyntraService getDeviceAttributeService = Myntra.getService(ServiceType.LGP_CAMPAIGNSYSTEMS,APINAME.CAMPAIGNGETADBYID,init.Configurations,"");
		  getDeviceAttributeService.URL=apiUtil.prepareparameterizedURL(getDeviceAttributeService.URL, new String[]{objectId});
		  
		  RequestGenerator getAttributeRequest= new RequestGenerator(getDeviceAttributeService);
		  String attributesResponse = getAttributeRequest.returnresponseasstring();
		  System.out.println(attributesResponse);
		  
		  if(output.get(0).equals("200")){
			  
			  AssertJUnit.assertEquals("Status not equal to "+output.get(0), Integer.parseInt(output.get(0)),getAttributeRequest.response.getStatus());
				AssertJUnit.assertEquals("Status message is not equal to "+output.get(1),output.get(1).toLowerCase(), JsonPath.read(attributesResponse,"$.status.statusMessage").toString().toLowerCase());
				AssertJUnit.assertEquals("Status Type is not equal to "+output.get(2),output.get(2).toLowerCase(), JsonPath.read(attributesResponse,"$.status.statusType").toString().toLowerCase());
				AssertJUnit.assertEquals("Status Code is not equal to "+output.get(3),output.get(3).toLowerCase(), JsonPath.read(attributesResponse,"$.status.statusCode").toString().toLowerCase());
			
		  }
		  else if(output.get(0).equals("400")){
			  
			  AssertJUnit.assertEquals("Status not equal to "+output.get(0), Integer.parseInt(output.get(0)),getAttributeRequest.response.getStatus());
				AssertJUnit.assertEquals("Status message is not equal to "+output.get(1),output.get(1).toLowerCase(), JsonPath.read(attributesResponse,"$.error").toString().toLowerCase());
				//AssertJUnit.assertEquals("Status Type is not equal to "+output.get(2),output.get(2).toLowerCase(), JsonPath.read(attributesResponse,"$.status").toString().toLowerCase());
		  }
		  	
	}
	
	@Test (groups = {"Regression"}, dataProvider = "deleteAdById")
	public void deleteAdById(ArrayList<String> testData,ArrayList<String> output){
		
		String objectId = testData.get(0);
		MyntraService getDeviceAttributeService = Myntra.getService(ServiceType.LGP_CAMPAIGNSYSTEMS,APINAME.CAMPAIGNDELETEADBYID,init.Configurations,"");
		  getDeviceAttributeService.URL=apiUtil.prepareparameterizedURL(getDeviceAttributeService.URL, new String[]{objectId});
		  
		  RequestGenerator getAttributeRequest= new RequestGenerator(getDeviceAttributeService);
		  String attributesResponse = getAttributeRequest.returnresponseasstring();
		  
		  if(output.get(0).equals("200")){
			  
			  AssertJUnit.assertEquals("Status not equal to "+output.get(0), Integer.parseInt(output.get(0)),getAttributeRequest.response.getStatus());
				AssertJUnit.assertEquals("Status message is not equal to "+output.get(1),output.get(1).toLowerCase(), JsonPath.read(attributesResponse,"$.status.statusMessage").toString().toLowerCase());
				AssertJUnit.assertEquals("Status Type is not equal to "+output.get(2),output.get(2).toLowerCase(), JsonPath.read(attributesResponse,"$.status.statusType").toString().toLowerCase());
				AssertJUnit.assertEquals("Status Code is not equal to "+output.get(3),output.get(3).toLowerCase(), JsonPath.read(attributesResponse,"$.status.statusCode").toString().toLowerCase());
			
		  }
		  else if(output.get(0).equals("400")){
			  
			  AssertJUnit.assertEquals("Status not equal to "+output.get(0), Integer.parseInt(output.get(0)),getAttributeRequest.response.getStatus());
				AssertJUnit.assertEquals("Status message is not equal to "+output.get(1),output.get(1).toLowerCase(), JsonPath.read(attributesResponse,"$.error").toString().toLowerCase());
				//AssertJUnit.assertEquals("Status Type is not equal to "+output.get(2),output.get(2).toLowerCase(), JsonPath.read(attributesResponse,"$.status").toString().toLowerCase());
		  }
		  
		  MyntraService getDeviceAttributeService2 = Myntra.getService(ServiceType.LGP_CAMPAIGNSYSTEMS,APINAME.CAMPAIGNGETADBYID,init.Configurations,"");
		  getDeviceAttributeService2.URL=apiUtil.prepareparameterizedURL(getDeviceAttributeService2.URL, new String[]{objectId});
		  
		  RequestGenerator getAttributeRequest2= new RequestGenerator(getDeviceAttributeService2);
		  String attributesResponse2 = getAttributeRequest2.returnresponseasstring();
		  
		  if(output.get(0).equals("200")){
			  
			  AssertJUnit.assertEquals("Status not equal to "+output.get(4), Integer.parseInt(output.get(4)),getAttributeRequest2.response.getStatus());
				AssertJUnit.assertEquals("Status message is not equal to "+output.get(5),output.get(5).toLowerCase(), JsonPath.read(attributesResponse2,"$.status.statusMessage").toString().toLowerCase());
				AssertJUnit.assertEquals("Status Type is not equal to "+output.get(6),output.get(6).toLowerCase(), JsonPath.read(attributesResponse2,"$.status.statusType").toString().toLowerCase());
				AssertJUnit.assertEquals("Status Code is not equal to "+output.get(7),output.get(7).toLowerCase(), JsonPath.read(attributesResponse2,"$.status.statusCode").toString().toLowerCase());
			
		  }	
		  else if(output.get(0).equals("400")){
			  
			  AssertJUnit.assertEquals("Status not equal to "+output.get(0), Integer.parseInt(output.get(0)),getAttributeRequest2.response.getStatus());
				AssertJUnit.assertEquals("Status message is not equal to "+output.get(1),output.get(1).toLowerCase(), JsonPath.read(attributesResponse2,"$.error").toString().toLowerCase());
				//AssertJUnit.assertEquals("Status Type is not equal to "+output.get(2),output.get(2).toLowerCase(), JsonPath.read(attributesResponse,"$.status").toString().toLowerCase());
		  }
	}
	
	@Test (groups = {"Regression"}, dataProvider = "deleteAdByObjectId")
	public void deleteAdByObjectId(ArrayList<String> testData,ArrayList<String> output){
		
		String objectId = testData.get(0);
		MyntraService getDeviceAttributeService = Myntra.getService(ServiceType.LGP_CAMPAIGNSYSTEMS,APINAME.CAMPAIGNDELETEADBYOBJECTID,init.Configurations,"");
		  getDeviceAttributeService.URL=apiUtil.prepareparameterizedURL(getDeviceAttributeService.URL, new String[]{objectId});
		  
		  RequestGenerator getAttributeRequest= new RequestGenerator(getDeviceAttributeService);
		  String attributesResponse = getAttributeRequest.returnresponseasstring();
		  
		  if(output.get(0).equals("200")){
			  
			  AssertJUnit.assertEquals("Status not equal to "+output.get(0), Integer.parseInt(output.get(0)),getAttributeRequest.response.getStatus());
				AssertJUnit.assertEquals("Status message is not equal to "+output.get(1),output.get(1).toLowerCase(), JsonPath.read(attributesResponse,"$.status.statusMessage").toString().toLowerCase());
				AssertJUnit.assertEquals("Status Type is not equal to "+output.get(2),output.get(2).toLowerCase(), JsonPath.read(attributesResponse,"$.status.statusType").toString().toLowerCase());
				AssertJUnit.assertEquals("Status Code is not equal to "+output.get(3),output.get(3).toLowerCase(), JsonPath.read(attributesResponse,"$.status.statusCode").toString().toLowerCase());
			
		  }
		  else if(output.get(0).equals("400")){
			  
			  AssertJUnit.assertEquals("Status not equal to "+output.get(0), Integer.parseInt(output.get(0)),getAttributeRequest.response.getStatus());
				AssertJUnit.assertEquals("Status message is not equal to "+output.get(1),output.get(1).toLowerCase(), JsonPath.read(attributesResponse,"$.error").toString().toLowerCase());
				//AssertJUnit.assertEquals("Status Type is not equal to "+output.get(2),output.get(2).toLowerCase(), JsonPath.read(attributesResponse,"$.status").toString().toLowerCase());
		  }
		  
		  MyntraService getDeviceAttributeService2 = Myntra.getService(ServiceType.LGP_CAMPAIGNSYSTEMS,APINAME.CAMPAIGNGETAD,init.Configurations,"");
		  getDeviceAttributeService2.URL=apiUtil.prepareparameterizedURL(getDeviceAttributeService2.URL, new String[]{objectId});
		  
		  RequestGenerator getAttributeRequest2= new RequestGenerator(getDeviceAttributeService2);
		  String attributesResponse2 = getAttributeRequest2.returnresponseasstring();
		  
		  if(output.get(0).equals("200")){
			  
			  AssertJUnit.assertEquals("Status not equal to "+output.get(4), Integer.parseInt(output.get(4)),getAttributeRequest2.response.getStatus());
				AssertJUnit.assertEquals("Status message is not equal to "+output.get(5),output.get(5).toLowerCase(), JsonPath.read(attributesResponse2,"$.status.statusMessage").toString().toLowerCase());
				AssertJUnit.assertEquals("Status Type is not equal to "+output.get(6),output.get(6).toLowerCase(), JsonPath.read(attributesResponse2,"$.status.statusType").toString().toLowerCase());
				AssertJUnit.assertEquals("Status Code is not equal to "+output.get(7),output.get(7).toLowerCase(), JsonPath.read(attributesResponse2,"$.status.statusCode").toString().toLowerCase());
			
		  }	
		  else if(output.get(0).equals("400")){
			  
			  AssertJUnit.assertEquals("Status not equal to "+output.get(0), Integer.parseInt(output.get(0)),getAttributeRequest2.response.getStatus());
				AssertJUnit.assertEquals("Status message is not equal to "+output.get(1),output.get(1).toLowerCase(), JsonPath.read(attributesResponse2,"$.error").toString().toLowerCase());
				//AssertJUnit.assertEquals("Status Type is not equal to "+output.get(2),output.get(2).toLowerCase(), JsonPath.read(attributesResponse,"$.status").toString().toLowerCase());
		  }
	}

}
