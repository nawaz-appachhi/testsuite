package com.myntra.apiTests.portalservices.checkoutservice;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shankara.c
 *
 */
public enum MyntCouponNodes {

	COUPON_ID("data.coupon"),
    COUPON_EXP_DATE("data.expiryDate"),
    COUPON_CONDITION_MIN("data.conditionMin"),
    COUPON_CONDITION_MAX("data.conditionMax"),
    COUPON_AMT("data.couponAmount"),
    COUPON_PERCENT("data.couponPercentage"),
    COUPON_MIN_MORE("data.minMore"),
    COUPON_BENEFIT("data.benefit"),
    COUPON_NOT_APP_PROD("data.notApplicableProducts"),
    COUPON_APP_MSG("data.couponApplicabilityMessage"),
    COUPON_DISP_MSG("data.displayType"),
    COUPON_TYPE("data.couponType"),
    COUPON_DESC_TXT("data.couponDescriptionText"),
    COUPON_APPLICABLE("data.applicable");
	
	private String nodePath;
	
	private MyntCouponNodes(String nodePath){
		this.nodePath = nodePath;
	}
    
    public String getNodePath() {
		return nodePath;
	}
    
    @Override
    public String toString() {
    	return getNodePath();
    }
    
    public static List<String> getCouponNodes(){
    	List<String> couponList = new ArrayList<String>();
    	couponList.add(COUPON_ID.getNodePath());
    	couponList.add(COUPON_EXP_DATE.getNodePath());
    	couponList.add(COUPON_CONDITION_MIN.getNodePath());
    	couponList.add(COUPON_CONDITION_MAX.getNodePath());
    	couponList.add(COUPON_AMT.getNodePath());
    	couponList.add(COUPON_PERCENT.getNodePath());
    	couponList.add(COUPON_MIN_MORE.getNodePath());
    	couponList.add(COUPON_BENEFIT.getNodePath());
    	//couponList.add(COUPON_NOT_APP_PROD.getNodePath());
    	//couponList.add(COUPON_APP_MSG.getNodePath());
    	couponList.add(COUPON_DISP_MSG.getNodePath());
    	couponList.add(COUPON_TYPE.getNodePath());
    	couponList.add(COUPON_DESC_TXT.getNodePath());
    	couponList.add(COUPON_APPLICABLE.getNodePath());
    	
    	return couponList;
    }
    
}
