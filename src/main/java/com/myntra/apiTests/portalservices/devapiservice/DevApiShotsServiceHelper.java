package com.myntra.apiTests.portalservices.devapiservice;

import java.util.HashMap;

import com.myntra.apiTests.common.Myntra;
import com.myntra.apiTests.portalservices.commons.CommonUtils;
import org.apache.log4j.Logger;
import net.minidev.json.JSONArray;
import org.json.JSONException;


import com.jayway.jsonpath.JsonPath;
import com.myntra.lordoftherings.Initialize;
import com.myntra.apiTests.common.APINAME;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.apiTests.common.ServiceType;

public class DevApiShotsServiceHelper extends CommonUtils {
	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(DevApiServiceHelper.class);
	static String xId, sXid, uidx, ppid;
	APIUtilities apiUtil=new APIUtilities();
	
	public void printAndLog(String Message, String Param)
	{
		System.out.println(Message+Param);
		log.info(Message+Param);
	}
	
	public String getUIDX()
	{
		return uidx;
	}
	
	
	
	private void getAndSetTokens(String userName, String password)
	{
		System.out.println("\nUsername : "+userName+"\nPassword : "+password);
		MyntraService signInService = Myntra.getService(ServiceType.PORTAL_DEVAPIS, APINAME.DEVAPISIGNIN, init.Configurations, new String[] { userName, password });
		printAndLog("\nSign In Service URL : ",signInService.URL);
		
		RequestGenerator signInRequest = new RequestGenerator(signInService);
		
		String Response = signInRequest.respvalidate.returnresponseasstring();
		printAndLog("\nSign In Response : ",Response);
		uidx = JsonPath.read(Response, "$.data.uidx").toString();
		xId = JsonPath.read(Response, "$.meta.token").toString();
		ppid= JsonPath.read(Response, "$.data.profile.publicProfileId");
		
		printAndLog("\nXID : ",xId);
		printAndLog("\nUIDX : ",uidx);
		printAndLog("\nPPID : ",ppid);
	}
	
	
	//Service Helpers
	public RequestGenerator getShotsByCriteria (String userName, String password, String Criteria, String ObjectId, String OccasionType, String OccasionName)
	{
		MyntraService getShotService;
		HashMap<String, String> getShotServiceHeaders = new HashMap<String, String>();
		getAndSetTokens(userName, password);
		switch (Criteria)
		{
			case "BY_OBJECTID":
			{
				System.out.println("GET SHOTS BY OBJECT ID");
				getShotService = Myntra.getService(ServiceType.PORTAL_DEVAPISHOTS, APINAME.GETSHOTSBYOBJECTID,init.Configurations);
				getShotService.URL = apiUtil.prepareparameterizedURL(getShotService.URL, ObjectId);
				break;
			}
			
			case "BY_OCCASION":
			{
				System.out.println("GET SHOTS BY OCCASSION");
				getShotService = Myntra.getService(ServiceType.PORTAL_DEVAPISHOTS, APINAME.GETSHOTSBYOCCASION,init.Configurations);
				getShotService.URL = apiUtil.prepareparameterizedURL(getShotService.URL, new String[] {OccasionType,OccasionName});
				break;
			}
			
			case "BY_USER":
			{
				System.out.println("GET SHOTS BY USER");
				getShotService = Myntra.getService(ServiceType.PORTAL_DEVAPISHOTS, APINAME.GETSHOTSBYUSER,init.Configurations);
				break;
			}
			
			case "BY_UIDX":
			{
				System.out.println("GET SHOTS BY UIDX");
				getShotService = Myntra.getService(ServiceType.PORTAL_DEVAPISHOTS, APINAME.GETSHOTSBYUIDX,init.Configurations);
				getShotService.URL = apiUtil.prepareparameterizedURL(getShotService.URL, uidx);
				break;

			}
			
			case "BY_PPID":
			{
				System.out.println("GET SHOTS BY PPID");
				getShotService = Myntra.getService(ServiceType.PORTAL_DEVAPISHOTS, APINAME.GETSHOTSBYPPID,init.Configurations);
				getShotService.URL = apiUtil.prepareparameterizedURL(getShotService.URL, ppid);
				break;

			}
			
			default:
			{
				System.out.println("GET SHOTS BY USER - DEFAULT CASE");
				getShotService = Myntra.getService(ServiceType.PORTAL_DEVAPISHOTS, APINAME.GETSHOTSBYUSER,init.Configurations);
				break;

			}
		}
		
		printAndLog(new String ("Get Shots by "+Criteria+" Service URL : "),getShotService.URL);
		getShotServiceHeaders.put("xid", xId);		
		return new RequestGenerator(getShotService,getShotServiceHeaders);
		
	}
	
	public RequestGenerator createShots(String userName, String password, String shotDescription, String styleData, String topic)
	{
		MyntraService createShotsService = Myntra.getService(ServiceType.PORTAL_DEVAPISHOTS, APINAME.CREATESHOTS,init.Configurations, new String[] {shotDescription,styleData,topic});
		HashMap<String, String> createShotsServiceHeaders = new HashMap<String, String>();
		getAndSetTokens(userName, password);
		printAndLog("Create Shots Service URL : ",createShotsService.URL);
		printAndLog("Create Shots Service Payload : ",createShotsService.Payload);
		createShotsServiceHeaders.put("xid", xId);
		return new RequestGenerator(createShotsService,createShotsServiceHeaders);
	}
	
	
	public RequestGenerator updateShots(String userName, String password, String objectId, String shotDescription, String styleData)
	{
		MyntraService updateShotsService = Myntra.getService(ServiceType.PORTAL_DEVAPISHOTS, APINAME.UPDATESHOTS,init.Configurations, new String[] {shotDescription,styleData});
		updateShotsService.URL = apiUtil.prepareparameterizedURL(updateShotsService.URL, objectId);
		HashMap<String, String> updateShotsServiceHeaders = new HashMap<String, String>();
		getAndSetTokens(userName, password);
		printAndLog("Update Shots Service URL : ",updateShotsService.URL);
		printAndLog("Update Shots Service Payload : ",updateShotsService.Payload);

		updateShotsServiceHeaders.put("xid", xId);
		return new RequestGenerator(updateShotsService,updateShotsServiceHeaders);
	}
	
	public RequestGenerator deleteShots(String userName, String password, String objectId)
	{
		MyntraService deleteShotsService = Myntra.getService(ServiceType.PORTAL_DEVAPISHOTS, APINAME.DELETESHOTS,init.Configurations);
		deleteShotsService.URL = apiUtil.prepareparameterizedURL(deleteShotsService.URL, objectId);
		HashMap<String, String> deleteShotsServiceHeaders = new HashMap<String, String>();
		getAndSetTokens(userName, password);
		printAndLog("Delete Shots Service URL : ",deleteShotsService.URL);
		printAndLog("Delete Shots Service Payload : ",deleteShotsService.Payload);

		deleteShotsServiceHeaders.put("xid", xId);
		return new RequestGenerator(deleteShotsService,deleteShotsServiceHeaders);
	}
	
	
	public RequestGenerator searchShots(String type, String status)
	{
		MyntraService searchShots = Myntra.getService(ServiceType.PORTAL_DEVAPISHOTS, APINAME.SEARCHSHOTS, init.Configurations, new String[] {type,status});
		printAndLog("Search Shots Service URL : ",searchShots.URL);
		return new RequestGenerator(searchShots);
	}
	
	public RequestGenerator getTopics(String userName, String password)
	{
		MyntraService service = Myntra.getService(ServiceType.PORTAL_DEVAPISHOTS, APINAME.GETTOPICS,init.Configurations);
		HashMap<String, String> headers = new HashMap<String, String>();
		getAndSetTokens(userName, password);
		printAndLog("Get Topics Service URL : ",service.URL);

		headers.put("xid", xId);
		return new RequestGenerator(service,headers);
	
	
	}
	
	public String[] loadTopics(String userName, String password) throws JSONException
	{
		RequestGenerator request = getTopics(userName, password);
		int Status = request.response.getStatus();
		JSONArray array = new JSONArray();
		String response = request.respvalidate.returnresponseasstring();
		System.out.println("Load Topics Response : \n  "+response);
		if (Status ==200)
		{
			 array = JsonPath.read(response, "$.data.topics[*]");
		}
		
		else
		{
			System.out.println("Unable to Fetch Topics From Server !!!");
		}
		int ArraySize = array.size();
		System.out.println("Total Topics Found : "+ArraySize);
		if(ArraySize==0)
		{
			return new String[] {};
		}
		else
		{
			String Topics[] = new String[ArraySize];
			for(int i=0; i<ArraySize;i++)
			{
				Topics[i] = array.get(i).toString();
			}
			return Topics;
		}
	}
	
}