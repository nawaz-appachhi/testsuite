package com.myntra.apiTests.dataproviders;

import com.myntra.lordoftherings.Toolbox;
import com.myntra.apiTests.common.APINAME;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

public class StyleDP
{
	@DataProvider
	public Object[][] dp_getstyledetails(ITestContext iTestContext)
	{
		APINAME apiundertest = APINAME.GETSTYLEDETAILS;
		String styleid1 = "157457";
		String styleid2 = "38966";

		Object[][] dataSet = new Object[][] {
				new Object[] { apiundertest, styleid1 },
				new Object[] { apiundertest, styleid2 }, };
		return Toolbox.returnReducedDataSet(dataSet,
				iTestContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public Object[][] styleIdAjaxSearchDataProvider(ITestContext iTestContext)
	{
		// APINAME apiundertest = APINAME.STYLEIDS2;
		// Payload pl = new Payload(apiundertest);
		String[] arr1 = { "Nike", "1 TO *", "0", "50", "false" };
		String[] arr2 = { "Puma", "1 TO *", "0", "50", "false" };
		String[] arr3 = { "Biba", "1 TO *", "0", "50", "false" };
		String[] arr4 = { "Nike", "1 TO *", "0", "50", "true" };
		String[] arr5 = { "Puma", "1 TO *", "0", "50", "true" };
		String[] arr6 = { "Biba", "1 TO *", "0", "50", "true" };
		String[] arr7 = { "Nike", "1 TO *", "0", "5000", "false" };
		String[] arr8 = { "Puma", "1 TO *", "0", "5000", "false" };
		String[] arr9 = { "Biba", "1 TO *", "0", "5000", "false" };
		String[] arr10 = { "Nike", "1 TO *", "500", "5000", "false" };
		String[] arr11 = { "Puma", "1 TO *", "500", "5000", "false" };
		String[] arr12 = { "Biba", "1 TO *", "500", "5000", "false" };
		String[] arr13 = {
				"Biba",
				"1 TO *",
				"500",
				"59898989898982179837194789237497329874987239847983274987329874932878927998989000",
				"false" };
		String[] arr14 = { "Nike", "1 TO *", "500", "KJAHKJH", "false" };
		String[] arr15 = { "Puma", "1 TO *", "500", "IYIYI", "false" };

		/*
		 * Object[][] dataSet = new Object[][] { new Object[] { arr1 }, new
		 * Object[] { arr2 }, new Object[] { arr3 }, new Object[] { arr4 }, new
		 * Object[] { arr5 }, new Object[] { arr6 }, new Object[] { arr7 }, new
		 * Object[] { arr8 }, new Object[] { arr9 }, new Object[] { arr10 }, new
		 * Object[] { arr11 }, new Object[] { arr12 }, new Object[] { arr13 },
		 * new Object[] { arr14 }, new Object[] { arr15 } };
		 */
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5,
				arr6, arr7, arr8, arr9, arr10, arr11, arr12, arr13, arr14,
				arr15 };
		return Toolbox.returnReducedDataSet(dataSet,
				iTestContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public Object[][] getStyleByIdDataProvider(ITestContext iTestContext)
	{
		String [] arr1 = {"174465"};
		String [] arr2 = {"247953"};

		Object[][] dataSet = new Object[][] { arr1,arr2  };
		return Toolbox.returnReducedDataSet(dataSet,
				iTestContext.getIncludedGroups(), 1, 2);
	}
}
