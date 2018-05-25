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

public class TaxonomyServiceTest extends CommonUtils {

	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(TaxonomyServiceTest.class);
	

	@BeforeMethod(alwaysRun = true)
	public void openHome() {
		log.info("Entry");
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 1, description = "Verify response of taxonomy/allmastercategory api")
	public void taxonomyAllMasterTest() throws Exception {
		RequestGenerator req = e2eRegressionCmsHelper.getRequestObjForCMSForQueryParam(ServiceType.CMS_CATALOG, APINAME.CMS_TAXONOMY_ALLMASTER, null);
		AssertJUnit.assertEquals("Status is not 200", 200, req.response.getStatus());
		AssertJUnit.assertNotNull("id is not correct", JsonPath.read(req.returnresponseasstring(),"$.data[0].id").toString());
		AssertJUnit.assertNotNull("name is null", JsonPath.read(req.returnresponseasstring(),"$.data[0].name").toString());
		AssertJUnit.assertNotNull("code is null", JsonPath.read(req.returnresponseasstring(),"$.data[0].code").toString());
		AssertJUnit.assertNotNull("isActive is null", JsonPath.read(req.returnresponseasstring(),"$.data[0].isActive").toString());
	}
	
	@Test(groups = { "e2eCMSRegression" }, priority = 2, description = "Verify response of taxonomy/allsubcategory api")
	public void taxonomyAllSubTest() throws Exception {
		RequestGenerator req = e2eRegressionCmsHelper.getRequestObjForCMSForQueryParam(ServiceType.CMS_CATALOG, APINAME.CMS_TAXONOMY_ALLSUB, null);
		AssertJUnit.assertEquals("Status is not 200", 200, req.response.getStatus());
		AssertJUnit.assertNotNull("id is not correct", JsonPath.read(req.returnresponseasstring(),"$.data[0].id").toString());
		AssertJUnit.assertNotNull("name is null", JsonPath.read(req.returnresponseasstring(),"$.data[0].name").toString());
		AssertJUnit.assertNotNull("code is null", JsonPath.read(req.returnresponseasstring(),"$.data[0].code").toString());
		AssertJUnit.assertNotNull("isActive is null", JsonPath.read(req.returnresponseasstring(),"$.data[0].isActive").toString());
	}
	
	@Test(groups = { "e2eCMSRegression" }, priority = 3, description = "Verify response of taxonomy/pim/allmastercategory api")
	public void taxonomyAllPimMasterTest() throws Exception {
		RequestGenerator req = e2eRegressionCmsHelper.getRequestObjForCMSForQueryParam(ServiceType.CMS_CATALOG, APINAME.CMS_TAXONOMY_PIMALLMASTER, null);
		AssertJUnit.assertEquals("Status is not 200", 200, req.response.getStatus());
		AssertJUnit.assertNotNull("id is not correct", JsonPath.read(req.returnresponseasstring(),"$.data[0].id").toString());
		AssertJUnit.assertNotNull("name is null", JsonPath.read(req.returnresponseasstring(),"$.data[0].name").toString());
		AssertJUnit.assertNotNull("code is null", JsonPath.read(req.returnresponseasstring(),"$.data[0].code").toString());
		AssertJUnit.assertNotNull("isActive is null", JsonPath.read(req.returnresponseasstring(),"$.data[0].isActive").toString());
	}
	
	@Test(groups = { "e2eCMSRegression" }, priority = 4, description = "Verify response of taxonomy/pim/allsubcategory api")
	public void taxonomyAllPimSubTest() throws Exception {
		RequestGenerator req = e2eRegressionCmsHelper.getRequestObjForCMSForQueryParam(ServiceType.CMS_CATALOG, APINAME.CMS_TAXONOMY_PIMALLSUB, null);
		AssertJUnit.assertEquals("Status is not 200", 200, req.response.getStatus());
		AssertJUnit.assertNotNull("id is not correct", JsonPath.read(req.returnresponseasstring(),"$.data[0].id").toString());
		AssertJUnit.assertNotNull("name is null", JsonPath.read(req.returnresponseasstring(),"$.data[0].name").toString());
		AssertJUnit.assertNotNull("code is null", JsonPath.read(req.returnresponseasstring(),"$.data[0].code").toString());
		AssertJUnit.assertNotNull("isActive is null", JsonPath.read(req.returnresponseasstring(),"$.data[0].isActive").toString());
	}
	
}