package com.myntra.apiTests.dataproviders;

import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import com.myntra.lordoftherings.Toolbox;

public class BankAPIsDP {

	@DataProvider
	public Object[][] saveBankDetailsDataProvider(ITestContext testContext) {
		
		
		String[] arr1 = { "Automation", "State Bank of India", "Kormangala",
				"CURRENT", "123456789", "New User", "SBI12345", "qa", "1.0" };
		
	//	String[] test_array = { "Automation", "State Bank of India", "Kormangala",
	//			"CURRENT", "123456789", "New User", "SBI12345", "qa", "1.0" };

		Object[][] dataSet = new Object[][] { arr1 };

		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] getBankDetailsWithAccountIDDataProvider(ITestContext testContext) {
		
		
		String[] arr1 = { "Automation", "5", "qa", "1.0" };
		
	//	String[] test_array = { "Automation", "5", "qa", "1.0" };

		Object[][] dataSet = new Object[][] { arr1 };

		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] getBankDetailsWithoutAccountIDDataProvider(ITestContext testContext) {
		
		
		String[] arr1 = { "Automation", "qa", "1.0" };
		
	//	String[] test_array = { "Automation", "qa", "1.0" };


		Object[][] dataSet = new Object[][] { arr1 };

		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] updateBankNameDetailDataProvider(ITestContext testContext) {
		
		
		String[] arr1 = { "Automation", "5","ICICI", "qa", "1.0" };
		
	//	String[] test_array = { "Automation", "5","ICICI", "qa", "1.0" };

		Object[][] dataSet = new Object[][] { arr1 };

		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] updateBranchNameDetailDataProvider(ITestContext testContext) {
		
		
		String[] arr1 = { "Automation", "5","HSR Layout", "qa", "1.0" };
		
	//	String[] test_array = { "Automation", "5","HSR Layout", "qa", "1.0" };

		Object[][] dataSet = new Object[][] { arr1 };

		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] updateAccountTypeDataProvider(ITestContext testContext) {
		
		
		String[] arr1 = { "Automation", "5","SAVINGS", "qa", "1.0" };
		
	// 	String[] test_array = { "Automation", "5","SAVINGS", "qa", "1.0" };

		Object[][] dataSet = new Object[][] { arr1 };

		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] updateAccountNumberDataProvider(ITestContext testContext) {
		
		
		String[] arr1 = { "Automation", "5","987654321", "qa", "1.0" };
		
	//	String[] test_array = { "Automation", "5","987654321", "qa", "1.0" };

		Object[][] dataSet = new Object[][] { arr1 };

		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] updateAccountNameDataProvider(ITestContext testContext) {
		
		
		String[] arr1 = { "Automation", "5","Name Changed", "qa", "1.0" };
		
	//	String[] test_array = { "Automation", "5","Name Changed", "qa", "1.0" };

		Object[][] dataSet = new Object[][] { arr1 };

		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] updateIFSCCodeDataProvider(ITestContext testContext) {
		
		
		String[] arr1 = { "Automation", "5","ABCD12345", "qa", "1.0" };
		
	//	String[] test_array = { "Automation", "5","ABCD12345", "qa", "1.0" };

		Object[][] dataSet = new Object[][] { arr1 };

		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] deleteBankDataProvider(ITestContext testContext) {
		
		
		String[] arr1 = { "Automation", "12", "qa", "1.0" };
		
	//	String[] test_array = { "Automation", "12", "qa", "1.0" };

		Object[][] dataSet = new Object[][] { arr1 };

		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}
	
}
