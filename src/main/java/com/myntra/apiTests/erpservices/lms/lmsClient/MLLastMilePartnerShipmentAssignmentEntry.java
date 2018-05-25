package com.myntra.apiTests.erpservices.lms.lmsClient;

import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.myntra.commons.entries.BaseEntry;
import com.myntra.lms.client.status.CourierCreationStatus;
import com.myntra.lms.client.status.ShipmentType;

/**
 * Created by Shubham Gupta on 7/26/17.
 */
@SuppressWarnings("serial")
@JsonRootName("mlLastMilePartnerAssignment")
public class MLLastMilePartnerShipmentAssignmentEntry extends BaseEntry {

    private ShipmentType shipmentType;

    private String mlTrackingNumber;

    private String courierCode;

    private String lastMilePartnerTrackingNumber;

    private DateTime handoverDate;

    private DateTime returnedDate;

    private DateTime lostOnDate;

    private CourierCreationStatus courierCreationStatus;

    private Boolean isActive;


    public ShipmentType getShipmentType() {
        return shipmentType;
    }

    public void setShipmentType(ShipmentType shipmentType) {
        this.shipmentType = shipmentType;
    }

    public String getMlTrackingNumber() {
        return mlTrackingNumber;
    }

    public void setMlTrackingNumber(String mlTrackingNumber) {
        this.mlTrackingNumber = mlTrackingNumber;
    }

    public String getCourierCode() {
        return courierCode;
    }

    public void setCourierCode(String courierCode) {
        this.courierCode = courierCode;
    }

    public String getLastMilePartnerTrackingNumber() {
        return lastMilePartnerTrackingNumber;
    }

    public void setLastMilePartnerTrackingNumber(String lastMilePartnerTrackingNumber) {
        this.lastMilePartnerTrackingNumber = lastMilePartnerTrackingNumber;
    }

    public DateTime getHandoverDate() {
        return handoverDate;
    }

    public void setHandoverDate(DateTime handoverDate) {
        this.handoverDate = handoverDate;
    }

    public DateTime getReturnedDate() {
        return returnedDate;
    }

    public void setReturnedDate(DateTime returnedDate) {
        this.returnedDate = returnedDate;
    }

    public DateTime getLostOnDate() {
        return lostOnDate;
    }

    public void setLostOnDate(DateTime lostOnDate) {
        this.lostOnDate = lostOnDate;
    }

    public CourierCreationStatus getCourierCreationStatus() {
        return courierCreationStatus;
    }

    public void setCourierCreationStatus(CourierCreationStatus courierCreationStatus) {
        this.courierCreationStatus = courierCreationStatus;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
}

