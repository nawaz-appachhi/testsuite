package com.myntra.apiTests.portalservices.mobileappservices;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shankara.c
 *
 */
public enum MobileIdpServiceDataNodes 
{
	MOBILE_IDP_RESP_META("meta"),
	MOBILE_IDP_RESP_META_CODE("meta.code"),
	MOBILE_IDP_RESP_META_TOKEN("meta.token"),
	MOBILE_IDP_RESP_META_XSRF_TOKEN("meta.xsrfToken"),
	MOBILE_IDP_RESP_META_ERROR_DETAIL("meta.errorDetail"),
	MOBILE_IDP_RESP_META_ERROR_TYPE("meta.errorType"),
	MOBILE_IDP_RESP_META_REQ_ID("meta.requestId"),
	
	MOBILE_IDP_RESP_NOTIFICATION("notification"),
	
	MOBILE_IDP_RESP_DATA("data"),
	MOBILE_IDP_RESP_DATA_LOGIN("data.login"),
	MOBILE_IDP_RESP_DATA_USER_TYPE("data.usertype"),
	MOBILE_IDP_RESP_DATA_TITLE("data.title"),
	MOBILE_IDP_RESP_DATA_FIRST_NAME("data.firstname"),
	MOBILE_IDP_RESP_DATA_LAST_NAME("data.lastname"),
	MOBILE_IDP_RESP_DATA_EMAIL("data.email"),
	MOBILE_IDP_RESP_DATA_PHONE("data.phone"),
	MOBILE_IDP_RESP_DATA_MOBILE("data.mobile"),
	MOBILE_IDP_RESP_DATA_STATUS("data.status"),
	MOBILE_IDP_RESP_DATA_LAST_LOGIN("data.lastlogin"),
	MOBILE_IDP_RESP_DATA_FIRST_LOGIN("data.firstlogin");
	
	
	private String nodePath;
	
	private MobileIdpServiceDataNodes(String nodePath)
	{
		this.nodePath = nodePath;
	}
	
	public String getNodePath() {
		return nodePath;
	}
	
	@Override
	public String toString() {
		return getNodePath();
	}
	
	public static List<String> getMobileIdpSignInResponseMetaTagNodes()
	{
		List<String> mobileIdpMetaTagNodes = new ArrayList<String>();
		mobileIdpMetaTagNodes.add(MOBILE_IDP_RESP_META.getNodePath());
		mobileIdpMetaTagNodes.add(MOBILE_IDP_RESP_META_CODE.getNodePath());
		mobileIdpMetaTagNodes.add(MOBILE_IDP_RESP_META_TOKEN.getNodePath());
		mobileIdpMetaTagNodes.add(MOBILE_IDP_RESP_META_XSRF_TOKEN.getNodePath());
		
		return mobileIdpMetaTagNodes;
	}
	
	public static List<String> getMobileIdpSignInResponseNotificationTagNodes()
	{
		List<String> mobileIdpNotificationTagNodes = new ArrayList<String>();
		mobileIdpNotificationTagNodes.add(MOBILE_IDP_RESP_NOTIFICATION.getNodePath());
		
		return mobileIdpNotificationTagNodes;
	}
	
	public static List<String> getMobileIdpSignInResponseDataTagNodes()
	{
		List<String> mobileIdpDataTagNodes = new ArrayList<String>();
		mobileIdpDataTagNodes.add(MOBILE_IDP_RESP_DATA.getNodePath());
		mobileIdpDataTagNodes.add(MOBILE_IDP_RESP_DATA_LOGIN.getNodePath());
		mobileIdpDataTagNodes.add(MOBILE_IDP_RESP_DATA_USER_TYPE.getNodePath());
		mobileIdpDataTagNodes.add(MOBILE_IDP_RESP_DATA_TITLE.getNodePath());
		mobileIdpDataTagNodes.add(MOBILE_IDP_RESP_DATA_FIRST_NAME.getNodePath());
		mobileIdpDataTagNodes.add(MOBILE_IDP_RESP_DATA_LAST_NAME.getNodePath());
		mobileIdpDataTagNodes.add(MOBILE_IDP_RESP_DATA_EMAIL.getNodePath());
		mobileIdpDataTagNodes.add(MOBILE_IDP_RESP_DATA_PHONE.getNodePath());
		mobileIdpDataTagNodes.add(MOBILE_IDP_RESP_DATA_MOBILE.getNodePath());
		mobileIdpDataTagNodes.add(MOBILE_IDP_RESP_DATA_STATUS.getNodePath());
		mobileIdpDataTagNodes.add(MOBILE_IDP_RESP_DATA_LAST_LOGIN.getNodePath());
		mobileIdpDataTagNodes.add(MOBILE_IDP_RESP_DATA_FIRST_LOGIN.getNodePath());
		
		return mobileIdpDataTagNodes;
	}
	
	public static List<String> getMobileIdpSignInResponseErrorMetaTagNodes() 
	{
		ArrayList<String> errorMetaTagNodes = new ArrayList<String>();
		errorMetaTagNodes.add(MOBILE_IDP_RESP_META.getNodePath());
		errorMetaTagNodes.add(MOBILE_IDP_RESP_META_CODE.getNodePath());
		errorMetaTagNodes.add(MOBILE_IDP_RESP_META_ERROR_DETAIL.getNodePath());
		errorMetaTagNodes.add(MOBILE_IDP_RESP_META_ERROR_TYPE.getNodePath());
		errorMetaTagNodes.add(MOBILE_IDP_RESP_META_TOKEN.getNodePath());
		errorMetaTagNodes.add(MOBILE_IDP_RESP_META_XSRF_TOKEN.getNodePath());
		errorMetaTagNodes.add(MOBILE_IDP_RESP_NOTIFICATION.getNodePath());
		errorMetaTagNodes.add(MOBILE_IDP_RESP_DATA.getNodePath());
		
		return errorMetaTagNodes;
	}
	
	public static List<String> getMobileIdpSignUpResponseMetaTagNodes()
	{
		List<String> mobileIdpMetaTagNodes = new ArrayList<String>();
		mobileIdpMetaTagNodes.add(MOBILE_IDP_RESP_META.getNodePath());
		mobileIdpMetaTagNodes.add(MOBILE_IDP_RESP_META_CODE.getNodePath());
		mobileIdpMetaTagNodes.add(MOBILE_IDP_RESP_META_TOKEN.getNodePath());
		mobileIdpMetaTagNodes.add(MOBILE_IDP_RESP_META_XSRF_TOKEN.getNodePath());
		
		return mobileIdpMetaTagNodes;
	}
	
	public static List<String> getMobileIdpSignUpResponseNotificationTagNodes()
	{
		List<String> mobileIdpNotificationTagNodes = new ArrayList<String>();
		mobileIdpNotificationTagNodes.add(MOBILE_IDP_RESP_NOTIFICATION.getNodePath());
		
		return mobileIdpNotificationTagNodes;
	}
	
	public static List<String> getMobileIdpSignUpResponseDataTagNodes()
	{
		List<String> mobileIdpDataTagNodes = new ArrayList<String>();
		mobileIdpDataTagNodes.add(MOBILE_IDP_RESP_DATA.getNodePath());
		mobileIdpDataTagNodes.add(MOBILE_IDP_RESP_DATA_LOGIN.getNodePath());
		mobileIdpDataTagNodes.add(MOBILE_IDP_RESP_DATA_USER_TYPE.getNodePath());
		mobileIdpDataTagNodes.add(MOBILE_IDP_RESP_DATA_TITLE.getNodePath());
		mobileIdpDataTagNodes.add(MOBILE_IDP_RESP_DATA_FIRST_NAME.getNodePath());
		mobileIdpDataTagNodes.add(MOBILE_IDP_RESP_DATA_LAST_NAME.getNodePath());
		mobileIdpDataTagNodes.add(MOBILE_IDP_RESP_DATA_EMAIL.getNodePath());
		mobileIdpDataTagNodes.add(MOBILE_IDP_RESP_DATA_PHONE.getNodePath());
		mobileIdpDataTagNodes.add(MOBILE_IDP_RESP_DATA_MOBILE.getNodePath());
		mobileIdpDataTagNodes.add(MOBILE_IDP_RESP_DATA_STATUS.getNodePath());
		mobileIdpDataTagNodes.add(MOBILE_IDP_RESP_DATA_LAST_LOGIN.getNodePath());
		mobileIdpDataTagNodes.add(MOBILE_IDP_RESP_DATA_FIRST_LOGIN.getNodePath());
		
		return mobileIdpDataTagNodes;
	}
	
	public static List<String> getMobileIdpSignUpResponseErrorMetaTagNodes() 
	{
		ArrayList<String> errorMetaTagNodes = new ArrayList<String>();
		errorMetaTagNodes.add(MOBILE_IDP_RESP_META.getNodePath());
		errorMetaTagNodes.add(MOBILE_IDP_RESP_META_CODE.getNodePath());
		errorMetaTagNodes.add(MOBILE_IDP_RESP_META_ERROR_DETAIL.getNodePath());
		errorMetaTagNodes.add(MOBILE_IDP_RESP_META_ERROR_TYPE.getNodePath());
		errorMetaTagNodes.add(MOBILE_IDP_RESP_META_TOKEN.getNodePath());
		errorMetaTagNodes.add(MOBILE_IDP_RESP_META_XSRF_TOKEN.getNodePath());
		errorMetaTagNodes.add(MOBILE_IDP_RESP_NOTIFICATION.getNodePath());
		errorMetaTagNodes.add(MOBILE_IDP_RESP_DATA.getNodePath());
		
		return errorMetaTagNodes;
	}
	
}
