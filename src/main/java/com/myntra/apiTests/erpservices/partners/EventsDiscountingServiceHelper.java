package com.myntra.apiTests.erpservices.partners;

import java.util.HashMap;

import com.myntra.apiTests.erpservices.Constants;
import org.testng.Assert;

import com.myntra.pricingengine.domain.Event;
import com.myntra.test.commons.service.HTTPMethods;
import com.myntra.test.commons.service.HttpExecutorService;
import com.myntra.apiTests.SERVICE_TYPE;
import com.myntra.test.commons.service.Svc;

public class EventsDiscountingServiceHelper {
	
	private HashMap<String, String> getPartnerPortalAPIHeader() {
		HashMap<String, String> createPartnerConnectServiceHeaders = new HashMap<String, String>();
		createPartnerConnectServiceHeaders.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0=");
		createPartnerConnectServiceHeaders.put("Content-Type", "Application/json");
		createPartnerConnectServiceHeaders.put("Accept", "Application/json");
		return createPartnerConnectServiceHeaders;
	}
	
	public void getDiscountsByVendorIdAndEventId(String vendorId, String eventId) throws Exception {	
		Svc service = HttpExecutorService.executeHttpService(Constants.EVENTS_DISCOUNT_PATH.VENDOR, new String[]{vendorId+"/event/"+eventId},
				SERVICE_TYPE.VENDOR_SVC.toString(), HTTPMethods.GET, null, getPartnerPortalAPIHeader());
		Assert.assertEquals(service.getResponseStatus(), 200, "API Statuscode : ");	 	
		com.myntra.pricingengine.domain.Event e=new Event();
		Assert.assertEquals(service.getResponseStatusType("statusType"), "SUCCESS");
//		com.myntra.pricingengine.domain.Events r=new Events();
//		r.g
//		BannerResponse bannerResponse=(BannerResponse)APIUtilities.getJsontoObject(service.getResponseBody(), new BannerResponse());
//		return bannerResponse;
	}

	public void getEventsListByVendorId(String vendorId) throws Exception {
		Svc service = HttpExecutorService.executeHttpService(Constants.EVENTS_DISCOUNT_PATH.VENDOR, new String[]{vendorId+"/event/"},
				SERVICE_TYPE.VENDOR_SVC.toString(), HTTPMethods.GET, null, getPartnerPortalAPIHeader());
		Assert.assertEquals(service.getResponseStatus(), 200, "API Statuscode : ");	 
		Assert.assertEquals(service.getResponseStatusType("statusType"), "SUCCESS");
		
	}

	public void getAllStylesAvailableForDiscounts(String vendorId) throws Exception {
		Svc service = HttpExecutorService.executeHttpService(Constants.EVENTS_DISCOUNT_PATH.VENDOR, new String[]{vendorId+"/event/style"},
				SERVICE_TYPE.VENDOR_SVC.toString(), HTTPMethods.GET, null, getPartnerPortalAPIHeader());
		Assert.assertEquals(service.getResponseStatus(), 200, "API Statuscode : ");	 
		Assert.assertEquals(service.getResponseStatusType("statusType"), "SUCCESS");
		
	}

	public void addDiscounts(String vendorId,String payload) throws Exception {
		Svc service = HttpExecutorService.executeHttpService(Constants.EVENTS_DISCOUNT_PATH.VENDOR, new String[]{vendorId+"/event/discount"},
				SERVICE_TYPE.VENDOR_SVC.toString(), HTTPMethods.POST,payload , getPartnerPortalAPIHeader());
		Assert.assertEquals(service.getResponseStatus(), 200, "API Statuscode : ");	 
		Assert.assertEquals(service.getResponseStatusType("statusType"), "SUCCESS");
		
	}

}
