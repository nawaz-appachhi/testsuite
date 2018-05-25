package com.myntra.apiTests.erpservices.partners.Tests;

import com.myntra.apiTests.dataproviders.vmsSellerServiceDP;
import com.myntra.apiTests.erpservices.sellerapis.SellerApiHelper;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.boromir.DBUtilities;

import com.myntra.test.commons.testbase.BaseTest;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import javax.xml.bind.JAXBException;
import java.io.UnsupportedEncodingException;
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

public class vmsSeller extends BaseTest
{
	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(PartnerPortalService.class);
	static String envName = "fox7";

	APIUtilities apiUtil = new APIUtilities();

	/** API Name - Create Seller 
	 * @throws SQLException **/

	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression",
			"ExhaustiveRegression" }, priority = 0, dataProvider = "createSeller", dataProviderClass = vmsSellerServiceDP.class)
	public void VMS_createSeller(String name, String paramStatusCode,
			String paramStatusMessage, String paramStatusType) throws SQLException
	{
		MyntraService service = Myntra.getService(
				ServiceType.ERP_MARKETPLACESELLER, APINAME.CREATESELLER,
				init.Configurations, new String[] { name, paramStatusCode,
						paramStatusMessage, paramStatusType },
				PayloadType.JSON, PayloadType.JSON);
		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0=");
		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());

		String responseStatusCode = req.respvalidate
				.NodeValue("sellerResponse.status.statusCode", true)
				.replaceAll("\"", "").trim();
		String responseStatusMessage = req.respvalidate
				.NodeValue("sellerResponse.status.statusMessage", true)
				.replaceAll("\"", "").trim();
		String responseStatusType = req.respvalidate
				.NodeValue("sellerResponse.status.statusType", true)
				.replaceAll("\"", "").trim();

		AssertJUnit.assertTrue("Unable to create seller, Expected: <"
				+ paramStatusCode + "> but Actual: <" + responseStatusCode
				+ ">", responseStatusCode.equals(paramStatusCode));

		AssertJUnit.assertTrue("Unable to create seller, Expected: <"
				+ paramStatusMessage + "> but Actual: <"
				+ responseStatusMessage + ">",
				responseStatusMessage.equals(paramStatusMessage));

		AssertJUnit.assertTrue("Unable to create seller, Expected: <"
				+ paramStatusType + "> but Actual: <" + responseStatusType
				+ ">", responseStatusType.equals(paramStatusType));
		
		String Id = req.respvalidate
				.NodeValue("sellerResponse.data.seller.id", true)
				.replaceAll("\"", "").trim();
		System.out.println("Seller ID: " + Id);
		
		deleteSeller(Id);
		
		}
	public void deleteSeller(String Id)
			throws SQLException {
		String updateSeller = "DELETE from seller where id = "+Id+"";
		DBUtilities.exUpdateQuery(updateSeller, "vms");

	}
	
	/** API Name - Create Seller Negative test case**/

	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression","ExhaustiveRegression" }, priority = 0, dataProvider = "createSellerNegative", dataProviderClass = vmsSellerServiceDP.class)
	public void VMS_createSellernegative(String name, String paramStatusCode,
			String paramStatusMessage, String paramStatusType)
	{
		MyntraService service = Myntra.getService(
				ServiceType.ERP_MARKETPLACESELLER, APINAME.CREATESELLER,
				init.Configurations, new String[] { name, paramStatusCode,
						paramStatusMessage, paramStatusType },
				PayloadType.JSON, PayloadType.JSON);
		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0=");
		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());

		String responseStatusCode = req.respvalidate
				.NodeValue("sellerResponse.status.statusCode", true)
				.replaceAll("\"", "").trim();
		String responseStatusMessage = req.respvalidate
				.NodeValue("sellerResponse.status.statusMessage", true)
				.replaceAll("\"", "").trim();
		String responseStatusType = req.respvalidate
				.NodeValue("sellerResponse.status.statusType", true)
				.replaceAll("\"", "").trim();

		AssertJUnit.assertTrue("Unable to create seller, Expected: <"
				+ paramStatusCode + "> but Actual: <" + responseStatusCode
				+ ">", responseStatusCode.equals(paramStatusCode));

		AssertJUnit.assertTrue("Unable to create seller, Expected: <"
				+ paramStatusMessage + "> but Actual: <"
				+ responseStatusMessage + ">",
				responseStatusMessage.equals(paramStatusMessage));

		AssertJUnit.assertTrue("Unable to create seller, Expected: <"
				+ paramStatusType + "> but Actual: <" + responseStatusType
				+ ">", responseStatusType.equals(paramStatusType));

	}

	/** API Name - Get All Sellers **/

	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression","ExhaustiveRegression" }, priority = 0, dataProvider = "getAllSeller", dataProviderClass = vmsSellerServiceDP.class)
	public void VMS_getAllSeller(String paramStatusCode,
			String paramStatusMessage, String paramStatusType)
	{
		MyntraService service = Myntra.getService(
				ServiceType.ERP_MARKETPLACESELLER, APINAME.GETALLSELLER,
				init.Configurations, new String[] { paramStatusCode,
						paramStatusMessage, paramStatusType },
				PayloadType.JSON, PayloadType.JSON);
		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0=");
		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());

		String responseStatusCode = req.respvalidate
				.NodeValue("sellerResponse.status.statusCode", true)
				.replaceAll("\"", "").trim();
		String responseStatusMessage = req.respvalidate
				.NodeValue("sellerResponse.status.statusMessage", true)
				.replaceAll("\"", "").trim();
		String responseStatusType = req.respvalidate
				.NodeValue("sellerResponse.status.statusType", true)
				.replaceAll("\"", "").trim();

		AssertJUnit.assertTrue("Unable to search, Expected: <"
				+ paramStatusCode + "> but Actual: <" + responseStatusCode
				+ ">", responseStatusCode.equals(paramStatusCode));

		AssertJUnit.assertTrue("Unable to search, Expected: <"
				+ paramStatusMessage + "> but Actual: <"
				+ responseStatusMessage + ">",
				responseStatusMessage.equals(paramStatusMessage));

		AssertJUnit.assertTrue("Unable to search, Expected: <"
				+ paramStatusType + "> but Actual: <" + responseStatusType
				+ ">", responseStatusType.equals(paramStatusType));

	}

	/** API Name - Get Sellers Info **/

	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression","ExhaustiveRegression" }, priority = 0, dataProvider = "getAllSeller", dataProviderClass = vmsSellerServiceDP.class)
	public void VMS_getSellerInfo(String paramStatusCode,
			String paramStatusMessage, String paramStatusType)
	{
		MyntraService service = Myntra.getService(
				ServiceType.ERP_MARKETPLACESELLER, APINAME.GETSELLERINFO,
				init.Configurations, new String[] { paramStatusCode,
						paramStatusMessage, paramStatusType },
				PayloadType.JSON, PayloadType.JSON);
		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0=");
		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());

		String responseStatusCode = req.respvalidate
				.NodeValue("sellerResponse.status.statusCode", true)
				.replaceAll("\"", "").trim();
		String responseStatusMessage = req.respvalidate
				.NodeValue("sellerResponse.status.statusMessage", true)
				.replaceAll("\"", "").trim();
		String responseStatusType = req.respvalidate
				.NodeValue("sellerResponse.status.statusType", true)
				.replaceAll("\"", "").trim();

		AssertJUnit.assertTrue("Unable to search, Expected: <"
				+ paramStatusCode + "> but Actual: <" + responseStatusCode
				+ ">", responseStatusCode.equals(paramStatusCode));

		AssertJUnit.assertTrue("Unable to search, Expected: <"
				+ paramStatusMessage + "> but Actual: <"
				+ responseStatusMessage + ">",
				responseStatusMessage.equals(paramStatusMessage));

		AssertJUnit.assertTrue("Unable to search, Expected: <"
				+ paramStatusType + "> but Actual: <" + responseStatusType
				+ ">", responseStatusType.equals(paramStatusType));
	}

	/** API Name - Search Address by seller **/
	// it will get addresses of all sellers 

	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression","ExhaustiveRegression" }, priority = 0, dataProvider = "getAddressBySeller", dataProviderClass = vmsSellerServiceDP.class)
	public void VMS_searchAddressBySeller(String id, String paramStatusCode,
			String paramStatusMessage, String paramStatusType)
	{
		MyntraService service = Myntra.getService(
				ServiceType.ERP_MARKETPLACESELLER, APINAME.GETADDRESSBYSELLER,
				init.Configurations,PayloadType.JSON, new String[] { id },
				 PayloadType.JSON);
		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0=");
		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());

		String responseStatusCode = req.respvalidate
				.NodeValue("partnerContactAddressResponse.status.statusCode",
						true).replaceAll("\"", "").trim();
		String responseStatusMessage = req.respvalidate
				.NodeValue(
						"partnerContactAddressResponse.status.statusMessage",
						true).replaceAll("\"", "").trim();
		String responseStatusType = req.respvalidate
				.NodeValue("partnerContactAddressResponse.status.statusType",
						true).replaceAll("\"", "").trim();

		AssertJUnit.assertTrue("Unable to search, Expected: <"
				+ paramStatusCode + "> but Actual: <" + responseStatusCode
				+ ">", responseStatusCode.equals(paramStatusCode));

		AssertJUnit.assertTrue("Unable to search, Expected: <"
				+ paramStatusMessage + "> but Actual: <"
				+ responseStatusMessage + ">",
				responseStatusMessage.equals(paramStatusMessage));

		AssertJUnit.assertTrue("Unable to search, Expected: <"
				+ paramStatusType + "> but Actual: <" + responseStatusType
				+ ">", responseStatusType.equals(paramStatusType));
	}
	
	/*API -- Search(Get) all address*/
	
	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression","ExhaustiveRegression" }, priority = 0, dataProvider = "getAllSellerAddress", dataProviderClass = vmsSellerServiceDP.class)
	public void VMS_getAllSellerAddress(String paramStatusCode,
			String paramStatusMessage, String paramStatusType)
	{
		MyntraService service = Myntra.getService(
				ServiceType.ERP_MARKETPLACESELLER, APINAME.GETALLSELLERADDRESS,
				init.Configurations, new String[] { paramStatusCode,
						paramStatusMessage, paramStatusType },
				PayloadType.JSON, PayloadType.JSON);
		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0=");
		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());

		String responseStatusCode = req.respvalidate
				.NodeValue("partnerContactAddressResponse.status.statusCode",
						true).replaceAll("\"", "").trim();
		String responseStatusMessage = req.respvalidate
				.NodeValue(
						"partnerContactAddressResponse.status.statusMessage",
						true).replaceAll("\"", "").trim();
		String responseStatusType = req.respvalidate
				.NodeValue("partnerContactAddressResponse.status.statusType",
						true).replaceAll("\"", "").trim();

		AssertJUnit.assertTrue("Unable to search, Expected: <"
				+ paramStatusCode + "> but Actual: <" + responseStatusCode
				+ ">", responseStatusCode.equals(paramStatusCode));

		AssertJUnit.assertTrue("Unable to search, Expected: <"
				+ paramStatusMessage + "> but Actual: <"
				+ responseStatusMessage + ">",
				responseStatusMessage.equals(paramStatusMessage));

		AssertJUnit.assertTrue("Unable to search, Expected: <"
				+ paramStatusType + "> but Actual: <" + responseStatusType
				+ ">", responseStatusType.equals(paramStatusType));
	}

	/** API add billing address **/

	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression","ExhaustiveRegression" }, priority = 0, dataProvider = "addBillingAddress", dataProviderClass = vmsSellerServiceDP.class)
	public void Seller_addBillingAddress(String address1, String address2,
			String addressType, String city, String country, String email1,
			String email2, String enabled, String partnerId, String notes,
			String partnerType, String phone1, String phone2, String pinCode,
			String state, String paramStatusCode, String paramStatusMessage,
			String paramStatusType)
	{
		MyntraService service = Myntra.getService(
				ServiceType.ERP_MARKETPLACESELLER, APINAME.ADDBILLINGADDRESS,
				init.Configurations, new String[] { address1, address2,
						addressType, city, country, email1, email2, enabled,
						partnerId, notes, partnerType, phone1, phone2, pinCode,
						state, paramStatusCode, paramStatusMessage,
						paramStatusType }, PayloadType.JSON, PayloadType.JSON);
		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0=");
		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());

		String responseStatusCode = req.respvalidate
				.NodeValue("partnerContactAddressResponse.status.statusCode",
						true).replaceAll("\"", "").trim();
		String responseStatusMessage = req.respvalidate
				.NodeValue(
						"partnerContactAddressResponse.status.statusMessage",
						true).replaceAll("\"", "").trim();
		String responseStatusType = req.respvalidate
				.NodeValue("partnerContactAddressResponse.status.statusType",
						true).replaceAll("\"", "").trim();

		AssertJUnit.assertTrue("Address is not updated, Expected: <"
				+ paramStatusCode + "> but Actual: <" + responseStatusCode
				+ ">", responseStatusCode.equals(paramStatusCode));

		AssertJUnit.assertTrue("Address is not updated, Expected: <"
				+ paramStatusMessage + "> but Actual: <"
				+ responseStatusMessage + ">",
				responseStatusMessage.equals(paramStatusMessage));

		AssertJUnit.assertTrue("Address is not updated, Expected: <"
				+ paramStatusType + "> but Actual: <" + responseStatusType
				+ ">", responseStatusType.equals(paramStatusType));
	}

	/** API add billing address Negative test cases**/

	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression","ExhaustiveRegression" }, priority = 0, dataProvider = "addBillingAddressNegative", dataProviderClass = vmsSellerServiceDP.class)
	public void Seller_addBillingAddressNegative(String address1, String address2,
			String addressType, String city, String country, String email1,
			String email2, String enabled, String partnerId, String notes,
			String partnerType, String phone1, String phone2, String pinCode,
			String state, String paramStatusCode, String paramStatusMessage,
			String paramStatusType)
	{
		MyntraService service = Myntra.getService(
				ServiceType.ERP_MARKETPLACESELLER, APINAME.ADDBILLINGADDRESS,
				init.Configurations, new String[] { address1, address2,
						addressType, city, country, email1, email2, enabled,
						partnerId, notes, partnerType, phone1, phone2, pinCode,
						state, paramStatusCode, paramStatusMessage,
						paramStatusType }, PayloadType.JSON, PayloadType.JSON);
		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0=");
		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());

		String responseStatusCode = req.respvalidate
				.NodeValue("partnerContactAddressResponse.status.statusCode",
						true).replaceAll("\"", "").trim();
		String responseStatusMessage = req.respvalidate
				.NodeValue(
						"partnerContactAddressResponse.status.statusMessage",
						true).replaceAll("\"", "").trim();
		String responseStatusType = req.respvalidate
				.NodeValue("partnerContactAddressResponse.status.statusType",
						true).replaceAll("\"", "").trim();

		AssertJUnit.assertTrue("Address is not updated, Expected: <"
				+ paramStatusCode + "> but Actual: <" + responseStatusCode
				+ ">", responseStatusCode.equals(paramStatusCode));

		AssertJUnit.assertTrue("Address is not updated, Expected: <"
				+ paramStatusMessage + "> but Actual: <"
				+ responseStatusMessage + ">",
				responseStatusMessage.equals(paramStatusMessage));

		AssertJUnit.assertTrue("Address is not updated, Expected: <"
				+ paramStatusType + "> but Actual: <" + responseStatusType
				+ ">", responseStatusType.equals(paramStatusType));
	}

	
	/** API shipping address **/

	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression","ExhaustiveRegression" }, priority = 0, dataProvider = "addShippingAddress", dataProviderClass = vmsSellerServiceDP.class)
	public void Seller_addShippingAddress(String address1, String address2,
			String addressType, String city, String country, String email1,
			String email2, String enabled, String partnerId, String notes,
			String partnerType, String phone1, String phone2, String pinCode,
			String state, String paramStatusCode, String paramStatusMessage,
			String paramStatusType)
	{
		MyntraService service = Myntra.getService(
				ServiceType.ERP_MARKETPLACESELLER, APINAME.ADDSHIPPINGADDRESS,
				init.Configurations, new String[] { address1, address2,
						addressType, city, country, email1, email2, enabled,
						partnerId, notes, partnerType, phone1, phone2, pinCode,
						state, paramStatusCode, paramStatusMessage,
						paramStatusType }, PayloadType.JSON, PayloadType.JSON);
		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0=");
		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());

		String responseStatusCode = req.respvalidate
				.NodeValue("partnerContactAddressResponse.status.statusCode",
						true).replaceAll("\"", "").trim();
		String responseStatusMessage = req.respvalidate
				.NodeValue(
						"partnerContactAddressResponse.status.statusMessage",
						true).replaceAll("\"", "").trim();
		String responseStatusType = req.respvalidate
				.NodeValue("partnerContactAddressResponse.status.statusType",
						true).replaceAll("\"", "").trim();

		AssertJUnit.assertTrue("Address is not updated, Expected: <"
				+ paramStatusCode + "> but Actual: <" + responseStatusCode
				+ ">", responseStatusCode.equals(paramStatusCode));

		AssertJUnit.assertTrue("Address is not updated, Expected: <"
				+ paramStatusMessage + "> but Actual: <"
				+ responseStatusMessage + ">",
				responseStatusMessage.equals(paramStatusMessage));

		AssertJUnit.assertTrue("Address is not updated, Expected: <"
				+ paramStatusType + "> but Actual: <" + responseStatusType
				+ ">", responseStatusType.equals(paramStatusType));
	}

	/** API Name - Add KYC **/

	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression","ExhaustiveRegression" }, priority = 0, dataProvider = "addKYC", dataProviderClass = vmsSellerServiceDP.class)
	public void Seller_addKYC(String documentType, String documnetNumebr,
			String documnetRefId, String enabled, String entityId,
			String partnerType, String paramStatusCode,
			String paramStatusMessage, String paramStatusType)
	{
		MyntraService service = Myntra.getService(
				ServiceType.ERP_MARKETPLACESELLER, APINAME.ADDKYC,
				init.Configurations, new String[] { documentType,
						documnetNumebr, documnetRefId, enabled, entityId,
						partnerType, paramStatusCode, paramStatusMessage,
						paramStatusType }, PayloadType.JSON, PayloadType.JSON);
		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0=");
		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());

		String responseStatusCode = req.respvalidate
				.NodeValue("partnerKYCDocumentResponse.status.statusCode", true)
				.replaceAll("\"", "").trim();
		String responseStatusMessage = req.respvalidate
				.NodeValue("partnerKYCDocumentResponse.status.statusMessage",
						true).replaceAll("\"", "").trim();
		String responseStatusType = req.respvalidate
				.NodeValue("partnerKYCDocumentResponse.status.statusType", true)
				.replaceAll("\"", "").trim();

		AssertJUnit.assertTrue("Unable to create seller, Expected: <"
				+ paramStatusCode + "> but Actual: <" + responseStatusCode
				+ ">", responseStatusCode.equals(paramStatusCode));

		AssertJUnit.assertTrue("Unable to create seller, Expected: <"
				+ paramStatusMessage + "> but Actual: <"
				+ responseStatusMessage + ">",
				responseStatusMessage.equals(paramStatusMessage));

		AssertJUnit.assertTrue("Unable to create seller, Expected: <"
				+ paramStatusType + "> but Actual: <" + responseStatusType
				+ ">", responseStatusType.equals(paramStatusType));
	}

	
	/** API Name - Add KYC -- Negative cases**/

	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression","ExhaustiveRegression" }, priority = 0, dataProvider = "addKYCNegative", dataProviderClass = vmsSellerServiceDP.class)
	public void Seller_addKYC_Negative(String documentType, String documnetNumebr,
			String documnetRefId, String enabled, String entityId,
			String partnerType, String paramStatusCode,
			String paramStatusMessage, String paramStatusType)
	{
		MyntraService service = Myntra.getService(
				ServiceType.ERP_MARKETPLACESELLER, APINAME.ADDKYC,
				init.Configurations, new String[] { documentType,
						documnetNumebr, documnetRefId, enabled, entityId,
						partnerType, paramStatusCode, paramStatusMessage,
						paramStatusType }, PayloadType.JSON, PayloadType.JSON);
		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0=");
		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());

		String responseStatusCode = req.respvalidate
				.NodeValue("partnerKYCDocumentResponse.status.statusCode", true)
				.replaceAll("\"", "").trim();
		String responseStatusMessage = req.respvalidate
				.NodeValue("partnerKYCDocumentResponse.status.statusMessage",
						true).replaceAll("\"", "").trim();
		String responseStatusType = req.respvalidate
				.NodeValue("partnerKYCDocumentResponse.status.statusType", true)
				.replaceAll("\"", "").trim();

		AssertJUnit.assertTrue("Unable to create seller, Expected: <"
				+ paramStatusCode + "> but Actual: <" + responseStatusCode
				+ ">", responseStatusCode.equals(paramStatusCode));

		AssertJUnit.assertTrue("Unable to create seller, Expected: <"
				+ paramStatusMessage + "> but Actual: <"
				+ responseStatusMessage + ">",
				responseStatusMessage.equals(paramStatusMessage));

		AssertJUnit.assertTrue("Unable to create seller, Expected: <"
				+ paramStatusType + "> but Actual: <" + responseStatusType
				+ ">", responseStatusType.equals(paramStatusType));
	}
	
	/** API Name - Add Seller Category Manager **/

	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression","ExhaustiveRegression" }, priority = 0, dataProvider = "addCategoryManager", dataProviderClass = vmsSellerServiceDP.class)
	public void Seller_addSellerCategoryManager(String enabled, String sellerEntry,
			String userEntry, String paramStatusCode,
			String paramStatusMessage, String paramStatusType)
	{
		MyntraService service = Myntra.getService(
				ServiceType.ERP_MARKETPLACESELLER, APINAME.ADDCATEGORYMANAGER,
				init.Configurations, new String[] { enabled, sellerEntry,
						userEntry, paramStatusCode, paramStatusMessage,
						paramStatusType }, PayloadType.JSON, PayloadType.JSON);
		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0=");
		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());

		String responseStatusCode = req.respvalidate
				.NodeValue("sellerCategoryManagerResponse.status.statusCode",
						true).replaceAll("\"", "").trim();
		String responseStatusMessage = req.respvalidate
				.NodeValue(
						"sellerCategoryManagerResponse.status.statusMessage",
						true).replaceAll("\"", "").trim();
		String responseStatusType = req.respvalidate
				.NodeValue("sellerCategoryManagerResponse.status.statusType",
						true).replaceAll("\"", "").trim();

		AssertJUnit.assertTrue(
				"Unable to add category manager to the seller, Expected: <"
						+ paramStatusCode + "> but Actual: <"
						+ responseStatusCode + ">",
				responseStatusCode.equals(paramStatusCode));

		AssertJUnit.assertTrue(
				"Unable to add category manager to the seller, Expected: <"
						+ paramStatusMessage + "> but Actual: <"
						+ responseStatusMessage + ">",
				responseStatusMessage.equals(paramStatusMessage));

		AssertJUnit.assertTrue(
				"Unable to add category manager to the seller, Expected: <"
						+ paramStatusType + "> but Actual: <"
						+ responseStatusType + ">",
				responseStatusType.equals(paramStatusType));
	}
	
	/** API Name - Add Seller Category Manager Negative Test cases **/

	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression","ExhaustiveRegression" }, priority = 0, dataProvider = "addCategoryManagerNegative", dataProviderClass = vmsSellerServiceDP.class)
	public void Seller_addSellerCategoryManager_Negative(String enabled, String sellerEntry,
			String userEntry, String paramStatusCode,
			String paramStatusMessage, String paramStatusType)
	{
		MyntraService service = Myntra.getService(
				ServiceType.ERP_MARKETPLACESELLER, APINAME.ADDCATEGORYMANAGER,
				init.Configurations, new String[] { enabled, sellerEntry,
						userEntry, paramStatusCode, paramStatusMessage,
						paramStatusType }, PayloadType.JSON, PayloadType.JSON);
		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0=");
		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());

		String responseStatusCode = req.respvalidate
				.NodeValue("sellerCategoryManagerResponse.status.statusCode",
						true).replaceAll("\"", "").trim();
		String responseStatusMessage = req.respvalidate
				.NodeValue(
						"sellerCategoryManagerResponse.status.statusMessage",
						true).replaceAll("\"", "").trim();
		String responseStatusType = req.respvalidate
				.NodeValue("sellerCategoryManagerResponse.status.statusType",
						true).replaceAll("\"", "").trim();

		AssertJUnit.assertTrue(
				"Unable to add category manager to the seller, Expected: <"
						+ paramStatusCode + "> but Actual: <"
						+ responseStatusCode + ">",
				responseStatusCode.equals(paramStatusCode));

		AssertJUnit.assertTrue(
				"Unable to add category manager to the seller, Expected: <"
						+ paramStatusMessage + "> but Actual: <"
						+ responseStatusMessage + ">",
				responseStatusMessage.equals(paramStatusMessage));

		AssertJUnit.assertTrue(
				"Unable to add category manager to the seller, Expected: <"
						+ paramStatusType + "> but Actual: <"
						+ responseStatusType + ">",
				responseStatusType.equals(paramStatusType));
	}

	/** API Name - Disable Seller Category Manager **/

	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression","ExhaustiveRegression" }, priority = 0, dataProvider = "disableCategoryManager", dataProviderClass = vmsSellerServiceDP.class)
	public void Seller_disableSellerCategoryManager(String enabled,
			String sellerEntry, String userEntry, String paramStatusCode,
			String paramStatusMessage, String paramStatusType)
	{
		MyntraService service = Myntra.getService(
				ServiceType.ERP_MARKETPLACESELLER,
				APINAME.DISABLECATEGORYMANAGER, init.Configurations,
				new String[] { enabled, sellerEntry, userEntry,
						paramStatusCode, paramStatusMessage, paramStatusType },
				PayloadType.JSON, PayloadType.JSON);
		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0=");
		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());

		String responseStatusCode = req.respvalidate
				.NodeValue("sellerCategoryManagerResponse.status.statusCode",
						true).replaceAll("\"", "").trim();
		String responseStatusMessage = req.respvalidate
				.NodeValue(
						"sellerCategoryManagerResponse.status.statusMessage",
						true).replaceAll("\"", "").trim();
		String responseStatusType = req.respvalidate
				.NodeValue("sellerCategoryManagerResponse.status.statusType",
						true).replaceAll("\"", "").trim();

		AssertJUnit.assertTrue(
				"Unable to disable category manager to the seller, Expected: <"
						+ paramStatusCode + "> but Actual: <"
						+ responseStatusCode + ">",
				responseStatusCode.equals(paramStatusCode));

		AssertJUnit.assertTrue(
				"Unable to disable category manager to the seller, Expected: <"
						+ paramStatusMessage + "> but Actual: <"
						+ responseStatusMessage + ">",
				responseStatusMessage.equals(paramStatusMessage));

		AssertJUnit.assertTrue(
				"Unable to disable category manager to the seller, Expected: <"
						+ paramStatusType + "> but Actual: <"
						+ responseStatusType + ">",
				responseStatusType.equals(paramStatusType));
	}

	/** API Name - Add Seller Payment Configuration **/

	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression","ExhaustiveRegression" }, priority = 0, dataProvider = "addSellerPaymentConfiguration", dataProviderClass = vmsSellerServiceDP.class)
	public void Seller_addSellerPaymentConfiguration(String enabled,
			String marginMinimum, String marginPercentage, String offset,
			String sellerEntry, String sellerPayoutScheduleType,
			String paramStatusCode, String paramStatusMessage,
			String paramStatusType)
	{
		MyntraService service = Myntra.getService(
				ServiceType.ERP_MARKETPLACESELLER,
				APINAME.ADDPAYMENTCONFIGURATION, init.Configurations,
				new String[] { enabled, marginMinimum, marginPercentage,
						offset, sellerEntry, sellerPayoutScheduleType,
						paramStatusCode, paramStatusMessage, paramStatusType },
				PayloadType.JSON, PayloadType.JSON);
		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0=");
		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());

		String responseStatusCode = req.respvalidate
				.NodeValue(
						"sellerPaymentConfigurationResponse.status.statusCode",
						true).replaceAll("\"", "").trim();
		String responseStatusMessage = req.respvalidate
				.NodeValue(
						"sellerPaymentConfigurationResponse.status.statusMessage",
						true).replaceAll("\"", "").trim();
		String responseStatusType = req.respvalidate
				.NodeValue(
						"sellerPaymentConfigurationResponse.status.statusType",
						true).replaceAll("\"", "").trim();

		AssertJUnit.assertTrue(
				"Unable to add seller payment configuration, Expected: <"
						+ paramStatusCode + "> but Actual: <"
						+ responseStatusCode + ">",
				responseStatusCode.equals(paramStatusCode));

		AssertJUnit.assertTrue(
				"Unable to add seller payment configuration, Expected: <"
						+ paramStatusMessage + "> but Actual: <"
						+ responseStatusMessage + ">",
				responseStatusMessage.equals(paramStatusMessage));

		AssertJUnit.assertTrue(
				"Unable to add seller payment configuration, Expected: <"
						+ paramStatusType + "> but Actual: <"
						+ responseStatusType + ">",
				responseStatusType.equals(paramStatusType));
	}
	
	/** API Name - Add Seller Payment Configuration Negative Test cases**/

	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression","ExhaustiveRegression" }, priority = 0, dataProvider = "addSellerPaymentConfigurationNegative", dataProviderClass = vmsSellerServiceDP.class)
	public void Seller_addSellerPaymentConfiguration_Negative(String enabled,
			String marginMinimum, String marginPercentage, String offset,
			String sellerEntry, String sellerPayoutScheduleType,
			String paramStatusCode, String paramStatusMessage,
			String paramStatusType)
	{
		MyntraService service = Myntra.getService(
				ServiceType.ERP_MARKETPLACESELLER,
				APINAME.ADDPAYMENTCONFIGURATION, init.Configurations,
				new String[] { enabled, marginMinimum, marginPercentage,
						offset, sellerEntry, sellerPayoutScheduleType,
						paramStatusCode, paramStatusMessage, paramStatusType },
				PayloadType.JSON, PayloadType.JSON);
		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0=");
		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());

		String responseStatusCode = req.respvalidate
				.NodeValue(
						"sellerPaymentConfigurationResponse.status.statusCode",
						true).replaceAll("\"", "").trim();
		String responseStatusMessage = req.respvalidate
				.NodeValue(
						"sellerPaymentConfigurationResponse.status.statusMessage",
						true).replaceAll("\"", "").trim();
		String responseStatusType = req.respvalidate
				.NodeValue(
						"sellerPaymentConfigurationResponse.status.statusType",
						true).replaceAll("\"", "").trim();

		AssertJUnit.assertTrue(
				"Unable to add seller payment configuration, Expected: <"
						+ paramStatusCode + "> but Actual: <"
						+ responseStatusCode + ">",
				responseStatusCode.equals(paramStatusCode));

		AssertJUnit.assertTrue(
				"Unable to add seller payment configuration, Expected: <"
						+ paramStatusMessage + "> but Actual: <"
						+ responseStatusMessage + ">",
				responseStatusMessage.equals(paramStatusMessage));

		AssertJUnit.assertTrue(
				"Unable to add seller payment configuration, Expected: <"
						+ paramStatusType + "> but Actual: <"
						+ responseStatusType + ">",
				responseStatusType.equals(paramStatusType));
	}

	
	
	/** API Name - Disable Seller Payment Configuration **/

	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression","ExhaustiveRegression" }, priority = 0, dataProvider = "disablePaymentConfiguration", dataProviderClass = vmsSellerServiceDP.class)
	public void Seller_disableSellerPaymentConfiguration(String enabled,
			String marginMinimum, String marginPercentage, String offset,
			String sellerEntry, String sellerPayoutScheduleType,
			String paramStatusCode, String paramStatusMessage,
			String paramStatusType)
	{
		MyntraService service = Myntra.getService(
				ServiceType.ERP_MARKETPLACESELLER,
				APINAME.DISABLEPAYMENTCONFIGURATION, init.Configurations,
				new String[] { enabled, marginMinimum, marginPercentage,
						offset, sellerEntry, sellerPayoutScheduleType,
						paramStatusCode, paramStatusMessage, paramStatusType },
				PayloadType.JSON, PayloadType.JSON);
		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0=");
		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());

		String responseStatusCode = req.respvalidate
				.NodeValue(
						"sellerPaymentConfigurationResponse.status.statusCode",
						true).replaceAll("\"", "").trim();
		String responseStatusMessage = req.respvalidate
				.NodeValue(
						"sellerPaymentConfigurationResponse.status.statusMessage",
						true).replaceAll("\"", "").trim();
		String responseStatusType = req.respvalidate
				.NodeValue(
						"sellerPaymentConfigurationResponse.status.statusType",
						true).replaceAll("\"", "").trim();

		AssertJUnit.assertTrue(
				"Unable to Disable payment configuration of seller, Expected: <"
						+ paramStatusCode + "> but Actual: <"
						+ responseStatusCode + ">",
				responseStatusCode.equals(paramStatusCode));

		AssertJUnit.assertTrue(
				"Unable to Disable payment configuration of seller, Expected: <"
						+ paramStatusMessage + "> but Actual: <"
						+ responseStatusMessage + ">",
				responseStatusMessage.equals(paramStatusMessage));

		AssertJUnit.assertTrue(
				"Unable to Disable payment configuration of seller, Expected: <"
						+ paramStatusType + "> but Actual: <"
						+ responseStatusType + ">",
				responseStatusType.equals(paramStatusType));
	}
	
	/** API Name - Add Seller Warehouse **/

	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression","ExhaustiveRegression" }, priority = 0, dataProvider = "addSellerWarehouse", dataProviderClass = vmsSellerServiceDP.class)
	public void VMS_addSellerWarehouse(String leadTime,
			String myntraReturnWarehouse, String sellerEntry, String supplyType,
			String warehouseEntry,
			String paramStatusCode, String paramStatusMessage,
			String paramStatusType)
	{
		MyntraService service = Myntra.getService(
				ServiceType.ERP_MARKETPLACESELLER,
				APINAME.ADDSELLERWAREHOUSE, init.Configurations,
				new String[] { leadTime, myntraReturnWarehouse, sellerEntry,
						supplyType, warehouseEntry,
						paramStatusCode, paramStatusMessage, paramStatusType },
				PayloadType.JSON, PayloadType.JSON);
		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0=");
		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());

		String responseStatusCode = req.respvalidate
				.NodeValue(
						"sellerWarehouseResponse.status.statusCode",
						true).replaceAll("\"", "").trim();
		String responseStatusMessage = req.respvalidate
				.NodeValue(
						"sellerWarehouseResponse.status.statusMessage",
						true).replaceAll("\"", "").trim();
		String responseStatusType = req.respvalidate
				.NodeValue(
						"sellerWarehouseResponse.status.statusType",
						true).replaceAll("\"", "").trim();

		AssertJUnit.assertTrue(
				"Unable to add seller Warehouse, Expected: <"
						+ paramStatusCode + "> but Actual: <"
						+ responseStatusCode + ">",
				responseStatusCode.equals(paramStatusCode));

		AssertJUnit.assertTrue(
				"Unable to add seller Warehouse, Expected: <"
						+ paramStatusMessage + "> but Actual: <"
						+ responseStatusMessage + ">",
				responseStatusMessage.equals(paramStatusMessage));

		AssertJUnit.assertTrue(
				"Unable to add seller Warehouse, Expected: <"
						+ paramStatusType + "> but Actual: <"
						+ responseStatusType + ">",
				responseStatusType.equals(paramStatusType));
	}

	
	/** API Name - Add Seller Warehouse Negative test cases**/

	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression","ExhaustiveRegression" }, priority = 0, dataProvider = "addSellerWarehouseNegative", dataProviderClass = vmsSellerServiceDP.class)
	public void VMS_addSellerWarehouse_Negative(String leadTime,
			String myntraReturnWarehouse, String sellerEntry, String supplyType,
			String warehouseEntry,
			String paramStatusCode, String paramStatusMessage,
			String paramStatusType)
	{
		MyntraService service = Myntra.getService(
				ServiceType.ERP_MARKETPLACESELLER,
				APINAME.ADDSELLERWAREHOUSE, init.Configurations,
				new String[] { leadTime, myntraReturnWarehouse, sellerEntry,
						supplyType, warehouseEntry,
						paramStatusCode, paramStatusMessage, paramStatusType },
				PayloadType.JSON, PayloadType.JSON);
		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0=");
		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());

		String responseStatusCode = req.respvalidate
				.NodeValue(
						"sellerWarehouseResponse.status.statusCode",
						true).replaceAll("\"", "").trim();
		String responseStatusMessage = req.respvalidate
				.NodeValue(
						"sellerWarehouseResponse.status.statusMessage",
						true).replaceAll("\"", "").trim();
		String responseStatusType = req.respvalidate
				.NodeValue(
						"sellerWarehouseResponse.status.statusType",
						true).replaceAll("\"", "").trim();

		AssertJUnit.assertTrue(
				"Unable to add seller Warehouse, Expected: <"
						+ paramStatusCode + "> but Actual: <"
						+ responseStatusCode + ">",
				responseStatusCode.equals(paramStatusCode));

		AssertJUnit.assertTrue(
				"Unable to add seller Warehouse, Expected: <"
						+ paramStatusMessage + "> but Actual: <"
						+ responseStatusMessage + ">",
				responseStatusMessage.equals(paramStatusMessage));

		AssertJUnit.assertTrue(
				"Unable to add seller Warehouse, Expected: <"
						+ paramStatusType + "> but Actual: <"
						+ responseStatusType + ">",
				responseStatusType.equals(paramStatusType));
	}
	
	/** API Name - Disable Seller Warehouse **/

	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression","ExhaustiveRegression" }, priority = 0, dataProvider = "disableSellerWarehouse", dataProviderClass = vmsSellerServiceDP.class)
	public void VMS_disableSellerWarehouse(String leadTime,
			String myntraReturnWarehouse, String sellerEntry, String supplyType,
			String warehouseEntry,
			String paramStatusCode, String paramStatusMessage,
			String paramStatusType)
	{
		MyntraService service = Myntra.getService(
				ServiceType.ERP_MARKETPLACESELLER,
				APINAME.DISABLESELLERWAREHOUSE, init.Configurations,
				new String[] { leadTime, myntraReturnWarehouse, sellerEntry,
						supplyType, warehouseEntry,
						paramStatusCode, paramStatusMessage, paramStatusType },
				PayloadType.JSON, PayloadType.JSON);
		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0=");
		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());

		String responseStatusCode = req.respvalidate
				.NodeValue(
						"sellerWarehouseResponse.status.statusCode",
						true).replaceAll("\"", "").trim();
		String responseStatusMessage = req.respvalidate
				.NodeValue(
						"sellerWarehouseResponse.status.statusMessage",
						true).replaceAll("\"", "").trim();
		String responseStatusType = req.respvalidate
				.NodeValue(
						"sellerWarehouseResponse.status.statusType",
						true).replaceAll("\"", "").trim();

		AssertJUnit.assertTrue(
				"Unable to add seller Warehouse, Expected: <"
						+ paramStatusCode + "> but Actual: <"
						+ responseStatusCode + ">",
				responseStatusCode.equals(paramStatusCode));

		AssertJUnit.assertTrue(
				"Unable to add seller Warehouse, Expected: <"
						+ paramStatusMessage + "> but Actual: <"
						+ responseStatusMessage + ">",
				responseStatusMessage.equals(paramStatusMessage));

		AssertJUnit.assertTrue(
				"Unable to add seller Warehouse, Expected: <"
						+ paramStatusType + "> but Actual: <"
						+ responseStatusType + ">",
				responseStatusType.equals(paramStatusType));
	}
	
	
	/** API Name - Edit seller **/

	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression","ExhaustiveRegression" }, priority = 0, dataProvider = "editSeller", dataProviderClass = vmsSellerServiceDP.class)
	public void VMS_editSeller( String description, String enabled, String status,
			String paramStatusCode, String paramStatusMessage,
			String paramStatusType)
	{
		MyntraService service = Myntra.getService(
				ServiceType.ERP_MARKETPLACESELLER,
				APINAME.EDITSELLER, init.Configurations,
				new String[] { description, enabled, status,
						paramStatusCode, paramStatusMessage, paramStatusType },
				PayloadType.JSON, PayloadType.JSON);
		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0=");
		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());

		String responseStatusCode = req.respvalidate
				.NodeValue(
						"sellerResponse.status.statusCode",
						true).replaceAll("\"", "").trim();
		String responseStatusMessage = req.respvalidate
				.NodeValue(
						"sellerResponse.status.statusMessage",
						true).replaceAll("\"", "").trim();
		String responseStatusType = req.respvalidate
				.NodeValue(
						"sellerResponse.status.statusType",
						true).replaceAll("\"", "").trim();

		AssertJUnit.assertTrue(
				"Unable to Edit seller , Expected: <"
						+ paramStatusCode + "> but Actual: <"
						+ responseStatusCode + ">",
				responseStatusCode.equals(paramStatusCode));

		AssertJUnit.assertTrue(
				"Unable to Edit seller , Expected: <"
						+ paramStatusMessage + "> but Actual: <"
						+ responseStatusMessage + ">",
				responseStatusMessage.equals(paramStatusMessage));

		AssertJUnit.assertTrue(
				"Unable to Edit seller , Expected: <"
						+ paramStatusType + "> but Actual: <"
						+ responseStatusType + ">",
				responseStatusType.equals(paramStatusType));
	}

	/**API Name - Add seller Finance Information**/
	
	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression","ExhaustiveRegression" }, priority = 0, dataProvider = "addSellerFinancialInformation", dataProviderClass = vmsSellerServiceDP.class)
	public void VMS_addSellerFinanceInformation(String accountId, String enabled, String sellerEntry,
			String paramStatusCode, String paramStatusMessage,
			String paramStatusType)
	{
		MyntraService service = Myntra.getService(
				ServiceType.ERP_MARKETPLACESELLER,
				APINAME.AddSELLERFINANCIALINFORMATION, init.Configurations,
				new String[] { accountId, enabled, sellerEntry,
						paramStatusCode, paramStatusMessage, paramStatusType },
				PayloadType.JSON, PayloadType.JSON);
		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0=");
		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());

		String responseStatusCode = req.respvalidate
				.NodeValue(
						"SellerFinanceInformationResponse.status.statusCode",
						true).replaceAll("\"", "").trim();
		String responseStatusMessage = req.respvalidate
				.NodeValue(
						"SellerFinanceInformationResponse.status.statusMessage",
						true).replaceAll("\"", "").trim();
		String responseStatusType = req.respvalidate
				.NodeValue(
						"SellerFinanceInformationResponse.status.statusType",
						true).replaceAll("\"", "").trim();

		AssertJUnit.assertTrue(
				"Unable to Edit seller , Expected: <"
						+ paramStatusCode + "> but Actual: <"
						+ responseStatusCode + ">",
				responseStatusCode.equals(paramStatusCode));

		AssertJUnit.assertTrue(
				"Unable to Edit seller , Expected: <"
						+ paramStatusMessage + "> but Actual: <"
						+ responseStatusMessage + ">",
				responseStatusMessage.equals(paramStatusMessage));

		AssertJUnit.assertTrue(
				"Unable to Edit seller , Expected: <"
						+ paramStatusType + "> but Actual: <"
						+ responseStatusType + ">",
				responseStatusType.equals(paramStatusType));
	}
	
	
	
/**API Name - Add Brand(one) to a particular seller**/
	
	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression","ExhaustiveRegression" }, priority = 0, dataProvider = "addBrandToSeller", dataProviderClass = vmsSellerServiceDP.class)
	public void VMS_addBrandToSeller(String brandId,
			String brandName, String enabled, String id,
			String paramStatusCode, String paramStatusMessage,
			String paramStatusType)
	{
		MyntraService service = Myntra.getService(
				ServiceType.ERP_MARKETPLACESELLER,
				APINAME.ADDBRANDTOSELLER, init.Configurations,
				new String[] { brandId, brandName, enabled, id, 
						paramStatusCode, paramStatusMessage, paramStatusType },
				PayloadType.JSON, PayloadType.JSON);
		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0=");
		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());

		String responseStatusCode = req.respvalidate
				.NodeValue(
						"sellerBrandResponse.status.statusCode",
						true).replaceAll("\"", "").trim();
		String responseStatusMessage = req.respvalidate
				.NodeValue(
						"sellerBrandResponse.status.statusMessage",
						true).replaceAll("\"", "").trim();
		String responseStatusType = req.respvalidate
				.NodeValue(
						"sellerBrandResponse.status.statusType",
						true).replaceAll("\"", "").trim();

		AssertJUnit.assertTrue(
				"Unable to Edit seller , Expected: <"
						+ paramStatusCode + "> but Actual: <"
						+ responseStatusCode + ">",
				responseStatusCode.equals(paramStatusCode));

		AssertJUnit.assertTrue(
				"Unable to Edit seller , Expected: <"
						+ paramStatusMessage + "> but Actual: <"
						+ responseStatusMessage + ">",
				responseStatusMessage.equals(paramStatusMessage));

		AssertJUnit.assertTrue(
				"Unable to Edit seller , Expected: <"
						+ paramStatusType + "> but Actual: <"
						+ responseStatusType + ">",
				responseStatusType.equals(paramStatusType));
	}
	
/**API Name - Add Brand(one) to a particular seller**/
	
	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression","ExhaustiveRegression" }, priority = 0, dataProvider = "addBrandToSellerNegative", dataProviderClass = vmsSellerServiceDP.class)
	public void VMS_addBrandToSellerNegative(String brandId,
			String brandName, String enabled, String id,
			String paramStatusCode, String paramStatusMessage,
			String paramStatusType)
	{
		MyntraService service = Myntra.getService(
				ServiceType.ERP_MARKETPLACESELLER,
				APINAME.ADDBRANDTOSELLER, init.Configurations,
				new String[] { brandId, brandName, enabled, id, 
						paramStatusCode, paramStatusMessage, paramStatusType },
				PayloadType.JSON, PayloadType.JSON);
		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0=");
		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());

		String responseStatusCode = req.respvalidate
				.NodeValue(
						"sellerBrandResponse.status.statusCode",
						true).replaceAll("\"", "").trim();
		String responseStatusMessage = req.respvalidate
				.NodeValue(
						"sellerBrandResponse.status.statusMessage",
						true).replaceAll("\"", "").trim();
		String responseStatusType = req.respvalidate
				.NodeValue(
						"sellerBrandResponse.status.statusType",
						true).replaceAll("\"", "").trim();

		AssertJUnit.assertTrue(
				"Unable to Edit seller , Expected: <"
						+ paramStatusCode + "> but Actual: <"
						+ responseStatusCode + ">",
				responseStatusCode.equals(paramStatusCode));

		AssertJUnit.assertTrue(
				"Unable to Edit seller , Expected: <"
						+ paramStatusMessage + "> but Actual: <"
						+ responseStatusMessage + ">",
				responseStatusMessage.equals(paramStatusMessage));

		AssertJUnit.assertTrue(
				"Unable to Edit seller , Expected: <"
						+ paramStatusType + "> but Actual: <"
						+ responseStatusType + ">",
				responseStatusType.equals(paramStatusType));
	}
	
/**API Name - Add multiple Brands to a particular seller**/
	
	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression","ExhaustiveRegression" }, priority = 0, dataProvider = "addMultiBrandToSeller", dataProviderClass = vmsSellerServiceDP.class)
	public void VMS_addMultiBrandToSeller(String brandId,
			String brandName, String enabled, String id,String brandId1,
			String brandName1, String enabled1, String id1,String brandId2,
			String brandName2, String enabled2, String id2,
			String paramStatusCode, String paramStatusMessage,
			String paramStatusType)
	{
		MyntraService service = Myntra.getService(
				ServiceType.ERP_MARKETPLACESELLER,
				APINAME.ADDMULTIBRANDTOSELLER, init.Configurations,
				new String[] { brandId, brandName, enabled, id, brandId1, brandName1, enabled1, id1, brandId2, brandName2, enabled2, id2, 
						paramStatusCode, paramStatusMessage, paramStatusType },
				PayloadType.JSON, PayloadType.JSON);
		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0=");
		System.out.println(service.URL);
		System.out.println(service.Payload);
		RequestGenerator req = new RequestGenerator(service, getParam);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());

		String responseStatusCode = req.respvalidate
				.NodeValue(
						"sellerBrandResponse.status.statusCode",
						true).replaceAll("\"", "").trim();
		String responseStatusMessage = req.respvalidate
				.NodeValue(
						"sellerBrandResponse.status.statusMessage",
						true).replaceAll("\"", "").trim();
		String responseStatusType = req.respvalidate
				.NodeValue(
						"sellerBrandResponse.status.statusType",
						true).replaceAll("\"", "").trim();

		AssertJUnit.assertTrue(
				"Unable to Edit seller , Expected: <"
						+ paramStatusCode + "> but Actual: <"
						+ responseStatusCode + ">",
				responseStatusCode.equals(paramStatusCode));

		AssertJUnit.assertTrue(
				"Unable to Edit seller , Expected: <"
						+ paramStatusMessage + "> but Actual: <"
						+ responseStatusMessage + ">",
				responseStatusMessage.equals(paramStatusMessage));

		AssertJUnit.assertTrue(
				"Unable to Edit seller , Expected: <"
						+ paramStatusType + "> but Actual: <"
						+ responseStatusType + ">",
				responseStatusType.equals(paramStatusType));
	}
	
	
	
	/** API Name - Fetch Seller Terms **/
	
	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression","ExhaustiveRegression" }, priority = 0, dataProvider = "fetchSellerTerms", dataProviderClass = vmsSellerServiceDP.class)
	public void VMS_fetchSellerTerms(String paramStatusCode, String paramStatusMessage,
			String paramStatusType)
	{
		MyntraService service = Myntra.getService(
				ServiceType.ERP_MARKETPLACESELLER,
				APINAME.FETCHSELLERTERMS, init.Configurations,
				new String[] { paramStatusCode, paramStatusMessage, paramStatusType },
				PayloadType.JSON, PayloadType.JSON);
		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0=");
		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());

		String responseStatusCode = req.respvalidate
				.NodeValue(
						"sellerTermsResponse.status.statusCode",
						true).replaceAll("\"", "").trim();
		String responseStatusMessage = req.respvalidate
				.NodeValue(
						"sellerTermsResponse.status.statusMessage",
						true).replaceAll("\"", "").trim();
		String responseStatusType = req.respvalidate
				.NodeValue(
						"sellerTermsResponse.status.statusType",
						true).replaceAll("\"", "").trim();

		AssertJUnit.assertTrue(
				"Unable to fetch seller terms, Expected: <"
						+ paramStatusCode + "> but Actual: <"
						+ responseStatusCode + ">",
				responseStatusCode.equals(paramStatusCode));

		AssertJUnit.assertTrue(
				"Unable to fetch seller terms, Expected: <"
						+ paramStatusMessage + "> but Actual: <"
						+ responseStatusMessage + ">",
				responseStatusMessage.equals(paramStatusMessage));

		AssertJUnit.assertTrue(
				"Unable to fetch seller terms, Expected: <"
						+ paramStatusType + "> but Actual: <"
						+ responseStatusType + ">",
				responseStatusType.equals(paramStatusType));
	}
	 
	/** Api Name - Add Slabs To Seller Terms **/
	
	/*@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression","ExhaustiveRegression" }, priority = 0, dataProvider = "addSlabs", dataProviderClass = vmsSellerServiceDP.class)
	public void VMS_addSlabsToSellerTerms(String bottomRange,String percentage,String topRange,String paramStatusCode, String paramStatusMessage,
			String paramStatusType)
	{
		MyntraService service = Myntra.getService(
				ServiceType.ERP_MARKETPLACESELLER,
				APINAME.ADDSLABS, init.Configurations,
				new String[] { bottomRange,percentage,topRange,paramStatusCode, paramStatusMessage, paramStatusType },
				PayloadType.JSON, PayloadType.JSON);
		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0=");
		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());

		String responseStatusCode = req.respvalidate
				.NodeValue(
						"sellerTurnoverIncentivePercentageSlabResponse.status.statusCode",
						true).replaceAll("\"", "").trim();
		String responseStatusMessage = req.respvalidate
				.NodeValue(
						"sellerTurnoverIncentivePercentageSlabResponse.status.statusMessage",
						true).replaceAll("\"", "").trim();
		String responseStatusType = req.respvalidate
				.NodeValue(
						"sellerTurnoverIncentivePercentageSlabResponse.status.statusType",
						true).replaceAll("\"", "").trim();

		AssertJUnit.assertTrue(
				"Unable to add seller terms slabs, Expected: <"
						+ paramStatusCode + "> but Actual: <"
						+ responseStatusCode + ">",
				responseStatusCode.equals(paramStatusCode));

		AssertJUnit.assertTrue(
				"Unable to add seller terms slabs, Expected: <"
						+ paramStatusMessage + "> but Actual: <"
						+ responseStatusMessage + ">",
				responseStatusMessage.equals(paramStatusMessage));

		AssertJUnit.assertTrue(
				"Unable to add seller terms slabs, Expected: <"
						+ paramStatusType + "> but Actual: <"
						+ responseStatusType + ">",
				responseStatusType.equals(paramStatusType));
	}
	
	*//** API Name - Update Slabs **//*
	
	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression",
			"ExhaustiveRegression" }, priority = 0, dataProvider = "updateSlabs", dataProviderClass = vmsSellerServiceDP.class)
	public void VMS_updateSlabsToSellerTerms(String bottomRange,String percentage,String topRange,String paramStatusCode, String paramStatusMessage,
			String paramStatusType)
	{
		MyntraService service = Myntra.getService(
				ServiceType.ERP_MARKETPLACESELLER,
				APINAME.UPDATESLABS, init.Configurations,
				new String[] { bottomRange,percentage,topRange,paramStatusCode, paramStatusMessage, paramStatusType },
				PayloadType.JSON, PayloadType.JSON);
		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0=");
		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());

		String responseStatusCode = req.respvalidate
				.NodeValue(
						"sellerTurnoverIncentivePercentageSlabResponse.status.statusCode",
						true).replaceAll("\"", "").trim();
		String responseStatusMessage = req.respvalidate
				.NodeValue(
						"sellerTurnoverIncentivePercentageSlabResponse.status.statusMessage",
						true).replaceAll("\"", "").trim();
		String responseStatusType = req.respvalidate
				.NodeValue(
						"sellerTurnoverIncentivePercentageSlabResponse.status.statusType",
						true).replaceAll("\"", "").trim();

		AssertJUnit.assertTrue(
				"Unable to update seller terms slabs, Expected: <"
						+ paramStatusCode + "> but Actual: <"
						+ responseStatusCode + ">",
				responseStatusCode.equals(paramStatusCode));

		AssertJUnit.assertTrue(
				"Unable to update seller terms slabs, Expected: <"
						+ paramStatusMessage + "> but Actual: <"
						+ responseStatusMessage + ">",
				responseStatusMessage.equals(paramStatusMessage));

		AssertJUnit.assertTrue(
				"Unable to update seller terms slabs, Expected: <"
						+ paramStatusType + "> but Actual: <"
						+ responseStatusType + ">",
				responseStatusType.equals(paramStatusType));
	}*/
	
	/** API Name - Approve Seller Term **/
	
	/*@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression",
			"ExhaustiveRegression"" }, priority = 0, dataProvider = "sellerTerms")
	public void approveSellerTerms(String paramStatusCode, String paramStatusMessage,
			String paramStatusType)
	{
		MyntraService service = Myntra.getService(
				ServiceType.ERP_MARKETPLACESELLER,
				APINAME.APPROVESELLERTERM, init.Configurations,
				new String[] { paramStatusCode, paramStatusMessage, paramStatusType },
				PayloadType.JSON, PayloadType.JSON);
		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0=");
		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());

		String responseStatusCode = req.respvalidate
				.NodeValue(
						"sellerTermsResponse.status.statusCode",
						true).replaceAll("\"", "").trim();
		String responseStatusMessage = req.respvalidate
				.NodeValue(
						"sellerTermsResponse.status.statusMessage",
						true).replaceAll("\"", "").trim();
		String responseStatusType = req.respvalidate
				.NodeValue(
						"sellerTermsResponse.status.statusType",
						true).replaceAll("\"", "").trim();

		AssertJUnit.assertTrue(
				"Unable to approve seller terms, Expected: <"
						+ paramStatusCode + "> but Actual: <"
						+ responseStatusCode + ">",
				responseStatusCode.equals(paramStatusCode));

		AssertJUnit.assertTrue(
				"Unable to approve seller terms, Expected: <"
						+ paramStatusMessage + "> but Actual: <"
						+ responseStatusMessage + ">",
				responseStatusMessage.equals(paramStatusMessage));

		AssertJUnit.assertTrue(
				"Unable to approve seller terms, Expected: <"
						+ paramStatusType + "> but Actual: <"
						+ responseStatusType + ">",
				responseStatusType.equals(paramStatusType));
	}
	*/
	
	/** API Name - Fetch all Seller Item **/
	
	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression",
			"ExhaustiveRegression" }, priority = 0, dataProvider = "fetchAllSellerItem", dataProviderClass = vmsSellerServiceDP.class)
	public void VMS_fetchAllSellerItem(String paramStatusCode, String paramStatusMessage,
			String paramStatusType)
	{
		MyntraService service = Myntra.getService(
				ServiceType.ERP_MARKETPLACESELLER,
				APINAME.FETCHALLSELLERITEM, init.Configurations,
				new String[] { paramStatusCode, paramStatusMessage, paramStatusType },
				PayloadType.JSON, PayloadType.JSON);
		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0=");
		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());

		String responseStatusCode = req.respvalidate
				.NodeValue(
						"sellerItemMasterResponse.status.statusCode",
						true).replaceAll("\"", "").trim();
		String responseStatusMessage = req.respvalidate
				.NodeValue(
						"sellerItemMasterResponse.status.statusMessage",
						true).replaceAll("\"", "").trim();
		String responseStatusType = req.respvalidate
				.NodeValue(
						"sellerItemMasterResponse.status.statusType",
						true).replaceAll("\"", "").trim();

		AssertJUnit.assertTrue(
				"Unable to fetch Partner item, Expected: <"
						+ paramStatusCode + "> but Actual: <"
						+ responseStatusCode + ">",
				responseStatusCode.equals(paramStatusCode));

		AssertJUnit.assertTrue(
				"Unable to fetch Partner item, Expected: <"
						+ paramStatusMessage + "> but Actual: <"
						+ responseStatusMessage + ">",
				responseStatusMessage.equals(paramStatusMessage));

		AssertJUnit.assertTrue(
				"Unable to fetch Partner item, Expected: <"
						+ paramStatusType + "> but Actual: <"
						+ responseStatusType + ">",
				responseStatusType.equals(paramStatusType));
	}
	
	
/** API Name - SELLER ITEM MASTER BY SELLER ID **/
	
	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression",
			"ExhaustiveRegression" }, priority = 0, dataProvider = "fetchAllSellerItem", dataProviderClass = vmsSellerServiceDP.class)
	public void VMS_sellerItemBySellerId(String paramStatusCode, String paramStatusMessage,
			String paramStatusType)
	{
		MyntraService service = Myntra.getService(
				ServiceType.ERP_MARKETPLACESELLER,
				APINAME.SELLERITEMBYSELLER, init.Configurations,
				new String[] { paramStatusCode, paramStatusMessage, paramStatusType },
				PayloadType.JSON, PayloadType.JSON);
		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0=");
		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());

		String responseStatusCode = req.respvalidate
				.NodeValue(
						"sellerItemMasterResponse.status.statusCode",
						true).replaceAll("\"", "").trim();
		String responseStatusMessage = req.respvalidate
				.NodeValue(
						"sellerItemMasterResponse.status.statusMessage",
						true).replaceAll("\"", "").trim();
		String responseStatusType = req.respvalidate
				.NodeValue(
						"sellerItemMasterResponse.status.statusType",
						true).replaceAll("\"", "").trim();

		AssertJUnit.assertTrue(
				"Unable to fetch Partner item, Expected: <"
						+ paramStatusCode + "> but Actual: <"
						+ responseStatusCode + ">",
				responseStatusCode.equals(paramStatusCode));

		AssertJUnit.assertTrue(
				"Unable to fetch Partner item, Expected: <"
						+ paramStatusMessage + "> but Actual: <"
						+ responseStatusMessage + ">",
				responseStatusMessage.equals(paramStatusMessage));

		AssertJUnit.assertTrue(
				"Unable to fetch Partner item, Expected: <"
						+ paramStatusType + "> but Actual: <"
						+ responseStatusType + ">",
				responseStatusType.equals(paramStatusType));
	}

/** API Name - SELLER ITEM MASTER BY SELLER ID AND SKU CODE **/
	
	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression",
			"ExhaustiveRegression" }, priority = 0, dataProvider = "fetchAllSellerItem", dataProviderClass = vmsSellerServiceDP.class)
	public void VMS_sellerItemBySellerSku(String paramStatusCode, String paramStatusMessage,
			String paramStatusType)
	{
		MyntraService service = Myntra.getService(
				ServiceType.ERP_MARKETPLACESELLER,
				APINAME.SELLERITEMBYSELLERSKU, init.Configurations,
				new String[] { paramStatusCode, paramStatusMessage, paramStatusType },
				PayloadType.JSON, PayloadType.JSON);
		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0=");
		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());

		String responseStatusCode = req.respvalidate
				.NodeValue(
						"sellerItemMasterResponse.status.statusCode",
						true).replaceAll("\"", "").trim();
		String responseStatusMessage = req.respvalidate
				.NodeValue(
						"sellerItemMasterResponse.status.statusMessage",
						true).replaceAll("\"", "").trim();
		String responseStatusType = req.respvalidate
				.NodeValue(
						"sellerItemMasterResponse.status.statusType",
						true).replaceAll("\"", "").trim();

		AssertJUnit.assertTrue(
				"Unable to fetch Partner item, Expected: <"
						+ paramStatusCode + "> but Actual: <"
						+ responseStatusCode + ">",
				responseStatusCode.equals(paramStatusCode));

		AssertJUnit.assertTrue(
				"Unable to fetch Partner item, Expected: <"
						+ paramStatusMessage + "> but Actual: <"
						+ responseStatusMessage + ">",
				responseStatusMessage.equals(paramStatusMessage));

		AssertJUnit.assertTrue(
				"Unable to fetch Partner item, Expected: <"
						+ paramStatusType + "> but Actual: <"
						+ responseStatusType + ">",
				responseStatusType.equals(paramStatusType));
	}
	
	@Test(groups = { "Regression" }, priority = 0, dataProvider = "bulkUploadSellerItem", dataProviderClass = vmsSellerServiceDP.class, description = "1.Bulk Upload seller item master \n 2.Giving MyntraSkuCode and SellerSkuCode Different \n 3.Giving MyntraSkuCode and SellerSkuCode Same ")
	public void VMS_bulkUploadSellerItem(String myntraSkuCode,String sellerBarcode,String sellerId,String sellerSkuCode,String styleId,String myntraSkuCode1,String sellerBarcode1,String sellerId1,String sellerSkuCode1,String styleId1,String expectedResult) throws SQLException {
		
		deleteSellerItem();
		MyntraService service = Myntra.getService(
				ServiceType.ERP_MARKETPLACESELLER, APINAME.BULKUPLOADSELLERITEM,
				init.Configurations, new String[] {  myntraSkuCode, sellerBarcode,sellerId, sellerSkuCode, styleId, myntraSkuCode1, sellerBarcode1,sellerId1, sellerSkuCode1, styleId },  PayloadType.JSON,PayloadType.JSON);
		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0=");
		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam);
		String res = req.respvalidate.returnresponseasstring();
		System.out.println(res);
		AssertJUnit.assertEquals(200, req.response.getStatus());
		APIUtilities.validateResponse("json", res, expectedResult);	
	}
	
	public void deleteSellerItem()
			throws SQLException {
		String updateSellerItem = "DELETE from seller_item_master where seller_id = 3";
		DBUtilities.exUpdateQuery(updateSellerItem, "vms");

	}
	
	@Test(groups = { "Regression" }, priority = 0, dataProviderClass = vmsSellerServiceDP.class, dataProvider = "bulkUploadSellerItem1", description = "1.Bulk Upload seller item master \n 2.Add already existing SkuCode")
	public void VMS_bulkUploadSellerItem1(String myntraSkuCode,String sellerBarcode,String sellerId,String sellerSkuCode,String styleId,String myntraSkuCode1,String sellerBarcode1,String sellerId1,String sellerSkuCode1,String styleId1,String expectedResult) throws SQLException {
		
		
		MyntraService service = Myntra.getService(
				ServiceType.ERP_MARKETPLACESELLER, APINAME.BULKUPLOADSELLERITEM,
				init.Configurations, new String[] {   myntraSkuCode, sellerBarcode,sellerId, sellerSkuCode, styleId, myntraSkuCode1, sellerBarcode1,sellerId1, sellerSkuCode1, styleId },  PayloadType.JSON,PayloadType.JSON);
		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0=");
		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam);
		String res = req.respvalidate.returnresponseasstring();
		System.out.println(res);
		AssertJUnit.assertEquals(200, req.response.getStatus());
		APIUtilities.validateResponse("json", res, expectedResult);	
	}
	
	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression",
	"ExhaustiveRegression" }, priority = 0)
		public void VMS_createSellerV2() throws SQLException, UnsupportedEncodingException, JAXBException
			{   
				String name = "Abhijit pati";
				SellerApiHelper sellerServiceHelper = new SellerApiHelper();
				com.myntra.sellers.response.SellerResponse  sellerResponse = sellerServiceHelper.createSeller(name);
				Assert.assertEquals(sellerResponse.getStatus().getStatusCode() , 1625);
				Assert.assertEquals(sellerResponse.getStatus().getStatusType().toString(), "SUCCESS");
				Long id= sellerResponse.getData().get(0).getId();
				System.out.println("SellerId: "+id);
				deleteSeller(id.toString());
			}
	
}
