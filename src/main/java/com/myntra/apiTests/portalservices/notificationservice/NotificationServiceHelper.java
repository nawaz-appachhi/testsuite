package com.myntra.apiTests.portalservices.notificationservice;
import com.myntra.apiTests.common.Myntra;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.myntra.lordoftherings.Initialize;
import com.myntra.apiTests.common.APINAME;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.PayloadType;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.apiTests.common.ServiceType;

/**
 * @author shankara.c
 *
 */
public class NotificationServiceHelper 
{
	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(NotificationServiceHelper.class);
	
	/**
	 * This method is used to invoke NotificationService getActiveNotificationsCount API
	 * 
	 * @param userId
	 * @param portalGroup
	 * @return RequestGenerator
	 */
	public RequestGenerator invokeGetActiveNotificationsCount(String userId, String portalGroup) 
	{
		MyntraService getActiveNotificationsCountService = Myntra.getService(ServiceType.PORTAL_NOTIFICATIONS, APINAME.GETACTIVENOTIFICATIONSCOUNT,
				init.Configurations, PayloadType.JSON, new String[] { userId, portalGroup }, PayloadType.JSON);
		System.out.println("\nPrinting NotificationService getActiveNotificationsCount API URL :"+getActiveNotificationsCountService.URL);
		log.info("\nPrinting NotificationService getActiveNotificationsCount API URL :"+getActiveNotificationsCountService.URL);
		
		System.out.println("\nPrinting NotificationService getActiveNotificationsCount API Payload :\n\n"+getActiveNotificationsCountService.Payload);
		log.info("\nPrinting NotificationService getActiveNotificationsCount API Payload :\n\n"+getActiveNotificationsCountService.Payload);
		
		return new RequestGenerator(getActiveNotificationsCountService);
	}
	
	/**
	 * This method is used to invoke NotificationService getActiveNotifications API
	 * 
	 * @param userId
	 * @param portalGroup
	 * @return RequestGenerator
	 */
	public RequestGenerator invokeGetActiveNotifications(String userId, String portalGroup)
	{
		MyntraService getActiveNotificationsService = Myntra.getService(ServiceType.PORTAL_NOTIFICATIONS, APINAME.GETACTIVENOTIFICATIONS, init.Configurations,
				PayloadType.JSON, new String[] { userId, portalGroup }, PayloadType.JSON);
		System.out.println("\nPrinting NotificationService getActiveNotifications API URL :"+getActiveNotificationsService.URL);
		log.info("\nPrinting NotificationService getActiveNotifications API URL :"+getActiveNotificationsService.URL);
		
		System.out.println("\nPrinting NotificationService getActiveNotifications API Payload :\n\n"+getActiveNotificationsService.Payload);
		log.info("\nPrinting NotificationService getActiveNotifications API Payload :\n\n"+getActiveNotificationsService.Payload);
		
		return new RequestGenerator(getActiveNotificationsService);
	}
	
	/**
	 * This method is used to invoke NotificationService deleteNotifications API
	 * 
	 * @param portalGroup
	 * @param masterNotificationId
	 * @return RequestGenerator
	 */
	public RequestGenerator invokeDeleteNotifications(String portalGroup, String masterNotificationId)
	{
		MyntraService deleteNotificationsService = Myntra.getService(ServiceType.PORTAL_NOTIFICATIONS, APINAME.DELETENOTIFICATIONS, init.Configurations, PayloadType.JSON,
				new String[]{ portalGroup, masterNotificationId }, PayloadType.JSON);
		System.out.println("\nPrinting NotificationService deleteNotifications API URL :"+deleteNotificationsService.URL);
		log.info("\nPrinting NotificationService deleteNotifications API URL :"+deleteNotificationsService.URL);
		
		System.out.println("\nPrinting NotificationService deleteNotifications API Payload :\n\n"+deleteNotificationsService.Payload);
		log.info("\nPrinting NotificationService deleteNotifications API Payload :\n\n"+deleteNotificationsService.Payload);
		
		return new RequestGenerator(deleteNotificationsService);
	}
	
	/**
	 * This method is used to invoke NotificationService createNotificationsForSingleUser API
	 * 
	 * @param userId
	 * @param portalGroup
	 * @param masterNotificationId
	 * @return RequestGenerator
	 */
	public RequestGenerator invokeCreateNotificationsForSingleUser(String userId, String portalGroup, String masterNotificationId)
	{
		MyntraService createNotificationsForSingleUserService = Myntra.getService(ServiceType.PORTAL_NOTIFICATIONS,	APINAME.CREATENOTIFICATIONSINGLEUSER,
				init.Configurations,PayloadType.JSON, new String[]{ userId, portalGroup, masterNotificationId }, PayloadType.JSON);
		System.out.println("\nPrinting NotificationService createNotificationsForSingleUser API URL :"+createNotificationsForSingleUserService.URL);
		log.info("\nPrinting NotificationService createNotificationsForSingleUser API URL :"+createNotificationsForSingleUserService.URL);
		
		System.out.println("\nPrinting NotificationService createNotificationsForSingleUser API Payload :\n\n"+createNotificationsForSingleUserService.Payload);
		log.info("\nPrinting NotificationService createNotificationsForSingleUser API Payload :\n\n"+createNotificationsForSingleUserService.Payload);
		
		return new RequestGenerator(createNotificationsForSingleUserService);
	}
	
	/**
	 * This method is used to invoke NotificationService createNotificationsForSetOfUsers API
	 * 
	 * @param portalGroup
	 * @param masterNotificationId
	 * @param userIds
	 * @return RequestGenerator
	 */
	public RequestGenerator invokeCreateNotificationsForSetOfUsers(String portalGroup, String masterNotificationId, String userIds)
	{
		MyntraService createNotificationsForSetOfUsersService = Myntra.getService(ServiceType.PORTAL_NOTIFICATIONS, APINAME.CREATENOTIFICATIONSETOFUSER,
				init.Configurations, PayloadType.JSON, new String[]{ portalGroup, masterNotificationId, userIds }, PayloadType.JSON);
		System.out.println("\nPrinting NotificationService createNotificationsForSetOfUsers API URL :"+createNotificationsForSetOfUsersService.URL);
		log.info("\nPrinting NotificationService createNotificationsForSetOfUsers API URL :"+createNotificationsForSetOfUsersService.URL);
		
		System.out.println("\nPrinting NotificationService createNotificationsForSetOfUsers API Payload :\n\n"+createNotificationsForSetOfUsersService.Payload);
		log.info("\nPrinting NotificationService createNotificationsForSetOfUsers API Payload :\n\n"+createNotificationsForSetOfUsersService.Payload);
		
		return new RequestGenerator(createNotificationsForSetOfUsersService);
	}
	
	/**
	 * This method is used to invoke NotificationService getPortalGroups API	 
	 * 
	 * @return RequestGenerator
	 */
	public RequestGenerator invokeGetAllPortalGroups()
	{
		MyntraService getAllPortalGroupsService = Myntra.getService(ServiceType.PORTAL_NOTIFICATIONS, APINAME.GETPORTALGROUPS, init.Configurations,
				PayloadType.JSON, PayloadType.JSON);
		System.out.println("\nPrinting NotificationService getPortalGroups API URL :"+getAllPortalGroupsService.URL);
		log.info("\nPrinting NotificationService getPortalGroups API URL :"+getAllPortalGroupsService.URL);
		
		System.out.println("\nPrinting NotificationService getPortalGroups API Payload :\n\n"+getAllPortalGroupsService.Payload);
		log.info("\nPrinting NotificationService getPortalGroups API Payload :\n\n"+getAllPortalGroupsService.Payload);
		
		return new RequestGenerator(getAllPortalGroupsService);
	}
	
	/**
	 * This method is used to invoke NotificationService getNotificationTypes API
	 * 
	 * @return RequestGenerator
	 */
	public RequestGenerator invokeGetNotificationTypes()
	{
		MyntraService getNotificationTypesService = Myntra.getService(ServiceType.PORTAL_NOTIFICATIONS, APINAME.GETNOTIFICATIONTYPES, init.Configurations,
				PayloadType.JSON, PayloadType.JSON);
		System.out.println("\nPrinting NotificationService getNotificationTypes API URL :"+getNotificationTypesService.URL);
		log.info("\nPrinting NotificationService getNotificationTypes API URL :"+getNotificationTypesService.URL);
		
		System.out.println("\nPrinting NotificationService getNotificationTypes API Payload :\n\n"+getNotificationTypesService.Payload);
		log.info("\nPrinting NotificationService getNotificationTypes API Payload :\n\n"+getNotificationTypesService.Payload);
		
		return new RequestGenerator(getNotificationTypesService);
	}
	 
	/**
	 * This method is used to invoke NotificationService getDetailsMasterNotification API
	 * 
	 * @param portalGroup
	 * @param masterNotificationId
	 * @return RequestGenerator
	 */
	public RequestGenerator invokeGetDetailsMasterNotification(String portalGroup, String masterNotificationId)
	{
		MyntraService getDetailsMasterNotificationService = Myntra.getService(ServiceType.PORTAL_NOTIFICATIONS,APINAME.GETDETAILSMASTERNOTIFICATION, init.Configurations,
				PayloadType.JSON, new String[]{ portalGroup, masterNotificationId }, PayloadType.JSON);
		System.out.println("\nPrinting NotificationService getDetailsMasterNotification API URL :"+getDetailsMasterNotificationService.URL);
		log.info("\nPrinting NotificationService getDetailsMasterNotification API URL :"+getDetailsMasterNotificationService.URL);
		
		System.out.println("\nPrinting NotificationService getDetailsMasterNotification API Payload :\n\n"+getDetailsMasterNotificationService.Payload);
		log.info("\nPrinting NotificationService getDetailsMasterNotification API Payload :\n\n"+getDetailsMasterNotificationService.Payload);
		
		return new RequestGenerator(getDetailsMasterNotificationService);
	}
	
	/**
	 * This method is used to invoke NotificationService searchMasterNotification API
	 * 
	 * @param portalGroup
	 * @param notificationType
	 * @return RequestGenerator
	 */
	public RequestGenerator invokeSearchMasterNotification(String portalGroup, String notificationType)
	{
		MyntraService searchMasterNotificationService = Myntra.getService(ServiceType.PORTAL_NOTIFICATIONS, APINAME.SEARCHMASTERNOTIFICATION, init.Configurations,
				PayloadType.JSON, new String[]{ portalGroup, notificationType }, PayloadType.JSON);
		System.out.println("\nPrinting NotificationService searchMasterNotification API URL :"+searchMasterNotificationService.URL);
		log.info("\nPrinting NotificationService searchMasterNotification API URL :"+searchMasterNotificationService.URL);
		
		System.out.println("\nPrinting NotificationService searchMasterNotification API Payload :\n\n"+searchMasterNotificationService.Payload);
		log.info("\nPrinting NotificationService searchMasterNotification API Payload :\n\n"+searchMasterNotificationService.Payload);
		
		return new RequestGenerator(searchMasterNotificationService);
	}
	
	/**
	 * This method is used to invoke NotificationService searchMasterNotificationWithSTET API
	 * 
	 * @param portalGroup
	 * @param notificationType
	 * @return RequestGenerator
	 */
	public RequestGenerator invokeSearchMasterNotificationWithSTET(String portalGroup, String notificationType, String startTime, String endTime)
	{
		MyntraService searchMasterNotificationService = Myntra.getService(ServiceType.PORTAL_NOTIFICATIONS, APINAME.SEARCHMASTERNOTIFICATIONWITHSTET, init.Configurations,
				PayloadType.JSON, new String[]{ portalGroup, notificationType, startTime, endTime }, PayloadType.JSON);
		System.out.println("\nPrinting NotificationService searchMasterNotification with StartTime and EndTime API URL :"+searchMasterNotificationService.URL);
		log.info("\nPrinting NotificationService searchMasterNotification with StartTime and EndTime API URL :"+searchMasterNotificationService.URL);
		
		System.out.println("\nPrinting NotificationService searchMasterNotification with StartTime and EndTime API Payload :\n\n"+searchMasterNotificationService.Payload);
		log.info("\nPrinting NotificationService searchMasterNotification with StartTime and EndTime API Payload :\n\n"+searchMasterNotificationService.Payload);
		
		return new RequestGenerator(searchMasterNotificationService);
	}
	
	/**
	 * This method is used to invoke NotificationService createMasterNotification API
	 * 
	 * @param notificationText
	 * @param notificationTitle
	 * @param imageUrl
	 * @param pageUrl
	 * @param publishTime
	 * @param startTime
	 * @param endTime
	 * @param notificationType
	 * @param forPortalGroup
	 * @return RequestGenerator
	 */
	public RequestGenerator invokeCreateMasterNotification(String notificationText, String notificationTitle, String imageUrl, String pageUrl,
			String publishTime, String startTime, String endTime, String notificationType, String forPortalGroup)
	{
		String createMasterNotificationPayload = prepareCreateMasterNotificationPayloadURLEncoded(notificationText, notificationTitle, imageUrl, pageUrl, publishTime, 
				startTime, endTime, notificationType, forPortalGroup);
		
		MyntraService createMasterNotificationService = Myntra.getService(ServiceType.PORTAL_NOTIFICATIONS,	APINAME.CREATEMASTERNOTIFICATION, init.Configurations,
				new String[] { createMasterNotificationPayload },
				PayloadType.URLENCODED, PayloadType.JSON);
		System.out.println("\nPrinting NotificationService createMasterNotification API URL :"+createMasterNotificationService.URL);
		log.info("\nPrinting NotificationService createMasterNotification API URL :"+createMasterNotificationService.URL);
		
		System.out.println("\nPrinting NotificationService createMasterNotification API Payload :\n\n"+createMasterNotificationService.Payload);
		log.info("\nPrinting NotificationService createMasterNotification API Payload :\n\n"+createMasterNotificationService.Payload);
		
		return new RequestGenerator(createMasterNotificationService);
	}
	
	/**
	 * This method is used to invoke NotificationService markAsReadNotification API
	 * 
	 * @param userId
	 * @param portalGroup
	 * @param publishTime
	 * @param masterNotificationId
	 * @return RequestGenerator
	 */
	public RequestGenerator invokeMarkAsReadNotification(String userId, String portalGroup, String publishTime, String masterNotificationId)
	{
		MyntraService markAsReadNotificationService = Myntra.getService(ServiceType.PORTAL_NOTIFICATIONS, APINAME.MARKASREAD, init.Configurations,
				PayloadType.JSON, new String[] { userId, portalGroup, publishTime, masterNotificationId }, PayloadType.JSON);
		System.out.println("\nPrinting NotificationService markAsReadNotification API URL :"+markAsReadNotificationService.URL);
		log.info("\nPrinting NotificationService markAsReadNotification API URL :"+markAsReadNotificationService.URL);
		
		System.out.println("\nPrinting NotificationService markAsReadNotification API Payload :\n\n"+markAsReadNotificationService.Payload);
		log.info("\nPrinting NotificationService markAsReadNotification API Payload :\n\n"+markAsReadNotificationService.Payload);
		
		return new RequestGenerator(markAsReadNotificationService);
	}
	
	/**
	 * This method is used to invoke NotificationService createNotificationsForUser API
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
	 * @return RequestGenerator
	 */
	public RequestGenerator invokeCreateNotificationsForUser(String userId, String portalGroup, String imageUrl, String pageUrl, String startTime, String endTime, 
			String notificationText, String notificationTitle, String notificationId, String notificationType)
	{
		String createNotificationForUserPayload = prepareCreateNotificationForUserPayloadURLEncoded(imageUrl, pageUrl, startTime, endTime, notificationText, notificationTitle,
				notificationId, notificationType);
		
		MyntraService createNotificationForUserService = Myntra.getService(ServiceType.PORTAL_NOTIFICATIONS, APINAME.CREATENOTIFICATIONFORUSER, init.Configurations,
				new String[] { createNotificationForUserPayload }, new String[] { userId, portalGroup }, PayloadType.URLENCODED, PayloadType.JSON);
		System.out.println("\nPrinting NotificationService createNotificationForUser API URL :"+createNotificationForUserService.URL);
		log.info("\nPrinting NotificationService createNotificationForUser API URL :"+createNotificationForUserService.URL);
		
		System.out.println("\nPrinting NotificationService createNotificationForUser API Payload :\n\n"+createNotificationForUserService.Payload);
		log.info("\nPrinting NotificationService createNotificationForUser API Payload :\n\n"+createNotificationForUserService.Payload);
		
		return new RequestGenerator(createNotificationForUserService);
	}
	
	private String prepareCreateMasterNotificationPayloadURLEncoded(String notificationText, String notificationTitle, String imageUrl, String pageUrl, String publishTime,
			String startTime, String endTime, String notificationType, String forPortalGroup)
	{
		StringBuffer encodedURL = new StringBuffer();
		encodedURL.append("notification-title=");
		encodedURL.append(notificationTitle.trim());
		encodedURL.append("&");

		encodedURL.append("publish-time=");
		encodedURL.append(publishTime.trim());
		encodedURL.append("&");

		encodedURL.append("start-time=");
		encodedURL.append(startTime.trim());
		encodedURL.append("&");
		
		encodedURL.append("end-time=");
		encodedURL.append(endTime.trim());
		encodedURL.append("&");

		encodedURL.append("notification-type=");
		encodedURL.append(notificationType.trim());
		encodedURL.append("&");

		encodedURL.append("page-url=");
		encodedURL.append(pageUrl.trim());
		encodedURL.append("&");

		encodedURL.append("image-url=");
		encodedURL.append(imageUrl.trim());
		encodedURL.append("&");

		encodedURL.append("notification-text=");
		encodedURL.append(notificationText.trim());
		encodedURL.append("&");

		encodedURL.append("forPortalGroup=");
		encodedURL.append(forPortalGroup.trim());

		return encodedURL.toString();
	}
	
	private String prepareCreateNotificationForUserPayloadURLEncoded(String imageUrl, String pageUrl,
			 String startTime, String endTime, String notificationText, String notificationTitle, String notificationId, String notificationType)
	{
		StringBuffer encodedURL = new StringBuffer();
		encodedURL.append("image-url=");
		encodedURL.append(imageUrl.trim());
		encodedURL.append("&");
		
		encodedURL.append("page-url=");
		encodedURL.append(pageUrl.trim());
		encodedURL.append("&");

		encodedURL.append("start-time=");
		encodedURL.append(startTime.trim());
		encodedURL.append("&");

		encodedURL.append("end-time=");
		encodedURL.append(endTime.trim());
		encodedURL.append("&");

		encodedURL.append("notification-text=");
		encodedURL.append(notificationText.trim());
		encodedURL.append("&");

		encodedURL.append("notification-title=");
		encodedURL.append(notificationTitle.trim());
		encodedURL.append("&");

		encodedURL.append("notification-id=");
		encodedURL.append(notificationId.trim());
		encodedURL.append("&");
		
		encodedURL.append("notification-type=");
		encodedURL.append(notificationType.trim());
		
		return encodedURL.toString();
	}
	
	public List<String> getNotificationTypes()
	{
		List<String> notificationTypesList = new ArrayList<String>();
		notificationTypesList.add(NotificationServiceConstants.NOTIFICATION_TYPE_MARKETING);
		notificationTypesList.add(NotificationServiceConstants.NOTIFICATION_TYPE_REVENUE_LABS);
		notificationTypesList.add(NotificationServiceConstants.NOTIFICATION_TYPE_ORDER_STATUS_CHANGE);
		notificationTypesList.add(NotificationServiceConstants.NOTIFICATION_TYPE_PRICE_CHANGE);
		
		return notificationTypesList;
	}
	
	public List<String> getPortalGroups()
	{
		List<String> portalGroupsList = new ArrayList<String>();
		portalGroupsList.add(NotificationServiceConstants.PORTAL_GROUP_MYNTRA);
		portalGroupsList.add(NotificationServiceConstants.PORTAL_GROUP_MY_URBAN_LOOK);
		portalGroupsList.add(NotificationServiceConstants.PORTAL_GROUP_GAP);
		portalGroupsList.add(NotificationServiceConstants.PORTAL_GROUP_PUMA);
		portalGroupsList.add(NotificationServiceConstants.PORTAL_GROUP_LEVIS);
		
		return portalGroupsList;
	}

}
