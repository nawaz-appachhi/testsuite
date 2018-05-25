package com.myntra.apiTests.portalservices.devapiservice;

import java.util.ArrayList;
import java.util.HashMap;

import com.jayway.jsonpath.JsonPath;
import com.myntra.apiTests.common.Myntra;
import com.myntra.lordoftherings.Initialize;
import com.myntra.apiTests.common.APINAME;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.PayloadType;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.apiTests.common.ServiceType;

import net.minidev.json.JSONArray;

//Helper methods for DevAPI - Style related tests
public class StyleTestsHelper 
{
	static Initialize init = new Initialize("/Data/configuration");
	APIUtilities apiUtilities=new APIUtilities();
	
	//Method #1 - To get style information based on StyleID
	public RequestGenerator getStyleInfo(String styleId)
	{
		MyntraService GetStyleInfoService = Myntra.getService(ServiceType.PORTAL_DEVAPIS, APINAME.DEVAPISTYLE, init.Configurations, new String[]{ styleId });
		GetStyleInfoService.URL = apiUtilities.prepareparameterizedURL(GetStyleInfoService.URL, styleId);
		System.out.println("Get Style Information Service URL : "+GetStyleInfoService.URL);
		return new RequestGenerator(GetStyleInfoService);

	}
	
	//Method #2 - To get offer data for Style
	public RequestGenerator getStyleOffers(String styleId)
	{
		MyntraService GetStyleOffersService = Myntra.getService(ServiceType.PORTAL_DEVAPIS, APINAME.DEVAPISTYLEOFFERS, init.Configurations, new String[] { styleId });
		GetStyleOffersService.URL = apiUtilities.prepareparameterizedURL(GetStyleOffersService.URL, styleId);
		System.out.println("Get Style Offer Information Service URL : "+GetStyleOffersService.URL);
		return new RequestGenerator(GetStyleOffersService);
	}
	
	//Method #3 - To get related Styles
	public RequestGenerator getSimilarStyles(String styleId, String name, String useVisualSearch, String useColorRecommendation)
	{
		String VisuallySimilarItemsHeader = "pdp.details=table; lgp.rollout=enabled; lgp.cardloadevent=enabled; d0.newuser=disabled; pdp.forum=enabled; lga.shots=enabled; nav.links=store; lgp.personalization.rollout=enabled; rn.update=default; pdp.video=enabled; nav.store=disabled; search.sampling=enabled; cart.juspay=enabled; lga.forum=disabled; lgp.timeline.cardloadevent=enabled; contactus.number=outside; nav.guided=enabled; search.additionalInfo=test; search.visual=enabled; rn.update.ios=default; testset=test2; recommendations.visual=enabled; lgp.stream=enabled; lgp.rollout.ios=enabled; lgp.stream.ios=disabled; pps=enabled";
		String SimilarItemsHeader = "pdp.details=table; lgp.rollout=enabled; lgp.cardloadevent=enabled; d0.newuser=disabled; pdp.forum=enabled; lga.shots=enabled; nav.links=store; lgp.personalization.rollout=enabled; rn.update=default; pdp.video=enabled; nav.store=disabled; search.sampling=enabled; cart.juspay=enabled; lga.forum=disabled; lgp.timeline.cardloadevent=enabled; contactus.number=outside; nav.guided=enabled; search.additionalInfo=test; search.visual=enabled; rn.update.ios=default; testset=test2; recommendations.visual=disabled; lgp.stream=enabled; lgp.rollout.ios=enabled; lgp.stream.ios=disabled; pps=enabled";

		MyntraService GetSimilarStylesService = Myntra.getService(ServiceType.PORTAL_DEVAPIS, APINAME.DEVAPIGETSIMILARITEMS, init.Configurations);
		GetSimilarStylesService.URL = apiUtilities.prepareparameterizedURL(GetSimilarStylesService.URL, new String[] {name, styleId, useColorRecommendation});
		System.out.println("Get Similar Items URL : "+GetSimilarStylesService.URL);
		HashMap<String, String> headers = new HashMap<String,String>();
		
		if(useVisualSearch.equals("true"))
		{
			headers.put("X-MYNTRA-ABTEST", VisuallySimilarItemsHeader);
		}
		else
		{
			headers.put("X-MYNTRA-ABTEST", SimilarItemsHeader);
		}
		return new RequestGenerator(GetSimilarStylesService,headers);
	}
	
	//Method #4 - To get likes summary for a style
	public RequestGenerator getLikesSummary(String styleId)
	{
		MyntraService GetLikeSummaryService = Myntra.getService(ServiceType.PORTAL_DEVAPISPROFILEANDPRODUCTS,APINAME.LIKESUMMERY, init.Configurations);
		GetLikeSummaryService.URL = apiUtilities.prepareparameterizedURL(GetLikeSummaryService.URL, styleId);
		System.out.println("Get Likes Summary URL : "+GetLikeSummaryService.URL);
		return new RequestGenerator(GetLikeSummaryService);
	}
	
	//Method #5 - To get V1 Serviceability
	public RequestGenerator getStyleServiceabilityV1(String searchString)
	{
		MyntraService GetStyleServiceabilityV1 = Myntra.getService(ServiceType.PORTAL_DEVAPIS, APINAME.DEVAPISERVICEABILITYV1, init.Configurations);
		GetStyleServiceabilityV1.URL = apiUtilities.prepareparameterizedURL(GetStyleServiceabilityV1.URL, searchString);
		System.out.println("V1 Style Serviceability Service URL : "+GetStyleServiceabilityV1.URL);
		return new RequestGenerator(GetStyleServiceabilityV1);
	}
	
	//Method #6 - To get V2 Serviceability
	public RequestGenerator getStyleServiceabilityV2(String searchString)
	{
		MyntraService GetStyleServiceabilityV2 = Myntra.getService(ServiceType.PORTAL_DEVAPIS, APINAME.DEVAPISERVICEABILITYV2, init.Configurations);
		GetStyleServiceabilityV2.URL = apiUtilities.prepareparameterizedURL(GetStyleServiceabilityV2.URL, searchString);
		System.out.println("V2 Style Serviceability Service URL : "+GetStyleServiceabilityV2.URL);
		return new RequestGenerator(GetStyleServiceabilityV2);
	}
	
	//Method #7 - To get V3 Serviceability
	public RequestGenerator getStyleServiceabilityV3(String searchString)
	{
		MyntraService GetStyleServiceabilityV3 = Myntra.getService(ServiceType.PORTAL_DEVAPIS, APINAME.DEVAPISERVICEABILITYV3, init.Configurations);
		GetStyleServiceabilityV3.URL = apiUtilities.prepareparameterizedURL(GetStyleServiceabilityV3.URL, searchString);
		System.out.println("V3 Style Serviceability Service URL : "+GetStyleServiceabilityV3.URL);
		return new RequestGenerator(GetStyleServiceabilityV3);
	}
	
	//Method #8 - To get V4 Serviceability [Need to Edit]
	public RequestGenerator getStyleServiceabilityV4(String StyleInfoResponse, String Pincode)
	{
		String ArticleTypeId = JsonPath.read(StyleInfoResponse,"$.data.articleType.id").toString();
		JSONArray AvailableSkus = JsonPath.read(StyleInfoResponse,"$.data.styleOptions[*].id");
		int AvailableSkusCount = AvailableSkus.size();
		int SkuIndex = 0 + (int)(Math.random() * AvailableSkusCount); 
		String WareHouses = JsonPath.read(StyleInfoResponse,"$.data.styleOptions["+SkuIndex+"].skuAvailabilityDetailMap.1.availableInWarehouses").toString();
		String availableWarehouses = WareHouses.length()==0 ? "28" : WareHouses;
		String codEnabled1 = JsonPath.read(StyleInfoResponse,"$.data.codEnabled").toString();
		String discountedPrice = JsonPath.read(StyleInfoResponse,"$.data.discountedPrice").toString();
		String isTrynBuyEnabled = JsonPath.read(StyleInfoResponse,"$.data.articleType.isTryAndBuyEnabled").toString();
		String leadTime = JsonPath.read(StyleInfoResponse,"$.data.styleOptions["+SkuIndex+"].skuAvailabilityDetailMap.1.leadTime").toString();
		String isReturnable = JsonPath.read(StyleInfoResponse,"$.data.otherFlags[0].value").toString();
		String isExchangeable = JsonPath.read(StyleInfoResponse,"$.data.otherFlags[1].value").toString();
		String pickupEnabled = JsonPath.read(StyleInfoResponse,"$.data.otherFlags[2].value").toString();
		String isTryAndBuyEnabled = JsonPath.read(StyleInfoResponse,"$.data.otherFlags[3].value").toString();
		String isLarge = JsonPath.read(StyleInfoResponse,"$.data.otherFlags[4].value").toString();
		String isHazmat = JsonPath.read(StyleInfoResponse,"$.data.otherFlags[5].value").toString();
		String isJewellery = JsonPath.read(StyleInfoResponse,"$.data.otherFlags[6].value").toString();
		String isFragile = JsonPath.read(StyleInfoResponse,"$.data.otherFlags[7].value").toString();
		String active = JsonPath.read(StyleInfoResponse,"$.data.otherFlags[8].value").toString();
		String socialSharingEnabled = JsonPath.read(StyleInfoResponse,"$.data.otherFlags[9].value").toString();
		String codEnabled2= JsonPath.read(StyleInfoResponse,"$.data.otherFlags[10].value").toString();
		String price = JsonPath.read(StyleInfoResponse,"$.data.price").toString();
		String styleId = JsonPath.read(StyleInfoResponse,"$.data.id").toString();
		String subCategoryId = JsonPath.read(StyleInfoResponse,"$.data.subCategory.id").toString();
		String supplyType = JsonPath.read(StyleInfoResponse,"$.data.styleOptions["+SkuIndex+"].skuAvailabilityDetailMap.1.supplyType").toString();
		String[] payloadParams = new String[] {ArticleTypeId,availableWarehouses,codEnabled1,discountedPrice,isTrynBuyEnabled,leadTime,isReturnable,isExchangeable,pickupEnabled,isTryAndBuyEnabled,isLarge,isHazmat,isJewellery,isFragile,active,socialSharingEnabled,codEnabled2,Pincode,price,styleId,subCategoryId,subCategoryId,supplyType};

		MyntraService GetStyleServiceabilityV4 = Myntra.getService(ServiceType.PORTAL_DEVAPIS, APINAME.DEVAPISERVICEABILITYV4, init.Configurations,payloadParams);
		System.out.println("V4 Style Serviceability Service URL : "+GetStyleServiceabilityV4.URL);
		System.out.println("V4 Style Serviceability Service Payload : "+GetStyleServiceabilityV4.URL);

		return new RequestGenerator(GetStyleServiceabilityV4);
	}
	
	//Method #9 - To get Product Likes
	public RequestGenerator getProductLikes(String StyleID)
	{
		MyntraService GetProductLikesService = Myntra.getService(ServiceType.PORTAL_DEVAPIS, APINAME.DEVAPIGETPRODUCTLIKES, init.Configurations, new String[] {StyleID});
		GetProductLikesService.URL = apiUtilities.prepareparameterizedURL(GetProductLikesService.URL, StyleID);
		System.out.println("Get Product Likes Service URL : "+GetProductLikesService.URL);
		return new RequestGenerator(GetProductLikesService);
	}
	
	//Method #10 - Get Sku IDs for a particular Style
	public ArrayList<Integer> getStyleSKUInfo(String styleId)
	{
		MyntraService GetStyleInfoService = Myntra.getService(ServiceType.PORTAL_DEVAPIS, APINAME.DEVAPISTYLE, init.Configurations, new String[]{ styleId });
		GetStyleInfoService.URL = apiUtilities.prepareparameterizedURL(GetStyleInfoService.URL, styleId);
		System.out.println("Get Style Information Service URL : "+GetStyleInfoService.URL);
		RequestGenerator request = new RequestGenerator(GetStyleInfoService);
		String response = request.returnresponseasstring();
		ArrayList<Integer> sku = JsonPath.read(response, "$.data.styleOptions[*].skuId");
		return sku;

	}
	
	//Method #11 - Get Style ID from SKU Code
	public RequestGenerator getStyleIdFromSkuCode(String SkuCode)
	{
		MyntraService GetStyleIdfromSkuCode = Myntra.getService(ServiceType.PORTAL_DEVAPIS, APINAME.DEVAPIGETSTYLEFROMSKUCODE, init.Configurations);
		GetStyleIdfromSkuCode.URL = apiUtilities.prepareparameterizedURL(GetStyleIdfromSkuCode.URL, SkuCode);
		System.out.println("Get Style Id from SKU code  Service URL : "+GetStyleIdfromSkuCode.URL);
		return new RequestGenerator(GetStyleIdfromSkuCode);
	}
}
