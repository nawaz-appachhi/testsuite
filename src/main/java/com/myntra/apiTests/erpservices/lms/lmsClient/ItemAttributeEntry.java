package com.myntra.apiTests.erpservices.lms.lmsClient;

/**
 * Created by Shubham Gupta on 7/21/17.
 */
public class ItemAttributeEntry {
    private boolean jewellery;
    private boolean fragile;
    private boolean hazmat;
    private boolean large;

    public boolean isJewellery() {
        return jewellery;
    }

    public void setJewellery(boolean jewellery) {
        this.jewellery = jewellery;
    }

    public boolean isFragile() {
        return fragile;
    }

    public void setFragile(boolean fragile) {
        this.fragile = fragile;
    }

    public boolean isHazmat() {
        return hazmat;
    }

    public void setHazmat(boolean hazmat) {
        this.hazmat = hazmat;
    }

    public boolean isLarge() {
        return large;
    }

    public void setLarge(boolean large) {
        this.large = large;
    }

    public ItemAttributeEntry(boolean jewellery, boolean fragile, boolean hazmat, boolean large) {
        this.jewellery = jewellery;
        this.fragile = fragile;
        this.hazmat = hazmat;
        this.large = large;
    }

    public ItemAttributeEntry() {}
}
