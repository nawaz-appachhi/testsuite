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

public class DIYCuration extends SheetHelper {

	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(DIYCuration.class);
	// DO NOT Change vendor_id as these tests are run in production and this is
	// the only test_vendor available
	String vendor_id = "1082";
	String studio = "siddharth_pruthi_photography_jaipur";
	String source = "vendor";
	String lotName = "test_vendor_lot";
	String file1 = System.getProperty("user.dir")
			+ "/Data/payloads/EXCELSHEET/cms/DIYCuration_1.xlsx";
	String file2 = System.getProperty("user.dir")
			+ "/Data/payloads/EXCELSHEET/cms/DIYCuration_2.xlsx";
	String uploadDIYSheetResponse;
	String diyTaskResponse;
	String diyGetStylesResponse;
	String taskId;
	String brand = "Adidas";
	String BU = "Sports";
	String productCount = "15";
	String diyStyleDraftStatus = "D";
	String diyStyleRejectStatus = "QCR";
	String curationApvd = "CA";
	String curationRejected = "CR";
	String curationBulkUpdateErr = "No products updated, check importId and status";
	String curationBulkUpdateSuccess = "Successfully updated";
	String inCurationStatus = "IN_CURATION";
	String manualQC = "MANUAL_QC";
	String manualQCErrorMessage = "Error Occurred while doing transition for Task $id. Exception occurred during transition, Blocked by guard, cannot perform transition.";
	String transitionSuccessMessage = "Transition Done Successfully for Task ";

	@BeforeMethod(alwaysRun = true)
	public void openHome() {
		log.info("Entry");
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 1, description = "Check incuration pending, reject count for sheet with D, QCR state styles")
	public void verifyCurationCountPostUpload() throws Exception {
		Svc service = uploadDIYSheet(file1, studio, source, lotName);
		AssertJUnit.assertEquals(200, service.getResponseStatus());
		uploadDIYSheetResponse = service.getResponseBody();
		diyTaskResponse = getDIYTaskResponseObject(uploadDIYSheetResponse);
		int count = 5;
		while (!verifyDIYTaskStatus(inCurationStatus, uploadDIYSheetResponse)
				&& count != 0) {
			diyTaskResponse = getDIYTaskResponseObject(uploadDIYSheetResponse);
			count--;
		}
		diyTaskResponse = getDIYTaskResponseObject(uploadDIYSheetResponse);
		AssertJUnit.assertEquals(
				"Curation pending count is not correct",
				"9",
				JsonPath.read(diyTaskResponse,
						"$.data[0].curationPendingProductsCount").toString());
		AssertJUnit.assertEquals(
				"Curation reject count is not correct",
				"6",
				JsonPath.read(diyTaskResponse,
						"$.data[0].curationRejectProductsCount").toString());
		AssertJUnit.assertEquals("Studio name incorrect", studio, JsonPath
				.read(diyTaskResponse, "$.data[0].studio").toString());
		AssertJUnit.assertEquals("BU name incorrect", BU,
				JsonPath.read(diyTaskResponse, "$.data[0].businessUnit")
						.toString());
		AssertJUnit.assertEquals("Brand name incorrect", brand,
				JsonPath.read(diyTaskResponse, "$.data[0].brandName")
						.toString());
		AssertJUnit.assertEquals("productsCount name incorrect", productCount,
				JsonPath.read(diyTaskResponse, "$.data[0].productsCount")
						.toString());
		AssertJUnit.assertEquals("source name incorrect", source, JsonPath
				.read(diyTaskResponse, "$.data[0].source").toString());
		AssertJUnit.assertEquals("lotName name incorrect", lotName, JsonPath
				.read(diyTaskResponse, "$.data[0].lotName").toString());
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 2, description = "Update all styles to CA except QCR,D and check error message")
	public void verifyBulkUpdateErrorMessage() throws Exception {
		taskId = getDIYTaskIdFromSheetUploadResponse(uploadDIYSheetResponse);
        log.info("taskId obtained: "+taskId);
		RequestGenerator req = e2eRegressionCmsHelper
				.getRequestObjForCMSForPayloadAndQueryParam(
						APINAME.DIY_INCURATIONUPDATEALL,
						new String[] { "[\"D\", \"QCR\"]" }, new String[] {
								taskId, "CA", "ca" });
		String bulkUpdateResponse = req.returnresponseasstring();
		AssertJUnit.assertEquals("Status Message is not correct",
				curationBulkUpdateErr,
				JsonPath.read(bulkUpdateResponse, "$.status.statusMessage")
						.toString());
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 3, description = "Update all styles to CR except QCR and check curation counts and original/changed styles status")
	public void verifyUpdateAllProductsToCR() throws Exception {
		List<String> styleQCRList = getStylesFromIncurationSearchAPI(taskId,
				"QC%20Reject");
		List<String> styleDraftList = getStylesFromIncurationSearchAPI(taskId,
				"Draft");
		log.info("styleQCRList: "+styleQCRList);
		log.info("styleDraftList: "+styleDraftList);
		RequestGenerator req = e2eRegressionCmsHelper
				.getRequestObjForCMSForPayloadAndQueryParam(
						APINAME.DIY_INCURATIONUPDATEALL,
						new String[] { "[\"QCR\"]" }, new String[] { taskId,
								"CR", "cr" });
		String bulkUpdateResponse = req.returnresponseasstring();
		AssertJUnit.assertEquals("Bulk update is not successful",
				curationBulkUpdateSuccess,
				JsonPath.read(bulkUpdateResponse, "$.status.statusMessage")
						.toString());
		List<String> styleQCRList_AfterUpdate = getStylesFromIncurationSearchAPI(
				taskId, "QC%20Reject");
		List<String> updatedStyleList = getStylesFromIncurationSearchAPI(
				taskId, "Curation%20Rejected");
		log.info("styleQCRList_AfterUpdate: "+styleQCRList_AfterUpdate);
		log.info("updatedStyleList: "+updatedStyleList);
		styleQCRList.removeAll(styleQCRList_AfterUpdate);
		styleDraftList.removeAll(updatedStyleList);
		AssertJUnit.assertTrue("Status of following QCR styles changed: "
				+ styleQCRList, styleQCRList.isEmpty());
		AssertJUnit.assertTrue("Following style status is not CR: "
				+ styleDraftList, styleDraftList.isEmpty());
		for (int i = 0; i < styleDraftList.size(); i++) {
			String style = styleDraftList.get(i);
			log.info("style: "+style);
			RequestGenerator req2 = e2eRegressionCmsHelper
					.getRequestObjForCMSForQueryParam(ServiceType.CMS_CATALOG,
							APINAME.DIY_STYLE, new String[] { style });
			AssertJUnit.assertEquals(
					"Comment for style " + style + " is not updated properly",
					"cr",
					JsonPath.read(req2.returnresponseasstring(),
							"$.data[0].comments").toString());
		}
		diyTaskResponse = getDIYTaskResponseObject(uploadDIYSheetResponse);
		AssertJUnit.assertEquals(
				"Curation pending count is not correct",
				"0",
				JsonPath.read(diyTaskResponse,
						"$.data[0].curationPendingProductsCount").toString());
		AssertJUnit.assertEquals(
				"Curation reject count is not correct",
				"15",
				JsonPath.read(diyTaskResponse,
						"$.data[0].curationRejectProductsCount").toString());
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 4, description = "Update all styles to CA except QCR and check curation counts")
	public void verifyUpdateAllProductsToCA() throws Exception {
		List<String> styleQCRList = getStylesFromIncurationSearchAPI(taskId,
				"QC%20Reject");
		log.info("styleQCRList: "+styleQCRList);
		RequestGenerator req = e2eRegressionCmsHelper
				.getRequestObjForCMSForPayloadAndQueryParam(
						APINAME.DIY_INCURATIONUPDATEALL,
						new String[] { "[\"QCR\"]" }, new String[] { taskId,
								"CA", "ca" });
		String bulkUpdateResponse = req.returnresponseasstring();
		AssertJUnit.assertEquals("Bulk update is not successful",
				curationBulkUpdateSuccess,
				JsonPath.read(bulkUpdateResponse, "$.status.statusMessage")
						.toString());
		List<String> styleQCRList_AfterUpdate = getStylesFromIncurationSearchAPI(
				taskId, "QC%20Reject");
		log.info("styleQCRList_AfterUpdate: "+styleQCRList_AfterUpdate);
		styleQCRList.removeAll(styleQCRList_AfterUpdate);
		AssertJUnit.assertTrue("Error in QCR styles: " + styleQCRList,
				styleQCRList.isEmpty());
		diyTaskResponse = getDIYTaskResponseObject(uploadDIYSheetResponse);
		AssertJUnit.assertEquals(
				"Curation pending count is not correct",
				"0",
				JsonPath.read(diyTaskResponse,
						"$.data[0].curationPendingProductsCount").toString());
		AssertJUnit.assertEquals(
				"Curation reject count is not correct",
				"6",
				JsonPath.read(diyTaskResponse,
						"$.data[0].curationRejectProductsCount").toString());
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 5, description = "Move updated lot to Manual_QC")
	public void moveLotToManualQC() throws Exception {
		RequestGenerator reqManualQC = e2eRegressionCmsHelper
				.getRequestObjForCMSForQueryParam(ServiceType.CMS_CATALOG,
						APINAME.DIY_MOVETOMANUALQC, new String[] { taskId });
		AssertJUnit.assertEquals(
				"Manual qc status is not correct",
				transitionSuccessMessage + taskId,
				JsonPath.read(reqManualQC.returnresponseasstring(),
						"$.status.statusMessage").toString());
		AssertJUnit.assertTrue("Status didn't get changed to MANUAL_QC",
				verifyDIYTaskStatus("MANUAL_QC", uploadDIYSheetResponse));
		diyTaskResponse = getDIYTaskResponseObject(uploadDIYSheetResponse);
		AssertJUnit.assertEquals(
				"Curation pending count is not correct",
				"0",
				JsonPath.read(diyTaskResponse,
						"$.data[0].curationPendingProductsCount").toString());
		AssertJUnit.assertEquals(
				"Curation reject count is not correct",
				"6",
				JsonPath.read(diyTaskResponse,
						"$.data[0].curationRejectProductsCount").toString());
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 6, description = "Reject the manual qc lot and check status and count")
	public void rejectManualQCLot() throws Exception {
		RequestGenerator reqRejectLot = rejectDIYLot(taskId);
		AssertJUnit.assertEquals(
				"Reject is not correct",
				transitionSuccessMessage + taskId,
				JsonPath.read(reqRejectLot.returnresponseasstring(),
						"$.status.statusMessage").toString());
		String styleSearchResponse = getInCurationSearchResponse(taskId,
				"QC%20Reject");
		AssertJUnit.assertEquals("All styles didn't move to QCR", productCount,
				JsonPath.read(styleSearchResponse, "$.status.totalCount")
						.toString());
		diyTaskResponse = getDIYTaskResponseObject(uploadDIYSheetResponse);
		AssertJUnit.assertEquals(
				"Curation pending count is not correct",
				"0",
				JsonPath.read(diyTaskResponse,
						"$.data[0].curationPendingProductsCount").toString());
		AssertJUnit.assertEquals(
				"Curation reject count is not correct",
				"15",
				JsonPath.read(diyTaskResponse,
						"$.data[0].curationRejectProductsCount").toString());
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 7, description = "Check error msg when lot is moved to manual qc with some styles in Draft")
	public void moveToManualQCWithSomeStylesInDraft() throws Exception {
		Svc service = uploadDIYSheet(file2, studio, source, lotName);
		AssertJUnit.assertEquals(200, service.getResponseStatus());
		String uploadDIYSheetResponse2 = service.getResponseBody();
		int count = 5;
		while (!verifyDIYTaskStatus(inCurationStatus, uploadDIYSheetResponse2)
				&& count != 0) {
			diyTaskResponse = getDIYTaskResponseObject(uploadDIYSheetResponse2);
			count--;
		}
		String taskId2 = getDIYTaskIdFromSheetUploadResponse(uploadDIYSheetResponse2);
		log.info("taskId2 id: "+taskId2);
		RequestGenerator req = e2eRegressionCmsHelper
				.getRequestObjForCMSForQueryParam(ServiceType.CMS_CATALOG,
						APINAME.DIY_MOVETOMANUALQC, new String[] { taskId2 });
		AssertJUnit
				.assertEquals(
						"Error message for moving to manual qc with D styles is not correct",
						manualQCErrorMessage.replace("$id", taskId2),
						JsonPath.read(req.returnresponseasstring(),
								"$.status.statusMessage").toString());
		RequestGenerator req2 = e2eRegressionCmsHelper
				.getRequestObjForCMSForPayloadAndQueryParam(
						APINAME.DIY_INCURATIONUPDATEALL,
						new String[] { "[\"QCR\"]" }, new String[] { taskId2,
								"CA", "ca" });
		AssertJUnit.assertEquals(
				"Bulk update is not successful",
				curationBulkUpdateSuccess,
				JsonPath.read(req2.returnresponseasstring(),
						"$.status.statusMessage").toString());
		rejectLot(taskId2);
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 8, description = "Re-upload same lot where previous lots have CA,QCR styles and check for dedup error")
	public void checkDedupErrorOnCAStyles() throws Exception {
		Svc service1 = uploadDIYSheet(file2, studio, source, lotName);
		AssertJUnit.assertEquals(200, service1.getResponseStatus());
		String uploadDIYSheetResponse3 = service1.getResponseBody();
		String taskId3 = getDIYTaskIdFromSheetUploadResponse(uploadDIYSheetResponse3);
		log.info("taskId3 is: "+taskId3);
		int count = 2;
		while (!verifyDIYTaskStatus(inCurationStatus, uploadDIYSheetResponse3)
				&& count != 0) {
			diyTaskResponse = getDIYTaskResponseObject(uploadDIYSheetResponse3);
			count--;
		}
		RequestGenerator req = e2eRegressionCmsHelper
				.getRequestObjForCMSForPayloadAndQueryParam(
						APINAME.DIY_INCURATIONUPDATEALL,
						new String[] { "[\"QCR\"]" }, new String[] { taskId3,
								"CA", "ca" });
		String bulkUpdateResponse = req.returnresponseasstring();
		AssertJUnit.assertEquals("Status Message is not correct",
				curationBulkUpdateSuccess,
				JsonPath.read(bulkUpdateResponse, "$.status.statusMessage")
						.toString());
		Svc service2 = uploadDIYSheet(file2, studio, source, lotName);
		AssertJUnit.assertEquals(200, service2.getResponseStatus());
		String uploadDIYSheetResponse4 = service2.getResponseBody();
		String taskId4 = getDIYTaskIdFromSheetUploadResponse(uploadDIYSheetResponse4);
		log.info("taskId4 is: "+taskId4);
		String taskResponse = null;
		while (!verifyDIYTaskStatus(inCurationStatus, uploadDIYSheetResponse4)
				&& count != 0) {
			taskResponse = getDIYTaskResponseObject(uploadDIYSheetResponse4);
			count--;
		}
		taskResponse = getDIYTaskResponseObject(uploadDIYSheetResponse4);
		AssertJUnit.assertEquals(
				"Curation pending count is not correct",
				"0",
				JsonPath.read(taskResponse,
						"$.data[0].curationPendingProductsCount").toString());
		AssertJUnit.assertEquals(
				"Curation reject count is not correct",
				"3",
				JsonPath.read(taskResponse,
						"$.data[0].curationRejectProductsCount").toString());
		rejectLot(taskId3);
		rejectLot(taskId4);
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 9, dependsOnMethods = "checkDedupErrorOnCAStyles", description = "Reject a lot with CR state styles. CR state should not change")
	public void rejectManualQCLotWithCRState() throws Exception {
		Svc service1 = uploadDIYSheet(file2, studio, source, lotName);
		AssertJUnit.assertEquals(200, service1.getResponseStatus());
		String uploadDIYSheetResponse = service1.getResponseBody();
		String taskId = getDIYTaskIdFromSheetUploadResponse(uploadDIYSheetResponse);
		log.info("taskId for test rejectManualQCLotWithCRState is :" +taskId);
		int count = 2;
		while (!verifyDIYTaskStatus(inCurationStatus, uploadDIYSheetResponse)
				&& count != 0) {
			diyTaskResponse = getDIYTaskResponseObject(uploadDIYSheetResponse);
			count--;
		}
		RequestGenerator req = e2eRegressionCmsHelper
				.getRequestObjForCMSForPayloadAndQueryParam(
						APINAME.DIY_INCURATIONUPDATEALL,
						new String[] { "[\"QCR\"]" }, new String[] { taskId,
								"CR", "cr" });
		String bulkUpdateResponse = req.returnresponseasstring();
		AssertJUnit.assertEquals("Status Message is not correct",
				curationBulkUpdateSuccess,
				JsonPath.read(bulkUpdateResponse, "$.status.statusMessage")
						.toString());
		List<String> styleCRList_BeforeReject = getStylesFromIncurationSearchAPI(
				taskId, "Curation%20Reject");
		log.info("styleCRList_BeforeReject: "+styleCRList_BeforeReject);
		rejectLot(taskId);
		List<String> styleCRList_AfterReject = getStylesFromIncurationSearchAPI(
				taskId, "Curation%20Reject");
		log.info("styleCRList_AfterReject: "+styleCRList_AfterReject);
		styleCRList_BeforeReject.removeAll(styleCRList_AfterReject);
		AssertJUnit.assertTrue("CR styles changed the status to QCR: "
				+ styleCRList_BeforeReject, styleCRList_BeforeReject.isEmpty());
	}

	public void rejectLot(String taskid) {
		RequestGenerator req = e2eRegressionCmsHelper
				.getRequestObjForCMSForQueryParam(ServiceType.CMS_CATALOG,
						APINAME.DIY_MOVETOMANUALQC, new String[] { taskid });
		AssertJUnit.assertEquals(
				"Manual qc status is not correct",
				transitionSuccessMessage + taskid,
				JsonPath.read(req.returnresponseasstring(),
						"$.status.statusMessage").toString());
		RequestGenerator req2 = e2eRegressionCmsHelper
				.getRequestObjForCMSForQueryParam(ServiceType.CMS_CATALOG,
						APINAME.DIY_REJECTLOT, new String[] { taskid });
		AssertJUnit.assertEquals(
				"Reject status is not correct",
				transitionSuccessMessage + taskid,
				JsonPath.read(req2.returnresponseasstring(),
						"$.status.statusMessage").toString());
	}

	/*
	 * TODO: DIY Curation Bulk Update API Automation is pending Testcases
	 * pending : Bulk update >10 styles and check error message, bulk update <10
	 * styles
	 */

}
