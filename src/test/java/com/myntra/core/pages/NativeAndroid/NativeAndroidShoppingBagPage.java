package com.myntra.core.pages.NativeAndroid;

import com.myntra.core.pages.AddressPage;
import com.myntra.core.pages.HomePage;
import com.myntra.core.pages.ShoppingBagPage;
import com.myntra.core.pages.WishListPage;
import com.myntra.utils.test_utils.Assert;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.HashMap;
import java.util.List;

public class NativeAndroidShoppingBagPage extends ShoppingBagPage {

    @Step
    @Override
    protected void isLoaded() {
        boolean isElementDisplayed = false;
        webView();
        if (utils.isElementPresent(By.className("retry-service-failure"), 3)) {
            isElementDisplayed = utils.findElement(By.className("retry-service-failure"))
                                      .isDisplayed();
        }
        Assert.assertTrue(!isElementDisplayed, "Failed to load shopping bag page");
    }

    @Step
    @Override
    protected void load() {
        utils.click(By.className("retry-service-failure"), true);
    }

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
        utils.wait(ExpectedConditions.invisibilityOfElementLocated(getLocator("lblGiftWrapSuccess")), 10);
        scrollTillElementVisible(getLocator("tabApplyCoupon"));
        utils.wait(ExpectedConditions.elementToBeClickable(getLocator("tabApplyCoupon")), 5);
        utils.click(getLocator("tabApplyCoupon"), true);
        String personalisedCoupons = getCouponTestData().getCouponCode();
        utils.sendKeys(getLocator("txtCouponCode"), personalisedCoupons);
        utils.wait(ExpectedConditions.invisibilityOfElementLocated(getLocator("txtCouponAppliedSuccessfully")), 5);
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
    public ShoppingBagPage addMoreFromWishList() {
        nativeView();
        utils.click(getLocator("tlbWishlist"), true);
        WishListPage.createInstance()
                    .moveProductToBag();
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
            if ("btn size-btn-group size-btn  ".equals(checkSize)) {
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
        utils.isElementPresent(getLocator("txtCouponCode"), 4);
        if (!isFreeGiftMsgPresent()) {
            utils.click(getLocator("btnQuantity"), true);
            utils.click(getLocator("lstSelectQty"), true);
            utils.isElementPresent(getLocator("txtCouponCode"), 4);
        } else {
            LOG.debug("Cannot change the quantity as product is Free gift ");
        }
        return this;
    }

    @Step
    @Override
    public WishListPage moveProductToWishlist() {
        webView();
        scrollTillElementVisible(getLocator("btnMoveToWishList"));
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
        webView();
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

    @Step
    @Override
    public boolean isProductDiscountInShoppingBagEqualToPDP() {
        //TODO need to implement on product discount
        return true;
    }

    @Step
    @Override
    protected ShoppingBagPage giftWrapThisProduct() {
        scrollTillElementVisible(getLocator("btnGiftWrap"));
        utils.click(getLocator("btnGiftWrap"), true);
        utils.sendKeys(getLocator("txtRecipient"), (String) getTestData().getAdditionalProperties()
                                                                         .get("RecipientName"));
        utils.sendKeys(getLocator("txtMessage"), (String) getTestData().getAdditionalProperties()
                                                                       .get("GiftMessage"));
        utils.sendKeys(getLocator("txtSender"), (String) getTestData().getAdditionalProperties()
                                                                      .get("SenderName"));
        utils.click(getLocator("btnSaveGift"), true);
        utils.wait(ExpectedConditions.invisibilityOfElementLocated(getLocator("btnSaveGift")));
        return this;
    }

    @Step
    @Override
    public ShoppingBagPage addGiftWrap() {
        webView();
        return super.addGiftWrap();
    }

    @Step
    @Override
    public WishListPage navigateToWishlist() {
        nativeView();
        return super.navigateToWishlist();
    }

    @Step
    @Override
    public ShoppingBagPage applyMyntCouponCode() {
        webView();
        scrollTillElementVisible(getLocator("txaMyntAvailable"));
        return super.applyMyntCouponCode();
    }

    @Step
    @Override
    public boolean isErrorMessageDisplayedForInvalidCoupon() {
        webView();
        scrollTillElementVisible(getLocator("tabApplyCoupon"));
        return super.isErrorMessageDisplayedForInvalidCoupon();
    }

    @Step
    @Override
    public boolean isNotApplicableCouponCodeSelected() {
        webView();
        scrollTillElementVisible(getLocator("tabApplyCoupon"));
        return super.isNotApplicableCouponCodeSelected();
    }
}
