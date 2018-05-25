package com.myntra.apiTests.portalservices.payments;

import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.Toolbox;

public class PaymentsServiceDP {

	
	static Initialize init = new Initialize("/Data/configuration");
	
	@DataProvider
	public static Object[][] formParametersOnline(ITestContext testContext)
	{
		// TC desc, email, pwd, pm, isAutoGCEnable, isAutoGCOrder, isManualGCEnable, isManualGCOrder, isMyntOrder,isLoyalty,isGiftWrap,isTryNBuy, isCancel, isReturn, isExchange
		String[] arr1= {"TC : ",
				"automationpps1@myntra.com","123456", "creditcard","false", "false","false","false","false","false","false","false","false","false","false"};
		Object[][] dataSet = new Object[][] {arr1};
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(),1,1);
	}	
	
	
	@DataProvider
	public static Object[][] getSavedCards(ITestContext testContext)
	{
		String[] arr1= {"TC : Get saved cards","automationtest@myntra.com"};
		Object[][] dataSet = new Object[][] {arr1};
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(),1,1);
	}
	
	@DataProvider
	public static Object[][] addSavedCards(ITestContext testContext)
	{
		String[] arr1= {"TC : Add saved cards","automationtest@myntra.com","371448636398431","04","20","automation test"};
		Object[][] dataSet = new Object[][] {arr1};
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(),1,1);
	}
	
	
	@DataProvider
	public static Object[][] updateSavedCards(ITestContext testContext)
	{
		String[] arr1= {"TC : Add saved cards","automationtest@myntra.com","12","25"};
		Object[][] dataSet = new Object[][] {arr1};
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(),1,1);
	}
	
	@DataProvider
	public static Object[][] deleteSavedCards(ITestContext testContext)
	{
		String[] arr1= {"TC : Add saved cards","automationtest@myntra.com"};
		Object[][] dataSet = new Object[][] {arr1};
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(),1,1);
	}
	
}