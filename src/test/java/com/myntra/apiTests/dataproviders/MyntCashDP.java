package com.myntra.apiTests.dataproviders;

import com.myntra.apiTests.portalservices.commons.CommonUtils;
import com.myntra.lordoftherings.Configuration;
import com.myntra.lordoftherings.Toolbox;

import org.testng.ITestContext;
import org.testng.annotations.DataProvider;


public class MyntCashDP extends CommonUtils
{
	Configuration con = new Configuration("./Data/configuration");
	String Env;
	public MyntCashDP(){
		if(System.getenv("ENVIRONMENT")==null)
			Env = con.GetTestEnvironemnt().toString();
		else
			Env = System.getenv("ENVIRONMENT");
	}

	@DataProvider
	public Object[][] checkbalanceDataProvider(ITestContext testContext)
	{
		String [] arr1 = {"Production","526a78d7.e9e9.4760.a8e0.06247305b0fc9Dqw8qETJG", "false"};
		String [] arr2 = {"Fox7","abbe5494.feb3.40c4.ae8b.3afc670d1fa1PWoV3TOXRv", "true"};
		String [] arr3 = {"functional","abbe5494.feb3.40c4.ae8b.3afc670d1fa1PWoV3TOXRv", "true"};	
		String [] arr4 = {"Fox7","abbe5494.feb3.40c4.ae8b.3afc670d1fa1PWoV3TOXRv", "true"};
		String [] arr5 = {"Fox7","526a78d7.e9e9.4760.a8e0.06247305b0fc9Dqw8qETJG", "false"};

		Object [][] dataSet = new Object [][] {arr1, arr2, arr3, arr4, arr5};
		return Toolbox.returnReducedDataSet(Env,dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	
	
	@DataProvider
	public Object[][] EmailCashBackDataProvider(ITestContext testContext)
	{
		String [] arr1 = {"526a78d7.e9e9.4760.a8e0.06247305b0fc9Dqw8qETJG"};
		String [] arr2 = {"abbe5494.feb3.40c4.ae8b.3afc670d1fa1PWoV3TOXRv"};
		String [] arr3 = {"abbe5494.feb3.40c4.ae8b.3afc670d1fa1PWoV3TOXRv"};	


		Object [][] dataSet = new Object [][] {arr1, arr2, arr3};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 3, 3);
	}
	
	@DataProvider
	public Object[][] checkbalanceDataProvider_negative(ITestContext testContext)
	{
		String [] arr1 = {"Production","576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U", "false"};
		String [] arr2 = {"Fox7","abbe5494.feb3.40c4.ae8b.3afc670d1fa1PWoV3TOXRv", "true"};
		String [] arr3 = {"functional","abbe5494.feb3.40c4.ae8b.3afc670d1fa1PWoV3TOXRv", "true"};	
		String [] arr4 = {"Production","526a78d7.e9e9.4760.a8e0.06247305b0fc9Dqw8qETJG", "true"};

		Object [][] dataSet = new Object [][] {arr1, arr2, arr3, arr4};
		return Toolbox.returnReducedDataSet(Env,dataSet, testContext.getIncludedGroups(), 4, 5);		
	}

	@DataProvider
	public Object[][] checkbalanceDataProvider_negative_emailID(ITestContext testContext)
	{
		String [] arr1 = {"Production","mohit.ja@myntra.com", "false"};
		String [] arr2 = {"Fox7","526a78d7.e9e9.4760.a8e0.06247305b0fc9Dqw8qETJG", "true"};
		String [] arr3 = {"functional","newUr_login@myntra.com", "true"};	
		String [] arr4 = {"Fox7","526a78d7.e9e9.4760.a8e0.06247305b0fc9Dqw8qETJG", "false"};

		Object [][] dataSet = new Object [][] {arr1, arr2, arr3, arr4};
		return Toolbox.returnReducedDataSet(Env,dataSet, testContext.getIncludedGroups(), 4, 5);		
	}
	
	@DataProvider
	public Object[][] checkTransactionLogsofUserDataProvider(ITestContext testContext){
		String [] arr1 = {"Fox7","576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U", "false"};
		
		Object[][] dataSet = new Object[][]{arr1};
		return Toolbox.returnReducedDataSet(Env,dataSet, testContext.getIncludedGroups(), 1, 2);	
	}
	
	@DataProvider
	public Object[][] checkIncorrectmailIDTransactionLogsofUserDataProvider(ITestContext testContext){
		String [] arr1 = {"Production","576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U", "false"};
		String [] arr2 = {"Fox7","abbe5494.feb3.40c4.ae8b.3afc670d1fa1PWoV3TOXRv", "true"};	
		Object[][] dataSet = new Object[][]{arr1, arr2};
		return Toolbox.returnReducedDataSet(Env,dataSet, testContext.getIncludedGroups(), 2, 3);	
	}
	
		
	/*	@DataProvider
	public Object[][] debitCashBackDataProvider(ITestContext testContext){
	//last index is for user id
		String[] arr1 = {"576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U","0","0","1","description","GOOD_WILL", "GOOD_WILL", "001", "576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U"};
		String[] arr2 = {"576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U","0","0","100","description","GOOD_WILL", "GOOD_WILL", "002", "576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U"};
		String[] arr3 = {"576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U","0","0","-1","description","GOOD_WILL", "GOOD_WILL", "003", "576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U"};
		//earnedCredit and storedCredit should get ignored
		String[] arr4 = {"576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U","10","0","1","description","GOOD_WILL", "GOOD_WILL", "004", "576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U"};
		String[] arr5 = {"576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U","0","10","1","description","GOOD_WILL", "GOOD_WILL", "005", "576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U"};
		String[] arr6 = {"576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U","0","0","1","description","GOOD_WILL", "GOOD_WILL", "006", "576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U"};
		String[] arr7 = {"576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U","10","10","1","description","GOOD_WILL", "GOOD_WILL", "007", "576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U"};
		//Different itemType
		String[] arr8 = {"576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U","0","","1","description","GOOD_WILL", "ORDER", "008", "576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U"};
		String[] arr9 = {"576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U","0","","1","description","GOOD_WILL", "GOOD_WILL", "009", "576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U"};
		String[] arr10 = {"576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U","0","","1","description","GOOD_WILL", "RETURN_ID", "010", "576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U"};
		String[] arr11 = {"576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U","0","","1","description","GOOD_WILL", "ITEM_ID", "011", "576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U"};
		String[] arr12 = {"576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U","0","","1","description","GOOD_WILL", "INVALID", "011", "576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U"};
		//big value for debit
		String[] arr13 = {"576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U","0","","10000","description","GOOD_WILL", "GOOD_WILL", "013", "576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U"};
		//unregistered User
		String[] arr14 = {"mohit.jain.Unregistered@myntra.com","0","0","1","description","GOOD_WILL", "GOOD_WILL", "014", "576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U"};
		Object[][] dataSet = new Object[][]{arr1, arr2, arr3, arr4, arr5 , arr6, arr7, arr8, arr9, arr10, arr11, arr12, arr13, arr14};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 2);	
	}*/

	@DataProvider
	public Object[][] debitCashBackDataProvider(ITestContext testContext){
		//last index is for user id
		String[] arr1 = {"576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U","0","0","1","description","CASHBACK_TO_BANK_TRANFER", "NEFT_ID", "001", "576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U"};
		String[] arr2 = {"576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U","0","0","100","description","GOOD_WILL", "GOOD_WILL", "002", "576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U"};
		String[] arr3 = {"576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U","0","0","-1","description","CASHBACK_TO_BANK_TRANFER", "NEFT_ID", "003", "576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U"};
		String[] arr4 = {"576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U","0","0","10","description","CASHBACK_TO_BANK_TRANFER", "NEFT_ID", "001", "576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U"};
		String[] arr5 = {"576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U","0","0","10","description","CASHBACK_TO_BANK_TRANSFER", "NEFT_ID", "001", "576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U"};
		String[] arr6 = {"576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U","0","0","5","description","CASHBACK_TO_BANK_TRANSFER", "NEFT_ID", "001", "576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U"};

		Object[][] dataSet = new Object[][]{arr1, arr2, arr3, arr4,arr5,arr6};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 6, 5);	
	}
	
	
	
	@DataProvider
	public Object[][] debitCashBackDataProviderNegative1(ITestContext testContext){
		//last index is for user id
		
		String[] arr5 = {"576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U","0","0","10","description","CASHBACK_TO_BANK_TRANFER", "NEFT_ID", "001", "576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U"};
		String[] arr6 = {"576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U","0","0","5","description","CASHBACK_TO_BANK_TRANSFER", "NEFT_ID", "001", "576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U"};

		Object[][] dataSet = new Object[][]{arr5,arr6};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 2, 2);	
	}
	
	@DataProvider
	public Object[][] debitCashBackOmsDataProvider(ITestContext testContext){
		//last index is for user id
		String[] arr3 = {"576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U","0","0","-1","description","GOOD_WILL", "GOOD_WILL", "003", "576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U"};

		Object[][] dataSet = new Object[][]{arr3};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 4, 5);	
	}
	@DataProvider
	public Object[][] debitCashBackDataProvider_status_negative(ITestContext testContext){

		//big value for debit
		String[] arr1 = {"576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U","0","0","10000","description","GOOD_WILL", "GOOD_WILL", "013", "error"};
		//unregistered User
		String[] arr2 = {"526a78d7.e9e9.4760.a8e0.06247305b0fc9Dqw8qETJG","0","0","9000","description","GOOD_WILL", "GOOD_WILL", "014", "error"};
		Object[][] dataSet = new Object[][]{arr1,arr2};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 2, 3);	
	}

	@DataProvider
	public Object[][] debitCashBackDataProvider_negativeData(ITestContext testContext){
		//earnedCredit and storedCredit should get ignored
		String[] arr1 = {"576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U","10","0","1","description","GOOD_WILL", "GOOD_WILL", "004", "576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U"};
		String[] arr2 = {"576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U","0","10","1","description","GOOD_WILL", "GOOD_WILL", "005", "576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U"};
		String[] arr3 = {"576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U","0","0","1","description","GOOD_WILL", "GOOD_WILL", "006", "576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U"};
		String[] arr4 = {"576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U","10","10","1","description","GOOD_WILL", "GOOD_WILL", "007", "576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U"};
		//Different temType
		String[] arr5 = {"576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U","0","","1","description","GOOD_WILL", "ORDER", "008", "576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U"};
		String[] arr6 = {"576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U","0","","1","description","GOOD_WILL", "GOOD_WILL", "009", "576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U"};
		String[] arr7 = {"576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U","0","","1","description","GOOD_WILL", "RETURN_ID", "010", "576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U"};
		String[] arr8 = {"576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U","0","","1","description","GOOD_WILL", "ITEM_ID", "011", "576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U"};
	
		Object[][] dataSet = new Object[][]{arr1, arr2, arr3, arr4, arr5 , arr6, arr7, arr8};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 3, 4);	
	}

	@DataProvider
	public Object[][] debitCashBackDataProvider_WrontMailId(ITestContext testContext){
		//earnedCredit and storedCredit should get ignored
		String[] arr1 = {"mohit@myntra.com","0","0","1","description","GOOD_WILL", "GOOD_WILL", "004"};
		String[] arr2 = {"myntcashExt@myntra","0","10","1","description","GOOD_WILL", "GOOD_WILL", "005"};
		String[] arr3 = {"jain@myntra.com","0","0","1","description","GOOD_WILL", "GOOD_WILL", "006"};
		String[] arr4 = {"myntcashExt@com","0","0","1","description","GOOD_WILL", "GOOD_WILL", "007"};	
	
		Object[][] dataSet = new Object[][]{arr1, arr2, arr3, arr4};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 4, 5);	
	}
	
	@DataProvider
	public Object[][] debitCashBackDataProvider_earnedAmt(ITestContext testContext){
		//last index is for user id
		String[] arr1 = {"5a87acf9.834a.4c7b.b2ee.a04686e0b686G4PdIWBYni","0","0","6000","description","CASHBACK_TO_BANK_TRANSFER","NEFT_ID", "1"};
		String[] arr2 = {"5a87acf9.834a.4c7b.b2ee.a04686e0b686G4PdIWBYni","0","0","6000","description","CASHBACK_TO_BANK_TRANSFER","NEFT_ID", "2"};
		String[] arr3 = {"5a87acf9.834a.4c7b.b2ee.a04686e0b686G4PdIWBYni","0","0","6000","description","CASHBACK_TO_BANK_TRANSFER","NEFT_ID", "3"};
		Object[][] dataSet = new Object[][]{arr1, arr2, arr3};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 4);	
	}
	
	@DataProvider
	public Object[][] debitCashBackDataProvider_earned_stored_amt(ITestContext testContext){
		//last index is for user id
		String[] arr1 = {"526a78d7.e9e9.4760.a8e0.06247305b0fc9Dqw8qETJG","0","0","5","description","GOOD_WILL", "GOOD_WILL", "1"};
		String[] arr2 = {"526a78d7.e9e9.4760.a8e0.06247305b0fc9Dqw8qETJG","0","0","10","description","GOOD_WILL", "GOOD_WILL", "1"};
		String[] arr3 = {"526a78d7.e9e9.4760.a8e0.06247305b0fc9Dqw8qETJG","0","0","20","description","GOOD_WILL", "GOOD_WILL", "2"};
		Object[][] dataSet = new Object[][]{arr1, arr2, arr3};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 3, 4);	
	}
	
	@DataProvider
	public Object[][] debitCashBackDataProvider_Nodes(ITestContext testContext){
		//last index is for user id
		String[] arr1 = {"576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U","0","0","1","description","GOOD_WILL", "GOOD_WILL", "001"};
		String[] arr2 = {"526a78d7.e9e9.4760.a8e0.06247305b0fc9Dqw8qETJG","0","0","1","description","GOOD_WILL", "GOOD_WILL", "001"};
		Object[][] dataSet = new Object[][]{arr1, arr2};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 2, 3);	
	}
	
	@DataProvider
	public Object[][] debitCashBackDataProvider_negativeVerifycation_Nodes(ITestContext testContext){
		//last index is for user id
		String[] arr1 = {"mohit@myntra.com","0","0","1","description","GOOD_WILL", "GOOD_WILL", "004"};
		String[] arr2 = {"mohit.jain@myntra","0","","1","description","GOOD_WILL", "ITEM_ID", "011"};
		Object[][] dataSet = new Object[][]{arr1, arr2};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 2);	
	}

	@DataProvider
	public Object[][] debitCashBackDataProvider_moreDbtAmt(ITestContext testContext){
		//last index is for user id
		String[] arr1 = {"7ee47440.1aa4.49fe.9fc4.8f4c3c347d39FbMjBPQDtY","0","0","1100","description","GOOD_WILL", "GOOD_WILL", "001", "error"};
		String[] arr2 = {"7ee47440.1aa4.49fe.9fc4.8f4c3c347d39FbMjBPQDtY","0","0","3000","description","GOOD_WILL", "GOOD_WILL", "002", "error"};
		Object[][] dataSet = new Object[][]{arr1, arr2};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 2, 3);	
	}

	@DataProvider
	public Object[][] debitCashBackDataProvider_negative(ITestContext testContext){
	//last index is for user id
		
		String[] arr1 = {"526a78d7.e9e9.4760.a8e0.06247305b0fc9Dqw8qETJG","0","0","0","description","GOOD_WILL", "GOOD_WILL", "1"};	
		String[] arr2 = {"526a78d7.e9e9.4760.a8e0.06247305b0fc9Dqw8qETJG","0","0","0","description","GOOD_WILL", "GOOD_WILL", "2"};	
		String[] arr3 = {"abbe5494.feb3.40c4.ae8b.3afc670d1fa1PWoV3TOXRv","0","0","0","description","GOOD_WILL", "GOOD_WILL", "3"};	
	
		Object[][] dataSet = new Object[][]{arr1, arr2, arr3};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 3, 4);	
	}
	
	/*@DataProvider
	public Object[][] creditCashBackDataProvider(ITestContext testContext){
		//last index is for user id
		String[] arr1 = {"576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U","1","0","","description","GOOD_WILL", "GOOD_WILL", "001", "576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U", "success"};
		String[] arr2 = {"576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U","1","1","0","description","GOOD_WILL", "GOOD_WILL", "002", "576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U","success"};
		String[] arr3 = {"576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U","0","1","0","description","GOOD_WILL", "GOOD_WILL", "003", "576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U","success"};
		String[] arr4 = {"576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U","0","0","0","description","GOOD_WILL", "GOOD_WILL", "004", "576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U", "success"};
		//Test cases for different itemType
		String[] arr5 = {"576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U","1","","100","description","GOOD_WILL", "GOOD_WILL", "005", "576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U", "success"};
		String[] arr6 = {"576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U","1","","0","description","GOOD_WILL", "ORDER", "006", "576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U", "success"};
		String[] arr7 = {"576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U","1","","0","description","GOOD_WILL", "RETURN_ID", "007", "576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U", "success"};
		String[] arr8 = {"576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U","1","","0","description","GOOD_WILL", "ITEM_ID", "008", "576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U", "success"};
		//Value of debitamount should get discarded
		String[] arr9 = {"576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U","1","1","100","description","GOOD_WILL", "GOOD_WILL", "009", "576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U", "success"};
		//-ve scenarios
		String[] arr10 = {"576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U","-1","0","100","description","GOOD_WILL", "GOOD_WILL", "010", "576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U", "success"};
		String[] arr11 = {"576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U","0","-1","100","description","GOOD_WILL", "GOOD_WILL", "011", "576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U", "success"};
		String[] arr12 = {"576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U","-1","-1","100","description","GOOD_WILL", "GOOD_WILL", "012", "576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U", "success"};
		String[] arr13 = {"576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U","0","0","100","description","GOOD_WILL", "GOOD_WILL", "013", "mohit.jain.diff@myntra.com", "success"};
		//Credit large amount
		String[] arr14 = {"576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U","5000","0","100","description","GOOD_WILL", "GOOD_WILL", "014", "576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U", "success"};
		String[] arr15 = {"576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U","0","5000","100","description","GOOD_WILL", "GOOD_WILL", "015", "576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U", "success"};
		//Invalid userid
		String[] arr16 = {"mohit.jain.invaliduser@myntra.com","1","1","100","description","GOOD_WILL", "GOOD_WILL", "016", "mohit.jain.diff@myntra.com", "success"};
		//Invalid itemType
		String[] arr17 = {"576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U","0","5000","100","description","GOOD_WILL", "INVALID", "017", "mohit.jain.diff@myntra.com", "error"};
		Object[][] dataSet = new Object[][]{arr1, arr2, arr3, arr4, arr5 , arr6, arr7, arr8, arr9, arr10, arr11, arr12, arr13, arr14, arr15, arr16, arr17};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 2);	
	}*/
	
	@DataProvider
	public Object[][] creditCashBackDataProvider(ITestContext testContext){
		//last index is for user id
		String[] arr1 = {"576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U","1","0","","description","GOOD_WILL", "GOOD_WILL", "001", "576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U", "success"};
		String[] arr2 = {"576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U","1","1","0","description","GOOD_WILL", "GOOD_WILL", "002", "576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U","success"};
		String[] arr3 = {"576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U","0","1","0","description","GOOD_WILL", "GOOD_WILL", "003", "576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U","success"};
		String[] arr4 = {"576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U","0","0","0","description","GOOD_WILL", "GOOD_WILL", "004", "576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U", "success"};
		//Test cases for different itemType
		String[] arr5 = {"576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U","1","","100","description","GOOD_WILL", "GOOD_WILL", "005", "576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U", "success"};
		String[] arr6 = {"576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U","1","","0","description","GOOD_WILL", "ORDER", "006", "576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U", "success"};
		String[] arr7 = {"576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U","1","","0","description","GOOD_WILL", "RETURN_ID", "007", "576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U", "success"};
		String[] arr8 = {"576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U","1","","0","description","GOOD_WILL", "ITEM_ID", "008", "576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U", "success"};
		//Value of debitamount should get discarded
		String[] arr9 = {"576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U","1","1","100","description","GOOD_WILL", "GOOD_WILL", "009", "576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U", "success"};
		//-ve scenarios
		String[] arr10 = {"576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U","-1","0","100","description","GOOD_WILL", "GOOD_WILL", "010", "576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U", "success"};
		String[] arr11 = {"576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U","0","-1","100","description","GOOD_WILL", "GOOD_WILL", "011", "576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U", "success"};
		String[] arr12 = {"576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U","-1","-1","100","description","GOOD_WILL", "GOOD_WILL", "012", "576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U", "success"};
		String[] arr13 = {"576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U","0","0","100","description","GOOD_WILL", "GOOD_WILL", "013", "mohit.jain.diff@myntra.com", "success"};
		//Credit large amount
		String[] arr14 = {"576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U","100","0","100","description","GOOD_WILL", "GOOD_WILL", "014", "576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U", "success"};
		String[] arr15 = {"576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U","0","5000","100","description","GOOD_WILL", "GOOD_WILL", "015", "576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U", "success"};
		//Invalid userid
		//Invalid itemType
		String[] arr17 = {"576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U","0","100","100","description","GOOD_WILL", "INVALID", "017", "576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U", "error"};
		Object[][] dataSet = new Object[][]{arr17, arr2, arr3, arr4, arr5 , arr6, arr7, arr8, arr9, arr10, arr11, arr12, arr13, arr14, arr15,arr17};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 17, 2);	
	}
	
	//Start
	
	//Test cases for different itemTypes
	@DataProvider
	public Object[][] creditCashBackDifferentItemTypesDataProvider(ITestContext testContext){
	
	
			String[] arr1 = {"576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U","1","","100","description","GOOD_WILL", "GOOD_WILL", "005", "576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U", "success"};
			String[] arr2 = {"576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U","1","","0","description","GOOD_WILL", "ORDER", "006", "576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U", "success"};
			String[] arr3 = {"576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U","1","","0","description","GOOD_WILL", "RETURN_ID", "007", "576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U", "success"};
			String[] arr4 = {"576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U","1","","0","description","GOOD_WILL", "ITEM_ID", "008", "576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U", "success"};
	
			Object[][] dataSet = new Object[][]{arr1, arr2, arr3, arr4};
			return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 17, 2);	
	}

	//Invalid userid
	@DataProvider
	public Object[][] creditCashBackInvalidUserIdDataProvider(ITestContext testContext){
		
		String[] arr1 = {"mohit.jain.invaliduserid@myntra.com","1","1","100","description","GOOD_WILL", "GOOD_WILL", "016", "mohit.jain.invaliduserid@myntra.com", "success"};
		String[] arr2 = {"invalid.userdid@myntra.com","0","0","100","description","GOOD_WILL", "GOOD_WILL", "016", "invalid.userid@myntra.com", "success"};
		Object[][] dataSet = new Object[][]{arr1, arr2};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 17, 2);	
	}
	
		
	@DataProvider
	public Object[][] creditCashBackstoredCreditBalanceDataProvider(ITestContext testContext){
		
		String[] arr1 = {"04bc25f5.0b4a.46ae.bd17.68f700a9ab15qHqX6UqvAL","0","0","0","description","GOOD_WILL", "GOOD_WILL", "001", "04bc25f5.0b4a.46ae.bd17.68f700a9ab15qHqX6UqvAL", "success"};
		String[] arr2 = {"04bc25f5.0b4a.46ae.bd17.68f700a9ab15qHqX6UqvAL","0","1","0","description","GOOD_WILL", "GOOD_WILL", "002", "04bc25f5.0b4a.46ae.bd17.68f700a9ab15qHqX6UqvAL","success"};
		String[] arr3 = {"04bc25f5.0b4a.46ae.bd17.68f700a9ab15qHqX6UqvAL","1","0","0","description","GOOD_WILL", "GOOD_WILL", "003", "04bc25f5.0b4a.46ae.bd17.68f700a9ab15qHqX6UqvAL","success"};
		String[] arr4 = {"04bc25f5.0b4a.46ae.bd17.68f700a9ab15qHqX6UqvAL","1","1","0","description","GOOD_WILL", "GOOD_WILL", "004", "04bc25f5.0b4a.46ae.bd17.68f700a9ab15qHqX6UqvAL", "success"};
		
		Object[][] dataSet = new Object[][]{arr1, arr2, arr3, arr4};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 17, 2);	
	}
	
	@DataProvider
	public Object[][] creditCashBackstoredCreditBalanceNullValueDataProvider(ITestContext testContext){
		
		String[] arr1 = {"11ed7792.686d.4c48.ac03.06b24d973738edgIxiKSB4","0","","0","description","GOOD_WILL", "GOOD_WILL", "001", "11ed7792.686d.4c48.ac03.06b24d973738edgIxiKSB4", "success"};
		String[] arr2 = {"11ed7792.686d.4c48.ac03.06b24d973738edgIxiKSB4","1","","0","description","GOOD_WILL", "GOOD_WILL", "002", "11ed7792.686d.4c48.ac03.06b24d973738edgIxiKSB4","success"};
		String[] arr3 = {"04bc25f5.0b4a.46ae.bd17.68f700a9ab15qHqX6UqvAL","0","","0","description","GOOD_WILL", "GOOD_WILL", "003", "04bc25f5.0b4a.46ae.bd17.68f700a9ab15qHqX6UqvAL","success"};
		String[] arr4 = {"04bc25f5.0b4a.46ae.bd17.68f700a9ab15qHqX6UqvAL","1","","0","description","GOOD_WILL", "GOOD_WILL", "004", "04bc25f5.0b4a.46ae.bd17.68f700a9ab15qHqX6UqvAL", "success"};
		
		Object[][] dataSet = new Object[][]{arr1, arr2, arr3, arr4};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 17, 2);	
	}
	
	
	@DataProvider
	public Object[][] creditCashBackstoredCreditBalanceNegetiveValueDataProvider(ITestContext testContext){
		
		String[] arr1 = {"11ed7792.686d.4c48.ac03.06b24d973738edgIxiKSB4","1","-1","0","description","GOOD_WILL", "GOOD_WILL", "001", "11ed7792.686d.4c48.ac03.06b24d973738edgIxiKSB4", "error"};
		String[] arr2 = {"11ed7792.686d.4c48.ac03.06b24d973738edgIxiKSB4","0","-1","0","description","GOOD_WILL", "GOOD_WILL", "002", "11ed7792.686d.4c48.ac03.06b24d973738edgIxiKSB4","error"};
		String[] arr3 = {"04bc25f5.0b4a.46ae.bd17.68f700a9ab15qHqX6UqvAL","1","-1","0","description","GOOD_WILL", "GOOD_WILL", "003", "04bc25f5.0b4a.46ae.bd17.68f700a9ab15qHqX6UqvAL","error"};
		String[] arr4 = {"04bc25f5.0b4a.46ae.bd17.68f700a9ab15qHqX6UqvAL","0","-1","0","description","GOOD_WILL", "GOOD_WILL", "004", "04bc25f5.0b4a.46ae.bd17.68f700a9ab15qHqX6UqvAL", "error"};
		
		Object[][] dataSet = new Object[][]{arr1, arr2, arr3, arr4};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 17, 2);	
	}
	
	@DataProvider
	public Object[][] creditCashBackearnedCreditBalanceDataProvider(ITestContext testContext){
		
		String[] arr1 = {"526a78d7.e9e9.4760.a8e0.06247305b0fc9Dqw8qETJG","0","0","0","description","GOOD_WILL", "GOOD_WILL", "001", "04bc25f5.0b4a.46ae.bd17.68f700a9ab15qHqX6UqvAL", "success"};
		String[] arr2 = {"04bc25f5.0b4a.46ae.bd17.68f700a9ab15qHqX6UqvAL","0","1","0","description","GOOD_WILL", "GOOD_WILL", "002", "04bc25f5.0b4a.46ae.bd17.68f700a9ab15qHqX6UqvAL","success"};
		String[] arr3 = {"04bc25f5.0b4a.46ae.bd17.68f700a9ab15qHqX6UqvAL","1","0","0","description","GOOD_WILL", "GOOD_WILL", "003", "04bc25f5.0b4a.46ae.bd17.68f700a9ab15qHqX6UqvAL","success"};
		String[] arr4 = {"04bc25f5.0b4a.46ae.bd17.68f700a9ab15qHqX6UqvAL","1","1","0","description","GOOD_WILL", "GOOD_WILL", "004", "04bc25f5.0b4a.46ae.bd17.68f700a9ab15qHqX6UqvAL", "success"};
		
		Object[][] dataSet = new Object[][]{arr1, arr2, arr3, arr4};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 17, 2);	
	}
	
	@DataProvider
	public Object[][] creditCashBackearnedCreditBalanceNullValueDataProvider(ITestContext testContext){
		
		String[] arr1 = {"11ed7792.686d.4c48.ac03.06b24d973738edgIxiKSB4","","0","0","description","GOOD_WILL", "GOOD_WILL", "001", "11ed7792.686d.4c48.ac03.06b24d973738edgIxiKSB4", "success"};
		String[] arr2 = {"11ed7792.686d.4c48.ac03.06b24d973738edgIxiKSB4","","1","0","description","GOOD_WILL", "GOOD_WILL", "002", "11ed7792.686d.4c48.ac03.06b24d973738edgIxiKSB4","success"};
		String[] arr3 = {"creditCashBackearnedCreditBalanceNullValueDataProvider","","0","0","description","GOOD_WILL", "GOOD_WILL", "003", "04bc25f5.0b4a.46ae.bd17.68f700a9ab15qHqX6UqvAL","success"};
		String[] arr4 = {"04bc25f5.0b4a.46ae.bd17.68f700a9ab15qHqX6UqvAL","","1","0","description","GOOD_WILL", "GOOD_WILL", "004", "04bc25f5.0b4a.46ae.bd17.68f700a9ab15qHqX6UqvAL", "success"};
		
		Object[][] dataSet = new Object[][]{arr1, arr2, arr3, arr4};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 17, 2);	
	}
	
	@DataProvider
	public Object[][] creditCashBackearnedCreditBalanceNegetiveValueDataProvider(ITestContext testContext){
		
		String[] arr1 = {"11ed7792.686d.4c48.ac03.06b24d973738edgIxiKSB4","-1","1","0","description","GOOD_WILL", "GOOD_WILL", "001", "11ed7792.686d.4c48.ac03.06b24d973738edgIxiKSB4", "error"};
		String[] arr2 = {"11ed7792.686d.4c48.ac03.06b24d973738edgIxiKSB4","-1","0","0","description","GOOD_WILL", "GOOD_WILL", "002", "11ed7792.686d.4c48.ac03.06b24d973738edgIxiKSB4","error"};
		String[] arr3 = {"04bc25f5.0b4a.46ae.bd17.68f700a9ab15qHqX6UqvAL","-1","1","0","description","GOOD_WILL", "GOOD_WILL", "003", "04bc25f5.0b4a.46ae.bd17.68f700a9ab15qHqX6UqvAL","error"};
		String[] arr4 = {"04bc25f5.0b4a.46ae.bd17.68f700a9ab15qHqX6UqvAL","-1","0","0","description","GOOD_WILL", "GOOD_WILL", "004", "04bc25f5.0b4a.46ae.bd17.68f700a9ab15qHqX6UqvAL", "error"};
		
		Object[][] dataSet = new Object[][]{arr1, arr2, arr3, arr4};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 17, 2);	
	}
	
	@DataProvider
	public Object[][] creditCashBackVerifyMessageDataProvider(ITestContext testContext){
		//last index is for user id
		String[] arr1 = {"04bc25f5.0b4a.46ae.bd17.68f700a9ab15qHqX6UqvAL","1","0","","description","GOOD_WILL", "GOOD_WILL", "001", "04bc25f5.0b4a.46ae.bd17.68f700a9ab15qHqX6UqvAL", "success"};
		
		Object[][] dataSet = new Object[][]{arr1};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 2);	
	}
	
	@DataProvider
	public Object[][] creditCashBackResponseNodesDataProvider(ITestContext testContext){
		String[] arr1 = {"11ed7792.686d.4c48.ac03.06b24d973738edgIxiKSB4","1","0","","description","GOOD_WILL", "GOOD_WILL", "001", "11ed7792.686d.4c48.ac03.06b24d973738edgIxiKSB4", "success"};
		
		Object[][] dataSet = new Object[][]{arr1};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 2);	
	}
	
	//End
	@DataProvider
	public Object[][] MyntCashDP_checkMyntCashBalance_verifyResponseDataNodesUsingSchemaValidations(ITestContext testContext)
	{
		String [] arr1 = { "Production", "526a78d7.e9e9.4760.a8e0.06247305b0fc9Dqw8qETJG", "200"};
		String [] arr2 = { "Production", "muser420@myntra.com", "200"};
		String [] arr3 = { "Fox7", "526a78d7.e9e9.4760.a8e0.06247305b0fc9Dqw8qETJG", "200"};
		String [] arr4 =  {"Fox7", "muser420@myntra.com", "200"};
		String [] arr5 = { "functional", "526a78d7.e9e9.4760.a8e0.06247305b0fc9Dqw8qETJG", "200"};	
		String [] arr6 = { "functional", "muser420@myntra.com", "200"};	
		
		Object [][] dataSet = new Object [][] { arr1, arr2, arr3, arr4, arr5, arr6 };
		return Toolbox.returnReducedDataSet(Env,dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	
	@DataProvider
	public Object[][] MyntCashDP_checkTransactionLogsofUser_verifyResponseDataNodesUsingSchemaValidations(ITestContext testContext)
	{
		String [] arr1 = {"Fox7","576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U", "200"};
		String [] arr2 = {"Fox7", "526a78d7.e9e9.4760.a8e0.06247305b0fc9Dqw8qETJG", "200"};
		
		Object[][] dataSet = new Object[][]{ arr1, arr2 };
		return Toolbox.returnReducedDataSet(Env,dataSet, testContext.getIncludedGroups(), 1, 2);	
	}
	
	@DataProvider
	public Object[][] MyntCashDP_debitCashBack_verifyResponseDataNodesUsingSchemaValidations(ITestContext testContext)
	{
		//last index is for user id
		String[] arr1 = { "576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U", "0", "0", "1", "description", "GOOD_WILL", "GOOD_WILL", "001", "200"};
		String[] arr2 = { "576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U", "0", "0", "100", "description", "GOOD_WILL", "GOOD_WILL", "002", "200"};
		String[] arr3 = { "576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U", "0", "0", "10", "description", "GOOD_WILL", "GOOD_WILL", "003", "200"};

		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 2);	
	}
	
	@DataProvider
	public Object[][] MyntCashDP_creditCashBack_verifyResponseDataNodesUsingSchemaValidations(ITestContext testContext)
	{
		//last index is for user id
		// login, earnedCreditAmount, storeCreditAmount, debitAmount, description, businessProcess, itemType, itemId
		String[] arr1 = { "576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U","1","0","","description","GOOD_WILL", "GOOD_WILL", "001", "200" };
		String[] arr2 = { "576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U","1","1","0","description","GOOD_WILL", "GOOD_WILL", "002", "200" };
		String[] arr3 = { "576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U","0","1","0","description","GOOD_WILL", "GOOD_WILL", "003", "200" };
		String[] arr4 = { "576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U","0","0","0","description","GOOD_WILL", "GOOD_WILL", "004", "200" };
		//Test cases for different itemType
		String[] arr5 = { "576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U","1","","100","description","GOOD_WILL", "GOOD_WILL", "005", "200" };
		String[] arr6 = {"576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U","1","","0","description","GOOD_WILL", "ORDER", "006", "200"};
		String[] arr7 = {"576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U","1","","0","description","GOOD_WILL", "RETURN_ID", "007", "200"};
		String[] arr8 = {"576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U","1","","0","description","GOOD_WILL", "ITEM_ID", "008", "200"};
		//Value of debitamount should get discarded
		String[] arr9 = {"576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U","1","1","100","description","GOOD_WILL", "GOOD_WILL", "009", "200"};
		//Credit large amount
		String[] arr10 = {"576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U","100","0","100","description","GOOD_WILL", "GOOD_WILL", "014", "200"};
		String[] arr11 = {"576ddbd1.a4e4.4392.9362.eecbaf5284eaemuo4n6l2U","0","5000","100","description","GOOD_WILL", "GOOD_WILL", "015", "200"};
		
		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4, arr5 , arr6, arr7, arr8, arr9, arr10, arr11 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 2);	
	}
}
	
 