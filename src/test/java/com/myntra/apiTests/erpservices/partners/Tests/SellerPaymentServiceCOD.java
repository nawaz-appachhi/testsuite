package com.myntra.apiTests.erpservices.partners.Tests;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBException;

import com.myntra.apiTests.common.APINAME;
import com.myntra.apiTests.common.Myntra;
import com.myntra.apiTests.common.ServiceType;
import com.myntra.apiTests.erpservices.lms.Helper.LmsServiceHelper;
import com.myntra.apiTests.erpservices.oms.OMSServiceHelper;
import com.myntra.apiTests.erpservices.partners.SellerPaymentServiceHelper;
import com.myntra.apiTests.erpservices.partners.dp.SellerPaymentDP;

import com.myntra.lordoftherings.gandalf.PayloadType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import com.myntra.lms.client.response.PincodeEntryV2;
import com.myntra.lms.client.response.PincodeResponseV2;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.oms.common.enums.PaymentMethod;
import com.myntra.oms.common.enums.RefundType;
import com.myntra.sps.client.codes.enums.TransactionSplitStatus;
import com.myntra.taxmaster.client.entry.TaxEntry;
import com.myntra.taxmaster.client.enums.TaxType;
import com.myntra.taxmaster.client.response.TaxResponse;
import com.myntra.test.commons.testbase.BaseTest;

public class SellerPaymentServiceCOD extends BaseTest {
	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = LoggerFactory.getLogger(SellerPaymentServiceCOD.class);
	static String envName = "fox8";

	APIUtilities apiUtil = new APIUtilities();

	SellerPaymentServiceHelper sellerHelper = new SellerPaymentServiceHelper();
	SellerPaymentDP sellerDP = new SellerPaymentDP();
	OMSServiceHelper omsServiceHelper = new OMSServiceHelper();

	@Test(groups = { "Regression",
			"Smoke" }, priority = 0, dataProvider = "SPS_SellerSettlementRptQty1CODOrderPKandCancel", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1. Create online order with seller qty1 sku1. \n2. check the split in citrus than in SPS")
	public void SPS_SellerSettlementRptQty1CODOrderPKandCancel(String orderId, String OrderReleaseId,
			String store_order_id, String ppsId, String paymentPlanItemId, String orderMessage, String lineId,
			String releaseMessage, String ppsIdCancellation, String paymentPlanItemIdCancel, String cancellationMessage)
			throws SQLException, IOException, InterruptedException {
		log.debug("----------------" + orderId + " : " + ppsId + " : " + paymentPlanItemId);
		sellerHelper.rabbitMqAddTx(orderMessage, orderId);

		sellerHelper.updateOrderReleaseStatusAndPackedOn(OrderReleaseId, "PK");
		sellerHelper.updateOrderLineStatus(orderId, "QD");
		sellerHelper.rabbitMqReleasePKTx(releaseMessage, orderId);

		log.debug("Wait for 10Sec more to get order details in settlement detail table....");
		Thread.sleep(10000);

		int releaseTxResult = sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED");
		log.debug("Number of releases on SPS Transaction_detail DB: " + releaseTxResult);

		Assert.assertEquals(releaseTxResult, 1,
				"Release Order id :  " + orderId + " Not available in SPS Settlement detail table");
		Assert.assertEquals(sellerHelper.getTxFundReleaseAmount(orderId), "2399.00");

		/*
		 * SellerPaymentServiceDP.
		 * SPS_SellerSettlementRptQty1CODOrderPKandCancel_Helper(orderId,
		 * store_order_id, OrderReleaseId, lineId, ppsId, ppsIdCancellation,
		 * paymentPlanItemIdCancel);
		 * 
		 * sellerHelper.rabbitMqRefundTx(cancellationMessage,
		 * ppsIdCancellation); Thread.sleep(10000);
		 * 
		 * Assert.assertEquals(sellerHelper.getSettlementdetailDBStatusCount(
		 * orderId, "REFUNDED"), 1, "Refund Order id :  " + orderId +
		 * " Not available in SPS Settlement detail table");
		 * 
		 * sellerHelper.deleteOrderRecord(orderId, ppsId, paymentPlanItemId);
		 */

	}

	// Create online order with JIT Multi Sku and Multi qty release all. Check
	// the orders in settlement detail table
	@Test(groups = {
			"Regression" }, priority = 0, dataProvider = "SPS_SellerSettlementRptCODMultiSkuMultiQtyPKandReturn1", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1. Create online order with JIT MultiQty MultiSku1 COD(Tatal 4qty). \n2.Release all check released. \3. Check the order details in SPS Settlement detail table. \4. Validate Fund release amount")
	public void SPS_SellerSettlementRptCODMultiSkuMultiQtyPKandReturn1(String orderId, String OrderReleaseId,
			String store_order_id, String ppsId, String paymentPlanItemId, String paymentPlanItemId1, String message,
			String releaseFundMessage, String lineId, String ppsIdCancellation, String paymentPlanItemIdref,
			String returnMessage) throws SQLException, IOException, InterruptedException {
		log.debug(orderId + " : " + ppsId + " : " + paymentPlanItemId);
		sellerHelper.rabbitMqAddTx(message, orderId);
		sellerHelper.updateOrderReleaseStatusAndPackedOn(OrderReleaseId, "PK");
		sellerHelper.updateOrderLineStatus(orderId, "QD");
		sellerHelper.rabbitMqReleasePKTx(releaseFundMessage, orderId);
		log.debug("Wait for 10Sec more to get order details in settlement detail table....");
		Thread.sleep(10000);

		int releaseTxResult = sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED");
		Assert.assertEquals(releaseTxResult, 4,
				"Order: " + orderId + "  id Not available in SPS Settlement detail table");
		Assert.assertEquals(sellerHelper.getSettlementDetailFundReleaseAmount(orderId), "999.00",
				"Fund Release amount for 1st sku:");
		SellerPaymentDP.SPS_SellerSettlementRptCODMultiSkuMultiQtyPKandReturn1Helper(orderId, store_order_id,
				paymentPlanItemIdref, ppsId, ppsIdCancellation);

		sellerHelper.rabbitMqRefundTx(returnMessage, ppsIdCancellation);
		Thread.sleep(10000);
		Assert.assertEquals(sellerHelper.getSettlementdetailDBStatusCount(orderId, "REFUNDED"), 1,
				"REFUND Order id : " + orderId + " Not available in SPS Settlement detail table");

		sellerHelper.deleteFromPaymentPlanItemInstrument(paymentPlanItemId1);
		sellerHelper.deleteOrderRecord(orderId, ppsId, paymentPlanItemId, ppsIdCancellation, paymentPlanItemIdref);
	}

	// Create online order with JIT Multi Sku and Multi qty release all. Check
	// the orders in settlement detail table
	@Test(groups = {
			"Regression" }, priority = 0, dataProvider = "SPS_SellerSettlementRptCODMultiQty_LineCancel_2_Pack_1_Return_1", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1. Create online order with JIT MultiQty MultiSku1 COD(Tatal 4qty). \n2.Release all check released. \3. Check the order details in SPS Settlement detail table. \4. Validate Fund release amount")
	public void SPS_SellerSettlementRptCODMultiQty_LineCancel_2_Pack_1_Return_1(String orderId, String OrderReleaseId,
			String store_order_id, String ppsId, String paymentPlanItemId, String paymentPlanItemId1, String message,
			String releaseFundMessage, String lineId, String ppsIdCancellation, String paymentPlanItemIdref,
			String cancellationMessage, String returnMessage, String ppsIdReturn, String paymentPlanItemIdret)
			throws SQLException, IOException, InterruptedException {
		log.debug(orderId + " : " + ppsId + " : " + paymentPlanItemId);
		sellerHelper.rabbitMqAddTx(message, orderId);
		sellerHelper.rabbitMqRefundTx(cancellationMessage, ppsIdCancellation);

		sellerHelper.updateOrderReleaseStatusAndPackedOn(OrderReleaseId, "PK");
		sellerHelper.updateOrderLineStatus(orderId, "QD");
		sellerHelper.rabbitMqReleasePKTx(releaseFundMessage, orderId);
		log.debug("Wait for 10Sec more to get order details in settlement detail table....");
		Thread.sleep(10000);

		int releaseTxResult = sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED");
		Assert.assertEquals(releaseTxResult, 1, "Order id Not available in SPS Settlement detail table");
		Assert.assertEquals(sellerHelper.getSettlementDetailFundReleaseAmount(orderId), "999.00",
				"Fund Release amount for 1st sku:");

		SellerPaymentDP.SPS_SellerSettlementRptCODMultiQty_LineCancel_2_Pack_1_Return_1_Helper(orderId,
				store_order_id, OrderReleaseId, paymentPlanItemIdret, ppsId, ppsIdReturn);
		sellerHelper.rabbitMqRefundTx(returnMessage, ppsIdReturn);
		Thread.sleep(10000);
		Assert.assertEquals(sellerHelper.getSettlementdetailDBStatusCount(orderId, "REFUNDED"), 1,
				"REFUND Order id : " + orderId + " Not available in SPS Settlement detail table");

		sellerHelper.deleteFromPaymentPlanItemInstrument(paymentPlanItemId1);
		sellerHelper.deleteOrderRecord(orderId, ppsId, paymentPlanItemId, ppsIdCancellation, paymentPlanItemIdref);
	}

	// Create online order with JIT Multi Sku and Multi qty release all. Check
	// the orders in settlement detail table
	@Test(groups = {
			"Regression" }, priority = 0, dataProvider = "SPS_SellerSettlementRptCOD_GiftCard_Qty_1_PK_Return", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1. Create COD+Giftcard order with JIT quantity=1. \n2.Release the order. \3. Check the order details in SPS Settlement detail table. \4. Validate Fund release amount")
	public void SPS_SellerSettlementRptCOD_GiftCard_Qty_1_PK_Return(String orderId, String store_order_id, String ppsId,
			String paymentPlanItemId, String message, String lineId, String packedMessage, String OrderReleaseId,
			String paymentPlanItemIdref, String ppsIdCancellation, String returnMessage)
			throws SQLException, IOException, InterruptedException {
		log.debug(orderId + " : " + ppsId + " : " + paymentPlanItemId);
		sellerHelper.rabbitMqAddTx(message, orderId);
		sellerHelper.updateOrderReleaseStatusAndPackedOn(OrderReleaseId, "PK");
		sellerHelper.updateOrderLineStatus(orderId, "QD");
		sellerHelper.rabbitMqReleasePKTx(packedMessage, orderId);
		Thread.sleep(10000);
		Assert.assertEquals(sellerHelper.getTxDBStatus1(orderId, "RELEASED"), 1,
				"Release Transaction db order id : " + orderId + " not found");

		int releaseTxResult = sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED");
		Assert.assertEquals(releaseTxResult, 2,
				"RELEASE Order id: " + orderId + "Not available in SPS Settlement detail table");

		SellerPaymentDP.createCod_GF_OrderWithSellerQty1ReleaseHelper(orderId, store_order_id, OrderReleaseId,
				paymentPlanItemIdref, ppsId, ppsIdCancellation);
		sellerHelper.rabbitMqRefundTx(returnMessage, ppsIdCancellation);

		log.debug("Wait for 10Sec more to get order details in settlement detail table....");
		Thread.sleep(10000);
		int releaseTxResult1 = sellerHelper.getSettlementdetailDBStatusCount(orderId, "REFUNDED");
		Assert.assertEquals(releaseTxResult1, 2,
				"REFUND Order_id:" + orderId + " Not available in SPS Settlement detail table");
		Assert.assertEquals(sellerHelper.getSettlementDetailRefundAmount(orderId, "CASH_ON_DELIVERY"), "-1399.00",
				"COD Refund amount:");
		Assert.assertEquals(sellerHelper.getSettlementDetailRefundAmount(orderId, "ONLINE"), "-1000.00",
				"Giftcard Refund amount:");

		sellerHelper.deleteFromPaymentPlanItemInstrument(paymentPlanItemId);
		sellerHelper.deleteOrderRecord(orderId, ppsId, paymentPlanItemId);
	}

	@Test(groups = {
			"Regression" }, priority = 0, dataProvider = "SPS_SellerSettlementRptCOD_GiftCard_Qty_1_PK_Cancel", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1. Create COD+Giftcard order with JIT quantity=1. \n2.Release the order. \3. Check the order details in SPS Settlement detail table. \4. Validate Fund release amount")
	public void SPS_SellerSettlementRptCOD_GiftCard_Qty_1_PK_Cancel(String orderId, String store_order_id, String ppsId,
			String paymentPlanItemId, String message, String lineId, String packedMessage, String OrderReleaseId,
			String paymentPlanItemIdref, String ppsIdCancellation, String cancellationMessage)
			throws SQLException, IOException, InterruptedException {
		log.debug(orderId + " : " + ppsId + " : " + paymentPlanItemId);
		sellerHelper.rabbitMqAddTx(message, orderId);
		sellerHelper.updateOrderReleaseStatusAndPackedOn(OrderReleaseId, "PK");
		sellerHelper.updateOrderLineStatus(orderId, "QD");
		sellerHelper.rabbitMqReleasePKTx(packedMessage, orderId);
		Thread.sleep(10000);
		Assert.assertEquals(sellerHelper.getTxDBStatus1(orderId, "RELEASED"), 1,
				"Release Transaction db order id : " + orderId + " not found");

		int releaseTxResult = sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED");
		Assert.assertEquals(releaseTxResult, 2,
				"RELEASE Order id: " + orderId + "Not available in SPS Settlement detail table");

		SellerPaymentDP.SPS_SellerSettlementRptCOD_GiftCard_Qty_1_PK_Cancel_Helper(orderId, store_order_id,
				lineId, OrderReleaseId, paymentPlanItemIdref, ppsId, ppsIdCancellation);
		sellerHelper.rabbitMqRefundTx(cancellationMessage, ppsIdCancellation);

		log.debug("Wait for 10Sec more to get order details in settlement detail table....");
		Thread.sleep(10000);
		int releaseTxResult1 = sellerHelper.getSettlementdetailDBStatusCount(orderId, "REFUNDED");
		Assert.assertEquals(releaseTxResult1, 2,
				"REFUND Order_id:" + orderId + " Not available in SPS Settlement detail table");
		Assert.assertEquals(sellerHelper.getSettlementDetailRefundAmount(orderId, "CASH_ON_DELIVERY"), "-1399.00",
				"COD Refund amount:");
		Assert.assertEquals(sellerHelper.getSettlementDetailRefundAmount(orderId, "ONLINE"), "-1000.00",
				"Giftcard Refund amount:");

		sellerHelper.deleteFromPaymentPlanItemInstrument(paymentPlanItemId);
		sellerHelper.deleteOrderRecord(orderId, ppsId, paymentPlanItemId);
	}

	// Create online order with JIT Multi Sku and Multi qty release all. Check
	// the orders in settlement detail table
	@Test(groups = {
			"Regression" }, priority = 0, dataProvider = "SPS_SellerSettlementRptCOD_GiftCard_MultiSkuMultiQtyPK_Return", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1. Create online order with JIT MultiQty MultiSku1 COD(Tatal 4qty). \n2.Release all check released. \3. Check the order details in SPS Settlement detail table. \4. Validate Fund release amount")
	public void SPS_SellerSettlementRptCOD_GiftCard_MultiSkuMultiQtyPK_Return(String orderId, String store_order_id,
			String ppsId, String paymentPlanItemId, String paymentPlanItemId1, String message, String packedMessage,
			String lineId, String ppsIdCancellation, String paymentPlanItemIdref, String OrderReleaseId,
			String returnMessage, String paymentPlanItemIdref1) throws SQLException, IOException, InterruptedException {
		log.debug(orderId + " : " + ppsId + " : " + paymentPlanItemId);
		sellerHelper.rabbitMqAddTx(message, orderId);

		sellerHelper.updateOrderReleaseStatusAndPackedOn(OrderReleaseId, "PK");
		sellerHelper.updateOrderLineStatus(orderId, "QD");
		sellerHelper.rabbitMqReleasePKTx(packedMessage, orderId);
		Thread.sleep(10000);
		Assert.assertEquals(sellerHelper.getTxDBStatus1(orderId, "RELEASED"), 2,
				"Release Transaction db order id : " + orderId + " not found");
		Assert.assertEquals(sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED"), 4,
				"Release Settlement db order id : " + orderId + " not found");

		SellerPaymentDP.createCod_GF_OrderWithSellerMultiQtyReleaseHelper(orderId, store_order_id,
				OrderReleaseId, paymentPlanItemIdref, ppsId, ppsIdCancellation, paymentPlanItemIdref1);
		sellerHelper.rabbitMqRefundTx(returnMessage, ppsIdCancellation);

		log.debug("Wait for 10Sec more to get order details in settlement detail table....");
		Thread.sleep(10000);

		int releaseTxResult1 = sellerHelper.getSettlementdetailDBStatusCount(orderId, "REFUNDED");
		Assert.assertEquals(releaseTxResult1, 4,
				"Refund Order_id:" + orderId + " Not available in SPS Settlement detail table");

		sellerHelper.deleteOrderRecord(orderId, ppsId, paymentPlanItemId);
	}

	@Test(groups = {
			"Regression" }, priority = 0, dataProvider = "SPS_SellerSettlementRptCOD_GiftCard_MultiSku_Cancel_1_PK_1_Return_1", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1. Create online order with JIT MultiQty MultiSku1 COD(Tatal 4qty). \n2.Release all check released. \3. Check the order details in SPS Settlement detail table. \4. Validate Fund release amount")
	public void SPS_SellerSettlementRptCOD_GiftCard_MultiSku_Cancel_1_PK_1_Return_1(String orderId,
			String store_order_id, String ppsId, String paymentPlanItemId, String paymentPlanItemId1, String message,
			String packedMessage, String lineId, String cancellationMessage, String ppsIdCancellation,
			String paymentPlanItemIdref, String OrderReleaseId, String returnMessage, String ppsIdReturn,
			String paymentPlanItemIdret) throws SQLException, IOException, InterruptedException {
		log.debug(orderId + " : " + ppsId + " : " + paymentPlanItemId);
		sellerHelper.rabbitMqAddTx(message, orderId);
		SellerPaymentDP.SPS_SellerSettlementRptCOD_GiftCard_MultiSku_Cancel_Helper(orderId, store_order_id,
				OrderReleaseId, lineId, ppsIdReturn, ppsIdCancellation, paymentPlanItemIdref);
		sellerHelper.rabbitMqRefundTx(cancellationMessage, ppsIdCancellation);
		Thread.sleep(10000);
		Assert.assertEquals(sellerHelper.getTxDBStatus1(orderId, "REFUNDED"), 1,
				"Release Transaction db order id : " + orderId + " not found");

		sellerHelper.updateOrderReleaseStatusAndPackedOn(OrderReleaseId, "PK");
		sellerHelper.updateOrderLineStatus(orderId, "QD");
		sellerHelper.rabbitMqReleasePKTx(packedMessage, orderId);
		Thread.sleep(10000);
		Assert.assertEquals(sellerHelper.getTxDBStatus1(orderId, "RELEASED"), 1,
				"Release Transaction db order id : " + orderId + " not found");
		Assert.assertEquals(sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED"), 2,
				"Release Settlement db order id : " + orderId + " not found");

		SellerPaymentDP.SPS_SellerSettlementRptCOD_GiftCard_MultiSku_Cancel_1_PK_1_Return_1_Helper(orderId,
				store_order_id, OrderReleaseId, paymentPlanItemIdret, ppsId, ppsIdReturn);
		sellerHelper.rabbitMqRefundTx(returnMessage, ppsIdReturn);

		log.debug("Wait for 10Sec more to get order details in settlement detail table....");
		Thread.sleep(10000);

		int releaseTxResult1 = sellerHelper.getSettlementdetailDBStatusCount(orderId, "REFUNDED");
		Assert.assertEquals(releaseTxResult1, 2,
				"Refund Order_id:" + orderId + " Not available in SPS Settlement detail table");

		sellerHelper.deleteOrderRecord(orderId, ppsId, paymentPlanItemId);
	}

	@Test(groups = {
			"Regression" }, priority = 0, dataProvider = "SPS_SellerSettlementRptCOD_GiftCard_MultiSku_Cancel_1_PK_1_Cancel1", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1. Create online order with JIT MultiQty MultiSku1 COD(Tatal 4qty). \n2.Release all check released. \3. Check the order details in SPS Settlement detail table. \4. Validate Fund release amount")
	public void SPS_SellerSettlementRptCOD_GiftCard_MultiSku_Cancel_1_PK_1_Cancel1(String orderId,
			String store_order_id, String ppsId, String paymentPlanItemId, String paymentPlanItemId1,
			String orderMessage, String packedMessage, String lineId, String lineId1, String cancellationMessage,
			String ppsIdCancellation, String paymentPlanItemIdref, String OrderReleaseId, String OrderReleaseId1,
			String cancellationMessage1, String ppsIdCancellation1, String paymentPlanItemIdCancel)
			throws SQLException, IOException, InterruptedException {
		log.debug(orderId + " : " + ppsId + " : " + paymentPlanItemId);
		sellerHelper.rabbitMqAddTx(orderMessage, orderId);
		SellerPaymentDP.SPS_SellerSettlementRptCOD_GiftCard_MultiSku_Cancel_Helper(orderId, store_order_id,
				OrderReleaseId1, lineId1, ppsId, ppsIdCancellation, paymentPlanItemIdref);
		sellerHelper.rabbitMqRefundTx(cancellationMessage, ppsIdCancellation);
		Thread.sleep(10000);
		Assert.assertEquals(sellerHelper.getTxDBStatus1(orderId, "REFUNDED"), 1,
				"Release Transaction db order id : " + orderId + " not found");

		sellerHelper.updateOrderReleaseStatusAndPackedOn(OrderReleaseId, "PK");
		sellerHelper.updateOrderLineStatus(orderId, "QD");
		sellerHelper.rabbitMqReleasePKTx(packedMessage, orderId);
		Thread.sleep(10000);
		Assert.assertEquals(sellerHelper.getTxDBStatus1(orderId, "RELEASED"), 1,
				"Release Transaction db order id : " + orderId + " not found");
		Assert.assertEquals(sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED"), 2,
				"Release Settlement db order id : " + orderId + " not found");

		SellerPaymentDP.SPS_SellerSettlementRptCOD_GiftCard_MultiSku_Cancel_1_PK_1_Cancel_1_Helper(orderId,
				store_order_id, OrderReleaseId, lineId, ppsId, ppsIdCancellation1, paymentPlanItemIdCancel);
		sellerHelper.rabbitMqRefundTx(cancellationMessage1, ppsIdCancellation1);

		log.debug("Wait for 10Sec more to get order details in settlement detail table....");
		Thread.sleep(10000);

		int releaseTxResult1 = sellerHelper.getSettlementdetailDBStatusCount(orderId, "REFUNDED");
		Assert.assertEquals(releaseTxResult1, 2,
				"Refund Order_id:" + orderId + " Not available in SPS Settlement detail table");

		sellerHelper.deleteOrderRecord(orderId, ppsId, paymentPlanItemId);
	}

	@Test(groups = {
			"Regression" }, priority = 0, dataProvider = "SPS_SellerSettlementCOD_LP_Qty1PK_Return", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1. Create COD+Loyalty order with JIT quantity=1. \n2.Release the order. \3. Check the order details in SPS Settlement detail table. \4. Validate Fund release amount")
	public void SPS_SellerSettlementCOD_LP_Qty1PK_Return(String orderId, String store_order_id, String ppsId,
			String paymentPlanItemId, String message, String lineId, String packedMessage, String OrderReleaseId,
			String paymentPlanItemIdref, String ppsIdCancellation, String returnMessage)
			throws SQLException, IOException, InterruptedException {
		log.debug(orderId + " : " + ppsId + " : " + paymentPlanItemId);
		sellerHelper.rabbitMqAddTx(message, orderId);

		sellerHelper.updateOrderReleaseStatusAndPackedOn(OrderReleaseId, "PK");
		sellerHelper.rabbitMqReleasePKTx(packedMessage, orderId);
		Thread.sleep(10000);
		Assert.assertEquals(sellerHelper.getTxDBStatus1(orderId, "RELEASED"), 1,
				"Release Transaction db order id : " + orderId + " not found");
		int releaseTxResult = sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED");
		Assert.assertEquals(releaseTxResult, 2,
				"Release Order id: " + orderId + " Not available in SPS Settlement detail table");

		SellerPaymentDP.createCod_LP_OrderWithSellerQty1ReleaseHelper(orderId, store_order_id, OrderReleaseId,
				paymentPlanItemIdref, ppsId, ppsIdCancellation);
		sellerHelper.rabbitMqRefundTx(returnMessage, ppsIdCancellation);

		log.debug("Wait for 10Sec more to get order details in settlement detail table....");
		Thread.sleep(10000);

		int releaseTxResult1 = sellerHelper.getSettlementdetailDBStatusCount(orderId, "REFUNDED");
		Assert.assertEquals(releaseTxResult1, 2,
				"Refund Order_id:" + orderId + " Not available in SPS Settlement detail table");

		Assert.assertEquals(sellerHelper.getSettlementDetailRefundAmount(orderId, "CASH_ON_DELIVERY"), "-1399.00",
				"COD Refund amount:");
		Assert.assertEquals(sellerHelper.getSettlementDetailRefundAmount(orderId, "ONLINE"), "-1000.00",
				"LP Refund amount:");

		sellerHelper.deleteFromPaymentPlanItemInstrument(paymentPlanItemId);
		sellerHelper.deleteOrderRecord(orderId, ppsId, paymentPlanItemId);
	}

	@Test(groups = {
			"Regression" }, priority = 0, dataProvider = "SPS_SellerSettlementCOD_LP_Qty1_Cancel", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1. Create COD+Loyalty order with JIT quantity=1. \n2.Release the order. \3. Check the order details in SPS Settlement detail table. \4. Validate Fund release amount")
	public void SPS_SellerSettlementCOD_LP_Qty1_Cancel(String orderId, String store_order_id, String ppsId,
			String paymentPlanItemId, String message, String lineId, String OrderReleaseId, String paymentPlanItemIdref,
			String ppsIdCancellation, String cancellationMessage)
			throws SQLException, IOException, InterruptedException {
		log.debug(orderId + " : " + ppsId + " : " + paymentPlanItemId);
		sellerHelper.rabbitMqAddTx(message, orderId);
		SellerPaymentDP.SPS_SellerSettlementCOD_LP_Qty1_CancelHelper(orderId, store_order_id, lineId,
				OrderReleaseId, paymentPlanItemIdref, ppsId, ppsIdCancellation);

		sellerHelper.rabbitMqRefundTx(cancellationMessage, ppsIdCancellation);
		log.debug("Wait for 10Sec more to get order details in settlement detail table....");
		Thread.sleep(10000);
		Assert.assertEquals(sellerHelper.getTxDBStatus1(orderId, "REFUNDED"), 1,
				"REFUND Transaction db order id : " + orderId + " not found");

		sellerHelper.deleteFromPaymentPlanItemInstrument(paymentPlanItemId);
		sellerHelper.deleteOrderRecord(orderId, ppsId, paymentPlanItemId);
	}

	@Test(groups = {
			"Regression" }, priority = 0, dataProvider = "SPS_SellerSettlementCOD_LP_MultiSkuMultiQtyPK_Return", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1. Create online order with JIT MultiQty MultiSku1 COD(Tatal 4qty). \n2.Release all check released. \3. Check the order details in SPS Settlement detail table. \4. Validate Fund release amount")
	public void SPS_SellerSettlementCOD_LP_MultiSkuMultiQtyPK_Return(String orderId, String store_order_id,
			String ppsId, String paymentPlanItemId, String paymentPlanItemId1, String message, String packedMessage,
			String releaseFundMessage, String lineId, String ppsIdCancellation, String paymentPlanItemIdref,
			String OrderReleaseId, String paymentPlanItemIdref1, String returnMessage)
			throws SQLException, IOException, InterruptedException {
		log.debug(orderId + " : " + ppsId + " : " + paymentPlanItemId);
		sellerHelper.rabbitMqAddTx(message, orderId);

		sellerHelper.updateOrderReleaseStatusAndPackedOn(OrderReleaseId, "PK");
		sellerHelper.updateOrderLineStatus(orderId, "QD");
		sellerHelper.rabbitMqReleasePKTx(packedMessage, orderId);
		Thread.sleep(10000);
		Assert.assertEquals(sellerHelper.getTxDBStatus1(orderId, "RELEASED"), 2,
				"Release Transaction db order id : " + orderId + " not found");

		int releaseTxResult = sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED");
		Assert.assertEquals(releaseTxResult, 4, "Order id Not available in SPS Settlement detail table");

		SellerPaymentDP.createCod_LP_OrderWithSellerMultiQtyReleaseHelper(orderId, store_order_id,
				OrderReleaseId, paymentPlanItemIdref, ppsId, ppsIdCancellation, paymentPlanItemIdref1);
		sellerHelper.rabbitMqRefundTx(returnMessage, ppsIdCancellation);

		log.debug("Wait for 10Sec more to get order details in settlement detail table....");
		Thread.sleep(10000);

		int releaseTxResult1 = sellerHelper.getSettlementdetailDBStatusCount(orderId, "REFUNDED");
		Assert.assertEquals(releaseTxResult1, 4,
				"Order_id:" + orderId + " Not available in SPS Settlement detail table");

		sellerHelper.deleteFromOrders(orderId);
		sellerHelper.deleteFromOrdersRelease(orderId);
		sellerHelper.deleteFromOrdersLine(orderId);
	}

	@Test(groups = {
			"Regression" }, priority = 0, dataProvider = "SPS_SellerSettlementCOD_Wallet_Qty1PK_Return", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1. Create COD+Wallet order with JIT quantity=1. \n2.Release the order. \3. Check the order details in SPS Settlement detail table. \4. Validate Fund release amount")
	public void SPS_SellerSettlementCOD_Wallet_Qty1PK_Return(String orderId, String store_order_id, String ppsId,
			String paymentPlanItemId, String message, String lineId, String packedMessage, String releaseFundMessage,
			String OrderReleaseId, String paymentPlanItemIdref, String ppsIdCancellation, String returnMessage)
			throws SQLException, IOException, InterruptedException {
		log.debug(orderId + " : " + ppsId + " : " + paymentPlanItemId);
		sellerHelper.rabbitMqAddTx(message, orderId);

		sellerHelper.updateOrderReleaseStatusAndPackedOn(OrderReleaseId, "PK");
		sellerHelper.updateOrderLineStatus(orderId, "QD");
		sellerHelper.rabbitMqReleasePKTx(packedMessage, orderId);
		Thread.sleep(10000);
		Assert.assertEquals(sellerHelper.getTxDBStatus1(orderId, "RELEASED"), 1,
				"Release Transaction db order id : " + orderId + " not found");

		int releaseTxResult = sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED");
		Assert.assertEquals(releaseTxResult, 2,
				"Release Order id: " + orderId + " Not available in SPS Settlement detail table");

		/*
		 * SellerPaymentServiceDP.
		 * createCod_Wallet_OrderWithSellerQty1ReturnHelper(orderId,
		 * store_order_id, OrderReleaseId, paymentPlanItemIdref, ppsId,
		 * ppsIdCancellation); sellerHelper.rabbitMqRefundTx(returnMessage,
		 * ppsIdCancellation);
		 * 
		 * log.debug(
		 * "Wait for 10Sec more to get order details in settlement detail table...."
		 * ); Thread.sleep(10000);
		 * 
		 * int releaseTxResult1 =
		 * sellerHelper.getSettlementdetailDBStatusCount(orderId, "REFUNDED");
		 * Assert.assertEquals(releaseTxResult1, 2, "Refund Order_id:" + orderId
		 * + " Not available in SPS Settlement detail table");
		 * Assert.assertEquals(sellerHelper.getSettlementDetailRefundAmount(
		 * orderId, "CASH_ON_DELIVERY"), "-1399.00", "COD Refund amount:");
		 * Assert.assertEquals(sellerHelper.getSettlementDetailRefundAmount(
		 * orderId, "ONLINE"), "-1000.00", "Wallet Refund amount:");
		 * 
		 * sellerHelper.deleteFromPaymentPlanItemInstrument(paymentPlanItemId);
		 * sellerHelper.deleteOrderRecord(orderId, ppsId, paymentPlanItemId);
		 */
	}

	@Test(groups = {
			"Regression" }, priority = 0, dataProvider = "SPS_COD_Wallet_GCQty1_Return", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1. Create COD+Wallet order with JIT quantity=1. \n2.Release the order. \3. Check the order details in SPS Settlement detail table. \4. Validate Fund release amount")
	public void SPS_COD_Wallet_GCQty1_Return(String orderId, String store_order_id, String ppsId,
			String paymentPlanItemId, String message, String lineId, String packedMessage, String releaseFundMessage,
			String OrderReleaseId, String paymentPlanItemIdref, String ppsIdCancellation, String returnMessage)
			throws SQLException, IOException, InterruptedException {
		log.debug(orderId + " : " + ppsId + " : " + paymentPlanItemId);
		sellerHelper.rabbitMqAddTx(message, orderId);

		sellerHelper.updateOrderReleaseStatusAndPackedOn(OrderReleaseId, "PK");
		sellerHelper.updateOrderLineStatus(orderId, "QD");
		sellerHelper.rabbitMqReleasePKTx(packedMessage, orderId);

		Thread.sleep(10000);
		Assert.assertEquals(sellerHelper.getTxDBStatus1(orderId, "RELEASED"), 1,
				"Release Transaction db order id : " + orderId + " not found");

		int releaseTxResult = sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED");
		Assert.assertEquals(releaseTxResult, 2,
				"Release Order id: " + orderId + " Not available in SPS Settlement detail table");

		SellerPaymentDP.createCod_Wallet_GCReturnHelper(orderId, store_order_id, OrderReleaseId,
				paymentPlanItemIdref, ppsId, ppsIdCancellation);
		sellerHelper.rabbitMqRefundTx(returnMessage, ppsIdCancellation);

		log.debug("Wait for 10Sec more to get order details in settlement detail table....");
		Thread.sleep(15000);

		int releaseTxResult1 = sellerHelper.getSettlementdetailDBStatusCount(orderId, "REFUNDED");
		Assert.assertEquals(releaseTxResult1, 2,
				"Refund Order_id:" + orderId + " Not available in SPS Settlement detail table");
		Assert.assertEquals(sellerHelper.getSettlementDetailRefundAmount(orderId, "CASH_ON_DELIVERY"), "-1000.00",
				"COD Refund amount:");
		Assert.assertEquals(sellerHelper.getSettlementDetailRefundAmount(orderId, "ONLINE"), "-1300.00",
				"Wallet Refund amount:");

		sellerHelper.deleteFromPaymentPlanItemInstrument(paymentPlanItemId);
		sellerHelper.deleteOrderRecord(orderId, ppsId, paymentPlanItemId);
	}

	@Test(groups = {
			"Regression" }, priority = 0, dataProvider = "SPS_COD_Wallet_GCQty1_Return1", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1. Create COD+Wallet order with JIT quantity=1. \n2.Release the order. \3. Check the order details in SPS Settlement detail table. \4. Validate Fund release amount")
	public void SPS_COD_Wallet_GCQty1_Return1(String orderId, String store_order_id, String ppsId,
			String paymentPlanItemId, String message, String lineId, String packedMessage, String releaseFundMessage,
			String OrderReleaseId, String paymentPlanItemIdref, String ppsIdCancellation, String returnMessage)
			throws SQLException, IOException, InterruptedException {
		log.debug(orderId + " : " + ppsId + " : " + paymentPlanItemId);
		sellerHelper.rabbitMqAddTx(message, orderId);

		sellerHelper.updateOrderReleaseStatusAndPackedOn(OrderReleaseId, "PK");
		sellerHelper.updateOrderLineStatus(orderId, "QD");
		sellerHelper.rabbitMqReleasePKTx(packedMessage, orderId);
		Thread.sleep(10000);
		Assert.assertEquals(sellerHelper.getTxDBStatus1(orderId, "RELEASED"), 1,
				"Release Transaction db order id : " + orderId + " not found");
		Assert.assertEquals(sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED"), 2,
				"Release Settlement db order id : " + orderId + " not found");

		SellerPaymentDP.createCod_Wallet_GCReturnHelper1(orderId, store_order_id, OrderReleaseId,
				paymentPlanItemIdref, ppsId, ppsIdCancellation);
		sellerHelper.rabbitMqRefundTx(returnMessage, ppsIdCancellation);

		log.debug("Wait for 10Sec more to get order details in settlement detail table....");
		Thread.sleep(10000);

		int releaseTxResult1 = sellerHelper.getSettlementdetailDBStatusCount(orderId, "REFUNDED");
		Assert.assertEquals(releaseTxResult1, 2,
				"Refund Order_id:" + orderId + " Not available in SPS Settlement detail table");
		Assert.assertEquals(sellerHelper.getSettlementDetailRefundAmount(orderId, "CASH_ON_DELIVERY"), "-1000.00",
				"COD Refund amount:");
		Assert.assertEquals(sellerHelper.getSettlementDetailRefundAmount(orderId, "ONLINE"), "-1300.00",
				"Wallet Refund amount:");

		sellerHelper.deleteFromPaymentPlanItemInstrument(paymentPlanItemId);
		sellerHelper.deleteOrderRecord(orderId, ppsId, paymentPlanItemId);
	}

	@Test(groups = {
			"Regression" }, priority = 0, dataProvider = "SPS_COD_Wallet_GCQty1_Packed_Cancel", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1. Create COD+Wallet order with JIT quantity=1. \n2.Release the order. \3. Check the order details in SPS Settlement detail table. \4. Validate Fund release amount")
	public void SPS_COD_Wallet_GCQty1_Packed_Cancel(String orderId, String store_order_id, String ppsId,
			String paymentPlanItemId, String message, String lineId, String packedMessage, String OrderReleaseId,
			String paymentPlanItemIdref, String ppsIdCancellation, String cancellationMessage)
			throws SQLException, IOException, InterruptedException {
		log.debug(orderId + " : " + ppsId + " : " + paymentPlanItemId);
		sellerHelper.rabbitMqAddTx(message, orderId);

		sellerHelper.updateOrderReleaseStatusAndPackedOn(OrderReleaseId, "PK");
		sellerHelper.rabbitMqReleasePKTx(packedMessage, orderId);
		Thread.sleep(10000);
		Assert.assertEquals(sellerHelper.getTxDBStatus1(orderId, "RELEASED"), 1,
				"Release Transaction db order id : " + orderId + " not found");
		Assert.assertEquals(sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED"), 2,
				"Release Settlement db order id : " + orderId + " not found");

		SellerPaymentDP.SPS_COD_Wallet_GCQty1_Packed_Cancel_Helper(orderId, store_order_id, OrderReleaseId,
				lineId, ppsId, ppsIdCancellation, paymentPlanItemIdref);
		sellerHelper.rabbitMqRefundTx(cancellationMessage, ppsIdCancellation);
		log.debug("Wait for 10Sec more to get order details in settlement detail table....");
		Thread.sleep(10000);

		int releaseTxResult1 = sellerHelper.getSettlementdetailDBStatusCount(orderId, "REFUNDED");
		Assert.assertEquals(releaseTxResult1, 2,
				"Refund Order_id:" + orderId + " Not available in SPS Settlement detail table");

		Assert.assertEquals(sellerHelper.getSettlementDetailRefundAmount(orderId, "ONLINE"), "-1399.00",
				"Wallet Refund amount:");

		sellerHelper.deleteFromPaymentPlanItemInstrument(paymentPlanItemId);
		sellerHelper.deleteOrderRecord(orderId, ppsId, paymentPlanItemId);
	}

	@Test(groups = {
			"Regression" }, priority = 0, dataProvider = "SPS_SellerSettlementCOD_Wallet_MultiSkuMultiQtyPK_Return", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1. Create online order with JIT MultiQty MultiSku1 COD(Tatal 4qty). \n2.Release all check released. \3. Check the order details in SPS Settlement detail table. \4. Validate Fund release amount")
	public void SPS_SellerSettlementCOD_Wallet_MultiSkuMultiQtyPK_Return(String orderId, String store_order_id,
			String ppsId, String paymentPlanItemId, String paymentPlanItemId1, String message, String packedMessage,
			String releaseFundMessage, String lineId, String ppsIdCancellation, String paymentPlanItemIdref,
			String OrderReleaseId, String paymentPlanItemIdref1, String returnMessage)
			throws SQLException, IOException, InterruptedException {

		log.debug(orderId + " : " + ppsId + " : " + paymentPlanItemId);
		sellerHelper.rabbitMqAddTx(message, orderId);

		sellerHelper.updateOrderReleaseStatusAndPackedOn(OrderReleaseId, "PK");
		sellerHelper.updateOrderLineStatus(orderId, "QD");
		sellerHelper.rabbitMqReleasePKTx(packedMessage, orderId);
		Thread.sleep(10000);
		Assert.assertEquals(sellerHelper.getTxDBStatus1(orderId, "RELEASED"), 2,
				"Release Transaction db order id : " + orderId + " not found");
		Assert.assertEquals(sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED"), 4,
				"Release Settlement db order id : " + orderId + " not found");

		SellerPaymentDP.createCod_Wallet_OrderWithSellerMultiQtyReleaseHelper(orderId, store_order_id,
				OrderReleaseId, paymentPlanItemIdref, ppsId, ppsIdCancellation, paymentPlanItemIdref1);
		sellerHelper.rabbitMqRefundTx(returnMessage, ppsIdCancellation);

		log.debug("Wait for 10Sec more to get order details in settlement detail table....");
		Thread.sleep(10000);

		int releaseTxResult1 = sellerHelper.getSettlementdetailDBStatusCount(orderId, "REFUNDED");
		Assert.assertEquals(releaseTxResult1, 4,
				"Refund Order_id:" + orderId + " Not available in SPS Settlement detail table");

		sellerHelper.deleteFromPaymentPlanItemInstrument(paymentPlanItemId1);
		sellerHelper.deleteFromOrders(orderId);
		sellerHelper.deleteFromOrdersRelease(orderId);
		sellerHelper.deleteFromOrdersLine(orderId);

	}

	@Test(groups = { "Regression",
			"Sanity" }, priority = 0, dataProvider = "SPS_CodWithShippingReleaseReturn", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1.Create online order with Vector qty1 sku1 with shipping charges. \n2.check the splits in citrus than in SPS for both sku and shipping. \n3.cancel the order. \n4.check the refund on citurs and sps both for both shipping and sku")
	public void SPS_CodWithShippingReleaseReturn(String orderId, String OrderReleaseId, String store_order_id,
			String message, String lineId, String ppsId, String paymentPlanItemId, String ppsIdCancellation,
			String paymentPlanItemIdref, String paymentPlanItemIdship, String paymentPlanItemIdrefship,
			String releaseFundMessage, String returnMessage) throws SQLException, IOException, InterruptedException {
		log.debug(orderId + " : " + ppsId + " : " + paymentPlanItemId);
		sellerHelper.rabbitMqAddTx(message, orderId);
		sellerHelper.updateOrderReleaseStatusAndPackedOn(OrderReleaseId, "PK");
		sellerHelper.updateOrderLineStatus(orderId, "QD");
		sellerHelper.rabbitMqReleasePKTx(releaseFundMessage, orderId);
		log.debug("Wait for 10Sec more to get order details in settlement detail table....");
		Thread.sleep(10000);

		int releaseTxResult = sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED");
		Assert.assertEquals(releaseTxResult, 2,
				"Release Order id:" + orderId + "  Not available in SPS Settlement detail table");

		SellerPaymentDP.createCod_Shipping_OrderQty1ReturnHelper(orderId, store_order_id, paymentPlanItemIdref,
				ppsId, ppsIdCancellation);
		sellerHelper.rabbitMqRefundTx(returnMessage, ppsIdCancellation);
		sellerHelper.rabbitMqRefundTx(returnMessage, ppsIdCancellation);
		sellerHelper.rabbitMqRefundTx(returnMessage, ppsIdCancellation);

		log.debug("Wait for 10Sec more to get order details in settlement detail table....");
		Thread.sleep(10000);

		int releaseTxResult1 = sellerHelper.getSettlementdetailDBStatusCount(orderId, "REFUNDED");
		Assert.assertEquals(releaseTxResult1, 1,
				"Return Order_id:" + orderId + " Not available in SPS Settlement detail table");
		Assert.assertEquals(sellerHelper.getSettlementDetailRefundAmount(orderId, "CASH_ON_DELIVERY"), "-2.00",
				"COD Refund amount:");

		sellerHelper.deleteFromOrders(orderId);
		sellerHelper.deleteFromOrdersRelease(orderId);
		sellerHelper.deleteFromOrdersLine(orderId);

	}

	@Test(groups = { "Regression",
			"Sanity" }, priority = 0, dataProvider = "SPS_CodMultiReleaseWithShippingReleaseReturn", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1.Create online order with Vector qty1 sku1 with shipping charges. \n2.check the splits in citrus than in SPS for both sku and shipping. \n3.cancel the order. \n4.check the refund on citurs and sps both for both shipping and sku")
	public void SPS_CodMultiReleaseWithShippingReleaseReturn(String orderId, String OrderReleaseId,
			String OrderReleaseId1, String store_order_id, String message, String lineId, String ppsId,
			String paymentPlanItemId, String ppsIdCancellation, String paymentPlanItemIdref,
			String paymentPlanItemIdship, String paymentPlanItemIdrefship, String releaseFundMessage,
			String returnMessage, String releaseMessage1) throws SQLException, IOException, InterruptedException {
		log.debug(orderId + " : " + ppsId + " : " + paymentPlanItemId);
		sellerHelper.rabbitMqAddTx(message, orderId);
		sellerHelper.updateOrderReleaseStatusAndPackedOn(OrderReleaseId, "PK");
		sellerHelper.updateOrderLineStatus(orderId, "QD");
		sellerHelper.rabbitMqReleasePKTx(releaseFundMessage, orderId);
		log.debug("Wait for 10Sec more to get order details in settlement detail table....");
		Thread.sleep(10000);

		int releaseTxResult = sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED");
		Assert.assertEquals(releaseTxResult, 2,
				"Release Order id:" + orderId + "  Not available in SPS Settlement detail table");

		sellerHelper.updateOrderReleaseStatusAndPackedOn(OrderReleaseId1, "PK");
		sellerHelper.rabbitMqReleasePKTx(releaseMessage1, orderId);
		log.debug("Wait for 10Sec more to get order details in settlement detail table....");

		Thread.sleep(10000);

		int releaseTxResult2 = sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED");
		Assert.assertEquals(releaseTxResult2, 3,
				"Release Order id:" + orderId + "  Not available in SPS Settlement detail table");

		SellerPaymentDP.createCod_Shipping_OrderQty1ReturnHelper(orderId, store_order_id, paymentPlanItemIdref,
				ppsId, ppsIdCancellation);
		sellerHelper.rabbitMqRefundTx(returnMessage, ppsIdCancellation);

		log.debug("Wait for 10Sec more to get order details in settlement detail table....");
		Thread.sleep(10000);

		int releaseTxResult1 = sellerHelper.getSettlementdetailDBStatusCount(orderId, "REFUNDED");
		Assert.assertEquals(releaseTxResult1, 1,
				"Return Order_id:" + orderId + " Not available in SPS Settlement detail table");
		Assert.assertEquals(sellerHelper.getSettlementDetailRefundAmount(orderId, "CASH_ON_DELIVERY"), "-2.00",
				"COD Refund amount:");

		sellerHelper.deleteFromOrders(orderId);
		sellerHelper.deleteFromOrdersRelease(orderId);
		sellerHelper.deleteFromOrdersLine(orderId);

	}

	@Test(groups = { "Regression",
			"Sanity" }, priority = 0, dataProvider = "SPS_Cod_WalletMultiReleaseWithShippingReleaseReturn", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1.Create online order with Vector qty1 sku1 with shipping charges. \n2.check the splits in citrus than in SPS for both sku and shipping. \n3.cancel the order. \n4.check the refund on citurs and sps both for both shipping and sku")
	public void SPS_Cod_WalletMultiReleaseWithShippingReleaseReturn(String orderId, String OrderReleaseId,
			String OrderReleaseId1, String store_order_id, String message, String lineId, String ppsId,
			String paymentPlanItemId, String ppsIdCancellation, String paymentPlanItemIdref,
			String paymentPlanItemIdship, String paymentPlanItemIdrefship, String releaseFundMessage,
			String returnMessage, String releaseMessage1, String deliverMessage1)
			throws SQLException, IOException, InterruptedException {
		log.debug(orderId + " : " + ppsId + " : " + paymentPlanItemId);
		sellerHelper.rabbitMqAddTx(message, orderId);
		sellerHelper.updateOrderReleaseStatusAndPackedOn(OrderReleaseId, "PK");
		sellerHelper.updateOrderLineStatus(orderId, "QD");
		sellerHelper.rabbitMqReleasePKTx(releaseFundMessage, orderId);
		log.debug("Wait for 10Sec more to get order details in settlement detail table....");
		Thread.sleep(10000);

		int releaseTxResult = sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED");
		Assert.assertEquals(releaseTxResult, 2,
				"Release Order id:" + orderId + "  Not available in SPS Settlement detail table");

		sellerHelper.updateOrderReleaseStatusAndPackedOn(OrderReleaseId1, "PK");
		sellerHelper.rabbitMqReleasePKTx(releaseMessage1, orderId);
		sellerHelper.rabbitMqReleaseDLTx(deliverMessage1, orderId);
		Thread.sleep(10000);

		int releaseTxResult1 = sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED");
		Assert.assertEquals(releaseTxResult1, 3,
				"Release Order id:" + orderId + "  Not available in SPS Settlement detail table");

		SellerPaymentDP.createCod_Shipping_OrderQty1ReturnHelper(orderId, store_order_id, paymentPlanItemIdref,
				ppsId, ppsIdCancellation);
		sellerHelper.rabbitMqRefundTx(returnMessage, ppsIdCancellation);

		log.debug("Wait for 10Sec more to get order details in settlement detail table....");
		Thread.sleep(10000);

		int releaseTxResult2 = sellerHelper.getSettlementdetailDBStatusCount(orderId, "REFUNDED");
		Assert.assertEquals(releaseTxResult2, 1,
				"Return Order_id:" + orderId + " Not available in SPS Settlement detail table");
		Assert.assertEquals(sellerHelper.getSettlementDetailRefundAmount(orderId, "CASH_ON_DELIVERY"), "-2.00",
				"COD Refund amount:");

		sellerHelper.deleteFromOrders(orderId);
		sellerHelper.deleteFromOrdersRelease(orderId);
		sellerHelper.deleteFromOrdersLine(orderId);

	}

	@Test(groups = { "Regression",
			"Sanity" }, priority = 0, dataProvider = "SPS_CodWithDifferentSellersMultiReleaseWithShippingReleaseReturn", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1.Create online order with Vector qty1 sku1 with shipping charges. \n2.check the splits in citrus than in SPS for both sku and shipping. \n3.cancel the order. \n4.check the refund on citurs and sps both for both shipping and sku")
	public void SPS_CodWithDifferentSellersMultiReleaseWithShippingReleaseReturn(String orderId, String OrderReleaseId,
			String OrderReleaseId1, String store_order_id, String message, String lineId, String ppsId,
			String paymentPlanItemId, String ppsIdCancellation, String paymentPlanItemIdref,
			String paymentPlanItemIdship, String paymentPlanItemIdrefship, String releaseFundMessage,
			String returnMessage, String releaseMessage1, String deliverMessage1)
			throws SQLException, IOException, InterruptedException {
		log.debug(orderId + " : " + ppsId + " : " + paymentPlanItemId);
		sellerHelper.rabbitMqAddTx(message, orderId);
		sellerHelper.updateOrderReleaseStatusAndPackedOn(OrderReleaseId, "PK");
		sellerHelper.updateOrderLineStatus(orderId, "QD");

		sellerHelper.rabbitMqReleasePKTx(releaseFundMessage, orderId);
		log.debug(releaseFundMessage);
		log.debug("Wait for 10Sec more to get order details in settlement detail table....");
		Thread.sleep(10000);

		int releaseTxResult = sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED");
		Assert.assertEquals(releaseTxResult, 2,
				"Release Order id:" + orderId + "  Not available in SPS Settlement detail table");

		sellerHelper.updateOrderReleaseStatusAndPackedOn(OrderReleaseId1, "PK");
		sellerHelper.rabbitMqReleasePKTx(releaseMessage1, orderId);
		sellerHelper.rabbitMqReleaseDLTx(deliverMessage1, orderId);
		Thread.sleep(10000);
		int releaseTxResult1 = sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED");
		Assert.assertEquals(releaseTxResult1, 3,
				"Release Order id:" + orderId + "  Not available in SPS Settlement detail table");

		SellerPaymentDP.createCod_Shipping_OrderQty1ReturnHelper(orderId, store_order_id, paymentPlanItemIdref,
				ppsId, ppsIdCancellation);
		sellerHelper.rabbitMqRefundTx(returnMessage, ppsIdCancellation);

		log.debug("Wait for 10Sec more to get order details in settlement detail table....");
		Thread.sleep(10000);

		int releaseTxResult2 = sellerHelper.getSettlementdetailDBStatusCount(orderId, "REFUNDED");
		Assert.assertEquals(releaseTxResult2, 1,
				"Return Order_id:" + orderId + " Not available in SPS Settlement detail table");
		Assert.assertEquals(sellerHelper.getSettlementDetailRefundAmount(orderId, "CASH_ON_DELIVERY"), "-2.00",
				"COD Refund amount:");

		sellerHelper.deleteFromOrders(orderId);
		sellerHelper.deleteFromOrdersRelease(orderId);
		sellerHelper.deleteFromOrdersLine(orderId);

	}

	@Test(groups = { "Regression",
			"Sanity" }, priority = 0, dataProvider = "SPS_CodWithDifferentSellersMultiReleaseWithShippingReleaseReturn", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1.Create online order with Vector qty1 sku1 with shipping charges. \n2.check the splits in citrus than in SPS for both sku and shipping. \n3.cancel the order. \n4.check the refund on citurs and sps both for both shipping and sku")
	public void SPS_CodWithDifferentSellersMultiReleaseWithShippingReleaseReturn1(String orderId, String OrderReleaseId,
			String OrderReleaseId1, String store_order_id, String message, String lineId, String ppsId,
			String paymentPlanItemId, String ppsIdCancellation, String paymentPlanItemIdref,
			String paymentPlanItemIdship, String paymentPlanItemIdrefship, String releaseFundMessage,
			String returnMessage, String releaseMessage1, String deliverMessage1)
			throws SQLException, IOException, InterruptedException {
		log.debug(orderId + " : " + ppsId + " : " + paymentPlanItemId);
		sellerHelper.rabbitMqAddTx(message, orderId);
		sellerHelper.updateOrderReleaseStatusAndPackedOn(OrderReleaseId1, "PK");
		sellerHelper.updateOrderLineStatus(orderId, "QD");

		sellerHelper.rabbitMqReleasePKTx(releaseMessage1, orderId);
		sellerHelper.rabbitMqReleaseDLTx(deliverMessage1, orderId);
		log.debug("Wait for 10Sec more to get order details in settlement detail table....");
		Thread.sleep(10000);

		int releaseTxResult = sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED");
		Assert.assertEquals(releaseTxResult, 1,
				"Release Order id:" + orderId + "  Not available in SPS Settlement detail table");

		sellerHelper.updateOrderReleaseStatusAndPackedOn(OrderReleaseId, "PK");
		sellerHelper.rabbitMqReleasePKTx(releaseFundMessage, orderId);
		log.debug("Wait for 10Sec more to get order details in settlement detail table....");

		Thread.sleep(10000);

		int releaseTxResult1 = sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED");
		Assert.assertEquals(releaseTxResult1, 3,
				"Release Order id:" + orderId + "  Not available in SPS Settlement detail table");

		SellerPaymentDP.createCod_Shipping_OrderQty1ReturnHelper(orderId, store_order_id, paymentPlanItemIdref,
				ppsId, ppsIdCancellation);
		sellerHelper.rabbitMqRefundTx(returnMessage, ppsIdCancellation);

		log.debug("Wait for 10Sec more to get order details in settlement detail table....");
		Thread.sleep(10000);

		int releaseTxResult2 = sellerHelper.getSettlementdetailDBStatusCount(orderId, "REFUNDED");
		Assert.assertEquals(releaseTxResult2, 1,
				"Return Order_id:" + orderId + " Not available in SPS Settlement detail table");
		Assert.assertEquals(sellerHelper.getSettlementDetailRefundAmount(orderId, "CASH_ON_DELIVERY"), "-2.00",
				"COD Refund amount:");

		sellerHelper.deleteFromOrders(orderId);
		sellerHelper.deleteFromOrdersRelease(orderId);
		sellerHelper.deleteFromOrdersLine(orderId);

	}

	@Test(groups = { "Regression",
			"Sanity" }, priority = 0, dataProvider = "SPS_CodWithDifferentSellersMultiRelease1CancelSkuWithShippingChrgs", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1.Create online order with Vector qty1 sku1 with shipping charges. \n2.check the splits in citrus than in SPS for both sku and shipping. \n3.cancel the order. \n4.check the refund on citurs and sps both for both shipping and sku")
	public void SPS_CodWithDifferentSellersMultiRelease1CancelSkuWithShippingChrgs(String orderId,
			String store_order_id, String message, String lineId, String ppsId, String paymentPlanItemId,
			String ppsIdCancellation, String paymentPlanItemIdref, String paymentPlanItemIdship,
			String paymentPlanItemIdrefship, String releaseFundMessage, String returnMessage, String releaseMessage1,
			String deliverMessage1, String cancellationMessage, String ppsIdLineCancellation,
			String paymentPlanItemIdCancel, String OrderReleaseId, String OrderReleaseId1, String paymentPlanItemIdref1)
			throws Exception {
		log.debug(orderId + " : " + ppsId + " : " + paymentPlanItemId);
		sellerHelper.rabbitMqAddTx(message, orderId);
		sellerHelper.updateOrderReleaseStatusAndPackedOn(OrderReleaseId1, "PK");
		sellerHelper.updateOrderLineStatus(orderId, "QD");

		sellerHelper.rabbitMqReleasePKTx(releaseMessage1, orderId);

		sellerHelper.rabbitMqReleaseDLTx(deliverMessage1, orderId);
		log.debug("Wait for 10Sec more to get order details in settlement detail table....");
		Thread.sleep(10000);

		int releaseTxResult = sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED");
		Assert.assertEquals(releaseTxResult, 1,
				"Release Order id:" + orderId + "  Not available in SPS Settlement detail table");

		SellerPaymentDP.SPS_CodWithDifferentSellersMultiRelease1CancelSkuWithShippingChrgsLineCancellation(
				lineId, orderId, store_order_id, OrderReleaseId, ppsId, ppsIdLineCancellation, paymentPlanItemIdCancel,
				paymentPlanItemIdref1);
		sellerHelper.rabbitMqRefundTx(cancellationMessage, ppsIdCancellation);
		Thread.sleep(10000);
		int releaseTxResult1 = sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED");
		int releaseTxRefunded = sellerHelper.getSettlementdetailDBStatusCount(orderId, "REFUNDED");

		Assert.assertEquals(releaseTxResult1, 1,
				"Release Order id:" + orderId + "  Not available in SPS Settlement detail table");
		Assert.assertEquals(releaseTxRefunded, 0,
				"Refund Order id:" + orderId + "  Not available in SPS Settlement detail table");
		sellerHelper.deleteFromOrders(orderId);
		sellerHelper.deleteFromOrdersRelease(orderId);
		sellerHelper.deleteFromOrdersLine(orderId);

	}

	// Create online order with Vectore qty1 sku 1 with SDD charges. Check split
	// for SDD as well release and mark sdd breach for that sku and check refun
	// for SDD charges.
	@Test(groups = {
			"Regression" }, priority = 0, dataProvider = "SPS_CodQty1SDDBreach", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1.Create online order with Vector qty1 sku1 with SDD charges. \n2.check the splits in citrus than in SPS for both sku and SDD. \n3.release the order and check the release for both. "
					+ "\n4.Mark SDD breach and check the refund happning only for SDD charges")
	public void SPS_CodQty1SDDBreachAfterRelease(String message, String releaseMessage, String refundMessage,
			String orderId, String OrderReleaseId, String store_order_id, String lineId, String ppsId,
			String paymentPlanItemId, String ppsIdCancellation, String paymentPlanItemIdref,
			String paymentPlanItemIdship, String refundMessage1, String ppsIdSkuCancellation,
			String paymentPlanItemIdref1) throws SQLException, IOException, InterruptedException {
		log.debug(orderId + " : " + ppsId + " : " + paymentPlanItemId);
		sellerHelper.rabbitMqAddTx(message, orderId);
		sellerHelper.updateOrderReleaseStatusAndPackedOn(OrderReleaseId, "PK");
		sellerHelper.updateOrderLineStatus(orderId, "QD");
		sellerHelper.rabbitMqReleasePKTx(releaseMessage, orderId);

		SellerPaymentDP.SPS_CodQty1SDDBreachHelper(orderId, store_order_id, ppsId, ppsIdCancellation,
				paymentPlanItemIdref);
		sellerHelper.rabbitMqRefundTx(refundMessage, ppsIdCancellation);
		log.debug("Wait for 10Sec more to get order details in settlement detail table....");
		Thread.sleep(10000);

		int releaseTxResult = sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED");
		Assert.assertEquals(releaseTxResult, 2,
				"Release Order id : " + orderId + " Not available in SPS Settlement detail table");

		int releaseTxResult1 = sellerHelper.getSettlementdetailDBStatusCount(orderId, "REFUNDED");
		Assert.assertEquals(releaseTxResult1, 1,
				"Shipping chrgs Refund Order id : " + orderId + " Not available in SPS Settlement detail table");
		Assert.assertEquals(sellerHelper.getSettlementDetailRefundAmount(orderId, "CASH_ON_DELIVERY"), "-25.00",
				"Shipping chrgs COD Refund amount:");

		SellerPaymentDP.SPS_CodQty1SDDBreachSkuReturnHelper(orderId, store_order_id, paymentPlanItemIdref1,
				ppsId, ppsIdSkuCancellation);
		sellerHelper.rabbitMqRefundTx(refundMessage1, ppsIdSkuCancellation);
		log.debug("Wait for 10Sec more to get order details in settlement detail table....");
		Thread.sleep(10000);
		int releaseTxResult2 = sellerHelper.getSettlementdetailDBStatusCount(orderId, "REFUNDED");
		Assert.assertEquals(releaseTxResult2, 2,
				"SKU Refund Order id : " + orderId + " Not available in SPS Settlement detail table");

		sellerHelper.deleteFromOrders(orderId);
		sellerHelper.deleteFromOrdersRelease(orderId);
		sellerHelper.deleteFromOrdersLine(orderId);

	}

	@Test(groups = {
			"Regression" }, priority = 0, dataProvider = "SPS_CodQty1SDDBreachAfterReleasePK_Cancel", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1.Create online order with Vector qty1 sku1 with SDD charges. \n2.check the splits in citrus than in SPS for both sku and SDD. \n3.release the order and check the release for both. "
					+ "\n4.Mark SDD breach and check the refund happning only for SDD charges")
	public void SPS_CodQty1SDDBreachAfterReleasePK_Cancel(String message, String releaseMessage, String refundMessage,
			String orderId, String OrderReleaseId, String store_order_id, String lineId, String ppsId,
			String paymentPlanItemId, String ppsIdCancellation, String paymentPlanItemIdref,
			String paymentPlanItemIdship, String skuCancellationMessage, String ppsIdSkuCancellation,
			String paymentPlanItemIdref1) throws SQLException, IOException, InterruptedException {
		log.debug(orderId + " : " + ppsId + " : " + paymentPlanItemId);
		sellerHelper.rabbitMqAddTx(message, orderId);
		sellerHelper.updateOrderReleaseStatusAndPackedOn(OrderReleaseId, "PK");
		sellerHelper.updateOrderLineStatus(orderId, "QD");
		sellerHelper.rabbitMqReleasePKTx(releaseMessage, orderId);

		SellerPaymentDP.SPS_CodQty1SDDBreachHelper(orderId, store_order_id, ppsId, ppsIdCancellation,
				paymentPlanItemIdref);
		sellerHelper.rabbitMqRefundTx(refundMessage, ppsIdCancellation);
		log.debug("Wait for 10Sec more to get order details in settlement detail table....");
		Thread.sleep(10000);

		int releaseTxResult = sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED");
		Assert.assertEquals(releaseTxResult, 2,
				"Release Order id : " + orderId + " Not available in SPS Settlement detail table");

		int releaseTxResult1 = sellerHelper.getSettlementdetailDBStatusCount(orderId, "REFUNDED");
		Assert.assertEquals(releaseTxResult1, 1,
				"Shipping chrgs Refund Order id : " + orderId + " Not available in SPS Settlement detail table");
		Assert.assertEquals(sellerHelper.getSettlementDetailRefundAmount(orderId, "CASH_ON_DELIVERY"), "-25.00",
				"Shipping chrgs COD Refund amount:");

		SellerPaymentDP.SPS_CodQty1SDDBreachAfterReleasePK_CancelHelper(orderId, store_order_id, ppsId,
				ppsIdSkuCancellation, paymentPlanItemIdref1);
		sellerHelper.rabbitMqRefundTx(skuCancellationMessage, ppsIdSkuCancellation);
		log.debug("Wait for 10Sec more to get order details in settlement detail table....");
		Thread.sleep(10000);
		int releaseTxResult2 = sellerHelper.getSettlementdetailDBStatusCount(orderId, "REFUNDED");
		Assert.assertEquals(releaseTxResult2, 2,
				"SKU Refund Order id : " + orderId + " Not available in SPS Settlement detail table");

		sellerHelper.deleteFromOrders(orderId);
		sellerHelper.deleteFromOrdersRelease(orderId);
		sellerHelper.deleteFromOrdersLine(orderId);

	}

	@Test(groups = {
			"Regression" }, priority = 0, dataProvider = "SPS_CodQty1SDDBreachBeforeRelease", dataProviderClass = SellerPaymentDP.class, enabled = false, description = "1.Create online order with Vector qty1 sku1 with SDD charges. \n2.check the splits in citrus than in SPS for both sku and SDD. \n3.release the order and check the release for both. "
					+ "\n4.Mark SDD breach and check the refund happning only for SDD charges")
	public void SPS_CodQty1SDDBreachBeforeRelease(String message, String releaseMessage, String refundMessage,
			String orderId, String store_order_id, String lineId, String ppsId, String paymentPlanItemId,
			String ppsIdCancellation, String paymentPlanItemIdref, String paymentPlanItemIdship, String refundMessage1,
			String ppsIdSkuCancellation, String paymentPlanItemIdref1, String refundMessageVatAdjustment,
			String ppsIdrefundVatRefund) throws SQLException, IOException, InterruptedException {
		log.debug(orderId + " : " + ppsId + " : " + paymentPlanItemId);
		sellerHelper.rabbitMqAddTx(message, orderId);

		SellerPaymentDP.SPS_CodQty1SDDBreachHelper(orderId, store_order_id, ppsId, ppsIdCancellation,
				paymentPlanItemIdref);
		sellerHelper.rabbitMqRefundTx(refundMessageVatAdjustment, ppsIdrefundVatRefund);
		sellerHelper.rabbitMqRefundTx(refundMessage, ppsIdCancellation);
		log.debug("Wait for 10Sec more to get order details in settlement detail table....");
		Thread.sleep(10000);
		sellerHelper.updateOrderReleaseStatus(orderId, "PK");
		sellerHelper.updateOrderLineStatus(orderId, "QD");
		sellerHelper.rabbitMqReleasePKTx(releaseMessage, orderId);

		Thread.sleep(10000);
		int releaseTxResult = sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED");
		Assert.assertEquals(releaseTxResult, 2,
				"Release Order id : " + orderId + " Not available in SPS Settlement detail table");

		int releaseTxResult1 = sellerHelper.getSettlementdetailDBStatusCount(orderId, "REFUNDED");
		Assert.assertEquals(releaseTxResult1, 1,
				"Shipping chrgs Refund Order id : " + orderId + " Not available in SPS Settlement detail table");
		Assert.assertEquals(sellerHelper.getSettlementDetailRefundAmount(orderId, "CASH_ON_DELIVERY"), "-25.00",
				"Shipping chrgs COD Refund amount:");

		SellerPaymentDP.SPS_CodQty1SDDBreachSkuReturnHelper(orderId, store_order_id, paymentPlanItemIdref1,
				ppsId, ppsIdSkuCancellation);
		sellerHelper.rabbitMqRefundTx(refundMessage1, ppsIdSkuCancellation);
		log.debug("Wait for 10Sec more to get order details in settlement detail table....");
		Thread.sleep(10000);
		int releaseTxResult2 = sellerHelper.getSettlementdetailDBStatusCount(orderId, "REFUNDED");
		Assert.assertEquals(releaseTxResult2, 2,
				"SKU Refund Order id : " + orderId + " Not available in SPS Settlement detail table");

		sellerHelper.deleteFromOrders(orderId);
		sellerHelper.deleteFromOrdersRelease(orderId);
		sellerHelper.deleteFromOrdersLine(orderId);

	}

	@Test(groups = {
			"Regression" }, priority = 0, dataProvider = "SPS_CodQty1PriceOverRide", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1.Create online order with JIT qty1 sku1. \n2.check the split in citrus than in SPS. \n3.mark overwrite price. "
					+ "\n4.check that the fund deducted from split, order amount should remain same on citrus\n5.check split amount deducted on DB\n6.check refund for the override amount")
	public void SPS_CodQty1PriceOverRide(String orderId, String ppsId, String paymentPlanItemId, String message,
			String OrderReleaseId, String lineId, String refundMessage, String paymentPlanItemIdref, String ppsIdrefund,
			String releaseFundMessage) throws SQLException, IOException, InterruptedException, JAXBException {
		log.debug(orderId + " : " + ppsId + " : " + paymentPlanItemId);
		sellerHelper.rabbitMqAddTx(message, orderId);

		sellerHelper.rabbitMqRefundTx(refundMessage, ppsIdrefund);
		sellerHelper.updateOrderReleaseStatusAndPackedOn(OrderReleaseId, "PK");
		sellerHelper.updateOrderLineStatus(orderId, "QD");
		sellerHelper.rabbitMqReleasePKTx(releaseFundMessage, orderId);
		log.debug("Wait for 10Sec more to get order details in settlement detail table....");
		Thread.sleep(10000);

		int releaseTxResult = sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED");
		Assert.assertEquals(releaseTxResult, 1,
				"Order id" + orderId + " Not available in SPS Settlement detail table :" + orderId);
		Assert.assertEquals(sellerHelper.getSettlementDetailFundReleaseAmount(orderId, "RELEASED"), "699.00",
				"COD Fund Release amount:");

		sellerHelper.deleteFromOrdersLineAdditionalInfo(lineId);
		sellerHelper.deleteOrderRecord(orderId, ppsId, paymentPlanItemId, ppsIdrefund, paymentPlanItemIdref);
	}

	// Create online order with seller qty1 sku 1 and release fund on PK. Check
	// split and release for that sku in citrus and Db both
	@Test(groups = { "Regression","myntcredit",
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

	@Test(groups = { "Regression",
			"Sanity" }, priority = 0, dataProvider = "SPS_CodQty1ReleaseAndReturnInCBandLP", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1.Create online order with seller qty1 sku1. \n2.check the split in citrus than in SPS. \n3.Release the sku and check release in both(On order Packed). "
					+ "\n4.Return the sku and check the refund in both")
	public void SPS_CodQty1ReleaseAndReturnInCBandLP(String message, String releaseFundMessage, String returnMessage,
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

		SellerPaymentDP.SPS_CodQty1ReleaseAndReturnInCBandLPHelper(orderId, store_order_id, OrderReleaseId,
				paymentPlanItemIdref, ppsId, ppsIdCancellation);
		sellerHelper.rabbitMqRefundTx(returnMessage, ppsIdCancellation);

		log.debug("Wait for 10Sec more to get order details in settlement detail table....");
		Thread.sleep(10000);

		int releaseTxResult1 = sellerHelper.getSettlementdetailDBStatusCount(orderId, "REFUNDED");
		Assert.assertEquals(releaseTxResult1, 1,
				"Refund Order id : " + orderId + " Not available in SPS Settlement detail table");
		sellerHelper.deleteFromOrders(orderId);
		sellerHelper.deleteOrderRecord(orderId, ppsId, paymentPlanItemId, ppsIdCancellation, paymentPlanItemIdref);
	}

	// Create online order with seller qty1 sku 1 and release fund on PK. Check
	// split and release for that sku in citrus and Db both
	@Test(groups = { "Regression",
			"Sanity" }, priority = 0, dataProvider = "SPS_CodQty2ReleaseAndReturn", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1.Create online order with seller qty1 sku1. \n2.check the split in citrus than in SPS. \n3.Release the sku and check release in both(On order Packed). "
					+ "\n4.Return the sku and check the refund in both")
	public void SPS_CodQty2ReleaseAndReturn(String message, String releaseFundMessage, String returnMessage,
			String orderId, String store_order_id, String OrderReleaseId, String lineId, String ppsId,
			String ppsIdCancellation, String paymentPlanItemId, String paymentPlanItemIdref)
			throws SQLException, IOException, InterruptedException {
		log.debug(orderId + " : " + ppsId + " : " + paymentPlanItemId);
		sellerHelper.rabbitMqAddTx(message, orderId);
		sellerHelper.updateOrderReleaseStatusAndPackedOn(OrderReleaseId, "PK");
		sellerHelper.updateOrderLineStatus(orderId, "QD");
		sellerHelper.rabbitMqReleasePKTx(releaseFundMessage, orderId);
		sellerHelper.rabbitMqReleasePKTx(releaseFundMessage, orderId);
		log.debug("Wait for 10Sec more to get order details in settlement detail table....");
		Thread.sleep(10000);

		int releaseTxResult = sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED");
		Assert.assertEquals(releaseTxResult, 2, "Order id Not available in SPS Settlement detail table");

		SellerPaymentDP.createCodOrderWithSellerQty2ReleaseHelper(orderId, store_order_id, OrderReleaseId,
				paymentPlanItemIdref, ppsId, ppsIdCancellation);
		sellerHelper.rabbitMqRefundTx(returnMessage, ppsIdCancellation);

		log.debug("Wait for 10Sec more to get order details in settlement detail table....");
		Thread.sleep(10000);

		int releaseTxResult1 = sellerHelper.getSettlementdetailDBStatusCount(orderId, "REFUNDED");
		Assert.assertEquals(releaseTxResult1, 2, "Order id Not available in SPS Settlement detail table");

		sellerHelper.deleteOrderRecord(orderId, ppsId, paymentPlanItemId, ppsIdCancellation, paymentPlanItemIdref);
	}

	@Test(groups = { "Regression",
			"Sanity" }, priority = 0, dataProvider = "SPS_CodQty2ReleaseAndReturnSingle", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1.Create online order with seller qty1 sku1. \n2.check the split in citrus than in SPS. \n3.Release the sku and check release in both(On order Packed). "
					+ "\n4.Return the sku and check the refund in both")
	public void SPS_CodQty2ReleaseAndReturnSingle(String message, String releaseFundMessage, String returnMessage,
			String orderId, String store_order_id, String OrderReleaseId, String lineId, String ppsId,
			String ppsIdCancellation, String paymentPlanItemId, String paymentPlanItemIdref, String sellerId)
			throws SQLException, IOException, InterruptedException, Exception {
		log.debug(orderId + " : " + ppsId + " : " + paymentPlanItemId);
		sellerHelper.rabbitMqAddTx(message, orderId);

		sellerHelper.updateOrderReleaseStatusAndPackedOn(OrderReleaseId, "PK");
		sellerHelper.updateOrderLineStatus(orderId, "QD");
		sellerHelper.rabbitMqReleasePKTx(releaseFundMessage, orderId);

		log.debug("Wait for 10Sec more to get order details in settlement detail table....");
		Thread.sleep(10000);

		int releaseTxResult = sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED");
		Assert.assertEquals(releaseTxResult, 2,
				"Releas Order id: " + orderId + " Not available in SPS Settlement detail table");

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

		SellerPaymentDP.createCodOrderWithSellerQty2Return1Helper(orderId, store_order_id, OrderReleaseId,
				paymentPlanItemIdref, ppsId, ppsIdCancellation);
		sellerHelper.rabbitMqRefundTx(returnMessage, ppsIdCancellation);

		log.debug("Wait for 10Sec more to get order details in settlement detail table....");
		Thread.sleep(10000);

		int releaseTxResult1 = sellerHelper.getSettlementdetailDBStatusCount(orderId, "REFUNDED");
		Assert.assertEquals(releaseTxResult1, 1,
				"Refund Order id : " + orderId + " Not available in SPS Settlement detail table");
		Assert.assertEquals(sellerHelper.getSettlementDetailRefundAmount(orderId, "CASH_ON_DELIVERY"), "-400.00",
				"COD Refund amount:");

		BigDecimal settlementCommissionAmountRefund = sellerHelper.getSettlementDetailCommissionAmount(OrderReleaseId,
				TransactionSplitStatus.REFUNDED.toString());
		BigDecimal settlementCgstTaxRateRefund = sellerHelper.getSettlementDetailCgstTaxRate(OrderReleaseId,
				TransactionSplitStatus.REFUNDED.toString());
		BigDecimal settlementSgstTaxRateRefund = sellerHelper.getSettlementDetailSgstTaxRate(OrderReleaseId,
				TransactionSplitStatus.REFUNDED.toString());
		BigDecimal settlementCgstTaxAmountRefund = sellerHelper.getSettlementDetailCgstTaxAmount(OrderReleaseId,
				TransactionSplitStatus.REFUNDED.toString());
		BigDecimal settlementSgstTaxAmountRefund = sellerHelper.getSettlementDetailSgstTaxAmount(OrderReleaseId,
				TransactionSplitStatus.REFUNDED.toString());
		String pincodeRefund = sellerHelper.getSellerBillingPinCode(sellerId);

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

		Assert.assertEquals(mapRefund.get(TaxType.CGST).doubleValue(), settlementCgstTaxRateRefund.doubleValue(),
				" CGST Rate :");
		Assert.assertEquals(mapRefund.get(TaxType.SGST).doubleValue(), settlementSgstTaxRateRefund.doubleValue(),
				" SGST Rate :");

		Double cgstCalcRefund = (settlementCommissionAmountRefund.doubleValue()
				* mapRefund.get(TaxType.CGST).doubleValue())
				/ (1 + mapRefund.get(TaxType.CGST) + mapRefund.get(TaxType.SGST));
		Double SgstCalcRefund = (settlementCommissionAmountRefund.doubleValue()
				* mapRefund.get(TaxType.SGST).doubleValue())
				/ (1 + mapRefund.get(TaxType.CGST) + mapRefund.get(TaxType.SGST));
		log.debug("CGST Tax Amount Calculation: " + cgstCalcRefund);
		log.debug("SGST Tax Amount Calculation: " + SgstCalcRefund);

		Assert.assertEquals(settlementCgstTaxAmountRefund.doubleValue(), cgstCalcRefund.doubleValue(),
				" CGST Tax Amount :");
		Assert.assertEquals(settlementSgstTaxAmountRefund.doubleValue(), SgstCalcRefund.doubleValue(),
				" SGST Tax Amount :");

		sellerHelper.deleteOrderRecord(orderId, ppsId, paymentPlanItemId, ppsIdCancellation, paymentPlanItemIdref);
	}

	@Test(groups = {
			"Regression" }, priority = 0, dataProvider = "SPS_CodMultiQtyRelRTO", dataProviderClass = SellerPaymentDP.class, enabled = false, description = "1. Create online order with seller qty3 sku1. \n2.check the split in citrus than in SPS for all 3qty.\n3.release all 3 qty and check releases on both.\n "
					+ "4.Mark item rto. \n5.Check the refund on both")
	public void SPS_CodMultiQtyRelRTO(String orderId, String store_order_id, String ppsId, String ppsIdCancellation,
			String paymentPlanItemId, String paymentPlanItemIdref, String message, String lineId,
			String paymentPlanExecutionStatus_id, String releaseMessage, String OrderReleaseId, String OrderReleaseId1,
			String returnMessage, String ppsIdReturn, String paymentPlanItemIdret)
			throws SQLException, IOException, InterruptedException {
		log.debug("---------orderId: " + orderId + " ppsId: " + ppsId + " PymentPlanItemId: " + paymentPlanItemId);
		sellerHelper.rabbitMqAddTx(message, orderId);

		sellerHelper.updateOrderReleaseStatusAndPackedOn(OrderReleaseId, "PK");
		sellerHelper.updateOrderLineStatus(orderId, "QD");
		sellerHelper.rabbitMqReleasePKTx(releaseMessage, orderId);

		log.debug("Wait for 10Sec more to get order details in settlement detail table....");
		Thread.sleep(10000);

		int releaseTxResult = sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED");
		Assert.assertEquals(releaseTxResult, 3,
				"Release Order id " + orderId + " Not available in SPS Settlement detail table");

		SellerPaymentDP.SPS_CodMultiQtyRelRTOHelper(orderId, store_order_id, OrderReleaseId1,
				paymentPlanItemIdret, ppsId, ppsIdReturn);
		sellerHelper.rabbitMqRefundTx(returnMessage, ppsIdCancellation);

		log.debug("Wait for 10Sec more to get order details in settlement detail table....");
		Thread.sleep(10000);

		int releaseTxResult1 = sellerHelper.getSettlementdetailDBStatusCount(orderId, "REFUNDED");
		Assert.assertEquals(releaseTxResult1, 3,
				"Refund Order id " + orderId + " Not available in SPS Settlement detail table");

		sellerHelper.deleteFromPaymentPlanItemInstrument(paymentPlanItemIdret);
		sellerHelper.deleteOrderRecord(orderId, ppsId, paymentPlanItemId, ppsIdReturn, paymentPlanItemIdref);

	}

	// Create online order with seller qty1 sku 1 and release fund on PK. Check
	// split and release for that sku in citrus and Db both
	@Test(groups = { "Regression",
			"Sanity" }, priority = 0, dataProvider = "SPS_CodTryAndBuy2", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1.Create online order with seller qty1 sku1. \n2.check the split in citrus than in SPS. \n3.Release the sku and check release in both(On order Packed). "
					+ "\n4.Return the sku and check the refund in both")
	public void SPS_CodTryAndBuy2(String message, String releaseFundMessage, String returnMessage, String orderId,
			String store_order_id, String OrderReleaseId, String lineId, String ppsId, String ppsIdCancellation,
			String paymentPlanItemId, String paymentPlanItemIdref, String paymentPlanItemIdref1)
			throws SQLException, IOException, InterruptedException {
		log.debug(orderId + " : " + ppsId + " : " + paymentPlanItemId);
		sellerHelper.rabbitMqAddTx(message, orderId);

		sellerHelper.updateOrderReleaseStatusAndPackedOn(OrderReleaseId, "PK");
		sellerHelper.updateOrderLineStatus(orderId, "QD");
		sellerHelper.rabbitMqReleasePKTx(releaseFundMessage, orderId);

		log.debug("Wait for 10Sec more to get order details in settlement detail table....");
		Thread.sleep(10000);

		int releaseTxResult = sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED");
		Assert.assertEquals(releaseTxResult, 2, "Order id Not available in SPS Settlement detail table");

		SellerPaymentDP.createCodOrderWithVectorQty2ReleaseHelper1(orderId, store_order_id, OrderReleaseId,
				paymentPlanItemIdref, ppsId, ppsIdCancellation, paymentPlanItemIdref1);
		sellerHelper.rabbitMqRefundTx(returnMessage, ppsIdCancellation);

		log.debug("Wait for 10Sec more to get order details in settlement detail table....");
		Thread.sleep(10000);

		int releaseTxResult1 = sellerHelper.getSettlementdetailDBStatusCount(orderId, "REFUNDED");
		Assert.assertEquals(releaseTxResult1, 2, "Order id Not available in SPS Settlement detail table");

		sellerHelper.deleteOrderRecord(orderId, ppsId, paymentPlanItemId, ppsIdCancellation, paymentPlanItemIdref);

	}

	// Create online order with JIT Multi Sku and Multi qty release all. Check
	// the orders in settlement detail table
	@Test(groups = {
			"Regression" }, priority = 0, dataProvider = "SPS_SellerSettlementRptCODMultiQty_Diff_Releases_PK_Return", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1. Create online order with JIT MultiQty MultiSku1 COD(Tatal 4qty). \n2.Release all check released. \3. Check the order details in SPS Settlement detail table. \4. Validate Fund release amount")
	public void SPS_SellerSettlementRptCODMultiQty_Diff_Releases_PK_1_Return(String orderId, String store_order_id,
			String OrderReleaseId1, String ppsId, String paymentPlanItemId, String paymentPlanItemId1, String message,
			String releaseFundMessage, String ppsIdCancellation, String paymentPlanItemIdref, String OrderReleaseId,
			String paymentPlanItemIdref1, String returnMessage, String releaseFundMessage1)
			throws SQLException, IOException, InterruptedException {
		log.debug(orderId + " : " + ppsId + " : " + paymentPlanItemId);
		sellerHelper.rabbitMqAddTx(message, orderId);
		sellerHelper.updateOrderReleaseStatusAndPackedOn(OrderReleaseId, "PK");
		sellerHelper.updateOrderLineStatus(orderId, "QD");
		sellerHelper.rabbitMqReleasePKTx(releaseFundMessage, orderId);

		sellerHelper.updateOrderReleaseStatusAndPackedOn(OrderReleaseId1, "PK");
		sellerHelper.updateOrderLineStatus(OrderReleaseId1, "QD");
		sellerHelper.rabbitMqReleasePKTx(releaseFundMessage1, orderId);

		log.debug("Wait for 10Sec more to get order details in settlement detail table....");
		Thread.sleep(10000);

		int releaseTxResult = sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED");
		Assert.assertEquals(releaseTxResult, 2,
				"Release Order id " + orderId + "Not available in SPS Settlement detail table");

		SellerPaymentDP.createCODOrderWithMultyQtyDiffReleasesAndReturnBoth(orderId, store_order_id,
				paymentPlanItemIdref, ppsId, ppsIdCancellation, paymentPlanItemIdref1);
		sellerHelper.rabbitMqRefundTx(returnMessage, ppsIdCancellation);

		log.debug("Wait for 10Sec more to get order details in settlement detail table....");
		Thread.sleep(10000);

		int releaseTxResult1 = sellerHelper.getSettlementdetailDBStatusCount(orderId, "REFUNDED");
		Assert.assertEquals(releaseTxResult1, 1,
				"Refund Order id " + orderId + "Not available in SPS Settlement detail table");
		Assert.assertEquals(sellerHelper.getSettlementDetailRefundAmount(orderId, "CASH_ON_DELIVERY"), "-500.00",
				"COD Refund amount:");

		sellerHelper.deleteOrderRecord(orderId, ppsId, paymentPlanItemId, ppsIdCancellation, paymentPlanItemIdref);

	}

	@Test(groups = { "Regression",
			"Sanity" }, priority = 0, dataProvider = "SPS_CodVectorQty1Exchange", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1. Create online order with Vector qty1 sku1. \n2.check the split in citrus than in SPS.\n3. Mark DL(By creating enrties in DB) and check for release.\n4. Mark exchange check for new order being created"
					+ "\n5. check the refund for the original sku")
	public void SPS_CodVectorQty1Exchange(String orderId, String store_order_id, String OrderReleaseId, String ppsId,
			String paymentPlanItemId, String orderMessage, String releaseMessage, String orderIdex,
			String store_order_id_ex, String OrderReleaseIdex, String lineIdex, String ppsIdex,
			String paymentPlanItemIdex, String paymentPlanItemIdex1, String ppsIdexChild,
			String parentOrderRefundMessage)
			throws SQLException, IOException, InterruptedException, NumberFormatException, JAXBException {
		log.debug("-----------------orderId: " + orderId + ", ppsId: " + ppsId + ", PaymentPlanId: " + paymentPlanItemId
				+ ", orderIdex: " + orderIdex + ", ppsIdex: " + ppsIdex + ", lineIdex:" + lineIdex);
		log.debug("Child PPSID: " + ppsIdexChild);
		log.debug("Wait for 10 sec to get the lineId....");
		sellerHelper.rabbitMqAddTx(orderMessage, orderId);
		sellerHelper.updateOrderReleaseStatusAndPackedOn(OrderReleaseId, "PK");
		sellerHelper.updateOrderLineStatus(orderId, "QD");
		sellerHelper.rabbitMqReleasePKTx(releaseMessage, orderId);
		log.debug("Wait for 10Sec more to get lineId....");
		Thread.sleep(10000);
		int refundSettxResult = sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED");
		Assert.assertEquals(refundSettxResult, 1,
				"Release Order_id:" + orderId + " Not available in SPS Settlement detail table");

		Integer lineId = sellerHelper.getLineId(orderId);
		log.debug("lineId: " + lineId);

		SellerPaymentDP.SPS_CodVectorQty1ExchangeHelper(orderId, store_order_id, OrderReleaseId,
				Integer.toString(lineId), ppsId, orderIdex, store_order_id_ex, OrderReleaseIdex, lineIdex, ppsIdex,
				paymentPlanItemIdex, paymentPlanItemIdex1, ppsIdexChild);

		String exchangeMessage = sellerHelper.exchangeMessageCreation(Long.parseLong(orderIdex), store_order_id_ex,
				799.0, 799.0, Long.parseLong(OrderReleaseIdex), Long.parseLong(OrderReleaseId), "WP",
				"WORK_IN_PROGRESS", Long.parseLong(lineIdex), lineId.longValue(), 799.0, 3832, 1, 19, 3832, "A",
				"Added", 1531, "ON_HAND", 799.0, ppsIdex, PaymentMethod.cod.name());
		String exchangeReleaseMessage = sellerHelper.exchangeMessageCreation(Long.parseLong(orderIdex),
				store_order_id_ex, 799.0, 799.0, Long.parseLong(OrderReleaseIdex), Long.parseLong(OrderReleaseId), "PK",
				"Packed", Long.parseLong(lineIdex), lineId.longValue(), 799.0, 3832, 1, 19, 3832, "QD", "QA Done", 1531,
				"ON_HAND", 799.0, ppsIdex, PaymentMethod.cod.name());

		sellerHelper.rabbitMqAddTx(exchangeMessage, orderIdex);

		sellerHelper.updateOrderReleaseStatusAndPackedOn(OrderReleaseIdex, "PK");
		sellerHelper.updateOrderLineStatus(orderIdex, "QD");

		sellerHelper.rabbitMqReleasePKTx(exchangeReleaseMessage, orderIdex);
		sellerHelper.rabbitMqRefundTx(parentOrderRefundMessage, ppsIdex);
		log.debug("Wait for 10Sec more to get lineId....");
		Thread.sleep(10000);
		int refundSettxResult2 = sellerHelper.getSettlementdetailDBStatusCount(orderIdex, "RELEASED");
		Assert.assertEquals(refundSettxResult2, 1,
				"Release Order_idex:" + orderIdex + " Not available in SPS Settlement detail table");

		int refundSettxResult3 = sellerHelper.getSettlementdetailDBStatusCount(orderId, "REFUNDED");
		Assert.assertEquals(refundSettxResult3, 1,
				"REFUND Order_id:" + orderId + " Not available in SPS Settlement detail table");

		sellerHelper.deleteOrderRecord(orderIdex, ppsIdexChild, paymentPlanItemIdex1);
		sellerHelper.deleteOrderRecord(orderId, ppsId, paymentPlanItemId, ppsIdex, paymentPlanItemIdex);

	}

	@Test(groups = { "Regression",
			"Sanity" }, priority = 0, dataProvider = "SPS_CodVectorQty1Exchange", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1. Create online order with Vector qty1 sku1. \n2.check the split in citrus than in SPS.\n3. Mark DL(By creating enrties in DB) and check for release.\n4. Mark exchange check for new order being created"
					+ "\n5. check the refund for the original sku")
	public void SPS_Cod_WalletQty1Exchange(String orderId, String store_order_id, String OrderReleaseId, String ppsId,
			String paymentPlanItemId, String orderMessage, String releaseMessage, String orderIdex,
			String store_order_id_ex, String OrderReleaseIdex, String lineIdex, String ppsIdex,
			String paymentPlanItemIdex, String paymentPlanItemIdex1, String ppsIdexChild,
			String parentOrderRefundMessage)
			throws SQLException, IOException, InterruptedException, NumberFormatException, JAXBException {
		log.debug("-----------------orderId: " + orderId + ", ppsId: " + ppsId + ", PaymentPlanId: " + paymentPlanItemId
				+ ", orderIdex: " + orderIdex + ", ppsIdex: " + ppsIdex + ", lineIdex:" + lineIdex);
		log.debug("Child PPSID: " + ppsIdexChild);
		log.debug("Wait for 10 sec to get the lineId....");
		sellerHelper.rabbitMqAddTx(orderMessage, orderId);
		sellerHelper.updateOrderReleaseStatusAndPackedOn(OrderReleaseId, "PK");
		sellerHelper.updateOrderLineStatus(orderId, "QD");
		sellerHelper.rabbitMqReleasePKTx(releaseMessage, orderId);
		log.debug("Wait for 10Sec more to get lineId....");
		Thread.sleep(10000);
		int refundSettxResult = sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED");
		Assert.assertEquals(refundSettxResult, 1,
				"Release Order_id:" + orderId + " Not available in SPS Settlement detail table");

		Integer lineId = sellerHelper.getLineId(orderId);
		log.debug("lineId: " + lineId);

		SellerPaymentDP.SPS_CodVectorQty1ExchangeHelper(orderId, store_order_id, OrderReleaseId,
				Integer.toString(lineId), ppsId, orderIdex, store_order_id_ex, OrderReleaseIdex, lineIdex, ppsIdex,
				paymentPlanItemIdex, paymentPlanItemIdex1, ppsIdexChild);

		String exchangeMessage = sellerHelper.exchangeMessageCreation(Long.parseLong(orderIdex), store_order_id_ex,
				799.0, 799.0, Long.parseLong(OrderReleaseIdex), Long.parseLong(OrderReleaseId), "WP",
				"WORK_IN_PROGRESS", Long.parseLong(lineIdex), lineId.longValue(), 799.0, 3832, 1, 19, 3832, "A",
				"Added", 1531, "ON_HAND", 799.0, ppsIdex, PaymentMethod.cod.name());
		String exchangeReleaseMessage = sellerHelper.exchangeMessageCreation(Long.parseLong(orderIdex),
				store_order_id_ex, 799.0, 799.0, Long.parseLong(OrderReleaseIdex), Long.parseLong(OrderReleaseId), "PK",
				"Packed", Long.parseLong(lineIdex), lineId.longValue(), 799.0, 3832, 1, 19, 3832, "QD", "QA Done", 1531,
				"ON_HAND", 799.0, ppsIdex, PaymentMethod.cod.name());

		sellerHelper.rabbitMqAddTx(exchangeMessage, orderIdex);

		sellerHelper.updateOrderReleaseStatusAndPackedOn(OrderReleaseIdex, "PK");
		sellerHelper.updateOrderLineStatus(orderIdex, "QD");

		sellerHelper.rabbitMqReleasePKTx(exchangeReleaseMessage, orderIdex);
		sellerHelper.rabbitMqRefundTx(parentOrderRefundMessage, ppsIdex);
		log.debug("Wait for 10Sec more to get lineId....");
		Thread.sleep(10000);
		int refundSettxResult2 = sellerHelper.getSettlementdetailDBStatusCount(orderIdex, "RELEASED");
		Assert.assertEquals(refundSettxResult2, 1,
				"Release Order_idex:" + orderIdex + " Not available in SPS Settlement detail table");

		int refundSettxResult3 = sellerHelper.getSettlementdetailDBStatusCount(orderId, "REFUNDED");
		Assert.assertEquals(refundSettxResult3, 1,
				"REFUND Order_id:" + orderId + " Not available in SPS Settlement detail table");

		sellerHelper.deleteOrderRecord(orderIdex, ppsIdexChild, paymentPlanItemIdex1);
		sellerHelper.deleteOrderRecord(orderId, ppsId, paymentPlanItemId, ppsIdex, paymentPlanItemIdex);

	}

	@Test(groups = { "Regression",
			"Smoke" }, priority = 0, dataProvider = "SPS_Cod_Funds_Release_ExchangeOrder_Cancel1_And_Packed1_Cancel1", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1. Create online order with seller qty1 sku1. \n2. check the split in citrus than in SPS")
	public void SPS_Cod_Funds_Release_ExchangeOrder_Cancel1_And_Packed1_Cancel1(String orderId, String OrderReleaseId,
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

		SellerPaymentDP.SPS_Cod_Funds_Release_ExchangeOrderCreationHelper(orderId, store_order_id,
				OrderReleaseId, lineId, ppsId, orderIdex, store_order_id_ex, OrderReleaseIdex, lineIdex, ppsIdex,
				paymentPlanItemIdex0, paymentPlanItemIdex, ppsIdexChild);

		String exchangeMessage = sellerHelper.exchangeMessageCreation(Long.parseLong(orderIdex), store_order_id_ex,
				2299.0, 2299.0, Long.parseLong(OrderReleaseIdex), Long.parseLong(OrderReleaseId), "WP",
				"WORK_IN_PROGRESS", Long.parseLong(lineIdex), Long.valueOf(lineId), 2299.0, 1251882, 1, 5, 1251882, "A",
				"Added", 373908, "ON_HAND", 2299.0, ppsIdex, PaymentMethod.cod.name());

		sellerHelper.rabbitMqAddTx(exchangeMessage, orderIdex);

		SellerPaymentDP.SPS_Cod_Funds_Release_ExchangeOrderCancellation_Helper(orderIdex, store_order_id_ex,
				OrderReleaseIdex, lineIdex, ppsIdexChild, ppsIdexCancellation, paymentPlanItemIdrefex,
				paymentPlanExecutionStatus_Idrefex);
		sellerHelper.rabbitMqRefundTx(exchangeOrderCancellationMessage, ppsIdexCancellation);

		SellerPaymentDP.SPS_Cod_Funds_Release_ExchangeOrderCreationHelper1(orderId, store_order_id,
				OrderReleaseId1, lineId1, ppsId, orderIdex1, store_order_id_ex1, OrderReleaseIdex1, lineIdex1, ppsIdex1,
				paymentPlanItemIdex1, paymentPlanItemIdex2, ppsIdexChild1);
		String exchangeMessage1 = sellerHelper.exchangeMessageCreation(Long.parseLong(orderIdex1), store_order_id_ex1,
				799.0, 799.0, Long.parseLong(OrderReleaseIdex1), Long.parseLong(OrderReleaseId1), "WP",
				"WORK_IN_PROGRESS", Long.parseLong(lineIdex1), Long.valueOf(lineId1), 799.0, 1251843, 1, 5, 1251843,
				"A", "Added", 373900, "ON_HAND", 799.0, ppsIdex1, PaymentMethod.cod.name());

		sellerHelper.rabbitMqAddTx(exchangeMessage1, orderIdex1);
		Thread.sleep(10000);

		String releaseExchangeMessage = sellerHelper.exchangeMessageCreation(Long.parseLong(orderIdex1),
				store_order_id_ex1, 799.0, 799.0, Long.parseLong(OrderReleaseIdex1), Long.parseLong(OrderReleaseId1),
				"PK", "Packed", Long.parseLong(lineIdex1), Long.valueOf(lineId1), 799.0, 1251843, 1, 5, 1251843, "QD",
				"QA Done", 373900, "ON_HAND", 799.0, ppsIdex1, PaymentMethod.cod.name());

		sellerHelper.updateOrderReleaseStatusAndPackedOn(OrderReleaseIdex1, "PK");
		sellerHelper.rabbitMqReleasePKTx(releaseExchangeMessage, orderIdex1);
		sellerHelper.rabbitMqRefundTx(parentOrderRefundMessage, ppsIdex1);
		Thread.sleep(10000);
		int releaseSettlResult3 = sellerHelper.getSettlementdetailDBStatusCount(orderIdex1, "RELEASED");
		Assert.assertEquals(releaseSettlResult3, 1,
				"Exchange Release Order Id: " + orderIdex1 + " Not available in SPS Settlement detail table");
		int refundSettlResult = sellerHelper.getSettlementdetailDBStatusCount(orderId, "REFUNDED");
		Assert.assertEquals(refundSettlResult, 1,
				"Exchange REFUND Order Id: " + orderId + " Not available in SPS Settlement detail table");

		SellerPaymentDP.SPS_Cod_Funds_Release_ExchangeOrderCancellation_Helper1(orderIdex1, store_order_id_ex1,
				OrderReleaseIdex1, lineIdex1, ppsIdex1, ppsIdexCancellation1, paymentPlanItemIdrefex1,
				paymentPlanExecutionStatus_idex1);
		sellerHelper.rabbitMqRefundTx(exchangeOrderCancellationMessage1, ppsIdex1);
		Thread.sleep(10000);
		int releaseSettlResult4 = sellerHelper.getSettlementdetailDBStatusCount(orderIdex1, "REFUNDED");
		Assert.assertEquals(releaseSettlResult4, 1, "Exchange Release Cancellation Failed REFUND Order Id: "
				+ orderIdex1 + " Not available in SPS Settlement detail table");

		sellerHelper.deleteOrderRecord(orderId, ppsId, paymentPlanItemId);

	}

	@Test(groups = { "Regression",
			"Smoke" }, priority = 0, dataProvider = "SPS_Cod_Funds_Release_ExchangeOrder_Packed_And_RTO", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1. Create online order with seller qty1 sku1. \n2. check the split in citrus than in SPS")
	public void SPS_Cod_Funds_Release_ExchangeOrder_Packed_And_RTO(String orderId, String OrderReleaseId,
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

		SellerPaymentDP.SPS_Cod_Funds_Release_ExchangeOrderCreationHelper(orderId, store_order_id,
				OrderReleaseId, lineId, ppsId, orderIdex, store_order_id_ex, OrderReleaseIdex, lineIdex, ppsIdex,
				paymentPlanItemIdex0, paymentPlanItemIdex, ppsIdexChild);

		String exchangeMessage = sellerHelper.exchangeMessageCreation(Long.parseLong(orderIdex), store_order_id_ex,
				2299.0, 2299.0, Long.parseLong(OrderReleaseIdex), Long.parseLong(OrderReleaseId), "WP",
				"WORK_IN_PROGRESS", Long.parseLong(lineIdex), Long.valueOf(lineId), 2299.0, 1251882, 1, 5, 1251882, "A",
				"Added", 373908, "ON_HAND", 2299.0, ppsIdex, PaymentMethod.cod.name());

		sellerHelper.rabbitMqAddTx(exchangeMessage, orderIdex);

		String releaseExchangeMessage = sellerHelper.exchangeMessageCreation(Long.parseLong(orderIdex),
				store_order_id_ex, 2299.0, 2299.0, Long.parseLong(OrderReleaseIdex), Long.parseLong(OrderReleaseId),
				"PK", "Packed", Long.parseLong(lineIdex), Long.valueOf(lineId), 2299.0, 1251882, 1, 5, 1251882, "QD",
				"QA Done", 373908, "ON_HAND", 2299.0, ppsIdex, PaymentMethod.cod.name());

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

		SellerPaymentDP.SPS_Cod_Funds_Release_ExchangeOrder_Packed_And_RTO_Helper(orderIdex, store_order_id_ex,
				OrderReleaseIdex, lineIdex, ppsIdexChild, ppsIdexCancellation, paymentPlanItemIdrefex,
				paymentPlanExecutionStatus_Idrefex);

		sellerHelper.rabbitMqRefundTx(exchangeOrderRTOMessage, ppsIdexChild);
		Thread.sleep(10000);
		int releaseSettlResult4 = sellerHelper.getSettlementdetailDBStatusCount(orderIdex, "REFUNDED");
		Assert.assertEquals(releaseSettlResult4, 1,
				"Exchange REFUND Order Id: " + orderIdex + " Not available in SPS Settlement detail table");

		sellerHelper.deleteOrderRecord(orderId, ppsId, paymentPlanItemId);

	}

	@Test(groups = { "Regression",
			"Smoke" }, priority = 0, dataProvider = "SPS_Cod_Funds_Release_ExchangeOrder_Packed_And_RTO", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1. Create online order with seller qty1 sku1. \n2. check the split in citrus than in SPS")
	public void SPS_Cod_Funds_Release_ExchangeOrder_Packed_And_Lost(String orderId, String OrderReleaseId,
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

		SellerPaymentDP.SPS_Cod_Funds_Release_ExchangeOrderCreationHelper(orderId, store_order_id,
				OrderReleaseId, lineId, ppsId, orderIdex, store_order_id_ex, OrderReleaseIdex, lineIdex, ppsIdex,
				paymentPlanItemIdex0, paymentPlanItemIdex, ppsIdexChild);

		String exchangeMessage = sellerHelper.exchangeMessageCreation(Long.parseLong(orderIdex), store_order_id_ex,
				2299.0, 2299.0, Long.parseLong(OrderReleaseIdex), Long.parseLong(OrderReleaseId), "WP",
				"WORK_IN_PROGRESS", Long.parseLong(lineIdex), Long.valueOf(lineId), 2299.0, 1251882, 1, 5, 1251882, "A",
				"Added", 373908, "ON_HAND", 2299.0, ppsIdex, PaymentMethod.cod.name());

		sellerHelper.rabbitMqAddTx(exchangeMessage, orderIdex);

		String releaseExchangeMessage = sellerHelper.exchangeMessageCreation(Long.parseLong(orderIdex),
				store_order_id_ex, 2299.0, 2299.0, Long.parseLong(OrderReleaseIdex), Long.parseLong(OrderReleaseId),
				"PK", "Packed", Long.parseLong(lineIdex), Long.valueOf(lineId), 2299.0, 1251882, 1, 5, 1251882, "QD",
				"QA Done", 373908, "ON_HAND", 2299.0, ppsIdex, PaymentMethod.cod.name());

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

		SellerPaymentDP.SPS_Cod_Funds_Release_ExchangeOrder_Packed_And_Lost_Helper(orderIdex, store_order_id_ex,
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
			"Smoke" }, priority = 0, dataProvider = "SPS_Cod_Funds_Release_ExchangeOfExchangePacked", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1. Create online order with seller qty1 sku1. \n2. check the split in citrus than in SPS")
	public void SPS_Cod_Funds_Release_ExchangeOfExchangePacked(String orderId, String OrderReleaseId,
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

		SellerPaymentDP.SPS_Cod_Funds_Release_ExchangeOrderCreationHelper(orderId, store_order_id,
				OrderReleaseId, lineId, ppsId, orderIdex, store_order_id_ex, OrderReleaseIdex, lineIdex, ppsIdex,
				paymentPlanItemIdex0, paymentPlanItemIdex, ppsIdexChild);

		String exchangeMessage = sellerHelper.exchangeMessageCreation(Long.parseLong(orderIdex), store_order_id_ex,
				2299.0, 2299.0, Long.parseLong(OrderReleaseIdex), Long.parseLong(OrderReleaseId), "WP",
				"WORK_IN_PROGRESS", Long.parseLong(lineIdex), Long.valueOf(lineId), 2299.0, 1251882, 1, 5, 1251882, "A",
				"Added", 373908, "ON_HAND", 2299.0, ppsIdex, PaymentMethod.cod.name());

		sellerHelper.rabbitMqAddTx(exchangeMessage, orderIdex);

		String releaseExchangeMessage = sellerHelper.exchangeMessageCreation(Long.parseLong(orderIdex),
				store_order_id_ex, 2299.0, 2299.0, Long.parseLong(OrderReleaseIdex), Long.parseLong(OrderReleaseId),
				"PK", "Packed", Long.parseLong(lineIdex), Long.valueOf(lineId), 2299.0, 1251882, 1, 5, 1251882, "QD",
				"QA Done", 373908, "ON_HAND", 2299.0, ppsIdex, PaymentMethod.cod.name());

		sellerHelper.rabbitMqReleasePKTx(releaseExchangeMessage, orderIdex);
		sellerHelper.rabbitMqRefundTx(parentOrderRefundMessage, ppsIdex);

		Thread.sleep(10000);

		int releaseSettlResult3 = sellerHelper.getSettlementdetailDBStatusCount(orderIdex, "RELEASED");
		Assert.assertEquals(releaseSettlResult3, 1,
				"Exchange Release Order Id: " + orderIdex + " Not available in SPS Settlement detail table");
		int refundSettlResult = sellerHelper.getSettlementdetailDBStatusCount(orderId, "REFUNDED");
		Assert.assertEquals(refundSettlResult, 1,
				"Exchange REFUND Order Id: " + orderId + " Not available in SPS Settlement detail table");

		SellerPaymentDP.SPS_Cod_Funds_Release_ExchangeofExchangeOrderCreationHelper(orderIdex, store_order_id_ex,
				OrderReleaseIdex, lineIdex, ppsIdex, orderIdex1, store_order_id_ex1, OrderReleaseIdex1, lineIdex1,
				ppsIdex1, paymentPlanItemIdex1, paymentPlanItemIdex2, ppsIdexChild1);

		String exchangeMessage2 = sellerHelper.exchangeMessageCreation(Long.parseLong(orderIdex1), store_order_id_ex1,
				2299.0, 2299.0, Long.parseLong(OrderReleaseIdex1), Long.parseLong(OrderReleaseIdex), "WP",
				"WORK_IN_PROGRESS", Long.parseLong(lineIdex1), Long.valueOf(lineIdex), 2299.0, 1251883, 1, 5, 1251883,
				"A", "Added", 373908, "ON_HAND", 2299.0, ppsIdex1, PaymentMethod.cod.name());

		sellerHelper.rabbitMqAddTx(exchangeMessage2, orderIdex1);

		String releaseExchangeMessage2 = sellerHelper.exchangeMessageCreation(Long.parseLong(orderIdex1),
				store_order_id_ex1, 2299.0, 2299.0, Long.parseLong(OrderReleaseIdex1), Long.parseLong(OrderReleaseIdex),
				"PK", "Packed", Long.parseLong(lineIdex1), Long.valueOf(lineIdex), 2299.0, 1251883, 1, 5, 1251883, "QD",
				"QA Done", 373908, "ON_HAND", 2299.0, ppsIdex1, PaymentMethod.cod.name());
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

	@Test(groups = { "Regression",
			"Smoke" }, priority = 0, dataProvider = "SPS_Cod_Funds_Release_ExchangeOfExchangePacked_Cancel", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1. Create online order with seller qty1 sku1. \n2. check the split in citrus than in SPS")
	public void SPS_Cod_Funds_Release_ExchangeOfExchangePacked_Cancel(String orderId, String OrderReleaseId,
			String OrderReleaseId1, String store_order_id, String orderMessage, String releaseMessage,
			String releaseMessage1, String ppsId, String paymentPlanItemId, String OrderReleaseIdex,
			String store_order_id_ex, String lineId, String lineId1, String orderIdex, String lineIdex, String ppsIdex,
			String paymentPlanItemIdex0, String paymentPlanItemIdex, String ppsIdexChild, String paymentPlanItemIdrefex,
			String paymentPlanExecutionStatus_Idrefex, String orderIdex1, String store_order_id_ex1,
			String OrderReleaseIdex1, String lineIdex1, String ppsIdex1, String paymentPlanItemIdex1,
			String paymentPlanItemIdex2, String ppsIdexChild1, String parentOrderRefundMessage,
			String exchangeOrderCancellationMessage, String ppsIdexCancel, String paymentPlanItemIdexCancel)
			throws SQLException, IOException, InterruptedException, Exception, Exception {
		log.debug("----------------" + orderId + " : " + ppsId);
		sellerHelper.rabbitMqAddTx(orderMessage, orderId);
		Thread.sleep(10000);

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

		SellerPaymentDP.SPS_Cod_Funds_Release_ExchangeOrderCreationHelper(orderId, store_order_id,
				OrderReleaseId, lineId, ppsId, orderIdex, store_order_id_ex, OrderReleaseIdex, lineIdex, ppsIdex,
				paymentPlanItemIdex0, paymentPlanItemIdex, ppsIdexChild);

		String exchangeMessage = sellerHelper.exchangeMessageCreation(Long.parseLong(orderIdex), store_order_id_ex,
				2299.0, 2299.0, Long.parseLong(OrderReleaseIdex), Long.parseLong(OrderReleaseId), "WP",
				"WORK_IN_PROGRESS", Long.parseLong(lineIdex), Long.valueOf(lineId), 2299.0, 1251882, 1, 5, 1251882, "A",
				"Added", 373908, "ON_HAND", 2299.0, ppsIdex, PaymentMethod.cod.name());

		sellerHelper.rabbitMqAddTx(exchangeMessage, orderIdex);

		String releaseExchangeMessage = sellerHelper.exchangeMessageCreation(Long.parseLong(orderIdex),
				store_order_id_ex, 2299.0, 2299.0, Long.parseLong(OrderReleaseIdex), Long.parseLong(OrderReleaseId),
				"PK", "Packed", Long.parseLong(lineIdex), Long.valueOf(lineId), 2299.0, 1251882, 1, 5, 1251882, "QD",
				"QA Done", 373908, "ON_HAND", 2299.0, ppsIdex, PaymentMethod.cod.name());

		sellerHelper.rabbitMqReleasePKTx(releaseExchangeMessage, orderIdex);
		sellerHelper.rabbitMqRefundTx(parentOrderRefundMessage, ppsIdex);

		Thread.sleep(10000);

		int releaseSettlResult3 = sellerHelper.getSettlementdetailDBStatusCount(orderIdex, "RELEASED");
		Assert.assertEquals(releaseSettlResult3, 1,
				"Exchange Release Order Id: " + orderIdex + " Not available in SPS Settlement detail table");
		int refundSettlResult = sellerHelper.getSettlementdetailDBStatusCount(orderId, "REFUNDED");
		Assert.assertEquals(refundSettlResult, 1,
				"Exchange REFUND Order Id: " + orderId + " Not available in SPS Settlement detail table");

		SellerPaymentDP.SPS_Cod_Funds_Release_ExchangeofExchangeOrderCreationHelper(orderIdex, store_order_id_ex,
				OrderReleaseIdex, lineIdex, ppsIdex, orderIdex1, store_order_id_ex1, OrderReleaseIdex1, lineIdex1,
				ppsIdex1, paymentPlanItemIdex1, paymentPlanItemIdex2, ppsIdexChild1);

		String exchangeMessage2 = sellerHelper.exchangeMessageCreation(Long.parseLong(orderIdex1), store_order_id_ex1,
				2299.0, 2299.0, Long.parseLong(OrderReleaseIdex1), Long.parseLong(OrderReleaseIdex), "WP",
				"WORK_IN_PROGRESS", Long.parseLong(lineIdex1), Long.valueOf(lineIdex), 2299.0, 1251883, 1, 5, 1251883,
				"A", "Added", 373908, "ON_HAND", 2299.0, ppsIdex1, PaymentMethod.cod.name());

		sellerHelper.rabbitMqAddTx(exchangeMessage2, orderIdex1);

		String releaseExchangeMessage2 = sellerHelper.exchangeMessageCreation(Long.parseLong(orderIdex1),
				store_order_id_ex1, 2299.0, 2299.0, Long.parseLong(OrderReleaseIdex1), Long.parseLong(OrderReleaseIdex),
				"PK", "Packed", Long.parseLong(lineIdex1), Long.valueOf(lineIdex), 2299.0, 1251883, 1, 5, 1251883, "QD",
				"QA Done", 373908, "ON_HAND", 2299.0, ppsIdex1, PaymentMethod.cod.name());
		sellerHelper.rabbitMqReleasePKTx(releaseExchangeMessage2, orderIdex1);
		// sellerHelper.rabbitMqRefundTx(parentOrderRefundMessage1, ppsIdex1);
		Thread.sleep(10000);

		int releaseSettlResult4 = sellerHelper.getSettlementdetailDBStatusCount(orderIdex1, "RELEASED");
		Assert.assertEquals(releaseSettlResult4, 1,
				"Exchange Release Order Id: " + orderIdex1 + " Not available in SPS Settlement detail table");
		//
		SellerPaymentDP.SPS_Cod_Funds_Release_ExchangeOfExchangePacked_Cancel_Helper(orderIdex1,
				store_order_id_ex1, OrderReleaseIdex1, paymentPlanItemIdexCancel, ppsIdex1, ppsIdexCancel);
		sellerHelper.rabbitMqRefundTx(exchangeOrderCancellationMessage, ppsIdexCancel);
		Thread.sleep(10000);
		int refundSettlResult1 = sellerHelper.getSettlementdetailDBStatusCount(orderIdex1, "REFUNDED");
		Assert.assertEquals(refundSettlResult1, 1,
				"Exchange REFUND Order Id: " + orderIdex1 + " Not available in SPS Settlement detail table");

		sellerHelper.deleteOrderRecord(orderId, ppsId, paymentPlanItemId);

	}

	@Test(groups = { "Regression",
			"Smoke" }, priority = 0, dataProvider = "SPS_Cod_Funds_Release_ExchangeOfExchangePacked_Return", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1. Create online order with seller qty1 sku1. \n2. check the split in citrus than in SPS")
	public void SPS_Cod_Funds_Release_ExchangeOfExchangePacked_Return(String orderId, String OrderReleaseId,
			String OrderReleaseId1, String store_order_id, String orderMessage, String releaseMessage,
			String releaseMessage1, String ppsId, String paymentPlanItemId, String OrderReleaseIdex,
			String store_order_id_ex, String lineId, String lineId1, String orderIdex, String lineIdex, String ppsIdex,
			String paymentPlanItemIdex0, String paymentPlanItemIdex, String ppsIdexChild, String paymentPlanItemIdrefex,
			String paymentPlanExecutionStatus_Idrefex, String orderIdex1, String store_order_id_ex1,
			String OrderReleaseIdex1, String lineIdex1, String ppsIdex1, String paymentPlanItemIdex1,
			String paymentPlanItemIdex2, String ppsIdexChild1, String parentOrderRefundMessage,
			String parentOrderRefundMessage1, String ppsIdReturnExchange, String paymentPlanItemIdreturn,
			String exchangeOfExchangeReturnMessage)
			throws SQLException, IOException, InterruptedException, Exception, Exception {
		log.debug("----------------" + orderId + " : " + ppsId);
		sellerHelper.rabbitMqAddTx(orderMessage, orderId);
		Thread.sleep(10000);

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

		SellerPaymentDP.SPS_Cod_Funds_Release_ExchangeOrderCreationHelper(orderId, store_order_id,
				OrderReleaseId, lineId, ppsId, orderIdex, store_order_id_ex, OrderReleaseIdex, lineIdex, ppsIdex,
				paymentPlanItemIdex0, paymentPlanItemIdex, ppsIdexChild);

		String exchangeMessage = sellerHelper.exchangeMessageCreation(Long.parseLong(orderIdex), store_order_id_ex,
				2299.0, 2299.0, Long.parseLong(OrderReleaseIdex), Long.parseLong(OrderReleaseId), "WP",
				"WORK_IN_PROGRESS", Long.parseLong(lineIdex), Long.valueOf(lineId), 2299.0, 1251882, 1, 5, 1251882, "A",
				"Added", 373908, "ON_HAND", 2299.0, ppsIdex, PaymentMethod.cod.name());

		sellerHelper.rabbitMqAddTx(exchangeMessage, orderIdex);

		String releaseExchangeMessage = sellerHelper.exchangeMessageCreation(Long.parseLong(orderIdex),
				store_order_id_ex, 2299.0, 2299.0, Long.parseLong(OrderReleaseIdex), Long.parseLong(OrderReleaseId),
				"PK", "Packed", Long.parseLong(lineIdex), Long.valueOf(lineId), 2299.0, 1251882, 1, 5, 1251882, "QD",
				"QA Done", 373908, "ON_HAND", 2299.0, ppsIdex, PaymentMethod.cod.name());

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

		SellerPaymentDP.SPS_Cod_Funds_Release_ExchangeofExchangeOrderCreationHelper(orderIdex, store_order_id_ex,
				OrderReleaseIdex, lineIdex, ppsIdex, orderIdex1, store_order_id_ex1, OrderReleaseIdex1, lineIdex1,
				ppsIdex1, paymentPlanItemIdex1, paymentPlanItemIdex2, ppsIdexChild1);

		String exchangeMessage2 = sellerHelper.exchangeMessageCreation(Long.parseLong(orderIdex1), store_order_id_ex1,
				2299.0, 2299.0, Long.parseLong(OrderReleaseIdex1), Long.parseLong(OrderReleaseIdex), "WP",
				"WORK_IN_PROGRESS", Long.parseLong(lineIdex1), Long.valueOf(lineIdex), 2299.0, 1251883, 1, 5, 1251883,
				"A", "Added", 373908, "ON_HAND", 2299.0, ppsIdex1, PaymentMethod.cod.name());

		sellerHelper.rabbitMqAddTx(exchangeMessage2, orderIdex1);

		String releaseExchangeMessage2 = sellerHelper.exchangeMessageCreation(Long.parseLong(orderIdex1),
				store_order_id_ex1, 2299.0, 2299.0, Long.parseLong(OrderReleaseIdex1), Long.parseLong(OrderReleaseIdex),
				"PK", "Packed", Long.parseLong(lineIdex1), Long.valueOf(lineIdex), 2299.0, 1251883, 1, 5, 1251883, "QD",
				"QA Done", 373908, "ON_HAND", 2299.0, ppsIdex1, PaymentMethod.cod.name());

		sellerHelper.updateOrderReleaseStatusAndPackedOn(OrderReleaseIdex1, "PK");
		sellerHelper.rabbitMqReleasePKTx(releaseExchangeMessage2, orderIdex1);
		sellerHelper.rabbitMqRefundTx(parentOrderRefundMessage1, ppsIdex1);
		Thread.sleep(10000);

		int releaseSettlResult4 = sellerHelper.getSettlementdetailDBStatusCount(orderIdex1, "RELEASED");
		Assert.assertEquals(releaseSettlResult4, 1,
				"Exchange Release Order Id: " + orderIdex1 + " Not available in SPS Settlement detail table");
		int refundSettlResult1 = sellerHelper.getSettlementdetailDBStatusCount(orderIdex, "REFUNDED");
		Assert.assertEquals(refundSettlResult1, 1,
				"Exchange REFUND Order Id: " + orderIdex + " Not available in SPS Settlement detail table");

		SellerPaymentDP.SPS_Cod_Funds_Release_ExchangeOfExchangePacked_Return_Helper(orderIdex1,
				store_order_id_ex1, OrderReleaseIdex1, paymentPlanItemIdreturn, ppsIdex1, ppsIdReturnExchange);
		sellerHelper.rabbitMqRefundTx(exchangeOfExchangeReturnMessage, ppsIdReturnExchange);
		Thread.sleep(10000);
		Assert.assertEquals(sellerHelper.getSettlementdetailDBStatusCount(orderIdex1, "REFUNDED"), 1,
				"Return of Exchange  Order Id: " + orderIdex1 + " Not available in SPS Settlement detail table");

		sellerHelper.deleteOrderRecord(orderId, ppsId, paymentPlanItemId);

	}

	@Test(groups = { "Regression",
			"Smoke" }, priority = 0, dataProvider = "SPS_Cod_Funds_Release_ExchangeOfExchangePacked_RTO", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1. Create online order with seller qty1 sku1. \n2. check the split in citrus than in SPS")
	public void SPS_Cod_Funds_Release_ExchangeOfExchangePacked_RTO(String orderId, String OrderReleaseId,
			String OrderReleaseId1, String store_order_id, String orderMessage, String releaseMessage,
			String releaseMessage1, String ppsId, String paymentPlanItemId, String OrderReleaseIdex,
			String store_order_id_ex, String lineId, String lineId1, String orderIdex, String lineIdex, String ppsIdex,
			String paymentPlanItemIdex0, String paymentPlanItemIdex, String ppsIdexChild, String paymentPlanItemIdrefex,
			String orderIdex1, String store_order_id_ex1, String OrderReleaseIdex1, String lineIdex1, String ppsIdex1,
			String paymentPlanItemIdex1, String paymentPlanItemIdex2, String ppsIdexChild1,
			String parentOrderRefundMessage, String parentOrderRefundMessage1, String ppsIdex1Cancellation,
			String paymentPlanItemIdrefex1, String paymentPlanExecutionStatus_idex1,
			String exchangeOfExchangeRTOMessage)
			throws SQLException, IOException, InterruptedException, Exception, Exception {
		log.debug("----------------" + orderId + " : " + ppsId);
		sellerHelper.rabbitMqAddTx(orderMessage, orderId);
		Thread.sleep(10000);

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

		SellerPaymentDP.SPS_Cod_Funds_Release_ExchangeOrderCreationHelper(orderId, store_order_id,
				OrderReleaseId, lineId, ppsId, orderIdex, store_order_id_ex, OrderReleaseIdex, lineIdex, ppsIdex,
				paymentPlanItemIdex0, paymentPlanItemIdex, ppsIdexChild);

		String exchangeMessage = sellerHelper.exchangeMessageCreation(Long.parseLong(orderIdex), store_order_id_ex,
				2299.0, 2299.0, Long.parseLong(OrderReleaseIdex), Long.parseLong(OrderReleaseId), "WP",
				"WORK_IN_PROGRESS", Long.parseLong(lineIdex), Long.valueOf(lineId), 2299.0, 1251882, 1, 5, 1251882, "A",
				"Added", 373908, "ON_HAND", 2299.0, ppsIdex, PaymentMethod.cod.name());

		sellerHelper.rabbitMqAddTx(exchangeMessage, orderIdex);

		String releaseExchangeMessage = sellerHelper.exchangeMessageCreation(Long.parseLong(orderIdex),
				store_order_id_ex, 2299.0, 2299.0, Long.parseLong(OrderReleaseIdex), Long.parseLong(OrderReleaseId),
				"PK", "Packed", Long.parseLong(lineIdex), Long.valueOf(lineId), 2299.0, 1251882, 1, 5, 1251882, "QD",
				"QA Done", 373908, "ON_HAND", 2299.0, ppsIdex, PaymentMethod.cod.name());

		sellerHelper.updateOrderReleaseStatusAndPackedOn(OrderReleaseIdex, "PK");
		sellerHelper.rabbitMqReleasePKTx(releaseExchangeMessage, orderIdex);
		sellerHelper.rabbitMqRefundTx(parentOrderRefundMessage, ppsIdex);

		Thread.sleep(10000);

		int releaseSettlResult3 = sellerHelper.getSettlementdetailDBStatusCount(orderIdex, "RELEASED");
		Assert.assertEquals(releaseSettlResult3, 1,
				"Exchange Release Order Id: " + orderIdex + " Not available in SPS Settlement detail table");
		int refundSettlResult = sellerHelper.getSettlementdetailDBStatusCount(orderId, "REFUNDED");
		Assert.assertEquals(refundSettlResult, 1,
				"Parent REFUND Failed Order Id: " + orderId + " Not available in SPS Settlement detail table");

		SellerPaymentDP.SPS_Cod_Funds_Release_ExchangeofExchangeOrderCreationHelper(orderIdex, store_order_id_ex,
				OrderReleaseIdex, lineIdex, ppsIdex, orderIdex1, store_order_id_ex1, OrderReleaseIdex1, lineIdex1,
				ppsIdex1, paymentPlanItemIdex1, paymentPlanItemIdex2, ppsIdexChild1);

		String exchangeMessage2 = sellerHelper.exchangeMessageCreation(Long.parseLong(orderIdex1), store_order_id_ex1,
				2299.0, 2299.0, Long.parseLong(OrderReleaseIdex1), Long.parseLong(OrderReleaseIdex), "WP",
				"WORK_IN_PROGRESS", Long.parseLong(lineIdex1), Long.valueOf(lineIdex), 2299.0, 1251883, 1, 5, 1251883,
				"A", "Added", 373908, "ON_HAND", 2299.0, ppsIdex1, PaymentMethod.cod.name());

		sellerHelper.rabbitMqAddTx(exchangeMessage2, orderIdex1);

		String releaseExchangeMessage2 = sellerHelper.exchangeMessageCreation(Long.parseLong(orderIdex1),
				store_order_id_ex1, 2299.0, 2299.0, Long.parseLong(OrderReleaseIdex1), Long.parseLong(OrderReleaseIdex),
				"PK", "Packed", Long.parseLong(lineIdex1), Long.valueOf(lineIdex), 2299.0, 1251883, 1, 5, 1251883, "QD",
				"QA Done", 373908, "ON_HAND", 2299.0, ppsIdex1, PaymentMethod.cod.name());

		sellerHelper.updateOrderReleaseStatusAndPackedOn(OrderReleaseIdex1, "PK");
		sellerHelper.rabbitMqReleasePKTx(releaseExchangeMessage2, orderIdex1);
		sellerHelper.rabbitMqRefundTx(parentOrderRefundMessage1, ppsIdex1);
		Thread.sleep(10000);

		int releaseSettlResult4 = sellerHelper.getSettlementdetailDBStatusCount(orderIdex1, "RELEASED");
		Assert.assertEquals(releaseSettlResult4, 1,
				"Exchange Release Order Id: " + orderIdex1 + " Not available in SPS Settlement detail table");
		int refundSettlResult1 = sellerHelper.getSettlementdetailDBStatusCount(orderIdex, "REFUNDED");
		Assert.assertEquals(refundSettlResult1, 1,
				"Exchange REFUND Order Id: " + orderIdex + " Not available in SPS Settlement detail table");

		SellerPaymentDP.SPS_Cod_Funds_Release_ExchangeOfExchangePacked_RTO_Helper(orderIdex1, store_order_id_ex1,
				OrderReleaseIdex1, lineIdex1, ppsIdex1, ppsIdex1Cancellation, paymentPlanItemIdrefex1,
				paymentPlanExecutionStatus_idex1);
		sellerHelper.rabbitMqRefundTx(exchangeOfExchangeRTOMessage, ppsIdex1Cancellation);
		Thread.sleep(10000);
		Assert.assertEquals(sellerHelper.getSettlementdetailDBStatusCount(orderIdex1, "REFUNDED"), 1,
				"Return of Exchange  Order Id: " + orderIdex1 + " Not available in SPS Settlement detail table");

		sellerHelper.deleteOrderRecord(orderId, ppsId, paymentPlanItemId);

	}

	// Create online order with JIT Multi Sku and Multi qty release all. Check
	// split for that sku in citrus and Db both
	@Test(groups = {
			"Regression" }, priority = 0, dataProvider = "SPS_Cod_MultiSku_MultiQty_SingleReleaseAndRefundAll", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1. Create online order with JIT MultiQty MultiSku1(Tatal 4qty). \n2.check the split for all the skus in citrus than in SPS.\n3.Release all check released."
					+ "\n4. Return 1 sku with qty1 and check the refund only for that sku")
	public void SPS_Cod_MultiSku_MultiQty_SingleReleaseAndRefundAll(String orderId, String OrderReleaseId,
			String store_order_id, String ppsId, String paymentPlanItemId, String paymentPlanItemId1, String message,
			String releaseFundMessage, String lineId, String ppsIdCancellation, String ppsIdCancellation1,
			String ppsIdCancellation2, String ppsIdCancellation3, String paymentPlanItemIdref,
			String paymentPlanItemIdref1, String paymentPlanItemIdref2, String paymentPlanItemIdref3,
			String returnMessage, String returnMessage1, String returnMessage2, String returnMessage3)
			throws SQLException, IOException, InterruptedException {
		log.debug(orderId + " : " + ppsId + " : " + paymentPlanItemId);
		sellerHelper.rabbitMqAddTx(message, orderId);

		sellerHelper.updateOrderReleaseStatusAndPackedOn(OrderReleaseId, "PK");
		sellerHelper.updateOrderLineStatus(orderId, "QD");
		sellerHelper.rabbitMqReleasePKTx(releaseFundMessage, orderId);
		log.debug("Wait for 10Sec more to get order details in settlement detail table....");
		Thread.sleep(10000);

		int releaseSettlResult = sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED");
		Assert.assertEquals(releaseSettlResult, 4,
				"Order Id: " + orderId + " Not available in SPS Settlement detail table");

		SellerPaymentDP.SPS_Cod_MultiSku_MultiQty_SingleReleaseAndRefundAllHelper(orderId, store_order_id,
				orderId, paymentPlanItemIdref, paymentPlanItemIdref1, paymentPlanItemIdref2, paymentPlanItemIdref3,
				ppsId, ppsIdCancellation, ppsIdCancellation1, ppsIdCancellation2, ppsIdCancellation3);
		sellerHelper.rabbitMqRefundTx(returnMessage, ppsIdCancellation);
		log.debug("Wait for 10Sec more to get order details in settlement detail table....");
		Thread.sleep(10000);

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

	@Test(groups = {
			"Regression" }, priority = 0, dataProvider = "SPS_COD_WalletAmountLessThan1_Qty1PK_Return", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1. Create COD+Wallet order with JIT quantity=1. \n2.Release the order. \3. Check the order details in SPS Settlement detail table. \4. Validate Fund release amount")
	public void SPS_COD_WalletAmountLessThan1_Qty1PK_Return(String orderId, String store_order_id, String ppsId,
			String paymentPlanItemId, String message, String lineId, String packedMessage, String releaseFundMessage,
			String OrderReleaseId, String paymentPlanItemIdref, String ppsIdCancellation, String returnMessage)
			throws SQLException, IOException, InterruptedException {
		log.debug(orderId + " : " + ppsId + " : " + paymentPlanItemId);
		sellerHelper.rabbitMqAddTx(message, orderId);

		sellerHelper.updateOrderReleaseStatusAndPackedOn(OrderReleaseId, "PK");
		sellerHelper.updateOrderLineStatus(orderId, "QD");
		sellerHelper.rabbitMqReleasePKTx(packedMessage, orderId);

		log.debug("Wait for 10Sec more to get order details in settlement detail table....");
		Thread.sleep(10000);

		Assert.assertEquals(sellerHelper.getTxDBStatus1(orderId, "RELEASED"), 0,
				"Release Transaction db order id : " + orderId + " not found");

		Assert.assertEquals(sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED"), 1,
				"Order id: " + orderId + "Not available in SPS Settlement detail table");

		SellerPaymentDP.createCod_Wallet_OrderWithSellerQty1ReleaseHelper(orderId, store_order_id,
				OrderReleaseId, paymentPlanItemIdref, ppsId, ppsIdCancellation);
		sellerHelper.rabbitMqRefundTx(returnMessage, ppsIdCancellation);

		log.debug("Wait for 10Sec more to get order details in settlement detail table....");
		Thread.sleep(10000);

		int releaseTxResult1 = sellerHelper.getSettlementdetailDBStatusCount(orderId, "REFUNDED");

		Assert.assertEquals(releaseTxResult1, 1,
				" Refund Order_id:" + orderId + " Not available in SPS Settlement detail table");

		Assert.assertEquals(sellerHelper.getSettlementDetailRefundAmount(orderId, "CASH_ON_DELIVERY"), "-2398.50",
				"COD Refund amount:");

		sellerHelper.deleteFromPaymentPlanItemInstrument(paymentPlanItemId);
		sellerHelper.deleteOrderRecord(orderId, ppsId, paymentPlanItemId);
	}

	@Test(groups = { "Regression",
			"Smoke" }, priority = 0, dataProvider = "SPS_COD_SellerQty2WithSkuPriceZero", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1. Create online order with seller qty1 sku1. \n2. check the split in citrus than in SPS")
	public void SPS_COD_SellerQty2WithSkuPriceZero(String orderId, String OrderReleaseId, String ppsId,
			String paymentPlanItemId, String message, String lineId, String releaseMessage)
			throws SQLException, IOException, InterruptedException {
		log.debug("----------------" + orderId + " : " + ppsId + " : " + paymentPlanItemId);
		sellerHelper.rabbitMqAddTx(message, orderId);
		sellerHelper.updateOrderReleaseStatusAndPackedOn(OrderReleaseId, "PK");
		sellerHelper.updateOrderLineStatus(orderId, "QD");
		sellerHelper.rabbitMqReleasePKTx(releaseMessage, orderId);
		sellerHelper.rabbitMqReleasePKTx(releaseMessage, orderId);

		log.debug("Wait for 10Sec more to get order details in settlement detail table....");
		Thread.sleep(10000);

		int releaseTxResult = sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED");
		Assert.assertEquals(releaseTxResult, 2,
				"Order id :" + orderId + " Not available in SPS Settlement detail table");

		sellerHelper.deleteOrderRecord(orderId, ppsId, paymentPlanItemId);

	}

	// Create online order with seller qty1 sku 1 and cancel. Check split and
	// refund for that sku in citrus and Db both
	@Test(groups = { "Regression",
			"Smoke" }, priority = 0, dataProvider = "SPS_COD_SellerMarginCheckForSellerId5", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1. Create online order with seller qty1 sku1. \n2. check the split in citrus than in SPS")
	public void SPS_COD_SKUInventoryCountZeroForMadhura(String orderId, String OrderReleaseId, String ppsId,
			String paymentPlanItemId, String message, String lineId, String releaseMessage)
			throws SQLException, IOException, InterruptedException {
		log.debug("----------------" + orderId + " : " + ppsId + " : " + paymentPlanItemId);
		sellerHelper.rabbitMqAddTx(message, orderId);

		sellerHelper.updateOrderReleaseStatusAndPackedOn(OrderReleaseId, "PK");
		sellerHelper.updateOrderLineStatus(orderId, "QD");
		sellerHelper.rabbitMqReleaseDLTx(releaseMessage, orderId);
		Thread.sleep(10000);

		int releaseSettlResult = sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED");
		Assert.assertEquals(releaseSettlResult, 1,
				"Release Transaction Order Id: " + orderId + " Not available in SPS Settlement detail table");

		// select `margin_percentage` from `payment_configuration` where
		// seller_id=5 and enabled=1;
		String sellerTermStatus = sellerHelper.getSellerTermsMarginStatus("5");

		String settlementDetailMargin = sellerHelper.getSettlementDetailMargin(orderId);
		if (sellerTermStatus.contains("true")) {
			Assert.assertEquals(settlementDetailMargin, "1.33", "Seller Terms Margin Percentage : ");
		} else {
			Assert.assertEquals(settlementDetailMargin, "46.93", "Seller Terms Margin Percentage : ");
		}

		sellerHelper.deleteOrderRecord(orderId, ppsId, paymentPlanItemId);

	}

	// Create online order with seller qty1 sku 1 and cancel. Check split and
	// refund for that sku in citrus and Db both
	@Test(groups = { "Regression",
			"Smoke" }, priority = 0, dataProvider = "SPS_COD_SellerMarginCheckForSellerId5", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1. Create online order with seller qty1 sku1. \n2. check the split in citrus than in SPS")
	public void SPS_COD_SellerMarginCheckForSellerId5(String orderId, String OrderReleaseId, String ppsId,
			String paymentPlanItemId, String message, String lineId, String releaseMessage)
			throws SQLException, IOException, InterruptedException {
		log.debug("----------------" + orderId + " : " + ppsId + " : " + paymentPlanItemId);
		sellerHelper.rabbitMqAddTx(message, orderId);

		sellerHelper.updateOrderReleaseStatusAndPackedOn(OrderReleaseId, "PK");
		sellerHelper.updateOrderLineStatus(orderId, "QD");
		sellerHelper.rabbitMqReleasePKTx(releaseMessage, orderId);
		Thread.sleep(10000);

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

	@Test(groups = {
			"Regression" }, priority = 0, dataProvider = "SPS_COD_Wallet_Qty1VatAdjustment", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1. Create COD+Wallet order with JIT quantity=1. \n2.Release the order. \3. Check the order details in SPS Settlement detail table. \4. Validate Fund release amount")
	public void SPS_COD_Wallet_Qty1VatAdjustment(String orderId, String ppsId, String paymentPlanItemId, String message,
			String lineId, String packedMessage, String OrderReleaseId, String paymentPlanItemIdref,
			String ppsIdCancellation, String returnMessage, String refundMessageVatAdjustment,
			String ppsIdrefundVatRefund) throws SQLException, IOException, InterruptedException {
		log.debug(orderId + " : " + ppsId + " : " + paymentPlanItemId);
		sellerHelper.rabbitMqAddTx(message, orderId);

		sellerHelper.updateOrderReleaseStatusAndPackedOn(OrderReleaseId, "PK");
		sellerHelper.updateOrderLineStatus(orderId, "QD");
		sellerHelper.rabbitMqRefundTx(refundMessageVatAdjustment, ppsIdrefundVatRefund);
		sellerHelper.rabbitMqReleasePKTx(packedMessage, orderId);
		Thread.sleep(10000);
		Assert.assertEquals(sellerHelper.getTxDBStatus1(orderId, "RELEASED"), 1,
				"Release Transaction db order id : " + orderId + " not found");

		int releaseTxResult = sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED");
		String fund_release_amount = sellerHelper
				.getSettlementDetailFundReleaseAmountByItemRef(orderId + "_" + OrderReleaseId + "_3831_0", "RELEASED");
		Assert.assertEquals(releaseTxResult, 2,
				"Release Order id: " + orderId + " Not available in SPS Settlement detail table");
		Assert.assertEquals(fund_release_amount, "1299.00", "Release Order id: " + orderId + " Release Fund amount : ");

		sellerHelper.deleteFromPaymentPlanItemInstrument(paymentPlanItemId);
		sellerHelper.deleteOrderRecord(orderId, ppsId, paymentPlanItemId);
	}

	// Create online order with seller qty1 sku 1 and cancel. Check split and
	// refund for that sku in citrus and Db both
	@Test(groups = { "Regression",
			"Smoke" }, priority = 0, dataProvider = "SPS_SellerQty1CodWalletAmount", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1. Create online order with seller qty1 sku1. \n2. check the split in citrus than in SPS")
	public void SPS_SellerQty1CodWalletAmount(String orderId, String store_order_id, String ppsId,
			String paymentPlanItemId, String message, String lineId)
			throws SQLException, IOException, InterruptedException {
		log.debug("----------------" + orderId + " : " + ppsId + " : " + paymentPlanItemId);
		sellerHelper.rabbitMqAddTx(message, orderId);
		Thread.sleep(1000);
		// int splits = sellerHelper.getNoOfSplits(store_order_id);
		// log.debug("Number of Splits: " + splits);
		int txResult = sellerHelper.getSplitTxDBCount(orderId);
		int pgResult = sellerHelper.getSplitPgDBCount(orderId);
		log.debug("DB check Tx table splits count: " + txResult + " pg table: " + pgResult);
		// AssertJUnit.assertEquals(1, splits);
		AssertJUnit.assertEquals(1, txResult);
		AssertJUnit.assertEquals(1, pgResult);
		Assert.assertEquals(sellerHelper.getTxAmount(orderId), "100.00");
		sellerHelper.deleteOrderRecord(orderId, ppsId, paymentPlanItemId);

	}

	// Create online order with seller qty1 sku 1 and cancel. Check split and
	// refund for that sku in citrus and Db both
	@Test(groups = { "Regression",
			"Smoke" }, priority = 0, dataProvider = "SPS_SellerQty1CodWalletAmountLessThan1", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1. Create online order with seller qty1 sku1. \n2. check the split in citrus than in SPS")
	public void SPS_SellerQty1CodWalletAmountLessThan1(String orderId, String OrderReleaseId, String store_order_id,
			String ppsId, String paymentPlanItemId, String message, String lineId, String packedMessage,
			String ppsIdRefund, String paymentPlanItemIdref1, String returnMessage)
			throws SQLException, IOException, InterruptedException {
		log.debug("----------------" + orderId + " : " + ppsId + " : " + paymentPlanItemId);
		sellerHelper.rabbitMqAddTx(message, orderId);

		int txResult = sellerHelper.getSplitTxDBCount(orderId);
		int pgResult = sellerHelper.getSplitPgDBCount(orderId);
		log.debug("DB check Tx table splits count: " + txResult + " pg table: " + pgResult);

		AssertJUnit.assertEquals(0, txResult);
		AssertJUnit.assertEquals(0, pgResult);

		sellerHelper.updateOrderReleaseStatusAndPackedOn(OrderReleaseId, "PK");
		sellerHelper.updateOrderLineStatus(orderId, "QD");
		sellerHelper.rabbitMqReleasePKTx(packedMessage, orderId);
		Thread.sleep(10000);
		Assert.assertEquals(sellerHelper.getTxDBStatus1(orderId, "RELEASED"), 0,
				"Release Transaction db order id : " + orderId + " not found");

		int releaseSettlResult = sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED");
		Assert.assertEquals(releaseSettlResult, 1,
				"RELEASE Order Id: " + orderId + " Not available in SPS Settlement detail table");

		SellerPaymentDP.SPS_SellerQty1CodWalletAmountLessThan1ReturnHelper(orderId, store_order_id,
				paymentPlanItemIdref1, ppsId, ppsIdRefund);
		sellerHelper.rabbitMqRefundTx(returnMessage, ppsIdRefund);
		Thread.sleep(10000);
		int refundSettlResult = sellerHelper.getSettlementdetailDBStatusCount(orderId, "REFUNDED");
		Assert.assertEquals(refundSettlResult, 1,
				"REFUND Order Id: " + orderId + " Not available in SPS Settlement detail table");

		sellerHelper.deleteOrderRecord(orderId, ppsId, paymentPlanItemId);

	}

	@Test(groups = { "Regression",
			"Smoke" }, priority = 0, dataProvider = "SPS_SellerQty2CodWalletAmountLessThan1AndGreaterThan1ReleaseReturnBoth", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1. Create online order with seller qty1 sku1. \n2. check the split in citrus than in SPS")
	public void SPS_SellerQty2CodWalletAmountLessThan1AndGreaterThan1ReleaseReturnBoth(String orderId,
			String OrderReleaseId, String OrderReleaseId1, String store_order_id, String ppsId,
			String paymentPlanItemId, String message, String lineId, String packedMessage, String packedMessage1,
			String ppsIdRefund, String paymentPlanItemIdref, String returnMessage, String ppsIdRefund1,
			String paymentPlanItemIdref1, String returnMessage1)
			throws SQLException, IOException, InterruptedException {
		log.debug("----------------" + orderId + " : " + ppsId + " : " + paymentPlanItemId);
		sellerHelper.rabbitMqAddTx(message, orderId);
		Thread.sleep(10000);
		int txResult = sellerHelper.getSplitTxDBCount(orderId);
		int pgResult = sellerHelper.getSplitPgDBCount(orderId);
		log.debug("DB check Tx table splits count: " + txResult + " pg table: " + pgResult);

		Assert.assertEquals(txResult, 1,
				"Add Transaction orderId " + orderId + " Not found in Transaction details table");
		Assert.assertEquals(pgResult, 1, "Add Transaction orderId " + orderId + " Not found in PG details table");

		sellerHelper.updateOrderReleaseStatusAndPackedOn(OrderReleaseId1, "PK");
		sellerHelper.rabbitMqReleasePKTx(packedMessage, orderId);
		Thread.sleep(10000);
		Assert.assertEquals(sellerHelper.getTxDBStatus1(orderId, "ADDED"), 1,
				"Release Transaction db order id : " + orderId + " not found");

		int releaseSettlResult = sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED");
		Assert.assertEquals(releaseSettlResult, 1,
				"RELEASE Order Id: " + orderId + " Not available in SPS Settlement detail table");

		sellerHelper.updateOrderReleaseStatusAndPackedOn(OrderReleaseId1, "PK");
		sellerHelper.rabbitMqReleasePKTx(packedMessage1, orderId);
		Thread.sleep(10000);
		Assert.assertEquals(sellerHelper.getTxDBStatus1(orderId, "RELEASED"), 1,
				"Release Transaction db order id : " + orderId + " not found");

		Assert.assertEquals(sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED"), 3,
				"RELEASE Order Id: " + orderId + " Not available in SPS Settlement detail table");

		SellerPaymentDP.SPS_SellerQty2CodWalletAmountLessThan1AndGreaterThan1ReleaseReturnBothHelper(orderId,
				store_order_id, paymentPlanItemIdref, ppsId, ppsIdRefund);
		sellerHelper.rabbitMqRefundTx(returnMessage, ppsIdRefund);
		Thread.sleep(10000);
		int refundSettlResult = sellerHelper.getSettlementdetailDBStatusCount(orderId, "REFUNDED");
		Assert.assertEquals(refundSettlResult, 2,
				"REFUND Order Id: " + orderId + " Not available in SPS Settlement detail table");

		SellerPaymentDP.SPS_SellerQty2CodWalletAmountLessThan1AndGreaterThan1ReleaseReturnBothHelper1(orderId,
				store_order_id, paymentPlanItemIdref1, ppsId, ppsIdRefund1);
		sellerHelper.rabbitMqRefundTx(returnMessage1, ppsIdRefund1);
		Thread.sleep(10000);
		Assert.assertEquals(sellerHelper.getSettlementdetailDBStatusCount(orderId, "REFUNDED"), 3,
				"REFUND Order Id: " + orderId + " Not available in SPS Settlement detail table");

		sellerHelper.deleteOrderRecord(orderId, ppsId, paymentPlanItemId);

	}

	@Test(groups = { "Regression",
			"Smoke" }, priority = 0, dataProvider = "SPS_SellerQty2CodWalletAmountLessThan1AndGreaterThan1ReleaseReturnBothToWallet", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1. Create online order with seller qty1 sku1. \n2. check the split in citrus than in SPS")
	public void SPS_SellerQty2CodWalletAmountLessThan1AndGreaterThan1ReleaseReturnBothToWallet(String orderId,
			String OrderReleaseId, String OrderReleaseId1, String store_order_id, String ppsId,
			String paymentPlanItemId, String message, String lineId, String packedMessage, String packedMessage1,
			String ppsIdRefund, String paymentPlanItemIdref, String returnMessage, String ppsIdRefund1,
			String paymentPlanItemIdref1, String returnMessage1)
			throws SQLException, IOException, InterruptedException {
		log.debug("----------------" + orderId + " : " + ppsId + " : " + paymentPlanItemId);
		sellerHelper.rabbitMqAddTx(message, orderId);
		Thread.sleep(10000);
		int txResult = sellerHelper.getSplitTxDBCount(orderId);
		int pgResult = sellerHelper.getSplitPgDBCount(orderId);
		log.debug("DB check Tx table splits count: " + txResult + " pg table: " + pgResult);

		Assert.assertEquals(txResult, 1,
				"Add Transaction orderId " + orderId + " Not found in Transaction details table");
		Assert.assertEquals(pgResult, 1, "Add Transaction orderId " + orderId + " Not found in PG details table");

		sellerHelper.updateOrderReleaseStatusAndPackedOn(OrderReleaseId1, "PK");
		sellerHelper.rabbitMqReleasePKTx(packedMessage, orderId);
		Thread.sleep(10000);
		Assert.assertEquals(sellerHelper.getTxDBStatus1(orderId, "ADDED"), 1,
				"Release Transaction db order id : " + orderId + " not found");

		int releaseSettlResult = sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED");
		Assert.assertEquals(releaseSettlResult, 1,
				"RELEASE Order Id: " + orderId + " Not available in SPS Settlement detail table");

		sellerHelper.updateOrderReleaseStatusAndPackedOn(OrderReleaseId, "PK");
		sellerHelper.rabbitMqReleasePKTx(packedMessage1, orderId);
		Thread.sleep(10000);
		Assert.assertEquals(sellerHelper.getTxDBStatus1(orderId, "RELEASED"), 1,
				"Release Transaction db order id : " + orderId + " not found");

		Assert.assertEquals(sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED"), 3,
				"RELEASE Order Id: " + orderId + " Not available in SPS Settlement detail table");

		SellerPaymentDP.SPS_SellerQty2CodWalletAmountLessThan1AndGreaterThan1ReleaseReturnBothToWalletHelper(
				orderId, store_order_id, paymentPlanItemIdref, ppsId, ppsIdRefund);
		sellerHelper.rabbitMqRefundTx(returnMessage, ppsIdRefund);
		Thread.sleep(10000);
		int refundSettlResult = sellerHelper.getSettlementdetailDBStatusCount(orderId, "REFUNDED");
		Assert.assertEquals(refundSettlResult, 2,
				"REFUND Order Id: " + orderId + " Not available in SPS Settlement detail table");

		SellerPaymentDP.SPS_SellerQty2CodWalletAmountLessThan1AndGreaterThan1ReleaseReturnBothToWalletHelper1(
				orderId, OrderReleaseId1, store_order_id, paymentPlanItemIdref1, ppsId, ppsIdRefund1);
		sellerHelper.rabbitMqRefundTx(returnMessage1, ppsIdRefund1);
		Thread.sleep(10000);
		Assert.assertEquals(sellerHelper.getSettlementdetailDBStatusCount(orderId, "REFUNDED"), 3,
				"REFUND Order Id: " + orderId + " Not available in SPS Settlement detail table");

		sellerHelper.deleteOrderRecord(orderId, ppsId, paymentPlanItemId);

	}

	@Test(groups = { "Regression",
			"Smoke" }, priority = 0, dataProvider = "SPS_SellerQty2_Release_1_CodWalletAmountLessThan1AndGreaterThan1ReleaseReturnBothToOrigin", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1. Create online order with seller qty1 sku1. \n2. check the split in citrus than in SPS")
	public void SPS_SellerQty2_Release_1_CodWalletAmountLessThan1AndGreaterThan1ReleaseReturnBothToOrigin(
			String orderId, String OrderReleaseId, String store_order_id, String ppsId, String paymentPlanItemId,
			String message, String lineId, String packedMessage, String ppsIdRefund, String paymentPlanItemIdref,
			String returnMessage, String ppsIdRefund1, String paymentPlanItemIdref1, String returnMessage1)
			throws SQLException, IOException, InterruptedException {
		log.debug("----------------" + orderId + " : " + ppsId + " : " + paymentPlanItemId);
		sellerHelper.rabbitMqAddTx(message, orderId);
		Thread.sleep(10000);
		int txResult = sellerHelper.getSplitTxDBCount(orderId);
		int pgResult = sellerHelper.getSplitPgDBCount(orderId);
		log.debug("DB check Tx table splits count: " + txResult + " pg table: " + pgResult);

		Assert.assertEquals(txResult, 1,
				"Add Transaction orderId " + orderId + " Not found in Transaction details table");
		Assert.assertEquals(pgResult, 1, "Add Transaction orderId " + orderId + " Not found in PG details table");

		sellerHelper.updateOrderReleaseStatusAndPackedOn(OrderReleaseId, "PK");
		sellerHelper.rabbitMqReleasePKTx(packedMessage, orderId);
		Thread.sleep(10000);
		Assert.assertEquals(sellerHelper.getTxDBStatus1(orderId, "RELEASED"), 1,
				"Release Transaction db order id : " + orderId + " not found");

		int releaseSettlResult = sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED");
		Assert.assertEquals(releaseSettlResult, 3,
				"RELEASE Order Id: " + orderId + " Not available in SPS Settlement detail table");

		SellerPaymentDP
				.SPS_SellerQty2_Release_1_CodWalletAmountLessThan1AndGreaterThan1ReleaseReturnBothToOriginHelper(
						orderId, store_order_id, paymentPlanItemIdref, ppsId, ppsIdRefund);
		sellerHelper.rabbitMqRefundTx(returnMessage, ppsIdRefund);
		Thread.sleep(10000);
		int refundSettlResult = sellerHelper.getSettlementdetailDBStatusCount(orderId, "REFUNDED");
		Assert.assertEquals(refundSettlResult, 2,
				"REFUND Order Id: " + orderId + " Not available in SPS Settlement detail table");

		SellerPaymentDP
				.SPS_SellerQty2_Release_1_CodWalletAmountLessThan1AndGreaterThan1ReleaseReturnBothToOriginHelper1(
						orderId, OrderReleaseId, store_order_id, paymentPlanItemIdref1, ppsId, ppsIdRefund1);
		sellerHelper.rabbitMqRefundTx(returnMessage1, ppsIdRefund1);
		Thread.sleep(10000);
		Assert.assertEquals(sellerHelper.getSettlementdetailDBStatusCount(orderId, "REFUNDED"), 3,
				"REFUND Order Id: " + orderId + " Not available in SPS Settlement detail table");

		sellerHelper.deleteOrderRecord(orderId, ppsId, paymentPlanItemId);

	}

	@Test(groups = { "Regression",
			"Smoke" }, priority = 0, dataProvider = "SPS_SellerQty2CodWalletAmountLessThan1AndGreaterThan1_PK_Cancel_Both", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1. Create online order with seller qty1 sku1. \n2. check the split in citrus than in SPS")
	public void SPS_SellerQty2CodWalletAmountLessThan1AndGreaterThan1_PK_Cancel_Both(String orderId,
			String OrderReleaseId, String OrderReleaseId1, String store_order_id, String ppsId,
			String paymentPlanItemId, String orderMessage, String lineId, String packedMessage, String packedMessage1,
			String ppsIdCancel, String paymentPlanItemIdref, String cancellationMessage,
			String paymentPlanExecutionStatus_id, String ppsIdCancel1, String paymentPlanItemIdref1,
			String cancellationMessage1, String paymentPlanExecutionStatus_id1) throws Exception {
		log.debug("----------------" + orderId + " : " + ppsId + " : " + paymentPlanItemId);
		sellerHelper.rabbitMqAddTx(orderMessage, orderId);
		Thread.sleep(10000);
		int txResult = sellerHelper.getSplitTxDBCount(orderId);
		int pgResult = sellerHelper.getSplitPgDBCount(orderId);
		log.debug("DB check Tx table splits count: " + txResult + " pg table: " + pgResult);

		Assert.assertEquals(txResult, 1,
				"Add Transaction orderId " + orderId + " Not found in Transaction details table");
		Assert.assertEquals(pgResult, 1, "Add Transaction orderId " + orderId + " Not found in PG details table");

		sellerHelper.updateOrderReleaseStatusAndPackedOn(OrderReleaseId, "PK");
		sellerHelper.rabbitMqReleasePKTx(packedMessage, orderId);
		Thread.sleep(10000);
		Assert.assertEquals(sellerHelper.getTxDBStatus1(orderId, "ADDED"), 1,
				"Release Transaction db order id : " + orderId + " not found");
		Assert.assertEquals(sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED"), 1,
				"Release Settlement db order id : " + orderId + " not found");

		SellerPaymentDP.SPS_SellerQty2CodWalletAmountLessThan1AndGreaterThan1_PK_Cancel_Both_Helper(orderId,
				OrderReleaseId, store_order_id, ppsId, ppsIdCancel, paymentPlanItemIdref,
				paymentPlanExecutionStatus_id);
		sellerHelper.rabbitMqRefundTx(cancellationMessage, ppsIdCancel);
		Thread.sleep(10000);

		Assert.assertEquals(sellerHelper.getTxDBStatus1(orderId, "REFUNDED"), 0,
				"REFUND Transaction orderId " + orderId + " Not found in Transaction details table");
		Assert.assertEquals(sellerHelper.getSettlementdetailDBStatusCount(orderId, "REFUNDED"), 1,
				"REFUND Order Id: " + orderId + " Not available in SPS Settlement detail table");

		sellerHelper.updateOrderReleaseStatusAndPackedOn(OrderReleaseId1, "PK");
		sellerHelper.rabbitMqReleasePKTx(packedMessage1, orderId);
		Thread.sleep(10000);
		Assert.assertEquals(sellerHelper.getTxDBStatus1(orderId, "RELEASED"), 1,
				"Release Transaction db order id : " + orderId + " not found");
		Assert.assertEquals(sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED"), 3,
				"Release Settlement db order id : " + orderId + " not found");

		SellerPaymentDP.SPS_SellerQty2CodWalletAmountLessThan1AndGreaterThan1_PK_Cancel_Both_Helper1(orderId,
				OrderReleaseId1, store_order_id, ppsId, ppsIdCancel1, paymentPlanItemIdref1,
				paymentPlanExecutionStatus_id1);
		sellerHelper.rabbitMqRefundTx(cancellationMessage1, ppsIdCancel1);
		Thread.sleep(10000);

		Assert.assertEquals(sellerHelper.getTxDBStatus1(orderId, "REFUNDED"), 1,
				"REFUND Transaction orderId " + orderId + " Not found in Transaction details table");
		Assert.assertEquals(sellerHelper.getSettlementdetailDBStatusCount(orderId, "REFUNDED"), 3,
				"REFUND Order Id: " + orderId + " Not available in SPS Settlement detail table");

		sellerHelper.deleteOrderRecord(orderId, ppsId, paymentPlanItemId);

	}

	@Test(groups = { "Regression",
			"Smoke" }, priority = 0, dataProvider = "SPS_CodQty1WithSKUZeroShippingChrgsPKOrderCancel", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1. Create online order with seller qty1 sku1. \n2. check the split in citrus than in SPS")
	public void SPS_CodQty1WithSKUZeroShippingChrgsPKOrderCancel(String orderId, String OrderReleaseId,
			String store_order_id, String packedMessage, String ppsId, String ppsIdCancellation,
			String paymentPlanItemId, String paymentPlanItemIdref, String paymentPlanItemIdshipCancel,
			String paymentPlanExecutionStatus_id, String orderMessage, String cancellationMessage, String lineId)
			throws Exception {
		log.debug("----------------" + orderId + " : " + ppsId + " : " + paymentPlanItemId);
		sellerHelper.rabbitMqAddTx(orderMessage, orderId);

		sellerHelper.updateOrderReleaseStatusAndPackedOn(OrderReleaseId, "PK");
		sellerHelper.updateOrderLineStatusByLineId(lineId, "QD");
		sellerHelper.rabbitMqReleasePKTx(packedMessage, orderId);
		Thread.sleep(10000);
		// Assert.assertEquals(sellerHelper.getTxDBStatus1(orderId, "RELEASED"),
		// 0,
		// "Release Transaction db order id : " + orderId + " not found");

		int releaseSettlResult = sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED");
		Assert.assertEquals(releaseSettlResult, 2,
				"RELEASE Order Id: " + orderId + " Not available in SPS Settlement detail table");

		SellerPaymentDP.SPS_CodQty1WithSKUZeroShippingChrgsPKOrderCancel_Helper(orderId, ppsId, OrderReleaseId,
				store_order_id, ppsIdCancellation, paymentPlanItemIdref, paymentPlanItemIdshipCancel,
				paymentPlanExecutionStatus_id);
		sellerHelper.rabbitMqRefundTx(cancellationMessage, ppsIdCancellation);
		Thread.sleep(10000);
		int refundSettlResult = sellerHelper.getSettlementdetailDBStatusCount(orderId, "REFUNDED");
		Assert.assertEquals(refundSettlResult, 2,
				"REFUND Order Id: " + orderId + " Not available in SPS Settlement detail table");

		sellerHelper.deleteOrderRecord(orderId, ppsId, paymentPlanItemId);

	}

	@Test(groups = { "Regression",
			"Smoke" }, priority = 0, dataProvider = "SPS_SellerQty2_Release_1_CodWalletAmountLessThan1WithVATAdjustmentAndGreaterThan1ReleaseReturn", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1. Create online order with seller qty1 sku1. \n2. check the split in citrus than in SPS")
	public void SPS_SellerQty2_Release_1_CodWalletAmountLessThan1WithVATAdjustmentAndGreaterThan1ReleaseReturn(
			String orderId, String OrderReleaseId, String store_order_id, String ppsId, String paymentPlanItemId,
			String message, String lineId, String packedMessage, String releaseMessage, String ppsIdRefund,
			String paymentPlanItemIdref, String returnMessage) throws SQLException, IOException, InterruptedException {
		log.debug("----------------" + orderId + " : " + ppsId + " : " + paymentPlanItemId);
		sellerHelper.rabbitMqAddTx(message, orderId);
		Thread.sleep(10000);
		int txResult = sellerHelper.getSplitTxDBCount(orderId);
		int pgResult = sellerHelper.getSplitPgDBCount(orderId);
		log.debug("DB check Tx table splits count: " + txResult + " pg table: " + pgResult);

		Assert.assertEquals(txResult, 1,
				"Add Transaction orderId " + orderId + " Not found in Transaction details table");
		Assert.assertEquals(pgResult, 1, "Add Transaction orderId " + orderId + " Not found in PG details table");

		sellerHelper.updateOrderReleaseStatusAndPackedOn(OrderReleaseId, "PK");
		sellerHelper.rabbitMqReleasePKTx(packedMessage, orderId);
		Thread.sleep(10000);
		Assert.assertEquals(sellerHelper.getTxDBStatus1(orderId, "RELEASED"), 1,
				"Release Transaction db order id : " + orderId + " not found");

		int releaseSettlResult = sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED");
		Assert.assertEquals(releaseSettlResult, 3,
				"RELEASE Order Id: " + orderId + " Not available in SPS Settlement detail table");
		Assert.assertEquals(
				sellerHelper.getSettlementDetailFundReleaseAmountByItemRef(orderId + "_" + OrderReleaseId + "_3827_0",
						"RELEASED"),
				"0.85", "RELEASE AMOUNT Order Id: " + orderId + " Not MATCHED in SPS Settlement detail table");

		SellerPaymentDP
				.SPS_SellerQty2_Release_1_CodWalletAmountLessThan1WithVATAdjustmentAndGreaterThan1ReleaseReturnHelper(
						orderId, OrderReleaseId, store_order_id, paymentPlanItemIdref, ppsId, ppsIdRefund);
		sellerHelper.rabbitMqRefundTx(returnMessage, ppsIdRefund);
		Thread.sleep(10000);
		int refundSettlResult = sellerHelper.getSettlementdetailDBStatusCount(orderId, "REFUNDED");
		Assert.assertEquals(refundSettlResult, 1,
				"REFUND Order Id: " + orderId + " Not available in SPS Settlement detail table");

		sellerHelper.deleteOrderRecord(orderId, ppsId, paymentPlanItemId);

	}

	@Test(groups = { "Regression",
			"Sanity" }, priority = 0, dataProvider = "SPS_CodQty2_VatAdjustment_Cancel_1_Release_1", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1.Create online order with seller qty1 sku1. \n2.check the split in citrus than in SPS. \n3.Release the sku and check release in both(On order Packed). "
					+ "\n4.Return the sku and check the refund in both")
	public void SPS_CodQty2_VatAdjustment_Cancel_1_Release_1(String orderId, String store_order_id,
			String OrderReleaseId, String ppsId, String paymentPlanItemId, String paymentPlanItemId1, String lineId,
			String lineId1, String orderMessage, String releaseMessage, String ppsIdRefund, String paymentPlanItemIdref,
			String paymentPlanExecutionStatus_id, String cancellationMessage, String vatRefundMessage,
			String vatRefundMessage1, String ppsIdVatrefund, String ppsIdVatrefund1)
			throws SQLException, IOException, InterruptedException {
		log.debug(orderId + " : " + ppsId + " : " + paymentPlanItemId);
		sellerHelper.rabbitMqAddTx(orderMessage, orderId);
		sellerHelper.rabbitMqRefundTx(vatRefundMessage, ppsIdVatrefund);
		sellerHelper.rabbitMqRefundTx(vatRefundMessage1, ppsIdVatrefund1);

		SellerPaymentDP.SPS_CodQty2_VatAdjustment_Cancel_1_Release_1_Helper(orderId, store_order_id,
				OrderReleaseId, lineId1, ppsId, ppsIdRefund, paymentPlanItemIdref, paymentPlanExecutionStatus_id);
		sellerHelper.rabbitMqRefundTx(cancellationMessage, ppsIdRefund);

		sellerHelper.updateOrderReleaseStatusAndPackedOn(OrderReleaseId, "PK");
		sellerHelper.updateOrderLineStatusByLineId(lineId, "QD");
		sellerHelper.rabbitMqReleasePKTx(releaseMessage, orderId);
		log.debug("Wait for 10Sec more to get order details in settlement detail table....");
		Thread.sleep(10000);

		int releaseTxResult = sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED");
		Assert.assertEquals(releaseTxResult, 1,
				"Order id :" + orderId + "Not available in SPS Settlement detail table");
		Assert.assertEquals(sellerHelper.getSettlementDetailFundReleaseAmount(orderId), "900.00",
				"Fund Release Amount :");

		sellerHelper.deleteOrderRecord(orderId, ppsId, paymentPlanItemId, ppsIdRefund, paymentPlanItemIdref);
	}

	@Test(groups = { "Regression",
			"Smoke" }, priority = 0, dataProvider = "SPS_Cod_Release_2_PK_2_OrderCancellation", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1. Create online order with seller qty1 sku1. \n2. check the split in citrus than in SPS")
	public void SPS_Cod_Release_2_PK_2_OrderCancellation(String orderId, String OrderReleaseId, String OrderReleaseId1,
			String store_order_id, String orderMessage, String releaseMessage, String releaseMessage1, String ppsId,
			String paymentPlanItemId, String lineId, String lineId1, String orderCancellationRefundMessage,
			String ppsIdCancellation, String paymentPlanItemIdref, String paymentPlanItemIdref1,
			String deliveredMessage) throws SQLException, IOException, InterruptedException, Exception, Exception {
		log.debug("----------------" + orderId + " : " + ppsId);
		sellerHelper.rabbitMqAddTx(orderMessage, orderId);
		Thread.sleep(10000);

		sellerHelper.updateOrderReleaseStatusAndPackedOn(OrderReleaseId, "PK");
		sellerHelper.rabbitMqReleasePKTx(releaseMessage, orderId);

		sellerHelper.updateOrderReleaseDeliveredOn(OrderReleaseId);
		sellerHelper.rabbitMqReleaseDLTx(deliveredMessage, orderId);
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

		SellerPaymentDP.SPS_Cod_Release_2_PK_2_OrderCancellation_Helper(orderId, OrderReleaseId1, store_order_id,
				ppsId, ppsIdCancellation, paymentPlanItemIdref, paymentPlanItemIdref1);
		sellerHelper.rabbitMqRefundTx(orderCancellationRefundMessage, ppsIdCancellation);
		Thread.sleep(10000);
		Assert.assertEquals(sellerHelper.getSettlementdetailDBStatusCount(orderId, "REFUNDED"), 1,
				"Release Order Id: " + orderId + " Not available in SPS Settlement detail table");

		sellerHelper.deleteOrderRecord(orderId, ppsId, paymentPlanItemId);

	}

	@Test(groups = { "Regression",
			"Smoke" }, priority = 0, dataProvider = "SPS_Cod_Release_2_PK_2_OrderCancellation", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1. Create online order with seller qty1 sku1. \n2. check the split in citrus than in SPS")
	public void SPS_Cod_Release_2_PK_1_OrderCancellation(String orderId, String OrderReleaseId, String OrderReleaseId1,
			String store_order_id, String orderMessage, String releaseMessage, String releaseMessage1, String ppsId,
			String paymentPlanItemId, String lineId, String lineId1, String orderCancellationRefundMessage,
			String ppsIdCancellation, String paymentPlanItemIdref, String paymentPlanItemIdref1)
			throws SQLException, IOException, InterruptedException, Exception, Exception {
		log.debug("----------------" + orderId + " : " + ppsId);
		sellerHelper.rabbitMqAddTx(orderMessage, orderId);
		Thread.sleep(10000);

		sellerHelper.updateOrderReleaseStatusAndPackedOn(OrderReleaseId, "PK");
		sellerHelper.updateOrderLineStatusByLineId(lineId, "QD");
		sellerHelper.rabbitMqReleasePKTx(releaseMessage, orderId);
		Thread.sleep(10000);
		int releaseSettlResult = sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED");
		Assert.assertEquals(releaseSettlResult, 1,
				"Release Order Id: " + orderId + " Not available in SPS Settlement detail table");

		SellerPaymentDP.SPS_Cod_Release_2_PK_2_OrderCancellation_Helper(orderId, OrderReleaseId1, store_order_id,
				ppsId, ppsIdCancellation, paymentPlanItemIdref, paymentPlanItemIdref1);
		sellerHelper.rabbitMqRefundTx(orderCancellationRefundMessage, ppsIdCancellation);
		Thread.sleep(10000);
		Assert.assertEquals(sellerHelper.getSettlementdetailDBStatusCount(orderId, "REFUNDED"), 1,
				"Release Order Id: " + orderId + " Not available in SPS Settlement detail table");

		sellerHelper.deleteOrderRecord(orderId, ppsId, paymentPlanItemId);

	}

	@Test(groups = { "Regression",
			"Smoke" }, priority = 0, dataProvider = "SPS_Cod_Release_2_PK_1_OrderCancellationWithoutorderId", dataProviderClass = SellerPaymentDP.class, enabled = false, description = "1. Create online order with seller qty1 sku1. \n2. check the split in citrus than in SPS")
	public void SPS_Cod_Release_2_PK_1_OrderCancellationWithoutorderId(String orderId, String OrderReleaseId,
			String OrderReleaseId1, String store_order_id, String orderMessage, String releaseMessage,
			String releaseMessage1, String ppsId, String paymentPlanItemId, String lineId, String lineId1,
			String orderCancellationRefundMessage, String ppsIdCancellation, String paymentPlanItemIdref,
			String paymentPlanItemIdref1) throws SQLException, IOException, InterruptedException, Exception, Exception {
		log.debug("----------------" + orderId + " : " + ppsId);
		sellerHelper.rabbitMqAddTx(orderMessage, orderId);
		Thread.sleep(10000);

		sellerHelper.updateOrderReleaseStatusAndPackedOn(OrderReleaseId, "PK");
		sellerHelper.updateOrderLineStatusByLineId(lineId, "QD");
		sellerHelper.rabbitMqReleasePKTx(releaseMessage, orderId);
		Thread.sleep(10000);
		int releaseSettlResult = sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED");
		Assert.assertEquals(releaseSettlResult, 1,
				"Release Order Id: " + orderId + " Not available in SPS Settlement detail table");

		SellerPaymentDP.SPS_Cod_Release_2_PK_2_OrderCancellation_Helper(orderId, OrderReleaseId1, store_order_id,
				ppsId, ppsIdCancellation, paymentPlanItemIdref, paymentPlanItemIdref1);
		sellerHelper.rabbitMqRefundTx(orderCancellationRefundMessage, ppsIdCancellation);
		Thread.sleep(10000);
		Assert.assertEquals(sellerHelper.getSettlementdetailDBStatusCount(orderId, "REFUNDED"), 1,
				"Release Order Id: " + orderId + " Not available in SPS Settlement detail table");

		sellerHelper.deleteOrderRecord(orderId, ppsId, paymentPlanItemId);

	}

	@Test(groups = { "Regression",
			"Smoke" }, priority = 0, dataProvider = "SPS_Cod_Funds_Release_PackedLost_And_LostBeforePacked", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "1. Create online order with seller qty1 sku1. \n2. check the split in citrus than in SPS")
	public void SPS_Cod_Funds_Release_PackedLost_And_LostBeforePacked(String orderId, String OrderReleaseId,String OrderReleaseId1, String store_order_id, String ppsId, 
			String paymentPlanItemId, String orderMessage, String lineId, String lineId1,String lineId2, String releaseMessage,
			String releaseMessage1,String packedLostMessage,String ppsIdLostAfterPack,String paymentPlanItemIdLAP,String paymentPlanExecutionStatus_id,
			String paymentPlanItemIdLBP,String  paymentPlanItemIdLBP1,String ppsIdLostBeforePack,String LostBeforePackedMessage)
			throws SQLException, IOException, InterruptedException {
		log.debug("----------------" + orderId + " : " + ppsId + " : " + paymentPlanItemId);

		sellerHelper.rabbitMqAddTx(orderMessage, orderId);
		Thread.sleep(10000);
		
		sellerHelper.updateOrderReleaseStatusAndPackedOn(OrderReleaseId1, "PK");
		sellerHelper.rabbitMqReleasePKTx(releaseMessage1, orderId);
		Thread.sleep(10000);
		int releaseSettlResult1 = sellerHelper.getSettlementdetailDBStatusCount(orderId, "RELEASED");
		Assert.assertEquals(releaseSettlResult1, 1,
				"Release Order Id: " + orderId + " Not available in SPS Settlement detail table");

		SellerPaymentDP.SPS_Cod_Funds_Release_PackedLost_Helper(orderId, store_order_id, OrderReleaseId1, lineId1, ppsId, ppsIdLostAfterPack, paymentPlanItemIdLAP, paymentPlanExecutionStatus_id);
		sellerHelper.rabbitMqRefundTx(packedLostMessage, ppsIdLostAfterPack);
		Thread.sleep(10000);
		int refundSettlResult = sellerHelper.getSettlementdetailDBStatusCount(orderId, "REFUNDED");
		Assert.assertEquals(refundSettlResult, 1,
				"REFUND Order Id: " + orderId + " Not available in SPS Settlement detail table");

		SellerPaymentDP.SPS_Cod_Funds_Release_LostBeforePacked_Helper(orderId, store_order_id, OrderReleaseId, paymentPlanItemIdLBP, paymentPlanItemIdLBP1, ppsId, ppsIdLostBeforePack);
		sellerHelper.rabbitMqRefundTx(LostBeforePackedMessage, ppsIdLostBeforePack);
//		Thread.sleep(10000);
//		int refundSettlResult1 = sellerHelper.getSettlementdetailDBStatusCount(orderId, "REFUNDED");
//		Assert.assertEquals(refundSettlResult1, 3,
//				"REFUND Order Id: " + orderId + " Not available in SPS Settlement detail table");
		sellerHelper.deleteOrderRecord(orderId, ppsId, paymentPlanItemId);

	}


	//
	// ---------------------------------------------------- SPS Simple API
//	Automation--------------------------------------------------------------- --------------------------------------

	// * Here onward we have Simple API's automated SPS like Seller creation,
	

	// API -- Get seller by ID

	@Test(groups={"Regression","Sanity"},priority=0,dataProvider="SPS_getSellerById",dataProviderClass=SellerPaymentDP.class,enabled=true,description="1. Get seller by Id")

	public void SPS_getSellerById(String sellerId, String expectedResult) {
			MyntraService service = Myntra.getService(ServiceType.ERP_SPS, APINAME.GETSELLERBYID, init.Configurations,
					new String[] {}, new String[] { sellerId, expectedResult }, com.myntra.lordoftherings.gandalf.PayloadType.JSON, PayloadType.JSON);
			
			
			
			HashMap getParam = new HashMap();
			getParam.put("Authorization", "Basic YXBpYWRtaW46bTFudHJhUjBja2V0MTMhIw==");
			log.debug(service.URL);
			RequestGenerator req = new RequestGenerator(service, getParam);
			String res = req.respvalidate.returnresponseasstring();
			AssertJUnit.assertEquals(200, req.response.getStatus());
			APIUtilities.validateResponse("json", res, expectedResult);
		}

	@Test(groups = { "Regression",
				"Sanity" }, priority = 0, dataProvider = "SPS_updateSeller", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "")
		public void SPS_updateSeller(String seller_id, String seller_name, String seller_add1, String seller_add2,
				String seller_mobile, String seller_city, String seller_state, String seller_country, String zip,
				String businessUrl, String selleremail, String seller_ifsc_code, String seller_acc_num, String payoutmode,
				String expectedResult) {
			MyntraService service = Myntra.getService(ServiceType.ERP_SPS, APINAME.UPDATESELLERSPS, init.Configurations,
					new String[] { seller_id, seller_name, seller_add1, seller_add2, seller_mobile, seller_city,
							seller_state, seller_country, zip, businessUrl, selleremail, seller_ifsc_code, seller_acc_num,
							payoutmode, expectedResult },
					PayloadType.JSON, PayloadType.JSON);
			HashMap getParam = new HashMap();
			getParam.put("Authorization", "Basic YXBpYWRtaW46bTFudHJhUjBja2V0MTMhIw==");
			log.debug(service.URL);
			RequestGenerator req = new RequestGenerator(service, getParam);
			String res = req.respvalidate.returnresponseasstring();
			AssertJUnit.assertEquals(200, req.response.getStatus());
			APIUtilities.validateResponse("json", res, expectedResult);
		}

	@Test(groups = { "Regression",
				"Sanity" }, priority = 0, dataProvider = "SPS_MarginCacheUpdate", dataProviderClass = SellerPaymentDP.class, enabled = true, description = "")
		public void SPS_MarginCacheUpdate(String sellerId, String brandName, String articleTypeName, String gender,
				String marginPercentage, String expectedResult) {
			MyntraService service = Myntra.getService(ServiceType.ERP_SPS, APINAME.MARGINCACHEUPDATE, init.Configurations,
					new String[] { sellerId, brandName, articleTypeName, gender, marginPercentage, expectedResult },
					PayloadType.XML, PayloadType.JSON);
			HashMap getParam = new HashMap();
			getParam.put("Authorization", "Basic YXBpYWRtaW46bTFudHJhUjBja2V0MTMhIw==");
			log.debug(service.URL);
			RequestGenerator req = new RequestGenerator(service, getParam);
			String res = req.respvalidate.returnresponseasstring();
			AssertJUnit.assertEquals(200, req.response.getStatus());
			APIUtilities.validateResponse("json", res, expectedResult);
		}

	

}
