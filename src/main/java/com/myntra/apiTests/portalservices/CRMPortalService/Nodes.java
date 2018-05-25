package com.myntra.apiTests.portalservices.CRMPortalService;

import org.testng.AssertJUnit;



public enum Nodes {
	
	//Customer Profile
	STATUS_CODE_PROFILE("portalProfileResponse.status.statusCode"),
	STATUS_TYPE_PROFILE("portalProfileResponse.status.statusType"),
	STATUS_TOTAL_COUNT_PROFILE("portalProfileResponse.status.totalCount"),
	DOB("portalProfileResponse.portalProfileEntry.dob"),
	EMAIL("portalProfileResponse.customerProfileEntry.email"),
	FIRSTLOGIN("portalProfileResponse.portalProfileEntry.firstLogin"),
	MOBILE("portalProfileResponse.portalProfileEntry.mobile"),
	STATUS("portalProfileResponse.portalProfileEntry.status"),
	
	//Return
	STATUS_CODE("returnResponse.status.statusCode"),
	STATUS_TYPE("returnResponse.status.statusType"),
	STATUS_TOTAL_COUNT("returnResponse.status.totalCount"),
	RETURN_CITY("returnResponse.returnEntryList.returnEntry.city"),
	COURIER_SERVICE("returnResponse.returnEntryList.returnEntry.courierService"),
	ORDER_ID("returnResponse.returnEntryList.returnEntry.orderId"),
	QUANTITY("returnResponse.returnEntryList.returnEntry.quantity"),
	RETURNCREATEDDATE("returnResponse.returnEntryList.returnEntry.returnCreatedDate"),
    RETURNID("returnResponse.returnEntryList.returnEntry.returnId"),
    RETURNMODE("returnResponse.returnEntryList.returnEntry.returnMode"),
    RETURNSTATUS("returnResponse.returnEntryList.returnEntry.returnStatus"),
    SKUCODE("returnResponse.returnEntryList.returnEntry.skuCode"),
    TRACKINGNUMBER("returnResponse.returnEntryList.returnEntry.trackingNumber"),
    ZIPCODE("returnResponse.returnEntryList.returnEntry.zipCode"),
	
	
	//Coupon
	STATUS_CODE_COUPON("couponResponse.status.statusCode"),
	STATUS_TYPE_COUPON("couponResponse.status.statusType"),
	STATUS_TOTAL_COUPON("couponResponse.status.totalCount"),
	COUPON("couponResponse.couponEntryList.couponEntry.coupon"),
	COUPON_STATUS("couponResponse.couponEntryList.couponEntry.couponStatus"),
	COUPON_TYPE("couponResponse.couponEntryList.couponEntry.couponType"),
	COUPON_MINIMUM("couponResponse.couponEntryList.couponEntry.minimum"),
	COUPON_MRPAMOUNT("couponResponse.couponEntryList.couponEntry.mrpAmount"),
	COUPON_MRPPERCENTAGE("couponResponse.couponEntryList.couponEntry.mrpPercentage"),
	COUPON_PER_USER("couponResponse.couponEntryList.couponEntry.per_user"),
	COUPON_TIMES("couponResponse.couponEntryList.couponEntry.times"),
	
	//Comment
	STATUS_CODE_COMMENT("customerOrderCommentResponse.status.statusCode"),
	STATUS_TYPE_COMMENT("customerOrderCommentResponse.status.statusType"),
	
	//ONHOLD
	STATUS_CODE_ONHOLD("codOnHoldLogResponse.status.statusCode"),
	STATUS_TYPE_ONHOLD("codOnHoldLogResponse.status.statusType"),
	STATUS_TOTAL_COUNT_ONHOLD("codOnHoldLogResponse.status.totalCount"),
	ONHOLD_DISPOSITION("codOnHoldLogResponse.codOnHoldLogEntryList.codOnHoldLogEntry.disposition"),
	ONHOLD_ORDEID("codOnHoldLogResponse.codOnHoldLogEntryList.codOnHoldLogEntry.orderId"),
	ONHOLD_REASON("codOnHoldLogResponse.codOnHoldLogEntryList.codOnHoldLogEntry.reason"),
	
	//EXCHANGE
	STATUS_CODE_EXCHANGE("orderExchangeResponse.status.statusCode"),
	STATUS_TYPE_EXCHANGE("orderExchangeResponse.status.statusType"),
	
	/*
	* CRM ERP
	*/
	
	//ORDER
	STATUS_CODE_ORDER("customerOrderResponse.status.statusCode"),
	STATUS_TYPE_ORDER("customerOrderResponse.status.statusType"),
	TOTALCOUNT_ORDER("customerOrderResponse.status.totalCount"),
	ORDER_ORDERID("customerOrderEntryList.customerOrderEntry.orderId"),
	//"customerOrderEntryList.customerOrderEntry.customerOrderBillingAddress.
	
	//Cashback
	STATUS_CODE_CASHBACK("cashbackResponse.status.statusCode"),
	STATUS_TYPE_CASHBACK("cashbackResponse.status.statusType"),
	TOTALCOUNT_CASHBACK("cashbackResponse.status.totalCount"),
	CASHBACK_BALANCEENTRY("cashbackResponse.cashbackBalanceEntry.cashbackBalance"),
	CASHBACK_LOGENTRYLIST("cashbackResponse.cashbackLogEntryList");
	
	
	private String nodePath;
	
	private Nodes(String nodePath) {
		this.nodePath = nodePath;
	}

	public String getNodePath() {
		return nodePath;
	}

	@Override
	public String toString() {
		return getNodePath();
	}

}
