/**
 * 
 */
package com.myntra.apiTests.portalservices.wmsServiceHelper;


import java.util.HashMap;

import com.myntra.apiTests.common.Myntra;
import org.apache.log4j.Logger;
import com.myntra.lordoftherings.Initialize;
import com.myntra.apiTests.common.APINAME;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.PayloadType;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.apiTests.common.ServiceType;


/**
 * @author santwana.samantray
 *
 */
public class WMSPipelineHelper {
	
	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(WMSPipelineHelper.class);
	
	public static RequestGenerator validate_GRN_API_Helper(String q, String f, String start, String fetchSize, String sortBy, String sortOrder) {
		MyntraService getOrderService = Myntra.getService(
				ServiceType.ERP_WMS, APINAME.GRN,
				init.Configurations, PayloadType.XML,
				new String[] {q, f, start, fetchSize, sortBy, sortOrder}, PayloadType.XML);

				HashMap<String, String> getOrderHeaders = new HashMap<String, String>();
				getOrderHeaders.put("Authorization",
				"Basic bW9iaWxlfm1vYmlsZTptb2JpbGU");
				getOrderHeaders.put("Content-Type", "Application/xml");

				return new RequestGenerator(getOrderService, getOrderHeaders);
	}
	
	public static RequestGenerator validate_IOS_API_Helper(String q, String f, String start, String fetchSize) {
		MyntraService getOrderService = Myntra.getService(
				ServiceType.ERP_WMS, APINAME.IOS,
				init.Configurations, PayloadType.XML,
				new String[] {q, f, start, fetchSize}, PayloadType.XML);

				HashMap<String, String> getOrderHeaders = new HashMap<String, String>();
				getOrderHeaders.put("Authorization",
				"Basic bW9iaWxlfm1vYmlsZTptb2JpbGU");
				getOrderHeaders.put("Content-Type", "Application/xml");

				return new RequestGenerator(getOrderService, getOrderHeaders);
	}



}
