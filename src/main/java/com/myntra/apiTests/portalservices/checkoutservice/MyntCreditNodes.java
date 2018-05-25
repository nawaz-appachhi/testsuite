package com.myntra.apiTests.portalservices.checkoutservice;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shankara.c
 *
 */
public enum MyntCreditNodes {
	
	STORE_CREDIT_BAL("data.storeCreditBalance"),
    EARNED_CREDIT_BAL("data.earnedCreditBalance"),
    TOT_BAL("data.balance"),
    USE_AMT("data.useAmount");
	
	private String nodePath;
	
	private MyntCreditNodes(String nodePath){
		this.nodePath = nodePath;
	}
	
	public String getNodePath() {
		return nodePath;
	}
	
	@Override
	public String toString() {
		return getNodePath();
	}
	
	public static List<String> getCreditNodes(){
		List<String> creditNodesList = new ArrayList<String>();
		creditNodesList.add(STORE_CREDIT_BAL.getNodePath());
		creditNodesList.add(EARNED_CREDIT_BAL.getNodePath());
		creditNodesList.add(TOT_BAL.getNodePath());
		creditNodesList.add(USE_AMT.getNodePath());
		
		return creditNodesList;
	}
}
