package com.myntra.apiTests.erpservices.partners.Tests;

import com.myntra.apiTests.erpservices.partners.VIMHelper;
import com.myntra.apiTests.erpservices.partners.VMSHelper;
import com.myntra.apiTests.erpservices.partners.dp.VendorItemMasterDP;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.boromir.DBUtilities;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.test.commons.testbase.BaseTest;
import com.myntra.vms.client.code.utils.CommercialType;
import com.myntra.vms.client.response.VendorItemMasterResponse;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.sql.SQLException;

public class VendorItemMasterServices extends BaseTest {
	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(VendorItemMasterServices.class);
	VMSHelper vmsHelper = new VMSHelper();
	APIUtilities apiUtil = new APIUtilities();

	static Long Id;
	static Long vendorId;

	@Test(groups = { "vim", "Smoke",
			"Regression" }, priority = 0, dataProviderClass = VendorItemMasterDP.class, dataProvider = "createVim", description = "1.Create VIM \n 2. Validates it got created successfully")
	public void VIM_create(String vendorId, String skuId, String vendorSkuCode, String skuCode, String styleId,
			String brand, CommercialType type1, CommercialType type2, CommercialType type3, String mrp,
			String unitPrice)
			throws SQLException, JAXBException, JsonParseException, JsonMappingException, IOException {
		VIMHelper vimHelper = new VIMHelper();
		VendorItemMasterResponse vendorItemMasterResponse = vimHelper.createVIM(vendorId, skuId, vendorSkuCode, skuCode,
				styleId, brand, type1, type2, type3, mrp, unitPrice);
		Assert.assertEquals(vendorItemMasterResponse.getStatus().getStatusCode(), 3005);
		Assert.assertEquals(vendorItemMasterResponse.getStatus().getStatusMessage().toString(),
				"Vendor Item Master added successfully");
		Assert.assertEquals(vendorItemMasterResponse.getStatus().getStatusType().toString(), "SUCCESS");
		Assert.assertEquals(vendorItemMasterResponse.getData().get(0).getMessage().toString(),
				"Vendor item added successfully.");
		Long id = vendorItemMasterResponse.getData().get(0).getId();
		System.out.println("Get Id: " + id);
		Id = id;

	}

	@Test(groups = { "vim",
			"Regression" }, dataProviderClass = VendorItemMasterDP.class, dataProvider = "createVimInvalidVendorId", description = "1.Create VIM with invalid Vendor Id \n 2. Validates it should not got created successfully")
	public void VIM_createWithInvalidVendorId(String vendorId, String skuId, String vendorSkuCode, String skuCode,
			String styleId, String brand, CommercialType type1, CommercialType type2, CommercialType type3, String mrp,
			String unitPrice)
			throws SQLException, JAXBException, JsonParseException, JsonMappingException, IOException {
		VIMHelper vimHelper = new VIMHelper();
		VendorItemMasterResponse vendorItemMasterResponse = vimHelper.createVIM(vendorId, skuId, vendorSkuCode, skuCode,
				styleId, brand, type1, type2, type3, mrp, unitPrice);
		Assert.assertEquals(vendorItemMasterResponse.getStatus().getStatusCode(), 2022);
		Assert.assertEquals(vendorItemMasterResponse.getStatus().getStatusMessage().toString(),
				"This vendorId doesn't exist in the system.");
		Assert.assertEquals(vendorItemMasterResponse.getStatus().getStatusType().toString(), "ERROR");

	}

	@Test(groups = { "vim",
			"Regression" }, dataProviderClass = VendorItemMasterDP.class, dataProvider = "createVimInvalidBrand", description = "1.Create VIM with Invalid Brand \n 2. Validates it should not got created successfully")
	public void VIM_createWithInvalidBrand(String vendorId, String skuId, String vendorSkuCode, String skuCode,
			String styleId, String brand, CommercialType type1, CommercialType type2, CommercialType type3, String mrp,
			String unitPrice)
			throws SQLException, JAXBException, JsonParseException, JsonMappingException, IOException {
		VIMHelper vimHelper = new VIMHelper();
		VendorItemMasterResponse vendorItemMasterResponse = vimHelper.createVIM(vendorId, skuId, vendorSkuCode, skuCode,
				styleId, brand, type1, type2, type3, mrp, unitPrice);
		Assert.assertEquals(vendorItemMasterResponse.getStatus().getStatusCode(), 2023);
		Assert.assertEquals(vendorItemMasterResponse.getStatus().getStatusMessage().toString(),
				"This brand doesn't exist in the system for the supplied vendor.");
		Assert.assertEquals(vendorItemMasterResponse.getStatus().getStatusType().toString(), "ERROR");

	}

	@Test(groups = { "vim",
			"Regression" }, priority = 1, dataProviderClass = VendorItemMasterDP.class, dataProvider = "editVim", description = "1.Edit VIM \n 2. Validates it got edited successfully")
	public void VIM_edit(String vendorId, String skuId, String vendorSkuCode, String skuCode, String styleId,
			String brand, CommercialType type, String mrp, String unitPrice)
			throws SQLException, JAXBException, JsonParseException, JsonMappingException, IOException {
		VIMHelper vimHelper = new VIMHelper();
		VendorItemMasterResponse vendorItemMasterResponse = vimHelper.editVIM(vendorId, vendorId, skuId, vendorSkuCode,
				skuCode, styleId, brand, type, mrp, unitPrice);
		Assert.assertEquals(vendorItemMasterResponse.getStatus().getStatusCode(), 3010);
		Assert.assertEquals(vendorItemMasterResponse.getStatus().getStatusMessage().toString(),
				"Vendor item updated successfully.");
		Assert.assertEquals(vendorItemMasterResponse.getStatus().getStatusType().toString(), "SUCCESS");
		Assert.assertEquals(vendorItemMasterResponse.getData().get(0).getMessage().toString(),
				"Vendor item updated successfully.");

	}

	/*
	 * @Test(groups = { "Smoke","Regression", }, priority = 0, dataProviderClass =
	 * VendorItemMasterDP.class, dataProvider = "editVim") public void
	 * VIM_bulk(String brand,String enabled,String skuCode,String skuId,String
	 * styleId,String vendorId,String vendorSkuCode) throws SQLException,
	 * JAXBException, JsonParseException, JsonMappingException, IOException {
	 * 
	 * 
	 * 
	 * }
	 */

	@Test(groups = { "vim",
			"Regression" }, priority = 2, dataProviderClass = VendorItemMasterDP.class, dataProvider = "commercialType", description = "1.Search by Commercial Type and Sku Code")
	public void VIM_commercialTypeAndSkuCode(String vendorId)
			throws SQLException, JAXBException, JsonParseException, JsonMappingException, IOException {

		VIMHelper vimHelper = new VIMHelper();
		String skuCode = vmsHelper.getVendorSKUCodeFromVIM(vendorId);
		String commType = vmsHelper.getCommercialTypeFromVIM(vendorId);
		VendorItemMasterResponse vendorItemMasterResponse = vimHelper.commercialTypeAndskuCode(vendorId.toString(),
				skuCode, commType);
		Assert.assertEquals(vendorItemMasterResponse.getStatus().getStatusCode(), 3006);
		Assert.assertEquals(vendorItemMasterResponse.getStatus().getStatusMessage().toString(),
				"Vendor Item Master successfully retrieved");
		Assert.assertEquals(vendorItemMasterResponse.getStatus().getStatusType().toString(), "SUCCESS");
		Assert.assertEquals(vendorItemMasterResponse.getData().get(0).getSkuCode().toString(), skuCode);

	}

	@Test(groups = { "vim",
			"Regression" }, dataProviderClass = VendorItemMasterDP.class, dataProvider = "commercialTypeNegative", description = "1.Search by invalid Commercial Type and Sku Code")
	public void VIM_commercialTypeAndSkuCodeNegative(String vendorId)
			throws SQLException, JAXBException, JsonParseException, JsonMappingException, IOException {

		VIMHelper vimHelper = new VIMHelper();
		VendorItemMasterResponse vendorItemMasterResponse = vimHelper.commercialTypeAndskuCode(vendorId.toString(),
				"ABCD", "JIT");
		Assert.assertEquals(vendorItemMasterResponse.getStatus().getStatusCode(), 3008);
		Assert.assertEquals(vendorItemMasterResponse.getStatus().getStatusMessage().toString(),
				"Vendor Item Master not found");
		Assert.assertEquals(vendorItemMasterResponse.getStatus().getStatusType().toString(), "SUCCESS");

	}

	@Test(groups = { "vim",
			"Regression" }, priority = 3, dataProviderClass = VendorItemMasterDP.class, dataProvider = "commercialType", description = "1.Search by Commercial Type ")
	public void VIM_commercialType(String vendorId)
			throws SQLException, JAXBException, JsonParseException, JsonMappingException, IOException {

		VIMHelper vimHelper = new VIMHelper();
		VendorItemMasterResponse vendorItemMasterResponse = vimHelper.commercialType(vendorId.toString());
		Assert.assertEquals(vendorItemMasterResponse.getStatus().getStatusCode(), 3006);
		Assert.assertEquals(vendorItemMasterResponse.getStatus().getStatusMessage().toString(),
				"Vendor Item Master successfully retrieved");
		Assert.assertEquals(vendorItemMasterResponse.getStatus().getStatusType().toString(), "SUCCESS");
		/*Assert.assertEquals(vendorItemMasterResponse.getData().get(0).getSkuCode().toString(),
				vmsHelper.getVendorSKUCodeFromVIM(vendorId));*/

	}

	@Test(groups = { "vim",
			"Regression" }, dataProviderClass = VendorItemMasterDP.class, dataProvider = "commercialTypeNegative", description = "1.Search by Invalid Commercial Type")
	public void VIM_commercialTypeNegative(String vendorId)
			throws SQLException, JAXBException, JsonParseException, JsonMappingException, IOException {

		VIMHelper vimHelper = new VIMHelper();
		VendorItemMasterResponse vendorItemMasterResponse = vimHelper.commercialType(vendorId.toString());
		Assert.assertEquals(vendorItemMasterResponse.getStatus().getStatusCode(), 3008);
		Assert.assertEquals(vendorItemMasterResponse.getStatus().getStatusMessage().toString(),
				"Vendor Item Master not found");
		Assert.assertEquals(vendorItemMasterResponse.getStatus().getStatusType().toString(), "SUCCESS");

	}

	@Test(groups = { "vim",
			"Regression" }, priority = 4, dataProviderClass = VendorItemMasterDP.class, dataProvider = "commercialType", description = "1.Search by Sku Code")
	public void VIM_SkuCode(String vendorId)
			throws SQLException, JAXBException, JsonParseException, JsonMappingException, IOException {
		VIMHelper vimHelper = new VIMHelper();
		VMSHelper vmsHelper = new VMSHelper();
		VendorItemMasterResponse vendorItemMasterResponse = vimHelper.skuCode(vendorId.toString());
		Assert.assertEquals(vendorItemMasterResponse.getStatus().getStatusCode(), 3006);
		Assert.assertEquals(vendorItemMasterResponse.getStatus().getStatusMessage().toString(),
				"Vendor Item Master successfully retrieved");
		Assert.assertEquals(vendorItemMasterResponse.getStatus().getStatusType().toString(), "SUCCESS");
		String skuCode = vmsHelper.getVendorSKUCodeFromVIM(vendorId);
		Assert.assertEquals(vendorItemMasterResponse.getData().get(0).getSkuCode().toString(), skuCode);

	}

	@Test(groups = { "vim",
			"Regression" }, dataProviderClass = VendorItemMasterDP.class, dataProvider = "commercialTypeNegative", description = "1.Search by invalid Sku Code")
	public void VIM_SkuCodeNegative(String vendorId)
			throws SQLException, JAXBException, JsonParseException, JsonMappingException, IOException {
		VIMHelper vimHelper = new VIMHelper();
		VendorItemMasterResponse vendorItemMasterResponse = vimHelper.skuCode(vendorId.toString());
		Assert.assertEquals(vendorItemMasterResponse.getStatus().getStatusCode(), 3008);
		Assert.assertEquals(vendorItemMasterResponse.getStatus().getStatusMessage().toString(),
				"Vendor Item Master not found");
		Assert.assertEquals(vendorItemMasterResponse.getStatus().getStatusType().toString(), "SUCCESS");

	}

	@Test(groups = { "vim",
			"Regression" }, priority = 5, dataProviderClass = VendorItemMasterDP.class, dataProvider = "commercialType", description = "1.Get VIM by Style Ids")
	public void VIM_getByStyleIds(String vendorId)
			throws SQLException, JAXBException, JsonParseException, JsonMappingException, IOException {
		VIMHelper vimHelper = new VIMHelper();
		VendorItemMasterResponse vendorItemMasterResponse = vimHelper.getByStyleIds(vendorId);
		Assert.assertEquals(vendorItemMasterResponse.getStatus().getStatusCode(), 3006);
		Assert.assertEquals(vendorItemMasterResponse.getStatus().getStatusMessage().toString(),
				"Vendor Item Master successfully retrieved");
		Assert.assertEquals(vendorItemMasterResponse.getStatus().getStatusType().toString(), "SUCCESS");
		Assert.assertEquals(vendorItemMasterResponse.getData().get(0).getStyleId().toString(),
				vmsHelper.getStyleIdFromVIM(vendorId));

	}

	@Test(groups = { "vim",
			"Regression" }, priority = 6, dataProviderClass = VendorItemMasterDP.class, dataProvider = "commercialType", description = "1.Get VIM by Vendor Ids")
	public void VIM_getByVendorId(String vendorId)
			throws SQLException, JAXBException, JsonParseException, JsonMappingException, IOException {
		VIMHelper vimHelper = new VIMHelper();
		VendorItemMasterResponse vendorItemMasterResponse = vimHelper.getVimByVendor(vendorId);
		Assert.assertEquals(vendorItemMasterResponse.getStatus().getStatusCode(), 3006);
		Assert.assertEquals(vendorItemMasterResponse.getStatus().getStatusMessage().toString(),
				"Vendor Item Master successfully retrieved");
		Assert.assertEquals(vendorItemMasterResponse.getStatus().getStatusType().toString(), "SUCCESS");

	}

	@Test(groups = { "vim",
			"Regression" }, priority = 7, dataProviderClass = VendorItemMasterDP.class, dataProvider = "commercialType", description = "1.Get VIM by Ids")
	public void VIM_getByVendorItemMasterId(String vendorId)
			throws SQLException, JAXBException, JsonParseException, JsonMappingException, IOException {
		VIMHelper vimHelper = new VIMHelper();
		VendorItemMasterResponse vendorItemMasterResponse = vimHelper.getVimById(vendorId);
		Assert.assertEquals(vendorItemMasterResponse.getStatus().getStatusCode(), 3006);
		Assert.assertEquals(vendorItemMasterResponse.getStatus().getStatusMessage().toString(),
				"Vendor Item Master successfully retrieved");
		Assert.assertEquals(vendorItemMasterResponse.getStatus().getStatusType().toString(), "SUCCESS");
		Assert.assertEquals(vendorItemMasterResponse.getData().get(0).getVendorId().toString(), vendorId);
	}

	@BeforeClass(alwaysRun = true)
	public void testBeforeClass() throws SQLException {
		String vendorId = null;
		deleteVendorItemMaster(vendorId);
	}

	public void deleteVendorItemMaster(String vendorId) throws SQLException {
		String updateVendorItem = "DELETE from vendor_item_master where vendor_id =" + 960 + "";
		DBUtilities.exUpdateQuery(updateVendorItem, "vms");
	}

}
