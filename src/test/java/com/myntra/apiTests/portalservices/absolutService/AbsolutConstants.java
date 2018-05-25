package com.myntra.apiTests.portalservices.absolutService;

import com.myntra.lordoftherings.Configuration;

public interface AbsolutConstants {
	
	public static final Configuration CONFIGURATION = new Configuration("./Data/configuration");
	//environments
	
	String M7_ENV = "M7";
	String Fox7_ENV ="Fox7";
	String Prod_ENV ="Prod";
	String Stage_ENV = "stage";
	String Sfqa_ENV = "sfqa";
	String Mjint_ENV = "mjint";
	
	// automation users for M7 env -- Cart
	String M7_CartAutoUser1 = "CartAutoUser1@myntra.com";
	String M7_CartAutoUser2 = "CartAutoUser1@gmail.com";
	String M7_CartAutoUser3 = "CartAutoUser2@myntra.com";
	String M7_CartAutoUser4 = "CartAutoUser2@gmail.com";
	String M7_CartCBUser1 = "CartAutoUser3@myntra.com";
	String M7_CartCBUser2 = "CartAutoUser3@gmail.com";
	String M7_CartCouponUser1 = "CartAutoUser4@myntra.com";
	String M7_CartCouponUser2 = "CartAutoUser4@gmail.com";
	String M7_CartGiftWrapUser1 ="CartAutoUser5@myntra.com";
	String M7_CartGiftWrapUser2 ="CartAutoUser5@gmail.com";
	String M7_CartLPUser1 = "CartAutoUser6@myntra.com";
	String M7_CartLPUser2 = "CartAutoUser6@gmail.com";
	String M7_CartWalletUser1 = "CartAutoUser7@myntra.com";
	String M7_CartWalletUser2 = "CartAutoUser7@gmail.com";
	String M7_CartCouponUser3 = "CartAutoUser8@myntra.com";
	String M7_CartCouponUser4 = "CartAutoUser8@gmail.com";
	String M7_CartEGiftCardUser1 = "CartAutoUser9@myntra.com";
	String M7_CartEGiftCardUser2 = "CartAutoUser9@gmail.com";
	String M7_FraudUser1 = "M7FraudUser1@myntra.com";
	String M7_FraudUser2 = "M7FraudUser1@gmail.com";
	
	String Password = "passWoRd213";
	
	//Automation User's of M7 env -- for Negative Cases
	String M7_NegCartAutoUser1 = "NegCartAutoUser1@myntra.com";
	String M7_NegCartAutoUser2 = "NegCartAutoUser1@gmail.com"; //ensure giftwrap is always applied for this user
	String M7_NegCartAutoUser3 = "NegCartAutoUser2@myntra.com";//120%coupon user, try to apply
	String M7_NegCartAutoUser4 = "NegCartAutoUser2@gmail.com"; //100% coupon user 
	String M7_NegCartAutoUser5 = "NegCartAutoUser3@myntra.com";//coupon user applied coupon of 30%
	String M7_NegCartAutoUser6 = "NegCartAutoUser4@myntra.com" ; //Negunit discount user 
	String M7_NegCartAutoUser7 = "NegCartAutoUser5@myntra.com" ; //NegShippig charges user
	String M7_NegCartAutoUser8 = "NegCartAutoUser6@myntra.com" ; //Discount more than MRP
	
	// automation users for stage env -- Cart
	String Stage_CartAutoUser1 = "CartAutoUser11@myntra.com";
	String Stage_CartAutoUser2 = "CartAutoUser1@gmail.com";
	String Stage_CartAutoUser3 = "CartAutoUser2@myntra.com";
	String Stage_CartAutoUser4 = "CartAutoUser2@gmail.com";
	String Stage_CartCBUser1 = "CartAutoUser3@myntra.com";
	String Stage_CartCBUser2 = "CartAutoUser3@gmail.com";
	String Stage_CartCouponUser1 = "CartAutoUser4@myntra.com"; //to be set all coupons users
	String Stage_CartCouponUser2 = "CartAutoUser4@gmail.com";
	String Stage_CartGiftWrapUser1 ="CartAutoUser5@myntra.com";
	String Stage_CartGiftWrapUser2 ="CartAutoUser5@gmail.com";
	String Stage_CartLPUser1 = "CartAutoUser6@myntra.com";
	String Stage_CartLPUser2 = "CartAutoUser6@gmail.com";
	String Stage_CartWalletUser1 = "CartAutoUser7@myntra.com";
	String Stage_CartWalletUser2 = "CartAutoUser7@gmail.com";
	String Stage_CartCouponUser3 = "CartAutoUser8@myntra.com";
	String Stage_CartCouponUser4 = "CartAutoUser8@gmail.com";
	String Stage_CartEGiftCardUser1 = "CartAutoUser9@myntra.com"; //to be
	String Stage_CartEGiftCardUser2 = "CartAutoUser9@gmail.com";
	String Stage_FraudUser1 = "StageFraudUser1@myntra.com";
	String Stage_FraudUser2 = "StageFraudUser1@gmail.com";
	String Stage_ConfigUser1 = "CartAutoUser20@myntra.com";	
	String Stage_ConfigUser2 = "CartAutoUser20@gmail.com";	
	String Stage_VBUser1 = "cartvirtualbundleuser1@gmail.com";
	
	String CartAutoUser = "cartmt@myntra.com";
	
	
	// ########## ALL SUCCESS MESSAGES ##########

	String SUCCESS_STATUS_TYPE="SUCCESS";
	String FAILURE_STATUS_TYPE="ERROR";

	String SUCCESS_STATUS_CODE="3";
	String SUCCESS_STATUS_MSG="Success";

	String SUCCESS_STATUS_RESP ="200";
	
	String ADDR_RET_STATUS_CODE="15002";
	String ADDR_RET_STATUS_MSG="Address(s) retrieved successfully";

	String ADDR_NF_STATUS_CODE="10008";
	String ADDR_NF_STATUS_MSG="Address not found";

		String ADDR_ADD_STATUS_CODE="15001";
		String ADDR_ADD_STATUS_MSG="Address added successfully";

		String ADDR_UPD_STATUS_CODE="15003";
		String ADDR_UPD_STATUS_MSG="Address updated successfully";

		String ADDR_DEL_STATUS_CODE="15004";
		String ADDR_DEL_STATUS_MSG="Address deleted successfully";

		//########## ALL FAILURE MESSAGES ##########

		String NOT_AUTH_STATUS_CODE="110001";
		String NOT_AUTH_STATUS_MSG="Not Authorized to perform the operation";

		String INV_ADDR_STATUS_CODE="10001";
		String INV_ADDR_STATUS_MSG="Invalid Address";

		String INV_CITY_STATUS_CODE="10003";
		String INV_CITY_STATUS_MSG="Invalid City";

		String INV_CNTRY_STATUS_CODE="10004";
		String INV_CNTRY_STATUS_MSG="Invalid Country";

		String INV_ST_STATUS_CODE="10006";
		String INV_ST_STATUS_MSG="Invalid Statecode";

		String ERROR_STATUS_CODE="58";
		String ERROR_STATUS_MSG="Error occurred while updating/processing data";

		// ########## COMMON SUCCESS RESPONSE CODE ##########

		String VALID_RESP_CODE="200";
		String INVALID_RESP_CODE = "500";

		String EMPTY_VALUE="0";

		String INV_ITEM_QTY="0";

		String NEG_INV_ITEM_QTY="-1";

		String TRUE_VALUE="true";

		String USE_CREDIT_AMT="200";

		String FALSE_VALUE="false";

		String ADD_OPERATION="ADD";

		String UPDATE_OPERATION="UPDATE";

		String DELETE_OPERATION="DELETE";


}
