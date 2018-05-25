package com.myntra.apiTests.erpservices.partners;

import com.myntra.apiTests.erpservices.Constants;
import com.myntra.client.security.response.UserEntry;
import com.myntra.client.wms.response.location.WarehouseEntry;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.sellers.entry.*;
import com.myntra.sellers.enums.*;
import com.myntra.sellers.response.*;

import com.myntra.test.commons.service.HTTPMethods;
import com.myntra.test.commons.service.HttpExecutorService;
import com.myntra.apiTests.SERVICE_TYPE;
import com.myntra.test.commons.service.Svc;

import javax.xml.bind.JAXBException;

import org.testng.Assert;
import org.testng.Reporter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class SellerServicesHelper {

	
	public SellerResponse createSeller(String name, String displayName, Boolean international) throws JAXBException, IOException {
		SellerEntry sellerEntry = new SellerEntry();
		sellerEntry.setName(name);
		sellerEntry.setDisplayName(displayName);
		sellerEntry.setInternational(international);
		String payload = APIUtilities.getObjectToJSON(sellerEntry);	
		Svc service = HttpExecutorService.executeHttpService(Constants.SELLERSERVICES_PATH.CREATE_SELLER, null, SERVICE_TYPE.SELLERSERVICES.toString(), HTTPMethods.POST, payload, getSellerServicesHeader());
		SellerResponse sellerResponse = (SellerResponse) APIUtilities.getJsontoObject(service.getResponseBody(), new SellerResponse());
		String status=sellerResponse.getStatus().getStatusType().toString();
		Assert.assertEquals(status, "SUCCESS","Expected success but got "+status);
		return sellerResponse;
	}
	
	public SellerResponse createSellerNegative(String name) throws JAXBException, IOException {
		SellerEntry sellerEntry = new SellerEntry();
		sellerEntry.setName(name);
		String payload = APIUtilities.getObjectToJSON(sellerEntry);	
		Svc service = HttpExecutorService.executeHttpService(Constants.SELLERSERVICES_PATH.CREATE_SELLER, null, SERVICE_TYPE.SELLERSERVICES.toString(), HTTPMethods.POST, payload, getSellerServicesHeader());
		SellerResponse sellerResponse = (SellerResponse) APIUtilities.getJsontoObject(service.getResponseBody(), new SellerResponse());
		return sellerResponse;
	}
	
	public SellerResponse updateSeller(String sellerId, String name, String barcode, SellerStatus status) throws JAXBException, IOException {
		SellerEntry sellerEntry = new SellerEntry();
		sellerEntry.setBarcode(barcode);
		sellerEntry.setName(name);
		sellerEntry.setStatus(status);
		String payload = APIUtilities.getObjectToJSON(sellerEntry);	
		Svc service = HttpExecutorService.executeHttpService(Constants.SELLERSERVICES_PATH.UPDATE_SELLER, new String[] {sellerId}, SERVICE_TYPE.SELLERSERVICES.toString(), HTTPMethods.PUT, payload, getSellerServicesHeader());
		SellerResponse sellerResponse = (SellerResponse) APIUtilities.getJsontoObject(service.getResponseBody(), new SellerResponse());
		return sellerResponse;
	}
	
	public SellerResponse getAllSeller() throws JAXBException, IOException {

		Svc service = HttpExecutorService.executeHttpService(Constants.SELLERSERVICES_PATH.GET_ALL_SELLER, null, SERVICE_TYPE.SELLERSERVICES.toString(), HTTPMethods.GET, null, getSellerServicesHeader());
		SellerResponse sellerResponse = (SellerResponse) APIUtilities.getJsontoObject(service.getResponseBody(), new SellerResponse());
		return sellerResponse;
	}
	
	
	public SellerResponse getSellerInfo() throws JAXBException, IOException {

		Svc service = HttpExecutorService.executeHttpService(Constants.SELLERSERVICES_PATH.GET_SELLER_INFO, new String[] {"1"}, SERVICE_TYPE.SELLERSERVICES.toString(), HTTPMethods.GET, null, getSellerServicesHeader());
		SellerResponse sellerResponse = (SellerResponse) APIUtilities.getJsontoObject(service.getResponseBody(), new SellerResponse());
		return sellerResponse;
	}
	
	public Svc getSellerById(String sellerId) throws JAXBException, IOException {
		return getSellerById(sellerId, null);
	}

	public Svc getSellerById(String sellerId, String query) throws JAXBException, IOException {
		String parameters="";
		if(sellerId==null || sellerId.isEmpty()) {
			parameters = "sellerConfiguration/search/bySellerId?"+query;
		} else {
			parameters = "sellerConfiguration/search/bySellerId?sellerId=" + sellerId + "&" + query;
		}
		Svc service = HttpExecutorService.executeHttpService(parameters, null, SERVICE_TYPE.SELLERSERVICES.toString(),
				HTTPMethods.GET, null, getSellerServicesHeader());
		Reporter.log(service.getResponseBody());
		SellerResponse sellerResponse = (SellerResponse) APIUtilities.getJsontoObject(service.getResponseBody(),
				new SellerResponse());
		String status=sellerResponse.getStatus().getStatusType().toString();
		Assert.assertEquals(status, "SUCCESS","Expected Success but got "+status);
		return service;
	}
	
	public SellerResponse addSellerConfigurations(String sellerId) throws Exception {
		String parameters="sellerConfiguration/add/configurations?sellerId="+sellerId;
		Svc service = HttpExecutorService.executeHttpService(parameters,null, SERVICE_TYPE.SELLERSERVICES.toString(), HTTPMethods.POST, null, getSellerServicesHeader());
		Reporter.log(service.getResponseBody());
		SellerResponse sellerResponse = (SellerResponse) APIUtilities.getJsontoObject(service.getResponseBody(), new SellerResponse());
		String status=sellerResponse.getStatus().getStatusType().toString();
		Assert.assertEquals(status, "SUCCESS","Expected Success but got "+status);
		return sellerResponse;
	}
	
	public SellerResponse brandSearch() throws JAXBException, IOException {

		Svc service = HttpExecutorService.executeHttpService(Constants.SELLERSERVICES_PATH.BRAND_SEARCH, null, SERVICE_TYPE.SELLERSERVICES.toString(), HTTPMethods.GET, null, getSellerServicesHeader());
		SellerResponse sellerResponse = (SellerResponse) APIUtilities.getJsontoObject(service.getResponseBody(), new SellerResponse());
		return sellerResponse;
	}
	
	
	public SellerResponse searchCategoryManager() throws JAXBException, IOException {

		Svc service = HttpExecutorService.executeHttpService(Constants.SELLERSERVICES_PATH.SEARCH_CATEGORY_MANAGER, null, SERVICE_TYPE.SELLERSERVICES.toString(), HTTPMethods.GET, null, getSellerServicesHeader());
		SellerResponse sellerResponse = (SellerResponse) APIUtilities.getJsontoObject(service.getResponseBody(), new SellerResponse());
		return sellerResponse;
	}
	
	public SellerResponse searchSellerWarehouse() throws JAXBException, IOException {

		Svc service = HttpExecutorService.executeHttpService(Constants.SELLERSERVICES_PATH.SEARCH_SELLER_WAREHOUSE, null, SERVICE_TYPE.SELLERSERVICES.toString(), HTTPMethods.GET, null, getSellerServicesHeader());
		SellerResponse sellerResponse = (SellerResponse) APIUtilities.getJsontoObject(service.getResponseBody(), new SellerResponse());
		return sellerResponse;
	}
	
	public SellerResponse getSellerAddress() throws JAXBException, IOException {

		Svc service = HttpExecutorService.executeHttpService(Constants.SELLERSERVICES_PATH.GET_SELLER_ADDRESS_WITH_TIN,null, SERVICE_TYPE.SELLERSERVICES.toString(), HTTPMethods.GET, null, getSellerServicesHeader());
		SellerResponse sellerResponse = (SellerResponse) APIUtilities.getJsontoObject(service.getResponseBody(), new SellerResponse());
		return sellerResponse;
	}
	
	public SellerResponse getSellerbyWarehouseId() throws JAXBException, IOException {

		Svc service = HttpExecutorService.executeHttpService(Constants.SELLERSERVICES_PATH.GET_SELLER_BY_WAREHOUSE_ID, null, SERVICE_TYPE.SELLERSERVICES.toString(), HTTPMethods.GET, null, getSellerServicesHeader());
		SellerResponse sellerResponse = (SellerResponse) APIUtilities.getJsontoObject(service.getResponseBody(), new SellerResponse());
		return sellerResponse;
	}
	
	public SellerResponse searchFinancialInfo() throws JAXBException, IOException {

		Svc service = HttpExecutorService.executeHttpService(Constants.SELLERSERVICES_PATH.GET_FINANCE_INFORMATION, null, SERVICE_TYPE.SELLERSERVICES.toString(), HTTPMethods.GET, null, getSellerServicesHeader());
		SellerResponse sellerResponse = (SellerResponse) APIUtilities.getJsontoObject(service.getResponseBody(), new SellerResponse());
		return sellerResponse;
	}
	
	public SellerResponse searchKycDoc() throws JAXBException, IOException {

		Svc service = HttpExecutorService.executeHttpService(Constants.SELLERSERVICES_PATH.GET_KYC_DOCUMENT, null, SERVICE_TYPE.SELLERSERVICES.toString(), HTTPMethods.GET, null, getSellerServicesHeader());
		SellerResponse sellerResponse = (SellerResponse) APIUtilities.getJsontoObject(service.getResponseBody(), new SellerResponse());
		return sellerResponse;
	}
	
	public SellerResponse searchPaymentConfig() throws JAXBException, IOException {

		Svc service = HttpExecutorService.executeHttpService(Constants.SELLERSERVICES_PATH.GET_PAYMENT_CONFIGURATION, null, SERVICE_TYPE.SELLERSERVICES.toString(), HTTPMethods.GET, null, getSellerServicesHeader());
		SellerResponse sellerResponse = (SellerResponse) APIUtilities.getJsontoObject(service.getResponseBody(), new SellerResponse());
		return sellerResponse;
	}
	
	
	public SellerResponse getSellerSettelmentInfoBySellerId(String sellerId) throws JAXBException, IOException {

		Svc service = HttpExecutorService.executeHttpService(Constants.SELLERSERVICES_PATH.GET_SELLER_SETTELMENT, new String[] {sellerId}, SERVICE_TYPE.SELLERSERVICES.toString(), HTTPMethods.GET, null, getSellerServicesHeader());
		SellerResponse sellerResponse = (SellerResponse) APIUtilities.getJsontoObject(service.getResponseBody(), new SellerResponse());
		return sellerResponse;
	}
	
	public SellerResponse getSellerConfiguration() throws JAXBException, IOException {

		Svc service = HttpExecutorService.executeHttpService(Constants.SELLERSERVICES_PATH.GET_SELLER_CONFIGURATION, null, SERVICE_TYPE.SELLERSERVICES.toString(), HTTPMethods.GET, null, getSellerServicesHeader());
		SellerResponse sellerResponse = (SellerResponse) APIUtilities.getJsontoObject(service.getResponseBody(), new SellerResponse());
		return sellerResponse;
	}
	
	public Svc getSellerConfiguration(String sellerId) throws JAXBException, IOException {
		return getSellerConfiguration(sellerId, "");
	}

	public Svc getSellerConfiguration(String sellerId, String query) throws JAXBException, IOException {
		String parameters="";
		if(sellerId==null||sellerId.isEmpty()) {
			parameters="sellerConfiguration/search?q="+query;
		} else {
			parameters = "sellerConfiguration/search?q=sellerId.eq:" + sellerId + "___" + query;
		}
		Svc service = HttpExecutorService.executeHttpService(parameters, null, SERVICE_TYPE.SELLERSERVICES.toString(),
				HTTPMethods.GET, null, getSellerServicesHeader());
		SellerResponse sellerResponse = (SellerResponse) APIUtilities.getJsontoObject(service.getResponseBody(),
				new SellerResponse());
		String status=sellerResponse.getStatus().getStatusType().toString();
		Assert.assertEquals(status, "SUCCESS","Expected Success but got "+status);
		Assert.assertNotNull(service.getResponseBody(), "No response found");
		Reporter.log(service.getResponseBody());
		return service;
	}
	
	
	public SellerResponse getAllAddress() throws JAXBException, IOException {

		Svc service = HttpExecutorService.executeHttpService(Constants.SELLERSERVICES_PATH.GET_ALL_ADDRESS, null, SERVICE_TYPE.SELLERSERVICES.toString(), HTTPMethods.GET, null, getSellerServicesHeader());
		SellerResponse sellerResponse = (SellerResponse) APIUtilities.getJsontoObject(service.getResponseBody(), new SellerResponse());
		return sellerResponse;
	}
	
	public SellerResponse getAddressBySeller() throws JAXBException, IOException {

		Svc service = HttpExecutorService.executeHttpService(Constants.SELLERSERVICES_PATH.GET_ADDRESS_BY_SELLER, null, SERVICE_TYPE.SELLERSERVICES.toString(), HTTPMethods.GET, null, getSellerServicesHeader());
		SellerResponse sellerResponse = (SellerResponse) APIUtilities.getJsontoObject(service.getResponseBody(), new SellerResponse());
		return sellerResponse;
	}
	
	public SellerResponse getStyleIdsBySeller(String sellerId) throws JAXBException, IOException {

		Svc service = HttpExecutorService.executeHttpService(Constants.SELLERSERVICES_PATH.GET_STYLEIDS_BY_SELLER, new String[] {"styleIds?sellerId="+sellerId}, SERVICE_TYPE.SELLERSERVICES.toString(), HTTPMethods.GET, null, getSellerServicesHeader());
		SellerResponse sellerResponse = (SellerResponse) APIUtilities.getJsontoObject(service.getResponseBody(), new SellerResponse());
		return sellerResponse;
	}
	
	public SellerItemMasterResponse getSellerItemBySeller(String sellerId) throws JAXBException, IOException {

		Svc service = HttpExecutorService.executeHttpService(Constants.SELLERSERVICES_PATH.GET_SELLERITEM_BY_SELLERID, new String[] {"search?q=sellerId.eq:"+sellerId}, SERVICE_TYPE.SELLERSERVICES.toString(), HTTPMethods.GET, null, getSellerServicesHeader());
		SellerItemMasterResponse sellerItemMasterResponse = (SellerItemMasterResponse) APIUtilities.getJsontoObject(service.getResponseBody(), new SellerItemMasterResponse());
		return sellerItemMasterResponse;
	}
	
	public SellerWarehouseResponse getWarehouseBySeller(String sellerId) throws JAXBException, IOException {

		Svc service = HttpExecutorService.executeHttpService(Constants.SELLERSERVICES_PATH.GET_WAREHOUSE_BY_SELLER, new String[] {"search?q=seller.id.eq:"+sellerId}, SERVICE_TYPE.SELLERSERVICES.toString(), HTTPMethods.GET, null, getSellerServicesHeader());
		SellerWarehouseResponse sellerWarehouseResponse = (SellerWarehouseResponse) APIUtilities.getJsontoObject(service.getResponseBody(), new SellerWarehouseResponse());
		return sellerWarehouseResponse;
	}
	
	public FinanceInformationResponse getFinancialInfoBySeller(String sellerId) throws JAXBException, IOException {

		Svc service = HttpExecutorService.executeHttpService(Constants.SELLERSERVICES_PATH.GET_FINANCEINFO_SELLER, new String[] {"search?q=seller.id.eq:"+sellerId}, SERVICE_TYPE.SELLERSERVICES.toString(), HTTPMethods.GET, null, getSellerServicesHeader());
		FinanceInformationResponse financeInformationResponse = (FinanceInformationResponse) APIUtilities.getJsontoObject(service.getResponseBody(), new FinanceInformationResponse());
		return financeInformationResponse;
	}
	
	public KYCDocumentResponse getKycDocBySeller(String sellerId) throws JAXBException, IOException {

		Svc service = HttpExecutorService.executeHttpService(Constants.SELLERSERVICES_PATH.GET_KYC_DOCUMENT_SELLER, new String[] {"search?q=sellerId.eq:"+sellerId}, SERVICE_TYPE.SELLERSERVICES.toString(), HTTPMethods.GET, null, getSellerServicesHeader());
		KYCDocumentResponse kYCDocumentResponse = (KYCDocumentResponse) APIUtilities.getJsontoObject(service.getResponseBody(), new KYCDocumentResponse());
		return kYCDocumentResponse;
	}
	
	public PaymentConfigurationResponse getPaymentConfigBySeller(String sellerId) throws JAXBException, IOException {

		Svc service = HttpExecutorService.executeHttpService(Constants.SELLERSERVICES_PATH.GET_PAYMENT_CONFIG_SELLER, new String[] {"search?q=seller.id.eq:"+sellerId}, SERVICE_TYPE.SELLERSERVICES.toString(), HTTPMethods.GET, null, getSellerServicesHeader());
		PaymentConfigurationResponse paymentConfigurationResponse = (PaymentConfigurationResponse) APIUtilities.getJsontoObject(service.getResponseBody(), new PaymentConfigurationResponse());
		return paymentConfigurationResponse;
	}
	
	public SellerResponse getAllSellerItem() throws JAXBException, IOException {

		Svc service = HttpExecutorService.executeHttpService(Constants.SELLERSERVICES_PATH.GET_ALL_SELLERITEM, null, SERVICE_TYPE.SELLERSERVICES.toString(), HTTPMethods.GET, null, getSellerServicesHeader());
		SellerResponse sellerResponse = (SellerResponse) APIUtilities.getJsontoObject(service.getResponseBody(), new SellerResponse());
		return sellerResponse;
	}
	
	
	
	public SellerResponse addSellerBrand(String brandId,String brandName,String sellerId) throws JAXBException, IOException {

		SellerEntry sellerEntry = new SellerEntry();
		BrandEntry brandEntry = new BrandEntry();
		brandEntry.setBrandId(Long.parseLong(brandId));
		brandEntry.setBrandName(brandName);
		sellerEntry.setId(Long.parseLong(sellerId));
		brandEntry.setSellerEntry(sellerEntry);
		BrandResponse brandResponse = new BrandResponse();
		List<BrandEntry> BR = new ArrayList<BrandEntry>();
		BR.add(brandEntry);
		brandResponse.setData(BR);
		String payload = APIUtilities.getObjectToJSON(brandResponse);	
		Svc service = HttpExecutorService.executeHttpService(Constants.SELLERSERVICES_PATH.ADD_BRAND, null, SERVICE_TYPE.SELLERSERVICES.toString(), HTTPMethods.POST, payload, getSellerServicesHeader());
		SellerResponse sellerResponse = (SellerResponse) APIUtilities.getJsontoObject(service.getResponseBody(), new SellerResponse());
		return sellerResponse;
	}
	
	
	public SellerResponse addCategoryManager(String enabled, String sellerId,String userId) throws JAXBException, IOException {

		SellerEntry sellerEntry = new SellerEntry();
		UserEntry userEntry = new UserEntry();
		CategoryManagerEntry categoryManagerEntry = new CategoryManagerEntry();
		sellerEntry.setId(Long.parseLong(sellerId));
		userEntry.setId(Long.parseLong(userId));
		categoryManagerEntry.setEnabled(Boolean.parseBoolean(enabled));
		categoryManagerEntry.setSellerEntry(sellerEntry);
		categoryManagerEntry.setUserEntry(userEntry);
		List<CategoryManagerEntry> cm = new ArrayList<>();
		cm.add(categoryManagerEntry);
		CategoryManagerResponse categoryManagerResponse = new CategoryManagerResponse();
		categoryManagerResponse.setData(cm);
		
		String payload = APIUtilities.getObjectToJSON(categoryManagerResponse);	
		Svc service = HttpExecutorService.executeHttpService(Constants.SELLERSERVICES_PATH.ADD_CATEGORY_MANAGER, null, SERVICE_TYPE.SELLERSERVICES.toString(), HTTPMethods.POST, payload, getSellerServicesHeader());
		SellerResponse sellerResponse = (SellerResponse) APIUtilities.getJsontoObject(service.getResponseBody(), new SellerResponse());
		return sellerResponse;
	}
	
	public SellerResponse addWarehouse(String leadTime,String sellerId,String warehouseId) throws JAXBException, IOException {

		SellerEntry sellerEntry = new SellerEntry();
		WarehouseEntry warehouseEntry = new WarehouseEntry();
		
		SellerWarehouseEntry sellerWarehouseEntry = new SellerWarehouseEntry();
		sellerEntry.setId(Long.parseLong(sellerId));
        warehouseEntry.setId(Long.parseLong(warehouseId));
        sellerWarehouseEntry.setLeadTime(Integer.parseInt(leadTime));
        sellerWarehouseEntry.setSellerEntry(sellerEntry);
        sellerWarehouseEntry.setSupplyType(SupplyType.ON_HAND);
        sellerWarehouseEntry.setWarehouseEntry(warehouseEntry);
        
		String payload = APIUtilities.getObjectToJSON(sellerWarehouseEntry);	
		Svc service = HttpExecutorService.executeHttpService(Constants.SELLERSERVICES_PATH.ADD_SELLER_WAREHOUSE, null, SERVICE_TYPE.SELLERSERVICES.toString(), HTTPMethods.POST, payload, getSellerServicesHeader());
		SellerResponse sellerResponse = (SellerResponse) APIUtilities.getJsontoObject(service.getResponseBody(), new SellerResponse());
		return sellerResponse;
	}
	
	
	public SellerResponse addFinancialInfo(String sellerId,String accountId,String enabled) throws JAXBException, IOException {

		SellerEntry sellerEntry = new SellerEntry();
		FinanceInformationEntry financeInformationEntry = new FinanceInformationEntry();
		sellerEntry.setId(Long.parseLong(sellerId));
		
		financeInformationEntry.setSellerEntry(sellerEntry);
		financeInformationEntry.setAccountId(accountId);
		financeInformationEntry.setEnabled(Boolean.parseBoolean(enabled));
	
		String payload = APIUtilities.getObjectToJSON(financeInformationEntry);	
		Svc service = HttpExecutorService.executeHttpService(Constants.SELLERSERVICES_PATH.ADD_FINANCE_INFORMATION, null, SERVICE_TYPE.SELLERSERVICES.toString(), HTTPMethods.POST, payload, getSellerServicesHeader());
		SellerResponse sellerResponse = (SellerResponse) APIUtilities.getJsontoObject(service.getResponseBody(), new SellerResponse());
		return sellerResponse;
	}
	
	public SellerResponse addPaymentConfig(String sellerId,String marginPercentage,String enabled) throws JAXBException, IOException {

		SellerEntry sellerEntry = new SellerEntry();
		
		PaymentConfigurationEntry paymentConfigurationEntry = new PaymentConfigurationEntry();
		sellerEntry.setId(Long.parseLong(sellerId));
		
		paymentConfigurationEntry.setSellerEntry(sellerEntry);
		paymentConfigurationEntry.setMarginPercentage(Double.parseDouble(marginPercentage));
		paymentConfigurationEntry.setPayoutScheduleType(PayoutScheduleType.ON_ORDER_DELIVERED);
		paymentConfigurationEntry.setEnabled(Boolean.parseBoolean(enabled));
		
		String payload = APIUtilities.getObjectToJSON(paymentConfigurationEntry);	
		Svc service = HttpExecutorService.executeHttpService(Constants.SELLERSERVICES_PATH.ADD_PAYMENT_CONFIGURATION, null, SERVICE_TYPE.SELLERSERVICES.toString(), HTTPMethods.POST, payload, getSellerServicesHeader());
		SellerResponse sellerResponse = (SellerResponse) APIUtilities.getJsontoObject(service.getResponseBody(), new SellerResponse());
		return sellerResponse;
	}
	
	public SellerResponse addKycDoc(String sellerId,String documentNumber,String documentRefId,String documentExtension,String enabled,String entityId) throws JAXBException, IOException {

		KYCDocumentEntry kycDocumentEntry = new KYCDocumentEntry();
	
		kycDocumentEntry.setSellerId(Long.parseLong(sellerId));
		kycDocumentEntry.setDocumentType(KYCDocumentType.SIGNATURE);
		kycDocumentEntry.setDocumentNumber(documentNumber);
		kycDocumentEntry.setDocumentRefId(documentRefId);
		kycDocumentEntry.setDocumentExtension(documentExtension);
		kycDocumentEntry.setEnabled(Boolean.parseBoolean(enabled));
		kycDocumentEntry.setKycEntityType(KYCEntityType.SELLER);
		kycDocumentEntry.setEntityId(Long.parseLong(entityId));
		
		List<KYCDocumentEntry> kd = new ArrayList<>();
		kd.add(kycDocumentEntry);
		KYCDocumentResponse kycDocumentResponse = new KYCDocumentResponse();
		kycDocumentResponse.setData(kd);
		
		String payload = APIUtilities.getObjectToJSON(kycDocumentResponse);	
		Svc service = HttpExecutorService.executeHttpService(Constants.SELLERSERVICES_PATH.ADD_KYC_DOCUMENT, null, SERVICE_TYPE.SELLERSERVICES.toString(), HTTPMethods.POST, payload, getSellerServicesHeader());
		SellerResponse sellerResponse = (SellerResponse) APIUtilities.getJsontoObject(service.getResponseBody(), new SellerResponse());
		return sellerResponse;
	}
	
	public SellerResponse addShipping(String address1,String city,String country,String email1,String enabled,String sellerId, String phone1,String pinCode,String state)  throws JAXBException, IOException {

		
		AddressEntry addressEntry = new AddressEntry();
	
		addressEntry.setAddress1(address1);
		addressEntry.setAddressType(AddressType.SHIPPING);
		addressEntry.setCity(city);
		addressEntry.setCountry(country);
		addressEntry.setEmail1(email1);
		addressEntry.setEnabled(Boolean.parseBoolean(enabled));
		addressEntry.setSellerId(Long.parseLong(sellerId));
		addressEntry.setPhone1(phone1);
		addressEntry.setPinCode(pinCode);
		addressEntry.setState(state);
		
		
		List<AddressEntry> AD = new ArrayList<>();
		AD.add(addressEntry);
		AddressResponse addressResponse = new AddressResponse();
		addressResponse.setData(AD);
		
		String payload = APIUtilities.getObjectToJSON(addressResponse);	
		Svc service = HttpExecutorService.executeHttpService(Constants.SELLERSERVICES_PATH.ADD_ADDRESS, null, SERVICE_TYPE.SELLERSERVICES.toString(), HTTPMethods.POST, payload, getSellerServicesHeader());
		SellerResponse sellerResponse = (SellerResponse) APIUtilities.getJsontoObject(service.getResponseBody(), new SellerResponse());
		return sellerResponse;
	}
	
public SellerResponse addBilling(String address1,String city,String country,String email1,String enabled,String sellerId, String phone1,String pinCode,String state)  throws JAXBException, IOException {

		
		AddressEntry addressEntry = new AddressEntry();
	
		addressEntry.setAddress1(address1);
		addressEntry.setAddressType(AddressType.BILLING);
		addressEntry.setCity(city);
		addressEntry.setCountry(country);
		addressEntry.setEmail1(email1);
		addressEntry.setEnabled(Boolean.parseBoolean(enabled));
		addressEntry.setSellerId(Long.parseLong(sellerId));
		addressEntry.setPhone1(phone1);
		addressEntry.setPinCode(pinCode);
		addressEntry.setState(state);
		
		List<AddressEntry> AD = new ArrayList<>();
		AD.add(addressEntry);
		AddressResponse addressResponse = new AddressResponse();
		addressResponse.setData(AD);
		
		String payload = APIUtilities.getObjectToJSON(addressResponse);	
		Svc service = HttpExecutorService.executeHttpService(Constants.SELLERSERVICES_PATH.ADD_ADDRESS, null, SERVICE_TYPE.SELLERSERVICES.toString(), HTTPMethods.POST, payload, getSellerServicesHeader());
		SellerResponse sellerResponse = (SellerResponse) APIUtilities.getJsontoObject(service.getResponseBody(), new SellerResponse());
		return sellerResponse;
	}

public SellerItemMasterResponse addSellerItemMaster(String sellerId, String sellerSkuCode , String myntraSkuCode,String enabled,String styleId,String sellerBarcode)  throws JAXBException, IOException {


	SellerItemMasterEntry sellerItemMasterEntry = new SellerItemMasterEntry();
	
	sellerItemMasterEntry.setSellerId(Long.parseLong(sellerId));
	sellerItemMasterEntry.setSellerSkuCode(sellerSkuCode);
	sellerItemMasterEntry.setMyntraSkuCode(myntraSkuCode);
	sellerItemMasterEntry.setEnabled(Boolean.parseBoolean(enabled));
	sellerItemMasterEntry.setStyleId(Long.parseLong(styleId));
	sellerItemMasterEntry.setSellerBarcode(sellerBarcode);
	List<SellerItemMasterEntry> data = new ArrayList<>();
	data.add(sellerItemMasterEntry);
	
	String pay = APIUtilities.getObjectToJSON(data);
	String payload = "{\"data\":"+ pay+"}";
	Svc service = HttpExecutorService.executeHttpService(Constants.SELLERSERVICES_PATH.ADD_SELLERITEMMASTER, null, SERVICE_TYPE.SELLERSERVICES.toString(), HTTPMethods.POST, payload, getSellerServicesHeader());
	SellerItemMasterResponse sellerItemMasterResponse = (SellerItemMasterResponse) APIUtilities.getJsontoObject(service.getResponseBody(), new SellerItemMasterResponse());
	return sellerItemMasterResponse;
}

public SellerTermsResponse getSellerCommision(String active,String status,String articleType,String brand,String gender,String masterCategory,String sellerId)  throws JAXBException, IOException {


	SellerTermsEntry sellerTermsEntry = new SellerTermsEntry();
	
	sellerTermsEntry.setSellerId(Long.parseLong(sellerId));
	sellerTermsEntry.setActive(Boolean.parseBoolean(active));
	sellerTermsEntry.setStatus(status);
	sellerTermsEntry.setArticleType(articleType);
	sellerTermsEntry.setBrand(brand);
	sellerTermsEntry.setCustomerPaymentType(CustomerPaymentType.ONLINE);
	sellerTermsEntry.setGender(gender);
	sellerTermsEntry.setMasterCategory(masterCategory);
	
	
//	String pay = APIUtilities.getObjectToJSON(data);
//	String payload = "{\"data\":"+ pay+"}";
	
	String payload = APIUtilities.getObjectToJSON(sellerTermsEntry);	
	Svc service = HttpExecutorService.executeHttpService(Constants.SELLERSERVICES_PATH.GET_SELLER_COMMISSION, null, SERVICE_TYPE.SELLERSERVICES.toString(), HTTPMethods.POST, payload, getSellerServicesHeader());
	SellerTermsResponse sellerTermsResponse1 = (SellerTermsResponse) APIUtilities.getJsontoObject(service.getResponseBody(), new SellerTermsResponse());
	return sellerTermsResponse1;
}

public SellerItemMasterResponse getSellerItemBySkuId(String sellerId,String skuId)  throws JAXBException, IOException {

	
SellerItemMasterEntry sellerItemMasterEntry = new SellerItemMasterEntry();
	
	sellerItemMasterEntry.setSkuId(Long.parseLong(skuId));
	
	List<SellerItemMasterEntry> data = new ArrayList<>();
	data.add(sellerItemMasterEntry);
	
	String pay = APIUtilities.getObjectToJSON(data);
	String payload = "{\"data\":"+ pay+"}";
	
	//String payload = skuId.toString();
	Svc service = HttpExecutorService.executeHttpService(Constants.SELLERSERVICES_PATH.GET_SELLERITEM_BY_SKUID, new String[] {sellerId, "sellerItemsbySkuId"}, SERVICE_TYPE.SELLERSERVICES.toString(), HTTPMethods.POST, payload, getSellerServicesHeader());
	SellerItemMasterResponse sellerItemMasterResponse = (SellerItemMasterResponse) APIUtilities.getJsontoObject(service.getResponseBody(), new SellerItemMasterResponse());
	return sellerItemMasterResponse;
}

public SellerItemMasterResponse getSellerItemBySkuCode(String sellerId,String sellerSkuCode)  throws JAXBException, IOException {

	SellerItemMasterEntry sellerItemMasterEntry = new SellerItemMasterEntry();
	
	sellerItemMasterEntry.setSellerSkuCode(sellerSkuCode);
	
	List<SellerItemMasterEntry> data = new ArrayList<>();
	data.add(sellerItemMasterEntry);
	
	String pay = APIUtilities.getObjectToJSON(data);
	String payload = "{\"data\":"+ pay+"}";
	
	//String payload = skuCode.toString();
	Svc service = HttpExecutorService.executeHttpService(Constants.SELLERSERVICES_PATH.GET_SELLERITEM_BY_SKUCODE, new String[] {sellerId, "sellerItemsbySellerSKU"}, SERVICE_TYPE.SELLERSERVICES.toString(), HTTPMethods.POST, payload, getSellerServicesHeader());
	SellerItemMasterResponse sellerItemMasterResponse = (SellerItemMasterResponse) APIUtilities.getJsontoObject(service.getResponseBody(), new SellerItemMasterResponse());
	return sellerItemMasterResponse;
}



public SellerResponse disableBrand(String Id) throws JAXBException, IOException {

	Svc service = HttpExecutorService.executeHttpService(Constants.SELLERSERVICES_PATH.DISABLE_BRAND, new String[] {Id}, SERVICE_TYPE.SELLERSERVICES.toString(), HTTPMethods.PUT, null, getSellerServicesHeader());
	SellerResponse sellerResponse = (SellerResponse) APIUtilities.getJsontoObject(service.getResponseBody(), new SellerResponse());
	return sellerResponse;
}

public SellerResponse disableCategoryManager(String Id) throws JAXBException, IOException {

	Svc service = HttpExecutorService.executeHttpService(Constants.SELLERSERVICES_PATH.DISABLE_CATEGORY_MANAGER, new String[] {Id}, SERVICE_TYPE.SELLERSERVICES.toString(), HTTPMethods.PUT, null, getSellerServicesHeader());
	SellerResponse sellerResponse = (SellerResponse) APIUtilities.getJsontoObject(service.getResponseBody(), new SellerResponse());
	return sellerResponse;
}

public SellerResponse disableWarehouse(String Id) throws JAXBException, IOException {

	Svc service = HttpExecutorService.executeHttpService(Constants.SELLERSERVICES_PATH.DISABLE_SELLER_WAREHOUSE, new String[] {Id}, SERVICE_TYPE.SELLERSERVICES.toString(), HTTPMethods.PUT, null, getSellerServicesHeader());
	SellerResponse sellerResponse = (SellerResponse) APIUtilities.getJsontoObject(service.getResponseBody(), new SellerResponse());
	return sellerResponse;
}

public SellerResponse disableFinanceInfo(String Id) throws JAXBException, IOException {

	Svc service = HttpExecutorService.executeHttpService(Constants.SELLERSERVICES_PATH.DISABLE_FINANCE_INFORMATION, new String[] {Id}, SERVICE_TYPE.SELLERSERVICES.toString(), HTTPMethods.PUT, null, getSellerServicesHeader());
	SellerResponse sellerResponse = (SellerResponse) APIUtilities.getJsontoObject(service.getResponseBody(), new SellerResponse());
	return sellerResponse;
}

public SellerResponse disablePaymentConfig(String Id) throws JAXBException,  IOException {

	Svc service = HttpExecutorService.executeHttpService(Constants.SELLERSERVICES_PATH.DISABLE_PAYMENT_CONFIGURATION, new String[] {Id}, SERVICE_TYPE.SELLERSERVICES.toString(), HTTPMethods.PUT, null, getSellerServicesHeader());
	SellerResponse sellerResponse = (SellerResponse) APIUtilities.getJsontoObject(service.getResponseBody(), new SellerResponse());
	return sellerResponse;
}

	private HashMap<String,String> getSellerServicesHeader() {
    	HashMap<String, String> createOrderHeaders = new HashMap<String, String>();
		createOrderHeaders.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0=");
		createOrderHeaders.put("Content-Type", "Application/json");
		createOrderHeaders.put("Accept", "Application/json");
		return createOrderHeaders;
    }
	
}
