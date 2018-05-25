package com.myntra.apiTests.erpservices.partners.Tests;

import com.myntra.apiTests.common.APINAME;
import com.myntra.apiTests.common.Myntra;
import com.myntra.apiTests.common.ServiceType;
import com.myntra.lordoftherings.Initialize;

import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.PayloadType;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import org.apache.log4j.Logger;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import java.util.HashMap;

public class ManageFulfillment
{
	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(ManageFulfillment.class);
	static String envName = "devint";

	APIUtilities apiUtil = new APIUtilities();

	/** API Name - Compute DragCoefficient **/

	@Test(groups = {"Regression" }, priority = 0)
	public void computeDragCoefficient_getStatusCode()
	{
		MyntraService service = Myntra.getService(
				ServiceType.ERP_MARKETPLACEMANAGEFULFILLMENT,
				APINAME.DRAGCOEFFICIENT, init.Configurations, PayloadType.JSON,
				PayloadType.JSON);

		HashMap getParam = new HashMap();
		getParam.put("Authorization",
				"Basic YXBpYWRtaW46bTFudHJhUjBja2V0MTMhIw==");

		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());

		String statusMessage = req.respvalidate
				.NodeValue("status.statusCode", true).replaceAll("\"", "")
				.trim();
		log.info(statusMessage);
		System.out.println(statusMessage);
		AssertJUnit.assertTrue("Status Code doesn't match",
				statusMessage.equalsIgnoreCase("1005"));
	}

	@Test(groups = { "Regression" }, priority = 0)
	public void computeDragCoefficient_getStatusMessage()
	{
		MyntraService service = Myntra.getService(
				ServiceType.ERP_MARKETPLACEMANAGEFULFILLMENT,
				APINAME.DRAGCOEFFICIENT, init.Configurations, PayloadType.JSON,
				PayloadType.JSON);

		HashMap getParam = new HashMap();
		getParam.put("Authorization",
				"Basic YXBpYWRtaW46bTFudHJhUjBja2V0MTMhIw==");

		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());

		String statusMessage = req.respvalidate
				.NodeValue("status.statusMessage", true).replaceAll("\"", "")
				.trim();
		log.info(statusMessage);
		System.out.println(statusMessage);
		AssertJUnit.assertTrue("Status Message doesn't match", statusMessage
				.equalsIgnoreCase("Vendors published for DC computation"));
	}

	@Test(groups = {"Regression" }, priority = 0)
	public void computeDragCoefficient_getStatusType()
	{
		MyntraService service = Myntra.getService(
				ServiceType.ERP_MARKETPLACEMANAGEFULFILLMENT,
				APINAME.DRAGCOEFFICIENT, init.Configurations, PayloadType.JSON,
				PayloadType.JSON);

		HashMap getParam = new HashMap();
		getParam.put("Authorization",
				"Basic YXBpYWRtaW46bTFudHJhUjBja2V0MTMhIw==");

		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());

		String statusMessage = req.respvalidate
				.NodeValue("status.statusType", true).replaceAll("\"", "")
				.trim();
		log.info(statusMessage);
		System.out.println(statusMessage);
		AssertJUnit.assertTrue("Status Type doesn't match",
				statusMessage.equalsIgnoreCase("SUCCESS"));
	}

	/** API Name - Compute Vendor Average Order **/

	@Test(groups = { "Regression"}, priority = 0)
	public void computeVendorAverageOrder_getStatusCode()
	{
		MyntraService service = Myntra.getService(
				ServiceType.ERP_MARKETPLACEMANAGEFULFILLMENT,
				APINAME.VENDORAVERAGEORDER, init.Configurations,
				PayloadType.JSON, PayloadType.JSON);

		HashMap getParam = new HashMap();
		getParam.put("Authorization",
				"Basic YXBpYWRtaW46bTFudHJhUjBja2V0MTMhIw==");

		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());

		String statusMessage = req.respvalidate
				.NodeValue("status.statusCode", true).replaceAll("\"", "")
				.trim();
		log.info(statusMessage);
		System.out.println(statusMessage);
		AssertJUnit.assertTrue("Status Code doesn't match",
				statusMessage.equalsIgnoreCase("1006"));
	}

	@Test(groups = {"Regression" }, priority = 0)
	public void computeVendorAverageOrder_getStatusMessage()
	{
		MyntraService service = Myntra.getService(
				ServiceType.ERP_MARKETPLACEMANAGEFULFILLMENT,
				APINAME.VENDORAVERAGEORDER, init.Configurations,
				PayloadType.JSON, PayloadType.JSON);

		HashMap getParam = new HashMap();
		getParam.put("Authorization",
				"Basic YXBpYWRtaW46bTFudHJhUjBja2V0MTMhIw==");

		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());

		String statusMessage = req.respvalidate
				.NodeValue("status.statusMessage", true).replaceAll("\"", "")
				.trim();
		log.info(statusMessage);
		System.out.println(statusMessage);
		AssertJUnit.assertTrue("Status Message doesn't match", statusMessage
				.equalsIgnoreCase("Vendors published for VAO computation"));
	}

	@Test(groups = {"Regression"}, priority = 0)
	public void computeVendorAverageOrder_getStatusType()
	{
		MyntraService service = Myntra.getService(
				ServiceType.ERP_MARKETPLACEMANAGEFULFILLMENT,
				APINAME.VENDORAVERAGEORDER, init.Configurations,
				PayloadType.JSON, PayloadType.JSON);

		HashMap getParam = new HashMap();
		getParam.put("Authorization",
				"Basic YXBpYWRtaW46bTFudHJhUjBja2V0MTMhIw==");

		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());

		String statusMessage = req.respvalidate
				.NodeValue("status.statusType", true).replaceAll("\"", "")
				.trim();
		log.info(statusMessage);
		System.out.println(statusMessage);
		AssertJUnit.assertTrue("Status Type doesn't match",
				statusMessage.equalsIgnoreCase("SUCCESS"));
	}

	/** API Name - Get All Bucket **/

	@Test(groups = {"Regression" }, priority = 0)
	public void getAllBucket_getStatusCode()
	{
		MyntraService service = Myntra.getService(
				ServiceType.ERP_MARKETPLACEMANAGEFULFILLMENT,
				APINAME.GETALLBUCKET, init.Configurations, PayloadType.JSON,
				PayloadType.JSON);

		HashMap getParam = new HashMap();
		getParam.put("Authorization",
				"Basic YXBpYWRtaW46bTFudHJhUjBja2V0MTMhIw==");

		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());

		String statusMessage = req.respvalidate
				.NodeValue("status.statusCode", true).replaceAll("\"", "")
				.trim();
		log.info(statusMessage);
		System.out.println(statusMessage);
		AssertJUnit.assertTrue("Status Code doesn't match",
				statusMessage.equalsIgnoreCase("1004"));
	}

	@Test(groups = {"Regression" }, priority = 0)
	public void getAllBucket_getStatusMessage()
	{
		MyntraService service = Myntra.getService(
				ServiceType.ERP_MARKETPLACEMANAGEFULFILLMENT,
				APINAME.GETALLBUCKET, init.Configurations, PayloadType.JSON,
				PayloadType.JSON);

		HashMap getParam = new HashMap();
		getParam.put("Authorization",
				"Basic YXBpYWRtaW46bTFudHJhUjBja2V0MTMhIw==");

		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());

		String statusMessage = req.respvalidate
				.NodeValue("status.statusMessage", true).replaceAll("\"", "")
				.trim();
		log.info(statusMessage);
		System.out.println(statusMessage);
		AssertJUnit.assertTrue("Status Message doesn't match",
				statusMessage.equalsIgnoreCase("Bucket fetched successfully"));
	}

	@Test(groups = {"Regression" }, priority = 0)
	public void getAllBucket_getStatusType()
	{
		MyntraService service = Myntra.getService(
				ServiceType.ERP_MARKETPLACEMANAGEFULFILLMENT,
				APINAME.GETALLBUCKET, init.Configurations, PayloadType.JSON,
				PayloadType.JSON);

		HashMap getParam = new HashMap();
		getParam.put("Authorization",
				"Basic YXBpYWRtaW46bTFudHJhUjBja2V0MTMhIw==");

		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());

		String statusMessage = req.respvalidate
				.NodeValue("status.statusType", true).replaceAll("\"", "")
				.trim();
		log.info(statusMessage);
		System.out.println(statusMessage);
		AssertJUnit.assertTrue("Status Type doesn't match",
				statusMessage.equalsIgnoreCase("SUCCESS"));
	}

	/** Api Name - Compute Property Fetch **/

	@Test(groups = { "Regression"}, priority = 0)
	public void fetchAllProperty_getStatusCode()
	{
		MyntraService service = Myntra.getService(
				ServiceType.ERP_MARKETPLACEMANAGEFULFILLMENT,
				APINAME.FETCHALLPROPERTY, init.Configurations,
				PayloadType.JSON, PayloadType.JSON);

		HashMap getParam = new HashMap();
		getParam.put("Authorization",
				"Basic YXBpYWRtaW46bTFudHJhUjBja2V0MTMhIw==");

		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());

		String statusMessage = req.respvalidate
				.NodeValue("status.statusCode", true).replaceAll("\"", "")
				.trim();
		log.info(statusMessage);
		System.out.println(statusMessage);
		AssertJUnit.assertTrue("Status Code doesn't match",
				statusMessage.equalsIgnoreCase("1008"));
	}

	@Test(groups = {"Regression" }, priority = 0)
	public void fetchAllProperty_getStatusMessage()
	{
		MyntraService service = Myntra.getService(
				ServiceType.ERP_MARKETPLACEMANAGEFULFILLMENT,
				APINAME.FETCHALLPROPERTY, init.Configurations,
				PayloadType.JSON, PayloadType.JSON);

		HashMap getParam = new HashMap();
		getParam.put("Authorization",
				"Basic YXBpYWRtaW46bTFudHJhUjBja2V0MTMhIw==");

		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());

		String statusMessage = req.respvalidate
				.NodeValue("status.statusMessage", true).replaceAll("\"", "")
				.trim();
		log.info(statusMessage);
		System.out.println(statusMessage);
		AssertJUnit.assertTrue("Status Message doesn't match", statusMessage
				.equalsIgnoreCase("Properties fetched successfully"));
	}

	@Test(groups = { "Regression" }, priority = 0)
	public void fetchAllProperty_getStatusType()
	{
		MyntraService service = Myntra.getService(
				ServiceType.ERP_MARKETPLACEMANAGEFULFILLMENT,
				APINAME.FETCHALLPROPERTY, init.Configurations,
				PayloadType.JSON, PayloadType.JSON);

		HashMap getParam = new HashMap();
		getParam.put("Authorization",
				"Basic YXBpYWRtaW46bTFudHJhUjBja2V0MTMhIw==");

		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());

		String statusMessage = req.respvalidate
				.NodeValue("status.statusType", true).replaceAll("\"", "")
				.trim();
		log.info(statusMessage);
		System.out.println(statusMessage);
		AssertJUnit.assertTrue("Status Type doesn't match",
				statusMessage.equalsIgnoreCase("SUCCESS"));
	}

	/** API Name - Add Bucket **/

	@Test(groups = {"Regression" })
	public void addBucket_getStatusCode()
	{

		MyntraService service = Myntra.getService(
				ServiceType.ERP_MARKETPLACEMANAGEFULFILLMENT,
				APINAME.ADDBUCKET, init.Configurations, new String[] {});

		HashMap getParam = new HashMap();
		getParam.put("Authorization",
				"Basic YXBpYWRtaW46bTFudHJhUjBja2V0MTMhIw==");
		System.out.println("url =" + service.URL);
		System.out.println("payload = " + service.Payload);

		RequestGenerator req = new RequestGenerator(service, getParam);
		System.out.println("response = " + req.returnresponseasstring());

		String statusMessage = req.respvalidate
				.NodeValue("status.statusCode", true).replaceAll("\"", "")
				.trim();
		log.info(statusMessage);
		System.out.println(statusMessage);
		AssertJUnit.assertTrue(
				"Add bucket is not working Status Code does't match",
				statusMessage.equalsIgnoreCase("2012"));

		AssertJUnit.assertEquals("Add bucket is not giving 200 response", 200,
				req.response.getStatus());
	}

	@Test(groups = { "Regression" })
	public void addBucket_getStatusMessage()
	{

		MyntraService service = Myntra.getService(
				ServiceType.ERP_MARKETPLACEMANAGEFULFILLMENT,
				APINAME.ADDBUCKET, init.Configurations, new String[] {});

		HashMap getParam = new HashMap();
		getParam.put("Authorization",
				"Basic YXBpYWRtaW46bTFudHJhUjBja2V0MTMhIw==");
		System.out.println("url =" + service.URL);
		System.out.println("payload = " + service.Payload);

		RequestGenerator req = new RequestGenerator(service, getParam);
		System.out.println("response = " + req.returnresponseasstring());

		String statusMessage = req.respvalidate
				.NodeValue("status.statusMessage", true).replaceAll("\"", "")
				.trim();
		log.info(statusMessage);
		System.out.println(statusMessage);
		AssertJUnit.assertTrue(
				"Add bucket is not working Status Message does't match",
				statusMessage.equalsIgnoreCase("Min Order not valid"));

		AssertJUnit.assertEquals("Add bucket is not giving 200 response", 200,
				req.response.getStatus());
	}

	@Test(groups = { "Regression" })
	public void addBucket_getStatusType()
	{

		MyntraService service = Myntra.getService(
				ServiceType.ERP_MARKETPLACEMANAGEFULFILLMENT,
				APINAME.ADDBUCKET, init.Configurations, new String[] {});

		HashMap getParam = new HashMap();
		getParam.put("Authorization",
				"Basic YXBpYWRtaW46bTFudHJhUjBja2V0MTMhIw==");
		System.out.println("url =" + service.URL);
		System.out.println("payload = " + service.Payload);

		RequestGenerator req = new RequestGenerator(service, getParam);
		System.out.println("response = " + req.returnresponseasstring());

		String statusMessage = req.respvalidate
				.NodeValue("status.statusType", true).replaceAll("\"", "")
				.trim();
		log.info(statusMessage);
		System.out.println(statusMessage);
		AssertJUnit.assertTrue(
				"Add bucket is not working Status Type does't match",
				statusMessage.equalsIgnoreCase("ERROR"));

		AssertJUnit.assertEquals("Add bucket is not giving 200 response", 200,
				req.response.getStatus());
	}

	/** API Name - Delete Bucket **/

	@Test(groups = {"Regression" })
	public void deleteBucket_getStatusCode()
	{

		MyntraService service = Myntra.getService(
				ServiceType.ERP_MARKETPLACEMANAGEFULFILLMENT,
				APINAME.DELETEBUCKET, init.Configurations, new String[] {});

		HashMap getParam = new HashMap();
		getParam.put("Authorization",
				"Basic YXBpYWRtaW46bTFudHJhUjBja2V0MTMhIw==");
		System.out.println("url =" + service.URL);
		System.out.println("payload = " + service.Payload);

		RequestGenerator req = new RequestGenerator(service, getParam);
		System.out.println("response = " + req.returnresponseasstring());

		String statusMessage = req.respvalidate
				.NodeValue("status.statusCode", true).replaceAll("\"", "")
				.trim();
		log.info(statusMessage);
		System.out.println(statusMessage);
		AssertJUnit.assertTrue(
				"Delete bucket is not working,Status Code doesn't match",
				statusMessage.equalsIgnoreCase("2014"));

		AssertJUnit.assertEquals("Delete bucket is not giving 200 response",
				200, req.response.getStatus());
	}

	@Test(groups = {"Regression" })
	public void deleteBucket_getStatusMessage()
	{

		MyntraService service = Myntra.getService(
				ServiceType.ERP_MARKETPLACEMANAGEFULFILLMENT,
				APINAME.DELETEBUCKET, init.Configurations, new String[] {});

		HashMap getParam = new HashMap();
		getParam.put("Authorization",
				"Basic YXBpYWRtaW46bTFudHJhUjBja2V0MTMhIw==");
		System.out.println("url =" + service.URL);
		System.out.println("payload = " + service.Payload);

		RequestGenerator req = new RequestGenerator(service, getParam);
		System.out.println("response = " + req.returnresponseasstring());

		String statusMessage = req.respvalidate
				.NodeValue("status.statusMessage", true).replaceAll("\"", "")
				.trim();
		log.info(statusMessage);
		System.out.println(statusMessage);
		AssertJUnit.assertTrue(
				"Delete bucket is not working,Status Message doesn't match",
				statusMessage.equalsIgnoreCase("wrong bucket key passed"));

		AssertJUnit.assertEquals("Delete bucket is not giving 200 response",
				200, req.response.getStatus());
	}

	@Test(groups = {"Regression" })
	public void deleteBucket_getStatusType()
	{

		MyntraService service = Myntra.getService(
				ServiceType.ERP_MARKETPLACEMANAGEFULFILLMENT,
				APINAME.DELETEBUCKET, init.Configurations, new String[] {});

		HashMap getParam = new HashMap();
		getParam.put("Authorization",
				"Basic YXBpYWRtaW46bTFudHJhUjBja2V0MTMhIw==");
		System.out.println("url =" + service.URL);
		System.out.println("payload = " + service.Payload);

		RequestGenerator req = new RequestGenerator(service, getParam);
		System.out.println("response = " + req.returnresponseasstring());

		String statusMessage = req.respvalidate
				.NodeValue("status.statusType", true).replaceAll("\"", "")
				.trim();
		log.info(statusMessage);
		System.out.println(statusMessage);
		AssertJUnit.assertTrue(
				"Delete bucket is not working,Status Type doesn't match",
				statusMessage.equalsIgnoreCase("ERROR"));

		AssertJUnit.assertEquals("Delete bucket is not giving 200 response",
				200, req.response.getStatus());
	}

	/** API Name - Edit Bucket **/

	@Test(groups = { "Regression" })
	public void editBucket_getStatusCode()
	{

		MyntraService service = Myntra.getService(
				ServiceType.ERP_MARKETPLACEMANAGEFULFILLMENT,
				APINAME.EDITBUCKET, init.Configurations, new String[] {});

		HashMap getParam = new HashMap();
		getParam.put("Authorization",
				"Basic YXBpYWRtaW46bTFudHJhUjBja2V0MTMhIw==");
		System.out.println("url =" + service.URL);
		System.out.println("payload = " + service.Payload);

		RequestGenerator req = new RequestGenerator(service, getParam);
		System.out.println("response = " + req.returnresponseasstring());

		String statusMessage = req.respvalidate
				.NodeValue("status.statusCode", true).replaceAll("\"", "")
				.trim();
		log.info(statusMessage);
		System.out.println(statusMessage);
		AssertJUnit.assertTrue(
				"Edit bucket is not working,Status Code doesn't match",
				statusMessage.equalsIgnoreCase("1006"));

		AssertJUnit.assertEquals("Edit bucket is not giving 200 response", 200,
				req.response.getStatus());
	}

	@Test(groups = {"Regression" })
	public void editBucket_getStatusMessage()
	{

		MyntraService service = Myntra.getService(
				ServiceType.ERP_MARKETPLACEMANAGEFULFILLMENT,
				APINAME.EDITBUCKET, init.Configurations, new String[] {});

		HashMap getParam = new HashMap();
		getParam.put("Authorization",
				"Basic YXBpYWRtaW46bTFudHJhUjBja2V0MTMhIw==");
		System.out.println("url =" + service.URL);
		System.out.println("payload = " + service.Payload);

		RequestGenerator req = new RequestGenerator(service, getParam);
		System.out.println("response = " + req.returnresponseasstring());

		String statusMessage = req.respvalidate
				.NodeValue("status.statusMessage", true).replaceAll("\"", "")
				.trim();
		log.info(statusMessage);
		System.out.println(statusMessage);
		AssertJUnit.assertTrue(
				"Edit bucket is not working,Status Message doesn't match",
				statusMessage.equalsIgnoreCase("Bucket edited successfully"));

		AssertJUnit.assertEquals("Edit bucket is not giving 200 response", 200,
				req.response.getStatus());
	}

	@Test(groups = { "Regression" })
	public void editBucket_getStatusType()
	{

		MyntraService service = Myntra.getService(
				ServiceType.ERP_MARKETPLACEMANAGEFULFILLMENT,
				APINAME.EDITBUCKET, init.Configurations, new String[] {});

		HashMap getParam = new HashMap();
		getParam.put("Authorization",
				"Basic YXBpYWRtaW46bTFudHJhUjBja2V0MTMhIw==");
		System.out.println("url =" + service.URL);
		System.out.println("payload = " + service.Payload);

		RequestGenerator req = new RequestGenerator(service, getParam);
		System.out.println("response = " + req.returnresponseasstring());

		String statusMessage = req.respvalidate
				.NodeValue("status.statusType", true).replaceAll("\"", "")
				.trim();
		log.info(statusMessage);
		System.out.println(statusMessage);
		AssertJUnit.assertTrue(
				"Edit bucket is not working,Status Type doesn't match",
				statusMessage.equalsIgnoreCase("SUCCESS"));

		AssertJUnit.assertEquals("Edit bucket is not giving 200 response", 200,
				req.response.getStatus());
	}

	/** API Name = Generate Scheduled Report **/

	@Test(groups = { "Regression" })
	public void generateReport_getStatusCode()
	{

		MyntraService service = Myntra.getService(
				ServiceType.ERP_MARKETPLACEMANAGEFULFILLMENT,
				APINAME.GENERATESCHEDULEDREPORT, init.Configurations,
				new String[] {});

		HashMap getParam = new HashMap();
		getParam.put("Authorization",
				"Basic YXBpYWRtaW46bTFudHJhUjBja2V0MTMhIw==");
		System.out.println("url =" + service.URL);
		System.out.println("payload = " + service.Payload);

		RequestGenerator req = new RequestGenerator(service, getParam);
		System.out.println("response = " + req.returnresponseasstring());

		String statusMessage = req.respvalidate
				.NodeValue("status.statusCode", true).replaceAll("\"", "")
				.trim();
		log.info(statusMessage);
		System.out.println(statusMessage);
		AssertJUnit.assertTrue(
				"Generate Report is not working,Status Code doesn't match",
				statusMessage.equalsIgnoreCase("1008"));

		AssertJUnit.assertEquals("Generate Report is not giving 200 response",
				200, req.response.getStatus());
	}

	@Test(groups = { "Regression" })
	public void generateReport_getStatusMessage()
	{

		MyntraService service = Myntra.getService(
				ServiceType.ERP_MARKETPLACEMANAGEFULFILLMENT,
				APINAME.GENERATESCHEDULEDREPORT, init.Configurations,
				new String[] {});

		HashMap getParam = new HashMap();
		getParam.put("Authorization",
				"Basic YXBpYWRtaW46bTFudHJhUjBja2V0MTMhIw==");
		System.out.println("url =" + service.URL);
		System.out.println("payload = " + service.Payload);

		RequestGenerator req = new RequestGenerator(service, getParam);
		System.out.println("response = " + req.returnresponseasstring());

		String statusMessage = req.respvalidate
				.NodeValue("status.statusMessage", true).replaceAll("\"", "")
				.trim();
		log.info(statusMessage);
		System.out.println(statusMessage);
		AssertJUnit.assertTrue(
				"Generate Report is not working,Status Message doesn't match",
				statusMessage.equalsIgnoreCase("Report generated"));

		AssertJUnit.assertEquals("Generate Report is not giving 200 response",
				200, req.response.getStatus());
	}

	@Test(groups = { "Regression" })
	public void generateReport_getStatusType()
	{

		MyntraService service = Myntra.getService(
				ServiceType.ERP_MARKETPLACEMANAGEFULFILLMENT,
				APINAME.GENERATESCHEDULEDREPORT, init.Configurations,
				new String[] {});

		HashMap getParam = new HashMap();
		getParam.put("Authorization",
				"Basic YXBpYWRtaW46bTFudHJhUjBja2V0MTMhIw==");
		System.out.println("url =" + service.URL);
		System.out.println("payload = " + service.Payload);

		RequestGenerator req = new RequestGenerator(service, getParam);
		System.out.println("response = " + req.returnresponseasstring());

		String statusMessage = req.respvalidate
				.NodeValue("status.statusType", true).replaceAll("\"", "")
				.trim();
		log.info(statusMessage);
		System.out.println(statusMessage);
		AssertJUnit.assertTrue(
				"Generate Report is not working,Status Type doesn't match",
				statusMessage.equalsIgnoreCase("SUCCESS"));

		AssertJUnit.assertEquals("Generate Report is not giving 200 response",
				200, req.response.getStatus());
	}

	/** API Name - Compute Property Edit **/

	@Test(groups = {"Regression" })
	public void propertyEdit_getStatusCode()
	{

		MyntraService service = Myntra.getService(
				ServiceType.ERP_MARKETPLACEMANAGEFULFILLMENT,
				APINAME.COMPUTEPROPERTYEDIT, init.Configurations,
				new String[] {});

		HashMap getParam = new HashMap();
		getParam.put("Authorization",
				"Basic YXBpYWRtaW46bTFudHJhUjBja2V0MTMhIw==");
		System.out.println("url =" + service.URL);
		System.out.println("payload = " + service.Payload);

		RequestGenerator req = new RequestGenerator(service, getParam);
		System.out.println("response = " + req.returnresponseasstring());

		String statusMessage = req.respvalidate
				.NodeValue("status.statusCode", true).replaceAll("\"", "")
				.trim();
		log.info(statusMessage);
		System.out.println(statusMessage);
		AssertJUnit.assertTrue(
				"Property Edit is not working,Status Code doesn't match",
				statusMessage.equalsIgnoreCase("1010"));

		AssertJUnit.assertEquals("Property Edit is not giving 200 response",
				200, req.response.getStatus());
	}

	@Test(groups = {"Regression" })
	public void propertyEdit_getStatusMessage()
	{

		MyntraService service = Myntra.getService(
				ServiceType.ERP_MARKETPLACEMANAGEFULFILLMENT,
				APINAME.COMPUTEPROPERTYEDIT, init.Configurations,
				new String[] {});

		HashMap getParam = new HashMap();
		getParam.put("Authorization",
				"Basic YXBpYWRtaW46bTFudHJhUjBja2V0MTMhIw==");
		System.out.println("url =" + service.URL);
		System.out.println("payload = " + service.Payload);

		RequestGenerator req = new RequestGenerator(service, getParam);
		System.out.println("response = " + req.returnresponseasstring());

		String statusMessage = req.respvalidate
				.NodeValue("status.statusMessage", true).replaceAll("\"", "")
				.trim();
		log.info(statusMessage);
		System.out.println(statusMessage);
		AssertJUnit.assertTrue(
				"Property Edit is not working,Status Message doesn't match",
				statusMessage.equalsIgnoreCase("Properties edited successfully"));

		AssertJUnit.assertEquals("Property Edit is not giving 200 response",
				200, req.response.getStatus());
	}

	@Test(groups = {"Regression" })
	public void propertyEdit_getStatusType()
	{

		MyntraService service = Myntra.getService(
				ServiceType.ERP_MARKETPLACEMANAGEFULFILLMENT,
				APINAME.COMPUTEPROPERTYEDIT, init.Configurations,
				new String[] {});

		HashMap getParam = new HashMap();
		getParam.put("Authorization",
				"Basic YXBpYWRtaW46bTFudHJhUjBja2V0MTMhIw==");
		System.out.println("url =" + service.URL);
		System.out.println("payload = " + service.Payload);

		RequestGenerator req = new RequestGenerator(service, getParam);
		System.out.println("response = " + req.returnresponseasstring());

		String statusMessage = req.respvalidate
				.NodeValue("status.statusType", true).replaceAll("\"", "")
				.trim();
		log.info(statusMessage);
		System.out.println(statusMessage);
		AssertJUnit.assertTrue(
				"Property Edit is not working,Status Type doesn't match",
				statusMessage.equalsIgnoreCase("SUCCESS"));

		AssertJUnit.assertEquals("Property Edit is not giving 200 response",
				200, req.response.getStatus());
	}

}
