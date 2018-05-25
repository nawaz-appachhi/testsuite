package com.myntra.apiTests.dataproviders;

import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import com.myntra.lordoftherings.Toolbox;

public class RmsGatePassDP {
	
	@DataProvider 
	public Object [][] createGatePassSTR (ITestContext testContext)
	{
	//	String [] arr1 = {"1","2","8503","Gatepass created successfully","SUCCESS"};
		String [] arr2 = {"2","20","8503","Gatepass created successfully","SUCCESS"};
		
		
		Object[][] dataSet = new Object[][] { arr2 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 5, 6);
	}
	
	@DataProvider 
	public Object [][] createGatePassRTV (ITestContext testContext)
	{
	//	String [] arr1 = {"1","2","8503","Gatepass created successfully","SUCCESS"};
		String [] arr2 = {"4","6","8503","Gatepass created successfully","SUCCESS"};
		
		
		Object[][] dataSet = new Object[][] { arr2 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 5, 6);
	}
	
	@DataProvider 
	public Object [][] createGatePassNegativeSTR (ITestContext testContext)
	{
		String [] arr1 = {"","2","54","To party warehouse id can not be empty","ERROR"};
		String [] arr2 = {"2000","2","54","Invalid to party warehouse id","ERROR"};
		String [] arr3 = {"2","","8064","From party warehouse id can not be empty","ERROR"};
		String [] arr4 = {"2","2000","8065","Invalid from party warehouse id","ERROR"};
		
		
		Object[][] dataSet = new Object[][] { arr1,arr2,arr3 ,arr4};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 5, 6);
	}
	
	@DataProvider 
	public Object [][] createGatePassNegativeRTV (ITestContext testContext)
	{
		String [] arr1 = {"","6","54","To party warehouse id can not be empty","ERROR"};
		String [] arr2 = {"2000","6","54","Invalid to party warehouse id","ERROR"};
		//String [] arr3 = {"1","2","8066","No address is associated with the seller","ERROR"};
		String [] arr4 = {"2","","8062","From party seller id can not be empty","ERROR"};
		String [] arr5 = {"2","2000","8058","Error while retrieving seller details","ERROR"};
		
		
		Object[][] dataSet = new Object[][] { arr1,arr2,arr4,arr5 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 5, 6);
	}
	
	@DataProvider 
	public Object [][] getToParty (ITestContext testContext)
	{
		String [] arr1 = {"1","8506","To party retrieved successfully","SUCCESS"};
		String [] arr2 = {"2","8506","To party retrieved successfully","SUCCESS"};
		String [] arr3 = {"3","8506","To party retrieved successfully","SUCCESS"};
		String [] arr4 = {"30000","8061","Invalid To party warehouse id","ERROR"};
		
		
		
		Object[][] dataSet = new Object[][] { arr1,arr2,arr3,arr4};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 5, 6);
	}
	
	@DataProvider 
	public Object [][] getGetPassTypes (ITestContext testContext)
	{
		String [] arr1 = {"8507","Type retrieved successfully","SUCCESS"};
		
		
		Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 5, 6);
	}

	@DataProvider 
	public Object [][] getGetPassItem (ITestContext testContext)
	{
		String [] arr1 = {"8501","Item retrieved successfully","SUCCESS"};
		
		
		Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 5, 6);
	}
	
	@DataProvider 
	public Object [][] searchGatepass (ITestContext testContext)
	{
		String [] arr1 = {"8504","Gatepass retrieved successfully","SUCCESS"};
		
		
		Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 5, 6);
	}
	
	@DataProvider 
	public Object [][] getRTVtoParty (ITestContext testContext)
	{
		String [] arr1 = {"1","8506","To party retrieved successfully","SUCCESS"};
		String [] arr2 = {"2","8506","To party retrieved successfully","SUCCESS"};
		String [] arr3 = {"3","8506","To party retrieved successfully","SUCCESS"};
		String [] arr4 = {"3000","8061","Invalid To party warehouse id","ERROR"};
		
		
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3,arr4};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 5, 6);
	}
	
	@DataProvider 
	public Object [][] createGatePassItem_negative (ITestContext testContext)
	{
		String [] arr1 = {"2132872738","8","8070","Error while retrieving return line","ERROR"};
		String [] arr2 = {"2132872738","1","8054","Could not find gatepass","ERROR"};
		String [] arr3 = {"","8","8070","Error while retrieving return line","ERROR"};
		//String [] arr4 = {"2132872738","","54","Gate Pass Id can not be empty","ERROR"};
		String [] arr5 = {"100123453","120","8057","Item does not belong to this seller","ERROR"};
		String [] arr6 = {"100123453","126","8052","This item is already associated to another gatepass","ERROR"};
		String [] arr7 = {"100123457","126","8055","Gatepass To-Party does not match item warehouse","ERROR"};
		String [] arr8 = {"100123457","120","8055","From party of the gatepass does not match item warehouse","ERROR"};
		
		
		
		Object[][] dataSet = new Object[][] { arr1,arr2,arr3,arr5,arr6,arr7,arr8};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 9, 10);
	}
	

}
