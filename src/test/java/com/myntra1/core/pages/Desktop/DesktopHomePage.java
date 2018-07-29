package com.myntra.core.pages.Desktop;

import com.myntra.core.pages.AddressPage;
import com.myntra.core.pages.HomePage;
import com.myntra.core.pages.ProductDescPage;
import com.myntra.core.pages.WishListPage;
import io.qameta.allure.Step;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class DesktopHomePage extends HomePage {

    @Step
    @Override
    public ProductDescPage searchProductUsingStyleID() {
        String searchItem = getTestData().get("SearchItem");
        utils.sendKeys(getLocator("txtSearchField"), searchItem);
        utils.click(getLocator("btnSearch"));
        return ProductDescPage.createInstance();
    }

    @Step
    @Override
    public boolean isUserLoggedIn() {
        utils.wait(ExpectedConditions.elementToBeClickable(getLocator("icoUser")),5);
        utils.click(getLocator("icoUser"));
        return utils.getText(getLocator("icoProfile")).equalsIgnoreCase(getTestData().get("UserName"));
    }

    @Step
    @Override
    public HomePage reset() {
        utils.wait((ExpectedConditions.invisibilityOfElementLocated(getLocator("btnLogin"))));
        return super.reset();
    }

    @Step
    @Override
    protected HomePage resetAddress() {
        utils.click(getLocator("icoUser"));
        utils.click(getLocator("lnkSavedAddress"), true);
        AddressPage.createInstance()
                   .emptyAddress();
        return this;
    }

    @Step
    @Override
    protected WishListPage navigateToWishlist() {
        DesktopProductDescPage.createInstance()
                              .navigateToWishlist();
        return WishListPage.createInstance();
    }

    @Override
    protected void isLoaded() throws Error {
        LOG.debug(String.format("%s :: Nothing required to be done here", getClass().getSimpleName()));
    }
}
