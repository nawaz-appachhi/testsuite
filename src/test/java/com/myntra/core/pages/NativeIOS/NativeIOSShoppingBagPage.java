package com.myntra.core.pages.NativeIOS;

import com.myntra.core.pages.AddressPage;
import com.myntra.core.pages.HomePage;
import com.myntra.core.pages.ShoppingBagPage;
import com.myntra.core.pages.WishListPage;
import io.qameta.allure.Step;


public class NativeIOSShoppingBagPage extends ShoppingBagPage {

    @Step
    @Override
    public HomePage emptyBag() {
        utils.click(getLocator("btnOK"));
        clickRemoveLink();
        nativeView();
        return HomePage.createInstance();
    }

    @Step
    @Override
    public ShoppingBagPage applyPersonalisedCoupon() {
        utils.findElement(getLocator("tabApplyCoupon"))
             .click();
        utils.findElement(getLocator("btnCancel"))
             .click();
        return this;
    }

    @Step
    @Override
    public AddressPage placeOrder() {
        handleOkPermission();
        utils.swipeDown_m(5);
        return super.placeOrder();
    }

    @Step
    @Override
    public ShoppingBagPage removeItemFromCart() {
        utils.findElement(getLocator("btnRemove"))
             .click();
        utils.findElement(getLocator("btnPopupRemove"))
             .click();
        return this;
    }

    @Step
    @Override
    public ShoppingBagPage addMoreFromWishList() {
        utils.findElement(getLocator("tlbWishlist"))
             .click();
        WishListPage.createInstance()
                    .moveProductToBag();
        WishListPage.createInstance()
                    .navigateToBag();
        return this;
    }

    @Step
    @Override
    protected boolean isEmptyBagMsgPresent() {
        return utils.isElementPresent(getLocator("lblEmptyBagMsg"), 5);
    }

    @Step
    @Override
    protected ShoppingBagPage clickRemoveLink() {
        webView();
        while (!isEmptyBagMsgPresent()) {
            if (utils.isElementPresent(getLocator("fraProduct"), 5)) {
                if (utils.isElementPresent(getLocator("btnRemove"), 2)) {
                    removeItemFromCart();
                }
            } else {
                break;
            }
        }
        return this;
    }

    @Step
    @Override
    public boolean isProductPresentInBag() {
        utils.waitForElementToBeVisible(getLocator("btnOKPermission"));
        handleOkPermission();
        utils.waitForElementToBeVisible(getLocator("btnRemove"));
        return utils.isElementPresent(getLocator("btnRemove"), 5);
    }
}
