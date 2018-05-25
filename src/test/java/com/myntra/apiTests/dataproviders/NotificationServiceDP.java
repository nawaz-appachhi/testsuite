package com.myntra.apiTests.dataproviders;

import java.util.Calendar;

import com.myntra.apiTests.portalservices.commons.CommonUtils;
import com.myntra.apiTests.portalservices.commons.IServiceConstants;
import com.myntra.apiTests.portalservices.notificationservice.NotificationServiceConstants;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import com.eaio.uuid.UUID;
import com.myntra.lordoftherings.Toolbox;

/**
 * @author shankara.c
 *
 */
public class NotificationServiceDP extends CommonUtils implements NotificationServiceConstants
{
	String environment;
	
	public NotificationServiceDP()
	{
		if(System.getenv(IServiceConstants.ENVIRONMENT) == null)
			environment = IServiceConstants.CONFIGURATION.GetTestEnvironemnt().toString();
		else
			environment = System.getenv(IServiceConstants.ENVIRONMENT);
	}
	
	@DataProvider
	public Object[][] NotificationServiceDP_createMasterNotification_verifySuccessResponse(ITestContext testContext)
	{
		// notification valid in hours
		String publishTime = String.valueOf(Calendar.getInstance().getTimeInMillis());
		
		Calendar startTimeCal = Calendar.getInstance();
		startTimeCal.add(Calendar.HOUR, 2);
		String startTime = String.valueOf(startTimeCal.getTimeInMillis()); 
		
		Calendar endTimeCal = Calendar.getInstance();
		endTimeCal.add(Calendar.HOUR, 8);
		String endTime = String.valueOf(endTimeCal.getTimeInMillis());
		
		/*String[] arr1 = { ENVIRONMENT_PROD, PUMA_PROD_NOTIFICATION_TEXT, PUMA_PROD_NOTIFICATION_TITLE, PUMA_PROD_NOTIFICATION_IMG_URL, PUMA_PROD_NOTIFICATION_PAGE_URL,
				publishTime, startTime, endTime, NOTIFICATION_TYPE_MARKETING, PORTAL_GROUP_MYNTRA };
		String[] arr2 = { ENVIRONMENT_PROD, VEROMODA_PROD_NOTIFICATION_TEXT, VEROMODA_PROD_NOTIFICATION_TITLE, VEROMODA_PROD_NOTIFICATION_IMG_URL, VEROMODA_PROD_NOTIFICATION_PAGE_URL,
				publishTime, startTime, endTime, NOTIFICATION_TYPE_REVENUE_LABS, PORTAL_GROUP_MYNTRA };
		String[] arr3 = { ENVIRONMENT_PROD, NIKE_PROD_NOTIFICATION_TEXT, NIKE_PROD_NOTIFICATION_TITLE, NIKE_PROD_NOTIFICATION_IMG_URL, NIKE_PROD_NOTIFICATION_PAGE_URL,
				publishTime, startTime, endTime, NOTIFICATION_TYPE_ORDER_STATUS_CHANGE, PORTAL_GROUP_MYNTRA };
		String[] arr4 = { ENVIRONMENT_PROD, ADIDAS_PROD_NOTIFICATION_TEXT, ADIDAS_PROD_NOTIFICATION_TITLE, ADIDAS_PROD_NOTIFICATION_IMG_URL, ADIDAS_PROD_NOTIFICATION_PAGE_URL, 
				publishTime, startTime, endTime, NOTIFICATION_TYPE_PRICE_CHANGE, PORTAL_GROUP_MYNTRA };*/
		
		String[] arr5 = { IServiceConstants.ENVIRONMENT_FOX7, PUMA_PROD_NOTIFICATION_TEXT, PUMA_PROD_NOTIFICATION_TITLE, PUMA_PROD_NOTIFICATION_IMG_URL, PUMA_PROD_NOTIFICATION_PAGE_URL,
				publishTime, startTime, endTime, NOTIFICATION_TYPE_MARKETING, PORTAL_GROUP_MYNTRA };
		String[] arr6 = { IServiceConstants.ENVIRONMENT_FOX7, VEROMODA_PROD_NOTIFICATION_TEXT, VEROMODA_PROD_NOTIFICATION_TITLE, VEROMODA_PROD_NOTIFICATION_IMG_URL, VEROMODA_PROD_NOTIFICATION_PAGE_URL,
				publishTime, startTime, endTime, NOTIFICATION_TYPE_REVENUE_LABS, PORTAL_GROUP_MYNTRA };
		String[] arr7 = { IServiceConstants.ENVIRONMENT_FOX7, NIKE_PROD_NOTIFICATION_TEXT, NIKE_PROD_NOTIFICATION_TITLE, NIKE_PROD_NOTIFICATION_IMG_URL, NIKE_PROD_NOTIFICATION_PAGE_URL,
				publishTime, startTime, endTime, NOTIFICATION_TYPE_ORDER_STATUS_CHANGE, PORTAL_GROUP_MYNTRA };
		String[] arr8 = { IServiceConstants.ENVIRONMENT_FOX7, ADIDAS_PROD_NOTIFICATION_TEXT, ADIDAS_PROD_NOTIFICATION_TITLE, ADIDAS_PROD_NOTIFICATION_IMG_URL, ADIDAS_PROD_NOTIFICATION_PAGE_URL,
				publishTime, startTime, endTime, NOTIFICATION_TYPE_PRICE_CHANGE, PORTAL_GROUP_MYNTRA };
		
		String[] arr9 = { IServiceConstants.ENVIRONMENT_FUNC, PUMA_PROD_NOTIFICATION_TEXT, PUMA_PROD_NOTIFICATION_TITLE, PUMA_PROD_NOTIFICATION_IMG_URL, PUMA_PROD_NOTIFICATION_PAGE_URL,
				publishTime, startTime, endTime, NOTIFICATION_TYPE_MARKETING, PORTAL_GROUP_MYNTRA };
		String[] arr10 = { IServiceConstants.ENVIRONMENT_FUNC, VEROMODA_PROD_NOTIFICATION_TEXT, VEROMODA_PROD_NOTIFICATION_TITLE, VEROMODA_PROD_NOTIFICATION_IMG_URL, VEROMODA_PROD_NOTIFICATION_PAGE_URL,
				publishTime, startTime, endTime, NOTIFICATION_TYPE_REVENUE_LABS, PORTAL_GROUP_MYNTRA };
		String[] arr11 = { IServiceConstants.ENVIRONMENT_FUNC, NIKE_PROD_NOTIFICATION_TEXT, NIKE_PROD_NOTIFICATION_TITLE, NIKE_PROD_NOTIFICATION_IMG_URL, NIKE_PROD_NOTIFICATION_PAGE_URL,
				publishTime, startTime, endTime, NOTIFICATION_TYPE_ORDER_STATUS_CHANGE, PORTAL_GROUP_MYNTRA };
		String[] arr12 = { IServiceConstants.ENVIRONMENT_FUNC, ADIDAS_PROD_NOTIFICATION_TEXT, ADIDAS_PROD_NOTIFICATION_TITLE, ADIDAS_PROD_NOTIFICATION_IMG_URL, ADIDAS_PROD_NOTIFICATION_PAGE_URL,
				publishTime, startTime, endTime, NOTIFICATION_TYPE_PRICE_CHANGE, PORTAL_GROUP_MYNTRA };
		
		String[] arr13 = { IServiceConstants.ENVIRONMENT_PERF, PUMA_PROD_NOTIFICATION_TEXT, PUMA_PROD_NOTIFICATION_TITLE, PUMA_PROD_NOTIFICATION_IMG_URL, PUMA_PROD_NOTIFICATION_PAGE_URL,
				publishTime, startTime, endTime, NOTIFICATION_TYPE_MARKETING, PORTAL_GROUP_MYNTRA };
		String[] arr14 = { IServiceConstants.ENVIRONMENT_PERF, VEROMODA_PROD_NOTIFICATION_TEXT, VEROMODA_PROD_NOTIFICATION_TITLE, VEROMODA_PROD_NOTIFICATION_IMG_URL, VEROMODA_PROD_NOTIFICATION_PAGE_URL,
				publishTime, startTime, endTime, NOTIFICATION_TYPE_REVENUE_LABS, PORTAL_GROUP_MYNTRA };
		String[] arr15 = { IServiceConstants.ENVIRONMENT_PERF, NIKE_PROD_NOTIFICATION_TEXT, NIKE_PROD_NOTIFICATION_TITLE, NIKE_PROD_NOTIFICATION_IMG_URL, NIKE_PROD_NOTIFICATION_PAGE_URL,
				publishTime, startTime, endTime, NOTIFICATION_TYPE_ORDER_STATUS_CHANGE, PORTAL_GROUP_MYNTRA };
		String[] arr16 = { IServiceConstants.ENVIRONMENT_PERF, ADIDAS_PROD_NOTIFICATION_TEXT, ADIDAS_PROD_NOTIFICATION_TITLE, ADIDAS_PROD_NOTIFICATION_IMG_URL, ADIDAS_PROD_NOTIFICATION_PAGE_URL,
				publishTime, startTime, endTime, NOTIFICATION_TYPE_PRICE_CHANGE, PORTAL_GROUP_MYNTRA };
				
		Object[][] dataSet = new Object[][] { arr5, arr6, arr7, arr8, arr9, arr10, arr11, arr12, arr13, arr14, arr15, arr16 };
		return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public Object[][] NotificationServiceDP_createMasterNotification_verifyNodeValues(ITestContext testContext)
	{
		// hour validity dp for all the notificationTypes
		String publishTime = String.valueOf(Calendar.getInstance().getTimeInMillis());
		
		Calendar startTimeCal = Calendar.getInstance();
		startTimeCal.add(Calendar.HOUR, 1);
		String startTime = String.valueOf(startTimeCal.getTimeInMillis()); 
		
		Calendar endTimeCal = Calendar.getInstance();
		endTimeCal.add(Calendar.DAY_OF_MONTH, 4);
		String endTime = String.valueOf(endTimeCal.getTimeInMillis());
		
		/*String[] arr1 = { ENVIRONMENT_PROD, TITAN_PROD_NOTIFICATION_TEXT, TITAN_PROD_NOTIFICATION_TITLE, TITAN_PROD_NOTIFICATION_IMG_URL, TITAN_PROD_NOTIFICATION_PAGE_URL,
				publishTime, startTime, endTime, NOTIFICATION_TYPE_MARKETING, PORTAL_GROUP_MYNTRA };
		String[] arr2 = { ENVIRONMENT_PROD, FOSSIL_PROD_NOTIFICATION_TEXT, FOSSIL_PROD_NOTIFICATION_TITLE, FOSSIL_PROD_NOTIFICATION_IMG_URL, FOSSIL_PROD_NOTIFICATION_PAGE_URL,
				publishTime, startTime, endTime, NOTIFICATION_TYPE_REVENUE_LABS, PORTAL_GROUP_MYNTRA };
		String[] arr3 = { ENVIRONMENT_PROD, TITAN_PROD_NOTIFICATION_TEXT, TITAN_PROD_NOTIFICATION_TITLE, TITAN_PROD_NOTIFICATION_IMG_URL, TITAN_PROD_NOTIFICATION_PAGE_URL,
				publishTime, startTime, endTime, NOTIFICATION_TYPE_ORDER_STATUS_CHANGE, PORTAL_GROUP_MYNTRA };
		String[] arr4 = { ENVIRONMENT_PROD, FOSSIL_PROD_NOTIFICATION_TEXT, FOSSIL_PROD_NOTIFICATION_TITLE, FOSSIL_PROD_NOTIFICATION_IMG_URL, FOSSIL_PROD_NOTIFICATION_PAGE_URL, 
				publishTime, startTime, endTime, NOTIFICATION_TYPE_PRICE_CHANGE, PORTAL_GROUP_MYNTRA };*/
		
		String[] arr5 = { IServiceConstants.ENVIRONMENT_FOX7, TITAN_PROD_NOTIFICATION_TEXT, TITAN_PROD_NOTIFICATION_TITLE, TITAN_PROD_NOTIFICATION_IMG_URL, TITAN_PROD_NOTIFICATION_PAGE_URL,
				publishTime, startTime, endTime, NOTIFICATION_TYPE_MARKETING, PORTAL_GROUP_MYNTRA };
		String[] arr6 = { IServiceConstants.ENVIRONMENT_FOX7, FOSSIL_PROD_NOTIFICATION_TEXT, FOSSIL_PROD_NOTIFICATION_TITLE, FOSSIL_PROD_NOTIFICATION_IMG_URL, FOSSIL_PROD_NOTIFICATION_PAGE_URL,
				publishTime, startTime, endTime, NOTIFICATION_TYPE_REVENUE_LABS, PORTAL_GROUP_MYNTRA };
		String[] arr7 = { IServiceConstants.ENVIRONMENT_FOX7, TITAN_PROD_NOTIFICATION_TEXT, TITAN_PROD_NOTIFICATION_TITLE, TITAN_PROD_NOTIFICATION_IMG_URL, TITAN_PROD_NOTIFICATION_PAGE_URL,
				publishTime, startTime, endTime, NOTIFICATION_TYPE_ORDER_STATUS_CHANGE, PORTAL_GROUP_MYNTRA };
		String[] arr8 = { IServiceConstants.ENVIRONMENT_FOX7, FOSSIL_PROD_NOTIFICATION_TEXT, FOSSIL_PROD_NOTIFICATION_TITLE, FOSSIL_PROD_NOTIFICATION_IMG_URL, FOSSIL_PROD_NOTIFICATION_PAGE_URL,
				publishTime, startTime, endTime, NOTIFICATION_TYPE_PRICE_CHANGE, PORTAL_GROUP_MYNTRA };
		
		String[] arr9 = { IServiceConstants.ENVIRONMENT_FUNC, TITAN_PROD_NOTIFICATION_TEXT, TITAN_PROD_NOTIFICATION_TITLE, TITAN_PROD_NOTIFICATION_IMG_URL, TITAN_PROD_NOTIFICATION_PAGE_URL,
				publishTime, startTime, endTime, NOTIFICATION_TYPE_MARKETING, PORTAL_GROUP_MYNTRA };
		String[] arr10 = { IServiceConstants.ENVIRONMENT_FUNC, FOSSIL_PROD_NOTIFICATION_TEXT, FOSSIL_PROD_NOTIFICATION_TITLE, FOSSIL_PROD_NOTIFICATION_IMG_URL, FOSSIL_PROD_NOTIFICATION_PAGE_URL,
				publishTime, startTime, endTime, NOTIFICATION_TYPE_REVENUE_LABS, PORTAL_GROUP_MYNTRA };
		String[] arr11 = { IServiceConstants.ENVIRONMENT_FUNC, TITAN_PROD_NOTIFICATION_TEXT, TITAN_PROD_NOTIFICATION_TITLE, TITAN_PROD_NOTIFICATION_IMG_URL, TITAN_PROD_NOTIFICATION_PAGE_URL,
				publishTime, startTime, endTime, NOTIFICATION_TYPE_ORDER_STATUS_CHANGE, PORTAL_GROUP_MYNTRA };
		String[] arr12 = { IServiceConstants.ENVIRONMENT_FUNC, FOSSIL_PROD_NOTIFICATION_TEXT, FOSSIL_PROD_NOTIFICATION_TITLE, FOSSIL_PROD_NOTIFICATION_IMG_URL, FOSSIL_PROD_NOTIFICATION_PAGE_URL,
				publishTime, startTime, endTime, NOTIFICATION_TYPE_PRICE_CHANGE, PORTAL_GROUP_MYNTRA };
		
		String[] arr13 = { IServiceConstants.ENVIRONMENT_PERF, TITAN_PROD_NOTIFICATION_TEXT, TITAN_PROD_NOTIFICATION_TITLE, TITAN_PROD_NOTIFICATION_IMG_URL, TITAN_PROD_NOTIFICATION_PAGE_URL,
				publishTime, startTime, endTime, NOTIFICATION_TYPE_MARKETING, PORTAL_GROUP_MYNTRA };
		String[] arr14 = { IServiceConstants.ENVIRONMENT_PERF, FOSSIL_PROD_NOTIFICATION_TEXT, FOSSIL_PROD_NOTIFICATION_TITLE, FOSSIL_PROD_NOTIFICATION_IMG_URL, FOSSIL_PROD_NOTIFICATION_PAGE_URL,
				publishTime, startTime, endTime, NOTIFICATION_TYPE_REVENUE_LABS, PORTAL_GROUP_MYNTRA };
		String[] arr15 = { IServiceConstants.ENVIRONMENT_PERF, TITAN_PROD_NOTIFICATION_TEXT, TITAN_PROD_NOTIFICATION_TITLE, TITAN_PROD_NOTIFICATION_IMG_URL, TITAN_PROD_NOTIFICATION_PAGE_URL,
				publishTime, startTime, endTime, NOTIFICATION_TYPE_ORDER_STATUS_CHANGE, PORTAL_GROUP_MYNTRA };
		String[] arr16 = { IServiceConstants.ENVIRONMENT_PERF, FOSSIL_PROD_NOTIFICATION_TEXT, FOSSIL_PROD_NOTIFICATION_TITLE, FOSSIL_PROD_NOTIFICATION_IMG_URL, FOSSIL_PROD_NOTIFICATION_PAGE_URL,
				publishTime, startTime, endTime, NOTIFICATION_TYPE_PRICE_CHANGE, PORTAL_GROUP_MYNTRA };
		
		Object[][] dataSet = new Object[][] { arr5, arr6, arr7, arr8, arr9, arr10, arr11, arr12, arr13, arr14, arr15, arr16  };
		return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] NotificationServiceDP_createMasterNotification_verifyFailureResponse(ITestContext testContext)
	{
		String publishTime = String.valueOf(Calendar.getInstance().getTimeInMillis());
		
		Calendar startTimeCal = Calendar.getInstance();
		startTimeCal.add(Calendar.HOUR, 2);
		String startTime = String.valueOf(startTimeCal.getTimeInMillis());
		
		Calendar endTimeCal = Calendar.getInstance();
		endTimeCal.add(Calendar.HOUR, 4);
		String endTime = String.valueOf(endTimeCal.getTimeInMillis());
		
		// prod env dp
		/*// notificationText as blank
		String[] arr1 = { ENVIRONMENT_PROD, "", NIKE_PROD_NOTIFICATION_TITLE, NIKE_PROD_NOTIFICATION_IMG_URL, NIKE_PROD_NOTIFICATION_PAGE_URL, 
				publishTime, startTime, endTime, NOTIFICATION_TYPE_MARKETING, PORTAL_GROUP_MYNTRA };
		
		// publish time, start time and endtime are same
		String[] arr2 = { ENVIRONMENT_PROD, NIKE_PROD_NOTIFICATION_TEXT, NIKE_PROD_NOTIFICATION_TITLE, NIKE_PROD_NOTIFICATION_IMG_URL, NIKE_PROD_NOTIFICATION_PAGE_URL,
				publishTime, publishTime, publishTime, NOTIFICATION_TYPE_MARKETING, PORTAL_GROUP_MYNTRA };
		
		// notificationTitle as blank
		String[] arr3 = { ENVIRONMENT_PROD, NIKE_PROD_NOTIFICATION_TEXT, "", NIKE_PROD_NOTIFICATION_IMG_URL, NIKE_PROD_NOTIFICATION_PAGE_URL, publishTime, startTime,
				endTime, NOTIFICATION_TYPE_MARKETING, PORTAL_GROUP_MYNTRA };
		
		// imageUrl as blank
		String[] arr4 = { ENVIRONMENT_PROD, NIKE_PROD_NOTIFICATION_TEXT, NIKE_PROD_NOTIFICATION_TITLE, "", NIKE_PROD_NOTIFICATION_PAGE_URL, publishTime, startTime, 
				endTime, NOTIFICATION_TYPE_MARKETING, PORTAL_GROUP_MYNTRA };
		
		// pageUrl as blank
		String[] arr5 = { ENVIRONMENT_PROD, NIKE_PROD_NOTIFICATION_TEXT, NIKE_PROD_NOTIFICATION_TITLE, NIKE_PROD_NOTIFICATION_IMG_URL, "", publishTime, startTime,
				endTime, NOTIFICATION_TYPE_MARKETING, PORTAL_GROUP_MYNTRA };
		
		// publishTime as blank
		String[] arr6 = { ENVIRONMENT_PROD, NIKE_PROD_NOTIFICATION_TEXT, NIKE_PROD_NOTIFICATION_TITLE, NIKE_PROD_NOTIFICATION_IMG_URL, NIKE_PROD_NOTIFICATION_PAGE_URL,
				"", startTime, endTime, NOTIFICATION_TYPE_MARKETING, PORTAL_GROUP_MYNTRA };
		
		// endTime as blank
		String[] arr7 = { ENVIRONMENT_PROD, NIKE_PROD_NOTIFICATION_TEXT, NIKE_PROD_NOTIFICATION_TITLE, NIKE_PROD_NOTIFICATION_IMG_URL, NIKE_PROD_NOTIFICATION_PAGE_URL,
				publishTime, startTime, "", NOTIFICATION_TYPE_MARKETING, PORTAL_GROUP_MYNTRA };
		
		// notificationType as blank
		String[] arr8 = { ENVIRONMENT_PROD, NIKE_PROD_NOTIFICATION_TEXT, NIKE_PROD_NOTIFICATION_TITLE, NIKE_PROD_NOTIFICATION_IMG_URL, NIKE_PROD_NOTIFICATION_PAGE_URL,
				publishTime, startTime, endTime, "", PORTAL_GROUP_MYNTRA };
		
		// forPortalGroup as blank
		String[] arr9 = { ENVIRONMENT_PROD, NIKE_PROD_NOTIFICATION_TEXT, NIKE_PROD_NOTIFICATION_TITLE, NIKE_PROD_NOTIFICATION_IMG_URL, NIKE_PROD_NOTIFICATION_PAGE_URL,
				publishTime, startTime, endTime, NOTIFICATION_TYPE_MARKETING, "" };
		
		// forPortalGroup as my-urban-look
		String[] arr10 = { ENVIRONMENT_PROD, NIKE_PROD_NOTIFICATION_TEXT, NIKE_PROD_NOTIFICATION_TITLE, NIKE_PROD_NOTIFICATION_IMG_URL, NIKE_PROD_NOTIFICATION_PAGE_URL,
				publishTime, startTime, endTime, NOTIFICATION_TYPE_MARKETING, PORTAL_GROUP_MY_URBAN_LOOK };
		
		// forPortalGroup as gap
		String[] arr11 = { ENVIRONMENT_PROD, NIKE_PROD_NOTIFICATION_TEXT, NIKE_PROD_NOTIFICATION_TITLE, NIKE_PROD_NOTIFICATION_IMG_URL, NIKE_PROD_NOTIFICATION_PAGE_URL,
				publishTime, startTime, endTime, NOTIFICATION_TYPE_MARKETING, PORTAL_GROUP_GAP };
		
		// forPortalGroup as puma
		String[] arr12 = { ENVIRONMENT_PROD, NIKE_PROD_NOTIFICATION_TEXT, NIKE_PROD_NOTIFICATION_TITLE, NIKE_PROD_NOTIFICATION_IMG_URL, NIKE_PROD_NOTIFICATION_PAGE_URL,
				publishTime, startTime, endTime, NOTIFICATION_TYPE_MARKETING, PORTAL_GROUP_PUMA };
		
		// forPortalGroup as levis
		String[] arr13 = { ENVIRONMENT_PROD, NIKE_PROD_NOTIFICATION_TEXT, NIKE_PROD_NOTIFICATION_TITLE, NIKE_PROD_NOTIFICATION_IMG_URL, NIKE_PROD_NOTIFICATION_PAGE_URL,
				publishTime, startTime, endTime, NOTIFICATION_TYPE_MARKETING, PORTAL_GROUP_LEVIS };*/
		
		// fox7 env dp
		// notificationText as blank
		String[] arr14 = { IServiceConstants.ENVIRONMENT_FOX7, "", NIKE_PROD_NOTIFICATION_TITLE, NIKE_PROD_NOTIFICATION_IMG_URL, NIKE_PROD_NOTIFICATION_PAGE_URL,
				publishTime, startTime, endTime, NOTIFICATION_TYPE_MARKETING, PORTAL_GROUP_MYNTRA };
		
		// publish time, start time and endtime are same
		String[] arr15 = { IServiceConstants.ENVIRONMENT_FOX7, NIKE_PROD_NOTIFICATION_TEXT, NIKE_PROD_NOTIFICATION_TITLE, NIKE_PROD_NOTIFICATION_IMG_URL, NIKE_PROD_NOTIFICATION_PAGE_URL,
				publishTime, publishTime, publishTime, NOTIFICATION_TYPE_MARKETING, PORTAL_GROUP_MYNTRA };
		
		// notificationTitle as blank
		String[] arr16 = { IServiceConstants.ENVIRONMENT_FOX7, NIKE_PROD_NOTIFICATION_TEXT, "", NIKE_PROD_NOTIFICATION_IMG_URL, NIKE_PROD_NOTIFICATION_PAGE_URL, publishTime, startTime,
				endTime, NOTIFICATION_TYPE_MARKETING, PORTAL_GROUP_MYNTRA };
		
		// imageUrl as blank
		String[] arr17 = { IServiceConstants.ENVIRONMENT_FOX7, NIKE_PROD_NOTIFICATION_TEXT, NIKE_PROD_NOTIFICATION_TITLE, "", NIKE_PROD_NOTIFICATION_PAGE_URL, publishTime, startTime,
				endTime, NOTIFICATION_TYPE_MARKETING, PORTAL_GROUP_MYNTRA };
		
		// pageUrl as blank
		String[] arr18 = { IServiceConstants.ENVIRONMENT_FOX7, NIKE_PROD_NOTIFICATION_TEXT, NIKE_PROD_NOTIFICATION_TITLE, NIKE_PROD_NOTIFICATION_IMG_URL, "", publishTime, startTime,
				endTime, NOTIFICATION_TYPE_MARKETING, PORTAL_GROUP_MYNTRA };
		
		// publishTime as blank
		String[] arr19 = { IServiceConstants.ENVIRONMENT_FOX7, NIKE_PROD_NOTIFICATION_TEXT, NIKE_PROD_NOTIFICATION_TITLE, NIKE_PROD_NOTIFICATION_IMG_URL, NIKE_PROD_NOTIFICATION_PAGE_URL,
				"", startTime, endTime, NOTIFICATION_TYPE_MARKETING, PORTAL_GROUP_MYNTRA };
		
		// endTime as blank
		String[] arr20 = { IServiceConstants.ENVIRONMENT_FOX7, NIKE_PROD_NOTIFICATION_TEXT, NIKE_PROD_NOTIFICATION_TITLE, NIKE_PROD_NOTIFICATION_IMG_URL, NIKE_PROD_NOTIFICATION_PAGE_URL,
				publishTime, startTime, "", NOTIFICATION_TYPE_MARKETING, PORTAL_GROUP_MYNTRA };
		
		// notificationType as blank
		String[] arr21 = { IServiceConstants.ENVIRONMENT_FOX7, NIKE_PROD_NOTIFICATION_TEXT, NIKE_PROD_NOTIFICATION_TITLE, NIKE_PROD_NOTIFICATION_IMG_URL, NIKE_PROD_NOTIFICATION_PAGE_URL,
				publishTime, startTime, endTime, "", PORTAL_GROUP_MYNTRA };
		
		// forPortalGroup as blank
		String[] arr22 = { IServiceConstants.ENVIRONMENT_FOX7, NIKE_PROD_NOTIFICATION_TEXT, NIKE_PROD_NOTIFICATION_TITLE, NIKE_PROD_NOTIFICATION_IMG_URL, NIKE_PROD_NOTIFICATION_PAGE_URL,
				publishTime, startTime, endTime, NOTIFICATION_TYPE_MARKETING, "" };
		
		// forPortalGroup as my-urban-look
		String[] arr23 = { IServiceConstants.ENVIRONMENT_FOX7, NIKE_PROD_NOTIFICATION_TEXT, NIKE_PROD_NOTIFICATION_TITLE, NIKE_PROD_NOTIFICATION_IMG_URL, NIKE_PROD_NOTIFICATION_PAGE_URL,
				publishTime, startTime, endTime, NOTIFICATION_TYPE_MARKETING, PORTAL_GROUP_MY_URBAN_LOOK };
		
		// forPortalGroup as gap
		String[] arr24 = { IServiceConstants.ENVIRONMENT_FOX7, NIKE_PROD_NOTIFICATION_TEXT, NIKE_PROD_NOTIFICATION_TITLE, NIKE_PROD_NOTIFICATION_IMG_URL, NIKE_PROD_NOTIFICATION_PAGE_URL,
				publishTime, startTime, endTime, NOTIFICATION_TYPE_MARKETING, PORTAL_GROUP_GAP };
		
		// forPortalGroup as puma
		String[] arr25 = { IServiceConstants.ENVIRONMENT_FOX7, NIKE_PROD_NOTIFICATION_TEXT, NIKE_PROD_NOTIFICATION_TITLE, NIKE_PROD_NOTIFICATION_IMG_URL, NIKE_PROD_NOTIFICATION_PAGE_URL,
				publishTime, startTime, endTime, NOTIFICATION_TYPE_MARKETING, PORTAL_GROUP_PUMA };
		
		// forPortalGroup as levis
		String[] arr26 = { IServiceConstants.ENVIRONMENT_FOX7, NIKE_PROD_NOTIFICATION_TEXT, NIKE_PROD_NOTIFICATION_TITLE, NIKE_PROD_NOTIFICATION_IMG_URL, NIKE_PROD_NOTIFICATION_PAGE_URL,
				publishTime, startTime, endTime, NOTIFICATION_TYPE_MARKETING, PORTAL_GROUP_LEVIS };
		
		// functional env dp
		// notificationText as blank
		String[] arr27 = { IServiceConstants.ENVIRONMENT_FUNC, "", NIKE_PROD_NOTIFICATION_TITLE, NIKE_PROD_NOTIFICATION_IMG_URL, NIKE_PROD_NOTIFICATION_PAGE_URL,
				publishTime, startTime, endTime, NOTIFICATION_TYPE_MARKETING, PORTAL_GROUP_MYNTRA };
		
		// publish time, start time and endtime are same
		String[] arr28 = { IServiceConstants.ENVIRONMENT_FUNC, NIKE_PROD_NOTIFICATION_TEXT, NIKE_PROD_NOTIFICATION_TITLE, NIKE_PROD_NOTIFICATION_IMG_URL, NIKE_PROD_NOTIFICATION_PAGE_URL,
				publishTime, publishTime, publishTime, NOTIFICATION_TYPE_MARKETING, PORTAL_GROUP_MYNTRA };
		
		// notificationTitle as blank
		String[] arr29 = { IServiceConstants.ENVIRONMENT_FUNC, NIKE_PROD_NOTIFICATION_TEXT, "", NIKE_PROD_NOTIFICATION_IMG_URL, NIKE_PROD_NOTIFICATION_PAGE_URL, publishTime, startTime,
				endTime, NOTIFICATION_TYPE_MARKETING, PORTAL_GROUP_MYNTRA };
		
		// imageUrl as blank
		String[] arr30 = { IServiceConstants.ENVIRONMENT_FUNC, NIKE_PROD_NOTIFICATION_TEXT, NIKE_PROD_NOTIFICATION_TITLE, "", NIKE_PROD_NOTIFICATION_PAGE_URL, publishTime, startTime,
				endTime, NOTIFICATION_TYPE_MARKETING, PORTAL_GROUP_MYNTRA };
		
		// pageUrl as blank
		String[] arr31 = { IServiceConstants.ENVIRONMENT_FUNC, NIKE_PROD_NOTIFICATION_TEXT, NIKE_PROD_NOTIFICATION_TITLE, NIKE_PROD_NOTIFICATION_IMG_URL, "", publishTime, startTime,
				endTime, NOTIFICATION_TYPE_MARKETING, PORTAL_GROUP_MYNTRA };

		// publishTime as blank
		String[] arr32 = { IServiceConstants.ENVIRONMENT_FUNC, NIKE_PROD_NOTIFICATION_TEXT, NIKE_PROD_NOTIFICATION_TITLE, NIKE_PROD_NOTIFICATION_IMG_URL, NIKE_PROD_NOTIFICATION_PAGE_URL,
				"", startTime, endTime, NOTIFICATION_TYPE_MARKETING, PORTAL_GROUP_MYNTRA };
		
		// endTime as blank
		String[] arr33 = { IServiceConstants.ENVIRONMENT_FUNC, NIKE_PROD_NOTIFICATION_TEXT, NIKE_PROD_NOTIFICATION_TITLE, NIKE_PROD_NOTIFICATION_IMG_URL, NIKE_PROD_NOTIFICATION_PAGE_URL,
				publishTime, startTime, "", NOTIFICATION_TYPE_MARKETING, PORTAL_GROUP_MYNTRA };
		
		// notificationType as blank
		String[] arr34 = { IServiceConstants.ENVIRONMENT_FUNC, NIKE_PROD_NOTIFICATION_TEXT, NIKE_PROD_NOTIFICATION_TITLE, NIKE_PROD_NOTIFICATION_IMG_URL, NIKE_PROD_NOTIFICATION_PAGE_URL,
				publishTime, startTime, endTime, "", PORTAL_GROUP_MYNTRA };
		
		// forPortalGroup as blank
		String[] arr35 = { IServiceConstants.ENVIRONMENT_FUNC, NIKE_PROD_NOTIFICATION_TEXT, NIKE_PROD_NOTIFICATION_TITLE, NIKE_PROD_NOTIFICATION_IMG_URL, NIKE_PROD_NOTIFICATION_PAGE_URL,
				publishTime, startTime, endTime, NOTIFICATION_TYPE_MARKETING, "" };
		
		// forPortalGroup as my-urban-look
		String[] arr36 = { IServiceConstants.ENVIRONMENT_FUNC, NIKE_PROD_NOTIFICATION_TEXT, NIKE_PROD_NOTIFICATION_TITLE, NIKE_PROD_NOTIFICATION_IMG_URL, NIKE_PROD_NOTIFICATION_PAGE_URL,
				publishTime, startTime, endTime, NOTIFICATION_TYPE_MARKETING, PORTAL_GROUP_MY_URBAN_LOOK };
		
		// forPortalGroup as gap
		String[] arr37 = { IServiceConstants.ENVIRONMENT_FUNC, NIKE_PROD_NOTIFICATION_TEXT, NIKE_PROD_NOTIFICATION_TITLE, NIKE_PROD_NOTIFICATION_IMG_URL, NIKE_PROD_NOTIFICATION_PAGE_URL,
				publishTime, startTime, endTime, NOTIFICATION_TYPE_MARKETING, PORTAL_GROUP_GAP };
		
		// forPortalGroup as puma
		String[] arr38 = { IServiceConstants.ENVIRONMENT_FUNC, NIKE_PROD_NOTIFICATION_TEXT, NIKE_PROD_NOTIFICATION_TITLE, NIKE_PROD_NOTIFICATION_IMG_URL, NIKE_PROD_NOTIFICATION_PAGE_URL,
				publishTime, startTime, endTime, NOTIFICATION_TYPE_MARKETING, PORTAL_GROUP_PUMA };
		
		// forPortalGroup as levis
		String[] arr39 = { IServiceConstants.ENVIRONMENT_FUNC, NIKE_PROD_NOTIFICATION_TEXT, NIKE_PROD_NOTIFICATION_TITLE, NIKE_PROD_NOTIFICATION_IMG_URL, NIKE_PROD_NOTIFICATION_PAGE_URL,
				publishTime, startTime, endTime, NOTIFICATION_TYPE_MARKETING, PORTAL_GROUP_LEVIS };
		
		// perf env dp
		// notificationText as blank
		String[] arr40 = { IServiceConstants.ENVIRONMENT_PERF, "", NIKE_PROD_NOTIFICATION_TITLE, NIKE_PROD_NOTIFICATION_IMG_URL, NIKE_PROD_NOTIFICATION_PAGE_URL,
				publishTime, startTime, endTime, NOTIFICATION_TYPE_MARKETING, PORTAL_GROUP_MYNTRA };
		
		// publish time, start time and endtime are same
		String[] arr41 = { IServiceConstants.ENVIRONMENT_PERF, NIKE_PROD_NOTIFICATION_TEXT, NIKE_PROD_NOTIFICATION_TITLE, NIKE_PROD_NOTIFICATION_IMG_URL, NIKE_PROD_NOTIFICATION_PAGE_URL,
				publishTime, publishTime, publishTime, NOTIFICATION_TYPE_MARKETING, PORTAL_GROUP_MYNTRA };
		
		// notificationTitle as blank
		String[] arr42 = { IServiceConstants.ENVIRONMENT_PERF, NIKE_PROD_NOTIFICATION_TEXT, "", NIKE_PROD_NOTIFICATION_IMG_URL, NIKE_PROD_NOTIFICATION_PAGE_URL, publishTime, startTime,
				endTime, NOTIFICATION_TYPE_MARKETING, PORTAL_GROUP_MYNTRA };
		
		// imageUrl as blank
		String[] arr43 = { IServiceConstants.ENVIRONMENT_PERF, NIKE_PROD_NOTIFICATION_TEXT, NIKE_PROD_NOTIFICATION_TITLE, "", NIKE_PROD_NOTIFICATION_PAGE_URL, publishTime, startTime,
				endTime, NOTIFICATION_TYPE_MARKETING, PORTAL_GROUP_MYNTRA };
		
		// pageUrl as blank
		String[] arr44 = { IServiceConstants.ENVIRONMENT_PERF, NIKE_PROD_NOTIFICATION_TEXT, NIKE_PROD_NOTIFICATION_TITLE, NIKE_PROD_NOTIFICATION_IMG_URL, "", publishTime, startTime,
				endTime, NOTIFICATION_TYPE_MARKETING, PORTAL_GROUP_MYNTRA };
		
		// publishTime as blank
		String[] arr45 = { IServiceConstants.ENVIRONMENT_PERF, NIKE_PROD_NOTIFICATION_TEXT, NIKE_PROD_NOTIFICATION_TITLE, NIKE_PROD_NOTIFICATION_IMG_URL, NIKE_PROD_NOTIFICATION_PAGE_URL,
				"", startTime, endTime, NOTIFICATION_TYPE_MARKETING, PORTAL_GROUP_MYNTRA };
		
		// endTime as blank
		String[] arr46 = { IServiceConstants.ENVIRONMENT_PERF, NIKE_PROD_NOTIFICATION_TEXT, NIKE_PROD_NOTIFICATION_TITLE, NIKE_PROD_NOTIFICATION_IMG_URL, NIKE_PROD_NOTIFICATION_PAGE_URL,
				publishTime, startTime, "", NOTIFICATION_TYPE_MARKETING, PORTAL_GROUP_MYNTRA };
		
		// notificationType as blank
		String[] arr47 = { IServiceConstants.ENVIRONMENT_PERF, NIKE_PROD_NOTIFICATION_TEXT, NIKE_PROD_NOTIFICATION_TITLE, NIKE_PROD_NOTIFICATION_IMG_URL, NIKE_PROD_NOTIFICATION_PAGE_URL,
				publishTime, startTime, endTime, "", PORTAL_GROUP_MYNTRA };
		
		// forPortalGroup as blank
		String[] arr48 = { IServiceConstants.ENVIRONMENT_PERF, NIKE_PROD_NOTIFICATION_TEXT, NIKE_PROD_NOTIFICATION_TITLE, NIKE_PROD_NOTIFICATION_IMG_URL, NIKE_PROD_NOTIFICATION_PAGE_URL,
				publishTime, startTime, endTime, NOTIFICATION_TYPE_MARKETING, "" };
		
		// forPortalGroup as my-urban-look
		String[] arr49 = { IServiceConstants.ENVIRONMENT_PERF, NIKE_PROD_NOTIFICATION_TEXT, NIKE_PROD_NOTIFICATION_TITLE, NIKE_PROD_NOTIFICATION_IMG_URL, NIKE_PROD_NOTIFICATION_PAGE_URL,
				publishTime, startTime, endTime, NOTIFICATION_TYPE_MARKETING, PORTAL_GROUP_MY_URBAN_LOOK };
		
		// forPortalGroup as gap
		String[] arr50 = { IServiceConstants.ENVIRONMENT_PERF, NIKE_PROD_NOTIFICATION_TEXT, NIKE_PROD_NOTIFICATION_TITLE, NIKE_PROD_NOTIFICATION_IMG_URL, NIKE_PROD_NOTIFICATION_PAGE_URL,
				publishTime, startTime, endTime, NOTIFICATION_TYPE_MARKETING, PORTAL_GROUP_GAP };
		
		// forPortalGroup as puma
		String[] arr51 = { IServiceConstants.ENVIRONMENT_PERF, NIKE_PROD_NOTIFICATION_TEXT, NIKE_PROD_NOTIFICATION_TITLE, NIKE_PROD_NOTIFICATION_IMG_URL, NIKE_PROD_NOTIFICATION_PAGE_URL,
				publishTime, startTime, endTime, NOTIFICATION_TYPE_MARKETING, PORTAL_GROUP_PUMA };
		
		// forPortalGroup as levis
		String[] arr52 = { IServiceConstants.ENVIRONMENT_PERF, NIKE_PROD_NOTIFICATION_TEXT, NIKE_PROD_NOTIFICATION_TITLE, NIKE_PROD_NOTIFICATION_IMG_URL, NIKE_PROD_NOTIFICATION_PAGE_URL,
				publishTime, startTime, endTime, NOTIFICATION_TYPE_MARKETING, PORTAL_GROUP_LEVIS };
		
		Object[][] dataSet = new Object[][] { arr14, arr15, arr16, arr17, arr18, arr19, arr20, arr21, arr22, arr23, arr24, arr25, arr26, arr27, arr28, arr29, arr30, 
				arr31, arr32, arr33, arr34, arr35, arr36, arr37, arr38, arr39, arr40, arr41, arr42, arr43, arr44, arr45, arr46, arr47, arr48, arr49, arr50, arr51, arr52 };
		return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public Object[][] NotificationServiceDP_searchMasterNotification_verifySuccessResponse(ITestContext testContext)
	{
		// {portalGroup}/{notificationType}
		/*String[] arr1 = { ENVIRONMENT_PROD, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_MARKETING };
		String[] arr2 = { ENVIRONMENT_PROD, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_REVENUE_LABS };
		String[] arr3 = { ENVIRONMENT_PROD, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_ORDER_STATUS_CHANGE };
		String[] arr4 = { ENVIRONMENT_PROD, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_PRICE_CHANGE };*/
		
		String[] arr5 = { IServiceConstants.ENVIRONMENT_FOX7, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_MARKETING };
		String[] arr6 = { IServiceConstants.ENVIRONMENT_FOX7, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_REVENUE_LABS };
		String[] arr7 = { IServiceConstants.ENVIRONMENT_FOX7, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_ORDER_STATUS_CHANGE };
		String[] arr8 = { IServiceConstants.ENVIRONMENT_FOX7, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_PRICE_CHANGE };
		
		String[] arr9 = { IServiceConstants.ENVIRONMENT_FUNC, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_MARKETING };
		String[] arr10 = { IServiceConstants.ENVIRONMENT_FUNC, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_REVENUE_LABS };
		String[] arr11 = { IServiceConstants.ENVIRONMENT_FUNC, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_ORDER_STATUS_CHANGE };
		String[] arr12 = { IServiceConstants.ENVIRONMENT_FUNC, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_PRICE_CHANGE };
		
		String[] arr13 = { IServiceConstants.ENVIRONMENT_PERF, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_MARKETING };
		String[] arr14 = { IServiceConstants.ENVIRONMENT_PERF, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_REVENUE_LABS };
		String[] arr15 = { IServiceConstants.ENVIRONMENT_PERF, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_ORDER_STATUS_CHANGE };
		String[] arr16 = { IServiceConstants.ENVIRONMENT_PERF, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_PRICE_CHANGE };

		Object[][] dataSet = new Object[][] { arr5, arr6, arr7, arr8, arr9, arr10, arr11, arr12, arr13, arr14, arr15, arr16 };
		return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] NotificationServiceDP_searchMasterNotification_verifyNodeValues(ITestContext testContext)
	{
		// {portalGroup}/{notificationType}
		/*String[] arr1 = { ENVIRONMENT_PROD, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_MARKETING };
		String[] arr2 = { ENVIRONMENT_PROD, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_REVENUE_LABS };
		String[] arr3 = { ENVIRONMENT_PROD, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_ORDER_STATUS_CHANGE };
		String[] arr4 = { ENVIRONMENT_PROD, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_PRICE_CHANGE };*/
		
		String[] arr5 = { IServiceConstants.ENVIRONMENT_FOX7, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_MARKETING };
		String[] arr6 = { IServiceConstants.ENVIRONMENT_FOX7, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_REVENUE_LABS };
		String[] arr7 = { IServiceConstants.ENVIRONMENT_FOX7, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_ORDER_STATUS_CHANGE };
		String[] arr8 = { IServiceConstants.ENVIRONMENT_FOX7, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_PRICE_CHANGE };
		
		String[] arr9 = { IServiceConstants.ENVIRONMENT_FUNC, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_MARKETING };
		String[] arr10 = { IServiceConstants.ENVIRONMENT_FUNC, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_REVENUE_LABS };
		String[] arr11 = { IServiceConstants.ENVIRONMENT_FUNC, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_ORDER_STATUS_CHANGE };
		String[] arr12 = { IServiceConstants.ENVIRONMENT_FUNC, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_PRICE_CHANGE };
		
		String[] arr13 = { IServiceConstants.ENVIRONMENT_PERF, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_MARKETING };
		String[] arr14 = { IServiceConstants.ENVIRONMENT_PERF, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_REVENUE_LABS };
		String[] arr15 = { IServiceConstants.ENVIRONMENT_PERF, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_ORDER_STATUS_CHANGE };
		String[] arr16 = { IServiceConstants.ENVIRONMENT_PERF, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_PRICE_CHANGE };

		Object[][] dataSet = new Object[][] { arr5, arr6, arr7, arr8, arr9, arr10, arr11, arr12, arr13, arr14, arr15, arr16 };
		return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] NotificationServiceDP_searchMasterNotification_verifyFailureResponse(ITestContext testContext)
	{
		/*String[] arr1 = { ENVIRONMENT_PROD, "", NOTIFICATION_TYPE_MARKETING };
		String[] arr2 = { ENVIRONMENT_PROD, "", NOTIFICATION_TYPE_REVENUE_LABS };
		String[] arr3 = { ENVIRONMENT_PROD, "", NOTIFICATION_TYPE_ORDER_STATUS_CHANGE };
		String[] arr4 = { ENVIRONMENT_PROD, "", NOTIFICATION_TYPE_PRICE_CHANGE };
		String[] arr5 = { ENVIRONMENT_PROD, PORTAL_GROUP_MYNTRA, "" };
		String[] arr6 = { ENVIRONMENT_PROD, PORTAL_GROUP_MY_URBAN_LOOK, "" };
		String[] arr7 = { ENVIRONMENT_PROD, PORTAL_GROUP_GAP, "" };
		String[] arr8 = { ENVIRONMENT_PROD, PORTAL_GROUP_PUMA, "" };
		String[] arr9 = { ENVIRONMENT_PROD, PORTAL_GROUP_LEVIS, "" };*/
		
		String[] arr10 = { IServiceConstants.ENVIRONMENT_FOX7, "", NOTIFICATION_TYPE_MARKETING };
		String[] arr11 = { IServiceConstants.ENVIRONMENT_FOX7, "", NOTIFICATION_TYPE_REVENUE_LABS };
		String[] arr12 = { IServiceConstants.ENVIRONMENT_FOX7, "", NOTIFICATION_TYPE_ORDER_STATUS_CHANGE };
		String[] arr13 = { IServiceConstants.ENVIRONMENT_FOX7, "", NOTIFICATION_TYPE_PRICE_CHANGE };
		String[] arr14 = { IServiceConstants.ENVIRONMENT_FOX7, PORTAL_GROUP_MYNTRA, "" };
		String[] arr15 = { IServiceConstants.ENVIRONMENT_FOX7, PORTAL_GROUP_MY_URBAN_LOOK, "" };
		String[] arr16 = { IServiceConstants.ENVIRONMENT_FOX7, PORTAL_GROUP_GAP, "" };
		String[] arr17 = { IServiceConstants.ENVIRONMENT_FOX7, PORTAL_GROUP_PUMA, "" };
		String[] arr18 = { IServiceConstants.ENVIRONMENT_FOX7, PORTAL_GROUP_LEVIS, "" };
		
		
		String[] arr19 = { IServiceConstants.ENVIRONMENT_FUNC, "", NOTIFICATION_TYPE_MARKETING };
		String[] arr20 = { IServiceConstants.ENVIRONMENT_FUNC, "", NOTIFICATION_TYPE_REVENUE_LABS };
		String[] arr21 = { IServiceConstants.ENVIRONMENT_FUNC, "", NOTIFICATION_TYPE_ORDER_STATUS_CHANGE };
		String[] arr22 = { IServiceConstants.ENVIRONMENT_FUNC, "", NOTIFICATION_TYPE_PRICE_CHANGE };
		String[] arr23 = { IServiceConstants.ENVIRONMENT_FUNC, PORTAL_GROUP_MYNTRA, "" };
		String[] arr24 = { IServiceConstants.ENVIRONMENT_FUNC, PORTAL_GROUP_MY_URBAN_LOOK, "" };
		String[] arr25 = { IServiceConstants.ENVIRONMENT_FUNC, PORTAL_GROUP_GAP, "" };
		String[] arr26 = { IServiceConstants.ENVIRONMENT_FUNC, PORTAL_GROUP_PUMA, "" };
		String[] arr27 = { IServiceConstants.ENVIRONMENT_FUNC, PORTAL_GROUP_LEVIS, "" };
		
		String[] arr28 = { IServiceConstants.ENVIRONMENT_PERF, "", NOTIFICATION_TYPE_MARKETING };
		String[] arr29 = { IServiceConstants.ENVIRONMENT_PERF, "", NOTIFICATION_TYPE_REVENUE_LABS };
		String[] arr30 = { IServiceConstants.ENVIRONMENT_PERF, "", NOTIFICATION_TYPE_ORDER_STATUS_CHANGE };
		String[] arr31 = { IServiceConstants.ENVIRONMENT_PERF, "", NOTIFICATION_TYPE_PRICE_CHANGE };
		String[] arr32 = { IServiceConstants.ENVIRONMENT_PERF, PORTAL_GROUP_MYNTRA, "" };
		String[] arr33 = { IServiceConstants.ENVIRONMENT_PERF, PORTAL_GROUP_MY_URBAN_LOOK, "" };
		String[] arr34 = { IServiceConstants.ENVIRONMENT_PERF, PORTAL_GROUP_GAP, "" };
		String[] arr35 = { IServiceConstants.ENVIRONMENT_PERF, PORTAL_GROUP_PUMA, "" };
		String[] arr36 = { IServiceConstants.ENVIRONMENT_PERF, PORTAL_GROUP_LEVIS, "" };
		
		Object[][] dataSet = new Object[][] { arr10, arr11, arr12, arr13, arr14, arr15, arr16, arr17, arr18, arr19, arr20, arr21, arr22, arr23, arr24, arr25, arr26, 
				arr27, arr28, arr29, arr30, arr31, arr32, arr33, arr34, arr35, arr36};
		return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] NotificationServiceDP_searchMasterNotificationWithSTET_verifySuccessResponse(ITestContext testContext)
	{
		
		Calendar startTimeCal = Calendar.getInstance();
		//startTimeCal.add(Calendar.HOUR, 2);
		String startTime = String.valueOf(startTimeCal.getTimeInMillis());
		
		Calendar endTimeCal = Calendar.getInstance();
		endTimeCal.add(Calendar.MONTH, 4);
		String endTime = String.valueOf(endTimeCal.getTimeInMillis());
		
		// {portalGroup}/{notificationType}
		/*String[] arr1 = { ENVIRONMENT_PROD, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_MARKETING, startTime, endTime };
		String[] arr2 = { ENVIRONMENT_PROD, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_REVENUE_LABS, startTime, endTime };
		String[] arr3 = { ENVIRONMENT_PROD, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_ORDER_STATUS_CHANGE, startTime, endTime };
		String[] arr4 = { ENVIRONMENT_PROD, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_PRICE_CHANGE, startTime, endTime };*/
		
		String[] arr5 = { IServiceConstants.ENVIRONMENT_FOX7, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_MARKETING, startTime, endTime };
		String[] arr6 = { IServiceConstants.ENVIRONMENT_FOX7, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_REVENUE_LABS, startTime, endTime };
		String[] arr7 = { IServiceConstants.ENVIRONMENT_FOX7, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_ORDER_STATUS_CHANGE, startTime, endTime };
		String[] arr8 = { IServiceConstants.ENVIRONMENT_FOX7, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_PRICE_CHANGE, startTime, endTime };
		
		String[] arr9 = { IServiceConstants.ENVIRONMENT_FUNC, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_MARKETING, startTime, endTime };
		String[] arr10 = { IServiceConstants.ENVIRONMENT_FUNC, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_REVENUE_LABS, startTime, endTime };
		String[] arr11 = { IServiceConstants.ENVIRONMENT_FUNC, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_ORDER_STATUS_CHANGE, startTime, endTime };
		String[] arr12 = { IServiceConstants.ENVIRONMENT_FUNC, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_PRICE_CHANGE, startTime, endTime };
		
		String[] arr13 = { IServiceConstants.ENVIRONMENT_PERF, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_MARKETING, startTime, endTime };
		String[] arr14 = { IServiceConstants.ENVIRONMENT_PERF, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_REVENUE_LABS, startTime, endTime };
		String[] arr15 = { IServiceConstants.ENVIRONMENT_PERF, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_ORDER_STATUS_CHANGE, startTime, endTime };
		String[] arr16 = { IServiceConstants.ENVIRONMENT_PERF, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_PRICE_CHANGE, startTime, endTime };

		Object[][] dataSet = new Object[][] { arr5, arr6, arr7, arr8, arr9, arr10, arr11, arr12, arr13, arr14, arr15, arr16 };
		return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] NotificationServiceDP_searchMasterNotificationWithSTET_verifyFailureResponse(ITestContext testContext)
	{
		Calendar startTimeCal = Calendar.getInstance();
		//startTimeCal.add(Calendar.HOUR, 2);
		String startTime = String.valueOf(startTimeCal.getTimeInMillis());
		
		Calendar endTimeCal = Calendar.getInstance();
		endTimeCal.add(Calendar.MONTH, 4);
		String endTime = String.valueOf(endTimeCal.getTimeInMillis());
		
		/*String[] arr1 = { ENVIRONMENT_PROD, "", NOTIFICATION_TYPE_MARKETING, startTime, endTime };
		String[] arr2 = { ENVIRONMENT_PROD, "", NOTIFICATION_TYPE_REVENUE_LABS, startTime, endTime };
		String[] arr3 = { ENVIRONMENT_PROD, "", NOTIFICATION_TYPE_ORDER_STATUS_CHANGE, startTime, endTime };
		String[] arr4 = { ENVIRONMENT_PROD, "", NOTIFICATION_TYPE_PRICE_CHANGE, startTime, endTime };
		String[] arr5 = { ENVIRONMENT_PROD, PORTAL_GROUP_MYNTRA, "", startTime, endTime };
		String[] arr6 = { ENVIRONMENT_PROD, PORTAL_GROUP_MY_URBAN_LOOK, "", startTime, endTime };
		String[] arr7 = { ENVIRONMENT_PROD, PORTAL_GROUP_GAP, "", startTime, endTime };
		String[] arr8 = { ENVIRONMENT_PROD, PORTAL_GROUP_PUMA, "", startTime, endTime };
		String[] arr9 = { ENVIRONMENT_PROD, PORTAL_GROUP_LEVIS, "", startTime, endTime };*/
		
		String[] arr10 = { IServiceConstants.ENVIRONMENT_FOX7, "", NOTIFICATION_TYPE_MARKETING, startTime, endTime };
		String[] arr11 = { IServiceConstants.ENVIRONMENT_FOX7, "", NOTIFICATION_TYPE_REVENUE_LABS, startTime, endTime };
		String[] arr12 = { IServiceConstants.ENVIRONMENT_FOX7, "", NOTIFICATION_TYPE_ORDER_STATUS_CHANGE, startTime, endTime };
		String[] arr13 = { IServiceConstants.ENVIRONMENT_FOX7, "", NOTIFICATION_TYPE_PRICE_CHANGE, startTime, endTime };
		String[] arr14 = { IServiceConstants.ENVIRONMENT_FOX7, PORTAL_GROUP_MYNTRA, "", startTime, endTime };
		String[] arr15 = { IServiceConstants.ENVIRONMENT_FOX7, PORTAL_GROUP_MY_URBAN_LOOK, "", startTime, endTime };
		String[] arr16 = { IServiceConstants.ENVIRONMENT_FOX7, PORTAL_GROUP_GAP, "", startTime, endTime };
		String[] arr17 = { IServiceConstants.ENVIRONMENT_FOX7, PORTAL_GROUP_PUMA, "", startTime, endTime };
		String[] arr18 = { IServiceConstants.ENVIRONMENT_FOX7, PORTAL_GROUP_LEVIS, "", startTime, endTime };
		
		String[] arr19 = { IServiceConstants.ENVIRONMENT_FUNC, "", NOTIFICATION_TYPE_MARKETING, startTime, endTime };
		String[] arr20 = { IServiceConstants.ENVIRONMENT_FUNC, "", NOTIFICATION_TYPE_REVENUE_LABS, startTime, endTime };
		String[] arr21 = { IServiceConstants.ENVIRONMENT_FUNC, "", NOTIFICATION_TYPE_ORDER_STATUS_CHANGE, startTime, endTime };
		String[] arr22 = { IServiceConstants.ENVIRONMENT_FUNC, "", NOTIFICATION_TYPE_PRICE_CHANGE, startTime, endTime };
		String[] arr23 = { IServiceConstants.ENVIRONMENT_FUNC, PORTAL_GROUP_MYNTRA, "", startTime, endTime };
		String[] arr24 = { IServiceConstants.ENVIRONMENT_FUNC, PORTAL_GROUP_MY_URBAN_LOOK, "", startTime, endTime };
		String[] arr25 = { IServiceConstants.ENVIRONMENT_FUNC, PORTAL_GROUP_GAP, "", startTime, endTime };
		String[] arr26 = { IServiceConstants.ENVIRONMENT_FUNC, PORTAL_GROUP_PUMA, "", startTime, endTime };
		String[] arr27 = { IServiceConstants.ENVIRONMENT_FUNC, PORTAL_GROUP_LEVIS, "", startTime, endTime };
		
		String[] arr28 = { IServiceConstants.ENVIRONMENT_PERF, "", NOTIFICATION_TYPE_MARKETING, startTime, endTime };
		String[] arr29 = { IServiceConstants.ENVIRONMENT_PERF, "", NOTIFICATION_TYPE_REVENUE_LABS, startTime, endTime };
		String[] arr30 = { IServiceConstants.ENVIRONMENT_PERF, "", NOTIFICATION_TYPE_ORDER_STATUS_CHANGE, startTime, endTime };
		String[] arr31 = { IServiceConstants.ENVIRONMENT_PERF, "", NOTIFICATION_TYPE_PRICE_CHANGE, startTime, endTime };
		String[] arr32 = { IServiceConstants.ENVIRONMENT_PERF, PORTAL_GROUP_MYNTRA, "", startTime, endTime };
		String[] arr33 = { IServiceConstants.ENVIRONMENT_PERF, PORTAL_GROUP_MY_URBAN_LOOK, "", startTime, endTime };
		String[] arr34 = { IServiceConstants.ENVIRONMENT_PERF, PORTAL_GROUP_GAP, "", startTime, endTime };
		String[] arr35 = { IServiceConstants.ENVIRONMENT_PERF, PORTAL_GROUP_PUMA, "", startTime, endTime };
		String[] arr36 = { IServiceConstants.ENVIRONMENT_PERF, PORTAL_GROUP_LEVIS, "", startTime, endTime };
		
		Object[][] dataSet = new Object[][] { arr10, arr11, arr12, arr13, arr14, arr15, arr16, arr17, arr18, arr19, arr20, arr21, arr22, arr23, arr24, arr25, arr26, 
				arr27, arr28, arr29, arr30, arr31, arr32, arr33, arr34, arr35, arr36};
		return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] NotificationServiceDP_searchMasterNotificationWithSTET_verifyNodeValues(ITestContext testContext)
	{
		Calendar startTimeCal = Calendar.getInstance();
		//startTimeCal.add(Calendar.HOUR, 2);
		String startTime = String.valueOf(startTimeCal.getTimeInMillis());
		
		Calendar endTimeCal = Calendar.getInstance();
		endTimeCal.add(Calendar.MONTH, 4);
		String endTime = String.valueOf(endTimeCal.getTimeInMillis());
		
		// {portalGroup}/{notificationType}
		/*String[] arr1 = { ENVIRONMENT_PROD, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_MARKETING, startTime, endTime };
		String[] arr2 = { ENVIRONMENT_PROD, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_REVENUE_LABS, startTime, endTime };
		String[] arr3 = { ENVIRONMENT_PROD, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_ORDER_STATUS_CHANGE, startTime, endTime };
		String[] arr4 = { ENVIRONMENT_PROD, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_PRICE_CHANGE, startTime, endTime };*/
		
		String[] arr5 = { IServiceConstants.ENVIRONMENT_FOX7, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_MARKETING, startTime, endTime };
		String[] arr6 = { IServiceConstants.ENVIRONMENT_FOX7, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_REVENUE_LABS, startTime, endTime };
		String[] arr7 = { IServiceConstants.ENVIRONMENT_FOX7, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_ORDER_STATUS_CHANGE, startTime, endTime };
		String[] arr8 = { IServiceConstants.ENVIRONMENT_FOX7, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_PRICE_CHANGE, startTime, endTime };
		
		String[] arr9 = { IServiceConstants.ENVIRONMENT_FUNC, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_MARKETING, startTime, endTime };
		String[] arr10 = { IServiceConstants.ENVIRONMENT_FUNC, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_REVENUE_LABS, startTime, endTime };
		String[] arr11 = { IServiceConstants.ENVIRONMENT_FUNC, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_ORDER_STATUS_CHANGE, startTime, endTime };
		String[] arr12 = { IServiceConstants.ENVIRONMENT_FUNC, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_PRICE_CHANGE, startTime, endTime };
		
		String[] arr13 = { IServiceConstants.ENVIRONMENT_PERF, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_MARKETING, startTime, endTime };
		String[] arr14 = { IServiceConstants.ENVIRONMENT_PERF, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_REVENUE_LABS, startTime, endTime };
		String[] arr15 = { IServiceConstants.ENVIRONMENT_PERF, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_ORDER_STATUS_CHANGE, startTime, endTime };
		String[] arr16 = { IServiceConstants.ENVIRONMENT_PERF, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_PRICE_CHANGE, startTime, endTime };

		Object[][] dataSet = new Object[][] { arr5, arr6, arr7, arr8, arr9, arr10, arr11, arr12, arr13, arr14, arr15, arr16 };
		return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] NotificationServiceDP_getDetailsMasterNotification_verifySuccessResponse(ITestContext testContext)
	{
		// {portalGroup}/{notificationType}
		/*String[] arr1 = { ENVIRONMENT_PROD, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_MARKETING };
		String[] arr2 = { ENVIRONMENT_PROD, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_REVENUE_LABS };
		String[] arr3 = { ENVIRONMENT_PROD, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_ORDER_STATUS_CHANGE };
		String[] arr4 = { ENVIRONMENT_PROD, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_PRICE_CHANGE };*/
		
		String[] arr5 = { IServiceConstants.ENVIRONMENT_FOX7, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_MARKETING };
		String[] arr6 = { IServiceConstants.ENVIRONMENT_FOX7, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_REVENUE_LABS };
		String[] arr7 = { IServiceConstants.ENVIRONMENT_FOX7, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_ORDER_STATUS_CHANGE };
		String[] arr8 = { IServiceConstants.ENVIRONMENT_FOX7, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_PRICE_CHANGE };
		
		String[] arr9 = { IServiceConstants.ENVIRONMENT_FUNC, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_MARKETING };
		String[] arr10 = { IServiceConstants.ENVIRONMENT_FUNC, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_REVENUE_LABS };
		String[] arr11 = { IServiceConstants.ENVIRONMENT_FUNC, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_ORDER_STATUS_CHANGE };
		String[] arr12 = { IServiceConstants.ENVIRONMENT_FUNC, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_PRICE_CHANGE };
		
		String[] arr13 = { IServiceConstants.ENVIRONMENT_PERF, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_MARKETING };
		String[] arr14 = { IServiceConstants.ENVIRONMENT_PERF, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_REVENUE_LABS };
		String[] arr15 = { IServiceConstants.ENVIRONMENT_PERF, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_ORDER_STATUS_CHANGE };
		String[] arr16 = { IServiceConstants.ENVIRONMENT_PERF, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_PRICE_CHANGE };

		Object[][] dataSet = new Object[][] { arr5, arr6, arr7, arr8, arr9, arr10, arr11, arr12, arr13, arr14, arr15, arr16 };
		return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] NotificationServiceDP_getDetailsMasterNotification_verifyNodeValues(ITestContext testContext)
	{
		// {portalGroup}/{notificationType}
		/*String[] arr1 = { ENVIRONMENT_PROD, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_MARKETING };
		String[] arr2 = { ENVIRONMENT_PROD, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_REVENUE_LABS };
		String[] arr3 = { ENVIRONMENT_PROD, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_ORDER_STATUS_CHANGE };
		String[] arr4 = { ENVIRONMENT_PROD, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_PRICE_CHANGE };*/
		
		String[] arr5 = { IServiceConstants.ENVIRONMENT_FOX7, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_MARKETING };
		String[] arr6 = { IServiceConstants.ENVIRONMENT_FOX7, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_REVENUE_LABS };
		String[] arr7 = { IServiceConstants.ENVIRONMENT_FOX7, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_ORDER_STATUS_CHANGE };
		String[] arr8 = { IServiceConstants.ENVIRONMENT_FOX7, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_PRICE_CHANGE };
		
		String[] arr9 = { IServiceConstants.ENVIRONMENT_FUNC, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_MARKETING };
		String[] arr10 = { IServiceConstants.ENVIRONMENT_FUNC, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_REVENUE_LABS };
		String[] arr11 = { IServiceConstants.ENVIRONMENT_FUNC, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_ORDER_STATUS_CHANGE };
		String[] arr12 = { IServiceConstants.ENVIRONMENT_FUNC, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_PRICE_CHANGE };
		
		String[] arr13 = { IServiceConstants.ENVIRONMENT_PERF, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_MARKETING };
		String[] arr14 = { IServiceConstants.ENVIRONMENT_PERF, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_REVENUE_LABS };
		String[] arr15 = { IServiceConstants.ENVIRONMENT_PERF, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_ORDER_STATUS_CHANGE };
		String[] arr16 = { IServiceConstants.ENVIRONMENT_PERF, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_PRICE_CHANGE };

		Object[][] dataSet = new Object[][] { arr5, arr6, arr7, arr8, arr9, arr10, arr11, arr12, arr13, arr14, arr15, arr16 };
		return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public Object[][] NotificationServiceDP_getDetailsMasterNotification_verifyFailureResponse(ITestContext testContext)
	{
		// {portalGroup}/{masterNotificationId}
		String masterNotificationId = String.valueOf(new UUID());
		/*String[] arr1 = { ENVIRONMENT_PROD, "", masterNotificationId }; // portalGroup as blank
		String[] arr2 = { ENVIRONMENT_PROD, PORTAL_GROUP_MYNTRA, "" }; // masterNotificationId as blank
		String[] arr3 = { ENVIRONMENT_PROD, "", "" }; // portalGroup and masterNotificationId as blank*/	
		
		String[] arr4 = { IServiceConstants.ENVIRONMENT_FOX7, "", masterNotificationId }; // portalGroup as blank
		String[] arr5 = { IServiceConstants.ENVIRONMENT_FOX7, PORTAL_GROUP_MYNTRA, "" }; // masterNotificationId as blank
		String[] arr6 = { IServiceConstants.ENVIRONMENT_FOX7, "", "" }; // portalGroup and masterNotificationId as blank
		
		String[] arr7 = { IServiceConstants.ENVIRONMENT_FUNC, "", masterNotificationId }; // portalGroup as blank
		String[] arr8 = { IServiceConstants.ENVIRONMENT_FUNC, PORTAL_GROUP_MYNTRA, "" }; // masterNotificationId as blank
		String[] arr9 = { IServiceConstants.ENVIRONMENT_FUNC, "", "" }; // portalGroup and masterNotificationId as blank

		String[] arr10 = { IServiceConstants.ENVIRONMENT_PERF, "", masterNotificationId }; // portalGroup as blank
		String[] arr11 = { IServiceConstants.ENVIRONMENT_PERF, PORTAL_GROUP_MYNTRA, "" }; // masterNotificationId as blank
		String[] arr12 = { IServiceConstants.ENVIRONMENT_PERF, "", "" }; // portalGroup and masterNotificationId as blank
		
		Object[][] dataSet = new Object[][] { arr4, arr5, arr6, arr7, arr8, arr9, arr10, arr11, arr12 };
		return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] NotificationServiceDP_deleteNotifications_verifySuccessResponse(ITestContext testContext)
	{
		// {portalGroup}/{notificationType}
		/*String[] arr1 = { ENVIRONMENT_PROD, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_MARKETING };
		String[] arr2 = { ENVIRONMENT_PROD, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_REVENUE_LABS };
		String[] arr3 = { ENVIRONMENT_PROD, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_ORDER_STATUS_CHANGE };
		String[] arr4 = { ENVIRONMENT_PROD, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_PRICE_CHANGE };*/
		
		String[] arr5 = { IServiceConstants.ENVIRONMENT_FOX7, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_MARKETING };
		String[] arr6 = { IServiceConstants.ENVIRONMENT_FOX7, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_REVENUE_LABS };
		String[] arr7 = { IServiceConstants.ENVIRONMENT_FOX7, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_ORDER_STATUS_CHANGE };
		String[] arr8 = { IServiceConstants.ENVIRONMENT_FOX7, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_PRICE_CHANGE };
		
		String[] arr9 = { IServiceConstants.ENVIRONMENT_FUNC, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_MARKETING };
		String[] arr10 = { IServiceConstants.ENVIRONMENT_FUNC, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_REVENUE_LABS };
		String[] arr11 = { IServiceConstants.ENVIRONMENT_FUNC, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_ORDER_STATUS_CHANGE };
		String[] arr12 = { IServiceConstants.ENVIRONMENT_FUNC, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_PRICE_CHANGE };
		
		String[] arr13 = { IServiceConstants.ENVIRONMENT_PERF, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_MARKETING };
		String[] arr14 = { IServiceConstants.ENVIRONMENT_PERF, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_REVENUE_LABS };
		String[] arr15 = { IServiceConstants.ENVIRONMENT_PERF, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_ORDER_STATUS_CHANGE };
		String[] arr16 = { IServiceConstants.ENVIRONMENT_PERF, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_PRICE_CHANGE };

		Object[][] dataSet = new Object[][] { arr5, arr6, arr7, arr8, arr9, arr10, arr11, arr12, arr13, arr14, arr15, arr16 };
		return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] NotificationServiceDP_deleteNotifications_verifyNodeValues(ITestContext testContext)
	{
		// {portalGroup}/{notificationType}
		/*String[] arr1 = { ENVIRONMENT_PROD, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_MARKETING };
		String[] arr2 = { ENVIRONMENT_PROD, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_REVENUE_LABS };
		String[] arr3 = { ENVIRONMENT_PROD, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_ORDER_STATUS_CHANGE };
		String[] arr4 = { ENVIRONMENT_PROD, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_PRICE_CHANGE };*/
		
		String[] arr5 = { IServiceConstants.ENVIRONMENT_FOX7, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_MARKETING };
		String[] arr6 = { IServiceConstants.ENVIRONMENT_FOX7, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_REVENUE_LABS };
		String[] arr7 = { IServiceConstants.ENVIRONMENT_FOX7, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_ORDER_STATUS_CHANGE };
		String[] arr8 = { IServiceConstants.ENVIRONMENT_FOX7, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_PRICE_CHANGE };
		
		String[] arr9 = { IServiceConstants.ENVIRONMENT_FUNC, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_MARKETING };
		String[] arr10 = { IServiceConstants.ENVIRONMENT_FUNC, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_REVENUE_LABS };
		String[] arr11 = { IServiceConstants.ENVIRONMENT_FUNC, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_ORDER_STATUS_CHANGE };
		String[] arr12 = { IServiceConstants.ENVIRONMENT_FUNC, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_PRICE_CHANGE };
		
		String[] arr13 = { IServiceConstants.ENVIRONMENT_PERF, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_MARKETING };
		String[] arr14 = { IServiceConstants.ENVIRONMENT_PERF, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_REVENUE_LABS };
		String[] arr15 = { IServiceConstants.ENVIRONMENT_PERF, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_ORDER_STATUS_CHANGE };
		String[] arr16 = { IServiceConstants.ENVIRONMENT_PERF, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_PRICE_CHANGE };

		Object[][] dataSet = new Object[][] { arr5, arr6, arr7, arr8, arr9, arr10, arr11, arr12, arr13, arr14, arr15, arr16 };
		return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public Object[][] NotificationServiceDP_deleteNotifications_verifyFailureResponse(ITestContext testContext)
	{
		// {portalGroup}/{masterNotificationId}
		String masterNotificationId = String.valueOf(new UUID());
		/*String[] arr1 = { ENVIRONMENT_PROD, "", masterNotificationId }; // portalGroup as blank
		String[] arr2 = { ENVIRONMENT_PROD, PORTAL_GROUP_MYNTRA, "" }; // masterNotificationId as blank
		String[] arr3 = { ENVIRONMENT_PROD, "", "" }; // portalGroup and masterNotificationId as blank*/	
		
		String[] arr4 = { IServiceConstants.ENVIRONMENT_FOX7, "", masterNotificationId }; // portalGroup as blank
		String[] arr5 = { IServiceConstants.ENVIRONMENT_FOX7, PORTAL_GROUP_MYNTRA, "" }; // masterNotificationId as blank
		String[] arr6 = { IServiceConstants.ENVIRONMENT_FOX7, "", "" }; // portalGroup and masterNotificationId as blank
		
		String[] arr7 = { IServiceConstants.ENVIRONMENT_FUNC, "", masterNotificationId }; // portalGroup as blank
		String[] arr8 = { IServiceConstants.ENVIRONMENT_FUNC, PORTAL_GROUP_MYNTRA, "" }; // masterNotificationId as blank
		String[] arr9 = { IServiceConstants.ENVIRONMENT_FUNC, "", "" }; // portalGroup and masterNotificationId as blank

		String[] arr10 = { IServiceConstants.ENVIRONMENT_PERF, "", masterNotificationId }; // portalGroup as blank
		String[] arr11 = { IServiceConstants.ENVIRONMENT_PERF, PORTAL_GROUP_MYNTRA, "" }; // masterNotificationId as blank
		String[] arr12 = { IServiceConstants.ENVIRONMENT_PERF, "", "" }; // portalGroup and masterNotificationId as blank
		
		Object[][] dataSet = new Object[][] { arr4, arr5, arr6, arr7, arr8, arr9, arr10, arr11, arr12 };
		return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] NotificationServiceDP_createNotificationForSingleUser_verifySuccessResponse(ITestContext testContext)
	{
		// {userId}/{portalGroup}/{notificationType}
		/*String[] arr1 = { ENVIRONMENT_PROD, PROD_VALID_USER_ID, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_MARKETING };
		String[] arr2 = { ENVIRONMENT_PROD, PROD_VALID_USER_ID, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_REVENUE_LABS }; 
		String[] arr3 = { ENVIRONMENT_PROD, PROD_VALID_USER_ID, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_ORDER_STATUS_CHANGE }; 
		String[] arr4 = { ENVIRONMENT_PROD, PROD_VALID_USER_ID, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_PRICE_CHANGE }; */
		
		String[] arr5 = { IServiceConstants.ENVIRONMENT_FOX7, FOX7_VALID_USER_ID, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_MARKETING };
		String[] arr6 = { IServiceConstants.ENVIRONMENT_FOX7, FOX7_VALID_USER_ID, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_REVENUE_LABS };
		String[] arr7 = { IServiceConstants.ENVIRONMENT_FOX7, FOX7_VALID_USER_ID, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_ORDER_STATUS_CHANGE };
		String[] arr8 = { IServiceConstants.ENVIRONMENT_FOX7, FOX7_VALID_USER_ID, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_PRICE_CHANGE };
		
		String[] arr9 = { IServiceConstants.ENVIRONMENT_FUNC, FUNC_VALID_USER_ID, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_MARKETING };
		String[] arr10 = { IServiceConstants.ENVIRONMENT_FUNC, FUNC_VALID_USER_ID, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_REVENUE_LABS };
		String[] arr11 = { IServiceConstants.ENVIRONMENT_FUNC, FUNC_VALID_USER_ID, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_ORDER_STATUS_CHANGE };
		String[] arr12 = { IServiceConstants.ENVIRONMENT_FUNC, FUNC_VALID_USER_ID, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_PRICE_CHANGE };
		
		
		String[] arr13 = { IServiceConstants.ENVIRONMENT_PERF, PERF_VALID_USER_ID, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_MARKETING };
		String[] arr14 = { IServiceConstants.ENVIRONMENT_PERF, PERF_VALID_USER_ID, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_REVENUE_LABS };
		String[] arr15 = { IServiceConstants.ENVIRONMENT_PERF, PERF_VALID_USER_ID, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_ORDER_STATUS_CHANGE };
		String[] arr16 = { IServiceConstants.ENVIRONMENT_PERF, PERF_VALID_USER_ID, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_PRICE_CHANGE };

		Object[][] dataSet = new Object[][] { arr5, arr6, arr7, arr8, arr9, arr10, arr11, arr12, arr13, arr14, arr15, arr16 };
		return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] NotificationServiceDP_createNotificationForSingleUser_verifyNodeValues(ITestContext testContext)
	{
		// {userId}/{portalGroup}/{notificationType}
		/*String[] arr1 = { ENVIRONMENT_PROD, PROD_VALID_USER_ID, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_MARKETING };
		String[] arr2 = { ENVIRONMENT_PROD, PROD_VALID_USER_ID, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_REVENUE_LABS }; 
		String[] arr3 = { ENVIRONMENT_PROD, PROD_VALID_USER_ID, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_ORDER_STATUS_CHANGE }; 
		String[] arr4 = { ENVIRONMENT_PROD, PROD_VALID_USER_ID, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_PRICE_CHANGE }; */
		
		String[] arr5 = { IServiceConstants.ENVIRONMENT_FOX7, FOX7_VALID_USER_ID, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_MARKETING };
		String[] arr6 = { IServiceConstants.ENVIRONMENT_FOX7, FOX7_VALID_USER_ID, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_REVENUE_LABS };
		String[] arr7 = { IServiceConstants.ENVIRONMENT_FOX7, FOX7_VALID_USER_ID, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_ORDER_STATUS_CHANGE };
		String[] arr8 = { IServiceConstants.ENVIRONMENT_FOX7, FOX7_VALID_USER_ID, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_PRICE_CHANGE };
		
		String[] arr9 = { IServiceConstants.ENVIRONMENT_FUNC, FUNC_VALID_USER_ID, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_MARKETING };
		String[] arr10 = { IServiceConstants.ENVIRONMENT_FUNC, FUNC_VALID_USER_ID, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_REVENUE_LABS };
		String[] arr11 = { IServiceConstants.ENVIRONMENT_FUNC, FUNC_VALID_USER_ID, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_ORDER_STATUS_CHANGE };
		String[] arr12 = { IServiceConstants.ENVIRONMENT_FUNC, FUNC_VALID_USER_ID, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_PRICE_CHANGE };
		
		
		String[] arr13 = { IServiceConstants.ENVIRONMENT_PERF, PERF_VALID_USER_ID, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_MARKETING };
		String[] arr14 = { IServiceConstants.ENVIRONMENT_PERF, PERF_VALID_USER_ID, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_REVENUE_LABS };
		String[] arr15 = { IServiceConstants.ENVIRONMENT_PERF, PERF_VALID_USER_ID, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_ORDER_STATUS_CHANGE };
		String[] arr16 = { IServiceConstants.ENVIRONMENT_PERF, PERF_VALID_USER_ID, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_PRICE_CHANGE };

		Object[][] dataSet = new Object[][] { arr5, arr6, arr7, arr8, arr9, arr10, arr11, arr12, arr13, arr14, arr15, arr16 };
		return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public Object[][] NotificationServiceDP_createNotificationForSingleUser_verifyFailureResponse(ITestContext testContext)
	{
		// {userId}/{portalGroup}/{masterNotificationId}
		String masterNotificationId = String.valueOf(new UUID());
		/*String[] arr1 = { ENVIRONMENT_PROD, PROD_VALID_USER_ID, PORTAL_GROUP_MYNTRA, "" };
		String[] arr2 = { ENVIRONMENT_PROD, PROD_VALID_USER_ID, PORTAL_GROUP_MY_URBAN_LOOK, masterNotificationId };
		String[] arr3 = { ENVIRONMENT_PROD, PROD_VALID_USER_ID, PORTAL_GROUP_GAP, masterNotificationId };
		String[] arr4 = { ENVIRONMENT_PROD, PROD_VALID_USER_ID, PORTAL_GROUP_PUMA, masterNotificationId };
		String[] arr5 = { ENVIRONMENT_PROD, PROD_VALID_USER_ID, PORTAL_GROUP_LEVIS, masterNotificationId };*/
		
		String[] arr6 = { IServiceConstants.ENVIRONMENT_FOX7, FOX7_VALID_USER_ID, PORTAL_GROUP_MYNTRA, "" };
		String[] arr7 = { IServiceConstants.ENVIRONMENT_FOX7, FOX7_VALID_USER_ID, PORTAL_GROUP_MY_URBAN_LOOK, masterNotificationId };
		String[] arr8 = { IServiceConstants.ENVIRONMENT_FOX7, FOX7_VALID_USER_ID, PORTAL_GROUP_GAP, masterNotificationId };
		String[] arr9 = { IServiceConstants.ENVIRONMENT_FOX7, FOX7_VALID_USER_ID, PORTAL_GROUP_PUMA, masterNotificationId };
		String[] arr10 = { IServiceConstants.ENVIRONMENT_FOX7, FOX7_VALID_USER_ID, PORTAL_GROUP_LEVIS, masterNotificationId };
		
		String[] arr11 = { IServiceConstants.ENVIRONMENT_FUNC, FUNC_VALID_USER_ID, PORTAL_GROUP_MYNTRA, "" };
		String[] arr12 = { IServiceConstants.ENVIRONMENT_FUNC, FUNC_VALID_USER_ID, PORTAL_GROUP_MY_URBAN_LOOK, masterNotificationId };
		String[] arr13 = { IServiceConstants.ENVIRONMENT_FUNC, FUNC_VALID_USER_ID, PORTAL_GROUP_GAP, masterNotificationId };
		String[] arr14 = { IServiceConstants.ENVIRONMENT_FUNC, FUNC_VALID_USER_ID, PORTAL_GROUP_PUMA, masterNotificationId };
		String[] arr15 = { IServiceConstants.ENVIRONMENT_FUNC, FUNC_VALID_USER_ID, PORTAL_GROUP_LEVIS, masterNotificationId };
		
		String[] arr16 = { IServiceConstants.ENVIRONMENT_PERF, PERF_VALID_USER_ID, PORTAL_GROUP_MYNTRA, "" };
		String[] arr17 = { IServiceConstants.ENVIRONMENT_PERF, PERF_VALID_USER_ID, PORTAL_GROUP_MY_URBAN_LOOK, masterNotificationId };
		String[] arr18 = { IServiceConstants.ENVIRONMENT_PERF, PERF_VALID_USER_ID, PORTAL_GROUP_GAP, masterNotificationId };
		String[] arr19 = { IServiceConstants.ENVIRONMENT_PERF, PERF_VALID_USER_ID, PORTAL_GROUP_PUMA, masterNotificationId };
		String[] arr20 = { IServiceConstants.ENVIRONMENT_PERF, PERF_VALID_USER_ID, PORTAL_GROUP_LEVIS, masterNotificationId };
		
		Object[][] dataSet = new Object[][] { arr6, arr7, arr8, arr9, arr10, arr11, arr12, arr13, arr14, arr15, arr16, arr17, arr18, arr19, arr20 };
		return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public Object[][] NotificationServiceDP_createNotificationForSetOfUsers_verifySuccessResponse(ITestContext testContext)
	{
		// {portalGroup}/{notificationType}/{userIds}
		/*String[] arr1 = { ENVIRONMENT_PROD, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_MARKETING, PROD_VALID_USER_IDS };
		String[] arr2 = { ENVIRONMENT_PROD, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_REVENUE_LABS, PROD_VALID_USER_IDS };
		String[] arr3 = { ENVIRONMENT_PROD, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_ORDER_STATUS_CHANGE, PROD_VALID_USER_IDS };
		String[] arr4 = { ENVIRONMENT_PROD, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_PRICE_CHANGE, PROD_VALID_USER_IDS };*/
		
		String[] arr5 = { IServiceConstants.ENVIRONMENT_FOX7, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_MARKETING, FOX7_VALID_USER_IDS };
		String[] arr6 = { IServiceConstants.ENVIRONMENT_FOX7, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_REVENUE_LABS, FOX7_VALID_USER_IDS };
		String[] arr7 = { IServiceConstants.ENVIRONMENT_FOX7, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_ORDER_STATUS_CHANGE, FOX7_VALID_USER_IDS };
		String[] arr8 = { IServiceConstants.ENVIRONMENT_FOX7, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_PRICE_CHANGE, FOX7_VALID_USER_IDS };
		
		String[] arr9 = { IServiceConstants.ENVIRONMENT_FUNC, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_MARKETING, FUNC_VALID_USER_IDS};
		String[] arr10 = { IServiceConstants.ENVIRONMENT_FUNC, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_REVENUE_LABS, FUNC_VALID_USER_IDS };
		String[] arr11 = { IServiceConstants.ENVIRONMENT_FUNC, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_ORDER_STATUS_CHANGE, FUNC_VALID_USER_IDS };
		String[] arr12 = { IServiceConstants.ENVIRONMENT_FUNC, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_PRICE_CHANGE, FUNC_VALID_USER_IDS };
		
		String[] arr13 = { IServiceConstants.ENVIRONMENT_PERF, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_MARKETING, PERF_VALID_USER_IDS};
		String[] arr14 = { IServiceConstants.ENVIRONMENT_PERF, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_REVENUE_LABS, PERF_VALID_USER_IDS };
		String[] arr15 = { IServiceConstants.ENVIRONMENT_PERF, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_ORDER_STATUS_CHANGE, PERF_VALID_USER_IDS };
		String[] arr16 = { IServiceConstants.ENVIRONMENT_PERF, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_PRICE_CHANGE, PERF_VALID_USER_IDS };
		
		Object[][] dataSet = new Object[][] { arr5, arr6, arr7, arr8, arr9, arr10, arr11, arr12, arr13, arr14, arr15, arr16 };
		return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] NotificationServiceDP_createNotificationForSetOfUsers_verifyNodeValues(ITestContext testContext)
	{
		// {portalGroup}/{notificationType}/{userIds}
		/*String[] arr1 = { ENVIRONMENT_PROD, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_MARKETING, PROD_VALID_USER_IDS };
		String[] arr2 = { ENVIRONMENT_PROD, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_REVENUE_LABS, PROD_VALID_USER_IDS };
		String[] arr3 = { ENVIRONMENT_PROD, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_ORDER_STATUS_CHANGE, PROD_VALID_USER_IDS };
		String[] arr4 = { ENVIRONMENT_PROD, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_PRICE_CHANGE, PROD_VALID_USER_IDS };*/
		
		String[] arr5 = { IServiceConstants.ENVIRONMENT_FOX7, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_MARKETING, FOX7_VALID_USER_IDS };
		String[] arr6 = { IServiceConstants.ENVIRONMENT_FOX7, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_REVENUE_LABS, FOX7_VALID_USER_IDS };
		String[] arr7 = { IServiceConstants.ENVIRONMENT_FOX7, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_ORDER_STATUS_CHANGE, FOX7_VALID_USER_IDS };
		String[] arr8 = { IServiceConstants.ENVIRONMENT_FOX7, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_PRICE_CHANGE, FOX7_VALID_USER_IDS };
		
		String[] arr9 = { IServiceConstants.ENVIRONMENT_FUNC, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_MARKETING, FUNC_VALID_USER_IDS};
		String[] arr10 = { IServiceConstants.ENVIRONMENT_FUNC, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_REVENUE_LABS, FUNC_VALID_USER_IDS };
		String[] arr11 = { IServiceConstants.ENVIRONMENT_FUNC, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_ORDER_STATUS_CHANGE, FUNC_VALID_USER_IDS };
		String[] arr12 = { IServiceConstants.ENVIRONMENT_FUNC, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_PRICE_CHANGE, FUNC_VALID_USER_IDS };
		
		String[] arr13 = { IServiceConstants.ENVIRONMENT_PERF, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_MARKETING, PERF_VALID_USER_IDS};
		String[] arr14 = { IServiceConstants.ENVIRONMENT_PERF, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_REVENUE_LABS, PERF_VALID_USER_IDS };
		String[] arr15 = { IServiceConstants.ENVIRONMENT_PERF, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_ORDER_STATUS_CHANGE, PERF_VALID_USER_IDS };
		String[] arr16 = { IServiceConstants.ENVIRONMENT_PERF, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_PRICE_CHANGE, PERF_VALID_USER_IDS };
		
		Object[][] dataSet = new Object[][] { arr5, arr6, arr7, arr8, arr9, arr10, arr11, arr12, arr13, arr14, arr15, arr16 };
		return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public Object[][] NotificationServiceDP_createNotificationForSetOfUsers_verifyFailureResponse(ITestContext testContext)
	{
		// {portalGroup}/{masterNotificationId}/{userIds}
		String masterNotificationId = String.valueOf(new UUID());
		/*String[] arr1 = { ENVIRONMENT_PROD, PORTAL_GROUP_MYNTRA, "abcd$1234", PROD_VALID_USER_IDS };
		String[] arr2 = { ENVIRONMENT_PROD, PORTAL_GROUP_MY_URBAN_LOOK, masterNotificationId, PROD_VALID_USER_IDS };
		String[] arr3 = { ENVIRONMENT_PROD, PORTAL_GROUP_GAP, masterNotificationId, PROD_VALID_USER_IDS };
		String[] arr4 = { ENVIRONMENT_PROD, PORTAL_GROUP_PUMA, masterNotificationId, PROD_VALID_USER_IDS };
		String[] arr5 = { ENVIRONMENT_PROD, PORTAL_GROUP_LEVIS, masterNotificationId, PROD_VALID_USER_IDS };*/

		String[] arr6 = { IServiceConstants.ENVIRONMENT_FOX7, PORTAL_GROUP_MYNTRA, "abcd$1234", FOX7_VALID_USER_IDS };
		String[] arr7 = { IServiceConstants.ENVIRONMENT_FOX7, PORTAL_GROUP_MY_URBAN_LOOK, masterNotificationId, FOX7_VALID_USER_IDS };
		String[] arr8 = { IServiceConstants.ENVIRONMENT_FOX7, PORTAL_GROUP_GAP, masterNotificationId, FOX7_VALID_USER_IDS };
		String[] arr9 = { IServiceConstants.ENVIRONMENT_FOX7, PORTAL_GROUP_PUMA, masterNotificationId, FOX7_VALID_USER_IDS };
		String[] arr10 = { IServiceConstants.ENVIRONMENT_FOX7, PORTAL_GROUP_LEVIS, masterNotificationId, FOX7_VALID_USER_IDS };
		
		String[] arr11 = { IServiceConstants.ENVIRONMENT_FUNC, PORTAL_GROUP_MYNTRA, "abcd$1234", FUNC_VALID_USER_IDS };
		String[] arr12 = { IServiceConstants.ENVIRONMENT_FUNC, PORTAL_GROUP_MY_URBAN_LOOK, masterNotificationId, FUNC_VALID_USER_IDS };
		String[] arr13 = { IServiceConstants.ENVIRONMENT_FUNC, PORTAL_GROUP_GAP, masterNotificationId, FUNC_VALID_USER_IDS };
		String[] arr14 = { IServiceConstants.ENVIRONMENT_FUNC, PORTAL_GROUP_PUMA, masterNotificationId, FUNC_VALID_USER_IDS };
		String[] arr15 = { IServiceConstants.ENVIRONMENT_FUNC, PORTAL_GROUP_LEVIS, masterNotificationId, FUNC_VALID_USER_IDS };

		String[] arr16 = { IServiceConstants.ENVIRONMENT_PERF, PORTAL_GROUP_MYNTRA, "abcd$1234", PERF_VALID_USER_IDS };
		String[] arr17 = { IServiceConstants.ENVIRONMENT_PERF, PORTAL_GROUP_MY_URBAN_LOOK, masterNotificationId, PERF_VALID_USER_IDS };
		String[] arr18 = { IServiceConstants.ENVIRONMENT_PERF, PORTAL_GROUP_GAP, masterNotificationId, PERF_VALID_USER_IDS };
		String[] arr19 = { IServiceConstants.ENVIRONMENT_PERF, PORTAL_GROUP_PUMA, masterNotificationId, PERF_VALID_USER_IDS };
		String[] arr20 = { IServiceConstants.ENVIRONMENT_PERF, PORTAL_GROUP_LEVIS, masterNotificationId, PERF_VALID_USER_IDS };

		Object[][] dataSet = new Object[][] { arr6, arr7, arr8, arr9, arr10, arr11, arr12, arr13, arr14, arr15, arr16, arr17, arr18, arr19, arr20 };
		return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] NotificationServiceDP_getActiveNotificationsCount_verifySuccessResponse(ITestContext testContext) 
	{
		// PROD Existing user
		String[] arr1 = { IServiceConstants.ENVIRONMENT_PROD, PROD_VALID_USER_ID, PORTAL_GROUP_MYNTRA };
		
		// PROD Non Existing user
		String[] arr2 = { IServiceConstants.ENVIRONMENT_PROD, "fnsdfdfghhfg@gmail.com", PORTAL_GROUP_MYNTRA };
				
		// FOX7 Existing user
		String[] arr3 = { IServiceConstants.ENVIRONMENT_FOX7, FOX7_VALID_USER_ID, PORTAL_GROUP_MYNTRA };
		
		// FOX7 Non Existing user
		String[] arr4 = { IServiceConstants.ENVIRONMENT_FOX7, "fnsdfdfghhfg@gmail.com", PORTAL_GROUP_MYNTRA };
		
		// FUNC Existing user
		String[] arr5 = { IServiceConstants.ENVIRONMENT_FUNC, FUNC_VALID_USER_ID, PORTAL_GROUP_MYNTRA };

		// FUNC Non Existing user
		String[] arr6 = { IServiceConstants.ENVIRONMENT_FUNC, "fnsdfdfghhfg@gmail.com", PORTAL_GROUP_MYNTRA };
		
		// PERF Existing user
		String[] arr7 = { IServiceConstants.ENVIRONMENT_PERF, PERF_VALID_USER_ID, PORTAL_GROUP_MYNTRA };
		
		// PERF Non Existing user
		String[] arr8 = { IServiceConstants.ENVIRONMENT_PERF, "fnsdfdfghhfg@gmail.com", PORTAL_GROUP_MYNTRA };
						
		Object dataset[][] = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8 };
		return Toolbox.returnReducedDataSet(environment, dataset, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] NotificationServiceDP_getActiveNotificationsCount_verifyNodeValues(ITestContext testContext) 
	{
		// PROD Existing user
		String[] arr1 = { IServiceConstants.ENVIRONMENT_PROD, PROD_VALID_USER_ID, PORTAL_GROUP_MYNTRA };
		
		// PROD Non Existing user
		String[] arr2 = { IServiceConstants.ENVIRONMENT_PROD, "fnsdfdfghhfg@gmail.com", PORTAL_GROUP_MYNTRA };
				
		// FOX7 Existing user
		String[] arr3 = { IServiceConstants.ENVIRONMENT_FOX7, FOX7_VALID_USER_ID, PORTAL_GROUP_MYNTRA };
		
		// FOX7 Non Existing user
		String[] arr4 = { IServiceConstants.ENVIRONMENT_FOX7, "fnsdfdfghhfg@gmail.com", PORTAL_GROUP_MYNTRA };
		
		// FUNC Existing user
		String[] arr5 = { IServiceConstants.ENVIRONMENT_FUNC, FUNC_VALID_USER_ID, PORTAL_GROUP_MYNTRA };

		// FUNC Non Existing user
		String[] arr6 = { IServiceConstants.ENVIRONMENT_FUNC, "fnsdfdfghhfg@gmail.com", PORTAL_GROUP_MYNTRA };
		
		// PERF Existing user
		String[] arr7 = { IServiceConstants.ENVIRONMENT_PERF, PERF_VALID_USER_ID, PORTAL_GROUP_MYNTRA };
		
		// PERF Non Existing user
		String[] arr8 = { IServiceConstants.ENVIRONMENT_PERF, "fnsdfdfghhfg@gmail.com", PORTAL_GROUP_MYNTRA };
						
		Object dataset[][] = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8 };
		return Toolbox.returnReducedDataSet(environment, dataset, testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public Object[][] NotificationServiceDP_getActiveNotificationsCount_verifyFailureResponse(ITestContext testContext)
	{
		// valid user with invalid groups
		String[] arr1 = { IServiceConstants.ENVIRONMENT_PROD, PROD_VALID_USER_ID, "fdhfhf" };
		String[] arr2 = { IServiceConstants.ENVIRONMENT_PROD, PROD_VALID_USER_ID, PORTAL_GROUP_MY_URBAN_LOOK };
		String[] arr3 = { IServiceConstants.ENVIRONMENT_PROD, PROD_VALID_USER_ID, PORTAL_GROUP_GAP };
		String[] arr4 = { IServiceConstants.ENVIRONMENT_PROD, PROD_VALID_USER_ID, PORTAL_GROUP_PUMA };
		String[] arr5 = { IServiceConstants.ENVIRONMENT_PROD, PROD_VALID_USER_ID, PORTAL_GROUP_LEVIS };
		
		String[] arr6 = { IServiceConstants.ENVIRONMENT_FOX7, FOX7_VALID_USER_ID, "fdhfhf" };
		String[] arr7 = { IServiceConstants.ENVIRONMENT_FOX7, FOX7_VALID_USER_ID, PORTAL_GROUP_MY_URBAN_LOOK };
		String[] arr8 = { IServiceConstants.ENVIRONMENT_FOX7, FOX7_VALID_USER_ID, PORTAL_GROUP_GAP };
		String[] arr9 = { IServiceConstants.ENVIRONMENT_FOX7, FOX7_VALID_USER_ID, PORTAL_GROUP_PUMA };
		String[] arr10 = { IServiceConstants.ENVIRONMENT_FOX7, FOX7_VALID_USER_ID, PORTAL_GROUP_LEVIS };
		
		String[] arr11 = { IServiceConstants.ENVIRONMENT_FUNC, FUNC_VALID_USER_ID, "fdhfhf" };
		String[] arr12 = { IServiceConstants.ENVIRONMENT_FUNC, FUNC_VALID_USER_ID, PORTAL_GROUP_MY_URBAN_LOOK };
		String[] arr13 = { IServiceConstants.ENVIRONMENT_FUNC, FUNC_VALID_USER_ID, PORTAL_GROUP_GAP };
		String[] arr14 = { IServiceConstants.ENVIRONMENT_FUNC, FUNC_VALID_USER_ID, PORTAL_GROUP_PUMA };
		String[] arr15 = { IServiceConstants.ENVIRONMENT_FUNC, FUNC_VALID_USER_ID, PORTAL_GROUP_LEVIS };
		
		String[] arr16 = { IServiceConstants.ENVIRONMENT_PERF, PERF_VALID_USER_ID, "fdhfhf" };
		String[] arr17 = { IServiceConstants.ENVIRONMENT_PERF, PERF_VALID_USER_ID, PORTAL_GROUP_MY_URBAN_LOOK };
		String[] arr18 = { IServiceConstants.ENVIRONMENT_PERF, PERF_VALID_USER_ID, PORTAL_GROUP_GAP };
		String[] arr19 = { IServiceConstants.ENVIRONMENT_PERF, PERF_VALID_USER_ID, PORTAL_GROUP_PUMA };
		String[] arr20 = { IServiceConstants.ENVIRONMENT_PERF, PERF_VALID_USER_ID, PORTAL_GROUP_LEVIS };
		
		Object dataSet[][] = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9, arr10, 
				arr11, arr12, arr13, arr14, arr15, arr16, arr17, arr18, arr19, arr20 };
		return Toolbox.returnReducedDataSet(environment, dataSet,testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] NotificationServiceDP_getActiveNotifications_verifySuccessResponse(ITestContext testContext) 
	{
		// PROD Existing user
		String[] arr1 = { IServiceConstants.ENVIRONMENT_PROD, PROD_VALID_USER_ID, PORTAL_GROUP_MYNTRA };
		
		// PROD Non Existing user
		String[] arr2 = { IServiceConstants.ENVIRONMENT_PROD, "fnsdfdfghhfg@gmail.com", PORTAL_GROUP_MYNTRA };
				
		// FOX7 Existing user
		String[] arr3 = { IServiceConstants.ENVIRONMENT_FOX7, FOX7_VALID_USER_ID, PORTAL_GROUP_MYNTRA };
		
		// FOX7 Non Existing user
		String[] arr4 = { IServiceConstants.ENVIRONMENT_FOX7, "fnsdfdfghhfg@gmail.com", PORTAL_GROUP_MYNTRA };
		
		// FUNC Existing user
		String[] arr5 = { IServiceConstants.ENVIRONMENT_FUNC, FUNC_VALID_USER_ID, PORTAL_GROUP_MYNTRA };

		// FUNC Non Existing user
		String[] arr6 = { IServiceConstants.ENVIRONMENT_FUNC, "fnsdfdfghhfg@gmail.com", PORTAL_GROUP_MYNTRA };
		
		// PERF Existing user
		String[] arr7 = { IServiceConstants.ENVIRONMENT_PERF, PERF_VALID_USER_ID, PORTAL_GROUP_MYNTRA };
		
		// PERF Non Existing user
		String[] arr8 = { IServiceConstants.ENVIRONMENT_PERF, "fnsdfdfghhfg@gmail.com", PORTAL_GROUP_MYNTRA };
				
		Object dataSet[][] = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8 };
		return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public Object[][] NotificationServiceDP_getActiveNotifications_verifyNodeValues(ITestContext testContext) 
	{
		/*// PROD Existing user
		String[] arr1 = { ENVIRONMENT_PROD, PROD_VALID_USER_ID, PORTAL_GROUP_MYNTRA };
		
		// PROD Non Existing user
		String[] arr2 = { ENVIRONMENT_PROD, "fnsdfdfghhfg@gmail.com", PORTAL_GROUP_MYNTRA };*/
				
		// FOX7 Existing user
		String[] arr3 = { IServiceConstants.ENVIRONMENT_FOX7, FOX7_VALID_USER_ID, PORTAL_GROUP_MYNTRA };
		
		// FOX7 Non Existing user
		String[] arr4 = { IServiceConstants.ENVIRONMENT_FOX7, "fnsdfdfghhfg@gmail.com", PORTAL_GROUP_MYNTRA };
		
		// FUNC Existing user
		String[] arr5 = { IServiceConstants.ENVIRONMENT_FUNC, FUNC_VALID_USER_ID, PORTAL_GROUP_MYNTRA };

		// FUNC Non Existing user
		String[] arr6 = { IServiceConstants.ENVIRONMENT_FUNC, "fnsdfdfghhfg@gmail.com", PORTAL_GROUP_MYNTRA };
		
		// PERF Existing user
		String[] arr7 = { IServiceConstants.ENVIRONMENT_PERF, PERF_VALID_USER_ID, PORTAL_GROUP_MYNTRA };
		
		// PERF Non Existing user
		String[] arr8 = { IServiceConstants.ENVIRONMENT_PERF, "fnsdfdfghhfg@gmail.com", PORTAL_GROUP_MYNTRA };
				
		Object dataSet[][] = new Object[][] { arr3, arr4, arr5, arr6, arr7, arr8 };
		return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public Object[][] NotificationServiceDP_getActiveNotifications_verifyFailureResponse(ITestContext testContext)
	{
		String[] arr1 = { IServiceConstants.ENVIRONMENT_PROD, PROD_VALID_USER_ID, "fdhfhf" };
		String[] arr2 = { IServiceConstants.ENVIRONMENT_PROD, PROD_VALID_USER_ID, PORTAL_GROUP_MY_URBAN_LOOK };
		String[] arr3 = { IServiceConstants.ENVIRONMENT_PROD, PROD_VALID_USER_ID, PORTAL_GROUP_GAP };
		String[] arr4 = { IServiceConstants.ENVIRONMENT_PROD, PROD_VALID_USER_ID, PORTAL_GROUP_PUMA };
		String[] arr5 = { IServiceConstants.ENVIRONMENT_PROD, PROD_VALID_USER_ID, PORTAL_GROUP_LEVIS };
		
		String[] arr6 = { IServiceConstants.ENVIRONMENT_FOX7, FOX7_VALID_USER_ID, "fdhfhf" };
		String[] arr7 = { IServiceConstants.ENVIRONMENT_FOX7, FOX7_VALID_USER_ID, PORTAL_GROUP_MY_URBAN_LOOK };
		String[] arr8 = { IServiceConstants.ENVIRONMENT_FOX7, FOX7_VALID_USER_ID, PORTAL_GROUP_GAP };
		String[] arr9 = { IServiceConstants.ENVIRONMENT_FOX7, FOX7_VALID_USER_ID, PORTAL_GROUP_PUMA };
		String[] arr10 = { IServiceConstants.ENVIRONMENT_FOX7, FOX7_VALID_USER_ID, PORTAL_GROUP_LEVIS };
		
		String[] arr11 = { IServiceConstants.ENVIRONMENT_FUNC, FUNC_VALID_USER_ID, "fdhfhf" };
		String[] arr12 = { IServiceConstants.ENVIRONMENT_FUNC, FUNC_VALID_USER_ID, PORTAL_GROUP_MY_URBAN_LOOK };
		String[] arr13 = { IServiceConstants.ENVIRONMENT_FUNC, FUNC_VALID_USER_ID, PORTAL_GROUP_GAP };
		String[] arr14 = { IServiceConstants.ENVIRONMENT_FUNC, FUNC_VALID_USER_ID, PORTAL_GROUP_PUMA };
		String[] arr15 = { IServiceConstants.ENVIRONMENT_FUNC, FUNC_VALID_USER_ID, PORTAL_GROUP_LEVIS };
		
		String[] arr16 = { IServiceConstants.ENVIRONMENT_PERF, PERF_VALID_USER_ID, "fdhfhf" };
		String[] arr17 = { IServiceConstants.ENVIRONMENT_PERF, PERF_VALID_USER_ID, PORTAL_GROUP_MY_URBAN_LOOK };
		String[] arr18 = { IServiceConstants.ENVIRONMENT_PERF, PERF_VALID_USER_ID, PORTAL_GROUP_GAP };
		String[] arr19 = { IServiceConstants.ENVIRONMENT_PERF, PERF_VALID_USER_ID, PORTAL_GROUP_PUMA };
		String[] arr20 = { IServiceConstants.ENVIRONMENT_PERF, PERF_VALID_USER_ID, PORTAL_GROUP_LEVIS };
		
		Object dataSet[][] = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9, arr10, arr11, arr12, arr13, arr14, arr15, arr16, arr17, 
				arr18, arr19, arr20 };
		return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] NotificationServiceDP_markAsReadNotification_verifySuccessResponse(ITestContext testContext) 
	{
		/*// PROD Existing user
		String[] arr1 = { ENVIRONMENT_PROD, PROD_VALID_USER_ID, PORTAL_GROUP_MYNTRA };
		
		// PROD Non Existing user
		String[] arr2 = { ENVIRONMENT_PROD, "fnsdfdfghhfg@gmail.com", PORTAL_GROUP_MYNTRA };*/
				
		// FOX7 Existing user
		String[] arr3 = { IServiceConstants.ENVIRONMENT_FOX7, FOX7_VALID_USER_ID, PORTAL_GROUP_MYNTRA };
		
		// FOX7 Non Existing user
		
		//String[] arr4 = { ENVIRONMENT_FOX7, "fnsdfdfghhfg@gmail.com", PORTAL_GROUP_MYNTRA };
		
		// FUNC Existing user
		String[] arr5 = { IServiceConstants.ENVIRONMENT_FUNC, FUNC_VALID_USER_ID, PORTAL_GROUP_MYNTRA };

		// FUNC Non Existing user
		String[] arr6 = { IServiceConstants.ENVIRONMENT_FUNC, "fnsdfdfghhfg@gmail.com", PORTAL_GROUP_MYNTRA };
		
		// PERF Existing user
		String[] arr7 = { IServiceConstants.ENVIRONMENT_PERF, PERF_VALID_USER_ID, PORTAL_GROUP_MYNTRA };
		
		// PERF Non Existing user
		String[] arr8 = { IServiceConstants.ENVIRONMENT_PERF, "fnsdfdfghhfg@gmail.com", PORTAL_GROUP_MYNTRA };
				
		Object dataSet[][] = new Object[][] { arr3, /*arr4,*/ arr5, arr6, arr7, arr8 };
		return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public Object[][] NotificationServiceDP_markAsReadNotification_verifyNodeValues(ITestContext testContext) 
	{
		/*// PROD Existing user
		String[] arr1 = { ENVIRONMENT_PROD, PROD_VALID_USER_ID, PORTAL_GROUP_MYNTRA };
		
		// PROD Non Existing user
		String[] arr2 = { ENVIRONMENT_PROD, "fnsdfdfghhfg@gmail.com", PORTAL_GROUP_MYNTRA };*/
				
		// FOX7 Existing user
		String[] arr3 = { IServiceConstants.ENVIRONMENT_FOX7, FOX7_VALID_USER_ID, PORTAL_GROUP_MYNTRA };
		
		// FOX7 Non Existing user
		String[] arr4 = { IServiceConstants.ENVIRONMENT_FOX7, "fnsdfdfghhfg@gmail.com", PORTAL_GROUP_MYNTRA };
		
		// FUNC Existing user
		String[] arr5 = { IServiceConstants.ENVIRONMENT_FUNC, FUNC_VALID_USER_ID, PORTAL_GROUP_MYNTRA };

		// FUNC Non Existing user
		String[] arr6 = { IServiceConstants.ENVIRONMENT_FUNC, "fnsdfdfghhfg@gmail.com", PORTAL_GROUP_MYNTRA };
		
		// PERF Existing user
		String[] arr7 = { IServiceConstants.ENVIRONMENT_PERF, PERF_VALID_USER_ID, PORTAL_GROUP_MYNTRA };
		
		// PERF Non Existing user
		String[] arr8 = { IServiceConstants.ENVIRONMENT_PERF, "fnsdfdfghhfg@gmail.com", PORTAL_GROUP_MYNTRA };
				
		Object dataSet[][] = new Object[][] { arr3, arr4, arr5, arr6, arr7, arr8 };
		return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public Object[][] NotificationServiceDP_markAsReadNotification_verifyFailureResponse(ITestContext testContext)
	{
		String masterNotificationId = String.valueOf(new UUID());
		String publishTime = String.valueOf(Calendar.getInstance().getTimeInMillis());
		
		/*String[] arr1 = { ENVIRONMENT_PROD, PROD_VALID_USER_ID, "fdhfhf", publishTime,	masterNotificationId }; 
		String[] arr2 = { ENVIRONMENT_PROD, PROD_VALID_USER_ID, PORTAL_GROUP_MY_URBAN_LOOK, publishTime, masterNotificationId }; 
		String[] arr3 = { ENVIRONMENT_PROD, PROD_VALID_USER_ID, PORTAL_GROUP_GAP, publishTime, masterNotificationId }; 
		String[] arr4 = { ENVIRONMENT_PROD, PROD_VALID_USER_ID, PORTAL_GROUP_PUMA, publishTime, masterNotificationId }; 
		String[] arr5 = { ENVIRONMENT_PROD, PROD_VALID_USER_ID, PORTAL_GROUP_LEVIS, publishTime, masterNotificationId };*/
		
		String[] arr6 = { IServiceConstants.ENVIRONMENT_FOX7, FOX7_VALID_USER_ID, "fdhfhf",  publishTime, masterNotificationId };
		String[] arr7 = { IServiceConstants.ENVIRONMENT_FOX7, FOX7_VALID_USER_ID, PORTAL_GROUP_MY_URBAN_LOOK,  publishTime, masterNotificationId };
		String[] arr8 = { IServiceConstants.ENVIRONMENT_FOX7, FOX7_VALID_USER_ID, PORTAL_GROUP_GAP, publishTime, masterNotificationId };
		String[] arr9 = { IServiceConstants.ENVIRONMENT_FOX7, FOX7_VALID_USER_ID, PORTAL_GROUP_PUMA, publishTime, masterNotificationId };
		String[] arr10 = { IServiceConstants.ENVIRONMENT_FOX7, FOX7_VALID_USER_ID, PORTAL_GROUP_LEVIS, publishTime, masterNotificationId };
		
		String[] arr11 = { IServiceConstants.ENVIRONMENT_FUNC, FUNC_VALID_USER_ID, "fdhfhf", publishTime, masterNotificationId };
		String[] arr12 = { IServiceConstants.ENVIRONMENT_FUNC, FUNC_VALID_USER_ID, PORTAL_GROUP_MY_URBAN_LOOK, publishTime, masterNotificationId };
		String[] arr13 = { IServiceConstants.ENVIRONMENT_FUNC, FUNC_VALID_USER_ID, PORTAL_GROUP_GAP, publishTime, masterNotificationId };
		String[] arr14 = { IServiceConstants.ENVIRONMENT_FUNC, FUNC_VALID_USER_ID, PORTAL_GROUP_PUMA, publishTime, masterNotificationId };
		String[] arr15 = { IServiceConstants.ENVIRONMENT_FUNC, FUNC_VALID_USER_ID, PORTAL_GROUP_LEVIS, publishTime, masterNotificationId };
		
		String[] arr16 = { IServiceConstants.ENVIRONMENT_PERF, PERF_VALID_USER_ID, "fdhfhf", publishTime, masterNotificationId };
		String[] arr17 = { IServiceConstants.ENVIRONMENT_PERF, PERF_VALID_USER_ID, PORTAL_GROUP_MY_URBAN_LOOK, publishTime, masterNotificationId };
		String[] arr18 = { IServiceConstants.ENVIRONMENT_PERF, PERF_VALID_USER_ID, PORTAL_GROUP_GAP, publishTime, masterNotificationId };
		String[] arr19 = { IServiceConstants.ENVIRONMENT_PERF, PERF_VALID_USER_ID, PORTAL_GROUP_PUMA, publishTime, masterNotificationId };
		String[] arr20 = { IServiceConstants.ENVIRONMENT_PERF, PERF_VALID_USER_ID, PORTAL_GROUP_LEVIS, publishTime, masterNotificationId };
		
		Object dataSet[][] = new Object[][] { arr6, arr7, arr8, arr9, arr10, arr11, arr12, arr13, arr14, arr15, arr16, arr17, arr18, arr19, arr20 };
		return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] NotificationServiceDP_createNotificationForUser_verifySuccessResponse(ITestContext testContext)
	{
		// notification valid in hours
		Calendar startTimeCal = Calendar.getInstance();
		//startTimeCal.add(Calendar.HOUR, 2);
		String startTime = String.valueOf(startTimeCal.getTimeInMillis());
		
		Calendar endTimeCal = Calendar.getInstance();
		endTimeCal.add(Calendar.HOUR, 4);
		String endTime = String.valueOf(endTimeCal.getTimeInMillis());
		
		/*String[] arr1 = { ENVIRONMENT_PROD, PROD_VALID_USER_ID, PORTAL_GROUP_MYNTRA, TITAN_PROD_NOTIFICATION_IMG_URL, TITAN_PROD_NOTIFICATION_PAGE_URL, startTime, endTime,
				TITAN_PROD_NOTIFICATION_TEXT, TITAN_PROD_NOTIFICATION_TITLE, String.valueOf(new UUID()), NOTIFICATION_TYPE_MARKETING };
		String[] arr2 = { ENVIRONMENT_PROD, PROD_VALID_USER_ID, PORTAL_GROUP_MYNTRA, FOSSIL_PROD_NOTIFICATION_IMG_URL, FOSSIL_PROD_NOTIFICATION_PAGE_URL, startTime, endTime,
				FOSSIL_PROD_NOTIFICATION_TEXT, FOSSIL_PROD_NOTIFICATION_TITLE, String.valueOf(new UUID()), NOTIFICATION_TYPE_ORDER_STATUS_CHANGE };
		String[] arr3 = { ENVIRONMENT_PROD, PROD_VALID_USER_ID, PORTAL_GROUP_MYNTRA, TITAN_PROD_NOTIFICATION_IMG_URL, TITAN_PROD_NOTIFICATION_PAGE_URL, startTime, endTime,
				TITAN_PROD_NOTIFICATION_TEXT, TITAN_PROD_NOTIFICATION_TITLE, String.valueOf(new UUID()), NOTIFICATION_TYPE_PRICE_CHANGE };
		String[] arr4 = { ENVIRONMENT_PROD, PROD_VALID_USER_ID, PORTAL_GROUP_MYNTRA, FOSSIL_PROD_NOTIFICATION_IMG_URL, FOSSIL_PROD_NOTIFICATION_PAGE_URL, startTime, endTime,
				FOSSIL_PROD_NOTIFICATION_TEXT, FOSSIL_PROD_NOTIFICATION_TITLE, String.valueOf(new UUID()), NOTIFICATION_TYPE_REVENUE_LABS };*/
		
		String[] arr5 = { IServiceConstants.ENVIRONMENT_FOX7, FOX7_VALID_USER_ID, PORTAL_GROUP_MYNTRA, TITAN_PROD_NOTIFICATION_IMG_URL, TITAN_PROD_NOTIFICATION_PAGE_URL, startTime, endTime,
				TITAN_PROD_NOTIFICATION_TEXT, TITAN_PROD_NOTIFICATION_TITLE, String.valueOf(new UUID()), NOTIFICATION_TYPE_MARKETING };
		String[] arr6 = { IServiceConstants.ENVIRONMENT_FOX7, FOX7_VALID_USER_ID, PORTAL_GROUP_MYNTRA, FOSSIL_PROD_NOTIFICATION_IMG_URL, FOSSIL_PROD_NOTIFICATION_PAGE_URL, startTime, endTime,
				FOSSIL_PROD_NOTIFICATION_TEXT, FOSSIL_PROD_NOTIFICATION_TITLE, String.valueOf(new UUID()), NOTIFICATION_TYPE_ORDER_STATUS_CHANGE };
		String[] arr7 = { IServiceConstants.ENVIRONMENT_FOX7, FOX7_VALID_USER_ID, PORTAL_GROUP_MYNTRA, TITAN_PROD_NOTIFICATION_IMG_URL, TITAN_PROD_NOTIFICATION_PAGE_URL, startTime, endTime,
				TITAN_PROD_NOTIFICATION_TEXT, TITAN_PROD_NOTIFICATION_TITLE, String.valueOf(new UUID()), NOTIFICATION_TYPE_PRICE_CHANGE };
		String[] arr8 = { IServiceConstants.ENVIRONMENT_FOX7, FOX7_VALID_USER_ID, PORTAL_GROUP_MYNTRA, FOSSIL_PROD_NOTIFICATION_IMG_URL, FOSSIL_PROD_NOTIFICATION_PAGE_URL, startTime, endTime,
				FOSSIL_PROD_NOTIFICATION_TEXT, FOSSIL_PROD_NOTIFICATION_TITLE, String.valueOf(new UUID()), NOTIFICATION_TYPE_REVENUE_LABS };
		
		String[] arr9 = { IServiceConstants.ENVIRONMENT_FUNC, FUNC_VALID_USER_ID, PORTAL_GROUP_MYNTRA, TITAN_PROD_NOTIFICATION_IMG_URL, TITAN_PROD_NOTIFICATION_PAGE_URL, startTime, endTime,
				TITAN_PROD_NOTIFICATION_TEXT, TITAN_PROD_NOTIFICATION_TITLE, String.valueOf(new UUID()), NOTIFICATION_TYPE_MARKETING };
		String[] arr10 = { IServiceConstants.ENVIRONMENT_FUNC, FUNC_VALID_USER_ID, PORTAL_GROUP_MYNTRA, FOSSIL_PROD_NOTIFICATION_IMG_URL, FOSSIL_PROD_NOTIFICATION_PAGE_URL, startTime, endTime,
				FOSSIL_PROD_NOTIFICATION_TEXT, FOSSIL_PROD_NOTIFICATION_TITLE, String.valueOf(new UUID()), NOTIFICATION_TYPE_ORDER_STATUS_CHANGE };
		String[] arr11 = { IServiceConstants.ENVIRONMENT_FUNC, FUNC_VALID_USER_ID, PORTAL_GROUP_MYNTRA, TITAN_PROD_NOTIFICATION_IMG_URL, TITAN_PROD_NOTIFICATION_PAGE_URL, startTime, endTime,
				TITAN_PROD_NOTIFICATION_TEXT, TITAN_PROD_NOTIFICATION_TITLE, String.valueOf(new UUID()), NOTIFICATION_TYPE_PRICE_CHANGE };
		String[] arr12 = { IServiceConstants.ENVIRONMENT_FUNC, FUNC_VALID_USER_ID, PORTAL_GROUP_MYNTRA, FOSSIL_PROD_NOTIFICATION_IMG_URL, FOSSIL_PROD_NOTIFICATION_PAGE_URL, startTime, endTime,
				FOSSIL_PROD_NOTIFICATION_TEXT, FOSSIL_PROD_NOTIFICATION_TITLE, String.valueOf(new UUID()), NOTIFICATION_TYPE_REVENUE_LABS };
		
		String[] arr13 = { IServiceConstants.ENVIRONMENT_PERF, PERF_VALID_USER_ID, PORTAL_GROUP_MYNTRA, TITAN_PROD_NOTIFICATION_IMG_URL, TITAN_PROD_NOTIFICATION_PAGE_URL, startTime, endTime,
				TITAN_PROD_NOTIFICATION_TEXT, TITAN_PROD_NOTIFICATION_TITLE, String.valueOf(new UUID()), NOTIFICATION_TYPE_MARKETING };
		String[] arr14 = { IServiceConstants.ENVIRONMENT_PERF, PERF_VALID_USER_ID, PORTAL_GROUP_MYNTRA, FOSSIL_PROD_NOTIFICATION_IMG_URL, FOSSIL_PROD_NOTIFICATION_PAGE_URL, startTime, endTime,
				FOSSIL_PROD_NOTIFICATION_TEXT, FOSSIL_PROD_NOTIFICATION_TITLE, String.valueOf(new UUID()), NOTIFICATION_TYPE_ORDER_STATUS_CHANGE };
		String[] arr15 = { IServiceConstants.ENVIRONMENT_PERF, PERF_VALID_USER_ID, PORTAL_GROUP_MYNTRA, TITAN_PROD_NOTIFICATION_IMG_URL, TITAN_PROD_NOTIFICATION_PAGE_URL, startTime, endTime,
				TITAN_PROD_NOTIFICATION_TEXT, TITAN_PROD_NOTIFICATION_TITLE, String.valueOf(new UUID()), NOTIFICATION_TYPE_PRICE_CHANGE };
		String[] arr16 = { IServiceConstants.ENVIRONMENT_PERF, PERF_VALID_USER_ID, PORTAL_GROUP_MYNTRA, FOSSIL_PROD_NOTIFICATION_IMG_URL, FOSSIL_PROD_NOTIFICATION_PAGE_URL, startTime, endTime,
				FOSSIL_PROD_NOTIFICATION_TEXT, FOSSIL_PROD_NOTIFICATION_TITLE, String.valueOf(new UUID()), NOTIFICATION_TYPE_REVENUE_LABS };
		
		Object dataSet[][] = new Object[][] { arr5, arr6, arr7, arr8, arr9, arr10, arr11, arr12, arr13, arr14, arr15, arr16 };
		return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] NotificationServiceDP_createNotificationForUser_verifyFailureResponse(ITestContext testContext)
	{
		// notification valid in hours
		Calendar startTimeCal = Calendar.getInstance();
		startTimeCal.add(Calendar.HOUR, 2);
		String startTime = String.valueOf(startTimeCal.getTimeInMillis());
		
		Calendar endTimeCal = Calendar.getInstance();
		endTimeCal.add(Calendar.HOUR, 4);
		String endTime = String.valueOf(endTimeCal.getTimeInMillis());
		
		/*// portalGroup is gap
		String[] arr1 = { ENVIRONMENT_PROD, PROD_VALID_USER_ID, PORTAL_GROUP_GAP, PUMA_PROD_NOTIFICATION_IMG_URL, PUMA_PROD_NOTIFICATION_PAGE_URL, startTime,
				endTime, PUMA_PROD_NOTIFICATION_TEXT, PUMA_PROD_NOTIFICATION_TITLE, String.valueOf(new UUID()), NOTIFICATION_TYPE_MARKETING };

		// image url as blank
		String[] arr2 = { ENVIRONMENT_PROD, PROD_VALID_USER_ID, PORTAL_GROUP_MYNTRA, "", PUMA_PROD_NOTIFICATION_PAGE_URL, startTime, endTime, 
				PUMA_PROD_NOTIFICATION_TEXT, PUMA_PROD_NOTIFICATION_TITLE, String.valueOf(new UUID()), NOTIFICATION_TYPE_MARKETING };
		
		// page url as blank
		String[] arr3 = { ENVIRONMENT_PROD, PROD_VALID_USER_ID, PORTAL_GROUP_MYNTRA, PUMA_PROD_NOTIFICATION_IMG_URL, "", startTime, endTime, 
				PUMA_PROD_NOTIFICATION_TEXT, PUMA_PROD_NOTIFICATION_TITLE, String.valueOf(new UUID()), NOTIFICATION_TYPE_MARKETING };

		// startTime as blank
		String[] arr4 = { ENVIRONMENT_PROD, PROD_VALID_USER_ID, PORTAL_GROUP_MYNTRA, PUMA_PROD_NOTIFICATION_IMG_URL, PUMA_PROD_NOTIFICATION_PAGE_URL, "", 
				endTime, PUMA_PROD_NOTIFICATION_TEXT, PUMA_PROD_NOTIFICATION_TITLE, String.valueOf(new UUID()), NOTIFICATION_TYPE_MARKETING };
				
		// endTime as blank
		String[] arr5 = { ENVIRONMENT_PROD, PROD_VALID_USER_ID, PORTAL_GROUP_MYNTRA, PUMA_PROD_NOTIFICATION_IMG_URL, PUMA_PROD_NOTIFICATION_PAGE_URL, startTime, "",
				PUMA_PROD_NOTIFICATION_TEXT, PUMA_PROD_NOTIFICATION_TITLE, String.valueOf(new UUID()), NOTIFICATION_TYPE_MARKETING };
		
		// notificationText as blank
		String[] arr6 = { ENVIRONMENT_PROD, PROD_VALID_USER_ID, PORTAL_GROUP_MYNTRA, PUMA_PROD_NOTIFICATION_IMG_URL, PUMA_PROD_NOTIFICATION_PAGE_URL, startTime, endTime,
				"", PUMA_PROD_NOTIFICATION_TITLE, String.valueOf(new UUID()), NOTIFICATION_TYPE_MARKETING };

		// notificationTitle as blank
		String[] arr7 = { ENVIRONMENT_PROD, PROD_VALID_USER_ID, PORTAL_GROUP_MYNTRA, PUMA_PROD_NOTIFICATION_IMG_URL, PUMA_PROD_NOTIFICATION_PAGE_URL, startTime, endTime,
				PUMA_PROD_NOTIFICATION_TEXT, "", String.valueOf(new UUID()), NOTIFICATION_TYPE_MARKETING };
		
		// notificationId as blank
		String[] arr8 = { ENVIRONMENT_PROD, PROD_VALID_USER_ID, PORTAL_GROUP_MYNTRA, PUMA_PROD_NOTIFICATION_IMG_URL, PUMA_PROD_NOTIFICATION_PAGE_URL, startTime, endTime,
				PUMA_PROD_NOTIFICATION_TEXT, PUMA_PROD_NOTIFICATION_TITLE, "", NOTIFICATION_TYPE_MARKETING };
		
		// notificationType as blank
		String[] arr9 = { ENVIRONMENT_PROD, PROD_VALID_USER_ID, PORTAL_GROUP_MYNTRA, PUMA_PROD_NOTIFICATION_IMG_URL, PUMA_PROD_NOTIFICATION_PAGE_URL, startTime, endTime,
				PUMA_PROD_NOTIFICATION_TEXT, PUMA_PROD_NOTIFICATION_TITLE, String.valueOf(new UUID()), "" };*/
				
		// portalGroup is gap
		String[] arr10 = { IServiceConstants.ENVIRONMENT_FOX7, FOX7_VALID_USER_ID, PORTAL_GROUP_GAP, PUMA_PROD_NOTIFICATION_IMG_URL, PUMA_PROD_NOTIFICATION_PAGE_URL, startTime,
				endTime, PUMA_PROD_NOTIFICATION_TEXT, PUMA_PROD_NOTIFICATION_TITLE, String.valueOf(new UUID()), NOTIFICATION_TYPE_MARKETING };
		
		// image url as blank
		String[] arr11 = { IServiceConstants.ENVIRONMENT_FOX7, FOX7_VALID_USER_ID, PORTAL_GROUP_MYNTRA, "", PUMA_PROD_NOTIFICATION_PAGE_URL, startTime, endTime,
				PUMA_PROD_NOTIFICATION_TEXT, PUMA_PROD_NOTIFICATION_TITLE, String.valueOf(new UUID()), NOTIFICATION_TYPE_MARKETING };
		
		// page url as blank
		String[] arr12 = { IServiceConstants.ENVIRONMENT_FOX7, FOX7_VALID_USER_ID, PORTAL_GROUP_MYNTRA, PUMA_PROD_NOTIFICATION_IMG_URL, "", startTime, endTime,
				PUMA_PROD_NOTIFICATION_TEXT, PUMA_PROD_NOTIFICATION_TITLE, String.valueOf(new UUID()), NOTIFICATION_TYPE_MARKETING };
		
		// startTime as blank
		String[] arr13 = { IServiceConstants.ENVIRONMENT_FOX7, FOX7_VALID_USER_ID, PORTAL_GROUP_MYNTRA, PUMA_PROD_NOTIFICATION_IMG_URL, PUMA_PROD_NOTIFICATION_PAGE_URL, "",
				endTime, PUMA_PROD_NOTIFICATION_TEXT, PUMA_PROD_NOTIFICATION_TITLE, String.valueOf(new UUID()), NOTIFICATION_TYPE_MARKETING };
		
		// endTime as blank
		String[] arr14 = { IServiceConstants.ENVIRONMENT_FOX7, FOX7_VALID_USER_ID, PORTAL_GROUP_MYNTRA, PUMA_PROD_NOTIFICATION_IMG_URL, PUMA_PROD_NOTIFICATION_PAGE_URL, startTime, "",
				PUMA_PROD_NOTIFICATION_TEXT, PUMA_PROD_NOTIFICATION_TITLE, String.valueOf(new UUID()), NOTIFICATION_TYPE_MARKETING };
		
		// notificationText as blank
		String[] arr15 = { IServiceConstants.ENVIRONMENT_FOX7, FOX7_VALID_USER_ID, PORTAL_GROUP_MYNTRA, PUMA_PROD_NOTIFICATION_IMG_URL, PUMA_PROD_NOTIFICATION_PAGE_URL, startTime, endTime,
				"", PUMA_PROD_NOTIFICATION_TITLE, String.valueOf(new UUID()), NOTIFICATION_TYPE_MARKETING };
		
		// notificationTitle as blank
		String[] arr16 = { IServiceConstants.ENVIRONMENT_FOX7, FOX7_VALID_USER_ID, PORTAL_GROUP_MYNTRA, PUMA_PROD_NOTIFICATION_IMG_URL, PUMA_PROD_NOTIFICATION_PAGE_URL, startTime, endTime,
				PUMA_PROD_NOTIFICATION_TEXT, "", String.valueOf(new UUID()), NOTIFICATION_TYPE_MARKETING };
		
		// notificationId as blank
		String[] arr17 = { IServiceConstants.ENVIRONMENT_FOX7, FOX7_VALID_USER_ID, PORTAL_GROUP_MYNTRA, PUMA_PROD_NOTIFICATION_IMG_URL, PUMA_PROD_NOTIFICATION_PAGE_URL, startTime, endTime,
				PUMA_PROD_NOTIFICATION_TEXT, PUMA_PROD_NOTIFICATION_TITLE, "", NOTIFICATION_TYPE_MARKETING };

		// notificationType as blank
		String[] arr18 = { IServiceConstants.ENVIRONMENT_FOX7, FOX7_VALID_USER_ID, PORTAL_GROUP_MYNTRA, PUMA_PROD_NOTIFICATION_IMG_URL, PUMA_PROD_NOTIFICATION_PAGE_URL, startTime, endTime,
				PUMA_PROD_NOTIFICATION_TEXT, PUMA_PROD_NOTIFICATION_TITLE, String.valueOf(new UUID()), "" };
		
		// portalGroup is gap
		String[] arr19 = { IServiceConstants.ENVIRONMENT_FUNC, FUNC_VALID_USER_ID, PORTAL_GROUP_GAP, PUMA_PROD_NOTIFICATION_IMG_URL, PUMA_PROD_NOTIFICATION_PAGE_URL, startTime,
				endTime, PUMA_PROD_NOTIFICATION_TEXT, PUMA_PROD_NOTIFICATION_TITLE, String.valueOf(new UUID()), NOTIFICATION_TYPE_MARKETING };
		
		// image url as blank
		String[] arr20 = { IServiceConstants.ENVIRONMENT_FUNC, FUNC_VALID_USER_ID, PORTAL_GROUP_MYNTRA, "", PUMA_PROD_NOTIFICATION_PAGE_URL, startTime, endTime,
				PUMA_PROD_NOTIFICATION_TEXT, PUMA_PROD_NOTIFICATION_TITLE, String.valueOf(new UUID()), NOTIFICATION_TYPE_MARKETING };
		
		// page url as blank
		String[] arr21 = { IServiceConstants.ENVIRONMENT_FUNC, FUNC_VALID_USER_ID, PORTAL_GROUP_MYNTRA, PUMA_PROD_NOTIFICATION_IMG_URL, "", startTime, endTime,
				PUMA_PROD_NOTIFICATION_TEXT, PUMA_PROD_NOTIFICATION_TITLE, String.valueOf(new UUID()), NOTIFICATION_TYPE_MARKETING };
		
		// startTime as blank
		String[] arr22 = { IServiceConstants.ENVIRONMENT_FUNC, FUNC_VALID_USER_ID, PORTAL_GROUP_MYNTRA, PUMA_PROD_NOTIFICATION_IMG_URL, PUMA_PROD_NOTIFICATION_PAGE_URL, "",
				endTime, PUMA_PROD_NOTIFICATION_TEXT, PUMA_PROD_NOTIFICATION_TITLE, String.valueOf(new UUID()), NOTIFICATION_TYPE_MARKETING };
		
		// endTime as blank
		String[] arr23 = { IServiceConstants.ENVIRONMENT_FUNC, FUNC_VALID_USER_ID, PORTAL_GROUP_MYNTRA, PUMA_PROD_NOTIFICATION_IMG_URL, PUMA_PROD_NOTIFICATION_PAGE_URL, startTime, "",
				PUMA_PROD_NOTIFICATION_TEXT, PUMA_PROD_NOTIFICATION_TITLE, String.valueOf(new UUID()), NOTIFICATION_TYPE_MARKETING };
		
		// notificationText as blank
		String[] arr24 = { IServiceConstants.ENVIRONMENT_FUNC, FUNC_VALID_USER_ID, PORTAL_GROUP_MYNTRA, PUMA_PROD_NOTIFICATION_IMG_URL, PUMA_PROD_NOTIFICATION_PAGE_URL, startTime, endTime,
				"", PUMA_PROD_NOTIFICATION_TITLE, String.valueOf(new UUID()), NOTIFICATION_TYPE_MARKETING };
		
		// notificationTitle as blank
		String[] arr25 = { IServiceConstants.ENVIRONMENT_FUNC, FUNC_VALID_USER_ID, PORTAL_GROUP_MYNTRA, PUMA_PROD_NOTIFICATION_IMG_URL, PUMA_PROD_NOTIFICATION_PAGE_URL, startTime, endTime,
				PUMA_PROD_NOTIFICATION_TEXT, "", String.valueOf(new UUID()), NOTIFICATION_TYPE_MARKETING };
		
		// notificationId as blank
		String[] arr26 = { IServiceConstants.ENVIRONMENT_FUNC, FUNC_VALID_USER_ID, PORTAL_GROUP_MYNTRA, PUMA_PROD_NOTIFICATION_IMG_URL, PUMA_PROD_NOTIFICATION_PAGE_URL, startTime, endTime,
				PUMA_PROD_NOTIFICATION_TEXT, PUMA_PROD_NOTIFICATION_TITLE, "", NOTIFICATION_TYPE_MARKETING };
		
		// notificationType as blank
		String[] arr27 = { IServiceConstants.ENVIRONMENT_FUNC, FUNC_VALID_USER_ID, PORTAL_GROUP_MYNTRA, PUMA_PROD_NOTIFICATION_IMG_URL, PUMA_PROD_NOTIFICATION_PAGE_URL, startTime, endTime,
				PUMA_PROD_NOTIFICATION_TEXT, PUMA_PROD_NOTIFICATION_TITLE, String.valueOf(new UUID()), "" };
		
		// portalGroup is gap
		String[] arr28 = { IServiceConstants.ENVIRONMENT_PERF, PERF_VALID_USER_ID, PORTAL_GROUP_GAP, PUMA_PROD_NOTIFICATION_IMG_URL, PUMA_PROD_NOTIFICATION_PAGE_URL, startTime,
				endTime, PUMA_PROD_NOTIFICATION_TEXT, PUMA_PROD_NOTIFICATION_TITLE, String.valueOf(new UUID()), NOTIFICATION_TYPE_MARKETING };
		
		// image url as blank
		String[] arr29 = { IServiceConstants.ENVIRONMENT_PERF, PERF_VALID_USER_ID, PORTAL_GROUP_MYNTRA, "", PUMA_PROD_NOTIFICATION_PAGE_URL, startTime, endTime,
				PUMA_PROD_NOTIFICATION_TEXT, PUMA_PROD_NOTIFICATION_TITLE, String.valueOf(new UUID()), NOTIFICATION_TYPE_MARKETING };
		
		// page url as blank
		String[] arr30 = { IServiceConstants.ENVIRONMENT_PERF, PERF_VALID_USER_ID, PORTAL_GROUP_MYNTRA, PUMA_PROD_NOTIFICATION_IMG_URL, "", startTime, endTime,
				PUMA_PROD_NOTIFICATION_TEXT, PUMA_PROD_NOTIFICATION_TITLE, String.valueOf(new UUID()), NOTIFICATION_TYPE_MARKETING };
		
		// startTime as blank
		String[] arr31 = { IServiceConstants.ENVIRONMENT_PERF, PERF_VALID_USER_ID, PORTAL_GROUP_MYNTRA, PUMA_PROD_NOTIFICATION_IMG_URL, PUMA_PROD_NOTIFICATION_PAGE_URL, "",
				endTime, PUMA_PROD_NOTIFICATION_TEXT, PUMA_PROD_NOTIFICATION_TITLE, String.valueOf(new UUID()), NOTIFICATION_TYPE_MARKETING };
		
		// endTime as blank
		String[] arr32 = { IServiceConstants.ENVIRONMENT_PERF, PERF_VALID_USER_ID, PORTAL_GROUP_MYNTRA, PUMA_PROD_NOTIFICATION_IMG_URL, PUMA_PROD_NOTIFICATION_PAGE_URL, startTime, "",
				PUMA_PROD_NOTIFICATION_TEXT, PUMA_PROD_NOTIFICATION_TITLE, String.valueOf(new UUID()), NOTIFICATION_TYPE_MARKETING };
		
		// notificationText as blank
		String[] arr33 = { IServiceConstants.ENVIRONMENT_PERF, PERF_VALID_USER_ID, PORTAL_GROUP_MYNTRA, PUMA_PROD_NOTIFICATION_IMG_URL, PUMA_PROD_NOTIFICATION_PAGE_URL, startTime, endTime,
				"", PUMA_PROD_NOTIFICATION_TITLE, String.valueOf(new UUID()), NOTIFICATION_TYPE_MARKETING };
		
		// notificationTitle as blank
		String[] arr34 = { IServiceConstants.ENVIRONMENT_PERF, PERF_VALID_USER_ID, PORTAL_GROUP_MYNTRA, PUMA_PROD_NOTIFICATION_IMG_URL, PUMA_PROD_NOTIFICATION_PAGE_URL, startTime, endTime,
				PUMA_PROD_NOTIFICATION_TEXT, "", String.valueOf(new UUID()), NOTIFICATION_TYPE_MARKETING };
		
		// notificationId as blank
		String[] arr35 = { IServiceConstants.ENVIRONMENT_PERF, PERF_VALID_USER_ID, PORTAL_GROUP_MYNTRA, PUMA_PROD_NOTIFICATION_IMG_URL, PUMA_PROD_NOTIFICATION_PAGE_URL, startTime, endTime,
				PUMA_PROD_NOTIFICATION_TEXT, PUMA_PROD_NOTIFICATION_TITLE, "", NOTIFICATION_TYPE_MARKETING };
		
		// notificationType as blank
		String[] arr36 = { IServiceConstants.ENVIRONMENT_PERF, PERF_VALID_USER_ID, PORTAL_GROUP_MYNTRA, PUMA_PROD_NOTIFICATION_IMG_URL, PUMA_PROD_NOTIFICATION_PAGE_URL, startTime, endTime,
				PUMA_PROD_NOTIFICATION_TEXT, PUMA_PROD_NOTIFICATION_TITLE, String.valueOf(new UUID()), "" };
		
		Object dataSet[][] = new Object[][] { arr10, arr11, arr12, arr13, arr14, arr15, arr16, arr17, arr18, arr19, arr20, arr21, arr22, arr23, arr24, arr25, arr26, 
				arr27, arr28, arr29, arr30, arr31, arr32, arr33, arr34, arr35, arr36 };
		return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] NotificationServiceDP_createNotificationForUser_verifyNodeValues(ITestContext testContext)
	{
		// notification valid in hours
		Calendar startTimeCal = Calendar.getInstance();
		//startTimeCal.add(Calendar.HOUR, 2);
		String startTime = String.valueOf(startTimeCal.getTimeInMillis());
		
		Calendar endTimeCal = Calendar.getInstance();
		endTimeCal.add(Calendar.HOUR, 4);
		String endTime = String.valueOf(endTimeCal.getTimeInMillis());
		
		/*String[] arr1 = { ENVIRONMENT_PROD, PROD_VALID_USER_ID, PORTAL_GROUP_MYNTRA, PUMA_PROD_NOTIFICATION_IMG_URL, PUMA_PROD_NOTIFICATION_PAGE_URL, startTime, endTime,
				PUMA_PROD_NOTIFICATION_TEXT, PUMA_PROD_NOTIFICATION_TITLE, String.valueOf(new UUID()), NOTIFICATION_TYPE_MARKETING };
		String[] arr2 = { ENVIRONMENT_PROD, PROD_VALID_USER_ID, PORTAL_GROUP_MYNTRA, ADIDAS_PROD_NOTIFICATION_IMG_URL, ADIDAS_PROD_NOTIFICATION_PAGE_URL, startTime, endTime,
				ADIDAS_PROD_NOTIFICATION_TEXT, ADIDAS_PROD_NOTIFICATION_TITLE, String.valueOf(new UUID()), NOTIFICATION_TYPE_ORDER_STATUS_CHANGE };
		String[] arr3 = { ENVIRONMENT_PROD, PROD_VALID_USER_ID, PORTAL_GROUP_MYNTRA, PUMA_PROD_NOTIFICATION_IMG_URL, PUMA_PROD_NOTIFICATION_PAGE_URL, startTime, endTime,
				PUMA_PROD_NOTIFICATION_TEXT, PUMA_PROD_NOTIFICATION_TITLE, String.valueOf(new UUID()), NOTIFICATION_TYPE_PRICE_CHANGE };
		String[] arr4 = { ENVIRONMENT_PROD, PROD_VALID_USER_ID, PORTAL_GROUP_MYNTRA, ADIDAS_PROD_NOTIFICATION_IMG_URL, ADIDAS_PROD_NOTIFICATION_PAGE_URL, startTime, endTime,
				ADIDAS_PROD_NOTIFICATION_TEXT, ADIDAS_PROD_NOTIFICATION_TITLE, String.valueOf(new UUID()), NOTIFICATION_TYPE_REVENUE_LABS };*/
		
		String[] arr5 = { IServiceConstants.ENVIRONMENT_FOX7, FOX7_VALID_USER_ID, PORTAL_GROUP_MYNTRA, PUMA_PROD_NOTIFICATION_IMG_URL, PUMA_PROD_NOTIFICATION_PAGE_URL, startTime, endTime,
				PUMA_PROD_NOTIFICATION_TEXT, PUMA_PROD_NOTIFICATION_TITLE, String.valueOf(new UUID()), NOTIFICATION_TYPE_MARKETING };
		String[] arr6 = { IServiceConstants.ENVIRONMENT_FOX7, FOX7_VALID_USER_ID, PORTAL_GROUP_MYNTRA, ADIDAS_PROD_NOTIFICATION_IMG_URL, ADIDAS_PROD_NOTIFICATION_PAGE_URL, startTime, endTime,
				ADIDAS_PROD_NOTIFICATION_TEXT, ADIDAS_PROD_NOTIFICATION_TITLE, String.valueOf(new UUID()), NOTIFICATION_TYPE_ORDER_STATUS_CHANGE };
		String[] arr7 = { IServiceConstants.ENVIRONMENT_FOX7, FOX7_VALID_USER_ID, PORTAL_GROUP_MYNTRA, PUMA_PROD_NOTIFICATION_IMG_URL, PUMA_PROD_NOTIFICATION_PAGE_URL, startTime, endTime,
				PUMA_PROD_NOTIFICATION_TEXT, PUMA_PROD_NOTIFICATION_TITLE, String.valueOf(new UUID()), NOTIFICATION_TYPE_PRICE_CHANGE };
		String[] arr8 = { IServiceConstants.ENVIRONMENT_FOX7, FOX7_VALID_USER_ID, PORTAL_GROUP_MYNTRA, ADIDAS_PROD_NOTIFICATION_IMG_URL, ADIDAS_PROD_NOTIFICATION_PAGE_URL, startTime, endTime,
				ADIDAS_PROD_NOTIFICATION_TEXT, ADIDAS_PROD_NOTIFICATION_TITLE, String.valueOf(new UUID()), NOTIFICATION_TYPE_REVENUE_LABS };
		
		String[] arr9 = { IServiceConstants.ENVIRONMENT_FUNC, FUNC_VALID_USER_ID, PORTAL_GROUP_MYNTRA, PUMA_PROD_NOTIFICATION_IMG_URL, PUMA_PROD_NOTIFICATION_PAGE_URL, startTime, endTime,
				PUMA_PROD_NOTIFICATION_TEXT, PUMA_PROD_NOTIFICATION_TITLE, String.valueOf(new UUID()), NOTIFICATION_TYPE_MARKETING };
		String[] arr10 = { IServiceConstants.ENVIRONMENT_FUNC, FUNC_VALID_USER_ID, PORTAL_GROUP_MYNTRA, ADIDAS_PROD_NOTIFICATION_IMG_URL, ADIDAS_PROD_NOTIFICATION_PAGE_URL, startTime, endTime,
				ADIDAS_PROD_NOTIFICATION_TEXT, ADIDAS_PROD_NOTIFICATION_TITLE, String.valueOf(new UUID()), NOTIFICATION_TYPE_ORDER_STATUS_CHANGE };
		String[] arr11 = { IServiceConstants.ENVIRONMENT_FUNC, FUNC_VALID_USER_ID, PORTAL_GROUP_MYNTRA, PUMA_PROD_NOTIFICATION_IMG_URL, PUMA_PROD_NOTIFICATION_PAGE_URL, startTime, endTime,
				PUMA_PROD_NOTIFICATION_TEXT, PUMA_PROD_NOTIFICATION_TITLE, String.valueOf(new UUID()), NOTIFICATION_TYPE_PRICE_CHANGE };
		String[] arr12 = { IServiceConstants.ENVIRONMENT_FUNC, FUNC_VALID_USER_ID, PORTAL_GROUP_MYNTRA, ADIDAS_PROD_NOTIFICATION_IMG_URL, ADIDAS_PROD_NOTIFICATION_PAGE_URL, startTime, endTime,
				ADIDAS_PROD_NOTIFICATION_TEXT, ADIDAS_PROD_NOTIFICATION_TITLE, String.valueOf(new UUID()), NOTIFICATION_TYPE_REVENUE_LABS };
		
		String[] arr13 = { IServiceConstants.ENVIRONMENT_PERF, PERF_VALID_USER_ID, PORTAL_GROUP_MYNTRA, PUMA_PROD_NOTIFICATION_IMG_URL, PUMA_PROD_NOTIFICATION_PAGE_URL, startTime, endTime,
				PUMA_PROD_NOTIFICATION_TEXT, PUMA_PROD_NOTIFICATION_TITLE, String.valueOf(new UUID()), NOTIFICATION_TYPE_MARKETING };
		String[] arr14 = { IServiceConstants.ENVIRONMENT_PERF, PERF_VALID_USER_ID, PORTAL_GROUP_MYNTRA, ADIDAS_PROD_NOTIFICATION_IMG_URL, ADIDAS_PROD_NOTIFICATION_PAGE_URL, startTime, endTime,
				ADIDAS_PROD_NOTIFICATION_TEXT, ADIDAS_PROD_NOTIFICATION_TITLE, String.valueOf(new UUID()), NOTIFICATION_TYPE_ORDER_STATUS_CHANGE };
		String[] arr15 = { IServiceConstants.ENVIRONMENT_PERF, PERF_VALID_USER_ID, PORTAL_GROUP_MYNTRA, PUMA_PROD_NOTIFICATION_IMG_URL, PUMA_PROD_NOTIFICATION_PAGE_URL, startTime, endTime,
				PUMA_PROD_NOTIFICATION_TEXT, PUMA_PROD_NOTIFICATION_TITLE, String.valueOf(new UUID()), NOTIFICATION_TYPE_PRICE_CHANGE };
		String[] arr16 = { IServiceConstants.ENVIRONMENT_PERF, PERF_VALID_USER_ID, PORTAL_GROUP_MYNTRA, ADIDAS_PROD_NOTIFICATION_IMG_URL, ADIDAS_PROD_NOTIFICATION_PAGE_URL, startTime, endTime,
				ADIDAS_PROD_NOTIFICATION_TEXT, ADIDAS_PROD_NOTIFICATION_TITLE, String.valueOf(new UUID()), NOTIFICATION_TYPE_REVENUE_LABS };
		
		Object dataSet[][] = new Object[][] { arr5, arr6, arr7, arr8, arr9, arr10, arr11, arr12, arr13, arr14, arr15, arr16 };
		return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] NotificationServiceDP_createMasterNotification_verifyResponseDataNodesUsingSchemaValidations(ITestContext testContext)
	{
		// notification valid in hours
		String publishTime = String.valueOf(Calendar.getInstance().getTimeInMillis());
		
		Calendar startTimeCal = Calendar.getInstance();
		startTimeCal.add(Calendar.HOUR, 2);
		String startTime = String.valueOf(startTimeCal.getTimeInMillis()); 
		
		Calendar endTimeCal = Calendar.getInstance();
		endTimeCal.add(Calendar.HOUR, 8);
		String endTime = String.valueOf(endTimeCal.getTimeInMillis());
		
		/*String[] arr1 = { ENVIRONMENT_PROD, PUMA_PROD_NOTIFICATION_TEXT, PUMA_PROD_NOTIFICATION_TITLE, PUMA_PROD_NOTIFICATION_IMG_URL, PUMA_PROD_NOTIFICATION_PAGE_URL,
				publishTime, startTime, endTime, NOTIFICATION_TYPE_MARKETING, PORTAL_GROUP_MYNTRA };
		String[] arr2 = { ENVIRONMENT_PROD, VEROMODA_PROD_NOTIFICATION_TEXT, VEROMODA_PROD_NOTIFICATION_TITLE, VEROMODA_PROD_NOTIFICATION_IMG_URL, VEROMODA_PROD_NOTIFICATION_PAGE_URL,
				publishTime, startTime, endTime, NOTIFICATION_TYPE_REVENUE_LABS, PORTAL_GROUP_MYNTRA };
		String[] arr3 = { ENVIRONMENT_PROD, NIKE_PROD_NOTIFICATION_TEXT, NIKE_PROD_NOTIFICATION_TITLE, NIKE_PROD_NOTIFICATION_IMG_URL, NIKE_PROD_NOTIFICATION_PAGE_URL,
				publishTime, startTime, endTime, NOTIFICATION_TYPE_ORDER_STATUS_CHANGE, PORTAL_GROUP_MYNTRA };
		String[] arr4 = { ENVIRONMENT_PROD, ADIDAS_PROD_NOTIFICATION_TEXT, ADIDAS_PROD_NOTIFICATION_TITLE, ADIDAS_PROD_NOTIFICATION_IMG_URL, ADIDAS_PROD_NOTIFICATION_PAGE_URL, 
				publishTime, startTime, endTime, NOTIFICATION_TYPE_PRICE_CHANGE, PORTAL_GROUP_MYNTRA };*/
		
		String[] arr5 = { IServiceConstants.ENVIRONMENT_FOX7, PUMA_PROD_NOTIFICATION_TEXT, PUMA_PROD_NOTIFICATION_TITLE, PUMA_PROD_NOTIFICATION_IMG_URL, PUMA_PROD_NOTIFICATION_PAGE_URL,
				publishTime, startTime, endTime, NOTIFICATION_TYPE_MARKETING, PORTAL_GROUP_MYNTRA };
		String[] arr6 = { IServiceConstants.ENVIRONMENT_FOX7, VEROMODA_PROD_NOTIFICATION_TEXT, VEROMODA_PROD_NOTIFICATION_TITLE, VEROMODA_PROD_NOTIFICATION_IMG_URL, VEROMODA_PROD_NOTIFICATION_PAGE_URL,
				publishTime, startTime, endTime, NOTIFICATION_TYPE_REVENUE_LABS, PORTAL_GROUP_MYNTRA };
		String[] arr7 = { IServiceConstants.ENVIRONMENT_FOX7, NIKE_PROD_NOTIFICATION_TEXT, NIKE_PROD_NOTIFICATION_TITLE, NIKE_PROD_NOTIFICATION_IMG_URL, NIKE_PROD_NOTIFICATION_PAGE_URL,
				publishTime, startTime, endTime, NOTIFICATION_TYPE_ORDER_STATUS_CHANGE, PORTAL_GROUP_MYNTRA };
		String[] arr8 = { IServiceConstants.ENVIRONMENT_FOX7, ADIDAS_PROD_NOTIFICATION_TEXT, ADIDAS_PROD_NOTIFICATION_TITLE, ADIDAS_PROD_NOTIFICATION_IMG_URL, ADIDAS_PROD_NOTIFICATION_PAGE_URL,
				publishTime, startTime, endTime, NOTIFICATION_TYPE_PRICE_CHANGE, PORTAL_GROUP_MYNTRA };
		
		String[] arr9 = { IServiceConstants.ENVIRONMENT_FUNC, PUMA_PROD_NOTIFICATION_TEXT, PUMA_PROD_NOTIFICATION_TITLE, PUMA_PROD_NOTIFICATION_IMG_URL, PUMA_PROD_NOTIFICATION_PAGE_URL,
				publishTime, startTime, endTime, NOTIFICATION_TYPE_MARKETING, PORTAL_GROUP_MYNTRA };
		String[] arr10 = { IServiceConstants.ENVIRONMENT_FUNC, VEROMODA_PROD_NOTIFICATION_TEXT, VEROMODA_PROD_NOTIFICATION_TITLE, VEROMODA_PROD_NOTIFICATION_IMG_URL, VEROMODA_PROD_NOTIFICATION_PAGE_URL,
				publishTime, startTime, endTime, NOTIFICATION_TYPE_REVENUE_LABS, PORTAL_GROUP_MYNTRA };
		String[] arr11 = { IServiceConstants.ENVIRONMENT_FUNC, NIKE_PROD_NOTIFICATION_TEXT, NIKE_PROD_NOTIFICATION_TITLE, NIKE_PROD_NOTIFICATION_IMG_URL, NIKE_PROD_NOTIFICATION_PAGE_URL,
				publishTime, startTime, endTime, NOTIFICATION_TYPE_ORDER_STATUS_CHANGE, PORTAL_GROUP_MYNTRA };
		String[] arr12 = { IServiceConstants.ENVIRONMENT_FUNC, ADIDAS_PROD_NOTIFICATION_TEXT, ADIDAS_PROD_NOTIFICATION_TITLE, ADIDAS_PROD_NOTIFICATION_IMG_URL, ADIDAS_PROD_NOTIFICATION_PAGE_URL,
				publishTime, startTime, endTime, NOTIFICATION_TYPE_PRICE_CHANGE, PORTAL_GROUP_MYNTRA };
		
		String[] arr13 = { IServiceConstants.ENVIRONMENT_PERF, PUMA_PROD_NOTIFICATION_TEXT, PUMA_PROD_NOTIFICATION_TITLE, PUMA_PROD_NOTIFICATION_IMG_URL, PUMA_PROD_NOTIFICATION_PAGE_URL,
				publishTime, startTime, endTime, NOTIFICATION_TYPE_MARKETING, PORTAL_GROUP_MYNTRA };
		String[] arr14 = { IServiceConstants.ENVIRONMENT_PERF, VEROMODA_PROD_NOTIFICATION_TEXT, VEROMODA_PROD_NOTIFICATION_TITLE, VEROMODA_PROD_NOTIFICATION_IMG_URL, VEROMODA_PROD_NOTIFICATION_PAGE_URL,
				publishTime, startTime, endTime, NOTIFICATION_TYPE_REVENUE_LABS, PORTAL_GROUP_MYNTRA };
		String[] arr15 = { IServiceConstants.ENVIRONMENT_PERF, NIKE_PROD_NOTIFICATION_TEXT, NIKE_PROD_NOTIFICATION_TITLE, NIKE_PROD_NOTIFICATION_IMG_URL, NIKE_PROD_NOTIFICATION_PAGE_URL,
				publishTime, startTime, endTime, NOTIFICATION_TYPE_ORDER_STATUS_CHANGE, PORTAL_GROUP_MYNTRA };
		String[] arr16 = { IServiceConstants.ENVIRONMENT_PERF, ADIDAS_PROD_NOTIFICATION_TEXT, ADIDAS_PROD_NOTIFICATION_TITLE, ADIDAS_PROD_NOTIFICATION_IMG_URL, ADIDAS_PROD_NOTIFICATION_PAGE_URL,
				publishTime, startTime, endTime, NOTIFICATION_TYPE_PRICE_CHANGE, PORTAL_GROUP_MYNTRA };
				
		Object[][] dataSet = new Object[][] { arr5, arr6, arr7, arr8, arr9, arr10, arr11, arr12, arr13, arr14, arr15, arr16 };
		return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] NotificationServiceDP_searchMasterNotification_verifyResponseDataNodesUsingSchemaValidations(ITestContext testContext)
	{
		// {portalGroup}/{notificationType}
		/*String[] arr1 = { ENVIRONMENT_PROD, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_MARKETING };
		String[] arr2 = { ENVIRONMENT_PROD, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_REVENUE_LABS };
		String[] arr3 = { ENVIRONMENT_PROD, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_ORDER_STATUS_CHANGE };
		String[] arr4 = { ENVIRONMENT_PROD, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_PRICE_CHANGE };*/
		
		String[] arr5 = { IServiceConstants.ENVIRONMENT_FOX7, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_MARKETING };
		String[] arr6 = { IServiceConstants.ENVIRONMENT_FOX7, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_REVENUE_LABS };
		String[] arr7 = { IServiceConstants.ENVIRONMENT_FOX7, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_ORDER_STATUS_CHANGE };
		String[] arr8 = { IServiceConstants.ENVIRONMENT_FOX7, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_PRICE_CHANGE };
		
		String[] arr9 = { IServiceConstants.ENVIRONMENT_FUNC, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_MARKETING };
		String[] arr10 = { IServiceConstants.ENVIRONMENT_FUNC, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_REVENUE_LABS };
		String[] arr11 = { IServiceConstants.ENVIRONMENT_FUNC, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_ORDER_STATUS_CHANGE };
		String[] arr12 = { IServiceConstants.ENVIRONMENT_FUNC, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_PRICE_CHANGE };
		
		String[] arr13 = { IServiceConstants.ENVIRONMENT_PERF, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_MARKETING };
		String[] arr14 = { IServiceConstants.ENVIRONMENT_PERF, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_REVENUE_LABS };
		String[] arr15 = { IServiceConstants.ENVIRONMENT_PERF, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_ORDER_STATUS_CHANGE };
		String[] arr16 = { IServiceConstants.ENVIRONMENT_PERF, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_PRICE_CHANGE };

		Object[][] dataSet = new Object[][] { arr5, arr6, arr7, arr8, arr9, arr10, arr11, arr12, arr13, arr14, arr15, arr16 };
		return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] NotificationServiceDP_searchMasterNotificationWithSTET_verifyResponseDataNodesUsingSchemaValidations(ITestContext testContext)
	{
		
		Calendar startTimeCal = Calendar.getInstance();
		//startTimeCal.add(Calendar.HOUR, 2);
		String startTime = String.valueOf(startTimeCal.getTimeInMillis());
		
		Calendar endTimeCal = Calendar.getInstance();
		endTimeCal.add(Calendar.MONTH, 4);
		String endTime = String.valueOf(endTimeCal.getTimeInMillis());
		
		// {portalGroup}/{notificationType}
		/*String[] arr1 = { ENVIRONMENT_PROD, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_MARKETING, startTime, endTime };
		String[] arr2 = { ENVIRONMENT_PROD, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_REVENUE_LABS, startTime, endTime };
		String[] arr3 = { ENVIRONMENT_PROD, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_ORDER_STATUS_CHANGE, startTime, endTime };
		String[] arr4 = { ENVIRONMENT_PROD, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_PRICE_CHANGE, startTime, endTime };*/
		
		String[] arr5 = { IServiceConstants.ENVIRONMENT_FOX7, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_MARKETING, startTime, endTime };
		String[] arr6 = { IServiceConstants.ENVIRONMENT_FOX7, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_REVENUE_LABS, startTime, endTime };
		String[] arr7 = { IServiceConstants.ENVIRONMENT_FOX7, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_ORDER_STATUS_CHANGE, startTime, endTime };
		String[] arr8 = { IServiceConstants.ENVIRONMENT_FOX7, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_PRICE_CHANGE, startTime, endTime };
		
		String[] arr9 = { IServiceConstants.ENVIRONMENT_FUNC, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_MARKETING, startTime, endTime };
		String[] arr10 = { IServiceConstants.ENVIRONMENT_FUNC, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_REVENUE_LABS, startTime, endTime };
		String[] arr11 = { IServiceConstants.ENVIRONMENT_FUNC, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_ORDER_STATUS_CHANGE, startTime, endTime };
		String[] arr12 = { IServiceConstants.ENVIRONMENT_FUNC, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_PRICE_CHANGE, startTime, endTime };
		
		String[] arr13 = { IServiceConstants.ENVIRONMENT_PERF, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_MARKETING, startTime, endTime };
		String[] arr14 = { IServiceConstants.ENVIRONMENT_PERF, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_REVENUE_LABS, startTime, endTime };
		String[] arr15 = { IServiceConstants.ENVIRONMENT_PERF, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_ORDER_STATUS_CHANGE, startTime, endTime };
		String[] arr16 = { IServiceConstants.ENVIRONMENT_PERF, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_PRICE_CHANGE, startTime, endTime };

		Object[][] dataSet = new Object[][] { arr5, arr6, arr7, arr8, arr9, arr10, arr11, arr12, arr13, arr14, arr15, arr16 };
		return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] NotificationServiceDP_getDetailsMasterNotification_verifyResponseDataNodesUsingSchemaValidations(ITestContext testContext)
	{
		// {portalGroup}/{notificationType}
		/*String[] arr1 = { ENVIRONMENT_PROD, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_MARKETING };
		String[] arr2 = { ENVIRONMENT_PROD, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_REVENUE_LABS };
		String[] arr3 = { ENVIRONMENT_PROD, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_ORDER_STATUS_CHANGE };
		String[] arr4 = { ENVIRONMENT_PROD, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_PRICE_CHANGE };*/
		
		String[] arr5 = { IServiceConstants.ENVIRONMENT_FOX7, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_MARKETING };
		String[] arr6 = { IServiceConstants.ENVIRONMENT_FOX7, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_REVENUE_LABS };
		String[] arr7 = { IServiceConstants.ENVIRONMENT_FOX7, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_ORDER_STATUS_CHANGE };
		String[] arr8 = { IServiceConstants.ENVIRONMENT_FOX7, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_PRICE_CHANGE };
		
		String[] arr9 = { IServiceConstants.ENVIRONMENT_FUNC, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_MARKETING };
		String[] arr10 = { IServiceConstants.ENVIRONMENT_FUNC, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_REVENUE_LABS };
		String[] arr11 = { IServiceConstants.ENVIRONMENT_FUNC, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_ORDER_STATUS_CHANGE };
		String[] arr12 = { IServiceConstants.ENVIRONMENT_FUNC, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_PRICE_CHANGE };
		
		String[] arr13 = { IServiceConstants.ENVIRONMENT_PERF, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_MARKETING };
		String[] arr14 = { IServiceConstants.ENVIRONMENT_PERF, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_REVENUE_LABS };
		String[] arr15 = { IServiceConstants.ENVIRONMENT_PERF, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_ORDER_STATUS_CHANGE };
		String[] arr16 = { IServiceConstants.ENVIRONMENT_PERF, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_PRICE_CHANGE };

		Object[][] dataSet = new Object[][] { arr5, arr6, arr7, arr8, arr9, arr10, arr11, arr12, arr13, arr14, arr15, arr16 };
		return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] NotificationServiceDP_deleteNotifications_verifyResponseDataNodesUsingSchemaValidations(ITestContext testContext)
	{
		// {portalGroup}/{notificationType}
		/*String[] arr1 = { ENVIRONMENT_PROD, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_MARKETING };
		String[] arr2 = { ENVIRONMENT_PROD, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_REVENUE_LABS };
		String[] arr3 = { ENVIRONMENT_PROD, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_ORDER_STATUS_CHANGE };
		String[] arr4 = { ENVIRONMENT_PROD, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_PRICE_CHANGE };*/
		
		String[] arr5 = { IServiceConstants.ENVIRONMENT_FOX7, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_MARKETING };
		String[] arr6 = { IServiceConstants.ENVIRONMENT_FOX7, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_REVENUE_LABS };
		String[] arr7 = { IServiceConstants.ENVIRONMENT_FOX7, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_ORDER_STATUS_CHANGE };
		String[] arr8 = { IServiceConstants.ENVIRONMENT_FOX7, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_PRICE_CHANGE };
		
		String[] arr9 = { IServiceConstants.ENVIRONMENT_FUNC, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_MARKETING };
		String[] arr10 = { IServiceConstants.ENVIRONMENT_FUNC, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_REVENUE_LABS };
		String[] arr11 = { IServiceConstants.ENVIRONMENT_FUNC, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_ORDER_STATUS_CHANGE };
		String[] arr12 = { IServiceConstants.ENVIRONMENT_FUNC, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_PRICE_CHANGE };
		
		String[] arr13 = { IServiceConstants.ENVIRONMENT_PERF, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_MARKETING };
		String[] arr14 = { IServiceConstants.ENVIRONMENT_PERF, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_REVENUE_LABS };
		String[] arr15 = { IServiceConstants.ENVIRONMENT_PERF, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_ORDER_STATUS_CHANGE };
		String[] arr16 = { IServiceConstants.ENVIRONMENT_PERF, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_PRICE_CHANGE };

		Object[][] dataSet = new Object[][] { arr5, arr6, arr7, arr8, arr9, arr10, arr11, arr12, arr13, arr14, arr15, arr16 };
		return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] NotificationServiceDP_createNotificationForSingleUser_verifyResponseDataNodesUsingSchemaValidations(ITestContext testContext)
	{
		// {userId}/{portalGroup}/{notificationType}
		/*String[] arr1 = { ENVIRONMENT_PROD, PROD_VALID_USER_ID, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_MARKETING };
		String[] arr2 = { ENVIRONMENT_PROD, PROD_VALID_USER_ID, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_REVENUE_LABS }; 
		String[] arr3 = { ENVIRONMENT_PROD, PROD_VALID_USER_ID, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_ORDER_STATUS_CHANGE }; 
		String[] arr4 = { ENVIRONMENT_PROD, PROD_VALID_USER_ID, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_PRICE_CHANGE }; */
		
		String[] arr5 = { IServiceConstants.ENVIRONMENT_FOX7, FOX7_VALID_USER_ID, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_MARKETING };
		String[] arr6 = { IServiceConstants.ENVIRONMENT_FOX7, FOX7_VALID_USER_ID, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_REVENUE_LABS };
		String[] arr7 = { IServiceConstants.ENVIRONMENT_FOX7, FOX7_VALID_USER_ID, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_ORDER_STATUS_CHANGE };
		String[] arr8 = { IServiceConstants.ENVIRONMENT_FOX7, FOX7_VALID_USER_ID, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_PRICE_CHANGE };
		
		String[] arr9 = { IServiceConstants.ENVIRONMENT_FUNC, FUNC_VALID_USER_ID, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_MARKETING };
		String[] arr10 = { IServiceConstants.ENVIRONMENT_FUNC, FUNC_VALID_USER_ID, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_REVENUE_LABS };
		String[] arr11 = { IServiceConstants.ENVIRONMENT_FUNC, FUNC_VALID_USER_ID, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_ORDER_STATUS_CHANGE };
		String[] arr12 = { IServiceConstants.ENVIRONMENT_FUNC, FUNC_VALID_USER_ID, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_PRICE_CHANGE };
		
		
		String[] arr13 = { IServiceConstants.ENVIRONMENT_PERF, PERF_VALID_USER_ID, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_MARKETING };
		String[] arr14 = { IServiceConstants.ENVIRONMENT_PERF, PERF_VALID_USER_ID, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_REVENUE_LABS };
		String[] arr15 = { IServiceConstants.ENVIRONMENT_PERF, PERF_VALID_USER_ID, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_ORDER_STATUS_CHANGE };
		String[] arr16 = { IServiceConstants.ENVIRONMENT_PERF, PERF_VALID_USER_ID, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_PRICE_CHANGE };

		Object[][] dataSet = new Object[][] { arr5, arr6, arr7, arr8, arr9, arr10, arr11, arr12, arr13, arr14, arr15, arr16 };
		return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] NotificationServiceDP_createNotificationForSetOfUsers_verifyResponseDataNodesUsingSchemaValidations(ITestContext testContext)
	{
		// {portalGroup}/{notificationType}/{userIds}
		/*String[] arr1 = { ENVIRONMENT_PROD, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_MARKETING, PROD_VALID_USER_IDS };
		String[] arr2 = { ENVIRONMENT_PROD, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_REVENUE_LABS, PROD_VALID_USER_IDS };
		String[] arr3 = { ENVIRONMENT_PROD, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_ORDER_STATUS_CHANGE, PROD_VALID_USER_IDS };
		String[] arr4 = { ENVIRONMENT_PROD, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_PRICE_CHANGE, PROD_VALID_USER_IDS };*/
		
		String[] arr5 = { IServiceConstants.ENVIRONMENT_FOX7, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_MARKETING, FOX7_VALID_USER_IDS };
		String[] arr6 = { IServiceConstants.ENVIRONMENT_FOX7, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_REVENUE_LABS, FOX7_VALID_USER_IDS };
		String[] arr7 = { IServiceConstants.ENVIRONMENT_FOX7, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_ORDER_STATUS_CHANGE, FOX7_VALID_USER_IDS };
		String[] arr8 = { IServiceConstants.ENVIRONMENT_FOX7, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_PRICE_CHANGE, FOX7_VALID_USER_IDS };
		
		String[] arr9 = { IServiceConstants.ENVIRONMENT_FUNC, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_MARKETING, FUNC_VALID_USER_IDS};
		String[] arr10 = { IServiceConstants.ENVIRONMENT_FUNC, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_REVENUE_LABS, FUNC_VALID_USER_IDS };
		String[] arr11 = { IServiceConstants.ENVIRONMENT_FUNC, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_ORDER_STATUS_CHANGE, FUNC_VALID_USER_IDS };
		String[] arr12 = { IServiceConstants.ENVIRONMENT_FUNC, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_PRICE_CHANGE, FUNC_VALID_USER_IDS };
		
		String[] arr13 = { IServiceConstants.ENVIRONMENT_PERF, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_MARKETING, PERF_VALID_USER_IDS};
		String[] arr14 = { IServiceConstants.ENVIRONMENT_PERF, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_REVENUE_LABS, PERF_VALID_USER_IDS };
		String[] arr15 = { IServiceConstants.ENVIRONMENT_PERF, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_ORDER_STATUS_CHANGE, PERF_VALID_USER_IDS };
		String[] arr16 = { IServiceConstants.ENVIRONMENT_PERF, PORTAL_GROUP_MYNTRA, NOTIFICATION_TYPE_PRICE_CHANGE, PERF_VALID_USER_IDS };
		
		Object[][] dataSet = new Object[][] { arr5, arr6, arr7, arr8, arr9, arr10, arr11, arr12, arr13, arr14, arr15, arr16 };
		return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] NotificationServiceDP_getActiveNotificationsCount_verifyResponseDataNodesUsingSchemaValidations(ITestContext testContext) 
	{
		// PROD Existing user
		String[] arr1 = { IServiceConstants.ENVIRONMENT_PROD, PROD_VALID_USER_ID, PORTAL_GROUP_MYNTRA };
		
		// PROD Non Existing user
		String[] arr2 = { IServiceConstants.ENVIRONMENT_PROD, "fnsdfdfghhfg@gmail.com", PORTAL_GROUP_MYNTRA };
				
		// FOX7 Existing user
		String[] arr3 = { IServiceConstants.ENVIRONMENT_FOX7, FOX7_VALID_USER_ID, PORTAL_GROUP_MYNTRA };
		
		// FOX7 Non Existing user
		String[] arr4 = { IServiceConstants.ENVIRONMENT_FOX7, "fnsdfdfghhfg@gmail.com", PORTAL_GROUP_MYNTRA };
		
		// FUNC Existing user
		String[] arr5 = { IServiceConstants.ENVIRONMENT_FUNC, FUNC_VALID_USER_ID, PORTAL_GROUP_MYNTRA };

		// FUNC Non Existing user
		String[] arr6 = { IServiceConstants.ENVIRONMENT_FUNC, "fnsdfdfghhfg@gmail.com", PORTAL_GROUP_MYNTRA };
		
		// PERF Existing user
		String[] arr7 = { IServiceConstants.ENVIRONMENT_PERF, PERF_VALID_USER_ID, PORTAL_GROUP_MYNTRA };
		
		// PERF Non Existing user
		String[] arr8 = { IServiceConstants.ENVIRONMENT_PERF, "fnsdfdfghhfg@gmail.com", PORTAL_GROUP_MYNTRA };
						
		Object dataset[][] = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8 };
		return Toolbox.returnReducedDataSet(environment, dataset, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] NotificationServiceDP_getActiveNotifications_verifyResponseDataNodesUsingSchemaValidations(ITestContext testContext) 
	{
		// PROD Existing user
		String[] arr1 = { IServiceConstants.ENVIRONMENT_PROD, PROD_VALID_USER_ID, PORTAL_GROUP_MYNTRA };
		
		// PROD Non Existing user
		String[] arr2 = { IServiceConstants.ENVIRONMENT_PROD, "fnsdfdfghhfg@gmail.com", PORTAL_GROUP_MYNTRA };
				
		// FOX7 Existing user
		String[] arr3 = { IServiceConstants.ENVIRONMENT_FOX7, FOX7_VALID_USER_ID, PORTAL_GROUP_MYNTRA };
		
		// FOX7 Non Existing user
		String[] arr4 = { IServiceConstants.ENVIRONMENT_FOX7, "fnsdfdfghhfg@gmail.com", PORTAL_GROUP_MYNTRA };
		
		// FUNC Existing user
		String[] arr5 = { IServiceConstants.ENVIRONMENT_FUNC, FUNC_VALID_USER_ID, PORTAL_GROUP_MYNTRA };

		// FUNC Non Existing user
		String[] arr6 = { IServiceConstants.ENVIRONMENT_FUNC, "fnsdfdfghhfg@gmail.com", PORTAL_GROUP_MYNTRA };
		
		// PERF Existing user
		String[] arr7 = { IServiceConstants.ENVIRONMENT_PERF, PERF_VALID_USER_ID, PORTAL_GROUP_MYNTRA };
		
		// PERF Non Existing user
		String[] arr8 = { IServiceConstants.ENVIRONMENT_PERF, "fnsdfdfghhfg@gmail.com", PORTAL_GROUP_MYNTRA };
				
		Object dataSet[][] = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8 };
		return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] NotificationServiceDP_markAsReadNotification_verifyResponseDataNodesUsingSchemaValidations(ITestContext testContext) 
	{
		/*// PROD Existing user
		String[] arr1 = { ENVIRONMENT_PROD, PROD_VALID_USER_ID, PORTAL_GROUP_MYNTRA };
		
		// PROD Non Existing user
		String[] arr2 = { ENVIRONMENT_PROD, "fnsdfdfghhfg@gmail.com", PORTAL_GROUP_MYNTRA };*/
				
		// FOX7 Existing user
		String[] arr3 = { IServiceConstants.ENVIRONMENT_FOX7, FOX7_VALID_USER_ID, PORTAL_GROUP_MYNTRA };
		
		// FOX7 Non Existing user
		
		//String[] arr4 = { ENVIRONMENT_FOX7, "fnsdfdfghhfg@gmail.com", PORTAL_GROUP_MYNTRA };
		
		// FUNC Existing user
		String[] arr5 = { IServiceConstants.ENVIRONMENT_FUNC, FUNC_VALID_USER_ID, PORTAL_GROUP_MYNTRA };

		// FUNC Non Existing user
		String[] arr6 = { IServiceConstants.ENVIRONMENT_FUNC, "fnsdfdfghhfg@gmail.com", PORTAL_GROUP_MYNTRA };
		
		// PERF Existing user
		String[] arr7 = { IServiceConstants.ENVIRONMENT_PERF, PERF_VALID_USER_ID, PORTAL_GROUP_MYNTRA };
		
		// PERF Non Existing user
		String[] arr8 = { IServiceConstants.ENVIRONMENT_PERF, "fnsdfdfghhfg@gmail.com", PORTAL_GROUP_MYNTRA };
				
		Object dataSet[][] = new Object[][] { arr3, /*arr4,*/ arr5, arr6, arr7, arr8 };
		return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] NotificationServiceDP_createNotificationForUser_verifyResponseDataNodesUsingSchemaValidations(ITestContext testContext)
	{
		// notification valid in hours
		Calendar startTimeCal = Calendar.getInstance();
		//startTimeCal.add(Calendar.HOUR, 2);
		String startTime = String.valueOf(startTimeCal.getTimeInMillis());
		
		Calendar endTimeCal = Calendar.getInstance();
		endTimeCal.add(Calendar.HOUR, 4);
		String endTime = String.valueOf(endTimeCal.getTimeInMillis());
		
		/*String[] arr1 = { ENVIRONMENT_PROD, PROD_VALID_USER_ID, PORTAL_GROUP_MYNTRA, TITAN_PROD_NOTIFICATION_IMG_URL, TITAN_PROD_NOTIFICATION_PAGE_URL, startTime, endTime,
				TITAN_PROD_NOTIFICATION_TEXT, TITAN_PROD_NOTIFICATION_TITLE, String.valueOf(new UUID()), NOTIFICATION_TYPE_MARKETING };
		String[] arr2 = { ENVIRONMENT_PROD, PROD_VALID_USER_ID, PORTAL_GROUP_MYNTRA, FOSSIL_PROD_NOTIFICATION_IMG_URL, FOSSIL_PROD_NOTIFICATION_PAGE_URL, startTime, endTime,
				FOSSIL_PROD_NOTIFICATION_TEXT, FOSSIL_PROD_NOTIFICATION_TITLE, String.valueOf(new UUID()), NOTIFICATION_TYPE_ORDER_STATUS_CHANGE };
		String[] arr3 = { ENVIRONMENT_PROD, PROD_VALID_USER_ID, PORTAL_GROUP_MYNTRA, TITAN_PROD_NOTIFICATION_IMG_URL, TITAN_PROD_NOTIFICATION_PAGE_URL, startTime, endTime,
				TITAN_PROD_NOTIFICATION_TEXT, TITAN_PROD_NOTIFICATION_TITLE, String.valueOf(new UUID()), NOTIFICATION_TYPE_PRICE_CHANGE };
		String[] arr4 = { ENVIRONMENT_PROD, PROD_VALID_USER_ID, PORTAL_GROUP_MYNTRA, FOSSIL_PROD_NOTIFICATION_IMG_URL, FOSSIL_PROD_NOTIFICATION_PAGE_URL, startTime, endTime,
				FOSSIL_PROD_NOTIFICATION_TEXT, FOSSIL_PROD_NOTIFICATION_TITLE, String.valueOf(new UUID()), NOTIFICATION_TYPE_REVENUE_LABS };*/
		
		String[] arr5 = { IServiceConstants.ENVIRONMENT_FOX7, FOX7_VALID_USER_ID, PORTAL_GROUP_MYNTRA, TITAN_PROD_NOTIFICATION_IMG_URL, TITAN_PROD_NOTIFICATION_PAGE_URL, startTime, endTime,
				TITAN_PROD_NOTIFICATION_TEXT, TITAN_PROD_NOTIFICATION_TITLE, String.valueOf(new UUID()), NOTIFICATION_TYPE_MARKETING };
		String[] arr6 = { IServiceConstants.ENVIRONMENT_FOX7, FOX7_VALID_USER_ID, PORTAL_GROUP_MYNTRA, FOSSIL_PROD_NOTIFICATION_IMG_URL, FOSSIL_PROD_NOTIFICATION_PAGE_URL, startTime, endTime,
				FOSSIL_PROD_NOTIFICATION_TEXT, FOSSIL_PROD_NOTIFICATION_TITLE, String.valueOf(new UUID()), NOTIFICATION_TYPE_ORDER_STATUS_CHANGE };
		String[] arr7 = { IServiceConstants.ENVIRONMENT_FOX7, FOX7_VALID_USER_ID, PORTAL_GROUP_MYNTRA, TITAN_PROD_NOTIFICATION_IMG_URL, TITAN_PROD_NOTIFICATION_PAGE_URL, startTime, endTime,
				TITAN_PROD_NOTIFICATION_TEXT, TITAN_PROD_NOTIFICATION_TITLE, String.valueOf(new UUID()), NOTIFICATION_TYPE_PRICE_CHANGE };
		String[] arr8 = { IServiceConstants.ENVIRONMENT_FOX7, FOX7_VALID_USER_ID, PORTAL_GROUP_MYNTRA, FOSSIL_PROD_NOTIFICATION_IMG_URL, FOSSIL_PROD_NOTIFICATION_PAGE_URL, startTime, endTime,
				FOSSIL_PROD_NOTIFICATION_TEXT, FOSSIL_PROD_NOTIFICATION_TITLE, String.valueOf(new UUID()), NOTIFICATION_TYPE_REVENUE_LABS };
		
		String[] arr9 = { IServiceConstants.ENVIRONMENT_FUNC, FUNC_VALID_USER_ID, PORTAL_GROUP_MYNTRA, TITAN_PROD_NOTIFICATION_IMG_URL, TITAN_PROD_NOTIFICATION_PAGE_URL, startTime, endTime,
				TITAN_PROD_NOTIFICATION_TEXT, TITAN_PROD_NOTIFICATION_TITLE, String.valueOf(new UUID()), NOTIFICATION_TYPE_MARKETING };
		String[] arr10 = { IServiceConstants.ENVIRONMENT_FUNC, FUNC_VALID_USER_ID, PORTAL_GROUP_MYNTRA, FOSSIL_PROD_NOTIFICATION_IMG_URL, FOSSIL_PROD_NOTIFICATION_PAGE_URL, startTime, endTime,
				FOSSIL_PROD_NOTIFICATION_TEXT, FOSSIL_PROD_NOTIFICATION_TITLE, String.valueOf(new UUID()), NOTIFICATION_TYPE_ORDER_STATUS_CHANGE };
		String[] arr11 = { IServiceConstants.ENVIRONMENT_FUNC, FUNC_VALID_USER_ID, PORTAL_GROUP_MYNTRA, TITAN_PROD_NOTIFICATION_IMG_URL, TITAN_PROD_NOTIFICATION_PAGE_URL, startTime, endTime,
				TITAN_PROD_NOTIFICATION_TEXT, TITAN_PROD_NOTIFICATION_TITLE, String.valueOf(new UUID()), NOTIFICATION_TYPE_PRICE_CHANGE };
		String[] arr12 = { IServiceConstants.ENVIRONMENT_FUNC, FUNC_VALID_USER_ID, PORTAL_GROUP_MYNTRA, FOSSIL_PROD_NOTIFICATION_IMG_URL, FOSSIL_PROD_NOTIFICATION_PAGE_URL, startTime, endTime,
				FOSSIL_PROD_NOTIFICATION_TEXT, FOSSIL_PROD_NOTIFICATION_TITLE, String.valueOf(new UUID()), NOTIFICATION_TYPE_REVENUE_LABS };
		
		String[] arr13 = { IServiceConstants.ENVIRONMENT_PERF, PERF_VALID_USER_ID, PORTAL_GROUP_MYNTRA, TITAN_PROD_NOTIFICATION_IMG_URL, TITAN_PROD_NOTIFICATION_PAGE_URL, startTime, endTime,
				TITAN_PROD_NOTIFICATION_TEXT, TITAN_PROD_NOTIFICATION_TITLE, String.valueOf(new UUID()), NOTIFICATION_TYPE_MARKETING };
		String[] arr14 = { IServiceConstants.ENVIRONMENT_PERF, PERF_VALID_USER_ID, PORTAL_GROUP_MYNTRA, FOSSIL_PROD_NOTIFICATION_IMG_URL, FOSSIL_PROD_NOTIFICATION_PAGE_URL, startTime, endTime,
				FOSSIL_PROD_NOTIFICATION_TEXT, FOSSIL_PROD_NOTIFICATION_TITLE, String.valueOf(new UUID()), NOTIFICATION_TYPE_ORDER_STATUS_CHANGE };
		String[] arr15 = { IServiceConstants.ENVIRONMENT_PERF, PERF_VALID_USER_ID, PORTAL_GROUP_MYNTRA, TITAN_PROD_NOTIFICATION_IMG_URL, TITAN_PROD_NOTIFICATION_PAGE_URL, startTime, endTime,
				TITAN_PROD_NOTIFICATION_TEXT, TITAN_PROD_NOTIFICATION_TITLE, String.valueOf(new UUID()), NOTIFICATION_TYPE_PRICE_CHANGE };
		String[] arr16 = { IServiceConstants.ENVIRONMENT_PERF, PERF_VALID_USER_ID, PORTAL_GROUP_MYNTRA, FOSSIL_PROD_NOTIFICATION_IMG_URL, FOSSIL_PROD_NOTIFICATION_PAGE_URL, startTime, endTime,
				FOSSIL_PROD_NOTIFICATION_TEXT, FOSSIL_PROD_NOTIFICATION_TITLE, String.valueOf(new UUID()), NOTIFICATION_TYPE_REVENUE_LABS };
		
		Object dataSet[][] = new Object[][] { arr5, arr6, arr7, arr8, arr9, arr10, arr11, arr12, arr13, arr14, arr15, arr16 };
		return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 1, 2);
	}
}
