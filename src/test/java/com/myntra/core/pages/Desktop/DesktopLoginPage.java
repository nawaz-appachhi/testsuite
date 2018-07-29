package com.myntra.core.pages.Desktop;

import com.myntra.core.pages.HomePage;
import com.myntra.core.pages.LoginPage;
import io.qameta.allure.Step;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class DesktopLoginPage extends LoginPage {

    @Step
    @Override
    public HomePage login() {
        utils.sendKeys(getLocator("txtEmailId"), getTestData().get("UserName"));
        utils.sendKeys(getLocator("txtPassword"), getTestData().get("Password"));
        utils.waitForElementToBeVisible(getLocator("btnLogin"));
        utils.click(getLocator("btnLogin"));
        utils.wait(ExpectedConditions.invisibilityOfElementLocated(getLocator("btnLogin")), 5);
        return HomePage.createInstance();
    }

    @Step
    @Override
    public LoginPage navigateToLogin() {
        LOG.debug(String.format("(%s) Logging in...", getChannelUtils()));
        utils.click(getLocator("icoUser"));
        utils.click(getLocator("lnkLogIn"));
        utils.waitForElementToBeVisible(getLocator("txtEmailId"));
        return this;
    }
}
