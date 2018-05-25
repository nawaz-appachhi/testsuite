package com.myntra.apiTests.portalservices.all;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import net.minidev.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import com.jayway.jsonpath.JsonPath;
import com.myntra.apiTests.dataproviders.NotificationServiceDP;
import com.myntra.apiTests.portalservices.notificationservice.NotificationServiceHelper;
import com.myntra.apiTests.portalservices.notificationservice.NotificationServiceNodes;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.Toolbox;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.RequestGenerator;

/**
 * @author shankara.c
 *
 */
public class NotificationServiceTests extends NotificationServiceDP 
{
	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(NotificationServiceTests.class);
	static NotificationServiceHelper notificationServiceHelper = new NotificationServiceHelper();
	static APIUtilities apiUtil = new APIUtilities();
	
	/**
	 * This TestCase is used to invoke NotificationService getPortalGroups API and verification for success response code
	 * 
	 */
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression", "MiniRegression", "ExhaustiveRegression" })
	public void NotificationService_getPortalGroups_verifySuccessResponse()
	{
		RequestGenerator getPortalGroupsReqGen = notificationServiceHelper.invokeGetAllPortalGroups();
		String getPortalGroupsResponse = getPortalGroupsReqGen.ResponseValidations.GetResponseAsString();
		System.out.println("\nPrinting NotificationService getPortalGroups API response :\n\n"+getPortalGroupsResponse+"\n");
		log.info("\nPrinting NotificationService getPortalGroups API response :\n\n"+getPortalGroupsResponse+"\n");
		
		AssertJUnit.assertEquals("NotificationService getPortalGroup API is not working", 200, getPortalGroupsReqGen.response.getStatus());
	}
	
	/**
	 * This TestCase is used to invoke NotificationService getPortalGroups API and verification for node values
	 * 
	 */
	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression"  })
	public void NotificationService_getPortalGroups_verifyNodeValues() 
	{
		RequestGenerator getPortalGroupsReqGen = notificationServiceHelper.invokeGetAllPortalGroups();
		String getPortalGroupsResponse = getPortalGroupsReqGen.ResponseValidations.GetResponseAsString();
		System.out.println("\nPrinting NotificationService getPortalGroups API response :\n\n"+getPortalGroupsResponse+"\n");
		log.info("\nPrinting NotificationService getPortalGroups API response :\n\n"+getPortalGroupsResponse+"\n");
		
		AssertJUnit.assertNotNull("NotificationService getPortalGroups API response is null", getPortalGroupsResponse);
		
		String[] responsePortalGroups = getPortalGroupsResponse.replace("[", "").replace("]", "").replace("\"", "").trim().split(",");
		List<String> responsePortalGroupsList = new ArrayList<String>();
		for(String responsePortalGroup : responsePortalGroups){
			responsePortalGroupsList.add(responsePortalGroup.replace("\"", "").trim());
		}
		
		AssertJUnit.assertTrue("NotificationService getPortalGroups API response doesn't contains complete portal groups", 
				responsePortalGroupsList.containsAll(notificationServiceHelper.getPortalGroups()));
	}

	/**
	 * This TestCase is used to invoke NotificationService getNotificationTypes API and verification for success response code(200)
	 * 
	 */
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression", "MiniRegression", "ExhaustiveRegression" })
	public void NotificationService_getNotificationTypes_verifySuccessResponse() 
	{
		RequestGenerator getNotificationTypesReqGen = notificationServiceHelper.invokeGetNotificationTypes();
		String getNotificationTypesResponse = getNotificationTypesReqGen.ResponseValidations.GetResponseAsString();
		System.out.println("\nPrinting NotificationService getNotificationTypes API response :\n\n"+getNotificationTypesResponse+"\n");
		log.info("\nPrinting NotificationService getNotificationTypes API response :\n\n"+getNotificationTypesResponse+"\n");
		
		AssertJUnit.assertEquals("NotificationService getNotificationTypes API is not working", 200, getNotificationTypesReqGen.response.getStatus());
	}

	/**
	 * This TestCase is used to invoke NotificationService getNotificationTypes API and verification for node values
	 * 
	 */
	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression"  })
	public void NotificationService_getNotificationTypes_verifyNodeValues() 
	{
		RequestGenerator getNotificationTypesReqGen = notificationServiceHelper.invokeGetNotificationTypes();
		String getNotificationTypesResponse = getNotificationTypesReqGen.ResponseValidations.GetResponseAsString();
		System.out.println("\nPrinting NotificationService getNotificationTypes API response :\n\n"+getNotificationTypesResponse+"\n");
		log.info("\nPrinting NotificationService getNotificationTypes API response :\n\n"+getNotificationTypesResponse+"\n");
		
		AssertJUnit.assertNotNull("NotificationService getNotificationTypes API response is null", getNotificationTypesResponse);
		
		String[] responseNotificationTypes = getNotificationTypesResponse.replace("[", "").replace("]", "").replace("\"", "").trim().split(",");
		List<String> responseNotificationTypesList = new ArrayList<String>();
		for(String responseNotificationType : responseNotificationTypes){
			responseNotificationTypesList.add(responseNotificationType.replace("\"", "").trim());
		}
		
		AssertJUnit.assertTrue("NotificationService getNotificationTypes API response doesn't contains complete notification types", 
				responseNotificationTypesList.containsAll(notificationServiceHelper.getNotificationTypes()));
	}
	
	/**
	 * This TestCase is used to invoke NotificationService createMasterNotification API and verify for success response code(200)
	 * 
	 * @param notificationText
	 * @param notificationTitle
	 * @param imageUrl
	 * @param pageUrl
	 * @param startTime
	 * @param publishTime
	 * @param endTime
	 * @param notificationType
	 * @param forPortalGroup
	 */
	@Test(groups = { "Smoke", "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression" },
			dataProvider = "NotificationServiceDP_createMasterNotification_verifySuccessResponse")
	public void NotificationService_createMasterNotification_verifySuccessResponse(String notificationText, String notificationTitle, String imageUrl, String pageUrl,
			String startTime, String publishTime, String endTime, String notificationType, String forPortalGroup)
	{
		RequestGenerator createMasterNotificationReqGen = notificationServiceHelper.invokeCreateMasterNotification(notificationText, notificationTitle, imageUrl, pageUrl,
				publishTime, startTime, endTime, notificationType, forPortalGroup);
		String createMasterNotificationResponse = createMasterNotificationReqGen.ResponseValidations.GetResponseAsString();
		System.out.println("\nPrinting NotificationService createMasterNotification API response :\n\n"+createMasterNotificationResponse+"\n");
		log.info("\nPrinting NotificationService createMasterNotification API response :\n\n"+createMasterNotificationResponse+"\n");
		
		AssertJUnit.assertEquals("NotificationService createMasterNotification API is not working", 200, createMasterNotificationReqGen.response.getStatus());
	}

	/**
	 * This TestCase is used to invoke NotificationService createMasterNotification API and verification for failure response
	 * 
	 * @param notificationText
	 * @param notificationTitle
	 * @param imageUrl
	 * @param pageUrl
	 * @param startTime
	 * @param publishTime
	 * @param endTime
	 * @param notificationType
	 * @param forPortalGroup
	 */
	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression" },
			dataProvider = "NotificationServiceDP_createMasterNotification_verifyFailureResponse")
	public void NotificationService_createMasterNotification_verifyFailureResponse(String notificationText, String notificationTitle, String imageUrl, String pageUrl,
			String publishTime, String startTime, String endTime, String notificationType, String forPortalGroup)
	{
		RequestGenerator createMasterNotificationReqGen = notificationServiceHelper.invokeCreateMasterNotification(notificationText, notificationTitle, imageUrl, pageUrl,
				publishTime, startTime, endTime, notificationType, forPortalGroup);
		String createMasterNotificationResponse = createMasterNotificationReqGen.ResponseValidations.GetResponseAsString();
		System.out.println("\nPrinting NotificationService createMasterNotification API response :\n\n"+createMasterNotificationResponse+"\n");
		log.info("\nPrinting NotificationService createMasterNotification API response :\n\n"+createMasterNotificationResponse+"\n");
		
		AssertJUnit.assertTrue("NotificationService createMasterNotification API response success node value is not false value",
				createMasterNotificationReqGen.respvalidate.NodeValue(NotificationServiceNodes.SUCCESS_NODE.getNodePath(), true).equals(FALSE_VALUE));
	}
	
	/**
	 * This TestCase is used to invoke NotificationService createMasterNotification API and verify the master nodes values
	 * 
	 * @param notificationText
	 * @param notificationTitle
	 * @param imageUrl
	 * @param pageUrl
	 * @param startTime
	 * @param publishTime
	 * @param endTime
	 * @param notificationType
	 * @param forPortalGroup
	 */
	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression" }, 
			dataProvider = "NotificationServiceDP_createMasterNotification_verifyNodeValues")
	public void NotificationService_createMasterNotification_verifyNodeValues(String notificationText, String notificationTitle, String imageUrl, String pageUrl,
			String publishTime, String startTime, String endTime, String notificationType, String forPortalGroup)
	{
		RequestGenerator createMasterNotificationReqGen = notificationServiceHelper.invokeCreateMasterNotification(notificationText, notificationTitle, imageUrl, pageUrl,
				publishTime, startTime, endTime, notificationType, forPortalGroup);
		String createMasterNotificationResponse = createMasterNotificationReqGen.ResponseValidations.GetResponseAsString();
		System.out.println("\nPrinting NotificationService createMasterNotification API response :\n\n"+createMasterNotificationResponse+"\n");
		log.info("\nPrinting NotificationService createMasterNotification API response :\n\n"+createMasterNotificationResponse+"\n");
		
		AssertJUnit.assertTrue("createMasterNotification response Nodes are incomplete", 
				createMasterNotificationReqGen.respvalidate.DoesNodesExists(NotificationServiceNodes.getMasterNotificationNodes()));
		
		AssertJUnit.assertTrue("response success node value is not true value",
				createMasterNotificationReqGen.respvalidate.NodeValue(NotificationServiceNodes.SUCCESS_NODE.getNodePath(), true).equals(TRUE_VALUE));
		
		AssertJUnit.assertNotNull("Master Notification id is null", 
				createMasterNotificationReqGen.respvalidate.NodeValue(NotificationServiceNodes.MASTER_NOTIFICATION_ID.getNodePath(), true));
		
		AssertJUnit.assertEquals("request and response notificationType is not same", notificationType,
				createMasterNotificationReqGen.respvalidate.NodeValue(NotificationServiceNodes.MASTER_NOTIFICATION_TYPE.getNodePath(), true).replace("\"", ""));
	}
	
	/**
	 * This TestCase is used to invoke NotificationService searchMasterNotification API and verification success response code
	 * 
	 * @param portalGroup
	 * @param notificationType
	 */
	@Test(groups = { "Smoke", "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression" }, 
			dataProvider = "NotificationServiceDP_searchMasterNotification_verifySuccessResponse")
	public void NotificationService_searchMasterNotification_verifySuccessResponse(String portalGroup, String notificationType)
	{
		RequestGenerator searchMasterNotificationReqGen = notificationServiceHelper.invokeSearchMasterNotification(portalGroup, notificationType);
		String searchMasterNotificationResponse = searchMasterNotificationReqGen.ResponseValidations.GetResponseAsString();
		System.out.println("\nPrinting NotificationService searchMasterNotification API response :\n\n"+searchMasterNotificationResponse+"\n");
		log.info("\nPrinting NotificationService searchMasterNotification API response :\n\n"+searchMasterNotificationResponse+"\n");
		
		AssertJUnit.assertEquals("NotificationService searchMasterNotification API is not working", 200, searchMasterNotificationReqGen.response.getStatus());
	}
	
	/**
	 * This TestCase is used to invoke searchMasterNotification API and verification for failure response
	 * 
	 * @param portalGroup
	 * @param notificationType
	 */
	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression" }, 
			dataProvider = "NotificationServiceDP_searchMasterNotification_verifyFailureResponse")
	public void NotificationService_searchMasterNotification_verifyFailureResponse(String portalGroup, String notificationType)
	{
		RequestGenerator searchMasterNotificationReqGen = notificationServiceHelper.invokeSearchMasterNotification(portalGroup, notificationType);
		String searchMasterNotificationResponse = searchMasterNotificationReqGen.ResponseValidations.GetResponseAsString();
		System.out.println("\nPrinting NotificationService searchMasterNotification API response :\n\n"+searchMasterNotificationResponse+"\n");
		log.info("\nPrinting NotificationService searchMasterNotification API response :\n\n"+searchMasterNotificationResponse+"\n");
		
		AssertJUnit.assertEquals("NotificationService searchMasterNotification API is not working", 404, searchMasterNotificationReqGen.response.getStatus());
	}
	
	/**
	 * This TestCase is used to invoke NotificationService searchMasterNotification API and verification for notification node values
	 * 
	 * @param portalGroup
	 * @param notificationType
	 */
	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression" }, 
			dataProvider = "NotificationServiceDP_searchMasterNotification_verifyNodeValues")
	public void NotificationService_searchMasterNotification_verifyNodeValues(String portalGroup, String notificationType)
	{
		RequestGenerator searchMasterNotificationReqGen = notificationServiceHelper.invokeSearchMasterNotification(portalGroup, notificationType);
		String searchMasterNotificationResponse = searchMasterNotificationReqGen.ResponseValidations.GetResponseAsString();
		System.out.println("\nPrinting NotificationService searchMasterNotification API response :\n\n"+searchMasterNotificationResponse+"\n");
		log.info("\nPrinting NotificationService searchMasterNotification API response :\n\n"+searchMasterNotificationResponse+"\n");
		
		AssertJUnit.assertTrue("Error while invoking searchMasterNotification API",
				searchMasterNotificationReqGen.respvalidate.NodeValue(NotificationServiceNodes.SUCCESS_NODE.getNodePath(), true).equals(TRUE_VALUE));
		
		AssertJUnit.assertTrue("masterNotificationSearchResults node doesn't exists", 
				searchMasterNotificationReqGen.respvalidate.DoesNodeExists(NotificationServiceNodes.MASTER_NOTIFICATION_SEARCH_RESULTS.getNodePath()));
		
		List<JSONObject> masterNotificationSearchResults = JsonPath.read(searchMasterNotificationResponse, "$.masterNotificationSearchResults[*]");
		
		if(CollectionUtils.isEmpty(masterNotificationSearchResults))
		{
			String publishTime = String.valueOf(Calendar.getInstance().getTimeInMillis());
			Calendar startTimeCal = Calendar.getInstance();
			startTimeCal.add(Calendar.HOUR, 2);
			String startTime = String.valueOf(startTimeCal.getTimeInMillis());
			Calendar endTimeCal = Calendar.getInstance();
			endTimeCal.add(Calendar.HOUR, 4);
			String endTime = String.valueOf(endTimeCal.getTimeInMillis());
			
			RequestGenerator createMasterNotificationReqGen = notificationServiceHelper.invokeCreateMasterNotification(TITAN_PROD_NOTIFICATION_TEXT, TITAN_PROD_NOTIFICATION_TITLE, 
					TITAN_PROD_NOTIFICATION_IMG_URL, TITAN_PROD_NOTIFICATION_PAGE_URL, publishTime, startTime, endTime, notificationType, portalGroup);
			String createMasterNotificationResponse = createMasterNotificationReqGen.ResponseValidations.GetResponseAsString();
			System.out.println("\nPrinting NotificationService createMasterNotification API response :\n\n"+createMasterNotificationResponse+"\n");
			log.info("\nPrinting NotificationService createMasterNotification API response :\n\n"+createMasterNotificationResponse+"\n");
			
			AssertJUnit.assertTrue("Error while invoking createMasterNotification API",
					createMasterNotificationReqGen.respvalidate.NodeValue(NotificationServiceNodes.SUCCESS_NODE.getNodePath(), true).equals(TRUE_VALUE));
			
			searchMasterNotificationReqGen = notificationServiceHelper.invokeSearchMasterNotification(portalGroup, notificationType);
			searchMasterNotificationResponse = searchMasterNotificationReqGen.ResponseValidations.GetResponseAsString();
			System.out.println("\nPrinting NotificationService searchMasterNotification API response :\n\n"+searchMasterNotificationResponse+"\n");
			log.info("\nPrinting NotificationService searchMasterNotification API response :\n\n"+searchMasterNotificationResponse+"\n");
			
			AssertJUnit.assertTrue("Error while invoking searchMasterNotification API",
					searchMasterNotificationReqGen.respvalidate.NodeValue(NotificationServiceNodes.SUCCESS_NODE.getNodePath(), true).equals(TRUE_VALUE));
			
			masterNotificationSearchResults = JsonPath.read(searchMasterNotificationResponse, "$.masterNotificationSearchResults[*]");
		} 
		
		for(int i = 0; i < masterNotificationSearchResults.size(); i++)
		{
			AssertJUnit.assertNotNull("MasterNotificationId node is null/blank", 
					JsonPath.read(searchMasterNotificationResponse, "$.masterNotificationSearchResults["+i+"].masterNotificationId"));
			
			AssertJUnit.assertNotNull("actions node is null/blank", 
					JsonPath.read(searchMasterNotificationResponse, "$.masterNotificationSearchResults["+i+"].actions"));
			
			AssertJUnit.assertNotNull("getDetails node is null/blank", 
					JsonPath.read(searchMasterNotificationResponse, "$.masterNotificationSearchResults["+i+"].actions.getDetails"));
			
			AssertJUnit.assertTrue("success node value is not true value", 
					String.valueOf(JsonPath.read(searchMasterNotificationResponse, "$.masterNotificationSearchResults["+i+"].success")).contains(TRUE_VALUE));
		}
		
	}
	
	/**
	 * This TestCase is used to invoke NotificationService searchMasterNotification with StartTime and EndTime API and verification success response code
	 * 
	 * @param portalGroup
	 * @param notificationType
	 */
	@Test(groups = { "Smoke", "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression" }, 
			dataProvider = "NotificationServiceDP_searchMasterNotificationWithSTET_verifySuccessResponse")
	public void NotificationService_searchMasterNotificationWithSTET_verifySuccessResponse(String portalGroup, String notificationType, String startTime, String endTime)
	{
		RequestGenerator searchMasterNotificationReqGen = notificationServiceHelper.invokeSearchMasterNotificationWithSTET(portalGroup, notificationType, startTime, endTime );
		String searchMasterNotificationResponse = searchMasterNotificationReqGen.ResponseValidations.GetResponseAsString();
		System.out.println("\nPrinting NotificationService searchMasterNotification with StartTime and EndTime API response :\n\n"+searchMasterNotificationResponse+"\n");
		log.info("\nPrinting NotificationService searchMasterNotification with StartTime and EndTime API response :\n\n"+searchMasterNotificationResponse+"\n");
		
		AssertJUnit.assertEquals("NotificationService searchMasterNotification with StartTime and EndTime API is not working", 200, searchMasterNotificationReqGen.response.getStatus());
	}
	
	/**
	 * This TestCase is used to invoke searchMasterNotification with StartTime and EndTime API and verification for failure response
	 * 
	 * @param portalGroup
	 * @param notificationType
	 */
	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression" }, 
			dataProvider = "NotificationServiceDP_searchMasterNotificationWithSTET_verifyFailureResponse")
	public void NotificationService_searchMasterNotificationWithSTET_verifyFailureResponse(String portalGroup, String notificationType, String startTime, String endTime)
	{
		RequestGenerator searchMasterNotificationReqGen = notificationServiceHelper.invokeSearchMasterNotificationWithSTET(portalGroup, notificationType, startTime, endTime);
		String searchMasterNotificationResponse = searchMasterNotificationReqGen.ResponseValidations.GetResponseAsString();
		System.out.println("\nPrinting NotificationService searchMasterNotification with StartTime and EndTime API response :\n\n"+searchMasterNotificationResponse+"\n");
		log.info("\nPrinting NotificationService searchMasterNotification with StartTime and EndTime API response :\n\n"+searchMasterNotificationResponse+"\n");
		
		AssertJUnit.assertEquals("NotificationService searchMasterNotification with StartTime and EndTime API is not working", 404, searchMasterNotificationReqGen.response.getStatus());
	}
	
	/**
	 * This TestCase is used to invoke NotificationService searchMasterNotification with StartTime and EndTime API and verification for notification node values
	 * 
	 * @param portalGroup
	 * @param notificationType
	 */
	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression" }, 
			dataProvider = "NotificationServiceDP_searchMasterNotificationWithSTET_verifyNodeValues")
	public void NotificationService_searchMasterNotificationWithSTET_verifyNodeValues(String portalGroup, String notificationType, String startTime, String endTime)
	{
		RequestGenerator searchMasterNotificationReqGen = notificationServiceHelper.invokeSearchMasterNotificationWithSTET(portalGroup, notificationType, startTime, endTime);
		String searchMasterNotificationResponse = searchMasterNotificationReqGen.ResponseValidations.GetResponseAsString();
		System.out.println("\nPrinting NotificationService searchMasterNotification with StartTime and EndTime API response :\n\n"+searchMasterNotificationResponse+"\n");
		log.info("\nPrinting NotificationService searchMasterNotification with StartTime and EndTime API response :\n\n"+searchMasterNotificationResponse+"\n");
		
		AssertJUnit.assertTrue("Error while invoking searchMasterNotification with StartTime and EndTime API",
				searchMasterNotificationReqGen.respvalidate.NodeValue(NotificationServiceNodes.SUCCESS_NODE.getNodePath(), true).equals(TRUE_VALUE));
		
		List<JSONObject> masterNotificationSearchResults = JsonPath.read(searchMasterNotificationResponse, "$.masterNotificationSearchResults[*]");
		if(CollectionUtils.isEmpty(masterNotificationSearchResults))
		{
			RequestGenerator createMasterNotificationReqGen = notificationServiceHelper.invokeCreateMasterNotification(TITAN_PROD_NOTIFICATION_TEXT, TITAN_PROD_NOTIFICATION_TITLE, 
					TITAN_PROD_NOTIFICATION_IMG_URL, TITAN_PROD_NOTIFICATION_PAGE_URL, startTime, startTime, endTime, notificationType, portalGroup);
			String createMasterNotificationResponse = createMasterNotificationReqGen.ResponseValidations.GetResponseAsString();
			System.out.println("\nPrinting NotificationService createMasterNotification API response :\n\n"+createMasterNotificationResponse+"\n");
			log.info("\nPrinting NotificationService createMasterNotification API response :\n\n"+createMasterNotificationResponse+"\n");
			
			AssertJUnit.assertTrue("Error while invoking createMasterNotification API",
					createMasterNotificationReqGen.respvalidate.NodeValue(NotificationServiceNodes.SUCCESS_NODE.getNodePath(), true).equals(TRUE_VALUE));
			
			searchMasterNotificationReqGen= notificationServiceHelper.invokeSearchMasterNotificationWithSTET(portalGroup, notificationType, startTime, endTime );
			searchMasterNotificationResponse = searchMasterNotificationReqGen.ResponseValidations.GetResponseAsString();
			System.out.println("\nPrinting NotificationService searchMasterNotification with StartTime and EndTime API response :\n\n"+searchMasterNotificationResponse+"\n");
			log.info("\nPrinting NotificationService searchMasterNotification with StartTime and EndTime API response :\n\n"+searchMasterNotificationResponse+"\n");
			
			AssertJUnit.assertTrue("Error while invoking searchMasterNotification with StartTime and EndTime API",
					searchMasterNotificationReqGen.respvalidate.NodeValue(NotificationServiceNodes.SUCCESS_NODE.getNodePath(), true).equals(TRUE_VALUE));
			
			masterNotificationSearchResults = JsonPath.read(searchMasterNotificationResponse, "$.masterNotificationSearchResults[*]");
		} 
		
		for(int i = 0; i < masterNotificationSearchResults.size(); i++)
		{
			AssertJUnit.assertNotNull("MasterNotificationId node is null/blank", 
					JsonPath.read(searchMasterNotificationResponse, "$.masterNotificationSearchResults["+i+"].masterNotificationId"));
			
			AssertJUnit.assertNotNull("actions node is null/blank", 
					JsonPath.read(searchMasterNotificationResponse, "$.masterNotificationSearchResults["+i+"].actions"));
			
			AssertJUnit.assertNotNull("getDetails node is null/blank", 
					JsonPath.read(searchMasterNotificationResponse, "$.masterNotificationSearchResults["+i+"].actions.getDetails"));
			
			AssertJUnit.assertTrue("success node value is not true value", 
					String.valueOf(JsonPath.read(searchMasterNotificationResponse, "$.masterNotificationSearchResults["+i+"].success")).contains(TRUE_VALUE));
		}
		
	}
	
	/**
	 * This TestCase is used to invoke NotificationService getDetailsMasterNotification API and verification for success response
	 * 
	 * @param portalGroup
	 * @param notificationType
	 */
	@Test(groups = { "Smoke", "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression" }, 
			dataProvider = "NotificationServiceDP_getDetailsMasterNotification_verifySuccessResponse")
	public void NotificationService_getDetailsMasterNotification_verifySuccessResponse(String portalGroup, String notificationType)
	{
		String publishTime = String.valueOf(Calendar.getInstance().getTimeInMillis());
		Calendar startTimeCal = Calendar.getInstance();
		startTimeCal.add(Calendar.HOUR, 2);
		String startTime = String.valueOf(startTimeCal.getTimeInMillis());
		Calendar endTimeCal = Calendar.getInstance();
		endTimeCal.add(Calendar.HOUR, 4);
		String endTime = String.valueOf(endTimeCal.getTimeInMillis());
		
		RequestGenerator createMasterNotificationReqGen = notificationServiceHelper.invokeCreateMasterNotification(PUMA_PROD_NOTIFICATION_TEXT, PUMA_PROD_NOTIFICATION_TITLE, 
				PUMA_PROD_NOTIFICATION_IMG_URL, PUMA_PROD_NOTIFICATION_PAGE_URL, publishTime, startTime, endTime, notificationType, portalGroup);
		String createMasterNotificationResponse = createMasterNotificationReqGen.ResponseValidations.GetResponseAsString();
		System.out.println("\nPrinting NotificationService createMasterNotification API response :\n\n"+createMasterNotificationResponse+"\n");
		log.info("\nPrinting NotificationService createMasterNotification API response :\n\n"+createMasterNotificationResponse+"\n");
		
		AssertJUnit.assertTrue("Error while invoking createMasterNotification API",
				createMasterNotificationReqGen.respvalidate.NodeValue(NotificationServiceNodes.SUCCESS_NODE.getNodePath(), true).equals(TRUE_VALUE));

		String masterNotificationId = JsonPath.read(createMasterNotificationResponse, "$.masterNotification.id");
		System.out.println("Printing masterNotificationId : "+masterNotificationId);
		log.info("Printing masterNotificationId : "+masterNotificationId);
		
		RequestGenerator getDetailsMasterNotificationReqGen = notificationServiceHelper.invokeGetDetailsMasterNotification(portalGroup, masterNotificationId);
		String getDetailsMasterNotificationResponse = getDetailsMasterNotificationReqGen.ResponseValidations.GetResponseAsString();
		System.out.println("\nPrinting NotificationService getDetailsMasterNotification API response :\n\n"+getDetailsMasterNotificationResponse+"\n");
		log.info("\nPrinting NotificationService getDetailsMasterNotification API response :\n\n"+getDetailsMasterNotificationResponse+"\n");
		
		AssertJUnit.assertEquals("NotificationService getDetailsMasterNotification API is not working", 200, getDetailsMasterNotificationReqGen.response.getStatus());
	}
	
	/**
	 * This TestCase is used to invoke NotificationService getDetailsMasterNotification API and verification for failure response
	 * 
	 * @param portalGroup
	 * @param masterNotificationId
	 */
	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression" }, 
			dataProvider = "NotificationServiceDP_getDetailsMasterNotification_verifyFailureResponse")
	public void NotificationService_getDetailsMasterNotification_verifyFailureResponse(String portalGroup, String masterNotificationId) 
	{
		RequestGenerator getDetailsMasterNotificationReqGen = notificationServiceHelper.invokeGetDetailsMasterNotification(portalGroup, masterNotificationId);
		String getDetailsMasterNotificationResponse = getDetailsMasterNotificationReqGen.ResponseValidations.GetResponseAsString();
		System.out.println("\nPrinting NotificationService getDetailsMasterNotification API response :\n\n"+getDetailsMasterNotificationResponse+"\n");
		log.info("\nPrinting NotificationService getDetailsMasterNotification API response :\n\n"+getDetailsMasterNotificationResponse+"\n");
		
		AssertJUnit.assertEquals("NotificationService getDetailsMasterNotification API is not working", 404, getDetailsMasterNotificationReqGen.response.getStatus());
	}

	/**
	 * This TestCase is used to invoke NotificationService getDetailsMasterNotification API and verification for node values
	 * 
	 * @param portalGroup
	 * @param notificationType
	 */
	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression" },
			dataProvider = "NotificationServiceDP_getDetailsMasterNotification_verifyNodeValues")
	public void NotificationService_getDetailsMasterNotification_verifyNodeValues(String portalGroup, String notificationType)
	{
		String publishTime = String.valueOf(Calendar.getInstance().getTimeInMillis());
		Calendar startTimeCal = Calendar.getInstance();
		startTimeCal.add(Calendar.HOUR, 2);
		String startTime = String.valueOf(startTimeCal.getTimeInMillis());
		Calendar endTimeCal = Calendar.getInstance();
		endTimeCal.add(Calendar.HOUR, 4);
		String endTime = String.valueOf(endTimeCal.getTimeInMillis());
		
		RequestGenerator createMasterNotificationReqGen = notificationServiceHelper.invokeCreateMasterNotification(ADIDAS_PROD_NOTIFICATION_TEXT, ADIDAS_PROD_NOTIFICATION_TITLE, 
				ADIDAS_PROD_NOTIFICATION_IMG_URL, ADIDAS_PROD_NOTIFICATION_PAGE_URL, publishTime, startTime, endTime, notificationType, portalGroup);
		String createMasterNotificationResponse = createMasterNotificationReqGen.ResponseValidations.GetResponseAsString();
		System.out.println("\nPrinting NotificationService createMasterNotification API response :\n\n"+createMasterNotificationResponse+"\n");
		log.info("\nPrinting NotificationService createMasterNotification API response :\n\n"+createMasterNotificationResponse+"\n");
		
		AssertJUnit.assertTrue("Error while invoking createMasterNotification API",
				createMasterNotificationReqGen.respvalidate.NodeValue(NotificationServiceNodes.SUCCESS_NODE.getNodePath(), true).equals(TRUE_VALUE));

		String masterNotificationId = JsonPath.read(createMasterNotificationResponse, "$.masterNotification.id");
		System.out.println("Printing masterNotificationId : "+masterNotificationId);
		log.info("Printing masterNotificationId : "+masterNotificationId);
		
		RequestGenerator getDetailsMasterNotificationReqGen = notificationServiceHelper.invokeGetDetailsMasterNotification(portalGroup, masterNotificationId);
		String getDetailsMasterNotificationResponse = getDetailsMasterNotificationReqGen.ResponseValidations.GetResponseAsString();
		System.out.println("\nPrinting NotificationService getDetailsMasterNotification API response :\n\n"+getDetailsMasterNotificationResponse+"\n");
		log.info("\nPrinting NotificationService getDetailsMasterNotification API response :\n\n"+getDetailsMasterNotificationResponse+"\n");
		
		AssertJUnit.assertTrue("Error while invoking getDetailsMasterNotification API", 
				getDetailsMasterNotificationReqGen.respvalidate.NodeValue(NotificationServiceNodes.SUCCESS_NODE.getNodePath(), true).contains(TRUE_VALUE));

		AssertJUnit.assertTrue("Notification Nodes are incomplete",
				getDetailsMasterNotificationReqGen.respvalidate.DoesNodesExists(NotificationServiceNodes.getMasterNotificationNodes()));
		
	}
	
	/**
	 * This TestCase is used to invoke NotificationService deleteNotifications API and verification for success response code
	 * 
	 * @param portalGroup
	 * @param notificationType
	 */
	@Test(groups = { "Smoke", "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression" },
			dataProvider = "NotificationServiceDP_deleteNotifications_verifySuccessResponse")
	public void NotificationService_deleteNotifications_verifySuccessResponse(String portalGroup, String notificationType)
	{
		String publishTime = String.valueOf(Calendar.getInstance().getTimeInMillis());
		Calendar startTimeCal = Calendar.getInstance();
		startTimeCal.add(Calendar.HOUR, 2);
		String startTime = String.valueOf(startTimeCal.getTimeInMillis());
		Calendar endTimeCal = Calendar.getInstance();
		endTimeCal.add(Calendar.HOUR, 4);
		String endTime = String.valueOf(endTimeCal.getTimeInMillis());
		
		RequestGenerator createMasterNotificationReqGen = notificationServiceHelper.invokeCreateMasterNotification(NIKE_PROD_NOTIFICATION_TEXT, NIKE_PROD_NOTIFICATION_TITLE, 
				NIKE_PROD_NOTIFICATION_IMG_URL, NIKE_PROD_NOTIFICATION_PAGE_URL, publishTime, startTime, endTime, notificationType, portalGroup);
		String createMasterNotificationResponse = createMasterNotificationReqGen.ResponseValidations.GetResponseAsString();
		System.out.println("\nPrinting NotificationService createMasterNotification API response :\n\n"+createMasterNotificationResponse+"\n");
		log.info("\nPrinting NotificationService createMasterNotification API response :\n\n"+createMasterNotificationResponse+"\n");
		
		AssertJUnit.assertTrue("Error while invoking createMasterNotification API",
				createMasterNotificationReqGen.respvalidate.NodeValue(NotificationServiceNodes.SUCCESS_NODE.getNodePath(), true).equals(TRUE_VALUE));

		String masterNotificationId = JsonPath.read(createMasterNotificationResponse, "$.masterNotification.id");
		System.out.println("Printing masterNotificationId : "+masterNotificationId);
		log.info("Printing masterNotificationId : "+masterNotificationId);
		
		RequestGenerator deleteNotificationsReqGen = notificationServiceHelper.invokeDeleteNotifications(portalGroup, masterNotificationId);
		String deleteNotificationsResponse = deleteNotificationsReqGen.ResponseValidations.GetResponseAsString();
		System.out.println("\nPrinting NotificationService deleteNotifications API response :\n\n"+deleteNotificationsResponse+"\n");
		log.info("\nPrinting NotificationService deleteNotifications API response :\n\n"+deleteNotificationsResponse+"\n");
		
		AssertJUnit.assertEquals("NotificationService deleteNotifications API is not working", 200, deleteNotificationsReqGen.response.getStatus());
	}

	/**
	 * This TestCase is used to invoke NotificationService deleteNotifications API and verification for failure response
	 * 
	 * @param portalGroup
	 * @param masterNotificationId
	 */
	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression" },
			dataProvider = "NotificationServiceDP_deleteNotifications_verifyFailureResponse")
	public void NotificationService_deleteNotifications_verifyFailureResponse(String portalGroup, String masterNotificationId) 
	{
		RequestGenerator deleteNotificationsReqGen = notificationServiceHelper.invokeDeleteNotifications(portalGroup, masterNotificationId);
		String deleteNotificationsResponse = deleteNotificationsReqGen.ResponseValidations.GetResponseAsString();
		System.out.println("\nPrinting NotificationService deleteNotifications API response :\n\n"+deleteNotificationsResponse+"\n");
		log.info("\nPrinting NotificationService deleteNotifications API response :\n\n"+deleteNotificationsResponse+"\n");
		
		AssertJUnit.assertEquals("NotificationService deleteNotifications API is not working", 404, deleteNotificationsReqGen.response.getStatus());
	}

	/**
	 * This TestCase is used to invoke NotificationService deleteNotifications API and verifying the response node values
	 * 
	 * @param portalGroup
	 * @param notificationType
	 */
	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression" }, 
			dataProvider = "NotificationServiceDP_deleteNotifications_verifyNodeValues")
	public void NotificationService_deleteNotifications_verifyNodeValues(String portalGroup, String notificationType)
	{
		String publishTime = String.valueOf(Calendar.getInstance().getTimeInMillis());
		Calendar startTimeCal = Calendar.getInstance();
		startTimeCal.add(Calendar.HOUR, 2);
		String startTime = String.valueOf(startTimeCal.getTimeInMillis());
		Calendar endTimeCal = Calendar.getInstance();
		endTimeCal.add(Calendar.HOUR, 4);
		String endTime = String.valueOf(endTimeCal.getTimeInMillis());
		
		RequestGenerator createMasterNotificationReqGen = notificationServiceHelper.invokeCreateMasterNotification(FOSSIL_PROD_NOTIFICATION_TEXT, FOSSIL_PROD_NOTIFICATION_TITLE, 
				FOSSIL_PROD_NOTIFICATION_IMG_URL, FOSSIL_PROD_NOTIFICATION_PAGE_URL, publishTime, startTime, endTime, notificationType, portalGroup);
		String createMasterNotificationResponse = createMasterNotificationReqGen.ResponseValidations.GetResponseAsString();
		System.out.println("\nPrinting NotificationService createMasterNotification API response :\n\n"+createMasterNotificationResponse+"\n");
		log.info("\nPrinting NotificationService createMasterNotification API response :\n\n"+createMasterNotificationResponse+"\n");
		
		AssertJUnit.assertTrue("Error while invoking createMasterNotification API",
				createMasterNotificationReqGen.respvalidate.NodeValue(NotificationServiceNodes.SUCCESS_NODE.getNodePath(), true).equals(TRUE_VALUE));

		String masterNotificationId = JsonPath.read(createMasterNotificationResponse, "$.masterNotification.id");
		System.out.println("Printing masterNotificationId : "+masterNotificationId);
		log.info("Printing masterNotificationId : "+masterNotificationId);
		
		RequestGenerator deleteNotificationsReqGen = notificationServiceHelper.invokeDeleteNotifications(portalGroup, masterNotificationId);
		String deleteNotificationsResponse = deleteNotificationsReqGen.ResponseValidations.GetResponseAsString();
		System.out.println("\nPrinting NotificationService deleteNotifications API response :\n\n"+deleteNotificationsResponse+"\n");
		log.info("\nPrinting NotificationService deleteNotifications API response :\n\n"+deleteNotificationsResponse+"\n");
		
		AssertJUnit.assertTrue("action value is not delete", 
				deleteNotificationsReqGen.respvalidate.NodeValue(NotificationServiceNodes.ACTION_NODE.getNodePath(), true).contains(ACTION_VALUE));
		
		AssertJUnit.assertTrue("Error while invoking NotificationService deleteNotifications API",
				deleteNotificationsReqGen.respvalidate.NodeValue(NotificationServiceNodes.SUCCESS_NODE.getNodePath(), true).contains(TRUE_VALUE));
		
	}
	
	/**
	 * This TestCase is used to invoke NotificationService createNotificationsForSingleUser API and verify success response
	 * 
	 * @param userId
	 * @param portalGroup
	 * @param notificationType
	 */
	@Test(groups = { "Smoke", "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression" }, 
			dataProvider = "NotificationServiceDP_createNotificationForSingleUser_verifySuccessResponse")
	public void NotificationService_createNotificationsForSingleUser_verifySuccessResponse(String userId, String portalGroup, String notificationType) 
	{
		String publishTime = String.valueOf(Calendar.getInstance().getTimeInMillis());
		Calendar startTimeCal = Calendar.getInstance();
		startTimeCal.add(Calendar.HOUR, 2);
		String startTime = String.valueOf(startTimeCal.getTimeInMillis());
		Calendar endTimeCal = Calendar.getInstance();
		endTimeCal.add(Calendar.HOUR, 4);
		String endTime = String.valueOf(endTimeCal.getTimeInMillis());
		
		RequestGenerator createMasterNotificationReqGen = notificationServiceHelper.invokeCreateMasterNotification(FOSSIL_PROD_NOTIFICATION_TEXT, FOSSIL_PROD_NOTIFICATION_TITLE, 
				FOSSIL_PROD_NOTIFICATION_IMG_URL, FOSSIL_PROD_NOTIFICATION_PAGE_URL, publishTime, startTime, endTime, notificationType, portalGroup);
		String createMasterNotificationResponse = createMasterNotificationReqGen.ResponseValidations.GetResponseAsString();
		System.out.println("\nPrinting NotificationService createMasterNotification API response :\n\n"+createMasterNotificationResponse+"\n");
		log.info("\nPrinting NotificationService createMasterNotification API response :\n\n"+createMasterNotificationResponse+"\n");
		
		AssertJUnit.assertTrue("Error while invoking createMasterNotification API",
				createMasterNotificationReqGen.respvalidate.NodeValue(NotificationServiceNodes.SUCCESS_NODE.getNodePath(), true).equals(TRUE_VALUE));

		String masterNotificationId = JsonPath.read(createMasterNotificationResponse, "$.masterNotification.id");
		RequestGenerator createNotificationForSingleUserService = notificationServiceHelper.invokeCreateNotificationsForSingleUser(userId, portalGroup, masterNotificationId);
		String createNotificationForSingleUserResponse = createNotificationForSingleUserService.ResponseValidations.GetResponseAsString();
		System.out.println("\nPrinting NotificationService createNotificationForSingleUser API response :\n\n"+createNotificationForSingleUserResponse);
		log.info("\nPrinting NotificationService createNotificationForSingleUser API response :\n\n"+createNotificationForSingleUserResponse);
		
		AssertJUnit.assertEquals("NotificationService createNotificationForSingleUser API is not working", 200, 
				createNotificationForSingleUserService.response.getStatus());
		
	}

	/**
	 * This TestCase is used to invoke NotificationService createNotificationsForSingleUser API and verify failure response
	 * 
	 * @param userId
	 * @param portalGroup
	 * @param masterNotificationId
	 */
	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression" }, 
			dataProvider = "NotificationServiceDP_createNotificationForSingleUser_verifyFailureResponse")
	public void NotificationService_createNotificationsForSingleUser_verifyFailureResponse(String userId, String portalGroup, String masterNotificationId)
	{
		RequestGenerator createNotificationForSingleUserService = notificationServiceHelper.invokeCreateNotificationsForSingleUser(userId, portalGroup, masterNotificationId);
		String createNotificationForSingleUserResponse = createNotificationForSingleUserService.ResponseValidations.GetResponseAsString();
		System.out.println("\nPrinting NotificationService createNotificationForSingleUser API response :\n\n"+createNotificationForSingleUserResponse);
		log.info("\nPrinting NotificationService createNotificationForSingleUser API response :\n\n"+createNotificationForSingleUserResponse);
		
		AssertJUnit.assertEquals("NotificationService createNotificationForSingleUser API is not working", 404, 
				createNotificationForSingleUserService.response.getStatus());
		
		AssertJUnit.assertTrue("Error while invoking createNotificationForSingleUser API",
				createNotificationForSingleUserService.respvalidate.NodeValue(NotificationServiceNodes.SUCCESS_NODE.getNodePath(), true).equals(FALSE_VALUE));
	}
	
	/**
	 * This TestCase is used to invoke NotificationService createNotificationsForSingleUser API and verify node values
	 * 
	 * @param userId
	 * @param portalGroup
	 * @param notificationType
	 */
	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression" }, 
			dataProvider = "NotificationServiceDP_createNotificationForSingleUser_verifyNodeValues")
	public void NotificationService_createNotificationsForSingleUser_verifyNodeValues(String userId, String portalGroup, String notificationType) 
	{
		String publishTime = String.valueOf(Calendar.getInstance().getTimeInMillis());
		Calendar startTimeCal = Calendar.getInstance();
		startTimeCal.add(Calendar.HOUR, 2);
		String startTime = String.valueOf(startTimeCal.getTimeInMillis());
		Calendar endTimeCal = Calendar.getInstance();
		endTimeCal.add(Calendar.HOUR, 4);
		String endTime = String.valueOf(endTimeCal.getTimeInMillis());
		
		RequestGenerator createMasterNotificationReqGen = notificationServiceHelper.invokeCreateMasterNotification(TITAN_PROD_NOTIFICATION_TEXT, TITAN_PROD_NOTIFICATION_TITLE, 
				TITAN_PROD_NOTIFICATION_IMG_URL, TITAN_PROD_NOTIFICATION_PAGE_URL, publishTime, startTime, endTime, notificationType, portalGroup);
		String createMasterNotificationResponse = createMasterNotificationReqGen.ResponseValidations.GetResponseAsString();
		System.out.println("\nPrinting NotificationService createMasterNotification API response :\n\n"+createMasterNotificationResponse+"\n");
		log.info("\nPrinting NotificationService createMasterNotification API response :\n\n"+createMasterNotificationResponse+"\n");
		
		AssertJUnit.assertTrue("Error while invoking createMasterNotification API",
				createMasterNotificationReqGen.respvalidate.NodeValue(NotificationServiceNodes.SUCCESS_NODE.getNodePath(), true).equals(TRUE_VALUE));

		String masterNotificationId = JsonPath.read(createMasterNotificationResponse, "$.masterNotification.id");
		RequestGenerator createNotificationForSingleUserService = notificationServiceHelper.invokeCreateNotificationsForSingleUser(userId, portalGroup, masterNotificationId);
		String createNotificationForSingleUserResponse = createNotificationForSingleUserService.ResponseValidations.GetResponseAsString();
		System.out.println("\nPrinting NotificationService createNotificationForSingleUser API response :\n\n"+createNotificationForSingleUserResponse);
		log.info("\nPrinting NotificationService createNotificationForSingleUser API response :\n\n"+createNotificationForSingleUserResponse);
		
		AssertJUnit.assertTrue("Error while invoking createNotificationForSingleUser API",
				createNotificationForSingleUserService.respvalidate.NodeValue(NotificationServiceNodes.SUCCESS_NODE.getNodePath(), true).equals(TRUE_VALUE));
		
		AssertJUnit.assertTrue("createNotificationForSingleUser response nodes are invalid", 
				createNotificationForSingleUserService.respvalidate.DoesNodesExists(NotificationServiceNodes.getCreateNotificationForSingleUserNodes()));
	}
	
	
	/**
	 * This TestCase is used to invoke NotificationService createNotificationsForSetOfUsers API and verify success response
	 * 
	 * @param portalGroup
	 * @param notificationType
	 * @param userIds
	 */
	@Test(groups = { "Smoke", "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression" }, 
			dataProvider = "NotificationServiceDP_createNotificationForSetOfUsers_verifySuccessResponse")
	public void NotificationService_createNotificationsForSetOfUsers_verifySuccessResponse(String portalGroup, String notificationType, String userIds)
	{
		String publishTime = String.valueOf(Calendar.getInstance().getTimeInMillis());
		Calendar startTimeCal = Calendar.getInstance();
		startTimeCal.add(Calendar.HOUR, 2);
		String startTime = String.valueOf(startTimeCal.getTimeInMillis());
		Calendar endTimeCal = Calendar.getInstance();
		endTimeCal.add(Calendar.HOUR, 4);
		String endTime = String.valueOf(endTimeCal.getTimeInMillis());
		
		RequestGenerator createMasterNotificationReqGen = notificationServiceHelper.invokeCreateMasterNotification(CAT_PROD_NOTIFICATION_TEXT, CAT_PROD_NOTIFICATION_TITLE, 
				CAT_PROD_NOTIFICATION_IMG_URL, CAT_PROD_NOTIFICATION_PAGE_URL, publishTime, startTime, endTime, notificationType, portalGroup);
		String createMasterNotificationResponse = createMasterNotificationReqGen.ResponseValidations.GetResponseAsString();
		System.out.println("\nPrinting NotificationService createMasterNotification API response :\n\n"+createMasterNotificationResponse+"\n");
		log.info("\nPrinting NotificationService createMasterNotification API response :\n\n"+createMasterNotificationResponse+"\n");
		
		AssertJUnit.assertTrue("Error while invoking createMasterNotification API",
				createMasterNotificationReqGen.respvalidate.NodeValue(NotificationServiceNodes.SUCCESS_NODE.getNodePath(), true).equals(TRUE_VALUE));

		String masterNotificationId = JsonPath.read(createMasterNotificationResponse, "$.masterNotification.id");
		
		RequestGenerator createNotificationForSetOfUsersService = notificationServiceHelper.invokeCreateNotificationsForSetOfUsers(portalGroup, masterNotificationId, userIds);
		String createNotificationForSetOfUsersResponse = createNotificationForSetOfUsersService.ResponseValidations.GetResponseAsString();
		System.out.println("\nPrinting NotificationService createNotificationForSetOfUsers API response :\n\n"+createNotificationForSetOfUsersResponse);
		log.info("\nPrinting NotificationService createNotificationForSetOfUsers API response :\n\n"+createNotificationForSetOfUsersResponse);
		
		AssertJUnit.assertEquals("NotificationService createNotificationForSetOfUsers API is not working", 200, 
				createNotificationForSetOfUsersService.response.getStatus());
		
	}

	/**
	 * This TestCase is used to invoke NotificationService createNotificationsForSetOfUsers API and verify failure response
	 * 
	 * @param urlparams
	 */
	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression" },
			dataProvider = "NotificationServiceDP_createNotificationForSetOfUsers_verifyFailureResponse")
	public void NotificationService_createNotificationsForSetOfUsers_verifyFailureResponse(String portalGroup, String masterNotificationId, String userIds)
	{
		RequestGenerator createNotificationForSetOfUsersService = notificationServiceHelper.invokeCreateNotificationsForSetOfUsers(portalGroup, masterNotificationId, userIds);
		String createNotificationForSetOfUsersResponse = createNotificationForSetOfUsersService.ResponseValidations.GetResponseAsString();
		System.out.println("\nPrinting NotificationService createNotificationForSetOfUsers API response :\n\n"+createNotificationForSetOfUsersResponse);
		log.info("\nPrinting NotificationService createNotificationForSetOfUsers API response :\n\n"+createNotificationForSetOfUsersResponse);
		
		AssertJUnit.assertEquals("NotificationService createNotificationForSetOfUsers API is not working", 404, 
				createNotificationForSetOfUsersService.response.getStatus());
		
		AssertJUnit.assertTrue("Error while invoking createNotificationForSetOfUsers API",
				createNotificationForSetOfUsersService.respvalidate.NodeValue(NotificationServiceNodes.SUCCESS_NODE.getNodePath(), true).equals(FALSE_VALUE));
	}
	
	/**
	 * This TestCase is used to invoke NotificationService createNotificationsForSetOfUsers API and verification for node values
	 * 
	 * @param portalGroup
	 * @param notificationType
	 * @param userIds
	 */
	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression" }, 
			dataProvider = "NotificationServiceDP_createNotificationForSetOfUsers_verifyNodeValues")
	public void NotificationService_createNotificationsForSetOfUsers_verifyNodeValues(String portalGroup, String notificationType, String userIds)
	{
		String publishTime = String.valueOf(Calendar.getInstance().getTimeInMillis());
		Calendar startTimeCal = Calendar.getInstance();
		startTimeCal.add(Calendar.HOUR, 2);
		String startTime = String.valueOf(startTimeCal.getTimeInMillis());
		Calendar endTimeCal = Calendar.getInstance();
		endTimeCal.add(Calendar.HOUR, 4);
		String endTime = String.valueOf(endTimeCal.getTimeInMillis());
		
		RequestGenerator createMasterNotificationReqGen = notificationServiceHelper.invokeCreateMasterNotification(VEROMODA_PROD_NOTIFICATION_TEXT, VEROMODA_PROD_NOTIFICATION_TITLE, 
				VEROMODA_PROD_NOTIFICATION_IMG_URL, VEROMODA_PROD_NOTIFICATION_PAGE_URL, publishTime, startTime, endTime, notificationType, portalGroup);
		String createMasterNotificationResponse = createMasterNotificationReqGen.ResponseValidations.GetResponseAsString();
		System.out.println("\nPrinting NotificationService createMasterNotification API response :\n\n"+createMasterNotificationResponse+"\n");
		log.info("\nPrinting NotificationService createMasterNotification API response :\n\n"+createMasterNotificationResponse+"\n");
		
		AssertJUnit.assertTrue("Error while invoking createMasterNotification API",
				createMasterNotificationReqGen.respvalidate.NodeValue(NotificationServiceNodes.SUCCESS_NODE.getNodePath(), true).equals(TRUE_VALUE));

		String masterNotificationId = JsonPath.read(createMasterNotificationResponse, "$.masterNotification.id");
		
		RequestGenerator createNotificationForSetOfUsersService = notificationServiceHelper.invokeCreateNotificationsForSetOfUsers(portalGroup, masterNotificationId, userIds);
		String createNotificationForSetOfUsersResponse = createNotificationForSetOfUsersService.ResponseValidations.GetResponseAsString();
		System.out.println("\nPrinting NotificationService createNotificationForSetOfUsers API response :\n\n"+createNotificationForSetOfUsersResponse);
		log.info("\nPrinting NotificationService createNotificationForSetOfUsers API response :\n\n"+createNotificationForSetOfUsersResponse);
		
		AssertJUnit.assertTrue("Error while invoking createNotificationForSingleUser API",
				createNotificationForSetOfUsersService.respvalidate.NodeValue(NotificationServiceNodes.SUCCESS_NODE.getNodePath(), true).equals(TRUE_VALUE));
		
		AssertJUnit.assertTrue("createNotificationsForSetOfUsers response nodes are invalid", 
				createNotificationForSetOfUsersService.respvalidate.DoesNodesExists(NotificationServiceNodes.getCreateNotificationsForSetOfUsersNodes()));
	}
	
	/**
	 * This TestCase is used to get the active notifications for the user and verification for success response code(200)
	 * 
	 * @param userId
	 * @param portalGroup
	 */
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression", "MiniRegression", "ExhaustiveRegression" }, 
			dataProvider = "NotificationServiceDP_getActiveNotificationsCount_verifySuccessResponse")
	public void NotificationService_getActiveNotificationsCount_verifySuccessResponse(String userId, String portalGroup) 
	{
		RequestGenerator getActiveNotificationsCountReqGen = notificationServiceHelper.invokeGetActiveNotificationsCount(userId, portalGroup);
		String getActiveNotificationsCountResponse = getActiveNotificationsCountReqGen.ResponseValidations.GetResponseAsString();
		System.out.println("\nPrinting NotificationService getActiveNotificationsCount API response :\n\n"+getActiveNotificationsCountResponse+"\n");
		log.info("\nPrinting NotificationService getActiveNotificationsCount API response :\n\n"+getActiveNotificationsCountResponse+"\n");
		
		AssertJUnit.assertEquals("NotificationService getActiveNotificationsCount API is not working", 200, getActiveNotificationsCountReqGen.response.getStatus());
	}

	/**
	 * This TestCase is used to get the active notifications for the user and verification for failure response
	 *  (response code as 404 and success node as false)
	 * 
	 * @param userId
	 * @param portalGroup
	 */
	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression" }, 
			dataProvider = "NotificationServiceDP_getActiveNotificationsCount_verifyFailureResponse")
	public void NotificationService_getActiveNotificationsCount_verifyFailureResponse(String userId, String portalGroup) 
	{
		RequestGenerator getActiveNotificationsCountReqGen = notificationServiceHelper.invokeGetActiveNotificationsCount(userId, portalGroup);
		String getActiveNotificationsCountResponse = getActiveNotificationsCountReqGen.ResponseValidations.GetResponseAsString();
		System.out.println("\nPrinting NotificationService getActiveNotificationsCount API response :\n\n"+getActiveNotificationsCountResponse+"\n");
		log.info("\nPrinting NotificationService getActiveNotificationsCount API response :\n\n"+getActiveNotificationsCountResponse+"\n");
		
		AssertJUnit.assertEquals("Response code does not match", 404, getActiveNotificationsCountReqGen.response.getStatus());
		
		AssertJUnit.assertTrue("success node value is not false value", 
				getActiveNotificationsCountReqGen.respvalidate.NodeValue(NotificationServiceNodes.SUCCESS_NODE.getNodePath(), true).equals(FALSE_VALUE));
	}
	
	/**
	 * This TestCase is used to get the active notifications for the user and verification for success response(success node as true) and verify notification nodes
	 * 
	 * @param userId
	 * @param portalGroup
	 */
	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression" },
			dataProvider = "NotificationServiceDP_getActiveNotificationsCount_verifyNodeValues")
	public void NotificationService_getActiveNotificationsCount_verifyNodeValues(String userId, String portalGroup) 
	{
		RequestGenerator getActiveNotificationsCountReqGen = notificationServiceHelper.invokeGetActiveNotificationsCount(userId, portalGroup);
		String getActiveNotificationsCountResponse = getActiveNotificationsCountReqGen.ResponseValidations.GetResponseAsString();
		System.out.println("\nPrinting NotificationService getActiveNotificationsCount API response :\n\n"+getActiveNotificationsCountResponse+"\n");
		log.info("\nPrinting NotificationService getActiveNotificationsCount API response :\n\n"+getActiveNotificationsCountResponse+"\n");
		
		AssertJUnit.assertTrue("Notification Nodes are incomplete", 
				getActiveNotificationsCountReqGen.respvalidate.DoesNodesExists(NotificationServiceNodes.getActiveNotificationCountNodes()));
		
		AssertJUnit.assertTrue("success node value is not true value",
				getActiveNotificationsCountReqGen.respvalidate.NodeValue(NotificationServiceNodes.SUCCESS_NODE.getNodePath(), true).equals(TRUE_VALUE));
		
		AssertJUnit.assertNotNull("getActiveNotifications node value is null", 
				getActiveNotificationsCountReqGen.respvalidate.NodeValue(NotificationServiceNodes.ACTIONS_GETACTNOTIFICATIONS_NODE.getNodePath(), true));
	}

	/**
	 * This TestCase is used to get the active notifications for the user and verification for success response code(200)
	 * 
	 * @param userId
	 * @param portalGroup
	 */
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression", "MiniRegression", "ExhaustiveRegression" },
			dataProvider = "NotificationServiceDP_getActiveNotifications_verifySuccessResponse")
	public void NotificationService_getActiveNotifications_verifySuccessResponse( String userId, String portalGroup )
	{
		RequestGenerator getActiveNotificationsReqGen = notificationServiceHelper.invokeGetActiveNotifications(userId, portalGroup);
		String getActiveNotificationsResponse = getActiveNotificationsReqGen.ResponseValidations.GetResponseAsString();
		System.out.println("\nPrinting NotificationService getActiveNotifications API response :\n\n"+getActiveNotificationsResponse);
		log.info("\nPrinting NotificationService getActiveNotifications API response :\n\n"+getActiveNotificationsResponse);
		
		AssertJUnit.assertEquals("NotificationService getActiveNotifications API is not working", 200, getActiveNotificationsReqGen.response.getStatus());
	}
	
	/**
	 * This TestCase is used to get the active notifications for the user and verification for failure response
	 * 
	 * @param userId
	 * @param portalGroup
	 */
	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression" }, 
			dataProvider = "NotificationServiceDP_getActiveNotifications_verifyFailureResponse")
	public void NotificationService_getActiveNotifications_verifyFailureResponse(String userId, String portalGroup)
	{
		RequestGenerator getActiveNotificationsReqGen = notificationServiceHelper.invokeGetActiveNotifications(userId, portalGroup);
		String getActiveNotificationsResponse = getActiveNotificationsReqGen.ResponseValidations.GetResponseAsString();
		System.out.println("\nPrinting NotificationService getActiveNotifications API response :\n\n"+getActiveNotificationsResponse);
		log.info("\nPrinting NotificationService getActiveNotifications API response :\n\n"+getActiveNotificationsResponse);
		
		AssertJUnit.assertTrue("Error while invoking NotificationService getActiveNotifications API", 
				getActiveNotificationsReqGen.respvalidate.NodeValue(NotificationServiceNodes.SUCCESS_NODE.getNodePath(), true).contains(FALSE_VALUE));
	}
	
	/**
	 * This TestCase is used to get the active notifications for the user and verification for node values
	 * 
	 * @param userId
	 * @param portalGroup
	 */
	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression" }, 
			dataProvider = "NotificationServiceDP_getActiveNotifications_verifyNodeValues")
	public void NotificationService_getActiveNotifications_verifyNodeValues(String userId, String portalGroup)
	{
		RequestGenerator getActiveNotificationsCountReqGen = notificationServiceHelper.invokeGetActiveNotificationsCount(userId, portalGroup);
		String getActiveNotificationsCountResponse = getActiveNotificationsCountReqGen.ResponseValidations.GetResponseAsString();
		System.out.println("\nPrinting NotificationService getActiveNotificationsCount API response :\n\n"+getActiveNotificationsCountResponse+"\n");
		log.info("\nPrinting NotificationService getActiveNotificationsCount API response :\n\n"+getActiveNotificationsCountResponse+"\n");
		
		if(getActiveNotificationsCountReqGen.respvalidate.NodeValue(NotificationServiceNodes.NUMOFACTIVENOTIFICATIONS_NODE.getNodePath(), true).equals(ZERO_VALUE))
		{
			String publishTime = String.valueOf(Calendar.getInstance().getTimeInMillis());
			Calendar startTimeCal = Calendar.getInstance();
			startTimeCal.add(Calendar.HOUR, 2);
			String startTime = String.valueOf(startTimeCal.getTimeInMillis());
			Calendar endTimeCal = Calendar.getInstance();
			endTimeCal.add(Calendar.HOUR, 4);
			String endTime = String.valueOf(endTimeCal.getTimeInMillis());
			
			RequestGenerator createMasterNotificationReqGen = notificationServiceHelper.invokeCreateMasterNotification(ADIDAS_PROD_NOTIFICATION_TEXT, ADIDAS_PROD_NOTIFICATION_TITLE, 
					ADIDAS_PROD_NOTIFICATION_IMG_URL, ADIDAS_PROD_NOTIFICATION_PAGE_URL, publishTime, startTime, endTime, NOTIFICATION_TYPE_MARKETING, portalGroup);
			String createMasterNotificationResponse = createMasterNotificationReqGen.ResponseValidations.GetResponseAsString();
			System.out.println("\nPrinting NotificationService createMasterNotification API response :\n\n"+createMasterNotificationResponse+"\n");
			log.info("\nPrinting NotificationService createMasterNotification API response :\n\n"+createMasterNotificationResponse+"\n");
			
			AssertJUnit.assertTrue("Error while invoking createMasterNotification API",
					createMasterNotificationReqGen.respvalidate.NodeValue(NotificationServiceNodes.SUCCESS_NODE.getNodePath(), true).equals(TRUE_VALUE));

			String masterNotificationId = JsonPath.read(createMasterNotificationResponse, "$.masterNotification.id");
			RequestGenerator createNotificationForSingleUserService = notificationServiceHelper.invokeCreateNotificationsForSingleUser(userId, portalGroup, masterNotificationId);
			String createNotificationForSingleUserResponse = createNotificationForSingleUserService.ResponseValidations.GetResponseAsString();
			System.out.println("\nPrinting NotificationService createNotificationForSingleUser API response :\n\n"+createNotificationForSingleUserResponse);
			log.info("\nPrinting NotificationService createNotificationForSingleUser API response :\n\n"+createNotificationForSingleUserResponse);
			
			AssertJUnit.assertTrue("Error while invoking createNotificationForSingleUser API",
					createNotificationForSingleUserService.respvalidate.NodeValue(NotificationServiceNodes.SUCCESS_NODE.getNodePath(), true).equals(TRUE_VALUE));
		}
		
		RequestGenerator getActiveNotificationsReqGen = notificationServiceHelper.invokeGetActiveNotifications(userId, portalGroup);
		String getActiveNotificationsResponse = getActiveNotificationsReqGen.ResponseValidations.GetResponseAsString();
		System.out.println("\nPrinting NotificationService getActiveNotifications API response :\n\n"+getActiveNotificationsResponse);
		log.info("\nPrinting NotificationService getActiveNotifications API response :\n\n"+getActiveNotificationsResponse);
		
		AssertJUnit.assertTrue("Error while invoking NotificationService getActiveNotifications API", 
				getActiveNotificationsReqGen.respvalidate.NodeValue(NotificationServiceNodes.SUCCESS_NODE.getNodePath(), true).contains(TRUE_VALUE));
		
		AssertJUnit.assertTrue("notifications node doesn't exists", 
				getActiveNotificationsReqGen.respvalidate.DoesNodeExists(NotificationServiceNodes.NOTIFICATIONS.getNodePath()));
		
		List<JSONObject> getActiveNotificationsJson = JsonPath.read(getActiveNotificationsResponse, "$.notifications[*]");
		if(!CollectionUtils.isEmpty(getActiveNotificationsJson))
		{
			for(int i = 0; i < getActiveNotificationsJson.size(); i++)
			{
				String activeNotificationJson = (String) getActiveNotificationsJson.get(i).toString();
				for(String activeNotificationNode : NotificationServiceNodes.getActiveNotificationNodes())
				{
					AssertJUnit.assertTrue(activeNotificationNode+" is not exists in response", apiUtil.Exists(activeNotificationNode, activeNotificationJson));
				}
			}
		} 
	}
	
	/**
	 * This TestCase is used to mark as read the active notifications for the user and verification for success response
	 * 
	 * @param userId
	 * @param portalGroup
	 */
	@Test(groups = { "Smoke", "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression" },
			dataProvider = "NotificationServiceDP_markAsReadNotification_verifySuccessResponse")
	public void NotificationService_markAsReadNotification_verifySuccessResponse(String userId, String portalGroup)
	{
		RequestGenerator getActiveNotificationsCountReqGen = notificationServiceHelper.invokeGetActiveNotificationsCount(userId, portalGroup);
		String getActiveNotificationsCountResponse = getActiveNotificationsCountReqGen.ResponseValidations.GetResponseAsString();
		System.out.println("\nPrinting NotificationService getActiveNotificationsCount API response :\n\n"+getActiveNotificationsCountResponse+"\n");
		log.info("\nPrinting NotificationService getActiveNotificationsCount API response :\n\n"+getActiveNotificationsCountResponse+"\n");
		
		if(getActiveNotificationsCountReqGen.respvalidate.NodeValue(NotificationServiceNodes.NUMOFACTIVENOTIFICATIONS_NODE.getNodePath(), true).equals(ZERO_VALUE))
		{
			String publishTime = String.valueOf(Calendar.getInstance().getTimeInMillis());
			Calendar startTimeCal = Calendar.getInstance();
			startTimeCal.add(Calendar.HOUR, 2);
			String startTime = String.valueOf(startTimeCal.getTimeInMillis());
			Calendar endTimeCal = Calendar.getInstance();
			endTimeCal.add(Calendar.HOUR, 4);
			String endTime = String.valueOf(endTimeCal.getTimeInMillis());
			
			RequestGenerator createMasterNotificationReqGen = notificationServiceHelper.invokeCreateMasterNotification(PUMA_PROD_NOTIFICATION_TEXT, PUMA_PROD_NOTIFICATION_TITLE, 
					PUMA_PROD_NOTIFICATION_IMG_URL, PUMA_PROD_NOTIFICATION_PAGE_URL, publishTime, startTime, endTime, NOTIFICATION_TYPE_MARKETING, portalGroup);
			String createMasterNotificationResponse = createMasterNotificationReqGen.ResponseValidations.GetResponseAsString();
			System.out.println("\nPrinting NotificationService createMasterNotification API response :\n\n"+createMasterNotificationResponse+"\n");
			log.info("\nPrinting NotificationService createMasterNotification API response :\n\n"+createMasterNotificationResponse+"\n");
			
			AssertJUnit.assertTrue("Error while invoking createMasterNotification API",
					createMasterNotificationReqGen.respvalidate.NodeValue(NotificationServiceNodes.SUCCESS_NODE.getNodePath(), true).equals(TRUE_VALUE));

			String masterNotificationId = JsonPath.read(createMasterNotificationResponse, "$.masterNotification.id");
			RequestGenerator createNotificationForSingleUserService = notificationServiceHelper.invokeCreateNotificationsForSingleUser(userId, portalGroup, masterNotificationId);
			String createNotificationForSingleUserResponse = createNotificationForSingleUserService.ResponseValidations.GetResponseAsString();
			System.out.println("\nPrinting NotificationService createNotificationForSingleUser API response :\n\n"+createNotificationForSingleUserResponse);
			log.info("\nPrinting NotificationService createNotificationForSingleUser API response :\n\n"+createNotificationForSingleUserResponse);
			
			AssertJUnit.assertTrue("Error while invoking createNotificationForSingleUser API",
					createNotificationForSingleUserService.respvalidate.NodeValue(NotificationServiceNodes.SUCCESS_NODE.getNodePath(), true).equals(TRUE_VALUE));
		}
		
		RequestGenerator getActiveNotificationsReqGen = notificationServiceHelper.invokeGetActiveNotifications(userId, portalGroup);
		String getActiveNotificationsResponse = getActiveNotificationsReqGen.ResponseValidations.GetResponseAsString();
		System.out.println("\nPrinting NotificationService getActiveNotifications API response :\n\n"+getActiveNotificationsResponse);
		log.info("\nPrinting NotificationService getActiveNotifications API response :\n\n"+getActiveNotificationsResponse);
		
		AssertJUnit.assertTrue("Error while invoking NotificationService getActiveNotifications API", 
				getActiveNotificationsReqGen.respvalidate.NodeValue(NotificationServiceNodes.SUCCESS_NODE.getNodePath(), true).contains(TRUE_VALUE));
		
		String publishTime = String.valueOf(JsonPath.read(getActiveNotificationsResponse, "$.notifications[0].notification.publishTime"));
		String masterNotificationId = JsonPath.read(getActiveNotificationsResponse, "$.notifications[0].notification.masterNotificationId");
		System.out.println("Printing publishTime : "+publishTime+" : masterNotificationId : "+masterNotificationId);
		RequestGenerator markAsReadNotificationReqGen = notificationServiceHelper.invokeMarkAsReadNotification(userId, portalGroup, publishTime, masterNotificationId);
		String markAsReadNotificationResponse = markAsReadNotificationReqGen.ResponseValidations.GetResponseAsString();
		System.out.println("\nPrinting NotificationService markAsReadNotification API response :\n\n"+markAsReadNotificationResponse);
		log.info("\nPrinting NotificationService markAsReadNotification API response :\n\n"+markAsReadNotificationResponse);
		
		AssertJUnit.assertEquals("NotificationService getActiveNotifications API is not working", 200, markAsReadNotificationReqGen.response.getStatus());
	}
	
	/**
	 * This TestCase is used to mark as read the active notifications for the user and verification for failure response
	 * 
	 * @param userId
	 * @param portalGroup
	 * @param publishTime
	 * @param masterNotificationId
	 */
	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression" }, 
			dataProvider = "NotificationServiceDP_markAsReadNotification_verifyFailureResponse")
	public void NotificationService_markAsReadNotification_verifyFailureResponse(String userId, String portalGroup, String publishTime, String masterNotificationId)
	{
		RequestGenerator markAsReadNotificationReqGen = notificationServiceHelper.invokeMarkAsReadNotification(userId, portalGroup, publishTime, masterNotificationId);
		String markAsReadNotificationResponse = markAsReadNotificationReqGen.ResponseValidations.GetResponseAsString();
		System.out.println("\nPrinting NotificationService markAsReadNotification API response :\n\n"+markAsReadNotificationResponse);
		log.info("\nPrinting NotificationService markAsReadNotification API response :\n\n"+markAsReadNotificationResponse);
		
		AssertJUnit.assertTrue("Error while invoking NotificationService markAsReadNotification API", 
				markAsReadNotificationReqGen.respvalidate.NodeValue(NotificationServiceNodes.SUCCESS_NODE.getNodePath(), true).contains(FALSE_VALUE));
	}
	
	/**
	 * This TestCase is used to mark as read the active notifications for the user and verification for node values
	 * 
	 * @param userId
	 * @param portalGroup
	 */
	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression" },
			dataProvider = "NotificationServiceDP_markAsReadNotification_verifySuccessResponse")
	public void NotificationService_markAsReadNotification_verifyNodeValues(String userId, String portalGroup)
	{
		RequestGenerator getActiveNotificationsCountReqGen = notificationServiceHelper.invokeGetActiveNotificationsCount(userId, portalGroup);
		String getActiveNotificationsCountResponse = getActiveNotificationsCountReqGen.ResponseValidations.GetResponseAsString();
		System.out.println("\nPrinting NotificationService getActiveNotificationsCount API response :\n\n"+getActiveNotificationsCountResponse+"\n");
		log.info("\nPrinting NotificationService getActiveNotificationsCount API response :\n\n"+getActiveNotificationsCountResponse+"\n");
		
		if(getActiveNotificationsCountReqGen.respvalidate.NodeValue(NotificationServiceNodes.NUMOFACTIVENOTIFICATIONS_NODE.getNodePath(), true).equals(ZERO_VALUE))
		{
			String publishTime = String.valueOf(Calendar.getInstance().getTimeInMillis());
			Calendar startTimeCal = Calendar.getInstance();
			startTimeCal.add(Calendar.HOUR, 2);
			String startTime = String.valueOf(startTimeCal.getTimeInMillis());
			Calendar endTimeCal = Calendar.getInstance();
			endTimeCal.add(Calendar.HOUR, 4);
			String endTime = String.valueOf(endTimeCal.getTimeInMillis());
			
			RequestGenerator createMasterNotificationReqGen = notificationServiceHelper.invokeCreateMasterNotification(NIKE_PROD_NOTIFICATION_TEXT, NIKE_PROD_NOTIFICATION_TITLE, 
					NIKE_PROD_NOTIFICATION_IMG_URL, NIKE_PROD_NOTIFICATION_PAGE_URL, publishTime, startTime, endTime, NOTIFICATION_TYPE_MARKETING, portalGroup);
			String createMasterNotificationResponse = createMasterNotificationReqGen.ResponseValidations.GetResponseAsString();
			System.out.println("\nPrinting NotificationService createMasterNotification API response :\n\n"+createMasterNotificationResponse+"\n");
			log.info("\nPrinting NotificationService createMasterNotification API response :\n\n"+createMasterNotificationResponse+"\n");
			
			AssertJUnit.assertTrue("Error while invoking createMasterNotification API",
					createMasterNotificationReqGen.respvalidate.NodeValue(NotificationServiceNodes.SUCCESS_NODE.getNodePath(), true).equals(TRUE_VALUE));

			String masterNotificationId = JsonPath.read(createMasterNotificationResponse, "$.masterNotification.id");
			RequestGenerator createNotificationForSingleUserService = notificationServiceHelper.invokeCreateNotificationsForSingleUser(userId, portalGroup, masterNotificationId);
			String createNotificationForSingleUserResponse = createNotificationForSingleUserService.ResponseValidations.GetResponseAsString();
			System.out.println("\nPrinting NotificationService createNotificationForSingleUser API response :\n\n"+createNotificationForSingleUserResponse);
			log.info("\nPrinting NotificationService createNotificationForSingleUser API response :\n\n"+createNotificationForSingleUserResponse);
			
			AssertJUnit.assertTrue("Error while invoking createNotificationForSingleUser API",
					createNotificationForSingleUserService.respvalidate.NodeValue(NotificationServiceNodes.SUCCESS_NODE.getNodePath(), true).equals(TRUE_VALUE));
		}
		
		RequestGenerator getActiveNotificationsReqGen = notificationServiceHelper.invokeGetActiveNotifications(userId, portalGroup);
		String getActiveNotificationsResponse = getActiveNotificationsReqGen.ResponseValidations.GetResponseAsString();
		System.out.println("\nPrinting NotificationService getActiveNotifications API response :\n\n"+getActiveNotificationsResponse);
		log.info("\nPrinting NotificationService getActiveNotifications API response :\n\n"+getActiveNotificationsResponse);
		
		AssertJUnit.assertTrue("Error while invoking NotificationService getActiveNotifications API", 
				getActiveNotificationsReqGen.respvalidate.NodeValue(NotificationServiceNodes.SUCCESS_NODE.getNodePath(), true).contains(TRUE_VALUE));
		
		String publishTime = String.valueOf(JsonPath.read(getActiveNotificationsResponse, "$.notifications[0].notification.publishTime"));
		String masterNotificationId = JsonPath.read(getActiveNotificationsResponse, "$.notifications[0].notification.masterNotificationId");
		System.out.println("Printing publishTime : "+publishTime+" : masterNotificationId : "+masterNotificationId);
		RequestGenerator markAsReadNotificationReqGen = notificationServiceHelper.invokeMarkAsReadNotification(userId, portalGroup, publishTime, masterNotificationId);
		String markAsReadNotificationResponse = markAsReadNotificationReqGen.ResponseValidations.GetResponseAsString();
		System.out.println("\nPrinting NotificationService markAsReadNotification API response :\n\n"+markAsReadNotificationResponse);
		log.info("\nPrinting NotificationService markAsReadNotification API response :\n\n"+markAsReadNotificationResponse);
				
		AssertJUnit.assertTrue("Error while invoking NotificationService markAsReadNotification API", 
			markAsReadNotificationReqGen.respvalidate.NodeValue(NotificationServiceNodes.SUCCESS_NODE.getNodePath(), true).contains(TRUE_VALUE));
		
		AssertJUnit.assertTrue("markAsReadNotification response nodes are invalid",
				markAsReadNotificationReqGen.respvalidate.DoesNodesExists(NotificationServiceNodes.getMarkAsReadNotificationNodes()));
	}
	
	/**
	 * This TestCase is used to mark as read the active notifications for the user and verification for success response code(200)
	 * 
	 * @param userId
	 * @param portalGroup
	 * @param imageUrl
	 * @param pageUrl
	 * @param startTime
	 * @param endTime
	 * @param notificationText
	 * @param notificationTitle
	 * @param notificationId
	 * @param notificationType
	 */
	@Test(groups = { "Smoke", "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression" }, 
			dataProvider = "NotificationServiceDP_createNotificationForUser_verifySuccessResponse")
	public void NotificationService_createNotificationsForUser_verifySuccessResponse(String userId, String portalGroup, String imageUrl, String pageUrl, String startTime,
			String endTime, String notificationText, String notificationTitle, String notificationId, String notificationType) 
	{
		RequestGenerator createNotificationForUserService = notificationServiceHelper.invokeCreateNotificationsForUser(userId, portalGroup, imageUrl, pageUrl, startTime,
				endTime, notificationText, notificationTitle, notificationId, notificationType);
		String createNotificationForUserResponse = createNotificationForUserService.ResponseValidations.GetResponseAsString();
		System.out.println("\nPrinting NotificationService createNotificationForUser API response :\n\n"+createNotificationForUserResponse);
		log.info("\nPrinting NotificationService createNotificationForUser API response :\n\n"+createNotificationForUserResponse);
		
		AssertJUnit.assertEquals("NotificationService createNotificationForUser API is not working", 200, 
				createNotificationForUserService.response.getStatus());
	}
	
	/**
	 *  This TestCase is used to mark as read the active notifications for the user and verification for failure response
	 * 
	 * @param userId
	 * @param portalGroup
	 * @param imageUrl
	 * @param pageUrl
	 * @param startTime
	 * @param endTime
	 * @param notificationText
	 * @param notificationTitle
	 * @param notificationId
	 * @param notificationType
	 */
	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression" }, 
			dataProvider = "NotificationServiceDP_createNotificationForUser_verifyFailureResponse")
	public void NotificationService_createNotificationsForUser_verifyFailureResponse(String userId, String portalGroup, String imageUrl, String pageUrl, String startTime,
			String endTime, String notificationText, String notificationTitle, String notificationId, String notificationType) 
	{
		RequestGenerator createNotificationForUserReqGen = notificationServiceHelper.invokeCreateNotificationsForUser(userId, portalGroup, imageUrl, pageUrl, startTime,
				endTime, notificationText, notificationTitle, notificationId, notificationType);
		String createNotificationForUserResponse = createNotificationForUserReqGen.ResponseValidations.GetResponseAsString();
		System.out.println("\nPrinting NotificationService createNotificationForUser API response :\n\n"+createNotificationForUserResponse);
		log.info("\nPrinting NotificationService createNotificationForUser API response :\n\n"+createNotificationForUserResponse);
		
		AssertJUnit.assertEquals("NotificationService createNotificationForUser API is not working", 404, 
				createNotificationForUserReqGen.response.getStatus());
		
		AssertJUnit.assertTrue("Error while invoking NotificationService createNotificationForUser API", 
				createNotificationForUserReqGen.respvalidate.NodeValue(NotificationServiceNodes.SUCCESS_NODE.getNodePath(), true).contains(FALSE_VALUE));
			
	}
	
	/**
	 *  This TestCase is used to mark as read the active notifications for the user and verification for node values
	 * 
	 * @param userId
	 * @param portalGroup
	 * @param imageUrl
	 * @param pageUrl
	 * @param startTime
	 * @param endTime
	 * @param notificationText
	 * @param notificationTitle
	 * @param notificationId
	 * @param notificationType
	 */
	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression" }, 
			dataProvider = "NotificationServiceDP_createNotificationForUser_verifyNodeValues")
	public void NotificationService_createNotificationsForUser_verifyNodeValues(String userId, String portalGroup, String imageUrl, String pageUrl, String startTime,
			String endTime, String notificationText, String notificationTitle, String notificationId, String notificationType) 
	{
		RequestGenerator createNotificationForUserReqGen = notificationServiceHelper.invokeCreateNotificationsForUser(userId, portalGroup, imageUrl, pageUrl, startTime,
				endTime, notificationText, notificationTitle, notificationId, notificationType);
		String createNotificationForUserResponse = createNotificationForUserReqGen.ResponseValidations.GetResponseAsString();
		System.out.println("\nPrinting NotificationService createNotificationForUser API response :\n\n"+createNotificationForUserResponse);
		log.info("\nPrinting NotificationService createNotificationForUser API response :\n\n"+createNotificationForUserResponse);
		
		AssertJUnit.assertTrue("Error while invoking NotificationService createNotificationForUser API", 
				createNotificationForUserReqGen.respvalidate.NodeValue(NotificationServiceNodes.SUCCESS_NODE.getNodePath(), true).contains(TRUE_VALUE));
			
		AssertJUnit.assertTrue("createNotificationForUser response nodes are invalid",
				createNotificationForUserReqGen.respvalidate.DoesNodesExists(NotificationServiceNodes.getCreateNotificationForUserNodes()));
	}
	
	@Test(groups = { "SchemaValidation" }, dataProvider = "NotificationServiceDP_createMasterNotification_verifyResponseDataNodesUsingSchemaValidations")
	public void NotificationService_createMasterNotification_verifyResponseDataNodesUsingSchemaValidations(String notificationText, String notificationTitle, 
			String imageUrl, String pageUrl,String startTime, String publishTime, String endTime, String notificationType, String forPortalGroup)
	{
		RequestGenerator createMasterNotificationReqGen = notificationServiceHelper.invokeCreateMasterNotification(notificationText, notificationTitle, imageUrl, pageUrl,
				publishTime, startTime, endTime, notificationType, forPortalGroup);
		String createMasterNotificationResponse = createMasterNotificationReqGen.ResponseValidations.GetResponseAsString();
		System.out.println("\nPrinting NotificationService createMasterNotification API response :\n\n"+createMasterNotificationResponse+"\n");
		log.info("\nPrinting NotificationService createMasterNotification API response :\n\n"+createMasterNotificationResponse+"\n");
		
		AssertJUnit.assertEquals("NotificationService createMasterNotification API is not working", 200, createMasterNotificationReqGen.response.getStatus());
		
		try {
            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/notificationservice-createmasternotification-schema.txt");
            List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(createMasterNotificationResponse, jsonschema);
            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in NotificationService createMasterNotification API response", 
            		CollectionUtils.isEmpty(missingNodeList));
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	@Test(groups = { "SchemaValidation" }, dataProvider = "NotificationServiceDP_searchMasterNotification_verifyResponseDataNodesUsingSchemaValidations")
	public void NotificationService_searchMasterNotification_verifyResponseDataNodesUsingSchemaValidations(String portalGroup, String notificationType)
	{
		RequestGenerator searchMasterNotificationReqGen = notificationServiceHelper.invokeSearchMasterNotification(portalGroup, notificationType);
		String searchMasterNotificationResponse = searchMasterNotificationReqGen.ResponseValidations.GetResponseAsString();
		System.out.println("\nPrinting NotificationService searchMasterNotification API response :\n\n"+searchMasterNotificationResponse+"\n");
		log.info("\nPrinting NotificationService searchMasterNotification API response :\n\n"+searchMasterNotificationResponse+"\n");
		
		AssertJUnit.assertEquals("NotificationService searchMasterNotification API is not working", 200, searchMasterNotificationReqGen.response.getStatus());
		
		try {
            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/notificationservice-searchmasternotification-schema.txt");
            List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(searchMasterNotificationResponse, jsonschema);
            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in NotificationService searchMasterNotification API response", 
            		CollectionUtils.isEmpty(missingNodeList));
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	@Test(groups = { "SchemaValidation" }, dataProvider = "NotificationServiceDP_searchMasterNotificationWithSTET_verifyResponseDataNodesUsingSchemaValidations")
	public void NotificationService_searchMasterNotificationWithSTET_verifyResponseDataNodesUsingSchemaValidations(String portalGroup, String notificationType, String startTime, String endTime)
	{
		RequestGenerator searchMasterNotificationReqGen = notificationServiceHelper.invokeSearchMasterNotificationWithSTET(portalGroup, notificationType, startTime, endTime );
		String searchMasterNotificationResponse = searchMasterNotificationReqGen.ResponseValidations.GetResponseAsString();
		System.out.println("\nPrinting NotificationService searchMasterNotification with StartTime and EndTime API response :\n\n"+searchMasterNotificationResponse+"\n");
		log.info("\nPrinting NotificationService searchMasterNotification with StartTime and EndTime API response :\n\n"+searchMasterNotificationResponse+"\n");
		
		AssertJUnit.assertEquals("NotificationService searchMasterNotification with StartTime and EndTime API is not working", 200, searchMasterNotificationReqGen.response.getStatus());
		
		try {
            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/notificationservice-searchmasternotificationwithstet-schema.txt");
            List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(searchMasterNotificationResponse, jsonschema);
            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in NotificationService searchMasterNotification API response", 
            		CollectionUtils.isEmpty(missingNodeList));
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	@Test(groups = { "SchemaValidation" }, dataProvider = "NotificationServiceDP_getDetailsMasterNotification_verifyResponseDataNodesUsingSchemaValidations")
	public void NotificationService_getDetailsMasterNotification_verifyResponseDataNodesUsingSchemaValidations(String portalGroup, String notificationType)
	{
		String publishTime = String.valueOf(Calendar.getInstance().getTimeInMillis());
		Calendar startTimeCal = Calendar.getInstance();
		startTimeCal.add(Calendar.HOUR, 2);
		String startTime = String.valueOf(startTimeCal.getTimeInMillis());
		Calendar endTimeCal = Calendar.getInstance();
		endTimeCal.add(Calendar.HOUR, 4);
		String endTime = String.valueOf(endTimeCal.getTimeInMillis());
		
		RequestGenerator createMasterNotificationReqGen = notificationServiceHelper.invokeCreateMasterNotification(PUMA_PROD_NOTIFICATION_TEXT, PUMA_PROD_NOTIFICATION_TITLE, 
				PUMA_PROD_NOTIFICATION_IMG_URL, PUMA_PROD_NOTIFICATION_PAGE_URL, publishTime, startTime, endTime, notificationType, portalGroup);
		String createMasterNotificationResponse = createMasterNotificationReqGen.ResponseValidations.GetResponseAsString();
		System.out.println("\nPrinting NotificationService createMasterNotification API response :\n\n"+createMasterNotificationResponse+"\n");
		log.info("\nPrinting NotificationService createMasterNotification API response :\n\n"+createMasterNotificationResponse+"\n");
		
		AssertJUnit.assertTrue("Error while invoking createMasterNotification API",
				createMasterNotificationReqGen.respvalidate.NodeValue(NotificationServiceNodes.SUCCESS_NODE.getNodePath(), true).equals(TRUE_VALUE));

		String masterNotificationId = JsonPath.read(createMasterNotificationResponse, "$.masterNotification.id");
		System.out.println("Printing masterNotificationId : "+masterNotificationId);
		log.info("Printing masterNotificationId : "+masterNotificationId);
		
		RequestGenerator getDetailsMasterNotificationReqGen = notificationServiceHelper.invokeGetDetailsMasterNotification(portalGroup, masterNotificationId);
		String getDetailsMasterNotificationResponse = getDetailsMasterNotificationReqGen.ResponseValidations.GetResponseAsString();
		System.out.println("\nPrinting NotificationService getDetailsMasterNotification API response :\n\n"+getDetailsMasterNotificationResponse+"\n");
		log.info("\nPrinting NotificationService getDetailsMasterNotification API response :\n\n"+getDetailsMasterNotificationResponse+"\n");
		
		AssertJUnit.assertEquals("NotificationService getDetailsMasterNotification API is not working", 200, getDetailsMasterNotificationReqGen.response.getStatus());
		
		try {
            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/notificationservice-getdetailsmasternotification-schema.txt");
            List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(getDetailsMasterNotificationResponse, jsonschema);
            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in NotificationService getdetailsmasternotification API response", 
            		CollectionUtils.isEmpty(missingNodeList));
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	@Test(groups = { "SchemaValidation" }, dataProvider = "NotificationServiceDP_deleteNotifications_verifyResponseDataNodesUsingSchemaValidations")
	public void NotificationService_deleteNotifications_verifyResponseDataNodesUsingSchemaValidations(String portalGroup, String notificationType)
	{
		String publishTime = String.valueOf(Calendar.getInstance().getTimeInMillis());
		Calendar startTimeCal = Calendar.getInstance();
		startTimeCal.add(Calendar.HOUR, 2);
		String startTime = String.valueOf(startTimeCal.getTimeInMillis());
		Calendar endTimeCal = Calendar.getInstance();
		endTimeCal.add(Calendar.HOUR, 4);
		String endTime = String.valueOf(endTimeCal.getTimeInMillis());
		
		RequestGenerator createMasterNotificationReqGen = notificationServiceHelper.invokeCreateMasterNotification(NIKE_PROD_NOTIFICATION_TEXT, NIKE_PROD_NOTIFICATION_TITLE, 
				NIKE_PROD_NOTIFICATION_IMG_URL, NIKE_PROD_NOTIFICATION_PAGE_URL, publishTime, startTime, endTime, notificationType, portalGroup);
		String createMasterNotificationResponse = createMasterNotificationReqGen.ResponseValidations.GetResponseAsString();
		System.out.println("\nPrinting NotificationService createMasterNotification API response :\n\n"+createMasterNotificationResponse+"\n");
		log.info("\nPrinting NotificationService createMasterNotification API response :\n\n"+createMasterNotificationResponse+"\n");
		
		AssertJUnit.assertTrue("Error while invoking createMasterNotification API",
				createMasterNotificationReqGen.respvalidate.NodeValue(NotificationServiceNodes.SUCCESS_NODE.getNodePath(), true).equals(TRUE_VALUE));

		String masterNotificationId = JsonPath.read(createMasterNotificationResponse, "$.masterNotification.id");
		System.out.println("Printing masterNotificationId : "+masterNotificationId);
		log.info("Printing masterNotificationId : "+masterNotificationId);
		
		RequestGenerator deleteNotificationsReqGen = notificationServiceHelper.invokeDeleteNotifications(portalGroup, masterNotificationId);
		String deleteNotificationsResponse = deleteNotificationsReqGen.ResponseValidations.GetResponseAsString();
		System.out.println("\nPrinting NotificationService deleteNotifications API response :\n\n"+deleteNotificationsResponse+"\n");
		log.info("\nPrinting NotificationService deleteNotifications API response :\n\n"+deleteNotificationsResponse+"\n");
		
		AssertJUnit.assertEquals("NotificationService deleteNotifications API is not working", 200, deleteNotificationsReqGen.response.getStatus());
		
		try {
            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/notificationservice-deletenotifications-schema.txt");
            List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(deleteNotificationsResponse, jsonschema);
            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in NotificationService deleteNotifications API response", 
            		CollectionUtils.isEmpty(missingNodeList));
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	@Test(groups = { "SchemaValidation" }, dataProvider = "NotificationServiceDP_createNotificationForSingleUser_verifyResponseDataNodesUsingSchemaValidations")
	public void NotificationService_createNotificationsForSingleUser_verifyResponseDataNodesUsingSchemaValidations(String userId, String portalGroup, String notificationType) 
	{
		String publishTime = String.valueOf(Calendar.getInstance().getTimeInMillis());
		Calendar startTimeCal = Calendar.getInstance();
		startTimeCal.add(Calendar.HOUR, 2);
		String startTime = String.valueOf(startTimeCal.getTimeInMillis());
		Calendar endTimeCal = Calendar.getInstance();
		endTimeCal.add(Calendar.HOUR, 4);
		String endTime = String.valueOf(endTimeCal.getTimeInMillis());
		
		RequestGenerator createMasterNotificationReqGen = notificationServiceHelper.invokeCreateMasterNotification(FOSSIL_PROD_NOTIFICATION_TEXT, FOSSIL_PROD_NOTIFICATION_TITLE, 
				FOSSIL_PROD_NOTIFICATION_IMG_URL, FOSSIL_PROD_NOTIFICATION_PAGE_URL, publishTime, startTime, endTime, notificationType, portalGroup);
		String createMasterNotificationResponse = createMasterNotificationReqGen.ResponseValidations.GetResponseAsString();
		System.out.println("\nPrinting NotificationService createMasterNotification API response :\n\n"+createMasterNotificationResponse+"\n");
		log.info("\nPrinting NotificationService createMasterNotification API response :\n\n"+createMasterNotificationResponse+"\n");
		
		AssertJUnit.assertTrue("Error while invoking createMasterNotification API",
				createMasterNotificationReqGen.respvalidate.NodeValue(NotificationServiceNodes.SUCCESS_NODE.getNodePath(), true).equals(TRUE_VALUE));

		String masterNotificationId = JsonPath.read(createMasterNotificationResponse, "$.masterNotification.id");
		RequestGenerator createNotificationForSingleUserService = notificationServiceHelper.invokeCreateNotificationsForSingleUser(userId, portalGroup, masterNotificationId);
		String createNotificationForSingleUserResponse = createNotificationForSingleUserService.ResponseValidations.GetResponseAsString();
		System.out.println("\nPrinting NotificationService createNotificationForSingleUser API response :\n\n"+createNotificationForSingleUserResponse);
		log.info("\nPrinting NotificationService createNotificationForSingleUser API response :\n\n"+createNotificationForSingleUserResponse);
		
		AssertJUnit.assertEquals("NotificationService createNotificationForSingleUser API is not working", 200, 
				createNotificationForSingleUserService.response.getStatus());
		
		try {
            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/notificationservice-createnotificationforsingleuser-schema.txt");
            List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(createNotificationForSingleUserResponse, jsonschema);
            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in NotificationService createNotificationForSingleUser API response", 
            		CollectionUtils.isEmpty(missingNodeList));
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	@Test(groups = { "SchemaValidation" }, dataProvider = "NotificationServiceDP_createNotificationForSetOfUsers_verifyResponseDataNodesUsingSchemaValidations")
	public void NotificationService_createNotificationsForSetOfUsers_verifyResponseDataNodesUsingSchemaValidations(String portalGroup, String notificationType, 
			String userIds)
	{
		String publishTime = String.valueOf(Calendar.getInstance().getTimeInMillis());
		Calendar startTimeCal = Calendar.getInstance();
		startTimeCal.add(Calendar.HOUR, 2);
		String startTime = String.valueOf(startTimeCal.getTimeInMillis());
		Calendar endTimeCal = Calendar.getInstance();
		endTimeCal.add(Calendar.HOUR, 4);
		String endTime = String.valueOf(endTimeCal.getTimeInMillis());
		
		RequestGenerator createMasterNotificationReqGen = notificationServiceHelper.invokeCreateMasterNotification(CAT_PROD_NOTIFICATION_TEXT, CAT_PROD_NOTIFICATION_TITLE, 
				CAT_PROD_NOTIFICATION_IMG_URL, CAT_PROD_NOTIFICATION_PAGE_URL, publishTime, startTime, endTime, notificationType, portalGroup);
		String createMasterNotificationResponse = createMasterNotificationReqGen.ResponseValidations.GetResponseAsString();
		System.out.println("\nPrinting NotificationService createMasterNotification API response :\n\n"+createMasterNotificationResponse+"\n");
		log.info("\nPrinting NotificationService createMasterNotification API response :\n\n"+createMasterNotificationResponse+"\n");
		
		AssertJUnit.assertTrue("Error while invoking createMasterNotification API",
				createMasterNotificationReqGen.respvalidate.NodeValue(NotificationServiceNodes.SUCCESS_NODE.getNodePath(), true).equals(TRUE_VALUE));

		String masterNotificationId = JsonPath.read(createMasterNotificationResponse, "$.masterNotification.id");
		
		RequestGenerator createNotificationForSetOfUsersService = notificationServiceHelper.invokeCreateNotificationsForSetOfUsers(portalGroup, masterNotificationId, userIds);
		String createNotificationForSetOfUsersResponse = createNotificationForSetOfUsersService.ResponseValidations.GetResponseAsString();
		System.out.println("\nPrinting NotificationService createNotificationForSetOfUsers API response :\n\n"+createNotificationForSetOfUsersResponse);
		log.info("\nPrinting NotificationService createNotificationForSetOfUsers API response :\n\n"+createNotificationForSetOfUsersResponse);
		
		AssertJUnit.assertEquals("NotificationService createNotificationForSetOfUsers API is not working", 200, 
				createNotificationForSetOfUsersService.response.getStatus());
		
		try {
            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/notificationservice-createnotificationforsetofusers-schema.txt");
            List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(createNotificationForSetOfUsersResponse, jsonschema);
            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in NotificationService createNotificationForSetOfUsers API response", 
            		CollectionUtils.isEmpty(missingNodeList));
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	@Test(groups = { "SchemaValidation" }, dataProvider = "NotificationServiceDP_getActiveNotificationsCount_verifyResponseDataNodesUsingSchemaValidations")
	public void NotificationService_getActiveNotificationsCount_verifyResponseDataNodesUsingSchemaValidations(String userId, String portalGroup) 
	{
		RequestGenerator getActiveNotificationsCountReqGen = notificationServiceHelper.invokeGetActiveNotificationsCount(userId, portalGroup);
		String getActiveNotificationsCountResponse = getActiveNotificationsCountReqGen.ResponseValidations.GetResponseAsString();
		System.out.println("\nPrinting NotificationService getActiveNotificationsCount API response :\n\n"+getActiveNotificationsCountResponse+"\n");
		log.info("\nPrinting NotificationService getActiveNotificationsCount API response :\n\n"+getActiveNotificationsCountResponse+"\n");
		
		AssertJUnit.assertEquals("NotificationService getActiveNotificationsCount API is not working", 200, getActiveNotificationsCountReqGen.response.getStatus());
		
		try {
            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/notificationservice-getactivenotificationscount-schema.txt");
            List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(getActiveNotificationsCountResponse, jsonschema);
            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in NotificationService getActiveNotificationsCount API response", 
            		CollectionUtils.isEmpty(missingNodeList));
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	@Test(groups = { "SchemaValidation" }, dataProvider = "NotificationServiceDP_getActiveNotifications_verifyResponseDataNodesUsingSchemaValidations")
	public void NotificationService_getActiveNotifications_verifyResponseDataNodesUsingSchemaValidations(String userId, String portalGroup)
	{
		RequestGenerator getActiveNotificationsReqGen = notificationServiceHelper.invokeGetActiveNotifications(userId, portalGroup);
		String getActiveNotificationsResponse = getActiveNotificationsReqGen.ResponseValidations.GetResponseAsString();
		System.out.println("\nPrinting NotificationService getActiveNotifications API response :\n\n"+getActiveNotificationsResponse);
		log.info("\nPrinting NotificationService getActiveNotifications API response :\n\n"+getActiveNotificationsResponse);
		
		AssertJUnit.assertEquals("NotificationService getActiveNotifications API is not working", 200, getActiveNotificationsReqGen.response.getStatus());
		
		try {
            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/notificationservice-getactivenotifications-schema.txt");
            List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(getActiveNotificationsResponse, jsonschema);
            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in NotificationService getActiveNotifications API response", 
            		CollectionUtils.isEmpty(missingNodeList));
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	@Test(groups = { "SchemaValidation" }, dataProvider = "NotificationServiceDP_markAsReadNotification_verifyResponseDataNodesUsingSchemaValidations")
	public void NotificationService_markAsReadNotification_verifyResponseDataNodesUsingSchemaValidations(String userId, String portalGroup)
	{
		RequestGenerator getActiveNotificationsCountReqGen = notificationServiceHelper.invokeGetActiveNotificationsCount(userId, portalGroup);
		String getActiveNotificationsCountResponse = getActiveNotificationsCountReqGen.ResponseValidations.GetResponseAsString();
		System.out.println("\nPrinting NotificationService getActiveNotificationsCount API response :\n\n"+getActiveNotificationsCountResponse+"\n");
		log.info("\nPrinting NotificationService getActiveNotificationsCount API response :\n\n"+getActiveNotificationsCountResponse+"\n");
		
		if(getActiveNotificationsCountReqGen.respvalidate.NodeValue(NotificationServiceNodes.NUMOFACTIVENOTIFICATIONS_NODE.getNodePath(), true).equals(ZERO_VALUE))
		{
			String publishTime = String.valueOf(Calendar.getInstance().getTimeInMillis());
			Calendar startTimeCal = Calendar.getInstance();
			startTimeCal.add(Calendar.HOUR, 2);
			String startTime = String.valueOf(startTimeCal.getTimeInMillis());
			Calendar endTimeCal = Calendar.getInstance();
			endTimeCal.add(Calendar.HOUR, 4);
			String endTime = String.valueOf(endTimeCal.getTimeInMillis());
			
			RequestGenerator createMasterNotificationReqGen = notificationServiceHelper.invokeCreateMasterNotification(PUMA_PROD_NOTIFICATION_TEXT, PUMA_PROD_NOTIFICATION_TITLE, 
					PUMA_PROD_NOTIFICATION_IMG_URL, PUMA_PROD_NOTIFICATION_PAGE_URL, publishTime, startTime, endTime, NOTIFICATION_TYPE_MARKETING, portalGroup);
			String createMasterNotificationResponse = createMasterNotificationReqGen.ResponseValidations.GetResponseAsString();
			System.out.println("\nPrinting NotificationService createMasterNotification API response :\n\n"+createMasterNotificationResponse+"\n");
			log.info("\nPrinting NotificationService createMasterNotification API response :\n\n"+createMasterNotificationResponse+"\n");
			
			AssertJUnit.assertTrue("Error while invoking createMasterNotification API",
					createMasterNotificationReqGen.respvalidate.NodeValue(NotificationServiceNodes.SUCCESS_NODE.getNodePath(), true).equals(TRUE_VALUE));

			String masterNotificationId = JsonPath.read(createMasterNotificationResponse, "$.masterNotification.id");
			RequestGenerator createNotificationForSingleUserService = notificationServiceHelper.invokeCreateNotificationsForSingleUser(userId, portalGroup, masterNotificationId);
			String createNotificationForSingleUserResponse = createNotificationForSingleUserService.ResponseValidations.GetResponseAsString();
			System.out.println("\nPrinting NotificationService createNotificationForSingleUser API response :\n\n"+createNotificationForSingleUserResponse);
			log.info("\nPrinting NotificationService createNotificationForSingleUser API response :\n\n"+createNotificationForSingleUserResponse);
			
			AssertJUnit.assertTrue("Error while invoking createNotificationForSingleUser API",
					createNotificationForSingleUserService.respvalidate.NodeValue(NotificationServiceNodes.SUCCESS_NODE.getNodePath(), true).equals(TRUE_VALUE));
		}
		
		RequestGenerator getActiveNotificationsReqGen = notificationServiceHelper.invokeGetActiveNotifications(userId, portalGroup);
		String getActiveNotificationsResponse = getActiveNotificationsReqGen.ResponseValidations.GetResponseAsString();
		System.out.println("\nPrinting NotificationService getActiveNotifications API response :\n\n"+getActiveNotificationsResponse);
		log.info("\nPrinting NotificationService getActiveNotifications API response :\n\n"+getActiveNotificationsResponse);
		
		AssertJUnit.assertTrue("Error while invoking NotificationService getActiveNotifications API", 
				getActiveNotificationsReqGen.respvalidate.NodeValue(NotificationServiceNodes.SUCCESS_NODE.getNodePath(), true).contains(TRUE_VALUE));
		
		String publishTime = String.valueOf(JsonPath.read(getActiveNotificationsResponse, "$.notifications[0].notification.publishTime"));
		String masterNotificationId = JsonPath.read(getActiveNotificationsResponse, "$.notifications[0].notification.masterNotificationId");
		System.out.println("Printing publishTime : "+publishTime+" : masterNotificationId : "+masterNotificationId);
		RequestGenerator markAsReadNotificationReqGen = notificationServiceHelper.invokeMarkAsReadNotification(userId, portalGroup, publishTime, masterNotificationId);
		String markAsReadNotificationResponse = markAsReadNotificationReqGen.ResponseValidations.GetResponseAsString();
		System.out.println("\nPrinting NotificationService markAsReadNotification API response :\n\n"+markAsReadNotificationResponse);
		log.info("\nPrinting NotificationService markAsReadNotification API response :\n\n"+markAsReadNotificationResponse);
		
		AssertJUnit.assertEquals("NotificationService markAsReadNotification API is not working", 200, markAsReadNotificationReqGen.response.getStatus());
		
		try {
            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/notificationservice-markasreadnotification-schema.txt");
            List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(markAsReadNotificationResponse, jsonschema);
            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in NotificationService markAsReadNotification API response", 
            		CollectionUtils.isEmpty(missingNodeList));
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	@Test(groups = { "SchemaValidation" }, dataProvider = "NotificationServiceDP_createNotificationForUser_verifyResponseDataNodesUsingSchemaValidations")
	public void NotificationService_createNotificationsForUser_verifyResponseDataNodesUsingSchemaValidations(String userId, String portalGroup, String imageUrl, String pageUrl, String startTime,
			String endTime, String notificationText, String notificationTitle, String notificationId, String notificationType) 
	{
		RequestGenerator createNotificationForUserService = notificationServiceHelper.invokeCreateNotificationsForUser(userId, portalGroup, imageUrl, pageUrl, startTime,
				endTime, notificationText, notificationTitle, notificationId, notificationType);
		String createNotificationForUserResponse = createNotificationForUserService.ResponseValidations.GetResponseAsString();
		System.out.println("\nPrinting NotificationService createNotificationForUser API response :\n\n"+createNotificationForUserResponse);
		log.info("\nPrinting NotificationService createNotificationForUser API response :\n\n"+createNotificationForUserResponse);
		
		AssertJUnit.assertEquals("NotificationService createNotificationForUser API is not working", 200, createNotificationForUserService.response.getStatus());
		
		try {
            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/notificationservice-createnotificationforuser-schema.txt");
            List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(createNotificationForUserResponse, jsonschema);
            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in NotificationService createNotificationForUser API response", 
            		CollectionUtils.isEmpty(missingNodeList));
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
}