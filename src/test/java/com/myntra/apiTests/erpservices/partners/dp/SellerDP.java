package com.myntra.apiTests.erpservices.partners.dp;

import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import com.myntra.lordoftherings.Toolbox;
import com.myntra.sellers.enums.SellerStatus;

public class SellerDP {
	
	@DataProvider 
	public static Object [][] SellerServices_createSellerNegative(ITestContext testContext)
	{
			
		String [] arr1 = {" "};
		String [] arr2 = {"#"};
		String [] arr3 = {"00"};
		String [] arr4 = {"34343"};
	//	String [] arr5 = {"3n34535_#"};
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4 };
		
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 5, 6);
	}
	
	@DataProvider 
	public static Object [][] SellerServices_updateSeller(ITestContext testContext)
	{
			
		Object[] arr1 = {"2", "myTestSeller", "MYTS", SellerStatus.READY };
		
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 5, 6);
	}
	
	
	
	@DataProvider 
	public static Object [][] SellerServices_addBrandNegative(ITestContext testContext)
	{
			
		Object[] arr1 = {"7436","Baby League","-1"};
		Object[] arr2 = {"7436","Baby League","0"};
		
		Object[][] dataSet = new Object[][] { arr1 ,arr2};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 5, 6);
	}
	
	
	
	@DataProvider 
	public static Object [][] SellerServices_addcategoryManagerNegative(ITestContext testContext)
	{
			
		Object[] arr1 = {"true","0","14"};
		Object[] arr2 = {"true","-1","14"};
		
		
		Object[][] dataSet = new Object[][] { arr1,arr2 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 5, 6);
	}
	
	
	
	@DataProvider 
	public static Object [][] SellerServices_addWarehouseNegative(ITestContext testContext)
	{
			
		Object[] arr1 = {"3","0","45"};
		Object[] arr2 = {"3","-1","45"};
		
		
		Object[][] dataSet = new Object[][] { arr1 ,arr2};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 5, 6);
	}
	

	
	@DataProvider 
	public static Object [][] SellerServices_addFinancialInfoNegative(ITestContext testContext)
	{
			
		Object[] arr1 = {"0","357","true"};
		Object[] arr2 = {"-1","357","true"};
		
		
		Object[][] dataSet = new Object[][] { arr1,arr2 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 5, 6);
	}
	
	
	@DataProvider 
	public static Object [][] SellerServices_addPaymentConfigNegative(ITestContext testContext)
	{
			
		Object[] arr1 = {"0","33.0","true"};
		Object[] arr2 = {"-1","33.0","true"};
		
		Object[][] dataSet = new Object[][] { arr1 ,arr2};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 5, 6);
	}
	
	
	@DataProvider 
	public static Object [][] SellerServices_addKycDocNegative(ITestContext testContext)
	{
			
		Object[] arr1 = {"0","123","abcdAttach","jpg","true","0"};
		Object[] arr2 = {"-1","123","abcdAttach","jpg","true","-1"};
		
		
		Object[][] dataSet = new Object[][] { arr1 ,arr2};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 5, 6);
	}
	
	
	@DataProvider 
	public static Object [][] SellerServices_addShippingNegative(ITestContext testContext)
	{
			
		Object[] arr1 = {"Add Shipping Address","Bangalore","India","abcseller@gmail.com","true","178686332","1234567890","560068","Karnataka"};
		
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 5, 6);
	}
	
	
	@DataProvider 
	public static Object [][] SellerServices_addSellerItem(ITestContext testContext)
	{
			
		Object[] arr1 = {"XNYYSHRT00251MADURAINT1","XNYYSHRT00251","true","373911"};
	//	Object[] arr2 = {"1","XNYYSHRT00252","XNYYSHRT00252","true","373911"};
		
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 5, 6);
	}
	
	@DataProvider 
	public static Object [][] SellerServices_getSellerItemBySkuId(ITestContext testContext)
	{
			
		Object[] arr1 = {"1251896"};
	
		
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 5, 6);
	}
	
	@DataProvider 
	public static Object [][] SellerServices_getSellerItemBySkuCode(ITestContext testContext)
	{
			
		Object[] arr1 = {"XNYYSHRT00251MADURAINT1"};
	
		
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 5, 6);
	}
	
	@DataProvider 
	public static Object [][] disable(ITestContext testContext)
	{
			
		Object[] arr1 = {"6"};
		
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 5, 6);
	}
	
	@DataProvider 
	public static Object [][] e2e_createSeller(ITestContext testContext)
	{
			
		Object[] arr1 = {"Seller Service Automation Test","TestDisplayName",false};
		
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 5, 6);
	}
	
	@DataProvider 
	public static Object [][] e2e_addcategoryManager(ITestContext testContext)
	{
			
		Object[] arr1 = {"true","14"};
		
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 5, 6);
	}
	
	@DataProvider 
	public static Object [][] e2e_addBrand(ITestContext testContext)
	{
			
		Object[] arr1 = {"7436","Baby League"};
		
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 5, 6);
	}
	
	@DataProvider 
	public static Object [][] e2e_addWarehouse(ITestContext testContext)
	{
			
		Object[] arr1 = {"3","45"};
		
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 5, 6);
	}
	
	@DataProvider 
	public static Object [][] e2e_addFinancialInfo(ITestContext testContext)
	{
			
		Object[] arr1 = {"357","true"};
		
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 5, 6);
	}
	
	
	@DataProvider 
	public static Object [][] e2e_addPaymentConfig(ITestContext testContext)
	{
			
		Object[] arr1 = {"33.0","true"};
		
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 5, 6);
	}
	
	
	
	@DataProvider 
	public static Object [][] e2e_addKycDoc(ITestContext testContext)
	{
			
		Object[] arr1 = {"123","abcdAttach","jpg","true"};
		
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 5, 6);
	}
	
	
	
	@DataProvider 
	public static Object [][] e2e_addShipping(ITestContext testContext)
	{
			
		Object[] arr1 = {"Add Shipping Address","Bangalore","India","abcseller@gmail.com","true","1234567890","560068","Karnataka"};
		
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 5, 6);
	}
	
	@DataProvider 
	public static Object [][] e2e_addBilling(ITestContext testContext)
	{
			
		Object[] arr1 = {"Add Billing Address","Gurgaon","India","xyzseller@gmail.com","true","1234567890","110011","Delhi"};
		
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 5, 6);
	}
	
	@DataProvider 
	public static Object [][] getSellerComm(ITestContext testContext)
	{
			
		Object[] arr1 = {"true","APPROVED","Shirts","XnY","MEN","Apparel","5"};
		Object[] arr2 = {"true","APPROVED","Tops","FabAlley","WOMEN","Apparel","5"};
		
		
		Object[][] dataSet = new Object[][] { arr1 ,arr2};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 5, 6);
	}

}
