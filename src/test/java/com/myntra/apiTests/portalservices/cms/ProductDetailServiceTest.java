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

public class ProductDetailServiceTest extends CommonUtils {

	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(ProductDetailServiceTest.class);
	

	@BeforeMethod(alwaysRun = true)
	public void openHome() {
		log.info("Entry");
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 1, description = "Verify response of /product/style/ids?productIdsCSV=")
	public void productStyleTest() throws Exception {
		RequestGenerator req = e2eRegressionCmsHelper.getRequestObjForCMSForQueryParam(ServiceType.CMS_CATALOG, APINAME.CMS_PRODUCTSTYLE, new String[]{"12345,123456"});
		AssertJUnit.assertEquals("Status is not 200", 200, req.response.getStatus());
		AssertJUnit.assertEquals("productId is not correct", "12345", JsonPath.read(req.returnresponseasstring(),"$.data[0].productId").toString());
		AssertJUnit.assertEquals("productId is not correct", "123456", JsonPath.read(req.returnresponseasstring(),"$.data[1].productId").toString());
	}
	
	@Test(groups = { "e2eCMSRegression" }, priority = 2, description = "Verify response of /product/search_pim?q=productId.in:${0}")
	public void searchPimTest() throws Exception {
		RequestGenerator req = e2eRegressionCmsHelper.getRequestObjForCMSForQueryParam(ServiceType.CMS_CATALOG, APINAME.CMS_SEARCHPIM, new String[]{"123456"});
		AssertJUnit.assertEquals("Status is not 200", 200, req.response.getStatus());
		AssertJUnit.assertNotNull("returnPeriod is null", JsonPath.read(req.returnresponseasstring(),"$.data[0].inferredAttributeValues.returnInfo.returnPeriod").toString());
		AssertJUnit.assertNotNull("isChatEnabled is null", JsonPath.read(req.returnresponseasstring(),"$.data[0].inferredAttributeValues.isChatEnabled").toString());
	}
	
	@Test(groups = { "e2eCMSRegression" }, priority = 3, description = "Verify response of /product/clearallcache")
	public void clearAllCacheTest() throws Exception {
		RequestGenerator req = e2eRegressionCmsHelper.getRequestObjForCMSForQueryParam(ServiceType.CMS_CATALOG, APINAME.CMS_CLEARALLCACHE, null);
		AssertJUnit.assertEquals("Status is not 200", 200, req.response.getStatus());
		AssertJUnit.assertEquals("statusMessage is not correct", "Successfully cleared cache", JsonPath.read(req.returnresponseasstring(),"$.status.statusMessage").toString());
	}
	
	@Test(groups = { "e2eCMSRegression" }, priority = 4, description = "Verify response of /product/delCache")
	public void deleteCacheTest() throws Exception {
		RequestGenerator req = e2eRegressionCmsHelper.getRequestObjForCMSForQueryParam(ServiceType.CMS_CATALOG, APINAME.CMS_DELCACHE, new String[]{"123456"});
		AssertJUnit.assertEquals("Status is not 200", 200, req.response.getStatus());
		AssertJUnit.assertEquals("statusMessage is not correct", "Successfully deleted key from cache for style id 123456", JsonPath.read(req.returnresponseasstring(),"$.status.statusMessage").toString());
	}
	
	@Test(groups = { "e2eCMSRegression" }, priority = 5, description = "Verify response of /product/range")
	public void rangeTest() throws Exception {
		RequestGenerator req = e2eRegressionCmsHelper.getRequestObjForCMSForQueryParam(ServiceType.CMS_CATALOG, APINAME.CMS_RANGE, null);
		AssertJUnit.assertEquals("Status is not 200", 200, req.response.getStatus());
		AssertJUnit.assertNotNull("minProductId is null", JsonPath.read(req.returnresponseasstring(),"$.data[0].minProductId").toString());
		AssertJUnit.assertNotNull("maxProductId is null", JsonPath.read(req.returnresponseasstring(),"$.data[0].maxProductId").toString());
	}
	
	@Test(groups = { "e2eCMSRegression" }, priority = 6, description = "Verify response of /product/all")
	public void allProdTest() throws Exception {
		RequestGenerator req = e2eRegressionCmsHelper.getRequestObjForCMSForQueryParam(ServiceType.CMS_CATALOG, APINAME.CMS_ALL, null);
		AssertJUnit.assertEquals("Status is not 200", 200, req.response.getStatus());
		AssertJUnit.assertNotNull("styleId is null", JsonPath.read(req.returnresponseasstring(),"$.productExpressEntries[0].styleId").toString());
	}
	
	@Test(groups = { "e2eCMSRegression" }, priority = 7, description = "Verify response of /product/mandatorymeasurements?articleType=tops")
	public void mandatoryMeasurementTest() throws Exception {
		RequestGenerator req = e2eRegressionCmsHelper.getRequestObjForCMSForQueryParam(ServiceType.CMS_CATALOG, APINAME.CMS_MEASUREMENT, new String[]{"tops"});
		AssertJUnit.assertEquals("Status is not 200", 200, req.response.getStatus());
		AssertJUnit.assertEquals("article type is not tops", "tops", JsonPath.read(req.returnresponseasstring(),"$.data[0].articleType").toString());
		AssertJUnit.assertEquals("mandatory measurement is not correct", "Chest", JsonPath.read(req.returnresponseasstring(),"$.data[0].mandatoryMeasurements").toString());
		AssertJUnit.assertNotNull("toleranceValues is null", JsonPath.read(req.returnresponseasstring(),"$.data[0].toleranceValues").toString());
	}
	
	@Test(groups = { "e2eCMSRegression" }, priority = 8, description = "Verify response of /product/export/isSearchSafeToExport?q=productId.eq:${0}")
	public void safeToExportTest() throws Exception {
		RequestGenerator req = e2eRegressionCmsHelper.getRequestObjForCMSForQueryParam(ServiceType.CMS_CATALOG, APINAME.CMS_SAFETOEXPORT, new String[]{"12345"});
		AssertJUnit.assertEquals("Status is not 200", 200, req.response.getStatus());
		AssertJUnit.assertEquals("safeToDownload is false", "true", JsonPath.read(req.returnresponseasstring(),"$.safeToDownload").toString());
	}
	
	@Test(groups = { "e2eCMSRegression" }, priority = 9, description = "Verify response of /product/sizeunification/90")
	public void sizeUnificationTest() throws Exception {
		RequestGenerator req = e2eRegressionCmsHelper.getRequestObjForCMSForQueryParam(ServiceType.CMS_CATALOG, APINAME.CMS_SIZEUNIFICATION, new String[]{"90"});
		AssertJUnit.assertEquals("Status is not 200", 200, req.response.getStatus());
		AssertJUnit.assertEquals("id is not correct", "90", JsonPath.read(req.returnresponseasstring(),"$.productOptionEntries[0].id").toString());
		AssertJUnit.assertEquals("name is not correct", "Size", JsonPath.read(req.returnresponseasstring(),"$.productOptionEntries[0].name").toString());
		AssertJUnit.assertEquals("value is not correct", "6-7 years", JsonPath.read(req.returnresponseasstring(),"$.productOptionEntries[0].value").toString());
		AssertJUnit.assertEquals("isActive is not correct", "true", JsonPath.read(req.returnresponseasstring(),"$.productOptionEntries[0].isActive").toString());
		AssertJUnit.assertEquals("active is not correct", "true", JsonPath.read(req.returnresponseasstring(),"$.productOptionEntries[0].active").toString());
		AssertJUnit.assertNotNull("styleId is not correct", JsonPath.read(req.returnresponseasstring(),"$.productOptionEntries[0].styleId").toString());
	}
	
	@Test(groups = { "e2eCMSRegression" }, priority = 10, description = "Verify response of /product/${0}/validatestyle")
	public void validateStyleTest() throws Exception {
		RequestGenerator req = e2eRegressionCmsHelper.getRequestObjForCMSForQueryParam(ServiceType.CMS_CATALOG, APINAME.CMS_VALIDATESTYLE, new String[]{"474843"});
		AssertJUnit.assertEquals("Status is not 200", 200, req.response.getStatus());
		AssertJUnit.assertEquals("styleId is not correct", "474843", JsonPath.read(req.returnresponseasstring(),"$.data[0].styleId").toString());
		AssertJUnit.assertNotNull("errors is null", JsonPath.read(req.returnresponseasstring(),"$.data[0].errors").toString());
	}
	
}