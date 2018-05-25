package com.myntra.apiTests.portalservices.cms;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.jayway.jsonpath.JsonPath;
import com.myntra.apiTests.common.APINAME;
import com.myntra.apiTests.common.ServiceType;
import com.myntra.apiTests.portalservices.commons.CommonUtils;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.Toolbox;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.test.commons.service.Svc;

/**
 * @author Vaishali Behere
 * 
 */

public class DIYTest_1AT extends SheetHelper {

	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(DIYTest_1AT.class);
	// DO NOT Change vendor_id as these tests are run in production and this is
	// the only test_vendor available
	String vendor_id = "1082";
	String studio = "siddharth_pruthi_photography_jaipur";
	String source = "vendor";
	String lotName = "test_vendor_lot";

	String file1 = System.getProperty("user.dir")
			+ "/Data/payloads/EXCELSHEET/cms/diy_1AT_SameBrandSameBU.xlsx";
	String file2 = System.getProperty("user.dir")
			+ "/Data/payloads/EXCELSHEET/cms/diy_1AT_SameBrandDifferentBU.xlsx";
	String file3 = System.getProperty("user.dir")
			+ "/Data/payloads/EXCELSHEET/cms/diy_1AT_SameBrandOneGenderWithNoBU.xlsx";
	String file4 = System.getProperty("user.dir")
			+ "/Data/payloads/EXCELSHEET/cms/diy_1AT_SameBrandNoBU.xlsx";
	String file5 = System.getProperty("user.dir")
			+ "/Data/payloads/EXCELSHEET/cms/diy_1AT_DifferentBrand.xlsx";
	String file6 = System.getProperty("user.dir")
			+ "/Data/payloads/EXCELSHEET/cms/diy_1AT_EmptySheet.xlsx";
	String file7 = System.getProperty("user.dir")
			+ "/Data/payloads/EXCELSHEET/cms/diy_1AT_1row.xlsx";
	String file8 = System.getProperty("user.dir")
			+ "/Data/payloads/EXCELSHEET/cms/diy_1AT_2rows.xlsx";
	String file9 = System.getProperty("user.dir")
			+ "/Data/payloads/EXCELSHEET/cms/diy_SyncErrors.xlsx";

	@BeforeMethod(alwaysRun = true)
	public void openHome() {
		log.info("Entry");
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 1, description = "Verify successful upload for same brand and same BU")
	public void verifyDIYUpload_SameBrandSameBU() throws Exception {
		String brand = "Puma";
		String BU = "Sports";
		String productCount = "3";
		Svc service = uploadDIYSheet(file1, studio, source, lotName);
		AssertJUnit.assertEquals(200, service.getResponseStatus());
		String responseAsString = service.getResponseBody();
		AssertJUnit.assertEquals(BU,
				JsonPath.read(responseAsString, "$.data.businessUnit")
						.toString());
		AssertJUnit.assertEquals(brand,
				JsonPath.read(responseAsString, "$.data.brandName").toString());
		assertValidateSuccessEntries(responseAsString);
		AssertJUnit.assertEquals(
				"sheetValidatorErrorEntries not empty",
				"[]",
				JsonPath.read(responseAsString,
						"$.data.sheetValidatorErrorEntries[*]").toString());
		assertDexterTaskEntries(responseAsString, studio, BU, brand,
				productCount, source, lotName);
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 2, description = "Verify upload failure for same brand and different BU")
	public void verifyDIYUpload_SameBrandDifferentBU() throws Exception {
		String brand = "Duke";
		String[] BAG_Combinations = { "Duke-Tshirts-Boys", "Duke-Tshirts-Men" };
		Svc service = uploadDIYSheet(file2, studio, source, lotName);
		AssertJUnit.assertEquals(200, service.getResponseStatus());
		String responseAsString = service.getResponseBody();
	
		AssertJUnit.assertEquals(brand,
				JsonPath.read(responseAsString, "$.data.brandName").toString());
		assertValidateErrorEntries(responseAsString,
				ErrorEntryType.BU_MISMATCH_ERROR1, BAG_Combinations);
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 3, description = "Verify upload failure for same brand and different BU (1 gender without BU)")
	public void verifyDIYUpload_SameBrandAndOneGenderWithNoBU()
			throws Exception {
		String brand = "Arrow";
		Svc service = uploadDIYSheet(file3, studio, source, lotName);
		AssertJUnit.assertEquals(200, service.getResponseStatus());
		String responseAsString = service.getResponseBody();
		
		AssertJUnit.assertEquals(brand,
				JsonPath.read(responseAsString, "$.data.brandName").toString());
		assertValidateErrorEntries(responseAsString,
				ErrorEntryType.BU_MISMATCH_ERROR2,
				new String[] { "Arrow-Tshirts-Women" });
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 4, description = "Verify upload failure for same brand no BU")
	public void verifyDIYUpload_SameBrandNoBU() throws Exception {
		String brand = "Arrow";
		Svc service = uploadDIYSheet(file4, studio, source, lotName);
		AssertJUnit.assertEquals(200, service.getResponseStatus());
		String responseAsString = service.getResponseBody();
		
		AssertJUnit.assertEquals(brand,
				JsonPath.read(responseAsString, "$.data.brandName").toString());
		assertValidateErrorEntries(responseAsString,
				ErrorEntryType.BU_MISMATCH_ERROR2, new String[] {
						"Arrow-Tshirts-Girls", "Arrow-Tshirts-Women" });
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 5, description = "Verify upload failure for different brand same BU")
	public void verifyDIYUpload_DifferentBrand() throws Exception {
		String businessUnit = "Sports";
		Svc service = uploadDIYSheet(file5, studio, source, lotName);
		AssertJUnit.assertEquals(200, service.getResponseStatus());
		String responseAsString = service.getResponseBody();
		
		AssertJUnit.assertEquals(businessUnit,
				JsonPath.read(responseAsString, "$.data.businessUnit")
						.toString());
		assertValidateErrorEntries(responseAsString,
				ErrorEntryType.BRAND_MISMATCH_ERROR, null);
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 6, description = "Verify upload failure error for empty sheet")
	public void verifyDIYUpload_EmptySheet() throws Exception {
		String businessUnit = "Sports";
		String brand = "Puma";
		Svc service = uploadDIYSheet(file6, studio, source, lotName);
		AssertJUnit.assertEquals(200, service.getResponseStatus());
		String responseAsString = service.getResponseBody();
		
		AssertJUnit.assertEquals(businessUnit,
				JsonPath.read(responseAsString, "$.data.businessUnit")
						.toString());
		AssertJUnit.assertEquals(brand,
				JsonPath.read(responseAsString, "$.data.brandName").toString());
		assertValidateErrorEntries(responseAsString,
				ErrorEntryType.EMPTY_SHEET_ERROR, null);
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 7, description = "Verify product count for sheet with only 1 row")
	public void verifyDIYUpload_SheetWithOneRow() throws Exception {
		String brand = "Puma";
		String BU = "Sports";
		String productCount = "1";
		Svc service = uploadDIYSheet(file7, studio, source, lotName);
		AssertJUnit.assertEquals(200, service.getResponseStatus());
		String responseAsString = service.getResponseBody();
		
		AssertJUnit.assertEquals(BU,
				JsonPath.read(responseAsString, "$.data.businessUnit")
						.toString());
		AssertJUnit.assertEquals(brand,
				JsonPath.read(responseAsString, "$.data.brandName").toString());
		assertValidateSuccessEntries(responseAsString);
		AssertJUnit.assertEquals(
				"sheetValidatorErrorEntries not empty",
				"[]",
				JsonPath.read(responseAsString,
						"$.data.sheetValidatorErrorEntries[*]").toString());
		assertDexterTaskEntries(responseAsString, studio, BU, brand,
				productCount, source, lotName);
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 8, description = "Verify product count for sheet with 2 rows")
	public void verifyDIYUpload_SheetWithTwoRows() throws Exception {
		String brand = "Puma";
		String BU = "Sports";
		String productCount = "2";
		Svc service = uploadDIYSheet(file8, studio, source, lotName);
		AssertJUnit.assertEquals(200, service.getResponseStatus());
		String responseAsString = service.getResponseBody();
		
		AssertJUnit.assertEquals(BU,
				JsonPath.read(responseAsString, "$.data.businessUnit")
						.toString());
		AssertJUnit.assertEquals(brand,
				JsonPath.read(responseAsString, "$.data.brandName").toString());
		assertValidateSuccessEntries(responseAsString);
		AssertJUnit.assertEquals(
				"sheetValidatorErrorEntries not empty",
				"[]",
				JsonPath.read(responseAsString,
						"$.data.sheetValidatorErrorEntries[*]").toString());
		assertDexterTaskEntries(responseAsString, studio, BU, brand,
				productCount, source, lotName);
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 9, description = "Verify upload failure error for invalid studio")
	public void verifyDIYUpload_InvalidStudio() throws Exception {
		String businessUnit = "Sports";
		String brand = "Puma";
		Svc service = uploadDIYSheet(file1, "test", source, lotName);
		AssertJUnit.assertEquals(200, service.getResponseStatus());
		String responseAsString = service.getResponseBody();
		
		AssertJUnit.assertEquals(businessUnit,
				JsonPath.read(responseAsString, "$.data.businessUnit")
						.toString());
		AssertJUnit.assertEquals(brand,
				JsonPath.read(responseAsString, "$.data.brandName").toString());
		assertValidateErrorEntries(responseAsString,
				ErrorEntryType.INVALID_STUDIO, null);
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 10, description = "Verify sync diy error messages")
	public void verifyDIYSyncErrorMessages() throws Exception {
		Svc service = uploadDIYSheet(file9, studio, source, lotName);
		AssertJUnit.assertEquals(200, service.getResponseStatus());
		String responseAsString = service.getResponseBody();
		
		ErrorEntryType[] errorEntry = new ErrorEntryType[] {
				ErrorEntryType.BU_PARSE_ERROR, ErrorEntryType.EMPTY_BRAND,
				ErrorEntryType.EMPTY_PRODUCTSIZE, ErrorEntryType.EMPTY_VAN,
				ErrorEntryType.EMPTY_VENDORNAME,
				ErrorEntryType.INVALID_ARTICLE, ErrorEntryType.INVALID_GTIN,
				ErrorEntryType.INVALID_MRP };
		assertDIYSyncErrorEntries(responseAsString, errorEntry, null);
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 11, description = "Validate GTIN has 14 digits in diy response")
	public void validateGTINInDIYResponse() throws Exception {
		String gtinInSheet = "123456";
		Svc service = uploadDIYSheet(file2, studio, source, lotName);
		AssertJUnit.assertEquals(200, service.getResponseStatus());
		String responseAsString = service.getResponseBody();
		
		AssertJUnit.assertEquals("GTIN doesn't contain 14 digits", "00000000"
				+ gtinInSheet,
				JsonPath.read(responseAsString, "$.data.rowDTOList[0].gtin")
						.toString());
	}
	
	@Test(groups = { "e2eCMSRegression" }, priority = 12, description = "Check schema of /diy/allArticleType api")
	public void verifyDIYAllAT() throws Exception {
		RequestGenerator req = e2eRegressionCmsHelper.getRequestObjForCMSForQueryParam(ServiceType.CMS_CATALOG, APINAME.DIY_ALLAT, null);
		AssertJUnit.assertEquals("Status is not 200", 200, req.response.getStatus());
		AssertJUnit.assertEquals("statusMessage is not correct", "Success", JsonPath.read(req.returnresponseasstring(),"$.status.statusMessage").toString());
		AssertJUnit.assertFalse("Total Count is 0", JsonPath.read(req.returnresponseasstring(),"$.status.totalCount").toString().equals("0"));
		List<String> list = JsonPath.read(req.returnresponseasstring(),"$.data[*]");
		AssertJUnit.assertFalse("Data node size is 0", list.size()==0);
	}
	
	@Test(groups = { "e2eCMSRegression" }, priority = 13, description = "Check schema of /diy/allPPStudios api")
	public void verifyDIYAllStudios() throws Exception {
		RequestGenerator req = e2eRegressionCmsHelper.getRequestObjForCMSForQueryParam(ServiceType.CMS_CATALOG, APINAME.DIY_ALLSTUDIOS, null);
		AssertJUnit.assertEquals("Status is not 200", 200, req.response.getStatus());
		AssertJUnit.assertNotNull("name is null", JsonPath.read(req.returnresponseasstring(),"$.data[0].name").toString());
		AssertJUnit.assertNotNull("code is null", JsonPath.read(req.returnresponseasstring(),"$.data[0].code").toString());
		AssertJUnit.assertNotNull("location is null", JsonPath.read(req.returnresponseasstring(),"$.data[0].location").toString());
	}
	
	/** Here first argument is response returned from uploadDIYSheet method **/
	public void assertDexterTaskEntries(String responseAsString, String studio, String bu, String brandName, String productsCount, String source, String lotName ){
		String response = getDIYTaskResponseObject(responseAsString);
		AssertJUnit.assertEquals("Studio name incorrect", studio, JsonPath.read(response, "$.data[0].studio").toString());
		AssertJUnit.assertEquals("BU name incorrect", bu, JsonPath.read(response, "$.data[0].businessUnit").toString());
		AssertJUnit.assertEquals("Brand name incorrect", brandName, JsonPath.read(response, "$.data[0].brandName").toString());
		AssertJUnit.assertEquals("productsCount name incorrect", productsCount, JsonPath.read(response, "$.data[0].productsCount").toString());
		AssertJUnit.assertEquals("source name incorrect", source, JsonPath.read(response, "$.data[0].source").toString());
		AssertJUnit.assertEquals("lotName name incorrect", lotName, JsonPath.read(response, "$.data[0].lotName").toString());
	}
	
}
