package com.myntra.apiTests.portalservices.all;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import com.myntra.apiTests.dataproviders.KeyValuePairDP;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.Toolbox;
import com.myntra.apiTests.common.APINAME;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.PayloadType;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.apiTests.common.ServiceType;
import com.myntra.apiTests.common.Myntra;

/**
 * @author shankara.c
 * 
 */
public class KeyValuePairService extends KeyValuePairDP {
	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(KeyValuePairService.class);

	/*
	 * @Test(groups = { "Smoke", "Sanity", "Regression", "MiniRegression",
	 * "ExhaustiveRegression", "ProdSanity" }, dataProvider =
	 * "getKeyValuePairDataProvider") public void
	 * KeyValuePairService_getKeyValuePair(String urlparams) { MyntraService
	 * service = Myntra.getService( ServiceType.PORTAL_CONFIGURATION,
	 * APINAME.GETWIDGETKEYVALUE, init.Configurations, PayloadType.JSON, new
	 * String[] { urlparams }, PayloadType.JSON); RequestGenerator req = new
	 * RequestGenerator(service); log.info(service.URL);
	 * AssertJUnit.assertEquals("Get KeyValuePair service is not working", 200,
	 * req.response.getStatus());
	 * 
	 * }
	 */
	/*
	 * @Test(groups = { "Smoke", "Sanity", "Regression", "MiniRegression",
	 * "ExhaustiveRegression", "ExhaustiveRegression"}, dataProvider =
	 * "getKeyValuePairDataProvider") public void
	 * KeyValuePairService_getWidgetKeyValuePair(String urlparams) {
	 * MyntraService service = Myntra.getService(
	 * ServiceType.PORTAL_CONFIGURATION, APINAME.GETWIDGETKEYVALUE,
	 * init.Configurations, PayloadType.JSON, new String[] { urlparams },
	 * PayloadType.JSON); RequestGenerator req = new RequestGenerator(service);
	 * log.info(service.URL);
	 * 
	 * String value = req.respvalidate .NodeValue("data.value",
	 * true).replaceAll("\"", "") .trim();
	 * System.out.println("Value captured in response is : " + value);
	 * log.info(value);
	 * AssertJUnit.assertEquals("Get Widget KeyValuePair service is not working"
	 * , "99", value);
	 * 
	 * AssertJUnit.assertEquals("Get Widget KeyValuePair service is not working",
	 * 200, req.response.getStatus());
	 * 
	 * }
	 */
	/*
	 * @Test(groups = { "Sanity", "Regression", "MiniRegression",
	 * "ExhaustiveRegression", "ProdSanity" }, dataProvider =
	 * "getKeyValuePairForGivenListDataProvider") public void
	 * KeyValuePairService_getKeyValuePairForGivenList(String pair1, String
	 * pair2) { MyntraService service = Myntra.getService(
	 * ServiceType.PORTAL_CONFIGURATION, APINAME.GETKEYVALUEFORGIVENLIST,
	 * init.Configurations, PayloadType.JSON, new String[] { pair1, pair2 },
	 * PayloadType.JSON); HashMap hs = new HashMap(); hs.put("keyList",
	 * ArrayUtils.toString(new String[] { pair1, pair2 })); RequestGenerator req
	 * = new RequestGenerator(service, hs); log.info(service.URL); //
	 * System.out.println(req.respvalidate.returnresponseasstring());
	 * AssertJUnit.assertEquals("getKeyValuePairForGivenList is not working",
	 * 200, req.response.getStatus()); }
	 */
	/*
	 * @Test(groups = { "Sanity", "Regression", "MiniRegression",
	 * "ExhaustiveRegression", "ProdSanity" }) public void
	 * KeyValuePairService_getAllKeyValuePair() { MyntraService service = new
	 * MyntraService( ServiceType.PORTAL_CONFIGURATION,
	 * APINAME.GETALLKEYVALUEPAIR, init.Configurations); RequestGenerator reqGen
	 * = new RequestGenerator(service); log.info(service.URL); //
	 * System.out.println(reqGen.respvalidate.returnresponseasstring());
	 * AssertJUnit.assertEquals("getAllKeyValuePair is not working", 200,
	 * reqGen.response.getStatus()); }
	 */

	/*
	 * @Test(groups = { "Regression", "MiniRegression", "ExhaustiveRegression",
	 * "ExhaustiveRegression" }, dataProvider =
	 * "createNewFeatureKeyValuePairDataProvider") public void
	 * KeyValuePairService_createNewFeatureKeyValuePair(String key, String
	 * value, String desc) { MyntraService service = Myntra.getService(
	 * ServiceType.PORTAL_CONFIGURATION, APINAME.CREATENEWFEATUREKEYVALUEPAIR,
	 * init.Configurations, new String[] { key, value, desc }); RequestGenerator
	 * reqGen = new RequestGenerator(service); log.info(service.URL); //
	 * System.out.println(reqGen.respvalidate.returnresponseasstring());
	 * AssertJUnit.assertEquals("getAllKeyValuePair is not working", 200,
	 * reqGen.response.getStatus()); }
	 * 
	 * @Test(groups = { "Regression", "MiniRegression", "ExhaustiveRegression",
	 * "ExhaustiveRegression" }, dataProvider =
	 * "deleteFeatureKeyValuePairDataProvider") public void
	 * KeyValuePairService_deleteFeatureKeyValuePair(String key) { MyntraService
	 * service = Myntra.getService( ServiceType.PORTAL_CONFIGURATION,
	 * APINAME.DELETEFEATUREKEYVALUEPAIR, init.Configurations, PayloadType.JSON,
	 * new String[] { key }, PayloadType.JSON); RequestGenerator reqGen = new
	 * RequestGenerator(service); System.out.println(); log.info(service.URL);
	 * // System.out.println(reqGen.respvalidate.returnresponseasstring());
	 * AssertJUnit.assertEquals("deleteFeatureKeyValuePair is not working", 200,
	 * reqGen.response.getStatus()); }
	 */
	/*
	 * @Test(groups = { "Regression", "MiniRegression", "ExhaustiveRegression",
	 * "ExhaustiveRegression" }, dataProvider =
	 * "updateFeatureKeyValuePairDataProvider") public void
	 * KeyValuePairService_updateFeatureKeyValuePair(String key, String upValue,
	 * String upDesc) { MyntraService service = Myntra.getService(
	 * ServiceType.PORTAL_CONFIGURATION, APINAME.UPDATEFEATUREKEYVALUEPAIR,
	 * init.Configurations, new String[] { key, upValue, upDesc });
	 * System.out.println(service.Payload); RequestGenerator reqGen = new
	 * RequestGenerator(service); log.info(service.URL); //
	 * System.out.println(reqGen.respvalidate.returnresponseasstring());
	 * AssertJUnit.assertEquals("updateFeatureKeyValuePair is not working", 200,
	 * reqGen.response.getStatus()); }
	 * 
	 * @Test(groups = { "Regression", "MiniRegression", "ExhaustiveRegression",
	 * "ExhaustiveRegression" }, dataProvider =
	 * "updateListOfFeatureKeyValuePairDataProvider") public void
	 * KeyValuePairService_updateListOfFeatureKeyValuePair( String key1, String
	 * value1, String desc1, String key2, String value2, String desc2) {
	 * MyntraService service = Myntra.getService(
	 * ServiceType.PORTAL_CONFIGURATION,
	 * APINAME.UPDATELISTOFFEATUREKEYVALUEPAIR, init.Configurations, new
	 * String[] { key1, value1, desc1, key2, value2, desc2 });
	 * System.out.println(service.Payload); RequestGenerator reqGen = new
	 * RequestGenerator(service); log.info(service.URL); //
	 * System.out.println(reqGen.respvalidate.returnresponseasstring());
	 * AssertJUnit.assertEquals("updateFeatureKeyValuePair is not working", 200,
	 * reqGen.response.getStatus()); }
	 */

	// --------------------------------------featureGate----------------------------------------------

	/**
	 * Get a Feature Gate key value pair.
	 * 
	 * @author jhansi.bai
	 * @param urlparams
	 */
	@Test(groups = { "Smoke", "Sanity", "Prodsanity", "Regression",
			"MiniRegression", "ExhaustiveRegression", "RFPFOX7", "RFPPROD" }, dataProvider = "getKeyValuePairDP", description="This test is for FeatureKeyValue Pairs."
					+ "\n 1. Hit getKeyValue API giving a key."
					+ "\n2. Verify APIs response is 200")
	public void KeyValuePairService_getKeyValue(String key) {
		RequestGenerator req = getQueryRequest(APINAME.GETKEYVALUE, key);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("Get getKeyValue service is not working", 200,
				req.response.getStatus());

	}

	/**
	 * Get a Feature Gate key value pair.
	 * 
	 * @author jhansi.bai
	 */
	@Test(groups = { "ProdSanity", "Regression", "MiniRegression",
			"ExhaustiveRegression", "RFPFOX7", "RFPPROD" }, dataProvider = "getKeyValuePairDP", description="This test is for FeatureKeyValue Pairs."
					+ "\n1.Hit getKeyValue API giving one key."
					+ "\n 2.Verify StatusData Nodes in APIs response.")
	public void KeyValuePairService_getKeyValue_vStatusAdDataNodes(String key) {

		RequestGenerator req = getQueryRequest(APINAME.GETKEYVALUE, key);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertTrue("Inval getKeyValue API status nodes.",
				req.respvalidate.DoesNodesExists(keyValuePairStatusNodes()));
		AssertJUnit.assertTrue("Inval getKeyValue API status nodes.",
				req.respvalidate.DoesNodesExists(keyValuePairDataNodes()));
	}

	/**
	 * Get a Feature Gate key value pair.
	 * 
	 * @author jhansi.bai
	 */
	@Test(groups = { "MiniRegression", "ExhaustiveRegression", "Regression",
			"RFPFOX7", "RFPPROD" }, dataProvider = "getKeyValuePairDP1", description="This test is for FeatureKeyValue Pairs."
					+ "\n1.Hit getKeyValue API giving correct key value."
					+ "\n2.Verfiy success Status Nodes & Data Nodes in APIs response.")
	public void KeyValuePairService_getKeyValue_vSuccStatusMsg(String key,
			String value, String description) {
		RequestGenerator req = getQueryRequest(APINAME.GETKEYVALUE, key);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("Status code does't match", req.respvalidate
				.NodeValue(keyValuePairStatusNodes().get(0), true), "15002");
		AssertJUnit.assertEquals("Status message dosn't match",
				req.respvalidate.NodeValue(keyValuePairStatusNodes().get(1),
						true), "\"KeyValuePair(s) retrieved successfully\"");
		AssertJUnit.assertEquals("Status type dosn't match", req.respvalidate
				.NodeValue(keyValuePairStatusNodes().get(2), true),
				"\"SUCCESS\"");
		AssertJUnit.assertEquals("Total count dosn't match", req.respvalidate
				.NodeValue(keyValuePairStatusNodes().get(3), true), "1");
		AssertJUnit.assertEquals("key does't match", req.respvalidate
				.NodeValue(keyValuePairDataNodes().get(0), true), "\"" + key
				+ "\"");
		AssertJUnit.assertEquals("value dosn't match", req.respvalidate
				.NodeValue(keyValuePairDataNodes().get(1), true), "\"" + value
				+ "\"");
		AssertJUnit.assertEquals("description dosn't match", req.respvalidate
				.NodeValue(keyValuePairDataNodes().get(2), true), "\""
				+ description + "\"");
		log.info(req.respvalidate.returnresponseasstring());
	}

	/**
	 * Get a Feature Gate key value pair.
	 * 
	 * @author jhansi.bai
	 * @param urlparams
	 */
	@Test(groups = { "Sanity", "Regression", "MiniRegression",
			"ExhaustiveRegression", "RFPFOX7" }, dataProvider = "getKeyValuePairDP_negative",description="This test is for FeatureKeyValue Pairs."
					+ "\n1.Hit getKeyValue API giving non existing key."
					+ "\n2.Verify service is Up and key is not found.")
	public void KeyValuePairService_getKeyValue_negative(String key) {
		RequestGenerator req = getQueryRequest(APINAME.GETKEYVALUE, key);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("Get getKeyValue service is not working", 200,
				req.response.getStatus());
	}

	/**
	 * Get a Feature Gate key value pair.
	 * 
	 * @author jhansi.bai
	 */
	@Test(groups = { "Regression", "MiniRegression", "ExhaustiveRegression",
			"RFPFOX7" }, dataProvider = "getKeyValuePairDP_negative",description="This test is for FeatureKeyValue Pairs."
					+ "\n1.Hit getKeyValue API giving non existing key."
					+ "\n2.Verify correct status nodes exists in APIs response.")
	public void KeyValuePairService_getKeyValue_vNegativeStatusNodes(String key) {

		RequestGenerator req = getQueryRequest(APINAME.GETKEYVALUE, key);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertTrue("Inval getKeyValue API status nodes.",
				req.respvalidate.DoesNodesExists(keyValuePairStatusNodes()));
	}

	/**
	 * Get a Feature Gate key value pair.
	 * 
	 * @author jhansi.bai
	 */
	@Test(groups = { "MiniRegression", "ExhaustiveRegression", "Regression",
			"RFPFOX7" }, dataProvider = "getKeyValuePairDP_negative",description="This test is for FeatureKeyValue Pairs."
					+ "\n1.Hit getKeyValue API giving non existing key."
					+ "\n2.Verify StatusNodes values are of failure in APIs response.")
	public void KeyValuePairService_getKeyValue_vNegagtiveSuccStatusMsg(
			String key) {
		RequestGenerator req = getQueryRequest(APINAME.GETKEYVALUE, key);
		AssertJUnit.assertEquals("Status code does't match", req.respvalidate
				.NodeValue(keyValuePairStatusNodes().get(0), true), "10008");
		AssertJUnit.assertEquals("Status message dosn't match",
				req.respvalidate.NodeValue(keyValuePairStatusNodes().get(1),
						true), "\"Key not found\"");
		AssertJUnit
				.assertEquals("Status type dosn't match", req.respvalidate
						.NodeValue(keyValuePairStatusNodes().get(2), true),
						"\"ERROR\"");
		AssertJUnit.assertEquals("Total count dosn't match", req.respvalidate
				.NodeValue(keyValuePairStatusNodes().get(3), true), "0");
		log.info(req.respvalidate.returnresponseasstring());
	}

	/**
	 * Get a Feature Gate key value pairs for a given list.
	 * 
	 * @author jhansi.bai
	 */
	@Test(groups = { "Sanity", "Regression", "MiniRegression",
			"ExhaustiveRegression", "ProdSanity", "RFPFOX7", "RFPPROD" }, dataProvider = "getKeyValuePairForGivenListDP",description="This test is for FeatureKeyValue Pairs."
					+ "\n1.Hit getKeyValueForGivenList API by passing a list of Keys."
					+ "\n2.Verify API is Up and responsecode is 200.")
	public void KeyValuePairService_getKeyValuePairForGivenList(String keys) {
		RequestGenerator req = getHeaderRequest(
				APINAME.GETKEYVALUEFORGIVENLIST, keys);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("getKeyValuePairForGivenList is not working",
				200, req.response.getStatus());
	}

	/**
	 * Get a Feature Gate key value pair.
	 * 
	 * @author jhansi.bai
	 */
	@Test(groups = { "ProdSanity", "Regression", "MiniRegression",
			"ExhaustiveRegression", "RFPFOX7", "RFPPROD" }, dataProvider = "getKeyValuePairForGivenListDP",description="This test is for FeatureKeyValue Pairs."
					+ "\n1.Hit getKeyValueForGivenList API by passing a list of Keys."
					+ "\n2.Verify correct StatusNodes exists in APIs response.")
	public void KeyValuePairService_getKeyValuePairForGivenList_vStatusNodes(
			String keys) {

		RequestGenerator req = getHeaderRequest(
				APINAME.GETKEYVALUEFORGIVENLIST, keys);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertTrue("Inval getKeyValue API status nodes.",
				req.respvalidate.DoesNodesExists(keyValuePairStatusNodes()));
	}

	/**
	 * Get a Feature Gate key value pair.
	 * 
	 * @author jhansi.bai
	 */
	@Test(groups = { "MiniRegression", "ExhaustiveRegression", "Regression",
			"RFPFOX7", "RFPPROD" }, dataProvider = "getKeyValuePairForGivenListDP",description="This test is for FeatureKeyValue Pairs."
					+ "\n1.Hit getKeyValueForGivenList API by passing a list of Keys."
					+ "\n2.Verify values of StatusNodes & DataNodes are of success.")
	public void KeyValuePairService_getKeyValuePairForGivenList_vSuccStatusMsg(
			String keys) {
		RequestGenerator req = getHeaderRequest(
				APINAME.GETKEYVALUEFORGIVENLIST, keys);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("Status code does't match", req.respvalidate
				.NodeValue(keyValuePairStatusNodes().get(0), true), "15002");
		AssertJUnit.assertEquals("Status message dosn't match",
				req.respvalidate.NodeValue(keyValuePairStatusNodes().get(1),
						true), "\"KeyValuePair(s) retrieved successfully\"");
		AssertJUnit.assertEquals("Status type dosn't match", req.respvalidate
				.NodeValue(keyValuePairStatusNodes().get(2), true),
				"\"SUCCESS\"");
		AssertJUnit.assertEquals("Total count dosn't match", req.respvalidate
				.NodeValue(keyValuePairStatusNodes().get(3), true),
				req.respvalidate.GetArraySize("data") + "");
		log.info(req.respvalidate.returnresponseasstring());
	}

	/**
	 * Get a Feature Gate key value pairs for a given list.
	 * 
	 * @author jhansi.bai
	 */
	@Test(groups = { "Sanity", "ProdSanity", "Regression", "MiniRegression",
			"ExhaustiveRegression", "RFPFOX7" }, dataProvider = "getKeyValuePairForGivenListDP_negative",
			description="This test is for FeatureKeyValue Pairs."
					+ "\n1.Hit getKeyValueForGivenList API passing non existing Keys."
					+ "\n2.Verify API is not failing and giving 200 response.")
	public void KeyValuePairService_getKeyValuePairForGivenList_negative200Status(
			String keys) {
		RequestGenerator req = getHeaderRequest(
				APINAME.GETKEYVALUEFORGIVENLIST, keys);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("getKeyValuePairForGivenList is not working",
				200, req.response.getStatus());
	}

	/**
	 * Get a Feature Gate key value pair.
	 * 
	 * @author jhansi.bai
	 */
	@Test(groups = { "Regression", "MiniRegression", "ExhaustiveRegression",
			"RFPFOX7" }, dataProvider = "getKeyValuePairForGivenListDP_negative"
			,description="This test is for FeatureKeyValue Pairs."
					+ "\n1.Hit givenKeyValueForGivenList API passing non existing Keys."
					+ "\n2.Verify correct StatusNodes exists in APIs response.")
	public void KeyValuePairService_getKeyValuePairForGivenList_vNegativeStatusNodes(
			String keys) {

		RequestGenerator req = getHeaderRequest(
				APINAME.GETKEYVALUEFORGIVENLIST, keys);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertTrue("Inval getKeyValue API status nodes.",
				req.respvalidate.DoesNodesExists(keyValuePairStatusNodes()));
	}

	/**
	 * Get a Feature Gate key value pair.
	 * 
	 * @author jhansi.bai
	 */
	@Test(groups = { "MiniRegression", "ExhaustiveRegression", "Regression",
			"RFPFOX7" }, dataProvider = "getKeyValuePairForGivenListDP_negative",
			description="This test is for FeatureKeyValue Pairs."
					+ "\n1.Hit givenKeyValueForGivenList API passing non existing Keys."
					+ "\n2.Verify StatusNodes values in APIs response.")
	public void KeyValuePairService_getKeyValuePairForGivenList_vNegativeSuccStatusMsg(
			String keys) {
		RequestGenerator req = getHeaderRequest(
				APINAME.GETKEYVALUEFORGIVENLIST, keys);
		AssertJUnit.assertEquals("Status code does't match", req.respvalidate
				.NodeValue(keyValuePairStatusNodes().get(0), true), "15002");
		AssertJUnit.assertEquals("Status message dosn't match",
				req.respvalidate.NodeValue(keyValuePairStatusNodes().get(1),
						true), "\"KeyValuePair(s) retrieved successfully\"");
		AssertJUnit.assertEquals("Status type dosn't match", req.respvalidate
				.NodeValue(keyValuePairStatusNodes().get(2), true),
				"\"SUCCESS\"");
		log.info(req.respvalidate.returnresponseasstring());
	}

	/**
	 * Get a Feature Gate key value pairs for a given list.
	 * 
	 * @author jhansi.bai
	 */
	@Test(groups = { "Sanity", "Regression", "MiniRegression",
			"ExhaustiveRegression", "RFPFOX7" }, dataProvider = "getKeyValuePairForGivenListDP_negative1",
			description="This test is for FeatureKeyValue Pairs."
					+ "\n1.Hit getKeyValueForGivenList API passing null value as Key."
					+ "\n2.Verify API response is 500.")
	public void KeyValuePairService_getKeyValuePairForGivenList_negative500Status(
			String keys) {
		RequestGenerator req = getHeaderRequest(
				APINAME.GETKEYVALUEFORGIVENLIST, keys);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("getKeyValuePairForGivenList is not working",
				500, req.response.getStatus());
	}

	/**
	 * Get all Feature Gate key value pairs.
	 * 
	 * @author jhansi.bai
	 */
	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
			"Regression", "RFPFOX7", "RFPPROD" },description="This test is for FeatureKeyValue Pairs. \n1.Hit getAllKeyValuePair."
					+ "\n2.Verify API response is 200.")
	public void KeyValuePairService_getAllKeyValuePair() {
		RequestGenerator req = getRequest(APINAME.GETALLKEYVALUEPAIR);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("getAllKeyValuePair is not working", 200,
				req.response.getStatus());
	}

	/**
	 * Get all Feature Gate key value pairs.
	 * 
	 * @author jhansi.bai
	 */
	@Test(groups = { "MiniRegression", "ExhaustiveRegression", "Regression",
			"RFPFOX7", "RFPPROD" },description="This test is for FeatureKeyValue Pairs."
					+ "\n1.Hit getAllKeyValuePair API. \n2.Verify StatusNodes exists in APIs response.")
	public void KeyValuePairService_getAllKeyValuePair_vStatusNodes() {

		RequestGenerator req = getRequest(APINAME.GETALLKEYVALUEPAIR);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertTrue("Inval getKeyValue API status nodes.",
				req.respvalidate.DoesNodesExists(keyValuePairStatusNodes()));
	}

	/**
	 * Verify all Feature Gate key value pairs
	 * 
	 * @author jhansi.bai
	 */
	@Test(groups = { "MiniRegression", "ExhaustiveRegression", "Regression",
			"RFPFOX7", "RFPPROD" },description="This test is for FeatureKeyValue Pairs. \n1.Hit getAllKeyValuePair API. "
					+ "\n2.Verify StatusNodes values are of success in APIs response.")
	public void PageConfigurator_getAllKeyValuePair_vSuccStatusMsg() {
		RequestGenerator req = getRequest(APINAME.GETALLKEYVALUEPAIR);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("Status code does't match", req.respvalidate
				.NodeValue(keyValuePairStatusNodes().get(0), true), "15002");
		AssertJUnit.assertEquals("Status message dosn't match",
				req.respvalidate.NodeValue(keyValuePairStatusNodes().get(1),
						true), "\"KeyValuePair(s) retrieved successfully\"");
		AssertJUnit.assertEquals("Status type dosn't match", req.respvalidate
				.NodeValue(keyValuePairStatusNodes().get(2), true),
				"\"SUCCESS\"");
		AssertJUnit.assertEquals("Total count dosn't match", req.respvalidate
				.NodeValue(keyValuePairStatusNodes().get(3), true),
				req.respvalidate.GetArraySize("data") + "");
		log.info(req.respvalidate.returnresponseasstring());
	}

	// ----------------------Until unless delete api is used, this is not
	// suppose to execute in regular basis--------------
	/*	
	*//**
	 * Create a new Feature Gate key value pair.
	 * 
	 * @author jhansi.bai
	 */
	/*
	 * @Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
	 * "Regression", "RFPFOX7" }, dataProvider =
	 * "createNewFeatureKeyValuePairDP") public void
	 * KeyValuePairService_createNewFeatureKeyValuePair(String key, String
	 * value, String desc, String SuccessCode, String successMessage, String
	 * status, String totalCount) { RequestGenerator req =
	 * getRequest(APINAME.CREATENEWFEATUREKEYVALUEPAIR, key, value, desc); //
	 * System.out.println(reqGen.respvalidate.returnresponseasstring());
	 * AssertJUnit.assertEquals("createNewFeatureKeyValuePair is not working",
	 * 200, req.response.getStatus()); }
	 *//**
	 * Create a new Feature Gate key value pair.
	 * 
	 * @author jhansi.bai
	 */
	/*
	 * @Test(groups = {"MiniRegression", "ExhaustiveRegression", "Regression",
	 * "RFPFOX7" }, dataProvider = "createNewFeatureKeyValuePairDP") public void
	 * KeyValuePairService_createNewFeatureKeyValuePair_vStatusAdDataNodes
	 * (String key, String value, String desc, String SuccessCode, String
	 * successMessage, String status, String totalCount) {
	 * 
	 * RequestGenerator req = getRequest(APINAME.CREATENEWFEATUREKEYVALUEPAIR,
	 * key, value, desc);
	 * System.out.println(req.respvalidate.returnresponseasstring());
	 * AssertJUnit
	 * .assertTrue("Inval createNewFeatureKeyValuePair API status nodes.",
	 * req.respvalidate.DoesNodesExists(keyValuePairStatusNodes()));
	 * AssertJUnit.
	 * assertTrue("Inval createNewFeatureKeyValuePair API status nodes.",
	 * req.respvalidate.DoesNodesExists(keyValuePairDataNodes())); }
	 *//**
	 * Create a new Feature Gate key value pair.
	 * 
	 * @author jhansi.bai
	 */
	/*
	 * @Test(groups = {"MiniRegression", "ExhaustiveRegression", "Regression",
	 * "RFPFOX7"}, dataProvider = "createNewFeatureKeyValuePairDP") public void
	 * KeyValuePairService_createNewFeatureKeyValuePair_vSuccStatusMsg(String
	 * key, String value, String desc, String SuccessCode, String
	 * successMessage, String status, String totalCount) { RequestGenerator req
	 * = getRequest(APINAME.CREATENEWFEATUREKEYVALUEPAIR, key, value, desc);
	 * AssertJUnit.assertEquals("Status code does't match",
	 * req.respvalidate.NodeValue(keyValuePairStatusNodes().get(0), true),
	 * SuccessCode); AssertJUnit.assertEquals("Status message dosn't match",
	 * req.respvalidate.NodeValue(keyValuePairStatusNodes().get(1), true),
	 * successMessage); AssertJUnit.assertEquals("Status type dosn't match",
	 * req.respvalidate.NodeValue(keyValuePairStatusNodes().get(2), true),
	 * status); AssertJUnit.assertEquals("Total count dosn't match",
	 * req.respvalidate.NodeValue(keyValuePairStatusNodes().get(3), true),
	 * totalCount); AssertJUnit.assertEquals("Status code does't match",
	 * req.respvalidate.NodeValue(keyValuePairDataNodes().get(0), true),
	 * "\""+key+"\""); AssertJUnit.assertEquals("Status message dosn't match",
	 * req.respvalidate.NodeValue(keyValuePairDataNodes().get(1), true),
	 * "\""+value+"\""); AssertJUnit.assertEquals("Status type dosn't match",
	 * req.respvalidate.NodeValue(keyValuePairDataNodes().get(2), true),
	 * "\""+desc+"\""); log.info(req.respvalidate.returnresponseasstring()); }
	 *//**
	 * Create a new Feature Gate key value pair.
	 * 
	 * @author jhansi.bai
	 */
	/*
	 * @Test(groups = { "Sanity", "Regression", "MiniRegression",
	 * "ExhaustiveRegression", "RFPFOX7"}, dataProvider =
	 * "createNewFeatureKeyValuePairDP_negative") public void
	 * KeyValuePairService_createNewFeatureKeyValuePair_negative(String key,
	 * String value, String desc, String errorCode, String errorMessage, String
	 * status, String totalCount) { RequestGenerator req =
	 * getRequest(APINAME.CREATENEWFEATUREKEYVALUEPAIR, key, value, desc); //
	 * System.out.println(reqGen.respvalidate.returnresponseasstring());
	 * AssertJUnit.assertEquals("createNewFeatureKeyValuePair is not working",
	 * 200, req.response.getStatus()); }
	 *//**
	 * Create a new Feature Gate key value pair.
	 * 
	 * @author jhansi.bai
	 */
	/*
	 * @Test(groups = {"Regression", "MiniRegression", "ExhaustiveRegression",
	 * "RFPFOX7" }, dataProvider = "createNewFeatureKeyValuePairDP_negative")
	 * public void
	 * KeyValuePairService_createNewFeatureKeyValuePair_vNegativeStatusAdDataNodes
	 * (String key, String value, String desc, String errorCode, String
	 * errorMessage, String status, String totalCount) {
	 * 
	 * RequestGenerator req = getRequest(APINAME.CREATENEWFEATUREKEYVALUEPAIR,
	 * key, value, desc);
	 * System.out.println(req.respvalidate.returnresponseasstring());
	 * AssertJUnit
	 * .assertTrue("Inval createNewFeatureKeyValuePair API status nodes.",
	 * req.respvalidate.DoesNodesExists(keyValuePairStatusNodes())); }
	 *//**
	 * Create a new Feature Gate key value pair.
	 * 
	 * @author jhansi.bai
	 */
	/*
	 * @Test(groups = {"MiniRegression", "ExhaustiveRegression", "Regression",
	 * "RFPFOX7"}, dataProvider = "createNewFeatureKeyValuePairDP_negative")
	 * public void
	 * KeyValuePairService_createNewFeatureKeyValuePair_vNegativeSuccStatusMsg
	 * (String key, String value, String desc, String errorCode, String
	 * errorMessage, String status, String totalCount ) { RequestGenerator req =
	 * getRequest(APINAME.CREATENEWFEATUREKEYVALUEPAIR, key, value, desc);
	 * AssertJUnit.assertEquals("Status code does't match",
	 * req.respvalidate.NodeValue(keyValuePairStatusNodes().get(0), true),
	 * errorCode); AssertJUnit.assertEquals("Status message dosn't match",
	 * req.respvalidate.NodeValue(keyValuePairStatusNodes().get(1), true),
	 * errorMessage); AssertJUnit.assertEquals("Status type dosn't match",
	 * req.respvalidate.NodeValue(keyValuePairStatusNodes().get(2), true),
	 * status); AssertJUnit.assertEquals("Total count dosn't match",
	 * req.respvalidate.NodeValue(keyValuePairStatusNodes().get(3), true),
	 * totalCount); log.info(req.respvalidate.returnresponseasstring()); }
	 *//**
	 * Create a new Feature Gate key value pair.
	 * 
	 * @author jhansi.bai
	 */
	/*
	 * @Test(groups = {"MiniRegression", "ExhaustiveRegression", "Regression",
	 * "RFPFOX7"}, dataProvider = "createNewFeatureKeyValuePairDP_negative1")
	 * public void
	 * KeyValuePairService_createNewFeatureKeyValuePair_vNegativeSuccStatusMsg1
	 * (String key, String value, String desc, String errorCode, String
	 * errorMessage, String status, String totalCount ) { RequestGenerator req =
	 * getRequest(APINAME.CREATENEWFEATUREKEYVALUEPAIR, key, value, desc);
	 * AssertJUnit.assertEquals("Status code does't match",
	 * req.respvalidate.NodeValue(keyValuePairStatusNodes().get(0), true),
	 * errorCode); AssertJUnit.assertEquals("Status message dosn't match",
	 * req.respvalidate.NodeValue(keyValuePairStatusNodes().get(1), true),
	 * errorMessage); AssertJUnit.assertEquals("Status type dosn't match",
	 * req.respvalidate.NodeValue(keyValuePairStatusNodes().get(2), true),
	 * status); AssertJUnit.assertEquals("Total count dosn't match",
	 * req.respvalidate.NodeValue(keyValuePairStatusNodes().get(3), true),
	 * totalCount); log.info(req.respvalidate.returnresponseasstring()); }
	 */

	/**
	 * update key value pair.
	 * 
	 * @author jhansi.bai
	 */
	@Test(groups = { "Sanity", "Regression", "MiniRegression",
			"ExhaustiveRegression", "RFPFOX7" }, dataProvider = "updateKeyValuePairDP", 
			description="This test is for FeatureKeyValue Pairs. \n1.Hit updateKeyValuePair API Passing correct value set."
					+ "\n2.Verify API is Up with response code 200.")
	public void KeyValuePairService_updateKeyValuePair(String key,
			String value, String desc, String SuccessCode,
			String successMessage, String status, String totalCount) {
		RequestGenerator req = getRequest(APINAME.UPDATEFEATUREKEYVALUEPAIR,
				key, value, desc);
		// System.out.println(reqGen.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("updateKeyValuePair is not working", 200,
				req.response.getStatus());
	}

	/**
	 * update key value pair.
	 * 
	 * @author jhansi.bai
	 */
	@Test(groups = { "Regression", "MiniRegression", "ExhaustiveRegression",
			"RFPFOX7" }, dataProvider = "updateKeyValuePairDP",
			description="This test is for FeatureKeyValue Pairs. \n1.Hit updateKeyValuePair API Passing correct value set."
					+ "\n2.Verify API response contains correct StatusNodes & DataNodes.")
	public void KeyValuePairService_updateKeyValuePair_vStatusAdDataNodes(
			String key, String value, String desc, String SuccessCode,
			String successMessage, String status, String totalCount) {

		RequestGenerator req = getRequest(APINAME.UPDATEFEATUREKEYVALUEPAIR,
				key, value, desc);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertTrue("Inval updateKeyValuePair API status nodes.",
				req.respvalidate.DoesNodesExists(keyValuePairStatusNodes()));
		AssertJUnit.assertTrue("Inval updateKeyValuePair API status nodes.",
				req.respvalidate.DoesNodesExists(keyValuePairDataNodes()));
	}

	/**
	 * update key value pair.
	 * 
	 * @author jhansi.bai
	 */
	@Test(groups = { "MiniRegression", "ExhaustiveRegression", "Regression",
			"RFPFOX7" }, dataProvider = "updateKeyValuePairDP",
			description="This test is for FeatureKeyValue Pairs. \n1.Hit updateKeyValuePair API Passing correct value set."
					+ "\n2.Verify StatusNodes & DataNodes values are of success in APIs response.")
	public void KeyValuePairService_updateKeyValuePair_vSuccStatusMsg(
			String key, String value, String desc, String SuccessCode,
			String successMessage, String status, String totalCount) {
		RequestGenerator req = getRequest(APINAME.UPDATEFEATUREKEYVALUEPAIR,
				key, value, desc);
		AssertJUnit
				.assertEquals("Status code does't match", req.respvalidate
						.NodeValue(keyValuePairStatusNodes().get(0), true),
						SuccessCode);
		AssertJUnit.assertEquals("Status message dosn't match",
				req.respvalidate.NodeValue(keyValuePairStatusNodes().get(1),
						true), successMessage);
		AssertJUnit.assertEquals("Status type dosn't match", req.respvalidate
				.NodeValue(keyValuePairStatusNodes().get(2), true), status);
		AssertJUnit.assertEquals("Total count dosn't match", req.respvalidate
				.NodeValue(keyValuePairStatusNodes().get(3), true), totalCount);
		AssertJUnit.assertEquals("Status code does't match", req.respvalidate
				.NodeValue(keyValuePairDataNodes().get(0), true), "\"" + key
				+ "\"");
		AssertJUnit.assertEquals("Status message dosn't match",
				req.respvalidate
						.NodeValue(keyValuePairDataNodes().get(1), true), "\""
						+ value + "\"");
		AssertJUnit.assertEquals("Status type dosn't match", req.respvalidate
				.NodeValue(keyValuePairDataNodes().get(2), true), "\"" + desc
				+ "\"");
		log.info(req.respvalidate.returnresponseasstring());
	}

	/**
	 * update key value pair.
	 * 
	 * @author jhansi.bai
	 */
	@Test(groups = { "Sanity", "Regression", "MiniRegression",
			"ExhaustiveRegression", "RFPFOX7" }, dataProvider = "updateKeyValuePairDP_negative",
			description="This test is for FeatureKeyValue Pairs."
					+ "\n1.Hit updateKeyValuePair API Passing incorrect value set."
					+ "\n2.Verify API is not failing and response code is 200")
	public void KeyValuePairService_updateKeyValuePair_negative(String key,
			String value, String desc, String errorCode, String errorMessage,
			String status, String totalCount) {
		RequestGenerator req = getRequest(APINAME.UPDATEFEATUREKEYVALUEPAIR,
				key, value, desc);
		// System.out.println(reqGen.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("updateKeyValuePair is not working", 200,
				req.response.getStatus());
	}

	/**
	 * update key value pair.
	 * 
	 * @author jhansi.bai
	 */
	@Test(groups = { "Regression", "MiniRegression", "ExhaustiveRegression",
			"RFPFOX7" }, dataProvider = "updateKeyValuePairDP_negative",
			description="This test is for FeatureKeyValue Pairs. \n1.Hit updateKeyValuePair API Passing incorrect value set."
					+ "\n2.Verify API is not failing and correct StatusNodes exists in APIs response.")
	public void KeyValuePairService_updateKeyValuePair_vNegativeStatusNodes(
			String key, String value, String desc, String errorCode,
			String errorMessage, String status, String totalCount) {

		RequestGenerator req = getRequest(APINAME.UPDATEFEATUREKEYVALUEPAIR,
				key, value, desc);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertTrue("Inval updateKeyValuePair API status nodes.",
				req.respvalidate.DoesNodesExists(keyValuePairStatusNodes()));
	}

	/**
	 * update key value pair.
	 * 
	 * @author jhansi.bai
	 */
	@Test(groups = { "MiniRegression", "ExhaustiveRegression", "Regression",
			"RFPFOX7" }, dataProvider = "updateKeyValuePairDP_negative",
			description="This test is for FeatureKeyValue Pairs. \n1.Hit updateKeyValuePair API Passing incorrect value set."
					+ "\n2.Verify StatusNodes data in APIs response.")
	public void KeyValuePairService_updateKeyValuePair_vNegativeSuccStatusMsg(
			String key, String value, String desc, String errorCode,
			String errorMessage, String status, String totalCount) {
		RequestGenerator req = getRequest(APINAME.UPDATEFEATUREKEYVALUEPAIR,
				key, value, desc);
		AssertJUnit.assertEquals("Status code does't match", req.respvalidate
				.NodeValue(keyValuePairStatusNodes().get(0), true), errorCode);
		AssertJUnit.assertEquals("Status message dosn't match",
				req.respvalidate.NodeValue(keyValuePairStatusNodes().get(1),
						true), errorMessage);
		AssertJUnit.assertEquals("Status type dosn't match", req.respvalidate
				.NodeValue(keyValuePairStatusNodes().get(2), true), status);
		AssertJUnit.assertEquals("Total count dosn't match", req.respvalidate
				.NodeValue(keyValuePairStatusNodes().get(3), true), totalCount);
		log.info(req.respvalidate.returnresponseasstring());
	}

	/**
	 * update key value pair for a given list.
	 * 
	 * @author jhansi.bai
	 */
	@Test(groups = { "Sanity", "Regression", "MiniRegression",
			"ExhaustiveRegression", "RFPFOX7" }, dataProvider = "updateKeyValuePairForGivenListDP",
			description="This test is for FeatureKeyValue Pairs. \n1.Hit updateListOfFeatureKeyValuePair API Passing correct keys set."
					+ "\n2.Verify API is Up & response code is 200.")
	public void KeyValuePairService_updateKeyValuePairForGivenList(String key1,
			String value1, String desc1, String key2, String value2,
			String desc2, String SuccessCode, String successMessage,
			String status, String totalCount) {
		RequestGenerator req = getRequest(
				APINAME.UPDATELISTOFFEATUREKEYVALUEPAIR, key1, value1, desc1,
				key2, value2, desc2);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("getKeyValuePairForGivenList is not working",
				200, req.response.getStatus());
	}

	/**
	 * update key value pair for a given list.
	 * 
	 * @author jhansi.bai
	 */
	@Test(groups = { "Regression", "MiniRegression", "ExhaustiveRegression",
			"RFPFOX7" }, dataProvider = "updateKeyValuePairForGivenListDP",
			description="This test is for FeatureKeyValue Pairs. \n1.Hit updateListOfFeatureKeyValuePair API Passing correct keys set."
					+ "\n2.Verify API is Up & StatusNodes and DataNodes exists in response.")
	public void KeyValuePairService_updateKeyValuePairForGivenList_vStatusadDataNodes(
			String key1, String value1, String desc1, String key2,
			String value2, String desc2, String SuccessCode,
			String successMessage, String status, String totalCount) {
		RequestGenerator req = getRequest(
				APINAME.UPDATELISTOFFEATUREKEYVALUEPAIR, key1, value1, desc1,
				key2, value2, desc2);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertTrue("Inval updateKeyValuePair API status nodes.",
				req.respvalidate.DoesNodesExists(keyValuePairStatusNodes()));
		AssertJUnit.assertTrue(
				"Inval updateKeyValuePairForGivenList API status nodes.",
				req.respvalidate.DoesNodesExists(keyValuePairDataNodes()));
	}

	/**
	 * update key value pair for a given list.
	 * 
	 * @author jhansi.bai
	 */
	@Test(groups = { "Regression", "MiniRegression", "ExhaustiveRegression",
			"RFPFOX7" }, dataProvider = "updateKeyValuePairForGivenListDP",
			description="This test is for FeatureKeyValue Pairs. \n1.Hit updateListOfFeatureKeyValuePair API Passing correct keys set."
					+ "\n2.Verify StatusNodes values are of success in APIs response.")
	public void KeyValuePairService_updateKeyValuePairForGivenList_vSuccStatusMsg(
			String key1, String value1, String desc1, String key2,
			String value2, String desc2, String SuccessCode,
			String successMessage, String status, String totalCount) {
		RequestGenerator req = getRequest(
				APINAME.UPDATELISTOFFEATUREKEYVALUEPAIR, key1, value1, desc1,
				key2, value2, desc2);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit
				.assertEquals("Status code does't match", req.respvalidate
						.NodeValue(keyValuePairStatusNodes().get(0), true),
						SuccessCode);
		AssertJUnit.assertEquals("Status message dosn't match",
				req.respvalidate.NodeValue(keyValuePairStatusNodes().get(1),
						true), successMessage);
		AssertJUnit.assertEquals("Status type dosn't match", req.respvalidate
				.NodeValue(keyValuePairStatusNodes().get(2), true), status);
		log.info(req.respvalidate.returnresponseasstring());
	}

	/**
	 * update key value pair for a given list.
	 * 
	 * @author jhansi.bai
	 */
	@Test(groups = { "Sanity", "Regression", "MiniRegression",
			"ExhaustiveRegression", "RFPFOX7" }, dataProvider = "updateKeyValuePairForGivenListDP_negative",
			description="This test is for FeatureKeyValue Pairs. \n1.Hit updateListOfFeatureKeyValuePair API Passing non existing keys set."
					+ "\n2.Verify API is not failing & response code is 200.")
	public void KeyValuePairService_updateKeyValuePairForGivenList_negative(
			String key1, String value1, String desc1, String key2,
			String value2, String desc2, String errorCode, String errorMessage,
			String status, String totalCount) {
		RequestGenerator req = getRequest(
				APINAME.UPDATELISTOFFEATUREKEYVALUEPAIR, key1, value1, desc1,
				key2, value2, desc2);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("getKeyValuePairForGivenList is not working",
				200, req.response.getStatus());
	}

	/**
	 * update key value pair for a given list.
	 * 
	 * @author jhansi.bai
	 */
	@Test(groups = { "Sanity", "Regression", "MiniRegression",
			"ExhaustiveRegression", "RFPFOX7" }, dataProvider = "updateKeyValuePairForGivenListDP_negative",
			description="This test is for FeatureKeyValue Pairs. \n1.Hit updateListOfFeatureKeyValuePair API Passing non existing keys set."
					+ "\n2.Verify API is not failing & StatusNodes exists in response.")
	public void KeyValuePairService_updateKeyValuePairForGivenList_vNegativeStatusNodes(
			String key1, String value1, String desc1, String key2,
			String value2, String desc2, String errorCode, String errorMessage,
			String status, String totalCount) {
		RequestGenerator req = getRequest(
				APINAME.UPDATELISTOFFEATUREKEYVALUEPAIR, key1, value1, desc1,
				key2, value2, desc2);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertTrue("Inval updateKeyValuePair API status nodes.",
				req.respvalidate.DoesNodesExists(keyValuePairStatusNodes()));
	}

	/**
	 * update key value pair for a given list.
	 * 
	 * @author jhansi.bai
	 */
	@Test(groups = { "Sanity", "Regression", "MiniRegression",
			"ExhaustiveRegression", "RFPFOX7" }, dataProvider = "updateKeyValuePairForGivenListDP_negative",
			description="This test is for FeatureKeyValue Pairs. \n1.Hit updateListOfFeatureKeyValuePair API Passing non existing keys set."
					+ "\n2.Verify StatusNodes values in APIs response.")
	public void KeyValuePairService_updateKeyValuePairForGivenList_vNegativeSuccStatusMsg(
			String key1, String value1, String desc1, String key2,
			String value2, String desc2, String errorCode, String errorMessage,
			String status, String totalCount) {
		RequestGenerator req = getRequest(
				APINAME.UPDATELISTOFFEATUREKEYVALUEPAIR, key1, value1, desc1,
				key2, value2, desc2);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("Status code does't match", req.respvalidate
				.NodeValue(keyValuePairStatusNodes().get(0), true), errorCode);
		AssertJUnit.assertEquals("Status message dosn't match",
				req.respvalidate.NodeValue(keyValuePairStatusNodes().get(1),
						true), errorMessage);
		AssertJUnit.assertEquals("Status type dosn't match", req.respvalidate
				.NodeValue(keyValuePairStatusNodes().get(2), true), status);
		AssertJUnit.assertEquals("Total count dosn't match", req.respvalidate
				.NodeValue(keyValuePairStatusNodes().get(3), true), totalCount);
		log.info(req.respvalidate.returnresponseasstring());
	}

	// ---------------------------------------This API is skipped. no more
	// used-----------------------------------------------------------
	/*
	*//**
	 * Delete key value pair.
	 * 
	 * @author jhansi.bai
	 */
	/*
	 * @Test(groups = { "Regression", "MiniRegression", "ExhaustiveRegression",
	 * "RFPFOX7"}, dataProvider = "deleteKeyValuePairDP") public void
	 * KeyValuePairService_deleateKeyValuePair(String key, String errorCode,
	 * String errorMessage, String status, String totalCount) { RequestGenerator
	 * req = getQueryRequest(APINAME.DELETEFEATUREKEYVALUEPAIR, key); //
	 * System.out.println(reqGen.respvalidate.returnresponseasstring());
	 * AssertJUnit.assertEquals("Inval deleateKeyValuePair API. ", 200,
	 * req.response.getStatus()); }
	 *//**
	 * Delete key value pair.
	 * 
	 * @author jhansi.bai
	 */
	/*
	 * @Test(groups = {"Smoke", "Sanity", "ProdSanity", "Regression",
	 * "MiniRegression", "ExhaustiveRegression", "RFPFOX7" }, dataProvider =
	 * "deleteKeyValuePairDP") public void
	 * KeyValuePairService_deleateKeyValuePair_vStatusNodes(String key, String
	 * errorCode, String errorMessage, String status, String totalCount) {
	 * 
	 * RequestGenerator req = getQueryRequest(APINAME.DELETEFEATUREKEYVALUEPAIR,
	 * key); System.out.println(req.respvalidate.returnresponseasstring());
	 * AssertJUnit.assertTrue("Inval deleateKeyValuePair API status nodes.",
	 * req.respvalidate.DoesNodesExists(keyValuePairStatusNodes())); }
	 *//**
	 * Delete key value pair.
	 * 
	 * @author jhansi.bai
	 */
	/*
	 * @Test(groups = {"Sanity","MiniRegression", "ExhaustiveRegression",
	 * "Regression", "RFPFOX7"}, dataProvider = "deleteKeyValuePairDP") public
	 * void KeyValuePairService_deleateKeyValuePair_vSuccStatusMsg(String key,
	 * String errorCode, String errorMessage, String status, String totalCount )
	 * { RequestGenerator req =
	 * getQueryRequest(APINAME.DELETEFEATUREKEYVALUEPAIR, key);
	 * AssertJUnit.assertEquals("Status code does't match",
	 * req.respvalidate.NodeValue(keyValuePairStatusNodes().get(0), true),
	 * errorCode); AssertJUnit.assertEquals("Status message dosn't match",
	 * req.respvalidate.NodeValue(keyValuePairStatusNodes().get(1), true),
	 * errorMessage); AssertJUnit.assertEquals("Status type dosn't match",
	 * req.respvalidate.NodeValue(keyValuePairStatusNodes().get(2), true),
	 * status); AssertJUnit.assertEquals("Total count dosn't match",
	 * req.respvalidate.NodeValue(keyValuePairStatusNodes().get(3), true),
	 * totalCount); log.info(req.respvalidate.returnresponseasstring()); }
	 *//**
	 * Delete key value pair.
	 * 
	 * @author jhansi.bai
	 */
	/*
	 * @Test(groups = { "Regression", "MiniRegression", "ExhaustiveRegression",
	 * "RFPFOX7"}, dataProvider = "deleteKeyValuePairDP_negative") public void
	 * KeyValuePairService_deleateKeyValuePair_negative(String key, String
	 * errorCode, String errorMessage, String status, String totalCount) {
	 * RequestGenerator req = getQueryRequest(APINAME.DELETEFEATUREKEYVALUEPAIR,
	 * key); //
	 * System.out.println(reqGen.respvalidate.returnresponseasstring());
	 * AssertJUnit.assertEquals("Inval deleateKeyValuePair API. ", 200,
	 * req.response.getStatus()); }
	 *//**
	 * Delete key value pair.
	 * 
	 * @author jhansi.bai
	 */
	/*
	 * @Test(groups = {"Smoke", "Sanity", "ProdSanity", "Regression",
	 * "MiniRegression", "ExhaustiveRegression", "RFPFOX7" }, dataProvider =
	 * "deleteKeyValuePairDP_negative") public void
	 * KeyValuePairService_deleateKeyValuePair_vNegativeStatusAdDataNodes(String
	 * key, String errorCode, String errorMessage, String status, String
	 * totalCount) {
	 * 
	 * RequestGenerator req = getQueryRequest(APINAME.DELETEFEATUREKEYVALUEPAIR,
	 * key); System.out.println(req.respvalidate.returnresponseasstring());
	 * AssertJUnit.assertTrue("Inval deleateKeyValuePair API status nodes.",
	 * req.respvalidate.DoesNodesExists(keyValuePairStatusNodes())); }
	 *//**
	 * Delete key value pair.
	 * 
	 * @author jhansi.bai
	 */
	/*
	 * @Test(groups = {"Sanity","MiniRegression", "ExhaustiveRegression",
	 * "Regression", "RFPFOX7"}, dataProvider = "deleteKeyValuePairDP_negative")
	 * public void
	 * KeyValuePairService_deleateKeyValuePair_vNegativeSuccStatusMsg(String
	 * key, String errorCode, String errorMessage, String status, String
	 * totalCount ) { RequestGenerator req =
	 * getQueryRequest(APINAME.DELETEFEATUREKEYVALUEPAIR, key);
	 * AssertJUnit.assertEquals("Status code does't match",
	 * req.respvalidate.NodeValue(keyValuePairStatusNodes().get(0), true),
	 * errorCode); AssertJUnit.assertEquals("Status message dosn't match",
	 * req.respvalidate.NodeValue(keyValuePairStatusNodes().get(1), true),
	 * errorMessage); AssertJUnit.assertEquals("Status type dosn't match",
	 * req.respvalidate.NodeValue(keyValuePairStatusNodes().get(2), true),
	 * status); AssertJUnit.assertEquals("Total count dosn't match",
	 * req.respvalidate.NodeValue(keyValuePairStatusNodes().get(3), true),
	 * totalCount); log.info(req.respvalidate.returnresponseasstring()); }
	 *//**
	 * Delete key value pair.
	 * 
	 * @author jhansi.bai
	 */
	/*
	 * @Test(groups = {"Sanity","MiniRegression", "ExhaustiveRegression",
	 * "Regression", "RFPFOX7"}, dataProvider =
	 * "deleteKeyValuePairDP_negative1") public void
	 * KeyValuePairService_deleateKeyValuePair_vNegativeSuccStatusMsg1(String
	 * key, String errorCode, String errorMessage, String status, String
	 * totalCount ) { RequestGenerator req =
	 * getQueryRequest(APINAME.DELETEFEATUREKEYVALUEPAIR, key);
	 * AssertJUnit.assertEquals("Status code does't match",
	 * req.respvalidate.NodeValue(keyValuePairStatusNodes().get(0), true),
	 * errorCode); AssertJUnit.assertEquals("Status message dosn't match",
	 * req.respvalidate.NodeValue(keyValuePairStatusNodes().get(1), true),
	 * errorMessage); AssertJUnit.assertEquals("Status type dosn't match",
	 * req.respvalidate.NodeValue(keyValuePairStatusNodes().get(2), true),
	 * status); AssertJUnit.assertEquals("Total count dosn't match",
	 * req.respvalidate.NodeValue(keyValuePairStatusNodes().get(3), true),
	 * totalCount); log.info(req.respvalidate.returnresponseasstring()); }
	 */

	// --------------------------------------------widget------------------------------------------------------

	/**
	 * Get a widget key value pair.
	 * 
	 * @author jhansi.bai
	 * @param urlparams
	 */
	@Test(groups = { "Smoke", "Sanity", "Regression", "MiniRegression",
			"ExhaustiveRegression", "ExhaustiveRegression", "RFPFOX7" }, dataProvider = "getWidgetKeyValuePairDP",
			description="This test is for WidgetKeyValue Pairs. \n1.Hit WidgetGetKeyValue API. \n2.Verify API response code is 200.")
	public void KeyValuePairService_widget_getKeyValue(String key) {
		RequestGenerator req = getQueryRequest(APINAME.WIDGETGETKEYVALUE, key);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(
				"Get getwidgetKeyValue service is not working", 200,
				req.response.getStatus());
	}

	/**
	 * Get a widget key value pair.
	 * 
	 * @author jhansi.bai
	 */
	@Test(groups = { "Regression", "MiniRegression", "ExhaustiveRegression",
			"ExhaustiveRegression", "RFPFOX7" }, dataProvider = "getWidgetKeyValuePairDP", description="This test is for WidgetKeyValue Pairs."
					+ "\n1.Hit WidgetGetKeyValue API. \n2.Verify StatusNodes & DataNodes exists in APIs response.")
	public void KeyValuePairService_widget_getKeyValue_vStatusAdDataNodes(
			String key) {

		RequestGenerator req = getQueryRequest(APINAME.WIDGETGETKEYVALUE, key);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertTrue("Inval WIDGETGETKEYVALUE API status nodes.",
				req.respvalidate.DoesNodesExists(keyValuePairStatusNodes()));
		AssertJUnit.assertTrue("Inval WIDGETGETKEYVALUE API status nodes.",
				req.respvalidate.DoesNodesExists(keyValuePairDataNodes()));
	}

	/**
	 * Get a widget key value pair.
	 * 
	 * @author jhansi.bai
	 */
	@Test(groups = { "MiniRegression", "ExhaustiveRegression", "Regression",
			"RFPFOX7" }, dataProvider = "getWidgetKeyValuePairForGivenListDP1", description="This test is for WidgetKeyValue Pairs."
					+ "\n1.Hit WidgetGetKeyValue API. \n2.Verify StatusNodes & DataNodes values are of Success in APIs response.")
	public void KeyValuePairService_widget_getKeyValue_vSuccStatusMsg(
			String key, String value, String description) {
		RequestGenerator req = getQueryRequest(APINAME.WIDGETGETKEYVALUE, key);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("Status code does't match", req.respvalidate
				.NodeValue(keyValuePairStatusNodes().get(0), true), "15002");
		AssertJUnit.assertEquals("Status message dosn't match",
				req.respvalidate.NodeValue(keyValuePairStatusNodes().get(1),
						true), "\"KeyValuePair(s) retrieved successfully\"");
		AssertJUnit.assertEquals("Status type dosn't match", req.respvalidate
				.NodeValue(keyValuePairStatusNodes().get(2), true),
				"\"SUCCESS\"");
		AssertJUnit.assertEquals("Total count dosn't match", req.respvalidate
				.NodeValue(keyValuePairStatusNodes().get(3), true),
				req.respvalidate.GetArraySize("data.") + "");
		AssertJUnit.assertEquals("key does't match", req.respvalidate
				.NodeValue(keyValuePairDataNodes().get(0), true), "\"" + key
				+ "\"");
		AssertJUnit.assertEquals("value dosn't match", req.respvalidate
				.NodeValue(keyValuePairDataNodes().get(1), true), "\"" + value
				+ "\"");
		AssertJUnit.assertEquals("description dosn't match", req.respvalidate
				.NodeValue(keyValuePairDataNodes().get(2), true), "\""
				+ description + "\"");
		log.info(req.respvalidate.returnresponseasstring());
	}

	/**
	 * Get a widget key value pair.
	 * 
	 * @author jhansi.bai
	 * @param urlparams
	 */
	@Test(groups = { "Regression", "MiniRegression", "ExhaustiveRegression",
			"RFPFOX7" }, dataProvider = "getWidgetKeyValuePairDP_negative" , description="This test is for WidgetKeyValue Pairs."
					+ "\n1.Hit WidgetGetKeyValue API passing non existing Key. \n2.Verify API is not failing & response code is 200.")
	public void KeyValuePairService_widget_getKeyValue_negative(String key) {
		RequestGenerator req = getQueryRequest(APINAME.WIDGETGETKEYVALUE, key);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(
				"Get WIDGETGETKEYVALUE service is not working", 200,
				req.response.getStatus());
	}

	/**
	 * Get a widget key value pair.
	 * 
	 * @author jhansi.bai
	 */
	@Test(groups = { "Regression", "MiniRegression", "ExhaustiveRegression",
			"RFPFOX7" }, dataProvider = "getWidgetKeyValuePairDP_negative" , description="This test is for WidgetKeyValue Pairs."
					+ "\n1.Hit WidgetGetKeyValue API passing non existing Key."
					+ " \n2.Verify API is not failing & correct StatusNodes exists in APIs response.")
	public void KeyValuePairService_widget_getKeyValue_vNegativeStatusNodes(
			String key) {

		RequestGenerator req = getQueryRequest(APINAME.WIDGETGETKEYVALUE, key);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertTrue("Inval WIDGETGETKEYVALUE API status nodes.",
				req.respvalidate.DoesNodesExists(keyValuePairStatusNodes()));
	}

	/**
	 * Get a widget key value pair.
	 * 
	 * @author jhansi.bai
	 */
	@Test(groups = { "MiniRegression", "ExhaustiveRegression", "Regression",
			"RFPFOX7" }, dataProvider = "getWidgetKeyValuePairDP_negative", description="This test is for WidgetKeyValue Pairs."
					+ "\n1.Hit WidgetGetKeyValue API passing non existing Key."
					+ " \n2.Verify StatusNodes data in APIs response are as expected.")
	public void KeyValuePairService_widget_getKeyValue_vNegagtiveSuccStatusMsg(
			String key) {
		RequestGenerator req = getQueryRequest(APINAME.WIDGETGETKEYVALUE, key);
		AssertJUnit.assertEquals("Status code does't match", req.respvalidate
				.NodeValue(keyValuePairStatusNodes().get(0), true), "10008");
		AssertJUnit.assertEquals("Status message dosn't match",
				req.respvalidate.NodeValue(keyValuePairStatusNodes().get(1),
						true), "\"Key not found\"");
		AssertJUnit
				.assertEquals("Status type dosn't match", req.respvalidate
						.NodeValue(keyValuePairStatusNodes().get(2), true),
						"\"ERROR\"");
		AssertJUnit.assertEquals("Total count dosn't match", req.respvalidate
				.NodeValue(keyValuePairStatusNodes().get(3), true), "0");
		log.info(req.respvalidate.returnresponseasstring());
	}

	/**
	 * Get all widget key value pairs.
	 * 
	 * @author jhansi.bai
	 */
	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
			"Regression", "RFPFOX7" }, description="This test is for WidgetKeyValue Pairs."
					+ "\n1.Hit GetAllWidgetKeyValuePair API."
					+ " \n2.Verify API is Up & response code is 200.")
	public void KeyValuePairService_getAllWidgetKeyValuePair() {
		RequestGenerator req = getRequest(APINAME.GETALLWIDGETKEYVALUEPAIR);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("getAllKeyValuePair is not working", 200,
				req.response.getStatus());
	}

	/**
	 * Get all widget key value pairs.
	 * 
	 * @author jhansi.bai
	 */
	@Test(groups = { "MiniRegression", "ExhaustiveRegression", "Regression",
			"RFPFOX7" }, description="This test is for WidgetKeyValue Pairs."
					+ "\n1.Hit GetAllWidgetKeyValuePair API."
					+ " \n2.Verify correct StatusNodes exists in APIs response.")
	public void KeyValuePairService_getAllWidgetKeyValuePair_vStatusNodes() {

		RequestGenerator req = getRequest(APINAME.GETALLWIDGETKEYVALUEPAIR);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertTrue(
				"Inval getAllWidgetKeyValuePair API status nodes.",
				req.respvalidate.DoesNodesExists(keyValuePairStatusNodes()));
	}

	/**
	 * Verify all widget key value pairs
	 * 
	 * @author jhansi.bai
	 */
	@Test(groups = { "MiniRegression", "ExhaustiveRegression", "Regression",
			"RFPFOX7" }, description="This test is for WidgetKeyValue Pairs."
					+ "\n1.Hit GetAllWidgetKeyValuePair API."
					+ " \n2.Verify StatusNodes data in APIs response are of success.")
	public void PageConfigurator_getAllWidgetKeyValuePair_vSuccStatusMsg() {
		RequestGenerator req = getRequest(APINAME.GETALLWIDGETKEYVALUEPAIR);
		AssertJUnit.assertEquals("Status code does't match", req.respvalidate
				.NodeValue(keyValuePairStatusNodes().get(0), true), "15002");
		AssertJUnit.assertEquals("Status message dosn't match",
				req.respvalidate.NodeValue(keyValuePairStatusNodes().get(1),
						true), "\"KeyValuePair(s) retrieved successfully\"");
		AssertJUnit.assertEquals("Status type dosn't match", req.respvalidate
				.NodeValue(keyValuePairStatusNodes().get(2), true),
				"\"SUCCESS\"");
		AssertJUnit.assertEquals("Total count dosn't match", req.respvalidate
				.NodeValue(keyValuePairStatusNodes().get(3), true),
				req.respvalidate.GetArraySize("data") + "");
		log.info(req.respvalidate.returnresponseasstring());
	}

	/**
	 * Get a widget key value pairs for a given list.
	 * 
	 * @author jhansi.bai
	 */
	@Test(groups = { "Sanity", "Regression", "MiniRegression",
			"ExhaustiveRegression", "ProdSanity", "RFPFOX7", "RFPPROD" }, dataProvider = "getWidgetKeyValuePairForGivenListDP",
			description="This test is for WidgetKeyValue Pairs."
					+ "\n1.Hit GetWidgetKeyValueForGivenList API passing correct set of Keys."
					+ " \n2.Verify API is Up & response code is 200.")
	public void KeyValuePairService_getWidgetKeyValuePairForGivenList(
			String keys) {
		System.out.println(keys);
		RequestGenerator req = getHeaderRequest(
				APINAME.GETWIDGETKEYVALUEFORGIVENLIST, keys);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(
				"getWidgetKeyValuePairForGivenList is not working", 200,
				req.response.getStatus());
	}

	/**
	 * Get a widget key value pair.
	 * 
	 * @author jhansi.bai
	 */
	@Test(groups = { "ProdSanity", "Regression", "MiniRegression",
			"ExhaustiveRegression", "RFPFOX7", "RFPPROD" }, dataProvider = "getWidgetKeyValuePairForGivenListDP",
			description="This test is for WidgetKeyValue Pairs."
					+ "\n1.Hit GetWidgetKeyValueForGivenList API passing correct set of Keys."
					+ " \n2.Verify correct StatusNodes exists in APIs response.")
	public void KeyValuePairService_getWidgetKeyValuePairForGivenList_vStatusNodes(
			String keys) {

		RequestGenerator req = getHeaderRequest(
				APINAME.GETWIDGETKEYVALUEFORGIVENLIST, keys);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertTrue(
				"Inval getWidgetKeyValuePairForGivenList API status nodes.",
				req.respvalidate.DoesNodesExists(keyValuePairStatusNodes()));
	}

	/**
	 * Get a widget key value pair.
	 * 
	 * @author jhansi.bai
	 */
	@Test(groups = { "MiniRegression", "ExhaustiveRegression", "Regression",
			"RFPFOX7", "RFPPROD" }, dataProvider = "getWidgetKeyValuePairForGivenListDP",
			description="This test is for WidgetKeyValue Pairs."
					+ "\n1.Hit GetWidgetKeyValueForGivenList API passing correct set of Keys."
					+ " \n2.Verify StatusNodes data is of Success in APIs response.")
	public void KeyValuePairService_getWidgetKeyValuePairForGivenList_vSuccStatusMsg(
			String keys) {
		RequestGenerator req = getHeaderRequest(
				APINAME.GETWIDGETKEYVALUEFORGIVENLIST, keys);
		AssertJUnit.assertEquals("Status code does't match", req.respvalidate
				.NodeValue(keyValuePairStatusNodes().get(0), true), "15002");
		AssertJUnit.assertEquals("Status message dosn't match",
				req.respvalidate.NodeValue(keyValuePairStatusNodes().get(1),
						true), "\"KeyValuePair(s) retrieved successfully\"");
		AssertJUnit.assertEquals("Status type dosn't match", req.respvalidate
				.NodeValue(keyValuePairStatusNodes().get(2), true),
				"\"SUCCESS\"");
		AssertJUnit.assertEquals("Total count dosn't match", req.respvalidate
				.NodeValue(keyValuePairStatusNodes().get(3), true),
				req.respvalidate.GetArraySize("data") + "");
		log.info(req.respvalidate.returnresponseasstring());
	}

	/**
	 * Get a widget key value pairs for a given list.
	 * 
	 * @author jhansi.bai
	 */
	@Test(groups = { "Sanity", "ProdSanity", "Regression", "MiniRegression",
			"ExhaustiveRegression", "RFPFOX7" }, dataProvider = "getWidgetKeyValuePairForGivenListDP_negative",
			description="This test is for WidgetKeyValue Pairs."
					+ "\n1.Hit GetWidgetKeyValueForGivenList API passing non-existing set of Keys."
					+ " \n2.Verify API is not failing & response code is 200.")
	public void KeyValuePairService_getWidgetKeyValuePairForGivenList_negative200Status(
			String keys) {
		RequestGenerator req = getHeaderRequest(
				APINAME.GETWIDGETKEYVALUEFORGIVENLIST, keys);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(
				"getWidgetKeyValuePairForGivenList is not working", 200,
				req.response.getStatus());
	}

	/**
	 * Get a widget key value pair.
	 * 
	 * @author jhansi.bai
	 */
	@Test(groups = { "Regression", "MiniRegression", "ExhaustiveRegression",
			"RFPFOX7" }, dataProvider = "getWidgetKeyValuePairForGivenListDP_negative",
			description="This test is for WidgetKeyValue Pairs."
					+ "\n1.Hit GetWidgetKeyValueForGivenList API passing non-existing set of Keys."
					+ " \n2.Verify correct StatusNodes exists in APIs response.")
	public void KeyValuePairService_getWidgetKeyValuePairForGivenList_vNegativeStatusNodes(
			String keys) {

		RequestGenerator req = getHeaderRequest(
				APINAME.GETWIDGETKEYVALUEFORGIVENLIST, keys);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertTrue(
				"Inval getWidgetKeyValuePairForGivenList API status nodes.",
				req.respvalidate.DoesNodesExists(keyValuePairStatusNodes()));
	}

	/**
	 * Get a widget key value pair.
	 * 
	 * @author jhansi.bai
	 */
	@Test(groups = { "MiniRegression", "ExhaustiveRegression", "Regression",
			"RFPFOX7" }, dataProvider = "getWidgetKeyValuePairForGivenListDP_negative",
			description="This test is for WidgetKeyValue Pairs."
					+ "\n1.Hit GetWidgetKeyValueForGivenList API passing non-existing set of Keys."
					+ " \n2.Verify StatusNodes data in APIs response.")
	public void KeyValuePairService_getWidgetKeyValuePairForGivenList_vNegativeSuccStatusMsg(
			String keys) {
		RequestGenerator req = getHeaderRequest(
				APINAME.GETWIDGETKEYVALUEFORGIVENLIST, keys);
		AssertJUnit.assertEquals("Status code does't match", req.respvalidate
				.NodeValue(keyValuePairStatusNodes().get(0), true), "15002");
		AssertJUnit.assertEquals("Status message dosn't match",
				req.respvalidate.NodeValue(keyValuePairStatusNodes().get(1),
						true), "\"KeyValuePair(s) retrieved successfully\"");
		AssertJUnit.assertEquals("Status type dosn't match", req.respvalidate
				.NodeValue(keyValuePairStatusNodes().get(2), true),
				"\"SUCCESS\"");
		log.info(req.respvalidate.returnresponseasstring());
	}

	/**
	 * Get a widget key value pairs for a given list.
	 * 
	 * @author jhansi.bai
	 */
	@Test(groups = { "Sanity", "Regression", "MiniRegression",
			"ExhaustiveRegression", "RFPFOX7" }, dataProvider = "getWidgetKeyValuePairForGivenListDP_negative1",
			description="This test is for WidgetKeyValue Pairs."
					+ "\n1.Hit GetWidgetKeyValueForGivenList API passing null as Key."
					+ " \n2.Verify APIs response code is 500.")
	public void KeyValuePairService_getWidgetKeyValuePairForGivenList_negative500Status(
			String keys) {
		RequestGenerator req = getHeaderRequest(
				APINAME.GETWIDGETKEYVALUEFORGIVENLIST, keys);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(
				"getWidgetKeyValuePairForGivenList is not working", 500,
				req.response.getStatus());
	}

	// ----------------------Until unless delete api is used, this is not
	// suppose to execute in regular basis--------------
	/*
		*//**
	 * Create a new widget key value pair.
	 * 
	 * @author jhansi.bai
	 */
	/*
	 * @Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
	 * "Regression", "RFPFOX7" }, dataProvider =
	 * "createNewWidgetKeyValuePairDP") public void
	 * KeyValuePairService_createNewWidgetKeyValuePair(String key, String value,
	 * String desc, String SuccessCode, String successMessage, String status,
	 * String totalCount) { RequestGenerator req =
	 * getRequest(APINAME.CREATENEWWIDGETKEYVALUEPAIR, key, value, desc);
	 * System.out.println(req.respvalidate.returnresponseasstring());
	 * AssertJUnit.assertEquals("createNewWidgetKeyValuePair is not working",
	 * 200, req.response.getStatus()); }
	 */

	/**
	 * Create a new widget key value pair.
	 * 
	 * @author jhansi.bai
	 */
	/*
	 * @Test(groups = {"MiniRegression", "ExhaustiveRegression", "Regression",
	 * "RFPFOX7" }, dataProvider = "createNewWidgetKeyValuePairDP") public void
	 * KeyValuePairService_createNewWidgetKeyValuePair_vStatusAdDataNodes(String
	 * key, String value, String desc, String SuccessCode, String
	 * successMessage, String status, String totalCount) {
	 * 
	 * RequestGenerator req = getRequest(APINAME.CREATENEWWIDGETKEYVALUEPAIR,
	 * key, value, desc);
	 * System.out.println(req.respvalidate.returnresponseasstring());
	 * AssertJUnit
	 * .assertTrue("Inval createNewWidgetKeyValuePair API status nodes.",
	 * req.respvalidate.DoesNodesExists(keyValuePairStatusNodes()));
	 * AssertJUnit.
	 * assertTrue("Inval createNewWidgetKeyValuePair API status nodes.",
	 * req.respvalidate.DoesNodesExists(keyValuePairDataNodes())); }
	 *//**
	 * Create a new widget key value pair.
	 * 
	 * @author jhansi.bai
	 */
	/*
	 * @Test(groups = {"MiniRegression", "ExhaustiveRegression", "Regression",
	 * "RFPFOX7"}, dataProvider = "createNewWidgetKeyValuePairDP") public void
	 * KeyValuePairService_createNewWidgetKeyValuePair_vSuccStatusMsg(String
	 * key, String value, String desc, String SuccessCode, String
	 * successMessage, String status, String totalCount) { RequestGenerator req
	 * = getRequest(APINAME.CREATENEWWIDGETKEYVALUEPAIR, key, value, desc);
	 * AssertJUnit.assertEquals("Status code does't match",
	 * req.respvalidate.NodeValue(keyValuePairStatusNodes().get(0), true),
	 * SuccessCode); AssertJUnit.assertEquals("Status message dosn't match",
	 * req.respvalidate.NodeValue(keyValuePairStatusNodes().get(1), true),
	 * successMessage); AssertJUnit.assertEquals("Status type dosn't match",
	 * req.respvalidate.NodeValue(keyValuePairStatusNodes().get(2), true),
	 * status); AssertJUnit.assertEquals("Total count dosn't match",
	 * req.respvalidate.NodeValue(keyValuePairStatusNodes().get(3), true),
	 * totalCount); AssertJUnit.assertEquals("Status code does't match",
	 * req.respvalidate.NodeValue(keyValuePairDataNodes().get(0), true),
	 * "\""+key+"\""); AssertJUnit.assertEquals("Status message dosn't match",
	 * req.respvalidate.NodeValue(keyValuePairDataNodes().get(1), true),
	 * "\""+value+"\""); AssertJUnit.assertEquals("Status type dosn't match",
	 * req.respvalidate.NodeValue(keyValuePairDataNodes().get(2), true),
	 * "\""+desc+"\""); log.info(req.respvalidate.returnresponseasstring()); }
	 *//**
	 * Create a new widget key value pair.
	 * 
	 * @author jhansi.bai
	 */
	/*
	 * @Test(groups = { "Sanity", "Regression", "MiniRegression",
	 * "ExhaustiveRegression", "RFPFOX7"}, dataProvider =
	 * "createNewWidgetKeyValuePairDP_negative") public void
	 * KeyValuePairService_createNewWidgetKeyValuePair_negative(String key,
	 * String value, String desc, String errorCode, String errorMessage, String
	 * status, String totalCount) { RequestGenerator req =
	 * getRequest(APINAME.CREATENEWWIDGETKEYVALUEPAIR, key, value, desc); //
	 * System.out.println(reqGen.respvalidate.returnresponseasstring());
	 * AssertJUnit.assertEquals("createNewWidgetKeyValuePair is not working",
	 * 200, req.response.getStatus()); }
	 *//**
	 * Create a new widget key value pair.
	 * 
	 * @author jhansi.bai
	 */
	/*
	 * @Test(groups = {"Regression", "MiniRegression", "ExhaustiveRegression",
	 * "RFPFOX7" }, dataProvider = "createNewWidgetKeyValuePairDP_negative")
	 * public void
	 * KeyValuePairService_createNewWidgetKeyValuePair_vNegativeStatusAdDataNodes
	 * (String key, String value, String desc, String errorCode, String
	 * errorMessage, String status, String totalCount) {
	 * 
	 * RequestGenerator req = getRequest(APINAME.CREATENEWWIDGETKEYVALUEPAIR,
	 * key, value, desc);
	 * System.out.println(req.respvalidate.returnresponseasstring());
	 * AssertJUnit
	 * .assertTrue("Inval createNewWidgetKeyValuePair API status nodes.",
	 * req.respvalidate.DoesNodesExists(keyValuePairStatusNodes())); }
	 *//**
	 * Create a new widget key value pair.
	 * 
	 * @author jhansi.bai
	 */
	/*
	 * @Test(groups = {"MiniRegression", "ExhaustiveRegression", "Regression",
	 * "RFPFOX7"}, dataProvider = "createNewWidgetKeyValuePairDP_negative")
	 * public void
	 * KeyValuePairService_createNewWidgetKeyValuePair_vNegativeSuccStatusMsg
	 * (String key, String value, String desc, String errorCode, String
	 * errorMessage, String status, String totalCount ) { RequestGenerator req =
	 * getRequest(APINAME.CREATENEWWIDGETKEYVALUEPAIR, key, value, desc);
	 * AssertJUnit.assertEquals("Status code does't match",
	 * req.respvalidate.NodeValue(keyValuePairStatusNodes().get(0), true),
	 * errorCode); AssertJUnit.assertEquals("Status message dosn't match",
	 * req.respvalidate.NodeValue(keyValuePairStatusNodes().get(1), true),
	 * errorMessage); AssertJUnit.assertEquals("Status type dosn't match",
	 * req.respvalidate.NodeValue(keyValuePairStatusNodes().get(2), true),
	 * status); AssertJUnit.assertEquals("Total count dosn't match",
	 * req.respvalidate.NodeValue(keyValuePairStatusNodes().get(3), true),
	 * totalCount); log.info(req.respvalidate.returnresponseasstring()); }
	 *//**
	 * Create a new widget key value pair.
	 * 
	 * @author jhansi.bai
	 */
	/*
	 * @Test(groups = {"MiniRegression", "ExhaustiveRegression", "Regression",
	 * "RFPFOX7"}, dataProvider = "createNewWidgetKeyValuePairDP_negative1")
	 * public void
	 * KeyValuePairService_createNewWidgetKeyValuePair_vNegativeSuccStatusMsg1
	 * (String key, String value, String desc, String errorCode, String
	 * errorMessage, String status, String totalCount ) { RequestGenerator req =
	 * getRequest(APINAME.CREATENEWWIDGETKEYVALUEPAIR, key, value, desc);
	 * AssertJUnit.assertEquals("Status code does't match",
	 * req.respvalidate.NodeValue(keyValuePairStatusNodes().get(0), true),
	 * errorCode); AssertJUnit.assertEquals("Status message dosn't match",
	 * req.respvalidate.NodeValue(keyValuePairStatusNodes().get(1), true),
	 * errorMessage); AssertJUnit.assertEquals("Status type dosn't match",
	 * req.respvalidate.NodeValue(keyValuePairStatusNodes().get(2), true),
	 * status); AssertJUnit.assertEquals("Total count dosn't match",
	 * req.respvalidate.NodeValue(keyValuePairStatusNodes().get(3), true),
	 * totalCount); log.info(req.respvalidate.returnresponseasstring()); }
	 */

	/**
	 * update widget key value pair.
	 * 
	 * @author jhansi.bai
	 */
	@Test(groups = { "Sanity", "Regression", "MiniRegression",
			"ExhaustiveRegression", "RFPFOX7" }, dataProvider = "updateWidgetKeyValuePairDP",
			description="This test is for WidgetKeyValue Pairs."
					+ "\n1.Hit UpdateWidgetKeyValuePair API passing correct key-value set."
					+ " \n2.Verify API is up & response code is 200.")
	public void KeyValuePairService_updateWidgetKeyValuePair(String key,
			String value, String desc, String SuccessCode,
			String successMessage, String status, String totalCount) {
		RequestGenerator req = getRequest(APINAME.UPDATEWIDGETKEYVALUEPAIR,
				key, value, desc);
		// System.out.println(reqGen.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("updateWidgetKeyValuePair is not working",
				200, req.response.getStatus());
	}

	/**
	 * update widget key value pair.
	 * 
	 * @author jhansi.bai
	 */
	@Test(groups = { "Regression", "MiniRegression", "ExhaustiveRegression",
			"RFPFOX7" }, dataProvider = "updateWidgetKeyValuePairDP",
			description="This test is for WidgetKeyValue Pairs."
					+ "\n1.Hit UpdateWidgetKeyValuePair API passing correct key-value set."
					+ " \n2.Verify correct StatusNodes exists in APIs response.")
	public void KeyValuePairService_updateWidgetKeyValuePair_vStatusAdDataNodes(
			String key, String value, String desc, String SuccessCode,
			String successMessage, String status, String totalCount) {

		RequestGenerator req = getRequest(APINAME.UPDATEWIDGETKEYVALUEPAIR,
				key, value, desc);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertTrue(
				"Inval updateWidgetKeyValuePair API status nodes.",
				req.respvalidate.DoesNodesExists(keyValuePairStatusNodes()));
		AssertJUnit.assertTrue(
				"Inval updateWidgetKeyValuePair API status nodes.",
				req.respvalidate.DoesNodesExists(keyValuePairDataNodes()));
	}

	/**
	 * update widget key value pair.
	 * 
	 * @author jhansi.bai
	 */
	@Test(groups = { "MiniRegression", "ExhaustiveRegression", "Regression",
			"RFPFOX7" }, dataProvider = "updateWidgetKeyValuePairDP",description="This test is for WidgetKeyValue Pairs."
					+ "\n1.Hit UpdateWidgetKeyValuePair API passing correct key-value set."
					+ " \n2.Verify StatusNodes data is of Success in APIs response.")
	public void KeyValuePairService_updateWidgetKeyValuePair_vSuccStatusMsg(
			String key, String value, String desc, String SuccessCode,
			String successMessage, String status, String totalCount) {
		RequestGenerator req = getRequest(APINAME.UPDATEWIDGETKEYVALUEPAIR,
				key, value, desc);
		AssertJUnit
				.assertEquals("Status code does't match", req.respvalidate
						.NodeValue(keyValuePairStatusNodes().get(0), true),
						SuccessCode);
		AssertJUnit.assertEquals("Status message dosn't match",
				req.respvalidate.NodeValue(keyValuePairStatusNodes().get(1),
						true), successMessage);
		AssertJUnit.assertEquals("Status type dosn't match", req.respvalidate
				.NodeValue(keyValuePairStatusNodes().get(2), true), status);
		AssertJUnit.assertEquals("Total count dosn't match", req.respvalidate
				.NodeValue(keyValuePairStatusNodes().get(3), true), totalCount);
		AssertJUnit.assertEquals("Status code does't match", req.respvalidate
				.NodeValue(keyValuePairDataNodes().get(0), true), "\"" + key
				+ "\"");
		AssertJUnit.assertEquals("Status message dosn't match",
				req.respvalidate
						.NodeValue(keyValuePairDataNodes().get(1), true), "\""
						+ value + "\"");
		AssertJUnit.assertEquals("Status type dosn't match", req.respvalidate
				.NodeValue(keyValuePairDataNodes().get(2), true), "\"" + desc
				+ "\"");
		log.info(req.respvalidate.returnresponseasstring());
	}

	/**
	 * update widget key value pair.
	 * 
	 * @author jhansi.bai
	 */
	@Test(groups = { "Sanity", "Regression", "MiniRegression",
			"ExhaustiveRegression", "RFPFOX7" }, dataProvider = "updateWidgetKeyValuePairDP_negative",description="This test is for WidgetKeyValue Pairs."
					+ "\n1.Hit UpdateWidgetKeyValuePair API passing incorrect key-value set."
					+ " \n2.Verify API is not failing & response code is 200.")
	public void KeyValuePairService_updateWidgetKeyValuePair_negative(
			String key, String value, String desc, String errorCode,
			String errorMessage, String status, String totalCount) {
		RequestGenerator req = getRequest(APINAME.UPDATEWIDGETKEYVALUEPAIR,
				key, value, desc);
		// System.out.println(reqGen.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("updateWidgetKeyValuePair is not working",
				200, req.response.getStatus());
	}

	/**
	 * update widget key value pair.
	 * 
	 * @author jhansi.bai
	 */
	@Test(groups = { "Regression", "MiniRegression", "ExhaustiveRegression",
			"RFPFOX7" }, dataProvider = "updateWidgetKeyValuePairDP_negative",description="This test is for WidgetKeyValue Pairs."
					+ "\n1.Hit UpdateWidgetKeyValuePair API passing incorrect key-value set."
					+ " \n2.Verify correct StatusNodes exists in APIs response.")
	public void KeyValuePairService_updateWidgetKeyValuePair_vNegativeStatusNodes(
			String key, String value, String desc, String errorCode,
			String errorMessage, String status, String totalCount) {

		RequestGenerator req = getRequest(APINAME.UPDATEWIDGETKEYVALUEPAIR,
				key, value, desc);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertTrue(
				"Inval updateWidgetKeyValuePair API status nodes.",
				req.respvalidate.DoesNodesExists(keyValuePairStatusNodes()));
	}

	/**
	 * update widget key value pair.
	 * 
	 * @author jhansi.bai
	 */
	@Test(groups = { "MiniRegression", "ExhaustiveRegression", "Regression",
			"RFPFOX7" }, dataProvider = "updateWidgetKeyValuePairDP_negative",description="This test is for WidgetKeyValue Pairs."
					+ "\n1.Hit UpdateWidgetKeyValuePair API passing incorrect key-value set."
					+ " \n2.Verify StatusNodes values in APIs response are as expected.")
	public void KeyValuePairService_updateWidgetKeyValuePair_vNegativeSuccStatusMsg(
			String key, String value, String desc, String errorCode,
			String errorMessage, String status, String totalCount) {
		RequestGenerator req = getRequest(APINAME.UPDATEWIDGETKEYVALUEPAIR,
				key, value, desc);
		AssertJUnit.assertEquals("Status code does't match", req.respvalidate
				.NodeValue(keyValuePairStatusNodes().get(0), true), errorCode);
		AssertJUnit.assertEquals("Status message dosn't match",
				req.respvalidate.NodeValue(keyValuePairStatusNodes().get(1),
						true), errorMessage);
		AssertJUnit.assertEquals("Status type dosn't match", req.respvalidate
				.NodeValue(keyValuePairStatusNodes().get(2), true), status);
		AssertJUnit.assertEquals("Total count dosn't match", req.respvalidate
				.NodeValue(keyValuePairStatusNodes().get(3), true), totalCount);
		log.info(req.respvalidate.returnresponseasstring());
	}

	/**
	 * update widget key value pair for a given list.
	 * 
	 * @author jhansi.bai
	 */
	@Test(groups = { "Sanity", "Regression", "MiniRegression",
			"ExhaustiveRegression", "RFPFOX7" }, dataProvider = "updateWidgetKeyValuePairForGivenListDP",
			description="This test is for WidgetKeyValue Pairs."
					+ "\n1.Hit UpdateListOfKeyValuePair API passing correct key-value set."
					+ " \n2.Verify API is Up & response code is 200.")
	public void KeyValuePairService_updateWidgetKeyValuePairForGivenList(
			String key1, String value1, String desc1, String key2,
			String value2, String desc2, String SuccessCode,
			String successMessage, String status, String totalCount) {
		RequestGenerator req = getRequest(
				APINAME.UPDATELISTOFWIDGETKEYVALUEPAIR, key1, value1, desc1,
				key2, value2, desc2);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(
				"updateWidgetKeyValuePairForGivenList is not working", 200,
				req.response.getStatus());
	}

	/**
	 * update widget key value pair for a given list.
	 * 
	 * @author jhansi.bai
	 */
	@Test(groups = { "Regression", "MiniRegression", "ExhaustiveRegression",
			"RFPFOX7" }, dataProvider = "updateWidgetKeyValuePairForGivenListDP",description="This test is for WidgetKeyValue Pairs."
					+ "\n1.Hit UpdateListOfKeyValuePair API passing correct key-value set."
					+ " \n2.Verify correct StatusNodes & DataNodes exists in APIs response.")
	public void KeyValuePairService_updateWidgetKeyValuePairForGivenList_vStatusadDataNodes(
			String key1, String value1, String desc1, String key2,
			String value2, String desc2, String SuccessCode,
			String successMessage, String status, String totalCount) {
		RequestGenerator req = getRequest(
				APINAME.UPDATELISTOFWIDGETKEYVALUEPAIR, key1, value1, desc1,
				key2, value2, desc2);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertTrue(
				"Inval updateWidgetKeyValuePairForGivenList API status nodes.",
				req.respvalidate.DoesNodesExists(keyValuePairStatusNodes()));
		AssertJUnit.assertTrue(
				"Inval updateWidgetKeyValuePairForGivenList API status nodes.",
				req.respvalidate.DoesNodesExists(keyValuePairDataNodes()));
	}

	/**
	 * update widget key value pair for a given list.
	 * 
	 * @author jhansi.bai
	 */
	@Test(groups = { "Regression", "MiniRegression", "ExhaustiveRegression",
			"RFPFOX7" }, dataProvider = "updateWidgetKeyValuePairForGivenListDP",description="This test is for WidgetKeyValue Pairs."
					+ "\n1.Hit UpdateListOfKeyValuePair API passing correct key-value set."
					+ " \n2.Verify StatusNodes values are of Success in APIs response.")
	public void KeyValuePairService_updateWidgetKeyValuePairForGivenList_vSuccStatusMsg(
			String key1, String value1, String desc1, String key2,
			String value2, String desc2, String SuccessCode,
			String successMessage, String status, String totalCount) {
		RequestGenerator req = getRequest(
				APINAME.UPDATELISTOFWIDGETKEYVALUEPAIR, key1, value1, desc1,
				key2, value2, desc2);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit
				.assertEquals("Status code does't match", req.respvalidate
						.NodeValue(keyValuePairStatusNodes().get(0), true),
						SuccessCode);
		AssertJUnit.assertEquals("Status message dosn't match",
				req.respvalidate.NodeValue(keyValuePairStatusNodes().get(1),
						true), successMessage);
		AssertJUnit.assertEquals("Status type dosn't match", req.respvalidate
				.NodeValue(keyValuePairStatusNodes().get(2), true), status);
		log.info(req.respvalidate.returnresponseasstring());
	}

	/**
	 * update widget key value pair for a given list.
	 * 
	 * @author jhansi.bai
	 */
	@Test(groups = { "Sanity", "Regression", "MiniRegression",
			"ExhaustiveRegression", "RFPFOX7" }, dataProvider = "updateWidgetKeyValuePairForGivenListDP_negative",
			description="This test is for WidgetKeyValue Pairs."
					+ "\n1.Hit UpdateListOfKeyValuePair API passing incorrect key-value set."
					+ " \n2.Verify API is not failing & response code is 200.")
	public void KeyValuePairService_updateWidgetKeyValuePairForGivenList_negative(
			String key1, String value1, String desc1, String key2,
			String value2, String desc2, String errorCode, String errorMessage,
			String status, String totalCount) {
		RequestGenerator req = getRequest(
				APINAME.UPDATELISTOFWIDGETKEYVALUEPAIR, key1, value1, desc1,
				key2, value2, desc2);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(
				"updateWidgetKeyValuePairForGivenList is not working", 200,
				req.response.getStatus());
	}

	/**
	 * update widget key value pair for a given list.
	 * 
	 * @author jhansi.bai
	 */
	@Test(groups = { "Sanity", "Regression", "MiniRegression",
			"ExhaustiveRegression", "RFPFOX7" }, dataProvider = "updateWidgetKeyValuePairForGivenListDP_negative",
			description="This test is for WidgetKeyValue Pairs."
					+ "\n1.Hit UpdateListOfKeyValuePair API passing incorrect key-value set."
					+ " \n2.Verify correct StatusNodes exists in APIs response.")
	public void KeyValuePairService_updateWidgetKeyValuePairForGivenList_vNegativeStatusNodes(
			String key1, String value1, String desc1, String key2,
			String value2, String desc2, String errorCode, String errorMessage,
			String status, String totalCount) {
		RequestGenerator req = getRequest(
				APINAME.UPDATELISTOFWIDGETKEYVALUEPAIR, key1, value1, desc1,
				key2, value2, desc2);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertTrue(
				"Inval updateWidgetKeyValuePairForGivenList API status nodes.",
				req.respvalidate.DoesNodesExists(keyValuePairStatusNodes()));
	}

	/**
	 * update widget key value pair for a given list.
	 * 
	 * @author jhansi.bai
	 */
	@Test(groups = { "Sanity", "Regression", "MiniRegression",
			"ExhaustiveRegression", "RFPFOX7" }, dataProvider = "updateWidgetKeyValuePairForGivenListDP_negative",
			description="This test is for WidgetKeyValue Pairs."
					+ "\n1.Hit UpdateListOfKeyValuePair API passing incorrect key-value set."
					+ " \n2.Verify StatusNodes values are as expected in APIs response.")
	public void KeyValuePairService_updateWidgetKeyValuePairForGivenList_vNegativeSuccStatusMsg(
			String key1, String value1, String desc1, String key2,
			String value2, String desc2, String errorCode, String errorMessage,
			String status, String totalCount) {
		RequestGenerator req = getRequest(
				APINAME.UPDATELISTOFWIDGETKEYVALUEPAIR, key1, value1, desc1,
				key2, value2, desc2);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("Status code does't match", req.respvalidate
				.NodeValue(keyValuePairStatusNodes().get(0), true), errorCode);
		AssertJUnit.assertEquals("Status message dosn't match",
				req.respvalidate.NodeValue(keyValuePairStatusNodes().get(1),
						true), errorMessage);
		AssertJUnit.assertEquals("Status type dosn't match", req.respvalidate
				.NodeValue(keyValuePairStatusNodes().get(2), true), status);
		AssertJUnit.assertEquals("Total count dosn't match", req.respvalidate
				.NodeValue(keyValuePairStatusNodes().get(3), true), totalCount);
		log.info(req.respvalidate.returnresponseasstring());
	}

	@Test(groups = { "Production" }, dataProvider = "updateWidgetKeyValuePairOnProduction")
	public void KeyValuePairService_updateWidgetKeyValuePairOnProducton(
			String key, String value) {

		String description = getDescriptionForFeatureGateKey(key);
		String[] payloadparams = new String[] { key, value };

		String finalPayload = getPayloadAsString("updatefeaturekeyvaluepair",
				payloadparams, description);

		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_CONFIGURATION,
				APINAME.UPDATEFEATUREKEYVALUEPAIR, init.Configurations,
				finalPayload);
		RequestGenerator req = new RequestGenerator(service);
		System.out.println(service.URL);

		String statusMessage = req.respvalidate
				.NodeValue("status.statusMessage", true).replaceAll("\"", "")
				.trim();
		System.out.println("statusMessage : " + statusMessage);

		AssertJUnit.assertTrue("Status Message doesn't match",
				statusMessage.contains("KeyValuePair updated successfully"));

	}

	private String getDescriptionForFeatureGateKey(String key) {
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_CONFIGURATION, APINAME.GETKEYVALUE,
				init.Configurations, PayloadType.JSON, new String[] { key },
				PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		System.out.println(service.URL);

		String description = req.respvalidate
				.NodeValue("data.description", true).replaceAll("\"", "")
				.trim();
		System.out.println("description for " + key + "is :" + description);

		return description;
	}

	private String getDescriptionForWidgetKeyValue(String key) {
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_CONFIGURATION, APINAME.WIDGETGETKEYVALUE,
				init.Configurations, PayloadType.JSON, new String[] { key },
				PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		System.out.println(service.URL);

		String description = req.respvalidate
				.NodeValue("data.description", true).replaceAll("\"", "")
				.trim();
		System.out.println("description for " + key + "is :" + description);

		return description;
	}

	private String getPayloadAsString(String payloadName,
			String[] payloadparams, String toReplace) {
		String customPayloadDir = System.getProperty("user.dir")
				+ File.separator + "Data" + File.separator + "payloads"
				+ File.separator + "JSON";
		StringBuffer sb = new StringBuffer();
		String finalPayload = "";
		Scanner sc;
		try {
			sc = new Scanner(new File(customPayloadDir + File.separator
					+ payloadName));
			while (sc.hasNextLine())
				sb.append(sc.nextLine() + "\n");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		finalPayload = sb.toString();
		for (int i = 0; i < payloadparams.length; i++) {
			finalPayload = finalPayload.replace("${" + i + "}",
					payloadparams[i]);
		}
		finalPayload = finalPayload.replace("${2}", toReplace);
		// System.out.println(finalPayload);
		return finalPayload;
	}

	public static LinkedHashMap<String, String> getKeyValueMap(String pathOfFile) {
		LinkedHashMap<String, String> keyValueMap = new LinkedHashMap<String, String>();
		// String pathOfFile =
		// "/Users/mohit.jain/Desktop/keyvaluepairlist/keyvaluepair.csv";
		// String pathOfFile = "/root/Desktop/SalesOps/keyvaluepair.csv";
		BufferedReader fileReader = null;
		try {
			String line = "";
			fileReader = new BufferedReader(new FileReader(pathOfFile));

			while ((line = fileReader.readLine()) != null) {
				String[] tokens = line.split(",");
				keyValueMap.put(tokens[0], tokens[1]);
			}
			System.out.println("map= " + keyValueMap);
		}

		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				fileReader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return keyValueMap;
	}

	/*
	 * public static void main (String[] args) { LinkedHashMap<String,String>
	 * keyvalepairmap = getKeyValueMap(); Set<Entry<String,String>> entrySet =
	 * keyvalepairmap.entrySet(); for(Entry<String,String> entry : entrySet){
	 * System.out.println(entry.getKey()+":"+entry.getValue()); }
	 * 
	 * }
	 */

	/*
	 * public static String getFileAsString(String Path) throws Exception{ File
	 * f = new File(Path); if(f.exists()){ String line; StringBuffer sb = new
	 * StringBuffer(); BufferedReader bf = new BufferedReader(new
	 * FileReader(f)); while((line = bf.readLine())!=null){
	 * sb.append(line+"\n"); } return sb.toString(); }else{
	 * System.out.println("ERROR  : "+Path+" file not found"); return ""; }
	 * 
	 * }
	 */

	// Test case for SalesOps - Update Feature Gate Values- Before Sales Starts
	@Test(groups = { "BeforeSales" }, dataProvider = "KeyValuePairDP_UpdateFG_SalesActivityBeforeSales")
	public void KeyValuePairService_updateFeatureGateValuesOnProducton_BeforeSales(
			String key, String value) {
		/*
		 * System.out.println("....................Before Sales.............");
		 * String pathOfFile = "/root/Desktop/SalesOps/keyvaluepair.csv";
		 * LinkedHashMap<String,String> keyvalepairmap =
		 * getKeyValueMap(pathOfFile); Set<Entry<String,String>> entrySet =
		 * keyvalepairmap.entrySet(); for(Entry<String,String> entry :
		 * entrySet){ String key = entry.getKey(); System.out.println("Key = "+
		 * key); String value = entry.getValue(); System.out.println("value = "
		 * +value);
		 */
		String description = getDescriptionForFeatureGateKey(key);
		String[] payloadparams = new String[] { key, value };
		String finalPayload = getPayloadAsString("updatefeaturekeyvaluepair",
				payloadparams, description);

		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_CONFIGURATION,
				APINAME.UPDATEFEATUREKEYVALUEPAIR, init.Configurations,
				finalPayload);
		RequestGenerator req = new RequestGenerator(service);
		System.out.println(service.URL);

		String statusMessage = req.respvalidate
				.NodeValue("status.statusMessage", true).replaceAll("\"", "")
				.trim();
		System.out.println("statusMessage : " + statusMessage);

		AssertJUnit.assertTrue("Status Message doesn't match",
				statusMessage.contains("KeyValuePair updated successfully"));
	}

	/*
	 * String keyFromJenkins = System.getProperty("keyfromui"); if
	 * (null==keyFromJenkins) { keyFromJenkins = System.getenv("keyfromui"); }
	 * if (null==keyFromJenkins) { keyFromJenkins = "default-key"; } String
	 * valueFromJenkins = System.getProperty("valuefromui"); if
	 * (null==valueFromJenkins) { valueFromJenkins =
	 * System.getenv("valuefromui"); } if (null==valueFromJenkins) {
	 * valueFromJenkins = "default-value"; } System.out.println(
	 * "--------------------------------------------------------------------\n"
	 * ); System.out.println(System.getProperty("keyfromui"));
	 * System.out.println(System.getenv("keyfromui")); System.out.println(
	 * "--------------------------------------------------------------------\n"
	 * );
	 * 
	 * String description = getDescriptionForKey (keyFromJenkins); String[]
	 * payloadparams=new String[]{ keyFromJenkins, valueFromJenkins}; String
	 * finalPayload = getPayloadAsString("updatefeaturekeyvaluepair",
	 * payloadparams, description);
	 * 
	 * MyntraService service = Myntra.getService(
	 * ServiceType.PORTAL_CONFIGURATION, APINAME.UPDATEFEATUREKEYVALUEPAIR,
	 * init.Configurations, finalPayload); RequestGenerator req = new
	 * RequestGenerator(service); System.out.println(service.URL);
	 * 
	 * String statusMessage = req.respvalidate
	 * .NodeValue("status.statusMessage", true) .replaceAll("\"", "").trim();
	 * System.out.println("statusMessage : " + statusMessage);
	 * 
	 * AssertJUnit.assertTrue("Status Message doesn't match",
	 * statusMessage.contains("KeyValuePair updated successfully"));
	 */

	// }

	// Test case for SalesOps - Update Widget Key Value Pair - Before Sales
	// Starts
	@Test(groups = { "BeforeSales" }, dataProvider = "KeyValuePairDP_UpdateWidgetKeyValue_SalesActivityBeforeSales")
	public void KeyValuePairService_updateWidgetKeyValueOnProducton_BeforeSales(
			String key, String value) {
		/*
		 * System.out.println("....................Before Sales.............");
		 * String pathOfFile = "/root/Desktop/SalesOps/keyvaluepair.csv";
		 * LinkedHashMap<String,String> keyvalepairmap =
		 * getKeyValueMap(pathOfFile); Set<Entry<String,String>> entrySet =
		 * keyvalepairmap.entrySet(); for(Entry<String,String> entry :
		 * entrySet){ String key = entry.getKey(); System.out.println("Key = "+
		 * key); String value = entry.getValue(); System.out.println("value = "
		 * +value);
		 */
		String description = getDescriptionForWidgetKeyValue(key);
		System.out.println("Description for key - " + "key:" + description);
		String[] payloadparams = new String[] { key, value };
		String finalPayload = getPayloadAsString("updatewidgetkeyvaluepair",
				payloadparams, description);

		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_CONFIGURATION,
				APINAME.UPDATEWIDGETKEYVALUEPAIR, init.Configurations,
				finalPayload);
		RequestGenerator req = new RequestGenerator(service);
		System.out.println(service.URL);

		String statusMessage = req.respvalidate
				.NodeValue("status.statusMessage", true).replaceAll("\"", "")
				.trim();
		System.out.println("statusMessage : " + statusMessage);

		AssertJUnit.assertTrue("Status Message doesn't match",
				statusMessage.contains("KeyValuePair updated successfully"));
	}

	/*
	 * String keyFromJenkins = System.getProperty("keyfromui"); if
	 * (null==keyFromJenkins) { keyFromJenkins = System.getenv("keyfromui"); }
	 * if (null==keyFromJenkins) { keyFromJenkins = "default-key"; } String
	 * valueFromJenkins = System.getProperty("valuefromui"); if
	 * (null==valueFromJenkins) { valueFromJenkins =
	 * System.getenv("valuefromui"); } if (null==valueFromJenkins) {
	 * valueFromJenkins = "default-value"; } System.out.println(
	 * "--------------------------------------------------------------------\n"
	 * ); System.out.println(System.getProperty("keyfromui"));
	 * System.out.println(System.getenv("keyfromui")); System.out.println(
	 * "--------------------------------------------------------------------\n"
	 * );
	 * 
	 * String description = getDescriptionForKey (keyFromJenkins); String[]
	 * payloadparams=new String[]{ keyFromJenkins, valueFromJenkins}; String
	 * finalPayload = getPayloadAsString("updatefeaturekeyvaluepair",
	 * payloadparams, description);
	 * 
	 * MyntraService service = Myntra.getService(
	 * ServiceType.PORTAL_CONFIGURATION, APINAME.UPDATEFEATUREKEYVALUEPAIR,
	 * init.Configurations, finalPayload); RequestGenerator req = new
	 * RequestGenerator(service); System.out.println(service.URL);
	 * 
	 * String statusMessage = req.respvalidate
	 * .NodeValue("status.statusMessage", true) .replaceAll("\"", "").trim();
	 * System.out.println("statusMessage : " + statusMessage);
	 * 
	 * AssertJUnit.assertTrue("Status Message doesn't match",
	 * statusMessage.contains("KeyValuePair updated successfully"));
	 */

	// }

	// Test case for SalesOps - Update Feature Gate Values- After Sales Ends
	@Test(groups = { "AfterSales" }, dataProvider = "KeyValuePairDP_UpdateFG_SalesActivityAfterSales")
	public void KeyValuePairService_updateFeatureGateValuesOnProducton_AfterSales(
			String key, String value) {
		String description = getDescriptionForFeatureGateKey(key);
		String[] payloadparams = new String[] { key, value };
		String finalPayload = getPayloadAsString("updatefeaturekeyvaluepair",
				payloadparams, description);

		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_CONFIGURATION,
				APINAME.UPDATEFEATUREKEYVALUEPAIR, init.Configurations,
				finalPayload);
		RequestGenerator req = new RequestGenerator(service);
		System.out.println(service.URL);

		String statusMessage = req.respvalidate
				.NodeValue("status.statusMessage", true).replaceAll("\"", "")
				.trim();
		System.out.println("statusMessage : " + statusMessage);

		AssertJUnit.assertTrue("Status Message doesn't match",
				statusMessage.contains("KeyValuePair updated successfully"));
	}

	// Test case for SalesOps - Update Widget Key Value Pair - After Sales
	// Starts
	@Test(groups = { "AfterSales" }, dataProvider = "KeyValuePairDP_UpdateWidgetKeyValue_SalesActivityAfterSales")
	public void KeyValuePairService_updateWidgetKeyValueOnProducton_AfterSales(
			String key, String value) {
		/*
		 * System.out.println("....................Before Sales.............");
		 * String pathOfFile = "/root/Desktop/SalesOps/keyvaluepair.csv";
		 * LinkedHashMap<String,String> keyvalepairmap =
		 * getKeyValueMap(pathOfFile); Set<Entry<String,String>> entrySet =
		 * keyvalepairmap.entrySet(); for(Entry<String,String> entry :
		 * entrySet){ String key = entry.getKey(); System.out.println("Key = "+
		 * key); String value = entry.getValue(); System.out.println("value = "
		 * +value);
		 */
		String description = getDescriptionForWidgetKeyValue(key);
		System.out.println("Description for key - " + "key:" + description);
		String[] payloadparams = new String[] { key, value };
		String finalPayload = getPayloadAsString("updatewidgetkeyvaluepair",
				payloadparams, description);

		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_CONFIGURATION,
				APINAME.UPDATEWIDGETKEYVALUEPAIR, init.Configurations,
				finalPayload);
		RequestGenerator req = new RequestGenerator(service);
		System.out.println(service.URL);

		String statusMessage = req.respvalidate
				.NodeValue("status.statusMessage", true).replaceAll("\"", "")
				.trim();
		System.out.println("statusMessage : " + statusMessage);

		AssertJUnit.assertTrue("Status Message doesn't match",
				statusMessage.contains("KeyValuePair updated successfully"));
	}

	/*
	 * String keyFromJenkins = System.getProperty("keyfromui"); if
	 * (null==keyFromJenkins) { keyFromJenkins = System.getenv("keyfromui"); }
	 * if (null==keyFromJenkins) { keyFromJenkins = "default-key"; } String
	 * valueFromJenkins = System.getProperty("valuefromui"); if
	 * (null==valueFromJenkins) { valueFromJenkins =
	 * System.getenv("valuefromui"); } if (null==valueFromJenkins) {
	 * valueFromJenkins = "default-value"; } System.out.println(
	 * "--------------------------------------------------------------------\n"
	 * ); System.out.println(System.getProperty("keyfromui"));
	 * System.out.println(System.getenv("keyfromui")); System.out.println(
	 * "--------------------------------------------------------------------\n"
	 * );
	 * 
	 * String description = getDescriptionForKey (keyFromJenkins); String[]
	 * payloadparams=new String[]{ keyFromJenkins, valueFromJenkins}; String
	 * finalPayload = getPayloadAsString("updatefeaturekeyvaluepair",
	 * payloadparams, description);
	 * 
	 * MyntraService service = Myntra.getService(
	 * ServiceType.PORTAL_CONFIGURATION, APINAME.UPDATEFEATUREKEYVALUEPAIR,
	 * init.Configurations, finalPayload); RequestGenerator req = new
	 * RequestGenerator(service); System.out.println(service.URL);
	 * 
	 * String statusMessage = req.respvalidate
	 * .NodeValue("status.statusMessage", true) .replaceAll("\"", "").trim();
	 * System.out.println("statusMessage : " + statusMessage);
	 * 
	 * AssertJUnit.assertTrue("Status Message doesn't match",
	 * statusMessage.contains("KeyValuePair updated successfully"));
	 */

	// }

	@Test(groups = { "SchemaValidation" }, dataProvider = "KeyValuePairDP_getKeyValue_verifyResponseDataNodesUsingSchemaValidations")
	public void KeyValuePairService_getKeyValue_verifyResponseDataNodesUsingSchemaValidations(
			String key) {
		RequestGenerator getKeyValueReqGen = getQueryRequest(
				APINAME.GETKEYVALUE, key);
		String getKeyValueResponse = getKeyValueReqGen.respvalidate
				.returnresponseasstring();
		System.out
				.println("\nPrinting KeyValuePairService getKeyValue API response :\n\n"
						+ getKeyValueResponse);
		log.info("\nPrinting KeyValuePairService getKeyValue API response :\n\n"
				+ getKeyValueResponse);

		AssertJUnit.assertEquals(
				"KeyValuePairService getKeyValue API is not working", 200,
				getKeyValueReqGen.response.getStatus());

		try {
			String jsonschema = new Toolbox()
					.readFileAsString("Data/SchemaSet/JSON/configservice-keyvaluepair-getkeyvalue-schema.txt");
			List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(
					getKeyValueResponse, jsonschema);
			AssertJUnit
					.assertTrue(
							missingNodeList
									+ " nodes are missing in KeyValuePairService getKeyValue API response",
							CollectionUtils.isEmpty(missingNodeList));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test(groups = { "SchemaValidation" }, dataProvider = "KeyValuePairDP_getKeyValuePairForGivenList_verifyResponseDataNodesUsingSchemaValidations")
	public void KeyValuePairService_getKeyValuePairForGivenList_verifyResponseDataNodesUsingSchemaValidations(
			String keys) {
		RequestGenerator getKeyValuePairForGivenListReqGen = getHeaderRequest(
				APINAME.GETKEYVALUEFORGIVENLIST, keys);
		String getKeyValuePairForGivenListResponse = getKeyValuePairForGivenListReqGen.respvalidate
				.returnresponseasstring();
		System.out
				.println("\nPrinting KeyValuePairService getKeyValuePairForGivenList API response :\n\n"
						+ getKeyValuePairForGivenListResponse);
		log.info("\nPrinting KeyValuePairService getKeyValuePairForGivenList API response :\n\n"
				+ getKeyValuePairForGivenListResponse);

		AssertJUnit
				.assertEquals(
						"KeyValuePairService getKeyValuePairForGivenList API is not working",
						200,
						getKeyValuePairForGivenListReqGen.response.getStatus());

		try {
			String jsonschema = new Toolbox()
					.readFileAsString("Data/SchemaSet/JSON/configservice-keyvaluepair-getkeyvaluepairforgivenlist-schema.txt");
			List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(
					getKeyValuePairForGivenListResponse, jsonschema);
			AssertJUnit
					.assertTrue(
							missingNodeList
									+ " nodes are missing in KeyValuePairService getKeyValuePairForGivenList API response",
							CollectionUtils.isEmpty(missingNodeList));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test(groups = { "SchemaValidation" })
	public void KeyValuePairService_getAllKeyValuePair_verifyResponseDataNodesUsingSchemaValidations() {
		RequestGenerator getAllKeyValuePairReqGen = getRequest(APINAME.GETALLKEYVALUEPAIR);
		String getAllKeyValuePairResponse = getAllKeyValuePairReqGen.respvalidate
				.returnresponseasstring();
		System.out
				.println("\nPrinting KeyValuePairService getAllKeyValuePair API response :\n\n"
						+ getAllKeyValuePairResponse);
		log.info("\nPrinting KeyValuePairService getAllKeyValuePair API response :\n\n"
				+ getAllKeyValuePairResponse);

		AssertJUnit.assertEquals(
				"KeyValuePairService getAllKeyValuePair API is not working",
				200, getAllKeyValuePairReqGen.response.getStatus());

		try {
			String jsonschema = new Toolbox()
					.readFileAsString("Data/SchemaSet/JSON/configservice-keyvaluepair-getallkeyvaluepair-schema.txt");
			List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(
					getAllKeyValuePairResponse, jsonschema);
			AssertJUnit
					.assertTrue(
							missingNodeList
									+ " nodes are missing in KeyValuePairService getAllKeyValuePair API response",
							CollectionUtils.isEmpty(missingNodeList));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test(groups = { "SchemaValidation" }, dataProvider = "KeyValuePairDP_updateKeyValuePair_verifyResponseDataNodesUsingSchemaValidations")
	public void KeyValuePairService_updateKeyValuePair_verifyResponseDataNodesUsingSchemaValidations(
			String key, String value, String desc, String SuccessCode,
			String successMessage, String status, String totalCount) {
		RequestGenerator updateKeyValuePairReqGen = getRequest(
				APINAME.UPDATEFEATUREKEYVALUEPAIR, key, value, desc);
		String updateKeyValuePairResponse = updateKeyValuePairReqGen.respvalidate
				.returnresponseasstring();
		System.out
				.println("\nPrinting KeyValuePairService updateKeyValuePair API response :\n\n"
						+ updateKeyValuePairResponse);
		log.info("\nPrinting KeyValuePairService updateKeyValuePair API response :\n\n"
				+ updateKeyValuePairResponse);

		AssertJUnit.assertEquals(
				"KeyValuePairService updateKeyValuePair API is not working",
				200, updateKeyValuePairReqGen.response.getStatus());

		try {
			String jsonschema = new Toolbox()
					.readFileAsString("Data/SchemaSet/JSON/configservice-keyvaluepair-updatekeyvaluepair-schema.txt");
			List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(
					updateKeyValuePairResponse, jsonschema);
			AssertJUnit
					.assertTrue(
							missingNodeList
									+ " nodes are missing in KeyValuePairService updateKeyValuePair API response",
							CollectionUtils.isEmpty(missingNodeList));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test(groups = { "SchemaValidation" }, dataProvider = "KeyValuePairDP_updateKeyValuePairForGivenList_verifyResponseDataNodesUsingSchemaValidations")
	public void KeyValuePairService_updateKeyValuePairForGivenList_verifyResponseDataNodesUsingSchemaValidations(
			String key1, String value1, String desc1, String key2,
			String value2, String desc2, String SuccessCode,
			String successMessage, String status, String totalCount) {
		RequestGenerator updateKeyValuePairForGivenListReqGen = getRequest(
				APINAME.UPDATELISTOFFEATUREKEYVALUEPAIR, key1, value1, desc1,
				key2, value2, desc2);
		String updateKeyValuePairForGivenListResponse = updateKeyValuePairForGivenListReqGen.respvalidate
				.returnresponseasstring();
		System.out
				.println("\nPrinting KeyValuePairService updateKeyValuePairForGivenList API response :\n\n"
						+ updateKeyValuePairForGivenListResponse);
		log.info("\nPrinting KeyValuePairService updateKeyValuePairForGivenList API response :\n\n"
				+ updateKeyValuePairForGivenListResponse);

		AssertJUnit
				.assertEquals(
						"KeyValuePairService updateKeyValuePairForGivenList API is not working",
						200, updateKeyValuePairForGivenListReqGen.response
								.getStatus());

		try {
			String jsonschema = new Toolbox()
					.readFileAsString("Data/SchemaSet/JSON/configservice-keyvaluepair-updatekeyvaluepairforgivenlist-schema.txt");
			List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(
					updateKeyValuePairForGivenListResponse, jsonschema);
			AssertJUnit
					.assertTrue(
							missingNodeList
									+ " nodes are missing in KeyValuePairService updateKeyValuePairForGivenList API response",
							CollectionUtils.isEmpty(missingNodeList));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test(groups = { "SchemaValidation" }, dataProvider = "KeyValuePairDP_getWidgetKeyValuePair_verifyResponseDataNodesUsingSchemaValidations")
	public void KeyValuePairService_getWidgetKeyValuePair_verifyResponseDataNodesUsingSchemaValidations(
			String key) {
		RequestGenerator getWidgetKeyValuePairReqGen = getQueryRequest(
				APINAME.WIDGETGETKEYVALUE, key);
		String getWidgetKeyValuePairResponse = getWidgetKeyValuePairReqGen.respvalidate
				.returnresponseasstring();
		System.out
				.println("\nPrinting KeyValuePairService getWidgetKeyValuePair API response :\n\n"
						+ getWidgetKeyValuePairResponse);
		log.info("\nPrinting KeyValuePairService getWidgetKeyValuePair API response :\n\n"
				+ getWidgetKeyValuePairResponse);

		AssertJUnit.assertEquals(
				"KeyValuePairService getWidgetKeyValuePair API is not working",
				200, getWidgetKeyValuePairReqGen.response.getStatus());

		try {
			String jsonschema = new Toolbox()
					.readFileAsString("Data/SchemaSet/JSON/configservice-keyvaluepair-getwidgetkeyvaluepair-schema.txt");
			List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(
					getWidgetKeyValuePairResponse, jsonschema);
			AssertJUnit
					.assertTrue(
							missingNodeList
									+ " nodes are missing in KeyValuePairService getWidgetKeyValuePair API response",
							CollectionUtils.isEmpty(missingNodeList));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test(groups = { "SchemaValidation" }, dataProvider = "KeyValuePairDP_getWidgetKeyValuePairForGivenList_verifyResponseDataNodesUsingSchemaValidations")
	public void KeyValuePairService_getWidgetKeyValuePairForGivenList_verifyResponseDataNodesUsingSchemaValidations(
			String keys) {
		RequestGenerator getWidgetKeyValuePairForGivenListReqGen = getHeaderRequest(
				APINAME.GETWIDGETKEYVALUEFORGIVENLIST, keys);
		String getWidgetKeyValuePairForGivenListResponse = getWidgetKeyValuePairForGivenListReqGen.respvalidate
				.returnresponseasstring();
		System.out
				.println("\nPrinting KeyValuePairService getWidgetKeyValuePairForGivenList API response :\n\n"
						+ getWidgetKeyValuePairForGivenListResponse);
		log.info("\nPrinting KeyValuePairService getWidgetKeyValuePairForGivenList API response :\n\n"
				+ getWidgetKeyValuePairForGivenListResponse);

		AssertJUnit
				.assertEquals(
						"KeyValuePairService getWidgetKeyValuePairForGivenList API is not working",
						200, getWidgetKeyValuePairForGivenListReqGen.response
								.getStatus());

		try {
			String jsonschema = new Toolbox()
					.readFileAsString("Data/SchemaSet/JSON/configservice-keyvaluepair-getwidgetkeyvaluepairforgivenlist-schema.txt");
			List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(
					getWidgetKeyValuePairForGivenListResponse, jsonschema);
			AssertJUnit
					.assertTrue(
							missingNodeList
									+ " nodes are missing in KeyValuePairService getWidgetKeyValuePairForGivenList API response",
							CollectionUtils.isEmpty(missingNodeList));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test(groups = { "SchemaValidation" })
	public void KeyValuePairService_getAllWidgetKeyValuePair_verifyResponseDataNodesUsingSchemaValidations() {
		RequestGenerator getAllWidgetKeyValuePairReqGen = getRequest(APINAME.GETALLWIDGETKEYVALUEPAIR);
		String getAllWidgetKeyValuePairResponse = getAllWidgetKeyValuePairReqGen.respvalidate
				.returnresponseasstring();
		System.out
				.println("\nPrinting KeyValuePairService getAllWidgetKeyValuePair API response :\n\n"
						+ getAllWidgetKeyValuePairResponse);
		log.info("\nPrinting KeyValuePairService getAllWidgetKeyValuePair API response :\n\n"
				+ getAllWidgetKeyValuePairResponse);

		AssertJUnit
				.assertEquals(
						"KeyValuePairService getAllWidgetKeyValuePair API is not working",
						200,
						getAllWidgetKeyValuePairReqGen.response.getStatus());

		try {
			String jsonschema = new Toolbox()
					.readFileAsString("Data/SchemaSet/JSON/configservice-keyvaluepair-getallwidgetkeyvaluepair-schema.txt");
			List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(
					getAllWidgetKeyValuePairResponse, jsonschema);
			AssertJUnit
					.assertTrue(
							missingNodeList
									+ " nodes are missing in KeyValuePairService getAllWidgetKeyValuePair API response",
							CollectionUtils.isEmpty(missingNodeList));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test(groups = { "SchemaValidation" }, dataProvider = "KeyValuePairDP_updateWidgetKeyValuePair_verifyResponseDataNodesUsingSchemaValidations")
	public void KeyValuePairService_updateWidgetKeyValuePair_verifyResponseDataNodesUsingSchemaValidations(
			String key, String value, String desc, String SuccessCode,
			String successMessage, String status, String totalCount) {
		RequestGenerator updateWidgetKeyValuePairReqGen = getRequest(
				APINAME.UPDATEWIDGETKEYVALUEPAIR, key, value, desc);
		String updateWidgetKeyValuePairResponse = updateWidgetKeyValuePairReqGen.respvalidate
				.returnresponseasstring();
		System.out
				.println("\nPrinting KeyValuePairService updateWidgetKeyValuePair API response :\n\n"
						+ updateWidgetKeyValuePairResponse);
		log.info("\nPrinting KeyValuePairService updateWidgetKeyValuePair API response :\n\n"
				+ updateWidgetKeyValuePairResponse);

		AssertJUnit
				.assertEquals(
						"KeyValuePairService updateWidgetKeyValuePair API is not working",
						200,
						updateWidgetKeyValuePairReqGen.response.getStatus());

		try {
			String jsonschema = new Toolbox()
					.readFileAsString("Data/SchemaSet/JSON/configservice-keyvaluepair-updatewidgetkeyvaluepair-schema.txt");
			List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(
					updateWidgetKeyValuePairResponse, jsonschema);
			AssertJUnit
					.assertTrue(
							missingNodeList
									+ " nodes are missing in KeyValuePairService updateWidgetKeyValuePair API response",
							CollectionUtils.isEmpty(missingNodeList));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test(groups = { "SchemaValidation" }, dataProvider = "KeyValuePairDP_updateWidgetKeyValuePairForGivenList_verifyResponseDataNodesUsingSchemaValidations")
	public void KeyValuePairService_updateWidgetKeyValuePairForGivenList_verifyResponseDataNodesUsingSchemaValidations(
			String key1, String value1, String desc1, String key2,
			String value2, String desc2, String SuccessCode,
			String successMessage, String status, String totalCount) {
		RequestGenerator updateWidgetKeyValuePairForGivenListReqGen = getRequest(
				APINAME.UPDATELISTOFWIDGETKEYVALUEPAIR, key1, value1, desc1,
				key2, value2, desc2);
		String updateWidgetKeyValuePairForGivenListResponse = updateWidgetKeyValuePairForGivenListReqGen.respvalidate
				.returnresponseasstring();
		System.out
				.println("\nPrinting KeyValuePairService updateWidgetKeyValuePairForGivenList API response :\n\n"
						+ updateWidgetKeyValuePairForGivenListResponse);
		log.info("\nPrinting KeyValuePairService updateWidgetKeyValuePairForGivenList API response :\n\n"
				+ updateWidgetKeyValuePairForGivenListResponse);

		AssertJUnit
				.assertEquals(
						"KeyValuePairService updateWidgetKeyValuePairForGivenList API is not working",
						200,
						updateWidgetKeyValuePairForGivenListReqGen.response
								.getStatus());

		try {
			String jsonschema = new Toolbox()
					.readFileAsString("Data/SchemaSet/JSON/configservice-keyvaluepair-updatewidgetkeyvaluepairforgivenlist-schema.txt");
			List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(
					updateWidgetKeyValuePairForGivenListResponse, jsonschema);
			AssertJUnit
					.assertTrue(
							missingNodeList
									+ " nodes are missing in KeyValuePairService updateWidgetKeyValuePairForGivenList API response",
							CollectionUtils.isEmpty(missingNodeList));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
