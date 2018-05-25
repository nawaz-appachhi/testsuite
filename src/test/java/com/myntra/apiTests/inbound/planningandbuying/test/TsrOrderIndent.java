package com.myntra.apiTests.inbound.planningandbuying.test;

import java.util.HashMap;
import java.util.List;

import com.myntra.apiTests.inbound.dp.buyingandplanningDP;
import com.myntra.apiTests.inbound.helper.planningandbuying.*;
import com.myntra.tenant.client.entry.TenantEntry;

import org.apache.log4j.Logger;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.myntra.apiTests.inbound.helper.planningandbuying.Constants.BUSINESS_UNITS;
import com.myntra.apiTests.inbound.helper.planningandbuying.Constants.HEADERS;
import com.myntra.apiTests.inbound.helper.planningandbuying.Constants.RUN_CONFIG_FILTERS;
import com.myntra.apiTests.inbound.helper.planningandbuying.Constants.DATA_FILES;
import com.myntra.apiTests.inbound.helper.planningandbuying.Constants.PAYLOADS;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myntra.lordoftherings.Initialize;
import com.myntra.test.commons.testbase.BaseTest;
import com.myntra.tenant.Response.TenantResponse;

/**
 * @author Ajay API Tests for TSR OrderIndents
 *
 */
public class TsrOrderIndent extends BaseTest {
	static Logger log;
	public static Initialize init;
	HashMap<String, String> create_baseoi_headers;
	HashMap<String, String> upload_baseoi_headers;
	HashMap<String, String> download_baseoi_headers;
	HashMap<String, String> download_rconfig_headers;
	HashMap<String, String> download_replenishmentSet_headers;
	HashMap<String, String> upload_rconfig_headers;
	BaseOrderIndentJson baseOI;
	JeevesHelper jeeveshelper;
	JeevesValidator jeevesvalidator;
	private VendorData benetton;

	@BeforeClass
	public void init() {
		log = Logger.getLogger(TsrOrderIndent.class);
		create_baseoi_headers = new HashMap<String, String>();
		upload_baseoi_headers = new HashMap<String, String>();
		download_baseoi_headers = new HashMap<String, String>();
		download_rconfig_headers = new HashMap<String, String>();
		download_replenishmentSet_headers = new HashMap<String, String>();
		upload_rconfig_headers = new HashMap<String, String>();
		baseOI = new BaseOrderIndentJson();
		jeeveshelper = new JeevesHelper();
		jeevesvalidator = new JeevesValidator();
		benetton =new Constants.OIHeaderUCB();

		// headers
		create_baseoi_headers = jeeveshelper.Headers(HEADERS.AUTHORIZATION, HEADERS.ACCEPT, HEADERS.CONTENTTYPE);
		download_baseoi_headers = jeeveshelper.Headers(HEADERS.AUTHORIZATION, HEADERS.OCTED_CONTENTTYPE,
				HEADERS.CONTENTTYPE);
		upload_baseoi_headers = jeeveshelper.Headers(HEADERS.UPLOAD_OI_AUTHORIZATION, HEADERS.ACCEPT,
				HEADERS.MULTIPART_CONTENTTYPE);
		download_rconfig_headers = jeeveshelper.Headers(HEADERS.AUTHORIZATION, HEADERS.ACCEPT_XML_FORMAT,
				HEADERS.MULTIPART_CONTENTTYPE);
		upload_rconfig_headers = jeeveshelper.Headers(HEADERS.AUTHORIZATION, HEADERS.ACCEPT,
				HEADERS.MULTIPART_CONTENTTYPE);
		download_replenishmentSet_headers = jeeveshelper.Headers(HEADERS.AUTHORIZATION, HEADERS.ACCEPT_XML_FORMAT,
				HEADERS.CONTENTTYPE);
	}

	@Test(enabled = true, dataProvider = "CreateTsrOi", dataProviderClass = buyingandplanningDP.class, groups = {
			"sanity", "order-indents" }, description = "Create OI & PI Test")
	public void create_pi_from_tsr_oi_test(String tenant, String config_query, String oi_query) throws Exception {

		// Download tenant rconfig
		jeeveshelper.downloadRconfigForBuAndValidateBu(tenant, BUSINESS_UNITS.MENS_CASUAL_FOOTWEAR,
				download_rconfig_headers);

		// upload the downloaded rconfig
		jeeveshelper.uploadRconfigForBu(DATA_FILES.downloaded_rconfig, tenant, upload_rconfig_headers);

		// Run rconfig and wait till it gets completed
		jeeveshelper.runTsrRconfigAndWaitTillSuccess(tenant, BUSINESS_UNITS.MENS_CASUAL_FOOTWEAR, config_query,
				create_baseoi_headers);

		// GENERATE OI
		jeeveshelper.generateOIAndWaitTillSuccess(tenant, PAYLOADS.MENS_CAUSAL_FOOTWEAR, oi_query,
				create_baseoi_headers);

		/*// get any OI ID for creating pi from the generated order indents
		String OI_ID = jeeveshelper.getOiData(DATA_FILES.OI_query, "id", create_baseoi_headers);

		// get VENDOR ID FOR vendor to cm mapping
		String vendor_id = jeeveshelper.getOiData(DATA_FILES.OI_query, "vendorId", create_baseoi_headers);

		// vendor to cm mapping for creating pi
		jeeveshelper.vendorToCmMapping(vendor_id, "22", create_baseoi_headers);

		// construct payload
		String updateOI_headers_payload = baseOI.updateOIPayload(benetton);

		// updateOI headers
		String update_OI_response = jeeveshelper.updateOIHeaders(OI_ID, updateOI_headers_payload,
				create_baseoi_headers);
		jeevesvalidator.validateTheResponseStatus(update_OI_response, "UPDATE_OI_HEADERS");

		// update OI status from draft to pending_with_partner
		jeeveshelper.updateOIStatus(OI_ID, DATA_FILES.pending_with_partner_state, create_baseoi_headers, ROLES.CM_ROLE);

		// update the OI status from pending_with_partner to pending_with_myntra
		jeeveshelper.updateOIStatus(OI_ID, DATA_FILES.pending_with_myntra_state, create_baseoi_headers,
				"role=vendor&vendorId=" + vendor_id);

		// create PI
		String create_pi_response = jeeveshelper.createPI(OI_ID, create_baseoi_headers);
		jeevesvalidator.validateTheResponseStatus(create_pi_response, "CREATE_PI");*/

	}

	@Test(enabled = true, dataProvider = "BulkUploadTsrOi", dataProviderClass = buyingandplanningDP.class, groups = {
			"sanity", "order-indents" }, description = "Update OI & create PI Test")
	public void bulk_upload_of_tsr_oi_test(String tenant, String config_query, String oi_query) throws Exception {

		// Download tenant rconfig
		jeeveshelper.downloadRconfigForBuAndValidateBu(tenant, BUSINESS_UNITS.MENS_CASUAL_FOOTWEAR, download_rconfig_headers);

		// upload the downloaded rconfig
		jeeveshelper.uploadRconfigForBu(DATA_FILES.downloaded_file, tenant, upload_rconfig_headers);

		// Run rconfig and wait till it gets completed
		jeeveshelper.runTsrRconfigAndWaitTillSuccess(tenant, BUSINESS_UNITS.MENS_CASUAL_FOOTWEAR, config_query,
				create_baseoi_headers);

		// GENERATE OI
		jeeveshelper.generateOIAndWaitTillSuccess(tenant, PAYLOADS.MENS_CAUSAL_FOOTWEAR, oi_query, create_baseoi_headers);

	/*	// get any OI ID for creating pi from the generated order indents
		String OI_ID = jeeveshelper.getOiData(DATA_FILES.OI_query, "id", create_baseoi_headers);

		// get VENDOR ID FOR vendor to cm mapping
		String vendor_id = jeeveshelper.getOiData(DATA_FILES.OI_query, "vendorId", create_baseoi_headers);

		// Download OI sheet
		jeeveshelper.downloadOI(OI_ID, download_baseoi_headers, ROLES.CM_ROLE, DATA_FILES.downloaded_file);

		// Update OI by uploading a modified oi sheet
		jeeveshelper.bulkUpdateOIAndWaitTillSuccess(Constants.DATA_FILES.downloaded_file, OI_ID, create_baseoi_headers,
				upload_baseoi_headers);

		// update the OI status from draft to pending_with_partner
		jeeveshelper.updateOIStatus(OI_ID, DATA_FILES.pending_with_partner_state, create_baseoi_headers, ROLES.CM_ROLE);

		// update the OI status from pending_with_partner to pending_with_myntra
		jeeveshelper.updateOIStatus(OI_ID, DATA_FILES.pending_with_myntra_state, create_baseoi_headers,
				ROLES.VENDOR_ROLE + vendor_id);

		// vendor to cm mapping
		jeeveshelper.vendorToCmMapping(vendor_id, ROLES.CM_ID, create_baseoi_headers);

		// construct payload
		String updateOI_headers_payload = baseOI.updateOIPayload(benetton);

		// updateOI headers
		String update_OI_response = jeeveshelper.updateOIHeaders(OI_ID, updateOI_headers_payload,
				create_baseoi_headers);
		jeevesvalidator.validateTheResponseStatus(update_OI_response, "UPDATE_OI_HEADERS");

		// create PI
		String create_pi_response = jeeveshelper.createPI(OI_ID, create_baseoi_headers);
		jeevesvalidator.validateTheResponseStatus(create_pi_response, "CREATE_PI");*/
	}

	@Test(enabled = true, dataProvider = "ReplenishmentSummaryAndStyle", dataProviderClass = buyingandplanningDP.class, groups = {
			"sanity", "order-indents" }, description = "get summary and style data for BU in tenants")
	public void get_summary_and_style_data_for_bu_in_tenant(String tenant, String config_query) throws Exception {

		// Run rconfig and wait till it gets completed
		jeeveshelper.runTsrRconfigAndWaitTillSuccess(tenant, BUSINESS_UNITS.SPORTS, config_query,
				create_baseoi_headers);

		// get the summary data for the BU in tenant
		String summary_data = jeeveshelper.getSummaryDataForBu(tenant, BUSINESS_UNITS.SPORTS, DATA_FILES.filter,
				create_baseoi_headers);
		
		// validate summary is fetched or not 
		jeevesvalidator.validateResponseObjectArrayLength(jeeveshelper.getReplenishmentSummaryData(summary_data), "no summary data for the bu: " + BUSINESS_UNITS.SPORTS);
		
		// get the Replenishment set(styles) for the BU in tenant
		String replenishmentset_resp = jeeveshelper.getReplenishmentSet(tenant, BUSINESS_UNITS.SPORTS,
				create_baseoi_headers);
		
		// validate style is fetched or not 
		jeevesvalidator.validateResponseObjectArrayLength(jeeveshelper.getReplenishmentStyleData(replenishmentset_resp), "no style data for the bu: " + BUSINESS_UNITS.SPORTS);
	}

	@Test(enabled = true, dataProvider = "MultiTsrTenants", dataProviderClass = buyingandplanningDP.class, groups = {
			"sanity", "order-indents" }, description = "export replenishment set for BU in tenants")
	public void export_replenishmentset_for_bu_in_tenant(String tenant) throws Exception {

		// Download replenishment set sheet
		jeeveshelper.downloadReplenishmentStyles(DATA_FILES.downloaded_file, download_replenishmentSet_headers, tenant,
				BUSINESS_UNITS.SPORTS);

		// Verify all the headers of the replenishment style sheet
		for (int i = 0; i < HEADERS.replenishment_style_sheet_headers.length; i++) {
			jeevesvalidator.validateTheFileData(DATA_FILES.downloaded_file,
					HEADERS.replenishment_style_sheet_headers[i], 0, i, "values didn't match");
		}

		// Verify the sheet has the styles
		for (int i = 1; i < HEADERS.replenishment_style_sheet_headers.length; i++) {
			jeevesvalidator.validateCellsAreNotEmpty(DATA_FILES.downloaded_file, 1, i, 1);
		}

	}

	/**
     * @param tenant
     * @param config_query
     * @param file
     * @throws Exception
     */
    @Test(enabled = true, dataProvider = "ReplenishmentSet", dataProviderClass = buyingandplanningDP.class, groups = {
            "sanity", "order-indents" }, description = "export replenishment set for BU in tenants")
    public void validate_run_config_filters_for_tenants(String tenant, String file, String config_query)
            throws Exception {
        // upload the rconfig
        jeeveshelper.uploadRconfigForBu(file, tenant, upload_rconfig_headers);

        // Run rconfig and wait till it gets completed
        jeeveshelper.runTsrRconfigAndWaitTillSuccess(tenant, BUSINESS_UNITS.MENS_CASUAL, config_query,
                create_baseoi_headers);

        // get the Replenishment set(styles) for the BU in tenant
        String replenishmentset_resp = jeeveshelper.getReplenishmentSet(tenant, BUSINESS_UNITS.MENS_CASUAL,
                create_baseoi_headers);

        // validate all the RCONFIG Header filter values
        jeevesvalidator.validateRconfigFilterValues(jeeveshelper.getReplenishmentStyleData(replenishmentset_resp),
                "daysOnHand", RUN_CONFIG_FILTERS.DOH, "MAX");
        jeevesvalidator.validateRconfigFilterValues(jeeveshelper.getReplenishmentStyleData(replenishmentset_resp),
                "rgmPerUnit", RUN_CONFIG_FILTERS.RGM, "MIN");
        jeevesvalidator.validateRconfigFilterValues(jeeveshelper.getReplenishmentStyleData(replenishmentset_resp),
                "rateOfSale", RUN_CONFIG_FILTERS.ROS, "MIN");
        jeevesvalidator.validateRconfigFilterValues(jeeveshelper.getReplenishmentStyleData(replenishmentset_resp),
                "discount", RUN_CONFIG_FILTERS.DISCOUNT, "MAX");
        jeevesvalidator.validateRconfigFilterValues(jeeveshelper.getReplenishmentStyleData(replenishmentset_resp),
                "age", RUN_CONFIG_FILTERS.AGE, "MAX");
        jeevesvalidator.validateRconfigFilterValues(jeeveshelper.getReplenishmentStyleData(replenishmentset_resp),
                "rateOfReturns", RUN_CONFIG_FILTERS.RATE_OF_RETURNS, "MAX");

    }

    @Test(enabled = true, groups = {
            "sanity", "order-indents" }, dataProvider = "MultiTsrTenants", dataProviderClass = buyingandplanningDP.class, description = "Tenant service tests")
    public void tenant_service_tests(String tenant)
            throws Exception {
    	ObjectMapper mapper = new ObjectMapper();
    	mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    	String tenant_response = jeeveshelper.getTenantService(tenant, create_baseoi_headers);
    	TenantResponse response = mapper.readValue(tenant_response,TenantResponse.class);
    	List<TenantEntry> tenantEntry = response.getData();
    	jeevesvalidator.getTenantEntryAttributes(tenantEntry, tenant);
    	jeevesvalidator.getTenantDefinition(tenantEntry, tenant);  	
    	jeevesvalidator.getRconfiMetadata(tenantEntry);
    	jeevesvalidator.getWorkflowMetadata(tenantEntry, tenant);
    	jeevesvalidator.getWorkspaceMetadata(tenantEntry, tenant);
    	
    }
    
    
}
