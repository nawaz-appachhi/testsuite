package com.myntra.apiTests.dataproviders;

import com.myntra.lordoftherings.Toolbox;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

public class SearchDataDP
{
	@DataProvider
	public Object[][] searchDataQueryDataProvider(ITestContext iTestContext)
	{
		Object[][] dataSet = new Object[][] { { "Nike" }, { "Puma" },
				{ "Shoes" }, { "Roadster" } };
		return Toolbox.returnReducedDataSet(dataSet,
				iTestContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public Object[][] searchDataWithOneFilterDataProvider(
			ITestContext iTestContext)
	{
		Object[][] dataSet = new Object[][] {
				{ "Nike", "categories", "Sports&#32;Shoes" },
				{ "Dress", "brands", "Anouk" } };
		return Toolbox.returnReducedDataSet(dataSet,
				iTestContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public Object[][] searchDataWithTwoFiltersDataProvider(
			ITestContext iTestContext)
	{
		Object[][] dataSet = new Object[][] { { "Dress", "brands", "Anouk",
				"colour", "blue" } };
		return Toolbox.returnReducedDataSet(dataSet,
				iTestContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public Object[][] searchDataWithAllFiltersDataProvider(
			ITestContext iTestContext)
	{
		Object[][] dataSet = new Object[][] { { "Jeans", "brands",
				"Lee", "colour", "blue", "discount", "20.0", "gender",
				"men", "size", "30" } };
		return Toolbox.returnReducedDataSet(dataSet,
				iTestContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public Object[][] searchDataSortByDataProvider(ITestContext iTestContext)
	{
		Object[][] dataSet = new Object[][] { { "Shoes", "popularity" },
				{ "Dressberry", "new" }, { "Shirts", "discount" },
				{ "Tops", "low" }, { "Heels", "high" } };
		return Toolbox.returnReducedDataSet(dataSet,
				iTestContext.getIncludedGroups(), 1, 2);
	}
}