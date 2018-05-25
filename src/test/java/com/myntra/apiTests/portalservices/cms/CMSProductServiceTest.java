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

public class CMSProductServiceTest extends CommonUtils {

	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(CMSProductServiceTest.class);

	@BeforeMethod(alwaysRun = true)
	public void openHome() {
		log.info("Entry");
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 1, description = "Verify response of /style/pim api")
	public void getStylePimStatus() throws Exception {
		RequestGenerator req = e2eRegressionCmsHelper.getRequestObjForCMSForQueryParam(ServiceType.CMS_CATALOG, APINAME.CMS_STYLEPIM, new String[]{"123456"});
		AssertJUnit.assertEquals("Status is not 200", 200, req.response.getStatus());
		List<String> data = JsonPath.read(req.returnresponseasstring(),"$.data[*]");
		AssertJUnit.assertFalse("Size is 0", data.size()==0);
	}
	
	@Test(groups = { "e2eCMSRegression" }, priority = 2, description = "Verify schema of /style/pim api")
	public void getAttributeTypeSchema() throws Exception {
		RequestGenerator req = e2eRegressionCmsHelper.getRequestObjForCMSForQueryParam(ServiceType.CMS_CATALOG, APINAME.CMS_STYLEPIM, new String[]{"123456"});
		try {
			String jsonschema = new Toolbox()
					.readFileAsString("Data/SchemaSet/JSON/CMS/CMS_StylePim.txt");
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
