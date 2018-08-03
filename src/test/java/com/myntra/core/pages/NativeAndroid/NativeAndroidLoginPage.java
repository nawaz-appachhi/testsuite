package com.myntra.core.pages.NativeAndroid;

import com.myntra.core.pages.AddressPage;
import com.myntra.core.pages.HomePage;
import com.myntra.core.pages.LoginPage;
import com.myntra.utils.test_utils.Assert;
import io.qameta.allure.Step;
import org.apache.commons.lang3.NotImplementedException;

import static com.myntra.core.utils.JavaUtils.generateUserNameForTest;

public class NativeAndroidLoginPage extends LoginPage {

    @Step
    @Override
    protected void isLoaded() {
        Assert.assertTrue(utils.isElementPresent(getLocator("btnOBSLogin"), 10), String.format("Page - %s - NOT Loaded", getClass().getSimpleName()));
    }

    @Step
    @Override
    protected void load() {
        if (!utils.isElementPresent(getLocator("btnOBSLogin"), 10)) {
            goBack();
        }
    }

    @Step
    @Override
    public HomePage login() {
        utils.findElement(getLocator("txtEmailId"))
             .sendKeys(getUserTestData().getEmail());
        utils.findElement(getLocator("txtPassword"))
             .sendKeys(getUserTestData().getPassword());
        utils.click(getLocator("btnLogin"), true);
        return HomePage.createInstance();
    }

    @Step
    @Override
    public LoginPage navigateToLogin() {
        utils.click(getLocator("lnkLogIn"), true);
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
        utils.click(getLocator("lnkSignUp"));
        utils.click(getLocator("lblEMAILADDRESS"));
        utils.sendKeys(getLocator("txtSignUpEmail"), generateUserNameForTest(testExecutionContext.getTestName()));
        utils.sendKeys(getLocator("txtSignUpPassword"), "password");
        utils.sendKeys(getLocator("txtSignUpFname"), testExecutionContext.getTestName());
        utils.sendKeys(getLocator("txtSignUpMobile"), getUserTestData().getPhoneDetails()
                                                                       .get(0)
                                                                       .getPhone());
        utils.click(getLocator("btnGenderM"), true);
        utils.click(getLocator("btnCreateAccount"), true);
        return HomePage.createInstance();
    }
}
