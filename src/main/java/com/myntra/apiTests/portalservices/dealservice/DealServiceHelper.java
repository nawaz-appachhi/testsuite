package com.myntra.apiTests.portalservices.dealservice;

import java.util.ArrayList;
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
public class DealServiceHelper extends CommonUtils
{

	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(DealServiceHelper.class);
			
	/**
	 * Get expected request
	 * @author jhansi.bai
	 * @param apiName
	 * @param param
	 * @return
	 */
	public RequestGenerator getRequest(APINAME apiName, String  param){
		MyntraService service = Myntra.getService(ServiceType.PORTAL_DEALSSERVICE,
				apiName, init.Configurations, new String[] { param },	
				PayloadType.JSON, PayloadType.JSON);
		/*System.out.println(service.URL);
		System.out.println(service.Payload);*/
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
	public RequestGenerator getUrlRequest(APINAME apiName, String param){
		MyntraService service = Myntra.getService(ServiceType.PORTAL_DEALSSERVICE,
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
	public RequestGenerator getRequest(APINAME apiName, String name, String desc, String dealType, String startTime, 
			String endTime, String visible, String state, String discPercent, String banner, String styleId, String channelType){
		MyntraService service = Myntra.getService(ServiceType.PORTAL_DEALSSERVICE,
				apiName, init.Configurations, new String[] { name, desc, dealType, startTime, endTime, visible, state,
																discPercent, banner, styleId, channelType},	
				PayloadType.JSON, PayloadType.JSON);
		System.out.println(service.URL);
		System.out.println("Create New Deal Payload ----> "+service.Payload);
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
	public RequestGenerator getRequest(APINAME apiName, String name, String desc, String dealType, String startTime, 
			String endTime, String visible, String state, String discPercent, String banner, String styleId){
		MyntraService service = Myntra.getService(ServiceType.PORTAL_DEALSSERVICE,
				apiName, init.Configurations, new String[] { name, desc, dealType, startTime, endTime, visible, state,
																discPercent, banner, styleId},	
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
	public RequestGenerator getRequest(APINAME apiName, String name, String desc, String dealType, String startTime, 
			String endTime, String visible, String state, String discPercent, String banner, String styleId1,  String styleId2,  
			String styleId3, String channelType1, String channelType2, String channelType3){
		MyntraService service = Myntra.getService(ServiceType.PORTAL_DEALSSERVICE,
				apiName, init.Configurations, new String[] { name, desc, dealType, startTime, endTime, visible, state,
				discPercent, banner, styleId1, styleId2, styleId3, channelType1, channelType2, channelType3},	
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
	public RequestGenerator getRequest(APINAME apiName, String urlParam, String name, String desc, String dealType, String discPercent, String banner){
		MyntraService service = Myntra.getService(ServiceType.PORTAL_DEALSSERVICE,
				apiName, init.Configurations, new String[] { urlParam, name, desc, dealType, discPercent, banner}, new String[] { urlParam },	
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
	public RequestGenerator getRequest(APINAME apiName, String name, String desc, String dealType, String startTime, 
			String endTime, String visible, String state, String discPercent, String banner, String styleId, 
			String channelType, String status, String errorMessage){
		MyntraService service = Myntra.getService(ServiceType.PORTAL_DEALSSERVICE,
				apiName, init.Configurations, new String[] { name, desc, dealType, startTime, endTime, visible, state,
																discPercent, banner, styleId, channelType},	
				PayloadType.JSON, PayloadType.JSON);
		/*System.out.println(service.URL);
		System.out.println(service.Payload);*/
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
		MyntraService service = Myntra.getService(ServiceType.PORTAL_DEALSSERVICE,
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
	public RequestGenerator getQuerryPayLoadRequest(APINAME apiName, String param1, String param2, String param3, 
			String param4, String param5, String param6, String param7, String param8, String param9,  String param10){		
		MyntraService service = Myntra.getService(ServiceType.PORTAL_DEALSSERVICE,
				apiName, init.Configurations, new String[] {  param1, param2, param3, param4, param5, param6, param7, 
				param8, param9, param10}, new String[] { param1 },	
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
	public RequestGenerator getRequest(APINAME apiName, String param1, String param2, String param3){		
		MyntraService service = Myntra.getService(ServiceType.PORTAL_DEALSSERVICE,
				apiName, init.Configurations, new String[] { param1, param2, param3},	
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
	public RequestGenerator getRequest(APINAME apiName, int state){		
		String payload = "{ \"state\":"+state+" }";
		MyntraService service = Myntra.getService(ServiceType.PORTAL_DEALSSERVICE,
				apiName, init.Configurations, payload);
		System.out.println(service.URL);
		service.Payload = payload;
		System.out.println(service.Payload);	
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);
		System.out.println(req.returnresponseasstring());
		return req;
	}

	
	/**
	 * Get expected request
	 * @author jhansi.bai
	 * @param apiName
	 * @param param
	 * @return
	 */
	public RequestGenerator getRequest(APINAME apiName, String param1, String param2, String param3, String param4){		
		MyntraService service = Myntra.getService(ServiceType.PORTAL_DEALSSERVICE,
				apiName, init.Configurations, new String[] { param1, param2, param3, param4},	
				PayloadType.JSON, PayloadType.JSON);
		//System.out.println(service.URL);
	//	System.out.println(service.Payload);
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
	public RequestGenerator getRequest(APINAME apiName){
		String payload = "{}";
		MyntraService service = Myntra.getService(ServiceType.PORTAL_DEALSSERVICE,
				apiName, init.Configurations, payload);
		System.out.println(service.URL);
		service.Payload = payload;
		System.out.println("Get All Deal Payload :--> "+service.Payload);	
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);
		System.out.println(req.returnresponseasstring());
		return req;
	}
	
	
	
	/**
	 * Get deal ids
	 * @author jhansi.bai
	 * @param apiName
	 * @param param
	 * @return
	 */
	public List<Integer> getDealIds(String visible, String dataType, String state, String channel)
	{
		List<Integer> dealIds = new ArrayList<>();
		RequestGenerator req = getRequest(APINAME.GETALLDEALS, visible, dataType, state, channel);
	//	System.out.println("Resposne : "+req.respvalidate.returnresponseasstring());
		dealIds = JsonPath.read(req.respvalidate.returnresponseasstring(), "$[*].id"); 
		return dealIds;
	}
	
	/**
	 * Get deal endtime
	 * @author jhansi.bai
	 * @param apiName
	 * @param param
	 * @return
	 */
	public List<Integer> getDealEndTimes(String visible, String dataType, String state, String channel)
	{
		List<Integer> dealEndTimes = new ArrayList<>();
		RequestGenerator req = getRequest(APINAME.GETALLDEALS, visible, dataType, state, channel);
		dealEndTimes = JsonPath.read(req.respvalidate.returnresponseasstring(), "$[*].endTime"); 
		return dealEndTimes;
	}
	
	//-------------------------------------------------------------------------------------------------------------------
	

	static String dealStylesPath = "dealStyles.";
	static String dealChannelsPath = "dealChannels.";
	static String updateDealPath = ".";
	
	/**
	 * deal nodes
	 * @author jhansi.bai
	 * @return
	 */
	public static List<String> getDealNodes (){
		List<String> addNodes = new ArrayList<>();
		addNodes.add("id");
		addNodes.add("createdOn");
		addNodes.add("name");
		addNodes.add("discountId");
		addNodes.add("description");
		addNodes.add("dealType");
		addNodes.add("startTime");
		addNodes.add("endTime");
		addNodes.add("discountPercent");
		addNodes.add("visible");
		addNodes.add("state");
		addNodes.add("bannerURL");
		addNodes.add("dealStyles");
		addNodes.add("dealChannels");
		return addNodes;		
	}	
	
	/**
	 * deal nodes with out discountid
	 * @author jhansi.bai
	 * @return
	 */
	public static List<String> getDealNodesWithOutDiscId (){
		List<String> addNodes = new ArrayList<>();
		addNodes.add("id");
		addNodes.add("createdOn");
		addNodes.add("name");
		addNodes.add("description");
		addNodes.add("dealType");
		addNodes.add("startTime");
		addNodes.add("endTime");
		addNodes.add("discountPercent");
		addNodes.add("visible");
		addNodes.add("state");
		addNodes.add("bannerURL");
		addNodes.add("dealStyles");
		addNodes.add("dealChannels");
		return addNodes;		
	}	
	
	/**
	 * deal nodes
	 * @author jhansi.bai
	 * @return
	 */
	public static List<String> updateDealNodes (String dealId)
	{
		List<String> addNodes = new ArrayList<>();
		addNodes.add(dealId+updateDealPath+"id");
		addNodes.add(dealId+updateDealPath+"createdOn");
		addNodes.add(dealId+updateDealPath+"name");
//		addNodes.add(dealId+updateDealPath+"discountId");
		addNodes.add(dealId+updateDealPath+"description");
		addNodes.add(dealId+updateDealPath+"dealType");
		addNodes.add(dealId+updateDealPath+"startTime");
		addNodes.add(dealId+updateDealPath+"endTime");
		addNodes.add(dealId+updateDealPath+"discountPercent");
		addNodes.add(dealId+updateDealPath+"visible");
		addNodes.add(dealId+updateDealPath+"state");
		addNodes.add(dealId+updateDealPath+"bannerURL");
		addNodes.add(dealId+updateDealPath+"dealStyles");
		addNodes.add(dealId+updateDealPath+"dealChannels");
		
		return addNodes;		
	}	
	
	/**
	 * dealStyles nodes
	 * @author jhansi.bai
	 * @return
	 */
	public static List<String> getDealStylesNodes(){
		List<String> addNodes = new ArrayList<>();
		addNodes.add(dealStylesPath+"id");
		addNodes.add(dealStylesPath+"createdOn");
		addNodes.add(dealStylesPath+"styleId");
		return addNodes;	
	}
	
	/**
	 * dealChannels nodes
	 * @author jhansi.bai
	 * @return
	 */
	public static List<String> getDealChannesNodes(){
		List<String> addNodes = new ArrayList<>();
		addNodes.add(dealChannelsPath+"id");
		addNodes.add(dealChannelsPath+"createdOn");
		addNodes.add(dealChannelsPath+"channelType");
		return addNodes;	
	}
	
	/**
	 * Error message nodes
	 * @author jhansi.bai
	 */
	public static List<String> getErrorMessgeNodes(){
		List<String> addNodes = new ArrayList<>();
		addNodes.add("status");
		addNodes.add("message");
		return addNodes;	
	}
/*	
	*//**
	 * get all deals
	 * @param testContext
	 * @author jhansi.bai
	 *//*
	public List<Integer> allDeals(){
		List<Integer> deals = new ArrayList<Integer>();
		List<Integer> getVisibleDeals = getDealIds("true", "0", "1", "0");
		List<Integer> getInVisibleDeals = getDealIds("false");
		deals.addAll(getVisibleDeals);
		deals.addAll(getInVisibleDeals);
		return deals;
	}*/
	
	public RequestGenerator getPayLoadReqByQuery(APINAME apiName, String param1, String param2){		
		MyntraService service = Myntra.getService(ServiceType.PORTAL_DEALSSERVICE,
				apiName, init.Configurations, new String[] { param1}, new String[] { param2 },	
				PayloadType.JSON, PayloadType.JSON);
		System.out.println(service.URL);
		System.out.println(service.Payload);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);
		return req;
	}
	
	public List<Integer> allDeals(){
		List<Integer> deals = new ArrayList<Integer>();
		//List<Integer> getVisibleDeals = getDealIds("true", "0", "1", "0");
		List<Integer> getInVisibleDeals = getDealIds("false", "0", "0", "0");
		//deals.addAll(getVisibleDeals);
		deals.addAll(getInVisibleDeals);
		return deals;
	}
}
