/**
 * 
 */
package com.myntra.apiTests.portalservices.idpservice;

import com.myntra.lordoftherings.Configuration;
import com.myntra.lordoftherings.PropertyReader;

/**
 * @author shankara.c
 *
 */
public interface IDPServiceConstants 
{
	public static final Configuration CONFIGURATION = new Configuration("./Data/configuration");
	public static final PropertyReader PROPERTY_READER = new PropertyReader(new String[]{"./Data/dataproviders/idpservicedp.properties"});
	
	// PROD ENV RELATED DATA
	public static final String ENVIRONMENT_PROD = PROPERTY_READER.getPropertyValue(IDPServiceDataProviderEnum.ENVIRONMENT_PROD.name());
	public static final String PROD_VALID_USERNAME1 = PROPERTY_READER.getPropertyValue(IDPServiceDataProviderEnum.PROD_VALID_USERNAME1.name());
	public static final String PROD_VALID_PASSWORD1 = PROPERTY_READER.getPropertyValue(IDPServiceDataProviderEnum.PROD_VALID_PASSWORD1.name());
	public static final String PROD_INVALID_USERNAME1 = PROPERTY_READER.getPropertyValue(IDPServiceDataProviderEnum.PROD_INVALID_USERNAME1.name());
	public static final String PROD_INVALID_PASSWORD1 = PROPERTY_READER.getPropertyValue(IDPServiceDataProviderEnum.PROD_INVALID_PASSWORD1.name());
	public static final String PROD_INVALID_USERNAME2 = PROPERTY_READER.getPropertyValue(IDPServiceDataProviderEnum.PROD_INVALID_USERNAME2.name());
	public static final String PROD_INVALID_PASSWORD2 = PROPERTY_READER.getPropertyValue(IDPServiceDataProviderEnum.PROD_INVALID_PASSWORD2.name());
	public static final String PROD_RP_VALID_USERNAME = PROPERTY_READER.getPropertyValue(IDPServiceDataProviderEnum.PROD_RP_VALID_USERNAME.name());
	public static final String PROD_RP_VALID_PASSWORD = PROPERTY_READER.getPropertyValue(IDPServiceDataProviderEnum.PROD_RP_VALID_PASSWORD.name());
	
	// FOX7 ENV RELATED DATA
	public static final String ENVIRONMENT_FOX7 = PROPERTY_READER.getPropertyValue(IDPServiceDataProviderEnum.ENVIRONMENT_FOX7.name());
	public static final String FOX7_VALID_USERNAME1 = PROPERTY_READER.getPropertyValue(IDPServiceDataProviderEnum.FOX7_VALID_USERNAME1.name());
	public static final String FOX7_VALID_PASSWORD1 = PROPERTY_READER.getPropertyValue(IDPServiceDataProviderEnum.FOX7_VALID_PASSWORD1.name());
	public static final String FOX7_INVALID_USERNAME1 = PROPERTY_READER.getPropertyValue(IDPServiceDataProviderEnum.FOX7_INVALID_USERNAME1.name());
	public static final String FOX7_INVALID_PASSWORD1 = PROPERTY_READER.getPropertyValue(IDPServiceDataProviderEnum.FOX7_INVALID_PASSWORD1.name());
	public static final String FOX7_INVALID_USERNAME2 = PROPERTY_READER.getPropertyValue(IDPServiceDataProviderEnum.FOX7_INVALID_USERNAME2.name());
	public static final String FOX7_INVALID_PASSWORD2 = PROPERTY_READER.getPropertyValue(IDPServiceDataProviderEnum.FOX7_INVALID_PASSWORD2.name());
	public static final String FOX7_RP_VALID_USERNAME = PROPERTY_READER.getPropertyValue(IDPServiceDataProviderEnum.FOX7_RP_VALID_USERNAME.name());
	public static final String FOX7_RP_VALID_PASSWORD = PROPERTY_READER.getPropertyValue(IDPServiceDataProviderEnum.FOX7_RP_VALID_PASSWORD.name());
	
	// FUNC ENV RELATED DATA
	public static final String ENVIRONMENT_FUNC = PROPERTY_READER.getPropertyValue(IDPServiceDataProviderEnum.ENVIRONMENT_FUNC.name());
	public static final String FUNC_VALID_USERNAME1 = PROPERTY_READER.getPropertyValue(IDPServiceDataProviderEnum.FUNC_VALID_USERNAME1.name());
	public static final String FUNC_VALID_PASSWORD1 = PROPERTY_READER.getPropertyValue(IDPServiceDataProviderEnum.FUNC_VALID_PASSWORD1.name());
	public static final String FUNC_INVALID_USERNAME1 = PROPERTY_READER.getPropertyValue(IDPServiceDataProviderEnum.FUNC_INVALID_USERNAME1.name());
	public static final String FUNC_INVALID_PASSWORD1 = PROPERTY_READER.getPropertyValue(IDPServiceDataProviderEnum.FUNC_INVALID_PASSWORD1.name());
	public static final String FUNC_INVALID_USERNAME2 = PROPERTY_READER.getPropertyValue(IDPServiceDataProviderEnum.FUNC_INVALID_USERNAME2.name());
	public static final String FUNC_INVALID_PASSWORD2 = PROPERTY_READER.getPropertyValue(IDPServiceDataProviderEnum.FUNC_INVALID_PASSWORD2.name());
	public static final String FUNC_RP_VALID_USERNAME = PROPERTY_READER.getPropertyValue(IDPServiceDataProviderEnum.FUNC_RP_VALID_USERNAME.name());
	public static final String FUNC_RP_VALID_PASSWORD = PROPERTY_READER.getPropertyValue(IDPServiceDataProviderEnum.FUNC_RP_VALID_PASSWORD.name());
	
	// PERF ENV RELATED DATA
	public static final String ENVIRONMENT_PERF = PROPERTY_READER.getPropertyValue(IDPServiceDataProviderEnum.ENVIRONMENT_PERF.name());
	public static final String PERF_VALID_USERNAME1 = PROPERTY_READER.getPropertyValue(IDPServiceDataProviderEnum.PERF_VALID_USERNAME1.name());
	public static final String PERF_VALID_PASSWORD1 = PROPERTY_READER.getPropertyValue(IDPServiceDataProviderEnum.PERF_VALID_PASSWORD1.name());
	public static final String PERF_INVALID_USERNAME1 = PROPERTY_READER.getPropertyValue(IDPServiceDataProviderEnum.PERF_INVALID_USERNAME1.name());
	public static final String PERF_INVALID_PASSWORD1 = PROPERTY_READER.getPropertyValue(IDPServiceDataProviderEnum.PERF_INVALID_PASSWORD1.name());
	public static final String PERF_INVALID_USERNAME2 = PROPERTY_READER.getPropertyValue(IDPServiceDataProviderEnum.PERF_INVALID_USERNAME2.name());
	public static final String PERF_INVALID_PASSWORD2 = PROPERTY_READER.getPropertyValue(IDPServiceDataProviderEnum.PERF_INVALID_PASSWORD2.name());
	public static final String PERF_RP_VALID_USERNAME = PROPERTY_READER.getPropertyValue(IDPServiceDataProviderEnum.PERF_RP_VALID_USERNAME.name());
	public static final String PERF_RP_VALID_PASSWORD = PROPERTY_READER.getPropertyValue(IDPServiceDataProviderEnum.PERF_RP_VALID_PASSWORD.name());
	
	// SUCCESS RELATED DATA
	public static final String SUCCESS_STATUS_CODE = PROPERTY_READER.getPropertyValue(IDPServiceDataProviderEnum.SUCCESS_STATUS_CODE.name());
	public static final String SUCCESS_STATUS_MSG = PROPERTY_READER.getPropertyValue(IDPServiceDataProviderEnum.SUCCESS_STATUS_MSG.name());
	public static final String SUCCESS_STATUS_TYPE = PROPERTY_READER.getPropertyValue(IDPServiceDataProviderEnum.SUCCESS_STATUS_TYPE.name());
	public static final String SUCCESS_RESP_CODE = PROPERTY_READER.getPropertyValue(IDPServiceDataProviderEnum.SUCCESS_RESP_CODE.name());
	public static final String VALID_MOBILE_LENGTH = PROPERTY_READER.getPropertyValue(IDPServiceDataProviderEnum.VALID_MOBILE_LENGTH.name());
	
	// FAILURE RELATED DATA
	public static final String SIGNIN_FAILURE_STATUS_CODE = PROPERTY_READER.getPropertyValue(IDPServiceDataProviderEnum.SIGNIN_FAILURE_STATUS_CODE.name());
	public static final String SIGNIN_FAILURE_STATUS_MSG = PROPERTY_READER.getPropertyValue(IDPServiceDataProviderEnum.SIGNIN_FAILURE_STATUS_MSG.name());
	public static final String FAILURE_STATUS_TYPE = PROPERTY_READER.getPropertyValue(IDPServiceDataProviderEnum.FAILURE_STATUS_TYPE.name());
	public static final String SIGNUP_FAILURE_STATUS_CODE1 = PROPERTY_READER.getPropertyValue(IDPServiceDataProviderEnum.SIGNUP_FAILURE_STATUS_CODE1.name());
	public static final String SIGNUP_FAILURE_STATUS_MSG1 = PROPERTY_READER.getPropertyValue(IDPServiceDataProviderEnum.SIGNUP_FAILURE_STATUS_MSG1.name());
	public static final String SIGNUP_FAILURE_STATUS_CODE2 = PROPERTY_READER.getPropertyValue(IDPServiceDataProviderEnum.SIGNUP_FAILURE_STATUS_CODE2.name());
	public static final String SIGNUP_FAILURE_STATUS_MSG2 = PROPERTY_READER.getPropertyValue(IDPServiceDataProviderEnum.SIGNUP_FAILURE_STATUS_MSG2.name());
	public static final String SIGNUP_EMPTY_LOGINID = PROPERTY_READER.getPropertyValue(IDPServiceDataProviderEnum.SIGNUP_EMPTY_LOGINID.name());
	public static final String FP_ERROR_STATUS_CODE1 = PROPERTY_READER.getPropertyValue(IDPServiceDataProviderEnum.FP_ERROR_STATUS_CODE1.name());
	public static final String FP_ERROR_STATUS_MSG1 = PROPERTY_READER.getPropertyValue(IDPServiceDataProviderEnum.FP_ERROR_STATUS_MSG1.name());
	public static final String FP_ERROR_STATUS_CODE2 = PROPERTY_READER.getPropertyValue(IDPServiceDataProviderEnum.FP_ERROR_STATUS_CODE2.name());
	public static final String FP_ERROR_STATUS_MSG2 = PROPERTY_READER.getPropertyValue(IDPServiceDataProviderEnum.FP_ERROR_STATUS_MSG2.name());
	public static final String NULL_VALUE = PROPERTY_READER.getPropertyValue(IDPServiceDataProviderEnum.NULL_VALUE.name());
	public static final String RP_ERROR_STATUS_CODE1 = PROPERTY_READER.getPropertyValue(IDPServiceDataProviderEnum.RP_ERROR_STATUS_CODE1.name());
	public static final String RP_ERROR_STATUS_MSG1 = PROPERTY_READER.getPropertyValue(IDPServiceDataProviderEnum.RP_ERROR_STATUS_MSG1.name());
	public static final String RP_ERROR_STATUS_CODE2 = PROPERTY_READER.getPropertyValue(IDPServiceDataProviderEnum.RP_ERROR_STATUS_CODE2.name());
	public static final String RP_ERROR_STATUS_MSG2 = PROPERTY_READER.getPropertyValue(IDPServiceDataProviderEnum.RP_ERROR_STATUS_MSG2.name());
	
	// SIGN UP API RELATED DATA
	public static final String SIGNUP_MYNT_DOMAIN = PROPERTY_READER.getPropertyValue(IDPServiceDataProviderEnum.SIGNUP_MYNT_DOMAIN.name());
	public static final String SIGNUP_GMAIL_DOMAIN = PROPERTY_READER.getPropertyValue(IDPServiceDataProviderEnum.SIGNUP_GMAIL_DOMAIN.name());
	public static final String SIGNUP_FIRST_NAME = PROPERTY_READER.getPropertyValue(IDPServiceDataProviderEnum.SIGNUP_FIRST_NAME.name());
	public static final String SIGNUP_LAST_NAME = PROPERTY_READER.getPropertyValue(IDPServiceDataProviderEnum.SIGNUP_LAST_NAME.name());
	public static final String SIGNUP_PASSWORD1 = PROPERTY_READER.getPropertyValue(IDPServiceDataProviderEnum.SIGNUP_PASSWORD1.name());
	public static final String SIGNUP_PASSWORD2 = PROPERTY_READER.getPropertyValue(IDPServiceDataProviderEnum.SIGNUP_PASSWORD2.name());
	public static final String SIGNUP_USER_TYPE = PROPERTY_READER.getPropertyValue(IDPServiceDataProviderEnum.SIGNUP_USER_TYPE.name());
	public static final String SIGNUP_GENDER_MALE = PROPERTY_READER.getPropertyValue(IDPServiceDataProviderEnum.SIGNUP_GENDER_MALE.name());
	public static final String SIGNUP_GENDER_FEMALE = PROPERTY_READER.getPropertyValue(IDPServiceDataProviderEnum.SIGNUP_GENDER_FEMALE.name());
	public static final String SIGNUP_TITLE_MR = PROPERTY_READER.getPropertyValue(IDPServiceDataProviderEnum.SIGNUP_TITLE_MR.name());
	public static final String SIGNUP_TITLE_MRS = PROPERTY_READER.getPropertyValue(IDPServiceDataProviderEnum.SIGNUP_TITLE_MRS.name());
	public static final String SIGNUP_MOBILE_NUMBER = PROPERTY_READER.getPropertyValue(IDPServiceDataProviderEnum.SIGNUP_MOBILE_NUMBER.name());
}
