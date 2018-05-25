package com.myntra.apiTests.erpservices.lms.Helper;

import com.myntra.apiTests.SERVICE_TYPE;
import com.myntra.apiTests.erpservices.Constants;
import com.myntra.cts.entries.CourierTrackingResponse;
import com.myntra.lordoftherings.boromir.DBUtilities;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.test.commons.service.HTTPMethods;
import com.myntra.test.commons.service.HttpExecutorService;
import com.myntra.test.commons.service.Svc;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

import javax.xml.bind.JAXBException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * 
 * @author Shubham gupta
 *
 */
public class DelhiveryHelper {

	public int delhivryCreate() throws JAXBException, JsonGenerationException, JsonMappingException, IOException {
		Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.DELHIVERY_CREATE, null,
				SERVICE_TYPE.DELHIVERY.toString(), HTTPMethods.POST, null, getDelhiveryHeader());
		int status = service.getResponseStatus();
		return status;
	}

	private HashMap<String, String> getDelhiveryHeader() {
		HashMap<String, String> pushPickupToCourierHeader = new HashMap<String, String>();
		pushPickupToCourierHeader.put("Authorization", "Token cf3ee76782c42f959370012940a68d22b0756beb");
		pushPickupToCourierHeader.put("Content-Type", "application/x-www-form-urlencoded");
		return pushPickupToCourierHeader;
	}

	public String getTrackingNumber() throws IOException {
		String response;
		URL url = new URL(
				"https://test.delhivery.com/waybill/api/fetch/json/?token=cf3ee76782c42f959370012940a68d22b0756beb");
		BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
		response = br.readLine();
		return response;
	}


	/**
	 * updateDE_CTS
	 * 
	 * @param trackingNumber
	 * @param orderId
	 * @param statusLocation
	 * @param statusType
	 * @param status
	 * @param instructions
	 * @return
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public CourierTrackingResponse updateDE_CTS(String trackingNumber, String orderId, String statusLocation,
			String statusType, String status, String instructions)
			throws JsonGenerationException, JsonMappingException, IOException {
		//DelhiveryShipmentUpdateRequest dsRequest = new DelhiveryShipmentUpdateRequest();
		//DelhiveryShipmentUpdateRequest.ShipmentStatus shipmentStatus = dsRequest.new ShipmentStatus();
		//Shipment shipment = new Shipment();

		/*
		 * shipmentStatus.setInstructions(instructions);
		 * shipmentStatus.setStatusLocation(statusLocation);
		 * shipmentStatus.setStatusType(statusType);
		 * shipmentStatus.setStatusDateTime(getDate());
		 * shipmentStatus.setStatus(status);
		 * 
		 * dsRequest.setStatus(shipmentStatus); dsRequest.setAwb(trackingNumber);
		 * dsRequest.setReferenceNo(orderId); dsRequest.setNSLCode("ED-100");
		 * dsRequest.setPickUpDate(getDate()); String payload =
		 * APIUtilities.getObjectToJSON(dsRequest);
		 */

		String payload = "{\"Shipment\":{\"Status\":{\"Status\":\"" + status + "\",\"StatusDateTime\":\"" + LMSUtils.getDate()
				+ "\", \"StatusType\":\"" + statusType + "\", \"StatusLocation\"" + ":\"" + statusLocation
				+ "\",\"Instructions\":\"" + instructions + "\",\"lenght\": \"35.8\","
				+ "\"Breadth\":\"28.5\",\"Height\":\"12.3\",\"Weight\":\"0\",\"ChargedWeight\":\"0\"},"
				+ "\"PickUpDate\":\"" + LMSUtils.getDate() + "\",\"NSLCode\":\"ED-100\",\"Sortcode\""
				+ ":\"JDH/BID\",\"ReferenceNo\":\"" + orderId + "\",\"AWB\":\"" + trackingNumber + "\"}}";

		System.out.println(payload);
		Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.CTS_DE_UPDATE, null,
				SERVICE_TYPE.CTS.toString(), HTTPMethods.POST, payload, getCTSHeader());
		CourierTrackingResponse courierTrackingResponse = (CourierTrackingResponse) APIUtilities
				.getJsontoObject(service.getResponseBody(), new CourierTrackingResponse());
		return courierTrackingResponse;
	}

	public CourierTrackingResponse updateDE_CTS(String trackingNumber, String orderId, String statusLocation,
			String statusType, String status, String instructions, String nslCode)
			throws JsonGenerationException, JsonMappingException, IOException {
//		DelhiveryShipmentUpdateRequest dsRequest = new DelhiveryShipmentUpdateRequest();
//		DelhiveryShipmentUpdateRequest.ShipmentStatus shipmentStatus = dsRequest.new ShipmentStatus();
//		Shipment shipment = new Shipment();

		/*
		 * shipmentStatus.setInstructions(instructions);
		 * shipmentStatus.setStatusLocation(statusLocation);
		 * shipmentStatus.setStatusType(statusType);
		 * shipmentStatus.setStatusDateTime(getDate());
		 * shipmentStatus.setStatus(status);
		 * 
		 * dsRequest.setStatus(shipmentStatus); dsRequest.setAwb(trackingNumber);
		 * dsRequest.setReferenceNo(orderId); dsRequest.setNSLCode("ED-100");
		 * dsRequest.setPickUpDate(getDate()); String payload =
		 * APIUtilities.getObjectToJSON(dsRequest);
		 */

		String payload = "{\"Shipment\":{\"Status\":{\"Status\":\"" + status + "\",\"StatusDateTime\":\"" + LMSUtils.getDate()
				+ "\", \"StatusType\":\"" + statusType + "\", \"StatusLocation\"" + ":\"" + statusLocation
				+ "\",\"Instructions\":\"" + instructions + "\",\"lenght\": \"35.8\","
				+ "\"Breadth\":\"28.5\",\"Height\":\"12.3\",\"Weight\":\"0\",\"ChargedWeight\":\"0\"},"
				+ "\"PickUpDate\":\"" + LMSUtils.getDate() + "\",\"NSLCode\":\"" + nslCode + "\",\"Sortcode\""
				+ ":\"JDH/BID\",\"ReferenceNo\":\"" + orderId + "\",\"AWB\":\"" + trackingNumber + "\"}}";

		System.out.println(payload);
		Svc service = HttpExecutorService.executeHttpService(Constants.LMS_PATH.CTS_DE_UPDATE, null,
				SERVICE_TYPE.CTS.toString(), HTTPMethods.POST, payload, getCTSHeader());
		CourierTrackingResponse courierTrackingResponse = (CourierTrackingResponse) APIUtilities
				.getJsontoObject(service.getResponseBody(), new CourierTrackingResponse());
		return courierTrackingResponse;
	}

	private HashMap<String, String> getCTSHeader() {
		HashMap<String, String> createOrderHeaders = new HashMap<String, String>();
		createOrderHeaders.put("Authorization", "Basic ZGVsaGl2ZXJ5OmFjY2Vzcw==");
		createOrderHeaders.put("Content-Type", "Application/json");
		createOrderHeaders.put("Accept", "Application/json");
		return createOrderHeaders;
	}

	public void deleteFromOMSLMS(String orderId, String orderTrackingId) throws SQLException {
		String deleteFromOrders = "DELETE from orders where id = " + orderId + "";
		String deleteFromOrderRelease = "DELETE from order_release where order_id_fk = " + orderId;
		String deleteFromOrderLine = "DELETE from order_line where order_id_fk = " + orderId;
		String deleteHubAtivivtyDetail = "DELETE from hub_activity_detail where shipment_id = " + orderId;
		String deleteOrderTrackingDetail = "DELETE from order_tracking_detail where order_tracking_id = "
				+ orderTrackingId;
		String deleteOrderToShip = "DELETE from order_to_ship where order_id = " + orderId;
		String deletePickup = "DELETE from pickup where order_id = " + orderId;
		String deleteOrderTracking = "DELETE from order_tracking where order_id = " + orderId;

		DBUtilities.exUpdateQuery(deleteFromOrders, "myntra_oms");
		DBUtilities.exUpdateQuery(deleteFromOrderRelease, "myntra_oms");
		DBUtilities.exUpdateQuery(deleteFromOrderLine, "myntra_oms");
		DBUtilities.exUpdateQuery(deleteHubAtivivtyDetail, "myntra_lms");
		DBUtilities.exUpdateQuery(deleteOrderTrackingDetail, "myntra_lms");
		DBUtilities.exUpdateQuery(deletePickup, "myntra_lms");
		DBUtilities.exUpdateQuery(deleteOrderTracking, "myntra_lms");
		DBUtilities.exUpdateQuery(deleteOrderToShip, "myntra_lms");
	}

	public void insertIntoOMSLMS(String trackingNumber) throws SQLException {
		
		String insertOrders = "INSERT INTO `orders` (`id`, `invoice_id`, `login`, `user_contact_no`, `customer_name`, `payment_method`, `status_code`, `coupon_code`, `cash_coupon_code`, `mrp_total`, `discount`, `cart_discount`, `coupon_discount`, `cash_redeemed`, `pg_discount`, `final_amount`, `shipping_charge`, `cod_charge`, `emi_charge`, `gift_charge`, `tax_amount`, `cashback_offered`, `request_server`, `response_server`, `is_on_hold`, `is_gift`, `notes`, `billing_address_id_fk`, `cancellation_reason_id_fk`, `on_hold_reason_id_fk`, `queued_on`, `cancelled_on`, `created_by`, `created_on`, `last_modified_on`, `version`, `order_type`, `loyalty_points_used`, `store_id`, `store_order_id`) VALUES (70020644, NULL, 'cbb7bb10.edbb.45ef.940c.5a44551911feVlY98wSiXe', '1111111111', 'test_1', 'cod', NULL, '', '', 1199.00, 0.00, 0.00, 0.00, 0.00, 0.20, 1199.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, NULL, NULL, 0, 0, '', 1, NULL, NULL, '2015-10-21 04:59:52', NULL, 'cbb7bb10.edbb.45ef.940c.5a44551911feVlY98wSiXe', '2015-10-20 00:00:00', '2016-02-12 21:13:47', 4, 'on', 0.00, 1, NULL)";
		String insterOrderRelease = "INSERT INTO `order_release` (`id`, `order_id_fk`, `login`, `status_code`, `payment_method`, `mrp_total`, `discount`, `cart_discount`, `coupon_discount`, `cash_redeemed`, `pg_discount`, `final_amount`, `shipping_charge`, `cod_charge`, `emi_charge`, `gift_charge`, `tax_amount`, `cashback_offered`, `receiver_name`, `address`, `city`, `locality`, `state`, `country`, `zipcode`, `mobile`, `email`, `courier_code`, `tracking_no`, `warehouse_id`, `is_refunded`, `cod_pay_status`, `cancellation_reason_id_fk`, `packed_on`, `shipped_on`, `delivered_on`, `completed_on`, `cancelled_on`, `created_by`, `created_on`, `last_modified_on`, `version`, `is_on_hold`, `queued_on`, `invoice_id`, `cheque_no`, `exchange_release_id`, `user_contact_no`, `shipping_method`, `on_hold_reason_id_fk`, `loyalty_points_used`, `store_id`, `store_release_id`) VALUES(70020644, 70020644, 'cbb7bb10.edbb.45ef.940c.5a44551911feVlY98wSiXe', 'DL', 'cod', 1199.00, 0.00, 0.00, 0.00, 0.00, 0.20, 1199.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 'test_1 ', '&#x23;333, 33th cross, 18th main &#xa;', 'Bangalore', 'Mico Layout', 'KA', 'India', '560076', '1234567890', 'test_1&#x40;myntra.com', 'DE', '"
				+ trackingNumber
				+ "', 1, 0, 'pending', NULL, '2015-10-21 04:59:55', '2015-10-21 04:59:57', '2015-10-21 05:00:11', NULL, NULL, 'cbb7bb10.edbb.45ef.940c.5a44551911feVlY98wSiXe', '2015-10-20 00:00:00', '2016-04-12 11:00:49', 14, 0, '2015-10-21 04:59:52', NULL, NULL, NULL, '1111111111', 'NORMAL', NULL, 0.00, 1, NULL)";
		String insertOrderLine = "INSERT INTO `order_line` (`id`, `order_id_fk`, `order_release_id_fk`, `style_id`, `option_id`, `sku_id`, `status_code`, `unit_price`, `quantity`, `discounted_quantity`, `discount`, `cart_discount`, `cash_redeemed`, `coupon_discount`, `pg_discount`, `final_amount`, `tax_amount`, `tax_rate`, `cashback_offered`, `disocunt_rule_id`, `discount_rule_rev_id`, `promotion_id`, `is_discounted`, `is_returnable`, `cancellation_reason_id_fk`, `cancelled_on`, `created_by`, `created_on`, `last_modified_on`, `version`, `exchange_orderline_id`, `loyalty_points_used`, `seller_id`, `supply_type`, `vendor_id`, `store_line_id`, `po_status`) VALUES(52667615, 70020644, 70020644, 245024, 825090, 822644, 'D', 1199.00, 1, 0, 0.00, 0.00, 0.00, 0.00, 0.20, 1198.80, 0.00, 5.50, 0.00, 0, 0, NULL, 0, 1, NULL, NULL, 'cbb7bb10.edbb.45ef.940c.5a44551911feVlY98wSiXe', '2015-10-21 04:59:47', '2016-01-20 16:24:01', 6, NULL, 0.00, 1, 'ON_HAND', NULL, NULL, 'UNUSED')";
		String insertOrderToShip = "INSERT INTO `order_to_ship` (`id`, `created_by`, `created_on`, `last_modified_on`, `version`, `address`, `city`, `is_cod`, `cod_amount`, `country`, `email`, `first_name`, `last_name`, `mobile`, `order_id`, `state`, `title`, `zipcode`, `cust_mobile`, `status`, `warehouse_id`, `delivery_center_id`, `packed_on`, `inscanned_on`, `shipped_on`, `order_additional_info_id`, `sales_order_id`, `promise_date`, `shipping_method`, `locality`, `store_id`, `seller_id`, `user_id`, `rto_warehouse_id`, `courier_code`, `tracking_number`, `shipment_status`, `rto_received_date`) VALUES(10276337, 'erpMessageQueue', '2015-10-21 04:59:55', '2016-04-12 16:39:38', 7, 'Myntra design, AKR tech park, Near kudlu gate, Bangalore', 'Bangalore', 1, 1199.00, 'India', 'spsautomation@myntra.com', 'sps automation', '  ', '9191919191', 70020644, 'KA', NULL, '560076', '1111111111', 'PK', 1, 293, '2015-10-21 04:59:55', '2015-10-21 04:59:57', '2015-10-21 04:59:57', 9493879, NULL, '2015-10-25 23:45:00', 'NORMAL', 'Bangalore', 1, NULL, NULL, NULL, NULL, '"
				+ trackingNumber + "', 'PACKED', NULL)";
		String insertOrderTracking = "INSERT INTO `order_tracking` (`id`, `created_by`, `created_on`, `last_modified_on`, `version`, `courier_code`, `delivery_date`, `delivery_status`, `failed_attempts`, `last_scan_time`, `pickup_date`, `order_id`, `return_id`, `shipment_type`, `tracking_no`, `tracking_task_status`, `is_synced_with_portal`, `rto_date`, `first_attempt_date`, `last_attempt_date`, `lost_on_date`, `courier_creation_status`, `order_tracking_status`, `shipment_status`, `rto_received_date`) VALUES (19934394, 'amit.bharti', '2015-01-01 01:34:51', '2016-04-13 10:23:55', 9, 'DE', '2015-01-05 19:08:39', 'FIT', 0, '2015-01-05 19:08:39', '2015-01-01 16:39:33', 70020644, NULL, 'DL', '"
				+ trackingNumber
				+ "', 'SUCCESS', 0, NULL, '2015-01-05 10:54:39', '2015-01-05 10:54:39', NULL, 'AWAITING', NULL, 'PACKED', NULL)";
		String insterPickup = "INSERT INTO `pickup` (`id`, `created_by`, `created_on`, `last_modified_on`, `version`, `return_id`, `order_id`, `address`, `city`, `country`, `email`, `first_name`, `last_name`, `mobile`, `state`, `title`, `zipcode`, `cust_mobile`, `status`, `delivery_center_id`, `exchange_order_id`, `item_description`, `approval_status`, `customer_signature`, `locality`, `po_number`, `dest_warehouse_id`, `courier_code`, `quantity`, `vendor_code`, `remarks`, `route`, `style_ids`, `shipment_status`, `tracking_number`) VALUES(1648128, 'rms_s', '2015-11-17 14:49:17', '2015-11-17 14:49:17', 0, 10000678, 70020644, '&#x23;333, 33th cross, 18th main &#xa;', 'Bangalore', 'India', 'test_1@myntra.com', 'test_1', ' ', '1234567890', 'KA', NULL, '560076', NULL, 'RIT', 293, NULL, ', null', NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, '245024', NULL, NULL)";

		DBUtilities.exUpdateQuery(insertOrders, "myntra_oms");
		DBUtilities.exUpdateQuery(insterOrderRelease, "myntra_oms");
		DBUtilities.exUpdateQuery(insertOrderLine, "myntra_oms");
		DBUtilities.exUpdateQuery(insertOrderToShip, "myntra_lms");
		DBUtilities.exUpdateQuery(insertOrderTracking, "myntra_lms");
		DBUtilities.exUpdateQuery(insterPickup, "myntra_lms");
	}

	
}
