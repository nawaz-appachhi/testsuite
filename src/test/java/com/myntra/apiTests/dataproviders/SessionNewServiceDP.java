package com.myntra.apiTests.dataproviders;

import com.myntra.apiTests.portalservices.commons.CommonUtils;
import com.myntra.lordoftherings.Configuration;
import com.myntra.lordoftherings.Toolbox;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import java.util.Calendar;

/**
 * @author shankara.c
 *
 */
public class SessionNewServiceDP extends CommonUtils
{
	String Env;
	Configuration con = new Configuration("./Data/configuration");

	public SessionNewServiceDP() {
		if (System.getenv("ENVIRONMENT") == null)
			Env = con.GetTestEnvironemnt().toString();
		else
			Env = System.getenv("ENVIRONMENT");
	}

	@DataProvider
	public Object [][] createNewSessionDataProvider(ITestContext testContext)
	{
		String [] arr1 = {"Fox7","mohittest100@myntra.com","mohit","jain","1111111111"};
		String [] arr2 = {"Fox7","","","",""};
		String [] arr3 = {"Production","mohittest100@myntra.com","mohit","jain","1111111111"};
		
		Object[][] dataSet = new Object[][] { arr1,arr2,arr3};
		return Toolbox.returnReducedDataSet(Env, dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object [][] SessionNewServiceDP_changeUserSessionInfo_verifyResponse(ITestContext testContext)
	{
		String lastAccessedTime = String.valueOf(Calendar.getInstance().getTimeInMillis());
		Calendar startTimeCal = Calendar.getInstance();
		startTimeCal.add(Calendar.HOUR, 2);
		String lastModifiedTime = String.valueOf(startTimeCal.getTimeInMillis());
		Calendar endTimeCal = Calendar.getInstance();
		endTimeCal.add(Calendar.HOUR, 4);
		String endTime = String.valueOf(endTimeCal.getTimeInMillis());
		
		String [] arr1 = { "Fox7", "mohittest100@myntra.com", "mohit", "jain", "1111111111", endTime, lastAccessedTime, lastModifiedTime};
		String [] arr2 = { "Fox7", "", "", "", "", endTime, lastAccessedTime, lastModifiedTime };
		String [] arr3 = { "Production", "mohittest100@myntra.com", "mohit", "jain", "1111111111", endTime, lastAccessedTime, lastModifiedTime};
		
		Object[][] dataSet = new Object[][] { arr1, arr2,arr3 };
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object [][] SessionNewServiceDP_changeUserSessionInfo_verifyFailureResponse(ITestContext testContext)
	{
		String lastAccessedTime = String.valueOf(Calendar.getInstance().getTimeInMillis());
		Calendar startTimeCal = Calendar.getInstance();
		startTimeCal.add(Calendar.HOUR, 2);
		String lastModifiedTime = String.valueOf(startTimeCal.getTimeInMillis());
		Calendar endTimeCal = Calendar.getInstance();
		endTimeCal.add(Calendar.HOUR, 4);
		String endTime = String.valueOf(endTimeCal.getTimeInMillis());
		
		String [] arr1 = { "Fox7", "mohittest100@myntra.com", "mohit", "jain", "1111111111", "JJNZ028Z87fZ314Z594Z829Z7beZ95dZff1Zb21Z220Z364ZM", endTime, 
				lastAccessedTime, lastModifiedTime};
		String [] arr2 = { "Fox7", "", "", "", "", "JJNZ028Z87fZ314Z594Z829Z7beZ95dZff1Zb21Z220Z364ZM", endTime, lastAccessedTime, lastModifiedTime};
		
		String currentTime = String.valueOf(Calendar.getInstance().getTimeInMillis());
		
		String [] arr3 = { "Fox7", "mohittest100@myntra.com", "mohit", "jain", "1111111111", "JJNZ028Z87fZ314Z594Z829Z7beZ95dZff1Zb21Z220Z364ZM", currentTime,  
				currentTime, currentTime };
		String [] arr4 = { "Fox7", "", "", "", "", "JJNZ028Z87fZ314Z594Z829Z7beZ95dZff1Zb21Z220Z364ZM", currentTime, currentTime, currentTime };
		
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4 };
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 2, 3);
	}
	
	@DataProvider
	public Object [][] SessionNewServiceDP_saveUserSessionInfo_verifyResponse(ITestContext testContext)
	{
		String lastAccessedTime = String.valueOf(Calendar.getInstance().getTimeInMillis());
		Calendar startTimeCal = Calendar.getInstance();
		startTimeCal.add(Calendar.HOUR, 2);
		String lastModifiedTime = String.valueOf(startTimeCal.getTimeInMillis());
		Calendar endTimeCal = Calendar.getInstance();
		endTimeCal.add(Calendar.HOUR, 4);
		String endTime = String.valueOf(endTimeCal.getTimeInMillis());
		
		String [] arr1 = { "Fox7", "mohittest100@myntra.com", "mohit", "jain", "1111111111", endTime, lastAccessedTime, lastModifiedTime};
		String [] arr2 = { "Fox7", "", "", "", "", endTime, lastAccessedTime, lastModifiedTime };
		String [] arr3 = { "Production", "mohittest100@myntra.com", "mohit", "jain", "1111111111", endTime, lastAccessedTime, lastModifiedTime};
		
		Object[][] dataSet = new Object[][] { arr1, arr2,arr3 };
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object [][] SessionNewServiceDP_saveUserSessionInfo_verifyFailureResponse(ITestContext testContext)
	{
		String lastAccessedTime = String.valueOf(Calendar.getInstance().getTimeInMillis());
		Calendar startTimeCal = Calendar.getInstance();
		startTimeCal.add(Calendar.HOUR, 2);
		String lastModifiedTime = String.valueOf(startTimeCal.getTimeInMillis());
		Calendar endTimeCal = Calendar.getInstance();
		endTimeCal.add(Calendar.HOUR, 4);
		String endTime = String.valueOf(endTimeCal.getTimeInMillis());
		
		String [] arr1 = { "Fox7", "mohittest100@myntra.com", "mohit", "jain", "1111111111", "JJNZ028Z87fZ314Z594Z829Z7beZ95dZff1Zb21Z220Z364ZM", endTime, 
				lastAccessedTime, lastModifiedTime};
		String [] arr2 = { "Fox7", "", "", "", "", "JJNZ028Z87fZ314Z594Z829Z7beZ95dZff1Zb21Z220Z364ZM", endTime, lastAccessedTime, lastModifiedTime};
		
		String currentTime = String.valueOf(Calendar.getInstance().getTimeInMillis());
		
		String [] arr3 = { "Fox7", "mohittest100@myntra.com", "mohit", "jain", "1111111111", "JJNZ028Z87fZ314Z594Z829Z7beZ95dZff1Zb21Z220Z364ZM", currentTime,  
				currentTime, currentTime };
		String [] arr4 = { "Fox7", "", "", "", "", "JJNZ028Z87fZ314Z594Z829Z7beZ95dZff1Zb21Z220Z364ZM", currentTime, currentTime, currentTime };
		
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4 };
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object [][] SessionNewServiceDP_getNewSession_verifyResponseDataNodesUsingSchemaValidations(ITestContext testContext)
	{
		String [] arr1 = { "Fox7", "mohittest100@myntra.com", "mohit", "jain", "1111111111" };
		String [] arr2 = { "Fox7", "", "", "", "" };
		String [] arr3 = { "Production", "mohittest100@myntra.com", "mohit", "jain", "1111111111" };
		
		Object[][] dataSet = new Object[][] { arr1, arr2,arr3 };
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	}
}
