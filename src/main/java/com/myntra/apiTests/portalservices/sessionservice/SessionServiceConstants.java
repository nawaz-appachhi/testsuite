package com.myntra.apiTests.portalservices.sessionservice;

import com.myntra.apiTests.portalservices.commons.IServiceConstants;
import com.myntra.lordoftherings.PropertyReader;

/**
 * @author shankara.c
 *
 */
public interface SessionServiceConstants extends IServiceConstants
{
	public static final PropertyReader PROPERTY_READER = new PropertyReader(new String[]{"./Data/dataproviders/sessionservicedp.properties"});
	
	public static final String PROD_VALID_USER = PROPERTY_READER.getPropertyValue(SessionServiceEnum.PROD_VALID_USER.name());
	public static final String PROD_VALID_PASS = PROPERTY_READER.getPropertyValue(SessionServiceEnum.PROD_VALID_PASS.name());
	
	public static final String FOX7_VALID_USER = PROPERTY_READER.getPropertyValue(SessionServiceEnum.FOX7_VALID_USER.name());
	public static final String FOX7_VALID_PASS = PROPERTY_READER.getPropertyValue(SessionServiceEnum.FOX7_VALID_PASS.name());
	
	public static final String FUNC_VALID_USER = PROPERTY_READER.getPropertyValue(SessionServiceEnum.FUNC_VALID_USER.name());
	public static final String FUNC_VALID_PASS = PROPERTY_READER.getPropertyValue(SessionServiceEnum.FUNC_VALID_PASS.name());

	public static final String PERF_VALID_USER = PROPERTY_READER.getPropertyValue(SessionServiceEnum.PERF_VALID_USER.name());
	public static final String PERF_VALID_PASS = PROPERTY_READER.getPropertyValue(SessionServiceEnum.PERF_VALID_PASS.name());

	
}
