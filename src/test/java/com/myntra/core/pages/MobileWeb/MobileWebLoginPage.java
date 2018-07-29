package com.myntra.core.pages.MobileWeb;

import com.myntra.core.pages.HomePage;
import com.myntra.core.pages.LoginPage;
import io.qameta.allure.Step;

public class MobileWebLoginPage extends LoginPage {

    @Step
    @Override
    public HomePage login() {
        utils.click(getLocator("txtEmailId"), true);
        utils.sendKeys(getLocator("txtEmailId"), getTestData().get("UserName"));
        utils.sendKeys(getLocator("txtPassword"), getTestData().get("Password"));
        utils.waitForElementToBeVisible(getLocator("btnLogin"));
        utils.click(getLocator("btnLogin"), true);
        return HomePage.createInstance();
    }

    @Step
    @Override
    public LoginPage navigateToLogin() {
        utils.click(getLocator("mnuHamburger"), true);
        utils.click(getLocator("lnkLogIn"), true);
        utils.waitForElementToBeVisible(getLocator("txtEmailId"));
        return this;
    }
}
