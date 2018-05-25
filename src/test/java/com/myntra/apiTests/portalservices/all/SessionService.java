package com.myntra.apiTests.portalservices.all;

import java.util.List;

import com.myntra.apiTests.portalservices.sessionservice.SessionNodes;
import com.myntra.apiTests.portalservices.sessionservice.SessionServiceHelper;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;
import org.testng.log4testng.Logger;

import com.jayway.jsonpath.JsonPath;
import com.myntra.apiTests.dataproviders.SessionServiceDP;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.RequestGenerator;

/**
 * @author shankara.c
 *
 */
public class SessionService extends SessionServiceDP
{
	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(SessionService.class);
	APIUtilities apiUtil = new APIUtilities();
	SessionServiceHelper sessionServiceHelper = new SessionServiceHelper();

	
	/**
	 * This TestCase is used to invoke SessionService getSession API and verification for success response code(200)
	 * 
	 * @param userName
	 * @param password
	 */
	@Test(groups = {  /*"Smoke", "Sanity", "ProdSanity", "MiniRegression", "Regression", "ExhaustiveRegression", "laxmiTc"*/ }, 
			dataProvider = "SessionServiceDP_getSessionDataProvider_verifySuccessResponse")
	public void SessionService_getSession_verifySuccessResponse(String userName, String password)
	{
		String xId = sessionServiceHelper.performGetXId(userName, password);
		AssertJUnit.assertNotNull("xId is null", xId);
		
		RequestGenerator getSessionServiceReqGen = sessionServiceHelper.perfomGetSessionOperation(xId);
		String getSessionServiceXMLResponse = getSessionServiceReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting SessionService getSession API response : "+getSessionServiceXMLResponse+"\n");
		log.info("\nPrinting SessionService getSession API XML response : "+getSessionServiceXMLResponse+"\n");
		
		AssertJUnit.assertEquals("SessionService getSession API is not working", 200, getSessionServiceReqGen.response.getStatus());		
	}
	
	/**
	 * This TestCase is used to invoke SessionService getSession API and validate session nodes
	 * 
	 * @param userName
	 * @param password
	 */
	@Test(groups = { /*"Sanity", "MiniRegression", "Regression", "ExhaustiveRegression"*/ }, 
			dataProvider = "SessionServiceDP_getSessionDataProvider_validateSessionNodes")
	public void SessionService_getSession_validateSessionNodes(String userName, String password)
	{
		String xId = sessionServiceHelper.performGetXId(userName, password);
		AssertJUnit.assertNotNull("xId is null", xId);
		
		RequestGenerator getSessionServiceReqGen = sessionServiceHelper.perfomGetSessionOperation(xId);
		String getSessionServiceXMLResponse = getSessionServiceReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting SessionService getSession API response in XML : "+getSessionServiceXMLResponse);
		log.info("\nPrinting SessionService getSession API response in XML : "+getSessionServiceXMLResponse);
		
		AssertJUnit.assertEquals("SessionService getSession API is not working", 200, getSessionServiceReqGen.response.getStatus());	
		
		try {
			JSONObject  getSessionServiceJSONObject = XML.toJSONObject(getSessionServiceXMLResponse);
			String getSessionServiceJSONResponse = getSessionServiceJSONObject.toString();
			System.out.println("\nPrinting SessionService getSession API response in JSON : "+getSessionServiceJSONResponse+"\n");
			log.info("\nPrinting SessionService getSession API response in JSON : "+getSessionServiceJSONResponse+"\n");
			
			List<String> sessionNodes = SessionNodes.getSessionNodes();
			for(String sessionNode : sessionNodes){
				AssertJUnit.assertTrue(sessionNode+" is not exists", apiUtil.Exists(sessionNode, getSessionServiceJSONResponse));	
			}
			
			AssertJUnit.assertEquals("request xid and response xid are not equal", apiUtil.GetNodeValue(SessionNodes.SESSION_ID.getNodePath(), 
					getSessionServiceJSONResponse).replace("\"", ""), xId);
			
		} catch (JSONException e) {
			System.out.println("Error while converting xml to json");
			log.error("Error while converting xml to json", e);
		}
	}
	
	/**
	 *  This TestCase is used to invoke SessionService getSession API and validate session CDATA nodes
	 * 
	 * @param userName
	 * @param password
	 */
	@Test(groups = { /*"Sanity", "MiniRegression", "Regression", "ExhaustiveRegression"*/ }, 
			dataProvider = "SessionServiceDP_getSessionDataProvider_validateSessionCDATANodes")
	public void SessionService_getSession_validateSessionCDATANodes(String userName, String password)
	{
		String xId = sessionServiceHelper.performGetXId(userName, password);
		AssertJUnit.assertNotNull("xId is null", xId);
		
		RequestGenerator getSessionServiceReqGen = sessionServiceHelper.perfomGetSessionOperation(xId);
		String getSessionServiceXMLResponse = getSessionServiceReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting SessionService getSession API response in XML : "+getSessionServiceXMLResponse);
		log.info("\nPrinting SessionService getSession API response in XML : "+getSessionServiceXMLResponse);
		
		AssertJUnit.assertEquals("SessionService getSession API is not working", 200, getSessionServiceReqGen.response.getStatus());
		
		try {
			JSONObject getSessionServiceJSONObject = XML.toJSONObject(getSessionServiceXMLResponse);
			String getSessionServiceJSONResponse = getSessionServiceJSONObject.toString();
			System.out.println("\nPrinting SessionService getSession API response in JSON : "+getSessionServiceJSONResponse+"\n");
			log.info("\nPrinting SessionService getSession API response in JSON : "+getSessionServiceJSONResponse+"\n");
			
			String sessionCDATAResponse = JsonPath.read(getSessionServiceJSONResponse, "$."+SessionNodes.SESSION_DATA.getNodePath());
			List<String> sessionNodes = SessionNodes.getSessionCDATANodes();
			for(String sessionNode : sessionNodes){
				AssertJUnit.assertTrue(sessionNode+" is not exists", apiUtil.Exists(sessionNode, sessionCDATAResponse));	
			}
			
		} catch (JSONException e) {
			System.out.println("Error while converting xml to json");
			log.error("Error while converting xml to json", e);
		}
	}
	
	/**
	 * This TestCase is used to invoke createSession API and verification for success response(200)
	 * 
	 * @param creationDate
	 * @param lastAccessedDate
	 * @param lastModifiedDate
	 * @param expiryDate
	 * @param userName
	 * @param password
	 */
	@Test( groups = {  /* "Smoke", "Sanity", "ProdSanity", "MiniRegression", "Regression", "ExhaustiveRegression", "laxmiTc" */ },
			dataProvider = "SessionServiceDP_createSessionDataProvider_verifySuccessResponse" )
	public void SessionService_createNewSessionWithXidChange_verifySuccessResponse(String creationDate, String lastAccessedDate, String lastUpdatedDate,
			String lastModifiedDate, String expiryDate, String userName, String password)	
	{
		String xId = sessionServiceHelper.performModifyXId(userName, password);
		AssertJUnit.assertNotNull("xId is null", xId);
		
		RequestGenerator createSessionReqGen = sessionServiceHelper.performCreateSessionOperation(creationDate, lastAccessedDate, lastUpdatedDate, lastModifiedDate, 
				expiryDate, userName, xId);
		System.out.println("\nPrinting SessionService createSession API response in XML : "+createSessionReqGen.returnresponseasstring());
		log.info("\nPrinting SessionService createSession API response in XML : "+createSessionReqGen+"\n");
		
		AssertJUnit.assertEquals("SessionService createSession API is not working", 200, createSessionReqGen.response.getStatus());
	}
	
	/**
	 * This TestCase is used to invoke createSession API and validate session nodes
	 * 
	 * @param creationDate
	 * @param lastAccessedDate
	 * @param lastModifiedDate
	 * @param expiryDate
	 * @param userName
	 * @param password
	 */
	@Test( groups = { /* "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression", "laxmiTc" */ },
			dataProvider = "SessionServiceDP_createSessionDataProvider_validateSessionNodes" )
	public void SessionService_createNewSessionWithXidChange_validateSessionNodes(String creationDate, String lastAccessedDate, String lastUpdatedDate, 
			String lastModifiedDate, String expiryDate, String userName, String password)
	{
		String xId = sessionServiceHelper.performModifyXId(userName, password);
		AssertJUnit.assertNotNull("xId is null", xId);
		
		RequestGenerator createSessionReqGen = sessionServiceHelper.performCreateSessionOperation(creationDate, lastAccessedDate, lastUpdatedDate, lastModifiedDate,
				expiryDate, userName, xId);
		System.out.println("\nPrinting SessionService createSession API response : "+createSessionReqGen.returnresponseasstring());
		log.info("\nPrinting SessionService createSession API response : "+createSessionReqGen.returnresponseasstring()+"\n");
		
		AssertJUnit.assertEquals("SessionService createSession API is not working", 200, createSessionReqGen.response.getStatus());
		
		RequestGenerator getSessionServiceReqGen = sessionServiceHelper.perfomGetSessionOperation(xId);
		String getSessionServiceXMLResponse = getSessionServiceReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting SessionService getSession API response in XML : "+getSessionServiceXMLResponse);
		log.info("\nPrinting SessionService getSession API response in XML : "+getSessionServiceXMLResponse);
		
		AssertJUnit.assertEquals("SessionService getSession API is not working", 200, getSessionServiceReqGen.response.getStatus());
		
		try {
			JSONObject  getSessionServiceJSONObject = XML.toJSONObject(getSessionServiceXMLResponse);
			String getSessionServiceJSONResponse = getSessionServiceJSONObject.toString();
			System.out.println("\nPrinting SessionService getSession API response in JSON : "+getSessionServiceJSONResponse+"\n");
			log.info("\nPrinting SessionService getSession API response in JSON : "+getSessionServiceJSONResponse+"\n");
			
			List<String> sessionNodes = SessionNodes.getSessionNodes();
			for(String sessionNode : sessionNodes){
				AssertJUnit.assertTrue(sessionNode+" is not exists", apiUtil.Exists(sessionNode, getSessionServiceJSONResponse));	
			}
			
			AssertJUnit.assertEquals("request xid and response xid are not equal", apiUtil.GetNodeValue(SessionNodes.SESSION_ID.getNodePath(), 
					getSessionServiceJSONResponse).replace("\"", ""), xId);
			
		} catch (JSONException e) {
			System.out.println("Error while converting xml to json");
			log.error("Error while converting xml to json", e);
		}
		
	}
	
	/**
	 * This TestCase is used to invoke createSession API and validate session CDATA nodes
	 * 
	 * @param creationDate
	 * @param lastAccessedDate
	 * @param lastModifiedDate
	 * @param expiryDate
	 * @param userName
	 * @param password
	 */
	@Test( groups = { /* "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression", "laxmiTc" */ },
			dataProvider = "SessionServiceDP_createSessionDataProvider_validateSessionCDATANodes" )
	public void SessionService_createNewSessionWithXidChange_validateSessionCDATANodes(String creationDate, String lastAccessedDate, String lastUpdatedDate,
			String lastModifiedDate, String expiryDate, String userName, String password)	
	{
		String xId = sessionServiceHelper.performModifyXId(userName, password);
		AssertJUnit.assertNotNull("xId is null", xId);
		
		RequestGenerator createSessionReqGen = sessionServiceHelper.performCreateSessionOperation(creationDate, lastAccessedDate, lastUpdatedDate, lastModifiedDate, 
				expiryDate, userName, xId);
		System.out.println("\nPrinting SessionService createSession API response : "+createSessionReqGen.returnresponseasstring());
		log.info("\nPrinting SessionService createSession API response : "+createSessionReqGen+"\n");
		
		AssertJUnit.assertEquals("SessionService createSession API is not working", 200, createSessionReqGen.response.getStatus());
		
		RequestGenerator getSessionServiceReqGen = sessionServiceHelper.perfomGetSessionOperation(xId);
		String getSessionServiceXMLResponse = getSessionServiceReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting SessionService getSession API response in XML : "+getSessionServiceXMLResponse);
		log.info("\nPrinting SessionService getSession API response in XML : "+getSessionServiceXMLResponse);
		
		AssertJUnit.assertEquals("SessionService getSession API is not working", 200, getSessionServiceReqGen.response.getStatus());
		
		try {
			JSONObject getSessionServiceJSONObject = XML.toJSONObject(getSessionServiceXMLResponse);
			String getSessionServiceJSONResponse = getSessionServiceJSONObject.toString();
			System.out.println("\nPrinting SessionService getSession API response in JSON : "+getSessionServiceJSONResponse+"\n");
			log.info("\nPrinting SessionService converted getSession API response in JSON : "+getSessionServiceJSONResponse+"\n");
			
			String sessionCDATAResponse = JsonPath.read(getSessionServiceJSONResponse, "$."+SessionNodes.SESSION_DATA.getNodePath());
			List<String> sessionNodes = SessionNodes.getSessionCDATANodes();
			for(String sessionNode : sessionNodes){
				AssertJUnit.assertTrue(sessionNode+" is not exists", apiUtil.Exists(sessionNode, sessionCDATAResponse));	
			}
			
		} catch (JSONException e) {
			System.out.println("Error while converting xml to json");
			log.error("Error while converting xml to json", e);
		}
		
	}

	/**
	 * This TestCase is used to invoke createSession API and verification for success response(200)
	 * 
	 * @param creationDate
	 * @param lastAccessedDate
	 * @param lastModifiedDate
	 * @param expiryDate
	 * @param userName
	 * @param password
	 */
	@Test( groups = {  /* "Smoke", "Sanity", "ProdSanity", "MiniRegression", "Regression", "ExhaustiveRegression", "laxmiTc" */ },
			dataProvider = "SessionServiceDP_createSessionDataProvider_verifySuccessResponse" )
	public void SessionService_createNewSessionWithNoXidChange_verifySuccessResponse(String creationDate, String lastAccessedDate, String lastUpdatedDate,
			String lastModifiedDate, String expiryDate, String userName, String password)	
	{
		String xId = sessionServiceHelper.performGetXId(userName, password);
		AssertJUnit.assertNotNull("xId is null", xId);
		
		RequestGenerator createSessionReqGen = sessionServiceHelper.performCreateSessionOperation(creationDate, lastAccessedDate, lastUpdatedDate, lastModifiedDate,
				expiryDate, userName, xId);
		System.out.println("\nPrinting SessionService createSession API response : "+createSessionReqGen.returnresponseasstring());
		log.info("\nPrinting SessionService createSession API response : "+createSessionReqGen.returnresponseasstring()+"\n");
		
		AssertJUnit.assertEquals("SessionService createSession API is not working", 200, createSessionReqGen.response.getStatus());
		
	}
	
	/**
	 * This TestCase is used to invoke createSession API and validate session nodes
	 * 
	 * @param creationDate
	 * @param lastAccessedDate
	 * @param lastModifiedDate
	 * @param expiryDate
	 * @param userName
	 * @param password
	 */
	@Test( groups = { /* "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression", "laxmiTc" */ },
			dataProvider = "SessionServiceDP_createSessionDataProvider_validateSessionNodes" )
	public void SessionService_createNewSessionWithNoXidChange_validateSessionNodes(String creationDate, String lastAccessedDate, String lastUpdatedDate,
			String lastModifiedDate, String expiryDate, String userName, String password)
	{
		String xId = sessionServiceHelper.performGetXId(userName, password);
		AssertJUnit.assertNotNull("xId is null", xId);
		
		RequestGenerator createSessionReqGen = sessionServiceHelper.performCreateSessionOperation(creationDate, lastAccessedDate, lastUpdatedDate, lastModifiedDate, 
				expiryDate, userName, xId);
		System.out.println("\nPrinting SessionService createSession API response : "+createSessionReqGen.returnresponseasstring());
		log.info("\nPrinting SessionService createSession API response : "+createSessionReqGen+"\n");
		
		AssertJUnit.assertEquals("SessionService createSession API is not working", 200, createSessionReqGen.response.getStatus());
		
		RequestGenerator getSessionServiceReqGen = sessionServiceHelper.perfomGetSessionOperation(xId);
		String getSessionServiceXMLResponse = getSessionServiceReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting SessionService getSession API response in XML : "+getSessionServiceXMLResponse);
		log.info("\nPrinting SessionService getSession API response in XML : "+getSessionServiceXMLResponse);
		
		AssertJUnit.assertEquals("SessionService getSession API is not working", 200, getSessionServiceReqGen.response.getStatus());
		
		try {
			JSONObject  getSessionServiceJSONObject = XML.toJSONObject(getSessionServiceXMLResponse);
			String getSessionServiceJSONResponse = getSessionServiceJSONObject.toString();
			System.out.println("\nPrinting SessionService getSession API response in JSON : "+getSessionServiceJSONResponse+"\n");
			log.info("\nPrinting SessionService getSession API response in JSON : "+getSessionServiceJSONResponse+"\n");
			
			List<String> sessionNodes = SessionNodes.getSessionNodes();
			for(String sessionNode : sessionNodes){
				AssertJUnit.assertTrue(sessionNode+" is not exists", apiUtil.Exists(sessionNode, getSessionServiceJSONResponse));	
			}
			
			AssertJUnit.assertEquals("request xid and response xid are not equal", apiUtil.GetNodeValue(SessionNodes.SESSION_ID.getNodePath(), 
					getSessionServiceJSONResponse).replace("\"", ""), xId);
			
		} catch (JSONException e) {
			System.out.println("Error while converting xml to json");
			log.error("Error while converting xml to json", e);
		}
		
	}
	
	/**
	 * This TestCase is used to invoke createSession API and validate session CDATA nodes
	 * 
	 * @param creationDate
	 * @param lastAccessedDate
	 * @param lastModifiedDate
	 * @param expiryDate
	 * @param userName
	 * @param password
	 */
	@Test( groups = {  /* "Smoke", "Sanity", "ProdSanity", "MiniRegression", "Regression", "ExhaustiveRegression", "laxmiTc" */ },
			dataProvider = "SessionServiceDP_createSessionDataProvider_validateSessionCDATANodes" )
	public void SessionService_createNewSessionWithNoXidChange_validateSessionCDATANodes(String creationDate, String lastAccessedDate, String lastUpdatedDate,
			String lastModifiedDate, String expiryDate, String userName, String password)	
	{
		String xId = sessionServiceHelper.performGetXId(userName, password);
		AssertJUnit.assertNotNull("xId is null", xId);
		
		RequestGenerator createSessionReqGen = sessionServiceHelper.performCreateSessionOperation(creationDate, lastAccessedDate, lastUpdatedDate, lastModifiedDate, 
				expiryDate, userName, xId);
		System.out.println("\nPrinting SessionService createSession API response : "+createSessionReqGen.returnresponseasstring());
		log.info("\nPrinting SessionService createSession API response : "+createSessionReqGen+"\n");
		
		AssertJUnit.assertEquals("SessionService createSession API is not working", 200, createSessionReqGen.response.getStatus());
		
		RequestGenerator getSessionServiceReqGen = sessionServiceHelper.perfomGetSessionOperation(xId);
		String getSessionServiceXMLResponse = getSessionServiceReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting SessionService getSession API response in XML : "+getSessionServiceXMLResponse);
		log.info("\nPrinting SessionService getSession API response in XML : "+getSessionServiceXMLResponse);
		
		AssertJUnit.assertEquals("SessionService getSession API is not working", 200, getSessionServiceReqGen.response.getStatus());
		
		try {
			JSONObject getSessionServiceJSONObject = XML.toJSONObject(getSessionServiceXMLResponse);
			String getSessionServiceJSONResponse = getSessionServiceJSONObject.toString();
			System.out.println("\nPrinting SessionService getSession API response in JSON : "+getSessionServiceJSONResponse+"\n");
			log.info("\nPrinting SessionService converted getSession API response in JSON : "+getSessionServiceJSONResponse+"\n");
			
			String sessionCDATAResponse = JsonPath.read(getSessionServiceJSONResponse, "$."+SessionNodes.SESSION_DATA.getNodePath());
			List<String> sessionNodes = SessionNodes.getSessionCDATANodes();
			for(String sessionNode : sessionNodes){
				AssertJUnit.assertTrue(sessionNode+" is not exists", apiUtil.Exists(sessionNode, sessionCDATAResponse));	
			}
			
		} catch (JSONException e) {
			System.out.println("Error while converting xml to json");
			log.error("Error while converting xml to json", e);
		}
		
	}
	
	/**
	 * This TestCase is used to invoke updateSession API and verification for success response(200)
	 * 
	 * @param creationDate
	 * @param lastAccessedDate
	 * @param lastModifiedDate
	 * @param expiryDate
	 * @param userName
	 * @param password
	 */
	@Test(groups = { /* "Smoke", "Sanity", "ProdSanity", "MiniRegression",	"Regression", "ExhaustiveRegression", "laxmiTc" */ }, 
			dataProvider = "SessionServiceDP_updateSessionDataProvider_verifySuccessResponse")
	public void SessionService_updateSession_verifySuccessResponse(String creationDate, String lastAccessedDate, String lastUpdatedDate, String lastModifiedDate, 
			String expiryDate, String userName, String password)
	{	
		String xId = sessionServiceHelper.performGetXId(userName, password);
		AssertJUnit.assertNotNull("xId is null", xId);
		
		RequestGenerator updateSessionReqGen = sessionServiceHelper.perfomUpdateSessionOperation(creationDate, lastAccessedDate, lastUpdatedDate, lastModifiedDate, 
				expiryDate, userName, xId);
		System.out.println("\nPrinting SessionService updateSession API response : "+updateSessionReqGen.returnresponseasstring()+"\n");
		log.info("\nPrinting SessionService updateSession API response : "+updateSessionReqGen.returnresponseasstring());
		
		AssertJUnit.assertEquals("SessionService updateSession API is not working", 200, updateSessionReqGen.response.getStatus());
	}
	
	/**
	 * This TestCase is used to invoke SessionService updateSession API and validate session nodes
	 * 
	 * @param creationDate
	 * @param lastAccessedDate
	 * @param lastModifiedDate
	 * @param expiryDate
	 * @param userName
	 * @param password
	 */
	@Test( groups = { /* "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression", "laxmiTc" */ },
			dataProvider = "SessionServiceDP_updateSessionDataProvider_validateSessionNodes" )
	public void SessionService_updateSession_validateSessionNodes(String creationDate, String lastAccessedDate, String lastUpdatedDate, String lastModifiedDate, 
			String expiryDate, String userName, String password)
	{	
		String xId = sessionServiceHelper.performGetXId(userName, password);
		AssertJUnit.assertNotNull("xId is null", xId);
		
		RequestGenerator updateSessionReqGen = sessionServiceHelper.perfomUpdateSessionOperation(creationDate, lastAccessedDate, lastUpdatedDate, lastModifiedDate, 
				expiryDate, userName, xId);
		System.out.println("\nPrinting SessionService updateSession API response : "+updateSessionReqGen.returnresponseasstring()+"\n");
		log.info("\nPrinting SessionService updateSession API response : "+updateSessionReqGen.returnresponseasstring());
		
		AssertJUnit.assertEquals("SessionService updateSession API is not working", 200, updateSessionReqGen.response.getStatus());
		
		RequestGenerator getSessionServiceReqGen = sessionServiceHelper.perfomGetSessionOperation(xId);
		String getSessionServiceXMLResponse = getSessionServiceReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting SessionService getSession API response in XML : "+getSessionServiceXMLResponse);
		log.info("\nPrinting SessionService getSession API response in XML : "+getSessionServiceXMLResponse);
		
		AssertJUnit.assertEquals("SessionService getSession API is not working", 200, getSessionServiceReqGen.response.getStatus());
		
		try {
			JSONObject  getSessionServiceJSONObject = XML.toJSONObject(getSessionServiceXMLResponse);
			String getSessionServiceJSONResponse = getSessionServiceJSONObject.toString();
			System.out.println("\nPrinting SessionService getSession API response in JSON : "+getSessionServiceJSONResponse+"\n");
			log.info("\nPrinting SessionService getSession API response in JSON : "+getSessionServiceJSONResponse+"\n");
			
			List<String> sessionNodes = SessionNodes.getSessionNodes();
			for(String sessionNode : sessionNodes){
				AssertJUnit.assertTrue(sessionNode+" is not exists", apiUtil.Exists(sessionNode, getSessionServiceJSONResponse));
			}
			
			AssertJUnit.assertEquals("request xid and response xid are not equal", apiUtil.GetNodeValue(SessionNodes.SESSION_ID.getNodePath(), 
					getSessionServiceJSONResponse).replace("\"", ""), xId);
			
		} catch (JSONException e) {
			System.out.println("Error while converting xml to json");
			log.error("Error while converting xml to json", e);
		}
		
	}
	
	/**
	 * This TestCase is used to invoke SessionService updateSession API and validate session CDATA nodes
	 * 
	 * @param creationDate
	 * @param lastAccessedDate
	 * @param lastModifiedDate
	 * @param expiryDate
	 * @param userName
	 * @param password
	 */
	@Test( groups = { /* "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression", "laxmiTc" */ },
			dataProvider = "SessionServiceDP_updateSessionDataProvider_validateSessionCDATANodes" )
	public void SessionService_updateSession_validateSessionCDATANodes(String creationDate, String lastAccessedDate, String lastUpdatedDate, String lastModifiedDate,
			String expiryDate, String userName, String password)
	{	
		String xId = sessionServiceHelper.performGetXId(userName, password);
		AssertJUnit.assertNotNull("xId is null", xId);
		
		RequestGenerator updateSessionReqGen = sessionServiceHelper.perfomUpdateSessionOperation(creationDate, lastAccessedDate, lastUpdatedDate, lastModifiedDate, 
				expiryDate, userName, xId);
		System.out.println("\nPrinting SessionService updateSession API response : "+updateSessionReqGen.returnresponseasstring()+"\n");
		log.info("\nPrinting SessionService updateSession API response : "+updateSessionReqGen.returnresponseasstring());
		
		AssertJUnit.assertEquals("SessionService updateSession API is not working", 200, updateSessionReqGen.response.getStatus());
		
		RequestGenerator getSessionServiceReqGen = sessionServiceHelper.perfomGetSessionOperation(xId);
		String getSessionServiceXMLResponse = getSessionServiceReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting SessionService getSession API response in XML : "+getSessionServiceXMLResponse);
		log.info("\nPrinting SessionService getSession API response in XML : "+getSessionServiceXMLResponse);
		
		AssertJUnit.assertEquals("SessionService getSession API is not working", 200, getSessionServiceReqGen.response.getStatus());
		
		try {
			JSONObject  getSessionServiceJSONObject = XML.toJSONObject(getSessionServiceXMLResponse);
			String getSessionServiceJSONResponse = getSessionServiceJSONObject.toString();
			System.out.println("\nPrinting SessionService getSession API response : "+getSessionServiceJSONResponse+"\n");
			log.info("\nPrinting SessionService getSession API response : "+getSessionServiceJSONResponse+"\n");
			
			String sessionCDATAResponse = JsonPath.read(getSessionServiceJSONResponse, "$."+SessionNodes.SESSION_DATA.getNodePath());
			List<String> sessionNodes = SessionNodes.getSessionCDATANodes();
			for(String sessionNode : sessionNodes){
				AssertJUnit.assertTrue(sessionNode+" is not exists", apiUtil.Exists(sessionNode, sessionCDATAResponse));	
			}
			
		} catch (JSONException e) {
			System.out.println("Error while converting xml to json");
			log.error("Error while converting xml to json", e);
		}
		
	}
	
	/**
	 * This TestCase is used to invoke SessionService updateSession API and validate session CreationDate
	 * 
	 * @param creationDate
	 * @param lastAccessedDate
	 * @param lastModifiedDate
	 * @param expiryDate
	 * @param userName
	 * @param passWord
	 */
	@Test( groups = { /* "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression", "laxmiTc" */ },
			dataProvider = "SessionServiceDP_updateSessionDataProvider_validateSessionCreationDate" )
	public void SessionService_updateSession_validateSessionCreationDate(String creationDate, String lastAccessedDate, String lastUpdatedDate, String lastModifiedDate,
			String expiryDate, String userName, String passWord)
	{		
		String xId = sessionServiceHelper.performGetXId(userName, passWord);
		AssertJUnit.assertNotNull("xId is null", xId);
		
		RequestGenerator updateSessionReqGen = sessionServiceHelper.perfomUpdateSessionOperation(creationDate, lastAccessedDate, lastUpdatedDate, lastModifiedDate, 
				expiryDate, userName, xId);
		System.out.println("\nPrinting SessionService updateSession API response : "+updateSessionReqGen.returnresponseasstring()+"\n");
		log.info("\nPrinting SessionService updateSession API response : "+updateSessionReqGen.returnresponseasstring());
		
		AssertJUnit.assertEquals("SessionService updateSession API is not working", 200, updateSessionReqGen.response.getStatus());
		
		RequestGenerator getSessionServiceReqGen = sessionServiceHelper.perfomGetSessionOperation(xId);
		String getSessionServiceXMLResponse = getSessionServiceReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting SessionService getSession API response in XML : "+getSessionServiceXMLResponse);
		log.info("\nPrinting SessionService getSession API response in XML : "+getSessionServiceXMLResponse);
		
		AssertJUnit.assertEquals("SessionService getSession API is not working", 200, getSessionServiceReqGen.response.getStatus());
		
		try {
			JSONObject  getSessionServiceJSONObject = XML.toJSONObject(getSessionServiceXMLResponse);
			String getSessionServiceJSONResponse = getSessionServiceJSONObject.toString();
			System.out.println("\nPrinting SessionService updateSession API response in JSON : "+getSessionServiceJSONResponse+"\n");
			log.info("\nPrinting SessionService updateSession API response in JSON : "+getSessionServiceJSONResponse+"\n");
			
			AssertJUnit.assertEquals("Failed to update sessionCreationDate", apiUtil.GetNodeValue(SessionNodes.SESSION_CREATE_DATE.getNodePath(), 
					getSessionServiceJSONResponse).replace("\"", ""), creationDate);
			
		} catch (JSONException e) {
			System.out.println("Error while converting xml to json");
			log.error("Error while converting xml to json", e);
		}
	}

	/**
	 * This TestCase is used to invoke SessionService updateSession API and validate session LastAccessedDate
	 * 
	 * @param creationDate
	 * @param lastAccessedDate
	 * @param lastModifiedDate
	 * @param expiryDate
	 * @param userName
	 * @param passWord
	 */
	@Test( groups = { /* "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression", "laxmiTc" */ },
			dataProvider = "SessionServiceDP_updateSessionDataProvider_validateLastAccessedDate" )
	public void SessionService_updateSession_validateLastAccessedDate(String creationDate, String lastAccessedDate, String lastUpdatedDate, String lastModifiedDate, 
			String expiryDate, String userName, String passWord)
	{		
		String xId = sessionServiceHelper.performGetXId(userName, passWord);
		AssertJUnit.assertNotNull("xId is null", xId);
		
		RequestGenerator updateSessionReqGen = sessionServiceHelper.perfomUpdateSessionOperation(creationDate, lastAccessedDate, lastUpdatedDate, lastModifiedDate, 
				expiryDate, userName, xId);
		System.out.println("\nPrinting SessionService updateSession API response : "+updateSessionReqGen.returnresponseasstring()+"\n");
		log.info("\nPrinting SessionService updateSession API response : "+updateSessionReqGen.returnresponseasstring());
		
		AssertJUnit.assertEquals("SessionService updateSession API is not working", 200, updateSessionReqGen.response.getStatus());
		
		RequestGenerator getSessionServiceReqGen = sessionServiceHelper.perfomGetSessionOperation(xId);
		String getSessionServiceXMLResponse = getSessionServiceReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting SessionService getSession API response in XML : "+getSessionServiceXMLResponse);
		log.info("\nPrinting SessionService getSession API response in XML : "+getSessionServiceXMLResponse);
		
		AssertJUnit.assertEquals("SessionService getSession API is not working", 200, getSessionServiceReqGen.response.getStatus());
		
		try {
			JSONObject  getSessionServiceJSONObject = XML.toJSONObject(getSessionServiceXMLResponse);
			String getSessionServiceJSONResponse = getSessionServiceJSONObject.toString();
			System.out.println("\nPrinting SessionService getSession API response in JSON : "+getSessionServiceJSONResponse+"\n");
			log.info("\nPrinting SessionService getSession API response in JSON : "+getSessionServiceJSONResponse+"\n");
			
			AssertJUnit.assertEquals("Failed to update sessionLastAccessedDate", apiUtil.GetNodeValue(SessionNodes.SESSION_LAST_ACCESSED_DATE.getNodePath(), 
					getSessionServiceJSONResponse).replace("\"", ""), lastAccessedDate);
			
		} catch (JSONException e) {
			System.out.println("Error while converting xml to json");
			log.error("Error while converting xml to json", e);
		}
	}

	/**
	 * This TestCase is used to invoke SessionService updateSession API and validate session LastAccessedDate
	 * 
	 * @param creationDate
	 * @param lastAccessedDate
	 * @param lastModifiedDate
	 * @param expiryDate
	 * @param userName
	 * @param passWord
	 */
	@Test( groups = { /* "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression", "laxmiTc" */ },
			dataProvider = "SessionServiceDP_updateSessionDataProvider_validateLastModifiedDate" )
	public void SessionService_updateSession_validateLastModifiedDate(String creationDate, String lastAccessedDate, String lastUpdatedDate, String lastModifiedDate, 
			String expiryDate, String userName, String passWord)
	{
		String xId = sessionServiceHelper.performGetXId(userName, passWord);
		AssertJUnit.assertNotNull("xId is null", xId);
		
		RequestGenerator updateSessionReqGen = sessionServiceHelper.perfomUpdateSessionOperation(creationDate, lastAccessedDate, lastUpdatedDate, lastModifiedDate, 
				expiryDate, userName, xId);
		System.out.println("\nPrinting SessionService updateSession API response : "+updateSessionReqGen.returnresponseasstring()+"\n");
		log.info("\nPrinting SessionService updateSession API response : "+updateSessionReqGen.returnresponseasstring());
		
		AssertJUnit.assertEquals("SessionService updateSession API is not working", 200, updateSessionReqGen.response.getStatus());
		
		RequestGenerator getSessionServiceReqGen = sessionServiceHelper.perfomGetSessionOperation(xId);
		String getSessionServiceXMLResponse = getSessionServiceReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting SessionService getSession API response in XML : "+getSessionServiceXMLResponse);
		log.info("\nPrinting SessionService getSession API response in XML : "+getSessionServiceXMLResponse);
		
		AssertJUnit.assertEquals("SessionService getSession API is not working", 200, getSessionServiceReqGen.response.getStatus());
		
		try {
			JSONObject  getSessionServiceJSONObject = XML.toJSONObject(getSessionServiceXMLResponse);
			String getSessionServiceJSONResponse = getSessionServiceJSONObject.toString();
			System.out.println("\nPrinting SessionService getSession API response in JSON : "+getSessionServiceJSONResponse+"\n");
			log.info("\nPrinting SessionService getSession API response in JSON : "+getSessionServiceJSONResponse+"\n");
			
			AssertJUnit.assertEquals("Failed to update sessionLastModifiedDate", apiUtil.GetNodeValue(SessionNodes.SESSION_LAST_MODIFIED_DATE.getNodePath(), 
					getSessionServiceJSONResponse).replace("\"", ""), lastModifiedDate);
			
		} catch (JSONException e) {
			System.out.println("Error while converting xml to json");
			log.error("Error while converting xml to json", e);
		}
	}
	
	/**
	 * This TestCase is used to invoke SessionService updateSession API and validate session ExpiryDate
	 * 
	 * @param creationDate
	 * @param lastAccessedDate
	 * @param lastModifiedDate
	 * @param expiryDate
	 * @param userName
	 * @param passWord
	 */
	@Test( groups = { /* "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression", "laxmiTc" */ },
			dataProvider = "SessionServiceDP_updateSessionDataProvider_validateSessionExpiryDate" )
	public void SessionService_updateSession_validateExpiryDate(String creationDate, String lastAccessedDate, String lastUpdatedDate, String lastModifiedDate, 
			String expiryDate, String userName, String passWord)	
	{			
		String xId = sessionServiceHelper.performGetXId(userName, passWord);
		AssertJUnit.assertNotNull("xId is null", xId);
		
		RequestGenerator updateSessionReqGen = sessionServiceHelper.perfomUpdateSessionOperation(creationDate, lastAccessedDate, lastUpdatedDate, lastModifiedDate, 
				expiryDate, userName, xId);
		System.out.println("\nPrinting SessionService updateSession API response : "+updateSessionReqGen.returnresponseasstring()+"\n");
		log.info("\nPrinting SessionService updateSession API response : "+updateSessionReqGen.returnresponseasstring());
		
		AssertJUnit.assertEquals("SessionService updateSession API is not working", 200, updateSessionReqGen.response.getStatus());
		
		RequestGenerator getSessionServiceReqGen = sessionServiceHelper.perfomGetSessionOperation(xId);
		String getSessionServiceXMLResponse = getSessionServiceReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting SessionService getSession API response in XML : "+getSessionServiceXMLResponse);
		log.info("\nPrinting SessionService getSession API response in XML : "+getSessionServiceXMLResponse);
		
		AssertJUnit.assertEquals("SessionService getSession API is not working", 200, getSessionServiceReqGen.response.getStatus());
		
		try {
			JSONObject  getSessionServiceJSONObject = XML.toJSONObject(getSessionServiceXMLResponse);
			String getSessionServiceJSONResponse = getSessionServiceJSONObject.toString();
			System.out.println("\nPrinting SessionService getSession API response in JSON : "+getSessionServiceJSONResponse+"\n");
			log.info("\nPrinting SessionService getSession API response in JSON : "+getSessionServiceJSONResponse+"\n");
			
			AssertJUnit.assertEquals("Failed to update sessionExpiryDate", apiUtil.GetNodeValue(SessionNodes.SESSION_EXPIRY_DATE.getNodePath(), 
					getSessionServiceJSONResponse).replace("\"", ""), expiryDate);
			
		} catch (JSONException e) {
			System.out.println("Error while converting xml to json");
			log.error("Error while converting xml to json", e);
		}
	}
}
