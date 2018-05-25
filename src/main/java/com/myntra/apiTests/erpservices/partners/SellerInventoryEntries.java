package com.myntra.apiTests.erpservices.partners;

import com.myntra.sellerapi.service.client.entry.InventoryEntry;

import java.util.List;



public class SellerInventoryEntries {
	private List<InventoryEntry> inventoryEntries;

    public List<InventoryEntry> getInventoryEntries() {
        return inventoryEntries;
    }

    public void setInventoryEntries(List<InventoryEntry> inventoryEntries) {
        this.inventoryEntries = inventoryEntries;
    }

    @Override
    public String toString() {
        return ""+inventoryEntries;
    }
}
