package com.myntra.apiTests.dataproviders;

import com.myntra.apiTests.portalservices.commons.CommonUtils;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import com.myntra.lordoftherings.Configuration;
import com.myntra.lordoftherings.Toolbox;

public class LgpServicesDP extends CommonUtils {

	String Env;
	Configuration con = new Configuration("./Data/configuration");
	

	public LgpServicesDP() {
		if (System.getenv("ENVIRONMENT") == null)
			Env = con.GetTestEnvironemnt().toString();
		else
			Env = System.getenv("ENVIRONMENT");
	}
	
	
	@DataProvider
	public Object[][]abTestDataprovider(ITestContext context){
		String[] data1={"lgpservice@myntra.com","123456"};
		String[] data2={"lgpservice@myntra.com","123456"};
		String[] data3={"lgpservice@myntra.com","123456"};
		
		Object[][] dataset={data1,data2,data3};
		
		return Toolbox.returnReducedDataSet(dataset, context.getIncludedGroups(), 1, 2);
	}
	
	//LGP SERVE DATA PROVIDERS
	@DataProvider
	public Object[][]lgpServe_ActionDP(ITestContext  testContext){
		String[] arr1 = new String[]{"Fox7","aravind.raj@myntra.com","welcome"};
		String[] arr2 = new String[]{"Functional","manu.chada@myntra.comaravind.raj@myntra.com","welcome"};
		String[] arr3 = new String[]{"Production","aravind.raj@myntra.com","welcome"};
      Object[][] dataSet = new Object[][]{ arr1,arr2,arr3};
      return Toolbox.returnReducedDataSet(Env,dataSet, testContext.getExcludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][]lgpServe_BulkActionsDP(ITestContext  testContext){
		String[] arr1 = new String[]{"Fox7","aravind.raj@myntra.com","welcome"};
		String[] arr2 = new String[]{"Functional","manu.chada@myntra.comaravind.raj@myntra.com","welcome"};
		String[] arr3 = new String[]{"Production","aravind.raj@myntra.com","welcome"};
      Object[][] dataSet = new Object[][]{ arr1,arr2,arr3};
      return Toolbox.returnReducedDataSet(Env,dataSet, testContext.getExcludedGroups(), 1, 2);
	}
	@DataProvider
	public Object[][]lgpServe_ActionFollowDP(ITestContext  testContext){
		String[] arr1 = new String[]{"Fox7","aravind.raj@myntra.com","welcome"};
		String[] arr2 = new String[]{"Functional","manu.chada@myntra.comaravind.raj@myntra.com","welcome"};
		String[] arr3 = new String[]{"Production","aravind.raj@myntra.com","welcome"};
      Object[][] dataSet = new Object[][]{ arr1,arr2,arr3};
      return Toolbox.returnReducedDataSet(Env,dataSet, testContext.getExcludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][]lgpServe_ActionUnFollowDP(ITestContext  testContext){
		String[] arr1 = new String[]{"Fox7","aravind.raj@myntra.com","welcome"};
		String[] arr2 = new String[]{"Functional","manu.chada@myntra.comaravind.raj@myntra.com","welcome"};
		String[] arr3 = new String[]{"Production","aravind.raj@myntra.com","welcome"};
      Object[][] dataSet = new Object[][]{ arr1,arr2,arr3};
      return Toolbox.returnReducedDataSet(Env,dataSet, testContext.getExcludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][]lgpServe_ActionFollowAllDP(ITestContext  testContext){
		String[] arr1 = new String[]{"Fox7","aravind.raj@myntra.com","welcome"};
		String[] arr2 = new String[]{"Functional","manu.chada@myntra.comaravind.raj@myntra.com","welcome"};
		String[] arr3 = new String[]{"Production","aravind.raj@myntra.com","welcome"};
      Object[][] dataSet = new Object[][]{ arr1,arr2,arr3};
      return Toolbox.returnReducedDataSet(Env,dataSet, testContext.getExcludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][]lgpServe_GetFeedDP(ITestContext  testContext){
		String[] arr1 = new String[]{"Fox7","aravind.raj@myntra.com","welcome"};
		String[] arr2 = new String[]{"Functional","manu.chada@myntra.comaravind.raj@myntra.com","welcome"};
		String[] arr3 = new String[]{"Production","aravind.raj@myntra.com","welcome"};
      Object[][] dataSet = new Object[][]{ arr1,arr2,arr3};
      return Toolbox.returnReducedDataSet(Env,dataSet, testContext.getExcludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][]lgpServe_GetObjectDP(ITestContext  testContext){
		String[] arr1 = new String[]{"Fox7","aravind.raj@myntra.com","welcome"};
		String[] arr2 = new String[]{"Functional","manu.chada@myntra.comaravind.raj@myntra.com","welcome"};
		String[] arr3 = new String[]{"Production","aravind.raj@myntra.com","welcome"};
      Object[][] dataSet = new Object[][]{ arr1,arr2,arr3};
      return Toolbox.returnReducedDataSet(Env,dataSet, testContext.getExcludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][]lgpServe_GetUIDX_DP(ITestContext  testContext){
		String[] arr1 = new String[]{"Fox7","aravind.raj@myntra.com","welcome"};
		String[] arr2 = new String[]{"Functional","manu.chada@myntra.comaravind.raj@myntra.com","welcome"};
		String[] arr3 = new String[]{"Production","aravind.raj@myntra.com","welcome"};
      Object[][] dataSet = new Object[][]{ arr1,arr2,arr3};
      return Toolbox.returnReducedDataSet(Env,dataSet, testContext.getExcludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][]lgpServe_GetPrivateProfileDP(ITestContext  testContext){
		String[] arr1 = new String[]{"Fox7","aravind.raj@myntra.com","welcome"};
		String[] arr2 = new String[]{"Functional","manu.chada@myntra.comaravind.raj@myntra.com","welcome"};
		String[] arr3 = new String[]{"Production","aravind.raj@myntra.com","welcome"};
      Object[][] dataSet = new Object[][]{ arr1,arr2,arr3};
      return Toolbox.returnReducedDataSet(Env,dataSet, testContext.getExcludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][]lgpServe_GetProfileByUDIX_DP(ITestContext  testContext){
		String[] arr1 = new String[]{"Fox7","aravind.raj@myntra.com","welcome"};
		String[] arr2 = new String[]{"Functional","manu.chada@myntra.comaravind.raj@myntra.com","welcome"};
		String[] arr3 = new String[]{"Production","aravind.raj@myntra.com","welcome"};
      Object[][] dataSet = new Object[][]{ arr1,arr2,arr3};
      return Toolbox.returnReducedDataSet(Env,dataSet, testContext.getExcludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][]lgpServe_GetProfileByPPID_DP(ITestContext  testContext){
		String[] arr1 = new String[]{"Fox7","aravind.raj@myntra.com","welcome"};
		String[] arr2 = new String[]{"Functional","manu.chada@myntra.comaravind.raj@myntra.com","welcome"};
		String[] arr3 = new String[]{"Production","aravind.raj@myntra.com","welcome"};
      Object[][] dataSet = new Object[][]{ arr1,arr2,arr3};
      return Toolbox.returnReducedDataSet(Env,dataSet, testContext.getExcludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][]lgpServe_GetFollowing_DP(ITestContext  testContext){
		String[] arr1 = new String[]{"Fox7","aravind.raj@myntra.com","welcome"};
		String[] arr2 = new String[]{"Functional","manu.chada@myntra.comaravind.raj@myntra.com","welcome"};
		String[] arr3 = new String[]{"Production","aravind.raj@myntra.com","welcome"};
      Object[][] dataSet = new Object[][]{ arr1,arr2,arr3};
      return Toolbox.returnReducedDataSet(Env,dataSet, testContext.getExcludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][]lgpServe_GetFollowers_DP(ITestContext  testContext){
		String[] arr1 = new String[]{"Fox7","aravind.raj@myntra.com","welcome"};
		String[] arr2 = new String[]{"Functional","manu.chada@myntra.comaravind.raj@myntra.com","welcome"};
		String[] arr3 = new String[]{"Production","aravind.raj@myntra.com","welcome"};
      Object[][] dataSet = new Object[][]{ arr1,arr2,arr3};
      return Toolbox.returnReducedDataSet(Env,dataSet, testContext.getExcludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][]lgpServe_GetInfluencers_DP(ITestContext  testContext){
		String[] arr1 = new String[]{"Fox7","aravind.raj@myntra.com","welcome"};
		String[] arr2 = new String[]{"Functional","manu.chada@myntra.comaravind.raj@myntra.com","welcome"};
		String[] arr3 = new String[]{"Production","aravind.raj@myntra.com","welcome"};
      Object[][] dataSet = new Object[][]{ arr1,arr2,arr3};
      return Toolbox.returnReducedDataSet(Env,dataSet, testContext.getExcludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][]lgpServe_GetContacts_DP(ITestContext  testContext){
		String[] arr1 = new String[]{"Fox7","aravind.raj@myntra.com","welcome"};
		String[] arr2 = new String[]{"Functional","manu.chada@myntra.comaravind.raj@myntra.com","welcome"};
		String[] arr3 = new String[]{"Production","aravind.raj@myntra.com","welcome"};
      Object[][] dataSet = new Object[][]{ arr1,arr2,arr3};
      return Toolbox.returnReducedDataSet(Env,dataSet, testContext.getExcludedGroups(), 1, 2);
	}
	
	
}
