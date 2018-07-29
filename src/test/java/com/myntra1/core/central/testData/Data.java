package com.myntra.core.central.testData;

import com.myntra.utils.test_utils.Assert;

import java.util.HashMap;

public abstract class Data {
    protected HashMap<String, String> parsedData = new HashMap<>();

    protected HashMap parseAndAddData(String className, HashMap<String, String> addressData) {
        HashMap<String, String> parsedDataMap = new HashMap<>();
        for (String eachKey : addressData.keySet()) {
            String correspondingValue = String.valueOf(addressData.get(eachKey));
            eachKey = eachKey.toLowerCase();
            Assert.assertFalse(parsedDataMap.containsKey(eachKey),
                    String.format("%s - Duplicate key - '%s' with value - '%s' provided", className, eachKey,
                            correspondingValue));
            parsedDataMap.put(eachKey.toLowerCase(), correspondingValue);
        }
        return parsedDataMap;
    }

    public String get(String className, String key) {
        key = key.toLowerCase();
        String correspondingValue = parsedData.get(key);
        Assert.assertNotNull(correspondingValue, String.format("%s - No data available for '%s'", className, key));
        return correspondingValue;
    }

    public boolean isEmpty() {
        return parsedData.size() == 0;
    }
}
