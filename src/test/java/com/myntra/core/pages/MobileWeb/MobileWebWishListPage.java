package com.myntra.core.pages.MobileWeb;

import com.myntra.core.pages.WishListPage;
import io.qameta.allure.Step;

public class MobileWebWishListPage extends WishListPage {

    @Step
    private boolean sizeAvailability() {
        boolean sizeAvailability = false;
        if (utils.isElementPresent(getLocator("btnSelectSize"), 3)) {
            sizeAvailability = true;
        }
        return sizeAvailability;
    }
}

