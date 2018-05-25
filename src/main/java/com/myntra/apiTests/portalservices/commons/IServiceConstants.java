package com.myntra.apiTests.portalservices.commons;

import com.myntra.lordoftherings.Configuration;

/**
 * @author shankara.c
 *
 */
public interface IServiceConstants
{
	public static final Configuration CONFIGURATION = new Configuration("./Data/configuration");
	
	public static final String ENVIRONMENT = "ENVIRONMENT";
	public static final String ENVIRONMENT_PROD = "Production";
	public static final String ENVIRONMENT_FOX7 = "Fox7";
	public static final String ENVIRONMENT_FOX8 = "Fox8";
	public static final String ENVIRONMENT_FUNC = "Functional";
	public static final String ENVIRONMENT_PERF = "Performance";
	
	public static final String TRUE_VALUE = "true";
	public static final String FALSE_VALUE = "false";
	
	public static final String ZERO_VALUE = "0";
	
	public static final String SUCCESS_STATUS_TYPE = "SUCCESS";
	public static final String FAILURE_STATUS_TYPE = "ERROR";
	
}
