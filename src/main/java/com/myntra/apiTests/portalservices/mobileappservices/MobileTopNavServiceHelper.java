package com.myntra.apiTests.portalservices.mobileappservices;

import com.myntra.apiTests.InitializeFramework;
import com.myntra.apiTests.common.Myntra;
import com.myntra.apiTests.portalservices.commons.CommonUtils;
import org.apache.log4j.Logger;

import com.myntra.lordoftherings.Initialize;
import com.myntra.apiTests.common.APINAME;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.apiTests.common.ServiceType;

/**
 * @author shankara.c
 *
 */
public class MobileTopNavServiceHelper extends CommonUtils
{
	static Initialize init = InitializeFramework.init;
	static Logger log = Logger.getLogger(MobileTopNavServiceHelper.class);
	
	/**
	 * This method is used to invoke MobileTopNavService API
	 * 
	 * @return RequestGenerator
	 */
	public RequestGenerator invokeMobileTopNavService()
	{
		ServiceType serviceundertest = ServiceType.PORTAL_MOBILEAPPWEBSERVICES;
		APINAME apiundertest = APINAME.MOBILEAPPGETNAV;
		MyntraService mobileTopNavService = Myntra.getService(serviceundertest, apiundertest, init.Configurations);
		
		System.out.println("\nPrinting MobileTopNavService getTopNav API URL : "+mobileTopNavService.URL);
		log.info("\nPrinting MobileTopNavService getTopNav API URL : "+mobileTopNavService.URL);
		
		System.out.println("\nPrinting MobileTopNavService getTopNav API Payload : \n\n"+mobileTopNavService.Payload);
		log.info("\nPrinting MobileTopNavService getTopNav API Payload : \n\n"+mobileTopNavService.Payload);
		
		return new RequestGenerator(mobileTopNavService);
	}
	
}
