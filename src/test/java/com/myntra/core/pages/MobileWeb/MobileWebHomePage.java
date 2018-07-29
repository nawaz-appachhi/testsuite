package com.myntra.core.pages.MobileWeb;

import com.myntra.core.pages.ContactUsPage;
import com.myntra.core.pages.HomePage;
import com.myntra.core.pages.ProductDescPage;
import io.qameta.allure.Step;
import org.apache.commons.lang3.NotImplementedException;
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
        String searchItem = getTestData().get("SearchItem");
        utils.click(getLocator("btnSearch"), true);
        utils.sendKeys(getLocator("txtSearchField"), searchItem + Keys.ENTER);
        utils.wait(ExpectedConditions.invisibilityOfElementLocated(getLocator("txtSearchField")));
        return ProductDescPage.createInstance();
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
