package com.myntra.apiTests.portalservices.commons;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shankara.c
 *
 */
public enum StatusNodes
{
	STATUS_CODE("status.statusCode"),
	STATUS_MESSAGE("status.statusMessage"),
	STATUS_TYPE("status.statusType"),
	STATUS_TOTAL_COUNT("status.totalCount");
	
	private String nodePath;
	
	private StatusNodes(String nodePath) {
		this.nodePath = nodePath;
	}

	public String getNodePath() {
		return nodePath;
	}

	@Override
	public String toString() {
		return getNodePath();
	}
	
	public static List<String> getStatusNodes()
	{
		List<String> statusNodes = new ArrayList<String>();
		statusNodes.add(STATUS_CODE.getNodePath());
		statusNodes.add(STATUS_MESSAGE.getNodePath());
		statusNodes.add(STATUS_TYPE.getNodePath());
		statusNodes.add(STATUS_TOTAL_COUNT.getNodePath());
		
		return statusNodes;		
	
	}
	
}
