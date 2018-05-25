package com.myntra.apiTests.portalservices.pimservice;

import java.util.HashMap;

import com.myntra.apiTests.common.Myntra;
import com.myntra.apiTests.erpservices.bounty.BountyServiceHelper;
import com.myntra.apiTests.portalservices.checkoutservice.CheckoutTestsHelper;
import org.apache.log4j.Logger;

import com.myntra.lordoftherings.Initialize;
import com.myntra.apiTests.common.APINAME;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.PayloadType;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.apiTests.common.ServiceType;

public class pimservice {
	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(BountyServiceHelper.class);
	static CheckoutTestsHelper checkoutTestsHelper = new CheckoutTestsHelper();

	public RequestGenerator invokeGetPIMAttribute(String source) {
		MyntraService pimService = Myntra.getService(
				ServiceType.PORTAL_PIM, APINAME.DISPLAYATTRLIST,
				init.Configurations, PayloadType.JSON,
				new String[] { source }, PayloadType.JSON);

		System.out.println("\nPrinting pim getAttribute API URL : "
				+ pimService.URL);
		log.info("\nPrinting pim getAttribute API URL : "
				+ pimService.URL);

		System.out.println("\nPrinting pim getAttribute API Payload : "
				+ pimService.Payload);
		log.info("\nPrinting pim getAttribute API Payload : "
				+ pimService.Payload);

		HashMap<String, String> getOrderHeaders = new HashMap<String, String>();
		getOrderHeaders.put("Authorization",
				"Basic bW9iaWxlfm1vYmlsZTptb2JpbGU");
		getOrderHeaders.put("Content-Type", "Application/xml");

		return new RequestGenerator(pimService, getOrderHeaders);
	
}
}