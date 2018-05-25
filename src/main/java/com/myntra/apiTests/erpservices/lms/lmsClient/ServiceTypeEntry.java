package com.myntra.apiTests.erpservices.lms.lmsClient;

/**
 * Created by Shubham Gupta on 7/21/17.
 */
public class ServiceTypeEntry {
    private boolean delivery;
    private boolean exchange;
    private boolean trynbuy;
    private boolean openBoxPickup;
    private boolean closeBoxPickup;

    public boolean isDelivery() {
        return delivery;
    }

    public void setDelivery(boolean delivery) {
        this.delivery = delivery;
    }

    public boolean isExchange() {
        return exchange;
    }

    public void setExchange(boolean exchange) {
        this.exchange = exchange;
    }

    public boolean isTrynbuy() {
        return trynbuy;
    }

    public void setTrynbuy(boolean trynbuy) {
        this.trynbuy = trynbuy;
    }

    public boolean isOpenBoxPickup() {
        return openBoxPickup;
    }

    public void setOpenBoxPickup(boolean openBoxPickup) {
        this.openBoxPickup = openBoxPickup;
    }

    public boolean isCloseBoxPickup() {
        return closeBoxPickup;
    }

    public void setCloseBoxPickup(boolean closeBoxPickup) {
        this.closeBoxPickup = closeBoxPickup;
    }

    public ServiceTypeEntry(boolean delivery, boolean exchange, boolean trynbuy, boolean openBoxPickup, boolean closeBoxPickup) {
        this.delivery = delivery;
        this.exchange = exchange;
        this.trynbuy = trynbuy;
        this.openBoxPickup = openBoxPickup;
        this.closeBoxPickup = closeBoxPickup;
    }

    public ServiceTypeEntry() {}
}
