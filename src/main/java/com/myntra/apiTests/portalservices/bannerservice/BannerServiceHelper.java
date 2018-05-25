package com.myntra.apiTests.portalservices.bannerservice;

import com.myntra.apiTests.common.Myntra;
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
public class BannerServiceHelper
{
	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(BannerServiceHelper.class);
	
	public RequestGenerator invokeGetPersonalizedBanner(String locationtype, String section, String pagelocation)
	{
		MyntraService getPersonalizedBannerService = Myntra.getService(ServiceType.PORTAL_BANNER, APINAME.GETPERSONALIZEDBANNER, init.Configurations, PayloadType.JSON,
				new String[] { locationtype, section, pagelocation }, PayloadType.JSON);
	
		System.out.println("\nPrinting BannerService getPersonalizedBanner API URL :"+getPersonalizedBannerService.URL);
		log.info("\nPrinting BannerService getPersonalizedBanner API URL :"+getPersonalizedBannerService.URL);
		
		System.out.println("\nPrinting BannerService getPersonalizedBanner API Payload :\n\n"+getPersonalizedBannerService.Payload);
		log.info("\nPrinting BannerService getPersonalizedBanner API Payload :\n\n"+getPersonalizedBannerService.Payload);
		
		return new RequestGenerator(getPersonalizedBannerService);
	}

}
