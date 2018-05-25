package com.myntra.apiTests.erpservices.lms.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.myntra.apiTests.common.Constants.EnumSCM;
import com.myntra.apiTests.erpservices.lms.Constants.LMS_PINCODE;
import com.myntra.apiTests.erpservices.lms.Helper.LMSHelper;
import com.myntra.apiTests.erpservices.lms.Helper.LmsServiceHelper;
import com.myntra.apiTests.erpservices.lms.dp.LMSTestsDP;
import com.myntra.apiTests.erpservices.oms.OMSServiceHelper;
import com.myntra.lms.client.status.ShipmentType;
import com.myntra.lms.client.status.ShippingMethod;
import com.myntra.lordoftherings.boromir.DBUtilities;
import com.myntra.test.commons.testbase.BaseTest;

/**
 * Created by Shubham Gupta on 1/19/17.
 */
public class LMS_OrderInscan extends BaseTest {

	private LmsServiceHelper lmsServiceHelper = new LmsServiceHelper();
	private LMSHelper lmsHelper = new LMSHelper();
	private OMSServiceHelper omsServiceHelper = new OMSServiceHelper();

	@Test(groups = { "OrderInScan", "Smoke", "Regression", "P0s" }, priority = 5, dataProviderClass = LMSTestsDP.class, dataProvider = "ordersInScanNormal", description = "ID: C374 , ", enabled = true)
	public void ordersInScanNormal(String toStatus, String zipcode, String courierCode, String warehouseId,
			String inscanWhId, String shippingMethod, String paymentMode, boolean isTnB, String statusType)
			throws Exception {
		String orderId = lmsHelper.createMockOrder(toStatus, zipcode, courierCode, warehouseId, shippingMethod, paymentMode, isTnB, true);
		String releaseId = omsServiceHelper.getPacketId(orderId);
		Thread.sleep(3000);
		if (!courierCode.equals("ML"))
			DBUtilities
					.exUpdateQuery("update order_tracking set courier_creation_status = 'ACCEPTED' where order_id = '"
							+ releaseId + "'", "lms");
		Assert.assertEquals(lmsServiceHelper.orderInScanNew(releaseId, inscanWhId, true), statusType);
		if (statusType.equalsIgnoreCase(EnumSCM.SUCCESS))
			lmsHelper.inscanValidation(releaseId, lmsServiceHelper.getHubConfig(inscanWhId, "DL"));
	}

	@Test(groups = { "OrderInScan", "Smoke", "Regression",
			"P0s" }, priority = 5, description = "ID: C375 , ", enabled = true)
	public void ordersInScanPkNotInLMS() throws Exception {
		String orderId = lmsHelper.createMockOrder("WP", LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.NORMAL, "cod", false,
				true);
		String releaseId = omsServiceHelper.getPacketId(orderId);
		DBUtilities.exUpdateQuery("update order_release set status_code = 'PK' where order_id_fk = " + orderId, "oms");
		DBUtilities.exUpdateQuery("update order_line set status_code = 'QD' where order_id_fk = " + orderId, "oms");
		Assert.assertEquals(lmsServiceHelper.orderInScanNew(releaseId, "36"), EnumSCM.SUCCESS);
		lmsHelper.inscanValidation(releaseId, lmsServiceHelper.getHubConfig("36", "DL"));
	}

	@Test(groups = { "OrderInScan", "Smoke", "Regression",
			"P0s" }, priority = 5, dataProviderClass = LMSTestsDP.class, dataProvider = "ordersInScanWithDiffStatusOrders", description = "ID: C376 , ", enabled = true)
	public void ordersInScanWithDiffStatusOrders(String toSTatus, String courierCode, String status, String pincode,
			String statusType) throws Exception {
		String releaseId = omsServiceHelper.getPacketId(lmsHelper.createMockOrder(toSTatus, pincode, courierCode, "36", EnumSCM.NORMAL, "cod", false, true));
		Assert.assertEquals(lmsServiceHelper.orderInScanNew(releaseId), statusType);
		if (status.equals(EnumSCM.CANCELLED_IN_HUB)) {
			Assert.assertEquals(lmsHelper.getOrderToShipStatus(releaseId), EnumSCM.CANCELLED_IN_HUB);
		}
	}

	@Test(groups = { "OrderInScan", "Smoke", "Regression",
			"P0s" }, priority = 5, dataProviderClass = LMSTestsDP.class, dataProvider = "ordersInScanWIthDiffCourierCreationStatus", description = "ID: C377 , ", enabled = true)
	public void ordersInScanWIthDiffCourierCreationStatus(String zipcode, String courierCode, String courierCreationStatus, String statusType) throws Exception {
		
		String orderId = lmsHelper.createMockOrder(EnumSCM.PK, zipcode, courierCode, "36", EnumSCM.NORMAL, "cod", false, true);
		String releaseId = omsServiceHelper.getPacketId(orderId);
		Assert.assertEquals(lmsServiceHelper.orderInScanNew(releaseId, "36", courierCreationStatus), statusType);
		if (statusType.equalsIgnoreCase(EnumSCM.SUCCESS))
			lmsHelper.inscanValidation(releaseId, lmsServiceHelper.getHubConfig("36", "DL"));
	}

	@Test(groups = { "OrderInScan", "Smoke", "Regression",
			"P0s" }, priority = 5, dataProviderClass = LMSTestsDP.class, dataProvider = "ordersInScan3PLOffline", description = "ID: C378 , ", enabled = true)
	public void ordersInScan3PLOffline(String pincode, String orderStatus, String zipcode, String courierCode,
			String statusType, String statusTypeOnError) throws Exception {
		String orderId = lmsHelper.createMockOrder(orderStatus, zipcode, courierCode, "36", EnumSCM.NORMAL, "cod",
				false, true);
		String releaseId = omsServiceHelper.getPacketId(orderId);
		if (!pincode.equals("0")) {
			DBUtilities.exUpdateQuery(
					"update order_to_ship set zipcode = " + pincode + " where order_id = '" + releaseId + "'", "lms");
		}
		Assert.assertEquals(lmsServiceHelper.orderInScanNew(releaseId, "36", false), statusType);
		if (statusType.equalsIgnoreCase(EnumSCM.SUCCESS)) {
			lmsHelper.inscanValidation(releaseId, lmsServiceHelper.getHubConfig("36", "DL"));
			Assert.assertEquals(lmsServiceHelper.orderInScanNew(releaseId, "28", false), statusType);
			if (statusType.equalsIgnoreCase(EnumSCM.SUCCESS))
				lmsHelper.inscanValidation(releaseId, lmsServiceHelper.getHubConfig("28", "DL"));
		} else {
			Assert.assertEquals(lmsServiceHelper.orderInScanNew(releaseId, "36", true), statusTypeOnError);
		}
	}

	@Test(groups = { "OrderInScan", "Smoke", "Regression",
			"P0" }, priority = 5, description = "ID: C379 , ", dataProviderClass = LMSTestsDP.class, dataProvider = "ordersInScanWithDiffWHnProcess", enabled = true)
	public void ordersInScanWithDiffWHnProcess(String orderWHId, String inscanAt, String processInWH, String status)
			throws Exception {
		String orderId = lmsHelper.createMockOrder(EnumSCM.PK, LMS_PINCODE.ML_BLR, "ML", orderWHId, EnumSCM.NORMAL,
				"cod", false, true);
		String releaseId = omsServiceHelper.getPacketId(orderId);
		Assert.assertEquals(lmsServiceHelper.orderInScanNew(releaseId, orderWHId), EnumSCM.SUCCESS);
		lmsHelper.inscanValidation(releaseId, lmsServiceHelper.getHubConfig(orderWHId, "DL"));
		Assert.assertEquals(lmsServiceHelper.orderInScanNew(releaseId, inscanAt), EnumSCM.SUCCESS);
		lmsHelper.inscanValidation(releaseId, lmsServiceHelper.getHubConfig(inscanAt, "DL"));
		long masterBagId = lmsServiceHelper.createMasterBag(5L, processInWH, ShippingMethod.NORMAL).getEntries().get(0)
				.getId();
		Assert.assertEquals(
				lmsServiceHelper.addAndSaveMasterBag(releaseId, "" + masterBagId, ShipmentType.DL, false), status);
	}

	@Test(groups = { "OrderInScan", "Smoke", "Regression",
			"P0s" }, priority = 5, description = "ID: C380 , ", enabled = true)
	public void ordersInScanMLWithNotInitiated() throws Exception {
		String orderId = lmsHelper.createMockOrder(EnumSCM.PK, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.NORMAL, "cod",
				false, true);
		String releaseId = omsServiceHelper.getPacketId(orderId);
		lmsHelper.deleteOrderFromML(releaseId, lmsHelper.getTrackingNumber(releaseId));
		DBUtilities
				.exUpdateQuery("update order_tracking set courier_creation_status = 'NOT_INITIATED' where order_id = '"
						+ releaseId + "'", "lms");
		Assert.assertEquals(lmsServiceHelper.orderInScanNew(releaseId), EnumSCM.SUCCESS);
		Assert.assertEquals(lmsHelper.getMLShipmentStatus(releaseId), EnumSCM.EXPECTED_IN_DC);
	}

	@Test(groups = { "OrderInScan", "Smoke", "Regression",
			"P0s" }, priority = 5, description = "ID: C381 , ", enabled = true)
	public void ordersInScanWithDiffServiceability() throws Exception {
		String orderId = lmsHelper.createMockOrder(EnumSCM.PK, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.NORMAL, "cod",
				false, true);
		DBUtilities.exUpdateQuery("update order_to_ship set zipcode = 112233 where order_id = '" + orderId + "'",
				"lms");
		DBUtilities.exUpdateQuery(
				"update ml_shipment set pincode = 112233 where source_reference_id = '" + orderId + "'", "lms");
		String releaseId = omsServiceHelper.getPacketId(orderId);
		//Assert.assertEquals(lmsServiceHelper.orderInScanNew(releaseId, "36", false), EnumSCM.ERROR);
		Assert.assertEquals(lmsServiceHelper.orderInScanNew(releaseId, "36", true), EnumSCM.SUCCESS);
		Assert.assertEquals(lmsServiceHelper.orderInScanNew(releaseId, "36", true), EnumSCM.SUCCESS);
	}

	@Test(groups = { "OrderInScan", "Smoke", "Regression",
			"P0" }, priority = 5, description = "ID: C382 , ", enabled = true)
	public void ordersInScanWithDiffWarehouseOneHub() throws Exception {
		String orderId = lmsHelper.createMockOrder(EnumSCM.PK, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.NORMAL, "cod",
				false, true);
		String orderId1 = lmsHelper.createMockOrder(EnumSCM.PK, LMS_PINCODE.ML_BLR, "ML", "1", EnumSCM.NORMAL, "cod",
				false, true);
		Assert.assertEquals(lmsServiceHelper.orderInScanNew("" + omsServiceHelper.getPacketId(orderId)),
				EnumSCM.SUCCESS);
		Assert.assertEquals(lmsServiceHelper.orderInScanNew("" + omsServiceHelper.getPacketId(orderId1)),
				EnumSCM.SUCCESS);
		String hubCode = lmsServiceHelper.getHubConfig("36", "DL");
		lmsHelper.inscanValidation(omsServiceHelper.getPacketId(orderId), hubCode);
		lmsHelper.inscanValidation(omsServiceHelper.getPacketId(orderId1), hubCode);
	}

	@Test(groups = { "OrderInScan", "Smoke", "Regression",
			"P0" }, priority = 5, description = "ID: C514 , ", dataProviderClass = LMSTestsDP.class, dataProvider = "orderInScanGOR", enabled = true)
	public void orderInScanGOR(String pincode, String courier, String shippingMethod, String status) throws Exception {
		String orderId = lmsHelper.createMockOrder(EnumSCM.PK, pincode, courier, "36", shippingMethod, "cod", false,
				true);
		if (!courier.equals("ML")) {
			DBUtilities
					.exUpdateQuery("update order_tracking set courier_creation_status = 'ACCEPTED' where order_id = '"
							+ omsServiceHelper.getPacketId(orderId) + "'", "lms");
			Thread.sleep(2000);
		}
		Assert.assertEquals(lmsServiceHelper.orderInScanGOR(omsServiceHelper.getPacketId(orderId), "36").getStatus()
				.getStatusType().toString(), EnumSCM.SUCCESS);
		String hubCode = lmsServiceHelper.getHubConfig("36", "DL");
		lmsHelper.inscanValidation(omsServiceHelper.getPacketId(orderId), hubCode);
	}

}
