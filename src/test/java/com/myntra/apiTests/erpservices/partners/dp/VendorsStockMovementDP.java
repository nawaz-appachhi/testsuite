package com.myntra.apiTests.erpservices.partners.dp;

import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import com.myntra.lordoftherings.Toolbox;

public class VendorsStockMovementDP {
	
	@DataProvider
	public static Object[][] VSM_po_base_search(ITestContext testContext)
	{
		String[] arr1 = { "33"};	

		Object[][] dataSet = new Object[][] { arr1, };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 5, 6);

	}
	@DataProvider
	public static Object[][] VSM_get_po_details_by_poid(ITestContext testContext)
	{
		//String[] arr1 = { "351075"};
		String[] arr1 = { "441461"};

		Object[][] dataSet = new Object[][] { arr1, };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 5, 6);

	}
	@DataProvider
	public static Object[][] VSM_get_skus_by_poid(ITestContext testContext)
	{
//		String[] arr1 = { "351075"};
		//
		String[] arr1 = { "441461"};

		Object[][] dataSet = new Object[][] { arr1, };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 5, 6);

	}
	@DataProvider
	public static Object[][] VSM_get_po_details_by_vendorid(ITestContext testContext)
	{
		String[] arr1 = { "34"};	

		Object[][] dataSet = new Object[][] { arr1, };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 5, 6);

	}
	
	@DataProvider
	public static Object[][] VSM_get_RO_list_by_vendorName(ITestContext testContext)
	{
		Object[] arr1 = { "Prateek%20Apparels%20Pvt.%20Ltd",34};	

		Object[][] dataSet = new Object[][] { arr1, };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 5, 6);

	}


	@DataProvider
	public static Object[][] VSM_get_RO_details(ITestContext testContext)
	{
		String[] arr1 = { "3217"};	

		Object[][] dataSet = new Object[][] { arr1, };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 5, 6);

	}
	
	
	@DataProvider
	public static Object[][] VSM_print_po(ITestContext testContext)
	{
		String[] arr1 = { "351075"};	

		Object[][] dataSet = new Object[][] { arr1, };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 5, 6);

	}
	//
	@DataProvider
	public static Object[][] VSM_get_po_filter_attributes_by_vendorid(ITestContext testContext)
	{
		Object[] arr1 = { 34,"warehouseName"};	
		Object[] arr2 = { 34,"poStatus"};	
		Object[] arr3 = { 34,"poType"};	
		Object[] arr4 = { 34,"brandName"};	
		Object[] arr5 = { 34,"articleType"};	

		Object[][] dataSet = new Object[][] { arr1,arr2,arr3,arr4,arr5 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 5, 6);

	}
	
	
	@DataProvider
	public static Object[][] VSM_get_skus_by_invoice_number(ITestContext testContext)
	{
		String[] arr1 = { "PSINV114015"};	

		Object[][] dataSet = new Object[][] { arr1, };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 5, 6);

	}
	
	
	@DataProvider
	public static Object[][] VSM_get_pos_by_vendorid(ITestContext testContext)
	{
		String[] arr1 = { "847"};	

		Object[][] dataSet = new Object[][] { arr1, };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 5, 6);

	}
	
	@DataProvider
	public static Object[][] VSM_get_rejectedItems_invoice_number(ITestContext testContext)
	{
		String[] arr1 = { "NDI127397"};	

		Object[][] dataSet = new Object[][] { arr1, };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 5, 6);

	}
}
