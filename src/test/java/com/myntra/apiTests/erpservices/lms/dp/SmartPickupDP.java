package com.myntra.apiTests.erpservices.lms.dp;

import com.myntra.apiTests.erpservices.oms.OMSServiceHelper;
import com.myntra.lordoftherings.Toolbox;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import java.sql.SQLException;

public class SmartPickupDP {

	@DataProvider 
	public static Object [][] SmartPickup_CreateReturn(ITestContext testContext)
	{
			
		Object[] arr1 = {"seller management automation test"};
		
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 5, 6);
	}
	
	public void deleteDataFromOMSLMS(String orderId) throws SQLException{
		
		OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
		omsServiceHelper.deleteOMSDBEntriesForOrderID(orderId);
	}
	
	public void createOrder(){
		
//		DBUtilities.exUpdateQuery(xyz, "oms");	
	}
	
	
}

