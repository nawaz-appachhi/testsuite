package com.myntra.scenarios;

import com.myntra.core.business.Home;
import com.myntra.core.central.SFCentral;
import com.myntra.core.central.SessionContext;
import com.myntra.core.central.TestExecutionContext;
import com.myntra.utils.test_utils.Assert;
import com.myntra.utils.test_utils.BaseTest;
import io.qameta.allure.Step;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.testng.*;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

import java.lang.reflect.Method;
import java.util.Map;

public class SupportTest extends BaseTest implements IHookable {
    String testName = null;

    private static final String SOFT_ASSERT = "softAssert";

    @Override
    public void run(IHookCallBack callBack, ITestResult testResult) {
        SoftAssert softAssert = new SoftAssert();
        testResult.setAttribute(SOFT_ASSERT, softAssert);
        callBack.runTestMethod(testResult);
        softAssert = getSoftAssert(testResult);
        if (!testResult.isSuccess()) {
            Throwable throwable = testResult.getThrowable();
            if (null != throwable) {
                if (null != throwable.getCause()) {
                    throwable = throwable.getCause();
                }
                softAssert.assertNull(throwable, ExceptionUtils.getStackTrace(throwable));
            }
        }
        softAssert.assertAll();
    }

    @Step("{0}")
    public static void logStep(final String message){
        //intentionally empty - this will create a step in the Allure report based on the message
    }

    public static SoftAssert getSoftAssert() {
        return getSoftAssert(Reporter.getCurrentTestResult());
    }

    private static SoftAssert getSoftAssert(ITestResult result) {
        Object object = result.getAttribute(SOFT_ASSERT);
        if (object instanceof SoftAssert) {
            return (SoftAssert) object;
        }
        throw new IllegalStateException("Could not find a soft assertion object");
    }

    @BeforeSuite(alwaysRun = true)
    public void beforeSuite(ITestContext context) throws Exception {
        LOG.info(String.format("%s - beforeSuite", this.getClass().getSimpleName()));
        SFCentral.INSTANCE.init();
        SessionContext.addDynamicLoggerListener((TestRunner) context);
    }

    @BeforeClass(alwaysRun = true)
    public void beforeClass(ITestContext context) throws Exception {
        LOG.info(String.format("%s - beforeClass", this.getClass().getSimpleName()));
        SFCentral.INSTANCE.registerContext(context);
    }

    @BeforeMethod(alwaysRun = true)
    public void beforeMethod(ITestContext context, Method method) {
        LOG.info(String.format("%s - beforeMethod", this.getClass().getSimpleName()));
        testName = method.getName();
        LOG.info(String.format("Test Method Name Started :: %s", testName));
        Map<String, String> testData = SFCentral.INSTANCE.getTestData(context, testName);
        TestExecutionContext testExecutionContext = new TestExecutionContext(getDriver(), getDeviceId(), testName,
                testData, new SoftAssert());

        SessionContext.addContext(Thread.currentThread()
                                        .getId(), testExecutionContext);

        LOG.info(String.format("Starting test execution - %s", testName));
    }

    @AfterMethod(alwaysRun = true)
    public void afterMethod(ITestContext context, Method method, ITestResult result) {
        LOG.info(String.format("%s - afterMethod", this.getClass().getSimpleName()));
        SessionContext.remove(Thread.currentThread()
                                    .getId());
    }

    @AfterClass(alwaysRun = true)
    public void afterClass(ITestContext suite) {
        LOG.info(String.format("%s - afterClass", this.getClass().getSimpleName()));
    }

    Home loginAndCleanup() {
        LOG.info(String.format("loginAndCleanup - Set up for test method [%s] started.", testName));
        TestExecutionContext testExecutionContext = SessionContext.getTestExecutionContext(Thread.currentThread()
                                                                                                 .getId());
        testName = testExecutionContext.getTestName();
        validateTestData(testName, testExecutionContext.getTestData());
        Home home = Home.getInstance()
                        .loginSuccessfully()
                        .home()
                        .cleanState();
        LOG.info(String.format("loginAndCleanup - Set up for test method [%s] ended.", testName));
        return home;
    }

    private void validateTestData(String testName, Map<String, String> testData) {
        String message = String.format("No Test Data provided for test name - %s", testName);
        Assert.assertNotNull(testData, message);
        Assert.assertTrue(testData.size() > 0, message);
        LOG.info(String.format("Test: %s :: Loaded %d entries from data file", testName, testData.size()));
    }
}
