/**
 * 
 */
package com.myntra.apiTests.erpservices.wms.Tests;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;

import com.myntra.apiTests.erpservices.oms.TaxationUtil;
import com.myntra.apiTests.erpservices.oms.Test.StockTransferNoteDataProvider;
import com.myntra.apiTests.erpservices.StnTestConstants;
import com.myntra.apiTests.erpservices.oms.StockTransferUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.myntra.client.inventory.enums.Quality;
import com.myntra.client.wms.codes.utils.ItemStatus;
import com.myntra.client.wms.codes.utils.StnCategory;
import com.myntra.client.wms.response.StnResponse;
import com.myntra.client.wms.response.StnSkuResponse;
import com.myntra.test.commons.testbase.BaseTest;

/**
 * The class covers the Negative tests for the STN feature
 * 
 * @author puneet.khanna1@myntra.com
 * @since July 2016
 * 
 */
public class StockTransferNoteNegativeTests extends BaseTest {
	private static Logger log = LoggerFactory
			.getLogger(StockTransferNoteNegativeTests.class);
	static String deprioritiseTest = System.getenv("deprioritiseTest");
//This deprioritiseTest environment variable created for skip the known P3/P4 issues, which is not planned for upcoming fixes. 
//whenever those issues are fixed ,we can enable those tests from jenkins with quick change.
//Only you need to change "deprioritiseTest" value from false to "true" and it will verify all skip tests as well.
	
	/**
	 * The test asserts that the Stn Create fails when source and destination
	 * wareHouse ids are same.
	 * 
	 * @throws UnsupportedEncodingException
	 * @throws JAXBException
	 */

	@Test(groups = { "Regression" }, description = "testCreateStnFailureForSameSourceAndDestinationWareHouseId", dataProvider = "getDataSetStatingDistinctQualityAndItemStatusCombinations", dataProviderClass = StockTransferNoteDataProvider.class)
	public void testCreateStnFailureForSameSourceAndDestinationWareHouseId(
			Quality qualityTypeToTest, ItemStatus itemStatusTotest,
			StnCategory stnCategory) throws UnsupportedEncodingException,
			JAXBException {
		boolean isSuccessExpectedFlag = false;
		StockTransferUtils.createStockTransferNoteAndVerifyExpectedResponse(
						StnTestConstants.DEFAUL_TEST_SOURCE_WAREHOUSEID,
						StnTestConstants.DEFAUL_TEST_SOURCE_WAREHOUSEID,
						itemStatusTotest,
						qualityTypeToTest,
						StnTestConstants.STN_CREATE_REMARK,
						StnTestConstants.STN_DEFAULT_TEST_APPROVER,
						StnTestConstants.STN_Status_Message_SameWHInput,
						StnTestConstants.STN_CREATE_ERROR_CODE_FOR_SAME_SOURCE_AND_DESTINATION_WAREHOUSES,
						isSuccessExpectedFlag,
						StnTestConstants.STATUS_TYPE_ERROR, stnCategory);
	}

	/**
	 * The test asserts that the Stn Create fails when there is an
	 * unacceptable/invalid/negative source wareHouse Id passed
	 * 
	 * @throws UnsupportedEncodingException
	 * @throws JAXBException
	 */
	@Test(groups = { "Regression" }, description = "testCreateStnFailureForInvalidSourceWareHouseId", dataProvider = "getDataSetStatingDistinctQualityAndItemStatusCombinations", dataProviderClass = StockTransferNoteDataProvider.class)
	public void testCreateStnFailureForInvalidSourceWareHouseId(
			Quality qualityTypeToTest, ItemStatus itemStatusTotest,
			StnCategory stnCategory) throws UnsupportedEncodingException,
			JAXBException {
		boolean isSuccessExpectedFlag = false;

		StockTransferUtils
				.createStockTransferNoteAndVerifyExpectedResponse(
						StnTestConstants.TEST_NEGATIVE_AND_INVALID_WAREHOUSE_ID,
						StnTestConstants.DEFAULT_TEST_DESTINATION_WAREHOUSE_ID,
						itemStatusTotest,
						qualityTypeToTest,
						StnTestConstants.STN_CREATE_REMARK,
						StnTestConstants.STN_DEFAULT_TEST_APPROVER,
						StnTestConstants.STN_Status_Message_InvalidWH,
						StnTestConstants.EXPECTED_INVALID_WAREHOUSE_INPUT_FOR_STN_CREATION_STATUS_CODE,
						isSuccessExpectedFlag,
						StnTestConstants.STATUS_TYPE_ERROR, stnCategory);

	}

	/**
	 * The test asserts that the Stn Create fails when there is an invalid
	 * Destination wareHouse Id passed
	 * 
	 * @throws UnsupportedEncodingException
	 * @throws JAXBException
	 */
	@Test(groups = { "Regression" }, description = "testCreateStnFailureForInvalidDestinationWareHouseId", dataProvider = "getDataSetStatingDistinctQualityAndItemStatusCombinations", dataProviderClass = StockTransferNoteDataProvider.class)
	public void testCreateStnFailureForInvalidDestinationWareHouseId(
			Quality qualityTypeToTest, ItemStatus itemStatusTotest,
			StnCategory stnCategory) throws UnsupportedEncodingException,
			JAXBException {

		boolean isSuccessExpectedFlag = false;

		StockTransferUtils
				.createStockTransferNoteAndVerifyExpectedResponse(
						StnTestConstants.DEFAUL_TEST_SOURCE_WAREHOUSEID,
						StnTestConstants.TEST_NEGATIVE_AND_INVALID_WAREHOUSE_ID,
						itemStatusTotest,
						qualityTypeToTest,
						StnTestConstants.STN_CREATE_REMARK,
						StnTestConstants.STN_DEFAULT_TEST_APPROVER,
						StnTestConstants.STN_Status_Message_InvalidWH,
						StnTestConstants.EXPECTED_INVALID_WAREHOUSE_INPUT_FOR_STN_CREATION_STATUS_CODE,
						isSuccessExpectedFlag,
						StnTestConstants.STATUS_TYPE_ERROR, stnCategory);

	}

	/**
	 * The negative test edits all the fields of the STN which has items
	 * dispatched in bulk already
	 * 
	 * @throws JAXBException
	 * @throws InterruptedException 
	 * @throws IOException 
	 */
	@Test(groups = { "Regression" }, description = "SCMOB-2932-STN edition possible in non editable states,The test shows a failure until this defect is fixed.")
	void testEditStnWhenItemsAreAlreadyDispatched()
			throws JAXBException, InterruptedException, IOException {
		int countofItemsIntentedTobeMoved = 2;
		Quality qualityTypeToTest = Quality.Q1;
		ItemStatus itemStatusTotest = ItemStatus.STORED;
		StnCategory stnCategory = StnCategory.NORMAL;
		Long createdStnId = StockTransferUtils
				.createStnAndVerifyCreationToReturnStnId(
						StnTestConstants.DEFAUL_TEST_SOURCE_WAREHOUSEID,
						StnTestConstants.DEFAULT_TEST_DESTINATION_WAREHOUSE_ID,
						itemStatusTotest, qualityTypeToTest,
						StnTestConstants.STN_CREATE_REMARK,
						StnTestConstants.STN_DEFAULT_TEST_APPROVER, stnCategory, StnTestConstants.STN_BUYER_ID);
		List<String> itemIdsToTest = StockTransferUtils.createTestItems(
				qualityTypeToTest.toString(), countofItemsIntentedTobeMoved,
				StnTestConstants.TEST_SKU_ID,
				StnTestConstants.DEFAUL_TEST_SOURCE_WAREHOUSEID,
				itemStatusTotest.toString(), StnTestConstants.STN_BUYER_ID, StnTestConstants.TEST_PO_SKU_ID);
		
		StockTransferUtils.updateInventory(StnTestConstants.TEST_SKU_ID, 
				StnTestConstants.DEFAUL_TEST_SOURCE_WAREHOUSEID, countofItemsIntentedTobeMoved);
		
		String file = StockTransferUtils.createStnItemsCSVFile(itemIdsToTest);
		
		StockTransferUtils.uploadItemsFile(createdStnId, "csv", file.substring(file.lastIndexOf("/")+1), file);
		
		Assert.assertTrue(StockTransferUtils.verifyDownloadDocumentStatus(createdStnId, ""));
		
		Assert.assertTrue(StockTransferUtils.sendStnForApproval(createdStnId));
		
		Assert.assertTrue(StockTransferUtils.validateStnQuantity(createdStnId));
		
		Assert.assertTrue(StockTransferUtils.approveStn(createdStnId, StnTestConstants.DEFAUL_TEST_SOURCE_WAREHOUSEID, 
				StnTestConstants.DEFAULT_TEST_DESTINATION_WAREHOUSE_ID, qualityTypeToTest, itemStatusTotest, 
				stnCategory, StnTestConstants.STN_DEFAULT_TEST_APPROVER, StnTestConstants.STN_CREATE_REMARK));
		
		StockTransferUtils.initiatePickUpForStnAndVerifySuccess(createdStnId);
		StockTransferUtils.dispatchItemsInBulkAndVerifySuccess(itemIdsToTest, createdStnId);
		
		StockTransferUtils.editStnPropertiesAndVerifyExpectedResponse(
				createdStnId,
				StnTestConstants.DEFAULT_TEST_DESTINATION_WAREHOUSE_ID,
				StnTestConstants.DEFAUL_TEST_SOURCE_WAREHOUSEID,
				StnTestConstants.STN_DEFAULT_NON_EXISTING_TEST_APPROVER,
				StnTestConstants.REMARK_EDIT_STN_IN_DISPATCHED_STATE,
				StnTestConstants.STATE_DISPATCHED,
				StnTestConstants.STN_EDIT_ATTEMPT_FAILURE_STATUS_MESSAGE,
				StnTestConstants.STN_EDIT_ATTEMPT_FAILURE_STATUS_CODE);

	}

	/**
	 * The test tries to dispatch a new item from a SKU which not from the
	 * source WareHouse of the STN
	 *
	 * @param qualityTypeToTest
	 * @param itemStatusTotest
	 * @throws JAXBException
	 * @throws InterruptedException
	 * @throws IOException
	 */
	@Test(groups = { "Regression" }, description = "testItemDispatchFailureForUnAssociatedSourceWareHouse")
	void testItemDispatchFailureForUnAssociatedSourceWareHouse()
			throws JAXBException, InterruptedException, IOException {
		int countofItemsIntentedTobeMoved = 2;
		Quality qualityTypeToTest = Quality.Q1;
		ItemStatus itemStatusTotest = ItemStatus.STORED;
		StnCategory stnCategory = StnCategory.NORMAL;
		Long createdStnID = StockTransferUtils
				.createStnAndVerifyCreationToReturnStnId(
						StnTestConstants.DEFAUL_TEST_SOURCE_WAREHOUSEID,
						StnTestConstants.DEFAULT_TEST_DESTINATION_WAREHOUSE_ID,
						itemStatusTotest, qualityTypeToTest,
						StnTestConstants.STN_CREATE_REMARK,
						StnTestConstants.STN_DEFAULT_TEST_APPROVER, stnCategory, StnTestConstants.STN_BUYER_ID);

		List<String> itemIdsToTest = StockTransferUtils.createTestItems(
				qualityTypeToTest.toString(), countofItemsIntentedTobeMoved,
				StnTestConstants.TEST_SKU_ID,
				StnTestConstants.DEFAUL_TEST_SOURCE_WAREHOUSEID,
				itemStatusTotest.toString(), StnTestConstants.STN_BUYER_ID, StnTestConstants.TEST_PO_SKU_ID);

		List<String> itemsFromAnotherSku = StockTransferUtils.createTestItems(
				qualityTypeToTest.toString(), countofItemsIntentedTobeMoved,
				StnTestConstants.TEST_SKU_ID_1,
				StnTestConstants.DEFAULT_TEST_DESTINATION_WAREHOUSE_ID,
				itemStatusTotest.toString(), StnTestConstants.STN_BUYER_ID, StnTestConstants.TEST_PO_SKU_ID);

		StockTransferUtils.updateInventory(StnTestConstants.TEST_SKU_ID,
				StnTestConstants.DEFAUL_TEST_SOURCE_WAREHOUSEID, countofItemsIntentedTobeMoved);

		String file = StockTransferUtils.createStnItemsCSVFile(itemIdsToTest);

		StockTransferUtils.uploadItemsFile(createdStnID, "csv", file.substring(file.lastIndexOf("/")+1), file);

		Assert.assertTrue(StockTransferUtils.verifyDownloadDocumentStatus(createdStnID, ""));

		Assert.assertTrue(StockTransferUtils.sendStnForApproval(createdStnID));

		Assert.assertTrue(StockTransferUtils.validateStnQuantity(createdStnID));

		Assert.assertTrue(StockTransferUtils.approveStn(createdStnID, StnTestConstants.DEFAUL_TEST_SOURCE_WAREHOUSEID,
				StnTestConstants.DEFAULT_TEST_DESTINATION_WAREHOUSE_ID, qualityTypeToTest, itemStatusTotest,
				stnCategory, StnTestConstants.STN_DEFAULT_TEST_APPROVER, StnTestConstants.STN_CREATE_REMARK));

		StockTransferUtils.initiatePickUpForStnAndVerifySuccess(createdStnID);
		StnResponse disptachItemresponse = StockTransferUtils.dispatchItemsInBulk(itemsFromAnotherSku, createdStnID);

		log.info("Printing the disptach item response response below ");
		log.info(disptachItemresponse.toString());
		log.info("verifying that the received statusMessage is "
				+ StnTestConstants.DISPATCH_ITEM_FROM_THE_WAREHOUSE_DIFFERENT_FROM_SOUCE_WAREHOUSE_FAILURE_STATUS_MESSAGE);
		Assert.assertEquals(
				disptachItemresponse.getStatus().getStatusMessage(),
				StnTestConstants.DISPATCH_ITEM_FROM_THE_WAREHOUSE_DIFFERENT_FROM_SOUCE_WAREHOUSE_FAILURE_STATUS_MESSAGE);
		log.info("verifying that the received statusCode is "
				+ StnTestConstants.DISPATCH_ITEM_FROM_THE_WAREHOUSE_DIFFERENT_FROM_SOUCE_WAREHOUSE_FAILURE_STATUS_CODE);
		Assert.assertEquals(
				disptachItemresponse.getStatus().getStatusCode(),
				StnTestConstants.DISPATCH_ITEM_FROM_THE_WAREHOUSE_DIFFERENT_FROM_SOUCE_WAREHOUSE_FAILURE_STATUS_CODE);

	}

	@Test(groups = { "Regression" }, description = "testItemDispatchFailureForAnItemWithDifferentItemStatus")
	void testItemDispatchFailureForAnItemWithDifferentItemStatus()
			throws JAXBException, InterruptedException, IOException {
		int countofItemsIntentedTobeMoved = 2;
		Quality qualityTypeToTest = Quality.Q1;
		ItemStatus itemStatusTotest = ItemStatus.STORED;
		ItemStatus otherItemStatusTotest = ItemStatus.DELETED;
		StnCategory stnCategory = StnCategory.NORMAL;
		Long createdStnId = StockTransferUtils
				.createStnAndVerifyCreationToReturnStnId(
						StnTestConstants.DEFAUL_TEST_SOURCE_WAREHOUSEID,
						StnTestConstants.DEFAULT_TEST_DESTINATION_WAREHOUSE_ID,
						itemStatusTotest, qualityTypeToTest,
						StnTestConstants.STN_CREATE_REMARK,
						StnTestConstants.STN_DEFAULT_TEST_APPROVER, stnCategory, StnTestConstants.STN_BUYER_ID);

		List<String> itemIdsToTest = StockTransferUtils.createTestItems(
				qualityTypeToTest.toString(), countofItemsIntentedTobeMoved,
				StnTestConstants.TEST_SKU_ID,
				StnTestConstants.DEFAUL_TEST_SOURCE_WAREHOUSEID,
				itemStatusTotest.toString(), StnTestConstants.STN_BUYER_ID, StnTestConstants.TEST_PO_SKU_ID);

		List<String> itemsFromOtherItemStatus = StockTransferUtils.createTestItems(qualityTypeToTest.toString(),
						countofItemsIntentedTobeMoved,
						StnTestConstants.TEST_SKU_ID,
						StnTestConstants.DEFAUL_TEST_SOURCE_WAREHOUSEID,
						otherItemStatusTotest.toString(), StnTestConstants.STN_BUYER_ID, StnTestConstants.TEST_PO_SKU_ID);

		StockTransferUtils.updateInventory(StnTestConstants.TEST_SKU_ID,
				StnTestConstants.DEFAUL_TEST_SOURCE_WAREHOUSEID, countofItemsIntentedTobeMoved);

		String file = StockTransferUtils.createStnItemsCSVFile(itemIdsToTest);

		StockTransferUtils.uploadItemsFile(createdStnId, "csv", file.substring(file.lastIndexOf("/")+1), file);

		Assert.assertTrue(StockTransferUtils.verifyDownloadDocumentStatus(createdStnId, ""));

		Assert.assertTrue(StockTransferUtils.sendStnForApproval(createdStnId));

		Assert.assertTrue(StockTransferUtils.validateStnQuantity(createdStnId));

		Assert.assertTrue(StockTransferUtils.approveStn(createdStnId, StnTestConstants.DEFAUL_TEST_SOURCE_WAREHOUSEID,
				StnTestConstants.DEFAULT_TEST_DESTINATION_WAREHOUSE_ID, qualityTypeToTest, itemStatusTotest,
				stnCategory, StnTestConstants.STN_DEFAULT_TEST_APPROVER, StnTestConstants.STN_CREATE_REMARK));

		StockTransferUtils.initiatePickUpForStnAndVerifySuccess(createdStnId);

		StockTransferUtils.dispatchItemsInBulk(itemIdsToTest, createdStnId);
		StnResponse disptachItemresponse = StockTransferUtils.dispatchItemsInBulk(itemsFromOtherItemStatus, createdStnId);

		log.info("Printing the disptach item response response below ");
		log.info(disptachItemresponse.toString());
		log.info("verifying that the received statusMessage is "
				+ StnTestConstants.ITEM_NOT_IN_ISSUED_FOR_OPS_STATUS);
		Assert.assertEquals(
				disptachItemresponse.getStatus().getStatusMessage(),
				StnTestConstants.ITEM_NOT_IN_ISSUED_FOR_OPS_STATUS);
		log.info("verifying that the received statusCode is "
				+ StnTestConstants.ITEM_NOT_IN_ISSUED_FOR_OPS_STATUS_CODE);
		Assert.assertEquals(
				disptachItemresponse.getStatus().getStatusCode(),
				StnTestConstants.ITEM_NOT_IN_ISSUED_FOR_OPS_STATUS_CODE);

	}

	/**
	 * The test tries to dispatch item post stn dispatch success
	 *
	 * @throws JAXBException
	 * @throws InterruptedException
	 * @throws IOException
	 */
	@Test(groups = { "Regression" }, description = "SCMOB-2922:Item dispatch is allowed post STN dispatch is fixed")
	void testItemDispatchFailurePostStnDisptach()
			throws JAXBException, InterruptedException, IOException {
		int countofItemsIntentedTobeMoved = 2;
		Quality quality = Quality.Q1;
		ItemStatus itemStatus = ItemStatus.STORED;
		StnCategory stnCategory = StnCategory.NORMAL;
		Long createdStnID = StockTransferUtils
				.createStnAndVerifyCreationToReturnStnId(
						StnTestConstants.DEFAUL_TEST_SOURCE_WAREHOUSEID,
						StnTestConstants.DEFAULT_TEST_DESTINATION_WAREHOUSE_ID,
						itemStatus, quality,
						StnTestConstants.STN_CREATE_REMARK,
						StnTestConstants.STN_DEFAULT_TEST_APPROVER, stnCategory, StnTestConstants.STN_BUYER_ID);
		List<String> initialItemsToDispatch = StockTransferUtils
				.createTestItems(quality.toString(),
						countofItemsIntentedTobeMoved,
						StnTestConstants.TEST_SKU_ID,
						StnTestConstants.DEFAUL_TEST_SOURCE_WAREHOUSEID,
						itemStatus.toString(), StnTestConstants.STN_BUYER_ID, StnTestConstants.TEST_PO_SKU_ID);

		StockTransferUtils.updateInventory(StnTestConstants.TEST_SKU_ID,
				StnTestConstants.DEFAUL_TEST_SOURCE_WAREHOUSEID, countofItemsIntentedTobeMoved);

		String file = StockTransferUtils.createStnItemsCSVFile(initialItemsToDispatch);

		StockTransferUtils.uploadItemsFile(createdStnID, "csv", file.substring(file.lastIndexOf("/")+1), file);

		Assert.assertTrue(StockTransferUtils.verifyDownloadDocumentStatus(createdStnID, ""));

		Assert.assertTrue(StockTransferUtils.sendStnForApproval(createdStnID));

		Assert.assertTrue(StockTransferUtils.validateStnQuantity(createdStnID));

		Assert.assertTrue(StockTransferUtils.approveStn(createdStnID, StnTestConstants.DEFAUL_TEST_SOURCE_WAREHOUSEID,
				StnTestConstants.DEFAULT_TEST_DESTINATION_WAREHOUSE_ID, quality, itemStatus,
				stnCategory, StnTestConstants.STN_DEFAULT_TEST_APPROVER, StnTestConstants.STN_CREATE_REMARK));

		StockTransferUtils.initiatePickUpForStnAndVerifySuccess(createdStnID);
		StockTransferUtils.dispatchItemsInBulkAndVerifySuccess(initialItemsToDispatch,
				createdStnID);
		StockTransferUtils.disptachStnAndConfirmDisptach(createdStnID);

		List<String> additionalItemsToDispatch = StockTransferUtils
				.createTestItems(quality.toString(),
						countofItemsIntentedTobeMoved,
						StnTestConstants.TEST_SKU_ID,
						StnTestConstants.DEFAUL_TEST_SOURCE_WAREHOUSEID,
						itemStatus.toString(), StnTestConstants.STN_BUYER_ID, StnTestConstants.TEST_PO_SKU_ID);

		StnResponse disptachItemresponse = StockTransferUtils
				.dispatchItemsInBulk(additionalItemsToDispatch, createdStnID);

		log.info("Printing the updated dispatch item response below ");
		log.info(disptachItemresponse.toString());
		log.info("Verifying that the received statusMessage is not "
				+ StnTestConstants.STN_UPDATE_SUCCESS_STATUS_MESSAGE);
		Assert.assertFalse(disptachItemresponse.getStatus().getStatusMessage()
				.equals(StnTestConstants.STN_UPDATE_SUCCESS_STATUS_MESSAGE));
		log.info("Verifying that the received statusMessage is not "
				+ StnTestConstants.DISPATCH_ITEM_ATTEMPT_MORE_THE_STN_INTENDS_TO_MOVE_STATUS_CODE);
		Assert.assertFalse(disptachItemresponse.getStatus().getStatusCode() == StnTestConstants.DISPATCH_ITEM_ATTEMPT_MORE_THE_STN_INTENDS_TO_MOVE_STATUS_CODE);

	}

	/**
	 * The test tries to dispatch the STN when it has no item associated with it
	 *
	 * @throws UnsupportedEncodingException
	 * @throws JAXBException
	 */
	@Test(groups = { "Regression" }, description = "testApprovalFailureForStnWithNoSkuAssociation")
	void testApprovalFailureForStnWithNoSkuAssociation()
			throws UnsupportedEncodingException, JAXBException {
		Quality quality = Quality.Q1;
		ItemStatus itemStatus = ItemStatus.STORED;
		StnCategory stnCategory = StnCategory.NORMAL;
		Long createdStnId = StockTransferUtils.createStnAndVerifyCreationToReturnStnId(
						StnTestConstants.DEFAUL_TEST_SOURCE_WAREHOUSEID,
						StnTestConstants.DEFAULT_TEST_DESTINATION_WAREHOUSE_ID,
						itemStatus, quality,
						StnTestConstants.STN_CREATE_REMARK,
						StnTestConstants.STN_DEFAULT_TEST_APPROVER, stnCategory, StnTestConstants.STN_BUYER_ID);

		StockTransferUtils.moveStockTransferToReadyState(createdStnId);
		StnResponse approveStnReponse = StockTransferUtils.approveStockTransferNote(createdStnId);

		log.info("Printing the updated dispatch item response below ");
		log.info(approveStnReponse.toString());
		log.info("Verifying that the received statusMessage is "
				+ StnTestConstants.STN_APPROVE_ATTEMPT_WITHOUT_ITEM_ASSOCIATION_FAILURE_STATUS_MESSAGE_TEMP);
		Assert.assertEquals(approveStnReponse.getStatus().getStatusMessage(),
				StnTestConstants.STN_APPROVE_ATTEMPT_WITHOUT_ITEM_ASSOCIATION_FAILURE_STATUS_MESSAGE_TEMP);
		log.info("Verifying that the received statusCode is "
				+ StnTestConstants.STN_APPROVE_ATTEMPT_WITHOUT_ITEM_ASSOCIATION_FAILURE_STATUS_CODE_TEMP);
		Assert.assertEquals(approveStnReponse.getStatus().getStatusCode(),
				StnTestConstants.STN_APPROVE_ATTEMPT_WITHOUT_ITEM_ASSOCIATION_FAILURE_STATUS_CODE_TEMP);

	}

	/**
	 * The test tries to dispatch duplicate items in bulk
	 *
	 * @throws JAXBException
	 * @throws InterruptedException
	 * @throws IOException
	 */
	@Test(groups = { "Regression" }, description = "testDuplicateItemAssociationFailure")
	void testDuplicateItemAssociationFailure()
			throws JAXBException, InterruptedException, IOException {
		int countofItemsIntentedTobeMoved = 1;
		Quality quality = Quality.Q1;
		ItemStatus itemStatus = ItemStatus.STORED;
		StnCategory stnCategory = StnCategory.NORMAL;
		Long createdStnId = StockTransferUtils.createStnAndVerifyCreationToReturnStnId(
						StnTestConstants.DEFAUL_TEST_SOURCE_WAREHOUSEID,
						StnTestConstants.DEFAULT_TEST_DESTINATION_WAREHOUSE_ID,
						itemStatus, quality,
						StnTestConstants.STN_CREATE_REMARK,
						StnTestConstants.STN_DEFAULT_TEST_APPROVER, stnCategory, StnTestConstants.STN_BUYER_ID);

		List<String> initialItemsToDispatch = StockTransferUtils
				.createTestItems(quality.toString(),
						countofItemsIntentedTobeMoved,
						StnTestConstants.TEST_SKU_ID,
						StnTestConstants.DEFAUL_TEST_SOURCE_WAREHOUSEID,
						itemStatus.toString(), StnTestConstants.STN_BUYER_ID, StnTestConstants.TEST_PO_SKU_ID);

		StockTransferUtils.updateInventory(StnTestConstants.TEST_SKU_ID,
				StnTestConstants.DEFAUL_TEST_SOURCE_WAREHOUSEID, countofItemsIntentedTobeMoved);

		String file = StockTransferUtils.createStnItemsCSVFile(initialItemsToDispatch);

		StockTransferUtils.uploadItemsFile(createdStnId, "csv", file.substring(file.lastIndexOf("/")+1), file);

		Assert.assertTrue(StockTransferUtils.verifyDownloadDocumentStatus(createdStnId, ""));

		Assert.assertTrue(StockTransferUtils.sendStnForApproval(createdStnId));

		Assert.assertTrue(StockTransferUtils.validateStnQuantity(createdStnId));

		Assert.assertTrue(StockTransferUtils.approveStn(createdStnId, StnTestConstants.DEFAUL_TEST_SOURCE_WAREHOUSEID,
				StnTestConstants.DEFAULT_TEST_DESTINATION_WAREHOUSE_ID, quality, itemStatus,
				stnCategory, StnTestConstants.STN_DEFAULT_TEST_APPROVER, StnTestConstants.STN_CREATE_REMARK));

		StockTransferUtils.initiatePickUpForStnAndVerifySuccess(createdStnId);
		StockTransferUtils.dispatchItemsInBulkAndVerifySuccess(initialItemsToDispatch,
				createdStnId);
		StockTransferUtils.disptachStnAndConfirmDisptach(createdStnId);

		StnResponse reDisptachResponse = StockTransferUtils.dispatchItemsInBulk(initialItemsToDispatch, createdStnId);
		log.info("Printing the  reDisptachResponse item response below ");
		log.info(reDisptachResponse.toString());
		log.info("Verifying that the received statusMessage is "
				+ StnTestConstants.ITEM_NOT_IN_ISSUED_FOR_OPS_STATUS);
		Assert.assertEquals(reDisptachResponse.getStatus().getStatusMessage(),
				StnTestConstants.ITEM_NOT_IN_ISSUED_FOR_OPS_STATUS);
		log.info("Verifying that the received statusCode is "
				+ StnTestConstants.ITEM_NOT_IN_ISSUED_FOR_OPS_STATUS_CODE);
		Assert.assertEquals(reDisptachResponse.getStatus().getStatusCode(),
				StnTestConstants.ITEM_NOT_IN_ISSUED_FOR_OPS_STATUS_CODE);

	}

	/**
	 * The negative test edits all the fields of the STN which is already in
	 * "CREATED" state
	 *
	 * @throws UnsupportedEncodingException
	 * @throws JAXBException
	 */
	/*
	@Test(groups = { "Regression" }, description = "SCMOB-2932-STN edition possible in non editable states,The test shows a failure until this defect is fixed.")
	void testEditStnInCreatedState() throws UnsupportedEncodingException,
			JAXBException {
		if (deprioritiseTest.equals("true")) {
		Quality qualityTypeToTest = Quality.Q1;
		ItemStatus itemStatusTotest = ItemStatus.STORED;
		StnCategory stnCategory = StnCategory.NORMAL;
		Long createdStnId = StockTransferUtils
				.createStnAndVerifyCreationToReturnStnId(
						StnTestConstants.DEFAUL_TEST_SOURCE_WAREHOUSEID,
						StnTestConstants.DEFAULT_TEST_DESTINATION_WAREHOUSE_ID,
						itemStatusTotest, qualityTypeToTest,
						StnTestConstants.STN_CREATE_REMARK,
						StnTestConstants.STN_DEFAULT_TEST_APPROVER, stnCategory, StnTestConstants.STN_BUYER_ID);

		StockTransferUtils.editStnPropertiesAndVerifyExpectedResponse(
				createdStnId,
				StnTestConstants.DEFAULT_TEST_DESTINATION_WAREHOUSE_ID,
				StnTestConstants.DEFAUL_TEST_SOURCE_WAREHOUSEID,
				StnTestConstants.STN_DEFAULT_NON_EXISTING_TEST_APPROVER,
				StnTestConstants.REMARK_EDIT_STNIN_CREATED_STATE,
				StnTestConstants.STATE_CREATED,
				StnTestConstants.STN_EDIT_ATTEMPT_FAILURE_STATUS_MESSAGE,
				StnTestConstants.STN_EDIT_ATTEMPT_FAILURE_STATUS_CODE);
		}
	}	*/

	/**
	 * The negative test edits all the fields of the STN which is already in
	 * "APPROVED" state
	 *
	 * @throws UnsupportedEncodingException
	 * @throws JAXBException
	 */
	/*
	@Test(groups = { "Regression" }, description = "SCMOB-2932-STN edition possible in non editable states,The test shows a failure until this defect is fixed.")
	void testEditStnInApprovedState() throws UnsupportedEncodingException,
			JAXBException {
		if (deprioritiseTest.equals("true")) {
		int countofItemsIntentedTobeMoved = 2;
		Quality qualityTypeToTest = Quality.Q1;
		ItemStatus itemStatusTotest = ItemStatus.STORED;
		StnCategory stnCategory = StnCategory.NORMAL;
		Long createdStnId = StockTransferUtils
				.createStnAndVerifyCreationToReturnStnId(
						StnTestConstants.DEFAUL_TEST_SOURCE_WAREHOUSEID,
						StnTestConstants.DEFAULT_TEST_DESTINATION_WAREHOUSE_ID,
						itemStatusTotest, qualityTypeToTest,
						StnTestConstants.STN_CREATE_REMARK,
						StnTestConstants.STN_DEFAULT_TEST_APPROVER, stnCategory, StnTestConstants.STN_BUYER_ID);
		TaxationUtil.setUpInventoryForTheSkuUnderTest(
				countofItemsIntentedTobeMoved,
				("" + StnTestConstants.DEFAUL_TEST_SOURCE_WAREHOUSEID)
						.split("-"), StnTestConstants.TEST_SKU_ID);

		StockTransferUtils.createTestItems(qualityTypeToTest.toString(),
				countofItemsIntentedTobeMoved, StnTestConstants.TEST_SKU_ID,
				StnTestConstants.DEFAUL_TEST_SOURCE_WAREHOUSEID,
				itemStatusTotest.toString(), StnTestConstants.STN_BUYER_ID, StnTestConstants.TEST_PO_SKU_ID);

		StockTransferUtils.approveStnPostTestSkuAssociationAndVerifySuccess(
				createdStnId, countofItemsIntentedTobeMoved);

		StockTransferUtils.editStnPropertiesAndVerifyExpectedResponse(
				createdStnId,
				StnTestConstants.DEFAULT_TEST_DESTINATION_WAREHOUSE_ID,
				StnTestConstants.DEFAUL_TEST_SOURCE_WAREHOUSEID,
				StnTestConstants.STN_DEFAULT_NON_EXISTING_TEST_APPROVER,
				StnTestConstants.REMARK_EDIT_STNIN_CREATED_STATE,
				StnTestConstants.STATE_APPROVED,
				StnTestConstants.STN_EDIT_ATTEMPT_FAILURE_STATUS_MESSAGE,
				StnTestConstants.STN_EDIT_ATTEMPT_FAILURE_STATUS_CODE);
		}
	}	*/

	/**
	 * The negative test edits all the fields of the STN which is already in
	 * "Pickup Initiated" state
	 *
	 * @throws UnsupportedEncodingException
	 * @throws JAXBException
	 */
	/*
	@Test(groups = { "Regression" }, description = "SCMOB-2932-STN edition possible in non editable states,The test shows a failure until this defect is fixed.")
	void testEditStnInPickUpInitatedState()
			throws UnsupportedEncodingException, JAXBException {
		if (deprioritiseTest.equals("true")) {
		int countofItemsIntentedTobeMoved = 2;
		Quality qualityTypeToTest = Quality.Q1;
		ItemStatus itemStatusTotest = ItemStatus.STORED;
		StnCategory stnCategory = StnCategory.NORMAL;
		Long createdStnId = StockTransferUtils
				.createStnAndVerifyCreationToReturnStnId(
						StnTestConstants.DEFAUL_TEST_SOURCE_WAREHOUSEID,
						StnTestConstants.DEFAULT_TEST_DESTINATION_WAREHOUSE_ID,
						itemStatusTotest, qualityTypeToTest,
						StnTestConstants.STN_CREATE_REMARK,
						StnTestConstants.STN_DEFAULT_TEST_APPROVER, stnCategory, StnTestConstants.STN_BUYER_ID);
		TaxationUtil.setUpInventoryForTheSkuUnderTest(
				countofItemsIntentedTobeMoved,
				("" + StnTestConstants.DEFAUL_TEST_SOURCE_WAREHOUSEID)
						.split("-"), StnTestConstants.TEST_SKU_ID);

		StockTransferUtils.createTestItems(qualityTypeToTest.toString(),
				countofItemsIntentedTobeMoved, StnTestConstants.TEST_SKU_ID,
				StnTestConstants.DEFAUL_TEST_SOURCE_WAREHOUSEID,
				itemStatusTotest.toString(), StnTestConstants.STN_BUYER_ID, StnTestConstants.TEST_PO_SKU_ID);

		StockTransferUtils.approveStnPostTestSkuAssociationAndVerifySuccess(
				createdStnId, countofItemsIntentedTobeMoved);
		StockTransferUtils.initiatePickUpForStockTransferNote(createdStnId);

		StockTransferUtils.editStnPropertiesAndVerifyExpectedResponse(
				createdStnId,
				StnTestConstants.DEFAULT_TEST_DESTINATION_WAREHOUSE_ID,
				StnTestConstants.DEFAUL_TEST_SOURCE_WAREHOUSEID,
				StnTestConstants.STN_DEFAULT_NON_EXISTING_TEST_APPROVER,
				StnTestConstants.REMARK_EDIT_STN_IN_PICKUP_STATE,
				StnTestConstants.STATE_PICKUP_INITIATED,
				StnTestConstants.STN_EDIT_ATTEMPT_FAILURE_STATUS_MESSAGE,
				StnTestConstants.STN_EDIT_ATTEMPT_FAILURE_STATUS_CODE);
		}
	}	*/

	/**
	 * The negative test edits all the fields of the STN which is already in
	 * "CREATED" state
	 * 
	 * @throws JAXBException
	 * @throws InterruptedException 
	 * @throws IOException 
	 */
	/*
	@Test(groups = { "Regression" }, description = "SCMOB-2932-STN edition possible in non editable states,The test shows a failure until this defect is fixed.")
	void testEditDispatchedStn() throws JAXBException, InterruptedException, IOException {
		if (deprioritiseTest.equals("true")) {
		int countofItemsIntentedTobeMoved = 1;
		Quality qualityTypeToTest = Quality.Q1;
		ItemStatus itemStatusTotest = ItemStatus.STORED;
		StnCategory stnCategory = StnCategory.NORMAL;
		Long createdStnId = StockTransferUtils
				.createStnAndVerifyCreationToReturnStnId(
						StnTestConstants.DEFAUL_TEST_SOURCE_WAREHOUSEID,
						StnTestConstants.DEFAULT_TEST_DESTINATION_WAREHOUSE_ID,
						itemStatusTotest, qualityTypeToTest,
						StnTestConstants.STN_CREATE_REMARK,
						StnTestConstants.STN_DEFAULT_TEST_APPROVER, stnCategory, StnTestConstants.STN_BUYER_ID);
		List<String> itemIdsToTest = StockTransferUtils.createTestItems(
				qualityTypeToTest.toString(), countofItemsIntentedTobeMoved,
				StnTestConstants.TEST_SKU_ID,
				StnTestConstants.DEFAUL_TEST_SOURCE_WAREHOUSEID,
				itemStatusTotest.toString(), StnTestConstants.STN_BUYER_ID, StnTestConstants.TEST_PO_SKU_ID);

		//TaxationUtil.setUpInventoryForTheSkuUnderTest(
			//	countofItemsIntentedTobeMoved,
				//("" + StnTestConstants.DEFAUL_TEST_SOURCE_WAREHOUSEID)
					//	.split("-"), StnTestConstants.TEST_SKU_ID);

		//StockTransferUtils.approveStnPostTestSkuAssociationAndVerifySuccess(
			//	createdStnId, countofItemsIntentedTobeMoved);
		//StockTransferUtils.initiatePickUpForStockTransferNote(createdStnId);

		//StockTransferUtils.dispatchItemsInBulk(itemIdsToTest, createdStnId);
		//StockTransferUtils.dispatchStockTransferNote(createdStnId);

		StockTransferUtils.updateInventory(StnTestConstants.TEST_SKU_ID, 
				StnTestConstants.DEFAUL_TEST_SOURCE_WAREHOUSEID, countofItemsIntentedTobeMoved);
		
		String file = StockTransferUtils.createStnItemsCSVFile(itemIdsToTest);
		
		StockTransferUtils.uploadItemsFile(createdStnId, "csv", file.substring(file.lastIndexOf("/")+1), file);
		
		Assert.assertTrue(StockTransferUtils.verifyDownloadDocumentStatus(createdStnId, ""));
		
		Assert.assertTrue(StockTransferUtils.sendStnForApproval(createdStnId));
		
		Assert.assertTrue(StockTransferUtils.validateStnQuantity(createdStnId));
		
		Assert.assertTrue(StockTransferUtils.approveStn(createdStnId, StnTestConstants.DEFAUL_TEST_SOURCE_WAREHOUSEID, 
				StnTestConstants.DEFAULT_TEST_DESTINATION_WAREHOUSE_ID, qualityTypeToTest, itemStatusTotest, 
				stnCategory, StnTestConstants.STN_DEFAULT_TEST_APPROVER, StnTestConstants.STN_CREATE_REMARK));
		
		StockTransferUtils.initiatePickUpForStnAndVerifySuccess(createdStnId);
		StockTransferUtils.dispatchItemsInBulkAndVerifySuccess(itemIdsToTest,
				createdStnId);
		
		StockTransferUtils.disptachStnAndConfirmDisptach(createdStnId);
		
		StockTransferUtils.editStnPropertiesAndVerifyExpectedResponse(
				createdStnId,
				StnTestConstants.DEFAULT_TEST_DESTINATION_WAREHOUSE_ID,
				StnTestConstants.DEFAUL_TEST_SOURCE_WAREHOUSEID,
				StnTestConstants.STN_DEFAULT_NON_EXISTING_TEST_APPROVER,
				StnTestConstants.REMARK_EDIT_STN_IN_DISPATCHED_STATE,
				StnTestConstants.STATE_DISPATCHED,
				StnTestConstants.STN_EDIT_ATTEMPT_FAILURE_STATUS_MESSAGE,
				StnTestConstants.STN_EDIT_ATTEMPT_FAILURE_STATUS_CODE);
		}
	}	*/

	/**
	 * The test tries to dispatch a new item from a SKU which is not yet
	 * associated to STN
	 * 
	 * @param qualityTypeToTest
	 * @param itemStatusTotest
	 * @throws JAXBException
	 * @throws InterruptedException 
	 * @throws IOException 
	 */
	/*
	@Test(groups = { "Regression" }, description = "SCMOB-3884-WMS service is not sending any response if dispatch unassociated SKU in payload.")
	void testItemDispatchFailureForUnAssociatedSku()
			throws JAXBException, InterruptedException, IOException {
		if (deprioritiseTest.equals("true")) {
			int countofItemsIntentedTobeMoved = 2;
			Quality qualityTypeToTest = Quality.Q1;
			ItemStatus itemStatusTotest = ItemStatus.STORED;
			StnCategory stnCategory = StnCategory.NORMAL;
			Long createdStnId = StockTransferUtils
					.createStnAndVerifyCreationToReturnStnId(
							StnTestConstants.DEFAUL_TEST_SOURCE_WAREHOUSEID,
							StnTestConstants.DEFAULT_TEST_DESTINATION_WAREHOUSE_ID,
							itemStatusTotest, qualityTypeToTest,
							StnTestConstants.STN_CREATE_REMARK,
							StnTestConstants.STN_DEFAULT_TEST_APPROVER,
							stnCategory, StnTestConstants.STN_BUYER_ID);

			List<String> itemIdsToTest = StockTransferUtils.createTestItems(
					qualityTypeToTest.toString(),
					countofItemsIntentedTobeMoved,
					StnTestConstants.TEST_SKU_ID,
					StnTestConstants.DEFAUL_TEST_SOURCE_WAREHOUSEID,
					itemStatusTotest.toString(), StnTestConstants.STN_BUYER_ID, StnTestConstants.TEST_PO_SKU_ID);

			//TaxationUtil.setUpInventoryForTheSkuUnderTest(
				//	countofItemsIntentedTobeMoved,
					//("" + StnTestConstants.DEFAUL_TEST_SOURCE_WAREHOUSEID)
						//	.split("-"), StnTestConstants.TEST_SKU_ID);

			//StockTransferUtils
				//	.approveStnPostTestSkuAssociationAndVerifySuccess(
					//		createdStnId, countofItemsIntentedTobeMoved);
			//StockTransferUtils.initiatePickUpForStockTransferNote(createdStnId);

			//StockTransferUtils.dispatchItemsInBulk(itemIdsToTest, createdStnId);
			
			StockTransferUtils.updateInventory(StnTestConstants.TEST_SKU_ID, 
					StnTestConstants.DEFAUL_TEST_SOURCE_WAREHOUSEID, countofItemsIntentedTobeMoved);
			
			String file = StockTransferUtils.createStnItemsCSVFile(itemIdsToTest);
			
			StockTransferUtils.uploadItemsFile(createdStnId, "csv", file.substring(file.lastIndexOf("/")+1), file);
			
			Assert.assertTrue(StockTransferUtils.verifyDownloadDocumentStatus(createdStnId, ""));
			
			Assert.assertTrue(StockTransferUtils.sendStnForApproval(createdStnId));
			
			Assert.assertTrue(StockTransferUtils.validateStnQuantity(createdStnId));
			
			Assert.assertTrue(StockTransferUtils.approveStn(createdStnId, StnTestConstants.DEFAUL_TEST_SOURCE_WAREHOUSEID, 
					StnTestConstants.DEFAULT_TEST_DESTINATION_WAREHOUSE_ID, qualityTypeToTest, itemStatusTotest, 
					stnCategory, StnTestConstants.STN_DEFAULT_TEST_APPROVER, StnTestConstants.STN_CREATE_REMARK));
			
			StockTransferUtils.initiatePickUpForStnAndVerifySuccess(createdStnId);
			StockTransferUtils.dispatchItemsInBulkAndVerifySuccess(itemIdsToTest,
					createdStnId);
			
			StnResponse disptachItemresponse = StockTransferUtils
					.dispatchSingleItem(
							StnTestConstants.ITEM_NOT_ASSOCIATED_WITH_ANY_TEST_SKU,
							createdStnId);
			log.info("printing the updated STN response below ");
			log.info(disptachItemresponse.toString());
			log.info("verifying that the received statusMessage is "
					+ "Item : "
					+ StnTestConstants.ITEM_NOT_ASSOCIATED_WITH_ANY_TEST_SKU
					+ StnTestConstants.DISPATCH_ITEM_UN_RELATED_TO_THE_STN_SKU_ERROR_STATUS_MESSAGE);
			Assert.assertEquals(
					disptachItemresponse.getStatus().getStatusMessage()
							.toString(),
					"Item : "
							+ StnTestConstants.ITEM_NOT_ASSOCIATED_WITH_ANY_TEST_SKU
							+ StnTestConstants.DISPATCH_ITEM_UN_RELATED_TO_THE_STN_SKU_ERROR_STATUS_MESSAGE);
			log.info("verifying that the received statusCode is "
					+ StnTestConstants.DISPATCH_ITEM_UNRELATED_TO_THE_STN_SKU_ERROR_STATUS_CODE);
			Assert.assertEquals(
					disptachItemresponse.getStatus().getStatusCode(),
					StnTestConstants.DISPATCH_ITEM_UNRELATED_TO_THE_STN_SKU_ERROR_STATUS_CODE);
		}
	}	*/


	/**
	 * The test tries to dispatch a new item with quality different than what
	 * STN tries to Move
	 * 
	 * @throws UnsupportedEncodingException
	 * @throws JAXBException
	 *//*
	@Test(groups = { "Regression" }, description = "testItemDispatchFailureForAnItemWithNonAssociatedQuality")
	void testItemDispatchFailureForAnItemWithNonAssociatedQuality()
			throws UnsupportedEncodingException, JAXBException {
		int countofItemsIntentedTobeMoved = 2;
		Quality quality = Quality.Q1;
		Quality otherQuality = Quality.Q2;
		StnCategory stnCategory = StnCategory.NORMAL;
		ItemStatus itemStatus = ItemStatus.STORED;
		Long createdStnId = StockTransferUtils
				.createStnAndVerifyCreationToReturnStnId(
						StnTestConstants.DEFAUL_TEST_SOURCE_WAREHOUSEID,
						StnTestConstants.DEFAULT_TEST_DESTINATION_WAREHOUSE_ID,
						itemStatus, quality,
						StnTestConstants.STN_CREATE_REMARK,
						StnTestConstants.STN_DEFAULT_TEST_APPROVER, stnCategory, StnTestConstants.STN_BUYER_ID);

		//TaxationUtil.setUpInventoryForTheSkuUnderTest(
			//	countofItemsIntentedTobeMoved,
				//("" + StnTestConstants.DEFAUL_TEST_SOURCE_WAREHOUSEID)
					//	.split("-"), StnTestConstants.TEST_SKU_ID);

		StockTransferUtils.createTestItems(quality.toString(),
				countofItemsIntentedTobeMoved, StnTestConstants.TEST_SKU_ID,
				StnTestConstants.DEFAUL_TEST_SOURCE_WAREHOUSEID,
				itemStatus.toString(), StnTestConstants.STN_BUYER_ID, StnTestConstants.TEST_PO_SKU_ID);

		StockTransferUtils.approveStnPostTestSkuAssociationAndVerifySuccess(
				createdStnId, countofItemsIntentedTobeMoved);
		StockTransferUtils.initiatePickUpForStockTransferNote(createdStnId);

		List<String> itemIdsWithDistinctQuality = StockTransferUtils
				.createTestItems(otherQuality.toString(),
						countofItemsIntentedTobeMoved,
						StnTestConstants.TEST_SKU_ID,
						StnTestConstants.DEFAUL_TEST_SOURCE_WAREHOUSEID,
						itemStatus.toString(), StnTestConstants.STN_BUYER_ID, StnTestConstants.TEST_PO_SKU_ID);

		StnResponse disptachItemresponse = StockTransferUtils
				.dispatchItemsInBulk(itemIdsWithDistinctQuality, createdStnId);

		log.info("Printing the updated dispatch item response below ");
		log.info(disptachItemresponse.toString());
		log.info("Verifying that the received statusMessage is "
				+ StnTestConstants.DISPATCH_ITEM_FROM_DIFFERENT_QUALITY_MENTIONED_IN_STN_STATUS_MESSAGE);
		Assert.assertEquals(
				disptachItemresponse.getStatus().getStatusMessage(),
				StnTestConstants.DISPATCH_ITEM_FROM_DIFFERENT_QUALITY_MENTIONED_IN_STN_STATUS_MESSAGE);
		log.info("Verifying that the received statusCode is "
				+ StnTestConstants.DISPATCH_ITEM_FROM_DIFFERENT_QUALITY_MENTIONED_IN_STN_STATUS_CODE);
		Assert.assertEquals(
				disptachItemresponse.getStatus().getStatusCode(),
				StnTestConstants.DISPATCH_ITEM_FROM_DIFFERENT_QUALITY_MENTIONED_IN_STN_STATUS_CODE);
	}	*/

	/**
	 * The test tries to dispatch items per sku which are more than the defined
	 * quanity at the time of SKU association
	 * 
	 * @throws JAXBException
	 * @throws InterruptedException 
	 * @throws IOException 
	 */
	/*
	@Test(groups = { "Regression" }, description = "testItemsDispatchWithItemsPerSkuMoreThanIntendedToTransfer")
	void testItemsDispatchWithItemsPerSkuMoreThanIntendedToTransfer()
			throws JAXBException, InterruptedException, IOException {
		int countofItemsIntentedTobeMoved = 1;
		Quality quality = Quality.Q1;
		ItemStatus itemStatus = ItemStatus.STORED;
		StnCategory stnCategory = StnCategory.NORMAL;
		Long createdStnId = StockTransferUtils
				.createStnAndVerifyCreationToReturnStnId(
						StnTestConstants.DEFAUL_TEST_SOURCE_WAREHOUSEID,
						StnTestConstants.DEFAULT_TEST_DESTINATION_WAREHOUSE_ID,
						itemStatus, quality,
						StnTestConstants.STN_CREATE_REMARK,
						StnTestConstants.STN_DEFAULT_TEST_APPROVER, stnCategory, StnTestConstants.STN_BUYER_ID);
		// prepare greater number of items to associate than STN declared to
		// move for the SKU
		List<String> itemIdsToTest = StockTransferUtils.createTestItems(
				quality.toString(), countofItemsIntentedTobeMoved + 1,
				StnTestConstants.TEST_SKU_ID,
				StnTestConstants.DEFAUL_TEST_SOURCE_WAREHOUSEID,
				itemStatus.toString(), StnTestConstants.STN_BUYER_ID, StnTestConstants.TEST_PO_SKU_ID);

		//TaxationUtil.setUpInventoryForTheSkuUnderTest(
			//	countofItemsIntentedTobeMoved,
				//("" + StnTestConstants.DEFAUL_TEST_SOURCE_WAREHOUSEID)
					//	.split("-"), StnTestConstants.TEST_SKU_ID);

		//StockTransferUtils.approveStnPostTestSkuAssociationAndVerifySuccess(
			//	createdStnId, countofItemsIntentedTobeMoved);
		//StockTransferUtils.initiatePickUpForStockTransferNote(createdStnId);

		StockTransferUtils.updateInventory(StnTestConstants.TEST_SKU_ID, 
				StnTestConstants.DEFAUL_TEST_SOURCE_WAREHOUSEID, countofItemsIntentedTobeMoved + 1);
		
		String file = StockTransferUtils.createStnItemsCSVFile(itemIdsToTest);
		
		StockTransferUtils.uploadItemsFile(createdStnId, "csv", file.substring(file.lastIndexOf("/")+1), file);
		
		Assert.assertTrue(StockTransferUtils.verifyDownloadDocumentStatus(createdStnId, ""));
		
		Assert.assertTrue(StockTransferUtils.sendStnForApproval(createdStnId));
		
		Assert.assertTrue(StockTransferUtils.validateStnQuantity(createdStnId));
		
		Assert.assertTrue(StockTransferUtils.approveStn(createdStnId, StnTestConstants.DEFAUL_TEST_SOURCE_WAREHOUSEID, 
				StnTestConstants.DEFAULT_TEST_DESTINATION_WAREHOUSE_ID, quality, itemStatus, 
				stnCategory, StnTestConstants.STN_DEFAULT_TEST_APPROVER, StnTestConstants.STN_CREATE_REMARK));
		
		StockTransferUtils.initiatePickUpForStnAndVerifySuccess(createdStnId);
		
		StnResponse disptachItemresponse = StockTransferUtils
				.dispatchItemsInBulk(itemIdsToTest, createdStnId);

		log.info("Printing the updated dispatch item response below ");
		log.info(disptachItemresponse.toString());
		log.info("Verifying that the received statusMessage is "
				+ StnTestConstants.DISPATCH_ITEM_ATTEMPT_MORE_THE_STN_INTENDS_TO_MOVE_STATUSMESSAGE);
		Assert.assertEquals(
				disptachItemresponse.getStatus().getStatusMessage(),
				StnTestConstants.DISPATCH_ITEM_ATTEMPT_MORE_THE_STN_INTENDS_TO_MOVE_STATUSMESSAGE);
		log.info("Verifying that the received statusCode is "
				+ StnTestConstants.DISPATCH_ITEM_ATTEMPT_MORE_THAN_STN_INTENDS_TO_STATUSCODE);
		Assert.assertEquals(
				disptachItemresponse.getStatus().getStatusCode(),
				StnTestConstants.DISPATCH_ITEM_ATTEMPT_MORE_THAN_STN_INTENDS_TO_STATUSCODE);

	}	*/


	/**
	 * The test tries to create STN which intends to move items in TRANSIT state
	 * 
	 * @throws UnsupportedEncodingException
	 * @throws JAXBException
	 */
	/*
	@Test(groups = { "Regression" }, description = "SCMOB-2934:WMS service allows to create STN intending to move items in non-eligible state, The test fails until the defect is fixed.")
	void testStnCreateToMoveItemsInTransitState()
			throws UnsupportedEncodingException, JAXBException {
		if (deprioritiseTest.equals("true")) {
		Quality quality = Quality.Q1;
		ItemStatus itemStatus = ItemStatus.TRANSIT;
		StnResponse createStnResponse = StockTransferUtils
				.createStockTransferRequestAndReturnResponse(
						StnTestConstants.DEFAUL_TEST_SOURCE_WAREHOUSEID,
						StnTestConstants.DEFAULT_TEST_DESTINATION_WAREHOUSE_ID,
						itemStatus, quality,
						StnTestConstants.STN_CREATE_REMARK,
						StnTestConstants.STN_DEFAULT_TEST_APPROVER, StnTestConstants.STN_BUYER_ID);
		log.info("Printing the  reDisptachResponse item response below ");
		log.info(createStnResponse.toString());
		log.info("Verifying that the received statusMessage is "
				+ StnTestConstants.STN_CREATE_SUCCESS_STATUS_MESSAGE);
		Assert.assertFalse(createStnResponse.getStatus().getStatusMessage()
				.equals(StnTestConstants.STN_CREATE_SUCCESS_STATUS_MESSAGE),
				"The STN must not have been created,The unexpected status message recieved is "
						+ createStnResponse.getStatus().getStatusMessage());
		log.info("Verifying that the received statusCode is "
				+ StnTestConstants.STN_CREATE_SUCCESS_STATUS_CODE);
		Assert.assertFalse(
				createStnResponse.getStatus().getStatusCode() == StnTestConstants.STN_CREATE_SUCCESS_STATUS_CODE,
				"The unexpected status message recieved is "
						+ createStnResponse.getStatus().getStatusMessage());
		}
	}	*/

	/**
	 * The test tries to create STN which intends to move items in FOUND state
	 * 
	 * @throws UnsupportedEncodingException
	 * @throws JAXBException
	 */
	/*
	@Test(groups = { "Regression" }, description = "SCMOB-2934:WMS service allows to create STN intending to move items in non-eligible state, The test fails until the defect is fixed.")
	void testStnCreateToMoveItemsInFoundState()
			throws UnsupportedEncodingException, JAXBException {
		if (deprioritiseTest.equals("true")) {
		Quality quality = Quality.Q1;
		ItemStatus itemStatus = ItemStatus.FOUND;
		StnResponse createStnResponse = StockTransferUtils
				.createStockTransferRequestAndReturnResponse(
						StnTestConstants.DEFAUL_TEST_SOURCE_WAREHOUSEID,
						StnTestConstants.DEFAULT_TEST_DESTINATION_WAREHOUSE_ID,
						itemStatus, quality,
						StnTestConstants.STN_CREATE_REMARK,
						StnTestConstants.STN_DEFAULT_TEST_APPROVER, StnTestConstants.STN_BUYER_ID);
		log.info("Printing the  reDisptachResponse item response below ");
		log.info(createStnResponse.toString());
		log.info("Verifying that the received statusMessage is "
				+ StnTestConstants.STN_CREATE_SUCCESS_STATUS_MESSAGE);
		Assert.assertFalse(createStnResponse.getStatus().getStatusMessage()
				.equals(StnTestConstants.STN_CREATE_SUCCESS_STATUS_MESSAGE),
				"The STN must not have been created,The unexpected status message recieved is "
						+ createStnResponse.getStatus().getStatusMessage());
		log.info("Verifying that the received statusCode is "
				+ StnTestConstants.STN_CREATE_SUCCESS_STATUS_CODE);
		Assert.assertFalse(
				createStnResponse.getStatus().getStatusCode() == StnTestConstants.STN_CREATE_SUCCESS_STATUS_CODE,
				"The unexpected status message recieved is "
						+ createStnResponse.getStatus().getStatusMessage());
		}
	}	*/

	/**
	 * The test tries to create STN which intends to move items in ISSUED state
	 * 
	 * @throws UnsupportedEncodingException
	 * @throws JAXBException
	 */
	/*
	@Test(groups = { "Regression" }, description = "SCMOB-2934:WMS service allows to create STN intending to move items in non-eligible state, The test fails until the defect is fixed.")
	void testStnCreateToMoveItemsInIssuedState()
			throws UnsupportedEncodingException, JAXBException {
		if (deprioritiseTest.equals("true")) {
		Quality quality = Quality.Q1;
		ItemStatus itemStatus = ItemStatus.ISSUED;
		StnResponse createStnResponse = StockTransferUtils
				.createStockTransferRequestAndReturnResponse(
						StnTestConstants.DEFAUL_TEST_SOURCE_WAREHOUSEID,
						StnTestConstants.DEFAULT_TEST_DESTINATION_WAREHOUSE_ID,
						itemStatus, quality,
						StnTestConstants.STN_CREATE_REMARK,
						StnTestConstants.STN_DEFAULT_TEST_APPROVER, StnTestConstants.STN_BUYER_ID);
		log.info("Printing the  reDisptachResponse item response below ");
		log.info(createStnResponse.toString());
		log.info("Verifying that the received statusMessage is "
				+ StnTestConstants.STN_CREATE_SUCCESS_STATUS_MESSAGE);
		Assert.assertFalse(createStnResponse.getStatus().getStatusMessage()
				.equals(StnTestConstants.STN_CREATE_SUCCESS_STATUS_MESSAGE),
				"The STN must not have been created,The unexpected status message recieved is "
						+ createStnResponse.getStatus().getStatusMessage());
		log.info("Verifying that the received statusCode is "
				+ StnTestConstants.STN_CREATE_SUCCESS_STATUS_CODE);
		Assert.assertFalse(
				createStnResponse.getStatus().getStatusCode() == StnTestConstants.STN_CREATE_SUCCESS_STATUS_CODE,
				"The unexpected status message recieved is "
						+ createStnResponse.getStatus().getStatusMessage());
		}
	}	*/

	/**
	 * The test tries to create STN which intends to move items in
	 * RETURN_FROM_OPS state
	 * 
	 * @throws UnsupportedEncodingException
	 * @throws JAXBException
	 */
	/*
	@Test(groups = { "Regression" }, description = "SCMOB-2934:WMS service allows to create STN intending to move items in non-eligible state, The test fails until the defect is fixed.")
	void testStnCreateToMoveItemsInReturnFromOpsState()
			throws UnsupportedEncodingException, JAXBException {
		if (deprioritiseTest.equals("true")) {
		Quality quality = Quality.Q1;
		ItemStatus itemStatus = ItemStatus.RETURN_FROM_OPS;
		StnResponse createStnResponse = StockTransferUtils
				.createStockTransferRequestAndReturnResponse(
						StnTestConstants.DEFAUL_TEST_SOURCE_WAREHOUSEID,
						StnTestConstants.DEFAULT_TEST_DESTINATION_WAREHOUSE_ID,
						itemStatus, quality,
						StnTestConstants.STN_CREATE_REMARK,
						StnTestConstants.STN_DEFAULT_TEST_APPROVER, StnTestConstants.STN_BUYER_ID);
		log.info("Printing the  reDisptachResponse item response below ");
		log.info(createStnResponse.toString());
		log.info("Verifying that the received statusMessage is "
				+ StnTestConstants.STN_CREATE_SUCCESS_STATUS_MESSAGE);
		Assert.assertFalse(createStnResponse.getStatus().getStatusMessage()
				.equals(StnTestConstants.STN_CREATE_SUCCESS_STATUS_MESSAGE),
				"The STN must not have been created,The unexpected status message recieved is "
						+ createStnResponse.getStatus().getStatusMessage());
		log.info("Verifying that the received statusCode is "
				+ StnTestConstants.STN_CREATE_SUCCESS_STATUS_CODE);
		Assert.assertFalse(
				createStnResponse.getStatus().getStatusCode() == StnTestConstants.STN_CREATE_SUCCESS_STATUS_CODE,
				"The unexpected status message recieved is "
						+ createStnResponse.getStatus().getStatusMessage());
		}
	}	*/

	/**
	 * The test tries to create STN which intends to move items in SHIPPED state
	 * 
	 * @throws UnsupportedEncodingException
	 * @throws JAXBException
	 */
	/*
	@Test(groups = { "Regression" }, description = "SCMOB-2934:WMS service allows to create STN intending to move items in non-eligible state, The test fails until the defect is fixed.")
	void testStnCreateToMoveItemsInShippedState()
			throws UnsupportedEncodingException, JAXBException {
		if (deprioritiseTest.equals("true")) {
		Quality quality = Quality.Q1;
		ItemStatus itemStatus = ItemStatus.SHIPPED;
		StnResponse createStnResponse = StockTransferUtils
				.createStockTransferRequestAndReturnResponse(
						StnTestConstants.DEFAUL_TEST_SOURCE_WAREHOUSEID,
						StnTestConstants.DEFAULT_TEST_DESTINATION_WAREHOUSE_ID,
						itemStatus, quality,
						StnTestConstants.STN_CREATE_REMARK,
						StnTestConstants.STN_DEFAULT_TEST_APPROVER, StnTestConstants.STN_BUYER_ID);
		log.info("Printing the  reDisptachResponse item response below ");
		log.info(createStnResponse.toString());
		log.info("Verifying that the received statusMessage is "
				+ StnTestConstants.STN_CREATE_SUCCESS_STATUS_MESSAGE);
		Assert.assertFalse(createStnResponse.getStatus().getStatusMessage()
				.equals(StnTestConstants.STN_CREATE_SUCCESS_STATUS_MESSAGE),
				"The STN must not have been created,The unexpected status message recieved is "
						+ createStnResponse.getStatus().getStatusMessage());
		log.info("Verifying that the received statusCode is "
				+ StnTestConstants.STN_CREATE_SUCCESS_STATUS_CODE);
		Assert.assertFalse(
				createStnResponse.getStatus().getStatusCode() == StnTestConstants.STN_CREATE_SUCCESS_STATUS_CODE,
				"The unexpected status message recieved is "
						+ createStnResponse.getStatus().getStatusMessage());
		}
	}	*/

	/**
	 * The test tries to create STN which intends to move items in SHRINKAGE
	 * state
	 * 
	 * @throws UnsupportedEncodingException
	 * @throws JAXBException
	 */
	/*
	@Test(description = "SCMOB-2934:WMS service allows to create STN intending to move items in non-eligible state, The test fails until the defect is fixed.")
	void testStnCreateToMoveItemsInShrinkageState()
			throws UnsupportedEncodingException, JAXBException {
		Quality quality = Quality.Q1;
		ItemStatus itemStatus = ItemStatus.SHRINKAGE;
		StnResponse createStnResponse = StockTransferUtils
				.createStockTransferRequestAndReturnResponse(
						StnTestConstants.DEFAUL_TEST_SOURCE_WAREHOUSEID,
						StnTestConstants.DEFAULT_TEST_DESTINATION_WAREHOUSE_ID,
						itemStatus, quality,
						StnTestConstants.STN_CREATE_REMARK,
						StnTestConstants.STN_DEFAULT_TEST_APPROVER, StnTestConstants.STN_BUYER_ID);
		log.info("Printing the  reDisptachResponse item response below ");
		log.info(createStnResponse.toString());
		log.info("Verifying that the received statusMessage is "
				+ StnTestConstants.STN_CREATE_SUCCESS_STATUS_MESSAGE);
		Assert.assertFalse(createStnResponse.getStatus().getStatusMessage()
				.equals(StnTestConstants.STN_CREATE_SUCCESS_STATUS_MESSAGE),
				"The STN must not have been created,The unexpected status message recieved is "
						+ createStnResponse.getStatus().getStatusMessage());
		log.info("Verifying that the received statusCode is "
				+ StnTestConstants.STN_CREATE_SUCCESS_STATUS_CODE);
		Assert.assertFalse(
				createStnResponse.getStatus().getStatusCode() == StnTestConstants.STN_CREATE_SUCCESS_STATUS_CODE,
				"The unexpected status message recieved is "
						+ createStnResponse.getStatus().getStatusMessage());

	}	*/

	/**
	 * The test tries to create STN which intends to move items in NOT_FOUND
	 * state
	 * 
	 * @throws UnsupportedEncodingException
	 * @throws JAXBException
	 */
	/*
	@Test(groups = { "Regression" }, description = "SCMOB-2934:WMS service allows to create STN intending to move items in non-eligible state, The test fails until the defect is fixed.")
	void testStnCreateToMoveItemsInNotFoundState()
			throws UnsupportedEncodingException, JAXBException {
		if (deprioritiseTest.equals("true")) {
		Quality quality = Quality.Q1;
		ItemStatus itemStatus = ItemStatus.NOT_FOUND;
		StnResponse createStnResponse = StockTransferUtils
				.createStockTransferRequestAndReturnResponse(
						StnTestConstants.DEFAUL_TEST_SOURCE_WAREHOUSEID,
						StnTestConstants.DEFAULT_TEST_DESTINATION_WAREHOUSE_ID,
						itemStatus, quality,
						StnTestConstants.STN_CREATE_REMARK,
						StnTestConstants.STN_DEFAULT_TEST_APPROVER, StnTestConstants.STN_BUYER_ID);
		log.info("Printing the  reDisptachResponse item response below ");
		log.info(createStnResponse.toString());
		log.info("Verifying that the received statusMessage is "
				+ StnTestConstants.STN_CREATE_SUCCESS_STATUS_MESSAGE);
		Assert.assertNotEquals(
				createStnResponse.getStatus().getStatusMessage(),
				StnTestConstants.STN_CREATE_SUCCESS_STATUS_MESSAGE);
		log.info("Verifying that the received statusCode is "
				+ StnTestConstants.STN_CREATE_SUCCESS_STATUS_CODE);
		Assert.assertNotEquals(createStnResponse.getStatus().getStatusCode(),
				StnTestConstants.STN_CREATE_SUCCESS_STATUS_CODE);
		}
	}	*/

	/**
	 * The test tries to create STN which intends to move items in PROCESSING
	 * state
	 * 
	 * @throws UnsupportedEncodingException
	 * @throws JAXBException
	 */
	/*
	@Test(groups = { "Regression" }, description = "SCMOB-2934:WMS service allows to create STN intending to move items in non-eligible state, The test fails until the defect is fixed.")
	void testStnCreateToMoveItemsInProcessingState()
			throws UnsupportedEncodingException, JAXBException {
		if (deprioritiseTest.equals("true")) {
		Quality quality = Quality.Q1;
		ItemStatus itemStatus = ItemStatus.PROCESSING;
		StnResponse createStnResponse = StockTransferUtils
				.createStockTransferRequestAndReturnResponse(
						StnTestConstants.DEFAUL_TEST_SOURCE_WAREHOUSEID,
						StnTestConstants.DEFAULT_TEST_DESTINATION_WAREHOUSE_ID,
						itemStatus, quality,
						StnTestConstants.STN_CREATE_REMARK,
						StnTestConstants.STN_DEFAULT_TEST_APPROVER, StnTestConstants.STN_BUYER_ID);
		log.info("Printing the  reDisptachResponse item response below ");
		log.info(createStnResponse.toString());
		log.info("Verifying that the received statusMessage is "
				+ StnTestConstants.STN_CREATE_SUCCESS_STATUS_MESSAGE);
		Assert.assertFalse(createStnResponse.getStatus().getStatusMessage()
				.equals(StnTestConstants.STN_CREATE_SUCCESS_STATUS_MESSAGE),
				"The STN must not have been created,The unexpected status message recieved is "
						+ createStnResponse.getStatus().getStatusMessage());
		log.info("Verifying that the received statusCode is "
				+ StnTestConstants.STN_CREATE_SUCCESS_STATUS_CODE);
		Assert.assertFalse(
				createStnResponse.getStatus().getStatusCode() == StnTestConstants.STN_CREATE_SUCCESS_STATUS_CODE,
				"The unexpected status message recieved is "
						+ createStnResponse.getStatus().getStatusMessage());
		}
	}	*/

	/**
	 * The test tries to create STN which intends to move items in NOT_RECEIVED
	 * state
	 * 
	 * @throws UnsupportedEncodingException
	 * @throws JAXBException
	 */
	/*
	@Test(groups = { "Regression" }, description = "SCMOB-2934:WMS service allows to create STN intending to move items in non-eligible state, The test fails until the defect is fixed.")
	void testStnCreateToMoveItemsInNotRecivedState()
			throws UnsupportedEncodingException, JAXBException {
		if (deprioritiseTest.equals("true")) {
		Quality quality = Quality.Q1;
		ItemStatus itemStatus = ItemStatus.NOT_RECEIVED;
		StnResponse createStnResponse = StockTransferUtils
				.createStockTransferRequestAndReturnResponse(
						StnTestConstants.DEFAUL_TEST_SOURCE_WAREHOUSEID,
						StnTestConstants.DEFAULT_TEST_DESTINATION_WAREHOUSE_ID,
						itemStatus, quality,
						StnTestConstants.STN_CREATE_REMARK,
						StnTestConstants.STN_DEFAULT_TEST_APPROVER, StnTestConstants.STN_BUYER_ID);
		log.info("Printing the  reDisptachResponse item response below ");
		log.info(createStnResponse.toString());
		log.info("Verifying that the received statusMessage is "
				+ StnTestConstants.STN_CREATE_SUCCESS_STATUS_MESSAGE);
		Assert.assertFalse(createStnResponse.getStatus().getStatusMessage()
				.equals(StnTestConstants.STN_CREATE_SUCCESS_STATUS_MESSAGE),
				"The STN must not have been created,The unexpected status message recieved is "
						+ createStnResponse.getStatus().getStatusMessage());
		log.info("Verifying that the received statusCode is "
				+ StnTestConstants.STN_CREATE_SUCCESS_STATUS_CODE);
		Assert.assertFalse(
				createStnResponse.getStatus().getStatusCode() == StnTestConstants.STN_CREATE_SUCCESS_STATUS_CODE,
				"The unexpected status message recieved is "
						+ createStnResponse.getStatus().getStatusMessage());
		}
	}	*/

	/**
	 * The test tries associate same sku multiple times in bulk upload to the
	 * same STN
	 * 
	 * @throws UnsupportedEncodingException
	 * @throws JAXBException
	 */
	/*
	@Test(description = "The test fails until SCMOB-2934 is fixed ")
	void testDuplicatedSkuAssociationToTheStn()
			throws UnsupportedEncodingException, JAXBException {
		int countofItemsIntentedTobeMoved = 1;
		int numOfTimesSkuToBeAssociatedInASingleRequest = 2;
		List<Integer> skuList = new ArrayList<Integer>();
		Quality quality = Quality.Q1;
		StnCategory stnCategory = StnCategory.NORMAL;
		ItemStatus itemStatus = ItemStatus.STORED;
		Long createdStnId = StockTransferUtils
				.createStnAndVerifyCreationToReturnStnId(
						StnTestConstants.DEFAUL_TEST_SOURCE_WAREHOUSEID,
						StnTestConstants.DEFAULT_TEST_DESTINATION_WAREHOUSE_ID,
						itemStatus, quality,
						StnTestConstants.STN_CREATE_REMARK,
						StnTestConstants.STN_DEFAULT_TEST_APPROVER, stnCategory);

		TaxationUtil.setUpInventoryForTheSkuUnderTest(
				countofItemsIntentedTobeMoved,
				("" + StnTestConstants.DEFAUL_TEST_SOURCE_WAREHOUSEID)
						.split("-"), StnTestConstants.TEST_SKU_ID);

		for (int i = 0; i < numOfTimesSkuToBeAssociatedInASingleRequest; i++) {
			skuList.add(StnTestConstants.TEST_SKU_ID);
		}

		StnResponse reDisptachResponse = StockTransferUtils.addSkusInBulkToStn(
				createdStnId, skuList, 1);

		log.info("Printing the  reDisptachResponse item response below ");
		log.info(reDisptachResponse.toString());
		log.info("Verifying that the received statusMessage is "
				+ StnTestConstants.DUPLICATE_ITEM_DISPTACH_FAILURE_STATUS_MESSAGE);
		Assert.assertEquals(reDisptachResponse.getStatus().getStatusMessage(),
				StnTestConstants.DUPLICATE_ITEM_DISPTACH_FAILURE_STATUS_MESSAGE);
		log.info("Verifying that the received statusCode is "
				+ StnTestConstants.DUPLICATE_ITEM_DISPTACH_FAILURE_STATUS_CODE);
		Assert.assertEquals(reDisptachResponse.getStatus().getStatusCode(),
				StnTestConstants.DUPLICATE_ITEM_DISPTACH_FAILURE_STATUS_CODE);

	}	*/

	/**
	 * The test tries to associate SKU to STN while intending to move zero items
	 * 
	 * @throws UnsupportedEncodingException
	 * @throws JAXBException
	 */
	/*
	@Test(groups = { "Regression" }, description = "testSkuAssociationToTheStnWithNoQuantity")
	void testSkuAssociationToTheStnWithNoQuantity()
			throws UnsupportedEncodingException, JAXBException {
		int countofItemsIntentedTobeMoved = 0;
		StnCategory stnCategory = StnCategory.NORMAL;
		Quality quality = Quality.Q1;
		ItemStatus itemStatus = ItemStatus.STORED;
		Long createdStnId = StockTransferUtils
				.createStnAndVerifyCreationToReturnStnId(
						StnTestConstants.DEFAUL_TEST_SOURCE_WAREHOUSEID,
						StnTestConstants.DEFAULT_TEST_DESTINATION_WAREHOUSE_ID,
						itemStatus, quality,
						StnTestConstants.STN_CREATE_REMARK,
						StnTestConstants.STN_DEFAULT_TEST_APPROVER, stnCategory, StnTestConstants.STN_BUYER_ID);

		StnSkuResponse stnSkuResponse = StockTransferUtils
				.associateSkuToStockTransferRequest(createdStnId,
						StnTestConstants.TEST_SKU_ID,
						countofItemsIntentedTobeMoved);

		log.info("Printing the  reDisptachResponse item response below ");
		log.info(stnSkuResponse.toString());
		log.info("Verifying that the received statusMessage is "
				+ StnTestConstants.INVALID_QUANITY_PER_SKU_FAILURE_STATUS_MESSAGE);
		Assert.assertEquals(stnSkuResponse.getStatus().getStatusMessage(),
				StnTestConstants.INVALID_QUANITY_PER_SKU_FAILURE_STATUS_MESSAGE);
		log.info("Verifying that the received statusCode is "
				+ StnTestConstants.INVALID_QUANITY_PER_SKU_FAILURE_STATUS_CODE);
		Assert.assertEquals(stnSkuResponse.getStatus().getStatusCode(),
				StnTestConstants.INVALID_QUANITY_PER_SKU_FAILURE_STATUS_CODE);
	}	*/

}
