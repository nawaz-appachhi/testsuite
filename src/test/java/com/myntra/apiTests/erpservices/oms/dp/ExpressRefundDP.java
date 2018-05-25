package com.myntra.apiTests.erpservices.oms.dp;

import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import com.myntra.lordoftherings.Toolbox;

public class ExpressRefundDP {

	@DataProvider(name = "expressrefundDP")
	public static Object[][] expressrefundDP(ITestContext testContext) {
		try {
			Object[] arr1 = {"XPRESS"};
			String[] arr2 = {"SDD"};
			Object[][] dataSet = new Object[][] {arr1, arr2};
			return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 11, 11);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
