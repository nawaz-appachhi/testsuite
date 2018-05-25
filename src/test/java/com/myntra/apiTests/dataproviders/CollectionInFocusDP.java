package com.myntra.apiTests.dataproviders;

import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import com.myntra.lordoftherings.Toolbox;

public class CollectionInFocusDP {
	
	
	@DataProvider
	public Object[][] collectionInFocusDP(ITestContext iTestContext) {
		String[] arr1 = {"0","3","OUTRIGHT","5","Created by manish kumar"};
		String[] arr2 = {"0","5","OUTRIGHT","4","Created by manish kumar"};
		String[] arr3 = {"0","10","OUTRIGHT","7","Created by manish kumar"};
		String[] arr4 = {"0","15","OUTRIGHT","6","Created by manish kumar"};
	
		Object[][] dataSet = { arr1,arr2,arr3,arr4};
		return Toolbox.returnReducedDataSet(dataSet,
				iTestContext.getIncludedGroups(), 4, 4);
	}

}
