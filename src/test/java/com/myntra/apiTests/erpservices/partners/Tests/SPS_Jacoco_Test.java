package com.myntra.apiTests.erpservices.partners.Tests;

import com.myntra.apiTests.common.APINAME;
import com.myntra.apiTests.common.Myntra;
import com.myntra.apiTests.common.ServiceType;
import com.myntra.apiTests.erpservices.oms.OMSServiceHelper;
import com.myntra.apiTests.erpservices.partners.SellerPaymentServiceHelper;
import com.myntra.apiTests.erpservices.partners.dp.SellerPaymentDP;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.PayloadType;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.test.commons.testbase.BaseTest;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

public class SPS_Jacoco_Test extends BaseTest {
	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(SellerPaymentServiceOnline.class);
	static String envName = "fox8";

	APIUtilities apiUtil = new APIUtilities();

	SellerPaymentServiceHelper sellerHelper = new SellerPaymentServiceHelper();
	SellerPaymentDP sellerDP = new SellerPaymentDP();
	OMSServiceHelper omsServiceHelper = new OMSServiceHelper();

	/*
	 * @Test(groups = { "Regression", "Smoke" }, priority = 0, dataProvider =
	 * "SPS_SellerQty1", dataProviderClass = SellerPaymentDP.class, enabled =
	 * true, description =
	 * "1. Create online order with seller qty1 sku1. \n2. check the split in citrus than in SPS"
	 * ) public void SPS_SellerQty1(String orderId, String store_order_id,
	 * String ppsId, String paymentPlanItemId, String message, String lineId)
	 * throws Exception { log.debug("----------------" + orderId + " : " + ppsId
	 * + " : " + paymentPlanItemId); sellerHelper.rabbitMqAddTx(message,
	 * orderId); // int splits = sellerHelper.getNoOfSplits(store_order_id); //
	 * log.debug("Number of Splits: " + splits); Thread.sleep(10000); int
	 * txResult = sellerHelper.getSplitTxDBCount(orderId); int pgResult =
	 * sellerHelper.getSplitPgDBCount(orderId); log.debug(
	 * "DB check Tx table splits count: " + txResult + " pg table: " +
	 * pgResult); // AssertJUnit.assertEquals(1, splits);
	 * AssertJUnit.assertEquals(1, txResult); AssertJUnit.assertEquals(1,
	 * pgResult); sellerHelper.deleteOrderRecord(orderId, ppsId,
	 * paymentPlanItemId);
	 * 
	 * }
	 * 
	 * @Test(groups = { "Regression", "Sanity" }, priority = 0, dataProvider =
	 * "SPS_getSellerById", dataProviderClass = SellerPaymentDP.class, enabled =
	 * true, description = "1. Get seller by Id")
	 * 
	 * public void SPS_getSellerById(String sellerId, String expectedResult) {
	 * MyntraService service = Myntra.getService(ServiceType.ERP_SPS,
	 * APINAME.GETSELLERBYID, init.Configurations, new String[] {}, new String[]
	 * { sellerId, expectedResult },
	 * com.myntra.lordoftherings.gandalf.PayloadType.JSON, PayloadType.JSON);
	 * 
	 * HashMap getParam = new HashMap(); getParam.put("Authorization",
	 * "Basic YXBpYWRtaW46bTFudHJhUjBja2V0MTMhIw=="); log.debug(service.URL);
	 * RequestGenerator req = new RequestGenerator(service, getParam); String
	 * res = req.respvalidate.returnresponseasstring();
	 * AssertJUnit.assertEquals(200, req.response.getStatus());
	 * APIUtilities.validateResponse("json", res, expectedResult); }
	 * 
	 * @Test(groups = { "Regression" }, priority = 0, dataProvider =
	 * "SPS_VectorQty2Cancel", dataProviderClass = SellerPaymentDP.class,
	 * enabled = true, description =
	 * "1.Create online order with Vector qty2 sku1. \n2.check the splits in citrus than in SPS. \n3.cancel only 1 skus. \n4.check the refund on citurs and sps both for only that sku"
	 * ) public void SPS_VectorQty2Cancel(String message, String
	 * cancellationMessage, String orderId, String store_order_id, String
	 * OrderReleaseId, String lineId, String ppsId, String ppsIdCancellation,
	 * String paymentPlanItemIdref, String paymentPlanItemId, String
	 * paymentPlanItemId2) throws SQLException, IOException,
	 * InterruptedException { log.debug(orderId + " : " + ppsId + " : " +
	 * paymentPlanItemId); sellerHelper.rabbitMqAddTx(message, orderId);
	 * Thread.sleep(10000); // int splits =
	 * sellerHelper.getNoOfSplits(store_order_id); // log.debug(
	 * "Number of Splits: " + splits); int txResult =
	 * sellerHelper.getSplitTxDBCount(orderId); int pgResult =
	 * sellerHelper.getSplitPgDBCount(orderId); log.debug(
	 * "DB check Tx table splits count: " + txResult + " pg table: " +
	 * pgResult); // AssertJUnit.assertEquals(2, splits);
	 * AssertJUnit.assertEquals(2, txResult); AssertJUnit.assertEquals(1,
	 * pgResult); SellerPaymentDP.OrderWithVectorQty2CancelHelper(orderId,
	 * store_order_id, OrderReleaseId, lineId, ppsId, ppsIdCancellation,
	 * paymentPlanItemIdref); log.debug(cancellationMessage);
	 * sellerHelper.rabbitMqRefundTx(cancellationMessage, ppsIdCancellation);
	 * Thread.sleep(10000); // int refund =
	 * sellerHelper.getNoOfRefund(store_order_id); // log.debug(
	 * "Number of Refund: " + refund); int refundTxResult =
	 * sellerHelper.getTxDBStatus(orderId, "3827", "REFUNDED"); log.debug(
	 * "DB status for refund: " + refundTxResult); //
	 * AssertJUnit.assertEquals(1, refund); AssertJUnit.assertEquals(1,
	 * refundTxResult); //
	 * sellerHelper.deleteFromPaymentPlanItemInstrument(paymentPlanItemId2); //
	 * sellerHelper.deleteOrderRecord(orderId, ppsId, paymentPlanItemId, //
	 * ppsIdCancellation, paymentPlanItemIdref); }
	 * 
	 * @Test(groups = { "Regression", "Smoke" }, priority = 0, dataProvider =
	 * "SPS_WalletQty1Cancel", dataProviderClass = SellerPaymentDP.class,
	 * enabled = true, description =
	 * "1.Create online order with seller qty1 sku1.\n2.check the split in citrus than in SPS. \n3.cancel the order. \n4.check the refund on citurs and sps both"
	 * ) public void SPS_WalletQty1Cancel(String orderId, String store_order_id,
	 * String ppsId, String ppsIdCancellation, String paymentPlanItemId, String
	 * paymentPlanItemIdref, String message, String cancellationMessage, String
	 * lineId) throws SQLException, IOException, InterruptedException {
	 * log.debug(orderId + " : " + ppsId + " : " + paymentPlanItemId);
	 * sellerHelper.rabbitMqAddTx(message, orderId); Thread.sleep(10000); // int
	 * splits = sellerHelper.getNoOfSplits(store_order_id); // log.debug(
	 * "Number of Splits: " + splits); int txResult =
	 * sellerHelper.getSplitTxDBCount(orderId); int pgResult =
	 * sellerHelper.getSplitPgDBCount(orderId); log.debug(
	 * "DB check Tx table splits count: " + txResult + " pg table: " +
	 * pgResult); // AssertJUnit.assertEquals(1, splits);
	 * Assert.assertEquals(txResult, 1, "Add Order Id " + orderId +
	 * " not found in Transaction details table"); Assert.assertEquals(pgResult,
	 * 1, "PG settle Order Id " + orderId +
	 * " not found in PG Settle details table"); log.debug(cancellationMessage);
	 * sellerHelper.rabbitMqRefundTx(cancellationMessage, ppsIdCancellation);
	 * Thread.sleep(10000); // int refund =
	 * sellerHelper.getNoOfRefund(store_order_id); // log.debug(
	 * "Number of Refund: " + refund); int refundTxResult =
	 * sellerHelper.getTxDBStatus1(orderId, "REFUNDED"); log.debug(
	 * "DB status for refund: " + refundTxResult); //
	 * AssertJUnit.assertEquals(1, refund); Assert.assertEquals(refundTxResult,
	 * 1, "Refund Order Id " + orderId +
	 * " not found in Transaction details table");
	 * sellerHelper.deleteOrderRecord(orderId, ppsId, paymentPlanItemId,
	 * ppsIdCancellation, paymentPlanItemIdref); }
	 * 
	 * @Test(groups = { "Regression" }, priority = 0, dataProvider =
	 * "SPS_VectorQty2CancelBoth", dataProviderClass = SellerPaymentDP.class,
	 * enabled = true, description =
	 * "1.Create online order with Vector qty2 sku1. \n2.check the splits in citrus than in SPS. \n3.cancel only 1 skus. \n4.check the refund on citurs and sps both for only that sku"
	 * ) public void SPS_VectorQty2CancelBoth(String message, String
	 * releaseMessage, String cancellationMessage, String orderId, String
	 * store_order_id, String OrderReleaseId, String lineId, String ppsId,
	 * String ppsIdCancellation, String paymentPlanItemIdref, String
	 * paymentPlanItemId, String paymentPlanItemIdref1) throws SQLException,
	 * IOException, InterruptedException { log.debug(orderId + " : " + ppsId +
	 * " : " + paymentPlanItemId); sellerHelper.rabbitMqAddTx(message, orderId);
	 * Thread.sleep(10000);
	 * 
	 * int txResult = sellerHelper.getSplitTxDBCount(orderId); int pgResult =
	 * sellerHelper.getSplitPgDBCount(orderId); log.debug(
	 * "DB check Tx table splits count: " + txResult + " pg table: " +
	 * pgResult);
	 * 
	 * AssertJUnit.assertEquals(2, txResult); AssertJUnit.assertEquals(1,
	 * pgResult);
	 * 
	 * sellerHelper.updateOrderReleaseStatusAndPackedOn(OrderReleaseId, "PK");
	 * sellerHelper.updateOrderLineStatusByLineId(lineId, "QD");
	 * sellerHelper.rabbitMqReleasePKTx(releaseMessage, orderId);
	 * Thread.sleep(10000);
	 * Assert.assertEquals(sellerHelper.getTxDBStatus1(orderId, "RELEASED"), 1,
	 * "Release order id " + orderId +
	 * " not available in SPS Transaction detail table");
	 * 
	 * SellerPaymentDP.createOnlineOrderWithVectorQty2CancelBoth(orderId,
	 * store_order_id, OrderReleaseId, lineId, paymentPlanItemIdref, ppsId,
	 * ppsIdCancellation, paymentPlanItemIdref1);
	 * log.debug(cancellationMessage);
	 * sellerHelper.rabbitMqRefundTx(cancellationMessage, ppsIdCancellation);
	 * Thread.sleep(10000); int refundTxResult =
	 * sellerHelper.getTxDBStatus1(orderId, "REFUNDED"); log.debug(
	 * "DB status for refund: " + refundTxResult);
	 * Assert.assertEquals(refundTxResult, 2, "Refund order id " + orderId +
	 * " not available in SPS Transaction detail table");
	 * 
	 * sellerHelper.deleteOrderRecord(orderId, ppsId, paymentPlanItemId,
	 * ppsIdCancellation, paymentPlanItemIdref); }
	 * 
	 * @Test(groups = { "Regression", "Sanity" }, priority = 0, dataProvider =
	 * "SPS_VectorQty1ShippingCancel", dataProviderClass =
	 * SellerPaymentDP.class, enabled = true, description =
	 * "1.Create online order with Vector qty1 sku1 with shipping charges. \n2.check the splits in citrus than in SPS for both sku and shipping. \n3.cancel the order. \n4.check the refund on citurs and sps both for both shipping and sku"
	 * ) public void SPS_VectorQty1ShippingCancel(String message, String
	 * cancellationMessage, String orderId, String store_order_id, String
	 * lineId, String ppsId, String paymentPlanItemId, String ppsIdCancellation,
	 * String paymentPlanItemIdref, String paymentPlanItemIdship, String
	 * paymentPlanItemIdrefship) throws SQLException, IOException,
	 * InterruptedException { log.debug(orderId + " : " + ppsId + " : " +
	 * paymentPlanItemId); sellerHelper.rabbitMqAddTx(message, orderId);
	 * Thread.sleep(10000); // int splits =
	 * sellerHelper.getNoOfSplits(store_order_id); // log.debug(
	 * "Number of Splits: " + splits); int txResult =
	 * sellerHelper.getSplitTxDBCount(orderId); int pgResult =
	 * sellerHelper.getSplitPgDBCount(orderId); log.debug(
	 * "DB check Tx table splits count: " + txResult + " pg table: " +
	 * pgResult); // AssertJUnit.assertEquals(2, splits);
	 * AssertJUnit.assertEquals(2, txResult); AssertJUnit.assertEquals(1,
	 * pgResult); log.debug(cancellationMessage);
	 * sellerHelper.rabbitMqRefundTx(cancellationMessage, ppsIdCancellation);
	 * Thread.sleep(10000); // int refund =
	 * sellerHelper.getNoOfRefund(store_order_id); // log.debug(
	 * "Number of Refund: " + refund); int refundTxResult =
	 * sellerHelper.getTxDBStatus1(orderId, "REFUNDED"); log.debug(
	 * "DB status for refund: " + refundTxResult); //
	 * AssertJUnit.assertEquals(2, refund); AssertJUnit.assertEquals(2,
	 * refundTxResult);
	 * 
	 * sellerHelper.deleteFromPaymentPlanItemInstrument( paymentPlanItemIdship);
	 * sellerHelper.deleteFromPaymentPlanItemInstrument(
	 * paymentPlanItemIdrefship); sellerHelper.deleteOrderRecord(orderId, ppsId,
	 * paymentPlanItemId, ppsIdCancellation, paymentPlanItemIdref);
	 * 
	 * 
	 * }
	 * 
	 * @Test(groups = { "Regression", "Smoke" }, priority = 0, dataProvider =
	 * "SPS_VectorQty1LP", dataProviderClass = SellerPaymentDP.class, enabled =
	 * true, description =
	 * "1. Create online order with seller qty1 sku1. \n2. check the split in citrus than in SPS"
	 * ) public void SPS_SellerQty1ValidateLPAmount(String orderId, String
	 * store_order_id, String ppsId, String paymentPlanItemId, String message,
	 * String lineId) throws SQLException, IOException, InterruptedException {
	 * log.debug("----------------" + orderId + " : " + ppsId + " : " +
	 * paymentPlanItemId); sellerHelper.rabbitMqAddTx(message, orderId);
	 * Thread.sleep(10000); // int splits =
	 * sellerHelper.getNoOfSplits(store_order_id); // log.debug(
	 * "Number of Splits: " + splits); int txResult =
	 * sellerHelper.getSplitTxDBCount(orderId); int pgResult =
	 * sellerHelper.getSplitPgDBCount(orderId); log.debug(
	 * "DB check Tx table splits count: " + txResult + " pg table: " +
	 * pgResult); // AssertJUnit.assertEquals(1, splits);
	 * AssertJUnit.assertEquals(1, txResult); AssertJUnit.assertEquals(1,
	 * pgResult); Assert.assertEquals(sellerHelper.getTxAmount(orderId),
	 * "2399.00"); sellerHelper.deleteOrderRecord(orderId, ppsId,
	 * paymentPlanItemId);
	 * 
	 * }
	 * 
	 * @Test(groups = { "Regression", "Smoke" }, priority = 0, dataProvider =
	 * "SPS_VectorQty1GC", dataProviderClass = SellerPaymentDP.class, enabled =
	 * true, description =
	 * "1. Create online order with seller qty1 sku1. \n2. check the split in citrus than in SPS"
	 * ) public void SPS_SellerQty1ValidateGCAmount(String orderId, String
	 * store_order_id, String ppsId, String paymentPlanItemId, String message,
	 * String lineId) throws SQLException, IOException, InterruptedException {
	 * log.debug("----------------" + orderId + " : " + ppsId + " : " +
	 * paymentPlanItemId); sellerHelper.rabbitMqAddTx(message, orderId);
	 * Thread.sleep(10000); // int splits =
	 * sellerHelper.getNoOfSplits(store_order_id); // log.debug(
	 * "Number of Splits: " + splits); int txResult =
	 * sellerHelper.getSplitTxDBCount(orderId); int pgResult =
	 * sellerHelper.getSplitPgDBCount(orderId); log.debug(
	 * "DB check Tx table splits count: " + txResult + " pg table: " +
	 * pgResult); // AssertJUnit.assertEquals(1, splits);
	 * AssertJUnit.assertEquals(1, txResult); AssertJUnit.assertEquals(1,
	 * pgResult); Assert.assertEquals(sellerHelper.getTxAmount(orderId),
	 * "2399.00"); sellerHelper.deleteOrderRecord(orderId, ppsId,
	 * paymentPlanItemId);
	 * 
	 * }
	 */

	@Test(groups = { "Regression", "myntcredit",
			"Sanity" }, priority = 0, dataProvider = "SPS_CodQty1ReleaseAndReturn", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1.Create online order with seller qty1 sku1. \n2.check the split in citrus than in SPS. \n3.Release the sku and check release in both(On order Packed). "
					+ "\n4.Return the sku and check the refund in both")
	public void SPS_CodQty1ReleaseAndReturn(String message, String releaseFundMessage, String returnMessage,
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

		SellerPaymentDP.createCodOrderWithSellerQty1ReleaseHelper(orderId, store_order_id, OrderReleaseId,
				paymentPlanItemIdref, ppsId, ppsIdCancellation);
		sellerHelper.rabbitMqRefundTx(returnMessage, ppsIdCancellation);

		log.debug("Wait for 10Sec more to get order details in settlement detail table....");
		Thread.sleep(10000);

		int releaseTxResult1 = sellerHelper.getSettlementdetailDBStatusCount(orderId, "REFUNDED");
		Assert.assertEquals(releaseTxResult1, 1,
				"Refund Order id : " + orderId + " Not available in SPS Settlement detail table");

		sellerHelper.deleteOrderRecord(orderId, ppsId, paymentPlanItemId, ppsIdCancellation, paymentPlanItemIdref);
	}
}
