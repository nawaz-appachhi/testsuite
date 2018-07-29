package com.myntra.core.pages;

import com.myntra.core.enums.ChannelUtils;
import com.myntra.core.pages.Desktop.DesktopAddressPage;
import com.myntra.core.pages.MobileWeb.MobileWebAddressPage;
import com.myntra.core.pages.NativeAndroid.NativeAndroidAddressPage;
import com.myntra.core.pages.NativeIOS.NativeIOSAddressPage;
import com.myntra.core.utils.DynamicEnhancer;
import com.myntra.core.utils.DynamicLogger;
import com.myntra.ui.Direction;
import io.qameta.allure.Step;
import org.apache.commons.lang3.NotImplementedException;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.ExpectedConditions;

public abstract class AddressPage extends Page {

    public static AddressPage createInstance() {
        AddressPage derivedAddressPage;
        switch (CHANNEL) {
            case NATIVE_ANDROID:
                derivedAddressPage = (AddressPage) DynamicEnhancer.create(NativeAndroidAddressPage.class, new DynamicLogger());
                break;

            case NATIVE_IOS:
                derivedAddressPage = (AddressPage) DynamicEnhancer.create(NativeIOSAddressPage.class, new DynamicLogger());
                break;

            case DESKTOP_WEB:
                derivedAddressPage = (AddressPage) DynamicEnhancer.create(DesktopAddressPage.class, new DynamicLogger());
                break;

            case MOBILE_WEB:
                derivedAddressPage = (AddressPage) DynamicEnhancer.create(MobileWebAddressPage.class, new DynamicLogger());
                break;

            default:
                throw new NotImplementedException("Invalid Channel");
        }
        return derivedAddressPage;
    }

    @Override
    public String pageName() {
        return AddressPage.class.getSimpleName();
    }

    @Step
    public PaymentPage addressContinue() {
        utils.wait((ExpectedConditions.invisibilityOfElementLocated(getLocator("btnSaveAddress"))));
        utils.click(getLocator("btnContinue"), true);
        return PaymentPage.createInstance();
    }

    @Step
    public AddressPage removeAddress() {
        utils.click(getLocator("btnEditChange"), true);
        utils.click(getLocator("btnRemove"), true);
        return this;
    }

    @Step
    public AddressPage emptyAddress() {
        if (isEmptyAddressMsgPresent()) {
            utils.click(getLocator("tlbBack"), true);
        } else {
            removeAllAddresses();
            utils.click(getLocator("tlbBack"), true);
        }
        return this;
    }

    @Step
    public AddressPage addAddress() {
        utils.waitForElementToBeVisible(getLocator("txtPincode"));
        utils.click(getLocator("txtPincode"), true);
        utils.sendKeys(getLocator("txtPincode"), getTestData().get("Pincode"));
        utils.sendKeys(getLocator("txtName"), getTestData().get("Name"));
        utils.sendKeys(getLocator("txtAddress"), getTestData().get("Address"));
        utils.scroll(Direction.DOWN);
        addressType();
        if (getChannelUtils() == ChannelUtils.DESKTOP) {
            utils.waitForElementToBeVisible(getLocator("txtLocality"));
            utils.click(getLocator("txtLocality"));
            utils.sendKeys(getLocator("txtLocality"), getTestData().get("Locality"));
        } else {
            utils.click(getLocator("lnkChoose"), true);
            utils.sendKeys(getLocator("txtLocality"), getTestData().get("Locality") + Keys.ENTER);
        }
        utils.sendKeys(getLocator("txtMobileNumber"), getTestData().get("MobileNumber"));
        utils.click(getLocator("btnSaveAddress"), true);
        return this;
    }

    @Step
    public boolean isAddressRemovedSuccessfully() {
        return utils.isElementPresent(getLocator("txaAddNewAddress"), 5);
    }

    @Step
    public boolean isEditAddressSuccessful() {
        utils.wait(ExpectedConditions.invisibilityOfElementLocated(getLocator("btnSaveAddress")));
        utils.scroll(Direction.DOWN, 1);
        return (utils.findElement(getLocator("txaEditedName"))
                     .getText()
                     .equalsIgnoreCase(getTestData().get("editedName")));
    }

    @Step
    public boolean isAddressAddedSuccessfully() {
        return (utils.isElementPresent(getLocator("btnEditChange"), 5));
    }

    @Step
    protected void addressType() {
        String rdoType = getTestData().get("rdoType");
        if (rdoType.contains("rdoHome")) {
            utils.click(getLocator(rdoType), true);
        } else if (rdoType.contains("rdoOffice")) {
            utils.click(getLocator(rdoType), true);
        }
    }

    @Step
    public AddressPage editAddress() {
        utils.click(getLocator("btnEditChange"), true);
        if (utils.isElementPresent(getLocator("btnEdit"), 3)) {
            utils.click(getLocator("btnEdit"), true);
            utils.findElement(getLocator("txtName"))
                 .clear();
            utils.sendKeys(getLocator("txtName"), getTestData().get("editedName"));
        }
        utils.click(getLocator("btnSaveAddress"), true);
        return AddressPage.createInstance();
    }

    @Step
    protected boolean isEmptyAddressMsgPresent() {
        return utils.isElementPresent(getLocator("lblEmptyAddress"), 3);
    }

    @Step
    protected void removeAllAddresses() {
        while (!isEmptyAddressMsgPresent()) {
            utils.click(getLocator("btnRemove"), true);
        }
    }

    @Step
    public AddressPage viewDetails() {
        utils.wait((ExpectedConditions.invisibilityOfElementLocated(getLocator("btnSaveAddress"))));
        utils.waitForElementToBeVisible(getLocator("lnkViewDetails"));
        utils.click(getLocator("lnkViewDetails"), true);
        return this;
    }

    @Step
    public boolean isAddressPageDisplayed() {
        return (utils.isElementPresent(getLocator("lnkDeliveryTitle"), 2));
    }

    @Step
    public boolean isPriceDetailsDisplayed() {
        return (utils.isElementPresent(getLocator("lnkHideDetails"), 2));
    }

}
