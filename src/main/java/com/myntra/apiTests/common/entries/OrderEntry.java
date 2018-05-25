package com.myntra.apiTests.common.entries;

import com.myntra.apiTests.common.Constants.ReleaseStatus;
import com.myntra.apiTests.common.Constants.ShipmentSource;

import java.util.List;

/**
 * Created by Shubham Gupta on 8/1/17.
 */
public class OrderEntry {
    private String orderId;
    private String storeOrderId;
    private List<ReleaseEntry> releaseEntries;
    private ReleaseStatus toStatus;
    private Boolean force;
    private ShipmentSource shipmentSource;

    public OrderEntry(String orderId, String storeOrderId, List<ReleaseEntry> releaseEntries, ReleaseStatus toStatus,
                      Boolean force, ShipmentSource shipmentSource) {
        this.orderId = orderId;
        this.storeOrderId = storeOrderId;
        this.releaseEntries = releaseEntries;
        this.toStatus = toStatus;
        this.force = force;
        this.shipmentSource = shipmentSource;
    }

    public static class Builder {
        protected String _orderId;
        protected String _storeOrderId;
        protected List<ReleaseEntry> _releaseEntries;
        protected ReleaseStatus _toStatus;
        protected Boolean _force;
        protected ShipmentSource _shipmentSource;

        public Builder(String orderId, String storeOrderId, ReleaseStatus status){
            this._orderId = orderId;
            this._storeOrderId = storeOrderId;
            this._toStatus = status;
        }

        public Builder(List<ReleaseEntry> releaseEntries){
            this._releaseEntries = releaseEntries;
        }

        public Builder force(Boolean force){
            this._force = force;
            return this;
        }

        public Builder shipmentSource(ShipmentSource shipmentSource){
            this._shipmentSource = shipmentSource;
            return this;
        }

        public OrderEntry build(){
            if (_shipmentSource == null)
                _shipmentSource = ShipmentSource.MYNTRA;
            if (_force==null)
                _force = false;
            return new OrderEntry(_orderId,_storeOrderId,_releaseEntries,_toStatus,_force,_shipmentSource);
        }
    }

    public String getOrderId() {
        return orderId;
    }

    public String getStoreOrderId() {
        return storeOrderId;
    }

    public List<ReleaseEntry> getReleaseEntries() {
        return releaseEntries;
    }

    public ReleaseStatus getToStatus() {
        return toStatus;
    }

    public Boolean getForce() {
        return force;
    }

    public ShipmentSource getShipmentSource() {
        return shipmentSource;
    }

    @Override
    public String toString() {
        return "OrderEntry{" +
                "orderId='" + orderId + '\'' +
                ", storeOrderId='" + storeOrderId + '\'' +
                ", releaseEntries=" + releaseEntries +
                ", toStatus=" + toStatus +
                ", force=" + force +
                ", shipmentSource=" + shipmentSource +
                '}';
    }
}
