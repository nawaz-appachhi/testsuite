/*package com.myntra.apiTests.test.searchservice.topnavtests;

import java.util.List;

import org.apache.log4j.Logger;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import com.myntra.apiTests.test.searchservice.topnavtests.dataproviders.SearchServiceTopNavDP;
import com.myntra.apiTests.test.searchservice.topnavtests.helpers.SearchServiceTopNavTestsHelper;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.gandalf.RequestGenerator;

*//**
 * @author shankara.c
 *
 *//*
public class SearchServiceTopNavTests extends SearchServiceTopNavDP
{
	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(SearchServiceTopNavTests.class);
	static SearchServiceTopNavTestsHelper searchServiceTopNavTestsHelper = new SearchServiceTopNavTestsHelper();
	
	*//**
	 * This TestCase is used to invoke desktopTopNav using searchService getQueryAPI and verification for success response
	 * 
	 * @param paramRows
	 * @param paramReturnDocs
	 * @param paramIsFacet
	 *//*
	@Test(groups = { "Smoke", "Sanity", "Prodsanity", "Regression", "MiniRegression", "ExhaustiveRegression" },
			dataProvider="SearchServiceTopNavDP_getQueryAPI_invokeAllDesktopTopNav_verifySuccessResponse")
	public void SearchServiceTopNavTests_getQueryAPI_invokeAllDesktopTopNav_verifySuccessResponse(String paramRows, String paramReturnDocs, String paramIsFacet)	
	{
		RequestGenerator desktopTopNavServiceReqGen = searchServiceTopNavTestsHelper.invokeDesktopTopNavService();
		String desktopTopNavServiceResponse = desktopTopNavServiceReqGen.respvalidate.returnresponseasstring();
		//System.out.println("\nPrinting DesktopTopNavService API response : \n\n"+desktopTopNavServiceResponse+"\n");
		//log.info("\nPrinting DesktopTopNavService API response : \n\n"+desktopTopNavServiceResponse+"\n");
		
		AssertJUnit.assertEquals("DesktopTopNavService API is not working", 200, desktopTopNavServiceReqGen.response.getStatus());

		List<String> desktopTopNavList = searchServiceTopNavTestsHelper.getAllTopNavUrls(desktopTopNavServiceResponse);
		System.out.println("\nPrinting DesktopTopNav TotalCount : "+desktopTopNavList.size()+"\n");
		log.info("\nPrinting DesktopTopNav TotalCount : "+desktopTopNavList.size()+"\n");
		
		for(int i = 0; i < desktopTopNavList.size(); i++){
			
			String desktopTopNav = desktopTopNavList.get(i);
			
			if(!desktopTopNav.startsWith("/")) desktopTopNav = "/"+desktopTopNav;
			
			System.out.println("\nPrinting DesktopTopNav - "+i+": "+desktopTopNav+"\n");
	        log.info("\nPrinting DesktopTopNav - "+i+": "+desktopTopNav+"\n");
	        
			RequestGenerator getQueryGetReqServiceReqGen = searchServiceTopNavTestsHelper.invokeSearchServiceGetQueryGetReq(desktopTopNav, paramRows, paramReturnDocs, paramIsFacet);
			//String getQueryGetReqServiceResponse = getQueryGetReqServiceReqGen.respvalidate.returnresponseasstring();
			//System.out.println("\nPrinting SearchService getQueryGetReq API response : \n\n"+getQueryGetReqServiceResponse+"\n");
			//log.info("\nPrinting SearchService getQueryGetReq API response : \n\n"+getQueryGetReqServiceResponse+"\n");
			
			AssertJUnit.assertEquals("Failed to Invoke: "+getQueryGetReqServiceReqGen.getServiceundertest().URL, 200, getQueryGetReqServiceReqGen.response.getStatus());
		}
	}
	
	*//**
	 * This TestCase is used to invoke windowsPhoneTopNav using searchService getQueryAPI and verification for success response
	 * 
	 * @param paramRows
	 * @param paramReturnDocs
	 * @param paramIsFacet
	 *//*
	@Test(groups = { "Smoke", "Sanity", "Prodsanity", "Regression", "MiniRegression", "ExhaustiveRegression" },
			dataProvider="SearchServiceTopNavDP_getQueryAPI_invokeAllWindowsPhoneTopNav_verifySuccessResponse")
	public void SearchServiceTopNavTests_getQueryAPI_invokeAllWindowsPhoneTopNav_verifySuccessResponse(String paramRows, String paramReturnDocs, String paramIsFacet)	
	{
		RequestGenerator windowsPhoneTopNavServiceReqGen = searchServiceTopNavTestsHelper.invokeWindowsPhoneTopNavService();
		String windowsPhoneTopNavServiceResponse = windowsPhoneTopNavServiceReqGen.respvalidate.returnresponseasstring();
		//System.out.println("\nPrinting WindowsPhoneTopNavService API response : \n\n"+windowsPhoneTopNavServiceResponse+"\n");
		//log.info("\nPrinting WindowsPhoneTopNavService API response : \n\n"+windowsPhoneTopNavServiceResponse+"\n");
		
		AssertJUnit.assertEquals("WindowsPhoneTopNavService API is not working", 200, windowsPhoneTopNavServiceReqGen.response.getStatus());

		List<String> windowsPhoneTopNavList = searchServiceTopNavTestsHelper.getAllTopNavUrls(windowsPhoneTopNavServiceResponse);
		System.out.println("\nPrinting WindowsPhoneTopNav TotalCount : "+windowsPhoneTopNavList.size()+"\n");
		log.info("\nPrinting WindowsPhoneTopNav TotalCount : "+windowsPhoneTopNavList.size()+"\n");
		
		for(int i = 0; i < windowsPhoneTopNavList.size(); i++){
			
			String windowsPhoneTopNav = windowsPhoneTopNavList.get(i);
			
			if(!windowsPhoneTopNav.startsWith("/")) windowsPhoneTopNav = "/"+windowsPhoneTopNav;
			
	        System.out.println("\nPrinting WindowsPhoneTopNav - "+i+": "+windowsPhoneTopNav+"\n");
	        log.info("\nPrinting WindowsPhoneTopNav - "+i+": "+windowsPhoneTopNav+"\n");
	        
			RequestGenerator getQueryGetReqServiceReqGen = searchServiceTopNavTestsHelper.invokeSearchServiceGetQueryGetReq(windowsPhoneTopNav, paramRows, paramReturnDocs, paramIsFacet);
			//String getQueryGetReqServiceResponse = getQueryGetReqServiceReqGen.respvalidate.returnresponseasstring();
			//System.out.println("\nPrinting SearchService getQueryGetReq API response : \n\n"+getQueryGetReqServiceResponse+"\n");
			//log.info("\nPrinting SearchService getQueryGetReq API response : \n\n"+getQueryGetReqServiceResponse+"\n");
			
			AssertJUnit.assertEquals("Failed to Invoke: "+getQueryGetReqServiceReqGen.getServiceundertest().URL, 200, getQueryGetReqServiceReqGen.response.getStatus());
		}
	}
	
	*//**
	 * This TestCase is used to invoke androidPhoneTopNav using searchService getQueryAPI and verification for success response
	 * 
	 * @param paramRows
	 * @param paramReturnDocs
	 * @param paramIsFacet
	 *//*
	@Test(groups = { "Smoke", "Sanity", "Prodsanity", "Regression", "MiniRegression", "ExhaustiveRegression" },
			dataProvider="SearchServiceTopNavDP_getQuery_invokeAllAndroidPhoneTopNav_verifySuccessResponse")
	public void SearchServiceTopNavTests_getQuery_invokeAllAndroidPhoneTopNav_verifySuccessResponse(String paramRows, String paramReturnDocs, String paramIsFacet)	
	{
		RequestGenerator androidPhoneTopNavServiceReqGen = searchServiceTopNavTestsHelper.invokeAndroidPhoneTopNavService();
		String androidPhoneTopNavServiceResponse = androidPhoneTopNavServiceReqGen.respvalidate.returnresponseasstring();
		//System.out.println("\nPrinting AndroidPhoneTopNavService API response : \n\n"+windowsPhoneTopNavServiceResponse+"\n");
		//log.info("\nPrinting AndroidPhoneTopNavService API response : \n\n"+windowsPhoneTopNavServiceResponse+"\n");
		
		AssertJUnit.assertEquals("AndroidPhoneTopNavService API is not working", 200, androidPhoneTopNavServiceReqGen.response.getStatus());

		List<String> androidPhoneTopNavList = searchServiceTopNavTestsHelper.getAllTopNavUrls(androidPhoneTopNavServiceResponse);
		System.out.println("\nPrinting AndroidPhoneTopNav TotalCount : "+androidPhoneTopNavList.size()+"\n");
		log.info("\nPrinting AndroidPhoneTopNav TotalCount : "+androidPhoneTopNavList.size()+"\n");
		
		for(int i = 0; i < androidPhoneTopNavList.size(); i++){
			
			String androidPhoneTopNav = androidPhoneTopNavList.get(i);
			
			if(!androidPhoneTopNav.startsWith("/")) androidPhoneTopNav = "/"+androidPhoneTopNav;
			
	        System.out.println("\nPrinting AndroidPhoneTopNav - "+i+": "+androidPhoneTopNav+"\n");
	        log.info("\nPrinting AndroidPhoneTopNav - "+i+": "+androidPhoneTopNav+"\n");
	        
			RequestGenerator getQueryGetReqServiceReqGen = searchServiceTopNavTestsHelper.invokeSearchServiceGetQueryGetReq(androidPhoneTopNav, paramRows, paramReturnDocs, paramIsFacet);
			//String getQueryGetReqServiceResponse = getQueryGetReqServiceReqGen.respvalidate.returnresponseasstring();
			//System.out.println("\nPrinting SearchService getQueryGetReq API response : \n\n"+getQueryGetReqServiceResponse+"\n");
			//log.info("\nPrinting SearchService getQueryGetReq API response : \n\n"+getQueryGetReqServiceResponse+"\n");
			
			AssertJUnit.assertEquals("Failed to Invoke: "+getQueryGetReqServiceReqGen.getServiceundertest().URL, 200, getQueryGetReqServiceReqGen.response.getStatus());
		}
	}
	
	*//**
	 * This TestCase is used to invoke iosPhoneTopNav using searchService getQueryAPI and verification for success response
	 * 
	 * @param paramRows
	 * @param paramReturnDocs
	 * @param paramIsFacet
	 *//*
	@Test(groups = { "Smoke", "Sanity", "Prodsanity", "Regression", "MiniRegression", "ExhaustiveRegression" },
			dataProvider="SearchServiceTopNavDP_getQuery_invokeAllIOSPhoneTopNav_verifySuccessResponse")
	public void SearchServiceTopNavTests_getQuery_invokeAllIOSPhoneTopNav_verifySuccessResponse(String paramRows, String paramReturnDocs, String paramIsFacet)	
	{
		RequestGenerator iosPhoneTopNavServiceReqGen = searchServiceTopNavTestsHelper.invokeIOSPhoneTopNavService();
		String iosPhoneTopNavServiceResponse = iosPhoneTopNavServiceReqGen.respvalidate.returnresponseasstring();
		//System.out.println("\nPrinting IOSPhoneTopNavService API response : \n\n"+iosPhoneTopNavServiceResponse+"\n");
		//log.info("\nPrinting IOSPhoneTopNavService API response : \n\n"+iosPhoneTopNavServiceResponse+"\n");
		
		AssertJUnit.assertEquals("IOSPhoneTopNavService API is not working", 200, iosPhoneTopNavServiceReqGen.response.getStatus());

		List<String> iosPhoneTopNavList = searchServiceTopNavTestsHelper.getAllTopNavUrls(iosPhoneTopNavServiceResponse);
		System.out.println("\nPrinting IOSPhoneTopNav TotalCount : "+iosPhoneTopNavList.size()+"\n");
		log.info("\nPrinting IOSPhoneTopNav TotalCount : "+iosPhoneTopNavList.size()+"\n");
		
		for(int i = 0; i < iosPhoneTopNavList.size(); i++){
			
			String iosPhoneTopNav = iosPhoneTopNavList.get(i);
			
			if(!iosPhoneTopNav.startsWith("/")) iosPhoneTopNav = "/"+iosPhoneTopNav;
			
	        System.out.println("\nPrinting IOSPhoneTopNav - "+i+": "+iosPhoneTopNav+"\n");
	        log.info("\nPrinting IOSPhoneTopNav - "+i+": "+iosPhoneTopNav+"\n");
	        
			RequestGenerator getQueryGetReqServiceReqGen = searchServiceTopNavTestsHelper.invokeSearchServiceGetQueryGetReq(iosPhoneTopNav, paramRows, paramReturnDocs, paramIsFacet);
			//String getQueryGetReqServiceResponse = getQueryGetReqServiceReqGen.respvalidate.returnresponseasstring();
			//System.out.println("\nPrinting SearchService getQueryGetReq API response : \n\n"+getQueryGetReqServiceResponse+"\n");
			//log.info("\nPrinting SearchService getQueryGetReq API response : \n\n"+getQueryGetReqServiceResponse+"\n");
			
			AssertJUnit.assertEquals("Failed to Invoke: "+getQueryGetReqServiceReqGen.getServiceundertest().URL, 200, getQueryGetReqServiceReqGen.response.getStatus());
		}
	}
	
	*//**
	 * This TestCase is used to invoke mobileWebTopNav using searchService getQueryAPI and verification for success response
	 * 
	 * @param paramRows
	 * @param paramReturnDocs
	 * @param paramIsFacet
	 *//*
	@Test(groups = { "Smoke", "Sanity", "Prodsanity", "Regression", "MiniRegression", "ExhaustiveRegression" },
			dataProvider="SearchServiceTopNavDP_getQuery_invokeAllMobileWebTopNav_verifySuccessResponse")
	public void SearchServiceTopNavTests_getQuery_invokeAllMobileWebTopNav_verifySuccessResponse(String paramRows, String paramReturnDocs, String paramIsFacet)	
	{
		RequestGenerator mobileWebTopNavServiceReqGen = searchServiceTopNavTestsHelper.invokeMobileWebTopNavService();
		String mobileWebTopNavServiceResponse = mobileWebTopNavServiceReqGen.respvalidate.returnresponseasstring();
		//System.out.println("\nPrinting MobileWebTopNavService API response : \n\n"+mobileWebTopNavServiceResponse+"\n");
		//log.info("\nPrinting MobileWebTopNavService API response : \n\n"+mobileWebTopNavServiceResponse+"\n");
		
		AssertJUnit.assertEquals("MobileWebTopNavService API is not working", 200, mobileWebTopNavServiceReqGen.response.getStatus());

		List<String> mobileWebTopNavList = searchServiceTopNavTestsHelper.getAllTopNavUrls(mobileWebTopNavServiceResponse);
		System.out.println("\nPrinting MobileWebTopNav TotalCount : "+mobileWebTopNavList.size()+"\n");
		log.info("\nPrinting MobileWebTopNav TotalCount : "+mobileWebTopNavList.size()+"\n");
		
		for(int i = 0; i < mobileWebTopNavList.size(); i++){
			
			String mobileWebTopNav = mobileWebTopNavList.get(i);
			
			if(!mobileWebTopNav.startsWith("/")) mobileWebTopNav = "/"+mobileWebTopNav;
			
	        System.out.println("\nPrinting MobileWebTopNav - "+i+": "+mobileWebTopNav+"\n");
	        log.info("\nPrinting MobileWebTopNav - "+i+": "+mobileWebTopNav+"\n");
	        
			RequestGenerator getQueryGetReqServiceReqGen = searchServiceTopNavTestsHelper.invokeSearchServiceGetQueryGetReq(mobileWebTopNav, paramRows, paramReturnDocs, paramIsFacet);
			//String getQueryGetReqServiceResponse = getQueryGetReqServiceReqGen.respvalidate.returnresponseasstring();
			//System.out.println("\nPrinting SearchService getQueryGetReq API response : \n\n"+getQueryGetReqServiceResponse+"\n");
			//log.info("\nPrinting SearchService getQueryGetReq API response : \n\n"+getQueryGetReqServiceResponse+"\n");
			
			AssertJUnit.assertEquals("Failed to Invoke: "+getQueryGetReqServiceReqGen.getServiceundertest().URL, 200, getQueryGetReqServiceReqGen.response.getStatus());
		}
	}
}
*/