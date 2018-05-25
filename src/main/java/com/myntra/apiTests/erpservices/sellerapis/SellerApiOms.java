package com.myntra.apiTests.erpservices.sellerapis;

import com.myntra.sellerapi.service.client.entry.OrderLineEntry;

import java.util.List;


public class SellerApiOms {
	
	private List<OrderLineEntry> orderLineEntry;

    public List<OrderLineEntry> getOrderLineEntry() {
        return orderLineEntry;
    }
    private List<OrderLineEntry> orderLineEntries;
    public void setOrderEntry(List<OrderLineEntry> orderLineEntries) {
        this.orderLineEntry =  orderLineEntries;
    }

    @Override
    public String toString() {
        return ""+orderLineEntry;
    }
}

