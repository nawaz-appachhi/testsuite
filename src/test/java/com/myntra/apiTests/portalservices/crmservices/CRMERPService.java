package com.myntra.apiTests.portalservices.crmservices;

import java.util.HashMap;

import com.myntra.apiTests.dataproviders.CRMServiceDP;
import com.myntra.apiTests.InitializeFramework;
import com.myntra.apiTests.portalservices.CRMPortalService.Nodes;
import org.apache.log4j.Logger;
import org.testng.Assert;
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
import com.myntra.apiTests.common.Myntra;

/**
 * @author eshita
 * 
 */

public class CRMERPService extends CRMServiceDP {

	static Logger log = Logger.getLogger(CRMPortalService.class);

	Initialize init = InitializeFramework.init;
	APIUtilities apiUtil = new APIUtilities();
	public ResponseValidator respvalidate;

	/**
	 * This Test Case is used to invoke ERPORDER API and verification for
	 * success response (200)
	 * 
	 * @throws Exception
	 * 
	 * 
	 */

	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "CRMERPServiceDP_OrderDataProvider_verifyAPIResponse")
	public void CRMSERVICEERP_order_verifySuccessResponse(String finalstring1) throws Exception {

		
		MyntraService service = Myntra.getService(
				ServiceType.ERP_CRMSERVICEERP, APINAME.ORDER,
				init.Configurations);

		service.URL = apiUtil.prepareparameterizedURL(service.URL,
				new String[] { finalstring1});
		System.out.println(service.URL);
		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic bW9iaWxlfm1vYmlsZTptb2JpbGU");

		RequestGenerator req = new RequestGenerator(service, getParam);

		Assert.assertNotNull("CRM ERP Status is not a success type",
				Nodes.STATUS_CODE_ORDER.toString());

		Assert.assertNotNull("CRM ERP Status type is not a success type",
				Nodes.STATUS_TYPE_ORDER.toString());
		Assert.assertNotNull("CRM ERP Status type is not a success type",
				Nodes.TOTALCOUNT_ORDER.toString());

		Assert.assertEquals(200, req.response.getStatus());
		System.out.println(req.respvalidate.returnresponseasstring());

		service.responsedataformat = PayloadType.XML;

		Assert.assertEquals(req.respvalidate.ResponseFromService.getStatus(),
				200);
		/*Toolbox tools = new Toolbox();

		String jsonschema = tools
				.readFileAsString("Data/SchemaSet/JSON/crmerporder.txt");
		System.out.println(jsonschema);

		String jsondata = req.respvalidate.returnresponseasstring();

		System.out.println(jsondata);
		System.out.println("validate(jsondata, jsonschema) = "
				+ validate(jsondata, jsonschema));
		Assert.assertTrue(validate(jsondata, jsonschema));*/

	}

	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "CRMERPServiceDP_ReturnDataProvider_verifyAPIResponse")
	public void CRMSERVICEERP_return_verifySuccessResponse(
			String finalstring) throws Exception {

		MyntraService service = Myntra.getService(
				ServiceType.ERP_CRMSERVICEERP, APINAME.RETURNORDER,
				init.Configurations);

		service.URL = apiUtil.prepareparameterizedURL(service.URL,
				new String[] {  finalstring });
		System.out.println(service.URL);
		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic bW9iaWxlfm1vYmlsZTptb2JpbGU");

		RequestGenerator req = new RequestGenerator(service, getParam);

		Assert.assertEquals(200, req.response.getStatus());
		System.out.println(req.respvalidate.returnresponseasstring());

		APIUtilities utilities = new APIUtilities();
		System.out.println(service.URL);

		service.responsedataformat = PayloadType.XML;

		Assert.assertEquals(req.respvalidate.ResponseFromService.getStatus(),
				200);
	}

	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "CRMERPServiceDP_CashbackDataProvider_verifyAPIResponse")
	public void CRMSERVICEERP_cashback_verifySuccessResponse(String loginid)
			throws Exception {

		MyntraService service = Myntra.getService(
				ServiceType.ERP_CRMSERVICEERP, APINAME.CASHBACK,
				init.Configurations);
		service.URL = apiUtil.prepareparameterizedURL(service.URL, loginid);
		System.out.println(service.URL);
		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic bW9iaWxlfm1vYmlsZTptb2JpbGU");

		RequestGenerator req = new RequestGenerator(service, getParam);

		Assert.assertNotNull("CRM ERP CASHBACK Status is not a success type",
				Nodes.STATUS_CODE_CASHBACK.toString());

		Assert.assertNotNull(
				"CRM ERP CASHBACK  Status type is not a success type",
				Nodes.STATUS_TYPE_CASHBACK.toString());

		Assert.assertNotNull(
				"CRM ERP CASHBACK TOTAL COUNT is not a success type",
				Nodes.TOTALCOUNT_CASHBACK.toString());

		Assert.assertEquals(200, req.response.getStatus());
		System.out.println(req.respvalidate.returnresponseasstring());

		service.responsedataformat = PayloadType.XML;

		Assert.assertEquals(req.respvalidate.ResponseFromService.getStatus(),
				200);
		Toolbox tools = new Toolbox();

		String jsonschema = tools
				.readFileAsString("Data/SchemaSet/JSON/crmerpcashback.txt");
		System.out.println(jsonschema);

		String jsondata = req.respvalidate.returnresponseasstring();

		System.out.println("validate(jsondata, jsonschema) = "
				+ validate(jsondata, jsonschema));
		Assert.assertTrue(validate(jsondata, jsonschema));

	}

	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "CRMERPServiceDP_DeliveryStaffDataProvider_verifyAPIResponse")
	public void CRMSERVICEERP_deliveryStaff_verifySuccessResponse(
			String deliverystaffid) throws Exception {

		MyntraService service = Myntra.getService(
				ServiceType.ERP_CRMSERVICEERP, APINAME.DELIVERYSTAFF,
				init.Configurations);
		service.URL = apiUtil.prepareparameterizedURL(service.URL,
				deliverystaffid);
		System.out.println(service.URL);
		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic bW9iaWxlfm1vYmlsZTptb2JpbGU");

		RequestGenerator req = new RequestGenerator(service, getParam);
		Assert.assertEquals(200, req.response.getStatus());
		System.out.println(req.respvalidate.returnresponseasstring());
		service.responsedataformat = PayloadType.XML;

		Assert.assertEquals(req.respvalidate.ResponseFromService.getStatus(),
				200);
		Toolbox tools = new Toolbox();

		String jsonschema = tools
				.readFileAsString("Data/SchemaSet/JSON/crmerpdeliverystaff.txt");
		System.out.println(jsonschema);

		String jsondata = req.respvalidate.returnresponseasstring();

		System.out.println("validate(jsondata, jsonschema) = "
				+ validate(jsondata, jsonschema));
		Assert.assertTrue(validate(jsondata, jsonschema));

	}

	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "CRMERPServiceDP_CommonOrderIdDataProvider_verifyAPIResponse")
	public void CRMSERVICEERP_paymentlog_verifySuccessResponse(String orderid) throws Exception {

		MyntraService service = Myntra.getService(
				ServiceType.ERP_CRMSERVICEERP, APINAME.PAYMENTLOG,
				init.Configurations);

		service.URL = apiUtil.prepareparameterizedURL(service.URL,
				new String[] {  orderid });
		System.out.println(service.URL);
		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic bW9iaWxlfm1vYmlsZTptb2JpbGU");

		RequestGenerator req = new RequestGenerator(service, getParam);

		Assert.assertEquals(200, req.response.getStatus());
		System.out.println(req.respvalidate.returnresponseasstring());

		APIUtilities utilities = new APIUtilities();
		System.out.println(service.URL);

//		service.responsedataformat = PayloadType.XML;
//
//		Assert.assertEquals(req.respvalidate.ResponseFromService.getStatus(),
//				200);
//		Toolbox tools = new Toolbox();
//
//		String jsonschema = tools
//				.readFileAsString("/Users/eshita.singh/crmerppaymentlog.txt");
//		System.out.println(jsonschema);
//
//		String jsondata = req.respvalidate.returnresponseasstring();
//
//		System.out.println("validate(jsondata, jsonschema) = "
//				+ validate(jsondata, jsonschema));
//		Assert.assertTrue(validate(jsondata, jsonschema));
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
