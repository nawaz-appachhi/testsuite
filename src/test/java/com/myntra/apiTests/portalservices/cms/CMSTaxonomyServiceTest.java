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


/**
 * @author Vaishali Behere
 * 
 */

public class CMSTaxonomyServiceTest extends CommonUtils {

	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(CMSTaxonomyServiceTest.class);
	

	@BeforeMethod(alwaysRun = true)
	public void openHome() {
		log.info("Entry");
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 1, description = "Verify response of /productcategory/{articleid}")
	public void getProdCatArticleId() throws Exception {
		RequestGenerator req = e2eRegressionCmsHelper.getRequestObjForCMSForQueryParam(ServiceType.CMS_CATALOG, APINAME.CMSPRODUCTCATEGORY, new String[]{"90"});
		AssertJUnit.assertEquals("Status is not 200", 200, req.response.getStatus());
		AssertJUnit.assertEquals("Typename is not correct", "Tshirts", JsonPath.read(req.returnresponseasstring(),"$.data[0].typeName").toString());
		AssertJUnit.assertEquals("typeCode is not correct", "TSHT", JsonPath.read(req.returnresponseasstring(),"$.data[0].typeCode").toString());
	}
	
	@Test(groups = { "e2eCMSRegression" }, priority = 2, description = "Verify response of /productcategory/all")
	public void getProdCatAll() throws Exception {
		RequestGenerator req = e2eRegressionCmsHelper.getRequestObjForCMSForQueryParam(ServiceType.CMS_CATALOG, APINAME.CMSPRODUCTCATEGORY, new String[]{"all"});
		AssertJUnit.assertEquals("Status is not 200", 200, req.response.getStatus());
	}
	
	@Test(groups = { "e2eCMSRegression" }, priority = 3, description = "Verify schema of prodcat articleid api")
	public void verifyProdCatAllSchema() throws Exception {
		RequestGenerator req = e2eRegressionCmsHelper.getRequestObjForCMSForQueryParam(ServiceType.CMS_CATALOG, APINAME.CMSPRODUCTCATEGORY, new String[]{"all"});
		try {
			String jsonschema = new Toolbox()
					.readFileAsString("Data/SchemaSet/JSON/CMS/CMS_ProdCat_All.txt");
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
	
	@Test(groups = { "e2eCMSRegression" }, priority = 4, description = "Verify response of /productcategory/90/clearcache")
	public void getProdCatClearcache() throws Exception {
		RequestGenerator req = e2eRegressionCmsHelper.getRequestObjForCMSForQueryParam(ServiceType.CMS_CATALOG, APINAME.CMSPRODUCTCATEGORY, new String[]{"90/clearcache"});
		AssertJUnit.assertEquals("Status is not 200", 200, req.response.getStatus());
		AssertJUnit.assertEquals("statusMessage is not correct", "Successfully cleared cache for style id 90", JsonPath.read(req.returnresponseasstring(),"$.status.statusMessage").toString());
	}
	
	@Test(groups = { "e2eCMSRegression" }, priority = 5, description = "Verify response of /productcategory/allWithParentDetails")
	public void getProdCatParentDetails() throws Exception {
		RequestGenerator req = e2eRegressionCmsHelper.getRequestObjForCMSForQueryParam(ServiceType.CMS_CATALOG, APINAME.CMSPRODCAT_PARENTDETAILS, null);
		AssertJUnit.assertEquals("Status is not 200", 200, req.response.getStatus());
		AssertJUnit.assertNotNull("typeName is null", JsonPath.read(req.returnresponseasstring(),"$.data[0].typeName").toString());
		AssertJUnit.assertNotNull("typeCode is null", JsonPath.read(req.returnresponseasstring(),"$.data[0].typeCode").toString());
		AssertJUnit.assertNotNull("isActive is null", JsonPath.read(req.returnresponseasstring(),"$.data[0].isActive").toString());
		AssertJUnit.assertNotNull("parent1Id is null", JsonPath.read(req.returnresponseasstring(),"$.data[0].parent1Id").toString());
		AssertJUnit.assertNotNull("parent2Id is null", JsonPath.read(req.returnresponseasstring(),"$.data[0].parent2Id").toString());
	}
	
	@Test(groups = { "e2eCMSRegression" }, priority = 6, description = "Verify response of /productcategory/90/getDetailsFromPIM")
	public void getProdCatDetailsFromPIM() throws Exception {
		RequestGenerator req = e2eRegressionCmsHelper.getRequestObjForCMSForQueryParam(ServiceType.CMS_CATALOG, APINAME.CMSPRODCAT_GETPIMDETAILS, new String[]{"90"});
		AssertJUnit.assertEquals("Status is not 200", 200, req.response.getStatus());
		AssertJUnit.assertEquals("typeName is not correct", "Tshirts", JsonPath.read(req.returnresponseasstring(),"$.data[0].typeName").toString());
		AssertJUnit.assertEquals("typeName is not correct", "TSHT", JsonPath.read(req.returnresponseasstring(),"$.data[0].typeCode").toString());
		AssertJUnit.assertNotNull("id not null", JsonPath.read(req.returnresponseasstring(),"$.data[0].allAttributeTypeEntires[0].id").toString());
		AssertJUnit.assertNotNull("isActive not null", JsonPath.read(req.returnresponseasstring(),"$.data[0].allAttributeTypeEntires[0].isActive").toString());
		AssertJUnit.assertNotNull("isReqd not null", JsonPath.read(req.returnresponseasstring(),"$.data[0].allAttributeTypeEntires[0].isRequired").toString());
		AssertJUnit.assertNotNull("code not null", JsonPath.read(req.returnresponseasstring(),"$.data[0].allAttributeTypeEntires[0].code").toString());
	}
}
