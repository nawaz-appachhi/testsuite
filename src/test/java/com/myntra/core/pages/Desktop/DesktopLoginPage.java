package com.myntra.core.pages.Desktop;

import com.myntra.core.pages.AddressPage;
import com.myntra.core.pages.HomePage;
import com.myntra.core.pages.LoginPage;
import io.qameta.allure.Step;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static com.myntra.core.utils.JavaUtils.generateUserNameForTest;

public class DesktopLoginPage extends LoginPage {

    @Step
    @Override
    public HomePage login() {
        utils.sendKeys(getLocator("txtEmailId"), getUserTestData().getEmail());
        utils.sendKeys(getLocator("txtPassword"), getUserTestData().getPassword());
        utils.waitForElementToBeVisible(getLocator("btnLogin"));
        utils.click(getLocator("btnLogin"));
        utils.wait(ExpectedConditions.invisibilityOfElementLocated(getLocator("btnLogin")), 10);
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

    @Step
    @Override
    public boolean isLoginOptionDisplayed() {
        return utils.isElementPresent(getLocator("btnLogin"), 5);
    }

    @Step
    @Override
    public AddressPage signUpAfterPlacingOrder() {
        utils.waitForElementToBeVisible(getLocator("lnkSignUp"));
        utils.click(getLocator("lnkSignUp"), true);
        utils.sendKeys(getLocator("txtSignUpEmail"),(generateUserNameForTest(testExecutionContext.getTestName()))+"@myntra.com");
        utils.sendKeys(getLocator("txtSignUpPassword"), "password");
        utils.sendKeys(getLocator("txtSignUpMobile"), getUserTestData().getPhoneDetails()
                                                                       .get(0)
                                                                       .getPhone());
        utils.click(getLocator("btnGenderM"), true);
        utils.click(getLocator("btnCreateAccount"), true);
        utils.wait(ExpectedConditions.invisibilityOfElementLocated(getLocator("btnCreateAccount")));
        return AddressPage.createInstance();
    }

    @Step
    @Override
    public HomePage signUp() {
        HomePage.createInstance()
                .openMenu();
        navigateToLogin();
        signUpAfterPlacingOrder();
        return HomePage.createInstance();
    }

    @Step
    @Override
    public HomePage logout() {
        utils.click(getLocator("icoUser"));
        utils.wait(ExpectedConditions.elementToBeClickable(getLocator("lnkLogOut")));
        utils.moveToElement(getLocator("lnkLogOut"));
        utils.click(getLocator("lnkLogOut"));
        return HomePage.createInstance();
    }
}
