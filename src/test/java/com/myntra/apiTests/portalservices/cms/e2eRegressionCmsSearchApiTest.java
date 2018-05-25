package com.myntra.apiTests.portalservices.cms;

import com.myntra.apiTests.common.APINAME;
import com.myntra.apiTests.common.ServiceType;
import com.myntra.apiTests.dataproviders.CMSAPIDP;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.aragorn.pages.WebPage;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.testng.AssertJUnit;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;


/**
 * @author Vaishali Behere
 * 
 */

public class e2eRegressionCmsSearchApiTest extends CMSAPIDP {

	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(e2eRegressionCmsSearchApiTest.class);

	@BeforeClass(groups = { "e2eCMSRegression" })
	public void styleClearCache(){
		for(int i=0; i<CMSAPIDP.dp_styles.length; i++){
			RequestGenerator req = e2eRegressionCmsHelper.getRequestObjForCMSForQueryParam(ServiceType.CMS_CATALOG, APINAME.CMSCLEARCACHE, new String[] { CMSAPIDP.dp_styles[i] });
			log.info("clearcache response = " + req.returnresponseasstring());
			AssertJUnit.assertEquals("Status code does not match", 200,
					req.response.getStatus());
		}
	}
	
	@BeforeMethod(alwaysRun = true)
	public void openHome() {
		log.info("Entry");
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 1, description = "Verify search api for basecolour")
	public void verifySearchByBaseColour() throws JSONException {
		RequestGenerator req = e2eRegressionCmsHelper.getRequestObjForCMSForQueryParam(ServiceType.CMS_CATALOG, APINAME.CMSSEARCHGENERIC_IN, new String[]{ "baseColour", "Brown", "globalAttributes"});
		AssertJUnit.assertEquals("Status code does not match", 200, req.response.getStatus());
		JSONArray jsonArray = e2eRegressionCmsHelper.getJSONArrayInsideData(req);
		for(int i=0; i<jsonArray.length();i++){
			AssertJUnit.assertEquals("\"Brown\"", req.respvalidate.GetNodeValue("globalAttributes.baseColour.attributeValue", jsonArray.getJSONObject(i).toString()));
		}
	}
	
	@Test(groups = { "e2eCMSRegression" }, priority = 2, description = "Verify search api for gender")
	public void verifySearchByGender() throws JSONException {
		RequestGenerator req = e2eRegressionCmsHelper.getRequestObjForCMSForQueryParam(ServiceType.CMS_CATALOG, APINAME.CMSSEARCHGENERIC_IN, new String[]{"gender","Unisex", "globalAttributes"});
		AssertJUnit.assertEquals("Status code does not match", 200, req.response.getStatus());
		JSONArray jsonArray = e2eRegressionCmsHelper.getJSONArrayInsideData(req);
		for(int i=0; i<jsonArray.length();i++){
			AssertJUnit.assertEquals("\"Unisex\"", req.respvalidate.GetNodeValue("globalAttributes.gender.attributeValue", jsonArray.getJSONObject(i).toString()));
		}
	}
	
	@Test(groups = { "e2eCMSRegression" }, priority = 3, description = "Verify search api for season")
	public void verifySearchBySeason() throws JSONException {
		RequestGenerator req = e2eRegressionCmsHelper.getRequestObjForCMSForQueryParam(ServiceType.CMS_CATALOG, APINAME.CMSSEARCHGENERIC_IN, new String[]{"season","Summer", "globalAttributes"});
		AssertJUnit.assertEquals("Status code does not match", 200, req.response.getStatus());
		JSONArray jsonArray = e2eRegressionCmsHelper.getJSONArrayInsideData(req);
		for(int i=0; i<jsonArray.length();i++){
			AssertJUnit.assertEquals("\"Summer\"", req.respvalidate.GetNodeValue("globalAttributes.season.attributeValue", jsonArray.getJSONObject(i).toString()));
		}
	}
	
	@Test(groups = { "e2eCMSRegression" }, priority = 4, description = "Verify search api for try and buy enabled")
	public void verifySearchForTryAndBuyEnabled() throws JSONException {
		RequestGenerator req = e2eRegressionCmsHelper.getRequestObjForCMSForQueryParam(ServiceType.CMS_CATALOG, APINAME.CMSSEARCHGENERIC_EQ, new String[]{"articleType.isTryAndBuyEnabled","true", "articleType"});
		AssertJUnit.assertEquals("Status code does not match", 200, req.response.getStatus());
		JSONArray jsonArray = e2eRegressionCmsHelper.getJSONArrayInsideData(req);
		for(int i=0; i<jsonArray.length();i++){
			AssertJUnit.assertEquals("true", req.respvalidate.GetNodeValue("articleType.isTryAndBuyEnabled", jsonArray.getJSONObject(i).toString()));
		}
	}
	
	@Test(groups = { "e2eCMSRegression" }, priority = 5, description = "Verify search api for sku code")
	public void verifySearchBySkuCode() {
		RequestGenerator req = e2eRegressionCmsHelper.getRequestObjForCMSForQueryParam(ServiceType.CMS_CATALOG, APINAME.CMSSEARCHGENERIC_IN, new String[]{"productOptions.sku.skuId","66377", "status"});
		AssertJUnit.assertEquals("Status code does not match", 200, req.response.getStatus());
		AssertJUnit.assertTrue(req.returnresponseasstring().contains("\"statusCode\" : 3"));
	}
	
	@Test(groups = { "e2eCMSRegression" }, priority = 6, description = "Verify search api for product type")
	public void verifySearchByProductType() throws JSONException {
		RequestGenerator req = e2eRegressionCmsHelper.getRequestObjForCMSForQueryParam(ServiceType.CMS_CATALOG, APINAME.CMSSEARCHGENERIC_EQ, new String[]{"productType.name","Skirts", "productDisplayName"});
		AssertJUnit.assertEquals("Status code does not match", 200, req.response.getStatus());
		JSONArray jsonArray = e2eRegressionCmsHelper.getJSONArrayInsideData(req);
		for(int i=0; i<jsonArray.length();i++){
			AssertJUnit.assertTrue(req.respvalidate.GetNodeValue("productDisplayName", jsonArray.getJSONObject(i).toString()).contains("Skirt"));
		}
	}
	
	@Test(groups = { "e2eCMSRegression" }, priority = 7, description = "Verify search api for article type attribute value")
	public void verifySearchByArticleTypeAttributeValue() throws JSONException {
		RequestGenerator req = e2eRegressionCmsHelper.getRequestObjForCMSForQueryParam(ServiceType.CMS_CATALOG, APINAME.CMSSEARCHGENERIC_EQ, new String[]{"styleAttributeValues.attributeValue","Cotton", "styleAttributeValues.attributeValue"});
		AssertJUnit.assertEquals("Status code does not match", 200, req.response.getStatus());
		JSONArray jsonArray1 = e2eRegressionCmsHelper.getJSONArrayInsideData(req);
		for(int i=0; i<jsonArray1.length();i++){
			JSONArray jsonArray2 = (JSONArray) jsonArray1.getJSONObject(i).get("productArticleTypeAttributeValues");
			ArrayList<String> arr = new ArrayList<String>();
			for(int j=0;j<jsonArray2.length();j++){
				arr.add(req.respvalidate.GetNodeValue("attributeValue", jsonArray2.getJSONObject(j).toString()));
			}
			AssertJUnit.assertTrue("Cotton is not present", arr.contains("\"Cotton\""));
		}
	}
	
	@AfterClass(alwaysRun = true)
	public void tearDown() {
		log.info("Entry");
		WebPage.endTest();
		log.info("Exit");

	}
}
