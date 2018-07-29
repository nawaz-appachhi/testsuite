package com.myntra.core.central.testData;

import java.util.HashMap;

public class UserData extends Data {
    public UserData(HashMap<String, String> userData) {
        parsedData = parseAndAddData(UserData.class.getSimpleName(), userData);
    }

    public String get(String key) {
        return get(UserData.class.getSimpleName(), key);
    }

    @Override
    public String toString() {
        return String.format("\nUser data:\n%s", parsedData.toString());
    }
}