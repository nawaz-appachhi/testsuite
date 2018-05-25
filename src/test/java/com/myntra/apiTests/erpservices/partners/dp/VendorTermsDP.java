package com.myntra.apiTests.erpservices.partners.dp;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.myntra.apiTests.erpservices.partners.Tests.VendorTermsTest;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import com.myntra.lordoftherings.Toolbox;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.terms.client.code.utils.TermAction;
import com.myntra.terms.client.code.utils.TermStatus;
import com.myntra.terms.client.code.utils.TermType;
import com.myntra.terms.client.code.utils.YesNoType;
import com.myntra.terms.client.entry.CatalogTermEntry;
import com.myntra.terms.client.entry.ExternalEntityEntry;
import com.myntra.terms.client.entry.MarketingTermEntry;
import com.myntra.terms.client.entry.POTermEntry;
import com.myntra.terms.client.entry.TOISlabEntry;
import com.myntra.terms.client.entry.TOITermEntry;
import com.myntra.terms.client.entry.TaxonomyEntry;
import com.myntra.terms.client.response.TermsResponse;
import com.myntra.terms.client.response.VendorTermsResponse;

public class VendorTermsDP {

	@DataProvider(name = "getBrandsByVendorId")
	public static Object[][] getBrandsByVendorId(ITestContext testContext) throws Exception {
		Object[] arr1 = { 1 };
		Object[][] dataSet = new Object[][] { arr1, };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 3, 3);

	}

	@DataProvider(name = "getAllArticleTypesUnderMasterCategoryId")
	public static Object[][] getAllArticleTypesUnderMasterCategoryId(ITestContext testContext) throws Exception {
		Object[] arr1 = { 9 };
		Object[][] dataSet = new Object[][] { arr1, };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 3, 3);

	}

	/**
	 * 1. AccountId 2.PartyId 3. ContracttypeId
	 **/
	@DataProvider(name = "getSingleContractPartyData")
	public static Object[][] getSingleContractPartyData(ITestContext testContext) throws Exception {
		Object[] arr1 = { 1, 1, 1 };
		Object[][] dataSet = new Object[][] { arr1, };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 3, 3);

	}

	/**
	 * 1. AccountId 2. ContracttypeId
	 **/
	@DataProvider(name = "getAllContractsPartyData")
	public static Object[][] getAllContractsPartyData(ITestContext testContext) throws Exception {
		Object[] arr1 = { 1, 1 };
		Object[][] dataSet = new Object[][] { arr1, };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 3, 3);

	}

	/**
	 * 1. ContractPartyId
	 **/
	@DataProvider(name = "getContractByContractPartyId")
	public static Object[][] getContractByContractPartyId(ITestContext testContext) throws Exception {
		Object[] arr1 = { 1 };
		Object[][] dataSet = new Object[][] { arr1, };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 3, 3);

	}

	/**
	 * { "termsSearchType": "FILTER", "accountId": 1, "contractTypeId": 1,
	 * "vendorTermsSearchRequest": { "brandIds": [ 1,2,3,4,21,22 ] },
	 * "partyIds": [ 1,2,3 ], "agreementStartDateFrom": "2011-05-01",
	 * "agreementStartDateTo": "2019-06-01", "createdDateFrom": "2011-05-01",
	 * "createdDateTo": "2019-06-01",
	 * 
	 * "termStatuses": [ "DRAFT", "ACTIVE", "ARCHIVED", "SENT_BACK" ] }
	 **/

	@DataProvider(name = "searchPoTerm")
	public static Object[][] searchPoTerm(ITestContext testContext) throws Exception {
		List<Long> brandIds = new ArrayList<Long>();
		brandIds.add(1L);
		brandIds.add(2L);
		brandIds.add(3L);
		brandIds.add(4L);
		brandIds.add(21L);
		brandIds.add(22L);

		// long[] partyIds={ 1,2,3};
		List<Long> partyIds = new ArrayList<Long>();
		partyIds.add(1L);
		partyIds.add(2L);
		partyIds.add(3L);

		List<TermStatus> termStatuses = new ArrayList<TermStatus>();
		termStatuses.add(TermStatus.DRAFT);
		termStatuses.add(TermStatus.ACTIVE);
		termStatuses.add(TermStatus.ARCHIVED);
		termStatuses.add(TermStatus.SENT_BACK);

		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		// Date dt=new
		Date agreementStartDateFrom = sdf.parse("2011-05-01");
		String newDateString = sdf.format(agreementStartDateFrom);
		System.out.println(newDateString);
		Date agreementStartDateTo = sdf.parse("2019-06-01");
		Date createdDateFrom = sdf.parse("2011-05-01");
		Date createdDateTo = sdf.parse("2019-06-01");
		Object[] arr1 = { 1, 1, brandIds, partyIds, agreementStartDateFrom, agreementStartDateTo, createdDateFrom,
				createdDateTo, termStatuses };
		Object[][] dataSet = new Object[][] { arr1, };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 3, 3);

	}

	/**
	 * { "vendorTermsResponse": { "vendorTermsPoData": [ { "contractId": 3,
	 * "archiveId": -2, "version": 3, "termStatus": "DRAFT", "termType": "PO",
	 * "partyEntry": { "id": 3, "displayName": "JK Industries" },
	 * "agmntStartDate": "2016-09-01", "agmntEndDate": "2017-06-01",
	 * "autoRenewal": "YES", "autoRenewalCycleEntry": { "id": 18, "displayName":
	 * "Yearly" }, "brandTypeEntry": { "id": 23, "displayName": "External" },
	 * "agreementTypeEntry": { "id": 46, "displayName": "OUTRIGHT" },
	 * "masterCategoryEntry": { "id": 9, "displayName": "Apparel" },
	 * "genderEntry": { "id": 6, "displayName": "Women" },
	 * "additionalClassificationEntry": { "id": 97, "displayName":
	 * "Not Applicable" }, "billToEntry": { "id": 20, "displayName":
	 * "Myntra Designs Private Limited" }, "doc": "YES", "isPrimary": "YES",
	 * "marginPercentage": 15.75, "marginBasisEntry": { "id": 62, "displayName":
	 * "Mark Down" }, "creditPeriodDays": 20, "sorSalesCycleEntry": { "id": 63,
	 * "displayName": "Monthly" }, "creditBasisAsOnEntry": { "id": 65,
	 * "displayName": "Date of Invoice" }, "paymentMethodEntry": { "id": 9,
	 * "displayName": "Electronic" }, "creditLimit": 12345.56,
	 * "discountLimitPer": 45, "discountLimitVal": 5467, "discountSharingPer":
	 * 20.25, "discountSharingExcList":
	 * "Old Season Stock,High Margin Stock / EOSS", "allowedDeliveryDays": 12,
	 * "delayedDeliveryPenalty": 100.45, "stockCorrectionPercentage": 10.1,
	 * "stockCorrectionBasisEntry": { "id": 100, "displayName":
	 * "Left Over Stock" }, "stockCorrectionFrequency": 2,
	 * "stockCorrectionExcList": "SMU,Old Season Stock", "minimumMargin": 456,
	 * "purchaseTypeEntry": { "id": 7, "displayName": "CST" }, "brandEntry": {
	 * "id": 126, "displayName": "Wrangler" }, "articleTypeEntry": { "id": 420,
	 * "displayName": "Kurta Pyjama" }, "accountId": 1, "contractTypeId": 1,
	 * "termAction": "FIELD_UPDATE" } ] } }
	 */

	@DataProvider(name = "createNewVendorTerm")
	public static Object[][] createNewVendorTerm(ITestContext testContext) throws Exception {

		VendorTermsDP vendorTermsDP = new VendorTermsDP();
		POTermEntry poTermEntry = vendorTermsDP.createvendorTermPOMessage();
		poTermEntry.setTermStatus(TermStatus.DRAFT);

		List<POTermEntry> vendorTermsPoData = new ArrayList<>();
		vendorTermsPoData.add(poTermEntry);
		VendorTermsResponse vendorTermsResponse = new VendorTermsResponse();
		vendorTermsResponse.setVendorTermsPoData(vendorTermsPoData);

		TermsResponse termsResponse = new TermsResponse();
		termsResponse.setVendorTermsResponse(vendorTermsResponse);

		String payload = APIUtilities.getObjectToJSON(termsResponse);
		System.out.println(payload);

		Object[] arr1 = { payload };
		Object[][] dataSet = new Object[][] { arr1, };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 3, 3);

	}

	@DataProvider(name = "updateVendorTerm")
	public static Object[][] updateVendorTerm(ITestContext testContext) throws Exception {

		VendorTermsDP vendorTermsDP = new VendorTermsDP();
		POTermEntry poTermEntry = vendorTermsDP.createvendorTermPOMessage();
		poTermEntry.setTermStatus(TermStatus.DRAFT);
		poTermEntry.setTermAction(TermAction.FIELD_UPDATE);
		Long id = VendorTermsTest.poVendorTermId;
		poTermEntry.setId(id);

		List<POTermEntry> vendorTermsPoData = new ArrayList<>();
		vendorTermsPoData.add(poTermEntry);
		VendorTermsResponse vendorTermsResponse = new VendorTermsResponse();
		vendorTermsResponse.setVendorTermsPoData(vendorTermsPoData);

		TermsResponse termsResponse = new TermsResponse();
		termsResponse.setVendorTermsResponse(vendorTermsResponse);

		String payload = APIUtilities.getObjectToJSON(termsResponse);
		System.out.println(payload);

		Object[] arr1 = { payload };
		Object[][] dataSet = new Object[][] { arr1, };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 3, 3);

	}

	@DataProvider(name = "sendVendorTermForActivation")
	public static Object[][] sendVendorTermForActivation(ITestContext testContext) throws Exception {

		VendorTermsDP vendorTermsDP = new VendorTermsDP();
		POTermEntry poTermEntry = vendorTermsDP.createvendorTermPOMessage();
		poTermEntry.setTermStatus(TermStatus.DRAFT);
		poTermEntry.setTermAction(TermAction.SEND_FOR_ACTIVATION);
		Long id = VendorTermsTest.poVendorTermId;
		poTermEntry.setId(id);

		List<POTermEntry> vendorTermsPoData = new ArrayList<>();
		vendorTermsPoData.add(poTermEntry);
		VendorTermsResponse vendorTermsResponse = new VendorTermsResponse();
		vendorTermsResponse.setVendorTermsPoData(vendorTermsPoData);

		TermsResponse termsResponse = new TermsResponse();
		termsResponse.setVendorTermsResponse(vendorTermsResponse);

		String payload = APIUtilities.getObjectToJSON(termsResponse);
		System.out.println(payload);

		Object[] arr1 = { payload };
		Object[][] dataSet = new Object[][] { arr1, };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 3, 3);

	}

	@DataProvider(name = "activateVendorTerm")
	public static Object[][] activateVendorTerm(ITestContext testContext) throws Exception {
		VendorTermsDP vendorTermsDP = new VendorTermsDP();
		POTermEntry poTermEntry = vendorTermsDP.createvendorTermPOMessage();
		poTermEntry.setTermStatus(TermStatus.PENDING_ACTIVATION);
		poTermEntry.setTermAction(TermAction.APPROVE);
		Long id = VendorTermsTest.poVendorTermId;
		poTermEntry.setId(id);

		List<POTermEntry> vendorTermsPoData = new ArrayList<>();
		vendorTermsPoData.add(poTermEntry);
		VendorTermsResponse vendorTermsResponse = new VendorTermsResponse();
		vendorTermsResponse.setVendorTermsPoData(vendorTermsPoData);

		TermsResponse termsResponse = new TermsResponse();
		termsResponse.setVendorTermsResponse(vendorTermsResponse);

		String payload = APIUtilities.getObjectToJSON(termsResponse);
		System.out.println(payload);

		Object[] arr1 = { payload };
		Object[][] dataSet = new Object[][] { arr1, };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 3, 3);
	}

	@DataProvider(name = "putOnHoldactivateVendorTerm")
	public static Object[][] putOnHoldactivateVendorTerm(ITestContext testContext) throws Exception {
		VendorTermsDP vendorTermsDP = new VendorTermsDP();
		POTermEntry poTermEntry = vendorTermsDP.createvendorTermPOMessage();
		poTermEntry.setTermStatus(TermStatus.ACTIVE);
		poTermEntry.setTermAction(TermAction.PUT_ON_HOLD);
		poTermEntry.setIsOnHold(YesNoType.NO);
		Long id = VendorTermsTest.poVendorTermId;
		poTermEntry.setId(id);

		List<POTermEntry> vendorTermsPoData = new ArrayList<>();
		vendorTermsPoData.add(poTermEntry);
		VendorTermsResponse vendorTermsResponse = new VendorTermsResponse();
		vendorTermsResponse.setVendorTermsPoData(vendorTermsPoData);

		TermsResponse termsResponse = new TermsResponse();
		termsResponse.setVendorTermsResponse(vendorTermsResponse);

		String payload = APIUtilities.getObjectToJSON(termsResponse);
		System.out.println(payload);

		Object[] arr1 = { payload };
		Object[][] dataSet = new Object[][] { arr1, };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 3, 3);
	}

	@DataProvider(name = "putOffHoldactivateVendorTerm")
	public static Object[][] putOffHoldactivateVendorTerm(ITestContext testContext) throws Exception {
		VendorTermsDP vendorTermsDP = new VendorTermsDP();
		POTermEntry poTermEntry = vendorTermsDP.createvendorTermPOMessage();
		poTermEntry.setTermStatus(TermStatus.ACTIVE);
		poTermEntry.setTermAction(TermAction.PUT_OFF_HOLD);
		poTermEntry.setIsOnHold(YesNoType.YES);
		Long id = VendorTermsTest.poVendorTermId;
		poTermEntry.setId(id);

		List<POTermEntry> vendorTermsPoData = new ArrayList<>();
		vendorTermsPoData.add(poTermEntry);
		VendorTermsResponse vendorTermsResponse = new VendorTermsResponse();
		vendorTermsResponse.setVendorTermsPoData(vendorTermsPoData);

		TermsResponse termsResponse = new TermsResponse();
		termsResponse.setVendorTermsResponse(vendorTermsResponse);

		String payload = APIUtilities.getObjectToJSON(termsResponse);
		System.out.println(payload);

		Object[] arr1 = { payload };
		Object[][] dataSet = new Object[][] { arr1, };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 3, 3);
	}

	@DataProvider(name = "archiveVendorTerm")
	public static Object[][] archiveVendorTerm(ITestContext testContext) throws Exception {

		VendorTermsDP vendorTermsDP = new VendorTermsDP();
		TaxonomyEntry closureReasonEntry = vendorTermsDP.taxonomyEntry(99L, "Testing for Archival of Term");
		POTermEntry poTermEntry = vendorTermsDP.createvendorTermPOMessage();
		Long id = VendorTermsTest.poVendorTermId;
		poTermEntry.setId(id);
		poTermEntry.setTermStatus(TermStatus.ACTIVE);
		poTermEntry.setTermAction(TermAction.ARCHIVE);
		poTermEntry.setClosureReasonEntry(closureReasonEntry);

		List<POTermEntry> vendorTermsPoData = new ArrayList<>();
		vendorTermsPoData.add(poTermEntry);
		VendorTermsResponse vendorTermsResponse = new VendorTermsResponse();
		vendorTermsResponse.setVendorTermsPoData(vendorTermsPoData);

		TermsResponse termsResponse = new TermsResponse();
		termsResponse.setVendorTermsResponse(vendorTermsResponse);

		String payload = APIUtilities.getObjectToJSON(termsResponse);
		System.out.println(payload);

		Object[] arr1 = { payload };
		Object[][] dataSet = new Object[][] { arr1, };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 3, 3);
	}


	public POTermEntry createvendorTermPOMessage() throws Exception {
		VendorTermsDP vendorTermsDP = new VendorTermsDP();

		ExternalEntityEntry partyEntry = vendorTermsDP.externalEntityEntry(1L, "Arvind Mills");

		TaxonomyEntry autoRenewalCycleEntry = vendorTermsDP.taxonomyEntry(18L, "Yearly");

		TaxonomyEntry brandTypeEntry = vendorTermsDP.taxonomyEntry(23L, "External");

		TaxonomyEntry agreementTypeEntry = vendorTermsDP.taxonomyEntry(46L, "OUTRIGHT");

		ExternalEntityEntry masterCategoryEntry = vendorTermsDP.externalEntityEntry(1L, "Accessories");

		TaxonomyEntry genderEntry = vendorTermsDP.taxonomyEntry(6L, "Women");

		TaxonomyEntry additionalClassificationEntry = vendorTermsDP.taxonomyEntry(97L, "Not Applicable");

		TaxonomyEntry billToEntry = vendorTermsDP.taxonomyEntry(20L, "Myntra Designs Private Limited");

		TaxonomyEntry marginBasisEntry = vendorTermsDP.taxonomyEntry(61L, "Gross + Taxes");

		TaxonomyEntry sorSalesCycleEntry = vendorTermsDP.taxonomyEntry(63L, "Monthly");

		TaxonomyEntry creditBasisAsOnEntry = vendorTermsDP.taxonomyEntry(65L, "Date of Invoice");

		TaxonomyEntry paymentMethodEntry = vendorTermsDP.taxonomyEntry(9L, "Electronic");

		TaxonomyEntry stockCorrectionBasisEntry = vendorTermsDP.taxonomyEntry(100L, "Left Over Stock");

		TaxonomyEntry purchaseTypeEntry = vendorTermsDP.taxonomyEntry(7L, "CST");

		ExternalEntityEntry brandEntry = vendorTermsDP.externalEntityEntry(4050L, "Lavie");

		ExternalEntityEntry articleTypeEntry = vendorTermsDP.externalEntityEntry(-1L, "ALL");

		Long contractId = 1L;
		Long archiveId = -2L;
		Double creditLimit = 12345.56;
		Double discountLimitPer = 45D;
		Double discountLimitVal = 5467D;
		Double discountSharingPer = 20.25;
		String discountSharingExcList = "Old Season Stock,High Margin Stock / EOSS";
		int allowedDeliveryDays = 12;
		Double delayedDeliveryPenalty = 100.45;
		Double stockCorrectionPercentage = 10.1;
		int stockCorrectionFrequency = 2;
		String stockCorrectionExcList = "SMU,Old Season Stock";
		Double minimumMargin = 456D;
		Long version = VendorTermsTest.poVersion;
		Double marginPercentage = 15.75D;
		int creditPeriodDays = 20;
		Long accountId = 1L;
		Long contractTypeId = 1L;

		String createdBy = "sravan.kumar";
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date createdOn = sdf.parse("2019-06-01");
		Date lastModifiedOn = sdf.parse("2019-06-01");
		Date agmntStartDate = sdf.parse("2012-05-01");
		Date agmntEndDate = sdf.parse("2017-06-01");

		POTermEntry poTermEntry = new POTermEntry();
		poTermEntry.setAccountId(accountId);
		poTermEntry.setAgmntStartDate(agmntStartDate);
		poTermEntry.setAgmntEndDate(agmntEndDate);
		poTermEntry.setArchiveId(archiveId);
		poTermEntry.setAutoRenewal(YesNoType.YES);
		poTermEntry.setAutoRenewalCycleEntry(autoRenewalCycleEntry);
		poTermEntry.setAgreementTypeEntry(agreementTypeEntry);
		poTermEntry.setArticleTypeEntry(articleTypeEntry);
		poTermEntry.setAdditionalClassificationEntry(additionalClassificationEntry);
		poTermEntry.setAllowedDeliveryDays(allowedDeliveryDays);
		poTermEntry.setBrandEntry(brandEntry);
		poTermEntry.setBrandTypeEntry(brandTypeEntry);
		poTermEntry.setBillToEntry(billToEntry);
		poTermEntry.setContractId(contractId);
		poTermEntry.setCreditLimit(creditLimit);
		poTermEntry.setCreatedBy(createdBy);
		poTermEntry.setCreatedOn(createdOn);
		poTermEntry.setContractTypeId(contractTypeId);
		poTermEntry.setCreditPeriodDays(creditPeriodDays);
		poTermEntry.setCreditBasisAsOnEntry(creditBasisAsOnEntry);
		poTermEntry.setDoc(YesNoType.YES);
		poTermEntry.setDiscountLimitPer(discountLimitPer);
		poTermEntry.setDiscountLimitVal(discountLimitVal);
		poTermEntry.setDiscountSharingPer(discountSharingPer);
		// poTermEntry.setDiscountSharingExcEntryList(discountSharingExcList);
		poTermEntry.setDelayedDeliveryPenalty(delayedDeliveryPenalty);
		poTermEntry.setGenderEntry(genderEntry);
		poTermEntry.setIsPrimary(YesNoType.YES);
		poTermEntry.setLastModifiedOn(lastModifiedOn);
		poTermEntry.setMarginPercentage(marginPercentage);
		poTermEntry.setMasterCategoryEntry(masterCategoryEntry);
		poTermEntry.setMarginBasisEntry(marginBasisEntry);
		poTermEntry.setMinimumMargin(minimumMargin);
		poTermEntry.setPaymentMethodEntry(paymentMethodEntry);
		poTermEntry.setPartyEntry(partyEntry);
		poTermEntry.setPurchaseTypeEntry(purchaseTypeEntry);
		poTermEntry.setStockCorrectionPercentage(stockCorrectionPercentage);
		poTermEntry.setStockCorrectionBasisEntry(stockCorrectionBasisEntry);
		poTermEntry.setStockCorrectionFrequency(stockCorrectionFrequency);
		// poTermEntry.setStockCorrectionExcEntryList(stockCorrectionExcList);
		poTermEntry.setSorSalesCycleEntry(sorSalesCycleEntry);
		poTermEntry.setTermType(TermType.PO);
		poTermEntry.setVersion(version);
		return poTermEntry;

	}

	public POTermEntry createVendorTermMessageForSentBackScenario() throws Exception {
		VendorTermsDP vendorTermsDP = new VendorTermsDP();

		ExternalEntityEntry partyEntry = vendorTermsDP.externalEntityEntry(4L, "Aarthi watches ");

		TaxonomyEntry autoRenewalCycleEntry = vendorTermsDP.taxonomyEntry(18L, "Yearly");

		TaxonomyEntry brandTypeEntry = vendorTermsDP.taxonomyEntry(23L, "External");

		TaxonomyEntry agreementTypeEntry = vendorTermsDP.taxonomyEntry(46L, "OUTRIGHT");

		ExternalEntityEntry masterCategoryEntry = vendorTermsDP.externalEntityEntry(9L, "Apparel");

		TaxonomyEntry genderEntry = vendorTermsDP.taxonomyEntry(6L, "Women");

		TaxonomyEntry additionalClassificationEntry = vendorTermsDP.taxonomyEntry(97L, "Not Applicable");

		TaxonomyEntry billToEntry = vendorTermsDP.taxonomyEntry(20L, "Myntra Designs Private Limited");

		TaxonomyEntry marginBasisEntry = vendorTermsDP.taxonomyEntry(61L, "Gross + Taxes");

		TaxonomyEntry sorSalesCycleEntry = vendorTermsDP.taxonomyEntry(63L, "Monthly");

		TaxonomyEntry creditBasisAsOnEntry = vendorTermsDP.taxonomyEntry(65L, "Date of Invoice");

		TaxonomyEntry paymentMethodEntry = vendorTermsDP.taxonomyEntry(9L, "Electronic");

		TaxonomyEntry stockCorrectionBasisEntry = vendorTermsDP.taxonomyEntry(100L, "Left Over Stock");

		TaxonomyEntry purchaseTypeEntry = vendorTermsDP.taxonomyEntry(7L, "CST");

		ExternalEntityEntry brandEntry = vendorTermsDP.externalEntityEntry(5651L, "Paislei");

		ExternalEntityEntry articleTypeEntry = vendorTermsDP.externalEntityEntry(-1L, "Kurta Pyjama");

		Long contractId = 4L;
		Long archiveId = -2L;
		Double creditLimit = 12345.56;
		Double discountLimitPer = 45D;
		Double discountLimitVal = 5467D;
		Double discountSharingPer = 20.25;
		String discountSharingExcList = "Old Season Stock,High Margin Stock / EOSS";
		int allowedDeliveryDays = 12;
		Double delayedDeliveryPenalty = 100.45;
		Double stockCorrectionPercentage = 10.1;
		int stockCorrectionFrequency = 2;
		String stockCorrectionExcList = "SMU,Old Season Stock";
		Double minimumMargin = 456D;
		Long version = VendorTermsTest.poVersion;
		Double marginPercentage = 15.75D;
		int creditPeriodDays = 20;
		Long accountId = 1L;
		Long contractTypeId = 1L;

		String createdBy = "sravan.kumar";
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date createdOn = sdf.parse("2019-06-01");
		Date lastModifiedOn = sdf.parse("2019-06-01");
		Date agmntStartDate = sdf.parse("2012-05-01");
		Date agmntEndDate = sdf.parse("2017-06-01");

		POTermEntry poTermEntry = new POTermEntry();
		poTermEntry.setAccountId(accountId);
		poTermEntry.setAgmntStartDate(agmntStartDate);
		poTermEntry.setAgmntEndDate(agmntEndDate);
		poTermEntry.setArchiveId(archiveId);
		poTermEntry.setAutoRenewal(YesNoType.YES);
		poTermEntry.setAutoRenewalCycleEntry(autoRenewalCycleEntry);
		poTermEntry.setAgreementTypeEntry(agreementTypeEntry);
		poTermEntry.setArticleTypeEntry(articleTypeEntry);
		poTermEntry.setAdditionalClassificationEntry(additionalClassificationEntry);
		poTermEntry.setAllowedDeliveryDays(allowedDeliveryDays);
		poTermEntry.setBrandEntry(brandEntry);
		poTermEntry.setBrandTypeEntry(brandTypeEntry);
		poTermEntry.setBillToEntry(billToEntry);
		poTermEntry.setContractId(contractId);
		poTermEntry.setCreditLimit(creditLimit);
		poTermEntry.setCreatedBy(createdBy);
		poTermEntry.setCreatedOn(createdOn);
		poTermEntry.setContractTypeId(contractTypeId);
		poTermEntry.setCreditPeriodDays(creditPeriodDays);
		poTermEntry.setCreditBasisAsOnEntry(creditBasisAsOnEntry);
		poTermEntry.setDoc(YesNoType.YES);
		poTermEntry.setDiscountLimitPer(discountLimitPer);
		poTermEntry.setDiscountLimitVal(discountLimitVal);
		poTermEntry.setDiscountSharingPer(discountSharingPer);
		// poTermEntry.setDiscountSharingExcEntryList(discountSharingExcList);
		poTermEntry.setDelayedDeliveryPenalty(delayedDeliveryPenalty);
		poTermEntry.setGenderEntry(genderEntry);
		poTermEntry.setIsPrimary(YesNoType.YES);
		poTermEntry.setLastModifiedOn(lastModifiedOn);
		poTermEntry.setMarginPercentage(marginPercentage);
		poTermEntry.setMasterCategoryEntry(masterCategoryEntry);
		poTermEntry.setMarginBasisEntry(marginBasisEntry);
		poTermEntry.setMinimumMargin(minimumMargin);
		poTermEntry.setPaymentMethodEntry(paymentMethodEntry);
		poTermEntry.setPartyEntry(partyEntry);
		poTermEntry.setPurchaseTypeEntry(purchaseTypeEntry);
		poTermEntry.setStockCorrectionPercentage(stockCorrectionPercentage);
		poTermEntry.setStockCorrectionBasisEntry(stockCorrectionBasisEntry);
		poTermEntry.setStockCorrectionFrequency(stockCorrectionFrequency);
		// poTermEntry.setStockCorrectionExcEntryList(stockCorrectionExcList);
		poTermEntry.setSorSalesCycleEntry(sorSalesCycleEntry);
		poTermEntry.setTermType(TermType.PO);
		return poTermEntry;

	}

	public String createvendorTermPOMessageForSentBack() throws Exception {
		VendorTermsDP vendorTermsDP = new VendorTermsDP();

		List<POTermEntry> vendorTermsPoData = new ArrayList<>();
		POTermEntry poTermEntry = new POTermEntry();
		poTermEntry = vendorTermsDP.createVendorTermMessageForSentBackScenario();

		poTermEntry.setTermStatus(TermStatus.DRAFT);
		vendorTermsPoData.add(poTermEntry);
		VendorTermsResponse vendorTermsResponse = new VendorTermsResponse();
		vendorTermsResponse.setVendorTermsPoData(vendorTermsPoData);

		TermsResponse termsResponse = new TermsResponse();
		termsResponse.setVendorTermsResponse(vendorTermsResponse);

		String payload = APIUtilities.getObjectToJSON(termsResponse);
		System.out.println(payload);

		return payload;

	}

	public String createvendorTermPOMessageForSendActivation(Long vendorTermId, Long version) throws Exception {
		VendorTermsDP vendorTermsDP = new VendorTermsDP();

		POTermEntry poTermEntry = vendorTermsDP.createVendorTermMessageForSentBackScenario();
		poTermEntry.setVersion(version);
		poTermEntry.setId(vendorTermId);
		poTermEntry.setTermStatus(TermStatus.DRAFT);
		poTermEntry.setTermAction(TermAction.SEND_FOR_ACTIVATION);

		List<POTermEntry> vendorTermsPoData = new ArrayList<>();
		vendorTermsPoData.add(poTermEntry);
		VendorTermsResponse vendorTermsResponse = new VendorTermsResponse();
		vendorTermsResponse.setVendorTermsPoData(vendorTermsPoData);

		TermsResponse termsResponse = new TermsResponse();
		termsResponse.setVendorTermsResponse(vendorTermsResponse);

		String payload = APIUtilities.getObjectToJSON(termsResponse);
		System.out.println(payload);

		return payload;

	}

	public String createVendorTermMessageForSendBack(Long id, Long version) throws Exception {

		VendorTermsDP vendorTermsDP = new VendorTermsDP();
		String sentBackRemarks="Testing SentBack feature";

		POTermEntry poTermEntry = vendorTermsDP.createVendorTermMessageForSentBackScenario();
		poTermEntry.setVersion(version);
		poTermEntry.setId(id);
		poTermEntry.setTermStatus(TermStatus.PENDING_ACTIVATION);
		poTermEntry.setTermAction(TermAction.SEND_BACK);
		poTermEntry.setSentBackRemarks(sentBackRemarks);

		List<POTermEntry> vendorTermsPoData = new ArrayList<>();
		vendorTermsPoData.add(poTermEntry);
		VendorTermsResponse vendorTermsResponse = new VendorTermsResponse();
		vendorTermsResponse.setVendorTermsPoData(vendorTermsPoData);

		TermsResponse termsResponse = new TermsResponse();
		termsResponse.setVendorTermsResponse(vendorTermsResponse);

		String payload = APIUtilities.getObjectToJSON(termsResponse);
		System.out.println(payload);

		return payload;

	}

	public String createvendorTermPOMessageForDelete(Long vendorTermId, Long version) throws Exception {
		VendorTermsDP vendorTermsDP = new VendorTermsDP();

		POTermEntry poTermEntry = vendorTermsDP.createVendorTermMessageForSentBackScenario();
		poTermEntry.setVersion(version);
		poTermEntry.setTermStatus(TermStatus.SENT_BACK);
		poTermEntry.setTermAction(TermAction.DELETE);
		poTermEntry.setId(vendorTermId);

		List<POTermEntry> vendorTermsPoData = new ArrayList<>();
		vendorTermsPoData.add(poTermEntry);
		VendorTermsResponse vendorTermsResponse = new VendorTermsResponse();
		vendorTermsResponse.setVendorTermsPoData(vendorTermsPoData);

		TermsResponse termsResponse = new TermsResponse();
		termsResponse.setVendorTermsResponse(vendorTermsResponse);

		String payload = APIUtilities.getObjectToJSON(termsResponse);
		System.out.println(payload);

		return payload;

	}

	public TaxonomyEntry taxonomyEntry(long id, String displayName) {
		TaxonomyEntry taxonomyEntry = new TaxonomyEntry();
		taxonomyEntry.setId(id);
		taxonomyEntry.setDisplayName(displayName);
		return taxonomyEntry;
	}

	public ExternalEntityEntry externalEntityEntry(long id, String displayName) {
		ExternalEntityEntry externalEntityEntry = new ExternalEntityEntry();
		externalEntityEntry.setId(id);
		externalEntityEntry.setDisplayName(displayName);
		return externalEntityEntry;
	}

	public MarketingTermEntry createNonPoMarketingMessage() throws Exception {
		VendorTermsDP vendorTermsDP = new VendorTermsDP();

		ExternalEntityEntry partyEntry = vendorTermsDP.externalEntityEntry(1L, "Arvind Mills");

		TaxonomyEntry autoRenewalCycleEntry = vendorTermsDP.taxonomyEntry(18L, "Yearly");

		TaxonomyEntry brandTypeEntry = vendorTermsDP.taxonomyEntry(23L, "External");

		TaxonomyEntry agreementTypeEntry = vendorTermsDP.taxonomyEntry(46L, "OUTRIGHT");

		ExternalEntityEntry masterCategoryEntry = vendorTermsDP.externalEntityEntry(1L, "Accessories");

		TaxonomyEntry genderEntry = vendorTermsDP.taxonomyEntry(6L, "Women");

		TaxonomyEntry additionalClassificationEntry = vendorTermsDP.taxonomyEntry(97L, "Not Applicable");

		TaxonomyEntry billToEntry = vendorTermsDP.taxonomyEntry(20L, "Myntra Designs Private Limited");

		TaxonomyEntry contributionBasisEntry = vendorTermsDP.taxonomyEntry(68L, "Purchase on MRP");

		TaxonomyEntry calculationContributionBasisEntry = vendorTermsDP.taxonomyEntry(26L,
				"[MRP X [Total Quantity Purchased - Purchase Returns]]");

		TaxonomyEntry collectionFrequencyEntry = vendorTermsDP.taxonomyEntry(78L, "Month");

		ExternalEntityEntry brandEntry = vendorTermsDP.externalEntityEntry(126L, "Wrangler");

		ExternalEntityEntry articleTypeEntry = vendorTermsDP.externalEntityEntry(-1L, "ALL");

		Long contractId = 1L;
		Long archiveId = -2L;
		Double contributionPercentage = 23D;
		Double contributionValue = 2.98D;
		Long accountId = 1L;
		Long contractTypeId = 1L;
		String createdBy = "sravan.kumar";
		String autoRenewalCycleRemarks = "fortnightly";
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date createdOn = sdf.parse("2019-06-01");
		Date lastModifiedOn = sdf.parse("2019-06-01");
		Date agmntStartDate = sdf.parse("2012-05-01");
		Date agmntEndDate = sdf.parse("2017-06-01");
		Long xlsRowId = 112L;

		MarketingTermEntry marketingTermEntry = new MarketingTermEntry();

		marketingTermEntry.setAccountId(accountId);
		marketingTermEntry.setAgmntStartDate(agmntStartDate);
		marketingTermEntry.setAgmntEndDate(agmntEndDate);
		marketingTermEntry.setArchiveId(archiveId);
		marketingTermEntry.setAutoRenewal(YesNoType.YES);
		marketingTermEntry.setAutoRenewalCycleEntry(autoRenewalCycleEntry);
		marketingTermEntry.setAgreementTypeEntry(agreementTypeEntry);
		marketingTermEntry.setAutoRenewalCycleRemarks(autoRenewalCycleRemarks);
		marketingTermEntry.setArticleTypeEntry(articleTypeEntry);
		marketingTermEntry.setAdditionalClassificationEntry(additionalClassificationEntry);
		marketingTermEntry.setBrandEntry(brandEntry);
		marketingTermEntry.setBrandTypeEntry(brandTypeEntry);
		marketingTermEntry.setBillToEntry(billToEntry);
		marketingTermEntry.setContractId(contractId);
		marketingTermEntry.setCreatedBy(createdBy);
		marketingTermEntry.setCreatedOn(createdOn);
		marketingTermEntry.setContractTypeId(contractTypeId);
		marketingTermEntry.setContributionBasisEntry(contributionBasisEntry);
		marketingTermEntry.setContributionPercentage(contributionPercentage);
//		marketingTermEntry.setContributionValue(contributionValue);
		marketingTermEntry.setCalculationContributionBasisEntry(calculationContributionBasisEntry);
		marketingTermEntry.setCollectionFrequencyEntry(collectionFrequencyEntry);
		marketingTermEntry.setGenderEntry(genderEntry);
		marketingTermEntry.setLastModifiedOn(lastModifiedOn);
		marketingTermEntry.setMasterCategoryEntry(masterCategoryEntry);
		marketingTermEntry.setMarketingStartDate(agmntStartDate);
		marketingTermEntry.setMarketingEndDate(agmntEndDate);
		marketingTermEntry.setPartyEntry(partyEntry);
		marketingTermEntry.setTermType(TermType.MARKETING);
		marketingTermEntry.setXlsRowId(xlsRowId);
		return marketingTermEntry;

	}

	@DataProvider(name = "createNewMarketingNonPoVendorTerm")
	public static Object[][] createNewMarketingNonPoVendorTerm(ITestContext testContext) throws Exception {

		VendorTermsDP vendorTermsDP = new VendorTermsDP();
		MarketingTermEntry marketingTermEntry = vendorTermsDP.createNonPoMarketingMessage();
		marketingTermEntry.setTermStatus(TermStatus.DRAFT);
		// poTermEntry.setTermAction(TermAction.FIELD_UPDATE);

		List<MarketingTermEntry> vendorTermsMarketingData = new ArrayList<>();
		vendorTermsMarketingData.add(marketingTermEntry);
		VendorTermsResponse vendorTermsResponse = new VendorTermsResponse();
		vendorTermsResponse.setVendorTermsMarketingData(vendorTermsMarketingData);

		TermsResponse termsResponse = new TermsResponse();
		termsResponse.setVendorTermsResponse(vendorTermsResponse);

		String payload = APIUtilities.getObjectToJSON(termsResponse);
		System.out.println(payload);

		Object[] arr1 = { payload };
		Object[][] dataSet = new Object[][] { arr1, };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 3, 3);

	}

	@DataProvider(name = "updateMarketingNonPoVendorTerm")
	public static Object[][] updateMarketingNonPoVendorTerm(ITestContext testContext) throws Exception {

		VendorTermsDP vendorTermsDP = new VendorTermsDP();
		MarketingTermEntry marketingTermEntry = vendorTermsDP.createNonPoMarketingMessage();

		marketingTermEntry.setId(VendorTermsTest.marketingVendorTermId);
		marketingTermEntry.setVersion(VendorTermsTest.marketingPoVersion);
		marketingTermEntry.setTermStatus(TermStatus.DRAFT);
		marketingTermEntry.setTermAction(TermAction.FIELD_UPDATE);

		List<MarketingTermEntry> vendorTermsMarketingData = new ArrayList<>();
		vendorTermsMarketingData.add(marketingTermEntry);
		VendorTermsResponse vendorTermsResponse = new VendorTermsResponse();
		vendorTermsResponse.setVendorTermsMarketingData(vendorTermsMarketingData);

		TermsResponse termsResponse = new TermsResponse();
		termsResponse.setVendorTermsResponse(vendorTermsResponse);

		String payload = APIUtilities.getObjectToJSON(termsResponse);
		System.out.println(payload);

		Object[] arr1 = { payload };
		Object[][] dataSet = new Object[][] { arr1, };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 3, 3);

	}

	//
	@DataProvider(name = "sendMarketingVendorTermForActivation")
	public static Object[][] sendMarketingVendorTermForActivation(ITestContext testContext) throws Exception {

		VendorTermsDP vendorTermsDP = new VendorTermsDP();
		MarketingTermEntry marketingTermEntry = vendorTermsDP.createNonPoMarketingMessage();

		marketingTermEntry.setId(VendorTermsTest.marketingVendorTermId);
		marketingTermEntry.setVersion(VendorTermsTest.marketingPoVersion);
		marketingTermEntry.setTermStatus(TermStatus.DRAFT);
		marketingTermEntry.setTermAction(TermAction.SEND_FOR_ACTIVATION);

		List<MarketingTermEntry> vendorTermsMarketingData = new ArrayList<>();
		vendorTermsMarketingData.add(marketingTermEntry);
		VendorTermsResponse vendorTermsResponse = new VendorTermsResponse();
		vendorTermsResponse.setVendorTermsMarketingData(vendorTermsMarketingData);

		TermsResponse termsResponse = new TermsResponse();
		termsResponse.setVendorTermsResponse(vendorTermsResponse);

		String payload = APIUtilities.getObjectToJSON(termsResponse);
		System.out.println(payload);

		Object[] arr1 = { payload };
		Object[][] dataSet = new Object[][] { arr1, };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 3, 3);

	}

	//
	@DataProvider(name = "activateMarketingVendorTerm")
	public static Object[][] activateMarketingVendorTerm(ITestContext testContext) throws Exception {

		VendorTermsDP vendorTermsDP = new VendorTermsDP();
		MarketingTermEntry marketingTermEntry = vendorTermsDP.createNonPoMarketingMessage();

		marketingTermEntry.setId(VendorTermsTest.marketingVendorTermId);
		marketingTermEntry.setVersion(VendorTermsTest.marketingPoVersion);
		marketingTermEntry.setTermStatus(TermStatus.PENDING_ACTIVATION);
		marketingTermEntry.setTermAction(TermAction.APPROVE);

		List<MarketingTermEntry> vendorTermsMarketingData = new ArrayList<>();
		vendorTermsMarketingData.add(marketingTermEntry);
		VendorTermsResponse vendorTermsResponse = new VendorTermsResponse();
		vendorTermsResponse.setVendorTermsMarketingData(vendorTermsMarketingData);

		TermsResponse termsResponse = new TermsResponse();
		termsResponse.setVendorTermsResponse(vendorTermsResponse);

		String payload = APIUtilities.getObjectToJSON(termsResponse);
		System.out.println(payload);

		Object[] arr1 = { payload };
		Object[][] dataSet = new Object[][] { arr1, };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 3, 3);

	}

	@DataProvider(name = "putOnHoldMarketingVendorTerm")
	public static Object[][] putOnHoldMarketingVendorTerm(ITestContext testContext) throws Exception {

		VendorTermsDP vendorTermsDP = new VendorTermsDP();
		MarketingTermEntry marketingTermEntry = vendorTermsDP.createNonPoMarketingMessage();

		marketingTermEntry.setId(VendorTermsTest.marketingVendorTermId);
		marketingTermEntry.setVersion(VendorTermsTest.marketingPoVersion);
		marketingTermEntry.setTermStatus(TermStatus.ACTIVE);
		marketingTermEntry.setTermAction(TermAction.PUT_ON_HOLD);
		marketingTermEntry.setIsOnHold(YesNoType.NO);

		List<MarketingTermEntry> vendorTermsMarketingData = new ArrayList<>();
		vendorTermsMarketingData.add(marketingTermEntry);
		VendorTermsResponse vendorTermsResponse = new VendorTermsResponse();
		vendorTermsResponse.setVendorTermsMarketingData(vendorTermsMarketingData);

		TermsResponse termsResponse = new TermsResponse();
		termsResponse.setVendorTermsResponse(vendorTermsResponse);

		String payload = APIUtilities.getObjectToJSON(termsResponse);
		System.out.println(payload);

		Object[] arr1 = { payload };
		Object[][] dataSet = new Object[][] { arr1, };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 3, 3);

	}

	@DataProvider(name = "putOffHoldMarketingVendorTerm")
	public static Object[][] putOffHoldMarketingVendorTerm(ITestContext testContext) throws Exception {

		VendorTermsDP vendorTermsDP = new VendorTermsDP();
		MarketingTermEntry marketingTermEntry = vendorTermsDP.createNonPoMarketingMessage();

		marketingTermEntry.setId(VendorTermsTest.marketingVendorTermId);
		marketingTermEntry.setVersion(VendorTermsTest.marketingPoVersion);
		marketingTermEntry.setTermStatus(TermStatus.ACTIVE);
		marketingTermEntry.setTermAction(TermAction.PUT_OFF_HOLD);
		marketingTermEntry.setIsOnHold(YesNoType.YES);

		List<MarketingTermEntry> vendorTermsMarketingData = new ArrayList<>();
		vendorTermsMarketingData.add(marketingTermEntry);
		VendorTermsResponse vendorTermsResponse = new VendorTermsResponse();
		vendorTermsResponse.setVendorTermsMarketingData(vendorTermsMarketingData);

		TermsResponse termsResponse = new TermsResponse();
		termsResponse.setVendorTermsResponse(vendorTermsResponse);

		String payload = APIUtilities.getObjectToJSON(termsResponse);
		System.out.println(payload);

		Object[] arr1 = { payload };
		Object[][] dataSet = new Object[][] { arr1, };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 3, 3);

	}

	@DataProvider(name = "archiveMarketingVendorTerm")
	public static Object[][] archiveMarketingVendorTerm(ITestContext testContext) throws Exception {

		VendorTermsDP vendorTermsDP = new VendorTermsDP();
		MarketingTermEntry marketingTermEntry = vendorTermsDP.createNonPoMarketingMessage();
		TaxonomyEntry closureReasonEntry = vendorTermsDP.taxonomyEntry(99L, "Testing for Archival of Term");
		marketingTermEntry.setClosureReasonEntry(closureReasonEntry);
		
		marketingTermEntry.setId(VendorTermsTest.marketingVendorTermId);
		marketingTermEntry.setVersion(VendorTermsTest.marketingPoVersion);
		marketingTermEntry.setTermStatus(TermStatus.ACTIVE);
		marketingTermEntry.setTermAction(TermAction.ARCHIVE);
		

		List<MarketingTermEntry> vendorTermsMarketingData = new ArrayList<>();
		vendorTermsMarketingData.add(marketingTermEntry);
		VendorTermsResponse vendorTermsResponse = new VendorTermsResponse();
		vendorTermsResponse.setVendorTermsMarketingData(vendorTermsMarketingData);

		TermsResponse termsResponse = new TermsResponse();
		termsResponse.setVendorTermsResponse(vendorTermsResponse);

		String payload = APIUtilities.getObjectToJSON(termsResponse);
		System.out.println(payload);

		Object[] arr1 = { payload };
		Object[][] dataSet = new Object[][] { arr1, };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 3, 3);

	}
	
	public CatalogTermEntry createNonPoCatalogMessage() throws Exception {
		VendorTermsDP vendorTermsDP = new VendorTermsDP();
		ExternalEntityEntry partyEntry = vendorTermsDP.externalEntityEntry(1L, "Arvind Mills");
		TaxonomyEntry autoRenewalCycleEntry = vendorTermsDP.taxonomyEntry(18L, "Yearly");
		TaxonomyEntry brandTypeEntry = vendorTermsDP.taxonomyEntry(23L, "External");
		TaxonomyEntry agreementTypeEntry = vendorTermsDP.taxonomyEntry(46L, "OUTRIGHT");
		ExternalEntityEntry masterCategoryEntry = vendorTermsDP.externalEntityEntry(1L, "Accessories");
		TaxonomyEntry genderEntry = vendorTermsDP.taxonomyEntry(6L, "Women");
		TaxonomyEntry additionalClassificationEntry = vendorTermsDP.taxonomyEntry(97L, "Not Applicable");
		TaxonomyEntry billToEntry = vendorTermsDP.taxonomyEntry(20L, "Myntra Designs Private Limited");
		TaxonomyEntry contributionBasisEntry = vendorTermsDP.taxonomyEntry(1359L, "Rate Card 1");
		TaxonomyEntry calculationContributionBasisEntry = vendorTermsDP.taxonomyEntry(26L,
				"[MRP X [Total Quantity Purchased - Purchase Returns]]");
		TaxonomyEntry collectionFrequencyEntry = vendorTermsDP.taxonomyEntry(78L, "Month");
		ExternalEntityEntry brandEntry = vendorTermsDP.externalEntityEntry(126L, "Wrangler");
		ExternalEntityEntry articleTypeEntry = vendorTermsDP.externalEntityEntry(-1L, "ALL");

		Long contractId = 1L;
		Long archiveId = -2L;
		Double contributionPercentage = 23D;
		Double contributionValue = 2.98D;
		Long accountId = 1L;
		Long contractTypeId = 1L;
		String createdBy = "sravan.kumar";
		String autoRenewalCycleRemarks = "fortnightly";
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date createdOn = sdf.parse("2019-06-01");
		Date lastModifiedOn = sdf.parse("2019-06-01");
		Date agmntStartDate = sdf.parse("2012-05-01");
		Date agmntEndDate = sdf.parse("2017-06-01");
		Long xlsRowId = 112L;

		CatalogTermEntry catalogTermEntry = new CatalogTermEntry();

		catalogTermEntry.setAccountId(accountId);
		catalogTermEntry.setAgmntStartDate(agmntStartDate);
		catalogTermEntry.setAgmntEndDate(agmntEndDate);
		catalogTermEntry.setArchiveId(archiveId);
		catalogTermEntry.setAutoRenewal(YesNoType.YES);
		catalogTermEntry.setAutoRenewalCycleEntry(autoRenewalCycleEntry);
		catalogTermEntry.setAgreementTypeEntry(agreementTypeEntry);
		catalogTermEntry.setAutoRenewalCycleRemarks(autoRenewalCycleRemarks);
		catalogTermEntry.setArticleTypeEntry(articleTypeEntry);
		catalogTermEntry.setAdditionalClassificationEntry(additionalClassificationEntry);
		catalogTermEntry.setBrandEntry(brandEntry);
		catalogTermEntry.setBrandTypeEntry(brandTypeEntry);
		catalogTermEntry.setBillToEntry(billToEntry);
		catalogTermEntry.setContractId(contractId);
		catalogTermEntry.setCreatedBy(createdBy);
		catalogTermEntry.setCreatedOn(createdOn);
		catalogTermEntry.setContractTypeId(contractTypeId);
		catalogTermEntry.setContributionBasisEntry(contributionBasisEntry);
		catalogTermEntry.setContributionPercentage(contributionPercentage);
		catalogTermEntry.setContributionValue(contributionValue);
		catalogTermEntry.setCollectionFrequencyEntry(collectionFrequencyEntry);
		catalogTermEntry.setGenderEntry(genderEntry);
		catalogTermEntry.setLastModifiedOn(lastModifiedOn);
		catalogTermEntry.setMasterCategoryEntry(masterCategoryEntry);
		catalogTermEntry.setPartyEntry(partyEntry);
		catalogTermEntry.setCatalogStartDate(agmntStartDate);
		catalogTermEntry.setCatalogEndDate(agmntEndDate);
		catalogTermEntry.setTermType(TermType.CATALOG);
		catalogTermEntry.setXlsRowId(xlsRowId);
		return catalogTermEntry;

	}
	
	@DataProvider(name = "createNewCatalogNonPoVendorTerm")
	public static Object[][] createNewCatalogNonPoVendorTerm(ITestContext testContext) throws Exception {

		VendorTermsDP vendorTermsDP = new VendorTermsDP();
		CatalogTermEntry catalogTermEntry = vendorTermsDP.createNonPoCatalogMessage();
		catalogTermEntry.setTermStatus(TermStatus.DRAFT);
		catalogTermEntry.setTermAction(TermAction.CREATE);

		List<CatalogTermEntry> vendorTermsCatalogData = new ArrayList<>();
		vendorTermsCatalogData.add(catalogTermEntry);
		VendorTermsResponse vendorTermsResponse = new VendorTermsResponse();
		vendorTermsResponse.setVendorTermsCatalogData(vendorTermsCatalogData);

		TermsResponse termsResponse = new TermsResponse();
		termsResponse.setVendorTermsResponse(vendorTermsResponse);

		String payload = APIUtilities.getObjectToJSON(termsResponse);
		System.out.println(payload);

		Object[] arr1 = { payload };
		Object[][] dataSet = new Object[][] { arr1, };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 3, 3);

	}
//
	@DataProvider(name = "updateCatalogNonPoVendorTerm")
	public static Object[][] updateCatalogNonPoVendorTerm(ITestContext testContext) throws Exception {
		VendorTermsDP vendorTermsDP = new VendorTermsDP();
		CatalogTermEntry catalogTermEntry = vendorTermsDP.createNonPoCatalogMessage();

		catalogTermEntry.setId(VendorTermsTest.catalogVendorTermId);
		catalogTermEntry.setVersion(VendorTermsTest.catalogPoVersion);
		catalogTermEntry.setTermStatus(TermStatus.DRAFT);
		catalogTermEntry.setTermAction(TermAction.FIELD_UPDATE);

		List<CatalogTermEntry> vendorTermsCatalogData = new ArrayList<>();
		vendorTermsCatalogData.add(catalogTermEntry);
		VendorTermsResponse vendorTermsResponse = new VendorTermsResponse();
		vendorTermsResponse.setVendorTermsCatalogData(vendorTermsCatalogData);

		TermsResponse termsResponse = new TermsResponse();
		termsResponse.setVendorTermsResponse(vendorTermsResponse);

		String payload = APIUtilities.getObjectToJSON(termsResponse);
		System.out.println(payload);

		Object[] arr1 = { payload };
		Object[][] dataSet = new Object[][] { arr1, };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 3, 3);

	}
	@DataProvider(name = "sendCatalogVendorTermForActivation")
	public static Object[][] sendCatalogVendorTermForActivation(ITestContext testContext) throws Exception {
		VendorTermsDP vendorTermsDP = new VendorTermsDP();
		CatalogTermEntry catalogTermEntry = vendorTermsDP.createNonPoCatalogMessage();

		catalogTermEntry.setId(VendorTermsTest.catalogVendorTermId);
		catalogTermEntry.setVersion(VendorTermsTest.catalogPoVersion);
		catalogTermEntry.setTermStatus(TermStatus.DRAFT);
		catalogTermEntry.setTermAction(TermAction.SEND_FOR_ACTIVATION);

		List<CatalogTermEntry> vendorTermsCatalogData = new ArrayList<>();
		vendorTermsCatalogData.add(catalogTermEntry);
		VendorTermsResponse vendorTermsResponse = new VendorTermsResponse();
		vendorTermsResponse.setVendorTermsCatalogData(vendorTermsCatalogData);

		TermsResponse termsResponse = new TermsResponse();
		termsResponse.setVendorTermsResponse(vendorTermsResponse);

		String payload = APIUtilities.getObjectToJSON(termsResponse);
		System.out.println(payload);

		Object[] arr1 = { payload };
		Object[][] dataSet = new Object[][] { arr1, };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 3, 3);

	}
	//
	@DataProvider(name = "activateCatalogVendorTerm")
	public static Object[][] activateCatalogVendorTerm(ITestContext testContext) throws Exception {
		VendorTermsDP vendorTermsDP = new VendorTermsDP();
		CatalogTermEntry catalogTermEntry = vendorTermsDP.createNonPoCatalogMessage();

		catalogTermEntry.setId(VendorTermsTest.catalogVendorTermId);
		catalogTermEntry.setVersion(VendorTermsTest.catalogPoVersion);
		catalogTermEntry.setTermStatus(TermStatus.PENDING_ACTIVATION);
		catalogTermEntry.setTermAction(TermAction.APPROVE);

		List<CatalogTermEntry> vendorTermsCatalogData = new ArrayList<>();
		vendorTermsCatalogData.add(catalogTermEntry);
		VendorTermsResponse vendorTermsResponse = new VendorTermsResponse();
		vendorTermsResponse.setVendorTermsCatalogData(vendorTermsCatalogData);

		TermsResponse termsResponse = new TermsResponse();
		termsResponse.setVendorTermsResponse(vendorTermsResponse);

		String payload = APIUtilities.getObjectToJSON(termsResponse);
		System.out.println(payload);

		Object[] arr1 = { payload };
		Object[][] dataSet = new Object[][] { arr1, };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 3, 3);

	}
	
	
	@DataProvider(name = "putOnHoldCatalogVendorTerm")
	public static Object[][] putOnHoldCatalogVendorTerm(ITestContext testContext) throws Exception {
		VendorTermsDP vendorTermsDP = new VendorTermsDP();
		CatalogTermEntry catalogTermEntry = vendorTermsDP.createNonPoCatalogMessage();

		catalogTermEntry.setId(VendorTermsTest.catalogVendorTermId);
		catalogTermEntry.setVersion(VendorTermsTest.catalogPoVersion);
		catalogTermEntry.setTermStatus(TermStatus.ACTIVE);
		catalogTermEntry.setTermAction(TermAction.PUT_ON_HOLD);
		catalogTermEntry.setIsOnHold(YesNoType.NO);

		List<CatalogTermEntry> vendorTermsCatalogData = new ArrayList<>();
		vendorTermsCatalogData.add(catalogTermEntry);
		VendorTermsResponse vendorTermsResponse = new VendorTermsResponse();
		vendorTermsResponse.setVendorTermsCatalogData(vendorTermsCatalogData);

		TermsResponse termsResponse = new TermsResponse();
		termsResponse.setVendorTermsResponse(vendorTermsResponse);

		String payload = APIUtilities.getObjectToJSON(termsResponse);
		System.out.println(payload);

		Object[] arr1 = { payload };
		Object[][] dataSet = new Object[][] { arr1, };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 3, 3);

	}
	@DataProvider(name = "putOffHoldCatalogVendorTerm")
	public static Object[][] putOffHoldCatalogVendorTerm(ITestContext testContext) throws Exception {
		VendorTermsDP vendorTermsDP = new VendorTermsDP();
		CatalogTermEntry catalogTermEntry = vendorTermsDP.createNonPoCatalogMessage();

		catalogTermEntry.setId(VendorTermsTest.catalogVendorTermId);
		catalogTermEntry.setVersion(VendorTermsTest.catalogPoVersion);
		catalogTermEntry.setTermStatus(TermStatus.ACTIVE);
		catalogTermEntry.setTermAction(TermAction.PUT_OFF_HOLD);
		catalogTermEntry.setIsOnHold(YesNoType.YES);

		List<CatalogTermEntry> vendorTermsCatalogData = new ArrayList<>();
		vendorTermsCatalogData.add(catalogTermEntry);
		VendorTermsResponse vendorTermsResponse = new VendorTermsResponse();
		vendorTermsResponse.setVendorTermsCatalogData(vendorTermsCatalogData);

		TermsResponse termsResponse = new TermsResponse();
		termsResponse.setVendorTermsResponse(vendorTermsResponse);

		String payload = APIUtilities.getObjectToJSON(termsResponse);
		System.out.println(payload);

		Object[] arr1 = { payload };
		Object[][] dataSet = new Object[][] { arr1, };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 3, 3);

	}
	//
	@DataProvider(name = "archiveCatalogVendorTerm")
	public static Object[][] archiveCatalogVendorTerm(ITestContext testContext) throws Exception {
		VendorTermsDP vendorTermsDP = new VendorTermsDP();
		CatalogTermEntry catalogTermEntry = vendorTermsDP.createNonPoCatalogMessage();

		catalogTermEntry.setId(VendorTermsTest.catalogVendorTermId);
		catalogTermEntry.setVersion(VendorTermsTest.catalogPoVersion);
		catalogTermEntry.setTermStatus(TermStatus.ACTIVE);
		catalogTermEntry.setTermAction(TermAction.ARCHIVE);
		TaxonomyEntry closureReasonEntry = vendorTermsDP.taxonomyEntry(99L, "Testing for Archival of Term");
		catalogTermEntry.setClosureReasonEntry(closureReasonEntry);
		

		List<CatalogTermEntry> vendorTermsCatalogData = new ArrayList<>();
		vendorTermsCatalogData.add(catalogTermEntry);
		VendorTermsResponse vendorTermsResponse = new VendorTermsResponse();
		vendorTermsResponse.setVendorTermsCatalogData(vendorTermsCatalogData);

		TermsResponse termsResponse = new TermsResponse();
		termsResponse.setVendorTermsResponse(vendorTermsResponse);

		String payload = APIUtilities.getObjectToJSON(termsResponse);
		System.out.println(payload);

		Object[] arr1 = { payload };
		Object[][] dataSet = new Object[][] { arr1, };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 3, 3);

	}
	
	public TOITermEntry createNonPoTOIMessage() throws Exception {
		VendorTermsDP vendorTermsDP = new VendorTermsDP();
		ExternalEntityEntry partyEntry = vendorTermsDP.externalEntityEntry(1L, "Arvind Mills");
		TaxonomyEntry autoRenewalCycleEntry = vendorTermsDP.taxonomyEntry(18L, "Yearly");
		TaxonomyEntry brandTypeEntry = vendorTermsDP.taxonomyEntry(23L, "External");
		TaxonomyEntry agreementTypeEntry = vendorTermsDP.taxonomyEntry(46L, "OUTRIGHT");
		ExternalEntityEntry masterCategoryEntry = vendorTermsDP.externalEntityEntry(1L, "Accessories");
		TaxonomyEntry genderEntry = vendorTermsDP.taxonomyEntry(6L, "Women");
		TaxonomyEntry additionalClassificationEntry = vendorTermsDP.taxonomyEntry(97L, "Not Applicable");
		TaxonomyEntry billToEntry = vendorTermsDP.taxonomyEntry(20L, "Myntra Designs Private Limited");
		TaxonomyEntry contributionBasisEntry = vendorTermsDP.taxonomyEntry(1359L, "Rate Card 1");
		TaxonomyEntry calculationContributionBasisEntry = vendorTermsDP.taxonomyEntry(26L,
				"[MRP X [Total Quantity Purchased - Purchase Returns]]");
		TaxonomyEntry collectionFrequencyEntry = vendorTermsDP.taxonomyEntry(78L, "Month");
		ExternalEntityEntry brandEntry = vendorTermsDP.externalEntityEntry(126L, "Wrangler");
		ExternalEntityEntry articleTypeEntry = vendorTermsDP.externalEntityEntry(-1L, "ALL");
		
		TaxonomyEntry toiAchievementBasisEntry = vendorTermsDP.taxonomyEntry(76L, "Net Sales Value");
		TaxonomyEntry calculationTOIAchievementBasisEntry = vendorTermsDP.taxonomyEntry(33L, "[[MRP - Discount borne by Supplier] X [Total Quantity Sold - Customer Returns]]");
		
		TaxonomyEntry toiClaimBasisEntry = vendorTermsDP.taxonomyEntry(75L, "MRP Sales Value");
		TaxonomyEntry calculationTOIClaimBasisEntry = vendorTermsDP.taxonomyEntry(33L, "[MRP X [Total Quantity Sold - Customer Returns]]");
		TaxonomyEntry exclusionEntryList=vendorTermsDP.taxonomyEntry(82L, "SMU");
		List<TaxonomyEntry> toiExclusionEntryList=new ArrayList<TaxonomyEntry>();
		toiExclusionEntryList.add(exclusionEntryList);

		Long contractId = 1L;
		Long archiveId = -2L;
		Double contributionPercentage = 23D;
		Double contributionValue = 2.98D;
		Long accountId = 1L;
		Long contractTypeId = 1L;
		String createdBy = "sravan.kumar";
		String autoRenewalCycleRemarks = "fortnightly";
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date createdOn = sdf.parse("2019-06-01");
		Date lastModifiedOn = sdf.parse("2019-06-01");
		Date agmntStartDate = sdf.parse("2012-05-01");
		Date agmntEndDate = sdf.parse("2017-06-01");
		Long xlsRowId = 112L;

		TOITermEntry toiTermEntry = new TOITermEntry();
		

		toiTermEntry.setAccountId(accountId);
		toiTermEntry.setAgmntStartDate(agmntStartDate);
		toiTermEntry.setAgmntEndDate(agmntEndDate);
		toiTermEntry.setArchiveId(archiveId);
		toiTermEntry.setAutoRenewal(YesNoType.YES);
		toiTermEntry.setAutoRenewalCycleEntry(autoRenewalCycleEntry);
		toiTermEntry.setAgreementTypeEntry(agreementTypeEntry);
		toiTermEntry.setAutoRenewalCycleRemarks(autoRenewalCycleRemarks);
		toiTermEntry.setArticleTypeEntry(articleTypeEntry);
		toiTermEntry.setAdditionalClassificationEntry(additionalClassificationEntry);
		TOISlabEntry toiSlabEntry=new TOISlabEntry();
		toiSlabEntry.setSlab(1.0);

		toiSlabEntry.setIncentive(3.24);
		toiSlabEntry.setRemarks("test remarks");
		
		List<TOISlabEntry> toiSlabEntries=new ArrayList<>();
		toiSlabEntries.add(toiSlabEntry);
		toiTermEntry.setToiSlabEntries(toiSlabEntries);
		
		toiTermEntry.setBrandEntry(brandEntry);
		toiTermEntry.setBrandTypeEntry(brandTypeEntry);
		toiTermEntry.setBillToEntry(billToEntry);
		toiTermEntry.setContractId(contractId);
		toiTermEntry.setCreatedBy(createdBy);
		toiTermEntry.setCreatedOn(createdOn);
		toiTermEntry.setContractTypeId(contractTypeId);
		toiTermEntry.setCalculationTOIAchievementBasisEntry(calculationTOIAchievementBasisEntry);
		toiTermEntry.setCalculationTOIClaimBasisEntry(calculationTOIClaimBasisEntry);
//		toiTermEntry.setContributionBasisEntry(contributionBasisEntry);
//		toiTermEntry.setContributionPercentage(contributionPercentage);
//		toiTermEntry.setContributionValue(contributionValue);
//		toiTermEntry.setCollectionFrequencyEntry(collectionFrequencyEntry);
		toiTermEntry.setGenderEntry(genderEntry);
		toiTermEntry.setLastModifiedOn(lastModifiedOn);
		toiTermEntry.setMasterCategoryEntry(masterCategoryEntry);
		toiTermEntry.setPartyEntry(partyEntry);
		toiTermEntry.setTermType(TermType.TOI);
		toiTermEntry.setToiAchievementBasisEntry(toiAchievementBasisEntry);	
		toiTermEntry.setToiClaimBasisEntry(toiClaimBasisEntry);
		toiTermEntry.setToiExclusionEntryList(toiExclusionEntryList);
		toiTermEntry.setToiStartDate(agmntStartDate);
		toiTermEntry.setToiEndDate(agmntEndDate);
		toiTermEntry.setXlsRowId(xlsRowId);
		return toiTermEntry;

	}
	
	@DataProvider(name = "createNewTOINonPoVendorTerm")
	public static Object[][] createNewTOINonPoVendorTerm(ITestContext testContext) throws Exception {

		VendorTermsDP vendorTermsDP = new VendorTermsDP();
		TOITermEntry toiTermEntry = vendorTermsDP.createNonPoTOIMessage();
		toiTermEntry.setTermStatus(TermStatus.DRAFT);
		toiTermEntry.setTermAction(TermAction.CREATE);

		List<TOITermEntry> vendorTermsTOIData = new ArrayList<>();
		vendorTermsTOIData.add(toiTermEntry);
		VendorTermsResponse vendorTermsResponse = new VendorTermsResponse();
		vendorTermsResponse.setVendorTermsToiData(vendorTermsTOIData);

		TermsResponse termsResponse = new TermsResponse();
		termsResponse.setVendorTermsResponse(vendorTermsResponse);

		String payload = APIUtilities.getObjectToJSON(termsResponse);
		System.out.println(payload);

		Object[] arr1 = { payload };
		Object[][] dataSet = new Object[][] { arr1, };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 3, 3);

	}
	//
	@DataProvider(name = "updateTOINonPoVendorTerm")
	public static Object[][] updateTOINonPoVendorTerm(ITestContext testContext) throws Exception {

		VendorTermsDP vendorTermsDP = new VendorTermsDP();
		TOITermEntry toiTermEntry = vendorTermsDP.createNonPoTOIMessage();
		toiTermEntry.setId(VendorTermsTest.toiVendorTermId);
		toiTermEntry.setVersion(VendorTermsTest.toiPoVersion);
		toiTermEntry.setTermStatus(TermStatus.DRAFT);
		toiTermEntry.setTermAction(TermAction.FIELD_UPDATE);
		
		List<TOITermEntry> vendorTermsTOIData = new ArrayList<>();
		vendorTermsTOIData.add(toiTermEntry);
		VendorTermsResponse vendorTermsResponse = new VendorTermsResponse();
		vendorTermsResponse.setVendorTermsToiData(vendorTermsTOIData);

		TermsResponse termsResponse = new TermsResponse();
		termsResponse.setVendorTermsResponse(vendorTermsResponse);

		String payload = APIUtilities.getObjectToJSON(termsResponse);
		System.out.println(payload);

		Object[] arr1 = { payload };
		Object[][] dataSet = new Object[][] { arr1, };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 3, 3);

	}
	
	@DataProvider(name = "sendTOIVendorTermForActivation")
	public static Object[][] sendTOIVendorTermForActivation(ITestContext testContext) throws Exception {

		VendorTermsDP vendorTermsDP = new VendorTermsDP();
		TOITermEntry toiTermEntry = vendorTermsDP.createNonPoTOIMessage();
		toiTermEntry.setId(VendorTermsTest.toiVendorTermId);
		toiTermEntry.setVersion(VendorTermsTest.toiPoVersion);
		toiTermEntry.setTermStatus(TermStatus.DRAFT);
		toiTermEntry.setTermAction(TermAction.SEND_FOR_ACTIVATION);
		

		List<TOITermEntry> vendorTermsTOIData = new ArrayList<>();
		vendorTermsTOIData.add(toiTermEntry);
		VendorTermsResponse vendorTermsResponse = new VendorTermsResponse();
		vendorTermsResponse.setVendorTermsToiData(vendorTermsTOIData);

		TermsResponse termsResponse = new TermsResponse();
		termsResponse.setVendorTermsResponse(vendorTermsResponse);

		String payload = APIUtilities.getObjectToJSON(termsResponse);
		System.out.println(payload);

		Object[] arr1 = { payload };
		Object[][] dataSet = new Object[][] { arr1, };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 3, 3);

	}
	
	@DataProvider(name = "activateTOIVendorTerm")
	public static Object[][] activateTOIVendorTerm(ITestContext testContext) throws Exception {

		VendorTermsDP vendorTermsDP = new VendorTermsDP();
		TOITermEntry toiTermEntry = vendorTermsDP.createNonPoTOIMessage();
		toiTermEntry.setId(VendorTermsTest.toiVendorTermId);
		toiTermEntry.setVersion(VendorTermsTest.toiPoVersion);
		toiTermEntry.setTermStatus(TermStatus.PENDING_ACTIVATION);
		toiTermEntry.setTermAction(TermAction.APPROVE);

		List<TOITermEntry> vendorTermsTOIData = new ArrayList<>();
		vendorTermsTOIData.add(toiTermEntry);
		VendorTermsResponse vendorTermsResponse = new VendorTermsResponse();
		vendorTermsResponse.setVendorTermsToiData(vendorTermsTOIData);

		TermsResponse termsResponse = new TermsResponse();
		termsResponse.setVendorTermsResponse(vendorTermsResponse);

		String payload = APIUtilities.getObjectToJSON(termsResponse);
		System.out.println(payload);

		Object[] arr1 = { payload };
		Object[][] dataSet = new Object[][] { arr1, };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 3, 3);

	}
	
	@DataProvider(name = "putOnHoldTOIVendorTerm")
	public static Object[][] putOnHoldTOIVendorTerm(ITestContext testContext) throws Exception {

		VendorTermsDP vendorTermsDP = new VendorTermsDP();
		TOITermEntry toiTermEntry = vendorTermsDP.createNonPoTOIMessage();
		toiTermEntry.setId(VendorTermsTest.toiVendorTermId);
		toiTermEntry.setVersion(VendorTermsTest.toiPoVersion);
		toiTermEntry.setTermStatus(TermStatus.ACTIVE);
		toiTermEntry.setTermAction(TermAction.PUT_ON_HOLD);
		toiTermEntry.setIsOnHold(YesNoType.NO);

		List<TOITermEntry> vendorTermsTOIData = new ArrayList<>();
		vendorTermsTOIData.add(toiTermEntry);
		VendorTermsResponse vendorTermsResponse = new VendorTermsResponse();
		vendorTermsResponse.setVendorTermsToiData(vendorTermsTOIData);

		TermsResponse termsResponse = new TermsResponse();
		termsResponse.setVendorTermsResponse(vendorTermsResponse);

		String payload = APIUtilities.getObjectToJSON(termsResponse);
		System.out.println(payload);

		Object[] arr1 = { payload };
		Object[][] dataSet = new Object[][] { arr1, };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 3, 3);

	}
	
	@DataProvider(name = "putOffHoldTOIVendorTerm")
	public static Object[][] putOffHoldTOIVendorTerm(ITestContext testContext) throws Exception {

		VendorTermsDP vendorTermsDP = new VendorTermsDP();
		TOITermEntry toiTermEntry = vendorTermsDP.createNonPoTOIMessage();
		toiTermEntry.setId(VendorTermsTest.toiVendorTermId);
		toiTermEntry.setVersion(VendorTermsTest.toiPoVersion);
		toiTermEntry.setTermStatus(TermStatus.ACTIVE);
		toiTermEntry.setTermAction(TermAction.PUT_OFF_HOLD);
		toiTermEntry.setIsOnHold(YesNoType.YES);
		

		List<TOITermEntry> vendorTermsTOIData = new ArrayList<>();
		vendorTermsTOIData.add(toiTermEntry);
		VendorTermsResponse vendorTermsResponse = new VendorTermsResponse();
		vendorTermsResponse.setVendorTermsToiData(vendorTermsTOIData);

		TermsResponse termsResponse = new TermsResponse();
		termsResponse.setVendorTermsResponse(vendorTermsResponse);

		String payload = APIUtilities.getObjectToJSON(termsResponse);
		System.out.println(payload);

		Object[] arr1 = { payload };
		Object[][] dataSet = new Object[][] { arr1, };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 3, 3);

	}
	@DataProvider(name = "archiveTOIVendorTerm")
	public static Object[][] archiveTOIVendorTerm(ITestContext testContext) throws Exception {

		VendorTermsDP vendorTermsDP = new VendorTermsDP();
		TOITermEntry toiTermEntry = vendorTermsDP.createNonPoTOIMessage();
		toiTermEntry.setId(VendorTermsTest.toiVendorTermId);
		toiTermEntry.setVersion(VendorTermsTest.toiPoVersion);
		toiTermEntry.setTermStatus(TermStatus.ACTIVE);
		toiTermEntry.setTermAction(TermAction.ARCHIVE);
		TaxonomyEntry closureReasonEntry = vendorTermsDP.taxonomyEntry(99L, "Testing for Archival of Term");
		toiTermEntry.setClosureReasonEntry(closureReasonEntry);
		
		List<TOITermEntry> vendorTermsTOIData = new ArrayList<>();
		vendorTermsTOIData.add(toiTermEntry);
		VendorTermsResponse vendorTermsResponse = new VendorTermsResponse();
		vendorTermsResponse.setVendorTermsToiData(vendorTermsTOIData);

		TermsResponse termsResponse = new TermsResponse();
		termsResponse.setVendorTermsResponse(vendorTermsResponse);

		String payload = APIUtilities.getObjectToJSON(termsResponse);
		System.out.println(payload);

		Object[] arr1 = { payload };
		Object[][] dataSet = new Object[][] { arr1, };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 3, 3);

	}
}
