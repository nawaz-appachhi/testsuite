package com.myntra.apiTests.portalservices.cms;

import org.apache.log4j.Logger;
import org.testng.AssertJUnit;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.jayway.jsonpath.JsonPath;
import com.myntra.lordoftherings.Initialize;
import com.myntra.test.commons.service.Svc;

/**
 * @author Vaishali Behere
 * 
 */

public class DIYTest_2AT extends SheetHelper {

	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(DIYTest_2AT.class);
	// DO NOT Change vendor_id as these tests are run in production and this is
	// the only test_vendor available
	String vendor_id = "1082";
	String studio = "siddharth_pruthi_photography_jaipur";
	String source = "vendor";
	String lotName = "test_vendor_lot";

	String file1 = System.getProperty("user.dir")
			+ "/Data/payloads/EXCELSHEET/cms/diy_2AT_SameBrandSameBU.xlsx";
	String file2 = System.getProperty("user.dir")
			+ "/Data/payloads/EXCELSHEET/cms/diy_2AT_SameBrandDifferentBU.xlsx";
	String file3 = System.getProperty("user.dir")
			+ "/Data/payloads/EXCELSHEET/cms/diy_2AT_SameBrandNoBU.xlsx";
	String file4 = System.getProperty("user.dir")
			+ "/Data/payloads/EXCELSHEET/cms/diy_2AT_DifferentBrand.xlsx";

	@BeforeMethod(alwaysRun = true)
	public void openHome() {
		log.info("Entry");
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 1, description = "Verify successful upload for same brand and same BU")
	public void verifyDIYUpload_SameBrandSameBU() throws Exception {
		String brand = "Basics";
		String BU = "Men's Casual";
		String productCount = "5";
		Svc service = uploadDIYSheet(file1, studio, source, lotName);
		AssertJUnit.assertEquals(200, service.getResponseStatus());
		String responseAsString = service.getResponseBody();
		AssertJUnit.assertEquals(BU,
				JsonPath.read(responseAsString, "$.data.businessUnit")
						.toString());
		AssertJUnit.assertEquals(brand,
				JsonPath.read(responseAsString, "$.data.brandName").toString());
		assertValidateSuccessEntries(responseAsString);
		AssertJUnit.assertEquals("sheetValidatorErrorEntries not empty", "[]",
				JsonPath.read(responseAsString,
						"$.data.sheetValidatorErrorEntries[*]").toString());
		assertDexterTaskEntries(responseAsString, studio, BU, brand,
				productCount, source, lotName);
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 2, description = "Verify upload failure for same brand and different BU")
	public void verifyDIYUpload_SameBrandDifferentBU() throws Exception {
		String brand = "Wrangler";
		Svc service = uploadDIYSheet(file2, studio, source, lotName);
		AssertJUnit.assertEquals(200, service.getResponseStatus());
		String responseAsString = service.getResponseBody();
		AssertJUnit.assertEquals(brand,
				JsonPath.read(responseAsString, "$.data.brandName").toString());
		assertValidateErrorEntries(responseAsString,
				ErrorEntryType.BU_MISMATCH_ERROR1, new String[] {"Wrangler-Shirts-Women", "Wrangler-Tshirts-Men"});
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 3, description = "Verify upload failure for same brand no BU")
	public void verifyDIYUpload_SameBrandNoBU() throws Exception {
		String brand = "Biba";
		Svc service = uploadDIYSheet(file3, studio, source, lotName);
		AssertJUnit.assertEquals(200, service.getResponseStatus());
		String responseAsString = service.getResponseBody();
		AssertJUnit.assertEquals(brand,
				JsonPath.read(responseAsString, "$.data.brandName").toString());
		assertValidateErrorEntries(responseAsString,
				ErrorEntryType.BU_MISMATCH_ERROR2, new String[] {"Biba-Tshirts-Men",  "Biba-Shirts-Men"});
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 4, description = "Verify upload failure for different brand same BU")
	public void verifyDIYUpload_DifferentBrand() throws Exception {
		String businessUnit = "Sports";
		Svc service = uploadDIYSheet(file4, studio, source, lotName);
		AssertJUnit.assertEquals(200, service.getResponseStatus());
		String responseAsString = service.getResponseBody();
		AssertJUnit.assertEquals(businessUnit,
				JsonPath.read(responseAsString, "$.data.businessUnit")
						.toString());
		assertValidateErrorEntries(responseAsString,
				ErrorEntryType.BRAND_MISMATCH_ERROR, null);
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
