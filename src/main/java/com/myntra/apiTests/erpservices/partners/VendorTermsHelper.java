package com.myntra.apiTests.erpservices.partners;

import com.myntra.apiTests.SERVICE_TYPE;
import com.myntra.apiTests.erpservices.Constants;
import com.myntra.lordoftherings.boromir.DBUtilities;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.terms.client.code.utils.TermStatus;
import com.myntra.terms.client.code.utils.TermType;
import com.myntra.terms.client.entry.TermsSearchRequest;
import com.myntra.terms.client.entry.VendorTermsSearchRequest;
import com.myntra.terms.client.response.ContractPartiesResponse;
import com.myntra.terms.client.response.TermsResponse;
import com.myntra.terms.client.response.VendorTermsResponse;
import com.myntra.test.commons.service.HTTPMethods;
import com.myntra.test.commons.service.HttpExecutorService;
import com.myntra.test.commons.service.Svc;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class VendorTermsHelper {
	static Logger log = LoggerFactory.getLogger(VendorTermsHelper.class);
	
	public static class VENDOR_TERMS_CONSTANTS{
		public static final String TERMSSEARCHTYPE="FILTER";
	}
	
	
	private HashMap<String, String> getVendorTermsAPIHeader() {
		HashMap<String, String> createArtieServiceHeaders = new HashMap<String, String>();
		createArtieServiceHeaders.put("Authorization", "Basic YXBpYWRtaW46bTFudHJhUjBja2V0MTMhIw==");
		createArtieServiceHeaders.put("Content-Type", "Application/json");
		createArtieServiceHeaders.put("Accept", "Application/json");
		return createArtieServiceHeaders;
	}
	
	public VendorTermsResponse getTaxonomyAdditionalClassification() throws Exception {
		Svc service = HttpExecutorService.executeHttpService(Constants.VENDOR_TERMS_PATH.GET_TAXONOMY_ADDITIONAL_CLASSIFICATION, null,
				SERVICE_TYPE.VENDORTERMS_SVC.toString(), HTTPMethods.GET, null, getVendorTermsAPIHeader());
		Assert.assertEquals(service.getResponseStatus(), 200, "API Statuscode : ");
//		TermsResponse termsResponse=(TermsResponse) APIUtilities.getJsontoObject(service.getResponseBody(), new TermsResponse());
//		System.out.println(termsResponse.getStatus().getStatusMessage());
		VendorTermsResponse vendorTermsResponse=(VendorTermsResponse) APIUtilities.getJsontoObject(service.getResponseBody(), new VendorTermsResponse());
		return vendorTermsResponse;
	}

	public VendorTermsResponse getTaxonomyAgreementType() throws Exception {
		Svc service = HttpExecutorService.executeHttpService(Constants.VENDOR_TERMS_PATH.GET_TAXONOMY_AGREEMENTTYPE, null,
				SERVICE_TYPE.VENDORTERMS_SVC.toString(), HTTPMethods.GET, null, getVendorTermsAPIHeader());
		Assert.assertEquals(service.getResponseStatus(), 200, "API Statuscode : ");
		VendorTermsResponse vendorTermsResponse=(VendorTermsResponse) APIUtilities.getJsontoObject(service.getResponseBody(), new VendorTermsResponse());
		return vendorTermsResponse;
		
	}
	
	public VendorTermsResponse getBrandsByVendorId(long vendorId) throws Exception {
		Svc service = HttpExecutorService.executeHttpService(Constants.VENDOR_TERMS_PATH.VENDOR, new String[]{vendorId+"/brands"},
				SERVICE_TYPE.VENDORTERMS_SVC.toString(), HTTPMethods.GET, null, getVendorTermsAPIHeader());
		Assert.assertEquals(service.getResponseStatus(), 200, "API Statuscode : ");
		VendorTermsResponse vendorTermsResponse=(VendorTermsResponse) APIUtilities.getJsontoObject(service.getResponseBody(), new VendorTermsResponse());
		return vendorTermsResponse;
		
	}
	
	
	public VendorTermsResponse getCatalogMasterCategoryList() throws Exception {
		Svc service = HttpExecutorService.executeHttpService(Constants.VENDOR_TERMS_PATH.GET_CATALOG_MASTERCATEGORY, null,
				SERVICE_TYPE.VENDORTERMS_SVC.toString(), HTTPMethods.GET, null, getVendorTermsAPIHeader());
		Assert.assertEquals(service.getResponseStatus(), 200, "API Statuscode : ");
		VendorTermsResponse vendorTermsResponse=(VendorTermsResponse) APIUtilities.getJsontoObject(service.getResponseBody(), new VendorTermsResponse());
		return vendorTermsResponse;
	}
	
	
	public VendorTermsResponse getTaxonomyGenderList() throws Exception  {
		Svc service = HttpExecutorService.executeHttpService(Constants.VENDOR_TERMS_PATH.GET_TAXONOMY_GENDER, null,
				SERVICE_TYPE.VENDORTERMS_SVC.toString(), HTTPMethods.GET, null, getVendorTermsAPIHeader());
		Assert.assertEquals(service.getResponseStatus(), 200, "API Statuscode : ");	
		VendorTermsResponse vendorTermsResponse=(VendorTermsResponse) APIUtilities.getJsontoObject(service.getResponseBody(), new VendorTermsResponse());
		return vendorTermsResponse;
	}
	
	public VendorTermsResponse getAllArticleTypesUnderMasterCategoryId(long masterCategoryId)  throws Exception{
		Svc service = HttpExecutorService.executeHttpService(Constants.VENDOR_TERMS_PATH.GET_CATALOG_MASTERCATEGORY, new String[]{masterCategoryId+"/articleType"},
				SERVICE_TYPE.VENDORTERMS_SVC.toString(), HTTPMethods.GET, null, getVendorTermsAPIHeader());
		Assert.assertEquals(service.getResponseStatus(), 200, "API Statuscode : ");	
		VendorTermsResponse vendorTermsResponse=(VendorTermsResponse) APIUtilities.getJsontoObject(service.getResponseBody(), new VendorTermsResponse());
		return vendorTermsResponse;
	}
	
	public ContractPartiesResponse getSingleContractPartyData(long accountId, long partyId, long contractTypeId) throws Exception {
		Svc service = HttpExecutorService.executeHttpService(Constants.VENDOR_TERMS_PATH.CONTRACT_ACCOUNT, new String[]{accountId+"/party/"+partyId+"/contractType/"+contractTypeId},
				SERVICE_TYPE.CONTRACT_SVC.toString(), HTTPMethods.GET, null, getVendorTermsAPIHeader());
		Assert.assertEquals(service.getResponseStatus(), 200, "API Statuscode : ");		
		ContractPartiesResponse contractPartiesResponse=(ContractPartiesResponse) APIUtilities.getJsontoObject(service.getResponseBody(), new ContractPartiesResponse());
		return contractPartiesResponse;
	}
	
	public ContractPartiesResponse getAllContractsPartyData(long accountId, long contractTypeId) throws Exception {
		Svc service = HttpExecutorService.executeHttpService(Constants.VENDOR_TERMS_PATH.CONTRACT_ACCOUNT, new String[]{accountId+"/contractType/"+contractTypeId},
				SERVICE_TYPE.CONTRACT_SVC.toString(), HTTPMethods.GET, null, getVendorTermsAPIHeader());
		Assert.assertEquals(service.getResponseStatus(), 200, "API Statuscode : ");		
		ContractPartiesResponse contractPartiesResponse=(ContractPartiesResponse) APIUtilities.getJsontoObject(service.getResponseBody(), new ContractPartiesResponse());
		return contractPartiesResponse;
		
	}
	
	public ContractPartiesResponse getContractByContractPartyId(long contractPartyId) throws Exception {
		Svc service = HttpExecutorService.executeHttpService(Constants.VENDOR_TERMS_PATH.CONTRACT, new String[]{String.valueOf(contractPartyId)},
				SERVICE_TYPE.CONTRACT_SVC.toString(), HTTPMethods.GET, null, getVendorTermsAPIHeader());
		Assert.assertEquals(service.getResponseStatus(), 200, "API Statuscode : ");		
		ContractPartiesResponse contractPartiesResponse=(ContractPartiesResponse) APIUtilities.getJsontoObject(service.getResponseBody(), new ContractPartiesResponse());
		return contractPartiesResponse;
	}
	
	public TermsResponse searchPoTerm( long accountId, long contractTypeId,
			List<Long> brandIds, List<Long> partyIds, Date agreementStartDateFrom, Date agreementStartDateTo,
			Date createdDateFrom, Date createdDateTo, List<TermStatus> termStatuses) throws Exception {
		List<TermType> list=new ArrayList<>();
		list.add(TermType.PO);
		DateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");
		VendorTermsSearchRequest vendorTermsSearchRequest=new VendorTermsSearchRequest();
		vendorTermsSearchRequest.setBrandIds(brandIds);
		TermsSearchRequest termsSearchRequest=new TermsSearchRequest();
//		termsSearchRequest.setSearchType(TermsSearchType.FILTER);
//		termsSearchRequest.setSearchTermTypes(list);
		termsSearchRequest.setAccountId(accountId);
		termsSearchRequest.setContractTypeId(contractTypeId);
//		termsSearchRequest.setAgreementStartDateFrom(agreementStartDateFrom);
//		termsSearchRequest.setAgreementStartDateTo(agreementStartDateTo); //dateFormat.parse(dateFormat.format(agreementStartDateTo)
//		termsSearchRequest.setCreatedDateFrom(dateFormat.parse(dateFormat.format(createdDateFrom)));
//		termsSearchRequest.setCreatedDateTo(dateFormat.parse(dateFormat.format(createdDateTo)));
		termsSearchRequest.setTermStatuses(termStatuses);
		termsSearchRequest.setPartyIds(partyIds);
		termsSearchRequest.setVendorTermsSearchRequest(vendorTermsSearchRequest);
	//com.myntra.terms.client.response.
		
		String payload=APIUtilities.convertJavaObjectToJsonUsingGson(termsSearchRequest);
		
		Svc service = HttpExecutorService.executeHttpService(Constants.VENDOR_TERMS_PATH.SEARCH_TERMS, null,
				SERVICE_TYPE.VENDORTERMS_SVC.toString(), HTTPMethods.POST, payload, getVendorTermsAPIHeader());
		Assert.assertEquals(service.getResponseStatus(), 200, "API Statuscode : ");		
		
		
		
		TermsResponse termsResponse=(TermsResponse) APIUtilities.getJsontoObject(service.getResponseBody(), new TermsResponse());
		return termsResponse;
		
		
	}
	
	public Svc createVendorTerm(String payload) throws Exception {
		Svc service = HttpExecutorService.executeHttpService(Constants.VENDOR_TERMS_PATH.CREATE_TERMS, null,
				SERVICE_TYPE.VENDORTERMS_SVC.toString(), HTTPMethods.POST, payload, getVendorTermsAPIHeader());
		Assert.assertEquals(service.getResponseStatus(), 200, "API Statuscode : ");		
		return service;
		
	}
	
	public Svc updateVendorTerm(String payload) throws Exception {
		Svc service = HttpExecutorService.executeHttpService(Constants.VENDOR_TERMS_PATH.UPDATE_TERMS, null,
				SERVICE_TYPE.VENDORTERMS_SVC.toString(), HTTPMethods.POST, payload, getVendorTermsAPIHeader());
		Assert.assertEquals(service.getResponseStatus(), 200, "API Statuscode : ");		
		return service;
		
	}
	
	public Svc deleteVendorTerm(String payload) throws Exception {
		Svc service = HttpExecutorService.executeHttpService(Constants.VENDOR_TERMS_PATH.DELETE_TERMS, null,
				SERVICE_TYPE.VENDORTERMS_SVC.toString(), HTTPMethods.POST, payload, getVendorTermsAPIHeader());
		Assert.assertEquals(service.getResponseStatus(), 200, "API Statuscode : ");		
		return service;
		
	}
	
	/*********DB Queries******************/
	public long getdbBrandCountByVendorId(long vendorId) throws Exception {
	
		Map<String, Object> totalBrands =
				 DBUtilities.exSelectQueryForSingleRecord("select count(id) from vendor_brand where vendor_id=1 and enabled=1", "vms");
		log.debug("Total brands associated for the vendor :" +totalBrands.get("count(id)"));
		return (long) totalBrands.get("count(id)"); 
		
		
//		List transaction_detail =
//			 DBUtilities.exSelectQuery("select count(id) from vendor_brand where vendor_id=1 and enabled=1", "vms");
//		log.debug(transaction_detail.get(0).toString());
//	 
//		System.out.println(transaction_detail.get(0).toString());
	}
	
	public long getdbTaxonomyAdditionalClassificationCount() throws Exception {
		Map<String, Object> additional_classification =
				 DBUtilities.exSelectQueryForSingleRecord("select count(id) from taxonomy where type='additional_classification' and is_active=1", "terms");
		log.debug(String.valueOf(additional_classification.get("count(id)")));
		return (long) additional_classification.get("count(id)");
		 
	}
	public long getdbAgreement_typeCount() throws Exception {
		Map<String, Object> additional_classification =
				 DBUtilities.exSelectQueryForSingleRecord("select count(id) from taxonomy where type='agreement_type' and is_active=1", "terms");
		log.debug(String.valueOf(additional_classification.get("count(id)")));
		return (long) additional_classification.get("count(id)");
		 
	}
	public long getdbTaxonomyGenderCount() throws Exception {
		Map<String, Object> totalTaxonomyGenderCount =
				 DBUtilities.exSelectQueryForSingleRecord("select count(id) from taxonomy where type='gender' and is_active=1", "terms");
		log.debug("Taxonomy gender db count: "+totalTaxonomyGenderCount.get("count(id)"));
		return (long) totalTaxonomyGenderCount.get("count(id)");
		 
	}
	
	public String getTermResponseValue(Svc service,String key) throws JSONException {
		 JSONObject jsonObject=new JSONObject( service.getResponseBody());
		 JSONObject vendorTermsResponse1=jsonObject.getJSONObject("vendorTermsResponse");
		 JSONArray data=vendorTermsResponse1.getJSONArray("data");
		 JSONObject resp= data.getJSONObject(0);
		return resp.get(key).toString();
	}
	
	public void deletePOVendorTerm(long vendorTermId) throws Exception {
		if(vendorTermId != 0){
		String deleteVendorTermQuery = "DELETE from po_term where id ="+vendorTermId+"";
		log.debug("Deleting PO term from database, query :" +deleteVendorTermQuery);
		DBUtilities.exUpdateQuery(deleteVendorTermQuery, "terms");
		}
	}
	
	public void deleteMarketingVendorTerm(long vendorTermId) throws Exception {
		if(vendorTermId != 0){
		String deleteVendorTermQuery = "DELETE from marketing_term where id ="+vendorTermId+"";
		log.debug("Deleting Marketing term from database, query :" +deleteVendorTermQuery);
		DBUtilities.exUpdateQuery(deleteVendorTermQuery, "terms");
		}
	}
	
	public void deleteCatalogVendorTerm(long vendorTermId) throws Exception {
		if(vendorTermId != 0){
		String deleteVendorTermQuery = "DELETE from catalog_term where id ="+vendorTermId+"";
		log.debug("Deleting Catalog term from database, query :" +deleteVendorTermQuery);
		DBUtilities.exUpdateQuery(deleteVendorTermQuery, "terms");
		}
	}
	
	public void deleteTOIVendorTerm(long vendorTermId) throws Exception {
		if(vendorTermId != 0){
		String deleteVendorTermQuery = "DELETE from toi_term where id ="+vendorTermId+"";
		log.debug("Deleting TOI term from database, query :" +deleteVendorTermQuery);
		DBUtilities.exUpdateQuery(deleteVendorTermQuery, "terms");
		}
	}


	

	

	
	
	;
	

	

	

	

	

	

	

}
