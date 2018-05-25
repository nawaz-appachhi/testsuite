package com.myntra.apiTests.testdevelopmentrnd;

import com.myntra.apiTests.dataproviders.pimDP;
import com.myntra.apiTests.portalservices.checkoutservice.CheckoutTestsHelper;
import com.myntra.apiTests.portalservices.pimservice.pimservice;
import org.apache.log4j.Logger;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.RequestGenerator;

public class pimTests extends pimDP {
	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(pimTests.class);
	APIUtilities apiUtil = new APIUtilities();
	com.myntra.apiTests.portalservices.pimservice.pimservice pimservice = new pimservice();
	static CheckoutTestsHelper checkoutTestsHelper = new CheckoutTestsHelper();

	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression", "ExhaustiveRegression"}, dataProvider = "getApps")
	public void CMSService_getContentById(String forApp)
	{ String response ="200";
		RequestGenerator getOrderReqGen = pimservice
				.invokeGetPIMAttribute(forApp);
		String getOrderResponse = getOrderReqGen.respvalidate
				.returnresponseasstring();
		System.out
				.println("\nPrinting PIM getAttribute API response :\n\n"
						+ getOrderResponse + "\n");
		log.info("\nPrinting PIM getAttribute API response :\n\n"
				+ getOrderResponse + "\n");
		AssertJUnit.assertEquals("PIM getAttribute API is not working",
				Integer.parseInt(response),
				getOrderReqGen.response.getStatus());

	}
	}


