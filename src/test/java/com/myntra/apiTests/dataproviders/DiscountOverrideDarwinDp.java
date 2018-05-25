package com.myntra.apiTests.dataproviders;

import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import com.myntra.lordoftherings.Toolbox;

public class DiscountOverrideDarwinDp {
	
	
	@DataProvider
	public Object[][] getstyleithData(ITestContext iTestContext) {
		String[] arr1 = {"0","5","OUTRIGHT","default"};
	
		Object[][] dataSet = { arr1};
		return Toolbox.returnReducedDataSet(dataSet,
				iTestContext.getIncludedGroups(), 1, 1);
	}
	@DataProvider
	public Object[][] getstyleithDataPg4(ITestContext iTestContext) {
		String[] arr1 = {"0","4","OUTRIGHT","default"};
	
		Object[][] dataSet = { arr1};
		return Toolbox.returnReducedDataSet(dataSet,
				iTestContext.getIncludedGroups(), 1, 1);
	}

	
	@DataProvider
	public Object[][] groupingOfstyleithDataForDoSeller(ITestContext iTestContext) {
		String[] arr1 = {"0","5","OUTRIGHT","default","1","Active","SELLER","null", "10"};
	
		Object[][] dataSet = { arr1};
		return Toolbox.returnReducedDataSet(dataSet,
				iTestContext.getIncludedGroups(), 1, 1);
	}
	
	@DataProvider
	public Object[][] groupingOfstyleithDataForDoEOSSVFnull(ITestContext iTestContext) {
		String[] arr1 = {"0","5","OUTRIGHT","default","1","Active","EOSS","8", "null"};
	
		Object[][] dataSet = { arr1};
		return Toolbox.returnReducedDataSet(dataSet,
				iTestContext.getIncludedGroups(), 1, 1);
	}
	
	@DataProvider
	public Object[][] groupingOfstyleithDataForDoEOSSWithVfandWithoutTradeDiscount(ITestContext iTestContext) {
		String[] arr1 = {"0","5","OUTRIGHT","default","1","Active","EOSS","null", "8"};
	
		Object[][] dataSet = { arr1};
		return Toolbox.returnReducedDataSet(dataSet,
				iTestContext.getIncludedGroups(), 1, 1);
	}
	
	@DataProvider
	public Object[][] groupingOfstyleithDataToV_FP_D_FT_Seller(ITestContext iTestContext) {
		String[] arr1 = {"0","5","OUTRIGHT","default","1","Active","SELLER","9", "8"};
	
		Object[][] dataSet = { arr1};
		return Toolbox.returnReducedDataSet(dataSet,
				iTestContext.getIncludedGroups(), 1, 1);
	}
	
	@DataProvider
	public Object[][] tlpFilter_False(ITestContext iTestContext) {
		String[] arr1 = {"5","REVBUMP","Sports","Sports","CATEGORY","INSEASON","EOSS","FW15","FW15","0","500"};  // "FW15","FW15"
	
		Object[][] dataSet = { arr1};
		return Toolbox.returnReducedDataSet(dataSet,
				iTestContext.getIncludedGroups(), 1, 1);
	}
	
	@DataProvider
	public Object[][] tlpFilter_true(ITestContext iTestContext) {
		String[] arr1 = {"6","REVBUMP","CATEGORY","INSEASON","EOSS","0","500"};  // "FW15","FW15"
	
		Object[][] dataSet = { arr1};
		return Toolbox.returnReducedDataSet(dataSet,
				iTestContext.getIncludedGroups(), 1, 1);
	}
	
	@DataProvider
	public Object[][] tlpFilter_true_EOSSand_INSEASON_SOR(ITestContext iTestContext) {
		String[] arr1 = {"6","REVBUMP","Sports","Sports","EOSS_SOR","INSEASON_SOR","INSEASON","EOSS","FW15","FW15","0","500"};  // "FW15","FW15"
	
		Object[][] dataSet = { arr1};
		return Toolbox.returnReducedDataSet(dataSet,
				iTestContext.getIncludedGroups(), 1, 1);
	}
	@DataProvider
	public Object[][] groupingOfstyleithData_Eoss_TDNotNull_VFNull(ITestContext iTestContext) {
		String[] arr1 = {"0","5","OUTRIGHT","default","1","Active","EOSS","8", "9"};
	
		Object[][] dataSet = { arr1};
		return Toolbox.returnReducedDataSet(dataSet,
				iTestContext.getIncludedGroups(), 1, 1);
	}

	@DataProvider
	public Object[][] getAllDiscountOverrides(ITestContext iTestContext) {
		String[] arr1 = {"Active","FlatPercent","imran.khan2@myntra.com"};
	
		Object[][] dataSet = { arr1};
		return Toolbox.returnReducedDataSet(dataSet,
				iTestContext.getIncludedGroups(), 1, 1);
	}
	
	@DataProvider
	public Object[][] getstyleithDataPg5(ITestContext iTestContext) {
		String[] arr1 = {"5","19","OUTRIGHT","default","FlatPercent","EOSS"};
	
		Object[][] dataSet = { arr1};
		return Toolbox.returnReducedDataSet(dataSet,
				iTestContext.getIncludedGroups(), 1, 1);
	}
	@DataProvider
	public Object[][] getstyleithDataCfTopUpRupeeOff(ITestContext iTestContext) {
		String[] arr1 = {"5","5","OUTRIGHT","default","FlatRupee","EOSS"};
	
		Object[][] dataSet = { arr1};
		return Toolbox.returnReducedDataSet(dataSet,
				iTestContext.getIncludedGroups(), 1, 1);
	}
	@DataProvider
	public Object[][] getTopUpType(ITestContext iTestContext) {
		String[] arr1 = {"FlatPercent","EOSS","Active"};
	
		Object[][] dataSet = { arr1};
		return Toolbox.returnReducedDataSet(dataSet,
				iTestContext.getIncludedGroups(), 1, 1);
	}
	
	@DataProvider
	public Object[][] getEventTypeFlatPercentCfVfmoreThan100(ITestContext iTestContext) {
		String[] arr1 = {"FlatPercent","60","32"};
	
		Object[][] dataSet = { arr1};
		return Toolbox.returnReducedDataSet(dataSet,
				iTestContext.getIncludedGroups(), 1, 1);
	}
	@DataProvider
	public Object[][] getEvent(ITestContext iTestContext) {
		String[] arr1 = {"0"};
	
		Object[][] dataSet = { arr1};
		return Toolbox.returnReducedDataSet(dataSet,
				iTestContext.getIncludedGroups(), 1, 1);
	}
	
	@DataProvider
	public Object[][] createEventFlatConditional(ITestContext iTestContext) {
		String[] arr1 = {"FlatConditional"};
	
		Object[][] dataSet = { arr1};
		return Toolbox.returnReducedDataSet(dataSet,
				iTestContext.getIncludedGroups(), 1, 1);
	}
	@DataProvider
	public Object[][] getEventTypeFlatPercentValidDiscount(ITestContext iTestContext) {
		String[] arr1 = {"FlatPercent","20","10"};
	
		Object[][] dataSet = { arr1};
		return Toolbox.returnReducedDataSet(dataSet,
				iTestContext.getIncludedGroups(), 1, 1);
	}
	@DataProvider
	public Object[][] createEventTypeFlatPercentCfVf(ITestContext iTestContext) {
		String[] arr1 = {"FlatPercent","20","12"};
	
		Object[][] dataSet = { arr1};
		return Toolbox.returnReducedDataSet(dataSet,
				iTestContext.getIncludedGroups(), 1, 1);
	}
}
