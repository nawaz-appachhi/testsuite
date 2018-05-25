package com.myntra.apiTests.erpservices.sellerapis.dp;

import com.myntra.lordoftherings.Toolbox;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

/**
 * Created by abhijit.pati on 13/03/16.
 */
public class SellerServiceDP {

    @DataProvider
    public static Object [][] sellerApi_InventoryUpdate(ITestContext testContext)
    {
        //[skucode:inventoryCount:warehouse:sla]

        //String[] abc = {"8903880133658:1000:4:5"};//, "8903880133627:15:4:5" ,"8907164089808:10:1:5"
    	String[] abc = {"8903880486532:1000:4:5"};
    	Object[] arr1 = {abc};

        Object[][] dataSet = new Object[][] { arr1 };
        return Toolbox.returnReducedDataSet(dataSet,
                                            testContext.getIncludedGroups(), 5, 6);
    }

    @DataProvider
    public static Object [][] sellerApi_InvalidInvCount(ITestContext testContext)
    {
        //[skucode:inventoryCount:warehouse:sla]

        String[] abc = {"8903880133658:-1:4:5"};
        Object[] arr1 = {abc};

        Object[][] dataSet = new Object[][] { arr1 };
        return Toolbox.returnReducedDataSet(dataSet,
                                            testContext.getIncludedGroups(), 5, 6);
    }

    @DataProvider
    public static Object [][] sellerApi_InvalidSkuCode(ITestContext testContext)
    {
        //[skucode:inventoryCount:warehouse:sla]

        String[] abc = {"123:500:4:5"};//, "8903880133627:15:4:5" ,"8907164089808:10:1:5"
        Object[] arr1 = {abc};

        Object[][] dataSet = new Object[][] { arr1 };
        return Toolbox.returnReducedDataSet(dataSet,
                                            testContext.getIncludedGroups(), 5, 6);
    }

    @DataProvider
    public static Object [][] sellerApi_InvalidSla(ITestContext testContext)
    {
        //[skucode:inventoryCount:warehouse:sla]

        String[] abc = {"8903880133658:500:4:-1"};//, "8903880133627:15:4:5" ,"8907164089808:10:1:5"
        Object[] arr1 = {abc};

        Object[][] dataSet = new Object[][] { arr1 };
        return Toolbox.returnReducedDataSet(dataSet,
                                            testContext.getIncludedGroups(), 5, 6);
    }

    @DataProvider
    public static Object [][] sellerApi_InvalidWarehouse(ITestContext testContext)
    {
        //[skucode:inventoryCount:warehouse:sla]

        String[] abc = {"8903880133658:500:0:5"};//, "8903880133627:15:4:5" ,"8907164089808:10:1:5"
        Object[] arr1 = {abc};

        Object[][] dataSet = new Object[][] { arr1 };
        return Toolbox.returnReducedDataSet(dataSet,
                                            testContext.getIncludedGroups(), 5, 6);
    }

	/*@DataProvider
	public static Object [][] SellerApi_InventorySearch(ITestContext testContext)
	{
//		String [] arr1 = {"KKBLFLTOPS00033"};
		List<String> list = new List<String>();
		list.add("BLFLTOPS00033");

		Object[][] dataSet = new Object[][] { list };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 5, 6);
	}*/

    @DataProvider
    public static Object [][] sellerApi_CancelItem(ITestContext testContext)
    {
        String [] arr1 = { "11001100:1:2" };

        Object[][] dataSet = new Object[][] { arr1 };
        return Toolbox.returnReducedDataSet(dataSet,
                                            testContext.getIncludedGroups(), 5, 6);
    }


    @DataProvider
    public static Object [][] sellerApi_TaxUpdate(ITestContext testContext)
    {
        String [] arr1 = { "100.00:10.1:testing" };

        Object[][] dataSet = new Object[][] { arr1 };
        return Toolbox.returnReducedDataSet(dataSet,
                                            testContext.getIncludedGroups(), 5, 6);
    }

}
