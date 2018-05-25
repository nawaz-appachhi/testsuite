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

public class CartServiceTestsDPV2 extends CommonUtils implements AbsolutConstants{
	public static String environment;
	Configuration con = new Configuration("./Data/configuration");
	public CartServiceTestsDPV2() 
	{
		if (System.getenv("ENVIRONMENT") == null)
			environment = con.GetTestEnvironemnt().toString();
		else
			environment = System.getenv("ENVIRONMENT");
	}

	@DataProvider
	public static Object[][] addItemToCart_verifyAPIIsUpDP(ITestContext testContext)
	{
		String[] arr1 = { Stage_ENV, "2297", Stage_CartAutoUser1, Password, "3827", SUCCESS_STATUS_RESP,SUCCESS_STATUS_MSG,SUCCESS_STATUS_TYPE};
		String[] arr2 = { Stage_ENV, "2297", Stage_CartAutoUser2, Password, "3827", SUCCESS_STATUS_RESP,SUCCESS_STATUS_MSG,SUCCESS_STATUS_TYPE};
		
		String[] arr3 = { Sfqa_ENV, "2297", CartAutoUser, Password, "3832", SUCCESS_STATUS_RESP,SUCCESS_STATUS_MSG,SUCCESS_STATUS_TYPE};
		String[] arr4 = { Mjint_ENV, "2297", CartAutoUser, Password, "3832", SUCCESS_STATUS_RESP,SUCCESS_STATUS_MSG,SUCCESS_STATUS_TYPE};

		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4};
		return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 2, 2);
	}

}

