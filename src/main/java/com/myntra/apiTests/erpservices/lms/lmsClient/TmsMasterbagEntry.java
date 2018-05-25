package com.myntra.apiTests.erpservices.lms.lmsClient;

import java.util.List;

/**
 * Created by Shubham Gupta on 5/15/17.
 */
public class TmsMasterbagEntry
{
    private String lastScannedOn;

    private String destinationHub;

    private String sourceHub;

    private String status;

    private String lastScannedCity;

    private String masterbagId;

    private List<MasterbagShipmentEntry> masterbagShipmentEntries;

    private String lastScannedHub;

    public String getLastScannedOn() {
        return lastScannedOn;
    }

    public void setLastScannedOn(String lastScannedOn) {
        this.lastScannedOn = lastScannedOn;
    }

    public String getDestinationHub() {
        return destinationHub;
    }

    public void setDestinationHub(String destinationHub) {
        this.destinationHub = destinationHub;
    }

    public String getSourceHub() {
        return sourceHub;
    }

    public void setSourceHub(String sourceHub) {
        this.sourceHub = sourceHub;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLastScannedCity() {
        return lastScannedCity;
    }

    public void setLastScannedCity(String lastScannedCity) {
        this.lastScannedCity = lastScannedCity;
    }

    public String getMasterbagId() {
        return masterbagId;
    }

    public void setMasterbagId(String masterbagId) {
        this.masterbagId = masterbagId;
    }

    public List<MasterbagShipmentEntry> getMasterbagShipmentEntries() {
        return masterbagShipmentEntries;
    }

    public void setMasterbagShipmentEntries(List<MasterbagShipmentEntry> masterbagShipmentEntries) {
        this.masterbagShipmentEntries = masterbagShipmentEntries;
    }

    public String getLastScannedHub() {
        return lastScannedHub;
    }

    public void setLastScannedHub(String lastScannedHub) {
        this.lastScannedHub = lastScannedHub;
    }

    @Override
    public String toString() {
        return "TmsMasterbagEntry{" +
                "lastScannedOn='" + lastScannedOn + '\'' +
                ", destinationHub='" + destinationHub + '\'' +
                ", sourceHub='" + sourceHub + '\'' +
                ", status='" + status + '\'' +
                ", lastScannedCity='" + lastScannedCity + '\'' +
                ", masterbagId='" + masterbagId + '\'' +
                ", masterbagShipmentEntries=" + masterbagShipmentEntries +
                ", lastScannedHub='" + lastScannedHub + '\'' +
                '}';
    }
}
