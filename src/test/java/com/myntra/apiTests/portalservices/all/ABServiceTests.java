package com.myntra.apiTests.portalservices.all;

import java.util.List;

import com.myntra.apiTests.dataproviders.ABTestDP;
import net.minidev.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import com.jayway.jsonpath.JsonPath;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.Toolbox;
import com.myntra.apiTests.common.APINAME;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.RequestGenerator;

/*
 * Moved following test cases in BestPractises:
 * 1. ABService_getVariantOfGivenABTestWithABtest_negative
 * 2. ABService_getVariantOfGivenABTestWithSegment_negative
 * 3. ABService_getVariantOfGivenABTest_negative1
 * 4. ABService_getVariantOfGivenABTest_vNegativeStatusNodes
 * 5. ABService_getVariantOfGivenABTest_vNegativeStatusNodesVals
 * 6. ABService_getVariantOfGivenABTest_negative
 */


/**
 * @author shankara.c
 *
 */
public class ABServiceTests extends ABTestDP
{
	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(ABServiceTests.class);
	APIUtilities apiUtil = new APIUtilities();

	/*@Test(groups = { "Smoke", "Sanity", "ProdSanity",
			 "ExhaustiveRegression" }, dataProvider = "getABTestDataProvider")
	public void ABService_getABTest(String ABTestName) {
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_CONFIGURATION, APINAME.GETABTEST,
				init.Configurations, PayloadType.JSON,
				new String[] { ABTestName }, PayloadType.JSON);
		System.out.println(service.URL);
		log.info(service.URL);
		RequestGenerator req = new RequestGenerator(service);
		log.info(req.response);
		AssertJUnit.assertEquals("Get AB Test service is not working", 200,
				req.response.getStatus());
	}*/

	/*@Test(groups = { "ProdSanity", 
	"ExhaustiveRegression" }, dataProvider = "getABTestForGivenListDataProvider")
	public void ABService_getABTestForGivenList(String pair1, String pair2) {
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_CONFIGURATION,
				APINAME.GETABTESTFORGIVENLIST, init.Configurations,
				PayloadType.JSON, new String[] { pair1, pair2 },
				PayloadType.JSON);
		HashMap hs = new HashMap();
		System.out.println(ArrayUtils.toString(new String[] { pair1, pair2 }));
		hs.put("abtestList", ArrayUtils.toString(new String[] { pair1, pair2 }));
		RequestGenerator req = new RequestGenerator(service, hs);
		log.info(service.URL);
		// System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("getABTestServiceForGivenList is not working",
				200, req.response.getStatus());
	}*/

	/*	@Test(groups = { "", "" })
	// Taking too much time to fetch results
	public void ABService_getAllABTest() {
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_CONFIGURATION, APINAME.GETALLABTESTS,
				init.Configurations);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);
		// System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("getAllABTest is not working", 200,
				req.response.getStatus());
	}
	 */
	/*@Test(groups = { "Sanity", "Smoke", "ProdSanity",
			 "ExhaustiveRegression" })
	public void ABService_getAllABTestNames() {
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_CONFIGURATION, APINAME.GETALLABTESTNAMES,
				init.Configurations);
		log.info(service.URL);
		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service);
		log.info(req.response);
		// System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("getAllABTestNames API is not working", 200,
				req.response.getStatus());
	}*/

	/*@Test(groups = { "Sanity", "ProdSanity", 
	"ExhaustiveRegression" }, dataProvider = "getAllABTestVariantsDataProvider")
	public void ABService_getAllABTestVariants(String ABTestName) {
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_CONFIGURATION, APINAME.GETALLABTESTVARIANTS,
				init.Configurations, PayloadType.JSON,
				new String[] { ABTestName }, PayloadType.JSON);
		log.info(service.URL);
		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service);
		log.info(req.response);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("getAllABTestVariants API is not working",
				200, req.response.getStatus());
	}
	 */
	/*@Test(groups = { "Sanity", 
	"ExhaustiveRegression" }, dataProvider = "createNewABTestDataProvider")
	public void ABService_createNewABTest(String variation_name,
			String percentProbability, String inlineHtml, String finalVariant,
			String jsFile, String cssFile, String configJson,
			String algoConfigJson, String auditLogs, String name,
			String isEnabled, String gaSlot, String omniSlot, String filters,
			String segmentationAlgo, String sourceType, String isCompleted,
			String apiVersion) {
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_CONFIGURATION, APINAME.CREATENEWABTEST,
				init.Configurations, new String[] { variation_name,
						percentProbability, inlineHtml, finalVariant, jsFile,
						cssFile, configJson, algoConfigJson, auditLogs, name,
						isEnabled, gaSlot, omniSlot, filters, segmentationAlgo,
						sourceType, isCompleted, apiVersion });
		System.out.println(service.Payload);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);
		System.out.println(service.URL);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("createNewABTest is not working", 200,
				req.response.getStatus());
	}*/

	/*@Test(groups = { "Sanity", 
	"ExhaustiveRegression" }, dataProvider = "getVariantOfGivenABTestDataProvider")
	public void ABService_getVariantOfGivenABTest(String ABTestName) {
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_CONFIGURATION,
				APINAME.GETVARIANTOFGIVENABTEST, init.Configurations,
				new String[] { ABTestName }, PayloadType.JSON, PayloadType.JSON);
		service.URL = apiUtil.prepareparameterizedURL(service.URL,
				new String[] { ABTestName });
		System.out.println(service.URL);
		HashMap hm = new HashMap();
		hm.put("login", "mohit.jain@myntra.com");
		RequestGenerator req = new RequestGenerator(service, hm);
		log.info(service.URL);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("getVariantOfGivenABTest is not working", 200,
				req.response.getStatus());
	}*/
	/*
	@Test(groups = { "Sanity", 
	"ExhaustiveRegression" }, dataProvider = "deleteExistingABTestDataProvider")
	public void ABService_deleteExistingABTest(String ABTestName) {
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_CONFIGURATION, APINAME.DELETEEXISTINGABTEST,
				init.Configurations, PayloadType.JSON,
				new String[] { ABTestName }, PayloadType.JSON);
		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("deleteExistingABTest is not working", 200,
				req.response.getStatus());
	}
	 */
	/*@Test(groups = { "Sanity", 
	"ExhaustiveRegression" }, dataProvider = "updateExistingABTestDataProvider")
	public void ABService_updateExistingABTest(String name,
			String percentProbability, String inlineHtml, String finalVariant,
			String name1, String percentProbability1, String inlineHtml1,
			String finalVariant1, String auditLogs, String name2,
			String isEnabled, String gaSlot, String omniSlot, String filters,
			String segmentationAlgo, String sourceType, String isCompleted,
			String apiVersion) {
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_CONFIGURATION, APINAME.UPDATEEXISTINGABTEST,
				init.Configurations, new String[] { name, percentProbability,
						inlineHtml, finalVariant, name1, percentProbability1,
						inlineHtml1, finalVariant1, auditLogs, name2,
						isEnabled, gaSlot, omniSlot, filters, segmentationAlgo,
						sourceType, isCompleted, apiVersion });
		System.out.println(service.Payload);
		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("updateExistingABTest is not working", 200,
				req.response.getStatus());
	}*/

	/*	@Test(groups = { "Sanity", 
	"ExhaustiveRegression" })
	public void updateOrderStatusToShip()
	{
		String orderId = "123456";
		MyntraService service = Myntra.getService(ServiceType.PORTAL_REQUEST,
				APINAME.UPDATEORDERSTATUSTOSHIP, init.Configurations,PayloadType.XML,
				new String[] { orderId }, PayloadType.XML);
		HashMap getParam = new HashMap();
		getParam.put("Authorization",
				"Basic YXBpYWRtaW46bTFudHJhUjBja2V0MTMhIw==");

		RequestGenerator req = new RequestGenerator(service, getParam);
		// RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);
		AssertJUnit.assertEquals("Status code does not match", 200,
				req.response.getStatus());
	}*/

	//--------------------------------------------------------------------------------------------------------

	/**
	 * Get all ABTests.
	 * @author jhansi.bai
	 */
	@Test(groups = { "Sanity",  "ProdSanity",  "Regression", "MiniRegression", "ExhaustiveRegression", "RFPFOX7",  "RFPPROD"},description = "Hit the service and get the list of all A/B Tests")
	public void ABService_getAllABTest()
	{
		RequestGenerator req = getRequest(APINAME.GETALLABTESTS);
		//System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("getAllABTest is not working", 200,
				req.response.getStatus());
	}

	/**
	 * Get all ABTests.
	 * @author jhansi.bai
	 */
	@Test(groups = { "Regression", "MiniRegression", "ExhaustiveRegression", "RFPFOX7",  "RFPPROD" },description = "1. Hit the service and get the list of all A/B Tests \n 2. Verify the status nodes.")
	public void ABService_getAllABTest_vStatusNodes()	{

		RequestGenerator req = getRequest(APINAME.GETALLABTESTS);
		//	System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertTrue("Inval getAllABTest API status nodes.", 
				req.respvalidate.DoesNodesExists(abTestsStatusNodes()));
	}

	/**
	 * Get all ABTests
	 * @author jhansi.bai
	 */
	@Test(groups = {  "Regression", "MiniRegression", "ExhaustiveRegression", "RFPFOX7",  "RFPPROD"},description = "1. Hit the service and get the list of all A/B Tests \n 2. Verify the success status message in response")
	public void ABService_getAllABTest_vSuccStatusMsg()	{
		RequestGenerator req = getRequest(APINAME.GETALLABTESTS);
		AssertJUnit.assertEquals("Status code does't match", req.respvalidate.NodeValue(abTestsStatusNodes().get(0), true), "15002");			
		AssertJUnit.assertEquals("Status message dosn't match",	req.respvalidate.NodeValue(abTestsStatusNodes().get(1), true), 
				"\"ABTest(s) retrieved successfully\"");		 
		AssertJUnit.assertEquals("Status type dosn't match", req.respvalidate.NodeValue(abTestsStatusNodes().get(2), true), "\"SUCCESS\"");
		AssertJUnit.assertEquals("Total count dosn't match", req.respvalidate.NodeValue(abTestsStatusNodes().get(3), true), req.respvalidate.GetArraySize("data")+"");
		log.info(req.respvalidate.returnresponseasstring());
	}

	/**
	 * Get ABTests for a given list.
	 * @author jhansi.bai
	 */
	@Test(groups = {"Sanity",  "ProdSanity", "Regression", "MiniRegression", "ExhaustiveRegression", "RFPFOX7",  "RFPPROD" }, 
			dataProvider = "getABTestsForGivenListDP", description = "Get the details of AB tests for a given list of inputs")
	public void ABService_getABTestsForGivenList(String tests, 
			String statusCode, String successMessage, String successStatus)
	{
		RequestGenerator req = getHeaderRequest(APINAME.GETABTESTFORGIVENLIST, tests);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("getABTestsForGivenList is not working",
				200, req.response.getStatus());
	}

	/**
	 * Get ABTests for a given list.
	 * @author jhansi.bai
	 */
	@Test(groups = { "Regression", "MiniRegression", "ExhaustiveRegression", "RFPFOX7",  "RFPPROD" }, 
			dataProvider = "getABTestsForGivenListDP",description = "1. Get the details of AB tests for a given list of inputs \n 2. Verify status and data nodes in response")
	public void ABService_getABTestsForGivenList_vStatusAdDataNodes(String tests, 
			String statusCode, String successMessage, String successStatus)	{

		RequestGenerator req = getHeaderRequest(APINAME.GETABTESTFORGIVENLIST, tests);
		//	System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertTrue("Inval ABtest API status nodes.", 
				req.respvalidate.DoesNodesExists(abTestsStatusNodes()));
		AssertJUnit.assertTrue("Inval ABtest API data nodes.", 
				req.respvalidate.DoesNodesExists(abTestsDataNodes()));
		AssertJUnit.assertTrue("Inval ABtest API variant nodes.", 
				req.respvalidate.DoesNodesExists(abTestsDataVariantNodes()));
	}

	/**
	 * Get ABTests for a given list.
	 * @author jhansi.bai
	 */
	@Test(groups = {"MiniRegression",  "Regression", "MiniRegression", "ExhaustiveRegression", "RFPFOX7",  "RFPPROD"},
			dataProvider = "getABTestsForGivenListDP",description = "1. Get the details of AB tests for a given list of inputs \n 2. Verify success status message in response")
	public void ABService_getABTestsForGivenList_vSuccStatusMsg(String tests,
			String statusCode, String successMessage, String successStatus)	{
		RequestGenerator req = getHeaderRequest(APINAME.GETABTESTFORGIVENLIST, tests);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("Status code does't match", req.respvalidate.NodeValue(abTestsStatusNodes().get(0), true), statusCode);			
		AssertJUnit.assertEquals("Status message dosn't match",	req.respvalidate.NodeValue(abTestsStatusNodes().get(1), true), 
				successMessage);		 
		AssertJUnit.assertEquals("Status type dosn't match", req.respvalidate.NodeValue(abTestsStatusNodes().get(2), true), successStatus);
		AssertJUnit.assertEquals("Total count dosn't match", req.respvalidate.NodeValue(abTestsStatusNodes().get(3), true),  
				req.respvalidate.GetArraySize("data")+"");
		log.info(req.respvalidate.returnresponseasstring());
	}

	/**
	 * Get ABTests for a given list.
	 * @author jhansi.bai
	 */
	@Test(groups = {"Sanity", "Regression", "MiniRegression", "ExhaustiveRegression", "RFPFOX7",  "RFPPROD" }, dataProvider = "getABTestsForGivenListDP_negative",description = "Verify the success response of the service for invalid values of AB Tests")
	public void ABService_getABTestsForGivenList_negative(String tests,
			String errStatusCode, String errorMessage, String errorStatus, String itemCount)
	{
		RequestGenerator req = getHeaderRequest(APINAME.GETABTESTFORGIVENLIST, tests);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("getABTestsForGivenList is not working",
				200, req.response.getStatus());
	}

	/**
	 * Get ABTests for a given list.
	 * @author jhansi.bai
	 */
	@Test(groups = { "Regression", "MiniRegression", "ExhaustiveRegression", "RFPFOX7",  "RFPPROD" }, 
			dataProvider = "getABTestsForGivenListDP_negative",description ="For invalid values of A/B Tests verify the status and data nodes")
	public void ABService_getABTestsForGivenList_vNegativeStatusAdDataNodes(String tests,
			String errStatusCode, String errorMessage, String errorStatus, String itemCount)	{

		RequestGenerator req = getHeaderRequest(APINAME.GETABTESTFORGIVENLIST, tests);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertTrue("Inval ABTests API status nodes.", 
				req.respvalidate.DoesNodesExists(abTestsStatusNodes()));
	}

	/**
	 * Get ABTests for a given list.
	 * @author jhansi.bai
	 */
	@Test(groups = {"MiniRegression",  "Regression", "MiniRegression", "ExhaustiveRegression", "RFPFOX7"},
			dataProvider = "getABTestsForGivenListDP_negative",description ="For invalid values of A/B Tests verify the status message")
	public void ABService_getABTestsForGivenList_vNegativeSuccStatusMsg(String tests,
			String errStatusCode, String errorMessage, String errorStatus, String itemCount)	{
		RequestGenerator req = getHeaderRequest(APINAME.GETABTESTFORGIVENLIST, tests);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("Status code does't match", req.respvalidate.NodeValue(abTestsStatusNodes().get(0), true), errStatusCode);			
		AssertJUnit.assertEquals("Status message dosn't match",	req.respvalidate.NodeValue(abTestsStatusNodes().get(1), true), 
				errorMessage);		 
		AssertJUnit.assertEquals("Status type dosn't match", req.respvalidate.NodeValue(abTestsStatusNodes().get(2), true), errorStatus);
		AssertJUnit.assertEquals("Status type dosn't match", req.respvalidate.NodeValue(abTestsStatusNodes().get(3), true), itemCount);
		log.info(req.respvalidate.returnresponseasstring());
	}  

	/**
	 * Get ABTests for a given list.
	 * @author jhansi.bai
	 */
	@Test(groups = {"Sanity", "Regression", "MiniRegression", "ExhaustiveRegression", "RFPFOX7" }, 
			dataProvider = "getABTestsForGivenListDP_negative1",description ="For invalid values of A/B Tests verify 500 resonse from service")
	public void ABService_getABTestsForGivenList_negative500Status(String tests)
	{
		RequestGenerator req = getHeaderRequest(APINAME.GETABTESTFORGIVENLIST, tests);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("getABTestsForGivenList is not working",
				500, req.response.getStatus());
	}

	/**
	 * Get ABTests for a given test.
	 * @author jhansi.bai
	 */
	@Test(groups = { "Smoke", "Sanity", "ProdSanity",
			 "Regression", "MiniRegression", "ExhaustiveRegression", "RFPFOX7",  "RFPPROD" }, dataProvider = "getABTestDP", description ="Verify 200 response from Get A/B Test api response")
	public void ABService_getABTest(String testName, 
			String statusCode, String successMessage, String successStatus) {
		RequestGenerator req = getQueryRequest(APINAME.GETABTEST, testName);
		//	System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("Get AB Test service is not working", 200,
				req.response.getStatus());
	}

	/**
	 * Get ABTests for a given test.
	 * @author jhansi.bai
	 */
	@Test(groups = { "Regression", "MiniRegression", "ExhaustiveRegression", "RFPFOX7",  "RFPPROD" }, 
			dataProvider = "getABTestDP", description ="Verify status and data nodes from Get A/B Test api response")
	public void ABService_getABTest_vStatusAdDataNodes(String testName, 
			String statusCode, String successMessage, String successStatus)	{

		RequestGenerator req = getQueryRequest(APINAME.GETABTEST, testName);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertTrue("Inval ABtest API status nodes.", 
				req.respvalidate.DoesNodesExists(abTestsStatusNodes()));
		AssertJUnit.assertTrue("Inval ABtest API data nodes.", 
				req.respvalidate.DoesNodesExists(abTestsDataNodes()));
		AssertJUnit.assertTrue("Inval ABtest API variant nodes.", 
				req.respvalidate.DoesNodesExists(abTestsDataVariantNodes()));
	}

	/**
	 * Get ABTests for a given test.
	 * @author jhansi.bai
	 */
	@Test(groups = {"MiniRegression",  "Regression", "MiniRegression", "ExhaustiveRegression", "RFPFOX7",  "RFPPROD"},
			dataProvider = "getABTestDP", description ="Verify success status message from Get A/B Test api response")
	public void ABService_getABTest_vSuccStatusMsg(String testName,
			String statusCode, String successMessage, String successStatus)	{
		RequestGenerator req = getQueryRequest(APINAME.GETABTEST, testName);
		AssertJUnit.assertEquals("Status code does't match", req.respvalidate.NodeValue(abTestsStatusNodes().get(0), true), statusCode);			
		AssertJUnit.assertEquals("Status message dosn't match",	req.respvalidate.NodeValue(abTestsStatusNodes().get(1), true), 
				successMessage);		 
		AssertJUnit.assertEquals("Status type dosn't match", req.respvalidate.NodeValue(abTestsStatusNodes().get(2), true), successStatus);
		AssertJUnit.assertEquals("Total count dosn't match", req.respvalidate.NodeValue(abTestsStatusNodes().get(3), true),  
				req.respvalidate.GetArraySize("data")+"");
		log.info(req.respvalidate.returnresponseasstring());
	}

	/**
	 * Get ABTests for a given test.
	 * @author jhansi.bai
	 */
	@Test(groups = {"Sanity",  "Regression", "MiniRegression", "ExhaustiveRegression", "RFPFOX7"},
			dataProvider = "getABTestDP_negative", description ="Verify 200 response from Get A/B Test api response for invalid set of datas")
	public void ABService_getABTest_negative(String testName,
			String errStatusCode, String errorMessage, String errorStatus, String itemCount)	{
		RequestGenerator req = getQueryRequest(APINAME.GETABTEST, testName);
		//	System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("Get AB Test service is not working", 200,
				req.response.getStatus());
	}

	/**
	 * Get ABTests for a given test.
	 * @author jhansi.bai
	 */
	@Test(groups = {"MiniRegression",  "Regression", "MiniRegression", "ExhaustiveRegression", "RFPFOX7"},
			dataProvider = "getABTestDP_negative", description ="Verify status nodes from Get A/B Test api response for set of invalid inputs")
	public void ABService_getABTest_vNegativeStatusNodes(String testName,
			String errStatusCode, String errorMessage, String errorStatus, String itemCount)	{
		RequestGenerator req = getQueryRequest(APINAME.GETABTEST, testName);
		//	System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertTrue("Inval ABtest API status nodes.", 
				req.respvalidate.DoesNodesExists(abTestsStatusNodes()));
	}

	/**
	 * Get ABTests for a given test.
	 * @author jhansi.bai
	 */
	@Test(groups = {"MiniRegression",  "Regression", "MiniRegression", "ExhaustiveRegression", "RFPFOX7"},
			dataProvider = "getABTestDP_negative", description ="Verify status message from Get A/B Test api response for set of invalid inputs")
	public void ABService_getABTest_vNegativeSuccStatusMsg(String testName,
			String errStatusCode, String errorMessage, String errorStatus, String itemCount)	{
		RequestGenerator req = getQueryRequest(APINAME.GETABTEST, testName);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("Status code does't match", req.respvalidate.NodeValue(abTestsStatusNodes().get(0), true), errStatusCode);			
		AssertJUnit.assertEquals("Status message dosn't match",	req.respvalidate.NodeValue(abTestsStatusNodes().get(1), true), 
				errorMessage);		 
		AssertJUnit.assertEquals("Status type dosn't match", req.respvalidate.NodeValue(abTestsStatusNodes().get(2), true), errorStatus);
		AssertJUnit.assertEquals("Total count dosn't match", req.respvalidate.NodeValue(abTestsStatusNodes().get(3), true), itemCount);
		log.info(req.respvalidate.returnresponseasstring());
	}

	/**
	 * Get all ABTests names.
	 * @author jhansi.bai
	 */
	@Test(groups = { "Sanity",  "ProdSanity",  "Regression", "MiniRegression", "ExhaustiveRegression", "RFPFOX7",  "RFPPROD"},description ="Verify 200 response for Get All A/B Test Names API")
	public void ABService_getAllABTestNames()
	{
		RequestGenerator req = getRequest(APINAME.GETALLABTESTNAMES);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("getAllABTest is not working", 200,
				req.response.getStatus());
	}

	/**
	 * Get all ABTests names.
	 * @author jhansi.bai
	 */
	@Test(groups = { "Regression", "MiniRegression", "ExhaustiveRegression", "RFPFOX7",  "RFPPROD" },description ="Verify success status node in response for Get All A/B Test Names API")
	public void ABService_getAllABTestNames_vStatusNodes()	{

		RequestGenerator req = getRequest(APINAME.GETALLABTESTNAMES);
		//	System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertTrue("Inval getAllABTest API status nodes.", 
				req.respvalidate.DoesNodesExists(abTestsStatusNodes()));
		AssertJUnit.assertTrue("Inval getAllABTest API status nodes.", 
				req.respvalidate.DoesNodesExists(abTestsNameNode()));
	}

	/**
	 * Get all ABTests names.
	 * @author jhansi.bai
	 */
	@Test(groups = {  "Regression", "MiniRegression", "ExhaustiveRegression", "RFPFOX7",  "RFPPROD"}, description ="Verify success status message for Get All A/B Test Names API")
	public void ABService_getAllABTestNames_vSuccStatusMsg()	{
		RequestGenerator req = getRequest(APINAME.GETALLABTESTNAMES);
		AssertJUnit.assertEquals("Status code does't match", req.respvalidate.NodeValue(abTestsStatusNodes().get(0), true), "15002");			
		AssertJUnit.assertEquals("Status message dosn't match",	req.respvalidate.NodeValue(abTestsStatusNodes().get(1), true), 
				"\"ABTest(s) retrieved successfully\"");		 
		AssertJUnit.assertEquals("Status type dosn't match", req.respvalidate.NodeValue(abTestsStatusNodes().get(2), true), "\"SUCCESS\"");
		AssertJUnit.assertEquals("Total count dosn't match", req.respvalidate.NodeValue(abTestsStatusNodes().get(3), true), 
				req.respvalidate.GetArraySize("data")+"");
		log.info(req.respvalidate.returnresponseasstring());
	}

	/**
	 * Get all ABTest variants.
	 * @author jhansi.bai
	 */
	@Test(groups = { "Smoke", "Sanity", "ProdSanity",
			 "Regression", "MiniRegression", "ExhaustiveRegression", "RFPFOX7",  "RFPPROD" }, dataProvider = "getABTestDP", description ="Verify Get All A/B Test variants API is giving 200 response for set of invalid values")
	public void ABService_getABTestVariants(String testName, 
			String statusCode, String successMessage, String successStatus) {
		RequestGenerator req = getQueryRequest(APINAME.GETALLABTESTVARIANTS, testName);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("Get AB Test variants service is not working", 200,
				req.response.getStatus());
	}

	/**
	 * Get all ABTest variants
	 * @author jhansi.bai
	 */
	@Test(groups = { "Regression", "MiniRegression", "ExhaustiveRegression", "RFPFOX7",  "RFPPROD" }, 
			dataProvider = "getABTestDP", description ="Verify success status nodes in response for Get All A/B Test variants API")
	public void ABService_getABTestVariants_vStatusNodes(String testName, 
			String statusCode, String successMessage, String successStatus)	{

		RequestGenerator req = getQueryRequest(APINAME.GETALLABTESTVARIANTS, testName);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertTrue("Inval ABtest variants API status nodes.", 
				req.respvalidate.DoesNodesExists(abTestsStatusNodes()));
	}

	/**
	 * Get all ABTest variants
	 * @author jhansi.bai
	 */
	@Test(groups = {"MiniRegression",  "Regression", "MiniRegression", "ExhaustiveRegression", "RFPFOX7",  "RFPPROD"},
			dataProvider = "getABTestDP",description ="Verify success status message in response for Get All A/B Test Variants API")
	public void ABService_getABTestVariants_vSuccStatusMsg(String testName,
			String statusCode, String successMessage, String successStatus)	{
		RequestGenerator req = getQueryRequest(APINAME.GETALLABTESTVARIANTS, testName);
		AssertJUnit.assertEquals("Status code does't match", req.respvalidate.NodeValue(abTestsStatusNodes().get(0), true), statusCode);			
		AssertJUnit.assertEquals("Status message dosn't match",	req.respvalidate.NodeValue(abTestsStatusNodes().get(1), true), 
				successMessage);		 
		AssertJUnit.assertEquals("Status type dosn't match", req.respvalidate.NodeValue(abTestsStatusNodes().get(2), true), successStatus);
		AssertJUnit.assertEquals("Total count dosn't match", req.respvalidate.NodeValue(abTestsStatusNodes().get(3), true),  
				req.respvalidate.GetArraySize("data")+"");
		log.info(req.respvalidate.returnresponseasstring());
	}

	/**
	 * Get all ABTest variants
	 * @author jhansi.bai
	 */
	@Test(groups = {"Sanity",  "Regression", "MiniRegression", "ExhaustiveRegression", "RFPFOX7"},
			dataProvider = "getABTestDP_negative1",description ="Verify 200 response for Get All A/B Test Variants API for set of invalid values")
	public void ABService_getABTestVariants_negative(String testName,
			String errStatusCode, String errorMessage, String errorStatus, String itemCount)	{
		RequestGenerator req = getQueryRequest(APINAME.GETALLABTESTVARIANTS, testName);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("Get AB Test variants service is not working", 200,
				req.response.getStatus());
	}

	/**
	 * Get all ABTest variants
	 * @author jhansi.bai
	 */
	@Test(groups = {"MiniRegression",  "Regression", "MiniRegression", "ExhaustiveRegression", "RFPFOX7"},
			dataProvider = "getABTestDP_negative1",description ="Verify status nodes for Get All A/B Test Variants API for set of invalid values")
	public void ABService_getABTestVariants_vNegativeStatusNodes(String testName,
			String errStatusCode, String errorMessage, String errorStatus, String itemCount)	{
		RequestGenerator req = getQueryRequest(APINAME.GETALLABTESTVARIANTS, testName);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertTrue("Inval ABtest variants API status nodes.", 
				req.respvalidate.DoesNodesExists(abTestsStatusNodes()));
	}

	/**
	 * Get all ABTest variants
	 * @author jhansi.bai
	 */
	@Test(groups = {"MiniRegression",  "Regression", "MiniRegression", "ExhaustiveRegression", "RFPFOX7"},
			dataProvider = "getABTestDP_negative1",description ="Verify status message for Get All A/B Test Variants API for set of invalid values")
	public void ABService_getABTestVariants_vNegativeSuccStatusMsg(String testName,
			String errStatusCode, String errorMessage, String errorStatus, String itemCount)	{
		RequestGenerator req = getQueryRequest(APINAME.GETALLABTESTVARIANTS, testName);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("Status code does't match", req.respvalidate.NodeValue(abTestsStatusNodes().get(0), true), errStatusCode);			
		AssertJUnit.assertEquals("Status message dosn't match",	req.respvalidate.NodeValue(abTestsStatusNodes().get(1), true), 
				errorMessage);		 
		AssertJUnit.assertEquals("Status type dosn't match", req.respvalidate.NodeValue(abTestsStatusNodes().get(2), true), errorStatus);
		AssertJUnit.assertEquals("Total count dosn't match", req.respvalidate.NodeValue(abTestsStatusNodes().get(3), true), itemCount);
		log.info(req.respvalidate.returnresponseasstring());
	}

	/**
	 * Get all ABTest variants
	 * @author jhansi.bai
	 */
	@Test(groups = {"Sanity",  "Regression", "MiniRegression", "ExhaustiveRegression", "RFPFOX7"},
			dataProvider = "getABTestDP_negative2",description ="Verify 500 response for Get All A/B Test Variants API for set of invalid values")
	public void ABService_getABTestVariants_negative2(String testName)	{
		RequestGenerator req = getQueryRequest(APINAME.GETALLABTESTVARIANTS, testName);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("Get AB Test variants service is not working", 500,
				req.response.getStatus());
	}
	/*
	 *//**
	 * Create a new ABTest.
	 * @author jhansi.bai
	 *//*
	@Test(groups = {"Sanity", 
	"Regression", "MiniRegression", "ExhaustiveRegression", "RFPFOX7" }, dataProvider = "createNewABTestDP")
	public void ABService_createNewABTest_StatusAdNodesAdVals(String variation_name, String percentProbability, String inlineHtml, String finalVariant,
			String jsFile, String cssFile, String configJson, String algoConfigJson, String auditLogs, String name,
			String isEnabled, String gaSlot, String omniSlot, String filters, String segmentationAlgo, String sourceType, 
			String isCompleted,	String apiVersion) {
		RequestGenerator req = getRequest(APINAME.CREATENEWABTEST, variation_name, percentProbability, inlineHtml, finalVariant, 
				jsFile,	cssFile, configJson, algoConfigJson, auditLogs, name, isEnabled, gaSlot, omniSlot, filters, segmentationAlgo,
				sourceType, isCompleted, apiVersion);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("createNewABTest is not working", 200,	req.response.getStatus());

		//Nodes verifiation
		AssertJUnit.assertTrue("Inval ABtest API status nodes.", 
				req.respvalidate.DoesNodesExists(abTestsStatusNodes()));
		AssertJUnit.assertTrue("Inval ABtest API data nodes.", 
				req.respvalidate.DoesNodesExists(abTestsNodes()));
		AssertJUnit.assertTrue("Inval ABtest API variant nodes.", 
				req.respvalidate.DoesNodesExists(abTestsVariantNodes()));

		//Node values verification

		AssertJUnit.assertEquals("Status code does't match", req.respvalidate.NodeValue(abTestsStatusNodes().get(0), true), "15001");			
		AssertJUnit.assertEquals("Status message dosn't match",	req.respvalidate.NodeValue(abTestsStatusNodes().get(1), true), 
				"\"ABTest added successfully\"");		 
		AssertJUnit.assertEquals("Status type dosn't match", req.respvalidate.NodeValue(abTestsStatusNodes().get(2), true), "\"SUCCESS\"");
		AssertJUnit.assertEquals("Total count dosn't match", req.respvalidate.NodeValue(abTestsStatusNodes().get(3), true),  
				req.respvalidate.GetArraySize("data")+"");
		log.info(req.respvalidate.returnresponseasstring());
	}*/

	/**
	 * Create a new ABTest.
	 * @author jhansi.bai
	 */
	@Test(groups = { "Sanity", 
			"Regression", "MiniRegression", "ExhaustiveRegression", "RFPFOX7" }, dataProvider = "createNewABTestDP_Duplicate_negative",description ="Verify 200 response for Create New A/B Test API for duplicate values")
	public void ABService_createNewABTest_duplicateNegative(String variation_name, String percentProbability, String inlineHtml, String finalVariant,
			String jsFile, String cssFile, String configJson, String algoConfigJson, String auditLogs, String name,
			String isEnabled, String gaSlot, String omniSlot, String filters, String segmentationAlgo, String sourceType, 
			String isCompleted,	String apiVersion) {
		RequestGenerator req = getRequest(APINAME.CREATENEWABTEST, variation_name, percentProbability, inlineHtml, finalVariant, 
				jsFile,	cssFile, configJson, algoConfigJson, auditLogs, name, isEnabled, gaSlot, omniSlot, filters, segmentationAlgo,
				sourceType, isCompleted, apiVersion);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("createNewABTest is not working", 200,	req.response.getStatus());
	}

	/**
	 * Create a new ABTest.
	 * @author jhansi.bai
	 */
	@Test(groups = {"Regression", "MiniRegression", "ExhaustiveRegression", "RFPFOX7" }, 
			dataProvider = "createNewABTestDP_Duplicate_negative",description ="Verify status nodes for Create New A/B Test API for duplicate values")
	public void ABService_createNewABTest_vDuplicateNegativeStatusNodes(String variation_name, String percentProbability, String inlineHtml, String finalVariant,
			String jsFile, String cssFile, String configJson, String algoConfigJson, String auditLogs, String name,
			String isEnabled, String gaSlot, String omniSlot, String filters, String segmentationAlgo, String sourceType, 
			String isCompleted,	String apiVersion) {
		RequestGenerator req = getRequest(APINAME.CREATENEWABTEST, variation_name, percentProbability, inlineHtml, finalVariant, 
				jsFile,	cssFile, configJson, algoConfigJson, auditLogs, name, isEnabled, gaSlot, omniSlot, filters, segmentationAlgo,
				sourceType, isCompleted, apiVersion);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertTrue("Inval ABtest API status nodes.", 
				req.respvalidate.DoesNodesExists(abTestsStatusNodes()));
	}

	/**
	 * Create a new ABTest.
	 * @author jhansi.bai
	 */
	@Test(groups = {"Regression", "MiniRegression", "ExhaustiveRegression", "RFPFOX7" }, 
			dataProvider = "createNewABTestDP_Duplicate_negative",description ="Verify status message for Create New A/B Test API for duplicate values")
	public void ABService_createNewABTest_vDuplicateNegativeSuccStatusMsg(String variation_name, String percentProbability, String inlineHtml, String finalVariant,
			String jsFile, String cssFile, String configJson, String algoConfigJson, String auditLogs, String name,
			String isEnabled, String gaSlot, String omniSlot, String filters, String segmentationAlgo, String sourceType, 
			String isCompleted,	String apiVersion) {
		RequestGenerator req = getRequest(APINAME.CREATENEWABTEST, variation_name, percentProbability, inlineHtml, finalVariant, 
				jsFile,	cssFile, configJson, algoConfigJson, auditLogs, name, isEnabled, gaSlot, omniSlot, filters, segmentationAlgo,
				sourceType, isCompleted, apiVersion);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("Status code does't match", req.respvalidate.NodeValue(abTestsStatusNodes().get(0), true), "54");			
		AssertJUnit.assertEquals("Status message dosn't match",	req.respvalidate.NodeValue(abTestsStatusNodes().get(1), true), 
				"\"Duplicate entry '"+name+"' for key 'name_2'\"");		 
		AssertJUnit.assertEquals("Status type dosn't match", req.respvalidate.NodeValue(abTestsStatusNodes().get(2), true), "\"ERROR\"");
		AssertJUnit.assertEquals("Total count dosn't match", req.respvalidate.NodeValue(abTestsStatusNodes().get(3), true), "0");
		log.info(req.respvalidate.returnresponseasstring());
	}

	/**
	 * Create a new ABTest.
	 * @author jhansi.bai
	 */
	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression", "RFPFOX7" }, 
			dataProvider = "createNewABTestDP_negative",description ="Verify 200 response for Create New A/B Test API for set of invalid values")
	public void ABService_createNewABTest_negative(String variation_name, String percentProbability, String inlineHtml, String finalVariant,
			String jsFile, String cssFile, String configJson, String algoConfigJson, String auditLogs, String name,
			String isEnabled, String gaSlot, String omniSlot, String filters, String segmentationAlgo, String sourceType, 
			String isCompleted,	String apiVersion) {
		RequestGenerator req = getRequest(APINAME.CREATENEWABTEST, variation_name, percentProbability, inlineHtml, finalVariant, 
				jsFile,	cssFile, configJson, algoConfigJson, auditLogs, name, isEnabled, gaSlot, omniSlot, filters, segmentationAlgo,
				sourceType, isCompleted, apiVersion);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("createNewABTest is not working", 200,	req.response.getStatus());
	}

	/**
	 * Create a new ABTest.
	 * @author jhansi.bai
	 */
	@Test(groups = {"Regression", "MiniRegression", "ExhaustiveRegression", "RFPFOX7" }, 
			dataProvider = "createNewABTestDP_negative",description ="Verify status nodes for Create New A/B Test API for set of invalid values")
	public void ABService_createNewABTest_vNegativeStatusNodes(String variation_name, String percentProbability, String inlineHtml, String finalVariant,
			String jsFile, String cssFile, String configJson, String algoConfigJson, String auditLogs, String name,
			String isEnabled, String gaSlot, String omniSlot, String filters, String segmentationAlgo, String sourceType, 
			String isCompleted,	String apiVersion) {
		RequestGenerator req = getRequest(APINAME.CREATENEWABTEST, variation_name, percentProbability, inlineHtml, finalVariant, 
				jsFile,	cssFile, configJson, algoConfigJson, auditLogs, name, isEnabled, gaSlot, omniSlot, filters, segmentationAlgo,
				sourceType, isCompleted, apiVersion);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertTrue("Inval ABtest API status nodes.", 
				req.respvalidate.DoesNodesExists(abTestsStatusNodes()));
	}

	/**
	 * Create a new ABTest.
	 * @author jhansi.bai
	 */
	@Test(groups = {"Regression", "MiniRegression", "ExhaustiveRegression", "RFPFOX7" }, 
			dataProvider = "createNewABTestDP_negative",description ="Verify status message for Create New A/B Test API for set of invalid values")
	public void ABService_createNewABTest_vNegativeSuccStatusMsg(String variation_name, String percentProbability, String inlineHtml,
			String finalVariant, String jsFile, String cssFile, String configJson, String algoConfigJson, String auditLogs, String name,
			String isEnabled, String gaSlot, String omniSlot, String filters, String segmentationAlgo, String sourceType, 
			String isCompleted,	String apiVersion) {
		RequestGenerator req = getRequest(APINAME.CREATENEWABTEST, variation_name, percentProbability, inlineHtml, finalVariant, 
				jsFile,	cssFile, configJson, algoConfigJson, auditLogs, name, isEnabled, gaSlot, omniSlot, filters, segmentationAlgo,
				sourceType, isCompleted, apiVersion);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("Status code does't match", req.respvalidate.NodeValue(abTestsStatusNodes().get(0), true), "10001");			
		AssertJUnit.assertEquals("Status message dosn't match",	req.respvalidate.NodeValue(abTestsStatusNodes().get(1), true), 
				"\"Empty name is not allowed\"");		 
		AssertJUnit.assertEquals("Status type dosn't match", req.respvalidate.NodeValue(abTestsStatusNodes().get(2), true), "\"ERROR\"");
		AssertJUnit.assertEquals("Total count dosn't match", req.respvalidate.NodeValue(abTestsStatusNodes().get(3), true), "0");
		log.info(req.respvalidate.returnresponseasstring());
	}

	/**
	 * Update an existing ABTest: 
	 * @author jhansi.bai
	 */
	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression", "RFPFOX7" }, 
			dataProvider = "updateExistingABTestDP",description ="Verify 200 response for Update exising A/B Test APIs")
	public void ABService_updateExistingABTest(String variation_name,	String percentProbability, String inlineHtml, String finalVariant,
			String name, String percentProbability1, String inlineHtml1, String finalVariant1, String auditLogs, String name2,
			String isEnabled, String gaSlot, String omniSlot, String filters, String segmentationAlgo, String sourceType, String isCompleted,
			String apiVersion) {

		RequestGenerator req = getRequest(APINAME.UPDATEEXISTINGABTEST, variation_name, percentProbability,
				inlineHtml, finalVariant, name, percentProbability1, inlineHtml1, finalVariant1, auditLogs, name2,
				isEnabled, gaSlot, omniSlot, filters, segmentationAlgo,	sourceType, isCompleted, apiVersion);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("updateExistingABTest is not working", 200,
				req.response.getStatus());
	}

	/**
	 * Update an existing ABTest: 
	 * @author jhansi.bai
	 */
	@Test(groups = {"Regression", "MiniRegression", "ExhaustiveRegression", "RFPFOX7" }, dataProvider = "updateExistingABTestDP",description ="Verify existance of status data nodes for Update exising A/B Test API")
	public void ABService_updateExistingABTest_vStatusAdDataNodes(String variation_name,	String percentProbability, String inlineHtml, String finalVariant,
			String name, String percentProbability1, String inlineHtml1, String finalVariant1, String auditLogs, String name2,
			String isEnabled, String gaSlot, String omniSlot, String filters, String segmentationAlgo, String sourceType, String isCompleted,
			String apiVersion) {

		RequestGenerator req = getRequest(APINAME.UPDATEEXISTINGABTEST, variation_name, percentProbability,
				inlineHtml, finalVariant, name, percentProbability1, inlineHtml1, finalVariant1, auditLogs, name2,
				isEnabled, gaSlot, omniSlot, filters, segmentationAlgo,	sourceType, isCompleted, apiVersion);
		System.out.println(req.respvalidate.returnresponseasstring());
		//Nodes verifiation
		AssertJUnit.assertTrue("Inval ABtest API status nodes.", 
				req.respvalidate.DoesNodesExists(abTestsStatusNodes()));
		AssertJUnit.assertTrue("Inval ABtest API data nodes.", 
				req.respvalidate.DoesNodesExists(abTestsDataNodes()));
		AssertJUnit.assertTrue("Inval ABtest API variant nodes.", 
				req.respvalidate.DoesNodesExists(abTestsDataUpdateVariantNodes()));
	}

	/**
	 * Update an existing ABTest: 
	 * @author jhansi.bai
	 */
	@Test(groups = {"Sanity", "Regression", "MiniRegression", "ExhaustiveRegression", "RFPFOX7" }, 
			dataProvider = "updateExistingABTestDP",description ="Verify status and data nodes values for Update A/B Test APIs")
	public void ABService_updateExistingABTest_vStatusAdDataNodesVals(String variation_name,	String percentProbability, String inlineHtml, String finalVariant,
			String name, String percentProbability1, String inlineHtml1, String finalVariant1, String auditLogs, String name2,
			String isEnabled, String gaSlot, String omniSlot, String filters, String segmentationAlgo, String sourceType, String isCompleted,
			String apiVersion) {

		RequestGenerator req = getRequest(APINAME.UPDATEEXISTINGABTEST, variation_name, percentProbability,
				inlineHtml, finalVariant, name, percentProbability1, inlineHtml1, finalVariant1, auditLogs, name2,
				isEnabled, gaSlot, omniSlot, filters, segmentationAlgo,	sourceType, isCompleted, apiVersion);
		System.out.println(req.respvalidate.returnresponseasstring());

		//Node values verification
		AssertJUnit.assertEquals("Status code does't match", req.respvalidate.NodeValue(abTestsStatusNodes().get(0), true), "15003");			
		AssertJUnit.assertEquals("Status message dosn't match",	req.respvalidate.NodeValue(abTestsStatusNodes().get(1), true), 
				"\"ABTest updated successfully\"");		 
		AssertJUnit.assertEquals("Status type dosn't match", req.respvalidate.NodeValue(abTestsStatusNodes().get(2), true), "\"SUCCESS\"");
		AssertJUnit.assertEquals("Total count dosn't match", req.respvalidate.NodeValue(abTestsStatusNodes().get(3), true),  
				req.respvalidate.GetArraySize("data")+"");
		//Updated data verification
		AssertJUnit.assertEquals("auditLogs does't match", req.respvalidate.NodeValue(abTestsDataNodes().get(1), true), auditLogs);			
		AssertJUnit.assertEquals("name2 does't match", req.respvalidate.NodeValue(abTestsDataNodes().get(2), true), "\""+name2+"\"");			
		AssertJUnit.assertEquals("isEnabled does't match", req.respvalidate.NodeValue(abTestsDataNodes().get(3), true), isEnabled);			
		AssertJUnit.assertEquals("gaSlot does't match", req.respvalidate.NodeValue(abTestsDataNodes().get(4), true), gaSlot);			
		AssertJUnit.assertEquals("omniSlot does't match", req.respvalidate.NodeValue(abTestsDataNodes().get(5), true), omniSlot);			
		AssertJUnit.assertEquals("filters does't match", req.respvalidate.NodeValue(abTestsDataNodes().get(6), true), "\""+filters+"\"");			
		AssertJUnit.assertEquals("segmentationAlgo does't match", req.respvalidate.NodeValue(abTestsDataNodes().get(7), true), "\""+segmentationAlgo+"\"");			
		AssertJUnit.assertEquals("sourceType does't match", req.respvalidate.NodeValue(abTestsDataNodes().get(8), true), "\""+sourceType+"\"");			
		AssertJUnit.assertEquals("isCompleted does't match", req.respvalidate.NodeValue(abTestsDataNodes().get(9), true), isCompleted);			
		AssertJUnit.assertEquals("apiVersion does't match", req.respvalidate.NodeValue(abTestsDataNodes().get(10), true), apiVersion);	
	}			

	/**
	 * Update an existing ABTest: 
	 * @author jhansi.bai
	 */
	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression", "RFPFOX7" }, 
			dataProvider = "updateExistingABTestDP_negative",description ="Verify 200 response Update exising A/B Test API for neagtive set of values")
	public void ABService_updateExistingABTest_negative(String variation_name,	String percentProbability, String inlineHtml, String finalVariant,
			String name, String percentProbability1, String inlineHtml1, String finalVariant1, String auditLogs, String name2,
			String isEnabled, String gaSlot, String omniSlot, String filters, String segmentationAlgo, String sourceType, String isCompleted,
			String apiVersion) {

		RequestGenerator req = getRequest(APINAME.UPDATEEXISTINGABTEST, variation_name, percentProbability,
				inlineHtml, finalVariant, name, percentProbability1, inlineHtml1, finalVariant1, auditLogs, name2,
				isEnabled, gaSlot, omniSlot, filters, segmentationAlgo,	sourceType, isCompleted, apiVersion);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("updateExistingABTest is not working", 200,
				req.response.getStatus());
	}

	/**
	 * Update an existing ABTest: 
	 * @author jhansi.bai
	 */
	@Test(groups = {"MiniRegression", "Regression", "MiniRegression", "ExhaustiveRegression", "RFPFOX7" }, 
			dataProvider = "updateExistingABTestDP_negative",description ="Verify status nodes for Update exising A/B Test API for set of invalid values")
	public void ABService_updateExistingABTest_vNegativeSuccStatusNodes(String variation_name,	String percentProbability, String inlineHtml, String finalVariant,
			String name, String percentProbability1, String inlineHtml1, String finalVariant1, String auditLogs, String name2,
			String isEnabled, String gaSlot, String omniSlot, String filters, String segmentationAlgo, String sourceType, String isCompleted,
			String apiVersion) {

		RequestGenerator req = getRequest(APINAME.UPDATEEXISTINGABTEST, variation_name, percentProbability,
				inlineHtml, finalVariant, name, percentProbability1, inlineHtml1, finalVariant1, auditLogs, name2,
				isEnabled, gaSlot, omniSlot, filters, segmentationAlgo,	sourceType, isCompleted, apiVersion);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertTrue("Inval ABtest API status nodes.", 
				req.respvalidate.DoesNodesExists(abTestsStatusNodes()));
	}

	/**
	 * Update an existing ABTest: 
	 * @author jhansi.bai
	 */
	@Test(groups = {"MiniRegression", "Regression", "MiniRegression", "ExhaustiveRegression", "RFPFOX7" }, 
			dataProvider = "updateExistingABTestDP_negative",description ="Verify status message for Update exising A/B Test API for set of invalid values")
	public void ABService_updateExistingABTest_vNegativeSuccStatusMsg(String variation_name,	String percentProbability, String inlineHtml, String finalVariant,
			String name, String percentProbability1, String inlineHtml1, String finalVariant1, String auditLogs, String name2,
			String isEnabled, String gaSlot, String omniSlot, String filters, String segmentationAlgo, String sourceType, String isCompleted,
			String apiVersion) {

		RequestGenerator req = getRequest(APINAME.UPDATEEXISTINGABTEST, variation_name, percentProbability,
				inlineHtml, finalVariant, name, percentProbability1, inlineHtml1, finalVariant1, auditLogs, name2,
				isEnabled, gaSlot, omniSlot, filters, segmentationAlgo,	sourceType, isCompleted, apiVersion);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("Status code does't match", req.respvalidate.NodeValue(abTestsStatusNodes().get(0), true), "10008");			
		AssertJUnit.assertEquals("Status message dosn't match",	req.respvalidate.NodeValue(abTestsStatusNodes().get(1), true), 
				"\"ABTest not found\"");		 
		AssertJUnit.assertEquals("Status type dosn't match", req.respvalidate.NodeValue(abTestsStatusNodes().get(2), true), "\"ERROR\"");
		AssertJUnit.assertEquals("Total count dosn't match", req.respvalidate.NodeValue(abTestsStatusNodes().get(3), true), "0");
		log.info(req.respvalidate.returnresponseasstring());
	}

	/**
	 * Get variant of a given ABTest: 
	 * @author jhansi.bai
	 */
	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression", "RFPFOX7" }, 
			dataProvider = "getVariantOfGivenABTestDP",description ="Verify 200 response for get varaints of given  A/B Test API ")
	public void ABService_getVariantOfGivenABTest(String ABTestName, String statusCode, 
			String successMessage, String successStatus, String itemCount) {

		RequestGenerator req = getQuerryPayLoadRequest(APINAME.GETVARIANTOFGIVENABTEST, ABTestName);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("getVariantOfGivenABTest is not working", 200,
				req.response.getStatus());
	}

	/**
	 * Get variant of a given ABTest: 
	 * @author jhansi.bai
	 */
	@Test(groups = {"Regression", "MiniRegression", "ExhaustiveRegression", "RFPFOX7" }, dataProvider = "getVariantOfGivenABTestDP",description ="Verify status and data nodes for get varaints of given  A/B Test API ")
	public void ABService_getVariantOfGivenABTest_vStatusAdDataNodes(String ABTestName,  String statusCode, 
			String successMessage, String successStatus, String itemCount) {

		RequestGenerator req = getQuerryPayLoadRequest(APINAME.GETVARIANTOFGIVENABTEST, ABTestName);
		String resp = req.respvalidate.returnresponseasstring();
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertTrue("Inval ABtest variants API main nodes.", 
				req.respvalidate.DoesNodesExists(abTestsMainNodes()));
		AssertJUnit.assertTrue("Inval ABtest variants API status nodes.", 
				req.respvalidate.DoesNodesExists(abTestsStatusNodes()));
		//verify data nodes
		List<JSONObject> myJsonList = JsonPath.read(resp, "$.data[*]");
		System.out.println(myJsonList.size());
		System.out.println(myJsonList);

		for(int i=0; i<myJsonList.size(); i++){
			String response = myJsonList.get(i).toJSONString();
			for(String nodePath : abTestsVariantsDataNodes()){
				AssertJUnit.assertTrue(nodePath+" is not exists", apiUtil.Exists(nodePath, response));
			}
		}
	}

	/**
	 * Get variant of a given ABTest: 
	 * @author jhansi.bai
	 */
	@Test(groups = {"Sanity", "Regression", "MiniRegression", "ExhaustiveRegression", "RFPFOX7" }, 
			dataProvider = "getVariantOfGivenABTestDP",description ="Verify values of status and data nodes for get varaints of given  A/B Test API ")
	public void ABService_getVariantOfGivenABTest_vStatusAdDataNodesVals(String ABTestName,  String statusCode, 
			String successMessage, String successStatus, String itemCount) {

		RequestGenerator req = getQuerryPayLoadRequest(APINAME.GETVARIANTOFGIVENABTEST, ABTestName);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("Status code does't match", req.respvalidate.NodeValue(abTestsStatusNodes().get(0), true), 
				statusCode);			
		AssertJUnit.assertEquals("Status message dosn't match",	req.respvalidate.NodeValue(abTestsStatusNodes().get(1), true), 
				successMessage);		 
		AssertJUnit.assertEquals("Status type dosn't match", req.respvalidate.NodeValue(abTestsStatusNodes().get(2), true), 
				successStatus);
		AssertJUnit.assertEquals("itemcount dosn't match", req.respvalidate.NodeValue(abTestsStatusNodes().get(3), true), 
				itemCount);
		AssertJUnit.assertTrue(req.respvalidate.NodeValue(abTestsMainNodes().get(1), true).contains(ABTestName));
		AssertJUnit.assertTrue(req.respvalidate.NodeValue(abTestsMainNodes().get(2), true).contains(ABTestName));
	}

	/**
	 * Get variant of a given ABTest: 
	 * @author jhansi.bai
	 */
	@Test(groups = { /* "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression", "RFPFOX7" */ }, 
			dataProvider = "getVariantOfGivenABTestDP_negative",description ="Verify 200 resonse for get variants of given A/B Test API for set of invalid values")
	public void ABService_getVariantOfGivenABTest_negative(String ABTestName, String errStatusCode, String errorMessage, 
			String errorStatus, String itemCount) {

		RequestGenerator req = getQuerryPayLoadRequest(APINAME.GETVARIANTOFGIVENABTEST, ABTestName);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("getVariantOfGivenABTest is not working", 200,
				req.response.getStatus());
	}	

	/**
	 * Get variant of a given ABTest: 
	 * @author jhansi.bai
	 */
	@Test(groups = { /* "Regression", "MiniRegression", "ExhaustiveRegression", "RFPFOX7"  */ }, 
			dataProvider = "getVariantOfGivenABTestDP_negative",description ="Verify status nodes for get variants of given A/B Test API for set of invalid values")
	public void ABService_getVariantOfGivenABTest_vNegativeStatusNodes(String ABTestName, String errStatusCode, String errorMessage, 
			String errorStatus, String itemCount) {

		RequestGenerator req = getQuerryPayLoadRequest(APINAME.GETVARIANTOFGIVENABTEST, ABTestName);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertTrue("Inval ABtest variants API status nodes.", 
				req.respvalidate.DoesNodesExists(abTestsStatusNodes()));
		log.info(req.respvalidate.returnresponseasstring());
	}
	/**
	 * Get variant of a given ABTest: 
	 * @author jhansi.bai
	 */
	@Test(groups = { /* "Regression", "MiniRegression", "ExhaustiveRegression", "RFPFOX7" */ }, 
			dataProvider = "getVariantOfGivenABTestDP_negative",description ="Verify status node values for get variants of given A/B Test API for set of invalid values")
	public void ABService_getVariantOfGivenABTest_vNegativeStatusNodesVals(String ABTestName, String errStatusCode, String errorMessage, 
			String errorStatus, String itemCount) {

		RequestGenerator req = getQuerryPayLoadRequest(APINAME.GETVARIANTOFGIVENABTEST, ABTestName);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("Status code does't match", req.respvalidate.NodeValue(abTestsStatusNodes().get(0), true), 
				errStatusCode);			
		AssertJUnit.assertEquals("Status message dosn't match",	req.respvalidate.NodeValue(abTestsStatusNodes().get(1), true), 
				errorMessage);		 
		AssertJUnit.assertEquals("Status type dosn't match", req.respvalidate.NodeValue(abTestsStatusNodes().get(2), true), 
				errorStatus);
		AssertJUnit.assertEquals("Total count dosn't match", req.respvalidate.NodeValue(abTestsStatusNodes().get(3), true), 
				itemCount);
		log.info(req.respvalidate.returnresponseasstring());
	}

	/**
	 * Get variant of a given ABTest: 
	 * @author jhansi.bai
	 */
	@Test(groups = { /* "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression", "RFPFOX7" */ }, 
			dataProvider = "getVariantOfGivenABTestDP_negative1",description ="Verify 405 error response get variants of given A/B Test API for set of invalid values")
	public void ABService_getVariantOfGivenABTest_negative405Status(String ABTestName) {

		RequestGenerator req = getQuerryPayLoadRequest(APINAME.GETVARIANTOFGIVENABTEST, ABTestName);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("getVariantOfGivenABTest is not working", 405,
				req.response.getStatus());
	}

	/**
	 * Get variant of a given ABTest with filter: 
	 * @author jhansi.bai
	 */
	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression", "RFPFOX7" }, 
			dataProvider = "getVariantOfGivenABTestWithFilterDP",description ="Verify 200 response for get variants of given A/B Test API with filter")
	public void ABService_getVariantOfGivenABTestWithFilter(String userId, String ABTestName,  String statusCode, 
			String successMessage, String successStatus, String itemCount) {
		RequestGenerator req = getQuerryPayLoadRequest(APINAME.GETVARIANTOFGIVENABTESTWITHFILTER, userId, ABTestName);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("getVariantOfGivenABTest is not working", 200,
				req.response.getStatus());
	}

	/**
	 * Get variant of a given ABTest with filter: 
	 * @author jhansi.bai
	 */
	@Test(groups = {"Regression", "MiniRegression", "ExhaustiveRegression", "RFPFOX7" }, 
			dataProvider = "getVariantOfGivenABTestWithFilterDP",description ="Verify status and data nodes for get variants of given A/B Test API with filter")
	public void ABService_getVariantOfGivenABTestWithFilter_vStatusAdDataNodes(String userId, String ABTestName,  String statusCode, 
			String successMessage, String successStatus, String itemCount) {
		RequestGenerator req = getQuerryPayLoadRequest(APINAME.GETVARIANTOFGIVENABTESTWITHFILTER, userId, ABTestName);
		String resp = req.respvalidate.returnresponseasstring();
		System.out.println(resp);
		AssertJUnit.assertTrue("Inval ABtest variants API main nodes.", 
				req.respvalidate.DoesNodesExists(abTestsMainNodes()));
		AssertJUnit.assertTrue("Inval ABtest variants API status nodes.", 
				req.respvalidate.DoesNodesExists(abTestsStatusNodes()));

		//verify data nodes
		List<JSONObject> myJsonList = JsonPath.read(resp, "$.data[*]");
		for(int i=0; i<myJsonList.size(); i++){
			String response = myJsonList.get(i).toJSONString();
			for(String nodePath : abTestsVariantsDataNodes()){
				AssertJUnit.assertTrue(nodePath+" is not exists", apiUtil.Exists(nodePath, response));
			}
		}
	}

	/**
	 * Get variant of a given ABTest with filter: 
	 * @author jhansi.bai
	 */
	@Test(groups = {"Sanity", "Regression", "MiniRegression", "ExhaustiveRegression", "RFPFOX7" }, 
			dataProvider = "getVariantOfGivenABTestWithFilterDP",description ="Verify status and data node values for get variants of given A/B Test API with filter")
	public void ABService_getVariantOfGivenABTestWithFilter_vStatusAdDataNodesVals(String userId, String ABTestName,  String statusCode, 
			String successMessage, String successStatus, String itemCount) {
		RequestGenerator req = getQuerryPayLoadRequest(APINAME.GETVARIANTOFGIVENABTESTWITHFILTER, userId, ABTestName);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("Status code does't match", req.respvalidate.NodeValue(abTestsStatusNodes().get(0), true), 
				statusCode);			
		AssertJUnit.assertEquals("Status message dosn't match",	req.respvalidate.NodeValue(abTestsStatusNodes().get(1), true), 
				successMessage);		 
		AssertJUnit.assertEquals("Status type dosn't match", req.respvalidate.NodeValue(abTestsStatusNodes().get(2), true), 
				successStatus);
		AssertJUnit.assertEquals("itemcount dosn't match", req.respvalidate.NodeValue(abTestsStatusNodes().get(3), true), 
				itemCount);
		AssertJUnit.assertTrue(req.respvalidate.NodeValue(abTestsMainNodes().get(1), true).contains(ABTestName));
		AssertJUnit.assertTrue(req.respvalidate.NodeValue(abTestsMainNodes().get(2), true).contains(ABTestName));
	}

	/**
	 * Get variant of a given ABTest  with filter: 
	 * @author jhansi.bai
	 */
	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression", "RFPFOX7" }, 
			dataProvider = "getVariantOfGivenABTestWithFilterDP_negative",description ="Verify 204 response for get variants of given A/B Test API with filter")
	public void ABService_getVariantOfGivenABTestWithFilter_negative(String userId, String ABTestName) {

		RequestGenerator req = getQuerryPayLoadRequest(APINAME.GETVARIANTOFGIVENABTESTWITHFILTER, userId, ABTestName);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("getVariantOfGivenABTest is not working", 204,
				req.response.getStatus());
	}	

	/**
	 * Get variant of a given ABTest  with filter: 
	 * @author jhansi.bai
	 */
	@Test(groups = {"Sanity", "Regression", "MiniRegression", "ExhaustiveRegression", "RFPFOX7" }, 
			dataProvider = "getVariantOfGivenABTestWithFilterDP_negative1",description ="Verify 405 response for get variants of given A/B Test API with filter")
	public void ABService_getVariantOfGivenABTestWithFilter_negative405Status(String userId, String ABTestName) {

		RequestGenerator req = getQuerryPayLoadRequest(APINAME.GETVARIANTOFGIVENABTESTWITHFILTER, userId, ABTestName);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("getVariantOfGivenABTest is not working", 405,
				req.response.getStatus());
	}	

	/**
	 * Get variant of a given ABTest with segment: 
	 * @author jhansi.bai
	 */
	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression", "RFPFOX7" }, 
			dataProvider = "getvariantofgivenabtestwithsegmentDP",description ="Verify 200 response for get variants of given A/B Test with segment API")
	public void ABService_getVariantOfGivenABTestWithSegment(String xid, String ABTestName,  String statusCode, 
			String successMessage, String successStatus, String itemCount) {
		RequestGenerator req = getQuerryPayLoadRequest(APINAME.GETVARIANTOFGIVENABTESTWITHSEGMENT, xid, ABTestName);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("getVariantOfGivenABTest is not working", 200,
				req.response.getStatus());
	}

	/**
	 * Get variant of a given ABTest with segment: 
	 * @author jhansi.bai
	 */
	@Test(groups = {"Regression", "MiniRegression", "ExhaustiveRegression", "RFPFOX7" },
			dataProvider = "getvariantofgivenabtestwithsegmentDP",description ="Verify status and data nodes for get variants of given A/B Test with segment API")
	public void ABService_getVariantOfGivenABTestWithSegmentvStatusAdDataNodes(String xid, String ABTestName,  String statusCode, 
			String successMessage, String successStatus, String itemCount) {
		RequestGenerator req = getQuerryPayLoadRequest(APINAME.GETVARIANTOFGIVENABTESTWITHSEGMENT, xid, ABTestName);
		String resp = req.respvalidate.returnresponseasstring();
		System.out.println(resp);
		AssertJUnit.assertTrue("Inval ABtest variants API main nodes.", 
				req.respvalidate.DoesNodesExists(abTestsMainNodes()));
		AssertJUnit.assertTrue("Inval ABtest variants API status nodes.", 
				req.respvalidate.DoesNodesExists(abTestsStatusNodes()));

		//verify data nodes
		List<JSONObject> myJsonList = JsonPath.read(resp, "$.data[*]");
		for(int i=0; i<myJsonList.size(); i++){
			String response = myJsonList.get(i).toJSONString();
			for(String nodePath : abTestsVariantsDataNodes()){
				AssertJUnit.assertTrue(nodePath+" is not exists", apiUtil.Exists(nodePath, response));
			}
		}
	}

	/**
	 * Get variant of a given ABTest with segment: 
	 * @author jhansi.bai
	 */
	@Test(groups = {"Sanity", "Regression", "MiniRegression", "ExhaustiveRegression", "RFPFOX7" }, 
			dataProvider = "getvariantofgivenabtestwithsegmentDP",description ="Verify status and data node values for get variants of given A/B Test with segment API")
	public void ABService_getVariantOfGivenABTestWithSegmentvStatusAdDataNodesVals(String xid, String ABTestName,  String statusCode, 
			String successMessage, String successStatus, String itemCount) {
		RequestGenerator req = getQuerryPayLoadRequest(APINAME.GETVARIANTOFGIVENABTESTWITHSEGMENT, xid, ABTestName);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("Status code does't match", req.respvalidate.NodeValue(abTestsStatusNodes().get(0), true), 
				statusCode);			
		AssertJUnit.assertEquals("Status message dosn't match",	req.respvalidate.NodeValue(abTestsStatusNodes().get(1), true), 
				successMessage);		 
		AssertJUnit.assertEquals("Status type dosn't match", req.respvalidate.NodeValue(abTestsStatusNodes().get(2), true), 
				successStatus);
		AssertJUnit.assertEquals("itemcount dosn't match", req.respvalidate.NodeValue(abTestsStatusNodes().get(3), true), 
				itemCount);
		AssertJUnit.assertTrue(req.respvalidate.NodeValue(abTestsMainNodes().get(1), true).contains(ABTestName));
		AssertJUnit.assertTrue(req.respvalidate.NodeValue(abTestsMainNodes().get(2), true).contains(ABTestName));
	}

	/**
	 * Get variant of a given ABTest with segment: 
	 * @author jhansi.bai
	 */
	@Test(groups = { /* "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression", "RFPFOX7" */}, 
			dataProvider = "getvariantofgivenabtestwithsegmentDP_negative",description ="Verify 200 response for get variants of given A/B Test with segment API for negative values")
	public void ABService_getVariantOfGivenABTestWithSegment_negative(String xid, String ABTestName) {
		RequestGenerator req = getQuerryPayLoadRequest(APINAME.GETVARIANTOFGIVENABTESTWITHSEGMENT, xid, ABTestName);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("getVariantOfGivenABTest is not working", 200,
				req.response.getStatus());
	}

	/**
	 * Get variant of a given ABTest with segment: 
	 * @author jhansi.bai
	 */
	@Test(groups = {"Sanity", "Regression", "MiniRegression", "ExhaustiveRegression", "RFPFOX7" }, 
			dataProvider = "getvariantofgivenabtestwithsegmentDP_negative1",description ="Verify 405 response for get variants of given A/B Test with segment API")
	public void ABService_getVariantOfGivenABTestWithSegment_negative405Status(String xid, String ABTestName) {
		RequestGenerator req = getQuerryPayLoadRequest(APINAME.GETVARIANTOFGIVENABTESTWITHSEGMENT, xid, ABTestName);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("getVariantOfGivenABTest is not working", 405,
				req.response.getStatus());
	}

	/**
	 * Get variant of a given ABTest with abtest: 
	 * @author jhansi.bai
	 */
	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression", "RFPFOX7" }, 
			dataProvider = "getvariantofgivenabtestwithABtestDP",description ="Verify 200 response for get variants of given A/B Test with AB Test API")
	public void ABService_getVariantOfGivenABTestWithABtest(String userid, String xid, String ABTestName,  String statusCode, 
			String successMessage, String successStatus, String itemCount) {
		RequestGenerator req = getQuerryPayLoadRequest(APINAME.GETVARIANTOFGIVENABTESTWITHABTEST, userid, xid, ABTestName);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("getVariantOfGivenABTest is not working", 200,
				req.response.getStatus());
	}

	/**
	 * Get variant of a given ABTest with abtest: 
	 * @author jhansi.bai
	 */
	@Test(groups = {"Regression", "MiniRegression", "ExhaustiveRegression", "RFPFOX7" }, 
			dataProvider = "getvariantofgivenabtestwithABtestDP",description ="Verify status and data nodes for get variants of given A/B Test with AB Test API")
	public void ABService_getVariantOfGivenABTestWithABtest_vStatusAdDataNodes(String userid, String xid, String ABTestName,  String statusCode, 
			String successMessage, String successStatus, String itemCount) {
		RequestGenerator req = getQuerryPayLoadRequest(APINAME.GETVARIANTOFGIVENABTESTWITHABTEST, userid, xid, ABTestName);
		String resp = req.respvalidate.returnresponseasstring();
		System.out.println(resp);
		AssertJUnit.assertTrue("Inval ABtest variants API main nodes.", 
				req.respvalidate.DoesNodesExists(abTestsMainNodes()));
		AssertJUnit.assertTrue("Inval ABtest variants API status nodes.", 
				req.respvalidate.DoesNodesExists(abTestsStatusNodes()));

		//verify data nodes
		List<JSONObject> myJsonList = JsonPath.read(resp, "$.data[*]");
		for(int i=0; i<myJsonList.size(); i++){
			String response = myJsonList.get(i).toJSONString();
			for(String nodePath : abTestsVariantsDataNodes()){
				AssertJUnit.assertTrue(nodePath+" is not exists", apiUtil.Exists(nodePath, response));
			}
		}
	}

	/**
	 * Get variant of a given ABTest with abtest: 
	 * @author jhansi.bai
	 */
	@Test(groups = {"Sanity", "Regression", "MiniRegression", "ExhaustiveRegression", "RFPFOX7" }, 
			dataProvider = "getvariantofgivenabtestwithABtestDP",description ="Verify status and data node values for get variants of given A/B Test with AB Test API")
	public void ABService_getVariantOfGivenABTestWithABtest_vStatusAdDataNodesVals(String userid, String xid, String ABTestName,  String statusCode, 
			String successMessage, String successStatus, String itemCount) {
		RequestGenerator req = getQuerryPayLoadRequest(APINAME.GETVARIANTOFGIVENABTESTWITHABTEST, userid, xid, ABTestName);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("Status code does't match", req.respvalidate.NodeValue(abTestsStatusNodes().get(0), true), 
				statusCode);			
		AssertJUnit.assertEquals("Status message dosn't match",	req.respvalidate.NodeValue(abTestsStatusNodes().get(1), true), 
				successMessage);		 
		AssertJUnit.assertEquals("Status type dosn't match", req.respvalidate.NodeValue(abTestsStatusNodes().get(2), true), 
				successStatus);
		AssertJUnit.assertEquals("itemcount dosn't match", req.respvalidate.NodeValue(abTestsStatusNodes().get(3), true), 
				itemCount);
		AssertJUnit.assertTrue(req.respvalidate.NodeValue(abTestsMainNodes().get(1), true).contains(ABTestName));
		AssertJUnit.assertTrue(req.respvalidate.NodeValue(abTestsMainNodes().get(2), true).contains(ABTestName));
	}


	/**
	 * Get variant of a given ABTest with abtest: 
	 * @author jhansi.bai
	 */

	@Test(groups = { /* "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression", "RFPFOX7" */ }, 
			dataProvider = "getvariantofgivenabtestwithABtestDP_negative",description ="Verify 200 response for get variants of given A/B Test with AB Test API for set of negative values")
	public void ABService_getVariantOfGivenABTestWithABtest_negative(String userid, String xid, String ABTestName) {
		RequestGenerator req = getQuerryPayLoadRequest(APINAME.GETVARIANTOFGIVENABTESTWITHABTEST, userid, xid, ABTestName);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("getVariantOfGivenABTest is not working", 200,
				req.response.getStatus());
	}

	/**
	 * Get variant of a given ABTest with abtest: 
	 * @author jhansi.bai
	 */
	@Test(groups = {"Sanity", "Regression", "MiniRegression", "ExhaustiveRegression", "RFPFOX7" }, 
			dataProvider = "getvariantofgivenabtestwithABtestDP_negative1",description ="Verify 405 error response for get variants of given A/B Test with AB Test API for set of negative values")
	public void ABService_getVariantOfGivenABTestWithABtest_negative405Status(String userid, String xid, String ABTestName) {
		RequestGenerator req = getQuerryPayLoadRequest(APINAME.GETVARIANTOFGIVENABTESTWITHABTEST, userid, xid, ABTestName);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("getVariantOfGivenABTest is not working", 405,
				req.response.getStatus());
	}

	/**
	 * Get variants of all active ABTests: 
	 * @author jhansi.bai
	 */
	@Test(groups = {"Sanity", "Prodsanity", 
			"Regression", "MiniRegression", "ExhaustiveRegression", "RFPFOX7" },description ="Verify 200 response for get variants of all active A/B Test API")
	public void ABService_getVariantsOfAllActiveABTests() {

		RequestGenerator req = getRequest(APINAME.GETVARIANTSOFALLACTIVEABTEST);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("getVariantsOfAllActiveABTests is not working", 200,
				req.response.getStatus());
	}

	/**
	 * Get variants of all active ABTests: 
	 * @author jhansi.bai
	 */
	@Test(groups = {"Regression", "MiniRegression", "ExhaustiveRegression", "RFPFOX7" },description ="Verify status and data nodes for get variants of all active A/B Test API")
	public void ABService_getVariantsOfAllActiveABTests_vStatusAdDataNodes() {

		RequestGenerator req = getRequest(APINAME.GETVARIANTSOFALLACTIVEABTEST);
		String resp = req.respvalidate.returnresponseasstring();
		System.out.println(resp);
		AssertJUnit.assertTrue("Inval ABtest variants API main nodes.", 
				req.respvalidate.DoesNodesExists(abTestsMainNodes()));
		AssertJUnit.assertTrue("Inval ABtest variants API status nodes.", 
				req.respvalidate.DoesNodesExists(abTestsStatusNodes()));

		//verify data nodes
		List<JSONObject> myJsonList = JsonPath.read(resp, "$.data[*]");
		for(int i=0; i<myJsonList.size(); i++){
			String response = myJsonList.get(i).toJSONString();
			for(String nodePath : abTestsVariantsDataNodes()){
				AssertJUnit.assertTrue(nodePath+" is not exists", apiUtil.Exists(nodePath, response));
			}
		}
	}

	/**
	 * Get variants of all active ABTests: 
	 * @author jhansi.bai
	 */
	@Test(groups = {"Regression", "MiniRegression", "ExhaustiveRegression", "RFPFOX7" },description ="Verify status and data node values for get variants of all active A/B Test API")
	public void ABService_getVariantsOfAllActiveABTests_vStatusAdDataNodesVals() {

		RequestGenerator req = getRequest(APINAME.GETVARIANTSOFALLACTIVEABTEST);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("Status code does't match", req.respvalidate.NodeValue(abTestsStatusNodes().get(0), true), 
				"15002");			
		AssertJUnit.assertEquals("Status message dosn't match",	req.respvalidate.NodeValue(abTestsStatusNodes().get(1), true), 
				"\"ABTest(s) retrieved successfully\"");		 
		AssertJUnit.assertEquals("Status type dosn't match", req.respvalidate.NodeValue(abTestsStatusNodes().get(2), true), 
				"\"SUCCESS\"");
		AssertJUnit.assertEquals("itemcount dosn't match", req.respvalidate.NodeValue(abTestsStatusNodes().get(3), true), 
				req.respvalidate.GetArraySize("data")+"");
	}

	/**
	 * Get variants of all active ABTests with filter: 
	 * @author jhansi.bai
	 */
	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression", "RFPFOX7" },
			dataProvider = "getVariantsOfAllActiveABTestsWithFilterDP",description ="Verify 200 response for get variants of all active A/B Test with filter API")
	public void ABService_getVariantsOfAllActiveABTestsWithFilter(String userId,  String statusCode, 
			String successMessage, String successStatus) {
		RequestGenerator req = getRequest(APINAME.GETVARIANTSOFALLACTIVEABTESTWITHFILTER, userId);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("getVariantsOfAllActiveABTests is not working", 200,
				req.response.getStatus());
	}

	/**
	 * Get variants of all active ABTests with filter: 
	 * @author jhansi.bai
	 */
	@Test(groups = {"Regression", "MiniRegression", "ExhaustiveRegression", "RFPFOX7" },
			dataProvider = "getVariantsOfAllActiveABTestsWithFilterDP",description ="Verify status and data nodes for get varaints of all active A/B Test with filter API")
	public void ABService_getVariantsOfAllActiveABTestsWithFilter_vStatusAdDataNodes(String userId,  String statusCode, 
			String successMessage, String successStatus) {
		RequestGenerator req = getRequest(APINAME.GETVARIANTSOFALLACTIVEABTESTWITHFILTER, userId);
		String resp = req.respvalidate.returnresponseasstring();
		System.out.println(resp);
		AssertJUnit.assertTrue("Inval ABtest variants API main nodes.", 
				req.respvalidate.DoesNodesExists(abTestsMainNodes()));
		AssertJUnit.assertTrue("Inval ABtest variants API status nodes.", 
				req.respvalidate.DoesNodesExists(abTestsStatusNodes()));

		//verify data nodes
		List<JSONObject> myJsonList = JsonPath.read(resp, "$.data[*]");
		for(int i=0; i<myJsonList.size(); i++){
			String response = myJsonList.get(i).toJSONString();
			for(String nodePath : abTestsVariantsDataNodes()){
				AssertJUnit.assertTrue(nodePath+" is not exists", apiUtil.Exists(nodePath, response));
			}
		}
	}

	/**
	 * Get variants of all active ABTests with filter: 
	 * @author jhansi.bai
	 */
	@Test(groups = { "Regression", "MiniRegression", "ExhaustiveRegression", "RFPFOX7" }, 
			dataProvider = "getVariantsOfAllActiveABTestsWithFilterDP",description ="Verify status and data node values for get varaints of all active A/B Test with filter API")
	public void ABService_getVariantsOfAllActiveABTestsWithFilter_vStatusAdDataNodesVals(String userId,  String statusCode, 
			String successMessage, String successStatus) {
		RequestGenerator req = getRequest(APINAME.GETVARIANTSOFALLACTIVEABTESTWITHFILTER, userId);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("Status code does't match", req.respvalidate.NodeValue(abTestsStatusNodes().get(0), true), 
				"15002");			
		AssertJUnit.assertEquals("Status message dosn't match",	req.respvalidate.NodeValue(abTestsStatusNodes().get(1), true), 
				"\"ABTest(s) retrieved successfully\"");		 
		AssertJUnit.assertEquals("Status type dosn't match", req.respvalidate.NodeValue(abTestsStatusNodes().get(2), true), 
				"\"SUCCESS\"");
		AssertJUnit.assertEquals("itemcount dosn't match", req.respvalidate.NodeValue(abTestsStatusNodes().get(3), true), 
				req.respvalidate.GetArraySize("data")+"");
	}

	/**
	 * Get variants of all active ABTests with SEGMENT: 
	 * @author jhansi.bai
	 */
	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression", "RFPFOX7" }, 
			dataProvider = "getVariantsOfAllActiveABTestswithsegmentDP",description ="Verify 200 response for get varaints of all active A/B Test with segment API")
	public void ABService_getVariantsOfAllActiveABTestsWithSegment(String xid,  String statusCode, 
			String successMessage, String successStatus) {
		RequestGenerator req = getRequest(APINAME.GETVARIANTSOFALLACTIVEABTESTWITHSEGMENT, xid);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("getVariantsOfAllActiveABTests is not working", 200,
				req.response.getStatus());
	}

	/**
	 * Get variants of all active ABTests with SEGMENT: 
	 * @author jhansi.bai
	 */
	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression", "RFPFOX7" },
			dataProvider = "getVariantsOfAllActiveABTestswithsegmentDP",description ="Verify status and data nodes for get varaints of all active A/B Test with segment API")
	public void ABService_getVariantsOfAllActiveABTestsWithSegment_vStatusAdDataNodes(String xid,  String statusCode, 
			String successMessage, String successStatus) {
		RequestGenerator req = getRequest(APINAME.GETVARIANTSOFALLACTIVEABTESTWITHSEGMENT, xid);
		String resp = req.respvalidate.returnresponseasstring();
		System.out.println(resp);
		AssertJUnit.assertTrue("Inval ABtest variants API main nodes.", 
				req.respvalidate.DoesNodesExists(abTestsMainNodes()));
		AssertJUnit.assertTrue("Inval ABtest variants API status nodes.", 
				req.respvalidate.DoesNodesExists(abTestsStatusNodes()));

		//verify data nodes
		List<JSONObject> myJsonList = JsonPath.read(resp, "$.data[*]");
		for(int i=0; i<myJsonList.size(); i++){
			String response = myJsonList.get(i).toJSONString();
			for(String nodePath : abTestsVariantsDataNodes()){
				AssertJUnit.assertTrue(nodePath+" is not exists", apiUtil.Exists(nodePath, response));
			}
		}
	}

	/**
	 * Get variants of all active ABTests with SEGMENT: 
	 * @author jhansi.bai
	 */
	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression", "RFPFOX7" }, 
			dataProvider = "getVariantsOfAllActiveABTestswithsegmentDP",description ="Verify status and data node values for get varaints of all active A/B Test with segment API")
	public void ABService_getVariantsOfAllActiveABTestsWithSegment_vStatusAdDataNodesVals(String xid,  String statusCode, 
			String successMessage, String successStatus) {
		RequestGenerator req = getRequest(APINAME.GETVARIANTSOFALLACTIVEABTESTWITHSEGMENT, xid);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("Status code does't match", req.respvalidate.NodeValue(abTestsStatusNodes().get(0), true), 
				"15002");			
		AssertJUnit.assertEquals("Status message dosn't match",	req.respvalidate.NodeValue(abTestsStatusNodes().get(1), true), 
				"\"ABTest(s) retrieved successfully\"");		 
		AssertJUnit.assertEquals("Status type dosn't match", req.respvalidate.NodeValue(abTestsStatusNodes().get(2), true), 
				"\"SUCCESS\"");
		AssertJUnit.assertEquals("itemcount dosn't match", req.respvalidate.NodeValue(abTestsStatusNodes().get(3), true), 
				req.respvalidate.GetArraySize("data")+"");
	}

	/**
	 * Get variants of all active ABTests with AB tests: 
	 * @author jhansi.bai
	 */
	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression", "RFPFOX7" },
			dataProvider = "getVariantsOfAllActiveABTestswithABtestsDP",description ="Verify 200 response for get varaints of all active A/B Test with AB Test API")
	public void ABService_getVariantsOfAllActiveABTestsWithABtests(String userId, String xid,  String statusCode, 
			String successMessage, String successStatus) {
		RequestGenerator req = getRequest(APINAME.GETVARIANTSOFALLACTIVEABTESTWITHABTEST, userId, xid);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("getVariantsOfAllActiveABTests is not working", 200,
				req.response.getStatus());
	}

	/**
	 * Get variants of all active ABTests with AB tests: 
	 * @author jhansi.bai
	 */
	@Test(groups = { "Regression", "MiniRegression", "ExhaustiveRegression", "RFPFOX7" }, 
			dataProvider = "getVariantsOfAllActiveABTestswithABtestsDP",description ="Verify status and data nodes for get varaints of all active A/B Test with AB Test API")
	public void ABService_getVariantsOfAllActiveABTestsWithABtests_vStatusAdDataNodes(String userId, String xid,  String statusCode, 
			String successMessage, String successStatus) {
		RequestGenerator req = getRequest(APINAME.GETVARIANTSOFALLACTIVEABTESTWITHABTEST, userId, xid);
		String resp = req.respvalidate.returnresponseasstring();
		System.out.println(resp);
		AssertJUnit.assertTrue("Inval ABtest variants API main nodes.", 
				req.respvalidate.DoesNodesExists(abTestsMainNodes()));
		AssertJUnit.assertTrue("Inval ABtest variants API status nodes.", 
				req.respvalidate.DoesNodesExists(abTestsStatusNodes()));

		//verify data nodes
		List<JSONObject> myJsonList = JsonPath.read(resp, "$.data[*]");
		for(int i=0; i<myJsonList.size(); i++){
			String response = myJsonList.get(i).toJSONString();
			for(String nodePath : abTestsVariantsDataNodes()){
				AssertJUnit.assertTrue(nodePath+" is not exists", apiUtil.Exists(nodePath, response));
			}
		}
	}

	/**
	 * Get variants of all active ABTests with AB tests: 
	 * @author jhansi.bai
	 */
	@Test(groups = { "Regression", "MiniRegression", "ExhaustiveRegression", "RFPFOX7" }, 
			dataProvider = "getVariantsOfAllActiveABTestswithABtestsDP",description ="Verify status and data node values for get varaints of all active A/B Test with AB Test API")
	public void ABService_getVariantsOfAllActiveABTestsWithABtests_vStatusAdDataNodesVals(String userId, String xid,  
			String statusCode, String successMessage, String successStatus) {
		RequestGenerator req = getRequest(APINAME.GETVARIANTSOFALLACTIVEABTESTWITHABTEST, userId, xid);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("Status code does't match", req.respvalidate.NodeValue(abTestsStatusNodes().get(0), true), 
				"15002");			
		AssertJUnit.assertEquals("Status message dosn't match",	req.respvalidate.NodeValue(abTestsStatusNodes().get(1), true), 
				"\"ABTest(s) retrieved successfully\"");		 
		AssertJUnit.assertEquals("Status type dosn't match", req.respvalidate.NodeValue(abTestsStatusNodes().get(2), true), 
				"\"SUCCESS\"");
		AssertJUnit.assertEquals("itemcount dosn't match", req.respvalidate.NodeValue(abTestsStatusNodes().get(3), true), 
				req.respvalidate.GetArraySize("data")+"");
	}
	
	@Test(groups = { "SchemaValidation" })
	public void ABService_getAllABTest_verifyResponseDataNodesUsingSchemaValidations()
	{
		RequestGenerator getAllABTestReqGen = getRequest(APINAME.GETALLABTESTS);
		String getAllABTestResponse = getAllABTestReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting ABService getAllABTest API response :\n\n"+getAllABTestResponse);
		log.info("\nPrinting ABService getAllABTest API response :\n\n"+getAllABTestResponse);

		AssertJUnit.assertEquals("ABService getAllABTest API is not working", 200, getAllABTestReqGen.response.getStatus());
		
		try {
	        String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/configservice-abtest-getallabtest-schema.txt");
	        List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(getAllABTestResponse, jsonschema);
	        AssertJUnit.assertTrue(missingNodeList+" nodes are missing in ABService getAllABTest API response", 
	        		CollectionUtils.isEmpty(missingNodeList));
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	@Test(groups = { "SchemaValidation" }, dataProvider = "ABTestDP_getABTestsForGivenList_verifyResponseDataNodesUsingSchemaValidations")
	public void ABService_getABTestsForGivenList_verifyResponseDataNodesUsingSchemaValidations(String tests, 
			String statusCode, String successMessage, String successStatus)
	{
		RequestGenerator getABTestsForGivenListReqGen = getHeaderRequest(APINAME.GETABTESTFORGIVENLIST, tests);
		String getABTestsForGivenListResponse = getABTestsForGivenListReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting ABService getABTestsForGivenList API response :\n\n"+getABTestsForGivenListResponse);
		log.info("\nPrinting ABService getABTestsForGivenList API response :\n\n"+getABTestsForGivenListResponse);

		AssertJUnit.assertEquals("ABService getABTestsForGivenList API is not working", 200, getABTestsForGivenListReqGen.response.getStatus());
		
		try {
	        String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/configservice-abtest-getabtestsforgivenlist-schema.txt");
	        List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(getABTestsForGivenListResponse, jsonschema);
	        AssertJUnit.assertTrue(missingNodeList+" nodes are missing in ABService getABTestsForGivenList API response", 
	        		CollectionUtils.isEmpty(missingNodeList));
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	@Test(groups = { "SchemaValidation" }, dataProvider = "ABTestDP_getABTest_verifyResponseDataNodesUsingSchemaValidations")
	public void ABService_getABTest_verifyResponseDataNodesUsingSchemaValidations(String testName, String statusCode, String successMessage, String successStatus) 
	{
		RequestGenerator getABTestReqGen = getQueryRequest(APINAME.GETABTEST, testName);
		String getABTestResponse = getABTestReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting ABService getABTest API response :\n\n"+getABTestResponse);
		log.info("\nPrinting ABService getABTest API response :\n\n"+getABTestResponse);

		AssertJUnit.assertEquals("ABService getABTest API is not working", 200, getABTestReqGen.response.getStatus());
		
		try {
	        String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/configservice-abtest-getabtest-schema.txt");
	        List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(getABTestResponse, jsonschema);
	        AssertJUnit.assertTrue(missingNodeList+" nodes are missing in ABService getABTest API response", 
	        		CollectionUtils.isEmpty(missingNodeList));
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	@Test(groups = { "SchemaValidation" })
	public void ABService_getAllABTestNames_verifyResponseDataNodesUsingSchemaValidations()
	{
		RequestGenerator getAllABTestNamesReqGen = getRequest(APINAME.GETALLABTESTNAMES);
		String getAllABTestNamesResponse = getAllABTestNamesReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting ABService getAllABTestNames API response :\n\n"+getAllABTestNamesResponse);
		log.info("\nPrinting ABService getAllABTestNames API response :\n\n"+getAllABTestNamesResponse);

		AssertJUnit.assertEquals("ABService getAllABTestNames API is not working", 200, getAllABTestNamesReqGen.response.getStatus());
		
		try {
	        String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/configservice-abtest-getallabtestnames-schema.txt");
	        List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(getAllABTestNamesResponse, jsonschema);
	        AssertJUnit.assertTrue(missingNodeList+" nodes are missing in ABService getAllABTestNames API response", 
	        		CollectionUtils.isEmpty(missingNodeList));
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	@Test(groups = { "SchemaValidation" }, dataProvider = "ABTestDP_getABTestVariants_verifyResponseDataNodesUsingSchemaValidations")
	public void ABService_getABTestVariants_verifyResponseDataNodesUsingSchemaValidations(String testName, String statusCode, String successMessage, String successStatus)
	{
		RequestGenerator getABTestVariantsReqGen = getQueryRequest(APINAME.GETALLABTESTVARIANTS, testName);
		String getABTestVariantsResponse = getABTestVariantsReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting ABService getABTestVariants API response :\n\n"+getABTestVariantsResponse);
		log.info("\nPrinting ABService getABTestVariants API response :\n\n"+getABTestVariantsResponse);

		AssertJUnit.assertEquals("ABService getABTestVariants API is not working", 200, getABTestVariantsReqGen.response.getStatus());
		
		try {
	        String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/configservice-abtest-getabtestvariants-schema.txt");
	        List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(getABTestVariantsResponse, jsonschema);
	        AssertJUnit.assertTrue(missingNodeList+" nodes are missing in ABService getABTestVariants API response", 
	        		CollectionUtils.isEmpty(missingNodeList));
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	@Test(groups = { "SchemaValidation" }, dataProvider = "ABTestDP_updateExistingABTest_verifyResponseDataNodesUsingSchemaValidations")
	public void ABService_updateExistingABTest_verifyResponseDataNodesUsingSchemaValidations(String variation_name,	String percentProbability, String inlineHtml,
			String finalVariant, String name, String percentProbability1, String inlineHtml1, String finalVariant1, String auditLogs, String name2, String isEnabled, 
			String gaSlot, String omniSlot, String filters, String segmentationAlgo, String sourceType, String isCompleted, String apiVersion) 
	{
		RequestGenerator updateExistingABTestReqGen = getRequest(APINAME.UPDATEEXISTINGABTEST, variation_name, percentProbability, inlineHtml, finalVariant, name, percentProbability1,
				inlineHtml1, finalVariant1, auditLogs, name2, isEnabled, gaSlot, omniSlot, filters, segmentationAlgo,	sourceType, isCompleted, apiVersion);
		String updateExistingABTestResponse = updateExistingABTestReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting ABService updateExistingABTest API response :\n\n"+updateExistingABTestResponse);
		log.info("\nPrinting ABService updateExistingABTest API response :\n\n"+updateExistingABTestResponse);

		AssertJUnit.assertEquals("ABService updateExistingABTest API is not working", 200, updateExistingABTestReqGen.response.getStatus());
		
		try {
	        String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/configservice-abtest-updateexistingabtest-schema.txt");
	        List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(updateExistingABTestResponse, jsonschema);
	        AssertJUnit.assertTrue(missingNodeList+" nodes are missing in ABService updateExistingABTest API response", 
	        		CollectionUtils.isEmpty(missingNodeList));
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	@Test(groups = { "SchemaValidation" }, dataProvider = "ABTestDP_getVariantOfGivenABTest_verifyResponseDataNodesUsingSchemaValidations")
	public void ABService_getVariantOfGivenABTest_verifyResponseDataNodesUsingSchemaValidations(String ABTestName, String statusCode, String successMessage,
			String successStatus, String itemCount) 
	{
		RequestGenerator getVariantOfGivenABTestReqGen = getQuerryPayLoadRequest(APINAME.GETVARIANTOFGIVENABTEST, ABTestName);
		String getVariantOfGivenABTestResponse = getVariantOfGivenABTestReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting ABService getVariantOfGivenABTest API response :\n\n"+getVariantOfGivenABTestResponse);
		log.info("\nPrinting ABService getVariantOfGivenABTest API response :\n\n"+getVariantOfGivenABTestResponse);

		AssertJUnit.assertEquals("ABService getVariantOfGivenABTest API is not working", 200, getVariantOfGivenABTestReqGen.response.getStatus());
		
		try {
	        String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/configservice-abtest-getvariantofgivenabtest-schema.txt");
	        List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(getVariantOfGivenABTestResponse, jsonschema);
	        AssertJUnit.assertTrue(missingNodeList+" nodes are missing in ABService getVariantOfGivenABTest API response", 
	        		CollectionUtils.isEmpty(missingNodeList));
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	@Test(groups = { "SchemaValidation" }, dataProvider = "ABTestDP_getVariantOfGivenABTestWithFilter_verifyResponseDataNodesUsingSchemaValidations")
	public void ABService_getVariantOfGivenABTestWithFilter_verifyResponseDataNodesUsingSchemaValidations(String userId, String ABTestName, String statusCode, 
			String successMessage, String successStatus, String itemCount)
	{
		RequestGenerator getVariantOfGivenABTestWithFilterReqGen = getQuerryPayLoadRequest(APINAME.GETVARIANTOFGIVENABTESTWITHFILTER, userId, ABTestName);
		String getVariantOfGivenABTestWithFilterResponse = getVariantOfGivenABTestWithFilterReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting ABService getVariantOfGivenABTestWithFilter API response :\n\n"+getVariantOfGivenABTestWithFilterResponse);
		log.info("\nPrinting ABService getVariantOfGivenABTestWithFilter API response :\n\n"+getVariantOfGivenABTestWithFilterResponse);

		AssertJUnit.assertEquals("ABService getVariantOfGivenABTestWithFilter API is not working", 200, getVariantOfGivenABTestWithFilterReqGen.response.getStatus());
		
		try {
	        String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/configservice-abtest-getvariantofgivenabtestwithfilter-schema.txt");
	        List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(getVariantOfGivenABTestWithFilterResponse, jsonschema);
	        AssertJUnit.assertTrue(missingNodeList+" nodes are missing in ABService getVariantOfGivenABTestWithFilter API response", 
	        		CollectionUtils.isEmpty(missingNodeList));
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	@Test(groups = { "SchemaValidation" }, dataProvider = "ABTestDP_getVariantOfGivenABTestWithSegment_verifyResponseDataNodesUsingSchemaValidations")
	public void ABService_getVariantOfGivenABTestWithSegment_verifyResponseDataNodesUsingSchemaValidations(String xid, String ABTestName,  String statusCode, 
			String successMessage, String successStatus, String itemCount)
	{
		RequestGenerator getVariantOfGivenABTestWithSegmentReqGen = getQuerryPayLoadRequest(APINAME.GETVARIANTOFGIVENABTESTWITHSEGMENT, xid, ABTestName);
		String getVariantOfGivenABTestWithSegmentResponse = getVariantOfGivenABTestWithSegmentReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting ABService getVariantOfGivenABTestWithSegment API response :\n\n"+getVariantOfGivenABTestWithSegmentResponse);
		log.info("\nPrinting ABService getVariantOfGivenABTestWithSegment API response :\n\n"+getVariantOfGivenABTestWithSegmentResponse);

		AssertJUnit.assertEquals("ABService getVariantOfGivenABTestWithSegment API is not working", 200, getVariantOfGivenABTestWithSegmentReqGen.response.getStatus());
		
		try {
	        String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/configservice-abtest-getvariantofgivenabtestwithsegment-schema.txt");
	        List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(getVariantOfGivenABTestWithSegmentResponse, jsonschema);
	        AssertJUnit.assertTrue(missingNodeList+" nodes are missing in ABService getVariantOfGivenABTestWithSegment API response", 
	        		CollectionUtils.isEmpty(missingNodeList));
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	@Test(groups = { "SchemaValidation" },  dataProvider = "ABTestDP_getVariantOfGivenABTestWithABtest_verifyResponseDataNodesUsingSchemaValidations")
	public void ABService_getVariantOfGivenABTestWithABtest_verifyResponseDataNodesUsingSchemaValidations(String userid, String xid, String ABTestName, 
			String statusCode, String successMessage, String successStatus, String itemCount) 
	{
		RequestGenerator getVariantOfGivenABTestWithABtestReqGen = getQuerryPayLoadRequest(APINAME.GETVARIANTOFGIVENABTESTWITHABTEST, userid, xid, ABTestName);
		String getVariantOfGivenABTestWithSegmentResponse = getVariantOfGivenABTestWithABtestReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting ABService getVariantOfGivenABTestWithABtest API response :\n\n"+getVariantOfGivenABTestWithSegmentResponse);
		log.info("\nPrinting ABService getVariantOfGivenABTestWithABtest API response :\n\n"+getVariantOfGivenABTestWithSegmentResponse);

		AssertJUnit.assertEquals("ABService getVariantOfGivenABTestWithABtest API is not working", 200, getVariantOfGivenABTestWithABtestReqGen.response.getStatus());
		
		try {
	        String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/configservice-abtest-getvariantofgivenabtestwithabtest-schema.txt");
	        List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(getVariantOfGivenABTestWithSegmentResponse, jsonschema);
	        AssertJUnit.assertTrue(missingNodeList+" nodes are missing in ABService getVariantOfGivenABTestWithABtest API response", 
	        		CollectionUtils.isEmpty(missingNodeList));
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	@Test(groups = { "SchemaValidation" })
	public void ABService_getVariantsOfAllActiveABTests_verifyResponseDataNodesUsingSchemaValidations() 
	{
		RequestGenerator getVariantsOfAllActiveABTestsReqGen = getRequest(APINAME.GETVARIANTSOFALLACTIVEABTEST);
		String getVariantsOfAllActiveABTestsResponse = getVariantsOfAllActiveABTestsReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting ABService getVariantsOfAllActiveABTests API response :\n\n"+getVariantsOfAllActiveABTestsResponse);
		log.info("\nPrinting ABService getVariantsOfAllActiveABTests API response :\n\n"+getVariantsOfAllActiveABTestsResponse);

		AssertJUnit.assertEquals("ABService getVariantsOfAllActiveABTests API is not working", 200, getVariantsOfAllActiveABTestsReqGen.response.getStatus());
		
		try {
	        String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/configservice-abtest-getvariantsofallactiveabtests-schema.txt");
	        List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(getVariantsOfAllActiveABTestsResponse, jsonschema);
	        AssertJUnit.assertTrue(missingNodeList+" nodes are missing in ABService getVariantsOfAllActiveABTests API response", 
	        		CollectionUtils.isEmpty(missingNodeList));
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	@Test(groups = { "SchemaValidation" }, dataProvider = "ABTestDP_getVariantsOfAllActiveABTestsWithFilter_verifyResponseDataNodesUsingSchemaValidations")
	public void ABService_getVariantsOfAllActiveABTestsWithFilter_verifyResponseDataNodesUsingSchemaValidations(String userId,  String statusCode, String successMessage, 
			String successStatus) 
	{
		RequestGenerator getVariantsOfAllActiveABTestsWithFilterReqGen = getRequest(APINAME.GETVARIANTSOFALLACTIVEABTESTWITHFILTER, userId);
		String getVariantsOfAllActiveABTestsResponse = getVariantsOfAllActiveABTestsWithFilterReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting ABService getVariantsOfAllActiveABTestsWithFilter API response :\n\n"+getVariantsOfAllActiveABTestsResponse);
		log.info("\nPrinting ABService getVariantsOfAllActiveABTestsWithFilter API response :\n\n"+getVariantsOfAllActiveABTestsResponse);

		AssertJUnit.assertEquals("ABService getVariantsOfAllActiveABTestsWithFilter API is not working", 200, 
				getVariantsOfAllActiveABTestsWithFilterReqGen.response.getStatus());
		
		try {
	        String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/configservice-abtest-getvariantsofallactiveabtestswithfilter-schema.txt");
	        List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(getVariantsOfAllActiveABTestsResponse, jsonschema);
	        AssertJUnit.assertTrue(missingNodeList+" nodes are missing in ABService getVariantsOfAllActiveABTestsWithFilter API response", 
	        		CollectionUtils.isEmpty(missingNodeList));
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	@Test(groups = { "SchemaValidation" }, dataProvider = "ABTestDP_getVariantsOfAllActiveABTestsWithSegment_verifyResponseDataNodesUsingSchemaValidations")
	public void ABService_getVariantsOfAllActiveABTestsWithSegment_verifyResponseDataNodesUsingSchemaValidations(String xid,  String statusCode, String successMessage, 
			String successStatus) 
	{
		RequestGenerator getVariantsOfAllActiveABTestsWithSegmentReqGen = getRequest(APINAME.GETVARIANTSOFALLACTIVEABTESTWITHSEGMENT, xid);
		String getVariantsOfAllActiveABTestsWithSegmentResponse = getVariantsOfAllActiveABTestsWithSegmentReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting ABService getVariantsOfAllActiveABTestsWithSegment API response :\n\n"+getVariantsOfAllActiveABTestsWithSegmentResponse);
		log.info("\nPrinting ABService getVariantsOfAllActiveABTestsWithSegment API response :\n\n"+getVariantsOfAllActiveABTestsWithSegmentResponse);

		AssertJUnit.assertEquals("ABService getVariantsOfAllActiveABTestsWithSegment API is not working", 200, 
				getVariantsOfAllActiveABTestsWithSegmentReqGen.response.getStatus());
		
		try {
	        String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/configservice-abtest-getvariantsofallactiveabtestswithsegment-schema.txt");
	        List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(getVariantsOfAllActiveABTestsWithSegmentResponse, jsonschema);
	        AssertJUnit.assertTrue(missingNodeList+" nodes are missing in ABService getVariantsOfAllActiveABTestsWithSegment API response", 
	        		CollectionUtils.isEmpty(missingNodeList));
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	@Test(groups = { "SchemaValidation" }, dataProvider = "ABTestDP_getVariantsOfAllActiveABTestsWithABtests_verifyResponseDataNodesUsingSchemaValidations")
	public void ABService_getVariantsOfAllActiveABTestsWithABtests_verifyResponseDataNodesUsingSchemaValidations(String userId, String xid,  String statusCode,
			String successMessage, String successStatus) 
	{
		RequestGenerator getVariantsOfAllActiveABTestsWithABtestsReqGen = getRequest(APINAME.GETVARIANTSOFALLACTIVEABTESTWITHABTEST, userId, xid);
		String getVariantsOfAllActiveABTestsWithABtestsResponse = getVariantsOfAllActiveABTestsWithABtestsReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting ABService getVariantsOfAllActiveABTestsWithABtests API response :\n\n"+getVariantsOfAllActiveABTestsWithABtestsResponse);
		log.info("\nPrinting ABService getVariantsOfAllActiveABTestsWithABtests API response :\n\n"+getVariantsOfAllActiveABTestsWithABtestsResponse);

		AssertJUnit.assertEquals("ABService getVariantsOfAllActiveABTestsWithABtests API is not working", 200, 
				getVariantsOfAllActiveABTestsWithABtestsReqGen.response.getStatus());
		
		try {
	        String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/configservice-abtest-getvariantsofallactiveabtestswithabtests-schema.txt");
	        List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(getVariantsOfAllActiveABTestsWithABtestsResponse, jsonschema);
	        AssertJUnit.assertTrue(missingNodeList+" nodes are missing in ABService getVariantsOfAllActiveABTestsWithABtests API response", 
	        		CollectionUtils.isEmpty(missingNodeList));
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
}