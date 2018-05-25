package com.myntra.apiTests.portalservices.cms;

import java.util.List;
import java.util.Random;

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

public class VirtualBundle_SkuCreation extends SheetHelper {

	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(VirtualBundle_SkuCreation.class);
	String styleId;
	

	@BeforeMethod(alwaysRun = true)
	public void openHome() {
		log.info("Entry");
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 1, description = "Verify that the style creation is successful")
	public void verifyStyleCreation() throws Exception {
		RequestGenerator req = e2eRegressionCmsHelper.getRequestObjForCMSForPayloadAndQueryParam(APINAME.VB_SKUCREATION, new String[]{"0","0","Tshirts","Tshirts",String.valueOf(generateRandomGTIN()),"testvan","testvan","Basics","Basics","200","200","S","M","42031010","42031010","Men","Men","21"}, null);
		AssertJUnit.assertEquals("SKU Creation status is not 200", 200, req.response.getStatus());
		AssertJUnit.assertNotNull("Style id is null", JsonPath.read(req.returnresponseasstring(),"$.data.successEntries[0].styleID").toString());
		styleId = JsonPath.read(req.returnresponseasstring(),"$.data.successEntries[0].styleID").toString();
		AssertJUnit.assertNotNull("Sku id is null", JsonPath.read(req.returnresponseasstring(),"$.data.successEntries[0].skusInfo[0].skuID").toString());
		AssertJUnit.assertNotNull("Style id is null", JsonPath.read(req.returnresponseasstring(),"$.data.successEntries[0].skusInfo[0].styleID").toString());
		AssertJUnit.assertNotNull("Sku code is null", JsonPath.read(req.returnresponseasstring(),"$.data.successEntries[0].skusInfo[0].skuCode").toString());
		AssertJUnit.assertNotNull("unifiedSizeValueId is null", JsonPath.read(req.returnresponseasstring(),"$.data.successEntries[0].skusInfo[0].unifiedSizeValueId").toString());
		AssertJUnit.assertNotNull("van is null", JsonPath.read(req.returnresponseasstring(),"$.data.successEntries[0].skusInfo[0].van").toString());
		List<String> skuList = JsonPath.read(req.returnresponseasstring(),"$.data.successEntries[0].skusInfo[*].skuID");
		AssertJUnit.assertEquals("All skus didn't get created", 2, skuList.size());
	}
	
	@Test(groups = { "e2eCMSRegression" }, priority = 2, description = "Check error message for different group id")
	public void verifyErrMsgForDifferentGroupId() throws Exception {
		RequestGenerator req = e2eRegressionCmsHelper.getRequestObjForCMSForPayloadAndQueryParam(APINAME.VB_SKUCREATION, new String[]{"0","1","Tshirts","Tshirts",String.valueOf(generateRandomGTIN()),"testvan","testvan","Basics","Basics","200","200","S","M","42031010","42031010","Men","Men","21"}, null);
		AssertJUnit.assertEquals("SKU Creation status is not 200", 200, req.response.getStatus());
		assertSyncSKUCreationErrorEntries(req.returnresponseasstring(), new ErrorEntryType[]{ErrorEntryType.SKUCREATION_DIFF_GRPID}, null);
	}
	
	@Test(groups = { "e2eCMSRegression" }, priority = 3, description = "Check error message for different article types")
	public void verifyErrMsgForDifferentArticleTypes() throws Exception {
		String errorInfo = "Article type error";
		String errorMessage = "All the rows listed do not belong to the same article type";
		RequestGenerator req = e2eRegressionCmsHelper.getRequestObjForCMSForPayloadAndQueryParam(APINAME.VB_SKUCREATION, new String[]{"0","0","Tshirts","Shirts",String.valueOf(generateRandomGTIN()),"testvan","testvan","Basics","Basics","200","200","S","M","42031010","42031010","Men","Men","21"}, null);
		AssertJUnit.assertEquals("SKU Creation status is not 200", 200, req.response.getStatus());
		String errorInfo_resp = JsonPath.read(req.returnresponseasstring(), "$.data.errorEntries[0].errorInfo").toString();
		String errorMsg_resp = JsonPath.read(req.returnresponseasstring(), "$.data.errorEntries[0].errorMessage").toString();
		AssertJUnit.assertEquals("No error for different brands", errorInfo, errorInfo_resp);
		AssertJUnit.assertEquals("No error for different brands", errorMessage, errorMsg_resp);
	}
	
	@Test(groups = { "e2eCMSRegression" }, priority = 4, description = "Check error message for different brand per groupid")
	public void verifyErrMsgForDifferentBrand() throws Exception {
		RequestGenerator req = e2eRegressionCmsHelper.getRequestObjForCMSForPayloadAndQueryParam(APINAME.VB_SKUCREATION, new String[]{"0","0","Tshirts","Tshirts",String.valueOf(generateRandomGTIN()),"testvan","testvan","Basics","Puma","200","200","S","M","42031010","42031010","Men","Men","21"}, null);
		AssertJUnit.assertEquals("SKU Creation status is not 200", 200, req.response.getStatus());
		ErrorEntryType[] errorEntry = new ErrorEntryType[] {ErrorEntryType.PROD_LEVEL_VALIDATIONS};
		assertSyncSKUCreationErrorEntries(req.returnresponseasstring(), errorEntry, new String[]{"brand", "Tshirts", "0"});
	}
	
	@Test(groups = { "e2eCMSRegression" }, priority = 5, description = "Check error message for different gender per groupid")
	public void verifyErrMsgForDifferentGender() throws Exception {
		RequestGenerator req = e2eRegressionCmsHelper.getRequestObjForCMSForPayloadAndQueryParam(APINAME.VB_SKUCREATION, new String[]{"0","0","Tshirts","Tshirts",String.valueOf(generateRandomGTIN()),"testvan","testvan","Basics","Basics","200","200","S","M","42031010","42031010","Men","Women","21"}, null);
		AssertJUnit.assertEquals("SKU Creation status is not 200", 200, req.response.getStatus());
		ErrorEntryType[] errorEntry = new ErrorEntryType[] {ErrorEntryType.PROD_LEVEL_VALIDATIONS};
		assertSyncSKUCreationErrorEntries(req.returnresponseasstring(), errorEntry, new String[]{"global Attribute Gender", "Tshirts", "0"});
	}
	
	@Test(groups = { "e2eCMSRegression" }, priority = 6, description = "Invalid gtin error")
	public void verifyErrMsgForInvalidGTIN() throws Exception {
		RequestGenerator req = e2eRegressionCmsHelper.getRequestObjForCMSForPayloadAndQueryParam(APINAME.VB_SKUCREATION, new String[]{"0","0","Tshirts","Tshirts","test","testvan","testvan","Basics","Basics","200","200","S","M","42031010","42031010","Men","Men","21"}, null);
		AssertJUnit.assertEquals("SKU Creation status is not 200", 200, req.response.getStatus());
		ErrorEntryType[] errorEntry = new ErrorEntryType[] {ErrorEntryType.SKUCREATION_INVALIDGTIN};
		assertSyncSKUCreationErrorEntries(req.returnresponseasstring(), errorEntry, new String[]{"test"});
	}
	
	@Test(groups = { "e2eCMSRegression" }, priority = 7, description = "Empty van error")
	public void verifyErrMsgForEmptyVan() throws Exception {
		RequestGenerator req = e2eRegressionCmsHelper.getRequestObjForCMSForPayloadAndQueryParam(APINAME.VB_SKUCREATION, new String[]{"0","0","Tshirts","Tshirts","123456","","testvan","Basics","Basics","200","200","S","M","42031010","42031010","Men","Men","21"}, null);
		AssertJUnit.assertEquals("SKU Creation status is not 200", 200, req.response.getStatus());
		ErrorEntryType[] errorEntry = new ErrorEntryType[] {ErrorEntryType.EMPTY_VAN, ErrorEntryType.EMPTY_VENDORNAME};
		assertSyncSKUCreationErrorEntries(req.returnresponseasstring(), errorEntry, null);
	}
	
	@Test(groups = { "e2eCMSRegression" }, priority = 8, description = "Empty brand")
	public void verifyErrMsgForEmptyBrand() throws Exception {
		RequestGenerator req = e2eRegressionCmsHelper.getRequestObjForCMSForPayloadAndQueryParam(APINAME.VB_SKUCREATION, new String[]{"0","0","Tshirts","Tshirts","123456","testvan","testvan","","Basics","200","200","S","M","42031010","42031010","Men","Men","21"}, null);
		AssertJUnit.assertEquals("SKU Creation status is not 200", 200, req.response.getStatus());
		ErrorEntryType[] errorEntry = new ErrorEntryType[] {ErrorEntryType.EMPTY_BRAND};
		assertSyncSKUCreationErrorEntries(req.returnresponseasstring(), errorEntry, null);
	}
	
	@Test(groups = { "e2eCMSRegression" }, priority = 9, description = "Invalid MRP")
	public void verifyErrMsgForInvalidMRP() throws Exception {
		RequestGenerator req = e2eRegressionCmsHelper.getRequestObjForCMSForPayloadAndQueryParam(APINAME.VB_SKUCREATION, new String[]{"0","0","Tshirts","Tshirts","123456","testvan","testvan","Basics","Basics","0","200","S","M","42031010","42031010","Men","Men","21"}, null);
		AssertJUnit.assertEquals("SKU Creation status is not 200", 200, req.response.getStatus());
		ErrorEntryType[] errorEntry = new ErrorEntryType[] {ErrorEntryType.ZERO_MRP};
		assertSyncSKUCreationErrorEntries(req.returnresponseasstring(), errorEntry, null);
	}
	
	@Test(groups = { "e2eCMSRegression" }, priority = 10, description = "Empty product size")
	public void verifyErrMsgForEmptyProductSize() throws Exception {
		RequestGenerator req = e2eRegressionCmsHelper.getRequestObjForCMSForPayloadAndQueryParam(APINAME.VB_SKUCREATION, new String[]{"0","0","Tshirts","Tshirts","123456","testvan","testvan","Basics","Basics","200","200","","M","42031010","42031010","Men","Men","21"}, null);
		AssertJUnit.assertEquals("SKU Creation status is not 200", 200, req.response.getStatus());
		ErrorEntryType[] errorEntry = new ErrorEntryType[] {ErrorEntryType.EMPTY_PRODUCTSIZE};
		assertSyncSKUCreationErrorEntries(req.returnresponseasstring(), errorEntry, null);
	}
	
	@Test(groups = { "e2eCMSRegression" }, priority = 11, description = "Empty hsn")
	public void verifyErrMsgForEmptyHSN() throws Exception {
		RequestGenerator req = e2eRegressionCmsHelper.getRequestObjForCMSForPayloadAndQueryParam(APINAME.VB_SKUCREATION, new String[]{"0","0","Tshirts","Tshirts","123456","testvan","testvan","Basics","Basics","200","200","S","M","","42031010","Men","Men","21"}, null);
		AssertJUnit.assertEquals("SKU Creation status is not 200", 200, req.response.getStatus());
		ErrorEntryType[] errorEntry = new ErrorEntryType[] {ErrorEntryType.EMPTY_HSN};
		assertSyncSKUCreationErrorEntries(req.returnresponseasstring(), errorEntry, null);
	}
	
	@Test(groups = { "e2eCMSRegression" }, priority = 12, description = "Non-numeric hsn")
	public void verifyErrMsgForNonNumericHSN() throws Exception {
		RequestGenerator req = e2eRegressionCmsHelper.getRequestObjForCMSForPayloadAndQueryParam(APINAME.VB_SKUCREATION, new String[]{"0","0","Tshirts","Tshirts","123456","testvan","testvan","Basics","Basics","200","200","S","M","test","42031010","Men","Men","21"}, null);
		AssertJUnit.assertEquals("SKU Creation status is not 200", 200, req.response.getStatus());
		ErrorEntryType[] errorEntry = new ErrorEntryType[] {ErrorEntryType.NONNUMERIC_HSN};
		assertSyncSKUCreationErrorEntries(req.returnresponseasstring(), errorEntry, new String[]{"test"});
	}
	
	@Test(groups = { "e2eCMSRegression" }, priority = 13, description = "Non 8 digit hsn")
	public void verifyErrMsgForNon8DigitHSN() throws Exception {
		RequestGenerator req = e2eRegressionCmsHelper.getRequestObjForCMSForPayloadAndQueryParam(APINAME.VB_SKUCREATION, new String[]{"0","0","Tshirts","Tshirts","123456","testvan","testvan","Basics","Basics","200","200","S","M","4203101010","4203101010","Men","Men","21"}, null);
		AssertJUnit.assertEquals("SKU Creation status is not 200", 200, req.response.getStatus());
		ErrorEntryType[] errorEntry = new ErrorEntryType[] {ErrorEntryType.NON_EIGHTDIGIT_HSN};
		assertSyncSKUCreationErrorEntries(req.returnresponseasstring(), errorEntry, null);
	}
	
	@Test(groups = { "e2eCMSRegression" }, priority = 14, description = "Error for missing hsn entry in main registry")
	public void verifyErrMsgForMissingHSNInRegistry() throws Exception {
		RequestGenerator req = e2eRegressionCmsHelper.getRequestObjForCMSForPayloadAndQueryParam(APINAME.VB_SKUCREATION, new String[]{"0","0","Tshirts","Tshirts","123456","testvan","testvan","Basics","Basics","200","200","S","M","11111111","11111111","Men","Men","21"}, null);
		AssertJUnit.assertEquals("SKU Creation status is not 200", 200, req.response.getStatus());
		ErrorEntryType[] errorEntry = new ErrorEntryType[] {ErrorEntryType.MISSING_HSN_IN_REGISTRY};
		assertSyncSKUCreationErrorEntries(req.returnresponseasstring(), errorEntry, new String[]{"11111111"});
	}
	
	@Test(groups = { "e2eCMSRegression" }, priority = 15, description = "Different hsn per groupid")
	public void verifyErrMsgForDiffHSNInGroup() throws Exception {
		RequestGenerator req = e2eRegressionCmsHelper.getRequestObjForCMSForPayloadAndQueryParam(APINAME.VB_SKUCREATION, new String[]{"0","0","Tshirts","Tshirts","123456","testvan","testvan","Basics","Basics","200","200","S","M","42031010","42031090","Men","Men","21"}, null);
		AssertJUnit.assertEquals("SKU Creation status is not 200", 200, req.response.getStatus());
		ErrorEntryType[] errorEntry = new ErrorEntryType[] {ErrorEntryType.PROD_LEVEL_VALIDATIONS};
		assertSyncSKUCreationErrorEntries(req.returnresponseasstring(), errorEntry, new String[]{"vendor HSN", "Tshirts", "0"});
	}
	
	@Test(groups = { "e2eCMSRegression" }, priority = 16, description = "Duplicate gtin error")
	public void verifyErrMsgForDuplicateGTIN() throws Exception {
		String duplicate_gtin = "Duplicate gtin number : ${gtin} found. This GTIN is already mapped with skuCode :";
		RequestGenerator req = e2eRegressionCmsHelper.getRequestObjForCMSForPayloadAndQueryParam(APINAME.VB_SKUCREATION, new String[]{"0","0","Tshirts","Tshirts","123456","testvan","testvan","Basics","Basics","200","200","S","M","42031010","42031010","Men","Men","21"}, null);
		AssertJUnit.assertEquals("SKU Creation status is not 200", 200, req.response.getStatus());
		String gtin_error = JsonPath.read(req.returnresponseasstring(), "$.data.errorEntries[0].errorMessage").toString();
		AssertJUnit.assertTrue("Duplicate gtin error message not present", gtin_error.contains(duplicate_gtin.replace("${gtin}", "123456")));
	}
	
	@Test(groups = { "e2eCMSRegression" }, priority = 17, description = "Empty sku code error")
	public void verifyErrMsgForEmptySkuCodeForNonAlpha() throws Exception {
		RequestGenerator req = e2eRegressionCmsHelper.getRequestObjForCMSForPayloadAndQueryParam(APINAME.VB_SKUCREATION, new String[]{"0","0","Tshirts","Tshirts","123456","testvan","testvan","Basics","Basics","200","200","S","M","42031010","42031010","Men","Men","17"}, null);
		AssertJUnit.assertEquals("SKU Creation status is not 200", 200, req.response.getStatus());
		ErrorEntryType[] errorEntry = new ErrorEntryType[] {ErrorEntryType.SKUCREATION_EMPTYSKUCODE};
		assertSyncSKUCreationErrorEntries(req.returnresponseasstring(), errorEntry, null);
	}
	
	@Test(groups = { "e2eCMSRegression" }, priority = 18, description = "Add sku to existing style")
	public void verifySkuAddition() throws Exception {
		RequestGenerator req = e2eRegressionCmsHelper.getRequestObjForCMSForPayloadAndQueryParam(APINAME.VB_SKUADDITION, new String[]{"21", styleId, "8XL", String.valueOf(generateRandomGTIN())}, null);
		AssertJUnit.assertEquals("SKU Addition status is not 200", 200, req.response.getStatus());
		AssertJUnit.assertNotNull("Sku id is null", JsonPath.read(req.returnresponseasstring(),"$.data.successEntries[0].skusInfo[0].skuID").toString());
		String skuId = JsonPath.read(req.returnresponseasstring(),"$.data.successEntries[0].skusInfo[0].skuID").toString();
		Thread.sleep(1000);
		RequestGenerator req2 = e2eRegressionCmsHelper.getRequestObjForCMSForQueryParam(ServiceType.CMS_CATALOG, APINAME.FINDBYID, new String[]{styleId});
		AssertJUnit.assertTrue("SkuId is not added", req2.returnresponseasstring().contains("\"skuId\" : "+skuId));
	}
	
	@Test(groups = { "e2eCMSRegression" }, priority = 19, description = "Verify sku addition error message for adding same size")
	public void verifySkuAdditionError_SameSize() throws Exception {
		String sameSizeError = "This productsize (${size}) is already mapped to skuCode";
		RequestGenerator req = e2eRegressionCmsHelper.getRequestObjForCMSForPayloadAndQueryParam(APINAME.VB_SKUADDITION, new String[]{"21", styleId, "S", String.valueOf(generateRandomGTIN())}, null);
		AssertJUnit.assertEquals("SKU Addition status is not 200", 200, req.response.getStatus());
		String sameSize_error = JsonPath.read(req.returnresponseasstring(), "$.data.errorEntries[0].errorMessage").toString();
		AssertJUnit.assertTrue("Sku addition error message not present", sameSize_error.contains(sameSizeError.replace("${size}", "S")));
	}
	
	@Test(groups = { "e2eCMSRegression" }, priority = 20, description = "Verify sku addition error message for adding sku with different seller")
	public void verifySkuAdditionError_ForDifferentSeller() throws Exception {
		RequestGenerator req = e2eRegressionCmsHelper.getRequestObjForCMSForPayloadAndQueryParam(APINAME.VB_SKUADDITION, new String[]{"19", styleId, "6XL", "123456"}, null);
		AssertJUnit.assertEquals("SKU Addition status is not 200", 200, req.response.getStatus());
		ErrorEntryType[] errorEntry = new ErrorEntryType[] {ErrorEntryType.SKUADDITION_DIFFSELLER};
		assertSyncSKUCreationErrorEntries(req.returnresponseasstring(), errorEntry, new String[]{styleId});
	}
	
	public int generateRandomGTIN(){
		Random rand = new Random();
		int gtin = rand.nextInt(900000000) + 100000000;
		return gtin;
	}

	
}
