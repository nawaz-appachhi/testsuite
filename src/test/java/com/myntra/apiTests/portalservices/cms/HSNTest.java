package com.myntra.apiTests.portalservices.cms;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.javers.common.collections.Arrays;
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

public class HSNTest extends SheetHelper {

	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(HSNTest.class);
	// DO NOT Change vendor_id as these tests are run in production and this is
	// the only test_vendor available
	String vendor_id = "1082";
	String studio = "siddharth_pruthi_photography_jaipur";
	String source = "vendor";
	String lotName = "test_vendor_lot";
	String hsnErrorResponse;

	String file1 = System.getProperty("user.dir")
			+ "/Data/payloads/EXCELSHEET/cms/HSNErrors.xlsx";
	String file2 = System.getProperty("user.dir")
			+ "/Data/payloads/EXCELSHEET/cms/ValidHSN.xlsx";
	
	@BeforeMethod(alwaysRun = true)
	public void openHome() {
		log.info("Entry");
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 1, description = "Verify error message when HSN is empty, string, incorrect and not same for all sku rows")
	public void verifyHSNErrors() throws Exception {
		String nonNumericHSN = "test123";
		String columnName = "vendor HSN";
		String articleType = "Tops";
		String groupId = "1";
		Svc service = uploadDIYSheet(file1, studio, source, lotName);
		AssertJUnit.assertEquals(200, service.getResponseStatus());
		hsnErrorResponse = service.getResponseBody();
		ErrorEntryType[] errorEntry = new ErrorEntryType[] {
				ErrorEntryType.EMPTY_HSN, ErrorEntryType.NONNUMERIC_HSN};//,
				//ErrorEntryType.INVALID_HSN};
		assertDIYSyncErrorEntries(hsnErrorResponse, errorEntry, new String[]{nonNumericHSN});
		errorEntry = new ErrorEntryType[] {
				ErrorEntryType.PROD_LEVEL_VALIDATIONS};
		assertDIYSyncErrorEntries(hsnErrorResponse, errorEntry, new String[]{columnName, articleType, groupId});
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 2, description = "Verify vendorHSN and mytraHSN in response for valid HSN (2,4,6,8 digits)")
	public void verifyStoredHSNInSheetUploadResponse() throws Exception {
		String[] sheetHSN = new String[]{"Blazers.S.42031010.42031010", "Blazers.M.42031010.42031010", "Blazers.L.42031010.42031010", "Blazers.XL.42031010.42031010", "Tops.S.42031010.42031090"};
		List<String> jsonHSN = new ArrayList<String>();
		Svc service = uploadDIYSheet(file2, studio, source, lotName);
		AssertJUnit.assertEquals(200, service.getResponseStatus());
		String responseAsString = service.getResponseBody();
		for(int i=0 ;i<5;i++){
			String sheetName = JsonPath.read(responseAsString, "$.data.rowDTOList["+i+"].sheetName").toString();
			String size = JsonPath.read(responseAsString, "$.data.rowDTOList["+i+"].vendorSize").toString();
			String vendorHSN = JsonPath.read(responseAsString, "$.data.rowDTOList["+i+"].vendorHSN").toString();
			String myntraHSN = JsonPath.read(responseAsString, "$.data.rowDTOList["+i+"].myntraHSN").toString();
			jsonHSN.add(sheetName+"."+size+"."+vendorHSN+"."+myntraHSN);
		}
		jsonHSN.removeAll(Arrays.asList(sheetHSN));
		AssertJUnit.assertTrue("There are some extra entries in response "+jsonHSN, jsonHSN.isEmpty());
	}
	
	@Test(groups = { "e2eCMSRegression" }, priority = 3, dependsOnMethods = "verifyHSNErrors", alwaysRun=true, description = "Verify same remarks for all sku rows of a style")
	public void verifySameRemarks() throws Exception {
		String columnName = "remarks";
		String articleType = "Blazers";
		String groupId = "100";
		ErrorEntryType[] errorEntry = new ErrorEntryType[] {
				ErrorEntryType.PROD_LEVEL_VALIDATIONS};
		assertDIYSyncErrorEntries(hsnErrorResponse, errorEntry, new String[]{columnName, articleType, groupId});
	}
	
	@Test(groups = { "e2eCMSRegression" }, priority = 4, dependsOnMethods = "verifyHSNErrors", alwaysRun=true, description = "Verify same age-group for all sku rows of a style")
	public void verifySameAgeGroup() throws Exception {
		String columnName = "global Attribute Age Group";
		String articleType = "Blazers";
		String groupId = "100";
		ErrorEntryType[] errorEntry = new ErrorEntryType[] {
				ErrorEntryType.PROD_LEVEL_VALIDATIONS};
		assertDIYSyncErrorEntries(hsnErrorResponse, errorEntry, new String[]{columnName, articleType, groupId});
	}
	
	@Test(groups = { "e2eCMSRegression" }, priority = 5, dependsOnMethods = "verifyHSNErrors", alwaysRun=true, description = "Verify same base colour for all sku rows of a style")
	public void verifySameBaseColour() throws Exception {
		String columnName = "global Attribute Base Color";
		String articleType = "Blazers";
		String groupId = "100";
		ErrorEntryType[] errorEntry = new ErrorEntryType[] {
				ErrorEntryType.PROD_LEVEL_VALIDATIONS};
		assertDIYSyncErrorEntries(hsnErrorResponse, errorEntry, new String[]{columnName, articleType, groupId});
	}
	
	@Test(groups = { "e2eCMSRegression" }, priority = 6, dependsOnMethods = "verifyHSNErrors", alwaysRun=true, description = "Verify same fashion type for all sku rows of a style")
	public void verifySameFashioType() throws Exception {
		String columnName = "global Attribute Fashion Type";
		String articleType = "Blazers";
		String groupId = "100";
		ErrorEntryType[] errorEntry = new ErrorEntryType[] {
				ErrorEntryType.PROD_LEVEL_VALIDATIONS};
		assertDIYSyncErrorEntries(hsnErrorResponse, errorEntry, new String[]{columnName, articleType, groupId});
	}
	
	@Test(groups = { "e2eCMSRegression" }, priority = 7, dependsOnMethods = "verifyHSNErrors", alwaysRun=true, description = "Verify same year for all sku rows of a style")
	public void verifySameYear() throws Exception {
		String columnName = "global Attribute Year";
		String articleType = "Blazers";
		String groupId = "100";
		ErrorEntryType[] errorEntry = new ErrorEntryType[] {
				ErrorEntryType.PROD_LEVEL_VALIDATIONS};
		assertDIYSyncErrorEntries(hsnErrorResponse, errorEntry, new String[]{columnName, articleType, groupId});
	}
	
	@Test(groups = { "e2eCMSRegression" }, priority = 8, dependsOnMethods = "verifyHSNErrors", alwaysRun=true, description = "Verify same season for all sku rows of a style")
	public void verifySameSeason() throws Exception {
		String columnName = "global Attribute Season";
		String articleType = "Blazers";
		String groupId = "100";
		ErrorEntryType[] errorEntry = new ErrorEntryType[] {
				ErrorEntryType.PROD_LEVEL_VALIDATIONS};
		assertDIYSyncErrorEntries(hsnErrorResponse, errorEntry, new String[]{columnName, articleType, groupId});
	}

}
