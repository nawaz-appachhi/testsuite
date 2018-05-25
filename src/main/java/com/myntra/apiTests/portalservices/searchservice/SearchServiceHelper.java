package com.myntra.apiTests.portalservices.searchservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.myntra.apiTests.common.Myntra;
import com.myntra.apiTests.portalservices.commons.CommonUtils;
import org.apache.log4j.Logger;
import org.testng.AssertJUnit;

import com.myntra.lordoftherings.Initialize;
import com.myntra.apiTests.common.APINAME;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.PayloadType;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.apiTests.common.ServiceType;


/**
 * @author shankara.c
 *
 */
public class SearchServiceHelper extends CommonUtils
{

	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(SearchServiceHelper.class);
	static APIUtilities apiUtil= new APIUtilities();
			
	/**
	 * Get expected request
	 * @author jhansi.bai
	 * @param apiName
	 * @param param
	 * @return
	 */
	public RequestGenerator getQueryRequest(APINAME apiName, String param){
		MyntraService service = Myntra.getService(ServiceType.PORTAL_SEARCHSERVICE,
				apiName, init.Configurations);
		APIUtilities utilities = new APIUtilities();
		service.URL = utilities.prepareparameterizedURL(service.URL, param);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);
		System.out.println(service.URL);
		System.out.println(service.Payload);
		return req;
	}
		
	/**
	 * Get expected request
	 * @author jhansi.bai
	 * @param apiName
	 * @param param
	 * @return
	 */
	
	public RequestGenerator getQuerryPayLoadRequest(APINAME apiName, String param1, String param2, String param3){		
		MyntraService service = Myntra.getService(ServiceType.PORTAL_SEARCHSERVICE,
				apiName, init.Configurations, new String[] {param2, param3}, new String[] { param1 },	
				PayloadType.JSON, PayloadType.JSON);
		System.out.println(service.URL);
		System.out.println(service.Payload);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);
		return req;
	}
	
	/**
	 * getStyleInfo Api request
	 */
	
	public RequestGenerator getRequest(APINAME apiName, String param1){
		MyntraService service = Myntra.getService(ServiceType.PORTAL_SEARCHSERVICE, apiName,
				init.Configurations, new String[] {param1},
				PayloadType.JSON, PayloadType.JSON);
		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service);
		System.out.println(service.URL);
		System.out.println(service.Payload);
		
		return req;
	}
	
	
	
	 
	 
	public RequestGenerator cmsPreOrder(String query)
	{
	 MyntraService service = Myntra.getService(
				ServiceType.PORTAL_CATALOGSERVICES, APINAME.CMSPREORDER,
				init.Configurations);
		service.URL = apiUtil.prepareparameterizedURL(service.URL, query);
		System.out.println("SERVICE URL--"+service.URL);
		HashMap<String,String> getParam = new HashMap<>();
		getParam.put("Authorization",
				"Basic WVhCcFlXUnRhVzQ2YlRGOnVkSEpoVWpCamEyVjBNVE1oSXc9PQ==");
		
		
		RequestGenerator req = new RequestGenerator(service, getParam);
		String jsonRes = req.respvalidate.returnresponseasstring();
		//System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());
	 
	 return req; 
	}
	
	public RequestGenerator pdpStyleAPI(String query)
	{
		MyntraService getStyleDataForSingleStyleIdService = Myntra.getService(ServiceType.PORTAL_STYLEAPI, APINAME.GETSTYLEDATAGET, init.Configurations);
		APIUtilities utilities = new APIUtilities();
		getStyleDataForSingleStyleIdService.URL = utilities.prepareparameterizedURL(getStyleDataForSingleStyleIdService.URL, query );
		
		System.out.println("\nPrinting StyleService getStyleDataForSingleStyleId API URL : "+getStyleDataForSingleStyleIdService.URL);
		log.info("\nPrinting StyleService getStyleDataForSingleStyleId API URL : "+getStyleDataForSingleStyleIdService.URL);
		
		System.out.println("Printing StyleService getStyleDataForSingleStyleId API Payload : \n\n"+getStyleDataForSingleStyleIdService.Payload+"\n");
		log.info("Printing StyleService getStyleDataForSingleStyleId API Payload : \n\n"+getStyleDataForSingleStyleIdService.Payload+"\n");
		
		return new RequestGenerator(getStyleDataForSingleStyleIdService);

	}
	
	
	/**
	 * Get expected request
	 * @author jhansi.bai
	 * @param apiName
	 * @param param
	 * @return
	 */
	public RequestGenerator getRequest(APINAME apiName, String param1, String param2){		
		MyntraService service = Myntra.getService(ServiceType.PORTAL_SEARCHSERVICE, apiName,
				init.Configurations, new String[] { param1, param2 },
				PayloadType.JSON, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);
			System.out.println(service.URL);
		System.out.println(service.Payload);
		return req;
	}
	
	/**
	 * Get expected request
	 * @author jhansi.bai
	 * @param apiName
	 * @param param
	 * @return
	 */
	public RequestGenerator getRequest(APINAME apiName){
		MyntraService service = Myntra.getService(ServiceType.PORTAL_SEARCHSERVICE, apiName,init.Configurations);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);
		System.out.println(service.URL);
		return req;
	}
	/**
	 * Get expected request
	 * @author jhansi.bai
	 * @param apiName
	 * @param param
	 * @return
	 */
	public RequestGenerator getRequest(APINAME apiName, String param1, String param2, String param3){		
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_SEARCHSERVICE, apiName,
				init.Configurations, new String[] { param1, param2, param3 },
				PayloadType.JSON, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);
			System.out.println(service.URL);
		System.out.println(service.Payload);
		return req;
	}
	
	/**
	 * Get expected request
	 * @author jhansi.bai
	 * @param apiName
	 * @param param
	 * @return
	 */
	public RequestGenerator getRequest(APINAME apiName, String param1, String param2, String param3, String param4){		
		MyntraService service = Myntra.getService(ServiceType.PORTAL_SEARCHSERVICE, apiName,init.Configurations, new String[] { param1, param2, param3, param4 },PayloadType.JSON, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);
			System.out.println(service.URL);
//		System.out.println(service.Payload);
		return req;
	}
	
	public RequestGenerator getRequest(APINAME apiName, String param1, String param2, String param3, String param4, String param5){		
		MyntraService service = Myntra.getService(ServiceType.PORTAL_SEARCHSERVICE, apiName,init.Configurations, new String[] { param1, param2, param3, param4, param5 },PayloadType.JSON, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);
			System.out.println(service.URL);
//		System.out.println(service.Payload);
		return req;
	}
	
	//---------------------------------------------------------------------------------------------------------
	
	public static String statusBasePath = "status.";
	public static String dataBasePath = "data.";
	public static String searchBasePath = "search.";
	public static String queryBasePath= "queryResult.";
	public static String queryResponse1BasePath= "response1.";
	public static String solrBasePath = "solr_sort_field.";
	public static String guidedNavBasePath= "guidedNavField.";
	
	
	public static String response1BasePath = "response1.";
	public static String filterBasePath= "filters.";
	public static String productBasePath = "products.";
	public static String filterPath= response1BasePath+filterBasePath;
	public static String productPath= response1BasePath+productBasePath;
	
	
	public static String articleAttributeBasePath="all_article_attribute.";
	public static String searchPath = dataBasePath+searchBasePath;
	public static String queryPath = dataBasePath+queryBasePath;
	public static String responsePath = dataBasePath+queryBasePath+queryResponse1BasePath;
	public static String solrPath = dataBasePath+solrBasePath;
	public static String attributePath = dataBasePath+articleAttributeBasePath;
	public static String guidedNavPath = dataBasePath+guidedNavBasePath;


	/**
	 * search service status nodes
	 * @author sneha.deep
	 * @return
	 */
	public static List<String> searchStatusNodes (){
		List<String> addNodes = new ArrayList<>();
		addNodes.add(statusBasePath+"statusCode");
		addNodes.add(statusBasePath+"statusMessage");
		addNodes.add(statusBasePath+"statusType");
		addNodes.add(statusBasePath+"totalCount");
		return addNodes;		
	}
	
	/**
	 * search service data nodes
	 * @author jhansi.bai
	 * @return
	 */
	public static List<String> searchDataNodes (){
		List<String> addNodes = new ArrayList<>();
		addNodes.add(dataBasePath+"id");
		addNodes.add(dataBasePath+"pageUrl");
		addNodes.add(dataBasePath+"parameterizedPageId");
		addNodes.add(dataBasePath+"numberOfResults");
		addNodes.add(dataBasePath+"numberOfTimesDispalyed");
		addNodes.add(dataBasePath+"sort");
		addNodes.add(dataBasePath+"facet");
		return addNodes;		
	}
		
	/**
	 * search service page key data nodes
	 * @author jhansi.bai
	 * @return
	 */
	public static List<String> pageKeyDataNodes(){
		List<String> addNodes = new ArrayList<>();
		addNodes.add(dataBasePath+"id");
		addNodes.add(dataBasePath+"pageKey");
		addNodes.add(dataBasePath+"parameterString");
		addNodes.add(dataBasePath+"parameterHashKey");
		addNodes.add(dataBasePath+"pageDesc");
		return addNodes;		
	}
	
	/**
	 * curatedSearchListByParamPageId data nodes
	 * @author jhansi.bai
	 * @return
	 */
	public static List<String> curatedSearchListByParamPageIdDataNodes(){
		List<String> addNodes = new ArrayList<>();
		addNodes.add(dataBasePath+"id");
		addNodes.add(dataBasePath+"fkParameterizedPageId");
		addNodes.add(dataBasePath+"numberSlots");
		addNodes.add(dataBasePath+"styleIdCsv");
		addNodes.add(dataBasePath+"remainingSlotsRule");
		return addNodes;		
	}
	
	/**
	 * addnewLandingPage data nodes
	 * @author jhansi.bai
	 * @return
	 */
	public static List<String> addnewLandingPageDataNodes(){
		List<String> addNodes = new ArrayList<>();
		addNodes.add(dataBasePath+"success");
		addNodes.add(dataBasePath+"message");
		return addNodes;		
	}
	
	/**
	 * get auto suggest data nodes
	 * @author sneha.deep
	 * @return
	 */
	public static List<String> getAutoSuggestDataNodes(){
		List<String> addNodes = new ArrayList<>();
		addNodes.add(dataBasePath+"id");
		addNodes.add(dataBasePath+"label");
		addNodes.add(dataBasePath+"value");
		addNodes.add(dataBasePath+"count");
		addNodes.add(dataBasePath+"category");
		return addNodes;		
	}
	
	/**
	 * get query data nodes
	 * @author sneha.deep
	 * @return
	 */
	public static List<String> getQueryDataNodes(){
		List<String> addNodes = new ArrayList<>();
		addNodes.add(dataBasePath+"search");
		addNodes.add(dataBasePath+"queryResult");
		addNodes.add(dataBasePath+"synonymMap");
		addNodes.add(dataBasePath+"queryType");
		addNodes.add(dataBasePath+"solr_sort_field");
		addNodes.add(dataBasePath+"totalProductsCount");
		addNodes.add(dataBasePath+"all_article_attribute");
		addNodes.add(dataBasePath+"listGroup");
		return addNodes;		
	}
	
	/**
	 * get query data nodes
	 * @author sneha.deep
	 * @return
	 */
	public static List<String> getQueryDataSearchNodes(){
		List<String> addNodes = new ArrayList<>();
		addNodes.add(searchPath+"query");
		addNodes.add(searchPath+"start");
		addNodes.add(searchPath+"rows");
		addNodes.add(searchPath+"facetField");
		addNodes.add(searchPath+"fq");
		addNodes.add(searchPath+"sort");
		addNodes.add(searchPath+"return_docs");
		addNodes.add(searchPath+"colour_grouping");
		addNodes.add(searchPath+"useCache");
		addNodes.add(searchPath+"facet");
		return addNodes;		
	}
	
	/**
	 * get query result nodes
	 * @author jitender.kumar1
	 * @return
	 */
	public static List<String> getQueryResultNodes()
	{
		List<String> addNodes = new ArrayList<>();
		addNodes.add(queryPath+"response1");                       //$.data.queryResult.response1
		addNodes.add(responsePath+"totalProductsCount");  //$.data.queryResult.response1.totalProductsCount
		addNodes.add(queryResponse1BasePath+"filters");
		addNodes.add(responsePath+"products");
		addNodes.add(responsePath+"filters.article_type_attributes");
//		addNodes.add(responsePath+"curated_products");     // for getQueryGet service
//		addNodes.add(queryResponse1BasePath+"response1");
//		addNodes.add(queryResponse1BasePath+"response1");

		return addNodes;
		
		
	}
	
	/**
	 * get solr nodes
	 * @author jitender.kumar1
	 * @return
	 */
	
	public static List<String> getSolrNodes()
	{
		List<String> addNodes = new ArrayList<>();
		addNodes.add(solrPath+"PRICEA");                       
		addNodes.add(solrPath+"RECENCY");  
		addNodes.add(solrPath+"PRICED");
		addNodes.add(solrPath+"POPULAR");
		addNodes.add(solrPath+"DISCOUNT");

		return addNodes;
	}
	
	
	/**
	 * get search nodes
	 * @author jitender.kumar1
	 * @return
	 */
		public static List<String> getQuerySearchNodes(){
			List<String> addNodes = new ArrayList<>();
			addNodes.add(searchPath+"query");
			addNodes.add(searchPath+"start");
			addNodes.add(searchPath+"rows");
			addNodes.add(searchPath+"facetField");
			//addNodes.add(searchPath+"return_docs");
			addNodes.add(searchPath+"curated_query");
			addNodes.add(searchPath+"fq");
			addNodes.add(searchPath+"sort");
			addNodes.add(searchPath+"return_docs");
			addNodes.add(searchPath+"colour_grouping");
			addNodes.add(searchPath+"useCache");
			addNodes.add(searchPath+"facet");
			return addNodes;		
		}
	
	
	/**
	 * get srticle attribute nodes
	 * @author jitender.kumar1
	 * @return
	 */
	public static List<String> getArticleAttributeNodes()
	{
		List<String> addNodes = new ArrayList<>();
		addNodes.add(attributePath+"Foot Care Accessories");                       
		addNodes.add(attributePath+"Multi Kurta Set");  
		addNodes.add(attributePath+"Sleep Shirts");
		addNodes.add(attributePath+"Nightdress");
		addNodes.add(attributePath+"Churidar and Dupatta");

		return addNodes;
	}
	
	
	/**
	 * get guidednav nodes
	 * @author jitender.kumar1
	 * @return
	 */
	public static List<String> getGuidedNavFieldNodes()
	{
		List<String> addNodes = new ArrayList<>();
		addNodes.add(guidedNavPath+"guidedNavEntries");                       
		addNodes.add(guidedNavPath+"name");  
		
		return addNodes;
	}
	
	
	/**
	 * get  response nodes
	 * @author jitender.kumar1
	 * @return
	 */
	
	
	
	public static List<String> getResponse1Nodes()
	{
		List<String> addNodes = new ArrayList<>();
		addNodes.add(response1BasePath+"totalProductsCount");                       
		addNodes.add(response1BasePath+"filters");
		addNodes.add(response1BasePath+"products");
		
		return addNodes;
	}
	
	
	/**
	 * get  Filter nodes
	 * @author jitender.kumar1
	 * @return
	 */
	public static List<String> getFilterNodes()
	{
		List<String> addNodes = new ArrayList<>();
		addNodes.add(filterPath+"dre_offerType");                       
		addNodes.add(filterPath+"sizes_facet");
		addNodes.add(filterPath+"global_attr_sub_category_facet");
		addNodes.add(filterPath+"brands_filter_facet");
		addNodes.add(filterPath+"discount_percentage");
		addNodes.add(filterPath+"discounted_price");
		addNodes.add(filterPath+"global_attr_gender_string");
		addNodes.add(filterPath+"global_attr_master_category_facet");
		addNodes.add(filterPath+"colour_family_list");
		
		return addNodes;
	}
	
	/**
	 * get  Product nodes
	 * @author jitender.kumar1
	 * @return
	 */
	public static List<String> getProductNodes()
	{
		List<String> addNodes = new ArrayList<>();
		addNodes.add(productPath);                       
		
		return addNodes;
	}
	
	/**
	 * get  Product nodes
	 * @author jitender.kumar1
	 * @change preOrder
	 * @return
	 */
	
	public static List<String> getPreOrderTag()
	{
		List<String> addNodes = new ArrayList<>();
		addNodes.add(productPath+"advanceOrderTag");
		addNodes.add(productPath+"advanceOrderType");
		
		return addNodes;
	}
	
	
	
	
	
}