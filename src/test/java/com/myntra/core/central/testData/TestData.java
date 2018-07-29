package com.myntra.core.central.testData;

import com.myntra.utils.logger.ILogger;

public class TestData implements ILogger {
    private String name;
    private UserData userData;
    private AddressData addressData;
    private ProductData productData;

    public TestData(String name, UserData userData, AddressData addressData, ProductData productData) {
        LOG.info(String.format("Loading test data for test - %s", name));
        this.name = name;
        this.userData = userData;
        this.addressData = addressData;
        this.productData = productData;
    }

    @Override
    public String toString() {
        return String.format("Test Name: %s,\n%s,\n%s, \n%s", name, userData.toString(), addressData.toString(),
                productData.toString());
    }

    public UserData user() {
        return userData;
    }

    public AddressData address() {
        return addressData;
    }

    public ProductData product() {
        return productData;
    }

    public boolean isEmpty() {
        return userData.isEmpty() && addressData.isEmpty() && productData.isEmpty();
    }

    public String get(String editedName) {
        return null;
    }
}

