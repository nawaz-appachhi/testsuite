package com.myntra.apiTests.portalservices.LGAListsHelper;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.MultivaluedMap;

import com.myntra.apiTests.common.Myntra;
import com.myntra.apiTests.portalservices.AppReferral.AppReferralServiceHelper;
import com.myntra.apiTests.portalservices.commons.CommonUtils;
import org.apache.log4j.Logger;

import com.jayway.jsonpath.JsonPath;
import com.myntra.lordoftherings.Initialize;
import com.myntra.apiTests.common.APINAME;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.apiTests.common.ServiceType;

public class LGAListsServiceHelper extends CommonUtils {
	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(AppReferralServiceHelper.class);
	static String xId, sXid, uidx, xsrf;
	APIUtilities apiUtil=new APIUtilities();
	
	private static void printAndLog(String Message, String Param)
	{
		System.out.println(Message+Param);
		log.info(Message+Param);
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
		
	}
	
	public RequestGenerator invokeGetUserListsService(String userName, String password)
	{
		getAndSetTokens(userName, password);
		MyntraService service = Myntra.getService(ServiceType.PORTAL_LISTS, APINAME.GETUSERLISTS,init.Configurations);
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("xid", xId);
		printAndLog("LGA Lists - GET USERLIST URL : ",service.URL);
		return new RequestGenerator(service,headers);
	}
	
	public RequestGenerator invokeGetUserListsByIdService(String userName, String password, String listId)
	{
		getAndSetTokens(userName, password);
		MyntraService service = Myntra.getService(ServiceType.PORTAL_LISTS, APINAME.GETLISTBYID,init.Configurations);
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("xid", xId);
		service.URL=apiUtil.prepareparameterizedURL(service.URL, listId);
		printAndLog("LGA Lists - GET USERLIST By ID URL : ",service.URL);
		return new RequestGenerator(service,headers);
	}
	
	public RequestGenerator invokeCreateListService(String userName, String password,String listAccess, String listDescription,String listType, String listName, String style1, String style2, String tag1, String tag2)
	{
		getAndSetTokens(userName, password);
		MyntraService service = Myntra.getService(ServiceType.PORTAL_LISTS, APINAME.CREATELIST, init.Configurations, new String[] {listAccess, listDescription, listType, listName,style1,style2,tag1,tag2});
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("xid", xId);
		printAndLog("LGA Lists - Create User List - Payload : ",service.Payload);
		printAndLog("LGA Lists - Create User List - URL : ",service.URL);
		return new RequestGenerator(service,headers);
	}
	
	

	public RequestGenerator invokeEditListService(String userName, String password,String listAccess, String listDescription,String listId, String listName)
	{
		getAndSetTokens(userName, password);
		MyntraService service = Myntra.getService(ServiceType.PORTAL_LISTS, APINAME.EDITLIST, init.Configurations, new String[] {listAccess, listDescription, listId,listName});
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("xid", xId);
		printAndLog("LGA Lists - Edit User List - Payload : ",service.Payload);
		service.URL=apiUtil.prepareparameterizedURL(service.URL,listId);
		printAndLog("LGA Lists - Edit User List - URL : ",service.URL);
		return new RequestGenerator(service,headers);
	}
	
	public RequestGenerator invokeTagService(String userName, String password, APINAME apiName, String listId, String tag)
	{
		getAndSetTokens(userName, password);
		MyntraService service = Myntra.getService(ServiceType.PORTAL_LISTS, apiName, init.Configurations, new String[] {tag, listId});
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("xid", xId);
		printAndLog("LGA Lists - Tag User List - Payload : ",service.Payload);
		service.URL=apiUtil.prepareparameterizedURL(service.URL,listId);
		printAndLog("LGA Lists - Tag User List - URL : ",service.URL);
		return new RequestGenerator(service,headers);
	}

	public RequestGenerator invokeAddRemoveStyleToListService(String userName, String password, APINAME apiName, String listId, String StyleId, String listType)
	{
		getAndSetTokens(userName, password);
		MyntraService service = Myntra.getService(ServiceType.PORTAL_LISTS, apiName, init.Configurations, new String[] {StyleId,listType, listId});
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("xid", xId);
		printAndLog("LGA Lists - Add/Remove Style in - Payload : ",service.Payload);
		service.URL=apiUtil.prepareparameterizedURL(service.URL,listId);
		printAndLog("LGA Lists - Add/Remove Style in User List - URL : ",service.URL);
		return new RequestGenerator(service,headers);
	}

	public RequestGenerator invokeModifyListAccessService(String userName, String password, String listId, String Access)
	{
		getAndSetTokens(userName, password);
		MyntraService service = Myntra.getService(ServiceType.PORTAL_LISTS, APINAME.MODIFYLISTACCESS, init.Configurations,new String[] {Access, listId});
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("xid", xId);
		service.URL=apiUtil.prepareparameterizedURL(service.URL, new String[] {uidx,listId});
		printAndLog("LGA Lists - Modify List Access - Payload : ",service.Payload);
		printAndLog("LGA Lists - Modify List Access List - URL : ",service.URL);
		return new RequestGenerator(service,headers);
	}
	
	
	
	public RequestGenerator invokeDeleteListService(String userName, String password, String listId)
	{
		getAndSetTokens(userName, password);
		MyntraService service = Myntra.getService(ServiceType.PORTAL_LISTS, APINAME.DELETELIST, init.Configurations,new String[] {listId});
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("xid", xId);
		service.URL=apiUtil.prepareparameterizedURL(service.URL, uidx);
		printAndLog("LGA Lists - Delete User List - Payload : ",service.Payload);
		printAndLog("LGA Lists - Delete User List - URL : ",service.URL);
		return new RequestGenerator(service,headers);
	}
	
	public RequestGenerator invokeRemoveStyleFromListUsingItemIDService(String userName, String password, String StyleId, String listType, String listId, String itemId)
	{
		getAndSetTokens(userName, password);
		MyntraService service = Myntra.getService(ServiceType.PORTAL_LISTS, APINAME.DELETESTYLEFROMLISTUSINGITEMID, init.Configurations,new String[] {StyleId, listType, listId, itemId});
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("xid", xId);
		service.URL=apiUtil.prepareparameterizedURL(service.URL, listId);
		printAndLog("LGA Lists - Delete Style From List - Payload : ",service.Payload);
		printAndLog("LGA Lists - Delete Style From List - URL : ",service.URL);
		return new RequestGenerator(service,headers);
	
	}
	
}
