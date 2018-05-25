package com.myntra.apiTests.dataproviders;

import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import com.myntra.lordoftherings.Toolbox;

public class IdeaMigrationDBDataProvider {

	@DataProvider
	public Object[][] GetFKConstraintsDataProvider(ITestContext testContext){		
		String arr1 []= {"jdbc:mysql://54.179.37.12:3306/information_schema?zeroDateTimeBehavior=convertToNull","Portal_FK_Constraints"};
		String arr2 [] ={"jdbc:mysql://54.251.205.116:3307/information_schema?zeroDateTimeBehavior=convertToNull", "ERP_FK_Constraints"};
		Object [][] dataSet = new Object[][]{arr1,arr2};
		return Toolbox.returnReducedDataSet( dataSet, testContext.getIncludedGroups(),2,1);
	}
	@DataProvider
	public Object[][] GetIndexConstraintsDataProvider(ITestContext testContext){		
		String arr1 []= {"jdbc:mysql://54.179.37.12:3306/information_schema?zeroDateTimeBehavior=convertToNull","Portal_Index_Constraints"};
		//String arr2 [] ={"jdbc:mysql://54.251.205.116:3307/information_schema?zeroDateTimeBehavior=convertToNull", "ERP_Index_Constraints"};
		Object [][] dataSet = new Object[][]{arr1};
		return Toolbox.returnReducedDataSet( dataSet, testContext.getIncludedGroups(),1,1);
	}
	@DataProvider
	public Object[][] GetEmailIDColumnsDataProvider(ITestContext testContext){		
		String arr1 []= {"jdbc:mysql://54.179.37.12:3306/payments?zeroDateTimeBehavior=convertToNull","Portal_EmailID_Columns"};
		String arr2 [] ={"jdbc:mysql://54.251.205.116:3307/myntra_lms?zeroDateTimeBehavior=convertToNull", "ERP_EmailID_Columns"};
		Object [][] dataSet = new Object[][]{arr1,arr2};
		return Toolbox.returnReducedDataSet( dataSet, testContext.getIncludedGroups(),2,1);
	}
}