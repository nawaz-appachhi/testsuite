package com.myntra.apiTests.portalservices.inlineContent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.MultivaluedMap;

import com.myntra.apiTests.common.Myntra;
import com.myntra.apiTests.portalservices.commons.CommonUtils;
import org.apache.log4j.Logger;

import com.jayway.jsonpath.JsonPath;
import com.myntra.lordoftherings.Initialize;
import com.myntra.apiTests.common.APINAME;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.PayloadType;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.apiTests.common.ServiceType;

public class InlineContentHelper extends CommonUtils {
	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(InlineContentHelper.class);
	static String xID, sXid;
	static String prevUser, prevPass;
	APIUtilities apiUtil = new APIUtilities();
	
	/**
	 * This method is used to get the XID Token based on valid user logins.
	 * 
	 * @param userName
	 * @param password
	 */
	public HashMap<String,String> getXIDandSXidHeader(String userName, String password)
	{
		HashMap<String, String> xidAndsxId=new HashMap<String, String>();
		System.out.println("\nPrinting \n Username : "+userName+" \n Password : "+password);
		log.info("\nPrinting \n Username :"+userName+" \n Password: "+password);
		
		MyntraService serviceSignIn = Myntra.getService(ServiceType.PORTAL_IDP, APINAME.SIGNIN, init.Configurations, new String[]{ userName, password });
		System.out.println("\nPrinting IDP Service API URL : "+serviceSignIn.URL);
		log.info("\nPrinting IDP Service API URL : "+serviceSignIn.URL);
		
		System.out.println("\nPrinting IDP Service API Payload : \n\n"+serviceSignIn.Payload);
		log.info("\nPrinting IDP Service API Payload : \n\n"+serviceSignIn.Payload);
		
		RequestGenerator reqSignIn = new RequestGenerator(serviceSignIn);
		System.out.println("\nPrinting IDP Service API response .....\n\n"+reqSignIn.respvalidate.returnresponseasstring());
		log.info("\nPrinting IDP Service API response .....\n\n"+reqSignIn.respvalidate.returnresponseasstring());
		
		MultivaluedMap<String, Object> map = reqSignIn.response.getHeaders();
		for (Map.Entry entry : map.entrySet())
		{
			if (entry.getKey().toString().equalsIgnoreCase("xid"))
				xID = entry.getValue().toString();
		}
		System.out.println("\nPrinting xID from Headers  : "+xID);
		log.info("\nPrinting xID from Headers  : "+xID);
		
		xID = xID.substring((xID.indexOf("[") + 1), xID.lastIndexOf("]"));
		
		System.out.println("\nPrinting final xID : "+xID);
		log.info("\nPrinting final xID : "+xID);
		sXid = reqSignIn.respvalidate.GetNodeTextUsingIndex("xsrfToken");
		
		if(sXid.contains("'"))
			sXid = sXid.substring(sXid.indexOf("'")+1, sXid.lastIndexOf("'"));
		else
			sXid = sXid.substring(sXid.indexOf("[")+1, sXid.lastIndexOf("]"));
		System.out.println("\nPrinting final sxid : " + sXid);
		log.info("\nPrinting final sxid : " + sXid);
		xidAndsxId.put("xid", xID);
		xidAndsxId.put("X-CSRF-Token", sXid);
		
		return xidAndsxId;
	}
	
	public  String extractJsonPath(String jsonString,String jsonPath){
		Object jsonResult=JsonPath.read(jsonString,jsonString);
		if(null==jsonResult){
			return "NULL";
		}else if(jsonResult instanceof List && ((List<?>)jsonResult).isEmpty()){
			return "NULL";
		}else{
			return jsonResult.toString();
		}
		
		
	}
	public String getInlineContentId(){
		String id="";
		MyntraService service = Myntra.getService(ServiceType.PORTAL_INLINECONTENT, APINAME.CREATEICADMIN, init.Configurations, new String[]{"sabyasachi.mishra@myntra.com","green","male","Adidas","Shoes","Sports"},PayloadType.JSON,PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		String jsonResponse = req.respvalidate.returnresponseasstring();
		if(req.response.getStatus()==200){
		 id=req.respvalidate.GetNodeValue("data.id", jsonResponse).replaceAll("\"", "");
		}
		
		return id;
	}


}
