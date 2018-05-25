package com.myntra.apiTests.portalservices.all;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.myntra.apiTests.common.Myntra;
import com.myntra.apiTests.dataproviders.SearchServiceDP;
import com.myntra.apiTests.portalservices.searchservice.SearchServiceHelper;
import org.apache.commons.collections.CollectionUtils;
import org.codehaus.jackson.JsonProcessingException;
import org.junit.Ignore;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;
import org.testng.log4testng.Logger;

import com.jayway.jsonpath.JsonPath;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.Toolbox;
import com.myntra.apiTests.common.APINAME;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.apiTests.common.ServiceType;

public class SearchServiceTests extends SearchServiceDP {
	private static final String String = null;
	Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(SearchServiceTests.class);
	static APIUtilities apiUtil = new APIUtilities();
	StringBuffer sb = new StringBuffer();
	static int counter = 0;
	static Toolbox tools = new Toolbox();
	static com.myntra.apiTests.portalservices.searchservice.SearchServiceHelper SearchServiceHelper = new SearchServiceHelper();

	// ------------------------------------------------Testcases for
	// getQuery-------------------------------------------------------
	/*
	 * Test-cases 1.1: POST SERVICE getQuery getting success response - default
	 * service
	 * 
	 * @author sneha.deep
	 */
	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression", "SearchSanity",
			"SearchRegression" }, dataProvider = "SearchService_getQueryWithValidInputs")
	public void SearchService_getQueryWithSuccessMessage(String query, String return_docs, String is_facet,
			String login) {

		long startTime = Calendar.getInstance().getTimeInMillis();

		MyntraService service = Myntra.getService(ServiceType.PORTAL_SEARCHSERVICE, APINAME.GETQUERY,
				init.Configurations, new String[] { query, return_docs, is_facet, login });
		RequestGenerator req = new RequestGenerator(service);
		System.out.println(service.URL);
		log.info(service.URL);
		System.out.println(service.Payload);
		// System.out.println(req.returnresponseasstring());
		AssertJUnit.assertEquals("getQueryWithValidInputs is not working", 200, req.response.getStatus());

		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;
		System.out.println("\nTime taken to execute - TestCase - SearchService_getQueryWithSuccessMessage : "
				+ timeTaken + " seconds\n");
		log.info("\nTime taken to execute - TestCase - SearchService_getQueryWithSuccessMessage : " + timeTaken
				+ " seconds\n");
	}

	// ------------------------------------------------------------------------------------------------------------------------------

	/*
	 * Test-cases 1.2: POST SERVICE getQuery having return_docs as false. As a
	 * result, product details json array should not be populated.
	 * 
	 * @author sneha.deep
	 */
	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression",
			"SearchRegression" }, dataProvider = "SearchService_getQueryWithReturnDocsAsFalseDP")
	public void SearchService_getQueryWithReturnDocsAsFalse(String query, String return_docs, String is_facet,
			String login) {

		long startTime = Calendar.getInstance().getTimeInMillis();

		MyntraService service = Myntra.getService(ServiceType.PORTAL_SEARCHSERVICE, APINAME.GETQUERY,
				init.Configurations, new String[] { query, return_docs, is_facet, login });
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);
		// System.out.println(service.Payload);
		String response = req.returnresponseasstring();
		// System.out.println(response);

		Object responseProducts = JsonPath.read(response, "$.data.queryResult.response1.products");
		String responseProductsStr = responseProducts.toString();
		Boolean responseProductsBool = responseProductsStr.contains("id");
		System.out.println("value of responseProductsBool: " + responseProductsBool);
		if (responseProductsBool) {
			AssertJUnit.assertTrue("Product Count should be 0", responseProductsBool);
		} else {
			AssertJUnit.assertEquals("SearchService_getQueryWithReturnDocsAsFalse is not working", 200,
					req.response.getStatus());
		}

		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;
		System.out.println("\nTime taken to execute - TestCase - SearchService_getQueryWithReturnDocsAsFalse : "
				+ timeTaken + " seconds\n");
		log.info("\nTime taken to execute - TestCase - SearchService_getQueryWithReturnDocsAsFalse : " + timeTaken
				+ " seconds\n");
	}

	// ------------------------------------------------------------------------------------------------------------------------------

	/*
	 * Test-cases 1.3: POST SERVICE getQuery having return_docs as true. As a
	 * result, product details json array should be populated.
	 * 
	 * @author sneha.deep
	 */
	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression",
			"SearchRegression" }, dataProvider = "SearchService_getQueryWithValidInputs")
	public void SearchService_getQueryWithReturnDocsAsTrue(String query, String return_docs, String is_facet,
			String login) {

		long startTime = Calendar.getInstance().getTimeInMillis();

		MyntraService service = Myntra.getService(ServiceType.PORTAL_SEARCHSERVICE, APINAME.GETQUERY,
				init.Configurations, new String[] { query, return_docs, is_facet, login });
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);
		// System.out.println(service.Payload);
		String response = req.returnresponseasstring();
		// System.out.println(response);

		Object responseProducts = JsonPath.read(response, "$.data.queryResult.response1.products");
		String responseProductsStr = responseProducts.toString();
		Boolean responseProductsBool = responseProductsStr.contains("id");
		System.out.println("value of responseProductsBool: " + responseProductsBool);
		if (responseProductsBool) {
			AssertJUnit.assertEquals("SearchService_getQueryWithReturnDocsAsFalse is not working", 200,
					req.response.getStatus());
		} else {
			AssertJUnit.assertTrue("Product Count should be 0", responseProductsBool);
		}

		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;
		System.out.println("\nTime taken to execute - TestCase - SearchService_getQueryWithReturnDocsAsFalse : "
				+ timeTaken + " seconds\n");
		log.info("\nTime taken to execute - TestCase - SearchService_getQueryWithReturnDocsAsFalse : " + timeTaken
				+ " seconds\n");
	}

	// -------------------------------------------------------------------------------------------------------------------------------------------

	/*
	 * Test-cases 1.4: POST SERVICE getQuery having is_facet as true. As a
	 * result, filters details json array should be populated.
	 * 
	 * @author sneha.deep
	 */
	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression",
			"SearchRegression" }, dataProvider = "SearchService_getQueryWithValidInputs")
	public void SearchService_getQueryWithIsFacetAsTrue(String query, String return_docs, String is_facet,
			String login) {

		long startTime = Calendar.getInstance().getTimeInMillis();

		MyntraService service = Myntra.getService(ServiceType.PORTAL_SEARCHSERVICE, APINAME.GETQUERY,
				init.Configurations, new String[] { query, return_docs, is_facet, login });
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);
		// System.out.println(service.Payload);
		String response = req.returnresponseasstring();
		// System.out.println(response);

		// verify filters
		Object responseProducts = JsonPath.read(response, "$.data.queryResult.response1.filters");
		String responseProductsStr = responseProducts.toString();
		Boolean responseProductsBool = responseProductsStr.contains("sizes_facet");
		// System.out.println("value of responseProductsBool:
		// "+responseProductsBool);
		if (responseProductsBool) {
			AssertJUnit.assertEquals("SearchService_getQueryWithIsFacetAsTrue is not working", 200,
					req.response.getStatus());
		} else {
			AssertJUnit.assertTrue("Filters array should be blank", responseProductsBool);
		}

		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;
		System.out.println("\nTime taken to execute - TestCase - SearchService_getQueryWithReturnDocsAsFalse : "
				+ timeTaken + " seconds\n");
		log.info("\nTime taken to execute - TestCase - SearchService_getQueryWithReturnDocsAsFalse : " + timeTaken
				+ " seconds\n");
	}

	// ------------------------------------------------------------------------------------------------------------------------------------------

	/*
	 * Test-cases 1.5: POST SERVICE getQuery having is_facet as false. As a
	 * result, filters details json array should not be populated.
	 * 
	 * @author sneha.deep
	 */
	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression",
			"SearchRegression" }, dataProvider = "SearchService_getQueryWithIsFacetAsFalseDP")
	public void SearchService_getQueryWithIsFacetAsFalse(String query, String return_docs, String is_facet,
			String login) {

		long startTime = Calendar.getInstance().getTimeInMillis();

		MyntraService service = Myntra.getService(ServiceType.PORTAL_SEARCHSERVICE, APINAME.GETQUERY,
				init.Configurations, new String[] { query, return_docs, is_facet, login });
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);
		// System.out.println(service.Payload);
		String response = req.returnresponseasstring();
		// System.out.println(response);

		Object responseProducts = JsonPath.read(response, "$.data.queryResult.response1.filters");
		String responseProductsStr = responseProducts.toString();
		Boolean responseProductsBool = responseProductsStr.contains("sizes_facet");
		// System.out.println("value of responseProductsBool:
		// "+responseProductsBool);
		if (responseProductsBool) {
			AssertJUnit.assertTrue("Filters array should be blank", responseProductsBool);
		} else {
			AssertJUnit.assertEquals("SearchService_getQueryWithIsFacetAsFalse is not working", 200,
					req.response.getStatus());
		}

		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;
		System.out.println("\nTime taken to execute - TestCase - SearchService_getQueryWithReturnDocsAsFalse : "
				+ timeTaken + " seconds\n");
		log.info("\nTime taken to execute - TestCase - SearchService_getQueryWithReturnDocsAsFalse : " + timeTaken
				+ " seconds\n");
	}

	// ------------------------------------------------------------------------------------------------------------------------------

	@Ignore
	/*
	 * Test-cases 1.6: POST SERVICE getQuery getting success response - default
	 * service - to store the API response in local storage
	 * 
	 * @author arunesh
	 */
	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression", "SearchRegression" })
	public void SearchService_getQuery_StoringResponseInTextFile() throws IOException {

		String searchQuery = tools.readFileAsString("/Users/13711/searchresponses/IncorrectUserQueries.txt");
		String return_docs = "true";
		String is_facet = "true";
		String login = "sneha.prasad@myntra.com";

		System.out.println(searchQuery);

		String[] arr = searchQuery.split("\\n");
		int range = arr.length;

		for (int i = 0; i <= range - 1; i++) {
			// if(!arr[i].trim().equals(""))

			// {

			String injectQuery = arr[i];
			MyntraService service = Myntra.getService(ServiceType.PORTAL_SEARCHSERVICE, APINAME.GETQUERY,
					init.Configurations, new String[] { injectQuery, return_docs, is_facet, login });
			RequestGenerator req = new RequestGenerator(service);
			System.out.println(service.URL);
			log.info(service.URL);
			System.out.println(service.Payload);
			String response = req.returnresponseasstring();
			tools.WriteDatatoFile("/Users/13711/searchresponses/queriesResponse/" + injectQuery, response, true);
			// System.out.println(req.returnresponseasstring());
			// AssertJUnit.assertEquals("getQueryWithValidInputs is not
			// working",200, req.response.getStatus());
			// }
		}
		// System.out.println("\nTime taken to execute - TestCase -
		// SearchService_getQueryWithSuccessMessage : "+timeTaken+" seconds\n");
		// log.info("\nTime taken to execute - TestCase -
		// SearchService_getQueryWithSuccessMessage : "+timeTaken+" seconds\n");
	}

	// ------------------------------------------------------------------------------------------------------------------------------

	/*
	 * Test-cases 1.7: POST SERVICE getQuery getting success response -
	 * validating data node
	 * 
	 * @author sneha.deep
	 */
	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression", "SearchSanity",
			"SearchRegression" }, dataProvider = "SearchService_getQueryWithValidInputs")
	public void SearchService_getQueryWithSuccessMessage_validatingDataNode(String query, String return_docs,
			String is_facet, String login) {

		long startTime = Calendar.getInstance().getTimeInMillis();

		MyntraService service = Myntra.getService(ServiceType.PORTAL_SEARCHSERVICE, APINAME.GETQUERY,
				init.Configurations, new String[] { query, return_docs, is_facet, login });
		RequestGenerator req = new RequestGenerator(service);
		System.out.println(service.URL);
		log.info(service.URL);
		System.out.println(service.Payload);
		// System.out.println(req.returnresponseasstring());
		// System.out.println("printing data node: "+getQueryDataNodes());
		AssertJUnit.assertTrue("Inval GETQUERY API status nodes.",
				req.respvalidate.DoesNodesExists(getQueryDataNodes()));

		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;
		System.out.println("\nTime taken to execute - TestCase - SearchService_getQueryWithSuccessMessage : "
				+ timeTaken + " seconds\n");
		log.info("\nTime taken to execute - TestCase - SearchService_getQueryWithSuccessMessage : " + timeTaken
				+ " seconds\n");
	}

	// ------------------------------------------------------------------------------------------------------------------------------

	/*
	 * Test-cases 1.8: POST SERVICE getQuery getting success response -
	 * validating status node
	 * 
	 * @author sneha.deep
	 */
	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression", "SearchSanity",
			"SearchRegression" }, dataProvider = "SearchService_getQueryWithValidInputs")
	public void SearchService_getQueryWithSuccessMessage_validatingStatusNode(String query, String return_docs,
			String is_facet, String login) {

		long startTime = Calendar.getInstance().getTimeInMillis();

		MyntraService service = Myntra.getService(ServiceType.PORTAL_SEARCHSERVICE, APINAME.GETQUERY,
				init.Configurations, new String[] { query, return_docs, is_facet, login });
		RequestGenerator req = new RequestGenerator(service);
		System.out.println(service.URL);
		log.info(service.URL);
		System.out.println(service.Payload);
		// System.out.println(req.returnresponseasstring());
		System.out.println("get(1)******" + req.respvalidate.NodeValue(getQueryDataNodes().get(1), true));
		System.out.println("\"totalProductsCount\\\":" + req.respvalidate.NodeValue(getQueryDataNodes().get(5), true));

		String productCount = req.respvalidate.NodeValue(getQueryDataNodes().get(5), true);
		System.out.println(productCount);
		AssertJUnit.assertTrue("Query dosn't match", req.respvalidate.NodeValue(getQueryDataSearchNodes().get(0), true)
				.toLowerCase().contains(query.toLowerCase()));
		AssertJUnit.assertEquals("returnDocs dosn't match",
				req.respvalidate.NodeValue(getQueryDataSearchNodes().get(6), true), return_docs);
		AssertJUnit.assertEquals("isFacet dosn't match",
				req.respvalidate.NodeValue(getQueryDataSearchNodes().get(9), true), is_facet);
		AssertJUnit.assertTrue("totalProductsCount dosn't match",
				req.respvalidate.NodeValue(getQueryDataNodes().get(1), true).contains(
						"\"totalProductsCount\":" + req.respvalidate.NodeValue(getQueryDataNodes().get(5), true)));

		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;
		System.out.println("\nTime taken to execute - TestCase - SearchService_getQueryWithSuccessMessage : "
				+ timeTaken + " seconds\n");
		log.info("\nTime taken to execute - TestCase - SearchService_getQueryWithSuccessMessage : " + timeTaken
				+ " seconds\n");
	}

	/*
	 * Test-cases 1.9: POST SERVICE getQuery getting success response -
	 * validating Query Result node
	 * 
	 * @author jitender.kumar1
	 */

	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression", "SearchSanity",
			"SearchRegression" }, dataProvider = "SearchService_getQueryWithValidInput")
	public void searchService_getQueryWithSorting_validatingNodes(String query, String return_docs, String is_facet,
			String login) {

		long startTime = Calendar.getInstance().getTimeInMillis();

		MyntraService service = Myntra.getService(ServiceType.PORTAL_SEARCHSERVICE, APINAME.GETQUERY,
				init.Configurations, new String[] { query, return_docs, is_facet, login });
		RequestGenerator req = new RequestGenerator(service);
		System.out.println(service.URL);
		log.info(service.URL);
		System.out.println(service.Payload);
		System.out.println("get(0)******" + req.respvalidate.NodeValue(getQueryResultNodes().get(0), true));

		System.out
				.println("\"totalProductsCount\\\":" + req.respvalidate.NodeValue(getQueryResultNodes().get(1), true));
		System.out.println("\"productNode\\\":" + req.respvalidate.NodeValue(getQueryResultNodes().get(3), true));

		String productCount = req.respvalidate.NodeValue(getQueryResultNodes().get(1), true);
		System.out.println(productCount);

		AssertJUnit.assertTrue("Query dosn't match", req.respvalidate.NodeValue(getQueryResultNodes().get(0), true)
				.toLowerCase().contains(query.toLowerCase()));
		AssertJUnit.assertEquals("isFacet dosn't match",
				req.respvalidate.NodeValue(getQueryDataSearchNodes().get(9), true), is_facet);
		AssertJUnit.assertEquals("returnDocs dosn't match",
				req.respvalidate.NodeValue(getQueryDataSearchNodes().get(6), true), return_docs);
		AssertJUnit.assertTrue("totalProductsCount dosn't match",
				req.respvalidate.NodeValue(getQueryDataNodes().get(1), true).contains(
						"\"totalProductsCount\":" + req.respvalidate.NodeValue(getQueryDataNodes().get(5), true)));

		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;
		System.out.println("\nTime taken to execute - TestCase - SearchService_getQueryWithSuccessMessage : "
				+ timeTaken + " seconds\n");
		log.info("\nTime taken to execute - TestCase - SearchService_getQueryWithSuccessMessage : " + timeTaken
				+ " seconds\n");

	}

	/*
	 * Test-cases 1.10: POST SERVICE getQuery getting success response -
	 * validating solr node
	 * 
	 * @author jitender.kumar1
	 */

	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression", "SearchSanity",
			"SearchRegression" }, dataProvider = "SearchService_getQueryWithValidInputForSolrNodes")
	public void searchService_getQuery_validatingSolrNodes(String query, String return_docs, String is_facet,
			String login) {

		long startTime = Calendar.getInstance().getTimeInMillis();

		MyntraService service = Myntra.getService(ServiceType.PORTAL_SEARCHSERVICE, APINAME.GETQUERY,
				init.Configurations, new String[] { query, return_docs, is_facet, login });
		RequestGenerator req = new RequestGenerator(service);
		System.out.println(service.URL);
		log.info(service.URL);
		System.out.println(service.Payload);
		System.out.println("get(0)******" + req.respvalidate.NodeValue(getSolrNodes().get(0), true));
		// System.out.println("\"totalProductsCount\\\":"+
		System.out.println("\"PricedNode\\\":" + req.respvalidate.NodeValue(getSolrNodes().get(2), true));
		System.out.println("\"PopularNode\\\":" + req.respvalidate.NodeValue(getSolrNodes().get(3), true));

		String recency = req.respvalidate.NodeValue(getSolrNodes().get(1), true);
		System.out.println(recency);

		AssertJUnit.assertEquals("isFacet dosn't match",
				req.respvalidate.NodeValue(getQueryDataSearchNodes().get(9), true), is_facet);
		AssertJUnit.assertEquals("returnDocs dosn't match",
				req.respvalidate.NodeValue(getQueryDataSearchNodes().get(6), true), return_docs);
		AssertJUnit.assertTrue("totalProductsCount dosn't match",
				req.respvalidate.NodeValue(getQueryDataNodes().get(1), true).contains(
						"\"totalProductsCount\":" + req.respvalidate.NodeValue(getQueryDataNodes().get(5), true)));

		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;
		System.out.println("\nTime taken to execute - TestCase - SearchService_getQueryWithSuccessMessage : "
				+ timeTaken + " seconds\n");
		log.info("\nTime taken to execute - TestCase - SearchService_getQueryWithSuccessMessage : " + timeTaken
				+ " seconds\n");

	}

	/*
	 * Test-cases 1.11: POST SERVICE getQuery getting success response -
	 * validating all_article_attribute node
	 * 
	 * @author jitender.kumar1
	 */

	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression", "SearchSanity",
			"SearchRegression" }, dataProvider = "SearchService_getQueryWithValidInputForAllArticleNodes")
	public void searchService_getQuery_validatingAllArticleAttributeNodes(String query, String return_docs,
			String is_facet, String login) {

		long startTime = Calendar.getInstance().getTimeInMillis();

		MyntraService service = Myntra.getService(ServiceType.PORTAL_SEARCHSERVICE, APINAME.GETQUERY,
				init.Configurations, new String[] { query, return_docs, is_facet, login });
		RequestGenerator req = new RequestGenerator(service);
		System.out.println(service.URL);
		log.info(service.URL);
		System.out.println(service.Payload);
		System.out.println("get(0)******" + req.respvalidate.NodeValue(getArticleAttributeNodes().get(0), true));

		System.out.println(
				"\"totalProductsCount\\\":" + req.respvalidate.NodeValue(getArticleAttributeNodes().get(1), true));
		System.out.println("\"productNode\\\":" + req.respvalidate.NodeValue(getArticleAttributeNodes().get(3), true));

		String productCount = req.respvalidate.NodeValue(getArticleAttributeNodes().get(1), true);
		System.out.println(productCount);
		String articleName = req.respvalidate.NodeValue(getArticleAttributeNodes().get(4), true);
		System.out.println("response" + articleName);
		String articleNameinLowercase = req.respvalidate.NodeValue(getArticleAttributeNodes().get(4), true)
				.toLowerCase();
		System.out.println("response1" + articleNameinLowercase);
		AssertJUnit.assertEquals("isFacet dosn't match",
				req.respvalidate.NodeValue(getQueryDataSearchNodes().get(9), true), is_facet);
		AssertJUnit.assertEquals("returnDocs dosn't match",
				req.respvalidate.NodeValue(getQueryDataSearchNodes().get(6), true), return_docs);
		AssertJUnit.assertTrue("totalProductsCount dosn't match",
				req.respvalidate.NodeValue(getQueryDataNodes().get(1), true).contains(
						"\"totalProductsCount\":" + req.respvalidate.NodeValue(getQueryDataNodes().get(5), true)));
		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;
		System.out.println("\nTime taken to execute - TestCase - SearchService_getQueryWithSuccessMessage : "
				+ timeTaken + " seconds\n");
		log.info("\nTime taken to execute - TestCase - SearchService_getQueryWithSuccessMessage : " + timeTaken
				+ " seconds\n");

	}

	/*
	 * Test-cases 1.12: POST SERVICE getQuery getting success response -
	 * validating GuidedNav node
	 * 
	 * @author jitender.kumar1
	 */

	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression", "SearchSanity",
			"SearchRegression" }, dataProvider = "SearchService_getQueryNavNodeValidation")
	public void searchService_getQuery_validatingGuidedNavNodes(String query, String return_docs, String is_facet,
			String login) {

		long startTime = Calendar.getInstance().getTimeInMillis();

		MyntraService service = Myntra.getService(ServiceType.PORTAL_SEARCHSERVICE, APINAME.GETQUERY,
				init.Configurations, new String[] { query, return_docs, is_facet, login });
		RequestGenerator req = new RequestGenerator(service);
		System.out.println(service.URL);
		log.info(service.URL);
		System.out.println(service.Payload);
		if (req.respvalidate.DoesNodeExists(dataBasePath)) {
			if (req.respvalidate.DoesNodeExists(getGuidedNavFieldNodes().get(0))) {

				System.out.println("Respsonse of guidedNav node-- > GET(0)"
						+ req.respvalidate.NodeValue(getGuidedNavFieldNodes().get(0), true));
				System.out.println("Respsonse of guidedNav-- > GET(1)"
						+ req.respvalidate.NodeValue(getGuidedNavFieldNodes().get(1), true));
			}
		}
		System.out.println("get(0)******" + req.respvalidate.NodeValue(getGuidedNavFieldNodes().get(1), true));

		System.out.println(
				"\"totalProductsCount\\\":" + req.respvalidate.NodeValue(getGuidedNavFieldNodes().get(1), true));
		// System.out.println("\"productNode\\\":"
		// +req.respvalidate.NodeValue(getGuidedNavFieldNodes().get(3), true));

		String articleName = req.respvalidate.NodeValue(getArticleAttributeNodes().get(4), true);
		System.out.println("response" + articleName);
		String articleNameinLowercase = req.respvalidate.NodeValue(getArticleAttributeNodes().get(4), true)
				.toLowerCase();
		System.out.println("response1" + articleNameinLowercase);
		AssertJUnit.assertEquals("isFacet dosn't match",
				req.respvalidate.NodeValue(getQueryDataSearchNodes().get(9), true), is_facet);
		AssertJUnit.assertEquals("returnDocs dosn't match",
				req.respvalidate.NodeValue(getQueryDataSearchNodes().get(6), true), return_docs);
		AssertJUnit.assertTrue("totalProductsCount dosn't match",
				req.respvalidate.NodeValue(getQueryDataNodes().get(1), true).contains(
						"\"totalProductsCount\":" + req.respvalidate.NodeValue(getQueryDataNodes().get(5), true)));
		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;
		System.out.println("\nTime taken to execute - TestCase - SearchService_getQueryWithSuccessMessage : "
				+ timeTaken + " seconds\n");
		log.info("\nTime taken to execute - TestCase - SearchService_getQueryWithSuccessMessage : " + timeTaken
				+ " seconds\n");
	}

	/*
	 * Test-cases 1.13: POST SERVICE getQuery getting success response -
	 * validating Searchs node
	 * 
	 * @author jitender.kumar1
	 */

	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression", "SearchSanity",
			"SearchRegression" }, dataProvider = "SearchService_getQueryWithSearchDP")
	public void searchService_getQuery_validatingSearchNodes(String query, String return_docs, String is_facet,
			String login) {

		long startTime = Calendar.getInstance().getTimeInMillis();

		MyntraService service = Myntra.getService(ServiceType.PORTAL_SEARCHSERVICE, APINAME.GETQUERY,
				init.Configurations, new String[] { query, return_docs, is_facet, login });
		RequestGenerator req = new RequestGenerator(service);
		System.out.println(service.URL);
		log.info(service.URL);
		System.out.println(service.Payload);
		if (req.respvalidate.DoesNodeExists(dataBasePath)) {
			if (req.respvalidate.DoesNodesExists(getQuerySearchNodes())) {

				System.out.println("Respsonse of search node-- > GET(0)"
						+ req.respvalidate.NodeValue(getQuerySearchNodes().get(0), true));
				System.out.println("Respsonse of search node-- > GET(1)"
						+ req.respvalidate.NodeValue(getQuerySearchNodes().get(1), true));
				System.out.println("Respsonse of search node-- > GET(5)"
						+ req.respvalidate.NodeValue(getQuerySearchNodes().get(5), true));
				System.out.println("Respsonse of search node-- > GET(9)"
						+ req.respvalidate.NodeValue(getQuerySearchNodes().get(9), true));
			} else {
				System.out.println("\nQuery data  nodes are empty in SearchService GETQUERY API response\n");
				log.info("\nQuery data  nodes are empty in SearchService GETQUERY API response\n");
			}

			System.out.println("\n");
			log.info("\n");

		}

		else {

			System.out.println("\n data  node is empty in SearchService GETQUERY API response\n");
			log.info("\ndata  nodes is empty in SearchService GETQUERY API response\n");

		}

		AssertJUnit.assertEquals("isFacet dosn't match",
				req.respvalidate.NodeValue(getQueryDataSearchNodes().get(9), true), is_facet);
		AssertJUnit.assertEquals("returnDocs dosn't match",
				req.respvalidate.NodeValue(getQueryDataSearchNodes().get(6), true), return_docs);
		AssertJUnit.assertTrue("totalProductsCount dosn't match",
				req.respvalidate.NodeValue(getQueryDataNodes().get(1), true).contains(
						"\"totalProductsCount\":" + req.respvalidate.NodeValue(getQueryDataNodes().get(5), true)));
		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;
		System.out.println("\nTime taken to execute - TestCase - SearchService_getQueryWithSuccessMessage : "
				+ timeTaken + " seconds\n");
		log.info("\nTime taken to execute - TestCase - SearchService_getQueryWithSuccessMessage : " + timeTaken
				+ " seconds\n");
	}

	// ------------------------------------------------------------------------------------------------------------------------------

	/*
	 * Test-cases 1.14: POST SERVICE getQuery getting success response -
	 * Semantic Search
	 * 
	 * @author sneha.deep
	 */
	// @Test(groups = { "Sanity", "Regression", "MiniRegression",
	// "ExhaustiveRegression", "SearchSanity", "SearchRegression" })
	// public void SearchService_getQuery_semanticSearch() throws IOException
	// {
	//
	// long startTime = Calendar.getInstance().getTimeInMillis();
	//
	// //Creating hashmap to pass header for semantic search
	// HashMap<String, String> semanticSearchHeader = new HashMap<>();
	// String headerKey = "search.charles";
	// String headerValue = "enabled";
	// semanticSearchHeader.put(headerKey, headerValue);
	//
	// //Creating custom payload
	// String searchQuery =
	// tools.readFileAsString("/Users/m02167/Desktop/searchQuery.txt");
	// String return_docs = "true";
	// String is_facet = "true";
	// String login = "sneha.prasad@myntra.com";
	//
	// System.out.println(searchQuery);
	//
	// String[] arr = searchQuery.split("\\n");
	// int range = arr.length;
	//
	// for(int i=0;i<=range-1;i++)
	// {
	// //if(!arr[i].trim().equals(""))
	//
	// //{
	//
	// String injectQuery = arr[i];
	// MyntraService service = new
	// MyntraService(ServiceType.PORTAL_SEARCHSERVICE,
	// APINAME.GETQUERY,init.Configurations,
	// new String[] { injectQuery, return_docs, is_facet, login });
	// RequestGenerator req = new RequestGenerator(service,
	// semanticSearchHeader);
	// System.out.println(service.URL);
	// log.info(service.URL);
	// System.out.println(service.Payload);
	// String response = req.returnresponseasstring();
	//
	//
	// String responseQuery = JsonPath.read(response, "$.data.search.query");
	// //System.out.println("query parameter****:" +responseQuery);
	//
	// tools.WriteDatatoFile("/Users/m02167/Desktop/searchResponse.txt",
	// responseQuery, true);
	//
	// }
	// AssertJUnit.assertEquals("getQueryWithValidInputs is not working",200,
	// req.response.getStatus());
	//
	// long endTime = Calendar.getInstance().getTimeInMillis();
	// double timeTaken = (endTime - startTime)/1000.0;
	// System.out.println("\nTime taken to execute - TestCase -
	// SearchService_getQueryWithSuccessMessage : "+timeTaken+" seconds\n");
	// log.info("\nTime taken to execute - TestCase -
	// SearchService_getQueryWithSuccessMessage : "+timeTaken+" seconds\n");
	// }

	// ------------------------------------------------------------------------------------------------------------------------------

	// ------------------------------------------------Testcases for
	// getQueryGet-------------------------------------------------------
	/*
	 * Test-cases 2.1: GET SERVICE getQueryGET getting success response -
	 * default service
	 * 
	 * @author sneha.deep
	 */
	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression", "SearchSanity",
			"SearchRegression" }, dataProvider = "SearchService_getQueryGetWithSuccessMessageDP")
	public void SearchService_getQueryGetWithSuccessMessage(String query, String return_docs, String is_facet,
			String login, String rows) {

		long startTime = Calendar.getInstance().getTimeInMillis();

		MyntraService service = Myntra.getService(ServiceType.PORTAL_SEARCHSERVICE, APINAME.GETQUERYGET,
				init.Configurations);
		service.URL = apiUtil.prepareparameterizedURL(service.URL,
				new String[] { query, return_docs, is_facet, login, rows });
		RequestGenerator req = new RequestGenerator(service);
		System.out.println(service.URL);
		log.info(service.URL);
		// System.out.println(req.returnresponseasstring());

		AssertJUnit.assertEquals("SearchService_getQueryGetWithSuccessMessage is not working", 200,
				req.response.getStatus());

		AssertJUnit.assertTrue("Query dosn't match", req.respvalidate.NodeValue(getQueryDataSearchNodes().get(0), true)
				.toLowerCase().contains(query.toLowerCase()));
		AssertJUnit.assertEquals("rows dosn't match",
				req.respvalidate.NodeValue(getQueryDataSearchNodes().get(2), true), rows);
		AssertJUnit.assertEquals("returnDocs dosn't match",
				req.respvalidate.NodeValue(getQueryDataSearchNodes().get(6), true), return_docs);
		AssertJUnit.assertEquals("isFacet dosn't match",
				req.respvalidate.NodeValue(getQueryDataSearchNodes().get(9), true), is_facet);
		AssertJUnit.assertTrue("totalProductsCount dosn't match",
				req.respvalidate.NodeValue(getQueryDataNodes().get(1), true).contains(
						"\"totalProductsCount\":" + req.respvalidate.NodeValue(getQueryDataNodes().get(5), true)));

		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;
		System.out.println("\nTime taken to execute - TestCase - SearchService_getQueryWithSuccessMessage : "
				+ timeTaken + " seconds\n");
		log.info("\nTime taken to execute - TestCase - SearchService_getQueryWithSuccessMessage : " + timeTaken
				+ " seconds\n");
	}

	/*
	 * Test-cases 2.2: GET SERVICE getQueryGET having return_docs as true. As a
	 * result, product details json array should be populated.
	 * 
	 * @author sneha.deep
	 */
	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression",
			"SearchRegression" }, dataProvider = "SearchService_getQueryGetWithSuccessMessageDP")
	public void SearchService_getQueryGetWithReturnDocsAsTrue(String query, String return_docs, String is_facet,
			String login, String rows) {

		long startTime = Calendar.getInstance().getTimeInMillis();

		MyntraService service = Myntra.getService(ServiceType.PORTAL_SEARCHSERVICE, APINAME.GETQUERYGET,
				init.Configurations);
		service.URL = apiUtil.prepareparameterizedURL(service.URL,
				new String[] { query, return_docs, is_facet, login, rows });
		RequestGenerator req = new RequestGenerator(service);
		System.out.println(service.URL);
		log.info(service.URL);
		String response = req.returnresponseasstring();
		System.out.println(req.returnresponseasstring());

		AssertJUnit.assertEquals("SearchService_getQueryGetWithReturnDocsAsTrue is not working", 200,
				req.response.getStatus());

		// verify products details
		Object responseProducts = JsonPath.read(response, "$.data.queryResult.response1.products");
		String responseProductsStr = responseProducts.toString();
		Boolean responseProductsBool = responseProductsStr.contains("id");
		System.out.println("value of responseProductsBool: " + responseProductsBool);
		if (responseProductsBool) {
			AssertJUnit.assertEquals("SearchService_getQueryGetWithReturnDocsAsTrue is not working", 200,
					req.response.getStatus());
		} else {
			AssertJUnit.assertTrue("Product Count should be 0", responseProductsBool);
		}

		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;
		System.out.println("\nTime taken to execute - TestCase - SearchService_getQueryWithSuccessMessage : "
				+ timeTaken + " seconds\n");
		log.info("\nTime taken to execute - TestCase - SearchService_getQueryWithSuccessMessage : " + timeTaken
				+ " seconds\n");
	}

	/*
	 * Test-cases 2.3: GET SERVICE getQueryGET having return_docs as false. As a
	 * result, product details json array should not be populated.
	 * 
	 * @author sneha.deep
	 */
	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression",
			"SearchRegression" }, dataProvider = "SearchService_getQueryGetWithReturnDocsAsFalseDP")
	public void SearchService_getQueryGetWithReturnDocsAsFalse(String query, String return_docs, String is_facet,
			String login, String rows) {

		long startTime = Calendar.getInstance().getTimeInMillis();

		MyntraService service = Myntra.getService(ServiceType.PORTAL_SEARCHSERVICE, APINAME.GETQUERYGET,
				init.Configurations);
		service.URL = apiUtil.prepareparameterizedURL(service.URL,
				new String[] { query, return_docs, is_facet, login, rows });
		RequestGenerator req = new RequestGenerator(service);
		System.out.println(service.URL);
		log.info(service.URL);
		String response = req.returnresponseasstring();
		System.out.println(req.returnresponseasstring());

		// verify products details
		Object responseProducts = JsonPath.read(response, "$.data.queryResult.response1.products");
		String responseProductsStr = responseProducts.toString();
		Boolean responseProductsBool = responseProductsStr.contains("id");
		System.out.println("value of responseProductsBool: " + responseProductsBool);
		if (responseProductsBool) {
			AssertJUnit.assertTrue("Product Count should be 0", responseProductsBool);
		} else {
			AssertJUnit.assertEquals("SearchService_getQueryGetWithReturnDocsAsFalse is not working", 200,
					req.response.getStatus());
		}

		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;
		System.out.println("\nTime taken to execute - TestCase - SearchService_getQueryWithSuccessMessage : "
				+ timeTaken + " seconds\n");
		log.info("\nTime taken to execute - TestCase - SearchService_getQueryWithSuccessMessage : " + timeTaken
				+ " seconds\n");
	}

	/*
	 * Test-cases 2.4: GET SERVICE getQueryGET having is_facet as true. As a
	 * result, filters details json array should be populated.
	 * 
	 * @author sneha.deep
	 */
	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression",
			"SearchRegression" }, dataProvider = "SearchService_getQueryGetWithSuccessMessageDP")
	public void SearchService_getQueryGetWithIsFacetAsTrue(String query, String return_docs, String is_facet,
			String login, String rows) {

		long startTime = Calendar.getInstance().getTimeInMillis();

		MyntraService service = Myntra.getService(ServiceType.PORTAL_SEARCHSERVICE, APINAME.GETQUERYGET,
				init.Configurations);
		service.URL = apiUtil.prepareparameterizedURL(service.URL,
				new String[] { query, return_docs, is_facet, login, rows });
		RequestGenerator req = new RequestGenerator(service);
		System.out.println(service.URL);
		log.info(service.URL);
		String response = req.returnresponseasstring();
		System.out.println(req.returnresponseasstring());

		// verify filters
		Object responseProducts = JsonPath.read(response, "$.data.queryResult.response1.filters");
		String responseProductsStr = responseProducts.toString();
		Boolean responseProductsBool = responseProductsStr.contains("sizes_facet");
		// System.out.println("value of responseProductsBool:
		// "+responseProductsBool);
		if (responseProductsBool) {
			AssertJUnit.assertEquals("SearchService_getQueryGetWithIsFacetAsTrue is not working", 200,
					req.response.getStatus());
		} else {
			AssertJUnit.assertTrue("Filters array should not be blank", responseProductsBool);
		}

		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;
		System.out.println("\nTime taken to execute - TestCase - SearchService_getQueryWithSuccessMessage : "
				+ timeTaken + " seconds\n");
		log.info("\nTime taken to execute - TestCase - SearchService_getQueryWithSuccessMessage : " + timeTaken
				+ " seconds\n");
	}

	/*
	 * Test-cases 2.5: GET SERVICE getQueryGET having is_facet as false. As a
	 * result, filters details json array should not be populated.
	 * 
	 * @author sneha.deep
	 */
	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression",
			"SearchRegression" }, dataProvider = "SearchService_getQueryGetWithIsFacetAsFalseDP")
	public void SearchService_getQueryGetWithIsFacetAsFalse(String query, String return_docs, String is_facet,
			String login, String rows) {

		long startTime = Calendar.getInstance().getTimeInMillis();

		MyntraService service = Myntra.getService(ServiceType.PORTAL_SEARCHSERVICE, APINAME.GETQUERYGET,
				init.Configurations);
		service.URL = apiUtil.prepareparameterizedURL(service.URL,
				new String[] { query, return_docs, is_facet, login, rows });
		RequestGenerator req = new RequestGenerator(service);
		System.out.println(service.URL);
		log.info(service.URL);
		String response = req.returnresponseasstring();
		// System.out.println(req.returnresponseasstring());

		// verify filters
		Object responseProducts = JsonPath.read(response, "$.data.queryResult.response1.filters");
		String responseProductsStr = responseProducts.toString();
		// System.out.println("response product: "+responseProductsStr);
		Boolean responseProductsBool = responseProductsStr.contains("sizes_facet");
		// System.out.println("value of responseProductsBool:
		// "+responseProductsBool);
		if (!responseProductsBool) {
			AssertJUnit.assertEquals("SearchService_getQueryGetWithIsFacetAsFalse is not working", 200,
					req.response.getStatus());
		} else {
			AssertJUnit.assertTrue("Filters array should be blank", responseProductsBool);
		}

		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;
		System.out.println("\nTime taken to execute - TestCase - SearchService_getQueryWithSuccessMessage : "
				+ timeTaken + " seconds\n");
		log.info("\nTime taken to execute - TestCase - SearchService_getQueryWithSuccessMessage : " + timeTaken
				+ " seconds\n");
	}

	/*
	 * Test-cases 2.6: GET SERVICE getQueryGET having is_facet and return_docs
	 * as true. Node Validation of root node "data".
	 * 
	 * @author jitender.kumar1
	 */

	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression",
			"SearchRegression" }, dataProvider = "SearchService_getQueryGetWithValidationRoot")
	public void SearchService_getQueryGetValidation(String query, String return_docs, String is_facet, String login,
			String rows) {

		long startTime = Calendar.getInstance().getTimeInMillis();

		MyntraService service = Myntra.getService(ServiceType.PORTAL_SEARCHSERVICE, APINAME.GETQUERYGET,
				init.Configurations);
		service.URL = apiUtil.prepareparameterizedURL(service.URL,
				new String[] { query, return_docs, is_facet, login, rows });
		RequestGenerator req = new RequestGenerator(service);
		System.out.println(service.URL);
		log.info(service.URL);
		String response = req.returnresponseasstring();

		Object totalProductsCount = JsonPath.read(response, "$.data.queryResult.response1.totalProductsCount");
		String totalProductsStr = totalProductsCount.toString();
		Object responseProducts = JsonPath.read(response, "$.data.queryResult.response1.filters");
		String responseProductsStr = responseProducts.toString();
		Boolean responseProductsBool = responseProductsStr.contains("sizes_facet");

		if (req.respvalidate.DoesNodeExists(dataBasePath)) {
			if (req.respvalidate.DoesNodesExists(getQueryDataNodes())) {
				String rootNode = req.respvalidate.NodeValue(getQueryDataNodes().get(1), true);
				System.out.println("Data" + rootNode);
				String productCount = req.respvalidate.NodeValue(getQueryDataNodes().get(5), true);
				System.out.println(productCount);
				System.out.println(req.respvalidate.NodeValue(getQueryDataNodes().get(6), true));

			} else {
				System.out.println("\nQuery data  nodes are empty in SearchService GETQUERYGET API response\n");
				log.info("\nQuery data  nodes are empty in SearchService GETQUERYGET API response\n");
			}

			System.out.println("\n");
			log.info("\n");

		}

		else {

			System.out.println("\n data  node is empty in SearchService GETQUERYGET API response\n");
			log.info("\ndata  nodes is empty in SearchService GETQUERYGET API response\n");

		}

		if (!responseProductsBool) {
			AssertJUnit.assertEquals("SearchService_getQueryGetWithIsFacetAsFalse is not working", 200,
					req.response.getStatus());
		} else {
			AssertJUnit.assertTrue("Filters array should be blank", responseProductsBool);
		}
		// AssertJUnit.assertEquals("Product Count is different --->",
		// productCount, totalProductsStr);
		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;
		System.out.println("\nTime taken to execute - TestCase - SearchService_getQueryWithSuccessMessage : "
				+ timeTaken + " seconds\n");
		log.info("\nTime taken to execute - TestCase - SearchService_getQueryWithSuccessMessage : " + timeTaken
				+ " seconds\n");
	}

	/*
	 * Test-cases 2.7: GET SERVICE getQueryGET having is_facet and return_docs
	 * as true. Node Validation of search node.
	 * 
	 * @author jitender.kumar1
	 */

	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression",
			"SearchRegression" }, dataProvider = "SearchService_getQueryGetWithValidationSearch")
	public void SearchService_getQueryGetValidationSearchNode(String query, String return_docs, String is_facet,
			String login, String rows) {

		long startTime = Calendar.getInstance().getTimeInMillis();

		MyntraService service = Myntra.getService(ServiceType.PORTAL_SEARCHSERVICE, APINAME.GETQUERYGET,
				init.Configurations);
		service.URL = apiUtil.prepareparameterizedURL(service.URL,
				new String[] { query, return_docs, is_facet, login, rows });
		RequestGenerator req = new RequestGenerator(service);
		System.out.println(service.URL);
		log.info(service.URL);
		String response = req.returnresponseasstring();

		String searchNodeResponse = req.respvalidate.NodeValue(getQueryDataSearchNodes().get(0), true);
		System.out.println("Respsonse of search node-- > GET(0)" + searchNodeResponse);
		String productCount = req.respvalidate.NodeValue(getQueryDataNodes().get(5), true);
		System.out.println(productCount);
		// System.out.println(req.respvalidate.NodeValue(getQueryDataSearchNodes().get(6),
		// true));
		Object totalProductsCount = JsonPath.read(response, "$.data.queryResult.response1.totalProductsCount");
		String totalProductsStr = totalProductsCount.toString();
		Object responseProducts = JsonPath.read(response, "$.data.queryResult.response1.filters");
		String responseProductsStr = responseProducts.toString();

		Boolean responseProductsBool = responseProductsStr.contains("sizes_facet");

		if (req.respvalidate.DoesNodeExists(dataBasePath)) {
			if (req.respvalidate.DoesNodesExists(getQueryDataSearchNodes())) {

				System.out.println(req.respvalidate.NodeValue(getQueryDataSearchNodes().get(7), true));
				System.out.println(req.respvalidate.NodeValue(getQueryDataSearchNodes().get(5), true));
				AssertJUnit.assertTrue("Query didn't matched\n", req.respvalidate
						.NodeValue(getQueryDataSearchNodes().get(0), true).toLowerCase().contains(query.toLowerCase()));
				AssertJUnit.assertEquals("return_docs is false",
						req.respvalidate.NodeValue(getQueryDataSearchNodes().get(6), true), return_docs);

			} else {
				System.out.println("\nSearch data  nodes are empty in SearchService GETQUERYGET API response\n");
				log.info("\nSearch data  nodes are empty in SearchService GETQUERYGET API response\n");
			}

			System.out.println("\n");
			log.info("\n");

		}

		else {

			System.out.println("\n data  node is empty in SearchService GETQUERYGET API response\n");
			log.info("\ndata  nodes is empty in SearchService GETQUERYGET API response\n");

		}

		if (!responseProductsBool) {
			AssertJUnit.assertEquals("SearchService_getQueryGetWithIsFacetAsFalse is not working", 200,
					req.response.getStatus());
		} else {
			AssertJUnit.assertTrue("Filters array should be blank", responseProductsBool);
		}

		if (!responseProductsBool) {
			AssertJUnit.assertEquals("SearchService_getQueryGetWithIsFacetAsFalse is not working", 200,
					req.response.getStatus());
		} else {
			AssertJUnit.assertTrue("Filters array should be blank", responseProductsBool);
		}

		// AssertJUnit.assertEquals("Product Count is different --->",
		// productCount, totalProductsStr);

		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;
		System.out.println("\nTime taken to execute - TestCase - SearchService_getQueryWithSuccessMessage : "
				+ timeTaken + " seconds\n");
		log.info("\nTime taken to execute - TestCase - SearchService_getQueryWithSuccessMessage : " + timeTaken
				+ " seconds\n");
	}

	/*
	 * Test-cases 2.8: GET SERVICE getQueryGET having is_facet and return_docs
	 * as true. Node Validation of queryResult node.
	 * 
	 * @author jitender.kumar1
	 */

	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression",
			"SearchRegression" }, dataProvider = "SearchService_getQueryGetWithValidationQueryResult")
	public void SearchService_getQueryGetValidationQueryResultNode(String query, String return_docs, String is_facet,
			String login, String rows) {

		long startTime = Calendar.getInstance().getTimeInMillis();

		MyntraService service = Myntra.getService(ServiceType.PORTAL_SEARCHSERVICE, APINAME.GETQUERYGET,
				init.Configurations);
		service.URL = apiUtil.prepareparameterizedURL(service.URL,
				new String[] { query, return_docs, is_facet, login, rows });
		RequestGenerator req = new RequestGenerator(service);
		System.out.println(service.URL);
		log.info(service.URL);
		String response = req.returnresponseasstring();

		if (req.respvalidate.DoesNodeExists(dataBasePath)) {
			if (req.respvalidate.DoesNodeExists(getQueryResultNodes().get(0))) {
				String searchNode = req.respvalidate.NodeValue(getQueryResultNodes().get(0), true);
				System.out.println("Data--> GET(0)--->" + searchNode);

				System.out.println(
						"Product count is not there" + req.respvalidate.NodeValue(getQueryResultNodes().get(1), true));
				String totalProductsCount = req.respvalidate.NodeValue(getQueryResultNodes().get(1), true);

				String totalProductsStr = totalProductsCount.toString();
				String productCount = req.respvalidate.NodeValue(getQueryDataNodes().get(5), true);
				System.out.println(productCount);

				AssertJUnit.assertEquals("Product count is not same", totalProductsStr, productCount);

				System.out.println(req.respvalidate.NodeValue(getQueryResultNodes().get(3), true));
				// System.out.println(req.respvalidate.NodeValue(getQueryResultNodes().get(5),
				// true));

			} else {
				System.out.println("\nQuery Result data  nodes are empty in SearchService GETQUERYGET API response\n");
				log.info("\nQuery Result data  nodes are empty in SearchService GETQUERYGET API response\n");
			}

			System.out.println("\n");
			log.info("\n");

		}

		else {

			System.out.println("\n data  node is empty in SearchService GETQUERYGET API response\n");
			log.info("\ndata  nodes is empty in SearchService GETQUERYGET API response\n");

		}

		Object responseProducts = JsonPath.read(response, "$.data.queryResult.response1.filters");
		String responseProductsStr = responseProducts.toString();

		Boolean responseProductsBool = responseProductsStr.contains("sizes_facet");

		if (!responseProductsBool) {
			AssertJUnit.assertEquals("SearchService_getQueryGetWithIsFacetAsFalse is not working", 200,
					req.response.getStatus());
		} else {
			AssertJUnit.assertTrue("Filters array should be blank", responseProductsBool);
		}
		// AssertJUnit.assertEquals("Product Count is different --->",
		// productCount, totalProductsStr);

		AssertJUnit.assertTrue("Query didn't matched\n", req.respvalidate
				.NodeValue(getQueryDataSearchNodes().get(0), true).toLowerCase().contains(query.toLowerCase()));
		AssertJUnit.assertEquals("return_docs is false",
				req.respvalidate.NodeValue(getQueryDataSearchNodes().get(6), true), return_docs);

		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;
		System.out.println("\nTime taken to execute - TestCase - SearchService_getQueryWithSuccessMessage : "
				+ timeTaken + " seconds\n");
		log.info("\nTime taken to execute - TestCase - SearchService_getQueryWithSuccessMessage : " + timeTaken
				+ " seconds\n");

	}

	/*
	 * Test-cases 2.9: GET SERVICE getQueryGET having is_facet and return_docs
	 * as true. Node Validation of solr_sort node.
	 * 
	 * @author jitender.kumar1
	 */

	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression",
			"SearchRegression" }, dataProvider = "SearchService_getQueryGetWithValidationSolr")
	public void SearchService_getQueryGetValidationSolrSortNode(String query, String return_docs, String is_facet,
			String login, String rows) {

		long startTime = Calendar.getInstance().getTimeInMillis();

		MyntraService service = Myntra.getService(ServiceType.PORTAL_SEARCHSERVICE, APINAME.GETQUERYGET,
				init.Configurations);
		service.URL = apiUtil.prepareparameterizedURL(service.URL,
				new String[] { query, return_docs, is_facet, login, rows });
		RequestGenerator req = new RequestGenerator(service);
		System.out.println(service.URL);
		log.info(service.URL);
		String response = req.returnresponseasstring();
		String solrSortResponse = JsonPath.read(response, "$.data.solr_sort_field").toString();

		System.out.println("Solr Node Response--->" + solrSortResponse);

		if (req.respvalidate.DoesNodeExists(dataBasePath)) {
			if (req.respvalidate.DoesNodeExists(getSolrNodes().get(0))) {
				System.out.println(
						"Respsonse of solr node-- > GET(0)" + req.respvalidate.NodeValue(getSolrNodes().get(0), true));
				System.out.println(
						"Respsonse of solr node-- > GET(1)" + req.respvalidate.NodeValue(getSolrNodes().get(1), true));
				System.out.println(
						"Respsonse of solr node-- > GET(2)" + req.respvalidate.NodeValue(getSolrNodes().get(2), true));
				System.out.println(
						"Respsonse of solr node-- > GET(3)" + req.respvalidate.NodeValue(getSolrNodes().get(3), true));
				System.out.println(
						"Respsonse of solr node-- > GET(4)" + req.respvalidate.NodeValue(getSolrNodes().get(4), true));

			} else {
				System.out.println("\nQuery Result data  nodes are empty in SearchService GETQUERYGET API response\n");
				log.info("\nQuery Result data  nodes are empty in SearchService GETQUERYGET API response\n");
			}

			System.out.println("\n");
			log.info("\n");

		}

		else {

			System.out.println("\n data  node is empty in SearchService GETQUERYGET API response\n");
			log.info("\ndata  nodes is empty in SearchService GETQUERYGET API response\n");

		}

		String productCount = req.respvalidate.NodeValue(getQueryDataNodes().get(5), true);
		System.out.println(productCount);

		String totalProductsCount = req.respvalidate.NodeValue(getQueryResultNodes().get(1), true);
		String totalProductsStr = totalProductsCount.toString();
		Object responseProducts = JsonPath.read(response, "$.data.queryResult.response1.filters");
		String responseProductsStr = responseProducts.toString();
		System.out.println("get(3)******" + req.respvalidate.NodeValue(getQueryResultNodes().get(3), true));
		System.out
				.println("\"totalProductsCount\\\":" + req.respvalidate.NodeValue(getQueryResultNodes().get(1), true));

		Boolean responseProductsBool = responseProductsStr.contains("sizes_facet");

		if (!responseProductsBool) {
			AssertJUnit.assertEquals("SearchService_getQueryGetWithIsFacetAsFalse is not working", 200,
					req.response.getStatus());
		} else {
			AssertJUnit.assertTrue("Filters array should be blank", responseProductsBool);
		}

		AssertJUnit.assertEquals("Product Count is different --->", productCount, totalProductsStr);
		AssertJUnit.assertTrue("Query didn't matched\n", req.respvalidate
				.NodeValue(getQueryDataSearchNodes().get(0), true).toLowerCase().contains(query.toLowerCase()));
		AssertJUnit.assertEquals("return_docs is false",
				req.respvalidate.NodeValue(getQueryDataSearchNodes().get(6), true), return_docs);

		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;
		System.out.println("\nTime taken to execute - TestCase - SearchService_getQueryWithSuccessMessage : "
				+ timeTaken + " seconds\n");
		log.info("\nTime taken to execute - TestCase - SearchService_getQueryWithSuccessMessage : " + timeTaken
				+ " seconds\n");
	}

	/*
	 * Test-cases 2.10: GET SERVICE getQueryGET having is_facet and return_docs
	 * as true. Node Validation of getguidednav node.
	 * 
	 * @author jitender.kumar1
	 */

	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression",
			"SearchRegression" }, dataProvider = "SearchService_getQueryGetWithValidationGuidedNav")
	public void SearchService_getQueryGetValidationGuidedNavFieldNode(String query, String return_docs, String is_facet,
			String login, String rows) {

		long startTime = Calendar.getInstance().getTimeInMillis();

		MyntraService service = Myntra.getService(ServiceType.PORTAL_SEARCHSERVICE, APINAME.GETQUERYGET,
				init.Configurations);
		service.URL = apiUtil.prepareparameterizedURL(service.URL,
				new String[] { query, return_docs, is_facet, login, rows });
		RequestGenerator req = new RequestGenerator(service);
		System.out.println(service.URL);
		log.info(service.URL);
		String response = req.returnresponseasstring();
		String guidedNavResponse = JsonPath.read(response, "$.data.guidedNavField").toString();

		System.out.println("guidedNav Node Response--->" + guidedNavResponse);
		if (req.respvalidate.DoesNodeExists(dataBasePath)) {
			if (req.respvalidate.DoesNodeExists(getGuidedNavFieldNodes().get(0))) {

				System.out.println("Respsonse of guidedNav node-- > GET(0)"
						+ req.respvalidate.NodeValue(getGuidedNavFieldNodes().get(0), true));
				System.out.println("Respsonse of guidedNav-- > GET(1)"
						+ req.respvalidate.NodeValue(getGuidedNavFieldNodes().get(1), true));
			}
		}
		String productCount = req.respvalidate.NodeValue(getQueryDataNodes().get(5), true);
		System.out.println(productCount);

		String totalProductsCount = req.respvalidate.NodeValue(getQueryResultNodes().get(1), true);
		String totalProductsStr = totalProductsCount.toString();
		Object responseProducts = JsonPath.read(response, "$.data.queryResult.response1.filters");
		String responseProductsStr = responseProducts.toString();
		System.out.println("get(3)******" + req.respvalidate.NodeValue(getQueryResultNodes().get(3), true));
		System.out
				.println("\"totalProductsCount\\\":" + req.respvalidate.NodeValue(getQueryResultNodes().get(1), true));
		// System.out.println("get(5)***curated_Product***"
		// +req.respvalidate.NodeValue(getQueryResultNodes().get(5), true));

		Boolean responseProductsBool = responseProductsStr.contains("sizes_facet");

		if (!responseProductsBool) {
			AssertJUnit.assertEquals("SearchService_getQueryGetWithIsFacetAsFalse is not working", 200,
					req.response.getStatus());
		} else {
			AssertJUnit.assertTrue("Filters array should be blank", responseProductsBool);
		}
		AssertJUnit.assertEquals("Product Count is different --->", productCount, totalProductsStr);
		AssertJUnit.assertTrue("Query didn't matched\n", req.respvalidate
				.NodeValue(getQueryDataSearchNodes().get(0), true).toLowerCase().contains(query.toLowerCase()));
		AssertJUnit.assertEquals("return_docs is false",
				req.respvalidate.NodeValue(getQueryDataSearchNodes().get(6), true), return_docs);
		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;
		System.out.println("\nTime taken to execute - TestCase - SearchService_getQueryWithSuccessMessage : "
				+ timeTaken + " seconds\n");
		log.info("\nTime taken to execute - TestCase - SearchService_getQueryWithSuccessMessage : " + timeTaken
				+ " seconds\n");
	}

	/*
	 * //-----------------------------------------------------------------------
	 * ------------------------------------------------------
	 * 
	 * //------------------------------------------------Method to create custom
	 * payload----------------------------------------------- private String
	 * getPayloadAsString(String payloadName,String[] payloadparams) { String
	 * customPayloadDir = System.getProperty("user.dir") + File.separator +
	 * "Data" + File.separator + "payloads" + File.separator + "JSON";
	 * StringBuffer sb = new StringBuffer(); String finalPayload = ""; Scanner
	 * sc; try { sc = new Scanner(new File(customPayloadDir + File.separator +
	 * payloadName)); while (sc.hasNextLine()) sb.append(sc.nextLine() + "\n");
	 * } catch (FileNotFoundException e) { e.printStackTrace(); }
	 * 
	 * finalPayload = sb.toString(); for (int i = 0; i < payloadparams.length;
	 * i++) { finalPayload = finalPayload.replace("${" + i + "}",
	 * payloadparams[i]); } return finalPayload; }
	 */

	/*
	 * Test-cases 3.1: filteredSearch for brands for success message - default
	 * 
	 * @author sneha.deep
	 */
	/*
	 * @Test(groups = { "Sanity", "Regression", "MiniRegression",
	 * "ExhaustiveRegression", "SearchSanity" }, dataProvider =
	 * "SearchService_filteredSearchForBrandsDP") public void
	 * SearchService_filteredSearchForBrands(String brands) { long startTime =
	 * Calendar.getInstance().getTimeInMillis(); String payloadParams[] = new
	 * String[] {"${0}",brands}; String customPayload =
	 * getPayloadAsString("filteredsearchbrand", payloadParams);
	 * System.out.println("final payload created is:" +customPayload);
	 * 
	 * MyntraService service = new
	 * MyntraService(ServiceType.PORTAL_SEARCHSERVICE,
	 * APINAME.FILTEREDSEARCHBRAND, init.Configurations, customPayload, brands);
	 * System.out.println(service.Payload); RequestGenerator req = new
	 * RequestGenerator(service); log.info(service.URL);
	 * System.out.println(req.returnresponseasstring());
	 * AssertJUnit.assertEquals(
	 * "SearchService_filteredSearchForBrands is not working", 200,
	 * req.response.getStatus());
	 * 
	 * long endTime = Calendar.getInstance().getTimeInMillis(); double timeTaken
	 * = (endTime - startTime)/1000.0; System.out.println(
	 * "\nTime taken to execute - TestCase - SearchService_filteredSearchForBrands : "
	 * +timeTaken+" seconds\n"); log.info(
	 * "\nTime taken to execute - TestCase - SearchService_filteredSearchForBrands : "
	 * +timeTaken+" seconds\n");
	 * 
	 * }
	 */
	// -------------------------------------------------------------------------------------------------------------------------------------

	// -----------------------------------------Test-case for filtered
	// search----------------------------------------------------------------

	/*
	 * Test-cases 3.1: filteredSearch for brands for success message and having
	 * products count greater than zero
	 * 
	 * @author sneha.deep
	 */
	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression", "SearchSanity",
			"SearchRegression" }, dataProvider = "SearchService_filteredSearchDP")
	public void SearchService_filteredSearch(String brands) {
		long startTime = Calendar.getInstance().getTimeInMillis();

		MyntraService service = Myntra.getService(ServiceType.PORTAL_SEARCHSERVICE, APINAME.FILTEREDSEARCH,
				init.Configurations, new String[] { brands });
		RequestGenerator req = new RequestGenerator(service);
		System.out.println(service.URL);
		log.info(service.URL);
		System.out.println("FIleterd paylaod------>>>>>\n" + service.Payload);
		System.out.println(req.returnresponseasstring());
		String resp = req.respvalidate.returnresponseasstring();
		String productCountStr = req.respvalidate.GetNodeValue("response1.totalProductsCount", resp);
		int productCount = Integer.parseInt(productCountStr);
		System.out.println("productcount:" + productCount);
		// System.out.println("Product Count : "+productCount);

		AssertJUnit.assertEquals("SearchService_filteredSearch is not working", 200, req.response.getStatus());
		AssertJUnit.assertTrue("Product Count should be > 0", productCount > 1);

		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;
		System.out.println("\nTime taken to execute - TestCase - SearchService_getQueryWithSuccessMessage : "
				+ timeTaken + " seconds\n");
		log.info("\nTime taken to execute - TestCase - SearchService_getQueryWithSuccessMessage : " + timeTaken
				+ " seconds\n");
	}

	/*
	 * Test-cases 3.2: filteredSearch for brands for success message and having
	 * products count greater than zero and filtered root node exists or not
	 * 
	 * @author jitender.kumar1
	 */
	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression", "SearchSanity",
			"SearchRegression" }, dataProvider = "SearchService_filteredSearchRootNodeDP")
	public void SearchService_filteredSearchNodeValidation(String brands) {
		long startTime = Calendar.getInstance().getTimeInMillis();

		MyntraService service = Myntra.getService(ServiceType.PORTAL_SEARCHSERVICE, APINAME.FILTEREDSEARCH,
				init.Configurations, new String[] { brands });
		RequestGenerator req = new RequestGenerator(service);
		System.out.println(service.URL);
		log.info(service.URL);
		System.out.println("FIleterd paylaod------>>>>>\n" + service.Payload);
		System.out.println(req.returnresponseasstring());
		String resp = req.respvalidate.returnresponseasstring();
		String productCountStr = req.respvalidate.GetNodeValue("response1.totalProductsCount", resp);
		int productCount = Integer.parseInt(productCountStr);
		System.out.println("productcount:" + productCount);

		if (req.respvalidate.DoesNodesExists(getResponse1Nodes())) {
			System.out.println(
					"get(0)----ProductCount---->" + req.respvalidate.NodeValue(getResponse1Nodes().get(0), true));
			System.out.println("Get(2)--> " + req.respvalidate.NodeValue(getResponse1Nodes().get(2), true));
			AssertJUnit.assertEquals("SearchService_filteredSearch is not working", 200, req.response.getStatus());
			AssertJUnit.assertTrue("Product Count should be > 0", productCount > 1);

			AssertJUnit.assertTrue("Get(1)--> doesn't contains sizes-facet",
					req.respvalidate.NodeValue(getResponse1Nodes().get(1), true).toLowerCase().contains("sizes_facet"));

		} else {
			System.out.println("Node is not present");
		}

		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;
		System.out.println("\nTime taken to execute - TestCase - SearchService_getQueryWithSuccessMessage : "
				+ timeTaken + " seconds\n");
		log.info("\nTime taken to execute - TestCase - SearchService_getQueryWithSuccessMessage : " + timeTaken
				+ " seconds\n");
	}

	/*
	 * Test-cases 3.3: filteredSearch for brands for success message and having
	 * products count greater than zero and filtered root node validation
	 * 
	 * @author jitender.kumar1
	 */
	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression", "SearchSanity",
			"SearchRegression" }, dataProvider = "SearchService_filteredSearchRootNodeDP")
	public void SearchService_filteredSearchNode1Validation(String brands) {
		long startTime = Calendar.getInstance().getTimeInMillis();

		MyntraService service = Myntra.getService(ServiceType.PORTAL_SEARCHSERVICE, APINAME.FILTEREDSEARCH,
				init.Configurations, new String[] { brands });
		RequestGenerator req = new RequestGenerator(service);
		System.out.println(service.URL);
		log.info(service.URL);
		System.out.println("FIleterd paylaod------>>>>>\n" + service.Payload);
		System.out.println(req.returnresponseasstring());
		String resp = req.respvalidate.returnresponseasstring();
		String productCountStr = req.respvalidate.GetNodeValue("response1.totalProductsCount", resp);
		int productCount = Integer.parseInt(productCountStr);
		System.out.println("productcount:" + productCount);

		System.out
				.println("get(0)----ProductCount---->" + req.respvalidate.NodeValue(getResponse1Nodes().get(0), true));

		System.out.println("Get(2)--> " + req.respvalidate.NodeValue(getResponse1Nodes().get(2), true));
		AssertJUnit.assertEquals("SearchService_filteredSearch is not working", 200, req.response.getStatus());
		AssertJUnit.assertTrue("Product Count should be > 0", productCount > 1);

		AssertJUnit.assertTrue("Get(1)--> doesn't contains sizes-facet",
				req.respvalidate.NodeValue(getResponse1Nodes().get(1), true).toLowerCase().contains("sizes_facet"));

		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;
		System.out.println("\nTime taken to execute - TestCase - SearchService_getQueryWithSuccessMessage : "
				+ timeTaken + " seconds\n");
		log.info("\nTime taken to execute - TestCase - SearchService_getQueryWithSuccessMessage : " + timeTaken
				+ " seconds\n");
	}

	/*
	 * Test-cases 3.3: filteredSearch for brands for success message and having
	 * products count greater than zero and filtered root node validation
	 * 
	 * @author jitender.kumar1
	 */
	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression", "SearchSanity",
			"SearchRegression" }, dataProvider = "SearchService_filteredSearchFilterRootNodeDP")
	public void SearchService_filteredSearchfilterNodeValidation(String brands) {
		long startTime = Calendar.getInstance().getTimeInMillis();

		MyntraService service = Myntra.getService(ServiceType.PORTAL_SEARCHSERVICE, APINAME.FILTEREDSEARCH,
				init.Configurations, new String[] { brands });
		RequestGenerator req = new RequestGenerator(service);
		System.out.println(service.URL);
		log.info(service.URL);
		System.out.println("FIleterd paylaod------>>>>>\n" + service.Payload);
		System.out.println(req.returnresponseasstring());
		String resp = req.respvalidate.returnresponseasstring();
		String productCountStr = req.respvalidate.GetNodeValue("response1.totalProductsCount", resp);
		int productCount = Integer.parseInt(productCountStr);
		System.out.println("productcount:" + productCount);

		if (req.respvalidate.DoesNodeExists(response1BasePath)) {
			if (req.respvalidate.DoesNodesExists(getFilterNodes())) {

				System.out.println(
						"get(0)----ProductCount---->" + req.respvalidate.NodeValue(getFilterNodes().get(0), true));

				System.out.println("Get(2)--> " + req.respvalidate.NodeValue(getFilterNodes().get(2), true));
				System.out.println("Get(3)--> " + req.respvalidate.NodeValue(getFilterNodes().get(3), true));
				System.out.println("Get(4)--> " + req.respvalidate.NodeValue(getFilterNodes().get(4), true));
				System.out.println("Get(5)--> " + req.respvalidate.NodeValue(getFilterNodes().get(5), true));
				System.out.println("Get(6)--> " + req.respvalidate.NodeValue(getFilterNodes().get(6), true));
				System.out.println("Get(7)--> " + req.respvalidate.NodeValue(getFilterNodes().get(7), true));
				System.out.println("Get(8)--> " + req.respvalidate.NodeValue(getFilterNodes().get(8), true));

				AssertJUnit.assertEquals("SearchService_filteredSearch is not working", 200, req.response.getStatus());
				AssertJUnit.assertTrue("Product Count should be > 0", productCount > 1);

				AssertJUnit.assertTrue("Get(1)--> doesn't contains sizes-facet", req.respvalidate
						.NodeValue(getResponse1Nodes().get(1), true).toLowerCase().contains("sizes_facet"));
			}
		}

		else {
			System.out.println("\ndata tag nodes are empty in FilteredSearchService filter node API response\n");
			log.info("\ndata tag nodes are empty in FilteredSearchService filter node API response\n");
		}

		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;
		System.out.println("\nTime taken to execute - TestCase - SearchService_getQueryWithSuccessMessage : "
				+ timeTaken + " seconds\n");
		log.info("\nTime taken to execute - TestCase - SearchService_getQueryWithSuccessMessage : " + timeTaken
				+ " seconds\n");
	}

	/*
	 * Test-cases 3.4: filteredSearch for brands for success message and having
	 * products count greater than zero and product node validation
	 * 
	 * @author jitender.kumar1
	 */
	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression", "SearchSanity",
			"SearchRegression" }, dataProvider = "SearchService_filteredSearchProductNodeDP")
	public void SearchService_filteredSearchProductNodeValidation(String brands) {
		long startTime = Calendar.getInstance().getTimeInMillis();

		MyntraService service = Myntra.getService(ServiceType.PORTAL_SEARCHSERVICE, APINAME.FILTEREDSEARCH,
				init.Configurations, new String[] { brands });
		RequestGenerator req = new RequestGenerator(service);
		System.out.println(service.URL);
		log.info(service.URL);
		System.out.println("FIleterd paylaod------>>>>>\n" + service.Payload);
		System.out.println(req.returnresponseasstring());
		String resp = req.respvalidate.returnresponseasstring();
		String productCountStr = req.respvalidate.GetNodeValue("response1.totalProductsCount", resp);
		int productCount = Integer.parseInt(productCountStr);
		System.out.println("productcount:" + productCount);
		AssertJUnit.assertEquals("SearchService_filteredSearch is not working", 200, req.response.getStatus());
		AssertJUnit.assertTrue("Product Count should be > 0", productCount > 1);

		AssertJUnit.assertTrue("Get(1)--> doesn't contains sizes-facet",
				req.respvalidate.NodeValue(getResponse1Nodes().get(1), true).toLowerCase().contains("sizes_facet"));

		if (req.respvalidate.DoesNodeExists(response1BasePath)) {
			if (req.respvalidate.DoesNodesExists(getProductNodes())) {

				System.out.println(
						"get(0)----Product node---->" + req.respvalidate.NodeValue(getProductNodes().get(0), true));

			}
		}

		else {
			System.out.println("\ndata tag nodes are empty in FilteredSearchService product node API response\n");
			log.info("\ndata tag nodes are empty in FilteredSearchService product node API response\n");
		}

		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;
		System.out.println("\nTime taken to execute - TestCase - SearchService_getQueryWithSuccessMessage : "
				+ timeTaken + " seconds\n");
		log.info("\nTime taken to execute - TestCase - SearchService_getQueryWithSuccessMessage : " + timeTaken
				+ " seconds\n");
	}

	// ---------------------------------------------------------------------------------------------------------------------------------------------------------------------

	// -----------------------------------------------------Testcases for
	// autoSuggest---------------------------------------------------------------------------------------
	/*
	 * Test-cases 4.1: GET SERVICE autoSuggest - getting a success response -
	 * default method
	 * 
	 * @author sneha.deep
	 */
	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression", "SearchSanity",
			"SearchRegression" }, dataProvider = "SearchService_autoSuggestDP")
	public void SearchService_autoSuggest_default(String query) {
		long startTime = Calendar.getInstance().getTimeInMillis();

		MyntraService service = Myntra.getService(ServiceType.PORTAL_SEARCHSERVICE, APINAME.AUTOSUGGESTAPIGET,
				init.Configurations);
		service.URL = apiUtil.prepareparameterizedURL(service.URL, new String[] { query });
		RequestGenerator req = new RequestGenerator(service);
		// System.out.println(service.URL);
		log.info(service.URL);
		// System.out.println(req.returnresponseasstring());

		AssertJUnit.assertEquals("SearchService_autoSuggest is not working", 200, req.response.getStatus());

		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;
		System.out.println("\nTime taken to execute - TestCase - SearchService_getQueryWithSuccessMessage : "
				+ timeTaken + " seconds\n");
		log.info("\nTime taken to execute - TestCase - SearchService_getQueryWithSuccessMessage : " + timeTaken
				+ " seconds\n");
	}

	// ------------------------------------------------------------------------------------------------------------------------------------------------------------------------

	/*
	 * Test-cases 4.2: GET SERVICE autoSuggest - Validating the status node in
	 * json response of autoSuggest API
	 * 
	 * @author sneha.deep
	 */
	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression",
			"SearchRegression" }, dataProvider = "SearchService_autoSuggestDP")
	public void SearchService_autoSuggest_validatingStatusNode(String query) {
		long startTime = Calendar.getInstance().getTimeInMillis();

		MyntraService service = Myntra.getService(ServiceType.PORTAL_SEARCHSERVICE, APINAME.AUTOSUGGESTAPIGET,
				init.Configurations);
		service.URL = apiUtil.prepareparameterizedURL(service.URL, new String[] { query });
		RequestGenerator req = new RequestGenerator(service);
		// System.out.println(service.URL);
		log.info(service.URL);
		// System.out.println(req.returnresponseasstring());

		AssertJUnit.assertTrue("Inval autoSuggestApiGet API status nodes.",
				req.respvalidate.DoesNodesExists(searchStatusNodes()));
		// System.out.println("searchstatusnode::::::::::::::::::"+searchStatusNodes());

		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;
		System.out.println("\nTime taken to execute - TestCase - SearchService_getQueryWithSuccessMessage : "
				+ timeTaken + " seconds\n");
		log.info("\nTime taken to execute - TestCase - SearchService_getQueryWithSuccessMessage : " + timeTaken
				+ " seconds\n");
	}

	// ------------------------------------------------------------------------------------------------------------------------------------------------------------------------

	/*
	 * Test-cases 4.3: GET SERVICE autoSuggest - Validating the existence of
	 * data node in json response of autoSuggest API
	 * 
	 * @author sneha.deep
	 */
	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression",
			"SearchRegression" }, dataProvider = "SearchService_autoSuggestDP")
	public void SearchService_autoSuggest_validatingDataNode(String query) {
		long startTime = Calendar.getInstance().getTimeInMillis();

		MyntraService service = Myntra.getService(ServiceType.PORTAL_SEARCHSERVICE, APINAME.AUTOSUGGESTAPIGET,
				init.Configurations);
		service.URL = apiUtil.prepareparameterizedURL(service.URL, new String[] { query });
		RequestGenerator req = new RequestGenerator(service);
		// System.out.println(service.URL);
		log.info(service.URL);
		// System.out.println(req.returnresponseasstring());

		AssertJUnit.assertTrue("Inval autoSuggestApiGet API data nodes.",
				req.respvalidate.DoesNodesExists(getAutoSuggestDataNodes()));
		// System.out.println("datastatusnode::::::::::::::::::"+getAutoSuggestDataNodes());

		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;
		System.out.println("\nTime taken to execute - TestCase - SearchService_getQueryWithSuccessMessage : "
				+ timeTaken + " seconds\n");
		log.info("\nTime taken to execute - TestCase - SearchService_getQueryWithSuccessMessage : " + timeTaken
				+ " seconds\n");

	}

	// ------------------------------------------------------------------------------------------------------------------------------------------------------------------------

	/*
	 * Test-cases 4.4: GET SERVICE autoSuggest - Validating the status sub nodes
	 * values in json response of autoSuggest API
	 * 
	 * @author sneha.deep
	 */
	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression",
			"SearchRegression" }, dataProvider = "SearchService_autoSuggest_validatingStatusSubNodesDP")
	public void SearchService_autoSuggest_validatingStatusSubNodes(String query, String statusCodeVerify,
			String statusMessageVerify, String statusTypeVerify) {
		long startTime = Calendar.getInstance().getTimeInMillis();

		MyntraService service = Myntra.getService(ServiceType.PORTAL_SEARCHSERVICE, APINAME.AUTOSUGGESTAPIGET,
				init.Configurations);
		service.URL = apiUtil.prepareparameterizedURL(service.URL,
				new String[] { query, statusCodeVerify, statusMessageVerify, statusTypeVerify });
		RequestGenerator req = new RequestGenerator(service);
		// System.out.println(service.URL);
		log.info(service.URL);
		// System.out.println(req.returnresponseasstring());

		AssertJUnit.assertEquals("Status code does't match",
				req.respvalidate.NodeValue(searchStatusNodes().get(0), true), statusCodeVerify);
		AssertJUnit.assertEquals("Status message dosn't match",
				req.respvalidate.NodeValue(searchStatusNodes().get(1), true), statusMessageVerify);
		AssertJUnit.assertEquals("Status type dosn't match",
				req.respvalidate.NodeValue(searchStatusNodes().get(2), true), statusTypeVerify);
		AssertJUnit.assertEquals("Total count dosn't match",
				req.respvalidate.NodeValue(searchStatusNodes().get(3), true),
				req.respvalidate.GetArraySize("data") + "");
		log.info(req.respvalidate.returnresponseasstring());

		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;
		System.out.println("\nTime taken to execute - TestCase - SearchService_getQueryWithSuccessMessage : "
				+ timeTaken + " seconds\n");
		log.info("\nTime taken to execute - TestCase - SearchService_getQueryWithSuccessMessage : " + timeTaken
				+ " seconds\n");
	}

	// ------------------------------------------------------------------------------------------------------------------------------------------------------------------------

	/*
	 * Test-cases 4.5: GET SERVICE autoSuggest - getting a success response -
	 * for negative scenario
	 * 
	 * @author sneha.deep
	 */
	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression", "SearchSanity",
			"SearchRegression" }, dataProvider = "SearchService_autoSuggest_negativeCases1DP")
	public void SearchService_autoSuggest_negativeCases1(String query) {
		long startTime = Calendar.getInstance().getTimeInMillis();

		MyntraService service = Myntra.getService(ServiceType.PORTAL_SEARCHSERVICE, APINAME.AUTOSUGGESTAPIGET,
				init.Configurations);
		service.URL = apiUtil.prepareparameterizedURL(service.URL, new String[] { query });
		RequestGenerator req = new RequestGenerator(service);
		// System.out.println(service.URL);
		log.info(service.URL);
		// System.out.println(req.returnresponseasstring());
		log.info(req.returnresponseasstring());

		AssertJUnit.assertEquals("SearchService_autoSuggest_negativeCases is not working", 200,
				req.response.getStatus());

		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;
		System.out.println("\nTime taken to execute - TestCase - SearchService_getQueryWithSuccessMessage : "
				+ timeTaken + " seconds\n");
		log.info("\nTime taken to execute - TestCase - SearchService_getQueryWithSuccessMessage : " + timeTaken
				+ " seconds\n");
	}

	// ------------------------------------------------------------------------------------------------------------------------------------------------------------------------

	/*
	 * Test-cases 4.6: GET SERVICE autoSuggest - Validating the status sub nodes
	 * in json response of autoSuggest API - for negative scenario
	 * 
	 * @author sneha.deep
	 */
	@Test(groups = { "MiniRegression", "Regression", "ExhasutiveRegression", "RFPFOX7", "RFPQA",
			"RFPPROD" }, dataProvider = "SearchService_autoSuggest_negativeCases1_validatingStatusSubNodesDP")
	public void SearchService_autoSuggest_negativeCases1_validatingStatusSubNodes(String query, String statusCodeVerify,
			String statusMessageVerify, String statusTypeVerify) {
		long startTime = Calendar.getInstance().getTimeInMillis();

		MyntraService service = Myntra.getService(ServiceType.PORTAL_SEARCHSERVICE, APINAME.AUTOSUGGESTAPIGET,
				init.Configurations);
		service.URL = apiUtil.prepareparameterizedURL(service.URL,
				new String[] { query, statusCodeVerify, statusMessageVerify, statusTypeVerify });
		RequestGenerator req = new RequestGenerator(service);
		// System.out.println(service.URL);
		log.info(service.URL);
		// System.out.println(req.returnresponseasstring());

		AssertJUnit.assertEquals("Status code does't match",
				req.respvalidate.NodeValue(searchStatusNodes().get(0), true), statusCodeVerify);
		AssertJUnit.assertEquals("Status message dosn't match",
				req.respvalidate.NodeValue(searchStatusNodes().get(1), true), statusMessageVerify);
		AssertJUnit.assertEquals("Status type dosn't match",
				req.respvalidate.NodeValue(searchStatusNodes().get(2), true), statusTypeVerify);
		AssertJUnit.assertEquals("Total count dosn't match",
				req.respvalidate.NodeValue(searchStatusNodes().get(3), true),
				req.respvalidate.GetArraySize("data") + "");
		log.info(req.respvalidate.returnresponseasstring());

		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;
		System.out.println("\nTime taken to execute - TestCase - SearchService_getQueryWithSuccessMessage : "
				+ timeTaken + " seconds\n");
		log.info("\nTime taken to execute - TestCase - SearchService_getQueryWithSuccessMessage : " + timeTaken
				+ " seconds\n");
	}

	// ------------------------------------------------------------------------------------------------------------------------------------------------------------------------

	/*
	 * Test-cases 4.7: GET SERVICE autoSuggest - getting a success response -
	 * for negative scenario2 - blank query
	 * 
	 * @author sneha.deep
	 */
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhasutiveRegression", "RFPFOX7", "RFPQA",
			"RFPPROD" }, dataProvider = "SearchService_autoSuggest_negativeCases2_blankQueryDP")
	public void SearchService_autoSuggest_negativeCases2_blankQuery(String query) {
		long startTime = Calendar.getInstance().getTimeInMillis();

		MyntraService service = Myntra.getService(ServiceType.PORTAL_SEARCHSERVICE, APINAME.AUTOSUGGESTAPIGET,
				init.Configurations);
		service.URL = apiUtil.prepareparameterizedURL(service.URL, new String[] { query });
		RequestGenerator req = new RequestGenerator(service);
		// System.out.println(service.URL);
		log.info(service.URL);
		// System.out.println(req.returnresponseasstring());
		log.info(req.returnresponseasstring());

		AssertJUnit.assertEquals("autoSuggestApiGet API is not working", 500, req.response.getStatus());
		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;
		System.out.println("\nTime taken to execute - TestCase - SearchService_getQueryWithSuccessMessage : "
				+ timeTaken + " seconds\n");
		log.info("\nTime taken to execute - TestCase - SearchService_getQueryWithSuccessMessage : " + timeTaken
				+ " seconds\n");
	}

	// ------------------------------------------------------------------------------------------------------------------------------------------------------------------------

	/*
	 * Test-cases 4.8: GET SERVICE autoSuggest - schema validation.
	 * 
	 * @author sneha.deep
	 */
	@Test(groups = { "SchemaValidation", "Regression",
			"ExhasutiveRegression" }, dataProvider = "SearchService_autoSuggestDP")
	public void SearchService_autoSuggest_SchemaValidation(String query) throws JsonProcessingException, IOException {
		long startTime = Calendar.getInstance().getTimeInMillis();

		MyntraService service = Myntra.getService(ServiceType.PORTAL_SEARCHSERVICE, APINAME.AUTOSUGGESTAPIGET,
				init.Configurations);
		service.URL = apiUtil.prepareparameterizedURL(service.URL, new String[] { query });
		RequestGenerator req = new RequestGenerator(service);
		String response = req.respvalidate.returnresponseasstring();
		// System.out.println(service.URL);
		log.info(service.URL);
		log.info("\nPrinting SearchService autoSuggestApiGet API response :\n\n" + response + "\n");

		AssertJUnit.assertEquals("SearchService_autoSuggest is not working", 200, req.response.getStatus());

		try {
			String jsonschema = new Toolbox()
					.readFileAsString("Data/SchemaSet/JSON/SearchService/autosuggest-schema.txt");
			List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(response, jsonschema);
			AssertJUnit.assertTrue(missingNodeList + " nodes are missing in autosuggest API response",
					CollectionUtils.isEmpty(missingNodeList));
		} catch (Exception e) {
			e.printStackTrace();
		}

		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;
		System.out.println("\nTime taken to execute - TestCase - SearchService_getQueryWithSuccessMessage : "
				+ timeTaken + " seconds\n");
		log.info("\nTime taken to execute - TestCase - SearchService_getQueryWithSuccessMessage : " + timeTaken
				+ " seconds\n");

	}

	// ------------------------------------------------------------------------------------------------------------------------------------------------------------------------

	/*
	 * Test-cases 4.9: GET SERVICE autoSuggest - getting a success response -
	 * for negative scenario - testing bad request.
	 * 
	 * @author sneha.deep
	 */
	/*
	 * @Test(groups = { "Sanity", "Regression",
	 * "MiniRegression","ExhaustiveRegression", "SearchSanity",
	 * "SearchRegression" }, dataProvider =
	 * "SearchService_autoSuggest_negativeCases1DP") public void
	 * SearchService_autoSuggest_negativeCases2_dataNodeCountValidation(String
	 * query) { long startTime = Calendar.getInstance().getTimeInMillis();
	 * 
	 * MyntraService service = new
	 * MyntraService(ServiceType.PORTAL_SEARCHSERVICE,
	 * APINAME.AUTOSUGGESTAPIGET, init.Configurations); service.URL =
	 * apiUtil.prepareparameterizedURL(service.URL, new String[] { query });
	 * RequestGenerator req = new RequestGenerator(service);
	 * //System.out.println(service.URL); log.info(service.URL); String response
	 * = req.returnresponseasstring(); //System.out.println(response);
	 * log.info(response);
	 * 
	 * AssertJUnit.assertTrue("No Data is populated. totalCount should be 0",
	 * req.respvalidate.NodeValue(searchStatusNodes().get(2),true) == "0");
	 * 
	 * long endTime = Calendar.getInstance().getTimeInMillis(); double timeTaken
	 * = (endTime - startTime)/1000.0; System.out.println(
	 * "\nTime taken to execute - TestCase - SearchService_getQueryWithSuccessMessage : "
	 * +timeTaken+" seconds\n"); log.info(
	 * "\nTime taken to execute - TestCase - SearchService_getQueryWithSuccessMessage : "
	 * +timeTaken+" seconds\n"); }
	 */

	// ------------------------------------------------------------------------------------------------------------------------------------------------------------------------

	// -----------------------------------------------------Testcases for
	// autoSuggest
	// reindexing---------------------------------------------------------------------------------------

	/*
	 * Test-cases 5.1: GET SERVICE autoSuggestReIndexing - getting a success
	 * response - default message
	 * 
	 * @author sneha.deep
	 */
	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression", "SearchSanity",
			"SearchRegression" })
	public void SearchService_autoSuggestReindexing_default() {
		long startTime = Calendar.getInstance().getTimeInMillis();

		MyntraService service = Myntra.getService(ServiceType.PORTAL_SEARCHSERVICE, APINAME.AUTOSUGGESTINDEXING,
				init.Configurations);
		RequestGenerator req = new RequestGenerator(service);
		// System.out.println(service.URL);
		log.info(service.URL);
		// System.out.println(req.returnresponseasstring());

		AssertJUnit.assertEquals("SearchService_autoSuggestReindexing_default is not working", 200,
				req.response.getStatus());
		AssertJUnit.assertTrue("Query dosn't match", req.respvalidate.NodeValue("status.statusCode", true).equals("3"));
		AssertJUnit.assertTrue("returnDocs dosn't match", req.respvalidate.NodeValue("status.statusMessage", true)
				.toLowerCase().contains("Suggest indexing is successful".toLowerCase()));

		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;
		System.out.println("\nTime taken to execute - TestCase - SearchService_getQueryWithSuccessMessage : "
				+ timeTaken + " seconds\n");
		log.info("\nTime taken to execute - TestCase - SearchService_getQueryWithSuccessMessage : " + timeTaken
				+ " seconds\n");
	}

	// ------------------------------------------------------------------------------------------------------------------------------------------------------------------------

	/*
	 * @Test(groups = { "Smoke", "Sanity", "Regression", "MiniRegression",
	 * "ExhaustiveRegression", "ProdSanity" }, dataProvider = "dp_landingpage")
	 * public void SearchService_landingPageForBrands(String BrandName) {
	 * MyntraService service = Myntra.getService(
	 * ServiceType.PORTAL_SEARCHSERVICE, APINAME.LANDINGPAGE,
	 * init.Configurations); APIUtilities utilities = new APIUtilities();
	 * //System.out.println(service.URL); service.URL =
	 * utilities.prepareparameterizedURL(service.URL, BrandName);
	 * RequestGenerator req = new RequestGenerator(service);
	 * System.out.println(req.returnresponseasstring());
	 * AssertJUnit.assertEquals("Landing page for brands API is not working",
	 * 200, req.response.getStatus()); }
	 * 
	 * @Test(groups = { "Sanity", "Regression", "MiniRegression",
	 * "ExhaustiveRegression", "ProdSanity" }, dataProvider = "dp_landingpage")
	 * public void SearchService_landingPageItemCounts(String urlparameter) {
	 * MyntraService service = Myntra.getService(
	 * ServiceType.PORTAL_SEARCHSERVICE, APINAME.LANDINGPAGE,
	 * init.Configurations); APIUtilities utilities = new APIUtilities();
	 * service.URL = utilities.prepareparameterizedURL(service.URL,
	 * urlparameter); RequestGenerator req = new RequestGenerator(service);
	 * AssertJUnit.assertEquals("Landing page item counts API is not working",
	 * 200, req.response.getStatus()); }
	 */

	/*
	 * @Test(groups = { "Sanity", "Regression", "MiniRegression",
	 * "ExhaustiveRegression", "ProdSanity" }, dataProvider = "dp_autosuggest")
	 * public void SearchService_autoSuggest(String[] urlparameter, String[]
	 * payloadparams) { MyntraService service = Myntra.getService(
	 * ServiceType.PORTAL_SEARCHSERVICE, APINAME.AUTOSUGGEST,
	 * init.Configurations, payloadparams, urlparameter, PayloadType.JSON,
	 * PayloadType.JSON); RequestGenerator req = new RequestGenerator(service);
	 * //System.out.println(service.URL); //System.out.println(service.Payload);
	 * log.info(req.respvalidate.ResponseFromService);
	 * System.out.println(req.respvalidate.ResponseFromService);
	 * AssertJUnit.assertEquals("AutoSuggest API is not working", 200,
	 * req.response.getStatus()); }
	 */

	/*
	 * @Test(groups = { "Sanity", "Regression", "MiniRegression",
	 * "ExhaustiveRegression" }, dataProvider = "dp_parameterizedpage") public
	 * void SearchService_parameterizedPageById(String urlparameter) {
	 * MyntraService service = Myntra.getService(
	 * ServiceType.PORTAL_SEARCHSERVICE, APINAME.PARAMETERIZEDPAGE,
	 * init.Configurations); APIUtilities utilities = new APIUtilities();
	 * service.URL = utilities.prepareparameterizedURL(service.URL,
	 * urlparameter); System.out.println(service.URL); RequestGenerator req =
	 * new RequestGenerator(service);
	 * System.out.println(req.returnresponseasstring());
	 * AssertJUnit.assertEquals("Parameterized page by id API is not working",
	 * 200, req.response.getStatus()); }
	 */

	/*
	 * @Test(groups = { "Sanity", "Regression", "MiniRegression",
	 * "ExhaustiveRegression", "ProdSanity" }, dataProvider =
	 * "curatedSearchListByParamPageIdDataProvider") public void
	 * SearchService_curatedSearchListByParamPageId(String urlparameter) {
	 * MyntraService service = Myntra.getService( C
	 * ServiceType.PORTAL_SEARCHSERVICE, APINAME.CURATEDSEARCHLISTBYPARAMPAGEID,
	 * init.Configurations); APIUtilities utilities = new APIUtilities();
	 * service.URL = utilities.prepareparameterizedURL(service.URL,
	 * urlparameter); System.out.println(service.URL); RequestGenerator req =
	 * new RequestGenerator(service);
	 * System.out.println(req.respvalidate.returnresponseasstring());
	 * AssertJUnit.assertEquals(
	 * "curatedSearchListByParamPageId API is not working", 200,
	 * req.response.getStatus()); }
	 */

	/*
	 * @Test(groups = { "Sanity", "Regression", "MiniRegression",
	 * "ExhaustiveRegression", "ProdSanity" }, dataProvider =
	 * "curatedSearchListByParamPageKeyDataProvider") public void
	 * SearchService_curatedSearchListByParamPageKey( String urlparameter) {
	 * MyntraService service = Myntra.getService(
	 * ServiceType.PORTAL_SEARCHSERVICE,
	 * APINAME.CURATEDSEARCHLISTBYPARAMPAGEKEY, init.Configurations);
	 * APIUtilities utilities = new APIUtilities(); service.URL =
	 * utilities.prepareparameterizedURL(service.URL, urlparameter);
	 * System.out.println(service.URL); RequestGenerator req = new
	 * RequestGenerator(service);
	 * System.out.println(req.respvalidate.returnresponseasstring());
	 * AssertJUnit.assertEquals(
	 * "curatedSearchListByParamPageKey API is not working", 200,
	 * req.response.getStatus()); }
	 */

	/*
	 * @Test(groups = { "Sanity", "Regression", "MiniRegression",
	 * "ExhaustiveRegression" }, dataProvider =
	 * "saveOrUpdateParamPageDataProvider") public void
	 * SearchService_saveOrUpdateParamPage(String param1, String param2) {
	 * MyntraService service = Myntra.getService(
	 * ServiceType.PORTAL_SEARCHSERVICE, APINAME.SAVEORUPDATEPARAMPAGE,
	 * init.Configurations, new String[] { param1, param2 });
	 * System.out.println(service.URL); System.out.println(service.Payload);
	 * RequestGenerator req = new RequestGenerator(service);
	 * System.out.println(req.respvalidate.returnresponseasstring());
	 * AssertJUnit.assertEquals("saveOrUpdateParamPage is not working", 200,
	 * req.response.getStatus()); }
	 */

	/*
	 * @Test(groups = { "Sanity", "Regression", "MiniRegression",
	 * "ExhaustiveRegression" }, dataProvider = "addnewLandingPageDataProvider")
	 * public void SearchService_addnewLandingPage(String param1, String param2,
	 * String param3) { MyntraService service = Myntra.getService(
	 * ServiceType.PORTAL_SEARCHSERVICE, APINAME.ADDNEWLANDINGPAGE,
	 * init.Configurations, new String[] { param1, param2, param3 });
	 * System.out.println(service.URL); System.out.println(service.Payload);
	 * RequestGenerator req = new RequestGenerator(service);
	 * System.out.println(req.respvalidate.returnresponseasstring());
	 * AssertJUnit.assertEquals("addnewLandingPage is not working", 200,
	 * req.response.getStatus()); }
	 */
	/*
	 * @Test(groups = { "Sanity", "Regression", "MiniRegression",
	 * "ExhaustiveRegression" }, dataProvider =
	 * "updateExistingLandingPageDataProvider") public void
	 * SearchService_updateExistingLandingPage(String param1, String param2,
	 * String param3, String param4, String param5) { MyntraService service =
	 * Myntra.getService( ServiceType.PORTAL_SEARCHSERVICE,
	 * APINAME.UPDATEEXISTINGLANDINGPAGE, init.Configurations, new String[] {
	 * param1, param2, param3, param4, param5 });
	 * System.out.println(service.URL); System.out.println(service.Payload);
	 * RequestGenerator req = new RequestGenerator(service);
	 * System.out.println(req.respvalidate.returnresponseasstring());
	 * AssertJUnit.assertEquals("updateExistingLandingPage is not working", 200,
	 * req.response.getStatus()); }
	 */

	// /**
	// * Get a Landing Page.
	// * @author jhansi.bai
	// */
	// @Test(groups = { "Smoke", "Sanity", "Prodsanity", "Regression",
	// "MiniRegression",
	// "ExhaustiveRegression", "RFPFOX7", "RFPQA", "RFPPROD"}, dataProvider =
	// "landingpageDP")
	// public void SearchService_landingPageForBrands(String BrandName, String
	// statusCode,
	// String successMessage, String successStatus)
	// {
	// RequestGenerator req = getQueryRequest(APINAME.LANDINGPAGE, BrandName);
	// // System.out.println(req.returnresponseasstring());
	// System.out.println(req.returnresponseasstring());
	// AssertJUnit.assertEquals("Landing page for brands API is not working",
	// 200, req.response.getStatus());
	// }
	//
	// /**
	// * Get a Landing Page.
	// * @author jhansi.bai
	// */
	// @Test(groups = { "Regression", "MiniRegression",
	// "ExhaustiveRegression", "RFPFOX7", "RFPQA", "RFPPROD"}, dataProvider =
	// "landingpageDP")
	// public void SearchService_landingPageForBrands_vStatusAdDataNodes(String
	// BrandName, String statusCode,
	// String successMessage, String successStatus)
	// {
	// RequestGenerator req = getQueryRequest(APINAME.LANDINGPAGE, BrandName);
	// System.out.println(req.returnresponseasstring());
	// AssertJUnit.assertTrue("Inval Landing page for brands API status nodes.",
	// req.respvalidate.DoesNodesExists(searchStatusNodes()));
	// AssertJUnit.assertTrue("Inval Landing page for brands API data nodes.",
	// req.respvalidate.DoesNodesExists(searchDataNodes()));
	// }
	//
	// /**
	// * Get a Landing Page.
	// * @author jhansi.bai
	// */
	// @Test(groups = { "Regression", "MiniRegression",
	// "ExhaustiveRegression", "RFPFOX7", "RFPQA", "RFPPROD"}, dataProvider =
	// "landingpageDP")
	// public void
	// SearchService_landingPageForBrands_vStatusAdDataNodesVals(String
	// BrandName, String statusCode,
	// String successMessage, String successStatus)
	// {
	// RequestGenerator req = getQueryRequest(APINAME.LANDINGPAGE, BrandName);
	// System.out.println(req.returnresponseasstring());
	// AssertJUnit.assertEquals("Status code does't match",
	// req.respvalidate.NodeValue(searchStatusNodes().get(0), true),
	// statusCode);
	// AssertJUnit.assertEquals("Status message dosn't match",
	// req.respvalidate.NodeValue(searchStatusNodes().get(1), true),
	// successMessage);
	// AssertJUnit.assertEquals("Status type dosn't match",
	// req.respvalidate.NodeValue(searchStatusNodes().get(2), true),
	// successStatus);
	// AssertJUnit.assertEquals("pageUrl dosn't match",
	// req.respvalidate.NodeValue(searchDataNodes().get(1), true),
	// "\""+BrandName+"\"");
	// log.info(req.respvalidate.returnresponseasstring());
	// }
	//
	// /**
	// * Get a Landing Page.
	// * @author jhansi.bai
	// */
	// @Test(groups = {"Sanity", "MiniRegression","Regression",
	// "ExhasutiveRegression", "RFPFOX7", "RFPQA", "RFPPROD"},
	// dataProvider = "landingpageDP_negative")
	// public void SearchService_landingPageForBrands_negative(String BrandName,
	// String errStatusCode, String errorMessage, String errorStatus, String
	// itemCount) {
	// RequestGenerator req = getQueryRequest(APINAME.LANDINGPAGE, BrandName);
	// System.out.println(req.respvalidate.returnresponseasstring());
	// AssertJUnit.assertEquals("Landing page for brands API is not working",
	// 200, req.response.getStatus());
	// }
	//
	// /**
	// * Get a Landing Page.
	// * @author jhansi.bai
	// */
	// @Test(groups = {"MiniRegression","Regression", "ExhasutiveRegression",
	// "RFPFOX7", "RFPQA", "RFPPROD"},
	// dataProvider = "landingpageDP_negative")
	// public void
	// SearchService_landingPageForBrands_vNegativeStatusNodes(String BrandName,
	// String errStatusCode, String errorMessage, String errorStatus, String
	// itemCount) {
	// RequestGenerator req = getQueryRequest(APINAME.LANDINGPAGE, BrandName);
	// System.out.println(req.respvalidate.returnresponseasstring());
	// AssertJUnit.assertTrue("Inval Landing page for brands API status nodes.",
	// req.respvalidate.DoesNodesExists(searchStatusNodes()));
	// }
	//
	// /**
	// * Get a Landing Page.
	// * @author jhansi.bai
	// */
	// @Test(groups = {"MiniRegression","Regression", "ExhasutiveRegression",
	// "RFPFOX7", "RFPQA", "RFPPROD"},
	// dataProvider = "landingpageDP_negative")
	// public void SearchService_landingPageForBrands_vNegativeStatusVals(String
	// BrandName,
	// String errStatusCode, String errorMessage, String errorStatus, String
	// itemCount) {
	// RequestGenerator req = getQueryRequest(APINAME.LANDINGPAGE, BrandName);
	// System.out.println(req.respvalidate.returnresponseasstring());
	// AssertJUnit.assertEquals("Status code does't match",
	// req.respvalidate.NodeValue(searchStatusNodes().get(0), true),
	// errStatusCode);
	// AssertJUnit.assertEquals("Status message dosn't match",
	// req.respvalidate.NodeValue(searchStatusNodes().get(1), true),
	// errorMessage);
	// AssertJUnit.assertEquals("Status type dosn't match",
	// req.respvalidate.NodeValue(searchStatusNodes().get(2), true),
	// errorStatus);
	// AssertJUnit.assertEquals("Total count dosn't match",
	// req.respvalidate.NodeValue(searchStatusNodes().get(3), true),
	// itemCount);
	// log.info(req.respvalidate.returnresponseasstring());
	// }
	//
	// /**
	// * Get a Landing Page.
	// * @author jhansi.bai
	// */
	// @Test(groups = {"Sanity", "MiniRegression","Regression",
	// "ExhasutiveRegression", "RFPFOX7", "RFPQA", "RFPPROD"},
	// dataProvider = "landingpageDP_negative1")
	// public void SearchService_landingPageForBrands_negative500Status(String
	// BrandName) {
	// RequestGenerator req = getQueryRequest(APINAME.LANDINGPAGE, BrandName);
	// System.out.println(req.respvalidate.returnresponseasstring());
	// AssertJUnit.assertEquals("Landing page for brands API is not working",
	// 500, req.response.getStatus());
	// }
	//
	// /**
	// * Get a Parameterized Page by Page Key.
	// * @author jhansi.bai
	// */
	// @Test(groups = {"Sanity", "Prodsanity", "MiniRegression","Regression",
	// "ExhasutiveRegression",
	// "RFPFOX7", "RFPQA", "RFPPROD"}, dataProvider =
	// "parameterisedPageByPageKeyDP")
	// public void SearchService_parameterisedPageByPageKey(String pageKkey,
	// String statusCode,
	// String successMessage, String successStatus) {
	// RequestGenerator req = getQueryRequest(APINAME.PARAMETERIZEDPAGEKEY,
	// pageKkey);
	// System.out.println(req.respvalidate.returnresponseasstring());
	// AssertJUnit.assertEquals("Landing page for brands API is not working",
	// 200, req.response.getStatus());
	// }
	//
	// /**
	// * Get a Parameterized Page by Page Key.
	// * @author jhansi.bai
	// */
	// @Test(groups = {"MiniRegression","Regression", "ExhasutiveRegression",
	// "RFPFOX7", "RFPQA", "RFPPROD"},
	// dataProvider = "parameterisedPageByPageKeyDP")
	// public void
	// SearchService_parameterisedPageByPageKey_vStatusAdDataNodes(String
	// pageKkey, String statusCode,
	// String successMessage, String successStatus) {
	// RequestGenerator req = getQueryRequest(APINAME.PARAMETERIZEDPAGEKEY,
	// pageKkey);
	// System.out.println(req.respvalidate.returnresponseasstring());
	// AssertJUnit.assertTrue("Inval Landing page for brands API status nodes.",
	// req.respvalidate.DoesNodesExists(searchStatusNodes()));
	// AssertJUnit.assertTrue("Inval Landing page for brands API data nodes.",
	// req.respvalidate.DoesNodesExists(pageKeyDataNodes()));
	// }
	//
	// /**
	// * Get a Parameterized Page by Page Key.
	// * @author jhansi.bai
	// */
	// @Test(groups = {"MiniRegression","Regression", "ExhasutiveRegression",
	// "RFPFOX7", "RFPQA", "RFPPROD"},
	// dataProvider = "parameterisedPageByPageKeyDP")
	// public void
	// SearchService_parameterisedPageByPageKey_vStatusNodesVals(String
	// pageKkey, String statusCode,
	// String successMessage, String successStatus) {
	// RequestGenerator req = getQueryRequest(APINAME.PARAMETERIZEDPAGEKEY,
	// pageKkey);
	// System.out.println(req.respvalidate.returnresponseasstring());
	// AssertJUnit.assertEquals("Status code does't match",
	// req.respvalidate.NodeValue(searchStatusNodes().get(0), true),
	// statusCode);
	// AssertJUnit.assertEquals("Status message dosn't match",
	// req.respvalidate.NodeValue(searchStatusNodes().get(1), true),
	// successMessage);
	// AssertJUnit.assertEquals("Status type dosn't match",
	// req.respvalidate.NodeValue(searchStatusNodes().get(2), true),
	// successStatus);
	// AssertJUnit.assertEquals("pageUrl dosn't match",
	// req.respvalidate.NodeValue(pageKeyDataNodes().get(1), true),
	// "\""+pageKkey+"\"");
	// log.info(req.respvalidate.returnresponseasstring());
	// }
	//
	// /**
	// * Get a Parameterized Page by Page Key.
	// * @author jhansi.bai
	// */
	// @Test(groups = {"Sanity", "MiniRegression","Regression",
	// "ExhasutiveRegression", "RFPFOX7", "RFPQA", "RFPPROD"},
	// dataProvider = "parameterisedPageByPageKeyDP_negative")
	// public void SearchService_parameterisedPageByPageKey_negative(String
	// pageKkey,
	// String errStatusCode, String errorMessage, String errorStatus, String
	// itemCount) {
	// RequestGenerator req = getQueryRequest(APINAME.PARAMETERIZEDPAGEKEY,
	// pageKkey);
	// System.out.println(req.respvalidate.returnresponseasstring());
	// AssertJUnit.assertEquals("Landing page for brands API is not working",
	// 200, req.response.getStatus());
	// }
	//
	// /**
	// * Get a Parameterized Page by Page Key.
	// * @author jhansi.bai
	// */
	// @Test(groups = {"MiniRegression","Regression", "ExhasutiveRegression",
	// "RFPFOX7", "RFPQA", "RFPPROD"},
	// dataProvider = "parameterisedPageByPageKeyDP_negative")
	// public void
	// SearchService_parameterisedPageByPageKey_vNegativeStatusNodes(String
	// pageKkey,
	// String errStatusCode, String errorMessage, String errorStatus, String
	// itemCount) {
	// RequestGenerator req = getQueryRequest(APINAME.PARAMETERIZEDPAGEKEY,
	// pageKkey);
	// System.out.println(req.respvalidate.returnresponseasstring());
	// AssertJUnit.assertTrue("Inval Landing page for brands API status nodes.",
	// req.respvalidate.DoesNodesExists(searchStatusNodes()));
	// }
	//
	// /**
	// * Get a Parameterized Page by Page Key.
	// * @author jhansi.bai
	// */
	// @Test(groups = {"MiniRegression","Regression", "ExhasutiveRegression",
	// "RFPFOX7", "RFPQA", "RFPPROD"},
	// dataProvider = "parameterisedPageByPageKeyDP_negative")
	// public void
	// SearchService_parameterisedPageByPageKey_vNegativeStatusVals(String
	// pageKkey,
	// String errStatusCode, String errorMessage, String errorStatus, String
	// itemCount) {
	// RequestGenerator req = getQueryRequest(APINAME.PARAMETERIZEDPAGEKEY,
	// pageKkey);
	// System.out.println(req.respvalidate.returnresponseasstring());
	// AssertJUnit.assertEquals("Status code does't match",
	// req.respvalidate.NodeValue(searchStatusNodes().get(0), true),
	// errStatusCode);
	// AssertJUnit.assertEquals("Status message dosn't match",
	// req.respvalidate.NodeValue(searchStatusNodes().get(1), true),
	// errorMessage);
	// AssertJUnit.assertEquals("Status type dosn't match",
	// req.respvalidate.NodeValue(searchStatusNodes().get(2), true),
	// errorStatus);
	// AssertJUnit.assertEquals("Total count dosn't match",
	// req.respvalidate.NodeValue(searchStatusNodes().get(3), true),
	// itemCount);
	// log.info(req.respvalidate.returnresponseasstring());
	// }
	//
	// /**
	// * Get a Parameterized Page by Page Key.
	// * @author jhansi.bai
	// */
	// @Test(groups = {"Sanity", "MiniRegression","Regression",
	// "ExhasutiveRegression", "RFPFOX7", "RFPQA", "RFPPROD"},
	// dataProvider = "parameterisedPageByPageKeyDP_negative1")
	// public void
	// SearchService_parameterisedPageByPageKey_negative404Status(String
	// pageKkey) {
	// RequestGenerator req = getQueryRequest(APINAME.PARAMETERIZEDPAGEKEY,
	// pageKkey);
	// System.out.println(req.respvalidate.returnresponseasstring());
	// AssertJUnit.assertEquals("Landing page for brands API is not working",
	// 404, req.response.getStatus());
	// }
	//
	// /**
	// * Get a Parameterized Page by Page Id.
	// * @author jhansi.bai
	// */
	// @Test(groups = { "Sanity", "Prodsanity", "Regression", "MiniRegression",
	// "ExhaustiveRegression", "RFPFOX7", "RFPQA", "RFPPROD" }, dataProvider =
	// "parameterizedpageByPageIdDP")
	// public void SearchService_parameterizedPageById(String pageId, String
	// statusCode,
	// String successMessage, String successStatus)
	// {
	// RequestGenerator req = getQueryRequest(APINAME.PARAMETERIZEDPAGE,
	// pageId);
	// System.out.println(req.returnresponseasstring());
	// AssertJUnit.assertEquals("Parameterized page by id API is not working",
	// 200, req.response.getStatus());
	// }
	//
	// /**
	// * Get a Parameterized Page by Page Id.
	// * @author jhansi.bai
	// */
	// @Test(groups = { "Sanity", "Regression", "MiniRegression",
	// "ExhaustiveRegression", "RFPFOX7", "RFPQA", "RFPPROD" }, dataProvider =
	// "parameterizedpageByPageIdDP")
	// public void SearchService_parameterizedPageById_vStatusAdDataNodes(String
	// pageId, String statusCode,
	// String successMessage, String successStatus)
	// {
	// RequestGenerator req = getQueryRequest(APINAME.PARAMETERIZEDPAGE,
	// pageId);
	// System.out.println(req.returnresponseasstring());
	// AssertJUnit.assertTrue("Inval Landing page for brands API status nodes.",
	// req.respvalidate.DoesNodesExists(searchStatusNodes()));
	// AssertJUnit.assertTrue("Inval Landing page for brands API data nodes.",
	// req.respvalidate.DoesNodesExists(pageKeyDataNodes()));
	// }
	//
	// /**
	// * Get a Parameterized Page by Page Id.
	// * @author jhansi.bai
	// */
	// @Test(groups = { "Sanity", "Regression", "MiniRegression",
	// "ExhaustiveRegression", "RFPFOX7", "RFPQA", "RFPPROD" }, dataProvider =
	// "parameterizedpageByPageIdDP")
	// public void
	// SearchService_parameterizedPageById_vStatusAdDataNodesVals(String pageId,
	// String statusCode,
	// String successMessage, String successStatus)
	// {
	// RequestGenerator req = getQueryRequest(APINAME.PARAMETERIZEDPAGE,
	// pageId);
	// System.out.println(req.returnresponseasstring());
	// AssertJUnit.assertEquals("Status code does't match",
	// req.respvalidate.NodeValue(searchStatusNodes().get(0), true),
	// statusCode);
	// AssertJUnit.assertEquals("Status message dosn't match",
	// req.respvalidate.NodeValue(searchStatusNodes().get(1), true),
	// successMessage);
	// AssertJUnit.assertEquals("Status type dosn't match",
	// req.respvalidate.NodeValue(searchStatusNodes().get(2), true),
	// successStatus);
	// AssertJUnit.assertEquals("pageUrl dosn't match",
	// req.respvalidate.NodeValue(pageKeyDataNodes().get(0), true),
	// pageId);
	// log.info(req.respvalidate.returnresponseasstring());
	// }
	//
	// /**
	// * Get a Parameterized Page by Page Id.
	// * @author jhansi.bai
	// */
	// @Test(groups = {"Sanity", "MiniRegression","Regression",
	// "ExhasutiveRegression", "RFPFOX7", "RFPQA", "RFPPROD"},
	// dataProvider = "parameterizedpageByPageIdDP_negative")
	// public void SearchService_parameterizedPageById_negative(String pageId,
	// String errStatusCode, String errorMessage, String errorStatus, String
	// itemCount) {
	// RequestGenerator req = getQueryRequest(APINAME.PARAMETERIZEDPAGE,
	// pageId);
	// System.out.println(req.respvalidate.returnresponseasstring());
	// AssertJUnit.assertEquals("Landing page for brands API is not working",
	// 200, req.response.getStatus());
	// }
	//
	// /**
	// * Get a Parameterized Page by Page Id.
	// * @author jhansi.bai
	// */
	// @Test(groups = {"MiniRegression","Regression", "ExhasutiveRegression",
	// "RFPFOX7", "RFPQA", "RFPPROD"},
	// dataProvider = "parameterizedpageByPageIdDP_negative")
	// public void
	// SearchService_parameterizedPageById_vNegativeStatusNodes(String pageId,
	// String errStatusCode, String errorMessage, String errorStatus, String
	// itemCount) {
	// RequestGenerator req = getQueryRequest(APINAME.PARAMETERIZEDPAGE,
	// pageId);
	// System.out.println(req.respvalidate.returnresponseasstring());
	// AssertJUnit.assertTrue("Inval Landing page for brands API status nodes.",
	// req.respvalidate.DoesNodesExists(searchStatusNodes()));
	// }
	//
	// /**
	// * Get a Parameterized Page by Page Id.
	// * @author jhansi.bai
	// */
	// @Test(groups = {"MiniRegression","Regression", "ExhasutiveRegression",
	// "RFPFOX7", "RFPQA", "RFPPROD"},
	// dataProvider = "parameterizedpageByPageIdDP_negative")
	// public void
	// SearchService_parameterizedPageById_vNegativeStatusVals(String pageId,
	// String errStatusCode, String errorMessage, String errorStatus, String
	// itemCount) {
	// RequestGenerator req = getQueryRequest(APINAME.PARAMETERIZEDPAGE,
	// pageId);
	// System.out.println(req.respvalidate.returnresponseasstring());
	// AssertJUnit.assertEquals("Status code does't match",
	// req.respvalidate.NodeValue(searchStatusNodes().get(0), true),
	// errStatusCode);
	// AssertJUnit.assertEquals("Status message dosn't match",
	// req.respvalidate.NodeValue(searchStatusNodes().get(1), true),
	// errorMessage);
	// AssertJUnit.assertEquals("Status type dosn't match",
	// req.respvalidate.NodeValue(searchStatusNodes().get(2), true),
	// errorStatus);
	// AssertJUnit.assertEquals("Total count dosn't match",
	// req.respvalidate.NodeValue(searchStatusNodes().get(3), true),
	// itemCount);
	// log.info(req.respvalidate.returnresponseasstring());
	// }
	//
	// /**
	// * Get a Parameterized Page by Page Id.
	// * @author jhansi.bai
	// */
	// @Test(groups = {"Sanity", "MiniRegression","Regression",
	// "ExhasutiveRegression", "RFPFOX7", "RFPQA", "RFPPROD"},
	// dataProvider = "parameterizedpageByPageIdDP_negative1")
	// public void SearchService_parameterizedPageById_negative404Status(String
	// pageId) {
	// RequestGenerator req = getQueryRequest(APINAME.PARAMETERIZEDPAGE,
	// pageId);
	// System.out.println(req.respvalidate.returnresponseasstring());
	// AssertJUnit.assertEquals("Landing page for brands API is not working",
	// 404, req.response.getStatus());
	// }
	//
	// /**
	// * Get Curated Search List by Parameterized Page Id.
	// * @author jhansi.bai
	// */
	// @Test(groups = { "Sanity", "Regression", "MiniRegression",
	// "ExhaustiveRegression", "ProdSanity", "RFPFOX7", "RFPQA", "RFPPROD" },
	// dataProvider = "curatedSearchListByParamPageIdDP")
	// public void SearchService_curatedSearchListByParamPageId(String
	// paramPageId, String statusCode,
	// String successMessage, String successStatus)
	// {
	// RequestGenerator req =
	// getQueryRequest(APINAME.CURATEDSEARCHLISTBYPARAMPAGEID, paramPageId);
	// System.out.println(req.respvalidate.returnresponseasstring());
	// AssertJUnit.assertEquals(
	// "curatedSearchListByParamPageId API is not working", 200,
	// req.response.getStatus());
	// }
	//
	// /**
	// * Get Curated Search List by Parameterized Page Id.
	// * @author jhansi.bai
	// */
	// @Test(groups = { "Regression", "MiniRegression",
	// "ExhaustiveRegression", "RFPFOX7", "RFPQA", "RFPPROD" }, dataProvider =
	// "curatedSearchListByParamPageIdDP")
	// public void
	// SearchService_curatedSearchListByParamPageId_vStatusAdDataNodes(String
	// paramPageId, String statusCode,
	// String successMessage, String successStatus)
	// {
	// RequestGenerator req =
	// getQueryRequest(APINAME.CURATEDSEARCHLISTBYPARAMPAGEID, paramPageId);
	// System.out.println(req.returnresponseasstring());
	// AssertJUnit.assertTrue("Inval curatedSearchListByParamPageId API status
	// nodes.",
	// req.respvalidate.DoesNodesExists(searchStatusNodes()));
	// AssertJUnit.assertTrue("Inval curatedSearchListByParamPageId API data
	// nodes.",
	// req.respvalidate.DoesNodesExists(curatedSearchListByParamPageIdDataNodes()));
	// }
	//
	// /**
	// * Get Curated Search List by Parameterized Page Id.
	// * @author jhansi.bai
	// */
	// @Test(groups = { "Regression", "MiniRegression",
	// "ExhaustiveRegression", "RFPFOX7", "RFPQA", "RFPPROD" }, dataProvider =
	// "curatedSearchListByParamPageIdDP")
	// public void
	// SearchService_curatedSearchListByParamPageId_vStatusAdDataNodesVals(String
	// paramPageId, String statusCode,
	// String successMessage, String successStatus)
	// {
	// RequestGenerator req =
	// getQueryRequest(APINAME.CURATEDSEARCHLISTBYPARAMPAGEID, paramPageId);
	// System.out.println(req.returnresponseasstring());
	// AssertJUnit.assertEquals("Status code does't match",
	// req.respvalidate.NodeValue(searchStatusNodes().get(0), true),
	// statusCode);
	// AssertJUnit.assertEquals("Status message dosn't match",
	// req.respvalidate.NodeValue(searchStatusNodes().get(1), true),
	// successMessage);
	// AssertJUnit.assertEquals("Status type dosn't match",
	// req.respvalidate.NodeValue(searchStatusNodes().get(2), true),
	// successStatus);
	// AssertJUnit.assertEquals("pageUrl dosn't match",
	// req.respvalidate.NodeValue(curatedSearchListByParamPageIdDataNodes().get(1),
	// true),
	// paramPageId);
	// log.info(req.respvalidate.returnresponseasstring());
	// }
	//
	// /**
	// * Get Curated Search List by Parameterized Page Id.
	// * @author jhansi.bai
	// */
	// @Test(groups = {"Sanity", "Prodsanity", "MiniRegression","Regression",
	// "ExhasutiveRegression", "RFPFOX7", "RFPQA", "RFPPROD"},
	// dataProvider = "curatedSearchListByParamPageIdDP_negative")
	// public void SearchService_curatedSearchListByParamPageId_negative(String
	// paramPageId,
	// String errStatusCode, String errorMessage, String errorStatus, String
	// itemCount) {
	// RequestGenerator req =
	// getQueryRequest(APINAME.CURATEDSEARCHLISTBYPARAMPAGEID, paramPageId);
	// System.out.println(req.respvalidate.returnresponseasstring());
	// AssertJUnit.assertEquals("curatedSearchListByParamPageId API is not
	// working", 200, req.response.getStatus());
	// }
	//
	// /**
	// * Get Curated Search List by Parameterized Page Id.
	// * @author jhansi.bai
	// */
	// @Test(groups = {"MiniRegression","Regression", "ExhasutiveRegression",
	// "RFPFOX7", "RFPQA", "RFPPROD"},
	// dataProvider = "curatedSearchListByParamPageIdDP_negative")
	// public void
	// SearchService_curatedSearchListByParamPageId_vNegativeStatusNodes(String
	// paramPageId,
	// String errStatusCode, String errorMessage, String errorStatus, String
	// itemCount) {
	// RequestGenerator req =
	// getQueryRequest(APINAME.CURATEDSEARCHLISTBYPARAMPAGEID, paramPageId);
	// System.out.println(req.respvalidate.returnresponseasstring());
	// AssertJUnit.assertTrue("Inval curatedSearchListByParamPageId API status
	// nodes.",
	// req.respvalidate.DoesNodesExists(searchStatusNodes()));
	// }
	//
	// /**
	// * Get Curated Search List by Parameterized Page Id.
	// * @author jhansi.bai
	// */
	// @Test(groups = {"MiniRegression","Regression", "ExhasutiveRegression",
	// "RFPFOX7", "RFPQA", "RFPPROD"},
	// dataProvider = "curatedSearchListByParamPageIdDP_negative")
	// public void
	// SearchService_curatedSearchListByParamPageId_vNegativeStatusVals(String
	// paramPageId,
	// String errStatusCode, String errorMessage, String errorStatus, String
	// itemCount) {
	// RequestGenerator req =
	// getQueryRequest(APINAME.CURATEDSEARCHLISTBYPARAMPAGEID, paramPageId);
	// System.out.println(req.respvalidate.returnresponseasstring());
	// AssertJUnit.assertEquals("Status code does't match",
	// req.respvalidate.NodeValue(searchStatusNodes().get(0), true),
	// errStatusCode);
	// AssertJUnit.assertEquals("Status message dosn't match",
	// req.respvalidate.NodeValue(searchStatusNodes().get(1), true),
	// errorMessage);
	// AssertJUnit.assertEquals("Status type dosn't match",
	// req.respvalidate.NodeValue(searchStatusNodes().get(2), true),
	// errorStatus);
	// AssertJUnit.assertEquals("Total count dosn't match",
	// req.respvalidate.NodeValue(searchStatusNodes().get(3), true),
	// itemCount);
	// log.info(req.respvalidate.returnresponseasstring());
	// }
	//
	// /**
	// * Get Curated Search List by Parameterized Page Id.
	// * @author jhansi.bai
	// */
	// @Test(groups = {"Sanity", "MiniRegression","Regression",
	// "ExhasutiveRegression", "RFPFOX7", "RFPQA", "RFPPROD"},
	// dataProvider = "curatedSearchListByParamPageIdDP_negative1")
	// public void
	// SearchService_curatedSearchListByParamPageId_negative404Status(String
	// paramPageId) {
	// RequestGenerator req =
	// getQueryRequest(APINAME.CURATEDSEARCHLISTBYPARAMPAGEID, paramPageId);
	// System.out.println(req.respvalidate.returnresponseasstring());
	// AssertJUnit.assertEquals("curatedSearchListByParamPageId API is not
	// working", 404, req.response.getStatus());
	// }
	//
	// /**
	// * Get Curated Search List by Parameterized Page Id.
	// * @author jhansi.bai
	// */
	// @Test(groups = {"Sanity", "MiniRegression","Regression",
	// "ExhasutiveRegression", "RFPFOX7", "RFPQA", "RFPPROD"},
	// dataProvider = "curatedSearchListByParamPageIdDP_negative2")
	// public void SearchService_curatedSearchListByParamPageId_negative2(String
	// paramPageId) {
	// RequestGenerator req =
	// getQueryRequest(APINAME.CURATEDSEARCHLISTBYPARAMPAGEID, paramPageId);
	// System.out.println(req.respvalidate.returnresponseasstring());
	// AssertJUnit.assertEquals("curatedSearchListByParamPageId API is not
	// working", 405, req.response.getStatus());
	// }
	//
	// /**
	// * Get Curated Search List by Parameterized Page key.
	// * @author jhansi.bai
	// */
	// @Test(groups = { "Sanity", "Prodsanity", "Regression", "MiniRegression",
	// "ExhaustiveRegression", "ProdSanity",
	// "RFPFOX7", "RFPQA", "RFPPROD"}, dataProvider =
	// "curatedSearchListByParamPageKeyDP")
	// public void SearchService_curatedSearchListByParamPageKey(
	// String pageKey, String statusCode, String successMessage, String
	// successStatus, String itemCount)
	// {
	// System.out.println("pageKey--"+pageKey);
	// RequestGenerator req =
	// getQueryRequest(APINAME.CURATEDSEARCHLISTBYPARAMPAGEKEY, pageKey);
	// System.out.println(req.respvalidate.returnresponseasstring());
	// AssertJUnit.assertEquals(
	// "curatedSearchListByParamPageKey API is not working", 200,
	// req.response.getStatus());
	// }
	//
	// /**
	// * Get Curated Search List by Parameterized Page key.
	// * @author jhansi.bai
	// */
	// @Test(groups = { "Regression", "MiniRegression",
	// "ExhaustiveRegression", "RFPFOX7", "RFPQA", "RFPPROD" }, dataProvider =
	// "curatedSearchListByParamPageKeyDP")
	// public void
	// SearchService_curatedSearchListByParamPageKey_vStatusAdDataNodes(String
	// pageKey, String statusCode,
	// String successMessage, String successStatus, String itemCount)
	// {
	// RequestGenerator req =
	// getQueryRequest(APINAME.CURATEDSEARCHLISTBYPARAMPAGEKEY, pageKey);
	// System.out.println(req.returnresponseasstring());
	// AssertJUnit.assertTrue("Inval curatedSearchListByParamPageKey API status
	// nodes.",
	// req.respvalidate.DoesNodesExists(searchStatusNodes()));
	// AssertJUnit.assertTrue("Inval curatedSearchListByParamPageKey API data
	// nodes.",
	// req.respvalidate.DoesNodesExists(curatedSearchListByParamPageIdDataNodes()));
	// }
	//
	// /**
	// * Get Curated Search List by Parameterized Page key.
	// * @author jhansi.bai
	// */
	// @Test(groups = { "Regression", "MiniRegression",
	// "ExhaustiveRegression", "RFPFOX7", "RFPQA", "RFPPROD" }, dataProvider =
	// "curatedSearchListByParamPageKeyDP")
	// public void
	// SearchService_curatedSearchListByParamPageKey_vStatusAdDataNodesVals(String
	// pageKey, String statusCode,
	// String successMessage, String successStatus, String itemCount)
	// {
	// RequestGenerator req =
	// getQueryRequest(APINAME.CURATEDSEARCHLISTBYPARAMPAGEKEY, pageKey);
	// System.out.println(req.returnresponseasstring());
	// AssertJUnit.assertEquals("Status code does't match",
	// req.respvalidate.NodeValue(searchStatusNodes().get(0), true),
	// statusCode);
	// AssertJUnit.assertEquals("Status message dosn't match",
	// req.respvalidate.NodeValue(searchStatusNodes().get(1), true),
	// successMessage);
	// AssertJUnit.assertEquals("Status type dosn't match",
	// req.respvalidate.NodeValue(searchStatusNodes().get(2), true),
	// successStatus);
	// AssertJUnit.assertEquals("Status type dosn't match",
	// req.respvalidate.NodeValue(searchStatusNodes().get(3), true),
	// itemCount);
	// log.info(req.respvalidate.returnresponseasstring());
	// }
	//
	// /**
	// * Get Curated Search List by Parameterized Page key.
	// * @author jhansi.bai
	// */
	// @Test(groups = {"Sanity", "MiniRegression","Regression",
	// "ExhasutiveRegression", "RFPFOX7", "RFPQA", "RFPPROD"},
	// dataProvider = "curatedSearchListByParamPageKeyDP_negative")
	// public void SearchService_curatedSearchListByParamPageKey_negative(String
	// pageKey,
	// String errStatusCode, String errorMessage, String errorStatus, String
	// itemCount) {
	// RequestGenerator req =
	// getQueryRequest(APINAME.CURATEDSEARCHLISTBYPARAMPAGEKEY, pageKey);
	// System.out.println(req.respvalidate.returnresponseasstring());
	// AssertJUnit.assertEquals("curatedSearchListByParamPageKey API is not
	// working", 200, req.response.getStatus());
	// }
	//
	// /**
	// * Get Curated Search List by Parameterized Page key.
	// * @author jhansi.bai
	// */
	// @Test(groups = {"MiniRegression","Regression", "ExhasutiveRegression",
	// "RFPFOX7", "RFPQA", "RFPPROD"},
	// dataProvider = "curatedSearchListByParamPageKeyDP_negative")
	// public void
	// SearchService_curatedSearchListByParamPageKey_vNegativeStatusNodes(String
	// pageKey,
	// String errStatusCode, String errorMessage, String errorStatus, String
	// itemCount) {
	// RequestGenerator req =
	// getQueryRequest(APINAME.CURATEDSEARCHLISTBYPARAMPAGEKEY, pageKey);
	// System.out.println(req.respvalidate.returnresponseasstring());
	// AssertJUnit.assertTrue("Inval curatedSearchListByParamPageKey API status
	// nodes.",
	// req.respvalidate.DoesNodesExists(searchStatusNodes()));
	// }
	//
	// /**
	// * Get Curated Search List by Parameterized Page key.
	// * @author jhansi.bai
	// */
	// @Test(groups = {"MiniRegression","Regression", "ExhasutiveRegression",
	// "RFPFOX7", "RFPQA", "RFPPROD"},
	// dataProvider = "curatedSearchListByParamPageKeyDP_negative")
	// public void
	// SearchService_curatedSearchListByParamPageKey_vNegativeStatusVals(String
	// pageKey,
	// String errStatusCode, String errorMessage, String errorStatus, String
	// itemCount) {
	// RequestGenerator req =
	// getQueryRequest(APINAME.CURATEDSEARCHLISTBYPARAMPAGEKEY, pageKey);
	// System.out.println(req.respvalidate.returnresponseasstring());
	// AssertJUnit.assertEquals("Status code does't match",
	// req.respvalidate.NodeValue(searchStatusNodes().get(0), true),
	// errStatusCode);
	// AssertJUnit.assertEquals("Status message dosn't match",
	// req.respvalidate.NodeValue(searchStatusNodes().get(1), true),
	// errorMessage);
	// AssertJUnit.assertEquals("Status type dosn't match",
	// req.respvalidate.NodeValue(searchStatusNodes().get(2), true),
	// errorStatus);
	// AssertJUnit.assertEquals("Total count dosn't match",
	// req.respvalidate.NodeValue(searchStatusNodes().get(3), true),
	// itemCount);
	// log.info(req.respvalidate.returnresponseasstring());
	// }
	//
	// /**
	// * Get Curated Search List by Parameterized Page key.
	// * @author jhansi.bai
	// */
	// @Test(groups = {"Sanity", "MiniRegression","Regression",
	// "ExhasutiveRegression", "RFPFOX7", "RFPQA", "RFPPROD"},
	// dataProvider = "curatedSearchListByParamPageKeyDP_negative1")
	// public void
	// SearchService_curatedSearchListByParamPageKey_negative405Status(String
	// paramPageId) {
	// RequestGenerator req =
	// getQueryRequest(APINAME.CURATEDSEARCHLISTBYPARAMPAGEKEY, paramPageId);
	// System.out.println(req.respvalidate.returnresponseasstring());
	// AssertJUnit.assertEquals("curatedSearchListByParamPageId API is not
	// working", 405, req.response.getStatus());
	// }
	//
	// /**
	// * Get Style Info
	// * @author Harikishan
	// *
	// */
	//
	//
	// @Test(groups = { "Sanity", "Prodsanity", "Regression", "MiniRegression",
	// "ExhaustiveRegression", "RFPFOX7", "RFPQA", "RFPPROD" }, dataProvider =
	// "SearchService_getStyleInfo")
	// public void SearchService_getStyleInfoPostRequest(String param1){
	//
	// RequestGenerator req = getRequest(APINAME.GETSTYLEINFO, param1);
	// System.out.println(req.respvalidate.returnresponseasstring());
	// AssertJUnit.assertEquals("SearchService_getStyleInfo not working as
	// expected", 200, req.response.getStatus());
	//
	//
	// }

	// /*@Test(groups = { "Sanity", "Prodsanity", "Regression",
	// "MiniRegression",
	// "ExhaustiveRegression", "RFPFOX7", "RFPQA", "RFPPROD", "SearchSanity" },
	// dataProvider = "SearchService_filteredQuerySolrSharding")
	// public void SearchService_FilteredQuery(String payload) throws
	// NumberFormatException, InvalidSyntaxException
	// {
	// //System.out.println(payload);
	// String diff = "";
	// boolean respCompare = true;
	// //ArrayList listOfStyleIdsOne = new ArrayList();
	// //ArrayList listOfStyleIdsTwo = new ArrayList();
	// String interCount = "";
	// long startTimeOne = Calendar.getInstance().getTimeInMillis();
	// MyntraService serviceOne = new
	// MyntraService(ServiceType.PORTAL_SEARCHSERVICE,
	// APINAME.FILTEREDSEARCH,init.Configurations, payload);
	// System.out.println(serviceOne.URL);
	// System.out.println(serviceOne.Payload);
	// RequestGenerator reqOne = new RequestGenerator(serviceOne);
	// long endTime = Calendar.getInstance().getTimeInMillis();
	// double timeTaken = (endTime - startTimeOne)/1000.0;
	// //System.out.println("Time Taken : "+timeTaken);
	// String resOne = reqOne.respvalidate.returnresponseasstring();
	// //System.out.println(reqOne.respvalidate.returnresponseasstring());
	// String productCountStr =
	// reqOne.respvalidate.GetNodeValue_Argo("response1.totalProductsCount",
	// resOne);
	// int productCount =
	// Integer.parseInt(productCountStr.substring(productCountStr.indexOf("'")+1,
	// productCountStr.lastIndexOf("'")));
	// System.out.println("Product Count : "+productCount);
	// //String respOne = JsonPath.read(resOne, "$.data.queryResult");
	// //listOfStyleIdsOne = JsonPath.read(respOne,
	// "$.response1..products[*].styleid");
	//
	//
	// long startTimeTwo = Calendar.getInstance().getTimeInMillis();
	// MyntraService serviceTwo = new
	// MyntraService(ServiceType.PORTAL_SEARCHSERVICE,
	// APINAME.FILTEREDSEARCH,init.Configurations, payload);
	// serviceTwo.URL =
	// "http://s5.myntra.com:7400/search-service/searchservice/search/filteredSearch";
	// RequestGenerator req = new RequestGenerator(serviceTwo);
	// long endTimeTwo = Calendar.getInstance().getTimeInMillis();
	// double timeTakenTwo = (endTimeTwo - startTimeTwo)/1000.0;
	// //System.out.println("Time Taken : "+timeTakenTwo);
	// String resTwo = req.respvalidate.returnresponseasstring();
	// //System.out.println(req.respvalidate.returnresponseasstring());
	// String productCountStrTwo =
	// req.respvalidate.GetNodeValue_Argo("response1.totalProductsCount",
	// resTwo);
	// int productCountTwo =
	// Integer.parseInt(productCountStrTwo.substring(productCountStrTwo.indexOf("'")+1,
	// productCountStrTwo.lastIndexOf("'")));
	// //System.out.println("Product Count : "+productCountTwo);
	// //String respTwo = JsonPath.read(resTwo, "$.data.queryResult");
	// //listOfStyleIdsTwo = JsonPath.read(respTwo,
	// "$.response1..products[*].styleid");
	//
	//
	// if(productCount > productCountTwo)
	// diff = "-"+(productCount - productCountTwo);
	// else if(productCount < productCountTwo)
	// diff = "+"+(productCountTwo - productCount);
	// else
	// diff = "0";
	// int diffLineNumber = compareLineByLine(resOne, resTwo);
	// if(diffLineNumber != 0)
	// respCompare = false;
	//
	//
	// AssertJUnit.assertEquals("getQueryWithTrueReturnDoc is not working",200,
	// reqOne.response.getStatus());
	// AssertJUnit.assertTrue("Product Count should be > 0", productCount > 1);
	// //AssertJUnit.assertEquals("getQueryWithTrueReturnDoc is not
	// working",200, req.response.getStatus());
	//
	//
	// String interSaction = intersectionOfTwoArrayList(listOfStyleIdsOne,
	// listOfStyleIdsTwo);
	// if(!interSaction.equalsIgnoreCase("NA"))
	// interCount = " == "+interSaction.split(" ").length;
	//
	//
	// if(respCompare)
	// sb.append("<tr><td>"+serviceOne.Payload+"</td><td>"+timeTaken+"</td><td>"+timeTakenTwo+"</td><td>"+productCount+"</td><td>"+productCountTwo+"</td><td>"+diff+"</td><td>"+respCompare+"</td>\n");
	// else
	// sb.append("<tr><td>"+serviceOne.Payload+"</td><td>"+timeTaken+"</td><td>"+timeTakenTwo+"</td><td>"+productCount+"</td><td>"+productCountTwo+"</td><td>"+diff+"</td><td
	// style=\"color:red\">"+respCompare+"</td>\n");
	//
	// System.out.println(++counter);
	//
	// }*/
	//
	//
	// /*@AfterTest
	// public void generateReport(){
	// String initTable = "";
	// String tearDownTable = "</table></div></body></html>";
	// StringBuffer finalReport = new StringBuffer();
	// try {
	// initTable =
	// getFileAsString("/Users/vivek.vasvani/Desktop/SolrSharding/tableInit.txt");
	// } catch (Exception e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	//
	// finalReport.append(initTable);
	// finalReport.append(sb);
	// finalReport.append(tearDownTable);
	// try {
	// writeStringAsFile(finalReport,
	// "/Users/vivek.vasvani/Desktop/SolrSharding/TestReport_filteredSearch.html");
	// } catch (IOException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }*/
	//
	// public static void writeStringAsFile(StringBuffer sb, String filePath)
	// throws IOException{
	// //String completeFilePath =
	// rootJmxFolder+File.separator+serviceName+File.separator+apiName+File.separator+apiName+"Csv.csv";
	// File f = new File(filePath);
	// if(f.exists())
	// f.delete();
	// else
	// f.createNewFile();
	//
	// PrintWriter pw = new PrintWriter(new FileOutputStream(f));
	// pw.write(sb.toString());
	// pw.close();
	// }
	//
	// public static String getFileAsString(String Path) throws Exception{
	// File f = new File(Path);
	// if(f.exists()){
	// String line;
	// StringBuffer sb = new StringBuffer();
	// BufferedReader bf = new BufferedReader(new FileReader(f));
	// while((line = bf.readLine())!=null){
	// sb.append(line+"\n");
	// }
	// return sb.toString();
	// }else{
	// System.out.println("ERROR : "+Path+" file not found");
	// return "";
	// }
	//
	// }
	//
	// /*@Test
	// public String intersectionOfTwoArrayList(ArrayList a, ArrayList b){
	// String toReturn = "";
	// if(a.size() > 0 && b.size()> 0){
	// //a-b
	// List<Integer> ab = new ArrayList<Integer>();
	// ab.addAll(a);
	// ab.removeAll(b);
	//
	// //b-a
	// List<Integer> ba = new ArrayList<Integer>();
	// ba.addAll(b);
	// ba.removeAll(a);
	//
	// List<Integer> diff = new ArrayList<Integer>(ab.size() + ba.size());
	// addNoDups(diff,ab);
	// addNoDups(diff,ba);
	//
	// for(int intersaction : diff){
	// toReturn += intersaction+" ";
	// }
	// if(diff.size() == 0)
	// toReturn = "NA";
	// else
	// toReturn = toReturn.substring(0, toReturn.length()-1);
	// }else{
	// toReturn = "NA";
	// }
	// return toReturn;
	// }*/
	//
	// private static void addNoDups(List<Integer> toAddTo,List<Integer>
	// iterateOver) {
	// for(Integer num:iterateOver){
	// if(toAddTo.indexOf(num) == -1) {
	// toAddTo.add(num);
	// }
	// }
	// }
	//
	// private int compareLineByLine(String a, String b){
	// String[] first = a.split("\n");
	// String[] sec = b.split("\n");
	// if(first.length != sec.length){
	// return -1;
	// }
	// for(int i=0;i<first.length;i++){
	// if(!first[i].equalsIgnoreCase(sec[i])){
	// return i;
	// }
	// }
	// return 0;
	// }

	// /**
	// * Get a Query
	// * @author jhansi.bai
	// */
	// @Test(groups = { "Regression", "MiniRegression",
	// "ExhaustiveRegression", "RFPFOX7", "RFPQA", "RFPPROD" }, dataProvider =
	// "SearchService_GetQuery_withfalsereturndoc")
	// public void SearchService_getQueryWithFalseReturnDo_vDataNodes(String
	// query,
	// String rows, String returnDocs, String isFacet)
	// {
	// RequestGenerator req = getRequest(APINAME.GETQUERY, query, rows,
	// returnDocs, isFacet);
	// System.out.println(req.respvalidate.returnresponseasstring());
	// AssertJUnit.assertTrue("Inval AutoSuggest API status nodes.",
	// req.respvalidate.DoesNodesExists(getQueryDataNodes()));
	// }
	//
	// /**
	// * Get a Query
	// * @author jhansi.bai
	// */
	// @Test(groups = { "Sanity", "Regression", "MiniRegression",
	// "ExhaustiveRegression", "RFPFOX7", "RFPQA", "RFPPROD" }, dataProvider =
	// "SearchService_GetQuery_withfalsereturndoc")
	// public void
	// SearchService_getQueryWithFalseReturnDo_vSearchNodesVals(String query,
	// String rows, String returnDocs, String isFacet)
	// {
	// RequestGenerator req = getRequest(APINAME.GETQUERY, query, rows,
	// returnDocs, isFacet);
	// String response = req.respvalidate.returnresponseasstring();
	// // System.out.println(response);
	// AssertJUnit.assertTrue("query dosn't match",
	// req.respvalidate.NodeValue(getQueryDataSearchNodes().get(0),
	// true).toLowerCase().contains(query.toLowerCase()));
	// AssertJUnit.assertEquals("rows dosn't match",
	// req.respvalidate.NodeValue(getQueryDataSearchNodes().get(2), true),
	// rows);
	// AssertJUnit.assertEquals("returnDocs dosn't match",
	// req.respvalidate.NodeValue(getQueryDataSearchNodes().get(6), true),
	// returnDocs);
	// AssertJUnit.assertEquals("isFacet dosn't match",
	// req.respvalidate.NodeValue(getQueryDataSearchNodes().get(9), true),
	// isFacet);
	// AssertJUnit.assertTrue("totalProductsCount dosn't match",
	// req.respvalidate.NodeValue(getQueryDataNodes().get(1), true).
	// contains("\"totalProductsCount\\\":"+req.respvalidate.NodeValue(getQueryDataNodes().get(5),
	// true)));
	// //verify filters, and products details
	// String queryResult = JsonPath.read(response, "$.data.queryResult");
	// String filterNodes = apiUtil.GetNodeValue("response1.filters",
	// queryResult);
	// String productNodes = apiUtil.GetNodeValue("response1.products",
	// queryResult);
	//
	// if(returnDocs.equals("false") && isFacet.equals("true")){
	// AssertJUnit.assertTrue("products details are available",
	// productNodes.equals("[]"));
	// AssertJUnit.assertTrue("filters details are not available",
	// !filterNodes.equals("{}"));
	// }
	// else if(returnDocs.equals("false") && isFacet.equals("false")){
	// AssertJUnit.assertTrue("products details are available",
	// productNodes.equals("[]"));
	// AssertJUnit.assertTrue("filters details are available",
	// filterNodes.equals("{}"));
	// }
	// }
	//
	// /**
	// * invalidate cache.
	// * @author jhansi.bai
	// */
	// @Test(groups = { "Smoke", "Sanity", "Prodsanity", "Regression",
	// "MiniRegression",
	// "ExhaustiveRegression", "RFPFOX7", "RFPQA", "RFPPROD"}, dataProvider =
	// "invalidateCacheDP")
	// public void SearchService_invalidateCache(String BrandName, String
	// dataSuccess, String dataMessage)
	// {
	// RequestGenerator req = getQueryRequest(APINAME.INVALIDATECACHE,
	// BrandName);
	// System.out.println(req.returnresponseasstring());
	// AssertJUnit.assertEquals("Invalidate cache for brands API is not
	// working",
	// 200, req.response.getStatus());
	// }
	//
	// /**
	// * invalidate cache.
	// * @author jhansi.bai
	// */
	// @Test(groups = { "Smoke", "Sanity", "Prodsanity", "Regression",
	// "MiniRegression",
	// "ExhaustiveRegression", "RFPFOX7", "RFPQA", "RFPPROD"}, dataProvider =
	// "invalidateCacheDP")
	// public void SearchService_invalidateCache_vdataNodes(String BrandName,
	// String dataSuccess, String dataMessage)
	// {
	// RequestGenerator req = getQueryRequest(APINAME.INVALIDATECACHE,
	// BrandName);
	// System.out.println(req.returnresponseasstring());
	// AssertJUnit.assertTrue("Invalidate cache for brands API status nodes.",
	// req.respvalidate.DoesNodesExists(addnewLandingPageDataNodes()));
	// }
	//
	// /**
	// * invalidate cache.
	// * @author jhansi.bai
	// */
	// @Test(groups = { "Smoke", "Sanity", "Prodsanity", "Regression",
	// "MiniRegression",
	// "ExhaustiveRegression", "RFPFOX7", "RFPQA", "RFPPROD"}, dataProvider =
	// "invalidateCacheDP")
	// public void SearchService_invalidateCache_vdataNodesVals(String
	// BrandName, String dataSuccess, String dataMessage)
	// {
	// RequestGenerator req = getQueryRequest(APINAME.INVALIDATECACHE,
	// BrandName);
	// System.out.println(req.returnresponseasstring());
	// AssertJUnit.assertEquals("dataSuccess dosn't match",
	// req.respvalidate.NodeValue(addnewLandingPageDataNodes().get(0), true),
	// dataSuccess);
	// AssertJUnit.assertEquals("dataMessage dosn't match",
	// req.respvalidate.NodeValue(addnewLandingPageDataNodes().get(1), true),
	// dataMessage);
	// }
	//
	// /**
	// * re-cache
	// * @author jhansi.bai
	// */
	// @Test(groups = { "Smoke", "Sanity", "Prodsanity", "Regression",
	// "MiniRegression",
	// "ExhaustiveRegression", "RFPFOX7", "RFPQA", "RFPPROD"}, dataProvider =
	// "reCachedDP")
	// public void SearchService_reCache(String BrandName, String dataSuccess,
	// String dataMessage)
	// {
	// RequestGenerator req = getQueryRequest(APINAME.RECACHE, BrandName);
	// System.out.println(req.returnresponseasstring());
	// AssertJUnit.assertEquals("Re cache for brands API is not working",
	// 200, req.response.getStatus());
	// }
	//
	// /**
	// * re-cache
	// * @author jhansi.bai
	// */
	// @Test(groups = { "Smoke", "Sanity", "Prodsanity", "Regression",
	// "MiniRegression",
	// "ExhaustiveRegression", "RFPFOX7", "RFPQA", "RFPPROD"}, dataProvider =
	// "reCachedDP")
	// public void SearchService_reCache_vDataNodes(String BrandName, String
	// dataSuccess, String dataMessage)
	// {
	// RequestGenerator req = getQueryRequest(APINAME.RECACHE, BrandName);
	// System.out.println(req.returnresponseasstring());
	// AssertJUnit.assertTrue("Invalidate cache for brands API status nodes.",
	// req.respvalidate.DoesNodesExists(addnewLandingPageDataNodes()));
	// }
	//
	// /**
	// * re-cache
	// * @author jhansi.bai
	// */
	// @Test(groups = { "Smoke", "Sanity", "Prodsanity", "Regression",
	// "MiniRegression",
	// "ExhaustiveRegression", "RFPFOX7", "RFPQA", "RFPPROD"}, dataProvider =
	// "reCachedDP")
	// public void SearchService_reCache_vDataNodesVals(String BrandName, String
	// dataSuccess, String dataMessage)
	// {
	// RequestGenerator req = getQueryRequest(APINAME.RECACHE, BrandName);
	// System.out.println(req.returnresponseasstring());
	// AssertJUnit.assertEquals("dataSuccess dosn't match",
	// req.respvalidate.NodeValue(addnewLandingPageDataNodes().get(0), true),
	// dataSuccess);
	// AssertJUnit.assertEquals("dataMessage dosn't match",
	// req.respvalidate.NodeValue(addnewLandingPageDataNodes().get(1), true),
	// dataMessage);
	// }
	//
	// /**
	// * re-cache
	// * @author jhansi.bai
	// */
	// @Test(groups = { "Smoke", "Sanity", "Prodsanity", "Regression",
	// "MiniRegression",
	// "ExhaustiveRegression", "RFPFOX7", "RFPQA", "RFPPROD"}, dataProvider =
	// "reCachedDP_negative")
	// public void SearchService_reCache_negative(String BrandName)
	// {
	// RequestGenerator req = getQueryRequest(APINAME.RECACHE, BrandName);
	// System.out.println(req.returnresponseasstring());
	// AssertJUnit.assertEquals("Re cache for brands API is not working",
	// 500, req.response.getStatus());
	// }
	//
	// /**
	// * Invalidate all cache
	// * @author jhansi.bai
	// */
	// @Test(groups = { "Smoke", "Sanity", "Prodsanity", "Regression",
	// "MiniRegression",
	// "ExhaustiveRegression", "RFPFOX7", "RFPQA", "RFPPROD"})
	// public void SearchService_invalidateCacheAll()
	// {
	// RequestGenerator req = getRequest(APINAME.INVALIDATECACHEALL);
	// System.out.println(req.returnresponseasstring());
	// AssertJUnit.assertEquals("Invalidated all cache for brands API is not
	// working",
	// 200, req.response.getStatus());
	// }
	//
	// /**
	// * Invalidate all cache
	// * @author jhansi.bai
	// */
	// @Test(groups = { "Smoke", "Sanity", "Prodsanity", "Regression",
	// "MiniRegression",
	// "ExhaustiveRegression", "RFPFOX7", "RFPQA", "RFPPROD"})
	// public void SearchService_invalidateCacheAll_vDataNodes()
	// {
	// RequestGenerator req = getRequest(APINAME.INVALIDATECACHEALL);
	// System.out.println(req.returnresponseasstring());
	// AssertJUnit.assertTrue("Invalidated all cache for brands API status
	// nodes.",
	// req.respvalidate.DoesNodesExists(addnewLandingPageDataNodes()));
	// }
	//
	// /**
	// * Invalidate all cache
	// * @author jhansi.bai
	// */
	// @Test(groups = { "Smoke", "Sanity", "Prodsanity", "Regression",
	// "MiniRegression",
	// "ExhaustiveRegression", "RFPFOX7", "RFPQA", "RFPPROD"})
	// public void SearchService_invalidateCacheAll_vDataNodesVals()
	// {
	// RequestGenerator req = getRequest(APINAME.INVALIDATECACHEALL);
	// System.out.println(req.returnresponseasstring());
	// AssertJUnit.assertEquals("dataSuccess dosn't match",
	// req.respvalidate.NodeValue(addnewLandingPageDataNodes().get(0), true),
	// "true");
	// AssertJUnit.assertEquals("dataMessage dosn't match",
	// req.respvalidate.NodeValue(addnewLandingPageDataNodes().get(1), true),
	// "\"Invalidated all cache entries\"");
	// }
	//
	//
	// //-----------------------------Below test cases are commented due to
	// theses Apis are skipped ------------------------
	//
	// /**
	// * Save or Update a Parameterized Page.
	// * @author jhansi.bai
	// *//*
	// @Test(groups = { "Sanity", "Regression", "MiniRegression",
	// "ExhaustiveRegression", "RFPFOX7", "RFPQA" }, dataProvider =
	// "saveOrUpdateParamPageDP")
	// public void SearchService_saveOrUpdateParamPage(String paramString,
	// String pageDesc, String statusCode,
	// String successMessage, String successStatus)
	// {
	// RequestGenerator req = getRequest(APINAME.SAVEORUPDATEPARAMPAGE,
	// paramString, pageDesc);
	// System.out.println(req.respvalidate.returnresponseasstring());
	// AssertJUnit.assertEquals("saveOrUpdateParamPage is not working", 200,
	// req.response.getStatus());
	// }*/
	//
	// /**
	// * Add a new Landing Page.
	// * @author jhansi.bai
	// *//*
	// @Test(groups = { "Sanity", "Regression", "MiniRegression",
	// "ExhaustiveRegression", "RFPFOX7", "RFPQA" }, dataProvider =
	// "addnewLandingPageDP")
	// public void SearchService_addnewLandingPage(String pageUrl, String
	// paramPageId, String altText, String statusCode,
	// String successMessage, String successStatus, String itemCount, String
	// dataSuccess, String dataMessage)
	// {
	// RequestGenerator req = getRequest(APINAME.ADDNEWLANDINGPAGE, pageUrl,
	// paramPageId, altText);
	// System.out.println(req.respvalidate.returnresponseasstring());
	// AssertJUnit.assertEquals("addnewLandingPage is not working", 200,
	// req.response.getStatus());
	// }
	// */
	// /*
	// *//**
	// * Add a new Landing Page.
	// * @author jhansi.bai
	// *//*
	// @Test(groups = { "Sanity", "Regression", "MiniRegression",
	// "ExhaustiveRegression", "RFPFOX7", "RFPQA" }, dataProvider =
	// "addnewLandingPageDP_negative1")
	// public void SearchService_addnewLandingPage_negative1(String pageUrl,
	// String paramPageId, String altText,
	// String errStatusCode, String errorMessage, String errorStatus, String
	// itemCount, String dataSuccess, String dataMessage)
	// {
	// RequestGenerator req = getRequest(APINAME.ADDNEWLANDINGPAGE, pageUrl,
	// paramPageId, altText);
	// System.out.println(req.respvalidate.returnresponseasstring());
	// AssertJUnit.assertEquals("addnewLandingPage is not working", 200,
	// req.response.getStatus());
	// }
	//
	// *//**
	// * Add a new Landing Page.
	// * @author jhansi.bai
	// *//*
	// @Test(groups = { "Sanity", "Regression", "MiniRegression",
	// "ExhaustiveRegression", "RFPFOX7", "RFPQA" },
	// dataProvider = "addnewLandingPageDP_negative1")
	// public void
	// SearchService_addnewLandingPage_vNegativeStatusAdDataNodes1(String
	// pageUrl, String paramPageId, String altText,
	// String errStatusCode, String errorMessage, String errorStatus, String
	// itemCount, String dataSuccess, String dataMessage)
	// {
	// RequestGenerator req = getRequest(APINAME.ADDNEWLANDINGPAGE, pageUrl,
	// paramPageId, altText);
	// System.out.println(req.respvalidate.returnresponseasstring());
	// AssertJUnit.assertTrue("Inval curatedSearchListByParamPageId API status
	// nodes.",
	// req.respvalidate.DoesNodesExists(searchStatusNodes()));
	// AssertJUnit.assertTrue("Inval curatedSearchListByParamPageId API data
	// nodes.",
	// req.respvalidate.DoesNodesExists(addnewLandingPageDataNodes()));
	// }
	//
	// *//**
	// * Add a new Landing Page.
	// * @author jhansi.bai
	// *//*
	// @Test(groups = { "Sanity", "Regression", "MiniRegression",
	// "ExhaustiveRegression", "RFPFOX7", "RFPQA" }, dataProvider =
	// "addnewLandingPageDP_negative1")
	// public void
	// SearchService_addnewLandingPage_vNegativeStatusAdDataNodesVals1(String
	// pageUrl, String paramPageId, String altText,
	// String errStatusCode, String errorMessage, String errorStatus, String
	// itemCount, String dataSuccess, String dataMessage)
	// {
	// RequestGenerator req = getRequest(APINAME.ADDNEWLANDINGPAGE, pageUrl,
	// paramPageId, altText);
	// System.out.println(req.respvalidate.returnresponseasstring());
	// AssertJUnit.assertEquals("Status code does't match",
	// req.respvalidate.NodeValue(searchStatusNodes().get(0), true),
	// errStatusCode);
	// AssertJUnit.assertEquals("Status message dosn't match",
	// req.respvalidate.NodeValue(searchStatusNodes().get(1), true),
	// errorMessage);
	// AssertJUnit.assertEquals("Status type dosn't match",
	// req.respvalidate.NodeValue(searchStatusNodes().get(2), true),
	// errorStatus);
	// AssertJUnit.assertEquals("Status type dosn't match",
	// req.respvalidate.NodeValue(searchStatusNodes().get(3), true),
	// itemCount);
	// AssertJUnit.assertEquals("pageUrl dosn't match",
	// req.respvalidate.NodeValue(addnewLandingPageDataNodes().get(0), true),
	// dataSuccess);
	// AssertJUnit.assertEquals("pageUrl dosn't match",
	// req.respvalidate.NodeValue(addnewLandingPageDataNodes().get(1), true),
	// dataMessage);
	// log.info(req.respvalidate.returnresponseasstring());
	// }
	//
	// *//**
	// * Add a new Landing Page.
	// * @author jhansi.bai
	// *//*
	// @Test(groups = { "Sanity", "Regression", "MiniRegression",
	// "ExhaustiveRegression", "RFPFOX7", "RFPQA" }, dataProvider =
	// "addnewLandingPageDP_negative2")
	// public void SearchService_addnewLandingPage_negative2(String pageUrl,
	// String paramPageId, String altText,
	// String errStatusCode, String errorMessage, String errorStatus, String
	// itemCount, String dataSuccess, String dataMessage)
	// {
	// RequestGenerator req = getRequest(APINAME.ADDNEWLANDINGPAGE, pageUrl,
	// paramPageId, altText);
	// System.out.println(req.respvalidate.returnresponseasstring());
	// AssertJUnit.assertEquals("addnewLandingPage is not working", 200,
	// req.response.getStatus());
	// }
	//
	// *//**
	// * Add a new Landing Page.
	// * @author jhansi.bai
	// *//*
	// @Test(groups = { "Sanity", "Regression", "MiniRegression",
	// "ExhaustiveRegression", "RFPFOX7", "RFPQA" },
	// dataProvider = "addnewLandingPageDP_negative2")
	// public void
	// SearchService_addnewLandingPage_vNegativeStatusAdDataNodes2(String
	// pageUrl, String paramPageId, String altText,
	// String errStatusCode, String errorMessage, String errorStatus, String
	// itemCount, String dataSuccess, String dataMessage)
	// {
	// RequestGenerator req = getRequest(APINAME.ADDNEWLANDINGPAGE, pageUrl,
	// paramPageId, altText);
	// System.out.println(req.respvalidate.returnresponseasstring());
	// AssertJUnit.assertTrue("Inval curatedSearchListByParamPageId API status
	// nodes.",
	// req.respvalidate.DoesNodesExists(searchStatusNodes()));
	// AssertJUnit.assertTrue("Inval curatedSearchListByParamPageId API data
	// nodes.",
	// req.respvalidate.DoesNodesExists(addnewLandingPageDataNodes()));
	// }
	//
	// *//**
	// * Add a new Landing Page.
	// * @author jhansi.bai
	// *//*
	// @Test(groups = { "Sanity", "Regression", "MiniRegression",
	// "ExhaustiveRegression", "RFPFOX7", "RFPQA" }, dataProvider =
	// "addnewLandingPageDP_negative2")
	// public void
	// SearchService_addnewLandingPage_vNegativeStatusAdDataNodesVals2(String
	// pageUrl, String paramPageId, String altText,
	// String errStatusCode, String errorMessage, String errorStatus, String
	// itemCount, String dataSuccess, String dataMessage)
	// {
	// RequestGenerator req = getRequest(APINAME.ADDNEWLANDINGPAGE, pageUrl,
	// paramPageId, altText);
	// System.out.println(req.respvalidate.returnresponseasstring());
	// AssertJUnit.assertEquals("Status code does't match",
	// req.respvalidate.NodeValue(searchStatusNodes().get(0), true),
	// errStatusCode);
	// AssertJUnit.assertEquals("Status message dosn't match",
	// req.respvalidate.NodeValue(searchStatusNodes().get(1), true),
	// errorMessage);
	// AssertJUnit.assertEquals("Status type dosn't match",
	// req.respvalidate.NodeValue(searchStatusNodes().get(2), true),
	// errorStatus);
	// AssertJUnit.assertEquals("Status type dosn't match",
	// req.respvalidate.NodeValue(searchStatusNodes().get(3), true),
	// itemCount);
	// AssertJUnit.assertEquals("pageUrl dosn't match",
	// req.respvalidate.NodeValue(addnewLandingPageDataNodes().get(0), true),
	// dataSuccess);
	// AssertJUnit.assertEquals("pageUrl dosn't match",
	// req.respvalidate.NodeValue(addnewLandingPageDataNodes().get(1), true),
	// dataMessage);
	// log.info(req.respvalidate.returnresponseasstring());
	// }
	//
	// *//**
	// * Add a new Landing Page.
	// * @author jhansi.bai
	// *//*
	// @Test(groups = { "Sanity", "Regression", "MiniRegression",
	// "ExhaustiveRegression", "RFPFOX7", "RFPQA" }, dataProvider =
	// "addnewLandingPageDP_negative3")
	// public void SearchService_addnewLandingPage_negative3(String pageUrl,
	// String paramPageId, String altText)
	// {
	// RequestGenerator req = getRequest(APINAME.ADDNEWLANDINGPAGE, pageUrl,
	// paramPageId, altText);
	// System.out.println(req.respvalidate.returnresponseasstring());
	// AssertJUnit.assertEquals("addnewLandingPage is not working", 500,
	// req.response.getStatus());
	// }*/
	//
	// @Test(groups = { "Smoke", "Sanity", "Prodsanity", "Regression",
	// "MiniRegression",
	// "ExhaustiveRegression"}, dataProvider = "SearchService_getQueryWithtFq")
	// public void SearchService_getQueryWithFq(String query, String returnDocs,
	// String isFacet, String fq)
	// {
	// MyntraService ms = Myntra.getService(ServiceType.PORTAL_SEARCHSERVICE,
	// APINAME.GETQUERYWITHFQ, init.Configurations, new String[] {query,
	// returnDocs, isFacet, fq});
	// System.out.println(ms.Payload);
	// RequestGenerator req = new RequestGenerator(ms);
	// String response = req.returnresponseasstring();
	// System.out.println(response);
	// AssertJUnit.assertEquals("GetQueryGetRequest is not working", 200,
	// req.response.getStatus());
	// AssertJUnit.assertTrue("Query dosn't match",
	// req.respvalidate.NodeValue(getQueryDataSearchNodes().get(0),
	// true).toLowerCase().contains(query.toLowerCase()));
	// AssertJUnit.assertEquals("returnDocs dosn't match",
	// req.respvalidate.NodeValue(getQueryDataSearchNodes().get(6), true),
	// returnDocs);
	// AssertJUnit.assertEquals("isFacet dosn't match",
	// req.respvalidate.NodeValue(getQueryDataSearchNodes().get(9), true),
	// isFacet);
	// AssertJUnit.assertTrue("totalProductsCount dosn't match",
	// req.respvalidate.NodeValue(getQueryDataNodes().get(1), true).
	// contains("\"totalProductsCount\\\":"+req.respvalidate.NodeValue(getQueryDataNodes().get(5),
	// true)));
	//
	// //verify filters, and products details
	// String queryResult = JsonPath.read(response, "$.data.queryResult");
	// String filterNodes = apiUtil.GetNodeValue("response1.filters",
	// queryResult);
	// String productNodes = apiUtil.GetNodeValue("response1.products",
	// queryResult);
	//
	// if(returnDocs.equals("true") && isFacet.equals("true")){
	// AssertJUnit.assertTrue("products details are available",
	// !productNodes.equals("[]"));
	// AssertJUnit.assertTrue("filters details are not available",
	// !filterNodes.equals("{}"));
	// }
	// else if(returnDocs.equals("true") && isFacet.equals("false")){
	// AssertJUnit.assertTrue("products details are available",
	// !productNodes.equals("[]"));
	// AssertJUnit.assertTrue("filters details are available",
	// filterNodes.equals("{}"));
	// }
	// }
	//
	// @Test(groups = { "Smoke", "Sanity", "Regression",
	// "MiniRegression","ExhaustiveRegression"}, dataProvider =
	// "SearchService_createCuratedPageDataProvider")
	// public void SearchService_curatedPageCreation(String page, String
	// styleids)
	// {
	// MyntraService ms = Myntra.getService(ServiceType.PORTAL_SEARCHSERVICE,
	// APINAME.CURATEDPAGECREATION, init.Configurations, new
	// String[]{styleids});
	// ms.URL = apiUtil.prepareparameterizedURL(ms.URL, page);
	// System.out.println(ms.URL);
	// System.out.println(ms.Payload);
	// RequestGenerator req = new RequestGenerator(ms);
	// String response = req.returnresponseasstring();
	// System.out.println(response);
	// AssertJUnit.assertEquals("curatedPageCreation is not working", 200,
	// req.response.getStatus());
	// /*
	// AssertJUnit.assertTrue("Query dosn't match",
	// req.respvalidate.NodeValue("status.statusCode", true).equals("3"));
	// AssertJUnit.assertTrue("returnDocs dosn't match",
	// req.respvalidate.NodeValue("status.statusMessage",
	// true).toLowerCase().contains("Suggest indexing is
	// successful".toLowerCase()));
	// */
	// }
	//
	//
	//
	// @Test(groups = { "SchemaValidation" }, dataProvider =
	// "SearchServiceDP_landingPageForBrands_verifyResponseDataNodesUsingSchemaValidations")
	// public void
	// SearchService_landingPageForBrands_verifyResponseDataNodesUsingSchemaValidations(String
	// BrandName, String statusCode, String successMessage,
	// String successStatus)
	// {
	// RequestGenerator landingPageForBrandsReqGen =
	// getQueryRequest(APINAME.LANDINGPAGE, BrandName);
	// String landingPageForBrandsResponse =
	// landingPageForBrandsReqGen.respvalidate.returnresponseasstring();
	// System.out.println("\nPrinting SearchService landingPageForBrands API
	// response :\n\n"+landingPageForBrandsResponse+"\n");
	// log.info("\nPrinting SearchService landingPageForBrands API response
	// :\n\n"+landingPageForBrandsResponse+"\n");
	//
	// AssertJUnit.assertEquals("[SearchService landingPageForBrands API is not
	// working.]", 200, landingPageForBrandsReqGen.response.getStatus());
	//
	// try {
	// String jsonschema = new
	// Toolbox().readFileAsString("Data/SchemaSet/JSON/searchservice-landingpageforbrands-schema.txt");
	// List<String> missingNodeList =
	// validateServiceResponseNodesUsingSchemaValidator(landingPageForBrandsResponse,
	// jsonschema);
	// AssertJUnit.assertTrue(missingNodeList+" nodes are missing in
	// SearchService landingPageForBrands API response",
	// CollectionUtils.isEmpty(missingNodeList));
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
	//
	// @Test(groups = { "SchemaValidation" }, dataProvider =
	// "SearchServiceDP_parameterisedPageByPageKey_verifyResponseDataNodesUsingSchemaValidations")
	// public void
	// SearchService_parameterisedPageByPageKey_verifyResponseDataNodesUsingSchemaValidations(String
	// pageKkey, String statusCode, String successMessage,
	// String successStatus)
	// {
	// RequestGenerator parameterisedPageByPageKeyReqGen =
	// getQueryRequest(APINAME.PARAMETERIZEDPAGEKEY, pageKkey);
	// String parameterisedPageByPageKeyResponse =
	// parameterisedPageByPageKeyReqGen.respvalidate.returnresponseasstring();
	// System.out.println("\nPrinting SearchService parameterisedPageByPageKey
	// API response :\n\n"+parameterisedPageByPageKeyResponse+"\n");
	// log.info("\nPrinting SearchService parameterisedPageByPageKey API
	// response :\n\n"+parameterisedPageByPageKeyResponse+"\n");
	//
	// AssertJUnit.assertEquals("[SearchService parameterisedPageByPageKey API
	// is not working.]", 200,
	// parameterisedPageByPageKeyReqGen.response.getStatus());
	//
	// try {
	// String jsonschema = new
	// Toolbox().readFileAsString("Data/SchemaSet/JSON/searchservice-parameterisedpagebypagekey-schema.txt");
	// List<String> missingNodeList =
	// validateServiceResponseNodesUsingSchemaValidator(parameterisedPageByPageKeyResponse,
	// jsonschema);
	// AssertJUnit.assertTrue(missingNodeList+" nodes are missing in
	// SearchService parameterisedPageByPageKey API response",
	// CollectionUtils.isEmpty(missingNodeList));
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
	//
	// @Test(groups = { "SchemaValidation" }, dataProvider =
	// "SearchServiceDP_parameterizedPageById_verifyResponseDataNodesUsingSchemaValidations")
	// public void
	// SearchService_parameterizedPageById_verifyResponseDataNodesUsingSchemaValidations(String
	// pageId, String statusCode, String successMessage,
	// String successStatus)
	// {
	// RequestGenerator parameterizedPageByIdReqGen =
	// getQueryRequest(APINAME.PARAMETERIZEDPAGE, pageId);
	// String parameterisedPageByPageKeyResponse =
	// parameterizedPageByIdReqGen.respvalidate.returnresponseasstring();
	// System.out.println("\nPrinting SearchService parameterizedPageById API
	// response :\n\n"+parameterisedPageByPageKeyResponse+"\n");
	// log.info("\nPrinting SearchService parameterizedPageById API response
	// :\n\n"+parameterisedPageByPageKeyResponse+"\n");
	//
	// AssertJUnit.assertEquals("[SearchService parameterizedPageById API is not
	// working.]", 200, parameterizedPageByIdReqGen.response.getStatus());
	//
	// try {
	// String jsonschema = new
	// Toolbox().readFileAsString("Data/SchemaSet/JSON/searchservice-parameterizedpagebyid-schema.txt");
	// List<String> missingNodeList =
	// validateServiceResponseNodesUsingSchemaValidator(parameterisedPageByPageKeyResponse,
	// jsonschema);
	// AssertJUnit.assertTrue(missingNodeList+" nodes are missing in
	// SearchService parameterizedPageById API response",
	// CollectionUtils.isEmpty(missingNodeList));
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
	//
	// @Test(groups = { "SchemaValidation" }, dataProvider =
	// "SearchServiceDP_curatedSearchListByParamPageKey_verifyResponseDataNodesUsingSchemaValidations")
	// public void
	// SearchService_curatedSearchListByParamPageKey_verifyResponseDataNodesUsingSchemaValidations(String
	// pageKey, String statusCode, String successMessage,
	// String successStatus, String itemCount)
	// {
	// RequestGenerator curatedSearchListByParamPageKeyReqGen =
	// getQueryRequest(APINAME.CURATEDSEARCHLISTBYPARAMPAGEKEY, pageKey);
	// String curatedSearchListByParamPageKeyResponse =
	// curatedSearchListByParamPageKeyReqGen.respvalidate.returnresponseasstring();
	// System.out.println("\nPrinting SearchService
	// curatedSearchListByParamPageKey API response
	// :\n\n"+curatedSearchListByParamPageKeyResponse+"\n");
	// log.info("\nPrinting SearchService curatedSearchListByParamPageKey API
	// response :\n\n"+curatedSearchListByParamPageKeyResponse+"\n");
	//
	// AssertJUnit.assertEquals("[SearchService curatedSearchListByParamPageKey
	// API is not working.]", 200,
	// curatedSearchListByParamPageKeyReqGen.response.getStatus());
	//
	// try {
	// String jsonschema = new
	// Toolbox().readFileAsString("Data/SchemaSet/JSON/searchservice-curatedsearchlistbyparampagekey-schema.txt");
	// List<String> missingNodeList =
	// validateServiceResponseNodesUsingSchemaValidator(curatedSearchListByParamPageKeyResponse,
	// jsonschema);
	// AssertJUnit.assertTrue(missingNodeList+" nodes are missing in
	// SearchService curatedSearchListByParamPageKey API response",
	// CollectionUtils.isEmpty(missingNodeList));
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
	//
	// @Test(groups = { "SchemaValidation" }, dataProvider =
	// "SearchServiceDP_curatedSearchListByParamPageId_verifyResponseDataNodesUsingSchemaValidations")
	// public void
	// SearchService_curatedSearchListByParamPageId_verifyResponseDataNodesUsingSchemaValidations(String
	// paramPageId, String statusCode, String successMessage,
	// String successStatus)
	// {
	// RequestGenerator curatedSearchListByParamPageIdReqGen =
	// getQueryRequest(APINAME.CURATEDSEARCHLISTBYPARAMPAGEID, paramPageId);
	// String curatedSearchListByParamPageIdResponse =
	// curatedSearchListByParamPageIdReqGen.respvalidate.returnresponseasstring();
	// System.out.println("\nPrinting SearchService
	// curatedSearchListByParamPageId API response
	// :\n\n"+curatedSearchListByParamPageIdResponse+"\n");
	// log.info("\nPrinting SearchService curatedSearchListByParamPageId API
	// response :\n\n"+curatedSearchListByParamPageIdResponse+"\n");
	//
	// AssertJUnit.assertEquals("[SearchService curatedSearchListByParamPageId
	// API is not working.]", 200,
	// curatedSearchListByParamPageIdReqGen.response.getStatus());
	//
	// try {
	// String jsonschema = new
	// Toolbox().readFileAsString("Data/SchemaSet/JSON/searchservice-curatedsearchlistbyparampageid-schema.txt");
	// List<String> missingNodeList =
	// validateServiceResponseNodesUsingSchemaValidator(curatedSearchListByParamPageIdResponse,
	// jsonschema);
	// AssertJUnit.assertTrue(missingNodeList+" nodes are missing in
	// SearchService curatedSearchListByParamPageId API response",
	// CollectionUtils.isEmpty(missingNodeList));
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
	//
	// @Test(groups = { "SchemaValidation" }, dataProvider =
	// "SearchServiceDP_getQueryWithtReturnDoc_verifyResponseDataNodesUsingSchemaValidations")
	// public void
	// SearchService_getQueryWithTrueReturnDoc_verifyResponseDataNodesUsingSchemaValidations(String
	// query, String rows, String returnDocs, String isFacet)
	// {
	// RequestGenerator getQueryWithtReturnDocReqGen =
	// getRequest(APINAME.GETQUERY, query, rows, returnDocs, isFacet);
	// String getQueryWithtReturnDocResponse =
	// getQueryWithtReturnDocReqGen.respvalidate.returnresponseasstring();
	// System.out.println("\nPrinting SearchService getQueryWithtReturnDoc API
	// response :\n\n"+getQueryWithtReturnDocResponse+"\n");
	// log.info("\nPrinting SearchService getQueryWithtReturnDoc API response
	// :\n\n"+getQueryWithtReturnDocResponse+"\n");
	//
	// AssertJUnit.assertEquals("[SearchService getQueryWithtReturnDoc API is
	// not working.]", 200, getQueryWithtReturnDocReqGen.response.getStatus());
	//
	// try {
	// String jsonschema = new
	// Toolbox().readFileAsString("Data/SchemaSet/JSON/searchservice-getquerywithtreturndoc-schema.txt");
	// List<String> missingNodeList =
	// validateServiceResponseNodesUsingSchemaValidator(getQueryWithtReturnDocResponse,
	// jsonschema);
	// AssertJUnit.assertTrue(missingNodeList+" nodes are missing in
	// SearchService getQueryWithtReturnDoc API response",
	// CollectionUtils.isEmpty(missingNodeList));
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
	//
	// @Test(groups = { "SchemaValidation" }, dataProvider =
	// "SearchServiceDP_getQueryWithfReturnDoc_verifyResponseDataNodesUsingSchemaValidations")
	// public void
	// SearchService_getQueryWithFalseReturnDoc_verifyResponseDataNodesUsingSchemaValidations(String
	// query, String rows, String returnDocs, String isFacet)
	// {
	// RequestGenerator getQueryWithfReturnDocReqGen =
	// getRequest(APINAME.GETQUERY, query, rows, returnDocs, isFacet);
	// String getQueryWithfReturnDocResponse =
	// getQueryWithfReturnDocReqGen.respvalidate.returnresponseasstring();
	// System.out.println("\nPrinting SearchService getQueryWithfReturnDoc API
	// response :\n\n"+getQueryWithfReturnDocResponse+"\n");
	// log.info("\nPrinting SearchService getQueryWithfReturnDoc API response
	// :\n\n"+getQueryWithfReturnDocResponse+"\n");
	//
	// AssertJUnit.assertEquals("[SearchService getQueryWithfReturnDoc API is
	// not working.]", 200, getQueryWithfReturnDocReqGen.response.getStatus());
	//
	// try {
	// String jsonschema = new
	// Toolbox().readFileAsString("Data/SchemaSet/JSON/searchservice-getquerywithfreturndoc-schema.txt");
	// List<String> missingNodeList =
	// validateServiceResponseNodesUsingSchemaValidator(getQueryWithfReturnDocResponse,
	// jsonschema);
	// AssertJUnit.assertTrue(missingNodeList+" nodes are missing in
	// SearchService getQueryWithfReturnDoc API response",
	// CollectionUtils.isEmpty(missingNodeList));
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
	//
	// @Test(groups = { "SchemaValidation" }, dataProvider =
	// "SearchServiceDP_invalidateCache_verifyResponseDataNodesUsingSchemaValidations")
	// public void
	// SearchService_invalidateCache_verifyResponseDataNodesUsingSchemaValidations(String
	// BrandName, String dataSuccess, String dataMessage)
	// {
	// RequestGenerator invalidateCacheReqGen =
	// getQueryRequest(APINAME.INVALIDATECACHE, BrandName);
	// String invalidateCacheResponse =
	// invalidateCacheReqGen.respvalidate.returnresponseasstring();
	// System.out.println("\nPrinting SearchService invalidateCache API response
	// :\n\n"+invalidateCacheResponse+"\n");
	// log.info("\nPrinting SearchService invalidateCache API response
	// :\n\n"+invalidateCacheResponse+"\n");
	//
	// AssertJUnit.assertEquals("[SearchService invalidateCache API is not
	// working.]", 200, invalidateCacheReqGen.response.getStatus());
	//
	// try {
	// String jsonschema = new
	// Toolbox().readFileAsString("Data/SchemaSet/JSON/searchservice-invalidatecache-schema.txt");
	// List<String> missingNodeList =
	// validateServiceResponseNodesUsingSchemaValidator(invalidateCacheResponse,
	// jsonschema);
	// AssertJUnit.assertTrue(missingNodeList+" nodes are missing in
	// SearchService invalidateCache API response",
	// CollectionUtils.isEmpty(missingNodeList));
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
	//
	// @Test(groups = { "SchemaValidation" }, dataProvider =
	// "SearchServiceDP_reCache_verifyResponseDataNodesUsingSchemaValidations")
	// public void
	// SearchService_reCache_verifyResponseDataNodesUsingSchemaValidations(String
	// BrandName, String dataSuccess, String dataMessage)
	// {
	// RequestGenerator reCacheReqGen = getQueryRequest(APINAME.RECACHE,
	// BrandName);
	// String reCacheResponse =
	// reCacheReqGen.respvalidate.returnresponseasstring();
	// System.out.println("\nPrinting SearchService reCache API response
	// :\n\n"+reCacheResponse+"\n");
	// log.info("\nPrinting SearchService reCache API response
	// :\n\n"+reCacheResponse+"\n");
	//
	// AssertJUnit.assertEquals("[SearchService reCache API is not working.]",
	// 200, reCacheReqGen.response.getStatus());
	//
	// try {
	// String jsonschema = new
	// Toolbox().readFileAsString("Data/SchemaSet/JSON/searchservice-recache-schema.txt");
	// List<String> missingNodeList =
	// validateServiceResponseNodesUsingSchemaValidator(reCacheResponse,
	// jsonschema);
	// AssertJUnit.assertTrue(missingNodeList+" nodes are missing in
	// SearchService reCache API response",
	// CollectionUtils.isEmpty(missingNodeList));
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
	//
	// @Test(groups = { "SchemaValidation" })
	// public void
	// SearchService_invalidateCacheAll_verifyResponseDataNodesUsingSchemaValidations()
	// {
	// RequestGenerator invalidateCacheAllReqGen =
	// getRequest(APINAME.INVALIDATECACHEALL);
	// String invalidateCacheAllResponse =
	// invalidateCacheAllReqGen.respvalidate.returnresponseasstring();
	// System.out.println("\nPrinting SearchService invalidateCacheAll API
	// response :\n\n"+invalidateCacheAllResponse+"\n");
	// log.info("\nPrinting SearchService invalidateCacheAll API response
	// :\n\n"+invalidateCacheAllResponse+"\n");
	//
	// AssertJUnit.assertEquals("[SearchService invalidateCacheAll API is not
	// working.]", 200, invalidateCacheAllReqGen.response.getStatus());
	//
	// try {
	// String jsonschema = new
	// Toolbox().readFileAsString("Data/SchemaSet/JSON/searchservice-invalidatecacheall-schema.txt");
	// List<String> missingNodeList =
	// validateServiceResponseNodesUsingSchemaValidator(invalidateCacheAllResponse,
	// jsonschema);
	// AssertJUnit.assertTrue(missingNodeList+" nodes are missing in
	// SearchService invalidateCacheAll API response",
	// CollectionUtils.isEmpty(missingNodeList));
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }

	/*
	 * Test-cases 5.1: GET SERVICE Trends getting success response - default
	 * service
	 * 
	 * @author jitender.kumar1
	 */

	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression",
			"SearchRegression" }, dataProvider = "SearchService_trends")
	public void SearchService_trendsApi(String limit) {

		long startTime = Calendar.getInstance().getTimeInMillis();

		MyntraService service = Myntra.getService(ServiceType.PORTAL_SEARCHSERVICE, APINAME.TRENDS,
				init.Configurations);
		service.URL = apiUtil.prepareparameterizedURL(service.URL, new String[] { limit });
		RequestGenerator req = new RequestGenerator(service);
		System.out.println(service.URL);
		log.info(service.URL);
		String response = req.returnresponseasstring();

		System.out.println("Trends Response" + response);
		AssertJUnit.assertEquals("SearchService_autoSuggest is not working", 200, req.response.getStatus());

		Object trendsResponse = JsonPath.read(response, "$.data.trendingTerms");

		System.out.println("OBJECT PRINT ------->>>" + trendsResponse);
		String responseProductsStr = trendsResponse.toString().replace("[", "").replace("]", "").trim();
		String[] arrTrends = responseProductsStr.split(",");

		for (int i = 0; i < arrTrends.length; i++) {
			System.out.println("List of Trending items=====" + arrTrends[i]);
		}

		String lastItemTrending = arrTrends[arrTrends.length - 1];

		System.out.println(limit + "th element is ======>" + lastItemTrending);

		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;
		System.out.println("\nTime taken to execute - TestCase - SearchService_trendsWithSuccessMessage : " + timeTaken
				+ " seconds\n");
		log.info("\nTime taken to execute - TestCase - SearchService_trendsWithSuccessMessage : " + timeTaken
				+ " seconds\n");
	}

	/*
	 * Test-cases 5.2: GET SERVICE Trends getting success response
	 * 
	 * @author jitender.kumar1
	 */

	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression",
			"SearchRegression" }, dataProvider = "SearchService_trendsNegative")
	public void SearchService_trendsNegative(String limit) {

		long startTime = Calendar.getInstance().getTimeInMillis();

		MyntraService service = Myntra.getService(ServiceType.PORTAL_SEARCHSERVICE, APINAME.TRENDS,
				init.Configurations);
		service.URL = apiUtil.prepareparameterizedURL(service.URL, new String[] { limit });
		RequestGenerator req = new RequestGenerator(service);
		System.out.println(service.URL);
		log.info(service.URL);

		AssertJUnit.assertEquals("SearchService_autoSuggest is not working", 404, req.response.getStatus());

		String response = req.returnresponseasstring();

		System.out.println("Trends Response" + response);

		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;
		System.out.println("\nTime taken to execute - TestCase - SearchService_trendsWithSuccessMessage : " + timeTaken
				+ " seconds\n");
		log.info("\nTime taken to execute - TestCase - SearchService_trendsWithSuccessMessage : " + timeTaken
				+ " seconds\n");
	}

	/*
	 * Test-cases for Pre- Order 6.1: POST SERVICE getQuery getting success
	 * response - default service
	 * 
	 * @author jitender.kumar1
	 */
	@Test(groups = { "DevAsk" }, dataProvider = "SearchService_preOrder")
	public void SearchService_getQueryPreOrderWithSuccess(String query, String return_docs, String is_facet,
			String login) {

		long startTime = Calendar.getInstance().getTimeInMillis();

		MyntraService service = Myntra.getService(ServiceType.PORTAL_SEARCHSERVICE, APINAME.GETQUERY,
				init.Configurations, new String[] { query, return_docs, is_facet, login });
		RequestGenerator req = new RequestGenerator(service);
		System.out.println(service.URL);
		log.info(service.URL);
		System.out.println(service.Payload);
		String response = req.returnresponseasstring();

		AssertJUnit.assertEquals("getQueryWithValidInputs is not working", 200, req.response.getStatus());

		String preOrderTagResponse = JsonPath.read(response, "$.data.queryResult.response1.products..advanceOrderTag")
				.toString();
		String preOrderTypeResponse = JsonPath.read(response, "$.data.queryResult.response1.products..advanceOrderType")
				.toString();
		String preOrderTag = preOrderTagResponse.toString().replace("[", "").replace("]", "").replace("\"", " ").trim();
		System.out.println("Response-->" + preOrderTag);
		String preOrderType = preOrderTypeResponse.toString().replace("[", "").replace("]", "").replace("\"", " ")
				.trim();
		System.out.println("Response-->" + preOrderType);

		AssertJUnit.assertEquals("NOT IN PREORDER Tag-------->", "Exclusive Pre-Order in Myntra", preOrderTag);
		AssertJUnit.assertEquals("NOT IN PREORDER Type-------->", "preorder", preOrderType);

		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;
		System.out.println("\nTime taken to execute - TestCase - SearchService_getQueryWithSuccessMessage : "
				+ timeTaken + " seconds\n");
		log.info("\nTime taken to execute - TestCase - SearchService_getQueryWithSuccessMessage : " + timeTaken
				+ " seconds\n");
	}

	/*
	 * Test-cases for Pre- Order 6.2: GET SERVICE getQuery getting success
	 * response - default service
	 * 
	 * @author jitender.kumar1
	 */

	@Test(groups = { "DevAsk" }, dataProvider = "SearchService_getQueryGetWithPreOrderDP")
	public void SearchService_getQueryGetPreOrderWithSuccess(String query, String return_docs, String is_facet,
			String login, String rows) {

		long startTime = Calendar.getInstance().getTimeInMillis();

		MyntraService service = Myntra.getService(ServiceType.PORTAL_SEARCHSERVICE, APINAME.GETQUERYGET,
				init.Configurations);
		service.URL = apiUtil.prepareparameterizedURL(service.URL,
				new String[] { query, return_docs, is_facet, login, rows });
		RequestGenerator req = new RequestGenerator(service);
		System.out.println(service.URL);
		log.info(service.URL);
		String response = req.returnresponseasstring();

		AssertJUnit.assertEquals("SearchService_getQueryGetWithPreOrder is not working", 200, req.response.getStatus());

		String preOrderTagResponse = JsonPath.read(response, "$.data.queryResult.response1.products..advanceOrderTag")
				.toString();
		String preOrderTypeResponse = JsonPath.read(response, "$.data.queryResult.response1.products..advanceOrderType")
				.toString();
		String preOrderTag = preOrderTagResponse.toString().replace("[", "").replace("]", "").replace("\"", " ").trim();
		System.out.println("Response-->" + preOrderTag);
		String preOrderType = preOrderTypeResponse.toString().replace("[", "").replace("]", "").replace("\"", " ")
				.trim();
		System.out.println("Response-->" + preOrderType);

		AssertJUnit.assertEquals("NOT IN PREORDER Tag-------->", "Exclusive Pre-Order in Myntra", preOrderTag);
		AssertJUnit.assertEquals("NOT IN PREORDER Type-------->", "preorder", preOrderType);

		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;
		System.out.println("\nTime taken to execute - TestCase - SearchService_getQueryWithSuccessMessage : "
				+ timeTaken + " seconds\n");
		log.info("\nTime taken to execute - TestCase - SearchService_getQueryWithSuccessMessage : " + timeTaken
				+ " seconds\n");
	}

	/*
	 * Test-cases for Pre-Order 6.3: GET SERVICE getQuery getting success
	 * response - default service
	 * 
	 * @author jitender.kumar1
	 */
	@Test(groups = { "DevAsk" }, dataProvider = "SearchService_getQueryGetWithPreOrderDP")
	public void SearchService_getQueryGETWithPreOrderWithCMS(String query, String return_docs, String is_facet,
			String login, String rows) {

		long startTime = Calendar.getInstance().getTimeInMillis();

		MyntraService service = Myntra.getService(ServiceType.PORTAL_SEARCHSERVICE, APINAME.GETQUERYGET,
				init.Configurations);
		service.URL = apiUtil.prepareparameterizedURL(service.URL,
				new String[] { query, return_docs, is_facet, login, rows });
		RequestGenerator req = new RequestGenerator(service);
		System.out.println(service.URL);
		log.info(service.URL);
		String response = req.returnresponseasstring();

		AssertJUnit.assertEquals("SearchService_getQueryGetWithPreOrder is not working", 200, req.response.getStatus());

		String preOrderTagResponse = JsonPath.read(response, "$.data.queryResult.response1.products..advanceOrderTag")
				.toString();
		String preOrderTypeResponse = JsonPath.read(response, "$.data.queryResult.response1.products..advanceOrderType")
				.toString();
		String preOrderTag = preOrderTagResponse.toString().replace("[", "").replace("]", "").replace("\"", " ").trim();
		System.out.println("Response-->" + preOrderTag);
		String preOrderType = preOrderTypeResponse.toString().replace("[", "").replace("]", "").replace("\"", " ")
				.trim();
		System.out.println("Response-->" + preOrderType);

		AssertJUnit.assertEquals("NOT IN PREORDER Tag-------->", "Exclusive Pre-Order in Myntra", preOrderTag);
		AssertJUnit.assertEquals("NOT IN PREORDER Type-------->", "preorder", preOrderType);

		RequestGenerator cmsRequest = SearchServiceHelper.cmsPreOrder(query);
		String cmsResponse = cmsRequest.returnresponseasstring();
		System.out.println("CMS--->" + cmsResponse);
		Object responseCMS = JsonPath.read(cmsResponse, "$.data..advancedOrderOptions");
		String preOrderCMS = responseCMS.toString().replace("[", "").replace("]", "").trim();
		System.out.println(preOrderCMS);

		Object preOrderTagCMS = JsonPath.read(cmsResponse, "$.data..advancedOrderOptions.preOrder.productImageTag");

		String preOrderTagCMSString = preOrderTagCMS.toString().replace("[", "").replace("]", "").replace("\"", "")
				.trim();
		System.out.println("response--->" + preOrderTagCMSString);

		AssertJUnit.assertEquals("NOT IN CMS -------->", "Pre-Order", preOrderTagCMSString);

		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;
		System.out.println("\nTime taken to execute - TestCase - SearchService_getQueryWithSuccessMessage : "
				+ timeTaken + " seconds\n");
		log.info("\nTime taken to execute - TestCase - SearchService_getQueryWithSuccessMessage : " + timeTaken
				+ " seconds\n");
	}

	/*
	 * Test-cases for Pre-Order 6.4: POST SERVICE with cms response getQuery
	 * getting success response - default service
	 * 
	 * @author jitender.kumar1
	 */
	@Test(groups = { "DevAsk" }, dataProvider = "SearchService_preOrderWithCMS")
	public void SearchService_getQueryWithPreOrderWithCMS(String query, String return_docs, String is_facet,
			String login) {

		long startTime = Calendar.getInstance().getTimeInMillis();

		MyntraService service = Myntra.getService(ServiceType.PORTAL_SEARCHSERVICE, APINAME.GETQUERY,
				init.Configurations, new String[] { query, return_docs, is_facet, login });
		RequestGenerator req = new RequestGenerator(service);
		System.out.println(service.URL);
		log.info(service.URL);
		System.out.println(service.Payload);
		String response = req.returnresponseasstring();

		AssertJUnit.assertEquals("getQueryWithPreOrder is not working", 200, req.response.getStatus());

		String preOrderTagResponse = JsonPath.read(response, "$.data.queryResult.response1.products..advanceOrderTag")
				.toString();
		String preOrderTypeResponse = JsonPath.read(response, "$.data.queryResult.response1.products..advanceOrderType")
				.toString();
		String preOrderTag = preOrderTagResponse.toString().replace("[", "").replace("]", "").replace("\"", " ").trim();
		System.out.println("Response-->" + preOrderTag);
		String preOrderType = preOrderTypeResponse.toString().replace("[", "").replace("]", "").replace("\"", " ")
				.trim();
		System.out.println("Response-->" + preOrderType);

		AssertJUnit.assertEquals("NOT IN PREORDER Tag-------->", "Exclusive Pre-Order in Myntra", preOrderTag);
		AssertJUnit.assertEquals("NOT IN PREORDER Type-------->", "preorder", preOrderType);

		RequestGenerator cmsRequest = SearchServiceHelper.cmsPreOrder(query);
		String cmsResponse = cmsRequest.returnresponseasstring();
		System.out.println("CMS--->" + cmsResponse);
		Object responseCMS = JsonPath.read(cmsResponse, "$.data..advancedOrderOptions");
		String preOrderCMS = responseCMS.toString().replace("[", "").replace("]", "").trim();
		System.out.println(preOrderCMS);

		Object preOrderTagCMS = JsonPath.read(cmsResponse, "$.data..advancedOrderOptions.preOrder.productImageTag");

		String preOrderTagCMSString = preOrderTagCMS.toString().replace("[", "").replace("]", "").replace("\"", "")
				.trim();
		System.out.println("response--->" + preOrderTagCMSString);

		AssertJUnit.assertEquals("NOT IN CMS -------->", "Pre-Order", preOrderTagCMSString);

		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;
		System.out.println("\nTime taken to execute - TestCase - SearchService_getQueryWithSuccessMessage : "
				+ timeTaken + " seconds\n");
		log.info("\nTime taken to execute - TestCase - SearchService_getQueryWithSuccessMessage : " + timeTaken
				+ " seconds\n");
	}

	/*
	 * Test-cases for Pre-Order 6.5: POST SERVICE search with style pdp getQuery
	 * getting success response - default service
	 * 
	 * @author jitender.kumar1
	 */
	@Test(groups = { "DevAsk" }, dataProvider = "SearchService_preOrderWithPdpStyle")
	public void SearchService_getQueryWithPreOrderWithPdp(String query, String return_docs, String is_facet,
			String login) {

		long startTime = Calendar.getInstance().getTimeInMillis();

		MyntraService service = Myntra.getService(ServiceType.PORTAL_SEARCHSERVICE, APINAME.GETQUERY,
				init.Configurations, new String[] { query, return_docs, is_facet, login });
		RequestGenerator req = new RequestGenerator(service);
		System.out.println(service.URL);
		log.info(service.URL);
		System.out.println(service.Payload);
		String response = req.returnresponseasstring();

		AssertJUnit.assertEquals("getQueryWithPreOrder is not working", 200, req.response.getStatus());

		String preOrderTagResponse = JsonPath.read(response, "$.data.queryResult.response1.products..advanceOrderTag")
				.toString();
		String preOrderTypeResponse = JsonPath.read(response, "$.data.queryResult.response1.products..advanceOrderType")
				.toString();
		String preOrderTag = preOrderTagResponse.toString().replace("[", "").replace("]", "").replace("\"", " ").trim();
		System.out.println("Response-->" + preOrderTag);
		String preOrderType = preOrderTypeResponse.toString().replace("[", "").replace("]", "").replace("\"", " ")
				.trim();
		System.out.println("Response-->" + preOrderType);

		AssertJUnit.assertEquals("NOT IN PREORDER Tag-------->", "Exclusive Pre-Order in Myntra", preOrderTag);
		AssertJUnit.assertEquals("NOT IN PREORDER Type-------->", "preorder", preOrderType);

		RequestGenerator pdpRequest = SearchServiceHelper.pdpStyleAPI(query);
		String pdpResponse = pdpRequest.returnresponseasstring();
		System.out.println("PDP--->" + pdpResponse);
		Object responseCMS = JsonPath.read(pdpResponse, "$.data..advancedOrderOptions");
		String preOrderCMS = responseCMS.toString().replace("[", "").replace("]", "").trim();
		System.out.println(preOrderCMS);

		Object preOrderTagPDP = JsonPath.read(pdpResponse, "$.data..advanceOrderOptions.preOrder.productImageTag");

		String preOrderTagPDPString = preOrderTagPDP.toString().replace("[", "").replace("]", "").replace("\"", "")
				.trim();
		System.out.println("response--->" + preOrderTagPDPString);

		AssertJUnit.assertEquals("NOT IN Style Response -------->", "Pre-Order", preOrderTagPDPString);

		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;
		System.out.println("\nTime taken to execute - TestCase - SearchService_getQueryWithSuccessMessage : "
				+ timeTaken + " seconds\n");
		log.info("\nTime taken to execute - TestCase - SearchService_getQueryWithSuccessMessage : " + timeTaken
				+ " seconds\n");
	}

	/*
	 * Test-cases for Pre-Order 6.6: GET SERVICE search with style pdp
	 * getQueryGet getting success response - default service
	 * 
	 * @author jitender.kumar1
	 */
	@Test(groups = { "DevAsk" }, dataProvider = "SearchService_getQueryGetWithPreOrderStyleDP")
	public void SearchService_getQueryGETWithPreOrderWithPdp(String query, String return_docs, String is_facet,
			String login, String rows) {

		long startTime = Calendar.getInstance().getTimeInMillis();

		MyntraService service = Myntra.getService(ServiceType.PORTAL_SEARCHSERVICE, APINAME.GETQUERYGET,
				init.Configurations);
		service.URL = apiUtil.prepareparameterizedURL(service.URL,
				new String[] { query, return_docs, is_facet, login, rows });
		RequestGenerator req = new RequestGenerator(service);
		System.out.println(service.URL);
		log.info(service.URL);
		String response = req.returnresponseasstring();

		AssertJUnit.assertEquals("SearchService_getQueryGetWithPreOrder is not working", 200, req.response.getStatus());

		String preOrderTagResponse = JsonPath.read(response, "$.data.queryResult.response1.products..advanceOrderTag")
				.toString();
		String preOrderTypeResponse = JsonPath.read(response, "$.data.queryResult.response1.products..advanceOrderType")
				.toString();
		String preOrderTag = preOrderTagResponse.toString().replace("[", "").replace("]", "").replace("\"", " ").trim();
		System.out.println("Response-->" + preOrderTag);
		String preOrderType = preOrderTypeResponse.toString().replace("[", "").replace("]", "").replace("\"", " ")
				.trim();
		System.out.println("Response-->" + preOrderType);

		AssertJUnit.assertEquals("NOT IN PREORDER Tag-------->", "Exclusive Pre-Order in Myntra", preOrderTag);
		AssertJUnit.assertEquals("NOT IN PREORDER Type-------->", "preorder", preOrderType);

		RequestGenerator pdpRequest = SearchServiceHelper.pdpStyleAPI(query);
		String pdpResponse = pdpRequest.returnresponseasstring();
		System.out.println("PDP--->" + pdpResponse);
		Object responseCMS = JsonPath.read(pdpResponse, "$.data..advancedOrderOptions");
		String preOrderCMS = responseCMS.toString().replace("[", "").replace("]", "").trim();
		System.out.println(preOrderCMS);

		Object preOrderTagPDP = JsonPath.read(pdpResponse, "$.data..advanceOrderOptions.preOrder.productImageTag");

		String preOrderTagPDPString = preOrderTagPDP.toString().replace("[", "").replace("]", "").replace("\"", "")
				.trim();
		System.out.println("response--->" + preOrderTagPDPString);

		AssertJUnit.assertEquals("NOT IN Style Response -------->", "Pre-Order", preOrderTagPDPString);

		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;
		System.out.println("\nTime taken to execute - TestCase - SearchService_getQueryWithSuccessMessage : "
				+ timeTaken + " seconds\n");
		log.info("\nTime taken to execute - TestCase - SearchService_getQueryWithSuccessMessage : " + timeTaken
				+ " seconds\n");
	}

	/*
	 * Test-cases for Pre-Order 6.7 POST SERVICE search with cms active=false
	 * getQueryGet getting success response - default service
	 * 
	 * @author jitender.kumar1
	 */
	@Test(groups = { "DevAsk" }, dataProvider = "SearchService_preOrderWithCMSActiveisFalse")
	public void SearchService_getQueryGETWithPreOrderWithCMSisActivefalse(String query, String return_docs,
			String is_facet, String login) {

		long startTime = Calendar.getInstance().getTimeInMillis();

		RequestGenerator cmsRequest = SearchServiceHelper.cmsPreOrder(query);
		String cmsResponse = cmsRequest.returnresponseasstring();
		System.out.println("CMS--->" + cmsResponse);
		Object responseCMS = JsonPath.read(cmsResponse, "$.data..advancedOrderOptions");
		String preOrderCMS = responseCMS.toString().replace("[", "").replace("]", "").trim();
		System.out.println(preOrderCMS);

		Object preOrderTagCMS = JsonPath.read(cmsResponse, "$.data..advancedOrderOptions.preOrder.active");

		String preOrderTagCMSString = preOrderTagCMS.toString().replace("[", "").replace("]", "").replace("\"", "")
				.trim();
		System.out.println("response--->" + preOrderTagCMSString);

		AssertJUnit.assertEquals("Preorder response IN CMS for active is true-------->", "false", preOrderTagCMSString);

		MyntraService service = Myntra.getService(ServiceType.PORTAL_SEARCHSERVICE, APINAME.GETQUERY,
				init.Configurations, new String[] { query, return_docs, is_facet, login });
		RequestGenerator req = new RequestGenerator(service);
		System.out.println(service.URL);
		log.info(service.URL);
		System.out.println(service.Payload);
		String response = req.returnresponseasstring();

		AssertJUnit.assertEquals("getQueryWithPreOrder is not working", 200, req.response.getStatus());

		String preOrderTagResponse = JsonPath.read(response, "$.data.queryResult.response1.products..advanceOrderTag")
				.toString();
		String preOrderTypeResponse = JsonPath.read(response, "$.data.queryResult.response1.products..advanceOrderType")
				.toString();
		String preOrderTag = preOrderTagResponse.toString().replace("[", "").replace("]", "").replace("\"", " ").trim();
		System.out.println("Response-->" + preOrderTag);
		String preOrderType = preOrderTypeResponse.toString().replace("[", "").replace("]", "").replace("\"", " ")
				.trim();
		System.out.println("Response-->" + preOrderType);
		System.out.println("Advanced Order Node is not present");
		AssertJUnit.assertEquals("PREORDER Tag is not present-------->", "Exclusive Pre-Order in Myntra", preOrderTag);
		AssertJUnit.assertEquals("PREORDER Type is not present-------->", "preorder", preOrderType);

		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;
		System.out.println("\nTime taken to execute - TestCase - SearchService_getQueryWithSuccessMessage : "
				+ timeTaken + " seconds\n");
		log.info("\nTime taken to execute - TestCase - SearchService_getQueryWithSuccessMessage : " + timeTaken
				+ " seconds\n");
	}

	/*
	 * Test-cases for Pre-Order 6.8 GET SERVICE search with getQueryGet getting
	 * success response - default service
	 * 
	 * @author jitender.kumar1
	 */
	@Test(groups = { "DevAsk" }, dataProvider = "SearchService_preOrderCMSActiveisTrue")
	public void SearchService_getQueryGETWithPreOrderisActiveTrue(String query, String return_docs, String is_facet,
			String login, String rows) {

		long startTime = Calendar.getInstance().getTimeInMillis();

		RequestGenerator cmsRequest = SearchServiceHelper.cmsPreOrder(query);
		String cmsResponse = cmsRequest.returnresponseasstring();
		System.out.println("CMS Response for Preorder--->" + cmsResponse);
		Object responseCMS = JsonPath.read(cmsResponse, "$.data..advancedOrderOptions");
		String preOrderCMS = responseCMS.toString().replace("[", "").replace("]", "").trim();
		System.out.println(preOrderCMS);

		Object preOrderTagCMS = JsonPath.read(cmsResponse, "$.data..advancedOrderOptions.preOrder.active");

		String preOrderTagCMSString = preOrderTagCMS.toString().replace("[", "").replace("]", "").replace("\"", "")
				.trim();
		System.out.println("response--->" + preOrderTagCMSString);

		AssertJUnit.assertEquals("SearchService_getQueryGetWithPreorder is not working", "true", preOrderTagCMSString);

		MyntraService service = Myntra.getService(ServiceType.PORTAL_SEARCHSERVICE, APINAME.GETQUERYGET,
				init.Configurations);
		service.URL = apiUtil.prepareparameterizedURL(service.URL,
				new String[] { query, return_docs, is_facet, login, rows });
		RequestGenerator req = new RequestGenerator(service);
		System.out.println(service.URL);
		log.info(service.URL);
		String response = req.returnresponseasstring();

		AssertJUnit.assertEquals("SearchService_getQueryGetWithPreOrder is not working", 200, req.response.getStatus());

		String preOrderTagResponse = JsonPath.read(response, "$.data.queryResult.response1.products..advanceOrderTag")
				.toString();
		String preOrderTypeResponse = JsonPath.read(response, "$.data.queryResult.response1.products..advanceOrderType")
				.toString();
		String preOrderTag = preOrderTagResponse.toString().replace("[", "").replace("]", "").replace("\"", " ").trim();
		System.out.println("Response-->" + preOrderTag);
		String preOrderType = preOrderTypeResponse.toString().replace("[", "").replace("]", "").replace("\"", " ")
				.trim();
		System.out.println("Response-->" + preOrderType);

		AssertJUnit.assertEquals("NOT IN PREORDER Tag-------->", "Exclusive Pre-Order in Myntra", preOrderTag);
		AssertJUnit.assertEquals("NOT IN PREORDER Type-------->", "preorder", preOrderType);

		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;
		System.out.println("\nTime taken to execute - TestCase - SearchService_getQueryWithSuccessMessage : "
				+ timeTaken + " seconds\n");
		log.info("\nTime taken to execute - TestCase - SearchService_getQueryWithSuccessMessage : " + timeTaken
				+ " seconds\n");
	}

	/*
	 * Test-cases for Pre-Order 6.9 GET SERVICE search with getQueryGet getting
	 * success response - default service
	 * 
	 * @author jitender.kumar1
	 */
	@Test(groups = { "DevAsk" }, dataProvider = "SearchService_preOrderCMSActiveisTrue")
	public void SearchService_getQueryGETWithPreOrderisActive(String query, String return_docs, String is_facet,
			String login, String rows) {

		long startTime = Calendar.getInstance().getTimeInMillis();

		RequestGenerator cmsRequest = SearchServiceHelper.cmsPreOrder(query);
		String cmsResponse = cmsRequest.returnresponseasstring();
		System.out.println("CMS--->" + cmsResponse);
		Object responseCMS = JsonPath.read(cmsResponse, "$.data..advancedOrderOptions");
		String preOrderCMS = responseCMS.toString().replace("[", "").replace("]", "").trim();
		System.out.println(preOrderCMS);

		Object preOrderTagCMS = JsonPath.read(cmsResponse, "$.data..advancedOrderOptions.preOrder.active");

		String preOrderTagCMSString = preOrderTagCMS.toString().replace("[", "").replace("]", "").replace("\"", "")
				.trim();
		System.out.println("response--->" + preOrderTagCMSString);

		AssertJUnit.assertEquals("SearchService_getQueryGetWithPreorder is not working", "true", preOrderTagCMSString);

		MyntraService service = Myntra.getService(ServiceType.PORTAL_SEARCHSERVICE, APINAME.GETQUERYGET,
				init.Configurations);
		service.URL = apiUtil.prepareparameterizedURL(service.URL,
				new String[] { query, return_docs, is_facet, login, rows });
		RequestGenerator req = new RequestGenerator(service);
		System.out.println(service.URL);
		log.info(service.URL);
		String response = req.returnresponseasstring();

		AssertJUnit.assertEquals("SearchService_getQueryGetWithPreOrder is not working", 200, req.response.getStatus());

		String preOrderTagResponse = JsonPath.read(response, "$.data.queryResult.response1.products..advanceOrderTag")
				.toString();
		String preOrderTypeResponse = JsonPath.read(response, "$.data.queryResult.response1.products..advanceOrderType")
				.toString();
		String preOrderTag = preOrderTagResponse.toString().replace("[", "").replace("]", "").replace("\"", " ").trim();
		System.out.println("Response-->" + preOrderTag);
		String preOrderType = preOrderTypeResponse.toString().replace("[", "").replace("]", "").replace("\"", " ")
				.trim();
		System.out.println("Response-->" + preOrderType);

		AssertJUnit.assertEquals("NOT IN PREORDER Tag-------->", "Exclusive Pre-Order in Myntra", preOrderTag);
		AssertJUnit.assertEquals("NOT IN PREORDER Type-------->", "preorder", preOrderType);

		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;
		System.out.println("\nTime taken to execute - TestCase - SearchService_getQueryWithSuccessMessage : "
				+ timeTaken + " seconds\n");
		log.info("\nTime taken to execute - TestCase - SearchService_getQueryWithSuccessMessage : " + timeTaken
				+ " seconds\n");
	}

	/*
	 * Test-cases for Pre-Order 6.10: GET SERVICE search with style pdp
	 * getQueryGet getting success response - default service
	 * 
	 * @author jitender.kumar1
	 */
	@Test(groups = { "DevAsk" }, dataProvider = "SearchService_preOrderWithPdpdata")
	public void SearchService_getQueryGETWithPreOrderWitPdp(String query) {

		long startTime = Calendar.getInstance().getTimeInMillis();

		RequestGenerator pdpRequest = SearchServiceHelper.pdpStyleAPI(query);
		String pdpResponse = pdpRequest.returnresponseasstring();
		System.out.println("PDP--->" + pdpResponse);
		Object responsePDP = JsonPath.read(pdpResponse, "$.data..advanceOrderOptions");
		String preOrderPDP = responsePDP.toString().replace("[", "").replace("]", "").trim();
		System.out.println(preOrderPDP);

		Object preOrderTagPDP = JsonPath.read(pdpResponse, "$.data..advanceOrderOptions.preOrder.productImageTag");

		String preOrderTagPDPString = preOrderTagPDP.toString().replace("[", "").replace("]", "").replace("\"", "")
				.trim();
		System.out.println("response--->" + preOrderTagPDPString);

		AssertJUnit.assertEquals("NOT IN Style Response -------->", "Pre-Order", preOrderTagPDPString);

		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;
		System.out.println("\nTime taken to execute - TestCase - SearchService_getQueryWithSuccessMessage : "
				+ timeTaken + " seconds\n");
		log.info("\nTime taken to execute - TestCase - SearchService_getQueryWithSuccessMessage : " + timeTaken
				+ " seconds\n");
	}

	/*
	 * Test-cases for Pre-Order 6.10: GET SERVICE search with style pdp
	 * getQueryGet getting success response - default service
	 * 
	 * @author jitender.kumar1
	 */
	@Test(groups = { "DevAsk" }, dataProvider = "SearchService_preOrderWithPdpandCMSdata")
	public void SearchService_PdpWithPreOrderCms(String query) {

		long startTime = Calendar.getInstance().getTimeInMillis();

		RequestGenerator pdpRequest = SearchServiceHelper.pdpStyleAPI(query);
		String pdpResponse = pdpRequest.returnresponseasstring();
		System.out.println("PDP--->" + pdpResponse);

		Object preOrderTagPDP = JsonPath.read(pdpResponse, "$.data..productTag");

		String preOrderProdcutTagPDPString = preOrderTagPDP.toString().replace("[", "").replace("]", "")
				.replace("\"", "").trim();
		System.out.println("response--->" + preOrderProdcutTagPDPString);

		RequestGenerator cmsRequest = SearchServiceHelper.cmsPreOrder(query);
		String cmsResponse = cmsRequest.returnresponseasstring();
		System.out.println("CMS--->" + cmsResponse);
		Object responseCMS = JsonPath.read(cmsResponse, "$.data..productTags");
		String preOrderCMS = responseCMS.toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		System.out.println(preOrderCMS);

		AssertJUnit.assertEquals("StyleService_PDPandCMSWithPreorder is not working", preOrderCMS,
				preOrderProdcutTagPDPString);

		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;
		System.out.println("\nTime taken to execute - TestCase - SearchService_getQueryWithSuccessMessage : "
				+ timeTaken + " seconds\n");
		log.info("\nTime taken to execute - TestCase - SearchService_getQueryWithSuccessMessage : " + timeTaken
				+ " seconds\n");
	}

	/*
	 * Test-cases for Pre-Order 6.11: IsActive=false in cms, then Preorder node
	 * is not available in pdp response getQueryGet getting success response -
	 * default service
	 * 
	 * @author jitender.kumar1
	 */
	@Test(groups = { "DevAsk" }, dataProvider = "SearchService_preOrderWithPdpandCMSdata")
	public void SearchService_cMSisActivefalsePDPResponse(String query) {

		long startTime = Calendar.getInstance().getTimeInMillis();

		RequestGenerator cmsRequest = SearchServiceHelper.cmsPreOrder(query);
		String cmsResponse = cmsRequest.returnresponseasstring();
		System.out.println("CMS--->" + cmsResponse);
		Object responseCMS = JsonPath.read(cmsResponse, "$.data..advancedOrderOptions.preOrder.active");
		String preOrderCMS = responseCMS.toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		System.out.println(preOrderCMS);

		AssertJUnit.assertEquals("IsActive is True in CMS", false, preOrderCMS);

		RequestGenerator pdpRequest = SearchServiceHelper.pdpStyleAPI(query);
		String pdpResponse = pdpRequest.returnresponseasstring();
		System.out.println("PDP--->" + pdpResponse);

		Object preOrderTagPDP = JsonPath.read(pdpResponse, "$.data..advancedOrderOptions");

		String preOrderProdcutTagPDPString = preOrderTagPDP.toString().replace("[", "").replace("]", "")
				.replace("\"", "").trim();
		System.out.println("response--->" + preOrderProdcutTagPDPString);

		AssertJUnit.assertEquals("StyleService_PDPandCMSWithPreorder is not working", preOrderCMS,
				preOrderProdcutTagPDPString);

		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;
		System.out.println("\nTime taken to execute - TestCase - SearchService_getQueryWithSuccessMessage : "
				+ timeTaken + " seconds\n");
		log.info("\nTime taken to execute - TestCase - SearchService_getQueryWithSuccessMessage : " + timeTaken
				+ " seconds\n");
	}

	/*
	 * Test-cases 6.: filteredSearch for style id(12348, 12349) for success
	 * message and preorder fields
	 * 
	 * @author sneha.deep
	 */
	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression", "SearchSanity",
			"SearchRegression" }, dataProvider = "SearchService_filteredSearchPreOrder")
	public void SearchService_filteredSearchPreOrder(String styleId) {
		long startTime = Calendar.getInstance().getTimeInMillis();

		MyntraService service = Myntra.getService(ServiceType.PORTAL_SEARCHSERVICE, APINAME.FILTEREDSEARCH,
				init.Configurations, new String[] { styleId });
		RequestGenerator req = new RequestGenerator(service);
		System.out.println(service.URL);
		log.info(service.URL);
		System.out.println("FIleterd paylaod------>>>>>\n" + service.Payload);
		System.out.println(req.returnresponseasstring());
		String resp = req.respvalidate.returnresponseasstring();

		Object preOrderTagPDP = JsonPath.read(resp, "$.response1.products..advanceOrderTag");
		String preOrderAdvanceTagString = preOrderTagPDP.toString().replace("[", "").replace("]", "").replace("\"", "")
				.trim();
		System.out.println("response--->" + preOrderAdvanceTagString);

		Object preOrderAdvanceOrderType = JsonPath.read(resp, "$.response1.products..advanceOrderType");
		String preOrderAdvanceOrder = preOrderAdvanceOrderType.toString().replace("[", "").replace("]", "")
				.replace("\"", "").trim();
		System.out.println("response--->" + preOrderAdvanceOrder);

		AssertJUnit.assertEquals("SearchService_filteredSearch is not working", 200, req.response.getStatus());
		AssertJUnit.assertEquals("SearchService_filteredSearch advanceOrderTag is not Present",
				preOrderAdvanceTagString, "Exclusive Pre-Order in Myntra");
		AssertJUnit.assertEquals("SearchService_filteredSearch is advanceOrderType filed is not present",
				preOrderAdvanceOrder, "preorder");

		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;
		System.out.println("\nTime taken to execute - TestCase - SearchService_getQueryWithSuccessMessage : "
				+ timeTaken + " seconds\n");
		log.info("\nTime taken to execute - TestCase - SearchService_getQueryWithSuccessMessage : " + timeTaken
				+ " seconds\n");
	}

	/*
	 * Test-cases 6.: filteredSearch for style id's(12348, 12349) contain
	 * Preorder tag's and cms preorder response
	 * 
	 * @author jitender.kumar1
	 */
	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression", "SearchSanity",
			"SearchRegression" }, dataProvider = "SearchService_filteredSearchPreOrderCMS")
	public void SearchService_filteredSearchCMS(String styleId, String styleIdPreOrder) {
		long startTime = Calendar.getInstance().getTimeInMillis();

		RequestGenerator cmsRequest = SearchServiceHelper.cmsPreOrder(styleIdPreOrder);
		String cmsResponse = cmsRequest.returnresponseasstring();
		System.out.println("CMS--->" + cmsResponse);
		Object responseCMS = JsonPath.read(cmsResponse, "$.data..advancedOrderOptions");
		String preOrderCMS = responseCMS.toString().replace("[", "").replace("]", "").trim();
		System.out.println(preOrderCMS);

		Object preOrderTagCMS = JsonPath.read(cmsResponse, "$.data..advancedOrderOptions.preOrder.active");

		String preOrderTagCMSString = preOrderTagCMS.toString().replace("[", "").replace("]", "").replace("\"", "")
				.trim();
		System.out.println("response--->" + preOrderTagCMSString);

		AssertJUnit.assertEquals("SearchService_getQueryGetWithPreorder is not working", "true", preOrderTagCMSString);

		MyntraService service = Myntra.getService(ServiceType.PORTAL_SEARCHSERVICE, APINAME.FILTEREDSEARCH,
				init.Configurations, new String[] { styleId });
		RequestGenerator req = new RequestGenerator(service);
		System.out.println(service.URL);
		log.info(service.URL);
		System.out.println("FIleterd paylaod------>>>>>\n" + service.Payload);
		System.out.println(req.returnresponseasstring());
		String resp = req.respvalidate.returnresponseasstring();

		Object preOrderTagPDP = JsonPath.read(resp, "$.response1.products..advanceOrderTag");
		String preOrderAdvanceTagString = preOrderTagPDP.toString().replace("[", "").replace("]", "").replace("\"", "")
				.trim();
		System.out.println("response--->" + preOrderAdvanceTagString);

		Object preOrderAdvanceOrderType = JsonPath.read(resp, "$.response1.products..advanceOrderType");
		String preOrderAdvanceOrder = preOrderAdvanceOrderType.toString().replace("[", "").replace("]", "")
				.replace("\"", "").trim();
		System.out.println("response--->" + preOrderAdvanceOrder);

		AssertJUnit.assertEquals("SearchService_filteredSearch is not working", 200, req.response.getStatus());
		AssertJUnit.assertEquals("SearchService_filteredSearch advanceOrderTag is not Present",
				preOrderAdvanceTagString, "Exclusive Pre-Order in Myntra");
		AssertJUnit.assertEquals("SearchService_filteredSearch is advanceOrderType filed is not present",
				preOrderAdvanceOrder, "preorder");

		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;
		System.out.println("\nTime taken to execute - TestCase - SearchService_getQueryWithSuccessMessage : "
				+ timeTaken + " seconds\n");
		log.info("\nTime taken to execute - TestCase - SearchService_getQueryWithSuccessMessage : " + timeTaken
				+ " seconds\n");
	}
	
	/*
	 * Test-cases 1.: getQuery GET for style id's contain
	 * discount field
	 * 
	 * 
	 * @author jitender.kumar1
	 */
	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression", "SearchSanity",
			"SearchRegression" }, dataProvider = "SearchService_StaggeredComboGet")
	public void SearchService_StaggeredCombo(String query, String return_docs, String is_facet,
			String login, String rows) {
		
		long startTime = Calendar.getInstance().getTimeInMillis();

		MyntraService service = Myntra.getService(ServiceType.PORTAL_SEARCHSERVICE, APINAME.GETQUERYGET,
				init.Configurations);
		service.URL = apiUtil.prepareparameterizedURL(service.URL,
				new String[] { query, return_docs, is_facet, login, rows });
		RequestGenerator req = new RequestGenerator(service);
		System.out.println(service.URL);
		log.info(service.URL);
		String response = req.returnresponseasstring();
		System.out.println(req.returnresponseasstring());
		
		HashMap<String, String> hmap= new HashMap<String, String>();
		hmap.put("308468", "20% OFF");
		hmap.put("267620", "20% OFF");
		
		

		AssertJUnit.assertEquals("SearchService_getQueryGetWithReturnDocsAsTrue is not working", 200,
				req.response.getStatus());

		// verify products details
		Object discountLabel = JsonPath.read(response, "$.data.queryResult.response1.products..dre_discount_label");
		String discountLabelStr = discountLabel.toString().replace("[", "").replace("]", "").replace("(", "").replace(")", "").replace("\"", "").trim();
				
		//Boolean responseProductsBool = responseProductsStr.contains("id");
		System.out.println("value of discountLabel: " + discountLabelStr);
		
		
		if(hmap.containsKey(query)) 
		{
			for (Map.Entry<String, String> entry : hmap.entrySet())
			{
			  String value1 = entry.getValue();
			  AssertJUnit.assertEquals("SearchService_getQueryGetWithReturnDocsAsTrue is not working", discountLabelStr, value1);
			  
		}
			
		}

		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;
		System.out.println("\nTime taken to execute - TestCase - SearchService_getQueryWithSuccessMessage : "
				+ timeTaken + " seconds\n");
		log.info("\nTime taken to execute - TestCase - SearchService_getQueryWithSuccessMessage : " + timeTaken
				+ " seconds\n");
		
		
	}
	
	/*
	 * Test-cases 1.: getQuery GET for style id's contain
	 * discount field
	 * 
	 * 
	 * @author jitender.kumar1
	 */
	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression", "SearchSanity",
			"SearchRegression" }, dataProvider = "SearchService_StaggeredComboDis")
	public void SearchService_StaggeredComboDis(String query, String return_docs, String is_facet,
			String login, String rows) {
		
		long startTime = Calendar.getInstance().getTimeInMillis();

		MyntraService service = Myntra.getService(ServiceType.PORTAL_SEARCHSERVICE, APINAME.GETQUERYGET,
				init.Configurations);
		service.URL = apiUtil.prepareparameterizedURL(service.URL,
				new String[] { query, return_docs, is_facet, login, rows });
		RequestGenerator req = new RequestGenerator(service);
		System.out.println(service.URL);
		log.info(service.URL);
		String response = req.returnresponseasstring();
		System.out.println(req.returnresponseasstring());
		
		HashMap<String, String> hmap= new HashMap<String, String>();
		hmap.put("308484", "Get upto");
		hmap.put("375405", "Get upto");
		
		

		AssertJUnit.assertEquals("SearchService_getQueryGetWithReturnDocsAsTrue is not working", 200,
				req.response.getStatus());

		// verify products details
		Object discountLabel = JsonPath.read(response, "$.data.queryResult.response1.products..dre_discount_label");
		String discountLabelStr = discountLabel.toString().replace("[", "").replace("]", "").replace("(", "").replace(")", "").replace("\"", "").trim();
				
		//Boolean responseProductsBool = responseProductsStr.contains("id");
		System.out.println("value of discountLabel: " + discountLabelStr);
		
		
		if(hmap.containsKey(query)) 
		{
			for (Map.Entry<String, String> entry : hmap.entrySet())
			{
			  String value1 = entry.getValue();
			 
			  AssertJUnit.assertTrue("SearchService_getQueryGetWithReturnDocsAsTrue is not working", discountLabelStr.contains(value1));
			
			}
			
		}
		else
		{
			for (Map.Entry<String, String> entry1 : hmap.entrySet())
			{
			  String value2 = entry1.getValue();
			  AssertJUnit.assertTrue("SearchService_getQueryGetWithReturnDocsAsTrue is not working", discountLabelStr.contains(value2));
		}

		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;
		System.out.println("\nTime taken to execute - TestCase - SearchService_getQueryWithSuccessMessage : "
				+ timeTaken + " seconds\n");
		log.info("\nTime taken to execute - TestCase - SearchService_getQueryWithSuccessMessage : " + timeTaken
				+ " seconds\n");
		
		}
	}
	/*
	 * Test-cases 1.: getQuery GET for style id's contain
	 * discount field
	 * 
	 * 
	 * @author jitender.kumar1
	 */
	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression", "SearchSanity",
			"SearchRegression" }, dataProvider = "SearchService_FixedPriceDis")
	public void SearchService_FixedPriceDis(String query, String return_docs, String is_facet,
			String login, String rows) {
		
		long startTime = Calendar.getInstance().getTimeInMillis();

		MyntraService service = Myntra.getService(ServiceType.PORTAL_SEARCHSERVICE, APINAME.GETQUERYGET,
				init.Configurations);
		service.URL = apiUtil.prepareparameterizedURL(service.URL,
				new String[] { query, return_docs, is_facet, login, rows });
		RequestGenerator req = new RequestGenerator(service);
		System.out.println(service.URL);
		log.info(service.URL);
		String response = req.returnresponseasstring();
		System.out.println(req.returnresponseasstring());
		
		HashMap<String, String> hmap= new HashMap<String, String>();
		hmap.put("308477", "Get 2 items for 1500");
		hmap.put("375405", "Get 2 items for 1500");
		
		

		AssertJUnit.assertEquals("SearchService_getQueryGetWithReturnDocsAsTrue is not working", 200,
				req.response.getStatus());

		// verify discount details
		Object discountLabel = JsonPath.read(response, "$.data.queryResult.response1.products..dre_discount_label");
		String discountLabelStr = discountLabel.toString().replace("[", "").replace("]", "").replace("(", "").replace(")", "").replace("\"", "").trim();
				
		
		System.out.println("value of discountLabel: " + discountLabelStr);
		
		
		if(hmap.containsKey(query)) 
		{
			for (Map.Entry<String, String> entry : hmap.entrySet())
			{
			  String value1 = entry.getValue();
			 
			  AssertJUnit.assertTrue("SearchService_getQueryGetWithReturnDocsAsTrue is not working", discountLabelStr.contains(value1));
			
			}
			
		}
		else
		{
			for (Map.Entry<String, String> entry1 : hmap.entrySet())
			{
			  String value2 = entry1.getValue();
			  AssertJUnit.assertTrue("SearchService_getQueryGetWithReturnDocsAsTrue is not working", discountLabelStr.contains(value2));
		}

		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;
		System.out.println("\nTime taken to execute - TestCase - SearchService_getQueryWithSuccessMessage : "
				+ timeTaken + " seconds\n");
		log.info("\nTime taken to execute - TestCase - SearchService_getQueryWithSuccessMessage : " + timeTaken
				+ " seconds\n");
		
		}
	}
	
	

	/*
	 * Test-cases : POST SERVICE getQuery 
	 * fixed Price discount
	 * 
	 * 
	 * @author jitender.kumar1
	 */
	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression",
			"SearchRegression" }, dataProvider = "SearchService_FixedPricePost")
	public void SearchService_PostFixedCombo(String query, String return_docs, String is_facet,
			String login) {

		long startTime = Calendar.getInstance().getTimeInMillis();

		MyntraService service = Myntra.getService(ServiceType.PORTAL_SEARCHSERVICE, APINAME.GETQUERY,
				init.Configurations, new String[] { query, return_docs, is_facet, login });
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);
		// System.out.println(service.Payload);
		String response = req.returnresponseasstring();
		// System.out.println(response);

		Object discountLabel = JsonPath.read(response, "$.data.queryResult.response1.products..dre_discount_label");
		String discountLabelStr = discountLabel.toString().replace("[", "").replace("]", "").replace("(", "").replace(")", "").replace("\"", "").trim();;
		
		
		System.out.println("value of discountLabel: " + discountLabelStr);
		
		
		HashMap<String, String> hmap= new HashMap<String, String>();
		hmap.put("308477", "Get 2 items for 1500");
		hmap.put("375405", "Get 2 items for 1500");
		
		 
		AssertJUnit.assertEquals("SearchService_getQueryWithReturnDocsAsFalse is not working", 200,
					req.response.getStatus());
		if(hmap.containsKey(query)) 
		
		{
			for (Map.Entry<String, String> entry : hmap.entrySet())
			{
				 String value1 = entry.getValue();
				 
				 AssertJUnit.assertTrue("SearchService_getQueryGetWithReturnDocsAsTrue is not working", discountLabelStr.contains(value1));
				
			}
				
		}
			

		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;
		System.out.println("\nTime taken to execute - TestCase - SearchService_getQueryWithReturnDocsAsFalse : "
				+ timeTaken + " seconds\n");
		log.info("\nTime taken to execute - TestCase - SearchService_getQueryWithReturnDocsAsFalse : " + timeTaken
				+ " seconds\n");
	}
	
	
	/*
	 * Test-cases : POST SERVICE getQuery 
	 * Staggered combo discount
	 * 
	 * 
	 * @author jitender.kumar1
	 */
	@Test(groups = { "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression",
			"SearchRegression" }, dataProvider = "SearchService_FixedPricePost")
	public void SearchService_PostStaggeredCombo(String query, String return_docs, String is_facet,
			String login) {

		long startTime = Calendar.getInstance().getTimeInMillis();

		MyntraService service = Myntra.getService(ServiceType.PORTAL_SEARCHSERVICE, APINAME.GETQUERY,
				init.Configurations, new String[] { query, return_docs, is_facet, login });
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);
		// System.out.println(service.Payload);
		String response = req.returnresponseasstring();
		// System.out.println(response);

		Object discountLabel = JsonPath.read(response, "$.data.queryResult.response1.products..dre_discount_label");
		String discountLabelStr = discountLabel.toString().replace("[", "").replace("]", "").replace("(", "").replace(")", "").replace("\"", "").trim();;
		
		
		System.out.println("value of discountLabel: " + discountLabelStr);
		
		
		HashMap<String, String> hmap= new HashMap<String, String>();
		hmap.put("308484", "Get upto");
		hmap.put("375405", "Get upto");
		
		 
		AssertJUnit.assertEquals("SearchService_getQueryWithReturnDocsAsFalse is not working", 200,
					req.response.getStatus());
		if(hmap.containsKey(query)) 
		
		{
			for (Map.Entry<String, String> entry : hmap.entrySet())
			{
				 String value1 = entry.getValue();
				 
				 AssertJUnit.assertTrue("SearchService_getQueryGetWithReturnDocsAsTrue is not working", discountLabelStr.contains(value1));
				
			}
				
		}
			

		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime) / 1000.0;
		System.out.println("\nTime taken to execute - TestCase - SearchService_getQueryWithReturnDocsAsFalse : "
				+ timeTaken + " seconds\n");
		log.info("\nTime taken to execute - TestCase - SearchService_getQueryWithReturnDocsAsFalse : " + timeTaken
				+ " seconds\n");
	}


	}

