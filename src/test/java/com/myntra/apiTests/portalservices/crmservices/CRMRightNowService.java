package com.myntra.apiTests.portalservices.crmservices;

import java.util.HashMap;

import com.myntra.apiTests.common.Myntra;
import com.myntra.apiTests.dataproviders.CRMServiceDP;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import com.github.fge.jsonschema.report.ProcessingReport;
import com.github.fge.jsonschema.util.JsonLoader;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.Toolbox;
import com.myntra.apiTests.common.APINAME;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.PayloadType;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.lordoftherings.gandalf.ResponseValidator;
import com.myntra.apiTests.common.ServiceType;

public class CRMRightNowService extends CRMServiceDP {

	static Logger log = Logger.getLogger(CRMPortalService.class);

	static Initialize init = new Initialize("/Data/configuration");
	APIUtilities apiUtil = new APIUtilities();
	public ResponseValidator respvalidate;

	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "CRMRightNowServiceDP_drishtiIVROrderStatus_verifyAPIResponse")
	public void CRMRightNowService_drishtiOrderStatus_verifySuccessResponse(
			String orderid) throws Exception {
		MyntraService service = Myntra.getService(
				ServiceType.ERP_CRMSERVICERIGHTNOW,
				APINAME.DRISHTIIVRORDERSTATUS, init.Configurations);
		service.URL = apiUtil.prepareparameterizedURL(service.URL, orderid);
		System.out.println(service.URL);
		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic bW9iaWxlfm1vYmlsZTptb2JpbGU");
		RequestGenerator req = new RequestGenerator(service, getParam);
		System.out.println(req.respvalidate.returnresponseasstring());

		Assert.assertEquals(200, req.response.getStatus());
		service.responsedataformat = PayloadType.XML;

		Assert.assertEquals(req.respvalidate.ResponseFromService.getStatus(),
				200);
		Toolbox tools = new Toolbox();

		String jsonschema = tools
				.readFileAsString("Data/SchemaSet/JSON/crmrightnowdrishtiorderstatus.txt");
		System.out.println(jsonschema);

		String jsondata = req.respvalidate.returnresponseasstring();

		System.out.println("validate(jsondata, jsonschema) = "
				+ validate(jsondata, jsonschema));
		Assert.assertTrue(validate(jsondata, jsonschema));

	}

	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "CRMRightNowServiceDP_createcustomerprofile_verifyAPIResponse")
	public void CRMRightNowService_createcustomerprofile_verifySuccessResponse(
			String emailid) throws Exception {
		MyntraService service = Myntra.getService(
				ServiceType.ERP_CRMSERVICERIGHTNOW,
				APINAME.CREATECUSTOMERPROFILE, init.Configurations);
		service.URL = apiUtil.prepareparameterizedURL(service.URL, emailid);
		System.out.println(service.URL);
		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic bW9iaWxlfm1vYmlsZTptb2JpbGU");
		RequestGenerator req = new RequestGenerator(service, getParam);
		System.out.println(req.respvalidate.returnresponseasstring());

		Assert.assertEquals(200, req.response.getStatus());
		service.responsedataformat = PayloadType.XML;

		Assert.assertEquals(req.respvalidate.ResponseFromService.getStatus(),
				200);
		

	}

	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "CRMRightNowServiceDP_getNEFTBankAccount_verifyAPIResponse")
	public void CRMRightNowService_GetNEFTABankccount_verifySuccessResponse(
			String emailid, String neftid) throws Exception {
		MyntraService service = Myntra.getService(
				ServiceType.ERP_CRMSERVICERIGHTNOW, APINAME.GETNEFTBANKACCOUNT,
				init.Configurations);
		service.URL = apiUtil.prepareparameterizedURL(service.URL,
				new String[] { emailid, neftid });
		System.out.println(service.URL);
		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic bW9iaWxlfm1vYmlsZTptb2JpbGU");
		RequestGenerator req = new RequestGenerator(service, getParam);
		System.out.println(req.respvalidate.returnresponseasstring());

		Assert.assertEquals(200, req.response.getStatus());
		service.responsedataformat = PayloadType.XML;

		Assert.assertEquals(req.respvalidate.ResponseFromService.getStatus(),
				200);
		Toolbox tools = new Toolbox();

		String jsonschema = tools
				.readFileAsString("Data/SchemaSet/JSON/crmrightnowgetNEFTaccount.txt");
		System.out.println(jsonschema);

		String jsondata = req.respvalidate.returnresponseasstring();

		System.out.println("validate(jsondata, jsonschema) = "
				+ validate(jsondata, jsonschema));
		Assert.assertTrue(validate(jsondata, jsonschema));

	}

	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "CRMRightNowServiceDP_drishtiIVROrderStatus_verifyAPIResponse")
	public void CRMRightNowService_ReturnOrderDetails_verifySuccessResponse(
			String orderid) throws Exception {
		MyntraService service = Myntra.getService(
				ServiceType.ERP_CRMSERVICERIGHTNOW, APINAME.RETURNDETAILS,
				init.Configurations);
		service.URL = apiUtil.prepareparameterizedURL(service.URL, orderid);
		System.out.println(service.URL);
		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic bW9iaWxlfm1vYmlsZTptb2JpbGU");
		RequestGenerator req = new RequestGenerator(service, getParam);
		System.out.println(req.respvalidate.returnresponseasstring());

		Assert.assertEquals(200, req.response.getStatus());
		service.responsedataformat = PayloadType.XML;

		Assert.assertEquals(req.respvalidate.ResponseFromService.getStatus(),
				200);

	}

	@Test(dataProvider = "CRMRightNowServiceDP_createcustomerprofile_verifyAPIResponse")
	public void CRMRightNowService_SaveNEFTBankAccount_verifySuccessResponse(
			String loginId) throws Exception {
		MyntraService service = Myntra.getService(
				ServiceType.ERP_CRMSERVICERIGHTNOW,
				APINAME.SAVENEFTBANKACCOUNT, init.Configurations,
				PayloadType.XML, PayloadType.JSON);
		service.URL = apiUtil.prepareparameterizedURL(service.URL, loginId);
		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic bW9iaWxlfm1vYmlsZTptb2JpbGU");
		System.out.println(service.Payload);
		RequestGenerator req = new RequestGenerator(service, getParam);
		System.out.println(req.respvalidate.returnresponseasstring());
		log.info(service.URL);

		service.responsedataformat = PayloadType.XML;

		Assert.assertEquals(req.respvalidate.ResponseFromService.getStatus(),
				200);
		

		AssertJUnit.assertEquals("Status code does not match", 200,
				req.response.getStatus());
	}

	@Test
	public void CRMRightNowService_Refund_verifySuccessResponse()
			throws Exception {
		MyntraService service = Myntra.getService(
				ServiceType.ERP_CRMSERVICERIGHTNOW, APINAME.REFUNDENTRY,
				init.Configurations, PayloadType.XML, PayloadType.JSON);
		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic bW9iaWxlfm1vYmlsZTptb2JpbGU");
		System.out.println(service.Payload);
		RequestGenerator req = new RequestGenerator(service, getParam);
		System.out.println(req.respvalidate.returnresponseasstring());
		log.info(service.URL);

		service.responsedataformat = PayloadType.XML;

		Assert.assertEquals(req.respvalidate.ResponseFromService.getStatus(),
				200);
		Toolbox tools = new Toolbox();

		String jsonschema = tools
				.readFileAsString("Data/SchemaSet/JSON/crmrightnowrefundtaskcreation.txt");
		System.out.println(jsonschema);

		String jsondata = req.respvalidate.returnresponseasstring();

		System.out.println("validate(jsondata, jsonschema) = "
				+ validate(jsondata, jsonschema));
		Assert.assertTrue(validate(jsondata, jsonschema));

		AssertJUnit.assertEquals("Status code does not match", 200,
				req.response.getStatus());
	}

	public boolean validate(String jsonData, String jsonSchema)
			throws Exception {
		// create the Json nodes for schema and data
		ProcessingReport report = null;
		JsonNode schemaNode = JsonLoader.fromString(jsonSchema); // throws
																	// JsonProcessingException
																	// if error
		JsonNode data = JsonLoader.fromString(jsonData); // same here

		JsonSchemaFactory factory = JsonSchemaFactory.byDefault();
		// load the schema and validate
		JsonSchema schema = factory.getJsonSchema(schemaNode);
		report = schema.validate(data);
		/*
		 * while(report.iterator().hasNext()) {
		 * System.out.println(report.iterator().next().getMessage()); }
		 */
		System.out.println("report = " + report.toString());// report.toString();
		// report.;
		return report.isSuccess();
	}

}