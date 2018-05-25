package com.myntra.apiTests.portalservices.configservice.pageconfig;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import com.myntra.apiTests.common.Myntra;
import com.myntra.apiTests.portalservices.commons.CommonUtils;
import org.apache.log4j.Logger;

import com.jayway.jsonpath.JsonPath;
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
public class PageConfigServiceApiHelper extends CommonUtils
{
	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(PageConfigServiceApiHelper.class);

	/**
	 * get all page types
	 * @author jhansi.bai
	 * @return
	 */
	public List<Integer> getPageTypes()		{
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_CONFIGURATION, APINAME.GETPAGETYPE,
				init.Configurations);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);
		//		System.out.println(req.respvalidate.returnresponseasstring());	
		List<Integer> pageTypesList= JsonPath.read(req.respvalidate.returnresponseasstring(), "$..pageType"); 
		pageTypesList.removeAll(Collections.singleton(null));  
		return pageTypesList;
	}

	/**
	 * get all widget ids
	 * @author jhansi.bai
	 * @return
	 */
	public List<String> getWidgetIds()		{
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_CONFIGURATION, APINAME.GETALLWIDGETS,
				init.Configurations);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);
		//	System.out.println(req.respvalidate.returnresponseasstring());	
		List<String> widgetIdsList= JsonPath.read(req.respvalidate.returnresponseasstring(), "$..widgetId"); 
		widgetIdsList.removeAll(Collections.singleton(null));  
		return widgetIdsList;
	}

	/**
	 * get all page versions
	 * @author jhansi.bai
	 * @return
	 */
	public List<Integer> getAllPageVersions()		{
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_CONFIGURATION, APINAME.GETALLPAGEVERSIONS,
				init.Configurations);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);
		//	System.out.println(req.respvalidate.returnresponseasstring());	
		List<Integer> pageVersionsList= JsonPath.read(req.respvalidate.returnresponseasstring(), "$..version"); 
		return pageVersionsList;
	}

	/**
	 * get all page versions
	 * @author jhansi.bai
	 * @return
	 */
	public ArrayList<List<String>> getAllPageVersionsDetails()		{
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_CONFIGURATION, APINAME.GETALLPAGEVERSIONS,
				init.Configurations);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);
		//	System.out.println(req.respvalidate.returnresponseasstring());	
		ArrayList<List<String>> pageVersionsDtlsList = new ArrayList<List<String>>();
		List<String> createdBy = JsonPath.read(req.respvalidate.returnresponseasstring(), "$..createdBy");
		List<String> version = JsonPath.read(req.respvalidate.returnresponseasstring(), "$..version");
		List<String> label = JsonPath.read(req.respvalidate.returnresponseasstring(), "$..label");
		pageVersionsDtlsList.add(createdBy);
		pageVersionsDtlsList.add(version);
		pageVersionsDtlsList.add(label);
		return pageVersionsDtlsList;
	}

	/**
	 * Get expected request
	 * @author jhansi.bai
	 * @param apiName
	 * @param param
	 * @return
	 */
	public RequestGenerator getRequest(APINAME apiName){
		MyntraService service = Myntra.getService(ServiceType.PORTAL_CONFIGURATION,
				apiName, init.Configurations);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);
		return req;
	}

	/**
	 * Get expected request
	 * @author jhansi.bai
	 * @param apiName
	 * @param param
	 * @return
	 */
	public RequestGenerator getRequest(APINAME apiName, String param){
		MyntraService service = Myntra.getService(ServiceType.PORTAL_CONFIGURATION,
				apiName, init.Configurations);
		APIUtilities utilities = new APIUtilities();
		service.URL = utilities.prepareparameterizedURL(service.URL, param);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);
		return req;
	}

	/**
	 * Get expected request
	 * @author jhansi.bai
	 * @param apiName
	 * @param param
	 * @return
	 */
	public RequestGenerator getRequest(APINAME apiName, String param1, String param2){
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_CONFIGURATION, apiName,
				init.Configurations, new String[] { param1, param2 },
				PayloadType.JSON, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);
		/*	System.out.println(service.URL);
		System.out.println(service.Payload);*/
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
				ServiceType.PORTAL_CONFIGURATION, apiName,
				init.Configurations, new String[] { param1, param2, param3 },
				PayloadType.JSON, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);
		/*	System.out.println(service.URL);
		System.out.println(service.Payload);*/
		return req;
	}

	/**
	 * Get expected request
	 * @author jhansi.bai
	 * @param apiName
	 * @param param
	 * @return
	 */
	public RequestGenerator getRequest(APINAME apiName, String param1, String param2, String param3,
			String param4, String param5, String param6){		
		MyntraService service = Myntra.getService(ServiceType.PORTAL_CONFIGURATION,
				apiName, init.Configurations, new String[] {  param1, param2, param3, param4, param5, param6 },	
				PayloadType.JSON, PayloadType.JSON);
		System.out.println(service.URL);
		System.out.println(service.Payload);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);
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
		MyntraService service = Myntra.getService(ServiceType.PORTAL_CONFIGURATION,
				apiName, init.Configurations, new String[] {  param1, param2, param3}, new String[] { param2 },	
				PayloadType.JSON, PayloadType.JSON);
		System.out.println(service.URL);
		System.out.println(service.Payload);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);
		return req;
	}


	/**
	 * Get expected request
	 * @author jhansi.bai
	 * @param apiName
	 * @param param
	 * @return
	 */
	public RequestGenerator getQuerryPayLoadRequest1(APINAME apiName, String param1, String param2, String param3){		
		MyntraService service = Myntra.getService(ServiceType.PORTAL_CONFIGURATION,
				apiName, init.Configurations, new String[] {  param1, param2}, new String[] { param3 },	
				PayloadType.JSON, PayloadType.JSON);
		System.out.println(service.URL);
		System.out.println(service.Payload);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);
		return req;
	}

	/**
	 * Get expected request
	 * @author jhansi.bai
	 * @param apiName
	 * @param param
	 * @return
	 */
	public RequestGenerator getQuerryPayLoadRequest1(APINAME apiName, String param1, String param2){		
		MyntraService service = Myntra.getService(ServiceType.PORTAL_CONFIGURATION,
				apiName, init.Configurations, new String[] { param1}, new String[] { param2 },	
				PayloadType.JSON, PayloadType.JSON);
		System.out.println(service.URL);
		System.out.println(service.Payload);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);
		return req;
	}

	/**
	 * Get expected request
	 * @author jhansi.bai
	 * @param apiName
	 * @param param
	 * @return
	 */
	public RequestGenerator getQuerryPayLoadAdHeaderRequest(APINAME apiName, String param1, String param2, String param3, String param4){		
		MyntraService service = Myntra.getService(ServiceType.PORTAL_CONFIGURATION,
				apiName, init.Configurations, new String[] {  param1, param2, param3}, new String[] { param4 },	
				PayloadType.JSON, PayloadType.JSON);
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("pageURL", "men-footwear");

		System.out.println(service.URL);
		System.out.println(service.Payload);
		RequestGenerator req = new RequestGenerator(service, headers);
		log.info(service.URL);
		return req;
	}
	
	/**
	 * Get expected request
	 * @author jhansi.bai
	 * @param apiName
	 * @param param
	 * @return
	 */
	public RequestGenerator getQuerryPayLoadAdHeadersRequest(APINAME apiName, String param1, String param2, String param3){		
		MyntraService service = Myntra.getService(ServiceType.PORTAL_CONFIGURATION,
				apiName, init.Configurations, new String[] {  param1, param2 }, new String[] { param3 },	
				PayloadType.JSON, PayloadType.JSON);
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("pageURL", "men-footwear");
		headers.put("widgetDataRequired", "true");

		System.out.println(service.URL);
		System.out.println(service.Payload);
		RequestGenerator req = new RequestGenerator(service, headers);
		log.info(service.URL);
		return req;
	}
	
	/**
	 * Get expected request
	 * @author jhansi.bai
	 * @param apiName
	 * @param param
	 * @return
	 */
	public RequestGenerator getQuerryPayLoadAdHeadersRequest(APINAME apiName, String param1, String param2, String param3, String param4){		
		MyntraService service = Myntra.getService(ServiceType.PORTAL_CONFIGURATION,
				apiName, init.Configurations, new String[] {  param1, param2, param3 }, new String[] { param4 },	
				PayloadType.JSON, PayloadType.JSON);
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("pageURL", "men-footwear");
		headers.put("widgetDataRequired", "true");

		System.out.println(service.URL);
		System.out.println(service.Payload);
		RequestGenerator req = new RequestGenerator(service, headers);
		log.info(service.URL);
		return req;
	}


	/**
	 * Get expected request
	 * @author jhansi.bai
	 * @param apiName
	 * @param param
	 * @return
	 */
	public RequestGenerator getQuerryPayLoadAdHeaderRequest(APINAME apiName, String param1, String param2, String param3){		
		MyntraService service = Myntra.getService(ServiceType.PORTAL_CONFIGURATION,
				apiName, init.Configurations, new String[] {  param1, param2}, new String[] { param3 },	
				PayloadType.JSON, PayloadType.JSON);
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("pageURL", "men-footwear");

		System.out.println(service.URL);
		System.out.println(service.Payload);
		RequestGenerator req = new RequestGenerator(service, headers);
		log.info(service.URL);
		return req;
	}

	//-------------------------------------------------------------------------------------------

	public static String statusBasePath = "status.";
	public static String dataBasePath = "data.";
	public static String widgetBasePath = "data.widgets.";
	public static String datAPageAVersionBasePath = "data.pageVersion.";

	/**
	 * All page status nodes
	 * @author jhansi.bai
	 * @return
	 */
	public static List<String> getAllPageStatusNodes (){
		List<String> addNodes = new ArrayList<>();
		addNodes.add(statusBasePath+"statusCode");
		addNodes.add(statusBasePath+"statusMessage");
		addNodes.add(statusBasePath+"statusType");
		addNodes.add(statusBasePath+"totalCount");
		return addNodes;		
	}	

	/**
	 * All Widget data nodes
	 * @author jhansi.bai
	 * @return
	 */
	public static List<String> getAllWidgetDataNodes (){
		List<String> addNodes = new ArrayList<>();
		addNodes.add(dataBasePath+"id");
		addNodes.add(dataBasePath+"widgetId");
		addNodes.add(dataBasePath+"data");
		return addNodes;		
	}

	/**
	 * All Widget data nodes
	 * @author jhansi.bai
	 * @return
	 */
	public static List<String> getAllWidgetsNodes (){
		List<String> addNodes = new ArrayList<>();
		addNodes.add(widgetBasePath+"id");
		addNodes.add(widgetBasePath+"widgetId");
		addNodes.add(widgetBasePath+"data");
		return addNodes;		
	}
	/**
	 * All Widget data nodes excluding 'data' node
	 * @author jhansi.bai
	 * @return
	 */
	public static List<String> getAllIdAdWidgetIdNodes (){
		List<String> addNodes = new ArrayList<>();
		addNodes.add(dataBasePath+"id");
		addNodes.add(dataBasePath+"widgetId");
		return addNodes;		
	}

	/**
	 * Get all the page versions associated with a widget given a widgetId data nodes
	 * @author jhansi.bai
	 * @return
	 */
	public static List<String> getAllPageVersionsWithWidgetDataNodes(){
		List<String> addNodes = new ArrayList<>();
		addNodes.add(dataBasePath+"id");
		addNodes.add(dataBasePath+"createdBy");
		addNodes.add(dataBasePath+"createdOn");
		addNodes.add(dataBasePath+"version");
		addNodes.add(dataBasePath+"label");
		addNodes.add(dataBasePath+"isEnabled");
		return addNodes;		
	}

	/**
	 * Get all the page version data nodes
	 * @author jhansi.bai
	 * @return
	 */
	public static List<String> getAllPageMapPageVersionsDataNodes(){
		List<String> addNodes = new ArrayList<>();
		addNodes.add(datAPageAVersionBasePath+"id");
		addNodes.add(datAPageAVersionBasePath+"createdBy");
		addNodes.add(datAPageAVersionBasePath+"createdOn");
		addNodes.add(datAPageAVersionBasePath+"version");
		addNodes.add(datAPageAVersionBasePath+"label");
		addNodes.add(datAPageAVersionBasePath+"isEnabled");
		return addNodes;		
	}

	/**
	 * Get all the page mapping for version data nodes
	 * @author jhansi.bai
	 * @return
	 */
	public static List<String> getAllPageMapVersionsDataNodes(){
		List<String> addNodes = new ArrayList<>();
		addNodes.add(dataBasePath+"id");
		addNodes.add(dataBasePath+"pageType");
		addNodes.add(dataBasePath+"pageVariant");
		addNodes.add(dataBasePath+"pageVersion");
		return addNodes;		
	}

	/**
	 * Get all the page mapping for type and url data nodes
	 * @author jhansi.bai
	 * @return
	 */
	public static List<String> getAllPageTypeDataNodes(){
		List<String> addNodes = new ArrayList<>();
		addNodes.add(dataBasePath+"id");
		addNodes.add(dataBasePath+"pageType");
		return addNodes;		
	}	
}
