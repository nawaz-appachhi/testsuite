package com.myntra.apiTests.erpservices.partners.Tests;

import com.myntra.apiTests.erpservices.partners.dp.PartnerConnectDP;
import com.myntra.apiTests.erpservices.partners.PartnerConnectHelper;
import com.myntra.lordoftherings.Initialize;
import com.myntra.partnerconnect.client.response.*;
import com.myntra.partnerconnect.client.util.Metric;
import com.myntra.test.commons.testbase.BaseTest;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class PartnerConnectTest extends BaseTest {
	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(PartnerConnectTest.class);
	static String envName = "fox7";
	public static PartnerConnectHelper partnerConnectHelper;

	@BeforeClass()
	public void testAfterClass() throws SQLException {
		init = new Initialize("Data/configuration");
		partnerConnectHelper = new PartnerConnectHelper();

	}

	@Test(enabled = true, groups = { "sanity",
	"Regression" }, dataProviderClass = PartnerConnectDP.class, dataProvider = "listBrandNameWithBrandCode", description = "Gets list of Brand Names for the given brand code")
	public void listBrandNameWithBrandCode(String payload) throws Exception {
		BrandResponse brandResponse = partnerConnectHelper.listBrandNameWithBrandCode(payload);
		Assert.assertEquals(brandResponse.getData().get(0).getBrandName(), "Adidas", "Get Brand Name by Brand Code : ");

	}

	@Test(enabled = true, groups = { "sanity",
	"Regression" }, dataProviderClass = PartnerConnectDP.class, dataProvider = "listCampaignsCreatedForBrand", description = "Gets list of all the campaigns created for a Brand and from a given start date")
	public void listCampaignsCreatedForBrand(String brandCode, String startDate) throws Exception {
		CampaignResponse campaignResponse = partnerConnectHelper.listCampaignsCreatedForBrand(brandCode,startDate);
		Assert.assertEquals(campaignResponse.getStatus().getStatusCode(), 3, "Status Code : ");
		for(int i=0;i<campaignResponse.getStatus().getTotalCount();i++){
			Assert.assertEquals(campaignResponse.getData().get(i).getBrandCode(),brandCode,"Brand Code");
		}
	}

	@Test(enabled = true, groups = { "sanity",
	"Regression" }, dataProviderClass = PartnerConnectDP.class, dataProvider = "listAllBannersOfCampaign", description = "Gets list of all the banners available for the campaigns")
	public void listAllBannersOfCampaign(String campaignId) throws Exception {
		BannerResponse bannerResponse = partnerConnectHelper.listAllBannersOfCampaign(campaignId);
		Assert.assertEquals(bannerResponse.getStatus().getStatusCode(), 3, "Status Code : ");
	}
	
	@Test(enabled = true, groups = { "sanity",
	"Regression" }, dataProviderClass = PartnerConnectDP.class, dataProvider = "getCampaignDetailsByCampaignId", description = "Gets campaign details by CampaignId")
	public void getCampaignDetailsByCampaignId(String campaignId) throws Exception {
		CampaignResponse campaignResponse = partnerConnectHelper.getCampaignDetailsByCampaignId(campaignId);
		Assert.assertEquals(campaignResponse.getStatus().getStatusCode(), 3, "Status Code : ");
	}
	
	@Test(enabled = true, groups = { "sanity",
	"Regression" }, dataProviderClass = PartnerConnectDP.class, dataProvider = "getAllFiltersForReportAndBrandCombination", description = "Gets campaign details by CampaignId")
	public void getAllFiltersForReportAndBrandCombination(String payload) throws Exception {
		ReportMetadataResponse reportMetadataResponse = partnerConnectHelper.getAllFiltersForReportAndBrandCombination(payload);
		Map<String, List<String>> filters=reportMetadataResponse.getFilters();
		List<String> article_type=filters.get("article_type");
		List<String> gender=filters.get("gender");
		Assert.assertTrue(article_type.size()>0, "article_type's for the brand");
		Assert.assertTrue(gender.size()>0, "gender's for the brand");
	}
	
	@Test(enabled = true, groups = { "sanity",
	"Regression" }, dataProviderClass = PartnerConnectDP.class, dataProvider = "fetchReportData", description = "Gets campaign details by CampaignId")
	public void fetchReportData(String payload) throws Exception {
		ReportResponse reportResponse = partnerConnectHelper.fetchReportData(payload);
		Assert.assertEquals(reportResponse.getStatus().getStatusCode(), 3, "Status Code : ");
		List<Metric> metrics=reportResponse.getResult().getMetrics();
		Assert.assertTrue(metrics.size()>=5, "metrics");
	}
	

}
