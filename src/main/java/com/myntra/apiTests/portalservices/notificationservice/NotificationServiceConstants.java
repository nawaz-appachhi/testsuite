/**
 * 
 */
package com.myntra.apiTests.portalservices.notificationservice;

import com.myntra.apiTests.portalservices.commons.IServiceConstants;
import com.myntra.lordoftherings.PropertyReader;

/**
 * @author shankara.c
 *
 */
public interface NotificationServiceConstants extends IServiceConstants
{
	public static final PropertyReader PROPERTY_READER = new PropertyReader(new String[]{"./Data/dataproviders/notificationservicedp.properties"});

	// notificationTypes
	public static final String NOTIFICATION_TYPE_MARKETING = PROPERTY_READER.getPropertyValue(NotificationServiceEnum.NOTIFICATION_TYPE_MARKETING.name());
	public static final String NOTIFICATION_TYPE_REVENUE_LABS = PROPERTY_READER.getPropertyValue(NotificationServiceEnum.NOTIFICATION_TYPE_REVENUE_LABS.name());
	public static final String NOTIFICATION_TYPE_ORDER_STATUS_CHANGE = PROPERTY_READER.getPropertyValue(NotificationServiceEnum.NOTIFICATION_TYPE_ORDER_STATUS_CHANGE.name());
	public static final String NOTIFICATION_TYPE_PRICE_CHANGE = PROPERTY_READER.getPropertyValue(NotificationServiceEnum.NOTIFICATION_TYPE_PRICE_CHANGE.name());

	// portalGroups
	public static final String PORTAL_GROUP_MYNTRA =  PROPERTY_READER.getPropertyValue(NotificationServiceEnum.PORTAL_GROUP_MYNTRA.name());
	public static final String PORTAL_GROUP_MY_URBAN_LOOK = PROPERTY_READER.getPropertyValue(NotificationServiceEnum.PORTAL_GROUP_MY_URBAN_LOOK.name());
	public static final String PORTAL_GROUP_GAP = PROPERTY_READER.getPropertyValue(NotificationServiceEnum.PORTAL_GROUP_GAP.name());
	public static final String PORTAL_GROUP_PUMA = PROPERTY_READER.getPropertyValue(NotificationServiceEnum.PORTAL_GROUP_PUMA.name());
	public static final String PORTAL_GROUP_LEVIS = PROPERTY_READER.getPropertyValue(NotificationServiceEnum.PORTAL_GROUP_LEVIS.name());
	
	public static final String ACTION_VALUE = PROPERTY_READER.getPropertyValue(NotificationServiceEnum.ACTION_DELETE.name());
	
	public static final String TITAN_PROD_NOTIFICATION_TEXT = PROPERTY_READER.getPropertyValue(NotificationServiceEnum.TITAN_PROD_NOTIFICATION_TEXT.name());
	public static final String TITAN_PROD_NOTIFICATION_TITLE = PROPERTY_READER.getPropertyValue(NotificationServiceEnum.TITAN_PROD_NOTIFICATION_TITLE.name());
	public static final String TITAN_PROD_NOTIFICATION_IMG_URL = PROPERTY_READER.getPropertyValue(NotificationServiceEnum.TITAN_PROD_NOTIFICATION_IMG_URL.name());
	public static final String TITAN_PROD_NOTIFICATION_PAGE_URL = PROPERTY_READER.getPropertyValue(NotificationServiceEnum.TITAN_PROD_NOTIFICATION_PAGE_URL.name());

	public static final String FOSSIL_PROD_NOTIFICATION_TEXT = PROPERTY_READER.getPropertyValue(NotificationServiceEnum.FOSSIL_PROD_NOTIFICATION_TEXT.name());
	public static final String FOSSIL_PROD_NOTIFICATION_TITLE = PROPERTY_READER.getPropertyValue(NotificationServiceEnum.FOSSIL_PROD_NOTIFICATION_TITLE.name());
	public static final String FOSSIL_PROD_NOTIFICATION_IMG_URL = PROPERTY_READER.getPropertyValue(NotificationServiceEnum.FOSSIL_PROD_NOTIFICATION_IMG_URL.name());
	public static final String FOSSIL_PROD_NOTIFICATION_PAGE_URL = PROPERTY_READER.getPropertyValue(NotificationServiceEnum.FOSSIL_PROD_NOTIFICATION_PAGE_URL.name());

	public static final String CAT_PROD_NOTIFICATION_TEXT = PROPERTY_READER.getPropertyValue(NotificationServiceEnum.CAT_PROD_NOTIFICATION_TEXT.name());
	public static final String CAT_PROD_NOTIFICATION_TITLE = PROPERTY_READER.getPropertyValue(NotificationServiceEnum.CAT_PROD_NOTIFICATION_TITLE.name());
	public static final String CAT_PROD_NOTIFICATION_IMG_URL = PROPERTY_READER.getPropertyValue(NotificationServiceEnum.CAT_PROD_NOTIFICATION_IMG_URL.name());
	public static final String CAT_PROD_NOTIFICATION_PAGE_URL = PROPERTY_READER.getPropertyValue(NotificationServiceEnum.CAT_PROD_NOTIFICATION_PAGE_URL.name());

	public static final String VEROMODA_PROD_NOTIFICATION_TEXT = PROPERTY_READER.getPropertyValue(NotificationServiceEnum.VEROMODA_PROD_NOTIFICATION_TEXT.name());
	public static final String VEROMODA_PROD_NOTIFICATION_TITLE = PROPERTY_READER.getPropertyValue(NotificationServiceEnum.VEROMODA_PROD_NOTIFICATION_TITLE.name());
	public static final String VEROMODA_PROD_NOTIFICATION_IMG_URL = PROPERTY_READER.getPropertyValue(NotificationServiceEnum.VEROMODA_PROD_NOTIFICATION_IMG_URL.name());
	public static final String VEROMODA_PROD_NOTIFICATION_PAGE_URL = PROPERTY_READER.getPropertyValue(NotificationServiceEnum.VEROMODA_PROD_NOTIFICATION_PAGE_URL.name());

	public static final String PUMA_PROD_NOTIFICATION_TEXT = PROPERTY_READER.getPropertyValue(NotificationServiceEnum.PUMA_PROD_NOTIFICATION_TEXT.name());
	public static final String PUMA_PROD_NOTIFICATION_TITLE = PROPERTY_READER.getPropertyValue(NotificationServiceEnum.PUMA_PROD_NOTIFICATION_TITLE.name());
	public static final String PUMA_PROD_NOTIFICATION_IMG_URL = PROPERTY_READER.getPropertyValue(NotificationServiceEnum.PUMA_PROD_NOTIFICATION_IMG_URL.name());
	public static final String PUMA_PROD_NOTIFICATION_PAGE_URL = PROPERTY_READER.getPropertyValue(NotificationServiceEnum.PUMA_PROD_NOTIFICATION_PAGE_URL.name());

	public static final String NIKE_PROD_NOTIFICATION_TEXT = PROPERTY_READER.getPropertyValue(NotificationServiceEnum.NIKE_PROD_NOTIFICATION_TEXT.name());
	public static final String NIKE_PROD_NOTIFICATION_TITLE = PROPERTY_READER.getPropertyValue(NotificationServiceEnum.NIKE_PROD_NOTIFICATION_TITLE.name());
	public static final String NIKE_PROD_NOTIFICATION_IMG_URL = PROPERTY_READER.getPropertyValue(NotificationServiceEnum.NIKE_PROD_NOTIFICATION_IMG_URL.name());
	public static final String NIKE_PROD_NOTIFICATION_PAGE_URL = PROPERTY_READER.getPropertyValue(NotificationServiceEnum.NIKE_PROD_NOTIFICATION_PAGE_URL.name());

	public static final String ADIDAS_PROD_NOTIFICATION_TEXT = PROPERTY_READER.getPropertyValue(NotificationServiceEnum.ADIDAS_PROD_NOTIFICATION_TEXT.name());
	public static final String ADIDAS_PROD_NOTIFICATION_TITLE = PROPERTY_READER.getPropertyValue(NotificationServiceEnum.ADIDAS_PROD_NOTIFICATION_TITLE.name());
	public static final String ADIDAS_PROD_NOTIFICATION_IMG_URL = PROPERTY_READER.getPropertyValue(NotificationServiceEnum.ADIDAS_PROD_NOTIFICATION_IMG_URL.name());
	public static final String ADIDAS_PROD_NOTIFICATION_PAGE_URL = PROPERTY_READER.getPropertyValue(NotificationServiceEnum.ADIDAS_PROD_NOTIFICATION_PAGE_URL.name());
	
	public static final String PROD_VALID_USER_ID = PROPERTY_READER.getPropertyValue(NotificationServiceEnum.PROD_VALID_USER_ID.name());
	public static final String FOX7_VALID_USER_ID = PROPERTY_READER.getPropertyValue(NotificationServiceEnum.FOX7_VALID_USER_ID.name());
	public static final String FUNC_VALID_USER_ID = PROPERTY_READER.getPropertyValue(NotificationServiceEnum.FUNC_VALID_USER_ID.name());
	public static final String PERF_VALID_USER_ID = PROPERTY_READER.getPropertyValue(NotificationServiceEnum.PERF_VALID_USER_ID.name());
	
	public static final String PROD_VALID_USER_IDS = PROPERTY_READER.getPropertyValue(NotificationServiceEnum.PROD_VALID_USER_IDS.name());
	public static final String FOX7_VALID_USER_IDS = PROPERTY_READER.getPropertyValue(NotificationServiceEnum.FOX7_VALID_USER_IDS.name());
	public static final String FUNC_VALID_USER_IDS = PROPERTY_READER.getPropertyValue(NotificationServiceEnum.FUNC_VALID_USER_IDS.name());
	public static final String PERF_VALID_USER_IDS = PROPERTY_READER.getPropertyValue(NotificationServiceEnum.PERF_VALID_USER_IDS.name());
	
}
