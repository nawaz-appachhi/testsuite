package com.myntra.apiTests.inbound.dp;

import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import com.myntra.apiTests.inbound.helper.SpotConstants.AMAZON_LIST;
import com.myntra.lordoftherings.Toolbox;

public class CrawlerDP {
	@DataProvider(name = "amazonTshirtListKeys")
	public static Object[][] amazonTshirtListKeys(ITestContext testContext) throws Exception {
		Object[] arr1 = { AMAZON_LIST.ASIN, AMAZON_LIST.TITLE, AMAZON_LIST.BRANDNAME, AMAZON_LIST.LISTPRICE, AMAZON_LIST.PRICE};
		Object[] lstKeys={arr1};
		Object[][] dataSet = new Object[][] { lstKeys, };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 3, 3);

	}

}
