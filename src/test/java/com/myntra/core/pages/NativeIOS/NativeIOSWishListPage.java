package com.myntra.core.pages.NativeIOS;

import com.myntra.core.pages.WishListPage;
import io.qameta.allure.Step;

public class NativeIOSWishListPage extends WishListPage {

    @Step
    @Override
    public WishListPage moveProductToBag() {
        utils.click(getLocator("lnkMoveToBag"));
        utils.click(getLocator("btnDone"));
        return this;
    }

    @Step
    @Override
    public WishListPage removeAllProductsFromWishlist() {
        while (!isWishlistEmpty()) {
            utils.waitForElementToBeVisible(getLocator("icoRemoveProduct"));
            utils.click(getLocator("icoRemoveProduct"), true);
        }
        return this;
    }

    @Step
    @Override
    public boolean isWishlistEmpty() {
        return utils.isElementPresent(getLocator("lblEmptyWishlistMsg"), 2);
    }
}
