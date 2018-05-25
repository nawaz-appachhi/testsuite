package com.myntra.apiTests.portalservices.devapiservice;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.myntra.lordoftherings.Initialize;
import com.myntra.apiTests.common.APINAME;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.apiTests.common.ServiceType;
import com.myntra.apiTests.common.Myntra;

public class StyleForumTestsHelper 
{
	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(StyleForumTestsHelper.class);
	APIUtilities apiUtilities=new APIUtilities();
	IDPTestsHelper idpHelper = new IDPTestsHelper();
	
	
	public RequestGenerator getStyleForumUserFeeds(String email, String password)
	{
		idpHelper.getAndSetTokens(email, password);
		MyntraService service = Myntra.getService(ServiceType.PORTAL_DEVAPISTYLEFORUM, APINAME.GETSTYLEFORUMUSERFEEDS,init.Configurations);
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("xid", IDPTestsHelper.XID);
		service.URL = apiUtilities.prepareparameterizedURL(service.URL, new String[] {IDPTestsHelper.UIDX});
		return new RequestGenerator(service,headers);
	}
	
	public RequestGenerator getStyleForumFeeds(String email, String password, String feedType, String topicId)
	{
		idpHelper.getAndSetTokens(email, password);
		MyntraService service = Myntra.getService(ServiceType.PORTAL_DEVAPISTYLEFORUM, APINAME.GETSTYLEFORUMFEEDS,init.Configurations);
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("xid", IDPTestsHelper.XID);
		service.URL = apiUtilities.prepareparameterizedURL(service.URL, new String[] {feedType,topicId});
		System.out.println("Service URL : "+service.URL);
		return new RequestGenerator(service,headers);
	}

	public RequestGenerator getStyleForumTopics()
	{
		
		MyntraService service = Myntra.getService(ServiceType.PORTAL_DEVAPISTYLEFORUM, APINAME.GETSTYLEFORUMTOPICS,init.Configurations);
		return new RequestGenerator(service);
	}
	
	public RequestGenerator getStyleForumUserPersonalizedFeeds(String email, String password)
	{
		idpHelper.getAndSetTokens(email, password);
		MyntraService service = Myntra.getService(ServiceType.PORTAL_DEVAPISTYLEFORUM, APINAME.GETSTYLEFORUMPERSONALIZEDFEEDS,init.Configurations);
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("xid", IDPTestsHelper.XID);
		service.URL = apiUtilities.prepareparameterizedURL(service.URL, new String[] {IDPTestsHelper.UIDX});
		return new RequestGenerator(service,headers);
	}
	
	/*public RequestGenerator postQuestion(String email, String password)
	{
	
	}*/
}
