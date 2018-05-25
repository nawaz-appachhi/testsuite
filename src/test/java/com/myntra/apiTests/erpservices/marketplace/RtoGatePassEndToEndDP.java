package com.myntra.apiTests.erpservices.marketplace;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import com.myntra.lordoftherings.Toolbox;

public class RtoGatePassEndToEndDP {
	
	@DataProvider 
	public static Object [][] createGatePass_StockTransfer (ITestContext testContext)
	{
		String expected = "status.statusCode==8503::"
				+ "status.statusMessage=='Gatepass created successfully'::"
				+ "status.statusType=='SUCCESS'";
		String [] arr1 = {"1", "45", "STOCK_TRANSFER_RTO",expected};
		
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 5, 6);
	}
	
	@DataProvider 
	public static Object [][] createGatePassOrder_ST (ITestContext testContext)
	{
		String expected = "status.statusCode==8500::"
			+ "status.statusMessage=='Item added successfully'::"
			+ "status.statusType=='SUCCESS'";
			String [] arr1 = {expected};
		
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 9, 10);
	}
	
	@DataProvider 
	public static Object [][] statusUpdateGatepass (ITestContext testContext)
	{	
		String expected = "status.statusCode==8505::"
				+ "status.statusMessage=='Gatepass updated successfully'::"
				+ "status.statusType=='SUCCESS'";
		String [] arr1 = { expected};
		
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 9, 10);
	}
	
	@DataProvider 
	public static Object [][] updateGatepassOrder (ITestContext testContext)
	{

		String expected = "status.statusCode==8502::"
				+ "status.statusMessage=='Item updated successfully'::"
				+ "status.statusType=='SUCCESS'";
		String [] arr1 = { expected};
		
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 9, 10);
	}
	
	@DataProvider 
	public static Object [][] statusUpdateGatepass_Closed (ITestContext testContext)
	{ 
		String expected = "status.statusCode==8505::"
			+ "status.statusMessage=='Gatepass updated successfully'::"
			+ "status.statusType=='SUCCESS'";
		String [] arr1 = { expected};
		
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 9, 10);
	}
	
	@DataProvider 
	public static Object [][] createGatePassRTV(ITestContext testContext)
	{
		String expected = "status.statusCode==8503::"
			+ "status.statusMessage=='Gatepass created successfully'::"
			+ "status.statusType=='SUCCESS'";
		String [] arr1 = {"45","5","RETURN_TO_SELLER_RTO",expected};
		
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 5, 6);
	}
	
	@DataProvider 
	public static Object [][] createGatePassItem_RTV(ITestContext testContext)
	{
		String expected = "status.statusCode==8500::"
				+ "status.statusMessage=='Item added successfully'::"
				+ "status.statusType=='SUCCESS'";
				String [] arr1 = {expected};
		
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 5, 6);
	}
	
	@DataProvider 
	public static Object [][] createGatePassOrder_RTV(ITestContext testContext)
	{
		String expected = "status.statusCode==8500::"
				+ "status.statusMessage=='Item added successfully'::"
				+ "status.statusType=='SUCCESS'";
				String [] arr1 = {expected};
		
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 5, 6);
	}
	
	@DataProvider 
	public static Object [][] statusUpdateGatepassRTV_IN_TRANSIT (ITestContext testContext)
	{
		String expected = "status.statusCode==8505::"
				+ "status.statusMessage=='Gatepass updated successfully'::"
				+ "status.statusType=='SUCCESS'";
		String [] arr1 = {expected};
		
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 9, 10);
	}
	
}
