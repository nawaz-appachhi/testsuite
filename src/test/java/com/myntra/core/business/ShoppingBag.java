package com.myntra.core.business;

import com.myntra.core.pages.AddressPage;
import com.myntra.core.pages.ShoppingBagPage;
import com.myntra.utils.test_utils.Assert;
import io.qameta.allure.Step;

public class ShoppingBag extends BusinessFlow {

    public static ShoppingBag getInstance() {
        return (ShoppingBag) getInstance(Of.SHOPPING_BAG);
    }

    @Step
    public Order provideAddressAndPlaceOrderUsingCOD() {
        return Order.getInstance();
    }

    @Step
    public Payment applyCouponAndPlaceOrderWithNewAddress() {
        ShoppingBagPage shoppingBagPage = ShoppingBagPage.createInstance();
        Assert.assertTrue(shoppingBagPage.isProductPriceInShoppingBagEqualToPDP(),
                "Product price in shopping bag page is not similar to product price in PDP page ");
        soft.assertTrue(shoppingBagPage.isProductDiscountInShoppingBagEqualToPDP(),
                "Product Discount in shopping bag page is not similar to product Discount in PDP page ");
        Assert.assertTrue(shoppingBagPage.isProductPresentInBag(), "Product added is NOT found in shopping bag");
        shoppingBagPage.applyPersonalisedCoupon();
        Assert.assertTrue(shoppingBagPage.isCouponApplied(), "Coupon NOT applied successfully");
        shoppingBagPage.placeOrder()
                       .addAddress()
                       .addressContinue();
        return Payment.getInstance();

    }

    @Step
    public ShoppingBag moveToWishlistAndAddFromWishlist() {
        ShoppingBagPage.createInstance()
                       .moveProductToWishlist()
                       .moveProductToBag()
                       .navigateToBag();
        return this;
    }

    @Step
    public Payment applyCouponAndPlaceOrderRemoveAddressAddNewAddress() {
        ShoppingBagPage shoppingBagPage = ShoppingBagPage.createInstance()
                                                         .applyPersonalisedCoupon();
        Assert.assertTrue(shoppingBagPage.isCouponApplied(), "Coupon NOT applied successfully");

        shoppingBagPage.placeOrder()
                       .addAddress()
                       .removeAddress()
                       .addAddress()
                       .addressContinue();
        return Payment.getInstance();
    }

    @Step
    public Payment placeOrderAndEditAddress() {
        return placeOrderAndAddAddress().editAddressAndProceedToPayment();
    }

    @Step
    public Address placeOrderAndAddAddress() {
        ShoppingBagPage shoppingBagPage = ShoppingBagPage.createInstance();
        // TODO - move this assertion to when you add the product into bag
        Assert.assertTrue(shoppingBagPage.isProductPresentInBag(), "Product added is NOT found in shopping bag");
        shoppingBagPage.placeOrder()
                       .addAddress();
        return Address.getInstance();
    }

    @Step
    public Payment placeOrderAddAddressAndContinueToPayment() {
        placeOrderAndAddAddress();
        AddressPage.createInstance()
                   .addressContinue();
        return Payment.getInstance();
    }

    @Step
    public ShoppingBag addMoreFromWishlist() {
        ShoppingBagPage.createInstance()
                       .addMoreFromWishList();
        return this;
    }

    @Step
    public ShoppingBag removeProductFromCart() {
        ShoppingBagPage.createInstance()
                       .removeItemFromCart();
        return this;
    }

    @Step
    public ShoppingBag emptyTheCartAndAddProductsFromWishlist() {
        ShoppingBagPage.createInstance()
                       .removeItemFromCart()
                       .addItemsFromWishlist()
                       .moveProductToBag()
                       .navigateToBag();
        return this;
    }

    @Step
    public ShoppingBag verifyFreeGift() {
        ShoppingBagPage shoppingBagPage = ShoppingBagPage.createInstance();
        Assert.assertTrue(shoppingBagPage.isFreeGiftPresent(), "Free Gift details are not available or incorrect");
        return this;
    }

    @Step
    public ShoppingBag changeSizeQuantityOfProductInCart() {
        ShoppingBagPage shoppingBagPage = ShoppingBagPage.createInstance();
        Assert.assertTrue(shoppingBagPage.isProductPresentInBag(), "Product added is NOT found in shopping bag");
        shoppingBagPage.changeProductSizeInCart()
                       .changeProductQuantityInCart();
        return this;
    }

    @Step
    public Address applyCouponAndAddNewAddress() {
        ShoppingBagPage shoppingBagPage = ShoppingBagPage.createInstance()
                                                         .applyPersonalisedCoupon();
        Assert.assertTrue(shoppingBagPage.isCouponApplied(), "Coupon NOT applied successfully");

        shoppingBagPage.placeOrder()
                       .addAddress();
        return Address.getInstance();
    }

    @Step
    public Payment applyCouponAndAddNewAddressAndEditAddress() {
        ShoppingBagPage shoppingBagPage = ShoppingBagPage.createInstance()
                                                         .applyPersonalisedCoupon();
        Assert.assertTrue(shoppingBagPage.isCouponApplied(), "Coupon NOT applied successfully");

        shoppingBagPage.placeOrder()
                       .addAddress()
                       .editAddress()
                       .addressContinue();
        return Payment.getInstance();
    }

    @Step
    public ShoppingBag giftWrapThisProduct() {
        ShoppingBagPage shoppingBag = ShoppingBagPage.createInstance()
                                                     .addGiftWrap();
        Assert.assertTrue(shoppingBag.isGiftWrapAdded(), "Failed to Giftwrap the item/items");
        return this;
    }
}
