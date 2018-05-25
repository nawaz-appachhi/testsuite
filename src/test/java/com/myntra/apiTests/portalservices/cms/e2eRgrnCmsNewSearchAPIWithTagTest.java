package com.myntra.apiTests.portalservices.cms;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import com.myntra.apiTests.dataproviders.CMSAPIDP;

import org.apache.log4j.Logger;
import org.testng.AssertJUnit;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.jayway.jsonpath.JsonPath;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.aragorn.pages.WebPage;
import com.myntra.apiTests.common.APINAME;
import com.myntra.apiTests.common.ServiceType;
import com.myntra.lordoftherings.gandalf.RequestGenerator;

import edu.emory.mathcs.backport.java.util.Collections;

/**
 * @author Vaishali Behere
 * 
 */

public class e2eRgrnCmsNewSearchAPIWithTagTest extends CMSAPIDP {

	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(e2eRegressionCmsSearchApiTest.class);
	String style = System.getenv("style");
	String styles = System.getenv("styles");
	String errMsg1 = "You cannot search for more than one value in case of 'eq'. Use 'in' instead";
	String errMsg2 = "Invalid operator! Only 'in' and 'eq' allowed.";
	RequestGenerator req1;
	RequestGenerator req2;

	@BeforeClass(groups = { "e2eCMSRegression" })
	public void styleClearCache(){
		for(int i=0; i<CMSAPIDP.dp_styles.length; i++){
			RequestGenerator req = e2eRegressionCmsHelper.getRequestObjForCMSForQueryParam(ServiceType.CMS_CATALOG, APINAME.CMSCLEARCACHE, new String[] { CMSAPIDP.dp_styles[i] });
			log.info("clearcache response = " + req.returnresponseasstring());
			AssertJUnit.assertEquals("Status code does not match", 200,
					req.response.getStatus());
		}
	}
	
	@BeforeMethod(alwaysRun = true)
	public void openHome() {
		log.info("Entry");
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 1, description = "Verify v2 search results for tags with eq")
	public void verifyV2SearchByTag_Eq() {
		req1 = e2eRegressionCmsHelper.getRequestObjForCMSForQueryParam(
				ServiceType.CMS_CATALOG, APINAME.CMSSEARCHGENERIC_V2_EQ, new String[] { "productTag",
						"Garfield&start=0&fetchSize=200", "" });
		AssertJUnit.assertEquals("Status code does not match", 200,
				req1.response.getStatus());
		assertTagsPresent(req1.returnresponseasstring(), "Garfield");
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 2, description = "Verify v2 search results for tags with in")
	public void verifyV2SearchByTag_In() {
		req2 = e2eRegressionCmsHelper.getRequestObjForCMSForQueryParam(
				ServiceType.CMS_CATALOG, APINAME.CMSSEARCHGENERIC_V2_IN, new String[] { "productTag",
						"Roxy&start=0&fetchSize=200", "" });
		AssertJUnit.assertEquals("Status code does not match", 200,
				req2.response.getStatus());
		assertTagsPresent(req2.returnresponseasstring(), "Roxy");
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 3, description = "Verify v2 search results for multiple tags with in")
	public void verifyV2SearchByTags_In() {
		boolean countFlag = false;
		RequestGenerator req = e2eRegressionCmsHelper
				.getRequestObjForCMSForQueryParam(
						ServiceType.CMS_CATALOG, APINAME.CMSSEARCHGENERIC_V2_IN, new String[] {
								"productTag",
								"Roxy,Garfield&start=0&fetchSize=0", "" });
		AssertJUnit.assertEquals("Status code does not match", 200,
				req.response.getStatus());
		String countForReq1 = JsonPath.read(req1.returnresponseasstring(),
				"$.status.totalCount").toString();
		String countForReq2 = JsonPath.read(req2.returnresponseasstring(),
				"$.status.totalCount").toString();
		String countForReq = JsonPath.read(req.returnresponseasstring(),
				"$.status.totalCount").toString();
		int totalCount = Integer.parseInt(countForReq1)
				+ Integer.parseInt(countForReq2);
		if (Integer.parseInt(countForReq) == totalCount) {
			countFlag = true;
		}
		AssertJUnit.assertTrue("Addition of total count is incorrect",
				countFlag);
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 4, description = "Verify correct error message for invalid operators eq")
	public void verifyErrorMessage_Tags_InvalidOpers_Eq() {
		RequestGenerator req = e2eRegressionCmsHelper
				.getRequestObjForCMSForQueryParam(
						ServiceType.CMS_CATALOG, APINAME.SEARCHFORSTYLE_OPER_V2, new String[] {
								"productTag", "eq", "Roxy,Garfield" });
		String msg = JsonPath.read(req.returnresponseasstring(),
				"$.status.statusMessage").toString();
		AssertJUnit.assertEquals("Status code does not match", 200,
				req.response.getStatus());
		AssertJUnit.assertEquals("EQ error msg doesn't match", errMsg1, msg);
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 5, description = "Verify correct error message for invalid operators is")
	public void verifyErrorMessage_SkuId_InvalidOpers_Is() {
		RequestGenerator req = e2eRegressionCmsHelper
				.getRequestObjForCMSForQueryParam(
						ServiceType.CMS_CATALOG, APINAME.SEARCHFORSTYLE_OPER_V2, new String[] {
								"productTag", "is", "Garfield" });
		String msg = JsonPath.read(req.returnresponseasstring(),
				"$.status.statusMessage").toString();
		AssertJUnit.assertEquals("Status code does not match", 200,
				req.response.getStatus());
		AssertJUnit.assertEquals("Err msg doesn't match", errMsg2, msg);
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 6, description = "Verify new search with sortOrder=ASC")
	public void verifyNewSearchWithASCSortOrder() {
		RequestGenerator req = e2eRegressionCmsHelper
				.getRequestObjForCMSForQueryParam(
						ServiceType.CMS_CATALOG, APINAME.CMSSEARCHGENERIC_V2_IN, new String[] {
								"productTag",
								"Roxy,Garfield&sortOrder=ASC&sortBy=productId",
								"" });
		List<String> productIds = JsonPath.read(req.returnresponseasstring(),
				"$.data[*].productId");
		List<String> prodIdBeforeSorting = new ArrayList<String>(productIds);
		Collections.sort(productIds);
		AssertJUnit.assertTrue(productIds.equals(prodIdBeforeSorting));
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 7, description = "Verify new search with sortOrder=DESC")
	public void verifyNewSearchWithDESCSortOrder() {
		RequestGenerator req = e2eRegressionCmsHelper
				.getRequestObjForCMSForQueryParam(
						ServiceType.CMS_CATALOG, APINAME.CMSSEARCHGENERIC_V2_IN,
						new String[] {
								"productTag",
								"Roxy,Garfield&sortOrder=DESC&sortBy=productId",
								"" });
		List<String> productIds = JsonPath.read(req.returnresponseasstring(),
				"$.data[*].productId");
		List<String> prodIdBeforeSorting = new ArrayList<String>(productIds);
		Collections.sort(productIds, Collections.reverseOrder());
		AssertJUnit.assertTrue(productIds.equals(prodIdBeforeSorting));
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 8, description = "Verify multiple query search")
	public void verifyNewSearchWithMultipleQuery() {
		RequestGenerator req = e2eRegressionCmsHelper
				.getRequestObjForCMSForQueryParam(
						ServiceType.CMS_CATALOG, APINAME.CMSSEARCHGENERIC_V2_IN,
						new String[] {
								"productTag",
								"Roxy___productOptions.sku.skuId.in:7374248,7374239",
								"" });
		String count = JsonPath.read(req.returnresponseasstring(),
				"$.status.totalCount").toString();
		AssertJUnit.assertEquals("Invalid count", "2", count);
		assertTagsPresent(req.returnresponseasstring(), "Roxy");
		AssertJUnit.assertEquals(
				"Sku id mismatch1",
				"7374239",
				JsonPath.read(req.returnresponseasstring(),
						"$.data[0].productOptions[0].skuId").toString());
		AssertJUnit.assertEquals(
				"Sku id mismatch2",
				"7374248",
				JsonPath.read(req.returnresponseasstring(),
						"$.data[1].productOptions[0].skuId").toString());
	}

	public void assertTagsPresent(String response, String tag) {
		List<String> tagsList = JsonPath
				.read(response, "$.data[*].productTags");
		for (int i = 0; i < tagsList.size(); i++) {
			ArrayList<String> tagsForIndex = new ArrayList<String>();
			String tags = JsonPath.read(response,
					"$.data[" + i + "].productTags").toString();
			StringTokenizer strtok = new StringTokenizer(tags, ",");
			while (strtok.hasMoreTokens()) {
				tagsForIndex.add(strtok.nextToken());
			}
			System.out.println("IDID:" + tagsForIndex);
			AssertJUnit.assertTrue("Tag results are not correct",
					tagsForIndex.contains(tag));
		}
	}

	@AfterClass(alwaysRun = true)
	public void tearDown() {
		log.info("Entry");
		WebPage.endTest();
		log.info("Exit");

	}
}
