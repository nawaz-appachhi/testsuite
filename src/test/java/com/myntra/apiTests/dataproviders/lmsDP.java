package com.myntra.apiTests.dataproviders;

import com.myntra.lordoftherings.Toolbox;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

/**
 * Created by ashish.bajpai on 04/07/14.
 */
public class lmsDP {


    @DataProvider
    public Object[][] servicabilitycheck(ITestContext testContext)
    {
        String [] arr1 = {"560068"};
        String [] arr2 = {"560102"};
        String [] arr3 = {"560001"};
        String [] arr4 = {"560095"};
        Object [][] dataSet = new Object [][] {arr1,arr2,arr3,arr4};
        return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 1);
    }
}
