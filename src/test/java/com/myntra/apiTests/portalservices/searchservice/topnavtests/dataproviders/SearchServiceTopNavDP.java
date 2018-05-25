package com.myntra.apiTests.portalservices.searchservice.topnavtests.dataproviders;

import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import com.myntra.lordoftherings.Toolbox;

/**
 * @author shankara.c
 *
 */
public class SearchServiceTopNavDP 
{
	@DataProvider
    public Object[][] SearchServiceTopNavDP_getQueryAPI_invokeAllDesktopTopNav_verifySuccessResponse(ITestContext testContext)
	{
		String[] arr1 = new String[]{ "96", "true", "true" };
		//String[] arr2 = new String[]{ "49", "true", "true" };
        
        Object[][] dataSet = new Object[][]{ arr1 /*, arr2*/ };
        return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 2);
    }
	
	@DataProvider
    public Object[][] SearchServiceTopNavDP_getQueryAPI_invokeAllWindowsPhoneTopNav_verifySuccessResponse(ITestContext testContext)
	{
		//String[] arr1 = new String[]{ "96", "true", "true" };
		String[] arr2 = new String[]{ "49", "true", "true" };
        
        Object[][] dataSet = new Object[][]{ /*arr1,*/ arr2 };
        return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 2);
    }
	
	@DataProvider
    public Object[][] SearchServiceTopNavDP_getQuery_invokeAllAndroidPhoneTopNav_verifySuccessResponse(ITestContext testContext)
	{
		//String[] arr1 = new String[]{ "96", "true", "true" };
		String[] arr2 = new String[]{ "49", "true", "true" };
        
        Object[][] dataSet = new Object[][]{ /*arr1,*/ arr2 };
        return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 2);
    }
	
	@DataProvider
    public Object[][] SearchServiceTopNavDP_getQuery_invokeAllIOSPhoneTopNav_verifySuccessResponse(ITestContext testContext)
	{
		//String[] arr1 = new String[]{ "96", "true", "true" };
		String[] arr2 = new String[]{ "49", "true", "true" };
        
        Object[][] dataSet = new Object[][]{ /*arr1,*/ arr2 };
        return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 2);
    }
	
	@DataProvider
    public Object[][] SearchServiceTopNavDP_getQuery_invokeAllMobileWebTopNav_verifySuccessResponse(ITestContext testContext)
	{
		//String[] arr1 = new String[]{ "96", "true", "true" };
		String[] arr2 = new String[]{ "49", "true", "true" };
        
        Object[][] dataSet = new Object[][]{ /*arr1,*/ arr2 };
        return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 2);
    }
}
