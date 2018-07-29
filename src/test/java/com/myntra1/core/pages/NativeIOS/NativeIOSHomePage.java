package com.myntra.core.pages.NativeIOS;

import com.myntra.core.pages.*;
import io.qameta.allure.Step;
import org.apache.commons.lang3.NotImplementedException;

public class NativeIOSHomePage extends HomePage {

    @Step
    @Override
    public boolean isUserLoggedIn() {
        utils.click(getLocator("icoProfile"));
        if (utils.isElementPresent(getLocator("mnuOrders"), 3)) {
            utils.click(getLocator("icoHome"));
            return true;
        } else {
            utils.click(getLocator("icoHome"));
            return false;
        }
    }

    @Step
    @Override
    public ProductDescPage searchProductUsingStyleID() {
        String searchItem = getTestData().get("SearchItem");
        utils.click(getLocator("icoHome"));
        utils.click(getLocator("btnSearch"));
        utils.sendKeys(getLocator("txtSearchField"), searchItem);
//      To  verify keys.enter in other devices
//      utils.sendKeys(getLocator("txtSearchField"), Keys.ENTER.name());
        return ProductDescPage.createInstance();
    }

    @Step
    @Override
    protected HomePage logout() {
        utils.click(getLocator("icoProfile"));
        utils.click(getLocator("btnLogOut"));
        return this;
    }

    @Step
    @Override
    protected HomePage resetAddress() {
        utils.click(getLocator("icoProfile"), true);
        HamburgerPage.createInstance()
                     .goToAddress()
                     .emptyAddress();
        utils.click(getLocator("icoHome"));
        return HomePage.createInstance();
    }

    @Step
    @Override
    protected WishListPage navigateToWishlist() {
        utils.click(getLocator("btnWishlist"), true);
        return WishListPage.createInstance();
    }

    @Step
    @Override
    public ContactUsPage openContactUsPage() {
        throw new NotImplementedException(getClass().getSimpleName() + "-" + new Object() {
        }.getClass()
         .getEnclosingMethod()
         .getName() + " - NOT YET IMPLEMENTED");
    }
}
