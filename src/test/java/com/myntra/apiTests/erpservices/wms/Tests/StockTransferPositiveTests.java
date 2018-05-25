/**
 * 
 */
package com.myntra.apiTests.erpservices.wms.Tests;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBException;

import com.myntra.apiTests.erpservices.StnTestConstants;
import com.myntra.apiTests.erpservices.oms.Test.StockTransferNoteDataProvider;
import com.myntra.apiTests.erpservices.oms.StockTransferUtils;

import org.mortbay.log.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.myntra.client.inventory.enums.Quality;
import com.myntra.client.wms.codes.utils.ItemStatus;
import com.myntra.client.wms.codes.utils.StnCategory;
import com.myntra.test.commons.testbase.BaseTest;

/**
 * The Class covers the Positive tests for STN feature
 * 
 * @author puneet.khanan1@myntra.com
 * @since July 2016
 * 
 */
public class StockTransferPositiveTests extends BaseTest {
	private static Logger log = LoggerFactory
			.getLogger(StockTransferNoteNegativeTests.class);

	/**
	 * The test tries to create STN for the item status
	 * "Stored","ACCEPTED_RETURNS" and for all possible /accepted QUALITY level
	 * 
	 * @param qualityTypeToTest
	 * @param itemStatusTotest
	 * @throws UnsupportedEncodingException
	 * @throws JAXBException
	 */
	@Test(groups = { "Regression" }, description = "verify testStnCreateProcess", dataProvider = "getDataSetStatingDistinctQualityAndItemStatusCombinations", dataProviderClass = StockTransferNoteDataProvider.class)
	void testStnCreateProcess(Quality qualityTypeToTest, ItemStatus itemStatusTotest, StnCategory stnCategory)
			throws UnsupportedEncodingException, JAXBException {
		Long createdStnID = StockTransferUtils.createStnAndVerifyCreationToReturnStnId(
				StnTestConstants.DEFAUL_TEST_SOURCE_WAREHOUSEID,
				StnTestConstants.DEFAULT_TEST_DESTINATION_WAREHOUSE_ID,
				itemStatusTotest, qualityTypeToTest,
				StnTestConstants.STN_CREATE_REMARK,
				StnTestConstants.STN_DEFAULT_TEST_APPROVER, stnCategory, StnTestConstants.STN_BUYER_ID);

		Log.info("The STN id created is :: " + createdStnID.toString());
	}

	/**
	 * The test tries to approve STN for the item status
	 * "Stored","ACCEPTED_RETURNS" and for all possible /accepted QUALITY level
	 * 
	 * @param qualityTypeToTest
	 * @param itemStatusTotest
	 * @throws JAXBException
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	@Test(groups = { "Regression" }, description = "verify testStnApprovalProcess", dataProvider = "getDataSetStatingDistinctQualityAndItemStatusCombinations", dataProviderClass = StockTransferNoteDataProvider.class)
	void testStnApprovalProcess(Quality qualityTypeToTest, ItemStatus itemStatusTotest, StnCategory stnCategory)
			throws JAXBException, IOException, InterruptedException {
		int countofItemsIntentedTobeMoved = 1;
		Long createdStnID = StockTransferUtils
				.createStnAndVerifyCreationToReturnStnId(
						StnTestConstants.DEFAUL_TEST_SOURCE_WAREHOUSEID,
						StnTestConstants.DEFAULT_TEST_DESTINATION_WAREHOUSE_ID,
						itemStatusTotest, qualityTypeToTest,
						StnTestConstants.STN_CREATE_REMARK,
						StnTestConstants.STN_DEFAULT_TEST_APPROVER, stnCategory, StnTestConstants.STN_BUYER_ID);
		Log.info("The stn id created is :: " + createdStnID.toString());

		List<String> items = StockTransferUtils.createTestItems(qualityTypeToTest.toString(),
				countofItemsIntentedTobeMoved, StnTestConstants.TEST_SKU_ID,
				StnTestConstants.DEFAUL_TEST_SOURCE_WAREHOUSEID,
				itemStatusTotest.toString(), StnTestConstants.STN_BUYER_ID, StnTestConstants.TEST_PO_SKU_ID);

		StockTransferUtils.updateInventory(StnTestConstants.TEST_SKU_ID, 
				StnTestConstants.DEFAUL_TEST_SOURCE_WAREHOUSEID, countofItemsIntentedTobeMoved);

		String file = StockTransferUtils.createStnItemsCSVFile(items);
		StockTransferUtils.uploadItemsFile(createdStnID, "csv", file.substring(file.lastIndexOf("/")+1), file);
		Assert.assertTrue(StockTransferUtils.verifyDownloadDocumentStatus(createdStnID, ""));

		Assert.assertTrue(StockTransferUtils.sendStnForApproval(createdStnID));
		Assert.assertTrue(StockTransferUtils.validateStnQuantity(createdStnID));
		Assert.assertTrue(StockTransferUtils.approveStn(createdStnID, StnTestConstants.DEFAUL_TEST_SOURCE_WAREHOUSEID, 
				StnTestConstants.DEFAULT_TEST_DESTINATION_WAREHOUSE_ID, qualityTypeToTest, itemStatusTotest, 
				stnCategory, StnTestConstants.STN_DEFAULT_TEST_APPROVER, StnTestConstants.STN_CREATE_REMARK));
	}


	/**
	 * The test tries to dispatch STN for the item status
	 * "Stored","ACCEPTED_RETURNS" and for all possible /accepted QUALITY level
	 * 
	 * @param qualityTypeToTest
	 * @param itemStatusTotest
	 * @throws JAXBException
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	@Test(groups = { "Regression" }, description = "verify testStnDispatchProcess", dataProvider = "getDataSetStatingDistinctQualityAndItemStatusCombinations", dataProviderClass = StockTransferNoteDataProvider.class)
	void testStnDispatchProcess(Quality qualityTypeToTest,
			ItemStatus itemStatusTotest, StnCategory stnCategory)
			throws JAXBException, IOException, InterruptedException {
		int countofItemsIntentedTobeMoved = 1;
		Long createdStnID = StockTransferUtils
				.createStnAndVerifyCreationToReturnStnId(
						StnTestConstants.DEFAUL_TEST_SOURCE_WAREHOUSEID,
						StnTestConstants.DEFAULT_TEST_DESTINATION_WAREHOUSE_ID,
						itemStatusTotest, qualityTypeToTest,
						StnTestConstants.STN_CREATE_REMARK,
						StnTestConstants.STN_DEFAULT_TEST_APPROVER, stnCategory, StnTestConstants.STN_BUYER_ID);
		Log.info("The stn id created is :: " + createdStnID.toString());

		List<String> itemIdsToTest = StockTransferUtils.createTestItems(
				qualityTypeToTest.toString(), countofItemsIntentedTobeMoved,
				StnTestConstants.TEST_SKU_ID,
				StnTestConstants.DEFAUL_TEST_SOURCE_WAREHOUSEID,
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
		StockTransferUtils.dispatchItemsInBulkAndVerifySuccess(itemIdsToTest,
				createdStnID);
		StockTransferUtils.disptachStnAndConfirmDisptach(createdStnID);

	}

	/**
	 * The test tries to receive STN for the item status
	 * "Stored","ACCEPTED_RETURNS" and for all possible /accepted QUALITY level
	 * 
	 * @param qualityTypeToTest
	 * @param itemStatusTotest
	 * @throws JAXBException
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	@Test(groups = { "Regression" }, description = "verify testStnReceiveProcess", dataProvider = "getDataSetStatingDistinctQualityAndItemStatusCombinations", dataProviderClass = StockTransferNoteDataProvider.class)
	void testStnReceiveProcess(Quality qualityTypeToTest,
			ItemStatus itemStatusTotest, StnCategory stnCategory)
			throws JAXBException, IOException, InterruptedException {
		int countofItemsIntentedTobeMoved = 1;

		Long createdStnID = StockTransferUtils
				.createStnAndVerifyCreationToReturnStnId(
						StnTestConstants.DEFAUL_TEST_SOURCE_WAREHOUSEID,
						StnTestConstants.DEFAULT_TEST_DESTINATION_WAREHOUSE_ID,
						itemStatusTotest, qualityTypeToTest,
						StnTestConstants.STN_CREATE_REMARK,
						StnTestConstants.STN_DEFAULT_TEST_APPROVER, stnCategory, StnTestConstants.STN_BUYER_ID);

		Log.info("The stn id created is :: " + createdStnID.toString());
		List<String> itemIdsToTest = StockTransferUtils.createTestItems(
				qualityTypeToTest.toString(), countofItemsIntentedTobeMoved,
				StnTestConstants.TEST_SKU_ID,
				StnTestConstants.DEFAUL_TEST_SOURCE_WAREHOUSEID,
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
		StockTransferUtils.dispatchItemsInBulkAndVerifySuccess(itemIdsToTest,
				createdStnID);
		StockTransferUtils.disptachStnAndConfirmDisptach(createdStnID);
		
		StockTransferUtils.recieveStockTransferNoteAndVerify(createdStnID,
				StnTestConstants.DEFAULT_TEST_DESTINATION_WAREHOUSE_ID);
	}

	/**
	 * The test tries to associate a sku to STN for the item status
	 * "Stored","ACCEPTED_RETURNS" and for all possible /accepted QUALITY level
	 * 
	 * @param qualityTypeToTest
	 * @param itemStatusTotest
	 * @throws UnsupportedEncodingException
	 * @throws JAXBException
	 */
	
	@Test(groups = { "Regression" }, description = "verify testSingleSkuAssociationWithStn", dataProvider = "getDataSetStatingDistinctQualityAndItemStatusCombinations", dataProviderClass = StockTransferNoteDataProvider.class)
	void testStnDispatchAndReceiveWithMultiQuantityItems(Quality qualityTypeToTest,
			ItemStatus itemStatusTotest, StnCategory stnCategory)
					throws JAXBException, IOException, InterruptedException {
		int countofItemsIntentedTobeMoved = 5;

		Long createdStnID = StockTransferUtils
				.createStnAndVerifyCreationToReturnStnId(
						StnTestConstants.DEFAUL_TEST_SOURCE_WAREHOUSEID,
						StnTestConstants.DEFAULT_TEST_DESTINATION_WAREHOUSE_ID,
						itemStatusTotest, qualityTypeToTest,
						StnTestConstants.STN_CREATE_REMARK,
						StnTestConstants.STN_DEFAULT_TEST_APPROVER, stnCategory, StnTestConstants.STN_BUYER_ID);

		Log.info("The stn id created is :: " + createdStnID.toString());
		List<String> itemIdsToTest = StockTransferUtils.createTestItems(
				qualityTypeToTest.toString(), countofItemsIntentedTobeMoved,
				StnTestConstants.TEST_SKU_ID,
				StnTestConstants.DEFAUL_TEST_SOURCE_WAREHOUSEID,
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
		StockTransferUtils.dispatchItemsInBulkAndVerifySuccess(itemIdsToTest,
				createdStnID);
		StockTransferUtils.disptachStnAndConfirmDisptach(createdStnID);
		
		StockTransferUtils.recieveStockTransferNoteAndVerify(createdStnID,
				StnTestConstants.DEFAULT_TEST_DESTINATION_WAREHOUSE_ID);
	}

	/**
	 * The test tries to reject/cancel STN for the item status
	 * "Stored","ACCEPTED_RETURNS" and for all possible /accepted QUALITY level
	 *
	 * @param qualityTypeToTest
	 * @param itemStatusTotest
	 * @throws JAXBException
	 * @throws IOException
	 * @throws InterruptedException
	 */
	@Test(groups = { "Regression" }, description = "verify testStnCancellationProcess", dataProvider = "getDataSetStatingDistinctQualityAndItemStatusCombinations", dataProviderClass = StockTransferNoteDataProvider.class)
	void testStnCancellationProcess(Quality qualityTypeToTest,
									ItemStatus itemStatusTotest, StnCategory stnCategory)
			throws JAXBException, IOException, InterruptedException {
		int countofItemsIntentedTobeMoved = 1;
		Long createdStnID = StockTransferUtils
				.createStnAndVerifyCreationToReturnStnId(
						StnTestConstants.DEFAUL_TEST_SOURCE_WAREHOUSEID,
						StnTestConstants.DEFAULT_TEST_DESTINATION_WAREHOUSE_ID,
						itemStatusTotest, qualityTypeToTest,
						StnTestConstants.STN_CREATE_REMARK,
						StnTestConstants.STN_DEFAULT_TEST_APPROVER, stnCategory, StnTestConstants.STN_BUYER_ID);

		Log.info("The stn id created is :: " + createdStnID.toString());
		List<String> items = StockTransferUtils.createTestItems(qualityTypeToTest.toString(),
				countofItemsIntentedTobeMoved, StnTestConstants.TEST_SKU_ID,
				StnTestConstants.DEFAUL_TEST_SOURCE_WAREHOUSEID,
				itemStatusTotest.toString(), StnTestConstants.STN_BUYER_ID, StnTestConstants.TEST_PO_SKU_ID);
		String[] wareHouseIdsArray = ("" + StnTestConstants.DEFAUL_TEST_SOURCE_WAREHOUSEID)
				.split("-");

		StockTransferUtils.updateInventory(StnTestConstants.TEST_SKU_ID,
				StnTestConstants.DEFAUL_TEST_SOURCE_WAREHOUSEID, countofItemsIntentedTobeMoved);

		String file = StockTransferUtils.createStnItemsCSVFile(items);

		StockTransferUtils.uploadItemsFile(createdStnID, "csv", file.substring(file.lastIndexOf("/")+1), file);

		Assert.assertTrue(StockTransferUtils.verifyDownloadDocumentStatus(createdStnID, ""));

		Assert.assertTrue(StockTransferUtils.sendStnForApproval(createdStnID));

		StockTransferUtils.rejectStnAndVerifyRejection(createdStnID);
	}


	/**
	 * he test tries to associate skus in bulk to STN for the item status
	 * "Stored","ACCEPTED_RETURNS" and for all possible /accepted QUALITY level
	 *
	 * @param qualityTypeToTest
	 * @param itemStatusTotest
	 * @throws JAXBException
	 * @throws IOException
	 * @throws InterruptedException
	 */
	@Test(groups = { "Regression" }, description = "verify testBulkAssociationOfSkusToStn", dataProvider = "getDataSetStatingDistinctQualityAndItemStatusCombinations", dataProviderClass = StockTransferNoteDataProvider.class)
	void testBulkAssociationOfSkusToStn(Quality qualityTypeToTest,
										ItemStatus itemStatusTotest, StnCategory stnCategory)
			throws JAXBException, IOException, InterruptedException {
		int countofItemsIntentedTobeMovedPerSku = 2;

		Long createdStnID = StockTransferUtils
				.createStnAndVerifyCreationToReturnStnId(
						StnTestConstants.DEFAUL_TEST_SOURCE_WAREHOUSEID,
						StnTestConstants.DEFAULT_TEST_DESTINATION_WAREHOUSE_ID,
						itemStatusTotest, qualityTypeToTest,
						StnTestConstants.STN_CREATE_REMARK,
						StnTestConstants.STN_DEFAULT_TEST_APPROVER, stnCategory, StnTestConstants.STN_BUYER_ID);

		List<String> itemIdsToTest = StockTransferUtils.createTestItems(qualityTypeToTest.toString(),
				countofItemsIntentedTobeMovedPerSku,
				StnTestConstants.TEST_SKU_ID,
				StnTestConstants.DEFAUL_TEST_SOURCE_WAREHOUSEID,
				itemStatusTotest.toString(), StnTestConstants.STN_BUYER_ID, StnTestConstants.TEST_PO_SKU_ID);

		List<String> itemIdsToTest2 = StockTransferUtils.createTestItems(qualityTypeToTest.toString(),
				countofItemsIntentedTobeMovedPerSku,
				StnTestConstants.TEST_SKU_ID_1,
				StnTestConstants.DEFAUL_TEST_SOURCE_WAREHOUSEID,
				itemStatusTotest.toString(), StnTestConstants.STN_BUYER_ID, StnTestConstants.TEST_PO_SKU_ID_1);

		List<String> totalItemsToTest = new ArrayList();
		totalItemsToTest.addAll(itemIdsToTest);
		totalItemsToTest.addAll(itemIdsToTest2);

		StockTransferUtils.updateInventory(StnTestConstants.TEST_SKU_ID,
				StnTestConstants.DEFAUL_TEST_SOURCE_WAREHOUSEID, countofItemsIntentedTobeMovedPerSku);

		StockTransferUtils.updateInventory(StnTestConstants.TEST_SKU_ID_1,
				StnTestConstants.DEFAUL_TEST_SOURCE_WAREHOUSEID, countofItemsIntentedTobeMovedPerSku);

		String file = StockTransferUtils.createStnItemsCSVFile(totalItemsToTest);

		StockTransferUtils.uploadItemsFile(createdStnID, "csv", file.substring(file.lastIndexOf("/")+1), file);

		Assert.assertTrue(StockTransferUtils.verifyDownloadDocumentStatus(createdStnID, ""));

		Assert.assertTrue(StockTransferUtils.sendStnForApproval(createdStnID));

		Assert.assertTrue(StockTransferUtils.validateStnQuantity(createdStnID));

		Assert.assertTrue(StockTransferUtils.approveStn(createdStnID, StnTestConstants.DEFAUL_TEST_SOURCE_WAREHOUSEID,
				StnTestConstants.DEFAULT_TEST_DESTINATION_WAREHOUSE_ID, qualityTypeToTest, itemStatusTotest,
				stnCategory, StnTestConstants.STN_DEFAULT_TEST_APPROVER, StnTestConstants.STN_CREATE_REMARK));

		StockTransferUtils.initiatePickUpForStnAndVerifySuccess(createdStnID);
		StockTransferUtils.dispatchItemsInBulkAndVerifySuccess(itemIdsToTest,
				createdStnID);
		StockTransferUtils.disptachStnAndConfirmDisptach(createdStnID);

		StockTransferUtils.recieveStockTransferNoteAndVerify(createdStnID,
				StnTestConstants.DEFAULT_TEST_DESTINATION_WAREHOUSE_ID);
	}

	/*
	@Test(groups = { "Regression" }, description = "verify testSingleSkuAssociationWithStn", dataProvider = "getDataSetStatingDistinctQualityAndItemStatusCombinations", dataProviderClass = StockTransferNoteDataProvider.class)
	void testSingleSkuAssociationWithStn(Quality qualityTypeToTest,
			ItemStatus itemStatusTotest, StnCategory stnCategory)
			throws UnsupportedEncodingException, JAXBException {
		int countofItemsIntentedTobeMoved = 1;

		Long createdStnID = StockTransferUtils
				.createStnAndVerifyCreationToReturnStnId(
						StnTestConstants.DEFAUL_TEST_SOURCE_WAREHOUSEID,
						StnTestConstants.DEFAULT_TEST_DESTINATION_WAREHOUSE_ID,
						itemStatusTotest, qualityTypeToTest,
						StnTestConstants.STN_CREATE_REMARK,
						StnTestConstants.STN_DEFAULT_TEST_APPROVER, stnCategory, StnTestConstants.STN_BUYER_ID);

		StockTransferUtils.createTestItems(qualityTypeToTest.toString(),
				countofItemsIntentedTobeMoved, StnTestConstants.TEST_SKU_ID,
				StnTestConstants.DEFAUL_TEST_SOURCE_WAREHOUSEID,
				itemStatusTotest.toString(), StnTestConstants.STN_BUYER_ID);

		String[] wareHouseIdsArray = ("" + StnTestConstants.DEFAUL_TEST_SOURCE_WAREHOUSEID)
				.split("-");
		TaxationUtil.setUpInventoryForTheSkuUnderTest(
				countofItemsIntentedTobeMoved, wareHouseIdsArray,
				StnTestConstants.TEST_SKU_ID);

		StockTransferUtils.associateSkuToStockTransferRequest(createdStnID,
				StnTestConstants.TEST_SKU_ID, countofItemsIntentedTobeMoved);

		HashMap<Integer, Integer> skuIdAndQuantityMap = StockTransferUtils
				.getSkuAndQuanityAssociatedWithStn(createdStnID);
		// since we pass only one sku to Associate with Stn
		Assert.assertTrue(skuIdAndQuantityMap.size() == 1,
				"The size of the skuIdAndQuantityMap is not 1 but is "
						+ skuIdAndQuantityMap.size());
		System.out.println(" "
				+ skuIdAndQuantityMap.get(StnTestConstants.TEST_SKU_ID));
		Assert.assertTrue(skuIdAndQuantityMap.get(StnTestConstants.TEST_SKU_ID)
				.equals(countofItemsIntentedTobeMoved));

	}	*/


	/**
	 * he test tries to associate skus in a sequential manner to STN for the
	 * item status "Stored","ACCEPTED_RETURNS" and for all possible /accepted
	 * QUALITY level
	 * 
	 * @param qualityTypeToTest
	 * @param itemStatusTotest
	 * @throws UnsupportedEncodingException
	 * @throws JAXBException
	 */
	/*
	@Test(groups = { "Regression" }, description = "verify testSequentialAssociationOfSkusToStn", dataProvider = "getDataSetStatingDistinctQualityAndItemStatusCombinations", dataProviderClass = StockTransferNoteDataProvider.class)
	void testSequentialAssociationOfSkusToStn(Quality qualityTypeToTest,
			ItemStatus itemStatusTotest, StnCategory stnCategory)
			throws UnsupportedEncodingException, JAXBException {
		int countofItemsIntentedTobeMovedPerSku = 1;

		Long createdStnID = StockTransferUtils
				.createStnAndVerifyCreationToReturnStnId(
						StnTestConstants.DEFAUL_TEST_SOURCE_WAREHOUSEID,
						StnTestConstants.DEFAULT_TEST_DESTINATION_WAREHOUSE_ID,
						itemStatusTotest, qualityTypeToTest,
						StnTestConstants.STN_CREATE_REMARK,
						StnTestConstants.STN_DEFAULT_TEST_APPROVER, stnCategory, StnTestConstants.STN_BUYER_ID);

		String[] wareHouseIdsArray = ("" + StnTestConstants.DEFAUL_TEST_SOURCE_WAREHOUSEID)
				.split("-");

		StockTransferUtils.createTestItems(qualityTypeToTest.toString(),
				countofItemsIntentedTobeMovedPerSku,
				StnTestConstants.TEST_SKU_ID,
				StnTestConstants.DEFAUL_TEST_SOURCE_WAREHOUSEID,
				itemStatusTotest.toString(), StnTestConstants.STN_BUYER_ID);

		StockTransferUtils.createTestItems(qualityTypeToTest.toString(),
				countofItemsIntentedTobeMovedPerSku,
				StnTestConstants.TEST_SKU_ID,
				StnTestConstants.DEFAUL_TEST_SOURCE_WAREHOUSEID,
				itemStatusTotest.toString(), StnTestConstants.STN_BUYER_ID);

		TaxationUtil.setUpInventoryForTheSkuUnderTest(
				countofItemsIntentedTobeMovedPerSku, wareHouseIdsArray,
				StnTestConstants.TEST_SKU_ID);
		TaxationUtil.setUpInventoryForTheSkuUnderTest(
				countofItemsIntentedTobeMovedPerSku, wareHouseIdsArray,
				StnTestConstants.TEST_SKU_ID_1);

		StnSkuResponse firstSkuStnAssociationResponse = StockTransferUtils
				.associateSkuToStockTransferRequest(createdStnID,
						StnTestConstants.TEST_SKU_ID,
						countofItemsIntentedTobeMovedPerSku);

		Assert.assertTrue(
				firstSkuStnAssociationResponse
						.getStatus()
						.getStatusMessage()
						.equals(StnTestConstants.SKU_STN_ASSOCIATION_SUCCESS_STATUS_MESSAGE),
				"The stn update response status message is not "
						+ StnTestConstants.SKU_STN_ASSOCIATION_SUCCESS_STATUS_MESSAGE);
		Assert.assertTrue(
				firstSkuStnAssociationResponse.getStatus().getStatusCode() == StnTestConstants.SKU_STN_ASSOCIATION_SUCCESS_STATUS_CODE,
				"The stn update response status code is not "
						+ StnTestConstants.SKU_STN_ASSOCIATION_SUCCESS_STATUS_CODE);

		HashMap<Integer, Integer> skuIdAndQuantityMap = StockTransferUtils
				.getSkuAndQuanityAssociatedWithStn(createdStnID);
		// since at moment we have only one sku associated with Stn
		Assert.assertTrue(skuIdAndQuantityMap.size() == 1,
				"The size of the skuIdAndQuantityMap is not 1 but is "
						+ skuIdAndQuantityMap.size());
		Assert.assertTrue(skuIdAndQuantityMap.get(StnTestConstants.TEST_SKU_ID)
				.equals(countofItemsIntentedTobeMovedPerSku));

		StnSkuResponse secondSkuStnAssociationResponse = StockTransferUtils
				.associateSkuToStockTransferRequest(createdStnID,
						StnTestConstants.TEST_SKU_ID_1,
						countofItemsIntentedTobeMovedPerSku);

		Assert.assertTrue(
				secondSkuStnAssociationResponse
						.getStatus()
						.getStatusMessage()
						.equals(StnTestConstants.SKU_STN_ASSOCIATION_SUCCESS_STATUS_MESSAGE),
				"The stn update response status message is not "
						+ StnTestConstants.SKU_STN_ASSOCIATION_SUCCESS_STATUS_MESSAGE);
		Assert.assertTrue(
				secondSkuStnAssociationResponse.getStatus().getStatusCode() == StnTestConstants.SKU_STN_ASSOCIATION_SUCCESS_STATUS_CODE,
				"The stn update response status code is not "
						+ StnTestConstants.SKU_STN_ASSOCIATION_SUCCESS_STATUS_CODE);

		HashMap<Integer, Integer> updatedSkuIdAndQuantityMap = StockTransferUtils
				.getSkuAndQuanityAssociatedWithStn(createdStnID);
		// since at moment we have two skus associated with Stn
		Assert.assertTrue(updatedSkuIdAndQuantityMap.size() == 2,
				"The size of the skuIdAndQuantityMap is not 2 but is "
						+ skuIdAndQuantityMap.size());
		Assert.assertTrue(updatedSkuIdAndQuantityMap.get(
				StnTestConstants.TEST_SKU_ID_1).equals(
				countofItemsIntentedTobeMovedPerSku));

	}	*/

	/**
	 * he test tries to associate a sku to STN and tries to fetch the Sku's
	 * associated with it for the item status "Stored","ACCEPTED_RETURNS" and
	 * for all possible /accepted QUALITY level
	 * 
	 * @param qualityTypeToTest
	 * @param itemStatusTotest
	 * @throws UnsupportedEncodingException
	 * @throws JAXBException
	 */
	/*
	@Test(groups = { "Regression" }, description = "verify testAssociatedSkuListRetreivalFromStn", dataProvider = "getDataSetStatingDistinctQualityAndItemStatusCombinations", dataProviderClass = StockTransferNoteDataProvider.class)
	void testAssociatedSkuListRetreivalFromStn(Quality qualityTypeToTest,
			ItemStatus itemStatusTotest, StnCategory stnCategory)
			throws UnsupportedEncodingException, JAXBException {
		int countofItemsIntentedTobeMovedPerSku = 1;

		Long createdStnID = StockTransferUtils
				.createStnAndVerifyCreationToReturnStnId(
						StnTestConstants.DEFAUL_TEST_SOURCE_WAREHOUSEID,
						StnTestConstants.DEFAULT_TEST_DESTINATION_WAREHOUSE_ID,
						itemStatusTotest, qualityTypeToTest,
						StnTestConstants.STN_CREATE_REMARK,
						StnTestConstants.STN_DEFAULT_TEST_APPROVER, stnCategory, StnTestConstants.STN_BUYER_ID);

		String[] wareHouseIdsArray = ("" + StnTestConstants.DEFAUL_TEST_SOURCE_WAREHOUSEID)
				.split("-");

		StockTransferUtils.createTestItems(qualityTypeToTest.toString(),
				countofItemsIntentedTobeMovedPerSku,
				StnTestConstants.TEST_SKU_ID,
				StnTestConstants.DEFAUL_TEST_SOURCE_WAREHOUSEID,
				itemStatusTotest.toString(), StnTestConstants.STN_BUYER_ID, StnTestConstants.TEST_PO_SKU_ID);

		StockTransferUtils.createTestItems(qualityTypeToTest.toString(),
				countofItemsIntentedTobeMovedPerSku,
				StnTestConstants.TEST_SKU_ID_1,
				StnTestConstants.DEFAUL_TEST_SOURCE_WAREHOUSEID,
				itemStatusTotest.toString(), StnTestConstants.STN_BUYER_ID, StnTestConstants.TEST_PO_SKU_ID);

		TaxationUtil.setUpInventoryForTheSkuUnderTest(
				countofItemsIntentedTobeMovedPerSku, wareHouseIdsArray,
				StnTestConstants.TEST_SKU_ID);
		TaxationUtil.setUpInventoryForTheSkuUnderTest(
				countofItemsIntentedTobeMovedPerSku, wareHouseIdsArray,
				StnTestConstants.TEST_SKU_ID_1);

		StnSkuResponse firstSkuStnAssociationResponse = StockTransferUtils
				.associateSkuToStockTransferRequest(createdStnID,
						StnTestConstants.TEST_SKU_ID,
						countofItemsIntentedTobeMovedPerSku);

		Assert.assertTrue(
				firstSkuStnAssociationResponse
						.getStatus()
						.getStatusMessage()
						.equals(StnTestConstants.SKU_STN_ASSOCIATION_SUCCESS_STATUS_MESSAGE),
				"The stn update response status message is not "
						+ StnTestConstants.SKU_STN_ASSOCIATION_SUCCESS_STATUS_MESSAGE);
		Assert.assertTrue(
				firstSkuStnAssociationResponse.getStatus().getStatusCode() == StnTestConstants.SKU_STN_ASSOCIATION_SUCCESS_STATUS_CODE,
				"The stn update response status code is not "
						+ StnTestConstants.SKU_STN_ASSOCIATION_SUCCESS_STATUS_CODE);

		HashMap<Integer, Integer> skuIdAndQuantityMap = StockTransferUtils
				.getSkuAndQuanityAssociatedWithStn(createdStnID);
		// since at moment we have only one sku associated with Stn
		Assert.assertTrue(skuIdAndQuantityMap.size() == 1,
				"The size of the skuIdAndQuantityMap is not 1 but is "
						+ skuIdAndQuantityMap.size());
		Assert.assertTrue(skuIdAndQuantityMap.get(StnTestConstants.TEST_SKU_ID)
				.equals(countofItemsIntentedTobeMovedPerSku));

	}	*/

	/**
	 * The test tries dispatch lower number of items per sku than intented to
	 * move for item status"Stored","ACCEPTED_RETURNS" and for all possible
	 * /accepted QUALITY level
	 * 
	 * @param qualityTypeToTest
	 * @param itemStatusTotest
	 * @throws UnsupportedEncodingException
	 * @throws JAXBException
	 */
	/*
	@Test(groups = { "Regression" }, description = "verify testStnDispatchWithLowerItemsAssociatedThanDeclaredToMove", dataProvider = "getDataSetStatingDistinctQualityAndItemStatusCombinations", dataProviderClass = StockTransferNoteDataProvider.class)
	void testStnDispatchWithLowerItemsAssociatedThanDeclaredToMove(
			Quality qualityTypeToTest, ItemStatus itemStatusTotest,
			StnCategory stnCategory) throws UnsupportedEncodingException,
			JAXBException {
		int countofItemsIntentedTobeMoved = 3;
		Long createdStnID = StockTransferUtils
				.createStnAndVerifyCreationToReturnStnId(
						StnTestConstants.DEFAUL_TEST_SOURCE_WAREHOUSEID,
						StnTestConstants.DEFAULT_TEST_DESTINATION_WAREHOUSE_ID,
						itemStatusTotest, qualityTypeToTest,
						StnTestConstants.STN_CREATE_REMARK,
						StnTestConstants.STN_DEFAULT_TEST_APPROVER, stnCategory, StnTestConstants.STN_BUYER_ID);

		Log.info("The stn id created is :: " + createdStnID.toString());
		// Creating only 2 items
		List<String> itemIdsToTest = StockTransferUtils.createTestItems(
				qualityTypeToTest.toString(),
				countofItemsIntentedTobeMoved - 1,
				StnTestConstants.TEST_SKU_ID,
				StnTestConstants.DEFAUL_TEST_SOURCE_WAREHOUSEID,
				itemStatusTotest.toString(), StnTestConstants.STN_BUYER_ID);
		String[] wareHouseIdsArray = ("" + StnTestConstants.DEFAUL_TEST_SOURCE_WAREHOUSEID)
				.split("-");
		TaxationUtil.setUpInventoryForTheSkuUnderTest(
				countofItemsIntentedTobeMoved, wareHouseIdsArray,
				StnTestConstants.TEST_SKU_ID);

		StockTransferUtils.approveStnPostTestSkuAssociationAndVerifySuccess(
				createdStnID, countofItemsIntentedTobeMoved);
		StockTransferUtils.initiatePickUpForStnAndVerifySuccess(createdStnID);
		// trying to disptach only two items (itemIdsToTest) when
		// countofItemsIntentedTobeMoved are 3
		StockTransferUtils.dispatchItemsInBulkAndVerifySuccess(itemIdsToTest,
				createdStnID);
		StockTransferUtils.disptachStnAndConfirmDisptach(createdStnID);

	}	*/

}
