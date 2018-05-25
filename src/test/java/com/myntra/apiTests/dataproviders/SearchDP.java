package com.myntra.apiTests.dataproviders;

import com.myntra.lordoftherings.Toolbox;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

public class SearchDP
{
	@DataProvider
	public Object[][] getFirstPageOfSearchDataProvider(ITestContext iTestContext)
	{
		String arr1 = "Puma";
		String arr2 = "Casual Shoes";
		String arr3 = "157462";
		String arr4 = "Adidas Casual Shoes";
		String arr5 = "Adidas Sarees";
		String arr6 = "dfdfdf";
		String arr7 = "sunglasses";
		String arr8 = "lee tshirt";
		String arr9 = "232sdsd";
		Object[][] data = new Object[][]{
				new Object[] {arr1},
				new Object[] {arr2},
				new Object[] {arr3},
				new Object[] {arr4},
				new Object[] {arr5},
				new Object[] {arr6},
				new Object[] {arr7},
				new Object[] {arr8},
				new Object[] {arr9}			
		};
		return Toolbox.returnReducedDataSet(data,
				iTestContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public Object[][] dp_webservice_applyfilter_positive_cases(
			ITestContext iTestContext)
	{
		// Add Gender as filter
		String[] arr1 = { "add", ":gender", "men" };
		String[] arr2 = { "add", ":gender", "women" };
		String[] arr3 = { "add", ":gender", "boys" };
		String[] arr4 = { "add", ":gender", "girls" };
		// Add Brand as filter
		String[] arr5 = { "add", ":brand", "Nike" };
		String[] arr6 = { "add", ":brand", "Pepe Jeans" };
		String[] arr7 = { "add", ":brand", "Levis" };
		String[] arr8 = { "add", ":brand", "Lee" };
		// Add size as filter
		String[] arr9 = { "add", ":size", "0-1Y" };
		String[] arr10 = { "add", ":size", "26/30" };
		String[] arr11 = { "add", ":size", "40" };
		String[] arr12 = { "add", ":size", "30/L" };
		// Add color as filter
		String[] arr13 = { "add", ":color", "Green" };
		String[] arr14 = { "add", ":color", "Blue" };
		String[] arr15 = { "add", ":color", "Navy" };
		String[] arr16 = { "add", ":color", "Red" };
		// Add discount as filter
		String[] arr17 = { "add", ":discount", "20.0" };
		String[] arr18 = { "add", ":discount", "40.0" };

		// Add mastercategory as filter

		// Add subcategory as filter

		// Add price as filter
		String[] arr19 = { "add", ":price", "9" };
		String[] arr20 = { "add", ":price", "100" };

		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5,
				arr6, arr7, arr8, arr9, arr10, arr11, arr12, arr13, arr14,
				arr15, arr16, arr17, arr18, arr19, arr20 };
		return Toolbox.returnReducedDataSet(dataSet,
				iTestContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public Object[][] dp_Webservice_ApplyTwoFilters_Positive_Cases(
			ITestContext iTestContext)
	{
		// Add two Genders as filter
		String[] arr1 = { "add", ":gender", "men", "add", ":gender", "women" };
		String[] arr2 = { "add", ":gender", "men", "add", ":gender", "boys" };
		String[] arr3 = { "add", ":gender", "men", "add", ":gender", "girls" };

		// Add two brands as filter
		String[] arr4 = { "add", ":brand", "Pepe Jeans", "add", ":brand",
				"Levis" };
		String[] arr5 = { "add", ":brand", "Lee", "add", ":brand", "LOCOMOTIVE" };
		String[] arr6 = { "add", ":brand", "HRX Jeans", "add", ":brand",
				"Flying Machine" };

		// Add two Sizes as filter
		String[] arr7 = { "add", ":size", "0-1Y", "add", ":size", "1-3M" };
		String[] arr8 = { "add", ":size", "24", "add", ":size", "25/32" };
		String[] arr9 = { "add", ":size", "28/M", "add", ":size", "28/L" };

		// Add two colors as filter
		String[] arr10 = { "add", ":color", "Red", "add", ":color", "Navy" };
		String[] arr11 = { "add", ":color", "Yellow", "add", ":color", "Pink" };
		String[] arr12 = { "add", ":color", "Green", "add", ":color", "White" };

		// Add two discounts as filter
		String[] arr13 = { "add", ":discount", "20.0", "add", ":discount",
				"10.0" };
		String[] arr14 = { "add", ":discount", "10.0", "add", ":discount",
				"20.0" };

		// Gender and brand as filter
		String[] arr15 = { "add", ":gender", "men", "add", ":brand", "Levis" };
		String[] arr16 = { "add", ":gender", "women", "add", ":brand", "Lee" };
		String[] arr17 = { "add", ":gender", "boys", "add", ":brand", "Nautica" };
		String[] arr18 = { "add", ":gender", "girls", "add", ":brand",
				"Nautica" };

		// Gender and size as filter
		String[] arr19 = { "add", ":gender", "men", "add", ":size", "30" };
		String[] arr20 = { "add", ":gender", "women", "add", ":size", "27/32" };
		String[] arr21 = { "add", ":gender", "boys", "add", ":size", "8-9Y" };
		String[] arr22 = { "add", ":gender", "girls", "add", ":size", "6-12M" };

		// Gender and color
		String[] arr23 = { "add", ":gender", "men", "add", ":color", "Red" };
		String[] arr24 = { "add", ":gender", "women", "add", ":color", "Red" };
		String[] arr25 = { "add", ":gender", "boys", "add", ":color", "Blue" };
		String[] arr26 = { "add", ":gender", "girls", "add", ":color", "Blue" };

		// Gender and discount
		String[] arr27 = { "add", ":gender", "men", "add", ":discount", "10.0" };
		String[] arr28 = { "add", ":gender", "women", "add", ":discount",
				"10.0" };
		String[] arr29 = { "add", ":gender", "boys", "add", ":discount", "0.0" };
		String[] arr30 = { "add", ":gender", "girls", "add", ":discount",
				"10.0" };

		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5,
				arr6, arr7, arr8, arr9, arr10, arr11, arr12, arr13, arr14,
				arr15, arr16, arr17, arr18, arr19, arr20, arr21, arr22, arr23,
				arr24, arr25, arr26, arr27, arr28, arr29, arr30 };
		return Toolbox.returnReducedDataSet(dataSet,
				iTestContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public Object[][] dp_webservice_applyAllfilters_positive_cases(
			ITestContext iTestContext)
	{
		// Add Gender as filter
		String[] arr1 = { "add", ":gender", "men", "add", ":brand", "Levis",
				"add", ":size", "30", "add", ":color", "Red", "add",
				":discount", "0.0" };
		Object[][] dataSet = new Object[][] { arr1  };
		return Toolbox.returnReducedDataSet(dataSet,
				iTestContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public Object[][] mobileSearchSortByDataProvider(ITestContext iTestContext)
	{
		String arr1 = ":popularity";
		String arr2 = ":discount";
		String arr3 = ":new";
		String arr4 = ":high";
		String arr5 = ":low";

		Object[][] dataSet = new Object[][] { { arr1 }, { arr2 }, { arr3 },
				{ arr4 }, { arr5 } };
		return Toolbox.returnReducedDataSet(dataSet,
				iTestContext.getIncludedGroups(), 5, 5);
	}

	@DataProvider
	public Object[][] mobileGetNextPageOfSearchDataProvider(
			ITestContext iTestContext)
	{
		String[] arr1 = { "next", "30" };
		String[] arr2 = { "next", "50" };
		//String[] arr3 = { "next", "-1" };
		//String[] arr4 = { "prev", "30" };
		//String[] arr5 = { "prev", "50" };

		Object[][] dataSet = new Object[][] { arr1, arr2 };
		return Toolbox.returnReducedDataSet(dataSet,
				iTestContext.getIncludedGroups(), 5, 5);
	}

	@DataProvider
	public Object[][] mobileWebServicesRemoveFilterDataProvider(
			ITestContext iTestContext)
	{
		String[] arr1 = { "remove", ":gender", "male" };
		String[] arr2 = { "remove", ":color", "Red" };
		String[] arr3 = { "remove", ":discount", "10.0" };
		String[] arr4 = { "remove", ":price", "10" };
		String[] arr5 = { "remove", ":brand", "nike" };
		String[] arr6 = { "remove", ":size", "40" };
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5,
				arr6 };
		return Toolbox.returnReducedDataSet(dataSet,
				iTestContext.getIncludedGroups(), 6, 6);
	}

}