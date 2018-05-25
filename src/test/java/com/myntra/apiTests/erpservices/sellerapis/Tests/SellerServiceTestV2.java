package com.myntra.apiTests.erpservices.sellerapis.Tests;


import com.myntra.apiTests.erpservices.Constants;
import com.myntra.apiTests.erpservices.lms.Helper.LmsServiceHelper;
import com.myntra.apiTests.erpservices.oms.OMSServiceHelper;
import com.myntra.apiTests.end2end.End2EndHelper;
import com.myntra.apiTests.erpservices.sellerapis.dp.SellerServiceDP;
import com.myntra.apiTests.erpservices.wms.WMSConstants;
import com.myntra.apiTests.end2end.End2EndHelper;
import com.myntra.apiTests.erpservices.lms.Helper.LmsServiceHelper;
import com.myntra.apiTests.erpservices.oms.OMSServiceHelper;
import com.myntra.apiTests.erpservices.sellerapis.SellerApiHelper;
import com.myntra.apiTests.erpservices.sellerapis.dp.SellerServiceDP;
import com.myntra.commons.codes.StatusResponse;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.boromir.DBUtilities;
import com.myntra.oms.client.entry.OrderLineEntry;
import com.myntra.oms.client.entry.OrderReleaseEntry;
import com.myntra.sellerapi.service.client.response.InventoryResponse;
import com.myntra.sellerapi.service.client.response.OrderResponse;
import com.myntra.test.commons.testbase.BaseTest;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SellerServiceTestV2 extends BaseTest {
	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(SellerServiceTestV2.class);
	// String login = "aceturtle@myntra.com";
	String login = "madura@myntra.com";
	String password = "123456";

	SellerApiHelper sellerServiceHelper = new SellerApiHelper();
	End2EndHelper end2EndHelper = new End2EndHelper();
	OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
	private com.myntra.oms.client.entry.OrderEntry orderEntry;
	private String orderReleaseId;

	@Test(groups = {
			"Regression" }, priority = 0, description = "1.Update Inventory \n 2.Check inventory got added successfully")
	public void sellerApi_InventoryUpdate() throws SQLException, IOException, JAXBException {
		String[] invData = { "8903880486532:1000:4:5" };
		String sellerSkuCode = invData[0].split(":")[0];
		int inventoryCount = Integer.parseInt(invData[0].split(":")[1]);
		InventoryResponse inventoryResponse = sellerServiceHelper.updateInventoryV2(invData);
		Assert.assertEquals(inventoryResponse.getStatus().getStatusCode(), 1001);
		Assert.assertEquals(inventoryResponse.getStatus().getStatusMessage().toString(),
				"Inventory processed successfully");
		Assert.assertEquals(inventoryResponse.getStatus().getStatusType().toString(), "SUCCESS");
		sellerServiceHelper.verifyInventoryCount(sellerSkuCode, inventoryCount);
	}

	@Test(groups = {
			"Regression" }, priority = 1, description = "1.Search Inventory \n 2.Check Search Inventory is Working for the SkuCode")
	public void sellerApi_InventorySearch() throws SQLException, JAXBException, IOException {
		List<String> skuCodeList = new ArrayList<>();
		skuCodeList.add("\"8903880133610\"");
		InventoryResponse inventoryResponse = sellerServiceHelper.searchInventoryV2(skuCodeList);
		Assert.assertEquals(inventoryResponse.getStatus().getStatusCode(), 1002);
		Assert.assertEquals(inventoryResponse.getStatus().getStatusMessage().toString(),
				"Inventory retrieved successfully");
		Assert.assertEquals(inventoryResponse.getStatus().getStatusType().toString(), "SUCCESS");
	}

	@Test(groups = {
			"Regression" }, priority = 2, description = "1.Update Inventory with Invalid Inventory Count \n 2.Check inventory is not added successfully")
	public void sellerApi_InvalidInvCount() throws SQLException, JAXBException, IOException {
		String[] invData = { "8903880486532:-1:4:5" };
		InventoryResponse inventoryResponse = sellerServiceHelper.updateInventoryV2(invData);
		Assert.assertEquals(inventoryResponse.getStatus().getStatusCode(), 1001);
		Assert.assertEquals(inventoryResponse.getStatus().getStatusMessage().toString(),
				"Inventory processed successfully");
		Assert.assertEquals(inventoryResponse.getStatus().getStatusType().toString(), "SUCCESS");
		Assert.assertEquals(inventoryResponse.getInventoryEntries().get(0).getRemarks().toString(),
				"Invalid inventory count value");
	}

	@Test(groups = {
			"Regression" }, priority = 3, description = "1.Update Inventory with Invalid SkuCode Count \n 2.Check inventory is not added successfully")
	public void sellerApi_InvalidSkuCode() throws SQLException, JAXBException, IOException {
		String[] invData = { "123:1000:4:5" };
		InventoryResponse inventoryResponse = sellerServiceHelper.updateInventoryV2(invData);
		Assert.assertEquals(inventoryResponse.getStatus().getStatusCode(), 1001);
		Assert.assertEquals(inventoryResponse.getStatus().getStatusMessage().toString(),
				"Inventory processed successfully");
		Assert.assertEquals(inventoryResponse.getStatus().getStatusType().toString(), "SUCCESS");
		Assert.assertEquals(inventoryResponse.getInventoryEntries().get(0).getRemarks().toString(), "Invalid SKU");
	}

	@Test(groups = {
			"Regression" }, priority = 4, dataProviderClass = SellerServiceDP.class, dataProvider = "sellerApi_InvalidSla", description = "1.Update Inventory with Invalid Sla  \n 2.Check inventory is not added successfully")
	public void sellerApiInvalidSla() throws SQLException, JAXBException, IOException {
		String[] invData = { "8903880486532:1000:4:-1" };
		InventoryResponse inventoryResponse = sellerServiceHelper.updateInventoryV2(invData);
		Assert.assertEquals(inventoryResponse.getStatus().getStatusCode(), 1001);
		Assert.assertEquals(inventoryResponse.getStatus().getStatusMessage().toString(),
				"Inventory processed successfully");
		Assert.assertEquals(inventoryResponse.getStatus().getStatusType().toString(), "SUCCESS");
		Assert.assertEquals(inventoryResponse.getInventoryEntries().get(0).getRemarks().toString(),
				"Invalid processing SLA value");
	}

	@Test(groups = {
			"Regression" }, priority = 5, description = "1.Update Inventory with Invalid Warehouse Id \n 2.Check inventory is not added successfully")
	public void sellerApiInvalidWarehouse() throws SQLException, JAXBException, IOException {
		String[] invData = { "8903880486532:1000:0:5" };
		InventoryResponse inventoryResponse = sellerServiceHelper.updateInventoryV2(invData);
		Assert.assertEquals(inventoryResponse.getStatus().getStatusCode(), 1001);
		Assert.assertEquals(inventoryResponse.getStatus().getStatusMessage().toString(),
				"Inventory processed successfully");
		Assert.assertEquals(inventoryResponse.getStatus().getStatusType().toString(), "SUCCESS");
		Assert.assertEquals(inventoryResponse.getInventoryEntries().get(0).getRemarks().toString(),
				"Invalid Warehouse");
	}

	@Test(groups = { "Regression" }, description = "Create Order and verify the Order details and Order line details")
	public void getOrderByReleaseIdForValidReleaseID() throws Exception {
		String skuId[] = { "1417887:1" }; // SkuCode - GRSNTSHT00021 ,
											// MaduraSkuCode - 8903880133641
											// Style id - 267511
		String orderId = end2EndHelper.createOrder(login, password, "6135071", skuId, "", false, false, false, "",false);
		// Assert.assertTrue(omsServiceHelper.validateReleaseStatusInOMS(orderId,
		// "WP", 10));
		omsServiceHelper.checkReleaseStatusForOrder(orderId, "WP");
		orderEntry = omsServiceHelper.getOrderEntry(orderId);
		orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();

		OrderResponse orderResponse = sellerServiceHelper.getOrderV2(orderReleaseId);
		Assert.assertEquals(orderResponse.getStatus().getStatusCode(), 1005);
		Assert.assertEquals(orderResponse.getStatus().getStatusType(), StatusResponse.Type.SUCCESS);
		// OrderEntry orderEntry = orderResponse.getOrderEntry();
		HashMap<String, Object> orderLineDetails = sellerServiceHelper.getOrderLineDetails(orderId);
		Assert.assertEquals(orderEntry.getOrderReleases().get(0).getId().toString(),
				sellerServiceHelper.getOrderReleaseID(orderId) + "");
		Assert.assertEquals(orderEntry.getOrderReleases().get(0).getOrderLines().get(0).getId(),
				orderLineDetails.get("id"));
		Assert.assertEquals(orderEntry.getOrderReleases().get(0).getOrderLines().get(0).getSkuId().toString(),
				orderLineDetails.get("sku_id").toString());
		Assert.assertEquals(orderEntry.getOrderReleases().get(0).getOrderLines().get(0).getStyleId().toString(),
				orderLineDetails.get("style_id").toString());
		Assert.assertEquals(orderEntry.getOrderReleases().get(0).getOrderLines().get(0).getSellerId().toString(),
				orderLineDetails.get("seller_id").toString());
	}

	@Test(groups = { "Regression" }, description = "Verify that Get Order Response for Invalid ReleaseId")
	public void getOrderByIDForInvalidReleaseID() throws Exception {
		String orderId = "700121";
		OrderResponse orderResponse = sellerServiceHelper.getOrderV2(orderId);
		Assert.assertEquals(orderResponse.getStatus().getStatusCode(), 2020);
		Assert.assertEquals(orderResponse.getStatus().getStatusType().toString(), "ERROR");
	}

	@Test(groups = { "Regression" }, description = "Verify Cancel Order functionality")
	public void cancelItemForValidSku() throws Exception {
		String skuId[] = { "1417887:1" }; // SkuCode - GRSNTSHT00021 ,
											// MaduraSkuCode - 8903880133641
											// Style id - 267511
		String orderId = end2EndHelper.createOrder(login, password, "6135071", skuId, "", false, false, false, "",false);
		omsServiceHelper.checkReleaseStatusForOrder(orderId, "WP");
		orderEntry = omsServiceHelper.getOrderEntry(orderId);
		orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();
		OrderResponse orderResponse = sellerServiceHelper.cancelOrderLineV2(orderReleaseId, skuId);
		Assert.assertEquals(orderResponse.getStatus().getStatusCode(), 1004);
		Assert.assertEquals(orderResponse.getStatus().getStatusMessage(), "Items cancelled successfully");
		Assert.assertEquals(orderResponse.getStatus().getStatusType(), StatusResponse.Type.SUCCESS);

		// Check Status in OMS
		OrderReleaseEntry orderReleaseEntry = omsServiceHelper.getOrderReleaseEntry(orderReleaseId);
		Assert.assertEquals(orderReleaseEntry.getStatus(), "F");
		OrderLineEntry orderLineEntry = orderReleaseEntry.getOrderLines().get(0);
		Assert.assertEquals(orderLineEntry.getStatus(), "IC");
	}

	@Test(groups = { "Regression" }, description = "Verify that Cancle Order Call for Realse not belong to the seller should give Error in the status")
	public void cancelOrderNotBelongToSeller() throws Exception {
		String skuId[] = { "3831:1" }; // SkuCode - GRSNTSHT00021 ,
										// MaduraSkuCode - 8903880133641 Style
										// id - 267511
		String skuIdNoBelongToSeller[] = { "895234:1" };
		String orderId = end2EndHelper.createOrder(login, password, "6135071", skuId, "", false, false, false, "",false);
		// omsServiceHelper.validateReleaseStatusInOMS(orderId, "WP", 10);
		omsServiceHelper.checkReleaseStatusForOrder(orderId, "WP");
		orderEntry = omsServiceHelper.getOrderEntry(orderId);
		orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();
		OrderResponse orderResponse = sellerServiceHelper.cancelOrderLineV2(orderReleaseId, skuIdNoBelongToSeller);
		Assert.assertEquals(orderResponse.getStatus().getStatusCode(), 2004);
		Assert.assertEquals(orderResponse.getStatus().getStatusMessage(),
				"This order does not belong to the given seller");
		Assert.assertEquals(orderResponse.getStatus().getStatusType(), StatusResponse.Type.ERROR);
	}

	@Test(groups = { "Regression" }, description = "Api should not allow a Cancel Order that has been Cancelled already. Appropriate Message should be displayed")
	public void cancelOrderDuplicate() throws Exception {
		String skuId[] = { "1417887:1" }; // SkuCode - GRSNTSHT00021 ,
											// MaduraSkuCode - 8903880133641
											// Style id - 267511
		String orderId = end2EndHelper.createOrder(login, password, "6135071", skuId, "", false, false, false, "",false);
		// omsServiceHelper.validateReleaseStatusInOMS(orderId, "WP", 10);
		omsServiceHelper.checkReleaseStatusForOrder(orderId, "WP");
		orderEntry = omsServiceHelper.getOrderEntry(orderId);
		orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();

		OrderResponse orderResponse = sellerServiceHelper.cancelOrderLineV2(orderReleaseId, skuId);
		Assert.assertEquals(orderResponse.getStatus().getStatusCode(), 1004);
		Assert.assertEquals(orderResponse.getStatus().getStatusMessage(), "Items cancelled successfully");
		Assert.assertEquals(orderResponse.getStatus().getStatusType(), StatusResponse.Type.SUCCESS);

		OrderResponse orderResponse1 = sellerServiceHelper.cancelOrderLineV2(orderReleaseId, skuId);
		Assert.assertEquals(orderResponse1.getStatus().getStatusCode(), 2004);
		Assert.assertEquals(orderResponse1.getStatus().getStatusMessage(),
				"Cancellation not allowed because release in not in Q, RFR, or WP status");
		Assert.assertEquals(orderResponse1.getStatus().getStatusType(), StatusResponse.Type.ERROR);
	}

	@Test(enabled = true, groups = { "Regression" }, description = "Update Tax and Dispatch an Order")
	public void sellerApiReadyToDispatch() throws Exception {
		LmsServiceHelper lmsServiceHelper = new LmsServiceHelper();
		String skuId[] = { "1417887:1" };
		String lineEntry[] = { "1417887:100.00:10.1:VAT" };// SkuCode -
															// GRSNTSHT00021 ,
															// MaduraSkuCode -
															// 8903880133641
															// Style id - 267511
		String orderId = end2EndHelper.createOrder(login, password, "6135071", skuId, "", false, false, false, "",false);
		// omsServiceHelper.validateReleaseStatusInOMS(orderId, "WP", 10);
		omsServiceHelper.checkReleaseStatusForOrder(orderId, "WP");
		orderEntry = omsServiceHelper.getOrderEntry(orderId);
		orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();

		OrderResponse orderResponse = sellerServiceHelper.updateTaxV2(orderReleaseId, lineEntry);

		String trackingNumber = lmsServiceHelper.getTrackingNumber("ML", "36", "true", "560068", "NORMAL")
				.getTrackingNumberEntry().getTrackingNumber().toString();
		DBUtilities.exUpdateQuery(
				"update order_release set tracking_no='" + trackingNumber + "' where ID=" + orderReleaseId, "oms");

		OrderResponse orderResponse1 = sellerServiceHelper.readyToDispatchV2(orderReleaseId, skuId);
		Assert.assertEquals(orderResponse.getStatus().getStatusCode(), 1003);
		Assert.assertEquals(orderResponse.getStatus().getStatusMessage(), "Order updated successfully");
		Assert.assertEquals(orderResponse.getStatus().getStatusType().toString(), "SUCCESS");
		omsServiceHelper.checkReleaseStatusForOrder(orderId, "PK");
	}

	@Test(groups = { "Regression" }, description = "Verify that system should not allow an Order to be dispatched that belong to different seller")
	public void RtdOrderNotBelongToSeller() throws Exception {
		LmsServiceHelper lmsServiceHelper = new LmsServiceHelper();
		String skuId[] = { "3831:1" }; // SkuCode - GRSNTSHT00021 ,
										// MaduraSkuCode - 8903880133641 Style
										// id - 267511
		String orderId = end2EndHelper.createOrder(login, password, "6135071", skuId, "", false, false, false, "",false);
		// omsServiceHelper.validateReleaseStatusInOMS(orderId, "WP", 10);
		omsServiceHelper.checkReleaseStatusForOrder(orderId, "WP");
		orderEntry = omsServiceHelper.getOrderEntry(orderId);
		orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();

		String trackingNumber = lmsServiceHelper.getTrackingNumber("ML", "36", "true", "560068", "NORMAL")
				.getTrackingNumberEntry().getTrackingNumber().toString();
		DBUtilities.exUpdateQuery(
				"update order_release set tracking_no='" + trackingNumber + "' where ID=" + orderReleaseId, "oms");

		OrderResponse orderResponse = sellerServiceHelper.readyToDispatchV2(orderReleaseId, skuId);
		Assert.assertEquals(orderResponse.getStatus().getStatusCode(), 2004);
		Assert.assertEquals(orderResponse.getStatus().getStatusMessage(),
				"This order does not belong to the given seller");
		Assert.assertEquals(orderResponse.getStatus().getStatusType(), StatusResponse.Type.ERROR);
	}

	@Test(enabled = true, groups = { "Regression" }, description = "Verify that System should throw Error in the status when dispatching a release that has already been dispatched")
	public void sellerApiReadyToDispatchDuplicate() throws Exception {
		LmsServiceHelper lmsServiceHelper = new LmsServiceHelper();
		String skuId[] = { "1417887:1" };
		String lineEntry[] = { "1417887:100.00:10.1:VAT" };// SkuCode -
															// GRSNTSHT00021 ,
															// MaduraSkuCode -
															// 8903880133641
															// Style id - 267511
		String orderId = end2EndHelper.createOrder(login, password, "6135071", skuId, "", false, false, false, "",false);
		// omsServiceHelper.validateReleaseStatusInOMS(orderId, "WP", 10);
		omsServiceHelper.checkReleaseStatusForOrder(orderId, "WP");
		orderEntry = omsServiceHelper.getOrderEntry(orderId);
		orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();

		OrderResponse orderResponse = sellerServiceHelper.updateTaxV2(orderReleaseId, lineEntry);

		String trackingNumber = lmsServiceHelper.getTrackingNumber("ML", "36", "true", "560068", "NORMAL")
				.getTrackingNumberEntry().getTrackingNumber().toString();
		DBUtilities.exUpdateQuery(
				"update order_release set tracking_no='" + trackingNumber + "' where ID=" + orderReleaseId, "oms");

		OrderResponse orderResponse1 = sellerServiceHelper.readyToDispatchV2(orderReleaseId, skuId);
		Assert.assertEquals(orderResponse1.getStatus().getStatusCode(), 1003);
		Assert.assertEquals(orderResponse1.getStatus().getStatusMessage(), "Order updated successfully");
		Assert.assertEquals(orderResponse1.getStatus().getStatusType().toString(), "SUCCESS");

		OrderResponse orderResponse2 = sellerServiceHelper.readyToDispatchV2(orderReleaseId, skuId);
		Assert.assertEquals(orderResponse2.getStatus().getStatusCode(), 2004);
		// Assert.assertEquals(orderResponse2.getStatus().getStatusMessage() ,
		// "Order is already marked Dispatched");
		Assert.assertEquals(orderResponse2.getStatus().getStatusType().toString(), "ERROR");

	}

	@Test(enabled = true, groups = { "Regression" }, description = "Get Shipping label for an Order")
	public void sellerApiFetchShippingLabel() throws Exception {
		String skuId[] = {WMSConstants.SKU_ID + ":1"}; // SkuCode - GRSNTSHT00021 ,
											// MaduraSkuCode - 8903880133641
											// Style id - 267511
		String orderId = end2EndHelper.createOrder(login, password, "6135071", skuId, "", false, false, false, "",false);
		omsServiceHelper.checkReleaseStatusForOrder(orderId, "WP");
		// omsServiceHelper.validateReleaseStatusInOMS(orderId, "WP", 10);
		orderEntry = omsServiceHelper.getOrderEntry(orderId);
		orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();
		// long releaseId = (long)
		// DBUtilities.exSelectQueryForSingleRecord("select id from
		// order_release where order_id_fk = " + orderId, "myntra_oms")
		// .get("id");
		// Thread.sleep(30000);
		omsServiceHelper.stampGovtTaxForVectorSuccess(orderReleaseId);
		int serviceResponse = sellerServiceHelper.downloadShippingLabelV2(orderReleaseId);
		Assert.assertEquals(serviceResponse, 200);
	}

	@Test(groups = { "Regression" }, description = "Update Tax value for an Order")
	public void sellerApiUpdateTax() throws Exception {
		String skuId[] = {WMSConstants.SKU_ID + ":1"};
		String lineEntry[] = { "1417887:100.00:10.1:VAT" };
		String orderId = end2EndHelper.createOrder(login, password, "6135071", skuId, "", false, false, false, "",false);
		// Assert.assertTrue(omsServiceHelper.validateReleaseStatusInOMS(orderId,
		// "WP", 10));
		omsServiceHelper.checkReleaseStatusForOrder(orderId, "WP");
		orderEntry = omsServiceHelper.getOrderEntry(orderId);
		orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();

		OrderResponse orderResponse = sellerServiceHelper.updateTaxV2(orderReleaseId, lineEntry);
		Assert.assertEquals(orderResponse.getStatus().getStatusCode(), 1003);
		Assert.assertEquals(orderResponse.getStatus().getStatusMessage(), "Order updated successfully");
		Assert.assertEquals(orderResponse.getStatus().getStatusType().toString(), "SUCCESS");
		OrderReleaseEntry orderReleaseEntry = omsServiceHelper.getOrderReleaseEntry(orderReleaseId);
		OrderLineEntry orderLineEntry = orderReleaseEntry.getOrderLines().get(0);
		Assert.assertEquals(orderLineEntry.getGovtTaxRate(), 10.1);
		Assert.assertEquals(orderLineEntry.getGovtTaxType(), "VAT");
		Assert.assertEquals(orderLineEntry.getGovtUnitTaxAmount(), 100.00);
		Assert.assertEquals(orderLineEntry.getGovtTaxAmount(), 100.00);
	}

	@Test(groups = { "Regression" }, description = "Verify that system should throw appropiate error in status when trying to update tax for an release that has already been dispatched")
	public void sellerApiUpdateTaxAfterRtd() throws Exception {
		LmsServiceHelper lmsServiceHelper = new LmsServiceHelper();
		String skuId[] = {WMSConstants.SKU_ID + ":1"};
		String lineEntry[] = { "1417887:100.00:10.1:VAT" };
		String orderId = end2EndHelper.createOrder(login, password, "6135071", skuId, "", false, false, false, "",false);
		// Assert.assertTrue(omsServiceHelper.validateReleaseStatusInOMS(orderId,
		// "WP", 10));
		omsServiceHelper.checkReleaseStatusForOrder(orderId, "WP");
		orderEntry = omsServiceHelper.getOrderEntry(orderId);
		orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();

		OrderResponse orderResponse = sellerServiceHelper.updateTaxV2(orderReleaseId, lineEntry);

		String trackingNumber = lmsServiceHelper.getTrackingNumber("ML", "36", "true", "560068", "NORMAL")
				.getTrackingNumberEntry().getTrackingNumber().toString();
		DBUtilities.exUpdateQuery(
				"update order_release set tracking_no='" + trackingNumber + "' where ID=" + orderReleaseId, "oms");

		OrderResponse orderResponse1 = sellerServiceHelper.readyToDispatchV2(orderReleaseId, skuId);
		Assert.assertEquals(orderResponse1.getStatus().getStatusCode(), 1003);
		Assert.assertEquals(orderResponse1.getStatus().getStatusMessage(), "Order updated successfully");
		Assert.assertEquals(orderResponse1.getStatus().getStatusType().toString(), "SUCCESS");

		OrderResponse orderResponse2 = sellerServiceHelper.updateTaxV2(orderReleaseId, lineEntry);
		Assert.assertEquals(orderResponse2.getStatus().getStatusCode(), 2004);
		Assert.assertEquals(orderResponse2.getStatus().getStatusMessage(),
				"Tax cannot be updated in current order status");
		Assert.assertEquals(orderResponse2.getStatus().getStatusType().toString(), "ERROR");

	}
}
