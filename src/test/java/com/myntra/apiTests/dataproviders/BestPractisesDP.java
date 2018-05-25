package com.myntra.apiTests.dataproviders;
import com.myntra.apiTests.portalservices.configservice.abtest.ABtestServiceHelper;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.Toolbox;

import java.util.ArrayList;
import java.util.List;

import com.myntra.lordoftherings.legolas.Commons;

public class BestPractisesDP extends ABtestServiceHelper {
	static Initialize init = new Initialize("/Data/configuration");
	Commons comUtil = new Commons();
	List<String> ids = new ArrayList();
	List<String> emails = new ArrayList();
	String completeInfoOfUserDefault = "id:${id},name:${name},email:${email},role:${role},from:${name},bio:${bio}";
	String completeInfoOfUserCreateUser = null;
	boolean isCreateUser = false;
	ArrayList styleIds = new ArrayList();
	
	@DataProvider
	public Object[][] updateExistingNegativeDP(ITestContext testContext)
	{
		String[] input1 = {"null","updated name", "updated email", "updated role", "updated city", "updated bio"};
		String[] input2 = {" ", "updated name", "updated email", "updated role", "updated city", "updated bio"};
		String[] input3 = {"1=1' or '1=1","updated name", "updated email", "updated role", "updated city", "updated bio"};
		Object[][] dataSet = new Object[][] { input1, input2 , input3};
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 3, 3);
	}
	
	@DataProvider
	public Object[][] getvariantofgivenabtestwithABtestDP_negative(ITestContext testContext){
		String[] ABTestName1 = {"laxmi@myntra.com", "JJN004392cd250a3c347a6be5549415a754be4", "random"};
		String[] ABTestName2 = {"laxmi@myntra.com", "", "random"};
		String[] ABTestName3 = {"laxmi@myntra.com", "JJN004392cd250a3c347a6be5549415a754b", "random1"};
		String[] ABTestName4 = {"", "JJN004392cd250a3c347a6be5549415a754be4", "$%#$%$#%#$"};
		String[] ABTestName5 = {"laxmi@abc.com", "JJN004392cd250a3c347a6be5549415a754be4", "$%#$%$#%#$"};
		Object[][] dataSet = new Object[][]{ABTestName1, ABTestName2, ABTestName3, ABTestName4, ABTestName5};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 2,3);
	}
	
	@DataProvider
	public Object[][] getvariantofgivenabtestwithsegmentDP_negative1(ITestContext testContext){
		String[] ABTestName1 = {"JJN004392cd250a3c347a6be5549415a754be4", ""};
		String[] ABTestName2 = {"", ""};
		String[] ABTestName3 = {"JJN004392cd250a3c347a6be5549415a754b", ""};
		Object[][] dataSet = new Object[][]{ABTestName1, ABTestName2, ABTestName3};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 3,4);
	}
	
	@DataProvider
	public Object[][] getVariantOfGivenABTestDP_negative1(ITestContext testContext){
		String[] ABTestName = {""};
		Object[][] dataSet = new Object[][]{ABTestName};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] getVariantOfGivenABTestDP_negative(ITestContext testContext){
		String[] ABTestName = {"random", "10008", "\"ABTest not found\"", "\"ERROR\"", "0"};
		String [] ABTestName2 = {"$%#$%$#%#$", "10008", "\"ABTest not found\"", "\"ERROR\"", "0"};
		Object[][] dataSet = new Object[][]{ABTestName, ABTestName2};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1,2);
	}
	
	@DataProvider
	public Object[][] NotificationServiceDP_markAsReadNotification_verifySuccessResponse(ITestContext testContext) 
	{
		String[] arr1 = { "fnsdfdfghhfg@gmail.com", "MYNTRA" };
		Object dataSet[][] = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 2);
	}

	// pageconfigurator
	
	/**
	 * Get Current version for a given pageType and pageVariant
	 * @param testContext
	 * @author jhansi.bai
	 * @return
	 */
	@DataProvider
	public Object[][] getCurrentVersionForPageTypeAndVariantDP(
			ITestContext testContext)
	{
		String[] arr1 = { "3", "0", "2", "1" };		
		Object[][] dataSet = new Object[][] {arr1};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1,2);
	}
	
	/**
	 * Invalidate Cache
	 * @param testContext
	 * @author jhansi.bai
	 * @return
	 */
	@DataProvider
	public Object[][] cachesDP_negative(
			ITestContext testContext)
	{
		String[] arr1 = { "widgetValueCache" };
		String[] arr2 = { null };		
		String[] arr3 = { "@#$@#$%#%" };
		
		Object[][] dataSet = new Object[][] {arr1, /*arr2, */arr3};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1,2);
	}
	
	@DataProvider
	public Object[][] getVersionDP_negative(
			ITestContext testContext)
	{
		String[] arr1 = { "9d@#$%%" };
		String[] arr2 = { null };
		Object[][] dataSet = new Object[][] { arr1, arr2 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 7,7);
	}
	
	@DataProvider
	public Object[][] getallpageversionsassociatedwithwidgetidDataProviderNegative(
			ITestContext testContext)
	{
		String[] arr1 = { null };		
		String[] arr2 = { "9d@#$%%" };
		Object[][] dataSet = new Object[][] { arr1, arr2 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 3,4);
	}
	
	
	
	
}
