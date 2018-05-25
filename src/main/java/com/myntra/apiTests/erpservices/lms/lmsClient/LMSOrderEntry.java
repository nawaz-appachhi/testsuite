package com.myntra.apiTests.erpservices.lms.lmsClient;

import com.myntra.apiTests.common.Constants.ReleaseStatus;
import com.myntra.lms.client.status.ShipmentStatus;
import com.myntra.lms.client.status.ShipmentType;

/**
 * Created by Abhijit.pati on 16/05/17.
 */
public class LMSOrderEntry {

    private String OrderID;
    private ShipmentType shipmentType;
    private ShipmentStatus shipmentStatus;
    private ReleaseStatus finalStatus;

    public LMSOrderEntry(String orderID, ShipmentType shipmentType, ShipmentStatus shipmentStatus) {
        this.OrderID = orderID;
        this.shipmentType = shipmentType;
        this.shipmentStatus = shipmentStatus;
    }

    public LMSOrderEntry(String orderID, ShipmentType shipmentType, ShipmentStatus shipmentStatus, ReleaseStatus finalStatus) {
        this.OrderID = orderID;
        this.shipmentType = shipmentType;
        this.shipmentStatus = shipmentStatus;
        this.finalStatus = finalStatus;
    }



    public ReleaseStatus getFinalStatus() {
        return finalStatus;
    }

    public void setFinalStatus(ReleaseStatus finalStatus) {
        this.finalStatus = finalStatus;
    }


    public ShipmentStatus getShipmentStatus() {
        return shipmentStatus;
    }

    public void setShipmentStatus(ShipmentStatus shipmentStatus) {
        this.shipmentStatus = shipmentStatus;
    }

    public String getOrderID() {
        return OrderID;
    }

    public void setOrderID(String orderID) {
        OrderID = orderID;
    }

    public ShipmentType getShipmentType() {
        return shipmentType;
    }

    public void setShipmentType(ShipmentType shipmentType) {
        this.shipmentType = shipmentType;
    }
}
