package com.myntra.apiTests.dataproviders;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.myntra.apiTests.portalservices.sessionservice.SessionServiceConstants;
import com.myntra.apiTests.portalservices.commons.IServiceConstants;
import org.apache.commons.lang.time.DateUtils;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import com.myntra.lordoftherings.Toolbox;

/**
 * @author shankara.c
 *
 */
public class SessionServiceDP implements SessionServiceConstants
{
	String environment;
	
	public SessionServiceDP()
	{
		if(System.getenv(IServiceConstants.ENVIRONMENT) == null)
			environment = IServiceConstants.CONFIGURATION.GetTestEnvironemnt().toString();
		else
			environment = System.getenv(IServiceConstants.ENVIRONMENT);
	}
	
	@DataProvider
    public Object[][] SessionServiceDP_getSessionDataProvider_verifySuccessResponse(ITestContext testContext)
	{
		String[] arr1 = new String[]{ IServiceConstants.ENVIRONMENT_PROD, PROD_VALID_USER, PROD_VALID_PASS };
		String[] arr2 = new String[]{ IServiceConstants.ENVIRONMENT_FOX7, FOX7_VALID_USER, FOX7_VALID_PASS };
		String[] arr3 = new String[]{ IServiceConstants.ENVIRONMENT_FUNC, FUNC_VALID_USER, FUNC_VALID_PASS };
		String[] arr4 = new String[]{ IServiceConstants.ENVIRONMENT_PERF, PERF_VALID_USER, PERF_VALID_PASS };
		
		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4 };
        return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 1, 1);
    }
	
	@DataProvider
    public Object[][] SessionServiceDP_getSessionDataProvider_validateSessionNodes(ITestContext testContext)
	{
		String[] arr1 = new String[]{ IServiceConstants.ENVIRONMENT_PROD, PROD_VALID_USER, PROD_VALID_PASS };
		String[] arr2 = new String[]{ IServiceConstants.ENVIRONMENT_FOX7, FOX7_VALID_USER, FOX7_VALID_PASS };
		String[] arr3 = new String[]{ IServiceConstants.ENVIRONMENT_FUNC, FUNC_VALID_USER, FUNC_VALID_PASS };
		String[] arr4 = new String[]{ IServiceConstants.ENVIRONMENT_PERF, PERF_VALID_USER, PERF_VALID_PASS };
		
		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4 };
        return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 1, 1);
	}
	
	@DataProvider
    public Object[][] SessionServiceDP_getSessionDataProvider_validateSessionCDATANodes(ITestContext testContext)
	{
		String[] arr1 = new String[]{ IServiceConstants.ENVIRONMENT_PROD, PROD_VALID_USER, PROD_VALID_PASS };
		String[] arr2 = new String[]{ IServiceConstants.ENVIRONMENT_FOX7, FOX7_VALID_USER, FOX7_VALID_PASS };
		String[] arr3 = new String[]{ IServiceConstants.ENVIRONMENT_FUNC, FUNC_VALID_USER, FUNC_VALID_PASS };
		String[] arr4 = new String[]{ IServiceConstants.ENVIRONMENT_PERF, PERF_VALID_USER, PERF_VALID_PASS };
		
		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4 };
        return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 1, 1);
	}
	
	/**
	 * Creation Date, Last Accessed Date, Last Modified Date - Same Date, Different Time, PM.
	 * Expiry Date - Future Date, AM
	 * @param iTestContext
	 * @return
	 */
	@DataProvider 
	public Object[][] SessionServiceDP_createSessionDataProvider_verifySuccessResponse(ITestContext iTestContext)
	{
		Date toDay = Calendar.getInstance().getTime();
		
		String[] arr1 = { IServiceConstants.ENVIRONMENT_PROD, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+10), String.valueOf(toDay.getTime()+20),
				String.valueOf(toDay.getTime()+30), String.valueOf(DateUtils.addDays(toDay, 1).getTime()), PROD_VALID_USER, PROD_VALID_PASS };
		String[] arr2 = { IServiceConstants.ENVIRONMENT_PROD, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+100), String.valueOf(toDay.getTime()+200),
				String.valueOf(toDay.getTime()+300), String.valueOf(DateUtils.addDays(toDay, 10).getTime()), PROD_VALID_USER, PROD_VALID_PASS };
		String[] arr3 = { IServiceConstants.ENVIRONMENT_PROD, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+200), String.valueOf(toDay.getTime()+400),
				String.valueOf(toDay.getTime()+600), String.valueOf(DateUtils.addDays(toDay, 4).getTime()), PROD_VALID_USER, PROD_VALID_PASS };
		String[] arr4 = { IServiceConstants.ENVIRONMENT_PROD, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+50), String.valueOf(toDay.getTime()+70),
				String.valueOf(toDay.getTime()+90), String.valueOf(DateUtils.addDays(toDay, 2).getTime()), PROD_VALID_USER, PROD_VALID_PASS };
		String[] arr5 = { IServiceConstants.ENVIRONMENT_PROD, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+1000), String.valueOf(toDay.getTime()+2000),
				String.valueOf(toDay.getTime()+3000), String.valueOf(DateUtils.addDays(toDay, 3).getTime()), PROD_VALID_USER, PROD_VALID_PASS };
		String[] arr6 = { IServiceConstants.ENVIRONMENT_PROD, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+2000), String.valueOf(toDay.getTime()+4000),
				String.valueOf(toDay.getTime()+6000), String.valueOf(DateUtils.addDays(toDay, 15).getTime()), PROD_VALID_USER, PROD_VALID_PASS };
		
		String[] arr7 = { IServiceConstants.ENVIRONMENT_FOX7, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+10), String.valueOf(toDay.getTime()+20),
				String.valueOf(toDay.getTime()+30), String.valueOf(DateUtils.addDays(toDay, 1).getTime()), FOX7_VALID_USER, FOX7_VALID_PASS };
		String[] arr8 = { IServiceConstants.ENVIRONMENT_FOX7, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+100), String.valueOf(toDay.getTime()+200),
				String.valueOf(toDay.getTime()+300), String.valueOf(DateUtils.addDays(toDay, 10).getTime()), FOX7_VALID_USER, FOX7_VALID_PASS };
		String[] arr9 = { IServiceConstants.ENVIRONMENT_FOX7, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+200), String.valueOf(toDay.getTime()+400),
				String.valueOf(toDay.getTime()+600), String.valueOf(DateUtils.addDays(toDay, 4).getTime()), FOX7_VALID_USER, FOX7_VALID_PASS };
		String[] arr10 = { IServiceConstants.ENVIRONMENT_FOX7, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+50), String.valueOf(toDay.getTime()+70),
				String.valueOf(toDay.getTime()+90), String.valueOf(DateUtils.addDays(toDay, 2).getTime()), FOX7_VALID_USER, FOX7_VALID_PASS };
		String[] arr11 = { IServiceConstants.ENVIRONMENT_FOX7, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+1000), String.valueOf(toDay.getTime()+2000),
				String.valueOf(toDay.getTime()+3000), String.valueOf(DateUtils.addDays(toDay, 3).getTime()), FOX7_VALID_USER, FOX7_VALID_PASS };
		String[] arr12 = { IServiceConstants.ENVIRONMENT_FOX7, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+2000), String.valueOf(toDay.getTime()+4000),
				String.valueOf(toDay.getTime()+6000), String.valueOf(DateUtils.addDays(toDay, 15).getTime()), FOX7_VALID_USER, FOX7_VALID_PASS };
		
		String[] arr13 = { IServiceConstants.ENVIRONMENT_FUNC, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+10), String.valueOf(toDay.getTime()+20),
				String.valueOf(toDay.getTime()+30), String.valueOf(DateUtils.addDays(toDay, 1).getTime()), FUNC_VALID_USER, FUNC_VALID_PASS };
		String[] arr14 = { IServiceConstants.ENVIRONMENT_FUNC, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+100), String.valueOf(toDay.getTime()+200),
				String.valueOf(toDay.getTime()+300), String.valueOf(DateUtils.addDays(toDay, 10).getTime()), FUNC_VALID_USER, FUNC_VALID_PASS };
		String[] arr15 = { IServiceConstants.ENVIRONMENT_FUNC, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+200), String.valueOf(toDay.getTime()+400),
				String.valueOf(toDay.getTime()+600), String.valueOf(DateUtils.addDays(toDay, 4).getTime()), FUNC_VALID_USER, FUNC_VALID_PASS };
		String[] arr16 = { IServiceConstants.ENVIRONMENT_FUNC, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+50), String.valueOf(toDay.getTime()+70),
				String.valueOf(toDay.getTime()+90), String.valueOf(DateUtils.addDays(toDay, 2).getTime()), FUNC_VALID_USER, FUNC_VALID_PASS };
		String[] arr17 = { IServiceConstants.ENVIRONMENT_FUNC, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+1000), String.valueOf(toDay.getTime()+2000),
				String.valueOf(toDay.getTime()+3000), String.valueOf(DateUtils.addDays(toDay, 3).getTime()), FUNC_VALID_USER, FUNC_VALID_PASS };
		String[] arr18 = { IServiceConstants.ENVIRONMENT_FUNC, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+2000), String.valueOf(toDay.getTime()+4000),
				String.valueOf(toDay.getTime()+6000), String.valueOf(DateUtils.addDays(toDay, 15).getTime()), FUNC_VALID_USER, FUNC_VALID_PASS };
		
		String[] arr19 = { IServiceConstants.ENVIRONMENT_PERF, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+10), String.valueOf(toDay.getTime()+20),
				String.valueOf(toDay.getTime()+30), String.valueOf(DateUtils.addDays(toDay, 1).getTime()), PERF_VALID_USER, PERF_VALID_PASS };
		String[] arr20= { IServiceConstants.ENVIRONMENT_PERF, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+100), String.valueOf(toDay.getTime()+200),
				String.valueOf(toDay.getTime()+300), String.valueOf(DateUtils.addDays(toDay, 10).getTime()), PERF_VALID_USER, PERF_VALID_PASS };
		String[] arr21 = { IServiceConstants.ENVIRONMENT_PERF, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+200), String.valueOf(toDay.getTime()+400),
				String.valueOf(toDay.getTime()+600), String.valueOf(DateUtils.addDays(toDay, 4).getTime()), PERF_VALID_USER, PERF_VALID_PASS };
		String[] arr22 = { IServiceConstants.ENVIRONMENT_PERF, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+50), String.valueOf(toDay.getTime()+70),
				String.valueOf(toDay.getTime()+90), String.valueOf(DateUtils.addDays(toDay, 2).getTime()), PERF_VALID_USER, PERF_VALID_PASS };
		String[] arr23 = { IServiceConstants.ENVIRONMENT_PERF, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+1000), String.valueOf(toDay.getTime()+2000),
				String.valueOf(toDay.getTime()+3000), String.valueOf(DateUtils.addDays(toDay, 3).getTime()), PERF_VALID_USER, PERF_VALID_PASS };
		String[] arr24 = { IServiceConstants.ENVIRONMENT_PERF, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+2000), String.valueOf(toDay.getTime()+4000),
				String.valueOf(toDay.getTime()+6000), String.valueOf(DateUtils.addDays(toDay, 15).getTime()), PERF_VALID_USER, PERF_VALID_PASS };
		
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9, arr10, arr11, arr12, arr13, arr14, arr15, arr16, arr17, 
				arr18, arr19, arr20, arr21, arr22, arr23, arr24 };
		return Toolbox.returnReducedDataSet(environment, dataSet, iTestContext.getIncludedGroups(), 1, 2);
	}

	/**
	 * Creation Date, Last Accessed Date, Last Modified Date - Same Date, Different Time, PM.
	 * Expiry Date - Future Date, AM
	 * @param iTestContext
	 * @return
	 */
	@DataProvider 
	public Object[][] SessionServiceDP_createSessionDataProvider_validateSessionNodes(ITestContext iTestContext)
	{
		Date toDay = Calendar.getInstance().getTime();
		
		String[] arr1 = { IServiceConstants.ENVIRONMENT_PROD, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+10), String.valueOf(toDay.getTime()+20),
				String.valueOf(toDay.getTime()+30), String.valueOf(DateUtils.addDays(toDay, 1).getTime()), PROD_VALID_USER, PROD_VALID_PASS };
		String[] arr2 = { IServiceConstants.ENVIRONMENT_PROD, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+100), String.valueOf(toDay.getTime()+200),
				String.valueOf(toDay.getTime()+300), String.valueOf(DateUtils.addDays(toDay, 10).getTime()), PROD_VALID_USER, PROD_VALID_PASS };
		String[] arr3 = { IServiceConstants.ENVIRONMENT_PROD, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+200), String.valueOf(toDay.getTime()+400),
				String.valueOf(toDay.getTime()+600), String.valueOf(DateUtils.addDays(toDay, 4).getTime()), PROD_VALID_USER, PROD_VALID_PASS };
		String[] arr4 = { IServiceConstants.ENVIRONMENT_PROD, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+50), String.valueOf(toDay.getTime()+70),
				String.valueOf(toDay.getTime()+90), String.valueOf(DateUtils.addDays(toDay, 2).getTime()), PROD_VALID_USER, PROD_VALID_PASS };
		String[] arr5 = { IServiceConstants.ENVIRONMENT_PROD, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+1000), String.valueOf(toDay.getTime()+2000),
				String.valueOf(toDay.getTime()+3000), String.valueOf(DateUtils.addDays(toDay, 3).getTime()), PROD_VALID_USER, PROD_VALID_PASS };
		String[] arr6 = { IServiceConstants.ENVIRONMENT_PROD, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+2000), String.valueOf(toDay.getTime()+4000),
				String.valueOf(toDay.getTime()+6000), String.valueOf(DateUtils.addDays(toDay, 15).getTime()), PROD_VALID_USER, PROD_VALID_PASS };
		
		String[] arr7 = { IServiceConstants.ENVIRONMENT_FOX7, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+10), String.valueOf(toDay.getTime()+20),
				String.valueOf(toDay.getTime()+30), String.valueOf(DateUtils.addDays(toDay, 1).getTime()), FOX7_VALID_USER, FOX7_VALID_PASS };
		String[] arr8 = { IServiceConstants.ENVIRONMENT_FOX7, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+100), String.valueOf(toDay.getTime()+200),
				String.valueOf(toDay.getTime()+300), String.valueOf(DateUtils.addDays(toDay, 10).getTime()), FOX7_VALID_USER, FOX7_VALID_PASS };
		String[] arr9 = { IServiceConstants.ENVIRONMENT_FOX7, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+200), String.valueOf(toDay.getTime()+400),
				String.valueOf(toDay.getTime()+600), String.valueOf(DateUtils.addDays(toDay, 4).getTime()), FOX7_VALID_USER, FOX7_VALID_PASS };
		String[] arr10 = { IServiceConstants.ENVIRONMENT_FOX7, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+50), String.valueOf(toDay.getTime()+70),
				String.valueOf(toDay.getTime()+90), String.valueOf(DateUtils.addDays(toDay, 2).getTime()), FOX7_VALID_USER, FOX7_VALID_PASS };
		String[] arr11 = { IServiceConstants.ENVIRONMENT_FOX7, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+1000), String.valueOf(toDay.getTime()+2000),
				String.valueOf(toDay.getTime()+3000), String.valueOf(DateUtils.addDays(toDay, 3).getTime()), FOX7_VALID_USER, FOX7_VALID_PASS };
		String[] arr12 = { IServiceConstants.ENVIRONMENT_FOX7, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+2000), String.valueOf(toDay.getTime()+4000),
				String.valueOf(toDay.getTime()+6000), String.valueOf(DateUtils.addDays(toDay, 15).getTime()), FOX7_VALID_USER, FOX7_VALID_PASS };
		
		String[] arr13 = { IServiceConstants.ENVIRONMENT_FUNC, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+10), String.valueOf(toDay.getTime()+20),
				String.valueOf(toDay.getTime()+30), String.valueOf(DateUtils.addDays(toDay, 1).getTime()), FUNC_VALID_USER, FUNC_VALID_PASS };
		String[] arr14 = { IServiceConstants.ENVIRONMENT_FUNC, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+100), String.valueOf(toDay.getTime()+200),
				String.valueOf(toDay.getTime()+300), String.valueOf(DateUtils.addDays(toDay, 10).getTime()), FUNC_VALID_USER, FUNC_VALID_PASS };
		String[] arr15 = { IServiceConstants.ENVIRONMENT_FUNC, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+200), String.valueOf(toDay.getTime()+400),
				String.valueOf(toDay.getTime()+600), String.valueOf(DateUtils.addDays(toDay, 4).getTime()), FUNC_VALID_USER, FUNC_VALID_PASS };
		String[] arr16 = { IServiceConstants.ENVIRONMENT_FUNC, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+50), String.valueOf(toDay.getTime()+70),
				String.valueOf(toDay.getTime()+90), String.valueOf(DateUtils.addDays(toDay, 2).getTime()), FUNC_VALID_USER, FUNC_VALID_PASS };
		String[] arr17 = { IServiceConstants.ENVIRONMENT_FUNC, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+1000), String.valueOf(toDay.getTime()+2000),
				String.valueOf(toDay.getTime()+3000), String.valueOf(DateUtils.addDays(toDay, 3).getTime()), FUNC_VALID_USER, FUNC_VALID_PASS };
		String[] arr18 = { IServiceConstants.ENVIRONMENT_FUNC, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+2000), String.valueOf(toDay.getTime()+4000),
				String.valueOf(toDay.getTime()+6000), String.valueOf(DateUtils.addDays(toDay, 15).getTime()), FUNC_VALID_USER, FUNC_VALID_PASS };
		
		String[] arr19 = { IServiceConstants.ENVIRONMENT_PERF, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+10), String.valueOf(toDay.getTime()+20),
				String.valueOf(toDay.getTime()+30), String.valueOf(DateUtils.addDays(toDay, 1).getTime()), PERF_VALID_USER, PERF_VALID_PASS };
		String[] arr20= { IServiceConstants.ENVIRONMENT_PERF, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+100), String.valueOf(toDay.getTime()+200),
				String.valueOf(toDay.getTime()+300), String.valueOf(DateUtils.addDays(toDay, 10).getTime()), PERF_VALID_USER, PERF_VALID_PASS };
		String[] arr21 = { IServiceConstants.ENVIRONMENT_PERF, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+200), String.valueOf(toDay.getTime()+400),
				String.valueOf(toDay.getTime()+600), String.valueOf(DateUtils.addDays(toDay, 4).getTime()), PERF_VALID_USER, PERF_VALID_PASS };
		String[] arr22 = { IServiceConstants.ENVIRONMENT_PERF, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+50), String.valueOf(toDay.getTime()+70),
				String.valueOf(toDay.getTime()+90), String.valueOf(DateUtils.addDays(toDay, 2).getTime()), PERF_VALID_USER, PERF_VALID_PASS };
		String[] arr23 = { IServiceConstants.ENVIRONMENT_PERF, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+1000), String.valueOf(toDay.getTime()+2000),
				String.valueOf(toDay.getTime()+3000), String.valueOf(DateUtils.addDays(toDay, 3).getTime()), PERF_VALID_USER, PERF_VALID_PASS };
		String[] arr24 = { IServiceConstants.ENVIRONMENT_PERF, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+2000), String.valueOf(toDay.getTime()+4000),
				String.valueOf(toDay.getTime()+6000), String.valueOf(DateUtils.addDays(toDay, 15).getTime()), PERF_VALID_USER, PERF_VALID_PASS };

		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9, arr10, arr11, arr12, arr13, arr14, arr15, arr16, arr17, 
				arr18, arr19, arr20, arr21, arr22, arr23, arr24 };
		return Toolbox.returnReducedDataSet(environment, dataSet, iTestContext.getIncludedGroups(), 1, 2);
	}
	
	/**
	 * Creation Date, Last Accessed Date, Last Modified Date - Same Date, Different Time, PM.
	 * Expiry Date - Future Date, AM
	 * @param iTestContext
	 * @return
	 */
	@DataProvider 
	public Object[][] SessionServiceDP_createSessionDataProvider_validateSessionCDATANodes(ITestContext iTestContext)
	{
		Date toDay = Calendar.getInstance().getTime();
		
		String[] arr1 = { IServiceConstants.ENVIRONMENT_PROD, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+10), String.valueOf(toDay.getTime()+20),
				String.valueOf(toDay.getTime()+30), String.valueOf(DateUtils.addDays(toDay, 1).getTime()), PROD_VALID_USER, PROD_VALID_PASS };
		String[] arr2 = { IServiceConstants.ENVIRONMENT_PROD, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+100), String.valueOf(toDay.getTime()+200),
				String.valueOf(toDay.getTime()+300), String.valueOf(DateUtils.addDays(toDay, 10).getTime()), PROD_VALID_USER, PROD_VALID_PASS };
		String[] arr3 = { IServiceConstants.ENVIRONMENT_PROD, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+200), String.valueOf(toDay.getTime()+400),
				String.valueOf(toDay.getTime()+600), String.valueOf(DateUtils.addDays(toDay, 4).getTime()), PROD_VALID_USER, PROD_VALID_PASS };
		String[] arr4 = { IServiceConstants.ENVIRONMENT_PROD, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+50), String.valueOf(toDay.getTime()+70),
				String.valueOf(toDay.getTime()+90), String.valueOf(DateUtils.addDays(toDay, 2).getTime()), PROD_VALID_USER, PROD_VALID_PASS };
		String[] arr5 = { IServiceConstants.ENVIRONMENT_PROD, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+1000), String.valueOf(toDay.getTime()+2000),
				String.valueOf(toDay.getTime()+3000), String.valueOf(DateUtils.addDays(toDay, 3).getTime()), PROD_VALID_USER, PROD_VALID_PASS };
		String[] arr6 = { IServiceConstants.ENVIRONMENT_PROD, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+2000), String.valueOf(toDay.getTime()+4000),
				String.valueOf(toDay.getTime()+6000), String.valueOf(DateUtils.addDays(toDay, 15).getTime()), PROD_VALID_USER, PROD_VALID_PASS };
		
		String[] arr7 = { IServiceConstants.ENVIRONMENT_FOX7, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+10), String.valueOf(toDay.getTime()+20),
				String.valueOf(toDay.getTime()+30), String.valueOf(DateUtils.addDays(toDay, 1).getTime()), FOX7_VALID_USER, FOX7_VALID_PASS };
		String[] arr8 = { IServiceConstants.ENVIRONMENT_FOX7, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+100), String.valueOf(toDay.getTime()+200),
				String.valueOf(toDay.getTime()+300), String.valueOf(DateUtils.addDays(toDay, 10).getTime()), FOX7_VALID_USER, FOX7_VALID_PASS };
		String[] arr9 = { IServiceConstants.ENVIRONMENT_FOX7, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+200), String.valueOf(toDay.getTime()+400),
				String.valueOf(toDay.getTime()+600), String.valueOf(DateUtils.addDays(toDay, 4).getTime()), FOX7_VALID_USER, FOX7_VALID_PASS };
		String[] arr10 = { IServiceConstants.ENVIRONMENT_FOX7, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+50), String.valueOf(toDay.getTime()+70),
				String.valueOf(toDay.getTime()+90), String.valueOf(DateUtils.addDays(toDay, 2).getTime()), FOX7_VALID_USER, FOX7_VALID_PASS };
		String[] arr11 = { IServiceConstants.ENVIRONMENT_FOX7, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+1000), String.valueOf(toDay.getTime()+2000),
				String.valueOf(toDay.getTime()+3000), String.valueOf(DateUtils.addDays(toDay, 3).getTime()), FOX7_VALID_USER, FOX7_VALID_PASS };
		String[] arr12 = { IServiceConstants.ENVIRONMENT_FOX7, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+2000), String.valueOf(toDay.getTime()+4000),
				String.valueOf(toDay.getTime()+6000), String.valueOf(DateUtils.addDays(toDay, 15).getTime()), FOX7_VALID_USER, FOX7_VALID_PASS };
		
		String[] arr13 = { IServiceConstants.ENVIRONMENT_FUNC, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+10), String.valueOf(toDay.getTime()+20),
				String.valueOf(toDay.getTime()+30), String.valueOf(DateUtils.addDays(toDay, 1).getTime()), FUNC_VALID_USER, FUNC_VALID_PASS };
		String[] arr14 = { IServiceConstants.ENVIRONMENT_FUNC, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+100), String.valueOf(toDay.getTime()+200),
				String.valueOf(toDay.getTime()+300), String.valueOf(DateUtils.addDays(toDay, 10).getTime()), FUNC_VALID_USER, FUNC_VALID_PASS };
		String[] arr15 = { IServiceConstants.ENVIRONMENT_FUNC, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+200), String.valueOf(toDay.getTime()+400),
				String.valueOf(toDay.getTime()+600), String.valueOf(DateUtils.addDays(toDay, 4).getTime()), FUNC_VALID_USER, FUNC_VALID_PASS };
		String[] arr16 = { IServiceConstants.ENVIRONMENT_FUNC, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+50), String.valueOf(toDay.getTime()+70),
				String.valueOf(toDay.getTime()+90), String.valueOf(DateUtils.addDays(toDay, 2).getTime()), FUNC_VALID_USER, FUNC_VALID_PASS };
		String[] arr17 = { IServiceConstants.ENVIRONMENT_FUNC, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+1000), String.valueOf(toDay.getTime()+2000),
				String.valueOf(toDay.getTime()+3000), String.valueOf(DateUtils.addDays(toDay, 3).getTime()), FUNC_VALID_USER, FUNC_VALID_PASS };
		String[] arr18 = { IServiceConstants.ENVIRONMENT_FUNC, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+2000), String.valueOf(toDay.getTime()+4000),
				String.valueOf(toDay.getTime()+6000), String.valueOf(DateUtils.addDays(toDay, 15).getTime()), FUNC_VALID_USER, FUNC_VALID_PASS };
		
		String[] arr19 = { IServiceConstants.ENVIRONMENT_PERF, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+10), String.valueOf(toDay.getTime()+20),
				String.valueOf(toDay.getTime()+30), String.valueOf(DateUtils.addDays(toDay, 1).getTime()), PERF_VALID_USER, PERF_VALID_PASS };
		String[] arr20= { IServiceConstants.ENVIRONMENT_PERF, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+100), String.valueOf(toDay.getTime()+200),
				String.valueOf(toDay.getTime()+300), String.valueOf(DateUtils.addDays(toDay, 10).getTime()), PERF_VALID_USER, PERF_VALID_PASS };
		String[] arr21 = { IServiceConstants.ENVIRONMENT_PERF, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+200), String.valueOf(toDay.getTime()+400),
				String.valueOf(toDay.getTime()+600), String.valueOf(DateUtils.addDays(toDay, 4).getTime()), PERF_VALID_USER, PERF_VALID_PASS };
		String[] arr22 = { IServiceConstants.ENVIRONMENT_PERF, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+50), String.valueOf(toDay.getTime()+70),
				String.valueOf(toDay.getTime()+90), String.valueOf(DateUtils.addDays(toDay, 2).getTime()), PERF_VALID_USER, PERF_VALID_PASS };
		String[] arr23 = { IServiceConstants.ENVIRONMENT_PERF, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+1000), String.valueOf(toDay.getTime()+2000),
				String.valueOf(toDay.getTime()+3000), String.valueOf(DateUtils.addDays(toDay, 3).getTime()), PERF_VALID_USER, PERF_VALID_PASS };
		String[] arr24 = { IServiceConstants.ENVIRONMENT_PERF, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+2000), String.valueOf(toDay.getTime()+4000),
				String.valueOf(toDay.getTime()+6000), String.valueOf(DateUtils.addDays(toDay, 15).getTime()), PERF_VALID_USER, PERF_VALID_PASS };

		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9, arr10, arr11, arr12, arr13, arr14, arr15, arr16, arr17, 
				arr18, arr19, arr20, arr21, arr22, arr23, arr24 };
		return Toolbox.returnReducedDataSet(environment, dataSet, iTestContext.getIncludedGroups(), 1, 2);
	}
	
	/**
	 * Creation Date, Last Accessed Date, Last Modified Date - Same Date, Different Time, PM.
	 * Expiry Date - Future Date, AM
	 * @param iTestContext
	 * @return
	 */
	@DataProvider 
	public Object[][] SessionServiceDP_updateSessionDataProvider_verifySuccessResponse(ITestContext iTestContext)
	{
		Date toDay = Calendar.getInstance().getTime();
		
		String[] arr1 = { IServiceConstants.ENVIRONMENT_PROD, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+10), String.valueOf(toDay.getTime()+20),
				String.valueOf(toDay.getTime()+30), String.valueOf(DateUtils.addDays(toDay, 1).getTime()), PROD_VALID_USER, PROD_VALID_PASS };
		String[] arr2 = { IServiceConstants.ENVIRONMENT_PROD, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+100), String.valueOf(toDay.getTime()+200),
				String.valueOf(toDay.getTime()+300), String.valueOf(DateUtils.addDays(toDay, 10).getTime()), PROD_VALID_USER, PROD_VALID_PASS };
		String[] arr3 = { IServiceConstants.ENVIRONMENT_PROD, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+200), String.valueOf(toDay.getTime()+400),
				String.valueOf(toDay.getTime()+600), String.valueOf(DateUtils.addDays(toDay, 4).getTime()), PROD_VALID_USER, PROD_VALID_PASS };
		String[] arr4 = { IServiceConstants.ENVIRONMENT_PROD, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+50), String.valueOf(toDay.getTime()+70),
				String.valueOf(toDay.getTime()+90), String.valueOf(DateUtils.addDays(toDay, 2).getTime()), PROD_VALID_USER, PROD_VALID_PASS };
		String[] arr5 = { IServiceConstants.ENVIRONMENT_PROD, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+1000), String.valueOf(toDay.getTime()+2000),
				String.valueOf(toDay.getTime()+3000), String.valueOf(DateUtils.addDays(toDay, 3).getTime()), PROD_VALID_USER, PROD_VALID_PASS };
		String[] arr6 = { IServiceConstants.ENVIRONMENT_PROD, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+2000), String.valueOf(toDay.getTime()+4000),
				String.valueOf(toDay.getTime()+6000), String.valueOf(DateUtils.addDays(toDay, 15).getTime()), PROD_VALID_USER, PROD_VALID_PASS };
		
		String[] arr7 = { IServiceConstants.ENVIRONMENT_FOX7, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+10), String.valueOf(toDay.getTime()+20),
				String.valueOf(toDay.getTime()+30), String.valueOf(DateUtils.addDays(toDay, 1).getTime()), FOX7_VALID_USER, FOX7_VALID_PASS };
		String[] arr8 = { IServiceConstants.ENVIRONMENT_FOX7, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+100), String.valueOf(toDay.getTime()+200),
				String.valueOf(toDay.getTime()+300), String.valueOf(DateUtils.addDays(toDay, 10).getTime()), FOX7_VALID_USER, FOX7_VALID_PASS };
		String[] arr9 = { IServiceConstants.ENVIRONMENT_FOX7, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+200), String.valueOf(toDay.getTime()+400),
				String.valueOf(toDay.getTime()+600), String.valueOf(DateUtils.addDays(toDay, 4).getTime()), FOX7_VALID_USER, FOX7_VALID_PASS };
		String[] arr10 = { IServiceConstants.ENVIRONMENT_FOX7, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+50), String.valueOf(toDay.getTime()+70),
				String.valueOf(toDay.getTime()+90), String.valueOf(DateUtils.addDays(toDay, 2).getTime()), FOX7_VALID_USER, FOX7_VALID_PASS };
		String[] arr11 = { IServiceConstants.ENVIRONMENT_FOX7, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+1000), String.valueOf(toDay.getTime()+2000),
				String.valueOf(toDay.getTime()+3000), String.valueOf(DateUtils.addDays(toDay, 3).getTime()), FOX7_VALID_USER, FOX7_VALID_PASS };
		String[] arr12 = { IServiceConstants.ENVIRONMENT_FOX7, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+2000), String.valueOf(toDay.getTime()+4000),
				String.valueOf(toDay.getTime()+6000), String.valueOf(DateUtils.addDays(toDay, 15).getTime()), FOX7_VALID_USER, FOX7_VALID_PASS };
		
		String[] arr13 = { IServiceConstants.ENVIRONMENT_FUNC, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+10), String.valueOf(toDay.getTime()+20),
				String.valueOf(toDay.getTime()+30), String.valueOf(DateUtils.addDays(toDay, 1).getTime()), FUNC_VALID_USER, FUNC_VALID_PASS };
		String[] arr14 = { IServiceConstants.ENVIRONMENT_FUNC, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+100), String.valueOf(toDay.getTime()+200),
				String.valueOf(toDay.getTime()+300), String.valueOf(DateUtils.addDays(toDay, 10).getTime()), FUNC_VALID_USER, FUNC_VALID_PASS };
		String[] arr15 = { IServiceConstants.ENVIRONMENT_FUNC, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+200), String.valueOf(toDay.getTime()+400),
				String.valueOf(toDay.getTime()+600), String.valueOf(DateUtils.addDays(toDay, 4).getTime()), FUNC_VALID_USER, FUNC_VALID_PASS };
		String[] arr16 = { IServiceConstants.ENVIRONMENT_FUNC, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+50), String.valueOf(toDay.getTime()+70),
				String.valueOf(toDay.getTime()+90), String.valueOf(DateUtils.addDays(toDay, 2).getTime()), FUNC_VALID_USER, FUNC_VALID_PASS };
		String[] arr17 = { IServiceConstants.ENVIRONMENT_FUNC, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+1000), String.valueOf(toDay.getTime()+2000),
				String.valueOf(toDay.getTime()+3000), String.valueOf(DateUtils.addDays(toDay, 3).getTime()), FUNC_VALID_USER, FUNC_VALID_PASS };
		String[] arr18 = { IServiceConstants.ENVIRONMENT_FUNC, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+2000), String.valueOf(toDay.getTime()+4000),
				String.valueOf(toDay.getTime()+6000), String.valueOf(DateUtils.addDays(toDay, 15).getTime()), FUNC_VALID_USER, FUNC_VALID_PASS };
		
		String[] arr19 = { IServiceConstants.ENVIRONMENT_PERF, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+10), String.valueOf(toDay.getTime()+20),
				String.valueOf(toDay.getTime()+30), String.valueOf(DateUtils.addDays(toDay, 1).getTime()), PERF_VALID_USER, PERF_VALID_PASS };
		String[] arr20= { IServiceConstants.ENVIRONMENT_PERF, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+100), String.valueOf(toDay.getTime()+200),
				String.valueOf(toDay.getTime()+300), String.valueOf(DateUtils.addDays(toDay, 10).getTime()), PERF_VALID_USER, PERF_VALID_PASS };
		String[] arr21 = { IServiceConstants.ENVIRONMENT_PERF, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+200), String.valueOf(toDay.getTime()+400),
				String.valueOf(toDay.getTime()+600), String.valueOf(DateUtils.addDays(toDay, 4).getTime()), PERF_VALID_USER, PERF_VALID_PASS };
		String[] arr22 = { IServiceConstants.ENVIRONMENT_PERF, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+50), String.valueOf(toDay.getTime()+70),
				String.valueOf(toDay.getTime()+90), String.valueOf(DateUtils.addDays(toDay, 2).getTime()), PERF_VALID_USER, PERF_VALID_PASS };
		String[] arr23 = { IServiceConstants.ENVIRONMENT_PERF, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+1000), String.valueOf(toDay.getTime()+2000),
				String.valueOf(toDay.getTime()+3000), String.valueOf(DateUtils.addDays(toDay, 3).getTime()), PERF_VALID_USER, PERF_VALID_PASS };
		String[] arr24 = { IServiceConstants.ENVIRONMENT_PERF, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+2000), String.valueOf(toDay.getTime()+4000),
				String.valueOf(toDay.getTime()+6000), String.valueOf(DateUtils.addDays(toDay, 15).getTime()), PERF_VALID_USER, PERF_VALID_PASS };

		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9, arr10, arr11, arr12, arr13, arr14, arr15, arr16, arr17, 
				arr18, arr19, arr20, arr21, arr22, arr23, arr24 };
		return Toolbox.returnReducedDataSet(environment, dataSet, iTestContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider 
	public Object[][] SessionServiceDP_updateSessionDataProvider_validateSessionNodes(ITestContext iTestContext)
	{
		Date toDay = Calendar.getInstance().getTime();
		
		String[] arr1 = { IServiceConstants.ENVIRONMENT_PROD, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+10), String.valueOf(toDay.getTime()+20),
				String.valueOf(toDay.getTime()+30), String.valueOf(DateUtils.addDays(toDay, 1).getTime()), PROD_VALID_USER, PROD_VALID_PASS };
		String[] arr2 = { IServiceConstants.ENVIRONMENT_PROD, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+100), String.valueOf(toDay.getTime()+200),
				String.valueOf(toDay.getTime()+300), String.valueOf(DateUtils.addDays(toDay, 10).getTime()), PROD_VALID_USER, PROD_VALID_PASS };
		String[] arr3 = { IServiceConstants.ENVIRONMENT_PROD, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+200), String.valueOf(toDay.getTime()+400),
				String.valueOf(toDay.getTime()+600), String.valueOf(DateUtils.addDays(toDay, 4).getTime()), PROD_VALID_USER, PROD_VALID_PASS };
		String[] arr4 = { IServiceConstants.ENVIRONMENT_PROD, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+50), String.valueOf(toDay.getTime()+70),
				String.valueOf(toDay.getTime()+90), String.valueOf(DateUtils.addDays(toDay, 2).getTime()), PROD_VALID_USER, PROD_VALID_PASS };
		String[] arr5 = { IServiceConstants.ENVIRONMENT_PROD, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+1000), String.valueOf(toDay.getTime()+2000),
				String.valueOf(toDay.getTime()+3000), String.valueOf(DateUtils.addDays(toDay, 3).getTime()), PROD_VALID_USER, PROD_VALID_PASS };
		String[] arr6 = { IServiceConstants.ENVIRONMENT_PROD, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+2000), String.valueOf(toDay.getTime()+4000),
				String.valueOf(toDay.getTime()+6000), String.valueOf(DateUtils.addDays(toDay, 15).getTime()), PROD_VALID_USER, PROD_VALID_PASS };
		
		String[] arr7 = { IServiceConstants.ENVIRONMENT_FOX7, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+10), String.valueOf(toDay.getTime()+20),
				String.valueOf(toDay.getTime()+30), String.valueOf(DateUtils.addDays(toDay, 1).getTime()), FOX7_VALID_USER, FOX7_VALID_PASS };
		String[] arr8 = { IServiceConstants.ENVIRONMENT_FOX7, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+100), String.valueOf(toDay.getTime()+200),
				String.valueOf(toDay.getTime()+300), String.valueOf(DateUtils.addDays(toDay, 10).getTime()), FOX7_VALID_USER, FOX7_VALID_PASS };
		String[] arr9 = { IServiceConstants.ENVIRONMENT_FOX7, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+200), String.valueOf(toDay.getTime()+400),
				String.valueOf(toDay.getTime()+600), String.valueOf(DateUtils.addDays(toDay, 4).getTime()), FOX7_VALID_USER, FOX7_VALID_PASS };
		String[] arr10 = { IServiceConstants.ENVIRONMENT_FOX7, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+50), String.valueOf(toDay.getTime()+70),
				String.valueOf(toDay.getTime()+90), String.valueOf(DateUtils.addDays(toDay, 2).getTime()), FOX7_VALID_USER, FOX7_VALID_PASS };
		String[] arr11 = { IServiceConstants.ENVIRONMENT_FOX7, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+1000), String.valueOf(toDay.getTime()+2000),
				String.valueOf(toDay.getTime()+3000), String.valueOf(DateUtils.addDays(toDay, 3).getTime()), FOX7_VALID_USER, FOX7_VALID_PASS };
		String[] arr12 = { IServiceConstants.ENVIRONMENT_FOX7, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+2000), String.valueOf(toDay.getTime()+4000),
				String.valueOf(toDay.getTime()+6000), String.valueOf(DateUtils.addDays(toDay, 15).getTime()), FOX7_VALID_USER, FOX7_VALID_PASS };
		
		String[] arr13 = { IServiceConstants.ENVIRONMENT_FUNC, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+10), String.valueOf(toDay.getTime()+20),
				String.valueOf(toDay.getTime()+30), String.valueOf(DateUtils.addDays(toDay, 1).getTime()), FUNC_VALID_USER, FUNC_VALID_PASS };
		String[] arr14 = { IServiceConstants.ENVIRONMENT_FUNC, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+100), String.valueOf(toDay.getTime()+200),
				String.valueOf(toDay.getTime()+300), String.valueOf(DateUtils.addDays(toDay, 10).getTime()), FUNC_VALID_USER, FUNC_VALID_PASS };
		String[] arr15 = { IServiceConstants.ENVIRONMENT_FUNC, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+200), String.valueOf(toDay.getTime()+400),
				String.valueOf(toDay.getTime()+600), String.valueOf(DateUtils.addDays(toDay, 4).getTime()), FUNC_VALID_USER, FUNC_VALID_PASS };
		String[] arr16 = { IServiceConstants.ENVIRONMENT_FUNC, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+50), String.valueOf(toDay.getTime()+70),
				String.valueOf(toDay.getTime()+90), String.valueOf(DateUtils.addDays(toDay, 2).getTime()), FUNC_VALID_USER, FUNC_VALID_PASS };
		String[] arr17 = { IServiceConstants.ENVIRONMENT_FUNC, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+1000), String.valueOf(toDay.getTime()+2000),
				String.valueOf(toDay.getTime()+3000), String.valueOf(DateUtils.addDays(toDay, 3).getTime()), FUNC_VALID_USER, FUNC_VALID_PASS };
		String[] arr18 = { IServiceConstants.ENVIRONMENT_FUNC, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+2000), String.valueOf(toDay.getTime()+4000),
				String.valueOf(toDay.getTime()+6000), String.valueOf(DateUtils.addDays(toDay, 15).getTime()), FUNC_VALID_USER, FUNC_VALID_PASS };
		
		String[] arr19 = { IServiceConstants.ENVIRONMENT_PERF, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+10), String.valueOf(toDay.getTime()+20),
				String.valueOf(toDay.getTime()+30), String.valueOf(DateUtils.addDays(toDay, 1).getTime()), PERF_VALID_USER, PERF_VALID_PASS };
		String[] arr20= { IServiceConstants.ENVIRONMENT_PERF, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+100), String.valueOf(toDay.getTime()+200),
				String.valueOf(toDay.getTime()+300), String.valueOf(DateUtils.addDays(toDay, 10).getTime()), PERF_VALID_USER, PERF_VALID_PASS };
		String[] arr21 = { IServiceConstants.ENVIRONMENT_PERF, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+200), String.valueOf(toDay.getTime()+400),
				String.valueOf(toDay.getTime()+600), String.valueOf(DateUtils.addDays(toDay, 4).getTime()), PERF_VALID_USER, PERF_VALID_PASS };
		String[] arr22 = { IServiceConstants.ENVIRONMENT_PERF, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+50), String.valueOf(toDay.getTime()+70),
				String.valueOf(toDay.getTime()+90), String.valueOf(DateUtils.addDays(toDay, 2).getTime()), PERF_VALID_USER, PERF_VALID_PASS };
		String[] arr23 = { IServiceConstants.ENVIRONMENT_PERF, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+1000), String.valueOf(toDay.getTime()+2000),
				String.valueOf(toDay.getTime()+3000), String.valueOf(DateUtils.addDays(toDay, 3).getTime()), PERF_VALID_USER, PERF_VALID_PASS };
		String[] arr24 = { IServiceConstants.ENVIRONMENT_PERF, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+2000), String.valueOf(toDay.getTime()+4000),
				String.valueOf(toDay.getTime()+6000), String.valueOf(DateUtils.addDays(toDay, 15).getTime()), PERF_VALID_USER, PERF_VALID_PASS };

		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9, arr10, arr11, arr12, arr13, arr14, arr15, arr16, arr17, 
				arr18, arr19, arr20, arr21, arr22, arr23, arr24 };
		return Toolbox.returnReducedDataSet(environment, dataSet, iTestContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider 
	public Object[][] SessionServiceDP_updateSessionDataProvider_validateSessionCDATANodes(ITestContext iTestContext)
	{
		Date toDay = Calendar.getInstance().getTime();
		
		String[] arr1 = { IServiceConstants.ENVIRONMENT_PROD, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+10), String.valueOf(toDay.getTime()+20),
				String.valueOf(toDay.getTime()+30), String.valueOf(DateUtils.addDays(toDay, 1).getTime()), PROD_VALID_USER, PROD_VALID_PASS };
		String[] arr2 = { IServiceConstants.ENVIRONMENT_PROD, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+100), String.valueOf(toDay.getTime()+200),
				String.valueOf(toDay.getTime()+300), String.valueOf(DateUtils.addDays(toDay, 10).getTime()), PROD_VALID_USER, PROD_VALID_PASS };
		String[] arr3 = { IServiceConstants.ENVIRONMENT_PROD, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+200), String.valueOf(toDay.getTime()+400),
				String.valueOf(toDay.getTime()+600), String.valueOf(DateUtils.addDays(toDay, 4).getTime()), PROD_VALID_USER, PROD_VALID_PASS };
		String[] arr4 = { IServiceConstants.ENVIRONMENT_PROD, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+50), String.valueOf(toDay.getTime()+70),
				String.valueOf(toDay.getTime()+90), String.valueOf(DateUtils.addDays(toDay, 2).getTime()), PROD_VALID_USER, PROD_VALID_PASS };
		String[] arr5 = { IServiceConstants.ENVIRONMENT_PROD, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+1000), String.valueOf(toDay.getTime()+2000),
				String.valueOf(toDay.getTime()+3000), String.valueOf(DateUtils.addDays(toDay, 3).getTime()), PROD_VALID_USER, PROD_VALID_PASS };
		String[] arr6 = { IServiceConstants.ENVIRONMENT_PROD, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+2000), String.valueOf(toDay.getTime()+4000),
				String.valueOf(toDay.getTime()+6000), String.valueOf(DateUtils.addDays(toDay, 15).getTime()), PROD_VALID_USER, PROD_VALID_PASS };
		
		String[] arr7 = { IServiceConstants.ENVIRONMENT_FOX7, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+10), String.valueOf(toDay.getTime()+20),
				String.valueOf(toDay.getTime()+30), String.valueOf(DateUtils.addDays(toDay, 1).getTime()), FOX7_VALID_USER, FOX7_VALID_PASS };
		String[] arr8 = { IServiceConstants.ENVIRONMENT_FOX7, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+100), String.valueOf(toDay.getTime()+200),
				String.valueOf(toDay.getTime()+300), String.valueOf(DateUtils.addDays(toDay, 10).getTime()), FOX7_VALID_USER, FOX7_VALID_PASS };
		String[] arr9 = { IServiceConstants.ENVIRONMENT_FOX7, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+200), String.valueOf(toDay.getTime()+400),
				String.valueOf(toDay.getTime()+600), String.valueOf(DateUtils.addDays(toDay, 4).getTime()), FOX7_VALID_USER, FOX7_VALID_PASS };
		String[] arr10 = { IServiceConstants.ENVIRONMENT_FOX7, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+50), String.valueOf(toDay.getTime()+70),
				String.valueOf(toDay.getTime()+90), String.valueOf(DateUtils.addDays(toDay, 2).getTime()), FOX7_VALID_USER, FOX7_VALID_PASS };
		String[] arr11 = { IServiceConstants.ENVIRONMENT_FOX7, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+1000), String.valueOf(toDay.getTime()+2000),
				String.valueOf(toDay.getTime()+3000), String.valueOf(DateUtils.addDays(toDay, 3).getTime()), FOX7_VALID_USER, FOX7_VALID_PASS };
		String[] arr12 = { IServiceConstants.ENVIRONMENT_FOX7, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+2000), String.valueOf(toDay.getTime()+4000),
				String.valueOf(toDay.getTime()+6000), String.valueOf(DateUtils.addDays(toDay, 15).getTime()), FOX7_VALID_USER, FOX7_VALID_PASS };
		
		String[] arr13 = { IServiceConstants.ENVIRONMENT_FUNC, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+10), String.valueOf(toDay.getTime()+20),
				String.valueOf(toDay.getTime()+30), String.valueOf(DateUtils.addDays(toDay, 1).getTime()), FUNC_VALID_USER, FUNC_VALID_PASS };
		String[] arr14 = { IServiceConstants.ENVIRONMENT_FUNC, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+100), String.valueOf(toDay.getTime()+200),
				String.valueOf(toDay.getTime()+300), String.valueOf(DateUtils.addDays(toDay, 10).getTime()), FUNC_VALID_USER, FUNC_VALID_PASS };
		String[] arr15 = { IServiceConstants.ENVIRONMENT_FUNC, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+200), String.valueOf(toDay.getTime()+400),
				String.valueOf(toDay.getTime()+600), String.valueOf(DateUtils.addDays(toDay, 4).getTime()), FUNC_VALID_USER, FUNC_VALID_PASS };
		String[] arr16 = { IServiceConstants.ENVIRONMENT_FUNC, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+50), String.valueOf(toDay.getTime()+70),
				String.valueOf(toDay.getTime()+90), String.valueOf(DateUtils.addDays(toDay, 2).getTime()), FUNC_VALID_USER, FUNC_VALID_PASS };
		String[] arr17 = { IServiceConstants.ENVIRONMENT_FUNC, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+1000), String.valueOf(toDay.getTime()+2000),
				String.valueOf(toDay.getTime()+3000), String.valueOf(DateUtils.addDays(toDay, 3).getTime()), FUNC_VALID_USER, FUNC_VALID_PASS };
		String[] arr18 = { IServiceConstants.ENVIRONMENT_FUNC, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+2000), String.valueOf(toDay.getTime()+4000),
				String.valueOf(toDay.getTime()+6000), String.valueOf(DateUtils.addDays(toDay, 15).getTime()), FUNC_VALID_USER, FUNC_VALID_PASS };
		
		String[] arr19 = { IServiceConstants.ENVIRONMENT_PERF, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+10), String.valueOf(toDay.getTime()+20),
				String.valueOf(toDay.getTime()+30), String.valueOf(DateUtils.addDays(toDay, 1).getTime()), PERF_VALID_USER, PERF_VALID_PASS };
		String[] arr20= { IServiceConstants.ENVIRONMENT_PERF, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+100), String.valueOf(toDay.getTime()+200),
				String.valueOf(toDay.getTime()+300), String.valueOf(DateUtils.addDays(toDay, 10).getTime()), PERF_VALID_USER, PERF_VALID_PASS };
		String[] arr21 = { IServiceConstants.ENVIRONMENT_PERF, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+200), String.valueOf(toDay.getTime()+400),
				String.valueOf(toDay.getTime()+600), String.valueOf(DateUtils.addDays(toDay, 4).getTime()), PERF_VALID_USER, PERF_VALID_PASS };
		String[] arr22 = { IServiceConstants.ENVIRONMENT_PERF, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+50), String.valueOf(toDay.getTime()+70),
				String.valueOf(toDay.getTime()+90), String.valueOf(DateUtils.addDays(toDay, 2).getTime()), PERF_VALID_USER, PERF_VALID_PASS };
		String[] arr23 = { IServiceConstants.ENVIRONMENT_PERF, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+1000), String.valueOf(toDay.getTime()+2000),
				String.valueOf(toDay.getTime()+3000), String.valueOf(DateUtils.addDays(toDay, 3).getTime()), PERF_VALID_USER, PERF_VALID_PASS };
		String[] arr24 = { IServiceConstants.ENVIRONMENT_PERF, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+2000), String.valueOf(toDay.getTime()+4000),
				String.valueOf(toDay.getTime()+6000), String.valueOf(DateUtils.addDays(toDay, 15).getTime()), PERF_VALID_USER, PERF_VALID_PASS };

		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9, arr10, arr11, arr12, arr13, arr14, arr15, arr16, arr17, 
				arr18, arr19, arr20, arr21, arr22, arr23, arr24 };
		return Toolbox.returnReducedDataSet(environment, dataSet, iTestContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider 
	public Object[][] SessionServiceDP_updateSessionDataProvider_validateSessionCreationDate(ITestContext iTestContext)	
	{
		Date toDay = Calendar.getInstance().getTime();
		
		String[] arr1 = { IServiceConstants.ENVIRONMENT_PROD, String.valueOf(DateUtils.addDays(toDay, -2).getTime()), String.valueOf(DateUtils.addDays(toDay, -2).getTime()),
				String.valueOf(DateUtils.addDays(toDay, -2).getTime()), String.valueOf(DateUtils.addDays(toDay, -2).getTime()), 
				String.valueOf(DateUtils.addDays(toDay, +2).getTime()), PROD_VALID_USER, PROD_VALID_PASS };
		String[] arr2 = { IServiceConstants.ENVIRONMENT_PROD, String.valueOf(DateUtils.addDays(toDay, -5).getTime()), String.valueOf(DateUtils.addDays(toDay, -4).getTime()),
				String.valueOf(DateUtils.addDays(toDay, -4).getTime()), String.valueOf(DateUtils.addDays(toDay, -4).getTime()), 
				String.valueOf(DateUtils.addDays(toDay, +6).getTime()), PROD_VALID_USER, PROD_VALID_PASS };
		String[] arr3 = { IServiceConstants.ENVIRONMENT_PROD, String.valueOf(DateUtils.addDays(toDay, -15).getTime()), String.valueOf(DateUtils.addDays(toDay, -14).getTime()),
				String.valueOf(DateUtils.addDays(toDay, -14).getTime()), String.valueOf(DateUtils.addDays(toDay, -14).getTime()), 
				String.valueOf(DateUtils.addDays(toDay, +10).getTime()), PROD_VALID_USER, PROD_VALID_PASS };
		
		String[] arr4 = { IServiceConstants.ENVIRONMENT_FOX7, String.valueOf(DateUtils.addDays(toDay, -2).getTime()), String.valueOf(DateUtils.addDays(toDay, -2).getTime()),
				String.valueOf(DateUtils.addDays(toDay, -2).getTime()), String.valueOf(DateUtils.addDays(toDay, -2).getTime()), 
				String.valueOf(DateUtils.addDays(toDay, +2).getTime()), FOX7_VALID_USER, FOX7_VALID_PASS };
		String[] arr5 = { IServiceConstants.ENVIRONMENT_FOX7, String.valueOf(DateUtils.addDays(toDay, -5).getTime()), String.valueOf(DateUtils.addDays(toDay, -4).getTime()),
				String.valueOf(DateUtils.addDays(toDay, -4).getTime()), String.valueOf(DateUtils.addDays(toDay, -4).getTime()), 
				String.valueOf(DateUtils.addDays(toDay, +6).getTime()), FOX7_VALID_USER, FOX7_VALID_PASS };
		String[] arr6 = { IServiceConstants.ENVIRONMENT_FOX7, String.valueOf(DateUtils.addDays(toDay, -15).getTime()), String.valueOf(DateUtils.addDays(toDay, -14).getTime()),
				String.valueOf(DateUtils.addDays(toDay, -14).getTime()), String.valueOf(DateUtils.addDays(toDay, -14).getTime()), 
				String.valueOf(DateUtils.addDays(toDay, +10).getTime()), FOX7_VALID_USER, FOX7_VALID_PASS };
		
		String[] arr7 = { IServiceConstants.ENVIRONMENT_FUNC, String.valueOf(DateUtils.addDays(toDay, -2).getTime()), String.valueOf(DateUtils.addDays(toDay, -2).getTime()),
				String.valueOf(DateUtils.addDays(toDay, -2).getTime()), String.valueOf(DateUtils.addDays(toDay, -2).getTime()), 
				String.valueOf(DateUtils.addDays(toDay, +2).getTime()), FUNC_VALID_USER, FUNC_VALID_PASS };
		String[] arr8 = { IServiceConstants.ENVIRONMENT_FUNC, String.valueOf(DateUtils.addDays(toDay, -5).getTime()), String.valueOf(DateUtils.addDays(toDay, -4).getTime()),
				String.valueOf(DateUtils.addDays(toDay, -4).getTime()), String.valueOf(DateUtils.addDays(toDay, -4).getTime()), 
				String.valueOf(DateUtils.addDays(toDay, +6).getTime()), FUNC_VALID_USER, FUNC_VALID_PASS };
		String[] arr9 = { IServiceConstants.ENVIRONMENT_FUNC, String.valueOf(DateUtils.addDays(toDay, -15).getTime()), String.valueOf(DateUtils.addDays(toDay, -14).getTime()),
				String.valueOf(DateUtils.addDays(toDay, -14).getTime()), String.valueOf(DateUtils.addDays(toDay, -14).getTime()), 
				String.valueOf(DateUtils.addDays(toDay, +10).getTime()), FUNC_VALID_USER, FUNC_VALID_PASS };
		
		String[] arr10 = { IServiceConstants.ENVIRONMENT_PERF, String.valueOf(DateUtils.addDays(toDay, -2).getTime()), String.valueOf(DateUtils.addDays(toDay, -2).getTime()),
				String.valueOf(DateUtils.addDays(toDay, -2).getTime()), String.valueOf(DateUtils.addDays(toDay, -2).getTime()), 
				String.valueOf(DateUtils.addDays(toDay, +2).getTime()), PERF_VALID_USER, PERF_VALID_PASS };
		String[] arr11 = { IServiceConstants.ENVIRONMENT_PERF, String.valueOf(DateUtils.addDays(toDay, -5).getTime()), String.valueOf(DateUtils.addDays(toDay, -4).getTime()),
				String.valueOf(DateUtils.addDays(toDay, -4).getTime()), String.valueOf(DateUtils.addDays(toDay, -4).getTime()), 
				String.valueOf(DateUtils.addDays(toDay, +6).getTime()), PERF_VALID_USER, PERF_VALID_PASS };
		String[] arr12 = { IServiceConstants.ENVIRONMENT_PERF, String.valueOf(DateUtils.addDays(toDay, -15).getTime()), String.valueOf(DateUtils.addDays(toDay, -14).getTime()),
				String.valueOf(DateUtils.addDays(toDay, -14).getTime()), String.valueOf(DateUtils.addDays(toDay, -14).getTime()), 
				String.valueOf(DateUtils.addDays(toDay, +10).getTime()), PERF_VALID_USER, PERF_VALID_PASS };
		

		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9, arr10, arr11, arr12 };
		return Toolbox.returnReducedDataSet(environment, dataSet, iTestContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public Object[][] SessionServiceDP_updateSessionDataProvider_validateLastAccessedDate(ITestContext iTestContext)
	{
		Date toDay = Calendar.getInstance().getTime();
		
		String[] arr1 = { IServiceConstants.ENVIRONMENT_PROD, String.valueOf(DateUtils.addDays(toDay, -2).getTime()), String.valueOf(DateUtils.addDays(toDay, -1).getTime()),
				String.valueOf(DateUtils.addDays(toDay, -1).getTime()), String.valueOf(DateUtils.addDays(toDay, -1).getTime()), 
				String.valueOf(DateUtils.addDays(toDay, +2).getTime()), PROD_VALID_USER, PROD_VALID_PASS };
		String[] arr2 = { IServiceConstants.ENVIRONMENT_PROD, String.valueOf(DateUtils.addDays(toDay, -5).getTime()), String.valueOf(DateUtils.addDays(toDay, -4).getTime()),
				 String.valueOf(DateUtils.addDays(toDay, -4).getTime()), String.valueOf(DateUtils.addDays(toDay, -4).getTime()),
				 String.valueOf(DateUtils.addDays(toDay, +6).getTime()),  PROD_VALID_USER, PROD_VALID_PASS };
		String[] arr3 = { IServiceConstants.ENVIRONMENT_PROD, String.valueOf(DateUtils.addDays(toDay, -15).getTime()), String.valueOf(DateUtils.addDays(toDay, -14).getTime()),
				String.valueOf(DateUtils.addDays(toDay, -14).getTime()), String.valueOf(DateUtils.addDays(toDay, -14).getTime()), 
				String.valueOf(DateUtils.addDays(toDay, +10).getTime()), PROD_VALID_USER, PROD_VALID_PASS };
		
		String[] arr4 = { IServiceConstants.ENVIRONMENT_FOX7, String.valueOf(DateUtils.addDays(toDay, -2).getTime()), String.valueOf(DateUtils.addDays(toDay, -1).getTime()),
				String.valueOf(DateUtils.addDays(toDay, -1).getTime()), String.valueOf(DateUtils.addDays(toDay, -1).getTime()), 
				String.valueOf(DateUtils.addDays(toDay, +2).getTime()), FOX7_VALID_USER, FOX7_VALID_PASS };
		String[] arr5 = { IServiceConstants.ENVIRONMENT_FOX7, String.valueOf(DateUtils.addDays(toDay, -5).getTime()), String.valueOf(DateUtils.addDays(toDay, -4).getTime()),
				 String.valueOf(DateUtils.addDays(toDay, -4).getTime()), String.valueOf(DateUtils.addDays(toDay, -4).getTime()),
				 String.valueOf(DateUtils.addDays(toDay, +6).getTime()),  FOX7_VALID_USER, FOX7_VALID_PASS };
		String[] arr6 = { IServiceConstants.ENVIRONMENT_FOX7, String.valueOf(DateUtils.addDays(toDay, -15).getTime()), String.valueOf(DateUtils.addDays(toDay, -14).getTime()),
				String.valueOf(DateUtils.addDays(toDay, -14).getTime()), String.valueOf(DateUtils.addDays(toDay, -14).getTime()), 
				String.valueOf(DateUtils.addDays(toDay, +10).getTime()), FOX7_VALID_USER, FOX7_VALID_PASS };
		
		String[] arr7 = { IServiceConstants.ENVIRONMENT_FUNC, String.valueOf(DateUtils.addDays(toDay, -2).getTime()), String.valueOf(DateUtils.addDays(toDay, -1).getTime()),
				String.valueOf(DateUtils.addDays(toDay, -1).getTime()), String.valueOf(DateUtils.addDays(toDay, -1).getTime()), 
				String.valueOf(DateUtils.addDays(toDay, +2).getTime()), FUNC_VALID_USER, FUNC_VALID_PASS };
		String[] arr8 = { IServiceConstants.ENVIRONMENT_FUNC, String.valueOf(DateUtils.addDays(toDay, -5).getTime()), String.valueOf(DateUtils.addDays(toDay, -4).getTime()),
				 String.valueOf(DateUtils.addDays(toDay, -4).getTime()), String.valueOf(DateUtils.addDays(toDay, -4).getTime()),
				 String.valueOf(DateUtils.addDays(toDay, +6).getTime()),  FUNC_VALID_USER, FUNC_VALID_PASS };
		String[] arr9 = { IServiceConstants.ENVIRONMENT_FUNC, String.valueOf(DateUtils.addDays(toDay, -15).getTime()), String.valueOf(DateUtils.addDays(toDay, -14).getTime()),
				String.valueOf(DateUtils.addDays(toDay, -14).getTime()), String.valueOf(DateUtils.addDays(toDay, -14).getTime()), 
				String.valueOf(DateUtils.addDays(toDay, +10).getTime()), FUNC_VALID_USER, FUNC_VALID_PASS };
		
		String[] arr10 = { IServiceConstants.ENVIRONMENT_PERF, String.valueOf(DateUtils.addDays(toDay, -2).getTime()), String.valueOf(DateUtils.addDays(toDay, -1).getTime()),
				String.valueOf(DateUtils.addDays(toDay, -1).getTime()), String.valueOf(DateUtils.addDays(toDay, -1).getTime()), 
				String.valueOf(DateUtils.addDays(toDay, +2).getTime()), PERF_VALID_USER, PERF_VALID_PASS };
		String[] arr11 = { IServiceConstants.ENVIRONMENT_PERF, String.valueOf(DateUtils.addDays(toDay, -5).getTime()), String.valueOf(DateUtils.addDays(toDay, -4).getTime()),
				 String.valueOf(DateUtils.addDays(toDay, -4).getTime()), String.valueOf(DateUtils.addDays(toDay, -4).getTime()),
				 String.valueOf(DateUtils.addDays(toDay, +6).getTime()),  PERF_VALID_USER, PERF_VALID_PASS };
		String[] arr12 = { IServiceConstants.ENVIRONMENT_PERF, String.valueOf(DateUtils.addDays(toDay, -15).getTime()), String.valueOf(DateUtils.addDays(toDay, -14).getTime()),
				String.valueOf(DateUtils.addDays(toDay, -14).getTime()), String.valueOf(DateUtils.addDays(toDay, -14).getTime()), 
				String.valueOf(DateUtils.addDays(toDay, +10).getTime()), PERF_VALID_USER, PERF_VALID_PASS };

		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9, arr10, arr11, arr12 };
		return Toolbox.returnReducedDataSet(environment, dataSet, iTestContext.getIncludedGroups(), 1, 2);

	}

	@DataProvider
	public Object[][] SessionServiceDP_updateSessionDataProvider_validateLastModifiedDate(ITestContext iTestContext)
	{
		Date toDay = Calendar.getInstance().getTime();
		
		String[] arr1 = { IServiceConstants.ENVIRONMENT_PROD, String.valueOf(DateUtils.addDays(toDay, -2).getTime()), String.valueOf(DateUtils.addDays(toDay, -1).getTime()),
				String.valueOf(DateUtils.addDays(toDay, -2).getTime()), String.valueOf(DateUtils.addDays(toDay, -2).getTime()), 
				String.valueOf(DateUtils.addDays(toDay, +2).getTime()), PROD_VALID_USER, PROD_VALID_PASS };
		String[] arr2 = { IServiceConstants.ENVIRONMENT_PROD, String.valueOf(DateUtils.addDays(toDay, -5).getTime()), String.valueOf(DateUtils.addDays(toDay, -6).getTime()),
				String.valueOf(DateUtils.addDays(toDay, -6).getTime()), String.valueOf(DateUtils.addDays(toDay, -7).getTime()), 
				String.valueOf(DateUtils.addDays(toDay, +5).getTime()), PROD_VALID_USER, PROD_VALID_PASS };
		String[] arr3 = { IServiceConstants.ENVIRONMENT_PROD, String.valueOf(DateUtils.addDays(toDay, -10).getTime()), String.valueOf(DateUtils.addDays(toDay, -8).getTime()),
				String.valueOf(DateUtils.addDays(toDay, -8).getTime()), String.valueOf(DateUtils.addDays(toDay, -9).getTime()), 
				String.valueOf(DateUtils.addDays(toDay, +2).getTime()), PROD_VALID_USER, PROD_VALID_PASS };
		
		String[] arr4 = { IServiceConstants.ENVIRONMENT_FOX7, String.valueOf(DateUtils.addDays(toDay, -2).getTime()), String.valueOf(DateUtils.addDays(toDay, -1).getTime()),
				String.valueOf(DateUtils.addDays(toDay, -2).getTime()), String.valueOf(DateUtils.addDays(toDay, -2).getTime()), 
				String.valueOf(DateUtils.addDays(toDay, +2).getTime()), FOX7_VALID_USER, FOX7_VALID_PASS };
		String[] arr5 = { IServiceConstants.ENVIRONMENT_FOX7, String.valueOf(DateUtils.addDays(toDay, -5).getTime()), String.valueOf(DateUtils.addDays(toDay, -6).getTime()),
				String.valueOf(DateUtils.addDays(toDay, -6).getTime()), String.valueOf(DateUtils.addDays(toDay, -7).getTime()), 
				String.valueOf(DateUtils.addDays(toDay, +5).getTime()), FOX7_VALID_USER, FOX7_VALID_PASS };
		String[] arr6 = { IServiceConstants.ENVIRONMENT_FOX7, String.valueOf(DateUtils.addDays(toDay, -10).getTime()), String.valueOf(DateUtils.addDays(toDay, -8).getTime()),
				String.valueOf(DateUtils.addDays(toDay, -8).getTime()), String.valueOf(DateUtils.addDays(toDay, -9).getTime()), 
				String.valueOf(DateUtils.addDays(toDay, +2).getTime()), FOX7_VALID_USER, FOX7_VALID_PASS };
		
		String[] arr7 = { IServiceConstants.ENVIRONMENT_FUNC, String.valueOf(DateUtils.addDays(toDay, -2).getTime()), String.valueOf(DateUtils.addDays(toDay, -1).getTime()),
				String.valueOf(DateUtils.addDays(toDay, -2).getTime()), String.valueOf(DateUtils.addDays(toDay, -2).getTime()), 
				String.valueOf(DateUtils.addDays(toDay, +2).getTime()), FUNC_VALID_USER, FUNC_VALID_PASS };
		String[] arr8 = { IServiceConstants.ENVIRONMENT_FUNC, String.valueOf(DateUtils.addDays(toDay, -5).getTime()), String.valueOf(DateUtils.addDays(toDay, -6).getTime()),
				String.valueOf(DateUtils.addDays(toDay, -6).getTime()), String.valueOf(DateUtils.addDays(toDay, -7).getTime()), 
				String.valueOf(DateUtils.addDays(toDay, +5).getTime()), FUNC_VALID_USER, FUNC_VALID_PASS };
		String[] arr9 = { IServiceConstants.ENVIRONMENT_FUNC, String.valueOf(DateUtils.addDays(toDay, -10).getTime()), String.valueOf(DateUtils.addDays(toDay, -8).getTime()),
				String.valueOf(DateUtils.addDays(toDay, -8).getTime()), String.valueOf(DateUtils.addDays(toDay, -9).getTime()), 
				String.valueOf(DateUtils.addDays(toDay, +2).getTime()), FUNC_VALID_USER, FUNC_VALID_PASS };
		
		String[] arr10 = { IServiceConstants.ENVIRONMENT_PERF, String.valueOf(DateUtils.addDays(toDay, -2).getTime()), String.valueOf(DateUtils.addDays(toDay, -1).getTime()),
				String.valueOf(DateUtils.addDays(toDay, -2).getTime()), String.valueOf(DateUtils.addDays(toDay, -2).getTime()), 
				String.valueOf(DateUtils.addDays(toDay, +2).getTime()), PERF_VALID_USER, PERF_VALID_PASS };
		String[] arr11 = { IServiceConstants.ENVIRONMENT_PERF, String.valueOf(DateUtils.addDays(toDay, -5).getTime()), String.valueOf(DateUtils.addDays(toDay, -6).getTime()),
				String.valueOf(DateUtils.addDays(toDay, -6).getTime()), String.valueOf(DateUtils.addDays(toDay, -7).getTime()), 
				String.valueOf(DateUtils.addDays(toDay, +5).getTime()), PERF_VALID_USER, PERF_VALID_PASS };
		String[] arr12 = { IServiceConstants.ENVIRONMENT_PERF, String.valueOf(DateUtils.addDays(toDay, -10).getTime()), String.valueOf(DateUtils.addDays(toDay, -8).getTime()),
				String.valueOf(DateUtils.addDays(toDay, -8).getTime()), String.valueOf(DateUtils.addDays(toDay, -9).getTime()), 
				String.valueOf(DateUtils.addDays(toDay, +2).getTime()), PERF_VALID_USER, PERF_VALID_PASS };
		
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9, arr10, arr11, arr12 };
		return Toolbox.returnReducedDataSet(environment, dataSet, iTestContext.getIncludedGroups(), 1, 2);
	}

	
	/**
	 * Creation Date, Last Accessed Date, Last Modified Date - Same Date, Different Time, PM.
	 * Expiry Date - past Date, AM
	 * @param iTestContext
	 * @return
	 */
	@DataProvider 
	public Object[][] SessionServiceDP_updateSessionDataProvider_validateSessionExpiryDate(ITestContext iTestContext) 
	{
		Date toDay = Calendar.getInstance().getTime();

		String[] arr1 = { IServiceConstants.ENVIRONMENT_PROD, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+100), String.valueOf(toDay.getTime()+100),
				String.valueOf(toDay.getTime()+300), String.valueOf(DateUtils.addDays(toDay, -6).getTime()), PROD_VALID_USER, PROD_VALID_PASS };
		String[] arr2 = { IServiceConstants.ENVIRONMENT_PROD, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+200), String.valueOf(toDay.getTime()+200),
				String.valueOf(toDay.getTime()+500), String.valueOf(DateUtils.addDays(toDay, -3).getTime()), PROD_VALID_USER, PROD_VALID_PASS };
		String[] arr3 = { IServiceConstants.ENVIRONMENT_PROD, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+1000), String.valueOf(toDay.getTime()+1000),
				String.valueOf(toDay.getTime()+3000), String.valueOf(DateUtils.addDays(toDay, -5).getTime()), PROD_VALID_USER, PROD_VALID_PASS };
		String[] arr4 = { IServiceConstants.ENVIRONMENT_PROD, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+50), String.valueOf(toDay.getTime()+50),
				String.valueOf(toDay.getTime()+100), String.valueOf(DateUtils.addDays(toDay, -10).getTime()), PROD_VALID_USER, PROD_VALID_PASS };

		String[] arr5 = { IServiceConstants.ENVIRONMENT_FOX7, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+100), String.valueOf(toDay.getTime()+100),
				String.valueOf(toDay.getTime()+300), String.valueOf(DateUtils.addDays(toDay, -6).getTime()), FOX7_VALID_USER, FOX7_VALID_PASS };
		String[] arr6 = { IServiceConstants.ENVIRONMENT_FOX7, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+200), String.valueOf(toDay.getTime()+200),
				String.valueOf(toDay.getTime()+500), String.valueOf(DateUtils.addDays(toDay, -3).getTime()), FOX7_VALID_USER, FOX7_VALID_PASS };
		String[] arr7 = { IServiceConstants.ENVIRONMENT_FOX7, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+1000), String.valueOf(toDay.getTime()+1000),
				String.valueOf(toDay.getTime()+3000), String.valueOf(DateUtils.addDays(toDay, -5).getTime()), FOX7_VALID_USER, FOX7_VALID_PASS };
		String[] arr8 = { IServiceConstants.ENVIRONMENT_FOX7, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+50), String.valueOf(toDay.getTime()+50),
				String.valueOf(toDay.getTime()+100), String.valueOf(DateUtils.addDays(toDay, -10).getTime()), FOX7_VALID_USER, FOX7_VALID_PASS };

		String[] arr9 = { IServiceConstants.ENVIRONMENT_FUNC, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+100), String.valueOf(toDay.getTime()+100),
				String.valueOf(toDay.getTime()+300), String.valueOf(DateUtils.addDays(toDay, -6).getTime()), FUNC_VALID_USER, FUNC_VALID_PASS };
		String[] arr10 = { IServiceConstants.ENVIRONMENT_FUNC, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+200), String.valueOf(toDay.getTime()+200),
				String.valueOf(toDay.getTime()+500), String.valueOf(DateUtils.addDays(toDay, -3).getTime()), FUNC_VALID_USER, FUNC_VALID_PASS };
		String[] arr11 = { IServiceConstants.ENVIRONMENT_FUNC, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+1000), String.valueOf(toDay.getTime()+1000),
				String.valueOf(toDay.getTime()+3000), String.valueOf(DateUtils.addDays(toDay, -5).getTime()), FUNC_VALID_USER, FUNC_VALID_PASS };
		String[] arr12 = { IServiceConstants.ENVIRONMENT_FUNC, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+50), String.valueOf(toDay.getTime()+50),
				String.valueOf(toDay.getTime()+100), String.valueOf(DateUtils.addDays(toDay, -10).getTime()), FUNC_VALID_USER, FUNC_VALID_PASS };

		String[] arr13 = { IServiceConstants.ENVIRONMENT_PERF, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+100), String.valueOf(toDay.getTime()+100),
				String.valueOf(toDay.getTime()+300), String.valueOf(DateUtils.addDays(toDay, -6).getTime()), PERF_VALID_USER, PERF_VALID_PASS };
		String[] arr14 = { IServiceConstants.ENVIRONMENT_PERF, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+200), String.valueOf(toDay.getTime()+200),
				String.valueOf(toDay.getTime()+500), String.valueOf(DateUtils.addDays(toDay, -3).getTime()), PERF_VALID_USER, PERF_VALID_PASS };
		String[] arr15 = { IServiceConstants.ENVIRONMENT_PERF, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+1000), String.valueOf(toDay.getTime()+1000),
				String.valueOf(toDay.getTime()+3000), String.valueOf(DateUtils.addDays(toDay, -5).getTime()), PERF_VALID_USER, PERF_VALID_PASS };
		String[] arr16 = { IServiceConstants.ENVIRONMENT_PERF, String.valueOf(toDay.getTime()), String.valueOf(toDay.getTime()+50), String.valueOf(toDay.getTime()+50),
				String.valueOf(toDay.getTime()+100), String.valueOf(DateUtils.addDays(toDay, -10).getTime()), PERF_VALID_USER, PERF_VALID_PASS };

		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr4, arr5, arr6, arr7, arr8, arr9, arr10, arr11, arr12, arr13, arr14, arr15, arr16 };
		return Toolbox.returnReducedDataSet(environment, dataSet, iTestContext.getIncludedGroups(), 4, 3);
	}

	@DataProvider // Creation Date,Last Accessed Date, Last Modified Date, Expiry Date
	public Object[][] updateSessionCDATADataProvider(ITestContext iTestContext)
	{
		List<String> list = modifiedDates(1);
		
		String[] arr1 = { list.get(0), list.get(1), list.get(2), list.get(3), "mohittest101@myntra.com" };
		String[] arr2 = { list.get(0), list.get(1), list.get(2), list.get(3), "mtra123@gmail.com" };
		
		List<String> list1 = modifiedDates(10);
		
		String[] arr3 = { list1.get(0), list1.get(1), list1.get(2), list1.get(3),"sessionservice_new@gmail.com","123456" };
		String[] arr4 = { list1.get(0), list1.get(1), list1.get(2), list1.get(3).toString(),"sessionservice_ext@gmail.com" };
		
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4 };
		return Toolbox.returnReducedDataSet(dataSet, iTestContext.getIncludedGroups(), 2, 3);
	}
	
	public static List<String> modifiedDates(int value) 
	{
		
		Date date = new Date ();
		String creationDate = Long.toString(date.getTime()/1000);
		String lastAccessedDate = Long.toString(date.getTime()/1000 + (value+2));
		String lastModifiedDate = Long.toString(date.getTime()/1000 + (value+4));
		String expiryDate = Long.toString(date.getTime()/1000 + (value+10));
		
		System.out.println("Creation Date:" +creationDate );
		System.out.println("lastAccessedDate:" +lastAccessedDate );
		System.out.println("lastModifiedDate:" +lastModifiedDate );
		System.out.println("expiryDate:" +expiryDate );
		
		List<String> listofUnixDates = new ArrayList<String>();
		listofUnixDates.add(creationDate);
		listofUnixDates.add(lastAccessedDate);
		listofUnixDates.add(lastModifiedDate);
		listofUnixDates.add(expiryDate);
		
		return listofUnixDates;		
	}
}
