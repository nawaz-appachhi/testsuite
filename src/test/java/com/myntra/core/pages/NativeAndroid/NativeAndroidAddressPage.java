package com.myntra.core.pages.NativeAndroid;

import com.myntra.core.pages.AddressPage;
import com.myntra.core.pages.PaymentPage;
import io.qameta.allure.Step;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class NativeAndroidAddressPage extends AddressPage {

    @Step
    @Override
    public AddressPage addAddress() {
        utils.waitForElementToBeVisible(getLocator("txtPincode"));
        utils.sendKeys(getLocator("txtPincode"), getTestData().get("Pincode"));
        utils.sendKeys(getLocator("txtName"), getTestData().get("Name"));
        utils.sendKeys(getLocator("txtAddress"), getTestData().get("Address"));
        utils.sendKeys(getLocator("txtMobileNumber"), getTestData().get("MobileNumber"));
        addressType();
        utils.click(getLocator("btnSaveAddress"));
        utils.waitForElementToBeVisible(getLocator("btnContinue"));
        return this;
    }

    @Step
    @Override
    public AddressPage removeAddress() {
        utils.click(getLocator("btnEditChange"), true);
        utils.click(getLocator("btnRemove"), true);
        return this;
    }

    @Step
    @Override
    public PaymentPage addressContinue() {
        utils.waitForElementToBeVisible(getLocator("btnContinue"));
        utils.click(getLocator("btnContinue"));
        return PaymentPage.createInstance();
    }

    @Step
    @Override
    protected boolean isEmptyAddressMsgPresent() {
        webView();
        return utils.isElementPresent(getLocator("lblEmptyAddress"), 3);
    }

    @Step
    @Override
    public AddressPage emptyAddress() {
        if (isEmptyAddressMsgPresent()) {
            nativeView();
            goBack();
            goBack();
        } else {
            removeAllAddresses();
            nativeView();
            goBack();
            goBack();
        }
        return this;
    }

    @Step
    @Override
    public boolean isAddressRemovedSuccessfully() {
        return utils.isElementPresent(getLocator("txtPincode"), 5);
    }

    @Step
    @Override
    public boolean isEditAddressSuccessful() {
        utils.wait(ExpectedConditions.invisibilityOfElementLocated(getLocator("btnSaveAddress")));
        return (utils.findElement(getLocator("txaEditedName"))
                     .getText()
                     .toString()
                     .equalsIgnoreCase(getTestData().get("editedName")));
    }

    @Step
    @Override
    public boolean isAddressPageDisplayed() {
        return (utils.isElementPresent(getLocator("btnEditChange"), 2));
    }

    @Step
    @Override
    public AddressPage viewDetails() {
        utils.waitForElementToBeVisible(getLocator("lnkViewDetails"));
        utils.click(getLocator("lnkViewDetails"), true);
        return this;
    }
}
