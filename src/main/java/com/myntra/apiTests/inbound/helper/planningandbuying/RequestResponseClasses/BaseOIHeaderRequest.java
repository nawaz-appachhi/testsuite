package com.myntra.apiTests.inbound.helper.planningandbuying.RequestResponseClasses;

import com.myntra.orderindent.client.entry.BaseOrderIndentEntry;

import java.util.HashMap;
import java.util.Set;

/**
 * Created by 300000929 on 13/01/17.
 */
public class BaseOIHeaderRequest extends BaseOrderIndentEntry{

        HashMap<String,Object> allValues = new HashMap<>();

        public BaseOIHeaderRequest() {

        }

        public void setAllValues()
        {
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
        }

        public Object getValue(String key)
        {
            return allValues.get(key);
        }

        public Set<String> getAllAttributes()
    {
        return allValues.keySet();
    }


}

