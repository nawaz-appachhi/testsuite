package com.myntra.apiTests.portalservices.shortenurl;

import com.myntra.apiTests.portalservices.commons.CommonUtils;
import org.apache.log4j.Logger;


import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.RequestGenerator;


public class ShortenURLServiceHelper extends CommonUtils
{
	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(ShortenURLServiceHelper.class);
	APIUtilities apiUtil=new APIUtilities();
	
	private static void printAndLog(String Message, String Param)
	{
		System.out.println(Message+Param);
		log.info(Message+Param);
	}
	
	public RequestGenerator shortenUrlWithPayload(String url, String alias)
	{
		MyntraService service = null;
		//Myntra.getService(ServiceType.PORTAL_SHORTENURL, APINAME.CREATESHORTURL,init.Configurations,new String[]{url,alias});
//		printAndLog("Create Short Code with payload - Service URL : ",service.URL);
//		printAndLog("Create Short Code with payload - Service Payload : ", service.Payload);
		return new RequestGenerator (service);
	}
	
	public RequestGenerator shortenUrlWithQueryParams(String url, String alias)
	{
		MyntraService service =null; 
//				Myntra.getService(ServiceType.PORTAL_SHORTENURL, APINAME.CREATESHORTURLFROMQUERYPARAMS,init.Configurations,new String[]{url,alias});
//		service.URL = apiUtil.prepareparameterizedURL(service.URL, new String[]{url,alias});
//		printAndLog("Create Short Code with Query Parameters - Service URL : ",service.URL);
//		printAndLog("Create Short Code with Query Parameters - Service Payload : ", service.Payload);
		return new RequestGenerator (service);
	}
	
	public RequestGenerator getURLfromShortCode (String code)
	{
		MyntraService service = null;
//		Myntra.getService(ServiceType.PORTAL_SHORTENURL, APINAME.GETURLFROMSHORTURLCODE,init.Configurations,new String[]{code});
//		service.URL = apiUtil.prepareparameterizedURL(service.URL, new String[]{code});
//		printAndLog("Get URL from Short Code - Service URL : ",service.URL);
		return new RequestGenerator (service);
	}
	}
	
	
	

