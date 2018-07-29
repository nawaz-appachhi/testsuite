package com.myntra.core.pages;

import com.myntra.core.enums.ChannelUtils;
import com.myntra.core.pages.Desktop.DesktopShoppingBagPage;
import com.myntra.core.pages.MobileWeb.MobileWebShoppingBagPage;
import com.myntra.core.pages.NativeAndroid.NativeAndroidShoppingBagPage;
import com.myntra.core.pages.NativeIOS.NativeIOSShoppingBagPage;
import com.myntra.core.utils.DynamicEnhancer;
import com.myntra.core.utils.DynamicLogger;
import io.qameta.allure.Step;
import org.apache.commons.lang3.NotImplementedException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.HashMap;

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
        while (!isEmptyBagMsgPresent()) {
            WebElement removeItem = utils.findElement(getLocator("btnRemove"));
            if (null != removeItem) {
                removeItemFromCart();
            }
        }
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
        utils.sendKeys(getLocator("txtRecipient"), getTestData().get("RecipientName"));
        utils.sendKeys(getLocator("txtMessage"), getTestData().get("GiftMessage"));
        utils.sendKeys(getLocator("txtSender"), getTestData().get("SenderName"));
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
    public boolean isProductApplicableForBOGO() {
        throw new NotImplementedException(getClass().getSimpleName() + "-" + new Object() {
        }.getClass()
         .getEnclosingMethod()
         .getName() + " - NOT YET IMPLEMENTED");
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
    public ShoppingBagPage selectSizePopUp() {
        throw new NotImplementedException(getClass().getSimpleName() + "-" + new Object() {
        }.getClass()
         .getEnclosingMethod()
         .getName() + " - NOT YET IMPLEMENTED");
    }

    @Step
    public WishListPage moveProductToWishlist() {
        utils.wait(ExpectedConditions.elementToBeClickable(getLocator("btnMoveToWishList")));
        utils.click(getLocator("btnMoveToWishList"), true);
        utils.click(getLocator("lnkWishlist"), true);
        return WishListPage.createInstance();
    }

    @Step
    public WishListPage addItemsFromWishlist() {
        utils.click(getLocator("lnkWishlist"), true);
        return WishListPage.createInstance();
    }

    @Step
    public HashMap<String, String> getProductDetails() {
        HashMap<String, String> productDetails = new HashMap<String, String>();
        if (!isFreeGiftMsgPresent()) {
            productDetails.put("Selling Price", utils.findElement(getLocator("lblSellingPrice"))
                                                     .getText()
                                                     .split(" ")[1]);
            if (utils.isElementPresent(getLocator("lblStrickedPrice"), 2)) {
                productDetails.put("Stricked Price", utils.findElement(getLocator("lblStrickedPrice"))
                                                          .getText());
                productDetails.put("product Discount", utils.findElement(getLocator("lblProductDiscount"))
                                                            .getText());
            }
        } else {
            productDetails.put("Selling Price", utils.findElements(getLocator("lblSellingPrice"))
                                                     .get(1)
                                                     .getText()
                                                     .split(" ")[1]);
            if (utils.isElementPresent(getLocator("lblStrickedPrice"), 2)) {
                productDetails.put("Stricked Price", utils.findElements(getLocator("lblStrickedPrice"))
                                                          .get(1)
                                                          .getText());
                productDetails.put("product Discount", utils.findElements(getLocator("lblProductDiscount"))
                                                            .get(1)
                                                            .getText());
            }
        }
        return productDetails;
    }

    @Step
    public boolean isProductPriceInShoppingBagEqualToPDP() {
        HashMap<String, String> productDetails = (HashMap<String, String>) testExecutionContext.getTestState(
                "productDetails");
        return productDetails.get("Selling Price")
                             .equals(getProductDetails().get("Selling Price")
                                                        .replace(",", ""));
    }

    @Step
    public boolean isProductDiscountInShoppingBagEqualToPDP() {
        HashMap<String, String> productDetails = (HashMap<String, String>) testExecutionContext.getTestState(
                "productDetails");
        return productDetails.get("product Discount")
                             .equals(getProductDetails().get("product Discount"));
    }
}
