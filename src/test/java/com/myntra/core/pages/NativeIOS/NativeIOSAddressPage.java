package com.myntra.core.pages.NativeIOS;

import com.myntra.core.pages.AddressPage;
import com.myntra.core.pages.PaymentPage;
import com.myntra.ui.Direction;
import io.qameta.allure.Step;
import org.apache.commons.lang3.NotImplementedException;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class NativeIOSAddressPage extends AddressPage {

    @Step
    @Override
    public AddressPage removeAddress() {
        utils.click(getLocator("btnEditChange"));
        utils.click(getLocator("btnRemove"));
        return this;
    }

    @Step
    @Override
    public AddressPage addAddress() {
        utils.sendKeys(getLocator("txtPincode"), getTestData().get("Pincode"));
        utils.findElement(getLocator("lnkChoose")).click();
        utils.sendKeys(getLocator("txtLocality"), getTestData().get("Locality"));
        utils.findElement(getLocator("btnSaveLocality")).click();
        utils.sendKeys(getLocator("txtName"), getTestData().get("Name"));
        utils.sendKeys(getLocator("txtAddress"), getTestData().get("Address"));
        utils.sendKeys(getLocator("txtMobileNumber"), getTestData().get("MobileNumber"));
        addressType();
        utils.findElement(getLocator("btnSaveAddress")).click();
        return this;
    }

    @Step
    @Override
    protected void addressType() {
        String rdoType = getTestData().get("rdoType");
        if (rdoType.contains("rdoHome")) {
            utils.findElement(getLocator(rdoType))
                 .click();
        } else if (rdoType.contains("rdoOffice")) {
            utils.findElement(getLocator(rdoType))
                 .click();
        }
    }

    @Step
    @Override
    public PaymentPage addressContinue() {
        utils.waitForElementToBeVisible(getLocator("btnContinue"));
        utils.click(getLocator("btnContinue"),true);
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
        if (!isEmptyAddressMsgPresent()) {
            removeAllAddresses();
        }
        nativeView();
        utils.findElement(getLocator("tlbBack"))
             .click();
        return this;
    }

    @Step
    @Override
    public AddressPage editAddress() {
        utils.click(getLocator("btnEditChange"), true);
        if (utils.isElementPresent(getLocator("btnEdit"), 3)) {
            utils.click(getLocator("btnEdit"), true);
            utils.findElement(getLocator("txtName"))
                    .clear();
            utils.sendKeys(getLocator("txtName"), getTestData().get("editedName"));
        }
        utils.click(getLocator("btnDoneKeypad"),true);
        utils.click(getLocator("btnSaveAddress"), true);

        return AddressPage.createInstance();
    }

    @Step
    @Override
    protected void removeAllAddresses() {
        while (!isEmptyAddressMsgPresent()) {
            utils.click(getLocator("btnRemove"), true);
            utils.click(getLocator("btnDelete"), true);
        }
    }

    @Step
    @Override
    public boolean isAddressPageDisplayed() {
        return (utils.isElementPresent(getLocator("btnEditChange"), 5));
    }

    @Step
    @Override
    public boolean isEditAddressSuccessful() {
        utils.wait(ExpectedConditions.invisibilityOfElementLocated(getLocator("btnSaveAddress")));
        utils.scroll(Direction.DOWN, 1);
        return (utils.findElement(getLocator("txaEditedName"))
                .getText()
                .equalsIgnoreCase(getTestData().get("editedName")));
    }


}