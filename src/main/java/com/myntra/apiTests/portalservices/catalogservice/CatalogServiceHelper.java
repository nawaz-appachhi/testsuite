package com.myntra.apiTests.portalservices.catalogservice;

import java.util.HashMap;

import com.myntra.apiTests.common.Myntra;
import com.myntra.apiTests.portalservices.cmsservice.CMSServiceHelper;
import org.apache.log4j.Logger;

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
public class CatalogServiceHelper 
{

	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(CMSServiceHelper.class);
	APIUtilities apiUtil = new APIUtilities();
	
	public RequestGenerator invokeGetProductById(String styleId)
	{
		MyntraService getProductByIdService = Myntra.getService(ServiceType.PORTAL_CATALOGSERVICE, APINAME.GETPRODUCTBYID, init.Configurations);
		getProductByIdService.URL = apiUtil.prepareparameterizedURL(getProductByIdService.URL, styleId);
		System.out.println("\nPrinting CatalogService getProductById API URL : "+getProductByIdService.URL);
		log.info("\nPrinting CatalogService getProductById API URL : "+getProductByIdService.URL);
		
		System.out.println("\nPrinting CatalogService getProductById API Payload : \n\n"+getProductByIdService.Payload);
		log.info("\nPrinting CatalogService getProductById API Payload : \n\n"+getProductByIdService.Payload);
		
		HashMap<String, String> getProductByIdHeaders = new HashMap<String, String>();
		getProductByIdHeaders.put("Authorization", "Basic YXBpYWRtaW46bTFudHJhUjBja2V0MTMhIw==");
		
		return new RequestGenerator(getProductByIdService, getProductByIdHeaders);
	}
	
	public RequestGenerator invokeSearchForProductByCriteria(String queryParams)
	{
		MyntraService searchForProductByCriteriaService = Myntra.getService(ServiceType.PORTAL_CATALOGSERVICE, APINAME.SEARCHFORPRODUCTBYCRITERIA, init.Configurations);
		searchForProductByCriteriaService.URL = apiUtil.prepareparameterizedURL(searchForProductByCriteriaService.URL, queryParams);
		System.out.println("\nPrinting CatalogService searchForProductByCriteria API URL : "+searchForProductByCriteriaService.URL);
		log.info("\nPrinting CatalogService searchForProductByCriteria API URL : "+searchForProductByCriteriaService.URL);
		
		System.out.println("\nPrinting CatalogService searchForProductByCriteria API Payload : \n\n"+searchForProductByCriteriaService.Payload);
		log.info("\nPrinting CatalogService searchForProductByCriteria API Payload : \n\n"+searchForProductByCriteriaService.Payload);
		
		HashMap<String, String> searchForProductByCriteriaHeaders = new HashMap<String, String>();
		searchForProductByCriteriaHeaders.put("Authorization", "Basic YXBpYWRtaW46bTFudHJhUjBja2V0MTMhIw==");
		
		return new RequestGenerator(searchForProductByCriteriaService, searchForProductByCriteriaHeaders);
	}
	
	public RequestGenerator invokeClearCacheForProduct(String productId)
	{
		MyntraService clearCacheForProductService = Myntra.getService(ServiceType.PORTAL_CATALOGSERVICE, APINAME.CLEARCACHEFORPRODUCT, init.Configurations);
		clearCacheForProductService.URL = apiUtil.prepareparameterizedURL(clearCacheForProductService.URL, productId);
		System.out.println("\nPrinting CatalogService clearCacheForProduct API URL : "+clearCacheForProductService.URL);
		log.info("\nPrinting CatalogService clearCacheForProduct API URL : "+clearCacheForProductService.URL);
		
		System.out.println("\nPrinting CatalogService clearCacheForProduct API Payload : \n\n"+clearCacheForProductService.Payload);
		log.info("\nPrinting CatalogService clearCacheForProduct API Payload : \n\n"+clearCacheForProductService.Payload);
		
		HashMap<String, String> clearCacheForProductHeaders = new HashMap<String, String>();
		clearCacheForProductHeaders.put("Authorization", "Basic YXBpYWRtaW46bTFudHJhUjBja2V0MTMhIw==");
		
		return new RequestGenerator(clearCacheForProductService, clearCacheForProductHeaders);
	}
	
	public RequestGenerator invokeClearCacheForAllProducts()
	{
		MyntraService clearCacheForAllProductsService = Myntra.getService(ServiceType.PORTAL_CATALOGSERVICE, APINAME.CLEARCACHEFORALLPRODUCT, init.Configurations);
		System.out.println("\nPrinting CatalogService clearCacheForAllProducts API URL : "+clearCacheForAllProductsService.URL);
		log.info("\nPrinting CatalogService clearCacheForAllProducts API URL : "+clearCacheForAllProductsService.URL);
		
		System.out.println("\nPrinting CatalogService clearCacheForAllProducts API Payload : \n\n"+clearCacheForAllProductsService.Payload);
		log.info("\nPrinting CatalogService clearCacheForAllProducts API Payload : \n\n"+clearCacheForAllProductsService.Payload);
		
		HashMap<String, String> clearCacheForAllProductsHeaders = new HashMap<String, String>();
		clearCacheForAllProductsHeaders.put("Authorization", "Basic YXBpYWRtaW46bTFudHJhUjBja2V0MTMhIw==");
		
		return new RequestGenerator(clearCacheForAllProductsService, clearCacheForAllProductsHeaders);
	}
	
	public RequestGenerator invokeClearCacheForListOfProducts(String prodList)
	{
		MyntraService clearCacheForListOfProductsService = Myntra.getService(ServiceType.PORTAL_CATALOGSERVICE, APINAME.CLEARCACHEFORLISTOFPRODUCT,
				init.Configurations, prodList);
		clearCacheForListOfProductsService.payloaddataformat = PayloadType.XML;
		clearCacheForListOfProductsService.responsedataformat = PayloadType.JSON;
		System.out.println("\nPrinting CatalogService clearCacheForListOfProducts API URL : "+clearCacheForListOfProductsService.URL);
		log.info("\nPrinting CatalogService clearCacheForListOfProducts API URL : "+clearCacheForListOfProductsService.URL);
		
		System.out.println("\nPrinting CatalogService clearCacheForListOfProducts API Payload : \n\n"+clearCacheForListOfProductsService.Payload);
		log.info("\nPrinting CatalogService clearCacheForListOfProducts API Payload : \n\n"+clearCacheForListOfProductsService.Payload);
		
		HashMap<String, String> clearCacheForListOfProductsHeaders = new HashMap<String, String>();
		clearCacheForListOfProductsHeaders.put("Authorization", "Basic YXBpYWRtaW46bTFudHJhUjBja2V0MTMhIw==");
		
		return new RequestGenerator(clearCacheForListOfProductsService, clearCacheForListOfProductsHeaders);
	}
	
	public RequestGenerator invokeGetSizeChartOfProductById(String productId)
	{
		MyntraService getSizeChartOfProductByIdService = Myntra.getService(ServiceType.PORTAL_CATALOGSERVICE, APINAME.GETSIZECHARTOFPRODUCTBYID, init.Configurations);
		getSizeChartOfProductByIdService.URL = apiUtil.prepareparameterizedURL(getSizeChartOfProductByIdService.URL, productId);
		System.out.println("\nPrinting CatalogService getSizeChartOfProductById API URL : "+getSizeChartOfProductByIdService.URL);
		log.info("\nPrinting CatalogService getSizeChartOfProductById API URL : "+getSizeChartOfProductByIdService.URL);
		
		System.out.println("\nPrinting CatalogService getSizeChartOfProductById API Payload : \n\n"+getSizeChartOfProductByIdService.Payload);
		log.info("\nPrinting CatalogService getSizeChartOfProductById API Payload : \n\n"+getSizeChartOfProductByIdService.Payload);
		
		HashMap<String, String> getSizeChartOfProductByIdHeaders = new HashMap<String, String>();
		getSizeChartOfProductByIdHeaders.put("Authorization", "Basic YXBpYWRtaW46bTFudHJhUjBja2V0MTMhIw==");
		
		return new RequestGenerator(getSizeChartOfProductByIdService, getSizeChartOfProductByIdHeaders);
	}
	
	public RequestGenerator invokeFindByListOfProductIds(String productIds)
	{
		MyntraService findByListOfProductIdsService = Myntra.getService(ServiceType.PORTAL_CATALOGSERVICE, APINAME.FINDBYLISTOFIDS, init.Configurations);
		findByListOfProductIdsService.URL = apiUtil.prepareparameterizedURL(findByListOfProductIdsService.URL, productIds);
		System.out.println("\nPrinting CatalogService findByListOfProductIds API URL : "+findByListOfProductIdsService.URL);
		log.info("\nPrinting CatalogService findByListOfProductIds API URL : "+findByListOfProductIdsService.URL);
		
		System.out.println("\nPrinting CatalogService findByListOfProductIds API Payload : \n\n"+findByListOfProductIdsService.Payload);
		log.info("\nPrinting CatalogService findByListOfProductIds API Payload : \n\n"+findByListOfProductIdsService.Payload);
		
		HashMap<String, String> findByListOfProductIdsHeaders = new HashMap<String, String>();
		findByListOfProductIdsHeaders.put("Authorization", "Basic YXBpYWRtaW46bTFudHJhUjBja2V0MTMhIw==");
		
		return new RequestGenerator(findByListOfProductIdsService, findByListOfProductIdsHeaders);
	}
}
