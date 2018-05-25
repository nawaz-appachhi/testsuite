package com.myntra.apiTests.erpservices.partners;

import com.myntra.apiTests.common.ServiceHelper;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.terms.client.code.utils.*;
import com.myntra.terms.client.entry.*;
import com.myntra.terms.client.response.TermsResponse;
import com.myntra.terms.client.response.VendorTermsResponse;
import com.myntra.test.commons.service.ServiceExecutor;
import com.myntra.test.commons.service.Svc;
import com.myntra.vms.client.code.utils.ConfigurationCategory;
import com.myntra.vms.client.code.utils.ContractStatus;
import com.myntra.vms.client.code.utils.EntityName;
import com.myntra.vms.client.entry.ConfigurationEntry;
import com.myntra.vms.client.entry.ContractEntry;
import com.myntra.vms.client.entry.ContractTypeEntry;
import com.myntra.vms.client.entry.VendorEntry;
import com.myntra.vms.client.response.ConfigurationResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.ITestContext;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class VendorServiceHelper extends ServiceHelper  {

	private String vmsURL;
	private String oldvmsURL;
	private String termsURL;
	public static String buyerid;
	public static int vendorid;
	public static String partner1_role;
	public static String partner2_role;
	public static String searchPOPayload;
	public static String createMoq;
	public static String searchMoq;
	public static String updateMoq;
	public static Svc vmsService;
	public static Svc oldvmsService;
	public static Svc termsService;
	private static VMSHelper vmsHelper = new VMSHelper();

	Logger log = LoggerFactory.getLogger(VendorServiceHelper.class);

	public VendorServiceHelper(ITestContext testContext) {
		super(testContext);
		buyerid = vmsHelper.getBuyerId();
		Map<String, Object> roles = vmsHelper.getPartnerRolesFromContractType("1");
		partner1_role = (String) roles.get("partner1_role");
		partner2_role = (String) roles.get("partner2_role");
		vmsURL = getVendorServiceURL();
		oldvmsURL = getVMSServiceURL();
		termsURL = getTermsServiceURL();
		searchPOPayload = testContext.getSuite().getParameter("searchPOTermMultiplePartners");
		createMoq = testContext.getSuite().getParameter("createMoqTerm");
		searchMoq = testContext.getSuite().getParameter("searchMoqTerm");
		updateMoq = testContext.getSuite().getParameter("updateMoqTerm");
	}

	public int createVendor() throws Exception {
		VendorEntry vendor = new VendorEntry();
		vendor.setApprover("commercial_team@myntra.com");
		vendor.setDescription("Vendor testing");
		vendor.setName("Test Vendor");
		vendor.setPoReceiversEmails("test@test.com");
		vendor.setReadyToSplit(true);
		vendor.setShipToAllWarehouses(true);
		String xmlPayload = APIUtilities.convertXMLObjectToString(vendor);
		String jsonPayload = XML.toJSONObject(xmlPayload).toString();
		Svc service = ServiceExecutor.executePOST(oldvmsURL, "vendors", jsonPayload);
		Assert.assertEquals(service.getResponseStatus(), 200, "API Statuscode : ");
		JSONObject obj = new JSONObject(service.getResponseBody());
		int id = (int) obj.getJSONObject("vendorResponse").getJSONObject("data").getJSONObject("vendor").get("id");
		return id;

	}

	public int createContract() throws Exception {
		vendorid = createVendor();
		return createContract(buyerid, vendorid);
	}

	public int createContract(int vendorid) throws Exception {
		return createContract(buyerid, vendorid);
	}

	public int createContract(String buyerid, int vendorid) throws Exception {
		return createContract(buyerid, vendorid, 1);
	}
	
	public int createContract(String buyerid, int vendorid,int contractTypeId) throws Exception {
		String payload = createContractPayload(buyerid, vendorid,contractTypeId);
		Svc contractSvc = ServiceExecutor.executePOST(vmsURL, "contract", payload);
		Assert.assertEquals(contractSvc.getResponseStatus(), 200, "API Statuscode : ");
		JSONObject obj = new JSONObject(contractSvc.getResponseBody());
		JSONArray array = obj.getJSONArray("data");
		Assert.assertNotEquals(array.length(), 0, "Expected data to be non-empty");
		int contractId = (int) array.getJSONObject(0).get("id");
		return contractId;
	}

	public String createContractPayload(String buyerid, int vendorid,int contractTypeId) throws IOException {
		ContractEntry contract=new ContractEntry();
		VendorEntry partner1=new VendorEntry();
		partner1.setId(Long.valueOf(buyerid));
		contract.setPartner1(partner1);
		VendorEntry partner2=new VendorEntry();
		partner2.setId(Long.valueOf(vendorid));
		contract.setPartner2(partner2);
		contract.setContractStatus(ContractStatus.ACTIVE);
		ContractTypeEntry contractType=new ContractTypeEntry();
		contractType.setId(Long.valueOf(contractTypeId));
		contract.setContractTypeEntry(contractType);		
		String payload=APIUtilities.getObjectToJSON(contract);
		return payload;
	}

	public int createPOTerm(int vendorid) throws URISyntaxException, IOException, JSONException {
		POTermEntry poTermEntry = createPOTermEntry(vendorid);
		poTermEntry.setTermAction(TermAction.CREATE);

		List<POTermEntry> vendorTermsPoData = new ArrayList<>();
		vendorTermsPoData.add(poTermEntry);
		VendorTermsResponse vendorTermsResponse = new VendorTermsResponse();
		vendorTermsResponse.setVendorTermsPoData(vendorTermsPoData);
		TermsResponse termsResponse = new TermsResponse();
		termsResponse.setVendorTermsResponse(vendorTermsResponse);

		String payload = APIUtilities.getObjectToJSON(termsResponse);

		Svc contractSvc = ServiceExecutor.executePOST(vmsURL,
				"contract/term/" + buyerid + "/" + "BUYER" + "/" + vendorid + "/" + "B2B_SELLER", payload);
		Assert.assertEquals(contractSvc.getResponseStatus(), 200, "API Statuscode : ");		
		JSONObject obj = new JSONObject(contractSvc.getResponseBody());
		JSONArray array = obj.getJSONObject("vendorTermsResponse").getJSONArray("data");
		Assert.assertNotEquals(array.length(), 0, "Expected data to be non-empty");
		int vendorTermId = (int) array.getJSONObject(0).get("id");
		String status = (String) array.getJSONObject(0).get("termStatus");
		Assert.assertEquals(status, "DRAFT", "Expected status to be DRAFT");
		JSONObject party=array.getJSONObject(0).getJSONObject("partyEntry");
		Assert.assertNotEquals(party.length(), 0, "partyEntry is empty");
		int vendId=(int) party.get("id");
		Assert.assertEquals(vendId, vendorid, "Vendor Ids do not match");
		vmsHelper.validatePOTermEntry(vendorTermId);
		return vendorTermId;
	}

	public void searchContractbyId(int contractId) throws UnsupportedEncodingException, URISyntaxException, JSONException {
		Svc service = ServiceExecutor.executeGET(oldvmsURL, "contract/" + String.valueOf(contractId));
		Assert.assertEquals(service.getResponseStatus(), 200, "API Statuscode : ");
		JSONObject obj = new JSONObject(service.getResponseBody());
		JSONObject data = obj.getJSONObject("contractResponse").getJSONObject("data");
		Assert.assertNotEquals(data.length(), 0, "Expected data to be non-empty");
	}

	public void searchContractBetweenPartners(int partnerId) throws UnsupportedEncodingException, URISyntaxException, JSONException {
		Svc service = ServiceExecutor.executeGET(vmsURL,
				"contract/" + buyerid + "/" + partner1_role + "/" + partnerId + "/" + partner2_role);
		Assert.assertEquals(service.getResponseStatus(), 200, "API Statuscode : ");
		JSONObject obj = new JSONObject(service.getResponseBody());
		JSONArray data = obj.getJSONArray("data");
		Assert.assertNotEquals(data.length(), 0, "Expected data to be non-empty");
	}

	public void searchContractBetweenMultiplePartners() throws URISyntaxException, IOException, JAXBException, JSONException {
		List partnerIds = vmsHelper.getPartnerIdsFromContractAndPOTerm(buyerid, 46, 11, 1);
		String payload = String.format(searchPOPayload, partnerIds.toString());
		Svc service = ServiceExecutor.executePOST(vmsURL,
				"contract/term/" + buyerid + "/" + partner1_role + "/" + partner2_role + "/search", payload);
		Assert.assertEquals(service.getResponseStatus(), 200, "API Statuscode : ");
		JSONObject obj = new JSONObject(service.getResponseBody());
		JSONArray data = obj.getJSONArray("searchResults").getJSONObject(0).getJSONObject("vendorTermsResponse")
				.getJSONArray("data");
		Assert.assertNotEquals(data.length(), 0, "Expected data to be non-empty");
	}

	public void searchTermBetweenPartners(int partnerId) throws UnsupportedEncodingException, URISyntaxException, JSONException {
		Svc service = ServiceExecutor.executeGET(vmsURL,
				"contract/term/" + buyerid + "/" + partner1_role + "/" + partnerId + "/" + partner2_role);
		Assert.assertEquals(service.getResponseStatus(), 200, "API Statuscode : ");
		JSONObject obj = new JSONObject(service.getResponseBody());
		JSONArray data = obj.getJSONArray("searchResults").getJSONObject(0).getJSONObject("vendorTermsResponse")
				.getJSONArray("data");
		Assert.assertNotEquals(data.length(), 0, "Expected data to be non-empty");
	}

	public void searchTermbyContractId(int contractId) throws UnsupportedEncodingException, URISyntaxException, JSONException {
		Svc service = ServiceExecutor.executeGET(termsURL, "terms/contract/" + String.valueOf(contractId));
		Assert.assertEquals(service.getResponseStatus(), 200, "API Statuscode : ");
		JSONObject obj = new JSONObject(service.getResponseBody());
		JSONArray array = obj.getJSONObject("vendorTermsResponse").getJSONArray("data");
		Assert.assertNotEquals(array.length(), 0, "Expected data to be non-empty");
	}

	public void updatePOTerm(int vendorTermId, int contractId, int partnerId) throws URISyntaxException, IOException, JSONException {
		POTermEntry poTermEntry = updatePOTermEntry(vendorTermId);
		poTermEntry.setTermStatus(TermStatus.DRAFT);
		poTermEntry.setTermAction(TermAction.FIELD_UPDATE);

		JSONObject obj = executeUpdate(partnerId, poTermEntry);

		double margin = (double) obj.get("marginPercentage");
		Assert.assertEquals(margin, 35.0, "Expected margin to be " + 35.0);
	}

	private JSONObject executeUpdate(int partnerId, POTermEntry poTermEntry)
			throws IOException, URISyntaxException, UnsupportedEncodingException, JSONException {

		List<POTermEntry> vendorTermsPoData = new ArrayList<>();
		vendorTermsPoData.add(poTermEntry);
		VendorTermsResponse vendorTermsResponse = new VendorTermsResponse();
		vendorTermsResponse.setVendorTermsPoData(vendorTermsPoData);
		TermsResponse termsResponse = new TermsResponse();
		termsResponse.setVendorTermsResponse(vendorTermsResponse);

		String payload = APIUtilities.getObjectToJSON(termsResponse);

		Svc service = ServiceExecutor.executePUT(vmsURL,
				"contract/term/" + buyerid + "/" + partner1_role + "/" + partnerId + "/" + partner2_role, payload);
		Assert.assertEquals(service.getResponseStatus(), 200, "API Statuscode : ");
		JSONObject obj = new JSONObject(service.getResponseBody());
		JSONArray array = obj.getJSONObject("vendorTermsResponse").getJSONArray("data");
		Assert.assertNotEquals(array.length(), 0, "Expected data to be non-empty");
		if (array.getJSONObject(0).has("entryStatus")) {
			String errMsg = (String) array.getJSONObject(0).get("message");
			throw new IllegalStateException("Error message: " + errMsg);
		}
		return array.getJSONObject(0);
	}

	public void updatePOTermAfterSendBack(int vendorTermId, int contractId, int partnerId)
			throws URISyntaxException, IOException, JSONException {
		POTermEntry poTermEntry = updatePOTermEntry(vendorTermId);
		poTermEntry.setMarginPercentage(25.0);
		poTermEntry.setTermStatus(TermStatus.SENT_BACK);
		poTermEntry.setTermAction(TermAction.FIELD_UPDATE);

		JSONObject obj = executeUpdate(partnerId, poTermEntry);

		double margin = (double) obj.get("marginPercentage");
		Assert.assertEquals(margin, 25.0, "Expected margin to be " + 25.0);
	}

	public void updateActivePOTerm(int vendorTermId, int contractId, int partnerId)
			throws URISyntaxException, IOException, JSONException {
		POTermEntry poTermEntry = updatePOTermEntry(vendorTermId);
		poTermEntry.setMarginPercentage(30.0);
		poTermEntry.setTermStatus(TermStatus.ACTIVE);
		poTermEntry.setTermAction(TermAction.FIELD_UPDATE);

		JSONObject obj = executeUpdate(partnerId, poTermEntry);

		double margin = (double) obj.get("marginPercentage");
		Assert.assertEquals(margin, 35.0, "Expected margin to be " + 35.0);
	}

	public void updatePendingActivePOTerm(int vendorTermId, int contractId, int partnerId)
			throws URISyntaxException, IOException, JSONException {
		POTermEntry poTermEntry = updatePOTermEntry(vendorTermId);
		poTermEntry.setMarginPercentage(30.0);
		poTermEntry.setTermStatus(TermStatus.PENDING_ACTIVATION);
		poTermEntry.setTermAction(TermAction.FIELD_UPDATE);

		JSONObject obj = executeUpdate(partnerId, poTermEntry);

		double margin = (double) obj.get("marginPercentage");
		Assert.assertEquals(margin, 35.0, "Expected margin to be " + 35.0);
	}

	public void archivePOTerm(int vendorTermId, int contractId, int partnerId) throws URISyntaxException, IOException, JSONException {
		POTermEntry poTermEntry = updatePOTermEntry(vendorTermId);
		TaxonomyEntry closureReasonEntry = new TaxonomyEntry();
		closureReasonEntry.setId(57L);
		poTermEntry.setClosureReasonEntry(closureReasonEntry);
		;
		poTermEntry.setTermStatus(TermStatus.ACTIVE);
		poTermEntry.setTermAction(TermAction.ARCHIVE);
		JSONObject obj = executeUpdate(partnerId, poTermEntry);
		String status = obj.getString("termStatus");
		Assert.assertEquals(status, "ARCHIVED", "Expected status to be ARCHIVED");
	}

	public void sendTermForActivation(int vendorTermId, int partnerId) throws URISyntaxException, IOException, JSONException {
		POTermEntry poTermEntry = updatePOTermEntry(vendorTermId);
		poTermEntry.setTermStatus(TermStatus.DRAFT);
		poTermEntry.setTermAction(TermAction.SEND_FOR_ACTIVATION);

		JSONObject obj = executeUpdate(partnerId, poTermEntry);
		String status = obj.getString("termStatus");
		Assert.assertEquals(status, "PENDING_ACTIVATION", "Expected status to be PENDING_ACTIVATION");
	}

	public void sendBackPOTerm(int vendorTermId, int partnerId) throws URISyntaxException, IOException, JSONException {
		POTermEntry poTermEntry = updatePOTermEntry(vendorTermId);
		poTermEntry.setSentBackRemarks("test");
		poTermEntry.setTermStatus(TermStatus.PENDING_ACTIVATION);
		poTermEntry.setTermAction(TermAction.SEND_BACK);

		JSONObject obj = executeUpdate(partnerId, poTermEntry);
		String status = obj.getString("termStatus");
		Assert.assertEquals(status, "SENT_BACK", "Expected status to be SENT_BACK");
	}

	public void sendTermForApproval(int vendorTermId, int partnerId) throws URISyntaxException, IOException, JSONException {
		POTermEntry poTermEntry = updatePOTermEntry(vendorTermId);
		poTermEntry.setTermStatus(TermStatus.PENDING_ACTIVATION);
		poTermEntry.setTermAction(TermAction.APPROVE);

		JSONObject obj = executeUpdate(partnerId, poTermEntry);
		String status = obj.getString("termStatus");
		Assert.assertEquals(status, "ACTIVE", "Expected status to be ACTIVE");
	}

	public int createMoqTerm(int vendorid) throws UnsupportedEncodingException, URISyntaxException, JSONException {
		String payload = String.format(createMoq, vendorid);
		Svc contractSvc = ServiceExecutor.executePOST(vmsURL,
				"contract/term/" + buyerid + "/" + partner1_role + "/" + vendorid + "/" + partner2_role, payload);
		Assert.assertEquals(contractSvc.getResponseStatus(), 200, "API Statuscode : ");
		JSONObject obj = new JSONObject(contractSvc.getResponseBody());
		JSONArray array = obj.getJSONObject("moqTermsResponse").getJSONArray("data");
		Assert.assertNotEquals(array.length(), 0, "Expected data to be non-empty");
		int moqTermId = (int) array.getJSONObject(0).get("id");
		String status = array.getJSONObject(0).getString("termStatus");
		Assert.assertEquals(status, "DRAFT", "Expected status to be DRAFT");
		vmsHelper.validatePOTermEntry(moqTermId);
		return moqTermId;
	}

	public void searchMoqBetweenMultiplePartners() throws UnsupportedEncodingException, URISyntaxException, JSONException {
		List partnerIds = vmsHelper.getPartnerIdsFromContractAndMoqTerm(buyerid, "PUMA");
		String payload = String.format(searchMoq, partnerIds.toString());
		Svc service = ServiceExecutor.executePOST(vmsURL,
				"contract/term/" + buyerid + "/" + partner1_role + "/" + partner2_role + "/search", payload);
		Assert.assertEquals(service.getResponseStatus(), 200, "API Statuscode : ");
		JSONObject obj = new JSONObject(service.getResponseBody());
		JSONArray array = obj.getJSONArray("searchResults").getJSONObject(0).getJSONObject("moqTermsResponse")
				.getJSONArray("data");
		Assert.assertNotEquals(array.length(), 0, "Expected data to be non-empty");
	}

	public void sendMoqTermForActivation(int moqTermId, int partnerId)
			throws UnsupportedEncodingException, URISyntaxException, JSONException {
		String payload = String.format(updateMoq, moqTermId, partnerId);
		Svc service = ServiceExecutor.executePUT(vmsURL,
				"contract/term/" + buyerid + "/" + partner1_role + "/" + partnerId + "/" + partner2_role, payload);
		Assert.assertEquals(service.getResponseStatus(), 200, "API Statuscode : ");
		JSONObject obj = new JSONObject(service.getResponseBody());
		JSONArray array = obj.getJSONObject("moqTermsResponse").getJSONArray("data");
		Assert.assertNotEquals(array.length(), 0, "Expected data to be non-empty");
		String status = array.getJSONObject(0).getString("termStatus");
		Assert.assertEquals(status, "PENDING_ACTIVATION", "Expected status to be PENDING_ACTIVATION");
	}

	public POTermEntry createPOTermEntry(int vendorid) {
		POTermEntry poTermEntry = new POTermEntry();
		poTermEntry.setTermType(TermType.PO);
		ExternalEntityEntry partyEntry = new ExternalEntityEntry();
		partyEntry.setId((long) vendorid);
		poTermEntry.setPartyEntry(partyEntry);
		ExternalEntityEntry brandEntry = new ExternalEntityEntry();
		brandEntry.setId(1L);
		poTermEntry.setBrandEntry(brandEntry);
		TaxonomyEntry brandTypeEntry = new TaxonomyEntry();
		brandTypeEntry.setId(23L);
		poTermEntry.setBrandTypeEntry(brandTypeEntry);
		TaxonomyEntry agreementTypeEntry = new TaxonomyEntry();
		agreementTypeEntry.setId(46L);
		poTermEntry.setAgreementTypeEntry(agreementTypeEntry);
		ExternalEntityEntry masterCategoryEntry = new ExternalEntityEntry();
		masterCategoryEntry.setId(1L);
		poTermEntry.setMasterCategoryEntry(masterCategoryEntry);
		ExternalEntityEntry articleTypeEntry = new ExternalEntityEntry();
		articleTypeEntry.setId(185L);
		poTermEntry.setArticleTypeEntry(articleTypeEntry);
		TaxonomyEntry genderEntry = new TaxonomyEntry();
		genderEntry.setId(-1L);
		poTermEntry.setGenderEntry(genderEntry);
		TaxonomyEntry additionalClassificationEntry = new TaxonomyEntry();
		additionalClassificationEntry.setId(11L);
		poTermEntry.setAdditionalClassificationEntry(additionalClassificationEntry);
		TaxonomyEntry billToEntry = new TaxonomyEntry();
		billToEntry.setId(20L);
		poTermEntry.setBillToEntry(billToEntry);
		poTermEntry.setAgmntStartDate(new Date());
		poTermEntry.setAgmntEndDate(new Date());
		poTermEntry.setMarginPercentage(20.0);
		poTermEntry.setAccountId(1L);
		poTermEntry.setContractTypeId(1L);
		poTermEntry.setAutoRenewal(YesNoType.YES);
		TaxonomyEntry autoRenewalCycleEntry = new TaxonomyEntry();
		autoRenewalCycleEntry.setId(18L);
		poTermEntry.setAutoRenewalCycleEntry(autoRenewalCycleEntry);
		TaxonomyEntry marginBasisEntry = new TaxonomyEntry();
		marginBasisEntry.setId(60L);
		poTermEntry.setMarginBasisEntry(marginBasisEntry);
		TaxonomyEntry paymentMethodEntry = new TaxonomyEntry();
		paymentMethodEntry.setId(8L);
		poTermEntry.setPaymentMethodEntry(paymentMethodEntry);
		poTermEntry.setStockCorrectionPercentage(100.0);
		poTermEntry.setIsPrimary(YesNoType.NO);
		poTermEntry.setDoc(YesNoType.YES);
		poTermEntry.setDiscountSharingPer(100.0);
		return poTermEntry;
	}

	public POTermEntry updatePOTermEntry(int vendorTermId) {
		POTermEntry poTermEntry = createPOTermEntry(vendorid);
		poTermEntry.setId((long) vendorTermId);
		poTermEntry.setMarginPercentage(35.0);

		long archiveId = vmsHelper.getArchiveIdFromPOTerm(vendorTermId);
		int version = vmsHelper.getVersionFromPOTerm(vendorTermId);

		poTermEntry.setVersion((long) version);
		poTermEntry.setArchiveId(archiveId);
		return poTermEntry;
	}

	public TermsSearchRequest createSearchPOTerm(List<Long> partyIds) {
		VendorTermsSearchRequest vendorTermsSearchRequest = new VendorTermsSearchRequest();
		vendorTermsSearchRequest.setBrandTypeIds(null);
		List<Long> agreementTypeIds = new ArrayList<Long>();
		List<Long> masterCategoryIds = new ArrayList<Long>();
		List<Long> addClassificationIds = new ArrayList<Long>();
		agreementTypeIds.add(46L);
		masterCategoryIds.add(1L);
		addClassificationIds.add(11L);
		vendorTermsSearchRequest.setAgreementTypeIds(agreementTypeIds);
		vendorTermsSearchRequest.setMasterCategoryIds(masterCategoryIds);
		vendorTermsSearchRequest.setAdditionalClassificationIds(addClassificationIds);
		TermsSearchRequest termsSearchRequest = new TermsSearchRequest();
		termsSearchRequest.setSearchType(TermsSearchType.FILTER);
		termsSearchRequest.setRequestId(1L);
		List<TermType> list = new ArrayList<TermType>();
		list.add(TermType.PO);
		termsSearchRequest.setSearchTermTypes(list);
		termsSearchRequest.setAccountId(1L);
		termsSearchRequest.setContractTypeId(1L);
		termsSearchRequest.setStart(0);
		termsSearchRequest.setFetchSize(50);
		termsSearchRequest.setPartyIds(partyIds);
		termsSearchRequest.setVendorTermsSearchRequest(vendorTermsSearchRequest);

		return termsSearchRequest;
	}
	
	public Svc getKeysFromConfigMaster(String query) throws URISyntaxException, JSONException {
		Svc service = ServiceExecutor.executeGET(vmsURL,"configuration/master/search?q="+query);
		Assert.assertEquals(service.getResponseStatus(), 200, "API Statuscode : ");
		JSONObject obj = new JSONObject(service.getResponseBody());
		JSONArray data = obj.getJSONArray("data");
		Assert.assertNotEquals(data.length(), 0, "Expected data to be non-empty");
		return service;
	}
	
	public String createSellerConfigurationPayload(String contractId,String key,String value) throws IOException {
		ConfigurationEntry configEntry=new ConfigurationEntry();
		configEntry.setEntityId(Long.valueOf(contractId));
		configEntry.setEntityType(EntityName.CONTRACT);
		configEntry.setCategory(ConfigurationCategory.COUPONS);
		configEntry.setKey(key);
		configEntry.setValue(value);
		List<ConfigurationEntry> configList=new ArrayList<>();
		configList.add(configEntry);
		ConfigurationResponse configResponse=new ConfigurationResponse();
		configResponse.setData(configList);
		return APIUtilities.getObjectToJSON(configResponse.getData());
	}
	
	public String createSellerConfiguration(String contractId,String key,String value) throws URISyntaxException, IOException, JSONException {
		String payload=createSellerConfigurationPayload(contractId, key, value);
		Svc service = ServiceExecutor.executePOST(vmsURL,"configuration/create/list",payload);
		Assert.assertEquals(service.getResponseStatus(), 200, "API Statuscode : ");
		JSONObject obj = new JSONObject(service.getResponseBody());
		JSONArray data = obj.getJSONArray("data");
		String status=obj.getJSONObject("status").getString("statusType");
		Assert.assertEquals(status, "SUCCESS","Expected Success but got "+status);
		Assert.assertNotEquals(data.length(), 0, "Expected data to be non-empty");
		return (String.valueOf(data.getJSONObject(0).get("id")));
	}
	
	public Svc updateSellerConfiguration(String id,String key,String value) throws URISyntaxException, IOException, JSONException {
		String payload="{\""+ key+"\":\""+ value+"\"}";
		Svc service = ServiceExecutor.executePUT(vmsURL,"configuration/"+id,payload);
		Assert.assertEquals(service.getResponseStatus(), 200, "API Statuscode : ");
		JSONObject obj = new JSONObject(service.getResponseBody());
		JSONArray data = obj.getJSONArray("data");
		String status=obj.getJSONObject("status").getString("statusType");
		Assert.assertEquals(status, "SUCCESS","Expected Success but got "+status);
		Assert.assertNotEquals(data.length(), 0, "Expected data to be non-empty");
		return service;
	}
	
	public void validateKeyValue(Svc response,String key,String value) throws JSONException {
		JSONObject obj = new JSONObject(response.getResponseBody());
		JSONArray data = obj.getJSONArray("data");
		for(int i=0;i<data.length();i++) {
			String val=(String) data.getJSONObject(i).get(key);
			Assert.assertEquals(val, value,"Expected "+key+" to be "+value);
		}
	}
	
	public void validateConfigurationAttributes(Svc response,String key,String value) throws JSONException {
		JSONObject obj = new JSONObject(response.getResponseBody());
		JSONArray data = obj.getJSONArray("data");
		for(int i=0;i<data.length();i++) {
			JSONArray attributes = data.getJSONObject(i).getJSONArray("configurationAttributes");	
			String val=(String)attributes.getJSONObject(0).get(key);
			Assert.assertEquals(val, value,"Expected "+key+" to be "+value);		
		}
	}

}
