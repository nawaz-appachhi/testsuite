package com.myntra.apiTests.erpservices.partners;


import java.util.HashMap;

import com.myntra.apiTests.erpservices.Constants;
import org.json.JSONObject;
import org.testng.Assert;

import com.myntra.client.security.response.UserEntry;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.partnerconnect.client.response.BannerResponse;
import com.myntra.partnerconnect.client.response.CampaignResponse;
import com.myntra.partnerconnect.client.response.BrandResponse;

import com.myntra.partnerconnect.client.response.ReportMetadataResponse;
import com.myntra.partnerconnect.client.response.ReportResponse;
import com.myntra.test.commons.service.HTTPMethods;
import com.myntra.test.commons.service.HttpExecutorService;
import com.myntra.apiTests.SERVICE_TYPE;
import com.myntra.test.commons.service.Svc;

public class PartnerConnectHelper {
	//PartnerConnectHelper partnerConnectHelper=new PartnerConnectHelper();
	
	private HashMap<String, String> getPartnerConnectAPIHeader() {
		HashMap<String, String> createPartnerConnectServiceHeaders = new HashMap<String, String>();
		createPartnerConnectServiceHeaders.put("Authorization", "Basic Ytpl");
		createPartnerConnectServiceHeaders.put("Content-Type", "Application/json");
		createPartnerConnectServiceHeaders.put("Accept", "Application/json");
		return createPartnerConnectServiceHeaders;
	}
	private HashMap<String, String> getCampaAPIHeader() throws Exception {
		HashMap<String, String> createPartnerConnectServiceHeaders = new HashMap<String, String>();
		createPartnerConnectServiceHeaders.put("Authorization", "Basic Ytpl");
		createPartnerConnectServiceHeaders.put("Content-Type", "Application/json");
		createPartnerConnectServiceHeaders.put("Accept", "Application/json");
		createPartnerConnectServiceHeaders.put("erptoken",campaSecurityTokenGenerator() );
		return createPartnerConnectServiceHeaders;
	}
	private HashMap<String, String> getCampaSecurityAPIHeader() {
		HashMap<String, String> createSecuirtyServiceHeaders = new HashMap<String, String>();
		createSecuirtyServiceHeaders.put("Authorization", "Basic Ytpl");
		createSecuirtyServiceHeaders.put("Content-Type", "Application/json");
		createSecuirtyServiceHeaders.put("Accept", "Application/json");
		createSecuirtyServiceHeaders.put("Cache-Control", "no-cache");
		createSecuirtyServiceHeaders.put("Postman-Token", "6592e6ed-e16e-7f5d-fdbc-ce3d78cb82ba");
		return createSecuirtyServiceHeaders;
	}

	public BrandResponse listBrandNameWithBrandCode(String payload) throws Exception {
		Svc service = HttpExecutorService.executeHttpService(Constants.PARTNER_CONNECT_PATH.LIST_BRAND_NAME_WITH_BRAND_CODE, null,
				SERVICE_TYPE.PARTNERCONNECT_SVC.toString(), HTTPMethods.POST, payload, getPartnerConnectAPIHeader());
		Assert.assertEquals(service.getResponseStatus(), 200, "API Statuscode : ");
		BrandResponse brandResponse=(BrandResponse)APIUtilities.getJsontoObject(service.getResponseBody(), new BrandResponse());
		return brandResponse;
	}

	public CampaignResponse listCampaignsCreatedForBrand(String brandCode,String startDate) throws Exception {
		Svc service = HttpExecutorService.executeHttpService(Constants.PARTNER_CONNECT_PATH.LIST_COMPAIGNS_OF_BRAND, new String[]{"?brandCode="+brandCode+"&startDate="+startDate},
				SERVICE_TYPE.PARTNERCONNECT_SVC.toString(), HTTPMethods.GET, null, getPartnerConnectAPIHeader());
		Assert.assertEquals(service.getResponseStatus(), 200, "API Statuscode : ");	 
		CampaignResponse campaignResponse=(CampaignResponse)APIUtilities.getJsontoObject(service.getResponseBody(), new CampaignResponse());
		return campaignResponse;
	}

	public BannerResponse listAllBannersOfCampaign(String campaignId) throws Exception {	
		Svc service = HttpExecutorService.executeHttpService(Constants.PARTNER_CONNECT_PATH.LIST_COMPAIGNS_OF_BRAND, new String[]{campaignId+"/banners"},
				SERVICE_TYPE.PARTNERCONNECT_SVC.toString(), HTTPMethods.GET, null, getCampaAPIHeader());
		Assert.assertEquals(service.getResponseStatus(), 200, "API Statuscode : ");	 	
		BannerResponse bannerResponse=(BannerResponse)APIUtilities.getJsontoObject(service.getResponseBody(), new BannerResponse());
		return bannerResponse;
	}
	
	public String campaSecurityTokenGenerator () throws Exception {
		UserEntry userEntry=new UserEntry();
		userEntry.setLoginId("erpadmin");
		userEntry.setPassword("welcome@258");
		
		String payload = APIUtilities.getObjectToJSON(userEntry);
		payload="{\"user\":"+payload+"}";
		System.out.println(payload);
		Svc service = HttpExecutorService.executeHttpService(Constants.SECURITY_PATH.SECURITY_SERVICE, null,
				SERVICE_TYPE.SECURITY_SVC.toString(), HTTPMethods.POST, payload, getCampaSecurityAPIHeader());
		Assert.assertEquals(service.getResponseStatus(), 200, "API Statuscode : ");	 
		JSONObject jsonObject=new JSONObject(service.getResponseBody());
		JSONObject userTokenResponse=jsonObject.getJSONObject("userTokenResponse");
		System.out.println(userTokenResponse.get("token"));
		
		return userTokenResponse.get("token").toString();
	}
	public CampaignResponse getCampaignDetailsByCampaignId(String campaignId) throws Exception, Exception {
		Svc service = HttpExecutorService.executeHttpService(Constants.PARTNER_CONNECT_PATH.GET_COMPAIGN_DETAILS, new String[]{campaignId},
				SERVICE_TYPE.PARTNERCONNECT_SVC.toString(), HTTPMethods.GET, null, getCampaAPIHeader());
		Assert.assertEquals(service.getResponseStatus(), 200, "API Statuscode : ");	 	
		CampaignResponse campaignResponse=(CampaignResponse)APIUtilities.getJsontoObject(service.getResponseBody(), new CampaignResponse());
		return campaignResponse;
	}
	public ReportMetadataResponse getAllFiltersForReportAndBrandCombination(String payload) throws Exception, Exception {
		Svc service = HttpExecutorService.executeHttpService(Constants.PARTNER_CONNECT_PATH.FETCH_ALL_FILTERS_FOR_REPORT_BRAND_COMBO, null,
				SERVICE_TYPE.PARTNERCONNECT_SVC.toString(), HTTPMethods.POST, payload, getPartnerConnectAPIHeader());
		Assert.assertEquals(service.getResponseStatus(), 200, "API Statuscode : ");	 	
		ReportMetadataResponse reportMetadataResponse=
				(ReportMetadataResponse)APIUtilities.getJsontoObject(service.getResponseBody(), new ReportMetadataResponse());
		return reportMetadataResponse;
	}
	public ReportResponse fetchReportData(String payload) throws Exception {
		Svc service = HttpExecutorService.executeHttpService(Constants.PARTNER_CONNECT_PATH.FETCH_REPORT_DATA, null,
				SERVICE_TYPE.PARTNERCONNECT_SVC.toString(), HTTPMethods.POST, payload, getPartnerConnectAPIHeader());
		Assert.assertEquals(service.getResponseStatus(), 200, "API Statuscode : ");	 	
		ReportResponse reportResponse=
				(ReportResponse)APIUtilities.getJsontoObject(service.getResponseBody(), new ReportResponse());
		return reportResponse;
	}


}
