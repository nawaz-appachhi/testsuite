package com.myntra.apiTests.common.entries;

/**
 * Created by Shubham Gupta on 8/1/17.
 */
public class TryNBuyEntry {
    long skuId;
    String status;

    public TryNBuyEntry(long skuId, String status) {
        this.skuId = skuId;
        this.status = status;
    }

    public long getSkuId() {
        return skuId;
    }

    public void setSkuId(long skuId) {
        this.skuId = skuId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "TryNBuyEntry{" +
                "skuId=" + skuId +
                ", status='" + status + '\'' +
                '}';
    }
}
