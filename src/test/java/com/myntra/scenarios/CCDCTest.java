package com.myntra.scenarios;

import org.testng.annotations.Test;

public class CCDCTest extends SupportTest {
    @Test(groups = {"CCDC"},
          description = "TC_ID:2786 - Search, Check for Delivery Option, Add Product, Apply Coupon, View Similar Products, New Address Office, Pay using Credit/Debit Card",
          alwaysRun = true)
    public void checkDeliveryOptionAddProductToCartPlaceOrderApplyCouponAddNewAddressOfficePayUsingCreditDebitCard() {
        loginAndCleanup().searchProductUsingStyleID()
                         .verifyProductDetailsInPDP()
                         .checkDeliveryAvailability()
                         .viewSimilarProducts() //TODO - SimilarProducts feature is not available on stage environment
                         .addProductFromPDPToShoppingBag()
                         .applyCouponAndPlaceOrderWithNewAddress()
                         .payUsingCreditDebit()
                         .verifyOrderNumberFromMyOrders();
    }

    @Test(groups = {"CCDC"},
          description = "TC_ID:2010 - Search, Add to Bag, Change Size & Quantity, Free Gift, View Details, Pay using Credit/Debit Card",
          alwaysRun = true)
    public void addToBagSizeQuantityInCartVerifyFreeGiftViewDetailsPayUsingCreditCard() {
        loginAndCleanup().searchProductUsingStyleID()
                         .addProductFromPDPToShoppingBag()
                         .changeSizeQuantityOfProductInCart()
                         .verifyFreeGift()
                         .applyCouponAndAddNewAddress()
                         .viewPriceDetailsAndProceedToPayment()
                         .payUsingCreditDebit();
    }

    @Test(groups = {"CCDC", "wip-ios", "wip-android"},
          description = "TC_ID:1241 - Search, Percentage Discount, Size Chart, Add to Bag, Move to Wishlist, Move to Bag, Add New Address, Pay using Credit/Debit Card",
          alwaysRun = true)
    public void sizeChartAddToBagMoveToWishlistMoveToBagAddNewAddressPayUsingCCDC() {
        loginAndCleanup().searchProductUsingStyleID()
                         //TODO-Style id is not available for percentage discount item (Test data not available)
                         .viewSizeChartAndSelectSizeAddToBagAndGoToShoppingBag()
                         .moveToWishlistAndAddFromWishlist()
                         .placeOrderAddAddressAndContinueToPayment()
                         .payUsingCreditDebit()
                         .verifyOrderNumberFromMyOrders();
    }

    @Test(groups = {"CCDC", "wip-ios", "wip-android"},
          description = "TC_ID:1737 - Search, Add to Bag, Move to Bag, Add New Address, View details, Pay using Credit/Debit Card",
          alwaysRun = true)
    public void selectSizeAddtoBagBOGOVIewDetailsPayUsingCCDC() {
        loginAndCleanup().searchProductUsingStyleID()
                         .selectSizeAddToBagAndMoveToShoppingBag()
                         .placeOrderAndAddAddress()
                         .viewPriceDetailsAndProceedToPayment()
                         .payUsingCreditDebit()
                         .verifyOrderNumberFromMyOrders();
    }

    @Test(groups = {"CCDC", "wip-ios", "wip-android"},
          description = "TC_ID:2121 - Search, save, add product from wishlist, Change size and quantity, place order,  Add New Address, Pay using Credit/Debit Card",
          alwaysRun = true)
    public void similarProductsSaveAddToBagFromWishlistClickForBestPriceChangeSizeAndQuantityAddAddressPayUsingCCDC() {
        loginAndCleanup().searchProductUsingStyleID()
                         .viewSizeChartAndTapForBestPrice()
                         .saveProductToWishlist()
                         .addProductFromWishlistToShoppingBag()
                         .changeSizeQuantityOfProductInCart()
                         .placeOrderAddAddressAndContinueToPayment()
                         .payUsingCreditDebit()
                         .verifyOrderNumberFromMyOrders();
    }

    @Test(groups = {"CCDC", "wip-ios", "wip-android"},
          description = "TC_ID:2158 - Search, save, add product from wishlist, place order,  Add New Address-Home, Pay using Credit/Debit Card",
          alwaysRun = true)
    public void saveAddToBagMovetoBagFromWishlistPlaceOrderPercentageDiscountAddNewAddressHomePayUsingCCDC() {
        loginAndCleanup().searchProductUsingStyleID()
                         .saveProductToWishlist()
                         .addProductFromWishlistToShoppingBag()
                         .placeOrderAddAddressAndContinueToPayment()
                         .payUsingCreditDebit()
                         .verifyOrderNumberFromMyOrders();
    }

    @Test(groups = {"CCDC", "wip-ios", "wip-android"},
          description = "TC_ID:2158 - Search,view similar products, Add to bag, gift wrap, Apply coupon, add new address, edit address place order,  Add New Address-Home, Pay using Credit/Debit Card",
          alwaysRun = true)
    public void showSimilarProductsAddToBagGiftWrapApplyCouponEditAddressPayUsingSavedCard() {
        loginAndCleanup().searchProductUsingStyleID()
                         .viewSimilarProducts()
                         .addProductFromPDPToShoppingBag()
                         .giftWrapThisProduct()
                         .applyCouponAndAddNewAddressAndEditAddress()
                         .payUsingCreditDebit()
                         .verifyOrderNumberFromMyOrders();
    }
}
