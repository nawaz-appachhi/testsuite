package com.myntra.apiTests.erpservices.partners.dp;

import com.myntra.lordoftherings.Toolbox;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.partnerconnect.client.entry.BrandEntry;
import com.myntra.partnerconnect.client.enums.ReportType;
import com.myntra.partnerconnect.client.response.BrandResponse;
import com.myntra.partnerconnect.client.response.ReportResponse;
import com.myntra.partnerconnect.client.util.Metadata;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import java.util.ArrayList;
import java.util.List;

public class PartnerConnectDP {

	@DataProvider
	public static Object[][] listBrandNameWithBrandCode(ITestContext testContext) throws Exception {
		BrandEntry brandEntry = new BrandEntry();
		brandEntry.setBrandCode("ADDS");
		List<BrandEntry> data = new ArrayList<>();
		data.add(brandEntry);
		BrandResponse brandResponse = new BrandResponse();
		brandResponse.setData(data);
		String payload = APIUtilities.getObjectToJSON(brandResponse);
		System.out.println(payload);

		Object[] arr1 = { payload };
		Object[][] dataSet = new Object[][] { arr1, };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 3, 3);
	}
	
	@DataProvider
	public static Object[][] listCampaignsCreatedForBrand(ITestContext testContext) throws Exception {
		Object[] arr1 = { "PUMA","2016/11/22" };
		Object[][] dataSet = new Object[][] { arr1, };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 3, 3);
	}
	
	@DataProvider
	public static Object[][] listAllBannersOfCampaign(ITestContext testContext) throws Exception {
		Object[] arr1 = { "582d8a94803cc93879b237fb" };
		Object[][] dataSet = new Object[][] { arr1, };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 3, 3);
	}
	
	@DataProvider
	public static Object[][] getCampaignDetailsByCampaignId(ITestContext testContext) throws Exception {
		Object[] arr1 = { "582d8a94803cc93879b237fb" };
		Object[][] dataSet = new Object[][] { arr1, };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 3, 3);
	}

	@DataProvider
	public static Object[][] getAllFiltersForReportAndBrandCombination(ITestContext testContext) throws Exception {
		List<String> dimensions=new ArrayList<>();
		dimensions.add("gender");
		dimensions.add("article_type");
		String filters="brandCode.eq:PUMA";
		Metadata metadata=new Metadata();
		metadata.setDimensions(dimensions);
		metadata.setFilters(filters);
		metadata.setId(ReportType.REVENUE);
		ReportResponse reportResponse=new ReportResponse();	
		reportResponse.setMetadata(metadata);
		String payload = APIUtilities.getObjectToJSON(reportResponse);
		Object[] arr1 = { payload };
		Object[][] dataSet = new Object[][] { arr1, };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 3, 3);
	}
	
	@DataProvider
	public static Object[][] fetchReportData(ITestContext testContext) throws Exception {
		List<String> dimensions=new ArrayList<>();
		dimensions.add("banner_id");
		String filters="campaign_id.eq:5810bc42395331508e745020";
		Metadata metadata=new Metadata();
		metadata.setDimensions(dimensions);
		metadata.setFilters(filters);
		metadata.setId(ReportType.CAMPAIGN_FUNNEL);
		List<String> metrics=new ArrayList<>();
		metrics.add("revenue");
		metrics.add("clicks");
		metrics.add("impressions");
		metrics.add("brand_qty");
		metrics.add("pdp");
		metadata.setMetrics(metrics);
		metadata.setRollUp("daily");
		metadata.setFromTime(1478544616000l);
		metadata.setToTime(1478744616000l);
		ReportResponse reportResponse=new ReportResponse();	
		reportResponse.setMetadata(metadata);
		String payload = APIUtilities.getObjectToJSON(reportResponse);
		Object[] arr1 = { payload };
		Object[][] dataSet = new Object[][] { arr1, };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 3, 3);
	}
}
