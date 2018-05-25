package com.myntra.apiTests.portalservices.cms;

import org.apache.log4j.Logger;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.jayway.jsonpath.JsonPath;
import com.myntra.apiTests.common.APINAME;
import com.myntra.apiTests.common.ServiceType;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.gandalf.RequestGenerator;


/**
 * @author Vaishali Behere
 * 
 */

public class ChangeManagementTest extends SheetHelper {

	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(VirtualBundle_BundleCreation.class);
	String env = init.Configurations.GetTestEnvironemnt().toString();
	String payload;
	private SoftAssert sft;
	String physical_style1;
	String physical_style2;
	String bundle;
	String quantity = "1";
	String bundle_delist = "BD";
	
	@BeforeClass(alwaysRun = true)
	public void testSetUp() {
		if(env.equalsIgnoreCase("M7")){
			physical_style1 = "1833527";
			physical_style2 = "1833528";
			bundle = "1833529";
		}
		else if(env.equalsIgnoreCase("Stage")){
			physical_style1 = "1787486";
			physical_style2 = "1787489";
			bundle = "1787490";
		}
	}
	
	
	@BeforeMethod(alwaysRun = true)
	public void payloadCreation() {
		log.info("Entry");
		payload = "$key: $value";
		setBundleStyleStatusToDraft();
		payload = "$key: $value";
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 1, description = "Change price of physical style, bundle price should get updated")
	public void changePhysicalStylePrice() throws Exception {
		String price_updated = "150.0";
		String price_orig = "100.0";
		// Bundle price calculation :  physical_style1_price * quantity + physical_style2 * quantity
		String bundle_price = "300.0";
		sft = new SoftAssert();
		payload = payload.replace("$key", "\"price\"").replace("$value", price_updated);
		log.info("payload string: "+payload);
		RequestGenerator req1 = e2eRegressionCmsHelper.getRequestObjForCMSForPayloadAndQueryParam(APINAME.CMSUPDATESTYLE, new String[]{physical_style1, payload}, new String[]{physical_style1});
		RequestGenerator req2 = e2eRegressionCmsHelper.getRequestObjForCMSForPayloadAndQueryParam(APINAME.CMSUPDATESTYLE, new String[]{physical_style2, payload}, new String[]{physical_style2});
		sft.assertEquals(req1.response.getStatus(),200, "Physical style1 price didn't get udpated");
		sft.assertEquals(req2.response.getStatus(),200, "Physical style2 price didn't get udpated");
		e2eRegressionCmsHelper.sleep(20000);
		RequestGenerator req_bundle = e2eRegressionCmsHelper.getRequestObjForCMSForQueryParam(ServiceType.CMS_CATALOG, APINAME.CMSGETSTYLE, new String[] { bundle });
		sft.assertEquals(req_bundle.response.getStatus(), 200,"Status code does not match");
		log.info("Bundle price is: "+JsonPath.read(req_bundle.returnresponseasstring(),"$.data[0].price").toString());
		sft.assertEquals(JsonPath.read(req_bundle.returnresponseasstring(),"$.data[0].price").toString(), bundle_price, "Bundle price is not set correctly");
		log.info("Setting back the price of physical styles to original price");
		payload = payload.replace(price_updated, price_orig);
		req1 = e2eRegressionCmsHelper.getRequestObjForCMSForPayloadAndQueryParam(APINAME.CMSUPDATESTYLE, new String[]{physical_style1, payload}, new String[]{physical_style1});
		req2 = e2eRegressionCmsHelper.getRequestObjForCMSForPayloadAndQueryParam(APINAME.CMSUPDATESTYLE, new String[]{physical_style2, payload}, new String[]{physical_style2});
		sft.assertEquals(req1.response.getStatus(), 200, "Physical style1 price didn't get udpated to original");
		sft.assertEquals(req2.response.getStatus(), 200, "Physical style2 price didn't get udpated to original");
		sft.assertAll();
	}
	
	@Test(groups = { "e2eCMSRegression" }, priority = 2, description = "Change status of physical style, bundle status should go to BD")
	public void changePhysicalStyleStatus() throws Exception {
		sft = new SoftAssert();
		payload = payload.replace("$key", "\"styleStatus\"").replace("$value", "\"D\"");
		log.info("payload string: "+payload);
		RequestGenerator req1 = e2eRegressionCmsHelper.getRequestObjForCMSForPayloadAndQueryParam(APINAME.CMSUPDATESTYLE, new String[]{physical_style1, payload}, new String[]{physical_style1});
		sft.assertEquals(req1.response.getStatus(), 200, "Physical style status update response is not 200");
		e2eRegressionCmsHelper.sleep(20000);
		RequestGenerator req_bundle = e2eRegressionCmsHelper.getRequestObjForCMSForQueryParam(ServiceType.CMS_CATALOG, APINAME.CMSGETSTYLE, new String[] { bundle });
		sft.assertEquals(req_bundle.response.getStatus(), 200, "Status code does not match");
		sft.assertEquals(JsonPath.read(req_bundle.returnresponseasstring(),"$.data[0].styleStatus").toString(), bundle_delist, "Bundle status didn't change to BD");
		log.info("Setting back the status of physical styles to original");
		payload = payload.replace("D", "P");
		req1 = e2eRegressionCmsHelper.getRequestObjForCMSForPayloadAndQueryParam(APINAME.CMSUPDATESTYLE, new String[]{physical_style1, payload}, new String[]{physical_style1});
		sft.assertEquals(req1.response.getStatus(), 200, "Physical style1 status didn't get udpated to original");
		sft.assertAll();
	}
	
	@Test(groups = { "e2eCMSRegression" }, priority = 3, description = "Change global attribute agegroup of physical style, bundle status should go to BD")
	public void changePhysicalStyleAgeGroup() throws Exception {
		sft = new SoftAssert();
		payload = payload.replace("$key", "\"globalAttributes\"").replace("$value", "{\"ageGroup\":\"Adults-Unisex\"}");
		log.info(" payload string: "+payload);
		RequestGenerator req1 = e2eRegressionCmsHelper.getRequestObjForCMSForPayloadAndQueryParam(APINAME.CMSUPDATESTYLE, new String[]{physical_style1, payload}, new String[]{physical_style1});
		sft.assertEquals(req1.response.getStatus(), 200, "Physical style status update response is not 200");
		e2eRegressionCmsHelper.sleep(20000);
		RequestGenerator req_bundle = e2eRegressionCmsHelper.getRequestObjForCMSForQueryParam(ServiceType.CMS_CATALOG, APINAME.CMSGETSTYLE, new String[] { bundle });
		sft.assertEquals(req_bundle.response.getStatus(), 200, "Status code does not match");
		sft.assertEquals(JsonPath.read(req_bundle.returnresponseasstring(),"$.data[0].styleStatus").toString(), bundle_delist, "Bundle status didn't change to BD");
		log.info("Setting back the ageGroup of physical styles to original");
		payload = payload.replace("Adults-Unisex", "Adults-Women");
		req1 = e2eRegressionCmsHelper.getRequestObjForCMSForPayloadAndQueryParam(APINAME.CMSUPDATESTYLE, new String[]{physical_style1, payload}, new String[]{physical_style1});
		sft.assertEquals(req1.response.getStatus(), 200, "Physical style1 ageGroup didn't get udpated to original");
		sft.assertAll();
	}
	
	@Test(groups = { "e2eCMSRegression" }, priority = 4, description = "Change global attribute gender of physical style, bundle status should go to BD")
	public void changePhysicalStyleGender() throws Exception {
		sft = new SoftAssert();
		payload = payload.replace("$key", "\"globalAttributes\"").replace("$value", "{\"gender\":\"Unisex\"}");
		log.info(" payload string: "+payload);
		RequestGenerator req1 = e2eRegressionCmsHelper.getRequestObjForCMSForPayloadAndQueryParam(APINAME.CMSUPDATESTYLE, new String[]{physical_style1, payload}, new String[]{physical_style1});
		sft.assertEquals(req1.response.getStatus(), 200, "Physical style status update response is not 200");
		e2eRegressionCmsHelper.sleep(20000);
		RequestGenerator req_bundle = e2eRegressionCmsHelper.getRequestObjForCMSForQueryParam(ServiceType.CMS_CATALOG, APINAME.CMSGETSTYLE, new String[] { bundle });
		sft.assertEquals(req_bundle.response.getStatus(), 200, "Status code does not match");
		sft.assertEquals(JsonPath.read(req_bundle.returnresponseasstring(),"$.data[0].styleStatus").toString(), bundle_delist, "Bundle status didn't change to BD");
		log.info("Setting back the gender of physical styles to original");
		payload = payload.replace("Unisex", "Women");
		req1 = e2eRegressionCmsHelper.getRequestObjForCMSForPayloadAndQueryParam(APINAME.CMSUPDATESTYLE, new String[]{physical_style1, payload}, new String[]{physical_style1});
		sft.assertEquals(req1.response.getStatus(), 200, "Physical style1 gender didn't get udpated to original");
		sft.assertAll();
	}
	
	@Test(groups = { "e2eCMSRegression" }, priority = 5, description = "Change global attribute baseColour of physical style, bundle status should go to BD")
	public void changePhysicalStyleBaseColour() throws Exception {
		sft = new SoftAssert();
		payload = payload.replace("$key", "\"globalAttributes\"").replace("$value", "{\"baseColour\":\"Pink\"}");
		log.info("payload string: "+payload);
		RequestGenerator req1 = e2eRegressionCmsHelper.getRequestObjForCMSForPayloadAndQueryParam(APINAME.CMSUPDATESTYLE, new String[]{physical_style1, payload}, new String[]{physical_style1});
		sft.assertEquals(req1.response.getStatus(), 200, "Physical style status update response is not 200");
		e2eRegressionCmsHelper.sleep(20000);
		RequestGenerator req_bundle = e2eRegressionCmsHelper.getRequestObjForCMSForQueryParam(ServiceType.CMS_CATALOG, APINAME.CMSGETSTYLE, new String[] { bundle });
		sft.assertEquals(req_bundle.response.getStatus(), 200, "Status code does not match");
		sft.assertEquals(JsonPath.read(req_bundle.returnresponseasstring(),"$.data[0].styleStatus").toString(), bundle_delist, "Bundle status didn't change to BD");
		log.info("Setting back the baseColour of physical styles to original");
		payload = payload.replace("Pink", "Blue");
		req1 = e2eRegressionCmsHelper.getRequestObjForCMSForPayloadAndQueryParam(APINAME.CMSUPDATESTYLE, new String[]{physical_style1, payload}, new String[]{physical_style1});
		sft.assertEquals(req1.response.getStatus(), 200, "Physical style1 baseColour didn't get udpated to original");
		sft.assertAll();
	}
	
	@Test(groups = { "e2eCMSRegression" }, priority = 5, description = "Change global attribute brand of physical style, bundle status should go to BD")
	public void changePhysicalStyleBrand() throws Exception {
		sft = new SoftAssert();
		payload = payload.replace("$key", "\"globalAttributes\"").replace("$value", "{\"brandName\":\"Puma\"}");
		log.info("payload string: "+payload);
		RequestGenerator req1 = e2eRegressionCmsHelper.getRequestObjForCMSForPayloadAndQueryParam(APINAME.CMSUPDATESTYLE, new String[]{physical_style1, payload}, new String[]{physical_style1});
		sft.assertEquals(req1.response.getStatus(), 200, "Physical style status update response is not 200");
		e2eRegressionCmsHelper.sleep(20000);
		RequestGenerator req_bundle = e2eRegressionCmsHelper.getRequestObjForCMSForQueryParam(ServiceType.CMS_CATALOG, APINAME.CMSGETSTYLE, new String[] { bundle });
		sft.assertEquals(req_bundle.response.getStatus(), 200, "Status code does not match");
		sft.assertEquals(JsonPath.read(req_bundle.returnresponseasstring(),"$.data[0].styleStatus").toString(), bundle_delist, "Bundle status didn't change to BD");
		log.info("Setting back the brandName of physical styles to original");
		payload = payload.replace("Puma", "Wrangler");
		req1 = e2eRegressionCmsHelper.getRequestObjForCMSForPayloadAndQueryParam(APINAME.CMSUPDATESTYLE, new String[]{physical_style1, payload}, new String[]{physical_style1});
		sft.assertEquals(req1.response.getStatus(), 200, "Physical style1 brand didn't get udpated to original");
		sft.assertAll();
	}
	
	@Test(groups = { "e2eCMSRegression" }, priority = 6, description = "Change global attribute year of physical style, bundle status should remain in P")
	public void changePhysicalStyleYear() throws Exception {
		sft = new SoftAssert();
		payload = payload.replace("$key", "\"globalAttributes\"").replace("$value", "{\"year\":2015}");
		log.info("payload string: "+payload);
		RequestGenerator req1 = e2eRegressionCmsHelper.getRequestObjForCMSForPayloadAndQueryParam(APINAME.CMSUPDATESTYLE, new String[]{physical_style1, payload}, new String[]{physical_style1});
		sft.assertEquals(req1.response.getStatus(), 200, "Physical style status update response is not 200");
		e2eRegressionCmsHelper.sleep(20000);
		RequestGenerator req_bundle = e2eRegressionCmsHelper.getRequestObjForCMSForQueryParam(ServiceType.CMS_CATALOG, APINAME.CMSGETSTYLE, new String[] { bundle });
		sft.assertEquals(req_bundle.response.getStatus(), 200, "Status code does not match");
		sft.assertNotEquals(JsonPath.read(req_bundle.returnresponseasstring(),"$.data[0].styleStatus").toString(), bundle_delist, "Bundle status got changed to BD");
		log.info("Setting back the year of physical styles to original");
		payload = payload.replace("2015", "2011");
		req1 = e2eRegressionCmsHelper.getRequestObjForCMSForPayloadAndQueryParam(APINAME.CMSUPDATESTYLE, new String[]{physical_style1, payload}, new String[]{physical_style1});
		sft.assertEquals(req1.response.getStatus(), 200, "Physical style1 year didn't get udpated to original");
		sft.assertAll();
	}
	
	@Test(groups = { "e2eCMSRegression" }, priority = 7, description = "Change status of physical style to BO, bundle status should not go to BD")
	public void changePhysicalStyleStatusToBO() throws Exception {
		sft = new SoftAssert();
		payload = payload.replace("$key", "\"styleStatus\"").replace("$value", "\"BO\"");
		log.info("payload string: "+payload);
		RequestGenerator req1 = e2eRegressionCmsHelper.getRequestObjForCMSForPayloadAndQueryParam(APINAME.CMSUPDATESTYLE, new String[]{physical_style1, payload}, new String[]{physical_style1});
		sft.assertEquals(req1.response.getStatus(), 200, "Physical style status update response is not 200");
		e2eRegressionCmsHelper.sleep(20000);
		RequestGenerator req_bundle = e2eRegressionCmsHelper.getRequestObjForCMSForQueryParam(ServiceType.CMS_CATALOG, APINAME.CMSGETSTYLE, new String[] { bundle });
		sft.assertEquals(req_bundle.response.getStatus(), 200, "Status code does not match");
		sft.assertNotEquals(JsonPath.read(req_bundle.returnresponseasstring(),"$.data[0].styleStatus").toString(), bundle_delist, "Bundle status didn't change to BD");
		log.info("Setting back the status of physical styles to original");
		payload = payload.replace("BO", "P");
		req1 = e2eRegressionCmsHelper.getRequestObjForCMSForPayloadAndQueryParam(APINAME.CMSUPDATESTYLE, new String[]{physical_style1, payload}, new String[]{physical_style1});
		sft.assertEquals(req1.response.getStatus(), 200, "Physical style1 status didn't get udpated to original");
		sft.assertAll();
	}
	
	public void setBundleStyleStatusToDraft(){
		sft = new SoftAssert();
		payload = payload.replace("$key", "\"styleStatus\"").replace("$value", "\"D\"");
		log.info("price payload string: "+payload);
		e2eRegressionCmsHelper.sleep(20000);
		RequestGenerator req = e2eRegressionCmsHelper.getRequestObjForCMSForPayloadAndQueryParam(APINAME.CMSUPDATESTYLE, new String[]{bundle, payload}, new String[]{bundle});
		sft.assertEquals(200, req.response.getStatus(),"Bundle style update to D status is not 200");
		sft.assertAll();
	}
	
	
}
	
	
	


	

