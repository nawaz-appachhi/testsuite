package com.myntra.apiTests.portalservices.idpservice;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shankara.c
 *
 */
public enum UserLoginNodes {
	
	USER("user"),
	USER_LOGIN("user.login"),
    USER_TYPE("user.usertype"),
    USER_UIDX("user.uidx"),
    USER_EMAIL("user.email"),
    USER_MOBILE("user.mobile"),
    USER_STATUS("user.status"),
    USER_LAST_LOGIN("user.lastlogin"),
    USER_FIRST_LOGIN("user.firstlogin");
	
	private String nodePath;
	
	private UserLoginNodes(String nodePath){
		this.nodePath = nodePath;
	}
	
	public String getNodePath() {
		return nodePath;
	}

	@Override
	public String toString() {
		return nodePath;
	}
	
	public static List<String> getUserLoginNodes(){
		List<String> userLoginNodes = new ArrayList<String>();
		userLoginNodes.add(USER.getNodePath());
		userLoginNodes.add(USER_LOGIN.getNodePath());
		userLoginNodes.add(USER_TYPE.getNodePath());
		userLoginNodes.add(USER_UIDX.getNodePath());
		userLoginNodes.add(USER_EMAIL.getNodePath());
		userLoginNodes.add(USER_MOBILE.getNodePath());
		userLoginNodes.add(USER_STATUS.getNodePath());
		userLoginNodes.add(USER_LAST_LOGIN.getNodePath());
		userLoginNodes.add(USER_FIRST_LOGIN.getNodePath());
		
		return userLoginNodes;
	}
	
}
