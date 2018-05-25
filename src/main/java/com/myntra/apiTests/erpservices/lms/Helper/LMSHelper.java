package com.myntra.apiTests.erpservices.lms.Helper;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.myntra.apiTests.SERVICE_TYPE;
import com.myntra.apiTests.common.Constants.EnumSCM;
import com.myntra.apiTests.common.Constants.LambdaInterfaces;
import com.myntra.apiTests.common.Constants.ReleaseStatus;
import com.myntra.apiTests.common.Constants.ShipmentSource;
import com.myntra.apiTests.common.ExceptionHandler;
import com.myntra.apiTests.common.ProcessOrder.Service.ProcessRelease;
import com.myntra.apiTests.common.entries.ReleaseEntry;
import com.myntra.apiTests.erpservices.Constants;
import com.myntra.apiTests.erpservices.lms.Constants.LMS_PINCODE;
import com.myntra.apiTests.erpservices.oms.OMSHelpersEnums.ReadyToDispatchType;
import com.myntra.apiTests.erpservices.oms.OMSServiceHelper;
import com.myntra.apiTests.erpservices.partners.SellerPaymentServiceHelper;
import com.myntra.apiTests.erpservices.sellerapis.SellerConfig;
import com.myntra.apiTests.erpservices.wms.WMSServiceHelper;
import com.myntra.client.wms.codes.utils.PacketItemStatus;
import com.myntra.client.wms.response.*;
import com.myntra.commons.exception.ManagerException;
import com.myntra.lms.client.response.OrderResponse;
import com.myntra.lms.client.response.ShipmentItemEntry;
import com.myntra.lms.client.response.TrackingNumberResponse;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.boromir.DBUtilities;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.oms.client.entry.OrderLineEntry;
import com.myntra.oms.client.entry.PacketEntry;
import com.myntra.oms.client.response.OrderReleaseResponse;
import com.myntra.oms.client.response.PacketResponse;
import com.myntra.test.commons.service.HTTPMethods;
import com.myntra.test.commons.service.HttpExecutorService;
import com.myntra.test.commons.service.Svc;
import com.myntra.test.commons.topology.SystemConfigProvider;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jettison.json.JSONException;
import org.testng.Assert;
import org.testng.SkipException;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 *
 * @author Shubham gupta
 *
 */
public class LMSHelper {

	private static Initialize init = new Initialize("/Data/configuration");
	private static Logger log = Logger.getLogger(LMSHelper.class);
	private String testOrder = System.getenv("TestOrder");
	private Boolean fg = fgVal(testOrder);

	public boolean fgVal(String testOrder){
		
		if (testOrder=="false")return false;
		else return true;
	}

	public Consumer<String> delHubActivityDetail = orderId-> DBUtilities.exUpdateQuery("delete from hub_activity_detail where shipment_id = '"+orderId+"'","lms");

	public Consumer<String> deltry_and_buy_shipment = orderId->DBUtilities.exUpdateQuery("delete from try_and_buy_shipment where actual_order_id = '"+orderId+"'","lms");

	public Consumer<String> deltry_and_buy_shipment_item = orderId-> DBUtilities.exUpdateQuery("delete from try_and_buy_shipment_item where order_to_ship_id in(select id from order_to_ship where order_id = '"+orderId+"')","lms");

	public Consumer<String> deOrder_tracking_detail = orderId->DBUtilities.exUpdateQuery("delete from order_tracking_detail where order_tracking_id in(select id from order_tracking where order_id = '"+orderId+"')","lms");
	
    public String getOrderIdFromReturnId(String returnId) {
		
		Map<String, Object> returnShipment = (HashMap<String, Object>) DBUtilities.exSelectQueryForSingleRecord("select order_id from return_shipment where source_return_id = '" + returnId+"'", "lms");
		return returnShipment.get("order_id").toString();
	}

	public String getReturnStatus(String return_id){
		
		Map<String, Object> getReturn = DBUtilities.exSelectQueryForSingleRecord("select shipment_status from return_shipment where source_return_id = '"+return_id+"'", "lms");
		return getReturn.get("shipment_status").toString();
	}

	public String getReturnApprovalStatus(String return_id){
		
		Map<String, Object> getReturn = DBUtilities.exSelectQueryForSingleRecord("select approval_flag from return_shipment where source_return_id = '"+return_id+"'", "lms");
		return getReturn.get("approval_flag").toString();
	}

	public String getReturnIdFromOrderId(String orderID){
		
		Map<String, Object> getReturnID = DBUtilities.exSelectQueryForSingleRecord("select source_return_id from return_shipment where order_id = '"+orderID+"'", "lms");
		return getReturnID.get("source_return_id").toString();
	}
	
	public void delShipment_order_map(String orderId){
		String delShipmentOrderMap = "delete from shipment_order_map where order_id = '"+orderId+"'";
		DBUtilities.exUpdateQuery(delShipmentOrderMap,"lms");
	}
	public void delTrip_order_assignment(String trackingNumber){
		String deltripOrderAssignment = "delete from trip_order_assignment where tracking_no = '"+trackingNumber+"'";
		DBUtilities.exUpdateQuery(deltripOrderAssignment,"lms");
	}
	public void delOrder_tracking(String orderId){
		String delOrderTracking = "delete from order_tracking where order_id = '"+orderId+"'";
		DBUtilities.exUpdateQuery(delOrderTracking,"lms");
	}

	public void deltShipentItem(String orderId){
		String delorderToship = "delete from shipment_item where order_to_ship_id = (select id from order_to_ship where order_id = '"+orderId+"')";
		DBUtilities.exUpdateQuery(delorderToship,"lms");
	}

	public void delOrder_to_ship(String orderId){
		String delorderToship = "delete from order_to_ship where order_id = '"+orderId+"'";
		DBUtilities.exUpdateQuery(delorderToship,"lms");
	}

	public void deltrynBuyshipment(String orderId){
		String delorderToship = "delete from try_and_buy_shipment where order_to_ship_id = (select id from order_to_ship where order_id = '"+orderId+"')";
		DBUtilities.exUpdateQuery(delorderToship,"lms");
	}

	public void delML_delivery_shipment_item(String orderId){
		String delMlDeliveryShipment = "delete from ml_delivery_shipment_item where ml_delivery_shipment_id = (select id from ml_shipment where source_reference_id = '"+orderId+"')";
		DBUtilities.exUpdateQuery(delMlDeliveryShipment,"lms");
	}

	public void delMl_delivery_shipment(String orderId){
		String delMlDeliveryShipment = "delete from ml_delivery_shipment where ml_shipment_id = (select id from ml_shipment where source_reference_id = '"+orderId+"')";
		DBUtilities.exUpdateQuery(delMlDeliveryShipment,"lms");
	}
	public void delMl_shipment(String orderId){
		String delMlShipment = "delete from ml_shipment where source_reference_id = '"+orderId+"'";
		DBUtilities.exUpdateQuery(delMlShipment,"lms");
	}

	public void delMl_shipmentWithReturnId(String orderId){
		String delMlShipment = "delete from ml_shipment where source_reference_id in(select id from return_shipment where order_id= '"+orderId+"')";
		DBUtilities.exUpdateQuery(delMlShipment,"lms");
	}
	public void delMl_shipment_tracking_detail(String trackingNumber){
		String delMLShipmenttrackingDetail = "delete from ml_shipment_tracking_detail where tracking_number = '"+trackingNumber+"'";
		DBUtilities.exUpdateQuery(delMLShipmenttrackingDetail,"lms");
	}

	public void delMl_last_mile_partner_shipment_assignment(String trackingNumber){
		String delmlLastMilePartnershipment = "delete from ml_last_mile_partner_shipment_assignment where ml_tracking_number = '"+trackingNumber+"'";
		DBUtilities.exUpdateQuery(delmlLastMilePartnershipment,"lms");
	}
	
	public void delDelivery_staff(String code){
		String delDelivery_staff = "delete from delivery_staff where code = '"+code+"'";
		DBUtilities.exUpdateQuery(delDelivery_staff,"lms");
	}

	public void deltracking_numbers(String delTrackigNumber) {
		String delTrackingNumbers = "delete from tracking_number where tracking_number like '"+delTrackigNumber+"'";
		DBUtilities.exUpdateQuery(delTrackingNumbers,"lms");
	}

	public void delCourier(String code) {
		String delTrackingNumbers = "delete from courier where code = '"+code+"'";
		DBUtilities.exUpdateQuery(delTrackingNumbers,"lms");
	}

	public void delRegion(String code) {
		String delRegion = "delete from region where code = '"+code+"'";
		DBUtilities.exUpdateQuery(delRegion,"lms");
	}

	public void delPincode(String pincode) {
		String delPincode = "delete from pincode where id = "+pincode;
		DBUtilities.exUpdateQuery(delPincode,"lms");
	}

	public void delReturn(String orderId) {
		String delReturn = "delete from return_shipment where order_id = '"+orderId+"'";
		DBUtilities.exUpdateQuery(delReturn,"lms");
	}

	/*public void delPickupAdditionInfo(String orderId) {
		String delPickupAdditionInfo = "delete from pickup_additional_info where pickup_id in(select id from pickup where order_id = "+orderId+")";
		DBUtilities.exUpdateQuery(delPickupAdditionInfo,"lms");
	}*/
	
	public void delml_shipment_tracking_detailForReturn(String orderLineId) {
		String delml_shipment_tracking_detailForReturn = "delete ml_shipment_tracking_detail from ml_shipment_tracking_detail inner join ml_try_and_buy_item on " +
				"ml_shipment_tracking_detail.tracking_number = ml_try_and_buy_item.return_tracking_number where ml_try_and_buy_item.source_item_reference_id = "+orderLineId;
		DBUtilities.exUpdateQuery(delml_shipment_tracking_detailForReturn,"lms");
	}

	public void delml_pickup_item(String orderLineId) {
		String delml_pickup_item = "delete from ml_pickup_item where ml_pickup_shipment_id = (select id from ml_shipment where tracking_number = " +
				"(select return_tracking_number from ml_try_and_buy_item where source_item_reference_id = "+orderLineId+"))";
		DBUtilities.exUpdateQuery(delml_pickup_item,"lms");
	}

	public void delml_pickup_shipment(String orderLineId) {
		String delml_pickup_shipment = "delete from ml_pickup_shipment where ml_shipment_id = (select id from ml_shipment where tracking_number = (select return_tracking_number " +
				"from ml_try_and_buy_item where source_item_reference_id = "+orderLineId+"))";
		DBUtilities.exUpdateQuery(delml_pickup_shipment,"lms");
	}

	public void delml_shipmentReturn(String orderLineId) {
		String delml_shipmentReturn = "delete ml_shipment_tracking_detail from ml_shipment_tracking_detail inner join ml_try_and_buy_item on " +
				"ml_shipment_tracking_detail.tracking_number = ml_try_and_buy_item.return_tracking_number where ml_try_and_buy_item.source_item_reference_id = "+orderLineId;
		DBUtilities.exUpdateQuery(delml_shipmentReturn,"lms");
	}
	

	public String getTrackingNumber(Object orderId){
		Map<String, Object> orderToShip = (HashMap<String, Object>) DBUtilities.exSelectQueryForSingleRecord("select tracking_number from order_to_ship where order_id = '"+orderId+"'", "lms");
		return orderToShip.get("tracking_number").toString();
	}

	@SuppressWarnings("rawtypes")
	public BiFunction getTripOrderAssignemntIdForOrder = (releaseId, tripId )-> DBUtilities.exSelectQueryForSingleRecord("select toa.id from trip_order_assignment toa where  toa.`trip_id` = "+tripId+" and toa.`order_id` = '"+releaseId+"' and toa.`trip_order_status` <> 'REM'","lms").get("id");

	@SuppressWarnings("rawtypes")
	public Function getTripOrderAssignemntIdForReturn = returnId -> DBUtilities.exSelectQueryForSingleRecord("select id from trip_order_assignment where source_return_id_fk = "+returnId,"lms").get("id");

	@SuppressWarnings("rawtypes")
	public Function getReturnsTrackingNumber = returnId ->  DBUtilities.exSelectQueryForSingleRecord("select tracking_number from return_shipment where source_return_id = "+returnId, "lms").get("tracking_number").toString();

	@SuppressWarnings("rawtypes")
	public Function getTripIdnNoForDC = (dcId)-> DBUtilities.exSelectQueryForSingleRecord("select tp.id, tp.trip_number from trip tp, trip_order_assignment toa where toa.`trip_id`= tp.id " +
			"and tp.`delivery_center_id` = "+dcId+" order by RAND() limit 1", "lms");

	public String getOrderReleaseStatus(Object orderReleaseId){
		Map<String, Object> dbStatus = DBUtilities.exSelectQueryForSingleRecord("select status_code from order_release where id = "+orderReleaseId, "oms");
		return dbStatus.get("status_code").toString();
	}
	
	public String getTODItemCodAmount(String itemId){
		Map<String, Object> item = DBUtilities.exSelectQueryForSingleRecord("select cod_amount from ml_try_and_buy_item where id = "+itemId, "lms");
		return item.get("cod_amount").toString();
	}

	public String getOrderToShipStatus(String orderId){
		Map<String, Object> status = DBUtilities.exSelectQueryForSingleRecord("select shipment_status from order_to_ship where order_id = '"+orderId+"'", "lms");
		return status.get("shipment_status").toString();
	}

	public String getOrderTrackingSTatus(long orderId){
		Map<String, Object> status = DBUtilities.exSelectQueryForSingleRecord("select shipment_status from order_tracking where order_id = '"+orderId+"'", "lms");
		return status.get("shipment_status").toString();
	}

	public String getMasterBagStatus(long masterBagId){
		Map<String, Object> status = DBUtilities.exSelectQueryForSingleRecord("select status from shipment where id = "+masterBagId, "lms");
		return status.get("status").toString();
	}

	public String getDCId(String orderId){
		Map<String, Object> status = DBUtilities.exSelectQueryForSingleRecord("select delivery_center_id from order_to_ship where order_id = '"+orderId+"'", "lms");
		return status.get("delivery_center_id").toString();
	}

	@SuppressWarnings("rawtypes")
	public Function getDHHubIdForWH = whId-> DBUtilities.exSelectQueryForSingleRecord("select h.id from dispatch_hub_warehouse_config dhc, hub h where dhc.`hub_code` = h.`code` and dhc.`warehouse_id` = "+whId,"lms").get("id");

	@SuppressWarnings("rawtypes")
	public Function getRTHubIdForWH = whId-> DBUtilities.exSelectQueryForSingleRecord("select h.id from return_hub_warehouse_config dhc, hub h where dhc.`hub_code` = h.`code` and dhc.`warehouse_id` = "+whId,"lms").get("id");

	@SuppressWarnings("rawtypes")
	public Function getCourierCodeOfRelease = orderId-> DBUtilities.exSelectQueryForSingleRecord("select courier_code from order_to_ship where order_id = '"+orderId +"'","lms").get("courier_code");

	public String getMLShipmentStatus(String orderId){
		Map<String, Object> status = DBUtilities.exSelectQueryForSingleRecord("select shipment_status from ml_shipment where source_reference_id =  '"+orderId+"'", "lms");
		return status.get("shipment_status").toString();
	}

	public String getMasterBagId(String orderId) {
		Map<String, Object> masterBag = DBUtilities.exSelectQueryForSingleRecord("select shipment_id from shipment_order_map where order_id = '" + orderId+"' limit 1", "lms");
		return masterBag.get("shipment_id").toString();

	}

	public long getTransporter(long warehouseId, long deliveryCenterId) {
		Map<String, Object> transporter = DBUtilities.exSelectQueryForSingleRecord("select tr.id from transporter tr, network_deliverycenter_assignment nd where tr.network_id = " +
				"nd.network_id and nd.`delivery_center_id`= "+deliveryCenterId+" and tr.`origin_premises_id`= "+warehouseId,"lms");
		return (Long) transporter.get("id");
	}

	
	
	
	
	
	
	
	

	@SuppressWarnings("unchecked")
	public String getStatusMLShipment(String orderId){
		
		String getStatusMLShipment = "select shipment_status from ml_shipment where source_reference_id = '"+orderId+"'";
		List<Map<String, Object>> TxQ = DBUtilities.exSelectQuery(getStatusMLShipment, "lms");
		Map<String, Object> txResult = TxQ.get(0);
		String result = (String) txResult.get("shipment_status");
		return result;
	}

	@SuppressWarnings("unchecked")
	public int getStatusMLShipmentTrackingDetails(String trackingNo, String status){
		
		String getStatusMLShipmentTrackingDetails = "select count(*) from ml_shipment_tracking_detail where tracking_number = '"+trackingNo+"' and activity_type = '"+status+"'";
		List<Map<String, Object>> TxQ = DBUtilities.exSelectQuery(getStatusMLShipmentTrackingDetails, "lms");
		Map<String, Object> txResult = TxQ.get(0);
		int result = (int) txResult.get("status");
		return result;
	}

	@SuppressWarnings("unchecked")
	public String getStatusOrderTracking(String orderId) throws SQLException{
		
		String getOrderTracking = "select shipment_status from order_tracking where order_id = '"+orderId+"'";
		List<Map<String, Object>> TxQ = DBUtilities.exSelectQuery(getOrderTracking, "lms");
		Map<String, Object> txResult = TxQ.get(0);
		String result = (String) txResult.get("shipment_status");
		return result;
	}

	public void delDelivery_center(String code){
		DBUtilities.exUpdateQuery("delete from location_hub_to_transport_hub_config where hub_code = '"+code+"'","myntra_tms");
		//DBUtilities.exUpdateQuery("delete from location_hub_to_transport_hub_config where hub_code = '"+code+"'","lms");
		DBUtilities.exUpdateQuery("delete from hub where code = '"+code+"'","myntra_tms");
		DBUtilities.exUpdateQuery("delete from hub where code = '"+code+"'","lms");
		DBUtilities.exUpdateQuery("delete from delivery_center where code = '"+code+"'","lms");
	}

	public void delhub(String code){
		//DBUtilities.exUpdateQuery("delete from location_hub_to_transport_hub_config where hub_code = '"+code+"'","lms");
		DBUtilities.exUpdateQuery("delete from tms_hub where code = '"+code+"'","lms");
		DBUtilities.exUpdateQuery("delete from hub where code = '"+code+"'","lms");
	}

	public void deleteLMSMLTnBReturn(String orderLineId){
		delml_shipment_tracking_detailForReturn(orderLineId);
		delml_pickup_item(orderLineId);
		delml_pickup_shipment(orderLineId);
		delml_shipmentReturn(orderLineId);
	}

	public void deleteLMSdataWihOrderIdTrackingNo(String orderId, String trackingNumber){
		delMl_shipmentWithReturnId(orderId);
//		delPickupAdditionInfo(orderId);
		delReturn(orderId);
		delTrip_order_assignment(trackingNumber);
		deOrder_tracking_detail.accept(orderId);
		delHubActivityDetail.accept(orderId);
		delOrder_tracking(orderId);
		delShipment_order_map(orderId);
		deltrynBuyshipment(orderId);
		deltShipentItem(orderId);
		delOrder_to_ship(orderId);
		delMl_shipment_tracking_detail(trackingNumber);
		delMl_last_mile_partner_shipment_assignment(trackingNumber);
		delML_delivery_shipment_item(orderId);
		delMl_delivery_shipment(orderId);
		delMl_shipment(orderId);
	}

	public void deleteOrderFromML(Object orderId, String trackingNumber){
		delMl_shipment_tracking_detail(trackingNumber);
		delMl_last_mile_partner_shipment_assignment(trackingNumber);
		delML_delivery_shipment_item(orderId.toString());
		delMl_delivery_shipment(orderId.toString());
		delMl_shipment(orderId.toString());
	}

	public void deleteLMSDataWithOrderId(String orderId){
		delHubActivityDetail.accept(orderId);
		deOrder_tracking_detail.accept(orderId);
		delShipment_order_map(orderId);
		delTrip_order_assignment(orderId);
		delOrder_tracking(orderId);
		delOrder_to_ship(orderId);
	}

	public void InsertOrder(String orderId, String mrp_total,
							String final_amount) throws SQLException {
		String orders = "INSERT INTO `orders` (`id`, `invoice_id`, `login`, `user_contact_no`, `customer_name`, `payment_method`, `status_code`, `coupon_code`, "
				+ "`cash_coupon_code`, `mrp_total`, `discount`, `cart_discount`, `coupon_discount`, `cash_redeemed`, `pg_discount`, `final_amount`, `shipping_charge`, "
				+ "`cod_charge`, `emi_charge`, `gift_charge`, `tax_amount`, `cashback_offered`, `request_server`, `response_server`, `is_on_hold`, `is_gift`, `notes`, "
				+ "`billing_address_id_fk`, `cancellation_reason_id_fk`, `on_hold_reason_id_fk`,`cancelled_on`, `created_by`,"
				+ " `version`, `order_type`, `loyalty_points_used`, `store_id`, `store_order_id`) VALUES("+ orderId+ ", NULL, 'shubhamguptaasd@gmail.com', '7875265650', "
				+ "'tryNbuy auto', 'cod', NULL, '', '', "+ mrp_total+ ", 0.0, 0.0, 0.0, 0.0, 0.0,"+ final_amount+ " , 0.0, 0.00, 0.00, 0.0, 0.0, 0.00, NULL, NULL, 0, 0, '',"+ " 29318814, NULL, NULL, NULL, 'shubhamguptaasd@gmail.com', 3, 'on', 0.0, 1, NULL)";
		DBUtilities.exUpdateQuery(orders, "oms");
	}

	public void InsertOrderRelease(String OrderReleaseId, String orderId,
								   String mrp_total, String final_amount, String wareHouseId, String trackingNumber) throws SQLException {
		long vectorSellerID = SellerConfig.getSellerID(SellerConfig.SellerName.ALFA);
		String orderRelease = "INSERT INTO `order_release` (`id`, `order_id_fk`, `login`, `status_code`, `payment_method`, `mrp_total`, `discount`, `cart_discount`, "
				+ "`coupon_discount`, `cash_redeemed`, `pg_discount`, `final_amount`, `shipping_charge`, `cod_charge`, `emi_charge`, `gift_charge`, `tax_amount`, "
				+ "`cashback_offered`, `receiver_name`, `address`, `city`, `locality`, `state`, `country`, `zipcode`, `mobile`, `email`, `courier_code`, `tracking_no`, "
				+ "`warehouse_id`, `is_refunded`, `cod_pay_status`, `cancellation_reason_id_fk`, `packed_on`, `shipped_on`, `delivered_on`, `completed_on`, "
				+ "`cancelled_on`, `created_by`, `version`, `is_on_hold`, `invoice_id`, `cheque_no`, `exchange_release_id`, "
				+ "`user_contact_no`, `shipping_method`, `on_hold_reason_id_fk`, `loyalty_points_used`, `store_id`, `store_release_id`,`seller_id`) VALUES ("+ OrderReleaseId+ ","+ orderId
				+ ", "+ "'shubhamguptaasd@gmail.com', 'PK', 'cod', "+ mrp_total+ ", 0.0, 0.0, 0.0, 0.0, 0.00, "+ final_amount+ ", 0.0,"+ " 0.00, 0.00, 0.0, 0.0, 0.00, 'tryNbuy auto ', "
				+ "'Myntra design, AKR tech park, Near kudlu gate', 'Bangalore', 'Bangalore', 'KA', 'India', '560068', '7875265650', 'shubhamguptaasd&#x40;gmail.com',"
				+ " 'ML', '"+trackingNumber+"', "+ wareHouseId+ ", 0, 'pending', NULL, NULL, NULL, NULL, NULL, NULL, 'shubhamguptaasd@gmail.com',"
				+ " 9, 0, NULL, NULL, NULL, '7875265650', 'NORMAL', NULL, 0.0, 1, NULL,"+vectorSellerID+")";
		DBUtilities.exUpdateQuery(orderRelease, "oms");
	}

	public void InsertOrderLine(String lineId, String orderId,
								String orderReleaseId, String styleId, String optionId,
								String skuId, String unitPrice, String quantity,
								String finalAmount, String SellerId)
			throws SQLException {
		String orderLine = "INSERT INTO `order_line` (`id`,`order_id_fk`, `order_release_id_fk`, `style_id`, `option_id`, `sku_id`, `status_code`, `unit_price`, `quantity`, "
				+ "`discounted_quantity`, `discount`, `cart_discount`, `cash_redeemed`, `coupon_discount`, `pg_discount`, `final_amount`, `tax_amount`, `tax_rate`, "
				+ "`cashback_offered`, `disocunt_rule_id`, `discount_rule_rev_id`, `promotion_id`, `is_discounted`, `is_returnable`, `cancellation_reason_id_fk`, `cancelled_on`, "
				+ "`created_by`, `version`, `exchange_orderline_id`, `loyalty_points_used`, `seller_id`, `supply_type`, `vendor_id`, "
				+ "`store_line_id`, `po_status`) VALUES("
				+ lineId
				+ ","
				+ orderId
				+ ","
				+ orderReleaseId
				+ ", "
				+ styleId
				+ ", "
				+ optionId
				+ ", "
				+ skuId
				+ ", 'A', "
				+ unitPrice
				+ ", "
				+ quantity
				+ ",0, 0.0, 0.0, 0.0, 0.0, 0.00,"
				+ finalAmount
				+ ",0.0, 0.0, 0.00, 0, 0, NULL, 0, 1, null, NULL, 'shubhamguptaasd@gmail.com', 3,null, 0.0, "
				+ SellerId+",'ON_HAND', NULL, NULL, 'UNUSED')";
		DBUtilities.exUpdateQuery(orderLine, "oms");
	}

	/*
	 * orderId ppsId
	 */
	public void InsertOrderAdditionalInfo(String orderId, String ppsId)
			throws SQLException {
		String orderAdditionalInfo = "INSERT INTO `order_additional_info` (`order_id_fk`, `key`, `value`, `created_by`, `version`) VALUES ("
				+ orderId
				+ ", "
				+ "'ORDER_PROCESSING_FLOW', 'OMS', 'pps-admin', 0), ("
				+ orderId
				+ ", 'CHANNEL', 'web', 'pps-admin',  0),("
				+ orderId
				+ ", 'LOYALTY_CONVERSION_FACTOR', '0.0', "
				+ "'pps-admin',  0),("
				+ orderId
				+ ", 'GIFT_CARD_AMOUNT', '0.0', 'pps-admin',  0),("
				+ orderId
				+ ", 'PAYMENT_PPS_ID', '"
				+ ppsId
				+ "', "
				+ "'pps-admin',  0),("
				+ orderId
				+ ", 'STORED_CREDIT_USAGE', '0.0', 'pps-admin', 0),("
				+ orderId
				+ ", 'EARNED_CREDIT_USAGE', '0.0', 'pps-admin', 0)";
		DBUtilities.exUpdateQuery(orderAdditionalInfo, "oms");
	}

	public void InsertOrderLineAdditionalInfo(String order_line_id_fk, String isTrynBuy, String ppsId)
			throws SQLException {
		String InsertOrderLineAdditionalInfo = "INSERT INTO `order_line_additional_info` (`order_line_id_fk`, `key`, `value`, `created_by`, `version`) VALUES"
				+ "("+order_line_id_fk+", 'FRAGILE', 'false', 'system', 0),"
				+ "("+order_line_id_fk+", 'HAZMAT', 'false', 'system',  0),"
				+ "("+order_line_id_fk+", 'JEWELLERY', 'false', 'system', 0),"
				+ "("+order_line_id_fk+", 'CUSTOMIZABLE', 'false', 'system', 0),"
				+ "("+order_line_id_fk+", 'CUSTOMIZED_MESSAGE', '', 'system', 0),"
				+ "("+order_line_id_fk+", 'PACKAGING_TYPE', 'NORMAL', 'system', 0),"
				+ "("+order_line_id_fk+", 'PACKAGING_STATUS', 'NOT_PACKAGED', 'system', 0),"
				+ "("+order_line_id_fk+", 'GIFT_CARD_AMOUNT', '0.0', 'system',  0),"
				+ "("+order_line_id_fk+", 'GOVT_TAX_RATE', '0.000', 'system', 0),"
				+ "("+order_line_id_fk+", 'GOVT_TAX_AMOUNT', '0.00', 'system', 0),"
				+ "("+order_line_id_fk+", 'IS_EXCHANGEABLE', 'false', 'system', 0),"
				+ "("+order_line_id_fk+", 'PAYMENT_PPS_ID', '"+ppsId+"', 'system', 0),"
				+ "("+order_line_id_fk+", 'TRY_AND_BUY', '"+isTrynBuy+"', 'system', 0),"
				+ "("+order_line_id_fk+", 'STORED_CREDIT_USAGE', '0.0', 'system', 0),"
				+ "("+order_line_id_fk+", 'EARNED_CREDIT_USAGE', '0.0', 'system', 0)";

		DBUtilities.exUpdateQuery(InsertOrderLineAdditionalInfo, "oms");
	}

	public void InsertPaymentPlan(String ppsId, String orderId,
								  String actionType, String amount)
			throws SQLException {
		String paymentPlan = "INSERT INTO `payment_plan` (`id`, `comments`, `updatedBy`, `updatedTimestamp`, `actionType`, `clientTransactionId`, `crmRefId`, `login`, "
				+ "`orderId`, `ppsType`, `sourceId`, `state`, `sessionId`, `cartContext`, `totalAmount`, `mobile`, `returnId`, `userAgent`, `clientIP`) "
				+ "VALUES('"
				+ ppsId
				+ "', 'PPS Plan created', 'SYSTEM', 1452168967721, '"
				+ actionType
				+ "', "
				+ "NULL, NULL, 'shubhamguptaasd@gmail.com', "
				+ orderId
				+ ", 'ORDER', 'bee790b6-4ee7-40a3-a26f-a01179feef29--s3', "
				+ "'PPFSM Order Taking done', 'JJN006f16b193f216e46ed87a3241ab780df9a1451887524M', 'DEFAULT', "
				+ amount
				+ ", NULL, null, "
				+ "'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.106 Safari/537.36', '27.251.249.86')";
		DBUtilities.exUpdateQuery(paymentPlan, "pps");
	}

	public void InsertPaymentPlanItem(String ppsId, String paymentPlanItemId,
									  String itemType, String pricePerUnit, String qty, String sellerId,
									  String skuId) throws SQLException {
		long vectorSellerID = SellerConfig.getSellerID(SellerConfig.SellerName.ALFA);
		sellerId = ""+vectorSellerID;
		String paymentPlanItem = "INSERT INTO `payment_plan_item` (`id`, `comments`, `updatedBy`, `updatedTimestamp`, `itemType`, `pricePerUnit`, `quantity`, "
				+ "`sellerId`, `skuId`, `pps_Id`) VALUES("
				+ paymentPlanItemId
				+ ", 'Payment Plan Item created', 'SYSTEM', 1452168967883, '"
				+ itemType
				+ "', "
				+ pricePerUnit
				+ ", "
				+ qty
				+ ", '"
				+ sellerId + "', '" + skuId + "', " + "'" + ppsId + "')";
		DBUtilities.exUpdateQuery(paymentPlanItem, "pps");
	}

	public void InsertPaymentPlanItemInstrument(String amount,
												String paymentPlanItemId) throws SQLException {
		String paymentPlanItemInstrument = "INSERT INTO `payment_plan_item_instrument` (`comments`, `updatedBy`, `updatedTimestamp`, `amount`, `paymentInstrumentType`, "
				+ "`ppsItemId`) VALUES('Payment Plan Item Instrument Detail created', 'SYSTEM', 1452168967895, "
				+ amount + ", 5, " + paymentPlanItemId + ")";
		DBUtilities.exUpdateQuery(paymentPlanItemInstrument, "pps");
	}

	public void InsertPaymentPlanInstrumentDetail(String ppsId,
												  String totalAmount, String actionType) throws SQLException {
		String paymentPlanInstrumentDetail = "INSERT INTO `payment_plan_instrument_details` (`comments`, `updatedBy`, `updatedTimestamp`, `paymentInstrumentType`, "
				+ "`totalPrice`, `pps_Id`, `paymentPlanExecutionStatus_id`, `actionType`, `parentInstrumentDetailId`) VALUES('PPS Plan Instrument Details created', "
				+ "'SYSTEM', 1452168967889, 5, "
				+ totalAmount
				+ ", '"
				+ ppsId
				+ "', 17931, NULL, NULL)";
		DBUtilities.exUpdateQuery(paymentPlanInstrumentDetail, "pps");
	}


	public void delRecords(String ppsId, String paymentPlanItemId) throws SQLException{
		SellerPaymentServiceHelper sellerPaymentServiceHelper = new SellerPaymentServiceHelper();
		sellerPaymentServiceHelper.deleteFromPaymentPlanItemInstrument(paymentPlanItemId);
		sellerPaymentServiceHelper.deleteFromPaymentPlanItem(ppsId);
		sellerPaymentServiceHelper.deleteFromPaymentPlanInstrumentDetail(ppsId);
		sellerPaymentServiceHelper.deleteFromPaymentPlan(ppsId);
	}

	public void deleteFromPaymentPlanItemInstrument(String paymentPlanItemId) throws SQLException{
		SellerPaymentServiceHelper sellerPaymentServiceHelper = new SellerPaymentServiceHelper();
		sellerPaymentServiceHelper.deleteFromPaymentPlanItemInstrument(paymentPlanItemId);
	}

	public void delRecordsfromOmsLlms(String orderId, String trackingNumber) throws SQLException{
		OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
		LMSHelper lmsHepler = new LMSHelper();
		lmsHepler.deltry_and_buy_shipment_item.accept(orderId);
		lmsHepler.deltry_and_buy_shipment.accept(orderId);
		lmsHepler.deleteLMSdataWihOrderIdTrackingNo(orderId, trackingNumber);
		omsServiceHelper.deleteOMSDBEntriesForOrderID(orderId);
	}


	public String flushLMSAndUpdateData(){
		String del1 = "delete from delivery_center where courier_code like 'DAD7'";
		String del2 = "delete from courier_serviceability where courier_code like 'DAD7'";
		String del3 = "delete from courier_additional_info where courier_id = (select id from courier where code like 'DAD7')";
		String del4 = "delete from courier_warehouse_map where courier_id= (select id from courier where code like 'DAD7')";
		String del5 = "delete from courier where code like 'DAD7'";
		String del6 = "delete from delivery_center where code like 'DAD7'";
		String del7 = "delete from delivery_staff_route_map where delivery_staff_id = (select id from delivery_staff where code  = 'DAD7')";
		String del8 = "delete from delivery_staff_location where delivery_staff_id  = (select id from delivery_staff where code = 'DAD7')";
		String del9 = "delete from delivery_staff where code  like 'DAD7'";
		String del10 = "delete from pincode where region_code like 'DAD7'";
		String del11 = "delete from courier_warehouse_map where region_code like 'DAD7'";
		String del12= "delete from courier_warehouse_map_v2 where region_code like 'DAD7'";
		String del13 = "delete from courier_preference_v2 where region_code like 'DAD7'";
		String del14 = "delete from region where code like 'DAD7'";
		String del15 = "delete from courier_warehouse_map where region_code like 'DAD7'";
		String del16 = "delete from region where code like 'DAD7'";
		String del17 = "delete from courier_warehouse_map where region_code like 'DAD7'";
		String del18 = "delete from region where code like 'DAD7'";
		String del19 = "delete from courier_serviceability where courier_code='DAD7'";
		String del20 = "delete from tat_data where destination_zipcode=100000 and source_warehouse_id=1 and courier_code='BD' and tat_time_in_hours='72'";
		String del21 = "delete from tat_data where destination_zipcode=100000 and source_warehouse_id=1 and courier_code='ML' and tat_time_in_hours='72'";
		String del22 = "delete from tat_data where destination_zipcode=412114 and source_warehouse_id=1 and courier_code='ML' and tat_time_in_hours='72'";
		String del23 = "delete from tat_data where destination_zipcode=412114 and source_warehouse_id=2 and courier_code='ML' and tat_time_in_hours='72'";
		String del24 = "delete from delivery_center where code='MADC'";
		String del25 = "update trip set trip_status='COMPLETED' where delivery_staff_id=17 and delivery_center_id=1";
		String del26 = "delete from courier_capacity where created_on >  (CURDATE() - INTERVAL 1 DAY) and warehouse_id=1  and courier_code='ML' and region_code='BLRP'";
		String del27 = "INSERT INTO `courier_capacity` (`warehouse_id`, `region_code`, `courier_code`, `consumed_count`, `created_on`, `last_modified_on`, `version`, `created_by`,`calculation_date`, `name`, `shipping_method`)VALUES( 1, 'BLRP', 'ML', 0, now(), now(), 1, 'system', (CURDATE() + INTERVAL 1 DAY), 'ML-1-BLRP', 'EXPRESS')";
		DBUtilities.exUpdateQuery(del1,"lms");
		DBUtilities.exUpdateQuery(del2,"lms");
		DBUtilities.exUpdateQuery(del3,"lms");
		DBUtilities.exUpdateQuery(del4,"lms");
		DBUtilities.exUpdateQuery(del5,"lms");
		DBUtilities.exUpdateQuery(del6,"lms");
		DBUtilities.exUpdateQuery(del7,"lms");
		DBUtilities.exUpdateQuery(del8,"lms");
		DBUtilities.exUpdateQuery(del9,"lms");
		DBUtilities.exUpdateQuery(del10,"lms");
		DBUtilities.exUpdateQuery(del11,"lms");
		DBUtilities.exUpdateQuery(del12,"lms");
		DBUtilities.exUpdateQuery(del13,"lms");
		DBUtilities.exUpdateQuery(del14,"lms");
		DBUtilities.exUpdateQuery(del15,"lms");
		DBUtilities.exUpdateQuery(del16,"lms");
		DBUtilities.exUpdateQuery(del17,"lms");
		DBUtilities.exUpdateQuery(del18,"lms");
		DBUtilities.exUpdateQuery(del19,"lms");
		DBUtilities.exUpdateQuery(del20,"lms");
		DBUtilities.exUpdateQuery(del21,"lms");
		DBUtilities.exUpdateQuery(del22,"lms");
		DBUtilities.exUpdateQuery(del23,"lms");
		DBUtilities.exUpdateQuery(del24,"lms");
		DBUtilities.exUpdateQuery(del25,"lms");
		DBUtilities.exUpdateQuery(del26,"lms");
		DBUtilities.exUpdateQuery(del27,"lms");
		return "Query exuecuted successfully";
	}

	public void singleSkuOrder(String lineId, String orderId, String orderReleaseId, String ppsId, String paymentPlanItemId, String trackingNumber, String isTryNBuy)
			throws SQLException, JAXBException, JsonGenerationException, JsonMappingException, IOException {
		LmsServiceHelper lmsServiceHelper = new LmsServiceHelper();
		long vectorSellerID = SellerConfig.getSellerID(SellerConfig.SellerName.ALFA);
		deleteLMSMLTnBReturn(lineId);
		delRecordsfromOmsLlms(orderId, trackingNumber);
		delRecords(ppsId, paymentPlanItemId);
		InsertOrder(orderId, "1099", "1099");
		InsertOrderLine(lineId, orderId, orderReleaseId, "1527", "3827", "3827", "1099", "1", "1099", ""+vectorSellerID);
		InsertOrderRelease(orderReleaseId, orderId, "1099", "1099", "1", trackingNumber);
		InsertOrderLineAdditionalInfo(lineId, isTryNBuy, ppsId);
		InsertOrderAdditionalInfo(orderId, ppsId);
		InsertPaymentPlan(ppsId, orderId, "SALE", "109900");
		InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU","109900" , "1", ""+vectorSellerID, "3827");
		InsertPaymentPlanItemInstrument("109900", paymentPlanItemId);
		InsertPaymentPlanInstrumentDetail(ppsId, "109900", null);
		String message = lmsServiceHelper.getOrderRelease(orderReleaseId);
		log.info("Message: "+message);
		SystemConfigProvider.getTopology().getQueueTemplate("createOrUpdateForwardShipmentQueue").convertAndSend(new String(message));
		log.info("Message Pushed");
	}

	public void singleSkuMultiQtyOrder(String lineId, String orderId, String orderReleaseId, String ppsId, String paymentPlanItemId, String trackingNumber, String isTryNBuy)
			throws SQLException, JAXBException, JsonGenerationException, JsonMappingException, IOException {
		LmsServiceHelper lmsServiceHelper = new LmsServiceHelper();
		long vectorSellerID = SellerConfig.getSellerID(SellerConfig.SellerName.ALFA);
		deleteLMSMLTnBReturn(lineId);
		delRecordsfromOmsLlms(orderId, trackingNumber);
		delRecords(ppsId, paymentPlanItemId);
		InsertOrder(orderId, "1099", "1099");
		InsertOrderLine(lineId, orderId, orderReleaseId, "1527", "3827", "3827", "2397", "3", "2397", ""+vectorSellerID);
		InsertOrderRelease(orderReleaseId, orderId, "2397", "2397", "1", trackingNumber);
		InsertOrderLineAdditionalInfo(lineId, isTryNBuy, ppsId);
		InsertOrderAdditionalInfo(orderId, ppsId);
		InsertPaymentPlan(ppsId, orderId, "SALE", "239700");
		InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU","239700" , "3", ""+vectorSellerID, "3827");
		InsertPaymentPlanItemInstrument("239700", paymentPlanItemId);
		InsertPaymentPlanInstrumentDetail(ppsId, "239700", null);
		String message = lmsServiceHelper.getOrderRelease(orderReleaseId);
		log.info("Message: "+message);
		SystemConfigProvider.getTopology().getQueueTemplate("createOrUpdateForwardShipmentQueue").convertAndSend(new String(message));
		log.info("Message Pushed");
	}

	public void multiSkuMultiQtyOrder(String lineId,
									  String lineId1, String lineId2, String orderId, String orderReleaseId, String ppsId,
									  String paymentPlanItemId, String paymentPlanItemId1, String paymentPlanItemId2, String trackingNumber)
			throws SQLException, JAXBException, JsonGenerationException, JsonMappingException, IOException {
		LmsServiceHelper lmsServiceHelper = new LmsServiceHelper();
		long vectorSellerID = SellerConfig.getSellerID(SellerConfig.SellerName.ALFA);
		deleteLMSMLTnBReturn(lineId);
		deleteLMSMLTnBReturn(lineId1);
		deleteLMSMLTnBReturn(lineId2);
		delRecordsfromOmsLlms(orderId, trackingNumber);
		deleteFromPaymentPlanItemInstrument(paymentPlanItemId1);
		deleteFromPaymentPlanItemInstrument(paymentPlanItemId2);
		delRecords(ppsId, paymentPlanItemId);
		InsertOrder(orderId, "3297", "3297");
		InsertOrderLine(lineId, orderId, orderReleaseId, "1531", "3831", "3831", "799", "1", "799", ""+vectorSellerID);
		InsertOrderLine(lineId1, orderId, orderReleaseId, "1535", "3847","3847", "699", "1", "699", ""+vectorSellerID);
		InsertOrderLine(lineId2, orderId, orderReleaseId, "1538", "3856", "3856", "1799", "1", "1799", ""+vectorSellerID);
		InsertOrderRelease(orderReleaseId, orderId, "3297", "3297", "1", trackingNumber);
		InsertOrderLineAdditionalInfo(lineId, "true", ppsId);
		InsertOrderLineAdditionalInfo(lineId1, "true", ppsId);
		InsertOrderLineAdditionalInfo(lineId2, "true", ppsId);
		InsertOrderAdditionalInfo(orderId, ppsId);
		InsertPaymentPlan(ppsId, orderId, "SALE", "329700");
		InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "79900", "1", ""+vectorSellerID, "3831");
		InsertPaymentPlanItem(ppsId, paymentPlanItemId1, "SKU","69900" , "1", ""+vectorSellerID, "3847");
		InsertPaymentPlanItem(ppsId, paymentPlanItemId2, "SKU", "179900", "1", ""+vectorSellerID, "3856");
		InsertPaymentPlanItemInstrument("79900", paymentPlanItemId);
		InsertPaymentPlanItemInstrument("69900", paymentPlanItemId1);
		InsertPaymentPlanItemInstrument("179900", paymentPlanItemId2);
		InsertPaymentPlanInstrumentDetail(ppsId, "329700", null);
		String message = lmsServiceHelper.getOrderRelease(orderReleaseId);
		log.info("Message: "+message);
		SystemConfigProvider.getTopology().getQueueTemplate("createOrUpdateForwardShipmentQueue").convertAndSend(new String(message));
		log.info("Message Pushed");
	}

	/**
	 * Place Order with OrderId's 70023010/11
	 * @param orderId
	 * @param isTryNBuy
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws SQLException
	 * @throws JAXBException
	 * @throws IOException
	 */
	public void createOrderWithSingleSku(String orderId, String isTryNBuy) throws JsonGenerationException, JsonMappingException, SQLException, JAXBException, IOException{
		switch (orderId) {
			case "70023010": {singleSkuOrder("52661724", "70023010", "70023010", "2c22e2d0-793e-40f8-89c4-c504783ae693", "500000", "ML000010000", isTryNBuy);
				break;
			}
			case "70023011": {singleSkuOrder("52661729", "70023011", "70023011", "3x98e2d0-980x-36g6-89c4-h974794ae894", "500001", "ML000010001", isTryNBuy);
				break;
			}
			default: log.info("Please enter the correct orderId which belongs to this section");
		}

	}

	/**
	 * Place Order with OrderId's 70023012/13/14
	 * @param orderId
	 * @param isTryNBuy
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws SQLException
	 * @throws JAXBException
	 * @throws IOException
	 */
	public void createOrderWithSingleSkuMutiQty(String orderId, String isTryNBuy) throws JsonGenerationException, JsonMappingException, SQLException, JAXBException, IOException{
		switch (orderId) {
			case "70023012": {singleSkuMultiQtyOrder("52661730", "70023012", "70023012", "9c57e2d0-764e-27h9-23x4-u808723rg693", "500002", "ML000010002", isTryNBuy);
				break;
			}
			case "70023013": {singleSkuMultiQtyOrder("52661731", "70023013", "70023013", "0c25f2i0-262d-75b9-19f4-u996283rg693", "500003", "ML000010003", isTryNBuy);
				break;
			}
			case "70023014": {singleSkuMultiQtyOrder("52661732", "70023014", "70023014", "9c87e2f0-092s-99a9-92v4-o800833wg631", "500004", "ML000010004", isTryNBuy);
				break;
			}
			default: log.info("Please enter the correct orderId which belongs to this section");

		}

	}

	/**
	 * Place Order with OrderId's 70023015/16/20...24
	 * @param orderId
	 * @param isTryNBuy
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws SQLException
	 * @throws JAXBException
	 * @throws IOException
	 */
	public void createOrderWithMultiSkuMutiQty(String orderId, String isTryNBuy) throws JsonGenerationException, JsonMappingException, SQLException, JAXBException, IOException{
		switch (orderId) {
			case "70023015": { multiSkuMultiQtyOrder("52661733", "52661734", "52661735", "70023015", "70023015", "0y87t2d1-937e-27h9-23x4-u982682rg693", "500005", "500006", "500007", "ML000010005");
				break;
			}
			case "70023016": { multiSkuMultiQtyOrder("52661736", "52661737", "52661738", "70023016", "70023016", "1e34h2d7-296r-27h9-23x4-u682929rg693", "500008", "500009", "500010", "ML000010006");
				break;
			}
			case "70023020": { multiSkuMultiQtyOrder("52661739", "52661740", "52661741", "70023020", "70023020", "2t92d2d6-292f-27h9-23x4-u952895rg693", "500011", "500012", "500013", "ML000010007");
				break;
			}
			case "70023021": { multiSkuMultiQtyOrder("52661742", "52661743", "52661744", "70023021", "70023021", "3w23b2d3-992q-27h9-23x4-u124685rg693", "500014", "500015", "500016", "ML000010008");
				break;
			}
			case "70023022": { multiSkuMultiQtyOrder("52661745", "52661746", "52661747", "70023022", "70023022", "4i27z2d9-993y-27h9-23x4-u346854rg693", "500017", "500018", "500019", "ML000010009");
				break;
			}
			case "70023023": { multiSkuMultiQtyOrder("52661748", "52661749", "52661750", "70023023", "70023023", "5q49j2d0-001e-27h9-23x4-u937841rg693", "500020", "500021", "500022", "ML000010010");
				break;
			}
			case "70023024": { multiSkuMultiQtyOrder("52661751", "52661752", "52661753", "70023024", "70023024", "6p28k2d2-539i-27h9-23x4-u965386rg693", "500023", "500024", "500025", "ML000010011");
				break;
			}
			default: log.info("Please enter the correct orderId which belongs to this section");
		}
	}

	public String getRtoWarehouseFromOrderToShip(Object orderId){
		HashMap<String, Object> orderToShip = (HashMap<String, Object>) DBUtilities.exSelectQueryForSingleRecord("select rto_warehouse_id from order_to_ship where order_id = "+orderId, "lms");
		return orderToShip.get("rto_warehouse_id").toString();
	}

	/*public String getRtWarehouseFromReturn(long return_id){
		Map<String, Object> returnShipmnet = DBUtilities.exSelectQueryForSingleRecord("select return_warehouse_id from return_shipment where source_return_id = "+return_id, "lms");
		return returnShipmnet.get("return_warehouse_id").toString();
	}*/

	
	@SuppressWarnings("rawtypes")
	public Function getRtWarehouseFromReturn = return_id -> DBUtilities.exSelectQueryForSingleRecord
			("select return_warehouse_id from return_shipment where source_return_id = "+return_id, "lms").get("return_warehouse_id").toString();

	public long getMasterBagTrackingCount(long masterBagId){
		Map<String, Object> masterBagTracking = DBUtilities.exSelectQueryForSingleRecord("select count(*) from `master_bag_tracking_detail` where master_bag_id = "+masterBagId,"lms");
		return (Long) masterBagTracking.get("count(*)");
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public LambdaInterfaces.Consumer createOrderValidationsForPayments = releaeId ->{
		LmsServiceHelper lmsServiceHelper = new LmsServiceHelper();
		OrderResponse response = (OrderResponse)lmsServiceHelper.getOrderLMS.apply(releaeId.toString());
		List<ShipmentItemEntry> sku3872 = response.getOrders().get(0).getShipmentItemEntries().stream().filter(x -> x.getSkuId().equals("3872")).collect(Collectors.toList());
		List<ShipmentItemEntry> sku3831 = response.getOrders().get(0).getShipmentItemEntries().stream().filter(x -> x.getSkuId().equals("3872")).collect(Collectors.toList());
		ExceptionHandler.handleEquals(response.getOrders().get(0).getCodAmount(), "0.0","cod amount mismatch");
		ExceptionHandler.handleEquals(Optional.ofNullable(response.getOrders().get(0).getCod()), false);
		ExceptionHandler.handleEquals(sku3872.get(0).getItemValue(),2603.22);
		ExceptionHandler.handleEquals(sku3872.get(0).getItemMRP(),3499.00);
		ExceptionHandler.handleEquals(sku3872.get(0).getCodAmount(),0.00);
		ExceptionHandler.handleEquals(sku3872.get(0).getTaxAmountPaid(),412.10);
		ExceptionHandler.handleEquals(sku3872.get(0).getAdditionalCharges(), 0.00);
		ExceptionHandler.handleEquals(sku3872.get(0).getValueAddedTax(), 412.10);

		ExceptionHandler.handleEquals(sku3831.get(0).getItemValue(),494.61);
		ExceptionHandler.handleEquals(sku3831.get(0).getItemMRP(),799.00);
		ExceptionHandler.handleEquals(sku3831.get(0).getCodAmount(),0.00);
		ExceptionHandler.handleEquals(sku3831.get(0).getTaxAmountPaid(),608.29);
		ExceptionHandler.handleEquals(sku3831.get(0).getAdditionalCharges(), 25.00);
		ExceptionHandler.handleEquals(sku3831.get(0).getValueAddedTax(), 2.920);
	};
	
	public Double getFinalAmount(PacketEntry packetEntry) {

		Double finalAmount = 0.0;
		List<OrderLineEntry> orderLines = packetEntry.getOrderLines();

		for (OrderLineEntry orderLineEntry : orderLines) {

			finalAmount = finalAmount + orderLineEntry.getFinalAmount();
		}
		return finalAmount;
	}

	@SuppressWarnings({ "unchecked" })
	public void createOrderInLMSValidation(String orderId, String isTod) throws JAXBException, IOException, ManagerException, InterruptedException {
		OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
		LmsServiceHelper lmsServiceHelper = new LmsServiceHelper();
		String orderReleaseId = omsServiceHelper.getPacketId(orderId);
		PacketEntry packetEntry = omsServiceHelper.getPacketEntry(orderReleaseId);
		OrderResponse lmsOrderEnrty = lmsServiceHelper.getLmsOrders(orderReleaseId);
		String shipmentType = "DL";
		
		if(isTod.equalsIgnoreCase("true")){
			
			shipmentType = "TRY_AND_BUY";
		}
		
		Map<String, Object> orderToShip = (Map<String, Object>) DBUtilities.exSelectQueryForSingleRecord("select * from order_to_ship where order_id = '"+orderReleaseId+"'", "lms");
		log.info(orderToShip);
		Double finalAmount = getFinalAmount(packetEntry);
		
		if(packetEntry.getPaymentMethod().toString().equalsIgnoreCase("cod")) {
			
			ExceptionHandler.handleEquals(lmsOrderEnrty.getOrders().get(0).getCodAmount().toString(), finalAmount.toString(), "COD amount mismatch in order_to_ship");
		} else {
			
			ExceptionHandler.handleNotNull(orderToShip.get("cod_amount").toString(), "COD amount mismatch in order_to_ship");
		}
		
		ExceptionHandler.handleEquals(orderToShip.get("mobile").toString(), packetEntry.getMobile().toString(), "Mobile number is wronge in order_to_ship");
		ExceptionHandler.handleNotNull(orderToShip.get("address").toString(), "address is null in order_to_ship");
		ExceptionHandler.handleEquals(orderToShip.get("city").toString(), packetEntry.getCity().toString(), "City name is not eqauls to the city in the order_to_ship");
		ExceptionHandler.handleNotNull(orderToShip.get("email").toString(), "Email not found in order_to_ship");
		ExceptionHandler.handleNotNull(orderToShip.get("first_name").toString(),"First name is missing in order_to_ship");
		ExceptionHandler.handleNotNull(orderToShip.get("state").toString(),"State name is null in order_to_ship");
		ExceptionHandler.handleEquals(orderToShip.get("zipcode").toString(), packetEntry.getZipcode().toString(), "Pin code is not 560068(same)");
		ExceptionHandler.handleEquals(orderToShip.get("status").toString(),"IS", "Status is not equal in order_to_ship");
		if (packetEntry.getShippingMethod().toString().equals(EnumSCM.XPRESS)) ExceptionHandler.handleEquals(orderToShip.get("shipping_method").toString(), EnumSCM.EXPRESS, "Shipping method is not same in order_to_ship");
		else {
			
			if(orderToShip.get("shipping_method").toString().equals("SAME DAY DELIVERY"))
			ExceptionHandler.handleEquals(EnumSCM.SDD, packetEntry.getShippingMethod().toString(), "Shipping method is not same in order_to_ship");
		}
		ExceptionHandler.handleEquals(orderToShip.get("courier_code").toString(), packetEntry.getCourierCode().toString(), "Courier code is not same in order_to_ship");
		ExceptionHandler.handleEquals(orderToShip.get("tracking_number").toString(),packetEntry.getTrackingNo().toString(), "tracking number is not same in order_to_ship");
		ExceptionHandler.handleNotNull(orderToShip.get("rto_warehouse_id").toString(), "RTO warehouse id is null in order_to_ship table");
		ExceptionHandler.handleEquals(orderToShip.get("shipment_status").toString(),EnumSCM.INSCANNED, "Shipment status is not 'INSCANNED'");

		ExceptionHandler.handleEquals(orderToShip.get("shipment_type").toString(), shipmentType, "Shipment type mismatch in order_to_ship");

		List<Map<String, Object>> shipmentItem = DBUtilities.exSelectQuery("select * from shipment_item where order_to_ship_id = (select id from order_to_ship where order_id = "+orderReleaseId+")", "lms");
		
		for(Map<?, ?> item : shipmentItem){
		/*shipmentItem.forEach(item->{ */ExceptionHandler.handleNotNull(item.get("source_item_reference_id"));
			ExceptionHandler.handleNotNull(item.get("style_id"));
			ExceptionHandler.handleNotNull(item.get("sku_id"));
			ExceptionHandler.handleNotNull(item.get("item_description"));
			ExceptionHandler.handleNotNull(item.get("item_value"));
			ExceptionHandler.handleNotNull(item.get("item_mrp"));
			ExceptionHandler.handleNotNull(item.get("cod_amount"));
			ExceptionHandler.handleNotNull(item.get("seller_id"));
			ExceptionHandler.handleNotNull(item.get("invoice_id"));
			ExceptionHandler.handleNotNull(item.get("seller_cstn"));
			ExceptionHandler.handleNotNull(item.get("goods_and_service_tax_identification_number"));
			if (!item.get("central_goods_and_service_tax").equals(null)) {
				ExceptionHandler.handleNotNull(item.get("central_goods_and_service_tax"));
				ExceptionHandler.handleNotNull(item.get("state_goods_and_service_tax"));
			}else ExceptionHandler.handleNotNull(item.get("integrated_goods_and_service_tax"));
		}

		Map<String, Object> orderTracking = (Map<String, Object>) DBUtilities.exSelectQueryForSingleRecord("select * from order_tracking where order_id = "+orderReleaseId, "lms");
		ExceptionHandler.handleEquals(orderTracking.get("courier_code").toString(), packetEntry.getCourierCode().toString(), "Courier code is not same in order_tracking");
		ExceptionHandler.handleEquals(orderTracking.get("delivery_status").toString(), "FIT", "delivery status is not FIT in order_tracking");
		ExceptionHandler.handleEquals(orderTracking.get("shipment_type").toString(),shipmentType,"ShipmentType is not same in order_tracking");
		ExceptionHandler.handleEquals(orderTracking.get("tracking_no").toString(), packetEntry.getTrackingNo().toString(), "tracking number is not same in order_tracking");
		ExceptionHandler.handleNotNull(orderTracking.get("courier_creation_status").toString(),"courier creation status is ACCEPTED");
		ExceptionHandler.handleEquals(orderTracking.get("shipment_status").toString(),"INSCANNED", "Shipment status is not 'PACKED'");

		Map<String, Object> orderAdditionInfo = (Map<String, Object>) DBUtilities.exSelectQueryForSingleRecord("select * from order_additional_info where id = (select order_additional_info_id from order_to_ship where order_id = "+orderReleaseId+")", "lms");

		ExceptionHandler.handleNotNull(orderAdditionInfo.get("total").toString(), "No total Info in order_additional_info");
		ExceptionHandler.handleNotNull(orderAdditionInfo.get("subtotal").toString(), "No subtotal Info in order_additional_info");

	    Map<String, Object> hubActivityDetail = (Map<String, Object>) DBUtilities.exSelectQueryForSingleRecord("select * from hub_activity_detail where shipment_id = "+orderReleaseId+" and activity_type = 'INSCAN'", "lms");
		ExceptionHandler.handleNotNull(hubActivityDetail.get("location").toString(),"Location is null in hub_activity_detail");
		ExceptionHandler.handleEquals(hubActivityDetail.get("from_status").toString(), "PACKED", "from status is not 'Packed' in hub_activity_detail");//PACKED
		ExceptionHandler.handleEquals(hubActivityDetail.get("to_status").toString(),"INSCANNED", "to_status is not 'INSCANNED' in hub_activity_detail");
		ExceptionHandler.handleEquals(hubActivityDetail.get("activity_result").toString(),EnumSCM.SUCCESS, "activity_type is not 'SUCCESS' in hub_activity_detail'");

		Map<String, Object> mlShipment = (Map<String, Object>) DBUtilities.exSelectQueryForSingleRecord("select * from ml_shipment where source_reference_id = "+orderReleaseId, "lms");
		if(packetEntry.getPaymentMethod().toString().equalsIgnoreCase("cod")){
			
			ExceptionHandler.handleEquals(lmsOrderEnrty.getOrders().get(0).getCodAmount().toString(), finalAmount.toString(), "COD amount mismatch in order_to_ship");
		} else ExceptionHandler.handleNotNull(mlShipment.get("shipment_value").toString(), "shipment value is not correct in ml_shipment");
		String shippingMethod = packetEntry.getShippingMethod().toString();
		if (shippingMethod.equals("XPRESS")) shippingMethod = "EXPRESS";
		ExceptionHandler.handleEquals(mlShipment.get("recipient_contact_number").toString(), packetEntry.getMobile().toString(), "contact number mismath in ml_shipment");//not null
		ExceptionHandler.handleNotNull(mlShipment.get("recipient_name").toString(),"recipient_name is null in ml_shipment");
		ExceptionHandler.handleNotNull(mlShipment.get("address").toString(),"Address is null in ml_shipment");
		ExceptionHandler.handleEquals(mlShipment.get("city").toString(),packetEntry.getCity().toString(),"City is not same in ml_shipment");
		ExceptionHandler.handleEquals(mlShipment.get("pincode").toString(), packetEntry.getZipcode().toString(), "Pincode is not correct in ml_shipment");
		ExceptionHandler.handleEquals(mlShipment.get("recipient_contact_number").toString(),packetEntry.getMobile().toString(), "contact number mismatch in ml_shipment");
		ExceptionHandler.handleEquals(mlShipment.get("shipment_status").toString(),"EXPECTED_IN_DC", "Shipment Status is not 'EXPECT_IN_DC' in ml_shipment");
		ExceptionHandler.handleNotNull(mlShipment.get("email").toString(), "email info is null in ml_shipment");
		ExceptionHandler.handleEquals(mlShipment.get("delivery_center_id").toString(), "5","Delivery_center_id mismatch in ml_shipment");
		
		if(mlShipment.get("shipping_method").toString().equals("SAME DAY DELIVERY")) {
			
			ExceptionHandler.handleEquals(mlShipment.get("shipping_method").toString(), shippingMethod, "shipping_method mismatch in ml_shipment");
		}
		
		ExceptionHandler.handleEquals(mlShipment.get("tracking_number").toString(), packetEntry.getTrackingNo().toString(), "Tracking number mismatch in ml_shipment");
		ExceptionHandler.handleEquals(mlShipment.get("shipment_type").toString(), shipmentType, "Shipment_type mismatch in ml_shipment");
		ExceptionHandler.handleNotNull(mlShipment.get("promise_date").toString(),"Promise_date is null in ml_shipment");
	}

	public void createOrderReturnInLMSValidation(String orderId,String returnId, String pincode)throws ManagerException{
		Map<String, Object> returnShipment = DBUtilities.exSelectQueryForSingleRecord("select * from return_shipment where source_return_id = "+returnId, "lms");
		ExceptionHandler.handleEquals(returnShipment.get("order_id").toString(), orderId, "orderId not found in Pickup table");
		ExceptionHandler.handleNotNull(returnShipment.get("address").toString(), "address is null in Pickup");
		ExceptionHandler.handleNotNull(returnShipment.get("city").toString(), "city is null in Pickup");
		ExceptionHandler.handleNotNull(returnShipment.get("country").toString(), "country is null in Pickup");
		ExceptionHandler.handleNotNull(returnShipment.get("email").toString(), "email is null in Pickup");
		ExceptionHandler.handleNotNull(returnShipment.get("customer_name").toString(), "customer_name is null in Pickup");
		ExceptionHandler.handleEquals(returnShipment.get("primary_contact_number").toString(), "1234567890", "primary_contact_number mismatch in Pickup");
		ExceptionHandler.handleEquals(returnShipment.get("state_code").toString(), "KA", "State mismatch in Pickup");
		ExceptionHandler.handleEquals(returnShipment.get("pincode").toString(), pincode, "Pincode mismatch in Pickup");
		ExceptionHandler.handleNotNull(returnShipment.get("status").toString(), "Status is null in Pickup");
		ExceptionHandler.handleEquals(returnShipment.get("delivery_center_id").toString(), "5", "Delivery center id mismatch in Pickup");
//		ExceptionHandler.handleNotNull(returnShipment.get("item_description").toString(), "item description is null in Pickup");
		ExceptionHandler.handleNotNull(returnShipment.get("return_warehouse_id").toString(), "destination warehouse id is null in Pickup");
		ExceptionHandler.handleEquals(returnShipment.get("courier_code").toString(), "ML","courier_code mismatch in Pickup");
//		ExceptionHandler.handleNotNull(returnShipment.get("style_ids").toString(), "style_ids is null in Pickup");
		ExceptionHandler.handleEquals(returnShipment.get("shipment_status").toString(), "PICKUP_CREATED", "shipment status is not PICKUP_CREATED in Pickup");
		ExceptionHandler.handleNotNull(returnShipment.get("tracking_number").toString(), "tracking_number is null in Pickup");
		ExceptionHandler.handleNotNull(returnShipment.get("shipment_value").toString(),"Shipment value is coming as null");
	}

	public void orderTrackingValidationDL(Object orderId, String shipmentType, String trackingNumber)throws ManagerException{
		Map<String, Object> orderTracking = (HashMap<String, Object>) DBUtilities.exSelectQueryForSingleRecord("select * from order_tracking where order_id = "+orderId+" and shipment_type = '"+shipmentType+"'", "lms");
		ExceptionHandler.handleEquals(orderTracking.get("courier_code").toString(), "ML", "Courier code is not same in order_tracking");
		ExceptionHandler.handleEquals(orderTracking.get("delivery_status").toString(), "DL", "delivery status is not FIT in order_tracking");
		ExceptionHandler.handleEquals(orderTracking.get("shipment_type").toString(),shipmentType,"ShipmentType is not same in order_tracking");
		ExceptionHandler.handleEquals(orderTracking.get("tracking_no").toString(),trackingNumber, "tracking number is not same in order_tracking");
		ExceptionHandler.handleNotNull(orderTracking.get("courier_creation_status").toString(),"courier creation status is ACCEPTED");
		ExceptionHandler.handleEquals(orderTracking.get("shipment_status").toString(), "DELIVERED", "Shipment status is not 'DELIVERED'");
		String id = orderTracking.get("id").toString();
		Map<String, Object> orderTrackingDetail = (HashMap<String, Object>) DBUtilities.exSelectQueryForSingleRecord("select count(*) from order_tracking_detail where order_tracking_id = "+id, "lms");
		ExceptionHandler.handleNotNull((long)orderTrackingDetail.get("count(*)")>0, "No order_tracking_details found for that order_tracking_id");
	}

	public void orderTrackingValidationPU(Object orderId, String returnId, String shipmentType)throws ManagerException{
		Map<String, Object> orderTracking = (HashMap<String, Object>) DBUtilities.exSelectQueryForSingleRecord("select * from order_tracking where source_return_id_fk = "+returnId, "lms");
		ExceptionHandler.handleEquals(orderTracking.get("courier_code").toString(), "ML", "Courier code is not same in order_tracking");
		ExceptionHandler.handleEquals(orderTracking.get("order_id").toString(), ""+orderId, "order_id is not same in order_tracking");
		ExceptionHandler.handleEquals(orderTracking.get("delivery_status").toString(), "RT", "delivery status is not FIT in order_tracking");
		ExceptionHandler.handleEquals(orderTracking.get("shipment_type").toString(), shipmentType,"ShipmentType is not same in order_tracking");
		ExceptionHandler.handleNotNull(orderTracking.get("tracking_no").toString(), "tracking number null in order_tracking");
//    	ExceptionHandler.handleNotNull(orderTracking.get("courier_creation_status").toString(),"courier creation status is ACCEPTED");
		ExceptionHandler.handleEquals(orderTracking.get("shipment_status").toString(),"PICKUP_SUCCESSFUL", "Shipment status is not 'PICKUP_SUCCESSFUL'");
		String id = orderTracking.get("id").toString();
		Map<String, Object> orderTrackingDetail = (HashMap<String, Object>) DBUtilities.exSelectQueryForSingleRecord("select count(*) from order_tracking_detail where order_tracking_id = "+id, "lms");
		ExceptionHandler.handleNotNull((long)orderTrackingDetail.get("count(*)")>0, "No order_tracking_details found for that order_tracking_id");
	}

	/*public void tryNbuyShipmentItemNBValidation(String orderToShipId, String returnId){
		HashMap<String, Object> tryNbuyShipmentItem = (HashMap<String, Object>) DBUtilities.exSelectQueryForSingleRecord("select * from try_and_buy_shipment_item where return_id = "+returnId, "lms");
		ExceptionHandler.handleNotNull(tryNbuyShipmentItem.get("sku_id").toString(),"SKU id is null in try_and_buy_shipment_item");
		ExceptionHandler.handleEquals(tryNbuyShipmentItem.get("order_to_ship_id").toString(), orderToShipId,"order_to_ship_id is null in try_and_buy_shipment_item");
		ExceptionHandler.handleNotNull(tryNbuyShipmentItem.get("item_identifier").toString(),"Item identifier is null in try_and_buy_shipment_item");
		ExceptionHandler.handleNotNull(tryNbuyShipmentItem.get("remarks").toString(),"remarks is null in try_and_buy_shipment_item");
		ExceptionHandler.handleNotNull(tryNbuyShipmentItem.get("item_value").toString(),"item value is null in try_and_buy_shipment_item");
		ExceptionHandler.handleNotNull(tryNbuyShipmentItem.get("cod_amount").toString(),"cod amount is null in try_and_buy_shipment_item");
		ExceptionHandler.handleNotNull(tryNbuyShipmentItem.get("style_id").toString(),"style_id is null in try_and_buy_shipment_item");
		ExceptionHandler.handleNotNull(tryNbuyShipmentItem.get("item_barcode").toString(),"item_barcode is null in try_and_buy_shipment_item");
		ExceptionHandler.handleNotNull(tryNbuyShipmentItem.get("tried_not_bought_reason").toString(),"tried_not_bought_reason is null in try_and_buy_shipment_item");
	}*/

	/*public void tryNbuyShipmentItemBValidation(String orderToShipId){
		HashMap<String, Object> tryNbuyShipmentItem = (HashMap<String, Object>) DBUtilities.exSelectQueryForSingleRecord("select * from try_and_buy_shipment_item where order_to_ship_id = "+orderToShipId, "lms");
	    ExceptionHandler.handleNotNull(tryNbuyShipmentItem.get("sku_id").toString(),"SKU id is null in try_and_buy_shipment_item");
	    ExceptionHandler.handleNotNull(tryNbuyShipmentItem.get("item_identifier").toString(),"Item identifier is null in try_and_buy_shipment_item");
	    ExceptionHandler.handleNotNull(tryNbuyShipmentItem.get("remarks").toString(),"remarks is null in try_and_buy_shipment_item");
	    ExceptionHandler.handleNotNull(tryNbuyShipmentItem.get("item_value").toString(),"item value is null in try_and_buy_shipment_item");
	    ExceptionHandler.handleNotNull(tryNbuyShipmentItem.get("cod_amount").toString(),"cod amount is null in try_and_buy_shipment_item");
	    ExceptionHandler.handleNotNull(tryNbuyShipmentItem.get("style_id").toString(),"style_id is null in try_and_buy_shipment_item");
	    ExceptionHandler.handleNotNull(tryNbuyShipmentItem.get("item_barcode").toString(),"item_barcode is null in try_and_buy_shipment_item");
	}*/

	/**
	 * mlTryNbuyItem
	 * @param itemId
	 * @param status
	 */
	public void mlTryNbuyItem(String itemId, String status)throws ManagerException{
		Map<String, Object> mlTryNbuyItem = (HashMap<String, Object>) DBUtilities.exSelectQueryForSingleRecord("select * from ml_try_and_buy_item where id = "+itemId, "lms");
		ExceptionHandler.handleNotNull(mlTryNbuyItem.get("source_item_reference_id").toString(),"source_item_reference_id is null in ml_try_and_buy_item");
		ExceptionHandler.handleNotNull(mlTryNbuyItem.get("item_description").toString(),"item_description is null in ml_try_and_buy_item");
		ExceptionHandler.handleNotNull(mlTryNbuyItem.get("item_value").toString(),"item_value is null in ml_try_and_buy_item");
		ExceptionHandler.handleNotNull(mlTryNbuyItem.get("cod_amount").toString(),"cod_amount is null in ml_try_and_buy_item");
		ExceptionHandler.handleNotNull(mlTryNbuyItem.get("remark").toString(),"remark is null in ml_try_and_buy_item");
		ExceptionHandler.handleNotNull(mlTryNbuyItem.get("item_barcode").toString(),"item_barcode is null in ml_try_and_buy_item");
		if (status.equals(EnumSCM.TRIED_AND_NOT_BOUGHT)) {
			ExceptionHandler.handleEquals(mlTryNbuyItem.get("trynbuy_status").toString(),EnumSCM.TRIED_AND_NOT_BOUGHT,"Status is not TRIED_AND_NOT_BOUGHT in ml_try_and_buy_item");
			ExceptionHandler.handleNotNull(mlTryNbuyItem.get("not_bought_reason").toString(),"not_bought_reason is null in ml_try_and_buy_item");
			ExceptionHandler.handleNotNull(mlTryNbuyItem.get("delivery_center_qc_report").toString(),"delivery_center_qc_report is null in ml_try_and_buy_item");
			ExceptionHandler.handleNotNull(mlTryNbuyItem.get("return_tracking_number").toString(),"return_tracking_number is null in ml_try_and_buy_item");
		}
		else
			ExceptionHandler.handleEquals(mlTryNbuyItem.get("trynbuy_status").toString(),status,"Status is not same in ml_try_and_buy_item");
	}

	/**
	 *
	 * @param orderId
	 * @param returnId
	 */
	public void returnShipmentValidation(Object orderId, String returnId) throws ManagerException {
		Map<String, Object> returnShipment = DBUtilities.exSelectQueryForSingleRecord("select * from return_shipment where source_return_id = "+returnId, "lms");
		ExceptionHandler.handleEquals(returnShipment.get("order_id").toString(), ""+orderId, "order Id mismatch in pickup");
		ExceptionHandler.handleNotNull(returnShipment.get("address").toString(), "Address should not be null in Pickup");
		ExceptionHandler.handleNotNull(returnShipment.get("city").toString(), "city should not be null in Pickup");
		ExceptionHandler.handleNotNull(returnShipment.get("email").toString(), "email should not be not null in Pickup");
		ExceptionHandler.handleNotNull(returnShipment.get("customer_name").toString(), "first_name should be not null in Pickup");
		ExceptionHandler.handleEquals(returnShipment.get("status").toString(), "RT","Status should be RT in Pickup");
		ExceptionHandler.handleNotNull(returnShipment.get("delivery_center_id").toString(), "delivery_center_id should not be null in Pickup");
		ExceptionHandler.handleNotNull(returnShipment.get("return_warehouse_id").toString(), "dest_warehouse_id should not be null in Pickup");
		ExceptionHandler.handleNotNull(returnShipment.get("courier_code").toString(), "dest_warehouse_id should not be null in Pickup");
//		ExceptionHandler.handleNotNull(returns.get("style_ids").toString(), "style_ids should not be null in Pickup");
		ExceptionHandler.handleEquals(returnShipment.get("shipment_status").toString(),"PICKUP_SUCCESSFUL", "style_ids should not be null in Pickup");
		ExceptionHandler.handleNotNull(returnShipment.get("tracking_number").toString(), "tracking_number should not be null in Pickup");
		ExceptionHandler.handleNotNull(returnShipment.get("return_type").toString(), "return_type should not be null in Pickup");
	}

	/**
	 * mlShipmentAndPickUpValidation
	 * @param returnId
	 */
	public void mlShipmentAndPickUpValidation(String returnId) throws ManagerException {
		Map<String, Object> mlShipment = (HashMap<String, Object>) DBUtilities.exSelectQueryForSingleRecord("select * from ml_shipment where source_reference_id = "+returnId, "lms");
		String mlShipmentId = mlShipment.get("id").toString();
		ExceptionHandler.handleNotNull(mlShipment.get("shipment_value").toString(),"shipment value is not correct in ml_shipment");
		ExceptionHandler.handleEquals(mlShipment.get("recipient_contact_number").toString(), "1234567890", "contact number mismath in ml_shipment");//not null
		ExceptionHandler.handleNotNull(mlShipment.get("recipient_name").toString(),"recipient_name is null in ml_shipment");
		ExceptionHandler.handleNotNull(mlShipment.get("address").toString(),"Address is null in ml_shipment");
		ExceptionHandler.handleNotNull(mlShipment.get("city").toString(),"City is not same in ml_shipment");
		ExceptionHandler.handleEquals(mlShipment.get("pincode").toString(),"560068", "Pincode is not 560068 in ml_shipment");
		ExceptionHandler.handleEquals(mlShipment.get("shipment_status").toString(),"PICKUP_SUCCESSFUL", "Shipment Status is not 'DELIVERED' in ml_shipment");
		ExceptionHandler.handleNotNull(mlShipment.get("email").toString(), "email info is null in ml_shipment");
		ExceptionHandler.handleEquals(mlShipment.get("delivery_center_id").toString(), "5","Delivery_center_id mismatch in ml_shipment");
		ExceptionHandler.handleNotNull(mlShipment.get("shipping_method").toString(), "shipping_method mismatch in ml_shipment");
		ExceptionHandler.handleNotNull(mlShipment.get("tracking_number").toString(), "Tracking number mismatch in ml_shipment");
		ExceptionHandler.handleEquals(mlShipment.get("shipment_type").toString(), "PU", "Shipment_type mismatch in ml_shipment");
		Map<String, Object> mlPickupShipment = (HashMap<String, Object>) DBUtilities.exSelectQueryForSingleRecord("select * from ml_pickup_shipment where ml_shipment_id = "+mlShipmentId, "lms");
		ExceptionHandler.handleEquals(mlPickupShipment.get("return_type"), "TRY_AND_BUY_PICKUP","Pickup type is not equals TRY_AND_BUY_PICKUP in ml_pickup_shipment");
		ExceptionHandler.handleNotNull(mlPickupShipment.get("return_warehouse_id"),"return_warehouse_id is null in ml_pickup_shipment");
	}

	/**
	 * todValidation
	 * @param
	 * @param trackingNumber
	 * @param shippingMethod
	 * @param itemIdAndStatus
	 * @throws UnsupportedEncodingException
	 * @throws JAXBException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
	public void todValidation(String packegeId, String trackingNumber, String shippingMethod, Map<String, String> itemIdAndStatus) throws UnsupportedEncodingException, JAXBException, ManagerException {
		
		OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
		PacketEntry packetEntry = omsServiceHelper.getPacketEntry(packegeId);
		int shipmentAmount;
		int codAmount;
		Double finalAmount = getFinalAmount(packetEntry);
		Map<String, Object> orderAmount = DBUtilities.exSelectQueryForSingleRecord("select * from packet where id= "+packegeId, "oms");
		shipmentAmount = (int)Math.round(Double.parseDouble(finalAmount.toString()));
		if (orderAmount.get("payment_method").toString().equals("cod")) {
			codAmount =(int)Math.round(Double.parseDouble(finalAmount.toString()));
		}else {
			codAmount = 0;
		}


		Iterator<?> it = itemIdAndStatus.entrySet().iterator();
		int NB_COUNT = 0;
		while(it.hasNext()){
			Map.Entry pair = (Map.Entry)it.next();
			String status = pair.getValue().toString();
			if (status.equals(EnumSCM.TRIED_AND_NOT_BOUGHT)) {
				NB_COUNT++;
			}
		}
		int B_S_NT_COUNT = 0;
		while(it.hasNext()){
			Map.Entry pair = (Map.Entry)it.next();
			String status = pair.getValue().toString();
			if (!status.equals(EnumSCM.TRIED_AND_NOT_BOUGHT)) {
				B_S_NT_COUNT++;
			}
		}
		List<String> returnIdList = new ArrayList<>();
		if (NB_COUNT>0) {
			List<Map<String, Object>> returnIds = DBUtilities.exSelectQuery("select source_return_id_fk from order_tracking where order_id = "+packegeId +" and shipment_type = 'PU'", "lms");
			for (Map<String, Object> abc : returnIds) {
				returnIdList.add(abc.get("source_return_id_fk").toString());
			}
		}
		String shipmentType = "TRY_AND_BUY";
		String mlShipmentId = "";
		Map<String, Object> orderToShip = (HashMap<String, Object>) DBUtilities.exSelectQueryForSingleRecord("select * from order_to_ship where order_id = "+packegeId, "lms");
		String orderToShipId = orderToShip.get("id").toString();
		ExceptionHandler.handleNotNull(orderToShip.get("is_cod").toString(), "is_cod is null in order_to_ship");
		ExceptionHandler.handleEquals((int)Math.round(Double.parseDouble(orderToShip.get("cod_amount").toString())), codAmount, "COD amount mismatch in order_to_ship");
		ExceptionHandler.handleEquals(orderToShip.get("mobile").toString(), "1234567890", "Mobile number is wronge in order_to_ship");
		ExceptionHandler.handleNotNull(orderToShip.get("address").toString(), "address is null in order_to_ship");
		ExceptionHandler.handleNotNull(orderToShip.get("city").toString(),"City name is not eqauls to the city in the order_to_ship");
		ExceptionHandler.handleNotNull(orderToShip.get("email").toString(), "Email not found in order_to_ship");
		ExceptionHandler.handleNotNull(orderToShip.get("first_name").toString(),"First name is missing in order_to_ship");
		ExceptionHandler.handleNotNull(orderToShip.get("state").toString(),"State name is null in order_to_ship");
		ExceptionHandler.handleEquals(orderToShip.get("zipcode").toString(), "560068", "Pin code is not 560068(same)");
		ExceptionHandler.handleEquals(orderToShip.get("status").toString(),"DL", "Status is not equal in order_to_ship");
		ExceptionHandler.handleEquals(orderToShip.get("shipping_method").toString(), shippingMethod, "Shipping method is not same in order_to_ship");
		ExceptionHandler.handleNotNull(orderToShip.get("delivery_center_id").toString(), "delivery center id is null in order_to_ship");
		ExceptionHandler.handleEquals(orderToShip.get("courier_code").toString(), "ML", "Courier code is not same in order_to_ship");
		ExceptionHandler.handleEquals(orderToShip.get("tracking_number").toString(), trackingNumber, "tracking number is not same in order_to_ship");
		ExceptionHandler.handleNotNull(orderToShip.get("rto_warehouse_id").toString(), "RTO warehouse id is null in order_to_ship table");
		ExceptionHandler.handleNotNull(orderToShip.get("warehouse_id").toString(), " warehouse id is null in order_to_ship table");
		ExceptionHandler.handleEquals(orderToShip.get("shipment_status").toString(),"DELIVERED", "Shipment status is not 'DELIVERED'");
		ExceptionHandler.handleEquals(orderToShip.get("shipment_type").toString(), shipmentType, "Shipment type mismatch in order_to_ship");
//		ExceptionHandler.handleNotNull(orderToShip.get("shipment_charges").toString(),"No entries in order_to_ship for shipment_charges");
		ExceptionHandler.handleEquals(orderToShip.get("try_and_buy_duration").toString(),"123","TnB duration not updated correctly in try_and_buy_shipment");

		Map<String, Object> mlShipment = (Map<String, Object>) DBUtilities.exSelectQueryForSingleRecord("select * from ml_shipment where source_reference_id = "+packegeId, "lms");
		mlShipmentId = mlShipment.get("id").toString();
		ExceptionHandler.handleEquals(((int)Math.round(Double.parseDouble(mlShipment.get("shipment_value").toString()))), shipmentAmount,"shipment value is not correct in ml_shipment");
		ExceptionHandler.handleEquals(mlShipment.get("recipient_contact_number").toString(), "1234567890", "contact number mismath in ml_shipment");//not null
		ExceptionHandler.handleNotNull(mlShipment.get("recipient_name").toString(),"recipient_name is null in ml_shipment");
		ExceptionHandler.handleNotNull(mlShipment.get("address").toString(),"Address is null in ml_shipment");
		ExceptionHandler.handleNotNull(mlShipment.get("city").toString(),"City is not same in ml_shipment");
		ExceptionHandler.handleEquals(mlShipment.get("alternate_contact_number").toString(),"1234567890","alternate_contact_number is not same in ml_shipment");
		ExceptionHandler.handleEquals(mlShipment.get("pincode").toString(),"560068", "Pincode is not 560068 in ml_shipment");
		ExceptionHandler.handleEquals(mlShipment.get("shipment_status").toString(),"DELIVERED", "Shipment Status is not 'DELIVERED' in ml_shipment");
		ExceptionHandler.handleNotNull(mlShipment.get("email").toString(), "email info is null in ml_shipment");
		ExceptionHandler.handleEquals(mlShipment.get("delivery_center_id").toString(), "5","Delivery_center_id mismatch in ml_shipment");
		ExceptionHandler.handleEquals(mlShipment.get("shipping_method").toString(), shippingMethod, "shipping_method mismatch in ml_shipment");
		ExceptionHandler.handleEquals(mlShipment.get("tracking_number").toString(), trackingNumber, "Tracking number mismatch in ml_shipment");
		ExceptionHandler.handleEquals(mlShipment.get("shipment_type").toString(), shipmentType, "Shipment_type mismatch in ml_shipment");
		ExceptionHandler.handleNotNull(mlShipment.get("promise_date").toString(),"Promise_date is null in ml_shipment");
		ExceptionHandler.handleNotNull(mlShipment.get("received_date").toString(),"received_date is null in ml_shipment");
		ExceptionHandler.handleNotNull(mlShipment.get("last_attempt_date").toString(),"last_attempt_date is null in ml_shipment");

		while(it.hasNext()){
			Map.Entry pair = (Map.Entry)it.next();
			String itemId = pair.getKey().toString();
			String status = pair.getValue().toString();
			mlTryNbuyItem(itemId, status);
		}

		orderTrackingValidationDL(packegeId, shipmentType, trackingNumber);

		returnIdList.forEach(returnId -> {
			try {
				orderTrackingValidationPU(packegeId, returnId, "PU");
			} catch (ManagerException e) {
				e.printStackTrace();
			}
		});

		/*for(String returnId : returnIdList) {
			tryNbuyShipmentItemNBValidation(orderToShipId, returnId);
		}*/

		/*if (B_S_NT_COUNT>0) {
			HashMap<String, Object> tryNbuyShipmentItemCount = (HashMap<String, Object>) DBUtilities.exSelectQueryForSingleRecord("select count(*) from try_and_buy_shipment_item where order_to_ship_id = "+orderToShipId+" and status in('NOT_TRIED','TRIED_AND_BOUGHT','SNATCHED')", "lms");
			String actualCount = tryNbuyShipmentItemCount.get("count(*)").toString();
			ExceptionHandler.handleEquals(actualCount, B_S_NT_COUNT);
		}*/

		Map<String, Object> ml_shipment_tracking_detail_count = DBUtilities.exSelectQueryForSingleRecord("select count(distinct activity_type) from ml_shipment_tracking_detail where tracking_number = '"+trackingNumber+"'", "lms");
		
		String activityCount = (long)ml_shipment_tracking_detail_count.get("count(distinct activity_type)")+"";
		ExceptionHandler.handleEquals(activityCount, "4");

		Map<String, Object> mlTryNbuyShipment = (HashMap<String, Object>) DBUtilities.exSelectQueryForSingleRecord("select * from ml_try_and_buy_shipment where ml_shipment_id = "+mlShipmentId, "lms");
		ExceptionHandler.handleNotNull(mlTryNbuyShipment.get("item_description").toString(), "item_description is null in  ml_shipment");
		ExceptionHandler.handleEquals((int)Math.round(Double.parseDouble(mlTryNbuyShipment.get("cod_amount").toString())), codAmount, "COD amount is not correct in  ml_shipment");
		ExceptionHandler.handleNotNull(mlTryNbuyShipment.get("payment_method").toString(), "payment_method is null in  ml_shipment");
		ExceptionHandler.handleNotNull(mlTryNbuyShipment.get("rto_warehouse_id").toString(), "rto_warehouse_id is null in  ml_shipment");
		ExceptionHandler.handleNotNull(mlTryNbuyShipment.get("delivered_date").toString(), "delivered_date is null in  ml_shipment");
		ExceptionHandler.handleNotNull(mlTryNbuyShipment.get("trynbuy_duration").toString(), "trynbuy_duration is null in  ml_shipment");

		returnIdList.forEach(returnId -> {
			try {
				returnShipmentValidation(packegeId, returnId);
			} catch (ManagerException e) {
				e.printStackTrace();
			}
		});
		returnIdList.forEach(returnId -> {
			try {
				mlShipmentAndPickUpValidation(returnId);
			} catch (ManagerException e) {
				e.printStackTrace();
			}
		});
	}

	
	@SuppressWarnings("unchecked")
	public String getShipmentTypeMLShipment(String orderId){
		String getStatusMLShipment = "select shipment_type from ml_shipment where source_reference_id = "+orderId;
		List<Map<String, Object>> TxQ = DBUtilities.exSelectQuery(getStatusMLShipment, "lms");
		Map<String, Object> txResult = TxQ.get(0);
		String result = (String) txResult.get("shipment_type");
		return result;
	}

	@SuppressWarnings("unchecked")
	public String getShipmentTypeOrder_tracking(String orderId){
		String getStatusMLShipment = "select shipment_type from order_tracking where order_id = "+orderId;
		List<Map<String, Object>> TxQ = DBUtilities.exSelectQuery(getStatusMLShipment, "lms");
		Map<String, Object> txResult = TxQ.get(0);
		String result = (String) txResult.get("shipment_type");
		return result;
	}

	@SuppressWarnings("unchecked")
	public String getShipmentTypeOrder_to_ship(String orderId){
		String getStatusMLShipment = "select shipment_type from order_to_ship where order_id = "+orderId+"'";
		List<Map<String, Object>> TxQ = DBUtilities.exSelectQuery(getStatusMLShipment, "lms");
		Map<String, Object> txResult = TxQ.get(0);
		String result = (String) txResult.get("shipment_type");
		return result;
	}

	public void delPincode(long pincode) {
		String delPincode = "delete from pincode where id = "+pincode;
		DBUtilities.exUpdateQuery(delPincode,"lms");
	}

	public void delDataCenter(String dcCode) {
		String delPincode = "delete from delivery_center where code = '"+dcCode+"'";
		DBUtilities.exUpdateQuery(delPincode,"lms");
	}

	public void delCourierServiceability(String courierCode){
		String query = "delete from courier_serviceability where courier_code ='"+courierCode+"'";
		DBUtilities.exUpdateQuery(query,"lms");
	}

	public void deltatData(long pincode){
		String query = "delete from tat_data where destination_zipcode ='"+pincode+"'";
		DBUtilities.exUpdateQuery(query,"lms");
	}

	public void delCapacityConfig(long warehouseId, String courierCode, String shippingMode){
		String query = "delete from courier_capacity_config where warehouse_id="+warehouseId+" and courier_code='"+courierCode+"' and shipping_method='"+shippingMode+"'";
		DBUtilities.exUpdateQuery(query,"lms");
	}

	public void delCapacityConfig(long warehouseId, String courierCode){
		String query = "delete from courier_capacity_config where warehouse_id="+warehouseId+" and courier_code='"+courierCode+"'";
		DBUtilities.exUpdateQuery(query,"lms");
	}

	public void delCourierPreferences(long warehouseId, String courierCode){
		String query = "delete from courier_preference_v2 where warehouse_id="+warehouseId+" and courier_code='"+courierCode+"'";
		DBUtilities.exUpdateQuery(query,"lms");
	}

	public void delCourirCapacity(int warehouseId, String courierCode) {
		String query = "delete from courier_capacity where warehouse_id="+warehouseId+" and courier_code='"+courierCode+"'";
		DBUtilities.exUpdateQuery(query,"lms");

	}

	public void delCourirCapacity(long warehouseId, String courierCode, String shippingMode) {
		String query = "delete from courier_capacity where warehouse_id="+warehouseId+" and courier_code='"+courierCode+"' and shipping_method='"+shippingMode+"'";
		DBUtilities.exUpdateQuery(query,"lms");

	}

	@SuppressWarnings("unchecked")
	public String getOrderTrackingId(String orderId) throws SQLException{
		String getOrderTracking = "select id from order_tracking where order_id = '"+orderId+"'";
		List<Map<String, Object>> TxQ = DBUtilities.exSelectQuery(getOrderTracking, "lms");
		Map<String, Object> txResult = TxQ.get(0);
		String result = txResult.get("id").toString();
		return result;
	}

	public String createPayloadMLShipmentUpdate(String trackingNumber, long dcId, String tripId, String event, String shipmentType){
		String dateFormat = new SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ss").format(new Date());
		String payload ="{\"trackingNumber\":\""+trackingNumber+"\",\"eventTime\":\""+dateFormat+"\",\"eventAdditionalInfo\":\"CUSTOMER_NOT_REACHABLE\",\"deliveryCenterId\":\""+dcId+"\",\"eventLocation\":\"DC-"+dcId+"\",\"remarks\":\"Customer not reachable\",\"tripId\":\""+tripId+"\",\"userName\":null,\"event\":\""+event+"\",\"shipmentType\":\""+shipmentType+"\",\"shipmentUpdateMode\":\"MyntraLogistics\"}";
		return payload;
	}

	

	/**
	 * inscanValidation
	 * @param orderId
	 * @param locationId
	 */
	public void inscanValidation(Object orderId, String locationId) throws InterruptedException, ManagerException {
		Thread.sleep(1000);
		Map<String, Object> orderToShip = DBUtilities.exSelectQueryForSingleRecord("select status, shipment_status from order_to_ship where order_id = '"+orderId+"'","lms");
		Map<String, Object> orderAdditionalInfo = DBUtilities.exSelectQueryForSingleRecord("select in_scanned_at,last_scan_premises_id, last_scan_premises_type from order_additional_info where id = (select order_additional_info_id from order_to_ship where order_id = '"+orderId+"'"+")","lms");
		Map<String, Object> orderTracking = DBUtilities.exSelectQueryForSingleRecord("select * from order_tracking where order_id = '"+orderId+"'","lms");
		ExceptionHandler.handleEquals(orderToShip.get("status").toString(),"IS");
		ExceptionHandler.handleEquals(orderToShip.get("shipment_status").toString(),"INSCANNED");
		ExceptionHandler.handleEquals(orderAdditionalInfo.get("in_scanned_at").toString(), locationId);
		ExceptionHandler.handleEquals(orderAdditionalInfo.get("last_scan_premises_id").toString(),locationId);
		ExceptionHandler.handleEquals(orderAdditionalInfo.get("last_scan_premises_type").toString(),"HUB");
		ExceptionHandler.handleEquals(orderTracking.get("delivery_status").toString(),"FIT");
		
		if (orderTracking.get("courier_code").toString().equals("ML")||orderTracking.get("courier_code").toString().equals("EK")||orderTracking.get("courier_code").toString().equals("DE"))
			ExceptionHandler.handleEquals(orderTracking.get("courier_creation_status").toString(),"ACCEPTED");
		if (orderTracking.get("courier_code").toString().equals("ML")){
			Map<String, Object> mlShipment = DBUtilities.exSelectQueryForSingleRecord("select * from ml_shipment where source_reference_id = '"+orderId+"'","lms");
			ExceptionHandler.handleNotNull(mlShipment.get("shipment_status"));
		}
	}

	/**
	 * hubActivityDetailValidation
	 * @param orderId
	 */
	public void hubActivityDetailValidation(Object orderId){
		List<String> statusCombination = new ArrayList<>();
		statusCombination.add("SHIPMENT_DETAILS_CREATED:PACKED:PACKED");
		statusCombination.add("SHIPMENT_DETAILS_UPDATED:PACKED:PACKED");
		statusCombination.add("INSCAN:PACKED:INSCANNED");
		statusCombination.add("ADD_TO_MASTERBAG:INSCANNED:ADDED_TO_MB");
		statusCombination.add("SHIPPED:ADDED_TO_MB:SHIPPED");
		statusCombination.forEach(status->{
			String[] temp = status.split(":");
			String activityType = temp[0];
			String fromStatus = temp[1];
			String toStatus = temp[2];
			Assert.assertTrue(Integer.parseInt(DBUtilities.exSelectQueryForSingleRecord("select count(*) from hub_activity_detail where activity_type = '"+activityType+"' and " +
							"from_status = '"+fromStatus+"' and to_status = '"+toStatus+"' and shipment_id = '"+orderId+"'","lms").get("count(*)").toString())>0,
					"Hub activity details are missing for activity: "+activityType);
		});
	}

	/**
	 * orderTrackingDetailValidation
	 * @param orderId
	 */
	public void orderTrackingDetailValidation(Object orderId){
		List<String> StatusComb = new ArrayList<>();
		StatusComb.add("IN_TRANSIT_NON_CRITICAL:SHIPPED:SHIPPED");
		StatusComb.add("IN_TRANSIT:SHIPPED:SHIPPED");
		StatusComb.add("OUT_FOR_DELIVERY:SHIPPED:OUT_FOR_DELIVERY");
		StatusComb.add("DELIVERED:OUT_FOR_DELIVERY:DELIVERED");
		StatusComb.forEach(status->{
			String[] temp = status.split(":");
			String activityType = temp[0];
			String fromStatus = temp[1];
			String toStatus = temp[2];
			Assert.assertTrue(Integer.parseInt(DBUtilities.exSelectQueryForSingleRecord("select count(*) from order_tracking_detail otd join order_tracking ot on otd.order_tracking_id " +
					"= ot.id and otd.activity_type = '"+activityType+"' and otd.from_status = '"+fromStatus+"' and otd.to_status = '"+toStatus+"' and ot.order_id = '"+orderId+"'","lms").get("count(*)").toString())>0,
					"Order tracking details not found for scenario");
		});
	}

	/**
	 * mlShipmentTrackingDetailValidation
	 * @param orderId
	 */
	public void mlShipmentTrackingDetailValidation(Object orderId){
		List<String> StatusComb = new ArrayList<>();
		StatusComb.add("RECEIVED_IN_DC:EXPECTED_IN_DC:UNASSIGNED");
		StatusComb.add("ASSIGN_TO_SDA:UNASSIGNED:ASSIGNED_TO_SDA");
		StatusComb.add("START_TRIP:ASSIGNED_TO_SDA:OUT_FOR_DELIVERY");
		StatusComb.add("DELIVERED:OUT_FOR_DELIVERY:DELIVERED");
		StatusComb.forEach(status->{
			String[] temp = status.split(":");
			String activityType = temp[0];
			String fromStatus = temp[1];
			String toStatus = temp[2];
			Assert.assertTrue(Integer.parseInt(DBUtilities.exSelectQueryForSingleRecord("select count(*) from ml_shipment_tracking_detail mst join ml_shipment ms on ms.tracking_number " +
					"= mst.tracking_number and mst.activity_type = '"+activityType+"' and mst.from_status = '"+fromStatus+"' and mst.to_status = '"+toStatus+"' and ms.source_reference_id = '"+orderId+"'","lms").
					get("count(*)").toString())>0);
		});
	}

	/**
	 * masterbagTrackingDetailValidation
	 * @param orderId
	 */
	public void masterbagTrackingDetailValidation(Object orderId) {
		List<String> StatusComb = new ArrayList<>();
		StatusComb.add("OUTSCAN:HUB");
		StatusComb.forEach(status->{
			String[] temp = status.split(":");
			String scanType = temp[0];
			String premisesType = temp[1];
			try {
				ExceptionHandler.handleNotNull(DBUtilities.exSelectQueryForSingleRecord("select * from master_bag_tracking_detail where scan_type = '"+scanType+"' and " +
                                "premises_type = '"+premisesType+"' and master_bag_id = (select shipment_id from shipment_order_map where order_id ="+orderId+")","lms").get("master_bag_id").toString(),
                        "master_bag_tracking_detail are missing for activity: "+scanType);
			} catch (ManagerException e) {
				e.printStackTrace();
			}
		});
	}

	public void tmsMasterBagTrackingValidation(Object orderId){
		List<String> StatusComb = new ArrayList<>();
		StatusComb.add("CREATE_MASTERBAG:NEW:NEW");
		StatusComb.add("RECEIVE:NEW:RECEIVED_AT_TRANSPORT_HUB");
		StatusComb.add("ADD_TO_CONTAINER:RECEIVED_AT_TRANSPORT_HUB:ADDED_TO_CONTAINER");
		StatusComb.add("SHIP:ADDED_TO_CONTAINER:IN_TRANSIT");
		StatusComb.add("IN_TRANSIT:IN_TRANSIT:IN_TRANSIT");
		StatusComb.add("RECEIVE:IN_TRANSIT:RECEIVED_AT_TRANSPORT_HUB");
		StatusComb.add("ADD_TO_CONTAINER:RECEIVED_AT_TRANSPORT_HUB:ADDED_TO_CONTAINER");
		StatusComb.add("SHIP:ADDED_TO_CONTAINER:IN_TRANSIT");
		StatusComb.add("UPDATE_MASTERBAG:IN_TRANSIT:RECEIVED");
		StatusComb.forEach(status->{
			String[] temp = status.split(":");
			String activityType = temp[0];
			String fromStatus = temp[1];
			String toStatus = temp[2];
			Assert.assertTrue(Integer.parseInt(DBUtilities.exSelectQueryForSingleRecord("select count(*) from myntra_tms.masterbag_tracking_detail tmd join myntra_tms.masterbag tmb join shipment_order_map som " +
					"on tmb.`source_reference_id` = som.`shipment_id` and tmd.`masterbag_id` = tmb.id and som.`order_id` = '"+orderId+"' and tmd.`activity_type` = '"+activityType+"' and tmd.`from_status` = " +
					"'"+fromStatus+"' and tmd.to_status = '"+toStatus+"'","lms").get("count(*)").toString())>0);
		});

	}

	/**
	 * insertOrderEntryForMasterBag
	 * @param orderId
	 * @param trackingNumber
	 * @param courierCode
	 * @param warehouseId
	 * @param dcId
	 * @param shipmentType
	 * @param shippingMethod
	 * @param pincode
	 * @throws Exception
	 */

	public void insertOrderEntryForMasterBag(Object orderId, String trackingNumber, String courierCode, int warehouseId,int dcId, String shipmentType, String shippingMethod, String pincode, String status, String shipmentStatus) throws IOException, JAXBException, JSONException, XMLStreamException, InterruptedException, ManagerException {
		LmsServiceHelper lmsServiceHelper = new LmsServiceHelper();
		Date myDate = new Date();
		SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = DATE_FORMAT.format(myDate);
		String orderAdditionalInfoId = LMSUtils.randomGenn(12);
		DBUtilities.exUpdateQuery("delete from hub_activity_detail where shipment_id = '"+orderId+"'","lms");
		DBUtilities.exUpdateQuery("delete from shipment_order_map where order_id = '"+orderId+"'","lms");
		DBUtilities.exUpdateQuery("delete from order_tracking_detail where order_tracking_id = (select id from order_tracking where order_id = '"+orderId+"')","lms");
		DBUtilities.exUpdateQuery("delete from order_tracking where order_id = '"+orderId+"'","lms");
		DBUtilities.exUpdateQuery("delete from order_to_ship where order_id = '"+orderId+"'","lms");
		DBUtilities.exUpdateQuery("delete from ml_shipment where source_reference_id = '"+orderId+"'","lms");
		String deliveryCenterId = ""+dcId;
		if (dcId==0){
			deliveryCenterId = null;
		}
		//String dhHub = ((HubWareHouseConfigResponse)lmsServiceHelper.getDispatchHubFromWarehouse.apply(warehouseId)).getHubWarehouseConfigEntries().get(0).getHubCode();
		
		String dhHub = lmsServiceHelper.getHubConfig(warehouseId+"", "DL");
		
		DBUtilities.exUpdateQuery("INSERT INTO `order_additional_info` (`id`, `created_by`,  `version`, `total`, `giftcert_discount`, `subtotal`, `discount`, `cart_discount`, `ref_discount`, " +
				"`coupon_discount`, `shipping_cost`, `tax`, `gift_charges`, `collected_amount`, `refund_amount`, `cashback`, `cash_redeemed`, `item_description`, `expected_delivery_center_id`, " +
				"`expected_ship_date`, `latitude`, `longitude`, `receivedBy`, `receivedAt`, `paymentType`, `payment_pos`, `customer_signature`, `govt_tax_amount`, `contains_hazmat`, " +
				"`shipment_weight`, `style_ids`, `in_scanned_at`, `address_type`, `tin_number`, `invoice_id`, `cst_number`,last_scan_premises_id,last_scan_premises_type)" +
				"VALUES ("+orderAdditionalInfoId+", 'erpMessageQueue', 0, 1099.00, NULL, 1099.00, 0.00, 0.00, NULL, 0.00, NULL, 0.00, NULL, NULL, NULL, NULL, 0.00, " +
				"'Puma Mens Ballistic Spike White Green Shoe:Sports Shoes', "+deliveryCenterId+", '2016-10-25 23:45:00', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 139.17, 0, 'NA', '1541', '"+
				dhHub+"', NULL, '1', 'INV_80200990', NULL, '"+dhHub+"','HUB')","lms");
		DBUtilities.exUpdateQuery("INSERT INTO `order_to_ship` ( `created_by`,`version`, `address`, `city`, `is_cod`, `cod_amount`, `country`, `email`, `first_name`, `last_name`, `mobile`, `order_id`, " +
				"`state`, `title`, `zipcode`, `cust_mobile`, `status`, `warehouse_id`, `delivery_center_id`, `packed_on`, `inscanned_on`, `shipped_on`, `sales_order_id`, `promise_date`, `shipping_method`, " +
				"`locality`, `store_id`, `seller_id`, `user_id`, `rto_warehouse_id`, `courier_code`, `tracking_number`, `shipment_status`, `shipment_type`, `order_additional_info_id`)" +
				"VALUES( 'erpMessageQueue', 3, 'test address myntra design pvt ltd , Dr.B.A. Chowk', 'Pune', 1, 1099.00, 'India', 'lmsautomation1@myntra.com', 'lmsautomation ', '', '1234567890', "+orderId+", " +
				"'MH', NULL, '"+pincode+"', '1234567890', '"+status+"', "+warehouseId+", "+deliveryCenterId+", '"+date+"', NULL, NULL, NULL, '"+date+"', '"+shippingMethod+"', 'Dr.B.A. Chowk', 1, '73', '30f43acf.f0f2.4e44.842f.1aeae1b79652VbqNFDEZLY', 36, '"+courierCode+"', " +
				"'"+trackingNumber+"', '"+shipmentStatus+"', '"+shipmentType+"',"+orderAdditionalInfoId+")","lms");
		DBUtilities.exUpdateQuery("INSERT INTO `order_tracking` (`created_by`, `version`, `courier_code`, `delivery_date`, `delivery_status`, `failed_attempts`,  `order_id`, `shipment_type`, " +
				"`tracking_no`, `tracking_task_status`, `is_synced_with_portal`, `rto_date`, `first_attempt_date`, `last_attempt_date`, `lost_on_date`, `courier_creation_status`, " +
				"`shipment_status`)VALUES('erpMessageQueue', 3, '"+courierCode+"', NULL, 'FIT', 0, "+orderId+", '"+shipmentType+"', '"+trackingNumber+"', NULL, 0, NULL, NULL, NULL, NULL, 'ACCEPTED', '"+shipmentStatus+"');","lms");
	}

	/**
	 * insertOrderEntryForMasterBagWithMLshipment
	 * @param orderId
	 * @param trackingNumber
	 * @param courierCode
	 * @param warehouseId
	 * @param dcId
	 * @param shipmentType
	 * @param shippingMethod
	 * @param pincode
	 * @param status
	 * @param shipmentStatus
	 * @throws Exception
	 */
	public void insertOrderEntryForMasterBagWithMLshipment(Object orderId, String trackingNumber, String courierCode, int warehouseId, int dcId, String shipmentType, String shippingMethod, String pincode, String status, String shipmentStatus, String mlShipmentStatus) throws Exception{
		LmsServiceHelper lmsServiceHelper = new LmsServiceHelper();
		Date myDate = new Date();
		SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = DATE_FORMAT.format(myDate);
		String orderAdditionalInfoId = LMSUtils.randomGenn(12);
		String mlShipmentId  = LMSUtils.randomGenn(12);
		
		String dhHub = lmsServiceHelper.getHubConfig(warehouseId+"", "DL");
		//String dhHub = ((HubWareHouseConfigResponse)lmsServiceHelper.getDispatchHubFromWarehouse.apply(warehouseId)).getHubWarehouseConfigEntries().get(0).getHubCode();
		DBUtilities.exUpdateQuery("delete from hub_activity_detail where shipment_id = '"+orderId+"'","lms");
		DBUtilities.exUpdateQuery("delete from shipment_order_map where order_id = '"+orderId+"'","lms");
		DBUtilities.exUpdateQuery("delete from order_tracking_detail where order_tracking_id = (select id from order_tracking where order_id = '"+orderId+"'"+")","lms");
		DBUtilities.exUpdateQuery("delete from order_tracking where order_id = '"+orderId+"'","lms");
		DBUtilities.exUpdateQuery("delete from order_to_ship where order_id = '"+orderId+"'","lms");
		DBUtilities.exUpdateQuery("delete from ml_delivery_shipment where ml_shipment_id = (select id from ml_shipment where source_reference_id = '"+orderId+"')","lms");
		DBUtilities.exUpdateQuery("delete from ml_shipment_tracking_detail where tracking_number = (select tracking_number from ml_shipment where source_reference_id = '"+orderId+"')","lms");
		DBUtilities.exUpdateQuery("delete from ml_last_mile_partner_shipment_assignment where ml_tracking_number = (select tracking_number from ml_shipment where source_reference_id = '"+orderId+"')","lms");
		DBUtilities.exUpdateQuery("delete from ml_shipment where source_reference_id = '"+orderId+"'","lms");

		DBUtilities.exUpdateQuery("INSERT INTO `order_additional_info` (`id`, `created_by`,  `version`, `total`, `giftcert_discount`, `subtotal`, `discount`, `cart_discount`, `ref_discount`, " +
				"`coupon_discount`, `shipping_cost`, `tax`, `gift_charges`, `collected_amount`, `refund_amount`, `cashback`, `cash_redeemed`, `item_description`, `expected_delivery_center_id`, " +
				"`expected_ship_date`, `latitude`, `longitude`, `receivedBy`, `receivedAt`, `paymentType`, `payment_pos`, `customer_signature`, `govt_tax_amount`, `contains_hazmat`, " +
				"`shipment_weight`, `style_ids`, `in_scanned_at`, `address_type`, `tin_number`, `invoice_id`, `cst_number`,last_scan_premises_id, last_scan_premises_type)" +
				"VALUES ("+orderAdditionalInfoId+", 'erpMessageQueue', 0, 1099.00, NULL, 1099.00, 0.00, 0.00, NULL, 0.00, NULL, 0.00, NULL, NULL, NULL, NULL, 0.00, " +
				"'Puma Mens Ballistic Spike White Green Shoe:Sports Shoes', "+dcId+", '2016-10-25 23:45:00', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 139.17, 0, 'NA', '1541', '"+dhHub+"', NULL, '1', 'INV_80200990', NULL, '"+dhHub+"','HUB')","lms");
		DBUtilities.exUpdateQuery("INSERT INTO `order_to_ship` ( `created_by`,`version`, `address`, `city`, `is_cod`, `cod_amount`, `country`, `email`, `first_name`, `last_name`, `mobile`, `order_id`, " +
				"`state`, `title`, `zipcode`, `cust_mobile`, `status`, `warehouse_id`, `delivery_center_id`, `packed_on`, `inscanned_on`, `shipped_on`, `sales_order_id`, `promise_date`, `shipping_method`, " +
				"`locality`, `store_id`, `seller_id`, `user_id`, `rto_warehouse_id`, `courier_code`, `tracking_number`, `shipment_status`, `shipment_type`, `order_additional_info_id`)" +
				"VALUES( 'erpMessageQueue', 3, 'test address myntra design pvt ltd , Dr.B.A. Chowk', 'Pune', 1, 1099.00, 'India', 'lmsautomation1@myntra.com', 'lmsautomation ', '', '1234567890', "+orderId+", " +
				"'MH', NULL, '"+pincode+"', '1234567890', '"+status+"', "+warehouseId+", "+dcId+", '"+date+"', NULL, NULL, NULL, '"+date+"', '"+shippingMethod+"', 'Dr.B.A. Chowk', 1, '73', '30f43acf.f0f2.4e44.842f.1aeae1b79652VbqNFDEZLY', 36, '"+courierCode+"', " +
				"'"+trackingNumber+"', '"+shipmentStatus+"', '"+shipmentType+"',"+orderAdditionalInfoId+")","lms");
		DBUtilities.exUpdateQuery("INSERT INTO `order_tracking` (`created_by`, `version`, `courier_code`, `delivery_date`, `delivery_status`, `failed_attempts`,  `order_id`, `shipment_type`, " +
				"`tracking_no`, `tracking_task_status`, `is_synced_with_portal`, `rto_date`, `first_attempt_date`, `last_attempt_date`, `lost_on_date`, `courier_creation_status`, " +
				"`shipment_status`)VALUES('erpMessageQueue', 3, '"+courierCode+"', NULL, 'FIT', 0, "+orderId+", '"+shipmentType+"', '"+trackingNumber+"', NULL, 0, NULL, NULL, NULL, NULL, 'ACCEPTED', '"+shipmentStatus+"');","lms");
		DBUtilities.exUpdateQuery("INSERT INTO `ml_shipment` (`id`,`created_by`, `last_modified_by`, `version`, `source_id`, `source_reference_id`, `tracking_number`, `delivery_center_id`, `shipment_type`, " +
				"`shipping_method`, `shipment_status`, `recipient_name`, `address`, `city`, `pincode`, `recipient_contact_number`, `alternate_contact_number`, `email`, `shipment_value`, `contents_description`, " +
				"`received_date`, `promise_date`, `failed_attempt_count`, `last_attempt_date`, `is_deleted`) VALUES("+mlShipmentId+",'erpMessageQueue', NULL, 1, 1, '"+orderId+"', '"+trackingNumber+"', "+dcId+", " +
				"'"+shipmentType+"', '"+shippingMethod+"', '"+mlShipmentStatus+"', 'shubham ', 'Myntra Design, AKR B, 3rd Floor', 'Bangalore', '"+pincode+"', NULL, NULL, 'lmsautomation1&amp;&#x23;x40&#x3b;myntra.com', " +
				"1099.00, 'Puma Men Ballistic Spike White Green Shoe:Size - UK7:SKU code - 3866:Article No - 10186601:MRP per qty - 1099.0', NULL, '"+date+"', NULL, NULL, 0)","lms");
		DBUtilities.exUpdateQuery("INSERT INTO `ml_delivery_shipment` (`ml_shipment_id`, `cod_amount`, `payment_method`, `rto_warehouse_id`, `delivered_date`, `rto_date`) " +
				"VALUES("+mlShipmentId+", 1099.00, NULL, 36, NULL, NULL)","lms");
	}

	public void insertReturnEntryForMasterBag(String orderId, String returnId,String trackingNumber, String courierCode, int warehouseId, int dcId, String shipmentType, String shippingMethod, String pincode) throws Exception{
		LmsServiceHelper lmsServiceHelper = new LmsServiceHelper();
		Date myDate = new Date();
		SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = DATE_FORMAT.format(myDate);
		String orderAdditionalInfoId = LMSUtils.randomGenn(12);
		
		String dhHub = lmsServiceHelper.getHubConfig(warehouseId+"", "DL");
		//String dhHub = ((HubWareHouseConfigResponse)lmsServiceHelper.getDispatchHubFromWarehouse.apply(warehouseId)).getHubWarehouseConfigEntries().get(0).getHubCode();
		DBUtilities.exUpdateQuery("delete from hub_activity_detail where shipment_id = '"+orderId+"'","lms");
		DBUtilities.exUpdateQuery("delete from shipment_order_map where order_id = '"+orderId+"'","lms");
		DBUtilities.exUpdateQuery("delete from shipment_order_map where source_return_id_fk = '"+returnId+"'","lms");
		DBUtilities.exUpdateQuery("delete from return_shipment where source_return_id = '"+returnId+"'","lms");
		DBUtilities.exUpdateQuery("delete from ml_shipment where source_reference_id = '"+orderId+"'","lms");
		DBUtilities.exUpdateQuery("delete from ml_shipment where source_reference_id = '"+returnId+"'","lms");
		DBUtilities.exUpdateQuery("delete from order_tracking_detail where order_tracking_id = (select id from order_tracking where order_id = '"+orderId+"')","lms");
		DBUtilities.exUpdateQuery("delete from order_tracking where order_id = '"+orderId+"'","lms");
		DBUtilities.exUpdateQuery("delete from order_to_ship where order_id = '"+orderId+"'","lms");

		DBUtilities.exUpdateQuery("INSERT INTO `order_additional_info` (`id`, `created_by`,  `version`, `total`, `giftcert_discount`, `subtotal`, `discount`, `cart_discount`, `ref_discount`, " +
				"`coupon_discount`, `shipping_cost`, `tax`, `gift_charges`, `collected_amount`, `refund_amount`, `cashback`, `cash_redeemed`, `item_description`, `expected_delivery_center_id`, " +
				"`expected_ship_date`, `latitude`, `longitude`, `receivedBy`, `receivedAt`, `paymentType`, `payment_pos`, `customer_signature`, `govt_tax_amount`, `contains_hazmat`, " +
				"`shipment_weight`, `style_ids`, `in_scanned_at`, `address_type`, `tin_number`, `invoice_id`, `cst_number`,last_scan_premises_id, last_scan_premises_type)" +
				"VALUES ("+orderAdditionalInfoId+", 'erpMessageQueue', 0, 1099.00, NULL, 1099.00, 0.00, 0.00, NULL, 0.00, NULL, 0.00, NULL, NULL, NULL, NULL, 0.00, " +
				"'Puma Mens Ballistic Spike White Green Shoe:Sports Shoes', "+dcId+", '2016-10-25 23:45:00', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 139.17, 0, 'NA', '1541', '"+
				dhHub+"', NULL, '1', 'INV_80200990', NULL, '"+dhHub+"','HUB')","lms");
		DBUtilities.exUpdateQuery("INSERT INTO `order_to_ship` ( `created_by`,`version`, `address`, `city`, `is_cod`, `cod_amount`, `country`, `email`, `first_name`, `last_name`, `mobile`, `order_id`, " +
				"`state`, `title`, `zipcode`, `cust_mobile`, `status`, `warehouse_id`, `delivery_center_id`, `packed_on`, `inscanned_on`, `shipped_on`, `sales_order_id`, `promise_date`, `shipping_method`, " +
				"`locality`, `store_id`, `seller_id`, `user_id`, `rto_warehouse_id`, `courier_code`, `tracking_number`, `shipment_status`, `shipment_type`, `order_additional_info_id`)" +
				"VALUES( 'erpMessageQueue', 3, 'test address myntra design pvt ltd , Dr.B.A. Chowk', 'Pune', 1, 1099.00, 'India', 'lmsautomation1@myntra.com', 'lmsautomation ', '', '1234567890', "+orderId+", " +
				"'MH', NULL, '"+pincode+"', '1234567890', 'IS', "+warehouseId+", "+dcId+", '"+date+"', NULL, NULL, NULL, '"+date+"', '"+shippingMethod+"', 'Dr.B.A. Chowk', 1, '73', '30f43acf.f0f2.4e44.842f.1aeae1b79652VbqNFDEZLY', 36, '"+courierCode+"', " +
				"'"+trackingNumber+"', 'INSCANNED', '"+shipmentType+"',"+orderAdditionalInfoId+")","lms");
		DBUtilities.exUpdateQuery("insert into return_shipment (`source_id`, `source_return_id`, `return_type`, `user_id`, `customer_name`, `address`, `city`, `state_code`, `country`, `pincode`, `email`, " +
				"`primary_contact_number`, `courier_code`, `tracking_number`, `shipment_status`, `status`, `delivery_center_id`, `order_id`, `return_warehouse_id`, `shipment_value`, `created_by`) " +
				"VALUES (1,  "+returnId+", 'OPEN_BOX_PICKUP','30f43acf.f0f2.4e44.842f.1aeae1b79652VbqNFDEZLY','shubham ','Myntra Design, AKR B, 3rd Floor', 'Bangalore', 'KA', 'India', '"+pincode+"', " +
				"'lmsautomation1&#x40;myntra.com', '1234567890', '"+courierCode+"','"+trackingNumber+"','PICKUP_SUCCESSFUL','RT',"+dcId+","+orderId+","+warehouseId+", 1099.00, 'automation')","lms");
		DBUtilities.exUpdateQuery("INSERT INTO `ml_shipment` (`created_by`, `last_modified_by`, `version`, `source_id`, `source_reference_id`, `tracking_number`, `delivery_center_id`, `shipment_type`, " +
				"`shipping_method`, `shipment_status`, `recipient_name`, `address`, `city`, `pincode`, `recipient_contact_number`, `alternate_contact_number`, `email`, `shipment_value`, `contents_description`, " +
				"`received_date`, `promise_date`, `failed_attempt_count`, `last_attempt_date`, `is_deleted`) VALUES('erpMessageQueue', NULL, 1, 1, '"+orderId+"', '"+trackingNumber+"', "+dcId+", " +
				"'"+shipmentType+"', '"+shippingMethod+"', 'PICKUP_SUCCESSFUL', 'shubham ', 'Myntra Design, AKR B, 3rd Floor', 'Bangalore', '"+pincode+"', NULL, NULL, 'lmsautomation1&amp;&#x23;x40&#x3b;myntra.com', " +
				"1099.00, 'Puma Men Ballistic Spike White Green Shoe:Size - UK7:SKU code - 3866:Article No - 10186601:MRP per qty - 1099.0', NULL, '"+date+"', NULL, NULL, 0)","lms");
	}

	/**
	 * insertTestOrder
	 * @param order_Id
	 * @param status_lms
	 * @param shipment_type
	 * @throws UnsupportedEncodingException
	 * @throws JAXBException
	 */
	public void insertTestOrder(String order_Id, String status_lms, String shipment_type){
		String envName = init.Configurations.GetTestEnvironemnt().name();
		Map<String, Object> orderRelease = DBUtilities.exSelectQueryForSingleRecord("select * from order_release where order_id_fk = "+order_Id,"oms");
		String warehouseId = "0";
		if (orderRelease.get("warehouse_id")!=null){
			warehouseId = orderRelease.get("warehouse_id").toString();
		}
		String insert = "INSERT INTO `test_order` (order_id, order_release_id, status_oms, status_lms, payment_method, cod_amount, shipment_value, zipcode, warehouse_id, dc_id, courier_code, shipping_method, shipment_type, env)"
				+ " VALUES("+order_Id+", "+orderRelease.get("id")+", '"+orderRelease.get("status_code")+"', '"+status_lms+"', '"+orderRelease.get("payment_method")+"',"+orderRelease.get("final_amount")+", " +
				""+orderRelease.get("final_amount")+", "+orderRelease.get("zipcode")+", "+warehouseId+","+orderRelease.get("delivery_center_id")+", '"+orderRelease.get("courier_code")+"'," +
				" '"+orderRelease.get("shipping_method")+"','"+shipment_type+"','"+envName+"')";
		DBUtilities.exUpdateQuery(insert, "test");
	}

	/**
	 * insertTestOrder
	 * @param order_Id
	 * @param status_lms
	 * @param shipment_type
	 * @param paymentMethod
	 * @throws UnsupportedEncodingException
	 * @throws JAXBException
	 */
	public void insertTestOrder(String order_Id, String status_lms, String shipment_type, String paymentMethod) {
		String envName = init.Configurations.GetTestEnvironemnt().name();
		Map<String, Object> orderRelease = DBUtilities.exSelectQueryForSingleRecord("select * from order_release where order_id_fk = "+order_Id,"oms");
		String warehouseId = "0";
		if (orderRelease.get("warehouse_id")!=null){
			warehouseId = orderRelease.get("warehouse_id").toString();
		}
		String insert = "INSERT INTO `test_order` (order_id, order_release_id, status_oms, status_lms, payment_method, cod_amount, shipment_value, zipcode, warehouse_id, dc_id, courier_code, shipping_method, shipment_type, env)"
				+ " VALUES("+order_Id+", "+orderRelease.get("id")+", '"+orderRelease.get("status_code")+"', '"+status_lms+"', '"+paymentMethod+"',"+orderRelease.get("final_amount")+", "+orderRelease.get("final_amount")+", " +
				""+orderRelease.get("zipcode")+", "+warehouseId+","+orderRelease.get("delivery_center_id")+", '"+orderRelease.get("courier_code")+"', " +
				"'"+orderRelease.get("shipping_method")+"','"+shipment_type+"','"+envName+"')";
		DBUtilities.exUpdateQuery(insert, "test");
	}

	/**
	 * insertTestOrder
	 * @param order_Id
	 * @throws Exception
	 */
	public void insertTestOrder(String order_Id){
		String envName = init.Configurations.GetTestEnvironemnt().name();
		Map<String, Object> orderRelease = DBUtilities.exSelectQueryForSingleRecord("select * from order_release where order_id_fk = "+order_Id,"oms");
		String insert = "INSERT INTO `test_order` (order_id, order_release_id, status_oms, cod_amount, shipment_value, env)"
				+ " VALUES("+order_Id+", "+orderRelease.get("id")+", '"+orderRelease.get("status_code")+"',"+orderRelease.get("final_amount")+", "+orderRelease.get("final_amount")+", "+envName+")";
		DBUtilities.exUpdateQuery(insert, "test");
	}

	/**
	 * getOrderFromTestDB
	 * @param omsStatus
	 * @param CourierCode
	 * @param zipcode
	 * @param WarehosueIds
	 * @param shipmentType
	 * @return
	 */
	public long getOrderFromTestDB(Boolean fg, String omsStatus, String CourierCode, String zipcode, String WarehosueIds, String shipmentType) throws IOException {
		if (fg==false) throw new IOException();
		String envName = init.Configurations.GetTestEnvironemnt().name();
		Map<String, Object> order = DBUtilities.exSelectQueryForSingleRecord("select order_id from test_order where status_oms = "+omsStatus+" and courier_code = '"+CourierCode+"' and zipcode = "+zipcode+" warehouse_id in ("+WarehosueIds+") and shipment_type = '"+shipmentType+"' and inUse = 0 and env = '"+envName+"' order by last_modified_on DESC" , "test");
		DBUtilities.exUpdateQuery("update test_order set inUse = 1 where order_id = "+order.get("order_id").toString(), "test");
		return (long)order.get("order_id");
	}

	/**
	 * getOrderFromTestDB
	 * @param omsStatus
	 * @param CourierCode
	 * @param zipcode
	 * @param shipmentType
	 * @return
	 * @throws IOException
	 */
	public String getOrderFromTestDB(String omsStatus, String CourierCode, String zipcode, String shipmentType) throws IOException {
		if (fg==false) throw new IOException();
		String envName = init.Configurations.GetTestEnvironemnt().name();
		String[] statusList = omsStatus.split(",");
		for (String status : statusList) {
			try {
				if (omsStatus.equals("'PK'")){
					Map<String, Object> order = DBUtilities.exSelectQueryForSingleRecord("select order_id from test_order where status_oms = (" + status + ") and status_lms in ('PACKED','INSCANNED') and courier_code = '" + CourierCode + "' and zipcode = '" + zipcode + "' and shipment_type = '" + shipmentType + "' and inUse = 0 and env = '"+envName+"' order by last_modified_on DESC limit 1", "test");
					updateTestDBInUse((long)order.get("order_id"),1);
					return order.get("order_id").toString();
				}
				else {
					Map<String, Object> order = DBUtilities.exSelectQueryForSingleRecord("select order_id from test_order where status_oms = (" + status + ") and courier_code = '" + CourierCode + "' and zipcode = '" + zipcode + "' and shipment_type = '" + shipmentType + "' and inUse = 0 and env = '" + envName + "' order by last_modified_on DESC limit 1", "test");
					updateTestDBInUse((long)order.get("order_id"),1);
					return order.get("order_id").toString();
				}
			} catch (Exception e) {
				log.info("Unable to find order with status with : " + status + ", finding order with some diff status...");
			}
		}
		throw new IOException("Unable to find Order with the given status");
	}

	/**
	 * getOrderFromTestDB
	 * @param omsStatus
	 * @param lmsStatus
	 * @param warehouseId
	 * @param zipcode
	 * @return
	 * @throws IOException
	 */
	public long getOrderFromTestDB(String omsStatus, String lmsStatus, int warehouseId, String zipcode) throws IOException {
		if (fg==false) throw new IOException();
		String envName = init.Configurations.GetTestEnvironemnt().name();
		String[] statusList = omsStatus.split(",");
		for (String status : statusList) {
			try {
				Map<String, Object> order = DBUtilities.exSelectQueryForSingleRecord("select order_id from test_order where status_oms = '"+status+"' and status_lms = '" +lmsStatus+ "' and zipcode = '" + zipcode + "' and warehouse_id = '" +warehouseId+ "' and inUse = 0 and env = '"+envName+"' order by last_modified_on DESC limit 1", "test");
				updateTestDBInUse((long)order.get("order_id"),1);
				return (long)order.get("order_id");
			} catch (Exception e) {
				log.info("Unable to find order with status with : " + status + ", finding order with some diff status...");
			}
		}
		throw new IOException("Unable to find Order with the given status");
	}

	/**
	 * getOrderFromTestDB
	 * @param omsStatus
	 * @param zipcode
	 * @param shipmentType
	 * @param warehouseId
	 * @return
	 * @throws IOException
	 */
	public long getOrderFromTestDB(String omsStatus, String zipcode, String shipmentType, int warehouseId) throws IOException {
		if (fg==false) throw new IOException();
		String envName = init.Configurations.GetTestEnvironemnt().name();
		String[] statusList = omsStatus.split(",");
		for (String status : statusList) {
			try {
				if (omsStatus.equals("'PK'")){
					Map<String, Object> order = DBUtilities.exSelectQueryForSingleRecord("select order_id from test_order where payment_method = 'cod' and status_oms = (" + status + ") and status_lms in ('PACKED','INSCANNED') and zipcode = '" + zipcode + "' and shipment_type = '" + shipmentType + "' and warehouse_id = " + warehouseId + " and inUse = 0 and env = '" + envName + "' order by last_modified_on DESC limit 1", "test");
					updateTestDBInUse((long)order.get("order_id"),1);
					return (long) order.get("order_id");
				}else {
					Map<String, Object> order = DBUtilities.exSelectQueryForSingleRecord("select order_id from test_order where payment_method = 'cod' and status_oms = (" + status + ") and zipcode = '" + zipcode + "' and shipment_type = '" + shipmentType + "' and warehouse_id = " + warehouseId + " and inUse = 0 and env = '" + envName + "' order by last_modified_on DESC limit 1", "test");
					updateTestDBInUse((long)order.get("order_id"),1);
					return (long) order.get("order_id");
				}
			} catch (Exception e) {
				log.info("Unable to find order with status with : " + status + ", finding order with some diff status...");
			}
		}
		throw new IOException("Unable to find Order with the given status");
	}

	/**
	 *
	 * @param omsStatus
	 * @param lmsStatus
	 * @param zipcode
	 * @param shipmentType
	 * @param warehouseId
	 * @return
	 * @throws IOException
	 */
	public long getOrderFromTestDB(String omsStatus, String lmsStatus, String zipcode, String shipmentType, int warehouseId) throws IOException {
		if (fg==false) throw new IOException();
		String envName = init.Configurations.GetTestEnvironemnt().name();
		String[] statusList = omsStatus.split(",");
		for (String status : statusList) {
			try {
				Map<String, Object> order = DBUtilities.exSelectQueryForSingleRecord("select order_id from test_order where payment_method = 'cod' and status_oms = ("+status+") and status_lms = '"+lmsStatus+"' and zipcode = '"+zipcode+"' and shipment_type = '"+shipmentType+"' and warehouse_id = "+warehouseId+" and inUse = 0 and env = '"+envName+"' order by last_modified_on DESC limit 1" , "test");
				updateTestDBInUse((long)order.get("order_id"),1);
				return (long)order.get("order_id");
			} catch (Exception e) {
				log.info("Unable to find order with status with : " + status + ", finding order with some diff status...");
			}
		}
		throw new IOException("Unable to find Order with the given status");
	}

	/**
	 * getOrderFromTestDB
	 * @param omsStatus
	 * @param CourierCode
	 * @param zipcode
	 * @param shipmentType
	 * @param shippingMethod
	 * @param payentMode
	 * @return
	 * @throws IOException
	 */
	public String getOrderFromTestDB(String omsStatus, String CourierCode, String zipcode, String shipmentType, String shippingMethod, String payentMode) throws IOException {
		if (fg==false) throw new IOException();
		String envName = init.Configurations.GetTestEnvironemnt().name();
		String[] statusList = omsStatus.split(",");
		for (String status : statusList) {
			try {
				if (omsStatus.equals("'PK'")) {
					Map<String, Object> order = DBUtilities.exSelectQueryForSingleRecord("select order_id from test_order where status_oms = (" + status + ") and status_lms in ('PACKED','INSCANNED') and courier_code = '" + CourierCode + "' and zipcode = '" + zipcode + "' and shipment_type = '" + shipmentType + "' and shipping_method = '" + shippingMethod + "' and payment_method = '" + payentMode + "' and inUse = 0 and env = '" + envName + "' order by last_modified_on DESC limit 1", "test");
					DBUtilities.exUpdateQuery("update test_order set inUse = 1 where order_id = " + order.get("order_id").toString(), "test");
					return order.get("order_id").toString();
				}else {
					Map<String, Object> order = DBUtilities.exSelectQueryForSingleRecord("select order_id from test_order where status_oms = (" + status + ") and courier_code = '" + CourierCode + "' and zipcode = '" + zipcode + "' and shipment_type = '" + shipmentType + "' and shipping_method = '" + shippingMethod + "' and payment_method = '" + payentMode + "' and inUse = 0 and env = '" + envName + "' order by last_modified_on DESC limit 1", "test");
					updateTestDBInUse((long)order.get("order_id"),1);
					return  order.get("order_id").toString();
				}
			} catch (Exception e) {
				log.info("Unable to find order with status with : " + status + ", finding order with some diff status...");
			}
		}
		throw new IOException("Unable to find Order with the given status");
	}

	/**
	 * updateTestOrder
	 * @param order_Id
	 * @throws UnsupportedEncodingException
	 * @throws JAXBException
	 * @throws InterruptedException 
	 */
	public void updateTestOrder(String order_Id) throws UnsupportedEncodingException, JAXBException, InterruptedException{
		OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
		String packetId = omsServiceHelper.getPacketId(order_Id);
		PacketEntry packetEntry = omsServiceHelper.getPacketEntry(packetId);
		String omsStatus = packetEntry.getStatus().toString();
		try {
			if (getOrderToShipStatus(packetId).equals("CANCELLED_IN_HUB")) omsStatus = "F";
			String update = "update test_order set status_oms = '"+omsStatus+"', status_lms = '"+getOrderToShipStatus(packetId) +"' , courier_code = '"+packetEntry.getCourierCode()+"', warehouse_id = "+packetEntry.getDispatchWarehouseId()+", dc_id = "+getDCId(packetId)+", tracking_no = '"+packetEntry.getTrackingNo()+"', inUse = 0 where packet_Id = "+packetId;
			DBUtilities.exUpdateQuery(update, "test");
		}catch (Exception e) {
			String update = "update test_order set status_oms = '" + omsStatus + "', status_lms = NULL , courier_code = '" + packetEntry.getCourierCode() + "', warehouse_id = "+packetEntry.getDispatchWarehouseId()+", inUse = 0  where order_id = " + order_Id;
			DBUtilities.exUpdateQuery(update, "test");
		}
	}

	public void updateTestOrderWithOutInUse(String order_Id) throws UnsupportedEncodingException, JAXBException{
		OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
		String order_release_id = omsServiceHelper.getReleaseId(order_Id);
		PacketEntry packetEntry = omsServiceHelper.getPacketEntry(order_release_id);
		String omsStatus = packetEntry.getStatus().toString();
		try {
			if (getOrderToShipStatus(""+order_release_id).equals("CANCELLED_IN_HUB")) omsStatus = "F";
			String update = "update test_order set status_oms = '"+omsStatus+"', status_lms = '"+getOrderToShipStatus(""+order_release_id) +"' , courier_code = '"+packetEntry.getCourierCode()+"', warehouse_id = "+packetEntry.getDispatchWarehouseId()+", dc_id = "+getDCId(""+order_release_id)+", tracking_no = '"+packetEntry.getTrackingNo()+"' where order_release_id = "+order_release_id;
			DBUtilities.exUpdateQuery(update, "test");
		}catch (Exception e) {
			String update = "update test_order set status_oms = '" + omsStatus + "', status_lms = NULL , courier_code = '" + packetEntry.getCourierCode() + "', warehouse_id = "+packetEntry.getDispatchWarehouseId()+" where order_id = " + order_Id;
			DBUtilities.exUpdateQuery(update, "test");
		}
	}

	/**
	 * updateTestOrder
	 * @param order_Id
	 * @param omsStatus
	 * @throws UnsupportedEncodingException
	 * @throws JAXBException
	 */
	public void updateTestOrder(String order_Id, String omsStatus) throws UnsupportedEncodingException, JAXBException{
		OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
		String order_release_id = omsServiceHelper.getReleaseId(order_Id);
		PacketEntry packetEntry = omsServiceHelper.getPacketEntry(order_release_id);
		try {
			String update = "update test_order set status_oms = '"+omsStatus+"', status_lms = '"+getOrderToShipStatus(order_release_id) +"' , courier_code = '"+packetEntry.getCourierCode()+"' , warehouse_id = "+packetEntry.getDispatchWarehouseId()+", dc_id = \"+getDCId(order_release_id)+\",tracking_no = '"+packetEntry.getTrackingNo()+"', inUse = 0 where order_release_id = "+order_release_id;
			DBUtilities.exUpdateQuery(update, "test");
		}catch (Exception e) {
			String update = "update test_order set status_oms = '"+omsStatus+"', status_lms = NULL , courier_code = '" + packetEntry.getCourierCode() + "', warehouse_id = "+packetEntry.getDispatchWarehouseId()+", tracking_no = '"+packetEntry.getTrackingNo()+"', inUse = 0 where order_id = " + order_release_id;
			DBUtilities.exUpdateQuery(update, "test");
		}
	}

	/**
	 * updateTestDBInUse
	 * @param orderId
	 * @param inUse
	 */
	public void updateTestDBInUse(long orderId, int inUse){
		DBUtilities.exUpdateQuery("update test_order set inUse = "+inUse+" where order_id = "+orderId, "test");
	}

	/**
	 * deleteTestOrder
	 * @param orderId
	 */
	public void deleteTestOrder(String orderId){
		if (orderId!=null) DBUtilities.exUpdateQuery("delete From test_order WHERE order_id = "+orderId, "test");
	}

	/**
	 * insertOrders
	 * @param storeOrderId
	 * @param paymentMode
	 * @param ppsId
	 * @param status
	 * @param lineStatus
	 * @param zipcode
	 * @param courierCode
	 * @param warehouseId
	 * @param shippingMethod
	 * @param isTryAndBuy
	 * @return
	 * @throws Exception
	 */
	public String insertOrders(String storeOrderId, String paymentMode, String ppsId, String status, String lineStatus, String zipcode, String courierCode, String warehouseId, String shippingMethod, boolean isTryAndBuy, boolean isMultiSeller) throws IOException, JAXBException {
		OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
		String q = "INSERT INTO `orders` (`store_order_id`, `login`, `user_contact_no`, `customer_name`, `payment_method`, `mrp_total`, `discount`, `cart_discount`, `coupon_discount`, `cash_redeemed`, `pg_discount`, " +
				"`final_amount`, `shipping_charge`, `cod_charge`, `emi_charge`, `gift_charge`, `tax_amount`, `cashback_offered`, `created_by`, `order_type`, `loyalty_points_used`) " +
				"VALUES('"+storeOrderId+"', '2aa1d18c.86c9.4eff.88c0.a7ef8c677eb7TK8AVXQJJz', '1234567890', 'lmsmock', '"+paymentMode+"', 1099.00, 0.00, 0.00, 0.00, 0.00, 0.00, 1099.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, " +
				"'2aa1d18c.86c9.4eff.88c0.a7ef8c677eb7TK8AVXQJJz', 'on', 0.00)";
		DBUtilities.exUpdateQuery(q,"oms");
		String orderId = omsServiceHelper.getOrderId(storeOrderId);
		insertOrderAdditionalInfo(orderId, ppsId);
		String releaseId = insertOrderRelease(orderId, ppsId, storeOrderId, status, paymentMode, zipcode, courierCode, warehouseId, shippingMethod);
		insertOrderLine(orderId, ppsId, releaseId, storeOrderId, lineStatus ,isTryAndBuy, isMultiSeller, warehouseId, warehouseId, courierCode);
		return orderId;
	}
	
	public String insertPacketData(String releaseId, String paymentMode, String status, String zipcode, String courierCode, String warehouseId, String shippingMethod) throws IOException, JAXBException {

		LmsServiceHelper lmsServiceHelper = new LmsServiceHelper();
		String trackingNumber = "";

		if (courierCode.equals("JB")) {
			
			trackingNumber = "JB" + System.currentTimeMillis();
		} else {
			
			TrackingNumberResponse trackingNumberResponse = lmsServiceHelper.getTrackingNumber(courierCode, warehouseId, "true", zipcode, shippingMethod);
			if (trackingNumberResponse != null && trackingNumberResponse.getTrackingNumberEntry() != null) {
				trackingNumber = trackingNumberResponse.getTrackingNumberEntry().getTrackingNumber().toString();
			} else {
				throw new SkipException("TrackingNumberResponse is null");
			}
		}
		
		String createdBy = "lmsMock"+System.currentTimeMillis();
		String currentDate = LMSUtils.getCurrSqlDate();
		String q = "INSERT INTO `packet` (`store_id`, `login`, `status_code`, `payment_method`, `receiver_name`, `address_id`, `address`, `city`, `locality`, "
				+ "`state`, `country`, `zipcode`, `mobile`, `email`, `courier_code`, `tracking_no`, `dispatch_warehouse_id`, `cod_pay_status`, "
				+ "`shipped_on`, `delivered_on`, `completed_on`, `cancelled_on`, `cancellation_reason_id_fk`, `user_contact_no`, `shipping_method`, "
				+ "`recipient_email`, `seller_group_id`, `seller_packet_id`, `created_by`, `created_on`, `last_modified_on`, `version`)\n" + 				   
				" VALUES (1, '2aa1d18c.86c9.4eff.88c0.a7ef8c677eb7TK8AVXQJJz', '"+status+"', '"+paymentMode+"', 'lmsmock', 6135071, "
			    + "'Myntra test lms automation', 'Bangalore', 'Bommanahalli  &#x28;Bangalore&#x29;', 'KA', 'India', '"+zipcode+"', "
				+ "'1234567890', 'lmsautomation7@myntra.com', '"+courierCode+"', '"+trackingNumber+"', "+warehouseId+", 'pending', NULL, NULL, NULL, NULL, NULL, '1234567890', "
						+ "'"+shippingMethod+"', NULL, 1, NULL, '"+createdBy+"', '"+currentDate+"', '"+currentDate+"', 1)";
		DBUtilities.exUpdateQuery(q, "oms");
		String packetIdQuery = "select id from packet where created_by='"+createdBy+"'";
		String packetId = (long)DBUtilities.exSelectQueryForSingleRecord(packetIdQuery, "oms").get("id")+"";
		String updateOrderLineQuery = "update order_line set packet_id_fk='"+packetId+"' where order_release_id_fk='"+releaseId+"'";
		DBUtilities.exUpdateQuery(updateOrderLineQuery, "oms");
		return packetId;
	}

	public String insertOrdersWithDiffPaymentMode(String storeOrderId, String ppsId, String status, String lineStatus, String zipcode, String courierCode, String warehouseId, String shippingMethod, boolean isTryAndBuy) throws IOException, JAXBException {
		OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
		String q = "INSERT INTO `orders` (`store_order_id`, `login`, `user_contact_no`, `customer_name`, `payment_method`, `mrp_total`, `discount`, `cart_discount`, `coupon_discount`, `cash_redeemed`," +
				" `pg_discount`, `final_amount`, `shipping_charge`, `cod_charge`, `emi_charge`, `gift_charge`, `tax_amount`, `cashback_offered`, `created_by`, `order_type`, `loyalty_points_used`) " +
				"VALUES ('"+storeOrderId+"', '9900d970.06bd.45af.9786.7c77dd948ca1odZExa49ae', '1111111111', '', 'on', 4298.00, 0.00, 300.75, 159.80, 0.00, 0.00, 3897.43, 0.00, 0.00, 0.00, 25.00, " +
				"35.00, 0.00, '9900d970.06bd.45af.9786.7c77dd948ca1odZExa49ae', 'on', 0.00)";
		DBUtilities.exUpdateQuery(q,"oms");
		String orderId = omsServiceHelper.getOrderId(storeOrderId);
		insertOrderAdditionalInfo(orderId, ppsId);
		String releaseId = insertOrderReleaseWithDiffPaymnetMode(orderId, ppsId, storeOrderId, status, zipcode, courierCode, warehouseId, shippingMethod);
		insertOrderLineWithDiffPaymentMode(orderId, ppsId, releaseId, storeOrderId, lineStatus ,isTryAndBuy);
		return orderId;
	}

	/**
	 *
	 * @param orderId
	 * @param ppsId
	 */
	public void insertOrderAdditionalInfo(String orderId, String ppsId){

		String q = "INSERT INTO `order_additional_info` (`order_id_fk`, `key`, `value`, `created_by`) VALUES " +
				"("+orderId+", 'ORDER_PROCESSING_FLOW', 'OMS', 'pps-admin'), " +
				"("+orderId+", 'CHANNEL', 'web', 'pps-admin'), " +
				"("+orderId+", 'LOYALTY_CONVERSION_FACTOR', '0.5', 'pps-admin'), " +
				"("+orderId+", 'GIFT_CARD_AMOUNT', '0.0', 'pps-admin'), " +
				"("+orderId+", 'PAYMENT_PPS_ID', '"+ppsId+"', 'pps-admin'), " +
				"("+orderId+", 'ADDITIONAL_CHARGES_SELLER_ID', '21', 'pps-admin'), " +
				"("+orderId+", 'STORED_CREDIT_USAGE', '0.0', 'pps-admin'), " +
				"("+orderId+", 'EARNED_CREDIT_USAGE', '0.0', 'pps-admin')";
		DBUtilities.exUpdateQuery(q,"oms");
	}

	/**
	 *
	 * @param orderId
	 * @param ppsId
	 * @param storeOrderId
	 * @param status
	 * @param paymentMode
	 * @param zipcode
	 * @param courierCode
	 * @param warehouseId
	 * @param shippingMethod
	 * @return
	 * @throws Exception
	 */
	public String insertOrderRelease(String orderId, String ppsId, String storeOrderId, String status, String paymentMode, String zipcode, String courierCode, String warehouseId, String shippingMethod) throws IOException, JAXBException {
		LmsServiceHelper lmsServiceHelper = new LmsServiceHelper();
		OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
		String city = "Bangalore";
		
		if (zipcode.equals(LMS_PINCODE.MUMBAI_DE_RHD)){
			city = "Mumbai";
		}else if (zipcode.equals(LMS_PINCODE.NORTH_DE)){
			city = "Lalitpur";
		} else if (zipcode.equals(LMS_PINCODE.PUNE_EK)){
			city = "Pune";
		}
		
		String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		String trackingNumber = "";
		TrackingNumberResponse trackingNumberResponse = null;
		
		if (courierCode.equals("JB")) {
			trackingNumber = "JB" + System.currentTimeMillis();
		} else {
			trackingNumberResponse = lmsServiceHelper.getTrackingNumber(courierCode, warehouseId, "true", zipcode, shippingMethod);
			if (trackingNumberResponse != null && trackingNumberResponse.getTrackingNumberEntry() != null) {
				trackingNumber = trackingNumberResponse.getTrackingNumberEntry().getTrackingNumber().toString();
			} else {
				throw new SkipException("TrackingNumberResponse is null");
			}
		}

		String q = "INSERT INTO `order_release` (`order_id_fk`, `store_order_id`, `login`, `status_code`, `payment_method`, `mrp_total`, `discount`, `cart_discount`, `coupon_discount`, `cash_redeemed`, `pg_discount`, `final_amount`, `shipping_charge`, " +
					"`cod_charge`, `emi_charge`, `gift_charge`, `tax_amount`, `cashback_offered`, `receiver_name`, `address`, `city`, `locality`, `state`, `country`, `zipcode`, `mobile`, `email`, `courier_code`, `tracking_no`, " +
					"`warehouse_id`, `is_refunded`, `cod_pay_status`, `packed_on`, `created_by`, `user_contact_no`, `shipping_method`, `loyalty_points_used`, `store_id`, `queued_on`) " +
					"VALUES("+orderId+","+storeOrderId+",'2aa1d18c.86c9.4eff.88c0.a7ef8c677eb7TK8AVXQJJz', '"+status+"', '"+paymentMode+"', 1099.00, 0.00, 0.00, 0.00, 0.00, 0.00, 1099.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 'lmsmock', 'Myntra test lms automation'," +
					" '"+city+"', 'Bommanahalli  &#x28;Bangalore&#x29;', 'KA', 'India', '"+zipcode+"', '1234567890', 'lmsautomation7@myntra.com', '"+courierCode+"', '"+trackingNumber+"', "+warehouseId+", 0, 'pending', '"+LMSUtils.getCurrSqlDate()+"', " +
					"'2aa1d18c.86c9.4eff.88c0.a7ef8c677eb7TK8AVXQJJz', '1234567890', '"+shippingMethod+"', 0.00, 1, '"+date+"')";
		DBUtilities.exUpdateQuery(q,"oms");
		String releaseId = omsServiceHelper.getReleaseId(orderId);
		inserOrderReleaseAdditionalInfo(releaseId, ppsId);
		return releaseId;
	}

	public String insertOrderReleaseWithDiffPaymnetMode(String orderId, String ppsId, String storeOrderId, String status, String zipcode, String courierCode, String warehouseId, String shippingMethod) throws IOException, JAXBException {
		LmsServiceHelper lmsServiceHelper = new LmsServiceHelper();
		OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
		String city = "Bangalore";
		if (zipcode.equals(LMS_PINCODE.MUMBAI_DE_RHD)){
			city = "Mumbai";
		}else if (zipcode.equals(LMS_PINCODE.NORTH_DE)){
			city = "Lalitpur";
		} else if (zipcode.equals(LMS_PINCODE.PUNE_EK)){
			city = "Pune";
		}
		String trackingNumber = lmsServiceHelper.getTrackingNumber(courierCode,"36","true",zipcode,"NORMAL").getTrackingNumberEntry().getTrackingNumber().toString();
		
			String q = "INSERT INTO `order_release` (`order_id_fk`, `store_order_id`, `login`, `status_code`, `payment_method`, `mrp_total`, `discount`, `cart_discount`, `coupon_discount`, " +
					"`cash_redeemed`, `pg_discount`, `final_amount`, `shipping_charge`, `cod_charge`, `emi_charge`, `gift_charge`, `tax_amount`, `cashback_offered`, `receiver_name`, `address`, " +
					"`city`, `locality`, `state`, `country`, `zipcode`, `mobile`, `email`, `courier_code`, `tracking_no`, `warehouse_id`, `is_refunded`, `cod_pay_status`, `packed_on`, `created_by`, " +
					"`user_contact_no`, `shipping_method`, `loyalty_points_used`, `store_id`)" +
					" VALUES ("+orderId+",'"+storeOrderId+"', '9900d970.06bd.45af.9786.7c77dd948ca1odZExa49ae', '"+status+"', 'on', 4298.00, 0.00, 300.75, 159.80, 0.00, 0.00, 3897.43, 0.00, 0.00, 0.00, 25.00, " +
					"35.00, 0.00, 'Someone test engineer', 'Hello,&#x23;1', '"+city+"', 'Begur', 'KA', 'India', '"+zipcode+"', '9914182614', 'sfloadmohit@myntra.com', '"+courierCode+"', '"+trackingNumber+"', "+warehouseId+", 0, 'pending', '"+LMSUtils.getCurrSqlDate()+"', " +
					"'9900d970.06bd.45af.9786.7c77dd948ca1odZExa49ae', '1111111111', '"+shippingMethod+"', 0.00, 1)";
			DBUtilities.exUpdateQuery(q,"oms");
			String releaseId = omsServiceHelper.getReleaseId(orderId);
			inserOrderReleaseAdditionalInfo(releaseId, ppsId);
			return releaseId;
	}

	/**
	 *
	 * @param releaseID
	 * @param ppsId
	 */
	public void inserOrderReleaseAdditionalInfo(String releaseID, String ppsId){
		String q = "INSERT INTO `order_release_additional_info` (`order_release_id_fk`, `key`, `value`, `created_by`) VALUES" +
				"("+releaseID+", 'GIFT_CARD_AMOUNT', '0.0', 'pps-admin'), " +
				"("+releaseID+", 'LOYALTY_CONVERSION_FACTOR', '0.5', 'pps-admin'), " +
				"("+releaseID+", 'ADDITIONAL_CHARGES_SELLER_ID', '21', 'pps-admin'), " +
				"("+releaseID+", 'ADDRESS_ID', '6135071', 'pps-admin'), " +
				"("+releaseID+", 'PAYMENT_PPS_ID', '"+ppsId+"', 'pps-admin'), " +
				"("+releaseID+", 'STORED_CREDIT_USAGE', '0.0', 'pps-admin'), " +
				"("+releaseID+", 'EARNED_CREDIT_USAGE', '0.0', 'pps-admin'), " +
				"("+releaseID+", 'CUSTOMER_PROMISE_TIME', '"+LMSUtils.addDate(5)+"', 'System'), " +
				"("+releaseID+", 'EXPECTED_CUTOFF_TIME', '"+LMSUtils.addDate(2)+"', 'System'), " +
				"("+releaseID+", 'EXPECTED_PICKING_TIME', '"+LMSUtils.addDate(1)+"', 'System'), " +
				"("+releaseID+", 'EXPECTED_PACKING_TIME', '"+LMSUtils.getCurrSqlDate()+"', 'System'), " +
				"("+releaseID+", 'EXPECTED_QC_TIME', '"+LMSUtils.getCurrSqlDate()+"', 'System'), " +
				"("+releaseID+", 'SELLER_PROCESSING_START_TIME', '"+LMSUtils.getCurrSqlDate()+"', 'erpMessageQueue'), " +
				"("+releaseID+", 'SELLER_PROCESSING_END_TIME', '"+LMSUtils.getCurrSqlDate()+"', 'erpMessageQueue')";
		DBUtilities.exUpdateQuery(q, "oms");
	}

	/**
	 *
	 * @param orderId
	 * @param ppsId
	 * @param releaseId
	 * @param storeOrderId
	 * @param lineStatus
	 * @param isTryAndBuy
	 */
	public void insertOrderLine(String orderId, String ppsId, String releaseId, String storeOrderId, String lineStatus, boolean isTryAndBuy, boolean isMultiSeller, String sourceWhId, String dispatchWhId, String courierCode){
		
		if (isMultiSeller) {
			String q = "INSERT INTO `order_line` (`order_id_fk`, `order_release_id_fk`, `store_order_id`, `style_id`, `option_id`, `sku_id`, `status_code`, `source_wh_id`, `dispatch_wh_id`, `courier_code`, `unit_price`, `quantity`, `discounted_quantity`, `discount`, " +
					"`cart_discount`, `cash_redeemed`, `coupon_discount`, `pg_discount`, `final_amount`, `tax_amount`, `tax_rate`, `cashback_offered`, `disocunt_rule_id`, `discount_rule_rev_id`, `is_discounted`, `is_returnable`, " +
					"`created_by`, `loyalty_points_used`, `seller_id`, `supply_type`, `store_id`, `po_status`) VALUES" +
					"(" + orderId + ", " + releaseId + ", '" + storeOrderId + "', 1543, 5298, 3879, '" + lineStatus + "', "+sourceWhId+", "+dispatchWhId+", '"+courierCode+"', 1099.00, 1, 0, 0.00, 0.00, 0.00, 0.00, 0.00, 1099.00, 0.00, 0.00, 0.00, 0, 0, 0, 1, " +
					"'2aa1d18c.86c9.4eff.88c0.a7ef8c677eb7TK8AVXQJJz', 0.00, 21, 'ON_HAND', '1', 'UNUSED')";

			DBUtilities.exUpdateQuery(q, "oms");
			Map<String, Object> ol1 = DBUtilities.exSelectQueryForSingleRecord("select id from order_line where sku_id = 3879 and order_release_id_fk = " + releaseId, "oms");
			inserOrderLineAdditionalInfo((long) ol1.get("id"), ppsId, isTryAndBuy);
		}else {
			String q = "INSERT INTO `order_line` (`order_id_fk`, `order_release_id_fk`, `store_order_id`, `style_id`, `option_id`, `sku_id`, `status_code`, `source_wh_id`, `dispatch_wh_id`, `courier_code`, `unit_price`, `quantity`, `discounted_quantity`, `discount`, " +
					"`cart_discount`, `cash_redeemed`, `coupon_discount`, `pg_discount`, `final_amount`, `tax_amount`, `tax_rate`, `cashback_offered`, `disocunt_rule_id`, `discount_rule_rev_id`, `is_discounted`, `is_returnable`, " +
					"`created_by`, `loyalty_points_used`, `seller_id`, `supply_type`, `store_id`, `po_status`) VALUES" +
					"(" + orderId + ", " + releaseId + ", '" + storeOrderId + "', 1543, 5298, 3879, '" + lineStatus + "', "+sourceWhId+", "+dispatchWhId+", '"+courierCode+"', 1099.00, 2, 0, 0.00, 0.00, 0.00, 0.00, 0.00, 1099.00, 0.00, 0.00, 0.00, 0, 0, 0, 1, " +
					"'2aa1d18c.86c9.4eff.88c0.a7ef8c677eb7TK8AVXQJJz', 0.00, 25, 'ON_HAND', '1', 'UNUSED')";
			DBUtilities.exUpdateQuery(q, "oms");
			Map<String, Object> ol1 = DBUtilities.exSelectQueryForSingleRecord("select id from order_line where sku_id = 3879 and order_release_id_fk = " + releaseId, "oms");
			inserOrderLineAdditionalInfo((long) ol1.get("id"), ppsId,  isTryAndBuy);
		}
	}

	public void insertOrderLineWithDiffPaymentMode(String orderId, String ppsId, String releaseId, String storeOrderId, String lineStatus, boolean isTryAndBuy){

		String q = "INSERT INTO `order_line` (`order_id_fk`, `order_release_id_fk`, `store_order_id`, `style_id`, `option_id`, `sku_id`, `status_code`, `unit_price`, `quantity`, `discounted_quantity`, `discount`, " +
				"`cart_discount`, `cash_redeemed`, `coupon_discount`, `pg_discount`, `final_amount`, `tax_amount`, `tax_rate`, `cashback_offered`, `disocunt_rule_id`, `discount_rule_rev_id`, `is_discounted`, `is_returnable`, " +
				"`created_by`, `loyalty_points_used`, `seller_id`, `supply_type`, `po_status`) VALUES " +
				"(" + orderId + ", " + releaseId + ", '" + storeOrderId + "', 1542, 5229, 3872, '" + lineStatus + "', 3499.00, 1, 0, 0.00, 244.84, 0.00, 0.00, 0.00, 3254.16, 0.00, 0.00, 0.00, 0, 0, 0, 1, '9900d970.06bd.45af.9786.7c77dd948ca1odZExa49ae', 0.00, 25, 'ON_HAND', 'UNUSED'), " +
				"(" + orderId + ", " + releaseId + ", '" + storeOrderId + "', 1531, 3831, 3831, '" + lineStatus + "', 799.00, 1, 0, 0.00, 55.91, 0.00, 159.80, 0.00, 618.29, 35.00, 6.00, 0.00, 0, 0, 0, 1, '9900d970.06bd.45af.9786.7c77dd948ca1odZExa49ae', 0.00, 21, 'ON_HAND', 'UNUSED')";
		DBUtilities.exUpdateQuery(q, "oms");
		Map<String, Object> ol1 = DBUtilities.exSelectQueryForSingleRecord("select id from order_line where sku_id = 3872 and order_release_id_fk = " + releaseId, "oms");
		Map<String, Object> ol2 = DBUtilities.exSelectQueryForSingleRecord("select id from order_line where sku_id = 3831 and order_release_id_fk = " + releaseId, "oms");
		inserOrderLineAdditionalInfoDiffPaymentMode((long) ol1.get("id"), ppsId, "412.10", "2842.06", "412.10", "14.500",isTryAndBuy);
		inserOrderLineAdditionalInfoDiffPaymentMode((long) ol2.get("id"), ppsId, "608.29", "33.46", "2.92", "5.500", isTryAndBuy);
	}

	/**
	 *
	 * @param lineId
	 * @param ppsId
	 * @param isTryAndBuy
	 */
	public void inserOrderLineAdditionalInfo( long lineId, String ppsId, boolean isTryAndBuy){
		
		String q = "INSERT INTO `order_line_additional_info` (`order_line_id_fk`, `key`, `value`, `created_by`) VALUES" +
				"("+lineId+", 'FRAGILE', 'true', 'pps-admin'), " +
				"("+lineId+", 'HAZMAT', 'true', 'pps-admin'), " +
				"("+lineId+", 'JEWELLERY', 'false', 'pps-admin'), " +
				"("+lineId+", 'CUSTOMIZABLE', 'false', 'pps-admin'), " +
				"("+lineId+", 'PACKAGING_TYPE', 'NORMAL', 'pps-admin'), " +
				"("+lineId+", 'PACKAGING_STATUS', 'NOT_PACKAGED', 'pps-admin'), " +
				"("+lineId+", 'GIFT_CARD_AMOUNT', '0.0', 'pps-admin'), " +
				"("+lineId+", 'IS_EXCHANGEABLE', 'true', 'pps-admin'), " +
				"("+lineId+", 'PAYMENT_PPS_ID', '"+ppsId+"', 'pps-admin'), " +
				"("+lineId+", 'TRY_AND_BUY', '"+isTryAndBuy+"', 'pps-admin'), " +
				"("+lineId+", 'STORED_CREDIT_USAGE', '0.0', 'pps-admin'), " +
				
				
				
				"("+lineId+", 'CUSTOMER_PROMISE_TIME', '"+LMSUtils.addDate(5)+"', 'System'), " +
				"("+lineId+", 'EXPECTED_CUSTOMER_PROMISE_TIME', '"+LMSUtils.addDate(5)+"', 'System'), " +
				"("+lineId+", 'ACTUAL_CUSTOMER_PROMISE_TIME', '"+LMSUtils.addDate(5)+"', 'System'), " +
				"("+lineId+", 'PICK_ITEM_START_TIME', '"+LMSUtils.addDate(0)+"', 'System'), " +
				"("+lineId+", 'SELLER_PROCESSING_END_TIME', '"+LMSUtils.addDate(4)+"', 'System'), " +
				"("+lineId+", 'SELLER_PROCESSING_START_TIME', '"+LMSUtils.addDate(3)+"', 'System'), " +
				
				"("+lineId+", 'EARNED_CREDIT_USAGE', '0.0', 'pps-admin')";
		DBUtilities.exUpdateQuery(q,"oms");
	}

	public void inserOrderLineAdditionalInfoDiffPaymentMode( long lineId, String ppsId, String govtTaxAmount, String govtTaxableAmount, String govtUnitTaxAmount, String taxRate,boolean isTryAndBuy){
		
		String q = "INSERT INTO `order_line_additional_info` (`order_line_id_fk`, `key`, `value`, `created_by`)" +
				"VALUES" +
				"("+lineId+", 'FRAGILE', 'true', 'pps-admin')," +
				"("+lineId+", 'HAZMAT', 'true', 'pps-admin')," +
				"("+lineId+", 'JEWELLERY', 'false', 'pps-admin')," +
				"("+lineId+", 'LOYALTY_CONVERSION_FACTOR', '0.5', 'pps-admin')," +
				"("+lineId+", 'CUSTOMIZABLE', 'false', 'pps-admin')," +
				"("+lineId+", 'PACKAGING_TYPE', 'NORMAL', 'pps-admin')," +
				"("+lineId+", 'PACKAGING_STATUS', 'NOT_PACKAGED', 'pps-admin')," +
				"("+lineId+", 'GIFT_CARD_AMOUNT', '0.0', 'pps-admin')," +
				"("+lineId+", 'IS_EXCHANGEABLE', 'true', 'pps-admin')," +
				"("+lineId+", 'PAYMENT_PPS_ID', '"+ppsId+"', 'pps-admin')," +
				"("+lineId+", 'TRY_AND_BUY', '"+isTryAndBuy+"', 'pps-admin')," +
				"("+lineId+", 'FULFILLMENT_TYPE', 'IMMEDIATE', 'pps-admin')," +
				"("+lineId+", 'SELLER_PROCESSING_START_TIME', '2017-05-30 19:28:11', 'pps-admin')," +
				"("+lineId+", 'STORED_CREDIT_USAGE', '0.0', 'pps-admin')," +
				"("+lineId+", 'EARNED_CREDIT_USAGE', '0.0', 'pps-admin')," +
				"("+lineId+", 'GOVT_TAX_RATE', '"+taxRate+"', 'system')," +
				"("+lineId+", 'GOVT_TAX_TYPE', 'VAT', 'system')," +
				"("+lineId+", 'GOVT_TAX_AMOUNT', '"+govtTaxAmount+"', 'system')," +
				"("+lineId+", 'GOVT_TAXABLE_AMOUNT', '"+govtTaxableAmount+"', 'system')," +
				
				"("+lineId+", 'CUSTOMER_PROMISE_TIME', '"+LMSUtils.addDate(5)+"', 'System'), " +
				"("+lineId+", 'EXPECTED_CUSTOMER_PROMISE_TIME', '"+LMSUtils.addDate(5)+"', 'System'), " +
				"("+lineId+", 'ACTUAL_CUSTOMER_PROMISE_TIME', '"+LMSUtils.addDate(5)+"', 'System'), " +
				"("+lineId+", 'PICK_ITEM_START_TIME', '"+LMSUtils.addDate(0)+"', 'System'), " +
				"("+lineId+", 'SELLER_PROCESSING_END_TIME', '"+LMSUtils.addDate(4)+"', 'System'), " +
				"("+lineId+", 'SELLER_PROCESSING_START_TIME', '"+LMSUtils.addDate(3)+"', 'System'), " +

				"("+lineId+", 'GOVT_UNIT_TAX_AMOUNT', '"+govtUnitTaxAmount+"', 'system')";
		
		
		DBUtilities.exUpdateQuery(q,"oms");
	}

	/**
	 *
	 * @param ppsId
	 * @param storeOrderId
	 * @param paymentInstrument
	 */
	public void insertPaymentPlan(String ppsId, String storeOrderId, int paymentInstrument, boolean isMulriSeller){
		String q = "INSERT INTO `payment_plan` (`id`, `comments`, `updatedBy`, `updatedTimestamp`, `actionType`, `login`, `orderId`, `ppsType`, `sourceId`, `state`, `sessionId`, `cartContext`, `totalAmount`, `clientIP`) " +
				"VALUES('"+ppsId+"', 'PPS Plan created', 'SYSTEM', 1483946011537, 'SALE', '2aa1d18c.86c9.4eff.88c0.a7ef8c677eb7TK8AVXQJJz', '"+storeOrderId+"', 'ORDER', " +
				"'e2c75adf-9179-48a8-92c9-94042a055c44', 'PPFSM Order Taking done', 'JJN1c52f20ed63b11e6b03422000a90a0271483946004G', 'DEFAULT', 109900, '1.1.1.1')";
		DBUtilities.exUpdateQuery(q, "pps");
		paymentPlanExecutionStatus(ppsId, paymentInstrument);
		insertPaymentPlanItem(ppsId, paymentInstrument, isMulriSeller);
	}

	public void insertPaymentPlanWithDiffPaymentMode(String ppsId, String storeOrderId){
		String q = "INSERT INTO `payment_plan` (`id`, `comments`, `updatedBy`, `updatedTimestamp`, `actionType`, `login`, `orderId`, `ppsType`, `sourceId`, `state`, `sessionId`, `cartContext`, `totalAmount`, `clientIP`) " +
				"VALUES('"+ppsId+"', 'PPS Plan created', 'SYSTEM', 1496152870171, 'SALE', '9900d970.06bd.45af.9786.7c77dd948ca1odZExa49ae', '"+storeOrderId+"', 'ORDER', '36e25b50-8b79-4351-a879-bb7474b9d6b1', " +
				"'PPFSM Order Taking done', 'JJNb575a734453911e7921006727850f2bd1496149981G', 'DEFAULT', 389744, '121.244.34.250')";
		DBUtilities.exUpdateQuery(q, "pps");
		paymentPlanExecutionStatusWithDiffPM(ppsId);
		insertPaymentPlanItemWithDiffPM(ppsId);
	}

	/**
	 *
	 * @param ppsId
	 * @param paymentInstrument
	 */
	public void paymentPlanExecutionStatus(String ppsId, int paymentInstrument){
		
		String random = "9"+LMSUtils.randomGenn(9);
		String q = "INSERT INTO `payment_plan_execution_status` (`comments`, `updatedBy`, `updatedTimestamp`, `actionType`, `instrumentTransactionId`, `invoker`, `invokerTransactionId`, `numOfRetriesDone`, " +
				"`ppsActionType`, `state`, `status`, `paymentPlanInstrumentDetailId`) VALUES	" +
				"('Payment Plan Execution Status created', 'SYSTEM', 1483946011494, 'DEBIT', 'COD:d35a502f-0785-4d4b-84ab-07b7d64beaec', 'pps', '5bdc8ac0-0b04-42b5-baad-ac41c8c2819b', 0, 'SALE', " +
				"'PIFSM Payment Successful', 0, "+random+")";
		DBUtilities.exUpdateQuery(q, "pps");
		Map<String, Object> execution = DBUtilities.exSelectQueryForSingleRecord("select id from payment_plan_execution_status where paymentPlanInstrumentDetailId = "+random,"pps");
		String instrumentId = insertPaymentInstrument(ppsId, paymentInstrument, execution.get("id").toString());
		DBUtilities.exUpdateQuery("update payment_plan_execution_status set paymentPlanInstrumentDetailId = "+instrumentId+" where id = "+execution.get("id").toString(), "pps");
	}

	public long plusValue(long value, long add){
		return value+add;
	}

	public void paymentPlanExecutionStatusWithDiffPM(String ppsId){
		
		String random = "9"+LMSUtils.randomGenn(9);
		String q = "INSERT INTO `payment_plan_execution_status` (`comments`, `updatedBy`, `updatedTimestamp`, `actionType`, `instrumentTransactionId`, `invoker`, `invokerTransactionId`, `numOfRetriesDone`, `ppsActionType`, `state`, `status`, `paymentPlanInstrumentDetailId`) VALUES" +
				"('Payment Plan Execution Status created', 'SYSTEM', 1496152866411, 'DEBIT', '4221451', 'pps', '3387ab8f-c7d2-40db-ac6d-84da6c8da896', 0, 'SALE', NULL, 0, "+random+")," +
				"('Payment Plan Execution Status created', 'SYSTEM', 1496152870108, 'DEBIT', '101010101084980', 'pps', 'ee0ea62e-81b1-4e55-9b4e-c788b67500c4', 0, 'SALE', 'PIFSM Payment Successful', 0, "+plusValue(Long.parseLong(random),1)+")," +
				"('Payment Plan Execution Status created', 'SYSTEM', 1496152870108, 'DEBIT', '40012050', 'pps', 'b7a29406-847b-4f44-979e-b1dafdca06b7', 0, 'SALE', 'PIFSM Payment Successful', 0, "+plusValue(Long.parseLong(random),2)+")," +
				"('Payment Plan Execution Status created', 'SYSTEM', 1496152870108, 'DEBIT', '862e310b-970e-4355-9821-9beb6f344811', 'pps', '0614b254-106f-4e60-8299-a77b29083466', 0, 'SALE', 'PIFSM Payment Successful', 0, "+plusValue(Long.parseLong(random),3)+")," +
				"('Payment Plan Execution Status created', 'SYSTEM', 1496152870109, 'DEBIT', 'MYNTS:e97443b6-343d-473d-8359-600e9e40d5e5', 'pps', '2acd9fd6-d6fc-4413-bf7e-63ad2049b5f7', 0, 'SALE', 'PIFSM Payment Successful', 0, "+plusValue(Long.parseLong(random),4)+")";
		DBUtilities.exUpdateQuery(q, "pps");
		Map<String, Object> execution = DBUtilities.exSelectQueryForSingleRecord("select id from payment_plan_execution_status where paymentPlanInstrumentDetailId = "+(random),"pps");
		String instrumentId = insertPaymentInstrumentWithDiffPM(ppsId, execution.get("id").toString());
		DBUtilities.exUpdateQuery("update payment_plan_execution_status set paymentPlanInstrumentDetailId = "+instrumentId+" where id = "+execution.get("id").toString(), "pps");
	}

	/**
	 *
	 * @param ppsId
	 * @param paymentInstrument
	 * @param executionId
	 * @return
	 */
	public String insertPaymentInstrument(String ppsId, int paymentInstrument, String executionId){
		String q = "INSERT INTO `payment_plan_instrument_details` (`comments`, `updatedBy`, `updatedTimestamp`, `paymentInstrumentType`, `totalPrice`, `pps_Id`, `paymentPlanExecutionStatus_id`, `actionType`) VALUES " +
				"('PPS Plan Instrument Details created', 'SYSTEM', 1483946011494, "+paymentInstrument+", 109900, '"+ppsId+"', "+executionId+", 'DEBIT')";
		DBUtilities.exUpdateQuery(q, "pps");
		Map<String, Object> instrument = DBUtilities.exSelectQueryForSingleRecord("select id from payment_plan_instrument_details where pps_Id = '"+ppsId+"'", "pps");
		return instrument.get("id").toString();
	}

	public String insertPaymentInstrumentWithDiffPM(String ppsId, String executionId){
		String q = "INSERT INTO `payment_plan_instrument_details` (`comments`, `updatedBy`, `updatedTimestamp`, `paymentInstrumentType`, `totalPrice`, `pps_Id`, `paymentPlanExecutionStatus_id`, `actionType`) VALUES " +
				"('PPS Plan Instrument Details created', 'SYSTEM', 1496152870108, 15, 9098, '"+ppsId+"', "+plusValue(Long.parseLong(executionId),3)+", 'DEBIT')," +
				"('PPS Plan Instrument Details created', 'SYSTEM', 1496152870108, 10, 18292, '"+ppsId+"', "+plusValue(Long.parseLong(executionId),2)+", 'DEBIT')," +
				"('PPS Plan Instrument Details created', 'SYSTEM', 1496152870108, 7, 116172, '"+ppsId+"', "+plusValue(Long.parseLong(executionId),1)+", 'DEBIT')," +
				"('PPS Plan Instrument Details created', 'SYSTEM', 1496152864859, 1, 168720, '"+ppsId+"', "+executionId+", 'DEBIT')," +
				"('PPS Plan Instrument Details created', 'SYSTEM', 1496152870109, 13, 77447, '"+ppsId+"', "+plusValue(Long.parseLong(executionId),4)+", 'DEBIT')";
		DBUtilities.exUpdateQuery(q, "pps");
		Map<String, Object> instrument = DBUtilities.exSelectQueryForSingleRecord("select id from payment_plan_instrument_details where pps_Id = '"+ppsId+"'", "pps");
		return instrument.get("id").toString();
	}

	/**
	 *
	 * @param ppsId
	 * @param paymentInstrument
	 */
	public void insertPaymentPlanItem(String ppsId, int paymentInstrument, boolean isMultiSeller){
		if (isMultiSeller) {
			String q = "INSERT INTO `payment_plan_item` (`comments`, `updatedBy`, `updatedTimestamp`, `itemType`, `pricePerUnit`, `quantity`, `sellerId`, `skuId`, `pps_Id`) VALUES " +
					//"('Payment Plan Item created', 'SYSTEM', 1483946011431, 'SKU', 109900, 2, '19', '3879', '" + ppsId + "'), " +
					"('Payment Plan Item created', 'SYSTEM', 1483946011433, 'SKU', 109900, 1, '25', '3879', '" + ppsId + "')";
			DBUtilities.exUpdateQuery(q,"pps");
		}else {
			String q = "INSERT INTO `payment_plan_item` (`comments`, `updatedBy`, `updatedTimestamp`, `itemType`, `pricePerUnit`, `quantity`, `sellerId`, `skuId`, `pps_Id`) VALUES " +
					//"('Payment Plan Item created', 'SYSTEM', 1483946011431, 'SKU', 109900, 2, '25', '3879', '" + ppsId + "'), " +
					"('Payment Plan Item created', 'SYSTEM', 1483946011433, 'SKU', 109900, 1, '25', '3879', '" + ppsId + "')";
			DBUtilities.exUpdateQuery(q,"pps");
		}
		Map<String, Object> ppi1 = DBUtilities.exSelectQueryForSingleRecord("select id from payment_plan_item where skuId = '3879' and pps_Id = '"+ppsId+"'", "pps");
		//Map<String, Object> ppi2 = DBUtilities.exSelectQueryForSingleRecord("select id from payment_plan_item where skuId = '3875' and pps_Id = '"+ppsId+"'", "pps");
		insertPaymentPlanItemInstrument(ppi1.get("id").toString(), paymentInstrument, "109900");
		//insertPaymentPlanItemInstrument(ppi2.get("id").toString(), paymentInstrument, "109900");
	}

	public void insertPaymentPlanItemWithDiffPM(String ppsId){
			String q = "INSERT INTO `payment_plan_item` (`comments`, `updatedBy`, `updatedTimestamp`, `itemType`, `pricePerUnit`, `quantity`, `sellerId`, `skuId`, `pps_Id`) VALUES" +
					"('Payment Plan Item created', 'SYSTEM', 1496152864502, 'SKU', 61828, 1, '21', '3831', '"+ppsId+"')," +
					"('Payment Plan Item created', 'SYSTEM', 1496152864513, 'SKU', 325415, 1, '25', '3872', '"+ppsId+"')," +
					"('Payment Plan Item created', 'SYSTEM', 1496152864524, 'GIFT_WRAP_CHARGES', 2500, 1, '21', 'PPS_9994', '"+ppsId+"')";
			DBUtilities.exUpdateQuery(q,"pps");
		Map<String, Object> ppi1 = DBUtilities.exSelectQueryForSingleRecord("select id from payment_plan_item where skuId = '3831' and pps_Id = '"+ppsId+"'", "pps");
		Map<String, Object> ppi2 = DBUtilities.exSelectQueryForSingleRecord("select id from payment_plan_item where skuId = '3872' and pps_Id = '"+ppsId+"'", "pps");
		Map<String, Object> ppi3 = DBUtilities.exSelectQueryForSingleRecord("select id from payment_plan_item where skuId = 'PPS_9994' and pps_Id = '"+ppsId+"'", "pps");
		insertPaymentPlanItemInstrumentWithDiffPM(ppi1.get("id").toString(), ppi2.get("id").toString(),ppi3.get("id").toString());
	}

	/**
	 *
	 * @param ppsItemId
	 * @param paymentInstrument
	 * @param amount
	 */
	public void insertPaymentPlanItemInstrument(String ppsItemId, int paymentInstrument, String amount){
		String q = "INSERT INTO `payment_plan_item_instrument` (`comments`, `updatedBy`, `updatedTimestamp`, `amount`, `paymentInstrumentType`, `ppsItemId`) VALUES" +
				"('Payment Plan Item Instrument Detail created', 'SYSTEM', 1483946011432, "+amount+", "+paymentInstrument+", "+ppsItemId+")";
		DBUtilities.exUpdateQuery(q, "pps");
	}

	public void insertPaymentPlanItemInstrumentWithDiffPM(String ppsItemId,String ppsItemId1,String ppsItemId2 ){
		String q = "INSERT INTO `payment_plan_item_instrument` (`comments`, `updatedBy`, `updatedTimestamp`, `amount`, `paymentInstrumentType`, `ppsItemId`)" +
				"VALUES" +
				"('Payment Plan Item Instrument Detail created', 'SYSTEM', 1496152864506, 12365, 13, "+ppsItemId+")," +
				"('Payment Plan Item Instrument Detail created', 'SYSTEM', 1496152864507, 26723, 1, "+ppsItemId+")," +
				"('Payment Plan Item Instrument Detail created', 'SYSTEM', 1496152864509, 2897, 10, "+ppsItemId+")," +
				"('Payment Plan Item Instrument Detail created', 'SYSTEM', 1496152864510, 18400, 7, "+ppsItemId+")," +
				"('Payment Plan Item Instrument Detail created', 'SYSTEM', 1496152864512, 1441, 15, "+ppsItemId+")," +
				"('Payment Plan Item Instrument Detail created', 'SYSTEM', 1496152864517, 15249, 10, "+ppsItemId1+")," +
				"('Payment Plan Item Instrument Detail created', 'SYSTEM', 1496152864518, 140646, 1, "+ppsItemId1+")," +
				"('Payment Plan Item Instrument Detail created', 'SYSTEM', 1496152864519, 7585, 15, "+ppsItemId1+")," +
				"('Payment Plan Item Instrument Detail created', 'SYSTEM', 1496152864521, 65082, 13, "+ppsItemId1+")," +
				"('Payment Plan Item Instrument Detail created', 'SYSTEM', 1496152864522, 96842, 7, "+ppsItemId1+")," +
				"('Payment Plan Item Instrument Detail created', 'SYSTEM', 1496152864527, 1351, 1, "+ppsItemId2+")," +
				"('Payment Plan Item Instrument Detail created', 'SYSTEM', 1496152864528, 146, 10, "+ppsItemId2+")," +
				"('Payment Plan Item Instrument Detail created', 'SYSTEM', 1496152864530, 930, 7, "+ppsItemId2+")," +
				"('Payment Plan Item Instrument Detail created', 'SYSTEM', 1496152864531, 72, 15, "+ppsItemId2+")";
		DBUtilities.exUpdateQuery(q, "pps");
	}

	/**
	 *
	 * @param skuId
	 * @param warehosueId
	 * @param qty
	 * @param releaseId
	 */
	public synchronized void insertItemWithOrder(String skuId, String warehosueId, int qty, String releaseId){
		
		Map<String, String> binEntry = new HashMap<>();
		binEntry.put("1", "403");
		binEntry.put("19", "151901");
		binEntry.put("28", "271521");
		binEntry.put("36", "577992");
		
		for (int i =0; i<qty;i++) {
			
			long barcode = getMaxItemId()+1;
			DBUtilities.exUpdateQuery("INSERT INTO `item` (`barcode`, `sku_id`, `quality`, `item_status`, `warehouse_id`, `enabled`, `po_id`, `po_barcode`, `po_sku_id`, `lot_id`, `lot_barcode`, " +
					"`comments`, `order_id`, `bin_id`) VALUES ("+barcode+", " + skuId + ", 'Q1', 'SHIPPED', " + warehosueId + ", 1, 313, 'OPST050911-09', 1, 1, 'LOTVHGA-01', 'Automation item', "+releaseId+", " +
					"" + binEntry.get("" + warehosueId) + ")", "wms");
//			DBUtilities.exUpdateQuery("INSERT INTO `item_info` ( `item_id`, `item_action_status`, `task_id`, `order_barcode`, `created_on`, `created_by`, `last_modified_on`, `version`, `order_id`, " +
//					"`invoice_sku_id`, `agreement_type`, `buyer_id`) VALUES ("+barcode+", 'NEW', NULL, NULL, now(), 'erpMessageQueue', now(), 0, NULL, NULL, 'OUTRIGHT', 2297)", "wms");
			DBUtilities.exUpdateQuery("INSERT INTO `item_info` ( `item_id`, `item_action_status`, `task_id`, `order_barcode`, `created_on`, `created_by`, `last_modified_on`, `version`, `order_id`, " +
					"`invoice_sku_id`, `agreement_type`, `buyer_id`) VALUES ("+barcode+", 'NEW', NULL, NULL, now(), 'erpMessageQueue', now(), 0, NULL, NULL, 'OUTRIGHT', 2297)", "wms");
		
			String query = "INSERT INTO `order_release_item` (`oms_release_id`, `worms_release_id`, `item_barcode`, `sku_id`, `shipped_on`, `return_id`, `return_type`, `return_received_on`, `return_restocked_on`, `status`, `created_by`, `created_on`, `last_modified_on`, `version`, `supply_type`, `warehouse_id`, `return_warehouse_id`, `quality`, `reject_reason`, `reject_reason_description`, `invoice_release_transaction_id`, `creditnote_release_transaction_id`)\n" + 
						   "VALUES\n" + 
						   " ('"+releaseId+"', NULL, '"+barcode+"', '"+skuId+"', '"+LMSUtils.getCurrSqlDate()+"', NULL, NULL, NULL, NULL, 'SHIPPED', 'erpadmin', '"+LMSUtils.getCurrSqlDate()+"', '"+LMSUtils.getCurrSqlDate()+"', 0, 'ON_HAND', "+warehosueId+", NULL, NULL, NULL, NULL, 6051, NULL)";
			DBUtilities.exUpdateQuery(query, "wms");
		}
	}
	
	public synchronized void insertCaptureOrderRelease(String releaseId, String orderId, String skuId, String warehosueId, int qty, String shippingMethod, Double finalAmount, String pincode, String paymentMethod, String receiverName){
		
		String sysDate = LMSUtils.getCurrSqlDate();
		String query = "INSERT INTO `capture_order_release` (`portal_order_release_id`, `order_id`, `order_date`, `order_release_status`, `warehouse_id`, `dispatch_warehouse_id`, "
				+ "`cutoff_time`, `created_by`, `created_on`, `last_modified_on`, `is_on_hold`, `version`, `is_gift`, `is_express`, "
				+ "`is_threshold_breached`, `description`, `is_single`, `pushed_to_pick_time`, `is_special`, `store_id`, "
				+ "`pick_type`, `wave_cycle_count`, `seller_id`, `seller_processing_start_time`, `seller_processing_end_time`, "
				+ "`order_allocation_type`, `shipping_charge`, `gift_charge`, `final_amount`, `receiver_name`, `address`, `city`, "
				+ "`locality`, `state`, `zipcode`, `country`, `mobile`, `cancellation_reason`, `shipping_method`, `expected_delivery_date`, "
				+ "`tax_amount`, `cod_charge`, `mrp_total`, `discount`, `coupon_discount`, `cart_discount`, `pg_discount`, `payment_method`, "
				+ "`customer_id`, `address_id`, `is_consolidatable`, `inventory_warehouse_start_time`, `inventory_warehouse_end_time`, "
				+ "`dispatch_warehouse_start_time`, `dispatch_warehouse_end_time`)\n" + 				
				"VALUES\n" + 
				"	("+releaseId+", "+orderId+", '"+sysDate+"', 'COMPLETED', "+warehosueId+", "+warehosueId+", '"+sysDate+"', 'erpMessageQueue', "
				+ "'"+sysDate+"', '"+sysDate+"', 0, 1, 0, 0, 0, 'SINGLE_ITEM_SHIPMENT', 1, '"+sysDate+"', 0, 1, "
				+ "'NORMAL', 1, NULL, '"+sysDate+"', '"+sysDate+"', 'MYNTRA', 4.00, 0.00, 822.17, '"+receiverName+"', "
				+ "'Flat no 407, Laa lavender, Old mangammanapalaya road, Bommanahalli', 'Bangalore, ', 'Bommanahalli  &#x28;Bangalore&#x29;, ', "
				+ "'KA', '"+pincode+"', 'India', '1234567890', NULL, '"+shippingMethod+"', '"+sysDate+"', 179.00, 0.00, 799.00, 159.80, 0.00, 0.00, 0.00, "
				+ "'"+paymentMethod+"', '8002d0d6.7152.4488.9492.acb3ea14920fF4LpgjTxYE', '6140348', 0, '"+sysDate+"', NULL, NULL, '"+sysDate+"');";
		
		DBUtilities.exUpdateQuery(query , "wms");
		String query2 = "select id from capture_order_release where receiver_name='"+receiverName+"'";
		Long captureOrdeReleaseId = (Long)DBUtilities.exSelectQueryForSingleRecord(query2, "wms").get("id");
		insertCaptureOrderReleaseLine(captureOrdeReleaseId+"", skuId);
	}
	
	public synchronized void insertCaptureOrderReleaseLine(String captureOrdeReleaseId, String skuId){
		
		String sysDate = LMSUtils.getCurrSqlDate();
		String query = "INSERT INTO `capture_order_release_line` (`order_release_id`, `sku_id`, `quantity`, `created_by`, `created_on`, `last_modified_on`, `version`, "
				+ "`is_fragile`, `cancelled_quantity`, `unit_price`, `unit_sale_price`, `unit_seller_discount`, `unit_marketplace_discount`, "
				+ "`final_amount`, `cancellation_reason`, `tax_rate`, `tax_amount`, `govt_tax_type`, `govt_tax_rate`, `govt_tax_amount`, "
				+ "`govt_taxable_amount`, `govt_unit_tax_amount`, `discounted_quantity`, `coupon_discount`, `cart_discount`, "
				+ "`seller_id`, `order_release_line_status`, `vendor_id`, `supply_type`)\n" + 
				"VALUES\n" + 
				"	("+captureOrdeReleaseId+", "+skuId+", 1, 'erpMessageQueue', '"+sysDate+"', '"+sysDate+"', 0, 0, NULL, 2.00, NULL, NULL, NULL, "
				+ "2.00, NULL, 0.00, 0.00, NULL, NULL, NULL, NULL, NULL, 0, 0.00, 0.00, 21, 'CREATED', NULL, 'ON_HAND');\n" + 
				"";
		
		DBUtilities.exUpdateQuery(query , "wms");
	}


	public long getMaxItemId(){
		Map<String, Object> getId = DBUtilities.exSelectQueryForSingleRecord("select max(id) from item","wms");
		return (Long)getId.get("max(id)");
	}

	/**
	 *
	 * @param releaseId
	 * @param warehouseId
	 */
	@SuppressWarnings("unchecked")
	public void insertWMSItem(String releaseId, String warehouseId){
		List<Map<String, Object>> orderLine = DBUtilities.exSelectQuery("select * from order_line where order_release_id_fk = "+releaseId, "oms");
		for(Map<String, Object> line: orderLine){
			int qty = (Integer)line.get("quantity");
			insertItemWithOrder(line.get("sku_id").toString(), warehouseId, qty, releaseId);
		}
	}

	/**
	 * updateOrderTrackingToAccepted
	 * @param releaseId
	 */
	public void updateOrderTrackingToAccepted(String releaseId){
		DBUtilities.exUpdateQuery("update order_tracking set courier_creation_status = 'ACCEPTED' where order_id = '" +releaseId+"'", "lms");
	}

	/**
	 * createMockOrder
	 * @return
	 * @throws Exception
	 */
	
	public List<Map<String, Object>> getOrderDetails(String storeOrderId){
		
		String selectQuery = "select order_release_id_fk, order_id_fk, sku_id, source_wh_id, dispatch_wh_id, quantity from order_line where store_order_id = '"+ storeOrderId +"'";
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> list = DBUtilities.exSelectQuery(selectQuery, "myntra_oms");
		return list;
	}
	
	public Map<Integer, String> getWarehouseIdsAndPickTypes(String OrderIdFk){
		
		Map<Integer, String> warehouseIds = new HashMap<>();
		String selectQuery = "select warehouse_id, pick_type from capture_order_release where order_id = "+ OrderIdFk;
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> list = DBUtilities.exSelectQuery(selectQuery, "myntra_wms");

		for (Map<String, Object> list1 : list) {
			
			warehouseIds.put(Integer.parseInt(list1.get("warehouse_id").toString()), list1.get("pick_type").toString());
		}
		return warehouseIds;
	}
	
	public void pushOrderToWave(Map<Integer, String> warehouseIdsAndPickTypes) throws UnsupportedEncodingException, InterruptedException { 
		
		WMSServiceHelper wmsServiceHelper = new WMSServiceHelper();

		for(Integer warehouseId : warehouseIdsAndPickTypes.keySet()){
			@SuppressWarnings("static-access")
			Svc service = HttpExecutorService.executeHttpService(Constants.WMS_PATH.PUSHORDERWMS,
					new String[] { warehouseId + "?pickType=" + warehouseIdsAndPickTypes.get(warehouseId) }, SERVICE_TYPE.WMS_SVC.toString(), HTTPMethods.PUT,
					null, wmsServiceHelper.getWMSHeader());
			String pushOrderResponse = service.getResponseBody();
			Thread.sleep(2000);
			if (!APIUtilities.getElement(pushOrderResponse, "orderReleaseResponse.status.statusType", "XML")
					.equalsIgnoreCase("SUCCESS")) {
				//creteRelaseInCore_order_release(releaseId.toString());
				log.info("Failed to Push the Order to Order wave");
				//SlackMessenger.send("scm_e2e_order_sanity", "Failed to Push the Order to Order wave", 2);
				Assert.fail("Failed to Push the Order to Order wave");
			}
		}
	}
	
	public void markVirtualPacketPicked(long consolidationPacketId) throws UnsupportedEncodingException, JAXBException{
		
		String query = "select id from virtual_packets where consolidation_packet_id = "+consolidationPacketId;
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> list = DBUtilities.exSelectQuery(query, "myntra_wms");
		
		for(Map<String, Object> list1 : list){
			
			VirtualPacketEntry virtualPacketEntry = new VirtualPacketEntry();
			virtualPacketEntry.setId((Long)list1.get("id"));
			
			List<PacketItemEntry> packetItemEntries = new ArrayList<>();
			
			query = "select item_barcode from packet_items where virtual_packet_id = "+list1.get("id") +" and consolidation_packet_id = "+ consolidationPacketId;
			@SuppressWarnings("unchecked")
			List<Map<String, Object>> items = DBUtilities.exSelectQuery(query, "myntra_wms");
			for(Map<String, Object> item : items){
				
				PacketItemEntry packetItemEntry = new PacketItemEntry();
				packetItemEntry.setItemBarcode(item.get("item_barcode").toString());
				packetItemEntry.setPacketItemStatus(PacketItemStatus.PICKED);
				packetItemEntries.add(packetItemEntry);
			}
			
			virtualPacketEntry.setPacketItemEntries(packetItemEntries);
			
			Svc service = HttpExecutorService.executeHttpService(Constants.WMS_PATH.LMC_MARK_PACKET_PICKED,
					new String[] {}, SERVICE_TYPE.WMS_SVC.toString(), HTTPMethods.POST,
					APIUtilities.convertXMLObjectToString(virtualPacketEntry), WMSServiceHelper.getWMSHeader());
			log.info(service.getResponseBody());
			VirtualPacketResponse response = (VirtualPacketResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(), new VirtualPacketResponse());
			Assert.assertEquals(response.getStatus().getStatusMessage(), "Success", response.getStatus().getStatusMessage());
			log.info(response.getStatus().getStatusMessage());
		}
	}
	
	public List<Map<String, Object>> insertItems(String skuId, String warehosueId, int qty, int buyerId, int poId,
			int poSkuId) {

		Map<String, String> binEntry = new HashMap<>();
		binEntry.put("1", "403");
		binEntry.put("19", "151924");
		binEntry.put("28", "271760");
		binEntry.put("36", "1324873");

		for (int i = 0; i < qty; i++) {

			long id = getMaxItemId() + 1;

			DBUtilities.exUpdateQuery(
					"INSERT INTO `item` (id, `barcode`, `sku_id`, `quality`, `item_status`, `warehouse_id`, `enabled`, `po_id`, `po_barcode`, `po_sku_id`, `lot_id`, `lot_barcode`, "
							+ "`comments`, `order_id`, `bin_id`) VALUES (" + id + ", " + id + ", " + skuId
							+ ", 'Q1', 'STORED', " + warehosueId + ", 1, " + poId + ", 'OPST050911-09', " + poSkuId
							+ ", 1, 'LOTVHGA-01', 'Automatio item', NULL, " + "" + binEntry.get("" + warehosueId) + ")",
					"wms");

			String insertItemQuery = "INSERT INTO `item_info` (`item_id`, `item_action_status`, `task_id`, `order_barcode`, `created_on`, `created_by`, `last_modified_on`, `version`, `order_id`, `invoice_sku_id`, `agreement_type`, `buyer_id`)"
					+ "VALUES(" + id
					+ ", 'NEW', NULL, NULL, now(), 'erpMessageQueue', now(), 0, NULL, NULL, 'OUTRIGHT', " + buyerId
					+ ")";
			DBUtilities.exUpdateQuery(insertItemQuery, "myntra_wms");

		}

		String selectQuery = "select barcode from item where sku_id = " + skuId + " and po_id = " + poId
				+ " and po_sku_id = " + poSkuId + " and warehouse_id = " + warehosueId + " order by id desc limit "
				+ qty;
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> list = DBUtilities.exSelectQuery(selectQuery, "myntra_wms");
		return list;
	}
	
	public void itemCheckout(List<Map<String, Object>> list, String orderId) throws UnsupportedEncodingException, JAXBException{
		
		WMSServiceHelper wmsServiceHelper = new WMSServiceHelper();
		List<ItemEntry> items = new ArrayList<ItemEntry>();
		ItemEntry item;
		for (Map<String, Object> list1 : list) {
			log.info(list1.get("barcode"));
			item = new ItemEntry();
			item.setId(Long.parseLong(list1.get("barcode").toString()));
			items.add(item);
		}
		
		CheckoutEntry entry = new CheckoutEntry();
		entry.setOrderId(orderId);
		entry.setCartonBarcode("");
		entry.setItems(items);
		
		//String checkout = APIUtilities.convertXMLObjectToString(entry);
		
		@SuppressWarnings("static-access")
		Svc service = HttpExecutorService.executeHttpService(Constants.WMS_PATH.ITEM_CHECKOUT,
				new String[] { "checkout" }, SERVICE_TYPE.WMS_SVC.toString(), HTTPMethods.POST,
				APIUtilities.convertXMLObjectToString(entry), wmsServiceHelper.getWMSHeader());
		log.info(service.getResponseBody());
		ItemResponse response = (ItemResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(), new ItemResponse());
		log.info(response.getStatus().getStatusMessage());
		Assert.assertEquals(response.getStatus().getStatusMessage(), "Items checkout for the order successfully", response.getStatus().getStatusMessage());
	}
	
	public String lmcSorting(String itemBarcode) throws UnsupportedEncodingException, JAXBException, JSONException, XMLStreamException{
		
		Svc service = HttpExecutorService.executeHttpService(Constants.WMS_PATH.LMC_ITEM_INSCAN,
				new String[] { ""+ itemBarcode}, SERVICE_TYPE.WMS_SVC.toString(), HTTPMethods.GET,
				null, WMSServiceHelper.getWMSHeader());
		log.info(service.getResponseBody());
		//String res = service.getResponseBody().replaceAll("lmcItemScanResponse", "lmcItemInScanResponse");
		//log.info(res);
		LmcItemInScanResponse response = (LmcItemInScanResponse) APIUtilities.jsonToObject(service.getResponseBody(), LmcItemInScanResponse.class);
		//Assert.assertEquals(response.getStatus().getStatusType().toString(), "SUCCESS");
		if(response.getBinNumber() == null){
			Assert.fail("BinBarcode was Null");
		}
		return response.getBinNumber();
	}
	
	public VirtualPacketResponse flushBin(String binBarcode) throws UnsupportedEncodingException, JAXBException{
		Svc service = HttpExecutorService.executeHttpService(Constants.WMS_PATH.LMC_FLUSH_BIN,
				new String[] { "flushBin?binBarcode="+binBarcode}, SERVICE_TYPE.WMS_SVC.toString(), HTTPMethods.POST,
				null, WMSServiceHelper.getWMSHeader());
		log.info(service.getResponseBody());
		VirtualPacketResponse response = (VirtualPacketResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(), new VirtualPacketResponse());
		Assert.assertEquals(response.getStatus().getStatusType().toString(), "SUCCESS", response.getStatus().getStatusMessage());
		return response;
	}
	
	public Map<String, Integer> getPaymentInsturmentId() {

		Map<String, Integer> paymentInsturmentIdMethodMap = new HashMap<>();

		paymentInsturmentIdMethodMap.put("cod", 5);
		paymentInsturmentIdMethodMap.put("COD", 5);
		paymentInsturmentIdMethodMap.put("CC", 1);
		paymentInsturmentIdMethodMap.put("DC", 2);
		paymentInsturmentIdMethodMap.put("WALLET", 10);
		paymentInsturmentIdMethodMap.put("NETBANKING", 4);
		paymentInsturmentIdMethodMap.put("ON", 1);
		paymentInsturmentIdMethodMap.put("on", 1);

		return paymentInsturmentIdMethodMap;
	}
	
	public Map<String, String> getPaymentInsturmentMethodMap() {

		Map<String, String> paymentInsturmentIdMethodMap = new HashMap<>();

		paymentInsturmentIdMethodMap.put("cod", "cod");
		paymentInsturmentIdMethodMap.put("COD", "cod");
		paymentInsturmentIdMethodMap.put("CC", "on");
		paymentInsturmentIdMethodMap.put("DC", "on");
		paymentInsturmentIdMethodMap.put("WALLET", "on");
		paymentInsturmentIdMethodMap.put("NETBANKING", "on");
		paymentInsturmentIdMethodMap.put("ON", "on");
		paymentInsturmentIdMethodMap.put("on", "on");

		return paymentInsturmentIdMethodMap;
	}

	public void insertInWMSForTODOrder(boolean isTryAndBuy, String packetId, String orderReleaseId, String orderId, String shippingMethod, String warehouseId, String zipcode, String paymentMode) throws UnsupportedEncodingException, JAXBException {
		
		OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
		
		//if (isTryAndBuy) {
			
			String receiverName = "lmsMock" + System.currentTimeMillis();
			PacketEntry packetEntry = omsServiceHelper.getPacketEntry(packetId);
			Double finalAmount = getFinalAmount(packetEntry);
			insertCaptureOrderRelease(orderReleaseId, orderId, "3879", warehouseId, 1, shippingMethod,finalAmount, zipcode, paymentMode, receiverName);
		//}
	}
	
	public String createMockOrder(String toStatus, String zipcode, String courierCode, String warehouseId, String shippingMethod, String paymentMode, boolean isTryAndBuy, boolean isMultiSeller) throws Exception {

		LmsServiceHelper lmsServiceHelper = new LmsServiceHelper();
		OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
		String paymentM = getPaymentInsturmentMethodMap().get(paymentMode);
		int paymentInstrument = getPaymentInsturmentId().get(paymentMode);
		String status = EnumSCM.WP;
		if (toStatus.equals(EnumSCM.RFR)) status = EnumSCM.RFR;
		String storeOrderId = new StringBuilder("20").append(LMSUtils.randomGenn(17)).append("001").toString();
		String ppsId = "f" + LMSUtils.randomGenn(3) + "xy" + LMSUtils.randomGenn(2) + "-f" + LMSUtils.randomGenn(2) + "c-9z0r-y" + LMSUtils.randomGenn(2) + "e-" + LMSUtils.randomGenn(7) + "oi7xg";
		String orderId = insertOrders(storeOrderId, paymentM, ppsId, status, EnumSCM.WP, zipcode, courierCode, warehouseId, shippingMethod, isTryAndBuy, isMultiSeller);
		insertPaymentPlan(ppsId, storeOrderId, paymentInstrument, isMultiSeller);
		log.info("----------------OrderId: " + orderId + "----------------");
		String orderReleaseId = omsServiceHelper.getReleaseId(orderId);
		int count = 0;

		while (count < 3) {

			OrderReleaseResponse orderReleaseResponse = omsServiceHelper.markReadyToDispatchV3ForMyntraSeller(orderReleaseId,ReadyToDispatchType.POSITIVE.name());
			String statusType = orderReleaseResponse.getStatus().getStatusType().toString();

			if (statusType.equals("SUCCESS")) {

				String packetId = insertPacketData(orderReleaseId, paymentMode, "PK", zipcode, courierCode, warehouseId, shippingMethod);
				if (packetId != null && !packetId.isEmpty()) {
					if (toStatus.equals(EnumSCM.RFR))
						return orderId;
					if (toStatus.equals(EnumSCM.WP))
						return orderId;
					else {

						DBUtilities.exUpdateQuery("update order_line set status_code = 'PK' where order_id_fk = " + orderId, "oms");
						DBUtilities.exUpdateQuery("update order_release set status_code = 'PK' where order_id_fk = " + orderId, "oms");
						insertWMSItem(orderReleaseId, warehouseId);
						insertInWMSForTODOrder(isTryAndBuy, packetId, orderReleaseId, orderId, shippingMethod, warehouseId, zipcode, paymentMode);
						ExceptionHandler.handleEquals(omsServiceHelper.pushPacketToLms(packetId).getStatus().getStatusType().toString(), EnumSCM.SUCCESS);
						/*DBUtilities.exUpdateQuery("update order_line set status_code = 'PK' where order_id_fk = " + orderId, "oms");
						DBUtilities.exUpdateQuery("update order_release set status_code = 'PK' where order_id_fk = " + orderId, "oms");
						insertWMSItem(orderReleaseId, warehouseId);
						insertInWMSForTODOrder(isTryAndBuy, packetId, orderReleaseId, orderId, shippingMethod, warehouseId, zipcode, paymentMode);
						*/PacketResponse packetResponse = omsServiceHelper.pushPacketToLms(packetId);

						if (packetResponse.getStatus().getStatusType().toString().equals("SUCCESS")) {
							Assert.assertTrue(omsServiceHelper.validatePacketStatusInOMS(packetId, EnumSCM.PK, 2), "Order status is not PK in OMS");
							boolean lmsStatus = lmsServiceHelper.validateOrderStatusInLMS(packetId, EnumSCM.PACKED, 5);

							if (!lmsStatus) {
								throw new SkipException("Packet is not created in LMS,  " + packetId);
							}
							processOrderInSCM(toStatus, courierCode, orderReleaseId, packetId);
						}
						break;
					}
				} else {
					count++;
					if (count == 3) {

						packetId = insertPacketData(orderReleaseId, paymentMode, statusType, zipcode, courierCode, warehouseId, shippingMethod);
						if (packetId != null && !packetId.isEmpty())
							throw new SkipException("Failed in packet creation in OMS " + orderReleaseResponse.getStatus().getStatusMessage());
					}
				}
			}
					else{

						throw new SkipException("Failed in packet creation in OMS ");
					}
				}

//			OrderReleaseResponse orderReleaseResponse = omsServiceHelper.markReadyToDispatchV3ForMyntraSeller(orderReleaseId);
					return orderId;
	}
	


	public void processOrderInSCM(String toStatus, String courierCode, String orderReleaseId, String packetId) throws ManagerException, IOException, JAXBException, JSONException, XMLStreamException, InterruptedException, NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException, SQLException {
		
		LmsServiceHelper lmsServiceHelper = new LmsServiceHelper();
		OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
		ProcessRelease processRelease = new ProcessRelease();
		List<ReleaseEntry> releaseEntries = new ArrayList<>();
        		
		switch (toStatus) {
		
			case EnumSCM.PK:
				break;
			case EnumSCM.IS:
				if (!courierCode.equals("ML")) {
					DBUtilities.exUpdateQuery("update order_tracking set courier_creation_status = 'ACCEPTED' where order_id = '" + packetId+"'", "lms");
				}
				ExceptionHandler.handleEquals(lmsServiceHelper.orderInScanNew(packetId), EnumSCM.SUCCESS);
				Assert.assertTrue(lmsServiceHelper.validateOrderStatusInLMS(packetId, EnumSCM.INSCANNED, 5), "Order is not in LMS");
				break;
			case EnumSCM.ADDED_TO_MB:
				releaseEntries.add(new ReleaseEntry.Builder(orderReleaseId, ReleaseStatus.ADDED_TO_MB).shipmentSource(ShipmentSource.MYNTRA).build());
				processRelease.processReleaseToStatus(processRelease.getReleaseEntryList(releaseEntries));
				Assert.assertTrue(lmsServiceHelper.validateOrderStatusInLMS(packetId,EnumSCM.ADDED_TO_MB,5),"Order is not in LMS");
				break;
			case EnumSCM.SH: 
				releaseEntries.add(new ReleaseEntry.Builder(orderReleaseId, ReleaseStatus.SH).shipmentSource(ShipmentSource.MYNTRA).build());
				processRelease.processReleaseToStatus(processRelease.getReleaseEntryList(releaseEntries));
				Assert.assertTrue(lmsServiceHelper.validateOrderStatusInLMS(packetId,EnumSCM.SHIPPED,5),"Order is not in SHIPPED state in LMS");
				Assert.assertTrue(omsServiceHelper.validatePacketStatusInOMS(packetId,EnumSCM.S,10),"Order status is not SH in OMS");
				break;
			case EnumSCM.DL: 
				releaseEntries.add(new ReleaseEntry.Builder(orderReleaseId, ReleaseStatus.DL).shipmentSource(ShipmentSource.MYNTRA).build());
				processRelease.processReleaseToStatus(processRelease.getReleaseEntryList(releaseEntries));
				Assert.assertTrue(lmsServiceHelper.validateOrderStatusInLMS(packetId,EnumSCM.DELIVERED,5),"Order Status is not DL in LMS");
				break;
			case EnumSCM.RTO: 
				releaseEntries.add(new ReleaseEntry.Builder(orderReleaseId, ReleaseStatus.RTO).shipmentSource(ShipmentSource.MYNTRA).build());
				processRelease.processReleaseToStatus(processRelease.getReleaseEntryList(releaseEntries));
				Assert.assertTrue(omsServiceHelper.validatePacketStatusInOMS(packetId,EnumSCM.RTO,10),"Order status is not RTO in OMS");
				break;
			case EnumSCM.UNRTO: 
				releaseEntries.add(new ReleaseEntry.Builder(orderReleaseId, ReleaseStatus.UNRTO).shipmentSource(ShipmentSource.MYNTRA).build());
				processRelease.processReleaseToStatus(processRelease.getReleaseEntryList(releaseEntries));
				Assert.assertTrue(omsServiceHelper.validatePacketStatusInOMS(packetId,EnumSCM.RTO,10),"Order status is not RTO in OMS");
				break;
			case EnumSCM.CANCELLED_IN_HUB: 
				ExceptionHandler.handleEquals(lmsServiceHelper.cancelShipmentInLMS(packetId),EnumSCM.SUCCESS,"Expected status is not equal");
				ExceptionHandler.handleEquals(getOrderToShipStatus(packetId),EnumSCM.CANCELLED_IN_HUB, "DB is not updated to CANCELLED_IN_HUB");
				break;
			case EnumSCM.LOST: 
				releaseEntries.add(new ReleaseEntry.Builder(orderReleaseId, ReleaseStatus.LOST).shipmentSource(ShipmentSource.MYNTRA).build());
				processRelease.processReleaseToStatus(processRelease.getReleaseEntryList(releaseEntries));	
				ExceptionHandler.handleEquals(getOrderToShipStatus(packetId),EnumSCM.LOST, "DB is not updated to LOST");
				Assert.assertTrue(omsServiceHelper.validatePacketStatusInOMS(packetId,EnumSCM.L,10),"Order status is not LOST in OMS");
				break;
			case EnumSCM.FD: 
				releaseEntries.add(new ReleaseEntry.Builder(orderReleaseId, ReleaseStatus.FD).shipmentSource(ShipmentSource.MYNTRA).build());
				processRelease.processReleaseToStatus(processRelease.getReleaseEntryList(releaseEntries));	
				ExceptionHandler.handleEquals(getOrderToShipStatus(packetId),EnumSCM.FAILED_DELIVERY, "DB is not updated to FAILED_DELIVERY");
				break;
			case EnumSCM.OFD: 
				releaseEntries.add(new ReleaseEntry.Builder(orderReleaseId, ReleaseStatus.OFD).shipmentSource(ShipmentSource.MYNTRA).build());
				processRelease.processReleaseToStatus(processRelease.getReleaseEntryList(releaseEntries));
				ExceptionHandler.handleEquals(getOrderToShipStatus(packetId),EnumSCM.OUT_FOR_DELIVERY, "DB is not updated to OUT_FOR_DELIVERY");
				break;
			case EnumSCM.RECEIVE_IN_DC: 				
				releaseEntries.add(new ReleaseEntry.Builder(orderReleaseId, ReleaseStatus.SH).shipmentSource(ShipmentSource.MYNTRA).build());
				processRelease.processReleaseToStatus(processRelease.getReleaseEntryList(releaseEntries));	
				Assert.assertTrue(lmsServiceHelper.validateOrderStatusInML(packetId,EnumSCM.UNASSIGNED,5), "DB is not updated to UNASSIGNED in ML world");
				break;
		 }
	}
	
	
	public String createMockOrderForJITinWP(String zipcode, String courierCode, String warehouseId, String shippingMethod, String paymentMode, boolean isTryAndBuy, long vendorId) throws Exception {
		String orderId = createMockOrder(EnumSCM.WP, zipcode, courierCode, warehouseId, shippingMethod, paymentMode, isTryAndBuy, false);
		DBUtilities.exUpdateQuery("update order_line set supply_type = '"+EnumSCM.JUST_IN_TIME+"', vendor_id = "+vendorId+" where order_id_fk = "+orderId,"oms");
		return orderId;
	}

	public String createMockOrderWithMultiPaymentMode(String toStatus, String zipcode, String courierCode, String warehouseId, String shippingMethod, boolean isTryAndBuy) throws Exception {
		LmsServiceHelper lmsServiceHelper = new LmsServiceHelper();
		OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
		String status = EnumSCM.WP;
		String lineStatus = EnumSCM.QD;
		if (toStatus.equalsIgnoreCase(EnumSCM.WP)) {
			lineStatus = EnumSCM.A;
		}
		if (!toStatus.equals(EnumSCM.WP)) {
			status = EnumSCM.PK;
		}
		String storeOrderId = new StringBuilder("20").append(LMSUtils.randomGenn(17)).append("001").toString();
		String ppsId = "f" + LMSUtils.randomGenn(3) + "xy" + LMSUtils.randomGenn(2) + "-f" + LMSUtils.randomGenn(2) + "c-9z0r-y" + LMSUtils.randomGenn(2) + "e-" + LMSUtils.randomGenn(7) + "oi7xg";
		String orderId = insertOrdersWithDiffPaymentMode(storeOrderId, ppsId, status, lineStatus, zipcode, courierCode, warehouseId, shippingMethod, isTryAndBuy);
		insertPaymentPlanWithDiffPaymentMode(ppsId, storeOrderId);
		log.info("----------------OrderId: " + orderId + "----------------");
		String releaseId = "" + omsServiceHelper.getReleaseId(orderId);
		if (toStatus.equals(EnumSCM.WP)) return orderId;
		else {
			insertWMSItem(releaseId, warehouseId);
			ExceptionHandler.handleEquals(omsServiceHelper.pushReleaseToLms(releaseId).getStatus().getStatusType().toString(), EnumSCM.SUCCESS);
			Assert.assertTrue(lmsServiceHelper.validateOrderStatusInLMS(releaseId, EnumSCM.PACKED, 15), "Order is not in LMS");
			Assert.assertTrue(omsServiceHelper.validateReleaseStatusInOMS(releaseId, EnumSCM.PK, 2), "Order status is not PK in OMS");
		}
		return orderId;
	}
	
	public Map<String, Object> getReleaseOrderIdList(String courierCode, String sourceId) {

		String query = "SELECT order_id FROM (SELECT order_id FROM order_to_ship where status = 'PK' and source_id='"+sourceId+"' and courier_code='"+courierCode+"' ORDER BY last_modified_on DESC LIMIT 20) T1 ORDER BY RAND() limit 1";	
		return DBUtilities.exSelectQueryForSingleRecord(query, "myntra_lms");
	}

	public Function getReturnHubCodeForWarehouse = whId-> DBUtilities.exSelectQueryForSingleRecord("select hub_code from return_hub_warehouse_config where `warehouse_id` = "+whId,"lms").get("hub_code");
    
    public Function getDCCodeForRelease = orderId-> DBUtilities.exSelectQueryForSingleRecord("select delivery_center_id from order_to_ship where order_id = '"+orderId +"'","lms").get("delivery_center_id");
}