package com.myntra.apiTests.portalservices.cmsservice;

import java.util.HashMap;

import com.myntra.apiTests.common.Myntra;
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
public class CMSServiceHelper 
{

	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(CMSServiceHelper.class);
	APIUtilities apiUtil = new APIUtilities();
	
	public RequestGenerator invokeGetContentById(String styleId)
	{
		MyntraService getContentByIdService = Myntra.getService(ServiceType.PORTAL_CMSSERVICE, APINAME.GETCONTENTBYID, init.Configurations);
		getContentByIdService.URL = apiUtil.prepareparameterizedURL(getContentByIdService.URL, styleId);
		System.out.println("\nPrinting CMSService getContentById API URL : "+getContentByIdService.URL);
		log.info("\nPrinting CMSService getContentById API URL : "+getContentByIdService.URL);
		
		System.out.println("\nPrinting CMSService getContentById API Payload : \n\n"+getContentByIdService.Payload);
		log.info("\nPrinting CMSService getContentById API Payload : \n\n"+getContentByIdService.Payload);
		
		HashMap<String, String> getContentByIdHeaders = new HashMap<String, String>();
		getContentByIdHeaders.put("Authorization", "Basic YXBpYWRtaW46bTFudHJhUjBja2V0MTMhIw==");
		
		return new RequestGenerator(getContentByIdService, getContentByIdHeaders);
	}
	
	public RequestGenerator invokeFilteredContentSearch(String queryParams)
	{
		MyntraService filteredContentSearchService = Myntra.getService(ServiceType.PORTAL_CMSSERVICE, APINAME.FILTEREDCONTENTSEARCH, init.Configurations, queryParams);
		filteredContentSearchService.URL = apiUtil.prepareparameterizedURL(filteredContentSearchService.URL, queryParams);
		System.out.println("\nPrinting CMSService filteredContentSearch API URL : "+filteredContentSearchService.URL);
		log.info("\nPrinting CMSService filteredContentSearch API URL : "+filteredContentSearchService.URL);

		System.out.println("\nPrinting CMSService filteredContentSearch API Payload : \n\n"+filteredContentSearchService.Payload);
		log.info("\nPrinting CMSService filteredContentSearch API Payload : \n\n"+filteredContentSearchService.Payload);
		
		HashMap<String, String> filteredContentSearchHeaders = new HashMap<String, String>();
		filteredContentSearchHeaders.put("Authorization", "Basic YXBpYWRtaW46bTFudHJhUjBja2V0MTMhIw==");
		
		return new RequestGenerator(filteredContentSearchService, filteredContentSearchHeaders);
	}
	
	public RequestGenerator invokeRelatedContentSearch(String type, String relation, String product)
	{
		MyntraService relatedContentSearchService = Myntra.getService(ServiceType.PORTAL_CMSSERVICE, APINAME.RELATEDCONTENTSEARCH, init.Configurations, PayloadType.JSON,
				new String[]{ type, relation, product }, PayloadType.JSON);
		System.out.println("\nPrinting CMSService relatedContentSearch API URL : "+relatedContentSearchService.URL);
		log.info("\nPrinting CMSService relatedContentSearch API URL : "+relatedContentSearchService.URL);

		System.out.println("\nPrinting CMSService relatedContentSearch API Payload : \n\n"+relatedContentSearchService.Payload);
		log.info("\nPrinting CMSService relatedContentSearch API Payload : \n\n"+relatedContentSearchService.Payload);
		
		HashMap<String, String> relatedContentSearchHeaders = new HashMap<String, String>();
		relatedContentSearchHeaders.put("Authorization", "Basic YXBpYWRtaW46bTFudHJhUjBja2V0MTMhIw==");
		
		return new RequestGenerator(relatedContentSearchService, relatedContentSearchHeaders);
	}
	
	public RequestGenerator invokeClearContentSearchCache(String queryParams)
	{
		MyntraService clearContentSearchCacheService = Myntra.getService(ServiceType.PORTAL_CMSSERVICE, APINAME.CLEARCONTENTCACHE, init.Configurations);
		clearContentSearchCacheService.URL = apiUtil.prepareparameterizedURL(clearContentSearchCacheService.URL, queryParams);
		System.out.println("\nPrinting CMSService clearContentSearchCache API URL : "+clearContentSearchCacheService.URL);
		log.info("\nPrinting CMSService clearContentSearchCache API URL : "+clearContentSearchCacheService.URL);

		System.out.println("\nPrinting CMSService clearContentSearchCache API Payload : \n\n"+clearContentSearchCacheService.Payload);
		log.info("\nPrinting CMSService clearContentSearchCache API Payload : \n\n"+clearContentSearchCacheService.Payload);
		
		HashMap<String, String> clearContentSearchCacheHeaders = new HashMap<String, String>();
		clearContentSearchCacheHeaders.put("Authorization", "Basic YXBpYWRtaW46bTFudHJhUjBja2V0MTMhIw==");
		
		return new RequestGenerator(clearContentSearchCacheService, clearContentSearchCacheHeaders);
	}
	
	public RequestGenerator invokeClearAllContentSearchCache()
	{
		MyntraService clearAllContentSearchCacheService = Myntra.getService(ServiceType.PORTAL_CMSSERVICE, APINAME.CLEARALLCONTENTCACHE, init.Configurations);
		System.out.println("\nPrinting CMSService clearAllContentSearchCache API URL : "+clearAllContentSearchCacheService.URL);
		log.info("\nPrinting CMSService clearAllContentSearchCache API URL : "+clearAllContentSearchCacheService.URL);

		System.out.println("\nPrinting CMSService clearAllContentSearchCache API Payload : \n\n"+clearAllContentSearchCacheService.Payload);
		log.info("\nPrinting CMSService clearAllContentSearchCache API Payload : \n\n"+clearAllContentSearchCacheService.Payload);
		
		HashMap<String, String> clearAllContentSearchCacheHeaders = new HashMap<String, String>();
		clearAllContentSearchCacheHeaders.put("Authorization", "Basic YXBpYWRtaW46bTFudHJhUjBja2V0MTMhIw==");
		
		return new RequestGenerator(clearAllContentSearchCacheService, clearAllContentSearchCacheHeaders);
	}
	
	public RequestGenerator invokeClearRelatedContentSearchCache(String product, String type, String relation)
	{
		MyntraService clearRelatedContentSearchCacheService = Myntra.getService(ServiceType.PORTAL_CMSSERVICE, APINAME.CLEARRELATEDCONTENTCACHE, init.Configurations,
				PayloadType.JSON, new String[]{ product, type, relation }, PayloadType.JSON);
		System.out.println("\nPrinting CMSService clearRelatedContentSearchCache API URL : "+clearRelatedContentSearchCacheService.URL);
		log.info("\nPrinting CMSService clearRelatedContentSearchCache API URL : "+clearRelatedContentSearchCacheService.URL);

		System.out.println("\nPrinting CMSService clearRelatedContentSearchCache API Payload : \n\n"+clearRelatedContentSearchCacheService.Payload);
		log.info("\nPrinting CMSService clearRelatedContentSearchCache API Payload : \n\n"+clearRelatedContentSearchCacheService.Payload);
		
		HashMap<String, String> clearRelatedContentSearchCacheHeaders = new HashMap<String, String>();
		clearRelatedContentSearchCacheHeaders.put("Authorization", "Basic YXBpYWRtaW46bTFudHJhUjBja2V0MTMhIw==");
		
		return new RequestGenerator(clearRelatedContentSearchCacheService, clearRelatedContentSearchCacheHeaders);
	}
	
	public RequestGenerator invokeClearAllRelatedContentSearchCache()
	{
		MyntraService clearAllRelatedContentSearchCacheService = Myntra.getService(ServiceType.PORTAL_CMSSERVICE, APINAME.CLEARALLRELATEDCONTENTCACHE, init.Configurations);
		System.out.println("\nPrinting CMSService clearAllRelatedContentSearchCache API URL : "+clearAllRelatedContentSearchCacheService.URL);
		log.info("\nPrinting CMSService clearAllRelatedContentSearchCache API URL : "+clearAllRelatedContentSearchCacheService.URL);

		System.out.println("\nPrinting CMSService clearAllRelatedContentSearchCache API Payload : \n\n"+clearAllRelatedContentSearchCacheService.Payload);
		log.info("\nPrinting CMSService clearAllRelatedContentSearchCache API Payload : \n\n"+clearAllRelatedContentSearchCacheService.Payload);
		
		HashMap<String, String> clearAllRelatedContentSearchCacheHeaders = new HashMap<String, String>();
		clearAllRelatedContentSearchCacheHeaders.put("Authorization", "Basic YXBpYWRtaW46bTFudHJhUjBja2V0MTMhIw==");
		
		return new RequestGenerator(clearAllRelatedContentSearchCacheService, clearAllRelatedContentSearchCacheHeaders);
	}
	
	public RequestGenerator invokeGetProductDetails(String prodId)
	{
		MyntraService getProductDetailsService = Myntra.getService(ServiceType.PORTAL_CMSSERVICE, APINAME.GETPRODUCTDETAILSFROMCMS, init.Configurations, prodId);
		getProductDetailsService.URL = apiUtil.prepareparameterizedURL(getProductDetailsService.URL, prodId);
		System.out.println("\nPrinting CMSService getProductDetails API URL : "+getProductDetailsService.URL);
		log.info("\nPrinting CMSService getProductDetails API URL : "+getProductDetailsService.URL);

		System.out.println("\nPrinting CMSService getProductDetails API Payload : \n\n"+getProductDetailsService.Payload);
		log.info("\nPrinting CMSService getProductDetails API Payload : \n\n"+getProductDetailsService.Payload);
		
		HashMap<String, String> getProductDetailsHeaders = new HashMap<String, String>();
		getProductDetailsHeaders.put("Authorization", "Basic YXBpYWRtaW46bTFudHJhUjBja2V0MTMhIw==");
		
		return new RequestGenerator(getProductDetailsService, getProductDetailsHeaders);
	}
	
	public RequestGenerator invokeGetLookBookByIdCms(String lookBookId)
	{
		MyntraService getLookBookByIdCmsService = Myntra.getService(ServiceType.PORTAL_CMSSERVICE, APINAME.GETLOOKBOOKBYIDCMS, init.Configurations, lookBookId);
		getLookBookByIdCmsService.URL = apiUtil.prepareparameterizedURL(getLookBookByIdCmsService.URL, lookBookId);
		System.out.println("\nPrinting CMSService getLookBookByIdCms API URL : "+getLookBookByIdCmsService.URL);
		log.info("\nPrinting CMSService getLookBookByIdCms API URL : "+getLookBookByIdCmsService.URL);

		System.out.println("\nPrinting CMSService getLookBookByIdCms API Payload : \n\n"+getLookBookByIdCmsService.Payload);
		log.info("\nPrinting CMSService getLookBookByIdCms API Payload : \n\n"+getLookBookByIdCmsService.Payload);
		
		HashMap<String, String> getLookBookByIdCmsHeaders = new HashMap<String, String>();
		getLookBookByIdCmsHeaders.put("Authorization", "Basic YXBpYWRtaW46bTFudHJhUjBja2V0MTMhIw==");
		
		return new RequestGenerator(getLookBookByIdCmsService, getLookBookByIdCmsHeaders);
	}
	
	public RequestGenerator invokeCreateContentUsingBody(String[] payLoadParams)
	{
		MyntraService createContentUsingBodyService = Myntra.getService(ServiceType.PORTAL_CMSSERVICE, APINAME.CREATECONTENTUSINGBODY, init.Configurations, payLoadParams);
		System.out.println("\nPrinting CMSService createContentUsingBody API URL : "+createContentUsingBodyService.URL);
		log.info("\nPrinting CMSService createContentUsingBody API URL : "+createContentUsingBodyService.URL);

		System.out.println("\nPrinting CMSService createContentUsingBody API Payload : \n\n"+createContentUsingBodyService.Payload);
		log.info("\nPrinting CMSService createContentUsingBody API Payload : \n\n"+createContentUsingBodyService.Payload);
		
		HashMap<String, String> createContentUsingBodyHeaders = new HashMap<String, String>();
		createContentUsingBodyHeaders.put("Authorization", "Basic YXBpYWRtaW46bTFudHJhUjBja2V0MTMhIw==");
		
		return new RequestGenerator(createContentUsingBodyService, createContentUsingBodyHeaders);
	}
	
	public RequestGenerator invokeUpdateContentUsingBody(String contentId, String[] payLoadParams)
	{
		MyntraService updateContentUsingBodyService = Myntra.getService(ServiceType.PORTAL_CMSSERVICE, APINAME.UPDATECONTENTUSINGBODY, init.Configurations, payLoadParams);
		updateContentUsingBodyService.URL = apiUtil.prepareparameterizedURL(updateContentUsingBodyService.URL, contentId);
		
		System.out.println("\nPrinting CMSService updateContentUsingBody API URL : "+updateContentUsingBodyService.URL);
		log.info("\nPrinting CMSService updateContentUsingBody API URL : "+updateContentUsingBodyService.URL);

		System.out.println("\nPrinting CMSService updateContentUsingBody API Payload : \n\n"+updateContentUsingBodyService.Payload);
		log.info("\nPrinting CMSService updateContentUsingBody API Payload : \n\n"+updateContentUsingBodyService.Payload);
		
		HashMap<String, String> updateContentUsingBodyHeaders = new HashMap<String, String>();
		updateContentUsingBodyHeaders.put("Authorization", "Basic YXBpYWRtaW46bTFudHJhUjBja2V0MTMhIw==");
		
		return new RequestGenerator(updateContentUsingBodyService, updateContentUsingBodyHeaders);
	}
}
