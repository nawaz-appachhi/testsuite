package com.myntra.apiTests.portalservices.cms;

import java.util.List;

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

public class MiscServiceTest extends CommonUtils {

	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(MiscServiceTest.class);
	

	@BeforeMethod(alwaysRun = true)
	public void openHome() {
		log.info("Entry");
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 1, description = "Verify response of attributeType api")
	public void colorVariantStyleGrpSvcTest() throws Exception {
		RequestGenerator req = e2eRegressionCmsHelper.getRequestObjForCMSForQueryParam(ServiceType.CMS_CATALOG, APINAME.CMS_COLORVARIANT, new String[]{"65"});
		AssertJUnit.assertEquals("Status is not 200", 200, req.response.getStatus());
		AssertJUnit.assertEquals("groupId is not correct", "65", JsonPath.read(req.returnresponseasstring(),"$.data[0].groupId").toString());
		AssertJUnit.assertEquals("groupType is not correct", "color", JsonPath.read(req.returnresponseasstring(),"$.data[0].groupType").toString());
		AssertJUnit.assertEquals("patternName is not correct", "Adidas, ADI BEST SHOT TEEGRAPHIC, Men Tshirts", JsonPath.read(req.returnresponseasstring(),"$.data[0].patternName").toString());
		List<String> attValueList = JsonPath.read(req.returnresponseasstring(),"$.data[0].styleIdList[*]");
		AssertJUnit.assertFalse("Size is 0", attValueList.size()==0);
	}
	
	@Test(groups = { "e2eCMSRegression" }, priority = 2, description = "Verify response of /attribute/fabric api")
	public void pimAttributeServiceTest_1() throws Exception {
		RequestGenerator req = e2eRegressionCmsHelper.getRequestObjForCMSForQueryParam(ServiceType.CMS_CATALOG, APINAME.CMS_PIMATT, new String[]{"fabric"});
		AssertJUnit.assertEquals("Status is not 200", 200, req.response.getStatus());
		AssertJUnit.assertEquals("statusMessage is not correct", "Attribute found with Code : fabric", JsonPath.read(req.returnresponseasstring(),"$.status.statusMessage").toString());
		AssertJUnit.assertEquals("code is not correct", "fabric", JsonPath.read(req.returnresponseasstring(),"$.data[0].code").toString());
		AssertJUnit.assertEquals("attributeName is not correct", "Fabric", JsonPath.read(req.returnresponseasstring(),"$.data[0].attributeName").toString());
		AssertJUnit.assertEquals("scope is not correct", "GLOBAL", JsonPath.read(req.returnresponseasstring(),"$.data[0].scope").toString());
		AssertJUnit.assertEquals("attributeName is not correct", "LIST", JsonPath.read(req.returnresponseasstring(),"$.data[0].attributeType").toString());
		AssertJUnit.assertEquals("attributeCategory is not correct", "ARTICLE_TYPE", JsonPath.read(req.returnresponseasstring(),"$.data[0].attributeCategory").toString());
		AssertJUnit.assertNotNull("isActive is null", JsonPath.read(req.returnresponseasstring(),"$.data[0].isActive").toString());
		AssertJUnit.assertNotNull("searchable is null", JsonPath.read(req.returnresponseasstring(),"$.data[0].searchable").toString());
		AssertJUnit.assertNotNull("unique is null", JsonPath.read(req.returnresponseasstring(),"$.data[0].unique").toString());
		AssertJUnit.assertNotNull("isMandatory is null", JsonPath.read(req.returnresponseasstring(),"$.data[0].isMandatory").toString());
	}
	
	@Test(groups = { "e2eCMSRegression" }, priority = 3, description = "Verify response of /attribute/text/fabric api")
	public void pimAttributeServiceTest_2() throws Exception {
		RequestGenerator req = e2eRegressionCmsHelper.getRequestObjForCMSForQueryParam(ServiceType.CMS_CATALOG, APINAME.CMS_PIMTEXTATT, new String[]{"fabric"});
		AssertJUnit.assertEquals("Status is not 200", 200, req.response.getStatus());
		AssertJUnit.assertEquals("statusMessage is not correct", "Attribute found with Code : fabric", JsonPath.read(req.returnresponseasstring(),"$.status.statusMessage").toString());
		AssertJUnit.assertEquals("code is not correct", "fabric", JsonPath.read(req.returnresponseasstring(),"$.data[0].code").toString());
		AssertJUnit.assertEquals("attributeName is not correct", "Fabric", JsonPath.read(req.returnresponseasstring(),"$.data[0].attributeName").toString());
		AssertJUnit.assertEquals("scope is not correct", "GLOBAL", JsonPath.read(req.returnresponseasstring(),"$.data[0].scope").toString());
		AssertJUnit.assertEquals("attributeName is not correct", "LIST", JsonPath.read(req.returnresponseasstring(),"$.data[0].attributeType").toString());
		AssertJUnit.assertEquals("attributeCategory is not correct", "ARTICLE_TYPE", JsonPath.read(req.returnresponseasstring(),"$.data[0].attributeCategory").toString());
		AssertJUnit.assertNotNull("isActive is null", JsonPath.read(req.returnresponseasstring(),"$.data[0].isActive").toString());
		AssertJUnit.assertNotNull("searchable is null", JsonPath.read(req.returnresponseasstring(),"$.data[0].searchable").toString());
		AssertJUnit.assertNotNull("unique is null", JsonPath.read(req.returnresponseasstring(),"$.data[0].unique").toString());
		AssertJUnit.assertNotNull("isMandatory is null", JsonPath.read(req.returnresponseasstring(),"$.data[0].isMandatory").toString());
	}
	
	@Test(groups = { "e2eCMSRegression" }, priority = 4, description = "Verify response of /validvalue/fabric api")
	public void pimAttributeValueServiceTest_1() throws Exception {
		RequestGenerator req = e2eRegressionCmsHelper.getRequestObjForCMSForQueryParam(ServiceType.CMS_CATALOG, APINAME.CMS_PIMVALIDVALUE_ATT, new String[]{"fabric"});
		AssertJUnit.assertEquals("Status is not 200", 200, req.response.getStatus());
		AssertJUnit.assertEquals("statusMessage is not correct", "Attribute Valid Values were displayed", JsonPath.read(req.returnresponseasstring(),"$.status.statusMessage").toString());
		AssertJUnit.assertEquals("attributeCode is not correct", "fabric", JsonPath.read(req.returnresponseasstring(),"$.data[0].attributeCode").toString());
		AssertJUnit.assertNotNull("value is null", JsonPath.read(req.returnresponseasstring(),"$.data[0].value").toString());
		AssertJUnit.assertNotNull("description is null", JsonPath.read(req.returnresponseasstring(),"$.data[0].description").toString());
		AssertJUnit.assertNotNull("isActive is null", JsonPath.read(req.returnresponseasstring(),"$.data[0].isActive").toString());
		AssertJUnit.assertNotNull("articleTypes is null", JsonPath.read(req.returnresponseasstring(),"$.data[0].articleTypes").toString());
	}
	
	@Test(groups = { "e2eCMSRegression" }, priority = 5, description = "Verify response of /validvalue/fabric/tsht api")
	public void pimAttributeValueServiceTest_2() throws Exception {
		RequestGenerator req = e2eRegressionCmsHelper.getRequestObjForCMSForQueryParam(ServiceType.CMS_CATALOG, APINAME.CMS_PIMVALIDVALUE, new String[]{"fabric", "tsht"});
		AssertJUnit.assertEquals("Status is not 200", 200, req.response.getStatus());
		AssertJUnit.assertEquals("statusMessage is not correct", "Attribute Valid Value found with Attribute Code : fabric", JsonPath.read(req.returnresponseasstring(),"$.status.statusMessage").toString());
		AssertJUnit.assertEquals("code is not correct", "fabric", JsonPath.read(req.returnresponseasstring(),"$.data[0].code").toString());
		AssertJUnit.assertNotNull("validValues is null", JsonPath.read(req.returnresponseasstring(),"$.data[0].validValues").toString());
	}
	
	@Test(groups = { "e2eCMSRegression" }, priority = 6, description = "Verify response of /search_in_pim/entity/attribute_metadata?q=id.in:1439 api")
	public void seachInPIM() throws Exception {
		RequestGenerator req = e2eRegressionCmsHelper.getRequestObjForCMSForQueryParam(ServiceType.CMS_CATALOG, APINAME.CMS_SEARCHINPIM, new String[]{"1439"});
		AssertJUnit.assertEquals("Status is not 200", 200, req.response.getStatus());
		AssertJUnit.assertEquals("code is not correct", "fabric", JsonPath.read(req.returnresponseasstring(),"$.data[0].code").toString());
		AssertJUnit.assertEquals("attributeName is not correct", "Fabric", JsonPath.read(req.returnresponseasstring(),"$.data[0].attributeName").toString());
		AssertJUnit.assertEquals("scope is not correct", "GLOBAL", JsonPath.read(req.returnresponseasstring(),"$.data[0].scope").toString());
		AssertJUnit.assertEquals("attributeName is not correct", "LIST", JsonPath.read(req.returnresponseasstring(),"$.data[0].attributeType").toString());
		AssertJUnit.assertEquals("attributeCategory is not correct", "ARTICLE_TYPE", JsonPath.read(req.returnresponseasstring(),"$.data[0].attributeCategory").toString());
		AssertJUnit.assertNotNull("isActive is null", JsonPath.read(req.returnresponseasstring(),"$.data[0].isActive").toString());
		AssertJUnit.assertNotNull("searchable is null", JsonPath.read(req.returnresponseasstring(),"$.data[0].searchable").toString());
		AssertJUnit.assertNotNull("unique is null", JsonPath.read(req.returnresponseasstring(),"$.data[0].unique").toString());
		AssertJUnit.assertNotNull("isMandatory is null", JsonPath.read(req.returnresponseasstring(),"$.data[0].isMandatory").toString());
	}
	
	@Test(groups = { "e2eCMSRegression" }, priority = 7, description = "Verify response of /producttype/all api")
	public void productTypeAllTest() throws Exception {
		RequestGenerator req = e2eRegressionCmsHelper.getRequestObjForCMSForQueryParam(ServiceType.CMS_CATALOG, APINAME.CMS_PRODUCTTYPE, null);
		AssertJUnit.assertEquals("Status is not 200", 200, req.response.getStatus());
		AssertJUnit.assertNotNull("id is null", JsonPath.read(req.returnresponseasstring(),"$.data[2].id").toString());
		AssertJUnit.assertNotNull("name is null", JsonPath.read(req.returnresponseasstring(),"$.data[2].name").toString());
		AssertJUnit.assertNotNull("codEnabled is null", JsonPath.read(req.returnresponseasstring(),"$.data[2].codEnabled").toString());
		AssertJUnit.assertNotNull("vat is null", JsonPath.read(req.returnresponseasstring(),"$.data[2].vat").toString());
	}
	
	@Test(groups = { "e2eCMSRegression" }, priority = 8, description = "Verify response of /product/optionsku/skuid/564744 api")
	public void optionSkuTest() throws Exception {
		RequestGenerator req = e2eRegressionCmsHelper.getRequestObjForCMSForQueryParam(ServiceType.CMS_CATALOG, APINAME.CMS_OPTIONSKU, new String[] {"564744"});
		AssertJUnit.assertEquals("Status is not 200", 200, req.response.getStatus());
		AssertJUnit.assertNotNull("id is null", JsonPath.read(req.returnresponseasstring(),"$.data[0].id").toString());
		AssertJUnit.assertNotNull("name is null", JsonPath.read(req.returnresponseasstring(),"$.data[0].name").toString());
		AssertJUnit.assertNotNull("value is null", JsonPath.read(req.returnresponseasstring(),"$.data[0].value").toString());
		AssertJUnit.assertNotNull("isActive is null", JsonPath.read(req.returnresponseasstring(),"$.data[0].isActive").toString());
		AssertJUnit.assertNotNull("allSize is null", JsonPath.read(req.returnresponseasstring(),"$.data[0].allSize").toString());
		AssertJUnit.assertNotNull("measurement is null", JsonPath.read(req.returnresponseasstring(),"$.data[0].measurement").toString());
		AssertJUnit.assertNotNull("styleId is null", JsonPath.read(req.returnresponseasstring(),"$.data[0].styleId").toString());
		AssertJUnit.assertNotNull("unifiedSizeValueId is null", JsonPath.read(req.returnresponseasstring(),"$.data[0].unifiedSizeValueId").toString());
		AssertJUnit.assertNotNull("unifiedSize is null", JsonPath.read(req.returnresponseasstring(),"$.data[0].unifiedSize").toString());
		AssertJUnit.assertNotNull("sizeChartScaleValueId is null", JsonPath.read(req.returnresponseasstring(),"$.data[0].sizeChartScaleValueId").toString());
		AssertJUnit.assertNotNull("active is null", JsonPath.read(req.returnresponseasstring(),"$.data[0].active").toString());
		AssertJUnit.assertEquals("Sku id is not correct", "564744", JsonPath.read(req.returnresponseasstring(),"$.data[0].skuId").toString());
	}
	
	@Test(groups = { "e2eCMSRegression" }, priority = 9, description = "Verify response of /product/optionsku/skuid/pim/123456 api")
	public void optionSkuPimTest() throws Exception {
		RequestGenerator req = e2eRegressionCmsHelper.getRequestObjForCMSForQueryParam(ServiceType.CMS_CATALOG, APINAME.CMS_OPTIONSKUPIM, new String[] {"123456"});
		AssertJUnit.assertEquals("Status is not 200", 200, req.response.getStatus());
		AssertJUnit.assertNotNull("id is null", JsonPath.read(req.returnresponseasstring(),"$.data[0].id").toString());
		AssertJUnit.assertNotNull("name is null", JsonPath.read(req.returnresponseasstring(),"$.data[0].name").toString());
		AssertJUnit.assertNotNull("value is null", JsonPath.read(req.returnresponseasstring(),"$.data[0].value").toString());
		AssertJUnit.assertNotNull("isActive is null", JsonPath.read(req.returnresponseasstring(),"$.data[0].isActive").toString());
		AssertJUnit.assertNotNull("allSize is null", JsonPath.read(req.returnresponseasstring(),"$.data[0].allSize").toString());
		AssertJUnit.assertNotNull("measurement is null", JsonPath.read(req.returnresponseasstring(),"$.data[0].measurement").toString());
		AssertJUnit.assertNotNull("unifiedSizeValueId is null", JsonPath.read(req.returnresponseasstring(),"$.data[0].unifiedSizeValueId").toString());
		AssertJUnit.assertNotNull("unifiedSizeValue is null", JsonPath.read(req.returnresponseasstring(),"$.data[0].unifiedSizeValue").toString());
		AssertJUnit.assertNotNull("unifiedSize is null", JsonPath.read(req.returnresponseasstring(),"$.data[0].unifiedSize").toString());
		AssertJUnit.assertNotNull("active is null", JsonPath.read(req.returnresponseasstring(),"$.data[0].active").toString());
		AssertJUnit.assertEquals("Sku id is not correct", "123456", JsonPath.read(req.returnresponseasstring(),"$.data[0].skuId").toString());
	}
	
	@Test(groups = { "e2eCMSRegression" }, priority = 10, description = "Verify response of mandatorymeasurements?articleType=${0}&amp;ageGroup=${1} api")
	public void mandatoryMeasurementTest() throws Exception {
		RequestGenerator req = e2eRegressionCmsHelper.getRequestObjForCMSForQueryParam(ServiceType.CMS_CATALOG, APINAME.CMS_MANDATORYMES, new String[] {"tops", "Adults-Men"});
		AssertJUnit.assertEquals("Status is not 200", 200, req.response.getStatus());
		AssertJUnit.assertEquals("article type is not tops", "tops", JsonPath.read(req.returnresponseasstring(),"$.data[0].articleType").toString());
		AssertJUnit.assertEquals("mandatory measurement is not correct", "Chest", JsonPath.read(req.returnresponseasstring(),"$.data[0].mandatoryMeasurements").toString());
		AssertJUnit.assertNotNull("toleranceValues is null", JsonPath.read(req.returnresponseasstring(),"$.data[0].toleranceValues").toString());
	}
	
	@Test(groups = { "e2eCMSRegression" }, priority = 11, description = "Verify response of /nlp/whitelistedATs api")
	public void nlpWhitelistedAtTest() throws Exception {
		RequestGenerator req = e2eRegressionCmsHelper.getRequestObjForCMSForQueryParam(ServiceType.CMS_CATALOG, APINAME.CMS_NLPWHITELIST, null);
		AssertJUnit.assertEquals("Status is not 200", 200, req.response.getStatus());
		AssertJUnit.assertNotNull("articleTypeCode is null", JsonPath.read(req.returnresponseasstring(),"$.data[0].articleTypeCode").toString());
		AssertJUnit.assertNotNull("whitelistedAttributes is null", JsonPath.read(req.returnresponseasstring(),"$.data[0].whitelistedAttributes").toString());
	}
	
	@Test(groups = { "e2eCMSRegression" }, priority = 12, description = "Verify response of stylestatus/alltransition api")
	public void styleStatusTest() throws Exception {
		RequestGenerator req = e2eRegressionCmsHelper.getRequestObjForCMSForQueryParam(ServiceType.CMS_CATALOG, APINAME.CMS_STYLESTATUS, null);
		AssertJUnit.assertEquals("Status is not 200", 200, req.response.getStatus());
		AssertJUnit.assertNotNull("id is null", JsonPath.read(req.returnresponseasstring(),"$.data[0].id").toString());
		AssertJUnit.assertNotNull("statusCodeFrom is null", JsonPath.read(req.returnresponseasstring(),"$.data[0].statusCodeFrom").toString());
		AssertJUnit.assertNotNull("statusNameFrom is null", JsonPath.read(req.returnresponseasstring(),"$.data[0].statusNameFrom").toString());
		AssertJUnit.assertNotNull("statusToCode is null", JsonPath.read(req.returnresponseasstring(),"$.data[0].statusToCode").toString());
		AssertJUnit.assertNotNull("statusToName is null", JsonPath.read(req.returnresponseasstring(),"$.data[0].statusToName").toString());
		AssertJUnit.assertNotNull("role is null", JsonPath.read(req.returnresponseasstring(),"$.data[0].role").toString());
	}
	
	@Test(groups = { "e2eCMSRegression" }, priority = 13, description = "Verify response of jobtracker/findactivejobsbyjobtype?jobType=${0} api")
	public void jobRegistryTest() throws Exception {
		RequestGenerator req = e2eRegressionCmsHelper.getRequestObjForCMSForQueryParam(ServiceType.CMS_CATALOG, APINAME.CMS_JOBREGISTRY, new String[] {"SIZING_UPDATE"});
		AssertJUnit.assertEquals("Status is not 200", 200, req.response.getStatus());
		AssertJUnit.assertEquals("jobType is not correct", "SIZING_UPDATE", JsonPath.read(req.returnresponseasstring(),"$.data[0].jobType").toString());
		AssertJUnit.assertNotNull("concurrencyLimit is null", JsonPath.read(req.returnresponseasstring(),"$.data[0].concurrencyLimit").toString());
		AssertJUnit.assertNotNull("maxRetries is null", JsonPath.read(req.returnresponseasstring(),"$.data[0].maxRetries").toString());
		AssertJUnit.assertNotNull("maxEntries is null", JsonPath.read(req.returnresponseasstring(),"$.data[0].maxEntries").toString());
		AssertJUnit.assertNotNull("processingQueueName is null", JsonPath.read(req.returnresponseasstring(),"$.data[0].processingQueueName").toString());
		AssertJUnit.assertNotNull("errorQueueName is null", JsonPath.read(req.returnresponseasstring(),"$.data[0].errorQueueName").toString());
		AssertJUnit.assertNotNull("processingQueueChannelName is null", JsonPath.read(req.returnresponseasstring(),"$.data[0].processingQueueChannelName").toString());
		AssertJUnit.assertNotNull("errorQueueChannelName is null", JsonPath.read(req.returnresponseasstring(),"$.data[0].errorQueueChannelName").toString());
	}
	
}