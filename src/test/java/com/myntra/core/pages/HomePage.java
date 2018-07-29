package com.myntra.core.pages;

import com.myntra.core.pages.Desktop.DesktopHomePage;
import com.myntra.core.pages.MobileWeb.MobileWebHomePage;
import com.myntra.core.pages.NativeAndroid.NativeAndroidHomePage;
import com.myntra.core.pages.NativeIOS.NativeIOSHomePage;
import com.myntra.core.utils.DynamicEnhancer;
import com.myntra.core.utils.DynamicLogger;
import com.myntra.utils.test_utils.Assert;
import io.qameta.allure.Step;
import org.apache.commons.lang3.NotImplementedException;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.ExpectedConditions;

public abstract class HomePage extends Page {

    public static HomePage createInstance() {
        HomePage derivedHomePage;
        switch (CHANNEL) {
            case NATIVE_ANDROID:
                derivedHomePage = (HomePage) DynamicEnhancer.create(NativeAndroidHomePage.class, new DynamicLogger());
                break;

            case NATIVE_IOS:
                derivedHomePage = (HomePage) DynamicEnhancer.create(NativeIOSHomePage.class, new DynamicLogger());
                break;

            case DESKTOP_WEB:
                derivedHomePage = (HomePage) DynamicEnhancer.create(DesktopHomePage.class, new DynamicLogger());
                break;

            case MOBILE_WEB:
                derivedHomePage = (HomePage) DynamicEnhancer.create(MobileWebHomePage.class, new DynamicLogger());
                break;

            default:
                throw new NotImplementedException("Invalid Channel");
        }
        return derivedHomePage;
    }

    @Override
    public String pageName() {
        return HomePage.class.getSimpleName();
    }

    @Step
    public abstract ProductDescPage searchProductUsingStyleID();

    @Step
    public ProductListPage searchProductUsingName() {
        String searchItem = getTestData().get("SearchItem");
        utils.click(getLocator("btnSearch"), true);
        utils.sendKeys(getLocator("txtSearchField"), searchItem + Keys.ENTER);
        return ProductListPage.createInstance();
    }

    @Step
    public HomePage verifyAutoSuggestion() {
        throw new NotImplementedException(getClass().getSimpleName() + "-" + new Object() {
        }.getClass()
         .getEnclosingMethod()
         .getName() + " - NOT YET IMPLEMENTED");
    }

    @Step
    public abstract boolean isUserLoggedIn();

    @Step
    public HomePage reset() {
        return resetBag().resetAddress()
                         .resetWishlist();
    }

    @Override
    protected void isLoaded() throws Error {
        Assert.assertTrue(utils.isElementPresent(getLocator("mnuHamburger"), 30),
                String.format("Page - %s - NOT Loaded", getClass().getSimpleName()));
    }

    @Step
    protected HomePage logout() {
        throw new NotImplementedException(getClass().getSimpleName() + "-" + new Object() {
        }.getClass()
         .getEnclosingMethod()
         .getName() + " - NOT YET IMPLEMENTED");
    }

    @Step
    protected HomePage resetAddress() {
        openMenu().goToAddress()
                  .emptyAddress();
        return this;
    }

    @Step
    protected HomePage resetWishlist() {
        navigateToWishlist().emptyWishlist();
        return this;
    }

    @Step
    protected WishListPage navigateToWishlist() {
        utils.wait(ExpectedConditions.visibilityOfElementLocated(getLocator("imgMyntraLogo")));
        utils.click(getLocator("btnWishlist"), true);
        return WishListPage.createInstance();
    }

    @Step
    public HamburgerPage openMenu() {
        utils.wait(ExpectedConditions.visibilityOfElementLocated(getLocator("mnuHamburger")));
        utils.click(getLocator("mnuHamburger"), true);
        return HamburgerPage.createInstance();
    }

    @Step
    private ShoppingBagPage navigateToBag() {
        utils.click(getLocator("btnCart"), true);
        return ShoppingBagPage.createInstance();
    }

    @Step
    private HomePage resetBag() {
        navigateToBag().emptyBag();
        return this;
    }

    @Step
    public ContactUsPage openContactUsPage() {
        utils.click(getLocator("lnkContactUs"));
        return ContactUsPage.createInstance();
    }
}
