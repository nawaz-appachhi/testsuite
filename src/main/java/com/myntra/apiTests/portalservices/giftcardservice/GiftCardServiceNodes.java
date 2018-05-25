/**
 * 
 */
package com.myntra.apiTests.portalservices.giftcardservice;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shankara.c
 *
 */
public enum GiftCardServiceNodes 
{
	GIFT_CARD_DATA("data"),
	GIFT_CARD_DATA_AMOUNT("data.amount"),
	GIFT_CARD_TXN_ID("data.transactionId");
	
	private String nodePath;
	
	private GiftCardServiceNodes(String nodePath){
		this.nodePath = nodePath;
	}
	
	public String getNodePath() {
		return nodePath;
	}
	
	@Override
	public String toString() {
		return getNodePath();
	}
	
	public static List<String> getGiftCardNodes(){
		List<String> giftCardNodes = new ArrayList<String>();
		giftCardNodes.add(GIFT_CARD_DATA.getNodePath());
		giftCardNodes.add(GIFT_CARD_DATA_AMOUNT.getNodePath());
		
		return giftCardNodes;
	}
	
	public static List<String> getRedeemGiftCardNodes(){
		List<String> giftCardNodes = new ArrayList<String>();
		giftCardNodes.add(GIFT_CARD_DATA.getNodePath());
		giftCardNodes.add(GIFT_CARD_DATA_AMOUNT.getNodePath());
		giftCardNodes.add(GIFT_CARD_TXN_ID.getNodePath());
		
		return giftCardNodes;
	}
	
}
