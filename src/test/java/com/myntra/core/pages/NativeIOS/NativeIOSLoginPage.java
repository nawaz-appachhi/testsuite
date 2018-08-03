package com.myntra.core.pages.NativeIOS;

import com.myntra.core.pages.AddressPage;
import com.myntra.core.pages.HomePage;
import com.myntra.core.pages.LoginPage;
import io.qameta.allure.Step;
import org.apache.commons.lang3.NotImplementedException;

public class NativeIOSLoginPage extends LoginPage {

    @Step
    @Override
    public HomePage login() {
        utils.sendKeys(getLocator("txtEmailId"), getUserTestData().getEmail());
        utils.sendKeys(getLocator("txtPassword"), getUserTestData().getPassword());
        utils.waitForElementToBeVisible(getLocator("btnLogin"));
        utils.click(getLocator("btnLogin"));
        return HomePage.createInstance();
    }

    @Step
    @Override
    public LoginPage navigateToLogin() {
        LOG.debug(String.format("(%s) Logging in...", getChannelUtils()));
        utils.click(getLocator("icoProfile"));
        utils.findElement(getLocator("lnkLogin"))
             .click();
        utils.waitForElementToBeVisible(getLocator("txtEmailId"));
        return this;
    }

    @Step
    @Override
    public boolean isLoginOptionDisplayed() {
        throw new NotImplementedException(getClass().getSimpleName() + "-" + new Object() {
        }.getClass()
         .getEnclosingMethod()
         .getName() + " - NOT YET IMPLEMENTED");
    }

    @Step
    @Override
    public AddressPage signUpAfterPlacingOrder() {
        throw new NotImplementedException(getClass().getSimpleName() + "-" + new Object() {
        }.getClass()
         .getEnclosingMethod()
         .getName() + " - NOT YET IMPLEMENTED");
    }

    @Step
    @Override
    public HomePage signUp() {
        throw new NotImplementedException(getClass().getSimpleName() + "-" + new Object() {
        }.getClass()
         .getEnclosingMethod()
         .getName() + " - NOT YET IMPLEMENTED");
    }

    @Step
    @Override
    public HomePage logout() {
        utils.click(getLocator("icoProfile"));
        utils.click(getLocator("btnLogOut"));
        return HomePage.createInstance();
    }
}