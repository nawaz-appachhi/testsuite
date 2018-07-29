package com.myntra.core.pages.NativeAndroid;

import com.myntra.core.pages.AddressPage;
import com.myntra.core.pages.HomePage;
import com.myntra.core.pages.ShoppingBagPage;
import com.myntra.core.pages.WishListPage;
import com.myntra.utils.test_utils.Assert;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.util.HashMap;
import java.util.List;

public class NativeAndroidShoppingBagPage extends ShoppingBagPage {

    @Step
    @Override
    public HomePage emptyBag() {
        webView();
        clickRemoveLink();
        nativeView();
        goBack();
        return HomePage.createInstance();
    }

    @Step
    @Override
    public ShoppingBagPage applyPersonalisedCoupon() {
        webView();
        scrollTillElementVisible(getLocator("tabApplyCoupon"));
        utils.click(getLocator("tabApplyCoupon"), true);
        String PersonalisedCoupons = getTestData().get("personalisedCoupons");
        utils.sendKeys(getLocator("txtCouponCode"), PersonalisedCoupons);
        utils.click(getLocator("btnApply"), true);
        utils.wait((ExpectedConditions.invisibilityOfElementLocated(getLocator("btnApply"))));
        return this;
    }

    @Step
    @Override
    public AddressPage placeOrder() {
        handleOkPermission();
        webView();
        return super.placeOrder();
    }

    @Step
    @Override
    public boolean isProductPresentInBag() {
        webView();
        boolean isProductPresent = super.isProductPresentInBag();
        nativeView();
        return isProductPresent;
    }

    @Step
    @Override
    public boolean isCouponApplied() {
        //TODO implemention to be for stage environment
        //utils.isElementPresent(getLocator("lblCouponApplied"), 10)
        return true;
    }

    @Step
    @Override
    public ShoppingBagPage addMoreFromWishList() {
        utils.click(getLocator("tlbWishlist"), true);
        utils.waitForElementToBeVisible(getLocator("btnMoveToBag"));
        utils.click(getLocator("btnMoveToBag"), true);
        //utils.click(getLocator("btnSelectSize"), true);
        ShoppingBagPage.createInstance()
                       .selectSizePopUp();
        goBack();
        return this;
    }

    @Step
    @Override
    public ShoppingBagPage selectSizePopUp() {
        List<WebElement> Sizes = utils.findElements(getLocator("btnSelectSize"));
        for (WebElement e : Sizes) {
            List<WebElement> sizecount = e.findElements(getLocator("chdSelectSize"));
            System.out.println("the size of the singlesize is" + sizecount.size());
            if (sizecount.size() == 0) {
                System.out.println("clicked successfully");
                e.click();
                break;
            }
        }
        return this;
    }

    @Step
    @Override
    public ShoppingBagPage changeProductSizeInCart() {
        webView();
        utils.click(getLocator("btnSize"));
        List<WebElement> sizeOptions = utils.findElements(getLocator("lstSelectSize"));
        boolean isSizeAvailable = false;
        for (WebElement eachSizeOption : sizeOptions) {
            String checkSize = eachSizeOption.getAttribute("class");
            if (checkSize.equals("btn size-btn-group size-btn  ")) {
                eachSizeOption.click();
                isSizeAvailable = true;
                break;
            }
        }
        Assert.assertTrue(isSizeAvailable, "Size NOT available, or Size is NOT clickable");
        return this;
    }

    @Step
    @Override
    public ShoppingBagPage changeProductQuantityInCart() {
        utils.click(getLocator("btnQuantity"));
        List<WebElement> selectSize = utils.findElements(getLocator("lstSelectQty"));
        for (WebElement quantityAvailable : selectSize) {
            String checkQuantity = quantityAvailable.getAttribute("class");
            if (checkQuantity.equals("sel-qty qty-btn-group  ")) {
                quantityAvailable.click();
                break;
                //TODO implemention for Assertion and quantity available condition need to be handle
            }
        }
        return this;
    }

    @Step
    @Override
    public WishListPage moveProductToWishlist() {
        //  utils.wait(ExpectedConditions.elementToBeClickable(getLocator("btnMoveToWishList")));
        webView();
        utils.click(getLocator("btnMoveToWishList"), true);
        nativeView();
        utils.click(getLocator("lnkWishlist"), true);
        return WishListPage.createInstance();
    }

    @Step
    @Override
    public boolean isFreeGiftPresent() {
        webView();
        return super.isFreeGiftPresent();
    }

    @Step
    @Override
    public ShoppingBagPage removeItemFromCart() {
        scrollTillElementVisible(getLocator("btnRemove"));
        super.removeItemFromCart();
        return this;
    }

    @Step
    @Override
    public HashMap<String, String> getProductDetails() {
        webView();
        return super.getProductDetails();
    }
}
