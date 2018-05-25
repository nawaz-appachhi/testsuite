package com.myntra.apiTests.portalservices.configservice.abtest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.myntra.apiTests.common.Myntra;
import com.myntra.apiTests.portalservices.commons.CommonUtils;
import org.apache.commons.lang.ArrayUtils;
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
public class ABtestServiceHelper extends CommonUtils
{
	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(ABtestServiceHelper.class);
	
	/**
	 * Get expected request
	 * @author jhansi.bai
	 * @param apiName
	 * @param param
	 * @return
	 */
	public RequestGenerator getQueryRequest(APINAME apiName, String param){
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
	public RequestGenerator getHeaderRequest(APINAME apiName, String keysArray){		
		MyntraService service = Myntra.getService(ServiceType.PORTAL_CONFIGURATION,
				apiName, init.Configurations);
		HashMap<String, String> header = new HashMap<String, String>();
		header.put("abtestList", ArrayUtils.toString(keysArray));		
		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service, header);
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
	public RequestGenerator getQuerryPayLoadRequest(APINAME apiName, String param1){		
		MyntraService service = Myntra.getService(ServiceType.PORTAL_CONFIGURATION,
				apiName, init.Configurations, new String[] {}, new String[] { param1 },	
				PayloadType.JSON, PayloadType.JSON);
		System.out.println(service.URL);
		//		System.out.println(service.Payload);
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
	public RequestGenerator getQuerryPayLoadRequest(APINAME apiName, String param1, String param2){		
		MyntraService service = Myntra.getService(ServiceType.PORTAL_CONFIGURATION,
				apiName, init.Configurations, new String[] {  param1 }, new String[] { param2 },	
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
				apiName, init.Configurations, new String[] {  param1, param2 }, new String[] { param3 },	
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
	public RequestGenerator getRequest(APINAME apiName, String param1){		
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_CONFIGURATION, apiName,
				init.Configurations, new String[] { param1},
				PayloadType.JSON, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);
		System.out.println(service.URL);
		//	System.out.println(service.Payload);
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
				init.Configurations, new String[] { param1, param2},
				PayloadType.JSON, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);
		System.out.println(service.URL);
		//	System.out.println(service.Payload);
		return req;
	}

	/**
	 * Get expected request
	 * @author jhansi.bai
	 * @param apiName
	 * @param param
	 * @return
	 */
	public RequestGenerator getRequest(APINAME apiName, String param1, String param2, String param3, String param4, String param5, 
			String param6, String param7, String param8, String param9, String param10, String param11, String param12,
			String param13, String param14, String param15, String param16, String param17, String param18){		
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_CONFIGURATION, apiName,
				init.Configurations, new String[] { param1, param2, param3, param4, param5, param6, param7, param8, param9, param10, 
						param11, param12, param13, param14, param15, param16, param17, param18},
						PayloadType.JSON, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);
		System.out.println(service.URL);
		//	System.out.println(service.Payload);
		return req;
	}
	
	//pageconfigurator
	
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


	//---------------------------------------------------------------------------------------------------------

	public static String statusBasePath = "status.";
	public static String dataBasePath = "data.";
	public static String variatBasePath = "variations.";
	public static String variantPath = dataBasePath+variatBasePath;

	/**
	 * AB tests status nodes
	 * @author jhansi.bai
	 * @return
	 */
	public static List<String> abTestsMainNodes (){
		List<String> addNodes = new ArrayList<>();
		addNodes.add("status");
		addNodes.add("mab");
		addNodes.add("gaCall");
		addNodes.add("data");
		return addNodes;		
	}

	/**
	 * AB tests status nodes
	 * @author jhansi.bai
	 * @return
	 */
	public static List<String> abTestsStatusNodes (){
		List<String> addNodes = new ArrayList<>();
		addNodes.add(statusBasePath+"statusCode");
		addNodes.add(statusBasePath+"statusMessage");
		addNodes.add(statusBasePath+"statusType");
		addNodes.add(statusBasePath+"totalCount");
		return addNodes;		
	}

	/**
	 * AB tests data nodes
	 * @author jhansi.bai
	 * @return
	 */
	public static List<String> abTestsDataNodes (){
		List<String> addNodes = new ArrayList<>();
		addNodes.add(dataBasePath+"variations");
		addNodes.add(dataBasePath+"auditLogs");
		addNodes.add(dataBasePath+"name");
		addNodes.add(dataBasePath+"isEnabled");
		addNodes.add(dataBasePath+"gaSlot");
		addNodes.add(dataBasePath+"omniSlot");
		addNodes.add(dataBasePath+"filters");
		addNodes.add(dataBasePath+"segmentationAlgo");
		addNodes.add(dataBasePath+"sourceType");
		addNodes.add(dataBasePath+"isCompleted");
		addNodes.add(dataBasePath+"apiVersion");
		return addNodes;		
	}

	/**
	 * AB tests variants data nodes
	 * @author jhansi.bai
	 * @return
	 */
	public static List<String> abTestsVariantsDataNodes (){
		List<String> addNodes = new ArrayList<>();
		addNodes.add("abTestName");
		addNodes.add("segmentValue");
		addNodes.add("cookieValue");
		return addNodes;		
	}

	/**
	 * AB tests data veriant nodes
	 * @author jhansi.bai
	 * @return
	 */
	public static List<String> abTestsDataVariantNodes (){
		List<String> addNodes = new ArrayList<>();
		addNodes.add(variantPath+"name");
		addNodes.add(variantPath+"percentProbability");
		addNodes.add(variantPath+"inlineHtml");
		addNodes.add(variantPath+"finalVariant");
		addNodes.add(variantPath+"jsFile");
		addNodes.add(variantPath+"cssFile");
		addNodes.add(variantPath+"configJson");
		addNodes.add(variantPath+"algoConfigJson");
		return addNodes;		
	}

	/**
	 * AB tests data veriant nodes
	 * @author jhansi.bai
	 * @return
	 */
	public static List<String> abTestsDataUpdateVariantNodes (){
		List<String> addNodes = new ArrayList<>();
		addNodes.add(variantPath+"name");
		addNodes.add(variantPath+"percentProbability");
		addNodes.add(variantPath+"inlineHtml");
		addNodes.add(variantPath+"finalVariant");
		addNodes.add(variantPath+"name");
		addNodes.add(variantPath+"percentProbability");
		addNodes.add(variantPath+"inlineHtml");
		addNodes.add(variantPath+"finalVariant");
		return addNodes;		
	}

	/**
	 * AB tests data nodes
	 * @author jhansi.bai
	 * @return
	 */
	public static List<String> abTestsNodes (){
		List<String> addNodes = new ArrayList<>();
		addNodes.add("variations");
		addNodes.add("auditLogs");
		addNodes.add("name");
		addNodes.add("isEnabled");
		addNodes.add("gaSlot");
		addNodes.add("omniSlot");
		addNodes.add("filters");
		addNodes.add("segmentationAlgo");
		addNodes.add("sourceType");
		addNodes.add("isCompleted");
		addNodes.add("apiVersion");
		return addNodes;		
	}


	/**
	 * AB tests data veriant nodes
	 * @author jhansi.bai
	 * @return
	 */
	public static List<String> abTestsVariantNodes (){
		List<String> addNodes = new ArrayList<>();
		addNodes.add(variatBasePath+"name");
		addNodes.add(variatBasePath+"percentProbability");
		addNodes.add(variatBasePath+"inlineHtml");
		addNodes.add(variatBasePath+"finalVariant");
		addNodes.add(variatBasePath+"jsFile");
		addNodes.add(variatBasePath+"cssFile");
		addNodes.add(variatBasePath+"configJson");
		addNodes.add(variatBasePath+"algoConfigJson");
		return addNodes;		
	}

	/**
	 * AB tests data name node
	 * @author jhansi.bai
	 * @return
	 */
	public static List<String> abTestsNameNode(){
		List<String> addNodes = new ArrayList<>();
		addNodes.add(dataBasePath+"name");
		return addNodes;		
	}	

	/**
	 * AB tests data name node
	 * @author jhansi.bai
	 * @return
	 */
	public static List<String> abTestsVariantNameNodes(){
		List<String> addNodes = new ArrayList<>();
		addNodes.add(variantPath+"name");
		addNodes.add(variantPath+"name");
		return addNodes;		
	}	
	
	//pageconfigurator
	
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
	
	public static String widgetBasePath = "data.widgets.";
	
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
}