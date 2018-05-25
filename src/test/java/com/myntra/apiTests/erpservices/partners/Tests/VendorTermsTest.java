package com.myntra.apiTests.erpservices.partners.Tests;

import com.myntra.apiTests.erpservices.partners.VendorTermsHelper;
import com.myntra.apiTests.erpservices.partners.dp.VendorTermsDP;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.terms.client.code.TermsSuccessCodes;
import com.myntra.terms.client.code.utils.TermStatus;
import com.myntra.terms.client.code.utils.YesNoType;
import com.myntra.terms.client.response.ContractPartiesResponse;
import com.myntra.terms.client.response.TermsResponse;
import com.myntra.terms.client.response.VendorTermsResponse;
import com.myntra.apiTests.SERVICE_TYPE;
import com.myntra.test.commons.service.Svc;
import com.myntra.test.commons.testbase.BaseTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import java.util.Date;
import java.util.List;

public class VendorTermsTest extends BaseTest {
	static Logger log = LoggerFactory.getLogger(VendorTermsTest.class);
	public static Initialize init=new Initialize("Data/configuration");;
	public static VendorTermsHelper vendorTermsHelper=new VendorTermsHelper();
	public static Long poVendorTermId = 0L;
	public static Long marketingVendorTermId = 0L;
	public static Long toiVendorTermId = 0L;
	public static Long catalogVendorTermId = 0L;
	public static Long deleteVendorTermId = 0L;
	public static Long poVersion;
	public static Long sentBackPoVersion;
	public static Long marketingPoVersion;
	public static Long toiPoVersion;
	public static Long catalogPoVersion;
	public static TermsResponse termsResponse;
	public static VendorTermsResponse vendorTermsResponse;
	public static VendorTermsDP vendorTermsDP=new VendorTermsDP();;



	@Test(enabled = true, groups = { "sanity",
			"Regression" }, description = "Gets Taxonomy Additional Classification details from Taxonomy details, fetches Active Records only")
	public void getTaxonomyAdditionalClassification() throws Exception {
		VendorTermsResponse vendorTermsResponse = (VendorTermsResponse) vendorTermsHelper
				.getTaxonomyAdditionalClassification();
		Assert.assertEquals(vendorTermsResponse.getStatus().getStatusCode(), 3,
				"Get Taxonomy Additional Classification status code : ");
		long dbTotalAddtnClassificationRecords = vendorTermsHelper.getdbTaxonomyAdditionalClassificationCount();
		Assert.assertEquals(vendorTermsResponse.getStatus().getTotalCount(), dbTotalAddtnClassificationRecords,
				"total Taxonomy Additional Classification records : ");

	}

	@Test(enabled = true, groups = { "sanity",
			"Regression" }, description = "Gets Taxonomy AgreementType details from Taxonomy details, fetches Active Records only")
	public void getTaxonomyAgreementType() throws Exception {
		VendorTermsResponse vendorTermsResponse = (VendorTermsResponse) vendorTermsHelper.getTaxonomyAgreementType();
		Assert.assertEquals(vendorTermsResponse.getStatus().getStatusCode(), 3,
				"Get Taxonomy AgreementType status code : ");
		long dbTotalAgreementTypeRecords = vendorTermsHelper.getdbAgreement_typeCount();
		Assert.assertEquals(vendorTermsResponse.getStatus().getTotalCount(), dbTotalAgreementTypeRecords,
				"total AgreementTypeRecords : ");

	}

	@Test(enabled = true, groups = { "sanity",
			"Regression" }, dataProviderClass = VendorTermsDP.class, dataProvider = "getBrandsByVendorId", description = "Gets Brands associated by the Vendor from vendor_brand table, fetches Active Records only")
	public void getBrandsByVendorId(long vendorId) throws Exception {
		VendorTermsResponse vendorTermsResponse = (VendorTermsResponse) vendorTermsHelper.getBrandsByVendorId(vendorId);
		Assert.assertEquals(vendorTermsResponse.getStatus().getStatusCode(), 3, "Get Brands status code : ");
		long dbTotalAgreementTypeRecords = vendorTermsHelper.getdbBrandCountByVendorId(vendorId);
		Assert.assertEquals(vendorTermsResponse.getStatus().getTotalCount(), dbTotalAgreementTypeRecords,
				"total Brands : ");

	}

	@Test(enabled = true, groups = { "sanity",
			"Regression" }, description = "Gets Catalog MasterCategory details, fetches Active Records only")
	public void getCatalogMasterCategoryList() throws Exception {
		VendorTermsResponse vendorTermsResponse = (VendorTermsResponse) vendorTermsHelper
				.getCatalogMasterCategoryList();
		Assert.assertEquals(vendorTermsResponse.getStatus().getStatusCode(), 3,
				"Get Catalog MasterCategory statuscode : ");
		// long
		// dbTotalAgreementTypeRecords=vendorTermsHelper.getdbBrandCountByVendorId(vendorId);
		// Assert.assertEquals(vendorTermsResponse.getStatus().getTotalCount(),dbTotalAgreementTypeRecords,"total
		// Brands : ");

	}

	@Test(enabled = true, groups = { "sanity",
			"Regression" }, description = "Gets Taxonomy Gender details, fetches Active Records only")
	public void getTaxonomyGenderList() throws Exception {
		VendorTermsResponse vendorTermsResponse = (VendorTermsResponse) vendorTermsHelper.getTaxonomyGenderList();
		Assert.assertEquals(vendorTermsResponse.getStatus().getStatusCode(), 3, "Get Taxonomy Gender status code : ");
		long dbTotalAgreementTypeRecords = vendorTermsHelper.getdbTaxonomyGenderCount();
		Assert.assertEquals(vendorTermsResponse.getStatus().getTotalCount(), dbTotalAgreementTypeRecords,
				"total Brands : ");

	}

	@Test(enabled = true, groups = { "sanity",
			"Regression" }, dataProviderClass = VendorTermsDP.class, dataProvider = "getAllArticleTypesUnderMasterCategoryId", description = "Gets all the article types available under the MasterCategory table by MasterCategoryId, fetches Active Records only")
	public void getAllArticleTypesUnderMasterCategoryId(long masterCategoryId) throws Exception {
		VendorTermsResponse vendorTermsResponse = (VendorTermsResponse) vendorTermsHelper
				.getAllArticleTypesUnderMasterCategoryId(masterCategoryId);
		Assert.assertEquals(vendorTermsResponse.getStatus().getStatusCode(), 3, "Get all Article Type status code : ");
		// long
		// dbTotalAgreementTypeRecords=vendorTermsHelper.getdbBrandCountByVendorId(vendorId);
		// Assert.assertEquals(vendorTermsResponse.getStatus().getTotalCount(),dbTotalAgreementTypeRecords,"total
		// Brands : ");

	}

	@Test(enabled = true, groups = { "sanity",
			"Regression" }, dataProviderClass = VendorTermsDP.class, dataProvider = "getSingleContractPartyData", description = "Gets Single Contract party data, fetches Active Records only")
	public void getSingleContractPartyData(long accountId, long partyId, long contractTypeId) throws Exception {
		ContractPartiesResponse contractPartiesResponse = (ContractPartiesResponse) vendorTermsHelper
				.getSingleContractPartyData(accountId, partyId, contractTypeId);
		Assert.assertEquals(contractPartiesResponse.getStatus().getStatusCode(), 103,
				"Get single contract status code : ");

	}

	@Test(enabled = true, groups = { "sanity",
			"Regression" }, dataProviderClass = VendorTermsDP.class, dataProvider = "getAllContractsPartyData", description = "Gets all the contract details by accountId and contractTypeId, fetches Active Records only")
	public void getAllContractsPartyData(long accountId, long contractTypeId) throws Exception {
		ContractPartiesResponse contractPartiesResponse = (ContractPartiesResponse) vendorTermsHelper
				.getAllContractsPartyData(accountId, contractTypeId);
		Assert.assertEquals(contractPartiesResponse.getStatus().getStatusCode(), 103,
				"Get all contracts status code : ");

	}

	@Test(enabled = true, groups = { "sanity",
			"Regression" }, dataProviderClass = VendorTermsDP.class, dataProvider = "getContractByContractPartyId", description = "Gets Contract details by contractPartyId, fetches Active Records only")
	public void getContractByContractPartyId(long contractPartyId) throws Exception {
		ContractPartiesResponse contractPartiesResponse = (ContractPartiesResponse) vendorTermsHelper
				.getContractByContractPartyId(contractPartyId);
		Assert.assertEquals(contractPartiesResponse.getStatus().getStatusCode(), 103, "Get contract status code : ");

	}

	@Test(enabled = false, groups = { "sanity",
			"Regression" }, dataProviderClass = VendorTermsDP.class, dataProvider = "searchPoTerm", description = "Search PO with the search criteria provided, fetches Active Records only")
	public void searchPoTerm(long accountId, long contractTypeId, List<Long> brandIds, List<Long> partyIds,
			Date agreementStartDateFrom, Date agreementStartDateTo, Date createdDateFrom, Date createdDateTo,
			List<TermStatus> termStatuses) throws Exception {
		TermsResponse termsResponse = vendorTermsHelper.searchPoTerm(accountId, contractTypeId, brandIds, partyIds,
				agreementStartDateFrom, agreementStartDateTo, createdDateFrom, createdDateTo, termStatuses);
		Assert.assertEquals(termsResponse.getVendorTermsResponse().getStatus().getStatusCode(),
				TermsSuccessCodes.SUCCESS.getStatusCode(), "Search PO Term status code : ");

	}

	@Test(enabled = true, groups = { "sanity",
			"Regression" }, dataProviderClass = VendorTermsDP.class, dataProvider = "createNewVendorTerm", description = "creates vendor term if it is not in db, else will update existing entry")
	public void createNewVendorTerm(String payload) throws Exception {
		Svc service = vendorTermsHelper.createVendorTerm(payload);
		TermsResponse termsResponse = (TermsResponse) APIUtilities.getJsontoObject(service.getResponseBody(),
				new TermsResponse());
		vendorTermsResponse = (VendorTermsResponse) termsResponse.getVendorTermsResponse();
		Assert.assertEquals(vendorTermsResponse.getStatus().getStatusCode(), TermsSuccessCodes.SUCCESS.getStatusCode(),
				"Create new Vendor term status code : ");

		poVendorTermId = Long.valueOf(vendorTermsHelper.getTermResponseValue(service, "id"));
		poVersion = Long.valueOf(vendorTermsHelper.getTermResponseValue(service, "version"));
		log.debug("Vendor Term Created, Id : " + poVendorTermId);
		Assert.assertEquals(vendorTermsHelper.getTermResponseValue(service, "termStatus"),
				TermStatus.DRAFT.toString().toUpperCase());

	}

	@Test(enabled = true, groups = { "sanity",
			"Regression" }, dependsOnMethods = "createNewVendorTerm", dataProviderClass = VendorTermsDP.class, dataProvider = "updateVendorTerm", description = "updates vendor term details")
	public void updateVendorTerm(String payload) throws Exception {
		Svc service = vendorTermsHelper.updateVendorTerm(payload);
		TermsResponse termsResponse = (TermsResponse) APIUtilities.getJsontoObject(service.getResponseBody(),
				new TermsResponse());
		vendorTermsResponse = (VendorTermsResponse) termsResponse.getVendorTermsResponse();
		Assert.assertEquals(vendorTermsResponse.getStatus().getStatusCode(), TermsSuccessCodes.SUCCESS.getStatusCode(),
				"Update Vendor term status code : ");
		long updatedpoVersion = Long.valueOf(vendorTermsHelper.getTermResponseValue(service, "version"));
		Assert.assertEquals(updatedpoVersion, poVersion + 1, "Term updation Failed : ");
		poVersion = updatedpoVersion;

	}

	@Test(enabled = true, groups = { "sanity",
			"Regression" }, dependsOnMethods = "updateVendorTerm", dataProviderClass = VendorTermsDP.class, dataProvider = "sendVendorTermForActivation", description = "Sends an Activation request for vendor term")
	public void sendVendorTermForActivation(String payload) throws Exception {
		Svc service = vendorTermsHelper.updateVendorTerm(payload);
		TermsResponse termsResponse = (TermsResponse) APIUtilities.getJsontoObject(service.getResponseBody(),
				new TermsResponse());
		vendorTermsResponse = (VendorTermsResponse) termsResponse.getVendorTermsResponse();
		Assert.assertEquals(vendorTermsResponse.getStatus().getStatusCode(), TermsSuccessCodes.SUCCESS.getStatusCode(),
				"Activate Vendor term status code : ");
		long updatedpoVersion = Long.valueOf(vendorTermsHelper.getTermResponseValue(service, "version"));
		Assert.assertEquals(updatedpoVersion, poVersion + 1, "Send Term for Activation Failed - Verison: ");
		String termStatus = vendorTermsHelper.getTermResponseValue(service, "termStatus");
		log.debug("Term status :" + termStatus);
		Assert.assertEquals(termStatus, "PENDING_ACTIVATION");
		poVersion = updatedpoVersion;
	}

	@Test(enabled = true, groups = { "sanity",
			"Regression" }, dependsOnMethods = "sendVendorTermForActivation", dataProviderClass = VendorTermsDP.class, dataProvider = "activateVendorTerm", description = "Approves an Vendor Term which is in 'Pending Activation' status")
	public void activateVendorTerm(String payload) throws Exception {
		Svc service = vendorTermsHelper.updateVendorTerm(payload);
		TermsResponse termsResponse = (TermsResponse) APIUtilities.getJsontoObject(service.getResponseBody(),
				new TermsResponse());
		vendorTermsResponse = (VendorTermsResponse) termsResponse.getVendorTermsResponse();
		Assert.assertEquals(vendorTermsResponse.getStatus().getStatusCode(), TermsSuccessCodes.SUCCESS.getStatusCode(),
				"Activate Vendor term status code : ");
		long updatedpoVersion = Long.valueOf(vendorTermsHelper.getTermResponseValue(service, "version"));
		Assert.assertEquals(updatedpoVersion, poVersion + 1, "Term Activation Failed : ");
		String termStatus = vendorTermsHelper.getTermResponseValue(service, "termStatus");
		log.debug("Term status :" + termStatus);
		Assert.assertEquals(termStatus, TermStatus.ACTIVE.toString().toUpperCase());
		poVersion = updatedpoVersion;
	}

	@Test(enabled = true, groups = { "sanity",
			"Regression" }, dependsOnMethods = "activateVendorTerm", dataProviderClass = VendorTermsDP.class, dataProvider = "putOnHoldactivateVendorTerm", description = "Approves an Vendor Term which is in 'Pending Activation' status")
	public void putOnHoldactivateVendorTerm(String payload) throws Exception {
		Svc service = vendorTermsHelper.updateVendorTerm(payload);
		TermsResponse termsResponse = (TermsResponse) APIUtilities.getJsontoObject(service.getResponseBody(),
				new TermsResponse());
		vendorTermsResponse = (VendorTermsResponse) termsResponse.getVendorTermsResponse();
		Assert.assertEquals(vendorTermsResponse.getStatus().getStatusCode(), TermsSuccessCodes.SUCCESS.getStatusCode(),
				"Put On Hold Vendor term status code : ");
		long updatedpoVersion = Long.valueOf(vendorTermsHelper.getTermResponseValue(service, "version"));
		Assert.assertEquals(updatedpoVersion, poVersion + 1, "Term Put On Hold Failed  poVersion: ");
		String termStatus = vendorTermsHelper.getTermResponseValue(service, "termStatus");
		log.debug("Put On Hold Term status :" + termStatus);
		Assert.assertEquals(termStatus, TermStatus.ACTIVE.toString().toUpperCase());
		Assert.assertEquals(vendorTermsHelper.getTermResponseValue(service, "isOnHold"),
				YesNoType.YES.toString().toUpperCase());
		poVersion = updatedpoVersion;
	}

	@Test(enabled = true, groups = { "sanity",
			"Regression" }, dependsOnMethods = "putOnHoldactivateVendorTerm", dataProviderClass = VendorTermsDP.class, dataProvider = "putOffHoldactivateVendorTerm", description = "Removes Hold-on flag for an Vendor Term which is in 'Active' status")
	public void putOffHoldactivateVendorTerm(String payload) throws Exception {
		Svc service = vendorTermsHelper.updateVendorTerm(payload);
		TermsResponse termsResponse = (TermsResponse) APIUtilities.getJsontoObject(service.getResponseBody(),
				new TermsResponse());
		vendorTermsResponse = (VendorTermsResponse) termsResponse.getVendorTermsResponse();
		Assert.assertEquals(vendorTermsResponse.getStatus().getStatusCode(), TermsSuccessCodes.SUCCESS.getStatusCode(),
				"Put Off Hold Vendor term status code : ");
		long updatedpoVersion = Long.valueOf(vendorTermsHelper.getTermResponseValue(service, "version"));
		Assert.assertEquals(updatedpoVersion, poVersion + 1, "Term Put Off Hold Failed  poVersion: ");
		String termStatus = vendorTermsHelper.getTermResponseValue(service, "termStatus");
		log.debug("Put Off Hold Term status :" + termStatus);
		Assert.assertEquals(termStatus, TermStatus.ACTIVE.toString().toUpperCase());
		Assert.assertEquals(vendorTermsHelper.getTermResponseValue(service, "isOnHold"),
				YesNoType.NO.toString().toUpperCase());
		poVersion = updatedpoVersion;
	}

	@Test(enabled = true, groups = { "sanity",
			"Regression" }, dependsOnMethods = "putOffHoldactivateVendorTerm", dataProviderClass = VendorTermsDP.class, dataProvider = "archiveVendorTerm", description = "Archives a vendor term which is in Active Status")
	public void archiveVendorTerm(String payload) throws Exception {
		Svc service = vendorTermsHelper.updateVendorTerm(payload);
		TermsResponse termsResponse = (TermsResponse) APIUtilities.getJsontoObject(service.getResponseBody(),
				new TermsResponse());
		vendorTermsResponse = (VendorTermsResponse) termsResponse.getVendorTermsResponse();
		Assert.assertEquals(vendorTermsResponse.getStatus().getStatusCode(), TermsSuccessCodes.SUCCESS.getStatusCode(),
				"Archive Vendor term status code : ");
		long updatedpoVersion = Long.valueOf(vendorTermsHelper.getTermResponseValue(service, "version"));
		Assert.assertEquals(updatedpoVersion, poVersion + 1, "Term Archival Failed - poVersion : ");
		String termStatus = vendorTermsHelper.getTermResponseValue(service, "termStatus");
		log.debug("Term status :" + termStatus);
		Assert.assertEquals(termStatus, TermStatus.ARCHIVED.toString().toUpperCase());
		poVersion = updatedpoVersion;

	}

	@Test(enabled = true, groups = { "sanity",
			"Regression" }, description = "Approver sends back a vendor term which was sent for Activation")
	public void sentBackVendorTerm() throws Exception {
		// Creating a new Vendor Term
		String payload = vendorTermsDP.createvendorTermPOMessageForSentBack();
		Svc service = vendorTermsHelper.createVendorTerm(payload);
		TermsResponse termsResponse = (TermsResponse) APIUtilities.getJsontoObject(service.getResponseBody(),
				new TermsResponse());
		vendorTermsResponse = (VendorTermsResponse) termsResponse.getVendorTermsResponse();
		Assert.assertEquals(vendorTermsResponse.getStatus().getStatusCode(), TermsSuccessCodes.SUCCESS.getStatusCode(),
				"Create new Marketing Vendor term status code : ");

		deleteVendorTermId = Long.valueOf(vendorTermsHelper.getTermResponseValue(service, "id"));
		sentBackPoVersion = Long.valueOf(vendorTermsHelper.getTermResponseValue(service, "version"));
		String payloadForActivation = vendorTermsDP.createvendorTermPOMessageForSendActivation(deleteVendorTermId,
				sentBackPoVersion);

		// Sending Vendor Term for Activation
		service = vendorTermsHelper.updateVendorTerm(payloadForActivation);
		termsResponse = (TermsResponse) APIUtilities.getJsontoObject(service.getResponseBody(), new TermsResponse());
		vendorTermsResponse = (VendorTermsResponse) termsResponse.getVendorTermsResponse();
		Assert.assertEquals(vendorTermsResponse.getStatus().getStatusCode(), TermsSuccessCodes.SUCCESS.getStatusCode(),
				"Activate Vendor term status code : ");
		long updatedpoVersion = Long.valueOf(vendorTermsHelper.getTermResponseValue(service, "version"));
		Assert.assertEquals(updatedpoVersion, sentBackPoVersion + 1, "Send Term for Activation Failed - Verison: ");
		String termStatus = vendorTermsHelper.getTermResponseValue(service, "termStatus");
		log.debug("Term status :" + termStatus);
		Assert.assertEquals(termStatus, "PENDING_ACTIVATION");
		sentBackPoVersion = updatedpoVersion;

		// Sending back vendorTerm to Edit

		String payloadForSentBack = vendorTermsDP.createVendorTermMessageForSendBack(deleteVendorTermId,
				sentBackPoVersion);
		service = vendorTermsHelper.updateVendorTerm(payloadForSentBack);
		termsResponse = (TermsResponse) APIUtilities.getJsontoObject(service.getResponseBody(), new TermsResponse());
		vendorTermsResponse = (VendorTermsResponse) termsResponse.getVendorTermsResponse();
		Assert.assertEquals(vendorTermsResponse.getStatus().getStatusCode(), TermsSuccessCodes.SUCCESS.getStatusCode(),
				"Send Back Vendor term status code : ");
		updatedpoVersion = Long.valueOf(vendorTermsHelper.getTermResponseValue(service, "version"));
		Assert.assertEquals(updatedpoVersion, sentBackPoVersion + 1, "Term Send Back Failed : ");
		termStatus = vendorTermsHelper.getTermResponseValue(service, "termStatus");
		log.debug("Term status :" + termStatus);
		Assert.assertEquals(termStatus, "SENT_BACK");
		sentBackPoVersion = updatedpoVersion;

	}

	@Test(enabled = true, groups = { "sanity", "Regression" }, dependsOnMethods = {
			"sentBackVendorTerm" }, description = "Deletes a vendor term which is in Draft Status")
	public void deleteVendorTerm() throws Exception {
		String payloadForDelete = vendorTermsDP.createvendorTermPOMessageForDelete(deleteVendorTermId,
				sentBackPoVersion);
		Svc service = vendorTermsHelper.deleteVendorTerm(payloadForDelete);
		termsResponse = (TermsResponse) APIUtilities.getJsontoObject(service.getResponseBody(), new TermsResponse());
		vendorTermsResponse = (VendorTermsResponse) termsResponse.getVendorTermsResponse();
		Assert.assertEquals(vendorTermsResponse.getStatus().getStatusCode(), TermsSuccessCodes.SUCCESS.getStatusCode(),
				"Delete Vendor term status code : ");

	}

	@Test(enabled = true, groups = { "sanity",
			"Regression" }, dataProviderClass = VendorTermsDP.class, dataProvider = "createNewMarketingNonPoVendorTerm", description = "creates a new Non-PO Marketing vendor term if it is not in db, else will update existing entry")
	public void createNewMarketingNonPoVendorTerm(String payload) throws Exception {
		Svc service = vendorTermsHelper.createVendorTerm(payload);
		TermsResponse termsResponse = (TermsResponse) APIUtilities.getJsontoObject(service.getResponseBody(),
				new TermsResponse());
		vendorTermsResponse = (VendorTermsResponse) termsResponse.getVendorTermsResponse();
		Assert.assertEquals(vendorTermsResponse.getStatus().getStatusCode(), TermsSuccessCodes.SUCCESS.getStatusCode(),
				"Create new Marketing Vendor term status code : ");

		marketingVendorTermId = Long.valueOf(vendorTermsHelper.getTermResponseValue(service, "id"));
		marketingPoVersion = Long.valueOf(vendorTermsHelper.getTermResponseValue(service, "version"));
		log.debug("Marketing Vendor Term Created, Id : " + marketingVendorTermId);
		Assert.assertEquals(vendorTermsHelper.getTermResponseValue(service, "termStatus"),
				TermStatus.DRAFT.toString().toUpperCase());

	}

	@Test(enabled = true, groups = { "sanity", "Regression" }, dependsOnMethods = {
			"createNewMarketingNonPoVendorTerm" }, dataProviderClass = VendorTermsDP.class, dataProvider = "updateMarketingNonPoVendorTerm", description = "updates an existing Non-PO Marketing vendor term.")
	public void updateMarketingNonPoVendorTerm(String payload) throws Exception {
		Svc service = vendorTermsHelper.updateVendorTerm(payload);
		TermsResponse termsResponse = (TermsResponse) APIUtilities.getJsontoObject(service.getResponseBody(),
				new TermsResponse());
		vendorTermsResponse = (VendorTermsResponse) termsResponse.getVendorTermsResponse();
		Assert.assertEquals(vendorTermsResponse.getStatus().getStatusCode(), TermsSuccessCodes.SUCCESS.getStatusCode(),
				"Update Marketing Vendor term status code : ");

		long updatedMarketingPoVersion = Long.valueOf(vendorTermsHelper.getTermResponseValue(service, "version"));
		log.debug("Update Marketing Vendor Term Created, Id : " + marketingVendorTermId);
		Assert.assertEquals(vendorTermsHelper.getTermResponseValue(service, "termStatus"),
				TermStatus.DRAFT.toString().toUpperCase());

		Assert.assertEquals(updatedMarketingPoVersion, marketingPoVersion + 1, "Term updation Failed : ");
		marketingPoVersion = updatedMarketingPoVersion;

	}

	@Test(enabled = true, groups = { "sanity",
			"Regression" }, dependsOnMethods = "updateMarketingNonPoVendorTerm", dataProviderClass = VendorTermsDP.class, dataProvider = "sendMarketingVendorTermForActivation", description = "Sends an Activation request for Marketing vendor term")
	public void sendMarketingVendorTermForActivation(String payload) throws Exception {
		Svc service = vendorTermsHelper.updateVendorTerm(payload);
		TermsResponse termsResponse = (TermsResponse) APIUtilities.getJsontoObject(service.getResponseBody(),
				new TermsResponse());
		vendorTermsResponse = (VendorTermsResponse) termsResponse.getVendorTermsResponse();
		Assert.assertEquals(vendorTermsResponse.getStatus().getStatusCode(), TermsSuccessCodes.SUCCESS.getStatusCode(),
				"Activate Marketing Vendor term status code : ");
		long updatedMarketingPoVersion = Long.valueOf(vendorTermsHelper.getTermResponseValue(service, "version"));
		Assert.assertEquals(updatedMarketingPoVersion, marketingPoVersion + 1,
				"Send Marketing Term for Activation Failed - Verison: ");
		String termStatus = vendorTermsHelper.getTermResponseValue(service, "termStatus");
		log.debug("Term status :" + termStatus);
		Assert.assertEquals(termStatus, "PENDING_ACTIVATION");
		marketingPoVersion = updatedMarketingPoVersion;
	}

	@Test(enabled = true, groups = { "sanity",
			"Regression" }, dependsOnMethods = "sendMarketingVendorTermForActivation", dataProviderClass = VendorTermsDP.class, dataProvider = "activateMarketingVendorTerm", description = "Approves an Marketing Vendor Term which is in 'Pending Activation' status")
	public void activateMarketingVendorTerm(String payload) throws Exception {
		Svc service = vendorTermsHelper.updateVendorTerm(payload);
		TermsResponse termsResponse = (TermsResponse) APIUtilities.getJsontoObject(service.getResponseBody(),
				new TermsResponse());
		vendorTermsResponse = (VendorTermsResponse) termsResponse.getVendorTermsResponse();
		Assert.assertEquals(vendorTermsResponse.getStatus().getStatusCode(), TermsSuccessCodes.SUCCESS.getStatusCode(),
				"Activate Vendor term status code : ");
		long updatedMarketingPoVersion = Long.valueOf(vendorTermsHelper.getTermResponseValue(service, "version"));
		Assert.assertEquals(updatedMarketingPoVersion, marketingPoVersion + 1, "Marketing Term Activation Failed : ");
		String termStatus = vendorTermsHelper.getTermResponseValue(service, "termStatus");
		log.debug("Marketing Term status :" + termStatus);
		Assert.assertEquals(termStatus, TermStatus.ACTIVE.toString().toUpperCase());
		marketingPoVersion = updatedMarketingPoVersion;
	}

	@Test(enabled = true, groups = { "sanity",
			"Regression" }, dependsOnMethods = "activateMarketingVendorTerm", dataProviderClass = VendorTermsDP.class, dataProvider = "putOnHoldMarketingVendorTerm", description = "Puts Hold on flag set to 1 for an Marketing Vendor Term which is in 'Active' status")
	public void putOnHoldMarketingVendorTerm(String payload) throws Exception {
		Svc service = vendorTermsHelper.updateVendorTerm(payload);
		TermsResponse termsResponse = (TermsResponse) APIUtilities.getJsontoObject(service.getResponseBody(),
				new TermsResponse());
		vendorTermsResponse = (VendorTermsResponse) termsResponse.getVendorTermsResponse();
		Assert.assertEquals(vendorTermsResponse.getStatus().getStatusCode(), TermsSuccessCodes.SUCCESS.getStatusCode(),
				"Put On Hold Marketing Vendor term status code : ");
		long updatedMarketingPoVersion = Long.valueOf(vendorTermsHelper.getTermResponseValue(service, "version"));
		Assert.assertEquals(updatedMarketingPoVersion, marketingPoVersion + 1,
				"Marketing Term Put On Hold Failed  poVersion: ");
		String termStatus = vendorTermsHelper.getTermResponseValue(service, "termStatus");
		log.debug("Marketing Put On Hold Term status :" + termStatus);
		Assert.assertEquals(termStatus, TermStatus.ACTIVE.toString().toUpperCase());
		Assert.assertEquals(vendorTermsHelper.getTermResponseValue(service, "isOnHold"),
				YesNoType.YES.toString().toUpperCase());
		marketingPoVersion = updatedMarketingPoVersion;
	}

	@Test(enabled = true, groups = { "sanity",
			"Regression" }, dependsOnMethods = "putOnHoldMarketingVendorTerm", dataProviderClass = VendorTermsDP.class, dataProvider = "putOffHoldMarketingVendorTerm", description = "Removes Hold-on flag for an Marketing Vendor Term which is in 'Active-on Hold' status")
	public void putOffHoldMarketingVendorTerm(String payload) throws Exception {
		Svc service = vendorTermsHelper.updateVendorTerm(payload);
		TermsResponse termsResponse = (TermsResponse) APIUtilities.getJsontoObject(service.getResponseBody(),
				new TermsResponse());
		vendorTermsResponse = (VendorTermsResponse) termsResponse.getVendorTermsResponse();
		Assert.assertEquals(vendorTermsResponse.getStatus().getStatusCode(), TermsSuccessCodes.SUCCESS.getStatusCode(),
				"Put Off Hold Marketing Vendor term status code : ");
		long updatedMarketingPoVersion = Long.valueOf(vendorTermsHelper.getTermResponseValue(service, "version"));
		Assert.assertEquals(updatedMarketingPoVersion, marketingPoVersion + 1,
				"Marketing Term Put Off Hold Failed  poVersion: ");
		String termStatus = vendorTermsHelper.getTermResponseValue(service, "termStatus");
		log.debug("Put Off Hold Term status :" + termStatus);
		Assert.assertEquals(termStatus, TermStatus.ACTIVE.toString().toUpperCase());
		Assert.assertEquals(vendorTermsHelper.getTermResponseValue(service, "isOnHold"),
				YesNoType.NO.toString().toUpperCase());
		marketingPoVersion = updatedMarketingPoVersion;
	}

	@Test(enabled = true, groups = { "sanity",
			"Regression" }, dependsOnMethods = "putOffHoldMarketingVendorTerm", dataProviderClass = VendorTermsDP.class, dataProvider = "archiveMarketingVendorTerm", description = "Archives a Marketing vendor term which is in Active Status")
	public void archiveMarketingVendorTerm(String payload) throws Exception {
		Svc service = vendorTermsHelper.updateVendorTerm(payload);
		TermsResponse termsResponse = (TermsResponse) APIUtilities.getJsontoObject(service.getResponseBody(),
				new TermsResponse());
		vendorTermsResponse = (VendorTermsResponse) termsResponse.getVendorTermsResponse();
		Assert.assertEquals(vendorTermsResponse.getStatus().getStatusCode(), TermsSuccessCodes.SUCCESS.getStatusCode(),
				"Archive Marketing Vendor term status code : ");
		long updatedMarketingPoVersion = Long.valueOf(vendorTermsHelper.getTermResponseValue(service, "version"));
		Assert.assertEquals(updatedMarketingPoVersion, marketingPoVersion + 1,
				"Markeing Term Archival Failed - poVersion : ");
		String termStatus = vendorTermsHelper.getTermResponseValue(service, "termStatus");
		log.debug("Term status :" + termStatus);
		Assert.assertEquals(termStatus, TermStatus.ARCHIVED.toString().toUpperCase());
		marketingPoVersion = updatedMarketingPoVersion;

	}

	@Test(enabled = true, groups = { "sanity",
			"Regression" }, dataProviderClass = VendorTermsDP.class, dataProvider = "createNewCatalogNonPoVendorTerm", description = "creates a new Non-PO Catalog vendor term if it is not in db, else will update existing entry")
	public void createNewCatalogNonPoVendorTerm(String payload) throws Exception {
		Svc service = vendorTermsHelper.createVendorTerm(payload);
		TermsResponse termsResponse = (TermsResponse) APIUtilities.getJsontoObject(service.getResponseBody(),
				new TermsResponse());
		vendorTermsResponse = (VendorTermsResponse) termsResponse.getVendorTermsResponse();
		Assert.assertEquals(vendorTermsResponse.getStatus().getStatusCode(), TermsSuccessCodes.SUCCESS.getStatusCode(),
				"Create new Catalog Vendor term status code : ");

		catalogVendorTermId = Long.valueOf(vendorTermsHelper.getTermResponseValue(service, "id"));
		catalogPoVersion = Long.valueOf(vendorTermsHelper.getTermResponseValue(service, "version"));
		log.debug("Catalog Vendor Term Created, Id : " + catalogVendorTermId);
		Assert.assertEquals(vendorTermsHelper.getTermResponseValue(service, "termStatus"),
				TermStatus.DRAFT.toString().toUpperCase());

	}

	@Test(enabled = true, groups = { "sanity", "Regression" }, dependsOnMethods = {
			"createNewCatalogNonPoVendorTerm" }, dataProviderClass = VendorTermsDP.class, dataProvider = "updateCatalogNonPoVendorTerm", description = "updates an existing Non-PO Catalog vendor term.")
	public void updateCatalogNonPoVendorTerm(String payload) throws Exception {
		Svc service = vendorTermsHelper.updateVendorTerm(payload);
		TermsResponse termsResponse = (TermsResponse) APIUtilities.getJsontoObject(service.getResponseBody(),
				new TermsResponse());
		vendorTermsResponse = (VendorTermsResponse) termsResponse.getVendorTermsResponse();
		Assert.assertEquals(vendorTermsResponse.getStatus().getStatusCode(), TermsSuccessCodes.SUCCESS.getStatusCode(),
				"Update Catalog Vendor term status code : ");

		long updatedCatalogPoVersion = Long.valueOf(vendorTermsHelper.getTermResponseValue(service, "version"));
		log.debug("Update Marketing Vendor Term Created, Id : " + catalogVendorTermId);
		Assert.assertEquals(vendorTermsHelper.getTermResponseValue(service, "termStatus"),
				TermStatus.DRAFT.toString().toUpperCase());

		Assert.assertEquals(updatedCatalogPoVersion, catalogPoVersion + 1, "Term updation Failed : ");
		catalogPoVersion = updatedCatalogPoVersion;

	}

	@Test(enabled = true, groups = { "sanity",
			"Regression" }, dependsOnMethods = "updateCatalogNonPoVendorTerm", dataProviderClass = VendorTermsDP.class, dataProvider = "sendCatalogVendorTermForActivation", description = "Sends an Activation request for Catalog vendor term")
	public void sendCatalogVendorTermForActivation(String payload) throws Exception {
		Svc service = vendorTermsHelper.updateVendorTerm(payload);
		TermsResponse termsResponse = (TermsResponse) APIUtilities.getJsontoObject(service.getResponseBody(),
				new TermsResponse());
		vendorTermsResponse = (VendorTermsResponse) termsResponse.getVendorTermsResponse();
		Assert.assertEquals(vendorTermsResponse.getStatus().getStatusCode(), TermsSuccessCodes.SUCCESS.getStatusCode(),
				"Activate Catalog Vendor term status code : ");
		long updatedCatalogPoVersion = Long.valueOf(vendorTermsHelper.getTermResponseValue(service, "version"));
		Assert.assertEquals(updatedCatalogPoVersion, catalogPoVersion + 1,
				"Send Catalog Term for Activation Failed - Verison: ");
		String termStatus = vendorTermsHelper.getTermResponseValue(service, "termStatus");
		log.debug("Term status :" + termStatus);
		Assert.assertEquals(termStatus, "PENDING_ACTIVATION");
		catalogPoVersion = updatedCatalogPoVersion;
	}

	@Test(enabled = true, groups = { "sanity",
			"Regression" }, dependsOnMethods = "sendCatalogVendorTermForActivation", dataProviderClass = VendorTermsDP.class, dataProvider = "activateCatalogVendorTerm", description = "Approves an Catalog Vendor Term which is in 'Pending Activation' status")
	public void activateCatalogVendorTerm(String payload) throws Exception {
		Svc service = vendorTermsHelper.updateVendorTerm(payload);
		TermsResponse termsResponse = (TermsResponse) APIUtilities.getJsontoObject(service.getResponseBody(),
				new TermsResponse());
		vendorTermsResponse = (VendorTermsResponse) termsResponse.getVendorTermsResponse();
		Assert.assertEquals(vendorTermsResponse.getStatus().getStatusCode(), TermsSuccessCodes.SUCCESS.getStatusCode(),
				"Activate Vendor term status code : ");
		long updatedCatalogPoVersion = Long.valueOf(vendorTermsHelper.getTermResponseValue(service, "version"));
		Assert.assertEquals(updatedCatalogPoVersion, catalogPoVersion + 1, "Catalog Term Activation Failed : ");
		String termStatus = vendorTermsHelper.getTermResponseValue(service, "termStatus");
		log.debug("Catalog Term status :" + termStatus);
		Assert.assertEquals(termStatus, TermStatus.ACTIVE.toString().toUpperCase());
		catalogPoVersion = updatedCatalogPoVersion;
	}

	@Test(enabled = true, groups = { "sanity",
			"Regression" }, dependsOnMethods = "activateCatalogVendorTerm", dataProviderClass = VendorTermsDP.class, dataProvider = "putOnHoldCatalogVendorTerm", description = "Puts Hold on flag set to 1 for an Catalog Vendor Term which is in 'Active' status")
	public void putOnHoldCatalogVendorTerm(String payload) throws Exception {
		Svc service = vendorTermsHelper.updateVendorTerm(payload);
		TermsResponse termsResponse = (TermsResponse) APIUtilities.getJsontoObject(service.getResponseBody(),
				new TermsResponse());
		vendorTermsResponse = (VendorTermsResponse) termsResponse.getVendorTermsResponse();
		Assert.assertEquals(vendorTermsResponse.getStatus().getStatusCode(), TermsSuccessCodes.SUCCESS.getStatusCode(),
				"Put On Hold Catalog Vendor term status code : ");
		long updatedCatalogPoVersion = Long.valueOf(vendorTermsHelper.getTermResponseValue(service, "version"));
		Assert.assertEquals(updatedCatalogPoVersion, catalogPoVersion + 1,
				"Catalog Term Put On Hold Failed  poVersion: ");
		String termStatus = vendorTermsHelper.getTermResponseValue(service, "termStatus");
		log.debug("Catalog Put On Hold Term status :" + termStatus);
		Assert.assertEquals(termStatus, TermStatus.ACTIVE.toString().toUpperCase());
		Assert.assertEquals(vendorTermsHelper.getTermResponseValue(service, "isOnHold"),
				YesNoType.YES.toString().toUpperCase());
		catalogPoVersion = updatedCatalogPoVersion;
	}

	@Test(enabled = true, groups = { "sanity",
			"Regression" }, dependsOnMethods = "putOnHoldCatalogVendorTerm", dataProviderClass = VendorTermsDP.class, dataProvider = "putOffHoldCatalogVendorTerm", description = "Removes Hold-on flag for an Catalog Vendor Term which is in 'Active-on Hold' status")
	public void putOffHoldCatalogVendorTerm(String payload) throws Exception {
		Svc service = vendorTermsHelper.updateVendorTerm(payload);
		TermsResponse termsResponse = (TermsResponse) APIUtilities.getJsontoObject(service.getResponseBody(),
				new TermsResponse());
		vendorTermsResponse = (VendorTermsResponse) termsResponse.getVendorTermsResponse();
		Assert.assertEquals(vendorTermsResponse.getStatus().getStatusCode(), TermsSuccessCodes.SUCCESS.getStatusCode(),
				"Put Off Hold Catalog Vendor term status code : ");
		long updatedCatalogPoVersion = Long.valueOf(vendorTermsHelper.getTermResponseValue(service, "version"));
		Assert.assertEquals(updatedCatalogPoVersion, catalogPoVersion + 1,
				"Catalog Term Put Off Hold Failed  poVersion: ");
		String termStatus = vendorTermsHelper.getTermResponseValue(service, "termStatus");
		log.debug("Put Off Hold Term status :" + termStatus);
		Assert.assertEquals(termStatus, TermStatus.ACTIVE.toString().toUpperCase());
		Assert.assertEquals(vendorTermsHelper.getTermResponseValue(service, "isOnHold"),
				YesNoType.NO.toString().toUpperCase());
		catalogPoVersion = updatedCatalogPoVersion;
	}

	@Test(enabled = true, groups = { "sanity",
			"Regression" }, dependsOnMethods = "putOffHoldCatalogVendorTerm", dataProviderClass = VendorTermsDP.class, dataProvider = "archiveCatalogVendorTerm", description = "Archives a Catalog vendor term which is in Active Status")
	public void archiveCatalogVendorTerm(String payload) throws Exception {
		Svc service = vendorTermsHelper.updateVendorTerm(payload);
		TermsResponse termsResponse = (TermsResponse) APIUtilities.getJsontoObject(service.getResponseBody(),
				new TermsResponse());
		vendorTermsResponse = (VendorTermsResponse) termsResponse.getVendorTermsResponse();
		Assert.assertEquals(vendorTermsResponse.getStatus().getStatusCode(), TermsSuccessCodes.SUCCESS.getStatusCode(),
				"Archive Catalog Vendor term status code : ");
		long updatedCatalogPoVersion = Long.valueOf(vendorTermsHelper.getTermResponseValue(service, "version"));
		Assert.assertEquals(updatedCatalogPoVersion, catalogPoVersion + 1,
				"Catalog Term Archival Failed - poVersion : ");
		String termStatus = vendorTermsHelper.getTermResponseValue(service, "termStatus");
		log.debug("Term status :" + termStatus);
		Assert.assertEquals(termStatus, TermStatus.ARCHIVED.toString().toUpperCase());
		catalogPoVersion = updatedCatalogPoVersion;

	}

	@Test(enabled = true, groups = { "sanity",
			"Regression" }, dataProviderClass = VendorTermsDP.class, dataProvider = "createNewTOINonPoVendorTerm", description = "creates a new Non-PO TOI vendor term if it is not in db, else will update existing entry")
	public void createNewTOINonPoVendorTerm(String payload) throws Exception {
		Svc service = vendorTermsHelper.createVendorTerm(payload);
		TermsResponse termsResponse = (TermsResponse) APIUtilities.getJsontoObject(service.getResponseBody(),
				new TermsResponse());
		vendorTermsResponse = (VendorTermsResponse) termsResponse.getVendorTermsResponse();
		Assert.assertEquals(vendorTermsResponse.getStatus().getStatusCode(), TermsSuccessCodes.SUCCESS.getStatusCode(),
				"Create new TOI Vendor term status code : ");

		toiVendorTermId = Long.valueOf(vendorTermsHelper.getTermResponseValue(service, "id"));
		toiPoVersion = Long.valueOf(vendorTermsHelper.getTermResponseValue(service, "version"));
		log.debug("TOI Vendor Term Created, Id : " + toiVendorTermId);
		Assert.assertEquals(vendorTermsHelper.getTermResponseValue(service, "termStatus"),
				TermStatus.DRAFT.toString().toUpperCase());

	}

	@Test(enabled = true, groups = { "sanity", "Regression" }, dependsOnMethods = {
			"createNewTOINonPoVendorTerm" }, dataProviderClass = VendorTermsDP.class, dataProvider = "updateTOINonPoVendorTerm", description = "updates an existing Non-PO TOI vendor term.")
	public void updateTOINonPoVendorTerm(String payload) throws Exception {
		Svc service = vendorTermsHelper.updateVendorTerm(payload);
		TermsResponse termsResponse = (TermsResponse) APIUtilities.getJsontoObject(service.getResponseBody(),
				new TermsResponse());
		vendorTermsResponse = (VendorTermsResponse) termsResponse.getVendorTermsResponse();
		Assert.assertEquals(vendorTermsResponse.getStatus().getStatusCode(), TermsSuccessCodes.SUCCESS.getStatusCode(),
				"Update TOI Vendor term status code : ");

		long updatedtoiVersion = Long.valueOf(vendorTermsHelper.getTermResponseValue(service, "version"));
		log.debug("Update TOI Vendor Term Created, Id : " + catalogVendorTermId);
		Assert.assertEquals(vendorTermsHelper.getTermResponseValue(service, "termStatus"),
				TermStatus.DRAFT.toString().toUpperCase());

		Assert.assertEquals(updatedtoiVersion, toiPoVersion + 1, "Term updation Failed : ");
		toiPoVersion = updatedtoiVersion;

	}

	@Test(enabled = true, groups = { "sanity",
			"Regression" }, dependsOnMethods = "updateTOINonPoVendorTerm", dataProviderClass = VendorTermsDP.class, dataProvider = "sendTOIVendorTermForActivation", description = "Sends an Activation request for TOI vendor term")
	public void sendTOIVendorTermForActivation(String payload) throws Exception {
		Svc service = vendorTermsHelper.updateVendorTerm(payload);
		TermsResponse termsResponse = (TermsResponse) APIUtilities.getJsontoObject(service.getResponseBody(),
				new TermsResponse());
		vendorTermsResponse = (VendorTermsResponse) termsResponse.getVendorTermsResponse();
		Assert.assertEquals(vendorTermsResponse.getStatus().getStatusCode(), TermsSuccessCodes.SUCCESS.getStatusCode(),
				"Activate TOI Vendor term status code : ");
		long updatedtoiVersion = Long.valueOf(vendorTermsHelper.getTermResponseValue(service, "version"));
		Assert.assertEquals(updatedtoiVersion, toiPoVersion + 1,
				"Send TOI Term for Activation Failed - Verison: ");
		String termStatus = vendorTermsHelper.getTermResponseValue(service, "termStatus");
		log.debug("Term status :" + termStatus);
		Assert.assertEquals(termStatus, "PENDING_ACTIVATION");
		toiPoVersion = updatedtoiVersion;
	}

	@Test(enabled = true, groups = { "sanity",
			"Regression" }, dependsOnMethods = "sendTOIVendorTermForActivation", dataProviderClass = VendorTermsDP.class, dataProvider = "activateTOIVendorTerm", description = "Approves an TOI Vendor Term which is in 'Pending Activation' status")
	public void activateTOIVendorTerm(String payload) throws Exception {
		Svc service = vendorTermsHelper.updateVendorTerm(payload);
		TermsResponse termsResponse = (TermsResponse) APIUtilities.getJsontoObject(service.getResponseBody(),
				new TermsResponse());
		vendorTermsResponse = (VendorTermsResponse) termsResponse.getVendorTermsResponse();
		Assert.assertEquals(vendorTermsResponse.getStatus().getStatusCode(), TermsSuccessCodes.SUCCESS.getStatusCode(),
				"Activate TOI Vendor term status code : ");
		long updatedtoiVersion = Long.valueOf(vendorTermsHelper.getTermResponseValue(service, "version"));
		Assert.assertEquals(updatedtoiVersion, toiPoVersion + 1, "TOI Term Activation Failed : ");
		String termStatus = vendorTermsHelper.getTermResponseValue(service, "termStatus");
		log.debug("TOI Term status :" + termStatus);
		Assert.assertEquals(termStatus, TermStatus.ACTIVE.toString().toUpperCase());
		toiPoVersion = updatedtoiVersion;
	}

	@Test(enabled = true, groups = { "sanity",
			"Regression" }, dependsOnMethods = "activateTOIVendorTerm", dataProviderClass = VendorTermsDP.class, dataProvider = "putOnHoldTOIVendorTerm", description = "Puts Hold on flag set to 1 for an TOI Vendor Term which is in 'Active' status")
	public void putOnHoldTOIVendorTerm(String payload) throws Exception {
		Svc service = vendorTermsHelper.updateVendorTerm(payload);
		TermsResponse termsResponse = (TermsResponse) APIUtilities.getJsontoObject(service.getResponseBody(),
				new TermsResponse());
		vendorTermsResponse = (VendorTermsResponse) termsResponse.getVendorTermsResponse();
		Assert.assertEquals(vendorTermsResponse.getStatus().getStatusCode(), TermsSuccessCodes.SUCCESS.getStatusCode(),
				"Put On Hold TOI Vendor term status code : ");
		long updatedtoiVersion = Long.valueOf(vendorTermsHelper.getTermResponseValue(service, "version"));
		Assert.assertEquals(updatedtoiVersion, toiPoVersion + 1,
				"TOI Term Put On Hold Failed  poVersion: ");
		String termStatus = vendorTermsHelper.getTermResponseValue(service, "termStatus");
		log.debug("TOI Put On Hold Term status :" + termStatus);
		Assert.assertEquals(termStatus, TermStatus.ACTIVE.toString().toUpperCase());
		Assert.assertEquals(vendorTermsHelper.getTermResponseValue(service, "isOnHold"),
				YesNoType.YES.toString().toUpperCase());
		toiPoVersion = updatedtoiVersion;
	}

	@Test(enabled = true, groups = { "sanity",
			"Regression" }, dependsOnMethods = "putOnHoldTOIVendorTerm", dataProviderClass = VendorTermsDP.class, dataProvider = "putOffHoldTOIVendorTerm", description = "Removes Hold-on flag for an TOI Vendor Term which is in 'Active-on Hold' status")
	public void putOffHoldTOIVendorTerm(String payload) throws Exception {
		Svc service = vendorTermsHelper.updateVendorTerm(payload);
		TermsResponse termsResponse = (TermsResponse) APIUtilities.getJsontoObject(service.getResponseBody(),
				new TermsResponse());
		vendorTermsResponse = (VendorTermsResponse) termsResponse.getVendorTermsResponse();
		Assert.assertEquals(vendorTermsResponse.getStatus().getStatusCode(), TermsSuccessCodes.SUCCESS.getStatusCode(),
				"Put Off Hold TOI Vendor term status code : ");
		long updatedtoiVersion = Long.valueOf(vendorTermsHelper.getTermResponseValue(service, "version"));
		Assert.assertEquals(updatedtoiVersion, toiPoVersion + 1,
				"TOI Term Put Off Hold Failed  poVersion: ");
		String termStatus = vendorTermsHelper.getTermResponseValue(service, "termStatus");
		log.debug("Put Off Hold Term status :" + termStatus);
		Assert.assertEquals(termStatus, TermStatus.ACTIVE.toString().toUpperCase());
		Assert.assertEquals(vendorTermsHelper.getTermResponseValue(service, "isOnHold"),
				YesNoType.NO.toString().toUpperCase());
		toiPoVersion = updatedtoiVersion;
	}
//
	@Test(enabled = true, groups = { "sanity",
			"Regression" }, dependsOnMethods = "putOffHoldTOIVendorTerm", dataProviderClass = VendorTermsDP.class, dataProvider = "archiveTOIVendorTerm", description = "Archives a TOI vendor term which is in Active Status")
	public void archiveTOIVendorTerm(String payload) throws Exception {
		Svc service = vendorTermsHelper.updateVendorTerm(payload);
		TermsResponse termsResponse = (TermsResponse) APIUtilities.getJsontoObject(service.getResponseBody(),
				new TermsResponse());
		vendorTermsResponse = (VendorTermsResponse) termsResponse.getVendorTermsResponse();
		Assert.assertEquals(vendorTermsResponse.getStatus().getStatusCode(), TermsSuccessCodes.SUCCESS.getStatusCode(),
				"Archive TOI Vendor term status code : ");
		long updatedtoiVersion = Long.valueOf(vendorTermsHelper.getTermResponseValue(service, "version"));
		Assert.assertEquals(updatedtoiVersion, toiPoVersion + 1,
				"TOI Term Archival Failed - poVersion : ");
		String termStatus = vendorTermsHelper.getTermResponseValue(service, "termStatus");
		log.debug("Term status :" + termStatus);
		Assert.assertEquals(termStatus, TermStatus.ARCHIVED.toString().toUpperCase());
		toiPoVersion = updatedtoiVersion;

	}

	@AfterClass(alwaysRun=true)
	public void tearDown() throws Exception {
		vendorTermsHelper.deleteMarketingVendorTerm(marketingVendorTermId);
		vendorTermsHelper.deletePOVendorTerm(poVendorTermId);
		vendorTermsHelper.deletePOVendorTerm(deleteVendorTermId);
		vendorTermsHelper.deleteCatalogVendorTerm(catalogVendorTermId);
		vendorTermsHelper.deleteTOIVendorTerm(toiVendorTermId);

	}

}
