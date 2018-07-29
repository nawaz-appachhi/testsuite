package com.myntra.core.pages.NativeAndroid;

import com.myntra.core.pages.ContactUsPage;
import com.myntra.core.pages.HomePage;
import com.myntra.core.pages.ProductDescPage;
import io.qameta.allure.Step;
import org.apache.commons.lang3.NotImplementedException;
import com.myntra.ui.Direction;

public class NativeAndroidHomePage extends HomePage {

    @Step
    @Override
    public ProductDescPage searchProductUsingStyleID() {
        String searchItem = getTestData().get("SearchItem");
        utils.click(getLocator("btnSearch"));
        utils.sendKeys(getLocator("txtSearchField"), searchItem);
        return ProductDescPage.createInstance();
    }

    @Step
    @Override
    public boolean isUserLoggedIn() {
        handleDevicePermissions();
        utils.click(getLocator("mnuHamburger"));
        scrollTillElementVisible(getLocator("mnuAccount"),Direction.DOWN);
        boolean isAccountButtonAvailable= utils.isElementPresent(getLocator("mnuAccount"),5);
        goBack();
        return isAccountButtonAvailable;
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
