package com.myntra.apiTests.dataproviders;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import com.myntra.lordoftherings.Toolbox;

public class PartnerPortalTestDP
{
	
	@DataProvider 
	public Object [][] vendorsWithoutBrands (ITestContext testContext)
	{
		String [] arr1 = {"966","1036","ERROR"};
		String [] arr2 = {"1010","1036","ERROR"};
		/*String [] arr3 = {"34","1036","ERROR"};
		String [] arr4 = {"162","1036","ERROR"};*/
		String [] arr3 = {"960","1036","ERROR"};
		String [] arr4 = {"1295","1036","ERROR"};
		
		Object[][] dataSet = new Object[][] { arr1 ,arr2 ,arr3,arr4};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 5, 6);
	}
	

	@DataProvider 
	public Object [][] validVendors (ITestContext testContext)
	{
		String [] arr1 = {"966","Blue Button","3","SUCCESS","SUCCESS"};
		String [] arr2 = {"1010","Belle Fille" ,"3","SUCCESS","SUCCESS" }; 
		/*String [] arr3 = {"34","HIGHLANDER" ,"3","SUCCESS","SUCCESS"};
		String [] arr4 = {"162","Vera Moda","3","SUCCESS","SUCCESS"};*/
		String [] arr3 = {"960","Athena","3","SUCCESS","SUCCESS"};
		String [] arr4 = {"1295","Bewakoof","3","SUCCESS","SUCCESS"};
		
				
		Object[][] dataSet = new Object[][] { arr1 ,arr2,arr3,arr4};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 5, 6);
	}
	
	@DataProvider 
	public Object [][] validVendorsBrands (ITestContext testContext)
	{
		String [] arr1 = {"966","Blue Button"};
		String [] arr2 = {"1010","Belle Fille"  }; 
		/*String [] arr3 = {"34","HIGHLANDER" };
		String [] arr4 = {"162","Vera Moda"};*/
		String [] arr3 = {"960","Athena"};
		String [] arr4 = {"1295","Bewakoof"};
				
		Object[][] dataSet = new Object[][] { arr1 ,arr2,arr3,arr4};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 5, 6);
	}
	
	@DataProvider 
	public Object [][] vendorsAndMasterCategory (ITestContext testContext)
	{
		String [] arr1 = {"1010","Apparel"};
		 
		Object[][] dataSet = new Object[][] { arr1   };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 3, 4);
	}
	
	@DataProvider 
	public Object [][] vendorsAndMasterSubCategory (ITestContext testContext)
	{
		String [] arr1 = {"1010", "Apparel", "Dress"};
		String [] arr2= {"1010", "Apparel", "Topwear"};
		 	
		Object[][] dataSet = new Object[][] { arr1 , arr2   };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 4, 5);
	}
	
	@DataProvider 
	public Object [][] vendorsWithoutMaster (ITestContext testContext)
	{
		String [] arr1 = {"1010", "Topwear","1038","ERROR"};
		
		Object[][] dataSet = new Object[][] { arr1    };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 4, 5);
	}
	
	@DataProvider 
	public Object [][] subCategoryAndArticleType (ITestContext testContext)
	{
		String [] arr1 = {"966", "Shoes", "Heels","1038","ERROR"};
		String [] arr2= {"966", "Shoes", "Flats","1038","ERROR"};
		
		Object[][] dataSet = new Object[][] { arr1 , arr2   };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 4, 5);
	}
	
	@DataProvider 
	public Object [][] filters (ITestContext testContext)
	{
		String [] arr1 = {"966","Blue Button" , "Black"};
		String [] arr2 = {"1010","Belle Fille" , "Blue"};
		
		Object[][] dataSet = new Object[][] { arr1 , arr2   };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 4, 5);
	}
	
	@DataProvider 
	public Object [][] inValidVendors (ITestContext testContext)
	{
		
		String [] arr1 = {"0","1052","ERROR"};
		String [] arr2 = {"-1","1052","ERROR"};
		
		Object[][] dataSet = new Object[][] { arr1 , arr2  };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 2, 3);
	}
	
	@DataProvider 
	public Object [][] categoryInValidVendors (ITestContext testContext)
	{
		
		String [] arr1 = {"1","1048","ERROR"};
		String [] arr2 = {"001","1048","ERROR"};
		
		Object[][] dataSet = new Object[][] { arr1 , arr2  };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 2, 3);
	}
	
	@DataProvider 
	public Object [][] styleIds (ITestContext testContext)
	{
		
			String [] arr1 = {"996","362013"};
					
		Object[][] dataSet = new Object[][] { arr1   };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 2, 3); 
	} 
	
	@DataProvider 
	public Object [][] createDiscount (ITestContext testContext)

	{
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        
        String from = dateFormat.format(cal.getTime());

        cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 1);
        String to = dateFormat.format(cal.getTime());
   
		
		String [] arr1 = {"Flat","124",from, to,"3"};
		String [] arr2 = {"Percentage","1",from, to,"3"};
	
					
		Object[][] dataSet = new Object[][] { arr1 ,arr2  };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 3, 4); 
	}
	
	@DataProvider 
	public Object [][] invalidDiscountBlank (ITestContext testContext)
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        
        String from = dateFormat.format(cal.getTime());

        cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 1);
        String to = dateFormat.format(cal.getTime());
		
		String [] arr1 = {"Flat","\"\"",from, to, "1031"};
		String [] arr2 = {"Percentage","\"\"",from, to, "1031"};
					
		Object[][] dataSet = new Object[][] { arr1,arr2};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 15, 16); 
	}
	
	@DataProvider 
	public Object [][] invalidDiscount (ITestContext testContext)
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        
        String from = dateFormat.format(cal.getTime());

        cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 1);
        String to = dateFormat.format(cal.getTime());
		
		String [] arr1 = {"Percentage","100",from, to, "1001"}; // Discount Percentage is more than permitted limit.
		String [] arr2 = {"Percentage","50",from, to, "1001"};  // Discount Percentage is more than permitted limit.
		String [] arr3 = {"Flat","5000",from, to, "1002"};     // Discount Amount is more than permitted limit.
		String [] arr4 = {"Percentage","0",from, to, "1003"};  // Invalid Discount Percentage.
		String [] arr5 = {"Percentage","-10",from, to, "1003"}; // Invalid Discount Percentage.
		String [] arr6 = {"Flat","0",from, to,"1004"};         // Invalid Discount Amount.
		String [] arr7 = {"Flat","-11",from,to,"1004"};       // Invalid Discount Amount.
		String [] arr8 = {"Flat","5","2015-4-01T01:00:00+0530", "2015-11-30T23:59:59+0530", "1006"};        // Start Date can not be less than today.
		String [] arr9 = {"Flat","5","2018-10-30T01:00:00+0530", "2015-09-30T23:59:59+0530", "1007"};       // End Date can not be less than Start Date.
		String [] arr10 = {"Flat","5","2017-01-01T01:00:00+0530", "2017-01-30T23:59:59+0530", "1507"};     // Start date cannot be more than 30 days. 
		String [] arr11 = {"Flat","5",from, "2020-11-30T23:59:59+0530", "1508"};                          // End date cannot be more than 365 days.
		
		
		
		
		
		
		
					
		Object[][] dataSet = new Object[][] { arr1,arr2,arr3,arr4,arr5,arr6,arr7,arr8,arr9,arr10,arr11};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 15, 16); 
	}
}