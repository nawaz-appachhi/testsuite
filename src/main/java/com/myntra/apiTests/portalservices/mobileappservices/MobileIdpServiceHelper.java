package com.myntra.apiTests.portalservices.mobileappservices;

import com.myntra.apiTests.InitializeFramework;
import com.myntra.apiTests.common.Myntra;
import com.myntra.apiTests.portalservices.commons.CommonUtils;
import org.apache.log4j.Logger;

import com.myntra.lordoftherings.Initialize;
import com.myntra.apiTests.common.APINAME;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.PayloadType;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.apiTests.common.ServiceType;

/**
 * @author shankara.c
 *
 */
public class MobileIdpServiceHelper extends CommonUtils
{
	static Initialize init = InitializeFramework.init;
	static Logger log = Logger.getLogger(MobileIdpServiceHelper.class);

	/**
	 * This TestCase is used to invoke MobileIDPService signIn API
	 * 
	 * @param userName
	 * @param password
	 * @param action
	 * @return RequestGenerator
	 */
	public RequestGenerator invokeMobileIdpSignInService(String userName, String password, String action)
	{
		MyntraService mobileIDPSignInService = Myntra.getService(ServiceType.PORTAL_MOBILEAPPWEBSERVICES, APINAME.MOBILEIDPLOGIN, init.Configurations,
				new String[] { userName, password, action }, PayloadType.JSON, PayloadType.JSON);
		System.out.println("\nPrinting MobileIDPService signIn API URL : "+mobileIDPSignInService.URL);
		log.info("\nPrinting MobileIDPService signIn API URL : "+mobileIDPSignInService.URL);
		
		System.out.println("\nPrinting MobileIDPService signIn API Payload : \n\n"+mobileIDPSignInService.Payload);
		log.info("\nPrinting MobileIDPService signIn API Payload : \n\n"+mobileIDPSignInService.Payload);
		
		return new RequestGenerator(mobileIDPSignInService);
	}
	
	/**
	 * This TestCase is used to invoke MobileIDPService signUp API
	 * 
	 * @param userName
	 * @param password
	 * @param action
	 * @return RequestGenerator
	 */
	public RequestGenerator invokeMobileIdpSignUpService(String userName, String password, String action)
	{
		MyntraService mobileIdpSignUpService = Myntra.getService(ServiceType.PORTAL_MOBILEAPPWEBSERVICES, APINAME.MOBILEIDPLOGIN, init.Configurations,
				new String[] { userName, password, action }, PayloadType.JSON, PayloadType.JSON);
		
		System.out.println("\nPrinting MobileIDPService signUp API URL : "+mobileIdpSignUpService.URL);
		log.info("\nPrinting MobileIDPService signUp API URL : "+mobileIdpSignUpService.URL);
		
		System.out.println("\nPrinting MobileIDPService signUp API Payload : \n\n"+mobileIdpSignUpService.Payload);
		log.info("\nPrinting MobileIDPService signUp API Payload : \n\n"+mobileIdpSignUpService.Payload);
		
		return new RequestGenerator(mobileIdpSignUpService);
	}
	
	
}
