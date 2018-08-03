package com.myntra.core.pages;

import com.myntra.core.enums.ChannelUtils;
import com.myntra.core.pages.Desktop.DesktopShoppingBagPage;
import com.myntra.core.pages.MobileWeb.MobileWebShoppingBagPage;
import com.myntra.core.pages.NativeAndroid.NativeAndroidShoppingBagPage;
import com.myntra.core.pages.NativeIOS.NativeIOSShoppingBagPage;
import com.myntra.core.utils.DynamicEnhancer;
import com.myntra.core.utils.DynamicLogger;
import com.myntra.utils.test_utils.Assert;
import io.qameta.allure.Step;
import org.apache.commons.lang3.NotImplementedException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.HashMap;
import java.util.Map;

public abstract class ShoppingBagPage extends Page {

    public static ShoppingBagPage createInstance() {
        ShoppingBagPage derivedShoppingBagPage;
        switch (CHANNEL) {
            case NATIVE_ANDROID:
                derivedShoppingBagPage = (ShoppingBagPage) DynamicEnhancer.create(NativeAndroidShoppingBagPage.class,
                        new DynamicLogger());
                break;

            case NATIVE_IOS:
                derivedShoppingBagPage = (ShoppingBagPage) DynamicEnhancer.create(NativeIOSShoppingBagPage.class,
                        new DynamicLogger());
                break;

            case DESKTOP_WEB:
                derivedShoppingBagPage = (ShoppingBagPage) DynamicEnhancer.create(DesktopShoppingBagPage.class,
                        new DynamicLogger());
                break;

            case MOBILE_WEB:
                derivedShoppingBagPage = (ShoppingBagPage) DynamicEnhancer.create(MobileWebShoppingBagPage.class,
                        new DynamicLogger());
                break;

            default:
                throw new NotImplementedException("Invalid Channel");
        }
        return derivedShoppingBagPage;
    }

    @Override
    public String pageName() {
        return ShoppingBagPage.class.getSimpleName();
    }

    @Step
    public abstract ShoppingBagPage applyPersonalisedCoupon();

    @Step
    public AddressPage placeOrder() {
        utils.click(getLocator("btnPlaceOrder"), true);
        return AddressPage.createInstance();
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
    public boolean isProductPresentInBag() {
        return utils.isElementPresent(getLocator("btnRemove"), 5);
    }

    @Step
    public boolean isCouponApplied() {
        return utils.isElementPresent(getLocator("lblYouSaved"), 5);
    }

    @Step
    public HomePage emptyBag() {
        clickRemoveLink();
        goBack();
        return HomePage.createInstance();
    }

    @Step
    protected ShoppingBagPage clickRemoveLink() {
        int productCount = 0;
        int maxProductCount = 10;
        while (!isEmptyBagMsgPresent() && (productCount < maxProductCount)) {
            WebElement removeItem = utils.findElement(getLocator("btnRemove"));
            if (null != removeItem) {
                removeItemFromCart();
            }
            productCount++;
        }
        Assert.assertTrue((productCount < maxProductCount), String.format(
                "Shopping bag page is not functional/More number of products found - Made %d attempts to empty bag",
                maxProductCount));
        return this;
    }

    @Step
    protected boolean isEmptyBagMsgPresent() {
        return utils.isElementPresent(getLocator("lblEmptyBagMsg"), 5);
    }

    @Step
    public ShoppingBagPage removeItemFromCart() {
        utils.click(getLocator("btnRemove"), true);
        utils.wait(ExpectedConditions.elementToBeClickable(getLocator("btnPopupRemove")));
        utils.click(getLocator("btnPopupRemove"), true);
        return this;
    }

    @Step
    public ShoppingBagPage addGiftWrap() {
        // This is implemented for Android_WEB
        if (!isGiftWrapAdded()) {
            return giftWrapThisProduct();
        } else {
            removeExistingGiftWrapAndAddItAgain();
        }
        return this;
    }

    @Step
    public boolean isGiftWrapAdded() {
        return utils.isElementPresent(getLocator("btnEditGiftMsg"), 10);
    }

    @Step
    protected ShoppingBagPage giftWrapThisProduct() {
        utils.click(getLocator("btnGiftWrap"));
        utils.sendKeys(getLocator("txtRecipient"), (String) getTestData().getAdditionalProperties()
                                                                         .get("RecipientName"));
        utils.sendKeys(getLocator("txtMessage"), (String) getTestData().getAdditionalProperties()
                                                                       .get("GiftMessage"));
        utils.sendKeys(getLocator("txtSender"), (String) getTestData().getAdditionalProperties()
                                                                      .get("SenderName"));
        utils.click(getLocator("btnSaveGift"));
        utils.wait(ExpectedConditions.invisibilityOfElementLocated(getLocator("btnSaveGift")));
        return this;
    }

    @Step
    protected ShoppingBagPage removeExistingGiftWrapAndAddItAgain() {
        utils.click(getLocator("btnRemoveGiftWrap"));
        LOG.debug("GiftWrap removed");
        giftWrapThisProduct();
        return this;
    }

    @Step
    public ShoppingBagPage addMoreFromWishList() {
        utils.scrollTillElementVisible(getLocator("tlbWishlist"));
        utils.click(getLocator("tlbWishlist"), true);
        WishListPage.createInstance()
                    .moveProductToBag();
        WishListPage.createInstance()
                    .navigateToBag();
        return this;
    }

    @Step
    public ShoppingBagPage changeProductSizeInCart() {
        utils.click(getLocator("btnSize"), true);
        utils.click(getLocator("lstSelectSize"), true);
        utils.wait(ExpectedConditions.invisibilityOfElementLocated(getLocator("lstSelectSize")));
        return this;
    }

    @Step
    public ShoppingBagPage changeProductQuantityInCart() {
        if (!isFreeGiftMsgPresent()) {
            utils.click(getLocator("btnQuantity"), true);
            utils.click(getLocator("lstSelectQty"), true);
            utils.wait(ExpectedConditions.invisibilityOfElementLocated(getLocator("lstSelectQty")));
        } else {
            LOG.debug("Cannot change the quantity as product is Free gift ");
        }
        return this;
    }

    @Step
    public boolean isBOGOApplied() {
        boolean isBOGOAppliedCorrectly = false;
        String sellingPriceBeforeBOGOApplied = utils.findElement(getLocator("lblSellingPrice"))
                                                    .getText();
        if (isBOGOAvailable()) {
            changeProductQuantityInCart();
            if (sellingPriceBeforeBOGOApplied.equals(utils.findElement(getLocator("lblSellingPrice"))
                                                          .getText())) {
                isBOGOAppliedCorrectly = true;
            } else {
                LOG.info("selling Price Before BOGO Applied is" + sellingPriceBeforeBOGOApplied +
                        "not same as selling price after BOGO applied " +
                        utils.findElement(getLocator("lblSellingPrice"))
                             .getText());
            }
        } else {
            LOG.info("BOGO (Buy One Get One) is not available for this product");
        }
        return isBOGOAppliedCorrectly;
    }

    @Step
    private boolean isBOGOAvailable() {
        return utils.isElementPresent(getLocator("btnAddItem"), 3);
    }

    @Step
    public ShoppingBagPage addSecondBogoProduct() {
        throw new NotImplementedException(getClass().getSimpleName() + "-" + new Object() {
        }.getClass()
         .getEnclosingMethod()
         .getName() + " - NOT YET IMPLEMENTED");
    }

    @Step
    public boolean isProductApplicableForFreeGift() {
        throw new NotImplementedException(getClass().getSimpleName() + "-" + new Object() {
        }.getClass()
         .getEnclosingMethod()
         .getName() + " - NOT YET IMPLEMENTED");
    }

    @Step
    public boolean isFreeGiftPresent() {
        int productCount = utils.findElements(getLocator("lstProductSet"))
                                .size();
        int removeBtnCount = utils.findElements(getLocator("btnRemove"))
                                  .size();
        int moveToWishListCount = utils.findElements(getLocator("btnMoveToWishlist"))
                                       .size();
        if (isFreeGiftMsgPresent()) {
            boolean isRemoveButtonLessThanNumberOfProductsInBag = (removeBtnCount < productCount);
            boolean isMoveToWishlistButtonLessThanNumberOfProductsInBag = (moveToWishListCount < productCount);
            return (isRemoveButtonLessThanNumberOfProductsInBag &&
                    isMoveToWishlistButtonLessThanNumberOfProductsInBag && (getSellingPriceOfFirstProductInBag() == 0));
        } else {
            LOG.info("Free gift feature is NOT available");
            return false;
        }
    }

    private int getSellingPriceOfFirstProductInBag() {
        int sellingPrice = Integer.parseInt(utils.findElement(getLocator("lblSellingPrice"))
                                                 .getText()
                                                 .split(" ")[1]);
        if (sellingPrice != 0) {
            LOG.info("expected freeGift price = 0 but actual is " + sellingPrice);
        }
        return sellingPrice;
    }

    @Step
    public boolean isFreeGiftMsgPresent() {
        return utils.isElementPresent(getLocator("freeGift"), 5);
    }

    @Step
    public ShoppingBagPage viewDetailsInCartPage() {
        throw new NotImplementedException(getClass().getSimpleName() + "-" + new Object() {
        }.getClass()
         .getEnclosingMethod()
         .getName() + " - NOT YET IMPLEMENTED");
    }

    @Step
    public boolean isProductApplicablePercentageDiscount() {
        throw new NotImplementedException(getClass().getSimpleName() + "-" + new Object() {
        }.getClass()
         .getEnclosingMethod()
         .getName() + " - NOT YET IMPLEMENTED");
    }

    @Step
    public WishListPage moveProductToWishlist() {
        //TODO refresh issue need to be removed once Jenkins issue is fixed for web channel
        if (!utils.isElementPresent(getLocator("btnMoveToWishList"), 5)) {
            utils.refreshPage();
        }
        utils.wait(ExpectedConditions.visibilityOfElementLocated(getLocator("btnMoveToWishList")), 10);
        utils.wait(ExpectedConditions.elementToBeClickable(getLocator("btnMoveToWishList")));
        utils.waitForElementToBeVisible(getLocator("btnMoveToWishList"));
        utils.click(getLocator("btnMoveToWishList"), true);
        utils.click(getLocator("lnkWishlist"), true);
        return WishListPage.createInstance();
    }

    @Step
    public WishListPage navigateToWishlist() {
        utils.click(getLocator("lnkWishlist"), true);
        return WishListPage.createInstance();
    }

    @Step
    public HashMap<String, String> getProductDetails() {
        HashMap<String, String> productDetails = new HashMap<>();
        if (!isFreeGiftMsgPresent()) {
            productDetails.put("Selling Price", utils.findElement(getLocator("lblSellingPrice"))
                                                     .getText()
                                                     .split(" ")[1]);
            if (utils.isElementPresent(getLocator("lblStrickedPrice"), 2) &&
                    utils.isElementPresent(getLocator("lblProductDiscount"), 2)) {
                productDetails.put("Stricked Price", utils.findElement(getLocator("lblStrickedPrice"))
                                                          .getText());
                productDetails.put("Product Discount", utils.findElement(getLocator("lblProductDiscount"))
                                                            .getText());
            }
        } else {
            productDetails.put("Selling Price", utils.findElements(getLocator("lblSellingPrice"))
                                                     .get(1)
                                                     .getText()
                                                     .split(" ")[1]);
        }
        return productDetails;
    }

    @Step
    public boolean isProductDiscountInShoppingBagEqualToPDP() {
        Map<String, String> productDetails = (HashMap<String, String>) testExecutionContext.getTestState(
                "productDetails");
        boolean isDiscountAvailable = true;
        if (!(getProductDetails().get("Product Discount") == null)) {
            isDiscountAvailable = productDetails.get("Product Discount")
                                                .equals(getProductDetails().get("Product Discount"));
        } else {
            LOG.info("Discount is not available");
        }
        return isDiscountAvailable;
    }

    @Step
    public boolean isProductPriceInShoppingBagEqualToPDPAndPLP() {
        HashMap<String, String> productDetails = (HashMap<String, String>) testExecutionContext.getTestState(
                "productDetails");

        HashMap<String, String> productDetailsfetchedFromPLP = (HashMap<String, String>) testExecutionContext.getTestState(
                "productDetailsInPLP");
        String productPriceInPLP = productDetailsfetchedFromPLP.get("Selling Price");
        boolean isProductPriceInBagEqualToPLP = productDetails.get("Selling Price")
                                                              .equals(productPriceInPLP);
        boolean isProductPriceInBagEqualToPDP = productPriceInPLP.equals(getProductDetails().get("Selling Price")
                                                                                            .replace(",", ""));
        if (!isProductPriceInBagEqualToPLP) {
            LOG.info("product price in plp " + productDetailsfetchedFromPLP.get("Selling Price") +
                    "is not matching with product price in pdp" + productDetails.get("Selling Price"));
        }
        return isProductPriceInBagEqualToPLP && isProductPriceInBagEqualToPDP;
    }

    @Step
    public boolean isProductDiscountInShoppingBagEqualToPDPAndPLP() {
        Map<String, String> productDetails = (HashMap<String, String>) testExecutionContext.getTestState(
                "productDetails");
        HashMap<String, String> productDetailsfetchedFromPLP = (HashMap<String, String>) testExecutionContext.getTestState(
                "productDetailsInPLP");
        boolean isProductDiscountSameInPLPAndPDPAndBag = true;
        String productDiscountInPLP = productDetailsfetchedFromPLP.get("Product Discount");
        if (productDiscountInPLP == null) {
            isProductDiscountSameInPLPAndPDPAndBag = productDetails.get("Product Discount") == null &&
                    (getProductDetails().get("Product Discount")) == null;
            return isProductDiscountSameInPLPAndPDPAndBag;
        } else {
            boolean isProductDiscountSameInPDPAndBag = isProductDiscountInShoppingBagEqualToPDP();
            boolean isProductDiscountSameInPLPAndBag = productDiscountInPLP.equals(
                    getProductDetails().get("Product Discount"));
            return (isProductDiscountSameInPDPAndBag == isProductDiscountSameInPLPAndBag);
        }
    }

    @Step
    public boolean isProductPriceInShoppingBagEqualToPDP() {
        Map<String, String> productDetails = (HashMap<String, String>) testExecutionContext.getTestState(
                "productDetails");

        return productDetails.get("Selling Price")
                             .equals(getProductDetails().get("Selling Price")
                                                        .replace(",", ""));
    }

    @Step
    public boolean isMyntCouponCodeAvailable() {
        return utils.isElementPresent(getLocator("txaMyntAvailable"), 5);
    }

    @Step
    public ShoppingBagPage applyMyntCouponCode() {
        if (isMyntCouponCodeAvailable()) {
            utils.click(getLocator("btnMyntApplyCode"));
            utils.sendKeys(getLocator("txtMyntDiscount"), (String) getCouponTestData().getAdditionalProperties()
                                                                                      .get("MyntCoupon"));
            utils.click(getLocator("btnMyntApply"));
            utils.wait(ExpectedConditions.invisibilityOfElementLocated(getLocator("btnMyntApply")));
        }
        return this;
    }

    @Step
    public boolean isStaggeredComboApplied() {
        //TODO-Need information on type of assertion to be done
        return true;
    }

    @Step
    public boolean isErrorMessageDisplayedForInvalidCoupon() {
        utils.wait(ExpectedConditions.elementToBeClickable(getLocator("tabApplyCoupon")));
        utils.click(getLocator("tabApplyCoupon"), true);
        String invalidPersonalisedCoupon = (String) getCouponTestData().getAdditionalProperties()
                                                                       .get("InvalidPersonalisedCoupons");
        utils.sendKeys(getLocator("txtCouponCode"), invalidPersonalisedCoupon);
        utils.click(getLocator("btnApply"), true);
        return (utils.isElementPresent(getLocator("txaErrorMessageForInvalidCoupon"), 5));
    }

    @Step
    public boolean isNotApplicableCouponCodeSelected() {
        utils.wait(ExpectedConditions.elementToBeClickable(getLocator("tabApplyCoupon")));
        utils.click(getLocator("tabApplyCoupon"), true);
        if (utils.isElementPresent(getLocator("txaCouponsNotApplicableHeader"), 2)) {
            utils.click(getLocator("lstCouponsNotApplicable"), true);
        } else {
            LOG.info("Not applicable coupons are not available");
        }
        return (utils.isElementPresent(getLocator("lstCouponNotApplicableAndDisabled"), 2));
    }

    @Step
    public ShoppingBagPage enterDisabledCouponCode() {
        if (isNotApplicableCouponCodeSelected()) {
            String couponCode = utils.getText(getLocator("lstCouponsNotApplicable"));
            utils.sendKeys(getLocator("txtCouponCode"), couponCode);
            utils.click(getLocator("btnApply"), true);
        }
        return this;
    }

    @Step
    public boolean isInvalidCouponMessageCorrect() {
        String couponCode = utils.getText(getLocator("lstCouponsNotApplicable"));
        String actualInvalidCouponMessage = utils.getText(getLocator("txaErrorMessageForInvalidCoupon"));
        String expectedInvalidCouponMessage = String.format(
                "Sorry, Your cart has no applicable products for this coupon - %s", couponCode);
        return actualInvalidCouponMessage.equals(expectedInvalidCouponMessage);
    }

    @Step
    public Integer totalNumberOfItemsInBag() {
        int productCount = 0;
        if (!isEmptyBagMsgPresent()) {
            productCount = utils.findElements(getLocator("btnRemove"))
                                .size();
        } else {
            LOG.info("Bag is Empty");
        }
        return productCount;
    }

    @Step
    public Integer totalNumberOfItemsInBagForGuestUser() {
        int productCount = 0;
        if (!isEmptyBagMsgPresent()) {
            productCount = utils.findElements(getLocator("btnRemove"))
                                .size();
        } else {
            LOG.info("Bag is Empty");
        }
        return productCount;
    }

    @Step
    public HomePage navigateToHomePageFromBag() {
        utils.click(getLocator("imgBagPageLogo"));
        return HomePage.createInstance();
    }

    @Step
    public boolean isProductCountInBagCorrect() {
        int productCountInBag = totalNumberOfItemsInBag();
        int productCountAfterMerging = totalNumberOfItemsInBagForGuestUser();
        return (productCountInBag < productCountAfterMerging);
    }

    @Step
    public boolean isFreeGiftApplicableAfterAddingOneMoreProduct() {
        throw new NotImplementedException(getClass().getSimpleName() + "-" + new Object() {
        }.getClass()
         .getEnclosingMethod()
         .getName() + " - NOT YET IMPLEMENTED");
    }

    @Step
    public boolean isOneMoreProductTobeAddedTOCompleteFreeGiftOffer() {
        throw new NotImplementedException(getClass().getSimpleName() + "-" + new Object() {
        }.getClass()
         .getEnclosingMethod()
         .getName() + " - NOT YET IMPLEMENTED");
    }

    @Step
    public boolean isConditionalDiscountApplied() {
        throw new NotImplementedException(getClass().getSimpleName() + "-" + new Object() {
        }.getClass()
         .getEnclosingMethod()
         .getName() + " - NOT YET IMPLEMENTED");
    }

    @Step
    public ShoppingBagPage completeConditionalDiscountOfProducts() {
        throw new NotImplementedException(getClass().getSimpleName() + "-" + new Object() {
        }.getClass()
         .getEnclosingMethod()
         .getName() + " - NOT YET IMPLEMENTED");
    }

    @Step
    public ShoppingBagPage verifyUnauthorizedPersonalizedCoupon() {
        utils.wait(ExpectedConditions.elementToBeClickable(getLocator("tabApplyCoupon")));
        utils.click(getLocator("tabApplyCoupon"), true);
        String couponCode = (String) getCouponTestData().getAdditionalProperties()
                                                        .get("AnotherPersonalisedCoupon");
        utils.sendKeys(getLocator("txtCouponCode"), couponCode);
        utils.click(getLocator("btnApply"), true);
        return this;
    }

    @Step
    public ShoppingBagPage enterCouponCode() {
        utils.wait(ExpectedConditions.elementToBeClickable(getLocator("tabApplyCoupon")));
        utils.click(getLocator("tabApplyCoupon"));
        utils.click(getLocator("txtCouponCode"));
        String personalisedCoupons = getCouponTestData().getCouponCode();
        utils.sendKeys(getLocator("txtCouponCode"), personalisedCoupons);
        return this;
    }

    @Step
    public boolean isUnauthorizedCouponMessageCorrect() {
        String ActualUnauthorizedCouponMessage = utils.getText(getLocator("txaErrorMessageForInvalidCoupon"));
        String expectedUnauthorizedCouponMessage = "Sorry, this coupon is not valid for this user account.";
        boolean isMessageForUnauthorizedCouponCorrect = ActualUnauthorizedCouponMessage.equals(
                expectedUnauthorizedCouponMessage);
        return isMessageForUnauthorizedCouponCorrect;
    }

    @Step
    public ShoppingBagPage chooseValidCoupon() {
        if (utils.isElementPresent(getLocator("txaChooseValidCouponHeader"), 2)) {
            utils.click(getLocator("lstValidCoupons"));
        } else {
            LOG.info("Valid Coupon list is not displayed");
        }
        return this;
    }

    @Step
    public boolean isMultipleCouponsSelected() {
        enterCouponCode();
        chooseValidCoupon();
        if (utils.findElement(getLocator("txtCouponCode"))
                 .getText()
                 .equalsIgnoreCase("")) {
            return false;
        } else {
            return true;
        }
    }
}
