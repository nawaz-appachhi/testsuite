package com.myntra.core.pages.NativeAndroid;

import com.myntra.core.pages.*;
import com.myntra.ui.Direction;
import io.qameta.allure.Step;

public class NativeAndroidHomePage extends HomePage {

    @Step
    @Override
    public ProductDescPage searchProductUsingStyleID() {
        String searchItem = (String) getTestData().getAdditionalProperties()
                                                  .get("SearchItem");
        return searchProductUsingStyleID(searchItem);
    }

    @Step
    @Override
    public ProductDescPage searchProductUsingStyleID(String searchItem) {
        utils.click(getLocator("btnSearch"));
        utils.sendKeys(getLocator("txtSearchField"), searchItem);
        return ProductDescPage.createInstance();
    }

    @Step
    @Override
    public boolean isUserLoggedIn() {
        handleDevicePermissions();
        utils.click(getLocator("mnuHamburger"));
        scrollTillElementVisible(getLocator("mnuAccount"), Direction.DOWN);
        boolean isAccountButtonAvailable = utils.isElementPresent(getLocator("mnuAccount"), 5);
        goBack();
        return isAccountButtonAvailable;
    }

    @Step
    @Override
    public ContactUsPage openContactUsPage() {
        openMenu();
        scrollTillElementVisible(getLocator("lnkContactUs"), Direction.DOWN);
        utils.click(getLocator("lnkContactUs"));
        return ContactUsPage.createInstance();
    }

    @Step
    @Override
    public ProductListPage searchProductUsingName() {
        String searchItem = (String) getTestData().getAdditionalProperties()
                                                  .get("SearchItem");
        utils.click(getLocator("btnSearch"), true);
        utils.findElement(getLocator("txtSearchField"))
             .sendKeys(searchItem + "\\n");
        return ProductListPage.createInstance();
    }

    @Step
    @Override
    public SavedCardsPage navigateToSavedCards() {
        openMenu();
        NativeAndroidHamburgerPage.createInstance()
                                  .navigateToSavedCards();
        return SavedCardsPage.createInstance();
    }

}
