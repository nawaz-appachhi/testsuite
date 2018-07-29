package com.myntra.core.central;

import org.openqa.selenium.WebDriver;
import org.testng.asserts.SoftAssert;
import java.util.HashMap;
import java.util.Map;

public class TestExecutionContext {
    private String testName;
    private ThreadLocal<WebDriver> webDriver;
    private String deviceId;
    private Map<String, String> testData;
    private SoftAssert soft;
    private HashMap<String, Object> testExecutionState;

    public TestExecutionContext(ThreadLocal<WebDriver> webDriver, String deviceId, String testName,
                                Map<String, String> testData, SoftAssert soft) {
        this.webDriver = webDriver;
        this.deviceId = deviceId;
        this.testName = testName;
        this.testData = testData;
        this.soft = soft;
        this.testExecutionState = new HashMap<>();
    }

    public Map<String, String> getTestData() {
        return testData;
    }

    public String getTestName() {
        return testName;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public ThreadLocal<WebDriver> getWebDriver() {
        return webDriver;
    }

    public SoftAssert getSoftAssert() {
        return soft;
    }

    public void addTestState(String key, HashMap<String, String> productDetails) {
        testExecutionState.put(key, productDetails);
    }

    public Object getTestState(String key) {
        return testExecutionState.get(key);
    }
}
