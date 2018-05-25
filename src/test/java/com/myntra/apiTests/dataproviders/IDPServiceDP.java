package com.myntra.apiTests.dataproviders;

import java.util.List;
import java.util.UUID;

import com.myntra.apiTests.InitializeFramework;
import com.myntra.apiTests.common.Myntra;
import com.myntra.apiTests.portalservices.commons.CommonUtils;
import com.myntra.apiTests.portalservices.idpservice.IDPServiceConstants;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import com.jayway.jsonpath.JsonPath;
import com.myntra.apiTests.portalservices.idpservice.IDPServiceDataProviderEnum;
import com.myntra.lordoftherings.Toolbox;
import com.myntra.apiTests.common.APINAME;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.apiTests.common.ServiceType;

/**
 * @author shankara.c
 *
 */
public class IDPServiceDP extends CommonUtils implements IDPServiceConstants
{
	static Logger log = Logger.getLogger(IDPServiceDP.class);
	String environment;
	
	public IDPServiceDP()
	{
		if(System.getenv(IDPServiceDataProviderEnum.ENVIRONMENT.name()) == null)
			environment = CONFIGURATION.GetTestEnvironemnt().toString();
		else
			environment = System.getenv(IDPServiceDataProviderEnum.ENVIRONMENT.name());
	}
	
	@DataProvider
    public Object[][] IDPServiceDP_signInDataProvider_verifyAPIResponse(ITestContext testContext)
	{
		String[] arr1 = new String[] { ENVIRONMENT_PROD, PROD_VALID_USERNAME1, PROD_VALID_PASSWORD1, SUCCESS_RESP_CODE };
		String[] arr2 = new String[] { ENVIRONMENT_FOX7, FOX7_VALID_USERNAME1, FOX7_VALID_PASSWORD1, SUCCESS_RESP_CODE };
		String[] arr3 = new String[] { ENVIRONMENT_FUNC, FUNC_VALID_USERNAME1, FUNC_VALID_PASSWORD1, SUCCESS_RESP_CODE };
		String[] arr4 = new String[] { ENVIRONMENT_PERF, PERF_VALID_USERNAME1, PERF_VALID_PASSWORD1, SUCCESS_RESP_CODE };
			
		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4 };
		return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 1, 1);
    }
	
	@DataProvider
    public Object[][] IDPServiceDP_signInDataProvider_positiveCases(ITestContext testContext)
	{
		String[] arr1 = new String[] { ENVIRONMENT_PROD, PROD_VALID_USERNAME1, PROD_VALID_PASSWORD1, SUCCESS_STATUS_CODE, SUCCESS_STATUS_MSG, SUCCESS_STATUS_TYPE };
		String[] arr2 = new String[] { ENVIRONMENT_FOX7, FOX7_VALID_USERNAME1, FOX7_VALID_PASSWORD1, SUCCESS_STATUS_CODE, SUCCESS_STATUS_MSG, SUCCESS_STATUS_TYPE };
		String[] arr3 = new String[] { ENVIRONMENT_FUNC, FUNC_VALID_USERNAME1, FUNC_VALID_PASSWORD1, SUCCESS_STATUS_CODE, SUCCESS_STATUS_MSG, SUCCESS_STATUS_TYPE };
		String[] arr4 = new String[] { ENVIRONMENT_PERF, PERF_VALID_USERNAME1, PERF_VALID_PASSWORD1, SUCCESS_STATUS_CODE, SUCCESS_STATUS_MSG, SUCCESS_STATUS_TYPE };
			
		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4 };
		return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 1, 1);
    }
	
	@DataProvider
    public Object[][] IDPServiceDP_signInDataProvider_negetiveCases(ITestContext testContext)
	{
		String[] arr1 = new String[] { ENVIRONMENT_PROD, PROD_INVALID_USERNAME1, PROD_INVALID_PASSWORD1, SIGNIN_FAILURE_STATUS_CODE, 
					SIGNIN_FAILURE_STATUS_MSG, FAILURE_STATUS_TYPE };
		String[] arr2 = new String[] { ENVIRONMENT_FOX7, FOX7_INVALID_USERNAME1, FOX7_INVALID_PASSWORD1, SIGNIN_FAILURE_STATUS_CODE, 
					SIGNIN_FAILURE_STATUS_MSG, FAILURE_STATUS_TYPE };
		String[] arr3 = new String[] { ENVIRONMENT_FUNC, FUNC_INVALID_USERNAME1, FUNC_INVALID_PASSWORD1, SIGNIN_FAILURE_STATUS_CODE,
					SIGNIN_FAILURE_STATUS_MSG, FAILURE_STATUS_TYPE };
		String[] arr4 = new String[] { ENVIRONMENT_PERF, PERF_INVALID_USERNAME1, PERF_INVALID_PASSWORD1, SIGNIN_FAILURE_STATUS_CODE,
				SIGNIN_FAILURE_STATUS_MSG, FAILURE_STATUS_TYPE };
		
		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4 };
		return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 1, 1);
    }
	
	@DataProvider
    public Object[][] IDPServiceDP_signInDataProvider_verifyUserLoginNodes(ITestContext testContext)
	{ 
		String[] arr1 = new String[] { ENVIRONMENT_PROD, PROD_VALID_USERNAME1, PROD_VALID_PASSWORD1, VALID_MOBILE_LENGTH };
		String[] arr2 = new String[] { ENVIRONMENT_FOX7, FOX7_VALID_USERNAME1, FOX7_VALID_PASSWORD1, VALID_MOBILE_LENGTH };
		String[] arr3 = new String[] { ENVIRONMENT_FUNC, FUNC_VALID_USERNAME1, FUNC_VALID_PASSWORD1, VALID_MOBILE_LENGTH };
		String[] arr4 = new String[] { ENVIRONMENT_PERF, PERF_VALID_USERNAME1, PERF_VALID_PASSWORD1, VALID_MOBILE_LENGTH };
		
		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4 };
		return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 1, 1);
    }
	
	@DataProvider
	public Object[][] IDPServiceDP_signUpDataProvider_verifyAPIResponse(ITestContext testContext)
	{
		String myntraLoginId = generateRandomId() + SIGNUP_MYNT_DOMAIN;
		String gmailLoginId = generateRandomId() + SIGNUP_GMAIL_DOMAIN;
		String firstName = SIGNUP_FIRST_NAME + generateRandomId();
		String lastName = generateRandomId() + SIGNUP_LAST_NAME;
		
		String[] arr1 = { ENVIRONMENT_PROD, myntraLoginId, SIGNUP_PASSWORD1, SIGNUP_USER_TYPE, SIGNUP_GENDER_MALE, SIGNUP_TITLE_MR, firstName, lastName, myntraLoginId,
				SIGNUP_MOBILE_NUMBER, SUCCESS_RESP_CODE };
		String[] arr2 = { ENVIRONMENT_FOX7, myntraLoginId, SIGNUP_PASSWORD1, SIGNUP_USER_TYPE, SIGNUP_GENDER_MALE, SIGNUP_TITLE_MR, firstName, lastName, myntraLoginId,
				SIGNUP_MOBILE_NUMBER, SUCCESS_RESP_CODE };
		String[] arr3 = { ENVIRONMENT_FUNC, myntraLoginId, SIGNUP_PASSWORD1, SIGNUP_USER_TYPE, SIGNUP_GENDER_MALE, SIGNUP_TITLE_MR, firstName, lastName, myntraLoginId,
				SIGNUP_MOBILE_NUMBER, SUCCESS_RESP_CODE };
		String[] arr4 = { ENVIRONMENT_PERF, myntraLoginId, SIGNUP_PASSWORD1, SIGNUP_USER_TYPE, SIGNUP_GENDER_MALE, SIGNUP_TITLE_MR, firstName, lastName, myntraLoginId,
				SIGNUP_MOBILE_NUMBER, SUCCESS_RESP_CODE };

		String[] arr5 = { ENVIRONMENT_PROD, gmailLoginId, SIGNUP_PASSWORD2, SIGNUP_USER_TYPE, SIGNUP_GENDER_FEMALE, SIGNUP_TITLE_MRS, firstName, lastName, gmailLoginId,
				SIGNUP_MOBILE_NUMBER, SUCCESS_RESP_CODE };
		String[] arr6 = { ENVIRONMENT_FOX7, gmailLoginId, SIGNUP_PASSWORD2, SIGNUP_USER_TYPE, SIGNUP_GENDER_FEMALE, SIGNUP_TITLE_MRS, firstName, lastName, gmailLoginId,
				SIGNUP_MOBILE_NUMBER, SUCCESS_RESP_CODE };
		String[] arr7 = { ENVIRONMENT_FUNC, gmailLoginId, SIGNUP_PASSWORD2, SIGNUP_USER_TYPE, SIGNUP_GENDER_FEMALE, SIGNUP_TITLE_MRS, firstName, lastName, gmailLoginId, 
				SIGNUP_MOBILE_NUMBER, SUCCESS_RESP_CODE };
		String[] arr8 = { ENVIRONMENT_PERF, gmailLoginId, SIGNUP_PASSWORD2, SIGNUP_USER_TYPE, SIGNUP_GENDER_FEMALE, SIGNUP_TITLE_MRS, firstName, lastName, gmailLoginId, 
				SIGNUP_MOBILE_NUMBER, SUCCESS_RESP_CODE };
		
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8 };
		return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] IDPServiceDP_signUpDataProvider_positiveCases(ITestContext testContext)
	{
		String myntraLoginId = generateRandomId() + SIGNUP_MYNT_DOMAIN;
		String gmailLoginId = generateRandomId() + SIGNUP_GMAIL_DOMAIN;
		String firstName = SIGNUP_FIRST_NAME + generateRandomId();
		String lastName = generateRandomId() + SIGNUP_LAST_NAME;
		
		String[] arr1 = { ENVIRONMENT_PROD, myntraLoginId, SIGNUP_PASSWORD1, SIGNUP_USER_TYPE, SIGNUP_GENDER_MALE, SIGNUP_TITLE_MR, firstName, lastName, myntraLoginId, 
				SIGNUP_MOBILE_NUMBER, SUCCESS_STATUS_CODE, SUCCESS_STATUS_MSG, SUCCESS_STATUS_TYPE };
		String[] arr2 = { ENVIRONMENT_FOX7, myntraLoginId, SIGNUP_PASSWORD1, SIGNUP_USER_TYPE, SIGNUP_GENDER_MALE, SIGNUP_TITLE_MR, firstName, lastName, myntraLoginId,
				SIGNUP_MOBILE_NUMBER, SUCCESS_STATUS_CODE, SUCCESS_STATUS_MSG, SUCCESS_STATUS_TYPE };
		String[] arr3 = { ENVIRONMENT_FUNC, myntraLoginId, SIGNUP_PASSWORD1, SIGNUP_USER_TYPE, SIGNUP_GENDER_MALE, SIGNUP_TITLE_MR, firstName, lastName, myntraLoginId,
				SIGNUP_MOBILE_NUMBER, SUCCESS_STATUS_CODE, SUCCESS_STATUS_MSG, SUCCESS_STATUS_TYPE };
		String[] arr4 = { ENVIRONMENT_PERF, myntraLoginId, SIGNUP_PASSWORD1, SIGNUP_USER_TYPE, SIGNUP_GENDER_MALE, SIGNUP_TITLE_MR, firstName, lastName, myntraLoginId,
				SIGNUP_MOBILE_NUMBER, SUCCESS_STATUS_CODE, SUCCESS_STATUS_MSG, SUCCESS_STATUS_TYPE };
		
		String[] arr5 = { ENVIRONMENT_PROD, gmailLoginId, SIGNUP_PASSWORD2, SIGNUP_USER_TYPE, SIGNUP_GENDER_FEMALE, SIGNUP_TITLE_MRS, firstName, lastName, gmailLoginId, 
				SIGNUP_MOBILE_NUMBER, SUCCESS_STATUS_CODE, SUCCESS_STATUS_MSG, SUCCESS_STATUS_TYPE };
		String[] arr6 = { ENVIRONMENT_FOX7, gmailLoginId, SIGNUP_PASSWORD2, SIGNUP_USER_TYPE, SIGNUP_GENDER_FEMALE, SIGNUP_TITLE_MRS, firstName, lastName, gmailLoginId,
				SIGNUP_MOBILE_NUMBER, SUCCESS_STATUS_CODE, SUCCESS_STATUS_MSG, SUCCESS_STATUS_TYPE };
		String[] arr7 = { ENVIRONMENT_FUNC, gmailLoginId, SIGNUP_PASSWORD2, SIGNUP_USER_TYPE, SIGNUP_GENDER_FEMALE, SIGNUP_TITLE_MRS, firstName, lastName, gmailLoginId,
				SIGNUP_MOBILE_NUMBER, SUCCESS_STATUS_CODE, SUCCESS_STATUS_MSG, SUCCESS_STATUS_TYPE };
		String[] arr8 = { ENVIRONMENT_PERF, gmailLoginId, SIGNUP_PASSWORD2, SIGNUP_USER_TYPE, SIGNUP_GENDER_FEMALE, SIGNUP_TITLE_MRS, firstName, lastName, gmailLoginId,
				SIGNUP_MOBILE_NUMBER, SUCCESS_STATUS_CODE, SUCCESS_STATUS_MSG, SUCCESS_STATUS_TYPE };
		
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8 };
		return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] IDPServiceDP_signUpDataProvider_negetiveCases(ITestContext testContext)
	{
		String firstName = SIGNUP_FIRST_NAME + generateRandomId();
		String lastName = generateRandomId() + SIGNUP_LAST_NAME;
		
		String[] arr1 = { ENVIRONMENT_PROD, PROD_VALID_USERNAME1, SIGNUP_PASSWORD1, SIGNUP_USER_TYPE, SIGNUP_GENDER_MALE, SIGNUP_TITLE_MR, firstName, lastName,
				PROD_VALID_USERNAME1, SIGNUP_MOBILE_NUMBER, SIGNUP_FAILURE_STATUS_CODE1, SIGNUP_FAILURE_STATUS_MSG1, FAILURE_STATUS_TYPE };
		String[] arr2 = { ENVIRONMENT_FOX7, FOX7_VALID_USERNAME1, SIGNUP_PASSWORD1, SIGNUP_USER_TYPE, SIGNUP_GENDER_MALE, SIGNUP_TITLE_MR, firstName, lastName,
				FOX7_VALID_USERNAME1, SIGNUP_MOBILE_NUMBER, SIGNUP_FAILURE_STATUS_CODE1, SIGNUP_FAILURE_STATUS_MSG1, FAILURE_STATUS_TYPE };
		String[] arr3 = { ENVIRONMENT_FUNC, FUNC_VALID_USERNAME1, SIGNUP_PASSWORD1, SIGNUP_USER_TYPE, SIGNUP_GENDER_MALE, SIGNUP_TITLE_MR, firstName, lastName,
				FUNC_VALID_USERNAME1, SIGNUP_MOBILE_NUMBER, SIGNUP_FAILURE_STATUS_CODE1, SIGNUP_FAILURE_STATUS_MSG1, FAILURE_STATUS_TYPE };
		String[] arr4 = { ENVIRONMENT_PERF, PERF_VALID_USERNAME1, SIGNUP_PASSWORD1, SIGNUP_USER_TYPE, SIGNUP_GENDER_MALE, SIGNUP_TITLE_MR, firstName, lastName,
				PERF_VALID_USERNAME1, SIGNUP_MOBILE_NUMBER, SIGNUP_FAILURE_STATUS_CODE1, SIGNUP_FAILURE_STATUS_MSG1, FAILURE_STATUS_TYPE };
		
		String[] arr5 = { ENVIRONMENT_PROD, SIGNUP_EMPTY_LOGINID, SIGNUP_PASSWORD2, SIGNUP_USER_TYPE, SIGNUP_GENDER_FEMALE, SIGNUP_TITLE_MRS, firstName, lastName,
				SIGNUP_EMPTY_LOGINID, SIGNUP_MOBILE_NUMBER, SIGNUP_FAILURE_STATUS_CODE2, SIGNUP_FAILURE_STATUS_MSG2, FAILURE_STATUS_TYPE };
		String[] arr6 = { ENVIRONMENT_FOX7, SIGNUP_EMPTY_LOGINID, SIGNUP_PASSWORD2, SIGNUP_USER_TYPE, SIGNUP_GENDER_FEMALE, SIGNUP_TITLE_MRS, firstName, lastName,
				SIGNUP_EMPTY_LOGINID, SIGNUP_MOBILE_NUMBER, SIGNUP_FAILURE_STATUS_CODE2, SIGNUP_FAILURE_STATUS_MSG2, FAILURE_STATUS_TYPE };
		String[] arr7 = { ENVIRONMENT_FUNC, SIGNUP_EMPTY_LOGINID, SIGNUP_PASSWORD2, SIGNUP_USER_TYPE, SIGNUP_GENDER_FEMALE, SIGNUP_TITLE_MRS, firstName, lastName,
				SIGNUP_EMPTY_LOGINID, SIGNUP_MOBILE_NUMBER, SIGNUP_FAILURE_STATUS_CODE2, SIGNUP_FAILURE_STATUS_MSG2, FAILURE_STATUS_TYPE };
		String[] arr8 = { ENVIRONMENT_PERF, SIGNUP_EMPTY_LOGINID, SIGNUP_PASSWORD2, SIGNUP_USER_TYPE, SIGNUP_GENDER_FEMALE, SIGNUP_TITLE_MRS, firstName, lastName,
				SIGNUP_EMPTY_LOGINID, SIGNUP_MOBILE_NUMBER, SIGNUP_FAILURE_STATUS_CODE2, SIGNUP_FAILURE_STATUS_MSG2, FAILURE_STATUS_TYPE };
		
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8 };
		return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] IDPServiceDP_signUpDataProvider_verifyUserLoginNodes(ITestContext testContext)
	{
		String myntraLoginId = generateRandomId() + SIGNUP_MYNT_DOMAIN;
		String gmailLoginId = generateRandomId() + SIGNUP_GMAIL_DOMAIN;
		String firstName = SIGNUP_FIRST_NAME + generateRandomId();
		String lastName = generateRandomId() + SIGNUP_FIRST_NAME;
		
		String[] arr1 = { ENVIRONMENT_PROD, myntraLoginId, SIGNUP_PASSWORD1, SIGNUP_USER_TYPE, SIGNUP_GENDER_MALE, SIGNUP_TITLE_MR, firstName, lastName, myntraLoginId,
				SIGNUP_MOBILE_NUMBER, SUCCESS_STATUS_TYPE, VALID_MOBILE_LENGTH };
		String[] arr2 = { ENVIRONMENT_FOX7, myntraLoginId, SIGNUP_PASSWORD1, SIGNUP_USER_TYPE, SIGNUP_GENDER_MALE, SIGNUP_TITLE_MR, firstName, lastName, myntraLoginId,
				SIGNUP_MOBILE_NUMBER, SUCCESS_STATUS_TYPE, VALID_MOBILE_LENGTH };
		String[] arr3 = { ENVIRONMENT_FUNC, myntraLoginId, SIGNUP_PASSWORD1, SIGNUP_USER_TYPE, SIGNUP_GENDER_MALE, SIGNUP_TITLE_MR, firstName, lastName, myntraLoginId,
				SIGNUP_MOBILE_NUMBER, SUCCESS_STATUS_TYPE, VALID_MOBILE_LENGTH };
		String[] arr4 = { ENVIRONMENT_PERF, myntraLoginId, SIGNUP_PASSWORD1, SIGNUP_USER_TYPE, SIGNUP_GENDER_MALE, SIGNUP_TITLE_MR, firstName, lastName, myntraLoginId,
				SIGNUP_MOBILE_NUMBER, SUCCESS_STATUS_TYPE, VALID_MOBILE_LENGTH };

		String[] arr5 = { ENVIRONMENT_PROD, gmailLoginId, SIGNUP_PASSWORD2, SIGNUP_USER_TYPE, SIGNUP_GENDER_FEMALE, SIGNUP_TITLE_MRS, firstName, lastName, gmailLoginId,
				SIGNUP_MOBILE_NUMBER, SUCCESS_STATUS_TYPE, VALID_MOBILE_LENGTH };
		String[] arr6 = { ENVIRONMENT_FOX7, gmailLoginId, SIGNUP_PASSWORD2, SIGNUP_USER_TYPE, SIGNUP_GENDER_FEMALE, SIGNUP_TITLE_MRS, firstName, lastName, gmailLoginId,
				SIGNUP_MOBILE_NUMBER, SUCCESS_STATUS_TYPE, VALID_MOBILE_LENGTH };
		String[] arr7 = { ENVIRONMENT_FUNC, gmailLoginId, SIGNUP_PASSWORD2, SIGNUP_USER_TYPE, SIGNUP_GENDER_FEMALE, SIGNUP_TITLE_MRS, firstName, lastName, gmailLoginId,
				SIGNUP_MOBILE_NUMBER, SUCCESS_STATUS_TYPE, VALID_MOBILE_LENGTH };
		String[] arr8 = { ENVIRONMENT_PERF, gmailLoginId, SIGNUP_PASSWORD2, SIGNUP_USER_TYPE, SIGNUP_GENDER_FEMALE, SIGNUP_TITLE_MRS, firstName, lastName, gmailLoginId,
				SIGNUP_MOBILE_NUMBER, SUCCESS_STATUS_TYPE, VALID_MOBILE_LENGTH };
		
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8 };
		return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
    public Object[][] IDPServiceDP_signOutDataProvider_verifyAPIResponse(ITestContext testContext)
	{
		String[] arr1 = new String[] { ENVIRONMENT_PROD, PROD_VALID_USERNAME1, PROD_VALID_PASSWORD1, SUCCESS_RESP_CODE };
		String[] arr2 = new String[] { ENVIRONMENT_FOX7, FOX7_VALID_USERNAME1, FOX7_VALID_PASSWORD1, SUCCESS_RESP_CODE };
		String[] arr3 = new String[] { ENVIRONMENT_FUNC, FUNC_VALID_USERNAME1, FUNC_VALID_PASSWORD1, SUCCESS_RESP_CODE };
		String[] arr4 = new String[] { ENVIRONMENT_PERF, PERF_VALID_USERNAME1, PERF_VALID_PASSWORD1, SUCCESS_RESP_CODE };
			
		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4 };
		return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 1, 1);
    }
	
	@DataProvider
    public Object[][] IDPServiceDP_signOutDataProvider_positiveCases(ITestContext testContext)
	{
		String[] arr1 = new String[] { ENVIRONMENT_PROD, PROD_VALID_USERNAME1, PROD_VALID_PASSWORD1, SUCCESS_STATUS_CODE, SUCCESS_STATUS_MSG, SUCCESS_STATUS_TYPE };
		String[] arr2 = new String[] { ENVIRONMENT_FOX7, FOX7_VALID_USERNAME1, FOX7_VALID_PASSWORD1, SUCCESS_STATUS_CODE, SUCCESS_STATUS_MSG, SUCCESS_STATUS_TYPE };
		String[] arr3 = new String[] { ENVIRONMENT_FUNC, FUNC_VALID_USERNAME1, FUNC_VALID_PASSWORD1, SUCCESS_STATUS_CODE, SUCCESS_STATUS_MSG, SUCCESS_STATUS_TYPE };
		String[] arr4 = new String[] { ENVIRONMENT_PERF, PERF_VALID_USERNAME1, PERF_VALID_PASSWORD1, SUCCESS_STATUS_CODE, SUCCESS_STATUS_MSG, SUCCESS_STATUS_TYPE };
		
		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4 };
		return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 1, 1);
    }
	
	@DataProvider
    public Object[][] IDPServiceDP_forgotPasswordDataProvider_verifyAPIResponse(ITestContext testContext)
	{
		String[] arr1 = new String[] { ENVIRONMENT_PROD, PROD_VALID_USERNAME1, SUCCESS_RESP_CODE };
		String[] arr2 = new String[] { ENVIRONMENT_FOX7, FOX7_VALID_USERNAME1, SUCCESS_RESP_CODE };
		String[] arr3 = new String[] { ENVIRONMENT_FUNC, FUNC_VALID_USERNAME1, SUCCESS_RESP_CODE };
		String[] arr4 = new String[] { ENVIRONMENT_PERF, PERF_VALID_USERNAME1, SUCCESS_RESP_CODE };
			
		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4 };
		return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 1, 1);
    }
	
	@DataProvider
    public Object[][] IDPServiceDP_forgotPasswordDataProvider_positiveCases(ITestContext testContext)
	{
		String[] arr1 = new String[] { ENVIRONMENT_PROD, PROD_VALID_USERNAME1, SUCCESS_STATUS_CODE, SUCCESS_STATUS_MSG, SUCCESS_STATUS_TYPE };
		String[] arr2 = new String[] { ENVIRONMENT_FOX7, FOX7_VALID_USERNAME1, SUCCESS_STATUS_CODE, SUCCESS_STATUS_MSG, SUCCESS_STATUS_TYPE };
		String[] arr3 = new String[] { ENVIRONMENT_FUNC, FUNC_VALID_USERNAME1, SUCCESS_STATUS_CODE, SUCCESS_STATUS_MSG, SUCCESS_STATUS_TYPE };
		String[] arr4 = new String[] { ENVIRONMENT_PERF, PERF_VALID_USERNAME1, SUCCESS_STATUS_CODE, SUCCESS_STATUS_MSG, SUCCESS_STATUS_TYPE };
			
		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4 };
		return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 1, 1);
    } 
	
	@DataProvider
    public Object[][] IDPServiceDP_forgotPasswordDataProvider_negetiveCases(ITestContext testContext)
	{
		String[] arr1 = new String[] { ENVIRONMENT_PROD, PROD_INVALID_USERNAME1, FP_ERROR_STATUS_CODE1, FP_ERROR_STATUS_MSG1, FAILURE_STATUS_TYPE };
		String[] arr2 = new String[] { ENVIRONMENT_FOX7, FOX7_INVALID_USERNAME1, FP_ERROR_STATUS_CODE1, FP_ERROR_STATUS_MSG1, FAILURE_STATUS_TYPE };
		String[] arr3 = new String[] { ENVIRONMENT_FUNC, FUNC_INVALID_USERNAME1, FP_ERROR_STATUS_CODE1, FP_ERROR_STATUS_MSG1, FAILURE_STATUS_TYPE };
		String[] arr4 = new String[] { ENVIRONMENT_PERF, PERF_INVALID_USERNAME1, FP_ERROR_STATUS_CODE1, FP_ERROR_STATUS_MSG1, FAILURE_STATUS_TYPE };
		
		String[] arr5 = new String[] { ENVIRONMENT_PROD, PROD_INVALID_USERNAME2, FP_ERROR_STATUS_CODE2, FP_ERROR_STATUS_MSG2, FAILURE_STATUS_TYPE };
		String[] arr6 = new String[] { ENVIRONMENT_FOX7, FOX7_INVALID_USERNAME2, FP_ERROR_STATUS_CODE2, FP_ERROR_STATUS_MSG2, FAILURE_STATUS_TYPE };
		String[] arr7 = new String[] { ENVIRONMENT_FUNC, FUNC_INVALID_USERNAME2, FP_ERROR_STATUS_CODE1, FP_ERROR_STATUS_MSG2, FAILURE_STATUS_TYPE };
		String[] arr8 = new String[] { ENVIRONMENT_PERF, PERF_INVALID_USERNAME2, FP_ERROR_STATUS_CODE1, FP_ERROR_STATUS_MSG2, FAILURE_STATUS_TYPE };

		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8 };
		return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 1,1);
    }
	
	@DataProvider
    public Object[][] IDPServiceDP_resetPasswordDataProvider_verifyAPIResponse(ITestContext testContext)
	{
		String[] arr2 = new String[] { ENVIRONMENT_FOX7, FOX7_RP_VALID_USERNAME, generateRandomId(), IDPServiceDataProviderEnum.ENVIRONMENT_FOX7.name(), SUCCESS_RESP_CODE };
		String[] arr3 = new String[] { ENVIRONMENT_FUNC, FUNC_RP_VALID_USERNAME, generateRandomId(), IDPServiceDataProviderEnum.ENVIRONMENT_FUNC.name(), SUCCESS_RESP_CODE };
		String[] arr4 = new String[] { ENVIRONMENT_PERF, PERF_RP_VALID_USERNAME, generateRandomId(), IDPServiceDataProviderEnum.ENVIRONMENT_PERF.name(), SUCCESS_RESP_CODE };
			
		Object[][] dataSet = new Object[][]{ arr2, arr3, arr4 };
		return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 1,1);
    }

	@DataProvider
    public Object[][] IDPServiceDP_resetPasswordDataProvider_positiveCases(ITestContext testContext)
	{
		String[] arr2 = new String[] { ENVIRONMENT_FOX7, FOX7_RP_VALID_USERNAME, generateRandomId(), IDPServiceDataProviderEnum.ENVIRONMENT_FOX7.name(),
				SUCCESS_STATUS_CODE, SUCCESS_STATUS_MSG, SUCCESS_STATUS_TYPE };
		String[] arr3 = new String[] { ENVIRONMENT_FUNC, FUNC_RP_VALID_USERNAME, generateRandomId(), IDPServiceDataProviderEnum.ENVIRONMENT_FUNC.name(),
				SUCCESS_STATUS_CODE, SUCCESS_STATUS_MSG, SUCCESS_STATUS_TYPE };
		String[] arr4 = new String[] { ENVIRONMENT_PERF, PERF_RP_VALID_USERNAME, generateRandomId(), IDPServiceDataProviderEnum.ENVIRONMENT_FUNC.name(),
				SUCCESS_STATUS_CODE, SUCCESS_STATUS_MSG, SUCCESS_STATUS_TYPE };

		Object[][] dataSet = new Object[][]{ arr2, arr3, arr4 };
		return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 1,1);
    }
	
	@DataProvider
    public Object[][] IDPServiceDP_resetPasswordDataProvider_negetiveCases(ITestContext testContext)
	{
		String[] arr1 = new String[] { ENVIRONMENT_PROD, PROD_RP_VALID_USERNAME, PROD_RP_VALID_PASSWORD, "", RP_ERROR_STATUS_CODE1, RP_ERROR_STATUS_MSG1,
				FAILURE_STATUS_TYPE };
		String[] arr2 = new String[] { ENVIRONMENT_FOX7, FOX7_RP_VALID_USERNAME, FOX7_RP_VALID_PASSWORD, "", RP_ERROR_STATUS_CODE1, RP_ERROR_STATUS_MSG1,
				FAILURE_STATUS_TYPE };
		String[] arr3 = new String[] { ENVIRONMENT_FUNC, FUNC_RP_VALID_USERNAME, FUNC_RP_VALID_PASSWORD, "", RP_ERROR_STATUS_CODE1, RP_ERROR_STATUS_MSG1,
				FAILURE_STATUS_TYPE };
		String[] arr4 = new String[] { ENVIRONMENT_PERF, PERF_RP_VALID_USERNAME, PERF_RP_VALID_PASSWORD, "", RP_ERROR_STATUS_CODE1, RP_ERROR_STATUS_MSG1,
				FAILURE_STATUS_TYPE };
		
		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4 };
		return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 1,1);
    }
	
	@DataProvider
    public Object[][] IDPServiceDP_fbLoginDataProvider_verifyAPIResponse(ITestContext testContext)
	{
            
		Object[][] dataSet = new Object[][]{  };
		return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 1,1);
    }
	
	@DataProvider
	public Object[][] GetAllMyntraUrlsFromTopNav(ITestContext Context)
	{
		Object[][] dataSet;
		ServiceType serviceundertest = ServiceType.PORTAL_MOBILEAPPWEBSERVICES;
		APINAME apiundertest = APINAME.MOBILEAPPGETNAV;
		MyntraService service = Myntra.getService(serviceundertest,
				apiundertest, InitializeFramework.init.Configurations);
		RequestGenerator request = new RequestGenerator(service);
		String response = request.respvalidate.returnresponseasstring();

		List<String> urls = JsonPath.read(response,
				"$.data..categories[*].linkUrl.resourceValue");
		Assert.assertEquals(200, request.response.getStatus());
		List<String> absoluteurls = Toolbox.getAbsoluteUrlSet(urls);
		dataSet = new Object[absoluteurls.size()][];
		int counter = 0;
		for (String url : absoluteurls)
		{
			System.out.println("url = " + url);
			String url1 = url;
			dataSet[counter] = new Object[] { url1 };
			counter++;
		}
		return Toolbox.returnReducedDataSet(dataSet,
				Context.getIncludedGroups(), 5, 5);
	}

	@DataProvider
	public Object[][] idpSignUpDataProvider(ITestContext iTestContext)
	{

		String[] arr1 = { "infrareferrer@gmail.com",
				"ashishbajpai@localhost.com", "password123", "C", "M", "Mr.",
				"Ashish", "Bajpai", "ashishbajpai@localhost.com", "9876543210" };
		String[] arr2 = { "infrareferrer@gmail.com",
				"ashishbajpai@localhost.com", "password123", "C", "M", "Mr.",
				"Ashish", "Bajpai", "ashishbajpai@localhost.com", "9876543210" };
		String[] arr3 = { "infrareferrer@gmail.com",
				"ashishbajpai@localhost.com", "password123", "C", "M", "Mr.",
				"Ashish", "Bajpai", "ashishbajpai@localhost.com", "9876543210" };
		String[] arr4 = { "infrareferrer@gmail.com",
				"ashishbajpai@localhost.com", "password123", "C", "M", "Mr.",
				"Ashish", "Bajpai", "ashishbajpai@localhost.com", "9876543210" };

		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4 };
		return Toolbox.returnReducedDataSet(dataSet,
				iTestContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public Object[][] iDpSignInDataProvider(ITestContext iTestContext)
	{
		String[] arr1 = { "mobileappservice-idp@myntra.com", "testing",
				"signin", "200" };

		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				iTestContext.getIncludedGroups(), 1, 1);
	}

	@DataProvider
	public Object[][] iDpIncorrectSignInDataProvider(ITestContext iTestContext)
	{
		String[] arr1 = { "dfdffdfk@fjkdfjkd.com", "Sherlock1@12345", "signin",
				"401" };
		String[] arr2 = { "mohit.jain@myntra.com", "Sherlock@1234567",
				"signin", "401" };

		Object[][] dataSet = new Object[][] { arr1, arr2 };
		return Toolbox.returnReducedDataSet(dataSet,
				iTestContext.getIncludedGroups(), 2, 2);
	}

	@DataProvider
	public Object[][] iDpSignUpDataProvider(ITestContext iTestContext)
	{
		String[] arr1 = { generateRandomId() + "@gmail.com", "123456123",
				"signup", "200" };
		String[] arr2 = { generateRandomId() + "@gmail.com", "123456123",
				"signup", "200" };
		String[] arr3 = { generateRandomId() + "@gmail.com", "123456123",
				"signup", "200" };
		String[] arr4 = { generateRandomId() + "@myntra.com", "123456123",
				"signup", "200" };
		String[] arr5 = { generateRandomId() + "@rediffmail.com", "123456123",
				"signup", "200" };
		String[] arr6 = { generateRandomId() + "@gmail.com", "123456123",
				"signup", "200" };

		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5,
				arr6 };
		return Toolbox.returnReducedDataSet(dataSet,
				iTestContext.getIncludedGroups(), 1, 1);
	}

	@DataProvider
	public Object[][] IDpSignUpForExistingUser(ITestContext iTestContext)
	{
		String[] arr1 = { "mobileappservice-idp@myntra.com", "testing",
				"signup", "405" };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				iTestContext.getIncludedGroups(), 1, 1);
	}

	public static String generateRandomId()
	{
		String uuid = UUID.randomUUID().toString();
		return uuid.substring(uuid.lastIndexOf("-") + 1, uuid.length());
	}
	
	@DataProvider
    public Object[][] IDPServiceDP_signInDataProvider_verifyResponseDataNodesUsingSchemaValidations(ITestContext testContext)
	{
		String[] arr1 = new String[] { ENVIRONMENT_PROD, PROD_VALID_USERNAME1, PROD_VALID_PASSWORD1, SUCCESS_RESP_CODE };
		String[] arr2 = new String[] { ENVIRONMENT_FOX7, FOX7_VALID_USERNAME1, FOX7_VALID_PASSWORD1, SUCCESS_RESP_CODE };
		String[] arr3 = new String[] { ENVIRONMENT_FUNC, FUNC_VALID_USERNAME1, FUNC_VALID_PASSWORD1, SUCCESS_RESP_CODE };
		String[] arr4 = new String[] { ENVIRONMENT_PERF, PERF_VALID_USERNAME1, PERF_VALID_PASSWORD1, SUCCESS_RESP_CODE };
			
		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4 };
		return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 1, 1);
    }
	
	@DataProvider
	public Object[][] IDPServiceDP_signUpDataProvider_verifyResponseDataNodesUsingSchemaValidations(ITestContext testContext)
	{
		String myntraLoginId = generateRandomId() + SIGNUP_MYNT_DOMAIN;
		String gmailLoginId = generateRandomId() + SIGNUP_GMAIL_DOMAIN;
		String firstName = SIGNUP_FIRST_NAME + generateRandomId();
		String lastName = generateRandomId() + SIGNUP_LAST_NAME;
		
		String[] arr1 = { ENVIRONMENT_PROD, myntraLoginId, SIGNUP_PASSWORD1, SIGNUP_USER_TYPE, SIGNUP_GENDER_MALE, SIGNUP_TITLE_MR, firstName, lastName, myntraLoginId,
				SIGNUP_MOBILE_NUMBER, SUCCESS_RESP_CODE };
		String[] arr2 = { ENVIRONMENT_FOX7, myntraLoginId, SIGNUP_PASSWORD1, SIGNUP_USER_TYPE, SIGNUP_GENDER_MALE, SIGNUP_TITLE_MR, firstName, lastName, myntraLoginId,
				SIGNUP_MOBILE_NUMBER, SUCCESS_RESP_CODE };
		String[] arr3 = { ENVIRONMENT_FUNC, myntraLoginId, SIGNUP_PASSWORD1, SIGNUP_USER_TYPE, SIGNUP_GENDER_MALE, SIGNUP_TITLE_MR, firstName, lastName, myntraLoginId,
				SIGNUP_MOBILE_NUMBER, SUCCESS_RESP_CODE };
		String[] arr4 = { ENVIRONMENT_PERF, myntraLoginId, SIGNUP_PASSWORD1, SIGNUP_USER_TYPE, SIGNUP_GENDER_MALE, SIGNUP_TITLE_MR, firstName, lastName, myntraLoginId,
				SIGNUP_MOBILE_NUMBER, SUCCESS_RESP_CODE };

		String[] arr5 = { ENVIRONMENT_PROD, gmailLoginId, SIGNUP_PASSWORD2, SIGNUP_USER_TYPE, SIGNUP_GENDER_FEMALE, SIGNUP_TITLE_MRS, firstName, lastName, gmailLoginId,
				SIGNUP_MOBILE_NUMBER, SUCCESS_RESP_CODE };
		String[] arr6 = { ENVIRONMENT_FOX7, gmailLoginId, SIGNUP_PASSWORD2, SIGNUP_USER_TYPE, SIGNUP_GENDER_FEMALE, SIGNUP_TITLE_MRS, firstName, lastName, gmailLoginId,
				SIGNUP_MOBILE_NUMBER, SUCCESS_RESP_CODE };
		String[] arr7 = { ENVIRONMENT_FUNC, gmailLoginId, SIGNUP_PASSWORD2, SIGNUP_USER_TYPE, SIGNUP_GENDER_FEMALE, SIGNUP_TITLE_MRS, firstName, lastName, gmailLoginId, 
				SIGNUP_MOBILE_NUMBER, SUCCESS_RESP_CODE };
		String[] arr8 = { ENVIRONMENT_PERF, gmailLoginId, SIGNUP_PASSWORD2, SIGNUP_USER_TYPE, SIGNUP_GENDER_FEMALE, SIGNUP_TITLE_MRS, firstName, lastName, gmailLoginId, 
				SIGNUP_MOBILE_NUMBER, SUCCESS_RESP_CODE };
		
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8 };
		return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
    public Object[][] IDPServiceDP_signOutDataProvider_verifyResponseDataNodesUsingSchemaValidations(ITestContext testContext)
	{
		String[] arr1 = new String[] { ENVIRONMENT_PROD, PROD_VALID_USERNAME1, PROD_VALID_PASSWORD1, SUCCESS_RESP_CODE };
		String[] arr2 = new String[] { ENVIRONMENT_FOX7, FOX7_VALID_USERNAME1, FOX7_VALID_PASSWORD1, SUCCESS_RESP_CODE };
		String[] arr3 = new String[] { ENVIRONMENT_FUNC, FUNC_VALID_USERNAME1, FUNC_VALID_PASSWORD1, SUCCESS_RESP_CODE };
		String[] arr4 = new String[] { ENVIRONMENT_PERF, PERF_VALID_USERNAME1, PERF_VALID_PASSWORD1, SUCCESS_RESP_CODE };
			
		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4 };
		return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 1, 1);
    }
	
	@DataProvider
    public Object[][] IDPServiceDP_forgotPasswordDataProvider_verifyResponseDataNodesUsingSchemaValidations(ITestContext testContext)
	{
		String[] arr1 = new String[] { ENVIRONMENT_PROD, PROD_VALID_USERNAME1, SUCCESS_RESP_CODE };
		String[] arr2 = new String[] { ENVIRONMENT_FOX7, FOX7_VALID_USERNAME1, SUCCESS_RESP_CODE };
		String[] arr3 = new String[] { ENVIRONMENT_FUNC, FUNC_VALID_USERNAME1, SUCCESS_RESP_CODE };
		String[] arr4 = new String[] { ENVIRONMENT_PERF, PERF_VALID_USERNAME1, SUCCESS_RESP_CODE };
			
		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4 };
		return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 1, 1);
    }
	
	@DataProvider
    public Object[][] IDPServiceDP_resetPasswordDataProvider_verifyResponseDataNodesUsingSchemaValidations(ITestContext testContext)
	{
		String[] arr2 = new String[] { ENVIRONMENT_FOX7, FOX7_RP_VALID_USERNAME, generateRandomId(), IDPServiceDataProviderEnum.ENVIRONMENT_FOX7.name(), SUCCESS_RESP_CODE };
		String[] arr3 = new String[] { ENVIRONMENT_FUNC, FUNC_RP_VALID_USERNAME, generateRandomId(), IDPServiceDataProviderEnum.ENVIRONMENT_FUNC.name(), SUCCESS_RESP_CODE };
		String[] arr4 = new String[] { ENVIRONMENT_PERF, PERF_RP_VALID_USERNAME, generateRandomId(), IDPServiceDataProviderEnum.ENVIRONMENT_PERF.name(), SUCCESS_RESP_CODE };
			
		Object[][] dataSet = new Object[][]{ arr2, arr3, arr4 };
		return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 1,1);
    }
	
}