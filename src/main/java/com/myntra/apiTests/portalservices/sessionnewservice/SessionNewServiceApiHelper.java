/**
 * 
 */
package com.myntra.apiTests.portalservices.sessionnewservice;

import com.myntra.apiTests.SERVICE_TYPE;
import com.myntra.apiTests.common.APINAME;
import com.myntra.apiTests.common.Myntra;
import com.myntra.apiTests.common.ServiceType;
import com.myntra.apiTests.common.Constants.Headers;
import com.myntra.apiTests.erpservices.Constants;
import com.myntra.apiTests.erpservices.CustomExceptions.SCMExceptions;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.PayloadType;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.test.commons.service.HTTPMethods;
import com.myntra.test.commons.service.HttpExecutorService;
import com.myntra.test.commons.service.Svc;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

/**
 * @author shankara.c
 *
 */
public class SessionNewServiceApiHelper
{
	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(SessionNewServiceApiHelper.class);
	
	public RequestGenerator createNewUserSession(String email, String firstName, String lastName, String phoneNumber)
	{
		MyntraService service = Myntra.getService(ServiceType.PORTAL_SESSIONNEW, APINAME.CREATENEWSESSION, init.Configurations, new String[] { email, firstName,
						lastName, phoneNumber }, PayloadType.JSON, PayloadType.JSON);
		
		System.out.println("\nPrinting SessionNewService createNewSession API URL : "+service.URL);
		log.info("\nPrinting SessionNewService createNewSession API URL : "+service.URL);
		
		System.out.println("\nPrinting SessionNewService createNewSession API Payload : \n\n"+service.Payload);
		log.info("\nPrinting SessionNewService createNewSession API Payload : \n\n"+service.Payload);
		
		return new RequestGenerator(service);
	}
	
	
	public RequestGenerator getUserSessionInfo(String xId)
	{
		MyntraService getSessionInfoService = Myntra.getService(ServiceType.PORTAL_SESSIONNEW, APINAME.GETNEWSESSION, init.Configurations, PayloadType.JSON,
				new String[] { xId }, PayloadType.JSON);
		
		System.out.println("\nPrinting SessionNewService getSessionInfo API URL : "+getSessionInfoService.URL);
		log.info("\nPrinting SessionNewService getSessionInfo API URL : "+getSessionInfoService.URL);
		
		System.out.println("\nPrinting SessionNewService getSessionInfo API Payload : \n\n"+getSessionInfoService.Payload);
		log.info("\nPrinting SessionNewService getSessionInfo API Payload : \n\n"+getSessionInfoService.Payload);
		
		return new RequestGenerator(getSessionInfoService);
	}
	
	public RequestGenerator changeUserSessionInfo(String email, String firstName, String lastName, String phoneNumber, String xId, String expiryTime, 
			String lastAccessedTime, String lastModifiedTime)
	{
		MyntraService changeSessionInfoService = Myntra.getService(ServiceType.PORTAL_SESSIONNEW, APINAME.CHANGESESSIONINFO, init.Configurations, new String[] { email,
				firstName, lastName, phoneNumber }, new String[] { xId }, PayloadType.JSON, PayloadType.JSON);
		
		System.out.println("\nPrinting SessionNewService changeSessionInfo API URL : "+changeSessionInfoService.URL);
		log.info("\nPrinting SessionNewService changeSessionInfo API URL : "+changeSessionInfoService.URL);
		
		System.out.println("\nPrinting SessionNewService changeSessionInfo API Payload : \n\n"+changeSessionInfoService.Payload);
		log.info("\nPrinting SessionNewService changeSessionInfo API Payload : \n\n"+changeSessionInfoService.Payload);
		
		HashMap<String, String> changeSessionInfoHeaders = new HashMap<String, String>();
		changeSessionInfoHeaders.put("XED", expiryTime);
		changeSessionInfoHeaders.put("XLA", lastAccessedTime);
		changeSessionInfoHeaders.put("XLM", lastModifiedTime);
		
		return new RequestGenerator(changeSessionInfoService, changeSessionInfoHeaders);
	}
	
	public RequestGenerator saveUserSessionInfo(String email, String firstName, String lastName, String phoneNumber, String xId, String expiryTime, 
			String lastAccessedTime, String lastModifiedTime)
	{
		MyntraService saveSessionInfoService = Myntra.getService(ServiceType.PORTAL_SESSIONNEW, APINAME.SAVESESSIONINFO, init.Configurations, new String[] { email,
				firstName, lastName, phoneNumber }, new String[] { xId }, PayloadType.JSON, PayloadType.JSON);
		
		System.out.println("\nPrinting SessionNewService saveSessionInfo API URL : "+saveSessionInfoService.URL);
		log.info("\nPrinting SessionNewService saveSessionInfo API URL : "+saveSessionInfoService.URL);
		
		System.out.println("\nPrinting SessionNewService saveSessionInfo API Payload : \n\n"+saveSessionInfoService.Payload);
		log.info("\nPrinting SessionNewService saveSessionInfo API Payload : \n\n"+saveSessionInfoService.Payload);
		
		HashMap<String, String> saveSessionInfoHeaders = new HashMap<String, String>();
		saveSessionInfoHeaders.put("XED", expiryTime);
		saveSessionInfoHeaders.put("XLA", lastAccessedTime);
		saveSessionInfoHeaders.put("XLM", lastModifiedTime);
		
		return new RequestGenerator(saveSessionInfoService, saveSessionInfoHeaders);
	}

	public Svc getSessionDetailsBySessionID(String sessionID) throws UnsupportedEncodingException {
		Svc service = HttpExecutorService.executeHttpService(Constants.SESSION_PATH.GETNEWSESSION, new String[]{ sessionID }, SERVICE_TYPE.SESSION_SVC.toString(),
				HTTPMethods.GET, null, Headers.getSessionHeader());
		return service;
	}

	/**
	 * Update Session with Address ID
	 * @param xid
	 * @param addressID
	 * @throws ParseException
	 * @throws UnsupportedEncodingException
	 * @throws SCMExceptions
	 */
	public void updateSession(String xid, String addressID) throws ParseException, UnsupportedEncodingException, SCMExceptions {
		//Get Session
		Svc service = getSessionDetailsBySessionID(xid);
		JSONParser parser = new JSONParser();
		JSONObject jsonObj = (JSONObject) parser.parse(service.getResponseBody());
		jsonObj.put("payment_address", addressID);
		if(!jsonObj.containsKey("shipping.shippingMethod"))
			jsonObj.put("shipping.shippingMethod", "NORMAL");

		String newResp = jsonObj.toString();

		// Update session details
		Svc updateSession = HttpExecutorService.executeHttpService(Constants.SESSION_PATH.UPDATESESSION, new String[]{ xid }, SERVICE_TYPE.SESSION_SVC.toString(),
				HTTPMethods.POST, newResp, Headers.getSessionHeader());
		log.debug(updateSession.getResponseBody());
		if(updateSession.getResponseStatus() != 200){
			throw new SCMExceptions("Unable to Update Session");
		}
	}

}
