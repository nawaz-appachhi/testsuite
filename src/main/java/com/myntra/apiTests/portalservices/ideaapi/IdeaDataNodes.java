package com.myntra.apiTests.portalservices.ideaapi;

/**
 * @author shankara.c
 *
 */
public enum IdeaDataNodes 
{
	IDEA_ENTRY_NODE("entry"),
	IDEA_ENTRY_STATUS_NODE("entry.status"),
	IDEA_ENTRY_DATE_OF_BIRTH_NODE("entry.dob"),
	IDEA_ENTRY_VERIFIED_NODE("entry.verified"),
	IDEA_ENTRY_CHANNEL_NODE("entry.channel"),
	IDEA_ENTRY_ID_NODE("entry.id"),
	IDEA_ENTRY_FIRST_NAME_NODE("entry.firstName"),
	IDEA_ENTRY_LAST_NAME_NODE("entry.lastName"),
	IDEA_ENTRY_GENDER_NODE("entry.gender"),
	IDEA_ENTRY_USER_TYPE_NODE("entry.userType"),
	IDEA_ENTRY_UIDX_NODE("entry.uidx"),
	IDEA_ENTRY_NEW_NODE("entry.new_"),
	IDEA_ENTRY_SHOW_CAPTCHA_NODE("showCaptcha"),
	IDEA_CAUSE_ERROR_CODES_NODE("causeErrorCodes"),
	
	IDEA_TOKEN_ENTRY_NODE("tokenEntry"),
	IDEA_TOKEN_ENTRY_AUTH_TOKEN_NODE("tokenEntry.at"),
	IDEA_TOKEN_ENTRY_REFRESH_TOKEN_NODE("tokenEntry.rt");
	
	private String ideaDataNode;
	
	private IdeaDataNodes(String ideaDataNode)
	{
		this.ideaDataNode = ideaDataNode;
	}
	
	public String getIdeaDataNode() {
		return ideaDataNode;
	}
}
