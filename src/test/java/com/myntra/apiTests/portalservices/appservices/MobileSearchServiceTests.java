package com.myntra.apiTests.portalservices.appservices;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.core.MultivaluedMap;

import com.myntra.apiTests.dataproviders.MobileSearchServiceDP;
import com.myntra.apiTests.InitializeFramework;
import com.myntra.apiTests.portalservices.commons.CommonUtils;
import com.myntra.apiTests.portalservices.mobileappservices.MobileSearchServiceDataNodes;
import com.myntra.apiTests.portalservices.mobileappservices.MobileSearchServiceHelper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import com.jayway.jsonpath.JsonPath;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.MyntAssert;
import com.myntra.lordoftherings.Toolbox;
import com.myntra.lordoftherings.gandalf.RequestGenerator;

/**
 * @author shankara.c
 *
 */
public class MobileSearchServiceTests extends MobileSearchServiceDP
{
	static Initialize init = InitializeFramework.init;
	static Logger log = Logger.getLogger(MobileSearchServiceTests.class);
	static MobileSearchServiceHelper mobileSearchServiceHelper = new MobileSearchServiceHelper();
	static List<String> mobileSearchServiceList = new ArrayList<String>();
	
	/**
	 * This TestCase is used to invoke MobileSearchService getFirstPageOfSearch API and verification for success response
	 * 
	 * @param searchQuery
	 * @param rows
	 * @param returnDocs
	 * @param isFacet
	 * @param successRespCode
	 */
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "MiniRegression", "Regression", "ExhaustiveRegression" },
			dataProvider = "MobileSearchServiceDP_getFirstPageOfSearch_verifySuccessResponse" )
    public void MobileSearchService_getFirstPageOfSearch_verifySuccessResponse(String searchQuery, String rows, String returnDocs, String isFacet, String successRespCode)
    {
        RequestGenerator getFirstPageOfMobileSearchServiceReqGen = mobileSearchServiceHelper.invokeGetFirstPageOfSearch(searchQuery, rows, 
        		returnDocs, isFacet);
        String getFirstPageOfMobileSearchServiceResponse = getFirstPageOfMobileSearchServiceReqGen.respvalidate.returnresponseasstring();
        System.out.println("\nPrinting MobileSearchService getFirstPageOfSearch API response :\n\n"+getFirstPageOfMobileSearchServiceResponse+"\n");
		log.info("\nPrinting MobileSearchService getFirstPageOfSearch API response :\n\n"+getFirstPageOfMobileSearchServiceResponse+"\n");
		 
		MyntAssert.assertEquals("MobileSearchService getFirstPageOfSearch API is not working",  Integer.parseInt(successRespCode), 
				getFirstPageOfMobileSearchServiceReqGen.response.getStatus());
        
        MyntAssert.setJsonResponse(getFirstPageOfMobileSearchServiceResponse);
		MyntAssert.setExpectedAndActualForMyntListen(true);
    }
	
	/**
	 * This TestCase is used to invoke MobileSearchService getFirstPageOfSearch API and verification for headers information in the response
	 * 
	 * @param searchQuery
	 * @param rows
	 * @param returnDocs
	 * @param isFacet
	 * @param successRespCode
	 */
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },
			dataProvider = "MobileSearchServiceDP_getFirstPageOfSearch_verifyResponseHeaders" )
    public void MobileSearchService_getFirstPageOfSearch_verifyResponseHeaders(String searchQuery, String rows, String returnDocs, String isFacet, String successRespCode)
    {
        RequestGenerator getFirstPageOfMobileSearchServiceReqGen = mobileSearchServiceHelper.invokeGetFirstPageOfSearch(searchQuery, rows, 
        		returnDocs, isFacet);
        String getFirstPageOfMobileSearchServiceResponse = getFirstPageOfMobileSearchServiceReqGen.respvalidate.returnresponseasstring();
        System.out.println("\nPrinting MobileSearchService getFirstPageOfSearch API response :\n\n"+getFirstPageOfMobileSearchServiceResponse+"\n");
		log.info("\nPrinting MobileSearchService getFirstPageOfSearch API response :\n\n"+getFirstPageOfMobileSearchServiceResponse+"\n");
		 
		MyntAssert.assertEquals("MobileSearchService getFirstPageOfSearch API is not working",  Integer.parseInt(successRespCode), 
				getFirstPageOfMobileSearchServiceReqGen.response.getStatus());
        
        MultivaluedMap<String, Object> getFirstPageOfSearchResponseHeaders = getFirstPageOfMobileSearchServiceReqGen.response.getHeaders();
        System.out.println("\nMobileSearchService getFirstPageOfSearch API response headers : "+getFirstPageOfSearchResponseHeaders+"\n");
        log.info("\nMobileSearchService getFirstPageOfSearch API response headers : "+getFirstPageOfSearchResponseHeaders+"\n");
      
        MyntAssert.assertEquals("Invalid headers information in MobileSearchService getFirstPageOfSearch API response", "success", 
        		validateResponseHeaders(getFirstPageOfSearchResponseHeaders));
        
        MyntAssert.setJsonResponse(getFirstPageOfMobileSearchServiceResponse);
		MyntAssert.setExpectedAndActualForMyntListen(true);
    }
	
	/**
	 * This TestCase is used to invoke MobileSearchService getFirstPageOfSearch API and verification for meta tag nodes in the response
	 * 
	 * @param searchQuery
	 * @param rows
	 * @param returnDocs
	 * @param isFacet
	 * @param successRespCode
	 */
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },
			dataProvider = "MobileSearchServiceDP_getFirstPageOfSearch_verifyMetaTagNodes" )
    public void MobileSearchService_getFirstPageOfSearch_verifyMetaTagNodes(String searchQuery, String rows, String returnDocs, String isFacet, String successRespCode)
    {
        RequestGenerator getFirstPageOfMobileSearchServiceReqGen = mobileSearchServiceHelper.invokeGetFirstPageOfSearch(searchQuery, rows, 
        		returnDocs, isFacet);
        String getFirstPageOfMobileSearchServiceResponse = getFirstPageOfMobileSearchServiceReqGen.respvalidate.returnresponseasstring();
        System.out.println("\nPrinting MobileSearchService getFirstPageOfSearch API response :\n\n"+getFirstPageOfMobileSearchServiceResponse+"\n");
		log.info("\nPrinting MobileSearchService getFirstPageOfSearch API response :\n\n"+getFirstPageOfMobileSearchServiceResponse+"\n");
		 
		MyntAssert.assertEquals("MobileSearchService getFirstPageOfSearch API is not working",  Integer.parseInt(successRespCode), 
				getFirstPageOfMobileSearchServiceReqGen.response.getStatus());
        
		AssertJUnit.assertTrue("Invalid MetaTag data nodes in MobileSearchService getFirstPageOfSearch API response", 
				getFirstPageOfMobileSearchServiceReqGen.respvalidate.DoesNodesExists(MobileSearchServiceDataNodes.getMobileSearchServiceResponseMetaTagNodes()));
		
		AssertJUnit.assertTrue("MetaTag code data value should be 200 in MobileSearchService getFirstPageOfSearch API response", 
				getFirstPageOfMobileSearchServiceReqGen.respvalidate.GetNodeValue(MobileSearchServiceDataNodes.MOBILE_SEARCH_RESP_META_CODE.getNodePath(), 
						getFirstPageOfMobileSearchServiceResponse).equalsIgnoreCase(successRespCode));
		
		MyntAssert.assertEquals("MetaTag errorDetail data value should be null in MobileSearchService getFirstPageOfSearch API response", "null", 
				getFirstPageOfMobileSearchServiceReqGen.respvalidate.GetNodeValue(
						MobileSearchServiceDataNodes.MOBILE_SEARCH_RESP_META_ERROR_DETAIL.getNodePath(), getFirstPageOfMobileSearchServiceResponse));
		
        MyntAssert.setJsonResponse(getFirstPageOfMobileSearchServiceResponse);
		MyntAssert.setExpectedAndActualForMyntListen(true);
    }
	
	/**
	 * This TestCase is used to invoke MobileSearchService getFirstPageOfSearch API and verification for notification tag nodes in the response
	 * 
	 * @param searchQuery
	 * @param rows
	 * @param returnDocs
	 * @param isFacet
	 * @param successRespCode
	 */
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },
			dataProvider = "MobileSearchServiceDP_getFirstPageOfSearch_verifyNotificationTagNodes" )
    public void MobileSearchService_getFirstPageOfSearch_verifyNotificationTagNodes(String searchQuery, String rows, String returnDocs, String isFacet, 
    		String successRespCode)
    {
        RequestGenerator getFirstPageOfMobileSearchServiceReqGen = mobileSearchServiceHelper.invokeGetFirstPageOfSearch(searchQuery, rows, 
        		returnDocs, isFacet);
        String getFirstPageOfMobileSearchServiceResponse = getFirstPageOfMobileSearchServiceReqGen.respvalidate.returnresponseasstring();
        System.out.println("\nPrinting MobileSearchService getFirstPageOfSearch API response :\n\n"+getFirstPageOfMobileSearchServiceResponse+"\n");
		log.info("\nPrinting MobileSearchService getFirstPageOfSearch API response :\n\n"+getFirstPageOfMobileSearchServiceResponse+"\n");
		 
		MyntAssert.assertEquals("MobileSearchService getFirstPageOfSearch API is not working",  Integer.parseInt(successRespCode), 
				getFirstPageOfMobileSearchServiceReqGen.response.getStatus());
        
		AssertJUnit.assertTrue("Invalid notification tag data nodes in MobileSearchService getFirstPageOfSearch API response", 
				getFirstPageOfMobileSearchServiceReqGen.respvalidate.DoesNodesExists(MobileSearchServiceDataNodes.getMobileSearchServiceResponseNotificationTagNodes()));
		
        MyntAssert.setJsonResponse(getFirstPageOfMobileSearchServiceResponse);
		MyntAssert.setExpectedAndActualForMyntListen(true);
    }
	
	/**
	 * This TestCase is used to invoke MobileSearchService getFirstPageOfSearch API and verification for requestObject tag nodes in the response
	 * 
	 * @param searchQuery
	 * @param rows
	 * @param returnDocs
	 * @param isFacet
	 * @param successRespCode
	 */
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },
			dataProvider = "MobileSearchServiceDP_getFirstPageOfSearch_verifyRequestObjectDataNodes" )
    public void MobileSearchService_getFirstPageOfSearch_verifyRequestObjectDataNodes(String searchQuery, String rows, String returnDocs, String isFacet, 
    		String successRespCode)
    {
        RequestGenerator getFirstPageOfMobileSearchServiceReqGen = mobileSearchServiceHelper.invokeGetFirstPageOfSearch(searchQuery, rows, 
        		returnDocs, isFacet);
        String getFirstPageOfMobileSearchServiceResponse = getFirstPageOfMobileSearchServiceReqGen.respvalidate.returnresponseasstring();
        System.out.println("\nPrinting MobileSearchService getFirstPageOfSearch API response :\n\n"+getFirstPageOfMobileSearchServiceResponse+"\n");
		log.info("\nPrinting MobileSearchService getFirstPageOfSearch API response :\n\n"+getFirstPageOfMobileSearchServiceResponse+"\n");
		 
		AssertJUnit.assertTrue("MetaTag code data value should be 200 in MobileSearchService getFirstPageOfSearch API response", 
				getFirstPageOfMobileSearchServiceReqGen.respvalidate.GetNodeValue(MobileSearchServiceDataNodes.MOBILE_SEARCH_RESP_META_CODE.getNodePath(), 
						getFirstPageOfMobileSearchServiceResponse).equalsIgnoreCase(successRespCode));
		
        if(getFirstPageOfMobileSearchServiceReqGen.respvalidate.DoesNodeExists(MobileSearchServiceDataNodes.MOBILE_SEARCH_RESP_DATA_REQ_OBJECT.getNodePath())){
        	
        	AssertJUnit.assertTrue("Invalid requestObject data nodes in MobileSearchService getFirstPageOfSearch API response", 
    				getFirstPageOfMobileSearchServiceReqGen.respvalidate.DoesNodesExists(MobileSearchServiceDataNodes.getRequestObjectDataTagNodes()));
    		
    		AssertJUnit.assertTrue("request and response rows tag nodes are not same", 
    				getFirstPageOfMobileSearchServiceReqGen.respvalidate.NodeValue(
    						MobileSearchServiceDataNodes.MOBILE_SEARCH_RESP_DATA_REQ_OBJECT_ROWS.getNodePath(), false).equals(rows));
    		
    		AssertJUnit.assertTrue("request and response returnDocs tag nodes are not same", 
    				getFirstPageOfMobileSearchServiceReqGen.respvalidate.NodeValue(
    						MobileSearchServiceDataNodes.MOBILE_SEARCH_RESP_DATA_REQ_OBJECT_RETURN_DOCS.getNodePath(), false).equals(returnDocs));
    		
    		AssertJUnit.assertTrue("request and response isFacet tag nodes are not same", 
    				getFirstPageOfMobileSearchServiceReqGen.respvalidate.NodeValue(
    						MobileSearchServiceDataNodes.MOBILE_SEARCH_RESP_DATA_REQ_OBJECT_FACET.getNodePath(), false).equals(isFacet));
    		
        } else {
        	System.out.println("\nrequestObject data node doesnt exists in MobileSearchService getFirstPageOfSearch API response\n");
        	log.info("\nrequestObject data node doesnt exists in MobileSearchService getFirstPageOfSearch API response\n");
        }
		
        MyntAssert.setJsonResponse(getFirstPageOfMobileSearchServiceResponse);
		MyntAssert.setExpectedAndActualForMyntListen(true);
    }
	
	/**
	 * This TestCase is used to invoke MobileSearchService getFirstPageOfSearch API and verification for requestObject curated_query data nodes in the response
	 * 
	 * @param searchQuery
	 * @param rows
	 * @param returnDocs
	 * @param isFacet
	 * @param successRespCode
	 */
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },
			dataProvider = "MobileSearchServiceDP_getFirstPageOfSearch_verifyRequestObjectWithCuratedQueryDataNodes" )
    public void MobileSearchService_getFirstPageOfSearch_verifyRequestObjectWithCuratedQueryDataNodes(String searchQuery, String rows, String returnDocs, String isFacet, 
    		String successRespCode)
    {
        RequestGenerator getFirstPageOfMobileSearchServiceReqGen = mobileSearchServiceHelper.invokeGetFirstPageOfSearch(searchQuery, rows, 
        		returnDocs, isFacet);
        String getFirstPageOfMobileSearchServiceResponse = getFirstPageOfMobileSearchServiceReqGen.respvalidate.returnresponseasstring();
        System.out.println("\nPrinting MobileSearchService getFirstPageOfSearch API response :\n\n"+getFirstPageOfMobileSearchServiceResponse+"\n");
		log.info("\nPrinting MobileSearchService getFirstPageOfSearch API response :\n\n"+getFirstPageOfMobileSearchServiceResponse+"\n");
		 
		AssertJUnit.assertTrue("MetaTag code data value should be 200 in MobileSearchService getFirstPageOfSearch API response", 
				getFirstPageOfMobileSearchServiceReqGen.respvalidate.GetNodeValue(MobileSearchServiceDataNodes.MOBILE_SEARCH_RESP_META_CODE.getNodePath(), 
						getFirstPageOfMobileSearchServiceResponse).equalsIgnoreCase(successRespCode));
        
		if(getFirstPageOfMobileSearchServiceReqGen.respvalidate.DoesNodeExists(MobileSearchServiceDataNodes.MOBILE_SEARCH_RESP_DATA_REQ_OBJECT.getNodePath())
				&& getFirstPageOfMobileSearchServiceReqGen.respvalidate.DoesNodeExists(
						MobileSearchServiceDataNodes.MOBILE_SEARCH_RESP_DATA_REQ_OBJECT_CURATED_QUERY.getNodePath()))
		{
			AssertJUnit.assertTrue("Invalid requestObject curated_query data nodes in MobileSearchService getFirstPageOfSearch API response", 
					getFirstPageOfMobileSearchServiceReqGen.respvalidate.DoesNodesExists(MobileSearchServiceDataNodes.getRequestObjectWithCuratedQueryDataTagNodes()));
		
			AssertJUnit.assertTrue("request and response rows tag nodes are not same", 
					getFirstPageOfMobileSearchServiceReqGen.respvalidate.NodeValue(
							MobileSearchServiceDataNodes.MOBILE_SEARCH_RESP_DATA_REQ_OBJECT_CURATED_QUERY_ROWS.getNodePath(), false).equals(rows));
		} else {
        	System.out.println("\nrequestObject curated_query data node doesnt exists in MobileSearchService getFirstPageOfSearch API response\n");
        	log.info("\nrequestObject curated_query data node doesnt exists in MobileSearchService getFirstPageOfSearch API response\n");
        }
		
        MyntAssert.setJsonResponse(getFirstPageOfMobileSearchServiceResponse);
		MyntAssert.setExpectedAndActualForMyntListen(true);
    }
	
	
	/**
	 * This TestCase is used to invoke MobileSearchService getFirstPageOfSearch API and verification for filters tag nodes in the response
	 * 
	 * @param searchQuery
	 * @param rows
	 * @param returnDocs
	 * @param isFacet
	 * @param successRespCode
	 */
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },
			dataProvider = "MobileSearchServiceDP_getFirstPageOfSearch_verifyBodyWithFiltersDataNodes" )
    public void MobileSearchService_getFirstPageOfSearch_verifyBodyWithFiltersDataNodes(String searchQuery, String rows, String returnDocs, String isFacet, 
    		String successRespCode)
    {
        RequestGenerator getFirstPageOfMobileSearchServiceReqGen = mobileSearchServiceHelper.invokeGetFirstPageOfSearch(searchQuery, rows, 
        		returnDocs, isFacet);
        String getFirstPageOfMobileSearchServiceResponse = getFirstPageOfMobileSearchServiceReqGen.respvalidate.returnresponseasstring();
        System.out.println("\nPrinting MobileSearchService getFirstPageOfSearch API response :\n\n"+getFirstPageOfMobileSearchServiceResponse+"\n");
		log.info("\nPrinting MobileSearchService getFirstPageOfSearch API response :\n\n"+getFirstPageOfMobileSearchServiceResponse+"\n");
		 
		AssertJUnit.assertTrue("MetaTag code data value should be 200 in MobileSearchService getFirstPageOfSearch API response", 
				getFirstPageOfMobileSearchServiceReqGen.respvalidate.GetNodeValue(MobileSearchServiceDataNodes.MOBILE_SEARCH_RESP_META_CODE.getNodePath(), 
						getFirstPageOfMobileSearchServiceResponse).equalsIgnoreCase(successRespCode));
        
		if(getFirstPageOfMobileSearchServiceReqGen.respvalidate.DoesNodeExists(MobileSearchServiceDataNodes.MOBIEL_SEARCH_RESP_DATA_BODY_FILTERS.getNodePath())){
			
			String responseFilterNodes = getFirstPageOfMobileSearchServiceReqGen.respvalidate.NodeValue(
					MobileSearchServiceDataNodes.MOBIEL_SEARCH_RESP_DATA_BODY_FILTERS.getNodePath(), true);
			
			if(!responseFilterNodes.equals(mobileSearchServiceList.toString())){
				
				AssertJUnit.assertTrue("Invalid filters data nodes in MobileSearchService getFirstPageOfSearch API response", 
						getFirstPageOfMobileSearchServiceReqGen.respvalidate.DoesNodesExists(MobileSearchServiceDataNodes.getBodyWithFiltersDataTagNodes()));
				
			} else {
				System.out.println("\nfilters data nodes are empty in MobileSearchService getFirstPageOfSearch API response\n");
				log.info("\nfilters data nodes are empty in MobileSearchService getFirstPageOfSearch API response\n");
			}
			
		} else {
			System.out.println("\nfilters data node doesnt exists in MobileSearchService getFirstPageOfSearch API response\n");
			log.info("\nfilters data node doesnt exists in MobileSearchService getFirstPageOfSearch API response\n");
		}
		
        MyntAssert.setJsonResponse(getFirstPageOfMobileSearchServiceResponse);
		MyntAssert.setExpectedAndActualForMyntListen(true);
    }
	
	/**
	 * This TestCase is used to invoke MobileSearchService getFirstPageOfSearch API and verification for products data nodes in the response
	 * 
	 * @param searchQuery
	 * @param rows
	 * @param returnDocs
	 * @param isFacet
	 * @param successRespCode
	 */
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },
			dataProvider = "MobileSearchServiceDP_getFirstPageOfSearch_verifyBodyWithProductsDataNodes" )
    public void MobileSearchService_getFirstPageOfSearch_verifyBodyWithProductsDataNodes(String searchQuery, String rows, String returnDocs, String isFacet, 
    		String successRespCode)
    {
        RequestGenerator getFirstPageOfMobileSearchServiceReqGen = mobileSearchServiceHelper.invokeGetFirstPageOfSearch(searchQuery, rows, 
        		returnDocs, isFacet);
        String getFirstPageOfMobileSearchServiceResponse = getFirstPageOfMobileSearchServiceReqGen.respvalidate.returnresponseasstring();
        System.out.println("\nPrinting MobileSearchService getFirstPageOfSearch API response :\n\n"+getFirstPageOfMobileSearchServiceResponse+"\n");
		log.info("\nPrinting MobileSearchService getFirstPageOfSearch API response :\n\n"+getFirstPageOfMobileSearchServiceResponse+"\n");
		 
		AssertJUnit.assertTrue("MetaTag code data value should be 200 in MobileSearchService getFirstPageOfSearch API response", 
				getFirstPageOfMobileSearchServiceReqGen.respvalidate.GetNodeValue(MobileSearchServiceDataNodes.MOBILE_SEARCH_RESP_META_CODE.getNodePath(), 
						getFirstPageOfMobileSearchServiceResponse).equalsIgnoreCase(successRespCode));
        
		if(getFirstPageOfMobileSearchServiceReqGen.respvalidate.DoesNodeExists(MobileSearchServiceDataNodes.MOBILE_SEARCH_RESP_DATA_BODY_PRODUCTS.getNodePath())){
			
			String responseProductNodes = getFirstPageOfMobileSearchServiceReqGen.respvalidate.NodeValue(
					MobileSearchServiceDataNodes.MOBILE_SEARCH_RESP_DATA_BODY_PRODUCTS.getNodePath(), true);
			
			if(!responseProductNodes.equals(mobileSearchServiceList.toString())){
				
				AssertJUnit.assertTrue("Invalid products data nodes in MobileSearchService getFirstPageOfSearch API response", 
						getFirstPageOfMobileSearchServiceReqGen.respvalidate.DoesNodesExists(MobileSearchServiceDataNodes.getBodyWithProductsDataTagNodes()));
				
			} else {
				System.out.println("\nproducts data nodes are empty in MobileSearchService getFirstPageOfSearch API response\n");
				log.info("\nproducts data nodes are empty in MobileSearchService getFirstPageOfSearch API response\n");
			}
			
		} else {
			System.out.println("\nproducts data nodes doesnt exists in MobileSearchService getFirstPageOfSearch API response\n");
			log.info("\nproducts data nodes doesnt exists in MobileSearchService getFirstPageOfSearch API response\n");
		}
		
        MyntAssert.setJsonResponse(getFirstPageOfMobileSearchServiceResponse);
		MyntAssert.setExpectedAndActualForMyntListen(true);
    }
	
	/**
	 * This TestCase is used to invoke MobileSearchService getFirstPageOfSearch API and verification for success response
	 * and invoke  MobileSearchService getNextPageOfSearch API and verification for success response
	 * 
	 * @param searchQuery
	 * @param firstRows
	 * @param returnDocs
	 * @param isFacet
	 * @param action
	 * @param nextRows
	 * @param successRespCode
	 */
	@Test(groups={ "Smoke", "Sanity", "ProdSanity", "MiniRegression", "Regression", "ExhaustiveRegression" }, 
			dataProvider="MobileSearchServiceDP_getNextPageOfSearch_verifySuccessResponse")
    public void MobileSearchService_getNextPageOfSearch_verifySuccessResponse(String searchQuery, String firstRows, String returnDocs, String isFacet, 
    		String action, String nextRows, String successRespCode)
    {
		// invoke getFirstPageOfSearch API
		RequestGenerator getFirstPageOfMobileSearchServiceReqGen = mobileSearchServiceHelper.invokeGetFirstPageOfSearch(searchQuery, firstRows, 
				returnDocs, isFacet);
		String getFirstPageOfMobileSearchServiceResponse = getFirstPageOfMobileSearchServiceReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting MobileSearchService getFirstPageOfSearch API response :\n\n"+getFirstPageOfMobileSearchServiceResponse+"\n");
		log.info("\nPrinting MobileSearchService getFirstPageOfSearch API response :\n\n"+getFirstPageOfMobileSearchServiceResponse+"\n");
			
		MyntAssert.assertEquals("MobileSearchService getFirstPageOfSearch API is not working",  Integer.parseInt(successRespCode), 
				getFirstPageOfMobileSearchServiceReqGen.response.getStatus());
		
		String requestObj = JsonPath.read(getFirstPageOfMobileSearchServiceResponse, "$.data.requestObj").toString();	
		
		// invoke getNextPageOfSearch API with requestObject
		RequestGenerator getNextPageOfMobileSearchServiceReqGen = mobileSearchServiceHelper.invokeGetNextPageOfSearch(action, nextRows, requestObj); 
		String getNextPageOfMobileSearchServiceResponse = getNextPageOfMobileSearchServiceReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting MobileSearchService getNextPageOfSearch API response :\n\n"+getNextPageOfMobileSearchServiceResponse+"\n");
		log.info("\nPrinting MobileSearchService getNextPageOfSearch API response :\n\n"+getNextPageOfMobileSearchServiceResponse+"\n");
		
		MyntAssert.assertEquals("MobileSearchService getNextPageOfSearch API is not working",  Integer.parseInt(successRespCode), 
				getNextPageOfMobileSearchServiceReqGen.response.getStatus());
        
        MyntAssert.setJsonResponse(getNextPageOfMobileSearchServiceResponse);
		MyntAssert.setExpectedAndActualForMyntListen(true);
		
    }
	
	/**
	 * This TestCase is used to invoke MobileSearchService getFirstPageOfSearch API and verification for success response
	 * and invoke MobileSearchService getNextPageOfSearch API and verification for headers information in the response
	 * 
	 * @param searchQuery
	 * @param firstRows
	 * @param returnDocs
	 * @param isFacet
	 * @param action
	 * @param nextRows
	 * @param successRespCode
	 */
	@Test(groups={ "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" }, 
			dataProvider="MobileSearchServiceDP_getNextPageOfSearch_verifyResponseHeaders")
    public void MobileSearchService_getNextPageOfSearch_verifyResponseHeaders(String searchQuery, String firstRows, String returnDocs, String isFacet, 
    		String action, String nextRows, String successRespCode)
    {
		// invoke getFirstPageOfSearch API
		RequestGenerator getFirstPageOfMobileSearchServiceReqGen = mobileSearchServiceHelper.invokeGetFirstPageOfSearch(searchQuery, firstRows, 
				returnDocs, isFacet);
		String getFirstPageOfMobileSearchServiceResponse = getFirstPageOfMobileSearchServiceReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting MobileSearchService getFirstPageOfSearch API response :\n\n"+getFirstPageOfMobileSearchServiceResponse+"\n");
		log.info("\nPrinting MobileSearchService getFirstPageOfSearch API response :\n\n"+getFirstPageOfMobileSearchServiceResponse+"\n");
			
		MyntAssert.assertEquals("MobileSearchService getFirstPageOfSearch API is not working",  Integer.parseInt(successRespCode), 
				getFirstPageOfMobileSearchServiceReqGen.response.getStatus());
		
		String requestObj = JsonPath.read(getFirstPageOfMobileSearchServiceResponse, "$.data.requestObj").toString();	
		
		// invoke getNextPageOfSearch API with requestObject
		RequestGenerator getNextPageOfMobileSearchServiceReqGen = mobileSearchServiceHelper.invokeGetNextPageOfSearch(action, nextRows, requestObj); 
		String getNextPageOfMobileSearchServiceResponse = getNextPageOfMobileSearchServiceReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting MobileSearchService getNextPageOfSearch API response :\n\n"+getNextPageOfMobileSearchServiceResponse+"\n");
		log.info("\nPrinting MobileSearchService getNextPageOfSearch API response :\n\n"+getNextPageOfMobileSearchServiceResponse+"\n");
		
		MyntAssert.assertEquals("MobileSearchService getNextPageOfSearch API is not working",  Integer.parseInt(successRespCode), 
				getNextPageOfMobileSearchServiceReqGen.response.getStatus());
		
        MultivaluedMap<String, Object> getNextPageOfSearchResponseHeaders = getNextPageOfMobileSearchServiceReqGen.response.getHeaders();
        System.out.println("\nMobileSearchService getNextPageOfSearch API response headers : "+getNextPageOfSearchResponseHeaders+"\n");
        log.info("\nMobileSearchService getNextPageOfSearch API response headers : "+getNextPageOfSearchResponseHeaders+"\n");
        
        MyntAssert.assertEquals("Invalid headers information in MobileSearchService getNextPageOfSearch API response", "success", 
        		validateResponseHeaders(getNextPageOfSearchResponseHeaders));
        
        MyntAssert.setJsonResponse(getNextPageOfMobileSearchServiceResponse);
		MyntAssert.setExpectedAndActualForMyntListen(true);
		
    }
	
	/**
	 * This TestCase is used to invoke MobileSearchService getFirstPageOfSearch API and verification for success response
	 * and invoke MobileSearchService getNextPageOfSearch API and verification for meta tag nodes in the response
	 * 
	 * @param searchQuery
	 * @param firstRows
	 * @param returnDocs
	 * @param isFacet
	 * @param action
	 * @param nextRows
	 * @param successRespCode
	 */
	@Test(groups={ "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" }, 
			dataProvider="MobileSearchServiceDP_getNextPageOfSearch_verifyMetaTagNodes")
    public void MobileSearchService_getNextPageOfSearch_verifyMetaTagNodes(String searchQuery, String firstRows, String returnDocs, String isFacet, 
    		String action, String nextRows, String successRespCode)
    {
		// invoke getFirstPageOfSearch API
		RequestGenerator getFirstPageOfMobileSearchServiceReqGen = mobileSearchServiceHelper.invokeGetFirstPageOfSearch(searchQuery, firstRows, 
				returnDocs, isFacet);
		String getFirstPageOfMobileSearchServiceResponse = getFirstPageOfMobileSearchServiceReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting MobileSearchService getFirstPageOfSearch API response :\n\n"+getFirstPageOfMobileSearchServiceResponse+"\n");
		log.info("\nPrinting MobileSearchService getFirstPageOfSearch API response :\n\n"+getFirstPageOfMobileSearchServiceResponse+"\n");
			
		MyntAssert.assertEquals("MobileSearchService getFirstPageOfSearch API is not working",  Integer.parseInt(successRespCode), 
				getFirstPageOfMobileSearchServiceReqGen.response.getStatus());
		
		String requestObj = JsonPath.read(getFirstPageOfMobileSearchServiceResponse, "$.data.requestObj").toString();
		
		// invoke getNextPageOfSearch API with requestObject
		RequestGenerator getNextPageOfMobileSearchServiceReqGen = mobileSearchServiceHelper.invokeGetNextPageOfSearch(action, nextRows, requestObj); 
		String getNextPageOfMobileSearchServiceResponse = getNextPageOfMobileSearchServiceReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting MobileSearchService getNextPageOfSearch API response :\n\n"+getNextPageOfMobileSearchServiceResponse+"\n");
		log.info("\nPrinting MobileSearchService getNextPageOfSearch API response :\n\n"+getNextPageOfMobileSearchServiceResponse+"\n");
		
		MyntAssert.assertEquals("MobileSearchService getNextPageOfSearch API is not working",  Integer.parseInt(successRespCode), 
				getNextPageOfMobileSearchServiceReqGen.response.getStatus());
        
		AssertJUnit.assertTrue("Invalid MetaTag data nodes in MobileSearchService getNextPageOfSearch API response", 
				getNextPageOfMobileSearchServiceReqGen.respvalidate.DoesNodesExists(MobileSearchServiceDataNodes.getMobileSearchServiceResponseMetaTagNodes()));
		
		AssertJUnit.assertTrue("MetaTag code data value should be 200 in MobileSearchService getNextPageOfSearch API response", 
				getNextPageOfMobileSearchServiceReqGen.respvalidate.GetNodeValue(MobileSearchServiceDataNodes.MOBILE_SEARCH_RESP_META_CODE.getNodePath(), 
						getNextPageOfMobileSearchServiceResponse).equalsIgnoreCase(successRespCode));
		
		MyntAssert.assertEquals("MetaTag errorDetail data value should be null in MobileSearchService getNextPageOfSearch API response", "null", 
				getNextPageOfMobileSearchServiceReqGen.respvalidate.GetNodeValue(MobileSearchServiceDataNodes.MOBILE_SEARCH_RESP_META_ERROR_DETAIL.getNodePath(),
						getNextPageOfMobileSearchServiceResponse));
		
        MyntAssert.setJsonResponse(getNextPageOfMobileSearchServiceResponse);
		MyntAssert.setExpectedAndActualForMyntListen(true);
		
    }
	
	/**
	 * This TestCase is used to invoke MobileSearchService getFirstPageOfSearch API and verification for success response
	 * and invoke MobileSearchService getNextPageOfSearch API and verification for notification tag nodes in the response
	 * 
	 * @param searchQuery
	 * @param firstRows
	 * @param returnDocs
	 * @param isFacet
	 * @param action
	 * @param nextRows
	 * @param successRespCode
	 */
	@Test(groups={ "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" }, 
			dataProvider="MobileSearchServiceDP_getNextPageOfSearch_verifyNotificationTagNodes")
    public void MobileSearchService_getNextPageOfSearch_verifyNotificationTagNodes(String searchQuery, String firstRows, String returnDocs, String isFacet, 
    		String action, String nextRows, String successRespCode)
    {
		// invoke getFirstPageOfSearch API
		RequestGenerator getFirstPageOfMobileSearchServiceReqGen = mobileSearchServiceHelper.invokeGetFirstPageOfSearch(searchQuery, firstRows, 
				returnDocs, isFacet);
		String getFirstPageOfMobileSearchServiceResponse = getFirstPageOfMobileSearchServiceReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting MobileSearchService getFirstPageOfSearch API response :\n\n"+getFirstPageOfMobileSearchServiceResponse+"\n");
		log.info("\nPrinting MobileSearchService getFirstPageOfSearch API response :\n\n"+getFirstPageOfMobileSearchServiceResponse+"\n");
			
		MyntAssert.assertEquals("MobileSearchService getFirstPageOfSearch API is not working",  Integer.parseInt(successRespCode), 
				getFirstPageOfMobileSearchServiceReqGen.response.getStatus());
		
		String requestObj = JsonPath.read(getFirstPageOfMobileSearchServiceResponse, "$.data.requestObj").toString();
		
		// invoke getNextPageOfSearch API with requestObject
		RequestGenerator getNextPageOfMobileSearchServiceReqGen = mobileSearchServiceHelper.invokeGetNextPageOfSearch(action, nextRows, requestObj); 
		String getNextPageOfMobileSearchServiceResponse = getNextPageOfMobileSearchServiceReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting MobileSearchService getNextPageOfSearch API response :\n\n"+getNextPageOfMobileSearchServiceResponse+"\n");
		log.info("\nPrinting MobileSearchService getNextPageOfSearch API response :\n\n"+getNextPageOfMobileSearchServiceResponse+"\n");
		
		MyntAssert.assertEquals("MobileSearchService getNextPageOfSearch API is not working",  Integer.parseInt(successRespCode), 
				getNextPageOfMobileSearchServiceReqGen.response.getStatus());
        
		AssertJUnit.assertTrue("Invalid notification tag data nodes in MobileSearchService getNextPageOfSearch API response", 
				getNextPageOfMobileSearchServiceReqGen.respvalidate.DoesNodesExists(MobileSearchServiceDataNodes.getMobileSearchServiceResponseNotificationTagNodes()));
		
        MyntAssert.setJsonResponse(getNextPageOfMobileSearchServiceResponse);
		MyntAssert.setExpectedAndActualForMyntListen(true);
		
    }
	
	/**
	 * This TestCase is used to invoke MobileSearchService getFirstPageOfSearch API and verification for success response
	 * and invoke MobileSearchService getNextPageOfSearch API and verification for requestObject tag nodes in the response
	 * 
	 * @param searchQuery
	 * @param firstRows
	 * @param returnDocs
	 * @param isFacet
	 * @param action
	 * @param nextRows
	 * @param successRespCode
	 */
	@Test(groups={ "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" }, 
			dataProvider="MobileSearchServiceDP_getNextPageOfSearch_verifyRequestObjectDataNodes")
    public void MobileSearchService_getNextPageOfSearch_verifyRequestObjectDataNodes(String searchQuery, String firstRows, String returnDocs, String isFacet, 
    		String action, String nextRows, String successRespCode)
    {
		// invoke getFirstPageOfSearch API
		RequestGenerator getFirstPageOfMobileSearchServiceReqGen = mobileSearchServiceHelper.invokeGetFirstPageOfSearch(searchQuery, firstRows, 
				returnDocs, isFacet);
		String getFirstPageOfMobileSearchServiceResponse = getFirstPageOfMobileSearchServiceReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting MobileSearchService getFirstPageOfSearch API response :\n\n"+getFirstPageOfMobileSearchServiceResponse+"\n");
		log.info("\nPrinting MobileSearchService getFirstPageOfSearch API response :\n\n"+getFirstPageOfMobileSearchServiceResponse+"\n");
			
		MyntAssert.assertEquals("MobileSearchService getFirstPageOfSearch API is not working",  Integer.parseInt(successRespCode), 
				getFirstPageOfMobileSearchServiceReqGen.response.getStatus());
		
		String requestObj = JsonPath.read(getFirstPageOfMobileSearchServiceResponse, "$.data.requestObj").toString();
		
		// invoke getNextPageOfSearch API with requestObject
		RequestGenerator getNextPageOfMobileSearchServiceReqGen = mobileSearchServiceHelper.invokeGetNextPageOfSearch(action, nextRows, requestObj); 
		String getNextPageOfMobileSearchServiceResponse = getNextPageOfMobileSearchServiceReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting MobileSearchService getNextPageOfSearch API response :\n\n"+getNextPageOfMobileSearchServiceResponse+"\n");
		log.info("\nPrinting MobileSearchService getNextPageOfSearch API response :\n\n"+getNextPageOfMobileSearchServiceResponse+"\n");
		
		AssertJUnit.assertTrue("MetaTag code data value should be 200 in MobileSearchService getNextPageOfSearch API response", 
				getNextPageOfMobileSearchServiceReqGen.respvalidate.GetNodeValue(MobileSearchServiceDataNodes.MOBILE_SEARCH_RESP_META_CODE.getNodePath(), 
						getNextPageOfMobileSearchServiceResponse).equalsIgnoreCase(successRespCode));
        
		if(getNextPageOfMobileSearchServiceReqGen.respvalidate.DoesNodeExists(MobileSearchServiceDataNodes.MOBILE_SEARCH_RESP_DATA_REQ_OBJECT.getNodePath())){
			
			AssertJUnit.assertTrue("Invalid RequestObject tag data nodes in MobileSearchService getNextPageOfSearch API response", 
					getNextPageOfMobileSearchServiceReqGen.respvalidate.DoesNodesExists(MobileSearchServiceDataNodes.getRequestObjectDataTagNodes()));
			
			AssertJUnit.assertTrue("request and response start tag nodes are not same", 
					getNextPageOfMobileSearchServiceReqGen.respvalidate.NodeValue(
							MobileSearchServiceDataNodes.MOBILE_SEARCH_RESP_DATA_REQ_OBJECT_START.getNodePath(), false).equals(firstRows));
			
			MyntAssert.assertEquals("request and response returnDocs tag nodes are not same", 
					getNextPageOfMobileSearchServiceReqGen.respvalidate.NodeValue(
							MobileSearchServiceDataNodes.MOBILE_SEARCH_RESP_DATA_REQ_OBJECT_RETURN_DOCS.getNodePath(), false), returnDocs);
			
			MyntAssert.assertEquals("request and response isFacet tag nodes are not same", 
					getNextPageOfMobileSearchServiceReqGen.respvalidate.NodeValue(
							MobileSearchServiceDataNodes.MOBILE_SEARCH_RESP_DATA_REQ_OBJECT_FACET.getNodePath(), false), isFacet);
			
		} else {
			System.out.println("\nrequestObject data nodes doesnt exists in MobileSearchService getNextPageOfSearch API response\n");
			log.info("\nrequestObject data nodes doesnt exists in MobileSearchService getNextPageOfSearch API response\n");
		}
		
        MyntAssert.setJsonResponse(getNextPageOfMobileSearchServiceResponse);
		MyntAssert.setExpectedAndActualForMyntListen(true);
		
    }
	
	/**
	 * This TestCase is used to invoke MobileSearchService getFirstPageOfSearch API and verification for success response
	 * and invoke MobileSearchService getNextPageOfSearch API and verification for requestObject curated_query tag nodes in the response
	 * 
	 * @param searchQuery
	 * @param firstRows
	 * @param returnDocs
	 * @param isFacet
	 * @param action
	 * @param nextRows
	 * @param successRespCode
	 */
	@Test(groups={ "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" }, 
			dataProvider="MobileSearchServiceDP_getNextPageOfSearch_verifyRequestObjectWithCuratedQueryDataNodes")
    public void MobileSearchService_getNextPageOfSearch_verifyRequestObjectWithCuratedQueryDataNodes(String searchQuery, String firstRows, String returnDocs, 
    		String isFacet, String action, String nextRows, String successRespCode)
    {
		// invoke getFirstPageOfSearch API
		RequestGenerator getFirstPageOfMobileSearchServiceReqGen = mobileSearchServiceHelper.invokeGetFirstPageOfSearch(searchQuery, firstRows, 
				returnDocs, isFacet);
		String getFirstPageOfMobileSearchServiceResponse = getFirstPageOfMobileSearchServiceReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting MobileSearchService getFirstPageOfSearch API response :\n\n"+getFirstPageOfMobileSearchServiceResponse+"\n");
		log.info("\nPrinting MobileSearchService getFirstPageOfSearch API response :\n\n"+getFirstPageOfMobileSearchServiceResponse+"\n");
			
		MyntAssert.assertEquals("MobileSearchService getFirstPageOfSearch API is not working",  Integer.parseInt(successRespCode), 
				getFirstPageOfMobileSearchServiceReqGen.response.getStatus());
		
		String requestObj = JsonPath.read(getFirstPageOfMobileSearchServiceResponse, "$.data.requestObj").toString();
		
		// invoke getNextPageOfSearch API with requestObject
		RequestGenerator getNextPageOfMobileSearchServiceReqGen = mobileSearchServiceHelper.invokeGetNextPageOfSearch(action, nextRows, requestObj); 
		String getNextPageOfMobileSearchServiceResponse = getNextPageOfMobileSearchServiceReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting MobileSearchService getNextPageOfSearch API response :\n\n"+getNextPageOfMobileSearchServiceResponse+"\n");
		log.info("\nPrinting MobileSearchService getNextPageOfSearch API response :\n\n"+getNextPageOfMobileSearchServiceResponse+"\n");
		
		AssertJUnit.assertTrue("MetaTag code data value should be 200 in MobileSearchService getNextPageOfSearch API response", 
				getNextPageOfMobileSearchServiceReqGen.respvalidate.GetNodeValue(MobileSearchServiceDataNodes.MOBILE_SEARCH_RESP_META_CODE.getNodePath(), 
						getNextPageOfMobileSearchServiceResponse).equalsIgnoreCase(successRespCode));
		
		if(getNextPageOfMobileSearchServiceReqGen.respvalidate.DoesNodeExists(MobileSearchServiceDataNodes.MOBILE_SEARCH_RESP_DATA_REQ_OBJECT.getNodePath())
				&& getNextPageOfMobileSearchServiceReqGen.respvalidate.DoesNodeExists(
						MobileSearchServiceDataNodes.MOBILE_SEARCH_RESP_DATA_REQ_OBJECT_CURATED_QUERY.getNodePath())){

			AssertJUnit.assertTrue("Invalid requestObject curated_query data nodes in MobileSearchService getNextPageOfSearch API response", 
					getNextPageOfMobileSearchServiceReqGen.respvalidate.DoesNodesExists(MobileSearchServiceDataNodes.getRequestObjectWithCuratedQueryDataTagNodes()));
			
			MyntAssert.assertEquals("request and response start tag nodes are not same", 
					getNextPageOfMobileSearchServiceReqGen.respvalidate.NodeValue(
							MobileSearchServiceDataNodes.MOBILE_SEARCH_RESP_DATA_REQ_OBJECT_CURATED_QUERY_START.getNodePath(), false), nextRows);
			
		} else {
			System.out.println("\nrequestObject curated_query data nodes doesnt exists in MobileSearchService getNextPageOfSearch API response\n");
			log.info("\nrequestObject curated_query data nodes doesnt exists in MobileSearchService getNextPageOfSearch API response\n");
		}
		
        MyntAssert.setJsonResponse(getNextPageOfMobileSearchServiceResponse);
		MyntAssert.setExpectedAndActualForMyntListen(true);
		
    }
	
	/**
	 * This TestCase is used to invoke MobileSearchService getFirstPageOfSearch API and verification for success response
	 * and invoke MobileSearchService getNextPageOfSearch API and verification for filters data tag nodes in the response 
	 * 
	 * @param searchQuery
	 * @param firstRows
	 * @param returnDocs
	 * @param isFacet
	 * @param action
	 * @param nextRows
	 * @param successRespCode
	 */
	@Test(groups={ "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" }, 
			dataProvider="MobileSearchServiceDP_getNextPageOfSearch_verifyBodyWithFiltersDataNodes")
    public void MobileSearchService_getNextPageOfSearch_verifyBodyWithFiltersDataNodes(String searchQuery, String firstRows, String returnDocs, String isFacet, 
    		String action, String nextRows, String successRespCode)
    {
		// invoke getFirstPageOfSearch API
		RequestGenerator getFirstPageOfMobileSearchServiceReqGen = mobileSearchServiceHelper.invokeGetFirstPageOfSearch(searchQuery, firstRows, 
				returnDocs, isFacet);
		String getFirstPageOfMobileSearchServiceResponse = getFirstPageOfMobileSearchServiceReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting MobileSearchService getFirstPageOfSearch API response :\n\n"+getFirstPageOfMobileSearchServiceResponse+"\n");
		log.info("\nPrinting MobileSearchService getFirstPageOfSearch API response :\n\n"+getFirstPageOfMobileSearchServiceResponse+"\n");
			
		MyntAssert.assertEquals("MobileSearchService getFirstPageOfSearch API is not working",  Integer.parseInt(successRespCode), 
				getFirstPageOfMobileSearchServiceReqGen.response.getStatus());
		
		String requestObj = JsonPath.read(getFirstPageOfMobileSearchServiceResponse, "$.data.requestObj").toString();
		
		// invoke getNextPageOfSearch API with requestObject
		RequestGenerator getNextPageOfMobileSearchServiceReqGen = mobileSearchServiceHelper.invokeGetNextPageOfSearch(action, nextRows, requestObj); 
		String getNextPageOfMobileSearchServiceResponse = getNextPageOfMobileSearchServiceReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting MobileSearchService getNextPageOfSearch API response :\n\n"+getNextPageOfMobileSearchServiceResponse+"\n");
		log.info("\nPrinting MobileSearchService getNextPageOfSearch API response :\n\n"+getNextPageOfMobileSearchServiceResponse+"\n");
		
		AssertJUnit.assertTrue("MetaTag code data value should be 200 in MobileSearchService getNextPageOfSearch API response", 
				getNextPageOfMobileSearchServiceReqGen.respvalidate.GetNodeValue(MobileSearchServiceDataNodes.MOBILE_SEARCH_RESP_META_CODE.getNodePath(), 
						getNextPageOfMobileSearchServiceResponse).equalsIgnoreCase(successRespCode));
        
		if(getNextPageOfMobileSearchServiceReqGen.respvalidate.DoesNodeExists(MobileSearchServiceDataNodes.MOBIEL_SEARCH_RESP_DATA_BODY_FILTERS.getNodePath())){
			
			String responseFilterNodes = getNextPageOfMobileSearchServiceReqGen.respvalidate.NodeValue(
					MobileSearchServiceDataNodes.MOBIEL_SEARCH_RESP_DATA_BODY_FILTERS.getNodePath(), true);
			
			if(!responseFilterNodes.equals(mobileSearchServiceList.toString())){
				
				AssertJUnit.assertTrue("Invalid filters data nodes in MobileSearchService getNextPageOfSearch API response", 
						getNextPageOfMobileSearchServiceReqGen.respvalidate.DoesNodesExists(MobileSearchServiceDataNodes.getBodyWithFiltersDataTagNodes()));
			
			} else {
				System.out.println("\nfilters data nodes are empty in MobileSearchService getNextPageOfSearch API response\n");
				log.info("\nfilters data nodes are empty in MobileSearchService getNextPageOfSearch API response\n");
			}
			
		} else {
			System.out.println("\nfilters data nodes doesnt exists in MobileSearchService getNextPageOfSearch API response\n");
			log.info("\nfilters data nodes doesnt exists in MobileSearchService getNextPageOfSearch API response\n");
		}
		
        MyntAssert.setJsonResponse(getNextPageOfMobileSearchServiceResponse);
		MyntAssert.setExpectedAndActualForMyntListen(true);
		
    }
	
	/**
	 * This TestCase is used to invoke MobileSearchService getFirstPageOfSearch API and verification for success response
	 * and invoke MobileSearchService getNextPageOfSearch API and verification for products tag nodes in the response 
	 * 
	 * @param searchQuery
	 * @param firstRows
	 * @param returnDocs
	 * @param isFacet
	 * @param action
	 * @param nextRows
	 * @param successRespCode
	 */
	@Test(groups={ "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" }, 
			dataProvider="MobileSearchServiceDP_getNextPageOfSearch_verifyBodyWithProductsDataNodes")
    public void MobileSearchService_getNextPageOfSearch_verifyBodyWithProductsDataNodes(String searchQuery, String firstRows, String returnDocs, String isFacet, 
    		String action, String nextRows, String successRespCode)
    {
		// invoke getFirstPageOfSearch API
		RequestGenerator getFirstPageOfMobileSearchServiceReqGen = mobileSearchServiceHelper.invokeGetFirstPageOfSearch(searchQuery, firstRows, 
				returnDocs, isFacet);
		String getFirstPageOfMobileSearchServiceResponse = getFirstPageOfMobileSearchServiceReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting MobileSearchService getFirstPageOfSearch API response :\n\n"+getFirstPageOfMobileSearchServiceResponse+"\n");
		log.info("\nPrinting MobileSearchService getFirstPageOfSearch API response :\n\n"+getFirstPageOfMobileSearchServiceResponse+"\n");
			
		MyntAssert.assertEquals("MobileSearchService getFirstPageOfSearch API is not working",  Integer.parseInt(successRespCode), 
				getFirstPageOfMobileSearchServiceReqGen.response.getStatus());
		
		String requestObj = JsonPath.read(getFirstPageOfMobileSearchServiceResponse, "$.data.requestObj").toString();
		
		// invoke getNextPageOfSearch API with requestObject
		RequestGenerator getNextPageOfMobileSearchServiceReqGen = mobileSearchServiceHelper.invokeGetNextPageOfSearch(action, nextRows, requestObj); 
		String getNextPageOfMobileSearchServiceResponse = getNextPageOfMobileSearchServiceReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting MobileSearchService getNextPageOfSearch API response :\n\n"+getNextPageOfMobileSearchServiceResponse+"\n");
		log.info("\nPrinting MobileSearchService getNextPageOfSearch API response :\n\n"+getNextPageOfMobileSearchServiceResponse+"\n");
		
		AssertJUnit.assertTrue("MetaTag code data value should be 200 in MobileSearchService getNextPageOfSearch API response", 
				getNextPageOfMobileSearchServiceReqGen.respvalidate.GetNodeValue(MobileSearchServiceDataNodes.MOBILE_SEARCH_RESP_META_CODE.getNodePath(), 
						getNextPageOfMobileSearchServiceResponse).equalsIgnoreCase(successRespCode));
		
		if(getNextPageOfMobileSearchServiceReqGen.respvalidate.DoesNodeExists(MobileSearchServiceDataNodes.MOBILE_SEARCH_RESP_DATA_BODY_PRODUCTS.getNodePath())){
			
			String responseProductNodes = getNextPageOfMobileSearchServiceReqGen.respvalidate.NodeValue(
					MobileSearchServiceDataNodes.MOBILE_SEARCH_RESP_DATA_BODY_PRODUCTS.getNodePath(), true);
			
			if(!responseProductNodes.equals(mobileSearchServiceList.toString())){
				
				AssertJUnit.assertTrue("Invalid products data nodes in MobileSearchService getNextPageOfSearch API response", 
						getNextPageOfMobileSearchServiceReqGen.respvalidate.DoesNodesExists(MobileSearchServiceDataNodes.getBodyWithProductsDataTagNodes()));
			
			} else {
				System.out.println("\nproducts data nodes are empty in MobileSearchService getNextPageOfSearch API response\n");
				log.info("\nproduct data nodes are empty in MobileSearchService getNextPageOfSearch API response\n");
			}
		} else {
			System.out.println("\nproducts data node doesnt exists in MobileSearchService getNextPageOfSearch API response\n");
			log.info("\nproducts data node doesnt exists in MobileSearchService getNextPageOfSearch API response\n");
		}
		
        MyntAssert.setJsonResponse(getNextPageOfMobileSearchServiceResponse);
		MyntAssert.setExpectedAndActualForMyntListen(true);
		
    }
	
	/**
	 * This TestCase is used to invoke MobileSearchService getFirstPageOfSearch API and verification for success response
	 * and invoke MobileSearchService getSortedMobileSearch API and verification for success response
	 * 
	 * @param searchQuery
	 * @param rows
	 * @param returnDocs
	 * @param isFacet
	 * @param sortBy
	 * @param successRespCode
	 */
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "MiniRegression", "Regression", "ExhaustiveRegression" },
			dataProvider = "MobileSearchServiceDP_getSortedMobileSearch_verifySuccessResponse" )
    public void MobileSearchService_getSortedMobileSearch_verifySuccessResponse(String searchQuery, String rows, String returnDocs, String isFacet, String sortByParam, 
    		String successRespCode)
    {
		// invoke getFirstPageOfSearch API
		RequestGenerator getFirstPageOfMobileSearchServiceReqGen = mobileSearchServiceHelper.invokeGetFirstPageOfSearch(searchQuery, rows, 
				returnDocs, isFacet);
		String getFirstPageOfMobileSearchServiceResponse = getFirstPageOfMobileSearchServiceReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting MobileSearchService getFirstPageOfSearch API response :\n\n"+getFirstPageOfMobileSearchServiceResponse+"\n");
		log.info("\nPrinting MobileSearchService getFirstPageOfSearch API response :\n\n"+getFirstPageOfMobileSearchServiceResponse+"\n");
			
		MyntAssert.assertEquals("MobileSearchService getFirstPageOfSearch API is not working",  Integer.parseInt(successRespCode), 
				getFirstPageOfMobileSearchServiceReqGen.response.getStatus());
		
		String requestObj = JsonPath.read(getFirstPageOfMobileSearchServiceResponse, "$.data.requestObj").toString();
		
		// invoke g API with requestObject
		RequestGenerator getSortedMobileSearchServiceReqGen = mobileSearchServiceHelper.invokeGetSortedMobileSearch(sortByParam, requestObj);
		String getSortedMobileSearchServiceResponse = getSortedMobileSearchServiceReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting MobileSearchService getSortedMobileSearch API response :\n\n"+getSortedMobileSearchServiceResponse+"\n");
		log.info("\nPrinting MobileSearchService getSortedMobileSearch API response :\n\n"+getSortedMobileSearchServiceResponse+"\n");
			
		MyntAssert.assertEquals("MobileSearchService getSortedMobileSearch API is not working",  Integer.parseInt(successRespCode), 
				getSortedMobileSearchServiceReqGen.response.getStatus());
		
		MyntAssert.setJsonResponse(getSortedMobileSearchServiceResponse);
		MyntAssert.setExpectedAndActualForMyntListen(true);
		
    }
	
	/**
	 * This TestCase is used to invoke MobileSearchService getFirstPageOfSearch API and verification for success response
	 * and invoke MobileSearchService getSortedMobileSearch API and verification for headers information in the response
	 * 
	 * @param searchQuery
	 * @param rows
	 * @param returnDocs
	 * @param isFacet
	 * @param sortByParam
	 * @param successRespCode
	 */
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },
			dataProvider = "MobileSearchServiceDP_getSortedMobileSearch_verifyResponseHeaders" )
    public void MobileSearchService_getSortedMobileSearch_verifyResponseHeaders(String searchQuery, String rows, String returnDocs, String isFacet, String sortByParam, 
    		String successRespCode)
    {
		// invoke getFirstPageOfSearch API
		RequestGenerator getFirstPageOfMobileSearchServiceReqGen = mobileSearchServiceHelper.invokeGetFirstPageOfSearch(searchQuery, rows, 
				returnDocs, isFacet);
		String getFirstPageOfMobileSearchServiceResponse = getFirstPageOfMobileSearchServiceReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting MobileSearchService getFirstPageOfSearch API response :\n\n"+getFirstPageOfMobileSearchServiceResponse+"\n");
		log.info("\nPrinting MobileSearchService getFirstPageOfSearch API response :\n\n"+getFirstPageOfMobileSearchServiceResponse+"\n");
			
		MyntAssert.assertEquals("MobileSearchService getFirstPageOfSearch API is not working",  Integer.parseInt(successRespCode), 
				getFirstPageOfMobileSearchServiceReqGen.response.getStatus());
		
		String requestObj = JsonPath.read(getFirstPageOfMobileSearchServiceResponse, "$.data.requestObj").toString();
		
		// invoke getSortedMobileSearch API with requestObject
		RequestGenerator getSortedMobileSearchServiceReqGen = mobileSearchServiceHelper.invokeGetSortedMobileSearch(sortByParam, requestObj);
		String getSortedMobileSearchServiceResponse = getSortedMobileSearchServiceReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting MobileSearchService getSortedMobileSearch API response :\n\n"+getSortedMobileSearchServiceResponse+"\n");
		log.info("\nPrinting MobileSearchService getSortedMobileSearch API response :\n\n"+getSortedMobileSearchServiceResponse+"\n");
			
		MyntAssert.assertEquals("MobileSearchService getSortedMobileSearch API is not working",  Integer.parseInt(successRespCode), 
				getSortedMobileSearchServiceReqGen.response.getStatus());
        
        MultivaluedMap<String, Object> getSortedMobileSearchResponseHeaders = getSortedMobileSearchServiceReqGen.response.getHeaders();
        System.out.println("\nMobileSearchService getSortedMobileSearch API response headers : "+getSortedMobileSearchResponseHeaders+"\n");
        log.info("\nMobileSearchService getSortedMobileSearch API response headers : "+getSortedMobileSearchResponseHeaders+"\n");
        
        MyntAssert.assertEquals("Invalid header information in MobileSearchService getSortedMobileSearch API response", "success", 
        		validateResponseHeaders(getSortedMobileSearchResponseHeaders));
        
        MyntAssert.setJsonResponse(getSortedMobileSearchServiceResponse);
		MyntAssert.setExpectedAndActualForMyntListen(true);
    }
	
	/**
	 * This TestCase is used to invoke MobileSearchService getFirstPageOfSearch API and verification for success response
	 * and invoke MobileSearchService getSortedMobileSearch API and verification for meta tag nodes in the response
	 * 
	 * @param searchQuery
	 * @param firstRows
	 * @param returnDocs
	 * @param isFacet
	 * @param sortByParam
	 * @param successRespCode
	 */
	@Test(groups={ "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" }, 
			dataProvider="MobileSearchServiceDP_getSortedMobileSearch_verifyMetaTagNodes")
    public void MobileSearchService_getSortedMobileSearch_verifyMetaTagNodes(String searchQuery, String firstRows, String returnDocs, String isFacet, 
    		String sortByParam, String successRespCode)
    {
		// invoke getFirstPageOfSearch API
		RequestGenerator getFirstPageOfMobileSearchServiceReqGen = mobileSearchServiceHelper.invokeGetFirstPageOfSearch(searchQuery, firstRows, 
				returnDocs, isFacet);
		String getFirstPageOfMobileSearchServiceResponse = getFirstPageOfMobileSearchServiceReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting MobileSearchService getFirstPageOfSearch API response :\n\n"+getFirstPageOfMobileSearchServiceResponse+"\n");
		log.info("\nPrinting MobileSearchService getFirstPageOfSearch API response :\n\n"+getFirstPageOfMobileSearchServiceResponse+"\n");
			
		MyntAssert.assertEquals("MobileSearchService getFirstPageOfSearch API is not working",  Integer.parseInt(successRespCode), 
				getFirstPageOfMobileSearchServiceReqGen.response.getStatus());
		
		String requestObj = JsonPath.read(getFirstPageOfMobileSearchServiceResponse, "$.data.requestObj").toString();
		
		// invoke getSortedMobileSearch API with requestObject
		RequestGenerator getSortedMobileSearchServiceReqGen = mobileSearchServiceHelper.invokeGetSortedMobileSearch(sortByParam, requestObj);
		String getSortedMobileSearchServiceResponse = getSortedMobileSearchServiceReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting MobileSearchService getSortedMobileSearch API response :\n\n"+getSortedMobileSearchServiceResponse+"\n");
		log.info("\nPrinting MobileSearchService getSortedMobileSearch API response :\n\n"+getSortedMobileSearchServiceResponse+"\n");
			
		MyntAssert.assertEquals("MobileSearchService getSortedMobileSearch API is not working",  Integer.parseInt(successRespCode), 
				getSortedMobileSearchServiceReqGen.response.getStatus());
		
		AssertJUnit.assertTrue("Invalid MetaTag data nodes in MobileSearchService getSortedMobileSearch API response", 
				getSortedMobileSearchServiceReqGen.respvalidate.DoesNodesExists(MobileSearchServiceDataNodes.getMobileSearchServiceResponseMetaTagNodes()));
		
		AssertJUnit.assertTrue("MetaTag code data value should be 200 in MobileSearchService getSortedMobileSearch API response", 
				getSortedMobileSearchServiceReqGen.respvalidate.GetNodeValue(MobileSearchServiceDataNodes.MOBILE_SEARCH_RESP_META_CODE.getNodePath(), 
						getSortedMobileSearchServiceResponse).equalsIgnoreCase(successRespCode));
		
		MyntAssert.assertEquals("MetaTag errorDetail data value should be null in MobileSearchService getSortedMobileSearch API response", "null", 
				getSortedMobileSearchServiceReqGen.respvalidate.GetNodeValue(MobileSearchServiceDataNodes.MOBILE_SEARCH_RESP_META_ERROR_DETAIL.getNodePath(),
						getSortedMobileSearchServiceResponse));
		
        MyntAssert.setJsonResponse(getSortedMobileSearchServiceResponse);
		MyntAssert.setExpectedAndActualForMyntListen(true);
		
    }
	
	/**
	 * This TestCase is used to invoke MobileSearchService getFirstPageOfSearch API and verification for success response
	 * and invoke MobileSearchService getSortedMobileSearch API and verification for notification tag nodes in the response
	 * 
	 * @param searchQuery
	 * @param firstRows
	 * @param returnDocs
	 * @param isFacet
	 * @param sortByParam
	 * @param successRespCode
	 */
	@Test(groups={ "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" }, 
			dataProvider="MobileSearchServiceDP_getSortedMobileSearch_verifyNotificationTagNodes")
    public void MobileSearchService_getSortedMobileSearch_verifyNotificationTagNodes(String searchQuery, String firstRows, String returnDocs, String isFacet, 
    		String sortByParam, String successRespCode)
    {
		// invoke getFirstPageOfSearch API
		RequestGenerator getFirstPageOfMobileSearchServiceReqGen = mobileSearchServiceHelper.invokeGetFirstPageOfSearch(searchQuery, firstRows, 
				returnDocs, isFacet);
		String getFirstPageOfMobileSearchServiceResponse = getFirstPageOfMobileSearchServiceReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting MobileSearchService getFirstPageOfSearch API response :\n\n"+getFirstPageOfMobileSearchServiceResponse+"\n");
		log.info("\nPrinting MobileSearchService getFirstPageOfSearch API response :\n\n"+getFirstPageOfMobileSearchServiceResponse+"\n");
			
		MyntAssert.assertEquals("MobileSearchService getFirstPageOfSearch API is not working",  Integer.parseInt(successRespCode), 
				getFirstPageOfMobileSearchServiceReqGen.response.getStatus());
		
		String requestObj = JsonPath.read(getFirstPageOfMobileSearchServiceResponse, "$.data.requestObj").toString();
		
		// invoke getSortedMobileSearch API with requestObject
		RequestGenerator getSortedMobileSearchServiceReqGen = mobileSearchServiceHelper.invokeGetSortedMobileSearch(sortByParam, requestObj);
		String getSortedMobileSearchServiceResponse = getSortedMobileSearchServiceReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting MobileSearchService getSortedMobileSearch API response :\n\n"+getSortedMobileSearchServiceResponse+"\n");
		log.info("\nPrinting MobileSearchService getSortedMobileSearch API response :\n\n"+getSortedMobileSearchServiceResponse+"\n");
		
		MyntAssert.assertEquals("MobileSearchService getSortedMobileSearch API is not working",  Integer.parseInt(successRespCode), 
				getSortedMobileSearchServiceReqGen.response.getStatus());
        
		AssertJUnit.assertTrue("Invalid notification tag data nodes in MobileSearchService getSortedMobileSearch API response", 
				getSortedMobileSearchServiceReqGen.respvalidate.DoesNodesExists(MobileSearchServiceDataNodes.getMobileSearchServiceResponseNotificationTagNodes()));
		
        MyntAssert.setJsonResponse(getSortedMobileSearchServiceResponse);
		MyntAssert.setExpectedAndActualForMyntListen(true);
		
    }
	
	/**
	 * This TestCase is used to invoke MobileSearchService getFirstPageOfSearch API and verification for success response
	 * and invoke MobileSearchService getSortedMobileSearch API and verification for requestObject tag nodes in the response
	 * 
	 * @param searchQuery
	 * @param firstRows
	 * @param returnDocs
	 * @param isFacet
	 * @param sortByParam
	 * @param successRespCode
	 */
	@Test(groups={ "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" }, 
			dataProvider="MobileSearchServiceDP_getSortedMobileSearch_verifyRequestObjectDataNodes")
    public void MobileSearchService_getSortedMobileSearch_verifyRequestObjectDataNodes(String searchQuery, String firstRows, String returnDocs, String isFacet, 
    		String sortByParam, String successRespCode)
    {
		// invoke getFirstPageOfSearch API
		RequestGenerator getFirstPageOfMobileSearchServiceReqGen = mobileSearchServiceHelper.invokeGetFirstPageOfSearch(searchQuery, firstRows, 
				returnDocs, isFacet);
		String getFirstPageOfMobileSearchServiceResponse = getFirstPageOfMobileSearchServiceReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting MobileSearchService getFirstPageOfSearch API response :\n\n"+getFirstPageOfMobileSearchServiceResponse+"\n");
		log.info("\nPrinting MobileSearchService getFirstPageOfSearch API response :\n\n"+getFirstPageOfMobileSearchServiceResponse+"\n");
			
		MyntAssert.assertEquals("MobileSearchService getFirstPageOfSearch API is not working",  Integer.parseInt(successRespCode), 
				getFirstPageOfMobileSearchServiceReqGen.response.getStatus());
		
		String requestObj = JsonPath.read(getFirstPageOfMobileSearchServiceResponse, "$.data.requestObj").toString();
		
		// invoke getSortedMobileSearch API with requestObject
		RequestGenerator getSortedMobileSearchServiceReqGen = mobileSearchServiceHelper.invokeGetSortedMobileSearch(sortByParam, requestObj);
		String getSortedMobileSearchServiceResponse = getSortedMobileSearchServiceReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting MobileSearchService getSortedMobileSearch API response :\n\n"+getSortedMobileSearchServiceResponse+"\n");
		log.info("\nPrinting MobileSearchService getSortedMobileSearch API response :\n\n"+getSortedMobileSearchServiceResponse+"\n");
		
		AssertJUnit.assertTrue("MetaTag code data value should be 200 in MobileSearchService getSortedMobileSearch API response", 
				getSortedMobileSearchServiceReqGen.respvalidate.GetNodeValue(MobileSearchServiceDataNodes.MOBILE_SEARCH_RESP_META_CODE.getNodePath(), 
						getSortedMobileSearchServiceResponse).equalsIgnoreCase(successRespCode));
		
		if(getSortedMobileSearchServiceReqGen.respvalidate.DoesNodeExists(MobileSearchServiceDataNodes.MOBILE_SEARCH_RESP_DATA_REQ_OBJECT.getNodePath())){
			
			AssertJUnit.assertTrue("Invalid RequestObjectTag data nodes in MobileSearchService getSortedMobileSearch API response", 
					getSortedMobileSearchServiceReqGen.respvalidate.DoesNodesExists(MobileSearchServiceDataNodes.getRequestObjectDataTagNodes()));
			
			AssertJUnit.assertTrue("request and response rows tag nodes are not same", 
					getSortedMobileSearchServiceReqGen.respvalidate.NodeValue(
							MobileSearchServiceDataNodes.MOBILE_SEARCH_RESP_DATA_REQ_OBJECT_ROWS.getNodePath(), false).equals(firstRows));
			
			MyntAssert.assertEquals("request and response returnDocs tag nodes are not same", 
					getSortedMobileSearchServiceReqGen.respvalidate.NodeValue(
							MobileSearchServiceDataNodes.MOBILE_SEARCH_RESP_DATA_REQ_OBJECT_RETURN_DOCS.getNodePath(), false), returnDocs);
			
			MyntAssert.assertEquals("request and response isFacet tag nodes are not same", 
					getSortedMobileSearchServiceReqGen.respvalidate.NodeValue(
							MobileSearchServiceDataNodes.MOBILE_SEARCH_RESP_DATA_REQ_OBJECT_FACET.getNodePath(), false), isFacet);
			
		} else {
			System.out.println("\nrequestObject data nodes doesnt exists in MobileSearchService getSortedMobileSearch API response\n");
			log.info("\nrequestObject data nodes doesnt exists in MobileSearchService getSortedMobileSearch API response\n");
		}
		
        
        MyntAssert.setJsonResponse(getSortedMobileSearchServiceResponse);
		MyntAssert.setExpectedAndActualForMyntListen(true);
		
    }
	
	/**
	 * This TestCase is used to invoke MobileSearchService getFirstPageOfSearch API and verification for success response
	 * and invoke MobileSearchService getSortedMobileSearch API and verification for requestObject curated_query tag nodes in the response
	 * 
	 * @param searchQuery
	 * @param rows
	 * @param returnDocs
	 * @param isFacet
	 * @param sortByParam
	 * @param successRespCode
	 */
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },
			dataProvider = "MobileSearchServiceDP_getSortedMobileSearch_verifyRequestObjectWithCuratedQueryDataNodes" )
    public void MobileSearchService_getSortedMobileSearch_verifyRequestObjectWithCuratedQueryDataNodes(String searchQuery, String rows, String returnDocs, String isFacet, 
    		String sortByParam, String successRespCode)
    {
        RequestGenerator getFirstPageOfMobileSearchServiceReqGen = mobileSearchServiceHelper.invokeGetFirstPageOfSearch(searchQuery, rows, 
        		returnDocs, isFacet);
        String getFirstPageOfMobileSearchServiceResponse = getFirstPageOfMobileSearchServiceReqGen.respvalidate.returnresponseasstring();
        System.out.println("\nPrinting MobileSearchService getFirstPageOfSearch API response :\n\n"+getFirstPageOfMobileSearchServiceResponse+"\n");
		log.info("\nPrinting MobileSearchService getFirstPageOfSearch API response :\n\n"+getFirstPageOfMobileSearchServiceResponse+"\n");
		 
		MyntAssert.assertEquals("MobileSearchService getFirstPageOfSearch API is not working",  Integer.parseInt(successRespCode), 
				getFirstPageOfMobileSearchServiceReqGen.response.getStatus());
		
		String requestObj = JsonPath.read(getFirstPageOfMobileSearchServiceResponse, "$.data.requestObj").toString();
		
		// invoke getSortedMobileSearch API with requestObject
		RequestGenerator getSortedMobileSearchServiceReqGen = mobileSearchServiceHelper.invokeGetSortedMobileSearch(sortByParam, requestObj);
		String getSortedMobileSearchServiceResponse = getSortedMobileSearchServiceReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting MobileSearchService getSortedMobileSearch API response :\n\n"+getSortedMobileSearchServiceResponse+"\n");
		log.info("\nPrinting MobileSearchService getSortedMobileSearch API response :\n\n"+getSortedMobileSearchServiceResponse+"\n");
		
		AssertJUnit.assertTrue("MetaTag code data value should be 200 in MobileSearchService getSortedMobileSearch API response", 
				getSortedMobileSearchServiceReqGen.respvalidate.GetNodeValue(MobileSearchServiceDataNodes.MOBILE_SEARCH_RESP_META_CODE.getNodePath(), 
						getSortedMobileSearchServiceResponse).equalsIgnoreCase(successRespCode));
		
		if(getSortedMobileSearchServiceReqGen.respvalidate.DoesNodeExists(MobileSearchServiceDataNodes.MOBILE_SEARCH_RESP_DATA_REQ_OBJECT.getNodePath())
				&& getSortedMobileSearchServiceReqGen.respvalidate.DoesNodeExists(
						MobileSearchServiceDataNodes.MOBILE_SEARCH_RESP_DATA_REQ_OBJECT_CURATED_QUERY.getNodePath())){
			
			AssertJUnit.assertTrue("Invalid requestObject curated_query data nodes in MobileSearchService getSortedMobileSearch API response", 
					getSortedMobileSearchServiceReqGen.respvalidate.DoesNodesExists(MobileSearchServiceDataNodes.getRequestObjectWithCuratedQueryDataTagNodes()));
			
			AssertJUnit.assertTrue("request and response rows tag nodes are not same", 
					getSortedMobileSearchServiceReqGen.respvalidate.NodeValue(
							MobileSearchServiceDataNodes.MOBILE_SEARCH_RESP_DATA_REQ_OBJECT_CURATED_QUERY_ROWS.getNodePath(), false).equals(rows));
			
		} else {
			System.out.println("\nrequestObject curated_query data nodes doesnt exists in MobileSearchService getSortedMobileSearch API response\n");
			log.info("\nrequestObject curated_query data nodes doesnt exists in MobileSearchService getSortedMobileSearch API response\n");
		}
        
        MyntAssert.setJsonResponse(getSortedMobileSearchServiceResponse);
		MyntAssert.setExpectedAndActualForMyntListen(true);
    }
	
	/**
	 * This TestCase is used to invoke MobileSearchService getFirstPageOfSearch API and verification for success response
	 * and invoke MobileSearchService getSortedMobileSearch API and verification for bodyWithFiltersDataNodes tag nodes in the response 
	 * 
	 * @param searchQuery
	 * @param rows
	 * @param returnDocs
	 * @param isFacet
	 * @param sortByParam
	 * @param successRespCode
	 */
	@Test(groups={ "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" }, 
			dataProvider="MobileSearchServiceDP_getSortedMobileSearch_verifyBodyWithFiltersDataNodes")
    public void MobileSearchService_getSortedMobileSearch_verifyBodyWithFiltersDataNodes(String searchQuery, String rows, String returnDocs, String isFacet, 
    		String sortByParam, String successRespCode)
    {
		RequestGenerator getFirstPageOfMobileSearchServiceReqGen = mobileSearchServiceHelper.invokeGetFirstPageOfSearch(searchQuery, rows, 
        		returnDocs, isFacet);
        String getFirstPageOfMobileSearchServiceResponse = getFirstPageOfMobileSearchServiceReqGen.respvalidate.returnresponseasstring();
        System.out.println("\nPrinting MobileSearchService getFirstPageOfSearch API response :\n\n"+getFirstPageOfMobileSearchServiceResponse+"\n");
		log.info("\nPrinting MobileSearchService getFirstPageOfSearch API response :\n\n"+getFirstPageOfMobileSearchServiceResponse+"\n");
		 
		MyntAssert.assertEquals("MobileSearchService getFirstPageOfSearch API is not working",  Integer.parseInt(successRespCode), 
				getFirstPageOfMobileSearchServiceReqGen.response.getStatus());
		
		String requestObj = JsonPath.read(getFirstPageOfMobileSearchServiceResponse, "$.data.requestObj").toString();
		
		// invoke getSortedMobileSearch API with requestObject
		RequestGenerator getSortedMobileSearchServiceReqGen = mobileSearchServiceHelper.invokeGetSortedMobileSearch(sortByParam, requestObj);
		String getSortedMobileSearchServiceResponse = getSortedMobileSearchServiceReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting MobileSearchService getSortedMobileSearch API response :\n\n"+getSortedMobileSearchServiceResponse+"\n");
		log.info("\nPrinting MobileSearchService getSortedMobileSearch API response :\n\n"+getSortedMobileSearchServiceResponse+"\n");
		
		AssertJUnit.assertTrue("MetaTag code data value should be 200 in MobileSearchService getSortedMobileSearch API response", 
				getSortedMobileSearchServiceReqGen.respvalidate.GetNodeValue(MobileSearchServiceDataNodes.MOBILE_SEARCH_RESP_META_CODE.getNodePath(), 
						getSortedMobileSearchServiceResponse).equalsIgnoreCase(successRespCode));
        
		if(getSortedMobileSearchServiceReqGen.respvalidate.DoesNodeExists(MobileSearchServiceDataNodes.MOBIEL_SEARCH_RESP_DATA_BODY_FILTERS.getNodePath())){
			
			String responseFilterNodes = getSortedMobileSearchServiceReqGen.respvalidate.NodeValue(
					MobileSearchServiceDataNodes.MOBIEL_SEARCH_RESP_DATA_BODY_FILTERS.getNodePath(), false);
			
			if(!responseFilterNodes.equals(mobileSearchServiceList.toString())){
				
				AssertJUnit.assertTrue("Invalid filter data nodes in MobileSearchService getSortedMobileSearch API response", 
						getSortedMobileSearchServiceReqGen.respvalidate.DoesNodesExists(MobileSearchServiceDataNodes.getBodyWithFiltersDataTagNodes()));
			
			} else {
				System.out.println("\nfilter data nodes are empty in MobileSearchService getSortedMobileSearch API response\n");
				log.info("\nfilter data nodes are empty in MobileSearchService getSortedMobileSearch API response\n");
			}
			
		} else {
			System.out.println("\nfilter data nodes doesnt exists in MobileSearchService getSortedMobileSearch API response\n");
			log.info("\nfilter data nodes doesnt exists in MobileSearchService getSortedMobileSearch API response\n");
		}
		
        MyntAssert.setJsonResponse(getSortedMobileSearchServiceResponse);
		MyntAssert.setExpectedAndActualForMyntListen(true);
		
    }
	
	/**
	 * This TestCase is used to invoke MobileSearchService getFirstPageOfSearch API and verification for success response
	 * and invoke MobileSearchService getSortedMobileSearch API and verification for bodyWithProductsDataNodes tag nodes in the response 
	 * 
	 * @param searchQuery
	 * @param rows
	 * @param returnDocs
	 * @param isFacet
	 * @param sortByParam
	 * @param successRespCode
	 */
	@Test(groups={ "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" }, 
			dataProvider="MobileSearchServiceDP_getSortedMobileSearch_verifyBodyWithProductsDataNodes")
    public void MobileSearchService_getSortedMobileSearch_verifyBodyWithProductsDataNodes(String searchQuery, String rows, String returnDocs, String isFacet, 
    		String sortByParam, String successRespCode)
    {
		// invoke getFirstPageOfSearch API
		RequestGenerator getFirstPageOfMobileSearchServiceReqGen = mobileSearchServiceHelper.invokeGetFirstPageOfSearch(searchQuery, rows, 
				returnDocs, isFacet);
		String getFirstPageOfMobileSearchServiceResponse = getFirstPageOfMobileSearchServiceReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting MobileSearchService getFirstPageOfSearch API response :\n\n"+getFirstPageOfMobileSearchServiceResponse+"\n");
		log.info("\nPrinting MobileSearchService getFirstPageOfSearch API response :\n\n"+getFirstPageOfMobileSearchServiceResponse+"\n");
			
		MyntAssert.assertEquals("MobileSearchService getFirstPageOfSearch API is not working",  Integer.parseInt(successRespCode), 
				getFirstPageOfMobileSearchServiceReqGen.response.getStatus());
		
		String requestObj = JsonPath.read(getFirstPageOfMobileSearchServiceResponse, "$.data.requestObj").toString();
		
		// invoke getSortedMobileSearch API with requestObject
		RequestGenerator getSortedMobileSearchServiceReqGen = mobileSearchServiceHelper.invokeGetSortedMobileSearch(sortByParam, requestObj);
		String getSortedMobileSearchServiceResponse = getSortedMobileSearchServiceReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting MobileSearchService getSortedMobileSearch API response :\n\n"+getSortedMobileSearchServiceResponse+"\n");
		log.info("\nPrinting MobileSearchService getSortedMobileSearch API response :\n\n"+getSortedMobileSearchServiceResponse+"\n");
				
		AssertJUnit.assertTrue("MetaTag code data value should be 200 in MobileSearchService getSortedMobileSearch API response", 
				getSortedMobileSearchServiceReqGen.respvalidate.GetNodeValue(MobileSearchServiceDataNodes.MOBILE_SEARCH_RESP_META_CODE.getNodePath(), 
						getSortedMobileSearchServiceResponse).equalsIgnoreCase(successRespCode));
        
		if(getSortedMobileSearchServiceReqGen.respvalidate.DoesNodeExists(MobileSearchServiceDataNodes.MOBILE_SEARCH_RESP_DATA_BODY_PRODUCTS.getNodePath())){
			
			String responseProductNodes = getSortedMobileSearchServiceReqGen.respvalidate.NodeValue(
					MobileSearchServiceDataNodes.MOBILE_SEARCH_RESP_DATA_BODY_PRODUCTS.getNodePath(), true);
			
			if(!responseProductNodes.equals(mobileSearchServiceList.toString())){
				
				AssertJUnit.assertTrue("Invalid product data nodes in MobileSearchService getSortedMobileSearch API response", 
						getSortedMobileSearchServiceReqGen.respvalidate.DoesNodesExists(MobileSearchServiceDataNodes.getBodyWithProductsDataTagNodes()));
				
			} else {
				System.out.println("\nproduct data nodes are empty in MobileSearchService getSortedMobileSearch API response\n");
				log.info("\nproduct data nodes are empty in MobileSearchService getSortedMobileSearch API response\n");
			}
			
		} else {
			System.out.println("\nproduct data nodes doesnt exists in MobileSearchService getSortedMobileSearch API response\n");
			log.info("\nproduct data nodes doesnt exists in MobileSearchService getSortedMobileSearch API response\n");
		}
		
        MyntAssert.setJsonResponse(getSortedMobileSearchServiceResponse);
		MyntAssert.setExpectedAndActualForMyntListen(true);
		
    }
	
	/**
	 * This TestCase is used to invoke MobileSearchService getFirstPageOfSearch API and verification for success response
	 * and invoke MobileSearchService getSortedMobileSearch API and verification for products in sorted order or not
	 * 
	 * @param searchQuery
	 * @param rows
	 * @param returnDocs
	 * @param isFacet
	 * @param sortByParam
	 * @param successRespCode
	 */
	@Test(groups={ "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" }, 
			dataProvider="MobileSearchServiceDP_getSortedMobileSearch_verifyIsProductsInSortedOrder")
    public void MobileSearchService_getSortedMobileSearch_verifyIsProductsInSortedOrder(String searchQuery, String rows, String returnDocs, String isFacet, 
    		String sortByParam, String successRespCode)
    {
		// invoke getFirstPageOfSearch API
		RequestGenerator getFirstPageOfMobileSearchServiceReqGen = mobileSearchServiceHelper.invokeGetFirstPageOfSearch(searchQuery, rows, 
				returnDocs, isFacet);
		String getFirstPageOfMobileSearchServiceResponse = getFirstPageOfMobileSearchServiceReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting MobileSearchService getFirstPageOfSearch API response :\n\n"+getFirstPageOfMobileSearchServiceResponse+"\n");
		log.info("\nPrinting MobileSearchService getFirstPageOfSearch API response :\n\n"+getFirstPageOfMobileSearchServiceResponse+"\n");
			
		MyntAssert.assertEquals("MobileSearchService getFirstPageOfSearch API is not working",  Integer.parseInt(successRespCode), 
				getFirstPageOfMobileSearchServiceReqGen.response.getStatus());
		
		String requestObj = JsonPath.read(getFirstPageOfMobileSearchServiceResponse, "$.data.requestObj").toString();
		
		// invoke getSortedMobileSearch API with requestObject
		RequestGenerator getSortedMobileSearchServiceReqGen = mobileSearchServiceHelper.invokeGetSortedMobileSearch(sortByParam, requestObj);
		String getSortedMobileSearchServiceResponse = getSortedMobileSearchServiceReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting MobileSearchService getSortedMobileSearch API response :\n\n"+getSortedMobileSearchServiceResponse+"\n");
		log.info("\nPrinting MobileSearchService getSortedMobileSearch API response :\n\n"+getSortedMobileSearchServiceResponse+"\n");
				
		AssertJUnit.assertTrue("MetaTag code data value should be 200 in MobileSearchService getSortedMobileSearch API response", 
				getSortedMobileSearchServiceReqGen.respvalidate.GetNodeValue(MobileSearchServiceDataNodes.MOBILE_SEARCH_RESP_META_CODE.getNodePath(), 
						getSortedMobileSearchServiceResponse).equalsIgnoreCase(successRespCode));
        
		if(getSortedMobileSearchServiceReqGen.respvalidate.DoesNodeExists(MobileSearchServiceDataNodes.MOBILE_SEARCH_RESP_DATA_BODY_PRODUCTS.getNodePath())){
			
			String responseProductNodes = getSortedMobileSearchServiceReqGen.respvalidate.NodeValue(
					MobileSearchServiceDataNodes.MOBILE_SEARCH_RESP_DATA_BODY_PRODUCTS.getNodePath(), true);
			
			if(!responseProductNodes.equals(mobileSearchServiceList.toString())){
				
				List<Integer> discountedPriceList = JsonPath.read(getSortedMobileSearchServiceResponse, "$.data.body.products[*].discounted_price");
				System.out.println("\nPrinting DiscountedPriceList : "+discountedPriceList+"\n");
				log.info("\nPrinting DiscountedPriceList : "+discountedPriceList+"\n");
				
				if(sortByParam.equalsIgnoreCase(":low")){
					
					AssertJUnit.assertTrue("discounted_price should be in ascending order in MobileSearchService getSortedMobileSearch API response", 
							CommonUtils.isArraySortedInAscendingOrder(discountedPriceList));
					
				} else if(sortByParam.equalsIgnoreCase(":high")){
					
					AssertJUnit.assertTrue("discounted_price should be in descending order in MobileSearchService getSortedMobileSearch API response", 
							CommonUtils.isArraySortedInDescendingOrder(discountedPriceList));
					
				}
				
			} else {
				System.out.println("\nproducts data nodes are empty in MobileSearchService getSortedMobileSearch API response\n");
				log.info("\nproducts data nodes are empty in MobileSearchService getSortedMobileSearch API response\n");
			}
			
		} else {
			System.out.println("\nproducts data nodes doesnt exists in MobileSearchService getSortedMobileSearch API response\n");
			log.info("\nproducts data nodes doesnt exists in MobileSearchService getSortedMobileSearch API response\n");
		}
		
        MyntAssert.setJsonResponse(getSortedMobileSearchServiceResponse);
		MyntAssert.setExpectedAndActualForMyntListen(true);
		
    }
	
	
	/**
	 * This TestCase is used to invoke MobileSearchService searchWithPresetFilters API and verification for success response
	 * 
	 * @param searchQuery
	 * @param rows
	 * @param returnDocs
	 * @param isFacet
	 * @param filters
	 * @param successRespCode
	 */
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "MiniRegression", "Regression", "ExhaustiveRegression" },
			dataProvider = "MobileSearchServiceDP_searchWithPresetFilters_verifySuccessResponse" )
    public void MobileSearchService_searchWithPresetFilters_verifySuccessResponse(String searchQuery, String rows, String returnDocs, String isFacet, String filters,
    		String successRespCode)
    {
        RequestGenerator searchWithPresetFiltersServiceReqGen = mobileSearchServiceHelper.invokeSearchWithPresetFilters(searchQuery, rows, 
        		returnDocs, isFacet, filters);
        String searchWithPresetFiltersServiceResponse = searchWithPresetFiltersServiceReqGen.respvalidate.returnresponseasstring();
        System.out.println("\nPrinting MobileSearchService searchWithPresetFilters API response :\n\n"+searchWithPresetFiltersServiceResponse+"\n");
		log.info("\nPrinting MobileSearchService searchWithPresetFilters API response :\n\n"+searchWithPresetFiltersServiceResponse+"\n");
		 
		MyntAssert.assertEquals("MobileSearchService searchWithPresetFilters API is not working",  Integer.parseInt(successRespCode), 
				searchWithPresetFiltersServiceReqGen.response.getStatus());
        
        MyntAssert.setJsonResponse(searchWithPresetFiltersServiceResponse);
		MyntAssert.setExpectedAndActualForMyntListen(true);
    }
	
	/**
	 * This TestCase is used to invoke MobileSearchService searchWithPresetFilters API and verification for headers information in the response
	 * 
	 * @param searchQuery
	 * @param rows
	 * @param returnDocs
	 * @param isFacet
	 * @param filters
	 * @param successRespCode
	 */
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },
			dataProvider = "MobileSearchServiceDP_searchWithPresetFilters_verifyResponseHeaders" )
    public void MobileSearchService_searchWithPresetFilters_verifyResponseHeaders(String searchQuery, String rows, String returnDocs, String isFacet, String filters,
    		String successRespCode)
    {
		RequestGenerator searchWithPresetFiltersServiceReqGen = mobileSearchServiceHelper.invokeSearchWithPresetFilters(searchQuery, rows, 
	       		returnDocs, isFacet, filters);
	    String searchWithPresetFiltersServiceResponse = searchWithPresetFiltersServiceReqGen.respvalidate.returnresponseasstring();
	    System.out.println("\nPrinting MobileSearchService searchWithPresetFilters API response :\n\n"+searchWithPresetFiltersServiceResponse+"\n");
		log.info("\nPrinting MobileSearchService searchWithPresetFilters API response :\n\n"+searchWithPresetFiltersServiceResponse+"\n");
			 
		MyntAssert.assertEquals("MobileSearchService searchWithPresetFilters API is not working",  Integer.parseInt(successRespCode), 
				searchWithPresetFiltersServiceReqGen.response.getStatus());
        
        MultivaluedMap<String, Object> searchWithPresetFiltersResponseHeaders = searchWithPresetFiltersServiceReqGen.response.getHeaders();
        System.out.println("\nMobileSearchService searchWithPresetFilters API response headers : "+searchWithPresetFiltersResponseHeaders+"\n");
        log.info("\nMobileSearchService searchWithPresetFilters API response headers : "+searchWithPresetFiltersResponseHeaders+"\n");
        
        MyntAssert.assertEquals("Invalid header information in MobileSearchService searchWithPresetFilters API response", "success", 
        		validateResponseHeaders(searchWithPresetFiltersResponseHeaders));
        
        MyntAssert.setJsonResponse(searchWithPresetFiltersServiceResponse);
		MyntAssert.setExpectedAndActualForMyntListen(true);
    }
	
	/**
	 * This TestCase is used to invoke MobileSearchService searchWithPresetFilters API and verification for meta tag nodes in the response
	 * 
	 * @param searchQuery
	 * @param rows
	 * @param returnDocs
	 * @param isFacet
	 * @param filters
	 * @param successRespCode
	 */
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },
			dataProvider = "MobileSearchServiceDP_searchWithPresetFilters_verifyMetaTagNodes" )
    public void MobileSearchService_searchWithPresetFilters_verifyMetaTagNodes(String searchQuery, String rows, String returnDocs, String isFacet, String filters,
    		String successRespCode)
    {
		RequestGenerator searchWithPresetFiltersServiceReqGen = mobileSearchServiceHelper.invokeSearchWithPresetFilters(searchQuery, rows, 
	       		returnDocs, isFacet, filters);
	    String searchWithPresetFiltersServiceResponse = searchWithPresetFiltersServiceReqGen.respvalidate.returnresponseasstring();
	    System.out.println("\nPrinting MobileSearchService searchWithPresetFilters API response :\n\n"+searchWithPresetFiltersServiceResponse+"\n");
		log.info("\nPrinting MobileSearchService searchWithPresetFilters API response :\n\n"+searchWithPresetFiltersServiceResponse+"\n");
			 
		MyntAssert.assertEquals("MobileSearchService searchWithPresetFilters API is not working",  Integer.parseInt(successRespCode), 
				searchWithPresetFiltersServiceReqGen.response.getStatus());
        
		AssertJUnit.assertTrue("Invalid MetaTag data nodes in MobileSearchService searchWithPresetFilters API response", 
				searchWithPresetFiltersServiceReqGen.respvalidate.DoesNodesExists(MobileSearchServiceDataNodes.getMobileSearchServiceResponseMetaTagNodes()));
		
		AssertJUnit.assertTrue("MetaTag code data value should be 200 in MobileSearchService searchWithPresetFilters API response", 
				searchWithPresetFiltersServiceReqGen.respvalidate.GetNodeValue(MobileSearchServiceDataNodes.MOBILE_SEARCH_RESP_META_CODE.getNodePath(), 
						searchWithPresetFiltersServiceResponse).equalsIgnoreCase(successRespCode));
		
		MyntAssert.assertEquals("MetaTag errorDetail data value should be null in MobileSearchService searchWithPresetFilters API response", "null", 
				searchWithPresetFiltersServiceReqGen.respvalidate.GetNodeValue(MobileSearchServiceDataNodes.MOBILE_SEARCH_RESP_META_ERROR_DETAIL.getNodePath(),
						searchWithPresetFiltersServiceResponse));
		
        MyntAssert.setJsonResponse(searchWithPresetFiltersServiceResponse);
		MyntAssert.setExpectedAndActualForMyntListen(true);
    }
	
	/**
	 * This TestCase is used to invoke MobileSearchService searchWithPresetFilters API and verification for notification tag nodes in the response
	 * 
	 * @param searchQuery
	 * @param rows
	 * @param returnDocs
	 * @param isFacet
	 * @param filters
	 * @param successRespCode
	 */
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },
			dataProvider = "MobileSearchServiceDP_searchWithPresetFilters_verifyNotificationTagNodes" )
    public void MobileSearchService_searchWithPresetFilters_verifyNotificationTagNodes(String searchQuery, String rows, String returnDocs, String isFacet, 
    		String filters, String successRespCode)
    {
		RequestGenerator searchWithPresetFiltersServiceReqGen = mobileSearchServiceHelper.invokeSearchWithPresetFilters(searchQuery, rows, 
	       		returnDocs, isFacet, filters);
	    String searchWithPresetFiltersServiceResponse = searchWithPresetFiltersServiceReqGen.respvalidate.returnresponseasstring();
	    System.out.println("\nPrinting MobileSearchService searchWithPresetFilters API response :\n\n"+searchWithPresetFiltersServiceResponse+"\n");
		log.info("\nPrinting MobileSearchService searchWithPresetFilters API response :\n\n"+searchWithPresetFiltersServiceResponse+"\n");
			 
		MyntAssert.assertEquals("MobileSearchService searchWithPresetFilters API is not working",  Integer.parseInt(successRespCode), 
				searchWithPresetFiltersServiceReqGen.response.getStatus());
        
		AssertJUnit.assertTrue("Invalid notification tag data nodes in MobileSearchService searchWithPresetFilters API response", 
				searchWithPresetFiltersServiceReqGen.respvalidate.DoesNodesExists(MobileSearchServiceDataNodes.getMobileSearchServiceResponseNotificationTagNodes()));
		
        MyntAssert.setJsonResponse(searchWithPresetFiltersServiceResponse);
		MyntAssert.setExpectedAndActualForMyntListen(true);
    }
	
	/**
	 *  This TestCase is used to invoke MobileSearchService searchWithPresetFilters API and verification for requestObject tag nodes in the response
	 * 
	 * @param searchQuery
	 * @param rows
	 * @param returnDocs
	 * @param isFacet
	 * @param filters
	 * @param successRespCode
	 */
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },
			dataProvider = "MobileSearchServiceDP_searchWithPresetFilters_verifyRequestObjectDataNodes" )
    public void MobileSearchService_searchWithPresetFilters_verifyRequestObjectDataNodes(String searchQuery, String rows, String returnDocs, String isFacet, 
    		String filters, String successRespCode)
    {
		RequestGenerator searchWithPresetFiltersServiceReqGen = mobileSearchServiceHelper.invokeSearchWithPresetFilters(searchQuery, rows, 
	       		returnDocs, isFacet, filters);
	    String searchWithPresetFiltersServiceResponse = searchWithPresetFiltersServiceReqGen.respvalidate.returnresponseasstring();
	    System.out.println("\nPrinting MobileSearchService searchWithPresetFilters API response :\n\n"+searchWithPresetFiltersServiceResponse+"\n");
		log.info("\nPrinting MobileSearchService searchWithPresetFilters API response :\n\n"+searchWithPresetFiltersServiceResponse+"\n");
			 
		AssertJUnit.assertTrue("MetaTag code data value should be 200 in MobileSearchService searchWithPresetFilters API response", 
				searchWithPresetFiltersServiceReqGen.respvalidate.GetNodeValue(MobileSearchServiceDataNodes.MOBILE_SEARCH_RESP_META_CODE.getNodePath(), 
						searchWithPresetFiltersServiceResponse).equalsIgnoreCase(successRespCode));
        
		if(searchWithPresetFiltersServiceReqGen.respvalidate.DoesNodeExists(MobileSearchServiceDataNodes.MOBILE_SEARCH_RESP_DATA_REQ_OBJECT.getNodePath())){
			
			AssertJUnit.assertTrue("Invalid RequestObject tag data nodes in MobileSearchService searchWithPresetFilters API response", 
					searchWithPresetFiltersServiceReqGen.respvalidate.DoesNodesExists(MobileSearchServiceDataNodes.getRequestObjectDataTagNodes()));
			
			AssertJUnit.assertTrue("request and response rows tag nodes are not same", 
					searchWithPresetFiltersServiceReqGen.respvalidate.NodeValue(
							MobileSearchServiceDataNodes.MOBILE_SEARCH_RESP_DATA_REQ_OBJECT_ROWS.getNodePath(), false).equals(rows));
			
			AssertJUnit.assertTrue("request and response returnDocs tag nodes are not same", 
					searchWithPresetFiltersServiceReqGen.respvalidate.NodeValue(
							MobileSearchServiceDataNodes.MOBILE_SEARCH_RESP_DATA_REQ_OBJECT_RETURN_DOCS.getNodePath(), false).equals(returnDocs));
			
			AssertJUnit.assertTrue("request and response isFacet tag nodes are not same", 
					searchWithPresetFiltersServiceReqGen.respvalidate.NodeValue(
							MobileSearchServiceDataNodes.MOBILE_SEARCH_RESP_DATA_REQ_OBJECT_FACET.getNodePath(), false).equals(isFacet));
			
		} else {
			System.out.println("\nrequestObject data nodes doesnt exists in MobileSearchService searchWithPresetFilters API response\n");
			log.info("\nrequestObject data nodes doesnt exists in MobileSearchService searchWithPresetFilters API response\n");
		}
		
        MyntAssert.setJsonResponse(searchWithPresetFiltersServiceResponse);
		MyntAssert.setExpectedAndActualForMyntListen(true);
    }
	
	/**
	 * This TestCase is used to invoke MobileSearchService searchWithPresetFilters API and verification for requestObject curated_query tag nodes in the response
	 * 
	 * @param searchQuery
	 * @param rows
	 * @param returnDocs
	 * @param isFacet
	 * @param filters
	 * @param successRespCode
	 */
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },
			dataProvider = "MobileSearchServiceDP_searchWithPresetFilters_verifyRequestObjectWithCuratedQueryDataNodes" )
    public void MobileSearchService_searchWithPresetFilters_verifyRequestObjectWithCuratedQueryDataNodes(String searchQuery, String rows, String returnDocs, 
    		String isFacet, String filters, String successRespCode)
    {
		RequestGenerator searchWithPresetFiltersServiceReqGen = mobileSearchServiceHelper.invokeSearchWithPresetFilters(searchQuery, rows, 
	       		returnDocs, isFacet, filters);
	    String searchWithPresetFiltersServiceResponse = searchWithPresetFiltersServiceReqGen.respvalidate.returnresponseasstring();
	    System.out.println("\nPrinting MobileSearchService searchWithPresetFilters API response :\n\n"+searchWithPresetFiltersServiceResponse+"\n");
		log.info("\nPrinting MobileSearchService searchWithPresetFilters API response :\n\n"+searchWithPresetFiltersServiceResponse+"\n");
			 
		AssertJUnit.assertTrue("MetaTag code data value should be 200 in MobileSearchService searchWithPresetFilters API response", 
				searchWithPresetFiltersServiceReqGen.respvalidate.GetNodeValue(MobileSearchServiceDataNodes.MOBILE_SEARCH_RESP_META_CODE.getNodePath(), 
						searchWithPresetFiltersServiceResponse).equalsIgnoreCase(successRespCode));
        
		if(searchWithPresetFiltersServiceReqGen.respvalidate.DoesNodeExists(MobileSearchServiceDataNodes.MOBILE_SEARCH_RESP_DATA_REQ_OBJECT.getNodePath())
				&& searchWithPresetFiltersServiceReqGen.respvalidate.DoesNodeExists(
						MobileSearchServiceDataNodes.MOBILE_SEARCH_RESP_DATA_REQ_OBJECT_CURATED_QUERY.getNodePath())){
			
			AssertJUnit.assertTrue("Invalid RequestObjectWithCuratedQueryTag data nodes in MobileSearchService searchWithPresetFilters API response", 
					searchWithPresetFiltersServiceReqGen.respvalidate.DoesNodesExists(MobileSearchServiceDataNodes.getRequestObjectWithCuratedQueryDataTagNodes()));
			
			AssertJUnit.assertTrue("request and response rows tag nodes are not same", 
					searchWithPresetFiltersServiceReqGen.respvalidate.NodeValue(
							MobileSearchServiceDataNodes.MOBILE_SEARCH_RESP_DATA_REQ_OBJECT_CURATED_QUERY_ROWS.getNodePath(), false).equals(rows));
			
		} else {
			System.out.println("\nrequestObject curated_query data nodes doesnt exists in MobileSearchService searchWithPresetFilters API response\n");
			log.info("\nrequestObject curated_query data nodes doesnt exists in MobileSearchService searchWithPresetFilters API response\n");
		}
		
        MyntAssert.setJsonResponse(searchWithPresetFiltersServiceResponse);
		MyntAssert.setExpectedAndActualForMyntListen(true);
    }
	
	
	/**
	 * This TestCase is used to invoke MobileSearchService searchWithPresetFilters API and verification for filters tag nodes in the response
	 * 
	 * @param searchQuery
	 * @param rows
	 * @param returnDocs
	 * @param isFacet
	 * @param filters
	 * @param successRespCode
	 */
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },
			dataProvider = "MobileSearchServiceDP_searchWithPresetFilters_verifyBodyWithFiltersDataNodes" )
    public void MobileSearchService_searchWithPresetFilters_verifyBodyWithFiltersDataNodes(String searchQuery, String rows, String returnDocs, String isFacet,
    		String filters, String successRespCode)
    {
		RequestGenerator searchWithPresetFiltersServiceReqGen = mobileSearchServiceHelper.invokeSearchWithPresetFilters(searchQuery, rows, 
	       		returnDocs, isFacet, filters);
	    String searchWithPresetFiltersServiceResponse = searchWithPresetFiltersServiceReqGen.respvalidate.returnresponseasstring();
	    System.out.println("\nPrinting MobileSearchService searchWithPresetFilters API response :\n\n"+searchWithPresetFiltersServiceResponse+"\n");
		log.info("\nPrinting MobileSearchService searchWithPresetFilters API response :\n\n"+searchWithPresetFiltersServiceResponse+"\n");
			 
		AssertJUnit.assertTrue("MetaTag code data value should be 200 in MobileSearchService searchWithPresetFilters API response", 
				searchWithPresetFiltersServiceReqGen.respvalidate.GetNodeValue(MobileSearchServiceDataNodes.MOBILE_SEARCH_RESP_META_CODE.getNodePath(), 
						searchWithPresetFiltersServiceResponse).equalsIgnoreCase(successRespCode));
        
		if(searchWithPresetFiltersServiceReqGen.respvalidate.DoesNodeExists(MobileSearchServiceDataNodes.MOBIEL_SEARCH_RESP_DATA_BODY_FILTERS.getNodePath())){
			
			String responseFilterNodes = searchWithPresetFiltersServiceReqGen.respvalidate.NodeValue(
					MobileSearchServiceDataNodes.MOBIEL_SEARCH_RESP_DATA_BODY_FILTERS.getNodePath(), true);
			
			if(!responseFilterNodes.equals(mobileSearchServiceList.toString())){
				
				AssertJUnit.assertTrue("Invalid BodyWithFiltersDataNodes in MobileSearchService searchWithPresetFilters API response", 
						searchWithPresetFiltersServiceReqGen.respvalidate.DoesNodesExists(MobileSearchServiceDataNodes.getBodyWithFiltersDataTagNodes()));
			
			} else {
				System.out.println("\nfilters data nodes are empty in MobileSearchService searchWithPresetFilters API response\n");
				log.info("\nfilters data nodes are empty in MobileSearchService searchWithPresetFilters API response\n");
			}
			
		} else {
			System.out.println("\nfilters data nodes doesnt exists in MobileSearchService searchWithPresetFilters API response\n");
			log.info("\nfilters data nodes doesnt exists in MobileSearchService searchWithPresetFilters API response\n");
		}
		
        MyntAssert.setJsonResponse(searchWithPresetFiltersServiceResponse);
		MyntAssert.setExpectedAndActualForMyntListen(true);
    }
	
	/**
	 * This TestCase is used to invoke MobileSearchService searchWithPresetFilters API and verification for products tag nodes in the response 
	 * 
	 * @param searchQuery
	 * @param rows
	 * @param returnDocs
	 * @param isFacet
	 * @param filters
	 * @param successRespCode
	 */
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },
			dataProvider = "MobileSearchServiceDP_searchWithPresetFilters_verifyBodyWithProductsDataNodess" )
    public void MobileSearchService_searchWithPresetFilters_verifyBodyWithProductsDataNodes(String searchQuery, String rows, String returnDocs, String isFacet, 
    		String filters, String successRespCode)
    {
		RequestGenerator searchWithPresetFiltersServiceReqGen = mobileSearchServiceHelper.invokeSearchWithPresetFilters(searchQuery, rows, 
	       		returnDocs, isFacet, filters);
	    String searchWithPresetFiltersServiceResponse = searchWithPresetFiltersServiceReqGen.respvalidate.returnresponseasstring();
	    System.out.println("\nPrinting MobileSearchService searchWithPresetFilters API response :\n\n"+searchWithPresetFiltersServiceResponse+"\n");
		log.info("\nPrinting MobileSearchService searchWithPresetFilters API response :\n\n"+searchWithPresetFiltersServiceResponse+"\n");
			 
		AssertJUnit.assertTrue("MetaTag code data value should be 200 in MobileSearchService searchWithPresetFilters API response", 
				searchWithPresetFiltersServiceReqGen.respvalidate.GetNodeValue(MobileSearchServiceDataNodes.MOBILE_SEARCH_RESP_META_CODE.getNodePath(), 
						searchWithPresetFiltersServiceResponse).equalsIgnoreCase(successRespCode));
        
		if(searchWithPresetFiltersServiceReqGen.respvalidate.DoesNodeExists(MobileSearchServiceDataNodes.MOBILE_SEARCH_RESP_DATA_BODY_PRODUCTS.getNodePath())){
			
			String responseProductsNodes = searchWithPresetFiltersServiceReqGen.respvalidate.NodeValue(
					MobileSearchServiceDataNodes.MOBILE_SEARCH_RESP_DATA_BODY_PRODUCTS.getNodePath(), true);
			
			if(!responseProductsNodes.equals(mobileSearchServiceList.toString())){
				
				AssertJUnit.assertTrue("Invalid BodyWithProductsDataNodes data nodes in MobileSearchService searchWithPresetFilters API response", 
						searchWithPresetFiltersServiceReqGen.respvalidate.DoesNodesExists(MobileSearchServiceDataNodes.getBodyWithProductsDataTagNodes()));
			
			} else {
				System.out.println("\nproducts data nodes are empty in MobileSearchService searchWithPresetFilters API response\n");
				log.info("\nproducts data nodes are empty in MobileSearchService searchWithPresetFilters API response\n");
			}
			
		} else {
			System.out.println("\nproducts data nodes doesnt exists in MobileSearchService searchWithPresetFilters API response\n");
			log.info("\nproducts data nodes doesnt exists in MobileSearchService searchWithPresetFilters API response\n");
		}
		
        MyntAssert.setJsonResponse(searchWithPresetFiltersServiceResponse);
		MyntAssert.setExpectedAndActualForMyntListen(true);
    }
	
	/**
	 * This TestCase is used to invoke MobileSearchService getFirstPageOfSearch API and verification for success response
	 * and invoke MobileSearchService searchAndApplySingleFilter API and verification for success response
	 * 
	 * @param searchQuery
	 * @param rows
	 * @param returnDocs
	 * @param isFacet
	 * @param singleFilters
	 * @param successRespCode
	 */
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "MiniRegression", "Regression", "ExhaustiveRegression" },
			dataProvider = "MobileSearchServiceDP_searchAndApplySingleFilter_verifySuccessResponse" )
    public void MobileSearchService_searchAndApplySingleFilter_verifySuccessResponse(String searchQuery, String rows, String returnDocs, String isFacet, 
    		String singleFilters, String successRespCode)
    {
		// invoke getFirstPageOfSearch API
		RequestGenerator getFirstPageOfMobileSearchServiceReqGen = mobileSearchServiceHelper.invokeGetFirstPageOfSearch(searchQuery, rows, 
				returnDocs, isFacet);
		String getFirstPageOfMobileSearchServiceResponse = getFirstPageOfMobileSearchServiceReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting MobileSearchService getFirstPageOfSearch API response :\n\n"+getFirstPageOfMobileSearchServiceResponse+"\n");
		log.info("\nPrinting MobileSearchService getFirstPageOfSearch API response :\n\n"+getFirstPageOfMobileSearchServiceResponse+"\n");
			
		MyntAssert.assertEquals("MobileSearchService getFirstPageOfSearch API is not working",  Integer.parseInt(successRespCode), 
				getFirstPageOfMobileSearchServiceReqGen.response.getStatus());
		
		String requestObj = JsonPath.read(getFirstPageOfMobileSearchServiceResponse, "$.data.requestObj").toString();
		
		// invoke searchAndApplyFilters
        RequestGenerator searchAndApplySingleFilterReqGen = mobileSearchServiceHelper.invokeSearchAndApplyFilters(singleFilters, requestObj);
        String searchAndApplySingleFilterResponse = searchAndApplySingleFilterReqGen.respvalidate.returnresponseasstring();
        System.out.println("\nPrinting MobileSearchService searchAndApplySingleFilter API response :\n\n"+searchAndApplySingleFilterResponse+"\n");
		log.info("\nPrinting MobileSearchService searchAndApplySingleFilter API response :\n\n"+searchAndApplySingleFilterResponse+"\n");
		 
		MyntAssert.assertEquals("MobileSearchService searchAndApplySingleFilter API is not working",  Integer.parseInt(successRespCode), 
				searchAndApplySingleFilterReqGen.response.getStatus());
        
        MyntAssert.setJsonResponse(searchAndApplySingleFilterResponse);
		MyntAssert.setExpectedAndActualForMyntListen(true);
    }
	
	/**
	 * This TestCase is used to invoke MobileSearchService getFirstPageOfSearch API and verification for success response
	 * and invoke MobileSearchService searchAndApplySingleFilter API and verification for headers information in the response
	 * 
	 * @param searchQuery
	 * @param rows
	 * @param returnDocs
	 * @param isFacet
	 * @param singleFilter
	 * @param successRespCode
	 */
	@Test(groups={ "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" }, 
			dataProvider="MobileSearchServiceDP_searchAndApplySingleFilter_verifyResponseHeaders")
    public void MobileSearchService_searchAndApplySingleFilter_verifyResponseHeaders(String searchQuery, String rows, String returnDocs, String isFacet, 
    		String singleFilter, String successRespCode)
    {
		// invoke getFirstPageOfSearch API
		RequestGenerator getFirstPageOfMobileSearchServiceReqGen = mobileSearchServiceHelper.invokeGetFirstPageOfSearch(searchQuery, rows, returnDocs, isFacet);
		String getFirstPageOfMobileSearchServiceResponse = getFirstPageOfMobileSearchServiceReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting MobileSearchService getFirstPageOfSearch API response :\n\n"+getFirstPageOfMobileSearchServiceResponse+"\n");
		log.info("\nPrinting MobileSearchService getFirstPageOfSearch API response :\n\n"+getFirstPageOfMobileSearchServiceResponse+"\n");
			
		MyntAssert.assertEquals("MobileSearchService getFirstPageOfSearch API is not working",  Integer.parseInt(successRespCode), 
				getFirstPageOfMobileSearchServiceReqGen.response.getStatus());
		
		String requestObj = JsonPath.read(getFirstPageOfMobileSearchServiceResponse, "$.data.requestObj").toString();	
		
		// invoke searchAndApplyFilters
        RequestGenerator searchAndApplySingleFilterReqGen = mobileSearchServiceHelper.invokeSearchAndApplyFilters(singleFilter, requestObj);
        String searchAndApplySingleFilterResponse = searchAndApplySingleFilterReqGen.respvalidate.returnresponseasstring();
        System.out.println("\nPrinting MobileSearchService searchAndApplySingleFilter API response :\n\n"+searchAndApplySingleFilterResponse+"\n");
		log.info("\nPrinting MobileSearchService searchAndApplySingleFilter API response :\n\n"+searchAndApplySingleFilterResponse+"\n");
		
		MyntAssert.assertEquals("MobileSearchService searchAndApplySingleFilter API is not working",  Integer.parseInt(successRespCode), 
				searchAndApplySingleFilterReqGen.response.getStatus());
		
        MultivaluedMap<String, Object> searchAndApplySingleFilterResponseHeaders = searchAndApplySingleFilterReqGen.response.getHeaders();
        System.out.println("\nMobileSearchService searchAndApplySingleFilter API response headers : "+searchAndApplySingleFilterResponseHeaders+"\n");
        log.info("\nMobileSearchService searchAndApplySingleFilter API response headers : "+searchAndApplySingleFilterResponseHeaders+"\n");
        
        MyntAssert.assertEquals("Invalid header information in MobileSearchService searchAndApplySingleFilter API response", "success", 
        		validateResponseHeaders(searchAndApplySingleFilterResponseHeaders));
        
        MyntAssert.setJsonResponse(searchAndApplySingleFilterResponse);
		MyntAssert.setExpectedAndActualForMyntListen(true);
		
    }
	
	/**
	 * This TestCase is used to invoke MobileSearchService getFirstPageOfSearch API and verification for success response
	 * and invoke MobileSearchService searchAndApplySingleFilter API and verification for meta tag nodes in the response
	 * 
	 * @param searchQuery
	 * @param rows
	 * @param returnDocs
	 * @param isFacet
	 * @param singleFilter
	 * @param successRespCode
	 */
	@Test(groups={ "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" }, 
			dataProvider="MobileSearchServiceDP_searchAndApplySingleFilter_verifyMetaTagNodes")
    public void MobileSearchService_searchAndApplySingleFilter_verifyMetaTagNodes(String searchQuery, String rows, String returnDocs, String isFacet, 
    		String singleFilter, String successRespCode)
    {
		// invoke getFirstPageOfSearch API
		RequestGenerator getFirstPageOfMobileSearchServiceReqGen = mobileSearchServiceHelper.invokeGetFirstPageOfSearch(searchQuery, rows, returnDocs, isFacet);
		String getFirstPageOfMobileSearchServiceResponse = getFirstPageOfMobileSearchServiceReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting MobileSearchService getFirstPageOfSearch API response :\n\n"+getFirstPageOfMobileSearchServiceResponse+"\n");
		log.info("\nPrinting MobileSearchService getFirstPageOfSearch API response :\n\n"+getFirstPageOfMobileSearchServiceResponse+"\n");
			
		MyntAssert.assertEquals("MobileSearchService getFirstPageOfSearch API is not working",  Integer.parseInt(successRespCode), 
				getFirstPageOfMobileSearchServiceReqGen.response.getStatus());
		
		String requestObj = JsonPath.read(getFirstPageOfMobileSearchServiceResponse, "$.data.requestObj").toString();	
		
		// invoke searchAndApplyFilters
        RequestGenerator searchAndApplySingleFilterReqGen = mobileSearchServiceHelper.invokeSearchAndApplyFilters(singleFilter, requestObj);
        String searchAndApplySingleFilterResponse = searchAndApplySingleFilterReqGen.respvalidate.returnresponseasstring();
        System.out.println("\nPrinting MobileSearchService searchAndApplySingleFilter API response :\n\n"+searchAndApplySingleFilterResponse+"\n");
		log.info("\nPrinting MobileSearchService searchAndApplySingleFilter API response :\n\n"+searchAndApplySingleFilterResponse+"\n");
		
		MyntAssert.assertEquals("MobileSearchService searchAndApplySingleFilter API is not working",  Integer.parseInt(successRespCode), 
				searchAndApplySingleFilterReqGen.response.getStatus());
        
		AssertJUnit.assertTrue("Invalid MetaTag data nodes in MobileSearchService searchAndApplySingleFilter API response", 
				searchAndApplySingleFilterReqGen.respvalidate.DoesNodesExists(MobileSearchServiceDataNodes.getMobileSearchServiceResponseMetaTagNodes()));
		
		AssertJUnit.assertTrue("MetaTag code data value should be 200 in MobileSearchService searchAndApplySingleFilter API response", 
				searchAndApplySingleFilterReqGen.respvalidate.GetNodeValue(MobileSearchServiceDataNodes.MOBILE_SEARCH_RESP_META_CODE.getNodePath(), 
						searchAndApplySingleFilterResponse).equalsIgnoreCase(successRespCode));
		
		MyntAssert.assertEquals("MetaTag errorDetail data value should be null in MobileSearchService searchAndApplySingleFilter API response", "null", 
				searchAndApplySingleFilterReqGen.respvalidate.GetNodeValue(MobileSearchServiceDataNodes.MOBILE_SEARCH_RESP_META_ERROR_DETAIL.getNodePath(),
						searchAndApplySingleFilterResponse));
		
        MyntAssert.setJsonResponse(searchAndApplySingleFilterResponse);
		MyntAssert.setExpectedAndActualForMyntListen(true);
		
    }
	
	
	/**
	 * This TestCase is used to invoke MobileSearchService getFirstPageOfSearch API and verification for success response
	 * and invoke MobileSearchService searchAndApplySingleFilter API and verification for notification tag nodes in the response
	 * 
	 * @param searchQuery
	 * @param rows
	 * @param returnDocs
	 * @param isFacet
	 * @param singleFilter
	 * @param successRespCode
	 */
	@Test(groups={ "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" }, 
			dataProvider="MobileSearchServiceDP_searchAndApplySingleFilter_verifyNotificationTagNodes")
    public void MobileSearchService_searchAndApplySingleFilter_verifyNotificationTagNodes(String searchQuery, String rows, String returnDocs, String isFacet, 
    		String singleFilter, String successRespCode)
    {
		// invoke getFirstPageOfSearch API
		RequestGenerator getFirstPageOfMobileSearchServiceReqGen = mobileSearchServiceHelper.invokeGetFirstPageOfSearch(searchQuery, rows, returnDocs, isFacet);
		String getFirstPageOfMobileSearchServiceResponse = getFirstPageOfMobileSearchServiceReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting MobileSearchService getFirstPageOfSearch API response :\n\n"+getFirstPageOfMobileSearchServiceResponse+"\n");
		log.info("\nPrinting MobileSearchService getFirstPageOfSearch API response :\n\n"+getFirstPageOfMobileSearchServiceResponse+"\n");
			
		MyntAssert.assertEquals("MobileSearchService getFirstPageOfSearch API is not working",  Integer.parseInt(successRespCode), 
				getFirstPageOfMobileSearchServiceReqGen.response.getStatus());
		
		String requestObj = JsonPath.read(getFirstPageOfMobileSearchServiceResponse, "$.data.requestObj").toString();	
		
		// invoke searchAndApplyFilters
        RequestGenerator searchAndApplySingleFilterReqGen = mobileSearchServiceHelper.invokeSearchAndApplyFilters(singleFilter, requestObj);
        String searchAndApplySingleFilterResponse = searchAndApplySingleFilterReqGen.respvalidate.returnresponseasstring();
        System.out.println("\nPrinting MobileSearchService searchAndApplySingleFilter API response :\n\n"+searchAndApplySingleFilterResponse+"\n");
		log.info("\nPrinting MobileSearchService searchAndApplySingleFilter API response :\n\n"+searchAndApplySingleFilterResponse+"\n");
		
		MyntAssert.assertEquals("MobileSearchService getNextPageOfSearch API is not working",  Integer.parseInt(successRespCode), 
				searchAndApplySingleFilterReqGen.response.getStatus());
        
		AssertJUnit.assertTrue("Invalid notification tag data nodes in MobileSearchService searchAndApplySingleFilter API response", 
				searchAndApplySingleFilterReqGen.respvalidate.DoesNodesExists(MobileSearchServiceDataNodes.getMobileSearchServiceResponseNotificationTagNodes()));
		
        MyntAssert.setJsonResponse(searchAndApplySingleFilterResponse);
		MyntAssert.setExpectedAndActualForMyntListen(true);
		
    }
	
	/**
	 * This TestCase is used to invoke MobileSearchService getFirstPageOfSearch API and verification for success response
	 * and invoke MobileSearchService searchAndApplySingleFilter API and verification for requestObject tag nodes in the response
	 * 
	 * @param searchQuery
	 * @param rows
	 * @param returnDocs
	 * @param isFacet
	 * @param singleFilter
	 * @param successRespCode
	 */
	@Test(groups={ "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" }, 
			dataProvider="MobileSearchServiceDP_searchAndApplySingleFilter_verifyRequestObjectDataNodes")
    public void MobileSearchService_searchAndApplySingleFilter_verifyRequestObjectDataNodes(String searchQuery, String rows, String returnDocs, String isFacet, 
    		String singleFilter, String successRespCode)
    {
		// invoke getFirstPageOfSearch API
		RequestGenerator getFirstPageOfMobileSearchServiceReqGen = mobileSearchServiceHelper.invokeGetFirstPageOfSearch(searchQuery, rows, returnDocs, isFacet);
		String getFirstPageOfMobileSearchServiceResponse = getFirstPageOfMobileSearchServiceReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting MobileSearchService getFirstPageOfSearch API response :\n\n"+getFirstPageOfMobileSearchServiceResponse+"\n");
		log.info("\nPrinting MobileSearchService getFirstPageOfSearch API response :\n\n"+getFirstPageOfMobileSearchServiceResponse+"\n");
			
		MyntAssert.assertEquals("MobileSearchService getFirstPageOfSearch API is not working",  Integer.parseInt(successRespCode), 
				getFirstPageOfMobileSearchServiceReqGen.response.getStatus());
		
		String requestObj = JsonPath.read(getFirstPageOfMobileSearchServiceResponse, "$.data.requestObj").toString();	
		
		// invoke searchAndApplyFilters
        RequestGenerator searchAndApplySingleFilterReqGen = mobileSearchServiceHelper.invokeSearchAndApplyFilters(singleFilter, requestObj);
        String searchAndApplySingleFilterResponse = searchAndApplySingleFilterReqGen.respvalidate.returnresponseasstring();
        System.out.println("\nPrinting MobileSearchService searchAndApplySingleFilter API response :\n\n"+searchAndApplySingleFilterResponse+"\n");
		log.info("\nPrinting MobileSearchService searchAndApplySingleFilter API response :\n\n"+searchAndApplySingleFilterResponse+"\n");
		
		AssertJUnit.assertTrue("MetaTag code data value should be 200 in MobileSearchService searchAndApplySingleFilter API response", 
				searchAndApplySingleFilterReqGen.respvalidate.GetNodeValue(MobileSearchServiceDataNodes.MOBILE_SEARCH_RESP_META_CODE.getNodePath(), 
						searchAndApplySingleFilterResponse).equalsIgnoreCase(successRespCode));
        
		if(searchAndApplySingleFilterReqGen.respvalidate.DoesNodeExists(MobileSearchServiceDataNodes.MOBILE_SEARCH_RESP_DATA_REQ_OBJECT.getNodePath())){
			
			AssertJUnit.assertTrue("Invalid requestObject data tag nodes in MobileSearchService searchAndApplySingleFilter API response", 
					searchAndApplySingleFilterReqGen.respvalidate.DoesNodesExists(MobileSearchServiceDataNodes.getRequestObjectDataTagNodes()));
			
			MyntAssert.assertEquals("request and response rows tag nodes are not same", searchAndApplySingleFilterReqGen.respvalidate.NodeValue(
					MobileSearchServiceDataNodes.MOBILE_SEARCH_RESP_DATA_REQ_OBJECT_ROWS.getNodePath(), false), rows);
			
			MyntAssert.assertEquals("request and response returnDocs tag nodes are not same", 
					searchAndApplySingleFilterReqGen.respvalidate.NodeValue(
							MobileSearchServiceDataNodes.MOBILE_SEARCH_RESP_DATA_REQ_OBJECT_RETURN_DOCS.getNodePath(), false), returnDocs);
			
			MyntAssert.assertEquals("request and response isFacet tag nodes are not same", 
					searchAndApplySingleFilterReqGen.respvalidate.NodeValue(
							MobileSearchServiceDataNodes.MOBILE_SEARCH_RESP_DATA_REQ_OBJECT_FACET.getNodePath(), false), isFacet);
			
		} else {
			System.out.println("\nrequestObject data nodes doesnt exists in MobileSearchService searchAndApplySingleFilter API response\n");
			log.info("\nrequestObject data nodes doesnt exists in MobileSearchService searchAndApplySingleFilter API response\n");
		}
		
        MyntAssert.setJsonResponse(searchAndApplySingleFilterResponse);
		MyntAssert.setExpectedAndActualForMyntListen(true);
		
    }
	
	/**
	 * This TestCase is used to invoke MobileSearchService getFirstPageOfSearch API and verification for success response
	 * and invoke MobileSearchService searchAndApplySingleFilter API and verification for requestObject curated_query tag nodes in the response
	 * 
	 * @param searchQuery
	 * @param rows
	 * @param returnDocs
	 * @param isFacet
	 * @param singleFilter
	 * @param successRespCode
	 */
	@Test(groups={ "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" }, 
			dataProvider="MobileSearchServiceDP_searchAndApplySingleFilter_verifyRequestObjectWithCuratedQueryDataNodes")
    public void MobileSearchService_searchAndApplySingleFilter_verifyRequestObjectWithCuratedQueryDataNodes(String searchQuery, String rows, String returnDocs, 
    		String isFacet, String singleFilter, String successRespCode)
    {
		// invoke getFirstPageOfSearch API
		RequestGenerator getFirstPageOfMobileSearchServiceReqGen = mobileSearchServiceHelper.invokeGetFirstPageOfSearch(searchQuery, rows, returnDocs, isFacet);
		String getFirstPageOfMobileSearchServiceResponse = getFirstPageOfMobileSearchServiceReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting MobileSearchService getFirstPageOfSearch API response :\n\n"+getFirstPageOfMobileSearchServiceResponse+"\n");
		log.info("\nPrinting MobileSearchService getFirstPageOfSearch API response :\n\n"+getFirstPageOfMobileSearchServiceResponse+"\n");
			
		MyntAssert.assertEquals("MobileSearchService getFirstPageOfSearch API is not working",  Integer.parseInt(successRespCode), 
				getFirstPageOfMobileSearchServiceReqGen.response.getStatus());
		
		String requestObj = JsonPath.read(getFirstPageOfMobileSearchServiceResponse, "$.data.requestObj").toString();	
		
		// invoke searchAndApplyFilters
        RequestGenerator searchAndApplySingleFilterReqGen = mobileSearchServiceHelper.invokeSearchAndApplyFilters(singleFilter, requestObj);
        String searchAndApplySingleFilterResponse = searchAndApplySingleFilterReqGen.respvalidate.returnresponseasstring();
        System.out.println("\nPrinting MobileSearchService searchAndApplySingleFilter API response :\n\n"+searchAndApplySingleFilterResponse+"\n");
		log.info("\nPrinting MobileSearchService searchAndApplySingleFilter API response :\n\n"+searchAndApplySingleFilterResponse+"\n");
		
		AssertJUnit.assertTrue("MetaTag code data value should be 200 in MobileSearchService searchAndApplySingleFilter API response", 
				searchAndApplySingleFilterReqGen.respvalidate.GetNodeValue(MobileSearchServiceDataNodes.MOBILE_SEARCH_RESP_META_CODE.getNodePath(), 
						searchAndApplySingleFilterResponse).equalsIgnoreCase(successRespCode));
		
		if(searchAndApplySingleFilterReqGen.respvalidate.DoesNodeExists(MobileSearchServiceDataNodes.MOBILE_SEARCH_RESP_DATA_REQ_OBJECT.getNodePath())
				&& searchAndApplySingleFilterReqGen.respvalidate.DoesNodeExists(
						MobileSearchServiceDataNodes.MOBILE_SEARCH_RESP_DATA_REQ_OBJECT_CURATED_QUERY.getNodePath())){
			
			AssertJUnit.assertTrue("Invalid requestObject curated_query data nodes in MobileSearchService searchAndApplySingleFilter API response", 
					searchAndApplySingleFilterReqGen.respvalidate.DoesNodesExists(MobileSearchServiceDataNodes.getRequestObjectWithCuratedQueryDataTagNodes()));
			
			MyntAssert.assertEquals("request and response rows tag nodes are not same", searchAndApplySingleFilterReqGen.respvalidate.NodeValue(
					MobileSearchServiceDataNodes.MOBILE_SEARCH_RESP_DATA_REQ_OBJECT_CURATED_QUERY_ROWS.getNodePath(), false), rows);
			
		} else {
			System.out.println("\nrequestObject curated_query data nodes doesnt exists in MobileSearchService searchAndApplySingleFilter API response\n");
			log.info("\nrequestObject curated_query data nodes doesnt exists in MobileSearchService searchAndApplySingleFilter API response\n");
		}
		
        MyntAssert.setJsonResponse(searchAndApplySingleFilterResponse);
		MyntAssert.setExpectedAndActualForMyntListen(true);
		
    }
	
	/**
	 * This TestCase is used to invoke MobileSearchService getFirstPageOfSearch API and verification for success response
	 * and invoke MobileSearchService searchAndApplySingleFilter API and verification for filters data tag nodes in the response 
	 * 
	 * @param searchQuery
	 * @param rows
	 * @param returnDocs
	 * @param isFacet
	 * @param singleFilter
	 * @param successRespCode
	 */
	@Test(groups={ "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" }, 
			dataProvider="MobileSearchServiceDP_searchAndApplySingleFilter_verifyBodyWithFiltersDataNodes")
    public void MobileSearchService_searchAndApplySingleFilter_verifyBodyWithFiltersDataNodes(String searchQuery, String rows, String returnDocs, String isFacet, 
    		String singleFilter, String successRespCode)
    {
		// invoke getFirstPageOfSearch API
		RequestGenerator getFirstPageOfMobileSearchServiceReqGen = mobileSearchServiceHelper.invokeGetFirstPageOfSearch(searchQuery, rows, returnDocs, isFacet);
		String getFirstPageOfMobileSearchServiceResponse = getFirstPageOfMobileSearchServiceReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting MobileSearchService getFirstPageOfSearch API response :\n\n"+getFirstPageOfMobileSearchServiceResponse+"\n");
		log.info("\nPrinting MobileSearchService getFirstPageOfSearch API response :\n\n"+getFirstPageOfMobileSearchServiceResponse+"\n");
			
		MyntAssert.assertEquals("MobileSearchService getFirstPageOfSearch API is not working",  Integer.parseInt(successRespCode), 
				getFirstPageOfMobileSearchServiceReqGen.response.getStatus());
		
		String requestObj = JsonPath.read(getFirstPageOfMobileSearchServiceResponse, "$.data.requestObj").toString();	
		
		// invoke searchAndApplyFilters
        RequestGenerator searchAndApplySingleFilterReqGen = mobileSearchServiceHelper.invokeSearchAndApplyFilters(singleFilter, requestObj);
        String searchAndApplySingleFilterResponse = searchAndApplySingleFilterReqGen.respvalidate.returnresponseasstring();
        System.out.println("\nPrinting MobileSearchService searchAndApplySingleFilter API response :\n\n"+searchAndApplySingleFilterResponse+"\n");
		log.info("\nPrinting MobileSearchService searchAndApplySingleFilter API response :\n\n"+searchAndApplySingleFilterResponse+"\n");
		
		AssertJUnit.assertTrue("MetaTag code data value should be 200 in MobileSearchService searchAndApplySingleFilter API response", 
				searchAndApplySingleFilterReqGen.respvalidate.GetNodeValue(MobileSearchServiceDataNodes.MOBILE_SEARCH_RESP_META_CODE.getNodePath(), 
						searchAndApplySingleFilterResponse).equalsIgnoreCase(successRespCode));
        
		if(searchAndApplySingleFilterReqGen.respvalidate.DoesNodeExists(MobileSearchServiceDataNodes.MOBIEL_SEARCH_RESP_DATA_BODY_FILTERS.getNodePath())){
		
			String responseFilterNodes = searchAndApplySingleFilterReqGen.respvalidate.NodeValue(
					MobileSearchServiceDataNodes.MOBIEL_SEARCH_RESP_DATA_BODY_FILTERS.getNodePath(), true);
			
			if(!responseFilterNodes.equals(mobileSearchServiceList.toString())){
				
				AssertJUnit.assertTrue("Invalid BodyWithFiltersDataNodes in MobileSearchService searchAndApplySingleFilter API response", 
						searchAndApplySingleFilterReqGen.respvalidate.DoesNodesExists(MobileSearchServiceDataNodes.getBodyWithFiltersDataTagNodes()));
			
			} else {
				System.out.println("\nfilters data nodes are empty in MobileSearchService searchAndApplySingleFilter API response\n");
				log.info("\nfilters data nodes are empty in MobileSearchService searchAndApplySingleFilter API response\n");
			}
			
		} else {
			System.out.println("\nfilters data nodes doesnt exists in MobileSearchService searchAndApplySingleFilter API response\n");
			log.info("\nfilters data nodes doesnt exists in MobileSearchService searchAndApplySingleFilter API response\n");
		}
		
        MyntAssert.setJsonResponse(searchAndApplySingleFilterResponse);
		MyntAssert.setExpectedAndActualForMyntListen(true);
		
    }
	
	/**
	 * This TestCase is used to invoke MobileSearchService getFirstPageOfSearch API and verification for success response
	 * and invoke MobileSearchService searchAndApplySingleFilter API and verification for products data tag nodes in the response 
	 * 
	 * @param searchQuery
	 * @param rows
	 * @param returnDocs
	 * @param isFacet
	 * @param singleFilter
	 * @param successRespCode
	 */
	@Test(groups={ "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" }, 
			dataProvider="MobileSearchServiceDP_searchAndApplySingleFilter_verifyBodyWithProductsDataNodes")
    public void MobileSearchService_searchAndApplySingleFilter_verifyBodyWithProductsDataNodes(String searchQuery, String rows, String returnDocs, String isFacet, 
    		String singleFilter, String successRespCode)
    {
		// invoke getFirstPageOfSearch API
		RequestGenerator getFirstPageOfMobileSearchServiceReqGen = mobileSearchServiceHelper.invokeGetFirstPageOfSearch(searchQuery, rows, returnDocs, isFacet);
		String getFirstPageOfMobileSearchServiceResponse = getFirstPageOfMobileSearchServiceReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting MobileSearchService getFirstPageOfSearch API response :\n\n"+getFirstPageOfMobileSearchServiceResponse+"\n");
		log.info("\nPrinting MobileSearchService getFirstPageOfSearch API response :\n\n"+getFirstPageOfMobileSearchServiceResponse+"\n");
			
		MyntAssert.assertEquals("MobileSearchService getFirstPageOfSearch API is not working",  Integer.parseInt(successRespCode), 
				getFirstPageOfMobileSearchServiceReqGen.response.getStatus());
		
		String requestObj = JsonPath.read(getFirstPageOfMobileSearchServiceResponse, "$.data.requestObj").toString();	
		
		// invoke searchAndApplyFilters
        RequestGenerator searchAndApplySingleFilterReqGen = mobileSearchServiceHelper.invokeSearchAndApplyFilters(singleFilter, requestObj);
        String searchAndApplySingleFilterResponse = searchAndApplySingleFilterReqGen.respvalidate.returnresponseasstring();
        System.out.println("\nPrinting MobileSearchService searchAndApplySingleFilter API response :\n\n"+searchAndApplySingleFilterResponse+"\n");
		log.info("\nPrinting MobileSearchService searchAndApplySingleFilter API response :\n\n"+searchAndApplySingleFilterResponse+"\n");
		
		AssertJUnit.assertTrue("MetaTag code data value should be 200 in MobileSearchService searchAndApplySingleFilter API response", 
				searchAndApplySingleFilterReqGen.respvalidate.GetNodeValue(MobileSearchServiceDataNodes.MOBILE_SEARCH_RESP_META_CODE.getNodePath(), 
						searchAndApplySingleFilterResponse).equalsIgnoreCase(successRespCode));
        
		if(searchAndApplySingleFilterReqGen.respvalidate.DoesNodeExists(MobileSearchServiceDataNodes.MOBILE_SEARCH_RESP_DATA_BODY_PRODUCTS.getNodePath())){
			
			String responseProductNodes = searchAndApplySingleFilterReqGen.respvalidate.NodeValue(
					MobileSearchServiceDataNodes.MOBILE_SEARCH_RESP_DATA_BODY_PRODUCTS.getNodePath(), true);
			
			if(!responseProductNodes.equals(mobileSearchServiceList.toString())){
				
				AssertJUnit.assertTrue("Invalid products data nodes in MobileSearchService searchAndApplySingleFilter API response", 
						searchAndApplySingleFilterReqGen.respvalidate.DoesNodesExists(MobileSearchServiceDataNodes.getBodyWithProductsDataTagNodes()));
				
			} else {
				System.out.println("\nproducts data nodes are empty in MobileSearchService searchAndApplySingleFilter API response\n");
				log.info("\nproducts data nodes are empty in MobileSearchService searchAndApplySingleFilter API response\n");
			}
			
		} else {
			System.out.println("\nproducts data nodes doesnt exists in MobileSearchService searchAndApplySingleFilter API response\n");
			log.info("\nproducts data nodes doesnt exists in MobileSearchService searchAndApplySingleFilter API response\n");
		}
		
        MyntAssert.setJsonResponse(searchAndApplySingleFilterResponse);
		MyntAssert.setExpectedAndActualForMyntListen(true);
		
    }
	
	/**
	 * This TestCase is used to invoke MobileSearchService getFirstPageOfSearch API and verification for success response
	 * and invoke MobileSearchService searchAndApplyDoubleFilters API and verification for success response
	 * 
	 * @param searchQuery
	 * @param rows
	 * @param returnDocs
	 * @param isFacet
	 * @param doubleFilters
	 * @param successRespCode
	 */
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "MiniRegression", "Regression", "ExhaustiveRegression" },
			dataProvider = "MobileSearchServiceDP_searchAndApplyDoubleFilters_verifySuccessResponse" )
    public void MobileSearchService_searchAndApplyDoubleFilters_verifySuccessResponse(String searchQuery, String rows, String returnDocs, String isFacet, 
    		String doubleFilters, String successRespCode)
    {
		// invoke getFirstPageOfSearch API
		RequestGenerator getFirstPageOfMobileSearchServiceReqGen = mobileSearchServiceHelper.invokeGetFirstPageOfSearch(searchQuery, rows, 
				returnDocs, isFacet);
		String getFirstPageOfMobileSearchServiceResponse = getFirstPageOfMobileSearchServiceReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting MobileSearchService getFirstPageOfSearch API response :\n\n"+getFirstPageOfMobileSearchServiceResponse+"\n");
		log.info("\nPrinting MobileSearchService getFirstPageOfSearch API response :\n\n"+getFirstPageOfMobileSearchServiceResponse+"\n");
			
		MyntAssert.assertEquals("MobileSearchService getFirstPageOfSearch API is not working",  Integer.parseInt(successRespCode), 
				getFirstPageOfMobileSearchServiceReqGen.response.getStatus());
		
		String requestObj = JsonPath.read(getFirstPageOfMobileSearchServiceResponse, "$.data.requestObj").toString();
		
		// invoke searchAndApplyFilters
        RequestGenerator searchAndApplyDoubleFilterReqGen = mobileSearchServiceHelper.invokeSearchAndApplyFilters(doubleFilters, requestObj);
        String searchAndApplyDoubleFiltersResponse = searchAndApplyDoubleFilterReqGen.respvalidate.returnresponseasstring();
        System.out.println("\nPrinting MobileSearchService searchAndApplyDoubleFilters API response :\n\n"+searchAndApplyDoubleFiltersResponse+"\n");
		log.info("\nPrinting MobileSearchService searchAndApplyDoubleFilters API response :\n\n"+searchAndApplyDoubleFiltersResponse+"\n");
		 
		MyntAssert.assertEquals("MobileSearchService searchAndApplyDoubleFilters API is not working",  Integer.parseInt(successRespCode), 
				searchAndApplyDoubleFilterReqGen.response.getStatus());
        
        MyntAssert.setJsonResponse(searchAndApplyDoubleFiltersResponse);
		MyntAssert.setExpectedAndActualForMyntListen(true);
    }
	
	/**
	 * This TestCase is used to invoke MobileSearchService getFirstPageOfSearch API and verification for success response
	 * and invoke MobileSearchService searchAndApplyDoubleFilters API and verification for headers information in the response
	 * 
	 * @param searchQuery
	 * @param rows
	 * @param returnDocs
	 * @param isFacet
	 * @param doubleFilters
	 * @param successRespCode
	 */
	@Test(groups={ "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" }, 
			dataProvider="MobileSearchServiceDP_searchAndApplyDoubleFilters_verifyResponseHeaders")
    public void MobileSearchService_searchAndApplyDoubleFilters_verifyResponseHeaders(String searchQuery, String rows, String returnDocs, String isFacet, 
    		String doubleFilters, String successRespCode)
    {
		// invoke getFirstPageOfSearch API
		RequestGenerator getFirstPageOfMobileSearchServiceReqGen = mobileSearchServiceHelper.invokeGetFirstPageOfSearch(searchQuery, rows, returnDocs, isFacet);
		String getFirstPageOfMobileSearchServiceResponse = getFirstPageOfMobileSearchServiceReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting MobileSearchService getFirstPageOfSearch API response :\n\n"+getFirstPageOfMobileSearchServiceResponse+"\n");
		log.info("\nPrinting MobileSearchService getFirstPageOfSearch API response :\n\n"+getFirstPageOfMobileSearchServiceResponse+"\n");
			
		MyntAssert.assertEquals("MobileSearchService getFirstPageOfSearch API is not working",  Integer.parseInt(successRespCode), 
				getFirstPageOfMobileSearchServiceReqGen.response.getStatus());
		
		String requestObj = JsonPath.read(getFirstPageOfMobileSearchServiceResponse, "$.data.requestObj").toString();	
		
		// invoke searchAndApplyFilters
        RequestGenerator searchAndApplyDoubleFilterReqGen = mobileSearchServiceHelper.invokeSearchAndApplyFilters(doubleFilters, requestObj);
        String searchAndApplyDoubleFiltersResponse = searchAndApplyDoubleFilterReqGen.respvalidate.returnresponseasstring();
        System.out.println("\nPrinting MobileSearchService searchAndApplyDoubleFilters API response :\n\n"+searchAndApplyDoubleFiltersResponse+"\n");
		log.info("\nPrinting MobileSearchService searchAndApplyDoubleFilters API response :\n\n"+searchAndApplyDoubleFiltersResponse+"\n");
		 
		MyntAssert.assertEquals("MobileSearchService searchAndApplyDoubleFilters API is not working",  Integer.parseInt(successRespCode), 
				searchAndApplyDoubleFilterReqGen.response.getStatus());
		
        MultivaluedMap<String, Object> searchAndApplyDoubleFiltersResponseHeaders = searchAndApplyDoubleFilterReqGen.response.getHeaders();
        System.out.println("\nMobileSearchService searchAndApplyDoubleFilters API response headers : "+searchAndApplyDoubleFiltersResponseHeaders+"\n");
        log.info("\nMobileSearchService searchAndApplyDoubleFilters API response headers : "+searchAndApplyDoubleFiltersResponseHeaders+"\n");
        
        MyntAssert.assertEquals("Invalid header information in MobileSearchService searchAndApplyDoubleFilters API response", "success", 
        		validateResponseHeaders(searchAndApplyDoubleFiltersResponseHeaders));
        
        MyntAssert.setJsonResponse(searchAndApplyDoubleFiltersResponse);
		MyntAssert.setExpectedAndActualForMyntListen(true);
		
    }
	
	/**
	 * This TestCase is used to invoke MobileSearchService getFirstPageOfSearch API and verification for success response
	 * and invoke MobileSearchService searchAndApplyDoubleFilters API and verification for meta tag nodes in the response
	 * 
	 * @param searchQuery
	 * @param rows
	 * @param returnDocs
	 * @param isFacet
	 * @param doubleFilters
	 * @param successRespCode
	 */
	@Test(groups={ "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" }, 
			dataProvider="MobileSearchServiceDP_searchAndApplyDoubleFilters_verifyMetaTagNodes")
    public void MobileSearchService_searchAndApplyDoubleFilters_verifyMetaTagNodes(String searchQuery, String rows, String returnDocs, String isFacet, 
    		String doubleFilters, String successRespCode)
    {
		// invoke getFirstPageOfSearch API
		RequestGenerator getFirstPageOfMobileSearchServiceReqGen = mobileSearchServiceHelper.invokeGetFirstPageOfSearch(searchQuery, rows, returnDocs, isFacet);
		String getFirstPageOfMobileSearchServiceResponse = getFirstPageOfMobileSearchServiceReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting MobileSearchService getFirstPageOfSearch API response :\n\n"+getFirstPageOfMobileSearchServiceResponse+"\n");
		log.info("\nPrinting MobileSearchService getFirstPageOfSearch API response :\n\n"+getFirstPageOfMobileSearchServiceResponse+"\n");
			
		MyntAssert.assertEquals("MobileSearchService getFirstPageOfSearch API is not working",  Integer.parseInt(successRespCode), 
				getFirstPageOfMobileSearchServiceReqGen.response.getStatus());
		
		String requestObj = JsonPath.read(getFirstPageOfMobileSearchServiceResponse, "$.data.requestObj").toString();	
		
		// invoke searchAndApplyFilters
        RequestGenerator searchAndApplyDoubleFilterReqGen = mobileSearchServiceHelper.invokeSearchAndApplyFilters(doubleFilters, requestObj);
        String searchAndApplyDoubleFiltersResponse = searchAndApplyDoubleFilterReqGen.respvalidate.returnresponseasstring();
        System.out.println("\nPrinting MobileSearchService searchAndApplyDoubleFilters API response :\n\n"+searchAndApplyDoubleFiltersResponse+"\n");
		log.info("\nPrinting MobileSearchService searchAndApplyDoubleFilters API response :\n\n"+searchAndApplyDoubleFiltersResponse+"\n");
		 
		MyntAssert.assertEquals("MobileSearchService searchAndApplyDoubleFilters API is not working",  Integer.parseInt(successRespCode), 
				searchAndApplyDoubleFilterReqGen.response.getStatus());
        
		AssertJUnit.assertTrue("Invalid MetaTag data nodes in MobileSearchService searchAndApplyDoubleFilters API response", 
				searchAndApplyDoubleFilterReqGen.respvalidate.DoesNodesExists(MobileSearchServiceDataNodes.getMobileSearchServiceResponseMetaTagNodes()));
		
		AssertJUnit.assertTrue("MetaTag code data value should be 200 in MobileSearchService searchAndApplyDoubleFilters API response", 
				searchAndApplyDoubleFilterReqGen.respvalidate.GetNodeValue(MobileSearchServiceDataNodes.MOBILE_SEARCH_RESP_META_CODE.getNodePath(), 
						searchAndApplyDoubleFiltersResponse).equalsIgnoreCase(successRespCode));
		
		MyntAssert.assertEquals("MetaTag errorDetail data value should be null in MobileSearchService searchAndApplyDoubleFilters API response", "null", 
				searchAndApplyDoubleFilterReqGen.respvalidate.GetNodeValue(MobileSearchServiceDataNodes.MOBILE_SEARCH_RESP_META_ERROR_DETAIL.getNodePath(),
						searchAndApplyDoubleFiltersResponse));
		
        MyntAssert.setJsonResponse(searchAndApplyDoubleFiltersResponse);
		MyntAssert.setExpectedAndActualForMyntListen(true);
		
    }
	
	/**
	 * This TestCase is used to invoke MobileSearchService getFirstPageOfSearch API and verification for success response
	 * and invoke MobileSearchService searchAndApplyDoubleFilters API and verification for notification tag nodes in the response
	 * 
	 * @param searchQuery
	 * @param rows
	 * @param returnDocs
	 * @param isFacet
	 * @param doubleFilters
	 * @param successRespCode
	 */
	@Test(groups={ "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" }, 
			dataProvider="MobileSearchServiceDP_searchAndApplyDoubleFilters_verifyNotificationTagNodes")
    public void MobileSearchService_searchAndApplyDoubleFilters_verifyNotificationTagNodes(String searchQuery, String rows, String returnDocs, String isFacet, 
    		String doubleFilters, String successRespCode)
    {
		// invoke getFirstPageOfSearch API
		RequestGenerator getFirstPageOfMobileSearchServiceReqGen = mobileSearchServiceHelper.invokeGetFirstPageOfSearch(searchQuery, rows, returnDocs, isFacet);
		String getFirstPageOfMobileSearchServiceResponse = getFirstPageOfMobileSearchServiceReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting MobileSearchService getFirstPageOfSearch API response :\n\n"+getFirstPageOfMobileSearchServiceResponse+"\n");
		log.info("\nPrinting MobileSearchService getFirstPageOfSearch API response :\n\n"+getFirstPageOfMobileSearchServiceResponse+"\n");
			
		MyntAssert.assertEquals("MobileSearchService getFirstPageOfSearch API is not working",  Integer.parseInt(successRespCode), 
				getFirstPageOfMobileSearchServiceReqGen.response.getStatus());
		
		String requestObj = JsonPath.read(getFirstPageOfMobileSearchServiceResponse, "$.data.requestObj").toString();	
		
		// invoke searchAndApplyFilters
        RequestGenerator searchAndApplyDoubleFilterReqGen = mobileSearchServiceHelper.invokeSearchAndApplyFilters(doubleFilters, requestObj);
        String searchAndApplyDoubleFiltersResponse = searchAndApplyDoubleFilterReqGen.respvalidate.returnresponseasstring();
        System.out.println("\nPrinting MobileSearchService searchAndApplyDoubleFilters API response :\n\n"+searchAndApplyDoubleFiltersResponse+"\n");
		log.info("\nPrinting MobileSearchService searchAndApplyDoubleFilters API response :\n\n"+searchAndApplyDoubleFiltersResponse+"\n");
		 
		MyntAssert.assertEquals("MobileSearchService searchAndApplyDoubleFilters API is not working",  Integer.parseInt(successRespCode), 
				searchAndApplyDoubleFilterReqGen.response.getStatus());
        
		AssertJUnit.assertTrue("Invalid notification data nodes in MobileSearchService searchAndApplyDoubleFilters API response", 
				searchAndApplyDoubleFilterReqGen.respvalidate.DoesNodesExists(MobileSearchServiceDataNodes.getMobileSearchServiceResponseNotificationTagNodes()));
		
        MyntAssert.setJsonResponse(searchAndApplyDoubleFiltersResponse);
		MyntAssert.setExpectedAndActualForMyntListen(true);
		
    }
	
	/**
	 * This TestCase is used to invoke MobileSearchService getFirstPageOfSearch API and verification for success response
	 * and invoke MobileSearchService searchAndApplyDoubleFilters API and verification for requestObject tag nodes in the response
	 * 
	 * @param searchQuery
	 * @param rows
	 * @param returnDocs
	 * @param isFacet
	 * @param doubleFilters
	 * @param successRespCode
	 */
	@Test(groups={ "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" }, 
			dataProvider="MobileSearchServiceDP_searchAndApplyDoubleFilters_verifyRequestObjectDataNodes")
    public void MobileSearchService_searchAndApplyDoubleFilters_verifyRequestObjectDataNodes(String searchQuery, String rows, String returnDocs, String isFacet, 
    		String doubleFilters, String successRespCode)
    {
		// invoke getFirstPageOfSearch API
		RequestGenerator getFirstPageOfMobileSearchServiceReqGen = mobileSearchServiceHelper.invokeGetFirstPageOfSearch(searchQuery, rows, returnDocs, isFacet);
		String getFirstPageOfMobileSearchServiceResponse = getFirstPageOfMobileSearchServiceReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting MobileSearchService getFirstPageOfSearch API response :\n\n"+getFirstPageOfMobileSearchServiceResponse+"\n");
		log.info("\nPrinting MobileSearchService getFirstPageOfSearch API response :\n\n"+getFirstPageOfMobileSearchServiceResponse+"\n");
			
		MyntAssert.assertEquals("MobileSearchService getFirstPageOfSearch API is not working",  Integer.parseInt(successRespCode), 
				getFirstPageOfMobileSearchServiceReqGen.response.getStatus());
		
		String requestObj = JsonPath.read(getFirstPageOfMobileSearchServiceResponse, "$.data.requestObj").toString();	
		
		// invoke searchAndApplyFilters
        RequestGenerator searchAndApplyDoubleFilterReqGen = mobileSearchServiceHelper.invokeSearchAndApplyFilters(doubleFilters, requestObj);
        String searchAndApplyDoubleFiltersResponse = searchAndApplyDoubleFilterReqGen.respvalidate.returnresponseasstring();
        System.out.println("\nPrinting MobileSearchService searchAndApplyDoubleFilters API response :\n\n"+searchAndApplyDoubleFiltersResponse+"\n");
		log.info("\nPrinting MobileSearchService searchAndApplyDoubleFilters API response :\n\n"+searchAndApplyDoubleFiltersResponse+"\n");
		 
		AssertJUnit.assertTrue("MetaTag code data value should be 200 in MobileSearchService searchAndApplyDoubleFilters API response", 
				searchAndApplyDoubleFilterReqGen.respvalidate.GetNodeValue(MobileSearchServiceDataNodes.MOBILE_SEARCH_RESP_META_CODE.getNodePath(), 
						searchAndApplyDoubleFiltersResponse).equalsIgnoreCase(successRespCode));
        
		if(searchAndApplyDoubleFilterReqGen.respvalidate.DoesNodeExists(MobileSearchServiceDataNodes.MOBILE_SEARCH_RESP_DATA_REQ_OBJECT.getNodePath())){
			
			AssertJUnit.assertTrue("Invalid RequestObject tag data nodes in MobileSearchService searchAndApplyDoubleFilters API response", 
					searchAndApplyDoubleFilterReqGen.respvalidate.DoesNodesExists(MobileSearchServiceDataNodes.getRequestObjectDataTagNodes()));
			
			MyntAssert.assertEquals("request and response rows tag nodes are not same", searchAndApplyDoubleFilterReqGen.respvalidate.NodeValue(
					MobileSearchServiceDataNodes.MOBILE_SEARCH_RESP_DATA_REQ_OBJECT_ROWS.getNodePath(), false), rows);
			
			MyntAssert.assertEquals("request and response returnDocs tag nodes are not same", 
					searchAndApplyDoubleFilterReqGen.respvalidate.NodeValue(
							MobileSearchServiceDataNodes.MOBILE_SEARCH_RESP_DATA_REQ_OBJECT_RETURN_DOCS.getNodePath(), false), returnDocs);
			
			MyntAssert.assertEquals("request and response isFacet tag nodes are not same", 
					searchAndApplyDoubleFilterReqGen.respvalidate.NodeValue(
							MobileSearchServiceDataNodes.MOBILE_SEARCH_RESP_DATA_REQ_OBJECT_FACET.getNodePath(), false), isFacet);
			
		} else {
			System.out.println("\nrequestObject data nodes doesnt exists in MobileSearchService searchAndApplyDoubleFilters API response\n");
			log.info("\nrequestObject data nodes doesnt exists in MobileSearchService searchAndApplyDoubleFilters API response\n");
		}
		
        MyntAssert.setJsonResponse(searchAndApplyDoubleFiltersResponse);
		MyntAssert.setExpectedAndActualForMyntListen(true);
		
    }
	
	/**
	 * This TestCase is used to invoke MobileSearchService getFirstPageOfSearch API and verification for success response
	 * and invoke MobileSearchService searchAndApplyDoubleFilters API and verification for requestObject curated_query tag nodes in the response
	 * 
	 * @param searchQuery
	 * @param rows
	 * @param returnDocs
	 * @param isFacet
	 * @param doubleFilters
	 * @param successRespCode
	 */
	@Test(groups={ "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" }, 
			dataProvider="MobileSearchServiceDP_searchAndApplyDoubleFilters_verifyRequestObjectWithCuratedQueryDataNodes")
    public void MobileSearchService_searchAndApplyDoubleFilters_verifyRequestObjectWithCuratedQueryDataNodes(String searchQuery, String rows, String returnDocs, 
    		String isFacet, String doubleFilters, String successRespCode)
    {
		// invoke getFirstPageOfSearch API
		RequestGenerator getFirstPageOfMobileSearchServiceReqGen = mobileSearchServiceHelper.invokeGetFirstPageOfSearch(searchQuery, rows, returnDocs, isFacet);
		String getFirstPageOfMobileSearchServiceResponse = getFirstPageOfMobileSearchServiceReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting MobileSearchService getFirstPageOfSearch API response :\n\n"+getFirstPageOfMobileSearchServiceResponse+"\n");
		log.info("\nPrinting MobileSearchService getFirstPageOfSearch API response :\n\n"+getFirstPageOfMobileSearchServiceResponse+"\n");
			
		MyntAssert.assertEquals("MobileSearchService getFirstPageOfSearch API is not working",  Integer.parseInt(successRespCode), 
				getFirstPageOfMobileSearchServiceReqGen.response.getStatus());
		
		String requestObj = JsonPath.read(getFirstPageOfMobileSearchServiceResponse, "$.data.requestObj").toString();	
		
		// invoke searchAndApplyFilters
        RequestGenerator searchAndApplyDoubleFilterReqGen = mobileSearchServiceHelper.invokeSearchAndApplyFilters(doubleFilters, requestObj);
        String searchAndApplyDoubleFiltersResponse = searchAndApplyDoubleFilterReqGen.respvalidate.returnresponseasstring();
        System.out.println("\nPrinting MobileSearchService searchAndApplyDoubleFilters API response :\n\n"+searchAndApplyDoubleFiltersResponse+"\n");
		log.info("\nPrinting MobileSearchService searchAndApplyDoubleFilters API response :\n\n"+searchAndApplyDoubleFiltersResponse+"\n");
		 
		AssertJUnit.assertTrue("MetaTag code data value should be 200 in MobileSearchService searchAndApplyDoubleFilters API response", 
				searchAndApplyDoubleFilterReqGen.respvalidate.GetNodeValue(MobileSearchServiceDataNodes.MOBILE_SEARCH_RESP_META_CODE.getNodePath(), 
						searchAndApplyDoubleFiltersResponse).equalsIgnoreCase(successRespCode));
		
		if(searchAndApplyDoubleFilterReqGen.respvalidate.DoesNodeExists(MobileSearchServiceDataNodes.MOBILE_SEARCH_RESP_DATA_REQ_OBJECT.getNodePath())
				&& searchAndApplyDoubleFilterReqGen.respvalidate.DoesNodeExists(
						MobileSearchServiceDataNodes.MOBILE_SEARCH_RESP_DATA_REQ_OBJECT_CURATED_QUERY.getNodePath())){
			
			AssertJUnit.assertTrue("Invalid requestObject curated_query data nodes in MobileSearchService searchAndApplyDoubleFilters API response", 
					searchAndApplyDoubleFilterReqGen.respvalidate.DoesNodesExists(MobileSearchServiceDataNodes.getRequestObjectWithCuratedQueryDataTagNodes()));
			
			MyntAssert.assertEquals("request and response rows tag nodes are not same", searchAndApplyDoubleFilterReqGen.respvalidate.NodeValue(
					MobileSearchServiceDataNodes.MOBILE_SEARCH_RESP_DATA_REQ_OBJECT_CURATED_QUERY_ROWS.getNodePath(), false), rows);
			
		} else {
			System.out.println("\nrequestObject curated_query data nodes doesnt exists in MobileSearchService searchAndApplyDoubleFilters API response\n");
			log.info("\nrequestObject curated_query data nodes doesnt exists in MobileSearchService searchAndApplyDoubleFilters API response\n");
		}
			
        MyntAssert.setJsonResponse(searchAndApplyDoubleFiltersResponse);
		MyntAssert.setExpectedAndActualForMyntListen(true);
		
    }
	
	/**
	 * This TestCase is used to invoke MobileSearchService getFirstPageOfSearch API and verification for success response
	 * and invoke MobileSearchService searchAndApplyDoubleFilters API and verification for filters data tag nodes in the response 
	 * 
	 * @param searchQuery
	 * @param rows
	 * @param returnDocs
	 * @param isFacet
	 * @param doubleFilters
	 * @param successRespCode
	 */
	@Test(groups={ "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" }, 
			dataProvider="MobileSearchServiceDP_searchAndApplyDoubleFilters_verifyBodyWithFiltersDataNodes")
    public void MobileSearchService_searchAndApplyDoubleFilters_verifyBodyWithFiltersDataNodes(String searchQuery, String rows, String returnDocs, String isFacet, 
    		String doubleFilters, String successRespCode)
    {
		// invoke getFirstPageOfSearch API
		RequestGenerator getFirstPageOfMobileSearchServiceReqGen = mobileSearchServiceHelper.invokeGetFirstPageOfSearch(searchQuery, rows, returnDocs, isFacet);
		String getFirstPageOfMobileSearchServiceResponse = getFirstPageOfMobileSearchServiceReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting MobileSearchService getFirstPageOfSearch API response :\n\n"+getFirstPageOfMobileSearchServiceResponse+"\n");
		log.info("\nPrinting MobileSearchService getFirstPageOfSearch API response :\n\n"+getFirstPageOfMobileSearchServiceResponse+"\n");
			
		MyntAssert.assertEquals("MobileSearchService getFirstPageOfSearch API is not working",  Integer.parseInt(successRespCode), 
				getFirstPageOfMobileSearchServiceReqGen.response.getStatus());
		
		String requestObj = JsonPath.read(getFirstPageOfMobileSearchServiceResponse, "$.data.requestObj").toString();	
		
		// invoke searchAndApplyFilters
        RequestGenerator searchAndApplyDoubleFilterReqGen = mobileSearchServiceHelper.invokeSearchAndApplyFilters(doubleFilters, requestObj);
        String searchAndApplyDoubleFiltersResponse = searchAndApplyDoubleFilterReqGen.respvalidate.returnresponseasstring();
        System.out.println("\nPrinting MobileSearchService searchAndApplyDoubleFilters API response :\n\n"+searchAndApplyDoubleFiltersResponse+"\n");
		log.info("\nPrinting MobileSearchService searchAndApplyDoubleFilters API response :\n\n"+searchAndApplyDoubleFiltersResponse+"\n");
		 
		AssertJUnit.assertTrue("MetaTag code data value should be 200 in MobileSearchService searchAndApplyDoubleFilters API response", 
				searchAndApplyDoubleFilterReqGen.respvalidate.GetNodeValue(MobileSearchServiceDataNodes.MOBILE_SEARCH_RESP_META_CODE.getNodePath(), 
						searchAndApplyDoubleFiltersResponse).equalsIgnoreCase(successRespCode));
        
		if(searchAndApplyDoubleFilterReqGen.respvalidate.DoesNodeExists(MobileSearchServiceDataNodes.MOBIEL_SEARCH_RESP_DATA_BODY_FILTERS.getNodePath())){
			
			String responseFilterNodes = searchAndApplyDoubleFilterReqGen.respvalidate.NodeValue(
					MobileSearchServiceDataNodes.MOBIEL_SEARCH_RESP_DATA_BODY_FILTERS.getNodePath(), true);
			
			if(!responseFilterNodes.equals(mobileSearchServiceList.toString())){
				
				AssertJUnit.assertTrue("Invalid filters data tag nodes in MobileSearchService searchAndApplyDoubleFilters API response", 
						searchAndApplyDoubleFilterReqGen.respvalidate.DoesNodesExists(MobileSearchServiceDataNodes.getBodyWithFiltersDataTagNodes()));
			
			} else {
				System.out.println("\nfilters data tag nodes are empty in MobileSearchService searchAndApplyDoubleFilters API response\n");
				log.info("\nfilters data tag nodes are empty in MobileSearchService searchAndApplyDoubleFilters API response\n");
			}
			
		} else {
			System.out.println("\nfilters data tag nodes doesnt exists in MobileSearchService searchAndApplyDoubleFilters API response\n");
			log.info("\nfilters data tag nodes doesnt exists in MobileSearchService searchAndApplyDoubleFilters API response\n");
		}
		
        MyntAssert.setJsonResponse(searchAndApplyDoubleFiltersResponse);
		MyntAssert.setExpectedAndActualForMyntListen(true);
		
    }
	
	/**
	 * This TestCase is used to invoke MobileSearchService getFirstPageOfSearch API and verification for success response
	 * and invoke MobileSearchService searchAndApplyDoubleFilters API and verification for products data tag nodes in the response 
	 * 
	 * @param searchQuery
	 * @param rows
	 * @param returnDocs
	 * @param isFacet
	 * @param doubleFilters
	 * @param successRespCode
	 */
	@Test(groups={ "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" }, 
			dataProvider="MobileSearchServiceDP_searchAndApplyDoubleFilters_verifyBodyWithProductsDataNodes")
    public void MobileSearchService_searchAndApplyDoubleFilters_verifyBodyWithProductsDataNodes(String searchQuery, String rows, String returnDocs, String isFacet, 
    		String doubleFilters, String successRespCode)
    {
		// invoke getFirstPageOfSearch API
		RequestGenerator getFirstPageOfMobileSearchServiceReqGen = mobileSearchServiceHelper.invokeGetFirstPageOfSearch(searchQuery, rows, returnDocs, isFacet);
		String getFirstPageOfMobileSearchServiceResponse = getFirstPageOfMobileSearchServiceReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting MobileSearchService getFirstPageOfSearch API response :\n\n"+getFirstPageOfMobileSearchServiceResponse+"\n");
		log.info("\nPrinting MobileSearchService getFirstPageOfSearch API response :\n\n"+getFirstPageOfMobileSearchServiceResponse+"\n");
			
		MyntAssert.assertEquals("MobileSearchService getFirstPageOfSearch API is not working",  Integer.parseInt(successRespCode), 
				getFirstPageOfMobileSearchServiceReqGen.response.getStatus());
		
		String requestObj = JsonPath.read(getFirstPageOfMobileSearchServiceResponse, "$.data.requestObj").toString();	
		
		// invoke searchAndApplyFilters
        RequestGenerator searchAndApplyDoubleFilterReqGen = mobileSearchServiceHelper.invokeSearchAndApplyFilters(doubleFilters, requestObj);
        String searchAndApplyDoubleFiltersResponse = searchAndApplyDoubleFilterReqGen.respvalidate.returnresponseasstring();
        System.out.println("\nPrinting MobileSearchService searchAndApplyDoubleFilters API response :\n\n"+searchAndApplyDoubleFiltersResponse+"\n");
		log.info("\nPrinting MobileSearchService searchAndApplyDoubleFilters API response :\n\n"+searchAndApplyDoubleFiltersResponse+"\n");
		 
		AssertJUnit.assertTrue("MetaTag code data value should be 200 in MobileSearchService searchAndApplyDoubleFilters API response", 
				searchAndApplyDoubleFilterReqGen.respvalidate.GetNodeValue(MobileSearchServiceDataNodes.MOBILE_SEARCH_RESP_META_CODE.getNodePath(), 
						searchAndApplyDoubleFiltersResponse).equalsIgnoreCase(successRespCode));
        
		if(searchAndApplyDoubleFilterReqGen.respvalidate.DoesNodeExists(MobileSearchServiceDataNodes.MOBILE_SEARCH_RESP_DATA_BODY_PRODUCTS.getNodePath())){
			
			String responseProductNodes = searchAndApplyDoubleFilterReqGen.respvalidate.NodeValue(
					MobileSearchServiceDataNodes.MOBILE_SEARCH_RESP_DATA_BODY_PRODUCTS.getNodePath(), true);
			
			if(!responseProductNodes.equals(mobileSearchServiceList.toString())){
				
				AssertJUnit.assertTrue("Invalid products data tag nodes in MobileSearchService searchAndApplyDoubleFilters API response", 
						searchAndApplyDoubleFilterReqGen.respvalidate.DoesNodesExists(MobileSearchServiceDataNodes.getBodyWithProductsDataTagNodes()));
			
			} else {
				System.out.println("\nproducts data tag nodes are empty in MobileSearchService searchAndApplyDoubleFilters API response\n");
				log.info("\nproducts data tag nodes are empty in MobileSearchService searchAndApplyDoubleFilters API response\n");
			}
		} else {
			System.out.println("\nproducts data tag nodes doesnt exists in MobileSearchService searchAndApplyDoubleFilters API response\n");
			log.info("\nproducts data tag nodes doesnt exists in MobileSearchService searchAndApplyDoubleFilters API response\n");
		}
		
        MyntAssert.setJsonResponse(searchAndApplyDoubleFiltersResponse);
		MyntAssert.setExpectedAndActualForMyntListen(true);
		
    }
	
	/**
	 *  This TestCase is used to invoke MobileSearchService getFirstPageOfSearch API and verification for success response
	 * and invoke MobileSearchService searchAndApplyAllFilters API and verification for success response
	 * 
	 * @param searchQuery
	 * @param rows
	 * @param returnDocs
	 * @param isFacet
	 * @param allFilters
	 * @param successRespCode
	 */
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "MiniRegression", "Regression", "ExhaustiveRegression" },
			dataProvider = "MobileSearchServiceDP_searchAndApplyAllFilters_verifySuccessResponse" )
    public void MobileSearchService_searchAndApplyAllFilters_verifySuccessResponse(String searchQuery, String rows, String returnDocs, String isFacet, 
    		String allFilters, String successRespCode)
    {
		// invoke getFirstPageOfSearch API
		RequestGenerator getFirstPageOfMobileSearchServiceReqGen = mobileSearchServiceHelper.invokeGetFirstPageOfSearch(searchQuery, rows, 
				returnDocs, isFacet);
		String getFirstPageOfMobileSearchServiceResponse = getFirstPageOfMobileSearchServiceReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting MobileSearchService getFirstPageOfSearch API response :\n\n"+getFirstPageOfMobileSearchServiceResponse+"\n");
		log.info("\nPrinting MobileSearchService getFirstPageOfSearch API response :\n\n"+getFirstPageOfMobileSearchServiceResponse+"\n");
			
		MyntAssert.assertEquals("MobileSearchService getFirstPageOfSearch API is not working",  Integer.parseInt(successRespCode), 
				getFirstPageOfMobileSearchServiceReqGen.response.getStatus());
		
		String requestObj = JsonPath.read(getFirstPageOfMobileSearchServiceResponse, "$.data.requestObj").toString();
		
		// invoke searchAndApplyFilters
        RequestGenerator searchAndApplyAllFiltersReqGen = mobileSearchServiceHelper.invokeSearchAndApplyFilters(allFilters, requestObj);
        String searchAndApplyAllFiltersResponse = searchAndApplyAllFiltersReqGen.respvalidate.returnresponseasstring();
        System.out.println("\nPrinting MobileSearchService searchAndApplyAllFilters API response :\n\n"+searchAndApplyAllFiltersResponse+"\n");
		log.info("\nPrinting MobileSearchService searchAndApplyAllFilters API response :\n\n"+searchAndApplyAllFiltersResponse+"\n");
		 
		MyntAssert.assertEquals("MobileSearchService searchAndApplyAllFilters API is not working",  Integer.parseInt(successRespCode), 
				searchAndApplyAllFiltersReqGen.response.getStatus());
        
        MyntAssert.setJsonResponse(searchAndApplyAllFiltersResponse);
		MyntAssert.setExpectedAndActualForMyntListen(true);
    }
	
	/**
	 * This TestCase is used to invoke MobileSearchService getFirstPageOfSearch API and verification for success response
	 * and invoke MobileSearchService searchAndApplyAllFilters API and verification for headers information in the response
	 * 
	 * @param searchQuery
	 * @param rows
	 * @param returnDocs
	 * @param isFacet
	 * @param allFilters
	 * @param successRespCode
	 */
	@Test(groups={ "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" }, 
			dataProvider="MobileSearchServiceDP_searchAndApplyAllFilters_verifyResponseHeaders")
    public void MobileSearchService_searchAndApplyAllFilters_verifyResponseHeaders(String searchQuery, String rows, String returnDocs, String isFacet, 
    		String allFilters, String successRespCode)
    {
		// invoke getFirstPageOfSearch API
		RequestGenerator getFirstPageOfMobileSearchServiceReqGen = mobileSearchServiceHelper.invokeGetFirstPageOfSearch(searchQuery, rows, returnDocs, isFacet);
		String getFirstPageOfMobileSearchServiceResponse = getFirstPageOfMobileSearchServiceReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting MobileSearchService getFirstPageOfSearch API response :\n\n"+getFirstPageOfMobileSearchServiceResponse+"\n");
		log.info("\nPrinting MobileSearchService getFirstPageOfSearch API response :\n\n"+getFirstPageOfMobileSearchServiceResponse+"\n");
			
		MyntAssert.assertEquals("MobileSearchService getFirstPageOfSearch API is not working",  Integer.parseInt(successRespCode), 
				getFirstPageOfMobileSearchServiceReqGen.response.getStatus());
		
		String requestObj = JsonPath.read(getFirstPageOfMobileSearchServiceResponse, "$.data.requestObj").toString();	
		
		// invoke searchAndApplyFilters
        RequestGenerator searchAndApplyAllFiltersReqGen = mobileSearchServiceHelper.invokeSearchAndApplyFilters(allFilters, requestObj);
        String searchAndApplyAllFiltersResponse = searchAndApplyAllFiltersReqGen.respvalidate.returnresponseasstring();
        System.out.println("\nPrinting MobileSearchService searchAndApplyAllFilters API response :\n\n"+searchAndApplyAllFiltersResponse+"\n");
		log.info("\nPrinting MobileSearchService searchAndApplyAllFilters API response :\n\n"+searchAndApplyAllFiltersResponse+"\n");
		 
		MyntAssert.assertEquals("MobileSearchService searchAndApplyAllFilters API is not working",  Integer.parseInt(successRespCode), 
				searchAndApplyAllFiltersReqGen.response.getStatus());
		
        MultivaluedMap<String, Object> searchAndApplyAllFiltersResponseHeaders = searchAndApplyAllFiltersReqGen.response.getHeaders();
        System.out.println("\nMobileSearchService searchAndApplyAllFilters API response headers : "+searchAndApplyAllFiltersResponseHeaders+"\n");
        log.info("\nMobileSearchService searchAndApplyAllFilters API response headers : "+searchAndApplyAllFiltersResponseHeaders+"\n");
        
        MyntAssert.assertEquals("Invalid header information in MobileSearchService searchAndApplyAllFilters API response", "success", 
        		validateResponseHeaders(searchAndApplyAllFiltersResponseHeaders));
        
        MyntAssert.setJsonResponse(searchAndApplyAllFiltersResponse);
		MyntAssert.setExpectedAndActualForMyntListen(true);
		
    }
	
	/**
	 * This TestCase is used to invoke MobileSearchService getFirstPageOfSearch API and verification for success response
	 * and invoke MobileSearchService searchAndApplyAllFilters API and verification for meta tag nodes in the response
	 * 
	 * @param searchQuery
	 * @param rows
	 * @param returnDocs
	 * @param isFacet
	 * @param allFilters
	 * @param successRespCode
	 */
	@Test(groups={ "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" }, 
			dataProvider="MobileSearchServiceDP_searchAndApplyAllFilters_verifyMetaTagNodes")
    public void MobileSearchService_searchAndApplyAllFilters_verifyMetaTagNodes(String searchQuery, String rows, String returnDocs, String isFacet, 
    		String allFilters, String successRespCode)
    {
		// invoke getFirstPageOfSearch API
		RequestGenerator getFirstPageOfMobileSearchServiceReqGen = mobileSearchServiceHelper.invokeGetFirstPageOfSearch(searchQuery, rows, returnDocs, isFacet);
		String getFirstPageOfMobileSearchServiceResponse = getFirstPageOfMobileSearchServiceReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting MobileSearchService getFirstPageOfSearch API response :\n\n"+getFirstPageOfMobileSearchServiceResponse+"\n");
		log.info("\nPrinting MobileSearchService getFirstPageOfSearch API response :\n\n"+getFirstPageOfMobileSearchServiceResponse+"\n");
			
		MyntAssert.assertEquals("MobileSearchService getFirstPageOfSearch API is not working",  Integer.parseInt(successRespCode), 
				getFirstPageOfMobileSearchServiceReqGen.response.getStatus());
		
		String requestObj = JsonPath.read(getFirstPageOfMobileSearchServiceResponse, "$.data.requestObj").toString();	
		
		// invoke searchAndApplyFilters
        RequestGenerator searchAndApplyAllFiltersReqGen = mobileSearchServiceHelper.invokeSearchAndApplyFilters(allFilters, requestObj);
        String searchAndApplyAllFiltersResponse = searchAndApplyAllFiltersReqGen.respvalidate.returnresponseasstring();
        System.out.println("\nPrinting MobileSearchService searchAndApplyAllFilters API response :\n\n"+searchAndApplyAllFiltersResponse+"\n");
		log.info("\nPrinting MobileSearchService searchAndApplyAllFilters API response :\n\n"+searchAndApplyAllFiltersResponse+"\n");
		 
		MyntAssert.assertEquals("MobileSearchService searchAndApplyAllFilters API is not working",  Integer.parseInt(successRespCode), 
				searchAndApplyAllFiltersReqGen.response.getStatus());
        
		AssertJUnit.assertTrue("Invalid MetaTag data nodes in MobileSearchService searchAndApplyAllFilters API response", 
				searchAndApplyAllFiltersReqGen.respvalidate.DoesNodesExists(MobileSearchServiceDataNodes.getMobileSearchServiceResponseMetaTagNodes()));
		
		AssertJUnit.assertTrue("MetaTag code data value should be 200 in MobileSearchService searchAndApplyAllFilters API response", 
				searchAndApplyAllFiltersReqGen.respvalidate.GetNodeValue(MobileSearchServiceDataNodes.MOBILE_SEARCH_RESP_META_CODE.getNodePath(), 
						searchAndApplyAllFiltersResponse).equalsIgnoreCase(successRespCode));
		
		MyntAssert.assertEquals("MetaTag errorDetail data value should be null in MobileSearchService searchAndApplyAllFilters API response", "null", 
				searchAndApplyAllFiltersReqGen.respvalidate.GetNodeValue(MobileSearchServiceDataNodes.MOBILE_SEARCH_RESP_META_ERROR_DETAIL.getNodePath(),
						searchAndApplyAllFiltersResponse));
		
        MyntAssert.setJsonResponse(searchAndApplyAllFiltersResponse);
		MyntAssert.setExpectedAndActualForMyntListen(true);
		
    }
	
	/**
	 * This TestCase is used to invoke MobileSearchService getFirstPageOfSearch API and verification for success response
	 * and invoke MobileSearchService searchAndApplyAllFilters API and verification for notification tag nodes in the response
	 * 
	 * @param searchQuery
	 * @param rows
	 * @param returnDocs
	 * @param isFacet
	 * @param allFilters
	 * @param successRespCode
	 */
	@Test(groups={ "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" }, 
			dataProvider="MobileSearchServiceDP_searchAndApplyAllFilters_verifyNotificationTagNodes")
    public void MobileSearchService_searchAndApplyAllFilters_verifyNotificationTagNodes(String searchQuery, String rows, String returnDocs, String isFacet, 
    		String allFilters, String successRespCode)
    {
		// invoke getFirstPageOfSearch API
		RequestGenerator getFirstPageOfMobileSearchServiceReqGen = mobileSearchServiceHelper.invokeGetFirstPageOfSearch(searchQuery, rows, returnDocs, isFacet);
		String getFirstPageOfMobileSearchServiceResponse = getFirstPageOfMobileSearchServiceReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting MobileSearchService getFirstPageOfSearch API response :\n\n"+getFirstPageOfMobileSearchServiceResponse+"\n");
		log.info("\nPrinting MobileSearchService getFirstPageOfSearch API response :\n\n"+getFirstPageOfMobileSearchServiceResponse+"\n");
			
		MyntAssert.assertEquals("MobileSearchService getFirstPageOfSearch API is not working",  Integer.parseInt(successRespCode), 
				getFirstPageOfMobileSearchServiceReqGen.response.getStatus());
		
		String requestObj = JsonPath.read(getFirstPageOfMobileSearchServiceResponse, "$.data.requestObj").toString();	
		
		// invoke searchAndApplyFilters
        RequestGenerator searchAndApplyAllFiltersReqGen = mobileSearchServiceHelper.invokeSearchAndApplyFilters(allFilters, requestObj);
        String searchAndApplyAllFiltersResponse = searchAndApplyAllFiltersReqGen.respvalidate.returnresponseasstring();
        System.out.println("\nPrinting MobileSearchService searchAndApplyAllFilters API response :\n\n"+searchAndApplyAllFiltersResponse+"\n");
		log.info("\nPrinting MobileSearchService searchAndApplyAllFilters API response :\n\n"+searchAndApplyAllFiltersResponse+"\n");
		 
		MyntAssert.assertEquals("MobileSearchService searchAndApplyAllFilters API is not working",  Integer.parseInt(successRespCode), 
				searchAndApplyAllFiltersReqGen.response.getStatus());
        
		AssertJUnit.assertTrue("Invalid notification tag data nodes in MobileSearchService searchAndApplyAllFilters API response", 
				searchAndApplyAllFiltersReqGen.respvalidate.DoesNodesExists(MobileSearchServiceDataNodes.getMobileSearchServiceResponseNotificationTagNodes()));
		
        MyntAssert.setJsonResponse(searchAndApplyAllFiltersResponse);
		MyntAssert.setExpectedAndActualForMyntListen(true);
		
    }
	
	/**
	 * This TestCase is used to invoke MobileSearchService getFirstPageOfSearch API and verification for success response
	 * and invoke MobileSearchService searchAndApplyAllFilters API and verification for requestObject tag data nodes in the response
	 * 
	 * @param searchQuery
	 * @param rows
	 * @param returnDocs
	 * @param isFacet
	 * @param allFilters
	 * @param successRespCode
	 */
	@Test(groups={ "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" }, 
			dataProvider="MobileSearchServiceDP_searchAndApplyAllFilters_verifyRequestObjectDataNodes")
    public void MobileSearchService_searchAndApplyAllFilters_verifyRequestObjectDataNodes(String searchQuery, String rows, String returnDocs, String isFacet, 
    		String allFilters, String successRespCode)
    {
		// invoke getFirstPageOfSearch API
		RequestGenerator getFirstPageOfMobileSearchServiceReqGen = mobileSearchServiceHelper.invokeGetFirstPageOfSearch(searchQuery, rows, returnDocs, isFacet);
		String getFirstPageOfMobileSearchServiceResponse = getFirstPageOfMobileSearchServiceReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting MobileSearchService getFirstPageOfSearch API response :\n\n"+getFirstPageOfMobileSearchServiceResponse+"\n");
		log.info("\nPrinting MobileSearchService getFirstPageOfSearch API response :\n\n"+getFirstPageOfMobileSearchServiceResponse+"\n");
			
		MyntAssert.assertEquals("MobileSearchService getFirstPageOfSearch API is not working",  Integer.parseInt(successRespCode), 
				getFirstPageOfMobileSearchServiceReqGen.response.getStatus());
		
		String requestObj = JsonPath.read(getFirstPageOfMobileSearchServiceResponse, "$.data.requestObj").toString();	
		
		// invoke searchAndApplyFilters
        RequestGenerator searchAndApplyAllFiltersReqGen = mobileSearchServiceHelper.invokeSearchAndApplyFilters(allFilters, requestObj);
        String searchAndApplyAllFiltersResponse = searchAndApplyAllFiltersReqGen.respvalidate.returnresponseasstring();
        System.out.println("\nPrinting MobileSearchService searchAndApplyAllFilters API response :\n\n"+searchAndApplyAllFiltersResponse+"\n");
		log.info("\nPrinting MobileSearchService searchAndApplyAllFilters API response :\n\n"+searchAndApplyAllFiltersResponse+"\n");
		 
		AssertJUnit.assertTrue("MetaTag code data value should be 200 in MobileSearchService searchAndApplyAllFilters API response", 
				searchAndApplyAllFiltersReqGen.respvalidate.GetNodeValue(MobileSearchServiceDataNodes.MOBILE_SEARCH_RESP_META_CODE.getNodePath(), 
						searchAndApplyAllFiltersResponse).equalsIgnoreCase(successRespCode));
        
		if(searchAndApplyAllFiltersReqGen.respvalidate.DoesNodeExists(MobileSearchServiceDataNodes.MOBILE_SEARCH_RESP_DATA_REQ_OBJECT.getNodePath())){
			
			AssertJUnit.assertTrue("Invalid requestObject tag data nodes in MobileSearchService searchAndApplyAllFilters API response", 
					searchAndApplyAllFiltersReqGen.respvalidate.DoesNodesExists(MobileSearchServiceDataNodes.getRequestObjectDataTagNodes()));
			
			MyntAssert.assertEquals("request and response rows tag nodes are not same", searchAndApplyAllFiltersReqGen.respvalidate.NodeValue(
					MobileSearchServiceDataNodes.MOBILE_SEARCH_RESP_DATA_REQ_OBJECT_ROWS.getNodePath(), false), rows);
			
			MyntAssert.assertEquals("request and response returnDocs tag nodes are not same", 
					searchAndApplyAllFiltersReqGen.respvalidate.NodeValue(
							MobileSearchServiceDataNodes.MOBILE_SEARCH_RESP_DATA_REQ_OBJECT_RETURN_DOCS.getNodePath(), false), returnDocs);
			
			MyntAssert.assertEquals("request and response isFacet tag nodes are not same", 
					searchAndApplyAllFiltersReqGen.respvalidate.NodeValue(
							MobileSearchServiceDataNodes.MOBILE_SEARCH_RESP_DATA_REQ_OBJECT_FACET.getNodePath(), false), isFacet);
			
		} else {
			System.out.println("\nrequestObject data tag nodes doesnt exists in MobileSearchService searchAndApplyDoubleFilters API response\n");
			log.info("\nrequestObject data tag nodes doesnt exists in MobileSearchService searchAndApplyDoubleFilters API response\n");
		}
		
        MyntAssert.setJsonResponse(searchAndApplyAllFiltersResponse);
		MyntAssert.setExpectedAndActualForMyntListen(true);
		
    }
	
	/**
	 * This TestCase is used to invoke MobileSearchService getFirstPageOfSearch API and verification for success response
	 * and invoke MobileSearchService searchAndApplyAllFilters API and verification for requestObject curated_query tag nodes in the response
	 * 
	 * @param searchQuery
	 * @param rows
	 * @param returnDocs
	 * @param isFacet
	 * @param allFilters
	 * @param successRespCode
	 */
	@Test(groups={ "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" }, 
			dataProvider="MobileSearchServiceDP_searchAndApplyAllFilters_verifyRequestObjectWithCuratedQueryDataNodes")
    public void MobileSearchService_searchAndApplyAllFilters_verifyRequestObjectWithCuratedQueryDataNodes(String searchQuery, String rows, String returnDocs, String isFacet, 
    		String allFilters, String successRespCode)
    {
		// invoke getFirstPageOfSearch API
		RequestGenerator getFirstPageOfMobileSearchServiceReqGen = mobileSearchServiceHelper.invokeGetFirstPageOfSearch(searchQuery, rows, returnDocs, isFacet);
		String getFirstPageOfMobileSearchServiceResponse = getFirstPageOfMobileSearchServiceReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting MobileSearchService getFirstPageOfSearch API response :\n\n"+getFirstPageOfMobileSearchServiceResponse+"\n");
		log.info("\nPrinting MobileSearchService getFirstPageOfSearch API response :\n\n"+getFirstPageOfMobileSearchServiceResponse+"\n");
			
		MyntAssert.assertEquals("MobileSearchService getFirstPageOfSearch API is not working",  Integer.parseInt(successRespCode), 
				getFirstPageOfMobileSearchServiceReqGen.response.getStatus());
		
		String requestObj = JsonPath.read(getFirstPageOfMobileSearchServiceResponse, "$.data.requestObj").toString();	
		
		// invoke searchAndApplyFilters
        RequestGenerator searchAndApplyAllFiltersReqGen = mobileSearchServiceHelper.invokeSearchAndApplyFilters(allFilters, requestObj);
        String searchAndApplyAllFiltersResponse = searchAndApplyAllFiltersReqGen.respvalidate.returnresponseasstring();
        System.out.println("\nPrinting MobileSearchService searchAndApplyAllFilters API response :\n\n"+searchAndApplyAllFiltersResponse+"\n");
		log.info("\nPrinting MobileSearchService searchAndApplyAllFilters API response :\n\n"+searchAndApplyAllFiltersResponse+"\n");
		 
		AssertJUnit.assertTrue("MetaTag code data value should be 200 in MobileSearchService searchAndApplyAllFilters API response", 
				searchAndApplyAllFiltersReqGen.respvalidate.GetNodeValue(MobileSearchServiceDataNodes.MOBILE_SEARCH_RESP_META_CODE.getNodePath(), 
						searchAndApplyAllFiltersResponse).equalsIgnoreCase(successRespCode));
		
		if(searchAndApplyAllFiltersReqGen.respvalidate.DoesNodeExists(MobileSearchServiceDataNodes.MOBILE_SEARCH_RESP_DATA_REQ_OBJECT.getNodePath())
				&& searchAndApplyAllFiltersReqGen.respvalidate.DoesNodeExists(
						MobileSearchServiceDataNodes.MOBILE_SEARCH_RESP_DATA_REQ_OBJECT_CURATED_QUERY.getNodePath())){
			
			AssertJUnit.assertTrue("Invalid requestObject curated_query data nodes in MobileSearchService searchAndApplyAllFilters API response", 
					searchAndApplyAllFiltersReqGen.respvalidate.DoesNodesExists(MobileSearchServiceDataNodes.getRequestObjectWithCuratedQueryDataTagNodes()));
			
			MyntAssert.assertEquals("request and response rows tag nodes are not same", searchAndApplyAllFiltersReqGen.respvalidate.NodeValue(
					MobileSearchServiceDataNodes.MOBILE_SEARCH_RESP_DATA_REQ_OBJECT_CURATED_QUERY_ROWS.getNodePath(), false), rows);
			
		} else {
			System.out.println("\nrequestObject curated_query data tag nodes doesnt exists in MobileSearchService searchAndApplyDoubleFilters API response\n");
			log.info("\nrequestObject curated_query data tag nodes doesnt exists in MobileSearchService searchAndApplyDoubleFilters API response\n");
		}
		
        MyntAssert.setJsonResponse(searchAndApplyAllFiltersResponse);
		MyntAssert.setExpectedAndActualForMyntListen(true);
		
    }
	
	/**
	 * This TestCase is used to invoke MobileSearchService getFirstPageOfSearch API and verification for success response
	 * and invoke MobileSearchService searchAndApplyAllFilters API and verification for filters tag data nodes in the response 
	 * 
	 * @param searchQuery
	 * @param rows
	 * @param returnDocs
	 * @param isFacet
	 * @param allFilters
	 * @param successRespCode
	 */
	@Test(groups={ "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" }, 
			dataProvider="MobileSearchServiceDP_searchAndApplyAllFilters_verifyBodyWithFiltersDataNodes")
    public void MobileSearchService_searchAndApplyAllFilters_verifyBodyWithFiltersDataNodes(String searchQuery, String rows, String returnDocs, String isFacet, 
    		String allFilters, String successRespCode)
    {
		// invoke getFirstPageOfSearch API
		RequestGenerator getFirstPageOfMobileSearchServiceReqGen = mobileSearchServiceHelper.invokeGetFirstPageOfSearch(searchQuery, rows, returnDocs, isFacet);
		String getFirstPageOfMobileSearchServiceResponse = getFirstPageOfMobileSearchServiceReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting MobileSearchService getFirstPageOfSearch API response :\n\n"+getFirstPageOfMobileSearchServiceResponse+"\n");
		log.info("\nPrinting MobileSearchService getFirstPageOfSearch API response :\n\n"+getFirstPageOfMobileSearchServiceResponse+"\n");
			
		MyntAssert.assertEquals("MobileSearchService getFirstPageOfSearch API is not working",  Integer.parseInt(successRespCode), 
				getFirstPageOfMobileSearchServiceReqGen.response.getStatus());
		
		String requestObj = JsonPath.read(getFirstPageOfMobileSearchServiceResponse, "$.data.requestObj").toString();	
		
		// invoke searchAndApplyFilters
        RequestGenerator searchAndApplyAllFiltersReqGen = mobileSearchServiceHelper.invokeSearchAndApplyFilters(allFilters, requestObj);
        String searchAndApplyAllFiltersResponse = searchAndApplyAllFiltersReqGen.respvalidate.returnresponseasstring();
        System.out.println("\nPrinting MobileSearchService searchAndApplyAllFilters API response :\n\n"+searchAndApplyAllFiltersResponse+"\n");
		log.info("\nPrinting MobileSearchService searchAndApplyAllFilters API response :\n\n"+searchAndApplyAllFiltersResponse+"\n");
		 
		AssertJUnit.assertTrue("MetaTag code data value should be 200 in MobileSearchService searchAndApplyAllFilters API response", 
				searchAndApplyAllFiltersReqGen.respvalidate.GetNodeValue(MobileSearchServiceDataNodes.MOBILE_SEARCH_RESP_META_CODE.getNodePath(), 
						searchAndApplyAllFiltersResponse).equalsIgnoreCase(successRespCode));
        
		if(searchAndApplyAllFiltersReqGen.respvalidate.DoesNodeExists(MobileSearchServiceDataNodes.MOBIEL_SEARCH_RESP_DATA_BODY_FILTERS.getNodePath())){
			
			String responseFilterNodes = searchAndApplyAllFiltersReqGen.respvalidate.NodeValue(
					MobileSearchServiceDataNodes.MOBIEL_SEARCH_RESP_DATA_BODY_FILTERS.getNodePath(), false);
			
			if(!responseFilterNodes.equals(mobileSearchServiceList.toString())){
				
				AssertJUnit.assertTrue("Invalid filters data tag nodes in MobileSearchService searchAndApplyAllFilters API response", 
						searchAndApplyAllFiltersReqGen.respvalidate.DoesNodesExists(MobileSearchServiceDataNodes.getBodyWithFiltersDataTagNodes()));
			
			} else {
				System.out.println("\nfilters data tag nodes are empty in MobileSearchService searchAndApplyAllFilters API response\n");
				log.info("\nfilters data tag nodes are empty in MobileSearchService searchAndApplyAllFilters API response\n");
			}
			
		} else {
			System.out.println("\nfilters data tag nodes doesnt exists in MobileSearchService searchAndApplyAllFilters API response\n");
			log.info("\nfilters data tag nodes doesnt exists in MobileSearchService searchAndApplyAllFilters API response\n");
		}
		
        MyntAssert.setJsonResponse(searchAndApplyAllFiltersResponse);
		MyntAssert.setExpectedAndActualForMyntListen(true);
		
    }
	
	/**
	 * This TestCase is used to invoke MobileSearchService getFirstPageOfSearch API and verification for success response
	 * and invoke MobileSearchService searchAndApplyAllFilters API and verification for products tag data nodes in the response
	 * 
	 * @param searchQuery
	 * @param rows
	 * @param returnDocs
	 * @param isFacet
	 * @param allFilters
	 * @param successRespCode
	 */
	@Test(groups={ "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" }, 
			dataProvider="MobileSearchServiceDP_searchAndApplyAllFilters_verifyBodyWithProductsDataNodes")
    public void MobileSearchService_searchAndApplyAllFilters_verifyBodyWithProductsDataNodes(String searchQuery, String rows, String returnDocs, String isFacet, 
    		String allFilters, String successRespCode)
    {
		// invoke getFirstPageOfSearch API
		RequestGenerator getFirstPageOfMobileSearchServiceReqGen = mobileSearchServiceHelper.invokeGetFirstPageOfSearch(searchQuery, rows, returnDocs, isFacet);
		String getFirstPageOfMobileSearchServiceResponse = getFirstPageOfMobileSearchServiceReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting MobileSearchService getFirstPageOfSearch API response :\n\n"+getFirstPageOfMobileSearchServiceResponse+"\n");
		log.info("\nPrinting MobileSearchService getFirstPageOfSearch API response :\n\n"+getFirstPageOfMobileSearchServiceResponse+"\n");
			
		MyntAssert.assertEquals("MobileSearchService getFirstPageOfSearch API is not working",  Integer.parseInt(successRespCode), 
				getFirstPageOfMobileSearchServiceReqGen.response.getStatus());
		
		String requestObj = JsonPath.read(getFirstPageOfMobileSearchServiceResponse, "$.data.requestObj").toString();	
		
		// invoke searchAndApplyFilters
        RequestGenerator searchAndApplyAllFiltersReqGen = mobileSearchServiceHelper.invokeSearchAndApplyFilters(allFilters, requestObj);
        String searchAndApplyAllFiltersResponse = searchAndApplyAllFiltersReqGen.respvalidate.returnresponseasstring();
        System.out.println("\nPrinting MobileSearchService searchAndApplyAllFilters API response :\n\n"+searchAndApplyAllFiltersResponse+"\n");
		log.info("\nPrinting MobileSearchService searchAndApplyAllFilters API response :\n\n"+searchAndApplyAllFiltersResponse+"\n");
		 
		AssertJUnit.assertTrue("MetaTag code data value should be 200 in MobileSearchService searchAndApplyAllFilters API response", 
				searchAndApplyAllFiltersReqGen.respvalidate.GetNodeValue(MobileSearchServiceDataNodes.MOBILE_SEARCH_RESP_META_CODE.getNodePath(), 
						searchAndApplyAllFiltersResponse).equalsIgnoreCase(successRespCode));
        
		if(searchAndApplyAllFiltersReqGen.respvalidate.DoesNodeExists(MobileSearchServiceDataNodes.MOBILE_SEARCH_RESP_DATA_BODY_PRODUCTS.getNodePath())){
			
			String responseProductNodes = searchAndApplyAllFiltersReqGen.respvalidate.NodeValue(
					MobileSearchServiceDataNodes.MOBILE_SEARCH_RESP_DATA_BODY_PRODUCTS.getNodePath(), true);
			
			if(!responseProductNodes.equals(mobileSearchServiceList.toString())){
				
				AssertJUnit.assertTrue("Invalid products tag data nodes in MobileSearchService searchAndApplyAllFilters API response", 
						searchAndApplyAllFiltersReqGen.respvalidate.DoesNodesExists(MobileSearchServiceDataNodes.getBodyWithProductsDataTagNodes()));
			
			} else {
				System.out.println("\nproducts tag data nodes are empty in MobileSearchService searchAndApplyAllFilters API response\n");
				log.info("\nproducts tag data nodes are empty in MobileSearchService searchAndApplyAllFilters API response\n");
			}
			
		} else {
			System.out.println("\nproducts tag data nodes doesnt exists in MobileSearchService searchAndApplyAllFilters API response\n");
			log.info("\nproducts tag data nodes doesnt exists in MobileSearchService searchAndApplyAllFilters API response\n");
		}
		
        MyntAssert.setJsonResponse(searchAndApplyAllFiltersResponse);
		MyntAssert.setExpectedAndActualForMyntListen(true);
		
    }
	
	/**
	 * This TestCase is used to invoke MobileSearchService parameterizedMobileSearchDataQuery API and verification for success response
	 * 
	 * @param paramMobileSearchDataQuery
	 * @param successRespCode
	 */
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "MiniRegression", "Regression", "ExhaustiveRegression" }, 
			dataProvider = "MobileSearchServiceDP_parameterizedMobileSearchDataQuery_verifySuccessResponse")
	public void MobileSearchService_parameterizedMobileSearchDataQuery_verifySuccessResponse(String paramMobileSearchDataQuery, String successRespCode) 
	{
		RequestGenerator parameterizedMobileSearchDataQueryReqGen = mobileSearchServiceHelper.invokeParameterizedMobileSearchDataQuery(paramMobileSearchDataQuery);
	    String parameterizedMobileSearchDataQueryResponse = parameterizedMobileSearchDataQueryReqGen.respvalidate.returnresponseasstring();
	    System.out.println("\nPrinting MobileSearchService parameterizedMobileSearchDataQuery API response :\n\n"+parameterizedMobileSearchDataQueryResponse+"\n");
		log.info("\nPrinting MobileSearchService parameterizedMobileSearchDataQuery API response :\n\n"+parameterizedMobileSearchDataQueryResponse+"\n");
			
		MyntAssert.assertEquals("MobileSearchService parameterizedMobileSearchDataQuery API is not working",  Integer.parseInt(successRespCode), 
				parameterizedMobileSearchDataQueryReqGen.response.getStatus());
		
		MyntAssert.setJsonResponse(parameterizedMobileSearchDataQueryResponse);
		MyntAssert.setExpectedAndActualForMyntListen(true);
	}
	
	/**
	 * This TestCase is used to invoke MobileSearchService parameterizedMobileSearchDataQuery API and verification for data tag nodes in the response
	 * 
	 * @param paramMobileSearchDataQuery
	 * @param successRespCode
	 */
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" }, 
			dataProvider = "MobileSearchServiceDP_parameterizedMobileSearchDataQuery_verifyDataTagNodes")
	public void MobileSearchService_parameterizedMobileSearchDataQuery_verifyDataTagNodes(String paramMobileSearchDataQuery, String successRespCode) 
	{
		RequestGenerator parameterizedMobileSearchDataQueryReqGen = mobileSearchServiceHelper.invokeParameterizedMobileSearchDataQuery(paramMobileSearchDataQuery);
	    String parameterizedMobileSearchDataQueryResponse = parameterizedMobileSearchDataQueryReqGen.respvalidate.returnresponseasstring();
	    System.out.println("\nPrinting MobileSearchService parameterizedMobileSearchDataQuery API response :\n\n"+parameterizedMobileSearchDataQueryResponse+"\n");
		log.info("\nPrinting MobileSearchService parameterizedMobileSearchDataQuery API response :\n\n"+parameterizedMobileSearchDataQueryResponse+"\n");
			
		MyntAssert.assertEquals("MobileSearchService parameterizedMobileSearchDataQuery API is not working",  Integer.parseInt(successRespCode), 
				parameterizedMobileSearchDataQueryReqGen.response.getStatus());
		
		if(parameterizedMobileSearchDataQueryReqGen.respvalidate.DoesNodeExists(MobileSearchServiceDataNodes.MOBILE_PARAMETERIZED_SEARCH_RESP_DATA.getNodePath())){
			
			AssertJUnit.assertTrue("Invalid data tag nodes in MobileSearchService parameterizedMobileSearchDataQuery API response", 
					parameterizedMobileSearchDataQueryReqGen.respvalidate.DoesNodesExists(MobileSearchServiceDataNodes.getParameterizedSearchDataNodes()));
			
		} else{
			System.out.println("\n data nodes doesnt exists in MobileSearchService parameterizedMobileSearchDataQuery API response\n");
			log.info("\n data nodes doesnt exists in MobileSearchService parameterizedMobileSearchDataQuery API response\n");
		}
		
		if(parameterizedMobileSearchDataQueryReqGen.respvalidate.DoesNodeExists(MobileSearchServiceDataNodes.MOBILE_PARAMETERIZED_SEARCH_RESP_DATA.getNodePath())  
				&& parameterizedMobileSearchDataQueryReqGen.respvalidate.DoesNodeExists(
				MobileSearchServiceDataNodes.MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_SEO.getNodePath())){
			
			AssertJUnit.assertTrue("Invalid seo data tag nodes in MobileSearchService parameterizedMobileSearchDataQuery API response", 
					parameterizedMobileSearchDataQueryReqGen.respvalidate.DoesNodesExists(MobileSearchServiceDataNodes.getParameterizedSearchSeoDataNodes()));
			
			AssertJUnit.assertTrue("Invalid page_title node value in MobileSearchService parameterizedMobileSearchDataQuery API response", 
					parameterizedMobileSearchDataQueryReqGen.respvalidate.NodeValue(
							MobileSearchServiceDataNodes.MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_SEO_META_DATA_PAGE_TITLE.getNodePath(), false).contains(
									paramMobileSearchDataQuery));
			
		} else {
			System.out.println("\nseo data nodes doesnt exists in MobileSearchService parameterizedMobileSearchDataQuery API response\n");
			log.info("\nseo data nodes doesnt exists in MobileSearchService parameterizedMobileSearchDataQuery API response\n");
		}
		
		if(parameterizedMobileSearchDataQueryReqGen.respvalidate.DoesNodeExists(MobileSearchServiceDataNodes.MOBILE_PARAMETERIZED_SEARCH_RESP_DATA.getNodePath())  
				&& parameterizedMobileSearchDataQueryReqGen.respvalidate.DoesNodeExists(
				MobileSearchServiceDataNodes.MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_SEARCH.getNodePath())){
			
			AssertJUnit.assertTrue("Invalid search data tag nodes in MobileSearchService parameterizedMobileSearchDataQuery API response", 
					parameterizedMobileSearchDataQueryReqGen.respvalidate.DoesNodesExists(MobileSearchServiceDataNodes.getParameterizedSearchDataSearchNodes()));
			
		} else {
			System.out.println("\nsearch data nodes doesnt exists in MobileSearchService parameterizedMobileSearchDataQuery API response\n");
			log.info("\nsearch data nodes doesnt exists in MobileSearchService parameterizedMobileSearchDataQuery API response\n");
		}
		
		MyntAssert.setJsonResponse(parameterizedMobileSearchDataQueryResponse);
		MyntAssert.setExpectedAndActualForMyntListen(true);
	}
	
	/**
	 * This TestCase is used to invoke MobileSearchService parameterizedMobileSearchDataQuery API and verification for filters data tag nodes in the response 
	 * 
	 * @param paramMobileSearchDataQuery
	 * @param successRespCode
	 */
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" }, 
			dataProvider = "MobileSearchServiceDP_parameterizedMobileSearchDataQuery_verifyFiltersDataTagNodes")
	public void MobileSearchService_parameterizedMobileSearchDataQuery_verifyFiltersDataTagNodes(String paramMobileSearchDataQuery, String successRespCode) 
	{
		RequestGenerator parameterizedMobileSearchDataQueryReqGen = mobileSearchServiceHelper.invokeParameterizedMobileSearchDataQuery(paramMobileSearchDataQuery);
	    String parameterizedMobileSearchDataQueryResponse = parameterizedMobileSearchDataQueryReqGen.respvalidate.returnresponseasstring();
	    System.out.println("\nPrinting MobileSearchService parameterizedMobileSearchDataQuery API response :\n\n"+parameterizedMobileSearchDataQueryResponse+"\n");
		log.info("\nPrinting MobileSearchService parameterizedMobileSearchDataQuery API response :\n\n"+parameterizedMobileSearchDataQueryResponse+"\n");
			
		MyntAssert.assertEquals("MobileSearchService parameterizedMobileSearchDataQuery API is not working",  Integer.parseInt(successRespCode), 
				parameterizedMobileSearchDataQueryReqGen.response.getStatus());
		
		if(parameterizedMobileSearchDataQueryReqGen.respvalidate.DoesNodeExists(
				MobileSearchServiceDataNodes.MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_RESULTS_FILTERS.getNodePath())){
			
			String responseFilterNodes = parameterizedMobileSearchDataQueryReqGen.respvalidate.NodeValue(
					MobileSearchServiceDataNodes.MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_RESULTS_FILTERS.getNodePath(), false);
			
			if(!responseFilterNodes.equals(mobileSearchServiceList.toString())){
				
				AssertJUnit.assertTrue("Invalid filters data tag nodes in MobileSearchService parameterizedMobileSearchDataQuery API response", 
						parameterizedMobileSearchDataQueryReqGen.respvalidate.DoesNodesExists(MobileSearchServiceDataNodes.getParameterizedSearchResultsFiltersDataNodes()));
				
			} else {
				System.out.println("\nfilters data nodes are empty in MobileSearchService parameterizedMobileSearchDataQuery API response\n");
				log.info("\nfilters data nodes are empty in MobileSearchService parameterizedMobileSearchDataQuery API response\n");
			}
			
		} else {
			System.out.println("\nfilters data nodes doesnt exists in MobileSearchService parameterizedMobileSearchDataQuery API response\n");
			log.info("\nfilters data nodes doesnt exists in MobileSearchService parameterizedMobileSearchDataQuery API response\n");
		}
		
		MyntAssert.setJsonResponse(parameterizedMobileSearchDataQueryResponse);
		MyntAssert.setExpectedAndActualForMyntListen(true);
	}
	
	/**
	 * This TestCase is used to invoke MobileSearchService parameterizedMobileSearchDataQuery API and verification for products data tag nodes in the response 
	 * 
	 * @param paramMobileSearchDataQuery
	 * @param successRespCode
	 */
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" }, 
			dataProvider = "MobileSearchServiceDP_parameterizedMobileSearchDataQuery_verifyProductsDataTagNodes")
	public void MobileSearchService_parameterizedMobileSearchDataQuery_verifyProductsDataTagNodes(String paramMobileSearchDataQuery, String successRespCode) 
	{
		RequestGenerator parameterizedMobileSearchDataQueryReqGen = mobileSearchServiceHelper.invokeParameterizedMobileSearchDataQuery(paramMobileSearchDataQuery);
	    String parameterizedMobileSearchDataQueryResponse = parameterizedMobileSearchDataQueryReqGen.respvalidate.returnresponseasstring();
	    System.out.println("\nPrinting MobileSearchService parameterizedMobileSearchDataQuery API response :\n\n"+parameterizedMobileSearchDataQueryResponse+"\n");
		log.info("\nPrinting MobileSearchService parameterizedMobileSearchDataQuery API response :\n\n"+parameterizedMobileSearchDataQueryResponse+"\n");
			
		MyntAssert.assertEquals("MobileSearchService parameterizedMobileSearchDataQuery API is not working",  Integer.parseInt(successRespCode), 
				parameterizedMobileSearchDataQueryReqGen.response.getStatus());
		
		if(parameterizedMobileSearchDataQueryReqGen.respvalidate.DoesNodeExists(
				MobileSearchServiceDataNodes.MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_RESULTS_PRODUCTS.getNodePath())){
			
			String responseProductNodes = parameterizedMobileSearchDataQueryReqGen.respvalidate.NodeValue(
					MobileSearchServiceDataNodes.MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_RESULTS_PRODUCTS.getNodePath(), false);
			
			if(!responseProductNodes.equals(mobileSearchServiceList.toString())){
				
				AssertJUnit.assertTrue("Invalid products data tag nodes in MobileSearchService parameterizedMobileSearchDataQuery API response", 
						parameterizedMobileSearchDataQueryReqGen.respvalidate.DoesNodesExists(
								MobileSearchServiceDataNodes.getParameterizedSearchResultsProductsDataNodes()));
				
			} else {
				System.out.println("\nproducts data nodes are empty in MobileSearchService parameterizedMobileSearchDataQuery API response\n");
				log.info("\nproducts data nodes are empty in MobileSearchService parameterizedMobileSearchDataQuery API response\n");
			}
			
		} else {
			System.out.println("\nproducts data nodes doesnt exists in MobileSearchService parameterizedMobileSearchDataQuery API response\n");
			log.info("\nproducts data nodes doesnt exists in MobileSearchService parameterizedMobileSearchDataQuery API response\n");
		}
		
		MyntAssert.setJsonResponse(parameterizedMobileSearchDataQueryResponse);
		MyntAssert.setExpectedAndActualForMyntListen(true);
	}
	
	/**
	 * This TestCase is used to invoke MobileSearchService parameterizedMobileSearchDataQueryWithOneFilter API and verification for success response
	 * 
	 * @param paramMobileSearchDataQuery
	 * @param paramArtifact
	 * @param paramValue
	 * @param successRespCode
	 */
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "MiniRegression", "Regression", "ExhaustiveRegression" }, 
			dataProvider = "MobileSearchServiceDP_parameterizedMobileSearchDataQueryWithOneFilter_verifySuccessResponse")
	public void MobileSearchService_parameterizedMobileSearchDataQueryWithOneFilter_verifySuccessResponse(String paramMobileSearchDataQuery, String paramArtifact, 
			String paramValue, String successRespCode)
	{
		RequestGenerator parameterizedMobileSearchDataQueryWithOneFilterReqGen = mobileSearchServiceHelper.invokeParameterizedMobileSearchDataQueryWithOneFilter(
				paramMobileSearchDataQuery, paramArtifact, paramValue);
	    String parameterizedMobileSearchDataQueryWithOneFilterResponse = parameterizedMobileSearchDataQueryWithOneFilterReqGen.respvalidate.returnresponseasstring();
	    System.out.println("\nPrinting MobileSearchService parameterizedMobileSearchDataQueryWithOneFilter API response :\n\n"+
	    		parameterizedMobileSearchDataQueryWithOneFilterResponse+"\n");
		log.info("\nPrinting MobileSearchService parameterizedMobileSearchDataQueryWithOneFilter API response :\n\n"+
	    		parameterizedMobileSearchDataQueryWithOneFilterResponse+"\n");
			
		MyntAssert.assertEquals("MobileSearchService parameterizedMobileSearchDataQueryWithOneFilter API is not working",  Integer.parseInt(successRespCode), 
				parameterizedMobileSearchDataQueryWithOneFilterReqGen.response.getStatus());
		
		MyntAssert.setJsonResponse(parameterizedMobileSearchDataQueryWithOneFilterResponse);
		MyntAssert.setExpectedAndActualForMyntListen(true);
	}
	
	/**
	 *  This TestCase is used to invoke MobileSearchService parameterizedMobileSearchDataQueryWithOneFilter API and verification for data tag nodes in the response
	 * 
	 * @param paramMobileSearchDataQuery
	 * @param paramArtifact
	 * @param paramValue
	 * @param successRespCode
	 */
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" }, 
			dataProvider = "MobileSearchServiceDP_parameterizedMobileSearchDataQueryWithOneFilter_verifyDataTagNodes")
	public void MobileSearchService_parameterizedMobileSearchDataQueryWithOneFilter_verifyDataTagNodes(String paramMobileSearchDataQuery, String paramArtifact, 
			String paramValue, String successRespCode)
	{
		RequestGenerator parameterizedMobileSearchDataQueryWithOneFilterReqGen = mobileSearchServiceHelper.invokeParameterizedMobileSearchDataQueryWithOneFilter(
				paramMobileSearchDataQuery, paramArtifact, paramValue);
	    String parameterizedMobileSearchDataQueryWithOneFilterResponse = parameterizedMobileSearchDataQueryWithOneFilterReqGen.respvalidate.returnresponseasstring();
	    System.out.println("\nPrinting MobileSearchService parameterizedMobileSearchDataQueryWithOneFilter API response :\n\n"+
	    		parameterizedMobileSearchDataQueryWithOneFilterResponse+"\n");
		log.info("\nPrinting MobileSearchService parameterizedMobileSearchDataQueryWithOneFilter API response :\n\n"+
	    		parameterizedMobileSearchDataQueryWithOneFilterResponse+"\n");
		
		MyntAssert.assertEquals("MobileSearchService parameterizedMobileSearchDataQueryWithOneFilter API is not working",  Integer.parseInt(successRespCode), 
				parameterizedMobileSearchDataQueryWithOneFilterReqGen.response.getStatus());
		
		if(parameterizedMobileSearchDataQueryWithOneFilterReqGen.respvalidate.DoesNodeExists(
				MobileSearchServiceDataNodes.MOBILE_PARAMETERIZED_SEARCH_RESP_DATA.getNodePath())){
			
			AssertJUnit.assertTrue("Invalid data tag nodes in MobileSearchService parameterizedMobileSearchDataQueryWithOneFilter API response", 
					parameterizedMobileSearchDataQueryWithOneFilterReqGen.respvalidate.DoesNodesExists(MobileSearchServiceDataNodes.getParameterizedSearchDataNodes()));
			
		} else{
			System.out.println("\n data nodes doesnt exists in MobileSearchService parameterizedMobileSearchDataQueryWithOneFilter API response\n");
			log.info("\n data nodes doesnt exists in MobileSearchService parameterizedMobileSearchDataQueryWithOneFilter API response\n");
		}
		
		if(parameterizedMobileSearchDataQueryWithOneFilterReqGen.respvalidate.DoesNodeExists(
				MobileSearchServiceDataNodes.MOBILE_PARAMETERIZED_SEARCH_RESP_DATA.getNodePath())  && 
				parameterizedMobileSearchDataQueryWithOneFilterReqGen.respvalidate.DoesNodeExists(
				MobileSearchServiceDataNodes.MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_SEO.getNodePath())){
			
			AssertJUnit.assertTrue("Invalid seo data tag nodes in MobileSearchService parameterizedMobileSearchDataQueryWithOneFilter API response", 
					parameterizedMobileSearchDataQueryWithOneFilterReqGen.respvalidate.DoesNodesExists(
							MobileSearchServiceDataNodes.getParameterizedSearchSeoDataNodes()));
			
			AssertJUnit.assertTrue("Invalid page_title node value in MobileSearchService parameterizedMobileSearchDataQueryWithOneFilter API response", 
					parameterizedMobileSearchDataQueryWithOneFilterReqGen.respvalidate.NodeValue(
							MobileSearchServiceDataNodes.MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_SEO_META_DATA_PAGE_TITLE.getNodePath(), false).contains(
									paramMobileSearchDataQuery));
			
		} else {
			System.out.println("\nseo data nodes doesnt exists in MobileSearchService parameterizedMobileSearchDataQueryWithOneFilter API response\n");
			log.info("\nseo data nodes doesnt exists in MobileSearchService parameterizedMobileSearchDataQueryWithOneFilter API response\n");
		}
		
		if(parameterizedMobileSearchDataQueryWithOneFilterReqGen.respvalidate.DoesNodeExists(
				MobileSearchServiceDataNodes.MOBILE_PARAMETERIZED_SEARCH_RESP_DATA.getNodePath())  
				&& parameterizedMobileSearchDataQueryWithOneFilterReqGen.respvalidate.DoesNodeExists(
				MobileSearchServiceDataNodes.MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_SEARCH.getNodePath())){
			
			AssertJUnit.assertTrue("Invalid search data tag nodes in MobileSearchService parameterizedMobileSearchDataQueryWithOneFilter API response", 
					parameterizedMobileSearchDataQueryWithOneFilterReqGen.respvalidate.DoesNodesExists(
							MobileSearchServiceDataNodes.getParameterizedSearchDataSearchNodes()));
			
			AssertJUnit.assertNotNull("filterQueryParam should not be null in MobileSearchService parameterizedMobileSearchDataQueryWithOneFilter API response", 
					parameterizedMobileSearchDataQueryWithOneFilterReqGen.respvalidate.NodeValue(
							MobileSearchServiceDataNodes.MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_RESULTS_FILTER_QUERY_PARAM.getNodePath(), false));
			
		} else {
			System.out.println("\nsearch data nodes doesnt exists in MobileSearchService parameterizedMobileSearchDataQueryWithOneFilter API response\n");
			log.info("\nsearch data nodes doesnt exists in MobileSearchService parameterizedMobileSearchDataQueryWithOneFilter API response\n");
		}
		
		MyntAssert.setJsonResponse(parameterizedMobileSearchDataQueryWithOneFilterResponse);
		MyntAssert.setExpectedAndActualForMyntListen(true);
		
	}
	
	/**
	 * This TestCase is used to invoke MobileSearchService parameterizedMobileSearchDataQueryWithOneFilter API and verification for filters data tag nodes in the response 
	 * 
	 * @param paramMobileSearchDataQuery
	 * @param paramArtifact
	 * @param paramValue
	 * @param successRespCode
	 */
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" }, 
			dataProvider = "MobileSearchServiceDP_parameterizedMobileSearchDataQueryWithOneFilter_verifyFiltersDataTagNodes")
	public void MobileSearchService_parameterizedMobileSearchDataQueryWithOneFilter_verifyFiltersDataTagNodes(String paramMobileSearchDataQuery, String paramArtifact, 
			String paramValue, String successRespCode)
	{
		RequestGenerator parameterizedMobileSearchDataQueryWithOneFilterReqGen = mobileSearchServiceHelper.invokeParameterizedMobileSearchDataQueryWithOneFilter(
				paramMobileSearchDataQuery, paramArtifact, paramValue);
	    String parameterizedMobileSearchDataQueryWithOneFilterResponse = parameterizedMobileSearchDataQueryWithOneFilterReqGen.respvalidate.returnresponseasstring();
	    System.out.println("\nPrinting MobileSearchService parameterizedMobileSearchDataQueryWithOneFilter API response :\n\n"+
	    		parameterizedMobileSearchDataQueryWithOneFilterResponse);
		log.info("\nPrinting MobileSearchService parameterizedMobileSearchDataQueryWithOneFilter API response :\n\n"+
	    		parameterizedMobileSearchDataQueryWithOneFilterResponse);
		
		MyntAssert.assertEquals("MobileSearchService parameterizedMobileSearchDataQueryWithOneFilter API is not working",  Integer.parseInt(successRespCode), 
				parameterizedMobileSearchDataQueryWithOneFilterReqGen.response.getStatus());
		
		if(parameterizedMobileSearchDataQueryWithOneFilterReqGen.respvalidate.DoesNodeExists(
				MobileSearchServiceDataNodes.MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_RESULTS_FILTERS.getNodePath())){
			
			String responseFilterNodes = parameterizedMobileSearchDataQueryWithOneFilterReqGen.respvalidate.NodeValue(
					MobileSearchServiceDataNodes.MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_RESULTS_FILTERS.getNodePath(), false);
			
			if(!responseFilterNodes.equals(mobileSearchServiceList.toString())){
				
				AssertJUnit.assertTrue("Invalid filters data tag nodes in MobileSearchService parameterizedMobileSearchDataQueryWithOneFilter API response", 
						parameterizedMobileSearchDataQueryWithOneFilterReqGen.respvalidate.DoesNodesExists(
								MobileSearchServiceDataNodes.getParameterizedSearchResultsFiltersDataNodes()));
				
			} else {
				System.out.println("\nfilters data nodes are empty in MobileSearchService parameterizedMobileSearchDataQueryWithOneFilter API response\n");
				log.info("\nfilters data nodes are empty in MobileSearchService parameterizedMobileSearchDataQueryWithOneFilter API response\n");
			}
			
		} else {
			System.out.println("\nfilters data nodes doesnt exists in MobileSearchService parameterizedMobileSearchDataQueryWithOneFilter API response\n");
			log.info("\nfilters data nodes doesnt exists in MobileSearchService parameterizedMobileSearchDataQueryWithOneFilter API response\n");
		}
		
		MyntAssert.setJsonResponse(parameterizedMobileSearchDataQueryWithOneFilterResponse);
		MyntAssert.setExpectedAndActualForMyntListen(true);
	}
	
	/**
	 * This TestCase is used to invoke MobileSearchService parameterizedMobileSearchDataQueryWithOneFilter API and verification for products data tag nodes 
	 * in the response 
	 * 
	 * @param paramMobileSearchDataQuery
	 * @param paramArtifact
	 * @param paramValue
	 * @param successRespCode
	 */
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" }, 
			dataProvider = "MobileSearchServiceDP_parameterizedMobileSearchDataQueryWithOneFilter_verifyProductsDataTagNodes")
	public void MobileSearchService_parameterizedMobileSearchDataQueryWithOneFilter_verifyProductsDataTagNodes(String paramMobileSearchDataQuery, String paramArtifact, 
			String paramValue, String successRespCode) 
	{
		RequestGenerator parameterizedMobileSearchDataQueryWithOneFilterReqGen = mobileSearchServiceHelper.invokeParameterizedMobileSearchDataQueryWithOneFilter(
				paramMobileSearchDataQuery, paramArtifact, paramValue);
	    String parameterizedMobileSearchDataQueryWithOneFilterResponse = parameterizedMobileSearchDataQueryWithOneFilterReqGen.respvalidate.returnresponseasstring();
	    System.out.println("\nPrinting MobileSearchService parameterizedMobileSearchDataQueryWithOneFilter API response :\n\n"+
	    		parameterizedMobileSearchDataQueryWithOneFilterResponse+"\n");
		log.info("\nPrinting MobileSearchService parameterizedMobileSearchDataQueryWithOneFilter API response :\n\n"+
	    		parameterizedMobileSearchDataQueryWithOneFilterResponse+"\n");
		
		MyntAssert.assertEquals("MobileSearchService parameterizedMobileSearchDataQueryWithOneFilter API is not working",  Integer.parseInt(successRespCode), 
				parameterizedMobileSearchDataQueryWithOneFilterReqGen.response.getStatus());
		
		if(parameterizedMobileSearchDataQueryWithOneFilterReqGen.respvalidate.DoesNodeExists(
				MobileSearchServiceDataNodes.MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_RESULTS_PRODUCTS.getNodePath())){
			
			String responseProductNodes = parameterizedMobileSearchDataQueryWithOneFilterReqGen.respvalidate.NodeValue(
					MobileSearchServiceDataNodes.MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_RESULTS_PRODUCTS.getNodePath(), false);
			
			if(!responseProductNodes.equals(mobileSearchServiceList.toString())) {
				
				AssertJUnit.assertTrue("Invalid products data tag nodes in MobileSearchService parameterizedMobileSearchDataQueryWithOneFilter API response", 
						parameterizedMobileSearchDataQueryWithOneFilterReqGen.respvalidate.DoesNodesExists(
								MobileSearchServiceDataNodes.getParameterizedSearchResultsProductsDataNodes()));
				
			} else if(parameterizedMobileSearchDataQueryWithOneFilterReqGen.respvalidate.DoesNodeExists(
					MobileSearchServiceDataNodes.MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_RESULTS_CURATED_PRODUCTS.getNodePath())){
				
					String curatedProducts = parameterizedMobileSearchDataQueryWithOneFilterReqGen.respvalidate.NodeValue(
							MobileSearchServiceDataNodes.MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_RESULTS_CURATED_PRODUCTS.getNodePath(), false);
					
					if(!curatedProducts.equals(mobileSearchServiceList.toString())){
	
						AssertJUnit.assertTrue("Invalid curated products data tag nodes in MobileSearchService parameterizedMobileSearchDataQueryWithOneFilter API response", 
								parameterizedMobileSearchDataQueryWithOneFilterReqGen.respvalidate.DoesNodesExists(
										MobileSearchServiceDataNodes.getParameterizedSearchResultsCuratedProductsDataNodes()));
						
					} else {
						System.out.println("\ncurated products data nodes are empty in MobileSearchService parameterizedMobileSearchDataQueryWithOneFilter API response\n");
						log.info("\ncurated products data nodes are empty in MobileSearchService parameterizedMobileSearchDataQueryWithOneFilter API response\n");
					}
				
			} else {
				System.out.println("\ncurated products data nodes doesnt exists in MobileSearchService parameterizedMobileSearchDataQueryWithOneFilter API response\n");
				log.info("\ncurated products data nodes doesnt exists in MobileSearchService parameterizedMobileSearchDataQueryWithOneFilter API response\n");
			}
		
		} else {
			System.out.println("\nproducts data nodes are empty in MobileSearchService parameterizedMobileSearchDataQueryWithOneFilter API response\n");
			log.info("\nproducts data nodes are empty in MobileSearchService parameterizedMobileSearchDataQueryWithOneFilter API response\n");
		}
		
		MyntAssert.setJsonResponse(parameterizedMobileSearchDataQueryWithOneFilterResponse);
		MyntAssert.setExpectedAndActualForMyntListen(true);
	}
	
	/**
	 * This TestCase is used to invoke MobileSearchService parameterizedMobileSearchDataQueryWithTwoFilters API and verification for success response
	 * 
	 * @param paramMobileSearchDataQuery
	 * @param paramArtifact1
	 * @param paramValue1
	 * @param paramArtifact2
	 * @param paramValue2
	 * @param successRespCode
	 */
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "MiniRegression", "Regression", "ExhaustiveRegression" }, 
			dataProvider = "MobileSearchServiceDP_parameterizedMobileSearchDataQueryWithTwoFilters_verifySuccessResponse")
	public void MobileSearchService_parameterizedMobileSearchDataQueryWithTwoFilters_verifySuccessResponse(String paramMobileSearchDataQuery, String paramArtifact1,
			String paramValue1, String paramArtifact2, String paramValue2, String successRespCode) 
	{
		RequestGenerator parameterizedMobileSearchDataQueryWithTwoFiltersReqGen = mobileSearchServiceHelper.invokeParameterizedMobileSearchDataQueryWithTwoFilters(
				paramMobileSearchDataQuery, paramArtifact1, paramValue1, paramArtifact2, paramValue2);
		
	    String parameterizedMobileSearchDataQueryWithTwoFiltersResponse = parameterizedMobileSearchDataQueryWithTwoFiltersReqGen.respvalidate.returnresponseasstring();
	    System.out.println("\nPrinting MobileSearchService parameterizedMobileSearchDataQueryWithTwoFilters API response :\n\n"+
	    		parameterizedMobileSearchDataQueryWithTwoFiltersResponse+"\n");
		log.info("\nPrinting MobileSearchService parameterizedMobileSearchDataQueryWithTwoFilters API response :\n\n"+
	    		parameterizedMobileSearchDataQueryWithTwoFiltersResponse+"\n");
			
		MyntAssert.assertEquals("MobileSearchService parameterizedMobileSearchDataQueryWithTwoFilters API is not working",  Integer.parseInt(successRespCode), 
				parameterizedMobileSearchDataQueryWithTwoFiltersReqGen.response.getStatus());
		
		MyntAssert.setJsonResponse(parameterizedMobileSearchDataQueryWithTwoFiltersResponse);
		MyntAssert.setExpectedAndActualForMyntListen(true);
	}
	
	/**
	 *  This TestCase is used to invoke MobileSearchService parameterizedMobileSearchDataQueryWithTwoFilters API and verification for data tag nodes in the response
	 * 
	 * @param paramMobileSearchDataQuery
	 * @param paramArtifact1
	 * @param paramValue1
	 * @param paramArtifact2
	 * @param paramValue2
	 * @param successRespCode
	 */
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" }, 
			dataProvider = "MobileSearchServiceDP_parameterizedMobileSearchDataQueryWithTwoFilters_verifyDataTagNodes")
	public void MobileSearchService_parameterizedMobileSearchDataQueryWithTwoFilters_verifyDataTagNodes(String paramMobileSearchDataQuery, String paramArtifact1,
			String paramValue1, String paramArtifact2, String paramValue2, String successRespCode)
	{
		RequestGenerator parameterizedMobileSearchDataQueryWithTwoFiltersReqGen = mobileSearchServiceHelper.invokeParameterizedMobileSearchDataQueryWithTwoFilters(
				paramMobileSearchDataQuery, paramArtifact1, paramValue1, paramArtifact2, paramValue2);
	    String parameterizedMobileSearchDataQueryWithTwoFiltersResponse = parameterizedMobileSearchDataQueryWithTwoFiltersReqGen.respvalidate.returnresponseasstring();
	    System.out.println("\nPrinting MobileSearchService parameterizedMobileSearchDataQueryWithTwoFilters API response :\n\n"+
	    		parameterizedMobileSearchDataQueryWithTwoFiltersResponse+"\n");
		log.info("\nPrinting MobileSearchService parameterizedMobileSearchDataQueryWithTwoFilters API response :\n\n"+
	    		parameterizedMobileSearchDataQueryWithTwoFiltersResponse+"\n");
			
		MyntAssert.assertEquals("MobileSearchService parameterizedMobileSearchDataQueryWithTwoFilters API is not working",  Integer.parseInt(successRespCode), 
				parameterizedMobileSearchDataQueryWithTwoFiltersReqGen.response.getStatus());
		
		if(parameterizedMobileSearchDataQueryWithTwoFiltersReqGen.respvalidate.DoesNodeExists(
				MobileSearchServiceDataNodes.MOBILE_PARAMETERIZED_SEARCH_RESP_DATA.getNodePath())){
			
			AssertJUnit.assertTrue("Invalid data tag nodes in MobileSearchService parameterizedMobileSearchDataQueryWithTwoFilters API response", 
					parameterizedMobileSearchDataQueryWithTwoFiltersReqGen.respvalidate.DoesNodesExists(MobileSearchServiceDataNodes.getParameterizedSearchDataNodes()));
			
		} else{
			System.out.println("\n data nodes doesnt exists in MobileSearchService parameterizedMobileSearchDataQueryWithTwoFilters API response\n");
			log.info("\n data nodes doesnt exists in MobileSearchService parameterizedMobileSearchDataQueryWithTwoFilters API response\n");
		}
		
		if(parameterizedMobileSearchDataQueryWithTwoFiltersReqGen.respvalidate.DoesNodeExists(
				MobileSearchServiceDataNodes.MOBILE_PARAMETERIZED_SEARCH_RESP_DATA.getNodePath())  && 
				parameterizedMobileSearchDataQueryWithTwoFiltersReqGen.respvalidate.DoesNodeExists(
				MobileSearchServiceDataNodes.MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_SEO.getNodePath())){
			
			AssertJUnit.assertTrue("Invalid seo data tag nodes in MobileSearchService parameterizedMobileSearchDataQueryWithTwoFilters API response", 
					parameterizedMobileSearchDataQueryWithTwoFiltersReqGen.respvalidate.DoesNodesExists(
							MobileSearchServiceDataNodes.getParameterizedSearchSeoDataNodes()));
			
			AssertJUnit.assertTrue("Invalid page_title node value in MobileSearchService parameterizedMobileSearchDataQueryWithTwoFilters API response", 
					parameterizedMobileSearchDataQueryWithTwoFiltersReqGen.respvalidate.NodeValue(
							MobileSearchServiceDataNodes.MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_SEO_META_DATA_PAGE_TITLE.getNodePath(), false).contains(
									paramMobileSearchDataQuery));
			
		} else {
			System.out.println("\nseo data nodes doesnt exists in MobileSearchService parameterizedMobileSearchDataQueryWithTwoFilters API response\n");
			log.info("\nseo data nodes doesnt exists in MobileSearchService parameterizedMobileSearchDataQueryWithTwoFilters API response\n");
		}
		
		if(parameterizedMobileSearchDataQueryWithTwoFiltersReqGen.respvalidate.DoesNodeExists(
				MobileSearchServiceDataNodes.MOBILE_PARAMETERIZED_SEARCH_RESP_DATA.getNodePath())  
				&& parameterizedMobileSearchDataQueryWithTwoFiltersReqGen.respvalidate.DoesNodeExists(
				MobileSearchServiceDataNodes.MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_SEARCH.getNodePath())){
			
			AssertJUnit.assertTrue("Invalid search data tag nodes in MobileSearchService parameterizedMobileSearchDataQueryWithTwoFilters API response", 
					parameterizedMobileSearchDataQueryWithTwoFiltersReqGen.respvalidate.DoesNodesExists(
							MobileSearchServiceDataNodes.getParameterizedSearchDataSearchNodes()));
			
			AssertJUnit.assertNotNull("filterQueryParam should not be null in MobileSearchService parameterizedMobileSearchDataQueryWithTwoFilters API response", 
					parameterizedMobileSearchDataQueryWithTwoFiltersReqGen.respvalidate.NodeValue(
							MobileSearchServiceDataNodes.MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_RESULTS_FILTER_QUERY_PARAM.getNodePath(), false));
			
		} else {
			System.out.println("\nsearch data nodes doesnt exists in MobileSearchService parameterizedMobileSearchDataQueryWithTwoFilters API response\n");
			log.info("\nsearch data nodes doesnt exists in MobileSearchService parameterizedMobileSearchDataQueryWithTwoFilters API response\n");
		}
		
		MyntAssert.setJsonResponse(parameterizedMobileSearchDataQueryWithTwoFiltersResponse);
		MyntAssert.setExpectedAndActualForMyntListen(true);
		
	}
	
	/**
	 * This TestCase is used to invoke MobileSearchService parameterizedMobileSearchDataQueryWithTwoFilters API and verification for filters data tag nodes 
	 * in the response 
	 * 
	 * @param paramMobileSearchDataQuery
	 * @param paramArtifact1
	 * @param paramValue1
	 * @param paramArtifact2
	 * @param paramValue2
	 * @param successRespCode
	 */
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" }, 
			dataProvider = "MobileSearchServiceDP_parameterizedMobileSearchDataQueryWithTwoFilters_verifyFiltersDataTagNodes")
	public void MobileSearchService_parameterizedMobileSearchDataQueryWithTwoFilters_verifyFiltersDataTagNodes(String paramMobileSearchDataQuery, String paramArtifact1,
			String paramValue1, String paramArtifact2, String paramValue2, String successRespCode) 
	{
		RequestGenerator parameterizedMobileSearchDataQueryWithTwoFiltersReqGen = mobileSearchServiceHelper.invokeParameterizedMobileSearchDataQueryWithTwoFilters(
				paramMobileSearchDataQuery, paramArtifact1, paramValue1, paramArtifact2, paramValue2);
		
	    String parameterizedMobileSearchDataQueryWithTwoFiltersResponse = parameterizedMobileSearchDataQueryWithTwoFiltersReqGen.respvalidate.returnresponseasstring();
	    System.out.println("\nPrinting MobileSearchService parameterizedMobileSearchDataQueryWithTwoFilters API response :\n\n"+
	    		parameterizedMobileSearchDataQueryWithTwoFiltersResponse+"\n");
		log.info("\nPrinting MobileSearchService parameterizedMobileSearchDataQueryWithTwoFilters API response :\n\n"+
	    		parameterizedMobileSearchDataQueryWithTwoFiltersResponse+"\n");
			
		MyntAssert.assertEquals("MobileSearchService parameterizedMobileSearchDataQueryWithTwoFilters API is not working",  Integer.parseInt(successRespCode), 
				parameterizedMobileSearchDataQueryWithTwoFiltersReqGen.response.getStatus());
		
		if(parameterizedMobileSearchDataQueryWithTwoFiltersReqGen.respvalidate.DoesNodeExists(
				MobileSearchServiceDataNodes.MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_RESULTS_FILTERS.getNodePath())){
			
			String responseFilterNodes = parameterizedMobileSearchDataQueryWithTwoFiltersReqGen.respvalidate.NodeValue(
					MobileSearchServiceDataNodes.MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_RESULTS_FILTERS.getNodePath(), false);
			
			if(!responseFilterNodes.equals(mobileSearchServiceList.toString())){
				
				AssertJUnit.assertTrue("Invalid filters data tag nodes in MobileSearchService parameterizedMobileSearchDataQueryWithTwoFilters API response", 
						parameterizedMobileSearchDataQueryWithTwoFiltersReqGen.respvalidate.DoesNodesExists(
								MobileSearchServiceDataNodes.getParameterizedSearchResultsFiltersDataNodes()));
				
			} else {
				System.out.println("\nfilters data nodes are empty in MobileSearchService parameterizedMobileSearchDataQueryWithTwoFilters API response\n");
				log.info("\nfilters data nodes are empty in MobileSearchService parameterizedMobileSearchDataQueryWithTwoFilters API response\n");
			}
			
		} else {
			System.out.println("\nfilters data nodes doesnt exists in MobileSearchService parameterizedMobileSearchDataQueryWithTwoFilters API response\n");
			log.info("\nfilters data nodes doesnt exists in MobileSearchService parameterizedMobileSearchDataQueryWithTwoFilters API response\n");
		}
		
		MyntAssert.setJsonResponse(parameterizedMobileSearchDataQueryWithTwoFiltersResponse);
		MyntAssert.setExpectedAndActualForMyntListen(true);
	}
	
	/**
	 * This TestCase is used to invoke MobileSearchService parameterizedMobileSearchDataQueryWithTwoFilters API and verification for products data tag nodes 
	 * in the response 
	 * 
	 * @param paramMobileSearchDataQuery
	 * @param paramArtifact1
	 * @param paramValue1
	 * @param paramArtifact2
	 * @param paramValue2
	 * @param successRespCode
	 */
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" }, 
			dataProvider = "MobileSearchServiceDP_parameterizedMobileSearchDataQueryWithTwoFilters_verifyProductsDataTagNodes")
	public void MobileSearchService_parameterizedMobileSearchDataQueryWithTwoFilters_verifyProductsDataTagNodes(String paramMobileSearchDataQuery, String paramArtifact1,
			String paramValue1, String paramArtifact2, String paramValue2, String successRespCode) 
	{
		RequestGenerator parameterizedMobileSearchDataQueryWithTwoFiltersReqGen = mobileSearchServiceHelper.invokeParameterizedMobileSearchDataQueryWithTwoFilters(
				paramMobileSearchDataQuery, paramArtifact1, paramValue1, paramArtifact2, paramValue2);
		
	    String parameterizedMobileSearchDataQueryWithTwoFiltersResponse = parameterizedMobileSearchDataQueryWithTwoFiltersReqGen.respvalidate.returnresponseasstring();
	    System.out.println("\nPrinting MobileSearchService parameterizedMobileSearchDataQueryWithTwoFilters API response :\n\n"+
	    		parameterizedMobileSearchDataQueryWithTwoFiltersResponse+"\n");
		log.info("\nPrinting MobileSearchService parameterizedMobileSearchDataQueryWithTwoFilters API response :\n\n"+
	    		parameterizedMobileSearchDataQueryWithTwoFiltersResponse+"\n");
			
		MyntAssert.assertEquals("MobileSearchService parameterizedMobileSearchDataQueryWithTwoFilters API is not working",  Integer.parseInt(successRespCode), 
				parameterizedMobileSearchDataQueryWithTwoFiltersReqGen.response.getStatus());
		
		if(parameterizedMobileSearchDataQueryWithTwoFiltersReqGen.respvalidate.DoesNodeExists(
				MobileSearchServiceDataNodes.MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_RESULTS_PRODUCTS.getNodePath())){
			
			String responseProductNodes = parameterizedMobileSearchDataQueryWithTwoFiltersReqGen.respvalidate.NodeValue(
					MobileSearchServiceDataNodes.MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_RESULTS_PRODUCTS.getNodePath(), false);
			
			if(!responseProductNodes.equals(mobileSearchServiceList.toString())) {
				
				AssertJUnit.assertTrue("Invalid products data tag nodes in MobileSearchService parameterizedMobileSearchDataQueryWithTwoFilters API response", 
						parameterizedMobileSearchDataQueryWithTwoFiltersReqGen.respvalidate.DoesNodesExists(
								MobileSearchServiceDataNodes.getParameterizedSearchResultsProductsDataNodes()));
				
			} else if(parameterizedMobileSearchDataQueryWithTwoFiltersReqGen.respvalidate.DoesNodeExists(
					MobileSearchServiceDataNodes.MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_RESULTS_CURATED_PRODUCTS.getNodePath())){
				
					String curatedProducts = parameterizedMobileSearchDataQueryWithTwoFiltersReqGen.respvalidate.NodeValue(
							MobileSearchServiceDataNodes.MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_RESULTS_CURATED_PRODUCTS.getNodePath(), false);
					
					if(!curatedProducts.equals(mobileSearchServiceList.toString())){
	
						AssertJUnit.assertTrue("Invalid curated products data tag nodes in MobileSearchService parameterizedMobileSearchDataQueryWithTwoFilters API response", 
								parameterizedMobileSearchDataQueryWithTwoFiltersReqGen.respvalidate.DoesNodesExists(
										MobileSearchServiceDataNodes.getParameterizedSearchResultsCuratedProductsDataNodes()));
						
					} else {
						System.out.println("\ncurated products data nodes are empty in MobileSearchService parameterizedMobileSearchDataQueryWithTwoFilters API response\n");
						log.info("\ncurated products data nodes are empty in MobileSearchService parameterizedMobileSearchDataQueryWithTwoFilters API response\n");
					}
				
			} else {
				System.out.println("\ncurated products data nodes doesnt exists in MobileSearchService parameterizedMobileSearchDataQueryWithTwoFilters API response\n");
				log.info("\ncurated products data nodes doesnt exists in MobileSearchService parameterizedMobileSearchDataQueryWithTwoFilters API response\n");
			}
		
		} else {
			System.out.println("\nproducts data nodes are empty in MobileSearchService parameterizedMobileSearchDataQueryWithTwoFilters API response\n");
			log.info("\nproducts data nodes are empty in MobileSearchService parameterizedMobileSearchDataQueryWithTwoFilters API response\n");
		}
		
		MyntAssert.setJsonResponse(parameterizedMobileSearchDataQueryWithTwoFiltersResponse);
		MyntAssert.setExpectedAndActualForMyntListen(true);
	}

	/**
	 * This TestCase is used to invoke MobileSearchService parameterizedMobileSearchDataQueryWithAllFilters API and verification for success response
	 * 
	 * @param paramMobileSearchDataQuery
	 * @param paramArtifact1
	 * @param paramValue1
	 * @param paramArtifact2
	 * @param paramValue2
	 * @param paramArtifact3
	 * @param paramValue3
	 * @param paramArtifact4
	 * @param paramValue4
	 * @param paramArtifact5
	 * @param paramValue5
	 * @param successRespCode
	 */
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "MiniRegression", "Regression", "ExhaustiveRegression" }, 
			dataProvider = "MobileSearchServiceDP_parameterizedMobileSearchDataQueryWithAllFilters_verifySuccessResponse")
	public void MobileSearchService_parameterizedMobileSearchDataQueryWithAllFilters_verifySuccessResponse(String paramMobileSearchDataQuery, String paramArtifact1,
			String paramValue1, String paramArtifact2, String paramValue2, String paramArtifact3, String paramValue3, String paramArtifact4, String paramValue4, 
			String paramArtifact5, String paramValue5, String successRespCode) 
	{
		RequestGenerator parameterizedMobileSearchDataQueryWithAllFiltersReqGen = mobileSearchServiceHelper.invokeParameterizedMobileSearchDataQueryWithAllFilters(
				paramMobileSearchDataQuery, paramArtifact1, paramValue1, paramArtifact2, paramValue2, paramArtifact3, paramValue3, paramArtifact4, paramValue4, 
				paramArtifact5, paramValue5);
	    String parameterizedMobileSearchDataQueryWithAllFiltersResponse = parameterizedMobileSearchDataQueryWithAllFiltersReqGen.respvalidate.returnresponseasstring();
	    System.out.println("\nPrinting MobileSearchService parameterizedMobileSearchDataQueryWithAllFilters API response :\n\n"+
	    		parameterizedMobileSearchDataQueryWithAllFiltersResponse+"\n");
		log.info("\nPrinting MobileSearchService parameterizedMobileSearchDataQueryWithAllFilters API response :\n\n"+
	    		parameterizedMobileSearchDataQueryWithAllFiltersResponse+"\n");
			
		MyntAssert.assertEquals("MobileSearchService parameterizedMobileSearchDataQueryWithAllFilters API is not working",  Integer.parseInt(successRespCode), 
				parameterizedMobileSearchDataQueryWithAllFiltersReqGen.response.getStatus());
		
		MyntAssert.setJsonResponse(parameterizedMobileSearchDataQueryWithAllFiltersResponse);
		MyntAssert.setExpectedAndActualForMyntListen(true);
	}
	
	/**
	 * This TestCase is used to invoke MobileSearchService parameterizedMobileSearchDataQueryWithAllFilters API and verification for data tag nodes in the response
	 * 
	 * @param paramMobileSearchDataQuery
	 * @param paramArtifact1
	 * @param paramValue1
	 * @param paramArtifact2
	 * @param paramValue2
	 * @param paramArtifact3
	 * @param paramValue3
	 * @param paramArtifact4
	 * @param paramValue4
	 * @param paramArtifact5
	 * @param paramValue5
	 * @param successRespCode
	 */
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" }, 
			dataProvider = "MobileSearchServiceDP_parameterizedMobileSearchDataQueryWithAllFilters_verifyDataTagNodes")
	public void MobileSearchService_parameterizedMobileSearchDataQueryWithAllFilters_verifyDataTagNodes(String paramMobileSearchDataQuery, String paramArtifact1,
			String paramValue1, String paramArtifact2, String paramValue2, String paramArtifact3, String paramValue3, String paramArtifact4, String paramValue4, 
			String paramArtifact5, String paramValue5, String successRespCode)
	{
		RequestGenerator parameterizedMobileSearchDataQueryWithAllFiltersReqGen = mobileSearchServiceHelper.invokeParameterizedMobileSearchDataQueryWithAllFilters(
				paramMobileSearchDataQuery, paramArtifact1, paramValue1, paramArtifact2, paramValue2, paramArtifact3, paramValue3, paramArtifact4, paramValue4, 
				paramArtifact5, paramValue5);
	    String parameterizedMobileSearchDataQueryWithAllFiltersResponse = parameterizedMobileSearchDataQueryWithAllFiltersReqGen.respvalidate.returnresponseasstring();
	    System.out.println("\nPrinting MobileSearchService parameterizedMobileSearchDataQueryWithAllFilters API response :\n\n"+
	    		parameterizedMobileSearchDataQueryWithAllFiltersResponse+"\n");
		log.info("\nPrinting MobileSearchService parameterizedMobileSearchDataQueryWithAllFilters API response :\n\n"+
	    		parameterizedMobileSearchDataQueryWithAllFiltersResponse+"\n");
			
		MyntAssert.assertEquals("MobileSearchService parameterizedMobileSearchDataQueryWithAllFilters API is not working",  Integer.parseInt(successRespCode), 
				parameterizedMobileSearchDataQueryWithAllFiltersReqGen.response.getStatus());
		
		if(parameterizedMobileSearchDataQueryWithAllFiltersReqGen.respvalidate.DoesNodeExists(
				MobileSearchServiceDataNodes.MOBILE_PARAMETERIZED_SEARCH_RESP_DATA.getNodePath())){
			
			AssertJUnit.assertTrue("Invalid data tag nodes in MobileSearchService parameterizedMobileSearchDataQueryWithAllFilters API response", 
					parameterizedMobileSearchDataQueryWithAllFiltersReqGen.respvalidate.DoesNodesExists(MobileSearchServiceDataNodes.getParameterizedSearchDataNodes()));
			
		} else{
			System.out.println("\n data nodes doesnt exists in MobileSearchService parameterizedMobileSearchDataQueryWithAllFilters API response\n");
			log.info("\n data nodes doesnt exists in MobileSearchService parameterizedMobileSearchDataQueryWithAllFilters API response\n");
		}
		
		if(parameterizedMobileSearchDataQueryWithAllFiltersReqGen.respvalidate.DoesNodeExists(
				MobileSearchServiceDataNodes.MOBILE_PARAMETERIZED_SEARCH_RESP_DATA.getNodePath())  && 
				parameterizedMobileSearchDataQueryWithAllFiltersReqGen.respvalidate.DoesNodeExists(
				MobileSearchServiceDataNodes.MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_SEO.getNodePath())){
			
			AssertJUnit.assertTrue("Invalid seo data tag nodes in MobileSearchService parameterizedMobileSearchDataQueryWithAllFilters API response", 
					parameterizedMobileSearchDataQueryWithAllFiltersReqGen.respvalidate.DoesNodesExists(
							MobileSearchServiceDataNodes.getParameterizedSearchSeoDataNodes()));
			
			AssertJUnit.assertTrue("Invalid page_title node value in MobileSearchService parameterizedMobileSearchDataQueryWithAllFilters API response", 
					parameterizedMobileSearchDataQueryWithAllFiltersReqGen.respvalidate.NodeValue(
							MobileSearchServiceDataNodes.MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_SEO_META_DATA_PAGE_TITLE.getNodePath(), false).contains(
									paramMobileSearchDataQuery));
			
		} else {
			System.out.println("\nseo data nodes doesnt exists in MobileSearchService parameterizedMobileSearchDataQueryWithAllFilters API response\n");
			log.info("\nseo data nodes doesnt exists in MobileSearchService parameterizedMobileSearchDataQueryWithAllFilters API response\n");
		}
		
		if(parameterizedMobileSearchDataQueryWithAllFiltersReqGen.respvalidate.DoesNodeExists(
				MobileSearchServiceDataNodes.MOBILE_PARAMETERIZED_SEARCH_RESP_DATA.getNodePath())  
				&& parameterizedMobileSearchDataQueryWithAllFiltersReqGen.respvalidate.DoesNodeExists(
				MobileSearchServiceDataNodes.MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_SEARCH.getNodePath())){
			
			AssertJUnit.assertTrue("Invalid search data tag nodes in MobileSearchService parameterizedMobileSearchDataQueryWithAllFilters API response", 
					parameterizedMobileSearchDataQueryWithAllFiltersReqGen.respvalidate.DoesNodesExists(
							MobileSearchServiceDataNodes.getParameterizedSearchDataSearchNodes()));
			
			AssertJUnit.assertNotNull("filterQueryParam should not be null in MobileSearchService parameterizedMobileSearchDataQueryWithAllFilters API response", 
					parameterizedMobileSearchDataQueryWithAllFiltersReqGen.respvalidate.NodeValue(
							MobileSearchServiceDataNodes.MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_RESULTS_FILTER_QUERY_PARAM.getNodePath(), false));
			
		} else {
			System.out.println("\nsearch data nodes doesnt exists in MobileSearchService parameterizedMobileSearchDataQueryWithAllFilters API response\n");
			log.info("\nsearch data nodes doesnt exists in MobileSearchService parameterizedMobileSearchDataQueryWithAllFilters API response\n");
		}
		
		MyntAssert.setJsonResponse(parameterizedMobileSearchDataQueryWithAllFiltersResponse);
		MyntAssert.setExpectedAndActualForMyntListen(true);
		
	}
	
	/**
	 * This TestCase is used to invoke MobileSearchService parameterizedMobileSearchDataQueryWithAllFilters API and verification for filters data tag nodes 
	 * in the response 
	 * 
	 * @param paramMobileSearchDataQuery
	 * @param paramArtifact1
	 * @param paramValue1
	 * @param paramArtifact2
	 * @param paramValue2
	 * @param paramArtifact3
	 * @param paramValue3
	 * @param paramArtifact4
	 * @param paramValue4
	 * @param paramArtifact5
	 * @param paramValue5
	 * @param successRespCode
	 */
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" }, 
			dataProvider = "MobileSearchServiceDP_parameterizedMobileSearchDataQueryWithAllFilters_verifyFiltersDataTagNodes")
	public void MobileSearchService_parameterizedMobileSearchDataQueryWithAllFilters_verifyFiltersDataTagNodes(String paramMobileSearchDataQuery, String paramArtifact1,
			String paramValue1, String paramArtifact2, String paramValue2, String paramArtifact3, String paramValue3, String paramArtifact4, String paramValue4, 
			String paramArtifact5, String paramValue5, String successRespCode)
	{
		RequestGenerator parameterizedMobileSearchDataQueryWithAllFiltersReqGen = mobileSearchServiceHelper.invokeParameterizedMobileSearchDataQueryWithAllFilters(
				paramMobileSearchDataQuery, paramArtifact1, paramValue1, paramArtifact2, paramValue2, paramArtifact3, paramValue3, paramArtifact4, paramValue4, 
				paramArtifact5, paramValue5);
	    String parameterizedMobileSearchDataQueryWithAllFiltersResponse = parameterizedMobileSearchDataQueryWithAllFiltersReqGen.respvalidate.returnresponseasstring();
	    System.out.println("\nPrinting MobileSearchService parameterizedMobileSearchDataQueryWithAllFilters API response :\n\n"+
	    		parameterizedMobileSearchDataQueryWithAllFiltersResponse+"\n");
		log.info("\nPrinting MobileSearchService parameterizedMobileSearchDataQueryWithAllFilters API response :\n\n"+
	    		parameterizedMobileSearchDataQueryWithAllFiltersResponse+"\n");
			
		MyntAssert.assertEquals("MobileSearchService parameterizedMobileSearchDataQueryWithAllFilters API is not working",  Integer.parseInt(successRespCode), 
				parameterizedMobileSearchDataQueryWithAllFiltersReqGen.response.getStatus());
		
		if(parameterizedMobileSearchDataQueryWithAllFiltersReqGen.respvalidate.DoesNodeExists(
				MobileSearchServiceDataNodes.MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_RESULTS_FILTERS.getNodePath())){
			
			String responseFilterNodes = parameterizedMobileSearchDataQueryWithAllFiltersReqGen.respvalidate.NodeValue(
					MobileSearchServiceDataNodes.MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_RESULTS_FILTERS.getNodePath(), false);
			
			if(!responseFilterNodes.equals(mobileSearchServiceList.toString())){
				
				AssertJUnit.assertTrue("Invalid filters data tag nodes in MobileSearchService parameterizedMobileSearchDataQueryWithAllFilters API response", 
						parameterizedMobileSearchDataQueryWithAllFiltersReqGen.respvalidate.DoesNodesExists(
								MobileSearchServiceDataNodes.getParameterizedSearchResultsFiltersDataNodes()));
				
			} else {
				System.out.println("\nfilters data nodes are empty in MobileSearchService parameterizedMobileSearchDataQueryWithAllFilters API response\n");
				log.info("\nfilters data nodes are empty in MobileSearchService parameterizedMobileSearchDataQueryWithAllFilters API response\n");
			}
			
		} else {
			System.out.println("\nfilters data nodes doesnt exists in MobileSearchService parameterizedMobileSearchDataQueryWithAllFilters API response\n");
			log.info("\nfilters data nodes doesnt exists in MobileSearchService parameterizedMobileSearchDataQueryWithAllFilters API response\n");
		}
		
		MyntAssert.setJsonResponse(parameterizedMobileSearchDataQueryWithAllFiltersResponse);
		MyntAssert.setExpectedAndActualForMyntListen(true);
	}
	
	/**
	 * This TestCase is used to invoke MobileSearchService parameterizedMobileSearchDataQueryWithAllFilters API and verification for products data tag nodes 
	 * in the response 
	 * 
	 * @param paramMobileSearchDataQuery
	 * @param paramArtifact1
	 * @param paramValue1
	 * @param paramArtifact2
	 * @param paramValue2
	 * @param paramArtifact3
	 * @param paramValue3
	 * @param paramArtifact4
	 * @param paramValue4
	 * @param paramArtifact5
	 * @param paramValue5
	 * @param successRespCode
	 */
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" }, 
			dataProvider = "MobileSearchServiceDP_parameterizedMobileSearchDataQueryWithAllFilters_verifyProductsDataTagNodes")
	public void MobileSearchService_parameterizedMobileSearchDataQueryWithAllFilters_verifyProductsDataTagNodes(String paramMobileSearchDataQuery, String paramArtifact1,
			String paramValue1, String paramArtifact2, String paramValue2, String paramArtifact3, String paramValue3, String paramArtifact4, String paramValue4, 
			String paramArtifact5, String paramValue5, String successRespCode) 
	{
		RequestGenerator parameterizedMobileSearchDataQueryWithAllFiltersReqGen = mobileSearchServiceHelper.invokeParameterizedMobileSearchDataQueryWithAllFilters(
				paramMobileSearchDataQuery, paramArtifact1, paramValue1, paramArtifact2, paramValue2, paramArtifact3, paramValue3, paramArtifact4, paramValue4, 
				paramArtifact5, paramValue5);
	    String parameterizedMobileSearchDataQueryWithAllFiltersResponse = parameterizedMobileSearchDataQueryWithAllFiltersReqGen.respvalidate.returnresponseasstring();
	    System.out.println("\nPrinting MobileSearchService parameterizedMobileSearchDataQueryWithAllFilters API response :\n\n"+
	    		parameterizedMobileSearchDataQueryWithAllFiltersResponse+"\n");
		log.info("\nPrinting MobileSearchService parameterizedMobileSearchDataQueryWithAllFilters API response :\n\n"+
	    		parameterizedMobileSearchDataQueryWithAllFiltersResponse+"\n");
			
		MyntAssert.assertEquals("MobileSearchService parameterizedMobileSearchDataQueryWithAllFilters API is not working",  Integer.parseInt(successRespCode), 
				parameterizedMobileSearchDataQueryWithAllFiltersReqGen.response.getStatus());
		
		if(parameterizedMobileSearchDataQueryWithAllFiltersReqGen.respvalidate.DoesNodeExists(
				MobileSearchServiceDataNodes.MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_RESULTS_PRODUCTS.getNodePath())){
			
			String responseProductNodes = parameterizedMobileSearchDataQueryWithAllFiltersReqGen.respvalidate.NodeValue(
					MobileSearchServiceDataNodes.MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_RESULTS_PRODUCTS.getNodePath(), false);
			
			if(!responseProductNodes.equals(mobileSearchServiceList.toString())) {
				
				AssertJUnit.assertTrue("Invalid products data tag nodes in MobileSearchService parameterizedMobileSearchDataQueryWithAllFilters API response", 
						parameterizedMobileSearchDataQueryWithAllFiltersReqGen.respvalidate.DoesNodesExists(
								MobileSearchServiceDataNodes.getParameterizedSearchResultsProductsDataNodes()));
				
			} else if(parameterizedMobileSearchDataQueryWithAllFiltersReqGen.respvalidate.DoesNodeExists(
					MobileSearchServiceDataNodes.MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_RESULTS_CURATED_PRODUCTS.getNodePath())){
				
					String curatedProducts = parameterizedMobileSearchDataQueryWithAllFiltersReqGen.respvalidate.NodeValue(
							MobileSearchServiceDataNodes.MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_RESULTS_CURATED_PRODUCTS.getNodePath(), false);
					
					if(!curatedProducts.equals(mobileSearchServiceList.toString())){
	
						AssertJUnit.assertTrue("Invalid curated products data tag nodes in MobileSearchService parameterizedMobileSearchDataQueryWithAllFilters API response", 
								parameterizedMobileSearchDataQueryWithAllFiltersReqGen.respvalidate.DoesNodesExists(
										MobileSearchServiceDataNodes.getParameterizedSearchResultsCuratedProductsDataNodes()));
						
					} else {
						System.out.println("\ncurated products data nodes are empty in MobileSearchService parameterizedMobileSearchDataQueryWithAllFilters API response\n");
						log.info("\ncurated products data nodes are empty in MobileSearchService parameterizedMobileSearchDataQueryWithAllFilters API response\n");
					}
				
			} else {
				System.out.println("\ncurated products data nodes doesnt exists in MobileSearchService parameterizedMobileSearchDataQueryWithAllFilters API response\n");
				log.info("\ncurated products data nodes doesnt exists in MobileSearchService parameterizedMobileSearchDataQueryWithAllFilters API response\n");
			}
		
		} else {
			System.out.println("\nproducts data nodes are empty in MobileSearchService parameterizedMobileSearchDataQueryWithAllFilters API response\n");
			log.info("\nproducts data nodes are empty in MobileSearchService parameterizedMobileSearchDataQueryWithAllFilters API response\n");
		}
		
		MyntAssert.setJsonResponse(parameterizedMobileSearchDataQueryWithAllFiltersResponse);
		MyntAssert.setExpectedAndActualForMyntListen(true);
	}
	
	/**
	 * This TestCase is used to invoke MobileSearchService parameterizedMobileSearchDataQueryWithSortBy API and verification for success response
	 * 
	 * @param paramMobileSearchDataQuery
	 * @param paramSortBy
	 * @param successRespCode
	 */
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "MiniRegression", "Regression", "ExhaustiveRegression" }, 
			dataProvider = "MobileSearchServiceDP_parameterizedMobileSearchDataQueryWithSortBy_verifySuccessResponse")
	public void MobileSearchService_parameterizedMobileSearchDataQueryWithSortBy_verifySuccessResponse(String paramMobileSearchDataQuery, String paramSortBy,
			String successRespCode) 
	{
		RequestGenerator parameterizedMobileSearchDataQueryWithSortByReqGen = mobileSearchServiceHelper.invokeParameterizedMobileSearchDataQueryWithSortBy(
				paramMobileSearchDataQuery, paramSortBy);
	    String parameterizedMobileSearchDataQueryWithSortByResponse = parameterizedMobileSearchDataQueryWithSortByReqGen.respvalidate.returnresponseasstring();
	    System.out.println("\nPrinting MobileSearchService parameterizedMobileSearchDataQueryWithSortBy API response :\n\n"+
	    		parameterizedMobileSearchDataQueryWithSortByResponse+"\n");
		log.info("\nPrinting MobileSearchService parameterizedMobileSearchDataQueryWithSortBy API response :\n\n"+
	    		parameterizedMobileSearchDataQueryWithSortByResponse+"\n");
			
		MyntAssert.assertEquals("MobileSearchService parameterizedMobileSearchDataQueryWithSortBy API is not working",  Integer.parseInt(successRespCode), 
				parameterizedMobileSearchDataQueryWithSortByReqGen.response.getStatus());
		
		MyntAssert.setJsonResponse(parameterizedMobileSearchDataQueryWithSortByResponse);
		MyntAssert.setExpectedAndActualForMyntListen(true);
	
	}
	
	/**
	 *  This TestCase is used to invoke MobileSearchService parameterizedMobileSearchDataQueryWithSortBy API and verification for data tag nodes in the response
	 * 
	 * @param paramMobileSearchDataQuery
	 * @param paramSortBy
	 * @param successRespCode
	 */
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" }, 
			dataProvider = "MobileSearchServiceDP_parameterizedMobileSearchDataQueryWithSortBy_verifyDataTagNodes")
	public void MobileSearchService_parameterizedMobileSearchDataQueryWithSortBy_verifyDataTagNodes(String paramMobileSearchDataQuery, String paramSortBy,
			String successRespCode)
	{
		RequestGenerator parameterizedMobileSearchDataQueryWithSortByReqGen = mobileSearchServiceHelper.invokeParameterizedMobileSearchDataQueryWithSortBy(
				paramMobileSearchDataQuery, paramSortBy);
	    String parameterizedMobileSearchDataQueryWithSortByResponse = parameterizedMobileSearchDataQueryWithSortByReqGen.respvalidate.returnresponseasstring();
	    System.out.println("\nPrinting MobileSearchService parameterizedMobileSearchDataQueryWithSortBy API response :\n\n"+
	    		parameterizedMobileSearchDataQueryWithSortByResponse+"\n");
		log.info("\nPrinting MobileSearchService parameterizedMobileSearchDataQueryWithSortBy API response :\n\n"+
	    		parameterizedMobileSearchDataQueryWithSortByResponse+"\n");
			
		MyntAssert.assertEquals("MobileSearchService parameterizedMobileSearchDataQueryWithSortBy API is not working",  Integer.parseInt(successRespCode), 
				parameterizedMobileSearchDataQueryWithSortByReqGen.response.getStatus());
		
		if(parameterizedMobileSearchDataQueryWithSortByReqGen.respvalidate.DoesNodeExists(
				MobileSearchServiceDataNodes.MOBILE_PARAMETERIZED_SEARCH_RESP_DATA.getNodePath())){
			
			AssertJUnit.assertTrue("Invalid data tag nodes in MobileSearchService parameterizedMobileSearchDataQueryWithSortBy API response", 
					parameterizedMobileSearchDataQueryWithSortByReqGen.respvalidate.DoesNodesExists(MobileSearchServiceDataNodes.getParameterizedSearchDataNodes()));
			
		} else{
			System.out.println("\n data nodes doesnt exists in MobileSearchService parameterizedMobileSearchDataQueryWithSortBy API response\n");
			log.info("\n data nodes doesnt exists in MobileSearchService parameterizedMobileSearchDataQueryWithSortBy API response\n");
		}
		
		if(parameterizedMobileSearchDataQueryWithSortByReqGen.respvalidate.DoesNodeExists(
				MobileSearchServiceDataNodes.MOBILE_PARAMETERIZED_SEARCH_RESP_DATA.getNodePath())  
				&& parameterizedMobileSearchDataQueryWithSortByReqGen.respvalidate.DoesNodeExists(
				MobileSearchServiceDataNodes.MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_SEO.getNodePath())){
			
			AssertJUnit.assertTrue("Invalid seo data tag nodes in MobileSearchService parameterizedMobileSearchDataQueryWithSortBy API response", 
					parameterizedMobileSearchDataQueryWithSortByReqGen.respvalidate.DoesNodesExists(MobileSearchServiceDataNodes.getParameterizedSearchSeoDataNodes()));
			
			AssertJUnit.assertTrue("Invalid page_title node value in MobileSearchService parameterizedMobileSearchDataQueryWithSortBy API response", 
					parameterizedMobileSearchDataQueryWithSortByReqGen.respvalidate.NodeValue(
							MobileSearchServiceDataNodes.MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_SEO_META_DATA_PAGE_TITLE.getNodePath(), false).contains(
									paramMobileSearchDataQuery));
			
		} else {
			System.out.println("\nseo data nodes doesnt exists in MobileSearchService parameterizedMobileSearchDataQueryWithSortBy API response\n");
			log.info("\nseo data nodes doesnt exists in MobileSearchService parameterizedMobileSearchDataQueryWithSortBy API response\n");
		}
		
		if(parameterizedMobileSearchDataQueryWithSortByReqGen.respvalidate.DoesNodeExists(
				MobileSearchServiceDataNodes.MOBILE_PARAMETERIZED_SEARCH_RESP_DATA.getNodePath())  
				&& parameterizedMobileSearchDataQueryWithSortByReqGen.respvalidate.DoesNodeExists(
				MobileSearchServiceDataNodes.MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_SEARCH.getNodePath())){
			
			AssertJUnit.assertTrue("Invalid search data tag nodes in MobileSearchService parameterizedMobileSearchDataQueryWithSortBy API response", 
					parameterizedMobileSearchDataQueryWithSortByReqGen.respvalidate.DoesNodesExists(
							MobileSearchServiceDataNodes.getParameterizedSearchDataSearchNodes()));
			
		} else {
			System.out.println("\nsearch data nodes doesnt exists in MobileSearchService parameterizedMobileSearchDataQueryWithSortBy API response\n");
			log.info("\nsearch data nodes doesnt exists in MobileSearchService parameterizedMobileSearchDataQueryWithSortBy API response\n");
		}
		
		MyntAssert.setJsonResponse(parameterizedMobileSearchDataQueryWithSortByResponse);
		MyntAssert.setExpectedAndActualForMyntListen(true);
		
	}
	
	/**
	 * This TestCase is used to invoke MobileSearchService parameterizedMobileSearchDataQueryWithSortBy API and verification for filters data tag nodes in the response 
	 * 
	 * @param paramMobileSearchDataQuery
	 * @param paramSortBy
	 * @param successRespCode
	 */
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" }, 
			dataProvider = "MobileSearchServiceDP_parameterizedMobileSearchDataQueryWithSortBy_verifyFiltersDataTagNodes")
	public void MobileSearchService_parameterizedMobileSearchDataQueryWithSortBy_verifyFiltersDataTagNodes(String paramMobileSearchDataQuery, String paramSortBy,
			String successRespCode) 
	{
		RequestGenerator parameterizedMobileSearchDataQueryWithSortByReqGen = mobileSearchServiceHelper.invokeParameterizedMobileSearchDataQueryWithSortBy(
				paramMobileSearchDataQuery, paramSortBy);
	    String parameterizedMobileSearchDataQueryWithSortByResponse = parameterizedMobileSearchDataQueryWithSortByReqGen.respvalidate.returnresponseasstring();
	    System.out.println("\nPrinting MobileSearchService parameterizedMobileSearchDataQueryWithSortBy API response :\n\n"+
	    		parameterizedMobileSearchDataQueryWithSortByResponse+"\n");
		log.info("\nPrinting MobileSearchService parameterizedMobileSearchDataQueryWithSortBy API response :\n\n"+
	    		parameterizedMobileSearchDataQueryWithSortByResponse+"\n");
			
		MyntAssert.assertEquals("MobileSearchService parameterizedMobileSearchDataQueryWithSortBy API is not working",  Integer.parseInt(successRespCode), 
				parameterizedMobileSearchDataQueryWithSortByReqGen.response.getStatus());
		
		if(parameterizedMobileSearchDataQueryWithSortByReqGen.respvalidate.DoesNodeExists(
				MobileSearchServiceDataNodes.MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_RESULTS_FILTERS.getNodePath())){
			
			String responseFilterNodes = parameterizedMobileSearchDataQueryWithSortByReqGen.respvalidate.NodeValue(
					MobileSearchServiceDataNodes.MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_RESULTS_FILTERS.getNodePath(), false);
			
			if(!responseFilterNodes.equals(mobileSearchServiceList.toString())){
				
				AssertJUnit.assertTrue("Invalid filters data tag nodes in MobileSearchService parameterizedMobileSearchDataQueryWithSortBy API response", 
						parameterizedMobileSearchDataQueryWithSortByReqGen.respvalidate.DoesNodesExists(
								MobileSearchServiceDataNodes.getParameterizedSearchResultsFiltersDataNodes()));
				
			} else {
				System.out.println("\nfilters data nodes are empty in MobileSearchService parameterizedMobileSearchDataQueryWithSortBy API response\n");
				log.info("\nfilters data nodes are empty in MobileSearchService parameterizedMobileSearchDataQueryWithSortBy API response\n");
			}
			
		} else {
			System.out.println("\nfilters data nodes doesnt exists in MobileSearchService parameterizedMobileSearchDataQueryWithSortBy API response\n");
			log.info("\nfilters data nodes doesnt exists in MobileSearchService parameterizedMobileSearchDataQueryWithSortBy API response\n");
		}
		
		MyntAssert.setJsonResponse(parameterizedMobileSearchDataQueryWithSortByResponse);
		MyntAssert.setExpectedAndActualForMyntListen(true);
	}
	
	/**
	 * This TestCase is used to invoke MobileSearchService parameterizedMobileSearchDataQueryWithSortBy API and verification for products data tag nodes in the response 
	 * 
	 * @param paramMobileSearchDataQuery
	 * @param paramSortBy
	 * @param successRespCode
	 */
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" }, 
			dataProvider = "MobileSearchServiceDP_parameterizedMobileSearchDataQueryWithSortBy_verifyProductsDataTagNodes")
	public void MobileSearchService_parameterizedMobileSearchDataQueryWithSortBy_verifyProductsDataTagNodes(String paramMobileSearchDataQuery, String paramSortBy,
			String successRespCode) 
	{
		RequestGenerator parameterizedMobileSearchDataQueryWithSortByReqGen = mobileSearchServiceHelper.invokeParameterizedMobileSearchDataQueryWithSortBy(
				paramMobileSearchDataQuery, paramSortBy);
	    String parameterizedMobileSearchDataQueryWithSortByResponse = parameterizedMobileSearchDataQueryWithSortByReqGen.respvalidate.returnresponseasstring();
	    System.out.println("\nPrinting MobileSearchService parameterizedMobileSearchDataQueryWithSortBy API response :\n\n"+
	    		parameterizedMobileSearchDataQueryWithSortByResponse+"\n");
		log.info("\nPrinting MobileSearchService parameterizedMobileSearchDataQueryWithSortBy API response :\n\n"+
	    		parameterizedMobileSearchDataQueryWithSortByResponse+"\n");
			
		MyntAssert.assertEquals("MobileSearchService parameterizedMobileSearchDataQueryWithSortBy API is not working",  Integer.parseInt(successRespCode), 
				parameterizedMobileSearchDataQueryWithSortByReqGen.response.getStatus());
		
		if(parameterizedMobileSearchDataQueryWithSortByReqGen.respvalidate.DoesNodeExists(
				MobileSearchServiceDataNodes.MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_RESULTS_PRODUCTS.getNodePath())){
			
			String responseProductNodes = parameterizedMobileSearchDataQueryWithSortByReqGen.respvalidate.NodeValue(
					MobileSearchServiceDataNodes.MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_RESULTS_PRODUCTS.getNodePath(), false);
			
			if(!responseProductNodes.equals(mobileSearchServiceList.toString())){
				
				AssertJUnit.assertTrue("Invalid products data tag nodes in MobileSearchService parameterizedMobileSearchDataQueryWithSortBy API response", 
						parameterizedMobileSearchDataQueryWithSortByReqGen.respvalidate.DoesNodesExists(
								MobileSearchServiceDataNodes.getParameterizedSearchResultsProductsDataNodes()));
				
			} else {
				System.out.println("\nproducts data nodes are empty in MobileSearchService parameterizedMobileSearchDataQueryWithSortBy API response\n");
				log.info("\nproducts data nodes are empty in MobileSearchService parameterizedMobileSearchDataQueryWithSortBy API response\n");
			}
			
		} else {
			System.out.println("\nproducts data nodes doesnt exists in MobileSearchService parameterizedMobileSearchDataQueryWithSortBy API response\n");
			log.info("\nproducts data nodes doesnt exists in MobileSearchService parameterizedMobileSearchDataQueryWithSortBy API response\n");
		}
		
		MyntAssert.setJsonResponse(parameterizedMobileSearchDataQueryWithSortByResponse);
		MyntAssert.setExpectedAndActualForMyntListen(true);
	}
	
	
	/**
	 * This TestCase is used to invoke MobileSearchService parameterizedMobileSearchDataQueryWithSortBy API and verification for products in sorted order or not
	 * 
	 * @param paramMobileSearchDataQuery
	 * @param paramSortBy
	 * @param successRespCode
	 */
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" }, 
			dataProvider = "MobileSearchServiceDP_parameterizedMobileSearchDataQueryWithSortBy_verifyIsProductsInSortedOrder")
	public void MobileSearchService_parameterizedMobileSearchDataQueryWithSortBy_verifyIsProductsInSortedOrder(String paramMobileSearchDataQuery, String paramSortBy,
			String successRespCode) 
	{
		RequestGenerator parameterizedMobileSearchDataQueryWithSortByReqGen = mobileSearchServiceHelper.invokeParameterizedMobileSearchDataQueryWithSortBy(
				paramMobileSearchDataQuery, paramSortBy);
	    String parameterizedMobileSearchDataQueryWithSortByResponse = parameterizedMobileSearchDataQueryWithSortByReqGen.respvalidate.returnresponseasstring();
	    System.out.println("\nPrinting MobileSearchService parameterizedMobileSearchDataQueryWithSortBy API response :\n\n"+
	    		parameterizedMobileSearchDataQueryWithSortByResponse+"\n");
		log.info("\nPrinting MobileSearchService parameterizedMobileSearchDataQueryWithSortBy API response :\n\n"+
	    		parameterizedMobileSearchDataQueryWithSortByResponse+"\n");
			
		MyntAssert.assertEquals("MobileSearchService parameterizedMobileSearchDataQueryWithSortBy API is not working",  Integer.parseInt(successRespCode), 
				parameterizedMobileSearchDataQueryWithSortByReqGen.response.getStatus());
		
		if(parameterizedMobileSearchDataQueryWithSortByReqGen.respvalidate.DoesNodeExists(
				MobileSearchServiceDataNodes.MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_RESULTS_PRODUCTS.getNodePath())){
			
			String responseProductNodes = parameterizedMobileSearchDataQueryWithSortByReqGen.respvalidate.NodeValue(
					MobileSearchServiceDataNodes.MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_RESULTS_PRODUCTS.getNodePath(), false);
			
			if(!responseProductNodes.equals(mobileSearchServiceList.toString())){
				
				List<Integer> discountedPriceList = JsonPath.read(parameterizedMobileSearchDataQueryWithSortByResponse, "$.data.results.products[*].discounted_price");
				System.out.println("\nDiscountedPriceList : "+discountedPriceList+"\n");
				log.info("\nDiscountedPriceList : "+discountedPriceList+"\n");
				
				if(paramSortBy.equalsIgnoreCase("low")){
					
					AssertJUnit.assertTrue("discounted_price of the product should be in ascending order", 
							CommonUtils.isArraySortedInAscendingOrder(discountedPriceList));
					
				} else if(paramSortBy.equalsIgnoreCase("high")){
					
					AssertJUnit.assertTrue("discounted_price  of the product should be in descending order", 
							CommonUtils.isArraySortedInDescendingOrder(discountedPriceList));
					
				} 
				
			} else {
				System.out.println("\nproducts data nodes are empty in MobileSearchService parameterizedMobileSearchDataQueryWithSortBy API response\n");
				log.info("\nproducts data nodes are empty in MobileSearchService parameterizedMobileSearchDataQueryWithSortBy API response\n");
			}
			
		} else {
			System.out.println("\nproducts data nodes doesnt exists in MobileSearchService parameterizedMobileSearchDataQueryWithSortBy API response\n");
			log.info("\nproducts data nodes doesnt exists in MobileSearchService parameterizedMobileSearchDataQueryWithSortBy API response\n");
		}
		
		MyntAssert.setJsonResponse(parameterizedMobileSearchDataQueryWithSortByResponse);
		MyntAssert.setExpectedAndActualForMyntListen(true);
	}
	
	 /**
	  * This method is used to validate the response headers.
	  * 
	 * @param headers
	 * @return String
	 */
	private String validateResponseHeaders(MultivaluedMap<String, Object> headers)
	 {
    	boolean dateValidation = true;
    	boolean conValidation = true;
    	boolean contentLenValidation = true;
    	boolean conetntTypeValidation = true;
    	boolean sizeValidation = true;
    	String toReturn = "";

    	log.info(headers.toString());
    	DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
    	Date date = new Date();
    	String todaysDate = dateFormat.format(date);

    	if(!(headers.containsKey("Date") && headers.get("Date").toString().contains(todaysDate))){
    		dateValidation = false;
    		toReturn += "Either date is missing or response dosn't contains todays date\n";
    		log.info("Either date is missing or response dosn't contains todays date");
    	}   		

    	/*if(!(headers.containsKey("Connection") && headers.get("Connection").toString().equalsIgnoreCase("[close]"))){
    		conValidation = false;
    		toReturn += "Either Connection Header is missing or the value of the connection header is not \"close\"\n";
    		log.info("Either Connection Header is missing or the value of the connection header is not \"close\"");
    	}*/
    	
    	if(!headers.containsKey("Content-Length")){
    		contentLenValidation = false;
    		toReturn +="Content-Length is missing";
    		log.info("Content-Length is missing");
    	}
    	    	
    	if(!(headers.containsKey("Content-Type") && headers.get("Content-Type").toString().equalsIgnoreCase("[application/json; charset=utf-8]"))){
    		conetntTypeValidation = false;
    		toReturn += "Either Content-Type Header is missing or the value of the Content-Type header is not \"application/json; charset=utf-8\"\n";
    		log.info("Either Content-Type Header is missing or the value of the Content-Type header is not \"application/json; charset=utf-8\"");
    	}
    		
    	if(!(headers.size() > 0)){
    		sizeValidation = false;
    		toReturn += "Response header size should be greater than 1. Header size is :"+headers.size()+"\n";
    		log.info("Response header size should be greater than 1. Header size is :"+headers.size());
    	}
    	
    	if(dateValidation && conValidation && contentLenValidation && conetntTypeValidation && sizeValidation)
    		return "success";
    	else
    		return toReturn;
    }
	
	@Test(groups = { "SchemaValidation" }, dataProvider = "MobileSearchServiceDP_getFirstPageOfSearch_verifyResponseDataNodesUsingSchemaValidations" )
    public void MobileSearchService_getFirstPageOfSearch_verifyResponseDataNodesUsingSchemaValidations(String searchQuery, String rows, String returnDocs, 
    		String isFacet, String successRespCode)
    {
        RequestGenerator getFirstPageOfMobileSearchServiceReqGen = mobileSearchServiceHelper.invokeGetFirstPageOfSearch(searchQuery, rows, 
        		returnDocs, isFacet);
        String getFirstPageOfMobileSearchServiceResponse = getFirstPageOfMobileSearchServiceReqGen.respvalidate.returnresponseasstring();
        System.out.println("\nPrinting MobileSearchService getFirstPageOfSearch API response :\n\n"+getFirstPageOfMobileSearchServiceResponse+"\n");
		log.info("\nPrinting MobileSearchService getFirstPageOfSearch API response :\n\n"+getFirstPageOfMobileSearchServiceResponse+"\n");
		 
		MyntAssert.assertEquals("MobileSearchService getFirstPageOfSearch API is not working",  Integer.parseInt(successRespCode), 
				getFirstPageOfMobileSearchServiceReqGen.response.getStatus());
        
		try {
            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/mobilesearchservice_getfirstpageofsearch-schema.txt");
            List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(getFirstPageOfMobileSearchServiceResponse, jsonschema);
            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in MobileSearchService getFirstPageOfSearch API response", 
            		CollectionUtils.isEmpty(missingNodeList));
        } catch (Exception e) {
            e.printStackTrace();
        }
		
        MyntAssert.setJsonResponse(getFirstPageOfMobileSearchServiceResponse);
		MyntAssert.setExpectedAndActualForMyntListen(true);
    }
	
	@Test(groups={ "SchemaValidation" }, dataProvider="MobileSearchServiceDP_getNextPageOfSearch_verifyResponseDataNodesUsingSchemaValidations")
    public void MobileSearchService_getNextPageOfSearch_verifyResponseDataNodesUsingSchemaValidations(String searchQuery, String firstRows, String returnDocs, 
    		String isFacet, String action, String nextRows, String successRespCode)
    {
		// invoke getFirstPageOfSearch API
		RequestGenerator getFirstPageOfMobileSearchServiceReqGen = mobileSearchServiceHelper.invokeGetFirstPageOfSearch(searchQuery, firstRows, 
				returnDocs, isFacet);
		String getFirstPageOfMobileSearchServiceResponse = getFirstPageOfMobileSearchServiceReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting MobileSearchService getFirstPageOfSearch API response :\n\n"+getFirstPageOfMobileSearchServiceResponse+"\n");
		log.info("\nPrinting MobileSearchService getFirstPageOfSearch API response :\n\n"+getFirstPageOfMobileSearchServiceResponse+"\n");
			
		MyntAssert.assertEquals("MobileSearchService getFirstPageOfSearch API is not working",  Integer.parseInt(successRespCode), 
				getFirstPageOfMobileSearchServiceReqGen.response.getStatus());
		
		String requestObj = JsonPath.read(getFirstPageOfMobileSearchServiceResponse, "$.data.requestObj").toString();	
		
		// invoke getNextPageOfSearch API with requestObject
		RequestGenerator getNextPageOfMobileSearchServiceReqGen = mobileSearchServiceHelper.invokeGetNextPageOfSearch(action, nextRows, requestObj); 
		String getNextPageOfMobileSearchServiceResponse = getNextPageOfMobileSearchServiceReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting MobileSearchService getNextPageOfSearch API response :\n\n"+getNextPageOfMobileSearchServiceResponse+"\n");
		log.info("\nPrinting MobileSearchService getNextPageOfSearch API response :\n\n"+getNextPageOfMobileSearchServiceResponse+"\n");
		
		MyntAssert.assertEquals("MobileSearchService getNextPageOfSearch API is not working",  Integer.parseInt(successRespCode), 
				getNextPageOfMobileSearchServiceReqGen.response.getStatus());
		
		try {
            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/mobilesearchservice_getnextpageofsearch-schema.txt");
            List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(getFirstPageOfMobileSearchServiceResponse, jsonschema);
            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in MobileSearchService getNextPageOfSearch API response", 
            		CollectionUtils.isEmpty(missingNodeList));
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        MyntAssert.setJsonResponse(getNextPageOfMobileSearchServiceResponse);
		MyntAssert.setExpectedAndActualForMyntListen(true);
		
    }
	
	@Test(groups = { "SchemaValidation" }, dataProvider = "MobileSearchServiceDP_getSortedMobileSearch_verifyResponseDataNodesUsingSchemaValidations" )
    public void MobileSearchService_getSortedMobileSearch_verifyResponseDataNodesUsingSchemaValidations(String searchQuery, String rows, String returnDocs, 
    		String isFacet, String sortByParam, String successRespCode)
    {
		// invoke getFirstPageOfSearch API
		RequestGenerator getFirstPageOfMobileSearchServiceReqGen = mobileSearchServiceHelper.invokeGetFirstPageOfSearch(searchQuery, rows, 
				returnDocs, isFacet);
		String getFirstPageOfMobileSearchServiceResponse = getFirstPageOfMobileSearchServiceReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting MobileSearchService getFirstPageOfSearch API response :\n\n"+getFirstPageOfMobileSearchServiceResponse+"\n");
		log.info("\nPrinting MobileSearchService getFirstPageOfSearch API response :\n\n"+getFirstPageOfMobileSearchServiceResponse+"\n");
			
		MyntAssert.assertEquals("MobileSearchService getFirstPageOfSearch API is not working",  Integer.parseInt(successRespCode), 
				getFirstPageOfMobileSearchServiceReqGen.response.getStatus());
		
		String requestObj = JsonPath.read(getFirstPageOfMobileSearchServiceResponse, "$.data.requestObj").toString();
		
		// invoke g API with requestObject
		RequestGenerator getSortedMobileSearchServiceReqGen = mobileSearchServiceHelper.invokeGetSortedMobileSearch(sortByParam, requestObj);
		String getSortedMobileSearchServiceResponse = getSortedMobileSearchServiceReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting MobileSearchService getSortedMobileSearch API response :\n\n"+getSortedMobileSearchServiceResponse+"\n");
		log.info("\nPrinting MobileSearchService getSortedMobileSearch API response :\n\n"+getSortedMobileSearchServiceResponse+"\n");
			
		MyntAssert.assertEquals("MobileSearchService getSortedMobileSearch API is not working",  Integer.parseInt(successRespCode), 
				getSortedMobileSearchServiceReqGen.response.getStatus());
		
		try {
            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/mobilesearchservice_getsortedmobilesearch-schema.txt");
            List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(getSortedMobileSearchServiceResponse, jsonschema);
            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in MobileSearchService getSortedMobileSearch API response", 
            		CollectionUtils.isEmpty(missingNodeList));
        } catch (Exception e) {
            e.printStackTrace();
        }
		
		MyntAssert.setJsonResponse(getSortedMobileSearchServiceResponse);
		MyntAssert.setExpectedAndActualForMyntListen(true);
		
    }
	
	@Test(groups = { "SchemaValidation" }, dataProvider = "MobileSearchServiceDP_searchWithPresetFilters_verifyResponseDataNodesUsingSchemaValidations" )
    public void MobileSearchService_searchWithPresetFilters_verifyResponseDataNodesUsingSchemaValidations(String searchQuery, String rows, String returnDocs, 
    		String isFacet, String filters, String successRespCode)
    {
		RequestGenerator searchWithPresetFiltersServiceReqGen = mobileSearchServiceHelper.invokeSearchWithPresetFilters(searchQuery, rows, 
	       		returnDocs, isFacet, filters);
	    String searchWithPresetFiltersServiceResponse = searchWithPresetFiltersServiceReqGen.respvalidate.returnresponseasstring();
	    System.out.println("\nPrinting MobileSearchService searchWithPresetFilters API response :\n\n"+searchWithPresetFiltersServiceResponse+"\n");
		log.info("\nPrinting MobileSearchService searchWithPresetFilters API response :\n\n"+searchWithPresetFiltersServiceResponse+"\n");
			 
		MyntAssert.assertEquals("MobileSearchService searchWithPresetFilters API is not working",  Integer.parseInt(successRespCode), 
				searchWithPresetFiltersServiceReqGen.response.getStatus());
        
        try {
            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/mobilesearchservice_searchwithpresetfilters-schema.txt");
            List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(searchWithPresetFiltersServiceResponse, jsonschema);
            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in MobileSearchService searchWithPresetFilters API response", 
            		CollectionUtils.isEmpty(missingNodeList));
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        MyntAssert.setJsonResponse(searchWithPresetFiltersServiceResponse);
		MyntAssert.setExpectedAndActualForMyntListen(true);
    }
	
	@Test(groups = { "SchemaValidation" }, dataProvider = "MobileSearchServiceDP_searchAndApplySingleFilter_verifyResponseDataNodesUsingSchemaValidations" )
    public void MobileSearchService_searchAndApplySingleFilter_verifyResponseDataNodesUsingSchemaValidations(String searchQuery, String rows, String returnDocs, 
    		String isFacet, String singleFilters, String successRespCode)
    {
		// invoke getFirstPageOfSearch API
		RequestGenerator getFirstPageOfMobileSearchServiceReqGen = mobileSearchServiceHelper.invokeGetFirstPageOfSearch(searchQuery, rows, 
				returnDocs, isFacet);
		String getFirstPageOfMobileSearchServiceResponse = getFirstPageOfMobileSearchServiceReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting MobileSearchService getFirstPageOfSearch API response :\n\n"+getFirstPageOfMobileSearchServiceResponse+"\n");
		log.info("\nPrinting MobileSearchService getFirstPageOfSearch API response :\n\n"+getFirstPageOfMobileSearchServiceResponse+"\n");
			
		MyntAssert.assertEquals("MobileSearchService getFirstPageOfSearch API is not working",  Integer.parseInt(successRespCode), 
				getFirstPageOfMobileSearchServiceReqGen.response.getStatus());
		
		String requestObj = JsonPath.read(getFirstPageOfMobileSearchServiceResponse, "$.data.requestObj").toString();
		
		// invoke searchAndApplyFilters
        RequestGenerator searchAndApplySingleFilterReqGen = mobileSearchServiceHelper.invokeSearchAndApplyFilters(singleFilters, requestObj);
        String searchAndApplySingleFilterResponse = searchAndApplySingleFilterReqGen.respvalidate.returnresponseasstring();
        System.out.println("\nPrinting MobileSearchService searchAndApplySingleFilter API response :\n\n"+searchAndApplySingleFilterResponse+"\n");
		log.info("\nPrinting MobileSearchService searchAndApplySingleFilter API response :\n\n"+searchAndApplySingleFilterResponse+"\n");
		 
		MyntAssert.assertEquals("MobileSearchService searchAndApplySingleFilter API is not working",  Integer.parseInt(successRespCode), 
				searchAndApplySingleFilterReqGen.response.getStatus());
		
		try {
            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/mobilesearchservice_searchwithpresetfilters-schema.txt");
            List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(searchAndApplySingleFilterResponse, jsonschema);
            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in MobileSearchService searchAndApplySingleFilter API response", 
            		CollectionUtils.isEmpty(missingNodeList));
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        MyntAssert.setJsonResponse(searchAndApplySingleFilterResponse);
		MyntAssert.setExpectedAndActualForMyntListen(true);
    }
	
	@Test(groups = { "SchemaValidation" }, dataProvider = "MobileSearchServiceDP_searchAndApplyDoubleFilters_verifyResponseDataNodesUsingSchemaValidations" )
    public void MobileSearchService_searchAndApplyDoubleFilters_verifyResponseDataNodesUsingSchemaValidations(String searchQuery, String rows, String returnDocs, 
    		String isFacet, String doubleFilters, String successRespCode)
    {
		// invoke getFirstPageOfSearch API
		RequestGenerator getFirstPageOfMobileSearchServiceReqGen = mobileSearchServiceHelper.invokeGetFirstPageOfSearch(searchQuery, rows, 
				returnDocs, isFacet);
		String getFirstPageOfMobileSearchServiceResponse = getFirstPageOfMobileSearchServiceReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting MobileSearchService getFirstPageOfSearch API response :\n\n"+getFirstPageOfMobileSearchServiceResponse+"\n");
		log.info("\nPrinting MobileSearchService getFirstPageOfSearch API response :\n\n"+getFirstPageOfMobileSearchServiceResponse+"\n");
			
		MyntAssert.assertEquals("MobileSearchService getFirstPageOfSearch API is not working",  Integer.parseInt(successRespCode), 
				getFirstPageOfMobileSearchServiceReqGen.response.getStatus());
		
		String requestObj = JsonPath.read(getFirstPageOfMobileSearchServiceResponse, "$.data.requestObj").toString();
		
		// invoke searchAndApplyFilters
        RequestGenerator searchAndApplyDoubleFilterReqGen = mobileSearchServiceHelper.invokeSearchAndApplyFilters(doubleFilters, requestObj);
        String searchAndApplyDoubleFiltersResponse = searchAndApplyDoubleFilterReqGen.respvalidate.returnresponseasstring();
        System.out.println("\nPrinting MobileSearchService searchAndApplyDoubleFilters API response :\n\n"+searchAndApplyDoubleFiltersResponse+"\n");
		log.info("\nPrinting MobileSearchService searchAndApplyDoubleFilters API response :\n\n"+searchAndApplyDoubleFiltersResponse+"\n");
		 
		MyntAssert.assertEquals("MobileSearchService searchAndApplyDoubleFilters API is not working",  Integer.parseInt(successRespCode), 
				searchAndApplyDoubleFilterReqGen.response.getStatus());
		
		try {
            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/mobilesearchservice_searchandapplydoublefilters-schema.txt");
            List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(searchAndApplyDoubleFiltersResponse, jsonschema);
            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in MobileSearchService searchAndApplyDoubleFilters API response", 
            		CollectionUtils.isEmpty(missingNodeList));
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        MyntAssert.setJsonResponse(searchAndApplyDoubleFiltersResponse);
		MyntAssert.setExpectedAndActualForMyntListen(true);
    }
	
	@Test(groups = { "SchemaValidation" }, dataProvider = "MobileSearchServiceDP_searchAndApplyAllFilters_verifyResponseDataNodesUsingSchemaValidations" )
    public void MobileSearchService_searchAndApplyAllFilters_verifyResponseDataNodesUsingSchemaValidations(String searchQuery, String rows, String returnDocs, 
    		String isFacet, String allFilters, String successRespCode)
    {
		// invoke getFirstPageOfSearch API
		RequestGenerator getFirstPageOfMobileSearchServiceReqGen = mobileSearchServiceHelper.invokeGetFirstPageOfSearch(searchQuery, rows, 
				returnDocs, isFacet);
		String getFirstPageOfMobileSearchServiceResponse = getFirstPageOfMobileSearchServiceReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting MobileSearchService getFirstPageOfSearch API response :\n\n"+getFirstPageOfMobileSearchServiceResponse+"\n");
		log.info("\nPrinting MobileSearchService getFirstPageOfSearch API response :\n\n"+getFirstPageOfMobileSearchServiceResponse+"\n");
			
		MyntAssert.assertEquals("MobileSearchService getFirstPageOfSearch API is not working",  Integer.parseInt(successRespCode), 
				getFirstPageOfMobileSearchServiceReqGen.response.getStatus());
		
		String requestObj = JsonPath.read(getFirstPageOfMobileSearchServiceResponse, "$.data.requestObj").toString();
		
		// invoke searchAndApplyFilters
        RequestGenerator searchAndApplyAllFiltersReqGen = mobileSearchServiceHelper.invokeSearchAndApplyFilters(allFilters, requestObj);
        String searchAndApplyAllFiltersResponse = searchAndApplyAllFiltersReqGen.respvalidate.returnresponseasstring();
        System.out.println("\nPrinting MobileSearchService searchAndApplyAllFilters API response :\n\n"+searchAndApplyAllFiltersResponse+"\n");
		log.info("\nPrinting MobileSearchService searchAndApplyAllFilters API response :\n\n"+searchAndApplyAllFiltersResponse+"\n");
		 
		MyntAssert.assertEquals("MobileSearchService searchAndApplyAllFilters API is not working",  Integer.parseInt(successRespCode), 
				searchAndApplyAllFiltersReqGen.response.getStatus());
		
		try {
            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/mobilesearchservice_searchandapplyallfilters-schema.txt");
            List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(searchAndApplyAllFiltersResponse, jsonschema);
            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in MobileSearchService searchAndApplyAllFilters API response", 
            		CollectionUtils.isEmpty(missingNodeList));
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        MyntAssert.setJsonResponse(searchAndApplyAllFiltersResponse);
		MyntAssert.setExpectedAndActualForMyntListen(true);
    }
	
	@Test(groups = { "SchemaValidation" }, dataProvider = "MobileSearchServiceDP_parameterizedMobileSearchDataQuery_verifyResponseDataNodesUsingSchemaValidations")
	public void MobileSearchService_parameterizedMobileSearchDataQuery_verifyResponseDataNodesUsingSchemaValidations(String paramMobileSearchDataQuery, 
			String successRespCode) 
	{
		RequestGenerator parameterizedMobileSearchDataQueryReqGen = mobileSearchServiceHelper.invokeParameterizedMobileSearchDataQuery(paramMobileSearchDataQuery);
	    String parameterizedMobileSearchDataQueryResponse = parameterizedMobileSearchDataQueryReqGen.respvalidate.returnresponseasstring();
	    System.out.println("\nPrinting MobileSearchService parameterizedMobileSearchDataQuery API response :\n\n"+parameterizedMobileSearchDataQueryResponse+"\n");
		log.info("\nPrinting MobileSearchService parameterizedMobileSearchDataQuery API response :\n\n"+parameterizedMobileSearchDataQueryResponse+"\n");
			
		MyntAssert.assertEquals("MobileSearchService parameterizedMobileSearchDataQuery API is not working",  Integer.parseInt(successRespCode), 
				parameterizedMobileSearchDataQueryReqGen.response.getStatus());
		
		try {
            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/mobilesearchservice_parameterizedmobilesearchdataquery-schema.txt");
            List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(parameterizedMobileSearchDataQueryResponse, jsonschema);
            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in MobileSearchService parameterizedMobileSearchDataQuery API response", 
            		CollectionUtils.isEmpty(missingNodeList));
        } catch (Exception e) {
            e.printStackTrace();
        }
		
		MyntAssert.setJsonResponse(parameterizedMobileSearchDataQueryResponse);
		MyntAssert.setExpectedAndActualForMyntListen(true);
	}
	
	@Test(groups = { "SchemaValidation" }, dataProvider = "MobileSearchServiceDP_parameterizedMobileSearchDataQueryWithOneFilter_verifyResponseDataNodesUsingSchemaValidations")
	public void MobileSearchService_parameterizedMobileSearchDataQueryWithOneFilter_verifyResponseDataNodesUsingSchemaValidations(String paramMobileSearchDataQuery, 
			String paramArtifact, String paramValue, String successRespCode)
	{
		RequestGenerator parameterizedMobileSearchDataQueryWithOneFilterReqGen = mobileSearchServiceHelper.invokeParameterizedMobileSearchDataQueryWithOneFilter(
				paramMobileSearchDataQuery, paramArtifact, paramValue);
	    String parameterizedMobileSearchDataQueryWithOneFilterResponse = parameterizedMobileSearchDataQueryWithOneFilterReqGen.respvalidate.returnresponseasstring();
	    System.out.println("\nPrinting MobileSearchService parameterizedMobileSearchDataQueryWithOneFilter API response :\n\n"+
	    		parameterizedMobileSearchDataQueryWithOneFilterResponse+"\n");
		log.info("\nPrinting MobileSearchService parameterizedMobileSearchDataQueryWithOneFilter API response :\n\n"+
	    		parameterizedMobileSearchDataQueryWithOneFilterResponse+"\n");
			
		MyntAssert.assertEquals("MobileSearchService parameterizedMobileSearchDataQueryWithOneFilter API is not working",  Integer.parseInt(successRespCode), 
				parameterizedMobileSearchDataQueryWithOneFilterReqGen.response.getStatus());
		
		try {
            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/mobilesearchservice_parameterizedmobilesearchdataquerywithonefilter-schema.txt");
            List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(parameterizedMobileSearchDataQueryWithOneFilterResponse, jsonschema);
            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in MobileSearchService parameterizedMobileSearchDataQueryWithOneFilter API response", 
            		CollectionUtils.isEmpty(missingNodeList));
        } catch (Exception e) {
            e.printStackTrace();
        }
		
		MyntAssert.setJsonResponse(parameterizedMobileSearchDataQueryWithOneFilterResponse);
		MyntAssert.setExpectedAndActualForMyntListen(true);
	}
	
	@Test(groups = { "SchemaValidation" }, dataProvider = "MobileSearchServiceDP_parameterizedMobileSearchDataQueryWithTwoFilters_verifyResponseDataNodesUsingSchemaValidations")
	public void MobileSearchService_parameterizedMobileSearchDataQueryWithTwoFilters_verifyResponseDataNodesUsingSchemaValidations(String paramMobileSearchDataQuery, String paramArtifact1,
			String paramValue1, String paramArtifact2, String paramValue2, String successRespCode) 
	{
		RequestGenerator parameterizedMobileSearchDataQueryWithTwoFiltersReqGen = mobileSearchServiceHelper.invokeParameterizedMobileSearchDataQueryWithTwoFilters(
				paramMobileSearchDataQuery, paramArtifact1, paramValue1, paramArtifact2, paramValue2);
		
	    String parameterizedMobileSearchDataQueryWithTwoFiltersResponse = parameterizedMobileSearchDataQueryWithTwoFiltersReqGen.respvalidate.returnresponseasstring();
	    System.out.println("\nPrinting MobileSearchService parameterizedMobileSearchDataQueryWithTwoFilters API response :\n\n"+
	    		parameterizedMobileSearchDataQueryWithTwoFiltersResponse+"\n");
		log.info("\nPrinting MobileSearchService parameterizedMobileSearchDataQueryWithTwoFilters API response :\n\n"+
	    		parameterizedMobileSearchDataQueryWithTwoFiltersResponse+"\n");
			
		MyntAssert.assertEquals("MobileSearchService parameterizedMobileSearchDataQueryWithTwoFilters API is not working",  Integer.parseInt(successRespCode), 
				parameterizedMobileSearchDataQueryWithTwoFiltersReqGen.response.getStatus());
		
		try {
            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/mobilesearchservice_parameterizedmobilesearchdataquerywithtwofilters-schema.txt");
            List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(parameterizedMobileSearchDataQueryWithTwoFiltersResponse, jsonschema);
            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in MobileSearchService parameterizedMobileSearchDataQueryWithTwoFilters API response", 
            		CollectionUtils.isEmpty(missingNodeList));
        } catch (Exception e) {
            e.printStackTrace();
        }
		
		MyntAssert.setJsonResponse(parameterizedMobileSearchDataQueryWithTwoFiltersResponse);
		MyntAssert.setExpectedAndActualForMyntListen(true);
	}
	
	@Test(groups = { "SchemaValidation" }, dataProvider = "MobileSearchServiceDP_parameterizedMobileSearchDataQueryWithAllFilters_verifyResponseDataNodesUsingSchemaValidations")
	public void MobileSearchService_parameterizedMobileSearchDataQueryWithAllFilters_verifyResponseDataNodesUsingSchemaValidations(String paramMobileSearchDataQuery,
			String paramArtifact1, String paramValue1, String paramArtifact2, String paramValue2, String paramArtifact3, String paramValue3, String paramArtifact4, 
			String paramValue4, String paramArtifact5, String paramValue5, String successRespCode) 
	{
		RequestGenerator parameterizedMobileSearchDataQueryWithAllFiltersReqGen = mobileSearchServiceHelper.invokeParameterizedMobileSearchDataQueryWithAllFilters(
				paramMobileSearchDataQuery, paramArtifact1, paramValue1, paramArtifact2, paramValue2, paramArtifact3, paramValue3, paramArtifact4, paramValue4, 
				paramArtifact5, paramValue5);
	    String parameterizedMobileSearchDataQueryWithAllFiltersResponse = parameterizedMobileSearchDataQueryWithAllFiltersReqGen.respvalidate.returnresponseasstring();
	    System.out.println("\nPrinting MobileSearchService parameterizedMobileSearchDataQueryWithAllFilters API response :\n\n"+
	    		parameterizedMobileSearchDataQueryWithAllFiltersResponse+"\n");
		log.info("\nPrinting MobileSearchService parameterizedMobileSearchDataQueryWithAllFilters API response :\n\n"+
	    		parameterizedMobileSearchDataQueryWithAllFiltersResponse+"\n");
			
		MyntAssert.assertEquals("MobileSearchService parameterizedMobileSearchDataQueryWithAllFilters API is not working",  Integer.parseInt(successRespCode), 
				parameterizedMobileSearchDataQueryWithAllFiltersReqGen.response.getStatus());
		
		try {
            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/mobilesearchservice_parameterizedmobilesearchdataquerywithallfilters-schema.txt");
            List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(parameterizedMobileSearchDataQueryWithAllFiltersResponse, jsonschema);
            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in MobileSearchService parameterizedMobileSearchDataQueryWithAllFilters API response", 
            		CollectionUtils.isEmpty(missingNodeList));
        } catch (Exception e) {
            e.printStackTrace();
        }
		
		MyntAssert.setJsonResponse(parameterizedMobileSearchDataQueryWithAllFiltersResponse);
		MyntAssert.setExpectedAndActualForMyntListen(true);
	}
	
	@Test(groups = { "SchemaValidation" }, dataProvider = "MobileSearchServiceDP_parameterizedMobileSearchDataQueryWithSortBy_verifyResponseDataNodesUsingSchemaValidations")
	public void MobileSearchService_parameterizedMobileSearchDataQueryWithSortBy_verifyResponseDataNodesUsingSchemaValidations(String paramMobileSearchDataQuery,
			String paramSortBy, String successRespCode) 
	{
		RequestGenerator parameterizedMobileSearchDataQueryWithSortByReqGen = mobileSearchServiceHelper.invokeParameterizedMobileSearchDataQueryWithSortBy(
				paramMobileSearchDataQuery, paramSortBy);
	    String parameterizedMobileSearchDataQueryWithSortByResponse = parameterizedMobileSearchDataQueryWithSortByReqGen.respvalidate.returnresponseasstring();
	    System.out.println("\nPrinting MobileSearchService parameterizedMobileSearchDataQueryWithSortBy API response :\n\n"+
	    		parameterizedMobileSearchDataQueryWithSortByResponse+"\n");
		log.info("\nPrinting MobileSearchService parameterizedMobileSearchDataQueryWithSortBy API response :\n\n"+
	    		parameterizedMobileSearchDataQueryWithSortByResponse+"\n");
			
		MyntAssert.assertEquals("MobileSearchService parameterizedMobileSearchDataQueryWithSortBy API is not working",  Integer.parseInt(successRespCode), 
				parameterizedMobileSearchDataQueryWithSortByReqGen.response.getStatus());
		
		try {
            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/mobilesearchservice_parameterizedmobilesearchdataquerywithsortby-schema.txt");
            List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(parameterizedMobileSearchDataQueryWithSortByResponse, jsonschema);
            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in MobileSearchService parameterizedMobileSearchDataQueryWithSortBy API response", 
            		CollectionUtils.isEmpty(missingNodeList));
        } catch (Exception e) {
            e.printStackTrace();
        }
		
		MyntAssert.setJsonResponse(parameterizedMobileSearchDataQueryWithSortByResponse);
		MyntAssert.setExpectedAndActualForMyntListen(true);
	
	}
	
}