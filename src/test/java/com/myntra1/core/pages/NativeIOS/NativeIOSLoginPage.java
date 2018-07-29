package com.myntra.core.pages.NativeIOS;

import com.myntra.core.pages.HomePage;
import com.myntra.core.pages.LoginPage;
import io.qameta.allure.Step;

public class NativeIOSLoginPage extends LoginPage {

    @Step
    @Override
    public HomePage login() {
        utils.sendKeys(getLocator("txtEmailId"), getTestData().get("UserName"));
        utils.sendKeys(getLocator("txtPassword"), getTestData().get("Password"));
        utils.waitForElementToBeVisible(getLocator("btnLogin"));
        utils.click(getLocator("btnLogin"));
        return HomePage.createInstance();
    }

    @Step
    @Override
    public LoginPage navigateToLogin() {
        LOG.debug(String.format("(%s) Logging in...", getChannelUtils()));
        utils.click(getLocator("icoProfile"));
        utils.findElement(getLocator("lnkLogin")).click();
        utils.waitForElementToBeVisible(getLocator("txtEmailId"));
        return this;
    }
}