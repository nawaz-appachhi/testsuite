package com.myntra.apiTests.portalservices.dealservice;

import java.util.HashMap;

import com.myntra.apiTests.common.Myntra;
import com.myntra.apiTests.portalservices.commons.CommonUtils;
import org.apache.log4j.Logger;
import org.testng.AssertJUnit;

import com.myntra.lordoftherings.Initialize;
import com.myntra.apiTests.common.APINAME;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.PayloadType;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.apiTests.common.ServiceType;

public class DOTDHelper extends CommonUtils {
	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(DealServiceHelper.class);
	
	public RequestGenerator getRequest(APINAME apiName, String name, String desc, String startTime, 
			String endTime, String styleId, String vfDiscPercent,String cfDiscPercent, String showInCard){
		MyntraService service = Myntra.getService(ServiceType.PORTAL_DOTD,
				apiName, init.Configurations, new String[] { name, desc,  startTime, endTime, styleId, 
																vfDiscPercent, cfDiscPercent, showInCard},	
				PayloadType.JSON, PayloadType.JSON);
		HashMap getParam = new HashMap();
		getParam.put("user",
				"coupontesting@myntra.com");
		
		System.out.println("Create New Deal Payload ----> "+service.Payload);
		RequestGenerator req = new RequestGenerator(service, getParam);
		log.info(service.URL);
		return req;
	}	
	
	public RequestGenerator getRequestSoldOut(APINAME apiName, String name, String desc, String startTime, 
			String endTime, String styleId, String vfDiscPercent,String cfDiscPercent, String showInCard,String startTimeSold, String expirySoldOutTime){
		MyntraService service = Myntra.getService(ServiceType.PORTAL_DOTD,
				apiName, init.Configurations, new String[] { name, desc,  startTime, endTime, styleId, 
																vfDiscPercent, cfDiscPercent, showInCard,startTimeSold,expirySoldOutTime},	
				PayloadType.JSON, PayloadType.JSON);
		HashMap getParam = new HashMap();
		getParam.put("user",
				"coupontesting@myntra.com");
		
		System.out.println("Create New Deal Payload ----> "+service.Payload);
		RequestGenerator req = new RequestGenerator(service, getParam);
		log.info(service.URL);
		return req;
	}	
	
	
	public RequestGenerator getRequestMultipleStyles(APINAME apiName, String name, String desc, String startTime, 
			String endTime, String styleId1, String vfDiscPercent1,String cfDiscPercent1, String showInCard1, String styleId2, String vfDiscPercent2,String cfDiscPercent2, String showInCard2,String styleId3, String vfDiscPercent3,String cfDiscPercent3, String showInCard3,String styleId4, String vfDiscPercent4,String cfDiscPercent4, String showInCard4,String styleId5, String vfDiscPercent5,String cfDiscPercent5, String showInCard5,String styleId6, String vfDiscPercent6,String cfDiscPercent6, String showInCard6,String styleId7, String vfDiscPercent7,String cfDiscPercent7, String showInCard7){
		MyntraService service = Myntra.getService(ServiceType.PORTAL_DOTD,
				apiName, init.Configurations, new String[] { name, desc,  startTime, endTime, styleId1, vfDiscPercent1, cfDiscPercent1, showInCard1, styleId2, vfDiscPercent2, cfDiscPercent2, showInCard2, styleId3, vfDiscPercent3, cfDiscPercent3, showInCard3, styleId4, vfDiscPercent4, cfDiscPercent4, showInCard4, styleId5, vfDiscPercent5, cfDiscPercent5,  showInCard5, styleId6, vfDiscPercent6, cfDiscPercent6,  showInCard6, styleId7, vfDiscPercent7, cfDiscPercent7, showInCard7},	
				PayloadType.JSON, PayloadType.JSON);
		HashMap getParam = new HashMap();
		getParam.put("user",
				"coupontesting@myntra.com");
		
		System.out.println("Create New Deal Payload ----> "+service.Payload);
		RequestGenerator req = new RequestGenerator(service, getParam);
		log.info(service.URL);
		return req;
	}
	
	public RequestGenerator getDeals(String dealID)
	{
		
		MyntraService service = Myntra.getService(ServiceType.PORTAL_DOTD, APINAME.GETDEALS,init.Configurations,PayloadType.JSON,new String[]{dealID},PayloadType.JSON);
		HashMap getParam = new HashMap();
		getParam.put("user",
				"coupontesting@myntra.com");
		getParam.put("Content-Type", "application/json");
		getParam.put("Accept", "application/json");
		RequestGenerator req = new RequestGenerator(service,getParam);
		log.info(service.URL);
		AssertJUnit.assertEquals("Response is not 200 OK", 200,
				req.response.getStatus());
		return req; 
	}
	
	
	public RequestGenerator getAllDeals()
	{
		
		MyntraService service = Myntra.getService(ServiceType.PORTAL_DOTD, APINAME.GETALLDEAL,init.Configurations);
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("user", "manish");
		headers.put("Content-Type", "application/json");
		headers.put("Accept", "application/json");

		RequestGenerator req = new RequestGenerator(service,headers);
		log.info(service.URL);
		AssertJUnit.assertEquals("Response is not 200 OK", 200,
				req.response.getStatus());
		return req; 
	}
	

	
	public RequestGenerator getAllDealsE()
	{
		
		MyntraService service = Myntra.getService(ServiceType.PORTAL_DOTD, APINAME.GETALLDEALE,init.Configurations);
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("user", "manish");
		headers.put("Content-Type", "application/json");
		headers.put("Accept", "application/json");

		RequestGenerator req = new RequestGenerator(service,headers);
		log.info(service.URL);
		AssertJUnit.assertEquals("Response is not 200 OK", 200,
				req.response.getStatus());
		return req; 
	}
	
	public RequestGenerator getAllDealsFE()
	{
		
		MyntraService service = Myntra.getService(ServiceType.PORTAL_DOTD, APINAME.GETALLDEALFE,init.Configurations);
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("user", "manish");
		headers.put("Content-Type", "application/json");
		headers.put("Accept", "application/json");

		RequestGenerator req = new RequestGenerator(service,headers);
		log.info(service.URL);
		AssertJUnit.assertEquals("Response is not 200 OK", 200,
				req.response.getStatus());
		return req; 
	}
	
	public RequestGenerator getAllDealsC()
	{
		
		MyntraService service = Myntra.getService(ServiceType.PORTAL_DOTD, APINAME.GETALLDEALC,init.Configurations);
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("user", "manish");
		headers.put("Content-Type", "application/json");
		headers.put("Accept", "application/json");

		RequestGenerator req = new RequestGenerator(service,headers);
		log.info(service.URL);
		AssertJUnit.assertEquals("Response is not 200 OK", 200,
				req.response.getStatus());
		return req; 
	}
	
	public RequestGenerator userSubscribed(APINAME apiName, String name)
	{
		
		MyntraService service = Myntra.getService(ServiceType.PORTAL_DOTD,
				apiName, init.Configurations, new String[] { name},	
				PayloadType.JSON, PayloadType.JSON);
		HashMap getParam = new HashMap();
		getParam.put("user",
				"coupontesting@myntra.com");
		
		System.out.println("Create New Deal Payload ----> "+service.Payload);
		RequestGenerator req = new RequestGenerator(service, getParam);
		log.info(service.URL);
		return req; 
	}
	public RequestGenerator userSubscribedFalse(APINAME apiName, String name)
	{
		
		MyntraService service = Myntra.getService(ServiceType.PORTAL_DOTD,
				apiName, init.Configurations, new String[] { name},	
				PayloadType.JSON, PayloadType.JSON);
		HashMap getParam = new HashMap();
		getParam.put("user",
				"coupontesting@myntra.com");
		
		System.out.println("Create New Deal Payload ----> "+service.Payload);
		RequestGenerator req = new RequestGenerator(service, getParam);
		log.info(service.URL);
		return req; 
	}
	
	public RequestGenerator isUserSubscribed(String uidx)
	{
		
		MyntraService service = Myntra.getService(ServiceType.PORTAL_DOTD, APINAME.ISUSERSUBSCRIBED,init.Configurations,PayloadType.JSON,new String[]{uidx},PayloadType.JSON);
		HashMap getParam = new HashMap();
		
		getParam.put("Content-Type", "application/json");
		getParam.put("Accept", "application/json");
		RequestGenerator req = new RequestGenerator(service,getParam);
		log.info(service.URL);
		AssertJUnit.assertEquals("Response is not 200 OK", 200,
				req.response.getStatus());
		return req; 
	}
	

}
