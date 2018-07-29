package com.myntra.core.pages;

import com.myntra.core.pages.Desktop.DesktopWishListPage;
import com.myntra.core.pages.MobileWeb.MobileWebWishListPage;
import com.myntra.core.pages.NativeAndroid.NativeAndroidWishListPage;
import com.myntra.core.pages.NativeIOS.NativeIOSWishListPage;
import com.myntra.core.utils.DynamicEnhancer;
import com.myntra.core.utils.DynamicLogger;
import io.qameta.allure.Step;
import org.apache.commons.lang3.NotImplementedException;
import org.openqa.selenium.support.ui.ExpectedConditions;

public abstract class WishListPage extends Page {

    public static WishListPage createInstance() {
        WishListPage derivedWishListPage;
        switch (CHANNEL) {
            case NATIVE_ANDROID:
                derivedWishListPage = (WishListPage) DynamicEnhancer.create(NativeAndroidWishListPage.class, new DynamicLogger());
                break;

            case NATIVE_IOS:
                derivedWishListPage = (WishListPage) DynamicEnhancer.create(NativeIOSWishListPage.class, new DynamicLogger());
                break;

            case DESKTOP_WEB:
                derivedWishListPage = (WishListPage) DynamicEnhancer.create(DesktopWishListPage.class, new DynamicLogger());
                break;

            case MOBILE_WEB:
                derivedWishListPage = (WishListPage) DynamicEnhancer.create(MobileWebWishListPage.class, new DynamicLogger());
                break;

            default:
                throw new NotImplementedException("Invalid Channel");
        }
        return derivedWishListPage;
    }

    @Override
    public String pageName() {
        return WishListPage.class.getSimpleName();
    }

    @Step
    public WishListPage moveProductToBag() {
        utils.click(getLocator("lnkMoveToBag"), true);
        utils.click(getLocator("btnSelectSize"), true);
        utils.click(getLocator("btnDone"), true);
        return this;
    }

    @Step
    public ShoppingBagPage navigateToBag() {
        utils.click(getLocator("tlbBag"), true);
        return ShoppingBagPage.createInstance();
    }

    @Step
    public HomePage emptyWishlist() {
        removeAllProductsFromWishlist();
        goBack();
        return HomePage.createInstance();
    }

    @Step
    public boolean isProductAddedToWishList() {
        utils.wait(ExpectedConditions.visibilityOfElementLocated(getLocator("lblWishlistPageTitle")));
        return (utils.isElementPresent(getLocator("fraProduct"), 5));
    }

    @Step
    protected WishListPage removeAllProductsFromWishlist() {
        while (!isWishlistEmpty()) {
//            utils.wait(ExpectedConditions.invisibilityOfElementLocated(getLocator("txaRemoveWishlistItemMsg")));
            utils.click(getLocator("icoRemoveProduct"), true);
        }
        return this;
    }

    @Step
    protected boolean isWishlistEmpty() {
        return utils.isElementPresent(getLocator("lblEmptyWishlistMsg"), 2);
    }

}
