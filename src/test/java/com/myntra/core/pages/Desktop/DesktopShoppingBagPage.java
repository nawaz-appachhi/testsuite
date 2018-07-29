package com.myntra.core.pages.Desktop;

import com.myntra.core.pages.AddressPage;
import com.myntra.core.pages.ShoppingBagPage;
import com.myntra.utils.test_utils.Assert;
import io.qameta.allure.Step;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.HashMap;

public class DesktopShoppingBagPage extends ShoppingBagPage {

    @Override
    protected void isLoaded() throws Error {
        utils.waitForElementToBeVisible(getLocator("spnLoader"));
        Assert.assertTrue(utils.isElementPresent(getLocator("spnLoader"), 15), "DesktopShoppingBagPage NOT loaded");
    }

    @Step
    @Override
    public AddressPage placeOrder() {
        HashMap<String, String> productDetails = (HashMap<String, String>) testExecutionContext.getTestState("productDetails");
        utils.click(getLocator("btnPlaceOrder"));
        return AddressPage.createInstance();
    }

    @Step
    @Override
    public ShoppingBagPage applyPersonalisedCoupon() {
        utils.wait(ExpectedConditions.elementToBeClickable(getLocator("tabApplyCoupon")));
        utils.click(getLocator("tabApplyCoupon"));
        String PersonalisedCoupons = getTestData().get("PersonalisedCoupons");
        utils.sendKeys(getLocator("txtCouponCode"), PersonalisedCoupons);
        utils.click(getLocator("btnApply"));
        utils.wait((ExpectedConditions.invisibilityOfElementLocated(getLocator("btnApply"))));
        return this;
    }
}
