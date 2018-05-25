package com.myntra.apiTests.erpservices.partners.dp;

/*
 * Author Shubham gupta 
 */
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import com.myntra.lordoftherings.Toolbox;

public class PartnerPortalSellerDP {

	@DataProvider 
	public static Object [][] searchOrder (ITestContext testContext)
	{
		String expected = "status.statusCode==3::"
				+ "status.statusMessage=='Success'::"
				+ "status.statusType=='SUCCESS'";
		String [] arr1 = {"3",expected};
		String [] arr2 = {"2", expected};
		
		Object[][] dataSet = new Object[][] { arr1, arr2 };
		return Toolbox.returnReducedDataSet(dataSet,
			testContext.getIncludedGroups(), 5, 6);
	}
	
	@DataProvider 
	public static Object [][] dispatchShipment (ITestContext testContext)
	{
		String expected = "status.statusCode==1505::"
				+ "status.statusMessage=='Order not found'::"
				+ "status.statusType=='ERROR'";
		String [] arr1 = {"3", "2147568755",expected};
		
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
			testContext.getIncludedGroups(), 5, 6);
	}
	
	
	@DataProvider 
	public static Object [][] dispatchShipmentByDB (ITestContext testContext)
	{
		String expected = "status.statusCode==3::"
				+ "status.statusMessage=='Success'::"
				+ "status.statusType=='SUCCESS'";
		String [] arr1 = {"5", "2147568763",expected};
		
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
			testContext.getIncludedGroups(), 5, 6);
	}
	
	
	@DataProvider 
	public static Object [][] categoryHierarchy (ITestContext testContext)
	{
		String expected = "status.statusCode==3::"
				+ "status.statusMessage=='Success'::"
				+ "status.statusType=='SUCCESS'::"
				+ "data[0].entityList[0].name=='XnY'::"
				+ "data[0].entityList[0].type=='BRAND'";
		
		String expected1 = "status.statusCode==3::"
				+ "status.statusMessage=='Success'::"
				+ "status.statusType=='SUCCESS'::"
				+ "data[0].entityList[0].name=='XnY'::"
				+ "data[0].entityList[0].type=='BRAND'";
		
		String [] arr1 = {"4",expected};
	//	String [] arr2 = {"5", expected1};
		
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
			testContext.getIncludedGroups(), 5, 6);
	}
	
	@DataProvider 
	public static Object [][] reload (ITestContext testContext)
	{
		String expected = "status.statusCode==3::"
				+ "status.statusMessage=='Success'::"
				+ "status.statusType=='SUCCESS'";
		String [] arr1 = {"3",expected};
		
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
			testContext.getIncludedGroups(), 5, 6);
	}
	
	
	@DataProvider 
	public static Object [][] listingByStyle(ITestContext testContext)
	{
		String expected = "status.statusCode== 3::"
				+ "status.statusMessage=='Success'::"
				+ "status.statusType=='SUCCESS'";
		String [] arr1 = {"4", "373865", expected};
		String [] arr2 = {"5", "373900", expected};
		
		Object[][] dataSet = new Object[][] { arr1, arr2 };
		return Toolbox.returnReducedDataSet(dataSet,
			testContext.getIncludedGroups(), 5, 6);
	}
	
	@DataProvider 
	public static Object [][] InventoryUpdate(ITestContext testContext)
	{
		String expected = "status.statusCode==1001::"
				+ "status.statusMessage=='Inventory updated successfully'::"
				+ "status.statusType=='SUCCESS'";
		String [] arr1 = {"4", "XNYYSHRT00021","110000", expected};
		
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
			testContext.getIncludedGroups(), 5, 6);
	}
}

