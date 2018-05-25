package com.myntra.apiTests.erpservices.utility;

import com.myntra.lordoftherings.Toolbox;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

/**
 * Created by 8403 on 29/10/15.
 */
public class MarkOrderDLDP {

    @DataProvider
    public Object[][] markOrderDL(ITestContext testContext) {

        String orderIDs = System.getenv("orderIds");


        String[] arr1 = orderIDs.split(",");

        Object[][] dataSet = new Object[][] { arr1 };
        return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 10, 10);
    }
}
