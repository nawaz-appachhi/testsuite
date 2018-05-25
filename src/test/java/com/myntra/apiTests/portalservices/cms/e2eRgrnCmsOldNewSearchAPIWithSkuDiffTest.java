package com.myntra.apiTests.portalservices.cms;

import com.jayway.jsonpath.JsonPath;
import com.myntra.apiTests.common.APINAME;
import com.myntra.apiTests.common.ServiceType;
import com.myntra.apiTests.dataproviders.CMSAPIDP;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.Toolbox;
import com.myntra.lordoftherings.aragorn.pages.WebPage;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import edu.emory.mathcs.backport.java.util.Collections;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.javers.core.diff.Diff;
import org.json.JSONException;
import org.junit.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Vaishali Behere
 * 
 */

public class e2eRgrnCmsOldNewSearchAPIWithSkuDiffTest extends CMSAPIDP {

	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(e2eRegressionCmsSearchApiTest.class);
	String style = System.getenv("style");
	String styles = System.getenv("styles");
	String errMsg1 = "You cannot search for more than one value in case of 'eq'. Use 'in' instead";
	String errMsg2 = "Invalid operator! Only 'in' and 'eq' allowed.";
	String errMsg3 = "No products found.";

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

	@Test(groups = { "e2eCMSRegression" }, priority = 1, description = "Verify that there is no diff in old and new v2 search with eq oper and 1 sku id")
	public void verifyNoDiffInOldAndV2SearchBySku() throws JSONException {
		RequestGenerator req1 = e2eRegressionCmsHelper
				.getRequestObjForCMSForQueryParam(
						ServiceType.CMS_CATALOG, APINAME.CMSSEARCHGENERIC_EQ,
						new String[] { "productOptions.sku.skuId", "123456", "" });
		RequestGenerator req2 = e2eRegressionCmsHelper
				.getRequestObjForCMSForQueryParam(
						ServiceType.CMS_CATALOG, APINAME.CMSSEARCHGENERIC_V2_EQ, new String[] {
								"productOptions.sku.skuId", "123456", "" });

		AssertJUnit.assertEquals("Status code does not match", 200,
				req1.response.getStatus());
		AssertJUnit.assertEquals("Status code does not match", 200,
				req2.response.getStatus());

		Diff diff = e2eRegressionCmsHelper.returnJsonDiff(req1, req2, false);
		AssertJUnit.assertEquals("Jsons have diff " + diff, 2, diff
				.countByType().toString().length());
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 2, description = "Verify that there is no diff in old and new v2 search with in operator and 1 sku id")
	public void verifyNoDiffInOldAndV2SearchBySku_OperInWithOneSKU() throws JSONException {
		RequestGenerator req1 = e2eRegressionCmsHelper
				.getRequestObjForCMSForQueryParam(
						ServiceType.CMS_CATALOG, APINAME.CMSSEARCHGENERIC_IN,
						new String[] { "productOptions.sku.skuId", "123456", "" });
		RequestGenerator req2 = e2eRegressionCmsHelper
				.getRequestObjForCMSForQueryParam(
						ServiceType.CMS_CATALOG, APINAME.CMSSEARCHGENERIC_V2_IN, new String[] {
								"productOptions.sku.skuId", "123456", "" });

		AssertJUnit.assertEquals("Status code does not match", 200,
				req1.response.getStatus());
		AssertJUnit.assertEquals("Status code does not match", 200,
				req2.response.getStatus());

		Diff diff = e2eRegressionCmsHelper.returnJsonDiff(req1, req2, false);
		AssertJUnit.assertEquals("Jsons have diff " + diff, 2, diff
				.countByType().toString().length());
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 3, description = "Verify old new search api diff with active prod style ids")
	public void verifyOldNewSearchDiffWithActiveProdStyleIds() throws JSONException {
		RequestGenerator req1 = e2eRegressionCmsHelper
				.getRequestObjForCMSForQueryParam(ServiceType.CMS_CATALOG, APINAME.CMSSEARCHGENERIC_IN,
						new String[] { "productOptions.sku.skuId",
								"473822,384930,346272,324563,342788", "" });
		RequestGenerator req2 = e2eRegressionCmsHelper
				.getRequestObjForCMSForQueryParam(
						ServiceType.CMS_CATALOG, APINAME.CMSSEARCHGENERIC_V2_IN, new String[] {
								"productOptions.sku.skuId",
								"473822,384930,346272,324563,342788", "" });

		AssertJUnit.assertEquals("Status code does not match", 200,
				req1.response.getStatus());
		AssertJUnit.assertEquals("Status code does not match", 200,
				req2.response.getStatus());

		Diff diff = e2eRegressionCmsHelper.returnJsonDiff(req1, req2, false);
		AssertJUnit.assertEquals("Jsons have diff " + diff, 2, diff
				.countByType().toString().length());
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 4, description = "Verify correct error message for invalid operators eq")
	public void verifyErrorMessage_SkuId_InvalidOpers_Eq() {
		RequestGenerator req = e2eRegressionCmsHelper
				.getRequestObjForCMSForQueryParam(
						ServiceType.CMS_CATALOG, APINAME.SEARCHFORSTYLE_OPER_V2,
						new String[] { "productOptions.sku.skuId", "eq",
								"123456,123457&sortBy=productId&sortOrder=DESC" });
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
						ServiceType.CMS_CATALOG, APINAME.SEARCHFORSTYLE_OPER_V2,
						new String[] { "productOptions.sku.skuId", "is",
								"123456,123457&sortBy=productId&sortOrder=DESC" });
		String msg = JsonPath.read(req.returnresponseasstring(),
				"$.status.statusMessage").toString();
		AssertJUnit.assertEquals("Status code does not match", 200,
				req.response.getStatus());
		AssertJUnit.assertEquals("Err msg doesn't match", errMsg2, msg);
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 6, description = "Verify correct error message for invalid operators nn")
	public void verifyErrorMessage_SkuId_InvalidOpers_Nn() {
		RequestGenerator req = e2eRegressionCmsHelper
				.getRequestObjForCMSForQueryParam(
						ServiceType.CMS_CATALOG, APINAME.SEARCHFORSTYLE_OPER_V2,
						new String[] { "productOptions.sku.skuId", "nn",
								"123456,123457&sortBy=productId&sortOrder=DESC" });
		String msg = JsonPath.read(req.returnresponseasstring(),
				"$.status.statusMessage").toString();
		AssertJUnit.assertEquals("Status code does not match", 200,
				req.response.getStatus());
		AssertJUnit.assertEquals("Err msg doesn't match", errMsg2, msg);
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 7, description = "Verify correct error message for invalid operators ne")
	public void verifyErrorMessage_SkuId_InvalidOpers_Ne() {
		RequestGenerator req = e2eRegressionCmsHelper
				.getRequestObjForCMSForQueryParam(
						ServiceType.CMS_CATALOG, APINAME.SEARCHFORSTYLE_OPER_V2,
						new String[] { "productOptions.sku.skuId", "ne",
								"123456,123457&sortBy=productId&sortOrder=DESC" });
		String msg = JsonPath.read(req.returnresponseasstring(),
				"$.status.statusMessage").toString();
		AssertJUnit.assertEquals("Status code does not match", 200,
				req.response.getStatus());
		AssertJUnit.assertEquals("Err msg doesn't match", errMsg2, msg);
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 8, description = "Verify correct error message for invalid operators like")
	public void verifyErrorMessage_SkuId_InvalidOpers_Like() {
		RequestGenerator req = e2eRegressionCmsHelper
				.getRequestObjForCMSForQueryParam(
						ServiceType.CMS_CATALOG, APINAME.SEARCHFORSTYLE_OPER_V2,
						new String[] { "productOptions.sku.skuId", "like",
								"123456,123457&sortBy=productId&sortOrder=DESC" });
		String msg = JsonPath.read(req.returnresponseasstring(),
				"$.status.statusMessage").toString();
		AssertJUnit.assertEquals("Status code does not match", 200,
				req.response.getStatus());
		AssertJUnit.assertEquals("Err msg doesn't match", errMsg2, msg);
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 9, description = "Verify correct error message for invalid operators gt")
	public void verifyErrorMessage_SkuId_InvalidOpers_Gt() {
		RequestGenerator req = e2eRegressionCmsHelper
				.getRequestObjForCMSForQueryParam(
						ServiceType.CMS_CATALOG, APINAME.SEARCHFORSTYLE_OPER_V2,
						new String[] { "productOptions.sku.skuId", "gt",
								"123456,123457&sortBy=productId&sortOrder=DESC" });
		String msg = JsonPath.read(req.returnresponseasstring(),
				"$.status.statusMessage").toString();
		AssertJUnit.assertEquals("Status code does not match", 200,
				req.response.getStatus());
		AssertJUnit.assertEquals("Err msg doesn't match", errMsg2, msg);
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 10, description = "Verify correct error message for invalid operators lt")
	public void verifyErrorMessage_SkuId_InvalidOpers_Lt() {
		RequestGenerator req = e2eRegressionCmsHelper
				.getRequestObjForCMSForQueryParam(
						ServiceType.CMS_CATALOG, APINAME.SEARCHFORSTYLE_OPER_V2,
						new String[] { "productOptions.sku.skuId", "lt",
								"123456,123457&sortBy=productId&sortOrder=DESC" });
		String msg = JsonPath.read(req.returnresponseasstring(),
				"$.status.statusMessage").toString();
		AssertJUnit.assertEquals("Status code does not match", 200,
				req.response.getStatus());
		AssertJUnit.assertEquals("Err msg doesn't match", errMsg2, msg);
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 11, description = "Verify correct error message for invalid operators le")
	public void verifyErrorMessage_SkuId_InvalidOpers_Le() {
		RequestGenerator req = e2eRegressionCmsHelper
				.getRequestObjForCMSForQueryParam(
						ServiceType.CMS_CATALOG, APINAME.SEARCHFORSTYLE_OPER_V2,
						new String[] { "productOptions.sku.skuId", "le",
								"123456,123457&sortBy=productId&sortOrder=DESC" });
		String msg = JsonPath.read(req.returnresponseasstring(),
				"$.status.statusMessage").toString();
		AssertJUnit.assertEquals("Status code does not match", 200,
				req.response.getStatus());
		AssertJUnit.assertEquals("Err msg doesn't match", errMsg2, msg);
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 12, description = "Verify correct error message for invalid operators nin")
	public void verifyErrorMessage_SkuId_InvalidOpers_Nin() {
		RequestGenerator req = e2eRegressionCmsHelper
				.getRequestObjForCMSForQueryParam(
						ServiceType.CMS_CATALOG, APINAME.SEARCHFORSTYLE_OPER_V2,
						new String[] { "productOptions.sku.skuId", "nin",
								"123456,123457&sortBy=productId&sortOrder=DESC" });
		String msg = JsonPath.read(req.returnresponseasstring(),
				"$.status.statusMessage").toString();
		AssertJUnit.assertEquals("Status code does not match", 200,
				req.response.getStatus());
		AssertJUnit.assertEquals("Err msg doesn't match", errMsg2, msg);
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 13, description = "Verify new search api with fetchSize=-1")
	public void verifyNewSearchWithNegativeFetchSize() {
		RequestGenerator req = e2eRegressionCmsHelper
				.getRequestObjForCMSForQueryParam(
						ServiceType.CMS_CATALOG, APINAME.CMSSEARCHGENERIC_V2_IN,
						new String[] {
								"productOptions.sku.skuId",
								"473822,384930,346272,324563,342788&start=0&fetchSize=-1",
								"" });
		AssertJUnit.assertEquals("Status code does not match", 200,
				req.response.getStatus());
		AssertJUnit.assertEquals(
				"TotalCount is incorrect",
				"5",
				JsonPath.read(req.returnresponseasstring(),
						"$.status.totalCount").toString());
		List<String> productIds = JsonPath.read(req.returnresponseasstring(),
				"$.data[*].productId");
		AssertJUnit.assertEquals("TotalCount is incorrect", 5,
				productIds.size());
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 14, description = "Verify new search api with fetchSize=0")
	public void verifyNewSearchWithZeroFetchSize() {
		RequestGenerator req = e2eRegressionCmsHelper
				.getRequestObjForCMSForQueryParam(
						ServiceType.CMS_CATALOG, APINAME.CMSSEARCHGENERIC_V2_IN,
						new String[] {
								"productOptions.sku.skuId",
								"473822,384930,346272,324563,342788&start=0&fetchSize=0",
								"" });
		AssertJUnit.assertEquals("Status code does not match", 200,
				req.response.getStatus());
		AssertJUnit.assertEquals(
				"TotalCount is incorrect",
				"5",
				JsonPath.read(req.returnresponseasstring(),
						"$.status.totalCount").toString());
		try {
			String jsonschema = new Toolbox()
					.readFileAsString("Data/SchemaSet/JSON/CMS/CMS_V2Search_ZeroFetchSize_Schema.txt");
			List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(
					req.returnresponseasstring(), jsonschema);
			AssertJUnit.assertTrue(missingNodeList
					+ " nodes are missing in response",
					CollectionUtils.isEmpty(missingNodeList));
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Schema Verification Failure");
		}
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 15, description = "Verify new search api with start=3")
	public void verifyNewSearchWithStartParam() {
		RequestGenerator req = e2eRegressionCmsHelper
				.getRequestObjForCMSForQueryParam(
						ServiceType.CMS_CATALOG, APINAME.CMSSEARCHGENERIC_V2_IN,
						new String[] {
								"productOptions.sku.skuId",
								"473822,384930,346272,324563,342788&start=3&fetchSize=-1",
								"" });
		AssertJUnit.assertEquals("Status code does not match", 200,
				req.response.getStatus());
		AssertJUnit.assertEquals(
				"TotalCount is incorrect",
				"5",
				JsonPath.read(req.returnresponseasstring(),
						"$.status.totalCount").toString());
		List<String> productIds = JsonPath.read(req.returnresponseasstring(),
				"$.data[*].productId");
		AssertJUnit.assertEquals("TotalCount is incorrect", 2,
				productIds.size());
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 16, description = "Verify msg no products found")
	public void verifyErrMsgWithNoProducts() {
		RequestGenerator req = e2eRegressionCmsHelper
				.getRequestObjForCMSForQueryParam(
						ServiceType.CMS_CATALOG, APINAME.CMSSEARCHGENERIC_V2_IN, new String[] {
								"productOptions.sku.skuId", "93", "" });
		String msg = JsonPath.read(req.returnresponseasstring(),
				"$.status.statusMessage").toString();
		AssertJUnit.assertEquals("Status code does not match", 200,
				req.response.getStatus());
		AssertJUnit.assertEquals("Err msg doesn't match", errMsg3, msg);
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 17, description = "Verify new search with sortOrder=ASC")
	public void verifyNewSearchWithASCSortOrder() {
		RequestGenerator req = e2eRegressionCmsHelper
				.getRequestObjForCMSForQueryParam(
						ServiceType.CMS_CATALOG, APINAME.CMSSEARCHGENERIC_V2_IN,
						new String[] {
								"productOptions.sku.skuId",
								"473822,384930,346272,324563,342788&sortOrder=ASC&sortBy=productId",
								"" });
		AssertJUnit.assertEquals("Status code does not match", 200,
				req.response.getStatus());
		List<String> productIds = JsonPath.read(req.returnresponseasstring(),
				"$.data[*].productId");
		List<String> prodIdBeforeSorting = new ArrayList<String>(productIds);
		Collections.sort(productIds);
		AssertJUnit.assertTrue(productIds.equals(prodIdBeforeSorting));
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 18, description = "Verify new search with sortOrder=DESC")
	public void verifyNewSearchWithDESCSortOrder() {
		RequestGenerator req = e2eRegressionCmsHelper
				.getRequestObjForCMSForQueryParam(
						ServiceType.CMS_CATALOG, APINAME.CMSSEARCHGENERIC_V2_IN,
						new String[] {
								"productOptions.sku.skuId",
								"473822,384930,346272,324563,342788&sortOrder=DESC&sortBy=productId",
								"" });
		AssertJUnit.assertEquals("Status code does not match", 200,
				req.response.getStatus());
		List<String> productIds = JsonPath.read(req.returnresponseasstring(),
				"$.data[*].productId");
		List<String> prodIdBeforeSorting = new ArrayList<String>(productIds);
		Collections.sort(productIds, Collections.reverseOrder());
		AssertJUnit.assertTrue(productIds.equals(prodIdBeforeSorting));
	}

	@AfterClass(alwaysRun = true)
	public void tearDown() {
		log.info("Entry");
		WebPage.endTest();
		log.info("Exit");

	}
}
