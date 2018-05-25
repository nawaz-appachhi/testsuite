package com.myntra.apiTests.erpservices.lms.lmsClient;

/**
 * Created by Shubham Gupta on 4/20/17.
 */

public class RedisResponse {
    private Status status;
    private String value;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
