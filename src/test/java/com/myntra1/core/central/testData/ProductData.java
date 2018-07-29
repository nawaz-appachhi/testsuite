package com.myntra.core.central.testData;

import java.util.HashMap;

public class ProductData extends Data {
    public ProductData(HashMap<String, String> productData) {
        parsedData = parseAndAddData(ProductData.class.getSimpleName(), productData);
    }

    public String get(String key) {
        return get(ProductData.class.getSimpleName(), key);
    }

    @Override
    public String toString() {
        return String.format("\nProduct data:\n%s", parsedData.toString());
    }
}

