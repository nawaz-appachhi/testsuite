package com.myntra.apiTests.portalservices.cms;

import java.util.ArrayList;

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

public class VirtualBundle_BundleCreation extends SheetHelper {

	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(VirtualBundle_BundleCreation.class);
	String env = init.Configurations.GetTestEnvironemnt().toString();
	String vb_payload;
	
	@BeforeMethod(alwaysRun = true)
	public void createVBPayload() {
		log.info("Entry");
		vb_payload = "{\"styleID\": $styleId, \"quantity\": $quantity}";
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 1, description = "Verify bundle creation error message with 0 quantity")
	public void verifyBundleCreationErrWithZeroQuantity1() throws Exception {
		String styleId = "1531";
		String quantity = "0";
		vb_payload = vb_payload.replace("$styleId", styleId).replace("$quantity", quantity);
		log.info("vb_payload string: "+vb_payload);
		RequestGenerator req = e2eRegressionCmsHelper.getRequestObjForCMSForPayloadAndQueryParam(APINAME.VB_BUNDLECREATION, new String[]{vb_payload}, null );
		AssertJUnit.assertEquals("Virtual Bundle Creation status is not success", 200, req.response.getStatus());
		assertVirtualBundleErrorEntries(req.returnresponseasstring(), new VBErrorType[]{VBErrorType.ZERO_QUANTITY_INDIVIDUAL}, new String[]{styleId, quantity});
	}
	
	@Test(groups = { "e2eCMSRegression" }, priority = 2, description = "Verify bundle creation error message with 0 quantity")
	public void verifyBundleCreationErrWithZeroQuantity2() throws Exception {
		String styleId = "1531";
		String quantity = "0";
		vb_payload = vb_payload.replace("$styleId", styleId).replace("$quantity", quantity);
		log.info("vb_payload string: "+vb_payload);
		RequestGenerator req = e2eRegressionCmsHelper.getRequestObjForCMSForPayloadAndQueryParam(APINAME.VB_BUNDLECREATION, new String[]{vb_payload}, null );
		AssertJUnit.assertEquals("Virtual Bundle Creation status is not success", 200, req.response.getStatus());
		assertVirtualBundleErrorEntries(req.returnresponseasstring(), new VBErrorType[]{VBErrorType.ZERO_QUANTITY_TOTAL}, new String[]{quantity});
	}
	
	@Test(groups = { "e2eCMSRegression" }, priority = 3, description = "Verify bundle creation error message with -ve quantity")
	public void verifyBundleCreationErrWithNegQuantity() throws Exception {
		String styleId = "1531";
		String quantity = "-1";
		vb_payload = vb_payload.replace("$styleId", styleId).replace("$quantity", quantity);
		log.info("vb_payload string: "+vb_payload);
		RequestGenerator req = e2eRegressionCmsHelper.getRequestObjForCMSForPayloadAndQueryParam(APINAME.VB_BUNDLECREATION, new String[]{vb_payload}, null );
		AssertJUnit.assertEquals("Virtual Bundle Creation status is not success", 200, req.response.getStatus());
		assertVirtualBundleErrorEntries(req.returnresponseasstring(), new VBErrorType[]{VBErrorType.ZERO_QUANTITY_INDIVIDUAL}, new String[]{styleId, quantity});
	}
	
	@Test(groups = { "e2eCMSRegression" }, priority = 4, description = "Verify bundle creation error message for style that doesn't exist")
	public void verifyBundleCreationErrForNonExistentStyle() throws Exception {
		String styleId = "0";
		String quantity = "3";
		vb_payload = vb_payload.replace("$styleId", styleId).replace("$quantity", quantity);
		log.info("vb_payload string: "+vb_payload);
		RequestGenerator req = e2eRegressionCmsHelper.getRequestObjForCMSForPayloadAndQueryParam(APINAME.VB_BUNDLECREATION, new String[]{vb_payload}, null );
		AssertJUnit.assertEquals("Virtual Bundle Creation status is not success", 200, req.response.getStatus());
		assertVirtualBundleErrorEntries(req.returnresponseasstring(), new VBErrorType[]{VBErrorType.NONEXISTENTSTYLE}, new String[]{styleId});
	}
	
	@Test(groups = { "e2eCMSRegression" }, priority = 5, description = "Verify bundle creation error message for 1 style 1 quantity")
	public void verifyBundleCreationErrForSingleStyleAndQuantity() throws Exception {
		String styleId = "1531";
		String quantity = "1";
		vb_payload = vb_payload.replace("$styleId", styleId).replace("$quantity", quantity);
		log.info("vb_payload string: "+vb_payload);
		RequestGenerator req = e2eRegressionCmsHelper.getRequestObjForCMSForPayloadAndQueryParam(APINAME.VB_BUNDLECREATION, new String[]{vb_payload}, null );
		AssertJUnit.assertEquals("Virtual Bundle Creation status is not success", 200, req.response.getStatus());
		assertVirtualBundleErrorEntries(req.returnresponseasstring(), new VBErrorType[]{VBErrorType.ZERO_QUANTITY_TOTAL}, new String[]{quantity});
	}
	
	@Test(groups = { "e2eCMSRegression" }, priority = 6, description = "Verify bundle creation error message for quantity > limit 4")
	public void verifyBundleCreationErrForLimitExceeded() throws Exception {
		String styleId = "1531";
		String quantity = "5";
		vb_payload = vb_payload.replace("$styleId", styleId).replace("$quantity", quantity);
		log.info("vb_payload string: "+vb_payload);
		RequestGenerator req = e2eRegressionCmsHelper.getRequestObjForCMSForPayloadAndQueryParam(APINAME.VB_BUNDLECREATION, new String[]{vb_payload}, null );
		AssertJUnit.assertEquals("Virtual Bundle Creation status is not success", 200, req.response.getStatus());
		assertVirtualBundleErrorEntries(req.returnresponseasstring(), new VBErrorType[]{VBErrorType.QUANTITY_LIMIT_INDIVIDUAL, VBErrorType.QUANTITY_LIMIT_TOTAL}, new String[]{styleId, quantity});
	}
	
	@Test(groups = { "e2eCMSRegression" }, priority = 7, description = "Verify bundle creation error message by giving same style twice")
	public void verifyBundleCreationErrForSameStyleTwice() throws Exception {
		String styleId = "1531";
		String quantity = "1";
		vb_payload = vb_payload.replace("$styleId", styleId).replace("$quantity", quantity);
		log.info("vb_payload string: "+vb_payload+","+vb_payload);
		RequestGenerator req = e2eRegressionCmsHelper.getRequestObjForCMSForPayloadAndQueryParam(APINAME.VB_BUNDLECREATION, new String[]{vb_payload+","+vb_payload}, null );
		AssertJUnit.assertEquals("Virtual Bundle Creation status is not success", 200, req.response.getStatus());
		assertVirtualBundleErrorEntries(req.returnresponseasstring(), new VBErrorType[]{VBErrorType.DUPLICATE_PRODUCT}, new String[]{styleId});
	}
	
	@Test(groups = { "e2eCMSRegression" }, priority = 8, description = "Bundle creation error for 4 styles with different quantities")
	public void verifyBundleCreationErrForFourStylesExceed() throws Exception {
		String[] styleIds = new String[]{"1531", "1532", "1533", "1534"};
		String quantity = "2";
		String vb_payload_final = "";
		for(int i=0; i< styleIds.length ; i++){
			vb_payload_final = vb_payload_final + vb_payload.replace("$styleId", styleIds[i]).replace("$quantity", quantity);
			if(i != styleIds.length-1)
				vb_payload_final = vb_payload_final+",";
		}
		log.info("vb_payload string: "+vb_payload_final);
		RequestGenerator req = e2eRegressionCmsHelper.getRequestObjForCMSForPayloadAndQueryParam(APINAME.VB_BUNDLECREATION, new String[]{vb_payload_final}, null );
		AssertJUnit.assertEquals("Virtual Bundle Creation status is not success", 200, req.response.getStatus());
		assertVirtualBundleErrorEntries(req.returnresponseasstring(), new VBErrorType[]{VBErrorType.QUANTITY_LIMIT_TOTAL}, null);
	}
	
	@Test(groups = { "e2eCMSRegression" }, priority = 9, description = "Bundle creation error for styles > limit i.e. 4")
	public void verifyBundleCreationErrForFiveStyles() throws Exception {
		String[] styleIds = new String[]{"1531", "1532", "1533", "1534", "1535"};
		String quantity = "1";
		String vb_payload_final = "";
		for(int i=0; i< styleIds.length ; i++){
			vb_payload_final = vb_payload_final + vb_payload.replace("$styleId", styleIds[i]).replace("$quantity", quantity);
			if(i != styleIds.length-1)
				vb_payload_final = vb_payload_final+",";
		}
		log.info("vb_payload string: "+vb_payload_final);
		RequestGenerator req = e2eRegressionCmsHelper.getRequestObjForCMSForPayloadAndQueryParam(APINAME.VB_BUNDLECREATION, new String[]{vb_payload_final}, null );
		AssertJUnit.assertEquals("Virtual Bundle Creation status is not success", 200, req.response.getStatus());
		assertVirtualBundleErrorEntries(req.returnresponseasstring(), new VBErrorType[]{VBErrorType.INPUT_STYLE_ERROR}, null);
	}
	
//	//Needs to be fixed
	@Test(groups = { "e2eCMSRegression" }, priority = 10, description = "Bundle creation error for multiple size charts")
	public void verifyBundleCreationErrForDiffSizeCharts() throws Exception {
		String[] styleIds = new String[]{"1833757", "1833758"};
		String quantity = "1";
		String vb_payload_final = "";
		for(int i=0; i< styleIds.length ; i++){
			vb_payload_final = vb_payload_final + vb_payload.replace("$styleId", styleIds[i]).replace("$quantity", quantity);
			if(i != styleIds.length-1)
				vb_payload_final = vb_payload_final+",";
		}
		log.info("vb_payload string: "+vb_payload_final);
		RequestGenerator req = e2eRegressionCmsHelper.getRequestObjForCMSForPayloadAndQueryParam(APINAME.VB_BUNDLECREATION, new String[]{vb_payload_final}, null);
		AssertJUnit.assertEquals("Virtual Bundle Creation status is not success", 200, req.response.getStatus());
		assertVirtualBundleErrorEntries(req.returnresponseasstring(), new VBErrorType[]{VBErrorType.DIFF_SIZE_CHARTS}, styleIds);
	}
	
	@Test(groups = { "e2eCMSRegression" }, priority = 11, description = "Bundle creation error for different brands")
	public void verifyBundleCreationErrForDiffBrands() throws Exception {
		String[] styleIds = new String[]{"58418", "18486"};
		String quantity = "2";
		String vb_payload_final = "";
		for(int i=0; i< styleIds.length ; i++){
			vb_payload_final = vb_payload_final + vb_payload.replace("$styleId", styleIds[i]).replace("$quantity", quantity);
			if(i != styleIds.length-1)
				vb_payload_final = vb_payload_final+",";
		}
		log.info("vb_payload string: "+vb_payload_final);
		RequestGenerator req = e2eRegressionCmsHelper.getRequestObjForCMSForPayloadAndQueryParam(APINAME.VB_BUNDLECREATION, new String[]{vb_payload_final}, null );
		AssertJUnit.assertEquals("Virtual Bundle Creation status is not success", 200, req.response.getStatus());
		assertVirtualBundleErrorEntries(req.returnresponseasstring(), new VBErrorType[]{VBErrorType.DIFF_BRANDS}, null);
	}
	
	//To fill error details
	@Test(groups = { "e2eCMSRegression" }, priority = 12, description = "Bundle creation error for style status that is not P")
	public void verifyBundleCreationErrForStyleStatus() throws Exception {
		String[] styleIds = new String[]{"58413", "58418"};
		String quantity = "2";
		String vb_payload_final = "";
		for(int i=0; i< styleIds.length ; i++){
			vb_payload_final = vb_payload_final + vb_payload.replace("$styleId", styleIds[i]).replace("$quantity", quantity);
			if(i != styleIds.length-1)
				vb_payload_final = vb_payload_final+",";
		}
		log.info("vb_payload string: "+vb_payload_final);
		RequestGenerator req = e2eRegressionCmsHelper.getRequestObjForCMSForPayloadAndQueryParam(APINAME.VB_BUNDLECREATION, new String[]{vb_payload_final}, null );
		AssertJUnit.assertEquals("Virtual Bundle Creation status is not success", 200, req.response.getStatus());
		assertVirtualBundleErrorEntries(req.returnresponseasstring(), new VBErrorType[]{VBErrorType.STYLE_STATUS}, new String[]{styleIds[0]});
	}
	
	@Test(groups = { "e2eCMSRegression" }, priority = 13, description = "Bundle creation error for different seller")
	public void verifyBundleCreationErrForSeller() throws Exception {
		String[] styleIds = null;
		String quantity = "1";
		String vb_payload_final = "";
		
		if(env.equalsIgnoreCase("M7")){
			styleIds =  new String[]{"1833757", "1833763"};
		}
		else if(env.equalsIgnoreCase("Stage")){
			styleIds =  new String[]{"1787451", "1787456"};   //insert atp entry
		}
		for(int i=0; i< styleIds.length ; i++){
			vb_payload_final = vb_payload_final + vb_payload.replace("$styleId", styleIds[i]).replace("$quantity", quantity);
			if(i != styleIds.length-1)
				vb_payload_final = vb_payload_final+",";
		}
		log.info("vb_payload string: "+vb_payload_final);
		RequestGenerator req = e2eRegressionCmsHelper.getRequestObjForCMSForPayloadAndQueryParam(APINAME.VB_BUNDLECREATION, new String[]{vb_payload_final}, null );
		AssertJUnit.assertEquals("Virtual Bundle Creation status is not success", 200, req.response.getStatus());
		assertVirtualBundleErrorEntries(req.returnresponseasstring(), new VBErrorType[]{VBErrorType.DIFF_SELLER}, null);
	}
	
	@Test(groups = { "e2eCMSRegression" }, priority = 14, description = "Bundle creation with AT=Tshirt, 1 style, 4 quantity")
	public void verifyBundle1ATDifferentQuantity_Tshirt() throws Exception {
		String styleId = null;
		String quantity = "4";
		vb_payload = vb_payload.replace("$styleId", styleId).replace("$quantity", quantity);
		
		if(env.equalsIgnoreCase("M7")){
			styleId =  "1834121";
		}
		else if(env.equalsIgnoreCase("Stage")){
			styleId =  "1787484";   //insert atp entry
		}
		log.info("vb_payload string: "+vb_payload);
		RequestGenerator req = e2eRegressionCmsHelper.getRequestObjForCMSForPayloadAndQueryParam(APINAME.VB_BUNDLECREATION, new String[]{vb_payload}, null );
		AssertJUnit.assertEquals("Virtual Bundle Creation status is not success", 200, req.response.getStatus());
		String bundle = JsonPath.read(req.returnresponseasstring(),"$.data[0].virtualStyle").toString();
		AssertJUnit.assertNotNull("Bundle is null", bundle);
		log.info("multipack virtual bundle created: "+bundle);
		AssertJUnit.assertEquals("Primary style is not set correctly for bundle "+bundle, styleId, JsonPath.read(req.returnresponseasstring(),"$.data[0].primaryStyleID").toString());
		AssertJUnit.assertEquals("errorEntries not empty for bundle "+bundle,"[]", JsonPath.read(req.returnresponseasstring(),"$.data[0].errorEntries[*]").toString());
		RequestGenerator req_bundle = e2eRegressionCmsHelper.getRequestObjForCMSForQueryParam(ServiceType.CMS_CATALOG, APINAME.FINDBYID, new String[] { bundle });
		AssertJUnit.assertEquals("Status code does not match for bundle "+bundle, 200, req_bundle.response.getStatus());
		checkBundleFindByIdResponse(new String[] {styleId}, new String[] {quantity}, bundle, req_bundle);
		checkFlagsInFindByIdResponse(new String[] {styleId}, styleId, bundle, req_bundle);
		checkBundleGlobalAttributes(styleId, bundle, req_bundle);
		checkBundleATSA(styleId, bundle, req_bundle);
//		String bundledSkus = e2eRegressionCmsHelper.getNodeValue(req_bundle, "data[0].bundledSkus", true);
//		AssertJUnit.assertNotNull("bundledSkus is null for bundle:", bundledSkus);
	}
	
	@Test(groups = { "e2eCMSRegression" }, priority = 15, description = "Bundle creation with AT=Tshirt, 2 style, 2 quantities each")
	public void verifyBundle1ATDiffStyle_Tshirt() throws Exception {
		String[] styleIds = null;
		String quantity = "2";
		String vb_payload_final = "";
		
		if(env.equalsIgnoreCase("M7")){
			styleIds =  new String[]{"1833757", "1833759"};
		}
		else if(env.equalsIgnoreCase("Stage")){
			styleIds =  new String[]{"1787486", "1787489"};  //change price of styles to make first one primary , insert atp entry
		}
		for(int i=0; i< styleIds.length ; i++){
			vb_payload_final = vb_payload_final + vb_payload.replace("$styleId", styleIds[i]).replace("$quantity", quantity);
			if(i != styleIds.length-1)
				vb_payload_final = vb_payload_final+",";
		}
		log.info("vb_payload string: "+vb_payload_final);
		RequestGenerator req = e2eRegressionCmsHelper.getRequestObjForCMSForPayloadAndQueryParam(APINAME.VB_BUNDLECREATION, new String[]{vb_payload_final}, null );
		AssertJUnit.assertEquals("Virtual Bundle Creation status is not success", 200, req.response.getStatus());
		String bundle = JsonPath.read(req.returnresponseasstring(),"$.data[0].virtualStyle").toString();
		AssertJUnit.assertNotNull("Bundle is null", bundle);
		log.info("same articletype multiple styles virtual bundle created: "+bundle);
		AssertJUnit.assertEquals("Primary style is not set correctly for bundle "+bundle, styleIds[0], JsonPath.read(req.returnresponseasstring(),"$.data[0].primaryStyleID").toString());
		AssertJUnit.assertEquals("errorEntries not empty for bundle "+bundle,"[]", JsonPath.read(req.returnresponseasstring(),"$.data[0].errorEntries[*]").toString());
		RequestGenerator req_bundle = e2eRegressionCmsHelper.getRequestObjForCMSForQueryParam(ServiceType.CMS_CATALOG, APINAME.FINDBYID, new String[] { bundle });
		AssertJUnit.assertEquals("Status code does not match for bundle "+bundle, 200, req_bundle.response.getStatus());
		checkBundleFindByIdResponse(styleIds, new String[] {quantity, quantity}, bundle, req_bundle);
		checkFlagsInFindByIdResponse(styleIds, styleIds[0], bundle, req_bundle);
		checkBundleGlobalAttributes(styleIds[0], bundle, req_bundle);
		checkBundleATSA(styleIds[0], bundle, req_bundle);
	}
	
	@Test(groups = { "e2eCMSRegression" }, priority = 15, description = "Bundle creation with AT=Tshirt, Toiletry Kit, 2 style, 2 quantities each")
	public void verifyBundle2AT_TshirtToiletryKit() throws Exception {
		String[] styleIds = null;
		String quantity = "2";
		String vb_payload_final = "";
		
		if(env.equalsIgnoreCase("M7")){
			styleIds =  new String[]{"1833757", "1833800"};
		}
		else if(env.equalsIgnoreCase("Stage")){
			styleIds =  new String[]{"1787503", "1787504"};    //change price of styles to make first one primary , insert atp entry
		}
		for(int i=0; i< styleIds.length ; i++){
			vb_payload_final = vb_payload_final + vb_payload.replace("$styleId", styleIds[i]).replace("$quantity", quantity);
			if(i != styleIds.length-1)
				vb_payload_final = vb_payload_final+",";
		}
		log.info("vb_payload string: "+vb_payload_final);
		RequestGenerator req = e2eRegressionCmsHelper.getRequestObjForCMSForPayloadAndQueryParam(APINAME.VB_BUNDLECREATION, new String[]{vb_payload_final}, null );
		AssertJUnit.assertEquals("Virtual Bundle Creation status is not success", 200, req.response.getStatus());
		String bundle = JsonPath.read(req.returnresponseasstring(),"$.data[0].virtualStyle").toString();
		AssertJUnit.assertNotNull("Bundle is null", bundle);
		log.info("Different article type virtual bundle created: "+bundle);
		AssertJUnit.assertEquals("Primary style is not set correctly for bundle "+bundle, styleIds[0], JsonPath.read(req.returnresponseasstring(),"$.data[0].primaryStyleID").toString());
		AssertJUnit.assertEquals("errorEntries not empty for bundle "+bundle,"[]", JsonPath.read(req.returnresponseasstring(),"$.data[0].errorEntries[*]").toString());
		RequestGenerator req_bundle = e2eRegressionCmsHelper.getRequestObjForCMSForQueryParam(ServiceType.CMS_CATALOG, APINAME.FINDBYID, new String[] { bundle });
		AssertJUnit.assertEquals("Status code does not match for bundle "+bundle, 200, req_bundle.response.getStatus());
		checkBundleFindByIdResponse(styleIds, new String[] {quantity, quantity}, bundle, req_bundle);
		checkFlagsInFindByIdResponse(styleIds, styleIds[0], bundle, req_bundle);
		checkBundleGlobalAttributes(styleIds[0], bundle, req_bundle);
		checkBundleATSA(styleIds[0], bundle, req_bundle);
	}
	
	public void checkBundleFindByIdResponse(String[] physicalStyles, String[] quantity, String bundle, RequestGenerator req_bundle){
		AssertJUnit.assertEquals("styleCategory is not correct for bundle "+bundle,"Bundles", JsonPath.read(req_bundle.returnresponseasstring(),"$.data[0].styleCategory").toString());
		AssertJUnit.assertEquals("codEnabled is not Y for bundle "+bundle,"Y", JsonPath.read(req_bundle.returnresponseasstring(),"$.data[0].codEnabled").toString());
		AssertJUnit.assertEquals("hsnCode is not 10000000 for bundle "+bundle,"10000000", JsonPath.read(req_bundle.returnresponseasstring(),"$.data[0].hsnCode").toString());
		AssertJUnit.assertEquals("vendorHSN is not 10000000 for bundle "+bundle,"10000000", JsonPath.read(req_bundle.returnresponseasstring(),"$.data[0].vendorHSN").toString());
		checkBundlePrice(physicalStyles, quantity, bundle, req_bundle);
		checkProductOptions(physicalStyles, quantity, bundle, req_bundle);
	}
	
	public void checkFlagsInFindByIdResponse(String[] physicalStyles, String primaryStyle, String bundle, RequestGenerator req_bundleStyle){
		boolean isJewellery = false;
		boolean isReturnable = true;
		boolean isExchangeable = true;
		boolean isLarge = false;
		boolean isHazmat = false;
		boolean isTryAndBuyEnabled = false;
		boolean pickupEnabled = true;
		boolean fragile = false;
		RequestGenerator req_primaryStyle = e2eRegressionCmsHelper.getRequestObjForCMSForQueryParam(ServiceType.CMS_CATALOG, APINAME.FINDBYID, new String[] { primaryStyle });
		AssertJUnit.assertEquals("Status is not success", 200, req_primaryStyle.response.getStatus());
		RequestGenerator req_physicalStyle;
		for(int i=0 ; i<physicalStyles.length ; i++){
			req_physicalStyle = e2eRegressionCmsHelper.getRequestObjForCMSForQueryParam(ServiceType.CMS_CATALOG, APINAME.FINDBYID, new String[] { physicalStyles[i] });
			AssertJUnit.assertEquals("Status code does not match", 200, req_physicalStyle.response.getStatus());
			isJewellery = isJewellery || Boolean.parseBoolean(JsonPath.read(req_physicalStyle.returnresponseasstring(),"$.data[0].articleType.isJewellery").toString());
			isReturnable = isReturnable && Boolean.parseBoolean(JsonPath.read(req_physicalStyle.returnresponseasstring(),"$.data[0].articleType.isReturnable").toString());
			isExchangeable = isExchangeable && Boolean.parseBoolean(JsonPath.read(req_physicalStyle.returnresponseasstring(),"$.data[0].articleType.isExchangeable").toString());
			isLarge = isLarge || Boolean.parseBoolean(JsonPath.read(req_physicalStyle.returnresponseasstring(),"$.data[0].articleType.isLarge").toString());
			isHazmat = isHazmat || Boolean.parseBoolean(JsonPath.read(req_physicalStyle.returnresponseasstring(),"$.data[0].articleType.isHazmat").toString());
			pickupEnabled = pickupEnabled && Boolean.parseBoolean(JsonPath.read(req_physicalStyle.returnresponseasstring(),"$.data[0].articleType.pickupEnabled").toString());
			fragile = fragile || Boolean.parseBoolean(JsonPath.read(req_physicalStyle.returnresponseasstring(),"$.data[0].articleType.fragile").toString());
		}
		AssertJUnit.assertEquals("isJewellery is not correct for bundle "+bundle,String.valueOf(isJewellery), JsonPath.read(req_bundleStyle.returnresponseasstring(),"$.data[0].globalAttributes.isJewellery.attributeValue").toString());
		AssertJUnit.assertEquals("isReturnable is not correct for bundle "+bundle,String.valueOf(isReturnable), JsonPath.read(req_bundleStyle.returnresponseasstring(),"$.data[0].globalAttributes.isReturnable.attributeValue").toString());
		AssertJUnit.assertEquals("isExchangeable is not correct for bundle "+bundle,String.valueOf(isExchangeable), JsonPath.read(req_bundleStyle.returnresponseasstring(),"$.data[0].globalAttributes.isExchangeable.attributeValue").toString());
		AssertJUnit.assertEquals("isLarge is not correct for bundle "+bundle,String.valueOf(isLarge), JsonPath.read(req_bundleStyle.returnresponseasstring(),"$.data[0].globalAttributes.isLarge.attributeValue").toString());
		AssertJUnit.assertEquals("isHazmat is not correct for bundle "+bundle,String.valueOf(isHazmat), JsonPath.read(req_bundleStyle.returnresponseasstring(),"$.data[0].globalAttributes.isHazmat.attributeValue").toString());
		AssertJUnit.assertEquals("pickupEnabled is not correct for bundle "+bundle,String.valueOf(pickupEnabled), JsonPath.read(req_bundleStyle.returnresponseasstring(),"$.data[0].globalAttributes.isPickupEnabled.attributeValue").toString());
		AssertJUnit.assertEquals("fragile is not correct for bundle "+bundle,String.valueOf(fragile), JsonPath.read(req_bundleStyle.returnresponseasstring(),"$.data[0].globalAttributes.isFragile.attributeValue").toString());
		AssertJUnit.assertEquals("isTryAndBuyEnabled is not correct for bundle "+bundle,String.valueOf(isTryAndBuyEnabled), JsonPath.read(req_bundleStyle.returnresponseasstring(),"$.data[0].globalAttributes.isJewellery.attributeValue").toString());
		AssertJUnit.assertEquals("articleType is not correct for bundle "+bundle,JsonPath.read(req_primaryStyle.returnresponseasstring(),"$.data[0].articleType.typeName").toString(), JsonPath.read(req_bundleStyle.returnresponseasstring(),"$.data[0].articleType.typeName").toString());
	}
	
	public void checkBundleGlobalAttributes(String primaryStyle, String bundle, RequestGenerator req_bundleStyle){
		RequestGenerator req_primaryStyle = e2eRegressionCmsHelper.getRequestObjForCMSForQueryParam(ServiceType.CMS_CATALOG, APINAME.FINDBYID, new String[] { primaryStyle });
		AssertJUnit.assertEquals("Status is not success", 200, req_primaryStyle.response.getStatus());
		AssertJUnit.assertEquals("brandName is not correct for bundle "+bundle,JsonPath.read(req_primaryStyle.returnresponseasstring(),"$.data[0].globalAttributes.brandName.attributeValue").toString(), JsonPath.read(req_bundleStyle.returnresponseasstring(),"$.data[0].globalAttributes.brandName.attributeValue").toString());
		AssertJUnit.assertEquals("colour2 is not correct for bundle "+bundle,JsonPath.read(req_primaryStyle.returnresponseasstring(),"$.data[0].globalAttributes.colour2.attributeValue").toString(), JsonPath.read(req_bundleStyle.returnresponseasstring(),"$.data[0].globalAttributes.colour2.attributeValue").toString());
		AssertJUnit.assertEquals("baseColour is not correct for bundle "+bundle,JsonPath.read(req_primaryStyle.returnresponseasstring(),"$.data[0].globalAttributes.baseColour.attributeValue").toString(), JsonPath.read(req_bundleStyle.returnresponseasstring(),"$.data[0].globalAttributes.baseColour.attributeValue").toString());
		AssertJUnit.assertEquals("colour1 is not correct for bundle "+bundle,JsonPath.read(req_primaryStyle.returnresponseasstring(),"$.data[0].globalAttributes.colour1.attributeValue").toString(), JsonPath.read(req_bundleStyle.returnresponseasstring(),"$.data[0].globalAttributes.colour1.attributeValue").toString());
		AssertJUnit.assertEquals("year is not correct for bundle "+bundle,JsonPath.read(req_primaryStyle.returnresponseasstring(),"$.data[0].globalAttributes.year.attributeValue").toString(), JsonPath.read(req_bundleStyle.returnresponseasstring(),"$.data[0].globalAttributes.year.attributeValue").toString());
		AssertJUnit.assertEquals("ageGroup is not correct for bundle "+bundle,JsonPath.read(req_primaryStyle.returnresponseasstring(),"$.data[0].globalAttributes.ageGroup.attributeValue").toString(), JsonPath.read(req_bundleStyle.returnresponseasstring(),"$.data[0].globalAttributes.ageGroup.attributeValue").toString());
		AssertJUnit.assertEquals("gender is not correct for bundle "+bundle,JsonPath.read(req_primaryStyle.returnresponseasstring(),"$.data[0].globalAttributes.gender.attributeValue").toString(), JsonPath.read(req_bundleStyle.returnresponseasstring(),"$.data[0].globalAttributes.gender.attributeValue").toString());
		AssertJUnit.assertEquals("fashionType is not correct for bundle "+bundle,JsonPath.read(req_primaryStyle.returnresponseasstring(),"$.data[0].globalAttributes.fashionType.attributeValue").toString(), JsonPath.read(req_bundleStyle.returnresponseasstring(),"$.data[0].globalAttributes.fashionType.attributeValue").toString());
		AssertJUnit.assertEquals("season is not correct for bundle "+bundle,JsonPath.read(req_primaryStyle.returnresponseasstring(),"$.data[0].globalAttributes.season.attributeValue").toString(), JsonPath.read(req_bundleStyle.returnresponseasstring(),"$.data[0].globalAttributes.season.attributeValue").toString());
		AssertJUnit.assertEquals("usage is not correct for bundle "+bundle,JsonPath.read(req_primaryStyle.returnresponseasstring(),"$.data[0].globalAttributes.usage.attributeValue").toString(), JsonPath.read(req_bundleStyle.returnresponseasstring(),"$.data[0].globalAttributes.usage.attributeValue").toString());
	}
	
	public void checkBundleATSA(String primaryStyle, String bundle, RequestGenerator req_bundleStyle){
		RequestGenerator req_primaryStyle = e2eRegressionCmsHelper.getRequestObjForCMSForQueryParam(ServiceType.CMS_CATALOG, APINAME.FINDBYID, new String[] { primaryStyle });
		AssertJUnit.assertEquals("Status is not success", 200, req_primaryStyle.response.getStatus());
		ArrayList<String> atsaList_Primary = new ArrayList<String>();
		ArrayList<String> atsaList_Bundle = new ArrayList<String>();
		atsaList_Primary = JsonPath.read(req_primaryStyle.returnresponseasstring(),"$.data[0].productArticleTypeAttributeValues[*].attributeValue");
		atsaList_Bundle = JsonPath.read(req_bundleStyle.returnresponseasstring(),"$.data[0].productArticleTypeAttributeValues[*].attributeValue");
		log.info("primary Style ATSA size "+atsaList_Primary.size());
		log.info("virtual Style ATSA size "+atsaList_Bundle.size());
		AssertJUnit.assertTrue("Not all ATSAs are populated in bundle "+bundle, atsaList_Primary.size() == atsaList_Bundle.size());
	}
	
	public void checkBundlePrice(String[] physicalStyles, String[] quantity, String bundle, RequestGenerator req_bundleStyle){
		RequestGenerator req_physicalStyle;
		float price = 0f;
		for(int i=0 ; i<physicalStyles.length ; i++){
			req_physicalStyle = e2eRegressionCmsHelper.getRequestObjForCMSForQueryParam(ServiceType.CMS_CATALOG, APINAME.FINDBYID, new String[] { physicalStyles[i] });
			AssertJUnit.assertEquals("Status code does not match", 200, req_physicalStyle.response.getStatus());
			price = price + (Float.parseFloat(JsonPath.read(req_physicalStyle.returnresponseasstring(),"$.data[0].price").toString()) * Float.parseFloat(quantity[i]));
		}
		AssertJUnit.assertEquals("price is not correct for bundle "+bundle, String.valueOf(price), JsonPath.read(req_bundleStyle.returnresponseasstring(),"$.data[0].price").toString());
	}
	
	public void checkProductOptions(String[] physicalStyles, String[] quantity, String bundle, RequestGenerator req_bundleStyle){
		ArrayList<String> physicalSizes = new ArrayList<String>();
		ArrayList<String> virtualSizes = new ArrayList<String>();
		int minListSize=0;
		virtualSizes =  JsonPath.read(req_bundleStyle.returnresponseasstring(),"$.data[0].productOptions[*].value");
		for(int i=0 ; i<physicalStyles.length ; i++){
			RequestGenerator req_physicalStyle = e2eRegressionCmsHelper.getRequestObjForCMSForQueryParam(ServiceType.CMS_CATALOG, APINAME.FINDBYID, new String[] { physicalStyles[i] });
			physicalSizes = JsonPath.read(req_physicalStyle.returnresponseasstring(),"$.data[0].productOptions[*].value");
			if(!(physicalSizes.size()==1 && JsonPath.read(req_physicalStyle.returnresponseasstring(),"$.data[0].productOptions[0].unifiedScaleCode").toString().equals("onesize"))){
				if(physicalSizes.size() < minListSize || minListSize==0)
					minListSize = physicalSizes.size();
			}
		}
		log.info("minListSize "+minListSize);
		log.info("virtualSize "+virtualSizes.size());
		AssertJUnit.assertTrue("Difference in sizes. Virtual style: "+bundle, minListSize == virtualSizes.size());
	}
}
	
	
	


	

