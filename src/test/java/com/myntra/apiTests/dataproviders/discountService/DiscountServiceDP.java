package com.myntra.apiTests.dataproviders.discountService;

import com.myntra.lordoftherings.Toolbox;

import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

public class DiscountServiceDP
{
	
	public long currentTime = System.currentTimeMillis();
	
	String startDate = String.valueOf((currentTime/1000));
	String expiredDate = String.valueOf((currentTime+400000)/1000);
	
	@DataProvider
	public Object[][] getDiscountByIdPositiveCasesDataProvider(ITestContext testContext)
	{
		String[] discount1 = { "38430" };
		String[] discount2 = { "38429" };

		/*
		 * String[] discount10 = {"%^^&%&"}; String[] discount11 = {"%^^&%&"};
		 * String[] discount12 = {"%^^&%&"}; String[] discount13 = {"%^^&%&"};
		 * String[] discount14 = {"%^^&%&"};
		 */

		Object[][] dataSet = new Object[][] { discount1, discount2 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] getDiscountByIdNegativeCasesDataProvider(ITestContext testContext)
	{
		String[] discount1 = { "aksdjhkasghda" };
//		String[] discount2 = { "<html>" };
//		String[] discount3 = { "//////" };
//		String[] discount4 = { "%^^&%&" };
		String[] discount5 = { "19723917843972149792174921" };
		String[] discount6 = { "12.54" };
		//String[] discount7 = { "0" };
		/*
		 * String[] discount10 = {"%^^&%&"}; String[] discount11 = {"%^^&%&"};
		 * String[] discount12 = {"%^^&%&"}; String[] discount13 = {"%^^&%&"};
		 * String[] discount14 = {"%^^&%&"};
		 */

		Object[][] dataSet = new Object[][] { discount1, discount5, discount6}; //discount2, discount3,discount4
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 7, 2);
	}
	
	@DataProvider
	public Object[][] createFlatDiscountDataProvider(ITestContext testContext)
	{
		
		// Fourth parameter should be the style id which is not associated with any discount rule
		// get the list of unused style ids from discount rule - 21095 
		String[] arr1 = {expiredDate,startDate,"22", "1553"};
		

		
		Object [][] dataSet = new Object [][] {arr1};
		
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 2);
				
	}
	
	@DataProvider
	public Object[][] createFlatConditionalDiscountDataProvider(ITestContext testContext)
	{
		
		// Fifth parameter should be the style id which is not associated with any discount rule
		// This particular combination is for - Buy Count - Get Amount
		String[] arr1 = {expiredDate,startDate,"3", "400" ,"1554"};

		
		Object [][] dataSet = new Object [][] {arr1};
		
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 2);
				
	}
	
	@DataProvider
	public Object[][] createFreeItemDiscountDataProvider(ITestContext testContext)
	{
		
		// Fifth parameter should be the style id which is not associated with any discount rule
		
		String[] arr1 = {expiredDate,startDate,"2999", "34124" ,"1555"};

		
		Object [][] dataSet = new Object [][] {arr1};
		
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 2);
				
	}
	
	@DataProvider
	public Object[][] createBuyXGetYDiscountDataProvider(ITestContext testContext)
	{
		
		// Fifth parameter should be the style id which is not associated with any discount rule
		String[] arr1 = {expiredDate,startDate,"3", "1" ,"1558"};

		
		Object [][] dataSet = new Object [][] {arr1};
		
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 2);
				
	}
	
	@DataProvider
	public Object[][] createCartLevelDiscountDataProvider(ITestContext testContext)
	{
		
		String[] arr1 = {expiredDate,startDate,"2499", "30" ,"bata","beatles"};

		
		Object [][] dataSet = new Object [][] {arr1};
		
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 2);
				
	}
	
	@DataProvider
	public Object[][] updateDiscountDataProvider(ITestContext testContext)
	{
		
		String[] arr1 = {"5974","Updated Discount Name",expiredDate,startDate,"1561"};

		
		Object [][] dataSet = new Object [][] {arr1};
		
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 2);
				
	}

}
