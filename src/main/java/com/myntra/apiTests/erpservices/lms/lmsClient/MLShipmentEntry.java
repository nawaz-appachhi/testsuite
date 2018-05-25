package com.myntra.apiTests.erpservices.lms.lmsClient;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.myntra.commons.entries.BaseEntry;
import com.myntra.commons.entries.ExcelSheetEntry;
import com.myntra.lms.client.status.*;
import org.joda.time.DateTime;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Shubham Gupta on 7/26/17.
 */
@SuppressWarnings("serial")
@JsonRootName("mlShipment")
@XmlRootElement(name = "mlShipment")
@XmlAccessorType(XmlAccessType.FIELD)
public class MLShipmentEntry extends BaseEntry {

    private String sourceId;

    private String sourceReferenceId;

    private String trackingNumber;

    private Long deliveryCenterId;

    private ShipmentType shipmentType;

    private com.myntra.lms.client.status.ShippingMethod shippingMethod;

    protected String shipmentStatus;

    private String recipientName;

    private String address;

    private String city;

    private String pincode;

    private String recipientContactNumber;

    private String alternateContactNumber;

    private String email;

    private Float shipmentValue;

    private String contentsDescription;

    private DateTime receivedDate;

    private DateTime promiseDate;

    private String failedAttemptCount;

    private DateTime lastAttemptDate;

    private boolean deleted;

    private ExcelSheetEntry excelSheet;

    private TripOrderAssignmentEntryV2 tripOrderAssignment;

    private MLLastMilePartnerShipmentAssignmentEntry mlLastMilePartnerShipmentAssignment;

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getSourceReferenceId() {
        return sourceReferenceId;
    }

    public void setSourceReferenceId(String sourceReferenceId) {
        this.sourceReferenceId = sourceReferenceId;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public Long getDeliveryCenterId() {
        return deliveryCenterId;
    }

    public void setDeliveryCenterId(Long deliveryCenterId) {
        this.deliveryCenterId = deliveryCenterId;
    }

    public ShipmentType getShipmentType() {
        return shipmentType;
    }

    public void setShipmentType(ShipmentType shipmentType) {
        this.shipmentType = shipmentType;
    }

    public com.myntra.lms.client.status.ShippingMethod getShippingMethod() {
        return shippingMethod;
    }

    public void setShippingMethod(com.myntra.lms.client.status.ShippingMethod shippingMethod) {
        this.shippingMethod = shippingMethod;
    }

    public Float getShipmentValue() {
        return shipmentValue;
    }

    public void setShipmentValue(Float shipmentValue) {
        this.shipmentValue = shipmentValue;
    }

    public String getContentsDescription() {
        return contentsDescription;
    }

    public void setContentsDescription(String contentsDescription) {
        this.contentsDescription = contentsDescription;
    }

    public DateTime getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(DateTime receivedDate) {
        this.receivedDate = receivedDate;
    }

    public DateTime getPromiseDate() {
        return promiseDate;
    }

    public void setPromiseDate(DateTime promiseDate) {
        this.promiseDate = promiseDate;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipient_name) {
        this.recipientName = recipient_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getRecipientContactNumber() {
        return recipientContactNumber;
    }

    public void setRecipientContactNumber(String recipientContactNumber) {
        this.recipientContactNumber = recipientContactNumber;
    }

    public String getAlternateContactNumber() {
        return alternateContactNumber;
    }

    public void setAlternateContactNumber(String alternateColumnNumber) {
        this.alternateContactNumber = alternateColumnNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFailedAttemptCount() {
        return failedAttemptCount;
    }

    public void setFailedAttemptCount(String failedAttemptCount) {
        this.failedAttemptCount = failedAttemptCount;
    }

    public DateTime getLastAttemptDate() {
        return lastAttemptDate;
    }

    public void setLastAttemptDate(DateTime lastAttemptDate) {
        this.lastAttemptDate = lastAttemptDate;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public String getRawShipmentStatus() {
        return shipmentStatus;
    }

    public void setShipmentStatus(String shipmentStatus) {
        this.shipmentStatus = shipmentStatus;
    }

    public String getShipmentStatus(){
        return shipmentStatus;
    }

    public TripOrderAssignmentEntryV2 getTripOrderAssignment() {
        return tripOrderAssignment;
    }

    public void setTripOrderAssignment(TripOrderAssignmentEntryV2 tripOrderAssignment) {
        this.tripOrderAssignment = tripOrderAssignment;
    }

    public MLLastMilePartnerShipmentAssignmentEntry getMlLastMilePartnerShipmentAssignment() {
        return mlLastMilePartnerShipmentAssignment;
    }

    public void setMlLastMilePartnerShipmentAssignment(MLLastMilePartnerShipmentAssignmentEntry mlLastMilePartnerShipmentAssignment) {
        this.mlLastMilePartnerShipmentAssignment = mlLastMilePartnerShipmentAssignment;
    }

    public ExcelSheetEntry getExcelSheet() {
        return excelSheet;
    }

    public void setExcelSheet(ExcelSheetEntry excelSheet) {
        this.excelSheet = excelSheet;
    }
}

