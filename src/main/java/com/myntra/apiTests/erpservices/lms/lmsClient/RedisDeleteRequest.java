package com.myntra.apiTests.erpservices.lms.lmsClient;

import java.util.List;

/**
 * Created by Shubham Gupta on 4/22/17.
 */
public class RedisDeleteRequest {
    private String host;
    private int port;
    private List<String> keys;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public List<String> getKeys() {
        return keys;
    }

    public void setKeys(List<String> keys) {
        this.keys = keys;
    }

}
