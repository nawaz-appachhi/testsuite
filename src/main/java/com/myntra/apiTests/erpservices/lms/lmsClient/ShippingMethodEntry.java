package com.myntra.apiTests.erpservices.lms.lmsClient;

/**
 * Created by Shubham Gupta on 7/21/17.
 */
public class ShippingMethodEntry {
    private Integer normal;
    private Integer express;
    private Integer sdd;
    private Integer valueShipping;

    public Integer getNormal() {
        return normal;
    }

    public void setNormal(Integer normal) {
        this.normal = normal;
    }

    public Integer getExpress() {
        return express;
    }

    public void setExpress(Integer express) {
        this.express = express;
    }

    public Integer getSdd() {
        return sdd;
    }

    public void setSdd(Integer sdd) {
        this.sdd = sdd;
    }

    public Integer getValueShipping() {
        return valueShipping;
    }

    public void setValueShipping(Integer valueShipping) {
        this.valueShipping = valueShipping;
    }

    public ShippingMethodEntry(Integer normal, Integer express, Integer sdd, Integer valueShipping) {
        this.normal = normal;
        this.express = express;
        this.sdd = sdd;
        this.valueShipping = valueShipping;
    }

    public ShippingMethodEntry() {}
}
