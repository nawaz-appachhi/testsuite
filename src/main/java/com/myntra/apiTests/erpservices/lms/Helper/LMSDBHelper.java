package com.myntra.apiTests.erpservices.lms.Helper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.myntra.lordoftherings.boromir.DBUtilities;

public class LMSDBHelper {

	public void updateOrderTrackingStatus(String orderId) throws SQLException {
		String updateOrderTracking = "update order_tracking set courier_creation_status = 'AWAITING' where order_id = "
				+ orderId;
		DBUtilities.exUpdateQuery(updateOrderTracking, "myntra_lms");
	}

	public void updateOrderTrackingAndOrderToShipStatusToShip(String orderId) throws SQLException {
		String updateOrderTracking = "update order_tracking set shipment_status = 'SHIPPED' where order_id = "
				+ orderId;
		String updateOrderToShip = "update order_to_ship set status = 'SH', shipment_status = 'SHIPPED' where order_id = "
				+ orderId;
		DBUtilities.exUpdateQuery(updateOrderTracking, "myntra_lms");
		DBUtilities.exUpdateQuery(updateOrderToShip, "myntra_lms");
	}

	@SuppressWarnings("unchecked")
	public String getOrderCourierCreationStatus(String orderId) throws SQLException {
		
		String checkStatusOrderTracking = "select courier_creation_status from order_tracking where order_id ="
				+ orderId;
		List<?> TxQ = DBUtilities.exSelectQuery(checkStatusOrderTracking, "myntra_lms");		
		Map<String, String> txResult = (Map<String, String>) TxQ.get(0);
		String result = txResult.get("courier_creation_status");
		return result;
	}

	@SuppressWarnings("unchecked")
	public String getShipmentStatus(String orderId) throws SQLException {
		
		String checkShipmentStatus = "select shipment_status from order_tracking where order_id =" + orderId;
		List<?> TxQ = DBUtilities.exSelectQuery(checkShipmentStatus, "myntra_lms");
		Map<String, String> txResult = (Map<String, String>) TxQ.get(0);
		String result = txResult.get("shipment_status");
		return result;
	}

	@SuppressWarnings("unchecked")
	public int getOrderTrackingDetailStatusCount(String orderTrackingId, String activity_type) throws SQLException {
		// String checkShipmentStatus = "select activity_type from order_tracking_detail
		// where order_tracking_id = "+orderTrackingId+" order by last_modified_on desc
		// limit 1";
		String checkShipmentStatus1 = "select count(*) from order_tracking_detail where order_tracking_id = "
				+ orderTrackingId + " and activity_type = '" + activity_type + "'";
		List<?> TxQ = DBUtilities.exSelectQuery(checkShipmentStatus1, "myntra_lms");
		Map<String, Long> txResult = (Map<String, Long>) TxQ.get(0);
		Long lp = txResult.get("count(*)");
		return lp.intValue();
	}
	
	

}
