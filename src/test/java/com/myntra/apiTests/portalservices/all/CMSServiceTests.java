package com.myntra.apiTests.portalservices.all;

import argo.saj.InvalidSyntaxException;

import com.myntra.apiTests.dataproviders.CMSServiceDP;
import com.myntra.apiTests.portalservices.cmsservice.CMSServiceHelper;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.Toolbox;

import com.myntra.apiTests.common.APINAME;
import com.myntra.apiTests.common.Myntra;
import com.myntra.apiTests.common.ServiceType;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.PayloadType;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author shankara.c
 *
 */
public class CMSServiceTests extends CMSServiceDP
{
	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(CMSServiceTests.class);
	APIUtilities apiUtil = new APIUtilities();
	CMSServiceHelper cmsServiceHelper = new CMSServiceHelper();

	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression", "ExhaustiveRegression"}, dataProvider = "getContentByIdDataProvider")
	public void CMSService_getContentById(String id)
	{
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_CMSSERVICE, APINAME.GETCONTENTBYID,
				init.Configurations);
		service.URL = apiUtil.prepareparameterizedURL(service.URL, id);
		System.out.println(service.URL);
		HashMap getParam = new HashMap();
		getParam.put("Authorization",
				"Basic YXBpYWRtaW46bTFudHJhUjBja2V0MTMhIw==");
		RequestGenerator req = new RequestGenerator(service, getParam);
		String jsonRes = req.respvalidate.returnresponseasstring();
		System.out.println(jsonRes);
		AssertJUnit.assertEquals(200, req.response.getStatus());
		AssertJUnit.assertTrue("Checking respose", req.respvalidate.DoesNodesExists(commonResponse()));
		AssertJUnit.assertTrue("Validation of statusMessage from response", req.respvalidate.GetNodeValue("status.statusMessage", jsonRes).equalsIgnoreCase("\"Content retrieved successfully\""));

	}
	
	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression", "ExhaustiveRegression"}, dataProvider = "getContentByIdNagativeDataProvider")
	public void CMSService_getContentByIdNegative(String id)
	{
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_CMSSERVICE, APINAME.GETCONTENTBYID,
				init.Configurations);
		service.URL = apiUtil.prepareparameterizedURL(service.URL, id);
		System.out.println(service.URL);
		HashMap getParam = new HashMap();
		getParam.put("Authorization",
				"Basic YXBpYWRtaW46bTFudHJhUjBja2V0MTMhIw==");
		RequestGenerator req = new RequestGenerator(service, getParam);
		String jsonRes = req.respvalidate.returnresponseasstring();
		System.out.println(jsonRes);
		AssertJUnit.assertEquals(404,req.response.getStatus());
		//AssertJUnit.assertTrue("Checking respose", req.respvalidate.DoesNodesExists(commonResponse()));
		//AssertJUnit.assertTrue("Validation of statusMessage from response", req.respvalidate.GetNodeValue("status.statusMessage", jsonRes).equalsIgnoreCase("\"Content retrieved successfully\""));

	}

	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression"}, dataProvider = "filteredContentSearchDataProvider")
	public void CMSService_filteredContentSearch(String queryParams)
	{
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_CMSSERVICE, APINAME.FILTEREDCONTENTSEARCH,
				init.Configurations, queryParams);
		service.URL = apiUtil.prepareparameterizedURL(service.URL,
		queryParams);
		System.out.println(service.URL);
		HashMap getParam = new HashMap();
		getParam.put("Authorization",
				"Basic YXBpYWRtaW46bTFudHJhUjBja2V0MTMhIw==");
		RequestGenerator req = new RequestGenerator(service, getParam);
		String jsonRes = req.respvalidate.returnresponseasstring();
		System.out.println(jsonRes);
		AssertJUnit.assertEquals(200, req.response.getStatus());
		AssertJUnit.assertTrue("Checking respose", req.respvalidate.DoesNodesExists(commonResponse()));
		AssertJUnit.assertTrue("Validation of statusMessage from response", req.respvalidate.GetNodeValue("status.statusMessage", jsonRes).equalsIgnoreCase("\"Content retrieved successfully\""));
		/*
		if(queryParams.startsWith("contentIds=")){
			AssertJUnit.assertTrue("Validation of total count from response", req.respvalidate.GetNodeValue("status.totalCount", jsonRes).equalsIgnoreCase(queryParams.split(",").length+""));
		}
		*/
	}

	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" }, dataProvider = "relatedContentSearchDataProvider")
	public void CMSService_relatedContentSearch(String type, String relation, String product)
	{
		String[] queryParams = new String[]{type, relation, product};
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_CMSSERVICE, APINAME.RELATEDCONTENTSEARCH,
				init.Configurations, PayloadType.JSON, queryParams,
				PayloadType.JSON);
		// service.URL = apiUtil.prepareparameterizedURL(service.URL,
		// queryParams);
		System.out.println(service.URL);
		HashMap getParam = new HashMap();
		getParam.put("Authorization",
				"Basic YXBpYWRtaW46bTFudHJhUjBja2V0MTMhIw==");
		RequestGenerator req = new RequestGenerator(service, getParam);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());
	}

	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" }, dataProvider = "clearContentCacheDataProvider")
	public void CMSService_clearContentCache(String queryParams)
	{
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_CMSSERVICE, APINAME.CLEARCONTENTCACHE,
				init.Configurations);
		service.URL = apiUtil.prepareparameterizedURL(service.URL, queryParams);
		System.out.println(service.URL);
		HashMap getParam = new HashMap();
		getParam.put("Authorization",
				"Basic YXBpYWRtaW46bTFudHJhUjBja2V0MTMhIw==");
		RequestGenerator req = new RequestGenerator(service, getParam);
		String jsonRes = req.respvalidate.returnresponseasstring();
		System.out.println(jsonRes);
		AssertJUnit.assertEquals(200, req.response.getStatus());
		AssertJUnit.assertTrue("Checking respose", req.respvalidate.DoesNodesExists(commonResponse()));
		AssertJUnit.assertTrue("Validation of statusMessage from response", req.respvalidate.GetNodeValue("status.statusMessage", jsonRes).equalsIgnoreCase("\"Successfully cleared content find by id cache for contentId : "+queryParams+"\""));
	}

	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" })
	public void CMSService_clearAllContentCache()
	{
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_CMSSERVICE, APINAME.CLEARALLCONTENTCACHE,
				init.Configurations);
		System.out.println(service.URL);
		HashMap getParam = new HashMap();
		getParam.put("Authorization",
				"Basic YXBpYWRtaW46bTFudHJhUjBja2V0MTMhIw==");
		RequestGenerator req = new RequestGenerator(service, getParam);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());
	}

	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" }, dataProvider = "clearRelatedContentCacheDataProvider")
	public void CMSService_clearRelatedContentCache(String product,String type, String relation)
	{
		String[] queryParams = new String[]{product, type, relation};
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_CMSSERVICE,
				APINAME.CLEARRELATEDCONTENTCACHE, init.Configurations,
				PayloadType.JSON, queryParams, PayloadType.JSON);
		System.out.println(service.URL);
		HashMap getParam = new HashMap();
		getParam.put("Authorization",
				"Basic YXBpYWRtaW46bTFudHJhUjBja2V0MTMhIw==");
		RequestGenerator req = new RequestGenerator(service, getParam);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());
	}

	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" })
	public void CMSService_clearAllRelatedContentCache()
	{
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_CMSSERVICE,
				APINAME.CLEARALLRELATEDCONTENTCACHE, init.Configurations);
		System.out.println(service.URL);
		HashMap getParam = new HashMap();
		getParam.put("Authorization",
				"Basic YXBpYWRtaW46bTFudHJhUjBja2V0MTMhIw==");
		RequestGenerator req = new RequestGenerator(service, getParam);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());
	}
	
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" }, dataProvider="createContentUsingBodyDataProvider")
	public void CMSService_createContentUsingBody(String contentId, String owner, String type, String typeId, String tags)
	{
		String[] payLoadParams = new String[]{contentId, owner, type, typeId, returnCommanSepTags(tags)};
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_CMSSERVICE,
				APINAME.CREATECONTENTUSINGBODY, init.Configurations, payLoadParams);
		System.out.println(service.URL);
		HashMap getParam = new HashMap();
		getParam.put("Authorization",
				"Basic YXBpYWRtaW46bTFudHJhUjBja2V0MTMhIw==");
		RequestGenerator req = new RequestGenerator(service, getParam);
		System.out.println("Payload :"+service.Payload);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());
	}
	
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" }, dataProvider="updateContentUsingBodyDataProvider")
	public void CMSService_updateContentUsingBody(String contentId, String owner, String type, String typeId, String tags)
	{
		String[] payLoadParams = new String[]{contentId, owner, type, typeId, returnCommanSepTags(tags)};
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_CMSSERVICE,
				APINAME.UPDATECONTENTUSINGBODY, init.Configurations, payLoadParams);
		service.URL = apiUtil.prepareparameterizedURL(service.URL, contentId);
		System.out.println(service.URL);
		HashMap getParam = new HashMap();
		getParam.put("Authorization",
				"Basic YXBpYWRtaW46bTFudHJhUjBja2V0MTMhIw==");
		RequestGenerator req = new RequestGenerator(service, getParam);
		System.out.println("Payload :"+service.Payload);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());
	}
	
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" }, dataProvider="getProductDetailsFromCmsDataProvider")
	public void CMSService_getProductDetailsFromCms(String prodId)
	{
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_CMSSERVICE,
				APINAME.GETPRODUCTDETAILSFROMCMS, init.Configurations, prodId);
		service.URL = apiUtil.prepareparameterizedURL(service.URL, prodId);
		System.out.println(service.URL);
		HashMap getParam = new HashMap();
		getParam.put("Authorization",
				"Basic YXBpYWRtaW46bTFudHJhUjBja2V0MTMhIw==");
		RequestGenerator req = new RequestGenerator(service, getParam);
		System.out.println("Payload :"+service.Payload);
		String jsonRes = req.respvalidate.returnresponseasstring();
		System.out.println(jsonRes);
		AssertJUnit.assertEquals(200, req.response.getStatus());
		AssertJUnit.assertTrue("Checking respose", req.respvalidate.DoesNodesExists(commonResponse()));
		AssertJUnit.assertTrue("Validation of statusMessage from response", req.respvalidate.GetNodeValue("status.statusMessage", jsonRes).equalsIgnoreCase("\"Product retrieved successfully\""));
	}
	
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" }, dataProvider="getLookBookByIdCmsDataProvider")
	public void CMSService_getLookBookByIdCms(String Id)
	{
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_CMSSERVICE,
				APINAME.GETLOOKBOOKBYIDCMS, init.Configurations, Id);
		service.URL = apiUtil.prepareparameterizedURL(service.URL, Id);
		System.out.println(service.URL);
		HashMap getParam = new HashMap();
		getParam.put("Authorization",
				"Basic YXBpYWRtaW46bTFudHJhUjBja2V0MTMhIw==");
		RequestGenerator req = new RequestGenerator(service, getParam);
		System.out.println("Payload :"+service.Payload);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());
	}
	
	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression", "ExhaustiveRegression"}, dataProvider = "getImage360ProductIDDataProvider")
	public void CMSService_getContentByIdandvalidateImage360andStyleVideo(String id,int validationcode,int nodecount) throws InvalidSyntaxException
	{
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_CATALOGSERVICE, APINAME.GETPRODUCTBYID,
				init.Configurations);
		service.URL = apiUtil.prepareparameterizedURL(service.URL, id);
		System.out.println(service.URL);
		HashMap getParam = new HashMap();
		getParam.put("Authorization",
				"Basic YXBpYWRtaW46bTFudHJhUjBja2V0MTMhIw==");
		RequestGenerator req = new RequestGenerator(service, getParam);
		String jsonRes = req.respvalidate.returnresponseasstring();
		System.out.println(jsonRes);
		if(id.equalsIgnoreCase("111794"))
		{
			int cntofImage360 =req.respvalidate.GetChildNodeCountUsingIndex("data.product360Albums", jsonRes);
			int cntofStyleVideos =req.respvalidate.GetChildNodeCountUsingIndex("data.productVideoAlbums", jsonRes);

			System.out.println(cntofImage360);
			Assert.assertEquals(nodecount, cntofImage360);
			Assert.assertEquals(nodecount, cntofStyleVideos);
		}
		Assert.assertEquals(req.response.getStatus(),validationcode);
		

	}
	
	private String returnCommanSepTags(String tags){
		String finalTagsStr = "";
		String[] splittedArray = tags.split(":");
		for(String tag : splittedArray){
			finalTagsStr += "\""+tag+"\",";
		}
		finalTagsStr = finalTagsStr.substring(0, finalTagsStr.length()-1);
		return finalTagsStr;
	}
	
	private List commonResponse(){
		List<String> checkResponse = new ArrayList();
		checkResponse.add("status.statusCode");
		checkResponse.add("status.statusMessage");
		checkResponse.add("status.statusType");
		checkResponse.add("status.totalCount");
		checkResponse.add("status.success");
		return checkResponse;
	}
	
	@Test(groups = { "SchemaValidation" }, dataProvider = "CMSServiceDP_getContentByStyleId_verifyResponseDataNodesUsingSchemaValidations")
	public void CMSService_getContentByStyleId_verifyResponseDataNodesUsingSchemaValidations(String styleId, String successRespCode)
	{
		RequestGenerator getContentByStyleIdReqGen = cmsServiceHelper.invokeGetContentById(styleId);
		String getContentByStyleIdResponse = getContentByStyleIdReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting CMSService getContentByStyleId API response :\n\n"+getContentByStyleIdResponse);
		log.info("\nPrinting CMSService getContentByStyleId API response :\n\n"+getContentByStyleIdResponse);
		
		AssertJUnit.assertEquals("CMSService getContentByStyleId API is not working", Integer.parseInt(successRespCode), getContentByStyleIdReqGen.response.getStatus());
		
		try {
            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/cmsservice-getcontentbystyleid-schema.txt");
            List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(getContentByStyleIdResponse, jsonschema);
            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in CMSService getContentByStyleId API response", CollectionUtils.isEmpty(missingNodeList));
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	@Test(groups = { "SchemaValidation" }, dataProvider = "CMSServiceDP_filteredContentSearch_verifyResponseDataNodesUsingSchemaValidations")
	public void CMSService_filteredContentSearch_verifyResponseDataNodesUsingSchemaValidations(String queryParams, String successRespCode)
	{
		RequestGenerator filteredContentSearchReqGen = cmsServiceHelper.invokeFilteredContentSearch(queryParams);
		String filteredContentSearchResponse = filteredContentSearchReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting CMSService filteredContentSearch API response :\n\n"+filteredContentSearchResponse);
		log.info("\nPrinting CMSService filteredContentSearch API response :\n\n"+filteredContentSearchResponse);
		
		AssertJUnit.assertEquals("CMSService filteredContentSearch API is not working", Integer.parseInt(successRespCode), filteredContentSearchReqGen.response.getStatus());
		
		try {
            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/cmsservice-filteredcontentsearch-schema.txt");
            List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(filteredContentSearchResponse, jsonschema);
            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in CMSService filteredContentSearch API response", CollectionUtils.isEmpty(missingNodeList));
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	@Test(groups = { "SchemaValidation" }, dataProvider = "CMSServiceDP_relatedContentSearch_verifyResponseDataNodesUsingSchemaValidations")
	public void CMSService_relatedContentSearch_verifyResponseDataNodesUsingSchemaValidations(String type, String relation, String product, String successRespCode)
	{
		RequestGenerator relatedContentSearchReqGen = cmsServiceHelper.invokeRelatedContentSearch(type, relation, product);
		String relatedContentSearchResponse = relatedContentSearchReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting CMSService relatedContentSearch API response :\n\n"+relatedContentSearchResponse);
		log.info("\nPrinting CMSService relatedContentSearch API response :\n\n"+relatedContentSearchResponse);
		
		AssertJUnit.assertEquals("CMSService relatedContentSearch API is not working", Integer.parseInt(successRespCode), relatedContentSearchReqGen.response.getStatus());
		
		try {
            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/cmsservice-relatedcontentsearch-schema.txt");
            List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(relatedContentSearchResponse, jsonschema);
            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in CMSService relatedContentSearch API response", CollectionUtils.isEmpty(missingNodeList));
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	@Test(groups = { "SchemaValidation" }, dataProvider = "CMSServiceDP_clearContentSearchCache_verifyResponseDataNodesUsingSchemaValidations")
	public void CMSService_clearContentSearchCache_verifyResponseDataNodesUsingSchemaValidations(String queryParams, String successRespCode)
	{
		RequestGenerator clearContentSearchCacheReqGen = cmsServiceHelper.invokeClearContentSearchCache(queryParams);
		String clearContentSearchCacheResponse = clearContentSearchCacheReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting CMSService clearContentSearchCache API response :\n\n"+clearContentSearchCacheResponse);
		log.info("\nPrinting CMSService clearContentSearchCache API response :\n\n"+clearContentSearchCacheResponse);
		
		AssertJUnit.assertEquals("CMSService clearContentSearchCache API is not working", Integer.parseInt(successRespCode), clearContentSearchCacheReqGen.response.getStatus());
		
		try {
            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/cmsservice-clearcontentsearchcache-schema.txt");
            List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(clearContentSearchCacheResponse, jsonschema);
            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in CMSService clearContentSearchCache API response", CollectionUtils.isEmpty(missingNodeList));
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

	@Test(groups = { "SchemaValidation" })
	public void CMSService_clearAllContentSearchCache_verifyResponseDataNodesUsingSchemaValidations()
	{
		RequestGenerator clearAllContentSearchCacheReqGen = cmsServiceHelper.invokeClearAllContentSearchCache();
		String clearAllContentSearchCacheResponse = clearAllContentSearchCacheReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting CMSService clearAllContentSearchCache API response :\n\n"+clearAllContentSearchCacheResponse);
		log.info("\nPrinting CMSService clearAllContentSearchCache API response :\n\n"+clearAllContentSearchCacheResponse);
		
		AssertJUnit.assertEquals("CMSService clearAllContentSearchCache API is not working", 200, clearAllContentSearchCacheReqGen.response.getStatus());
		
		try {
            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/cmsservice-clearallcontentsearchcache-schema.txt");
            List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(clearAllContentSearchCacheResponse, jsonschema);
            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in CMSService clearAllContentSearchCache API response", CollectionUtils.isEmpty(missingNodeList));
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	@Test(groups = { "SchemaValidation" }, dataProvider = "CMSServiceDP_clearRelatedContentSearchCache_verifyResponseDataNodesUsingSchemaValidations")
	public void CMSService_clearRelatedContentSearchCache_verifyResponseDataNodesUsingSchemaValidations(String product,String type, String relation)
	{
		RequestGenerator clearRelatedContentSearchCacheReqGen = cmsServiceHelper.invokeClearRelatedContentSearchCache(product, type, relation);
		String clearRelatedContentSearchCacheResponse = clearRelatedContentSearchCacheReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting CMSService clearRelatedContentSearchCache API response :\n\n"+clearRelatedContentSearchCacheResponse);
		log.info("\nPrinting CMSService clearRelatedContentSearchCache API response :\n\n"+clearRelatedContentSearchCacheResponse);
		
		AssertJUnit.assertEquals("CMSService clearRelatedContentSearchCache API is not working", 200, clearRelatedContentSearchCacheReqGen.response.getStatus());
		
		try {
            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/cmsservice-clearrelatedcontentsearchcache-schema.txt");
            List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(clearRelatedContentSearchCacheResponse, jsonschema);
            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in CMSService clearRelatedContentSearchCache API response", CollectionUtils.isEmpty(missingNodeList));
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

	@Test(groups = { "SchemaValidation" })
	public void CMSService_clearAllRelatedContentSearchCache_verifyResponseDataNodesUsingSchemaValidations()
	{
		RequestGenerator clearAllRelatedContentSearchCacheReqGen = cmsServiceHelper.invokeClearAllRelatedContentSearchCache();
		String clearAllRelatedContentSearchCacheResponse = clearAllRelatedContentSearchCacheReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting CMSService clearAllRelatedContentSearchCache API response :\n\n"+clearAllRelatedContentSearchCacheResponse);
		log.info("\nPrinting CMSService clearAllRelatedContentSearchCache API response :\n\n"+clearAllRelatedContentSearchCacheResponse);
		
		AssertJUnit.assertEquals("CMSService clearAllRelatedContentSearchCache API is not working", 200, clearAllRelatedContentSearchCacheReqGen.response.getStatus());
		
		try {
            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/cmsservice-clearallrelatedcontentsearchcache-schema.txt");
            List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(clearAllRelatedContentSearchCacheResponse, jsonschema);
            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in CMSService clearAllRelatedContentSearchCache API response", 
            		CollectionUtils.isEmpty(missingNodeList));
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	@Test(groups = { "SchemaValidation" }, dataProvider="CMSServiceDP_getProductDetailsFromCms_verifyResponseDataNodesUsingSchemaValidations")
	public void CMSService_getProductDetailsFromCms_verifyResponseDataNodesUsingSchemaValidations(String prodId, String successRespCode)
	{
		RequestGenerator getProductDetailsFromCmsReqGen = cmsServiceHelper.invokeGetProductDetails(prodId);
		String getProductDetailsFromCmsResponse = getProductDetailsFromCmsReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting CMSService getProductDetailsFromCms API response :\n\n"+getProductDetailsFromCmsResponse);
		log.info("\nPrinting CMSService getProductDetailsFromCms API response :\n\n"+getProductDetailsFromCmsResponse);
		
		AssertJUnit.assertEquals("CMSService getProductDetailsFromCms API is not working", Integer.parseInt(successRespCode), 
				getProductDetailsFromCmsReqGen.response.getStatus());
		
		try {
            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/cmsservice-getproductdetailsfromcms-schema.txt");
            List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(getProductDetailsFromCmsResponse, jsonschema);
            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in CMSService getProductDetailsFromCms API response", 
            		CollectionUtils.isEmpty(missingNodeList));
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	@Test(groups = { "SchemaValidation" }, dataProvider="CMSServiceDP_getLookBookByIdCms_verifyResponseDataNodesUsingSchemaValidations")
	public void CMSService_getLookBookByIdCms_verifyResponseDataNodesUsingSchemaValidations(String lookBookId, String successRespCode)
	{
		RequestGenerator getLookBookByIdCmsReqGen = cmsServiceHelper.invokeGetLookBookByIdCms(lookBookId);
		String getLookBookByIdCmsResponse = getLookBookByIdCmsReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting CMSService getLookBookByIdCms API response :\n\n"+getLookBookByIdCmsResponse);
		log.info("\nPrinting CMSService getLookBookByIdCms API response :\n\n"+getLookBookByIdCmsResponse);
		
		AssertJUnit.assertEquals("CMSService getLookBookByIdCms API is not working", Integer.parseInt(successRespCode), getLookBookByIdCmsReqGen.response.getStatus());
		
		try {
            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/cmsservice-getlookbookbyidcms-schema.txt");
            List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(getLookBookByIdCmsResponse, jsonschema);
            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in CMSService getLookBookByIdCms API response", 
            		CollectionUtils.isEmpty(missingNodeList));
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	@Test(groups = { "SchemaValidation" }, dataProvider="CMSServiceDP_createContentUsingBody_verifyResponseDataNodesUsingSchemaValidations")
	public void CMSService_createContentUsingBody_verifyResponseDataNodesUsingSchemaValidations(String contentId, String owner, String type, String typeId, String tags)
	{
		String[] payLoadParams = new String[]{ contentId, owner, type, typeId, returnCommanSepTags(tags) };
		RequestGenerator createContentUsingBodyReqGen = cmsServiceHelper.invokeCreateContentUsingBody(payLoadParams);
		String createContentUsingBodyResponse = createContentUsingBodyReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting CMSService createContentUsingBody API response :\n\n"+createContentUsingBodyResponse);
		log.info("\nPrinting CMSService createContentUsingBody API response :\n\n"+createContentUsingBodyResponse);
		
		AssertJUnit.assertEquals("CMSService createContentUsingBody API is not working", 200, createContentUsingBodyReqGen.response.getStatus());
		
		try {
            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/cmsservice-createcontentusingbody-schema.txt");
            List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(createContentUsingBodyResponse, jsonschema);
            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in CMSService createContentUsingBody API response", 
            		CollectionUtils.isEmpty(missingNodeList));
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	@Test(groups = { "SchemaValidation" }, dataProvider="CMSServiceDP_updateContentUsingBody_verifyResponseDataNodesUsingSchemaValidations")
	public void CMSService_updateContentUsingBody_verifyResponseDataNodesUsingSchemaValidations(String contentId, String owner, String type, String typeId, String tags)
	{
		String[] payLoadParams = new String[]{ contentId, owner, type, typeId, returnCommanSepTags(tags) };
		RequestGenerator updateContentUsingBodyReqGen = cmsServiceHelper.invokeUpdateContentUsingBody(contentId, payLoadParams);
		String updateContentUsingBodyResponse = updateContentUsingBodyReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting CMSService updateContentUsingBody API response :\n\n"+updateContentUsingBodyResponse);
		log.info("\nPrinting CMSService updateContentUsingBody API response :\n\n"+updateContentUsingBodyResponse);
		
		AssertJUnit.assertEquals("CMSService updateContentUsingBody API is not working", 200, updateContentUsingBodyReqGen.response.getStatus());
		
		try {
            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/cmsservice-updatecontentusingbody-schema.txt");
            List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(updateContentUsingBodyResponse, jsonschema);
            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in CMSService updateContentUsingBody API response", 
            		CollectionUtils.isEmpty(missingNodeList));
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
}
