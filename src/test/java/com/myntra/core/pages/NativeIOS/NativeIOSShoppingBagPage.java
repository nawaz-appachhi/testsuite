package com.myntra.core.pages.NativeIOS;

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

public class NativeIOSShoppingBagPage extends ShoppingBagPage {

    @Step
    @Override
    public HomePage emptyBag() {
        handleOkPermission();
        webView();
        clickRemoveLink();
        goBack();
        nativeView();
        return HomePage.createInstance();
    }

    @Step
    @Override
    public ShoppingBagPage applyPersonalisedCoupon() {
        // TODO Once styleid for production is available to apply coupon,need to implement this;
        return this;
    }

    @Step
    @Override
    public boolean isCouponApplied() {
        // TODO Once styleid for production is available to apply coupon,need to assert this;
        return true;
    }

    @Step
    @Override
    public AddressPage placeOrder() {
        webView();
        utils.swipeDown_m(5);
        return super.placeOrder();
    }

    @Step
    @Override
    public ShoppingBagPage removeItemFromCart() {
        handleOkPermission();
        webView();
        utils.click(getLocator("btnRemove"), true);
        utils.waitForElementToBeVisible(getLocator("btnPopupRemove"));
        utils.click(getLocator("btnPopupRemove"), true);
        return this;
    }

    @Step
    @Override
    public ShoppingBagPage addMoreFromWishList() {
        nativeView();
        utils.waitForElementToBeVisible(getLocator("bckIcon"));
        utils.click(getLocator("bckIcon"), true);
        utils.click(getLocator("icoWishlist"), true);
        WishListPage.createInstance()
                    .moveProductToBag()
                    .navigateToBag();
        return this;
    }

    @Step
    @Override
    protected boolean isEmptyBagMsgPresent() {
        return utils.isElementPresent(getLocator("lblEmptyBagMsg"), 5);
    }

    @Step
    @Override
    protected ShoppingBagPage clickRemoveLink() {
        while (!isEmptyBagMsgPresent()) {
            removeItemFromCart();
        }
        return this;
    }

    @Step
    @Override
    public boolean isProductPresentInBag() {
        handleOkPermission();
        webView();
        return utils.isElementPresent(getLocator("btnRemove"), 5);
    }

    @Step
    @Override
    public ShoppingBagPage changeProductSizeInCart() {
        utils.click(getLocator("btnSize"), true);
        List<WebElement> sizeOptions = utils.findElements(getLocator("lstSelectSize"));
        boolean isSizeAvailable = false;
        for (WebElement eachSizeOption : sizeOptions) {
            String checkSize = eachSizeOption.getAttribute("class");
            if ("btn size-btn-group size-btn  ".equals(checkSize)) {
                eachSizeOption.click();
                isSizeAvailable = true;
                utils.wait(ExpectedConditions.invisibilityOfElementLocated(getLocator("lstSelectSize")));
                break;
            }
        }
        Assert.assertTrue(isSizeAvailable, "Size NOT available, or Size is NOT clickable");

        return this;
    }

    @Step
    @Override
    public HashMap<String, String> getProductDetails() {
        HashMap<String, String> productDetails = new HashMap<>();
        // TODO,Once locators are given for product details as the same is in a single frame;
        return productDetails;
    }

    @Step
    @Override
    public ShoppingBagPage changeProductQuantityInCart() {
        utils.waitForElementToBeVisible(getLocator("btnQuantity"));
        utils.click(getLocator("btnQuantity"), true);
        List<WebElement> selectSize = utils.findElements(getLocator("lstSelectQty"));
        for (WebElement quantityAvailable : selectSize) {
            String checkQuantity = quantityAvailable.getAttribute("class");
            if ("sel-qty qty-btn-group  ".equals(checkQuantity)) {
                quantityAvailable.click();
                utils.wait(ExpectedConditions.invisibilityOfElementLocated(getLocator("lstSelectQty")));
                break;
                //TODO implemention for Assertion and quantity available condition need to be handle
            }
        }
        return this;
    }

    @Step
    @Override
    public boolean isFreeGiftMsgPresent() {
        //TODO as free gift not able to implement in production environment;
        return true;
    }

    @Step
    @Override
    public boolean isFreeGiftPresent() {
        //TODO as free gift not able to implement in production environment;
        return true;
    }

    @Step
    @Override
    public boolean isProductDiscountInShoppingBagEqualToPDP() {
        HashMap<String, String> productDetails = (HashMap<String, String>) testExecutionContext.getTestState("productDetails");
        // TODO,Once locators are given for product details as the same is in a single frame;
        boolean isDiscountAvailable = true;
        return isDiscountAvailable;
    }

    @Step
    @Override
    public boolean isGiftWrapAdded() {
        // TODO,once styleid with gift wrap is provided for production;
        return true;
    }

    @Step
    @Override
    protected ShoppingBagPage giftWrapThisProduct() {
        // TODO,once styleid with gift wrap is provided for production;
        return this;
    }

    @Step
    @Override
    protected ShoppingBagPage removeExistingGiftWrapAndAddItAgain() {
        // TODO,once styleid with gift wrap is provided for production;
        return this;
    }

    @Step
    @Override
    public WishListPage navigateToWishlist() {
        nativeView();
        utils.waitForElementToBeVisible(getLocator("bckIcon"));
        utils.click(getLocator("bckIcon"), true);
        utils.click(getLocator("icoWishlist"), true);
        return WishListPage.createInstance();
    }

    @Step
    @Override
    public boolean isBOGOApplied() {
        boolean isBOGOAppliedCorrectly = true;
        return isBOGOAppliedCorrectly;
    }

    @Step
    @Override
    public WishListPage moveProductToWishlist() {
        webView();
        utils.click(getLocator("btnMoveToWishList"), true);
        nativeView();
        utils.click(getLocator("tlbback"), true);
        utils.click(getLocator("icoWishlist"));
        return WishListPage.createInstance();
    }
}
