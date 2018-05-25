package com.myntra.apiTests.dataproviders;

import java.util.Random;
import java.util.UUID;

import com.myntra.apiTests.portalservices.commons.CommonUtils;
import com.myntra.apiTests.portalservices.checkoutservice.CheckoutDataProviderEnum;
import com.myntra.apiTests.portalservices.commons.IServiceConstants;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import com.myntra.apiTests.portalservices.idpservice.IDPServiceDataProviderEnum;
import com.myntra.lordoftherings.Toolbox;

/**
 * @author shankara.c
 *
 */
public class IdeaApiDP extends CommonUtils implements IServiceConstants
{
	String environment;
	
	public IdeaApiDP()
	{
		if(System.getenv(CheckoutDataProviderEnum.ENVIRONMENT.name()) == null)
			environment = CONFIGURATION.GetTestEnvironemnt().toString();
		else
			environment = System.getenv(CheckoutDataProviderEnum.ENVIRONMENT.name());
	}
	
	@DataProvider
    public Object[][] IdeaApiDP_getProfileByEmail_validateAPIResponse(ITestContext testContext)
	{
		String[] arr1 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting100@myntra.com", "200" };
		String[] arr2 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting101@myntra.com", "200" };
		String[] arr3 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting102@myntra.com", "200" };
		String[] arr4 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting103@myntra.com", "200" };
		String[] arr5 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting104@myntra.com", "200" };
		String[] arr6 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting1909@myntra.com", "200" };
		
		String[] arr7 = new String[]{ ENVIRONMENT_FOX7, "myntra", "ideatesting400@myntra.com", "200" };
		String[] arr8 = new String[]{ ENVIRONMENT_FOX7, "myntra", "ideatesting401@myntra.com", "200" };
		
		String[] arr9 = new String[]{ ENVIRONMENT_FOX8, "myntra", "ideatesting400@myntra.com", "200" };
		String[] arr10 = new String[]{ ENVIRONMENT_FOX8, "myntra", "ideatesting401@myntra.com", "200" };
		
        Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4, arr5,arr6,arr7,arr8,arr9,arr10 };
        return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 1, 2);
    }
	
	@DataProvider
    public Object[][] IdeaApiDP_getProfileByEmail_verifySuccessStatusInformation(ITestContext testContext)
	{
		String[] arr1 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting100@myntra.com", "3", "Success", "SUCCESS" };
		String[] arr2 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting101@myntra.com", "3", "Success", "SUCCESS" };
		String[] arr3 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting102@myntra.com", "3", "Success", "SUCCESS" };
		String[] arr4 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting103@myntra.com", "3", "Success", "SUCCESS" };
		String[] arr5 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting104@myntra.com", "3", "Success", "SUCCESS" };
	//	String[] arr6 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting1909@myntra.com", "3", "Success", "SUCCESS" };
		
		String[] arr7 = new String[]{ ENVIRONMENT_FOX7, "myntra", "ideatesting400@myntra.com", "3", "Success", "SUCCESS" };
		String[] arr8 = new String[]{ ENVIRONMENT_FOX7, "myntra", "ideatesting401@myntra.com", "3", "Success", "SUCCESS"  };
		
		String[] arr9 = new String[]{ ENVIRONMENT_FOX8, "myntra", "ideatesting400@myntra.com", "3", "Success", "SUCCESS" };
		String[] arr10 = new String[]{ ENVIRONMENT_FOX8, "myntra", "ideatesting401@myntra.com", "3", "Success", "SUCCESS"  };
		
        Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4, arr5,arr7,arr8,arr9,arr10 };
        return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 1, 2);
    }
	
	@DataProvider
    public Object[][] IdeaApiDP_getProfileByEmail_verifyFailureStatusInformation(ITestContext testContext)
	{
		String[] arr1 = new String[]{ ENVIRONMENT_FUNC, "nonmyntra", "ideatesting103@myntra.com", "111020", "Invalid App", "ERROR" };
		String[] arr2 = new String[]{ ENVIRONMENT_FOX7, "nonmyntra", "ideatesting103@myntra.com", "111020", "Invalid App", "ERROR" };
		String[] arr3 = new String[]{ ENVIRONMENT_FOX8, "nonmyntra", "ideatesting103@myntra.com", "111020", "Invalid App", "ERROR" };
		
        Object[][] dataSet = new Object[][]{ arr1,arr2,arr3 };
        return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 1, 2);
    }
	
	@DataProvider
    public Object[][] IdeaApiDP_getProfileByEmail_verifyNodeValues(ITestContext testContext)
	{
		String[] arr1 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting100", "IdeaTesting", "UserNo100", "ideatesting100@myntra.com", "9099414062", "MALE", 
				"1981-03-11", "SUCCESS" };
		String[] arr2 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting101", "IdeaTesting", "UserNo101", "ideatesting101@myntra.com", "9928286747", "FEMALE", 
				"1982-04-21", "SUCCESS" };
		String[] arr3 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting102", "IdeaTesting", "UserNo102", "ideatesting102@myntra.com", "9127963529", "MALE", 
				"1983-05-30", "SUCCESS" };
		String[] arr4 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting103", "IdeaTesting", "UserNo103", "ideatesting103@myntra.com", "9609690960", "FEMALE",
				"1984-06-09", "SUCCESS" };
		String[] arr5 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting104", "IdeaTesting", "UserNo104", "ideatesting104@myntra.com", "9499280686", "MALE", 
				"1985-07-02", "SUCCESS" };
		
		String[] arr6 = new String[]{ ENVIRONMENT_FOX7, "myntra", "ideatesting400", "main", "user", "ideatesting400@myntra.com", "9789645123", "MALE", 
				"1985-07-02", "SUCCESS" };
		
		String[] arr7 = new String[]{ ENVIRONMENT_FOX8, "myntra", "ideatesting400", "main", "user", "ideatesting400@myntra.com", "9789645123", "MALE", 
				"1985-07-02", "SUCCESS" };
		
        Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4, arr5,arr6,arr7 };
        return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 1, 2);
    }
	
	
	@DataProvider
    public Object[][] IdeaApiDP_getProfileByEmail_verifyResponseDataNodesUsingSchemaValidations(ITestContext testContext)
	{
		String[] arr1 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting100", "IdeaTesting", "UserNo100", "ideatesting100@myntra.com", "9099414062", "MALE", 
				"1981-03-11", "SUCCESS" };
		String[] arr2 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting101", "IdeaTesting", "UserNo101", "ideatesting101@myntra.com", "9928286747", "FEMALE", 
				"1982-04-21", "SUCCESS" };
		String[] arr3 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting102", "IdeaTesting", "UserNo102", "ideatesting102@myntra.com", "9127963529", "MALE", 
				"1983-05-30", "SUCCESS" };
		String[] arr4 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting103", "IdeaTesting", "UserNo103", "ideatesting103@myntra.com", "9609690960", "FEMALE",
				"1984-06-09", "SUCCESS" };
		String[] arr5 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting104", "IdeaTesting", "UserNo104", "ideatesting104@myntra.com", "9499280686", "MALE", 
				"1985-07-02", "SUCCESS" };
		
		String[] arr6 = new String[]{ ENVIRONMENT_FOX7, "myntra", "ideatesting400", "main", "user", "ideatesting400@myntra.com", "9789645123", "MALE", 
				"1985-07-02", "SUCCESS" };
		
		String[] arr7 = new String[]{ ENVIRONMENT_FOX8, "myntra", "ideatesting400", "main", "user", "ideatesting400@myntra.com", "9789645123", "MALE", 
				"1985-07-02", "SUCCESS" };
		
        Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4, arr5,arr6,arr7};
        return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 1, 2);
    }
	
	@DataProvider
    public Object[][] IdeaApiDP_getProfileByUserId_validateAPIResponse(ITestContext testContext)
	{
		String[] arr1 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting100@myntra.com", "200" };
		String[] arr2 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting101@myntra.com", "200" };
		String[] arr3 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting102@myntra.com", "200" };
		String[] arr4 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting103@myntra.com", "200" };
		String[] arr5 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting104@myntra.com", "200" };
		String[] arr6 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting1909@myntra.com", "200" };
		
		String[] arr7 = new String[]{ ENVIRONMENT_FOX7, "myntra", "ideatesting400@myntra.com", "200" };
		String[] arr8 = new String[]{ ENVIRONMENT_FOX7, "myntra", "ideatesting401@myntra.com", "200" };
		
		String[] arr9 = new String[]{ ENVIRONMENT_FOX8, "myntra", "ideatesting400@myntra.com", "200" };
		String[] arr10 = new String[]{ ENVIRONMENT_FOX8, "myntra", "ideatesting401@myntra.com", "200" };
		
        Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4, arr5, arr6,arr7,arr8,arr9,arr10 };
        return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 1, 2);
    }
	
	@DataProvider
    public Object[][] IdeaApiDP_getProfileByUserId_verifySuccessStatusInformation(ITestContext testContext)
	{
		String[] arr1 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting100@myntra.com", "3", "Success", "SUCCESS" };
		String[] arr2 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting101@myntra.com", "3", "Success", "SUCCESS" };
		String[] arr3 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting102@myntra.com", "3", "Success", "SUCCESS" };
		String[] arr4 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting103@myntra.com", "3", "Success", "SUCCESS" };
		String[] arr5 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting104@myntra.com", "3", "Success", "SUCCESS" };
	//	String[] arr6 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting1909@myntra.com", "3", "Success", "SUCCESS" };
		
		String[] arr7 = new String[]{ ENVIRONMENT_FOX7, "myntra", "ideatesting400@myntra.com", "3", "Success", "SUCCESS"  };
		String[] arr8 = new String[]{ ENVIRONMENT_FOX7, "myntra", "ideatesting401@myntra.com", "3", "Success", "SUCCESS"  };
		
		String[] arr9 = new String[]{ ENVIRONMENT_FOX8, "myntra", "ideatesting400@myntra.com", "3", "Success", "SUCCESS"  };
		String[] arr10 = new String[]{ ENVIRONMENT_FOX8, "myntra", "ideatesting401@myntra.com", "3", "Success", "SUCCESS"  };
		
        Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4, arr5,arr7,arr8,arr9,arr10};
        return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 1, 2);
    }

	@DataProvider
    public Object[][] IdeaApiDP_getProfileByUserId_verifyFailureStatusInformation(ITestContext testContext)
	{
		String[] arr1 = new String[]{ ENVIRONMENT_FUNC, "nonmyntra", "ideatesting103@myntra.com", "111020", "Invalid App", "ERROR" };
		String[] arr2 = new String[]{ ENVIRONMENT_FOX7, "nonmyntra", "ideatesting400@myntra.com", "111020", "Invalid App", "ERROR" };
		String[] arr3 = new String[]{ ENVIRONMENT_FOX8, "nonmyntra", "ideatesting400@myntra.com", "111020", "Invalid App", "ERROR" };		
		
        Object[][] dataSet = new Object[][]{ arr1,arr2,arr3 };
        return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 1, 2);
    }
	
	@DataProvider
    public Object[][] IdeaApiDP_getProfileByUserId_verifyNodeValues(ITestContext testContext)
	{
		String[] arr1 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting100", "IdeaTesting", "UserNo100", "ideatesting100@myntra.com", "9099414062", "MALE", 
				"1981-03-11", "SUCCESS" };
		String[] arr2 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting101", "IdeaTesting", "UserNo101", "ideatesting101@myntra.com", "9928286747", "FEMALE", 
				"1982-04-21", "SUCCESS" };
		String[] arr3 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting102", "IdeaTesting", "UserNo102", "ideatesting102@myntra.com", "9127963529", "MALE", 
				"1983-05-30", "SUCCESS" };
		String[] arr4 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting103", "IdeaTesting", "UserNo103", "ideatesting103@myntra.com", "9609690960", "FEMALE",
				"1984-06-09", "SUCCESS" };
		String[] arr5 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting104", "IdeaTesting", "UserNo104", "ideatesting104@myntra.com", "9499280686", "MALE", 
				"1985-07-02", "SUCCESS" };
		
		String[] arr6 = new String[]{ ENVIRONMENT_FOX7, "myntra", "ideatesting400", "main", "user", "ideatesting400@myntra.com", "9789645123", "MALE", 
				"1985-07-02", "SUCCESS" };
		String[] arr7 = new String[]{ ENVIRONMENT_FOX7, "myntra", "ideatesting401", "main", "user", "ideatesting401@myntra.com", "9789645124", "MALE", 
				"1985-07-02", "SUCCESS" };
		
		String[] arr8 = new String[]{ ENVIRONMENT_FOX8, "myntra", "ideatesting400", "main", "user", "ideatesting400@myntra.com", "9789645123", "MALE", 
				"1985-07-02", "SUCCESS" };
		String[] arr9 = new String[]{ ENVIRONMENT_FOX8, "myntra", "ideatesting401", "main", "user", "ideatesting401@myntra.com", "9789645124", "MALE", 
				"1985-07-02", "SUCCESS" };
		
        Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4, arr5,arr6,arr7,arr8,arr9 };
        return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 1, 2);
    }
	
	@DataProvider
    public Object[][] IdeaApiDP_getProfileByUserId_verifyResponseDataNodesUsingSchemaValidations(ITestContext testContext)
	{
		String[] arr1 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting100", "IdeaTesting", "UserNo100", "ideatesting100@myntra.com", "9099414062", "MALE", 
				"1981-03-11", "SUCCESS" };
		String[] arr2 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting101", "IdeaTesting", "UserNo101", "ideatesting101@myntra.com", "9928286747", "FEMALE", 
				"1982-04-21", "SUCCESS" };
		String[] arr3 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting102", "IdeaTesting", "UserNo102", "ideatesting102@myntra.com", "9127963529", "MALE", 
				"1983-05-30", "SUCCESS" };
		String[] arr4 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting103", "IdeaTesting", "UserNo103", "ideatesting103@myntra.com", "9609690960", "FEMALE",
				"1984-06-09", "SUCCESS" };
		String[] arr5 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting104", "IdeaTesting", "UserNo104", "ideatesting104@myntra.com", "9499280686", "MALE", 
				"1985-07-02", "SUCCESS" };
		
		String[] arr6 = new String[]{ ENVIRONMENT_FOX7, "myntra", "ideatesting400", "main", "user", "ideatesting400@myntra.com", "9789645123", "MALE", 
				"1985-07-02", "SUCCESS" };
		String[] arr7 = new String[]{ ENVIRONMENT_FOX7, "myntra", "ideatesting401", "main", "user", "ideatesting401@myntra.com", "9789645124", "MALE", 
				"1985-07-02", "SUCCESS" };
		
		String[] arr8 = new String[]{ ENVIRONMENT_FOX8, "myntra", "ideatesting400", "main", "user", "ideatesting400@myntra.com", "9789645123", "MALE", 
				"1985-07-02", "SUCCESS" };
		String[] arr9 = new String[]{ ENVIRONMENT_FOX8, "myntra", "ideatesting401", "main", "user", "ideatesting401@myntra.com", "9789645124", "MALE", 
				"1985-07-02", "SUCCESS" };
		
        Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4, arr5,arr6,arr7,arr8,arr9 };
        return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 1, 2);
    }
	
	@DataProvider
    public Object[][] IdeaApiDP_addEmailToUserProfile_validateAPIResponse(ITestContext testContext)
	{
		String[] arr1 = new String[]{ ENVIRONMENT_FUNC, "myntra", generateRandomId() + "@gmail.com", "ideatesting100@myntra.com", "200" };
		String[] arr2 = new String[]{ ENVIRONMENT_FUNC, "myntra", generateRandomId() + "@yahoo.com", "ideatesting100@myntra.com", "200" };
		
		String[] arr3 = new String[]{ ENVIRONMENT_FUNC, "myntra", generateRandomId() + "@outlook.com", "ideatesting101@myntra.com", "200" };
		String[] arr4 = new String[]{ ENVIRONMENT_FUNC, "myntra", generateRandomId() + "@yahoo.com", "ideatesting101@myntra.com", "200" };
		
		String[] arr5 = new String[]{ ENVIRONMENT_FUNC, "myntra", generateRandomId() + "@myntra.com", "ideatesting102@myntra.com", "200" };
		String[] arr6 = new String[]{ ENVIRONMENT_FUNC, "myntra", generateRandomId() + "@gmail.com", "ideatesting102@myntra.com", "200" };
		
		String[] arr7 = new String[]{ ENVIRONMENT_FUNC, "myntra", generateRandomId() + "@myntra.com", "ideatesting103@myntra.com", "200" };
		String[] arr8 = new String[]{ ENVIRONMENT_FUNC, "myntra", generateRandomId() + "@yahoo.com", "ideatesting103@myntra.com", "200" };
		
		String[] arr9 = new String[]{ ENVIRONMENT_FUNC, "myntra", generateRandomId() + "@outlook.com", "ideatesting104@myntra.com", "200" };
		String[] arr10 = new String[]{ ENVIRONMENT_FUNC, "myntra", generateRandomId() + "@myntra.com", "ideatesting104@myntra.com", "200" };
		
		String[] arr11 = new String[]{ ENVIRONMENT_FOX7, "myntra", generateRandomId() + "@outlook.com", "ideatesting400@myntra.com", "200" };
		String[] arr12 = new String[]{ ENVIRONMENT_FOX7, "myntra", generateRandomId() + "@myntra.com", "ideatesting401@myntra.com", "200" };
		
		String[] arr13 = new String[]{ ENVIRONMENT_FOX8, "myntra", generateRandomId() + "@outlook.com", "ideatesting400@myntra.com", "200" };
		String[] arr14 = new String[]{ ENVIRONMENT_FOX8, "myntra", generateRandomId() + "@myntra.com", "ideatesting401@myntra.com", "200" };
		
        Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9, arr10,arr11,arr12,arr13,arr14 };
        return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 1, 2);
    }
	
	@DataProvider
    public Object[][] IdeaApiDP_addEmailToUserProfile_verifySuccessStatusInformation(ITestContext testContext)
	{
		String[] arr1 = new String[]{ ENVIRONMENT_FUNC, "myntra", generateRandomId() + "@gmail.com", "ideatesting100", "IdeaTesting", "UserNo100", 
				"ideatesting100@myntra.com", "9099414062", "MALE", "1981-03-11", "3", "Success", "SUCCESS" };
		String[] arr2 = new String[]{ ENVIRONMENT_FUNC, "myntra", generateRandomId() + "@outlook.com", "ideatesting100", "IdeaTesting", "UserNo100", 
				"ideatesting100@myntra.com", "9099414062", "MALE", "1981-03-11", "3", "Success", "SUCCESS" };
		
		String[] arr3 = new String[]{ ENVIRONMENT_FUNC, "myntra", generateRandomId() + "@yahoo.com", "ideatesting101", "IdeaTesting", "UserNo101", 
				"ideatesting101@myntra.com", "9928286747", "FEMALE", "1982-04-21", "3", "Success", "SUCCESS" };
		String[] arr4 = new String[]{ ENVIRONMENT_FUNC, "myntra", generateRandomId() + "@gmail.com", "ideatesting101", "IdeaTesting", "UserNo101", 
				"ideatesting101@myntra.com", "9928286747", "FEMALE", "1982-04-21", "3", "Success", "SUCCESS" };
		
		String[] arr5 = new String[]{ ENVIRONMENT_FUNC, "myntra", generateRandomId() + "@yahoo.com", "ideatesting102", "IdeaTesting", "UserNo102", 
				"ideatesting102@myntra.com", "9127963529", "MALE", "1983-05-30", "3", "Success", "SUCCESS" };
		String[] arr6 = new String[]{ ENVIRONMENT_FUNC, "myntra", generateRandomId() + "@myntra.com", "ideatesting102", "IdeaTesting", "UserNo102", 
				"ideatesting102@myntra.com", "9127963529", "MALE", "1983-05-30", "3", "Success", "SUCCESS" };
		
		String[] arr7 = new String[]{ ENVIRONMENT_FUNC, "myntra", generateRandomId() + "@myntra.com", "ideatesting103", "IdeaTesting", "UserNo103", 
				"ideatesting103@myntra.com", "9609690960", "FEMALE", "1984-06-09", "3", "Success", "SUCCESS" };
		String[] arr8 = new String[]{ ENVIRONMENT_FUNC, "myntra", generateRandomId() + "@outlook.com", "ideatesting103", "IdeaTesting", "UserNo103", 
				"ideatesting103@myntra.com", "9609690960", "FEMALE", "1984-06-09", "3", "Success", "SUCCESS" };
		
		String[] arr9 = new String[]{ ENVIRONMENT_FUNC, "myntra", generateRandomId() + "@outlook.com", "ideatesting104", "IdeaTesting", "UserNo104", 
				"ideatesting104@myntra.com", "9499280686", "MALE", "1985-07-02", "3", "Success", "SUCCESS" };
		String[] arr10 = new String[]{ ENVIRONMENT_FUNC, "myntra", generateRandomId() + "@yahoo.com","ideatesting104", "IdeaTesting", "UserNo104", 
				"ideatesting104@myntra.com", "9499280686", "MALE", "1985-07-02", "3", "Success", "SUCCESS" };
		
		String[] arr11 = new String[]{ ENVIRONMENT_FOX7, "myntra", generateRandomId() + "@outlook.com", "ideatesting400", "main", "user", 
				"ideatesting400@myntra.com", "9789645123", "MALE", "1991-03-06", "3", "Success", "SUCCESS" };
		String[] arr12 = new String[]{ ENVIRONMENT_FOX7, "myntra", generateRandomId() + "@yahoo.com","ideatesting401", "main", "user", 
				"ideatesting401@myntra.com", "9789645124", "MALE", "1991-03-06", "3", "Success", "SUCCESS" };
		
		String[] arr13 = new String[]{ ENVIRONMENT_FOX8, "myntra", generateRandomId() + "@outlook.com", "ideatesting400", "main", "user", 
				"ideatesting400@myntra.com", "9789645123", "MALE", "1991-03-06", "3", "Success", "SUCCESS" };
		String[] arr14 = new String[]{ ENVIRONMENT_FOX8, "myntra", generateRandomId() + "@yahoo.com","ideatesting401", "main", "user", 
				"ideatesting401@myntra.com", "9789645124", "MALE", "1991-03-06", "3", "Success", "SUCCESS" };
        
        Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9, arr10,arr11,arr12,arr13,arr14 };
        return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 1, 2);
    }
	
	@DataProvider
    public Object[][] IdeaApiDP_addEmailToUserProfile_verifyFailureStatusInformation(ITestContext testContext)
	{
		String[] arr1 = new String[]{ ENVIRONMENT_FUNC, "myntra", "", "ideatesting104@myntra.com", "58", "Error occurred while updating/processing data", "ERROR" };
		String[] arr2 = new String[]{ ENVIRONMENT_FUNC, "myntra", "abc$2121", "ideatesting104@myntra.com", "58", "Error occurred while updating/processing data", "ERROR" };
		String[] arr3 = new String[]{ ENVIRONMENT_FUNC, "myntra", generateRandomId() + "@myntra.com", "55239ab59dba@yahoo.com", "111004", "Account Doesn't exists", "ERROR" };
		String[] arr4 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting104@myntra.com", "ideatesting104@myntra.com", "111014", "email already exists", "ERROR" };
		String[] arr5 = new String[]{ ENVIRONMENT_FUNC, "nonmyntra", generateRandomId() + "@myntra.com", "ideatesting104@myntra.com", "111020", "Invalid App", "ERROR" };
		
		String[] arr6 = new String[]{ ENVIRONMENT_FOX7, "myntra", "ideatesting400@myntra.com", "ideatesting400@myntra.com", "111014", "email already exists", "ERROR" };
		String[] arr7 = new String[]{ ENVIRONMENT_FOX7, "nonmyntra", generateRandomId() + "@myntra.com", "ideatesting104@myntra.com", "111020", "Invalid App", "ERROR" };
		
		String[] arr8 = new String[]{ ENVIRONMENT_FOX8, "myntra", "ideatesting400@myntra.com", "ideatesting400@myntra.com", "111014", "email already exists", "ERROR" };
		String[] arr9 = new String[]{ ENVIRONMENT_FOX8, "nonmyntra", generateRandomId() + "@myntra.com", "ideatesting104@myntra.com", "111020", "Invalid App", "ERROR" };
		
        Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4, arr5,arr6,arr7,arr8,arr9 };
        return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 1, 2);
    }
	
	@DataProvider
    public Object[][] IdeaApiDP_addEmailToUserProfile_verifyNodeValues(ITestContext testContext)
	{
		String[] arr1 = new String[]{ ENVIRONMENT_FUNC, "myntra", generateRandomId() + "@gmail.com", "ideatesting100", "IdeaTesting", "UserNo100", 
				"ideatesting100@myntra.com", "9099414062", "MALE", "1981-03-11", "SUCCESS" };
		String[] arr2 = new String[]{ ENVIRONMENT_FUNC, "myntra", generateRandomId() + "@outlook.com", "ideatesting100", "IdeaTesting", "UserNo100", 
				"ideatesting100@myntra.com", "9099414062", "MALE", "1981-03-11", "SUCCESS" };
		
		String[] arr3 = new String[]{ ENVIRONMENT_FUNC, "myntra", generateRandomId() + "@yahoo.com", "ideatesting101", "IdeaTesting", "UserNo101", 
				"ideatesting101@myntra.com", "9928286747", "FEMALE", "1982-04-21", "SUCCESS" };
		String[] arr4 = new String[]{ ENVIRONMENT_FUNC, "myntra", generateRandomId() + "@gmail.com", "ideatesting101", "IdeaTesting", "UserNo101", 
				"ideatesting101@myntra.com", "9928286747", "FEMALE", "1982-04-21", "SUCCESS" };
		
		String[] arr5 = new String[]{ ENVIRONMENT_FUNC, "myntra", generateRandomId() + "@yahoo.com", "ideatesting102", "IdeaTesting", "UserNo102", 
				"ideatesting102@myntra.com", "9127963529", "MALE", "1983-05-30", "SUCCESS" };
		String[] arr6 = new String[]{ ENVIRONMENT_FUNC, "myntra", generateRandomId() + "@myntra.com", "ideatesting102", "IdeaTesting", "UserNo102", 
				"ideatesting102@myntra.com", "9127963529", "MALE", "1983-05-30", "SUCCESS" };
		
		String[] arr7 = new String[]{ ENVIRONMENT_FUNC, "myntra", generateRandomId() + "@myntra.com", "ideatesting103", "IdeaTesting", "UserNo103", 
				"ideatesting103@myntra.com", "9609690960", "FEMALE", "1984-06-09", "SUCCESS" };
		String[] arr8 = new String[]{ ENVIRONMENT_FUNC, "myntra", generateRandomId() + "@outlook.com", "ideatesting103", "IdeaTesting", "UserNo103", 
				"ideatesting103@myntra.com", "9609690960", "FEMALE", "1984-06-09", "SUCCESS" };
		
		String[] arr9 = new String[]{ ENVIRONMENT_FUNC, "myntra", generateRandomId() + "@outlook.com", "ideatesting104", "IdeaTesting", "UserNo104", 
				"ideatesting104@myntra.com", "9499280686", "MALE", "1985-07-02", "SUCCESS" };
		String[] arr10 = new String[]{ ENVIRONMENT_FUNC, "myntra", generateRandomId() + "@yahoo.com","ideatesting104", "IdeaTesting", "UserNo104", 
				"ideatesting104@myntra.com", "9499280686", "MALE", "1985-07-02", "SUCCESS" };
		
		String[] arr11 = new String[]{ ENVIRONMENT_FOX7, "myntra", generateRandomId() + "@outlook.com", "ideatesting400", "main", "user", 
				"ideatesting400@myntra.com", "9789645123", "MALE", "1991-03-06", "SUCCESS" };
		String[] arr12 = new String[]{ ENVIRONMENT_FOX7, "myntra", generateRandomId() + "@yahoo.com","ideatesting401", "main", "user", 
				"ideatesting401@myntra.com", "9789645124", "MALE", "1991-03-06", "SUCCESS" };
		
		String[] arr13 = new String[]{ ENVIRONMENT_FOX8, "myntra", generateRandomId() + "@outlook.com", "ideatesting400", "main", "user", 
				"ideatesting400@myntra.com", "9789645123", "MALE", "1991-03-06", "SUCCESS" };
		String[] arr14 = new String[]{ ENVIRONMENT_FOX8, "myntra", generateRandomId() + "@yahoo.com","ideatesting401", "main", "user", 
				"ideatesting401@myntra.com", "9789645124", "MALE", "1991-03-06", "SUCCESS" };
        
        Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9, arr10,arr11,arr12,arr13,arr14 };
        return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 1, 2);
    }
	
	@DataProvider
    public Object[][] IdeaApiDP_addEmailToUserProfile_verifyResponseDataNodesUsingSchemaValidations(ITestContext testContext)
	{
		String[] arr1 = new String[]{ ENVIRONMENT_FUNC, "myntra", generateRandomId() + "@gmail.com", "ideatesting100", "IdeaTesting", "UserNo100", 
				"ideatesting100@myntra.com", "9099414062", "MALE", "1981-03-11", "SUCCESS" };
		String[] arr2 = new String[]{ ENVIRONMENT_FUNC, "myntra", generateRandomId() + "@outlook.com", "ideatesting100", "IdeaTesting", "UserNo100", 
				"ideatesting100@myntra.com", "9099414062", "MALE", "1981-03-11", "SUCCESS" };
		
		String[] arr3 = new String[]{ ENVIRONMENT_FUNC, "myntra", generateRandomId() + "@yahoo.com", "ideatesting101", "IdeaTesting", "UserNo101", 
				"ideatesting101@myntra.com", "9928286747", "FEMALE", "1982-04-21", "SUCCESS" };
		String[] arr4 = new String[]{ ENVIRONMENT_FUNC, "myntra", generateRandomId() + "@gmail.com", "ideatesting101", "IdeaTesting", "UserNo101", 
				"ideatesting101@myntra.com", "9928286747", "FEMALE", "1982-04-21", "SUCCESS" };
		
		String[] arr5 = new String[]{ ENVIRONMENT_FUNC, "myntra", generateRandomId() + "@yahoo.com", "ideatesting102", "IdeaTesting", "UserNo102", 
				"ideatesting102@myntra.com", "9127963529", "MALE", "1983-05-30", "SUCCESS" };
		String[] arr6 = new String[]{ ENVIRONMENT_FUNC, "myntra", generateRandomId() + "@myntra.com", "ideatesting102", "IdeaTesting", "UserNo102", 
				"ideatesting102@myntra.com", "9127963529", "MALE", "1983-05-30", "SUCCESS" };
		
		String[] arr7 = new String[]{ ENVIRONMENT_FUNC, "myntra", generateRandomId() + "@myntra.com", "ideatesting103", "IdeaTesting", "UserNo103", 
				"ideatesting103@myntra.com", "9609690960", "FEMALE", "1984-06-09", "SUCCESS" };
		String[] arr8 = new String[]{ ENVIRONMENT_FUNC, "myntra", generateRandomId() + "@outlook.com", "ideatesting103", "IdeaTesting", "UserNo103", 
				"ideatesting103@myntra.com", "9609690960", "FEMALE", "1984-06-09", "SUCCESS" };
		
		String[] arr9 = new String[]{ ENVIRONMENT_FUNC, "myntra", generateRandomId() + "@outlook.com", "ideatesting104", "IdeaTesting", "UserNo104", 
				"ideatesting104@myntra.com", "9499280686", "MALE", "1985-07-02", "SUCCESS" };
		String[] arr10 = new String[]{ ENVIRONMENT_FUNC, "myntra", generateRandomId() + "@yahoo.com","ideatesting104", "IdeaTesting", "UserNo104", 
				"ideatesting104@myntra.com", "9499280686", "MALE", "1985-07-02", "SUCCESS" };
		
		String[] arr11 = new String[]{ ENVIRONMENT_FOX7, "myntra", generateRandomId() + "@outlook.com", "ideatesting400", "main", "user", 
				"ideatesting400@myntra.com", "9789645123", "MALE", "1991-03-06", "SUCCESS" };
		String[] arr12 = new String[]{ ENVIRONMENT_FOX7, "myntra", generateRandomId() + "@yahoo.com","ideatesting401", "main", "user", 
				"ideatesting401@myntra.com", "9789645124", "MALE", "1991-03-06", "SUCCESS" };
		
		String[] arr13 = new String[]{ ENVIRONMENT_FOX8, "myntra", generateRandomId() + "@outlook.com", "ideatesting400", "main", "user", 
				"ideatesting400@myntra.com", "9789645123", "MALE", "1991-03-06", "SUCCESS" };
		String[] arr14 = new String[]{ ENVIRONMENT_FOX8, "myntra", generateRandomId() + "@yahoo.com","ideatesting401", "main", "user", 
				"ideatesting401@myntra.com", "9789645124", "MALE", "1991-03-06", "SUCCESS" };
        
        Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9, arr10,arr11,arr12,arr13,arr14 };
        return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 1, 2);
    }
	
	@DataProvider
    public Object[][] IdeaApiDP_addPhoneNumberToUserProfile_validateAPIResponse(ITestContext testContext)
	{
		String[] arr1 = new String[]{ ENVIRONMENT_FUNC, "myntra", generateRandomMobileNumber(), "ideatesting100@myntra.com", "200" };
		String[] arr2 = new String[]{ ENVIRONMENT_FUNC, "myntra", generateRandomMobileNumber(), "ideatesting101@myntra.com", "200" };
		String[] arr3 = new String[]{ ENVIRONMENT_FUNC, "myntra", generateRandomMobileNumber(), "ideatesting102@myntra.com", "200" };
		String[] arr4 = new String[]{ ENVIRONMENT_FUNC, "myntra", generateRandomMobileNumber(), "ideatesting103@myntra.com", "200" };
		String[] arr5 = new String[]{ ENVIRONMENT_FUNC, "myntra", generateRandomMobileNumber(), "ideatesting104@myntra.com", "200" };
		
		String[] arr6 = new String[]{ ENVIRONMENT_FOX7, "myntra", generateRandomMobileNumber(), "ideatesting400@myntra.com", "200" };
		String[] arr7 = new String[]{ ENVIRONMENT_FOX7, "myntra", generateRandomMobileNumber(), "ideatesting401@myntra.com", "200" };
		
		String[] arr8 = new String[]{ ENVIRONMENT_FOX8, "myntra", generateRandomMobileNumber(), "ideatesting400@myntra.com", "200" };
		String[] arr9 = new String[]{ ENVIRONMENT_FOX8, "myntra", generateRandomMobileNumber(), "ideatesting401@myntra.com", "200" };
		
        Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4, arr5,arr6,arr7,arr8,arr9 };
        return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 1, 2);
    }
	
	@DataProvider
    public Object[][] IdeaApiDP_addPhoneNumberToUserProfile_verifySuccessStatusInformation(ITestContext testContext)
	{
		String[] arr1 = new String[]{ ENVIRONMENT_FUNC, "myntra", generateRandomMobileNumber(), "ideatesting100", "IdeaTesting", "UserNo100", "ideatesting100@myntra.com",
				"9099414062", "MALE", "1981-03-11", "3", "Success", "SUCCESS" };
		String[] arr2 = new String[]{ ENVIRONMENT_FUNC, "myntra", generateRandomMobileNumber(), "ideatesting101", "IdeaTesting", "UserNo101", "ideatesting101@myntra.com", 
				"9928286747", "FEMALE", "1982-04-21", "3", "Success", "SUCCESS" };
		String[] arr3 = new String[]{ ENVIRONMENT_FUNC, "myntra", generateRandomMobileNumber(), "ideatesting102", "IdeaTesting", "UserNo102", "ideatesting102@myntra.com", 
				"9127963529", "MALE", "1983-05-30", "3", "Success", "SUCCESS" };
		String[] arr4 = new String[]{ ENVIRONMENT_FUNC, "myntra", generateRandomMobileNumber(), "ideatesting103", "IdeaTesting", "UserNo103", "ideatesting103@myntra.com", 
				"9609690960", "FEMALE", "1984-06-09", "3", "Success", "SUCCESS" };
		String[] arr5 = new String[]{ ENVIRONMENT_FUNC, "myntra", generateRandomMobileNumber(), "ideatesting104", "IdeaTesting", "UserNo104", "ideatesting104@myntra.com", 
				"9499280686", "MALE", "1985-07-02", "3", "Success", "SUCCESS" };
		
		String[] arr6 = new String[]{ ENVIRONMENT_FOX7, "myntra", generateRandomMobileNumber(), "ideatesting400", "main", "user", "ideatesting400@myntra.com", 
				"9789645123", "MALE", "1991-03-06", "3", "Success", "SUCCESS" };
		String[] arr7 = new String[]{ ENVIRONMENT_FOX7, "myntra", generateRandomMobileNumber(), "ideatesting401", "main", "user", "ideatesting401@myntra.com", 
				"9789645124", "MALE", "1991-03-06", "3", "Success", "SUCCESS" };
		
		String[] arr8 = new String[]{ ENVIRONMENT_FOX8, "myntra", generateRandomMobileNumber(), "ideatesting400", "main", "user", "ideatesting400@myntra.com", 
				"9789645123", "MALE", "1991-03-06", "3", "Success", "SUCCESS" };
		String[] arr9 = new String[]{ ENVIRONMENT_FOX8, "myntra", generateRandomMobileNumber(), "ideatesting401", "main", "user", "ideatesting401@myntra.com", 
				"9789645124", "MALE", "1991-03-06", "3", "Success", "SUCCESS" };
        
        Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4, arr5,arr6,arr7,arr8,arr9};
        return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 1, 2);
    }
	
	@DataProvider
    public Object[][] IdeaApiDP_addPhoneNumberToUserProfile_verifyFailureStatusInformation(ITestContext testContext)
	{
		String[] arr1 = new String[]{ ENVIRONMENT_FUNC, "myntra", "", "ideatesting104@myntra.com", "58", "Error occurred while updating/processing data", "ERROR" };
		String[] arr2 = new String[]{ ENVIRONMENT_FUNC, "myntra", generateRandomId(), "ideatesting104@myntra.com", "58", "Error occurred while updating/processing data", "ERROR" };
		String[] arr3 = new String[]{ ENVIRONMENT_FUNC, "myntra", "99809980889087", "ideatesting104@myntra.com", "58", "Error occurred while updating/processing data", "ERROR" };
		String[] arr4 = new String[]{ ENVIRONMENT_FUNC, "myntra", generateRandomMobileNumber(), "55239ab59dba@yahoo.com", "111004", "Account Doesn't exists", "ERROR" };
		//String[] arr5 = new String[]{ ENVIRONMENT_FUNC, "myntra", "9499280686", "ideatesting104@myntra.com", "111019", "phone already exists", "ERROR" };
		String[] arr6 = new String[]{ ENVIRONMENT_FUNC, "nonmyntra", "9499280686", "ideatesting104@myntra.com", "111020", "Invalid App", "ERROR" };
		
		String[] arr7 = new String[]{ ENVIRONMENT_FOX7, "myntra", generateRandomMobileNumber(), "55239ab59dba@yahoo.com", "111004", "Account Doesn't exists", "ERROR" };
		String[] arr8 = new String[]{ ENVIRONMENT_FOX7, "nonmyntra", "9789645123", "ideatesting400@myntra.com", "111020", "Invalid App", "ERROR" };
		
		String[] arr9 = new String[]{ ENVIRONMENT_FOX8, "myntra", generateRandomMobileNumber(), "55239ab59dba@yahoo.com", "111004", "Account Doesn't exists", "ERROR" };
		String[] arr10 = new String[]{ ENVIRONMENT_FOX8, "nonmyntra", "9789645123", "ideatesting400@myntra.com", "111020", "Invalid App", "ERROR" };
		
        Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4, /*arr5,*/ arr6,arr7,arr8,arr9,arr10 };
        return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 1, 2);
    }
	
	@DataProvider
    public Object[][] IdeaApiDP_addPhoneNumberToUserProfile_verifyNodeValues(ITestContext testContext)
	{
		String[] arr1 = new String[]{ ENVIRONMENT_FUNC, "myntra", generateRandomMobileNumber(), "ideatesting100", "IdeaTesting", "UserNo100", "ideatesting100@myntra.com",
				"9099414062", "MALE", "1981-03-11", "SUCCESS" };
		String[] arr2 = new String[]{ ENVIRONMENT_FUNC, "myntra", generateRandomMobileNumber(), "ideatesting101", "IdeaTesting", "UserNo101", "ideatesting101@myntra.com", 
				"9928286747", "FEMALE", "1982-04-21", "SUCCESS" };
		String[] arr3 = new String[]{ ENVIRONMENT_FUNC, "myntra", generateRandomMobileNumber(), "ideatesting102", "IdeaTesting", "UserNo102", "ideatesting102@myntra.com", 
				"9127963529", "MALE", "1983-05-30", "SUCCESS" };
		String[] arr4 = new String[]{ ENVIRONMENT_FUNC, "myntra", generateRandomMobileNumber(), "ideatesting103", "IdeaTesting", "UserNo103", "ideatesting103@myntra.com", 
				"9609690960", "FEMALE", "1984-06-09", "SUCCESS" };
		String[] arr5 = new String[]{ ENVIRONMENT_FUNC, "myntra", generateRandomMobileNumber(), "ideatesting104", "IdeaTesting", "UserNo104", "ideatesting104@myntra.com", 
				"9499280686", "MALE", "1985-07-02", "SUCCESS" };
		
		String[] arr6 = new String[]{ ENVIRONMENT_FOX7, "myntra", generateRandomMobileNumber(), "ideatesting400", "main", "user", "ideatesting400@myntra.com", 
				"9789645123", "MALE", "1991-03-06", "SUCCESS" };
		String[] arr7 = new String[]{ ENVIRONMENT_FOX7, "myntra", generateRandomMobileNumber(), "ideatesting400", "main", "user", "ideatesting401@myntra.com", 
				"9789645124", "MALE", "1991-03-06", "SUCCESS" };
		
		String[] arr8 = new String[]{ ENVIRONMENT_FOX8, "myntra", generateRandomMobileNumber(), "ideatesting400", "main", "user", "ideatesting400@myntra.com", 
				"9789645123", "MALE", "1991-03-06", "SUCCESS" };
		String[] arr9 = new String[]{ ENVIRONMENT_FOX8, "myntra", generateRandomMobileNumber(), "ideatesting400", "main", "user", "ideatesting401@myntra.com", 
				"9789645124", "MALE", "1991-03-06", "SUCCESS" };
        
        Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4, arr5,arr6,arr7,arr8,arr9 };
        return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 1, 2);
    }
	
	@DataProvider
    public Object[][] IdeaApiDP_addPhoneNumberToUserProfile_verifyResponseDataNodesUsingSchemaValidations(ITestContext testContext)
	{
		String[] arr1 = new String[]{ ENVIRONMENT_FUNC, "myntra", generateRandomMobileNumber(), "ideatesting100", "IdeaTesting", "UserNo100", "ideatesting100@myntra.com",
				"9099414062", "MALE", "1981-03-11", "SUCCESS" };
		String[] arr2 = new String[]{ ENVIRONMENT_FUNC, "myntra", generateRandomMobileNumber(), "ideatesting101", "IdeaTesting", "UserNo101", "ideatesting101@myntra.com", 
				"9928286747", "FEMALE", "1982-04-21", "SUCCESS" };
		String[] arr3 = new String[]{ ENVIRONMENT_FUNC, "myntra", generateRandomMobileNumber(), "ideatesting102", "IdeaTesting", "UserNo102", "ideatesting102@myntra.com", 
				"9127963529", "MALE", "1983-05-30", "SUCCESS" };
		String[] arr4 = new String[]{ ENVIRONMENT_FUNC, "myntra", generateRandomMobileNumber(), "ideatesting103", "IdeaTesting", "UserNo103", "ideatesting103@myntra.com", 
				"9609690960", "FEMALE", "1984-06-09", "SUCCESS" };
		String[] arr5 = new String[]{ ENVIRONMENT_FUNC, "myntra", generateRandomMobileNumber(), "ideatesting104", "IdeaTesting", "UserNo104", "ideatesting104@myntra.com", 
				"9499280686", "MALE", "1985-07-02", "SUCCESS" };
		
		String[] arr6 = new String[]{ ENVIRONMENT_FOX7, "myntra", generateRandomMobileNumber(), "ideatesting400", "main", "user", "ideatesting400@myntra.com", 
				"9789645123", "MALE", "1991-03-06", "SUCCESS" };
		String[] arr7 = new String[]{ ENVIRONMENT_FOX7, "myntra", generateRandomMobileNumber(), "ideatesting400", "main", "user", "ideatesting401@myntra.com", 
				"9789645124", "MALE", "1991-03-06", "SUCCESS" };
		
		String[] arr8 = new String[]{ ENVIRONMENT_FOX8, "myntra", generateRandomMobileNumber(), "ideatesting400", "main", "user", "ideatesting400@myntra.com", 
				"9789645123", "MALE", "1991-03-06", "SUCCESS" };
		String[] arr9 = new String[]{ ENVIRONMENT_FOX8, "myntra", generateRandomMobileNumber(), "ideatesting400", "main", "user", "ideatesting401@myntra.com", 
				"9789645124", "MALE", "1991-03-06", "SUCCESS" };
        
        Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4, arr5,arr6,arr7,arr8,arr9 };
        return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 1, 2);
    }
	
	@DataProvider
    public Object[][] IdeaApiDP_signUp_validateAPIResponse(ITestContext testContext)
	{
		String[] arr1 = new String[]{ ENVIRONMENT_FUNC, "myntra", "testp123", "Testing", "User", "abc.def@xyz.com", generateRandomMobileNumber(), "MALE", 
				"1991-03-06", "200" };
		String[] arr2 = new String[]{ ENVIRONMENT_FUNC, "myntra", "testp456", "Testing", "User", generateRandomId()+"@myntra.com", generateRandomMobileNumber(), "FEMALE", 
				"1993-04-12", "200" };
		String[] arr3 = new String[]{ ENVIRONMENT_FUNC, "myntra", "testp987", "Testing", "User", generateRandomId()+"@rediffmail.com", generateRandomMobileNumber(), "MALE", 
				"1996-02-11", "200" };
		String[] arr4 = new String[]{ ENVIRONMENT_FUNC, "myntra", "testp321", "Testing", "User", generateRandomId()+"@yahoo.com", generateRandomMobileNumber(), "FEMALE", 
				"1998-09-17", "200" };
		String[] arr5 = new String[]{ ENVIRONMENT_FUNC, "myntra", "testp122", "Testing", "User", generateRandomId()+"@outlook.com", generateRandomMobileNumber(), "MALE", 
				"1985-01-21", "200" };
		
		String[] arr6 = new String[]{ ENVIRONMENT_FOX7, "myntra", "testp321", "Testing", "User", generateRandomId()+"@yahoo.com", generateRandomMobileNumber(), "FEMALE", 
				"1998-09-17", "200" };
		String[] arr7 = new String[]{ ENVIRONMENT_FOX7, "myntra", "testp122", "Testing", "User", generateRandomId()+"@outlook.com", generateRandomMobileNumber(), "MALE", 
				"1985-01-21", "200" };
		
		String[] arr8 = new String[]{ ENVIRONMENT_FOX8, "myntra", "testp321", "Testing", "User", generateRandomId()+"@yahoo.com", generateRandomMobileNumber(), "FEMALE", 
				"1998-09-17", "200" };
		String[] arr9 = new String[]{ ENVIRONMENT_FOX8, "myntra", "testp122", "Testing", "User", generateRandomId()+"@outlook.com", generateRandomMobileNumber(), "MALE", 
				"1985-01-21", "200" };
		
        Object[][] dataSet = new Object[][]{ arr1,arr2,arr3,arr4,arr5,arr6,arr7,arr8,arr9};
        return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 1, 2);
    }
	
	@DataProvider
    public Object[][] IdeaApiDP_signUp_verifySuccessStatusInformation(ITestContext testContext)
	{
		String[] arr1 = new String[]{ ENVIRONMENT_FUNC, "myntra", "testp123", "Testing", "User", generateRandomId()+"@gmail.com", generateRandomMobileNumber(), "MALE", 
				"1991-03-06", "3", "Success", "SUCCESS" };
		String[] arr2 = new String[]{ ENVIRONMENT_FUNC, "myntra", "testp456", "Testing", "User", generateRandomId()+"@myntra.com", generateRandomMobileNumber(), "FEMALE", 
				"1993-04-12", "3", "Success", "SUCCESS" };
		String[] arr3 = new String[]{ ENVIRONMENT_FUNC, "myntra", "testp987", "Testing", "User", generateRandomId()+"@rediffmail.com", generateRandomMobileNumber(), "MALE", 
				"1996-02-11", "3", "Success", "SUCCESS" };
		String[] arr4 = new String[]{ ENVIRONMENT_FUNC, "myntra", "testp321", "Testing", "User", generateRandomId()+"@yahoo.com", generateRandomMobileNumber(), "FEMALE", 
				"1998-09-17", "3", "Success", "SUCCESS" };
		String[] arr5 = new String[]{ ENVIRONMENT_FUNC, "myntra", "testp122", "Testing", "User", generateRandomId()+"@outlook.com", generateRandomMobileNumber(), "MALE", 
				"1985-01-21", "3", "Success", "SUCCESS" };
		
		String[] arr6 = new String[]{ ENVIRONMENT_FOX7, "myntra", "testp321", "Testing", "User", generateRandomId()+"@yahoo.com", generateRandomMobileNumber(), "FEMALE", 
				"1998-09-17", "3", "Success", "SUCCESS" };
		String[] arr7 = new String[]{ ENVIRONMENT_FOX7, "myntra", "testp122", "Testing", "User", generateRandomId()+"@outlook.com", generateRandomMobileNumber(), "MALE", 
				"1985-01-21", "3", "Success", "SUCCESS" };
		
		String[] arr8 = new String[]{ ENVIRONMENT_FOX8, "myntra", "testp321", "Testing", "User", generateRandomId()+"@yahoo.com", generateRandomMobileNumber(), "FEMALE", 
				"1998-09-17", "3", "Success", "SUCCESS" };
		String[] arr9 = new String[]{ ENVIRONMENT_FOX8, "myntra", "testp122", "Testing", "User", generateRandomId()+"@outlook.com", generateRandomMobileNumber(), "MALE", 
				"1985-01-21", "3", "Success", "SUCCESS" };
		
        Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4, arr5,arr6,arr7,arr8,arr9 };
        return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 1, 2);
    }
	
	@DataProvider
    public Object[][] IdeaApiDP_signUp_verifyFailureStatusInformation(ITestContext testContext)
	{
		String[] arr1 = new String[]{ ENVIRONMENT_FUNC, "myntra", "testp123", "Testing", "User", "testuser456@myntra.com", "9099414062", "MALE", "1988-03-06", "111010", 
				"Registration Failed", "ERROR", "111014" };
		String[] arr2 = new String[]{ ENVIRONMENT_FUNC, "myntra", "testp123", "Testing", "User", "test%us@er456$", "9099414062", "MALE", "1988-03-06", "111010", 
				"Registration Failed", "ERROR", "111012" };
		String[] arr3 = new String[]{ ENVIRONMENT_FUNC, "myntra", "testp123", "Testing", "User", "", "9099414062", "MALE", "1988-03-06", "111010", 
				"Registration Failed", "ERROR", "111012" };
		String[] arr4 = new String[]{ ENVIRONMENT_FUNC, "nonmyntra", "testp123", "Testing", "User", "testuser981@myntra.com", "9099414062", "MALE", "1988-03-06", "111020", 
				"Invalid App", "ERROR", "111020" };
		String[] arr5 = new String[]{ ENVIRONMENT_FUNC, "", "testp123", "Testing", "User", "testuser982@myntra.com", "9099414062", "MALE", "1988-03-06", "111020", 
				"Invalid App", "ERROR", "111020" };
		String[] arr6 = new String[]{ ENVIRONMENT_FUNC, "myntra", "", "Testing", "User", "testuser983@myntra.com", "9099414062", "MALE", "1988-03-06", "112000", 
				"Invalid password format", "ERROR", "" };
		String[] arr7 = new String[]{ ENVIRONMENT_FUNC, "myntra", "mypassword", "", "User", "testuser984@myntra.com",  "9099414062", "MALE", "1988-03-06", "111010", 
				"Registration Failed", "ERROR", "111014" };
		String[] arr8 = new String[]{ ENVIRONMENT_FUNC, "myntra", "testp123", "Testing", "User", "testuser985@myntra.com", "", "MALE", "1988-03-06", "111010", 
				"Registration Failed", "ERROR", "111013" };
		String[] arr9 = new String[]{ ENVIRONMENT_FUNC, "myntra", "testp123", "Testing", "User", "testuser985@myntra.com", "9099414062232123", "MALE", "1988-03-06", "111010", 
				"Registration Failed", "ERROR", "111013" };
		String[] arr10 = new String[]{ ENVIRONMENT_FUNC, "myntra", "testp123", "Testing", "User", "testuser782@myntra.com", "9099414007", "INVALID", "1988-03-06", "51", 
				"Generic Exception", "ERROR", "" };
		String[] arr11 = new String[]{ ENVIRONMENT_FUNC, "myntra", "testp123", "Testing", "User", "testuser680@myntra.com", "9099414062", "MALE", "9099414062232123", "51", 
				"Generic Exception", "ERROR", "" };
		
		String[] arr12 = new String[]{ ENVIRONMENT_FOX7, "myntra", "testp123", "Testing", "User", "testuser782@myntra.com", "9099414007", "INVALID", "1988-03-06", "51", 
				"Generic Exception", "ERROR", "" };
		String[] arr13 = new String[]{ ENVIRONMENT_FOX7, "myntra", "testp123", "Testing", "User", "testuser680@myntra.com", "9099414062", "MALE", "9099414062232123", "51", 
				"Generic Exception", "ERROR", "" };
		
		String[] arr14 = new String[]{ ENVIRONMENT_FOX8, "myntra", "testp123", "Testing", "User", "testuser782@myntra.com", "9099414007", "INVALID", "1988-03-06", "51", 
				"Generic Exception", "ERROR", "" };
		String[] arr15 = new String[]{ ENVIRONMENT_FOX8, "myntra", "testp123", "Testing", "User", "testuser680@myntra.com", "9099414062", "MALE", "9099414062232123", "51", 
				"Generic Exception", "ERROR", "" };
		
        Object[][] dataSet = new Object[][]{ arr1,arr2,arr3,arr4,arr5,arr6,arr7,arr8,arr9,arr10,arr11,arr12,arr13,arr14,arr15};
        return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 1, 2);
    }
	
	@DataProvider
    public Object[][] IdeaApiDP_signUp_verifyNodeValues(ITestContext testContext)
	{
		String[] arr1 = new String[]{ ENVIRONMENT_FUNC, "myntra", "testp123", "Testing", "User", generateRandomId()+"@gmail.com", generateRandomMobileNumber(), "MALE", 
				"1991-03-06", "SUCCESS" };
		String[] arr2 = new String[]{ ENVIRONMENT_FUNC, "myntra", "testp456", "Testing", "User", generateRandomId()+"@myntra.com", generateRandomMobileNumber(), "FEMALE", 
				"1993-04-12", "SUCCESS" };
		String[] arr3 = new String[]{ ENVIRONMENT_FUNC, "myntra", "testp987", "Testing", "User", generateRandomId()+"@rediffmail.com", generateRandomMobileNumber(), "MALE", 
				"1996-02-11", "SUCCESS" };
		String[] arr4 = new String[]{ ENVIRONMENT_FUNC, "myntra", "testp321", "Testing", "User", generateRandomId()+"@yahoo.com", generateRandomMobileNumber(), "FEMALE", 
				"1998-09-17", "SUCCESS" };
		String[] arr5 = new String[]{ ENVIRONMENT_FUNC, "myntra", "testp122", "Testing", "User", generateRandomId()+"@outlook.com", generateRandomMobileNumber(), "MALE", 
				"1985-01-21", "SUCCESS" };
		
		String[] arr6 = new String[]{ ENVIRONMENT_FOX7, "myntra", "testp321", "Testing", "User", generateRandomId()+"@yahoo.com", generateRandomMobileNumber(), "FEMALE", 
				"1998-09-17", "SUCCESS" };
		String[] arr7 = new String[]{ ENVIRONMENT_FOX7, "myntra", "testp122", "Testing", "User", generateRandomId()+"@outlook.com", generateRandomMobileNumber(), "MALE", 
				"1985-01-21", "SUCCESS" };
		
		String[] arr8 = new String[]{ ENVIRONMENT_FOX8, "myntra", "testp321", "Testing", "User", generateRandomId()+"@yahoo.com", generateRandomMobileNumber(), "FEMALE", 
				"1998-09-17", "SUCCESS" };
		String[] arr9 = new String[]{ ENVIRONMENT_FOX8, "myntra", "testp122", "Testing", "User", generateRandomId()+"@outlook.com", generateRandomMobileNumber(), "MALE", 
				"1985-01-21", "SUCCESS" };
		
        Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4, arr5,arr6,arr7,arr8,arr9 };
        return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 1, 2);
    }
	
	@DataProvider
    public Object[][] IdeaApiDP_signUp_verifyDOBNodeValues(ITestContext testContext)
	{
		String[] arr1 = new String[]{ ENVIRONMENT_FUNC, "myntra", "testp123", "Testing", "User", generateRandomId()+"@gmail.com", generateRandomMobileNumber(), "MALE", 
				"1991-03-06", "SUCCESS" };
		String[] arr2 = new String[]{ ENVIRONMENT_FUNC, "myntra", "testp456", "Testing", "User", generateRandomId()+"@myntra.com", generateRandomMobileNumber(), "FEMALE", 
				"1993-04-12", "SUCCESS" };
		String[] arr3 = new String[]{ ENVIRONMENT_FUNC, "myntra", "testp987", "Testing", "User", generateRandomId()+"@rediffmail.com", generateRandomMobileNumber(), "MALE", 
				"1996-02-11", "SUCCESS" };
		String[] arr4 = new String[]{ ENVIRONMENT_FUNC, "myntra", "testp321", "Testing", "User", generateRandomId()+"@yahoo.com", generateRandomMobileNumber(), "FEMALE", 
				"1998-09-17", "SUCCESS" };
		String[] arr5 = new String[]{ ENVIRONMENT_FUNC, "myntra", "testp122", "Testing", "User", generateRandomId()+"@outlook.com", generateRandomMobileNumber(), "MALE", 
				"1985-01-21", "SUCCESS" };
		
		String[] arr6 = new String[]{ ENVIRONMENT_FOX7, "myntra", "testp321", "Testing", "User", generateRandomId()+"@yahoo.com", generateRandomMobileNumber(), "FEMALE", 
				"1998-09-17", "SUCCESS" };
		String[] arr7 = new String[]{ ENVIRONMENT_FOX7, "myntra", "testp122", "Testing", "User", generateRandomId()+"@outlook.com", generateRandomMobileNumber(), "MALE", 
				"1985-01-21", "SUCCESS" };
		
		String[] arr8 = new String[]{ ENVIRONMENT_FOX8, "myntra", "testp321", "Testing", "User", generateRandomId()+"@yahoo.com", generateRandomMobileNumber(), "FEMALE", 
				"1998-09-17", "SUCCESS" };
		String[] arr9 = new String[]{ ENVIRONMENT_FOX8, "myntra", "testp122", "Testing", "User", generateRandomId()+"@outlook.com", generateRandomMobileNumber(), "MALE", 
				"1985-01-21", "SUCCESS" };
		
        Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4, arr5,arr6,arr7,arr8,arr9 };
        return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 1, 2);
    }
	
	@DataProvider
    public Object[][] IdeaApiDP_signUp_verifyEmailDetailsNodeValues(ITestContext testContext)
	{
		String[] arr1 = new String[]{ ENVIRONMENT_FUNC, "myntra", "testp123", "Testing", "User", generateRandomId()+"@gmail.com", generateRandomMobileNumber(), "MALE", 
				"1991-03-06", "SUCCESS" };
		String[] arr2 = new String[]{ ENVIRONMENT_FUNC, "myntra", "testp456", "Testing", "User", generateRandomId()+"@myntra.com", generateRandomMobileNumber(), "FEMALE", 
				"1993-04-12", "SUCCESS" };
		String[] arr3 = new String[]{ ENVIRONMENT_FUNC, "myntra", "testp987", "Testing", "User", generateRandomId()+"@rediffmail.com", generateRandomMobileNumber(), "MALE", 
				"1996-02-11", "SUCCESS" };
		String[] arr4 = new String[]{ ENVIRONMENT_FUNC, "myntra", "testp321", "Testing", "User", generateRandomId()+"@yahoo.com", generateRandomMobileNumber(), "FEMALE", 
				"1998-09-17", "SUCCESS" };
		String[] arr5 = new String[]{ ENVIRONMENT_FUNC, "myntra", "testp122", "Testing", "User", generateRandomId()+"@outlook.com", generateRandomMobileNumber(), "MALE", 
				"1985-01-21", "SUCCESS" };
		
		String[] arr6 = new String[]{ ENVIRONMENT_FOX7, "myntra", "testp321", "Testing", "User", generateRandomId()+"@yahoo.com", generateRandomMobileNumber(), "FEMALE", 
				"1998-09-17", "SUCCESS" };
		String[] arr7 = new String[]{ ENVIRONMENT_FOX7, "myntra", "testp122", "Testing", "User", generateRandomId()+"@outlook.com", generateRandomMobileNumber(), "MALE", 
				"1985-01-21", "SUCCESS" };
		
		String[] arr8 = new String[]{ ENVIRONMENT_FOX8, "myntra", "testp321", "Testing", "User", generateRandomId()+"@yahoo.com", generateRandomMobileNumber(), "FEMALE", 
				"1998-09-17", "SUCCESS" };
		String[] arr9 = new String[]{ ENVIRONMENT_FOX8, "myntra", "testp122", "Testing", "User", generateRandomId()+"@outlook.com", generateRandomMobileNumber(), "MALE", 
				"1985-01-21", "SUCCESS" };
		
        Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4, arr5,arr6,arr7,arr8,arr9 };
        return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 1, 2);
    }
	
	@DataProvider
    public Object[][] IdeaApiDP_signUp_verifyPhoneDetailsNodeValues(ITestContext testContext)
	{
		String[] arr1 = new String[]{ ENVIRONMENT_FUNC, "myntra", "testp123", "Testing", "User", generateRandomId()+"@gmail.com", generateRandomMobileNumber(), "MALE", 
				"1991-03-06", "SUCCESS" };
		String[] arr2 = new String[]{ ENVIRONMENT_FUNC, "myntra", "testp456", "Testing", "User", generateRandomId()+"@myntra.com", generateRandomMobileNumber(), "FEMALE", 
				"1993-04-12", "SUCCESS" };
		String[] arr3 = new String[]{ ENVIRONMENT_FUNC, "myntra", "testp987", "Testing", "User", generateRandomId()+"@rediffmail.com", generateRandomMobileNumber(), "MALE", 
				"1996-02-11", "SUCCESS" };
		String[] arr4 = new String[]{ ENVIRONMENT_FUNC, "myntra", "testp321", "Testing", "User", generateRandomId()+"@yahoo.com", generateRandomMobileNumber(), "FEMALE", 
				"1998-09-17", "SUCCESS" };
		String[] arr5 = new String[]{ ENVIRONMENT_FUNC, "myntra", "testp122", "Testing", "User", generateRandomId()+"@outlook.com", generateRandomMobileNumber(), "MALE", 
				"1985-01-21", "SUCCESS" };
		
		String[] arr6 = new String[]{ ENVIRONMENT_FOX7, "myntra", "testp321", "Testing", "User", generateRandomId()+"@yahoo.com", generateRandomMobileNumber(), "FEMALE", 
				"1998-09-17", "SUCCESS" };
		String[] arr7 = new String[]{ ENVIRONMENT_FOX7, "myntra", "testp122", "Testing", "User", generateRandomId()+"@outlook.com", generateRandomMobileNumber(), "MALE", 
				"1985-01-21", "SUCCESS" };
		
		String[] arr8 = new String[]{ ENVIRONMENT_FOX8, "myntra", "testp321", "Testing", "User", generateRandomId()+"@yahoo.com", generateRandomMobileNumber(), "FEMALE", 
				"1998-09-17", "SUCCESS" };
		String[] arr9 = new String[]{ ENVIRONMENT_FOX8, "myntra", "testp122", "Testing", "User", generateRandomId()+"@outlook.com", generateRandomMobileNumber(), "MALE", 
				"1985-01-21", "SUCCESS" };
		
        Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4, arr5,arr6,arr7,arr8,arr9 };
        return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 1, 2);
    }
	
	@DataProvider
    public Object[][] IdeaApiDP_signUp_verifyResponseDataNodesUsingSchemaValidations(ITestContext testContext)
	{
		String[] arr1 = new String[]{ ENVIRONMENT_FUNC, "myntra", "testp123", "Testing", "User", generateRandomId()+"@gmail.com", generateRandomMobileNumber(), "MALE", 
				"1991-03-06", "SUCCESS" };
		String[] arr2 = new String[]{ ENVIRONMENT_FUNC, "myntra", "testp456", "Testing", "User", generateRandomId()+"@myntra.com", generateRandomMobileNumber(), "FEMALE", 
				"1993-04-12", "SUCCESS" };
		String[] arr3 = new String[]{ ENVIRONMENT_FUNC, "myntra", "testp987", "Testing", "User", generateRandomId()+"@rediffmail.com", generateRandomMobileNumber(), "MALE", 
				"1996-02-11", "SUCCESS" };
		String[] arr4 = new String[]{ ENVIRONMENT_FUNC, "myntra", "testp321", "Testing", "User", generateRandomId()+"@yahoo.com", generateRandomMobileNumber(), "FEMALE", 
				"1998-09-17", "SUCCESS" };
		String[] arr5 = new String[]{ ENVIRONMENT_FUNC, "myntra", "testp122", "Testing", "User", generateRandomId()+"@outlook.com", generateRandomMobileNumber(), "MALE", 
				"1985-01-21", "SUCCESS" };
		
		String[] arr6 = new String[]{ ENVIRONMENT_FOX7, "myntra", "testp321", "Testing", "User", generateRandomId()+"@yahoo.com", generateRandomMobileNumber(), "FEMALE", 
				"1998-09-17", "SUCCESS" };
		String[] arr7 = new String[]{ ENVIRONMENT_FOX7, "myntra", "testp122", "Testing", "User", generateRandomId()+"@outlook.com", generateRandomMobileNumber(), "MALE", 
				"1985-01-21", "SUCCESS" };
		
		String[] arr8 = new String[]{ ENVIRONMENT_FOX8, "myntra", "testp321", "Testing", "User", generateRandomId()+"@yahoo.com", generateRandomMobileNumber(), "FEMALE", 
				"1998-09-17", "SUCCESS" };
		String[] arr9 = new String[]{ ENVIRONMENT_FOX8, "myntra", "testp122", "Testing", "User", generateRandomId()+"@outlook.com", generateRandomMobileNumber(), "MALE", 
				"1985-01-21", "SUCCESS" };
		
        Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4, arr5 ,arr6,arr7,arr8,arr9};
        return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 1, 2);
    }
	
	@DataProvider
    public Object[][] IdeaApiDP_signInUsingPhoneNumber_validateAPIResponse(ITestContext testContext)
	{
		String[] arr1 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting100", "9554888018", "200" };
		String[] arr2 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting101", "9928286747", "200" };
		String[] arr3 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting102", "9127963529", "200" };
		String[] arr4 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting103", "9609690960", "200" };
		String[] arr5 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting104", "9499280686", "200" };
		
		String[] arr6 = new String[]{ ENVIRONMENT_FOX7, "myntra", "ideatesting400", "9789645123", "200" };
		String[] arr7 = new String[]{ ENVIRONMENT_FOX7, "myntra", "ideatesting401", "9789645124", "200" };
		
		String[] arr8 = new String[]{ ENVIRONMENT_FOX8, "myntra", "ideatesting400", "9789645123", "200" };
		String[] arr9 = new String[]{ ENVIRONMENT_FOX8, "myntra", "ideatesting401", "9789645124", "200" };
        
        Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4, arr5,arr6,arr7,arr8,arr9 };
        return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 1, 2);
    }
	
	@DataProvider
    public Object[][] IdeaApiDP_signInUsingPhoneNumber_verifySuccessStatusInformation(ITestContext testContext)
	{
		String[] arr1 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting300", "IdeaTesting", "UserNo100", "ideatesting300@myntra.com", "9448452348", "MALE", 
				"1981-03-11", "3", "Success", "SUCCESS" };
		String[] arr2 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting101", "IdeaTesting", "UserNo101", "ideatesting101@myntra.com", "9928286747", "FEMALE", 
				"1982-04-21", "3", "Success", "SUCCESS" };
		String[] arr3 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting102", "IdeaTesting", "UserNo102", "ideatesting102@myntra.com", "9127963529", "MALE", 
				"1983-05-30", "3", "Success", "SUCCESS" };
		String[] arr4 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting103", "IdeaTesting", "UserNo103", "ideatesting103@myntra.com", "9609690960", "FEMALE",
				"1984-06-09", "3", "Success", "SUCCESS" };
		String[] arr5 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting104", "IdeaTesting", "UserNo104", "ideatesting104@myntra.com", "9499280686", "MALE", 
				"1985-07-02", "3", "Success", "SUCCESS" };
		
		String[] arr6 = new String[]{ ENVIRONMENT_FOX7, "myntra", "ideatesting", "main", "user", "ideatesting400@myntra.com", "9789645123", "MALE",
				"1991-03-06", "3", "Success", "SUCCESS" };
		String[] arr7 = new String[]{ ENVIRONMENT_FOX7, "myntra", "ideatesting", "main", "user", "ideatesting401@myntra.com", "9789645124", "MALE", 
				"1991-03-06", "3", "Success", "SUCCESS" };
		
		String[] arr8 = new String[]{ ENVIRONMENT_FOX8, "myntra", "ideatesting", "main", "user", "ideatesting400@myntra.com", "9789645123", "MALE",
				"1991-03-06", "3", "Success", "SUCCESS" };
		String[] arr9 = new String[]{ ENVIRONMENT_FOX8, "myntra", "ideatesting", "main", "user", "ideatesting401@myntra.com", "9789645124", "MALE", 
				"1991-03-06", "3", "Success", "SUCCESS" };
		
        Object[][] dataSet = new Object[][]{ arr1,arr2,arr3,arr4,arr5,arr6,arr7,arr8,arr9};
        return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 1, 2);
    }
	public int generateRandomNumber(){
		int num=0;
		
		return num;
	}
	@DataProvider
    public Object[][] IdeaApiDP_signInUsingPhoneNumber_verifyFailureStatusInformation(ITestContext testContext)
	{
		String[] arr1 = new String[]{ ENVIRONMENT_FUNC, "myntra", "mypass123", "903581201301", "111004", "Account Doesn't exists", "ERROR" };
		String[] arr2 = new String[]{ ENVIRONMENT_FUNC, "myntra", "mypass123", "9099414062", "111011", "Authentication Failed", "ERROR" };
		
		String[] arr3 = new String[]{ ENVIRONMENT_FUNC, "myntra", "", "9928286747", "111011", "Authentication Failed", "ERROR" };
		String[] arr4 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting101", "", "111004", "Account Doesn't exists", "ERROR" };

		String[] arr5 = new String[]{ ENVIRONMENT_FUNC, "myntra", "", "", "111004", "Account Doesn't exists", "ERROR" };

		String[] arr6 = new String[]{ ENVIRONMENT_FUNC, "", "ideatesting101", "9928286747",  "111020", "Invalid App", "ERROR" };
		String[] arr7 = new String[]{ ENVIRONMENT_FUNC, "nonmyntra", "ideatesting101", "9928286747",  "111020", "Invalid App", "ERROR" };
		
		String[] arr8 = new String[]{ ENVIRONMENT_FOX7, "", "ideatesting101", "9928286747",  "111020", "Invalid App", "ERROR" };
		String[] arr9 = new String[]{ ENVIRONMENT_FOX7, "nonmyntra", "ideatesting101", "9928286747",  "111020", "Invalid App", "ERROR" };
		
		String[] arr10 = new String[]{ ENVIRONMENT_FOX8, "", "ideatesting101", "9928286747",  "111020", "Invalid App", "ERROR" };
		String[] arr11 = new String[]{ ENVIRONMENT_FOX8, "nonmyntra", "ideatesting101", "9928286747",  "111020", "Invalid App", "ERROR" };
		
        Object[][] dataSet = new Object[][]{ arr1,arr2,arr3,arr4,arr5,arr6,arr7,arr8,arr9,arr10,arr11 };
        return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 1, 2);
    }
	
	@DataProvider
    public Object[][] IdeaApiDP_signInUsingPhoneNumber_verifyNodeValues(ITestContext testContext)
	{
		String[] arr1 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting100", "IdeaTesting", "UserNo100", "ideatesting100@myntra.com", "9099414062", "MALE", 
				"1981-03-11", "SUCCESS" };
		String[] arr2 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting101", "IdeaTesting", "UserNo101", "ideatesting101@myntra.com", "9928286747", "FEMALE", 
				"1982-04-21", "SUCCESS" };
		String[] arr3 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting102", "IdeaTesting", "UserNo102", "ideatesting102@myntra.com", "9127963529", "MALE", 
				"1983-05-30", "SUCCESS" };
		String[] arr4 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting103", "IdeaTesting", "UserNo103", "ideatesting103@myntra.com", "9609690960", "FEMALE",
				"1984-06-09", "SUCCESS" };
		String[] arr5 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting104", "IdeaTesting", "UserNo104", "ideatesting104@myntra.com", "9499280686", "MALE", 
				"1985-07-02", "SUCCESS" };
		
		String[] arr6 = new String[]{ ENVIRONMENT_FOX7, "myntra", "ideatesting400", "main", "user", "ideatesting400@myntra.com", "9789645123", "MALE",
				"1991-03-06", "SUCCESS" };
		String[] arr7 = new String[]{ ENVIRONMENT_FOX7, "myntra", "ideatesting400", "main", "user", "ideatesting401@myntra.com", "9789645124", "MALE", 
				"1991-03-06", "SUCCESS" };
		
		String[] arr8 = new String[]{ ENVIRONMENT_FOX8, "myntra", "ideatesting400", "main", "user", "ideatesting400@myntra.com", "9789645123", "MALE",
				"1991-03-06", "SUCCESS" };
		String[] arr9 = new String[]{ ENVIRONMENT_FOX8, "myntra", "ideatesting400", "main", "user", "ideatesting401@myntra.com", "9789645124", "MALE", 
				"1991-03-06", "SUCCESS" };
		
        Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4, arr5,arr6,arr7,arr8,arr9 };
        return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 1, 2);
    }
	
	@DataProvider
    public Object[][] IdeaApiDP_signInUsingPhoneNumber_verifyResponseDataNodesUsingSchemaValidations(ITestContext testContext)
	{
		String[] arr1 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting100", "IdeaTesting", "UserNo100", "ideatesting100@myntra.com", "9099414062", "MALE", 
				"1981-03-11", "SUCCESS" };
		String[] arr2 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting101", "IdeaTesting", "UserNo101", "ideatesting101@myntra.com", "9928286747", "FEMALE", 
				"1982-04-21", "SUCCESS" };
		String[] arr3 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting102", "IdeaTesting", "UserNo102", "ideatesting102@myntra.com", "9127963529", "MALE", 
				"1983-05-30", "SUCCESS" };
		String[] arr4 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting103", "IdeaTesting", "UserNo103", "ideatesting103@myntra.com", "9609690960", "FEMALE",
				"1984-06-09", "SUCCESS" };
		String[] arr5 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting104", "IdeaTesting", "UserNo104", "ideatesting104@myntra.com", "9499280686", "MALE", 
				"1985-07-02", "SUCCESS" };
		
		String[] arr6 = new String[]{ ENVIRONMENT_FOX7, "myntra", "ideatesting400", "main", "user", "ideatesting400@myntra.com", "9789645123", "MALE",
				"1991-03-06", "SUCCESS" };
		String[] arr7 = new String[]{ ENVIRONMENT_FOX7, "myntra", "ideatesting400", "main", "user", "ideatesting401@myntra.com", "9789645124", "MALE", 
				"1991-03-06", "SUCCESS" };
		
		String[] arr8 = new String[]{ ENVIRONMENT_FOX8, "myntra", "ideatesting400", "main", "user", "ideatesting400@myntra.com", "9789645123", "MALE",
				"1991-03-06", "SUCCESS" };
		String[] arr9 = new String[]{ ENVIRONMENT_FOX8, "myntra", "ideatesting400", "main", "user", "ideatesting401@myntra.com", "9789645124", "MALE", 
				"1991-03-06", "SUCCESS" };
		
        Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4, arr5,arr6,arr7,arr8,arr9 };
        return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 1, 2);
    }
	
	
	@DataProvider
    public Object[][] IdeaApiDP_signInUsingEmail_validateAPIResponse(ITestContext testContext)
	{
		String[] arr1 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting100", "ideatesting100@myntra.com", "200" };
		String[] arr2 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting101", "ideatesting101@myntra.com", "200" };
		String[] arr3 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting102", "ideatesting102@myntra.com", "200" };
		String[] arr4 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting103", "ideatesting103@myntra.com", "200" };
		String[] arr5 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting104", "ideatesting104@myntra.com", "200" };
		
		String[] arr6 = new String[]{ ENVIRONMENT_FOX7, "myntra", "ideatesting400", "ideatesting400@myntra.com", "200" };
		String[] arr7 = new String[]{ ENVIRONMENT_FOX7, "myntra", "ideatesting401", "ideatesting401@myntra.com", "200" };
		
		String[] arr8 = new String[]{ ENVIRONMENT_FOX8, "myntra", "ideatesting400", "ideatesting400@myntra.com", "200" };
		String[] arr9 = new String[]{ ENVIRONMENT_FOX8, "myntra", "ideatesting401", "ideatesting401@myntra.com", "200" };
		
        Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4, arr5,arr6,arr7,arr8,arr9 };
        return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 1, 2);
    }
	
	@DataProvider
    public Object[][] IdeaApiDP_signInUsingEmail_verifySuccessStatusInformation(ITestContext testContext)
	{
		String[] arr1 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting100", "IdeaTesting", "UserNo100", "ideatesting100@myntra.com", "9099414062", "MALE", 
				"1981-03-11", "3", "Success", "SUCCESS" };
		String[] arr2 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting101", "IdeaTesting", "UserNo101", "ideatesting101@myntra.com", "9928286747", "FEMALE", 
				"1982-04-21", "3", "Success", "SUCCESS" };
		String[] arr3 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting102", "IdeaTesting", "UserNo102", "ideatesting102@myntra.com", "9127963529", "MALE", 
				"1983-05-30", "3", "Success", "SUCCESS" };
		String[] arr4 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting103", "IdeaTesting", "UserNo103", "ideatesting103@myntra.com", "9609690960", "FEMALE",
				"1984-06-09", "3", "Success", "SUCCESS" };
		String[] arr5 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting104", "IdeaTesting", "UserNo104", "ideatesting104@myntra.com", "9499280686", "MALE", 
				"1985-07-02", "3", "Success", "SUCCESS" };
		
		String[] arr6 = new String[]{ ENVIRONMENT_FOX7, "myntra", "ideatesting400", "main", "user", "ideatesting400@myntra.com", "9789645123", "FEMALE",
				"1991-03-06", "3", "Success", "SUCCESS" };
		String[] arr7 = new String[]{ ENVIRONMENT_FOX7, "myntra", "ideatesting401", "main", "user", "ideatesting401@myntra.com", "9789645124", "MALE", 
				"1991-03-06", "3", "Success", "SUCCESS" };
		
		String[] arr8 = new String[]{ ENVIRONMENT_FOX8, "myntra", "ideatesting400", "main", "user", "ideatesting400@myntra.com", "9789645123", "FEMALE",
				"1991-03-06", "3", "Success", "SUCCESS" };
		String[] arr9 = new String[]{ ENVIRONMENT_FOX8, "myntra", "ideatesting401", "main", "user", "ideatesting401@myntra.com", "9789645124", "MALE", 
				"1991-03-06", "3", "Success", "SUCCESS" };
		
        Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4, arr5,arr6,arr7,arr8,arr9 };
        return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 1, 2);
    }
	
	@DataProvider
    public Object[][] IdeaApiDP_signInUsingEmail_verifyFailureStatusInformation(ITestContext testContext)
	{
		String[] arr1 = new String[]{ ENVIRONMENT_FUNC, "myntra", "mypass123", "ideatesting100@myntra.com", "111011", "Authentication Failed", "ERROR" };
		String[] arr2 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting101", "testing900@myntra.com", "111004", "Account Doesn't exists", "ERROR" };
		String[] arr3 = new String[]{ ENVIRONMENT_FUNC, "myntra", "", "ideatesting102@myntra.com", "111011", "Authentication Failed", "ERROR" };
		String[] arr4 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting103", "", "111004", "Account Doesn't exists", "ERROR" };
		String[] arr5 = new String[]{ ENVIRONMENT_FUNC, "", "ideatesting104", "ideatesting104@myntra.com", "111020", "Invalid App", "ERROR" };
		String[] arr6 = new String[]{ ENVIRONMENT_FUNC, "nonmyntra", "ideatesting104", "ideatesting104@myntra.com", "111020", "Invalid App", "ERROR" };
		
		String[] arr7 = new String[]{ ENVIRONMENT_FOX7, "", "ideatesting400", "ideatesting400@myntra.com", "111020", "Invalid App", "ERROR" };
		String[] arr8 = new String[]{ ENVIRONMENT_FOX7, "nonmyntra", "ideatesting401", "ideatesting401@myntra.com", "111020", "Invalid App", "ERROR" };
		
		String[] arr9 = new String[]{ ENVIRONMENT_FOX8, "", "ideatesting400", "ideatesting400@myntra.com", "111020", "Invalid App", "ERROR" };
		String[] arr10 = new String[]{ ENVIRONMENT_FOX8, "nonmyntra", "ideatesting401", "ideatesting401@myntra.com", "111020", "Invalid App", "ERROR" };
		
        Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4, arr5, arr6,arr7,arr8,arr9,arr10 };
        return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 1, 2);
    }
	
	@DataProvider
    public Object[][] IdeaApiDP_signInUsingEmail_verifyNodeValues(ITestContext testContext)
	{
		String[] arr1 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting100", "IdeaTesting", "UserNo100", "ideatesting100@myntra.com", "9099414062", "MALE", 
				"1981-03-11", "SUCCESS" };
		String[] arr2 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting101", "IdeaTesting", "UserNo101", "ideatesting101@myntra.com", "9928286747", "FEMALE", 
				"1982-04-21", "SUCCESS" };
		String[] arr3 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting102", "IdeaTesting", "UserNo102", "ideatesting102@myntra.com", "9127963529", "MALE", 
				"1983-05-30", "SUCCESS" };
		String[] arr4 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting103", "IdeaTesting", "UserNo103", "ideatesting103@myntra.com", "9609690960", "FEMALE",
				"1984-06-09", "SUCCESS" };
		String[] arr5 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting104", "IdeaTesting", "UserNo104", "ideatesting104@myntra.com", "9499280686", "MALE", 
				"1985-07-02", "SUCCESS" };
		
		String[] arr6 = new String[]{ ENVIRONMENT_FOX7, "myntra", "ideatesting400", "main", "user", "ideatesting400@myntra.com", "9789645123", "MALE",
				"1991-03-06", "SUCCESS" };
		String[] arr7 = new String[]{ ENVIRONMENT_FOX7, "myntra", "ideatesting401", "main", "user", "ideatesting401@myntra.com", "9789645124", "MALE", 
				"1991-03-06", "SUCCESS" };
		
		String[] arr8 = new String[]{ ENVIRONMENT_FOX8, "myntra", "ideatesting400", "main", "user", "ideatesting400@myntra.com", "9789645123", "MALE",
				"1991-03-06", "SUCCESS" };
		String[] arr9 = new String[]{ ENVIRONMENT_FOX8, "myntra", "ideatesting401", "main", "user", "ideatesting401@myntra.com", "9789645124", "MALE", 
				"1991-03-06", "SUCCESS" };
		
        Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4, arr5,arr6,arr7,arr8,arr9 };
        return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 1, 2);
    }
	
	@DataProvider
    public Object[][] IdeaApiDP_signInUsingEmail_verifyResponseDataNodesUsingSchemaValidations(ITestContext testContext)
	{
		String[] arr1 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting100", "IdeaTesting", "UserNo100", "ideatesting100@myntra.com", "9099414062", "MALE", 
				"1981-03-11", "SUCCESS" };
		String[] arr2 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting101", "IdeaTesting", "UserNo101", "ideatesting101@myntra.com", "9928286747", "FEMALE", 
				"1982-04-21", "SUCCESS" };
		String[] arr3 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting102", "IdeaTesting", "UserNo102", "ideatesting102@myntra.com", "9127963529", "MALE", 
				"1983-05-30", "SUCCESS" };
		String[] arr4 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting103", "IdeaTesting", "UserNo103", "ideatesting103@myntra.com", "9609690960", "FEMALE",
				"1984-06-09", "SUCCESS" };
		String[] arr5 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting104", "IdeaTesting", "UserNo104", "ideatesting104@myntra.com", "9499280686", "MALE", 
				"1985-07-02", "SUCCESS" };
		
		String[] arr6 = new String[]{ ENVIRONMENT_FOX7, "myntra", "ideatesting400", "main", "user", "ideatesting400@myntra.com", "9789645123", "MALE",
				"1991-03-06", "SUCCESS" };
		String[] arr7 = new String[]{ ENVIRONMENT_FOX7, "myntra", "ideatesting401", "main", "user", "ideatesting401@myntra.com", "9789645124", "MALE", 
				"1991-03-06", "SUCCESS" };
		
		String[] arr8 = new String[]{ ENVIRONMENT_FOX8, "myntra", "ideatesting400", "main", "user", "ideatesting400@myntra.com", "9789645123", "MALE",
				"1991-03-06", "SUCCESS" };
		String[] arr9 = new String[]{ ENVIRONMENT_FOX8, "myntra", "ideatesting401", "main", "user", "ideatesting401@myntra.com", "9789645124", "MALE", 
				"1991-03-06", "SUCCESS" };
		
		
        Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4, arr5,arr6,arr7,arr8,arr9 };
        return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 1, 2);
    }
	
	@DataProvider
    public Object[][] IdeaApiDP_mobileSignInUsingEmail_validateAPIResponse(ITestContext testContext)
	{
		String[] arr1 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting100", "ideatesting100@myntra.com", "200" };
		String[] arr2 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting101", "ideatesting101@myntra.com", "200" };
		String[] arr3 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting102", "ideatesting102@myntra.com", "200" };
		String[] arr4 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting103", "ideatesting103@myntra.com", "200" };
		String[] arr5 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting104", "ideatesting104@myntra.com", "200" };
		
		String[] arr6 = new String[]{ ENVIRONMENT_FOX7, "myntra", "ideatesting400", "ideatesting400@myntra.com", "200" };
		String[] arr7 = new String[]{ ENVIRONMENT_FOX7, "myntra", "ideatesting401", "ideatesting401@myntra.com", "200" };
		
		String[] arr8 = new String[]{ ENVIRONMENT_FOX8, "myntra", "ideatesting400", "ideatesting400@myntra.com", "200" };
		String[] arr9 = new String[]{ ENVIRONMENT_FOX8, "myntra", "ideatesting401", "ideatesting401@myntra.com", "200" };
        
        Object[][] dataSet = new Object[][]{  arr1, arr2, arr3, arr4, arr5,arr6,arr7,arr8,arr9 };
        return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 1, 2);
    }
	
	@DataProvider
    public Object[][] IdeaApiDP_mobileSignInUsingEmail_verifySuccessStatusInformation(ITestContext testContext)
	{
		String[] arr1 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting100", "IdeaTesting", "UserNo100", "ideatesting100@myntra.com", "9099414062", "MALE", 
				"1981-03-11", "3", "Success", "SUCCESS" };
		String[] arr2 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting101", "IdeaTesting", "UserNo101", "ideatesting101@myntra.com", "9928286747", "FEMALE", 
				"1982-04-21", "3", "Success", "SUCCESS" };
		String[] arr3 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting102", "IdeaTesting", "UserNo102", "ideatesting102@myntra.com", "9127963529", "MALE", 
				"1983-05-30", "3", "Success", "SUCCESS" };
		String[] arr4 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting103", "IdeaTesting", "UserNo103", "ideatesting103@myntra.com", "9609690960", "FEMALE",
				"1984-06-09", "3", "Success", "SUCCESS" };
		String[] arr5 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting104", "IdeaTesting", "UserNo104", "ideatesting104@myntra.com", "9499280686", "MALE", 
				"1985-07-02", "3", "Success", "SUCCESS" };
		
		String[] arr6 = new String[]{ ENVIRONMENT_FOX7, "myntra", "ideatesting400", "main", "user", "ideatesting400@myntra.com", "9789645123", "MALE",
				"1991-03-06", "3", "Success", "SUCCESS" };
		String[] arr7 = new String[]{ ENVIRONMENT_FOX7, "myntra", "ideatesting401", "main", "user", "ideatesting401@myntra.com", "9789645124", "MALE", 
				"1991-03-06", "3", "Success", "SUCCESS" };
		
		String[] arr8 = new String[]{ ENVIRONMENT_FOX8, "myntra", "ideatesting400", "main", "user", "ideatesting400@myntra.com", "9789645123", "MALE",
				"1991-03-06", "3", "Success", "SUCCESS" };
		String[] arr9 = new String[]{ ENVIRONMENT_FOX8, "myntra", "ideatesting401", "main", "user", "ideatesting401@myntra.com", "9789645124", "MALE", 
				"1991-03-06", "3", "Success", "SUCCESS" };
		
        Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4, arr5,arr6,arr7,arr8,arr9 };
        return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 1, 2);
    }
	
	@DataProvider
    public Object[][] IdeaApiDP_mobileSignInUsingEmail_verifyFailureStatusInformation(ITestContext testContext)
	{
		String[] arr1 = new String[]{ ENVIRONMENT_FUNC, "myntra", "mypass123", "ideatesting100@myntra.com", "110002", "The username or password you entered is incorrect",
				"ERROR" };
		String[] arr2 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting101", "testing900@myntra.com", "111004", "Account Doesn't exists", "ERROR" };
		String[] arr3 = new String[]{ ENVIRONMENT_FUNC, "myntra", "", "ideatesting102@myntra.com", "110002", "The username or password you entered is incorrect", "ERROR" };
		String[] arr4 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting103", "", "111004", "Account Doesn't exists", "ERROR" };
		String[] arr5 = new String[]{ ENVIRONMENT_FUNC, "", "ideatesting104", "ideatesting104@myntra.com", "111020", "Invalid App", "ERROR" };
		String[] arr6 = new String[]{ ENVIRONMENT_FUNC, "nonmyntra", "ideatesting104", "ideatesting104@myntra.com", "111020", "Invalid App", "ERROR" };
		
		String[] arr7 = new String[]{ ENVIRONMENT_FOX7, "", "ideatesting400", "ideatesting400@myntra.com", "111020", "Invalid App", "ERROR" };
		String[] arr8 = new String[]{ ENVIRONMENT_FOX7, "nonmyntra", "ideatesting401", "ideatesting401@myntra.com", "111020", "Invalid App", "ERROR" };
		
		String[] arr9 = new String[]{ ENVIRONMENT_FOX8, "", "ideatesting400", "ideatesting400@myntra.com", "111020", "Invalid App", "ERROR" };
		String[] arr10 = new String[]{ ENVIRONMENT_FOX8, "nonmyntra", "ideatesting401", "ideatesting401@myntra.com", "111020", "Invalid App", "ERROR" };
		
        Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4, arr5, arr6,arr7,arr8,arr9,arr10 };
        return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 1, 2);
    }
	
	@DataProvider
    public Object[][] IdeaApiDP_mobileSignInUsingEmail_verifyNodeValues(ITestContext testContext)
	{
		String[] arr1 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting100", "IdeaTesting", "UserNo100", "ideatesting100@myntra.com", "9099414062", "MALE", 
				"1981-03-11", "SUCCESS" };
		String[] arr2 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting101", "IdeaTesting", "UserNo101", "ideatesting101@myntra.com", "9928286747", "FEMALE", 
				"1982-04-21", "SUCCESS" };
		String[] arr3 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting102", "IdeaTesting", "UserNo102", "ideatesting102@myntra.com", "9127963529", "MALE", 
				"1983-05-30", "SUCCESS" };
		String[] arr4 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting103", "IdeaTesting", "UserNo103", "ideatesting103@myntra.com", "9609690960", "FEMALE",
				"1984-06-09", "SUCCESS" };
		String[] arr5 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting104", "IdeaTesting", "UserNo104", "ideatesting104@myntra.com", "9499280686", "MALE", 
				"1985-07-02", "SUCCESS" };
		
		String[] arr6 = new String[]{ ENVIRONMENT_FOX7, "myntra", "ideatesting400", "main", "user", "ideatesting400@myntra.com", "9789645123", "MALE",
				"1991-03-06", "SUCCESS" };
		String[] arr7 = new String[]{ ENVIRONMENT_FOX7, "myntra", "ideatesting401", "main", "user", "ideatesting401@myntra.com", "9789645124", "MALE", 
				"1991-03-06", "SUCCESS" };
		
		String[] arr8 = new String[]{ ENVIRONMENT_FOX8, "myntra", "ideatesting400", "main", "user", "ideatesting400@myntra.com", "9789645123", "MALE",
				"1991-03-06", "SUCCESS" };
		String[] arr9 = new String[]{ ENVIRONMENT_FOX8, "myntra", "ideatesting401", "main", "user", "ideatesting401@myntra.com", "9789645124", "MALE", 
				"1991-03-06", "SUCCESS" };
		
        Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4, arr5,arr6,arr7,arr8,arr9 };
        return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 1, 2);
    }
	
	@DataProvider
    public Object[][] IdeaApiDP_mobileSignInUsingEmail_verifyResponseDataNodesUsingSchemaValidations(ITestContext testContext)
	{
		String[] arr1 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting100", "IdeaTesting", "UserNo100", "ideatesting100@myntra.com", "9099414062", "MALE", 
				"1981-03-11", "SUCCESS" };
		String[] arr2 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting101", "IdeaTesting", "UserNo101", "ideatesting101@myntra.com", "9928286747", "FEMALE", 
				"1982-04-21", "SUCCESS" };
		String[] arr3 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting102", "IdeaTesting", "UserNo102", "ideatesting102@myntra.com", "9127963529", "MALE", 
				"1983-05-30", "SUCCESS" };
		String[] arr4 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting103", "IdeaTesting", "UserNo103", "ideatesting103@myntra.com", "9609690960", "FEMALE",
				"1984-06-09", "SUCCESS" };
		String[] arr5 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting104", "IdeaTesting", "UserNo104", "ideatesting104@myntra.com", "9499280686", "MALE", 
				"1985-07-02", "SUCCESS" };
		
		String[] arr6 = new String[]{ ENVIRONMENT_FOX7, "myntra", "ideatesting400", "main", "user", "ideatesting400@myntra.com", "9789645123", "MALE",
				"1991-03-06", "SUCCESS" };
		String[] arr7 = new String[]{ ENVIRONMENT_FOX7, "myntra", "ideatesting401", "main", "user", "ideatesting401@myntra.com", "9789645124", "MALE", 
				"1991-03-06", "SUCCESS" };
		
		String[] arr8 = new String[]{ ENVIRONMENT_FOX8, "myntra", "ideatesting400", "main", "user", "ideatesting400@myntra.com", "9789645123", "MALE",
				"1991-03-06", "SUCCESS" };
		String[] arr9 = new String[]{ ENVIRONMENT_FOX8, "myntra", "ideatesting401", "main", "user", "ideatesting401@myntra.com", "9789645124", "MALE", 
				"1991-03-06", "SUCCESS" };
		
        Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4, arr5,arr6,arr7,arr8,arr9 };
        return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 1, 2);
    }
	
	@DataProvider
    public Object[][] IdeaApiDP_changeProfilePassword_validateAPIResponse(ITestContext testContext)
	{
		String[] arr1 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting401", "ideatesting301", "ideatesting301@myntra.com", "200" };
		String[] arr2 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting402", "ideatesting302", "ideatesting302@myntra.com", "200" };
		
		String[] arr3 = new String[]{ ENVIRONMENT_FOX7, "myntra", "ideatesting111", "ideatesting400", "ideatesting400@myntra.com", "200" };
		String[] arr4 = new String[]{ ENVIRONMENT_FOX7, "myntra", "ideatesting112", "ideatesting401", "ideatesting401@myntra.com", "200" };
		
		String[] arr5 = new String[]{ ENVIRONMENT_FOX8, "myntra", "ideatesting111", "ideatesting400", "ideatesting400@myntra.com", "200" };
		String[] arr6 = new String[]{ ENVIRONMENT_FOX8, "myntra", "ideatesting112", "ideatesting401", "ideatesting401@myntra.com", "200" };
        
        Object[][] dataSet = new Object[][]{ arr1, arr2,arr3,arr4,arr5,arr6 };
        return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 1, 2);
    }
	
	@DataProvider
    public Object[][] IdeaApiDP_changeProfilePassword_verifySuccessStatusInformation(ITestContext testContext)
	{
		String[] arr1 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting401", "ideatesting301", "IdeaTesting", "UserNo301", "ideatesting301@myntra.com", "9928286745", 
				"FEMALE", "1982-04-01", "3", "Success", "SUCCESS" };
		String[] arr2 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting402", "ideatesting302", "IdeaTesting", "UserNo302", "ideatesting302@myntra.com", "9127963526", 
				"MALE", "1983-05-30", "3", "Success", "SUCCESS" };
		
		String[] arr3 = new String[]{ ENVIRONMENT_FOX7, "myntra", "ideatesting111", "ideatesting400", "main", "user", "ideatesting400@myntra.com", "9789645123", 
				"MALE", "1991-03-06", "3", "Success", "SUCCESS" };
		String[] arr4 = new String[]{ ENVIRONMENT_FOX7, "myntra", "ideatesting112", "ideatesting401", "main", "user", "ideatesting401@myntra.com", "9789645124", 
				"MALE", "1991-03-06", "3", "Success", "SUCCESS" };
		
		String[] arr5 = new String[]{ ENVIRONMENT_FOX8, "myntra", "ideatesting111", "ideatesting400", "main", "user", "ideatesting400@myntra.com", "9789645123", 
				"MALE", "1991-03-06", "3", "Success", "SUCCESS" };
		String[] arr6 = new String[]{ ENVIRONMENT_FOX8, "myntra", "ideatesting112", "ideatesting401", "main", "user", "ideatesting401@myntra.com", "9789645124", 
				"MALE", "1991-03-06", "3", "Success", "SUCCESS" };
		
        Object[][] dataSet = new Object[][]{ arr1, arr2,arr3,arr4,arr5,arr6 };
        return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 1, 2);
    }
	
	@DataProvider
    public Object[][] IdeaApiDP_changeProfilePassword_verifyFailureStatusInformation(ITestContext testContext)
	{
		String[] arr1 = new String[]{ ENVIRONMENT_FUNC, "", "ideatesting401", "ideatesting301", "ideatesting301@myntra.com", "111020", "Invalid App", "ERROR" };
		String[] arr2 = new String[]{ ENVIRONMENT_FUNC, "nonmyntra", "ideatesting402", "ideatesting302", "ideatesting302@myntra.com", "111020", "Invalid App", "ERROR" };
		String[] arr3 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting402", "ideatesting302", "ideatesting1909@myntra.com", "111004", "Account Doesn't exists", 
				"ERROR" };
		String[] arr4 = new String[]{ ENVIRONMENT_FUNC, "myntra", "", "ideatesting301", "ideatesting301@myntra.com", "112000", "Invalid password format", "ERROR" };
		String[] arr5 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting401", "", "", "111004", "Account Doesn't exists", "ERROR" };
		String[] arr6 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting401", "ideatesting401", "ideatesting304@myntra.com", "111004", "Account Doesn't exists", "ERROR" };
		
		String[] arr7 = new String[]{ ENVIRONMENT_FOX7, "myntra", "ideatesting111", "", "", "111004", "Account Doesn't exists", "ERROR" };
		String[] arr8 = new String[]{ ENVIRONMENT_FOX7, "myntra", "ideatesting112", "ideatesting401", "ideatesting401456@myntra.com", "111004", "Account Doesn't exists", "ERROR" };
		
		String[] arr9 = new String[]{ ENVIRONMENT_FOX8, "myntra", "ideatesting111", "", "", "111004", "Account Doesn't exists", "ERROR" };
		String[] arr10 = new String[]{ ENVIRONMENT_FOX8, "myntra", "ideatesting112", "ideatesting401", "ideatesting401456@myntra.com", "111004", "Account Doesn't exists", "ERROR" };
		
        Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4, arr5, arr6,arr7,arr8,arr9,arr10 };
        return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 1, 2);
    }
	
	@DataProvider
    public Object[][] IdeaApiDP_changeProfilePassword_verifyResponseDataNodesUsingSchemaValidations(ITestContext testContext)
	{
		String[] arr1 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting401", "ideatesting301", "IdeaTesting", "UserNo301", "ideatesting301@myntra.com", "9928286745", 
				"FEMALE", "1982-04-01", "SUCCESS" };
		String[] arr2 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting402", "ideatesting302", "IdeaTesting", "UserNo302", "ideatesting302@myntra.com", "9127963526", 
				"MALE", "1983-05-30", "SUCCESS" };
		

		String[] arr3 = new String[]{ ENVIRONMENT_FOX7, "myntra", "ideatesting111", "ideatesting400", "main", "user", "ideatesting400@myntra.com", "9789645123", 
				"MALE", "1991-03-06", "SUCCESS" };
		String[] arr4 = new String[]{ ENVIRONMENT_FOX7, "myntra", "ideatesting112", "ideatesting401", "main", "user", "ideatesting401@myntra.com", "9789645124", 
				"MALE", "1991-03-06", "SUCCESS" };
		
		String[] arr5 = new String[]{ ENVIRONMENT_FOX8, "myntra", "ideatesting111", "ideatesting400", "main", "user", "ideatesting400@myntra.com", "9789645123", 
				"MALE", "1991-03-06", "SUCCESS" };
		String[] arr6 = new String[]{ ENVIRONMENT_FOX8, "myntra", "ideatesting112", "ideatesting401", "main", "user", "ideatesting401@myntra.com", "9789645124", 
				"MALE", "1991-03-06", "SUCCESS" };
        
        Object[][] dataSet = new Object[][]{ arr1, arr2,arr3,arr4,arr5,arr6 };
        return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 1, 2);
    }
	
	@DataProvider
    public Object[][] IdeaApiDP_updateUserProfileInfo_validateAPIResponse(ITestContext testContext)
	{
		String[] arr1 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting100", "IdeaTesting", "UserNo100", "ideatesting100@myntra.com", "9099414062", "MALE", 
				"1981-03-11", "200", "ACTIVE", "myntra", "MyIdeaTesting", "MyUserNo100", "FEMALE", "1999-01-01", "BLOCKED" };
		String[] arr2 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting101", "IdeaTesting", "UserNo101", "ideatesting101@myntra.com", "9928286747", "FEMALE", 
				"1982-04-21", "200", "ACTIVE", "myntra", "MyIdeaTesting", "MyUserNo100", "FEMALE", "1999-01-01", "BLOCKED" };
		String[] arr3 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting102", "IdeaTesting", "UserNo102", "ideatesting102@myntra.com", "9127963529", "MALE", 
				"1983-05-30", "200", "ACTIVE", "myntra", "MyIdeaTesting", "MyUserNo100", "FEMALE", "1999-01-01", "BLOCKED" };
		String[] arr4 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting103", "IdeaTesting", "UserNo103", "ideatesting103@myntra.com", "9609690960", "FEMALE",
				"1984-06-09", "200", "ACTIVE", "myntra", "MyIdeaTesting", "MyUserNo100", "FEMALE", "1999-01-01", "BLOCKED" };
		String[] arr5 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting104", "IdeaTesting", "UserNo104", "ideatesting104@myntra.com", "9499280686", "MALE", 
				"1985-07-02", "200", "ACTIVE", "myntra", "MyIdeaTesting", "MyUserNo100", "FEMALE", "1999-01-01", "BLOCKED" };
		
		String[] arr6 = new String[]{ ENVIRONMENT_FOX7, "myntra", "ideatesting400", "main", "user", "ideatesting400@myntra.com", "9789645123", "MALE",
				"1991-03-06", "200", "ACTIVE", "myntra", "MyIdeaTesting", "MyUserNo100", "FEMALE", "1999-01-01", "BLOCKED" };
		String[] arr7 = new String[]{ ENVIRONMENT_FOX7, "myntra", "ideatesting401", "main", "user", "ideatesting401@myntra.com", "9789645124", "MALE", 
				"1991-03-06", "200", "ACTIVE", "myntra", "MyIdeaTesting", "MyUserNo100", "FEMALE", "1999-01-01", "BLOCKED" };
		
		String[] arr8 = new String[]{ ENVIRONMENT_FOX8, "myntra", "ideatesting400", "main", "user", "ideatesting400@myntra.com", "9789645123", "MALE",
				"1991-03-06", "200", "ACTIVE", "myntra", "MyIdeaTesting", "MyUserNo100", "FEMALE", "1999-01-01", "BLOCKED" };
		String[] arr9 = new String[]{ ENVIRONMENT_FOX8, "myntra", "ideatesting401", "main", "user", "ideatesting401@myntra.com", "9789645124", "MALE", 
				"1991-03-06", "200", "ACTIVE", "myntra", "MyIdeaTesting", "MyUserNo100", "FEMALE", "1999-01-01", "BLOCKED" };
		
        Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4, arr5,arr6,arr7,arr8,arr9 };
        return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 1, 2);
    }
	
	@DataProvider
    public Object[][] IdeaApiDP_updateUserProfileInfo_verifySuccessStatusInformation(ITestContext testContext)
	{
		String[] arr1 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting100", "IdeaTesting", "UserNo100", "ideatesting100@myntra.com", "9099414062", "MALE", 
				"1981-03-11", "3", "Success", "SUCCESS", "ACTIVE", "myntra", "MyIdeaTesting", "MyUserNo100", "FEMALE", "1999-01-01", "BLOCKED" };
		String[] arr2 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting101", "IdeaTesting", "UserNo101", "ideatesting101@myntra.com", "9928286747", "FEMALE", 
				"1982-04-21", "3", "Success", "SUCCESS", "ACTIVE", "myntra", "MyIdeaTesting", "MyUserNo100", "FEMALE", "1999-01-01", "BLOCKED" };
		String[] arr3 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting102", "IdeaTesting", "UserNo102", "ideatesting102@myntra.com", "9127963529", "MALE", 
				"1983-05-30", "3", "Success", "SUCCESS", "ACTIVE", "myntra", "MyIdeaTesting", "MyUserNo100", "FEMALE", "1999-01-01", "BLOCKED" };
		String[] arr4 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting103", "IdeaTesting", "UserNo103", "ideatesting103@myntra.com", "9609690960", "FEMALE",
				"1984-06-09", "3", "Success", "SUCCESS", "ACTIVE", "myntra", "MyIdeaTesting", "MyUserNo100", "FEMALE", "1999-01-01", "BLOCKED" };
		String[] arr5 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting104", "IdeaTesting", "UserNo104", "ideatesting104@myntra.com", "9499280686", "MALE", 
				"1985-07-02", "3", "Success", "SUCCESS", "ACTIVE", "myntra", "MyIdeaTesting", "MyUserNo100", "FEMALE", "1999-01-01", "BLOCKED" };
		
		String[] arr6 = new String[]{ ENVIRONMENT_FOX7, "myntra", "ideatesting400", "main", "user", "ideatesting400@myntra.com", "9789645123", "MALE",
				"1991-03-06", "3", "Success", "SUCCESS", "ACTIVE", "myntra", "MyIdeaTesting", "MyUserNo100", "FEMALE", "1999-01-01", "BLOCKED" };
		String[] arr7 = new String[]{ ENVIRONMENT_FOX7, "myntra", "ideatesting401", "main", "user", "ideatesting401@myntra.com", "9789645124", "MALE", 
				"1991-03-06", "3", "Success", "SUCCESS", "ACTIVE", "myntra", "MyIdeaTesting", "MyUserNo100", "FEMALE", "1999-01-01", "BLOCKED" };
		
		String[] arr8 = new String[]{ ENVIRONMENT_FOX8, "myntra", "ideatesting400", "main", "user", "ideatesting400@myntra.com", "9789645123", "MALE",
				"1991-03-06", "3", "Success", "SUCCESS", "ACTIVE", "myntra", "MyIdeaTesting", "MyUserNo100", "FEMALE", "1999-01-01", "BLOCKED" };
		String[] arr9 = new String[]{ ENVIRONMENT_FOX8, "myntra", "ideatesting401", "main", "user", "ideatesting401@myntra.com", "9789645124", "MALE", 
				"1991-03-06", "3", "Success", "SUCCESS", "ACTIVE", "myntra", "MyIdeaTesting", "MyUserNo100", "FEMALE", "1999-01-01", "BLOCKED" };
		
        Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4, arr5,arr6,arr7,arr8,arr9 };
        return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 1, 2);
    }
	
	@DataProvider
    public Object[][] IdeaApiDP_updateUserProfileInfo_verifyFailureStatusInformation(ITestContext testContext)
	{
		String[] arr1 = new String[]{ ENVIRONMENT_FUNC, "", "MyIdeaTesting", "MyUserNo100", "FEMALE", "1999-01-01", "BLOCKED", "ideatesting100@myntra.com", "111020", 
				"Invalid App", "ERROR" };
		String[] arr2 = new String[]{ ENVIRONMENT_FUNC, "nonmyntra", "MyIdeaTesting", "MyUserNo100", "FEMALE", "1999-01-01", "BLOCKED", "ideatesting100@myntra.com", "111020",
				"Invalid App", "ERROR" };
		String[] arr3 = new String[]{ ENVIRONMENT_FUNC, "myntra", "MyIdeaTesting", "MyUserNo100", "FEMALE", "1999-01-01", "BLOCKED", "ideatesting1909@myntra.com", 
				"111004", "Account Doesn't exists", "ERROR" };
		String[] arr4 = new String[]{ ENVIRONMENT_FUNC, "myntra", "MyIdeaTesting", "MyUserNo100", "FEMALE", "1999-01-01", "BLOCKED", "",	"111004", 
				"Account Doesn't exists", "ERROR" };
		
		String[] arr5 = new String[]{ ENVIRONMENT_FOX7, "myntra", "MyIdeaTesting", "MyUserNo100", "FEMALE", "1999-01-01", "BLOCKED", "ideatesting601@myntra.com", 
				"111004", "Account Doesn't exists", "ERROR" };
		String[] arr6 = new String[]{ ENVIRONMENT_FOX7, "myntra", "MyIdeaTesting", "MyUserNo100", "FEMALE", "1999-01-01", "BLOCKED", "",	"111004", 
				"Account Doesn't exists", "ERROR" };
		
		String[] arr7 = new String[]{ ENVIRONMENT_FOX8, "myntra", "MyIdeaTesting", "MyUserNo100", "FEMALE", "1999-01-01", "BLOCKED", "ideatesting601@myntra.com", 
				"111004", "Account Doesn't exists", "ERROR" };
		String[] arr8 = new String[]{ ENVIRONMENT_FOX8, "myntra", "MyIdeaTesting", "MyUserNo100", "FEMALE", "1999-01-01", "BLOCKED", "",	"111004", 
				"Account Doesn't exists", "ERROR" };
		
        Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4,arr5,arr6,arr7,arr8 };
        return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 1, 2);
    }
	
	@DataProvider
    public Object[][] IdeaApiDP_updateUserProfileInfo_verifyNodeValues(ITestContext testContext)
	{
		String[] arr1 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting100", "IdeaTesting", "UserNo100", "ideatesting100@myntra.com", "9099414062", "MALE", 
				"1981-03-11", "3", "Success", "SUCCESS", "ACTIVE", "myntra", "MyIdeaTesting", "MyUserNo100", "FEMALE", "1999-01-01", "BLOCKED" };
		String[] arr2 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting101", "IdeaTesting", "UserNo101", "ideatesting101@myntra.com", "9928286747", "FEMALE", 
				"1982-04-21", "3", "Success", "SUCCESS", "ACTIVE", "myntra", "MyIdeaTesting", "MyUserNo100", "FEMALE", "1999-01-01", "BLOCKED" };
		String[] arr3 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting102", "IdeaTesting", "UserNo102", "ideatesting102@myntra.com", "9127963529", "MALE", 
				"1983-05-30", "3", "Success", "SUCCESS", "ACTIVE", "myntra", "MyIdeaTesting", "MyUserNo100", "FEMALE", "1999-01-01", "BLOCKED" };
		String[] arr4 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting103", "IdeaTesting", "UserNo103", "ideatesting103@myntra.com", "9609690960", "FEMALE",
				"1984-06-09", "3", "Success", "SUCCESS", "ACTIVE", "myntra", "MyIdeaTesting", "MyUserNo100", "FEMALE", "1999-01-01", "BLOCKED" };
		String[] arr5 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting104", "IdeaTesting", "UserNo104", "ideatesting104@myntra.com", "9499280686", "MALE", 
				"1985-07-02", "3", "Success", "SUCCESS", "ACTIVE", "myntra", "MyIdeaTesting", "MyUserNo100", "FEMALE", "1999-01-01", "BLOCKED" };
		
		String[] arr6 = new String[]{ ENVIRONMENT_FOX7, "myntra", "ideatesting400", "main", "user", "ideatesting400@myntra.com", "9789645123", "MALE",
				"1991-03-06", "3", "Success", "SUCCESS", "ACTIVE", "myntra", "MyIdeaTesting", "MyUserNo100", "FEMALE", "1999-01-01", "BLOCKED" };
		String[] arr7 = new String[]{ ENVIRONMENT_FOX7, "myntra", "ideatesting401", "main", "user", "ideatesting401@myntra.com", "9789645124", "MALE", 
				"1991-03-06", "3", "Success", "SUCCESS", "ACTIVE", "myntra", "MyIdeaTesting", "MyUserNo100", "FEMALE", "1999-01-01", "BLOCKED" };
		
		String[] arr8 = new String[]{ ENVIRONMENT_FOX8, "myntra", "ideatesting400", "main", "user", "ideatesting400@myntra.com", "9789645123", "MALE",
				"1991-03-06", "3", "Success", "SUCCESS", "ACTIVE", "myntra", "MyIdeaTesting", "MyUserNo100", "FEMALE", "1999-01-01", "BLOCKED" };
		String[] arr9 = new String[]{ ENVIRONMENT_FOX8, "myntra", "ideatesting401", "main", "user", "ideatesting401@myntra.com", "9789645124", "MALE", 
				"1991-03-06", "3", "Success", "SUCCESS", "ACTIVE", "myntra", "MyIdeaTesting", "MyUserNo100", "FEMALE", "1999-01-01", "BLOCKED" };
		
        Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4, arr5,arr6,arr7,arr8,arr9 };
        return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 1, 2);
    }
	
	@DataProvider
    public Object[][] IdeaApiDP_updateUserProfileInfo_verifyResponseDataNodesUsingSchemaValidations(ITestContext testContext)
	{
		String[] arr1 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting100", "IdeaTesting", "UserNo100", "ideatesting100@myntra.com", "9099414062", "MALE", 
				"1981-03-11", "3", "Success", "SUCCESS", "ACTIVE", "myntra", "MyIdeaTesting", "MyUserNo100", "FEMALE", "1999-01-01", "BLOCKED" };
		String[] arr2 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting101", "IdeaTesting", "UserNo101", "ideatesting101@myntra.com", "9928286747", "FEMALE", 
				"1982-04-21", "3", "Success", "SUCCESS", "ACTIVE", "myntra", "MyIdeaTesting", "MyUserNo100", "FEMALE", "1999-01-01", "BLOCKED" };
		String[] arr3 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting102", "IdeaTesting", "UserNo102", "ideatesting102@myntra.com", "9127963529", "MALE", 
				"1983-05-30", "3", "Success", "SUCCESS", "ACTIVE", "myntra", "MyIdeaTesting", "MyUserNo100", "FEMALE", "1999-01-01", "BLOCKED" };
		String[] arr4 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting103", "IdeaTesting", "UserNo103", "ideatesting103@myntra.com", "9609690960", "FEMALE",
				"1984-06-09", "3", "Success", "SUCCESS", "ACTIVE", "myntra", "MyIdeaTesting", "MyUserNo100", "FEMALE", "1999-01-01", "BLOCKED" };
		String[] arr5 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting104", "IdeaTesting", "UserNo104", "ideatesting104@myntra.com", "9499280686", "MALE", 
				"1985-07-02", "3", "Success", "SUCCESS", "ACTIVE", "myntra", "MyIdeaTesting", "MyUserNo100", "FEMALE", "1999-01-01", "BLOCKED" };
		
		String[] arr6 = new String[]{ ENVIRONMENT_FOX7, "myntra", "ideatesting400", "main", "user", "ideatesting400@myntra.com", "9789645123", "MALE",
				"1991-03-06", "3", "Success", "SUCCESS", "ACTIVE", "myntra", "MyIdeaTesting", "MyUserNo100", "FEMALE", "1999-01-01", "BLOCKED" };
		String[] arr7 = new String[]{ ENVIRONMENT_FOX7, "myntra", "ideatesting401", "main", "user", "ideatesting401@myntra.com", "9789645124", "MALE", 
				"1991-03-06", "3", "Success", "SUCCESS", "ACTIVE", "myntra", "MyIdeaTesting", "MyUserNo100", "FEMALE", "1999-01-01", "BLOCKED" };
		
		String[] arr8 = new String[]{ ENVIRONMENT_FOX8, "myntra", "ideatesting400", "main", "user", "ideatesting400@myntra.com", "9789645123", "MALE",
				"1991-03-06", "3", "Success", "SUCCESS", "ACTIVE", "myntra", "MyIdeaTesting", "MyUserNo100", "FEMALE", "1999-01-01", "BLOCKED" };
		String[] arr9 = new String[]{ ENVIRONMENT_FOX8, "myntra", "ideatesting401", "main", "user", "ideatesting401@myntra.com", "9789645124", "MALE", 
				"1991-03-06", "3", "Success", "SUCCESS", "ACTIVE", "myntra", "MyIdeaTesting", "MyUserNo100", "FEMALE", "1999-01-01", "BLOCKED" };
		
        Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4, arr5,arr6,arr7,arr8,arr9 };
        return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 1, 2);
    }
	
	@DataProvider
    public Object[][] IdeaApiDP_passwordRecoveryByEmail_validateAPIResponse(ITestContext testContext)
	{
		String[] arr1 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting100@myntra.com", "200" };
		String[] arr2 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting101@myntra.com", "200" };
		String[] arr3 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting102@myntra.com", "200" };
		String[] arr4 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting103@myntra.com", "200" };
		String[] arr5 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting104@myntra.com", "200" };
		String[] arr6 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting1909@myntra.com", "200" };
		
		String[] arr7 = new String[]{ ENVIRONMENT_FOX7, "myntra", "ideatesting400@myntra.com", "200" };
		String[] arr8 = new String[]{ ENVIRONMENT_FOX7, "myntra", "ideatesting401@myntra.com", "200" };
		
		String[] arr9 = new String[]{ ENVIRONMENT_FOX8, "myntra", "ideatesting400@myntra.com", "200" };
		String[] arr10 = new String[]{ ENVIRONMENT_FOX8, "myntra", "ideatesting401@myntra.com", "200" };
		
        Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4, arr5, arr6,arr7,arr8,arr9,arr10 };
        return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 1, 2);
    }
	
	@DataProvider
    public Object[][] IdeaApiDP_passwordRecoveryByEmail_verifySuccessStatusInformation(ITestContext testContext)
	{
		String[] arr1 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting100@myntra.com", "0", "", "SUCCESS" };
		String[] arr2 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting101@myntra.com", "0", "", "SUCCESS" };
		String[] arr3 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting102@myntra.com", "0", "", "SUCCESS" };
		String[] arr4 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting103@myntra.com", "0", "", "SUCCESS" };
		String[] arr5 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting104@myntra.com", "0", "", "SUCCESS" };
		String[] arr6 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting1909@myntra.com", "0", "", "SUCCESS" };
		
		String[] arr7 = new String[]{ ENVIRONMENT_FOX7, "myntra", "ideatesting400@myntra.com", "0", "", "SUCCESS" };
		String[] arr8 = new String[]{ ENVIRONMENT_FOX7, "myntra", "ideatesting401@myntra.com", "0", "", "SUCCESS" };
		
		String[] arr9 = new String[]{ ENVIRONMENT_FOX8, "myntra", "ideatesting400@myntra.com", "0", "", "SUCCESS" };
		String[] arr10 = new String[]{ ENVIRONMENT_FOX8, "myntra", "ideatesting401@myntra.com", "0", "", "SUCCESS" };
		
        Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4, arr5, arr6,arr7,arr8,arr9,arr10 };
        return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 1, 2);
    }
	
	@DataProvider
    public Object[][] IdeaApiDP_passwordRecoveryByEmail_verifyFailureStatusInformation(ITestContext testContext)
	{
		String[] arr1 = new String[]{ ENVIRONMENT_FUNC, "myntra", "", "111021", "Account recovery error", "ERROR" };
		String[] arr2 = new String[]{ ENVIRONMENT_FOX7, "myntra", "", "111021", "Account recovery error", "ERROR" };
		String[] arr3 = new String[]{ ENVIRONMENT_FOX8, "myntra", "", "111021", "Account recovery error", "ERROR" };
        
        Object[][] dataSet = new Object[][]{ arr1,arr2,arr3 };
        return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 1, 2);
    }
	
	@DataProvider
    public Object[][] IdeaApiDP_passwordRecoveryByEmail_verifyResponseDataNodesUsingSchemaValidations(ITestContext testContext)
	{
		String[] arr1 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting100@myntra.com", "SUCCESS" };
		String[] arr2 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting101@myntra.com", "SUCCESS" };
		String[] arr3 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting102@myntra.com", "SUCCESS" };
		String[] arr4 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting103@myntra.com", "SUCCESS" };
		String[] arr5 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting104@myntra.com", "SUCCESS" };
		String[] arr6 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting1909@myntra.com", "SUCCESS" };
		
		String[] arr7 = new String[]{ ENVIRONMENT_FOX7, "myntra", "ideatesting400@myntra.com", "SUCCESS" };
		String[] arr8 = new String[]{ ENVIRONMENT_FOX7, "myntra", "ideatesting401@myntra.com", "SUCCESS" };
		
		String[] arr9 = new String[]{ ENVIRONMENT_FOX8, "myntra", "ideatesting400@myntra.com", "SUCCESS" };
		String[] arr10 = new String[]{ ENVIRONMENT_FOX8, "myntra", "ideatesting401@myntra.com", "SUCCESS" };
		
        Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4, arr5, arr6,arr7,arr8,arr9,arr10 };
        return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 1, 2);
    }
	
	@DataProvider
    public Object[][] IdeaApiDP_resetPasswordByEmail_validateAPIResponse(ITestContext testContext)
	{
		String[] arr1 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting501", "ideatesting401", "IdeaTesting", "UserNo401", "ideatesting401@myntra.com", "9928286745", 
				"FEMALE", "1982-12-11", IDPServiceDataProviderEnum.ENVIRONMENT_FUNC.name(), "200", "SUCCESS" };
		String[] arr2 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting502", "ideatesting402", "IdeaTesting", "UserNo402", "ideatesting402@myntra.com", "9127963526", 
				"MALE", "1983-10-09", IDPServiceDataProviderEnum.ENVIRONMENT_FUNC.name(), "200", "SUCCESS" };
		
		String[] arr3 = new String[]{ ENVIRONMENT_FOX7, "myntra", "ideatesting500", "ideatesting400", "main", "user", "ideatesting400@myntra.com", "9789645123", 
				"FEMALE", "1982-12-11", IDPServiceDataProviderEnum.ENVIRONMENT_FOX7.name(), "200", "SUCCESS" };
		String[] arr4 = new String[]{ ENVIRONMENT_FOX7, "myntra", "ideatesting501", "ideatesting401", "main", "user", "ideatesting401@myntra.com", "9789645124", 
				"MALE", "1983-10-09", IDPServiceDataProviderEnum.ENVIRONMENT_FOX7.name(), "200", "SUCCESS" };
		
		String[] arr5 = new String[]{ ENVIRONMENT_FOX8, "myntra", "ideatesting500", "ideatesting400", "main", "user", "ideatesting400@myntra.com", "9789645123", 
				"FEMALE", "1982-12-11", IDPServiceDataProviderEnum.ENVIRONMENT_FOX8.name(), "200", "SUCCESS" };
		String[] arr6 = new String[]{ ENVIRONMENT_FOX8, "myntra", "ideatesting501", "ideatesting401", "main", "user", "ideatesting401@myntra.com", "9789645124", 
				"MALE", "1983-10-09", IDPServiceDataProviderEnum.ENVIRONMENT_FOX8.name(), "200", "SUCCESS" };
		
        Object[][] dataSet = new Object[][]{ arr1, arr2,arr3,arr4,arr5,arr6 };
        return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 1, 2);
    }
	
	@DataProvider
    public Object[][] IdeaApiDP_resetPasswordByEmail_verifySuccessStatusInformation(ITestContext testContext)
	{
		String[] arr1 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting415", "ideatesting315", "IdeaTesting", "UserNo401", "ideatesting315@myntra.com", "9448455348", 
				"FEMALE", "1982-12-11", IDPServiceDataProviderEnum.ENVIRONMENT_FUNC.name(), "0", "", "SUCCESS" };
		String[] arr2 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting502", "ideatesting402", "IdeaTesting", "UserNo402", "ideatesting402@myntra.com", "9127963526", 
				"MALE", "1983-10-09", IDPServiceDataProviderEnum.ENVIRONMENT_FUNC.name(), "0", "", "SUCCESS" };
		
		String[] arr3 = new String[]{ ENVIRONMENT_FOX7, "myntra", "ideatesting415", "ideatesting400", "main", "user", "ideatesting400@myntra.com", "9789645123", 
				"MALE", "1991-03-06", IDPServiceDataProviderEnum.ENVIRONMENT_FOX7.name(), "0", "", "SUCCESS" };
		String[] arr4 = new String[]{ ENVIRONMENT_FOX7, "myntra", "ideatesting502", "ideatesting401", "main", "user", "ideatesting401@myntra.com", "9789645124", 
				"MALE", "1991-03-06", IDPServiceDataProviderEnum.ENVIRONMENT_FOX7.name(), "0", "", "SUCCESS" };
		
		String[] arr5 = new String[]{ ENVIRONMENT_FOX8, "myntra", "ideatesting415", "ideatesting400", "main", "user", "ideatesting400@myntra.com", "9789645123", 
				"MALE", "1991-03-06", IDPServiceDataProviderEnum.ENVIRONMENT_FOX8.name(), "0", "", "SUCCESS" };
		String[] arr6 = new String[]{ ENVIRONMENT_FOX8, "myntra", "ideatesting502", "ideatesting401", "main", "user", "ideatesting401@myntra.com", "9789645124", 
				"MALE", "1991-03-06", IDPServiceDataProviderEnum.ENVIRONMENT_FOX8.name(), "0", "", "SUCCESS" };
		
        Object[][] dataSet = new Object[][]{ arr1,arr2,arr3,arr4,arr5,arr6 };
        return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 1, 2);
    }
	
	@DataProvider
    public Object[][] IdeaApiDP_resetPasswordByEmail_verifyFailureStatusInformation(ITestContext testContext)
	{
		String[] arr1 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting900@myntra.com", "", "ideatesting900", "111023", "Invalid OTP Token", "ERROR" };
		String[] arr2 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting900@myntra.com", "PyFUwD6Nc0BV", "ideatesting900", "111023", "Invalid OTP Token", "ERROR" };
		String[] arr3 = new String[]{ ENVIRONMENT_FUNC, "", "ideatesting900@myntra.com", "9HDFhoWodBuCvMVGU9zZwq", "ideatesting900", "111020", "Invalid App", "ERROR" };
		String[] arr4 = new String[]{ ENVIRONMENT_FUNC, "nonmyntra", "ideatesting900@myntra.com", "9HDFhoWodBuCv", "ideatesting900", "111020", "Invalid App", "ERROR" };
		String[] arr5 = new String[]{ ENVIRONMENT_FUNC, "myntra", "", "PyFUwD6Nc0BV", "ideatesting900", "111023", "Invalid OTP Token", "ERROR" };
		String[] arr6 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting900@myntra.com", "PyFUwD6Nc0BV", "", "111023", "Invalid OTP Token", "ERROR" };
		
		String[] arr7 = new String[]{ ENVIRONMENT_FOX7, "myntra", "", "PyFUwD6Nc0BV", "ideatesting900", "111023", "Invalid OTP Token", "ERROR" };
		String[] arr8 = new String[]{ ENVIRONMENT_FOX7, "myntra", "ideatesting401@myntra.com", "PyFUwD6Nc0BV", "", "111023", "Invalid OTP Token", "ERROR" };
		
		String[] arr9 = new String[]{ ENVIRONMENT_FOX8, "myntra", "", "PyFUwD6Nc0BV", "ideatesting900", "111023", "Invalid OTP Token", "ERROR" };
		String[] arr10 = new String[]{ ENVIRONMENT_FOX8, "myntra", "ideatesting401@myntra.com", "PyFUwD6Nc0BV", "", "111023", "Invalid OTP Token", "ERROR" };
		
        Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4, arr5, arr6,arr7,arr8,arr9,arr10 };
        return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 1, 2);
    }
	
	@DataProvider
    public Object[][] IdeaApiDP_resetPasswordByEmail_verifyResponseDataNodesUsingSchemaValidations(ITestContext testContext)
	{
		String[] arr1 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting501", "ideatesting401", "IdeaTesting", "UserNo401", "ideatesting401@myntra.com", "9928286745", 
				"FEMALE", "1982-12-11", IDPServiceDataProviderEnum.ENVIRONMENT_FUNC.name(), "SUCCESS" };
		String[] arr2 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting502", "ideatesting402", "IdeaTesting", "UserNo402", "ideatesting402@myntra.com", "9127963526", 
				"MALE", "1983-10-09", IDPServiceDataProviderEnum.ENVIRONMENT_FUNC.name(), "SUCCESS" };
		
		String[] arr3 = new String[]{ ENVIRONMENT_FOX7, "myntra", "ideatesting501", "ideatesting400", "main", "user", "ideatesting400@myntra.com", "9789645123", 
				"MALE", "1991-03-06", IDPServiceDataProviderEnum.ENVIRONMENT_FOX7.name(), "SUCCESS" };
		String[] arr4 = new String[]{ ENVIRONMENT_FOX7, "myntra", "ideatesting502", "ideatesting401", "main", "user", "ideatesting401@myntra.com", "9789645124", 
				"MALE", "1991-03-06", IDPServiceDataProviderEnum.ENVIRONMENT_FOX7.name(), "SUCCESS" };
		
		String[] arr5 = new String[]{ ENVIRONMENT_FOX8, "myntra", "ideatesting501", "ideatesting400", "main", "user", "ideatesting400@myntra.com", "9789645123", 
				"MALE", "1991-03-06", IDPServiceDataProviderEnum.ENVIRONMENT_FOX8.name(), "SUCCESS" };
		String[] arr6 = new String[]{ ENVIRONMENT_FOX8, "myntra", "ideatesting502", "ideatesting401", "main", "user", "ideatesting401@myntra.com", "9789645124", 
				"MALE", "1991-03-06", IDPServiceDataProviderEnum.ENVIRONMENT_FOX8.name(), "SUCCESS" };
		
        Object[][] dataSet = new Object[][]{ arr1, arr2,arr3,arr4,arr5,arr6 };
        return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 1, 2);
    }
	
	@DataProvider
    public Object[][] IdeaApiDP_mobileSecureRefresh_validateAPIResponse(ITestContext testContext)
	{
		String[] arr1 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting100", "IdeaTesting", "UserNo100", "ideatesting100@myntra.com", "9099414062", "MALE", 
				"1981-03-11", "200", "SUCCESS" };
		String[] arr2 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting101", "IdeaTesting", "UserNo101", "ideatesting101@myntra.com", "9928286747", "FEMALE", 
				"1982-04-21", "200", "SUCCESS" };
		String[] arr3 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting102", "IdeaTesting", "UserNo102", "ideatesting102@myntra.com", "9127963529", "MALE", 
				"1983-05-30", "200", "SUCCESS" };
		String[] arr4 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting103", "IdeaTesting", "UserNo103", "ideatesting103@myntra.com", "9609690960", "FEMALE",
				"1984-06-09", "200", "SUCCESS" };
		String[] arr5 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting104", "IdeaTesting", "UserNo104", "ideatesting104@myntra.com", "9499280686", "MALE", 
				"1985-07-02", "200", "SUCCESS" };
		
		String[] arr6 = new String[]{ ENVIRONMENT_FOX7, "myntra", "ideatesting400", "main", "user", "ideatesting400@myntra.com", "9789645123", "MALE",
				"1991-03-06", "200", "SUCCESS" };
		String[] arr7 = new String[]{ ENVIRONMENT_FOX7, "myntra", "ideatesting401", "main", "user", "ideatesting401@myntra.com", "9789645124", "MALE", 
				"1991-03-06", "200", "SUCCESS" };
		
		String[] arr8 = new String[]{ ENVIRONMENT_FOX8, "myntra", "ideatesting400", "main", "user", "ideatesting400@myntra.com", "9789645123", "MALE",
				"1991-03-06", "200", "SUCCESS" };
		String[] arr9 = new String[]{ ENVIRONMENT_FOX8, "myntra", "ideatesting401", "main", "user", "ideatesting401@myntra.com", "9789645124", "MALE", 
				"1991-03-06", "200", "SUCCESS" };
        
        Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4, arr5,arr6,arr7,arr8,arr9 };
        return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 1, 2);
    }
	
	@DataProvider
    public Object[][] IdeaApiDP_mobileSecureRefresh_verifySuccessStatusInformation(ITestContext testContext)
	{
		String[] arr1 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting100", "IdeaTesting", "UserNo100", "ideatesting100@myntra.com", "9099414062", "MALE", 
				"1981-03-11", "3", "Success", "SUCCESS" };
		String[] arr2 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting101", "IdeaTesting", "UserNo101", "ideatesting101@myntra.com", "9928286747", "FEMALE", 
				"1982-04-21", "3", "Success", "SUCCESS" };
		String[] arr3 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting102", "IdeaTesting", "UserNo102", "ideatesting102@myntra.com", "9127963529", "MALE", 
				"1983-05-30", "3", "Success", "SUCCESS" };
		String[] arr4 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting103", "IdeaTesting", "UserNo103", "ideatesting103@myntra.com", "9609690960", "FEMALE",
				"1984-06-09", "3", "Success", "SUCCESS" };
		String[] arr5 = new String[]{ ENVIRONMENT_FUNC, "myntra", "ideatesting104", "IdeaTesting", "UserNo104", "ideatesting104@myntra.com", "9499280686", "MALE", 
				"1985-07-02", "3", "Success", "SUCCESS" };
		
		String[] arr6 = new String[]{ ENVIRONMENT_FOX7, "myntra", "ideatesting400", "main", "user", "ideatesting400@myntra.com", "9789645123", "MALE",
				"1991-03-06", "3", "Success", "SUCCESS" };
		String[] arr7 = new String[]{ ENVIRONMENT_FOX7, "myntra", "ideatesting401", "main", "user", "ideatesting401@myntra.com", "9789645124", "MALE", 
				"1991-03-06", "3", "Success", "SUCCESS" };
		
		String[] arr8 = new String[]{ ENVIRONMENT_FOX8, "myntra", "ideatesting400", "main", "user", "ideatesting400@myntra.com", "9789645123", "MALE",
				"1991-03-06", "3", "Success", "SUCCESS" };
		String[] arr9 = new String[]{ ENVIRONMENT_FOX8, "myntra", "ideatesting401", "main", "user", "ideatesting401@myntra.com", "9789645124", "MALE", 
				"1991-03-06", "3", "Success", "SUCCESS" };
        
        Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4, arr5,arr6,arr7,arr8,arr9 };
        return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 1, 2);
    }
	
	@DataProvider
    public Object[][] IdeaApiDP_mobileSecureRefresh_verifyFailureStatusInformation(ITestContext testContext)
	{
		String[] arr1 = new String[]{ ENVIRONMENT_FUNC, "", "", "111022", "Invalid refresh token", "ERROR" };
		String[] arr2 = new String[]{ ENVIRONMENT_FUNC, "", "eyJ2ZXJzaW9uIjogMCwidG9rZW4iOiJudWxsIn0=", "111022", "Invalid refresh token", "ERROR" };
		//String[] arr3 = new String[]{ ENVIRONMENT_FUNC, "eyJ2ZXJzaW9uIjogMCwidG9rZW4iOiJudWxsIn0=", "", "51", "Generic Exception", "ERROR" };
		String[] arr3 = new String[]{ ENVIRONMENT_FUNC, "eyJ2ZXJzaW9uIjogMCwidG9rZW4iOiJudWxsIn0=", "", "111022", "Invalid refresh token", "ERROR" };
		
		String[] arr4 = new String[]{ ENVIRONMENT_FOX7, "", "", "111022", "Invalid refresh token", "ERROR" };
		String[] arr5 = new String[]{ ENVIRONMENT_FOX7, "", "eyJ2ZXJzaW9uIjogMCwidG9rZW4iOiJudWxsIn0=", "111022", "Invalid refresh token", "ERROR" };
		String[] arr6 = new String[]{ ENVIRONMENT_FOX7, "eyJ2ZXJzaW9uIjogMCwidG9rZW4iOiJudWxsIn0=", "", "111022", "Invalid refresh token", "ERROR" };
		
		String[] arr7 = new String[]{ ENVIRONMENT_FOX8, "", "", "111022", "Invalid refresh token", "ERROR" };
		String[] arr8 = new String[]{ ENVIRONMENT_FOX8, "", "eyJ2ZXJzaW9uIjogMCwidG9rZW4iOiJudWxsIn0=", "111022", "Invalid refresh token", "ERROR" };
		String[] arr9 = new String[]{ ENVIRONMENT_FOX8, "eyJ2ZXJzaW9uIjogMCwidG9rZW4iOiJudWxsIn0=", "", "111022", "Invalid refresh token", "ERROR" };
		
        Object[][] dataSet = new Object[][]{arr1,arr2,arr3,arr4,arr5,arr6,arr7,arr8,arr9 };
        return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 6, 6);
    }
	
	@DataProvider
	public Object [][] ContactGraph_APIOnlyNonRegisteredEmails(ITestContext testContext)
	{
		String [] arr1 = new String []{ENVIRONMENT_FUNC,"QA"};
		String [] arr2 = new String []{ENVIRONMENT_FOX7,"Fox7"};
		String [] arr3 = new String []{ENVIRONMENT_FOX8,"Fox8"};
		
		 Object[][] dataSet = new Object[][]{ arr1, arr2,arr3 };
	        return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object [][] onlyNonRegisteredEmails_signup(ITestContext testContext)
	{
		String [] arr1 = new String []{ENVIRONMENT_FUNC,"QA"};
		String [] arr2 = new String []{ENVIRONMENT_FOX7,"Fox7"};
		String [] arr3 = new String []{ENVIRONMENT_FOX8,"Fox8"};
		
		 Object[][] dataSet = new Object[][]{ arr1, arr2,arr3 };
	        return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object [][] contactGraphAPI_onlyNonRegisteredPhones(ITestContext testContext)
	{
		String [] arr1 = new String []{ENVIRONMENT_FUNC,"QA"};
		String [] arr2 = new String []{ENVIRONMENT_FOX7,"Fox7"};
		String [] arr3 = new String []{ENVIRONMENT_FOX8,"Fox8"};
		
		 Object[][] dataSet = new Object[][]{ arr1, arr2,arr3 };
	        return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object [][] contactGraphAPI_onlyNonRegisteredPhones_signup(ITestContext testContext)
	{
		String [] arr1 = new String []{ENVIRONMENT_FUNC,"QA"};
		String [] arr2 = new String []{ENVIRONMENT_FOX7,"Fox7"};
		String [] arr3 = new String []{ENVIRONMENT_FOX8,"Fox8"};
		
		 Object[][] dataSet = new Object[][]{ arr1, arr2,arr3 };
	        return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object [][] contactGraphAPI_groupNonRegisteredPhonesAndEmails(ITestContext testContext)
	{
		String [] arr1 = new String []{ENVIRONMENT_FUNC,"QA"};
		String [] arr2 = new String []{ENVIRONMENT_FOX7,"Fox7"};
		String [] arr3 = new String []{ENVIRONMENT_FOX8,"Fox8"};
		
		 Object[][] dataSet = new Object[][]{ arr1, arr2,arr3 };
	        return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object [][] contactGraphAPI_combiNonRegisteredPhonesAndEmails(ITestContext testContext)
	{
		String [] arr1 = new String []{ENVIRONMENT_FUNC,"QA"};
		String [] arr2 = new String []{ENVIRONMENT_FOX7,"Fox7"};
		String [] arr3 = new String []{ENVIRONMENT_FOX8,"Fox8"};
		
		 Object[][] dataSet = new Object[][]{ arr1, arr2,arr3 };
	        return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	
	public static String generateRandomId()
	{
		String uuid = UUID.randomUUID().toString();
		return uuid.substring(uuid.lastIndexOf("-") + 1, uuid.length());
	}
	
	public static String generateRandomMobileNumber(){
	    String val = String.valueOf(new Random(System.currentTimeMillis()).nextLong());
	    return val.substring(1, 11).replace(""+val.charAt(1), "9");
	}
	
	
}
