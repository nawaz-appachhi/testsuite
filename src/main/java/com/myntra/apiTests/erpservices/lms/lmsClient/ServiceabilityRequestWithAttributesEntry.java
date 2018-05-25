package com.myntra.apiTests.erpservices.lms.lmsClient;

import java.util.List;

/**
 * Created by Shubham Gupta on 7/21/17.
 */
public class ServiceabilityRequestWithAttributesEntry {
    private String warehouseId;
    private String pincode;
    private List<CouriersEntry> couriers;
    private boolean fulshRedis;

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

    public List<CouriersEntry> getCouriers() {
        return couriers;
    }

    public void setCouriers(List<CouriersEntry> couriers) {
        this.couriers = couriers;
    }

    public boolean isFulshRedis() {
        return fulshRedis;
    }

    public void setFulshRedis(boolean fulshRedis) {
        this.fulshRedis = fulshRedis;
    }

    public ServiceabilityRequestWithAttributesEntry(String warehouseId, String pincode, List<CouriersEntry> couriers, boolean fulshRedis) {
        this.warehouseId = warehouseId;
        this.pincode = pincode;
        this.couriers = couriers;
        this.fulshRedis = fulshRedis;
    }

    public ServiceabilityRequestWithAttributesEntry() {}
}
