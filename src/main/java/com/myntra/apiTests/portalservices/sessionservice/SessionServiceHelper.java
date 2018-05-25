package com.myntra.apiTests.portalservices.sessionservice;

import com.myntra.apiTests.common.APINAME;
import com.myntra.apiTests.common.Myntra;
import com.myntra.apiTests.common.ServiceType;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.PayloadType;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import org.testng.log4testng.Logger;

import javax.ws.rs.core.MultivaluedMap;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


/**
 * @author shankara.c
 *
 */
public class SessionServiceHelper 
{
	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(SessionServiceHelper.class);
	APIUtilities apiUtil = new APIUtilities();


	/**
	 * This method is used to invoke getSession API
	 * 
	 * @param xId
	 * @return RequestGenerator
	 */
	public RequestGenerator perfomGetSessionOperation(String xId)
	{
		MyntraService getSessionService = Myntra.getService(ServiceType.PORTAL_SESSION, APINAME.GETSESSION, init.Configurations,
				PayloadType.XML, new String[] { xId }, PayloadType.XML);
		
		System.out.println("\nPrinting SessionService getSession API URL : "+getSessionService.URL);
		log.info("\nPrinting SessionService getSession API URL : "+getSessionService.URL);
		
		System.out.println("\nPrinting SessionService getSession API Payload : \n\n"+getSessionService.Payload);
		log.info("\nPrinting SessionService getSession API Payload : \n\n"+getSessionService.Payload);
		
		return new RequestGenerator(getSessionService);
	}
	
	/**
	 * This method is used to invoke updateSession API
	 * 
	 * @param creationDate
	 * @param lastAccessedDate
	 * @param lastModifiedDate
	 * @param expiryDate
	 * @param loginId
	 * @param xId
	 * @return RequestGenerator
	 */
	public RequestGenerator perfomUpdateSessionOperation(String creationDate, String lastAccessedDate,  String lastUpdatedDate, String lastModifiedDate, 
			String expiryDate, String loginId, String xId)
	{
		String[] payloadParams = new String[] { creationDate, lastAccessedDate, lastUpdatedDate, lastModifiedDate, expiryDate };
		String finalPayload = getPayloadAsString ("updatesession", loginId, payloadParams, xId);
		MyntraService updateSessionService = Myntra.getService(ServiceType.PORTAL_SESSION, APINAME.UPDATESESSION, init.Configurations, finalPayload);
		updateSessionService.payloaddataformat = PayloadType.XML;
		updateSessionService.responsedataformat = PayloadType.XML;
		updateSessionService.URL = apiUtil.prepareparameterizedURL(updateSessionService.URL, xId);
		
		System.out.println("\nPrinting SessionService updateSession API URL : "+updateSessionService.URL);
		log.info("\nPrinting SessionService updateSession API URL : "+updateSessionService.URL);
		
		System.out.println("\nPrinting SessionService updateSession API Payload : \n\n"+updateSessionService.Payload);
		log.info("\nPrinting SessionService updateSession API Payload : \n\n"+updateSessionService.Payload);
		
		HashMap<String, String> headersMap = new HashMap<String, String>();
		headersMap.put("Content-Type", "Application/json");

		return new RequestGenerator(updateSessionService, headersMap);
	}
	
	/**
	 * This method is used to invoke createSession API
	 * 
	 * @param creationDate
	 * @param lastAccessedDate
	 * @param lastModifiedDate
	 * @param expiryDate
	 * @param loginId
	 * @param xId
	 * @return RequestGenerator
	 * 
	 */
	public RequestGenerator performCreateSessionOperation(String creationDate, String lastAccessedDate, String lastUpdatedDate, String lastModifiedDate,
			String expiryDate, String loginId, String xId) 
	{
		String[] PayloadParams = new String[] { creationDate, lastAccessedDate, lastUpdatedDate, lastModifiedDate, expiryDate };
		String finalPayload = getPayloadAsString ("createsession", loginId, PayloadParams, xId);
		MyntraService createSessionService = Myntra.getService(ServiceType.PORTAL_SESSION, APINAME.CREATESESSION, init.Configurations, finalPayload);
		createSessionService.payloaddataformat = PayloadType.XML;
		createSessionService.responsedataformat = PayloadType.XML;
		createSessionService.URL = apiUtil.prepareparameterizedURL(createSessionService.URL, xId);
		
		System.out.println("\nPrinting SessionService createSession API URL : "+createSessionService.URL);
		log.info("\nPrinting SessionService createSession API URL : "+createSessionService.URL);
		
		System.out.println("\nPrinting SessionService createSession API Payload : \n\n"+createSessionService.Payload);
		log.info("\nPrinting SessionService createSession API Payload : \n\n"+createSessionService.Payload);
		
		HashMap<String, String> headersMap = new HashMap<String, String>();
		headersMap.put("Content-Type", "Application/json");

		return new RequestGenerator(createSessionService, headersMap);
	}
	
	/**
	 * This method is used to invoke IDPService signIn API to get sessionId
	 * 
	 * @param userName
	 * @param passWord
	 * @return
	 */
	public String performGetXId(String userName, String passWord)
	{	
		String sessionId = null;
		
		MyntraService signInService = Myntra.getService(ServiceType.PORTAL_IDP,
				APINAME.SIGNIN, init.Configurations, new String[]{ userName, passWord });
		System.out.println("\n Printing IDPService signIn API URL : "+signInService.URL);
		log.info("\n Printing IDPService signIn API URL : "+signInService.URL);
	
		System.out.println("\n Printing IDPService signIn API Payload : \n\n"+signInService.Payload);
		log.info("\n Printing IDPService signIn API Payload : \n\n"+signInService.Payload);
		
		RequestGenerator signInReqGen = new RequestGenerator(signInService);
		System.out.println("\n Printing IDPService API response : \n\n"+signInReqGen.respvalidate.returnresponseasstring());
		log.info("\n Printing IDPService signIn API response : \n\n"+signInReqGen.respvalidate.returnresponseasstring());
	
		MultivaluedMap<String, Object> map = signInReqGen.response.getHeaders();
		for (Map.Entry<String, List<Object>> entry : map.entrySet())
		{
			if (entry.getKey().toString().equalsIgnoreCase("xid"))
				sessionId = entry.getValue().toString();
		}
		System.out.println("\nPrinting xID from Headers  : "+sessionId);
		log.info("\nPrinting xID from Headers  : "+sessionId);
		
		sessionId = sessionId.substring((sessionId.indexOf("[") + 1), sessionId.lastIndexOf("]"));
		
		System.out.println("\nPrinting final xID : "+sessionId);
		log.info("\nPrinting final xID : "+sessionId);
		
		System.out.println("\nPrinting IDP Service response : "+signInReqGen.response);
		log.info("\nPrinting IDP Service response : "+signInReqGen.response);
		
		return sessionId;
	}

	/**
	 * This method is used to modify the sessionId
	 * 
	 * @param userName
	 * @param passWord
	 * @return
	 */
	public String performModifyXId(String userName, String passWord)	
	{
		String sessionId = "";
		String xId = performGetXId(userName, passWord);
		char[] xIdArray = xId.toCharArray();
		System.out.println("Old xID :"+xId);
		log.info("Old xID :"+xId);
		
		for(int i = 3; i < xIdArray.length; i = i+4){
			if(xIdArray[i]=='-')
				continue;
			xIdArray[i] = 'Z';				
		}
		for(int j = 0; j < xIdArray.length; j++){
			sessionId += xIdArray[j];
		}
		System.out.println("New xID :"+sessionId);
		return sessionId;
	}
	
	private String getPayloadAsString(String payloadName, String loginid, String[] payloadparams, String xID)	
	{
		String customPayloadDir = System.getProperty("user.dir") + File.separator + "Data" + File.separator + "payloads" + File.separator + "JSON";
		StringBuffer sb = new StringBuffer();
		String finalPayload = "";
		Scanner sc = null;
		try
		{
			sc = new Scanner(new File(customPayloadDir + File.separator + payloadName));
			while (sc.hasNextLine()) sb.append(sc.nextLine() + "\n");
		} catch (FileNotFoundException e)	{
			e.printStackTrace();
		} finally {
			if(null != sc) sc.close();
		}
		finalPayload = sb.toString();
		for (int i = 1; i <= payloadparams.length; i++)		{
			finalPayload = finalPayload.replace("${" + i + "}", payloadparams[i-1]);
		}
		finalPayload = finalPayload.replace("${0}", xID);
		finalPayload = finalPayload.replace("${7}", loginid);
		return finalPayload;
	}
}
