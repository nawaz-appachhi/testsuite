package com.myntra.apiTests.erpservices.partners.Tests;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBException;

import com.myntra.apiTests.erpservices.lms.Helper.LmsServiceHelper;
import com.myntra.apiTests.erpservices.oms.OMSServiceHelper;
import com.myntra.apiTests.erpservices.partners.SellerPaymentServiceHelper;
import com.myntra.apiTests.erpservices.partners.dp.SellerPaymentDP;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;
import org.slf4j.*;

import com.myntra.lms.client.response.PincodeEntryV2;
import com.myntra.lms.client.response.PincodeResponseV2;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.oms.common.enums.PaymentMethod;
import com.myntra.oms.common.enums.RefundType;
import com.myntra.taxmaster.client.entry.TaxEntry;
import com.myntra.taxmaster.client.enums.TaxType;
import com.myntra.taxmaster.client.response.TaxResponse;
import com.myntra.test.commons.testbase.BaseTest;

public class SellerPaymentServiceOnline extends BaseTest {

	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = LoggerFactory.getLogger(SellerPaymentServiceOnline.class);
	static String envName = "fox8";

	APIUtilities apiUtil = new APIUtilities();

	SellerPaymentServiceHelper sellerHelper = new SellerPaymentServiceHelper();
	SellerPaymentDP sellerDP = new SellerPaymentDP();
	OMSServiceHelper omsServiceHelper = new OMSServiceHelper();

	// Create online order with seller qty1 sku 1 and cancel. Check split and
	// refund for that sku in citrus and Db both
	@Test(groups = { "Regression",
			"Smoke" }, priority = 0, dataProvider = "SPS_SellerQty1", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1. Create online order with seller qty1 sku1. \n2. check the split in citrus than in SPS")
	public void SPS_SellerQty1(String orderId, String store_order_id, String ppsId, String paymentPlanItemId,
			String message, String lineId) throws Exception {
		log.debug("----------------" + orderId + " : " + ppsId + " : " + paymentPlanItemId);
		sellerHelper.rabbitMqAddTx(message, orderId);
		// int splits = sellerHelper.getNoOfSplits(store_order_id);
		// log.debug("Number of Splits: " + splits);
		Thread.sleep(10000);
		int txResult = sellerHelper.getSplitTxDBCount(orderId);
		int pgResult = sellerHelper.getSplitPgDBCount(orderId);
		log.debug("DB check Tx table splits count: " + txResult + " pg table: " + pgResult);
		// AssertJUnit.assertEquals(1, splits);
		AssertJUnit.assertEquals(1, txResult);
		AssertJUnit.assertEquals(1, pgResult);
		sellerHelper.deleteOrderRecord(orderId, ppsId, paymentPlanItemId);

	}

	// Create online order with seller qty1 sku 1 and cancel. Check split and
	// refund for that sku in citrus and Db both
	@Test(groups = { "Regression",
			"Smoke" }, priority = 0, dataProvider = "SPS_SellerQty2WithSkuPriceZero", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1. Create online order with seller qty1 sku1. \n2. check the split in citrus than in SPS")
	public void SPS_SellerQty2WithSkuPriceZero(String orderId, String OrderReleaseId, String OrderReleaseId1,
			String store_order_id, String ppsId, String paymentPlanItemId, String message, String lineId,
			String releaseMessage, String releaseMessage1) throws SQLException, IOException, InterruptedException {
		log.debug("----------------" + orderId + " : " + ppsId + " : " + paymentPlanItemId);
		sellerHelper.rabbitMqAddTx(message, orderId);
		Thread.sleep(10000);
		// int splits = sellerHelper.getNoOfSplits(store_order_id);
		// log.debug("Number of Splits: " + splits);
		int txResult = sellerHelper.getSplitTxDBCount(orderId);
		int pgResult = sellerHelper.getSplitPgDBCount(orderId);
		log.debug("DB check Tx table splits count: " + txResult + " pg table: " + pgResult);
		// Assert.assertEquals(splits, 2, "Splits :");
		Assert.assertEquals(txResult, 3, "Transaction Result : ");
		Assert.assertEquals(pgResult, 1, "PG Results: ");

		sellerHelper.updateOrderReleaseStatusAndPackedOn(OrderReleaseId, "PK");
		sellerHelper.updateOrderLineStatus(orderId, "QD");
		sellerHelper.rabbitMqReleasePKTx(releaseMessage, orderId);
		sellerHelper.updateOrderReleaseStatusAndPackedOn(OrderReleaseId1, "PK");
		sellerHelper.rabbitMqReleasePKTx(releaseMessage1, orderId);
		Thread.sleep(10000);
		// int release = sellerHelper.getNoOfRelease(store_order_id);
		// log.debug("Number of release: " + release);
		int releaseTxResult = sellerHelper.getTxDBStatus1(orderId, "RELEASED");
		log.debug("DB status for release: " + releaseTxResult);
		// AssertJUnit.assertEquals(2, release);
		Assert.assertEquals(releaseTxResult, 3,
				" Release orderId :" + orderId + " not found in Transaction details table");

		int releaseSettlResult = sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED");
		Assert.assertEquals(releaseSettlResult, 3,
				"Order Id: " + orderId + " Not available in SPS Settlement detail table");

		sellerHelper.deleteFromOrders(orderId);
		sellerHelper.deleteFromOrdersLine(orderId);
		sellerHelper.deleteFromOrdersRelease(orderId);

	}

	// Create online order with seller qty1 sku 1 and cancel. Check split and
	// refund for that sku in citrus and Db both
	@Test(groups = { "Regression",
			"Smoke" }, priority = 0, dataProvider = "SPS_VectorQty1", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1. Create online order with Vector qty1 sku1. \n2.check the split in citrus than in SPS")
	public void SPS_VectorQty1(String orderId, String store_order_id, String ppsId, String paymentPlanItemId,
			String message, String lineId) throws SQLException, IOException, InterruptedException {
		log.debug(orderId + " : " + ppsId + " : " + paymentPlanItemId);
		sellerHelper.rabbitMqAddTx(message, orderId);
		Thread.sleep(10000);
		// int splits = sellerHelper.getNoOfSplits(store_order_id);
		// log.debug("Number of Splits: " + splits);
		int txResult = sellerHelper.getSplitTxDBCount(orderId);
		int pgResult = sellerHelper.getSplitPgDBCount(orderId);
		log.debug("DB check Tx table splits count: " + txResult + " pg table: " + pgResult);
		// AssertJUnit.assertEquals(1, splits);
		AssertJUnit.assertEquals(1, txResult);
		AssertJUnit.assertEquals(1, pgResult);
		sellerHelper.deleteFromOrders(orderId);
		sellerHelper.deleteFromOrdersLine(orderId);
		sellerHelper.deleteFromOrdersRelease(orderId);
	}

	// Create online order with seller qty1 sku 1 and cancel. Check split and
	// refund for that sku in citrus and Db both
	@Test(groups = { "Regression",
			"Smoke" }, priority = 0, dataProvider = "SPS_SellerQty1Cancel", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1.Create online order with seller qty1 sku1.\n2.check the split in citrus than in SPS. \n3.cancel the order. \n4.check the refund on citurs and sps both")
	public void SPS_SellerQty1Cancel(String orderId, String store_order_id, String ppsId, String ppsIdCancellation,
			String paymentPlanItemId, String paymentPlanItemIdref, String message, String cancellationMessage,
			String lineId) throws SQLException, IOException, InterruptedException {
		log.debug(orderId + " : " + ppsId + " : " + paymentPlanItemId);
		sellerHelper.rabbitMqAddTx(message, orderId);
		Thread.sleep(10000);
		// int splits = sellerHelper.getNoOfSplits(store_order_id);
		// log.debug("Number of Splits: " + splits);
		int txResult = sellerHelper.getSplitTxDBCount(orderId);
		int pgResult = sellerHelper.getSplitPgDBCount(orderId);
		log.debug("DB check Tx table splits count: " + txResult + " pg table: " + pgResult);
		// AssertJUnit.assertEquals(1, splits);
		AssertJUnit.assertEquals(1, txResult);
		AssertJUnit.assertEquals(1, pgResult);
		log.debug(cancellationMessage);
		sellerHelper.rabbitMqRefundTx(cancellationMessage, ppsIdCancellation);
		Thread.sleep(10000);
		// int refund = sellerHelper.getNoOfRefund(store_order_id);
		// log.debug("Number of Refund: " + refund);
		int refundTxResult = sellerHelper.getTxDBStatus1(orderId, "REFUNDED");
		log.debug("DB status for refund: " + refundTxResult);
		// AssertJUnit.assertEquals(1, refund);
		AssertJUnit.assertEquals(1, refundTxResult);
		sellerHelper.deleteOrderRecord(orderId, ppsId, paymentPlanItemId, ppsIdCancellation, paymentPlanItemIdref);
	}

	// Create online order with JIT Multi Sku and Multi qty release all. Check
	// split for that sku in citrus and Db both
	@Test(groups = {
			"Regression" }, priority = 0, dataProvider = "SPS_JITMuliSkuMultiQtyRel4Return4", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1. Create online order with JIT MultiQty MultiSku1(Tatal 4qty). \n2.check the split for all the skus in citrus than in SPS.\n3.Release all check released."
					+ "\n4. Return 1 sku with qty1 and check the refund only for that sku")
	public void SPS_JITMuliSkuMultiQtyRel4Return4(String orderId, String store_order_id, String OrderReleaseId,
			String ppsId, String paymentPlanItemId, String paymentPlanItemId1, String message,
			String releaseFundMessage, String lineId, String ppsIdCancellation, String ppsIdCancellation1,
			String ppsIdCancellation2, String ppsIdCancellation3, String paymentPlanItemIdref,
			String paymentPlanItemIdref1, String paymentPlanItemIdref2, String paymentPlanItemIdref3,
			String returnMessage, String returnMessage1, String returnMessage2, String returnMessage3)
			throws SQLException, IOException, InterruptedException {
		log.debug(orderId + " : " + ppsId + " : " + paymentPlanItemId);
		sellerHelper.rabbitMqAddTx(message, orderId);
		Thread.sleep(10000);
		// int splits = sellerHelper.getNoOfSplits(store_order_id);
		// log.debug("Number of Splits: " + splits);
		int txResult = sellerHelper.getSplitTxDBCount(orderId);
		int pgResult = sellerHelper.getSplitPgDBCount(orderId);
		log.debug("DB check Tx table splits count: " + txResult + " pg table: " + pgResult);
		// AssertJUnit.assertEquals(4, splits);
		AssertJUnit.assertEquals(4, txResult);
		AssertJUnit.assertEquals(1, pgResult);
		sellerHelper.updateOrderReleaseStatus(orderId, "PK");
		sellerHelper.updateOrderLineStatus(orderId, "QD");
		sellerHelper.rabbitMqReleasePKTx(releaseFundMessage, orderId);
		Thread.sleep(10000);
		// int release = sellerHelper.getNoOfRelease(store_order_id);
		// log.debug("Number of release: " + release);
		int releaseTxResult = sellerHelper.getTxDBStatusCount1(orderId, "1152953", "RELEASED");
		int releaseTxResult1 = sellerHelper.getTxDBStatusCount1(orderId, "1152953", "RELEASED");
		log.debug("DB status for release: " + releaseTxResult);
		// AssertJUnit.assertEquals(4, release);
		AssertJUnit.assertEquals(2, releaseTxResult);
		AssertJUnit.assertEquals(2, releaseTxResult1);

		int releaseSettlResult = sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED");
		Assert.assertEquals(releaseSettlResult, 4,
				"Order Id: " + orderId + " Not available in SPS Settlement detail table");

		SellerPaymentDP.SPS_JITMuliSkuMultiQtyHelper(orderId, store_order_id, OrderReleaseId, paymentPlanItemIdref,
				paymentPlanItemIdref1, paymentPlanItemIdref2, paymentPlanItemIdref3, ppsId, ppsIdCancellation,
				ppsIdCancellation1, ppsIdCancellation2, ppsIdCancellation3);
		sellerHelper.rabbitMqRefundTx(returnMessage, ppsIdCancellation);
		Thread.sleep(10000);

		// int refund = sellerHelper.getNoOfRefund(store_order_id);
		// log.debug("Number of Refund: " + refund);
		int refundTxResult = sellerHelper.getTxDBStatus(orderId, "1152953", "REFUNDED");
		log.debug("DB status for refund: " + refundTxResult);
		// AssertJUnit.assertEquals(1, refund);
		AssertJUnit.assertEquals(1, refundTxResult);

		int releaseTxResult2 = sellerHelper.getSettlementdetailDBStatusCount(orderId, "REFUNDED");
		Assert.assertEquals(releaseTxResult2, 1,
				"REFUND Order_id:" + orderId + " Not available in SPS Settlement detail table");
		sellerHelper.rabbitMqRefundTx(returnMessage1, ppsIdCancellation1);
		log.debug("Wait for 10Sec more to get order details in settlement detail table....");
		Thread.sleep(10000);
		int releaseTxResult3 = sellerHelper.getSettlementdetailDBStatusCount(orderId, "REFUNDED");
		Assert.assertEquals(releaseTxResult3, 2,
				"REFUND Order_id:" + orderId + " Not available in SPS Settlement detail table");

		sellerHelper.rabbitMqRefundTx(returnMessage2, ppsIdCancellation2);
		log.debug("Wait for 10Sec more to get order details in settlement detail table....");
		Thread.sleep(10000);
		int releaseTxResult4 = sellerHelper.getSettlementdetailDBStatusCount(orderId, "REFUNDED");
		Assert.assertEquals(releaseTxResult4, 3,
				"REFUND Order_id:" + orderId + " Not available in SPS Settlement detail table");

		sellerHelper.rabbitMqRefundTx(returnMessage3, ppsIdCancellation3);
		log.debug("Wait for 10Sec more to get order details in settlement detail table....");
		Thread.sleep(10000);
		int releaseTxResult5 = sellerHelper.getSettlementdetailDBStatusCount(orderId, "REFUNDED");
		Assert.assertEquals(releaseTxResult5, 4,
				"REFUND Order_id:" + orderId + " Not available in SPS Settlement detail table");

		sellerHelper.deleteFromPaymentPlanItemInstrument(paymentPlanItemId1);
		sellerHelper.deleteOrderRecord(orderId, ppsId, paymentPlanItemId, ppsIdCancellation, paymentPlanItemIdref);
	}

	// Create online order with seller qty1 sku 1 With Partial Cash back and
	// cancel. Check split and refund for that sku in citrus and Db both.
	// Check that SPS only take the amount which paid online not by
	@Test(groups = {
			"Regression" }, priority = 0, dataProvider = "SPS_VectorQty1CB", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1. Create online order with Vector qty1 sku1 and apply cash back. \n2.check that only online amount is in split in citrus & SPS. \n3.validate the fee amount")
	public void SPS_VectorQty1CB(String orderId, String store_order_id, String ppsId, String paymentPlanItemId,
			String message, String lineId) throws SQLException, IOException, InterruptedException {
		log.debug(orderId + " : " + ppsId + " : " + paymentPlanItemId);
		sellerHelper.rabbitMqAddTx(message, orderId);
		Thread.sleep(10000);
		java.util.List feeAmountAndSplits = sellerHelper.getNoOfSplitsAndFeeAmount(store_order_id);
		log.debug("Split and fee amount: " + feeAmountAndSplits);
		BigDecimal margin = sellerHelper.getSellerMargin(orderId);
		Double x = 299.00 * (margin.doubleValue() / 100);
		double marginCalc = (Math.round(x * 100.0) / 100.0) - 0.01;
		log.debug("Number of Splits: " + feeAmountAndSplits.get(0));
		String feeAmount = "" + feeAmountAndSplits.get(1);
		log.debug("fee amount: " + feeAmount);
		log.debug("margin calculation: " + marginCalc);
		int txResult = sellerHelper.getSplitTxDBCount(orderId);
		int pgResult = sellerHelper.getSplitPgDBCount(orderId);
		log.debug("DB check Tx table splits count: " + txResult + " pg table: " + pgResult);
		AssertJUnit.assertEquals(1, Integer.parseInt((String) feeAmountAndSplits.get(0)));
		AssertJUnit.assertEquals(1, txResult);
		AssertJUnit.assertEquals(1, pgResult);
		AssertJUnit.assertEquals(marginCalc, feeAmountAndSplits.get(1));
		sellerHelper.deleteOrderRecord(orderId, ppsId, paymentPlanItemId);
	}

	// Create online order with seller qty1 sku 1 and cancel. Check split and
	// refund for that sku in citrus and Db both
	@Test(groups = { "Regression",
			"Smoke" }, priority = 0, dataProvider = "SPS_SellerQty1WalletAmountReleaseReturn", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1. Create online order with seller qty1 sku1. \n2. check the split in citrus than in SPS")
	public void SPS_SellerQty1WalletAmountReleaseReturn(String orderId, String store_order_id, String ppsId,
			String paymentPlanItemId, String message, String lineId, String releaseFundMessage,
			String paymentPlanItemIdref, String ppsIdCancellation, String returnMessage)
			throws SQLException, IOException, InterruptedException {
		log.debug("----------------" + orderId + " : " + ppsId + " : " + paymentPlanItemId);
		sellerHelper.rabbitMqAddTx(message, orderId);
		Thread.sleep(10000);
		// int splits = sellerHelper.getNoOfSplits(store_order_id);
		// log.debug("Number of Splits: " + splits);
		int txResult = sellerHelper.getSplitTxDBCount(orderId);
		int pgResult = sellerHelper.getSplitPgDBCount(orderId);
		log.debug("DB check Tx table splits count: " + txResult + " pg table: " + pgResult);
		// AssertJUnit.assertEquals(1, splits);
		AssertJUnit.assertEquals(1, txResult);
		AssertJUnit.assertEquals(1, pgResult);
		Assert.assertEquals(sellerHelper.getTxAmount(orderId), "799.00");

		sellerHelper.updateOrderReleaseStatus(orderId, "PK");
		sellerHelper.updateOrderLineStatus(orderId, "QD");
		sellerHelper.rabbitMqReleasePKTx(releaseFundMessage, orderId);
		Thread.sleep(10000);
		// int release = sellerHelper.getNoOfRelease(store_order_id);
		// log.debug("Number of release: " + release);
		int releaseTxResult = sellerHelper.getTxDBStatus1(orderId, "RELEASED");

		log.debug("DB status for release: " + releaseTxResult);
		// Assert.assertEquals(release, 1, "Order Id: " + orderId + " Not
		// available in SPS Transaction detail table");
		Assert.assertEquals(releaseTxResult, 1, "sps transaction_detail status :");

		int releaseTxResult1 = sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED");
		Assert.assertEquals(releaseTxResult1, 1,
				"Order Id: " + orderId + " Not available in SPS Settlement detail table");
		Assert.assertEquals(sellerHelper.getSettlementDetailFundReleaseAmount(orderId, "RELEASED"), "799.00",
				"Online Release amount:");

		log.debug("Number of releases on SPS Transaction_detail DB: " + releaseTxResult1);
		log.debug("Fund Release Amount :" + sellerHelper.getSettlementDetailFundReleaseAmount(orderId, "RELEASED"));

		SellerPaymentDP.create_Online_Wallet_OrderWithSellerQty1ReleaseHelper(orderId, store_order_id,
				paymentPlanItemIdref, ppsId, ppsIdCancellation);
		sellerHelper.rabbitMqRefundTx(returnMessage, ppsIdCancellation);

		log.debug("Wait for 10Sec more to get order details in settlement detail table....");
		Thread.sleep(10000);

		int releaseTxResult2 = sellerHelper.getSettlementdetailDBStatusCount(orderId, "REFUNDED");
		Assert.assertEquals(releaseTxResult2, 1,
				"REFUND Order_id:" + orderId + " Not available in SPS Settlement detail table");
		// Assert.assertEquals(sellerHelper.getSettlementDetailRefundAmount(orderId,"CASH_ON_DELIVERY"),"-1399.00"
		// ,"COD Refund amount:");
		Assert.assertEquals(sellerHelper.getSettlementDetailRefundAmount(orderId, "ONLINE"), "-799.00",
				"Online Refund amount:");

	//	sellerHelper.deleteFromPaymentPlanItemInstrument(paymentPlanItemId);
	//	sellerHelper.deleteOrderRecord(orderId, ppsId, paymentPlanItemId);

	}

	// Create online order with seller qty1 sku 1 and cancel. Check split and
	// refund for that sku in citrus and Db both
	@Test(groups = { "Regression",
			"Smoke" }, priority = 0, dataProvider = "SPS_SellerQty1WalletAmountLessThan1", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1. Create online order with seller qty1 sku1. \n2. check the split in citrus than in SPS")
	public void SPS_SellerQty1WalletAmountLessThan1(String orderId, String store_order_id, String ppsId,
			String paymentPlanItemId, String message, String lineId, String releaseMessage, String returnMessage,
			String ppsReturnId, String ppsPlanItemRef) throws SQLException, IOException, InterruptedException {
		log.debug("----------------" + orderId + " : " + ppsId + " : " + paymentPlanItemId);
		sellerHelper.rabbitMqAddTx(message, orderId);
		Thread.sleep(10000);
		// int splits = sellerHelper.getNoOfSplits(store_order_id);
		// log.debug("Number of Splits: " + splits);
		int txResult = sellerHelper.getSplitTxDBCount(orderId);
		int pgResult = sellerHelper.getSplitPgDBCount(orderId);
		log.debug("DB check Tx table splits count: " + txResult + " pg table: " + pgResult);
		// AssertJUnit.assertEquals(1, splits);
		AssertJUnit.assertEquals(1, txResult);
		AssertJUnit.assertEquals(1, pgResult);
		Assert.assertEquals(sellerHelper.getTxAmount(orderId), "799.00");

		sellerHelper.updateOrderReleaseStatus(orderId, "PK");
		sellerHelper.updateOrderLineStatus(orderId, "QD");
		sellerHelper.rabbitMqReleasePKTx(releaseMessage, orderId);
		Thread.sleep(10000);
		// int release = sellerHelper.getNoOfRelease(store_order_id);
		// log.debug("Number of release: " + release);
		int releaseTxResult = sellerHelper.getTxDBStatus1(orderId, "RELEASED");

		log.debug("DB status for release: " + releaseTxResult);
		// Assert.assertEquals(release, 1, "Order Id: " + orderId + " Not
		// available in SPS Transaction detail table");
		Assert.assertEquals(releaseTxResult, 1, "sps transaction_detail status :");

		int releaseTxResult1 = sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED");
		Assert.assertEquals(releaseTxResult1, 1,
				"Order Id: " + orderId + " Not available in SPS Settlement detail table");
		Assert.assertEquals(sellerHelper.getSettlementDetailFundReleaseAmount(orderId, "RELEASED"), "799.00",
				"Online Release amount:");

		log.debug("Number of releases on SPS Transaction_detail DB: " + releaseTxResult1);
		log.debug("Fund Release Amount :" + sellerHelper.getSettlementDetailFundReleaseAmount(orderId, "RELEASED"));

		SellerPaymentDP.SPS_SellerQty1WalletAmountLessThan1Helper(orderId, store_order_id, ppsPlanItemRef, ppsId,
				ppsReturnId);
		sellerHelper.rabbitMqRefundTx(returnMessage, ppsReturnId);

		log.debug("Wait for 10Sec more to get order details in settlement detail table....");
		Thread.sleep(10000);

		int releaseTxResult2 = sellerHelper.getSettlementdetailDBStatusCount(orderId, "REFUNDED");
		Assert.assertEquals(releaseTxResult2, 1,
				"REFUND Order_id:" + orderId + " Not available in SPS Settlement detail table");

		Assert.assertEquals(sellerHelper.getSettlementDetailRefundAmount(orderId, "ONLINE"), "-799.00",
				"Online Refund amount:");

		sellerHelper.deleteOrderRecord(orderId, ppsId, paymentPlanItemId);

	}

	// Create online order with seller qty1 sku 1 and cancel. Check split and
	// refund for that sku in citrus and Db both
	@Test(groups = { "Regression",
			"Smoke" }, priority = 0, dataProvider = "SPS_SellerQty1WalletSkuAmountGreater1", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1. Create online order with seller qty1 sku1. \n2. check the split in citrus than in SPS")
	public void SPS_SellerQty1WalletSkuAmountGreater1(String orderId, String store_order_id, String ppsId,
			String paymentPlanItemId, String message, String lineId)
			throws SQLException, IOException, InterruptedException {
		log.debug("----------------" + orderId + " : " + ppsId + " : " + paymentPlanItemId);
		sellerHelper.rabbitMqAddTx(message, orderId);
		Thread.sleep(10000);
		// int splits = sellerHelper.getNoOfSplits(store_order_id);
		// log.debug("Number of Splits: " + splits);
		int txResult = sellerHelper.getSplitTxDBCount(orderId);
		int pgResult = sellerHelper.getSplitPgDBCount(orderId);
		log.debug("DB check Tx table splits count: " + txResult + " pg table: " + pgResult);
		// AssertJUnit.assertEquals(1, splits);
		AssertJUnit.assertEquals(1, txResult);
		AssertJUnit.assertEquals(1, pgResult);
		Assert.assertEquals(sellerHelper.getTxAmount(orderId), "1.15");
		sellerHelper.deleteOrderRecord(orderId, ppsId, paymentPlanItemId);

	}

	// Create online order with seller qty1 sku 1 and cancel. Check split and
	// refund for that sku in citrus and Db both
	@Test(groups = { "Regression",
			"Smoke" }, priority = 0, dataProvider = "SPS_SellerQty1WalletSkuAmountTotalLessThan1", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1. Create online order with seller qty1 sku1. \n2. check the split in citrus than in SPS")
	public void SPS_SellerQty1WalletSkuAmountTotalLessThan1(String orderId, String orderReleaseId,
			String store_order_id, String ppsId, String paymentPlanItemId, String message, String lineId,
			String releaseMessage, String ppsIdReturn, String paymentPlanItemIdref1, String returnMessage)
			throws SQLException, IOException, InterruptedException {
		log.debug("----------------" + orderId + " : " + ppsId + " : " + paymentPlanItemId);
		sellerHelper.rabbitMqAddTx(message, orderId);
		Thread.sleep(10000);

		int txResult = sellerHelper.getSplitTxDBCount(orderId);
		int pgResult = sellerHelper.getSplitPgDBCount(orderId);
		log.debug("DB check Tx table splits count: " + txResult + " pg table: " + pgResult);

		AssertJUnit.assertEquals(0, txResult);
		AssertJUnit.assertEquals(0, pgResult);
		sellerHelper.updateOrderReleaseStatusAndPackedOn(orderReleaseId, "PK");
		sellerHelper.updateOrderLineStatus(orderId, "QD");
		sellerHelper.rabbitMqReleasePKTx(releaseMessage, orderId);
		SellerPaymentDP.SPS_SellerQty1WalletSkuAmountTotalLessThan1ReturnHelper(orderId, store_order_id,
				paymentPlanItemIdref1, ppsId, ppsIdReturn);
		sellerHelper.rabbitMqRefundTx(returnMessage, ppsIdReturn);

		sellerHelper.deleteOrderRecord(orderId, ppsId, paymentPlanItemId);

	}

	@Test(groups = { "Regression",
			"Sanity" }, priority = 0, dataProvider = "SPS_walletToWalletReturn", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1.Create online order with seller qty1 sku1. \n2.check the split in citrus than in SPS. \n3.Release the sku and check release in both(On order Packed). "
					+ "\n4.Return the sku and check the refund in both")
	public void SPS_walletToWalletReturn(String message, String releaseFundMessage, String returnMessage,
			String orderId, String store_order_id, String OrderReleaseId, String lineId, String ppsId,
			String ppsIdCancellation, String paymentPlanItemId, String paymentPlanItemIdref)
			throws SQLException, IOException, InterruptedException {
		log.debug(orderId + " : " + ppsId + " : " + paymentPlanItemId);
		sellerHelper.rabbitMqAddTx(message, orderId);

		sellerHelper.updateOrderReleaseStatusAndPackedOn(OrderReleaseId, "PK");
		sellerHelper.updateOrderLineStatus(orderId, "QD");
		sellerHelper.rabbitMqReleasePKTx(releaseFundMessage, orderId);

		log.debug("Wait for 10Sec more to get order details in settlement detail table....");
		Thread.sleep(10000);

		int releaseTxResult = sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED");
		Assert.assertEquals(releaseTxResult, 1,
				"Release Order id :" + orderId + " Not available in SPS Settlement detail table");

		SellerPaymentDP.walletToWalletReturnHelper(orderId, store_order_id, OrderReleaseId, paymentPlanItemIdref, ppsId,
				ppsIdCancellation);
		sellerHelper.rabbitMqRefundTx(returnMessage, ppsIdCancellation);

		log.debug("Wait for 10Sec more to get order details in settlement detail table....");
		Thread.sleep(10000);

		int releaseTxResult1 = sellerHelper.getSettlementdetailDBStatusCount(orderId, "REFUNDED");
		Assert.assertEquals(releaseTxResult1, 1,
				"Refund Order id : " + orderId + " Not available in SPS Settlement detail table");

		sellerHelper.deleteOrderRecord(orderId, ppsId, paymentPlanItemId, ppsIdCancellation, paymentPlanItemIdref);
	}

	@Test(groups = { "Regression",
			"Smoke" }, priority = 0, dataProvider = "SPS_WalletQty1Cancel", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1.Create online order with seller qty1 sku1.\n2.check the split in citrus than in SPS. \n3.cancel the order. \n4.check the refund on citurs and sps both")
	public void SPS_WalletQty1Cancel(String orderId, String store_order_id, String ppsId, String ppsIdCancellation,
			String paymentPlanItemId, String paymentPlanItemIdref, String message, String cancellationMessage,
			String lineId) throws SQLException, IOException, InterruptedException {
		log.debug(orderId + " : " + ppsId + " : " + paymentPlanItemId);
		sellerHelper.rabbitMqAddTx(message, orderId);
		Thread.sleep(10000);
		// int splits = sellerHelper.getNoOfSplits(store_order_id);
		// log.debug("Number of Splits: " + splits);
		int txResult = sellerHelper.getSplitTxDBCount(orderId);
		int pgResult = sellerHelper.getSplitPgDBCount(orderId);
		log.debug("DB check Tx table splits count: " + txResult + " pg table: " + pgResult);
		// AssertJUnit.assertEquals(1, splits);
		Assert.assertEquals(txResult, 1, "Add Order Id " + orderId + " not found in Transaction details table");
		Assert.assertEquals(pgResult, 1, "PG settle Order Id " + orderId + " not found in PG Settle details table");
		log.debug(cancellationMessage);
		sellerHelper.rabbitMqRefundTx(cancellationMessage, ppsIdCancellation);
		Thread.sleep(10000);
		// int refund = sellerHelper.getNoOfRefund(store_order_id);
		// log.debug("Number of Refund: " + refund);
		int refundTxResult = sellerHelper.getTxDBStatus1(orderId, "REFUNDED");
		log.debug("DB status for refund: " + refundTxResult);
		// AssertJUnit.assertEquals(1, refund);
		Assert.assertEquals(refundTxResult, 1,
				"Refund Order Id " + orderId + " not found in Transaction details table");
		sellerHelper.deleteOrderRecord(orderId, ppsId, paymentPlanItemId, ppsIdCancellation, paymentPlanItemIdref);
	}

	@Test(groups = { "Regression",
			"Smoke" }, priority = 0, dataProvider = "SPS_WalletQty1WithShippingChrgsPKOrderCancel", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1.Create online order with seller qty1 sku1.\n2.check the split in citrus than in SPS. \n3.cancel the order. \n4.check the refund on citurs and sps both")
	public void SPS_WalletQty1WithShippingChrgsPKOrderCancel(String orderId, String orderReleaseId,
			String store_order_id, String packedMessage, String ppsId, String ppsIdCancellation,
			String paymentPlanItemId, String paymentPlanItemIdref, String paymentPlanItemIdshipCancel,
			String paymentPlanExecutionStatus_id, String message, String cancellationMessage, String lineId)
			throws Exception {
		log.debug(orderId + " : " + ppsId + " : " + paymentPlanItemId);
		sellerHelper.rabbitMqAddTx(message, orderId);
		Thread.sleep(10000);
		// int splits = sellerHelper.getNoOfSplits(store_order_id);
		// log.debug("Number of Splits: " + splits);
		int txResult = sellerHelper.getSplitTxDBCount(orderId);
		int pgResult = sellerHelper.getSplitPgDBCount(orderId);
		log.debug("DB check Tx table splits count: " + txResult + " pg table: " + pgResult);
		// AssertJUnit.assertEquals(1, splits);
		Assert.assertEquals(txResult, 2, "Add Order Id " + orderId + " not found in Transaction details table");
		Assert.assertEquals(pgResult, 1, "PG settle Order Id " + orderId + " not found in PG Settle details table");

		sellerHelper.updateOrderReleaseStatusAndPackedOn(orderReleaseId, "PK");
		sellerHelper.updateOrderLineStatus(orderId, "QD");
		sellerHelper.rabbitMqReleasePKTx(packedMessage, orderId);
		Thread.sleep(10000);

		int releaseSettlementDBResult = sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED");
		Assert.assertEquals(releaseSettlementDBResult, 2,
				"Release Order Id: " + orderId + " Not available in SPS Settlement detail table");

		log.debug(cancellationMessage);
		SellerPaymentDP.SPS_WalletQty1WithShippingChrgsPKOrderCancel_Helper(orderId, ppsId, orderReleaseId,
				store_order_id, ppsIdCancellation, paymentPlanItemIdref, paymentPlanItemIdshipCancel,
				paymentPlanExecutionStatus_id);
		sellerHelper.rabbitMqRefundTx(cancellationMessage, ppsIdCancellation);
		Thread.sleep(10000);
		// int refund = sellerHelper.getNoOfRefund(store_order_id);
		// log.debug("Number of Refund: " + refund);
		int refundTxResult = sellerHelper.getTxDBStatus1(orderId, "REFUNDED");
		log.debug("DB status for refund: " + refundTxResult);
		// AssertJUnit.assertEquals(1, refund);
		Assert.assertEquals(refundTxResult, 2,
				"Refund Order Id " + orderId + " not found in Transaction details table");

		Assert.assertEquals(sellerHelper.getSettlementdetailDBStatusCount(orderId, "REFUNDED"), 2,
				"Release Order Id: " + orderId + " Not available in SPS Settlement detail table");

		sellerHelper.deleteOrderRecord(orderId, ppsId, paymentPlanItemId, ppsIdCancellation, paymentPlanItemIdref);
	}

	@Test(groups = { "Regression",
			"Smoke" }, priority = 0, dataProvider = "SPS_WalletQty1WithShippingChrgsPKOrderCancelWithoutorderId", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1.Create online order with seller qty1 sku1.\n2.check the split in citrus than in SPS. \n3.cancel the order. \n4.check the refund on citurs and sps both")
	public void SPS_WalletQty1WithShippingChrgsPKOrderCancelWithoutorderId(String orderId, String orderReleaseId,
			String store_order_id, String packedMessage, String ppsId, String ppsIdCancellation,
			String paymentPlanItemId, String paymentPlanItemIdref, String paymentPlanItemIdshipCancel,
			String paymentPlanExecutionStatus_id, String message, String cancellationMessage, String lineId)
			throws Exception {
		log.debug(orderId + " : " + ppsId + " : " + paymentPlanItemId);
		sellerHelper.rabbitMqAddTx(message, orderId);
		Thread.sleep(10000);
		// int splits = sellerHelper.getNoOfSplits(store_order_id);
		// log.debug("Number of Splits: " + splits);
		int txResult = sellerHelper.getSplitTxDBCount(orderId);
		int pgResult = sellerHelper.getSplitPgDBCount(orderId);
		log.debug("DB check Tx table splits count: " + txResult + " pg table: " + pgResult);
		// AssertJUnit.assertEquals(1, splits);
		Assert.assertEquals(txResult, 2, "Add Order Id " + orderId + " not found in Transaction details table");
		Assert.assertEquals(pgResult, 1, "PG settle Order Id " + orderId + " not found in PG Settle details table");

		// sellerHelper.updateOrderReleaseStatusAndPackedOn(orderReleaseId,
		// "PK");
		// sellerHelper.updateOrderLineStatus(orderId, "QD");
		// sellerHelper.rabbitMqReleasePKTx(packedMessage, orderId);
		// Thread.sleep(10000);
		//
		// int releaseSettlementDBResult =
		// sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED");
		// Assert.assertEquals(releaseSettlementDBResult, 2,
		// "Release Order Id: " + orderId + " Not available in SPS Settlement
		// detail table");

		log.debug(cancellationMessage);
		SellerPaymentDP.SPS_WalletQty1WithShippingChrgsPKOrderCancel_Helper(orderId, ppsId, orderReleaseId,
				store_order_id, ppsIdCancellation, paymentPlanItemIdref, paymentPlanItemIdshipCancel,
				paymentPlanExecutionStatus_id);
		sellerHelper.rabbitMqRefundTx(cancellationMessage, ppsIdCancellation);
		Thread.sleep(10000);
		// int refund = sellerHelper.getNoOfRefund(store_order_id);
		// log.debug("Number of Refund: " + refund);
		int refundTxResult = sellerHelper.getTxDBStatus1(orderId, "REFUNDED");
		log.debug("DB status for refund: " + refundTxResult);
		// AssertJUnit.assertEquals(1, refund);
		Assert.assertEquals(refundTxResult, 2,
				"Refund Order Id " + orderId + " not found in Transaction details table");

		Assert.assertEquals(sellerHelper.getSettlementdetailDBStatusCount(orderId, "REFUNDED"), 2,
				"Release Order Id: " + orderId + " Not available in SPS Settlement detail table");

		sellerHelper.deleteOrderRecord(orderId, ppsId, paymentPlanItemId, ppsIdCancellation, paymentPlanItemIdref);
	}

	@Test(groups = { "Regression",
			"Smoke" }, priority = 0, dataProvider = "SPS_WalletQty1WithShippingChrgsPKReleaseCancel", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1.Create online order with seller qty1 sku1.\n2.check the split in citrus than in SPS. \n3.cancel the order. \n4.check the refund on citurs and sps both")
	public void SPS_WalletQty1WithShippingChrgsPKReleaseCancel(String orderId, String orderReleaseId,
			String store_order_id, String packedMessage, String ppsId, String ppsIdCancellation,
			String paymentPlanItemId, String paymentPlanItemIdref, String message, String cancellationMessage,
			String lineId, String paymentPlanItemIdshipCancel, String paymentPlanExecutionStatus_id) throws Exception {
		log.debug(orderId + " : " + ppsId + " : " + paymentPlanItemId);
		sellerHelper.rabbitMqAddTx(message, orderId);
		Thread.sleep(10000);
		// int splits = sellerHelper.getNoOfSplits(store_order_id);
		// log.debug("Number of Splits: " + splits);
		int txResult = sellerHelper.getSplitTxDBCount(orderId);
		int pgResult = sellerHelper.getSplitPgDBCount(orderId);
		log.debug("DB check Tx table splits count: " + txResult + " pg table: " + pgResult);
		// AssertJUnit.assertEquals(1, splits);
		Assert.assertEquals(txResult, 2, "Add Order Id " + orderId + " not found in Transaction details table");
		Assert.assertEquals(pgResult, 1, "PG settle Order Id " + orderId + " not found in PG Settle details table");

		sellerHelper.updateOrderReleaseStatusAndPackedOn(orderReleaseId, "PK");
		sellerHelper.updateOrderLineStatus(orderId, "QD");
		sellerHelper.rabbitMqReleasePKTx(packedMessage, orderId);
		Thread.sleep(10000);

		int releaseSettlementDBResult = sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED");
		Assert.assertEquals(releaseSettlementDBResult, 2,
				"Release Order Id: " + orderId + " Not available in SPS Settlement detail table");

		log.debug(cancellationMessage);
		SellerPaymentDP.SPS_WalletQty1WithShippingChrgsPKReleaseCancel_Helper(ppsId, orderReleaseId, store_order_id,
				ppsIdCancellation, paymentPlanItemIdref, paymentPlanItemIdshipCancel, paymentPlanExecutionStatus_id);
		sellerHelper.rabbitMqRefundTx(cancellationMessage, ppsIdCancellation);
		Thread.sleep(10000);
		// int refund = sellerHelper.getNoOfRefund(store_order_id);
		// log.debug("Number of Refund: " + refund);
		int refundTxResult = sellerHelper.getTxDBStatus1(orderId, "REFUNDED");
		log.debug("DB status for refund: " + refundTxResult);
		// AssertJUnit.assertEquals(1, refund);
		Assert.assertEquals(refundTxResult, 2,
				"Refund Order Id " + orderId + " not found in Transaction details table");

		Assert.assertEquals(sellerHelper.getSettlementdetailDBStatusCount(orderId, "REFUNDED"), 2,
				"Release Order Id: " + orderId + " Not available in SPS Settlement detail table");

		sellerHelper.deleteOrderRecord(orderId, ppsId, paymentPlanItemId, ppsIdCancellation, paymentPlanItemIdref);
	}

	@Test(groups = {
			"Regression" }, priority = 0, dataProvider = "SPS_VectorMultiQtyCanRelUsingWalletRelease1Return1", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1. Create online order with Vector qty3 sku1. \n2.check the split in citrus than in SPS for all 3qty.\n3.cancel 1qty of the order and check the refund.\n "
					+ "4.release qty1 and check the release in citurs and sps.\n5.check the pg settlement for 3rd split as we release the 1st sku")
	public void SPS_VectorMultiQtyCanRelUsingWalletRelease1Return1(String orderId, String store_order_id,
			String OrderReleaseId, String OrderReleaseId1, String OrderReleaseId2, String ppsId,
			String ppsIdCancellation, String ppsIdReturnId, String paymentPlanItemId, String paymentPlanItemIdref,
			String paymentPlanItemIdref1, String message, String cancellationMessage, String lineId, String lineId1,
			String lineId2, String paymentPlanExecutionStatus_id, String releaseMessage1, String releaseMessage2,
			String paymentPlanItemId1, String paymentPlanItemId2, String returnMessage)
			throws SQLException, IOException, InterruptedException {
		log.debug("---------orderId: " + orderId + " ppsId: " + ppsId + " PymentPlanItemId: " + paymentPlanItemId);
		sellerHelper.rabbitMqAddTx(message, orderId);
		Thread.sleep(10000);
		// int splits = sellerHelper.getNoOfSplits(store_order_id);
		// log.debug("Number of Splits: " + splits);
		int txResult = sellerHelper.getSplitTxDBCount(orderId);
		int pgResult = sellerHelper.getSplitPgDBCount(orderId);
		log.debug("DB check Tx table splits count: " + txResult + " pg table: " + pgResult);
		// AssertJUnit.assertEquals(3, splits);
		AssertJUnit.assertEquals(3, txResult);
		AssertJUnit.assertEquals(1, pgResult);

		SellerPaymentDP.SPS_VectorMultiQtyCanRelHelperUsingWallet(orderId, store_order_id, OrderReleaseId, lineId,
				ppsId, ppsIdCancellation, paymentPlanItemIdref, paymentPlanExecutionStatus_id);
		sellerHelper.rabbitMqRefundTx(cancellationMessage, ppsIdCancellation);
		Thread.sleep(10000);
		// int refund = sellerHelper.getNoOfRefund(store_order_id);
		// log.debug("Number of Refund: " + refund);
		int refundTxResult = sellerHelper.getTxDBStatusCount1(orderId, "3827", "REFUNDED");
		log.debug("DB status for refund: " + refundTxResult);
		// AssertJUnit.assertEquals(1, refund);
		AssertJUnit.assertEquals(1, refundTxResult);

		sellerHelper.updateOrderLineStatusByLineId(lineId1, "QD");
		sellerHelper.updateOrderLineStatusByLineId(lineId2, "QD");
		sellerHelper.updateOrderReleaseStatusAndPackedOn(OrderReleaseId1, "PK");
		sellerHelper.updateOrderReleaseStatusAndPackedOn(OrderReleaseId2, "PK");
		sellerHelper.rabbitMqReleasePKTx(releaseMessage1, orderId);
		Thread.sleep(10000);
		// int release = sellerHelper.getNoOfRelease(store_order_id);
		// log.debug("Number of release: " + release);
		int releaseTxResult = sellerHelper.getTxDBStatusCount(orderId, "RELEASED");
		log.debug("Number of release on DB: " + releaseTxResult);
		// AssertJUnit.assertEquals(2, release);
		AssertJUnit.assertEquals(1, releaseTxResult);

		int releaseSettlementDBResult = sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED");
		Assert.assertEquals(releaseSettlementDBResult, 1,
				"Release Order Id: " + orderId + " Not available in SPS Settlement detail table");

		log.debug("Number of releases on SPS Transaction_detail DB: " + releaseSettlementDBResult);
		log.debug("Fund Release Amount :" + sellerHelper.getSettlementDetailFundReleaseAmount(orderId, "RELEASED"));

		sellerHelper.rabbitMqReleasePKTx(releaseMessage2, orderId);
		Thread.sleep(10000);
		// int release = sellerHelper.getNoOfRelease(store_order_id);
		// log.debug("Number of release: " + release);
		int releaseTxResult1 = sellerHelper.getTxDBStatusCount(orderId, "RELEASED");
		log.debug("Number of release on DB: " + releaseTxResult);
		// AssertJUnit.assertEquals(2, release);
		AssertJUnit.assertEquals(2, releaseTxResult1);

		int releaseSettlementDBResult1 = sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED");
		Assert.assertEquals(releaseSettlementDBResult1, 2,
				"Release Order Id: " + orderId + " Not available in SPS Settlement detail table");

		log.debug("Number of releases on SPS Transaction_detail DB: " + releaseTxResult1);
		log.debug("Fund Release Amount :" + sellerHelper.getSettlementDetailFundReleaseAmount(orderId, "RELEASED"));

		SellerPaymentDP.create_Online_Wallet_OrderWithSellerMultipleQty2ReleaseHelper(orderId, store_order_id,
				paymentPlanItemIdref1, ppsId, ppsIdReturnId);
		sellerHelper.rabbitMqRefundTx(returnMessage, ppsIdReturnId);

		log.debug("Wait for 10Sec more to get order details in settlement detail table....");
		Thread.sleep(10000);

		int refundSettlementDBResult = sellerHelper.getSettlementdetailDBStatusCount(orderId, "REFUNDED");
		Assert.assertEquals(refundSettlementDBResult, 1,
				"REFUND Order_id:" + orderId + " Not available in SPS Settlement detail table");
		// Assert.assertEquals(sellerHelper.getSettlementDetailRefundAmount(orderId,"CASH_ON_DELIVERY"),"-1399.00"
		// ,"COD Refund amount:");
		Assert.assertEquals(sellerHelper.getSettlementDetailRefundAmount(orderId, "ONLINE"), "-2399.00",
				"Online Refund amount:");

		sellerHelper.deleteFromPaymentPlanItemInstrument(paymentPlanItemId);
		sellerHelper.deleteOrderRecord(orderId, ppsId, paymentPlanItemId);
	}

	@Test(groups = { "Regression",
			"Smoke" }, priority = 0, dataProvider = "SPS_VectorQty1CB", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1. Create online order with seller qty1 sku1. \n2. check the split in citrus than in SPS")
	public void SPS_SellerQty1ValidateCBAmount(String orderId, String store_order_id, String ppsId,
			String paymentPlanItemId, String message, String lineId)
			throws SQLException, IOException, InterruptedException {
		log.debug("----------------" + orderId + " : " + ppsId + " : " + paymentPlanItemId);
		sellerHelper.rabbitMqAddTx(message, orderId);
		Thread.sleep(10000);
		// int splits = sellerHelper.getNoOfSplits(store_order_id);
		// log.debug("Number of Splits: " + splits);
		int txResult = sellerHelper.getSplitTxDBCount(orderId);
		int pgResult = sellerHelper.getSplitPgDBCount(orderId);
		log.debug("DB check Tx table splits count: " + txResult + " pg table: " + pgResult);
		// AssertJUnit.assertEquals(1, splits);
		AssertJUnit.assertEquals(1, txResult);
		AssertJUnit.assertEquals(1, pgResult);
		Assert.assertEquals(sellerHelper.getTxAmount(orderId), "299.00");
		sellerHelper.deleteOrderRecord(orderId, ppsId, paymentPlanItemId);

	}

	@Test(groups = {
			"Regression" }, priority = 0, dataProvider = "SPS_VectorSellerMultiQtyCBTxAmountValidation", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1. Create online order with Vector qty2 seller qty1 + Cash back(CB Applicable only on vector). \n2. check the split in citrus than in SPS. "
					+ "\n3.Check that the total tx amount is equals to all splits")
	public void SPS_VectorSellerMultiQtyCBTxAmountValidation(String orderId, String store_order_id, String ppsId,
			String paymentPlanItemId, String message, String lineId, String paymentPlanItemId1)
			throws SQLException, IOException, InterruptedException {
		log.debug("----------------" + orderId + " : " + ppsId + " : " + paymentPlanItemId);
		sellerHelper.rabbitMqAddTx(message, orderId);
		// java.util.List txAmountAndSplits =
		// sellerHelper.getNoOfSplitsAndSplitAmountAndTxAmount(store_order_id);
		// log.debug("No of splits, tx amounts and splits: " +
		// txAmountAndSplits);
		// int splits = Integer.parseInt((String) txAmountAndSplits.get(0));
		// int txamount = (int) txAmountAndSplits.get(1);
		int splitsSum = 0;
		// for (int i = 2; i < txAmountAndSplits.size(); i++) {
		// splitsSum = splitsSum + (int) txAmountAndSplits.get(i);
		// }
		// // log.debug("Number of Splits: " + splits);
		// log.debug("Tx amount: " + txamount + ", splitSum: " + splitsSum);
		int txResult = sellerHelper.getSplitTxDBCount(orderId);
		int pgResult = sellerHelper.getSplitPgDBCount(orderId);
		log.debug("DB check Tx table splits count: " + txResult + " pg table: " + pgResult);
		// AssertJUnit.assertEquals(3, splits);
		AssertJUnit.assertEquals(3, txResult);
		AssertJUnit.assertEquals(1, pgResult);
		// AssertJUnit.assertEquals(txamount, splitsSum);

		sellerHelper.deleteFromPaymentPlanItemInstrument(paymentPlanItemId1);
		sellerHelper.deleteOrderRecord(orderId, ppsId, paymentPlanItemId);

	}

	@Test(groups = { "Regression",
			"Smoke" }, priority = 0, dataProvider = "SPS_SellerQty1LoyaltyPointReleaseReturn", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1. Create online order with seller qty1 sku1. \n2. check the split in citrus than in SPS")
	public void SPS_SellerQty1LoyaltyPointReleaseReturn(String orderId, String store_order_id, String ppsId,
			String paymentPlanItemId, String message, String lineId, String releaseFundMessage,
			String paymentPlanItemIdref, String ppsIdCancellation, String returnMessage)
			throws SQLException, IOException, InterruptedException {
		log.debug("----------------" + orderId + " : " + ppsId + " : " + paymentPlanItemId);
		sellerHelper.rabbitMqAddTx(message, orderId);
		Thread.sleep(10000);
		// int splits = sellerHelper.getNoOfSplits(store_order_id);
		// log.debug("Number of Splits: " + splits);
		int txResult = sellerHelper.getSplitTxDBCount(orderId);
		int pgResult = sellerHelper.getSplitPgDBCount(orderId);
		log.debug("DB check Tx table splits count: " + txResult + " pg table: " + pgResult);
		// AssertJUnit.assertEquals(1, splits);
		AssertJUnit.assertEquals(1, txResult);
		AssertJUnit.assertEquals(1, pgResult);
		Assert.assertEquals(sellerHelper.getTxAmount(orderId), "799.00");

		sellerHelper.updateOrderReleaseStatus(orderId, "PK");
		sellerHelper.updateOrderLineStatus(orderId, "QD");
		sellerHelper.rabbitMqReleasePKTx(releaseFundMessage, orderId);
		Thread.sleep(10000);
		// int release = sellerHelper.getNoOfRelease(store_order_id);
		// log.debug("Number of release: " + release);
		int releaseTxResult = sellerHelper.getTxDBStatus1(orderId, "RELEASED");

		log.debug("DB status for release: " + releaseTxResult);
		// Assert.assertEquals(release, 1, "Order Id: " + orderId + " Not
		// available in SPS Transaction detail table");
		Assert.assertEquals(releaseTxResult, 1, "sps transaction_detail status :");

		int releaseTxResult1 = sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED");
		Assert.assertEquals(releaseTxResult1, 1,
				"Order Id: " + orderId + " Not available in SPS Settlement detail table");
		Assert.assertEquals(sellerHelper.getSettlementDetailFundReleaseAmount(orderId, "RELEASED"), "799.00",
				"Online Release amount:");

		log.debug("Number of releases on SPS Transaction_detail DB: " + releaseTxResult1);
		log.debug("Fund Release Amount :" + sellerHelper.getSettlementDetailFundReleaseAmount(orderId, "RELEASED"));

		SellerPaymentDP.create_Online_LP_OrderWithSellerQty1ReleaseHelper(orderId, store_order_id, paymentPlanItemIdref,
				ppsId, ppsIdCancellation);
		sellerHelper.rabbitMqRefundTx(returnMessage, ppsIdCancellation);

		log.debug("Wait for 10Sec more to get order details in settlement detail table....");
		Thread.sleep(10000);

		int releaseTxResult2 = sellerHelper.getSettlementdetailDBStatusCount(orderId, "REFUNDED");
		Assert.assertEquals(releaseTxResult2, 1,
				"REFUND Order_id:" + orderId + " Not available in SPS Settlement detail table");
		// Assert.assertEquals(sellerHelper.getSettlementDetailRefundAmount(orderId,"CASH_ON_DELIVERY"),"-1399.00"
		// ,"COD Refund amount:");
		Assert.assertEquals(sellerHelper.getSettlementDetailRefundAmount(orderId, "ONLINE"), "-799.00",
				"Online Refund amount:");

		sellerHelper.deleteFromPaymentPlanItemInstrument(paymentPlanItemId);
		sellerHelper.deleteOrderRecord(orderId, ppsId, paymentPlanItemId);

	}

	@Test(groups = {
			"Regression" }, priority = 0, dataProvider = "SPS_VectorQty1LP", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1. Create online order with Vector qty1 sku1 and apply Loyalty point . \n2.check that only online amount is in split in citrus & SPS. \n3.validate the fee amount")
	public void SPS_VectorQty1LP(String orderId, String store_order_id, String ppsId, String paymentPlanItemId,
			String message, String lineId) throws SQLException, IOException, InterruptedException {
		log.debug(orderId + " : " + ppsId + " : " + paymentPlanItemId);
		sellerHelper.rabbitMqAddTx(message, orderId);
		Thread.sleep(10000);
		// java.util.List feeAmountAndSplits =
		// sellerHelper.getNoOfSplitsAndFeeAmount(store_order_id);
		// log.debug("Split and fee amount: " + feeAmountAndSplits);
		BigDecimal margin = sellerHelper.getSellerMargin(orderId);
		Double x = 2399.00 * (margin.doubleValue() / 100);
		double marginCalc = (Math.round(x * 100.0) / 100.0) - 0.01;
		// log.debug("Number of Splits: " + feeAmountAndSplits.get(0));
		// String feeAmount = "" + feeAmountAndSplits.get(1);
		// log.debug("fee amount: " + feeAmount);
		log.debug("margin calculation: " + marginCalc);
		int txResult = sellerHelper.getSplitTxDBCount(orderId);
		int pgResult = sellerHelper.getSplitPgDBCount(orderId);
		log.debug("DB check Tx table splits count: " + txResult + " pg table: " + pgResult);
		// AssertJUnit.assertEquals(1, Integer.parseInt((String)
		// feeAmountAndSplits.get(0)));
		AssertJUnit.assertEquals(1, txResult);
		AssertJUnit.assertEquals(1, pgResult);
		// AssertJUnit.assertEquals(marginCalc, feeAmountAndSplits.get(1));
		sellerHelper.deleteOrderRecord(orderId, ppsId, paymentPlanItemId);
	}

	@Test(groups = { "Regression",
			"Smoke" }, priority = 0, dataProvider = "SPS_VectorQty1LP", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1. Create online order with seller qty1 sku1. \n2. check the split in citrus than in SPS")
	public void SPS_SellerQty1ValidateLPAmount(String orderId, String store_order_id, String ppsId,
			String paymentPlanItemId, String message, String lineId)
			throws SQLException, IOException, InterruptedException {
		log.debug("----------------" + orderId + " : " + ppsId + " : " + paymentPlanItemId);
		sellerHelper.rabbitMqAddTx(message, orderId);
		Thread.sleep(10000);
		// int splits = sellerHelper.getNoOfSplits(store_order_id);
		// log.debug("Number of Splits: " + splits);
		int txResult = sellerHelper.getSplitTxDBCount(orderId);
		int pgResult = sellerHelper.getSplitPgDBCount(orderId);
		log.debug("DB check Tx table splits count: " + txResult + " pg table: " + pgResult);
		// AssertJUnit.assertEquals(1, splits);
		AssertJUnit.assertEquals(1, txResult);
		AssertJUnit.assertEquals(1, pgResult);
		Assert.assertEquals(sellerHelper.getTxAmount(orderId), "2399.00");
		sellerHelper.deleteOrderRecord(orderId, ppsId, paymentPlanItemId);

	}

	@Test(groups = {
			"Regression" }, priority = 0, dataProvider = "SPS_VectorQty1GC", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1.Create online order with Vector qty1 sku1 and apply Gift card . \n2.check that only online amount is in split in citrus & SPS. \n3.validate the fee amount")
	public void SPS_VectorQty1GC(String orderId, String store_order_id, String ppsId, String paymentPlanItemId,
			String message, String lineId) throws SQLException, IOException, InterruptedException {
		log.debug(orderId + " : " + ppsId + " : " + paymentPlanItemId);
		sellerHelper.rabbitMqAddTx(message, orderId);
		Thread.sleep(10000);
		// java.util.List feeAmountAndSplits =
		// sellerHelper.getNoOfSplitsAndFeeAmount(store_order_id);
		// log.debug("Split and fee amount: " + feeAmountAndSplits);
		BigDecimal margin = sellerHelper.getSellerMargin(orderId);
		Double x = 2399.00 * (margin.doubleValue() / 100);
		double marginCalc = (Math.round(x * 100.0) / 100.0) - 0.01;
		;
		// log.debug("Number of Splits: " + feeAmountAndSplits.get(0));
		// String feeAmount = "" + feeAmountAndSplits.get(1);
		// log.debug("fee amount: " + feeAmount);
		log.debug("margin calculation: " + marginCalc);
		int txResult = sellerHelper.getSplitTxDBCount(orderId);
		int pgResult = sellerHelper.getSplitPgDBCount(orderId);
		log.debug("DB check Tx table splits count: " + txResult + " pg table: " + pgResult);
		// AssertJUnit.assertEquals(1, Integer.parseInt((String)
		// feeAmountAndSplits.get(0)));
		AssertJUnit.assertEquals(1, txResult);
		AssertJUnit.assertEquals(1, pgResult);
		// AssertJUnit.assertEquals(marginCalc, feeAmountAndSplits.get(1));
		sellerHelper.deleteOrderRecord(orderId, ppsId, paymentPlanItemId);
	}

	@Test(groups = { "Regression",
			"Smoke" }, priority = 0, dataProvider = "SPS_VectorQty1GC", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1. Create online order with seller qty1 sku1. \n2. check the split in citrus than in SPS")
	public void SPS_SellerQty1ValidateGCAmount(String orderId, String store_order_id, String ppsId,
			String paymentPlanItemId, String message, String lineId)
			throws SQLException, IOException, InterruptedException {
		log.debug("----------------" + orderId + " : " + ppsId + " : " + paymentPlanItemId);
		sellerHelper.rabbitMqAddTx(message, orderId);
		Thread.sleep(10000);
		// int splits = sellerHelper.getNoOfSplits(store_order_id);
		// log.debug("Number of Splits: " + splits);
		int txResult = sellerHelper.getSplitTxDBCount(orderId);
		int pgResult = sellerHelper.getSplitPgDBCount(orderId);
		log.debug("DB check Tx table splits count: " + txResult + " pg table: " + pgResult);
		// AssertJUnit.assertEquals(1, splits);
		AssertJUnit.assertEquals(1, txResult);
		AssertJUnit.assertEquals(1, pgResult);
		Assert.assertEquals(sellerHelper.getTxAmount(orderId), "2399.00");
		sellerHelper.deleteOrderRecord(orderId, ppsId, paymentPlanItemId);

	}

	// Create online order with seller qty1 sku 1 With Partial Coupon. Check
	// split and fee amount that only the online tx amount is getting captured
	// in sps
	@Test(groups = {
			"Regression" }, priority = 0, dataProvider = "SPS_VectorQty1Coupon", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1.Create online order with Vector qty1 sku1 and apply Coupon . \n2.check that only online amount is in split in citrus & SPS. \n3.validate the fee amount")
	public void SPS_VectorQty1Coupon(String orderId, String store_order_id, String ppsId, String paymentPlanItemId,
			String message, String lineId) throws SQLException, IOException, InterruptedException {
		log.debug(orderId + " : " + ppsId + " : " + paymentPlanItemId);
		sellerHelper.rabbitMqAddTx(message, orderId);
		Thread.sleep(10000);
		// java.util.List feeAmountAndSplits =
		// sellerHelper.getNoOfSplitsAndFeeAmount(store_order_id);
		// log.debug("Split and fee amount: " + feeAmountAndSplits);
		BigDecimal margin = sellerHelper.getSellerMargin(orderId);
		Double x = 2000.00 * (margin.doubleValue() / 100);
		double marginCalc = (Math.round(x * 100.0) / 100.0);
		// log.debug("Number of Splits: " + feeAmountAndSplits.get(0));
		// String fA = "" + feeAmountAndSplits.get(1);
		// double feeAmount = Double.parseDouble(fA);

		// log.debug("fee amount: " + feeAmount);
		log.debug("margin calculation: " + marginCalc);
		int txResult = sellerHelper.getSplitTxDBCount(orderId);
		int pgResult = sellerHelper.getSplitPgDBCount(orderId);
		log.debug("DB check Tx table splits count: " + txResult + " pg table: " + pgResult);
		// AssertJUnit.assertEquals(1, Integer.parseInt((String)
		// feeAmountAndSplits.get(0)));
		AssertJUnit.assertEquals(1, txResult);
		AssertJUnit.assertEquals(1, pgResult);
		// AssertJUnit.assertEquals(marginCalc, feeAmount);
		sellerHelper.deleteOrderRecord(orderId, ppsId, paymentPlanItemId);
	}

	@Test(groups = {
			"Regression" }, priority = 0, dataProvider = "SPS_VectorQty1PriceOverRide", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1.Create online order with JIT qty1 sku1. \n2.check the split in citrus than in SPS. \n3.mark overwrite price. "
					+ "\n4.check that the fund deducted from split, order amount should remain same on citrus\n5.check split amount deducted on DB\n6.check refund for the override amount")
	public void SPS_VectorQty1PriceOverRide(String orderId, String store_order_id, String ppsId,
			String paymentPlanItemId, String message, String lineId, String refundMessage, String paymentPlanItemIdref,
			String ppsIdrefund, String releaseMessage)
			throws SQLException, IOException, InterruptedException, JAXBException {
		log.debug(orderId + " : " + ppsId + " : " + paymentPlanItemId);
		sellerHelper.rabbitMqAddTx(message, orderId);
		Thread.sleep(10000);
		/*
		 * java.util.List feeAmountAndSplits =
		 * sellerHelper.getNoOfSplitsAndFeeAmountAndTxAmount(orderId); // Call
		 * getNoOfSplit function which will check number of splits happened on
		 * Citrus and will return the value log.debug("Split and txamount & fee
		 * amount: "+feeAmountAndSplits); BigDecimal margin =
		 * sellerHelper.getSellerMargin(orderId); Double x =
		 * 799.00*(margin.doubleValue()/100); double marginCalc = Math.round(x *
		 * 100.0) / 100.0; log.debug("Number of Splits: "
		 * +feeAmountAndSplits.get(0)); String txAmount =
		 * ""+feeAmountAndSplits.get(1); log.debug("Transection amount: "
		 * +txAmount); String feeAmount = ""+feeAmountAndSplits.get(2);
		 * log.debug("fee amount: "+feeAmount); log.debug("margin calculation: "
		 * +marginCalc); int txResult = sellerHelper.getSplitTxDBCount(orderId);
		 * int pgResult = sellerHelper.getSplitPgDBCount(orderId); log.debug(
		 * "DB check Tx table splits count: "+txResult+" pg table: "+pgResult);
		 * AssertJUnit.assertEquals(1, Integer.parseInt((String)
		 * feeAmountAndSplits.get(0)));
		 */
		/*
		 * AssertJUnit.assertEquals(1, txResult); AssertJUnit.assertEquals(1,
		 * pgResult); Assert.assertEquals(feeAmount, marginCalc,
		 * "Margin calculation : ");
		 */
		sellerHelper.rabbitMqRefundTx(refundMessage, ppsIdrefund);
		Thread.sleep(10000);

		sellerHelper.rabbitMqReleasePKTx(releaseMessage, orderId);
		Thread.sleep(10000);
		// java.util.List getTxamountandfee =
		// sellerHelper.getNoOfSplitsAndFeeAmountAndTxAmount(store_order_id);
		// log.debug("Split and txamount & fee amount: " + getTxamountandfee);
		// Double x1 = 699.00*(margin.doubleValue()/100);
		// double marginCalc1 = Math.round(x1 * 100.0) / 100.0;
		// String txAmount1 = ""+getTxamountandfee.get(1);
		// log.debug("Transection amount: "+txAmount1);
		// String feeAmount1 = ""+getTxamountandfee.get(2);
		// log.debug("fee amount: "+feeAmount1);
		// log.debug("margin calculation: "+marginCalc1);
		// AssertJUnit.assertEquals(799, getTxamountandfee.get(1));
		// AssertJUnit.assertEquals(marginCalc1, getTxamountandfee.get(2));

		// int noOfRefund = sellerHelper.getNoOfRefund(store_order_id);
		// log.debug("Number of Refund: " + noOfRefund);
		String splitAmountInDb = sellerHelper.getSettlementDetailFundReleaseAmount(orderId);
		log.debug("Split amount after PriceOverride: " + splitAmountInDb);
		// AssertJUnit.assertEquals(1, noOfRefund);
		Assert.assertEquals(splitAmountInDb, "699.00",
				"Refund Order_id:" + orderId + " Amount misnatched in SPS Settlement detail table");

		sellerHelper.deleteFromOrdersLineAdditionalInfo(lineId);
		sellerHelper.deleteOrderRecord(orderId, ppsId, paymentPlanItemId, ppsIdrefund, paymentPlanItemIdref);
	}

	@Test(groups = {
			"Regression" }, priority = 0, dataProvider = "SPS_VectorQty1VatRefund", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1.Create online order with JIT qty1 sku1. \n2.check the split in citrus than in SPS. \n3.mark overwrite price. "
					+ "\n4.check that the fund deducted from split, order amount should remain same on citrus\n5.check split amount deducted on DB\n6.check refund for the override amount")
	public void SPS_VectorQty1VatRefund(String orderId, String store_order_id, String ppsId, String paymentPlanItemId,
			String message, String lineId, String refundMessage, String paymentPlanItemIdref, String ppsIdrefund,
			String releaseMessage, String ppsIdreturn, String paymentPlanItemIdret, String returnMessage)
			throws Exception {
		log.debug(orderId + " : " + ppsId + " : " + paymentPlanItemId);
		sellerHelper.rabbitMqAddTx(message, orderId);
		Thread.sleep(10000);

		sellerHelper.rabbitMqRefundTx(refundMessage, ppsIdrefund);

		sellerHelper.rabbitMqReleasePKTx(releaseMessage, orderId);
		Thread.sleep(10000);
		int releaseTxResult = sellerHelper.getTxDBStatusCount(orderId, "RELEASED");
		log.debug("Number of release on DB: " + releaseTxResult);
		Assert.assertEquals(releaseTxResult, 1, "Order not found in Transaction table");

		int releaseSettlementResult = sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED");
		Assert.assertEquals(releaseSettlementResult, 1,
				"Release Order_id:" + orderId + " Not available in SPS Settlement detail table");
		Assert.assertEquals(sellerHelper.getSettlementDetailFundReleaseAmount(orderId), "699.00",
				"Refund Order_id:" + orderId + " Amount misnatched in SPS Settlement detail table");

		SellerPaymentDP.SPS_VectorQty1VatRefundReturnHelper(orderId, store_order_id, ppsId, ppsIdreturn,
				paymentPlanItemIdret);
		sellerHelper.rabbitMqRefundTx(returnMessage, ppsIdreturn);
		Thread.sleep(10000);
		Assert.assertEquals(sellerHelper.getTxDBStatusCount(orderId, "REFUNDED"), 1,
				"Release Order_id:" + orderId + " Not available in SPS Settlement detail table");

		sellerHelper.deleteFromOrdersLineAdditionalInfo(lineId);
		sellerHelper.deleteOrderRecord(orderId, ppsId, paymentPlanItemId, ppsIdrefund, paymentPlanItemIdref);
	}

	@Test(groups = {
			"Regression" }, priority = 0, dataProvider = "SPS_VectorQty1VatAdjustmentCancelRefund", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1.Create online order with JIT qty1 sku1. \n2.check the split in citrus than in SPS. \n3.mark overwrite price. "
					+ "\n4.check that the fund deducted from split, order amount should remain same on citrus\n5.check split amount deducted on DB\n6.check refund for the override amount")
	public void SPS_VectorQty1VatAdjustmentCancelRefund(String orderId, String store_order_id, String ppsId,
			String paymentPlanItemId, String message, String lineId, String refundMessage, String paymentPlanItemIdref,
			String ppsIdrefund, String releaseMessage, String ppsIdLineCancellation,
			String lineCancellationRefundMessage, String paymentPlanExecutionStatus_id,
			String paymentPlanItemIdLineCancellation, String releaseMessage1, String releaseMessage2)
			throws SQLException, IOException, InterruptedException, JAXBException {
		log.debug(orderId + " : " + ppsId + " : " + paymentPlanItemId);
		sellerHelper.rabbitMqAddTx(message, orderId);
		Thread.sleep(10000);
		sellerHelper.rabbitMqRefundTx(refundMessage, ppsIdrefund);
		Thread.sleep(10000);

		sellerHelper.rabbitMqReleasePKTx(releaseMessage, orderId);
		sellerDP.SPS_VectorQty1VatAdjustmentCancelRefundHelper(orderId, store_order_id, orderId, lineId, ppsId,
				ppsIdLineCancellation, paymentPlanItemIdLineCancellation, paymentPlanExecutionStatus_id);

		sellerHelper.rabbitMqRefundTx(lineCancellationRefundMessage, ppsIdLineCancellation);
		Thread.sleep(10000);
		Assert.assertEquals(sellerHelper.getTxDBStatusCount(orderId, "REFUNDED"), 1,
				"Order not found in Transaction table");
		sellerHelper.rabbitMqReleasePKTx(releaseMessage, orderId);
		Thread.sleep(10000);
		int releaseTxResult = sellerHelper.getTxDBStatusCount(orderId, "RELEASED");
		log.debug("Number of release on DB: " + releaseTxResult);
		Assert.assertEquals(releaseTxResult, 1, "Order not found in Transaction table");

		int releaseSettlementResult = sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED");
		Assert.assertEquals(releaseSettlementResult, 1,
				"Release Order_id:" + orderId + " Not available in SPS Settlement detail table");

		sellerHelper.rabbitMqReleasePKTx(releaseMessage1, orderId);
		Thread.sleep(10000);

		int releaseTxResult1 = sellerHelper.getTxDBStatusCount(orderId, "RELEASED");
		log.debug("Number of release on DB: " + releaseTxResult);
		Assert.assertEquals(releaseTxResult1, 2, "Order not found in Transaction table");

		int releaseSettlementResult1 = sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED");
		Assert.assertEquals(releaseSettlementResult1, 2,
				"Release Order_id:" + orderId + " Not available in SPS Settlement detail table");

		sellerHelper.rabbitMqReleasePKTx(releaseMessage2, orderId);
		Thread.sleep(10000);

		int releaseTxResult2 = sellerHelper.getTxDBStatusCount(orderId, "RELEASED");
		log.debug("Number of release on DB: " + releaseTxResult);
		Assert.assertEquals(releaseTxResult2, 2, "Order not found in Transaction table");

		int releaseSettlementResult2 = sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED");
		Assert.assertEquals(releaseSettlementResult2, 2,
				"Release Order_id:" + orderId + " Not available in SPS Settlement detail table");

		sellerHelper.deleteFromOrdersLineAdditionalInfo(lineId);
		sellerHelper.deleteOrderRecord(orderId, ppsId, paymentPlanItemId, ppsIdrefund, paymentPlanItemIdref);
	}

	// Prod issue
	@Test(groups = {
			"Regression" }, priority = 0, dataProvider = "SPS_VectorQty2VatAdjustmentCancel1Release1", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1.Create online order with JIT qty1 sku1. \n2.check the split in citrus than in SPS. \n3.mark overwrite price. "
					+ "\n4.check that the fund deducted from split, order amount should remain same on citrus\n5.check split amount deducted on DB\n6.check refund for the override amount")
	public void SPS_VectorQty2VatAdjustmentCancel1Release1(String orderId, String OrderReleaseId,
			String OrderReleaseId1, String store_order_id, String ppsId, String paymentPlanItemId, String orderMessage,
			String lineId, String lineId1, String lineId2, String lineId3, String refundMessage, String releaseMessage,
			String ppsIdLineCancellation, String lineCancellationRefundMessage, String paymentPlanExecutionStatus_id,
			String paymentPlanItemIdLineCancellation, String releaseMessage1, String ppsIdrefund)
			throws SQLException, IOException, InterruptedException, JAXBException {
		log.debug(orderId + " : " + ppsId + " : " + paymentPlanItemId);
		sellerHelper.rabbitMqAddTx(orderMessage, orderId);
		Thread.sleep(10000);

		sellerDP.SPS_VectorQty2VatAdjustmentCancel1Release1Helper(orderId, store_order_id, OrderReleaseId, lineId1,
				ppsId, ppsIdLineCancellation, paymentPlanItemIdLineCancellation, paymentPlanExecutionStatus_id);
		sellerHelper.rabbitMqRefundTx(lineCancellationRefundMessage, ppsIdLineCancellation);
		Thread.sleep(10000);
		Assert.assertEquals(sellerHelper.getTxDBStatusCount(orderId, "REFUNDED"), 1,
				"Cancellation Order " + orderId + " not found in Transaction table");

		sellerHelper.updateOrderReleaseStatusAndPackedOn(OrderReleaseId, "PK");
		sellerHelper.updateOrderLineStatusByLineId(lineId, "QD");
		sellerHelper.rabbitMqReleasePKTx(releaseMessage, orderId);
		Thread.sleep(10000);
		int releaseTxResult = sellerHelper.getTxDBStatusCount(orderId, "RELEASED");
		log.debug("Number of release on DB: " + releaseTxResult);
		Assert.assertEquals(releaseTxResult, 2, "Release Order " + orderId + " not found in Transaction table");

		int releaseSettlementResult = sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED");
		Assert.assertEquals(releaseSettlementResult, 2,
				"Release Order_id:" + orderId + " Not available in SPS Settlement detail table");

		sellerHelper.rabbitMqRefundTx(refundMessage, ppsIdrefund);
		sellerHelper.updateOrderReleaseStatusAndPackedOn(OrderReleaseId1, "PK");
		sellerHelper.updateOrderLineStatusByLineId(lineId3, "QD");
		sellerHelper.rabbitMqReleasePKTx(releaseMessage1, orderId);
		Thread.sleep(10000);
		Assert.assertEquals(sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED"), 3,
				"Release Order_id:" + orderId + " Not available in SPS Settlement detail table");

		sellerHelper.deleteFromOrdersLineAdditionalInfo(lineId);
		sellerHelper.deleteOrderRecord(orderId, ppsId, paymentPlanItemId);
	}

	@Test(groups = {
			"Regression" }, priority = 0, dataProvider = "SPS_VectorQty1VatRefundAndPriceOveride", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1.Create online order with JIT qty1 sku1. \n2.check the split in citrus than in SPS. \n3.mark overwrite price. "
					+ "\n4.check that the fund deducted from split, order amount should remain same on citrus\n5.check split amount deducted on DB\n6.check refund for the override amount")
	public void SPS_VectorQty1VatRefundAndPriceOveride(String orderId, String OrderReleaseId, String store_order_id,
			String ppsId, String paymentPlanItemId, String message, String lineId, String refundMessage,
			String paymentPlanItemIdref, String ppsIdrefund, String releaseMessage, String refundMessageVatAdjustment,
			String ppsIdrefundVatRefund) throws SQLException, IOException, InterruptedException, JAXBException {
		log.debug(orderId + " : " + ppsId + " : " + paymentPlanItemId);
		sellerHelper.rabbitMqAddTx(message, orderId);
		sellerHelper.rabbitMqRefundTx(refundMessage, ppsIdrefund);
		sellerHelper.rabbitMqRefundTx(refundMessageVatAdjustment, ppsIdrefundVatRefund);
		sellerHelper.updateOrderReleaseStatusAndPackedOn(OrderReleaseId, "PK");
		sellerHelper.rabbitMqReleasePKTx(releaseMessage, orderId);
		Thread.sleep(10000);
		int releaseTxResult = sellerHelper.getTxDBStatusCount(orderId, "RELEASED");
		log.debug("Number of release on DB: " + releaseTxResult);
		Assert.assertEquals(releaseTxResult, 1, "Order not found in Transaction table");

		int releaseSettlementResult = sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED");
		Assert.assertEquals(releaseSettlementResult, 1,
				"Release Order_id:" + orderId + " Not available in SPS Settlement detail table");
		String fundReleaseAmount = sellerHelper.getSettlementDetailFundReleaseAmount(orderId);
		Assert.assertEquals(fundReleaseAmount, "599.00",
				"Fund Release Amount Mismatch for the Order_id:" + orderId + "  in SPS Settlement detail table");

		sellerHelper.deleteFromOrdersLineAdditionalInfo(lineId);
		sellerHelper.deleteOrderRecord(orderId, ppsId, paymentPlanItemId, ppsIdrefund, paymentPlanItemIdref);
	}

	@Test(groups = {
			"Regression" }, priority = 0, dataProvider = "SPS_VectorSellerMultiQtyPriceOverRide", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1.Create online order with Vector and seller multiqty. \n2.check the split in citrus than in SPS. \n3. Check the split amount and tx amount \n4.mark overwrite price. "
					+ "\n5.check that the fund deducted from split, order amount should remain same on citrus\n6.check split amount deducted on DB\n7.check refund for the override amount")
	public void SPS_VectorSellerMultiQtyPriceOverRide(String orderId, String OrderReleaseId, String store_order_id,
			String ppsId, String paymentPlanItemId, String paymentPlanItemId1, String paymentPlanItemId2,
			String message, String lineId, String refundMessage, String paymentPlanItemIdref, String ppsIdrefund,
			String releaseMessage) throws SQLException, IOException, InterruptedException, JAXBException {
		log.debug(orderId + " : " + ppsId + " : " + paymentPlanItemId);
		sellerHelper.rabbitMqAddTx(message, orderId);
		Thread.sleep(10000);
		sellerHelper.rabbitMqRefundTx(refundMessage, ppsIdrefund);
		sellerHelper.updateOrderReleaseStatusAndPackedOn(OrderReleaseId, "PK");
		sellerHelper.updateOrderLineStatusByLineId(lineId, "QD");
		sellerHelper.rabbitMqReleasePKTx(releaseMessage, orderId);
		Thread.sleep(10000);
		// int release = sellerHelper.getNoOfRelease(store_order_id);
		// log.debug("Number of release: " + release);
		int releaseTxResult = sellerHelper.getTxDBStatusCount(orderId, "RELEASED");
		log.debug("Number of release on DB: " + releaseTxResult);
		// Assert.assertEquals(release, 1);
		Assert.assertEquals(releaseTxResult, 1, "Order " + orderId + " not found in Transaction table");

		// //java.util.List getTxamountandfee =
		// sellerHelper.getNoOfSplitsAndSplitAmountAndTxAmount(store_order_id);
		// log.debug("Split and txamount & fee amount: " + getTxamountandfee);
		// log.debug("Transection amount: " + getTxamountandfee.get(1));
		// log.debug("Split amounts: " + getTxamountandfee.get(2) + ", " +
		// getTxamountandfee.get(3) + ", "
		// + getTxamountandfee.get(4));
		//
		// AssertJUnit.assertEquals(5397, getTxamountandfee.get(1));
		// ArrayList<Integer> y = new ArrayList<Integer>();
		// y.add((Integer) getTxamountandfee.get(2));
		// y.add((Integer) getTxamountandfee.get(3));
		// y.add((Integer) getTxamountandfee.get(4));
		// Collections.sort(y);
		// log.debug(y);
		// Assert.assertEquals(y.get(0).intValue(), 699, "SPS Transaction
		// Release Amount for Order " + orderId + "");
		// int noOfRefund = sellerHelper.getNoOfRefund(store_order_id);
		// log.debug("Number of Refund: " + noOfRefund);
		BigDecimal splitAmountInDb = sellerHelper.getTxDBAmount(orderId, "3831");
		log.debug("Split amount after PriceOverride: " + splitAmountInDb);
		// AssertJUnit.assertEquals(1, noOfRefund);
		AssertJUnit.assertEquals(699, splitAmountInDb.intValue());
		sellerHelper.deleteFromPaymentPlanItemInstrument(paymentPlanItemId1);
		sellerHelper.deleteFromPaymentPlanItemInstrument(paymentPlanItemId2);
		sellerHelper.deleteFromOrdersLineAdditionalInfo(lineId);
		sellerHelper.deleteOrderRecord(orderId, ppsId, paymentPlanItemId, ppsIdrefund, paymentPlanItemIdref);
	}

	// Create online order with seller Multiqty sku 1 cancel1 release1 check
	// pgsettled1. Check split and refund for that sku in citrus and Db both
	@Test(groups = { "Regression",
			"Sanity" }, priority = 0, dataProvider = "SPS_SellerVectorMulti1SkuLessThan1", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1. Create online order with seller qty2 sku1 and Vector qty1 MRP 0.95. \n2.Validate the tx amount and split amount \n3.check the split in citrus than in SPS That split happens only for 2 qty."
					+ "\n4.cancel vector sku of the order and check that refund should not happen.\n "
					+ "5.release seller both qty and check the release in citurs and sps")
	public void SPS_SellerVectorMulti1SkuLessThan1(String orderId, String store_order_id, String ppsId,
			String ppsIdCancellation, String paymentPlanItemId, String paymentPlanItemIdref, String message,
			String cancellationMessage, String lineId, String paymentPlanExecutionStatus_id, String releaseMessage,
			String paymentPlanItemId1) throws SQLException, IOException, InterruptedException {
		log.debug("---------orderId: " + orderId + " ppsId: " + ppsId + " PymentPlanItemId: " + paymentPlanItemId);
		sellerHelper.rabbitMqAddTx(message, orderId);
		Thread.sleep(10000);
		// List splitsTxAmountSplitAmount =
		// sellerHelper.getNoOfSplitsAndSplitAmountAndTxAmount(store_order_id);
		// log.debug("Number of Splits: " + splitsTxAmountSplitAmount.get(0));
		int txResult = sellerHelper.getSplitTxDBCount(orderId);
		int pgResult = sellerHelper.getSplitPgDBCount(orderId);
		log.debug("DB check Tx table splits count: " + txResult + " pg table: " + pgResult);
		log.debug("DB check Tx table splits count: " + txResult + " pg table: " + pgResult);

		AssertJUnit.assertEquals(2, txResult);
		AssertJUnit.assertEquals(1, pgResult);

		SellerPaymentDP.SPS_SellerVectorMulti1SkuLessThan1Helper(orderId, store_order_id, orderId, lineId, ppsId,
				ppsIdCancellation, paymentPlanItemIdref, paymentPlanExecutionStatus_id);
		sellerHelper.rabbitMqRefundTx(cancellationMessage, ppsIdCancellation);
		Thread.sleep(10000);
		// int refund = sellerHelper.getNoOfRefund(store_order_id);
		// log.debug("Number of Refund: " + refund);
		int refundTxResult = sellerHelper.getTxDBStatusCount1(orderId, "3827", "REFUNDED");
		log.debug("DB status for refund: " + refundTxResult);
		AssertJUnit.assertEquals(0, refundTxResult);

		sellerHelper.rabbitMqReleasePKTx(releaseMessage, orderId);
		Thread.sleep(10000);
		// int release = sellerHelper.getNoOfRelease(store_order_id);
		// log.debug("Number of release: " + release);
		int releaseTxResult = sellerHelper.getTxDBStatusCount(orderId, "RELEASED");
		log.debug("Number of release on DB: " + releaseTxResult);
		// AssertJUnit.assertEquals(2, release);
		AssertJUnit.assertEquals(2, releaseTxResult);

		sellerHelper.deleteFromPaymentPlanItemInstrument(paymentPlanItemId1);
		sellerHelper.deleteOrderRecord(orderId, ppsId, paymentPlanItemId, ppsIdCancellation, paymentPlanItemIdref);
	}

	// Create online order with seller Multiqty sku 1 cancel1 release1 check
	// pgsettled1. Check split and refund for that sku in citrus and Db both
	@Test(groups = { "Regression",
			"Sanity" }, priority = 0, dataProvider = "SPS_SellerMultiQtyCan1RelReturn1", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1. Create online order with seller qty3 sku1. \n2.check the split in citrus than in SPS for all 3qty.\n3.cancel 1qty of the order and check the refund.\n "
					+ "4.release qty1 and check the release in citurs and sps.\n5.check the pg settlement for 3rd split as we release the 1st sku")
	public void SPS_SellerMultiQtyCan1RelReturn1(String orderId, String store_order_id, String ppsId,
			String ppsIdCancellation, String paymentPlanItemId, String paymentPlanItemIdref, String message,
			String cancellationMessage, String lineId, String lineId1, String paymentPlanExecutionStatus_id,
			String releaseMessage, String returnMessage, String ppsReturnId, String paymentPlanreturnItemId)
			throws SQLException, IOException, InterruptedException {
		log.debug("---------orderId: " + orderId + " ppsId: " + ppsId + " PymentPlanItemId: " + paymentPlanItemId);
		sellerHelper.rabbitMqAddTx(message, orderId);
		Thread.sleep(10000);
		// int splits = sellerHelper.getNoOfSplits(store_order_id);
		// log.debug("Number of Splits: " + splits);
		int txResult = sellerHelper.getSplitTxDBCount(orderId);
		int pgResult = sellerHelper.getSplitPgDBCount(orderId);
		log.debug("DB check Tx table splits count: " + txResult + " pg table: " + pgResult);
		// Assert.assertEquals(splits, 3);
		Assert.assertEquals(txResult, 3);
		Assert.assertEquals(pgResult, 1);

		SellerPaymentDP.SPS_SellerMultiQtyCanRelHelper(orderId, store_order_id, orderId, lineId, ppsId,
				ppsIdCancellation, paymentPlanItemIdref, paymentPlanExecutionStatus_id);
		sellerHelper.rabbitMqRefundTx(cancellationMessage, ppsIdCancellation);
		Thread.sleep(10000);
		// int refund = sellerHelper.getNoOfRefund(store_order_id);
		// log.debug("Number of Refund: " + refund);
		int refundTxResult = sellerHelper.getTxDBStatus(orderId, "1251881", "REFUNDED");
		log.debug("DB status for refund: " + refundTxResult);
		// AssertJUnit.assertEquals(1, refund);
		AssertJUnit.assertEquals(1, refundTxResult);

		sellerHelper.rabbitMqReleasePKTx(releaseMessage, orderId);
		Thread.sleep(10000);
		// int release = sellerHelper.getNoOfRelease(store_order_id);
		// log.debug("Number of release: " + release);
		int releaseTxResult = sellerHelper.getTxDBStatusCount(orderId, "RELEASED");
		log.debug("Number of release on DB: " + releaseTxResult);
		// Assert.assertEquals(release, 1, "Number of release on SPS transaction
		// detailDB:");
		Assert.assertEquals(releaseTxResult, 1, "Number of release on SPS transaction detailDB :");
		int pgTxResult = sellerHelper.getTxDBStatusCount(orderId, "PGSETTLE");
		log.debug("Number of PG settled on DB: " + pgTxResult);
		AssertJUnit.assertEquals(1, pgTxResult);

		int releaseTxResult1 = sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED");
		Assert.assertEquals(releaseTxResult1, 1,
				"Order Id: " + orderId + " Not available in SPS Settlement detail table");
		Assert.assertEquals(sellerHelper.getSettlementDetailFundReleaseAmount(orderId, "RELEASED"), "2299.00",
				"Online Release amount:");

		log.debug("Number of releases on SPS Transaction_detail DB: " + releaseTxResult1);
		log.debug("Fund Release Amount :" + sellerHelper.getSettlementDetailFundReleaseAmount(orderId, "RELEASED"));

		SellerPaymentDP.create_SPS_SellerMultiQtyReturnHelper(lineId1, orderId, store_order_id, paymentPlanreturnItemId,
				ppsId, ppsReturnId);
		sellerHelper.rabbitMqRefundTx(returnMessage, ppsIdCancellation);

		log.debug("Wait for 10Sec more to get order details in settlement detail table....");
		Thread.sleep(10000);

		int releaseTxResult2 = sellerHelper.getSettlementdetailDBStatusCount(orderId, "REFUNDED");
		Assert.assertEquals(releaseTxResult2, 1,
				"REFUND Order_id:" + orderId + " Not available in SPS Settlement detail table");
		// Assert.assertEquals(sellerHelper.getSettlementDetailRefundAmount(orderId,"CASH_ON_DELIVERY"),"-1399.00"
		// ,"COD Refund amount:");
		Assert.assertEquals(sellerHelper.getSettlementDetailRefundAmount(orderId, "ONLINE"), "-2299.00",
				"Online Refund amount:");

		sellerHelper.deleteOrderRecord(orderId, ppsId, paymentPlanItemId, ppsIdCancellation, paymentPlanItemIdref);
	}

	// Create online order with seller Multiqty sku 1 cancel1 release1 check
	// pgsettled1. Check split and refund for that sku in citrus and Db both
	@Test(groups = {
			"Regression" }, priority = 0, dataProvider = "SPS_SellerMultiQtyCanRelRef", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1. Create online order with seller qty3 sku1. \n2.check the split in citrus than in SPS for all 3qty.\n3.cancel 1qty of the order and check the refund.\n "
					+ "4.release qty1 and check the release in citurs and sps.\n5.check the pg settlement for 3rd split as we release the 1st sku\n6. return the release sku and check refund")
	public void SPS_SellerMultiQtyCanRelRef(String orderId, String store_order_id, String ppsId,
			String ppsIdCancellation, String paymentPlanItemId, String paymentPlanItemIdref, String message,
			String cancellationMessage, String lineId, String paymentPlanExecutionStatus_id, String releaseMessage,
			String OrderReleaseId1, String returnMessage, String ppsIdReturn, String paymentPlanItemIdret)
			throws SQLException, IOException, InterruptedException {
		log.debug("---------orderId: " + orderId + " ppsId: " + ppsId + " PymentPlanItemId: " + paymentPlanItemId);
		sellerHelper.rabbitMqAddTx(message, orderId);
		Thread.sleep(10000);
		// int splits = sellerHelper.getNoOfSplits(store_order_id);
		// log.debug("Number of Splits: " + splits);
		int txResult = sellerHelper.getSplitTxDBCount(orderId);
		int pgResult = sellerHelper.getSplitPgDBCount(orderId);
		// log.debug("DB check Tx table splits count: " + txResult + " pg table:
		// " + pgResult);
		// AssertJUnit.assertEquals(3, splits);
		AssertJUnit.assertEquals(3, txResult);
		AssertJUnit.assertEquals(1, pgResult);

		SellerPaymentDP.SPS_SellerMultiQtyCanRelHelper(orderId, store_order_id, orderId, lineId, ppsId,
				ppsIdCancellation, paymentPlanItemIdref, paymentPlanExecutionStatus_id);
		sellerHelper.rabbitMqRefundTx(cancellationMessage, ppsIdCancellation);
		Thread.sleep(10000);
		// int refund = sellerHelper.getNoOfRefund(store_order_id);
		// log.debug("Number of Refund: " + refund);
		int refundTxResult = sellerHelper.getTxDBStatus(orderId, "1251881", "REFUNDED");
		log.debug("DB status for refund: " + refundTxResult);
		// AssertJUnit.assertEquals(1, refund);
		AssertJUnit.assertEquals(1, refundTxResult);

		sellerHelper.rabbitMqReleasePKTx(releaseMessage, orderId);
		Thread.sleep(10000);
		// int release = sellerHelper.getNoOfRelease(store_order_id);
		// log.debug("Number of release: " + release);
		int releaseTxResult = sellerHelper.getTxDBStatusCount(orderId, "RELEASED");
		log.debug("Number of release on DB: " + releaseTxResult);
		// AssertJUnit.assertEquals(1, release);
		AssertJUnit.assertEquals(1, releaseTxResult);
		int pgTxResult = sellerHelper.getTxDBStatusCount(orderId, "PGSETTLE");
		log.debug("Number of PG settled on DB: " + pgTxResult);
		AssertJUnit.assertEquals(1, pgTxResult);

		int releaseTxResult1 = sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED");
		Assert.assertEquals(releaseTxResult1, 1,
				"Order Id: " + orderId + " Not available in SPS Settlement detail table");
		Assert.assertEquals(sellerHelper.getSettlementDetailFundReleaseAmount(orderId, "RELEASED"), "2299.00",
				"Online Release amount:");

		log.debug("Number of releases on SPS Transaction_detail DB: " + releaseTxResult1);
		log.debug("Fund Release Amount :" + sellerHelper.getSettlementDetailFundReleaseAmount(orderId, "RELEASED"));

		SellerPaymentDP.SPS_SellerMultiQtyCanRelRefHelper(orderId, store_order_id, OrderReleaseId1,
				paymentPlanItemIdret, ppsId, ppsIdReturn);
		sellerHelper.rabbitMqRefundTx(returnMessage, ppsIdCancellation);
		// int Retrefund = sellerHelper.getNoOfRefund(store_order_id);
		// log.debug("Number of Refund: " + Retrefund);
		// // String RetrefundTxResult = sellerHelper.getTxDBStatus(orderId);
		// // log.debug("DB status for refund: "+RetrefundTxResult);
		// AssertJUnit.assertEquals(2, Retrefund);
		// // AssertJUnit.assertEquals("REFUNDED", refundTxResult);
		log.debug("Wait for 10Sec more to get order details in settlement detail table....");
		Thread.sleep(10000);

		int releaseTxResult2 = sellerHelper.getSettlementdetailDBStatusCount(orderId, "REFUNDED");
		Assert.assertEquals(releaseTxResult2, 1,
				"REFUND Order_id:" + orderId + " Not available in SPS Settlement detail table");
		Assert.assertEquals(sellerHelper.getSettlementDetailRefundAmount(orderId, "ONLINE"), "-2299.00",
				"Online Refund amount:");

		sellerHelper.deleteOrderRecord(orderId, ppsId, paymentPlanItemId, ppsIdCancellation, paymentPlanItemIdref);

	}

	// Create online order with seller Multiqty sku 1 cancel1 release1 check
	// pgsettled1. Check split and refund for that sku in citrus and Db both
	@Test(groups = {
			"Regression" }, priority = 0, dataProvider = "SPS_SellerMultiQtyRelRefCancel", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1. Create online order with seller qty3 sku1. \n2.check the split in citrus than in SPS for all 3qty.\n3.release 1qty of the order and check the release and pg settlement for other.\n "
					+ "4.return qty1 and check the refund in citurs and sps. \n5.cancel the other one sku and check refund")
	public void SPS_SellerMultiQtyRelRefCancel(String orderId, String store_order_id, String ppsId,
			String ppsIdCancellation, String paymentPlanItemId, String paymentPlanItemIdref, String message,
			String cancellationMessage, String lineId, String lineId1, String paymentPlanExecutionStatus_id,
			String releaseMessage, String OrderReleaseId, String OrderReleaseId1, String returnMessage,
			String ppsIdReturn, String paymentPlanItemIdret) throws SQLException, IOException, InterruptedException {
		log.debug("---------orderId: " + orderId + " ppsId: " + ppsId + " PymentPlanItemId: " + paymentPlanItemId);
		sellerHelper.rabbitMqAddTx(message, orderId);
		Thread.sleep(10000);
		// int splits = sellerHelper.getNoOfSplits(store_order_id);
		// log.debug("Number of Splits: " + splits);
		int txResult = sellerHelper.getSplitTxDBCount(orderId);
		int pgResult = sellerHelper.getSplitPgDBCount(orderId);
		log.debug("DB check Tx table splits count: " + txResult + " pg table: " + pgResult);
		// AssertJUnit.assertEquals(3, splits);
		AssertJUnit.assertEquals(3, txResult);
		AssertJUnit.assertEquals(1, pgResult);

		sellerHelper.updateOrderReleaseStatusAndPackedOn(OrderReleaseId, "PK");
		sellerHelper.updateOrderLineStatusByLineId(lineId, "QD");
		sellerHelper.rabbitMqReleasePKTx(releaseMessage, orderId);
		Thread.sleep(10000);

		int releaseTxResult = sellerHelper.getTxDBStatusCount(orderId, "RELEASED");
		log.debug("Number of release on DB: " + releaseTxResult);
		Assert.assertEquals(releaseTxResult, 1,
				"Order Id: " + orderId + " Not available in SPS Transaction detail table");
		int pgTxResult = sellerHelper.getTxDBStatusCount(orderId, "PGSETTLE");
		log.debug("Number of PG settled on DB: " + pgTxResult);
		AssertJUnit.assertEquals(2, pgTxResult);

		int releaseTxResult1 = sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED");
		Assert.assertEquals(releaseTxResult1, 1,
				"Order Id: " + orderId + " Not available in SPS Settlement detail table");
		Assert.assertEquals(sellerHelper.getSettlementDetailFundReleaseAmount(orderId, "RELEASED"), "2299.00",
				"Online Release amount:");

		log.debug("Number of releases on SPS Transaction_detail DB: " + releaseTxResult1);
		log.debug("Fund Release Amount :" + sellerHelper.getSettlementDetailFundReleaseAmount(orderId, "RELEASED"));

		SellerPaymentDP.SPS_SellerMultiQtyRelRefCancelHelper(orderId, store_order_id, OrderReleaseId1,
				paymentPlanItemIdret, ppsId, ppsIdReturn);
		sellerHelper.rabbitMqRefundTx(returnMessage, ppsIdCancellation);
		Thread.sleep(10000);

		log.debug("Wait for 10Sec more to get order details in settlement detail table....");
		Thread.sleep(10000);

		int releaseTxResult2 = sellerHelper.getSettlementdetailDBStatusCount(orderId, "REFUNDED");
		Assert.assertEquals(releaseTxResult2, 1,
				"REFUND Order_id:" + orderId + " Not available in SPS Settlement detail table");
		Assert.assertEquals(sellerHelper.getSettlementDetailRefundAmount(orderId, "ONLINE"), "-2299.00",
				"Online Refund amount:");

		SellerPaymentDP.SPS_SellerMultiQtyRelRefLineCancellationHelper(orderId, store_order_id, orderId, lineId, ppsId,
				ppsIdCancellation, paymentPlanItemIdref, paymentPlanExecutionStatus_id);
		sellerHelper.rabbitMqRefundTx(cancellationMessage, ppsIdCancellation);
		Thread.sleep(10000);
		Assert.assertEquals(sellerHelper.getSettlementdetailDBStatusCount(orderId, "REFUNDED"), 1,
				"REFUND Order_id:" + orderId + " Not available in SPS Settlement detail table");
		Assert.assertEquals(sellerHelper.getTxDBStatusCount(orderId, "REFUNDED"), 2,
				"REFUND Order_id:" + orderId + " Not available in SPS Settlement detail table");

		sellerHelper.deleteOrderRecord(orderId, ppsId, paymentPlanItemId, ppsIdCancellation, paymentPlanItemIdref);
	}

	// Create online order with seller Multiqty sku 1 cancel1 release1 check
	// pgsettled1. Check split and refund for that sku in citrus and Db both
	@Test(groups = {
			"Regression" }, priority = 0, dataProvider = "SPS_VectorMultiQtyCanRelease1Return1", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1. Create online order with Vector qty3 sku1. \n2.check the split in citrus than in SPS for all 3qty.\n3.cancel 1qty of the order and check the refund.\n "
					+ "4.release qty1 and check the release in citurs and sps.\n5.check the pg settlement for 3rd split as we release the 1st sku")
	public void SPS_VectorMultiQtyCanRelease1Return1(String orderId, String store_order_id, String ppsId,
			String ppsIdCancellation, String ppsreturnId, String paymentPlanItemId, String paymentPlanItemIdref,
			String paymentPlanItemIdref1, String message, String cancellationMessage, String lineId,
			String paymentPlanExecutionStatus_id, String releaseMessage, String paymentPlanItemId1,
			String paymentPlanItemId2, String returnMessage) throws SQLException, IOException, InterruptedException {
		log.debug("---------orderId: " + orderId + " ppsId: " + ppsId + " PymentPlanItemId: " + paymentPlanItemId);
		sellerHelper.rabbitMqAddTx(message, orderId);
		Thread.sleep(10000);
		// int splits = sellerHelper.getNoOfSplits(store_order_id);
		// log.debug("Number of Splits: " + splits);
		int txResult = sellerHelper.getSplitTxDBCount(orderId);
		int pgResult = sellerHelper.getSplitPgDBCount(orderId);
		log.debug("DB check Tx table splits count: " + txResult + " pg table: " + pgResult);
		// AssertJUnit.assertEquals(3, splits);
		AssertJUnit.assertEquals(3, txResult);
		AssertJUnit.assertEquals(1, pgResult);

		SellerPaymentDP.SPS_VectorMultiQtyCanRelRefHelper(orderId, store_order_id, orderId, lineId, ppsId,
				ppsIdCancellation, paymentPlanItemIdref, paymentPlanExecutionStatus_id);
		sellerHelper.rabbitMqRefundTx(cancellationMessage, ppsIdCancellation);
		Thread.sleep(10000);
		// int refund = sellerHelper.getNoOfRefund(store_order_id);
		// log.debug("Number of Refund: " + refund);
		int refundTxResult = sellerHelper.getTxDBStatus(orderId, "3827", "REFUNDED");
		log.debug("DB status for refund: " + refundTxResult);
		// AssertJUnit.assertEquals(1, refund);
		AssertJUnit.assertEquals(1, refundTxResult);

		sellerHelper.rabbitMqReleasePKTx(releaseMessage, orderId);
		Thread.sleep(10000);
		// int release = sellerHelper.getNoOfRelease(store_order_id);
		// log.debug("Number of release: " + release);
		int releaseTxResult = sellerHelper.getTxDBStatusCount(orderId, "RELEASED");
		log.debug("Number of release on DB: " + releaseTxResult);
		// AssertJUnit.assertEquals(2, release);
		AssertJUnit.assertEquals(2, releaseTxResult);

		int releaseTxResult1 = sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED");
		Assert.assertEquals(releaseTxResult1, 2,
				"Order Id: " + orderId + " Not available in SPS Settlement detail table");
		// Assert.assertEquals(sellerHelper.getSettlementDetailFundReleaseAmount(orderId,"RELEASED"),"799.00"
		// ,"Online Release amount:");

		log.debug("Number of releases on SPS Transaction_detail DB: " + releaseTxResult1);
		log.debug("Fund Release Amount :" + sellerHelper.getSettlementDetailFundReleaseAmount(orderId, "RELEASED"));

		SellerPaymentDP.create_Online_OrderWithSellerQty1ReturnHelper(orderId, store_order_id, paymentPlanItemIdref1,
				ppsId, ppsreturnId);
		sellerHelper.rabbitMqRefundTx(returnMessage, ppsIdCancellation);

		log.debug("Wait for 10Sec more to get order details in settlement detail table....");
		Thread.sleep(10000);

		int releaseTxResult2 = sellerHelper.getSettlementdetailDBStatusCount(orderId, "REFUNDED");
		Assert.assertEquals(releaseTxResult2, 1,
				"REFUND Order_id:" + orderId + " Not available in SPS Settlement detail table");
		// Assert.assertEquals(sellerHelper.getSettlementDetailRefundAmount(orderId,"CASH_ON_DELIVERY"),"-1399.00"
		// ,"COD Refund amount:");
		Assert.assertEquals(sellerHelper.getSettlementDetailRefundAmount(orderId, "ONLINE"), "-2399.00",
				"Online Refund amount:");

		sellerHelper.deleteFromPaymentPlanItemInstrument(paymentPlanItemId1);
		sellerHelper.deleteFromPaymentPlanItemInstrument(paymentPlanItemId2);
		sellerHelper.deleteOrderRecord(orderId, ppsId, paymentPlanItemId, ppsIdCancellation, paymentPlanItemIdref);
	}

	// Create online order with seller Multiqty sku 1 cancel1 release1 check
	// pgsettled1. Check split and refund for that sku in citrus and Db both
	@Test(groups = {
			"Regression" }, priority = 0, dataProvider = "SPS_VectorSellerJITSingleQtyCanRelRef", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1. Create online order with Vector, seller and JIT each qty1 sku1. \n2.check the split in citrus than in SPS for all 3qty.\n3.cancel 1qty of the order and check the refund.\n "
					+ "4.release qty1 and check the release in citurs and sps.\n5.check the pg settlement for 3rd split as we release the 1st sku\n6. return the release sku and check refund")
	public void SPS_VectorSellerJITSingleQtyCanRelRef(String orderId, String store_order_id, String ppsId,
			String ppsIdCancellation, String paymentPlanItemId, String paymentPlanItemIdref, String message,
			String cancellationMessage, String lineId, String lineId1, String paymentPlanExecutionStatus_id,
			String releaseMessage, String OrderReleaseId1, String returnMessage, String ppsIdReturn,
			String paymentPlanItemIdret, String paymentPlanItemId1, String paymentPlanItemId2)
			throws SQLException, IOException, InterruptedException {
		log.debug("---------orderId: " + orderId + " ppsId: " + ppsId + " PymentPlanItemId: " + paymentPlanItemId);
		sellerHelper.rabbitMqAddTx(message, orderId);
		Thread.sleep(10000);
		// int splits = sellerHelper.getNoOfSplits(store_order_id);
		// log.debug("Number of Splits: " + splits);
		int txResult = sellerHelper.getSplitTxDBCount(orderId);
		int pgResult = sellerHelper.getSplitPgDBCount(orderId);
		log.debug("DB check Tx table splits count: " + txResult + " pg table: " + pgResult);
		// AssertJUnit.assertEquals(3, splits);
		AssertJUnit.assertEquals(3, txResult);
		AssertJUnit.assertEquals(1, pgResult);

		SellerPaymentDP.SPS_VectorSellerJITSingleQtyCanRelRefCancelHelper(orderId, store_order_id, orderId, lineId,
				ppsId, ppsIdCancellation, paymentPlanItemIdref, paymentPlanExecutionStatus_id);
		sellerHelper.rabbitMqRefundTx(cancellationMessage, ppsIdCancellation);
		Thread.sleep(10000);
		// int refund = sellerHelper.getNoOfRefund(store_order_id);
		// log.debug("Number of Refund: " + refund);
		int refundTxResult = sellerHelper.getTxDBStatus(orderId, "3836", "REFUNDED");
		log.debug("DB status for refund: " + refundTxResult);
		// AssertJUnit.assertEquals(1, refund);
		Assert.assertEquals(refundTxResult, 1, "Refund" + orderId + " not available in Transaction details db");

		sellerHelper.rabbitMqReleasePKTx(releaseMessage, orderId);
		Thread.sleep(10000);
		// int release = sellerHelper.getNoOfRelease(store_order_id);
		// log.debug("Number of release: " + release);
		int releaseTxResult = sellerHelper.getTxDBStatusCount(orderId, "RELEASED");
		log.debug("Number of release on DB: " + releaseTxResult);
		// AssertJUnit.assertEquals(1, release);
		AssertJUnit.assertEquals(1, releaseTxResult);
		int pgTxResult = sellerHelper.getTxDBStatusCount(orderId, "PGSETTLE");
		log.debug("Number of PG settled on DB: " + pgTxResult);
		AssertJUnit.assertEquals(1, pgTxResult);

		int releaseTxResult1 = sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED");
		Assert.assertEquals(releaseTxResult1, 1,
				"Order Id: " + orderId + " Not available in SPS Settlement detail table");
		Assert.assertEquals(sellerHelper.getSettlementDetailFundReleaseAmount(orderId, "RELEASED"), "2299.00",
				"Online Release amount:");

		log.debug("Number of releases on SPS Transaction_detail DB: " + releaseTxResult1);
		log.debug("Fund Release Amount :" + sellerHelper.getSettlementDetailFundReleaseAmount(orderId, "RELEASED"));

		SellerPaymentDP.SPS_VectorSellerJITSingleQtyCanRelRefHelper(lineId1, orderId, store_order_id, OrderReleaseId1,
				paymentPlanItemIdret, ppsId, ppsIdReturn);
		sellerHelper.rabbitMqRefundTx(returnMessage, ppsIdCancellation);
		Thread.sleep(10000);
		// int Retrefund = sellerHelper.getNoOfRefund(store_order_id);
		// log.debug("Number of Refund: " + Retrefund);
		// String RetrefundTxResult = sellerHelper.getTxDBStatus(orderId);
		// log.debug("DB status for refund: "+RetrefundTxResult);
		// AssertJUnit.assertEquals(2, Retrefund);
		// AssertJUnit.assertEquals("REFUNDED", refundTxResult);

		int releaseTxResult2 = sellerHelper.getSettlementdetailDBStatusCount(orderId, "REFUNDED");
		Assert.assertEquals(releaseTxResult2, 1,
				"REFUND Order_id:" + orderId + " Not available in SPS Settlement detail table");
		// Assert.assertEquals(sellerHelper.getSettlementDetailRefundAmount(orderId,"CASH_ON_DELIVERY"),"-1399.00"
		// ,"COD Refund amount:");
		Assert.assertEquals(sellerHelper.getSettlementDetailRefundAmount(orderId, "ONLINE"), "-2299.00",
				"Online Refund amount:");

		sellerHelper.deleteFromPaymentPlanItemInstrument(paymentPlanItemId1);
		sellerHelper.deleteFromPaymentPlanItemInstrument(paymentPlanItemId2);
		sellerHelper.deleteOrderRecord(orderId, ppsId, paymentPlanItemId, ppsIdCancellation, paymentPlanItemIdref);
	}

	// Create online order with seller qty1 sku 1 . Check split and refund for
	// that sku in citrus and Db both
	@Test(groups = {
			"Regression" }, priority = 0, dataProvider = "SPS_MultiSellerMultiQty", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1.Create online order with seller qty4 sku2. \n2.check the splits in citrus than in SPS.")
	public void SPS_MultiSellerMultiQty(String orderId, String OrderReleaseId, String OrderReleaseId1,
			String OrderReleaseId2, String OrderReleaseId3, String store_order_id, String ppsId,
			String paymentPlanItemId, String message, String lineId, String lineId4, String paymentPlanItemId1,
			String releaseFundMessage, String releaseFundMessage1, String releaseFundMessage2,
			String releaseFundMessage3, String paymentPlanItemIdref1, String ppsIdRefund1, String paymentPlanItemIdref2,
			String ppsIdRefund2, String paymentPlanItemIdref3, String ppsIdRefund3, String paymentPlanItemIdref4,
			String ppsIdRefund4, String returnMessage1, String returnMessage2, String returnMessage3,
			String returnMessage4, String returnMessage5, String paymentPlanExecutionStatus_id, String ppsIdRefund5,
			String paymentPlanItemIdRefund5) throws SQLException, IOException, InterruptedException {
		log.debug("-------" + orderId + " : " + ppsId + " : " + paymentPlanItemId);
		sellerHelper.rabbitMqAddTx(message, orderId);
		Thread.sleep(10000);
		// int splits = sellerHelper.getNoOfSplits(store_order_id);
		// log.debug("Number of Splits: " + splits);
		int txResult = sellerHelper.getSplitTxDBCount(orderId);
		int pgResult = sellerHelper.getSplitPgDBCount(orderId);
		log.debug("DB check Tx table splits count: " + txResult + " pg table: " + pgResult);
		// AssertJUnit.assertEquals(4, splits);
		AssertJUnit.assertEquals(4, txResult);
		AssertJUnit.assertEquals(1, pgResult);

		sellerHelper.updateOrderReleaseStatusAndPackedOn(OrderReleaseId, "PK");
		sellerHelper.rabbitMqReleasePKTx(releaseFundMessage, orderId);
		sellerHelper.updateOrderReleaseStatusAndPackedOn(OrderReleaseId1, "PK");
		sellerHelper.rabbitMqReleasePKTx(releaseFundMessage1, orderId);
		sellerHelper.updateOrderReleaseStatusAndPackedOn(OrderReleaseId2, "PK");
		sellerHelper.rabbitMqReleasePKTx(releaseFundMessage2, orderId);
		// sellerHelper.rabbitMqReleasePKTx(releaseFundMessage3);
		Thread.sleep(10000);
		// int noOfRelease2 = sellerHelper.getNoOfRelease(store_order_id);
		// log.debug("No of release: " + noOfRelease2);
		int releaseTxResult = sellerHelper.getTxDBStatus1(orderId, "RELEASED");
		log.debug("DB status for release: " + releaseTxResult);
		// AssertJUnit.assertEquals(4, noOfRelease2);
		AssertJUnit.assertEquals(3, releaseTxResult);

		SellerPaymentDP.SPS_MultiSellerMultiQtyCancelHelper(orderId, store_order_id, OrderReleaseId3, lineId4, ppsId,
				ppsIdRefund4, paymentPlanItemIdref4, paymentPlanExecutionStatus_id);
		sellerHelper.rabbitMqRefundTx(returnMessage4, ppsIdRefund4);

		SellerPaymentDP.SPS_MultiSellerMultiQtyReturnHelper(orderId, store_order_id, ppsId, paymentPlanItemIdref1,
				ppsIdRefund1, paymentPlanItemIdref2, ppsIdRefund2, paymentPlanItemIdref3, ppsIdRefund3,
				paymentPlanItemIdRefund5, ppsIdRefund5);

		sellerHelper.rabbitMqRefundTx(returnMessage1, ppsIdRefund1);
		Thread.sleep(10000);
		int releaseTxResult1 = sellerHelper.getTxDBStatus1(orderId, "REFUNDED");
		int releaseSettlementDBResult = sellerHelper.getSettlementdetailDBStatusCount(orderId, "REFUNDED");
		Assert.assertEquals(releaseTxResult1, 2,
				"REFUND Order Id: " + orderId + " Not available in SPS Transaction db detail table");
		Assert.assertEquals(releaseSettlementDBResult, 1,
				"REFUND Order Id: " + orderId + " Not available in SPS Settlement detail table");

		sellerHelper.rabbitMqRefundTx(returnMessage2, ppsIdRefund2);
		Thread.sleep(10000);
		int releaseTxResult2 = sellerHelper.getTxDBStatus1(orderId, "REFUNDED");
		int releaseSettlementDBResult2 = sellerHelper.getSettlementdetailDBStatusCount(orderId, "REFUNDED");
		Assert.assertEquals(releaseTxResult2, 3,
				"REFUND Order Id: " + orderId + " Not available in SPS Transaction db detail table");
		Assert.assertEquals(releaseSettlementDBResult2, 2,
				"REFUND Order Id: " + orderId + " Not available in SPS Settlement detail table");

		sellerHelper.rabbitMqRefundTx(returnMessage3, ppsIdRefund3);
		Thread.sleep(10000);
		int releaseTxResult3 = sellerHelper.getTxDBStatus1(orderId, "REFUNDED");
		int releaseSettlementDBResult3 = sellerHelper.getSettlementdetailDBStatusCount(orderId, "REFUNDED");
		Assert.assertEquals(releaseTxResult3, 4,
				"REFUND Order Id: " + orderId + " Not available in SPS Transaction db detail table");
		Assert.assertEquals(releaseSettlementDBResult3, 3,
				"REFUND Order Id: " + orderId + " Not available in SPS Settlement detail table");

		sellerHelper.rabbitMqRefundTx(returnMessage5, ppsIdRefund5);
		Thread.sleep(10000);
		int releaseTxResult4 = sellerHelper.getTxDBStatus1(orderId, "REFUNDED");
		int releaseSettlementDBResult4 = sellerHelper.getSettlementdetailDBStatusCount(orderId, "REFUNDED");
		Assert.assertEquals(releaseTxResult4, 4,
				"REFUND Order Id: " + orderId + " Not available in SPS Transaction db detail table");
		Assert.assertEquals(releaseSettlementDBResult4, 3,
				"REFUND Order Id: " + orderId + " Not available in SPS Settlement detail table");

		sellerHelper.deleteFromPaymentPlanItemInstrument(paymentPlanItemId1);
		sellerHelper.deleteOrderRecord(orderId, ppsId, paymentPlanItemId);
	}

	// Create online order with seller qty1 sku 1 and release fund on PK. Check
	// split and release for that sku in citrus and Db both
	@Test(groups = { "Regression",
			"Sanity" }, priority = 0, dataProvider = "SPS_SellerQty1ReleaseAndReturn", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1.Create online order with seller qty1 sku1. \n2.check the split in citrus than in SPS. \n3.Release the sku and check release in both(On order Packed). "
					+ "\n4.Return the sku and check the refund in both")
	public void SPS_SellerQty1ReleaseAndReturn(String message, String releaseFundMessage, String returnMessage,
			String orderId, String store_order_id, String OrderReleaseId, String lineId, String ppsId,
			String ppsIdCancellation, String paymentPlanItemId, String paymentPlanItemIdref)
			throws SQLException, IOException, InterruptedException {
		log.debug(orderId + " : " + ppsId + " : " + paymentPlanItemId);
		sellerHelper.rabbitMqAddTx(message, orderId);
		Thread.sleep(10000);
		// int splits = sellerHelper.getNoOfSplits(store_order_id);
		// log.debug("Number of Splits: " + splits);
		int txResult = sellerHelper.getSplitTxDBCount(orderId);
		int pgResult = sellerHelper.getSplitPgDBCount(orderId);
		log.debug("DB check Tx table splits count: " + txResult + " pg table: " + pgResult);
		// AssertJUnit.assertEquals(1, splits);
		AssertJUnit.assertEquals(1, txResult);
		AssertJUnit.assertEquals(1, pgResult);
		sellerHelper.updateOrderReleaseStatus(orderId, "PK");
		sellerHelper.updateOrderLineStatus(orderId, "QD");
		sellerHelper.rabbitMqReleasePKTx(releaseFundMessage, orderId);
		Thread.sleep(10000);
		// int release = sellerHelper.getNoOfRelease(store_order_id);
		// log.debug("Number of release: " + release);
		int releaseTxResult = sellerHelper.getTxDBStatus1(orderId, "RELEASED");
		log.debug("DB status for release: " + releaseTxResult);
		// AssertJUnit.assertEquals(1, release);
		AssertJUnit.assertEquals(1, releaseTxResult);

		int releaseTxResult1 = sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED");
		Assert.assertEquals(releaseTxResult1, 1,
				"Order Id: " + orderId + " Not available in SPS Settlement detail table");
		Assert.assertEquals(sellerHelper.getSettlementDetailFundReleaseAmount(orderId, "RELEASED"), "2.00",
				"Online Release amount:");
		log.debug("Number of releases on SPS Transaction_detail DB: " + releaseTxResult1);
		log.debug("Fund Release Amount :" + sellerHelper.getSettlementDetailFundReleaseAmount(orderId, "RELEASED"));

		SellerPaymentDP.createOnlineOrderWithSellerQty1ReleaseHelper(orderId, store_order_id, OrderReleaseId,
				paymentPlanItemIdref, ppsId, ppsIdCancellation);
		sellerHelper.rabbitMqRefundTx(returnMessage, ppsIdCancellation);
		Thread.sleep(10000);
		// int refund = sellerHelper.getNoOfRefund(store_order_id);
		// log.debug("Number of Refund: " + refund);
		int refundTxResult = sellerHelper.getTxDBStatus1(orderId, "REFUNDED");
		log.debug("DB status for refund: " + refundTxResult);
		// AssertJUnit.assertEquals(1, refund);
		AssertJUnit.assertEquals(1, refundTxResult);
		int releaseTxResult2 = sellerHelper.getSettlementdetailDBStatusCount(orderId, "REFUNDED");
		Assert.assertEquals(releaseTxResult2, 1,
				"REFUND Order_id:" + orderId + " Not available in SPS Settlement detail table");
		Assert.assertEquals(sellerHelper.getSettlementDetailRefundAmount(orderId, "ONLINE"), "-2.00",
				"Online Refund amount:");

		sellerHelper.deleteOrderRecord(orderId, ppsId, paymentPlanItemId, ppsIdCancellation, paymentPlanItemIdref);
	}

	// Create online order with seller qty1 sku 1 and release fund on PK. Check
	// split and release for that sku in citrus and Db both
	@Test(groups = { "Regression",
			"Sanity" }, priority = 0, dataProvider = "SPS_tryAndBuy", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1.Create online order with seller qty1 sku1. \n2.check the split in citrus than in SPS. \n3.Release the sku and check release in both(On order Packed). "
					+ "\n4.Return the sku and check the refund in both")
	public void SPS_tryAndBuy(String message, String releaseFundMessage, String returnMessage, String orderId,
			String store_order_id, String OrderReleaseId, String lineId, String ppsId, String ppsIdCancellation,
			String paymentPlanItemId, String paymentPlanItemIdref)
			throws SQLException, IOException, InterruptedException {
		log.debug(orderId + " : " + ppsId + " : " + paymentPlanItemId);
		sellerHelper.rabbitMqAddTx(message, orderId);
		Thread.sleep(10000);
		// int splits = sellerHelper.getNoOfSplits(store_order_id);
		// log.debug("Number of Splits: " + splits);
		int txResult = sellerHelper.getSplitTxDBCount(orderId);
		int pgResult = sellerHelper.getSplitPgDBCount(orderId);
		log.debug("DB check Tx table splits count: " + txResult + " pg table: " + pgResult);
		// AssertJUnit.assertEquals(1, splits);
		AssertJUnit.assertEquals(1, txResult);
		AssertJUnit.assertEquals(1, pgResult);
		sellerHelper.updateOrderReleaseStatus(orderId, "PK");
		sellerHelper.updateOrderLineStatus(orderId, "QD");
		sellerHelper.rabbitMqReleasePKTx(releaseFundMessage, orderId);
		Thread.sleep(10000);
		// int release = sellerHelper.getNoOfRelease(store_order_id);
		// log.debug("Number of release: " + release);
		int releaseTxResult = sellerHelper.getTxDBStatus1(orderId, "RELEASED");
		log.debug("DB status for release: " + releaseTxResult);
		// AssertJUnit.assertEquals(1, release);
		AssertJUnit.assertEquals(1, releaseTxResult);
		int releaseTxResult1 = sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED");
		Assert.assertEquals(releaseTxResult1, 1,
				"Order Id: " + orderId + " Not available in SPS Settlement detail table");
		Assert.assertEquals(sellerHelper.getSettlementDetailFundReleaseAmount(orderId, "RELEASED"), "2.00",
				"Online Release amount:");
		log.debug("Number of releases on SPS Transaction_detail DB: " + releaseTxResult1);
		log.debug("Fund Release Amount :" + sellerHelper.getSettlementDetailFundReleaseAmount(orderId, "RELEASED"));

		SellerPaymentDP.createOnlineOrderWithSellerQty1ReleaseHelper1(orderId, store_order_id, OrderReleaseId,
				paymentPlanItemIdref, ppsId, ppsIdCancellation);
		sellerHelper.rabbitMqRefundTx(returnMessage, ppsIdCancellation);
		Thread.sleep(10000);
		// int refund = sellerHelper.getNoOfRefund(store_order_id);
		// log.debug("Number of Refund: " + refund);
		int refundTxResult = sellerHelper.getTxDBStatus1(orderId, "REFUNDED");
		log.debug("DB status for refund: " + refundTxResult);
		// AssertJUnit.assertEquals(1, refund);
		AssertJUnit.assertEquals(1, refundTxResult);
		int releaseTxResult2 = sellerHelper.getSettlementdetailDBStatusCount(orderId, "REFUNDED");
		Assert.assertEquals(releaseTxResult2, 1,
				"REFUND Order_id:" + orderId + " Not available in SPS Settlement detail table");
		Assert.assertEquals(sellerHelper.getSettlementDetailRefundAmount(orderId, "ONLINE"), "-2.00",
				"Online Refund amount:");

		sellerHelper.deleteOrderRecord(orderId, ppsId, paymentPlanItemId, ppsIdCancellation, paymentPlanItemIdref);
	}

	// Create online order with seller qty1 sku 1 and release fund on PK. Check
	// split and release for that sku in citrus and Db both
	@Test(groups = { "Regression",
			"Sanity" }, priority = 0, dataProvider = "SPS_tryAndBuy2", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1.Create online order with seller qty1 sku1. \n2.check the split in citrus than in SPS. \n3.Release the sku and check release in both(On order Packed). "
					+ "\n4.Return the sku and check the refund in both")
	public void SPS_tryAndBuy2(String message, String releaseFundMessage, String returnMessage, String orderId,
			String store_order_id, String OrderReleaseId, String lineId, String ppsId, String ppsIdCancellation,
			String paymentPlanItemId, String paymentPlanItemIdref, String paymentPlanItemIdref1)
			throws SQLException, IOException, InterruptedException {
		log.debug(orderId + " : " + ppsId + " : " + paymentPlanItemId);
		sellerHelper.rabbitMqAddTx(message, orderId);
		Thread.sleep(10000);
		// int splits = sellerHelper.getNoOfSplits(store_order_id);
		// log.debug("Number of Splits: " + splits);
		int txResult = sellerHelper.getSplitTxDBCount(orderId);
		int pgResult = sellerHelper.getSplitPgDBCount(orderId);
		log.debug("DB check Tx table splits count: " + txResult + " pg table: " + pgResult);
		// AssertJUnit.assertEquals(2, splits);
		AssertJUnit.assertEquals(2, txResult);
		AssertJUnit.assertEquals(1, pgResult);
		sellerHelper.updateOrderReleaseStatus(orderId, "PK");
		sellerHelper.updateOrderLineStatus(orderId, "QD");
		sellerHelper.rabbitMqReleasePKTx(releaseFundMessage, orderId);
		Thread.sleep(10000);
		// int release = sellerHelper.getNoOfRelease(store_order_id);
		// log.debug("Number of release: " + release);
		int releaseTxResult = sellerHelper.getTxDBStatus1(orderId, "RELEASED");
		log.debug("DB status for release: " + releaseTxResult);
		// AssertJUnit.assertEquals(2, release);
		AssertJUnit.assertEquals(2, releaseTxResult);
		int releaseTxResult1 = sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED");
		Assert.assertEquals(releaseTxResult1, 2,
				"Order Id: " + orderId + " Not available in SPS Settlement detail table");
		Assert.assertEquals(sellerHelper.getSettlementDetailFundReleaseAmount(orderId, "RELEASED"), "2.00",
				"Online Release amount:");
		log.debug("Number of releases on SPS Transaction_detail DB: " + releaseTxResult1);
		log.debug("Fund Release Amount :" + sellerHelper.getSettlementDetailFundReleaseAmount(orderId, "RELEASED"));

		SellerPaymentDP.createOnlineOrderWithVectorQty2ReleaseHelper1(orderId, store_order_id, OrderReleaseId,
				paymentPlanItemIdref, ppsId, ppsIdCancellation, paymentPlanItemIdref1);
		sellerHelper.rabbitMqRefundTx(returnMessage, ppsIdCancellation);
		Thread.sleep(10000);
		// int refund = sellerHelper.getNoOfRefund(store_order_id);
		// log.debug("Number of Refund: " + refund);
		int refundTxResult = sellerHelper.getTxDBStatus1(orderId, "REFUNDED");
		log.debug("DB status for refund: " + refundTxResult);
		// AssertJUnit.assertEquals(2, refund);
		AssertJUnit.assertEquals(2, refundTxResult);
		int releaseTxResult2 = sellerHelper.getSettlementdetailDBStatusCount(orderId, "REFUNDED");
		Assert.assertEquals(releaseTxResult2, 2,
				"REFUND Order_id:" + orderId + " Not available in SPS Settlement detail table");
		// Assert.assertEquals(sellerHelper.getSettlementDetailRefundAmount(orderId,"CASH_ON_DELIVERY"),"-1399.00"
		// ,"COD Refund amount:");
		Assert.assertEquals(sellerHelper.getSettlementDetailRefundAmount(orderId, "ONLINE"), "-2.00",
				"Online Refund amount:");

		// sellerHelper.deleteOrderRecord(orderId, ppsId, paymentPlanItemId,
		// ppsIdCancellation, paymentPlanItemIdref);
	}

	// Create online order with seller qty1 sku 1 and release fund on PK. Check
	// split and release for that sku in citrus and Db both
	@Test(groups = { "Regression",
			"Sanity" }, priority = 0, dataProvider = "SPS_sameSkuQty2AndReturnBoth", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1.Create online order with seller qty1 sku1. \n2.check the split in citrus than in SPS. \n3.Release the sku and check release in both(On order Packed). "
					+ "\n4.Return the sku and check the refund in both")
	public void SPS_sameSkuQty2AndReturnBoth(String message, String releaseFundMessage, String returnMessage,
			String orderId, String store_order_id, String OrderReleaseId, String lineId, String ppsId,
			String ppsIdCancellation, String paymentPlanItemId, String paymentPlanItemIdref,
			String paymentPlanItemIdref1) throws SQLException, IOException, InterruptedException {
		log.debug(orderId + " : " + ppsId + " : " + paymentPlanItemId);
		sellerHelper.rabbitMqAddTx(message, orderId);
		Thread.sleep(10000);
		// int splits = sellerHelper.getNoOfSplits(store_order_id);
		// log.debug("Number of Splits: " + splits);
		int txResult = sellerHelper.getSplitTxDBCount(orderId);
		int pgResult = sellerHelper.getSplitPgDBCount(orderId);
		log.debug("DB check Tx table splits count: " + txResult + " pg table: " + pgResult);
		// AssertJUnit.assertEquals(2, splits);
		AssertJUnit.assertEquals(2, txResult);
		AssertJUnit.assertEquals(1, pgResult);
		sellerHelper.updateOrderReleaseStatus(orderId, "PK");
		sellerHelper.updateOrderLineStatus(orderId, "QD");
		sellerHelper.rabbitMqReleasePKTx(releaseFundMessage, orderId);
		Thread.sleep(10000);
		// int release = sellerHelper.getNoOfRelease(store_order_id);
		// log.debug("Number of release: " + release);
		int releaseTxResult = sellerHelper.getTxDBStatus1(orderId, "RELEASED");
		log.debug("DB status for release: " + releaseTxResult);
		// AssertJUnit.assertEquals(2, release);
		AssertJUnit.assertEquals(2, releaseTxResult);
		int releaseTxResult1 = sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED");
		Assert.assertEquals(releaseTxResult1, 2,
				"Order Id: " + orderId + " Not available in SPS Settlement detail table");
		Assert.assertEquals(sellerHelper.getSettlementDetailFundReleaseAmount(orderId, "RELEASED"), "2.00",
				"Online Release amount:");
		log.debug("Number of releases on SPS Transaction_detail DB: " + releaseTxResult1);
		log.debug("Fund Release Amount :" + sellerHelper.getSettlementDetailFundReleaseAmount(orderId, "RELEASED"));

		SellerPaymentDP.createOnlineOrderWithSameSkuQty2AndReturnBoth(orderId, store_order_id, OrderReleaseId,
				paymentPlanItemIdref, ppsId, ppsIdCancellation, paymentPlanItemIdref1);
		sellerHelper.rabbitMqRefundTx(returnMessage, ppsIdCancellation);
		Thread.sleep(10000);
		// int refund = sellerHelper.getNoOfRefund(store_order_id);
		// log.debug("Number of Refund: " + refund);
		int refundTxResult = sellerHelper.getTxDBStatus1(orderId, "REFUNDED");
		log.debug("DB status for refund: " + refundTxResult);
		// AssertJUnit.assertEquals(2, refund);
		AssertJUnit.assertEquals(2, refundTxResult);
		int releaseTxResult2 = sellerHelper.getSettlementdetailDBStatusCount(orderId, "REFUNDED");
		Assert.assertEquals(releaseTxResult2, 2,
				"REFUND Order_id:" + orderId + " Not available in SPS Settlement detail table");
		// Assert.assertEquals(sellerHelper.getSettlementDetailRefundAmount(orderId,"CASH_ON_DELIVERY"),"-1399.00"
		// ,"COD Refund amount:");
		Assert.assertEquals(sellerHelper.getSettlementDetailRefundAmount(orderId, "ONLINE"), "-2.00",
				"Online Refund amount:");

	}

	// Create online order with seller qty1 sku 1 and release fund on PK. Check
	// split and release for that sku in citrus and Db both
	@Test(groups = { "Regression",
			"Sanity" }, priority = 0, dataProvider = "SPS_SellerQty1ReleaseRetryOnOrderDeliverAndReturn", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1.Create online order with seller qty1 sku1. \n2.check the split in citrus than in SPS. \n3.try to Release the sku on_order_packed and check that release should not happen as the seller is On order Delivered. "
					+ "\n4. Check retry that sku should not release \n5. Release the sku on_order_delivered and check on both \n6.Return the sku and check the refund in both")
	public void SPS_SellerQty1ReleaseRetryOnOrderDeliverAndReturn(String message, String releaseFundMessage,
			String returnMessage, String orderId, String store_order_id, String OrderReleaseId, String lineId,
			String ppsId, String ppsIdCancellation, String paymentPlanItemId, String paymentPlanItemIdref,
			String releaseFundMessage1) throws SQLException, IOException, InterruptedException {
		log.debug(orderId + " : " + ppsId + " : " + paymentPlanItemId);
		sellerHelper.rabbitMqAddTx(message, orderId);
		Thread.sleep(10000);
		// int splits = sellerHelper.getNoOfSplits(store_order_id);
		// log.debug("Number of Splits: " + splits);
		int txResult = sellerHelper.getSplitTxDBCount(orderId);
		int pgResult = sellerHelper.getSplitPgDBCount(orderId);
		log.debug("DB check Tx table splits count: " + txResult + " pg table: " + pgResult);
		// AssertJUnit.assertEquals(1, splits);
		AssertJUnit.assertEquals(1, txResult);
		AssertJUnit.assertEquals(1, pgResult);
		sellerHelper.updateOrderReleaseStatus(orderId, "PK");
		sellerHelper.updateOrderLineStatus(orderId, "QD");
		sellerHelper.rabbitMqReleasePKTx(releaseFundMessage, orderId);
		Thread.sleep(10000);
		// int release = sellerHelper.getNoOfRelease(store_order_id);
		// log.debug("Number of release: " + release);
		int releaseTxResult = sellerHelper.getTxDBStatus1(orderId, "RELEASED");
		log.debug("DB status for release: " + releaseTxResult);
		Assert.assertEquals(releaseTxResult, 1);
		sellerHelper.rabbitMqReleasePKTx(releaseFundMessage, orderId);
		// int release1 = sellerHelper.getNoOfRelease(store_order_id);
		// log.debug("Number of release: " + release1);
		// AssertJUnit.assertEquals(0, release1);

		sellerHelper.updateOrderReleaseStatus(orderId, "PK");
		sellerHelper.updateOrderLineStatus(orderId, "QD");
		sellerHelper.rabbitMqReleasePKTx(releaseFundMessage1, orderId);
		Thread.sleep(10000);
		// int release2 = sellerHelper.getNoOfRelease(store_order_id);
		// log.debug("Number of release: " + release2);
		int releaseTxResult2 = sellerHelper.getTxDBStatus1(orderId, "RELEASED");
		log.debug("DB status for release: " + releaseTxResult2);
		AssertJUnit.assertEquals(1, releaseTxResult2);

		int releaseTxResult1 = sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED");
		Assert.assertEquals(releaseTxResult1, 1,
				"Release Order Id: " + orderId + " Not available in SPS Settlement detail table");
		Assert.assertEquals(sellerHelper.getSettlementDetailFundReleaseAmount(orderId, "RELEASED"), "2.00",
				"Online Release amount:");
		log.debug("Number of releases on SPS Transaction_detail DB: " + releaseTxResult1);
		log.debug("Fund Release Amount :" + sellerHelper.getSettlementDetailFundReleaseAmount(orderId, "RELEASED"));

		SellerPaymentDP.SPS_SellerQty1ReleaseRetryOnOrderDeliverAndReturnHelper(orderId, store_order_id, OrderReleaseId,
				paymentPlanItemIdref, ppsId, ppsIdCancellation);
		sellerHelper.rabbitMqRefundTx(returnMessage, ppsIdCancellation);
		Thread.sleep(10000);
		// int refund = sellerHelper.getNoOfRefund(store_order_id);
		// log.debug("Number of Refund: " + refund);
		int refundTxResult = sellerHelper.getTxDBStatus1(orderId, "REFUNDED");
		log.debug("DB status for refund: " + refundTxResult);
		// AssertJUnit.assertEquals(1, refund);
		AssertJUnit.assertEquals(1, refundTxResult);
		int releaseTxResult3 = sellerHelper.getSettlementdetailDBStatusCount(orderId, "REFUNDED");
		Assert.assertEquals(releaseTxResult3, 1,
				"REFUND Order_id:" + orderId + " Not available in SPS Settlement detail table");
		// Assert.assertEquals(sellerHelper.getSettlementDetailRefundAmount(orderId,"CASH_ON_DELIVERY"),"-1399.00"
		// ,"COD Refund amount:");
		Assert.assertEquals(sellerHelper.getSettlementDetailRefundAmount(orderId, "ONLINE"), "-2.00",
				"Online Refund amount:");

		sellerHelper.deleteOrderRecord(orderId, ppsId, paymentPlanItemId, ppsIdCancellation, paymentPlanItemIdref);
	}

	// Create online order with seller qty1 sku 1 and release fund on PK. Check
	// split and release for that sku in citrus and Db both
	@Test(groups = {
			"Regression" }, priority = 0, dataProvider = "SPS_SellerQty1ReleaseAndReturnOnDL", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1.Create online order with seller qty1 sku1. \n2.check the split in citrus than in SPS. \n3.Release the sku and check release in both(On order Delivered). "
					+ "\n4.Return the sku and check the refund in both")
	public void SPS_SellerQty1ReleaseAndReturnOnDL(String message, String releaseFundMessage, String returnMessage,
			String orderId, String store_order_id, String OrderReleaseId, String lineId, String ppsId,
			String ppsIdCancellation, String paymentPlanItemId, String paymentPlanItemIdref)
			throws SQLException, IOException, InterruptedException {
		log.debug(orderId + " : " + ppsId + " : " + paymentPlanItemId);
		sellerHelper.rabbitMqAddTx(message, orderId);
		Thread.sleep(10000);
		// int splits = sellerHelper.getNoOfSplits(store_order_id);
		// log.debug("Number of Splits: " + splits);
		int txResult = sellerHelper.getSplitTxDBCount(orderId);
		int pgResult = sellerHelper.getSplitPgDBCount(orderId);
		log.debug("DB check Tx table splits count: " + txResult + " pg table: " + pgResult);
		// AssertJUnit.assertEquals(1, splits);
		AssertJUnit.assertEquals(1, txResult);
		AssertJUnit.assertEquals(1, pgResult);
		sellerHelper.updateOrderReleaseStatus(orderId, "PK");
		sellerHelper.updateOrderLineStatus(orderId, "QD");
		sellerHelper.rabbitMqReleasePKTx(releaseFundMessage, orderId);
		Thread.sleep(10000);

		// int release = sellerHelper.getNoOfRelease(store_order_id);
		// log.debug("Number of release: " + release);
		int releaseTxResult = sellerHelper.getTxDBStatus1(orderId, "RELEASED");
		log.debug("DB status for release: " + releaseTxResult);
		// AssertJUnit.assertEquals(1, release);
		AssertJUnit.assertEquals(1, releaseTxResult);
		int releaseTxResult1 = sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED");
		Assert.assertEquals(releaseTxResult1, 1,
				"Release Order Id: " + orderId + " Not available in SPS Settlement detail table");
		Assert.assertEquals(sellerHelper.getSettlementDetailFundReleaseAmount(orderId, "RELEASED"), "2.00",
				"Online Release amount:");
		log.debug("Number of releases on SPS Transaction_detail DB: " + releaseTxResult1);
		log.debug("Fund Release Amount :" + sellerHelper.getSettlementDetailFundReleaseAmount(orderId, "RELEASED"));

		SellerPaymentDP.SPS_SellerQty1ReleaseAndReturnOnDLHelper(orderId, store_order_id, OrderReleaseId,
				paymentPlanItemIdref, ppsId, ppsIdCancellation);
		sellerHelper.rabbitMqRefundTx(returnMessage, ppsIdCancellation);
		Thread.sleep(10000);
		// int refund = sellerHelper.getNoOfRefund(store_order_id);
		// log.debug("Number of Refund: " + refund);
		int refundTxResult = sellerHelper.getTxDBStatus1(orderId, "REFUNDED");
		log.debug("DB status for refund: " + refundTxResult);
		// AssertJUnit.assertEquals(1, refund);
		AssertJUnit.assertEquals(1, refundTxResult);
		int releaseTxResult2 = sellerHelper.getSettlementdetailDBStatusCount(orderId, "REFUNDED");
		Assert.assertEquals(releaseTxResult2, 1,
				"REFUND Order_id:" + orderId + " Not available in SPS Settlement detail table");
		// Assert.assertEquals(sellerHelper.getSettlementDetailRefundAmount(orderId,"CASH_ON_DELIVERY"),"-1399.00"
		// ,"COD Refund amount:");
		Assert.assertEquals(sellerHelper.getSettlementDetailRefundAmount(orderId, "ONLINE"), "-2.00",
				"Online Refund amount:");

		sellerHelper.deleteOrderRecord(orderId, ppsId, paymentPlanItemId, ppsIdCancellation, paymentPlanItemIdref);
	}

	// Create online order with Vectore qty2 with diff skus and cancel only one
	// item. Check split and refund for that sku in citrus and Db both along
	// with that the refund should happen for only single sku that has been
	// cancelled
	@Test(groups = {
			"Regression" }, priority = 0, dataProvider = "SPS_VectorQty2Cancel", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1.Create online order with Vector qty2 sku1. \n2.check the splits in citrus than in SPS. \n3.cancel only 1 skus. \n4.check the refund on citurs and sps both for only that sku")
	public void SPS_VectorQty2Cancel(String message, String cancellationMessage, String orderId, String store_order_id,
			String OrderReleaseId, String lineId, String ppsId, String ppsIdCancellation, String paymentPlanItemIdref,
			String paymentPlanItemId, String paymentPlanItemId2)
			throws SQLException, IOException, InterruptedException {
		log.debug(orderId + " : " + ppsId + " : " + paymentPlanItemId);
		sellerHelper.rabbitMqAddTx(message, orderId);
		Thread.sleep(10000);
		// int splits = sellerHelper.getNoOfSplits(store_order_id);
		// log.debug("Number of Splits: " + splits);
		int txResult = sellerHelper.getSplitTxDBCount(orderId);
		int pgResult = sellerHelper.getSplitPgDBCount(orderId);
		log.debug("DB check Tx table splits count: " + txResult + " pg table: " + pgResult);
		// AssertJUnit.assertEquals(2, splits);
		AssertJUnit.assertEquals(2, txResult);
		AssertJUnit.assertEquals(1, pgResult);
		SellerPaymentDP.OrderWithVectorQty2CancelHelper(orderId, store_order_id, OrderReleaseId, lineId, ppsId,
				ppsIdCancellation, paymentPlanItemIdref);
		log.debug(cancellationMessage);
		sellerHelper.rabbitMqRefundTx(cancellationMessage, ppsIdCancellation);
		Thread.sleep(10000);
		// int refund = sellerHelper.getNoOfRefund(store_order_id);
		// log.debug("Number of Refund: " + refund);
		int refundTxResult = sellerHelper.getTxDBStatus(orderId, "3827", "REFUNDED");
		log.debug("DB status for refund: " + refundTxResult);
		// AssertJUnit.assertEquals(1, refund);
		AssertJUnit.assertEquals(1, refundTxResult);
		sellerHelper.deleteFromPaymentPlanItemInstrument(paymentPlanItemId2);
		sellerHelper.deleteOrderRecord(orderId, ppsId, paymentPlanItemId, ppsIdCancellation, paymentPlanItemIdref);
	}

	// Create online order with Vectore qty2 with diff skus and cancel only one
	// item. Check split and refund for that sku in citrus and Db both along
	// with that the refund should happen for only single sku that has been
	// cancelled
	@Test(groups = {
			"Regression" }, priority = 0, dataProvider = "SPS_VectorQty2CancelBoth", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1.Create online order with Vector qty2 sku1. \n2.check the splits in citrus than in SPS. \n3.cancel only 1 skus. \n4.check the refund on citurs and sps both for only that sku")
	public void SPS_VectorQty2CancelBoth(String message, String releaseMessage, String cancellationMessage,
			String orderId, String store_order_id, String OrderReleaseId, String lineId, String ppsId,
			String ppsIdCancellation, String paymentPlanItemIdref, String paymentPlanItemId,
			String paymentPlanItemIdref1) throws SQLException, IOException, InterruptedException {
		log.debug(orderId + " : " + ppsId + " : " + paymentPlanItemId);
		sellerHelper.rabbitMqAddTx(message, orderId);
		Thread.sleep(10000);

		int txResult = sellerHelper.getSplitTxDBCount(orderId);
		int pgResult = sellerHelper.getSplitPgDBCount(orderId);
		log.debug("DB check Tx table splits count: " + txResult + " pg table: " + pgResult);

		AssertJUnit.assertEquals(2, txResult);
		AssertJUnit.assertEquals(1, pgResult);

		sellerHelper.updateOrderReleaseStatusAndPackedOn(OrderReleaseId, "PK");
		sellerHelper.updateOrderLineStatusByLineId(lineId, "QD");
		sellerHelper.rabbitMqReleasePKTx(releaseMessage, orderId);
		Thread.sleep(10000);
		Assert.assertEquals(sellerHelper.getTxDBStatus1(orderId, "RELEASED"), 1,
				"Release order id " + orderId + " not available in SPS Transaction detail table");

		SellerPaymentDP.createOnlineOrderWithVectorQty2CancelBoth(orderId, store_order_id, OrderReleaseId, lineId,
				paymentPlanItemIdref, ppsId, ppsIdCancellation, paymentPlanItemIdref1);
		log.debug(cancellationMessage);
		sellerHelper.rabbitMqRefundTx(cancellationMessage, ppsIdCancellation);
		Thread.sleep(10000);
		int refundTxResult = sellerHelper.getTxDBStatus1(orderId, "REFUNDED");
		log.debug("DB status for refund: " + refundTxResult);
		Assert.assertEquals(refundTxResult, 2,
				"Refund order id " + orderId + " not available in SPS Transaction detail table");

		sellerHelper.deleteOrderRecord(orderId, ppsId, paymentPlanItemId, ppsIdCancellation, paymentPlanItemIdref);
	}

	// Create online order with Vectore qty1 sku 1 with shipping charges and
	// cancel. Check split for shipping as well and refund for that sku in
	// citrus and Db both
	@Test(groups = { "Regression",
			"Sanity" }, priority = 0, dataProvider = "SPS_VectorQty1ShippingCancel", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1.Create online order with Vector qty1 sku1 with shipping charges. \n2.check the splits in citrus than in SPS for both sku and shipping. \n3.cancel the order. \n4.check the refund on citurs and sps both for both shipping and sku")
	public void SPS_VectorQty1ShippingCancel(String message, String cancellationMessage, String orderId,
			String store_order_id, String lineId, String ppsId, String paymentPlanItemId, String ppsIdCancellation,
			String paymentPlanItemIdref, String paymentPlanItemIdship, String paymentPlanItemIdrefship)
			throws SQLException, IOException, InterruptedException {
		log.debug(orderId + " : " + ppsId + " : " + paymentPlanItemId);
		sellerHelper.rabbitMqAddTx(message, orderId);
		Thread.sleep(10000);
		// int splits = sellerHelper.getNoOfSplits(store_order_id);
		// log.debug("Number of Splits: " + splits);
		int txResult = sellerHelper.getSplitTxDBCount(orderId);
		int pgResult = sellerHelper.getSplitPgDBCount(orderId);
		log.debug("DB check Tx table splits count: " + txResult + " pg table: " + pgResult);
		// AssertJUnit.assertEquals(2, splits);
		AssertJUnit.assertEquals(2, txResult);
		AssertJUnit.assertEquals(1, pgResult);
		log.debug(cancellationMessage);
		sellerHelper.rabbitMqRefundTx(cancellationMessage, ppsIdCancellation);
		Thread.sleep(10000);
		// int refund = sellerHelper.getNoOfRefund(store_order_id);
		// log.debug("Number of Refund: " + refund);
		int refundTxResult = sellerHelper.getTxDBStatus1(orderId, "REFUNDED");
		log.debug("DB status for refund: " + refundTxResult);
		// AssertJUnit.assertEquals(2, refund);
		AssertJUnit.assertEquals(2, refundTxResult);
		sellerHelper.deleteFromPaymentPlanItemInstrument(paymentPlanItemIdship);
		sellerHelper.deleteFromPaymentPlanItemInstrument(paymentPlanItemIdrefship);
		sellerHelper.deleteOrderRecord(orderId, ppsId, paymentPlanItemId, ppsIdCancellation, paymentPlanItemIdref);

	}

	@Test(groups = { "Regression",
			"Sanity" }, priority = 0, dataProvider = "SPS_DifferentSellersMultiReleaseSkuWithShippingChrgs", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1.Create online order with Vector qty1 sku1 with shipping charges. \n2.check the splits in citrus than in SPS for both sku and shipping. \n3.cancel the order. \n4.check the refund on citurs and sps both for both shipping and sku")
	public void SPS_DifferentSellersMultiReleaseSkuWithShippingChrgs(String orderId, String store_order_id,
			String message, String lineId, String ppsId, String paymentPlanItemId, String ppsIdCancellation,
			String paymentPlanItemIdref, String paymentPlanItemIdship, String paymentPlanItemIdrefship,
			String releaseFundMessage, String returnMessage, String releaseMessage1)
			throws SQLException, IOException, InterruptedException {
		log.debug(orderId + " : " + ppsId + " : " + paymentPlanItemId);
		sellerHelper.rabbitMqAddTx(message, orderId);
		Thread.sleep(10000);
		sellerHelper.updateOrderReleaseStatus(orderId, "DL");
		sellerHelper.updateOrderLineStatus(orderId, "QD");

		sellerHelper.rabbitMqReleasePKTx(releaseMessage1, orderId);
		log.debug("Wait for 10Sec more to get order details in settlement detail table....");
		Thread.sleep(10000);
		// int splits = sellerHelper.getNoOfSplits(store_order_id); // Call
		// getNoOfSplit function which will check number of splits happened on
		// Citrus and will return the value
		// log.debug("Number of Splits: "+splits);
		// int txResult = sellerHelper.getSplitTxDBCount(orderId);
		// int pgResult = sellerHelper.getSplitPgDBCount(orderId);
		//
		// log.debug("DB check Tx table splits count: "+txResult+" pg
		// table: "+pgResult);
		// //AssertJUnit.assertEquals(3, splits);
		// AssertJUnit.assertEquals(3, txResult);
		// AssertJUnit.assertEquals(1, pgResult);

		int releaseTxResult = sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED");
		Assert.assertEquals(releaseTxResult, 2,
				"Release Order id:" + orderId + "  Not available in SPS Settlement detail table");

		sellerHelper.rabbitMqReleasePKTx(releaseFundMessage, orderId);

		log.debug("Wait for 10Sec more to get order details in settlement detail table....");
		Thread.sleep(10000);

		int releaseTxResult1 = sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED");
		Assert.assertEquals(releaseTxResult1, 3,
				"Release Order id:" + orderId + "  Not available in SPS Settlement detail table");

		SellerPaymentDP.SPS_DifferentSellersMultiReleaseSkuWithShippingChrgsReturnHelper(orderId, store_order_id,
				paymentPlanItemIdref, ppsId, ppsIdCancellation);
		sellerHelper.rabbitMqRefundTx(returnMessage, ppsIdCancellation);

		log.debug("Wait for 10Sec more to get order details in settlement detail table....");
		Thread.sleep(10000);

		int releaseTxResult2 = sellerHelper.getSettlementdetailDBStatusCount(orderId, "REFUNDED");
		Assert.assertEquals(releaseTxResult2, 1,
				"Return Order_id:" + orderId + " Not available in SPS Settlement detail table");
		Assert.assertEquals(sellerHelper.getSettlementDetailRefundAmount(orderId, "ONLINE"), "-2.00",
				"COD Refund amount:");
		// Assert.assertEquals(sellerHelper.getSettlementDetailRefundAmount(orderId,"ONLINE"),"-1000.00"
		// ,"Wallet Refund amount:");
		//

		// sellerHelper.deleteFromPaymentPlanItemInstrument(paymentPlanItemId);
		// sellerHelper.deleteOrderRecord(orderId, ppsId, paymentPlanItemId);
		sellerHelper.deleteFromOrders(orderId);
		sellerHelper.deleteFromOrdersRelease(orderId);
		sellerHelper.deleteFromOrdersLine(orderId);

	}

	@Test(groups = { "Regression",
			"Sanity" }, priority = 0, dataProvider = "SPS_DifferentSellersMultiReleaseSkuWithShippingChrgs", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1.Create online order with Vector qty1 sku1 with shipping charges. \n2.check the splits in citrus than in SPS for both sku and shipping. \n3.cancel the order. \n4.check the refund on citurs and sps both for both shipping and sku")
	public void SPS_DifferentSellersMultiReleaseSkuWithShippingChrgs1(String orderId, String store_order_id,
			String message, String lineId, String ppsId, String paymentPlanItemId, String ppsIdCancellation,
			String paymentPlanItemIdref, String paymentPlanItemIdship, String paymentPlanItemIdrefship,
			String releaseFundMessage, String returnMessage, String releaseMessage1)
			throws SQLException, IOException, InterruptedException {
		log.debug(orderId + " : " + ppsId + " : " + paymentPlanItemId);
		sellerHelper.rabbitMqAddTx(message, orderId);
		Thread.sleep(10000);
		sellerHelper.updateOrderReleaseStatus(orderId, "DL");
		sellerHelper.updateOrderLineStatus(orderId, "QD");

		sellerHelper.rabbitMqReleasePKTx(releaseFundMessage, orderId);
		Thread.sleep(10000);
		// int splits = sellerHelper.getNoOfSplits(store_order_id); // Call
		// getNoOfSplit function which will check number of splits happened on
		// Citrus and will return the value
		// log.debug("Number of Splits: "+splits);
		log.debug("Wait for 10Sec more to get order details in settlement detail table....");
		Thread.sleep(10000);
		int txResult = sellerHelper.getSplitTxDBCount(orderId);
		int pgResult = sellerHelper.getSplitPgDBCount(orderId);

		log.debug("DB check Tx table splits count: " + txResult + " pg table: " + pgResult);
		// AssertJUnit.assertEquals(3, splits);
		AssertJUnit.assertEquals(3, txResult);
		AssertJUnit.assertEquals(1, pgResult);

		int releaseTxResult = sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED");
		Assert.assertEquals(releaseTxResult, 2,
				"Release Order id:" + orderId + "  Not available in SPS Settlement detail table");

		sellerHelper.rabbitMqReleasePKTx(releaseMessage1, orderId);
		log.debug("Wait for 10Sec more to get order details in settlement detail table....");
		Thread.sleep(10000);

		int releaseTxResult1 = sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED");
		Assert.assertEquals(releaseTxResult1, 3,
				"Release Order id:" + orderId + "  Not available in SPS Settlement detail table");

		SellerPaymentDP.SPS_DifferentSellersMultiReleaseSkuWithShippingChrgsReturnHelper(orderId, store_order_id,
				paymentPlanItemIdref, ppsId, ppsIdCancellation);
		sellerHelper.rabbitMqRefundTx(returnMessage, ppsIdCancellation);

		log.debug("Wait for 10Sec more to get order details in settlement detail table....");
		Thread.sleep(10000);

		int releaseTxResult2 = sellerHelper.getSettlementdetailDBStatusCount(orderId, "REFUNDED");
		Assert.assertEquals(releaseTxResult2, 1,
				"Return Order_id:" + orderId + " Not available in SPS Settlement detail table");
		Assert.assertEquals(sellerHelper.getSettlementDetailRefundAmount(orderId, "ONLINE"), "-2.00",
				"COD Refund amount:");
		// Assert.assertEquals(sellerHelper.getSettlementDetailRefundAmount(orderId,"ONLINE"),"-1000.00"
		// ,"Wallet Refund amount:");
		//

		// sellerHelper.deleteFromPaymentPlanItemInstrument(paymentPlanItemId);
		// sellerHelper.deleteOrderRecord(orderId, ppsId, paymentPlanItemId);
		sellerHelper.deleteFromOrders(orderId);
		sellerHelper.deleteFromOrdersRelease(orderId);
		sellerHelper.deleteFromOrdersLine(orderId);

	}

	@Test(groups = { "Regression",
			"Sanity" }, priority = 0, dataProvider = "SPS_DifferentSellersMultiRelease1CancelSkuWithShippingChrgs", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1.Create online order with Vector qty1 sku1 with shipping charges. \n2.check the splits in citrus than in SPS for both sku and shipping. \n3.cancel the order. \n4.check the refund on citurs and sps both for both shipping and sku")
	public void SPS_DifferentSellersMultiRelease1CancelSkuWithShippingChrgs(String orderId, String store_order_id,
			String message, String lineId, String ppsId, String paymentPlanItemId, String ppsIdCancellation,
			String paymentPlanItemIdref, String paymentPlanItemIdship, String paymentPlanItemIdrefship,
			String releaseFundMessage, String returnMessage, String releaseMessage1, String cancellationMessage,
			String ppsIdLineCancellation, String paymentPlanItemIdCancel, String OrderReleaseId) throws Exception {
		log.debug(orderId + " : " + ppsId + " : " + paymentPlanItemId);
		sellerHelper.rabbitMqAddTx(message, orderId);
		Thread.sleep(10000);
		sellerHelper.updateOrderReleaseStatus(orderId, "PK");
		sellerHelper.updateOrderLineStatus(orderId, "QD");

		sellerHelper.rabbitMqReleasePKTx(releaseMessage1, orderId);
		log.debug("Wait for 10Sec more to get order details in settlement detail table....");
		Thread.sleep(10000);

		int releaseTxResult = sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED");
		Assert.assertEquals(releaseTxResult, 2,
				"Release Order id:" + orderId + "  Not available in SPS Settlement detail table");

		SellerPaymentDP.SPS_DifferentSellersMultiRelease1CancelSkuWithShippingChrgsLineCancellation(lineId, orderId,
				store_order_id, OrderReleaseId, ppsId, ppsIdLineCancellation, paymentPlanItemIdCancel,
				paymentPlanItemIdrefship);
		sellerHelper.rabbitMqRefundTx(cancellationMessage, ppsIdLineCancellation);
		log.debug("Wait for 10Sec more to get order details in settlement detail table....");
		Thread.sleep(10000);
		int releaseTxResult1 = sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED");
		int releaseTxRefunded = sellerHelper.getSettlementdetailDBStatusCount(orderId, "REFUNDED");
		int txnrefundedskus = sellerHelper.getTxDBStatusCount(orderId, "REFUNDED");

		Assert.assertEquals(txnrefundedskus, 1,
				"Transaction db Refund Order id:" + orderId + "  Not available in SPS Settlement detail table");

		Assert.assertEquals(releaseTxResult1, 2,
				"Release Order id:" + orderId + "  Not available in SPS Settlement detail table");
		Assert.assertEquals(releaseTxRefunded, 0,
				"Refund Order id:" + orderId + "  Not available in SPS Settlement detail table");

		//
		// SellerPaymentServiceDP.createCod_Shipping_OrderQty1ReturnHelper(orderId,
		// paymentPlanItemIdref, ppsId, ppsIdCancellation);
		// sellerHelper.rabbitMqRefundTx(returnMessage, ppsIdCancellation);
		//
		// log.debug("Wait for 10Sec more to get order details in
		// settlement detail table....");
		// Thread.sleep(10000);
		//
		// int releaseTxResult2 =
		// sellerHelper.getSettlementdetailDBStatusCount(orderId, "REFUNDED");
		// Assert.assertEquals(releaseTxResult2,1 ,"Return Order_id:"+orderId+"
		// Not available in SPS Settlement detail table");
		// Assert.assertEquals(sellerHelper.getSettlementDetailRefundAmount(orderId,"CASH_ON_DELIVERY"),"-2.00"
		// ,"COD Refund amount:");
		// Assert.assertEquals(sellerHelper.getSettlementDetailRefundAmount(orderId,"ONLINE"),"-1000.00"
		// ,"Wallet Refund amount:");
		//

		// sellerHelper.deleteFromPaymentPlanItemInstrument(paymentPlanItemId);
		// sellerHelper.deleteOrderRecord(orderId, ppsId, paymentPlanItemId);
		sellerHelper.deleteFromOrders(orderId);
		sellerHelper.deleteFromOrdersRelease(orderId);
		sellerHelper.deleteFromOrdersLine(orderId);

	}

	// Create online order with Vectore qty1 sku 1 with SDD charges. Check split
	// for SDD as well release and refund for that sku in citrus and Db both
	@Test(groups = { "Regression",
			"Sanity" }, priority = 0, dataProvider = "SPS_VectorQty1SDDReleaseRefund", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1.Create online order with Vector qty1 sku1 with SDD charges. \n2.check the splits in citrus than in SPS for both sku and SDD. \n3.release the order and check the release for both. "
					+ "\n4.return the sku and check the refund happning for only sku not for SDD")
	public void SPS_VectorQty1SDDReleaseRefund(String message, String releaseMessage, String refundMessage,
			String orderId, String store_order_id, String lineId, String ppsId, String paymentPlanItemId,
			String ppsIdCancellation, String paymentPlanItemIdref, String paymentPlanItemIdship)
			throws SQLException, IOException, InterruptedException {
		log.debug(orderId + " : " + ppsId + " : " + paymentPlanItemId);
		sellerHelper.rabbitMqAddTx(message, orderId);
		Thread.sleep(10000);
		// int splits = sellerHelper.getNoOfSplits(store_order_id);
		// log.debug("Number of Splits: " + splits);
		int txResult = sellerHelper.getSplitTxDBCount(orderId);
		int pgResult = sellerHelper.getSplitPgDBCount(orderId);
		log.debug("DB check Tx table splits count: " + txResult + " pg table: " + pgResult);
		// AssertJUnit.assertEquals(2, splits);
		AssertJUnit.assertEquals(2, txResult);
		AssertJUnit.assertEquals(1, pgResult);

		sellerHelper.updateOrderLineStatus(orderId, "QD");
		sellerHelper.updateOrderReleaseStatus(orderId, "PK");
		sellerHelper.rabbitMqReleasePKTx(releaseMessage, orderId);
		Thread.sleep(10000);
		// int release = sellerHelper.getNoOfRelease(store_order_id);
		// log.debug("Number of release: " + release);
		int releaseTxResult = sellerHelper.getTxDBStatus1(orderId, "RELEASED");
		log.debug("DB status for release: " + releaseTxResult);
		// AssertJUnit.assertEquals(2, release);
		AssertJUnit.assertEquals(2, releaseTxResult);

		int releaseTxResult1 = sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED");
		Assert.assertEquals(releaseTxResult1, 2,
				"Order Id: " + orderId + " Not available in SPS Settlement detail table");

		log.debug("Number of releases on SPS Transaction_detail DB: " + releaseTxResult1);
		log.debug("Fund Release Amount :" + sellerHelper.getSettlementDetailFundReleaseAmount(orderId, "RELEASED"));

		SellerPaymentDP.SPS_VectorQty1SDDReleaseRefundHelper(lineId, orderId, store_order_id, ppsId, ppsIdCancellation,
				paymentPlanItemIdref);
		sellerHelper.rabbitMqRefundTx(refundMessage, ppsIdCancellation);
		Thread.sleep(10000);
		// int refund = sellerHelper.getNoOfRefund(store_order_id);
		// log.debug("Number of Refund: " + refund);
		int refundTxResult = sellerHelper.getTxDBStatus(orderId, "3827", "REFUNDED");
		log.debug("DB status for refund: " + refundTxResult);
		// AssertJUnit.assertEquals(1, refund);
		AssertJUnit.assertEquals(1, refundTxResult);

		int releaseTxResult2 = sellerHelper.getSettlementdetailDBStatusCount(orderId, "REFUNDED");
		Assert.assertEquals(releaseTxResult2, 1,
				"REFUND Order_id:" + orderId + " Not available in SPS Settlement detail table");
		// Assert.assertEquals(sellerHelper.getSettlementDetailRefundAmount(orderId,"CASH_ON_DELIVERY"),"-1399.00"
		// ,"COD Refund amount:");
		Assert.assertEquals(sellerHelper.getSettlementDetailRefundAmount(orderId, "ONLINE"), "-2399.00",
				"Online Refund amount:");

		sellerHelper.deleteFromPaymentPlanItemInstrument(paymentPlanItemIdship);
		sellerHelper.deleteOrderRecord(orderId, ppsId, paymentPlanItemId, ppsIdCancellation, paymentPlanItemIdref);
	}

	// Create online order with Vectore qty1 sku 1 with SDD charges. Check split
	// for SDD as well release and mark sdd breach for that sku and check refun
	// for SDD charges.
	@Test(groups = {
			"Regression" }, priority = 0, dataProvider = "SPS_VectorQty1SDDBreach", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1.Create online order with Vector qty1 sku1 with SDD charges. \n2.check the splits in citrus than in SPS for both sku and SDD. \n3.release the order and check the release for both. "
					+ "\n4.Mark SDD breach and check the refund happning only for SDD charges")
	public void SPS_VectorQty1SDDBreach(String message, String releaseMessage, String refundMessage, String orderId,
			String orderReleaseId, String store_order_id, String lineId, String ppsId, String paymentPlanItemId,
			String ppsIdCancellation, String paymentPlanItemIdref, String paymentPlanItemIdship)
			throws SQLException, IOException, InterruptedException {
		log.debug(orderId + " : " + ppsId + " : " + paymentPlanItemId);
		sellerHelper.rabbitMqAddTx(message, orderId);
		Thread.sleep(10000);
		// int splits = sellerHelper.getNoOfSplits(store_order_id);
		// log.debug("Number of Splits: " + splits);
		int txResult = sellerHelper.getSplitTxDBCount(orderId);
		int pgResult = sellerHelper.getSplitPgDBCount(orderId);
		log.debug("DB check Tx table splits count: " + txResult + " pg table: " + pgResult);
		// AssertJUnit.assertEquals(2, splits);
		AssertJUnit.assertEquals(2, txResult);
		AssertJUnit.assertEquals(1, pgResult);

		sellerHelper.updateOrderLineStatus(orderId, "QD");
		sellerHelper.updateOrderReleaseStatusAndPackedOn(orderId, "PK");
		sellerHelper.rabbitMqReleasePKTx(releaseMessage, orderId);
		Thread.sleep(10000);
		// int release = sellerHelper.getNoOfRelease(store_order_id);
		// log.debug("Number of release: " + release);
		int releaseTxResult = sellerHelper.getTxDBStatus1(orderId, "RELEASED");
		log.debug("DB status for release: " + releaseTxResult);
		// AssertJUnit.assertEquals(2, release);
		AssertJUnit.assertEquals(2, releaseTxResult);
		int releaseTxResult1 = sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED");
		Assert.assertEquals(releaseTxResult1, 2,
				"Order Id: " + orderId + " Not available in SPS Settlement detail table");
		// Assert.assertEquals(sellerHelper.getSettlementDetailFundReleaseAmount(orderId,"RELEASED"),"799.00"
		// ,"Online Release amount:");

		log.debug("Number of releases on SPS Transaction_detail DB: " + releaseTxResult1);
		log.debug("Fund Release Amount :" + sellerHelper.getSettlementDetailFundReleaseAmount(orderId, "RELEASED"));

		SellerPaymentDP.SPS_VectorQty1SDDBreachHelper(lineId, orderId, orderReleaseId, store_order_id, ppsId,
				ppsIdCancellation, paymentPlanItemIdref);
		sellerHelper.rabbitMqRefundTx(refundMessage, ppsIdCancellation);
		Thread.sleep(10000);

		Assert.assertEquals(sellerHelper.getSettlementdetailDBStatusCount(orderId, "REFUNDED"), 1,
				"Refund Shipping chrgs Order Id: " + orderId + " Not available in SPS Settlement detail table");
		Assert.assertEquals(sellerHelper.getSettlementDetailRefundAmount(orderId, "ONLINE"), "-25.00",
				"Refund Shipping chrgs Order Id: " + orderId + " Not available in SPS Settlement detail table");

		sellerHelper.deleteFromPaymentPlanItemInstrument(paymentPlanItemIdship);
		sellerHelper.deleteOrderRecord(orderId, ppsId, paymentPlanItemId, ppsIdCancellation, paymentPlanItemIdref);
	}

	// Create online order with Vectore qty3 sku 1 with Shipping charges. Check
	// split for Ship as well on release of firts sku release in citrus and Db
	// both.
	@Test(groups = {
			"Regression" }, priority = 0, dataProvider = "SPS_VectorQty3ShipRelease", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1.Create online order with Vector qty3 sku1 with Shipping charges. \n2.check the splits in citrus than in SPS for all skus and shipping. \n3.release the first sku and check the release that shipping also release with first sku and remaining reaches to PG settlement. "
					+ "\n4.return the sku and check the refund happning for only sku not for SDD")
	public void SPS_VectorQty3ShipRelease(String message, String releaseMessage, String orderId, String OrderReleaseId,
			String store_order_id, String lineId, String ppsId, String paymentPlanItemId, String paymentPlanItemIdship)
			throws SQLException, IOException, InterruptedException {
		log.debug(orderId + " : " + ppsId + " : " + paymentPlanItemId);
		sellerHelper.rabbitMqAddTx(message, orderId);
		Thread.sleep(10000);
		// int splits = sellerHelper.getNoOfSplits(store_order_id);
		// log.debug("Number of Splits: " + splits);
		int txResult = sellerHelper.getSplitTxDBCount(orderId);
		int pgResult = sellerHelper.getSplitPgDBCount(orderId);
		log.debug("DB check Tx table splits count: " + txResult + " pg table: " + pgResult);
		// AssertJUnit.assertEquals(4, splits);
		AssertJUnit.assertEquals(4, txResult);
		AssertJUnit.assertEquals(1, pgResult);

		sellerHelper.updateOrderReleaseStatusAndPackedOn(OrderReleaseId, "PK");
		sellerHelper.rabbitMqReleasePKTx(releaseMessage, orderId);
		Thread.sleep(10000);
		// int release = sellerHelper.getNoOfRelease(store_order_id);
		// log.debug("Number of release: " + release);
		// String releaseTxResult = sellerHelper.getTxDBStatus(orderId);
		// log.debug("DB status for release: "+releaseTxResult);
		int pgtableResult = sellerHelper.getTxDBStatusCount(orderId, "PGSETTLE");
		log.debug("DB status for release: " + pgtableResult);
		// AssertJUnit.assertEquals(2, release);
		AssertJUnit.assertEquals(2, pgtableResult);
		// AssertJUnit.assertEquals("RELEASED", releaseTxResult);
		int releaseTxResult1 = sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED");
		Assert.assertEquals(releaseTxResult1, 2,
				"Order Id: " + orderId + " Not available in SPS Settlement detail table");
		// Assert.assertEquals(sellerHelper.getSettlementDetailFundReleaseAmount(orderId,"RELEASED"),"799.00"
		// ,"Online Release amount:");

		log.debug("Number of releases on SPS Transaction_detail DB: " + releaseTxResult1);
		log.debug("Fund Release Amount :" + sellerHelper.getSettlementDetailFundReleaseAmount(orderId, "RELEASED"));

		sellerHelper.deleteFromPaymentPlanItemInstrument(paymentPlanItemIdship);
		sellerHelper.deleteOrderRecord(orderId, ppsId, paymentPlanItemId);
	}

	// Create online order with Vectore qty1 sku 1 with shipping charges less
	// than 1 . Check split for shipping should not happen.
	@Test(groups = {
			"Regression" }, priority = 0, dataProvider = "SPS_VectorQty1ShippingLessThan1", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1. Create online order with Vector qty1 sku1 with shipping charges less than 2.\n2.check the splits in citrus than in SPS that split should only happen for sku not for shipping"
					+ "\n3. Validate the tx amount included only the sku whose split happened and validate the split amount too")
	public void SPS_VectorQty1ShippingLessThan1(String orderId, String store_order_id, String ppsId,
			String paymentPlanItemId, String message, String lineId, String paymentPlanItemIdship)
			throws SQLException, IOException, InterruptedException {
		log.debug(orderId + " : " + ppsId + " : " + paymentPlanItemId);
		sellerHelper.rabbitMqAddTx(message, orderId);
		Thread.sleep(10000);
		// List splitsTxAmountSplitAmount =
		// sellerHelper.getNoOfSplitsAndSplitAmountAndTxAmount(store_order_id);
		// log.debug("Number of Splits: " + splitsTxAmountSplitAmount.get(0));
		int txResult = sellerHelper.getSplitTxDBCount(orderId);
		int pgResult = sellerHelper.getSplitPgDBCount(orderId);
		log.debug("DB check Tx table splits count: " + txResult + " pg table: " + pgResult);
		// log.debug("Tx amount: " + splitsTxAmountSplitAmount.get(1));
		// log.debug("Split amount: " + splitsTxAmountSplitAmount.get(2));
		// AssertJUnit.assertEquals(1, Integer.parseInt((String)
		// splitsTxAmountSplitAmount.get(0)));
		// AssertJUnit.assertEquals(2, splitsTxAmountSplitAmount.get(1));
		// AssertJUnit.assertEquals(2, splitsTxAmountSplitAmount.get(2));
		AssertJUnit.assertEquals(1, txResult);
		AssertJUnit.assertEquals(1, pgResult);
		sellerHelper.deleteFromPaymentPlanItemInstrument(paymentPlanItemIdship);
		sellerHelper.deleteOrderRecord(orderId, ppsId, paymentPlanItemId);
	}

	// Create order with Vector and JIT (1-1 qty) and cancel both
	@Test(groups = {
			"Regression" }, priority = 0, dataProvider = "SPS_VectorAndJITCancelboth", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1.Create online order with Vector and seller qty1 sku1. \n2.check the splits in citrus than in SPS for both sku. \n3.cancel both skus. \n4.check the refund on citurs and sps both for both sku")
	public void SPS_VectorAndJITCancelboth(String message, String cancellationMessage, String orderId,
			String store_order_id, String OrderReleaseId, String OrderReleaseId1, String lineId, String lineId1,
			String releaseMessage, String releaseMessage1, String ppsId, String ppsIdCancellation,
			String ppsIdCancellation2, String paymentPlanItemIdref, String paymentPlanItemIdref2,
			String paymentPlanItemId, String paymentPlanItemId2)
			throws SQLException, IOException, InterruptedException {
		log.debug(orderId + " : " + ppsId + " : " + paymentPlanItemId);
		sellerHelper.rabbitMqAddTx(message, orderId);
		Thread.sleep(10000);

		int txResult = sellerHelper.getSplitTxDBCount(orderId);
		int pgResult = sellerHelper.getSplitPgDBCount(orderId);
		log.debug("DB check Tx table splits count: " + txResult + " pg table: " + pgResult);

		AssertJUnit.assertEquals(2, txResult);
		AssertJUnit.assertEquals(1, pgResult);

		sellerHelper.updateOrderReleaseStatusAndPackedOn(OrderReleaseId, "PK");
		sellerHelper.updateOrderLineStatusByLineId(lineId, "QD");
		sellerHelper.rabbitMqReleasePKTx(releaseMessage, orderId);
		Thread.sleep(10000);
		Assert.assertEquals(sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED"), 1,
				"Order Id: " + orderId + " Not available in SPS Settlement detail table");

		sellerHelper.updateOrderReleaseStatusAndPackedOn(OrderReleaseId1, "PK");
		sellerHelper.updateOrderLineStatusByLineId(lineId1, "QD");
		sellerHelper.rabbitMqReleasePKTx(releaseMessage1, orderId);
		Thread.sleep(10000);
		Assert.assertEquals(sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED"), 2,
				"Order Id: " + orderId + " Not available in SPS Settlement detail table");

		SellerPaymentDP.OrderWithVectorAndJITCancelbothHelper(orderId, store_order_id, OrderReleaseId, OrderReleaseId1,
				lineId, lineId1, ppsId, ppsIdCancellation, ppsIdCancellation2, paymentPlanItemIdref,
				paymentPlanItemIdref2);
		log.debug(cancellationMessage);
		sellerHelper.rabbitMqRefundTx(cancellationMessage, ppsIdCancellation);
		sellerHelper.rabbitMqRefundTx(cancellationMessage, ppsIdCancellation2);
		Thread.sleep(10000);

		int refundTxResult = sellerHelper.getTxDBStatus(orderId, "3831", "REFUNDED");
		int refundTxResult1 = sellerHelper.getTxDBStatus(orderId, "1152953", "REFUNDED");
		log.debug("DB status for refund: " + refundTxResult + " " + refundTxResult1);

		AssertJUnit.assertEquals(1, refundTxResult);
		AssertJUnit.assertEquals(1, refundTxResult);
		sellerHelper.deleteFromPaymentPlanItemInstrument(paymentPlanItemId2);
		sellerHelper.deleteFromPaymentPlanItemInstrument(paymentPlanItemIdref2);
		sellerHelper.deleteOrderRecord(orderId, ppsId, paymentPlanItemId, ppsIdCancellation, paymentPlanItemIdref);
	}

	@Test(groups = {
			"Regression" }, priority = 0, dataProvider = "SPS_VectorQty1GiftWrapShippingCancel", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1.Create online order with Vector qty1 sku1 with shipping charges + gift wrap charges. \n2.check the splits in citrus than in SPS for sku, shipping and for GW. \n3.cancel the order. \n4.check the refund on citurs and sps both for all 3")
	public void SPS_VectorQty1GiftWrapShippingCancel(String message, String cancellationMessage, String orderId,
			String store_order_id, String lineId, String ppsId, String paymentPlanItemId, String paymentPlanItemIdship,
			String paymentPlanItemIdGW, String ppsIdCancellation, String paymentPlanItemIdref,
			String paymentPlanItemIdrefship, String paymentPlanItemIdrefGR)
			throws SQLException, IOException, InterruptedException {
		log.debug(orderId + " : " + ppsId + " : " + paymentPlanItemId);
		sellerHelper.rabbitMqAddTx(message, orderId);
		Thread.sleep(10000);
		// int splits = sellerHelper.getNoOfSplits(store_order_id);
		// log.debug("Number of Splits: " + splits);
		int txResult = sellerHelper.getSplitTxDBCount(orderId);
		int pgResult = sellerHelper.getSplitPgDBCount(orderId);
		log.debug("DB check Tx table splits count: " + txResult + " pg table: " + pgResult);
		// AssertJUnit.assertEquals(3, splits);
		AssertJUnit.assertEquals(3, txResult);
		AssertJUnit.assertEquals(1, pgResult);
		log.debug(cancellationMessage);
		sellerHelper.rabbitMqRefundTx(cancellationMessage, ppsIdCancellation);
		Thread.sleep(10000);
		// int refund = sellerHelper.getNoOfRefund(store_order_id);
		// log.debug("Number of Refund: " + refund);
		int refundTxResult = sellerHelper.getTxDBStatus1(orderId, "REFUNDED");
		log.debug("DB status for refund: " + refundTxResult);
		// AssertJUnit.assertEquals(3, refund);
		Assert.assertEquals(refundTxResult, 3,
				"SPS Refund orderId : " + orderId + " Not found in Transaction details table");
		sellerHelper.deleteFromPaymentPlanItemInstrument(paymentPlanItemIdship);
		sellerHelper.deleteFromPaymentPlanItemInstrument(paymentPlanItemIdGW);
		sellerHelper.deleteFromPaymentPlanItemInstrument(paymentPlanItemIdrefship);
		sellerHelper.deleteFromPaymentPlanItemInstrument(paymentPlanItemIdrefGR);
		sellerHelper.deleteOrderRecord(orderId, ppsId, paymentPlanItemId, ppsIdCancellation, paymentPlanItemIdref);
	}

	@Test(groups = {
			"Regression" }, priority = 0, dataProvider = "SPS_VectorQty1GiftWrapShippingReleaseRefund", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1.Create online order with Vector qty1 sku1 with shipping charges + gift wrap charges. \n2.check the splits in citrus than in SPS for sku, shipping and for GW. "
					+ "\n3.release the order and check that all 3 got released. \n4.return and check the refund on citurs and sps that refund happens only for sku")
	public void SPS_VectorQty1GiftWrapShippingReleaseRefund(String message, String releaseMessage, String orderId,
			String OrderReleaseId, String store_order_id, String lineId, String ppsId, String paymentPlanItemId,
			String paymentPlanItemIdship, String paymentPlanItemIdGW, String ppsIdCancellation,
			String paymentPlanItemIdref, String refundMessage) throws SQLException, IOException, InterruptedException {
		log.debug(orderId + " : " + ppsId + " : " + paymentPlanItemId);
		sellerHelper.rabbitMqAddTx(message, orderId);
		Thread.sleep(10000);
		// int splits = sellerHelper.getNoOfSplits(store_order_id);
		// log.debug("Number of Splits: " + splits);
		int txResult = sellerHelper.getSplitTxDBCount(orderId);
		int pgResult = sellerHelper.getSplitPgDBCount(orderId);
		log.debug("DB check Tx table splits count: " + txResult + " pg table: " + pgResult);
		// AssertJUnit.assertEquals(3, splits);
		AssertJUnit.assertEquals(3, txResult);
		AssertJUnit.assertEquals(1, pgResult);
		sellerHelper.updateOrderReleaseStatusAndPackedOn(OrderReleaseId, "PK");
		sellerHelper.updateOrderLineStatus(orderId, "QD");
		sellerHelper.rabbitMqReleasePKTx(releaseMessage, orderId);
		Thread.sleep(10000);
		// int release = sellerHelper.getNoOfRelease(store_order_id);
		// log.debug("Number of release: " + release);
		int releaseTxResult = sellerHelper.getTxDBStatus1(orderId, "RELEASED");
		log.debug("DB status for release: " + releaseTxResult);
		// AssertJUnit.assertEquals(3, release);
		AssertJUnit.assertEquals(3, releaseTxResult);
		int releaseTxResult1 = sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED");
		Assert.assertEquals(releaseTxResult1, 3,
				"Release Order Id: " + orderId + " Not available in SPS Settlement detail table");

		log.debug("Number of releases on SPS Transaction_detail DB: " + releaseTxResult1);
		log.debug("Fund Release Amount :" + sellerHelper.getSettlementDetailFundReleaseAmount(orderId, "RELEASED"));

		SellerPaymentDP.SPS_VectorQty1GiftWrapShippingReleaseRefundHelper(lineId, orderId, store_order_id, ppsId,
				ppsIdCancellation, paymentPlanItemIdref);
		sellerHelper.rabbitMqRefundTx(refundMessage, ppsIdCancellation);
		Thread.sleep(10000);
		// int refund = sellerHelper.getNoOfRefund(store_order_id);
		// log.debug("Number of Refund: " + refund);
		int refundTxResult = sellerHelper.getTxDBStatus(orderId, "3831", "REFUNDED");
		log.debug("DB status for refund: " + refundTxResult);
		// AssertJUnit.assertEquals(1, refund);
		AssertJUnit.assertEquals(1, refundTxResult);
		int releaseTxResult2 = sellerHelper.getSettlementdetailDBStatusCount(orderId, "REFUNDED");
		Assert.assertEquals(releaseTxResult2, 1,
				"REFUND Order_id:" + orderId + " Not available in SPS Settlement detail table");
		Assert.assertEquals(sellerHelper.getSettlementDetailRefundAmountByItemRef(store_order_id + "_3831_0", "ONLINE"),
				"-799.65", "Online Refund amount:");

		sellerHelper.deleteFromPaymentPlanItemInstrument(paymentPlanItemIdship);
		sellerHelper.deleteFromPaymentPlanItemInstrument(paymentPlanItemIdGW);
		sellerHelper.deleteOrderRecord(orderId, ppsId, paymentPlanItemId, ppsIdCancellation, paymentPlanItemIdref);
	}

	// Create online order with seller qty1 sku 1 and cancel. Check split and
	// refund for that sku in citrus and Db both
	@Test(groups = { "Sanity",
			"Regression" }, priority = 0, dataProvider = "SPS_SellerQty1ValidateFeeAmountCancel", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1.Create online order with seller qty1 sku1. \n2.check the split in citrus than in SPS. \n3.Validate the fee amount in citrus")
	public void SPS_SellerQty1ValidateFeeAmountCancel(String message, String cancellationMessage, String orderId,
			String store_order_id, String lineId, String ppsId, String paymentPlanItemId, String ppsIdCancellation,
			String paymentPlanItemIdref) throws SQLException, IOException, InterruptedException {
		log.debug(orderId + " : " + ppsId + " : " + paymentPlanItemId);
		sellerHelper.rabbitMqAddTx(message, orderId);
		Thread.sleep(10000);
		// java.util.List feeAmountAndSplits =
		// sellerHelper.getNoOfSplitsAndFeeAmount(store_order_id);
		// log.debug("Split and fee amount: " + feeAmountAndSplits);
		BigDecimal margin = sellerHelper.getSellerMargin(orderId);
		Double x = 2.00 * (margin.doubleValue() / 100);
		double marginCalc = Math.round(x * 100.0) / 100.0;
		// log.debug("Number of Splits: " + feeAmountAndSplits.get(0));
		// String feeAmount = "" + feeAmountAndSplits.get(1);
		// log.debug("fee amount: " + feeAmount);
		log.debug("margin calculation: " + marginCalc);
		int txResult = sellerHelper.getSplitTxDBCount(orderId);
		int pgResult = sellerHelper.getSplitPgDBCount(orderId);
		log.debug("DB check Tx table splits count: " + txResult + " pg table: " + pgResult);
		// AssertJUnit.assertEquals(1, Integer.parseInt((String)
		// feeAmountAndSplits.get(0)));
		AssertJUnit.assertEquals(1, txResult);
		AssertJUnit.assertEquals(1, pgResult);
		// AssertJUnit.assertEquals(marginCalc, feeAmountAndSplits.get(1));

		log.debug(cancellationMessage);
		sellerHelper.rabbitMqRefundTx(cancellationMessage, ppsIdCancellation);
		Thread.sleep(10000);
		// int refund = sellerHelper.getNoOfRefund(store_order_id);
		// log.debug("Number of Refund: " + refund);
		int refundTxResult = sellerHelper.getTxDBStatus1(orderId, "REFUNDED");
		log.debug("DB status for refund: " + refundTxResult);
		// AssertJUnit.assertEquals(1, refund);
		AssertJUnit.assertEquals(1, refundTxResult);
		sellerHelper.deleteOrderRecord(orderId, ppsId, paymentPlanItemId, ppsIdCancellation, paymentPlanItemIdref);
	}

	// Create online order with vector and seller
	@Test(groups = {
			"Regression" }, priority = 0, dataProvider = "SPS_VectorSellerMultiQty", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1. Create online order with vector and seller multiqty. \n2.check the split in citrus than in SPS for all skus.")
	public void SPS_VectorSellerMultiQty(String orderId, String store_order_id, String OrderReleaseId, String ppsId,
			String paymentPlanItemId, String paymentPlanItemId1, String lineId, String lineId1, String message,
			String releaseMessage, String deliverMessage, String cancellationMessage, String ppsIdCancellation,
			String paymentPlanItemIdref, String releaseMessage1, String RefundMessage, String ppsIdReturn,
			String paymentPlanItemIdReturnRef, String OrderReleaseId1)
			throws SQLException, IOException, InterruptedException {
		log.debug("---------orderId: " + orderId + " ppsId: " + ppsId + " PymentPlanItemId: " + paymentPlanItemId);
		sellerHelper.rabbitMqAddTx(message, orderId);
		Thread.sleep(10000);
		// int splits = sellerHelper.getNoOfSplits(store_order_id);
		// log.debug("Number of Splits: " + splits);
		int txResult = sellerHelper.getSplitTxDBCount(orderId);
		int pgResult = sellerHelper.getSplitPgDBCount(orderId);
		log.debug("DB check Tx table splits count: " + txResult + " pg table: " + pgResult);
		// AssertJUnit.assertEquals(4, splits);
		AssertJUnit.assertEquals(4, txResult);
		AssertJUnit.assertEquals(1, pgResult);

		SellerPaymentDP.SPS_VectorSellerMultiQtyLineCancellationHelper(orderId, store_order_id, OrderReleaseId, lineId,
				ppsId, ppsIdCancellation, paymentPlanItemIdref);
		sellerHelper.rabbitMqRefundTx(cancellationMessage, ppsIdCancellation);
		Thread.sleep(10000);

		sellerHelper.updateOrderReleaseStatusAndPackedOn(OrderReleaseId, "PK");
		sellerHelper.updateOrderLineStatusByLineId(lineId, "QD");
		sellerHelper.rabbitMqReleasePKTx(releaseMessage, orderId);
		sellerHelper.rabbitMqReleaseDLTx(deliverMessage, orderId);
		Thread.sleep(10000);
		int txResult1 = sellerHelper.getSplitTxDBCount(orderId);
		int pgResult1 = sellerHelper.getSplitPgDBCount(orderId);
		// int release1 = sellerHelper.getNoOfRelease(store_order_id);
		// log.debug("Number of release: " + release1);
		log.debug("DB check Tx table splits count: " + txResult1 + " pg table: " + pgResult1);
		Assert.assertEquals(txResult1, 4, "Transactions :");
		Assert.assertEquals(pgResult1, 1, "PG Settled :");
		// Assert.assertEquals(release1, 1, "Released :");

		int releaseSettlementResult1 = sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED");
		Assert.assertEquals(releaseSettlementResult1, 1,
				"Release Order Id:  " + orderId + " Not available in SPS Settlement detail table");

		sellerHelper.updateOrderReleaseStatusAndPackedOn(OrderReleaseId1, "PK");
		sellerHelper.updateOrderLineStatusByLineId(lineId1, "QD");
		sellerHelper.rabbitMqReleasePKTx(releaseMessage1, orderId);
		Thread.sleep(10000);
		int releaseSettlementResult2 = sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED");
		Assert.assertEquals(releaseSettlementResult2, 2,
				"Release Order Id:  " + orderId + " Not available in SPS Settlement detail table");

		SellerPaymentDP.SPS_VectorSellerMultiQty1RefundHelper(orderId, store_order_id, OrderReleaseId1, ppsId,
				ppsIdReturn, paymentPlanItemIdReturnRef);
		sellerHelper.rabbitMqRefundTx(RefundMessage, ppsIdReturn);
		Thread.sleep(10000);
		int refundSettlementResult = sellerHelper.getSettlementdetailDBStatusCount(orderId, "REFUNDED");
		Assert.assertEquals(refundSettlementResult, 1,
				"Refund Order Id:  " + orderId + " Not available in SPS Settlement detail table");
		Assert.assertEquals((sellerHelper.getSettlementDetailRefundAmount(orderId, "ONLINE")), "-1500.00",
				"Online Refund amount:");

		sellerHelper.deleteFromPaymentPlanItemInstrument(paymentPlanItemId1);
		sellerHelper.deleteOrderRecord(orderId, ppsId, paymentPlanItemId);
	}

	// Create online order with seller Multiqty sku 1 cancel1 release1 check
	// pgsettled1. Check split and refund for that sku in citrus and Db both
	@Test(groups = {
			"Regression" }, priority = 0, dataProvider = "SPS_SellerMultiQtyRelRTO", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1. Create online order with seller qty3 sku1. \n2.check the split in citrus than in SPS for all 3qty.\n3.release all 3 qty and check releases on both.\n "
					+ "4.Mark item rto. \n5.Check the refund on both")
	public void SPS_SellerMultiQtyRelRTO(String orderId, String store_order_id, String ppsId, String ppsIdCancellation,
			String paymentPlanItemId, String paymentPlanItemIdref, String orderMessage, String lineId,
			String paymentPlanExecutionStatus_id, String releaseMessage, String OrderReleaseId, String returnMessage,
			String ppsIdReturn, String paymentPlanItemIdret) throws SQLException, IOException, InterruptedException {
		log.debug("---------orderId: " + orderId + " ppsId: " + ppsId + " PymentPlanItemId: " + paymentPlanItemId);
		sellerHelper.rabbitMqAddTx(orderMessage, orderId);
		Thread.sleep(10000);
		// int splits = sellerHelper.getNoOfSplits(store_order_id);
		// log.debug("Number of Splits: " + splits);
		int txResult = sellerHelper.getSplitTxDBCount(orderId);
		int pgResult = sellerHelper.getSplitPgDBCount(orderId);
		log.debug("DB check Tx table splits count: " + txResult + " pg table: " + pgResult);
		// AssertJUnit.assertEquals(3, splits);
		AssertJUnit.assertEquals(3, txResult);
		AssertJUnit.assertEquals(1, pgResult);

		sellerHelper.updateOrderReleaseStatusAndPackedOn(OrderReleaseId, "PK");
		sellerHelper.rabbitMqReleasePKTx(releaseMessage, orderId);
		Thread.sleep(10000);

		int releaseTxResult = sellerHelper.getTxDBStatusCount(orderId, "RELEASED");
		log.debug("Number of release on DB: " + releaseTxResult);
		AssertJUnit.assertEquals(3, releaseTxResult);
		int releaseTxResult1 = sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED");
		Assert.assertEquals(releaseTxResult1, 3,
				"Release Order Id:  " + orderId + " Not available in SPS Settlement detail table");

		log.debug("Number of releases on SPS Transaction_detail DB: " + releaseTxResult1);
		log.debug("Fund Release Amount :" + sellerHelper.getSettlementDetailFundReleaseAmount(orderId, "RELEASED"));

		SellerPaymentDP.SPS_SellerMultiQtyRelRTOHelper(orderId, store_order_id, OrderReleaseId, paymentPlanItemIdret,
				ppsId, ppsIdReturn);
		sellerHelper.rabbitMqRefundTx(returnMessage, ppsIdCancellation);
		Thread.sleep(10000);

		int refundTxResult = sellerHelper.getTxDBStatus1(orderId, "REFUNDED");
		log.debug("DB status for refund: " + refundTxResult);
		AssertJUnit.assertEquals(3, refundTxResult);
		int releaseTxResult2 = sellerHelper.getSettlementdetailDBStatusCount(orderId, "REFUNDED");
		Assert.assertEquals(releaseTxResult2, 3,
				"REFUND Order_id:" + orderId + " Not available in SPS Settlement detail table");

		sellerHelper.deleteFromPaymentPlanItemInstrument(paymentPlanItemIdret);
		sellerHelper.deleteOrderRecord(orderId, ppsId, paymentPlanItemId, ppsIdReturn, paymentPlanItemIdref);
	}

	@Test(groups = {
			"Regression" }, priority = 0, dataProvider = "SPS_SellerMultiQtyRelLost", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1. Create online order with seller qty3 sku1. \n2.check the split in citrus than in SPS for all 3qty."
					+ "3.Mark order lost and check the release for all these. \n4.Check the reund in both")
	public void SPS_SellerMultiQtyRelLost(String orderId, String store_order_id, String ppsId, String paymentPlanItemId,
			String orderMessage, String lineId, String releaseMessage, String OrderReleaseId1)
			throws SQLException, IOException, InterruptedException {
		log.debug("---------orderId: " + orderId + " ppsId: " + ppsId + " PymentPlanItemId: " + paymentPlanItemId);
		sellerHelper.rabbitMqAddTx(orderMessage, orderId);
		Thread.sleep(10000);
		// int splits = sellerHelper.getNoOfSplits(store_order_id);
		// log.debug("Number of Splits: " + splits);
		int txResult = sellerHelper.getSplitTxDBCount(orderId);
		int pgResult = sellerHelper.getSplitPgDBCount(orderId);
		log.debug("DB check Tx table splits count: " + txResult + " pg table: " + pgResult);
		// AssertJUnit.assertEquals(3, splits);
		AssertJUnit.assertEquals(3, txResult);
		AssertJUnit.assertEquals(1, pgResult);

		sellerHelper.rabbitMqReleasePKTx(releaseMessage, orderId);
		Thread.sleep(10000);
		// int release = sellerHelper.getNoOfRelease(store_order_id);
		// log.debug("Number of release: " + release);
		int releaseTxResult = sellerHelper.getTxDBStatusCount(orderId, "RELEASED");
		log.debug("Number of release on DB: " + releaseTxResult);
		// AssertJUnit.assertEquals(3, release);
		AssertJUnit.assertEquals(3, releaseTxResult);
		sellerHelper.deleteOrderRecord(orderId, ppsId, paymentPlanItemId);
	}

	@Test(groups = { "Regression",
			"Sanity" }, priority = 0, dataProvider = "SPS_SellerMultiQtyCanRetryCan", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1. Create online order with seller qty3 same sku. \n2.check the split in citrus than in SPS for all 3qty.\n3.cancel 1qty of the order and check the refund.\n "
					+ "4.Hit the cancelaltion message(As retry) twice more and check that the other qty not got cancelled")
	public void SPS_SellerMultiQtyCanRetryCan(String orderId, String store_order_id, String ppsId,
			String ppsIdCancellation, String paymentPlanItemId, String paymentPlanItemIdref, String message,
			String cancellationMessage, String lineId) throws SQLException, IOException, InterruptedException {
		log.debug("---------orderId: " + orderId + " ppsId: " + ppsId + " PymentPlanItemId: " + paymentPlanItemId);
		sellerHelper.rabbitMqAddTx(message, orderId);
		Thread.sleep(10000);
		// int splits = sellerHelper.getNoOfSplits(store_order_id);
		// log.debug("Number of Splits: " + splits);
		int txResult = sellerHelper.getSplitTxDBCount(orderId);
		int pgResult = sellerHelper.getSplitPgDBCount(orderId);
		log.debug("DB check Tx table splits count: " + txResult + " pg table: " + pgResult);
		// AssertJUnit.assertEquals(3, splits);
		AssertJUnit.assertEquals(3, txResult);
		AssertJUnit.assertEquals(1, pgResult);
		SellerPaymentDP.SPS_SellerMultiQtyCanRetryCanHelper(orderId, store_order_id, orderId, lineId, ppsId,
				ppsIdCancellation, paymentPlanItemIdref);
		sellerHelper.rabbitMqRefundTx(cancellationMessage, ppsIdCancellation);
		Thread.sleep(10000);
		// int refund = sellerHelper.getNoOfRefund(store_order_id);
		// log.debug("Number of Refund: " + refund);
		int refundTxResult = sellerHelper.getTxDBStatus(orderId, "1251881", "REFUNDED");
		log.debug("DB status for refund: " + refundTxResult);
		// AssertJUnit.assertEquals(1, refund);
		AssertJUnit.assertEquals(1, refundTxResult);
		sellerHelper.rabbitMqRefundTx(cancellationMessage, ppsIdCancellation);
		Thread.sleep(10000);
		// int refund1 = sellerHelper.getNoOfRefund(store_order_id);
		// log.debug("Number of Refund: " + refund1);
		// AssertJUnit.assertEquals(1, refund1);
		sellerHelper.rabbitMqRefundTx(cancellationMessage, ppsIdCancellation);
		Thread.sleep(10000);
		// int refund2 = sellerHelper.getNoOfRefund(store_order_id);
		// log.debug("Number of Refund: " + refund2);
		// AssertJUnit.assertEquals(1, refund2);
		sellerHelper.deleteOrderRecord(orderId, ppsId, paymentPlanItemId, ppsIdCancellation, paymentPlanItemIdref);
	}

	@Test(groups = { "Regression",
			"Sanity" }, priority = 0, dataProvider = "SPS_VectorMultiQtyCanRetryCan", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1. Create online order with Vector qty3 same sku. \n2.check the split in citrus than in SPS for all 3qty.\n3.cancel 1qty of the order and check the refund.\n "
					+ "4.Hit the cancelaltion message(As retry) twice more and check that the other qty not got cancelled")
	public void SPS_VectorMultiQtyCanRetryCan(String orderId, String store_order_id, String ppsId,
			String ppsIdCancellation, String paymentPlanItemId, String paymentPlanItemIdref, String message,
			String cancellationMessage, String lineId) throws SQLException, IOException, InterruptedException {
		log.debug("---------orderId: " + orderId + " ppsId: " + ppsId + " PymentPlanItemId: " + paymentPlanItemId);
		sellerHelper.rabbitMqAddTx(message, orderId);
		Thread.sleep(10000);
		// int splits = sellerHelper.getNoOfSplits(store_order_id);
		// log.debug("Number of Splits: " + splits);
		int txResult = sellerHelper.getSplitTxDBCount(orderId);
		int pgResult = sellerHelper.getSplitPgDBCount(orderId);
		log.debug("DB check Tx table splits count: " + txResult + " pg table: " + pgResult);
		// AssertJUnit.assertEquals(3, splits);
		AssertJUnit.assertEquals(3, txResult);
		AssertJUnit.assertEquals(1, pgResult);
		SellerPaymentDP.SPS_VectorMultiQtyCanRetryCanHelper(orderId, store_order_id, orderId, lineId, ppsId,
				ppsIdCancellation, paymentPlanItemIdref);
		sellerHelper.rabbitMqRefundTx(cancellationMessage, ppsIdCancellation);
		Thread.sleep(10000);
		// int refund = sellerHelper.getNoOfRefund(store_order_id);
		// log.debug("Number of Refund: " + refund);
		int refundTxResult = sellerHelper.getTxDBStatus(orderId, "3831", "REFUNDED");
		log.debug("DB status for refund: " + refundTxResult);
		// AssertJUnit.assertEquals(1, refund);
		AssertJUnit.assertEquals(1, refundTxResult);
		sellerHelper.rabbitMqRefundTx(cancellationMessage, ppsIdCancellation);
		Thread.sleep(10000);
		// int refund1 = sellerHelper.getNoOfRefund(store_order_id);
		// log.debug("Number of Refund: " + refund1);
		// AssertJUnit.assertEquals(1, refund1);
		sellerHelper.rabbitMqRefundTx(cancellationMessage, ppsIdCancellation);
		Thread.sleep(10000);
		// int refund2 = sellerHelper.getNoOfRefund(store_order_id);
		// log.debug("Number of Refund: " + refund2);
		// AssertJUnit.assertEquals(1, refund2);
		sellerHelper.deleteOrderRecord(orderId, ppsId, paymentPlanItemId, ppsIdCancellation, paymentPlanItemIdref);
	}

	@Test(groups = {
			"Regression" }, priority = 0, dataProvider = "SPS_SellerMultiQtyReleaseRetry", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1. Create online order with seller qty3 same sku. \n2.check the split in citrus than in SPS for all 3qty.\n3.Now release 1 qty and check 1 release on both.\n "
					+ "4.Hit the same release message(As retry) twice more and check that the release is only 1. \n5. Now release the other same sku but diff release and check the release on both")
	public void SPS_SellerMultiQtyReleaseRetry(String orderId, String OrderReleaseId, String OrderReleaseId1,
			String store_order_id, String ppsId, String paymentPlanItemId, String message, String lineId,
			String releaseMessage, String releaseMessage1, String paymentPlanReturnItemId, String ppsIdCancellation,
			String returnMessage) throws SQLException, IOException, InterruptedException {
		log.debug("---------orderId: " + orderId + " ppsId: " + ppsId + " PymentPlanItemId: " + paymentPlanItemId);
		sellerHelper.rabbitMqAddTx(message, orderId);
		sellerHelper.rabbitMqAddTx(message, orderId);
		sellerHelper.rabbitMqAddTx(message, orderId);
		Thread.sleep(10000);
		// int splits = sellerHelper.getNoOfSplits(store_order_id);
		// log.debug("Number of Splits: " + splits);
		int txResult = sellerHelper.getSplitTxDBCount(orderId);
		int pgResult = sellerHelper.getSplitPgDBCount(orderId);
		log.debug("DB check Tx table splits count: " + txResult + " pg table: " + pgResult);

		Assert.assertEquals(txResult, 3, "Add Transactions at Transaction details db :");
		Assert.assertEquals(pgResult, 1, "PG Settlement table : ");
		sellerHelper.updateOrderReleaseStatusAndPackedOn(OrderReleaseId, "PK");
		sellerHelper.rabbitMqReleasePKTx(releaseMessage, orderId);
		Thread.sleep(10000);
		// int release = sellerHelper.getNoOfRelease(store_order_id);
		// log.debug("Number of release: " + release);
		int releaseTxResult = sellerHelper.getTxDBStatusCount(orderId, "RELEASED");
		log.debug("Number of release on DB: " + releaseTxResult);
		// AssertJUnit.assertEquals(1, release);
		AssertJUnit.assertEquals(1, releaseTxResult);

		sellerHelper.rabbitMqReleasePKTx(releaseMessage, orderId);
		Thread.sleep(10000);
		// int release1 = sellerHelper.getNoOfRelease(store_order_id);
		// log.debug("Number of release: " + release1);
		int releaseTxResult1 = sellerHelper.getTxDBStatusCount(orderId, "RELEASED");
		log.debug("Number of release on DB: " + releaseTxResult1);
		// AssertJUnit.assertEquals(1, release1);
		AssertJUnit.assertEquals(1, releaseTxResult1);

		sellerHelper.rabbitMqReleasePKTx(releaseMessage, orderId);
		Thread.sleep(10000);
		// int release2 = sellerHelper.getNoOfRelease(store_order_id);
		// log.debug("Number of release: " + release2);
		int releaseTxResult2 = sellerHelper.getTxDBStatusCount(orderId, "RELEASED");
		log.debug("Number of release on DB: " + releaseTxResult2);
		// AssertJUnit.assertEquals(1, release2);
		AssertJUnit.assertEquals(1, releaseTxResult2);
		int releaseSettlResult = sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED");
		Assert.assertEquals(releaseSettlResult, 1,
				"Release Order Id:  " + orderId + " Not available in SPS Settlement detail table");
		Assert.assertEquals(sellerHelper.getSettlementDetailFundReleaseAmount(orderId, "RELEASED"), "2299.00",
				"Online Release amount:");
		log.debug("Number of releases on SPS Transaction_detail DB: " + releaseSettlResult);
		log.debug("Fund Release Amount :" + sellerHelper.getSettlementDetailFundReleaseAmount(orderId, "RELEASED"));

		sellerHelper.updateOrderReleaseStatusAndPackedOn(OrderReleaseId1, "PK");
		sellerHelper.rabbitMqReleasePKTx(releaseMessage1, orderId);
		Thread.sleep(10000);
		// int release3 = sellerHelper.getNoOfRelease(store_order_id);
		// log.debug("Number of release: " + release3);
		int releaseTxResult3 = sellerHelper.getTxDBStatusCount(orderId, "RELEASED");
		log.debug("Number of release on DB: " + releaseTxResult3);
		// AssertJUnit.assertEquals(2, release3);
		AssertJUnit.assertEquals(2, releaseTxResult3);
		int releaseSettlResult1 = sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED");
		Assert.assertEquals(releaseSettlResult1, 2,
				"Release Order Id:  " + orderId + " Not available in SPS Settlement detail table");
		Assert.assertEquals(
				sellerHelper.getSettlementDetailFundReleaseAmountByItemRef(store_order_id + "_1251881_0", "RELEASED"),
				"2299.00", "Online Release amount:");
		Assert.assertEquals(
				sellerHelper.getSettlementDetailFundReleaseAmountByItemRef(store_order_id + "_1251881_1", "RELEASED"),
				"2299.00", "Online Release amount:");

		log.debug("Number of releases on SPS Transaction_detail DB: " + releaseSettlResult1);
		log.debug("Fund Release Amount :" + sellerHelper.getSettlementDetailFundReleaseAmount(orderId, "RELEASED"));

		SellerPaymentDP.SPS_SellerMultiQtyReleaseRetryHelper(orderId, store_order_id, paymentPlanReturnItemId, ppsId,
				ppsIdCancellation);
		sellerHelper.rabbitMqRefundTx(returnMessage, ppsIdCancellation);
		log.debug("Wait for 10Sec more to get order details in settlement detail table....");
		Thread.sleep(10000);

		int releaseSettlResult2 = sellerHelper.getSettlementdetailDBStatusCount(orderId, "REFUNDED");
		Assert.assertEquals(releaseSettlResult2, 1,
				"REFUND Order_id:" + orderId + " Not available in SPS Settlement detail table");
		// Assert.assertEquals(sellerHelper.getSettlementDetailRefundAmount(orderId,"CASH_ON_DELIVERY"),"-1399.00"
		// ,"COD Refund amount:");
		Assert.assertEquals(sellerHelper.getSettlementDetailRefundAmount(orderId, "ONLINE"), "-2299.00",
				"Online Refund amount:");

		sellerHelper.deleteOrderRecord(orderId, ppsId, paymentPlanItemId);
	}

	@Test(groups = {
			"Regression" }, priority = 0, dataProvider = "SPS_VectorMultiQtyRelRefundRetry", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1. Create online order with Vector qty3 same sku, qty1. \n2.check the split in citrus than in SPS for all 4qty.\n3.Release all sku one by one.\n "
					+ "5.check 4 release happened on both. \n4.Hit the retry message for one qty of 3qtysku(As retry) twice more and check that only 1 qty get refunded and the other qty not got refunded")
	public void SPS_VectorMultiQtyRelRefundRetry(String orderId, String store_order_id, String ppsId,
			String paymentPlanItemId, String message, String lineId, String ppsIdRefund, String returnMessage,
			String paymentPlanItemIdref, String releaseMessage, String releaseMessage1, String paymentPlanItemId1)
			throws SQLException, IOException, InterruptedException {
		log.debug("---------orderId: " + orderId + " ppsId: " + ppsId + " PymentPlanItemId: " + paymentPlanItemId);
		sellerHelper.rabbitMqAddTx(message, orderId);
		Thread.sleep(10000);
		// int splits = sellerHelper.getNoOfSplits(store_order_id);
		// log.debug("Number of Splits: " + splits);
		int txResult = sellerHelper.getSplitTxDBCount(orderId);
		int pgResult = sellerHelper.getSplitPgDBCount(orderId);
		log.debug("DB check Tx table splits count: " + txResult + " pg table: " + pgResult);
		// AssertJUnit.assertEquals(4, splits);
		AssertJUnit.assertEquals(4, txResult);
		AssertJUnit.assertEquals(1, pgResult);

		sellerHelper.rabbitMqReleasePKTx(releaseMessage, orderId);
		Thread.sleep(10000);
		// int release = sellerHelper.getNoOfRelease(store_order_id);
		// log.debug("Number of release: " + release);
		int releaseTxResult = sellerHelper.getTxDBStatusCount(orderId, "RELEASED");
		log.debug("Number of release on DB: " + releaseTxResult);
		// AssertJUnit.assertEquals(3, release);
		AssertJUnit.assertEquals(3, releaseTxResult);
		int releaseSettleResult1 = sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED");
		Assert.assertEquals(releaseSettleResult1, 3,
				"Release Order Id:  " + orderId + " Not available in SPS Settlement detail table");
		// Assert.assertEquals(sellerHelper.getSettlementDetailFundReleaseAmountByItemRef(orderId+"_3831_0","RELEASED"),"799.00"
		// ,"Online Release amount:");

		sellerHelper.rabbitMqReleasePKTx(releaseMessage1, orderId);
		Thread.sleep(10000);
		// int release1 = sellerHelper.getNoOfRelease(store_order_id);
		// log.debug("Number of release: " + release1);
		int releaseTxResult1 = sellerHelper.getTxDBStatusCount(orderId, "RELEASED");
		log.debug("Number of release on DB: " + releaseTxResult1);
		// AssertJUnit.assertEquals(4, release1);
		AssertJUnit.assertEquals(4, releaseTxResult1);
		int releaseSettleResult2 = sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED");
		Assert.assertEquals(releaseSettleResult2, 4,
				"Release Order Id:  " + orderId + " Not available in SPS Settlement detail table");
		// Assert.assertEquals(sellerHelper.getSettlementDetailFundReleaseAmountByItemRef(orderId+"_3831_0","RELEASED"),"799.00"
		// ,"Online Release amount:");

		SellerPaymentDP.SPS_VectorMultiQtyRelRefundRetryHelper(orderId, store_order_id, orderId, paymentPlanItemIdref,
				ppsId, ppsIdRefund);
		sellerHelper.rabbitMqRefundTx(returnMessage, ppsIdRefund);
		Thread.sleep(10000);
		// int refund = sellerHelper.getNoOfRefund(store_order_id);
		// log.debug("Number of Refund: " + refund);
		int refundTxResult = sellerHelper.getTxDBStatus(orderId, "3831", "REFUNDED");
		log.debug("DB status for refund: " + refundTxResult);
		// AssertJUnit.assertEquals(1, refund);
		AssertJUnit.assertEquals(1, refundTxResult);
		int refundsettlResult = sellerHelper.getSettlementdetailDBStatusCount(orderId, "REFUNDED");
		Assert.assertEquals(refundsettlResult, 1,
				"REFUND Order_id:" + orderId + " Not available in SPS Settlement detail table");
		Assert.assertEquals(sellerHelper.getSettlementDetailRefundAmount(orderId, "ONLINE"), "-2299.00",
				"Online Refund amount:");

		sellerHelper.rabbitMqRefundTx(returnMessage, ppsIdRefund);
		Thread.sleep(10000);
		// int refund1 = sellerHelper.getNoOfRefund(store_order_id);
		// log.debug("Number of Refund: " + refund1);
		// AssertJUnit.assertEquals(1, refund1);
		int refundsettlResult1 = sellerHelper.getSettlementdetailDBStatusCount(orderId, "REFUNDED");
		Assert.assertEquals(refundsettlResult1, 1,
				"REFUND Order_id:" + orderId + " Not available in SPS Settlement detail table");
		Assert.assertEquals(sellerHelper.getSettlementDetailRefundAmount(orderId, "ONLINE"), "-2299.00",
				"Online Refund amount:");

		sellerHelper.rabbitMqRefundTx(returnMessage, ppsIdRefund);
		Thread.sleep(10000);
		// int refund2 = sellerHelper.getNoOfRefund(store_order_id);
		// log.debug("Number of Refund: " + refund2);
		// AssertJUnit.assertEquals(1, refund2);
		int refundsettlResult2 = sellerHelper.getSettlementdetailDBStatusCount(orderId, "REFUNDED");
		Assert.assertEquals(refundsettlResult2, 1,
				"REFUND Order_id:" + orderId + " Not available in SPS Settlement detail table");
		Assert.assertEquals(sellerHelper.getSettlementDetailRefundAmount(orderId, "ONLINE"), "-2299.00",
				"Online Refund amount:");

		sellerHelper.deleteFromPaymentPlanItemInstrument(paymentPlanItemId1);
		sellerHelper.deleteOrderRecord(orderId, ppsId, paymentPlanItemId, ppsIdRefund, paymentPlanItemIdref);
	}

	@Test(groups = {
			"Regression" }, priority = 0, dataProvider = "SPS_VectorSellerMultiQtyRelTryOnPKforDLAndRel", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1. Create online order with vector and seller each with qty2. \n2.check the split in citrus than in SPS for all skus.\n3. Push PK message for both "
					+ "V&S and check only vector got released and other 2 got pg_settled.\n4.Push PK message for all 4qty one by one and check the vector should not release again and seller should got release one by one.")
	public void SPS_VectorSellerMultiQtyRelTryOnPKforDLAndRel(String orderId, String store_order_id,
			String OrderReleaseId1, String ppsId, String paymentPlanItemId, String paymentPlanItemId1, String lineId,
			String message, String releaseMessage, String releaseMessage1, String releaseMessage2,
			String ppsIdCancellation, String paymentPlanItemIdref, String returnMessage, String OrderReleaseId,
			String OrderReleaseId2) throws SQLException, IOException, InterruptedException {
		log.debug("---------orderId: " + orderId + " ppsId: " + ppsId + " PymentPlanItemId: " + paymentPlanItemId);
		sellerHelper.rabbitMqAddTx(message, orderId);
		Thread.sleep(10000);
		// int splits = sellerHelper.getNoOfSplits(store_order_id);
		// log.debug("Number of Splits: " + splits);
		int txResult = sellerHelper.getSplitTxDBCount(orderId);
		int pgResult = sellerHelper.getSplitPgDBCount(orderId);
		log.debug("DB check Tx table splits count: " + txResult + " pg table: " + pgResult);
		// AssertJUnit.assertEquals(4, splits);
		AssertJUnit.assertEquals(4, txResult);
		AssertJUnit.assertEquals(1, pgResult);

		sellerHelper.updateOrderReleaseStatus(OrderReleaseId, "PK");
		sellerHelper.updateOrderLineStatus(OrderReleaseId, "QD");

		sellerHelper.rabbitMqReleasePKTx(releaseMessage, orderId);
		Thread.sleep(10000);
		int releaseTxResult = sellerHelper.getTxDBStatusCount(orderId, "RELEASED");
		log.debug("Number of release on DB: " + releaseTxResult);
		AssertJUnit.assertEquals(2, releaseTxResult);
		Assert.assertEquals(sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED"), 2,
				"Release Order Id:  " + orderId + " Not available in SPS Settlement detail table");

		int pgTxResult = sellerHelper.getTxDBStatusCount(orderId, "PGSETTLE");
		log.debug("Number of PG settled on DB: " + pgTxResult);
		AssertJUnit.assertEquals(2, pgTxResult);

		sellerHelper.updateOrderReleaseStatus(OrderReleaseId1, "PK");
		sellerHelper.updateOrderLineStatus(OrderReleaseId1, "QD");

		sellerHelper.rabbitMqReleasePKTx(releaseMessage1, orderId);
		Thread.sleep(10000);
		int releaseTxResult1 = sellerHelper.getTxDBStatusCount(orderId, "RELEASED");
		log.debug("Number of release on DB: " + releaseTxResult1);
		Assert.assertEquals(releaseTxResult1, 3,
				"Release Order Id:  " + orderId + " Not available in SPS Transaction detail table");
		Assert.assertEquals(sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED"), 3,
				"Release Order Id:  " + orderId + " Not available in SPS Settlement detail table");

		sellerHelper.updateOrderReleaseStatus(OrderReleaseId2, "PK");
		sellerHelper.updateOrderLineStatus(OrderReleaseId2, "QD");

		sellerHelper.rabbitMqReleasePKTx(releaseMessage2, orderId);
		Thread.sleep(10000);
		int releaseTxResult2 = sellerHelper.getTxDBStatusCount(orderId, "RELEASED");
		log.debug("Number of release on DB: " + releaseTxResult2);
		AssertJUnit.assertEquals(4, releaseTxResult2);
		int releaseSettlResult3 = sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED");
		Assert.assertEquals(releaseSettlResult3, 4,
				"Release Order Id:  " + orderId + " Not available in SPS Settlement detail table");

		SellerPaymentDP.SPS_VectorSellerMultiQtyRelTryOnPKforDLAndRelHelper(orderId, store_order_id, OrderReleaseId,
				paymentPlanItemIdref, ppsId, ppsIdCancellation);
		sellerHelper.rabbitMqRefundTx(returnMessage, ppsIdCancellation);

		log.debug("Wait for 10Sec more to get order details in settlement detail table....");
		Thread.sleep(10000);

		int refundSettleResult = sellerHelper.getSettlementdetailDBStatusCount(orderId, "REFUNDED");
		Assert.assertEquals(refundSettleResult, 1,
				"REFUND Order_id:" + orderId + " Not available in SPS Settlement detail table");
		Assert.assertEquals(sellerHelper.getSettlementDetailRefundAmount(orderId, "ONLINE"), "-1500.00",
				"Online Refund amount:");

		sellerHelper.deleteFromPaymentPlanItemInstrument(paymentPlanItemId1);
		sellerHelper.deleteOrderRecord(orderId, ppsId, paymentPlanItemId);
	}

	@Test(groups = {
			"Regression" }, priority = 0, dataProvider = "SPS_VectorSellerMultiQtyAllRelMultiRet", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1. Create online order with vector and seller each with qty2. \n2.check the split in citrus than in SPS for all skus.\n3. Push PK message for both "
					+ "V&S and check only vector got released.\n4.Push PK message for all 4qty one by one all should got release one by one.\n5. Return 2 qty of vector one by one and keep on checking refund on both."
					+ "\n6. Return one seller qty and check refund on both.\n7.Retry the seller sku return and check on both that the retry qty should not got refunded")
	public void SPS_VectorSellerMultiQtyAllRelMultiRet(String orderId, String store_order_id, String ppsId,
			String paymentPlanItemId, String paymentPlanItemId1, String lineId, String message, String releaseMessage,
			String releaseMessage4, String releaseMessage5, String returnMessage, String returnMessage1,
			String returnMessage2, String ppsIdReturn, String ppsIdReturn1, String ppsIdReturn2,
			String paymentPlanItemIdret, String paymentPlanItemIdret1, String paymentPlanItemIdret2,
			String OrderReleaseId1, String OrderReleaseId2, String lineId1, String lineId2)
			throws SQLException, IOException, InterruptedException {
		log.debug("---------orderId: " + orderId + " ppsId: " + ppsId + " PymentPlanItemId: " + paymentPlanItemId);
		sellerHelper.rabbitMqAddTx(message, orderId);
		Thread.sleep(10000);
		// int splits = sellerHelper.getNoOfSplits(store_order_id);
		// log.debug("Number of Splits: " + splits);
		int txResult = sellerHelper.getSplitTxDBCount(orderId);
		int pgResult = sellerHelper.getSplitPgDBCount(orderId);
		log.debug("DB check Tx table splits count: " + txResult + " pg table: " + pgResult);
		// AssertJUnit.assertEquals(4, splits);
		AssertJUnit.assertEquals(4, txResult);
		AssertJUnit.assertEquals(1, pgResult);

		sellerHelper.rabbitMqReleasePKTx(releaseMessage, orderId);
		log.debug("Wait for 10Sec more to get settlement detail....");
		Thread.sleep(10000);
		// int release = sellerHelper.getNoOfRelease(store_order_id);
		// log.debug("Number of release: " + release);
		int releaseTxResult = sellerHelper.getTxDBStatusCount(orderId, "RELEASED");
		log.debug("Number of release on DB: " + releaseTxResult);
		// AssertJUnit.assertEquals(2, release);
		AssertJUnit.assertEquals(2, releaseTxResult);
		int releaseSettlResult1 = sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED");
		Assert.assertEquals(releaseSettlResult1, 2,
				"Release Order Id:  " + orderId + " Not available in SPS Settlement detail table");

		int pgTxResult = sellerHelper.getTxDBStatusCount(orderId, "PGSETTLE");
		log.debug("Number of PG settled on DB: " + pgTxResult);
		AssertJUnit.assertEquals(2, pgTxResult);

		sellerHelper.rabbitMqReleasePKTx(releaseMessage4, orderId);
		Thread.sleep(10000);
		// int release4 = sellerHelper.getNoOfRelease(store_order_id);
		// log.debug("Number of release: " + release4);
		int releaseTxResult4 = sellerHelper.getTxDBStatusCount(orderId, "RELEASED");
		log.debug("Number of release on DB: " + releaseTxResult4);
		// AssertJUnit.assertEquals(3, release4);
		AssertJUnit.assertEquals(3, releaseTxResult4);
		log.debug("Wait for 10Sec more to get settlement detail....");
		Thread.sleep(10000);
		int releaseSettlResult2 = sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED");
		Assert.assertEquals(releaseSettlResult2, 3,
				"Release Order Id:  " + orderId + " Not available in SPS Settlement detail table");

		sellerHelper.rabbitMqReleasePKTx(releaseMessage5, orderId);
		Thread.sleep(10000);
		// int release5 = sellerHelper.getNoOfRelease(store_order_id);
		// log.debug("Number of release: " + release5);
		int releaseTxResult5 = sellerHelper.getTxDBStatusCount(orderId, "RELEASED");
		log.debug("Number of release on DB: " + releaseTxResult5);
		// AssertJUnit.assertEquals(4, release5);
		AssertJUnit.assertEquals(4, releaseTxResult5);
		log.debug("Wait for 10Sec more to get settlement detail....");
		Thread.sleep(10000);
		int releaseSettlResult3 = sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED");
		Assert.assertEquals(releaseSettlResult3, 4,
				"Release Order Id:  " + orderId + " Not available in SPS Settlement detail table");

		SellerPaymentDP.SPS_VectorSellerMultiQtyAllRelMultiRetHelper(orderId, store_order_id, orderId, OrderReleaseId1,
				OrderReleaseId2, ppsId, ppsIdReturn, ppsIdReturn1, ppsIdReturn2, paymentPlanItemIdret,
				paymentPlanItemIdret1, paymentPlanItemIdret2, lineId, lineId1, lineId2);
		sellerHelper.rabbitMqRefundTx(returnMessage, ppsIdReturn);
		Thread.sleep(10000);
		// int refund = sellerHelper.getNoOfRefund(store_order_id);
		// log.debug("Number of Refund: " + refund);
		int returnTxResult = sellerHelper.getTxDBStatusCount(orderId, "REFUNDED");
		log.debug("Number of release on DB: " + returnTxResult);
		// AssertJUnit.assertEquals(1, refund);
		AssertJUnit.assertEquals(1, returnTxResult);
		log.debug("Wait for 10Sec more to get settlement detail....");
		Thread.sleep(10000);
		int refundSettxResult = sellerHelper.getSettlementdetailDBStatusCount(orderId, "REFUNDED");
		Assert.assertEquals(refundSettxResult, 1,
				"REFUND Order_id:" + orderId + " Not available in SPS Settlement detail table");

		Assert.assertEquals(sellerHelper.getSettlementDetailRefundAmount(orderId, "ONLINE"), "-1000.00",
				"Online Refund amount:");

		sellerHelper.rabbitMqRefundTx(returnMessage1, ppsIdReturn1);
		Thread.sleep(10000);
		// int refund1 = sellerHelper.getNoOfRefund(store_order_id);
		// log.debug("Number of Refund: " + refund1);
		int returnTxResult1 = sellerHelper.getTxDBStatusCount(orderId, "REFUNDED");
		log.debug("Number of release on DB: " + returnTxResult1);
		// AssertJUnit.assertEquals(2, refund1);
		AssertJUnit.assertEquals(2, returnTxResult1);
		log.debug("Wait for 10Sec more to get settlement detail....");
		Thread.sleep(10000);
		int refundSettxResult1 = sellerHelper.getSettlementdetailDBStatusCount(orderId, "REFUNDED");
		Assert.assertEquals(refundSettxResult1, 2,
				"REFUND Order_id:" + orderId + " Not available in SPS Settlement detail table");
		// Assert.assertEquals(sellerHelper.getSettlementDetailRefundAmount(orderId,"CASH_ON_DELIVERY"),"-1399.00"
		// ,"COD Refund amount:");
		// Assert.assertEquals(sellerHelper.getSettlementDetailRefundAmount(orderId,
		// "ONLINE"), "-799.00",
		// "Online Refund amount:");

		sellerHelper.rabbitMqRefundTx(returnMessage2, ppsIdReturn2);
		Thread.sleep(10000);
		// int refund2 = sellerHelper.getNoOfRefund(store_order_id);
		// log.debug("Number of Refund: " + refund2);
		int returnTxResult2 = sellerHelper.getTxDBStatusCount(orderId, "REFUNDED");
		log.debug("Number of release on DB: " + returnTxResult2);
		// AssertJUnit.assertEquals(3, refund2);
		AssertJUnit.assertEquals(3, returnTxResult2);
		log.debug("Wait for 10Sec more to get settlement detail....");
		Thread.sleep(10000);
		int refundSettxResult2 = sellerHelper.getSettlementdetailDBStatusCount(orderId, "REFUNDED");
		Assert.assertEquals(refundSettxResult2, 3,
				"REFUND Order_id:" + orderId + " Not available in SPS Settlement detail table");
		// Assert.assertEquals(sellerHelper.getSettlementDetailRefundAmount(orderId,"CASH_ON_DELIVERY"),"-1399.00"
		// ,"COD Refund amount:");
		// Assert.assertEquals(sellerHelper.getSettlementDetailRefundAmount(orderId,
		// "ONLINE"), "-799.00",
		// "Online Refund amount:");

		sellerHelper.rabbitMqRefundTx(returnMessage2, ppsIdReturn2);
		Thread.sleep(10000);
		// int refund3 = sellerHelper.getNoOfRefund(store_order_id);
		// log.debug("Number of Refund: " + refund3);
		int returnTxResult3 = sellerHelper.getTxDBStatusCount(orderId, "REFUNDED");
		log.debug("Number of release on DB: " + returnTxResult3);
		// AssertJUnit.assertEquals(3, refund3);
		AssertJUnit.assertEquals(3, returnTxResult3);
		log.debug("Wait for 10Sec more to get settlement detail....");
		Thread.sleep(10000);
		int refundSettxResult3 = sellerHelper.getSettlementdetailDBStatusCount(orderId, "REFUNDED");
		Assert.assertEquals(refundSettxResult3, 3,
				"REFUND Order_id:" + orderId + " Not available in SPS Settlement detail table");
		// Assert.assertEquals(sellerHelper.getSettlementDetailRefundAmount(orderId,"CASH_ON_DELIVERY"),"-1399.00"
		// ,"COD Refund amount:");
		// Assert.assertEquals(sellerHelper.getSettlementDetailRefundAmount(orderId,
		// "ONLINE"), "-799.00",
		// "Online Refund amount:");

		sellerHelper.deleteFromPaymentPlanItemInstrument(paymentPlanItemId1);
		sellerHelper.deleteOrderRecord(orderId, ppsIdReturn, paymentPlanItemIdret);
		sellerHelper.deleteOrderRecord(orderId, ppsIdReturn1, paymentPlanItemIdret1);
		sellerHelper.deleteOrderRecord(orderId, ppsIdReturn2, paymentPlanItemIdret2);
		sellerHelper.deleteOrderRecord(orderId, ppsId, paymentPlanItemId);
	}

	@Test(groups = { "Regression",
			"Sanity" }, priority = 0, dataProvider = "SPS_VectorQty1Exchange", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1. Create online order with Vector qty1 sku1. \n2.check the split in citrus than in SPS.\n3. Mark DL(By creating enrties in DB) and check for release.\n4. Mark exchange check for new order being created"
					+ "\n5. check the refund for the original sku")
	public void SPS_VectorQty1Exchange(String orderId, String ppsId, String paymentPlanItemId, String message,
			String releaseMessage, String orderIdex, String lineIdex, String ppsIdex, String paymentPlanItemIdex,
			String paymentPlanItemIdex1, String ppsIdexChild, String store_order_id, String store_order_id_ex,
			String OrderReleaseIdex, String OrderReleaseId, String lineId, String parentOrderRefundMessage)
			throws SQLException, IOException, InterruptedException, NumberFormatException, JAXBException {
		log.debug("-----------------orderId: " + orderId + ", ppsId: " + ppsId + ", PaymentPlanId: " + paymentPlanItemId
				+ ", orderIdex: " + orderIdex + ", ppsIdex: " + ppsIdex + ", lineIdex:" + lineIdex);
		log.debug("Child PPSID: " + ppsIdexChild);
		log.debug("Wait for 10 sec to get the lineId....");
		sellerHelper.rabbitMqAddTx(message, orderId);
		Thread.sleep(10000);

		int txResult = sellerHelper.getSplitTxDBCount(orderId);
		int pgResult = sellerHelper.getSplitPgDBCount(orderId);
		log.debug("DB check Tx table splits count: " + txResult + " pg table: " + pgResult);

		AssertJUnit.assertEquals(1, txResult);
		AssertJUnit.assertEquals(1, pgResult);

		sellerHelper.updateOrderReleaseStatusAndPackedOn(OrderReleaseId, "PK");
		sellerHelper.updateOrderLineStatusByLineId(lineId, "QD");
		sellerHelper.rabbitMqReleasePKTx(releaseMessage, orderId);
		Thread.sleep(10000);

		int releaseTxResult = sellerHelper.getTxDBStatusCount(orderId, "RELEASED");
		log.debug("Number of release on DB: " + releaseTxResult);

		AssertJUnit.assertEquals(1, releaseTxResult);
		int refundSettxResult = sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED");
		Assert.assertEquals(refundSettxResult, 1,
				"Release Order_id:" + orderId + " Not available in SPS Settlement detail table");

		log.debug("Wait for 10Sec more to get lineId....");
		Thread.sleep(10000);
		Integer lineId0 = sellerHelper.getLineId(orderId);
		log.debug("lineId: " + lineId);
		SellerPaymentDP.SPS_VectorQty1ExchangeHelper(orderId, store_order_id_ex, OrderReleaseId, OrderReleaseIdex,
				Integer.toString(lineId0), ppsId, orderIdex, lineIdex, ppsIdex, paymentPlanItemIdex,
				paymentPlanItemIdex1, ppsIdexChild);
		String exchangeMessage = sellerHelper.exchangeMessageCreation(Long.parseLong(orderIdex), store_order_id_ex,
				799.0, 799.0, Long.parseLong(OrderReleaseIdex), Long.parseLong(OrderReleaseId), "WP",
				"WORK_IN_PROGRESS", Long.parseLong(lineIdex), lineId0.longValue(), 799.0, 3832, 1, 19, 3832, "A",
				"Added", 1531, "ON_HAND", 799.0, ppsIdex, PaymentMethod.on.name());
		String exchangeReleaseMessage = sellerHelper.exchangeMessageCreation(Long.parseLong(orderIdex),
				store_order_id_ex, 799.0, 799.0, Long.parseLong(OrderReleaseIdex), Long.parseLong(OrderReleaseId), "PK",
				"Completed", Long.parseLong(lineIdex), lineId0.longValue(), 799.0, 3832, 1, 19, 3832, "QD", "Delivered",
				1531, "ON_HAND", 799.0, ppsIdex, PaymentMethod.on.name());

		sellerHelper.rabbitMqAddTx(exchangeMessage, orderIdex);
		Thread.sleep(10000);

		int txResult1 = sellerHelper.getSplitTxDBCount(orderIdex);
		int pgResult1 = sellerHelper.getSplitPgDBCount(orderIdex);
		log.debug("DB check Tx table splits count: " + txResult1 + " pg table: " + pgResult1);

		AssertJUnit.assertEquals(1, txResult1);
		AssertJUnit.assertEquals(1, pgResult1);

		sellerHelper.updateOrderReleaseStatusAndPackedOn(OrderReleaseIdex, "PK");
		sellerHelper.updateOrderLineStatusByLineId(lineIdex, "QD");
		sellerHelper.rabbitMqReleasePKTx(exchangeReleaseMessage, orderIdex);
		sellerHelper.rabbitMqRefundTx(parentOrderRefundMessage, ppsIdex);
		Thread.sleep(10000);
		// sellerHelper.rabbitMqReleasePKTx(exchangeReleaseMessage);
		// sellerHelper.rabbitMqReleasePKTx(exchangeReleaseMessage);
		// int refund = sellerHelper.getNoOfRefund(store_order_id);
		// log.debug("Number of Refund: " + refund);
		int returnTxResult = sellerHelper.getTxDBStatusCount(orderId, "REFUNDED");
		log.debug("Number of release on DB: " + returnTxResult);
		// AssertJUnit.assertEquals(1, refund);
		Assert.assertEquals(returnTxResult, 1,
				"Refund Order Id : " + orderId + " not found in Transaction details table");

		int refundSettxResult1 = sellerHelper.getSettlementdetailDBStatusCount(orderIdex, "RELEASED");
		Assert.assertEquals(refundSettxResult1, 1,
				"REFUND Order_id:" + orderIdex + " Not available in SPS Settlement detail table");

		int refundSettxResult2 = sellerHelper.getSettlementdetailDBStatusCount(orderId, "REFUNDED");
		Assert.assertEquals(refundSettxResult2, 1,
				"REFUND Order_id:" + orderId + " Not available in SPS Settlement detail table");
		Assert.assertEquals(sellerHelper.getSettlementDetailRefundAmount(orderId, "ONLINE"), "-799.00",
				"Online Refund amount:");

		sellerHelper.deleteOrderRecord(orderIdex, ppsIdexChild, paymentPlanItemIdex1);
		sellerHelper.deleteOrderRecord(orderId, ppsId, paymentPlanItemId, ppsIdex, paymentPlanItemIdex);
	}

	@Test(groups = { "Regression",
			"Sanity" }, priority = 0, dataProvider = "SPS_VectorQty1ExchangeCancel", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1. Create online order with Vector qty1 sku1. \n2.check the split in citrus than in SPS.\n3. Mark DL(By creating enrties in DB) and check for release.\n4. Mark exchange check for new order being created"
					+ "\n5. check the refund for the original sku\n6. Cancel the new sku which exchanges\n7. Check the refund for the new sku")
	public void SPS_VectorQty1ExchangeCancel(String orderId, String store_order_id, String OrderReleaseId, String ppsId,
			String paymentPlanItemId, String message, String releaseMessage, String orderIdex, String store_order_id_ex,
			String OrderReleaseIdex, String lineIdex, String ppsIdex, String paymentPlanItemIdex,
			String paymentPlanItemIdex1, String ppsIdexChild, String ppsIdCancellation, String paymentPlanItemIdref,
			String cancellationMessage)
			throws SQLException, IOException, InterruptedException, NumberFormatException, JAXBException {
		log.debug("-----------------orderId: " + orderId + ", ppsId: " + ppsId + ", PaymentPlanId: " + paymentPlanItemId
				+ ", orderIdex: " + orderIdex + ", ppsIdex: " + ppsIdex + ", lineIdex:" + lineIdex);
		log.debug("Child PPSID: " + ppsIdexChild);
		log.debug("Wait for 10 sec to get the lineId....");
		sellerHelper.rabbitMqAddTx(message, orderId);
		Thread.sleep(10000);
		// int splits = sellerHelper.getNoOfSplits(store_order_id);
		// log.debug("Number of Splits: " + splits);
		int txResult = sellerHelper.getSplitTxDBCount(orderId);
		int pgResult = sellerHelper.getSplitPgDBCount(orderId);
		log.debug("DB check Tx table splits count: " + txResult + " pg table: " + pgResult);
		// AssertJUnit.assertEquals(1, splits);
		AssertJUnit.assertEquals(1, txResult);
		AssertJUnit.assertEquals(1, pgResult);
		sellerHelper.rabbitMqReleasePKTx(releaseMessage, orderId);
		Thread.sleep(10000);
		// int release = sellerHelper.getNoOfRelease(store_order_id);
		// log.debug("Number of release: " + release);
		int releaseTxResult = sellerHelper.getTxDBStatusCount(orderId, "RELEASED");
		log.debug("Number of release on DB: " + releaseTxResult);
		// AssertJUnit.assertEquals(1, release);
		AssertJUnit.assertEquals(1, releaseTxResult);
		int releaseSxResult1 = sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED");
		Assert.assertEquals(releaseSxResult1, 1,
				"Release Order Id:  " + orderId + " Not available in SPS Settlement detail table");
		// Assert.assertEquals(sellerHelper.getSettlementDetailFundReleaseAmount(orderId,"RELEASED"),"799.00"
		// ,"Online Release amount:");
		log.debug("Number of releases on SPS Transaction_detail DB: " + releaseSxResult1);
		log.debug("Fund Release Amount :" + sellerHelper.getSettlementDetailFundReleaseAmount(orderId, "RELEASED"));

		log.debug("Wait for 10Sec more to get lineId....");
		Thread.sleep(10000);
		Integer lineId = sellerHelper.getLineId(orderId);
		log.debug("lineId: " + lineId);

		SellerPaymentDP.SPS_VectorQty1ExchangeCancelHelper(orderId, store_order_id, OrderReleaseId, store_order_id_ex,
				Integer.toString(lineId), ppsId, orderIdex, lineIdex, ppsIdex, paymentPlanItemIdex,
				paymentPlanItemIdex1, ppsIdexChild);
		String exchangeMessage = sellerHelper.exchangeMessageCreation(Long.parseLong(orderIdex), store_order_id_ex,
				799.0, 799.0, Long.parseLong(OrderReleaseIdex), Long.parseLong(OrderReleaseId), "WP",
				"WORK_IN_PROGRESS", Long.parseLong(lineIdex), lineId.longValue(), 799.0, 3832, 1, 1, 3832, "A", "Added",
				1530, "ON_HAND", 799.0, ppsIdex, PaymentMethod.on.name());

		sellerHelper.rabbitMqAddTx(exchangeMessage, orderIdex);
		Thread.sleep(10000);
		// int splits1 = sellerHelper.getNoOfSplits(store_order_id_ex);
		// log.debug(splits1);
		int txResult1 = sellerHelper.getSplitTxDBCount(orderIdex);
		int pgResult1 = sellerHelper.getSplitPgDBCount(orderIdex);
		log.debug("DB check Tx table splits count: " + txResult1 + " pg table: " + pgResult1);
		// AssertJUnit.assertEquals(1, splits1);
		AssertJUnit.assertEquals(1, txResult1);
		AssertJUnit.assertEquals(1, pgResult1);

		// int release1 = sellerHelper.getNoOfRelease(store_order_id);
		// log.debug("Number of release: " + release1);
		int releaseTxResult1 = sellerHelper.getTxDBStatusCount(orderId, "RELEASED");
		log.debug("Number of release on DB: " + releaseTxResult1);
		// AssertJUnit.assertEquals(1, release1);
		AssertJUnit.assertEquals(1, releaseTxResult1);
		int releaseSxResult2 = sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED");
		Assert.assertEquals(releaseSxResult2, 1,
				"Release Order Id:  " + orderId + " Not available in SPS Settlement detail table");

		SellerPaymentDP.SPS_VectorQty1ExchangeCancelHelper1(orderId, store_order_id_ex, orderId, lineIdex, ppsIdexChild,
				ppsIdCancellation, paymentPlanItemIdref);
		sellerHelper.rabbitMqRefundTx(cancellationMessage, ppsIdCancellation);
		Thread.sleep(10000);
		// int refund1 = sellerHelper.getNoOfRefund(store_order_id_ex);
		// log.debug("Number of Refund: " + refund1);
		int refundTxResult1 = sellerHelper.getTxDBStatus(orderIdex, "3832", "REFUNDED");
		log.debug("DB status for refund: " + refundTxResult1);
		// AssertJUnit.assertEquals(1, refund1);
		AssertJUnit.assertEquals(1, refundTxResult1);

		sellerHelper.deleteOrderRecord(orderId, ppsIdCancellation, paymentPlanItemIdref);
		sellerHelper.deleteOrderRecord(orderIdex, ppsIdexChild, paymentPlanItemIdex1);
		sellerHelper.deleteOrderRecord(orderId, ppsId, paymentPlanItemId, ppsIdex, paymentPlanItemIdex);
	}

	@Test(groups = { "Regression",
			"Sanity" }, priority = 0, dataProvider = "SPS_VectorSellerMultiQtyExchangeCancel", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1. Create online order with Vector qty2 sku1 and seller sku1qty1. \n2.check 3 split in citrus than in SPS.\n3. Mark DL(By creating enrties in DB) only vector qty and check for 2 release.\n4. Mark exchange for vector 1 qty "
					+ "\n5. check for new order being created only for that exchanged qty"
					+ "\n6. check the refund for the original sku but only qty1\n7. Cancel the new sku which exchanges\n8. Check the refund for that new sku")
	public void SPS_VectorSellerMultiQtyExchangeCancel(String orderId, String ppsId, String paymentPlanItemId,
			String message, String releaseMessage, String orderIdex, String lineId, String lineId1, String lineIdex,
			String ppsIdex, String paymentPlanItemIdex, String paymentPlanItemIdex1, String ppsIdexChild,
			String ppsIdCancellation, String paymentPlanItemIdref, String cancellationMessage, String releaseMessage1,
			String paymentPlanItemId1, String store_order_id, String store_order_id_ex, String orderReleaseId,
			String orderReleaseId1, String OrderReleaseIdex, String parentOrderRefundMessage)
			throws SQLException, IOException, InterruptedException, NumberFormatException, JAXBException {
		log.debug("-----------------orderId: " + orderId + ", ppsId: " + ppsId + ", PaymentPlanId: " + paymentPlanItemId
				+ ", orderIdex: " + orderIdex + ", ppsIdex: " + ppsIdex + ", lineIdex:" + lineIdex);
		log.debug("Child PPSID: " + ppsIdexChild);
		log.debug("Wait for 10 sec to get the lineId....");
		sellerHelper.rabbitMqAddTx(message, orderId);
		Thread.sleep(10000);

		int txResult = sellerHelper.getSplitTxDBCount(orderId);
		int pgResult = sellerHelper.getSplitPgDBCount(orderId);
		log.debug("DB check Tx table splits count: " + txResult + " pg table: " + pgResult);

		AssertJUnit.assertEquals(3, txResult);
		AssertJUnit.assertEquals(1, pgResult);
		sellerHelper.updateOrderReleaseStatusAndPackedOn(orderReleaseId, "PK");
		sellerHelper.updateOrderLineStatusByLineId(lineId, "QD");
		sellerHelper.rabbitMqReleasePKTx(releaseMessage, orderId);
		Thread.sleep(10000);

		int releaseTxResult = sellerHelper.getTxDBStatusCount(orderId, "RELEASED");
		log.debug("Number of release on DB: " + releaseTxResult);
		Assert.assertEquals(releaseTxResult, 2,
				"Release order id" + orderId + " not found in Transaction detail table");

		sellerHelper.updateOrderReleaseStatusAndPackedOn(orderReleaseId1, "PK");
		sellerHelper.updateOrderLineStatusByLineId(lineId1, "QD");
		sellerHelper.rabbitMqReleasePKTx(releaseMessage1, orderId);
		Thread.sleep(10000);

		int releaseTxResult1 = sellerHelper.getTxDBStatusCount(orderId, "RELEASED");
		log.debug("Number of release on DB: " + releaseTxResult1);
		Assert.assertEquals(releaseTxResult1, 3,
				"Release order id" + orderId + " not found in Transaction detail table");

		log.debug("Wait for 10Sec more to get lineId....");
		Thread.sleep(10000);
		Integer lineId0 = sellerHelper.getLineId(orderId);
		log.debug("lineId: " + lineId);

		SellerPaymentDP.SPS_VectorSellerMultiQtyExchangeCancelHelper(orderId, store_order_id, orderReleaseId,
				OrderReleaseIdex, store_order_id_ex, Integer.toString(lineId0), ppsId, orderIdex, lineIdex, ppsIdex,
				paymentPlanItemIdex, paymentPlanItemIdex1, ppsIdexChild);
		String exchangeMessage = sellerHelper.exchangeMessageCreation(Long.parseLong(orderIdex), store_order_id_ex,
				799.0, 799.0, Long.parseLong(OrderReleaseIdex), Long.parseLong(orderReleaseId), "WP",
				"WORK_IN_PROGRESS", Long.parseLong(lineIdex), lineId0.longValue(), 799.0, 3832, 1, 19, 3832, "A",
				"Added", 1530, "ON_HAND", 799.0, ppsIdex, PaymentMethod.on.name());
		String exchangeReleaseMessage = sellerHelper.exchangeMessageCreation(Long.parseLong(orderIdex),
				store_order_id_ex, 799.0, 799.0, Long.parseLong(OrderReleaseIdex), Long.parseLong(orderReleaseId), "PK",
				"Packed", Long.parseLong(lineIdex), lineId0.longValue(), 799.0, 3832, 1, 19, 3832, "QD", "QA Done",
				1530, "ON_HAND", 799.0, ppsIdex, PaymentMethod.on.name());

		sellerHelper.rabbitMqAddTx(exchangeMessage, orderIdex);
		Thread.sleep(10000);

		int txResult1 = sellerHelper.getSplitTxDBCount(orderIdex);
		int pgResult1 = sellerHelper.getSplitPgDBCount(orderIdex);
		log.debug("DB check Tx table splits count: " + txResult1 + " pg table: " + pgResult1);

		Assert.assertEquals(txResult1, 1,
				"Add Order Id:  " + orderIdex + " Not available in SPS Settlement detail table");
		Assert.assertEquals(pgResult1, 1);

		sellerHelper.updateOrderReleaseStatusAndPackedOn(OrderReleaseIdex, "PK");
		sellerHelper.updateOrderLineStatusByLineId(lineIdex, "QD");
		sellerHelper.rabbitMqReleasePKTx(exchangeReleaseMessage, orderIdex);
		sellerHelper.rabbitMqRefundTx(parentOrderRefundMessage, ppsIdex);
		log.debug("Wait for 10Sec more to get lineId....");
		Thread.sleep(10000);

		int releaseSxResult1 = sellerHelper.getSettlementdetailDBStatusCount(orderIdex, "RELEASED");
		Assert.assertEquals(releaseSxResult1, 1,
				"Release Order Id:  " + orderIdex + " Not available in SPS Settlement detail table");

		int returnTxResult = sellerHelper.getTxDBStatusCount(orderId, "REFUNDED");
		log.debug("Number of release on DB: " + returnTxResult);
		Assert.assertEquals(returnTxResult, 1,
				"Refund Order Id :" + orderId + " Not found in Transaction detail table");

		int releaseSxResult2 = sellerHelper.getSettlementdetailDBStatusCount(orderId, "REFUNDED");
		Assert.assertEquals(releaseSxResult2, 1,
				"Refund Order Id:  " + orderId + " Not available in SPS Settlement detail table");

		sellerHelper.deleteOrderRecord(orderId, ppsId, paymentPlanItemId);

	}

	@Test(groups = {
			"Regression" }, priority = 0, dataProvider = "SPS_SellerSettlementRptOnlineMultiQty_Diff_Releases_PK_Return", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1. Create online order with JIT MultiQty MultiSku1 COD(Tatal 4qty). \n2.Release all check released. \3. Check the order details in SPS Settlement detail table. \4. Validate Fund release amount")
	public void SPS_SellerSettlementRptOnlineMultiQty_Diff_Releases_PK_1_Return(String orderId, String store_order_id,
			String OrderReleaseId, String OrderReleaseId1, String lineId, String lineId1, String ppsId,
			String paymentPlanItemId, String paymentPlanItemId1, String message, String releaseFundMessage,
			String ppsIdCancellation, String paymentPlanItemIdref, String paymentPlanItemIdref1, String returnMessage,
			String releaseFundMessage1) throws SQLException, IOException, InterruptedException {
		log.debug(orderId + " : " + ppsId + " : " + paymentPlanItemId);
		sellerHelper.rabbitMqAddTx(message, orderId);
		sellerHelper.updateOrderReleaseStatusAndPackedOn(OrderReleaseId, "PK");
		sellerHelper.updateOrderLineStatusByLineId(lineId, "QD");
		sellerHelper.rabbitMqReleasePKTx(releaseFundMessage, orderId);
		Thread.sleep(10000);
		int releaseTxResult0 = sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED");
		Assert.assertEquals(releaseTxResult0, 1,
				"Release Order id " + orderId + " Not available in SPS Settlement detail table");

		sellerHelper.updateOrderReleaseStatusAndPackedOn(OrderReleaseId1, "PK");
		sellerHelper.updateOrderLineStatusByLineId(lineId1, "QD");
		sellerHelper.rabbitMqReleasePKTx(releaseFundMessage1, orderId);

		log.debug("Wait for 10Sec more to get order details in settlement detail table....");
		Thread.sleep(10000);

		int releaseTxResult = sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED");
		Assert.assertEquals(releaseTxResult, 2,
				"Release Order id " + orderId + "Not available in SPS Settlement detail table");

		SellerPaymentDP.createOnlineOrderWithMultyQtyDiffReleasesAndReturnBoth(orderId, store_order_id,
				paymentPlanItemIdref, ppsId, ppsIdCancellation, paymentPlanItemIdref1);
		sellerHelper.rabbitMqRefundTx(returnMessage, ppsIdCancellation);

		log.debug("Wait for 10Sec more to get order details in settlement detail table....");
		Thread.sleep(10000);

		int releaseTxResult1 = sellerHelper.getSettlementdetailDBStatusCount(orderId, "REFUNDED");
		Assert.assertEquals(releaseTxResult1, 1,
				"Refund Order id " + orderId + "Not available in SPS Settlement detail table");

		sellerHelper.deleteOrderRecord(orderId, ppsId, paymentPlanItemId);

	}

	// @Test(groups = { "Regression" }, priority = 0, dataProvider =
	// "SPS_Online_MultiSku_MultiQty_SingleReleaseAndRefundAll",
	// dataProviderClass = SellerPaymentServiceDP.class, enabled = true,
	// description="1. Create online order with vector and seller each with
	// qty2. \n2.check the split in citrus than in SPS for all skus.\n3. Push PK
	// message for both "
	// + "V&S and check only vector got released and other 2 got
	// pg_settled.\n4.Push PK message for all 4qty one by one and check the
	// vector should not release again and seller should got release one by
	// one.")
	// public void SPS_Online_MultiSku_MultiQty_SingleReleaseAndRefundAll(String
	// orderId, String OrderReleaseId,String ppsId, String paymentPlanItemId,
	// String paymentPlanItemId1, String lineId,
	// String orderMessage, String releaseMessage,
	// String ppsIdCancellation,String paymentPlanItemIdref, String
	// returnMessage,
	// String returnMessage1,String returnMessage2,String ppsIdRefund1,String
	// ppsIdRefund2,String paymentPlanItemIdref1, String paymentPlanItemIdref2)
	// throws SQLException, IOException, InterruptedException{
	//
	//
	// log.debug("---------orderId: "+orderId+" ppsId: "+ppsId+"
	// PymentPlanItemId: "+paymentPlanItemId);
	// sellerHelper.rabbitMqAddTx(orderMessage, orderId);
	// sellerHelper.updateOrderReleaseStatus(OrderReleaseId, "PK");
	// sellerHelper.updateOrderLineStatus(OrderReleaseId, "QD");
	//
	//
	// sellerHelper.rabbitMqReleasePKTx(releaseMessage, orderId);
	// log.debug("Wait for 10Sec more to get order details in
	// settlement detail table....");
	// Thread.sleep(10000);
	// int releaseSettlResult1 =
	// sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED");
	// Assert.assertEquals(releaseSettlResult1,3,"Release Order Id: "+orderId+"
	// Not available in SPS Settlement detail table");
	//
	// SellerPaymentServiceDP.SPS_Online_MultiSku_MultiQty_SingleReleaseAndRefundAllHelper(orderId,OrderReleaseId,
	// paymentPlanItemIdref, ppsId, ppsIdCancellation,
	// ppsIdRefund1,ppsIdRefund2,paymentPlanItemIdref1,paymentPlanItemIdref2);
	// sellerHelper.rabbitMqRefundTx(returnMessage, ppsIdCancellation);
	// log.debug("Wait for 10Sec more to get order details in
	// settlement detail table....");
	// Thread.sleep(10000);
	//
	// int refundSettleResult =
	// sellerHelper.getSettlementdetailDBStatusCount(orderId, "REFUNDED");
	// Assert.assertEquals(refundSettleResult,1 ,"REFUND Order_id:"+orderId+"
	// Not available in SPS Settlement detail table");
	//
	// sellerHelper.rabbitMqRefundTx(returnMessage2);
	// log.debug("Wait for 10Sec more to get order details in
	// settlement detail table....");
	// Thread.sleep(10000);
	//
	// int refundSettleResult1 =
	// sellerHelper.getSettlementdetailDBStatusCount(orderId, "REFUNDED");
	// Assert.assertEquals(refundSettleResult1,2 ,"REFUND Order_id:"+orderId+"
	// Not available in SPS Settlement detail table");
	//
	//
	// sellerHelper.rabbitMqRefundTx(returnMessage1);
	// log.debug("Wait for 10Sec more to get order details in
	// settlement detail table....");
	// Thread.sleep(10000);
	//
	// int refundSettleResult2 =
	// sellerHelper.getSettlementdetailDBStatusCount(orderId, "REFUNDED");
	// Assert.assertEquals(refundSettleResult2,3 ,"REFUND Order_id:"+orderId+"
	// Not available in SPS Settlement detail table");
	//
	// sellerHelper.deleteFromPaymentPlanItemInstrument(paymentPlanItemId1);

	// }

	// Create online order with seller qty1 sku 1 and cancel. Check split and
	// refund for that sku in citrus and Db both
	@Test(groups = { "Regression",
			"Smoke" }, priority = 0, dataProvider = "SPS_SellerMarginCheckForSellerId5", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1. Create online order with seller qty1 sku1. \n2. check the split in citrus than in SPS")
	public void SPS_SellerMarginCheckForSellerId5(String orderId, String OrderReleaseId, String store_order_id,
			String ppsId, String paymentPlanItemId, String message, String lineId, String releaseMessage)
			throws SQLException, IOException, InterruptedException {
		log.debug("----------------" + orderId + " : " + ppsId + " : " + paymentPlanItemId);
		sellerHelper.rabbitMqAddTx(message, orderId);

		sellerHelper.updateOrderReleaseStatusAndPackedOn(OrderReleaseId, "PK");
		sellerHelper.updateOrderLineStatus(orderId, "QD");
		sellerHelper.rabbitMqReleasePKTx(releaseMessage, orderId);
		Thread.sleep(10000);
		int releaseTxResult = sellerHelper.getTxDBStatus(orderId, "1251881", "RELEASED");
		log.debug("DB status for release: " + releaseTxResult);

		AssertJUnit.assertEquals(1, releaseTxResult);

		int releaseSettlResult = sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED");
		Assert.assertEquals(releaseSettlResult, 1,
				"Order Id: " + orderId + " Not available in SPS Settlement detail table");

		String sellerTermStatus = sellerHelper.getSellerTermsMarginStatus("5");

		String settlementDetailMargin = sellerHelper.getSettlementDetailMargin(orderId);
		if (sellerTermStatus.contains("true")) {
			Assert.assertEquals(settlementDetailMargin, "1.33", "Seller Terms Margin Percentage : ");
		} else {
			Assert.assertEquals(settlementDetailMargin, "46.93", "Seller Terms Margin Percentage : ");
		}

		sellerHelper.deleteOrderRecord(orderId, ppsId, paymentPlanItemId);

	}

	/**
	 * 
	 ************************ Scenarios: Release Funds On Packed Status Tests *******
	 */

	@Test(groups = { "Regression",
			"Smoke" }, priority = 0, dataProvider = "SPS_Funds_ReleaseOnPackedAndCancellation", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1. Create online order with seller qty1 sku1. \n2. check the split in citrus than in SPS")
	public void SPS_Funds_ReleaseOnPackedAndCancellation(String orderId, String OrderReleaseId, String OrderReleaseId1,
			String store_order_id, String ppsId, String paymentPlanItemId, String orderMessage, String lineId,
			String releaseMessage, String releaseMessage1, String cancellationMessage, String ppsIdCancellation,
			String paymentPlanItemIdCancel, String paymentPlanItemIdCancel1, String paymentPlanExecutionStatus_id,
			String lineId1) throws SQLException, IOException, InterruptedException {
		log.debug("----------------" + orderId + " : " + ppsId + " : " + paymentPlanItemId);

		sellerHelper.rabbitMqAddTx(orderMessage, orderId);
		Thread.sleep(10000);
		int txResult = sellerHelper.getSplitTxDBCount(orderId);
		int pgResult = sellerHelper.getSplitPgDBCount(orderId);
		log.debug("DB check Tx table splits count: " + txResult + " pg table: " + pgResult);
		Assert.assertEquals(txResult, 3, "Transaction details table, Add Transactions :");
		Assert.assertEquals(pgResult, 1, " PG Settlement table, PG Settled : ");

		sellerHelper.updateOrderReleaseStatusAndPackedOn(OrderReleaseId, "PK");
		sellerHelper.rabbitMqReleasePKTx(releaseMessage, orderId);
		Thread.sleep(10000);
		int releaseSettlResult = sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED");
		Assert.assertEquals(releaseSettlResult, 2,
				"Release Order Id: " + orderId + " Not available in SPS Settlement detail table");

		sellerHelper.updateOrderReleaseStatusAndPackedOn(OrderReleaseId1, "PK");
		sellerHelper.rabbitMqReleasePKTx(releaseMessage1, orderId);
		Thread.sleep(10000);
		int releaseSettlResult1 = sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED");
		Assert.assertEquals(releaseSettlResult1, 3,
				"Release Order Id: " + orderId + " Not available in SPS Settlement detail table");

		SellerPaymentDP.SPS_Funds_ReleaseOnPackedAndCancellation_Helper1(orderId, store_order_id, OrderReleaseId,
				lineId1, ppsId, ppsIdCancellation, paymentPlanItemIdCancel, paymentPlanItemIdCancel1,
				paymentPlanExecutionStatus_id);
		sellerHelper.rabbitMqRefundTx(cancellationMessage, ppsIdCancellation);
		Thread.sleep(10000);
		int refundSettlResult = sellerHelper.getSettlementdetailDBStatusCount(orderId, "REFUNDED");
		Assert.assertEquals(refundSettlResult, 2,
				"REFUND Order Id: " + orderId + " Not available in SPS Settlement detail table");

		sellerHelper.deleteOrderRecord(orderId, ppsId, paymentPlanItemId);

	}

	@Test(groups = { "Regression",
			"Smoke" }, priority = 0, dataProvider = "SPS_Funds_ReleaseOnPackedAndCancellation_RTO", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1. Create online order with seller qty1 sku1. \n2. check the split in citrus than in SPS")
	public void SPS_Funds_ReleaseOnPackedAndCancellation_RTO(String orderId, String OrderReleaseId,
			String OrderReleaseId1, String store_order_id, String ppsId, String paymentPlanItemId, String orderMessage,
			String lineId, String releaseMessage, String releaseMessage1, String cancellationMessage,
			String ppsIdCancellation, String paymentPlanItemIdCancel, String paymentPlanExecutionStatus_id,
			String lineId1, String paymentPlanItemIdrto, String paymentPlanItemIdrto1, String ppsIdRTO,
			String RTOMessage) throws SQLException, IOException, InterruptedException {
		log.debug("----------------" + orderId + " : " + ppsId + " : " + paymentPlanItemId);

		sellerHelper.rabbitMqAddTx(orderMessage, orderId);
		Thread.sleep(10000);
		int txResult = sellerHelper.getSplitTxDBCount(orderId);
		int pgResult = sellerHelper.getSplitPgDBCount(orderId);
		log.debug("DB check Tx table splits count: " + txResult + " pg table: " + pgResult);
		Assert.assertEquals(txResult, 3, "Transaction details table, Add Transactions :");
		Assert.assertEquals(pgResult, 1, " PG Settlement table, PG Settled : ");

		sellerHelper.updateOrderReleaseStatusAndPackedOn(OrderReleaseId, "PK");
		sellerHelper.rabbitMqReleasePKTx(releaseMessage, orderId);
		Thread.sleep(10000);
		int releaseSettlResult = sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED");
		Assert.assertEquals(releaseSettlResult, 2,
				"Release Order Id: " + orderId + " Not available in SPS Settlement detail table");

		sellerHelper.updateOrderReleaseStatusAndPackedOn(OrderReleaseId1, "PK");
		sellerHelper.rabbitMqReleasePKTx(releaseMessage1, orderId);
		Thread.sleep(10000);
		int releaseSettlResult1 = sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED");
		Assert.assertEquals(releaseSettlResult1, 3,
				"Release Order Id: " + orderId + " Not available in SPS Settlement detail table");

		SellerPaymentDP.SPS_Funds_ReleaseOnPackedAndCancellation_Helper(orderId, store_order_id, OrderReleaseId1,
				lineId1, ppsId, ppsIdCancellation, paymentPlanItemIdCancel, paymentPlanExecutionStatus_id);
		sellerHelper.rabbitMqRefundTx(cancellationMessage, ppsIdCancellation);
		Thread.sleep(10000);
		int refundSettlResult = sellerHelper.getSettlementdetailDBStatusCount(orderId, "REFUNDED");
		Assert.assertEquals(refundSettlResult, 1,
				"REFUND Order Id: " + orderId + " Not available in SPS Settlement detail table");

		SellerPaymentDP.SPS_Funds_ReleaseOnPackedAndRTO_Helper(orderId, store_order_id, OrderReleaseId,
				paymentPlanItemIdrto, paymentPlanItemIdrto1, ppsId, ppsIdRTO);
		sellerHelper.rabbitMqRefundTx(RTOMessage, ppsIdRTO);
		Thread.sleep(10000);
		int refundSettlResult1 = sellerHelper.getSettlementdetailDBStatusCount(orderId, "REFUNDED");
		Assert.assertEquals(refundSettlResult1, 3,
				"REFUND Order Id: " + orderId + " Not available in SPS Settlement detail table");
		sellerHelper.deleteOrderRecord(orderId, ppsId, paymentPlanItemId);

	}

	@Test(groups = { "Regression",
			"Smoke" }, priority = 0, dataProvider = "SPS_Funds_Release_PackedLost_And_LostBeforePacked", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1. Create online order with seller qty1 sku1. \n2. check the split in citrus than in SPS")
	public void SPS_Funds_Release_PackedLost_And_LostBeforePacked(String orderId, String OrderReleaseId,
			String OrderReleaseId1, String store_order_id, String ppsId, String paymentPlanItemId, String orderMessage,
			String lineId, String releaseMessage, String releaseMessage1, String packedLostMessage,
			String ppsIdPackedLost, String paymentPlanItemIdref, String paymentPlanExecutionStatus_id, String lineId1,
			String paymentPlanItemIdLostBeforePacked, String paymentPlanItemIdLostBeforePacked1,
			String ppsIdLostBeforePacked, String LostBeforePackedMessage)
			throws SQLException, IOException, InterruptedException {
		log.debug("----------------" + orderId + " : " + ppsId + " : " + paymentPlanItemId);

		sellerHelper.rabbitMqAddTx(orderMessage, orderId);
		Thread.sleep(10000);
		int txResult = sellerHelper.getSplitTxDBCount(orderId);
		int pgResult = sellerHelper.getSplitPgDBCount(orderId);
		log.debug("DB check Tx table splits count: " + txResult + " pg table: " + pgResult);
		Assert.assertEquals(txResult, 3, "Transaction details table, Add Transactions :");
		Assert.assertEquals(pgResult, 1, " PG Settlement table, PG Settled : ");

		// sellerHelper.rabbitMqReleasePKTx(releaseMessage, orderId);
		// int releaseSettlResult =
		// sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED");
		// Assert.assertEquals(releaseSettlResult, 2,
		// "Release Order Id: " + orderId + " Not available in SPS Settlement
		// detail table");
		sellerHelper.updateOrderReleaseStatusAndPackedOn(OrderReleaseId1, "PK");
		sellerHelper.rabbitMqReleasePKTx(releaseMessage1, orderId);
		Thread.sleep(10000);
		int releaseSettlResult1 = sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED");
		Assert.assertEquals(releaseSettlResult1, 1,
				"Release Order Id: " + orderId + " Not available in SPS Settlement detail table");

		SellerPaymentDP.SPS_Funds_Release_PackedLost_Helper(orderId, store_order_id, OrderReleaseId1, lineId1, ppsId,
				ppsIdPackedLost, paymentPlanItemIdref, paymentPlanExecutionStatus_id);
		sellerHelper.rabbitMqRefundTx(packedLostMessage, ppsIdPackedLost);
		Thread.sleep(10000);
		int refundSettlResult = sellerHelper.getSettlementdetailDBStatusCount(orderId, "REFUNDED");
		Assert.assertEquals(refundSettlResult, 1,
				"REFUND Order Id: " + orderId + " Not available in SPS Settlement detail table");

		SellerPaymentDP.SPS_Funds_Release_LostBeforePacked_Helper(orderId, store_order_id, OrderReleaseId1,
				paymentPlanItemIdLostBeforePacked, paymentPlanItemIdLostBeforePacked1, ppsId, ppsIdLostBeforePacked);
		sellerHelper.rabbitMqRefundTx(LostBeforePackedMessage, ppsIdLostBeforePacked);
		Thread.sleep(10000);
		int refundSettlResult1 = sellerHelper.getSettlementdetailDBStatusCount(orderId, "REFUNDED");
		Assert.assertEquals(refundSettlResult1, 3,
				"REFUND Order Id: " + orderId + " Not available in SPS Settlement detail table");
		sellerHelper.deleteOrderRecord(orderId, ppsId, paymentPlanItemId);

	}

	@Test(groups = { "Regression",
			"Smoke" }, priority = 0, dataProvider = "SPS_Funds_Release_ExchangeOrder_Cancel1_And_Packed1_Cancel1", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1. Create online order with seller qty1 sku1. \n2. check the split in citrus than in SPS")
	public void SPS_Funds_Release_ExchangeOrder_Cancel1_And_Packed1_Cancel1(String orderId, String OrderReleaseId,
			String OrderReleaseId1, String store_order_id, String orderMessage, String releaseMessage,
			String releaseMessage1, String ppsId, String paymentPlanItemId, String OrderReleaseIdex,
			String store_order_id_ex, String lineId, String lineId1, String orderIdex, String lineIdex, String ppsIdex,
			String paymentPlanItemIdex0, String paymentPlanItemIdex, String ppsIdexChild,
			String exchangeOrderCancellationMessage, String ppsIdexCancellation, String paymentPlanItemIdrefex,
			String paymentPlanExecutionStatus_Idrefex, String orderIdex1, String store_order_id_ex1,
			String OrderReleaseIdex1, String lineIdex1, String ppsIdex1, String paymentPlanItemIdex1,
			String paymentPlanItemIdex2, String ppsIdexChild1, String ppsIdexCancellation1,
			String paymentPlanItemIdrefex1, String paymentPlanExecutionStatus_idex1,
			String exchangeOrderCancellationMessage1, String parentOrderRefundMessage)
			throws SQLException, IOException, InterruptedException, Exception, Exception {
		log.debug("----------------" + orderId + " : " + ppsId);
		sellerHelper.rabbitMqAddTx(orderMessage, orderId);
		Thread.sleep(10000);
		int txResult = sellerHelper.getSplitTxDBCount(orderId);
		int pgResult = sellerHelper.getSplitPgDBCount(orderId);
		log.debug("DB check Tx table splits count: " + txResult + " pg table: " + pgResult);
		Assert.assertEquals(txResult, 2, "Transaction details table, Add Transactions :");
		Assert.assertEquals(pgResult, 1, " PG Settlement table, PG Settled : ");

		sellerHelper.updateOrderReleaseStatusAndPackedOn(OrderReleaseId, "PK");
		sellerHelper.rabbitMqReleasePKTx(releaseMessage, orderId);
		Thread.sleep(10000);
		int releaseSettlResult = sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED");
		Assert.assertEquals(releaseSettlResult, 1,
				"Release Order Id: " + orderId + " Not available in SPS Settlement detail table");

		sellerHelper.updateOrderReleaseStatusAndPackedOn(OrderReleaseId1, "PK");
		sellerHelper.rabbitMqReleasePKTx(releaseMessage1, orderId);
		Thread.sleep(10000);
		int releaseSettlResult1 = sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED");
		Assert.assertEquals(releaseSettlResult1, 2,
				"Release Order Id: " + orderId + " Not available in SPS Settlement detail table");

		SellerPaymentDP.SPS_Funds_Release_ExchangeOrderCreationHelper(orderId, store_order_id, OrderReleaseId, lineId,
				ppsId, orderIdex, store_order_id_ex, OrderReleaseIdex, lineIdex, ppsIdex, paymentPlanItemIdex0,
				paymentPlanItemIdex, ppsIdexChild);

		String exchangeMessage = sellerHelper.exchangeMessageCreation(Long.parseLong(orderIdex), store_order_id_ex,
				2299.0, 2299.0, Long.parseLong(OrderReleaseIdex), Long.parseLong(OrderReleaseId), "WP",
				"WORK_IN_PROGRESS", Long.parseLong(lineIdex), Long.valueOf(lineId), 2299.0, 1251882, 1, 5, 1251882, "A",
				"Added", 373908, "ON_HAND", 2299.0, ppsIdex, PaymentMethod.on.name());

		sellerHelper.rabbitMqAddTx(exchangeMessage, orderIdex);

		SellerPaymentDP.SPS_Funds_Release_ExchangeOrderCancellation_Helper(orderIdex, store_order_id_ex,
				OrderReleaseIdex, lineIdex, ppsIdexChild, ppsIdexCancellation, paymentPlanItemIdrefex,
				paymentPlanExecutionStatus_Idrefex);
		sellerHelper.rabbitMqRefundTx(exchangeOrderCancellationMessage, ppsIdexCancellation);
		Thread.sleep(10000);
		int refundTxnResult = sellerHelper.getTxDBStatus1(orderIdex, "REFUNDED");
		Assert.assertEquals(refundTxnResult, 1, "Exchange Release Cancellation Failed REFUND Order Id: " + orderIdex
				+ " Not available in SPS Settlement detail table");

		SellerPaymentDP.SPS_Funds_Release_ExchangeOrderCreationHelper1(orderId, store_order_id, OrderReleaseId1,
				lineId1, ppsId, orderIdex1, store_order_id_ex1, OrderReleaseIdex1, lineIdex1, ppsIdex1,
				paymentPlanItemIdex1, paymentPlanItemIdex2, ppsIdexChild1);
		String exchangeMessage1 = sellerHelper.exchangeMessageCreation(Long.parseLong(orderIdex1), store_order_id_ex1,
				799.0, 799.0, Long.parseLong(OrderReleaseIdex1), Long.parseLong(OrderReleaseId1), "WP",
				"WORK_IN_PROGRESS", Long.parseLong(lineIdex1), Long.valueOf(lineId1), 799.0, 1251843, 1, 5, 1251843,
				"A", "Added", 373900, "ON_HAND", 799.0, ppsIdex1, PaymentMethod.on.name());

		sellerHelper.rabbitMqAddTx(exchangeMessage1, orderIdex1);
		Thread.sleep(10000);

		String releaseExchangeMessage = sellerHelper.exchangeMessageCreation(Long.parseLong(orderIdex1),
				store_order_id_ex1, 799.0, 799.0, Long.parseLong(OrderReleaseIdex1), Long.parseLong(OrderReleaseId1),
				"PK", "Packed", Long.parseLong(lineIdex1), Long.valueOf(lineId1), 799.0, 1251843, 1, 5, 1251843, "QD",
				"QA Done", 373900, "ON_HAND", 799.0, ppsIdex1, PaymentMethod.on.name());

		sellerHelper.updateOrderReleaseStatusAndPackedOn(OrderReleaseIdex1, "PK");
		sellerHelper.rabbitMqReleasePKTx(releaseExchangeMessage, orderIdex1);
		sellerHelper.rabbitMqRefundTx(parentOrderRefundMessage, ppsIdex);
		Thread.sleep(10000);
		int releaseSettlResult3 = sellerHelper.getSettlementdetailDBStatusCount(orderIdex1, "RELEASED");
		Assert.assertEquals(releaseSettlResult3, 1,
				"Exchange Release Order Id: " + orderIdex1 + " Not available in SPS Settlement detail table");
		int refundSettlResult = sellerHelper.getSettlementdetailDBStatusCount(orderId, "REFUNDED");
		Assert.assertEquals(refundSettlResult, 1,
				"Exchange REFUND Order Id: " + orderId + " Not available in SPS Settlement detail table");

		SellerPaymentDP.SPS_Funds_Release_ExchangeOrderCancellation_Helper1(orderIdex1, store_order_id_ex1,
				OrderReleaseIdex1, lineIdex1, ppsIdex1, ppsIdexCancellation1, paymentPlanItemIdrefex1,
				paymentPlanExecutionStatus_idex1);
		sellerHelper.rabbitMqRefundTx(exchangeOrderCancellationMessage1, ppsIdexCancellation1);
		Thread.sleep(10000);
		int releaseSettlResult4 = sellerHelper.getSettlementdetailDBStatusCount(orderIdex1, "REFUNDED");
		Assert.assertEquals(releaseSettlResult4, 1, "Exchange Release Cancellation Failed REFUND Order Id: "
				+ orderIdex1 + " Not available in SPS Settlement detail table");

		sellerHelper.deleteOrderRecord(orderId, ppsId, paymentPlanItemId);

	}

	@Test(groups = { "Regression",
			"Smoke" }, priority = 0, dataProvider = "SPS_Funds_Release_ExchangeOrder_Packed_And_RTO", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1. Create online order with seller qty1 sku1. \n2. check the split in citrus than in SPS")
	public void SPS_Funds_Release_ExchangeOrder_Packed_And_RTO(String orderId, String OrderReleaseId,
			String OrderReleaseId1, String store_order_id, String orderMessage, String releaseMessage,
			String releaseMessage1, String ppsId, String paymentPlanItemId, String OrderReleaseIdex,
			String store_order_id_ex, String lineId, String lineId1, String orderIdex, String lineIdex, String ppsIdex,
			String paymentPlanItemIdex0, String paymentPlanItemIdex, String ppsIdexChild,
			String exchangeOrderRTOMessage, String ppsIdexCancellation, String paymentPlanItemIdrefex,
			String paymentPlanExecutionStatus_Idrefex, String parentOrderRefundMessage)
			throws SQLException, IOException, InterruptedException, Exception, Exception {
		log.debug("----------------" + orderId + " : " + ppsId);
		sellerHelper.rabbitMqAddTx(orderMessage, orderId);
		Thread.sleep(10000);
		int txResult = sellerHelper.getSplitTxDBCount(orderId);
		int pgResult = sellerHelper.getSplitPgDBCount(orderId);
		log.debug("DB check Tx table splits count: " + txResult + " pg table: " + pgResult);
		Assert.assertEquals(txResult, 2, "Transaction details table, Add Transactions :");
		Assert.assertEquals(pgResult, 1, " PG Settlement table, PG Settled : ");

		sellerHelper.updateOrderReleaseStatusAndPackedOn(OrderReleaseId, "PK");
		sellerHelper.rabbitMqReleasePKTx(releaseMessage, orderId);
		Thread.sleep(10000);
		int releaseSettlResult = sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED");
		Assert.assertEquals(releaseSettlResult, 1,
				"Release Order Id: " + orderId + " Not available in SPS Settlement detail table");

		sellerHelper.updateOrderReleaseStatusAndPackedOn(OrderReleaseId1, "PK");
		sellerHelper.rabbitMqReleasePKTx(releaseMessage1, orderId);
		Thread.sleep(10000);
		int releaseSettlResult1 = sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED");
		Assert.assertEquals(releaseSettlResult1, 2,
				"Release Order Id: " + orderId + " Not available in SPS Settlement detail table");

		SellerPaymentDP.SPS_Funds_Release_ExchangeOrderCreationHelper(orderId, store_order_id, OrderReleaseId, lineId,
				ppsId, orderIdex, store_order_id_ex, OrderReleaseIdex, lineIdex, ppsIdex, paymentPlanItemIdex0,
				paymentPlanItemIdex, ppsIdexChild);

		String exchangeMessage = sellerHelper.exchangeMessageCreation(Long.parseLong(orderIdex), store_order_id_ex,
				2299.0, 2299.0, Long.parseLong(OrderReleaseIdex), Long.parseLong(OrderReleaseId), "WP",
				"WORK_IN_PROGRESS", Long.parseLong(lineIdex), Long.valueOf(lineId), 2299.0, 1251882, 1, 5, 1251882, "A",
				"Added", 373908, "ON_HAND", 2299.0, ppsIdex, PaymentMethod.on.name());

		sellerHelper.rabbitMqAddTx(exchangeMessage, orderIdex);

		String releaseExchangeMessage = sellerHelper.exchangeMessageCreation(Long.parseLong(orderIdex),
				store_order_id_ex, 2299.0, 2299.0, Long.parseLong(OrderReleaseIdex), Long.parseLong(OrderReleaseId),
				"PK", "Packed", Long.parseLong(lineIdex), Long.valueOf(lineId), 2299.0, 1251882, 1, 5, 1251882, "QD",
				"QA Done", 373908, "ON_HAND", 2299.0, ppsIdex, PaymentMethod.on.name());

		sellerHelper.updateOrderReleaseStatusAndPackedOn(OrderReleaseIdex, "PK");
		sellerHelper.rabbitMqReleasePKTx(releaseExchangeMessage, orderIdex);
		sellerHelper.rabbitMqRefundTx(parentOrderRefundMessage, ppsIdex);
		Thread.sleep(10000);

		int releaseSettlResult3 = sellerHelper.getSettlementdetailDBStatusCount(orderIdex, "RELEASED");
		Assert.assertEquals(releaseSettlResult3, 1,
				"Exchange Release Order Id: " + orderIdex + " Not available in SPS Settlement detail table");

		int refundSettlResult = sellerHelper.getSettlementdetailDBStatusCount(orderId, "REFUNDED");
		Assert.assertEquals(refundSettlResult, 1,
				"Exchange REFUND Order Id: " + orderId + " Not available in SPS Settlement detail table");

		SellerPaymentDP.SPS_Funds_Release_ExchangeOrder_Packed_And_RTO_Helper(orderIdex, store_order_id_ex,
				OrderReleaseIdex, lineIdex, ppsIdexChild, ppsIdexCancellation, paymentPlanItemIdrefex,
				paymentPlanExecutionStatus_Idrefex);
		sellerHelper.rabbitMqRefundTx(exchangeOrderRTOMessage, ppsIdexCancellation);
		Thread.sleep(10000);
		int releaseSettlResult4 = sellerHelper.getSettlementdetailDBStatusCount(orderIdex, "REFUNDED");
		Assert.assertEquals(releaseSettlResult4, 1,
				"Exchange REFUND Order Id: " + orderIdex + " Not available in SPS Settlement detail table");

		sellerHelper.deleteOrderRecord(orderId, ppsId, paymentPlanItemId);

	}

	@Test(groups = { "Regression",
			"Smoke" }, priority = 0, dataProvider = "SPS_Funds_Release_ExchangeOrder_Packed_And_RTO", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1. Create online order with seller qty1 sku1. \n2. check the split in citrus than in SPS")
	public void SPS_Funds_Release_ExchangeOrder_Packed_And_Lost(String orderId, String OrderReleaseId,
			String OrderReleaseId1, String store_order_id, String orderMessage, String releaseMessage,
			String releaseMessage1, String ppsId, String paymentPlanItemId, String OrderReleaseIdex,
			String store_order_id_ex, String lineId, String lineId1, String orderIdex, String lineIdex, String ppsIdex,
			String paymentPlanItemIdex0, String paymentPlanItemIdex, String ppsIdexChild,
			String exchangeOrderRTOMessage, String ppsIdexCancellation, String paymentPlanItemIdrefex,
			String paymentPlanExecutionStatus_Idrefex, String parentOrderRefundMessage)
			throws SQLException, IOException, InterruptedException, Exception, Exception {
		log.debug("----------------" + orderId + " : " + ppsId);
		sellerHelper.rabbitMqAddTx(orderMessage, orderId);
		Thread.sleep(10000);
		int txResult = sellerHelper.getSplitTxDBCount(orderId);
		int pgResult = sellerHelper.getSplitPgDBCount(orderId);
		log.debug("DB check Tx table splits count: " + txResult + " pg table: " + pgResult);
		Assert.assertEquals(txResult, 2, "Transaction details table, Add Transactions :");
		Assert.assertEquals(pgResult, 1, " PG Settlement table, PG Settled : ");

		sellerHelper.updateOrderReleaseStatusAndPackedOn(OrderReleaseId, "PK");
		sellerHelper.rabbitMqReleasePKTx(releaseMessage, orderId);
		Thread.sleep(10000);
		int releaseSettlResult = sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED");
		Assert.assertEquals(releaseSettlResult, 1,
				"Release Order Id: " + orderId + " Not available in SPS Settlement detail table");

		sellerHelper.updateOrderReleaseStatusAndPackedOn(OrderReleaseId1, "PK");
		sellerHelper.rabbitMqReleasePKTx(releaseMessage1, orderId);
		Thread.sleep(10000);
		int releaseSettlResult1 = sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED");
		Assert.assertEquals(releaseSettlResult1, 2,
				"Release Order Id: " + orderId + " Not available in SPS Settlement detail table");

		SellerPaymentDP.SPS_Funds_Release_ExchangeOrderCreationHelper(orderId, store_order_id, OrderReleaseId, lineId,
				ppsId, orderIdex, store_order_id_ex, OrderReleaseIdex, lineIdex, ppsIdex, paymentPlanItemIdex0,
				paymentPlanItemIdex, ppsIdexChild);

		String exchangeMessage = sellerHelper.exchangeMessageCreation(Long.parseLong(orderIdex), store_order_id_ex,
				2299.0, 2299.0, Long.parseLong(OrderReleaseIdex), Long.parseLong(OrderReleaseId), "WP",
				"WORK_IN_PROGRESS", Long.parseLong(lineIdex), Long.valueOf(lineId), 2299.0, 1251882, 1, 5, 1251882, "A",
				"Added", 373908, "ON_HAND", 2299.0, ppsIdex, PaymentMethod.on.name());

		sellerHelper.rabbitMqAddTx(exchangeMessage, orderIdex);

		String releaseExchangeMessage = sellerHelper.exchangeMessageCreation(Long.parseLong(orderIdex),
				store_order_id_ex, 2299.0, 2299.0, Long.parseLong(OrderReleaseIdex), Long.parseLong(OrderReleaseId),
				"PK", "Packed", Long.parseLong(lineIdex), Long.valueOf(lineId), 2299.0, 1251882, 1, 5, 1251882, "QD",
				"QA Done", 373908, "ON_HAND", 2299.0, ppsIdex, PaymentMethod.on.name());

		sellerHelper.updateOrderReleaseStatusAndPackedOn(OrderReleaseIdex, "PK");
		sellerHelper.rabbitMqReleasePKTx(releaseExchangeMessage, orderIdex);
		sellerHelper.rabbitMqRefundTx(parentOrderRefundMessage, ppsIdex);
		Thread.sleep(10000);

		int releaseSettlResult3 = sellerHelper.getSettlementdetailDBStatusCount(orderIdex, "RELEASED");
		Assert.assertEquals(releaseSettlResult3, 1,
				"Exchange Release Order Id: " + orderIdex + " Not available in SPS Settlement detail table");

		int refundSettlResult = sellerHelper.getSettlementdetailDBStatusCount(orderId, "REFUNDED");
		Assert.assertEquals(refundSettlResult, 1,
				"Exchange REFUND Order Id: " + orderId + " Not available in SPS Settlement detail table");

		SellerPaymentDP.SPS_Funds_Release_ExchangeOrder_Packed_And_Lost_Helper(orderIdex, store_order_id_ex,
				OrderReleaseIdex, lineIdex, ppsIdexChild, ppsIdexCancellation, paymentPlanItemIdrefex,
				paymentPlanExecutionStatus_Idrefex);
		String refundMessage = RefundType.RELEASE_LOST_REFUND.toString();
		String exchangeOrderLostRefundMessage = sellerHelper.RefundMessageCreation(ppsIdexCancellation,
				OrderReleaseIdex, refundMessage);
		sellerHelper.rabbitMqRefundTx(exchangeOrderLostRefundMessage, ppsIdexCancellation);
		Thread.sleep(10000);
		int releaseSettlResult4 = sellerHelper.getSettlementdetailDBStatusCount(orderIdex, "REFUNDED");
		Assert.assertEquals(releaseSettlResult4, 1,
				"Exchange REFUND Order Id: " + orderIdex + " Not available in SPS Settlement detail table");

		sellerHelper.deleteOrderRecord(orderId, ppsId, paymentPlanItemId);

	}

	@Test(groups = { "Regression",
			"Smoke" }, priority = 0, dataProvider = "SPS_Funds_Release_ExchangeOrder_Packed_And_RTO", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1. Create online order with seller qty1 sku1. \n2. check the split in citrus than in SPS")
	public void SPS_Funds_Release_ExchangeOrder_Lost_Before_Packed(String orderId, String OrderReleaseId,
			String OrderReleaseId1, String store_order_id, String orderMessage, String releaseMessage,
			String releaseMessage1, String ppsId, String paymentPlanItemId, String OrderReleaseIdex,
			String store_order_id_ex, String lineId, String lineId1, String orderIdex, String lineIdex, String ppsIdex,
			String paymentPlanItemIdex0, String paymentPlanItemIdex, String ppsIdexChild,
			String exchangeOrderRTOMessage, String ppsIdexCancellation, String paymentPlanItemIdrefex,
			String paymentPlanExecutionStatus_Idrefex)
			throws SQLException, IOException, InterruptedException, Exception, Exception {
		log.debug("----------------" + orderId + " : " + ppsId);
		sellerHelper.rabbitMqAddTx(orderMessage, orderId);
		Thread.sleep(10000);
		int txResult = sellerHelper.getSplitTxDBCount(orderId);
		int pgResult = sellerHelper.getSplitPgDBCount(orderId);
		log.debug("DB check Tx table splits count: " + txResult + " pg table: " + pgResult);
		Assert.assertEquals(txResult, 2, "Transaction details table, Add Transactions :");
		Assert.assertEquals(pgResult, 1, " PG Settlement table, PG Settled : ");

		sellerHelper.updateOrderReleaseStatusAndPackedOn(OrderReleaseId, "PK");
		sellerHelper.rabbitMqReleasePKTx(releaseMessage, orderId);
		Thread.sleep(10000);
		int releaseSettlResult = sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED");
		Assert.assertEquals(releaseSettlResult, 1,
				"Release Order Id: " + orderId + " Not available in SPS Settlement detail table");

		sellerHelper.updateOrderReleaseStatusAndPackedOn(OrderReleaseId1, "PK");
		sellerHelper.rabbitMqReleasePKTx(releaseMessage1, orderId);
		Thread.sleep(10000);
		int releaseSettlResult1 = sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED");
		Assert.assertEquals(releaseSettlResult1, 2,
				"Release Order Id: " + orderId + " Not available in SPS Settlement detail table");

		SellerPaymentDP.SPS_Funds_Release_ExchangeOrderCreationHelper(orderId, store_order_id, OrderReleaseId, lineId,
				ppsId, orderIdex, store_order_id_ex, OrderReleaseIdex, lineIdex, ppsIdex, paymentPlanItemIdex0,
				paymentPlanItemIdex, ppsIdexChild);

		String exchangeMessage = sellerHelper.exchangeMessageCreation(Long.parseLong(orderIdex), store_order_id_ex,
				2299.0, 2299.0, Long.parseLong(OrderReleaseIdex), Long.parseLong(OrderReleaseId), "WP",
				"WORK_IN_PROGRESS", Long.parseLong(lineIdex), Long.valueOf(lineId), 2299.0, 1251882, 1, 5, 1251882, "A",
				"Added", 373908, "ON_HAND", 2299.0, ppsIdex, PaymentMethod.on.name());

		sellerHelper.rabbitMqAddTx(exchangeMessage, orderIdex);

		SellerPaymentDP.SPS_Funds_Release_ExchangeOrder_Packed_And_Lost_Helper(orderIdex, store_order_id_ex,
				OrderReleaseIdex, lineIdex, ppsIdexChild, ppsIdexCancellation, paymentPlanItemIdrefex,
				paymentPlanExecutionStatus_Idrefex);
		String refundMessage = RefundType.RELEASE_LOST_REFUND.toString();
		String exchangeOrderLostRefundMessage = sellerHelper.RefundMessageCreation(ppsIdexCancellation,
				OrderReleaseIdex, refundMessage);
		sellerHelper.rabbitMqRefundTx(exchangeOrderLostRefundMessage, ppsIdexCancellation);
		Thread.sleep(10000);
		int releaseSettlResult4 = sellerHelper.getSettlementdetailDBStatusCount(orderIdex, "REFUNDED");
		Assert.assertEquals(releaseSettlResult4, 1,
				"Exchange REFUND Order Id: " + orderIdex + " Not available in SPS Settlement detail table");

		sellerHelper.deleteOrderRecord(orderId, ppsId, paymentPlanItemId);

	}

	@Test(groups = { "Regression",
			"Smoke" }, priority = 0, dataProvider = "SPS_Funds_Release_ExchangeOfExchangePacked", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1. Create online order with seller qty1 sku1. \n2. check the split in citrus than in SPS")
	public void SPS_Funds_Release_ExchangeOfExchangePacked(String orderId, String OrderReleaseId,
			String OrderReleaseId1, String store_order_id, String orderMessage, String releaseMessage,
			String releaseMessage1, String ppsId, String paymentPlanItemId, String OrderReleaseIdex,
			String store_order_id_ex, String lineId, String lineId1, String orderIdex, String lineIdex, String ppsIdex,
			String paymentPlanItemIdex0, String paymentPlanItemIdex, String ppsIdexChild, String paymentPlanItemIdrefex,
			String paymentPlanExecutionStatus_Idrefex, String orderIdex1, String store_order_id_ex1,
			String OrderReleaseIdex1, String lineIdex1, String ppsIdex1, String paymentPlanItemIdex1,
			String paymentPlanItemIdex2, String ppsIdexChild1, String parentOrderRefundMessage,
			String parentOrderRefundMessage1)
			throws SQLException, IOException, InterruptedException, Exception, Exception {
		log.debug("----------------" + orderId + " : " + ppsId);
		sellerHelper.rabbitMqAddTx(orderMessage, orderId);
		Thread.sleep(10000);
		int txResult = sellerHelper.getSplitTxDBCount(orderId);
		int pgResult = sellerHelper.getSplitPgDBCount(orderId);
		log.debug("DB check Tx table splits count: " + txResult + " pg table: " + pgResult);
		Assert.assertEquals(txResult, 2, "Transaction details table, Add Transactions :");
		Assert.assertEquals(pgResult, 1, " PG Settlement table, PG Settled : ");

		sellerHelper.rabbitMqReleasePKTx(releaseMessage, orderId);
		Thread.sleep(10000);
		int releaseSettlResult = sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED");
		Assert.assertEquals(releaseSettlResult, 1,
				"Release Order Id: " + orderId + " Not available in SPS Settlement detail table");

		sellerHelper.rabbitMqReleasePKTx(releaseMessage1, orderId);
		Thread.sleep(10000);
		int releaseSettlResult1 = sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED");
		Assert.assertEquals(releaseSettlResult1, 2,
				"Release Order Id: " + orderId + " Not available in SPS Settlement detail table");

		SellerPaymentDP.SPS_Funds_Release_ExchangeOrderCreationHelper(orderId, store_order_id, OrderReleaseId, lineId,
				ppsId, orderIdex, store_order_id_ex, OrderReleaseIdex, lineIdex, ppsIdex, paymentPlanItemIdex0,
				paymentPlanItemIdex, ppsIdexChild);

		String exchangeMessage = sellerHelper.exchangeMessageCreation(Long.parseLong(orderIdex), store_order_id_ex,
				2299.0, 2299.0, Long.parseLong(OrderReleaseIdex), Long.parseLong(OrderReleaseId), "WP",
				"WORK_IN_PROGRESS", Long.parseLong(lineIdex), Long.valueOf(lineId), 2299.0, 1251882, 1, 5, 1251882, "A",
				"Added", 373908, "ON_HAND", 2299.0, ppsIdex, PaymentMethod.on.name());

		sellerHelper.rabbitMqAddTx(exchangeMessage, orderIdex);

		String releaseExchangeMessage = sellerHelper.exchangeMessageCreation(Long.parseLong(orderIdex),
				store_order_id_ex, 2299.0, 2299.0, Long.parseLong(OrderReleaseIdex), Long.parseLong(OrderReleaseId),
				"PK", "Packed", Long.parseLong(lineIdex), Long.valueOf(lineId), 2299.0, 1251882, 1, 5, 1251882, "QD",
				"QA Done", 373908, "ON_HAND", 2299.0, ppsIdex, PaymentMethod.on.name());

		sellerHelper.rabbitMqReleasePKTx(releaseExchangeMessage, orderIdex);
		sellerHelper.rabbitMqRefundTx(parentOrderRefundMessage, ppsIdex);

		Thread.sleep(10000);

		int releaseSettlResult3 = sellerHelper.getSettlementdetailDBStatusCount(orderIdex, "RELEASED");
		Assert.assertEquals(releaseSettlResult3, 1,
				"Exchange Release Order Id: " + orderIdex + " Not available in SPS Settlement detail table");
		int refundSettlResult = sellerHelper.getSettlementdetailDBStatusCount(orderId, "REFUNDED");
		Assert.assertEquals(refundSettlResult, 1,
				"Exchange REFUND Order Id: " + orderId + " Not available in SPS Settlement detail table");

		SellerPaymentDP.SPS_Funds_Release_ExchangeofExchangeOrderCreationHelper(orderIdex, store_order_id_ex,
				OrderReleaseIdex, lineIdex, ppsIdex, orderIdex1, store_order_id_ex1, OrderReleaseIdex1, lineIdex1,
				ppsIdex1, paymentPlanItemIdex1, paymentPlanItemIdex2, ppsIdexChild1);

		String exchangeMessage2 = sellerHelper.exchangeMessageCreation(Long.parseLong(orderIdex1), store_order_id_ex1,
				2299.0, 2299.0, Long.parseLong(OrderReleaseIdex1), Long.parseLong(OrderReleaseIdex), "WP",
				"WORK_IN_PROGRESS", Long.parseLong(lineIdex1), Long.valueOf(lineIdex), 2299.0, 1251883, 1, 5, 1251883,
				"A", "Added", 373908, "ON_HAND", 2299.0, ppsIdex1, PaymentMethod.on.name());

		sellerHelper.rabbitMqAddTx(exchangeMessage2, orderIdex1);

		String releaseExchangeMessage2 = sellerHelper.exchangeMessageCreation(Long.parseLong(orderIdex1),
				store_order_id_ex1, 2299.0, 2299.0, Long.parseLong(OrderReleaseIdex1), Long.parseLong(OrderReleaseIdex),
				"PK", "Packed", Long.parseLong(lineIdex1), Long.valueOf(lineIdex), 2299.0, 1251883, 1, 5, 1251883, "QD",
				"QA Done", 373908, "ON_HAND", 2299.0, ppsIdex1, PaymentMethod.on.name());
		sellerHelper.rabbitMqReleasePKTx(releaseExchangeMessage2, orderIdex1);
		sellerHelper.rabbitMqRefundTx(parentOrderRefundMessage1, ppsIdex1);
		Thread.sleep(10000);

		int releaseSettlResult4 = sellerHelper.getSettlementdetailDBStatusCount(orderIdex1, "RELEASED");
		Assert.assertEquals(releaseSettlResult4, 1,
				"Exchange Release Order Id: " + orderIdex1 + " Not available in SPS Settlement detail table");
		int refundSettlResult1 = sellerHelper.getSettlementdetailDBStatusCount(orderIdex, "REFUNDED");
		Assert.assertEquals(refundSettlResult1, 1,
				"Exchange REFUND Order Id: " + orderIdex + " Not available in SPS Settlement detail table");

		sellerHelper.deleteOrderRecord(orderId, ppsId, paymentPlanItemId);

	}

	@Test(groups = {
			"Regression" }, priority = 0, dataProvider = "SPS_FundsRelease_OrderDeliveredAndRTO", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1.Create online order with JIT qty1 sku1. \n2.check the split in citrus than in SPS. \n3.mark overwrite price. "
					+ "\n4.check that the fund deducted from split, order amount should remain same on citrus\n5.check split amount deducted on DB\n6.check refund for the override amount")
	public void SPS_FundsRelease_OrderDeliveredAndRTOAndGSTCalculation(String orderId, String OrderReleaseId,
			String OrderReleaseId1, String store_order_id, String orderMessage, String releaseMessage,
			String releaseMessage1, String ppsId, String paymentPlanItemId, String RTORefundMessage, String lineId,
			String ppsIdRTO, String paymentPlanItemIdref, String paymentPlanExecutionStatus_id, String sellerId,
			String sellerId1) throws Exception {

		log.debug(orderId + " : " + ppsId + " : " + paymentPlanItemId);
		sellerHelper.rabbitMqAddTx(orderMessage, orderId);
		Thread.sleep(10000);
		sellerHelper.updateOrderReleaseStatusAndPackedOn(OrderReleaseId, "PK");
		sellerHelper.rabbitMqReleasePKTx(releaseMessage, orderId);
		Thread.sleep(10000);
		log.debug("Number of release on DB: " + sellerHelper.getTxDBStatusCount(orderId, "RELEASED"));
		Assert.assertEquals(sellerHelper.getTxDBStatusCount(orderId, "RELEASED"), 1,
				"Release Order not found in Transaction table");
		Assert.assertEquals(sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED"), 1,
				"Release Order_id:" + orderId + " Not available in SPS Settlement detail table");

		BigDecimal settlementCommissionAmount = sellerHelper.getSettlementDetailCommissionAmount(OrderReleaseId);
		BigDecimal settlementCgstTaxRate = sellerHelper.getSettlementDetailCgstTaxRate(OrderReleaseId);
		BigDecimal settlementSgstTaxRate = sellerHelper.getSettlementDetailSgstTaxRate(OrderReleaseId);
		BigDecimal settlementCgstTaxAmount = sellerHelper.getSettlementDetailCgstTaxAmount(OrderReleaseId);
		BigDecimal settlementSgstTaxAmount = sellerHelper.getSettlementDetailSgstTaxAmount(OrderReleaseId);
		String pincode = sellerHelper.getSellerBillingPinCode(sellerId);

		LmsServiceHelper lmsServiceHelper = new LmsServiceHelper();
		PincodeResponseV2 pincodeResponseV2 = lmsServiceHelper.getPincodeV1(pincode);
		List<PincodeEntryV2> pincodeEntryV2 = pincodeResponseV2.getPincodes();
		String destinationStateCode = pincodeEntryV2.get(0).getStateCode();
		TaxResponse taxResponse = sellerHelper.getTaxMasterServiceGST_Tax("KA", destinationStateCode, "1011");

		List<TaxEntry> data = taxResponse.getData();
		Map<TaxType, Double> map = new HashMap<TaxType, Double>();
		for (int i = 0; i < data.size(); i++) {
			TaxEntry taxEntry = data.get(i);
			map.put(taxEntry.getTaxType(), taxEntry.getTaxRate());
		}

		Assert.assertEquals(map.get(TaxType.CGST).doubleValue(), settlementCgstTaxRate.doubleValue(), " CGST Rate :");
		Assert.assertEquals(map.get(TaxType.SGST).doubleValue(), settlementSgstTaxRate.doubleValue(), " SGST Rate :");

		Double cgstCalc = (settlementCommissionAmount.doubleValue() * map.get(TaxType.CGST).doubleValue())
				/ (1 + map.get(TaxType.CGST) + map.get(TaxType.SGST)) * -1;
		Double SgstCalc = (settlementCommissionAmount.doubleValue() * map.get(TaxType.SGST).doubleValue())
				/ (1 + map.get(TaxType.CGST) + map.get(TaxType.SGST)) * -1;
		log.debug("CGST Tax Amount Calculation: " + cgstCalc);
		log.debug("SGST Tax Amount Calculation: " + SgstCalc);

		Assert.assertEquals(settlementCgstTaxAmount.doubleValue(), cgstCalc.doubleValue(), " CGST Tax Amount :");
		Assert.assertEquals(settlementSgstTaxAmount.doubleValue(), SgstCalc.doubleValue(), " SGST Tax Amount :");

		sellerHelper.updateOrderReleaseStatusAndPackedOn(OrderReleaseId1, "PK");
		sellerHelper.rabbitMqReleasePKTx(releaseMessage1, orderId);
		Thread.sleep(10000);
		log.debug("Number of release on DB: " + sellerHelper.getTxDBStatusCount(orderId, "RELEASED"));
		Assert.assertEquals(sellerHelper.getTxDBStatusCount(orderId, "RELEASED"), 2,
				"Release Order not found in Transaction table");
		Assert.assertEquals(sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED"), 2,
				"Release Order_id:" + orderId + " Not available in SPS Settlement detail table");

		BigDecimal settlementCommissionAmount1 = sellerHelper.getSettlementDetailCommissionAmount(OrderReleaseId1);
		BigDecimal settlementIgstTaxRate = sellerHelper.getSettlementDetailIgstTaxRate(OrderReleaseId1);
		BigDecimal settlementIgstTaxAmount = sellerHelper.getSettlementDetailIgstTaxAmount(OrderReleaseId1);
		String pincode1 = sellerHelper.getSellerBillingPinCode(sellerId1);

		PincodeResponseV2 pincodeResponseV21 = lmsServiceHelper.getPincodeV1(pincode1);
		List<PincodeEntryV2> pincodeEntryV21 = pincodeResponseV21.getPincodes();
		String destinationStateCode1 = pincodeEntryV21.get(0).getStateCode();
		TaxResponse taxResponse1 = sellerHelper.getTaxMasterServiceGST_Tax("KA", destinationStateCode1, "1011");

		List<TaxEntry> data1 = taxResponse1.getData();
		Map<TaxType, Double> map1 = new HashMap<TaxType, Double>();
		for (int i = 0; i < data1.size(); i++) {
			TaxEntry taxEntry = data1.get(i);
			map1.put(taxEntry.getTaxType(), taxEntry.getTaxRate());
		}

		Assert.assertEquals(map1.get(TaxType.IGST).doubleValue(), settlementIgstTaxRate.doubleValue(), " IGST Rate :");

		Double igstCalc = (settlementCommissionAmount1.doubleValue() * map1.get(TaxType.IGST).doubleValue())
				/ (1 + map1.get(TaxType.IGST)) * -1;
		igstCalc = (double) Math.round(igstCalc * 100) / 100;

		log.debug("IGST Tax Amount Calculation: " + igstCalc);
		Assert.assertEquals(settlementIgstTaxAmount.doubleValue(), igstCalc.doubleValue(), " IGST Tax Amount :");

		SellerPaymentDP.SPS_FundsRelease_OrderDeliveredAndRTO_Helper(orderId, store_order_id, OrderReleaseId1, lineId,
				ppsId, ppsIdRTO, paymentPlanItemIdref, paymentPlanExecutionStatus_id);
		sellerHelper.rabbitMqRefundTx(RTORefundMessage, ppsIdRTO);
		Thread.sleep(10000);
		log.debug("Number of release on DB: " + sellerHelper.getTxDBStatusCount(orderId, "REFUNDED"));
		Assert.assertEquals(sellerHelper.getTxDBStatusCount(orderId, "REFUNDED"), 1,
				"Refund Order not found in Transaction table");
		Assert.assertEquals(sellerHelper.getSettlementdetailDBStatusCount(orderId, "REFUNDED"), 1,
				"Refund Order_id:" + orderId + " Not available in SPS Settlement detail table");

		BigDecimal settlementCommissionAmountRefund = sellerHelper.getSettlementDetailCommissionAmount(OrderReleaseId1,
				"REFUNDED");
		BigDecimal settlementIgstTaxRateRefund = sellerHelper.getSettlementDetailIgstTaxRate(OrderReleaseId1);
		BigDecimal settlementIgstTaxAmountRefund = sellerHelper.getSettlementDetailIgstTaxAmount(OrderReleaseId1,
				"REFUNDED");
		String pincodeRefund = sellerHelper.getSellerBillingPinCode(sellerId1);

		PincodeResponseV2 pincodeResponseV2Refund = lmsServiceHelper.getPincodeV1(pincodeRefund);
		List<PincodeEntryV2> pincodeEntryV2Refund = pincodeResponseV2Refund.getPincodes();
		String destinationStateCodeRefund = pincodeEntryV2Refund.get(0).getStateCode();
		TaxResponse taxResponseRefund = sellerHelper.getTaxMasterServiceGST_Tax("KA", destinationStateCodeRefund,
				"1011");

		List<TaxEntry> dataRefund = taxResponseRefund.getData();
		Map<TaxType, Double> mapRefund = new HashMap<TaxType, Double>();
		for (int i = 0; i < dataRefund.size(); i++) {
			TaxEntry taxEntry = dataRefund.get(i);
			mapRefund.put(taxEntry.getTaxType(), taxEntry.getTaxRate());
		}

		Assert.assertEquals(mapRefund.get(TaxType.IGST).doubleValue(), settlementIgstTaxRateRefund.doubleValue(),
				" IGST Rate :");

		Double igstCalcRefund = (settlementCommissionAmountRefund.doubleValue()
				* mapRefund.get(TaxType.IGST).doubleValue()) / (1 + mapRefund.get(TaxType.IGST));
		igstCalcRefund = (double) Math.round(igstCalcRefund * 100) / 100;
		log.debug("IGST Refund Tax Amount Calculation: " + igstCalcRefund);

		Assert.assertEquals(settlementIgstTaxAmountRefund.doubleValue(), igstCalcRefund.doubleValue(),
				" IGST Refund Tax Amount :");

		sellerHelper.deleteOrderRecord(orderId, ppsId, paymentPlanItemId, ppsIdRTO, paymentPlanItemIdref);
	}

	@Test(groups = {
			"Regression" }, priority = 0, dataProvider = "SPS_FundsRelease_SellersWithDifferentPayouts", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1.Create online order with JIT qty1 sku1. \n2.check the split in citrus than in SPS. \n3.mark overwrite price. "
					+ "\n4.check that the fund deducted from split, order amount should remain same on citrus\n5.check split amount deducted on DB\n6.check refund for the override amount")
	public void SPS_FundsRelease_SellersWithDifferentPayouts(String orderId, String OrderReleaseId,
			String store_order_id, String orderMessage, String releaseMessage, String ppsId, String paymentPlanItemId,
			String lineId) throws SQLException, IOException, InterruptedException, JAXBException {
		log.debug(orderId + " : " + ppsId + " : " + paymentPlanItemId);
		sellerHelper.rabbitMqAddTx(orderMessage, orderId);
		Thread.sleep(10000);
		Assert.assertEquals(sellerHelper.getTxDBStatusCount(orderId, "ADDED"), 1,
				"Add Order " + orderId + " not found in Transaction table");
		sellerHelper.updateOrderReleaseStatusAndPackedOn(OrderReleaseId, "PK");
		sellerHelper.rabbitMqReleasePKTx(releaseMessage, orderId);
		Thread.sleep(10000);
		log.debug("Number of release on DB: " + sellerHelper.getTxDBStatusCount(orderId, "RELEASED"));
		Assert.assertEquals(sellerHelper.getTxDBStatusCount(orderId, "RELEASED"), 0,
				"Release Order not found in Transaction table");
		Assert.assertEquals(sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED"), 0,
				"Release Order_id:" + orderId + " Not available in SPS Settlement detail table");

		sellerHelper.deleteOrderRecord(orderId, ppsId, paymentPlanItemId);
	}

	@Test(groups = { "Regression",
			"Smoke" }, priority = 0, dataProvider = "SPS_SellerQty1PhonePe", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1. Create online order with seller qty1 sku1. \n2. check the split in citrus than in SPS")
	public void SPS_SellerQty1PhonePe(String orderId, String store_order_id, String ppsId, String paymentPlanItemId,
			String message, String lineId, String releaseFundMessage, String paymentPlanItemIdref,
			String ppsIdCancellation, String returnMessage) throws SQLException, IOException, InterruptedException {
		log.debug("----------------" + orderId + " : " + ppsId + " : " + paymentPlanItemId);
		sellerHelper.rabbitMqAddTx(message, orderId);
		Thread.sleep(10000);
		// int splits = sellerHelper.getNoOfSplits(store_order_id);
		// log.debug("Number of Splits: " + splits);
		int txResult = sellerHelper.getSplitTxDBCount(orderId);
		int pgResult = sellerHelper.getSplitPgDBCount(orderId);
		log.debug("DB check Tx table splits count: " + txResult + " pg table: " + pgResult);
		// AssertJUnit.assertEquals(1, splits);
		AssertJUnit.assertEquals(1, txResult);
		AssertJUnit.assertEquals(1, pgResult);
		Assert.assertEquals(sellerHelper.getTxAmount(orderId), "799.00");

		sellerHelper.updateOrderReleaseStatus(orderId, "PK");
		sellerHelper.updateOrderLineStatus(orderId, "QD");
		sellerHelper.rabbitMqReleasePKTx(releaseFundMessage, orderId);
		Thread.sleep(10000);
		// int release = sellerHelper.getNoOfRelease(store_order_id);
		// log.debug("Number of release: " + release);
		int releaseTxResult = sellerHelper.getTxDBStatus1(orderId, "RELEASED");

		log.debug("DB status for release: " + releaseTxResult);
		// Assert.assertEquals(release, 1, "Order Id: " + orderId + " Not
		// available in SPS Transaction detail table");
		Assert.assertEquals(releaseTxResult, 1, "sps transaction_detail status :");

		int releaseTxResult1 = sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED");
		Assert.assertEquals(releaseTxResult1, 1,
				"Order Id: " + orderId + " Not available in SPS Settlement detail table");
		Assert.assertEquals(sellerHelper.getSettlementDetailFundReleaseAmount(orderId, "RELEASED"), "799.00",
				"Online Release amount:");

		log.debug("Number of releases on SPS Transaction_detail DB: " + releaseTxResult1);
		log.debug("Fund Release Amount :" + sellerHelper.getSettlementDetailFundReleaseAmount(orderId, "RELEASED"));

		SellerPaymentDP.create_Online_PhonePe_OrderWithSellerQty1ReleaseHelper(orderId, store_order_id,
				paymentPlanItemIdref, ppsId, ppsIdCancellation);
		sellerHelper.rabbitMqRefundTx(returnMessage, ppsIdCancellation);

		log.debug("Wait for 10Sec more to get order details in settlement detail table....");
		Thread.sleep(10000);

		int releaseTxResult2 = sellerHelper.getSettlementdetailDBStatusCount(orderId, "REFUNDED");
		Assert.assertEquals(releaseTxResult2, 1,
				"REFUND Order_id:" + orderId + " Not available in SPS Settlement detail table");
		// Assert.assertEquals(sellerHelper.getSettlementDetailRefundAmount(orderId,"CASH_ON_DELIVERY"),"-1399.00"
		// ,"COD Refund amount:");
		Assert.assertEquals(sellerHelper.getSettlementDetailRefundAmount(orderId, "ONLINE"), "-799.00",
				"Online Refund amount:");

		sellerHelper.deleteFromPaymentPlanItemInstrument(paymentPlanItemId);
		sellerHelper.deleteOrderRecord(orderId, ppsId, paymentPlanItemId);

	}

	@Test(groups = { "Regression",
			"Smoke" }, priority = 0, dataProvider = "SPS_SellerQty1Mynts", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1. Create online order with seller qty1 sku1. \n2. check the split in citrus than in SPS")
	public void SPS_SellerQty1Mynts(String orderId, String store_order_id, String ppsId, String paymentPlanItemId,
			String message, String lineId, String releaseFundMessage, String paymentPlanItemIdref,
			String ppsIdCancellation, String returnMessage) throws SQLException, IOException, InterruptedException {
		log.debug("----------------" + orderId + " : " + ppsId + " : " + paymentPlanItemId);
		sellerHelper.rabbitMqAddTx(message, orderId);
		Thread.sleep(10000);
		// int splits = sellerHelper.getNoOfSplits(store_order_id);
		// log.debug("Number of Splits: " + splits);
		int txResult = sellerHelper.getSplitTxDBCount(orderId);
		int pgResult = sellerHelper.getSplitPgDBCount(orderId);
		log.debug("DB check Tx table splits count: " + txResult + " pg table: " + pgResult);
		// AssertJUnit.assertEquals(1, splits);
		AssertJUnit.assertEquals(1, txResult);
		AssertJUnit.assertEquals(1, pgResult);
		Assert.assertEquals(sellerHelper.getTxAmount(orderId), "799.00");

		sellerHelper.updateOrderReleaseStatus(orderId, "PK");
		sellerHelper.updateOrderLineStatus(orderId, "QD");
		sellerHelper.rabbitMqReleasePKTx(releaseFundMessage, orderId);
		Thread.sleep(10000);
		// int release = sellerHelper.getNoOfRelease(store_order_id);
		// log.debug("Number of release: " + release);
		int releaseTxResult = sellerHelper.getTxDBStatus1(orderId, "RELEASED");

		log.debug("DB status for release: " + releaseTxResult);
		// Assert.assertEquals(release, 1, "Order Id: " + orderId + " Not
		// available in SPS Transaction detail table");
		Assert.assertEquals(releaseTxResult, 1, "sps transaction_detail status :");

		int releaseTxResult1 = sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED");
		Assert.assertEquals(releaseTxResult1, 1,
				"Order Id: " + orderId + " Not available in SPS Settlement detail table");
		Assert.assertEquals(sellerHelper.getSettlementDetailFundReleaseAmount(orderId, "RELEASED"), "799.00",
				"Online Release amount:");

		log.debug("Number of releases on SPS Transaction_detail DB: " + releaseTxResult1);
		log.debug("Fund Release Amount :" + sellerHelper.getSettlementDetailFundReleaseAmount(orderId, "RELEASED"));

		SellerPaymentDP.create_Online_Mynts_OrderWithSellerQty1ReleaseHelper(orderId, store_order_id,
				paymentPlanItemIdref, ppsId, ppsIdCancellation);
		sellerHelper.rabbitMqRefundTx(returnMessage, ppsIdCancellation);

		log.debug("Wait for 10Sec more to get order details in settlement detail table....");
		Thread.sleep(10000);

		int releaseTxResult2 = sellerHelper.getSettlementdetailDBStatusCount(orderId, "REFUNDED");
		Assert.assertEquals(releaseTxResult2, 1,
				"REFUND Order_id:" + orderId + " Not available in SPS Settlement detail table");
		// Assert.assertEquals(sellerHelper.getSettlementDetailRefundAmount(orderId,"CASH_ON_DELIVERY"),"-1399.00"
		// ,"COD Refund amount:");
		Assert.assertEquals(sellerHelper.getSettlementDetailRefundAmount(orderId, "ONLINE"), "-799.00",
				"Online Refund amount:");

		sellerHelper.deleteFromPaymentPlanItemInstrument(paymentPlanItemId);
		sellerHelper.deleteOrderRecord(orderId, ppsId, paymentPlanItemId);

	}

	@Test(groups = { "Regression",
			"Smoke" }, priority = 0, dataProvider = "SPS_SellerQty1EMI", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1. Create online order with seller qty1 sku1. \n2. check the split in citrus than in SPS")
	public void SPS_SellerQty1EMI(String orderId, String store_order_id, String ppsId, String paymentPlanItemId,
			String message, String lineId, String releaseFundMessage, String paymentPlanItemIdref,
			String ppsIdCancellation, String returnMessage) throws SQLException, IOException, InterruptedException {
		log.debug("----------------" + orderId + " : " + ppsId + " : " + paymentPlanItemId);
		sellerHelper.rabbitMqAddTx(message, orderId);
		Thread.sleep(10000);
		// int splits = sellerHelper.getNoOfSplits(store_order_id);
		// log.debug("Number of Splits: " + splits);
		int txResult = sellerHelper.getSplitTxDBCount(orderId);
		int pgResult = sellerHelper.getSplitPgDBCount(orderId);
		log.debug("DB check Tx table splits count: " + txResult + " pg table: " + pgResult);
		// AssertJUnit.assertEquals(1, splits);
		AssertJUnit.assertEquals(1, txResult);
		AssertJUnit.assertEquals(1, pgResult);
		Assert.assertEquals(sellerHelper.getTxAmount(orderId), "799.00");

		sellerHelper.updateOrderReleaseStatus(orderId, "PK");
		sellerHelper.updateOrderLineStatus(orderId, "QD");
		sellerHelper.rabbitMqReleasePKTx(releaseFundMessage, orderId);
		Thread.sleep(10000);
		// int release = sellerHelper.getNoOfRelease(store_order_id);
		// log.debug("Number of release: " + release);
		int releaseTxResult = sellerHelper.getTxDBStatus1(orderId, "RELEASED");

		log.debug("DB status for release: " + releaseTxResult);
		// Assert.assertEquals(release, 1, "Order Id: " + orderId + " Not
		// available in SPS Transaction detail table");
		Assert.assertEquals(releaseTxResult, 1, "sps transaction_detail status :");

		int releaseTxResult1 = sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED");
		Assert.assertEquals(releaseTxResult1, 1,
				"Order Id: " + orderId + " Not available in SPS Settlement detail table");
		Assert.assertEquals(sellerHelper.getSettlementDetailFundReleaseAmount(orderId, "RELEASED"), "799.00",
				"Online Release amount:");

		log.debug("Number of releases on SPS Transaction_detail DB: " + releaseTxResult1);
		log.debug("Fund Release Amount :" + sellerHelper.getSettlementDetailFundReleaseAmount(orderId, "RELEASED"));

		SellerPaymentDP.create_Online_EMI_OrderWithSellerQty1ReleaseHelper(orderId, store_order_id,
				paymentPlanItemIdref, ppsId, ppsIdCancellation);
		sellerHelper.rabbitMqRefundTx(returnMessage, ppsIdCancellation);

		log.debug("Wait for 10Sec more to get order details in settlement detail table....");
		Thread.sleep(10000);

		int releaseTxResult2 = sellerHelper.getSettlementdetailDBStatusCount(orderId, "REFUNDED");
		Assert.assertEquals(releaseTxResult2, 1,
				"REFUND Order_id:" + orderId + " Not available in SPS Settlement detail table");
		// Assert.assertEquals(sellerHelper.getSettlementDetailRefundAmount(orderId,"CASH_ON_DELIVERY"),"-1399.00"
		// ,"COD Refund amount:");
		Assert.assertEquals(sellerHelper.getSettlementDetailRefundAmount(orderId, "ONLINE"), "-799.00",
				"Online Refund amount:");

		sellerHelper.deleteFromPaymentPlanItemInstrument(paymentPlanItemId);
		sellerHelper.deleteOrderRecord(orderId, ppsId, paymentPlanItemId);

	}

	@Test(groups = { "Regression",
			"Smoke" }, priority = 0, dataProvider = "SPS_SellerQty1BankCB", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1. Create online order with seller qty1 sku1. \n2. check the split in citrus than in SPS")
	public void SPS_SellerQty1BankCB(String orderId, String store_order_id, String ppsId, String paymentPlanItemId,
			String message, String lineId, String releaseFundMessage, String paymentPlanItemIdref,
			String ppsIdCancellation, String returnMessage) throws SQLException, IOException, InterruptedException {
		log.debug("----------------" + orderId + " : " + ppsId + " : " + paymentPlanItemId);
		sellerHelper.rabbitMqAddTx(message, orderId);
		Thread.sleep(10000);
		// int splits = sellerHelper.getNoOfSplits(store_order_id);
		// log.debug("Number of Splits: " + splits);
		int txResult = sellerHelper.getSplitTxDBCount(orderId);
		int pgResult = sellerHelper.getSplitPgDBCount(orderId);
		log.debug("DB check Tx table splits count: " + txResult + " pg table: " + pgResult);
		// AssertJUnit.assertEquals(1, splits);
		AssertJUnit.assertEquals(1, txResult);
		AssertJUnit.assertEquals(1, pgResult);
		Assert.assertEquals(sellerHelper.getTxAmount(orderId), "799.00");

		sellerHelper.updateOrderReleaseStatus(orderId, "PK");
		sellerHelper.updateOrderLineStatus(orderId, "QD");
		sellerHelper.rabbitMqReleasePKTx(releaseFundMessage, orderId);
		Thread.sleep(10000);
		// int release = sellerHelper.getNoOfRelease(store_order_id);
		// log.debug("Number of release: " + release);
		int releaseTxResult = sellerHelper.getTxDBStatus1(orderId, "RELEASED");

		log.debug("DB status for release: " + releaseTxResult);
		// Assert.assertEquals(release, 1, "Order Id: " + orderId + " Not
		// available in SPS Transaction detail table");
		Assert.assertEquals(releaseTxResult, 1, "sps transaction_detail status :");

		int releaseTxResult1 = sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED");
		Assert.assertEquals(releaseTxResult1, 1,
				"Order Id: " + orderId + " Not available in SPS Settlement detail table");
		Assert.assertEquals(sellerHelper.getSettlementDetailFundReleaseAmount(orderId, "RELEASED"), "799.00",
				"Online Release amount:");

		log.debug("Number of releases on SPS Transaction_detail DB: " + releaseTxResult1);
		log.debug("Fund Release Amount :" + sellerHelper.getSettlementDetailFundReleaseAmount(orderId, "RELEASED"));

		SellerPaymentDP.create_Online_BankCB_OrderWithSellerQty1ReleaseHelper(orderId, store_order_id,
				paymentPlanItemIdref, ppsId, ppsIdCancellation);
		sellerHelper.rabbitMqRefundTx(returnMessage, ppsIdCancellation);

		log.debug("Wait for 10Sec more to get order details in settlement detail table....");
		Thread.sleep(10000);

		int releaseTxResult2 = sellerHelper.getSettlementdetailDBStatusCount(orderId, "REFUNDED");
		Assert.assertEquals(releaseTxResult2, 1,
				"REFUND Order_id:" + orderId + " Not available in SPS Settlement detail table");
		// Assert.assertEquals(sellerHelper.getSettlementDetailRefundAmount(orderId,"CASH_ON_DELIVERY"),"-1399.00"
		// ,"COD Refund amount:");
		Assert.assertEquals(sellerHelper.getSettlementDetailRefundAmount(orderId, "ONLINE"), "-799.00",
				"Online Refund amount:");

		sellerHelper.deleteFromPaymentPlanItemInstrument(paymentPlanItemId);
		sellerHelper.deleteOrderRecord(orderId, ppsId, paymentPlanItemId);

	}

	@Test(groups = { "Regression","mynt",
			"Smoke" }, priority = 0, dataProvider = "SPS_SellerQty1MyntraCredit", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1. Create online order with seller qty1 sku1. \n2. check the split in citrus than in SPS")
	public void SPS_SellerQty1MyntraCredit(String orderId, String store_order_id, String ppsId, String paymentPlanItemId,
			String message, String lineId, String releaseFundMessage, String paymentPlanItemIdref,
			String ppsIdCancellation, String returnMessage) throws SQLException, IOException, InterruptedException {
		log.debug("----------------" + orderId + " : " + ppsId + " : " + paymentPlanItemId);
		sellerHelper.rabbitMqAddTx(message, orderId);
		Thread.sleep(10000);
		// int splits = sellerHelper.getNoOfSplits(store_order_id);
		// log.debug("Number of Splits: " + splits);
		int txResult = sellerHelper.getSplitTxDBCount(orderId);
		int pgResult = sellerHelper.getSplitPgDBCount(orderId);
		log.debug("DB check Tx table splits count: " + txResult + " pg table: " + pgResult);
		// AssertJUnit.assertEquals(1, splits);
		AssertJUnit.assertEquals(1, txResult);
		AssertJUnit.assertEquals(1, pgResult);
		Assert.assertEquals(sellerHelper.getTxAmount(orderId), "799.00");

		sellerHelper.updateOrderReleaseStatus(orderId, "PK");
		sellerHelper.updateOrderLineStatus(orderId, "QD");
		sellerHelper.rabbitMqReleasePKTx(releaseFundMessage, orderId);
		Thread.sleep(10000);
		// int release = sellerHelper.getNoOfRelease(store_order_id);
		// log.debug("Number of release: " + release);
		/*int releaseTxResult = sellerHelper.getTxDBStatus1(orderId, "RELEASED");

		log.debug("DB status for release: " + releaseTxResult);
		// Assert.assertEquals(release, 1, "Order Id: " + orderId + " Not
		// available in SPS Transaction detail table");
		Assert.assertEquals(releaseTxResult, 1, "sps transaction_detail status :");

		int releaseTxResult1 = sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED");
		Assert.assertEquals(releaseTxResult1, 1,
				"Order Id: " + orderId + " Not available in SPS Settlement detail table");
		Assert.assertEquals(sellerHelper.getSettlementDetailFundReleaseAmount(orderId, "RELEASED"), "799.00",
				"Online Release amount:");

		log.debug("Number of releases on SPS Transaction_detail DB: " + releaseTxResult1);
		log.debug("Fund Release Amount :" + sellerHelper.getSettlementDetailFundReleaseAmount(orderId, "RELEASED"));*/

		SellerPaymentDP.create_Online_MyntraCredit_OrderWithSellerQty1ReleaseHelper(orderId, store_order_id,
				paymentPlanItemIdref, ppsId, ppsIdCancellation);
		sellerHelper.rabbitMqRefundTx(returnMessage, ppsIdCancellation);

		log.debug("Wait for 10Sec more to get order details in settlement detail table....");
		Thread.sleep(10000);

		int releaseTxResult2 = sellerHelper.getSettlementdetailDBStatusCount(orderId, "REFUNDED");
		Assert.assertEquals(releaseTxResult2, 1,
				"REFUND Order_id:" + orderId + " Not available in SPS Settlement detail table");
		// Assert.assertEquals(sellerHelper.getSettlementDetailRefundAmount(orderId,"CASH_ON_DELIVERY"),"-1399.00"
		// ,"COD Refund amount:");
		Assert.assertEquals(sellerHelper.getSettlementDetailRefundAmount(orderId, "ONLINE"), "-799.00",
				"Online Refund amount:");

	//	sellerHelper.deleteFromPaymentPlanItemInstrument(paymentPlanItemId);
	//	sellerHelper.deleteOrderRecord(orderId, ppsId, paymentPlanItemId);

	}

}
