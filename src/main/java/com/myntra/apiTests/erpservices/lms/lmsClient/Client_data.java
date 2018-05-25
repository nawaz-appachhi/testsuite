package com.myntra.apiTests.erpservices.lms.lmsClient;

/**
 * Created by Shubham Gupta on 4/20/17.
 */
public class Client_data {
    private String destinationCode;
    private String courierCode;
    private String shippingMethod;
    private String isFootwear;

    public String getDestinationCode() {
        return destinationCode;
    }

    public void setDestinationCode(String destinationCode) {
        this.destinationCode = destinationCode;
    }

    public String getCourierCode() {
        return courierCode;
    }

    public void setCourierCode(String courierCode) {
        this.courierCode = courierCode;
    }

    public String getShippingMethod() {
        return shippingMethod;
    }

    public void setShippingMethod(String shippingMethod) {
        this.shippingMethod = shippingMethod;
    }

    public String getIsFootwear() {
        return isFootwear;
    }

    public void setIsFootwear(String isFootwear) {
        this.isFootwear = isFootwear;
    }
}
