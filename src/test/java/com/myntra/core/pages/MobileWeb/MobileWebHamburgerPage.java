package com.myntra.core.pages.MobileWeb;

import com.myntra.core.pages.AddressPage;
import com.myntra.core.pages.HamburgerPage;
import com.myntra.core.pages.OrderConfirmationPage;
import com.myntra.ui.Direction;
import io.qameta.allure.Step;

public class MobileWebHamburgerPage extends HamburgerPage {

    @Step
    @Override
    public AddressPage goToAddress() {
        utils.click(getLocator("mnuAccount"), true);
        utils.scroll(Direction.DOWN, 30);
        utils.click(getLocator("mnuAddress"), true);
        return AddressPage.createInstance();
    }

    @Step
    @Override
    public OrderConfirmationPage goToMyOrdersPage() {
        utils.waitForElementToBeVisible(getLocator("mnuMyOrders"));
        utils.click(getLocator("mnuMyOrders"), true);
        return OrderConfirmationPage.createInstance();
    }
}
