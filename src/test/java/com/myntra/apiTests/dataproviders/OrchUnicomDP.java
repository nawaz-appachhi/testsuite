package com.myntra.apiTests.dataproviders;

import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import com.myntra.lordoftherings.Toolbox;

public class OrchUnicomDP {
	
	@DataProvider 
	public Object [][] getItemDetails (ITestContext testContext)
	{
		String [] arr1 = {"Item(s) retrieved successfully","SUCCESS"};
		
		
		Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 5, 6);
	}

}
