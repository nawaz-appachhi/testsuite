package com.myntra.apiTests.erpservices.lms.lmsClient;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Shubham Gupta on 7/21/17.
 */
public class CouriersEntry {
    @SerializedName("courierCode")
    private String courierCode;
    @SerializedName("capacity")
    private Integer capacity;
    @SerializedName("paymentMode")
    private PaymentModeEntry paymentMode;
    @SerializedName("shippingMethod")
    private ShippingMethodEntry shippingMethod;
    @SerializedName("serviceType")
    private ServiceTypeEntry serviceType;
    @SerializedName("itemAttribute")
    private ItemAttributeEntry itemAttribute;
    @SerializedName("codLimit")
    private Integer codLimit;

    public String getCourierCode() {
        return courierCode;
    }

    public void setCourierCode(String courierCode) {
        this.courierCode = courierCode;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public PaymentModeEntry getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(PaymentModeEntry paymentMode) {
        this.paymentMode = paymentMode;
    }

    public ShippingMethodEntry getShippingMethod() {
        return shippingMethod;
    }

    public void setShippingMethod(ShippingMethodEntry shippingMethod) {
        this.shippingMethod = shippingMethod;
    }

    public ServiceTypeEntry getServiceType() {
        return serviceType;
    }

    public void setServiceType(ServiceTypeEntry serviceType) {
        this.serviceType = serviceType;
    }

    public ItemAttributeEntry getItemAttribute() {
        return itemAttribute;
    }

    public void setItemAttribute(ItemAttributeEntry itemAttribute) {
        this.itemAttribute = itemAttribute;
    }

    public Integer getCodLimit() {
        return codLimit;
    }

    public void setCodLimit(Integer codLimit) {
        this.codLimit = codLimit;
    }

    public CouriersEntry(String courierCode, Integer capacity, PaymentModeEntry paymentMode, ShippingMethodEntry shippingMethod, ServiceTypeEntry serviceType, ItemAttributeEntry itemAttribute, Integer codLimit) {
        this.courierCode = courierCode;
        this.capacity = capacity;
        this.paymentMode = paymentMode;
        this.shippingMethod = shippingMethod;
        this.serviceType = serviceType;
        this.itemAttribute = itemAttribute;
        this.codLimit = codLimit;
    }

    public CouriersEntry() {
    }
}
