package com.myntra.apiTests.erpservices.partners.dp;

import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import com.myntra.lordoftherings.Toolbox;

public class InwardDP {
	
	@DataProvider 
	public static Object [][] getCapacity(ITestContext testContext)
	{
			
		Object[] arr1 = {"MMB"};
		Object[] arr2 = {"MFB"};
		
		
		Object[][] dataSet = new Object[][] { arr1,arr2 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 5, 6);
	}

}
