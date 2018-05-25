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

public class SellerServiceDelegateTest extends CommonUtils {

	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(SellerServiceDelegateTest.class);
	

	@BeforeMethod(alwaysRun = true)
	public void openHome() {
		log.info("Entry");
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 1, description = "Verify response of /seller/search")
	public void sellerSearchTest() throws Exception {
		RequestGenerator req = e2eRegressionCmsHelper.getRequestObjForCMSForQueryParam(ServiceType.CMS_CATALOG, APINAME.CMS_SELLERSEARCH, null);
		AssertJUnit.assertEquals("Status is not 200", 200, req.response.getStatus());
		AssertJUnit.assertNotNull("id is null", JsonPath.read(req.returnresponseasstring(),"$.data[0].id").toString());
		AssertJUnit.assertNotNull("barcode is null", JsonPath.read(req.returnresponseasstring(),"$.data[0].barcode").toString());
		AssertJUnit.assertNotNull("description is null", JsonPath.read(req.returnresponseasstring(),"$.data[0].description").toString());
		AssertJUnit.assertNotNull("enabled is null", JsonPath.read(req.returnresponseasstring(),"$.data[0].enabled").toString());
		AssertJUnit.assertNotNull("name is null", JsonPath.read(req.returnresponseasstring(),"$.data[0].name").toString());
		AssertJUnit.assertNotNull("displayName is null", JsonPath.read(req.returnresponseasstring(),"$.data[0].displayName").toString());
		AssertJUnit.assertNotNull("status is null", JsonPath.read(req.returnresponseasstring(),"$.data[0].status").toString());
		AssertJUnit.assertNotNull("status is null", JsonPath.read(req.returnresponseasstring(),"$.data[0].kycDocumentEntries[0].sellerId").toString());
		AssertJUnit.assertNotNull("status is null", JsonPath.read(req.returnresponseasstring(),"$.data[0].kycDocumentEntries[0].documentType").toString());
		AssertJUnit.assertNotNull("status is null", JsonPath.read(req.returnresponseasstring(),"$.data[0].kycDocumentEntries[0].documentNumber").toString());
		AssertJUnit.assertNotNull("status is null", JsonPath.read(req.returnresponseasstring(),"$.data[0].kycDocumentEntries[0].documentRefId").toString());
		AssertJUnit.assertNotNull("status is null", JsonPath.read(req.returnresponseasstring(),"$.data[0].kycDocumentEntries[0].documentExtension").toString());
		AssertJUnit.assertNotNull("status is null", JsonPath.read(req.returnresponseasstring(),"$.data[0].kycDocumentEntries[0].kycEntityType").toString());
	}
	
	@Test(groups = { "e2eCMSRegression" }, priority = 2, description = "Verify response of /seller/styleDetails")
	public void sellerStyleDetailsTest() throws Exception {
		RequestGenerator req = e2eRegressionCmsHelper.getRequestObjForCMSForPayloadAndQueryParam(APINAME.CMS_SELLERSTYLEDETAILS, new String[]{"123456"}, null);
		AssertJUnit.assertEquals("Status is not 200", 200, req.response.getStatus());
		AssertJUnit.assertEquals("styleId is not correct", "123456", JsonPath.read(req.returnresponseasstring(),"$.sellerDetailsEntries[0].styleId").toString());
		AssertJUnit.assertNotNull("skuId is null", JsonPath.read(req.returnresponseasstring(),"$.sellerDetailsEntries[0].skuSellerEntryList[0].skuId").toString());
		AssertJUnit.assertNotNull("sellerId is null", JsonPath.read(req.returnresponseasstring(),"$.sellerDetailsEntries[0].skuSellerEntryList[0].sellerId").toString());
		AssertJUnit.assertNotNull("listingId is null", JsonPath.read(req.returnresponseasstring(),"$.sellerDetailsEntries[0].skuSellerEntryList[0].listingId").toString());
	}
	
		
}