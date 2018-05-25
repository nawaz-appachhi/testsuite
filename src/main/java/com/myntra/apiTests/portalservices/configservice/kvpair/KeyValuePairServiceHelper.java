package com.myntra.apiTests.portalservices.configservice.kvpair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.myntra.apiTests.common.Myntra;
import com.myntra.apiTests.portalservices.commons.CommonUtils;
import org.apache.commons.lang.ArrayUtils;
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
public class KeyValuePairServiceHelper extends CommonUtils
{
	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(KeyValuePairServiceHelper.class);
			
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
		header.put("keyList", ArrayUtils.toString(keysArray));		
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
	public RequestGenerator getRequest(APINAME apiName, String param1, String param2, String param3, String param4, String param5, String param6){		
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_CONFIGURATION, apiName,
				init.Configurations, new String[] { param1, param2, param3, param4, param5, param6 },
				PayloadType.JSON, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);
		/*	System.out.println(service.URL);
		System.out.println(service.Payload);*/
		return req;
	}
	

	
	//---------------------------------------------------------------------------------------------------------
	
	public static String statusBasePath = "status.";
	public static String dataBasePath = "data.";

	/**
	 * key value pair status nodes
	 * @author jhansi.bai
	 * @return
	 */
	public static List<String> keyValuePairStatusNodes (){
		List<String> addNodes = new ArrayList<>();
		addNodes.add(statusBasePath+"statusCode");
		addNodes.add(statusBasePath+"statusMessage");
		addNodes.add(statusBasePath+"statusType");
		addNodes.add(statusBasePath+"totalCount");
		return addNodes;		
	}
	
	/**
	 * key value pair data nodes
	 * @author jhansi.bai
	 * @return
	 */
	public static List<String> keyValuePairDataNodes (){
		List<String> addNodes = new ArrayList<>();
		addNodes.add(dataBasePath+"key");
		addNodes.add(dataBasePath+"value");
		addNodes.add(dataBasePath+"description");
		return addNodes;		
	}
	

	/**
	 * keys
	 * @author jhansi.bai
	 * @param apiName
	 * @param param
	 * @return
	 */
	public List<String> getAllKeys()
	{
		List<String> allKeys = new ArrayList<>();
		RequestGenerator req = getRequest(APINAME.GETALLKEYVALUEPAIR);
	//	System.out.println(req.respvalidate.returnresponseasstring());
		allKeys = JsonPath.read(req.respvalidate.returnresponseasstring(), "$.data..key"); 
		System.out.println(allKeys);
		return allKeys;
	}
	
	/**
	 * widget keys
	 * @author jhansi.bai
	 * @param apiName
	 * @param param
	 * @return
	 */
	public List<String> getAllWidgetKeys()
	{
		List<String> allKeys = new ArrayList<>();
		RequestGenerator req = getRequest(APINAME.GETALLWIDGETKEYVALUEPAIR);
		allKeys = JsonPath.read(req.respvalidate.returnresponseasstring(), "$.data..key"); 
		System.out.println(allKeys);
		return allKeys;
	}
}
