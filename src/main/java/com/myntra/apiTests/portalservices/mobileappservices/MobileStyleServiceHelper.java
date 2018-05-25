package com.myntra.apiTests.portalservices.mobileappservices;

import com.myntra.apiTests.InitializeFramework;
import com.myntra.apiTests.common.Myntra;
import com.myntra.apiTests.portalservices.commons.CommonUtils;
import org.apache.log4j.Logger;

import com.myntra.lordoftherings.Initialize;
import com.myntra.apiTests.common.APINAME;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.apiTests.common.ServiceType;

/**
 * @author shankara.c
 *
 */
public class MobileStyleServiceHelper extends CommonUtils
{
	static Initialize init = InitializeFramework.init;
	static Logger log = Logger.getLogger(MobileStyleServiceHelper.class);

	/**
	 * This method is used to invoke MobileStyleService getStyleById API
	 * 
	 * @param styleId
	 * @return RequestGenerator
	 */
	public RequestGenerator invokeGetStyleById(String styleId)
	{
		ServiceType mobileStyleServiceType = ServiceType.PORTAL_MOBILEAPPWEBSERVICES;
		APINAME mobileStyleAPIName = APINAME.MOBILEAPPGETSTYLEBYID;
		MyntraService mobileStyleService = Myntra.getService(mobileStyleServiceType, mobileStyleAPIName, init.Configurations);
		mobileStyleService.URL = new APIUtilities().prepareparameterizedURL(mobileStyleService.URL, styleId);

		System.out.println("\nPrinting MobileStyleService getStyleById API URL : "+mobileStyleService.URL);
		log.info("\nPrinting MobileStyleService getStyleById API URL : "+mobileStyleService.URL);
		
		System.out.println("\nPrinting MobileStyleService getStyleById API URL : "+mobileStyleService.URL);
		log.info("\nPrinting MobileStyleService getStyleById API URL : "+mobileStyleService.URL);
		
		return new RequestGenerator(mobileStyleService);
	}
	
}
