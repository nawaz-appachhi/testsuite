package com.myntra.apiTests.erpservices.lms.lmsClient;

/**
 * Created by Shubham Gupta on 7/21/17.
 */
public class PaymentModeEntry {
    private boolean on;
    private boolean cod;

    public boolean isOn() {
        return on;
    }

    public void setOn(boolean on) {
        this.on = on;
    }

    public boolean isCod() {
        return cod;
    }

    public void setCod(boolean cod) {
        this.cod = cod;
    }

    public PaymentModeEntry(boolean on, boolean cod) {
        this.on = on;
        this.cod = cod;
    }

    public PaymentModeEntry() {}
}
