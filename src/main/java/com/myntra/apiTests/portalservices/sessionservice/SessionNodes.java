package com.myntra.apiTests.portalservices.sessionservice;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shankara.c
 *
 */
public enum SessionNodes
{
	SESSION_INFO("session"),
	SESSION_CREATE_DATE("session.creation_date"),
	SESSION_ID("session.id"),
	SESSION_LAST_MODIFIED_DATE("session.last_modified_date"),
	SESSION_LAST_ACCESSED_DATE("session.last_accessed_date"),
	SESSION_LINK("session.link"),
	SESSION_EXPIRY_DATE("session.expiry_date"),
	SESSION_UPDATED_FLAG("session.updated_flag"),
	SESSION_DATA("session.data"),
	
	SESSION_DATA_USER_FIRST_NAME("userfirstname"),
	SESSION_DATA_USER_LAST_NAME("userlastname"),
	SESSION_DATA_USER_TOKEN("USER_TOKEN"),
	SESSION_DATA_LOGIN("login"),
	SESSION_DATA_SXID("sxid"),
	SESSION_DATA_PROMPT_LOGIN("prompt_login"),
	SESSION_DATA_ACCOUNT_TYPE("account_type"),
	SESSION_DATA_MOBILE("mobile"),
	SESSION_DATA_LOGIN_TYPE("login_type"),
	SESSION_DATA_IDENTIFIERS("identifiers"),
	SESSION_DATA_IDENTIFIERS_C("identifiers.C"),
	SESSION_DATA_IDENTIFIERS_C_LOGIN("identifiers.C.login"),
	SESSION_DATA_IDENTIFIERS_C_FIRST_NAME("identifiers.C.firstname"),
	SESSION_DATA_IDENTIFIERS_C_FIRST_LOGIN_DATE("identifiers.C.first_login_date"),
	SESSION_DATA_IDENTIFIERS_C_LOGIN_TYPE("identifiers.C.login_type"),
	SESSION_DATA_IDENTIFIERS_C_ACCOUNT_TYPE("identifiers.C.account_type");
	
	private String nodePath;
	
	private SessionNodes(String nodePath)
	{
		this.nodePath = nodePath;
	}
	
	public String getNodePath() 
	{
		return nodePath;
	}
	
	@Override
	public String toString()
	{
		return getNodePath();
	}
	
	public static List<String> getSessionNodes()
	{
		List<String> sessionNodes = new ArrayList<String>();
		sessionNodes.add(SESSION_INFO.getNodePath());
		sessionNodes.add(SESSION_CREATE_DATE.getNodePath());
		sessionNodes.add(SESSION_ID.getNodePath());
		sessionNodes.add(SESSION_LAST_MODIFIED_DATE.getNodePath());
		sessionNodes.add(SESSION_LAST_ACCESSED_DATE.getNodePath());
		sessionNodes.add(SESSION_LINK.getNodePath());
		sessionNodes.add(SESSION_EXPIRY_DATE.getNodePath());
		sessionNodes.add(SESSION_UPDATED_FLAG.getNodePath());
		sessionNodes.add(SESSION_DATA.getNodePath());
		return sessionNodes;	
	}
	
	public static List<String> getSessionCDATANodes()
	{
		List<String> sessionCDATANodes = new ArrayList<String>();
		sessionCDATANodes.add(SESSION_DATA_USER_FIRST_NAME.getNodePath());
		sessionCDATANodes.add(SESSION_DATA_USER_LAST_NAME.getNodePath());
		sessionCDATANodes.add(SESSION_DATA_USER_TOKEN.getNodePath());
		sessionCDATANodes.add(SESSION_DATA_LOGIN.getNodePath());
		sessionCDATANodes.add(SESSION_DATA_SXID.getNodePath());
		sessionCDATANodes.add(SESSION_DATA_PROMPT_LOGIN.getNodePath());
		sessionCDATANodes.add(SESSION_DATA_ACCOUNT_TYPE.getNodePath());
		sessionCDATANodes.add(SESSION_DATA_MOBILE.getNodePath());
		sessionCDATANodes.add(SESSION_DATA_LOGIN_TYPE.getNodePath());
		sessionCDATANodes.add(SESSION_DATA_IDENTIFIERS.getNodePath());
		sessionCDATANodes.add(SESSION_DATA_IDENTIFIERS_C.getNodePath());
		sessionCDATANodes.add(SESSION_DATA_IDENTIFIERS_C_LOGIN.getNodePath());
		sessionCDATANodes.add(SESSION_DATA_IDENTIFIERS_C_FIRST_NAME.getNodePath());
		sessionCDATANodes.add(SESSION_DATA_IDENTIFIERS_C_FIRST_LOGIN_DATE.getNodePath());
		sessionCDATANodes.add(SESSION_DATA_IDENTIFIERS_C_LOGIN_TYPE.getNodePath());
		sessionCDATANodes.add(SESSION_DATA_IDENTIFIERS_C_ACCOUNT_TYPE.getNodePath());
		return sessionCDATANodes;	
	}
	
	
}
