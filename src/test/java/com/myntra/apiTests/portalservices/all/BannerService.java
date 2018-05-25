package com.myntra.apiTests.portalservices.all;

import java.util.ArrayList;
import java.util.List;

import com.myntra.apiTests.common.Myntra;
import com.myntra.apiTests.dataproviders.BannerServiceDP;
import com.myntra.apiTests.portalservices.bannerservice.BannerServiceHelper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.Toolbox;
import com.myntra.apiTests.common.APINAME;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.PayloadType;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.apiTests.common.ServiceType;

/**
 * @author shankara.c
 *
 */
public class BannerService extends BannerServiceDP
{
	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(BannerService.class);
	static BannerServiceHelper bannerServiceHelper = new BannerServiceHelper();
	
	
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "MiniRegression",
			"Regression", "ExhaustiveRegression" }, dataProvider = "getPersonalizedBannerDataProvider")
	public void BannerService_getPersonalizedBanner(String locationtype,
			String section, String pagelocation) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_BANNER,
				APINAME.GETPERSONALIZEDBANNER, init.Configurations,
				PayloadType.JSON, new String[] { locationtype, section,
						pagelocation }, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);
		System.out.println(service.URL);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(
				"getPersonalizedBanner service is not working", 200,
				req.response.getStatus());
	//	AssertJUnit.assertTrue("Invalid banner response nodes",
	//			req.respvalidate.DoesNodesExists(verifyBannerNodes()));
	}
	
	@Test(groups = { "Smoke", "Sanity", "MiniRegression",
			"Regression", "ExhaustiveRegression" }, dataProvider = "getPersonalizedBannerDataProvider")
	public void BannerService_getPersonalizedBanner_verifyBannerNodes(String locationtype,
			String section, String pagelocation) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_BANNER,
				APINAME.GETPERSONALIZEDBANNER, init.Configurations,
				PayloadType.JSON, new String[] { locationtype, section,
						pagelocation }, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);
		System.out.println(service.URL);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertTrue("Invalid banner response nodes",
				req.respvalidate.DoesNodesExists(verifyBannerNodes()));
	}

	// verify node values
	@Test(groups = { "Smoke", "Sanity", "MiniRegression",
			"Regression", "ExhaustiveRegression", "Sarath" }, dataProvider = "getPersonalizedBannerNodesDataProvider")
	public void BannerService_getPersonalizedBannerVerifyStatusNodeValues(
			String locationtype, String section, String pagelocation,
			String statusCode, String statusMsg, String sType, String tCount) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_BANNER,
				APINAME.GETPERSONALIZEDBANNER, init.Configurations,
				PayloadType.JSON, new String[] { locationtype, section,
						pagelocation }, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);
		System.out.println(service.URL);
		System.out.println(req.respvalidate.returnresponseasstring());

		AssertJUnit.assertTrue(
				"Value of status.statusCode should be " + statusCode
						+ " in Response",
				req.respvalidate.GetNodeValue(
						"personalizedBannerResponse.status.statusCode",
						req.respvalidate.returnresponseasstring()).matches(
						statusCode));

		AssertJUnit.assertTrue(
				"Value of status.statusMsg should be " + statusMsg
						+ " in Response",
				req.respvalidate.GetNodeValue(
						"personalizedBannerResponse.status.statusMessage",
						req.respvalidate.returnresponseasstring()).matches(
						"\"" + statusMsg + "\""));

		AssertJUnit.assertTrue(
				"Value of status.statusType should be " + sType
						+ " in Response",
				req.respvalidate.GetNodeValue(
						"personalizedBannerResponse.status.statusType",
						req.respvalidate.returnresponseasstring()).matches(
						"\"" + sType + "\""));

		AssertJUnit.assertTrue(
				"Value of status.totalCount should be " + tCount
						+ " in Response",
				req.respvalidate.GetNodeValue(
						"personalizedBannerResponse.status.totalCount",
						req.respvalidate.returnresponseasstring()).matches(
						tCount));
	}

	
	// verify negative scenario values
	@Test(groups = { "Smoke", "Sanity", "MiniRegression",
			"Regression", "ExhaustiveRegression", "Sarath" }, dataProvider = "getPersonalizedBannerNodesNegetiveValuesDataProvider")
	public void BannerService_getPersonalizedBannerVerifyNegativeValues(
			String locationtype, String section, String pagelocation,
			String statusCode, String statusMsg, String statusType,
			String totlaCount) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_BANNER,
				APINAME.GETPERSONALIZEDBANNER, init.Configurations,
				PayloadType.JSON, new String[] { locationtype, section,
						pagelocation }, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);
		System.out.println(service.URL);
		System.out.println(req.respvalidate.returnresponseasstring());

		AssertJUnit.assertTrue(
				"Value of status.statusCode should be " + statusCode
						+ " in Response",
				req.respvalidate.GetNodeValue(
						"personalizedBannerResponse.status.statusCode",
						req.respvalidate.returnresponseasstring()).matches(
						statusCode));

	}

	private ArrayList<String> verifyBannerNodes() {
		ArrayList<String> response = new ArrayList<String>();
		response.add("personalizedBannerResponse");
		response.add("personalizedBannerResponse.status");
		response.add("personalizedBannerResponse.status.statusCode");
		response.add("personalizedBannerResponse.status.statusMessage");
		response.add("personalizedBannerResponse.status.statusType");
		response.add("personalizedBannerResponse.status.totalCount");
		return response;
	}
	
	@Test(groups = { "SchemaValidation" }, dataProvider = "BannerServiceDP_getPersonalizedBanner_verifyResponseDataNodesUsingSchemaValidations")
	public void BannerService_getPersonalizedBanner_verifyResponseDataNodesUsingSchemaValidations(String locationtype, String section, String pagelocation) 
	{
		RequestGenerator getPersonalizedBannerReqGen = bannerServiceHelper.invokeGetPersonalizedBanner(locationtype, section, pagelocation);
		String getPersonalizedBannerResponse = getPersonalizedBannerReqGen.ResponseValidations.GetResponseAsString();
		System.out.println("\nPrinting BannerService getPersonalizedBanner API response :\n\n"+getPersonalizedBannerResponse);
		log.info("\nPrinting BannerService getPersonalizedBanner API response :\n\n"+getPersonalizedBannerResponse);
		
		AssertJUnit.assertEquals("BannerService getPersonalizedBanner API is not working", 200, getPersonalizedBannerReqGen.response.getStatus());
		
		try {
            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/bannerservice-getpersonalizedbanner-schema.txt");
            List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(getPersonalizedBannerResponse, jsonschema);
            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in BannerService getPersonalizedBanner API response", 
            		CollectionUtils.isEmpty(missingNodeList));
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

}
