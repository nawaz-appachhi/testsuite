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

public class AttributeTypeServiceTest extends CommonUtils {

	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(AttributeTypeServiceTest.class);
	

	@BeforeMethod(alwaysRun = true)
	public void openHome() {
		log.info("Entry");
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 1, description = "Verify response of attributeType api")
	public void getAttributeType() throws Exception {
		RequestGenerator req = e2eRegressionCmsHelper.getRequestObjForCMSForQueryParam(ServiceType.CMS_CATALOG, APINAME.CMS_ATTRIBUTETYPE, new String[]{"90"});
		AssertJUnit.assertEquals("Status is not 200", 200, req.response.getStatus());
		AssertJUnit.assertEquals("Typename is not correct", "Sleeve Length", JsonPath.read(req.returnresponseasstring(),"$.data[0].typeName").toString());
		AssertJUnit.assertEquals("productCategoryId is not correct", "89", JsonPath.read(req.returnresponseasstring(),"$.data[0].productCategoryId").toString());
		AssertJUnit.assertNotNull("code is null", JsonPath.read(req.returnresponseasstring(),"$.data[0].code").toString());
		List<String> attValueList = JsonPath.read(req.returnresponseasstring(),"$.data[0].allAttributeValues[*]");
		AssertJUnit.assertFalse("Size is 0", attValueList.size()==0);
	}
	
	@Test(groups = { "e2eCMSRegression" }, priority = 2, description = "Verify schema of attributeType api")
	public void getAttributeTypeSchema() throws Exception {
		RequestGenerator req = e2eRegressionCmsHelper.getRequestObjForCMSForQueryParam(ServiceType.CMS_CATALOG, APINAME.CMS_ATTRIBUTETYPE, new String[]{"90"});
		try {
			String jsonschema = new Toolbox()
					.readFileAsString("Data/SchemaSet/JSON/CMS/CMS_AttributeType.txt");
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
