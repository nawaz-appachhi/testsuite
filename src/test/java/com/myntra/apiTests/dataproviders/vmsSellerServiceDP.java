package com.myntra.apiTests.dataproviders;

import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import com.myntra.lordoftherings.Toolbox;

/**
 * Author Shubham gupta
 */

public class vmsSellerServiceDP {
	
	
	@DataProvider 
	public static Object [][] createSeller (ITestContext testContext)
	{
		String [] arr1 = {"Seller Management Automation Test", "1625", "Seller has been added successfully","SUCCESS"};
		
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 5, 6);
	}
	
	@DataProvider 
	public static Object [][] createSellerNegative (ITestContext testContext)
	{
		String [] arr1 = {"", "54", "Error occurred while inserting/processing data","ERROR"};
		String [] arr2 = {"#", "54", "Error occurred while inserting/processing data","ERROR"};
		String [] arr3 = {"00", "54", "Error occurred while inserting/processing data","ERROR"};
		String [] arr4 = {"34343", "54", "Error occurred while inserting/processing data","ERROR"};
		String [] arr5 = {"3n34535*", "54", "Error occurred while inserting/processing data","ERROR"};
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 5, 6);
	}
	
	@DataProvider 
	public static Object [][] getAllSeller (ITestContext testContext)
	{
		String [] arr1 = {"1626", "Seller has been retrieved successfully","SUCCESS"};
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 5, 6);
	}
	
	@DataProvider 
	public static Object [][] getAddressBySeller (ITestContext testContext)
	{
		String [] arr1 = {"16", "1642", "Seller Contact Address retrieved successfully","SUCCESS"};
		String [] arr2 = {"26", "1642", "Seller Contact Address retrieved successfully","SUCCESS"};
		String [] arr3 = {"17", "1642", "Seller Contact Address retrieved successfully","SUCCESS"};
		Object[][] dataSet = new Object[][] { arr1,arr2, arr3 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 5, 6);
	}
	
	@DataProvider 
	public static Object [][] getAllSellerAddress(ITestContext testContext)
	{
		String [] arr1 = {"1642", "Seller Contact Address retrieved successfully","SUCCESS"};
		
		Object[][] dataSet = new Object[][] { arr1,};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 5, 6);
	}
	
	@DataProvider 
	public static Object [][] addBillingAddress (ITestContext testContext)
	{
		String [] arr1 = { "my add1", "add2","BILLING", "Bangalore", "India", "abc@gmail.com", "zzz@gmail.com", "true", "4", "notes", "SELLER",  "9915151999", "9999999999", "560094", "Karnataka", "1640", "Seller Contact Address added successfully","SUCCESS"};
		String [] arr2 = { "my add1", "add2","SHIPPING", "Bangalore", "India", "abc@gmail.com", "zzz@gmail.com", "true", "5", "notes", "SELLER",  "9915151999", "9999999999", "560094", "Karnataka", "1640", "Seller Contact Address added successfully","SUCCESS"};
		Object[][] dataSet = new Object[][] { arr1, arr2 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 5, 6);
	}
	
	
	@DataProvider 
	public static Object [][] addBillingAddressNegative (ITestContext testContext)
	{
		String [] arr1 = { "my add1", "add2","BILLING", "Bangalore", "India", "abc@gmail.com", "zzz@gmail.com", "true", "-1", "notes", "SELLER",  "9915151999", "9999999999", "560094", "Karnataka", "1801", "Seller with the given ID is not found","ERROR"};
		String [] arr2 = { "my add1", "add2","BILLIN", "Bangalore", "India", "abc@gmail.com", "zzz@gmail.com", "true", "8", "notes", "SELLER",  "9915151999", "9999999999", "560094", "Karnataka", "2059", "Either empty or invalid address type","ERROR"};
		String [] arr3 = { "my add1", "add2","BILLIN", "Bangalore", "India", "abc@gmail.com", "zzz@gmail.com", "true", "8", "notes", "SELLER",  "9915151999", "9999999999", "560094", "Karnataka", "2059", "Either empty or invalid address type","ERROR"};
		String [] arr4 = { "##", "add2","BILLING", "Bangalore", "India", "abc@gmail.com", "zzz@gmail.com", "true", "8", "notes", "SELLER",  "9915151999", "9999999999", "560094", "Karnataka", "2050", "Either empty or invalid address.","ERROR"};
		String [] arr5 = { "my add1", "add2","BILLING", "", "India", "abc@gmail.com", "zzz@gmail.com", "true", "8", "notes", "SELLER",  "9915151999", "9999999999", "560094", "Karnataka", "2051", "Either empty or invalid city","ERROR"};
		String [] arr6 = { "my add1", "add2","BILLING", "Bangalore", "", "abc@gmail.com", "zzz@gmail.com", "true", "8", "notes", "SELLER",  "9915151999", "9999999999", "560094", "Karnataka", "2053", "Either empty or invalid country","ERROR"};
		String [] arr7 = { "my add1", "add2","BILLING", "Bangalore", "India", "", "zzz@gmail.com", "true", "8", "notes", "SELLER",  "9915151999", "9999999999", "560094", "Karnataka", "2055", "Either empty or invalid email","ERROR"};
		String [] arr8 = { "my add1", "add2","BILLING", "Bangalore", "India", "abc@gmail.com", "zzz@gmail.com", "true", "8", "notes", "",  "9915151999", "9999999999", "560094", "Karnataka", "62", "Bad search parameters provided","ERROR"};
		String [] arr9 = { "my add1", "add2","BILLING", "Bangalore", "India", "abc@gmail.com", "zzz@gmail.com", "true", "8", "notes", "",  "99151519", "9999999999", "560094", "Karnataka", "62", "Bad search parameters provided","ERROR"};
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 9, 6);
	}
	
	@DataProvider 
	public static Object [][] addShippingAddress (ITestContext testContext)
	{
		String [] arr1 = { "my add1", "add2","SHIPPING", "Bangalore", "India", "abc@gmail.com", "zzz@gmail.com", "true", "19", "notes", "SELLER",  "9915151999", "9999999999", "560094", "Karnataka", "1640", "Seller Contact Address added successfully","SUCCESS"};
		String [] arr2 = { "my add1", "add2","SHIPPING", "Bangalore", "India", "abc@gmail.com", "zzz@gmail.com", "true", "8", "notes", "SELLER",  "9915151999", "9999999999", "560094", "Karnataka", "1640", "Seller Contact Address added successfully","SUCCESS"};
		Object[][] dataSet = new Object[][] { arr1, arr2 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 5, 6);
	}
	
	@DataProvider 
	public static Object [][] addKYC (ITestContext testContext)
	{
		String [] arr1 = { "TIN", "TINN123", "abcdAttach-TIN", "true", "13", "SELLER", "1639", "Seller KYC Document added successfully","SUCCESS"};
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 5, 6);
	}
	
	@DataProvider 
	public static Object [][] addKYCNegative (ITestContext testContext)
	{
		String [] arr1 = { "TIN", "TINN123", "abcdAttach-TIN", "true", "13", "", "62", "Bad search parameters provided","ERROR"};
		String [] arr2 = { "TIN", "TINN123", "abcdAttach-TIN", "true", "", "SELLER", "62", "Bad search parameters provided","ERROR"};
		String [] arr3 = { "TIN", "TINN123", "abcdAttach-TIN", "true", "", "", "62", "Bad search parameters provided","ERROR"};
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 5, 6);
	}
	
	@DataProvider 
	public static Object [][] addCategoryManager (ITestContext testContext)
	{
		String [] arr1 = { "true","18", "14", "1629", "Seller Category Manager has been added successfully", "SUCCESS"};
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 5, 6);
	}
	
	@DataProvider 
	public static Object [][] addCategoryManagerNegative (ITestContext testContext)
	{
		String [] arr1 = { "true","18", "", "62", "Bad search parameters provided", "ERROR"};
		String [] arr2 = { "true","", "14", "1801", "Seller with the given ID is not found", "ERROR"};
		String [] arr3 = { "true","-1", "14", "1801", "Seller with the given ID is not found", "ERROR"};
		String [] arr4 = { "true","0", "14", "1801", "Seller with the given ID is not found", "ERROR"};
		String [] arr5 = { "true","156721t83916", "14", "1801", "Seller with the given ID is not found", "ERROR"};
		String [] arr6 = { "true","", "","1801", "Seller with the given ID is not found", "ERROR"};
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 6, 6);
	}
	
	@DataProvider 
	public static Object [][] disableCategoryManager (ITestContext testContext)
	{
		String [] arr1 = { "true","25", "14", "1628", "Seller Category Manager has been updated successfully", "SUCCESS"};
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 5, 6);
	}
	
	@DataProvider 
	public static Object [][] addSellerPaymentConfiguration (ITestContext testContext)
	{
		String [] arr1 = { "true","200.0", "33", "2", "2", "ON_ORDER_DELIVERED", "1633", "Seller payment configuration has been added successfully", "SUCCESS"};
		String [] arr2 = { "true","200.0", "33", "2", "8", "ON_ORDER_DELIVERED", "1633", "Seller payment configuration has been added successfully", "SUCCESS"};
		Object[][] dataSet = new Object[][] { arr1, arr2 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 5, 6);
	}
	
	
	@DataProvider 
	public static Object [][] addSellerPaymentConfigurationNegative (ITestContext testContext)
	{
		String [] arr1 = { "true","200.0", "33", "2", "-1", "ON_ORDER_DELIVERED", "1801", "Seller with the given ID is not found", "ERROR"};
		String [] arr2 = { "true","200.0", "33", "2", "0", "ON_ORDER_DELIVERED", "1801", "Seller with the given ID is not found", "ERROR"};
		String [] arr3 = { "true","200.0", "33", "2", "1500278", "ON_ORDER_DELIVERED", "1801", "Seller with the given ID is not found", "ERROR"};
		String [] arr4 = { "true","200.0", "33", "2", "", "ON_ORDER_DELIVERED", "1801", "Seller with the given ID is not found", "ERROR"};
//		String [] arr5 = { "true","", "33", "2", "2", "ON_ORDER_DELIVERED", "2064", "Empty or invalid minimum margin", "ERROR"};
		String [] arr6 = { "true","200.0", "", "2", "2", "ON_ORDER_DELIVERED", "2063", "Empty or invalid margin %age", "ERROR"};
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr6 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 6, 6);
	}
	
	
	@DataProvider 
	public static Object [][] disablePaymentConfiguration (ITestContext testContext)
	{
		String [] arr1 = { "true","200.0", "33", "2", "84", "ON_ORDER_DELIVERED", "1638", "Seller Payment Configuration has been updated successfully", "SUCCESS"};
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 5, 6);
	}
	
	@DataProvider 
	public static Object [][] addSellerWarehouse (ITestContext testContext)
	{
		String [] arr1 = { "3", "16", "2", "ON_HAND", "27" , "1629", "Seller warehouse added successfully", "SUCCESS"};
		String [] arr2 = { "3", "16", "8", "ON_HAND", "27" , "1629", "Seller warehouse added successfully", "SUCCESS"};
		Object[][] dataSet = new Object[][] { arr1, arr2 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 5, 6);
	}
	
	@DataProvider 
	public static Object [][] addSellerWarehouseNegative (ITestContext testContext)
	{
		String [] arr1 = { "3", "27", "-1", "ON_HAND", "26" , "1801", "Seller with the given ID is not found", "ERROR"};
		String [] arr2 = { "3", "27", "0", "ON_HAND", "26" , "1801", "Seller with the given ID is not found", "ERROR"};
		String [] arr3 = { "3", "27", "178686t", "ON_HAND", "26" , "1801", "Seller with the given ID is not found", "ERROR"};
		String [] arr4 = { "3", "27", "", "ON_HAND", "26" , "1801", "Seller with the given ID is not found", "ERROR"};
		String [] arr5 = { "3", "27", "", "ON_HAND", "" , "1801", "Seller with the given ID is not found", "ERROR"};
		String [] arr6 = { "3", "27", "2", "", "26" , "62", "Bad search parameters provided", "ERROR"};
		String [] arr7 = { "3", "27", "8", "ON_HAND", "" , "62", "Bad search parameters provided", "ERROR"};
		String [] arr8 = { "3", "27", "8", "", "" , "62", "Bad search parameters provided", "ERROR"};
		
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 8, 6);
	}
	
	@DataProvider 
	public static Object [][] disableSellerWarehouse (ITestContext testContext)
	{
		String [] arr1 = { "3", "27", "25", "ON_HAND", "26" , "1629", "Seller warehouse updated successfully", "SUCCESS"};
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 5, 6);
	}
	
	@DataProvider 
	public static Object [][] editSeller (ITestContext testContext)
	{
		String [] arr1 = { "disc", "true", "CREATED", "1627", "Seller has been updated successfully", "SUCCESS"};
		String [] arr2 = { "disc", "true", "READY", "1627", "Seller has been updated successfully", "SUCCESS"};
		
		Object[][] dataSet = new Object[][] { arr1, arr2 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 5, 6);
	}
	
	@DataProvider 
	public static Object [][] addSellerFinancialInformation (ITestContext testContext)
	{
		String [] arr1 = { "220", "true","12", "1631", "Seller finance info has been added successfully", "SUCCESS"};
		String [] arr2 = { "120", "true","16", "1631", "Seller finance info has been added successfully", "SUCCESS"};
		String [] arr3 = { "320", "true","8", "1631", "Seller finance info has been added successfully", "SUCCESS"};
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 5, 6);
	}
	
	@DataProvider 
	public static Object [][] addBrandToSeller (ITestContext testContext)
	{
		String [] arr1 = { "7210", "Aaboli","true", "25", "1636", "Seller Brand has been added successfully", "SUCCESS"};
		String [] arr2 = { "4272", "Aaboli","true", "23",  "1636","Seller Brand has been added successfully", "SUCCESS"};
		String [] arr3 = { "8001","Le Bison","true", "12", "1636", "Seller Brand has been added successfully", "SUCCESS"};
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 5, 6);
	}
	
	@DataProvider 
	public static Object [][] addBrandToSellerNegative (ITestContext testContext)
	{
		String [] arr1 = { "7210", "Aaboli","true", "", "1801", "Seller with the given ID is not found", "ERROR"};
		String [] arr2 = { "7210", "Aaboli","true", "0", "1801", "Seller with the given ID is not found", "ERROR"};
		String [] arr3 = { "", "Aaboli","true", "25",  "62","Bad search parameters provided", "ERROR"};
		//String [] arr4 = { "8001","Le Bison","", "2", "1636", "Seller Brand has been added successfully", "SUCCESS"};
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 5, 6);
	}
	
	@DataProvider 
	public static Object [][] addMultiBrandToSeller (ITestContext testContext)
	{
		String [] arr1 = { "7210", "Aaboli","true", "21", "4272", "Bahubali","true", "21","8001", "Le Bison","true", "21","1636", "Seller Brand has been added successfully", "SUCCESS"};
		String [] arr2 = { "7210", "Aaboli","true", "20", "4272", "Bahubali","true", "20","8001", "Le Bison","true", "20","1636", "Seller Brand has been added successfully", "SUCCESS"};
		Object[][] dataSet = new Object[][] { arr1, arr2};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 5, 6);
	}
	
	
	@DataProvider 
	public static Object [][] fetchSellerTerms (ITestContext testContext)
	{
		String [] arr1 = { "2001", "Seller Term(s) retrieved successfully", "SUCCESS"};
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 5, 6);
	}
	
	@DataProvider 
	public static Object [][] addSlabs (ITestContext testContext)
	{
		String [] arr1 = { "11","12","20","1547","Slabs are overlapping or out of bounds for VBAG : (Subhadeep saha,Mysilk,Backpacks,Women)", "ERROR"};
		String [] arr2 = { "","12","20","1547","Slabs are overlapping or out of bounds for VBAG : (Subhadeep saha,Mysilk,Backpacks,Women)", "ERROR"};
		
		Object[][] dataSet = new Object[][] { arr1 ,arr2};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 5, 6);
	}
	
	@DataProvider 
	public static Object [][] updateSlabs (ITestContext testContext)
	{
		String [] arr1 = { "11","12","20","2014", "Seller Turnover Incentive Percentage Slab updated successfully", "SUCCESS"};
		String [] arr2 = { "11","15","20","2014", "Seller Turnover Incentive Percentage Slab updated successfully", "SUCCESS"};
		
		Object[][] dataSet = new Object[][] { arr1 ,arr2};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 5, 6);
	}
	
	@DataProvider 
	public static Object [][] sellerTerms (ITestContext testContext)
	{
		String [] arr1 = {"61161", "1904", "Inactive Seller Terms cannot be edited", "ERROR"};
		String [] arr2 = { "61162","1904", "Inactive Seller Terms cannot be edited", "ERROR"};
		
		Object[][] dataSet = new Object[][] { arr1 ,arr2};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 5, 6);
	}
	
	@DataProvider 
	public static Object [][] fetchAllSellerItem (ITestContext testContext)
	{
		String [] arr1 = {"4001", "Seller Item Master retrieved successfully", "SUCCESS"};
		
		
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 5, 6);
	}
	
	
	@DataProvider 
	public static Object [][] bulkUploadSellerItem (ITestContext testContext)
	{
		String expected = "sellerItemMasterResponse.status.statusCode==4000::"
				+ "sellerItemMasterResponse.status.statusMessage=='Seller Item Master added successfully'::"
				+ "sellerItemMasterResponse.status.statusType=='SUCCESS'::"
				+ "sellerItemMasterResponse.data.sellerItemMaster[0].message=='Successfully Added'::"
				+ "sellerItemMasterResponse.data.sellerItemMaster[0].status=='SUCCESS'";
	
		
		String [] arr1 = {"XNYYSHRT00251","SUBH","3","XNYYSHRT00251MADURAINT1","373911","XNYYSHRT00252","SUBH","3","XNYYSHRT00252","373911",expected};
		
		
		Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet,
			testContext.getIncludedGroups(), 5, 6);
	}
	
	@DataProvider 
	public static Object [][] bulkUploadSellerItem1 (ITestContext testContext)
	{
		String expected = "sellerItemMasterResponse.status.statusCode==4000::"
				+ "sellerItemMasterResponse.status.statusMessage=='Seller Item Master added successfully'::"
				+ "sellerItemMasterResponse.status.statusType=='SUCCESS'::"
				+ "sellerItemMasterResponse.data.sellerItemMaster[0].message=='No change detected in Partner Item with Code : (SHUB,XNYYSHRT00211,373903)'::"
				+ "sellerItemMasterResponse.data.sellerItemMaster[0].status=='ERROR'";
	
		
		String [] arr1 = {"XNYYSHRT00211","SHUB","2","XNYYSHRT00211","373903","XNYYSHRT00212","SHUB","2","XNYYSHRT00212","373903",expected};
		
		
		Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet,
			testContext.getIncludedGroups(), 5, 6);
	}
	
	
}
