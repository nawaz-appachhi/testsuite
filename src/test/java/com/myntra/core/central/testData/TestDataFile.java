package com.myntra.core.central.testData;

import com.myntra.core.exceptions.NoTestDataException;
import com.myntra.utils.logger.ILogger;

import java.util.HashMap;
import java.util.Map;

public class TestDataFile implements ILogger {
    private HashMap<String, TestData> testData = new HashMap<>();

    public HashMap<String, TestData> getTestData() {
        return testData;
    }

    public TestDataFile parse(HashMap<String, Map> allTestsData) {
        for (String testName : allTestsData.keySet()) {
            UserData userData = new UserData((HashMap) allTestsData.get(testName)
                                                                   .get("user"));
            AddressData addressData = new AddressData((HashMap) allTestsData.get(testName)
                                                                            .get("address"));
            ProductData productData = new ProductData((HashMap) allTestsData.get(testName)
                                                                            .get("product"));
            testData.put(testName, new TestData(testName, userData, addressData, productData));
        }
        return this;
    }

    public TestData getTestDataFor(String testName) throws NoTestDataException {
        TestData testData = this.testData.get(testName);
        if (null == testData) {
            String message = String.format("No test data for test - %s", testName);
            LOG.error(message);
            throw new NoTestDataException(message);
        }
        return testData;
    }

    @Override
    public String toString() {
        String allTestData = "\nData for:\n";
        int counter = 0;
        for (String testName : testData.keySet()) {
            allTestData += String.format("\n\n#%d - %s", ++counter, testData.get(testName).toString());
        }
        return allTestData;
    }
}

