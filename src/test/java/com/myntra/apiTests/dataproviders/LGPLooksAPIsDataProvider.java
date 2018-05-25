package com.myntra.apiTests.dataproviders;
//LGP

import com.myntra.apiTests.portalservices.commons.CommonUtils;
import com.myntra.apiTests.portalservices.devapiservice.DevApiServiceHelper;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import com.myntra.lordoftherings.Configuration;
import com.myntra.lordoftherings.Toolbox;


public class LGPLooksAPIsDataProvider extends CommonUtils {
	
	
	String Env;
	Configuration con = new Configuration("./Data/configuration");
	static DevApiServiceHelper devApiServiceHelper = new DevApiServiceHelper();

	public LGPLooksAPIsDataProvider() {
		if (System.getenv("ENVIRONMENT") == null)
			Env = con.GetTestEnvironemnt().toString();
		else
			Env = System.getenv("ENVIRONMENT");
		}

//LGP LOOKS DATA PROVIDERS
	@DataProvider
  public Object[][] LGPLooksAPIs_looks_getAllStickers_verifySuccessResponse(ITestContext testContext)
	{ 
	
		String[] arr1 = new String[]{"Fox7","aravind.raj@myntra.com","welcome"};
		String[] arr2 = new String[]{"Functional","manu.chada@myntra.comaravind.raj@myntra.com","welcome"};
		String[] arr3 = new String[]{"Production","aravind.raj@myntra.com","welcome"};
      Object[][] dataSet = new Object[][]{ arr1,arr2,arr3};
      return Toolbox.returnReducedDataSet(Env,dataSet, testContext.getExcludedGroups(), 1, 2);
  }

	@DataProvider
  public Object[][] LGPLooksAPIs_looks_getAllStickers_verifyResponseUsingSchemaValidation(ITestContext testContext)
	{ 
	
		String[] arr1 = new String[]{"Fox7","aravind.raj@myntra.com","welcome"};
		String[] arr2 = new String[]{"Functional","aravind.raj1@myntra.com","welcome"};
		String[] arr3 = new String[]{"Production","aravind.raj@myntra.com","welcome"};
      Object[][] dataSet = new Object[][]{ arr1,arr2,arr3};
      return Toolbox.returnReducedDataSet(Env,dataSet, testContext.getExcludedGroups(), 1, 2);
  }
	
	@DataProvider
  public Object[][] LGPLooksAPIs_looks_getLookDetails_verifySuccessResponse(ITestContext testContext)
	{ 
	
		String[] arr1 = new String[]{"Fox7","aravind.raj@myntra.com","welcome", "56"};
		String[] arr2 = new String[]{"Functional","aravind.raj1@myntra.com","welcome", "56"};
		String[] arr3 = new String[]{"Production","aravind.raj@myntra.com","welcome", "56"};
      Object[][] dataSet = new Object[][]{ arr1,arr2,arr3};
      return Toolbox.returnReducedDataSet(Env,dataSet, testContext.getExcludedGroups(), 1, 2);
  }
	
	@DataProvider
  public Object[][] LGPLooksAPIs_looks_createLook_verifySuccessResponse(ITestContext testContext)
	{ 
	
		String[] arr1 = new String[]{"Fox7","aravind.raj@myntra.com","welcome", "LOOK TITLE FOX7", "LOOK DESCRIPTION FOX7", "true", "false", "false"};
		String[] arr2 = new String[]{"Functional","manu.chada@myntra.com","welcome", "LOOK TITLE Functional", "LOOK DESCRIPTION Functional", "true", "false", "false"};
		String[] arr3 = new String[]{"Production","aravind.raj@myntra.com","welcome", "LOOK TITLE Production", "LOOK DESCRIPTION Production", "true", "false", "false"};
      Object[][] dataSet = new Object[][]{ arr1,arr2,arr3};
      return Toolbox.returnReducedDataSet(Env,dataSet, testContext.getExcludedGroups(), 1, 2);
  }
	
	@DataProvider
  public Object[][] LGPLooksAPIs_looks_createLook_verifySuccessResponseSchemaValidation(ITestContext testContext)
	{ 
	
		String[] arr1 = new String[]{"Fox7","aravind.raj@myntra.com","welcome", "LOOK TITLE FOX7 SCHEMA VALIDATION", "LOOK DESCRIPTION FOX7", "true", "false", "false"};
		String[] arr2 = new String[]{"Functional","manu.chada@myntra.com","welcome", "LOOK TITLE Functional SCHEMA VALIDATION", "LOOK DESCRIPTION Functional", "true", "false", "false"};
		String[] arr3 = new String[]{"Production","aravind.raj@myntra.com","welcome", "LOOK TITLE Production SCHEMA VALIDATION", "LOOK DESCRIPTION Production", "true", "false", "false"};
      Object[][] dataSet = new Object[][]{ arr1,arr2,arr3};
      return Toolbox.returnReducedDataSet(Env,dataSet, testContext.getExcludedGroups(), 1, 2);
  }
	
	@DataProvider
  public Object[][] LGPLooksAPIs_looks_createLook_VerifyDataTagNodes(ITestContext testContext)
	{ 
	
		String[] arr1 = new String[]{"Fox7","aravind.raj@myntra.com","welcome", "LOOK TITLE FOX7 DATA VALIDATION", "LOOK DESCRIPTION FOX7", "true", "false", "false"};
		String[] arr2 = new String[]{"Functional","aravind.raj@myntra.com","welcome", "LOOK TITLE Functional DATA VALIDATION", "LOOK DESCRIPTION Functional", "true", "false", "false"};
		String[] arr3 = new String[]{"Production","aravind.raj@myntra.com","welcome", "LOOK TITLE Production DATA VALIDATION", "LOOK DESCRIPTION Production", "true", "false", "false"};
      Object[][] dataSet = new Object[][]{ arr1,arr2,arr3};
      return Toolbox.returnReducedDataSet(Env,dataSet, testContext.getExcludedGroups(), 1, 2);
  }
	
	@DataProvider
  public Object[][] LGPLooksAPIs_looks_createLookAsDraft_verifySuccessResponse(ITestContext testContext)
	{ 
	
		String[] arr1 = new String[]{"Fox7","aravind.raj@myntra.com","welcome", "DRAFT LOOK TITLE FOX7", "LOOK DESCRIPTION FOX7", "true", "true", "false"};
		String[] arr2 = new String[]{"Functional","aravind.raj@myntra.com","welcome", "DRAFT LOOK TITLE Functional DATA VALIDATION", "LOOK DESCRIPTION Functional", "true", "true", "false"};
		String[] arr3 = new String[]{"Production","aravind.raj@myntra.com","welcome", "DRAFT LOOK TITLE Production DATA VALIDATION", "LOOK DESCRIPTION Production", "true", "true", "false"};
      Object[][] dataSet = new Object[][]{ arr1,arr2,arr3};
      return Toolbox.returnReducedDataSet(Env,dataSet, testContext.getExcludedGroups(), 1, 2);
  }
	
	@DataProvider
  public Object[][] LGPLooksAPIs_looks_createLookAsRemyx_verifyFailureResponse(ITestContext testContext)
	{ 
	
		String[] arr1 = new String[]{"Fox7","aravind.raj@myntra.com","welcome", "REMYX LOOK TITLE FOX7", "LOOK DESCRIPTION FOX7", "true", "false", "true"};
		String[] arr2 = new String[]{"Functional","aravind.raj@myntra.com","welcome", "REMYX LOOK TITLE Functional DATA VALIDATION", "LOOK DESCRIPTION Functional", "true", "false", "true"};
		String[] arr3 = new String[]{"Production","aravind.raj@myntra.com","welcome", "REMYX", "LOOK DESCRIPTION Production", "true", "false", "true"};
      Object[][] dataSet = new Object[][]{ arr1,arr2,arr3};
      return Toolbox.returnReducedDataSet(Env,dataSet, testContext.getExcludedGroups(), 1, 2);
  }
	
	@DataProvider
  public Object[][] LGPLooksAPIs_looks_createLookWithoutIsActive_verifyFailureResponse(ITestContext testContext)
	{ 
	
		String[] arr1 = new String[]{"Fox7","aravind.raj@myntra.com","welcome", "REMYX LOOK TITLE FOX7", "LOOK DESCRIPTION FOX7", null, "false", "false"};
		String[] arr2 = new String[]{"Functional","aravind.raj@myntra.com","welcome", "REMYX LOOK TITLE Functional DATA VALIDATION", "LOOK DESCRIPTION Functional", null, "false", "false"};
		String[] arr3 = new String[]{"Production","aravind.raj@myntra.com","welcome", "REMYX", "LOOK DESCRIPTION Production", null, "false", "false"};
      Object[][] dataSet = new Object[][]{ arr1,arr2,arr3};
      return Toolbox.returnReducedDataSet(Env,dataSet, testContext.getExcludedGroups(), 1, 2);
  }
	
	@DataProvider
  public Object[][] LGPLooksAPIs_looks_createLookWithoutIsDraft_verifyFailureResponse(ITestContext testContext)
	{ 
	
		String[] arr1 = new String[]{"Fox7","aravind.raj@myntra.com","welcome", "REMYX LOOK TITLE FOX7", "LOOK DESCRIPTION FOX7", "true", null, "false"};
		String[] arr2 = new String[]{"Functional","aravind.raj@myntra.com","welcome", "REMYX LOOK TITLE Functional DATA VALIDATION", "LOOK DESCRIPTION Functional", "true", null, "false"};
		String[] arr3 = new String[]{"Production","aravind.raj@myntra.com","welcome", "REMYX", "LOOK DESCRIPTION Production", "true", null, "false"};
      Object[][] dataSet = new Object[][]{ arr1,arr2,arr3};
      return Toolbox.returnReducedDataSet(Env,dataSet, testContext.getExcludedGroups(), 1, 2);
  }
	
	@DataProvider
  public Object[][] LGPLooksAPIs_looks_createLookWithoutIsRemyx_verifyFailureResponse(ITestContext testContext)
	{ 
	
		String[] arr1 = new String[]{"Fox7","aravind.raj@myntra.com","welcome", "REMYX LOOK TITLE FOX7", "LOOK DESCRIPTION FOX7", "true", "false", null};
		String[] arr2 = new String[]{"Functional","aravind.raj@myntra.com","welcome", "REMYX LOOK TITLE Functional DATA VALIDATION", "LOOK DESCRIPTION Functional", "true", "false", null};
		String[] arr3 = new String[]{"Production","aravind.raj@myntra.com","welcome", "REMYX", "LOOK DESCRIPTION Production", "true", "false", null};
      Object[][] dataSet = new Object[][]{ arr1,arr2,arr3};
      return Toolbox.returnReducedDataSet(Env,dataSet, testContext.getExcludedGroups(), 1, 2);
  }
	
	@DataProvider
	public Object[][] LGPLooksAPIs_looks_UpdateLook_verifyDataTagNodes(ITestContext testContext)
		{ 
		
			String[] arr1 = new String[]{"Fox7","aravind.raj@myntra.com","welcome", "UPDATED TITLE FOX7 DATA VALIDATION", "UPDATE DESCRIPTION FOX7 DATA VALIDATION", "true", "false", "false"};
			String[] arr2 = new String[]{"Functional","aravind.raj@myntra.com","welcome", "UPDATEDLOOK TITLE Functional DATA VALIDATION", "UPDATED LOOK DESCRIPTION Functional", "true", "false", "false"};
			String[] arr3 = new String[]{"Production","aravind.raj@myntra.com","welcome", "UPDATEDLOOK TITLE Production DATA VALIDATION", "UPDATED LOOK DESCRIPTION Production", "true", "false", "false"};
	        Object[][] dataSet = new Object[][]{ arr1,arr2,arr3};
	        return Toolbox.returnReducedDataSet(Env,dataSet, testContext.getExcludedGroups(), 1, 2);
	    }
	
	 @DataProvider
	 public Object[][] LGPLooksAPIs_looks_getLooksByOccasion_verifySuccessResponse(ITestContext testContext)
		{ 
		
			String[] arr1 = new String[]{"Fox7","aravind.raj@myntra.com","welcome","1"};
			String[] arr2 = new String[]{"Functional","aravind.raj1@myntra.com","welcome","1"};
			String[] arr3 = new String[]{"Production","aravind.raj@myntra.com","welcome","1"};
	        Object[][] dataSet = new Object[][]{ arr1,arr2,arr3};
	        return Toolbox.returnReducedDataSet(Env,dataSet, testContext.getExcludedGroups(), 1, 2);
	    }
	 
	 @DataProvider
	 public Object[][] LGPLooksAPIs_looks_getLooksByOccasion_verifySchema(ITestContext testContext)
		{ 
		
			String[] arr1 = new String[]{"Fox7","aravind.raj@myntra.com","welcome","1"};
			String[] arr2 = new String[]{"Functional","aravind.raj1@myntra.com","welcome","1"};
			String[] arr3 = new String[]{"Production","aravind.raj@myntra.com","welcome","1"};
	        Object[][] dataSet = new Object[][]{ arr1,arr2,arr3};
	        return Toolbox.returnReducedDataSet(Env,dataSet, testContext.getExcludedGroups(), 1, 2);
	    }
	 
	 @DataProvider
	 public Object[][] LGPLooksAPIs_looks_getLooksByRemyx_verifySuccessResponse(ITestContext testContext)
		{ 
		
			String[] arr1 = new String[]{"Fox7","aravind.raj@myntra.com","welcome","25"};
			String[] arr2 = new String[]{"Functional","aravind.raj1@myntra.com","welcome","25"};
			String[] arr3 = new String[]{"Production","aravind.raj@myntra.com","welcome","25"};
	        Object[][] dataSet = new Object[][]{ arr1,arr2,arr3};
	        return Toolbox.returnReducedDataSet(Env,dataSet, testContext.getExcludedGroups(), 1, 2);
	    }
	 
	 @DataProvider
	 public Object[][] LGPLooksAPIs_looks_getLooksByRemyx_verifySchema(ITestContext testContext)
		{ 
		
			String[] arr1 = new String[]{"Fox7","aravind.raj@myntra.com","welcome","25"};
			String[] arr2 = new String[]{"Functional","aravind.raj1@myntra.com","welcome","25"};
			String[] arr3 = new String[]{"Production","aravind.raj@myntra.com","welcome","25"};
	        Object[][] dataSet = new Object[][]{ arr1,arr2,arr3};
	        return Toolbox.returnReducedDataSet(Env,dataSet, testContext.getExcludedGroups(), 1, 2);
	    }
	 
	 @DataProvider
	 public Object[][] LGPLooksAPIs_looks_getallOccasions_verifySuccessResponse(ITestContext testContext)
		{ 
		
			String[] arr1 = new String[]{"Fox7","aravind.raj@myntra.com","welcome"};
			String[] arr2 = new String[]{"Functional","aravind.raj1@myntra.com","welcome"};
			String[] arr3 = new String[]{"Production","aravind.raj@myntra.com","welcome"};
	        Object[][] dataSet = new Object[][]{ arr1,arr2,arr3};
	        return Toolbox.returnReducedDataSet(Env,dataSet, testContext.getExcludedGroups(), 1, 2);
	    }
	 
	 @DataProvider
	 public Object[][] LGPLooksAPIs_looks_getAllOccasions_verifySchema(ITestContext testContext)
		{ 
		
			String[] arr1 = new String[]{"Fox7","aravind.raj@myntra.com","welcome"};
			String[] arr2 = new String[]{"Functional","aravind.raj1@myntra.com","welcome"};
			String[] arr3 = new String[]{"Production","aravind.raj@myntra.com","welcome"};
	        Object[][] dataSet = new Object[][]{ arr1,arr2,arr3};
	        return Toolbox.returnReducedDataSet(Env,dataSet, testContext.getExcludedGroups(), 1, 2);
	    }
}