/**
 * 
 */
package com.myntra.apiTests.erpservices.atp.dp;

import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import com.myntra.lordoftherings.Toolbox;

/**
 * @author santwana.samantray
 *
 */
public class ATPtoStyleReindexingDP {
	@DataProvider
	public Object[][] SetInventoryLessThanThreshold(
			ITestContext testContext) {
		// paramOrderId, paramRespCode
		String[] arr1 = {"21249","1","200"};
		//String[] arr2 = {"skuid","1","200"};
		//String[] arr3 = {"skuid","1","200"};
	

		Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}
	@DataProvider
	public Object[][] SetInventoryMoreThanThreshold(
			ITestContext testContext) {
		// paramOrderId, paramRespCode
		String[] arr1 = {"361977","1","200"};
		//String[] arr2 = {"skuid","1","200"};
		//String[] arr3 = {"skuid","1","200"};


		Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}
	@DataProvider
	public Object[][] SetInventoryEqualtoThreshold(
			ITestContext testContext) {
		// paramOrderId, paramRespCode
		String[] arr1 = {"21254","1","200"};
		//String[] arr2 = {"skuid","1","200"};
		//String[] arr3 = {"skuid","1","200"};
	

		Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}
	@DataProvider
	public Object[][] UpdateInventorytoOOS(
			ITestContext testContext) {
		// paramOrderId, paramRespCode
		String[] arr1 = {"579498","1","200"};
		String[] arr2 = {"skuid","1","200"};
		String[] arr3 = {"skuid","1","200"};
		//String[] arr4 = { getRandomOrderId(), "200" };
		//String[] arr5 = { getRandomOrderId(), "200" };

		Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}
	@DataProvider
	public Object[][] UploadInventoryMorethanThreshhold(
			ITestContext testContext) {
		// paramOrderId, paramRespCode
		String[] arr1 = {"skuid","1","200"};
		String[] arr2 = {"skuid","1","200"};
		String[] arr3 = {"skuid","1","200"};
	

		Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}
	@DataProvider
	public Object[][] UploadInventoryLessThanThreshhold(
			ITestContext testContext) {
		// paramOrderId, paramRespCode
		String[] arr1 = {"skuid","1","200"};
		String[] arr2 = {"skuid","1","200"};
		String[] arr3 = {"skuid","1","200"};
	

		Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}
	@DataProvider
	public Object[][] UploadInventoryequalstoThreshhold(
			ITestContext testContext) {
		// paramOrderId, paramRespCode
		String[] arr1 = {"skuid","1","200"};
		String[] arr2 = {"skuid","1","200"};
		String[] arr3 = {"skuid","1","200"};
	
		Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] PlaceAOrderForOOSsku(
			ITestContext testContext) {
		// paramOrderId, paramRespCode
		String[] arr1 = {"522813","1","200"};
		String[] arr2 = {"skuid","1","200"};
		String[] arr3 = {"skuid","1","200"};
	

		Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}



}
