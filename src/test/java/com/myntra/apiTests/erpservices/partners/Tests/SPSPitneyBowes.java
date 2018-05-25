package com.myntra.apiTests.erpservices.partners.Tests;

import java.io.IOException;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import com.myntra.apiTests.erpservices.oms.OMSServiceHelper;
import com.myntra.apiTests.erpservices.partners.SellerPaymentServiceHelper;
import com.myntra.apiTests.erpservices.partners.dp.SellerPaymentDP;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.test.commons.testbase.BaseTest;

public class SPSPitneyBowes extends BaseTest {

	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = LoggerFactory.getLogger(SPSPitneyBowes.class);
	static String envName = "fox8";

	APIUtilities apiUtil = new APIUtilities();

	SellerPaymentServiceHelper sellerHelper = new SellerPaymentServiceHelper();
	SellerPaymentDP sellerDP = new SellerPaymentDP();
	OMSServiceHelper omsServiceHelper = new OMSServiceHelper();

	@Test(groups = { "Regression",
			"Smoke" }, priority = 0, dataProvider = "SPS_PBOrderSingleSku", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1. Create online order with seller qty1 sku1. \n2. check the split in citrus than in SPS")
	public void SPS_PBOrderSingleSku(String orderId, String store_order_id, String ppsId, String paymentPlanItemId,
			String message, String lineId) throws Exception {
		log.debug("----------------" + orderId + " : " + ppsId + " : " + paymentPlanItemId);
		sellerHelper.rabbitMqAddTx(message, orderId);

		Thread.sleep(30000);
		int txResult = sellerHelper.getSplitTxDBCount(orderId);
		int pgResult = sellerHelper.getSplitPgDBCount(orderId);
		log.debug("DB check Tx table splits count: " + txResult + " pg table: " + pgResult);

		AssertJUnit.assertEquals(1, txResult);
		AssertJUnit.assertEquals(1, pgResult);

		int releaseTxResult = sellerHelper.getTxDBStatus1(orderId, "RELEASED");
		log.debug("DB status for release: " + releaseTxResult);

		Assert.assertEquals(releaseTxResult, 1,
				" Release orderId :" + orderId + " not found in Transaction details table");

		int releaseSettlResult = sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED");
		Assert.assertEquals(releaseSettlResult, 1,
				"Order Id: " + orderId + " Not available in SPS Settlement detail table");

		sellerHelper.deleteOrderRecord(orderId, ppsId, paymentPlanItemId);

	}

	@Test(groups = { "Regression",
			"Smoke" }, priority = 0, dataProvider = "SPS_PBOrderSingleSkuAndCancel", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1.Create online order with seller qty1 sku1.\n2.check the split in citrus than in SPS. \n3.cancel the order. \n4.check the refund on citurs and sps both")
	public void SPS_PBOrderSingleSkuAndCancel(String orderId, String store_order_id, String ppsId,
			String ppsIdCancellation, String paymentPlanItemId, String paymentPlanItemIdref, String message,
			String cancellationMessage, String lineId) throws SQLException, IOException, InterruptedException {
		log.debug(orderId + " : " + ppsId + " : " + paymentPlanItemId);
		sellerHelper.rabbitMqAddTx(message, orderId);
		Thread.sleep(10000);

		int txResult = sellerHelper.getSplitTxDBCount(orderId);
		int pgResult = sellerHelper.getSplitPgDBCount(orderId);
		log.debug("DB check Tx table splits count: " + txResult + " pg table: " + pgResult);

		AssertJUnit.assertEquals(1, txResult);
		AssertJUnit.assertEquals(1, pgResult);

		int releaseTxResult = sellerHelper.getTxDBStatus1(orderId, "RELEASED");
		log.debug("DB status for release: " + releaseTxResult);

		Assert.assertEquals(releaseTxResult, 1,
				" Release orderId :" + orderId + " not found in Transaction details table");

		int releaseSettlResult = sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED");
		Assert.assertEquals(releaseSettlResult, 1,
				"Order Id: " + orderId + " Not available in SPS Settlement detail table");

		log.debug(cancellationMessage);
		sellerHelper.rabbitMqRefundTx(cancellationMessage, ppsIdCancellation);
		Thread.sleep(10000);

		int refundTxResult = sellerHelper.getTxDBStatus1(orderId, "REFUNDED");
		log.debug("DB status for refund: " + refundTxResult);

		AssertJUnit.assertEquals(1, refundTxResult);
		sellerHelper.deleteOrderRecord(orderId, ppsId, paymentPlanItemId, ppsIdCancellation, paymentPlanItemIdref);
	}

	@Test(groups = { "Regression",
			"Sanity" }, priority = 0, dataProvider = "SPS_PBMultiQtyCan1ReleaseReturn1", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1. Create online order with seller qty3 sku1. \n2.check the split in citrus than in SPS for all 3qty.\n3.cancel 1qty of the order and check the refund.\n "
					+ "4.release qty1 and check the release in citurs and sps.\n5.check the pg settlement for 3rd split as we release the 1st sku")
	public void SPS_PBMultiQtyCan1ReleaseReturn1(String orderId, String store_order_id, String ppsId,
			String ppsIdCancellation, String paymentPlanItemId, String paymentPlanItemIdref, String message,
			String cancellationMessage, String lineId, String lineId1, String paymentPlanExecutionStatus_id,
			String releaseMessage, String returnMessage, String ppsReturnId, String paymentPlanreturnItemId)
			throws SQLException, IOException, InterruptedException {
		log.debug("---------orderId: " + orderId + " ppsId: " + ppsId + " PymentPlanItemId: " + paymentPlanItemId);
		sellerHelper.rabbitMqAddTx(message, orderId);
		Thread.sleep(10000);
		
		int txResult = sellerHelper.getSplitTxDBCount(orderId);
		int pgResult = sellerHelper.getSplitPgDBCount(orderId);
		log.debug("DB check Tx table splits count: " + txResult + " pg table: " + pgResult);
		
		Assert.assertEquals(txResult, 3);
		Assert.assertEquals(pgResult, 1);
		
		int releaseTxResult = sellerHelper.getTxDBStatus1(orderId, "RELEASED");
		log.debug("DB status for release: " + releaseTxResult);

		Assert.assertEquals(releaseTxResult, 3,
				" Release orderId :" + orderId + " not found in Transaction details table");

		int releaseSettlResult = sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED");
		Assert.assertEquals(releaseSettlResult, 3,
				"Order Id: " + orderId + " Not available in SPS Settlement detail table");

		

		SellerPaymentDP.SPS_PBMultiQtyCanRelHelper(orderId, store_order_id, orderId, lineId, ppsId,
				ppsIdCancellation, paymentPlanItemIdref, paymentPlanExecutionStatus_id);
		sellerHelper.rabbitMqRefundTx(cancellationMessage, ppsIdCancellation);
		Thread.sleep(10000);
		
		int refundTxResult = sellerHelper.getTxDBStatus(orderId, "1251881", "REFUNDED");
		log.debug("DB status for refund: " + refundTxResult);
		
		AssertJUnit.assertEquals(1, refundTxResult);

		sellerHelper.rabbitMqReleasePKTx(releaseMessage, orderId);
		Thread.sleep(10000);
		
		
		SellerPaymentDP.create_SPS_PBMultiQtyReturnHelper(lineId1, orderId, store_order_id,
				paymentPlanreturnItemId, ppsId, ppsReturnId);
		sellerHelper.rabbitMqRefundTx(returnMessage, ppsIdCancellation);

		log.debug("Wait for 10Sec more to get order details in settlement detail table....");
		Thread.sleep(10000);

		int releaseTxResult1 = sellerHelper.getSettlementdetailDBStatusCount(orderId, "REFUNDED");
		Assert.assertEquals(releaseTxResult, 2,
				"REFUND Order_id:" + orderId + " Not available in SPS Settlement detail table");
		
		Assert.assertEquals(sellerHelper.getSettlementDetailRefundAmount(orderId, "ONLINE"), "-2299.00",
				"Online Refund amount:");

		sellerHelper.deleteOrderRecord(orderId, ppsId, paymentPlanItemId, ppsIdCancellation, paymentPlanItemIdref);
	}

}
