package com.myntra.apiTests.erpservices.lms.lmsClient;

import java.util.List;

/**
 * Created by Shubham Gupta on 4/20/17.
 */
public class ConsolidationBagShipmentResponse {

    private String bagseal;
    private Client_data client_data;
    private Integer shipment_count;
    private List<ConsolidationShipment> shipments;


    public String getBagseal() {
        return bagseal;
    }

    public void setBagseal(String bagseal) {
        this.bagseal = bagseal;
    }

    public Client_data getClient_data() {
        return client_data;
    }

    public void setClient_data(Client_data client_data) {
        this.client_data = client_data;
    }

    public Integer getShipment_count() {
        return shipment_count;
    }

    public void setShipment_count(Integer shipment_count) {
        this.shipment_count = shipment_count;
    }

    public List<ConsolidationShipment> getShipments() {
        return shipments;
    }

    public void setShipments(List<ConsolidationShipment> shipments) {
        this.shipments = shipments;
    }
}
