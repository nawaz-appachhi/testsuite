package com.myntra.apiTests.portalservices.cms;

import argo.saj.InvalidSyntaxException;
import com.jayway.jsonpath.JsonPath;
import com.myntra.apiTests.common.APINAME;
import com.myntra.apiTests.common.ServiceType;
import com.myntra.apiTests.portalservices.commons.CommonUtils;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.Toolbox;
import com.myntra.lordoftherings.aragorn.pages.WebPage;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.javers.core.diff.Diff;
import org.json.JSONException;
import org.junit.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.*;

import java.util.List;

/**
 * @author Vaishali Behere
 * 
 */

public class PIMMigrationTest extends CommonUtils {

	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(PIMMigrationTest.class);
	private RequestGenerator reqOldBeforeUpdate;
	private RequestGenerator reqNewBeforeUpdate;
	private RequestGenerator reqOldAfterUpdate;
	private RequestGenerator reqNewAfterUpdate;
	String responseChangeToOrig;
	private String styleId;
	APINAME putstylev2;
	APINAME putstyleold;
	public static String articleType;
	private String articleTypeCode;
	private String articleIdChange1;
	private String articleNameChange1;
	private String articleIdChange2;
	private String articleNameChange2;
	private String parent1NameChange1;
	private String parent2NameChange1;
	private String parent1NameChange2;
	private String parent2NameChange2;
	APINAME articleChangeAPIOld;
	APINAME articleChangeAPINew;

	public PIMMigrationTest(String styleId, String articleType,
			String articleTypeCode, APINAME putstylev2, APINAME putstyleold,
			APINAME articleChangeAPINew, APINAME articleChangeAPIOld,
			String articleIdChange1, String articleNameChange1,
			String articleIdChange2, String articleNameChange2,
			String parent1NameChange1, String parent2NameChange1,
			String parent1NameChange2, String parent2NameChange2) {
		this.styleId = styleId;
		PIMMigrationTest.articleType = articleType;
		this.articleTypeCode = articleTypeCode;
		this.putstylev2 = putstylev2;
		this.putstyleold = putstyleold;
		this.articleChangeAPIOld = articleChangeAPIOld;
		this.articleChangeAPINew = articleChangeAPINew;
		this.articleIdChange1 = articleIdChange1;
		this.articleNameChange1 = articleNameChange1;
		this.articleIdChange2 = articleIdChange2;
		this.articleNameChange2 = articleNameChange2;
		this.parent1NameChange1 = parent1NameChange1;
		this.parent2NameChange1 = parent2NameChange1;
		this.parent1NameChange2 = parent1NameChange2;
		this.parent2NameChange2 = parent2NameChange2;
	}

	@BeforeClass(groups = { "e2eCMSRegression" })
	public void styleClearCache(){
		RequestGenerator req = e2eRegressionCmsHelper.getRequestObjForCMSForQueryParam(ServiceType.CMS_CATALOG, APINAME.CMSCLEARCACHE, new String[] { this.styleId });
		log.info("clearcache response = " + req.returnresponseasstring());
		AssertJUnit.assertEquals("Status code does not match", 200,
					req.response.getStatus());
	}
	
	@BeforeMethod(alwaysRun = true)
	public void openHome() {
		log.info("Entry");
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 1, description = "Verify there's no diff in old and v2 style api without making changes to style")
	public void verifyNoDiffInOldNewStyle_WithoutStyleChanges()
			throws InvalidSyntaxException, JSONException {
		reqOldBeforeUpdate = e2eRegressionCmsHelper
				.getRequestObjForCMSForQueryParam(ServiceType.CMS_CATALOG, APINAME.CMSGETSTYLE,
						new String[] { styleId });
		reqNewBeforeUpdate = e2eRegressionCmsHelper
				.getRequestObjForCMSForQueryParam(ServiceType.CMS_CATALOG, APINAME.CMSGETSTYLEV2,
						new String[] { styleId });
		AssertJUnit.assertEquals(articleTypeCode+" : Status code does not match for request1",
				200, reqOldBeforeUpdate.response.getStatus());
		AssertJUnit.assertEquals(articleTypeCode+" : Status code does not match for request2",
				200, reqNewBeforeUpdate.response.getStatus());
		Diff diff = e2eRegressionCmsHelper.returnJsonDiff(reqOldBeforeUpdate,
				reqNewBeforeUpdate, true);
		AssertJUnit.assertEquals(articleTypeCode+" : Jsons have diff " + diff.prettyPrint(), 2,
				diff.countByType().toString().length());

	}

	@Test(groups = { "e2eCMSRegression" }, dataProvider = "dp_new", priority = 2, description = "Update style attribute using v2 api", dependsOnMethods = { "verifyNoDiffInOldNewStyle_WithoutStyleChanges" }, alwaysRun = true)
	public void updateV2StyleAttributes(String[] payload_params) {
		RequestGenerator req = e2eRegressionCmsHelper
				.getRequestObjForCMSForPayloadAndQueryParam(putstylev2,
						payload_params, new String[] { styleId });
		System.out.println("RESPONSE: " + req.returnresponseasstring());
		AssertJUnit.assertEquals(articleTypeCode+" : Status code does not match for request1",
				200, req.response.getStatus());
		AssertJUnit.assertFalse(articleTypeCode+" : Error Occured while Updating",
				(JsonPath.read(req.returnresponseasstring(),
						"$.status.statusCode").toString()).equals("51"));

	}

	@Test(groups = { "e2eCMSRegression" }, priority = 3, description = "Verify old style schema after update", dependsOnMethods = { "updateV2StyleAttributes" }, alwaysRun = true)
	public void verifyOldStyleSchemaAfterUpdate_1() {
		reqOldAfterUpdate = e2eRegressionCmsHelper
				.getRequestObjForCMSForQueryParam(ServiceType.CMS_CATALOG, APINAME.CMSGETSTYLE,
						new String[] { styleId });
		String response = reqOldAfterUpdate.returnresponseasstring();
		try {
			String jsonschema = new Toolbox()
					.readFileAsString("Data/SchemaSet/JSON/CMS/CMS_OldStyle_Schema.txt");
			List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(
					response, jsonschema);
			AssertJUnit.assertTrue(articleTypeCode+" : "+missingNodeList
					+ " nodes are missing old search api response",
					CollectionUtils.isEmpty(missingNodeList));
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(articleTypeCode+" : Schema Verification Failure");
		}
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 4, description = "Verify new style schema after update", dependsOnMethods = { "verifyOldStyleSchemaAfterUpdate_1" }, alwaysRun = true)
	public void verifyNewStyleSchemaAfterUpdate_1() {
		reqNewAfterUpdate = e2eRegressionCmsHelper
				.getRequestObjForCMSForQueryParam(ServiceType.CMS_CATALOG, APINAME.CMSGETSTYLEV2,
						new String[] { styleId });
		String response = reqNewAfterUpdate.returnresponseasstring();
		try {
			String jsonschema = new Toolbox()
					.readFileAsString("Data/SchemaSet/JSON/CMS/CMS_NewStyle_Schema.txt");
			List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(
					response, jsonschema);
			AssertJUnit.assertTrue(articleTypeCode+" : "+missingNodeList
					+ " nodes are missing new search api response",
					CollectionUtils.isEmpty(missingNodeList));
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(articleTypeCode+" : Schema Verification Failure");
		}
	}

	@Test(groups = { "e2eCMSRegression" }, dataProvider = "dp_new", priority = 5, description = "Verify old style globalAttr after update", dependsOnMethods = { "verifyNewStyleSchemaAfterUpdate_1" }, alwaysRun = true)
	public void verifyOldStyleGlobalAttrAfterUpdate_1(String[] payload_params) {
		e2eRegressionCmsHelper.validateGlobalAttributesStyleAPI(
				reqOldAfterUpdate, payload_params, articleTypeCode);
	}

	@Test(groups = { "e2eCMSRegression" }, dataProvider = "dp_new", priority = 6, description = "Verify new style globalAttr after update", dependsOnMethods = { "verifyOldStyleGlobalAttrAfterUpdate_1" }, alwaysRun = true)
	public void verifyNewStyleGlobalAttrAfterUpdate_1(String[] payload_params) {
		e2eRegressionCmsHelper.validateGlobalAttributesStyleAPI(
				reqNewAfterUpdate, payload_params, articleTypeCode);
	}

	@Test(groups = { "e2eCMSRegression" }, dataProvider = "dp_new", priority = 7, description = "Verify old style ATSA after update", dependsOnMethods = { "verifyNewStyleGlobalAttrAfterUpdate_1" }, alwaysRun = true)
	public void verifyOldStyleATSAAfterUpdate_1(String[] payload_params) {
		e2eRegressionCmsHelper.validateATSAStyleAPI(reqOldAfterUpdate,
				payload_params, articleTypeCode);
	}

	@Test(groups = { "e2eCMSRegression" }, dataProvider = "dp_new", priority = 8, description = "Verify new style ATSA after update", dependsOnMethods = { "verifyOldStyleATSAAfterUpdate_1" }, alwaysRun = true)
	public void verifyNewStyleATSAAfterUpdate_1(String[] payload_params) {
		e2eRegressionCmsHelper.validateATSAStyleAPI(reqNewAfterUpdate,
				payload_params, articleTypeCode);
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 9, description = "Verify new style ATSA codes after update", dependsOnMethods = { "verifyNewStyleATSAAfterUpdate_1" }, alwaysRun = true)
	public void verifyNewStyleATSACodeAfterUpdate_1() {
		e2eRegressionCmsHelper.validateATSACodeWithValue(reqNewAfterUpdate, articleTypeCode);
	}

	@Test(groups = { "e2eCMSRegression" }, dataProvider = "dp_old", priority = 10, description = "Update style attribute using new api", dependsOnMethods = { "verifyNewStyleATSACodeAfterUpdate_1" }, alwaysRun = true)
	public void updateOldStyleAttributes(String[] payload_params) {
		RequestGenerator req = e2eRegressionCmsHelper
				.getRequestObjForCMSForPayloadAndQueryParam(putstyleold,
						payload_params, new String[] { styleId });
		System.out.println("RESPONSE: " + req.returnresponseasstring());
		AssertJUnit.assertEquals(articleTypeCode+ " : Status code does not match for request1",
				200, req.response.getStatus());
		AssertJUnit.assertFalse(articleTypeCode+" : Error Occured while Updating",
				(JsonPath.read(req.returnresponseasstring(),
						"$.status.statusCode").toString()).equals("51"));
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 11, description = "Verify old style schema after update", dependsOnMethods = { "updateOldStyleAttributes" }, alwaysRun = true)
	public void verifyOldStyleSchemaAfterUpdate_2() {
		reqOldAfterUpdate = e2eRegressionCmsHelper
				.getRequestObjForCMSForQueryParam(ServiceType.CMS_CATALOG, APINAME.CMSGETSTYLE,
						new String[] { styleId });
		String response = reqOldAfterUpdate.returnresponseasstring();
		try {
			String jsonschema = new Toolbox()
					.readFileAsString("Data/SchemaSet/JSON/CMS/CMS_OldStyle_Schema.txt");
			List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(
					response, jsonschema);
			AssertJUnit.assertTrue(articleTypeCode + missingNodeList
					+ " nodes are missing old search api response",
					CollectionUtils.isEmpty(missingNodeList));
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(articleTypeCode+" : Schema Verification Failure");
		}
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 12, description = "Verify new style schema after update", dependsOnMethods = { "verifyOldStyleSchemaAfterUpdate_2" }, alwaysRun = true)
	public void verifyNewStyleSchemaAfterUpdate_2() {
		reqNewAfterUpdate = e2eRegressionCmsHelper
				.getRequestObjForCMSForQueryParam(ServiceType.CMS_CATALOG, APINAME.CMSGETSTYLEV2,
						new String[] { styleId });
		String response = reqNewAfterUpdate.returnresponseasstring();
		try {
			String jsonschema = new Toolbox()
					.readFileAsString("Data/SchemaSet/JSON/CMS/CMS_NewStyle_Schema.txt");
			List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(
					response, jsonschema);
			AssertJUnit.assertTrue(articleTypeCode+ missingNodeList
					+ " nodes are missing new search api response",
					CollectionUtils.isEmpty(missingNodeList));
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(articleTypeCode+" : Schema Verification Failure");
		}
	}

	@Test(groups = { "e2eCMSRegression" }, dataProvider = "dp_old", priority = 13, description = "Verify old style globalAttr after update", dependsOnMethods = { "verifyNewStyleSchemaAfterUpdate_2" }, alwaysRun = true)
	public void verifyOldStyleGlobalAttrAfterUpdate_2(String[] payload_params) {
		e2eRegressionCmsHelper.validateGlobalAttributesStyleAPI(
				reqOldAfterUpdate, payload_params, articleTypeCode);
	}

	@Test(groups = { "e2eCMSRegression" }, dataProvider = "dp_old", priority = 14, description = "Verify new style globalAttr after update", dependsOnMethods = { "verifyOldStyleGlobalAttrAfterUpdate_2" }, alwaysRun = true)
	public void verifyNewStyleGlobalAttrAfterUpdate_2(String[] payload_params) {
		e2eRegressionCmsHelper.validateGlobalAttributesStyleAPI(
				reqNewAfterUpdate, payload_params, articleTypeCode);
	}

	@Test(groups = { "e2eCMSRegression" }, dataProvider = "dp_old", priority = 15, description = "Verify old style ATSA after update", dependsOnMethods = { "verifyNewStyleGlobalAttrAfterUpdate_2" }, alwaysRun = true)
	public void verifyOldStyleATSAAfterUpdate_2(String[] payload_params) {
		e2eRegressionCmsHelper.validateATSAStyleAPI(reqOldAfterUpdate,
				payload_params, articleTypeCode);
	}

	@Test(groups = { "e2eCMSRegression" }, dataProvider = "dp_old", priority = 16, description = "Verify new style ATSA after update", dependsOnMethods = { "verifyOldStyleATSAAfterUpdate_2" }, alwaysRun = true)
	public void verifyNewStyleATSAAfterUpdate_2(String[] payload_params) {
		e2eRegressionCmsHelper.validateATSAStyleAPI(reqNewAfterUpdate,
				payload_params, articleTypeCode);
	}

	@Test(groups = { "e2eCMSRegression" }, dataProvider = "dp_old", priority = 17, description = "Verify new style ATSA codes after update", dependsOnMethods = { "verifyNewStyleATSAAfterUpdate_2" }, alwaysRun = true)
	public void verifyNewStyleATSACodeAfterUpdate_2(String[] payload_params) {
		e2eRegressionCmsHelper.validateATSACodeWithValue(reqNewAfterUpdate, articleTypeCode);
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 18, description = "Verify product id schema", dependsOnMethods = { "verifyNewStyleATSACodeAfterUpdate_2" }, alwaysRun = true)
	public void verifySchemaInProductIdAPI() {
		RequestGenerator req = e2eRegressionCmsHelper
				.getRequestObjForCMSForQueryParam(ServiceType.CMS_CATALOG, APINAME.FINDBYID,
						new String[] { styleId });
		String response = req.returnresponseasstring();
		try {
			String jsonschema = new Toolbox()
					.readFileAsString("Data/SchemaSet/JSON/CMS/CMS_ProductId_Schema.txt");
			List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(
					response, jsonschema);
			AssertJUnit.assertTrue(articleType+missingNodeList
					+ " nodes are missing in product id response",
					CollectionUtils.isEmpty(missingNodeList));
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(articleTypeCode+ " : Schema Verification Failure");
		}
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 19, description = "Verify search api schema", dependsOnMethods = { "verifySchemaInProductIdAPI" }, alwaysRun = true)
	public void verifySchemaInSearch() {
		RequestGenerator req = e2eRegressionCmsHelper
				.getRequestObjForCMSForQueryParam(ServiceType.CMS_CATALOG, APINAME.SEARCHFORSTYLE,
						new String[] { styleId });
		String response = req.returnresponseasstring();
		try {
			String jsonschema = new Toolbox()
					.readFileAsString("Data/SchemaSet/JSON/CMS/CMS_SearchStyle_Schema.txt");
			List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(
					response, jsonschema);
			AssertJUnit.assertTrue(articleType+missingNodeList
					+ " nodes are missing in product id response",
					CollectionUtils.isEmpty(missingNodeList));
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(articleTypeCode+ " : Schema Verification Failure");
		}
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 20, description = "Change article type from old style api", dependsOnMethods = { "verifySchemaInSearch" }, alwaysRun = true)
	public void changeArticleType_OldStyle1() {
		String[] responses = e2eRegressionCmsHelper.udpateArticleType(articleChangeAPIOld, styleId, articleIdChange1,
				articleNameChange1);
		reqOldAfterUpdate = e2eRegressionCmsHelper
				.getRequestObjForCMSForPayloadAndQueryParam(
						articleChangeAPIOld, new String[] { articleTypeCode,
								articleType, styleId }, new String[] { styleId });
		responseChangeToOrig = reqOldAfterUpdate.returnresponseasstring();
		e2eRegressionCmsHelper.assertArticleTypeChange(responses, articleIdChange1,
				articleNameChange1, parent1NameChange1, parent2NameChange1);
	}
	
	@Test(groups = { "e2eCMSRegression" }, priority = 21, description = "Change article type back to original one", dependsOnMethods = { "changeArticleType_OldStyle1" }, alwaysRun = true)
	public void changeArticleTypeBackToOriginal1() {
		assertChangeToOriginalArticleType(responseChangeToOrig);
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 22, description = "Change article type from old style api", dependsOnMethods = { "changeArticleTypeBackToOriginal1" }, alwaysRun = true)
	public void changeArticleType_OldStyle2() {
		String[] responses = e2eRegressionCmsHelper.udpateArticleType(articleChangeAPIOld, styleId, articleIdChange2,
				articleNameChange2);
		reqOldAfterUpdate = e2eRegressionCmsHelper
				.getRequestObjForCMSForPayloadAndQueryParam(
						articleChangeAPIOld, new String[] { articleTypeCode,
								articleType, styleId }, new String[] { styleId });
		responseChangeToOrig = reqOldAfterUpdate.returnresponseasstring();
		e2eRegressionCmsHelper.assertArticleTypeChange(responses, articleIdChange2,
				articleNameChange2, parent1NameChange2, parent2NameChange2);
	}
	
	@Test(groups = { "e2eCMSRegression" }, priority = 23, description = "Change article type back to original one", dependsOnMethods = { "changeArticleType_OldStyle2" }, alwaysRun = true)
	public void changeArticleTypeBackToOriginal2() {
		assertChangeToOriginalArticleType(responseChangeToOrig);
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 24, description = "Change article type from v2", dependsOnMethods = { "changeArticleTypeBackToOriginal2" }, alwaysRun = true)
	public void changeArticleType_V2Style1() {
		String[] responses = e2eRegressionCmsHelper.udpateArticleType(articleChangeAPINew, styleId, articleIdChange1,
				articleNameChange1);
		reqOldAfterUpdate = e2eRegressionCmsHelper
				.getRequestObjForCMSForPayloadAndQueryParam(
						articleChangeAPINew, new String[] { articleTypeCode,
								articleType, styleId }, new String[] { styleId });
		responseChangeToOrig = reqOldAfterUpdate.returnresponseasstring();
		e2eRegressionCmsHelper.assertArticleTypeChange(responses, articleIdChange1,
				articleNameChange1, parent1NameChange1, parent2NameChange1);
	}
	
	@Test(groups = { "e2eCMSRegression" }, priority = 25, description = "Change article type back to original one", dependsOnMethods = { "changeArticleType_V2Style1" }, alwaysRun = true)
	public void changeArticleTypeBackToOriginalV2_1() {
		assertChangeToOriginalArticleType(responseChangeToOrig);
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 26, description = "Change article type from v2", dependsOnMethods = { "changeArticleTypeBackToOriginalV2_1" }, alwaysRun = true)
	public void changeArticleType_V2Style2() {
		String[] responses = e2eRegressionCmsHelper.udpateArticleType(articleChangeAPINew, styleId, articleIdChange2,
				articleNameChange2);
		reqOldAfterUpdate = e2eRegressionCmsHelper
				.getRequestObjForCMSForPayloadAndQueryParam(
						articleChangeAPINew, new String[] { articleTypeCode,
								articleType, styleId }, new String[] { styleId });
		responseChangeToOrig = reqOldAfterUpdate.returnresponseasstring();
		e2eRegressionCmsHelper.assertArticleTypeChange(responses, articleIdChange2,
				articleNameChange2, parent1NameChange2, parent2NameChange2);
	}
	
	@Test(groups = { "e2eCMSRegression" }, priority = 27, description = "Change article type back to original one", dependsOnMethods = { "changeArticleType_V2Style2" }, alwaysRun = true)
	public void changeArticleTypeBackToOriginalV2_2() {
		assertChangeToOriginalArticleType(responseChangeToOrig);
	}

	@AfterClass(alwaysRun = true)
	public void tearDown() {
		log.info("Entry");
		WebPage.endTest();
		log.info("Exit");
	}
	
	@DataProvider
	public Object[][] dp_new() {
		String[] payload = null;
		if (articleTypeCode.equalsIgnoreCase("70")) {
			payload = new String[] { "test desc", "1000.0", "D", "test dispName",
					"test style note", "test mcd", "test sfd", "test comm",
					"test lvn", "18+", "Adults-Men", "Men", "Yellow", "Yellow",
					"Yellow", "Fashion", "Fall", "2010", "Formal", "1760", "237", "691", "1749", "testag", styleId };
		}
		else if(articleTypeCode.equalsIgnoreCase("90")){
			payload = new String[] { "test desc", "1000.0", "D", "test dispName",
					"test style note", "test mcd", "test sfd", "test comm",
					"test lvn", "18+", "Adults-Men", "Men", "Yellow", "Yellow",
					"Yellow", "Fashion", "Fall", "2010", "Formal", "980", "565", "840", "792", "testag", styleId };
		}
		return new Object[][] { new Object[] { payload } };
	}

	@DataProvider
	public Object[][] dp_old() {
		String[] payload = null;
		if (articleTypeCode.equalsIgnoreCase("70")) {
			payload = new String[] { "old desc", "500.0", "DEL", "old dispName",
					"old style note", "old mcd", "old sfd", "old comm",
					"old lvn", "Adidas", "Adults-Women", "Women", "Green",
					"Green", "Green", "Core", "Spring", "2008", "Travel",
					"5204", "238", "692", "1750", "testag, testagold", styleId };
		}
		else if(articleTypeCode.equalsIgnoreCase("90")){
			payload = new String[] { "old desc", "500.0", "DEL", "old dispName",
					"old style note", "old mcd", "old sfd", "old comm",
					"old lvn", "Adidas", "Adults-Women", "Women", "Green",
					"Green", "Green", "Core", "Spring", "2008", "Travel", "981", "569", "841", "793", "testagold", styleId };
		}
		return new Object[][] { new Object[] { payload } };
	}
	
	public void assertChangeToOriginalArticleType(String responseChangeToOrig){
		AssertJUnit.assertEquals(
				"Article type didn't get changed",
				articleTypeCode,
				JsonPath.read(responseChangeToOrig,
						"$.data[0].global_attr_article_type").toString());
		AssertJUnit.assertEquals(
				"Article type didn't get changed",
				articleType,
				JsonPath.read(responseChangeToOrig,
						"$.data[0].global_attr_article_type_name").toString());
	}
	

}
