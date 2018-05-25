package com.myntra.apiTests.portalservices.cms;

import org.apache.log4j.Logger;
import org.testng.AssertJUnit;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.jayway.jsonpath.JsonPath;
import com.myntra.apiTests.common.APINAME;
import com.myntra.apiTests.common.ServiceType;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.gandalf.RequestGenerator;


/**
 * @author Vaishali Behere
 * 
 */

public class AttributeValueAndBrandServiceTest{

	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(AttributeValueAndBrandServiceTest.class);

	@BeforeMethod(alwaysRun = true)
	public void openHome() {
		log.info("Entry");
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 1, description = "Verify response of attributeValue api")
	public void getAttributeValue() throws Exception {
		RequestGenerator req = e2eRegressionCmsHelper.getRequestObjForCMSForQueryParam(ServiceType.CMS_CATALOG, APINAME.CMS_ATTRIBUTEVALUE, new String[]{"678"});
		AssertJUnit.assertEquals("Status is not 200", 200, req.response.getStatus());
		AssertJUnit.assertEquals("attributeValue is not correct", "Boat Neck", JsonPath.read(req.returnresponseasstring(),"$.data[0].attributeValue").toString());
		AssertJUnit.assertEquals("attributeCode is not correct", "BOAT_NECK", JsonPath.read(req.returnresponseasstring(),"$.data[0].attributeCode").toString());
		AssertJUnit.assertNotNull("attributeTypeId is null", JsonPath.read(req.returnresponseasstring(),"$.data[0].attributeTypeId").toString());
		AssertJUnit.assertNotNull("isActive is null", JsonPath.read(req.returnresponseasstring(),"$.data[0].isActive").toString());
	}
	
	@Test(groups = { "e2eCMSRegression" }, priority = 1, description = "Verify response of brand api")
	public void getBrand() throws Exception {
		RequestGenerator req = e2eRegressionCmsHelper.getRequestObjForCMSForQueryParam(ServiceType.CMS_CATALOG, APINAME.CMS_BRAND, new String[]{"128"});
		AssertJUnit.assertEquals("Status is not 200", 200, req.response.getStatus());
		AssertJUnit.assertEquals("name is not correct", "Allen Solly", JsonPath.read(req.returnresponseasstring(),"$.data[0].name").toString());
		AssertJUnit.assertNotNull("logoURL is null", JsonPath.read(req.returnresponseasstring(),"$.data[0].logoURL").toString());
		AssertJUnit.assertNotNull("uidx is null", JsonPath.read(req.returnresponseasstring(),"$.data[0].uidx").toString());
	}
	
}
