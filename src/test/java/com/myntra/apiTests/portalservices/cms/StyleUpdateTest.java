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

public class StyleUpdateTest extends CommonUtils {

	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(StyleUpdateTest.class);
	String payload;
	

	@BeforeMethod(alwaysRun = true)
	public void createPayload() {
		log.info("Entry");
		payload = "$key: $value";
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 1, description = "Verify response of /style/123456 api")
	public void styleTest() throws Exception {
		RequestGenerator req = e2eRegressionCmsHelper.getRequestObjForCMSForQueryParam(ServiceType.CMS_CATALOG, APINAME.CMSGETSTYLE, new String[]{"123456"});
		AssertJUnit.assertEquals("Status is not 200", 200, req.response.getStatus());
		AssertJUnit.assertEquals("statusMessage is not correct", "Success", JsonPath.read(req.returnresponseasstring(),"$.status.statusMessage").toString());
		AssertJUnit.assertEquals("id is not correct", "123456", JsonPath.read(req.returnresponseasstring(),"$.data[0].id").toString());
	}
	
	@Test(groups = { "e2eCMSRegression" }, priority = 2, description = "Verify price update of /style/123456 api")
	public void stylePriceUpdate() throws Exception {
		String styleId = "123456";
		String price_updated = "990.0";
		String price_orig = "300.0";
		payload = payload.replace("$key", "\"price\"").replace("$value", price_updated);
		RequestGenerator req = e2eRegressionCmsHelper.getRequestObjForCMSForPayloadAndQueryParam(APINAME.CMSUPDATESTYLE, new String[]{styleId, payload}, new String[]{styleId});
		AssertJUnit.assertEquals("Status is not 200", 200, req.response.getStatus());
		AssertJUnit.assertEquals("statusMessage is not correct", "Success", JsonPath.read(req.returnresponseasstring(),"$.status.statusMessage").toString());
		AssertJUnit.assertEquals("price is not correct", price_updated, JsonPath.read(req.returnresponseasstring(),"$.data[0].price").toString());
		log.info("Set price back to original");
		payload = payload.replace(price_updated, price_orig);
		req = e2eRegressionCmsHelper.getRequestObjForCMSForPayloadAndQueryParam(APINAME.CMSUPDATESTYLE, new String[]{styleId, payload}, new String[]{styleId});
		AssertJUnit.assertEquals("price didn't set back to original value", price_orig, JsonPath.read(req.returnresponseasstring(),"$.data[0].price").toString());
	}
	
	@Test(groups = { "e2eCMSRegression" }, priority = 3, description = "Verify status update of /style/123456 api")
	public void styleStatusUpdate() throws Exception {
		String styleId = "123456";
		String status_updated = "P";
		String status_orig = "D";
		payload = payload.replace("$key", "\"styleStatus\"").replace("$value", "\""+status_updated+"\"");
		RequestGenerator req = e2eRegressionCmsHelper.getRequestObjForCMSForPayloadAndQueryParam(APINAME.CMSUPDATESTYLE, new String[]{styleId, payload}, new String[]{styleId});
		AssertJUnit.assertEquals("Status is not 200", 200, req.response.getStatus());
		AssertJUnit.assertEquals("statusMessage is not correct", "Success", JsonPath.read(req.returnresponseasstring(),"$.status.statusMessage").toString());
		AssertJUnit.assertEquals("status is not correct", status_updated, JsonPath.read(req.returnresponseasstring(),"$.data[0].styleStatus").toString());
		log.info("Set price back to original");
		payload = payload.replace(status_updated, status_orig);
		req = e2eRegressionCmsHelper.getRequestObjForCMSForPayloadAndQueryParam(APINAME.CMSUPDATESTYLE, new String[]{styleId, payload}, new String[]{styleId});
		AssertJUnit.assertEquals("status didn't set back to original value", status_orig, JsonPath.read(req.returnresponseasstring(),"$.data[0].styleStatus").toString());
	}
	
}