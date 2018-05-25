package com.myntra.apiTests.portalservices.notificationservice;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shankara.c
 *
 */
public enum NotificationServiceNodes 
{
	NUMOFACTIVENOTIFICATIONS_NODE("numberOfActiveNotifications"),
	SUCCESS_NODE("success"),
	ACTION_NODE("action"),
	ACTIONS_NODE("actions"),
	ACTIONS_GETACTNOTIFICATIONS_NODE("actions.getActiveNotifications"),
	ACTIONS_GETDETAILS("actions.getDetails"),
	ACTIONS_MARKASREAD("actions.markAsRead"),
	
	MASTER_NOTIFICATION_SEARCH_RESULTS("masterNotificationSearchResults"),
	MASTER_NOTIFICATION("masterNotification"),
	MASTER_NOTIFICATION_ID("masterNotification.id"),
	MASTER_NOTIFICATION_CDN_URL_FOR_IMG("masterNotification.cdnURLForImage"),
	MASTER_NOTIFICATION_START_TIME("masterNotification.startTime"),
	MASTER_NOTIFICATION_END_TIME("masterNotification.endTime"),
	MASTER_NOTIFICATION_PUBLISH_TIME("masterNotification.publishTime"),
	MASTER_NOTIFICATION_TEXT("masterNotification.notificationText"),
	MASTER_NOTIFICATION_TITLE("masterNotification.notificationTitle"),
	MASTER_NOTIFICATION_TYPE("masterNotification.notificationType"),
	MASTER_NOTIFICATION_URL_FOR_LAND_PAGE("masterNotification.urlForLandingPage"),
	
	SEARCH_MASTER_NOTIFICATION_ID("masterNotificationId"),
	
	// for getActiveNotifications API
	NOTIFICATIONS("notifications"),
	NOTIFICATION("notification"),
	NOTIFICATION_ID("notification.id"),
	NOTIFICATION_MASTER_NOTIFICATION_ID("notification.masterNotificationId"),
	NOTIFICATION_USER_ID("notification.userId"),
	NOTIFICATION_CDN_URL_FOR_IMG("notification.cdnURLForImage"),
	NOTIFICATION_START_TIME("notification.startTime"),
	NOTIFICATION_END_TIME("notification.endTime"),
	NOTIFICATION_PUB_TIME("notification.publishTime"),
	NOTIFICATION_TEXT("notification.notificationText"),
	NOTIFICATION_TITLE("notification.notificationTitle"),
	NOTIFICATION_STATUS("notification.notificationStatus"),
	NOTIFICATION_TYPE("notification.notificationType"),
	NOTIFICATION_URL_LAND_PAGE("notification.urlForLandingPage");
	
	private String nodePath;
	
	private NotificationServiceNodes(String nodePath)
	{
		this.nodePath = nodePath;
	}
	
	public String getNodePath() {
		return nodePath;
	}
	
	public static List <String> getActiveNotificationCountNodes()
	{
		List <String> notificationNodes = new ArrayList<String>();
		notificationNodes.add(NUMOFACTIVENOTIFICATIONS_NODE.getNodePath());
		notificationNodes.add(SUCCESS_NODE.getNodePath());
		notificationNodes.add(ACTIONS_NODE.getNodePath());
		notificationNodes.add(ACTIONS_GETACTNOTIFICATIONS_NODE.getNodePath());
		return notificationNodes;
	}
	
	public static List <String> getMasterNotificationNodes()
	{
		List <String> masterNotificationNodes = new ArrayList<String>();
		masterNotificationNodes.add(MASTER_NOTIFICATION.getNodePath());
		masterNotificationNodes.add(MASTER_NOTIFICATION_ID.getNodePath());
		masterNotificationNodes.add(MASTER_NOTIFICATION_CDN_URL_FOR_IMG.getNodePath());
		masterNotificationNodes.add(MASTER_NOTIFICATION_START_TIME.getNodePath());
		masterNotificationNodes.add(MASTER_NOTIFICATION_END_TIME.getNodePath());
		masterNotificationNodes.add(MASTER_NOTIFICATION_PUBLISH_TIME.getNodePath());
		masterNotificationNodes.add(MASTER_NOTIFICATION_TEXT.getNodePath());
		masterNotificationNodes.add(MASTER_NOTIFICATION_TITLE.getNodePath());
		masterNotificationNodes.add(MASTER_NOTIFICATION_TYPE.getNodePath());
		masterNotificationNodes.add(MASTER_NOTIFICATION_URL_FOR_LAND_PAGE.getNodePath());
		masterNotificationNodes.add(SUCCESS_NODE.getNodePath());
		masterNotificationNodes.add(ACTIONS_NODE.getNodePath());	
		
		return masterNotificationNodes;
	}
	
	public static List<String> getSearchMasterNotificationNodes()
	{
		List <String> searchMasterNotificationNodes = new ArrayList<String>();
		searchMasterNotificationNodes.add(SEARCH_MASTER_NOTIFICATION_ID.getNodePath());
		searchMasterNotificationNodes.add(SUCCESS_NODE.getNodePath());
		searchMasterNotificationNodes.add(ACTIONS_NODE.getNodePath());
		searchMasterNotificationNodes.add(ACTIONS_GETDETAILS.getNodePath());
		
		return searchMasterNotificationNodes;
	}
	
	public static List<String> getActiveNotificationNodes()
	{
		List <String> activeNotificationNodes = new ArrayList<String>();
		activeNotificationNodes.add(NOTIFICATION.getNodePath());
		activeNotificationNodes.add(NOTIFICATION_ID.getNodePath());
		activeNotificationNodes.add(NOTIFICATION_MASTER_NOTIFICATION_ID.getNodePath());
		activeNotificationNodes.add(NOTIFICATION_USER_ID.getNodePath());
		activeNotificationNodes.add(NOTIFICATION_CDN_URL_FOR_IMG.getNodePath());
		activeNotificationNodes.add(NOTIFICATION_START_TIME.getNodePath());
		activeNotificationNodes.add(NOTIFICATION_END_TIME.getNodePath());
		activeNotificationNodes.add(NOTIFICATION_PUB_TIME.getNodePath());
		activeNotificationNodes.add(NOTIFICATION_TEXT.getNodePath());
		activeNotificationNodes.add(NOTIFICATION_TITLE.getNodePath());
		activeNotificationNodes.add(NOTIFICATION_STATUS.getNodePath());
		activeNotificationNodes.add(NOTIFICATION_TYPE.getNodePath());
		activeNotificationNodes.add(NOTIFICATION_URL_LAND_PAGE.getNodePath());
		activeNotificationNodes.add(ACTIONS_NODE.getNodePath());
		activeNotificationNodes.add(ACTIONS_MARKASREAD.getNodePath());
		
		return activeNotificationNodes;
	}
	
	public static List<String> getCreateNotificationForSingleUserNodes()
	{
		List <String> searchMasterNotificationNodes = new ArrayList<String>();
		searchMasterNotificationNodes.add(SUCCESS_NODE.getNodePath());
		searchMasterNotificationNodes.add(ACTIONS_NODE.getNodePath());
		searchMasterNotificationNodes.add(ACTIONS_GETACTNOTIFICATIONS_NODE.getNodePath());
		
		return searchMasterNotificationNodes;
	}
	
	public static List<String> getCreateNotificationsForSetOfUsersNodes()
	{
		List <String> searchMasterNotificationNodes = new ArrayList<String>();
		searchMasterNotificationNodes.add(SUCCESS_NODE.getNodePath());
		searchMasterNotificationNodes.add(ACTIONS_NODE.getNodePath());
		
		return searchMasterNotificationNodes;
	}
	
	public static List<String> getMarkAsReadNotificationNodes()
	{
		List <String> markAsReadNotificationNodes = new ArrayList<String>();
		markAsReadNotificationNodes.add(SUCCESS_NODE.getNodePath());
		markAsReadNotificationNodes.add(ACTIONS_NODE.getNodePath());
		markAsReadNotificationNodes.add(ACTIONS_GETACTNOTIFICATIONS_NODE.getNodePath());
		
		return markAsReadNotificationNodes;
	} 
	
	public static List<String> getCreateNotificationForUserNodes()
	{
		List <String> searchMasterNotificationNodes = new ArrayList<String>();
		searchMasterNotificationNodes.add(SUCCESS_NODE.getNodePath());
		searchMasterNotificationNodes.add(ACTIONS_NODE.getNodePath());
		searchMasterNotificationNodes.add(ACTIONS_GETACTNOTIFICATIONS_NODE.getNodePath());
		
		return searchMasterNotificationNodes;
	}
}
