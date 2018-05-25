package com.myntra.apiTests.portalservices.absolutService;

import org.testng.ITestContext;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;

import com.myntra.apiTests.dataproviders.devapis.StyleTestsDP;
import com.myntra.apiTests.portalservices.checkoutservice.CheckoutDataProviderEnum;
import com.myntra.apiTests.portalservices.checkoutservice.CheckoutServiceConstants;
import com.myntra.apiTests.portalservices.commons.CommonUtils;
import com.myntra.lordoftherings.Configuration;
import com.myntra.lordoftherings.Toolbox;

public class CartServiceTestsDP extends CommonUtils implements AbsolutConstants{
	public static String environment;
	Configuration con = new Configuration("./Data/configuration");
	public CartServiceTestsDP() 
	{
		if (System.getenv("ENVIRONMENT") == null)
			environment = con.GetTestEnvironemnt().toString();
		else
			environment = System.getenv("ENVIRONMENT");
	}

	//	public CartServiceTestsDP()
	//	{
	//		environment = CONFIGURATION.GetTestEnvironemnt().toString();
	//		System.out.println("env name:"+environment);
	//	}

	@DataProvider
	public static Object[][] addItemToCart_verifyAPIIsUpDP(ITestContext testContext)
	{
		String[] arr1 = { M7_ENV, M7_CartAutoUser1, Password, "3827", SUCCESS_STATUS_RESP,SUCCESS_STATUS_MSG,SUCCESS_STATUS_TYPE};
		String[] arr2 = { M7_ENV, M7_CartAutoUser2, Password, "3827", SUCCESS_STATUS_RESP,SUCCESS_STATUS_MSG,SUCCESS_STATUS_TYPE};
		String[] arr3 = { Stage_ENV, Stage_CartAutoUser1, Password, "3827", SUCCESS_STATUS_RESP,SUCCESS_STATUS_MSG,SUCCESS_STATUS_TYPE};
		String[] arr4 = { Stage_ENV, Stage_CartAutoUser2, Password, "3827", SUCCESS_STATUS_RESP,SUCCESS_STATUS_MSG,SUCCESS_STATUS_TYPE};

		Object[][] dataSet = new Object[][] { arr1, arr2,arr3,arr4};
		return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 2, 2);
	}

	@DataProvider
	public static Object[][] fetchCart_verifyAPIIsUpDP(ITestContext testContext)
	{
		String[] arr1 = { M7_ENV, M7_CartAutoUser1, Password, SUCCESS_STATUS_RESP,SUCCESS_STATUS_MSG,SUCCESS_STATUS_TYPE};
		String[] arr2 = { M7_ENV, M7_CartAutoUser2, Password, SUCCESS_STATUS_RESP,SUCCESS_STATUS_MSG,SUCCESS_STATUS_TYPE};
		String[] arr3 = { Stage_ENV, Stage_CartAutoUser1, Password, SUCCESS_STATUS_RESP,SUCCESS_STATUS_MSG,SUCCESS_STATUS_TYPE};
		String[] arr4 = { Stage_ENV, Stage_CartAutoUser2, Password, SUCCESS_STATUS_RESP,SUCCESS_STATUS_MSG,SUCCESS_STATUS_TYPE};

		Object[][] dataSet = new Object[][] { arr1, arr2,arr3,arr4};
		return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 2, 2);
	}

	@DataProvider
	public static Object[][] clearCart_verifyAPIIsUpDP(ITestContext testContext)
	{
		String[] arr1 = { M7_ENV, M7_CartAutoUser1, Password};
		String[] arr2 = { M7_ENV, M7_CartAutoUser2, Password};
		String[] arr3 = { Stage_ENV, Stage_CartAutoUser1, Password};
		String[] arr4 = { Stage_ENV, Stage_CartAutoUser2, Password};

		Object[][] dataSet = new Object[][] { arr1, arr2,arr3,arr4};
		return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 2, 2);
	}
	@DataProvider
	public static  Object[][] updateItemInCart_verifyAPIIsUpDP(ITestContext testContext)
	{
		String[] arr1 = { M7_ENV, M7_CartAutoUser1, Password, "3828","123123", SUCCESS_STATUS_RESP};
		String[] arr2 = { M7_ENV, M7_CartAutoUser2, Password, "3827", "456451", SUCCESS_STATUS_RESP};
		String[] arr3 = { Stage_ENV, Stage_CartAutoUser1, Password, "3828","123123", SUCCESS_STATUS_RESP};
		String[] arr4 = { Stage_ENV, Stage_CartAutoUser2, Password, "3827", "456451", SUCCESS_STATUS_RESP};

		Object[][] dataSet = new Object[][] { arr1, arr2,arr3,arr4};
		return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 2, 2);
	}
	@DataProvider
	public static Object[][] removeItemFromCart_verifySuccessResponseDP(ITestContext testContext)
	{
		String[] arr1 = { M7_ENV, M7_CartAutoUser1, Password, "DELETE", SUCCESS_STATUS_RESP};
		String[] arr2 = { M7_ENV, M7_CartAutoUser2, Password, "DELETE", SUCCESS_STATUS_RESP};
		String[] arr3 = { Stage_ENV, Stage_CartAutoUser1, Password, "DELETE", SUCCESS_STATUS_RESP};
		String[] arr4 = { Stage_ENV, Stage_CartAutoUser2, Password, "DELETE", SUCCESS_STATUS_RESP};

		Object[][] dataSet = new Object[][] { arr1, arr2,arr3,arr4};
		return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 2, 2);
	}

	@DataProvider
	public static Object[][] addEGiftCardToCart_verifyAPIIsUpDP(ITestContext testContext)
	{
		String[] arr1 = { M7_ENV, M7_CartEGiftCardUser1, Password, SUCCESS_STATUS_RESP,SUCCESS_STATUS_MSG,SUCCESS_STATUS_TYPE};
		String[] arr2 = { M7_ENV, M7_CartEGiftCardUser2, Password, SUCCESS_STATUS_RESP,SUCCESS_STATUS_MSG,SUCCESS_STATUS_TYPE};
		String[] arr3 = { M7_ENV, M7_CartEGiftCardUser1, Password, SUCCESS_STATUS_RESP,SUCCESS_STATUS_MSG,SUCCESS_STATUS_TYPE};
		String[] arr4 = { M7_ENV, M7_CartEGiftCardUser2, Password, SUCCESS_STATUS_RESP,SUCCESS_STATUS_MSG,SUCCESS_STATUS_TYPE};

		Object[][] dataSet = new Object[][] { arr1, arr2,arr3,arr4};
		return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 2, 2);
	}

	@DataProvider
	public static Object[][] addItemToCart_verifyItemIsAddedDP(ITestContext testContext)
	{
		String[] arr1 = { M7_ENV, M7_CartAutoUser1, Password, "8503", SUCCESS_STATUS_RESP,SUCCESS_STATUS_MSG,SUCCESS_STATUS_TYPE};
		String[] arr2 = { M7_ENV, M7_CartAutoUser2, Password, "3816", SUCCESS_STATUS_RESP,SUCCESS_STATUS_MSG,SUCCESS_STATUS_TYPE};
		String[] arr3 = { M7_ENV, M7_CartAutoUser1, Password, "8503", SUCCESS_STATUS_RESP,SUCCESS_STATUS_MSG,SUCCESS_STATUS_TYPE};
		String[] arr4 = { M7_ENV, M7_CartAutoUser2, Password, "3816", SUCCESS_STATUS_RESP,SUCCESS_STATUS_MSG,SUCCESS_STATUS_TYPE};

		Object[][] dataSet = new Object[][] { arr1, arr2,arr3,arr4};
		return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 2, 2);
	}

	@DataProvider
	public static Object[][] updateItemInCart_verifyItemQtyIsUpdatedDP(ITestContext testContext)
	{
		String[] arr1 = { M7_ENV, M7_CartAutoUser1, Password,"3828","3867", "3828","2","UPDATE", SUCCESS_STATUS_RESP};
		String[] arr2 = { M7_ENV, M7_CartAutoUser2, Password, "3827","3868","3868","2","UPDATE", SUCCESS_STATUS_RESP};
		String[] arr3 = { Stage_ENV, Stage_CartAutoUser1, Password,"3828","3867", "3828","2","UPDATE", SUCCESS_STATUS_RESP};
		String[] arr4 = { Stage_ENV, Stage_CartAutoUser2, Password, "3827","3868","3868","2","UPDATE", SUCCESS_STATUS_RESP};

		Object[][] dataSet = new Object[][] { arr1, arr2,arr3,arr4};
		return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 2, 2);
	}
	@DataProvider
	public static Object[][] removeItemFromCartAPI_verifyItemIsRemovedDP(ITestContext testContext)
	{	

		String[] arr1 = { M7_ENV, M7_CartAutoUser1, Password, "3828","3867", "3828"};
		String[] arr2 = { M7_ENV, M7_CartAutoUser2, Password, "3828","3867", "3828"};
		String[] arr3 = { Stage_ENV, Stage_CartAutoUser1, Password, "3828","3867", "3828"};
		String[] arr4 = { Stage_ENV, Stage_CartAutoUser2, Password, "3828","3867", "3828"};

		Object[][] dataSet = new Object[][] { arr1, arr2,arr3,arr4};
		return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 2, 2);
	}

	@DataProvider
	public static Object[][] clearCartAPI_verifyCartIsClearedDP(ITestContext testContext)
	{
		String[] arr1 = { M7_ENV, M7_CartAutoUser1, Password };
		String[] arr2 = { M7_ENV, M7_CartAutoUser2, Password };
		String[] arr3 = { Stage_ENV, Stage_CartAutoUser1, Password };
		String[] arr4 = { Stage_ENV, Stage_CartAutoUser2, Password };

		Object[][] dataSet = new Object[][] { arr1, arr2,arr3,arr4};
		return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 2, 2);
	}

	@DataProvider
	public static Object[][] fetchCartByContextDP(ITestContext testContext)
	{
		String[] arr1 = { M7_ENV, M7_CartAutoUser1, Password, "default",SUCCESS_STATUS_RESP,SUCCESS_STATUS_MSG,SUCCESS_STATUS_TYPE};
		String[] arr2 = { M7_ENV, M7_CartAutoUser1, Password, "address/default",SUCCESS_STATUS_RESP,SUCCESS_STATUS_MSG,SUCCESS_STATUS_TYPE};
		String[] arr3 = { M7_ENV, M7_CartAutoUser1, Password, "payment/default",SUCCESS_STATUS_RESP,SUCCESS_STATUS_MSG,SUCCESS_STATUS_TYPE};

		String[] arr4 = { M7_ENV, M7_CartAutoUser2, Password, "default",SUCCESS_STATUS_RESP,SUCCESS_STATUS_MSG,SUCCESS_STATUS_TYPE};
		String[] arr5 = { M7_ENV, M7_CartAutoUser2, Password, "address/default",SUCCESS_STATUS_RESP,SUCCESS_STATUS_MSG,SUCCESS_STATUS_TYPE};
		String[] arr6 = { M7_ENV, M7_CartAutoUser2, Password, "payment/default",SUCCESS_STATUS_RESP,SUCCESS_STATUS_MSG,SUCCESS_STATUS_TYPE};

		String[] arr7 = { M7_ENV, M7_CartAutoUser1, Password, "default?rt=true",SUCCESS_STATUS_RESP,SUCCESS_STATUS_MSG,SUCCESS_STATUS_TYPE};
		String[] arr8 = { M7_ENV, M7_CartAutoUser2, Password, "default?rt=true",SUCCESS_STATUS_RESP,SUCCESS_STATUS_MSG,SUCCESS_STATUS_TYPE};

		String[] arr9 = { M7_ENV, M7_CartAutoUser1, Password, "EGIFTCARD",SUCCESS_STATUS_RESP,SUCCESS_STATUS_MSG,SUCCESS_STATUS_TYPE};
		String[] arr10 = { M7_ENV, M7_CartAutoUser2, Password, "EGIFTCARD",SUCCESS_STATUS_RESP,SUCCESS_STATUS_MSG,SUCCESS_STATUS_TYPE};
		String[] arr11 = { Stage_ENV, Stage_CartAutoUser1, Password, "default",SUCCESS_STATUS_RESP,SUCCESS_STATUS_MSG,SUCCESS_STATUS_TYPE};
		String[] arr12 = { Stage_ENV, Stage_CartAutoUser1, Password, "address/default",SUCCESS_STATUS_RESP,SUCCESS_STATUS_MSG,SUCCESS_STATUS_TYPE};
		String[] arr13 = { Stage_ENV, Stage_CartAutoUser1, Password, "payment/default",SUCCESS_STATUS_RESP,SUCCESS_STATUS_MSG,SUCCESS_STATUS_TYPE};

		String[] arr14 = { Stage_ENV, Stage_CartAutoUser2, Password, "default",SUCCESS_STATUS_RESP,SUCCESS_STATUS_MSG,SUCCESS_STATUS_TYPE};
		String[] arr15 = { Stage_ENV, Stage_CartAutoUser2, Password, "address/default",SUCCESS_STATUS_RESP,SUCCESS_STATUS_MSG,SUCCESS_STATUS_TYPE};
		String[] arr16 = { Stage_ENV, Stage_CartAutoUser2, Password, "payment/default",SUCCESS_STATUS_RESP,SUCCESS_STATUS_MSG,SUCCESS_STATUS_TYPE};

		String[] arr17 = { Stage_ENV, Stage_CartAutoUser1, Password, "default?rt=true",SUCCESS_STATUS_RESP,SUCCESS_STATUS_MSG,SUCCESS_STATUS_TYPE};
		String[] arr18 = { Stage_ENV, Stage_CartAutoUser2, Password, "default?rt=true",SUCCESS_STATUS_RESP,SUCCESS_STATUS_MSG,SUCCESS_STATUS_TYPE};

		String[] arr19 = { Stage_ENV, Stage_CartAutoUser1, Password, "EGIFTCARD",SUCCESS_STATUS_RESP,SUCCESS_STATUS_MSG,SUCCESS_STATUS_TYPE};
		String[] arr20 = { Stage_ENV, Stage_CartAutoUser2, Password, "EGIFTCARD",SUCCESS_STATUS_RESP,SUCCESS_STATUS_MSG,SUCCESS_STATUS_TYPE};

		Object[][] dataSet = new Object[][] { arr1, arr2,arr3,arr4,arr5,arr6,arr7,arr8,arr9,arr10,arr11, arr12,arr13,arr14,arr15,arr16,arr17,arr18,arr19,arr20};
		return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 10, 10);
	}

	@DataProvider
	public static Object[][] addGiftWrapAndMessage_verifyAPIIsUpDP(ITestContext testContext)
	{
		String[] arr1 = { M7_ENV, M7_CartGiftWrapUser1, Password, "test","best wishes","testing",SUCCESS_STATUS_RESP};
		String[] arr2 = { M7_ENV, M7_CartGiftWrapUser2, Password, "testUser","greetings","test",SUCCESS_STATUS_RESP};
		String[] arr3 = { Stage_ENV, Stage_CartGiftWrapUser1, Password, "test","best wishes","testing",SUCCESS_STATUS_RESP};
		String[] arr4 = { Stage_ENV, Stage_CartGiftWrapUser2, Password, "testUser","greetings","test",SUCCESS_STATUS_RESP};

		Object[][] dataSet = new Object[][] { arr1, arr2,arr3,arr4};
		return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 2, 2);
	}
	@DataProvider
	public static Object[][] addGiftWrapAndMessage_verifyGiftWrapIsAppliedDP(ITestContext testContext)
	{
		String[] arr1 = { M7_ENV, M7_CartGiftWrapUser1, Password, "3828","test","best wishes","testing",SUCCESS_STATUS_RESP};
		String[] arr2 = { M7_ENV, M7_CartGiftWrapUser2, Password, "3827", "testUser","greetings","test",SUCCESS_STATUS_RESP};
		String[] arr3 = { Stage_ENV, Stage_CartGiftWrapUser1, Password, "3828","test","best wishes","testing",SUCCESS_STATUS_RESP};
		String[] arr4 = { Stage_ENV, Stage_CartGiftWrapUser2, Password, "3827", "testUser","greetings","test",SUCCESS_STATUS_RESP};

		Object[][] dataSet = new Object[][] { arr1, arr2,arr3,arr4};
		return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 2, 2);
	}
	@DataProvider
	public static Object[][] addGiftWrapAndMessage_validateGiftWrapNodesDP(ITestContext testContext)
	{
		String[] arr1 = { M7_ENV, M7_CartGiftWrapUser1, Password, "3867","sender_user","Greetings","recieve"};
		String[] arr2 = { M7_ENV, M7_CartGiftWrapUser2, Password, "3828","test_user","best wishes","test"};
		String[] arr3 = { Stage_ENV, Stage_CartGiftWrapUser1, Password, "3867","sender_user","Greetings","recieve"};
		String[] arr4 = { Stage_ENV, Stage_CartGiftWrapUser2, Password, "3828","test_user","best wishes","test"};

		Object[][] dataSet = new Object[][] { arr1, arr2,arr3,arr4};
		return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 2, 2);
	}

	@DataProvider
	public static Object[][] removeGiftWrapAndMessage_verifyAPIIsUpDP(ITestContext testContext)
	{
		String[] arr1 = { M7_ENV, M7_CartGiftWrapUser1, Password,SUCCESS_STATUS_RESP};
		String[] arr2 = { M7_ENV, M7_CartGiftWrapUser2, Password,SUCCESS_STATUS_RESP};
		String[] arr3 = { Stage_ENV, Stage_CartGiftWrapUser1, Password,SUCCESS_STATUS_RESP};
		String[] arr4 = { Stage_ENV, Stage_CartGiftWrapUser2, Password,SUCCESS_STATUS_RESP};

		Object[][] dataSet = new Object[][] { arr1, arr2,arr3,arr4};
		return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 2, 2);
	}

	@DataProvider
	public static Object[][] removeGiftWrapAndMessage_verifyGiftWrapIsRemovedDP(ITestContext testContext)
	{
		String[] arr1 = { M7_ENV, M7_CartGiftWrapUser1, Password,"3867",SUCCESS_STATUS_RESP,SUCCESS_STATUS_MSG,SUCCESS_STATUS_TYPE};
		String[] arr2 = { M7_ENV, M7_CartGiftWrapUser2, Password,"3828",SUCCESS_STATUS_RESP,SUCCESS_STATUS_MSG,SUCCESS_STATUS_TYPE};
		String[] arr3 = { Stage_ENV, Stage_CartGiftWrapUser1, Password,"3867",SUCCESS_STATUS_RESP,SUCCESS_STATUS_MSG,SUCCESS_STATUS_TYPE};
		String[] arr4 = { Stage_ENV, Stage_CartGiftWrapUser2, Password,"3828",SUCCESS_STATUS_RESP,SUCCESS_STATUS_MSG,SUCCESS_STATUS_TYPE};

		Object[][] dataSet = new Object[][] { arr1, arr2,arr3,arr4};
		return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 2, 2);
	}

	@DataProvider
	public static Object[][] removeGiftWrapAndMessage_validateGiftWrapNodesDP(ITestContext testContext)
	{
		String[] arr1 = { M7_ENV, M7_CartGiftWrapUser1, Password,SUCCESS_STATUS_RESP};
		String[] arr2 = { M7_ENV, M7_CartGiftWrapUser2, Password,SUCCESS_STATUS_RESP};
		String[] arr3 = { Stage_ENV, Stage_CartGiftWrapUser1, Password,SUCCESS_STATUS_RESP};
		String[] arr4 = { Stage_ENV, Stage_CartGiftWrapUser2, Password,SUCCESS_STATUS_RESP};

		Object[][] dataSet = new Object[][] { arr1, arr2,arr3,arr4};
		return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 2, 2);
	}

	@DataProvider
	public static Object[][] applyCoupon_verifyAPIIsUpDP(ITestContext testContext)
	{	
		String[] arr1 = { M7_ENV, M7_CartCouponUser1, Password,"1071738","\"cartLessThan20\"",SUCCESS_STATUS_RESP,SUCCESS_STATUS_MSG,SUCCESS_STATUS_TYPE};
		String[] arr2 = { M7_ENV, M7_CartCouponUser2, Password,"1071738","\"cartMoreThan20\"",SUCCESS_STATUS_RESP,SUCCESS_STATUS_MSG,SUCCESS_STATUS_TYPE};
		String[] arr3 = { M7_ENV, M7_CartCouponUser1, Password,"1071738","\"cartLessThan20\"",SUCCESS_STATUS_RESP,SUCCESS_STATUS_MSG,SUCCESS_STATUS_TYPE};
		String[] arr4 = { M7_ENV, M7_CartCouponUser2, Password,"1071738","\"cartMoreThan20\"",SUCCESS_STATUS_RESP,SUCCESS_STATUS_MSG,SUCCESS_STATUS_TYPE};

		Object[][] dataSet = new Object[][] { arr1,arr2,arr3,arr4};
		return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 2, 2);
	}
	@DataProvider
	public static Object[][] applyCoupon_verifyCouponIsAppliedDP(ITestContext testContext)
	{	
		String[] arr1 = { M7_ENV, M7_CartCouponUser1, Password,"1071738","\"cartLessThan20\"",SUCCESS_STATUS_RESP,"\"SUCCESS\""};
		String[] arr2 = { M7_ENV, M7_CartCouponUser2, Password,"1071738","\"cartMoreThan20\"",SUCCESS_STATUS_RESP,"\"SUCCESS\""};
		String[] arr3 = { M7_ENV, M7_CartCouponUser1, Password,"1071738","\"cartLessThan20\"",SUCCESS_STATUS_RESP,"\"SUCCESS\""};
		String[] arr4 = { M7_ENV, M7_CartCouponUser2, Password,"1071738","\"cartMoreThan20\"",SUCCESS_STATUS_RESP,"\"SUCCESS\""};

		Object[][] dataSet = new Object[][] { arr1,arr2,arr3,arr4};
		return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 2, 2);
	}
	@DataProvider
	public static Object[][] applyCoupon_verifyCouponNodesDP(ITestContext testContext)
	{	
		String[] arr1 = { M7_ENV, M7_CartCouponUser1, Password,"1071738","\"cartLessThan20\"",SUCCESS_STATUS_RESP,"\"Applied Successfully\""};
		String[] arr2 = { M7_ENV, M7_CartCouponUser2, Password,"1071738","\"cartMoreThan20\"",SUCCESS_STATUS_RESP,"\"Applied Successfully\""};
		String[] arr3 = { M7_ENV, M7_CartCouponUser1, Password,"1071738","\"cartLessThan20\"",SUCCESS_STATUS_RESP,"\"Applied Successfully\""};
		String[] arr4 = { M7_ENV, M7_CartCouponUser2, Password,"1071738","\"cartMoreThan20\"",SUCCESS_STATUS_RESP,"\"Applied Successfully\""};

		Object[][] dataSet = new Object[][] { arr1,arr2,arr3,arr4};
		return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 2, 2);
	}

	@DataProvider
	public static Object[][] fetchAllCoupons_verifyAPIIsUpDP(ITestContext testContext)
	{	
		String[] arr1 = { M7_ENV, M7_CartCouponUser1, Password,SUCCESS_STATUS_RESP,SUCCESS_STATUS_MSG,SUCCESS_STATUS_TYPE};
		String[] arr2 = { M7_ENV, M7_CartCouponUser2, Password,SUCCESS_STATUS_RESP,SUCCESS_STATUS_MSG,SUCCESS_STATUS_TYPE};
		String[] arr3 = { M7_ENV, M7_CartCouponUser1, Password,SUCCESS_STATUS_RESP,SUCCESS_STATUS_MSG,SUCCESS_STATUS_TYPE};
		String[] arr4 = { M7_ENV, M7_CartCouponUser2, Password,SUCCESS_STATUS_RESP,SUCCESS_STATUS_MSG,SUCCESS_STATUS_TYPE};

		Object[][] dataSet = new Object[][] { arr1,arr2,arr3,arr4};
		return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 2, 2);
	}

	@DataProvider
	public static Object[][] getLoyaltyPoints_verifyAPIIsUpDP(ITestContext testContext)
	{	
		String[] arr1 = { M7_ENV, M7_CartLPUser1, Password,"ad887713.44a9.4f9e.9867.bc5158594b05jPNWB0MdWm",SUCCESS_STATUS_RESP,SUCCESS_STATUS_TYPE};
		String[] arr2 = { M7_ENV, M7_CartLPUser2, Password,"c52b7c38.01b7.4454.a74f.17bf11638bb2IbJzdydDsZ",SUCCESS_STATUS_RESP,SUCCESS_STATUS_TYPE};
		String[] arr3 = { Stage_ENV, Stage_CartLPUser1, Password,"73093ab7.5c47.4520.ab0f.e4d8fbffa984IeGl0m4lN8",SUCCESS_STATUS_RESP,SUCCESS_STATUS_TYPE};
		String[] arr4 = { Stage_ENV, Stage_CartLPUser2, Password,"80857b9f.6a8d.4310.84ee.e2510a25fc0dqy4GGCA6Nf",SUCCESS_STATUS_RESP,SUCCESS_STATUS_TYPE};

		Object[][] dataSet = new Object[][] { arr1,arr2,arr3,arr4};
		return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 2, 2);
	}

	@DataProvider
	public static Object[][] applyLoyaltyPoints_verifyAPIIsUpDP(ITestContext testContext)
	{	
		String[] arr1 = { M7_ENV, M7_CartLPUser1, Password,SUCCESS_STATUS_RESP,"\"Success\""};
		String[] arr2 = { M7_ENV, M7_CartLPUser2, Password,SUCCESS_STATUS_RESP,"\"Success\""};
		String[] arr3 = { Stage_ENV, Stage_CartLPUser1, Password,SUCCESS_STATUS_RESP,"\"Success\""};
		String[] arr4 = { Stage_ENV, Stage_CartLPUser2, Password,SUCCESS_STATUS_RESP,"\"Success\""};

		Object[][] dataSet = new Object[][] { arr1,arr2,arr3,arr4};
		return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 2, 2);
	}
	@DataProvider
	public static Object[][] applyLoyaltyPoints_verifyLPIsAppliedDP(ITestContext testContext)
	{	
		String[] arr1 = { M7_ENV, M7_CartLPUser1, Password,"ad887713.44a9.4f9e.9867.bc5158594b05jPNWB0MdWm","3833",SUCCESS_STATUS_RESP};
		String[] arr2 = { M7_ENV, M7_CartLPUser2, Password,"c52b7c38.01b7.4454.a74f.17bf11638bb2IbJzdydDsZ","3834",SUCCESS_STATUS_RESP};
		String[] arr3 = { Stage_ENV, Stage_CartLPUser1, Password,"73093ab7.5c47.4520.ab0f.e4d8fbffa984IeGl0m4lN8","3833",SUCCESS_STATUS_RESP};
		String[] arr4 = { Stage_ENV, Stage_CartLPUser2, Password,"80857b9f.6a8d.4310.84ee.e2510a25fc0dqy4GGCA6Nf","3834",SUCCESS_STATUS_RESP};

		Object[][] dataSet = new Object[][] { arr1,arr2,arr3,arr4};
		return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 2, 2);
	}

	@DataProvider
	public static Object[][] applyLoyaltyPoints_verifyMaxUsageDP(ITestContext testContext)
	{	
		String[] arr1 = { M7_ENV, M7_CartLPUser1, Password,"ad887713.44a9.4f9e.9867.bc5158594b05jPNWB0MdWm","3833",SUCCESS_STATUS_RESP};
		String[] arr2 = { M7_ENV, M7_CartLPUser2, Password,"c52b7c38.01b7.4454.a74f.17bf11638bb2IbJzdydDsZ","3834",SUCCESS_STATUS_RESP};
		String[] arr3 = { Stage_ENV, Stage_CartLPUser1, Password,"73093ab7.5c47.4520.ab0f.e4d8fbffa984IeGl0m4lN8","3833",SUCCESS_STATUS_RESP};
		String[] arr4 = { Stage_ENV, Stage_CartLPUser2, Password,"80857b9f.6a8d.4310.84ee.e2510a25fc0dqy4GGCA6Nf","3834",SUCCESS_STATUS_RESP};

		Object[][] dataSet = new Object[][] { arr1,arr2,arr3,arr4};
		return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 2, 2);
	}
	@DataProvider
	public static Object[][] removeLoyaltyPoints_verifyAPIIsUpDP(ITestContext testContext)
	{	
		String[] arr1 = { M7_ENV, M7_CartLPUser1, Password,SUCCESS_STATUS_RESP,"\"Success\""};
		String[] arr2 = { M7_ENV, M7_CartLPUser2, Password,SUCCESS_STATUS_RESP,"\"Success\""};
		String[] arr3 = { Stage_ENV, Stage_CartLPUser1, Password,SUCCESS_STATUS_RESP,"\"Success\""};
		String[] arr4 = { Stage_ENV, Stage_CartLPUser2, Password,SUCCESS_STATUS_RESP,"\"Success\""};

		Object[][] dataSet = new Object[][] { arr1,arr2,arr3,arr4};
		return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 2, 2);
	}
	@DataProvider
	public static Object[][] removeLoyaltyPointst_verifyLPISRemovedDP(ITestContext testContext)
	{	
		String[] arr1 = { M7_ENV, M7_CartLPUser1, Password,"ad887713.44a9.4f9e.9867.bc5158594b05jPNWB0MdWm","3833",SUCCESS_STATUS_RESP};
		String[] arr2 = { M7_ENV, M7_CartLPUser2, Password,"c52b7c38.01b7.4454.a74f.17bf11638bb2IbJzdydDsZ","3834",SUCCESS_STATUS_RESP};
		String[] arr3 = { Stage_ENV, Stage_CartLPUser1, Password,"73093ab7.5c47.4520.ab0f.e4d8fbffa984IeGl0m4lN8","3833",SUCCESS_STATUS_RESP};
		String[] arr4 = { Stage_ENV, Stage_CartLPUser2, Password,"80857b9f.6a8d.4310.84ee.e2510a25fc0dqy4GGCA6Nf","3834",SUCCESS_STATUS_RESP};

		Object[][] dataSet = new Object[][] { arr1,arr2,arr3,arr4};
		return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 2, 2);
	}
	@DataProvider
	public static Object[][] fetchCart_VerifyShippingChargesDP(ITestContext testContext)
	{	
		String[] arr1 = { M7_ENV, M7_CartAutoUser3, Password,"3828",SUCCESS_STATUS_RESP};
		String[] arr2 = { M7_ENV, M7_CartAutoUser4, Password,"1071735",SUCCESS_STATUS_RESP};
		String[] arr3 = { Stage_ENV, Stage_CartAutoUser3, Password,"3828",SUCCESS_STATUS_RESP};
		String[] arr4 = { Stage_ENV, Stage_CartAutoUser4, Password,"991249",SUCCESS_STATUS_RESP};

		Object[][] dataSet = new Object[][] { arr1,arr2,arr3,arr4};
		return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 2, 2);
	}
	@DataProvider
	public static Object[][] applyCouponDiscount_VerifyVATChargesDP(ITestContext testContext)
	{	
		String[] arr1 = { M7_ENV, M7_CartCouponUser1, Password,"1071738","\"cartLessThan20\"","NO",SUCCESS_STATUS_RESP};
		String[] arr2 = { M7_ENV, M7_CartCouponUser2, Password,"1071738","\"cartMoreThan20\"","YES",SUCCESS_STATUS_RESP};
		String[] arr3 = { M7_ENV, M7_CartCouponUser1, Password,"1071738","\"cartLessThan20\"","NO",SUCCESS_STATUS_RESP};
		String[] arr4 = { M7_ENV, M7_CartCouponUser2, Password,"1071738","\"cartMoreThan20\"","YES",SUCCESS_STATUS_RESP};

		Object[][] dataSet = new Object[][] { arr1,arr2};
		return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 2, 2);
	}

	@DataProvider
	public static Object[][] fetchCart_VerifyCalculationDP(ITestContext testContext)
	{	
		String[] arr1 = { M7_ENV, M7_CartAutoUser3, Password,"1071737",SUCCESS_STATUS_RESP};
		String[] arr2 = { M7_ENV, M7_CartAutoUser4, Password,"1071738",SUCCESS_STATUS_RESP};
		String[] arr3 = { Stage_ENV, Stage_CartAutoUser3, Password,"1071737",SUCCESS_STATUS_RESP};
		String[] arr4 = { Stage_ENV, Stage_CartAutoUser4, Password,"1071738",SUCCESS_STATUS_RESP};

		Object[][] dataSet = new Object[][] { arr1,arr2,arr3,arr4};
		return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 2, 2);
	}

	@DataProvider
	public static Object[][] applyCoupon_VerifyFetchCartCalculationDP(ITestContext testContext)
	{	
		String[] arr1 = { M7_ENV, M7_CartCouponUser1, Password,"1071738","\"cartLessThan20\"",SUCCESS_STATUS_RESP};
		String[] arr2 = { M7_ENV, M7_CartCouponUser2, Password,"1071738","\"cartMoreThan20\"",SUCCESS_STATUS_RESP};
		String[] arr3 = { M7_ENV, M7_CartCouponUser1, Password,"1071738","\"cartLessThan20\"",SUCCESS_STATUS_RESP};
		String[] arr4 = { M7_ENV, M7_CartCouponUser2, Password,"1071738","\"cartMoreThan20\"",SUCCESS_STATUS_RESP};

		Object[][] dataSet = new Object[][] { arr1,arr2};
		return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 2, 2);
	}

	@DataProvider
	public static Object[][] applyLP_VerifyFetchCartCalculationDP(ITestContext testContext)
	{	
		String[] arr1 = { M7_ENV, M7_CartLPUser1, Password,"ad887713.44a9.4f9e.9867.bc5158594b05jPNWB0MdWm","3833",SUCCESS_STATUS_RESP};
		String[] arr2 = { M7_ENV, M7_CartLPUser2, Password,"c52b7c38.01b7.4454.a74f.17bf11638bb2IbJzdydDsZ","3834",SUCCESS_STATUS_RESP};
		String[] arr3 = { Stage_ENV, Stage_CartLPUser1, Password,"73093ab7.5c47.4520.ab0f.e4d8fbffa984IeGl0m4lN8","991249",SUCCESS_STATUS_RESP};
		String[] arr4 = { Stage_ENV, Stage_CartLPUser2, Password,"80857b9f.6a8d.4310.84ee.e2510a25fc0dqy4GGCA6Nf","3847",SUCCESS_STATUS_RESP};

		Object[][] dataSet = new Object[][] { arr1,arr2,arr3};
		return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 2, 2);
	}
	@DataProvider
	public static Object[][] optTryNBuyOnDelivery_verifyAPIIsUpDP(ITestContext testContext)
	{
		String[] arr1 = { M7_ENV, M7_CartAutoUser3, Password,"57950112",SUCCESS_STATUS_RESP};
		String[] arr2 = { M7_ENV, M7_CartAutoUser4, Password,"85041231",SUCCESS_STATUS_RESP};
		String[] arr3 = { Stage_ENV, Stage_CartAutoUser3, Password,"57950112",SUCCESS_STATUS_RESP};
		String[] arr4 = { Stage_ENV, Stage_CartAutoUser4, Password,"85041231",SUCCESS_STATUS_RESP};

		Object[][] dataSet = new Object[][] { arr1,arr2,arr3,arr4};
		return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 2, 2);
	}

	@DataProvider
	public static Object[][] fraudUserVerifyShippingDP(ITestContext testContext)
	{	
		String[] arr1 = { M7_ENV, M7_FraudUser1, Password,"3828",SUCCESS_STATUS_RESP};
		String[] arr2 = { M7_ENV, M7_FraudUser1, Password,"1137029",SUCCESS_STATUS_RESP};
		String[] arr3 = { M7_ENV, M7_FraudUser1, Password,"3828",SUCCESS_STATUS_RESP};
		String[] arr4 = { M7_ENV, M7_FraudUser1, Password,"1137029",SUCCESS_STATUS_RESP};

		Object[][] dataSet = new Object[][] { arr1,arr2};
		return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 2, 2);
	}
	//Negative Cases DP (run only on demand)
	@DataProvider
	public static Object[][] addItemToCartAPI_VerifyFailureCasesDP(ITestContext testContext)
	{
		String[] arr1 = { M7_ENV, M7_CartAutoUser3, Password,"3816","-1",INVALID_RESP_CODE};
		String[] arr2 = { M7_ENV, M7_CartAutoUser4, Password,"8506","-2",INVALID_RESP_CODE};

		Object[][] dataSet = new Object[][] { arr1,arr2};
		return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 2, 2);
	}

	@DataProvider
	public static Object[][] updateItemToCartAPI_VerifyFailureCasesDP(ITestContext testContext)
	{
		String[] arr1 = { M7_ENV, M7_CartAutoUser3, Password,"8503","1","-1",INVALID_RESP_CODE};
		String[] arr2 = { M7_ENV, M7_CartAutoUser4, Password,"3816","1","-2",INVALID_RESP_CODE};

		Object[][] dataSet = new Object[][] { arr1,arr2};
		return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 2, 2);
	}


	@DataProvider
	public static Object[][] applyGiftWrapWithNVeGiftWrapChargesDP(ITestContext testContext)
	{
		String[] arr1 = { M7_ENV, M7_NegCartAutoUser1, Password,"NegTestUser","Best wishes","test",INVALID_RESP_CODE};

		Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 1,1);
	}

	@DataProvider
	public static Object[][] fetchCartWithNVeGiftWrapCharges(ITestContext testContext)
	{
		String[] arr1 = { M7_ENV, M7_NegCartAutoUser2, Password, INVALID_RESP_CODE};

		Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 1,1);
	}

	//created a 100% coupon and enabled it for this user with this style id 342031 skuId 1137029
	@DataProvider
	public static Object[][] apply100PerCouponVerifyFinalMRPDP(ITestContext testContext)
	{
		String[] arr1 = { M7_ENV, M7_NegCartAutoUser4, Password,"1137029","\"CouponPer100\"",SUCCESS_STATUS_RESP,SUCCESS_STATUS_MSG,SUCCESS_STATUS_TYPE};

		Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 1,1);
	}
	//created a 120% coupon
	@DataProvider
	public static Object[][] apply120PerCouponDP(ITestContext testContext)
	{
		String[] arr1 = { M7_ENV, M7_NegCartAutoUser3, Password,"1137029","\"Morethan100\"",INVALID_RESP_CODE};

		Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 1,1);
	}
	//created a 30% coupon, applied then modified coupon to 120%
	@DataProvider
	public static Object[][] fetchCartHaving120PerCouponAppliedDP(ITestContext testContext)
	{
		String[] arr1 = { M7_ENV, M7_NegCartAutoUser5, Password,"1137029","\"Coupon121\"",INVALID_RESP_CODE,FAILURE_STATUS_TYPE};

		Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 1,1);
	}
	@DataProvider
	public static Object[][] discountMoreThanMRPAddToCartDP(ITestContext testContext)
	{
		String[] arr1 = { M7_ENV, M7_NegCartAutoUser8, Password,"1006841",INVALID_RESP_CODE,FAILURE_STATUS_TYPE};

		Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 1,1);
	}

	@DataProvider
	public static Object[][] fetchCartWithDiscountMoreThanMRPDP(ITestContext testContext)
	{
		String[] arr1 = { M7_ENV, M7_NegCartAutoUser6, Password,"1006841",INVALID_RESP_CODE};

		Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 1,1);
	}
	@DataProvider
	public static Object[][] fetchCartWithNVeShippingChargesDP(ITestContext testContext)
	{
		String[] arr1 = { M7_ENV, M7_NegCartAutoUser7, Password,INVALID_RESP_CODE,};

		Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 1,1);
	}

	@DataProvider
	public static Object[][] singleItemCart_crossVerifyCartDataWithSellerConfigDP(ITestContext testContext)
	{
		String[] arr1 = { M7_ENV, M7_CartAutoUser1, Password, "3827", SUCCESS_STATUS_RESP,SUCCESS_STATUS_MSG,SUCCESS_STATUS_TYPE};
		String[] arr2 = { M7_ENV, M7_CartAutoUser2, Password, "3827", SUCCESS_STATUS_RESP,SUCCESS_STATUS_MSG,SUCCESS_STATUS_TYPE};
		String[] arr3 = { Stage_ENV, Stage_ConfigUser1, Password, "3827", SUCCESS_STATUS_RESP,SUCCESS_STATUS_MSG,SUCCESS_STATUS_TYPE};
		String[] arr4 = { Stage_ENV, Stage_ConfigUser2, Password, "3827", SUCCESS_STATUS_RESP,SUCCESS_STATUS_MSG,SUCCESS_STATUS_TYPE};

		Object[][] dataSet = new Object[][] { arr1, arr2,arr3,arr4};
		return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 2, 2);
	}

	@DataProvider
	public static Object[][] multiItemCart_crossVerifyCartDataLevelFlagsWithSellerConfigDP(ITestContext testContext)
	{
		String[] arr1 = { M7_ENV, "testing_wallet@myntra.com", "123456", "895234,3827,1251868", SUCCESS_STATUS_RESP,SUCCESS_STATUS_MSG,SUCCESS_STATUS_TYPE};
		String[] arr2 = { M7_ENV, M7_CartAutoUser2, Password, "3827", SUCCESS_STATUS_RESP,SUCCESS_STATUS_MSG,SUCCESS_STATUS_TYPE};
		String[] arr3 = { Stage_ENV, Stage_ConfigUser1, Password, "895234,3827", SUCCESS_STATUS_RESP,SUCCESS_STATUS_MSG,SUCCESS_STATUS_TYPE};
		String[] arr4 = { Stage_ENV, Stage_ConfigUser2, Password, "3827", SUCCESS_STATUS_RESP,SUCCESS_STATUS_MSG,SUCCESS_STATUS_TYPE};

		Object[][] dataSet = new Object[][] { arr1, arr2,arr3,arr4};
		return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 2, 2);
	}

	@DataProvider
	public static Object[][] addVirtualBundleItemToCart(ITestContext testContext)
	{

		//String[] arr = {environment, userName, password, virtualBundleSkuId, physicalSKU1/physicalSKU2, totalItemsInVB, successStatusCode, successStatusMsg}		
		String[] arr1 = {Stage_ENV, Stage_VBUser1, "123456", "12266271", "12266266", "2", SUCCESS_STATUS_RESP,SUCCESS_STATUS_MSG};
		String[] arr2 = {Stage_ENV, Stage_VBUser1, "123456", "12266287", "12266280/12266284", "4", SUCCESS_STATUS_RESP,SUCCESS_STATUS_MSG};

		Object[][] dataSet = new Object[][] {arr1, arr2};
		return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 2, 2);
	}

	@DataProvider
	public static  Object[][] updateVirtualBundleItem_verifyQuantityUpdated(ITestContext testContext)
	{
		//String[] arr = {environment, userName, password, virtualBundleSkuId, totalItemsInVB, itemQtyToUpdate, updatedTotalCartCount, successStatusCode, successStatusMsg}		
		String[] arr1 = {Stage_ENV, Stage_VBUser1, "123456", "12266271","2", "3", "6", SUCCESS_STATUS_RESP, SUCCESS_STATUS_MSG};
		String[] arr2 = {Stage_ENV, Stage_VBUser1, "123456", "12266287", "4", "2", "8", SUCCESS_STATUS_RESP,SUCCESS_STATUS_MSG};

		Object[][] dataSet = new Object[][] {arr1, arr2};
		return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 2, 2);
	}

	@DataProvider
	public static  Object[][] updateVirtualBundleItem_verifySkuUpdated(ITestContext testContext)
	{
		//String[] arr = {environment, userName, password, virtualBundleSkuId, physicalSKU1/physicalSKU2, totalItemsInVB, updatedSKU, updatedPhysicalSKU1/updatedPhysicalSKU2, successStatusCode, successStatusMsg}		
		String[] arr1 = {Stage_ENV, Stage_VBUser1, "123456", "12266271", "12266266", "2", "12266272", "12266267", SUCCESS_STATUS_RESP, SUCCESS_STATUS_MSG};
		String[] arr2 = {Stage_ENV, Stage_VBUser1, "123456", "12266287", "12266280/12266284", "4", "12266288", "12266281/12266285", SUCCESS_STATUS_RESP,SUCCESS_STATUS_MSG};

		Object[][] dataSet = new Object[][] {arr1, arr2};
		return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 2, 2);
	}

	@DataProvider
	public static Object[][] applyCoupon_VerifyProratedCouponDiscountForVb(ITestContext testContext)
	{	
		String[] arr1 = { Stage_ENV, Stage_VBUser1, "123456", "12266288", "12266281/12266285", "\"TSTBOTZMUZ8\"", SUCCESS_STATUS_RESP};
		String[] arr2 = { Stage_ENV, Stage_VBUser1, "123456", "12266305", "12266303", "\"TSTBOTNA0PL\"", SUCCESS_STATUS_RESP};

		Object[][] dataSet = new Object[][] {arr1, arr2};
		return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 2, 2);
	}

	@DataProvider
	public static Object[][] discounts_VerifyProratedDiscountForVb(ITestContext testContext)
	{	
		String[] arr1 = { Stage_ENV, Stage_VBUser1, "123456", "12266311", "12266308/12266303", SUCCESS_STATUS_RESP};
		String[] arr2 = { Stage_ENV, Stage_VBUser1, "123456", "12266312", "12266309/12266303", SUCCESS_STATUS_RESP};
		String[] arr3 = { Stage_ENV, Stage_VBUser1, "123456", "12266305", "12266303", SUCCESS_STATUS_RESP};

		Object[][] dataSet = new Object[][] {arr3};
		return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 2, 2);
	}

	@DataProvider
	public static Object[][] serviceabilityForMultipleItems(ITestContext testContext)
	{	
		//String[] arr = {ENV, userName, password, pincode, skuId, normalDelivery, expressDelivery, sdd, valueShipping, cashOnDelivery, codDetails, cardOnDelivery, tryNBuy};
		//SellerId = 21
		String[] arr1 = {Stage_ENV, Stage_CartAutoUser1, Password, "560068", "1133606", "SERVICEABLE", "SERVICEABLE", "SERVICEABLE", "SERVICEABLE", "SERVICEABLE", "0,,true", "false", "true", SUCCESS_STATUS_RESP, SUCCESS_STATUS_MSG, SUCCESS_STATUS_TYPE};
		String[] arr2 = {Stage_ENV, Stage_CartAutoUser1, Password, "160019", "1133606", "SERVICEABLE", "NA", "NA", "NA", "SERVICEABLE", "0,,true", "false", "true", SUCCESS_STATUS_RESP, SUCCESS_STATUS_MSG, SUCCESS_STATUS_TYPE};
		String[] arr3 = {Stage_ENV, Stage_CartAutoUser1, Password, "411001", "1133606", "SERVICEABLE", "SERVICEABLE", "SERVICEABLE", "NA", "SERVICEABLE", "0,,false", "true", "true", SUCCESS_STATUS_RESP, SUCCESS_STATUS_MSG, SUCCESS_STATUS_TYPE};
		String[] arr4 = {Stage_ENV, Stage_CartAutoUser1, Password, "131027", "1133606", "SERVICEABLE", "NA", "NA", "NA", "UNSERVICEABLE", "0,Cash on Delivery is not available for your Pincode.,false", "false", "false", SUCCESS_STATUS_RESP, SUCCESS_STATUS_MSG, SUCCESS_STATUS_TYPE};
		String[] arr5 = {Stage_ENV, "return_abuser1@gmail.com", "123456", "560068", "1133606", "SERVICEABLE", "NA", "NA", "NA", "UNSERVICEABLE", "1,<span class='note'>Please note: </span>Your Cash on Delivery limit for this transaction is between Rs. <span class='rs'>299.0</span> to Rs. <span class='rs'>1500.0</span> only,false", "false", "true", SUCCESS_STATUS_RESP, SUCCESS_STATUS_MSG, SUCCESS_STATUS_TYPE};
		String[] arr6 = {Stage_ENV, Stage_CartAutoUser1, Password, "560068", "3827", "SERVICEABLE", "NA", "NA", "UNSERVICEABLE", "UNSERVICEABLE", "1,<span class='note'>Please note: </span>Your Cash on Delivery limit for this transaction is between Rs. <span class='rs'>299.0</span> to Rs. <span class='rs'>20000.0</span> only,false", "false", "true", SUCCESS_STATUS_RESP, SUCCESS_STATUS_MSG, SUCCESS_STATUS_TYPE};

		//sellerId= 18
		String[] arr7 = {Stage_ENV, Stage_CartAutoUser1, Password, "560068", "10662860", "SERVICEABLE", "NA", "NA", "NA", "SERVICEABLE", "0,,true", "false", "true", SUCCESS_STATUS_RESP, SUCCESS_STATUS_MSG, SUCCESS_STATUS_TYPE};
		String[] arr8 = {Stage_ENV, Stage_CartAutoUser1, Password, "160019", "10662860", "SERVICEABLE", "NA", "NA", "NA", "SERVICEABLE", "0,,true", "false", "true", SUCCESS_STATUS_RESP, SUCCESS_STATUS_MSG, SUCCESS_STATUS_TYPE};
		String[] arr9 = {Stage_ENV, Stage_CartAutoUser1, Password, "411001", "10662860", "SERVICEABLE", "NA", "NA", "NA", "SERVICEABLE", "0,,false", "true", "true", SUCCESS_STATUS_RESP, SUCCESS_STATUS_MSG, SUCCESS_STATUS_TYPE};

		//SellerId = 21,21
		String[] arr10 = {Stage_ENV, Stage_CartAutoUser1, Password, "560068", "1071244,1133606", "SERVICEABLE", "SERVICEABLE", "SERVICEABLE", "SERVICEABLE", "SERVICEABLE", "0,,true", "false", "true", SUCCESS_STATUS_RESP, SUCCESS_STATUS_MSG, SUCCESS_STATUS_TYPE};
		String[] arr11 = {Stage_ENV, Stage_CartAutoUser1, Password, "160019", "1071244,1133606", "SERVICEABLE", "NA", "NA", "NA", "SERVICEABLE", "0,,true", "false", "true", SUCCESS_STATUS_RESP, SUCCESS_STATUS_MSG, SUCCESS_STATUS_TYPE};
		String[] arr12 = {Stage_ENV, Stage_CartAutoUser1, Password, "411001", "1071244,1133606", "SERVICEABLE", "NA", "NA", "NA", "SERVICEABLE", "0,,false", "true", "true", SUCCESS_STATUS_RESP, SUCCESS_STATUS_MSG, SUCCESS_STATUS_TYPE};
		
		//SellerId = 21,18
		String[] arr13 = {Stage_ENV, Stage_CartAutoUser1, Password, "560068", "1133606,10662860", "SERVICEABLE", "NA", "NA", "NA", "SERVICEABLE", "0,,true", "false", "true", SUCCESS_STATUS_RESP, SUCCESS_STATUS_MSG, SUCCESS_STATUS_TYPE};
		String[] arr14= {Stage_ENV, Stage_CartAutoUser1, Password, "160019", "1133606,10662860", "SERVICEABLE", "NA", "NA", "NA", "SERVICEABLE", "0,,true", "false", "true", SUCCESS_STATUS_RESP, SUCCESS_STATUS_MSG, SUCCESS_STATUS_TYPE};
		String[] arr15 = {Stage_ENV, Stage_CartAutoUser1, Password, "411001", "1133606,10662860", "SERVICEABLE", "NA", "NA", "NA", "SERVICEABLE", "0,,false", "true", "true", SUCCESS_STATUS_RESP, SUCCESS_STATUS_MSG, SUCCESS_STATUS_TYPE};
//		Object[][] dataSet = new Object[][] {arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9, arr10, arr11, arr12, arr13, arr14, arr15};
		Object[][] dataSet = new Object[][] {arr1, arr2};
		return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 5, 15);
	}
}

