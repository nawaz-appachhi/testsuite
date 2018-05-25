package com.myntra.apiTests.portalservices.cms;

import org.apache.log4j.Logger;
import org.testng.AssertJUnit;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.jayway.jsonpath.JsonPath;
import com.myntra.apiTests.common.APINAME;
import com.myntra.apiTests.common.ServiceType;
import com.myntra.apiTests.portalservices.commons.CommonUtils;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.gandalf.RequestGenerator;


/**
 * @author Vaishali Behere
 * 
 */

public class StyleGroupServiceTest extends CommonUtils {

	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(StyleGroupServiceTest.class);
	

	@BeforeMethod(alwaysRun = true)
	public void openHome() {
		log.info("Entry");
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 1, description = "Verify response of stylegroup/all/${0} api")
	public void styleGrpAllTest() throws Exception {
		RequestGenerator req = e2eRegressionCmsHelper.getRequestObjForCMSForQueryParam(ServiceType.CMS_CATALOG, APINAME.CMS_STYLEGRP_ALL, new String[]{"123456"});
		AssertJUnit.assertEquals("Status is not 200", 200, req.response.getStatus());
		AssertJUnit.assertNotNull("groupId is null", JsonPath.read(req.returnresponseasstring(),"$.result.color.groupId").toString());
		AssertJUnit.assertNotNull("groupType is null", JsonPath.read(req.returnresponseasstring(),"$.result.color.groupType").toString());
		AssertJUnit.assertNotNull("patternName is null", JsonPath.read(req.returnresponseasstring(),"$.result.color.patternName").toString());
		AssertJUnit.assertNotNull("styleIdList is null", JsonPath.read(req.returnresponseasstring(),"$.result.color.styleIdList").toString());
	}
	
	@Test(groups = { "e2eCMSRegression" }, priority = 2, description = "Verify response of stylegroup/color/${0} api")
	public void styleGrpColorTest() throws Exception {
		RequestGenerator req = e2eRegressionCmsHelper.getRequestObjForCMSForQueryParam(ServiceType.CMS_CATALOG, APINAME.CMS_STYLEGRP_COLOR, new String[]{"12"});
		AssertJUnit.assertEquals("Status is not 200", 200, req.response.getStatus());
		AssertJUnit.assertEquals("groupId is not correct", "12", JsonPath.read(req.returnresponseasstring(),"$.data[0].groupId").toString());
		AssertJUnit.assertNotNull("groupType is null", JsonPath.read(req.returnresponseasstring(),"$.data[0].groupType").toString());
		AssertJUnit.assertNotNull("patternName is null", JsonPath.read(req.returnresponseasstring(),"$.data[0].patternName").toString());
		AssertJUnit.assertNotNull("styleIdList is null", JsonPath.read(req.returnresponseasstring(),"$.data[0].styleIdList").toString());
	}
	
}