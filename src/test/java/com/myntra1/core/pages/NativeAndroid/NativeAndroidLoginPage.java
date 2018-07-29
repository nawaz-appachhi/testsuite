package com.myntra.core.pages.NativeAndroid;

import com.myntra.core.pages.HomePage;
import com.myntra.core.pages.LoginPage;
import com.myntra.utils.test_utils.Assert;
import io.qameta.allure.Step;

public class NativeAndroidLoginPage extends LoginPage {

    @Override
    protected void isLoaded() throws Error {
        Assert.assertTrue(utils.isElementPresent(getLocator("btnOBSLogin"), 30),
                String.format("Page - %s - NOT Loaded", getClass().getSimpleName()));
    }

    @Step
    @Override
    public HomePage login() {
        utils.sendKeys(getLocator("txtEmailId"), getTestData().get("UserName"));
        utils.sendKeys(getLocator("txtPassword"), getTestData().get("Password"));
        utils.click(getLocator("btnLogin"));
        return HomePage.createInstance();
    }

    @Step
    @Override
    public LoginPage navigateToLogin() {
        utils.click(getLocator("lnkLogIn"));
        return this;
    }
}
