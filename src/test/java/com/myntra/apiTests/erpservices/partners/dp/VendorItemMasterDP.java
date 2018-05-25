package com.myntra.apiTests.erpservices.partners.dp;

import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import com.myntra.apiTests.erpservices.partners.VMSHelper;
import com.myntra.lordoftherings.Toolbox;
import com.myntra.vms.client.code.utils.CommercialType;

public class VendorItemMasterDP {

	static VMSHelper vmsHelper = new VMSHelper();
	static String vendorId = vmsHelper.getVendorIdFromVIM();

	@DataProvider
	public static Object[][] createVim(ITestContext testContext) {

		Object[] arr1 = { "960", "828701", "ATNADRSS00002", "ATNADRSS00002", "247179", "Athena", CommercialType.JIT,
				CommercialType.SOR, CommercialType.OUTRIGHT, "100", "150" };
		Object[] arr2 = { "960", "828701", "ATNADRSS00001", "ATNADRSS00001", "247179", "Athena", CommercialType.JIT,
				CommercialType.SOR, CommercialType.OUTRIGHT, "100", "150" };

		Object[][] dataSet = new Object[][] { arr1, arr2 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	@DataProvider
	public static Object[][] createVimInvalidVendorId(ITestContext testContext) {

		Object[] arr1 = { "0", "828701", "ATNADRSS00002", "ATNADRSS00002", "247179", "Athena", CommercialType.JIT,
				CommercialType.SOR, CommercialType.OUTRIGHT, "100", "150" };
		Object[] arr2 = { "-1", "828701", "ATNADRSS00001", "ATNADRSS00001", "247179", "Athena", CommercialType.JIT,
				CommercialType.SOR, CommercialType.OUTRIGHT, "100", "150" };

		Object[][] dataSet = new Object[][] { arr1, arr2 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	@DataProvider
	public static Object[][] createVimInvalidBrand(ITestContext testContext) {

		Object[] arr1 = { "960", "828701", "ATNADRSS00002", "ATNADRSS00002", "247179", "abc", CommercialType.JIT,
				CommercialType.SOR, CommercialType.OUTRIGHT, "100", "150" };
		Object[] arr2 = { "960", "828701", "ATNADRSS00001", "ATNADRSS00001", "247179", "123", CommercialType.JIT,
				CommercialType.SOR, CommercialType.OUTRIGHT, "100", "150" };

		Object[][] dataSet = new Object[][] { arr1, arr2 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	@DataProvider
	public static Object[][] editVim(ITestContext testContext) {

		Object[] arr1 = { "960", "828701", "ATNADRSS00003", "ATNADRSS00003", "247179", "Athena", CommercialType.JIT,
				"100", "150" };

		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	@DataProvider
	public static Object[][] commercialType(ITestContext testContext) {

		Object[] arr1 = { vendorId };

		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	@DataProvider
	public static Object[][] commercialTypeNegative(ITestContext testContext) {

		Object[] arr1 = { "-1" };

		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

}
