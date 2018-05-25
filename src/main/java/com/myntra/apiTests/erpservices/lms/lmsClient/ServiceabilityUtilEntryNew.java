package com.myntra.apiTests.erpservices.lms.lmsClient;

/**
 * Created by Shubham Gupta on 4/20/17.
 */
public class ServiceabilityUtilEntryNew {

    private String env;
    private String warehouseId;
    private String pincode;
    private String[] couriers;
    private String serviceType;
    private String shippingMethod;
    private String paymentMode;
    private String item;
    private boolean fulshRedis;

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String[] getCouriers() {
        return couriers;
    }

    public void setCouriers(String[] couriers) {
        this.couriers = couriers;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getShippingMethod() {
        return shippingMethod;
    }

    public void setShippingMethod(String shippingMethod) {
        this.shippingMethod = shippingMethod;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public boolean isFulshRedis(boolean flushRedis) {
        return fulshRedis;
    }

    public void setFulshRedis(boolean fulshRedis) {
        this.fulshRedis = fulshRedis;
    }
}
