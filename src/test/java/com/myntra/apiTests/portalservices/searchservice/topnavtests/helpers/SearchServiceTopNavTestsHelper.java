package com.myntra.apiTests.portalservices.searchservice.topnavtests.helpers;

import java.util.ArrayList;
import java.util.List;

import com.myntra.apiTests.common.Myntra;
import net.minidev.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.jayway.jsonpath.JsonPath;
import com.myntra.lordoftherings.Initialize;
import com.myntra.apiTests.common.APINAME;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.apiTests.common.ServiceType;

/**
 * @author shankara.c
 *
 */
public class SearchServiceTopNavTestsHelper 
{
	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(SearchServiceTopNavTestsHelper.class);
	
	/**
	 * This method is used to invoke SearchService getQueryGetReq API
	 * 
	 * @param urlFromTheService
	 * @param paramRows
	 * @param paramReturnDocs
	 * @param paramIsFacet
	 * @return RequestGenerator
	 */
	/*public RequestGenerator invokeSearchServiceGetQueryGetReq(String urlFromTheService, String paramRows, String paramReturnDocs, String paramIsFacet)
	{
		MyntraService SearchServiceGetQueryGetReqService = Myntra.getService(ServiceType.PORTAL_SEARCHSERVICE, APINAME.GETQUERYGETREQ, init.Configurations);
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(SearchServiceGetQueryGetReqService.URL);
		stringBuffer.append(urlFromTheService);
		stringBuffer.append("?rows=");
		stringBuffer.append(paramRows);
		stringBuffer.append("&return_docs=");
		stringBuffer.append(paramReturnDocs);
		stringBuffer.append("&is_facet=");
		stringBuffer.append(paramIsFacet);
		
		SearchServiceGetQueryGetReqService.URL = stringBuffer.toString();
		
		System.out.println("\nPrinting SearchService getQueryGetReq API URL : "+SearchServiceGetQueryGetReqService.URL+"\n");
		log.info("\nPrinting SearchServiceGetQueryGetReq API URL : "+SearchServiceGetQueryGetReqService.URL+"\n");
		
		return new RequestGenerator(SearchServiceGetQueryGetReqService);
	}*/
	
	/**
	 * This method is used to invoke DesktopTopNavService API
	 * 
	 * @return RequestGenerator
	 */
	public RequestGenerator invokeDesktopTopNavService()
	{
		MyntraService desktopTopNavService = Myntra.getService(ServiceType.PORTAL_TOPNAV, APINAME.GETALLTOPNAV, init.Configurations);
		
		System.out.println("\nPrinting DesktopTopNavService API URL : "+desktopTopNavService.URL);
		log.info("\nPrinting DesktopTopNavService API URL : "+desktopTopNavService.URL);
		
		return new RequestGenerator(desktopTopNavService);
	}
	
	/**
	 * This method is used to invoke DesktopTopNavService API
	 * 
	 * @return RequestGenerator
	 */
	public RequestGenerator invokeWindowsPhoneTopNavService()
	{
		MyntraService windowsPhoneTopNavService = Myntra.getService(ServiceType.PORTAL_TOPNAV, APINAME.GETWINDOWSPHONETOPNAV, init.Configurations);
		
		System.out.println("\nPrinting WindowsPhoneTopNavService API URL : "+windowsPhoneTopNavService.URL);
		log.info("\nPrinting WindowsPhoneTopNavService API URL : "+windowsPhoneTopNavService.URL);
		
		return new RequestGenerator(windowsPhoneTopNavService);
	}
	
	/**
	 * This method is used to invoke AndroidPhoneTopNavService API
	 * 
	 * @return RequestGenerator
	 */
	public RequestGenerator invokeAndroidPhoneTopNavService()
	{
		MyntraService androidPhoneTopNavService = Myntra.getService(ServiceType.PORTAL_TOPNAV, APINAME.GETANDROIDPHONETOPNAV, init.Configurations);
		
		System.out.println("\nPrinting AndroidPhoneTopNavService API URL : "+androidPhoneTopNavService.URL);
		log.info("\nPrinting AndroidPhoneTopNavService API URL : "+androidPhoneTopNavService.URL);
		
		return new RequestGenerator(androidPhoneTopNavService);
	}
	
	/**
	 * This method is used to invoke IOSPhoneTopNavService API
	 * 
	 * @return RequestGenerator
	 */
	public RequestGenerator invokeIOSPhoneTopNavService()
	{
		MyntraService iOSPhoneTopNavService = Myntra.getService(ServiceType.PORTAL_TOPNAV, APINAME.GETIOSPHONETOPNAV, init.Configurations);
		
		System.out.println("\nPrinting IOSPhoneTopNavService API URL : "+iOSPhoneTopNavService.URL);
		log.info("\nPrinting IOSPhoneTopNavService API URL : "+iOSPhoneTopNavService.URL);
		
		return new RequestGenerator(iOSPhoneTopNavService);
	}
	
	/**
	 * This method is used to invoke MobileWebTopNavService API
	 * 
	 * @return RequestGenerator
	 */
	public RequestGenerator invokeMobileWebTopNavService()
	{
		MyntraService mobileWebTopNavService = Myntra.getService(ServiceType.PORTAL_TOPNAV, APINAME.GETMOBILEWEBTOPNAV, init.Configurations);
		
		System.out.println("\nPrinting MobileWebTopNavService API URL : "+mobileWebTopNavService.URL);
		log.info("\nPrinting MobileWebTopNavService API URL : "+mobileWebTopNavService.URL);
		
		return new RequestGenerator(mobileWebTopNavService);
	}
	
	public List<String> getAllTopNavUrls(String topNavResponse)
	{
		List<String> catagoryTopNavList = new ArrayList<String>();
		
		List<JSONObject> masterCatagories =  JsonPath.read(topNavResponse, "$..categories[*]");
		
		for(int i = 0; i < masterCatagories.size(); i++){
			
			String masterCatagoryUrl = String.valueOf(JsonPath.read(topNavResponse, "$..categories["+i+"].linkUrl")).replaceAll(".*/", "/");
			
			if(isValidTopNav(masterCatagoryUrl)) catagoryTopNavList.add(masterCatagoryUrl);
			
			List<JSONObject> subCatagories =  JsonPath.read(topNavResponse, "$..categories["+i+"].categories[*]");
			
			for(int j = 0; j < subCatagories.size(); j++){
				
				String subCatagoryUrl = String.valueOf(JsonPath.read(topNavResponse, "$..categories["+i+"].categories["+j+"].linkUrl")).replaceAll(".*/", "/");
				
				if(isValidTopNav(subCatagoryUrl)) catagoryTopNavList.add(subCatagoryUrl);
				
				List<JSONObject> childCatagories =  JsonPath.read(topNavResponse, "$..categories["+i+"].categories["+j+"].categories[*]");
				
				for(int k = 0; k < childCatagories.size(); k++){
					
					String childCatagoryUrl = String.valueOf(JsonPath.read(topNavResponse, "$..categories["+i+"].categories["+j+"].categories["+k+"].linkUrl")).replaceAll(".*/", "/");
					
					if(isValidTopNav(childCatagoryUrl)) catagoryTopNavList.add(childCatagoryUrl);
				}
			}
		}
		
		return catagoryTopNavList;
	}
	
	private boolean isValidTopNav(String paramTopNavUrl)
	{
		if(StringUtils.isAnyEmpty(paramTopNavUrl.trim()) || StringUtils.isAnyBlank(paramTopNavUrl.trim())) return false;
			
		if(paramTopNavUrl.trim().matches("[/\\\\]")) return false;
		
		if(paramTopNavUrl.trim().matches(".*/[^\\w\\s-]")) return false;
		
		if(paramTopNavUrl.trim().matches("^[^\\w\\s-]")) return false;
		
		return true;
	}
	
}
