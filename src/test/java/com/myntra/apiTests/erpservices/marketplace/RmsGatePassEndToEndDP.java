package com.myntra.apiTests.erpservices.marketplace;

import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import com.myntra.lordoftherings.Toolbox;

public class RmsGatePassEndToEndDP {
	
	@DataProvider 
	public static Object [][] createGatePass_StockTransfer (ITestContext testContext)
	{
		String expected = "status.statusCode==8503::"
				+ "status.statusMessage=='Gatepass created successfully'::"
				+ "status.statusType=='SUCCESS'";
		String [] arr1 = {"1","45","STOCK_TRANSFER_RETURN",expected};
		
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 5, 6);
	}
	
	@DataProvider 
	public static Object [][] createGatePassItem_ST (ITestContext testContext)
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
		String [] arr1 = {expected};
		
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 9, 10);
	}
	
	@DataProvider 
	public static Object [][] updateGatepassItem (ITestContext testContext)
	{

		String expected = "status.statusCode==8502::"
				+ "status.statusMessage=='Item updated successfully'::"
				+ "status.statusType=='SUCCESS'";
		String [] arr1 = {expected};
		
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 9, 10);
	}
	
	@DataProvider 
	public static Object [][] statusUpdateGatepass_Closed (ITestContext testContext)
	{ 
		String expected = "status.statusCode==8505::"
			+ "status.statusMessage=='Gatepass updated successfully'::"
			+ "status.statusType=='SUCCESS'";
		String [] arr1 = {expected};
		
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 9, 10);
	}
	
	@DataProvider 
	public static Object [][] createGatePassRTV(ITestContext testContext)
	{
		String expected = "status.statusCode==8503::"
			+ "status.statusMessage=='Gatepass created successfully'::"
			+ "status.statusType=='SUCCESS'";
		String [] arr1 = {"45","5", "RETURN_TO_SELLER_RETURN",expected};
		
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
	public static Object [][] statusUpdateGatepassRTV_IN_TRANSIT (ITestContext testContext)
	{
		String expected = "status.statusCode==8505::"
				+ "status.statusMessage=='Gatepass updated successfully'::"
				+ "status.statusType=='SUCCESS'";
		String [] arr1 = {expected};
		
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 9, 10);
	}
	
	//---------------------------------------------
	
	@DataProvider 
	public static Object [][] createGatePassNegativeSTR (ITestContext testContext)
	{
		//String [] arr1 = {"","2","STOCK_TRANSFER_RETURN","54","To party warehouse id can not be empty","ERROR"};
		String [] arr2 = {"2000","2","STOCK_TRANSFER_RETURN","8061","Invalid To party warehouse id","ERROR"};
		//String [] arr3 = {"2","","STOCK_TRANSFER_RETURN","8064","From party warehouse id can not be empty","ERROR"};
		String [] arr4 = {"2","2000","STOCK_TRANSFER_RETURN","8065","Invalid from party warehouse id","ERROR"};
		
		
		Object[][] dataSet = new Object[][] { arr2 ,arr4};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 5, 6);
	}
	
	
	@DataProvider 
	public static Object [][] createGatePassNegativeRTV (ITestContext testContext)
	{
		String [] arr1 = {"","3","RETURN_TO_SELLER_RETURN","8060","To party warehouse id can not be empty","ERROR"};
		String [] arr2 = {"2000","2","RETURN_TO_SELLER_RETURN","8061","Invalid To party warehouse id","ERROR"};
		//String [] arr3 = {"1","2","RETURN_TO_SELLER_RETURN","8066","No address is associated with the seller","ERROR"};
		String [] arr4 = {"2","","RETURN_TO_SELLER_RETURN","8062","From party seller id can not be empty","ERROR"};
		String [] arr5 = {"2","2000","RETURN_TO_SELLER_RETURN","8058","Error while retrieving seller details","ERROR"};
		
		
		Object[][] dataSet = new Object[][] { arr1, arr2, arr4, arr5 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 5, 6);
	}
	
	
	@DataProvider 
	public static Object [][] getToParty (ITestContext testContext)
	{
		String [] arr1 = {"1","8506","To party retrieved successfully","SUCCESS"};
		String [] arr2 = {"2","8506","To party retrieved successfully","SUCCESS"};
		String [] arr3 = {"3","8506","To party retrieved successfully","SUCCESS"};
		//String [] arr4 = {"30000","8506","Invalid To party warehouse id","ERROR"};
		
		
		
		Object[][] dataSet = new Object[][] { arr1,arr2,arr3};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 5, 6);
	}
	
	
	@DataProvider 
	public static Object [][] getGetPassTypes (ITestContext testContext)
	{
		String [] arr1 = {"8507","Type retrieved successfully","SUCCESS"};
		
		
		Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 5, 6);
	}

	@DataProvider 
	public static Object [][] getGetPassItem (ITestContext testContext)
	{
		String [] arr1 = {"8501","Item retrieved successfully","SUCCESS"};
		
		
		Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 5, 6);
	}
	
	@DataProvider 
	public static Object [][] searchGatepass (ITestContext testContext)
	{
		String [] arr1 = {"8504","Gatepass retrieved successfully","SUCCESS"};
		
		
		Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 5, 6);
	}
	
	@DataProvider 
	public static Object [][] getRTVtoParty (ITestContext testContext)
	{
		String [] arr1 = {"1","8506","To party retrieved successfully","SUCCESS"};
		String [] arr2 = {"45","8506","To party retrieved successfully","SUCCESS"};
		String [] arr3 = {"45","8506","To party retrieved successfully","SUCCESS"};
		
		
		
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 5, 6);
	}
	
	
	@DataProvider 
	public static Object [][] createGatePassItem_negative (ITestContext testContext)
	{
		String [] arr1 = {"400","2132872738","8070","Error while retrieving return line itemBarcode: 2132872738","ERROR"};
		String [] arr2 = {"1","2132872738","8089","order release not found","ERROR"};
		
		
		String [] arr5 = {"12","100123453","8098","Item can be added to gatepass only when gatepass is in created status","ERROR"};
		String [] arr6 = {"100123453","100123453","8054","Could not find gatepass","ERROR"};
		String [] arr7 = {"5","784939320","8052","This item is already associated to a gatepass","ERROR"};
		
		
		
		
		Object[][] dataSet = new Object[][] { arr1,arr2,arr5,arr6,arr7};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 9, 10);
	}	
	
}
