package com.myntra.apiTests.portalservices.cms;

import com.jayway.jsonpath.JsonPath;
import com.myntra.lordoftherings.Initialize;
import com.myntra.apiTests.common.APINAME;
import com.myntra.apiTests.common.ServiceType;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.test.commons.service.Svc;

import org.apache.log4j.Logger;
import org.testng.AssertJUnit;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

/**
 * @author Vaishali Behere
 * 
 */

public class DIY_EndToEndFlowTest extends SheetHelper {

	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(DIY_EndToEndFlowTest.class);
	// DO NOT Change vendor_id as these tests are run in production and this is
	// the only test_vendor available
	String vendor_id = "1082";
	String studio = "siddharth_pruthi_photography_jaipur";
	String source = "vendor";
	String lotName = "test_vendor_lot";
	String file1 = System.getProperty("user.dir")
			+ "/Data/payloads/EXCELSHEET/cms/DIY_EndToEndFlow.xlsx";
	String file2 = System.getProperty("user.dir")
			+ "/Data/payloads/EXCELSHEET/cms/diy_1AT_1row.xlsx";
	String file3 = System.getProperty("user.dir")
			+ "/Data/payloads/EXCELSHEET/cms/diy_1AT_SameBrandNoBU.xlsx";
	String uploadDIYSheetResponse;
	String diyTaskResponse;
	String diyGetStylesResponse;
	String taskId;
	String brand = "Wrangler";
	String BU = "Men's Casual";
	String productCount = "5";
	String diyStyleDraftStatus = "D";
	String diyStyleRejectStatus = "QCR";
	String diySuccessComment = "Created through DIY Portal";
	String diyDedupComment1 = "Product already exists in the system. Task ID: ";
	String diyImageValidation = "Exception occurred while uploading the images &Couldn't download the image, please check the url or file";
	String measurement = "{\"Chest\":{\"value\":{\"unit\":\"Inches\",\"value\":2}},\"Across Shoulder\":{\"value\":{\"unit\":\"Inches\",\"value\":2}},\"Bust\":{\"value\":{\"unit\":\"Inches\",\"value\":2}}}";
	String transitionSuccessMessage = "Transition Done Successfully for Task ";

	@BeforeMethod(alwaysRun = true)
	public void openHome() {
		log.info("Entry");
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 1, description = "Verify DIY sheet gets uploaded successfully. Verify studio, BU, brand, lotName, source are there in task entries")
	public void verifyDIYUpload() throws Exception {
		Svc service = uploadDIYSheet(file1, studio, source, lotName);
		AssertJUnit.assertEquals(200, service.getResponseStatus());
		uploadDIYSheetResponse = service.getResponseBody();
		AssertJUnit.assertEquals(BU,
				JsonPath.read(uploadDIYSheetResponse, "$.data.businessUnit")
						.toString());
		AssertJUnit.assertEquals(brand,
				JsonPath.read(uploadDIYSheetResponse, "$.data.brandName")
						.toString());
		assertValidateSuccessEntries(uploadDIYSheetResponse);
		AssertJUnit.assertEquals(
				"sheetValidatorErrorEntries not empty",
				"[]",
				JsonPath.read(uploadDIYSheetResponse,
						"$.data.sheetValidatorErrorEntries[*]").toString());
		assertDexterTaskEntries(uploadDIYSheetResponse, studio, BU, brand,
				productCount, source, lotName);
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 2, dependsOnMethods = "verifyDIYUpload", description = "Verify that status changes to manual qc for any task")
	public void verifyTaskStatus() throws Exception {
		String status = "IN_CURATION";
		AssertJUnit.assertTrue("Status didn't get changed to IN_CURATION", verifyDIYTaskStatus(status, uploadDIYSheetResponse));
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 3, dependsOnMethods = "verifyTaskStatus", description = "Verify that View Details page on UI is listing all styles")
	public void verifyViewDetailsPage() throws Exception {
		diyGetStylesResponse = getDIYGetStylesResponseObject(uploadDIYSheetResponse);
		List<String> styleStatus = JsonPath.read(diyGetStylesResponse,
				"$.data[*].styleStatus");
		AssertJUnit.assertEquals("Only " + styleStatus.size()
				+ " styles got listed instead of " + productCount,
				Integer.parseInt(productCount), styleStatus.size());
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 4, dependsOnMethods = "verifyViewDetailsPage", description = "Verify status and comments for diy styles")
	public void verifyViewDetailsPageStatusAndComments() throws Exception {
		for (int i = 0; i < Integer.parseInt(productCount); i++) {
			AssertJUnit.assertEquals(
					"Status is not correct",
					diyStyleDraftStatus,
					JsonPath.read(diyGetStylesResponse, "$.data[" + i
							+ "].styleStatus"));
			AssertJUnit.assertEquals(
					"Comment is not correct",
					diySuccessComment,
					JsonPath.read(diyGetStylesResponse, "$.data[" + i
							+ "].comments"));
		}
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 5, dependsOnMethods = "verifyViewDetailsPage", description = "Verify that all values filled in sheet are reflected in /diy/style screen")
	public void verifyStylesPageHasAllSheetValues() throws Exception {
		String style = JsonPath.read(diyGetStylesResponse, "$.data[0].id")
				.toString();
		RequestGenerator req = e2eRegressionCmsHelper
				.getRequestObjForCMSForQueryParam(ServiceType.CMS_CATALOG, APINAME.DIY_STYLE,
						new String[] { style });
		AssertJUnit.assertEquals("Status is not correct", "<p>test</p>", JsonPath
				.read(req.returnresponseasstring(),
						"$.data[0].materialCareDescription"));
		AssertJUnit.assertEquals("Status is not correct", "Tshirts", JsonPath
				.read(req.returnresponseasstring(),
						"$.data[0].global_attr_article_type_name"));
		AssertJUnit.assertEquals("Status is not correct", measurement, JsonPath
				.read(req.returnresponseasstring(),
						"$.data[0].productOptionEntries[0].measurement"));
		AssertJUnit.assertEquals("Status is not correct", "S", JsonPath.read(
				req.returnresponseasstring(),
				"$.data[0].productOptionEntries[0].allSize"));
		AssertJUnit.assertEquals("Status is not correct", "S", JsonPath.read(
				req.returnresponseasstring(),
				"$.data[0].productOptionEntries[0].myntraSize"));
	}
	
	@Test(groups = { "e2eCMSRegression" }, priority = 6, dependsOnMethods = "verifyViewDetailsPage", description = "Dedup: Upload same sheet again")
	public void dedupUploadSameSheetAgain() throws Exception {
		String status = "IN_CURATION";
		//This taskId is for sheet uploaded in first test. It is being used in rejectTheLot() method written in @AfterClass() also.
		taskId = getDIYTaskIdFromSheetUploadResponse(uploadDIYSheetResponse);
		Svc service = uploadDIYSheet(file1, studio, source, lotName);
		AssertJUnit.assertEquals(200, service.getResponseStatus());
		String diySameSheetUploadResponse = service.getResponseBody();
		if(!verifyDIYTaskStatus(status, diySameSheetUploadResponse))
			AssertJUnit.assertTrue("Status didn't get changed to IN_CURATION", verifyDIYTaskStatus(status, diySameSheetUploadResponse));
		String diySameSheetGetStylesResponse = getDIYGetStylesResponseObject(diySameSheetUploadResponse);
		String taskId2 = getDIYTaskIdFromSheetUploadResponse(diySameSheetUploadResponse);
		rejectLot(taskId2);
		for (int i = 0; i < Integer.parseInt(productCount); i++) {
			AssertJUnit.assertEquals(
					"Status is not correct",
					diyStyleRejectStatus,
					JsonPath.read(diySameSheetGetStylesResponse, "$.data[" + i
							+ "].styleStatus"));
			String comments = JsonPath.read(diySameSheetGetStylesResponse, "$.data[" + i + "].comments").toString();
			boolean flag2 = comments.contains(diyDedupComment1+taskId);
			AssertJUnit.assertTrue("Comments are not updated properly.Expected: "+diyDedupComment1+taskId+" Actual: "+comments, flag2);
		}
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 7, dependsOnMethods = "dedupUploadSameSheetAgain", alwaysRun=true, description = "Reject the lot uploaded in first test")
	public void rejectTheLot() {
		rejectLot(taskId);
	}
	
	@Test(groups = { "e2eCMSRegression" }, priority = 8, dependsOnMethods = "rejectTheLot", description = "Upload same sheet again after lot reject. It should go to D state")
	public void uploadSameSheetAfterLotReject() throws Exception {
		String status = "IN_CURATION";
		Svc service = uploadDIYSheet(file1, studio, source, lotName);
		AssertJUnit.assertEquals(200, service.getResponseStatus());
		String uploadDIYSheetResponse = service.getResponseBody();
		if(!verifyDIYTaskStatus(status, uploadDIYSheetResponse))
			AssertJUnit.assertTrue("Status didn't get changed to IN_CURATION", verifyDIYTaskStatus(status, uploadDIYSheetResponse));
		diyGetStylesResponse = getDIYGetStylesResponseObject(uploadDIYSheetResponse);
		taskId = getDIYTaskIdFromSheetUploadResponse(uploadDIYSheetResponse);
		rejectLot(taskId);
		for (int i = 0; i < Integer.parseInt(productCount); i++) {
			AssertJUnit.assertEquals(
					"Status is not correct",
					diyStyleDraftStatus,
					JsonPath.read(diyGetStylesResponse, "$.data[" + i
							+ "].styleStatus"));
			AssertJUnit.assertEquals(
					"Comment is not correct",
					diySuccessComment,
					JsonPath.read(diyGetStylesResponse, "$.data[" + i
							+ "].comments"));
		}
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 9, description = "Upload sheet with non-downloadable image url")
	public void uploadSheetWithNonDwnldableImage() throws Exception {
		Svc service = uploadDIYSheet(file2, studio, source, lotName);
		AssertJUnit.assertEquals(200, service.getResponseStatus());
		String uploadImageSheet = service.getResponseBody();
		String task = getDIYTaskIdFromSheetUploadResponse(uploadImageSheet);
		boolean flag1 = verifyDIYTaskStatus("IN_CURATION", uploadImageSheet);
		AssertJUnit.assertTrue("Status didn't change properly", flag1);
		String getStylesForImageSheet = getDIYGetStylesResponseObject(uploadImageSheet);
		rejectLot(task);
		AssertJUnit.assertEquals("Status is not correct", diyStyleRejectStatus,
				JsonPath.read(getStylesForImageSheet,
						"$.data[0].styleStatus"));
		String comments = JsonPath.read(getStylesForImageSheet,
				"$.data[0].comments").toString();
		boolean flag2 = comments.contains(diyImageValidation);
		AssertJUnit.assertTrue("Comments are not updated properly.Expected: "
				+ diyImageValidation + " Actual: " + comments, flag2);
	}
	
	@Test(groups = { "e2eCMSRegression" }, priority = 10, description = "Upload diy sheet with empty source")
	public void diyEmptySource() throws Exception {
		String error = "Source cannot be empty";
		Svc service = uploadDIYSheet(file3, studio, "", "");
		AssertJUnit.assertEquals(200, service.getResponseStatus());
		String diyResponse = service.getResponseBody();
		AssertJUnit.assertEquals("Empty source error message didn't appear", error, JsonPath.read(diyResponse,"$.status.statusMessage").toString());
	}
	
	@Test(groups = { "e2eCMSRegression" }, priority = 11, description = "Upload diy sheet with invalid source")
	public void diyInvalidSource() throws Exception {
		String error = "Invalid source type";
		Svc service = uploadDIYSheet(file3, studio, "invalid", "");
		AssertJUnit.assertEquals(200, service.getResponseStatus());
		String diyResponse = service.getResponseBody();
		AssertJUnit.assertEquals("Invalid source error message didn't appear", error, JsonPath.read(diyResponse,"$.status.statusMessage").toString());
	}
	
	@Test(groups = { "e2eCMSRegression" }, priority = 12, description = "Upload diy sheet with no lotName, sheet validation should go through")
	public void diyEmptyLotName() throws Exception {
		String success = "Successfully completed the sheet validation";
		Svc service = uploadDIYSheet(file3, studio, "vendor", "");
		AssertJUnit.assertEquals(200, service.getResponseStatus());
		String diyResponse = service.getResponseBody();
		AssertJUnit.assertEquals("Sheet isn't getting validated", success, JsonPath.read(diyResponse,"$.status.statusMessage").toString());
	}
	
	
	/** Here first argument is response returned from uploadDIYSheet method **/
	public void assertDexterTaskEntries(String responseAsString, String studio, String bu, String brandName, String productsCount, String source, String lotName ){
		String response = getDIYTaskResponseObject(responseAsString);
		AssertJUnit.assertEquals("Studio name incorrect", studio, JsonPath.read(response, "$.data[0].studio").toString());
		AssertJUnit.assertEquals("BU name incorrect", bu, JsonPath.read(response, "$.data[0].businessUnit").toString());
		AssertJUnit.assertEquals("Brand name incorrect", brandName, JsonPath.read(response, "$.data[0].brandName").toString());
		AssertJUnit.assertEquals("productsCount name incorrect", productsCount, JsonPath.read(response, "$.data[0].productsCount").toString());
		AssertJUnit.assertEquals("source name incorrect", source, JsonPath.read(response, "$.data[0].source").toString());
		AssertJUnit.assertEquals("lotName name incorrect", lotName, JsonPath.read(response, "$.data[0].lotName").toString());
	}
	
	public void rejectLot(String task){
		RequestGenerator req = e2eRegressionCmsHelper.getRequestObjForCMSForPayloadAndQueryParam(APINAME.DIY_INCURATIONUPDATEALL, new String[]{"[\"QCR\"]"}, new String[]{task, "CA", "ca"});
		AssertJUnit.assertEquals("Bulk update is not successful", 200,  req.response.getStatus());
		RequestGenerator reqMoveToManualQC = e2eRegressionCmsHelper
				.getRequestObjForCMSForQueryParam(ServiceType.CMS_CATALOG, APINAME.DIY_MOVETOMANUALQC,
						new String[] { task });
		AssertJUnit.assertEquals("Manual qc status is not correct", transitionSuccessMessage+task,
				JsonPath.read(reqMoveToManualQC.returnresponseasstring(), "$.status.statusMessage").toString());
		RequestGenerator reqRejectLot = rejectDIYLot(task);
		AssertJUnit.assertEquals("Reject is not correct", "Transition Done Successfully for Task "+task,
				JsonPath.read(reqRejectLot.returnresponseasstring(), "$.status.statusMessage").toString());
	}
	
}
