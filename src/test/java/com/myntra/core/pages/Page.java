package com.myntra.core.pages;

import com.myntra.core.central.SFCentral;
import com.myntra.core.central.SessionContext;
import com.myntra.core.central.TestExecutionContext;
import com.myntra.core.central.pageLocator.Identifier;
import com.myntra.core.central.pageLocator.PageLocatorsLoader;
import com.myntra.core.enums.ChannelUtils;
import com.myntra.ui.AbstractBasePage;
import com.myntra.ui.Channel;
import com.myntra.ui.Direction;
import com.myntra.utils.config.ConfigProperties;
import com.myntra.utils.test_utils.Assert;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.asserts.SoftAssert;
import java.time.Duration;
import java.util.Map;
import java.util.Set;

public abstract class Page extends AbstractBasePage<Page> implements IPage {
    static Channel CHANNEL = Channel.value(CONFIG_MANAGER.getString(ConfigProperties.CHANNEL.getKey()));
    private String testName;
    private PageLocatorsLoader pageLocatorsLoader;
    private ChannelUtils channelUtils;
    private Map<String, String> testData;
    protected SoftAssert soft;
    protected TestExecutionContext testExecutionContext;

    Page() {

        super(SessionContext.getTestExecutionContext(Thread.currentThread()
                                                           .getId())
                            .getWebDriver(), SessionContext.getTestExecutionContext(Thread.currentThread()
                                                                                          .getId())
                                                           .getDeviceId());
        testExecutionContext = SessionContext.getTestExecutionContext(Thread.currentThread()
                                                                                                 .getId());
        testName = testExecutionContext.getTestName();
        testData = testExecutionContext.getTestData();

        channelUtils = ChannelUtils.mappingChannelForLoadingData(CHANNEL);
        String currentPageName = pageName();
        pageLocatorsLoader = SFCentral.INSTANCE.getPageDef(currentPageName);
        locators = SFCentral.INSTANCE.convertPageLocatorsToProperties(currentPageName, channelUtils);
        soft = testExecutionContext.getSoftAssert();
    }

    @Override
    protected By getLocator(final String property, final Object... formatArgs) {
        return super.getLocator(property.toLowerCase(), formatArgs);
    }

    @Override
    protected void load() {
        utils.waitForPageLoad();
    }

    @Override
    protected void isLoaded() throws Error {
    }

    protected Map<String, String> getTestData() {
        return testData;
    }

    @Override
    public ChannelUtils getChannelUtils() {
        return channelUtils;
    }

    @Override
    public Identifier getIdentifier(String name) {
        return pageLocatorsLoader.getIdentifier(channelUtils, name);
    }

    @Step
    protected void goBack() {
        getDriver().navigate()
                   .back();
    }

    @Step
    protected void webView() {
        if (getChannelUtils().equals(ChannelUtils.NATIVE_IOS)) {
            Set<String> contextNames = ((IOSDriver) getDriver()).getContextHandles();
            LOG.info(String.format("Page Contains views as:::: %s", contextNames));

            for (String contextName : contextNames) {
                if (contextName.contains("WEBVIEW")) {
                    ((IOSDriver) getDriver()).context(contextName);
                }
            }
        } else if (getChannelUtils().equals(ChannelUtils.NATIVE_ANDROID)) {
            ((AndroidDriver) getDriver()).context("WEBVIEW_com.myntra.android");
            LOG.info("Switched to web view for Android channel");
        }
    }

    @Step
    protected void nativeView() {
        if (getChannelUtils().equals(ChannelUtils.NATIVE_IOS)) {
            ((IOSDriver) getDriver()).context("NATIVE_APP");
        } else if (getChannelUtils().equals(ChannelUtils.NATIVE_ANDROID)) {
            ((AndroidDriver) getDriver()).context("NATIVE_APP");
            LOG.info("Switched to Native view for Android channel");
        }
    }

    private void swipe_m(Direction direction, int count, double startFraction, double endFraction) {
        try {
            LOG.debug(String.format("Trying to Swipe %s for %d times", direction, count));
            Dimension size = getDriver().manage()
                                        .window()
                                        .getSize();
            int starty = (int) ((double) size.height * startFraction);
            int endy = (int) ((double) size.height * endFraction);
            int width = size.width / 2;
            for (int i = 0; i < count; ++i) {
                (new TouchAction((AppiumDriver) getDriver())).press(PointOption.point(width, starty))
                                                             .waitAction(
                                                                     WaitOptions.waitOptions(Duration.ofSeconds(1L)))
                                                             .moveTo(PointOption.point(width, endy))
                                                             .release()
                                                             .perform();
            }
        } catch (Exception var12) {
            LOG.error(String.format("swipe%s", direction.toString()), var12);
        }
    }

    private boolean isWebView() {
        Boolean isCurrentViewWebView = false;
        if (CHANNEL == Channel.NATIVE_ANDROID || CHANNEL == Channel.NATIVE_IOS) {
            isCurrentViewWebView = ((AppiumDriver) getDriver()).getContext()
                                                               .contains("WEBVIEW");
        }
        return isCurrentViewWebView;
    }

    @Step
    protected void swipeUp_m(int count) {
        this.swipe_m(Direction.UP, count, 0.5D, 0.8D);
    }

    @Step
    protected void swipeUp_m() {
        swipeUp_m(1);
    }

    @Step
    protected void scrollTillElementVisible(By locatorName, Direction direction) {
        if (CHANNEL == Channel.NATIVE_ANDROID || CHANNEL == Channel.NATIVE_IOS) {
            if (!isWebView()) {
                swipeTillElementVisible(locatorName, direction);
            }
        } else {
            scrollTillElementVisible(locatorName);
        }
    }

    @Step
    protected void scrollTillElementVisible(By locatorName) {
        if (CHANNEL == Channel.NATIVE_ANDROID || CHANNEL == Channel.NATIVE_IOS) {
            ((JavascriptExecutor) ((AndroidDriver) getDriver())).executeScript("arguments[0].scrollIntoView(true);",
                    getDriver().findElement(locatorName));
        }
    }

    private void swipeTillElementVisible(By locatorName, Direction direction) {
        int scrollOrSwipeCount = 0;
        int maxScrollOrSwipeCount = 10;
        while (!utils.isElementPresent((locatorName), 2) && (scrollOrSwipeCount < maxScrollOrSwipeCount)) {
            switch (direction) {
                case UP:
                    swipeUp_m();
                    break;
                case DOWN:
                    utils.swipeDown_m();
                    break;
                default:
                    throw new IllegalArgumentException(
                            String.format("Incorrect option - swipe provided for %s direction", direction));
            }
            scrollOrSwipeCount++;
        }
        Assert.assertTrue((scrollOrSwipeCount < maxScrollOrSwipeCount),
                String.format("Did not find locator - %s in %d attempts of swipe for %s direction", locatorName,
                        scrollOrSwipeCount, direction));
    }

    @Step
    protected void handleDevicePermissions(){
        if (utils.isElementPresent(By.id("com.google.android.gms:id/credential_save_reject"),3)) {
            utils.findElement(By.id("com.google.android.gms:id/credential_save_reject")).click();
        }
    }
}

