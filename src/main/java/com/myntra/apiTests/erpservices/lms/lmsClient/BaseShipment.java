package com.myntra.apiTests.erpservices.lms.lmsClient;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.myntra.lms.client.status.ShipmentType;
import com.myntra.logistics.platform.domain.ShipmentStatus;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.DateTime;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Created by Shubham Gupta on 5/2/17.
 */
public abstract class BaseShipment {
    /*
	 * returnId or orderReleaseId
	 */
    @NotEmpty(message="sourceReferenceId cannot be empty")
    protected String sourceReferenceId;

    protected String sourceId;

    @NotEmpty(message="trackingNumber cannot be empty")
    @Size(max = 255, message = "trackingNumber max length is 255 characters")
    protected String trackingNumber;

    @NotNull(message="courierCode cannot be empty")
    protected String courierCode;

    @NotNull(message="shipmentType cannot be empty")
    protected ShipmentType shipmentType;

    protected ShipmentStatus shipmentStatus;

    @NotEmpty(message="recipientName cannot be empty")
    @Size(max = 128, message = "recipientName max length is 128 characters")
    protected String recipientName;

    protected String addressId;

    @NotEmpty(message="recipientAddress cannot be empty")
    @Size(max = 512, message = "recipientAddress max length is 512 characters")
    protected String recipientAddress;

    @Size(max = 128, message = "locality max length is 128 characters")
    protected String locality;

    @Size(max = 128, message = "landmark max length is 128 characters")
    protected String landmark;

    protected String city;

    protected String stateCode;

    protected String country;

    @NotEmpty(message="pincode cannot be empty")
    @Size(min = 6 , max = 6, message = "pincode length is 6 characters")
    @Pattern(regexp = "^[1-9][0-9]{5}$",message = "pincode has to be six digit numeric and cannot start with zero")
    protected String pincode;

    @NotEmpty(message="recipientContactNumber cannot be empty")
    @Size(min = 8 , max = 10, message = "recipientContactNumber min lenght = 8 and  max length = 10 characters")
    protected String recipientContactNumber;

    @Size(max = 10, message = "alternateContactNumber max length = 10 characters")
    protected String alternateContactNumber;

    @Size(max = 255, message = "email max length = 255 characters")
    @Email(message = "email provided doesnot have a valid email syntax")
    protected String email;

    protected Float shipmentValue;

    @NotEmpty(message="contentsDescription cannot be empty")
    @Size(max = 2048, message = "contentsDescription max length = 2048 characters")
    protected String contentsDescription;

    @Future(message="promiseDate must be a future date")
    @NotNull(message="promiseDate cannot be empty")
    @JsonFormat(pattern = "yyyy-MM-dd")
    protected DateTime promiseDate;

    protected String userId;

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

    public String getCourierCode() {
        return courierCode;
    }

    public void setCourierCode(String courierCode) {
        this.courierCode = courierCode;
    }

    public ShipmentType getShipmentType() {
        return shipmentType;
    }

    public void setShipmentType(ShipmentType shipmentType) {
        this.shipmentType = shipmentType;
    }

    public ShipmentStatus getShipmentStatus() {
        return shipmentStatus;
    }

    public void setShipmentStatus(ShipmentStatus shipmentStatus) {
        this.shipmentStatus = shipmentStatus;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String customerName) {
        this.recipientName = customerName;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getRecipientAddress() {
        return recipientAddress;
    }

    public void setRecipientAddress(String customerAddress) {
        this.recipientAddress = customerAddress;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String state) {
        this.stateCode = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public void setAlternateContactNumber(String alternateContactNumber) {
        this.alternateContactNumber = alternateContactNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public DateTime getPromiseDate() {
        return promiseDate;
    }

    public void setPromiseDate(DateTime promiseDate) {
        this.promiseDate = promiseDate;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }


}
