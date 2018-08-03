package com.myntra.core.pages.MobileWeb;

import com.myntra.core.pages.AddressPage;
import io.qameta.allure.Step;
import org.openqa.selenium.Keys;

public class MobileWebAddressPage extends AddressPage {

    @Override
    @Step
    public AddressPage addAddress() {
        utils.waitForElementToBeVisible(getLocator("txtPincode"));
        utils.click(getLocator("txtPincode"), true);
        utils.sendKeys(getLocator("txtPincode"), getAddressTestData().getPincode());
        utils.sendKeys(getLocator("txtName"), getAddressTestData().getName());
        utils.sendKeys(getLocator("txtAddress"), getAddressTestData().getAddress());
        scrollTillElementVisible(getLocator("rdoHome"));
        selectAddressType();
        utils.sendKeys(getLocator("txtLocality"), getAddressTestData().getLocality() + Keys.ENTER);
        utils.sendKeys(getLocator("txtMobileNumber"), getUserTestData().getPhoneDetails()
                                                                       .get(0)
                                                                       .getPhone());
        utils.waitForElementToBeVisible(getLocator("btnSaveAddress"));
        utils.click(getLocator("btnSaveAddress"), true);
        return this;
    }

}
