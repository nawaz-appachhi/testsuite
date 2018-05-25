package com.myntra.apiTests.inbound.helper.planningandbuying.RequestResponseClasses;

import com.myntra.orderindent.client.entry.BaseOrderIndentEntry;

import java.util.HashMap;

/**
 * Created by 300000929 on 16/01/17.
 */
public class CreateOIResponseDate extends BaseOrderIndentEntry {

    HashMap<String,Object> allValues = new HashMap<>();

    public CreateOIResponseDate() {

    }

    public void setAllValues()
    {
        allValues = new HashMap<>();
        allValues.put("vendorName",getVendorName());
        allValues.put("vendorId",getVendorId());
        allValues.put("vendorContactPerson",getVendorContactPerson());
        allValues.put("vendorWarehouseLocation",getVendorWarehouseLocation());
        allValues.put("commercialType",getCommercialType());
        allValues.put("stockOrigin",getStockOrigin());
        allValues.put("paymentTerms",getPaymentTerms());
        allValues.put("vendorEmail",getVendorEmail());
        allValues.put("seasonYear",getSeasonYear());
        allValues.put("season",getSeason());
        allValues.put("orderIndentOrderType",getOrderIndentOrderType());
        allValues.put("orderIndentSource",getOrderIndentSource());
        allValues.put("createdBy",getCreatedBy());
        allValues.put("createdOn",getCreatedOn());
        allValues.put("lastModifiedOn",getLastModifiedOn());
        allValues.put("id",getId());
        allValues.put("warningCount",getWarningCount());
        allValues.put("jobId",getJobId());
    }

    public Object getValue(String key)
    {
        return allValues.get(key);
    }
}
