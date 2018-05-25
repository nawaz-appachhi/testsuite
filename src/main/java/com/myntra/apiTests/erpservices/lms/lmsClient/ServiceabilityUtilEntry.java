package com.myntra.apiTests.erpservices.lms.lmsClient;

/**
 * Created by Shubham Gupta on 3/20/17.
 */
public class ServiceabilityUtilEntry {
    private String env;
    private String warehouseid;
    private String[] pincodes;
    private String[] courier;
    private String servicetype;
    private String shipping;
    private String payment;
    private String item;

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    public String getWarehouseid() {
        return warehouseid;
    }

    public void setWarehouseid(String warehouseid) {
        this.warehouseid = warehouseid;
    }

    public String[] getPincodes() {
        return pincodes;
    }

    public void setPincodes(String[] pincodes) {
        this.pincodes = pincodes;
    }

    public String[] getCourier() {
        return courier;
    }

    public void setCourier(String[] courier) {
        this.courier = courier;
    }

    public String getServicetype() {
        return servicetype;
    }

    public void setServicetype(String servicetype) {
        this.servicetype = servicetype;
    }

    public String getShipping() {
        return shipping;
    }

    public void setShipping(String shipping) {
        this.shipping = shipping;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

}
