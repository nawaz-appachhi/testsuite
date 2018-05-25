package com.myntra.apiTests.portalservices.fashionCorner;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.MultivaluedMap;

import com.myntra.apiTests.common.Myntra;
import com.myntra.apiTests.portalservices.commons.CommonUtils;
import org.apache.log4j.Logger;

import com.myntra.lordoftherings.Initialize;
import com.myntra.apiTests.common.APINAME;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.apiTests.common.ServiceType;

/**
 * @author manu.c
 * 
 */
public class FashionCornerTestsHelper extends CommonUtils
{
	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(FashionCornerTestsHelper.class);
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
	
}
