package com.myntra.apiTests.erpservices.partners.Tests;

import com.myntra.apiTests.erpservices.partners.dp.vmsSellerEndToEndDP;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.boromir.DBUtilities;

import org.apache.log4j.Logger;
import org.testng.AssertJUnit;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.Test;

import java.sql.SQLException;
import java.util.HashMap;
import com.myntra.apiTests.common.APINAME;
import com.myntra.apiTests.common.Myntra;
import com.myntra.apiTests.common.ServiceType;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.PayloadType;
import com.myntra.lordoftherings.gandalf.RequestGenerator;


/**
 * Author Shubham gupta
 **/

public class vmsSellerEndToEnd extends vmsSellerEndToEndDP {

	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(PartnerPortalService.class);

	APIUtilities apiUtil = new APIUtilities();

	static String partnerId;
	static String barCode;

	/** API Name - Create Seller **/

	@Test(groups = { "Regression" }, priority = 0, dataProvider = "createSeller")
	public void createSeller(String name, String expectedResult) {
		MyntraService service = Myntra.getService(ServiceType.ERP_MARKETPLACESELLER, APINAME.CREATESELLER,
				init.Configurations, new String[] { name, expectedResult }, PayloadType.JSON, PayloadType.JSON);
		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0=");
		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam);
		String res = req.respvalidate.returnresponseasstring();
		AssertJUnit.assertEquals(200, req.response.getStatus());

		APIUtilities.validateResponse("json", res, expectedResult);

		String ID = req.respvalidate.NodeValue("sellerResponse.data.seller.id", true).replaceAll("\"", "").trim();
		String BarCode = req.respvalidate.NodeValue("sellerResponse.data.seller.barcode", true).replaceAll("\"", "")
				.trim();

		System.out.println("Seller ID: " + ID);
		System.out.println("Seller ID: " + BarCode);
		partnerId = ID;
		barCode = BarCode;
	}

	/** API add seller address both billing and shipping **/

	@Test(groups = { "Regression" }, priority = 1, dataProvider = "addSellarAddress")
	public void addSellarAddress(String address1, String address2, String addressType, String city, String country,
			String email1, String email2, String enabled, String notes, String partnerType, String phone1,
			String phone2, String pinCode, String state, String expectedResult) {

		MyntraService service = Myntra.getService(ServiceType.ERP_MARKETPLACESELLER, APINAME.ADDBILLINGADDRESS,
				init.Configurations,
				new String[] { address1, address2, addressType, city, country, email1, email2, enabled, partnerId,
						notes, partnerType, phone1, phone2, pinCode, state, expectedResult },
				PayloadType.JSON, PayloadType.JSON);
		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0=");
		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam);
		String res = req.respvalidate.returnresponseasstring();
		AssertJUnit.assertEquals(200, req.response.getStatus());
		APIUtilities.validateResponse("json", res, expectedResult);

	}

	/** API Name - Add Seller Category Manager **/

	@Test(groups = { "Regression" }, priority = 2, dataProvider = "addCategoryManager")
	public void addSellerCategoryManager(String enabled, String userEntry, String expectedResult) {
		MyntraService service = Myntra.getService(ServiceType.ERP_MARKETPLACESELLER, APINAME.ADDCATEGORYMANAGER,
				init.Configurations, new String[] { enabled, partnerId, userEntry, expectedResult }, PayloadType.JSON,
				PayloadType.JSON);
		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0=");
		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam);
		String res = req.respvalidate.returnresponseasstring();
		AssertJUnit.assertEquals(200, req.response.getStatus());
		APIUtilities.validateResponse("json", res, expectedResult);

	}

	/** API Name - Add multiple Brands to a particular seller **/

	@Test(groups = { "Regression" }, priority = 3, dataProvider = "addMultiBrandToSeller")
	public void addMultiBrandToSeller(String brandId, String brandName, String enabled, String brandId1,
			String brandName1, String enabled1, String brandId2, String brandName2, String enabled2,
			String expectedResult) {
		MyntraService service = Myntra.getService(ServiceType.ERP_MARKETPLACESELLER, APINAME.ADDMULTIBRANDTOSELLER,
				init.Configurations,
				new String[] { brandId, brandName, enabled, partnerId, brandId1, brandName1, enabled1, partnerId,
						brandId2, brandName2, enabled2, partnerId, expectedResult },
				PayloadType.JSON, PayloadType.JSON);
		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0=");
		System.out.println(service.URL);
		System.out.println(service.Payload);
		RequestGenerator req = new RequestGenerator(service, getParam);
		String res = req.respvalidate.returnresponseasstring();
		AssertJUnit.assertEquals(200, req.response.getStatus());

	}

	/** API Name - Add Seller Warehouse **/

	@Test(groups = { "Regression" }, priority = 4, dataProvider = "addSellerWarehouse", enabled = true)
	public void addSellerWarehouse(String leadTime, String myntraReturnWarehouse, String supplyType,
			String warehouseEntry, String expectedResult) {
		MyntraService service = Myntra.getService(ServiceType.ERP_MARKETPLACESELLER, APINAME.ADDSELLERWAREHOUSE,
				init.Configurations,
				new String[] { leadTime, myntraReturnWarehouse, partnerId, supplyType, warehouseEntry, expectedResult },
				PayloadType.JSON, PayloadType.JSON);
		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0=");
		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam);
		String res = req.respvalidate.returnresponseasstring();
		AssertJUnit.assertEquals(200, req.response.getStatus());

		APIUtilities.validateResponse("json", res, expectedResult);

	}

	/** API Name - Add seller Finance Information **/

	@Test(groups = { "Regression" }, priority = 5, dataProvider = "addSellerFinancialInformation")
	public void addSellerFinanceInformation(String accountId, String enabled, String expectedResult) {
		MyntraService service = Myntra.getService(ServiceType.ERP_MARKETPLACESELLER,
				APINAME.AddSELLERFINANCIALINFORMATION, init.Configurations,
				new String[] { accountId, enabled, partnerId, expectedResult }, PayloadType.JSON, PayloadType.JSON);
		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0=");
		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam);
		String res = req.respvalidate.returnresponseasstring();
		AssertJUnit.assertEquals(200, req.response.getStatus());
		APIUtilities.validateResponse("json", res, expectedResult);

	}

	/** API Name - Add KYC **/

	@Test(groups = { "Regression" }, priority = 6, dataProvider = "addKYC")
	public void Seller_addKYC(String documentType, String documnetNumebr, String documnetRefId, String enabled,
			String partnerType, String expectedResult) {
		MyntraService service = Myntra.getService(
				ServiceType.ERP_MARKETPLACESELLER, APINAME.ADDKYC, init.Configurations, new String[] { documentType,
						documnetNumebr, documnetRefId, enabled, partnerId, partnerType, expectedResult },
				PayloadType.JSON, PayloadType.JSON);
		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0=");
		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam);
		String res = req.respvalidate.returnresponseasstring();
		AssertJUnit.assertEquals(200, req.response.getStatus());
		APIUtilities.validateResponse("json", res, expectedResult);

	}

	/** API Name - Add Seller Payment Configuration **/

	@Test(groups = { "Regression" }, priority = 7, dataProvider = "addSellerPaymentConfiguration")
	public void addSellerPaymentConfiguration(String enabled, String marginMinimum, String marginPercentage,
			String offset, String sellerPayoutScheduleType, String expectedResult) {
		MyntraService service = Myntra.getService(ServiceType.ERP_MARKETPLACESELLER,
				APINAME.ADDPAYMENTCONFIGURATION, init.Configurations, new String[] { enabled, marginMinimum,
						marginPercentage, offset, partnerId, sellerPayoutScheduleType, expectedResult },
				PayloadType.JSON, PayloadType.JSON);
		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0=");
		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam);
		String res = req.respvalidate.returnresponseasstring();
		AssertJUnit.assertEquals(200, req.response.getStatus());
		APIUtilities.validateResponse("json", res, expectedResult);

	}

	/** API Name - Add Seller Bank detail Configuration **/

	@Test(groups = { "Regression" }, priority = 7, dataProvider = "addSellerPaymentConfiguration")
	public void addSellerBankDetail(String enabled, String marginMinimum, String marginPercentage, String offset,
			String sellerPayoutScheduleType, String expectedResult) {
		MyntraService service = Myntra.getService(ServiceType.ERP_MARKETPLACESELLER,
				APINAME.ADDPAYMENTCONFIGURATION, init.Configurations, new String[] { enabled, marginMinimum,
						marginPercentage, offset, partnerId, sellerPayoutScheduleType, expectedResult },
				PayloadType.JSON, PayloadType.JSON);
		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0=");
		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam);
		String res = req.respvalidate.returnresponseasstring();
		AssertJUnit.assertEquals(200, req.response.getStatus());
		APIUtilities.validateResponse("json", res, expectedResult);

	}

	@AfterSuite(groups = { "Regression" })
	public void testAfterSuite() throws SQLException {
		deleteSellerRecord(partnerId);
	}

	// --------------------Delete Record Calls-----------------
	public void deleteSellerRecord(String partnerId) throws SQLException {
		deleteSellerPaymentConfig(partnerId);
		deleteSellerKyc(partnerId);
		deleteSellerBankDetails(partnerId);
		deleteSellerWarehouse(partnerId);
		deleteSellerBrand(partnerId);
		deleteSellerCategoryManager(partnerId);
		deleteSeller(partnerId);

		System.out.println("Deletion of all the record which we Inserted Successfull");
	}

	public void deleteSeller(String partnerId) throws SQLException {
		String updateSeller = "DELETE from seller where id = " + partnerId + "";
		DBUtilities.exUpdateQuery(updateSeller, "vms");
	}

	public void deleteSellerCategoryManager(String partnerId) throws SQLException {
		String updateSellerCategoryManager = "DELETE from seller_category_manager where seller_id = " + partnerId + "";
		DBUtilities.exUpdateQuery(updateSellerCategoryManager, "vms");
	}

	public void deleteSellerBrand(String partnerId) throws SQLException {
		String updateSellerBrand = "DELETE from seller_brand where seller_id = " + partnerId + "";
		DBUtilities.exUpdateQuery(updateSellerBrand, "vms");
	}

	public void deleteSellerWarehouse(String partnerId) throws SQLException {
		String updateSellerWarehouse = "DELETE from seller_warehouse where seller_id = " + partnerId + "";
		DBUtilities.exUpdateQuery(updateSellerWarehouse, "vms");
	}

	public void deleteSellerBankDetails(String partnerId) throws SQLException {
		String updateSellerBankDetails = "DELETE from seller_finance_info where seller_id = " + partnerId + "";
		DBUtilities.exUpdateQuery(updateSellerBankDetails, "vms");
	}

	public void deleteSellerKyc(String partnerId) throws SQLException {
		String updateSellerKyc = "DELETE from partner_kyc_document where partner_id = " + partnerId + "";
		DBUtilities.exUpdateQuery(updateSellerKyc, "vms");
	}

	public void deleteSellerPaymentConfig(String partnerId) throws SQLException {
		String updateSellerPaymentConfig = "DELETE from seller_payment_configuration where seller_id = " + partnerId
				+ "";
		DBUtilities.exUpdateQuery(updateSellerPaymentConfig, "vms");
	}

}
