package com.myntra.core.pages.MobileWeb;

import com.myntra.core.pages.AddressPage;
import com.myntra.core.pages.HomePage;
import com.myntra.core.pages.LoginPage;
import io.qameta.allure.Step;

import static com.myntra.core.utils.JavaUtils.generateUserNameForTest;

public class MobileWebLoginPage extends LoginPage {

    @Step
    @Override
    public HomePage login() {
        utils.click(getLocator("txtEmailId"), true);
        utils.sendKeys(getLocator("txtEmailId"), getUserTestData().getEmail());
        utils.sendKeys(getLocator("txtPassword"), getUserTestData().getPassword());
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
        utils.sendKeys(getLocator("txtSignUpEmail"), generateUserNameForTest(testExecutionContext.getTestName()) + "@myntra.com");
        utils.sendKeys(getLocator("txtSignUpPassword"), "password");
        utils.sendKeys(getLocator("txtSignUpFname"), getUserTestData().getFirstName());
        utils.sendKeys(getLocator("txtSignUpMobile"), getUserTestData().getPhoneDetails()
                                                                       .get(0)
                                                                       .getPhone());
        utils.click(getLocator("btnGenderM"), true);
        utils.click(getLocator("btnCreateAccount"), true);
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
        utils.click(getLocator("mnuHamburger"), true);
        utils.click(getLocator("mnuAccount"), true);
        utils.click(getLocator("btnLogOut"), true);
        return HomePage.createInstance();
    }
}

