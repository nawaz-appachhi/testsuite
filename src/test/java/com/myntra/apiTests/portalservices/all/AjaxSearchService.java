package com.myntra.apiTests.portalservices.all;

import com.myntra.apiTests.InitializeFramework;
import com.myntra.apiTests.dataproviders.StyleDP;
import com.myntra.lordoftherings.Initialize;

import com.myntra.lordoftherings.gandalf.*;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;
import com.myntra.apiTests.common.APINAME;
import com.myntra.apiTests.common.Myntra;
import com.myntra.apiTests.common.ServiceType;


public class AjaxSearchService extends StyleDP
{
	static Initialize init = InitializeFramework.init;
	static Logger log = Logger.getLogger(AjaxSearchService.class);

	/**
	 * @author ashish.bajpai A Test to compare no impact on output response with
	 *         changes done to ajax search service api.
	 */
	// public void compareresponse_equality(APINAME apiundertest, Object
	// payload)
	@Test(groups = { "searchajaxservice" }, dataProvider = "dp_styleidajaxsearch")
	public void compareresponse_equality(String[] payloadparams)
	{
		APINAME testapi = APINAME.STYLEIDS2;
		Payload payload1 = new Payload(testapi.toString(),
				InitializeFramework.init.Configurations, PayloadType.JSON);
		MyntraService service1 = Myntra.getService(ServiceType.PORTAL_SEARCH,
				testapi, init.Configurations);

		service1.URL = "http://54.251.114.206:6060/searchws/search/styleids2";
		log.info(service1.URL);

		String payload = payload1.ParamatereizedPayload(payloadparams);
		RequestGenerator req1 = new RequestGenerator(service1, payload);
		String response1 = null;
		response1 = req1.respvalidate.returnresponseasstring();
		service1.URL = "http://54.251.114.206:6050/searchws/search/styleids2"; // New
																				// AJAX
																				// search
																				// endpoint
		RequestGenerator req2 = new RequestGenerator(service1, payload);
		String response2 = null;
		response2 = req2.respvalidate.returnresponseasstring();

		// AssertJUnit.assertEquals(req1.response.getStatus(), 200);
		// AssertJUnit.assertEquals(req2.response.getStatus(), 200);
		Assert.assertEquals(req1.response.getStatus(),
				req2.response.getStatus());
		Assert.assertEquals(response1, response2);
		log.info("Responses are " + req1.response.getStatus() + " & "
				+ req2.response.getStatus());
	}

	/*
	 * @Test(groups = {"Smoke", "Sanity"}, dataProvider =
	 * "dp_styleidajaxsearch") public void ajaxSearchUsingStyleIDs2(String[]
	 * payloadparams){ MyntraService service = new
	 * MyntraService(ServiceType.PORTAL_SEARCH, APINAME.STYLEIDS2,
	 * init.Configurations,payloadparams); RequestGenerator req = new
	 * RequestGenerator(service);
	 * System.out.println(req.respvalidate.returnresponseasstring());
	 * AssertJUnit.assertEquals("ajaxSearchUsingStyleIDs2 is not working", 200,
	 * req.response.getStatus()); }
	 */

	@Test(groups = { "Smoke", "Sanity" }, dataProvider = "styleIdAjaxSearchDataProvider")
	public void AjaxSearchService_ajaxSearchUsingStyleIDs2(String query,
			String count, String start, String end, String returndocs)
	{
		MyntraService service = Myntra.getService(ServiceType.PORTAL_SEARCH,
				APINAME.STYLEIDS2, init.Configurations, new String[] { query,
						count, start, end });
		RequestGenerator req = new RequestGenerator(service);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("ajaxSearchUsingStyleIDs2 is not working",
				200, req.response.getStatus());
	}
}
