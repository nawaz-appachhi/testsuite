package com.myntra.apiTests.dataproviders;

import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import com.myntra.lordoftherings.Toolbox;

public class tdRangeDP {
	
	
	@DataProvider
	public Object[][] getstyleithData(ITestContext iTestContext) {
		String[] arr1 = {"0","3","OUTRIGHT"};
		String[] arr2 = {"0","4","OUTRIGHT"};
		String[] arr3 = {"0","5","OUTRIGHT"};
		String[] arr4 = {"0","6","OUTRIGHT"};
	
		Object[][] dataSet = { arr1,arr2,arr3,arr4};
		return Toolbox.returnReducedDataSet(dataSet,
				iTestContext.getIncludedGroups(), 4, 4);
	}

}
