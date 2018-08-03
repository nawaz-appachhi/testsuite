package com.myntra.core.pages.MobileWeb;

import com.myntra.core.pages.*;
import com.myntra.utils.test_utils.Assert;
import io.qameta.allure.Step;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class MobileWebHomePage extends HomePage {

    @Step
    @Override
    public boolean isUserLoggedIn() {
        utils.wait(ExpectedConditions.invisibilityOfElementLocated(getLocator("btnLogin")));
        utils.click(getLocator("mnuHamburger"), true);
        if (utils.isElementPresent(getLocator("mnuAccount"), 3)) {
            utils.refreshPage();
            return true;
        } else {
            utils.refreshPage();
            return false;
        }
    }

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
        utils.click(getLocator("btnSearch"), true);
        utils.sendKeys(getLocator("txtSearchField"), searchItem + Keys.ENTER);
        utils.wait(ExpectedConditions.invisibilityOfElementLocated(getLocator("txtSearchField")));
        return ProductDescPage.createInstance();
    }

    @Step
    @Override
    public ContactUsPage openContactUsPage() {
        utils.click(getLocator("mnuHamburger"), true);
        scrollTillElementVisible(getLocator("lnkContactUs"));
        utils.waitForElementToBeVisible(getLocator("lnkContactUs"));
        utils.click(getLocator("lnkContactUs"), true);
        return ContactUsPage.createInstance();
    }

    @Step
    @Override
    protected void isLoaded() throws Error {
        Assert.assertTrue(utils.isElementPresent(getLocator("mnuHamburger"), 30),
                String.format("Page - %s - NOT Loaded", getClass().getSimpleName()));
    }

    @Override
    @Step
    public SavedCardsPage navigateToSavedCards() {
        utils.click(getLocator("mnuAccount"), true);
        utils.click(getLocator("lnkSavedCards"), true);
        return SavedCardsPage.createInstance();
    }

    @Override
    @Step
    public UserProfilePage goToProfilePage() {
        openMenu();
        utils.click(getLocator("mnuAccount"), true);
        utils.click(getLocator("icoProfile"), true);
        return UserProfilePage.createInstance();
    }
}
