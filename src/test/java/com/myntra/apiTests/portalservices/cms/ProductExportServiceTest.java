package com.myntra.apiTests.portalservices.cms;

import org.apache.log4j.Logger;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.myntra.apiTests.common.APINAME;
import com.myntra.apiTests.common.ServiceType;
import com.myntra.apiTests.portalservices.commons.CommonUtils;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.gandalf.RequestGenerator;


/**
 * @author Vaishali Behere
 * 
 */

public class ProductExportServiceTest extends CommonUtils {

	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(ProductExportServiceTest.class);
	SoftAssert sft;
	

	@BeforeMethod(alwaysRun = true)
	public void openHome() {
		log.info("Entry");
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 1, description = "Verify response of style template")
	public void styleTemplateTest() throws Exception {
		sft = new SoftAssert();
		String articleId = "90";
		RequestGenerator req = e2eRegressionCmsHelper.getRequestObjForCMSForQueryParam(ServiceType.CMS_CATALOG, APINAME.PRODUCT_EXPORT_STYLETEMPLATE, new String[]{articleId});
		sft.assertEquals(req.response.getStatus(), 200 , "Status is not 200");
	}
	
	@Test(groups = { "e2eCMSRegression" }, priority = 2, description = "Verify response of sku template")
	public void skuTemplateTest() throws Exception {
		sft = new SoftAssert();
		String articleId = "90";
		RequestGenerator req = e2eRegressionCmsHelper.getRequestObjForCMSForQueryParam(ServiceType.CMS_CATALOG, APINAME.PRODUCT_EXPORT_SKUTEMPLATE, new String[]{articleId});
		sft.assertEquals(req.response.getStatus(), 200 , "Status is not 200");
	}
	
	@Test(groups = { "e2eCMSRegression" }, priority = 3, description = "Verify response of style export")
	public void styleExportTest() throws Exception {
		sft = new SoftAssert();
		String productId = "123456";
		RequestGenerator req = e2eRegressionCmsHelper.getRequestObjForCMSForQueryParam(ServiceType.CMS_CATALOG, APINAME.PRODUCT_EXPORT_STYLE_SKU, new String[]{productId, "style"});
		sft.assertEquals(req.response.getStatus(), 200 , "Status is not 200");
	}
	
	@Test(groups = { "e2eCMSRegression" }, priority = 4, description = "Verify response of sku export")
	public void skuExportTest() throws Exception {
		sft = new SoftAssert();
		String productId = "123456";
		RequestGenerator req = e2eRegressionCmsHelper.getRequestObjForCMSForQueryParam(ServiceType.CMS_CATALOG, APINAME.PRODUCT_EXPORT_STYLE_SKU, new String[]{productId, "sku"});
		sft.assertEquals(req.response.getStatus(), 200 , "Status is not 200");
	}
	
	@Test(groups = { "e2eCMSRegression" }, priority = 5, description = "Verify response of sizing template")
	public void sizingTemplateTest() throws Exception {
		sft = new SoftAssert();
		String articleId = "90";
		RequestGenerator req = e2eRegressionCmsHelper.getRequestObjForCMSForQueryParam(ServiceType.CMS_CATALOG, APINAME.PRODUCT_EXPORT_SIZINGTEMPLATE, new String[]{articleId});
		sft.assertEquals(req.response.getStatus(), 200 , "Status is not 200");
	}
	
	
}
