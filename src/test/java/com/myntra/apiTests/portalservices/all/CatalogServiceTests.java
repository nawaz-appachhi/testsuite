package com.myntra.apiTests.portalservices.all;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.myntra.apiTests.common.Myntra;
import com.myntra.apiTests.dataproviders.CatalogServiceDP;
import com.myntra.apiTests.portalservices.catalogservice.CatalogServiceHelper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import argo.saj.InvalidSyntaxException;

import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.Toolbox;
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
public class CatalogServiceTests extends CatalogServiceDP
{
	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(CatalogServiceTests.class);
	APIUtilities apiUtil = new APIUtilities();
	CatalogServiceHelper catalogServiceHelper = new CatalogServiceHelper();
	
	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression",
			"ExhaustiveRegression", "ProdSanity" }, dataProvider = "getProductByIdDataProvider")
	public void CatalogService_getProductById(String id)
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
		//System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());
		AssertJUnit.assertTrue("Checking respose", req.respvalidate.DoesNodesExists(getProductByIdList()));
		AssertJUnit.assertTrue("Validation of product id from response", req.respvalidate.GetNodeValue("data.productId", jsonRes).equalsIgnoreCase(id));
	}

	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression",
	"ExhaustiveRegression"}, dataProvider = "getProductByIdNegativeDataProvider")
	public void CatalogService_getProductByIdNegative(String id)
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
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());
		AssertJUnit.assertTrue("Checking respose", req.respvalidate.DoesNodesExists(getProductByIdNegativeList()));
		try {
			AssertJUnit.assertTrue("Checking statusMessage", req.respvalidate.GetNodeValue_Argo("status.statusMessage", req.respvalidate.returnresponseasstring()).contains("Row with given id/info not found"));
		} catch (InvalidSyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test(groups = { "Sanity", "MiniRegression", "Regression",
			"ExhaustiveRegression", "ProdSanity" }, dataProvider = "searchForProductByCriteriaDataProvider")
	public void CatalogService_searchForProductByCriteria(String queryParams)
	{
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_CATALOGSERVICE,
				APINAME.SEARCHFORPRODUCTBYCRITERIA, init.Configurations);
		service.URL = apiUtil.prepareparameterizedURL(service.URL, queryParams);
		System.out.println(service.URL);
		HashMap getParam = new HashMap();
		getParam.put("Authorization",
				"Basic YXBpYWRtaW46bTFudHJhUjBja2V0MTMhIw==");
		RequestGenerator req = new RequestGenerator(service, getParam);
		System.out.println(req.respvalidate.returnresponseasstring());
		String jsonRes = req.respvalidate.returnresponseasstring();
		AssertJUnit.assertEquals(200, req.response.getStatus());
		AssertJUnit.assertTrue("Checking respose", req.respvalidate.DoesNodesExists(commonResponse()));
		AssertJUnit.assertTrue("Validation of status.statusMessage(Should be success) in response", req.respvalidate.GetNodeValue("status.statusMessage", jsonRes).equalsIgnoreCase("\"Success\""));
	}

	@Test(groups = { "" }, dataProvider = "searchForProductByCriteriaNegativeDataProvider")
	public void CatalogService_searchForProductByCriteriaNegative(String queryParams)
	{
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_CATALOGSERVICE,
				APINAME.SEARCHFORPRODUCTBYCRITERIA, init.Configurations);
		service.URL = apiUtil.prepareparameterizedURL(service.URL, queryParams);
		System.out.println(service.URL);
		HashMap getParam = new HashMap();
		getParam.put("Authorization",
				"Basic YXBpYWRtaW46bTFudHJhUjBja2V0MTMhIw==");
		RequestGenerator req = new RequestGenerator(service, getParam);
		String jsonRes = req.respvalidate.returnresponseasstring();
		AssertJUnit.assertEquals(200, req.response.getStatus());
	}

	@Test(groups = { "Sanity", "MiniRegression", "Regression",
	"ExhaustiveRegression" }, dataProvider = "clearCacheForProductDataProvider")
	public void CatalogService_clearCacheForProduct(String productId)
	{
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_CATALOGSERVICE,
				APINAME.CLEARCACHEFORPRODUCT, init.Configurations);
		service.URL = apiUtil.prepareparameterizedURL(service.URL, productId);
		System.out.println(service.URL);
		HashMap getParam = new HashMap();
		getParam.put("Authorization",
				"Basic YXBpYWRtaW46bTFudHJhUjBja2V0MTMhIw==");
		RequestGenerator req = new RequestGenerator(service, getParam);
		//System.out.println(req.respvalidate.returnresponseasstring());
		String jsonRes = req.respvalidate.returnresponseasstring();
		AssertJUnit.assertEquals(200, req.response.getStatus());
		AssertJUnit.assertTrue("Checking response", req.respvalidate.DoesNodesExists(commonResponse()));
		AssertJUnit.assertTrue("Validation of status.statusMessage(Should be success) in response", req.respvalidate.GetNodeValue("status.statusMessage", jsonRes).equalsIgnoreCase("\"Successfully cleared cache for style id "+productId+"\""));
	}


	@Test(groups = { "Sanity", "MiniRegression", "Regression",
	"ExhaustiveRegression" }, dataProvider = "clearCacheForProductNegativeDataProvider")
	public void CatalogService_clearCacheForProductNegative(String productId)
	{
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_CATALOGSERVICE,
				APINAME.CLEARCACHEFORPRODUCT, init.Configurations);
		service.URL = apiUtil.prepareparameterizedURL(service.URL, productId);
		System.out.println(service.URL);
		HashMap getParam = new HashMap();
		getParam.put("Authorization",
				"Basic YXBpYWRtaW46bTFudHJhUjBja2V0MTMhIw==");
		RequestGenerator req = new RequestGenerator(service, getParam);
		System.out.println(req.respvalidate.returnresponseasstring());
		String jsonRes = req.respvalidate.returnresponseasstring();
		AssertJUnit.assertEquals("Response code not matching",404, req.response.getStatus());
		//AssertJUnit.assertTrue("Checking respose", req.respvalidate.DoesNodesExists(commonResponse()));
		//AssertJUnit.assertTrue("Validation of status.statusMessage(Should be success) in response", req.respvalidate.GetNodeValue("status.statusMessage", jsonRes).equalsIgnoreCase("\"Successfully cleared cache for style id "+productId+"\""));
	}

	@Test(groups = { "Sanity", "MiniRegression", "Regression",
	"ExhaustiveRegression" })
	public void CatalogService_clearCacheForAllProducts()
	{
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_CATALOGSERVICE,
				APINAME.CLEARCACHEFORALLPRODUCT, init.Configurations);
		System.out.println(service.URL);
		HashMap getParam = new HashMap();
		getParam.put("Authorization",
				"Basic YXBpYWRtaW46bTFudHJhUjBja2V0MTMhIw==");
		RequestGenerator req = new RequestGenerator(service, getParam);
		System.out.println(req.respvalidate.returnresponseasstring());
		String jsonRes = req.respvalidate.returnresponseasstring();
		AssertJUnit.assertEquals(200, req.response.getStatus());
		AssertJUnit.assertTrue("Checking respose", req.respvalidate.DoesNodesExists(commonResponse()));
		AssertJUnit.assertTrue("Validation of status.statusMessage(Should be success) in response", req.respvalidate.GetNodeValue("status.statusMessage", jsonRes).equalsIgnoreCase("\"Successfully cleared cache\""));
		
	}

	@Test(groups = { "Sanity", "MiniRegression", "Regression",
	"ExhaustiveRegression" }, dataProvider = "clearCacheForListOfProductsDataProvider")
	public void CatalogService_clearCacheForListOfProducts(String prodList)
	{

		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_CATALOGSERVICE,
				APINAME.CLEARCACHEFORLISTOFPRODUCT, init.Configurations,
				returnListOfProducts(prodList));
		System.out.println(service.URL);
		System.out.println(service.Payload);
		service.payloaddataformat = PayloadType.XML;
		service.responsedataformat = PayloadType.JSON;
		HashMap getParam = new HashMap();
		getParam.put("Authorization",
				"Basic YXBpYWRtaW46bTFudHJhUjBja2V0MTMhIw==");
		RequestGenerator req = new RequestGenerator(service, getParam);
		String commaStyleId = prodList.replaceAll(",", ", ");
		String jsonRes = req.respvalidate.returnresponseasstring();
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());
		AssertJUnit.assertTrue("Checking respose", req.respvalidate.DoesNodesExists(commonResponse()));
		AssertJUnit.assertTrue("Validation of status.statusMessage(Should be success) in response", req.respvalidate.GetNodeValue("status.statusMessage", jsonRes).equalsIgnoreCase("\"Successfully cleared cache for style ids ["+commaStyleId+"]\""));
	}

	@Test(groups = { "Sanity", "MiniRegression", "Regression",
			"ExhaustiveRegression", "ProdSanity" }, dataProvider = "getSizeChartOfProductByIdDataProvider")
	public void CatalogService_getSizeChartOfProductById(String productId)
	{
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_CATALOGSERVICE,
				APINAME.GETSIZECHARTOFPRODUCTBYID, init.Configurations);
		service.URL = apiUtil.prepareparameterizedURL(service.URL, productId);
		System.out.println(service.URL);
		HashMap getParam = new HashMap();
		getParam.put("Authorization",
				"Basic YXBpYWRtaW46bTFudHJhUjBja2V0MTMhIw==");
		RequestGenerator req = new RequestGenerator(service, getParam);
		String jsonRes = req.respvalidate.returnresponseasstring();
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());
		AssertJUnit.assertTrue("Checking respose", req.respvalidate.DoesNodesExists(commonResponse()));
		AssertJUnit.assertTrue("Validation of product id from response", req.respvalidate.GetNodeValue("data.productId", jsonRes).equalsIgnoreCase(productId));
	}

	@Test(groups = { "Sanity", "MiniRegression", "Regression",
			"ExhaustiveRegression", "ProdSanity" }, dataProvider = "findByListOfProductIdsDataProvider")
	public void CatalogService_findByListOfProductIds(String productId)
	{
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_CATALOGSERVICE,
				APINAME.FINDBYLISTOFIDS, init.Configurations);
		service.URL = apiUtil.prepareparameterizedURL(service.URL, productId);
		System.out.println(service.URL);
		HashMap getParam = new HashMap();
		getParam.put("Authorization",
				"Basic YXBpYWRtaW46bTFudHJhUjBja2V0MTMhIw==");
		RequestGenerator req = new RequestGenerator(service, getParam);
		String jsonRes = req.respvalidate.returnresponseasstring();
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());
		AssertJUnit.assertTrue("Checking respose", req.respvalidate.DoesNodesExists(commonResponse()));
		AssertJUnit.assertTrue("Validation of total count from response", Integer.parseInt(req.respvalidate.GetNodeValue("status.totalCount", jsonRes))==(productId.split(",").length));
	}
	
	@Test(groups = { "Sanity", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "findByListOfProductIdsNegativeDataProvider")
	public void CatalogService_findByListOfProductIdsNegative(String productId)
	{
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_CATALOGSERVICE,
				APINAME.FINDBYLISTOFIDS, init.Configurations);
		service.URL = apiUtil.prepareparameterizedURL(service.URL, productId);
		System.out.println(service.URL);
		HashMap getParam = new HashMap();
		getParam.put("Authorization",
				"Basic YXBpYWRtaW46bTFudHJhUjBja2V0MTMhIw==");
		RequestGenerator req = new RequestGenerator(service, getParam);
		String jsonRes = req.respvalidate.returnresponseasstring();
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());
		AssertJUnit.assertTrue("Checking respose", req.respvalidate.DoesNodesExists(commonResponse()));
		AssertJUnit.assertTrue("Validation of status.statusCode from response", Integer.parseInt(req.respvalidate.GetNodeValue("status.statusCode", jsonRes))==51);
	}


	private String returnListOfProducts(String prodIds)
	{
		String finalTagsStr = "<listEntry>\n";
		String[] splittedArray = prodIds.split(",");
		for (String tag : splittedArray)
		{
			finalTagsStr += "<list>" + tag + "</list>\n";
		}
		finalTagsStr += "</listEntry>";
		return finalTagsStr;
	}

	private List getProductByIdList(){
		List<String> checkResponse = new ArrayList();
		checkResponse.add("status.statusCode");
		checkResponse.add("status.statusMessage");
		checkResponse.add("status.statusType");
		checkResponse.add("status.totalCount");
		return checkResponse;
	}

	private List getProductByIdNegativeList(){
		List<String> checkResponse = new ArrayList();
		checkResponse.add("status.statusCode");
		checkResponse.add("status.statusMessage");
		checkResponse.add("status.statusType");
		checkResponse.add("status.totalCount");
		return checkResponse;
	}

	private List commonResponse(){
		List<String> checkResponse = new ArrayList();
		checkResponse.add("status.statusCode");
		checkResponse.add("status.statusMessage");
		checkResponse.add("status.statusType");
		checkResponse.add("status.totalCount");
		return checkResponse;
	}

	@Test(groups = { "SchemaValidation" }, dataProvider = "CatalogServiceDP_getProductById_verifyResponseDataNodesUsingSchemaValidations")
	public void CatalogService_getProductById_verifyResponseDataNodesUsingSchemaValidations(String styleId, String successRespCode)
	{
		RequestGenerator getProductByIdReqGen = catalogServiceHelper.invokeGetProductById(styleId);
		String getProductByIdResponse = getProductByIdReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting CatalogService getProductById API response :\n\n"+getProductByIdResponse);
		log.info("\nPrinting CatalogService getProductById API response :\n\n"+getProductByIdResponse);
		
		AssertJUnit.assertEquals("CatalogService getProductById API is not working", 200, getProductByIdReqGen.response.getStatus());
		
		try {
            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/catalogservice-getProductById-schema.txt");
            List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(getProductByIdResponse, jsonschema);
            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in CatalogService getProductById API response", 
            		CollectionUtils.isEmpty(missingNodeList));
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	@Test(groups = { "SchemaValidation" }, dataProvider = "CatalogServiceDP_searchForProductByCriteria_verifyResponseDataNodesUsingSchemaValidations")
	public void CatalogService_searchForProductByCriteria_verifyResponseDataNodesUsingSchemaValidations(String queryParams)
	{
		RequestGenerator searchForProductByCriteriaReqGen = catalogServiceHelper.invokeSearchForProductByCriteria(queryParams);
		String getProductByIdResponse = searchForProductByCriteriaReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting CatalogService searchForProductByCriteria API response :\n\n"+getProductByIdResponse);
		log.info("\nPrinting CatalogService searchForProductByCriteria API response :\n\n"+getProductByIdResponse);
		
		AssertJUnit.assertEquals("CatalogService searchForProductByCriteria API is not working", 200, searchForProductByCriteriaReqGen.response.getStatus());
		
		try {
            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/catalogservice-searchforproductbycriteria-schema.txt");
            List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(getProductByIdResponse, jsonschema);
            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in CatalogService searchForProductByCriteria API response", 
            		CollectionUtils.isEmpty(missingNodeList));
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	@Test(groups = { "SchemaValidation" }, dataProvider = "CatalogServiceDP_clearCacheForProduct_verifyResponseDataNodesUsingSchemaValidations")
	public void CatalogService_clearCacheForProduct_verifyResponseDataNodesUsingSchemaValidations(String productId, String successRespCode)
	{
		RequestGenerator clearCacheForProductReqGen = catalogServiceHelper.invokeClearCacheForProduct(productId);
		String clearCacheForProductResponse = clearCacheForProductReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting CatalogService clearCacheForProduct API response :\n\n"+clearCacheForProductResponse);
		log.info("\nPrinting CatalogService clearCacheForProduct API response :\n\n"+clearCacheForProductResponse);
		
		AssertJUnit.assertEquals("CatalogService clearCacheForProduct API is not working", 200, clearCacheForProductReqGen.response.getStatus());
		
		try {
            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/catalogservice-clearcacheforproduct-schema.txt");
            List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(clearCacheForProductResponse, jsonschema);
            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in CatalogService clearCacheForProduct API response", 
            		CollectionUtils.isEmpty(missingNodeList));
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

	@Test(groups = { "SchemaValidation" })
	public void CatalogService_clearCacheForAllProducts_verifyResponseDataNodesUsingSchemaValidations()
	{
		RequestGenerator clearCacheForAllProductsReqGen = catalogServiceHelper.invokeClearCacheForAllProducts();
		String clearCacheForAllProductsResponse = clearCacheForAllProductsReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting CatalogService clearCacheForAllProducts API response :\n\n"+clearCacheForAllProductsResponse);
		log.info("\nPrinting CatalogService clearCacheForAllProducts API response :\n\n"+clearCacheForAllProductsResponse);
		
		AssertJUnit.assertEquals("CatalogService clearCacheForAllProducts API is not working", 200, clearCacheForAllProductsReqGen.response.getStatus());
		
		try {
            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/catalogservice-clearcacheforallproducts-schema.txt");
            List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(clearCacheForAllProductsResponse, jsonschema);
            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in CatalogService clearCacheForAllProducts API response", 
            		CollectionUtils.isEmpty(missingNodeList));
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	@Test(groups = { "SchemaValidation" }, dataProvider = "CatalogServiceDP_clearCacheForListOfProducts_verifyResponseDataNodesUsingSchemaValidations")
	public void CatalogService_clearCacheForListOfProducts_verifyResponseDataNodesUsingSchemaValidations(String prodList)
	{
		RequestGenerator clearCacheForListOfProductsReqGen = catalogServiceHelper.invokeClearCacheForListOfProducts(returnListOfProducts(prodList));
		String clearCacheForListOfProductsResponse = clearCacheForListOfProductsReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting CatalogService clearCacheForListOfProducts API response :\n\n"+clearCacheForListOfProductsResponse);
		log.info("\nPrinting CatalogService clearCacheForListOfProducts API response :\n\n"+clearCacheForListOfProductsResponse);
		
		AssertJUnit.assertEquals("CatalogService clearCacheForListOfProducts API is not working", 200, clearCacheForListOfProductsReqGen.response.getStatus());
		
		try {
            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/catalogservice-clearcacheforlistofproducts-schema.txt");
            List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(clearCacheForListOfProductsResponse, jsonschema);
            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in CatalogService clearCacheForListOfProducts API response", 
            		CollectionUtils.isEmpty(missingNodeList));
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	@Test(groups = { "SchemaValidation" }, dataProvider = "CatalogServiceDP_getSizeChartOfProductById_verifyResponseDataNodesUsingSchemaValidations")
	public void CatalogService_getSizeChartOfProductById_verifyResponseDataNodesUsingSchemaValidations(String productId, String successRespCode)
	{
		RequestGenerator getSizeChartOfProductByIdReqGen = catalogServiceHelper.invokeGetSizeChartOfProductById(productId);
		String getSizeChartOfProductByIdResponse = getSizeChartOfProductByIdReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting CatalogService getSizeChartOfProductById API response :\n\n"+getSizeChartOfProductByIdResponse);
		log.info("\nPrinting CatalogService getSizeChartOfProductById API response :\n\n"+getSizeChartOfProductByIdResponse);
		
		AssertJUnit.assertEquals("CatalogService getSizeChartOfProductById API is not working", 200, getSizeChartOfProductByIdReqGen.response.getStatus());
		
		try {
            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/catalogservice-getsizechartofproductbyid-schema.txt");
            List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(getSizeChartOfProductByIdResponse, jsonschema);
            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in CatalogService getSizeChartOfProductById API response", 
            		CollectionUtils.isEmpty(missingNodeList));
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

	@Test(groups = { "SchemaValidation" }, dataProvider = "CatalogServiceDP_findByListOfProductIds_verifyResponseDataNodesUsingSchemaValidations")
	public void CatalogService_findByListOfProductIds_verifyResponseDataNodesUsingSchemaValidations(String productIds)
	{
		RequestGenerator findByListOfProductIdsReqGen = catalogServiceHelper.invokeFindByListOfProductIds(productIds);
		String findByListOfProductIdsResponse = findByListOfProductIdsReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting CatalogService findByListOfProductIds API response :\n\n"+findByListOfProductIdsResponse);
		log.info("\nPrinting CatalogService findByListOfProductIds API response :\n\n"+findByListOfProductIdsResponse);
		
		AssertJUnit.assertEquals("CatalogService findByListOfProductIds API is not working", 200, findByListOfProductIdsReqGen.response.getStatus());
		
		try {
            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/catalogservice-findbylistofproductids-schema.txt");
            List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(findByListOfProductIdsResponse, jsonschema);
            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in CatalogService findByListOfProductIds API response", 
            		CollectionUtils.isEmpty(missingNodeList));
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
}
