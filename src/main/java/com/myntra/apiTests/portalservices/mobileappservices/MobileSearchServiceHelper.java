package com.myntra.apiTests.portalservices.mobileappservices;

import com.myntra.apiTests.common.Myntra;
import com.myntra.apiTests.portalservices.commons.CommonUtils;
import org.apache.log4j.Logger;

import com.myntra.apiTests.InitializeFramework;
import com.myntra.lordoftherings.Initialize;
import com.myntra.apiTests.common.APINAME;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.apiTests.common.ServiceType;

/**
 * @author shankara.c
 *
 */
public class MobileSearchServiceHelper extends CommonUtils
{
	static Initialize init = InitializeFramework.init;
	static Logger log = Logger.getLogger(MobileSearchServiceHelper.class);
	
	/**
	 * This method is used to invoke MobileSearchService getFirstPageOfSearch API
	 * 
	 * @param paramSearchQuery
	 * @param paramRows
	 * @param paramReturnDocs
	 * @param paramIsFacet
	 * @return RequestGenerator
	 */
	public RequestGenerator invokeGetFirstPageOfSearch(String paramSearchQuery, String paramRows, String paramReturnDocs, String paramIsFacet)
	{
        MyntraService getFirstPageOfMobileSearchService = Myntra.getService(ServiceType.PORTAL_MOBILEAPPWEBSERVICES, APINAME.MOBILEAPPSEARCHGETFIRSTPAGE, init.Configurations,
        		new String[]{ paramSearchQuery, paramRows, paramReturnDocs, paramIsFacet });
		System.out.println("\nPrinting MobileSearchService getFirstPageOfSearch API URL : "+getFirstPageOfMobileSearchService.URL);
		log.info("\nPrinting MobileSearchService getFirstPageOfSearch API URL : "+getFirstPageOfMobileSearchService.URL);
		
		System.out.println("\nPrinting MobileSearchService getFirstPageOfSearch API Payload : \n\n"+getFirstPageOfMobileSearchService.Payload);
		log.info("\nPrinting MobileSearchService getFirstPageOfSearch API Payload : \n\n"+getFirstPageOfMobileSearchService.Payload);
        
       return new RequestGenerator(getFirstPageOfMobileSearchService);
		
	}
	
	/**
	 * This method is used to invoke MobileSearchService getNextPageOfSearch API
	 * 
	 * @param paramAction
	 * @param paramRows
	 * @param paramRequestObject
	 * @return RequestGenerator
	 */
	public RequestGenerator invokeGetNextPageOfSearch(String paramAction, String paramRows, String paramRequestObject)
	{
        MyntraService getNextPageOfMobileSearchService = Myntra.getService(ServiceType.PORTAL_MOBILEAPPWEBSERVICES, APINAME.MOBILEAPPSEARCHGETNEXTPAGE, init.Configurations,
        		new String[]{ paramAction, paramRows, paramRequestObject });
		System.out.println("\nPrinting MobileSearchService getNextPageOfSearch API URL : "+getNextPageOfMobileSearchService.URL);
		log.info("\nPrinting MobileSearchService getNextPageOfSearch API URL : "+getNextPageOfMobileSearchService.URL);
		
		System.out.println("\nPrinting MobileSearchService getNextPageOfSearch API Payload : \n\n"+getNextPageOfMobileSearchService.Payload);
		log.info("\nPrinting MobileSearchService getNextPageOfSearch API Payload : \n\n"+getNextPageOfMobileSearchService.Payload);
        
       return new RequestGenerator(getNextPageOfMobileSearchService);
		
	}
	
	/**
	 * This method is used to invoke MobileSearchService getSortedMobileSearch API
	 * 
	 * @param paramSortBy
	 * @param paramRequestObject
	 * @return RequestGenerator
	 */
	public RequestGenerator invokeGetSortedMobileSearch(String paramSortBy, String paramRequestObject)
	{
        MyntraService getSortedMobileSearchService = Myntra.getService(ServiceType.PORTAL_MOBILEAPPWEBSERVICES, APINAME.MOBILEAPPSEARCHSORTBY, init.Configurations,
        		new String[]{ paramSortBy, paramRequestObject });
		System.out.println("\nPrinting MobileSearchService getSortedMobileSearch API URL : "+getSortedMobileSearchService.URL);
		log.info("\nPrinting MobileSearchService getSortedMobileSearch API URL : "+getSortedMobileSearchService.URL);
		
		System.out.println("\nPrinting MobileSearchService getSortedMobileSearch API Payload : \n\n"+getSortedMobileSearchService.Payload);
		log.info("\nPrinting MobileSearchService getSortedMobileSearch API Payload : \n\n"+getSortedMobileSearchService.Payload);
        
       return new RequestGenerator(getSortedMobileSearchService);
		
	}
	
	/**
	 * This method is used to invoke MobileSearchService searchWithPresetFilters API
	 * 
	 * @param paramSearchQuery
	 * @param paramRows
	 * @param paramReturnDocs
	 * @param paramIsFacet
	 * @param paramFilters
	 * @return RequestGenerator
	 */
	public RequestGenerator invokeSearchWithPresetFilters(String paramSearchQuery, String paramRows, String paramReturnDocs, String paramIsFacet, String paramFilters)
	{
        MyntraService searchWithPresetFiltersService = Myntra.getService(ServiceType.PORTAL_MOBILEAPPWEBSERVICES, APINAME.MOBILEAPPSEARCHWITHPRESETFILTERS,
        		init.Configurations, new String[]{ paramSearchQuery, paramRows, paramReturnDocs, paramIsFacet, paramFilters });
		System.out.println("\nPrinting MobileSearchService searchWithPresetFilters API URL : "+searchWithPresetFiltersService.URL);
		log.info("\nPrinting MobileSearchService searchWithPresetFilters API URL : "+searchWithPresetFiltersService.URL);
		
		System.out.println("\nPrinting MobileSearchService searchWithPresetFilters API Payload : \n\n"+searchWithPresetFiltersService.Payload);
		log.info("\nPrinting MobileSearchService searchWithPresetFilters API Payload : \n\n"+searchWithPresetFiltersService.Payload);
        
       return new RequestGenerator(searchWithPresetFiltersService);
		
	}
	
	/**
	 * This method is used to invoke MobileSearchService searchAndApplyFilters API
	 * 
	 * @param paramFilters
	 * @param paramRequestObject
	 * @return RequestGenerator
	 */
	public RequestGenerator invokeSearchAndApplyFilters(String paramFilters, String paramRequestObject)
	{
        MyntraService searchAndApplyFiltersService = Myntra.getService(ServiceType.PORTAL_MOBILEAPPWEBSERVICES, APINAME.MOBILEAPPSEARCHAPPLYFILTER,
        		init.Configurations, new String[]{ paramFilters, paramRequestObject });
		System.out.println("\nPrinting MobileSearchService searchAndApplyFilters API URL : "+searchAndApplyFiltersService.URL);
		log.info("\nPrinting MobileSearchService searchAndApplyFilters API URL : "+searchAndApplyFiltersService.URL);
		
		System.out.println("\nPrinting MobileSearchService searchAndApplyFilters API Payload : \n\n"+searchAndApplyFiltersService.Payload);
		log.info("\nPrinting MobileSearchService searchAndApplyFilters API Payload : \n\n"+searchAndApplyFiltersService.Payload);
        
       return new RequestGenerator(searchAndApplyFiltersService);
		
	}
	
	/**
	 * This method is used to invoke MobileSearchService ParameterizedMobileSearchDataQuery API
	 * 
	 * @param paramMobileSearchDataQuery
	 * @return RequestGenerator
	 */
	public RequestGenerator invokeParameterizedMobileSearchDataQuery(String paramMobileSearchDataQuery)
	{
		MyntraService parameterizedMobileSearchDataQueryService = Myntra.getService(ServiceType.PORTAL_MOBILEAPPWEBSERVICES, APINAME.MOBILEAPPSEARCHDATAQUERY,
				init.Configurations, null, new String[] { paramMobileSearchDataQuery });
		System.out.println("\nPrinting MobileSearchService ParameterizedMobileSearchWithDataQuery API URL : "+parameterizedMobileSearchDataQueryService.URL);
		log.info("\nPrinting MobileSearchService ParameterizedMobileSearchWithDataQuery API URL : "+parameterizedMobileSearchDataQueryService.URL);
		
		System.out.println("\nPrinting MobileSearchService ParameterizedMobileSearchWithDataQuery API Payload : \n\n"+parameterizedMobileSearchDataQueryService.Payload);
		log.info("\nPrinting MobileSearchService ParameterizedMobileSearchWithDataQuery API Payload : \n\n"+parameterizedMobileSearchDataQueryService.Payload);
		
		return new RequestGenerator(parameterizedMobileSearchDataQueryService);
	}
	
	/**
	 * This method is used to invoke MobileSearchService ParameterizedMobileSearchWithDataQueryWithOneFilter API
	 * 
	 * @param paramMobileSearchDataQuery
	 * @param paramArtifact
	 * @param paramValue
	 * @return RequestGenerator
	 */
	public RequestGenerator invokeParameterizedMobileSearchDataQueryWithOneFilter(String paramMobileSearchDataQuery, String paramArtifact, String paramValue)
	{
		MyntraService parameterizedMobileSearchDataQueryWithOneFilterService = Myntra.getService(ServiceType.PORTAL_MOBILEAPPWEBSERVICES,
				APINAME.MOBILEAPPSEARCHDATAAPPLYONEFILTER,  init.Configurations, null, new String[] { paramMobileSearchDataQuery, paramArtifact, paramValue });
		System.out.println("\nPrinting MobileSearchService ParameterizedMobileSearchWithDataQueryWithOneFilter API URL : "+
				parameterizedMobileSearchDataQueryWithOneFilterService.URL);
		log.info("\nPrinting MobileSearchService ParameterizedMobileSearchWithDataQueryWithOneFilter API URL : "+
				parameterizedMobileSearchDataQueryWithOneFilterService.URL);
		
		System.out.println("\nPrinting MobileSearchService ParameterizedMobileSearchWithDataQueryWithOneFilter API Payload : \n\n"+
				parameterizedMobileSearchDataQueryWithOneFilterService.Payload);
		log.info("\nPrinting MobileSearchService ParameterizedMobileSearchWithDataQueryWithOneFilter API Payload : \n\n"+
				parameterizedMobileSearchDataQueryWithOneFilterService.Payload);
		
		return new RequestGenerator(parameterizedMobileSearchDataQueryWithOneFilterService);
	}
	
	/**
	 * This method is used to invoke MobileSearchService ParameterizedMobileSearchWithDataQueryWithTwoFilters API
	 * 
	 * @param paramMobileSearchDataQuery
	 * @param paramArtifact
	 * @param paramValue
	 * @return RequestGenerator
	 */
	public RequestGenerator invokeParameterizedMobileSearchDataQueryWithTwoFilters(String paramMobileSearchDataQuery, String paramArtifact1, String paramValue1, 
			String paramArtifact2, String paramValue2)
	{
		MyntraService parameterizedMobileSearchDataQueryWithTwoFiltersService = Myntra.getService(ServiceType.PORTAL_MOBILEAPPWEBSERVICES,
				APINAME.MOBILEAPPSEARCHDATAAPPLYTWOFILTERS,  init.Configurations, null, new String[] { paramMobileSearchDataQuery, paramArtifact1, paramValue1,
				paramArtifact2, paramValue2});
		System.out.println("\nPrinting MobileSearchService parameterizedMobileSearchDataQueryWithTwoFilters API URL : "+
				parameterizedMobileSearchDataQueryWithTwoFiltersService.URL);
		log.info("\nPrinting MobileSearchService parameterizedMobileSearchDataQueryWithTwoFilters API URL : "+
				parameterizedMobileSearchDataQueryWithTwoFiltersService.URL);
		
		System.out.println("\nPrinting MobileSearchService parameterizedMobileSearchDataQueryWithTwoFilters API Payload : \n\n"+
				parameterizedMobileSearchDataQueryWithTwoFiltersService.Payload);
		log.info("\nPrinting MobileSearchService parameterizedMobileSearchDataQueryWithTwoFilters API Payload : \n\n"+
				parameterizedMobileSearchDataQueryWithTwoFiltersService.Payload);
		
		return new RequestGenerator(parameterizedMobileSearchDataQueryWithTwoFiltersService);
	}
	
	/**
	 * This method is used to invoke MobileSearchService ParameterizedMobileSearchWithDataQueryWithAllFilters API
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
	 * @return RequestGenerator
	 */
	public RequestGenerator invokeParameterizedMobileSearchDataQueryWithAllFilters(String paramMobileSearchDataQuery, String paramArtifact1, String paramValue1, 
			String paramArtifact2, String paramValue2, String paramArtifact3, String paramValue3, String paramArtifact4, String paramValue4, 
			String paramArtifact5, String paramValue5)
	{
		MyntraService parameterizedMobileSearchDataQueryWithAllFiltersService = Myntra.getService(ServiceType.PORTAL_MOBILEAPPWEBSERVICES,
				APINAME.MOBILEAPPSEARCHDATAAPPLYALLFILTERS,  init.Configurations, null, new String[] { paramMobileSearchDataQuery, paramArtifact1, paramValue1,
				paramArtifact2, paramValue2, paramArtifact3, paramValue3, paramArtifact4, paramValue4, paramArtifact5, paramValue5,});
		System.out.println("\nPrinting MobileSearchService parameterizedMobileSearchDataQueryWithAllFilters API URL : "+
				parameterizedMobileSearchDataQueryWithAllFiltersService.URL);
		log.info("\nPrinting MobileSearchService parameterizedMobileSearchDataQueryWithAllFilters API URL : "+
				parameterizedMobileSearchDataQueryWithAllFiltersService.URL);
		
		System.out.println("\nPrinting MobileSearchService parameterizedMobileSearchDataQueryWithAllFilters API Payload : \n\n"+
				parameterizedMobileSearchDataQueryWithAllFiltersService.Payload);
		log.info("\nPrinting MobileSearchService parameterizedMobileSearchDataQueryWithAllFilters API Payload : \n\n"+
				parameterizedMobileSearchDataQueryWithAllFiltersService.Payload);
		
		return new RequestGenerator(parameterizedMobileSearchDataQueryWithAllFiltersService);
	}
	
	/**
	 * This method is used to invoke MobileSearchService parameterizedMobileSearchDataQueryWithSortBy API
	 * 
	 * @param paramMobileSearchDataQuery
	 * @param paramSortBy
	 * @return RequestGenerator
	 */
	public RequestGenerator invokeParameterizedMobileSearchDataQueryWithSortBy(String paramMobileSearchDataQuery, String paramSortBy)
	{
		MyntraService parameterizedMobileSearchDataQueryWithSortByService = Myntra.getService(ServiceType.PORTAL_MOBILEAPPWEBSERVICES,
				APINAME.MOBILEAPPSEARCHDATASORTBY,  init.Configurations, null, new String[] { paramMobileSearchDataQuery, paramSortBy });
		System.out.println("\nPrinting MobileSearchService parameterizedMobileSearchDataQueryWithSortBy API URL : "+
				parameterizedMobileSearchDataQueryWithSortByService.URL);
		log.info("\nPrinting MobileSearchService parameterizedMobileSearchDataQueryWithSortBy API URL : "+
				parameterizedMobileSearchDataQueryWithSortByService.URL);
		
		System.out.println("\nPrinting MobileSearchService parameterizedMobileSearchDataQueryWithSortBy API Payload : \n\n"+
				parameterizedMobileSearchDataQueryWithSortByService.Payload);
		log.info("\nPrinting MobileSearchService parameterizedMobileSearchDataQueryWithSortBy API Payload : \n\n"+
				parameterizedMobileSearchDataQueryWithSortByService.Payload);
		
		return new RequestGenerator(parameterizedMobileSearchDataQueryWithSortByService);
	}
	
}
