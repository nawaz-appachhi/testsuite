package com.myntra.apiTests.portalservices.lgpservices;

import java.util.ArrayList;
import java.util.List;

public enum LgpServicesDataNodes {
	
	//LOOKS API
	LOOKS_CREATE_RESP_DATA_LOOKID("data.id"),
	LOOKS_CREATE_RESP_DATA_TITLE("data.title"),
	LOOKS_CREATE_RESP_DATA_DESCRIPTION("data.description"),
	LOOKS_CREATE_RESP_DATA_ISACTIVE("data.isActive"),
	LOOKS_CREATE_RESP_DATA_ISDRAFT("data.isDraft"),
	LOOKS_CREATE_RESP_DATA_ISREMYX("data.isRemyx");
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	private String nodePath;
	
	private LgpServicesDataNodes(String nodePath)
	{
		this.nodePath = nodePath;
	}
	
	
	@Override
	public String toString() {
		return nodePath;
	}
    
		
	public static List<String> getCreateLookDataTagNodes()
	{
		List<String> createLookDataTagNodes = new ArrayList<String>();
		createLookDataTagNodes.add(LOOKS_CREATE_RESP_DATA_LOOKID.toString());
		createLookDataTagNodes.add(LOOKS_CREATE_RESP_DATA_TITLE.toString());
		createLookDataTagNodes.add(LOOKS_CREATE_RESP_DATA_DESCRIPTION.toString());
		createLookDataTagNodes.add(LOOKS_CREATE_RESP_DATA_ISACTIVE.toString());
		createLookDataTagNodes.add(LOOKS_CREATE_RESP_DATA_ISDRAFT.toString());
		createLookDataTagNodes.add(LOOKS_CREATE_RESP_DATA_ISREMYX.toString());
		return createLookDataTagNodes;
		
	}
}
