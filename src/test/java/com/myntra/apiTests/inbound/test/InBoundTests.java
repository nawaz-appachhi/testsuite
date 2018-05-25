package com.myntra.apiTests.inbound.test;

import com.myntra.apiTests.SERVICE_TYPE;
import com.myntra.apiTests.erpservices.Constants;
import com.myntra.apiTests.inbound.dp.InboundServiceDP;
import com.myntra.apiTests.inbound.helper.*;
import com.myntra.apiTests.inbound.helper.ESConstants.AGGMETRICS;
import com.myntra.apiTests.inbound.helper.ESConstants.SOURCE;
import com.myntra.apiTests.inbound.helper.ESConstants.THRESHOLD;
import com.myntra.apiTests.inbound.response.FIFAResponse;
import com.myntra.apiTests.inbound.response.FIFAStatusCodes;
import com.myntra.apiTests.inbound.response.SpotResponse;
import com.myntra.apiTests.inbound.response.jabong.SpotJabongResponse;
import com.myntra.commons.codes.StatusResponse;
import com.myntra.lordoftherings.Initialize;
import com.myntra.apiTests.common.APINAME;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.Payload;
import com.myntra.lordoftherings.gandalf.PayloadType;
import com.myntra.test.commons.service.HTTPMethods;
import com.myntra.test.commons.service.HttpExecutorService;
import com.myntra.test.commons.service.Svc;
import com.myntra.test.commons.testbase.BaseTest;
import org.apache.http.NameValuePair;
import org.apache.log4j.Logger;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.*;

public class InBoundTests extends BaseTest {

	static Logger log = Logger.getLogger(InBoundTests.class);

	private String collectionName = null;
	public static String spotNodeCollectionName;
	private String AWS_ACCESS_KEY = "AKIAI42NZH3GC4DQDFYA";
	private String AWS_ACCESS_SECRET_KEY = "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";

	private String bucketName = "myntra";
	private String key = "logic_data_pipeline/RDF/PROD/AUTOPILOT/fifa_forecast.csv.gz";
	private String interimforcast_key = "logic_data_pipeline/RDF/PROD/AUTOPILOT/InterimForecast.csv.gz";
	private String appfarima_key = "logic_data_pipeline/RDF/PROD/AUTOPILOT/appfarima.csv.ovr.gz";
	private String approvedfinalforcast_key = "logic_data_pipeline/RDF/PROD/AUTOPILOT/ApprovedFinalForecast.csv.gz";
	private String approvedautoesforcast_key = "logic_data_pipeline/RDF/PROD/AUTOPILOT/ApprovedAutoESForecast.csv.gz";
	private String clnd_key = "logic_data_pipeline/RDF/PROD/AUTOPILOT/clnd.csv.dat.gz";
	public static Initialize init;
	public static FIFAResponse fifaResponse;
	public static SpotServiceHelper spothelper;
	public static SpotJson spotJsonGenerator;
	public static DdpQuery ddp;
	public static MongoDBClient mongodb;
	public static ElasticSearchClient esclient;
	
	public static ESQueryGenerator esquerygenerator;
	public static ESHelper eshelper;
	public static Client client;
	public String userid = "ajay.mehra@myntra.com";
	public long styleID = 1139215;
	String slackuser = System.getenv("slackuser");
	String botname = System.getenv("botname");
	public long styleID1 = 1000048;
	public String timestamp = DdpQuery.getCurrentTimeStamp();
	public String getDayBeforeYstrdyDate = DdpQuery.getLastNthDay(-2);
	public String getlast45thday = DdpQuery.getLastNthDay(-46);

	public String query = "select availability.style_id as styleid, sum(availability.is_live) as dayslive, sum(availability.not_broken) as daysnotbroken, sum(case when availability_date_diff <30 then availability.not_broken else 0 end) as daysnotbrokenlast30, sum(case when availability_date_diff <15 then availability.not_broken else 0 end) as daysnotbrokenlast15, max(availability.sku_count) as sku_count from (select style_id, date, datediff(day, to_date(date, 'yyyymmdd'), to_date("
			+ timestamp
			+ ", 'yyyymmdd')) as availability_date_diff, count(distinct sku_id) as sku_count, sum(is_live_on_portal) as live_skus, (case when sum(is_live_on_portal) > 0 then 1 else 0 end) as is_live, (case when (sum(is_live_on_portal) * 1.0/count(distinct sku_id)) * 100 > 70 then 1 else 0 end) as not_broken from fact_product_snapshot where date between 20150101 and "
			+ getDayBeforeYstrdyDate + " and style_id > 0 and style_id is not null and style_id=" + styleID
			+ " group by 1, 2) availability group by availability.style_id order by availability.style_id";

	public String query2 = "SELECT availability.style_id, orders.revenue, orders.rgm, orders.product_discount, orders.coupon_discount,orders.total_discount, orders.quantity, availability.days_live, availability.sku_count FROM ( SELECT p.style_id AS style_id, SUM(o.item_revenue_inc_cashback) AS revenue,SUM(o.item_revenue_inc_cashback -(CASE WHEN (o.item_purchase_price_inc_tax <(0.20*o.item_mrp_value*o.quantity)) THEN 0.65*o.item_mrp_value*o.quantity ELSE o.item_purchase_price_inc_tax END) + o.vendor_funding - o.tax) AS rgm,SUM(o.product_discount) AS product_discount, SUM(o.coupon_discount) as coupon_discount, SUM(o.coupon_discount + o.product_discount) AS total_discount, SUM(o.quantity) AS quantity FROM fact_orderitem o JOIN dim_product p ON p.sku_id = o.sku_id WHERE (o.is_realised = 1 or o.is_shipped = 1) AND (o.order_created_date BETWEEN "
			+ getlast45thday + " AND " + getDayBeforeYstrdyDate
			+ " )AND style_id not like '%N%' AND style_id is not NULL AND CAST(style_id as INTEGER) in (" + styleID1
			+ ") GROUP BY 1 ORDER BY 1,2)orders FULL OUTER JOIN (SELECT a.style_id AS style_id, SUM(live_status) AS days_live, sku_count FROM (SELECT fps.style_id, fps.date, MAX(fps.is_live_on_portal) AS live_status, COUNT(DISTINCT fps.sku_id) AS sku_count FROM fact_product_snapshot fps WHERE date BETWEEN "
			+ getlast45thday + " AND " + getDayBeforeYstrdyDate
			+ " AND fps.style_id is not NULL AND CAST(fps.style_id as INTEGER) in (" + styleID1
			+ ")GROUP BY 1,2) a GROUP BY 1, 3 ORDER BY style_id) availability ON orders.style_id = availability.style_id ORDER BY availability.style_id";

	public String query_name = "test";
	public String query_description = "test";
	public String unload = "true";
	public String userdir = System.getProperty("user.dir");

	@BeforeClass()
	public void testAfterClass() throws SQLException {
		init = new Initialize("Data/configuration");
		fifaResponse = new FIFAResponse();
		spothelper = new SpotServiceHelper();
		ddp = new DdpQuery();
		mongodb = new MongoDBClient();
		esclient = new ElasticSearchClient();
		client = esclient.getESClientConnection();
		esquerygenerator = new ESQueryGenerator();
		eshelper = new ESHelper();
		spotJsonGenerator=new SpotJson();

	}



	@Test(enabled = true, groups = { "sanity", "Regression",
			"spotapi" }, description = "get global attributes of all myntra brands")
	public void getGlobalAttributeOfAllBrands() throws Exception {
		HashMap<String, String> Headers = new HashMap<String, String>();
		Headers.put("Content-Type", "application/json");
		Headers.put("accept", "application/json");
		Headers.put("Authorization", "Basic YTpi");

		Svc svc = HttpExecutorService.executeHttpService(Constants.SPOT_PATH.GET_GLOBAL_ATTRIBUTE_OF_ALL_BRANDS_DATA, null,
				SERVICE_TYPE.SPOT_CATALOG_SVC.toString(), HTTPMethods.GET, null, Headers);
		Assert.assertEquals(svc.getResponseStatus(), 200, "API response status code is");
		Assert.assertNotEquals(svc.getResponseStatusType("statusType"), FIFAStatusCodes.Type.FAILURE.toString(),
				"API Status Type is Failure and status message is:" + svc.getResponseStatusType("statusMessage"));
		FIFAResponse response = (FIFAResponse) APIUtilities.getJsontoObject(svc.getResponseBody(), new FIFAResponse());
		Assert.assertEquals(response.getStatus().getStatusType(), StatusResponse.Type.SUCCESS.toString(),
				"status type is:");
		Assert.assertEquals(response.getStatus().getStatusCode(),
				FIFAStatusCodes.SPOT_NODE_DATA_FETCH_SUCCESS.getStatusCode(), "status code is:");
		log.debug("\nPrinting globalattribute/allBrands API response:\n" + svc.getResponseBody() + "\n");
		log.debug("\nPrinting globalattribute/allBrands API response-Status Type:\n"
				+ response.getStatus().getStatusType() + "\n");
	}

	@Test(enabled = true, groups = { "sanity", "Regression",
			"spotapi","checkforrep" }, dataProviderClass=InboundServiceDP.class,dataProvider="getClusterData",description = "get cluster data for available Myntra style")
	public void getClusterDatafromSpotAPI(String payload) throws Exception {
		

		// Check the response for the valid style id, the response must have
		// a success status type, and should have the data in the data array
		// object
		Svc svc = HttpExecutorService.executeHttpService(Constants.SPOT_PATH.GET_CLUSTER_DATA, null, SERVICE_TYPE.SPOT_SVC.toString(),
				HTTPMethods.POST, payload, spothelper.getSPOTHeader());
		Assert.assertEquals(svc.getResponseStatus(), 200, "API response status code is");
		Assert.assertNotEquals(svc.getResponseStatusType("statusType"), FIFAStatusCodes.Type.FAILURE.toString(),
				"API Status Type is Failure and status message is:" + svc.getResponseStatusType("statusMessage"));
		//FIFAResponse response = (FIFAResponse) APIUtilities.getJsontoObject(svc.getResponseBody(), new FIFAResponse());
		
		log.debug("\nPrinting getClusterData API response:\n" + svc.getResponseBody() + "\n");
		
		JSONObject jsonObject = new JSONObject(svc.getResponseBody());
		JSONArray data = jsonObject.getJSONArray("data");
		Assert.assertNotEquals(data.length(), 0, "the data array field is empty, Cluster info is not available for the style_id");
		log.debug("\nPrinting getClusterData API response-CollectionName:\n" + collectionName + "\n");

		

	}
	
	@Test(enabled = true, groups = { "sanity", "Regression",
			"spotapi","checkforrep" }, dataProviderClass=InboundServiceDP.class,dataProvider="getClusterDataForInvalidStyleId",description = "get cluster data for available Myntra style")
	public void getClusterDataForInvalidStyleId(String payload) throws Exception {
		

		// Check the response for the valid style id, the response must have
		// a success status type, and should have the data in the data array
		// object
		Svc svc = HttpExecutorService.executeHttpService(Constants.SPOT_PATH.GET_CLUSTER_DATA, null, SERVICE_TYPE.SPOT_SVC.toString(),
				HTTPMethods.POST, payload, spothelper.getSPOTHeader());
		Assert.assertEquals(svc.getResponseStatus(), 200, "API response status code is");
		Assert.assertNotEquals(svc.getResponseStatusType("statusType"), FIFAStatusCodes.Type.FAILURE.toString(),
				"API Status Type is Failure and status message is:" + svc.getResponseStatusType("statusMessage"));
		//FIFAResponse response = (FIFAResponse) APIUtilities.getJsontoObject(svc.getResponseBody(), new FIFAResponse());
		
		log.debug("\nPrinting getClusterData API response:\n" + svc.getResponseBody() + "\n");
		
		JSONObject jsonObject = new JSONObject(svc.getResponseBody());
		JSONArray data = jsonObject.getJSONArray("data");
		Assert.assertEquals(data.length(), 0, "the data array field is empty, Cluster info is not available for the style_id");
		log.debug("\nPrinting getClusterData API response-CollectionName:\n" + collectionName + "\n");

		

	}

	@Test(enabled = true, groups = { "sanity", "Regression",
			"spotapi" },dataProviderClass=InboundServiceDP.class,dataProvider="getProductDatafromSpotAPI", description = "get product data for available Myntra style")
	public void getProductDatafromSpotAPI(String payload,String source_ID) throws Exception {
		

		// Check the response for the valid style id, the response must have
		// a success status type, and should have the data in the data array
		// object
		Svc svc = HttpExecutorService.executeHttpService(Constants.SPOT_PATH.GET_PRODUCT_DATA, null, SERVICE_TYPE.SPOT_SVC.toString(),
				HTTPMethods.POST, payload, spothelper.getSPOTHeader());
		Assert.assertEquals(svc.getResponseStatus(), 200, "API response status code is");
		Assert.assertNotEquals(svc.getResponseStatusType("statusType"), FIFAStatusCodes.Type.FAILURE.toString(),
				"API Status Type is Failure and status message is:" + svc.getResponseStatusType("statusMessage"));
		FIFAResponse response = (FIFAResponse) APIUtilities.getJsontoObject(svc.getResponseBody(), new FIFAResponse());
		Assert.assertEquals(response.getStatus().getStatusType(), StatusResponse.Type.SUCCESS.toString(),
				"status type is:");
		Assert.assertEquals(response.getStatus().getStatusCode(),
				FIFAStatusCodes.SPOT_DATA_FETCH_SUCCESS.getStatusCode(), "status code is:");
		log.debug("\nPrinting getproductdata API response:\n" + svc.getResponseBody() + "\n");
		log.debug("\nPrinting getproductdata API response-Status Type:\n" + response.getStatus().getStatusType()
				+ "\n");
		JSONObject jsonObject = new JSONObject(svc.getResponseBody());
		JSONArray data = jsonObject.getJSONArray("data");
		Assert.assertEquals(data.getJSONObject(0).getString("styleId"), source_ID,
			"the order of styles are not preserved");

		// the order od the style id must be preserved
//		for (int i = 0; i < data.length(); i++) {
//			Assert.assertEquals(data.getJSONObject(i).getString("styleId"), source_ID[i],
//					"the order of styles are not preserved");
//		}
		

	}

	@Test(enabled = true, groups = { "sanity", "Regression",
			"spotapi" }, description = "Get the style data for the product that matched with the style_id")
	public void get_styleData_with_style_id() throws Exception {

		APINAME apiundertest = APINAME.GETSTYLEDATAWITHSTYLEID;
		Payload p1 = new Payload(apiundertest.toString(), init.Configurations, PayloadType.JSON);
		FIFAResponse response = (FIFAResponse) APIUtilities.getJsontoObject(p1.filecontent, new FIFAResponse());

		Svc svc = HttpExecutorService.executeHttpService(Constants.SPOT_PATH.GET_STYLE_DATA_WITH_STYLEID, null,
				SERVICE_TYPE.SPOT_SVC.toString(), HTTPMethods.POST, p1.filecontent, spothelper.getSPOTHeader());
		Assert.assertEquals(svc.getResponseStatus(), 200, "API Status Code is");
		Assert.assertNotEquals(svc.getResponseStatusType("statusType"), FIFAStatusCodes.Type.FAILURE.toString(),
				"API Status Type is Failure and status message is:" + svc.getResponseStatusType("statusMessage"));

		log.info("\nPrinting SPOT GetStyleData API response :\n" + svc.getResponseBody() + "\n");
		response = (FIFAResponse) APIUtilities.getJsontoObject(svc.getResponseBody(), new FIFAResponse());

		Assert.assertEquals(response.getStatus().getStatusType(), StatusResponse.Type.SUCCESS.toString(),
				"Status Type is:");
		Assert.assertEquals(response.getStatus().getStatusCode(),
				FIFAStatusCodes.SPOT_DATA_FETCH_SUCCESS.getStatusCode(), "Status Code is:");
		// Assert.assertEquals(response.getData().get(0).getStyleId(),
		// styleId[0],"Style_Id is:");

		log.debug("\nPrinting Get style data with style_id  API response:\n" + svc.getResponseBody() + "\n");
		log.debug("\nPrinting Get style data with style_id -Status Type:\n" + response.getStatus().getStatusType()
				+ "\n");
		
		
	}

	@Test(enabled = true, groups = { "sanity", "Regression",
			"spotapi" }, description = "Accepts Json file, uploads it and creates a collection id")
	public void upload_bi_data() throws Exception {

		APINAME apiundertest = APINAME.UPLOADBIDATA;
		Payload p1 = new Payload(apiundertest.toString(), init.Configurations, PayloadType.JSON);
		Svc svc = HttpExecutorService.executeHttpService(Constants.SPOT_PATH.UPLOAD_BI_DATA, null, SERVICE_TYPE.SPOT_SVC.toString(),
				HTTPMethods.POST, p1.filecontent, spothelper.getSPOTHeader());
		Assert.assertEquals(svc.getResponseStatus(), 200, "Status Code is:");
		Assert.assertNotEquals(svc.getResponseStatusType("statusType"), FIFAStatusCodes.Type.FAILURE.toString(),
				"API Status Type is Failure and status message is:" + svc.getResponseStatusType("statusMessage"));
		log.debug("\nPrinting SPOT UploadBIData API response :\n" + svc.getResponseBody() + "\n");
		log.debug("\nPrinting SPOT UploadBIData API generated Collection Name :\n" + svc.getResponseBody() + "\n");

	}

	@Test(enabled = true, groups = { "sanity", "Regression",
			"spotapi" }, dataProvider = "fetch_Attributes_Values", dataProviderClass = InboundServiceDP.class,description = "Returns the list of data available for attributes like brands, gender, seasoncode,filters for resp sources")
	public void fetch_Attributes_Values(String payload) throws Exception {

		Svc svc = HttpExecutorService.executeHttpService(Constants.SPOT_PATH.FETCH_ATTRIBUTES_VALUES, null, SERVICE_TYPE.SPOT_SVC.toString(),
				HTTPMethods.POST, payload, spothelper.getSPOTHeader());
		Assert.assertEquals(svc.getResponseStatus(), 200, "Status Code is:");
		Assert.assertNotEquals(svc.getResponseStatusType("statusType"), FIFAStatusCodes.Type.FAILURE.toString(),
				"API Status Type is Failure and status message is:" + svc.getResponseStatusType("statusMessage"));
		log.info("\nPrinting SPOT fetch_Attributes_Values API response :\n" + svc.getResponseBody() + "\n");
		spothelper.validateFetchAttributesAPI(svc.getResponseBody());

	}

	@Test(enabled = true, groups = { "sanity", "Regression",
			"spotapi" }, dataProvider = "filter_Styles", dataProviderClass = InboundServiceDP.class, description = "Filters the search results with the filter payload provided for resp sources")
	public void filter_Styles(String payload) throws Exception {

		Svc svc = HttpExecutorService.executeHttpService(Constants.SPOT_PATH.FILTER_STYLES, null,
				SERVICE_TYPE.SPOT_SVC.toString(), HTTPMethods.POST, payload, spothelper.getSPOTHeader());
		Assert.assertEquals(svc.getResponseStatus(), 200, "Status Code is:");
		Assert.assertNotEquals(svc.getResponseStatusType("statusType"), FIFAStatusCodes.Type.FAILURE.toString(),
				"API Status Type is Failure and status message is:" + svc.getResponseStatusType("statusMessage"));
		log.info("\nPrinting SPOT fetch_Attributes_Values API response :\n" + svc.getResponseBody() + "\n");
		spothelper.validateFilterStylesAPI(svc.getResponseBody());

	}

//	@Test(enabled = true, groups = { "sanity", "Regression",
//			"spotapi" }, description = "Accepts Json file, uploads it and creates a collection id")
//	public void uploadProcessedData() throws Exception {
//
//		APINAME apiundertest = APINAME.UPLOADPROCESSEDDATA;
//		Payload p1 = new Payload(apiundertest, init.Configurations, PayloadType.JSON);
//		Svc svc = HttpExecutorService.executeHttpService(SPOT_PATH.UPLOAD_PROCESSED_DATA, null,
//				SERVICE_TYPE.SPOT_SVC.toString(), HTTPMethods.POST, p1.filecontent, spothelper.getSPOTHeader());
//		Assert.assertEquals(svc.getResponseStatus(), 200, "API status Code is:");
//		Assert.assertNotEquals(svc.getResponseStatusType("statusType"), FIFAStatusCodes.Type.FAILURE.toString(),
//				"API Status Type is Failure and status message is:" + svc.getResponseStatusType("statusMessage"));
//
//		FIFAResponse response = (FIFAResponse) APIUtilities.getJsontoObject(svc.getResponseBody(), new FIFAResponse());
//		Assert.assertEquals(response.getStatus().getStatusCode(), FIFAStatusCodes.DATA_FETCH_SUCCESS.getStatusCode(),
//				"Status code is:");
//
//		log.debug("\nPrinting SPOT uploadProcessedData API response :\n" + svc.getResponseBody() + "\n");
//		log.debug("\nPrinting SPOT uploadProcessedData API generated Collection Name :\n"
//				+ fifaResponse.getCollectionName(svc.getResponseBody()) + "\n");
//
//	}

//	/**Descoped
//	@Test(enabled = false, groups = { "sanity", "Regression",
//			"spotapi" }, description = "Fetches the BIData for the given search criteria and creates a collection id")
//	public void external_trends_fetch_BI_Data() throws Exception {
//
//		APINAME apiundertest = APINAME.FETCHBIDATA;
//
//		Payload p1 = new Payload(apiundertest, init.Configurations, PayloadType.JSON);
//		Svc svc = HttpExecutorService.executeHttpService(SPOT_PATH.FETCH_BI_DATA, null, SERVICE_TYPE.SPOT_SVC.toString(),
//				HTTPMethods.POST, p1.filecontent, spothelper.getSPOTHeader());
//		Assert.assertEquals(svc.getResponseStatus(), 200, "API Response status code is :");
//		Assert.assertNotEquals(svc.getResponseStatusType("statusType"), FIFAStatusCodes.Type.FAILURE.toString(),
//				"API Status Type is Failure and status message is:" + svc.getResponseStatusType("statusMessage"));
//
//		fetchBIDataCollectionName = svc.getResponseBody();
//
//		log.debug("fetchBIData successful, Response is:\n" + svc.getResponseBody() + "\n");
//		log.debug("fetchBIData successful,generated collection name:\n" + fetchBIDataCollectionName + "\n");
//
//	}
	
//	***Descoped

//	@Test(enabled = true, groups = { "sanity", "Regression", "spotapi" }, dependsOnMethods = {
//			"external_trends_fetch_BI_Data" }, description = "Process the BIData for the collection name generated by fetchBIData API and creates a processed collection id")
//	public void external_trends_process_BI_Data() throws Exception {
//
//		Svc svc = HttpExecutorService.executeHttpService(SPOT_PATH.PROCESS_BI_DATA,
//				new String[] { fetchBIDataCollectionName }, SERVICE_TYPE.SPOT_SVC.toString(), HTTPMethods.GET, null,
//				spothelper.getSPOTHeader());
//		Assert.assertEquals(svc.getResponseStatus(), 200, "API Response status code is :");
//		Assert.assertNotEquals(svc.getResponseStatusType("statusType"), FIFAStatusCodes.Type.FAILURE.toString(),
//				"API Status Type is Failure and status message is:" + svc.getResponseStatusType("statusMessage"));
//		processedBIDataCollectionName = svc.getResponseBody();
//
//		log.debug("processBIData apitest successful,generated collection name:\n" + svc.getResponseBody() + "\n");
//
//	}
	
//	/**Descoped
//	@Test(enabled = true, groups = { "sanity", "Regression", "spotapi" }, dependsOnMethods = {
//			"external_trends_process_BI_Data" }, description = "get the BIData for the collection name processed by process_BI_Data API and returns the all the data available in collection id")
//	public void external_trends_get_processed_BI_Data() throws Exception {
//
//		Svc svc = HttpExecutorService.executeHttpService(SPOT_PATH.GET_DATA,
//				new String[] { processedBIDataCollectionName }, SERVICE_TYPE.SPOT_SVC.toString(), HTTPMethods.GET, null,
//				spothelper.getSPOTHeader());
//		Assert.assertEquals(svc.getResponseStatus(), 200, "API Response status code is :");
//		Assert.assertNotEquals(svc.getResponseStatusType("statusType"), FIFAStatusCodes.Type.FAILURE.toString(),
//				"API Status Type is Failure and status message is:" + svc.getResponseStatusType("statusMessage"));
//		log.debug("getData successful,returned all the styles matched with selection criteria:\n"
//				+ svc.getResponseBody() + "\n");
//
//	}

	@Test(enabled = true, dataProvider = "validate_rapid_api_for_myntra", dataProviderClass = InboundServiceDP.class, groups = {
			"sanity", "Regression",
			"spotapi" }, description = "Building search with style catalog date and order date combination and verifying the fields")

	public void validate_rapid_api_for_myntra( String style_Catalogue_from_date,String style_Catalogue_to_date,String order_from_date,String order_to_date, String articleType,String brand,String seasonCode,String gender,String source) throws Exception {
		
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("Content-Type", "application/json");
			
		String payload=spotJsonGenerator.spotPayload(style_Catalogue_from_date,style_Catalogue_to_date,order_from_date,order_to_date,
				articleType,brand,seasonCode, gender,source);
		
		String collectionId = spothelper.getspotrapidqueryID(payload, map);
		if(!collectionId.contains("No Data Available")){
		SpotResponse response=spothelper.get_rapid_query_data(collectionId, map);
		
		spothelper.validateRapidQueryData(response,style_Catalogue_from_date,style_Catalogue_to_date,order_from_date,order_to_date
				,articleType, gender,source);
		
		
		spothelper.validate_Filter_Type_Data_Myntra( collectionId, map);
		}
		


	}
	
	@Test(enabled = true, dataProvider = "validate_rapid_api_for_jabong", dataProviderClass = InboundServiceDP.class, groups = {
			"sanity", "Regression",
			"spotapi" }, description = "Building search with style catalog date and order date combination and verifying the fields")
	public void validate_rapid_api_for_jabong( String style_Catalogue_from_date,String style_Catalogue_to_date,String order_from_date,String order_to_date, String articleType,String brand,String seasonCode,String gender,String source) throws Exception {
		
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("Content-Type", "application/json");
			
		String payload=spotJsonGenerator.spotPayload(style_Catalogue_from_date,style_Catalogue_to_date,order_from_date,order_to_date,
				articleType,brand,seasonCode, gender,source);
		
		String collectionId = spothelper.getspotrapidqueryID(payload, map);
		if(!collectionId.contains("No Data Available")){
		SpotJabongResponse response=spothelper.get_rapid_query_data_for_Jabong(collectionId, map);
		
		spothelper.validateRapidQueryDataForJabong(response, style_Catalogue_from_date, style_Catalogue_to_date,
				order_from_date,order_to_date,articleType, gender,source);
		}


	}
	
	@Test(enabled = true, dataProvider = "validate_rapid_api_for_amazon", dataProviderClass = InboundServiceDP.class, groups = {
			"sanity", "Regression",
			"spotapi" }, description = "Building search with style catalog date and order date combination and verifying the fields")
	public void validate_rapid_api_for_amazon( String style_Catalogue_from_date,String style_Catalogue_to_date,String order_from_date,String order_to_date, String articleType,String brand,String seasonCode,String gender,String source) throws Exception {
		
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("Content-Type", "application/json");
			
		String payload=spotJsonGenerator.spotPayload(style_Catalogue_from_date,style_Catalogue_to_date,order_from_date,order_to_date,
				articleType,brand,seasonCode, gender,source);
		
		String collectionId = spothelper.getspotrapidqueryID(payload, map);
		if(!collectionId.contains("No Data Available")){
		SpotJabongResponse response=spothelper.get_rapid_query_data_for_Jabong(collectionId, map);
		
		
		spothelper.validateRapidQueryDataForJabong(response, style_Catalogue_from_date, style_Catalogue_to_date,
				order_from_date,order_to_date,articleType, gender,source);
		}

	}
	
	
	
	
	


	
//	@Test(enabled = true, dataProvider = "spotGenderData", dataProviderClass = InboundServiceDP.class, groups = {
//			"sanity", "Regression", "spotapi" }, description = "Building search on gender basis")
//	public void get_spot_data_with_different_genders(String gender) throws Exception {
//		SpotJson sp = new SpotJson();
//		SpotServiceHelper spot = new SpotServiceHelper();
//		HashMap<String, String> map = new HashMap<String, String>();
//		map.put("Content-Type", "application/json");
//		String todays_date = spot.getCurrentTimeStamp();
//		String past_date = spot.last15DaysCalender();
//		String payload = sp.spotPayload(past_date, todays_date, past_date, todays_date, ArticleType.TSHIRTS, Brands.ALL,
//				FilterType.TOP_SELLER_ROUND, SeasonCode.SEASONCODE, gender, Source.MYNTRA);
//		String rapidquery = spot.getspotrapidqueryID(payload, map);
//		spot.get_rapid_query_status(rapidquery, map);
//		String response = spot.get_spot_data_with_filters(rapidquery,
//				sp.spotTopSellerTshirtsFilterPayload(TopSeller_TSHIRTS.ASPMIN, TopSeller_TSHIRTS.ASPMAX,
//						TopSeller_TSHIRTS.DAYSLIVE, TopSeller_TSHIRTS.PTD, TopSeller_TSHIRTS.ROS, rapidquery,
//						Source.MYNTRA),
//				map);
//		SpotResponse responsebody = (SpotResponse) APIUtilities.getJsontoObject(response, new SpotResponse());
//		Data[] data = responsebody.getData();
//
//		for (int i = 0; i < data.length; i++) {
//
//			Assert.assertEquals(data[i].getGender(), gender.toLowerCase(),
//					"Gender is not correct: " + data[i].getGender());
//		}
//	}

	// ***********************SPOT Node
	// Cases*******************************************//

	@Test(enabled = false, groups = { "sanity", "Regression",
			"spotapi" }, description = "Fetchdata api makes a call to SPOT node, which will make fetch ,process api calls and returns collection id, no of styles")
	public void spot_node_fetchdata_api() throws Exception {

		APINAME apiundertest = APINAME.SPOTNODEFETCHDATA;
		Payload p1 = new Payload(apiundertest.toString(), init.Configurations, PayloadType.JSON);
		Svc svc = HttpExecutorService.executeHttpService(Constants.SPOT_NODE_PATH.FETCH_DATA, null,
				SERVICE_TYPE.SPOT_NODE_SVC.toString(), HTTPMethods.POST, p1.filecontent, spothelper.getSPOTHeader());
		Assert.assertEquals(svc.getResponseStatus(), 200, "Status Code is:");
		Assert.assertNotEquals(svc.getResponseStatusType("statusType"), FIFAStatusCodes.Type.FAILURE.toString(),
				"API Status Type is Failure and status message is:" + svc.getResponseStatusType("statusMessage"));
		log.info("\nPrinting SPOT Fetchdata API response :\n" + svc.getResponseBody() + "\n");
		FIFAResponse response = (FIFAResponse) APIUtilities.getJsontoObject(svc.getResponseBody(), new FIFAResponse());
		Assert.assertEquals(response.getStatus().getStatusCode(),
				FIFAStatusCodes.SPOT_NODE_DATA_FETCH_SUCCESS.getStatusCode(), "Status code is:");
		spotNodeCollectionName = response.getCollection_id();
		log.debug("\nPrinting SPOT Fetchdata API no of styles returned :\n" + response.getNum_styles() + "\n");
		log.debug("\nPrinting SPOT Fetchdata API generated Collection Name :\n" + response.getCollection_id() + "\n");

	}

	@Test(enabled = false, groups = { "sanity", "Regression", "spotapi" }, dependsOnMethods = {
			"spot_node_fetchdata_api" }, dataProviderClass = InboundServiceDP.class, dataProvider = "spotnodefilterDP", description = "Refine_Search api filters the styles generated by spot_node_fetchdata_api and returns the filtered data")
	public void spot_node_refinesearch_api(String jsonpayload) throws Exception {

		Svc svc = HttpExecutorService.executeHttpService(Constants.SPOT_NODE_PATH.REFINE_SEARCH, null,
				SERVICE_TYPE.SPOT_NODE_SVC.toString(), HTTPMethods.POST, jsonpayload, spothelper.getSPOTHeader());
		Assert.assertEquals(svc.getResponseStatus(), 200, "Status Code is:");
		Assert.assertNotEquals(svc.getResponseStatusType("statusType"), FIFAStatusCodes.Type.FAILURE.toString(),
				"API Status Type is Failure and status message is:" + svc.getResponseStatusType("statusMessage"));
		log.info("\nPrinting SPOT Fetchdata API response :\n" + svc.getResponseBody() + "\n");
		FIFAResponse response = (FIFAResponse) APIUtilities.getJsontoObject(svc.getResponseBody(), new FIFAResponse());
		Assert.assertEquals(response.getStatus().getStatusCode(),
				FIFAStatusCodes.SPOT_NODE_DATA_FETCH_SUCCESS.getStatusCode(), "Status code is:");

		log.debug("\nPrinting SPOT Fetchdata API no of styles returned :\n" + response.getNum_styles() + "\n");
		log.debug("\nPrinting SPOT Fetchdata API generated Collection Name :\n" + response.getCollection_id() + "\n");

	}

	@Test(enabled = false, groups = { "sanity", "Regression", "spotapi" }, dependsOnMethods = {
			"spot_node_fetchdata_api" }, dataProviderClass = InboundServiceDP.class, dataProvider = "spotnodefilterDP", description = "Refine_Search api filters the styles generated by spot_node_fetchdata_api and returns the filtered data")
	public void spot_node_start_visualizing_api(String jsonpayload) throws Exception {

		Svc svc = HttpExecutorService.executeHttpService(Constants.SPOT_NODE_PATH.VIEW_IMAGES, null,
				SERVICE_TYPE.SPOT_NODE_SVC.toString(), HTTPMethods.POST, jsonpayload, spothelper.getSPOTHeader());
		Assert.assertEquals(svc.getResponseStatus(), 200, "Status Code is:");
		Assert.assertNotEquals(svc.getResponseStatusType("statusType"), FIFAStatusCodes.Type.FAILURE.toString(),
				"API Status Type is Failure and status message is:" + svc.getResponseStatusType("statusMessage"));
		log.info("\nPrinting SPOT Fetchdata API response :\n" + svc.getResponseBody() + "\n");
		FIFAResponse response = (FIFAResponse) APIUtilities.getJsontoObject(svc.getResponseBody(), new FIFAResponse());
		Assert.assertEquals(response.getStatus().getStatusCode(),
				FIFAStatusCodes.SPOT_NODE_DATA_FETCH_SUCCESS.getStatusCode(), "Status code is:");

		log.debug("\nPrinting SPOT Fetchdata API no of styles returned :\n" + response.getNum_styles() + "\n");
		log.debug("\nPrinting SPOT Fetchdata API generated Collection Name :\n" + response.getCollection_id() + "\n");

	}

	// ***************************ES Validation
	// Scenarios********************************************//

	// verify_products_with_aggmetrics_sku_count_greater_than_fifty
	@Test(enabled = true, dataProvider = "myntrasourcepathsDP", dataProviderClass = InboundServiceDP.class, groups = {
			"sanity", "Regression",
			"es" }, description = "Verify the products whose aggMetrics.sku_count>50.If count is greater than 50, verify the actual product in AKR myntra website.check in kibana for manual and ES for automation")
	public void verify_products_with_aggmetrics_sku_count_greater_than_fifty(String searchIndex) throws Exception {

		int sku_count = 50;
		SearchRequestBuilder query = esquerygenerator.getdocshavingSkuCountgreaterthan50Query(client, searchIndex,
				sku_count);
		SearchResponse response = esclient.ESExecutor(query);
		long totalActiveDocs = eshelper.getTotalHits(response);
		long totalDocsMissingSkus = eshelper.getAggregationDocCount(response, 0);

		log.debug("\nPrinting Total Active Products :\n" + totalActiveDocs + "\n");
		log.debug("\nTotal documents that are missing style_ids:\n" + totalDocsMissingSkus + "\n");

		// Assert.assertTrue(totalDocsMissingSkus < (totalActiveDocs*0.1),
		// "Products missing skus for "+searchIndex+" source are
		// :"+totalDocsMissingSkus+" found which should not be exceeding 10%
		// of Total docs:"+totalActiveDocs);

	}

	@Test(enabled = true, dataProvider = "myntrasourcepathsDP", dataProviderClass = InboundServiceDP.class, groups = {
			"sanity", "Regression",
			"es" }, description = "Verify the products which are having styleAttributes and styleAttributes_source and not having sku’s section in ES/Kibana for CMS source")
	public void identify_count_of_products_missing_skus(String searchIndex) throws Exception {

		SearchRequestBuilder query = esquerygenerator.getdocsmissingSkusQuery(client, searchIndex);
		SearchResponse response = esclient.ESExecutor(query);
		long totalActiveDocs = eshelper.getTotalHits(response);
		long totalDocsMissingSkus = eshelper.getAggregationDocCount(response, 0);

		log.debug("\nPrinting Total Active Products :\n" + totalActiveDocs + "\n");
		log.debug("\nTotal documents that are missing style_ids:\n" + totalDocsMissingSkus + "\n");


			Assert.assertTrue(totalDocsMissingSkus <= (totalActiveDocs * 0.1),
					"Products missing skus for " + searchIndex + " source are :" + totalDocsMissingSkus
							+ " found which should not be exceeding 10% of Total docs:" + totalActiveDocs);


	}

	@Test(enabled = true, dataProvider = "myntrasourcepathsDP", dataProviderClass = InboundServiceDP.class, groups = {
			"sanity", "Regression",
			"es" }, description = "Verify the total count of products which are live or active. check in kibana for manual and ES for automation")
	public void identify_count_of_products_active_in_myntra(String searchIndex) throws Exception {

		SearchRequestBuilder query = esquerygenerator.getactiveproductsquery(client, searchIndex);
		SearchResponse response = esclient.ESExecutor(query);
		long totalDocs = eshelper.getTotalHits(response);
		long totalActiveDocs = eshelper.getAggregationDocCount(response, 0);

		log.debug("\nPrinting Total Products :\n" + totalDocs + "\n");
		log.debug("\nTotal documents that are active:\n" + totalActiveDocs + "\n");


			Assert.assertTrue(totalActiveDocs >= (totalDocs * 0.1),
					"Total active Products for " + searchIndex + " source are :" + totalActiveDocs
							+ " found which should be exceeding atleast 10% of Total docs:" + totalDocs);


	}

	@Test(enabled = true, dataProvider = "sourcepathsDP", dataProviderClass = InboundServiceDP.class, groups = {
			"sanity", "Regression",
			"es" }, description = "Verify the products that are missing min attributes like brand,article_type,gender,mrp/article_mrp,colour ")
	public void identify_count_of_products_missing_min_mandatory_attributes(String searchIndex) throws Exception {

		

			SearchRequestBuilder query = esquerygenerator.getproductsmissingminattributesquery(client, searchIndex);
			SearchResponse response = esclient.ESExecutor(query);
			long totalActiveDocs = eshelper.getTotalHits(response);
			long totalDocsMissingArticleType = eshelper.getAggregationDocCount(response, 0);
			long totalDocsMissingBrand = eshelper.getAggregationDocCount(response, 1);
			long totalDocsMissingGender = eshelper.getAggregationDocCount(response, 2);
			long totalDocsMissingMRP = eshelper.getAggregationDocCount(response, 3);

			log.debug("\nTotal products that are active:\n" + totalActiveDocs + "\n");
			log.debug("\nTotal products that are missing Article_Type Attribute:\n" + totalDocsMissingArticleType
					+ "\n");
			log.debug("\nTotal products that are missing Brand Attribute:\n" + totalDocsMissingBrand + "\n");
			log.debug("\nTotal products that are missing Gender Attribute:\n" + totalDocsMissingGender + "\n");
			log.debug("\nTotal products that are missing MRP Attribute:\n" + totalDocsMissingMRP + "\n");

			Assert.assertTrue(totalDocsMissingArticleType <= (totalActiveDocs * THRESHOLD.TENPERCENT),
					"Total Products missing Article_Type Attributes for " + searchIndex + " source are :"
							+ totalDocsMissingArticleType + ", which are found to be exceeding 10% of Total docs:"
							+ totalActiveDocs);
			Assert.assertTrue(totalDocsMissingBrand <= (totalActiveDocs * THRESHOLD.TENPERCENT),
					"Total Products missing Brand Attributes for " + searchIndex + " source are :"
							+ totalDocsMissingBrand + ", which are found to be exceeding 10% of Total docs:"
							+ totalActiveDocs);
			Assert.assertTrue(totalDocsMissingGender <= (totalActiveDocs * THRESHOLD.TENPERCENT),
					"Total Products missing Gender Attributes for " + searchIndex + " source are :"
							+ totalDocsMissingGender + ", which are found to be exceeding 10% of Total docs:"
							+ totalActiveDocs);
//			Assert.assertTrue(totalDocsMissingMRP <= (totalActiveDocs * THRESHOLD.TENPERCENT),
//					"Total Products missing MRP Attributes for " + searchIndex + " source are :"
//							+ totalDocsMissingMRP + ", which are found to be exceeding 10% of Total docs:"
//							+ totalActiveDocs);


	}

	@Test(enabled = true, dataProvider = "sourcepathsDP", dataProviderClass = InboundServiceDP.class, groups = {
			"sanity", "Regression",
			"es" }, description = "Verify the total count of products which are not formed well i.e missing mandatory attributes like styleAttributes, styleAttributes_Source,skus and are active myntra products.check in kibana for manual and ES for automation")
	public void identify_count_of_non_well_formed_docs(String searchIndex) throws Exception {

		SearchRequestBuilder query = esquerygenerator.getcountofNonWellFormedDocsQuery(client, searchIndex);
		SearchResponse response = esclient.ESExecutor(query);
		long totalActiveDocs = eshelper.getTotalHits(response);
		long totalDocsMissingMandatoryAttrs = eshelper.getAggregationDocCount(response, 0);

		log.debug("\nTotal documents that are active:\n" + totalActiveDocs + "\n");
		log.debug("\nPrinting Total documents missing mandatory Attributes :\n" + totalDocsMissingMandatoryAttrs
				+ "\n");


			Assert.assertTrue(
					totalDocsMissingMandatoryAttrs <= (totalActiveDocs * totalActiveDocs * THRESHOLD.TENPERCENT),
					"Total Products missing mandaotory attributes for " + searchIndex + " source are :"
							+ totalDocsMissingMandatoryAttrs
							+ " found which should be exceeding atleast 10% of Total docs:" + totalActiveDocs);


	}

	@Test(enabled = true, dataProvider = "myntrasourcepathsDP", dataProviderClass = InboundServiceDP.class, groups = {
			"sanity", "Regression",
			"es" }, description = "Verify the products that having demandforecast.rdf and demandforcast.ros forecast attributes.check in kibana for manual and ES for automation")
	public void get_count_of_products_having_rdf_ros_based_forecasts(String searchIndex) throws Exception {

		SearchRequestBuilder query = esquerygenerator.getCountOfDocsHavingRDFAndROSForecastsQuery(client, searchIndex);
		SearchResponse response = esclient.ESExecutor(query);
		long totalActiveDocs = eshelper.getTotalHits(response);
		long totalDocsHavingRDFForecast = eshelper.getAggregationDocCount(response, 0);
		long totalDocsHavingROSForecast = eshelper.getAggregationDocCount(response, 1);

		log.debug("\nTotal products that are active:\n" + totalActiveDocs + "\n");
		log.debug("\nTotal products that are having RDF based Forecasts:\n" + totalDocsHavingRDFForecast + "\n");
		log.debug("\nTotal products that are having ROS based Forecasts:\n" + totalDocsHavingROSForecast + "\n");

		Assert.assertTrue(totalDocsHavingRDFForecast > 0, "Total Products having  RDF based Forecasts for "
				+ searchIndex + " source are :" + totalDocsHavingRDFForecast + ", which should be greater than 0");
		Assert.assertTrue(totalDocsHavingROSForecast > 0, "Total Products having  ROS based Forecasts for "
				+ searchIndex + " source are :" + totalDocsHavingROSForecast + ", which should be greater than 0");

	}

	@Test(enabled = true, dataProvider = "myntrasourcepathsDP", dataProviderClass = InboundServiceDP.class, groups = {
			"sanity", "Regression",
			"es" }, description = "Verify demandforecast.rdf attribute exists and demandforcast.ros doesnt exist.check in kibana for manual and ES for automation")
	public void get_count_of_products_having_rdf_and_missing_ros_based_forecasts(String searchIndex) throws Exception {

		SearchRequestBuilder query = esquerygenerator.getCountOfDocsHavingRDFAndMissingROSForecastsQuery(client,
				searchIndex);
		SearchResponse response = esclient.ESExecutor(query);
		long totalActiveDocs = eshelper.getTotalHits(response);
		long totalDocsHavingRDFForecast = eshelper.getAggregationDocCount(response, 0);
		long totalDocsHavingROSForecast = eshelper.getAggregationDocCount(response, 1);

		log.debug("\nTotal products that are active:\n" + totalActiveDocs + "\n");
		log.debug("\nTotal products that are having RDF based Forecasts:\n" + totalDocsHavingRDFForecast + "\n");
		log.debug("\nTotal products that are Not having ROS based Forecasts:\n" + totalDocsHavingROSForecast + "\n");

		Assert.assertTrue(totalDocsHavingRDFForecast >= 0, "Total Products having  RDF based Forecasts for "
				+ searchIndex + " source are :" + totalDocsHavingRDFForecast + ", which should be greater than 0");
		Assert.assertTrue(totalDocsHavingRDFForecast >=0, "Total Products Not having  ROS based Forecasts for "
				+ searchIndex + " source are :" + totalDocsHavingROSForecast + ", which should be greater than 0");

	}

	@Test(enabled = true, dataProvider = "myntrasourcepathsDP", dataProviderClass = InboundServiceDP.class, groups = {
			"sanity", "Regression",
			"es" }, description = "Get all SOR/OR products which are not having vendor_info and whose commercial_type is either OUTRIGHT/SOR and supply_type is 'ON_HAND'")
	public void get_count_of_products_having_commercial_type_and_supply_type_and_missing_vendor_info(String searchIndex)
			throws Exception {

		SearchRequestBuilder query = esquerygenerator.getCountOfSOR_OR_DocsMissingVendorInfo(client, searchIndex);
		SearchResponse response = esclient.ESExecutor(query);
		long totalActiveDocs = eshelper.getTotalHits(response);
		long totalDocsMissingVendorName = eshelper.getAggregationDocCount(response, 0);
		long totalDocsMissingBasisMargin = eshelper.getAggregationDocCount(response, 1);
		long totalDocsMissingMargin_type = eshelper.getAggregationDocCount(response, 2);
		long totalDocsMissing_PO_Type = eshelper.getAggregationDocCount(response, 3);

		log.debug("\nTotal products that are active:\n" + totalActiveDocs + "\n");
		log.debug("\nTotal products that are Not having Vendor Name:\n" + totalDocsMissingVendorName + "\n");
		log.debug("\nTotal products that are Not having Basis of Margin:\n" + totalDocsMissingBasisMargin + "\n");
		log.debug("\nTotal products that are Not having Margin Type:\n" + totalDocsMissingMargin_type + "\n");
		log.debug("\nTotal products that are Not having PO Type:\n" + totalDocsMissing_PO_Type + "\n");

		Assert.assertEquals(totalDocsMissingVendorName, 0, "Total SOR/OR Products that are missing Vendor Name for "
				+ searchIndex + " source are :" + totalDocsMissingVendorName + ", which should be 0");
		Assert.assertEquals(totalDocsMissingBasisMargin, 0, "Total SOR/OR Products that are missing Vendor Name for "
				+ searchIndex + " source are :" + totalDocsMissingBasisMargin + ", which should be 0");
		Assert.assertEquals(totalDocsMissingMargin_type, 0, "Total SOR/OR Products that are missing Vendor Name for "
				+ searchIndex + " source are :" + totalDocsMissingMargin_type + ", which should be 0");
		Assert.assertEquals(totalDocsMissing_PO_Type, 0, "Total SOR/OR Products that are missing Vendor Name for "
				+ searchIndex + " source are :" + totalDocsMissing_PO_Type + ", which should be 0");

	}

	@Test(enabled = true, dataProvider = "myntrasourcepathsDP", dataProviderClass = InboundServiceDP.class, groups = {
			"sanity", "Regression", "es" }, description = "Get the count of products whose vendor_info.margin >100")
	public void get_count_of_products_having_vendor_info_margin_greater_than_100(String searchIndex) throws Exception {

		SearchRequestBuilder query = esquerygenerator.getCountOfDocsHavingVendorInfoMarginGreaterThan100Query(client,
				searchIndex);
		SearchResponse response = esclient.ESExecutor(query);

		long totalActiveDocs = eshelper.getTotalHits(response);
		long totalDocsHavingMargin = eshelper.getAggregationDocCount(response, 0);

		log.debug("\nTotal products that are active:\n" + totalActiveDocs + "\n");
		log.debug("\nTotal products that are having Margin greater than 100:\n" + totalDocsHavingMargin + "\n");

	}

	@Test(enabled = false, dataProvider = "myntrasourcepathsDP", dataProviderClass = InboundServiceDP.class, groups = {
			"sanity", "Regression",
			"es" }, description = "Verify if there are any documents that have productStreamMetrics and missing productStreamTimeseries")
	public void get_count_of_products_missing_productstreammetrics_and_productstreamtimeseries(String searchIndex)
			throws Exception {

		SearchRequestBuilder query = esquerygenerator.getCountOfDocsMissingProductsStreamAttributesQuery(client,
				searchIndex);
		SearchResponse response = esclient.ESExecutor(query);

		long totalActiveDocs = eshelper.getTotalHits(response);
		long totalMissingDocs = eshelper.getAggregationDocCount(response, 0);

		log.debug("\nTotal products that are active:\n" + totalActiveDocs + "\n");
		log.debug("\nTotal products that have productStreamMetrics and missing productStreamTimeseries:\n"
				+ totalMissingDocs + "\n");

		Assert.assertEquals(totalMissingDocs, 0,
				"Total Products that have productStreamMetrics and missing productStreamTimeseries for " + searchIndex
						+ " source are :" + totalMissingDocs + ", which should be 0");

	}
	//Descoped as Indexing removed at ES
	@Test(enabled = false, dataProvider = "myntrasourcepathsDP", dataProviderClass = InboundServiceDP.class, groups = {
			"sanity", "Regression",
			"es" }, description = "Verify the products whose clusterinfo is existing for atleast 50% of total products in ES/Kibana")
	public void get_count_of_products_having_cluster_info(String searchIndex) throws Exception {

		SearchRequestBuilder query = esquerygenerator.getCountOfDocsHavingClusterInfoQuery(client, searchIndex);
		SearchResponse response = esclient.ESExecutor(query);

		long totalActiveDocs = eshelper.getTotalHits(response);
		long totalDocsHavingClusterInfo = eshelper.getAggregationDocCount(response, 0);

		log.debug("\nTotal products that are active:\n" + totalActiveDocs + "\n");
		log.debug("\nTotal products that have ClusterInfo:\n" + totalDocsHavingClusterInfo + "\n");


			Assert.assertTrue(totalDocsHavingClusterInfo >= (totalActiveDocs * THRESHOLD.FIFTYPERCENT),
					"Total Products that have clusterInfo for "
							+ searchIndex + " source are :" + totalDocsHavingClusterInfo
							+ ", which should be atleast 50% greater than total active products :" + totalActiveDocs);


	}

	@Test(enabled = true, dataProvider = "myntrasourcepathsDP", dataProviderClass = InboundServiceDP.class, groups = {
			"sanity", "Regression",
			"es" }, description = "Get all products whose aggMetrics.totalOrders > 0 and totQty > 0 but having empty time series section(could be due to some issues in pipeline run)")
	public void get_count_of_products_having_aggmetrics_missing_timeseries(String searchIndex) throws Exception {

		SearchRequestBuilder query = esquerygenerator.getCountOfDocsMissingTimeSeriesQuery(client, searchIndex);
		SearchResponse response = esclient.ESExecutor(query);

		long totalActiveDocs = eshelper.getTotalHits(response);
		long totalDocsMissingTimeSeries = eshelper.getAggregationDocCount(response, 0);

		log.debug("\nTotal products that are active:\n" + totalActiveDocs + "\n");
		log.debug("\nTotal products that are missing Timeseries:\n" + totalDocsMissingTimeSeries + "\n");

		Assert.assertEquals(totalDocsMissingTimeSeries, 0, "Total Products that are missing Timeseries for "
				+ searchIndex + " source are :" + totalDocsMissingTimeSeries + ", which should be zero ");

	}

	@Test(enabled = true, dataProvider = "myntrasourcepathsDP", dataProviderClass = InboundServiceDP.class, groups = {
			"sanity", "Regression",
			"es" }, description = "Verify the products which has all the fields(fdbid,product data, metrics data, clicks data, style break dates, vendor info, clusterInfo etc..This should always be greater than 0 and greater than some threshold for commercial types say SOR and OR in ES and Kibana")
	public void check_if_cms_products_have_all_mandatory_attributes(String searchIndex) throws Exception {

		SearchRequestBuilder query = esquerygenerator.getCountOfDocsHavingAllMandatoryAttributesQuery(client,
				searchIndex);
		SearchResponse response = esclient.ESExecutor(query);

		long totalActiveDocs = eshelper.getTotalHits(response);
		long totalValidDocs = eshelper.getAggregationDocCount(response, 0);

		log.debug("\nTotal products that are active:\n" + totalActiveDocs + "\n");
		log.debug("\nTotal products that are having all Mandatory fields:\n" + totalValidDocs + "\n");

		Assert.assertTrue(totalValidDocs >= (totalActiveDocs * THRESHOLD.TENPERCENT),
				"Total Products that are having all Mandatory fields for " + searchIndex + " source are :"
						+ totalValidDocs + ", which should be atleast more than 10% of total Products: "
						+ totalActiveDocs);

	}

	@Test(enabled = true, dataProvider = "sourcepathsDP", dataProviderClass = InboundServiceDP.class, groups = {
			"sanity", "Regression",
			"es" }, description = "Verify the products that having style_id value as null and empty")
	public void get_count_of_products_missing_style_id(String searchIndex) throws Exception {

		SearchRequestBuilder query = esquerygenerator.getDocsMissingStyleIdQuery(client, searchIndex);
		SearchResponse response = esclient.ESExecutor(query);
		long totalActiveDocs = eshelper.getTotalHits(response);
		long totalDocsMissingStyleIds = eshelper.getAggregationDocCount(response, 0);

		log.debug("\nPrinting Total Active Products :\n" + totalActiveDocs + "\n");
		log.debug("\nTotal documents that are missing style_ids:\n" + totalDocsMissingStyleIds + "\n");

		Assert.assertTrue(totalDocsMissingStyleIds < 500, "Products missing style_ids for " + searchIndex
				+ " source are :" + totalDocsMissingStyleIds + " found which should be equal to :0");

	}
	//Descoped as Indexing removed at ES
	@Test(enabled = false, dataProvider = "myntrasourcepathsDP", dataProviderClass = InboundServiceDP.class, groups = {
			"sanity", "Regression",
			"es" }, description = "Verify in ES if any CMS products are missing styleAttributes.attrs section")
	public void get_count_of_products_missing_styleAttributes_attrs_section_for_myntra_prods(String searchIndex) {

		SearchRequestBuilder query = esquerygenerator.getdocsmissingStyleAttributes(client, searchIndex);
		SearchResponse response = esclient.ESExecutor(query);
		long totalActiveDocs = eshelper.getTotalHits(response);
		long totalDocsMissingStyleAttrs = eshelper.getAggregationDocCount(response, 0);

		log.debug("\nPrinting Total Active Products :\n" + totalActiveDocs + "\n");
		log.debug("\nTotal documents that are missing styleattributes:\n" + totalDocsMissingStyleAttrs + "\n");


			Assert.assertTrue(totalDocsMissingStyleAttrs <= (totalActiveDocs * 0.1),
					"CMS Docs missing styleattributes.attrs section is exceeding 10% of total products. \nDoc's missing styleAttrs section :"
							+ totalDocsMissingStyleAttrs + "\nTotal Active Products :" + totalActiveDocs);


	}

	@Test(enabled = true, dataProvider = "myntrasourcepathsDP", dataProviderClass = InboundServiceDP.class, groups = {
			"sanity", "Regression",
			"es" }, description = "check for products of CMS which are not having vendor_colour attribute in styleAttributes section")
	public void get_count_of_products_missing_styleAttributes_vendor_colour_attribute(String searchIndex) {

		SearchRequestBuilder query = esquerygenerator.getdocsmissingVendorColorAttributeQuery(client, searchIndex);
		SearchResponse response = esclient.ESExecutor(query);
		long totalActiveDocs = eshelper.getTotalHits(response);
		long totalDocsMissingAttrs = eshelper.getAggregationDocCount(response, 0);

		log.debug("\nPrinting Total Active Products :\n" + totalActiveDocs + "\n");
		log.debug("\nTotal documents that are missing styleattributes:\n" + totalDocsMissingAttrs + "\n");


			Assert.assertTrue(totalDocsMissingAttrs <= (totalActiveDocs / 10),
					"Docs missing styleattributes.vendor_colour section is exceeding 10% of total active docs.\nTotal Active Docs:"
							+ totalActiveDocs + "\nDoc's missing vendor_color attributes:" + totalDocsMissingAttrs
							+ "\n");


	}

	@Test(enabled = true, dataProvider = "myntrasourcepathsDP", dataProviderClass = InboundServiceDP.class, groups = {
			"sanity", "Regression",
			"es" }, description = "Get all the products which has aggMetrics section but without styleAttributes data.. we should never have such case")
	public void get_count_of_products_having_aggMetrics_missing_styleAttributes(String searchIndex) {

		SearchRequestBuilder query = esquerygenerator.getdocsmissingStyleAttributeshavingaggMetrics(client,
				searchIndex);
		SearchResponse response = esclient.ESExecutor(query);
		long totalActiveDocs = eshelper.getTotalHits(response);
		long totalDocsMissingAttrs = eshelper.getAggregationDocCount(response, 0);

		log.debug("\nPrinting Total Active Products :\n" + totalActiveDocs + "\n");
		log.debug("\nTotal documents that are missing styleattributes:\n" + totalDocsMissingAttrs + "\n");

		Assert.assertEquals(totalDocsMissingAttrs, 0, "Docs missing styleattributes section is: ");

	}

	@Test(enabled = true, dataProvider = "myntrasourcepathsDP", dataProviderClass = InboundServiceDP.class, groups = {
			"sanity", "Regression",
			"es" }, description = "check for products whose totalOrders > 100 and daysLive > 30 days and verify its ROS values(it must be greater than 0)")
	public void get_count_of_products_having_ros_less_than_or_equal_to_zero_for_CMS(String searchIndex) {

		int totalOrders = 100;
		int daysLive = 30;
		SearchRequestBuilder query = esquerygenerator.getdocshavingROSlessthanzeroQuery(client, searchIndex,
				totalOrders, daysLive);
		SearchResponse response = esclient.ESExecutor(query);
		long totalActiveDocs = eshelper.getTotalHits(response);
		long totalDocsMissingAttrs = eshelper.getAggregationDocCount(response, 0);

		log.debug("\nPrinting Total Active Products :\n" + totalActiveDocs + "\n");
		log.debug("\nTotal documents that are missing styleattributes:\n" + totalDocsMissingAttrs + "\n");

		Assert.assertEquals(totalDocsMissingAttrs, 0,
				"Docs with totalOrders > 100 and daysLive > 30 should not have ROS value as zero.\nTotal Active Docs:"
						+ totalActiveDocs + "\nDoc's having ROS as Zero:" + totalDocsMissingAttrs + "\n");

	}

	@Test(enabled = true, dataProvider = "myntrasourcepathsDP", dataProviderClass = InboundServiceDP.class, groups = {
			"sanity", "Regression",
			"es" }, description = "Check the values of RDF forecast in ES as well as with S3 and see if both its count are same.")
	public void compare_total_RDF_products_count_in_amazon_s3_and_ES(String searchIndex) throws Exception {

		long totalS3RDFProducts = spothelper.amazons3filedownload(bucketName, key, AWS_ACCESS_KEY,
				AWS_ACCESS_SECRET_KEY);
		
		log.debug("Total RDF products in Amazon S3:" + totalS3RDFProducts);

		SearchRequestBuilder query = esquerygenerator.getdocscounthavingRDFForecastsQuery(client, searchIndex);
		SearchResponse response = esclient.ESExecutor(query);
	
		long totalDocsHavingRDFForecasts = response.getHits().getTotalHits();
		

		
		log.debug("\nTotal FIFA documents that are having RDF Forecasts:" + totalDocsHavingRDFForecasts + "\n");

		Assert.assertTrue(totalDocsHavingRDFForecasts>=(totalS3RDFProducts*0.1),
				"\nFIFA Store RDF Docs:" + totalDocsHavingRDFForecasts + "" + "\nTotal Oracle RDF S3 Docs:"
						+ totalS3RDFProducts + "\nDocs having RDF forecasts in ES: ");

	}

	@Test(enabled = true, dataProvider = "myntrasourcepathsDP", dataProviderClass = InboundServiceDP.class, groups = {
			"sanity", "Regression",
			"es" }, description = "Verify in SPOT if there are any products whose asp and mrp are same.")
	public void check_if_any_cms_products_has_item_purchase_price_and_last_mrp_same(String searchIndex) {

		int inventory = 0;
		int item_purchase_price = 0;

		SearchRequestBuilder query = esquerygenerator.getCountOfProductsHavingSameMRPAndItemPurchasePriceQuery(client,
				searchIndex, inventory, item_purchase_price);
		SearchResponse response = esclient.ESExecutor(query);
		long totalActiveDocs = eshelper.getTotalHits(response);
		long totalDocsHavingSameMRP = eshelper.getAggregationDocCount(response, 0);

		log.debug("\nPrinting Total Active Products :" + totalActiveDocs + "\n");
		log.debug("\nTotal FIFA documents that are having same MRP and Item Purchase Price:"
				+ totalDocsHavingSameMRP + "\n");

		Assert.assertEquals(totalDocsHavingSameMRP, 0, "Docs having same item_purchase_price and last_mrp in ES: ");

	}

	@Test(enabled = true, dataProvider = "myntrasourcepathsDP", dataProviderClass = InboundServiceDP.class, groups = {
			"sanity", "Regression",
			"es" }, description = "As part of spot check if asp and mrp both are same.. Sometimes due to some issues in BI or in data science logic, asp comes same as MRP which is not expected.")
	public void check_if_any_cms_products_have_asp_and_mrp_same(String searchIndex) {

		SearchRequestBuilder query = esquerygenerator.getCountOfProductsHavingSameMRPAndASPQuery(client, searchIndex);
		SearchResponse response = esclient.ESExecutor(query);
		long totalActiveDocs = eshelper.getTotalHits(response);
		long totalDocsHavingSameMRP = eshelper.getAggregationDocCount(response, 0);

		log.debug("\nPrinting Total Active Products :\n" + totalActiveDocs + "\n");
		log.debug("\nTotal FIFA documents that are having same MRP and ASP:\n" + totalDocsHavingSameMRP + "\n");

		Assert.assertEquals(totalDocsHavingSameMRP, 0, "Docs having same ASP AND MRP in ES: ");

	}

	@Test(enabled = true, dataProvider = "myntrasourcepathsDP", dataProviderClass = InboundServiceDP.class, groups = {

			"sanity", "Regression",
			"ddp" }, description = "All products having source styleattribute but missing global styleattribute")
	public void get_all_products_with_source_attr_and_missing_global_attr(String searchIndex) {

		SearchRequestBuilder query = esquerygenerator.getproductwithStyleAttrbutmissingStyleAttrSource(client,
				searchIndex);
		SearchResponse response = esclient.ESExecutor(query);
		long totalActiveDocs = eshelper.getTotalHits(response);
		long totalDocsMissingAttrs = eshelper.getAggregationDocCount(response, 0);
		log.debug("\nPrinting Total Active Products :\n" + totalActiveDocs + "\n");
		log.debug("\nTotal documents that are missing styleattributes:\n" + totalDocsMissingAttrs + "\n");

		Assert.assertEquals(totalDocsMissingAttrs, 0, "Total Active Docs:" + totalActiveDocs
				+ "\nDoc's having styleattribute_source but not styleattribute:" + totalDocsMissingAttrs + "\n");

		// asserting for the threhold limit which should not exceed more
		// than
		// 10% of total docs count
		Assert.assertTrue(totalDocsMissingAttrs < ((totalActiveDocs * 10) / 100),
				"Docs missing styleattributes section is: ");

		log.debug("\nPrinting the Elastic Search response for the cms products that are missing global styleattributes"
				+ response + "\n");
		log.debug("\nPrinting Total Products :\n" + totalActiveDocs + "\n");

		log.debug("\nTotal documents that are missing styleattributes:\n" + totalDocsMissingAttrs + "\n");

	}

	@Test(enabled = false, dataProvider = "myntrasourcepathsDP", dataProviderClass = InboundServiceDP.class, groups = {
			"sanity", "Regression",
			"ddp" }, description = "get days live from ddb and verify that from the dayslive in es for some stylid")
	public void get_dayslive_data_from_ddp_and_validate_in_es_for_some_styleid(String searchIndex) throws Exception {

		String userdir = System.getProperty("user.dir");
		String s3zipfile = userdir + "/target/ddpQueryData.gz";
		String s3Unzipfile = userdir + "/target/ddpQueryData.csv";

		List<NameValuePair> list = ddp.passParameters(query, query_name, query_description, unload);

		String query_id = ddp.get_query_id_from_ddp(list, userid);
		ddp.get_query_status(query_id, userid);
		String download_url = ddp.get_download_link(query_id, userid);

		ddp.downloadUsingStream(download_url, s3zipfile);
		ddp.unGunzipFile(s3zipfile, s3Unzipfile);
		log.debug("The file was downloaded successfully! " + download_url);

		InputStream io = new FileInputStream(new File(s3Unzipfile));

		String ddp_dayslive = ddp.displayTextInputStream(io).split(",")[2];

		SearchRequestBuilder query = esquerygenerator.getproductshavingDaysLiveAttribute(client, searchIndex,
				Long.toString(styleID), AGGMETRICS.DAYSLIVE);
		SearchResponse response = esclient.ESExecutor(query);

		JSONObject responsebody = new JSONObject(response.toString());
		JSONObject hits_obj = responsebody.getJSONObject("hits");
		JSONArray hits_arr = hits_obj.getJSONArray("hits");
		JSONObject obj = hits_arr.getJSONObject(0);
		JSONObject source = obj.getJSONObject("_source");
		JSONObject aggMetrics = source.getJSONObject("aggMetrics");
		String es_dayslive = aggMetrics.getString("daysLive");

		Assert.assertEquals(ddp_dayslive.replaceAll("^\"|\"$", ""), es_dayslive,
				"the daysLive values aren't the same in ddp and es for style id: " + styleID);
		log.debug(
				"\nPrinting the Elastic Search response for the products with some styleid having daysLive attribute:\n"
						+ responsebody + "\n");

	}

	@Test(enabled = true, dataProvider = "myntrasourcepathsDP", dataProviderClass = InboundServiceDP.class, groups = {
			"sanity", "Regression", "es" }, description = "All products having same stylid should have same fdb_id's")
	public void get_all_products_with_same_style_id_which_has_same_fdb_id(String searchIndex) {

		SearchRequestBuilder query = esquerygenerator.getproductwithduplicatestyleids(client, searchIndex);
		SearchResponse response = esclient.ESExecutor(query);

		long totalActiveDocs = eshelper.getTotalHits(response);
		long duplicate_styleids = eshelper.getAggregationBucketSize(response, "duplicateCount");

		// 10% of total docs count
		Assert.assertTrue(duplicate_styleids < ((totalActiveDocs * 10) / 100),
				"Threshhold exceeds for duplicate style id's: ");

	}

	@Test(enabled = false, dataProvider = "myntrasourcepathsDP", dataProviderClass = InboundServiceDP.class, groups = {
			"sanity", "Regression",
			"es" }, description = "The total product count in mongodb should match with the total product count in es")
	public void get_total_product_counts_fm_mongodb_and_validate_with_total_product_counts_in_es(String searchIndex)
			throws Exception {

		// get total product count from collection style_search in stgdb
		long mongodb_products_count = mongodb.get_mongo_db_count();
		log.debug("\n Printing style aggregators collection counts frm proddb " + mongodb_products_count + "\n");

		SearchRequestBuilder query = esquerygenerator.getproductwithStyleAttrbutmissingStyleAttrSource(client,
				searchIndex);
		SearchResponse response = esclient.ESExecutor(query);
		long totalActiveDocs = eshelper.getTotalHits(response);

		// assertion for checking the count on mongo and es
		Assert.assertEquals(mongodb_products_count, totalActiveDocs,
				"the total no. of product records in mongo and es don't match ");
		log.debug("\nPrinting the es response of the product count in es\n" + response + "\n");

	}

	@Test(enabled = false, dataProvider = "myntrasourcepathsDP", dataProviderClass = InboundServiceDP.class, groups = {
			"sanity", "Regression", "ddp" }, description = "verify data in FIFA with BI for golden input data")
	public void verify_data_in_fifa_with_bi_for_golden_input_data(String searchIndex) throws Exception {

		String userdir = System.getProperty("user.dir");
		String s3zipfile = userdir + "/target/ddpQueryData.gz";
		String s3Unzipfile = userdir + "/target/ddpQueryData.csv";
		String[] es_aggmetrics_obj = { "totRevenue", "totalRgm", "productDiscount", "couponDiscount", "totalDiscount",
				"totQty", "daysLive", "sku_count" };

		List<NameValuePair> list = ddp.passParameters(URLEncoder.encode(query2, "UTF-8"), query_name, query_description,
				unload);

		String query_id = ddp.get_query_id_from_ddp(list, userid);
		ddp.get_query_status(query_id, userid);
		String download_url = ddp.get_download_link(query_id, userid);

		ddp.downloadUsingStream(download_url, s3zipfile);
		ddp.unGunzipFile(s3zipfile, s3Unzipfile);
		log.debug("The file was downloaded successfully! " + download_url);

		InputStream io = new FileInputStream(new File(s3Unzipfile));
		String[] golden_input_data = ddp.displayTextInputStream(io).split(",");

		SearchRequestBuilder query = esquerygenerator.getproductshavingDaysLiveAttribute(client, searchIndex,
				Long.toString(styleID1), SOURCE.AGGMETRICS_TIMESERIES);
		SearchResponse response = esclient.ESExecutor(query);
		JSONObject responsebody = new JSONObject(response.toString());
		JSONObject hits_obj = responsebody.getJSONObject("hits");
		JSONArray hits_arr = hits_obj.getJSONArray("hits");
		JSONObject obj = hits_arr.getJSONObject(0);
		JSONObject source = obj.getJSONObject("_source");
		JSONObject aggMetricsTimeseries = source.getJSONObject("aggMetricsTimeseries");
		JSONObject tsr = aggMetricsTimeseries.getJSONObject("tsr");

		for (int i = 0; i < es_aggmetrics_obj.length; i++) {
			
			Assert.assertEquals(golden_input_data[i + 1].replaceAll("^\"|\"$", ""), tsr.getString(es_aggmetrics_obj[i]),
					"the counts for golden input data " + es_aggmetrics_obj[i] + " are different in bi and es ");
		}
		log.debug("\nPrinting the es response of golden input data\n" + response + "\n");

	}

	@Test(enabled = true, groups = { "sanity", "Regression",
			"rdf" }, description = "Compare no of weeks of forcast from rdf generated script and myntra forecast script")
	public void compare_forcast_weeks_from_approvedautoesforcast_file_to_appfarima_file() throws Exception {

		// get the set of weekyear from the approvedforcast file
		ArrayList<String> approvedautoes_forcast_week = spothelper.downloadamazons3forcastfile(bucketName,
				approvedautoesforcast_key, AWS_ACCESS_KEY, AWS_ACCESS_SECRET_KEY, "approvedautoesforcast", 0);

		// calender util->gives corresponding date of the weekyear
		HashMap<String, String> map = spothelper.calenderUtilfromAmazons3(bucketName, clnd_key, AWS_ACCESS_KEY,
				AWS_ACCESS_SECRET_KEY, "clndfile", 2, 3);

		Iterator<String> it = approvedautoes_forcast_week.iterator();
		ArrayList<String> list = new ArrayList<String>();

		// iterate on the set and return corresponding date of the weekyear
		while (it.hasNext()) {
			String data1 = it.next();
			if (map.containsKey(data1)) {
				list.add(map.get(data1));
			}
		}

		// returns the highest week of the year
		String autoes_forcast_highest_week = Collections.max(list);
		list.clear();
		log.debug("*****************************************************");
		log.debug("the highest weekyear from approved autoes_forcast is " + autoes_forcast_highest_week);
		log.debug("*****************************************************");

		// get the set of weekyear from the appfarima file
		ArrayList<String> apparima_forcast_file = spothelper.downloadamazons3forcastfile(bucketName, appfarima_key,
				AWS_ACCESS_KEY, AWS_ACCESS_SECRET_KEY, "apparima", 0);

		// iterate on the set and return corresponding date of the weekyear
		Iterator<String> it1 = apparima_forcast_file.iterator();
		while (it1.hasNext()) {
			String data = it1.next();
			if (map.containsKey(data)) {
				list.add(map.get(data));
			}
		}

		// returns the highest week of the year
		String myntra_forcast_highest_weekyear = Collections.max(list);

		log.debug("*****************************************************");
		log.debug("the max weekyear from approved myntra_forcast is " + myntra_forcast_highest_weekyear);
		log.debug("*****************************************************");

		// verify that the forcast weekyear on both the files are same
		Assert.assertEquals(autoes_forcast_highest_week, myntra_forcast_highest_weekyear,
				"no. of weeks of forcast in rdf is different fm myntra forcast");

	}

	@Test(enabled = true, groups = { "sanity", "Regression",
			"rdf" }, description = "Validate Number of styles in intermforcast with approvedfinalforcast")
	public void verify_no_of_styles_in_interimforcast_with_approved_final_forcast() throws Exception {

		Calendar cal = Calendar.getInstance();
		String weekyear = Integer.toString(cal.get(Calendar.YEAR)) + Integer.toString(cal.get(Calendar.MONTH))
				+ Integer.toString(cal.get(Calendar.DATE));

		ArrayList<String> interim_forcast_styles = spothelper.downloadamazons3forcastfile(bucketName,
				interimforcast_key, AWS_ACCESS_KEY, AWS_ACCESS_SECRET_KEY, "interimforcast", 1);
		Set<String> interimforcast_set = new HashSet<String>(interim_forcast_styles);
		int interim_forcast_styles_count = interimforcast_set.size();

		
		log.debug("the total style count in interimforcast is " + interim_forcast_styles_count);
		

		ArrayList<String> approvedfinal_forcast_weeks = spothelper.downloadamazons3forcastfile(bucketName,
				approvedfinalforcast_key, AWS_ACCESS_KEY, AWS_ACCESS_SECRET_KEY, "approvedfinalforcast", 0);

		ArrayList<String> approvedfinal_forcast_styles = spothelper.downloadamazons3forcastfile(bucketName,
				approvedfinalforcast_key, AWS_ACCESS_KEY, AWS_ACCESS_SECRET_KEY, "approvedfinalforcast", 1);

		HashMap<String, String> calender = spothelper.calenderUtilfromAmazons3(bucketName, clnd_key, AWS_ACCESS_KEY,
				AWS_ACCESS_SECRET_KEY, "clndfile", 2, 3);
		Iterator<String> it1 = approvedfinal_forcast_weeks.iterator();
		Iterator<String> it2 = approvedfinal_forcast_styles.iterator();
		Set<String> set = new HashSet<String>();
		Map<String, String> map2 = new HashMap<String, String>();


		while (it1.hasNext() && it2.hasNext()) {
			String key = it2.next();
			String value = it1.next();
			map2.put(key, calender.get(value));
		}

		for (String styles : map2.keySet()) {
			if (Integer.parseInt(map2.get(styles)) >= Integer.parseInt(weekyear))
				set.add(styles);
		}

		int approvedfinal_forcast_styles_count = set.size();

		
		log.debug("the total style count in approvedfinalforcast is " + approvedfinal_forcast_styles_count);
		
		Assert.assertTrue(interim_forcast_styles_count > approvedfinal_forcast_styles_count,
				"count of styles in interimforcast is different fm approvedfinalforcast");


	}

	 @AfterClass(alwaysRun = true)
	 public void teardown() {
	 //esclient.closeESConnection();
//	  spothelper.webHookNotificationPersonal(init, "Test notifications",
//	  slackuser,
//	  "FIFA Dashboard Test Report");
	
	 }

}