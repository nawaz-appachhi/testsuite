package com.myntra.apiTests.portalservices.cms;

import org.apache.log4j.Logger;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.jayway.jsonpath.JsonPath;
import com.myntra.apiTests.common.APINAME;
import com.myntra.apiTests.portalservices.commons.CommonUtils;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.gandalf.RequestGenerator;


/**
 * @author Vaishali Behere
 * 
 */

public class MiscServiceWithListTest extends CommonUtils {

	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(MiscServiceWithListTest.class);
	private SoftAssert sft;
	String groupId;
	String patternName;

	@BeforeMethod(alwaysRun = true)
	public void openHome() {
		log.info("Entry");
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 1, description = "Verify response of /product/optionsku/skuids api")
	public void optionSkusResponse() throws Exception {
		sft = new SoftAssert();
		RequestGenerator req = e2eRegressionCmsHelper.getRequestObjForCMSForPayloadAndQueryParam(APINAME.CMS_LISTASPAYLOAD, new String[]{"195263,195264"}, new String[]{"product/optionsku/skuids"});
		sft.assertEquals(200, req.response.getStatus(), "Status code is not 200");
		sft.assertNotNull(JsonPath.read(req.returnresponseasstring(),"$.data[0].id").toString(), "id is null");
		sft.assertNotNull(JsonPath.read(req.returnresponseasstring(),"$.data[0].name").toString(), "name is null");
		sft.assertNotNull(JsonPath.read(req.returnresponseasstring(),"$.data[0].value").toString(), "value is null");
		sft.assertNotNull(JsonPath.read(req.returnresponseasstring(),"$.data[0].isActive").toString(), "isActive is null");
		sft.assertNotNull(JsonPath.read(req.returnresponseasstring(),"$.data[0].allSize").toString(), "allSize is null");
		sft.assertNotNull(JsonPath.read(req.returnresponseasstring(),"$.data[0].measurement").toString(), "measurement is null");
		sft.assertNotNull(JsonPath.read(req.returnresponseasstring(),"$.data[0].styleId").toString(), "styleId is null");
		sft.assertNotNull(JsonPath.read(req.returnresponseasstring(),"$.data[0].unifiedSizeValueId").toString(), "unifiedSizeValueId is null");
		sft.assertNotNull(JsonPath.read(req.returnresponseasstring(),"$.data[0].unifiedSize").toString(), "unifiedSize is null");
		sft.assertNotNull(JsonPath.read(req.returnresponseasstring(),"$.data[0].sizeChartScaleValueId").toString(), "sizeChartScaleValueId is null");
		sft.assertNotNull(JsonPath.read(req.returnresponseasstring(),"$.data[0].skuId").toString(), "skuId is null");
		sft.assertNotNull(JsonPath.read(req.returnresponseasstring(),"$.data[0].active").toString(), "active is null");
		sft.assertAll();
	}
	
	@Test(groups = { "e2eCMSRegression" }, priority = 2, description = "Verify response of /product/optionsku/pim/skuids api")
	public void optionSkusPimResponse() throws Exception {
		sft = new SoftAssert();
		RequestGenerator req = e2eRegressionCmsHelper.getRequestObjForCMSForPayloadAndQueryParam(APINAME.CMS_LISTASPAYLOAD, new String[]{"195263,195264"}, new String[]{"product/optionsku/pim/skuids"});
		sft.assertEquals(200, req.response.getStatus(), "Status code is not 200");
		sft.assertNotNull(JsonPath.read(req.returnresponseasstring(),"$.data[0].id").toString(), "id is null");
		sft.assertNotNull(JsonPath.read(req.returnresponseasstring(),"$.data[0].name").toString(), "name is null");
		sft.assertNotNull(JsonPath.read(req.returnresponseasstring(),"$.data[0].value").toString(), "value is null");
		sft.assertNotNull(JsonPath.read(req.returnresponseasstring(),"$.data[0].isActive").toString(), "isActive is null");
		sft.assertNotNull(JsonPath.read(req.returnresponseasstring(),"$.data[0].allSize").toString(), "allSize is null");
		sft.assertNotNull(JsonPath.read(req.returnresponseasstring(),"$.data[0].measurement").toString(), "measurement is null");
		sft.assertNotNull(JsonPath.read(req.returnresponseasstring(),"$.data[0].unifiedSizeValueId").toString(), "unifiedSizeValueId is null");
		sft.assertNotNull(JsonPath.read(req.returnresponseasstring(),"$.data[0].unifiedSize").toString(), "unifiedSize is null");
		sft.assertNotNull(JsonPath.read(req.returnresponseasstring(),"$.data[0].skuId").toString(), "skuId is null");
		sft.assertNotNull(JsonPath.read(req.returnresponseasstring(),"$.data[0].active").toString(), "active is null");
		sft.assertAll();
	}
	
	@Test(groups = { "e2eCMSRegression" }, priority = 3, description = "Verify response of /StyleACL api")
	public void styleACLResponse() throws Exception {
		sft = new SoftAssert();
		RequestGenerator req = e2eRegressionCmsHelper.getRequestObjForCMSForPayloadAndQueryParam(APINAME.CMS_LISTASPAYLOAD, new String[]{"\"CMS_TECH\""}, new String[]{"StyleACL"});
		sft.assertEquals(200, req.response.getStatus(), "Status code is not 200");
		sft.assertNotNull(JsonPath.read(req.returnresponseasstring(),"$.validFieldList").toString(), "validFieldList is null");
		sft.assertNotNull(JsonPath.read(req.returnresponseasstring(),"$.validFieldListDIY").toString(), "validFieldListDIY is null");
		sft.assertAll();
	}
	
	@Test(groups = { "e2eCMSRegression" }, priority = 4, description = "Verify response of /stylegroup/color/searchbystyles api")
	public void styleGrp_SearchStyles() throws Exception {
		sft = new SoftAssert();
		RequestGenerator req = e2eRegressionCmsHelper.getRequestObjForCMSForPayloadAndQueryParam(APINAME.CMS_LISTASPAYLOAD, new String[]{"123456, 123457"}, new String[]{"stylegroup/color/searchbystyles"});
		sft.assertEquals(200, req.response.getStatus(), "Status code is not 200");
		sft.assertNotNull(JsonPath.read(req.returnresponseasstring(),"$.result.123456.groupId").toString(), "groupId is null");
		sft.assertNotNull(JsonPath.read(req.returnresponseasstring(),"$.result.123456.groupType").toString(), "groupType is null");
		sft.assertNotNull(JsonPath.read(req.returnresponseasstring(),"$.result.123456.patternName").toString(), "patternName is null");
		sft.assertNotNull(JsonPath.read(req.returnresponseasstring(),"$.result.123456.styleIdList").toString(), "styleIdList is null");
		sft.assertNotNull(JsonPath.read(req.returnresponseasstring(),"$.result.123457.groupId").toString(), "groupId is null");
		sft.assertNotNull(JsonPath.read(req.returnresponseasstring(),"$.result.123457.groupType").toString(), "groupType is null");
		sft.assertNotNull(JsonPath.read(req.returnresponseasstring(),"$.result.123457.patternName").toString(), "patternName is null");
		sft.assertNotNull(JsonPath.read(req.returnresponseasstring(),"$.result.123457.styleIdList").toString(), "styleIdList is null");
		groupId = JsonPath.read(req.returnresponseasstring(),"$.result.123456.groupId").toString();
		patternName = JsonPath.read(req.returnresponseasstring(),"$.result.123456.patternName").toString();
		sft.assertAll();
	}
	
	@Test(groups = { "e2eCMSRegression" }, priority = 5, description = "Verify response of /stylegroup/mergegroups api", dependsOnMethods = { "styleGrp_SearchStyles" })
	public void styleGrp_MergeGroups() throws Exception {
		sft = new SoftAssert();
		RequestGenerator req = e2eRegressionCmsHelper.getRequestObjForCMSForPayloadAndQueryParam(APINAME.CMS_LISTASPAYLOAD, new String[]{groupId}, new String[]{"/stylegroup/mergegroups?patternName="+patternName.replace("\"", "").replace(" ", "%20")});
		sft.assertEquals(200, req.response.getStatus(), "Status code is not 200");
		sft.assertNotNull(JsonPath.read(req.returnresponseasstring(),"$.data[0].groupId").toString(), "groupId is null");
		sft.assertNotNull(JsonPath.read(req.returnresponseasstring(),"$.data[0].groupType").toString(), "groupType is null");
		sft.assertNotNull(JsonPath.read(req.returnresponseasstring(),"$.data[0].patternName").toString(), "patternName is null");
		sft.assertNotNull(JsonPath.read(req.returnresponseasstring(),"$.data[0].styleIdList").toString(), "styleIdList is null");
		sft.assertAll();
	}
	
	@Test(groups = { "e2eCMSRegression" }, priority = 6, description = "Verify err msg for /stylegroup/mergegroups api")
	public void styleGrp_MergeGroups_ErrMsg() throws Exception {
		sft = new SoftAssert();
		RequestGenerator req = e2eRegressionCmsHelper.getRequestObjForCMSForPayloadAndQueryParam(APINAME.CMS_LISTASPAYLOAD, new String[]{"0"}, new String[]{"/stylegroup/mergegroups?patternName=test"});
		sft.assertEquals(200, req.response.getStatus(), "Status code is not 200");
		sft.assertEquals(JsonPath.read(req.returnresponseasstring(),"$.status.statusMessage").toString(), "Incorrect group ids given", "Incorrect error message");
		sft.assertAll();
	}
	
	@Test(groups = { "e2eCMSRegression" }, priority = 7, description = "Verify response of /stylegroup/color/addstyles api")
	public void styleGrp_AddStylesErr() throws Exception {
		sft = new SoftAssert();
		RequestGenerator req = e2eRegressionCmsHelper.getRequestObjForCMSForPayloadAndQueryParam(APINAME.CMS_LISTASPAYLOAD, new String[]{"123456, 123457"}, new String[]{"stylegroup/color/addstyles?patternName=newGroup"});
		sft.assertEquals(200, req.response.getStatus(), "Status code is not 200");
		sft.assertEquals(JsonPath.read(req.returnresponseasstring(),"$.status.statusMessage").toString(), "Style IDs [123456, 123457] already exist", "Incorrect error message");
		sft.assertAll();
	}
	
	@Test(groups = { "e2eCMSRegression" }, priority = 8, description = "Verify response of /stylegroup/sku/deletestyles?groupId=192296 api")
	public void styleGrp_DeleteStyleErr() throws Exception {
		sft = new SoftAssert();
		RequestGenerator req = e2eRegressionCmsHelper.getRequestObjForCMSForPayloadAndQueryParam(APINAME.CMS_LISTASPAYLOAD, new String[]{"0"}, new String[]{"stylegroup/sku/deletestyles?groupId=0"});
		sft.assertEquals(200, req.response.getStatus(), "Status code is not 200");
		sft.assertEquals(JsonPath.read(req.returnresponseasstring(),"$.status.statusMessage").toString(), "No matching entity found for given group id, group type and style id list");
		sft.assertAll();
	}
	
	
}