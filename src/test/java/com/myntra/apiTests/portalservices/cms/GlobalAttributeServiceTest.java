package com.myntra.apiTests.portalservices.cms;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.myntra.apiTests.common.APINAME;
import com.myntra.apiTests.common.ServiceType;
import com.myntra.apiTests.portalservices.commons.CommonUtils;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.Toolbox;
import com.myntra.lordoftherings.gandalf.RequestGenerator;


/**
 * @author Vaishali Behere
 * 
 */

public class GlobalAttributeServiceTest extends CommonUtils {

	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(GlobalAttributeServiceTest.class);
	

	@BeforeMethod(alwaysRun = true)
	public void openHome() {
		log.info("Entry");
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 1, description = "Verify globalattribute/all schema")
	public void verifyGlobalAttrScehma_All() throws Exception {
		RequestGenerator req = e2eRegressionCmsHelper.getRequestObjForCMSForQueryParam(ServiceType.CMS_CATALOG, APINAME.CMS_GLOBALATTR, new String[]{"all"});
		try {
			String jsonschema = new Toolbox()
					.readFileAsString("Data/SchemaSet/JSON/CMS/CMS_GlobalAttr.txt");
			List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(
					req.returnresponseasstring(), jsonschema);
			AssertJUnit.assertTrue(missingNodeList
					+ " nodes are missing inresponse",
					CollectionUtils.isEmpty(missingNodeList));
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Schema Verification Failure");
		}
	}
	
	@Test(groups = { "e2eCMSRegression" }, priority = 2, description = "Verify globalattribute/v2/all schema")
	public void verifyGlobalAttrScehma_V2All() throws Exception {
		RequestGenerator req = e2eRegressionCmsHelper.getRequestObjForCMSForQueryParam(ServiceType.CMS_CATALOG, APINAME.CMS_GLOBALATTR, new String[]{"v2/all"});
		try {
			String jsonschema = new Toolbox()
					.readFileAsString("Data/SchemaSet/JSON/CMS/CMS_GlobalAttr_V2.txt");
			List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(
					req.returnresponseasstring(), jsonschema);
			AssertJUnit.assertTrue(missingNodeList
					+ " nodes are missing inresponse",
					CollectionUtils.isEmpty(missingNodeList));
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Schema Verification Failure");
		}
	}
	
	@Test(groups = { "e2eCMSRegression" }, priority = 3, description = "Verify globalattribute/allBrands schema")
	public void verifyGlobalAttrSchema_AllBrands() throws Exception {
		RequestGenerator req = e2eRegressionCmsHelper.getRequestObjForCMSForQueryParam(ServiceType.CMS_CATALOG, APINAME.CMS_GLOBALATTR, new String[]{"allBrands"});
		try {
			String jsonschema = new Toolbox()
					.readFileAsString("Data/SchemaSet/JSON/CMS/CMS_GlobalAttr2.txt");
			List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(
					req.returnresponseasstring(), jsonschema);
			AssertJUnit.assertTrue(missingNodeList
					+ " nodes are missing inresponse",
					CollectionUtils.isEmpty(missingNodeList));
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Schema Verification Failure");
		}
	}
	
	@Test(groups = { "e2eCMSRegression" }, priority = 4, description = "Verify globalattribute/allYears schema")
	public void verifyGlobalAttrSchema_AllYears() throws Exception {
		RequestGenerator req = e2eRegressionCmsHelper.getRequestObjForCMSForQueryParam(ServiceType.CMS_CATALOG, APINAME.CMS_GLOBALATTR, new String[]{"allYears"});
		try {
			String jsonschema = new Toolbox()
					.readFileAsString("Data/SchemaSet/JSON/CMS/CMS_GlobalAttr2.txt");
			List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(
					req.returnresponseasstring(), jsonschema);
			AssertJUnit.assertTrue(missingNodeList
					+ " nodes are missing inresponse",
					CollectionUtils.isEmpty(missingNodeList));
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Schema Verification Failure");
		}
	}
	
	@Test(groups = { "e2eCMSRegression" }, priority = 5, description = "Verify globalattribute/allAgeGroups schema")
	public void verifyGlobalAttrSchema_AllAgeGroups() throws Exception {
		RequestGenerator req = e2eRegressionCmsHelper.getRequestObjForCMSForQueryParam(ServiceType.CMS_CATALOG, APINAME.CMS_GLOBALATTR, new String[]{"allAgeGroups"});
		try {
			String jsonschema = new Toolbox()
					.readFileAsString("Data/SchemaSet/JSON/CMS/CMS_GlobalAttr2.txt");
			List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(
					req.returnresponseasstring(), jsonschema);
			AssertJUnit.assertTrue(missingNodeList
					+ " nodes are missing inresponse",
					CollectionUtils.isEmpty(missingNodeList));
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Schema Verification Failure");
		}
	}
	
	@Test(groups = { "e2eCMSRegression" }, priority = 6, description = "Verify globalattribute/allFashionTypes schema")
	public void verifyGlobalAttrSchema_AllFashionTypes() throws Exception {
		RequestGenerator req = e2eRegressionCmsHelper.getRequestObjForCMSForQueryParam(ServiceType.CMS_CATALOG, APINAME.CMS_GLOBALATTR, new String[]{"allFashionTypes"});
		try {
			String jsonschema = new Toolbox()
					.readFileAsString("Data/SchemaSet/JSON/CMS/CMS_GlobalAttr2.txt");
			List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(
					req.returnresponseasstring(), jsonschema);
			AssertJUnit.assertTrue(missingNodeList
					+ " nodes are missing inresponse",
					CollectionUtils.isEmpty(missingNodeList));
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Schema Verification Failure");
		}
	}
	
	@Test(groups = { "e2eCMSRegression" }, priority = 7, description = "Verify globalattribute/allGenders schema")
	public void verifyGlobalAttrSchema_AllGenders() throws Exception {
		RequestGenerator req = e2eRegressionCmsHelper.getRequestObjForCMSForQueryParam(ServiceType.CMS_CATALOG, APINAME.CMS_GLOBALATTR, new String[]{"allGenders"});
		try {
			String jsonschema = new Toolbox()
					.readFileAsString("Data/SchemaSet/JSON/CMS/CMS_GlobalAttr2.txt");
			List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(
					req.returnresponseasstring(), jsonschema);
			AssertJUnit.assertTrue(missingNodeList
					+ " nodes are missing inresponse",
					CollectionUtils.isEmpty(missingNodeList));
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Schema Verification Failure");
		}
	}
	
	@Test(groups = { "e2eCMSRegression" }, priority = 8, description = "Verify globalattribute/allColours schema")
	public void verifyGlobalAttrSchema_AllColours() throws Exception {
		RequestGenerator req = e2eRegressionCmsHelper.getRequestObjForCMSForQueryParam(ServiceType.CMS_CATALOG, APINAME.CMS_GLOBALATTR, new String[]{"allColours"});
		try {
			String jsonschema = new Toolbox()
					.readFileAsString("Data/SchemaSet/JSON/CMS/CMS_GlobalAttr2.txt");
			List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(
					req.returnresponseasstring(), jsonschema);
			AssertJUnit.assertTrue(missingNodeList
					+ " nodes are missing inresponse",
					CollectionUtils.isEmpty(missingNodeList));
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Schema Verification Failure");
		}
	}
	
	@Test(groups = { "e2eCMSRegression" }, priority = 9, description = "Verify globalattribute/allSeasons schema")
	public void verifyGlobalAttrSchema_AllSeasons() throws Exception {
		RequestGenerator req = e2eRegressionCmsHelper.getRequestObjForCMSForQueryParam(ServiceType.CMS_CATALOG, APINAME.CMS_GLOBALATTR, new String[]{"allSeasons"});
		try {
			String jsonschema = new Toolbox()
					.readFileAsString("Data/SchemaSet/JSON/CMS/CMS_GlobalAttr2.txt");
			List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(
					req.returnresponseasstring(), jsonschema);
			AssertJUnit.assertTrue(missingNodeList
					+ " nodes are missing inresponse",
					CollectionUtils.isEmpty(missingNodeList));
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Schema Verification Failure");
		}
	}
	
	@Test(groups = { "e2eCMSRegression" }, priority = 10, description = "Verify globalattribute/allUsages schema")
	public void verifyGlobalAttrSchema_AllUsages() throws Exception {
		RequestGenerator req = e2eRegressionCmsHelper.getRequestObjForCMSForQueryParam(ServiceType.CMS_CATALOG, APINAME.CMS_GLOBALATTR, new String[]{"allUsages"});
		try {
			String jsonschema = new Toolbox()
					.readFileAsString("Data/SchemaSet/JSON/CMS/CMS_GlobalAttr2.txt");
			List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(
					req.returnresponseasstring(), jsonschema);
			AssertJUnit.assertTrue(missingNodeList
					+ " nodes are missing inresponse",
					CollectionUtils.isEmpty(missingNodeList));
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Schema Verification Failure");
		}
	}
	
	@Test(groups = { "e2eCMSRegression" }, priority = 11, description = "Verify globalattribute/v2/allBrands schema")
	public void verifyGlobalAttrSchema_V2AllBrands() throws Exception {
		RequestGenerator req = e2eRegressionCmsHelper.getRequestObjForCMSForQueryParam(ServiceType.CMS_CATALOG, APINAME.CMS_GLOBALATTR, new String[]{"v2/allBrands"});
		try {
			String jsonschema = new Toolbox()
					.readFileAsString("Data/SchemaSet/JSON/CMS/CMS_GlobalAttr_V2_2.txt");
			List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(
					req.returnresponseasstring(), jsonschema);
			AssertJUnit.assertTrue(missingNodeList
					+ " nodes are missing inresponse",
					CollectionUtils.isEmpty(missingNodeList));
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Schema Verification Failure");
		}
	}
	
	@Test(groups = { "e2eCMSRegression" }, priority = 12, description = "Verify globalattribute/v2/allYears schema")
	public void verifyGlobalAttrSchema_V2AllYears() throws Exception {
		RequestGenerator req = e2eRegressionCmsHelper.getRequestObjForCMSForQueryParam(ServiceType.CMS_CATALOG, APINAME.CMS_GLOBALATTR, new String[]{"v2/allYears"});
		try {
			String jsonschema = new Toolbox()
					.readFileAsString("Data/SchemaSet/JSON/CMS/CMS_GlobalAttr_V2_2.txt");
			List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(
					req.returnresponseasstring(), jsonschema);
			AssertJUnit.assertTrue(missingNodeList
					+ " nodes are missing inresponse",
					CollectionUtils.isEmpty(missingNodeList));
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Schema Verification Failure");
		}
	}
	
	@Test(groups = { "e2eCMSRegression" }, priority = 13, description = "Verify globalattribute/v2/allAgeGroups schema")
	public void verifyGlobalAttrSchema_V2AllAgeGroups() throws Exception {
		RequestGenerator req = e2eRegressionCmsHelper.getRequestObjForCMSForQueryParam(ServiceType.CMS_CATALOG, APINAME.CMS_GLOBALATTR, new String[]{"v2/allAgeGroups"});
		try {
			String jsonschema = new Toolbox()
					.readFileAsString("Data/SchemaSet/JSON/CMS/CMS_GlobalAttr_V2_2.txt");
			List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(
					req.returnresponseasstring(), jsonschema);
			AssertJUnit.assertTrue(missingNodeList
					+ " nodes are missing inresponse",
					CollectionUtils.isEmpty(missingNodeList));
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Schema Verification Failure");
		}
	}
	
	@Test(groups = { "e2eCMSRegression" }, priority = 14, description = "Verify globalattribute/v2/allFashionTypes schema")
	public void verifyGlobalAttrSchema_V2AllFashionTypes() throws Exception {
		RequestGenerator req = e2eRegressionCmsHelper.getRequestObjForCMSForQueryParam(ServiceType.CMS_CATALOG, APINAME.CMS_GLOBALATTR, new String[]{"v2/allFashionTypes"});
		try {
			String jsonschema = new Toolbox()
					.readFileAsString("Data/SchemaSet/JSON/CMS/CMS_GlobalAttr_V2_2.txt");
			List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(
					req.returnresponseasstring(), jsonschema);
			AssertJUnit.assertTrue(missingNodeList
					+ " nodes are missing inresponse",
					CollectionUtils.isEmpty(missingNodeList));
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Schema Verification Failure");
		}
	}
	
	@Test(groups = { "e2eCMSRegression" }, priority = 15, description = "Verify globalattribute/v2/allGenders schema")
	public void verifyGlobalAttrSchema_V2AllGenders() throws Exception {
		RequestGenerator req = e2eRegressionCmsHelper.getRequestObjForCMSForQueryParam(ServiceType.CMS_CATALOG, APINAME.CMS_GLOBALATTR, new String[]{"v2/allGenders"});
		try {
			String jsonschema = new Toolbox()
					.readFileAsString("Data/SchemaSet/JSON/CMS/CMS_GlobalAttr_V2_2.txt");
			List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(
					req.returnresponseasstring(), jsonschema);
			AssertJUnit.assertTrue(missingNodeList
					+ " nodes are missing inresponse",
					CollectionUtils.isEmpty(missingNodeList));
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Schema Verification Failure");
		}
	}
	
	@Test(groups = { "e2eCMSRegression" }, priority = 16, description = "Verify globalattribute/v2/allColours schema")
	public void verifyGlobalAttrSchema_V2AllColours() throws Exception {
		RequestGenerator req = e2eRegressionCmsHelper.getRequestObjForCMSForQueryParam(ServiceType.CMS_CATALOG, APINAME.CMS_GLOBALATTR, new String[]{"v2/allColours"});
		try {
			String jsonschema = new Toolbox()
					.readFileAsString("Data/SchemaSet/JSON/CMS/CMS_GlobalAttr_V2_2.txt");
			List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(
					req.returnresponseasstring(), jsonschema);
			AssertJUnit.assertTrue(missingNodeList
					+ " nodes are missing inresponse",
					CollectionUtils.isEmpty(missingNodeList));
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Schema Verification Failure");
		}
	}
	
	@Test(groups = { "e2eCMSRegression" }, priority = 17, description = "Verify globalattribute/v2/allSeasons schema")
	public void verifyGlobalAttrSchema_V2AllSeasons() throws Exception {
		RequestGenerator req = e2eRegressionCmsHelper.getRequestObjForCMSForQueryParam(ServiceType.CMS_CATALOG, APINAME.CMS_GLOBALATTR, new String[]{"v2/allSeasons"});
		try {
			String jsonschema = new Toolbox()
					.readFileAsString("Data/SchemaSet/JSON/CMS/CMS_GlobalAttr_V2_2.txt");
			List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(
					req.returnresponseasstring(), jsonschema);
			AssertJUnit.assertTrue(missingNodeList
					+ " nodes are missing inresponse",
					CollectionUtils.isEmpty(missingNodeList));
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Schema Verification Failure");
		}
	}
	
	@Test(groups = { "e2eCMSRegression" }, priority = 18, description = "Verify globalattribute/v2/allUsages schema")
	public void verifyGlobalAttrSchema_V2AllUsages() throws Exception {
		RequestGenerator req = e2eRegressionCmsHelper.getRequestObjForCMSForQueryParam(ServiceType.CMS_CATALOG, APINAME.CMS_GLOBALATTR, new String[]{"v2/allUsages"});
		try {
			String jsonschema = new Toolbox()
					.readFileAsString("Data/SchemaSet/JSON/CMS/CMS_GlobalAttr_V2_2.txt");
			List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(
					req.returnresponseasstring(), jsonschema);
			AssertJUnit.assertTrue(missingNodeList
					+ " nodes are missing inresponse",
					CollectionUtils.isEmpty(missingNodeList));
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Schema Verification Failure");
		}
	}
	
	@Test(groups = { "e2eCMSRegression" }, priority = 19, description = "Verify globalattribute/allBrands_pim schema")
	public void verifyGlobalAttrPimScehma_AllBrands() throws Exception {
		RequestGenerator req = e2eRegressionCmsHelper.getRequestObjForCMSForQueryParam(ServiceType.CMS_CATALOG, APINAME.CMS_GLOBALATTR, new String[]{"allBrands_pim"});
		try {
			String jsonschema = new Toolbox()
					.readFileAsString("Data/SchemaSet/JSON/CMS/CMS_GlobalAttr_Pim.txt");
			List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(
					req.returnresponseasstring(), jsonschema);
			AssertJUnit.assertTrue(missingNodeList
					+ " nodes are missing inresponse",
					CollectionUtils.isEmpty(missingNodeList));
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Schema Verification Failure");
		}
	}
	
	@Test(groups = { "e2eCMSRegression" }, priority = 20, description = "Verify globalattribute/pim/all schema")
	public void verifyGlobalAttrPimScehma_All() throws Exception {
		RequestGenerator req = e2eRegressionCmsHelper.getRequestObjForCMSForQueryParam(ServiceType.CMS_CATALOG, APINAME.CMS_GLOBALATTR, new String[]{"pim/all"});
		try {
			String jsonschema = new Toolbox()
					.readFileAsString("Data/SchemaSet/JSON/CMS/CMS_GlobalAttr_Pim.txt");
			List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(
					req.returnresponseasstring(), jsonschema);
			AssertJUnit.assertTrue(missingNodeList
					+ " nodes are missing inresponse",
					CollectionUtils.isEmpty(missingNodeList));
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Schema Verification Failure");
		}
	}
	
	
}
