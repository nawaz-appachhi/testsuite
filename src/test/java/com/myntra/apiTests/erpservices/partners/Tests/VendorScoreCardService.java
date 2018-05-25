package com.myntra.apiTests.erpservices.partners.Tests;

import com.myntra.lordoftherings.Initialize;
import com.myntra.apiTests.common.APINAME;
import com.myntra.apiTests.common.Myntra;
import com.myntra.apiTests.common.ServiceType;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.PayloadType;
import com.myntra.lordoftherings.gandalf.RequestGenerator;

import org.apache.log4j.Logger;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import java.util.HashMap;

public class VendorScoreCardService
{
	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(VendorScoreCardService.class);
	APIUtilities apiUtil = new APIUtilities();

	/*@Test(groups = { "Sanity", "MiniRegression", "Regression",
			"ExhaustiveRegression" })
	public void createNewCriteria()
	{

		MyntraService service = Myntra.getService(
				ServiceType.ERP_MARKETPLACESCORECARD,
				APINAME.CREATENEWCRITERIA, init.Configurations,
				PayloadType.JSON, PayloadType.JSON);

		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0");
		System.out.println("url =" + service.URL);
		System.out.println("payload = " + service.Payload);

		RequestGenerator req = new RequestGenerator(service, getParam);
		System.out.println("response = " + req.returnresponseasstring());

		String statusMessage = req.respvalidate
				.NodeValue("vendorScoreCriteriaResponse.status.statusCode",
						true).replaceAll("\"", "").trim();
		log.info(statusMessage);
		System.out.println(statusMessage);
		AssertJUnit.assertTrue(
				"Create Criteria is not working. Code doesn't Match",
				statusMessage.equalsIgnoreCase("1629"));

		AssertJUnit.assertEquals("Not getting 200 response", 200,
				req.response.getStatus());
	}

	@Test(groups = { "Sanity", "MiniRegression", "Regression",
			"ExhaustiveRegression" })
	public void createNewCriteriaWithSameCode()
	{
		MyntraService service = Myntra.getService(
				ServiceType.ERP_MARKETPLACESCORECARD,
				APINAME.CREATEDUPLICATECRITERIA, init.Configurations,
				PayloadType.JSON, PayloadType.JSON);

		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0");
		System.out.println("url =" + service.URL);
		System.out.println("payload = " + service.Payload);

		RequestGenerator req = new RequestGenerator(service, getParam);
		System.out.println("response = " + req.returnresponseasstring());

		String statusMessage = req.respvalidate
				.NodeValue("vendorScoreCriteriaResponse.status.statusCode",
						true).replaceAll("\"", "").trim();
		log.info(statusMessage);
		System.out.println(statusMessage);
		AssertJUnit.assertTrue("Staus Code Doesn't Match",
				statusMessage.equalsIgnoreCase("1658"));

		AssertJUnit.assertEquals("Not getting 200 response", 200,
				req.response.getStatus());
	}

	@Test(groups = { "Sanity", "MiniRegression", "Regression",
			"ExhaustiveRegression" })
	public void createNewCriteriaWithSameCode_statusMessage()
	{
		MyntraService service = Myntra.getService(
				ServiceType.ERP_MARKETPLACESCORECARD,
				APINAME.CREATEDUPLICATECRITERIA, init.Configurations,
				PayloadType.JSON, PayloadType.JSON);

		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0");
		System.out.println("url =" + service.URL);
		System.out.println("payload = " + service.Payload);

		RequestGenerator req = new RequestGenerator(service, getParam);
		System.out.println("response = " + req.returnresponseasstring());

		String statusMessage = req.respvalidate
				.NodeValue("vendorScoreCriteriaResponse.status.statusMessage",
						true).replaceAll("\"", "").trim();
		log.info(statusMessage);
		System.out.println(statusMessage);
		AssertJUnit.assertTrue("Staus Message Doesn't Match",
				statusMessage.equalsIgnoreCase("Criteria code is not unique."));

		AssertJUnit.assertEquals("Not getting 200 response", 200,
				req.response.getStatus());
	}

	@Test(groups = { "Sanity", "MiniRegression", "Regression",
			"ExhaustiveRegression" })
	public void createNewParameter()
	{
		MyntraService service = Myntra.getService(
				ServiceType.ERP_MARKETPLACESCORECARD,
				APINAME.CREATENEWPARAMETER, init.Configurations,
				PayloadType.JSON, PayloadType.JSON);

		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0");
		System.out.println("url =" + service.URL);
		System.out.println("payload = " + service.Payload);

		RequestGenerator req = new RequestGenerator(service, getParam);
		System.out.println("response = " + req.returnresponseasstring());
		
		 * String statusMessage = req.respvalidate
		 * .NodeValue("vendorScoreParameterResponse.status.statusCode",
		 * true).replaceAll("\"", "") .trim(); log.info(statusMessage);
		 * System.out.println(statusMessage); AssertJUnit.assertTrue(
		 * "Create Criteria is not working. Code doesn't Match",
		 * statusMessage.equalsIgnoreCase("1626"));
		 

		AssertJUnit.assertEquals("Not getting 200 response", 200,
				req.response.getStatus());

	}

	@Test(groups = { "Sanity", "MiniRegression", "Regression",
			"ExhaustiveRegression" })
	public void CreateNewParameter__verifyStatusCode()
	{

	}

	@Test(groups = { "Sanity", "MiniRegression", "Regression",
			"ExhaustiveRegression" })
	public void checkCreateParameterWithSameCode()
	{

	}

	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression",
			"ExhaustiveRegression" })
	public void getParameterForCriteria_verifySuccessResponse()
	{
		MyntraService service = Myntra.getService(
				ServiceType.ERP_MARKETPLACESCORECARD,
				APINAME.GETPARAMETERFORCRITERIA, init.Configurations);

		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0");

		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());
	}

	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression",
			"ExhaustiveRegression" })
	public void getParameterForCriteria_verifyStatusCode()
	{
		MyntraService service = Myntra.getService(
				ServiceType.ERP_MARKETPLACESCORECARD,
				APINAME.GETPARAMETERFORCRITERIA, init.Configurations);

		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0");

		RequestGenerator req = new RequestGenerator(service, getParam);

		String XMLResponse = req.respvalidate.returnresponseasstring();

		System.out.println("Response in XML:" + XMLResponse);

		try
		{
			JSONObject getJSONObject = XML.toJSONObject(XMLResponse);
			String JSONResponse = getJSONObject.toString();
			System.out.println("Response in JSON:" + JSONResponse);

			String statusCode = JsonPath.read(JSONResponse,
					"$.vendorScoreParameterResponse.status.statusCode");

			System.out.println("Status Code: " + statusCode);
			AssertJUnit.assertEquals("Status Code does not match", "1625",
					statusCode);
		} catch (JSONException e)
		{

			System.out.println("Error while converting xml to json");
			log.error("Error while converting xml to json", e);

		}
	}

	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression",
			"ExhaustiveRegression" })
	public void getIsValidCriteria_verifySuccessResponse()
	{
		MyntraService service = Myntra.getService(
				ServiceType.ERP_MARKETPLACESCORECARD, APINAME.ISVALIDCRITERIA,
				init.Configurations);

		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0");

		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());
	}

	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression",
			"ExhaustiveRegression" })
	public void getVendorScoreEntry_verifySuccessResponse()
	{
		MyntraService service = Myntra.getService(
				ServiceType.ERP_MARKETPLACESCORECARD,
				APINAME.GETVENDORSCOREENTRY, init.Configurations);

		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0");

		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());
	}

	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression",
			"ExhaustiveRegression" })
	public void getVendorScoreTrendEntry_verifySuccessResponse()
	{
		MyntraService service = Myntra.getService(
				ServiceType.ERP_MARKETPLACESCORECARD,
				APINAME.GETVENDORSCORETRENDENTRY, init.Configurations);

		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0");

		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());
	}

	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression",
			"ExhaustiveRegression" })
	public void isValidScoreCard_verifySuccessResponse()
	{
		MyntraService service = Myntra.getService(
				ServiceType.ERP_MARKETPLACESCORECARD, APINAME.ISVALIDSCORECARD,
				init.Configurations);

		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0");

		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());
	}

	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression",
			"ExhaustiveRegression" })
	public void getScoreByVendorId_verifySuccessResponse()
	{
		MyntraService service = Myntra.getService(
				ServiceType.ERP_MARKETPLACESCORECARD,
				APINAME.GETVENDORSCORETOTAL, init.Configurations);

		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0");

		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());
	}*/

	/** API Name = Get All Criteria **/

	@Test(groups = {"Regression"}, priority = 0)
	public void getAllCriteria_getStatusCode()
	{
		MyntraService service = Myntra.getService(
				ServiceType.ERP_MARKETPLACESCORECARD, APINAME.GETALLCRITERIA,
				init.Configurations, PayloadType.JSON, PayloadType.JSON);

		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0");

		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());

		String statusMessage = req.respvalidate
				.NodeValue("status.statusCode",
						true).replaceAll("\"", "").trim();
		log.info(statusMessage);
		System.out.println(statusMessage);
		AssertJUnit.assertTrue("Status Code doesn't match",
				statusMessage.equalsIgnoreCase("1628"));
	}

	@Test(groups = { "Regression" }, priority = 0)
	public void getAllCriteria_getStatusMessage()
	{
		MyntraService service = Myntra.getService(
				ServiceType.ERP_MARKETPLACESCORECARD, APINAME.GETALLCRITERIA,
				init.Configurations, PayloadType.JSON, PayloadType.JSON);

		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0");

		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());

		String statusMessage = req.respvalidate
				.NodeValue("status.statusMessage",
						true).replaceAll("\"", "").trim();
		log.info(statusMessage);
		System.out.println(statusMessage);
		AssertJUnit
				.assertTrue(
						"Status Message doesn't match",
						statusMessage
								.equalsIgnoreCase("Vendor Score Criteria has been retrieved successfully"));
	}

	@Test(groups = {"Regression" }, priority = 0)
	public void getAllCriteria_getStatusType()
	{
		MyntraService service = Myntra.getService(
				ServiceType.ERP_MARKETPLACESCORECARD, APINAME.GETALLCRITERIA,
				init.Configurations, PayloadType.JSON, PayloadType.JSON);

		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0");

		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());

		String statusMessage = req.respvalidate
				.NodeValue("status.statusType",
						true).replaceAll("\"", "").trim();
		log.info(statusMessage);
		System.out.println(statusMessage);
		AssertJUnit.assertTrue("Status Type doesn't match",
				statusMessage.equalsIgnoreCase("SUCCESS"));
	}

	/** API Name = Get All Parameters **/

	@Test(groups = { "Regression" }, priority = 0)
	public void getAllParameters_getStatusCode()
	{
		MyntraService service = Myntra.getService(
				ServiceType.ERP_MARKETPLACESCORECARD, APINAME.GETALLPARAMETERS,
				init.Configurations, PayloadType.JSON, PayloadType.JSON);

		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0");

		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());

		String statusMessage = req.respvalidate
				.NodeValue("status.statusCode",
						true).replaceAll("\"", "").trim();
		log.info(statusMessage);
		System.out.println(statusMessage);
		AssertJUnit.assertTrue("Status Code doesn't match",
				statusMessage.equalsIgnoreCase("1625"));
	}

	@Test(groups = { "Regression" }, priority = 0)
	public void getAllParameters_getStatusMessage()
	{
		MyntraService service = Myntra.getService(
				ServiceType.ERP_MARKETPLACESCORECARD, APINAME.GETALLPARAMETERS,
				init.Configurations, PayloadType.JSON, PayloadType.JSON);

		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0");

		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());

		String statusMessage = req.respvalidate
				.NodeValue("status.statusMessage",
						true).replaceAll("\"", "").trim();
		log.info(statusMessage);
		System.out.println(statusMessage);
		AssertJUnit
				.assertTrue(
						"Status Message doesn't match",
						statusMessage
								.equalsIgnoreCase("Vendor Score Parameter has been retrieved successfully"));
	}

	@Test(groups = {"Regression" }, priority = 0)
	public void getAllParameters_getStatusType()
	{
		MyntraService service = Myntra.getService(
				ServiceType.ERP_MARKETPLACESCORECARD, APINAME.GETALLPARAMETERS,
				init.Configurations, PayloadType.JSON, PayloadType.JSON);

		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0");

		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());

		String statusMessage = req.respvalidate
				.NodeValue("status.statusType",
						true).replaceAll("\"", "").trim();
		log.info(statusMessage);
		System.out.println(statusMessage);
		AssertJUnit.assertTrue("Status Type doesn't match",
				statusMessage.equalsIgnoreCase("SUCCESS"));
	}

	/** API Name - Get All Scores **/

	@Test(groups = { "Regression" }, priority = 0)
	public void getAllScores_getStatusCode()
	{
		MyntraService service = Myntra.getService(
				ServiceType.ERP_MARKETPLACESCORECARD, APINAME.GETALLSCORES,
				init.Configurations, PayloadType.JSON, PayloadType.JSON);

		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0");

		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());

		String statusMessage = req.respvalidate
				.NodeValue("status.statusCode", true)
				.replaceAll("\"", "").trim();
		log.info(statusMessage);
		System.out.println(statusMessage);
		AssertJUnit.assertTrue("Status Code doesn't match",
				statusMessage.equalsIgnoreCase("1632"));
	}

	@Test(groups = { "Regression" }, priority = 0)
	public void getAllScores_getStatusMessage()
	{
		MyntraService service = Myntra.getService(
				ServiceType.ERP_MARKETPLACESCORECARD, APINAME.GETALLSCORES,
				init.Configurations, PayloadType.JSON, PayloadType.JSON);

		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0");

		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());

		String statusMessage = req.respvalidate
				.NodeValue("status.statusMessage", true)
				.replaceAll("\"", "").trim();
		log.info(statusMessage);
		System.out.println(statusMessage);
		AssertJUnit
				.assertTrue(
						"Status Message doesn't match",
						statusMessage
								.equalsIgnoreCase("Vendor Score Criteria has been retrieved successfully"));
	}

	@Test(groups = {"Regression" }, priority = 0)
	public void getAllScores_getStatusType()
	{
		MyntraService service = Myntra.getService(
				ServiceType.ERP_MARKETPLACESCORECARD, APINAME.GETALLSCORES,
				init.Configurations, PayloadType.JSON, PayloadType.JSON);

		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0");

		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());

		String statusMessage = req.respvalidate
				.NodeValue("status.statusType", true)
				.replaceAll("\"", "").trim();
		log.info(statusMessage);
		System.out.println(statusMessage);
		AssertJUnit.assertTrue("Status Type doesn't match",
				statusMessage.equalsIgnoreCase("SUCCESS"));
	}
}
