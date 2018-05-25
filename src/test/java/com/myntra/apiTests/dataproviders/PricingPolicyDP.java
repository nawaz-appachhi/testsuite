package com.myntra.apiTests.dataproviders;

import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import com.myntra.lordoftherings.Toolbox;

import javax.tools.Tool;

public class PricingPolicyDP {
	@DataProvider
	public Object[][] getinternalpricingpolicy(ITestContext iTestContext) {
		String arr1[] = { "OUTRIGHT" };
		String arr2[] = { "SOR" };
		Object[][] dataSet = { arr1, arr2 };
		return Toolbox.returnReducedDataSet(dataSet,
				iTestContext.getIncludedGroups(), 2, 2);
	}

	@DataProvider
	public Object[][] createInternalPolicy (ITestContext iTestContext) {
		String [] outrightMin = {"min", String.valueOf(33.5), String.valueOf(30), String.valueOf(50), "OUTRIGHT"};
		String [] outrightMax = {"max", String.valueOf(33.5), String.valueOf(30), String.valueOf(50), "OUTRIGHT"};
		String [] saleOrReturnMin = {"min", String.valueOf(33.5), String.valueOf(30), String.valueOf(50), "SOR"};
		String [] saleOrReturnMax = {"max", String.valueOf(33.5), String.valueOf(30), String.valueOf(50), "SOR"};
		String [] myntraFashionBrandMin = {"min", String.valueOf(33.5), String.valueOf(30), String.valueOf(50), "MFB"};
		String [] myntraFashionBrandMax = {"max", String.valueOf(33.5), String.valueOf(30), String.valueOf(50), "MFB"};
		String [] myntraMarketBrandMin = {"min", String.valueOf(33.5), String.valueOf(30), String.valueOf(50), "MMP"};
		String [] myntraMarketBrandMax = {"max", String.valueOf(33.5), String.valueOf(30), String.valueOf(50), "MMP"};

		Object [][] dataSet = {outrightMin, outrightMax, saleOrReturnMin, saleOrReturnMax, myntraFashionBrandMin,
				myntraFashionBrandMax, myntraMarketBrandMin, myntraMarketBrandMax};
		return Toolbox.returnReducedDataSet(dataSet, iTestContext.getIncludedGroups(), 8,8);
	}

	@DataProvider
	public Object[][] createFeatureGate (ITestContext iTestContext) {
		String [] data1 = {"true", "true", "true", "true", "true", "true", "true", "true", "false", "0", "100"};
		String [] data2 = {"true", "false", "true", "false", "true", "false", "true", "false", "false", "0", "100"};
		String [] data3 = {"true", "false", "true", "false", "true", "false", "true", "false", "false", "20", "70"};

		Object [][] dataSet = {data1, data2, data3};
		return Toolbox.returnReducedDataSet(dataSet, iTestContext.getIncludedGroups(), 3, 3);
	}

	@DataProvider
	public Object[][] createFeatureGateNegativeCases (ITestContext iTestContext) {
		String [] data1 = {"true", "true", "true", "true", "true", "true", "true", "true", "true", "-10", "30"};
		String [] data2 = {"true", "true", "true", "true", "true", "true", "true", "true", "false", "0", "110"};
		String [] data3 = {"true", "true", "true", "true", "true", "true", "true", "true", "false", "20", "10"};

		Object [][] dataSet = {data1, data2, data3};
		return Toolbox.returnReducedDataSet(dataSet, iTestContext.getIncludedGroups(), 3, 3);
	}

	@DataProvider
	public Object[][] createUserRole (ITestContext iTestContext) {
		String [] data1 = {"kiran.badam@myntra.com", "\"CM\" , \"CMAdmin\", \"Finance\""};
		Object [][] dataSet = {data1};
		return Toolbox.returnReducedDataSet(dataSet, iTestContext.getIncludedGroups(), 1, 1);
	}
}
