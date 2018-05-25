package com.myntra.apiTests.dataproviders;

import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import com.myntra.lordoftherings.Toolbox;

public class ManufacturingDP {
	
	@DataProvider 
	public Object [][] getDefects (ITestContext testContext)
	{
		String [] arr1 = {"3","Success","SUCCESS"};
		
		
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 4, 5);
	}
	
	
	@DataProvider 
	public Object [][] getAuditRequest (ITestContext testContext)
	{
		String [] arr1 = {"1","3","Success","SUCCESS"};
		
		
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 4, 5);
	}

}
