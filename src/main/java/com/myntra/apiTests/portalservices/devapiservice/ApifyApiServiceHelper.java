package com.myntra.apiTests.portalservices.devapiservice;

import java.util.HashMap;

import com.myntra.apiTests.common.Myntra;
import com.myntra.apiTests.portalservices.commons.CommonUtils;
import com.myntra.apiTests.portalservices.notificationservice.NotificationServiceConstants;
import org.apache.log4j.Logger;

import com.jayway.jsonpath.JsonPath;
import com.myntra.lordoftherings.Initialize;
import com.myntra.apiTests.common.APINAME;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.apiTests.common.ServiceType;

import net.minidev.json.JSONArray;

/**
 * @author Mani
 *
 */
public class ApifyApiServiceHelper extends CommonUtils implements NotificationServiceConstants
{
	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(ApifyApiServiceHelper.class);
	APIUtilities apiUtil=new APIUtilities();

	public RequestGenerator invokePdp(String styleId) {
		MyntraService apifyPdpService = Myntra.getService(ServiceType.PORTAL_APIFY, APINAME.APIFYPDP, init.Configurations, new String[]{ styleId });
		apifyPdpService.URL = new APIUtilities().prepareparameterizedURL(apifyPdpService.URL, "product/" + styleId + "?co=1");
		System.out.println("\nPrinting apifyAPIPdp URL : "+apifyPdpService.URL);
		log.info("\nPrinting apifyAPIPdp SignIn API URL : "+apifyPdpService.URL);
		return new RequestGenerator(apifyPdpService);
	}
	
	public RequestGenerator invokePdpCollapseState(String styleId, String networkType) {
		MyntraService apifyPdpService = Myntra.getService(ServiceType.PORTAL_APIFY, APINAME.APIFYPDP, init.Configurations, new String[]{ styleId });
		apifyPdpService.URL = new APIUtilities().prepareparameterizedURL(apifyPdpService.URL, "product/" + styleId + "?co=1");
		HashMap<String, String> apifyGetPdpHeaders = new HashMap<String, String>();
		//connectionClass=UNKNOWN; connectionType=WIFI;
		apifyGetPdpHeaders.put("x-device-state", networkType);
		System.out.println("\nPrinting apifyAPIPdp URL : "+apifyPdpService.URL);
		log.info("\nPrinting apifyAPIPdp SignIn API URL : "+apifyPdpService.URL);
		return new RequestGenerator(apifyPdpService, apifyGetPdpHeaders);
	}
	
	public RequestGenerator invokePdpSizeRecommendationAbTestState(String styleId, String abTestType) {
		String sizeRecommendation = "pdp.details=table; android.helpshift=disabled; windows.shutdown=true; lgp.rollout=enabled; lgp.cardloadevent=enabled; ios.browse=variantC; address.v2=enabled; d0.newuser=enabled; pdp.forum=enabled; lga.shots=enabled; suggest.charles=disabled; store.variant=store; nav.links=store; mymyntra.armor=enabled; lgp.personalization.rollout=enabled; lgp.stream.variant=variantC; rn.update=default; pdp.video=disabled; nav.store=disabled; search.sampling=enabled; cart.juspay=enabled; lga.forum=disabled; config.bucket=regular; lgp.timeline.cardloadevent=enabled; contactus.number=outside; notification.variant=v5; ios.profile.fab=default; lgp.feed.variants=variantB; shortlist.list.click=default; pdp.ios.livephoto=disabled; search.charles=disabled; pdp.android.react=disabled; nav.guided=enabled; android.help=disabled; search.additionalInfo=test; search.visual=enabled; pdp.ios.react=disabled; rn.update.ios=default; testset=test1; recommendations.visual=disabled; lgp.react.feed.390=batbelt; lgp.stream=enabled; lgp.rollout.ios=enabled; wallet=enabled; lgp.stream.ios=enabled; android.looksoms=disabled; pdp.react.sizereco=default; pps=enabled";
		String disableSizeRecommendation = "pdp.details=table; android.helpshift=disabled; windows.shutdown=true; lgp.rollout=enabled; lgp.cardloadevent=enabled; ios.browse=variantC; address.v2=enabled; d0.newuser=enabled; pdp.forum=enabled; lga.shots=enabled; suggest.charles=disabled; store.variant=store; nav.links=store; mymyntra.armor=enabled; lgp.personalization.rollout=enabled; lgp.stream.variant=variantC; rn.update=default; pdp.video=disabled; nav.store=disabled; search.sampling=enabled; cart.juspay=enabled; lga.forum=disabled; config.bucket=regular; lgp.timeline.cardloadevent=enabled; contactus.number=outside; notification.variant=v5; ios.profile.fab=default; lgp.feed.variants=variantB; shortlist.list.click=default; pdp.ios.livephoto=disabled; search.charles=disabled; pdp.android.react=disabled; nav.guided=enabled; android.help=disabled; search.additionalInfo=test; search.visual=enabled; pdp.ios.react=disabled; rn.update.ios=default; testset=test1; recommendations.visual=disabled; lgp.react.feed.390=batbelt; lgp.stream=enabled; lgp.rollout.ios=enabled; wallet=enabled; lgp.stream.ios=enabled; android.looksoms=disabled; pdp.react.sizereco=disabled; pps=enabled";
		MyntraService apifyPdpService = Myntra.getService(ServiceType.PORTAL_APIFY, APINAME.APIFYPDP, init.Configurations, new String[]{ styleId });
		apifyPdpService.URL = new APIUtilities().prepareparameterizedURL(apifyPdpService.URL, "product/" + styleId + "?co=1");
		HashMap<String, String> apifyGetPdpHeaders = new HashMap<String, String>();
		if (abTestType.equals("disabled")) {
			apifyGetPdpHeaders.put("X-MYNTRA-ABTEST", disableSizeRecommendation);
		} else {
			apifyGetPdpHeaders.put("X-MYNTRA-ABTEST", sizeRecommendation);
		}
		
		System.out.println("\nPrinting apifyAPIPdp URL : "+apifyPdpService.URL);
		log.info("\nPrinting apifyAPIPdp SignIn API URL : "+apifyPdpService.URL);
		return new RequestGenerator(apifyPdpService, apifyGetPdpHeaders);
	}
	
	public RequestGenerator invokePdpAction(String actionUrl) {
		MyntraService apifyPdpService = Myntra.getService(ServiceType.PORTAL_APIFY, APINAME.APIFYPDP, init.Configurations, new String[]{ actionUrl});
		apifyPdpService.URL = new APIUtilities().prepareparameterizedURL(apifyPdpService.URL, actionUrl);
		System.out.println("\nPrinting apifyAPIPdp URL : "+apifyPdpService.URL);
		log.info("\nPrinting apifyAPIPdp SignIn API URL : "+apifyPdpService.URL);
		return new RequestGenerator(apifyPdpService);
	}
	
	public RequestGenerator invokePdpBrand(String styleId) {
		MyntraService apifyPdpService = Myntra.getService(ServiceType.PORTAL_APIFY, APINAME.APIFYPDP, init.Configurations, new String[]{ styleId });
		apifyPdpService.URL = new APIUtilities().prepareparameterizedURL(apifyPdpService.URL, "product/" + styleId + "/brand?co=1");
		System.out.println("\nPrinting apifyAPIPdp URL : "+apifyPdpService.URL);
		log.info("\nPrinting apifyAPIPdp SignIn API URL : "+apifyPdpService.URL);
		return new RequestGenerator(apifyPdpService);
	}

	public RequestGenerator invokePdpRelated(String styleId) {
		MyntraService apifyPdpService = Myntra.getService(ServiceType.PORTAL_APIFY, APINAME.APIFYPDP, init.Configurations, new String[]{ styleId });
		apifyPdpService.URL = new APIUtilities().prepareparameterizedURL(apifyPdpService.URL, "product/" + styleId + "/related?co=1");
		System.out.println("\nPrinting apifyAPIPdp URL : "+apifyPdpService.URL);
		log.info("\nPrinting apifyAPIPdp SignIn API URL : "+apifyPdpService.URL);
		return new RequestGenerator(apifyPdpService);
	}

	public RequestGenerator invokePdpLikeSummary(String styleId) {
		MyntraService apifyPdpService = Myntra.getService(ServiceType.PORTAL_APIFY, APINAME.APIFYPDP, init.Configurations, new String[]{ styleId });
		apifyPdpService.URL = new APIUtilities().prepareparameterizedURL(apifyPdpService.URL, "product/" + styleId + "/likes/summary?co=1");
		System.out.println("\nPrinting apifyAPIPdp URL : "+apifyPdpService.URL);
		log.info("\nPrinting apifyAPIPdp SignIn API URL : "+apifyPdpService.URL);
		return new RequestGenerator(apifyPdpService);
	}

	public RequestGenerator invokePdpOffers(String styleId) {
		MyntraService apifyPdpService = Myntra.getService(ServiceType.PORTAL_APIFY, APINAME.APIFYPDP, init.Configurations, new String[]{ styleId });
		apifyPdpService.URL = new APIUtilities().prepareparameterizedURL(apifyPdpService.URL, "product/" + styleId + "/offers?co=1");
		System.out.println("\nPrinting apifyAPIPdp URL : "+apifyPdpService.URL);
		log.info("\nPrinting apifyAPIPdp SignIn API URL : "+apifyPdpService.URL);
		return new RequestGenerator(apifyPdpService);
	}
	
	public RequestGenerator invokePdpSizeRecommendation(String styleId, String allSkus, String uidx) {
		MyntraService apifyPdpSizeRecommendationService = Myntra.getService(ServiceType.PORTAL_APIFY, APINAME.APIFYPDPSIZERECOMMENDATION, init.Configurations, new String[]{ uidx, styleId, allSkus});
		apifyPdpSizeRecommendationService.URL = new APIUtilities().prepareparameterizedURL(apifyPdpSizeRecommendationService.URL, "product/" + styleId);
		System.out.println("\nPrinting apifyAPIPdp URL : "+apifyPdpSizeRecommendationService.URL);
		log.info("\nPrinting apifyAPIPdp SignIn API URL : "+apifyPdpSizeRecommendationService.URL);
		return new RequestGenerator(apifyPdpSizeRecommendationService);
	}
	
	public RequestGenerator invokeStyleServiceabilityV4(String StyleInfoResponse, String Pincode) {
		String price = JsonPath.read(StyleInfoResponse,"$..serviceability.payload.options.price").toString();
		String mrp = JsonPath.read(StyleInfoResponse,"$..serviceability.payload.options.mrp").toString();
		String warehouses = JsonPath.read(StyleInfoResponse,"$..serviceability.payload.options.warehouses").toString();
		String leadTime = JsonPath.read(StyleInfoResponse,"$..serviceability.payload.options.leadTime").toString();
		String returnPeriod = JsonPath.read(StyleInfoResponse,"$..serviceability.payload.options.returnPeriod").toString();
		String isHazmat = JsonPath.read(StyleInfoResponse,"$..serviceability.payload.options.flags.isHazmat").toString();
		String isFragile = JsonPath.read(StyleInfoResponse,"$..serviceability.payload.options.flags.isFragile").toString();
		String isJewellery = JsonPath.read(StyleInfoResponse,"$..serviceability.payload.options.flags.isJewellery").toString();
		String isExchangeable = JsonPath.read(StyleInfoResponse,"$..serviceability.payload.options.flags.isExchangeable").toString();
		String pickupEnabled = JsonPath.read(StyleInfoResponse,"$..serviceability.payload.options.flags.pickupEnabled").toString();
		String isTryable = JsonPath.read(StyleInfoResponse,"$..serviceability.payload.options.flags.isTryable").toString();
		String isLarge = JsonPath.read(StyleInfoResponse,"$..serviceability.payload.options.flags.isLarge").toString();
		String isReturnable = JsonPath.read(StyleInfoResponse,"$..serviceability.payload.options.flags.isReturnable").toString();

		String[] payloadParams = new String[] {Pincode, price, mrp, warehouses, leadTime, returnPeriod, isHazmat, isFragile, isJewellery, isExchangeable, pickupEnabled, isTryable, isLarge, isReturnable};

		MyntraService GetStyleServiceabilityV4 = Myntra.getService(ServiceType.PORTAL_APIFY, APINAME.APIFYPDPSERVICIABILITYCHECK, init.Configurations,payloadParams);
		System.out.println("V4 Style Serviceability Service URL : "+GetStyleServiceabilityV4.URL);
		System.out.println("V4 Style Serviceability Service Payload : "+GetStyleServiceabilityV4.URL);

		return new RequestGenerator(GetStyleServiceabilityV4);
	}
	
	public boolean isSocialValuePresentOrNot(String pdpResponse) {
		boolean socialVal = false;
		JSONArray findAllTypes = JsonPath.read(pdpResponse, "$.cards[*].type");
		for(Object types: findAllTypes) {
			if(types.equals("SOCIAL")) {
				socialVal = true;
			}
		}
		return socialVal;
	}
	
	public JSONArray isCollapseStatus(String pdpResponse) {
		JSONArray collapseStatus = JsonPath.read(pdpResponse, "$.cards[*].args.collapse");
		return collapseStatus;
	}
	
	public JSONArray findAllActionUrls(String pdpResponse) {
		JSONArray findAllActionUrl = JsonPath.read(pdpResponse, "$..props.action");
		return findAllActionUrl;
	}
	
	public JSONArray findAllCrossLinks(String pdpResponse) {
		JSONArray crossLinks = JsonPath.read(pdpResponse, "$..crossLinks[*].url");
		return crossLinks;
	}
	
	public JSONArray findAllLazyActionType(String pdpResponse) {
		JSONArray findAllLazyActionType = JsonPath.read(pdpResponse, "$..components[*].props.actionType");
		return findAllLazyActionType;
	}
	
	public boolean findAllComponentTypes(String pdpResponse, String type) {
		boolean typeStatus = false;
		JSONArray findAllComponentTypes = JsonPath.read(pdpResponse, "$.cards[*].components[*].type");
		for(Object responseType: findAllComponentTypes) {
			if(responseType.equals(type)) {
				typeStatus = true;
			}
		}
		return typeStatus;
	}
	
	public boolean findAllComponentViewTypes(String pdpResponse, String viewType) {
		boolean viewTypeStatus = false;
		JSONArray findAllComponentTypes = JsonPath.read(pdpResponse, "$.cards[*].components[*].viewType");
		for(Object responseViewType: findAllComponentTypes) {
			if(responseViewType.equals(viewType)) {
				viewTypeStatus = true;
			}
		}
		return viewTypeStatus;
	}
	
	public JSONArray findAllRelatedProductUrl(String pdpResponse) {
		JSONArray findAllRelatedProductUrl = JsonPath.read(pdpResponse, "$.cards[*]..props.sizes[*].action");
		return findAllRelatedProductUrl;
	}
	
	public JSONArray findAllSkus(String pdpResponse) {
		JSONArray findAllSkus = JsonPath.read(pdpResponse, "$.cards[*].components[*].props[*][*].skuId");
		return findAllSkus;
	}
	
	public boolean isSizeRecoLazyPresent(String pdpResponse) {
		boolean viewTypeStatus = false;
		JSONArray sizeRecoLazy;
		try {
			sizeRecoLazy = JsonPath.read(pdpResponse, "$.cards[*]..props.sizeRecoLazy");
			if(sizeRecoLazy.isEmpty()) {
				viewTypeStatus = false;
			} else {
				viewTypeStatus = true;
			}
		} catch(Exception e) {
			viewTypeStatus = false;
		}
		return viewTypeStatus;
	}
	
	public boolean findAllCardsTypes(String pdpResponse, String cardType) {
		boolean cardTypeStatus = false;
		JSONArray findAllCardsTypes = JsonPath.read(pdpResponse, "$.cards[*].type");
		for(Object responseViewType: findAllCardsTypes) {
			if(responseViewType.equals(cardType)) {
				cardTypeStatus = true;
			}
		}
		return cardTypeStatus;
	}
	
	public JSONArray isSbpEnabled(String pdpResponse) {
		JSONArray sbpEnabled = JsonPath.read(pdpResponse, "$.cards[*].components[*].props.sbpEnabled");
		return sbpEnabled;
	}
	
	
}