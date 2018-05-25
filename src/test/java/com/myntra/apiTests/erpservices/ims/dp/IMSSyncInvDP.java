/**
 * 
 */
package com.myntra.apiTests.erpservices.ims.dp;

import com.myntra.apiTests.erpservices.sellerapis.SellerConfig;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import com.myntra.lordoftherings.Toolbox;

/**
 * @author santwana.samantray
 *
 */
public class IMSSyncInvDP {

    private static Long vectorSellerID;

    static {
        vectorSellerID = SellerConfig.getSellerID(SellerConfig.SellerName.ALFA);
    }

    @DataProvider
	public static Object[][] invincrement(
			ITestContext testContext) {
		//upload inv 
		String[] arr1 = {"36", "1",""+vectorSellerID,"ON_HAND","12216","ADDSFLTM09247","PUREPLAY_SELLER_ALLOCATE","100"};
				Object[][] dataSet = new Object[][] {arr1};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}
    
	@DataProvider
	public static Object[][] checkabsolute(
			ITestContext testContext) {
		//upload inv 
		String[] arr1 = {"36", "1",""+vectorSellerID,"ON_HAND","12216","ADDSFLTM09247","PUREPLAY_SELLER_ALLOCATE","100"};
				Object[][] dataSet = new Object[][] {arr1};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public static Object[][] invdecrement(
			ITestContext testContext) {
		//dec inv 
		String[] arr1 = {"36", "1",""+vectorSellerID,"ON_HAND","12216","ADDSFLTM09247","PUREPLAY_SELLER_ALLOCATE","10"};
				Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public static Object[][] invsetto0(
			ITestContext testContext) {
		//set inv 0
		String[] arr1 = {"36", "1",""+vectorSellerID,"ON_HAND","12216","ADDSFLTM09247","PUREPLAY_SELLER_ALLOCATE","0"};
				Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public static Object[][] invsetto0WhenbocMorethan0(
			ITestContext testContext) {
		//set inv 0 when boc more than 0
		String[] arr1 = {"36", "1",""+vectorSellerID,"ON_HAND","11767","ADDSSGMC09358","PUREPLAY_SELLER_ALLOCATE","0"};
				Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public static Object[][] setinvLessthanboc(
			ITestContext testContext) {
		//set inv less than boc
		String[] arr1 = {"36", "1",""+vectorSellerID,"ON_HAND","12216","ADDSFLTM09247","PUREPLAY_SELLER_ALLOCATE","5"};
				Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}
	@DataProvider
	public static Object[][] setinvequalsboc(
			ITestContext testContext) {
		//set inv equal boc
		String[] arr1 = {"36", "1",""+vectorSellerID,"ON_HAND","12216","ADDSFLTM09247","PUREPLAY_SELLER_ALLOCATE","5"};
				Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public static Object[][] setinvgreterthanboc(
			ITestContext testContext) {
		//set inv greater boc
		String[] arr1 = {"36", "1",""+vectorSellerID,"ON_HAND","12216","ADDSFLTM09247","PUREPLAY_SELLER_ALLOCATE","15"};
				Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}
	@DataProvider
	public static Object[][] uploadinvForNewSku(
			ITestContext testContext) {
		//set new sku
		String[] arr1 = {"36", "1",""+vectorSellerID,"ON_HAND","123","AUTOMATIONSKU1","PUREPLAY_SELLER_ALLOCATE","0"};
				Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public static Object[][] random_warehouse(
			ITestContext testContext) {
		//set new sku
		String[] arr1 = {"7117", "1",""+vectorSellerID,"ON_HAND","12216","ADDSFLTM09247","PUREPLAY_SELLER_ALLOCATE","15"};
				Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public static Object[][]  random_storeid(
			ITestContext testContext) {
		//set new sku
		String[] arr1 = {"36", "1212121",""+vectorSellerID,"ON_HAND","12216","ADDSFLTM09247","PUREPLAY_SELLER_ALLOCATE","15"};
				Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public static Object[][]  random_supplyType(
			ITestContext testContext) {
		//set new sku
		String[] arr1 ={"36", "1",""+vectorSellerID,"sjahslkhaklhsalk","12216","ADDSFLTM09247","PUREPLAY_SELLER_ALLOCATE","15"};
				Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public static Object[][]  random_whInventoryOperation(
			ITestContext testContext) {
		//set new sku
		String[] arr1 = {"36", "1",""+vectorSellerID,"ON_HAND","12216","ADDSFLTM09247","dgsdgksjkjd","15"};
				Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

}
