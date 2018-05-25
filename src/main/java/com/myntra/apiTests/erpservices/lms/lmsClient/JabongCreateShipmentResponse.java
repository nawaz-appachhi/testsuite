package com.myntra.apiTests.erpservices.lms.lmsClient;

/**
 * Created by 8403 on 17/05/17.
 */
public class JabongCreateShipmentResponse {


    public JabongCreateShipmentResponse(String statusType, Shipment requestBody, String statusCode, String statusMessage, String orderID, String trackingNumber) {
        this.statusType = statusType;
        this.requestBody = requestBody;
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
        this.orderID = orderID;
        this.trackingNumber = trackingNumber;

    }

    public String getStatusType() {
        return statusType;
    }

    public void setStatusType(String statusType) {
        this.statusType = statusType;
    }

    public Shipment getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(Shipment requestBody) {
        this.requestBody = requestBody;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    private String statusType ;
    private Shipment requestBody;
    private String statusCode;
    private String orderID;
    private String trackingNumber;
    private String statusMessage;


}
