package com.myntra.apiTests.dataproviders;

import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import com.myntra.lordoftherings.Toolbox;

public class pimDP {
	@DataProvider
	public Object[][] getApps(ITestContext testContext)
	{
		
			// paramOrderId, paramRespCode
			String[] arr1 = {"MYNTRA_MOBILE_APP"};
			
			Object[][] dataSet = new Object[][] { arr1};
			return Toolbox.returnReducedDataSet(dataSet,
					testContext.getIncludedGroups(), 1, 2);
		}

		
	}


