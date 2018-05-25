package com.myntra.apiTests.erpservices.lms.lmsClient;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.myntra.commons.entries.BaseEntry;
import com.myntra.lms.client.status.*;
import org.joda.time.DateTime;

/**
 * Created by Shubham Gupta on 7/26/17.
 */
@SuppressWarnings("serial")
@JsonRootName("tripOrderAssignment")
public class TripOrderAssignmentEntryV2 extends BaseEntry {

    private Long tripId;

    private String orderId;

    private String trackingNumber;

    private TripOrderStatus tripOrderStatus = TripOrderStatus.WFD;

    private NotificationStatus notificationStatus = NotificationStatus.NOT_SENT;

    private DateTime assignmentDate;
    private String remark;

    private DeliveryPickupReasonCode deliveryReasonCode;

    private AttemptReasonCode attemptReasonCode;

    private Boolean isActive;

    private DateTime deliveryTime;

    private Boolean isOutScanned = false;

    private Boolean isReturnScanned = false;

    private String sourceReturnId;

    private Long exchangeOrderId;

    private Boolean isExchange = false;

    private ShipmentType shipmentType;

    private UpdatedVia updatedVia;

    public Long getTripId() {
        return tripId;
    }

    public void setTripId(Long tripId) {
        this.tripId = tripId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public TripOrderStatus getTripOrderStatus() {
        return tripOrderStatus;
    }

    public void setTripOrderStatus(TripOrderStatus tripOrderStatus) {
        this.tripOrderStatus = tripOrderStatus;
    }

    public NotificationStatus getNotificationStatus() {
        return notificationStatus;
    }

    public void setNotificationStatus(NotificationStatus notificationStatus) {
        this.notificationStatus = notificationStatus;
    }

    public DateTime getAssignmentDate() {
        return assignmentDate;
    }

    public void setAssignmentDate(DateTime assignmentDate) {
        this.assignmentDate = assignmentDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public DeliveryPickupReasonCode getDeliveryReasonCode() {
        return deliveryReasonCode;
    }

    public void setDeliveryReasonCode(DeliveryPickupReasonCode deliveryReasonCode) {
        this.deliveryReasonCode = deliveryReasonCode;
    }


    public AttemptReasonCode getAttemptReasonCode() {
        return attemptReasonCode;
    }

    public void setAttemptReasonCode(AttemptReasonCode attemptReasonCode) {
        this.attemptReasonCode = attemptReasonCode;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public DateTime getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(DateTime deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public Boolean getIsOutScanned() {
        return isOutScanned;
    }

    public void setIsOutScanned(Boolean outScanned) {
        isOutScanned = outScanned;
    }

    public Boolean getIsReturnScanned() {
        return isReturnScanned;
    }

    public void setIsReturnScanned(Boolean returnScanned) {
        isReturnScanned = returnScanned;
    }

    public String getSourceReturnId() {
        return sourceReturnId;
    }

    public void setSourceReturnId(String returnId) {
        this.sourceReturnId = returnId;
    }

    public Long getExchangeOrderId() {
        return exchangeOrderId;
    }

    public void setExchangeOrderId(Long exchangeOrderId) {
        this.exchangeOrderId = exchangeOrderId;
    }

    public Boolean getIsExchange() {
        return isExchange;
    }

    public void setIsExchange(Boolean exchange) {
        isExchange = exchange;
    }

    public ShipmentType getShipmentType() {
        return shipmentType;
    }

    public void setShipmentType(ShipmentType shipmentType) {
        this.shipmentType = shipmentType;
    }

    public UpdatedVia getUpdatedVia() {
        return updatedVia;
    }

    public void setUpdatedVia(UpdatedVia updatedVia) {
        this.updatedVia = updatedVia;
    }
}

