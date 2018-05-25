package com.myntra.apiTests.dataproviders;

import com.myntra.apiTests.portalservices.checkoutservice.CheckoutServiceConstants;

import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import com.myntra.apiTests.portalservices.checkoutservice.CheckoutDataProviderEnum;
import com.myntra.apiTests.portalservices.commons.CommonUtils;
import com.myntra.lordoftherings.Toolbox;

public class PayNowPaymentsServiceDP extends CommonUtils implements CheckoutServiceConstants {
	String environment;
	
	public PayNowPaymentsServiceDP(){
		if(System.getenv(CheckoutDataProviderEnum.ENVIRONMENT.name()) == null)
			environment = CONFIGURATION.GetTestEnvironemnt().toString();
		else
			environment = System.getenv(CheckoutDataProviderEnum.ENVIRONMENT.name());
	}
	
	@DataProvider
	public Object[][] PayNowPaymentsServiceTests_PlaceOrderUsingPayNowAPI_VerifyAPIIsUpDP(ITestContext testContext)
	{
		String[] arr1 = { ENVIRONMENT_FOX8, "fox8_testing5@myntra.com", "123456", FOX8_PAYNOW_AMOUNT };
		String[] arr2 = { ENVIRONMENT_FOX8, "fox8_testing6@myntra.com", "123456", FOX8_PAYNOW_AMOUNT };
		String[] arr3 = { ENVIRONMENT_FOX8, "fox8_testing1@gmail.com", "123456", FOX8_PAYNOW_AMOUNT };
		String[] arr4 = { ENVIRONMENT_FOX7, "fox7_testing5@myntra.com", "123456", FOX7_PAYNOW_AMOUNT };
		//String[] arr5 = { ENVIRONMENT_FOX7, "fox7_testing6@myntra.com", "123456", FOX7_PAYNOW_AMOUNT };
		String[] arr6 = { ENVIRONMENT_FOX7, "fox7_testing1@gmail.com", "123456", FOX7_PAYNOW_AMOUNT };
		
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr6 };
		return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 2, 3);
	}
	
	@DataProvider
	public Object[][] PayNowPaymentsServiceTests_ApplyCouponPlaceOrderAsCOD_VerifySuccessResponseDP(ITestContext testContext)
	{
		String[] arr4 = { ENVIRONMENT_FOX7, FOX7_VALID_USERNAME1, FOX7_VALID_PASSWORD1, FOX7_CART_ADD_ITEM_QTY ,ADD_OPERATION, SUCCESS_RESPONSE_CODE };
		String[] arr5 = { ENVIRONMENT_FOX7, FOX7_VALID_USERNAME2, FOX7_VALID_PASSWORD2, FOX7_CART_ADD_ITEM_QTY ,ADD_OPERATION, SUCCESS_RESPONSE_CODE };
		
		Object[][] dataSet = new Object[][] { arr4, arr5};
		return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 2, 2);
	}
	
	@DataProvider
	public Object[][] PayNowPaymentsServiceTests_PlaceOrderAsCOD_VerifySuccessResponseDP(ITestContext testContext)
	{
		String[] arr1 = { ENVIRONMENT_FOX8, FOX8_VALID_USERNAME1, FOX8_VALID_PASSWORD1, FOX8_CART_ADD_ITEM_QTY ,ADD_OPERATION, SUCCESS_RESPONSE_CODE };
		String[] arr2 = { ENVIRONMENT_FOX8, FOX8_VALID_USERNAME2, FOX8_VALID_PASSWORD2, FOX8_CART_ADD_ITEM_QTY ,ADD_OPERATION, SUCCESS_RESPONSE_CODE };
		String[] arr3 = { ENVIRONMENT_FOX8, FOX8_VALID_USERNAME5, FOX8_VALID_PASSWORD5, FOX8_CART_ADD_ITEM_QTY ,ADD_OPERATION, SUCCESS_RESPONSE_CODE };
		String[] arr4 = { ENVIRONMENT_FOX7, FOX7_VALID_USERNAME5, FOX7_VALID_PASSWORD, FOX7_CART_ADD_ITEM_QTY ,ADD_OPERATION, SUCCESS_RESPONSE_CODE };
		String[] arr5 = { ENVIRONMENT_FOX7, FOX7_VALID_USERNAME6, FOX7_VALID_PASSWORD, FOX7_CART_ADD_ITEM_QTY ,ADD_OPERATION, SUCCESS_RESPONSE_CODE };
		//String[] arr4 = { ENVIRONMENT_FOX7, FOX7_VALID_USERNAME5, FOX8_VALID_PASSWORD5, FOX7_CART_ADD_ITEM_QTY ,ADD_OPERATION, SUCCESS_RESPONSE_CODE };
		
		
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5};
		return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 2, 3);
	}
	
	@DataProvider
	public Object[][] PayNowPaymentsServiceTests_PlaceOrderUsingCashBack_VerifyPaymentPlanInstrumentDP(ITestContext testContext)
	{
		String[] arr1 = { ENVIRONMENT_FOX8, FOX8_VALID_USERNAME3, FOX8_VALID_PASSWORD3, FOX8_CART_ADD_ITEM_QTY ,ADD_OPERATION, SUCCESS_RESPONSE_CODE};
		String[] arr2 = { ENVIRONMENT_FOX8, FOX8_VALID_USERNAME4, FOX8_VALID_PASSWORD4, FOX8_CART_ADD_ITEM_QTY ,ADD_OPERATION, SUCCESS_RESPONSE_CODE };
		String[] arr3 = { ENVIRONMENT_FOX8, FOX8_VALID_USERNAME5, FOX8_VALID_PASSWORD5, FOX8_CART_ADD_ITEM_QTY ,ADD_OPERATION, SUCCESS_RESPONSE_CODE };
		String[] arr4 = { ENVIRONMENT_FOX7, FOX7_VALID_USERNAME1, FOX7_VALID_PASSWORD1, FOX7_CART_ADD_ITEM_QTY ,ADD_OPERATION, SUCCESS_RESPONSE_CODE };
		String[] arr5 = { ENVIRONMENT_FOX7, FOX7_VALID_USERNAME2, FOX7_VALID_PASSWORD2, FOX7_CART_ADD_ITEM_QTY ,ADD_OPERATION, SUCCESS_RESPONSE_CODE };
		
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5};
		return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 2, 3);
	}
	
	@DataProvider
	public Object[][] PayNowPaymentsServiceTests_PlaceOrderUsingLoyaltyPoints_VerifySuccessResponseDP(ITestContext testContext)
	{
		String[] arr1 = { ENVIRONMENT_FOX8, FOX8_VALID_USERNAME3, FOX8_VALID_PASSWORD3, FOX8_CART_ADD_ITEM_QTY ,ADD_OPERATION, SUCCESS_RESPONSE_CODE};
		String[] arr2 = { ENVIRONMENT_FOX8, FOX8_VALID_USERNAME4, FOX8_VALID_PASSWORD4, FOX8_CART_ADD_ITEM_QTY ,ADD_OPERATION, SUCCESS_RESPONSE_CODE };
		String[] arr3 = { ENVIRONMENT_FOX8, FOX8_VALID_USERNAME5, FOX8_VALID_PASSWORD5, FOX8_CART_ADD_ITEM_QTY ,ADD_OPERATION, SUCCESS_RESPONSE_CODE };
		String[] arr4 = { ENVIRONMENT_FOX7, FOX7_VALID_USERNAME1, FOX7_VALID_PASSWORD1, FOX7_CART_ADD_ITEM_QTY ,ADD_OPERATION, SUCCESS_RESPONSE_CODE };
		String[] arr5 = { ENVIRONMENT_FOX7, FOX7_VALID_USERNAME2, FOX7_VALID_PASSWORD2, FOX7_CART_ADD_ITEM_QTY ,ADD_OPERATION, SUCCESS_RESPONSE_CODE };
		
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5};
		return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 2, 3);
	}
	
	@DataProvider
	public Object[][] PayNowPaymentsServiceTests_GetPaymentPlanGivingPPSID_VerifyAPIIsUpDP(ITestContext testContext)
	{
		String[] arr1 = { ENVIRONMENT_FOX8, "7e7eb576-4a49-4d0d-b829-b85f2c505153",SUCCESS_RESPONSE_CODE  };
		String[] arr2 = { ENVIRONMENT_FOX8, "0c552bce-29f3-4531-b6bf-6ec09fbd0f7b",SUCCESS_RESPONSE_CODE  };
		String[] arr3 = { ENVIRONMENT_FOX8, "b25752e3-4a9a-4204-9cc7-0968dde5550a",SUCCESS_RESPONSE_CODE  };
		String[] arr4 = { ENVIRONMENT_FOX7, "7e7eb576-4a49-4d0d-b829-b85f2c505153",SUCCESS_RESPONSE_CODE  };
		String[] arr5 = { ENVIRONMENT_FOX7, "0c552bce-29f3-4531-b6bf-6ec09fbd0f7b",SUCCESS_RESPONSE_CODE  };
		String[] arr6 = { ENVIRONMENT_FOX7, "b25752e3-4a9a-4204-9cc7-0968dde5550a",SUCCESS_RESPONSE_CODE  };
		
		Object[][] dataSet = new Object[][] { arr1,arr2,arr3, arr4, arr5, arr6};
		return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 2, 3);
	}
	
	@DataProvider
	public Object[][] PayNowPaymentsServiceTests_GetPaymentPlanGivingPPSID_VerifyNegativeCasesDP(ITestContext testContext)
	{
		String[] arr1 = { ENVIRONMENT_FOX8, "00000000", "400"  };
		String[] arr2 = { ENVIRONMENT_FOX8, "7e7eb576-4a49-4d0d-b829-b83", "400" };
		String[] arr3 = { ENVIRONMENT_FOX8, "", "400"  };
		String[] arr4 = { ENVIRONMENT_FOX7, "00000000", "400"  };
		String[] arr5 = { ENVIRONMENT_FOX7, "7e7eb576-4a49-4d0d-b829-b83", "400" };
		String[] arr6 = { ENVIRONMENT_FOX7, "", "400"  };
		
		Object[][] dataSet = new Object[][] { arr1,arr2,arr3, arr4, arr5, arr6};
		return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 3, 3);
	}
}
