package com.myntra.core.central.testData;

import java.util.HashMap;

public class AddressData extends Data {
    public AddressData(HashMap<String, String> addressData) {
        parsedData = parseAndAddData(AddressData.class.getSimpleName(), addressData);
    }

    public String get(String key) {
        return get(AddressData.class.getSimpleName(), key);
    }

    @Override
    public String toString() {
        return String.format("\nAddress data:\n%s", parsedData.toString());
    }
}

