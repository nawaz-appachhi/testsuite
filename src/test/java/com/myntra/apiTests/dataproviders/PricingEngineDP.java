package com.myntra.apiTests.dataproviders;

import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import com.myntra.lordoftherings.Toolbox;

public class PricingEngineDP {
	
	@DataProvider
	public Object[][] getNegativeInstanceId(ITestContext iTestContext) {
		String[] arr1 = {"ccaheyfjsksdsdksdksk"};
		Object[][] dataSet = { arr1};
		return Toolbox.returnReducedDataSet(dataSet,
				iTestContext.getIncludedGroups(), 1, 1);
	}
	
	@DataProvider
	public Object[][] getNegativeInstanceId1(ITestContext iTestContext) {
		String[] arr1 = {""};
		Object[][] dataSet = { arr1};
		return Toolbox.returnReducedDataSet(dataSet,
				iTestContext.getIncludedGroups(), 1, 1);
	}

}
