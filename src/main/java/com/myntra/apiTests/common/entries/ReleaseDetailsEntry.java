package com.myntra.apiTests.common.entries;

import java.util.List;

import com.myntra.apiTests.common.Constants.ReleaseStatus;
import com.myntra.apiTests.common.Constants.ShipmentSource;
import com.myntra.lms.client.status.ShipmentType;
import com.myntra.lms.client.status.ShippingMethod;

/**
 * Created by Shubham Gupta on 8/2/17.
 */
public class ReleaseDetailsEntry {

	private long masterBagId;
	private long sellerId;
	private long tripId;
	private long tripOrderAssignmentId;
	private long deliveryCenterId;
	private String releaseId;
	private String packetId;
	private String orderId;
	private String storeOrderId;
	private String paymentMethod;
	private String pincode;
	private String courierCode;
	private String trackingNumber;
	private String warehouseId;
	private String supplyType;
	private String dispatchHub;
	private String rtoHubCode;
	private String rtoWarehouse;
	private boolean force;
	private boolean isTryNbuy;
	private boolean isPacketEnable;
	private ReleaseStatus toStatus;
	private ShipmentType shipmentType;
	private ShippingMethod shippingMethod;
	private ShipmentSource shipmentSource;
	private List<TryNBuyEntry> tryNBuyEntries;

	public String getReleaseId() {
		return releaseId;
	}

	public void setReleaseId(String releaseId) {
		this.releaseId = releaseId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getStoreOrderId() {
		return storeOrderId;
	}

	public void setStoreOrderId(String storeOrderId) {
		this.storeOrderId = storeOrderId;
	}

	public ReleaseStatus getToStatus() {
		return toStatus;
	}

	public void setToStatus(ReleaseStatus toStatus) {
		this.toStatus = toStatus;
	}

	public ShipmentSource getShipmentSource() {
		return this.shipmentSource;
	}

	public void setShipmentSource(ShipmentSource shipmentSource) {
		this.shipmentSource = shipmentSource;
	}

	public Boolean getForce() {
		return force;
	}

	public void setForce(Boolean force) {
		this.force = force;
	}

	public List<TryNBuyEntry> getTryNBuyEntries() {
		return this.tryNBuyEntries;
	}

	public void setTryNBuyEntries(List<TryNBuyEntry> tryNBuyEntries) {
		this.tryNBuyEntries = tryNBuyEntries;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public String getCourierCode() {
		return courierCode;
	}

	public void setCourierCode(String courierCode) {
		this.courierCode = courierCode;
	}

	public String getTrackingNumber() {
		return trackingNumber;
	}

	public void setTrackingNumber(String trackingNumber) {
		this.trackingNumber = trackingNumber;
	}

	public String getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(String warehouseId) {
		this.warehouseId = warehouseId;
	}

	public String getSupplyType() {
		return supplyType;
	}

	public void setSupplyType(String supplyType) {
		this.supplyType = supplyType;
	}

	public boolean isTryNbuy() {
		return isTryNbuy;
	}

	public void setTryNbuy(boolean tryNbuy) {
		isTryNbuy = tryNbuy;
	}

	public ShipmentType getShipmentType() {
		return shipmentType;
	}

	public void setShipmentType(ShipmentType shipmentType) {
		this.shipmentType = shipmentType;
	}

	public ShippingMethod getShippingMethod() {
		return shippingMethod;
	}

	public void setShippingMethod(ShippingMethod shippingMethod) {
		this.shippingMethod = shippingMethod;
	}

	public long getDeliveryCenterId() {
		return deliveryCenterId;
	}

	public void setDeliveryCenterId(long deliveryCenterId) {
		this.deliveryCenterId = deliveryCenterId;
	}

	public String getDispatchHub() {
		return dispatchHub;
	}

	public void setDispatchHub(String dispatchHub) {
		this.dispatchHub = dispatchHub;
	}

	public String getRtoHubCode() {
		return rtoHubCode;
	}

	public void setRtoHubCode(String rtoHubCode) {
		this.rtoHubCode = rtoHubCode;
	}

	public String getRtoWarehouse() {
		return rtoWarehouse;
	}

	public void setRtoWarehouse(String rtoWarehouse) {
		this.rtoWarehouse = rtoWarehouse;
	}

	public long getMasterBagId() {
		return masterBagId;
	}

	public void setMasterBagId(long masterBagId) {
		this.masterBagId = masterBagId;
	}

	public long getTripId() {
		return tripId;
	}

	public void setTripId(long tripId) {
		this.tripId = tripId;
	}

	public long getSellerId() {
		return sellerId;
	}

	public void setSellerId(long sellerId) {
		this.sellerId = sellerId;
	}

	public long getTripOrderAssignmentId() {
		return tripOrderAssignmentId;
	}

	public void setTripOrderAssignmentId(long tripOrderAssignmentId) {
		this.tripOrderAssignmentId = tripOrderAssignmentId;
	}

	public String getPacketId() {
		return packetId;
	}

	public void setPacketId(String packetId) {
		this.packetId = packetId;
	}

	public boolean isPacketEnable() {
		return isPacketEnable;
	}

	public void setPacketEnable(boolean isPacketEnable) {
		this.isPacketEnable = isPacketEnable;
	}

	@Override
	public String toString() {
		return "ReleaseDetailsEntry [releaseId=" + releaseId + ", packetId=" + packetId + ", orderId=" + orderId
				+ ", storeOrderId=" + storeOrderId + ", toStatus=" + toStatus + ", shipmentSource=" + shipmentSource
				+ ", force=" + force + ", tryNBuyEntries=" + tryNBuyEntries + ", paymentMethod=" + paymentMethod
				+ ", pincode=" + pincode + ", courierCode=" + courierCode + ", trackingNumber=" + trackingNumber
				+ ", warehouseId=" + warehouseId + ", supplyType=" + supplyType + ", isTryNbuy=" + isTryNbuy
				+ ", shipmentType=" + shipmentType + ", shippingMethod=" + shippingMethod + ", deliveryCenterId="
				+ deliveryCenterId + ", dispatchHub=" + dispatchHub + ", rtoHubCode=" + rtoHubCode + ", rtoWarehouse="
				+ rtoWarehouse + ", masterBagId=" + masterBagId + ", sellerId=" + sellerId + ", tripId=" + tripId
				+ ", tripOrderAssignmentId=" + tripOrderAssignmentId + ", isPacketEnable=" + isPacketEnable + "]";
	}
}
