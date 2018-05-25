package com.myntra.apiTests.portalservices.cms;

import java.util.List;

import com.myntra.apiTests.dataproviders.CMSAPIDP;
import com.myntra.apiTests.portalservices.commons.CommonUtils;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import argo.saj.InvalidSyntaxException;

import com.jayway.jsonpath.JsonPath;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.Toolbox;
import com.myntra.lordoftherings.aragorn.pages.WebPage;
import com.myntra.apiTests.common.APINAME;
import com.myntra.apiTests.common.ServiceType;
import com.myntra.lordoftherings.gandalf.RequestGenerator;

/**
 * @author Vaishali Behere
 * 
 */

public class e2eRegressionCmsProductCategoryTest extends CommonUtils {

	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(e2eRegressionCmsSearchApiTest.class);
	String productCategory = "79";
	String id;
	String typeName;
	String typeCode;
	String isActive;
	String allAttTypeEntry_id;
	String allAttTypeEntry_isActive;
	String allAttTypeEntry_isRequired;
	String allAttTypeEntry_code;
	String allAttTypeEntry_allAttVal;
	private RequestGenerator req;

	@BeforeClass(groups = { "e2eCMSRegression" })
	public void styleClearCache(){
		for(int i = 0; i< CMSAPIDP.dp_styles.length; i++){
			RequestGenerator req = e2eRegressionCmsHelper.getRequestObjForCMSForQueryParam(ServiceType.CMS_CATALOG, APINAME.CMSCLEARCACHE, new String[] { CMSAPIDP.dp_styles[i] });
			log.info("response = " + req.returnresponseasstring());
			AssertJUnit.assertEquals("Status code does not match", 200,
					req.response.getStatus());
		}
	}
	
	@BeforeMethod(alwaysRun = true)
	public void openHome() {
		log.info("Entry");
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 1, description = "Verify CMS productCategory status response")
	public void verifyCMSValues() throws InvalidSyntaxException {
		req = e2eRegressionCmsHelper.getRequestObjForCMSForQueryParam(
				ServiceType.CMS_CATALOG, APINAME.CMSPRODUCTCATEGORYV2, new String[] { productCategory });
		AssertJUnit.assertEquals("Status code does not match", 200,
				req.response.getStatus());
		id = e2eRegressionCmsHelper.getNodeValue(req, "data.id", true);
		typeName = e2eRegressionCmsHelper.getNodeValue(req, "data.typeName",
				true);
		typeCode = e2eRegressionCmsHelper.getNodeValue(req, "data.typeCode",
				true);
		isActive = e2eRegressionCmsHelper.getNodeValue(req, "data.isActive",
				true);
		allAttTypeEntry_id = JsonPath.read(req.returnresponseasstring(),
				"$.data[0].allAttributeTypeEntires[0].id").toString();
		allAttTypeEntry_isActive = JsonPath.read(req.returnresponseasstring(),
				"$.data[0].allAttributeTypeEntires[0].isActive").toString();
		allAttTypeEntry_isRequired = e2eRegressionCmsHelper.getNodeValue(req,
				"data.allAttributeTypeEntires.isRequired", true);
		allAttTypeEntry_code = e2eRegressionCmsHelper.getNodeValue(req,
				"data.allAttributeTypeEntires.code", true);
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 2, description = "Verify CMS productCategory id value")
	public void verifyCMSProductCategoryIdValue() {
		AssertJUnit.assertEquals("Ids don't match", productCategory, id);
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 3, description = "Verify CMS productCategory typename value")
	public void verifyCMSProductCategoryTypeNameValue() {
		AssertJUnit.assertEquals("Typenames don't match", "\"Dresses\"",
				typeName);
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 4, description = "Verify CMS productCategory typecode value")
	public void verifyCMSProductCategoryTypeCodeValue() {
		AssertJUnit.assertEquals("Typecodes don't match", "\"DRSS\"", typeCode);
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 5, description = "Verify CMS productCategory isActive value")
	public void verifyCMSProductCategoryIsActiveValue() {
		AssertJUnit.assertEquals("isActive doesn't match", "true", isActive);
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 6, description = "Verify CMS productCategory for allAttributeTypeEntires id")
	public void verifyCMSProductCategory_allAttTypeEntry_id() {
		AssertJUnit.assertNotNull(allAttTypeEntry_id);
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 7, description = "Verify CMS productCategory for allAttributeTypeEntires isActive")
	public void verifyCMSProductCategory_allAttTypeEntry_isActive() {
		AssertJUnit.assertNotNull(allAttTypeEntry_isActive);
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 8, description = "Verify CMS productCategory for allAttributeTypeEntires isReqd")
	public void verifyCMSProductCategory_allAttTypeEntry_isReqd() {
		AssertJUnit.assertNotNull(allAttTypeEntry_isRequired);
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 9, description = "Verify CMS productCategory for allAttributeTypeEntires code")
	public void verifyCMSProductCategory_allAttTypeEntry_code() {
		AssertJUnit.assertNotNull(allAttTypeEntry_code);
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 10, description = "Verify schema for v2 product category")
	public void verifyNewProdCatSchema() {
		String response = req.returnresponseasstring();
		try {
			String jsonschema = new Toolbox()
					.readFileAsString("Data/SchemaSet/JSON/CMS/CMS_V2ProdCat_Schema.txt");
			List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(
					response, jsonschema);
			AssertJUnit.assertTrue(missingNodeList
					+ " nodes are missing new search api response",
					CollectionUtils.isEmpty(missingNodeList));
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Schema Verification Failure");
		}
	}

	@AfterClass(alwaysRun = true)
	public void tearDown() {
		log.info("Entry");
		WebPage.endTest();
		log.info("Exit");

	}
}
