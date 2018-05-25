package com.myntra.apiTests.portalservices.all;

import java.util.List;

import com.myntra.apiTests.common.Myntra;
import com.myntra.apiTests.dataproviders.SessionNewServiceDP;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import com.myntra.apiTests.portalservices.sessionnewservice.SessionNewServiceApiHelper;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.Toolbox;
import com.myntra.apiTests.common.APINAME;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.PayloadType;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.apiTests.common.ServiceType;

/**
 * @author shankara.c
 *
 */
public class SessionNewServiceTests extends SessionNewServiceDP
{
	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(SessionNewServiceTests.class);
	static SessionNewServiceApiHelper sessionNewServiceApiHelper = new SessionNewServiceApiHelper();

	@Test(groups = {"Smoke","Sanity","ProdSanity","MiniRegression","Regression","ExhaustiveRegression"}, dataProvider = "createNewSessionDataProvider",description="1. Hit create session API with valid combinations of email, first_name, last_name and phone number. \n 2. Verify 200 response from service")
	public void SessionNewService_createNewSession_verifySuccessResponse(
			String email, String first_name, String last_name,
			String phoneNumber) {
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_SESSIONNEW, APINAME.CREATENEWSESSION,
				init.Configurations, new String[] { email, first_name,
						last_name, phoneNumber }, PayloadType.JSON,
				PayloadType.JSON);

		System.out.println("Service URL :" + service.URL);
		System.out.println("Payload : " + service.Payload);

		RequestGenerator req = new RequestGenerator(service);
		System.out.println("Response :" + req.returnresponseasstring());

		String xid = req.returnresponseasstring();
		System.out.println("XID collected in response is :" + xid);

		AssertJUnit.assertEquals("Session Creation API response is not 200 ",
				200, req.response.getStatus());
	}

	private String createNewSession(String email, String first_name,
			String last_name, String phoneNumber) {
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_SESSIONNEW, APINAME.CREATENEWSESSION,
				init.Configurations, new String[] { email, first_name,
						last_name, phoneNumber }, PayloadType.JSON,
				PayloadType.JSON);

		RequestGenerator req = new RequestGenerator(service);

		String XID = req.returnresponseasstring();
		System.out.println("XID collected in response is :" + XID);

		return XID;
	}

	
	@Test(groups = {"Smoke","Sanity","ProdSanity","MiniRegression","Regression","ExhaustiveRegression"}, dataProvider = "createNewSessionDataProvider",description="1. create session with valid combinations of email, first_name, last_name and phone number. \n 2. Hit Get session API. \n 3. Verify 200 response from service")
	public void SessionNewService_getNewSession_verifySuccessResponse(
			String email, String first_name, String last_name,
			String phoneNumber) {
		
		String xid = createNewSession(email, first_name, last_name, phoneNumber);

		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_SESSIONNEW, APINAME.GETNEWSESSION,
				init.Configurations, PayloadType.JSON, new String[] { xid },
				PayloadType.JSON);

		RequestGenerator req = new RequestGenerator(service);
		
		System.out.println("Service URL :" + service.URL);
		System.out.println("Payload : " + service.Payload);
		System.out.println("Response :" + req.returnresponseasstring());
		

		AssertJUnit.assertEquals("Get Session API is not working", 200,
				req.response.getStatus());
	}
	
	// Create Session - Modify XID - Get session
	@Test(groups = {"Sanity","ProdSanity","MiniRegression","Regression","ExhaustiveRegression"}, dataProvider = "createNewSessionDataProvider",description="1. Create session with valid combinations of email, first_name, last_name and phone number. \n 2. Modify the xid to a invalid xid got from step 1. \n 3. Verify 404 response from service")
	public void SessionNewService_getInvalidSession_verifyResponse(
			String email, String first_name, String last_name,
			String phoneNumber) {
		
		String xid = createNewSession(email, first_name, last_name, phoneNumber);

		String newXID = performModifyXID (xid);
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_SESSIONNEW, APINAME.GETNEWSESSION,
				init.Configurations, PayloadType.JSON, new String[] { newXID },
				PayloadType.JSON);

		RequestGenerator req = new RequestGenerator(service);
		
		System.out.println("Service URL :" + service.URL);
		System.out.println("Payload : " + service.Payload);
		System.out.println("Response :" + req.returnresponseasstring());
		

		AssertJUnit.assertEquals("Response status is not 404 ", 404,
				req.response.getStatus());
	}
	
	private String performModifyXID(String xid)
	{
		
		String sessionId = "";
		char[] xIDArray = xid.toCharArray();
		System.out.println("Old XID :" +xid);
		for (int i =3; i < xIDArray.length; i = i+4)
		{
			if (xIDArray[i] == '-')
				continue;
			xIDArray[i] = 'Z';
		}
		
		for (int j=0; j<xIDArray.length; j++)
			sessionId += xIDArray[j];
		System.out.println("New XID :" +sessionId);
		return sessionId;
	}
	
	@Test(groups = { "Sanity","Prodanity", "MiniRegression", "Regression", "ExhaustiveRegression" }, dataProvider = "SessionNewServiceDP_changeUserSessionInfo_verifyResponse",description="1. Create new session with valid combinations of email, first_name, last_name and phone number. \n 2. Change the session info to valid values \n 3. Verify 200 response from service")
	public void SessionNewService_changeUserSessionInfo_verifyResponse(String email, String first_name, String last_name, String phoneNumber, String expiryTime, 
			String lastAccessedTime, String lastModifiedTime) 
	{
		RequestGenerator createNewSessionReqGen = sessionNewServiceApiHelper.createNewUserSession(email, first_name, last_name, phoneNumber);
		String createNewSessionResponse = createNewSessionReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting SessionNewService createNewSession API response :\n\n"+createNewSessionResponse);
		log.info("\nPrinting SessionNewService createNewSession API response :\n\n"+createNewSessionResponse);
		
		AssertJUnit.assertEquals("SessionNewService createNewSession API is not working", 200, createNewSessionReqGen.response.getStatus());
		
		RequestGenerator changeUserSessionInfoReqGen = sessionNewServiceApiHelper.changeUserSessionInfo(email, first_name, last_name, phoneNumber,
				createNewSessionResponse, expiryTime, lastAccessedTime, lastModifiedTime);
		String changeUserSessionInfoResponse = changeUserSessionInfoReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting SessionNewService changeUserSessionInfo API response :\n\n"+changeUserSessionInfoResponse+"\n");
		log.info("\nPrinting SessionNewService changeUserSessionInfo API response :\n\n"+changeUserSessionInfoResponse+"\n");
		
		AssertJUnit.assertEquals("SessionNewService changeUserSessionInfo API is not working", 200, changeUserSessionInfoReqGen.response.getStatus());
	}
	
	
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" }, dataProvider = "SessionNewServiceDP_changeUserSessionInfo_verifyFailureResponse",description="1. Create new session with valid combinations of email, first_name, last_name and phone number. \n 2. Change the session info to invalid values \n 3. Verify 500 response from service")
	public void SessionNewService_changeUserSessionInfo_verifyFailureResponse(String email, String first_name, String last_name, String phoneNumber, String sessionId, 
			String expiryTime, String lastAccessedTime, String lastModifiedTime) 
	{
		RequestGenerator changeUserSessionInfoReqGen = sessionNewServiceApiHelper.changeUserSessionInfo(email, first_name, last_name, phoneNumber,
				sessionId, expiryTime, lastAccessedTime, lastModifiedTime);
		String changeUserSessionInfoResponse = changeUserSessionInfoReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting SessionNewService changeUserSessionInfo API response :\n\n"+changeUserSessionInfoResponse+"\n");
		log.info("\nPrinting SessionNewService changeUserSessionInfo API response :\n\n"+changeUserSessionInfoResponse+"\n");
		
		AssertJUnit.assertEquals("SessionNewService changeUserSessionInfo API is not working", 500, changeUserSessionInfoReqGen.response.getStatus());
	}
	
	@Test(groups = { "Sanity", "ProdSanity","MiniRegression", "Regression", "ExhaustiveRegression" }, dataProvider = "SessionNewServiceDP_saveUserSessionInfo_verifyResponse",description="1. Create new session with valid combinations of email, first_name, last_name and phone number. \n 2. Add more info in session \n 3. Verify 200 response from service")
	public void SessionNewService_saveUserSessionInfo_verifyResponse(String email, String first_name, String last_name, String phoneNumber, String expiryTime, 
			String lastAccessedTime, String lastModifiedTime) 
	{
		RequestGenerator createNewSessionReqGen = sessionNewServiceApiHelper.createNewUserSession(email, first_name, last_name, phoneNumber);
		String createNewSessionResponse = createNewSessionReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting SessionNewService createNewSession API response :\n\n"+createNewSessionResponse+"\n");
		log.info("\nPrinting SessionNewService createNewSession API response :\n\n"+createNewSessionResponse+"\n");
		
		AssertJUnit.assertEquals("SessionNewService createNewSession API is not working", 200, createNewSessionReqGen.response.getStatus());
		
		RequestGenerator saveUserSessionInfoReqGen = sessionNewServiceApiHelper.saveUserSessionInfo(email, first_name, last_name, phoneNumber,
				createNewSessionResponse, expiryTime, lastAccessedTime, lastModifiedTime);
		String saveUserSessionInfoResponse = saveUserSessionInfoReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting SessionNewService saveUserSessionInfo API response :\n\n"+saveUserSessionInfoResponse+"\n");
		log.info("\nPrinting SessionNewService saveUserSessionInfo API response :\n\n"+saveUserSessionInfoResponse+"\n");
		
		AssertJUnit.assertEquals("SessionNewService saveUserSessionInfo API is not working", 200, saveUserSessionInfoReqGen.response.getStatus());
	}
	
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" }, dataProvider = "SessionNewServiceDP_saveUserSessionInfo_verifyFailureResponse",description="1. Create new session with valid combinations of email, first_name, last_name and phone number. \n 2. Add invalid info in session \n 3. Verify 404 response from service")
	public void SessionNewService_saveUserSessionInfo_verifyFailureResponse(String email, String first_name, String last_name, String phoneNumber, String sessionId,
			String expiryTime, String lastAccessedTime, String lastModifiedTime) 
	{
		RequestGenerator saveUserSessionInfoReqGen = sessionNewServiceApiHelper.saveUserSessionInfo(email, first_name, last_name, phoneNumber,
				sessionId, expiryTime, lastAccessedTime, lastModifiedTime);
		String saveUserSessionInfoResponse = saveUserSessionInfoReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting SessionNewService saveUserSessionInfo API response :\n\n"+saveUserSessionInfoResponse+"\n");
		log.info("\nPrinting SessionNewService saveUserSessionInfo API response :\n\n"+saveUserSessionInfoResponse+"\n");
		
		AssertJUnit.assertEquals("SessionNewService saveUserSessionInfo API is not working", 404, saveUserSessionInfoReqGen.response.getStatus());
	}
	
	
	@Test(groups = { "SchemaValidation" },  dataProvider = "SessionNewServiceDP_getNewSession_verifyResponseDataNodesUsingSchemaValidations")
	public void SessionNewService_getSessionInfo_verifyResponseDataNodesUsingSchemaValidations(String email, String first_name, String last_name, String phoneNumber) 
	{
		RequestGenerator createNewSessionReqGen = sessionNewServiceApiHelper.createNewUserSession(email, first_name, last_name, phoneNumber);
		String createNewSessionResponse = createNewSessionReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting SessionNewService createNewSession API response :\n\n"+createNewSessionResponse+"\n");
		log.info("\nPrinting SessionNewService createNewSession API response :\n\n"+createNewSessionResponse+"\n");
		
		AssertJUnit.assertEquals("SessionNewService createNewSession API is not working", 200, createNewSessionReqGen.response.getStatus());

		RequestGenerator getSessionInfoReqGen = sessionNewServiceApiHelper.getUserSessionInfo(createNewSessionResponse);
		String getSessionInfoResponse = getSessionInfoReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting SessionNewService getSessionInfo API response :\n\n"+getSessionInfoResponse);
		log.info("\nPrinting SessionNewService getSessionInfo API response :\n\n"+getSessionInfoResponse);

		AssertJUnit.assertEquals("SessionNewService getSessionInfo API is not working", 200, getSessionInfoReqGen.response.getStatus());
		
		try {
	        String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/sessionnewservice-getsessioninfo-schema.txt");
	        List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(getSessionInfoResponse, jsonschema);
	        AssertJUnit.assertTrue(missingNodeList+" nodes are missing in PageConfigurator createUpdateListOfWidgets API response", 
	        		CollectionUtils.isEmpty(missingNodeList));
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
}
