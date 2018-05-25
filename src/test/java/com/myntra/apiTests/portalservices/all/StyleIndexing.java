package com.myntra.apiTests.portalservices.all;

import com.myntra.apiTests.common.Myntra;
import com.myntra.apiTests.portalservices.styleservice.StyleServiceApiHelper;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import com.myntra.lordoftherings.Initialize;
import com.myntra.apiTests.common.APINAME;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.apiTests.common.ServiceType;

public class StyleIndexing {
	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(StyleServiceApiTests.class);
	static StyleServiceApiHelper styleServiceApiHelper = new StyleServiceApiHelper();
	static APIUtilities apiUtil = new APIUtilities();
	/*@author: Shrinkhala.Gupta*/
	
	/**
	 * This TestCase is used to invoke StyleService doStyleIndexForSingleStyleId API and verification for success response
	 * 
	 * @param styleId
	 */
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "MiniRegression", "Regression", "ExhaustiveRegression" }, 
			dataProvider = "StyleServiceApiDP_doStyleIndexForSingleStyleId_verifySuccessResponse")
	public void StyleService_doStyleIndexForSingleStyleId_verifySuccessResponse(String styleId)
	{
		RequestGenerator doStyleIndexForSingleStyleIdReqGen = styleServiceApiHelper.doStyleIndexForSingleStyleId(styleId);
		String getPdpDataBySingleSkuIdResponse = doStyleIndexForSingleStyleIdReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting StyleService doStyleIndexForSingleStyleId API response :\n\n"+getPdpDataBySingleSkuIdResponse+"\n");
		log.info("\nPrinting StyleService doStyleIndexForSingleStyleId API response :\n\n"+getPdpDataBySingleSkuIdResponse+"\n");	
		
		AssertJUnit.assertEquals("StyleService doStyleIndexForSingleStyleId API is not working", 200, doStyleIndexForSingleStyleIdReqGen.response.getStatus());
	}
	
	// Below test case is risky to run on fox and QA. Full Re indexing API
		@Test(groups = {""})
		public void StyleService_fullReindexing(){
			MyntraService service = Myntra.getService(ServiceType.PORTAL_STYLEAPI, APINAME.FULLREINDEXING, init.Configurations);
			RequestGenerator req = new RequestGenerator(service);
			AssertJUnit.assertEquals("fullReindexing API is not working",200,req.response.getStatus());
			String statusMessageFromResponse = req.respvalidate.NodeValue("status.statusType", true);
			//System.out.println(statusMessageFromResponse);
			System.out.println(req.respvalidate.returnresponseasstring());
			Assert.assertTrue(statusMessageFromResponse.contains("SUCCESS"), "StatusType String didn't matched");
		}
}
