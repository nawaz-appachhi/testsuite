package com.myntra.apiTests.erpservices.wms.dp;

import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import com.myntra.lordoftherings.Toolbox;

public class ROTestDP {
	@DataProvider
	public static Object[][] addARO(ITestContext testContext) {
		// paramOrderId, paramRespCode
		Object[] arr1 = { 2197, 36, "TO_PAY", "INWARD_REJECTS" ,3974,"200",7128,"Return Order Added Successfully","SUCCESS",1};
		Object[] arr2 = { 2197, 36, "TO_PAY", "CUSTOMER_RETURNS",3974 ,"200",7128,"Return Order Added Successfully","SUCCESS",1};
		Object[] arr3 = { 2197, 36, "TO_PAY", "OUTWARD_REJECTS",3974 ,"200",7128,"Return Order Added Successfully","SUCCESS",1};
		Object[] arr4 = { 2197, 36, "TO_PAY", "STOCK_CORRECTION",3974 ,"200",7128,"Return Order Added Successfully","SUCCESS",1};
		Object[] arr5 = { 2197, 36, "TO_PAY", "SOR_ITEM",3974 ,"200",7128,"Return Order Added Successfully","SUCCESS",1};
		Object[] arr6 = { 2197, 36, "PICK_UP", "INWARD_REJECTS",3974 ,"200",7128,"Return Order Added Successfully","SUCCESS",1};
		Object[] arr7 = { 2197, 36, "PICK_UP", "CUSTOMER_RETURNS",3974 ,"200",7128,"Return Order Added Successfully","SUCCESS",1};
		Object[] arr8 = { 2197, 36, "PICK_UP", "OUTWARD_REJECTS",3974 ,"200",7128,"Return Order Added Successfully","SUCCESS",1};
		Object[] arr9 = { 2197, 36, "PICK_UP", "STOCK_CORRECTION",3974 ,"200",7128,"Return Order Added Successfully","SUCCESS",1};
		Object[] arr10 = { 2197, 36, "PICK_UP", "SOR_ITEM",3974 ,"200",7128,"Return Order Added Successfully","SUCCESS",1};

    	Object[][] dataSet = new Object[][] { arr1,arr2,arr3,arr4,arr5,arr6,arr7,arr8,arr9,arr10 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 10, 10);
	}
	@DataProvider
	public static Object[][] addAROitem(ITestContext testContext) {
		// paramOrderId, paramRespCode
		Object[] arr1 = { 1, 36, "TO_PAY", "INWARD_REJECTS",3974 ,"200","8300001",7129,"ReturnOrder Item(s) Retrieved successfully","SUCCESS"};
		Object[] arr2 = { 1, 36, "TO_PAY", "CUSTOMER_RETURNS",3974 ,"200","8300001",7129,"ReturnOrder Item(s) Retrieved successfully","SUCCESS"};
		Object[] arr3 = { 1, 36, "TO_PAY", "OUTWARD_REJECTS",3974 ,"200","8300001",7129,"ReturnOrder Item(s) Retrieved successfully","SUCCESS"};
		Object[] arr4 = { 1, 36, "TO_PAY", "STOCK_CORRECTION",3974 ,"200","8300001",7129,"ReturnOrder Item(s) Retrieved successfully","SUCCESS"};
		Object[] arr5 = { 1, 36, "TO_PAY", "SOR_ITEM",3974 ,"200","8300001",7129,"ReturnOrder Item(s) Retrieved successfully","SUCCESS"};
		Object[] arr6 = { 1, 36, "PICK_UP", "INWARD_REJECTS",3974 ,"200","8300001",7129,"ReturnOrder Item(s) Retrieved successfully","SUCCESS"};
		Object[] arr7 = { 1, 36, "PICK_UP", "CUSTOMER_RETURNS",3974 ,"200","8300001",7129,"ReturnOrder Item(s) Retrieved successfully","SUCCESS"};
		Object[] arr8 = { 1, 36, "PICK_UP", "OUTWARD_REJECTS",3974 ,"200","8300001",7129,"ReturnOrder Item(s) Retrieved successfully","SUCCESS"};
		Object[] arr9 = { 1, 36, "PICK_UP", "STOCK_CORRECTION",3974,"200","8300001",7129,"ReturnOrder Item(s) Retrieved successfully","SUCCESS"};
		Object[] arr10 ={ 1, 36, "PICK_UP", "SOR_ITEM",3974 ,"200","8300001",7129,"ReturnOrder Item(s) Retrieved successfully","SUCCESS"};

    	Object[][] dataSet = new Object[][] { arr1,arr2,arr3,arr4,arr5,arr6,arr7,arr8,arr9,arr10};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 10, 10);
	}
	
	@DataProvider
	public static Object[][] updateRO(ITestContext testContext) {
		// paramOrderId, paramRespCode
		String[] arr1 = { "2197", "36", "TO_PAY", "INWARD_REJECTS" ,"200"};
		String[] arr2 = { "2197", "36", "TO_PAY", "CUSTOMER_RETURNS" ,"200"};
		String[] arr3 = { "2197", "36", "TO_PAY", "OUTWARD_REJECTS" ,"200"};
		String[] arr4 = { "2197", "36", "TO_PAY", "STOCK_CORRECTION" ,"200"};
		String[] arr5 = { "2197", "36", "TO_PAY", "SOR_ITEM" ,"200"};
		String[] arr6 = { "2197", "36", "PICK_UP", "INWARD_REJECTS" ,"200"};
		String[] arr7 = { "2197", "36", "PICK_UP", "CUSTOMER_RETURNS" ,"200"};
		String[] arr8 = { "2197", "36", "PICK_UP", "OUTWARD_REJECTS" ,"200"};
		String[] arr9 = { "2197", "36", "PICK_UP", "STOCK_CORRECTION" ,"200"};
	    String[] arr10 = { "2197", "36", "PICK_UP", "SOR_ITEM" ,"200"};

    	Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

}
