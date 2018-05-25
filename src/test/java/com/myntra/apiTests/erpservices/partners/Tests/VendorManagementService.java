package com.myntra.apiTests.erpservices.partners.Tests;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.HashMap;

import com.myntra.apiTests.common.Myntra;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.myntra.apiTests.erpservices.Constants;
import com.myntra.apiTests.erpservices.partners.VMSHelper;
import com.myntra.apiTests.erpservices.partners.dp.VmsTestDP;
import com.myntra.client.contact.codes.ContactSuccessCodes;
import com.myntra.lordoftherings.Initialize;
import com.myntra.apiTests.SERVICE_TYPE;
import com.myntra.apiTests.common.APINAME;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.PayloadType;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.apiTests.common.ServiceType;
import com.myntra.test.commons.service.HTTPMethods;
import com.myntra.test.commons.service.HttpExecutorService;
import com.myntra.test.commons.service.Svc;
import com.myntra.test.commons.testbase.BaseTest;
import com.myntra.vms.client.code.VmsErrorCodes;
import com.myntra.vms.client.code.VmsSuccessCodes;

public class VendorManagementService extends BaseTest {
	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = LoggerFactory.getLogger(VendorManagementService.class);
	VMSHelper vmsHelper = new VMSHelper();
	static String vendorId;
	static String vendorWarehouseId;
	static String vendorCategoryManagerId;

	public static HashMap<String, String> getParam() {
		HashMap<String, String> getParam = new HashMap<>();
		getParam.put("Authorization", "Basic YXBpYWRtaW46bTFudHJhUjBja2V0MTMhIw==");
		return getParam;
	}

	/** API Name: Add Vendor API **/

	@Test(groups = { "Regression" }, priority = 0, dataProviderClass = VmsTestDP.class, dataProvider = "addVendor")
	public void VMS_addVendor(String approver, String description, String name, String poReceiversEmails,
			String readyToSplit, String shipToAllWarehouses) throws SQLException {
		MyntraService service = Myntra.getService(ServiceType.ERP_MARKETPLACEVMS, APINAME.ADDVENDOR,
				init.Configurations, new String[] { approver, description, name, poReceiversEmails, readyToSplit,
						shipToAllWarehouses },
				PayloadType.JSON, PayloadType.JSON);

		log.debug(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam());
		log.debug(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());

		String responseStatusCode = req.respvalidate.NodeValue("vendorResponse.status.statusCode", true)
				.replaceAll("\"", "").trim();
		String responseStatusType = req.respvalidate.NodeValue("vendorResponse.status.statusType", true)
				.replaceAll("\"", "").trim();
		String responseStatusMessage = req.respvalidate.NodeValue("vendorResponse.status.statusMessage", true)
				.replaceAll("\"", "").trim();

		Assert.assertEquals(Integer.parseInt(responseStatusCode),
				VmsSuccessCodes.VENDOR_ADDED.getStatusCode(), "StatusCode is invalid :");
		Assert.assertEquals(responseStatusType, VmsSuccessCodes.SUCCESS.getStatusMessage().toUpperCase(),
				"StatusType is invalid :");
		Assert.assertEquals(responseStatusMessage, VmsSuccessCodes.VENDOR_ADDED.getStatusMessage(),
				"StatusMessage is invalid :");
		

		vendorId = req.respvalidate.NodeValue("vendorResponse.data.vendor.id", true).replaceAll("\"", "").trim();
		log.debug("Vendor ID: " + vendorId);

		Assert.assertEquals(
				req.respvalidate.NodeValue("vendorResponse.data.vendor.status", true).replaceAll("\"", "").trim(),
				"CREATED", "Vendor Status: ");

	}

	/** API Name - Add vendors Brand **/

	@Test(groups = { "Regression" }, priority = 0, dependsOnMethods = {
			"VMS_addVendor" }, dataProviderClass = VmsTestDP.class, dataProvider = "addVendorBrand")
	public void Vendor_addBrand(String brandId, String brandName, String enable, String vendor) {
		MyntraService service = Myntra.getService(
				ServiceType.ERP_MARKETPLACEVMS, APINAME.ADDVENDORBRAND, init.Configurations, new String[] { brandId,
						brandName, enable, vendorId},
				PayloadType.JSON, PayloadType.JSON);

		log.debug(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam());
		log.debug(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());

		String responseStatusCode = req.respvalidate.NodeValue("vendorBrandResponse.status.statusCode", true)
				.replaceAll("\"", "").trim();
		String responseStatusType = req.respvalidate.NodeValue("vendorBrandResponse.status.statusType", true)
				.replaceAll("\"", "").trim();
		String responseStatusMessage = req.respvalidate.NodeValue("vendorBrandResponse.status.statusMessage", true)
				.replaceAll("\"", "").trim();

		Assert.assertEquals(Integer.parseInt(responseStatusCode),
				VmsSuccessCodes.VENDOR_BRAND_ADDED.getStatusCode(), "StatusCode is invalid :");
		Assert.assertEquals(responseStatusType, VmsSuccessCodes.SUCCESS.getStatusMessage().toUpperCase(),
				"StatusType is invalid :");
		Assert.assertEquals(responseStatusMessage, VmsSuccessCodes.VENDOR_BRAND_ADDED.getStatusMessage(),
				"StatusMessage is invalid :");
	}

	/** API Name - Add vendors Category Manager **/

	@Test(groups = { "Regression" }, priority = 0, dependsOnMethods = {
			"Vendor_addBrand" }, dataProviderClass = VmsTestDP.class, dataProvider = "addVendorCategoryManager")
	public void Vendor_addCategoryManager(String enable, String user, String vendor) {
		MyntraService service = Myntra.getService(ServiceType.ERP_MARKETPLACEVMS, APINAME.ADDVENDORCATEGORYMANAGER,
				init.Configurations, new String[] { enable, user, vendorId }, PayloadType.JSON, PayloadType.JSON);

		log.debug(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam());
		log.debug(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());

		String responseStatusCode = req.respvalidate.NodeValue("vendorCategoryManagerResponse.status.statusCode", true)
				.replaceAll("\"", "").trim();
		String responseStatusType = req.respvalidate.NodeValue("vendorCategoryManagerResponse.status.statusType", true)
				.replaceAll("\"", "").trim();
		String responseStatusMessage = req.respvalidate
				.NodeValue("vendorCategoryManagerResponse.status.statusMessage", true).replaceAll("\"", "").trim();
		vendorWarehouseId = req.respvalidate
				.NodeValue("vendorCategoryManagerResponse.data.vendorCategoryManager.id", true).replaceAll("\"", "")
				.trim();

		Assert.assertEquals(Integer.parseInt(responseStatusCode),
				VmsSuccessCodes.VENDOR_CATEGORY_MANAGER_ADDED.getStatusCode(), "StatusCode is invalid :");
		Assert.assertEquals(responseStatusType, VmsSuccessCodes.SUCCESS.getStatusMessage().toUpperCase(),
				"StatusType is invalid :");
		Assert.assertEquals(responseStatusMessage, VmsSuccessCodes.VENDOR_CATEGORY_MANAGER_ADDED.getStatusMessage(),
				"StatusMessage is invalid :");

	}

	/** API Name - Add vendors Warehouse **/

	@Test(groups = { "Regression" }, priority = 0, dependsOnMethods = {
			"Vendor_addCategoryManager" }, dataProviderClass = VmsTestDP.class, dataProvider = "addVendorWarehouse")
	public void Vendor_addWarehouse(String leadTime, String supplyType, String taxType, String vendor, String warehouse) {
		MyntraService service = Myntra.getService(ServiceType.ERP_MARKETPLACEVMS,
				APINAME.ADDVENDORWAREHOUSE, init.Configurations, new String[] { leadTime, supplyType, taxType, vendorId,
						warehouse},
				PayloadType.JSON, PayloadType.JSON);

		log.debug(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam());
		log.debug(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());

		String responseStatusCode = req.respvalidate.NodeValue("vendorWarehouseResponse.status.statusCode", true)
				.replaceAll("\"", "").trim();
		String responseStatusType = req.respvalidate.NodeValue("vendorWarehouseResponse.status.statusType", true)
				.replaceAll("\"", "").trim();
		String responseStatusMessage = req.respvalidate.NodeValue("vendorWarehouseResponse.status.statusMessage", true)
				.replaceAll("\"", "").trim();
		vendorWarehouseId = req.respvalidate
				.NodeValue("vendorWarehouseResponse.data.vendorWarehouse.warehouse.id", true).replaceAll("\"", "")
				.trim();

		Assert.assertEquals(Integer.parseInt(responseStatusCode),
				VmsSuccessCodes.VENDOR_WAREHOUSE_ADDED.getStatusCode(), "StatusCode is invalid :");
		Assert.assertEquals(responseStatusType, VmsSuccessCodes.SUCCESS.getStatusMessage().toUpperCase(),
				"StatusType is invalid :");
		Assert.assertEquals(responseStatusMessage, VmsSuccessCodes.VENDOR_WAREHOUSE_ADDED.getStatusMessage(),
				"StatusMessage is invalid :");
	}

	/** API Name - Add vendors Contact Person **/

	@Test(groups = { "Regression" }, priority = 0, dependsOnMethods = {
			"Vendor_addWarehouse" }, dataProviderClass = VmsTestDP.class, dataProvider = "addVendorContact")
	public void Vendor_addContactPerson(String entityId, String entityName, String firstName, String lastName,
			String middleName, String primaryEmail, String primaryPhoneNumber, String secondaryEmail,
			String secondaryPhoneNumber, String title) {

		MyntraService service = Myntra.getService(ServiceType.ERP_MARKETPLACEVMSVENDOR, APINAME.ADDVENDORCONTACT,
				init.Configurations,
				new String[] { vendorId, entityName, firstName, lastName, middleName, primaryEmail, primaryPhoneNumber,
						secondaryEmail, secondaryPhoneNumber, title},
				PayloadType.JSON, PayloadType.JSON);

		log.debug(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam());
		log.debug(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());

		String responseStatusCode = req.respvalidate.NodeValue("contactPersonResponse.status.statusCode", true)
				.replaceAll("\"", "").trim();
		String responseStatusType = req.respvalidate.NodeValue("contactPersonResponse.status.statusType", true)
				.replaceAll("\"", "").trim();
		String responseStatusMessage = req.respvalidate.NodeValue("contactPersonResponse.status.statusMessage", true)
				.replaceAll("\"", "").trim();

		Assert.assertEquals(Integer.parseInt(responseStatusCode),
				ContactSuccessCodes.CONTACT_ADDED.getStatusCode(), "StatusCode is invalid :");
		Assert.assertEquals(responseStatusType, VmsSuccessCodes.SUCCESS.getStatusMessage().toUpperCase(),
				"StatusType is invalid :");
		Assert.assertEquals(responseStatusMessage, ContactSuccessCodes.CONTACT_ADDED.getStatusMessage(),
				"StatusMessage is invalid :");
	}

	/** API Name - Add vendors Contact Address **/

	@Test(groups = { "Regression" }, priority = 0, dependsOnMethods = {
			"Vendor_addContactPerson" }, dataProviderClass = VmsTestDP.class, dataProvider = "addVendorAddress")
	public void Vendor_addContactAddress(String address1, String address2, String city, String country, String entityId,
			String entityName, String notes, String postalCode, String primaryEmail, String primaryPhoneNumber,
			String secondaryEmail, String secondaryPhoneNumber, String state) {
		MyntraService service = Myntra.getService(ServiceType.ERP_MARKETPLACEVMSVENDOR, APINAME.ADDVENDORADDRESS,
				init.Configurations,
				new String[] { address1, address2, city, country, vendorId, entityName, notes, postalCode, primaryEmail,
						primaryPhoneNumber, secondaryEmail, secondaryPhoneNumber, state},
				PayloadType.JSON, PayloadType.JSON);

		log.debug(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam());
		log.debug(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());

		String responseStatusCode = req.respvalidate.NodeValue("contactAddressResponse.status.statusCode", true)
				.replaceAll("\"", "").trim();
		String responseStatusType = req.respvalidate.NodeValue("contactAddressResponse.status.statusType", true)
				.replaceAll("\"", "").trim();
		String responseStatusMessage = req.respvalidate.NodeValue("contactAddressResponse.status.statusMessage", true)
				.replaceAll("\"", "").trim();

		Assert.assertEquals(Integer.parseInt(responseStatusCode),
				ContactSuccessCodes.ADDRESS_ADDED.getStatusCode(), "StatusCode is invalid :");
		Assert.assertEquals(responseStatusType, VmsSuccessCodes.SUCCESS.getStatusMessage().toUpperCase(),
				"StatusType is invalid :");
		Assert.assertEquals(responseStatusMessage, ContactSuccessCodes.ADDRESS_ADDED.getStatusMessage(),
				"StatusMessage is invalid :");
	}

	/** API Name - Add vendors Bank details **/

	@Test(groups = { "Regression" }, priority = 0, dependsOnMethods = {
			"Vendor_addContactAddress" }, dataProviderClass = VmsTestDP.class, dataProvider = "addBankDetails")
	public void Vendor_addBankDetails(String accountId, String city, String enabled) {
		MyntraService service = Myntra.getService(ServiceType.ERP_MARKETPLACEVMS, APINAME.ADDVENDORBANKDETAILS,
				init.Configurations, new String[] { vendorId, accountId, city, enabled },
				new String[] { "vendorFinanceInformation" }, PayloadType.JSON, PayloadType.JSON);

		log.debug(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam());
		log.debug(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());

		String responseStatusCode = req.respvalidate
				.NodeValue("VendorFinanceInformationResponse.status.statusCode", true).replaceAll("\"", "").trim();
		String responseStatusType = req.respvalidate
				.NodeValue("VendorFinanceInformationResponse.status.statusType", true).replaceAll("\"", "").trim();
		String responseStatusMessage = req.respvalidate
				.NodeValue("VendorFinanceInformationResponse.status.statusMessage", true).replaceAll("\"", "").trim();

		Assert.assertEquals(Integer.parseInt(responseStatusCode),
				VmsSuccessCodes.VENDOR_FINANCE_INFO_ADDED.getStatusCode(), "StatusCode is invalid :");
		Assert.assertEquals(responseStatusType, VmsSuccessCodes.SUCCESS.getStatusMessage().toUpperCase(),
				"StatusType is invalid :");
		Assert.assertEquals(responseStatusMessage, VmsSuccessCodes.VENDOR_FINANCE_INFO_ADDED.getStatusMessage(),
				"StatusMessage is invalid :");
	}

	@Test(groups = { "Regression" }, priority = 0, dependsOnMethods = {
			"Vendor_addBankDetails" }, dataProviderClass = VmsTestDP.class, dataProvider = "addAdditionalDetails")
	public void Vendor_addAdditionalDetails(String entityType, String msme) {
		MyntraService service = Myntra.getService(ServiceType.ERP_MARKETPLACEVMS, APINAME.ADDVENDORADDITIONALDETAILS,
				init.Configurations, new String[] { entityType, msme }, new String[] { vendorId }, PayloadType.JSON,
				PayloadType.JSON);

		log.debug(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam());
		log.debug(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());

		String responseStatusCode = req.respvalidate.NodeValue("vendorResponse.status.statusCode", true)
				.replaceAll("\"", "").trim();
		String responseStatusType = req.respvalidate.NodeValue("vendorResponse.status.statusType", true)
				.replaceAll("\"", "").trim();
		String responseStatusMessage = req.respvalidate.NodeValue("vendorResponse.status.statusMessage", true)
				.replaceAll("\"", "").trim();

		Assert.assertEquals(Integer.parseInt(responseStatusCode), VmsSuccessCodes.VENDOR_UPDATED.getStatusCode(),
				"StatusCode is invalid :");
		Assert.assertEquals(responseStatusType, VmsSuccessCodes.SUCCESS.getStatusMessage().toUpperCase(),
				"StatusType is invalid :");
		Assert.assertEquals(responseStatusMessage, VmsSuccessCodes.VENDOR_UPDATED.getStatusMessage(),
				"StatusMessage is invalid :");
	}

	@Test(groups = { "Regression" }, priority = 0, dependsOnMethods = {
			"Vendor_addAdditionalDetails" }, dataProviderClass = VmsTestDP.class, dataProvider = "createAddress")
	public void Vendor_createAddress(String addressType, String address1, String address2, String city, String state,
			String pinCode, String country, String phone1, String phone2, String email1, String email2, String notes,
			String partnerType, String isInternational , String enabled) {
		MyntraService service = Myntra.getService(ServiceType.ERP_MARKETPLACEVMS, APINAME.CREATEVENDORADDRESS,
				init.Configurations, new String[] { vendorId, addressType, address1, address2, city, state, pinCode,
						country, phone1, phone2, email1, email2, notes, partnerType,isInternational, enabled },
				PayloadType.JSON, PayloadType.JSON);

		log.debug(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam());
		log.debug(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());

		String responseStatusCode = req.respvalidate.NodeValue("partnerContactAddressResponse.status.statusCode", true)
				.replaceAll("\"", "").trim();
		String responseStatusType = req.respvalidate.NodeValue("partnerContactAddressResponse.status.statusType", true)
				.replaceAll("\"", "").trim();
		String responseStatusMessage = req.respvalidate
				.NodeValue("partnerContactAddressResponse.status.statusMessage", true).replaceAll("\"", "").trim();

		Assert.assertEquals(Integer.parseInt(responseStatusCode),
				VmsSuccessCodes.PARTNER_CONTACT_ADDRESS_ADDED.getStatusCode(), "StatusCode is invalid :");
		Assert.assertEquals(responseStatusType, VmsSuccessCodes.SUCCESS.getStatusMessage().toUpperCase(),
				"StatusType is invalid :");
		Assert.assertEquals(responseStatusMessage, VmsSuccessCodes.PARTNER_CONTACT_ADDRESS_ADDED.getStatusMessage(),
				"StatusMessage is invalid :");
	}

	@Test(groups = { "Regression" }, dependsOnMethods = {
			"Vendor_createAddress" }, priority = 0, dataProviderClass = VmsTestDP.class, dataProvider = "addKycDetails")
	public void Vendor_addKycDetails(String documentType, String documnetNumebr, String partnerType, String enabled,
			String address_type, String kycEntityType) throws Exception {

		String kycEntityId = vmsHelper.getIdFromPartnerContactAddress(vendorId, address_type);
		MyntraService service = Myntra.getService(
				ServiceType.ERP_MARKETPLACEVMS, APINAME.ADDKYCDETAILS, init.Configurations, new String[] { vendorId,
						documentType, documnetNumebr, partnerType, enabled, kycEntityId, kycEntityType },
				PayloadType.JSON, PayloadType.JSON);

		log.debug(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam());
		log.debug(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());

		String responseStatusCode = req.respvalidate.NodeValue("partnerKYCDocumentResponse.status.statusCode", true)
				.replaceAll("\"", "").trim();
		String responseStatusType = req.respvalidate.NodeValue("partnerKYCDocumentResponse.status.statusType", true)
				.replaceAll("\"", "").trim();
		String responseStatusMessage = req.respvalidate
				.NodeValue("partnerKYCDocumentResponse.status.statusMessage", true).replaceAll("\"", "").trim();

		Assert.assertEquals(Integer.parseInt(responseStatusCode),
				VmsSuccessCodes.PARTNER_KYC_DOCUMENT_ADDED.getStatusCode(), "StatusCode is invalid :");
		Assert.assertEquals(responseStatusType, VmsSuccessCodes.SUCCESS.getStatusMessage().toUpperCase(),
				"StatusType is invalid :");
		Assert.assertEquals(responseStatusMessage, VmsSuccessCodes.PARTNER_KYC_DOCUMENT_ADDED.getStatusMessage(),
				"StatusMessage is invalid :");

	}

	@Test(groups = { "Smoke", "Regression" }, dependsOnMethods = { "VMS_addVendor" }, priority = 0)
	public void VMS_findVendorById() {
		MyntraService service = Myntra.getService(ServiceType.ERP_MARKETPLACEVMS, APINAME.FINDVENDORBYID,
				init.Configurations, PayloadType.JSON, new String[] { vendorId }, PayloadType.JSON);

		HashMap<String, String> getParam = new HashMap<>();
		getParam.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0");

		log.debug(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam);
		log.debug(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());

		String responseStatusCode = req.respvalidate.NodeValue("vendorResponse.status.statusCode", true)
				.replaceAll("\"", "").trim();
		String responseStatusType = req.respvalidate.NodeValue("vendorResponse.status.statusType", true)
				.replaceAll("\"", "").trim();
		String responseStatusMessage = req.respvalidate.NodeValue("vendorResponse.status.statusMessage", true)
				.replaceAll("\"", "").trim();

		Assert.assertEquals(Integer.parseInt(responseStatusCode), VmsSuccessCodes.VENDOR_RETRIEVED.getStatusCode(),
				"StatusCode is invalid :");
		Assert.assertEquals(responseStatusType, VmsSuccessCodes.SUCCESS.getStatusMessage().toUpperCase(),
				"StatusType is invalid :");
		Assert.assertEquals(responseStatusMessage, VmsSuccessCodes.VENDOR_RETRIEVED.getStatusMessage(),
				"StatusMessage is invalid :");

	}

	/** API Name - Vendor Search -Lists all Vendors **/

	@Test(groups = { "Smoke", "Regression" }, priority = 0)
	public void VMS_vendorSearch() {
		MyntraService service = Myntra.getService(ServiceType.ERP_MARKETPLACEVMS, APINAME.VENDORSEARCH,
				init.Configurations, PayloadType.JSON, PayloadType.JSON);

		log.debug(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam());
		log.debug(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());

		String responseStatusCode = req.respvalidate.NodeValue("vendorResponse.status.statusCode", true)
				.replaceAll("\"", "").trim();
		String responseStatusType = req.respvalidate.NodeValue("vendorResponse.status.statusType", true)
				.replaceAll("\"", "").trim();
		String responseStatusMessage = req.respvalidate.NodeValue("vendorResponse.status.statusMessage", true)
				.replaceAll("\"", "").trim();

		Assert.assertEquals(Integer.parseInt(responseStatusCode), VmsSuccessCodes.VENDOR_RETRIEVED.getStatusCode(),
				"StatusCode is invalid :");
		Assert.assertEquals(responseStatusType, VmsSuccessCodes.SUCCESS.getStatusMessage().toUpperCase(),
				"StatusType is invalid :");
		Assert.assertEquals(responseStatusMessage, VmsSuccessCodes.VENDOR_RETRIEVED.getStatusMessage(),
				"StatusMessage is invalid :");

	}

	/** API Name - Get Brands Associated with Vendor **/

	@Test(groups = { "Regression" }, priority = 0, dependsOnMethods = { "Vendor_addBrand" })
	public void Vendor_getBrandsByVendorId() {
		MyntraService service = Myntra.getService(ServiceType.ERP_MARKETPLACEVMS, APINAME.SEARCHVENDORBRAND,
				init.Configurations, PayloadType.JSON, new String[] { vendorId }, PayloadType.JSON);

		log.debug(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam());
		log.debug(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());

		String responseStatusCode = req.respvalidate.NodeValue("vendorBrandResponse.status.statusCode", true)
				.replaceAll("\"", "").trim();
		String responseStatusType = req.respvalidate.NodeValue("vendorBrandResponse.status.statusType", true)
				.replaceAll("\"", "").trim();
		String responseStatusMessage = req.respvalidate.NodeValue("vendorBrandResponse.status.statusMessage", true)
				.replaceAll("\"", "").trim();

		Assert.assertEquals(Integer.parseInt(responseStatusCode),
				VmsSuccessCodes.VENDOR_BRAND_RETRIEVED.getStatusCode(), "StatusCode is invalid :");
		Assert.assertEquals(responseStatusType, VmsSuccessCodes.SUCCESS.getStatusMessage().toUpperCase(),
				"StatusType is invalid :");
		Assert.assertEquals(responseStatusMessage, VmsSuccessCodes.VENDOR_BRAND_RETRIEVED.getStatusMessage(),
				"StatusMessage is invalid :");
	}

	/** API Name - Lists all WareHouses **/

	@Test(groups = { "Smoke",
			"Regression" }, priority = 0, dataProviderClass = VmsTestDP.class, dataProvider = "whSearch")
	public void VMS_wareHouseSearch(String id, String paramStatusCode, String paramStatusType) {
		MyntraService service = Myntra.getService(ServiceType.ERP_MARKETPLACEVMS, APINAME.WHSEARCH, init.Configurations,
				PayloadType.JSON, new String[] { id, paramStatusCode, paramStatusType }, PayloadType.JSON);

		log.debug(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam());
		log.debug(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());

		String responseStatusCode = req.respvalidate.NodeValue("vendorWarehouseResponse.status.statusCode", true)
				.replaceAll("\"", "").trim();
		String responseStatusType = req.respvalidate.NodeValue("vendorWarehouseResponse.status.statusType", true)
				.replaceAll("\"", "").trim();
		String responseStatusMessage = req.respvalidate.NodeValue("vendorWarehouseResponse.status.statusMessage", true)
				.replaceAll("\"", "").trim();

		Assert.assertEquals(Integer.parseInt(responseStatusCode),
				VmsSuccessCodes.VENDOR_WAREHOUSE_RETRIEVED.getStatusCode(), "StatusCode is invalid :");
		Assert.assertEquals(responseStatusType, VmsSuccessCodes.SUCCESS.getStatusMessage().toUpperCase(),
				"StatusType is invalid :");
		Assert.assertEquals(responseStatusMessage, VmsSuccessCodes.VENDOR_WAREHOUSE_RETRIEVED.getStatusMessage(),
				"StatusMessage is invalid :");

	}

	/** API Name - get WareHouses assigned for VendorId **/

	@Test(groups = { "Smoke", "Regression" }, dependsOnMethods = { "Vendor_addWarehouse" }, priority = 0)
	public void VMS_getWarehousesByVendorId() {
		MyntraService service = Myntra.getService(ServiceType.ERP_MARKETPLACEVMS, APINAME.GETWAREHOUSEBYVENDORID,
				init.Configurations, PayloadType.JSON, new String[] { vendorId }, PayloadType.JSON);
		log.debug(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam());
		log.debug(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());

		String responseStatusCode = req.respvalidate.NodeValue("vendorWarehouseResponse.status.statusCode", true)
				.replaceAll("\"", "").trim();
		String responseStatusType = req.respvalidate.NodeValue("vendorWarehouseResponse.status.statusType", true)
				.replaceAll("\"", "").trim();
		String responseStatusMessage = req.respvalidate.NodeValue("vendorWarehouseResponse.status.statusMessage", true)
				.replaceAll("\"", "").trim();

		Assert.assertEquals(Integer.parseInt(responseStatusCode),
				VmsSuccessCodes.VENDOR_WAREHOUSE_RETRIEVED.getStatusCode(), "StatusCode is invalid :");
		Assert.assertEquals(responseStatusType, VmsSuccessCodes.SUCCESS.getStatusMessage().toUpperCase(),
				"StatusType is invalid :");
		Assert.assertEquals(responseStatusMessage, VmsSuccessCodes.VENDOR_WAREHOUSE_RETRIEVED.getStatusMessage(),
				"StatusMessage is invalid :");

	}

	@Test(groups = { "Smoke", "Regression" }, priority = 0, dependsOnMethods = { "Vendor_addBankDetails" })
	public void VMS_getBankDetailsByVendorId() {
		MyntraService service = Myntra.getService(ServiceType.ERP_MARKETPLACEVMS, APINAME.GETBANKDETAILSBYVENDORID,
				init.Configurations, PayloadType.JSON, new String[] { vendorId }, PayloadType.JSON);
		log.debug(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam());
		log.debug(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());

		String responseStatusCode = req.respvalidate
				.NodeValue("VendorFinanceInformationResponse.status.statusCode", true).replaceAll("\"", "").trim();
		String responseStatusType = req.respvalidate
				.NodeValue("VendorFinanceInformationResponse.status.statusType", true).replaceAll("\"", "").trim();
		String responseStatusMessage = req.respvalidate
				.NodeValue("VendorFinanceInformationResponse.status.statusMessage", true).replaceAll("\"", "").trim();

		Assert.assertEquals(Integer.parseInt(responseStatusCode),
				VmsSuccessCodes.VENDOR_FINANCE_INFO_RETRIEVED.getStatusCode(), "StatusCode is invalid :");
		Assert.assertEquals(responseStatusType, VmsSuccessCodes.SUCCESS.getStatusMessage().toUpperCase(),
				"StatusType is invalid :");
		Assert.assertEquals(responseStatusMessage, VmsSuccessCodes.VENDOR_FINANCE_INFO_RETRIEVED.getStatusMessage(),
				"StatusMessage is invalid :");

	}

	/** API Name - Get Partner Contact Person Associated with Vendor **/

	@Test(groups = { "exclude","Regression" }, priority = 0, dependsOnMethods = { "Vendor_addContactPerson" })
	public void Vendor_getPartnerContactPersonByVendorId() {
		MyntraService service = Myntra.getService(ServiceType.ERP_MARKETPLACEVMSVENDOR, APINAME.GETVENDORCONTACTPERSON,
				init.Configurations, PayloadType.JSON, new String[] { vendorId }, PayloadType.JSON);

		log.debug(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam());
		log.debug(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());

		String responseStatusCode = req.respvalidate.NodeValue("contactPersonResponse.status.statusCode", true)
				.replaceAll("\"", "").trim();
		String responseStatusType = req.respvalidate.NodeValue("contactPersonResponse.status.statusType", true)
				.replaceAll("\"", "").trim();
		String responseStatusMessage = req.respvalidate.NodeValue("contactPersonResponse.status.statusMessage", true)
				.replaceAll("\"", "").trim();

		Assert.assertEquals(Integer.parseInt(responseStatusCode), ContactSuccessCodes.CONTACT_RETRIEVED.getStatusCode(),
				"StatusCode is invalid :");
		Assert.assertEquals(responseStatusType, VmsSuccessCodes.SUCCESS.getStatusMessage().toUpperCase(),
				"StatusType is invalid :");
		Assert.assertEquals(responseStatusMessage, ContactSuccessCodes.CONTACT_RETRIEVED.getStatusMessage(),
				"StatusMessage is invalid :");
	}

	// /** API Name - Get Partner Contact Address Associated with Vendor **/
	//
	// @Test(groups = { "Regression" }, priority = 0, dependsOnMethods = {
	// "Vendor_addContactAddress" })
	// public void Vendor_getPartnerContactAddressByVendorId() {
	// MyntraService service = Myntra.getService(ServiceType.ERP_MARKETPLACEVMS,
	// APINAME.GETVENDORCONTACTADDRESS,
	// init.Configurations, PayloadType.JSON, new String[] { vendorId },
	// PayloadType.JSON);
	//
	// log.debug(service.URL);
	// RequestGenerator req = new RequestGenerator(service, getParam());
	// log.debug(req.respvalidate.returnresponseasstring());
	// AssertJUnit.assertEquals(200, req.response.getStatus());
	//
	// String responseStatusCode =
	// req.respvalidate.NodeValue("partnerContactAddressResponse.status.statusCode",
	// true)
	// .replaceAll("\"", "").trim();
	// String responseStatusType =
	// req.respvalidate.NodeValue("partnerContactAddressResponse.status.statusType",
	// true)
	// .replaceAll("\"", "").trim();
	// String responseStatusMessage =
	// req.respvalidate.NodeValue("partnerContactAddressResponse.status.statusMessage",
	// true)
	// .replaceAll("\"", "").trim();
	//
	// Assert.assertEquals(Integer.parseInt(responseStatusCode),
	// VmsSuccessCodes.PARTNER_CONTACT_ADDRESS_RETRIEVED.getStatusCode(),
	// "StatusCode is invalid :");
	// Assert.assertEquals(responseStatusType,
	// VmsSuccessCodes.SUCCESS.getStatusMessage().toUpperCase(),
	// "StatusType is invalid :");
	// Assert.assertEquals(responseStatusMessage,
	// VmsSuccessCodes.PARTNER_CONTACT_ADDRESS_RETRIEVED.getStatusMessage(),
	// "StatusMessage is invalid :");
	// }

	/** API Name - Get Address Associated with Vendor **/

	@Test(groups = { "Regression" }, priority = 0, dependsOnMethods = { "Vendor_createAddress" })
	public void Vendor_getAddressByVendorId() {
		MyntraService service = Myntra.getService(ServiceType.ERP_MARKETPLACEVMS, APINAME.GETVENDORADDRESS,
				init.Configurations, PayloadType.JSON, new String[] { vendorId }, PayloadType.JSON);

		log.debug(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam());
		log.debug(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());

		String responseStatusCode = req.respvalidate.NodeValue("partnerContactAddressResponse.status.statusCode", true)
				.replaceAll("\"", "").trim();
		String responseStatusType = req.respvalidate.NodeValue("partnerContactAddressResponse.status.statusType", true)
				.replaceAll("\"", "").trim();
		String responseStatusMessage = req.respvalidate
				.NodeValue("partnerContactAddressResponse.status.statusMessage", true).replaceAll("\"", "").trim();

		Assert.assertEquals(Integer.parseInt(responseStatusCode),
				VmsSuccessCodes.PARTNER_CONTACT_ADDRESS_RETRIEVED.getStatusCode(), "StatusCode is invalid :");
		Assert.assertEquals(responseStatusType, VmsSuccessCodes.SUCCESS.getStatusMessage().toUpperCase(),
				"StatusType is invalid :");
		Assert.assertEquals(responseStatusMessage, VmsSuccessCodes.PARTNER_CONTACT_ADDRESS_RETRIEVED.getStatusMessage(),
				"StatusMessage is invalid :");
	}

	/** API Name - Get KYC details Associated with Vendor **/

	@Test(groups = { "Regression" }, priority = 0, dependsOnMethods = { "Vendor_addKycDetails" })
	public void Vendor_getKycDetailsByVendorId() {
		MyntraService service = Myntra.getService(ServiceType.ERP_MARKETPLACEVMS, APINAME.GETKYCDETAILSBYVENDORID,
				init.Configurations, PayloadType.JSON, new String[] { vendorId }, PayloadType.JSON);

		log.debug(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam());
		log.debug(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());

		String responseStatusCode = req.respvalidate.NodeValue("partnerKYCDocumentResponse.status.statusCode", true)
				.replaceAll("\"", "").trim();
		String responseStatusType = req.respvalidate.NodeValue("partnerKYCDocumentResponse.status.statusType", true)
				.replaceAll("\"", "").trim();
		String responseStatusMessage = req.respvalidate
				.NodeValue("partnerKYCDocumentResponse.status.statusMessage", true).replaceAll("\"", "").trim();

		Assert.assertEquals(Integer.parseInt(responseStatusCode),
				VmsSuccessCodes.PARTNER_KYC_DOCUMENT_RETRIEVED.getStatusCode(), "StatusCode is invalid :");
		Assert.assertEquals(responseStatusType, VmsSuccessCodes.SUCCESS.getStatusMessage().toUpperCase(),
				"StatusType is invalid :");
		Assert.assertEquals(responseStatusMessage, VmsSuccessCodes.PARTNER_KYC_DOCUMENT_RETRIEVED.getStatusMessage(),
				"StatusMessage is invalid :");
	}

	@Test(groups = { "Regression" }, priority = 0, dependsOnMethods = {
			"Vendor_addKycDetails" }, dataProviderClass = VmsTestDP.class, dataProvider = "markVendorToReadyStatus")
	public void Vendor_markVendorToReadyStatus(String status, String poReceiversEmails, String description,
			String enabled, String readyToSplit, String shipToAllWarehouses) {
		MyntraService service = Myntra.getService(ServiceType.ERP_MARKETPLACEVMS, APINAME.APPROVEVENDOR,
				init.Configurations,
				new String[] { status, poReceiversEmails, description, enabled, readyToSplit, shipToAllWarehouses },
				new String[] { vendorId }, PayloadType.JSON, PayloadType.JSON);

		log.debug(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam());
		log.debug(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());

		String responseStatusCode = req.respvalidate.NodeValue("vendorResponse.status.statusCode", true)
				.replaceAll("\"", "").trim();
		String responseStatusType = req.respvalidate.NodeValue("vendorResponse.status.statusType", true)
				.replaceAll("\"", "").trim();
		String responseStatusMessage = req.respvalidate.NodeValue("vendorResponse.status.statusMessage", true)
				.replaceAll("\"", "").trim();

		Assert.assertEquals(Integer.parseInt(responseStatusCode), VmsSuccessCodes.VENDOR_UPDATED.getStatusCode(),
				"StatusCode is invalid :");
		Assert.assertEquals(responseStatusType, VmsSuccessCodes.SUCCESS.getStatusMessage().toUpperCase(),
				"StatusType is invalid :");
		Assert.assertEquals(responseStatusMessage, VmsSuccessCodes.VENDOR_UPDATED.getStatusMessage(),
				"StatusMessage is invalid :");
	}

	@Test(groups = { "Regression" }, priority = 0, dependsOnMethods = {
			"Vendor_markVendorToReadyStatus" }, dataProviderClass = VmsTestDP.class, dataProvider = "markVendorToApprovedStatus")
	public void Vendor_markVendorToApprovedStatus(String status, String poReceiversEmails, String description,
			String enabled, String readyToSplit, String shipToAllWarehouses) {
		MyntraService service = Myntra.getService(ServiceType.ERP_MARKETPLACEVMS, APINAME.APPROVEVENDOR,
				init.Configurations,
				new String[] { status, poReceiversEmails, description, enabled, readyToSplit, shipToAllWarehouses },
				new String[] { vendorId }, PayloadType.JSON, PayloadType.JSON);

		log.debug(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam());
		log.debug(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());

		String responseStatusCode = req.respvalidate.NodeValue("vendorResponse.status.statusCode", true)
				.replaceAll("\"", "").trim();
		String responseStatusType = req.respvalidate.NodeValue("vendorResponse.status.statusType", true)
				.replaceAll("\"", "").trim();
		String responseStatusMessage = req.respvalidate.NodeValue("vendorResponse.status.statusMessage", true)
				.replaceAll("\"", "").trim();

		Assert.assertEquals(Integer.parseInt(responseStatusCode), VmsSuccessCodes.VENDOR_UPDATED.getStatusCode(),
				"StatusCode is invalid :");
		Assert.assertEquals(responseStatusType, VmsSuccessCodes.SUCCESS.getStatusMessage().toUpperCase(),
				"StatusType is invalid :");
		Assert.assertEquals(responseStatusMessage, VmsSuccessCodes.VENDOR_UPDATED.getStatusMessage(),
				"StatusMessage is invalid :");
		
		String vmsVendorName=vmsHelper.getVendorNameFromVMS(vendorId);
		String contractsPartyName=vmsHelper.getPartyNameFromContractsDb(vendorId);
		boolean vmsContractsStatus=vmsHelper.getStatusFromVMSContracts(vendorId);	
		Assert.assertEquals(vmsVendorName, contractsPartyName,
				"Contracts Creation failed at Contracts db: Sync issues :");
		Assert.assertTrue(vmsContractsStatus,"Contracts Creation failed at VMS Contracts table: Sync issues :");
		
		
	}

	@Test(groups = { "Regression" }, priority = 0, dependsOnMethods = {
			"Vendor_addBrand" }, dataProviderClass = VmsTestDP.class, dataProvider = "disableBrandForVendor")
	public void Vendor_disableBrandForVendor(String brand) {

		String id = vmsHelper.getIdFromVendorBrand(vendorId, brand);
		MyntraService service = Myntra.getService(ServiceType.ERP_MARKETPLACEVMS, APINAME.DISABLEBRAND,
				init.Configurations, new String[] {null},new String[] { id });
		log.debug(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam());
		log.debug(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());

		String responseStatusCode = req.respvalidate.NodeValue("vendorBrandResponse.status.statusCode", true)
				.replaceAll("\"", "").trim();
		String responseStatusType = req.respvalidate.NodeValue("vendorBrandResponse.status.statusType", true)
				.replaceAll("\"", "").trim();
		String responseStatusMessage = req.respvalidate.NodeValue("vendorBrandResponse.status.statusMessage", true)
				.replaceAll("\"", "").trim();
		String brandStatus= req.respvalidate.NodeValue("vendorBrandResponse.data.vendorBrand.enabled", true)
				.replaceAll("\"", "").trim();

		Assert.assertEquals(Integer.parseInt(responseStatusCode), VmsSuccessCodes.VENDOR_BRAND_UPDATED.getStatusCode(),
				"StatusCode is invalid :");
		Assert.assertEquals(responseStatusType, VmsSuccessCodes.SUCCESS.getStatusMessage().toUpperCase(),
				"StatusType is invalid :");
		Assert.assertEquals(responseStatusMessage, VmsSuccessCodes.VENDOR_BRAND_UPDATED.getStatusMessage(),
				"StatusMessage is invalid :");
		Assert.assertEquals(brandStatus, "false",
				"Brand Status is invalid :");
	}

	/** API Name - Third Party Export **/

	@Test(groups = {
			"Regression" }, priority = 0, dataProviderClass = VmsTestDP.class, dataProvider = "thirdPartyExport", enabled=false)
	public void VMS_thirdPartyExport(String paramStatusCode, String paramStatusType, String paramStatusMessage) {
		MyntraService service = Myntra.getService(ServiceType.ERP_MARKETPLACEVMS, APINAME.THIRDPARTYEXPORT,
				init.Configurations, PayloadType.JSON,
				new String[] { paramStatusCode, paramStatusType, paramStatusMessage }, PayloadType.JSON);

		log.debug(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam());
		log.debug(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());

		String responseStatusCode = req.respvalidate.NodeValue("unicomResponse.status.statusCode", true)
				.replaceAll("\"", "").trim();
		String responseStatusType = req.respvalidate.NodeValue("unicomResponse.status.statusType", true)
				.replaceAll("\"", "").trim();
		String responseStatusMessage = req.respvalidate.NodeValue("unicomResponse.status.statusMessage", true)
				.replaceAll("\"", "").trim();

		AssertJUnit.assertTrue(
				"StatusCode is invalid, Expected: <" + paramStatusCode + "> but Actual: <" + responseStatusCode + ">",
				responseStatusCode.equals(paramStatusCode));

		AssertJUnit.assertTrue(
				"StatusType is invalid, Expected: <" + paramStatusType + "> but Actual: <" + responseStatusType + ">",
				responseStatusType.equals(paramStatusType));

		AssertJUnit.assertTrue(
				"StatusType is invalid, Expected: <" + paramStatusType + "> but Actual: <" + responseStatusType + ">",
				responseStatusMessage.equals(paramStatusMessage));

	}

	/** API Name - WareHouse Search **/

	/** API Name - Return Term Search **/

	@Test(groups = {
			"Regression" }, priority = 0, dataProviderClass = VmsTestDP.class, dataProvider = "returnTermSearch")
	public void VMS_returnTermSearch(String id, String paramStatusCode, String paramStatusType) {
		MyntraService service = Myntra.getService(ServiceType.ERP_MARKETPLACEVMS, APINAME.RETURNTERMSEARCH,
				init.Configurations, PayloadType.JSON, new String[] { id, paramStatusCode, paramStatusType },
				PayloadType.JSON);
		log.debug(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam());
		log.debug(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());

		String responseStatusCode = req.respvalidate.NodeValue("vendorReturnTermResponse.status.statusCode", true)
				.replaceAll("\"", "").trim();
		String responseStatusType = req.respvalidate.NodeValue("vendorReturnTermResponse.status.statusType", true)
				.replaceAll("\"", "").trim();
		String responseStatusMessage = req.respvalidate.NodeValue("vendorReturnTermResponse.status.statusMessage", true)
				.replaceAll("\"", "").trim();
		
		Assert.assertEquals(Integer.parseInt(responseStatusCode), VmsSuccessCodes.VENDOR_RETURN_TERM_RETRIEVED.getStatusCode(),
				"StatusCode is invalid :");
		Assert.assertEquals(responseStatusType, VmsSuccessCodes.SUCCESS.getStatusMessage().toUpperCase(),
				"StatusType is invalid :");
		Assert.assertEquals(responseStatusMessage, VmsSuccessCodes.VENDOR_RETURN_TERM_RETRIEVED.getStatusMessage(),
				"StatusMessage is invalid :");
		

	}

	/** API Name - Category Manager Search **/

	/*
	 * @Test(groups = { "Smoke","Regression" }, priority = 0
	 * ,dataProvider="categoryManagerSearch") public void
	 * VMS_categoryManagerSearch(String id ,String paramStatusCode, String
	 * paramStatusType) { MyntraService service = Myntra.getService(
	 * ServiceType.ERP_MARKETPLACEVMS, APINAME.CATEGORYMANAGERSEARCH,
	 * init.Configurations, PayloadType.JSON,new String[] { id, paramStatusCode,
	 * paramStatusType }, PayloadType.JSON);
	 * 
	 * HashMap getParam = new HashMap(); getParam.put("Authorization",
	 * "Basic YXBpYWRtaW46bTFudHJhUjBja2V0MTMhIw==");
	 * 
	 * log.debug(service.URL); RequestGenerator req = new
	 * RequestGenerator(service, getParam);
	 * log.debug(req.respvalidate.returnresponseasstring());
	 * AssertJUnit.assertEquals(200, req.response.getStatus());
	 * 
	 * String responseStatusCode = req.respvalidate
	 * .NodeValue("vendorCategoryManagerResponse.status.statusCode",
	 * true).replaceAll("\"", "") .trim(); String responseStatusType =
	 * req.respvalidate
	 * .NodeValue("vendorCategoryManagerResponse.status.statusType",
	 * true).replaceAll("\"", "") .trim();
	 * 
	 * 
	 * AssertJUnit.assertTrue( "StatusCode is invalid, Expected: <" +
	 * paramStatusCode + "> but Actual: <" + responseStatusCode + ">",
	 * responseStatusCode.equals(paramStatusCode));
	 * 
	 * 
	 * AssertJUnit.assertTrue( "StatusType is invalid, Expected: <" +
	 * paramStatusType + "> but Actual: <" + responseStatusType + ">",
	 * responseStatusType.equals(paramStatusType));
	 * 
	 * 
	 * }
	 */

	/** API Name - GetJitVendors **/

	@Test(groups = { "Smoke",
			"Regression" }, priority = 0, dataProviderClass = VmsTestDP.class, dataProvider = "findVendorbyId")
	public void VMS_getJitVendors(String id, String paramStatusCode, String paramStatusType) {
		MyntraService service = Myntra.getService(ServiceType.ERP_MARKETPLACEVMS, APINAME.GETJITVENDORS,
				init.Configurations, PayloadType.JSON, new String[] { id, paramStatusCode, paramStatusType },
				PayloadType.JSON);

		// HashMap getParam = new HashMap();
		// getParam.put("Authorization",
		// "Basic YXBpYWRtaW46bTFudHJhUjBja2V0MTMhIw==");

		log.debug(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam());
		log.debug(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());

		String responseStatusCode = req.respvalidate.NodeValue("vendorResponse.status.statusCode", true)
				.replaceAll("\"", "").trim();
		String responseStatusType = req.respvalidate.NodeValue("vendorResponse.status.statusType", true)
				.replaceAll("\"", "").trim();

		AssertJUnit.assertTrue(
				"StatusCode is invalid, Expected: <" + paramStatusCode + "> but Actual: <" + responseStatusCode + ">",
				responseStatusCode.equals(paramStatusCode));

		AssertJUnit.assertTrue(
				"StatusType is invalid, Expected: <" + paramStatusType + "> but Actual: <" + responseStatusType + ">",
				responseStatusType.equals(paramStatusType));

	}

	/** API Name - VendorWithBrandAndStyle **/

	@Test(groups = { "Regression" }, priority = 0, dataProviderClass = VmsTestDP.class, dataProvider = "findVendorbyId")
	public void Vendor_WithBrandAndStyle(String id, String paramStatusCode, String paramStatusType) {
		MyntraService service = Myntra.getService(ServiceType.ERP_MARKETPLACEVMS, APINAME.VENDORWITHBRANDANDSTYLE,
				init.Configurations, PayloadType.JSON, new String[] { id, paramStatusCode, paramStatusType },
				PayloadType.JSON);

		log.debug(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam());
		log.debug(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());

		String responseStatusCode = req.respvalidate.NodeValue("vendorResponse.status.statusCode", true)
				.replaceAll("\"", "").trim();
		String responseStatusType = req.respvalidate.NodeValue("vendorResponse.status.statusType", true)
				.replaceAll("\"", "").trim();

		AssertJUnit.assertTrue(
				"StatusCode is invalid, Expected: <" + paramStatusCode + "> but Actual: <" + responseStatusCode + ">",
				responseStatusCode.equals(paramStatusCode));

		AssertJUnit.assertTrue(
				"StatusType is invalid, Expected: <" + paramStatusType + "> but Actual: <" + responseStatusType + ">",
				responseStatusType.equals(paramStatusType));

	}

	/** API Name - get vendors terms **/

	@Test(groups = { "Regression" }, priority = 0, dataProviderClass = VmsTestDP.class, dataProvider = "getVendorTerms")
	public void VMS_getVenderTerms(String id, String paramStatusCode, String paramStatusType,
			String paramStatusMessage) {
		MyntraService service = Myntra.getService(ServiceType.ERP_MARKETPLACEVMS, APINAME.GETVENDORTERMS,
				init.Configurations, PayloadType.JSON,
				new String[] { id, paramStatusCode, paramStatusType, paramStatusMessage }, PayloadType.JSON);

		log.debug(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam());
		log.debug(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());

		String responseStatusCode = req.respvalidate.NodeValue("termsResponse.status.statusCode", true)
				.replaceAll("\"", "").trim();
		String responseStatusType = req.respvalidate.NodeValue("termsResponse.status.statusType", true)
				.replaceAll("\"", "").trim();
		String responseStatusMessage = req.respvalidate.NodeValue("termsResponse.status.statusMessage", true)
				.replaceAll("\"", "").trim();

		AssertJUnit.assertTrue(
				"StatusCode is invalid, Expected: <" + paramStatusCode + "> but Actual: <" + responseStatusCode + ">",
				responseStatusCode.equals(paramStatusCode));

		AssertJUnit.assertTrue(
				"StatusType is invalid, Expected: <" + paramStatusType + "> but Actual: <" + responseStatusType + ">",
				responseStatusType.equals(paramStatusType));

		AssertJUnit.assertTrue(
				"StatusType is invalid, Expected: <" + paramStatusType + "> but Actual: <" + responseStatusType + ">",
				responseStatusMessage.equals(paramStatusMessage));

	}

	/** API Name - get vendors terms **/

	@Test(groups = { "Regression" }, priority = 0, dataProviderClass = VmsTestDP.class, dataProvider = "getVendorTerms")
	public void Vendor_TermsMarginBasis(String id, String paramStatusCode, String paramStatusType,
			String paramStatusMessage) {
		MyntraService service = Myntra.getService(ServiceType.ERP_MARKETPLACEVMS, APINAME.GETVENDORTERMSMARGINBASIS,
				init.Configurations, PayloadType.JSON,
				new String[] { id, paramStatusCode, paramStatusType, paramStatusMessage }, PayloadType.JSON);

		log.debug(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam());
		log.debug(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());

		String responseStatusCode = req.respvalidate.NodeValue("termsResponse.status.statusCode", true)
				.replaceAll("\"", "").trim();
		String responseStatusType = req.respvalidate.NodeValue("termsResponse.status.statusType", true)
				.replaceAll("\"", "").trim();
		String responseStatusMessage = req.respvalidate.NodeValue("termsResponse.status.statusMessage", true)
				.replaceAll("\"", "").trim();

		AssertJUnit.assertTrue(
				"StatusCode is invalid, Expected: <" + paramStatusCode + "> but Actual: <" + responseStatusCode + ">",
				responseStatusCode.equals(paramStatusCode));

		AssertJUnit.assertTrue(
				"StatusType is invalid, Expected: <" + paramStatusType + "> but Actual: <" + responseStatusType + ">",
				responseStatusType.equals(paramStatusType));

		AssertJUnit.assertTrue(
				"StatusType is invalid, Expected: <" + paramStatusType + "> but Actual: <" + responseStatusType + ">",
				responseStatusMessage.equals(paramStatusMessage));
	}

	/** API Name - Add vendors Contact Negative test cases **/

	@Test(groups = {
			"Regression" }, priority = 0, dataProviderClass = VmsTestDP.class, dataProvider = "addVendorContactNegative")
	public void Vendor_addContactNegative(String entityId, String entityName, String firstName, String lastName,
			String middleName, String primaryEmail, String primaryPhoneNumber, String secondaryEmail,
			String secondaryPhoneNumber, String title, String paramStatusCode, String paramStatusType,
			String paramStatusMessage) {

		MyntraService service = Myntra.getService(ServiceType.ERP_MARKETPLACEVMSVENDOR, APINAME.ADDVENDORCONTACT,
				init.Configurations,
				new String[] { entityId, entityName, firstName, lastName, middleName, primaryEmail, primaryPhoneNumber,
						secondaryEmail, secondaryPhoneNumber, title, paramStatusCode, paramStatusType,
						paramStatusMessage },
				PayloadType.JSON, PayloadType.JSON);

		log.debug(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam());
		log.debug(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());

		String responseStatusCode = req.respvalidate.NodeValue("contactPersonResponse.status.statusCode", true)
				.replaceAll("\"", "").trim();
		String responseStatusType = req.respvalidate.NodeValue("contactPersonResponse.status.statusType", true)
				.replaceAll("\"", "").trim();
		String responseStatusMessage = req.respvalidate.NodeValue("contactPersonResponse.status.statusMessage", true)
				.replaceAll("\"", "").trim();

		AssertJUnit.assertTrue(
				"StatusCode is invalid, Expected: <" + paramStatusCode + "> but Actual: <" + responseStatusCode + ">",
				responseStatusCode.equals(paramStatusCode));

		AssertJUnit.assertTrue(
				"StatusType is invalid, Expected: <" + paramStatusType + "> but Actual: <" + responseStatusType + ">",
				responseStatusType.equals(paramStatusType));

		AssertJUnit.assertTrue(
				"StatusType is invalid, Expected: <" + paramStatusType + "> but Actual: <" + responseStatusType + ">",
				responseStatusMessage.equals(paramStatusMessage));
	}

	/** API Name - Add vendors Address Negative **/

	@Test(groups = {
			"Regression" }, priority = 0, dataProviderClass = VmsTestDP.class, dataProvider = "addVendorAddressNegative")
	public void Vendor_addAddressNegative(String address1, String address2, String city, String country,
			String entityId, String entityName, String notes, String postalCode, String primaryEmail,
			String primaryPhoneNumber, String secondaryEmail, String secondaryPhoneNumber, String state,
			String paramStatusCode, String paramStatusType, String paramStatusMessage) {
		MyntraService service = Myntra.getService(ServiceType.ERP_MARKETPLACEVMSVENDOR, APINAME.ADDVENDORADDRESS,
				init.Configurations,
				new String[] { address1, address2, city, country, entityId, entityName, notes, postalCode, primaryEmail,
						primaryPhoneNumber, secondaryEmail, secondaryPhoneNumber, paramStatusCode, state,
						paramStatusType, paramStatusMessage },
				PayloadType.JSON, PayloadType.JSON);

		log.debug(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam());
		log.debug(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());

		String responseStatusCode = req.respvalidate.NodeValue("contactAddressResponse.status.statusCode", true)
				.replaceAll("\"", "").trim();
		String responseStatusType = req.respvalidate.NodeValue("contactAddressResponse.status.statusType", true)
				.replaceAll("\"", "").trim();
		String responseStatusMessage = req.respvalidate.NodeValue("contactAddressResponse.status.statusMessage", true)
				.replaceAll("\"", "").trim();

		AssertJUnit.assertTrue(
				"StatusCode is invalid, Expected: <" + paramStatusCode + "> but Actual: <" + responseStatusCode + ">",
				responseStatusCode.equals(paramStatusCode));

		AssertJUnit.assertTrue(
				"StatusType is invalid, Expected: <" + paramStatusType + "> but Actual: <" + responseStatusType + ">",
				responseStatusType.equals(paramStatusType));

		AssertJUnit.assertTrue(
				"StatusType is invalid, Expected: <" + paramStatusType + "> but Actual: <" + responseStatusType + ">",
				responseStatusMessage.equals(paramStatusMessage));
	}

	/** API Name - Add vendors Category Manager Negative test cases **/

	@Test(groups = {
			"Regression" }, priority = 0, dataProviderClass = VmsTestDP.class, dataProvider = "addVendorCategoryManagerNegative")
	public void Vendor_addCategoryManagerNegative(String enable, String user, String vendor, String paramStatusCode,
			String paramStatusType, String paramStatusMessage) {
		MyntraService service = Myntra.getService(ServiceType.ERP_MARKETPLACEVMS, APINAME.ADDVENDORCATEGORYMANAGER,
				init.Configurations,
				new String[] { enable, user, vendor, paramStatusCode, paramStatusType, paramStatusMessage },
				PayloadType.JSON, PayloadType.JSON);

		log.debug(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam());
		log.debug(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());

		String responseStatusCode = req.respvalidate.NodeValue("vendorCategoryManagerResponse.status.statusCode", true)
				.replaceAll("\"", "").trim();
		String responseStatusType = req.respvalidate.NodeValue("vendorCategoryManagerResponse.status.statusType", true)
				.replaceAll("\"", "").trim();
		String responseStatusMessage = req.respvalidate
				.NodeValue("vendorCategoryManagerResponse.status.statusMessage", true).replaceAll("\"", "").trim();

		AssertJUnit.assertTrue(
				"StatusCode is invalid, Expected: <" + paramStatusCode + "> but Actual: <" + responseStatusCode + ">",
				responseStatusCode.equals(paramStatusCode));

		AssertJUnit.assertTrue(
				"StatusType is invalid, Expected: <" + paramStatusType + "> but Actual: <" + responseStatusType + ">",
				responseStatusType.equals(paramStatusType));

		AssertJUnit.assertTrue(
				"StatusType is invalid, Expected: <" + paramStatusType + "> but Actual: <" + responseStatusType + ">",
				responseStatusMessage.equals(paramStatusMessage));
	}

	/** API Name - Add vendors Brand Negative **/

	@Test(groups = {
			"Regression" }, priority = 0, dataProviderClass = VmsTestDP.class, dataProvider = "addVendorBrandNegative")
	public void Vendor_addBrandNegative(String brandId, String brandName, String enable, String vendor,
			String paramStatusCode, String paramStatusType, String paramStatusMessage) {
		MyntraService service = Myntra.getService(
				ServiceType.ERP_MARKETPLACEVMS, APINAME.ADDVENDORBRAND, init.Configurations, new String[] { brandId,
						brandName, enable, vendor, paramStatusCode, paramStatusType, paramStatusMessage },
				PayloadType.JSON, PayloadType.JSON);

		log.debug(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam());
		log.debug(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());

		String responseStatusCode = req.respvalidate.NodeValue("vendorBrandResponse.status.statusCode", true)
				.replaceAll("\"", "").trim();
		String responseStatusType = req.respvalidate.NodeValue("vendorBrandResponse.status.statusType", true)
				.replaceAll("\"", "").trim();
		String responseStatusMessage = req.respvalidate.NodeValue("vendorBrandResponse.status.statusMessage", true)
				.replaceAll("\"", "").trim();

		AssertJUnit.assertTrue(
				"StatusCode is invalid, Expected: <" + paramStatusCode + "> but Actual: <" + responseStatusCode + ">",
				responseStatusCode.equals(paramStatusCode));

		AssertJUnit.assertTrue(
				"StatusType is invalid, Expected: <" + paramStatusType + "> but Actual: <" + responseStatusType + ">",
				responseStatusType.equals(paramStatusType));

		AssertJUnit.assertTrue(
				"StatusType is invalid, Expected: <" + paramStatusType + "> but Actual: <" + responseStatusType + ">",
				responseStatusMessage.equals(paramStatusMessage));
	}

	/** API Name - Add vendors Warehouse Negative test cases **/

	@Test(groups = {
			"Regression" }, priority = 0, dataProviderClass = VmsTestDP.class, dataProvider = "addVendorWarehouseNegative")
	public void Vendor_addWarehouseNegative(String leadTime, String supplyType, String taxType, String vendor,
			String warehouse, String paramStatusCode, String paramStatusType, String paramStatusMessage) {
		MyntraService service = Myntra.getService(ServiceType.ERP_MARKETPLACEVMS,
				APINAME.ADDVENDORWAREHOUSE, init.Configurations, new String[] { leadTime, supplyType, taxType, vendor,
						warehouse, paramStatusCode, paramStatusType, paramStatusMessage },
				PayloadType.JSON, PayloadType.JSON);

		log.debug(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam());
		log.debug(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());

		String responseStatusCode = req.respvalidate.NodeValue("vendorWarehouseResponse.status.statusCode", true)
				.replaceAll("\"", "").trim();
		String responseStatusType = req.respvalidate.NodeValue("vendorWarehouseResponse.status.statusType", true)
				.replaceAll("\"", "").trim();
		String responseStatusMessage = req.respvalidate.NodeValue("vendorWarehouseResponse.status.statusMessage", true)
				.replaceAll("\"", "").trim();

		AssertJUnit.assertTrue(
				"StatusCode is invalid, Expected: <" + paramStatusCode + "> but Actual: <" + responseStatusCode + ">",
				responseStatusCode.equals(paramStatusCode));

		AssertJUnit.assertTrue(
				"StatusType is invalid, Expected: <" + paramStatusType + "> but Actual: <" + responseStatusType + ">",
				responseStatusType.equals(paramStatusType));

		AssertJUnit.assertTrue(
				"StatusType is invalid, Expected: <" + paramStatusType + "> but Actual: <" + responseStatusType + ">",
				responseStatusMessage.equals(paramStatusMessage));
	}

	/** API Name - Add vendors Warehouse Negative test cases **/

	@Test(groups = { "Regression" }, priority = 0, dependsOnMethods = { "Vendor_addWarehouse" })
	public void Vendor_disableWarehouse() {
		MyntraService service = Myntra.getService(ServiceType.ERP_MARKETPLACEVMS, APINAME.DISABLEWAREHOUSE,
				init.Configurations, new String[] {null},new String[] { vendorWarehouseId });
		
		log.debug(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam());
		log.debug(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());

		String responseStatusCode = req.respvalidate.NodeValue("vendorWarehouseResponse.status.statusCode", true)
				.replaceAll("\"", "").trim();
		String responseStatusType = req.respvalidate.NodeValue("vendorWarehouseResponse.status.statusType", true)
				.replaceAll("\"", "").trim();
		String responseStatusMessage = req.respvalidate.NodeValue("vendorWarehouseResponse.status.statusMessage", true)
				.replaceAll("\"", "").trim();
		String warehouseIsEnabled= req.respvalidate
				.NodeValue("vendorWarehouseResponse.data.vendorWarehouse.enabled", true).replaceAll("\"", "").trim();


		Assert.assertEquals(Integer.parseInt(responseStatusCode),
				VmsSuccessCodes.VENDOR_WAREHOUSE_UPDATED.getStatusCode(), "StatusCode is invalid :");
		Assert.assertEquals(responseStatusType, VmsSuccessCodes.SUCCESS.getStatusMessage().toUpperCase(),
				"StatusType is invalid :");
		Assert.assertEquals(responseStatusMessage, VmsSuccessCodes.VENDOR_WAREHOUSE_UPDATED.getStatusMessage(),
				"StatusMessage is invalid :");
		Assert.assertEquals(warehouseIsEnabled, "false",
				"CM Status is invalid :");

	}
	
	/** API Name - Add vendors Category Manager **/

	@Test(groups = { "Regression" }, priority = 0, enabled=false, dependsOnMethods = {
			"Vendor_addCategoryManager" })
	public void Vendor_disableCategoryManager() {
		MyntraService service = Myntra.getService(ServiceType.ERP_MARKETPLACEVMS, APINAME.DISABLEVENDORCATEGORYMANAGER,
				init.Configurations, new String[] {null}, new String[] {vendorCategoryManagerId });

		log.debug(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam());
		log.debug(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());

		String responseStatusCode = req.respvalidate.NodeValue("vendorCategoryManagerResponse.status.statusCode", true)
				.replaceAll("\"", "").trim();
		String responseStatusType = req.respvalidate.NodeValue("vendorCategoryManagerResponse.status.statusType", true)
				.replaceAll("\"", "").trim();
		String responseStatusMessage = req.respvalidate
				.NodeValue("vendorCategoryManagerResponse.status.statusMessage", true).replaceAll("\"", "").trim();
		String cmIsEnabled= req.respvalidate
				.NodeValue("vendorCategoryManagerResponse.data.vendorCategoryManager.enabled", true).replaceAll("\"", "").trim();

		Assert.assertEquals(Integer.parseInt(responseStatusCode),
				VmsSuccessCodes.VENDOR_CATEGORY_MANAGER_UPDATED.getStatusCode(), "StatusCode is invalid :");
		Assert.assertEquals(responseStatusType, VmsSuccessCodes.SUCCESS.getStatusMessage().toUpperCase(),
				"StatusType is invalid :");
		Assert.assertEquals(responseStatusMessage, VmsSuccessCodes.VENDOR_CATEGORY_MANAGER_UPDATED.getStatusMessage(),
				"StatusMessage is invalid :");
		Assert.assertEquals(cmIsEnabled, "false",
				"CM Status is invalid :");

	}
	
	
	/** API Name - gets PartyId from Source System mapping table by SourceSystem Id 
	 * @throws Exception **/

	@Test(groups = { "Smoke", "Regression" }, priority = 0,dataProviderClass=VmsTestDP.class, dataProvider="getPartyIdBySourceSystemId")
	public void VMS_getPartyIdBySourceSystemId(String sourceSystemId) throws Exception {
		MyntraService service = Myntra.getService(ServiceType.ERP_MARKETPLACEVMS, APINAME.GETPARTYIDBYSOURCESYSTEMID,
				init.Configurations, PayloadType.JSON, new String[] { sourceSystemId }, PayloadType.JSON);
		log.debug(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam());
		log.debug(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());

		String responseStatusCode = req.respvalidate.NodeValue("vendorResponse.status.statusCode", true)
				.replaceAll("\"", "").trim();
		String responseStatusType = req.respvalidate.NodeValue("vendorResponse.status.statusType", true)
				.replaceAll("\"", "").trim();
		String responseStatusMessage = req.respvalidate.NodeValue("vendorResponse.status.statusMessage", true)
				.replaceAll("\"", "").trim();
		
		String partner_id = req.respvalidate.NodeValue("vendorResponse.data.vendor.id", true)
				.replaceAll("\"", "").trim();

		Assert.assertEquals(Integer.parseInt(responseStatusCode),
				VmsSuccessCodes.SUCCESS.getStatusCode(), "StatusCode is invalid :");
		Assert.assertEquals(responseStatusType, VmsSuccessCodes.SUCCESS.getStatusMessage().toUpperCase(),
				"StatusType is invalid :");
		Assert.assertEquals(responseStatusMessage, VmsSuccessCodes.SUCCESS.getStatusMessage(),
				"StatusMessage is invalid :");
		Assert.assertEquals(partner_id, vmsHelper.getPartnerFromSourceSystem(sourceSystemId),
				"Partner Id Mismatch:");

	}
	
	/** API Name - gets PartyId from Source System mapping table by SourceSystem Id 
	 * @throws Exception **/

	@Test(groups = { "Smoke", "Regression" }, priority = 0,dataProviderClass=VmsTestDP.class, dataProvider="getPartyIdByVendorId")
	public void VMS_getPartyIdByVendorId(String vendorId) throws Exception {
		MyntraService service = Myntra.getService(ServiceType.ERP_MARKETPLACEVMS, APINAME.GETPARTYIDBYVENDORID,
				init.Configurations, PayloadType.JSON, new String[] { vendorId }, PayloadType.JSON);
		log.debug(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam());
		log.debug(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());

		String responseStatusCode = req.respvalidate.NodeValue("vendorResponse.status.statusCode", true)
				.replaceAll("\"", "").trim();
		String responseStatusType = req.respvalidate.NodeValue("vendorResponse.status.statusType", true)
				.replaceAll("\"", "").trim();
		String responseStatusMessage = req.respvalidate.NodeValue("vendorResponse.status.statusMessage", true)
				.replaceAll("\"", "").trim();
		
		String partner_id = req.respvalidate.NodeValue("vendorResponse.data.vendor.id", true)
				.replaceAll("\"", "").trim();
		String partner_name = req.respvalidate.NodeValue("vendorResponse.data.vendor.name", true)
				.replaceAll("\"", "").trim();

		Assert.assertEquals(Integer.parseInt(responseStatusCode),
				VmsSuccessCodes.SUCCESS.getStatusCode(), "StatusCode is invalid :");
		Assert.assertEquals(responseStatusType, VmsSuccessCodes.SUCCESS.getStatusMessage().toUpperCase(),
				"StatusType is invalid :");
		Assert.assertEquals(responseStatusMessage, VmsSuccessCodes.SUCCESS.getStatusMessage(),
				"StatusMessage is invalid :");
		Assert.assertEquals(partner_id, "1",
				"Partner Id Mismatch:");
		Assert.assertEquals(partner_name, "OPEN STOCK",
				"Partner Name Mismatch:");

	}
	
	/** API Name - get All Contracts where Seller=2 
	 * @throws Exception **/

	@Test(groups = { "Smoke", "Regression" }, priority = 0,dataProviderClass=VmsTestDP.class, dataProvider="getContractsBySeller")
	public void VMS_getContractsBySeller(String vendorId) throws Exception {
		MyntraService service = Myntra.getService(ServiceType.ERP_MARKETPLACEVMS, APINAME.GETCONTRACTSBYSELLER,
				init.Configurations, PayloadType.JSON, new String[] { vendorId }, PayloadType.JSON);
		log.debug(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam());
		log.debug(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());

		String responseStatusCode = req.respvalidate.NodeValue("contractResponse.status.statusCode", true)
				.replaceAll("\"", "").trim();
		String responseStatusType = req.respvalidate.NodeValue("contractResponse.status.statusType", true)
				.replaceAll("\"", "").trim();
		String responseStatusMessage = req.respvalidate.NodeValue("contractResponse.status.statusMessage", true)
				.replaceAll("\"", "").trim();
		
		String partner_id = req.respvalidate.NodeValue("contractResponse.data.contractEntry.partner1.id", true)
				.replaceAll("\"", "").trim();
		String partner_name = req.respvalidate.NodeValue("contractResponse.data.contractEntry.partner1.name", true)
				.replaceAll("\"", "").trim();

		Assert.assertEquals(Integer.parseInt(responseStatusCode),
				VmsSuccessCodes.SUCCESS.getStatusCode(), "StatusCode is invalid :");
		Assert.assertEquals(responseStatusType, VmsSuccessCodes.SUCCESS.getStatusMessage().toUpperCase(),
				"StatusType is invalid :");
		Assert.assertEquals(responseStatusMessage, VmsSuccessCodes.SUCCESS.getStatusMessage(),
				"StatusMessage is invalid :");
		Assert.assertEquals(partner_id, "3974",
				"Partner Id Mismatch:");
		Assert.assertEquals(partner_name, "Myntra Jabong India Pvt Ltd",
				"Partner Name Mismatch:");

	}
	
	/** API Name - get All Contracts where Buyer=2 
	 * @throws Exception **/

	@Test(groups = { "Smoke", "Regression" }, priority = 0,dataProviderClass=VmsTestDP.class, dataProvider="getContractsByBuyer")
	public void VMS_getContractsByBuyer(String vendorId) throws Exception {
		MyntraService service = Myntra.getService(ServiceType.ERP_MARKETPLACEVMS, APINAME.GETCONTRACTSBYBUYER,
				init.Configurations, PayloadType.JSON, new String[] { vendorId }, PayloadType.JSON);
		log.debug(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam());
		log.debug(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());

		String responseStatusCode = req.respvalidate.NodeValue("contractResponse.status.statusCode", true)
				.replaceAll("\"", "").trim();
		String responseStatusType = req.respvalidate.NodeValue("contractResponse.status.statusType", true)
				.replaceAll("\"", "").trim();
		String responseStatusMessage = req.respvalidate.NodeValue("contractResponse.status.statusMessage", true)
				.replaceAll("\"", "").trim();
		
		String partner_id = req.respvalidate.NodeValue("contractResponse.data.contractEntry.partner1.id", true)
				.replaceAll("\"", "").trim();
		String partner_name = req.respvalidate.NodeValue("contractResponse.data.contractEntry.partner1.name", true)
				.replaceAll("\"", "").trim();

		Assert.assertEquals(Integer.parseInt(responseStatusCode),
				VmsSuccessCodes.SUCCESS.getStatusCode(), "StatusCode is invalid :");
		Assert.assertEquals(responseStatusType, VmsSuccessCodes.SUCCESS.getStatusMessage().toUpperCase(),
				"StatusType is invalid :");
		Assert.assertEquals(responseStatusMessage, VmsSuccessCodes.SUCCESS.getStatusMessage(),
				"StatusMessage is invalid :");
		Assert.assertEquals(partner_id, vmsHelper.getPartner2Id(),
				"Partner Id Mismatch:");
		//Assert.assertEquals(partner_name, "Puma Sports India Pvt. Ltd.",
		//		"Partner Name Mismatch:");

	}
	
	/** API Name - get All Buyers/Vendors/Partners where Seller=2 
	 * @throws Exception **/

	@Test(groups = { "Smoke", "Regression" }, priority = 0,dataProviderClass=VmsTestDP.class, dataProvider="getAllBuyersBySeller")
	public void VMS_getAllBuyersBySeller(String vendorId) throws Exception {
		MyntraService service = Myntra.getService(ServiceType.ERP_MARKETPLACEVMS, APINAME.GETALLBUYERSBYSELLER,
				init.Configurations, PayloadType.JSON, new String[] { vendorId }, PayloadType.JSON);
		log.debug(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam());
		log.debug(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());

		String responseStatusCode = req.respvalidate.NodeValue("vendorResponse.status.statusCode", true)
				.replaceAll("\"", "").trim();
		String responseStatusType = req.respvalidate.NodeValue("vendorResponse.status.statusType", true)
				.replaceAll("\"", "").trim();
		String responseStatusMessage = req.respvalidate.NodeValue("vendorResponse.status.statusMessage", true)
				.replaceAll("\"", "").trim();
		
		String partner_id = req.respvalidate.NodeValue("vendorResponse.data.vendor.id", true)
				.replaceAll("\"", "").trim();
		String partner_name = req.respvalidate.NodeValue("vendorResponse.data.vendor.name", true)
				.replaceAll("\"", "").trim();

		Assert.assertEquals(Integer.parseInt(responseStatusCode),
				VmsSuccessCodes.SUCCESS.getStatusCode(), "StatusCode is invalid :");
		Assert.assertEquals(responseStatusType, VmsSuccessCodes.SUCCESS.getStatusMessage().toUpperCase(),
				"StatusType is invalid :");
		Assert.assertEquals(responseStatusMessage, VmsSuccessCodes.SUCCESS.getStatusMessage(),
				"StatusMessage is invalid :");
		Assert.assertEquals(partner_id, "3974",
				"Partner Id Mismatch:");
		Assert.assertEquals(partner_name, "Myntra Jabong India Pvt Ltd",
				"Partner Name Mismatch:");

	}
	
	
	/** API Name - get All Sellers where Buyer=2297  
	 * @throws Exception **/

	@Test(groups = { "Smoke", "Regression" }, priority = 0,dataProviderClass=VmsTestDP.class, dataProvider="getAllSellersByBuyer")
	public void VMS_getAllSellersByBuyer(String vendorId) throws Exception {
		MyntraService service = Myntra.getService(ServiceType.ERP_MARKETPLACEVMS, APINAME.GETALLSELLERSBYBUYER,
				init.Configurations, PayloadType.JSON, new String[] { vendorId }, PayloadType.JSON);
		log.debug(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam());
		log.debug(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());

		String responseStatusCode = req.respvalidate.NodeValue("vendorResponse.status.statusCode", true)
				.replaceAll("\"", "").trim();
		String responseStatusType = req.respvalidate.NodeValue("vendorResponse.status.statusType", true)
				.replaceAll("\"", "").trim();
		String responseStatusMessage = req.respvalidate.NodeValue("vendorResponse.status.statusMessage", true)
				.replaceAll("\"", "").trim();
		
		String partner_id = req.respvalidate.NodeValue("vendorResponse.data.vendor.id", true)
				.replaceAll("\"", "").trim();
		String partner_name = req.respvalidate.NodeValue("vendorResponse.data.vendor.name", true)
				.replaceAll("\"", "").trim();

		Assert.assertEquals(Integer.parseInt(responseStatusCode),
				VmsSuccessCodes.SUCCESS.getStatusCode(), "StatusCode is invalid :");
		Assert.assertEquals(responseStatusType, VmsSuccessCodes.SUCCESS.getStatusMessage().toUpperCase(),
				"StatusType is invalid :");
		Assert.assertEquals(responseStatusMessage, VmsSuccessCodes.SUCCESS.getStatusMessage(),
				"StatusMessage is invalid :");
		Assert.assertEquals(partner_id, String.valueOf(vmsHelper.getBuyerId()),
				"Partner Id Mismatch:");
		Assert.assertEquals(partner_name, "Myntra Jabong India Pvt Ltd",
				"Partner Name Mismatch:");

	}
	
	
	/** API Name - get All Stores where Seller=2297  
	 * @throws Exception **/

	@Test(groups = { "Smoke", "Regression" }, priority = 0,dataProviderClass=VmsTestDP.class, dataProvider="getAllStoresBySeller")
	public void VMS_getAllStoresBySeller(String vendorId) throws Exception {
		MyntraService service = Myntra.getService(ServiceType.ERP_MARKETPLACEVMS, APINAME.GETALLSTORESBYSELLER,
				init.Configurations, PayloadType.JSON, new String[] { vendorId }, PayloadType.JSON);
		log.debug(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam());
		log.debug(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());

		String responseStatusCode = req.respvalidate.NodeValue("vendorResponse.status.statusCode", true)
				.replaceAll("\"", "").trim();
		String responseStatusType = req.respvalidate.NodeValue("vendorResponse.status.statusType", true)
				.replaceAll("\"", "").trim();
		String responseStatusMessage = req.respvalidate.NodeValue("vendorResponse.status.statusMessage", true)
				.replaceAll("\"", "").trim();
		
		String partner_id = req.respvalidate.NodeValue("vendorResponse.data.vendor.id", true)
				.replaceAll("\"", "").trim();
		String partner_name = req.respvalidate.NodeValue("vendorResponse.data.vendor.name", true)
				.replaceAll("\"", "").trim();

		Assert.assertEquals(Integer.parseInt(responseStatusCode),
				VmsSuccessCodes.SUCCESS.getStatusCode(), "StatusCode is invalid :");
		Assert.assertEquals(responseStatusType, VmsSuccessCodes.SUCCESS.getStatusMessage().toUpperCase(),
				"StatusType is invalid :");
		Assert.assertEquals(responseStatusMessage, VmsSuccessCodes.SUCCESS.getStatusMessage(),
				"StatusMessage is invalid :");
		Assert.assertEquals(partner_id, vmsHelper.getStoreId(),
				"Partner Id Mismatch:");
		Assert.assertEquals(partner_name, "MYNTRA DESIGNS PVT LTD",
				"Partner Name Mismatch:");

	}
	
	/** API Name - get All sellers where store=2297  
	 * @throws Exception **/

	@Test(groups = { "exclude","Smoke", "Regression" }, priority = 0,dataProviderClass=VmsTestDP.class, dataProvider="getAllSellersByStore")
	public void VMS_getAllSellersByStore(String vendorId) throws Exception {
		MyntraService service = Myntra.getService(ServiceType.ERP_MARKETPLACEVMS, APINAME.GETALLSELLERSBYSTORES,
				init.Configurations, PayloadType.JSON, new String[] { vendorId }, PayloadType.JSON);
		log.debug(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam());
		log.debug(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());

		String responseStatusCode = req.respvalidate.NodeValue("vendorResponse.status.statusCode", true)
				.replaceAll("\"", "").trim();
		String responseStatusType = req.respvalidate.NodeValue("vendorResponse.status.statusType", true)
				.replaceAll("\"", "").trim();
		String responseStatusMessage = req.respvalidate.NodeValue("vendorResponse.status.statusMessage", true)
				.replaceAll("\"", "").trim();
		
		String partner_id = req.respvalidate.NodeValue("vendorResponse.data.vendor.id", true)
				.replaceAll("\"", "").trim();
		String partner_name = req.respvalidate.NodeValue("vendorResponse.data.vendor.name", true)
				.replaceAll("\"", "").trim();

		Assert.assertEquals(Integer.parseInt(responseStatusCode),
				VmsSuccessCodes.SUCCESS.getStatusCode(), "StatusCode is invalid :");
		Assert.assertEquals(responseStatusType, VmsSuccessCodes.SUCCESS.getStatusMessage().toUpperCase(),
				"StatusType is invalid :");
		Assert.assertEquals(responseStatusMessage, VmsSuccessCodes.SUCCESS.getStatusMessage(),
				"StatusMessage is invalid :");
		Assert.assertEquals(partner_id, "3",
				"Partner Id Mismatch:");
		Assert.assertEquals(partner_name, "VF Brands India Pvt Ltd",
				"Partner Name Mismatch:");

	}
	
	/** API Name - get Terms where Seller=1 and Buyer=4 
	 * @throws Exception **/

	@Test(groups = { "exclude","Smoke", "Regression" }, priority = 0,dataProviderClass=VmsTestDP.class, dataProvider="getTermsBySellerAndBuyer")
	public void VMS_getTermsBySellerAndBuyer(String seller, String buyer) throws Exception {
		MyntraService service = Myntra.getService(ServiceType.ERP_MARKETPLACEVMS, APINAME.GETTERMSBYSELLERANDBUYER,
				init.Configurations, PayloadType.JSON, new String[] { seller, buyer }, PayloadType.JSON);
		log.debug(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam());
		log.debug(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());

		String responseStatusCode = req.respvalidate.NodeValue("vendorResponse.status.statusCode", true)
				.replaceAll("\"", "").trim();
		String responseStatusType = req.respvalidate.NodeValue("vendorResponse.status.statusType", true)
				.replaceAll("\"", "").trim();
		String responseStatusMessage = req.respvalidate.NodeValue("vendorResponse.status.statusMessage", true)
				.replaceAll("\"", "").trim();
		
		String termStatus = req.respvalidate.NodeValue("vendorTermsResponse.vendorTermsPoData.termStatus", true)
				.replaceAll("\"", "").trim();
		String termType = req.respvalidate.NodeValue("vendorTermsResponse.vendorTermsPoData.termType", true)
				.replaceAll("\"", "").trim();

		Assert.assertEquals(Integer.parseInt(responseStatusCode),
				VmsSuccessCodes.SUCCESS.getStatusCode(), "StatusCode is invalid :");
		Assert.assertEquals(responseStatusType, VmsSuccessCodes.SUCCESS.getStatusMessage().toUpperCase(),
				"StatusType is invalid :");
		Assert.assertEquals(responseStatusMessage, VmsSuccessCodes.SUCCESS.getStatusMessage(),
				"StatusMessage is invalid :");
		Assert.assertEquals(termStatus, "ACTIVE",
				"Term status:");
		Assert.assertEquals(termType, "PO",
				"TermType:");

	}
	
	/** API Name - get Terms where Buyer=4  and Seller=1
	 * @throws Exception **/

	@Test(groups = { "Smoke", "Regression" }, priority = 0,dataProviderClass=VmsTestDP.class, dataProvider="getTermsByBuyerAndSeller")
	public void VMS_getTermsByBuyerAndSeller(String seller, String buyer) throws Exception {
		MyntraService service = Myntra.getService(ServiceType.ERP_MARKETPLACEVMS, APINAME.GETTERMSBYBUYERANDSELLER,
				init.Configurations, PayloadType.JSON, new String[] { seller, buyer }, PayloadType.JSON);
		log.debug(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam());
		log.debug(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());

		String responseStatusCode = req.respvalidate.NodeValue("vendorTermsResponse.status.statusCode", true)
				.replaceAll("\"", "").trim();
		String responseStatusType = req.respvalidate.NodeValue("vendorTermsResponse.status.statusType", true)
				.replaceAll("\"", "").trim();
		String responseStatusMessage = req.respvalidate.NodeValue("vendorTermsResponse.status.statusMessage", true)
				.replaceAll("\"", "").trim();
		
		String termStatus = req.respvalidate.NodeValue("vendorTermsResponse.vendorTermsPoData.termStatus", true)
				.replaceAll("\"", "").trim();
		String termType = req.respvalidate.NodeValue("vendorTermsResponse.vendorTermsPoData.termType", true)
				.replaceAll("\"", "").trim();

		Assert.assertEquals(Integer.parseInt(responseStatusCode),
				VmsSuccessCodes.SUCCESS.getStatusCode(), "StatusCode is invalid :");
		Assert.assertEquals(responseStatusType, VmsSuccessCodes.SUCCESS.getStatusMessage().toUpperCase(),
				"StatusType is invalid :");
		Assert.assertEquals(responseStatusMessage, VmsSuccessCodes.SUCCESS.getStatusMessage(),
				"StatusMessage is invalid :");
		//Assert.assertEquals(termStatus, "ACTIVE",
		//		"Term status:");
		Assert.assertEquals(termType, "PO",
				"TermType:");

	}
	
	@AfterClass(alwaysRun=true)
	public void tearDown() throws Exception {
		if(vendorId!=null) {
			vmsHelper.deleteFromCategoryManager(vendorId);
			vmsHelper.deleteFromBrand(vendorId);
			vmsHelper.deleteFromVendorWarehouse(vendorId);
			vmsHelper.deleteFromVendor(vendorId);
		}
	}

}
