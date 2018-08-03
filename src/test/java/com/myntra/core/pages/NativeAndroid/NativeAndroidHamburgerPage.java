package com.myntra.core.pages.NativeAndroid;

import com.myntra.core.pages.*;
import com.myntra.ui.Direction;
import io.qameta.allure.Step;

public class NativeAndroidHamburgerPage extends HamburgerPage {

    @Step
    @Override
    public AddressPage goToAddress() {
        scrollTillElementVisible(getLocator("mnuAddress"), Direction.DOWN);
        utils.click(getLocator("mnuAddress"), true);
        return AddressPage.createInstance();
    }

    @Step
    @Override
    public OrderConfirmationPage goToMyOrdersPage() {
        utils.click(getLocator("mnuMyOrders"), true);
        return OrderConfirmationPage.createInstance();
    }

    @Step
    @Override
    public SavedCardsPage navigateToSavedCards() {
        scrollTillElementVisible(getLocator("lnkSavedCards"), Direction.DOWN);
        utils.click(getLocator("lnkSavedCards"), true);
        return SavedCardsPage.createInstance();
    }

    @Step
    public UserProfilePage goToEditProfile() {
        utils.click(getLocator("btnAccount"), true);
        scrollTillElementVisible(getLocator("lblEditProfile"), Direction.DOWN);
        utils.click(getLocator("lblEditProfile"));
        return UserProfilePage.createInstance();
    }
}
