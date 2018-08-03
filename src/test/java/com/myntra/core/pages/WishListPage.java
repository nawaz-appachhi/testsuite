package com.myntra.core.pages;

import com.myntra.core.business.WishList;
import com.myntra.core.enums.ChannelUtils;
import com.myntra.core.pages.Desktop.DesktopWishListPage;
import com.myntra.core.pages.MobileWeb.MobileWebWishListPage;
import com.myntra.core.pages.NativeAndroid.NativeAndroidWishListPage;
import com.myntra.core.pages.NativeIOS.NativeIOSWishListPage;
import com.myntra.core.utils.DynamicEnhancer;
import com.myntra.core.utils.DynamicLogger;
import com.myntra.utils.test_utils.Assert;
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
        int productCount = 0;
        int maxProductCount = 10;
        while (!isWishlistEmpty() && (productCount < maxProductCount)) {
            utils.click(getLocator("icoRemoveProduct"), true);
            productCount++;
        }
        Assert.assertTrue((productCount < maxProductCount),
                String.format("Wishlist page is not functional/More number of products found - Made %d attempts to empty wishlist", maxProductCount));
        return this;
    }

    @Step
    protected void handleOkPermission() {
        if (ChannelUtils.isMobileNativePlatform(getChannelUtils())) {
            if (utils.isElementPresent(getLocator("btnOKPermission"), 3)) {
                utils.click(getLocator("btnOKPermission"));
            }
        }
    }

    @Step
    protected boolean isWishlistEmpty() {
        return utils.isElementPresent(getLocator("lblEmptyWishlistMsg"), 2);
    }

    @Step
    public WishList getDetailsInWishlist() {
        throw new NotImplementedException(getClass().getSimpleName() + "-" + new Object() {
        }.getClass()
         .getEnclosingMethod()
         .getName() + " - NOT YET IMPLEMENTED");
    }

    @Step
    public boolean isProductPriceAvailable() {
        throw new NotImplementedException(getClass().getSimpleName() + "-" + new Object() {
        }.getClass()
         .getEnclosingMethod()
         .getName() + " - NOT YET IMPLEMENTED");
    }

    @Step
    public boolean isStrikedPriceAvailable() {
        throw new NotImplementedException(getClass().getSimpleName() + "-" + new Object() {
        }.getClass()
         .getEnclosingMethod()
         .getName() + " - NOT YET IMPLEMENTED");
    }

    @Step
    public boolean isProductDiscountAvailable() {
        throw new NotImplementedException(getClass().getSimpleName() + "-" + new Object() {
        }.getClass()
         .getEnclosingMethod()
         .getName() + " - NOT YET IMPLEMENTED");
    }

    @Step
    public boolean isProductDetailsOfWishlistWithPDPSame() {
        throw new NotImplementedException(getClass().getSimpleName() + "-" + new Object() {
        }.getClass()
         .getEnclosingMethod()
         .getName() + " - NOT YET IMPLEMENTED");
    }

    @Step
    public boolean isProductDetailsOfWishlistWithPLPSame() {
        throw new NotImplementedException(getClass().getSimpleName() + "-" + new Object() {
        }.getClass()
         .getEnclosingMethod()
         .getName() + " - NOT YET IMPLEMENTED");
    }


}
