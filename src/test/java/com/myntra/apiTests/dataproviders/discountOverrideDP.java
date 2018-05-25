package com.myntra.apiTests.dataproviders;

import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import com.myntra.lordoftherings.Toolbox;

public class discountOverrideDP {
	
	
	
	@DataProvider
	public Object[][] getstyleithData(ITestContext iTestContext) {
		String[] arr1 = {"0","5","OUTRIGHT","default"};
	
		Object[][] dataSet = { arr1};
		return Toolbox.returnReducedDataSet(dataSet,
				iTestContext.getIncludedGroups(), 1, 1);
	}

	@DataProvider
	public Object[][] getDiscountOverrideData(ITestContext iTestContext) {
		String[] arr1 = {"default"};
	
		Object[][] dataSet = { arr1};
		return Toolbox.returnReducedDataSet(dataSet,
				iTestContext.getIncludedGroups(), 1, 1);
	}
	
	@DataProvider
	public Object[][] getstyleithDataForDo(ITestContext iTestContext) {
		String[] arr1 = {"0","5","OUTRIGHT","default","1","Active"};
	
		Object[][] dataSet = { arr1};
		return Toolbox.returnReducedDataSet(dataSet,
				iTestContext.getIncludedGroups(), 1, 1);
	}
	
	@DataProvider
	public Object[][] groupingOfstyleithDataForDo(ITestContext iTestContext) {
		String[] arr1 = {"0","5","OUTRIGHT","default","1","Active"};
	
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
	public Object[][] groupingOfstyleithData_Eoss_TDNull_VFNull(ITestContext iTestContext) {
		String[] arr1 = {"0","5","OUTRIGHT","default","1","Active","EOSS","null", "null"};
	
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
}
