package com.myntra.apiTests.erpservices.partners.Tests;

import com.myntra.apiTests.erpservices.partners.SellerServicesHelper;
import com.myntra.apiTests.erpservices.partners.VMSHelper;
import com.myntra.apiTests.erpservices.partners.VendorServiceHelper;
import com.myntra.lordoftherings.Initialize;
import com.myntra.sellers.entry.SellerEntry;
import com.myntra.sellers.response.SellerResponse;
import com.myntra.test.commons.service.Svc;
import com.myntra.test.commons.testbase.BaseTest;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.net.URISyntaxException;

/**
 * 
 * @author sandal.iqbal
 *
 */
public class SellerConfigurationTest extends BaseTest {

	static Logger log = LoggerFactory.getLogger(TermsTest.class);
	public static Initialize init = new Initialize("Data/configuration");
	VendorServiceHelper vendorHelper;
	VMSHelper vmsHelper = new VMSHelper();
	SellerServicesHelper sellerHelper = new SellerServicesHelper();
	String sellerid;

	@BeforeClass(alwaysRun = true)
	public void beforeClass() {
		sellerid = String.valueOf(vmsHelper.getExistingSeller());
		Reporter.log("Using sellerId: " + sellerid);
	}

	@Test(enabled = true, groups = { "sanity", "Regression",
			"sellerconfig" }, description = "retrieves all keys from vms configuration master")
	public void getKeysFromConfigurationMaster(ITestContext testContext) throws URISyntaxException, JSONException {
		vendorHelper = new VendorServiceHelper(testContext);
		String query = "entityType.eq:CONTRACT";
		Svc response = vendorHelper.getKeysFromConfigMaster(query);
		vendorHelper.validateKeyValue(response, "entityType", "CONTRACT");
		query = "entityType.eq:CONTRACT___category.eq:ORDER_MANAGEMENT";
		response = vendorHelper.getKeysFromConfigMaster(query);
		vendorHelper.validateKeyValue(response, "category", "ORDER_MANAGEMENT");
		query = "entityType.eq:CONTRACT___key.eq:COUPONS_ALLOWED";
		response = vendorHelper.getKeysFromConfigMaster(query);
		vendorHelper.validateKeyValue(response, "key", "COUPONS_ALLOWED");
		query = "entityType.eq:CONTRACT___value.eq:true";
		response = vendorHelper.getKeysFromConfigMaster(query);
		vendorHelper.validateKeyValue(response, "value", "true");
		query = "entityType.eq:CONTRACT___valueType.eq:STRING";
		response = vendorHelper.getKeysFromConfigMaster(query);
		vendorHelper.validateKeyValue(response, "valueType", "STRING");
	}

	@Test(enabled = true, groups = { "sanity", "Regression", "sellerconfig" }, description = "searches seller by id")
	public void searchSellerById(ITestContext testContext) throws JAXBException, IOException, JSONException {
		vendorHelper = new VendorServiceHelper(testContext);
		sellerHelper.getSellerById(sellerid);
		String query = "configurationCategory=ORDER_MANAGEMENT";
		Svc response = sellerHelper.getSellerById(sellerid, query);
		vendorHelper.validateKeyValue(response, "configurationCategory", "ORDER_MANAGEMENT");
	}

	@Test(enabled = true, groups = { "sanity", "Regression", "sellerconfig" }, description = "searches seller by id")
	public void searchSellerByIdWithoutSeller(ITestContext testContext) throws JAXBException, IOException, JSONException {
		vendorHelper = new VendorServiceHelper(testContext);
		sellerHelper.getSellerById(null, null);
		String query = "configurationCategory=ORDER_MANAGEMENT";
		Svc response = sellerHelper.getSellerById(null, query);
		vendorHelper.validateKeyValue(response, "configurationCategory", "ORDER_MANAGEMENT");
	}

	@Test(enabled = true, groups = { "sanity", "Regression",
			"sellerconfig" }, description = "searches seller configuration in vms")
	public void searchSellerConfiguration(ITestContext testContext) throws JAXBException, IOException, JSONException {
		vendorHelper = new VendorServiceHelper(testContext);
		sellerHelper.getSellerConfiguration(sellerid);
		String query = "configurationCategory.eq:ORDER_MANAGEMENT";
		Svc response = sellerHelper.getSellerConfiguration(sellerid, query);
		vendorHelper.validateKeyValue(response, "configurationCategory", "ORDER_MANAGEMENT");
	}

	@Test(enabled = true, groups = { "sanity", "Regression",
			"sellerconfig" }, description = "searches seller configuration in vms")
	public void searchSellerConfigurationWithoutSeller(ITestContext testContext) throws JAXBException, IOException, JSONException {
		vendorHelper = new VendorServiceHelper(testContext);
		sellerHelper.getSellerConfiguration(null, null);
		String query = "configurationCategory.eq:ORDER_MANAGEMENT";
		Svc response = sellerHelper.getSellerConfiguration(null, query);
		vendorHelper.validateKeyValue(response, "configurationCategory", "ORDER_MANAGEMENT");
		query = "configurationCategory.eq:ORDER_MANAGEMENT___configurationKey.eq:QC_REQUIRED";
		response = sellerHelper.getSellerConfiguration(null, query);
		vendorHelper.validateKeyValue(response, "configurationCategory", "ORDER_MANAGEMENT");
		vendorHelper.validateConfigurationAttributes(response, "configurationKey", "QC_REQUIRED");
	}

	@Test(enabled = true, groups = { "sanity", "Regression",
			"sellerconfig" }, description = "adds seller configuration in vms and seller")
	public void addSellerConfigurations(ITestContext testContext) throws Exception {
		vendorHelper = new VendorServiceHelper(testContext);
		SellerResponse response = sellerHelper.createSeller("Test Seller", "test", false);
		SellerEntry seller = response.getData().get(0);
		Long sellerId = seller.getId();
		int vendorid = vendorHelper.createVendor();
		String storeid = vmsHelper.getStoreId();
		int contractId = vendorHelper.createContract(storeid, vendorid, 2);
		try {
			vmsHelper.insertIntoSourceSystemPartnerMapping(sellerId, Long.valueOf(vendorid));
			sellerHelper.addSellerConfigurations(String.valueOf(sellerId));
			sellerHelper.getSellerConfiguration(String.valueOf(sellerId));
		} finally {
			vmsHelper.deleteConfigurations(String.valueOf(sellerId), String.valueOf(contractId));
			vmsHelper.deleteFromSourceSystemPartnerMapping(String.valueOf(sellerId));
			vmsHelper.deleteContract(String.valueOf(contractId));
			vmsHelper.deleteFromVendor(String.valueOf(vendorid));
			vmsHelper.deleteFromSeller(String.valueOf(sellerId));
		}
	}

	@Test(enabled = true, groups = { "sanity", "Regression",
			"sellerconfig" }, description = "add seller configuration in vms")
	public void createSellerConfiguration(ITestContext testContext) throws Exception {
		vendorHelper = new VendorServiceHelper(testContext);
		SellerResponse response = sellerHelper.createSeller("Test Seller", "test", false);
		SellerEntry seller = response.getData().get(0);
		Long sellerId = seller.getId();
		int vendorid = vendorHelper.createVendor();
		String storeid = vmsHelper.getStoreId();
		int contractId = vendorHelper.createContract(storeid, vendorid, 2);
		try {
			vmsHelper.insertIntoSourceSystemPartnerMapping(sellerId, Long.valueOf(vendorid));
			vendorHelper.createSellerConfiguration(String.valueOf(contractId), "COUPONS_ALLOWED", "true");
			String query = "configurationCategory=COUPONS";
			Svc service = sellerHelper.getSellerById(String.valueOf(sellerId), query);
			vendorHelper.validateConfigurationAttributes(service, "configurationKey", "COUPONS_ALLOWED");
			vendorHelper.validateConfigurationAttributes(service, "configurationValue", "true");
		} finally {
			vmsHelper.deleteConfigurations(String.valueOf(sellerId), String.valueOf(contractId));
			vmsHelper.deleteFromSourceSystemPartnerMapping(String.valueOf(sellerId));
			vmsHelper.deleteContract(String.valueOf(contractId));
			vmsHelper.deleteFromVendor(String.valueOf(vendorid));
			vmsHelper.deleteFromSeller(String.valueOf(sellerId));
		}
	}

	@Test(enabled = true, groups = { "sanity", "Regression",
			"sellerconfig" }, description = "update seller configuration in vms")
	public void updateSellerConfiguration(ITestContext testContext) throws Exception {
		vendorHelper = new VendorServiceHelper(testContext);
		SellerResponse response = sellerHelper.createSeller("Test Seller", "test", false);
		SellerEntry seller = response.getData().get(0);
		Long sellerId = seller.getId();
		int vendorid = vendorHelper.createVendor();
		String storeid = vmsHelper.getStoreId();
		int contractId = vendorHelper.createContract(storeid, vendorid, 2);
		try {
			vmsHelper.insertIntoSourceSystemPartnerMapping(sellerId, Long.valueOf(vendorid));
			String id = vendorHelper.createSellerConfiguration(String.valueOf(contractId), "COUPONS_ALLOWED", "true");
			vendorHelper.updateSellerConfiguration(id, "value", "test");
			String query = "configurationCategory.eq:COUPONS";
			sellerHelper.getSellerById(String.valueOf(sellerId), query);
		} finally {
			vmsHelper.deleteConfigurations(String.valueOf(sellerId), String.valueOf(contractId));
			vmsHelper.deleteFromSourceSystemPartnerMapping(String.valueOf(sellerId));
			vmsHelper.deleteContract(String.valueOf(contractId));
			vmsHelper.deleteFromVendor(String.valueOf(vendorid));
			vmsHelper.deleteFromSeller(String.valueOf(sellerId));
		}
	}

}
