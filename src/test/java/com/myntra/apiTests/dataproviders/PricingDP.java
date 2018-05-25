package com.myntra.apiTests.dataproviders;

import java.util.Random;

import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import com.myntra.lordoftherings.Toolbox;

public class PricingDP {

	
	@DataProvider
	public Object[][] getstyleithData(ITestContext iTestContext) {
		String[] arr1 = {"0","3","OUTRIGHT","0","2000"};
		String[] arr2 = {"0","4","OUTRIGHT","0","1000"};
		String[] arr3 = {"0","5","OUTRIGHT","0","3000"};

		String[] arr4 = {"0","5","OUTRIGHT","0","4000"};

		

	
		Object[][] dataSet = { arr1,arr2,arr3,arr4};
		return Toolbox.returnReducedDataSet(dataSet,
				iTestContext.getIncludedGroups(), 4, 4);
	}
	
	
	
	
	@DataProvider
	public Object[][] setBasline(ITestContext iTestContext) {
		String[] arr1 = {"BASELINE"};
	
		Object[][] dataSet = { arr1};
		return Toolbox.returnReducedDataSet(dataSet,
				iTestContext.getIncludedGroups(), 1, 1);
	}
	private String generateRandomString()
	{
		int leftLimit = 97; // letter 'a'
		int rightLimit = 122; // letter 'z'
		int targetStringLength = 5;
		StringBuilder buffer = new StringBuilder(targetStringLength);
		for (int i = 0; i < targetStringLength; i++) {
		int randomLimitedInt = leftLimit + (int) 
		(new Random().nextFloat() * (rightLimit - leftLimit));
		buffer.append((char) randomLimitedInt);
		}
		String generatedString = buffer.toString();
		System.out.println(generatedString);
		return generatedString;
	}
	
	
}
