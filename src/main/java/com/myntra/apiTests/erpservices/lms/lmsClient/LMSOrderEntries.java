package com.myntra.apiTests.erpservices.lms.lmsClient;


import java.util.List;

import com.myntra.apiTests.common.Constants.ReleaseStatus;
import com.myntra.lms.client.status.PremisesType;
import com.myntra.lms.client.status.ShippingMethod;

/**
 * Created by abhijit.pati on 17/05/17.
 */
@SuppressWarnings("deprecation")
public class LMSOrderEntries {


    public LMSOrderEntries(List<LMSOrderEntry> orderEntries, String wareHouseID, ShippingMethod shippingMethod, PremisesType premisesType, Long dcID, ReleaseStatus finalStatus) {
        this.orderEntries = orderEntries;
        this.wareHouseID = wareHouseID;
        this.shippingMethod = shippingMethod;
        this.premisesType = premisesType;
        this.dcID = dcID;
        this.finalStatus = finalStatus;
    }

    public LMSOrderEntries(List<LMSOrderEntry> orderEntries, String wareHouseID, ShippingMethod shippingMethod, PremisesType premisesType, Long dcID) {
        this.orderEntries = orderEntries;
        this.wareHouseID = wareHouseID;
        this.shippingMethod = shippingMethod;
        this.premisesType = premisesType;
        this.dcID = dcID;
    }

    List<LMSOrderEntry> orderEntries;
    private String wareHouseID;
    private ShippingMethod shippingMethod;
    private PremisesType premisesType;
    private Long dcID;
    private ReleaseStatus finalStatus;


    public LMSOrderEntries() {
    }

    public LMSOrderEntries(List<LMSOrderEntry> orderEntries, String wareHouseID, ShippingMethod shippingMethod, PremisesType premisesType) {
        this.orderEntries = orderEntries;
        this.wareHouseID = wareHouseID;
        this.shippingMethod = shippingMethod;
        this.premisesType = premisesType;
    }

    public String getWareHouseID() {
        return wareHouseID;
    }

    public void setWareHouseID(String wareHouseID) {
        this.wareHouseID = wareHouseID;
    }

    public List<LMSOrderEntry> getOrderEntries() {
        return orderEntries;
    }

    public void setOrderEntries(List<LMSOrderEntry> masterBagOrderEntries) {
        this.orderEntries = masterBagOrderEntries;
    }

    public ShippingMethod getShippingMethod() {
        return shippingMethod;
    }

    public void setShippingMethod(ShippingMethod shippingMethod) {
        this.shippingMethod = shippingMethod;
    }

    public PremisesType getPremisesType() {
        return premisesType;
    }

    public void setPremisesType(PremisesType premisesType) {
        this.premisesType = premisesType;
    }

    public Long getDcID() {
        return dcID;
    }

    public void setDcID(long dcID) {
        this.dcID = dcID;
    }

    public ReleaseStatus getFinalStatus() {
        return finalStatus;
    }

    public void setFinalStatus(ReleaseStatus finalStatus) {
        this.finalStatus = finalStatus;
    }


}
