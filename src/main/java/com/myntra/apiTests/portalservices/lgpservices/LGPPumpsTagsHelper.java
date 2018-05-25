package com.myntra.apiTests.portalservices.lgpservices;
import java.util.HashMap;
import java.util.Iterator;

import com.myntra.apiTests.common.Myntra;
import com.myntra.apiTests.portalservices.commons.CommonUtils;
import com.myntra.lordoftherings.Initialize;
import com.myntra.apiTests.common.APINAME;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.apiTests.common.ServiceType;

public class LGPPumpsTagsHelper extends CommonUtils {
	Initialize init = new Initialize("/Data/configuration");
	APIUtilities apiUtil=new APIUtilities();
	String AuthorizationKey = "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0=";
	HashMap<String, String> headers = new HashMap<String,String>();
	String createTagPayload;
	
	public LGPPumpsTagsHelper()
	{
		System.out.println("IN CONSTRUCTOR");
		headers.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0=");
		headers.put("Accept", "application/json");
		headers.put("Content-Type", "application/json");
		System.out.println("ALL HEADERS SET");
	}
	
	public String createPayload(String name, String type)
	{
		String payload = "{\"name\":\""+name+"\",\"type\":\""+type+"\"}";
		return payload;
	}
	
	public RequestGenerator createSingleTag(String name, String type) {
		System.out.println("HEADER VALUE : "+headers);
		MyntraService service = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.LGPTAGSCREATETAGSINBULK,init.Configurations,new String[] {createPayload(name,type)});
		System.out.println("Create Tag Service URL : "+service.URL);
		System.out.println("Create Tag Service Payload : "+service.Payload);
		return new RequestGenerator(service,headers);
	}
	
	public RequestGenerator createMultipleTags(HashMap<String, String> Tags)
	{
		
		Iterator map = Tags.keySet().iterator();
		int i = 0;
		String payload = null;
		
		
		while(map.hasNext())
		{
			String name = map.next().toString();
			//String type = Tags.get(map.next()).toString();
			String type = Tags.get(name).toString();
			System.out.println("NAME : "+name);
			System.out.println("TYPE : "+type);
			
			if(i==0)
			{
				payload = createPayload(name,type).toString();
				System.out.println("Payload : "+payload);
			}
			else
			{
				payload=payload+","+createPayload(name,type);
			}
			
		}		
		
		//MyntraService service = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.LGPTAGSFETCHBYTYPE,init.Configurations,payload);
		MyntraService service = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.LGPTAGSCREATETAGSINBULK,init.Configurations,payload);


		System.out.println("Final Payload : "+service.Payload);
		return new RequestGenerator(service,headers);

	}
	
	public RequestGenerator findTagByName(String name)
	{
		MyntraService service = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.LGPTAGSFINDTAGBYNAME,init.Configurations);
		service.URL = apiUtil.prepareparameterizedURL(service.URL, new String[]{name});
		System.out.println(service.URL);
		return new RequestGenerator(service,headers);
	}
	
	public RequestGenerator fetchByType(String type) {
		MyntraService service = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.LGPTAGSFETCHBYTYPE,init.Configurations);
		service.URL = apiUtil.prepareparameterizedURL(service.URL, new String[]{type});
		System.out.println(service.URL);
		return new RequestGenerator(service,headers);
	}
	
	public RequestGenerator fetchByKey(String type, String name) {
		MyntraService service = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.LGPTAGSFETCHBYKEY,init.Configurations);
		service.URL = apiUtil.prepareparameterizedURL(service.URL, new String[]{type+":"+name});
		System.out.println(service.URL);
		return new RequestGenerator(service,headers);
	}
	
}