package com.myntra1.scenarios;

import org.testng.annotations.Test;

@Test(groups = {"ManiTest"},
description = "TC_ID:1622 - Select Size, Apply Coupon, BOGO, Remove Address, Pay using Manual GC+Online",
alwaysRun = true)
public class OnlineTest extends SupportTest {
    @Test(groups = {"ManiTest"},
          description = "TC_ID:1622 - Select Size, Apply Coupon, BOGO, Remove Address, Pay using Manual GC+Online",
          alwaysRun = true)
    public void addProductMoveToBagApplyCouponPlaceOrderRemoveAddressPayUsingOnline() {
        loginAndCleanup().searchProductUsingStyleID()
                         .saveProductToWishlist()
                         .addProductFromPDPToShoppingBag()
                         .applyCouponAndPlaceOrderRemoveAddressAddNewAddress()
                         .payUsingNetBanking();
    }

    @Test(groups = {"Online"},
          description = "TC_ID:3155 - Save To Wishlist, Empty Wishlist, Gift Wrap, EditAddress, Pay using LP+Online",
          alwaysRun = true)
    public void saveToWishlistEmptyWishlistGiftWrapEditAddressPayUsingOnline() {
        loginAndCleanup().searchProductUsingStyleID()
                         .saveProductToWishlist()
                         .addProductFromWishlistToShoppingBag()
                         .placeOrderAndEditAddress()
                         .payUsingNetBanking();
    }

    // @Test(groups = {"Online","wip-android"},
    //       description = "TC_ID:2792 - Search, Show More Products, Similar Product, Select Size, Place Order Conditional Discount, Add Address_Home, Pay Using Net Banking",
    //       alwaysRun = true)
    // public void searchaddProductToCartPlaceOrderConditionalDiscountAddNewAddressHomePayUsingNetBanking() {
    //     loginAndCleanup().searchProductUsingStyleID()
    //                      .verifyProductDetailsInPDP()
    //                      .addProductFromPDPToShoppingBag()
    //                      .applyCouponAndPlaceOrderWithNewAddress()
    //                      .payUsingNetBanking();
    // }

    // @Test(groups = {"Online","wip-android"},
    //       description = "TC_ID:2967 - Save, Move to Bag, Apply Coupon, Free Gift, Add New Address_Office, Pay using Net Banking",
    //       alwaysRun = true)
    // public void saveToWishlistAfterSearchAddFreeGiftApplycouponPlaceOrderUsingNetbanking() {
    //     loginAndCleanup().searchProductUsingStyleID()
    //                      .verifyProductDetailsInPDP()
    //                      .saveProductToWishlist()
    //                      .addProductFromWishlistToShoppingBag()
    //                      .applyCouponAndPlaceOrderWithNewAddress()
    //                      .payUsingNetBanking();
    // }

    // @Test(groups = {"Online","wip-android"},
    //       description = "TC_ID:68 - Save Product, Empty Wishlist, Gift Wrap, Apply Coupon, Pay using Wallet",
    //       alwaysRun = true)
    // public void saveEmptyWishlistAddProductToBagGiftWrapApplyCouponPayUsingWallet() {
    //     loginAndCleanup().searchProductUsingStyleID()
    //                      .saveProductToWishlist()
    //                      .addProductFromPDPToShoppingBag()
    //                      .giftWrapThisProduct()
    //                      .applyCouponAndAddNewAddress()
    //                      .viewPriceDetailsAndProceedToPayment()
    //                      .payUsingWallet();
    // }

    // @Test(groups = {"Online","wip-android"},
    //       description = "TC_ID:1065 - Save Product, Empty Wishlist, Gift Wrap, Apply Coupon, Pay using wallet",
    //       alwaysRun = true)
    // public void searchSaveContactUsBuyOneGetOneAddNewAddressOfficePhonePe() {
    //     loginAndCleanup().searchProductUsingStyleID()
    //                      .verifyProductDetailsInPDP()
    //                      .saveProductToWishlist()
    //                      .addProductFromPDPToShoppingBag()
    //                      .applyCouponAndPlaceOrderWithNewAddress()
    //                      .payUsingPhonepe();
    // }

    // @Test(groups = {"Online","wip-android"},
    //       description = "TC_ID:2635 - Add to bag, Move to Bag, Apply Coupon, Remove Address, Pay using PhonePe",
    //       alwaysRun = true)
    // public void selectBestPriceAddProductToCartPlaceOrderApplyCouponRemoveAddressAddAddressPayUsingPhonePe() {
    //     loginAndCleanup().searchProductUsingStyleID()
    //                      // .viewSimilarProduct() TODO - SimilarProducts feature is not available on stage Env.
    //                      .selectBestPriceAddProductToBagAndGoToBag()
    //                      .applyCouponAndPlaceOrderRemoveAddressAddNewAddress()
    //                      .payUsingPhonepe();
    // }

    // @Test(groups = {"Online","wip-android"},
    //       description = "TC_ID:2559 - Verify Product Details, View Size Chart, Apply Coupon, Click for Offer, View Details, Pay using Wallet",
    //       alwaysRun = true)
    // public void verifyProductDetailsInPdpSizeChartApplyCouponViewPriceDetailsPayUsingWallet() {
    //     loginAndCleanup().searchProductUsingStyleID()
    //                      .verifyProductDetailsInPDP()
    //                      .viewSizeChartAndSelectSizeAddToBagAndGoToShoppingBag()
    //                      .applyCouponAndAddNewAddress()
    //                      .viewPriceDetailsAndProceedToPayment()
    //                      .payUsingWallet();
    // }

    // @Test(groups = {"Online","wip-android"},
    //       description = "TC_ID:2001 - Search, Save Product to Wishlist, Apply Coupon, Add New Address, Pay using Net Banking",
    //       alwaysRun = true)
    // public void searchSaveToWishlistApplyCouponAddNewAddressAnPayUsingNetBanking() {
    //     loginAndCleanup().searchProductUsingStyleID()
    //                      .verifyProductDetailsInPDP()
    //                      .saveProductToWishlist()
    //                      .addProductFromPDPToShoppingBag()
    //                      .applyCouponAndPlaceOrderWithNewAddress()
    //                      .payUsingNetBanking();
    // }

    // @Test(groups = {"Online","wip-android"},
    //       description = "TC_ID:815 - Search, Save Product to Wishlist, Apply Coupon, Add New address, Pay using Net Banking",
    //       alwaysRun = true)
    // public void searchSaveToWishlistCheckBestPriceApplyCouponAddNewAddressEditAddressVerifyFreeGiftAndPayUsingNetBanking() {
    //     loginAndCleanup().searchProductUsingStyleID()
    //                      .saveProductToWishlist()
    //                      .selectBestPriceAddProductToBagAndGoToBag()
    //                      .applyCouponAndAddNewAddressAndEditAddress()
    //                      .payUsingNetBanking();
    // }

    // @Test(groups = {"Online","wip-android"},
    //       description = "TC_ID:1156 - Search, Save Product to Wishlist, Select Size Chart, Apply Coupon, Add New Address, Edit Address, Pay using Net Banking",
    //       alwaysRun = true)
    // public void searchSaveProductSelectSizeChartApplyCouponVerifyFreeGiftAddAndEditAddressPayUsingNetBanking() {
    //     loginAndCleanup().searchProductUsingStyleID()
    //                      .saveProductToWishlist()
    //                      .viewSizeChartAndSelectSizeAddToBagAndGoToShoppingBag()
    //                      .applyCouponAndAddNewAddressAndEditAddress()
    //                      .payUsingNetBanking()
    //                      .returnToMerchantSiteAfterNetbankingPayment()
    //                      .verifyOrderNumberFromMyOrders();

    // }

    // @Test(groups = {"Online","wip-android"},
    //       description = "TC_ID:536 - Search, Save Product to Wishlist, Select Size Chart, Apply Coupon, Add New Address, Pay using Net Banking",
    //       alwaysRun = true)
    // public void saveProductToWishlistCheckBestPriceApplyCouponViewDetailsPlaceOrderAddNewAddressAndPayUsingNetbanking() {
    //     loginAndCleanup().searchProductUsingStyleID()
    //                      .saveProductToWishlist()
    //                      .selectBestPriceAddProductToBagAndGoToBag()
    //                      .applyCouponAndAddNewAddress()
    //                      .viewPriceDetailsAndProceedToPayment()
    //                      .payUsingNetBanking();
    // }

    // @Test(groups = {"Online", "wip-ios", "wip-android"},
    //       description = "TC_ID:1046 - Search, Save Product to Wishlist, Move To Bag from Wishlist, Apply Personalized Coupon, Edit Address, Pay Using Netbanking+LP",
    //       alwaysRun = true)
    // public void saveMoveToBagApplyPersonalizedCouponEditAddressPayUsingOnline() {
    //     loginAndCleanup().searchProductUsingStyleID()
    //                      .saveProductToWishlist()
    //                      .addProductFromWishlistToShoppingBag()
    //                      .applyCouponAndAddNewAddressAndEditAddress()
    //                      .payUsingNetBanking();
    // }

    // @Test(groups = {"Online", "wip-ios", "wip-android"},
    //       description = "TC_ID:754 - Search, Add Product to Bag, Move to Bag, Add New Address, Pay using MyntraCredit",
    //       alwaysRun = true)
    // public void addToBagPlaceOrderBOGOViewDetailsAddNewAddressPayUsingMyntraCredit() {
    //     loginAndCleanup().searchProductUsingStyleID()
    //                      .addProductFromPDPToShoppingBag()
    //                      .placeOrderAndAddAddress()
    //                      .viewPriceDetailsAndProceedToPayment();
    //     //TODO-Myntra Credit as a payment (Test data not avaliable)
    // }

    // @Test(groups = {"Online", "wip-ios", "wip-android"},
    //       description = "TC_ID:734 - Search, BOGO, Size Chart, Add to Bag, Change Size and Quantity, Add Address, Pay Using Online+Manual GC",
    //       alwaysRun = true)
    // public void sizeChartAddToBagChangeSizeAndQuantityBOGOAddNewAddressPayUsingOnline() {
    //     loginAndCleanup().searchProductUsingStyleID()
    //                      .viewSizeChartAndSelectSizeAddToBagAndGoToShoppingBag()
    //                      .changeSizeQuantityOfProductInCart()
    //                      .placeOrderAddAddressAndContinueToPayment()
    //                      .payUsingNetBanking();
    // }

    // @Test(groups = {"Online", "wip-ios", "wip-android"},
    //       description = "TC_ID:1089 - BUG - Search, Staggered Combo, Check Delivery, Add to Bag, Edit Address, Pay Using Online+Mynt",
    //       alwaysRun = true)
    // public void checkDeliveryAddToBagStaggeredComboEditAddressPayUsingOnline() {
    //     loginAndCleanup().searchProductUsingStyleID()
    //                      .checkDeliveryAvailability()
    //                      .addProductFromPDPToShoppingBag()
    //                      .placeOrderAndEditAddress()
    //                      .payUsingNetBanking();
    // }

    // @Test(groups = {"Online", "wip-ios", "wip-android"},
    //       description = "TC_ID:1417 - Search, Add to Bag, Move to Bag, Edit Address, Pay Using Online",
    //       alwaysRun = true)
    // public void moveToBagPlaceOrderStaggeredComboEditAddressPayUsingOnline() {
    //     loginAndCleanup().searchProductUsingStyleID()
    //                      .selectSizeAddToBagAndMoveToShoppingBag()
    //                      .placeOrderAndEditAddress()
    //                      .payUsingNetBanking();
    // }

    // @Test(groups = {"Online", "wip-ios", "wip-android"},
    //       description = "TC_ID:1429 - Search, Save, Tap For Best Price, Size Chart, Add To Bag, Place Order, Add New Address, Pay Using Online+Manual GC",
    //       alwaysRun = true)
    // public void saveClickForBestPriceSizeChartAddToBagAddAddressPayUsingOnline() {
    //     loginAndCleanup().searchProductUsingStyleID()
    //                      .saveProductToWishlist()
    //                      .viewSizeChartAndTapForBestPrice()
    //                      .addProductFromPDPToShoppingBag()
    //                      .placeOrderAddAddressAndContinueToPayment()
    //                      .payUsingNetBanking();
    // }

    // @Test(groups = {"Online", "wip-ios", "wip-android"},
    //       description = "TC_ID:1438 - Search, Save To Wishlist, Add To Bag From Wishlist, Place Order, Add New Address, View Price Details, Pay Using Online+LP",
    //       alwaysRun = true)
    // public void saveEmptyWishlistMoveToBagPlaceOrderPercentageDiscountViewDetailsAddAddressPayUsingOnline() {
    //     loginAndCleanup().searchProductUsingStyleID()
    //                      .saveProductToWishlist()
    //                      .addProductFromWishlistToShoppingBag()
    //                      .placeOrderAndAddAddress()
    //                      .viewPriceDetailsAndProceedToPayment()
    //                      .payUsingNetBanking();
    //     //TODO-Style id is not available for percentage discount item (Test data not available)
    //     //TODO-LP as a payment (Test data not avaliable)
    // }

    // @Test(groups = {"Online", "wip-ios", "wip-android"},
    //       description = "TC_ID:1675 - Search, Image Verification, Save To Wishlist, Add To Bag, Add More From Wishlist, Place Order, Add New Address, Pay Using PhonePe",
    //       alwaysRun = true)
    // public void verifyImageSaveAddToBagAddMoreFromWishlistFreegiftAddAddressPayUsingPhonePe() {
    //     loginAndCleanup().searchProductUsingStyleID()
    //                      .navigateThroughImagesOfProduct()
    //                      .saveProductToWishlist()
    //                      .selectSizeAddToBagAndMoveToShoppingBag()
    //                      .addMoreFromWishlist()
    //                      .verifyFreeGift()
    //                      .placeOrderAddAddressAndContinueToPayment()
    //                      .payUsingPhonepe();
    //     //TODO - PhonePe Payment option, Test data not available
    // }

    // @Test(groups = {"Online", "wip-ios", "wip-android"},
    //       description = "TC_ID:2097 - Search, Save To Wishlist, Add to Bag From Wishlist, Apply personalised Coupon, Add New Address, View details, Pay using online+manual GC",
    //       alwaysRun = true)
    // //TODO - Rupee discount was not available for the style id. Add discount verification on PDP (Test data not available)
    // //TODO - Manual GiftCard Testdata is not available
    // public void verifyDiscountPriceSaveAddToBagFromWishListPlaceOrderApplyCouponViewDetailsPayUsingOnline() {
    //     loginAndCleanup().searchProductUsingStyleID()
    //                      .saveProductToWishlist()
    //                      .addProductFromWishlistToShoppingBag()
    //                      .applyCouponAndAddNewAddress()
    //                      .viewPriceDetailsAndProceedToPayment()
    //                      .payUsingNetBanking()
    //                      .returnToMerchantSiteAfterNetbankingPayment()
    //                      .verifyOrderNumberFromMyOrders();
    // }

    // @Test(groups = {"Online", "wip-ios", "wip-android"},
    //       description = "TC_ID:1746 - Search, Show similar products, Tap For Best Price, Add To Bag, gift wrap, Place Order, Add New Address, View details, Pay Using EMI",
    //       alwaysRun = true)
    // public void similarProductsClickForBestPriceGiftWrapViewDetailsPayusingNetBanking() {
    //     loginAndCleanup().searchProductUsingStyleID()
    //                      .viewSimilarProducts() //TODO - SimilarProducts feature is not available on stage environment
    //                      .selectBestPriceAddProductToBagAndGoToBag()
    //                      .giftWrapThisProduct()
    //                      .placeOrderAndAddAddress()
    //                      .viewPriceDetailsAndProceedToPayment()
    //                      .payUsingNetBanking()
    //                      .returnToMerchantSiteAfterNetbankingPayment()
    //                      .verifyOrderNumberFromMyOrders();
    // }

    // @Test(groups = {"Online", "wip-ios", "wip-android", "wip-mobile"},
    //       description = "TC_ID:269 - Search, Add to Bag, Change Size And Quantity, Verify Free gift, Add New Address Office, Pay Using Myntra Credit",
    //       alwaysRun = true)
    // //TODO - Myntracredit Testdata is not available
    // public void addToBagPlaceOrderChangeSizeAndQuantityFromBagFreeGiftAddNewAddressOfficePayUsingMyntraCredit() {
    //     loginAndCleanup().searchProductUsingStyleID()
    //                      .verifyProductDetailsInPDP()
    //                      .addProductFromPDPToShoppingBag()
    //                      .changeSizeQuantityOfProductInCart()
    //                      .verifyFreeGift()
    //                      .placeOrderAddAddressAndContinueToPayment()
    //                      .payUsingNetBanking()
    //                      .returnToMerchantSiteAfterNetbankingPayment()
    //                      .verifyOrderNumberFromMyOrders();
    // }

    // @Test(groups = {"Online", "wip-ios", "wip-android"},
    //       description = "TC_ID:1795 - Search, Show similar products, Tap For Best Price, Add To Bag, gift wrap, Place Order, Add New Address, View details, Pay Using EMI",
    //       alwaysRun = true)
    // public void similarProductsAddToBagPlaceOrderFreeGiftAddNewAddressOfficePayUsingNetBanking() {
    //     loginAndCleanup().searchProductUsingStyleID()
    //                      .viewSimilarProducts() //TODO - SimilarProducts feature is not available on stage environment
    //                      .addProductFromPDPToShoppingBag()
    //                      .verifyFreeGift()
    //                      .placeOrderAddAddressAndContinueToPayment()
    //                      .payUsingNetBanking()
    //                      .returnToMerchantSiteAfterNetbankingPayment()
    //                      .verifyOrderNumberFromMyOrders();
    // }

    // @Test(groups = {"Online", "wip-ios", "wip-android", "wip-mobile"},
    //       description = "TC_ID:1508 - Search, Save, Add To Bag, Go To Bag, Move To Wishlist, Move To Bag From Wishlist, Apply coupon, Edit Address, Pay Using GiftCard",
    //       alwaysRun = true)
    // //TODO - Giftcard Testdata is not available
    // public void saveGoToBagMoveToWishlistMoveToBagFromWishlistApplyCouponEditAddressPayUsingGiftCard() {
    //     loginAndCleanup().searchProductUsingStyleID()
    //                      .verifyProductDetailsInPDP()
    //                      .addProductFromPDPToShoppingBag()
    //                      .moveToWishlistAndAddFromWishlist()
    //                      .applyCouponAndAddNewAddressAndEditAddress()
    //                      .payUsingNetBanking()
    //                      .returnToMerchantSiteAfterNetbankingPayment()
    //                      .verifyOrderNumberFromMyOrders();
    // }

    // @Test(groups = {"Online", "wip-ios", "wip-android", "wip-mobile"},
    //       description = "TC_ID:2147 - Search, Add To Bag, View Size Chart, Contact Us, Go To Bag, BOGO, Add New Address, View Details, Pay Using Online+LP",
    //       alwaysRun = true)
    // //TODO - LP Testdata is not available
    // public void addToBagViewSizeChartContactUsBOGOViewDetailsPayUsingOnline() {
    //     loginAndCleanup().verifyContactUsHasDetails()
    //                      .searchProductUsingStyleID()
    //                      .verifyProductDetailsInPDP()
    //                      .viewSizeChartAndTapForBestPrice()
    //                      .addProductFromPDPToShoppingBag()
    //                      .placeOrderAndAddAddress()
    //                      .viewPriceDetailsAndProceedToPayment()
    //                      .payUsingNetBanking()
    //                      .returnToMerchantSiteAfterNetbankingPayment()
    //                      .verifyOrderNumberFromMyOrders();
    // }

    // @Test(groups = {"Online", "wip-ios", "wip-android"},
    //       description = "TC_ID:2186 - Search, Show similar products, Tap For Best Price, Add To Bag, Place Order, Apply coupon, Add New Address, Pay Using NetBanking",
    //       alwaysRun = true)
    // public void saveViewSimilarProductsAddToBagFromWishlistApplyCouponClickForBestPriceAddNewAddressHomePayUsingNetbanking() {
    //     loginAndCleanup().searchProductUsingStyleID()
    //                      .verifyProductDetailsInPDP()
    //                      .saveProductToWishlist()
    //                      .viewSimilarProducts() //TODO - SimilarProducts feature is not available on stage environment
    //                      .selectBestPriceAddProductToBagAndGoToBag()
    //                      .applyCouponAndPlaceOrderWithNewAddress()
    //                      .payUsingNetBanking()
    //                      .returnToMerchantSiteAfterNetbankingPayment()
    //                      .verifyOrderNumberFromMyOrders();
    // }

    // @Test(groups = {"Online", "wip-ios", "wip-android", "wip-mobile"},
    //       description = "TC_ID:2200 - Search, View Similar Products, Contact Us, Add To Bag, View Size Chart, Contact Us, Go To Bag, BOGO, Add New Address, View Details, Pay Using Online+LP",
    //       alwaysRun = true)
    // //TODO - EMI Testdata is not available
    // public void saveViewSimilarProductsContactUsAddToBagFromWishlistBOGOViewDetailsPayUsingEMI() {
    //     loginAndCleanup().verifyContactUsHasDetails()
    //                      .searchProductUsingStyleID()
    //                      .verifyProductDetailsInPDP()
    //                      .saveProductToWishlist()
    //                      .viewSizeChartAndTapForBestPrice()
    //                      .addProductFromWishlistToShoppingBag()
    //                      .placeOrderAndAddAddress()
    //                      .viewPriceDetailsAndProceedToPayment()
    //                      .payUsingNetBanking()
    //                      .returnToMerchantSiteAfterNetbankingPayment()
    //                      .verifyOrderNumberFromMyOrders();
    // }

    // @Test(groups = {"Online", "wip-ios", "wip-android"},
    //       description = "TC_ID:2368 - Search, Show similar products, Tap For Best Price, Add To Bag, Place Order, Gift Wrap, Add New Address, Pay Using NetBanking",
    //       alwaysRun = true)
    // public void clickForBestPriceviewSimilarProductsAddToBagGiftwrapAddNewAddressOfficePayUsingNetbanking() {
    //     loginAndCleanup().searchProductUsingStyleID()
    //                      .verifyProductDetailsInPDP()
    //                      .viewSimilarProducts() //TODO - SimilarProducts feature is not available on stage environment
    //                      .selectBestPriceAddProductToBagAndGoToBag()
    //                      .giftWrapThisProduct()
    //                      .placeOrderAddAddressAndContinueToPayment()
    //                      .payUsingNetBanking()
    //                      .returnToMerchantSiteAfterNetbankingPayment()
    //                      .verifyOrderNumberFromMyOrders();
    // }

    // @Test(groups = {"Online", "wip-ios", "wip-android", "wip-mobile"},
    //       description = "TC_ID:2439 - Search, Save, Check Delivery Option, Add To Bag, Remove Item From Bag, Add ITem From Wishlist, BOGO, Add New Address Office, Pay Using Online+LP",
    //       alwaysRun = true)
    // //TODO - LP Testdata is not available
    // public void saveCheckDeliveryOptionAddToBagRemoveItemFromBagAddItemFromWishlistBOGOAddAddressOfficePayUsingOnline() {
    //     loginAndCleanup().searchProductUsingStyleID()
    //                      .verifyProductDetailsInPDP()
    //                      .saveProductToWishlist()
    //                      .checkDeliveryAvailability()
    //                      .addProductFromPDPToShoppingBag()
    //                      .emptyTheCartAndAddProductsFromWishlist()
    //                      .placeOrderAddAddressAndContinueToPayment()
    //                      .payUsingNetBanking()
    //                      .returnToMerchantSiteAfterNetbankingPayment()
    //                      .verifyOrderNumberFromMyOrders();
    // }

    // @Test(groups = {"Online", "wip-ios", "wip-android"},
    //       description = "TC_ID:2559 - Search, view size chart, Tap For Best Price, Add To Bag, Apply Coupon, Place Order, viw price details, Add New Address, Pay Using NetBanking",
    //       alwaysRun = true)
    // public void viewSizeChartClickForBestPriceApplyCouponViewPriceDetailsPayUsingNetBanking() {
    //     loginAndCleanup().searchProductUsingStyleID()
    //                      .verifyProductDetailsInPDP()
    //                      .viewSizeChartAndTapForBestPrice()
    //                      .addProductFromPDPToShoppingBag()
    //                      .placeOrderAndAddAddress()
    //                      .viewPriceDetailsAndProceedToPayment()
    //                      .payUsingNetBanking()
    //                      .returnToMerchantSiteAfterNetbankingPayment()
    //                      .verifyOrderNumberFromMyOrders();
    // }

    // @Test(groups = {"Online", "wip-ios", "wip-android"},
    //       description = "TC_ID:2740 - Search, save, Add To Bag, Remove product from cart, Add more from wishlist, Place Order, Add New Address, Pay Using NetBanking",
    //       alwaysRun = true)
    // public void saveAddToBagEmptyWishlistPercentsgeDiscountRemoveAddressAddAddressPayUsingNetBanking() {
    //     loginAndCleanup().searchProductUsingStyleID()
    //                      .verifyProductDetailsInPDP()
    //                      .saveProductToWishlist()
    //                      .addProductFromPDPToShoppingBag()
    //                      .removeProductFromCart()
    //                      .addMoreFromWishlist()
    //                      .placeOrderAddAddressAndContinueToPayment()
    //                      .payUsingNetBanking()
    //                      .returnToMerchantSiteAfterNetbankingPayment()
    //                      .verifyOrderNumberFromMyOrders();
    // }

    // @Test(groups = {"Online", "wip-ios", "wip-android", "wip-mobile"},
    //       description = "TC_ID:2516 - Search, Save, Click For Best Price, Add To Bag, Empty Wishlist, Go To Bag, Percentage Discount, Edit Address, Pay Using Online+LP",
    //       alwaysRun = true)
    // //TODO - LP Testdata is not available
    // public void saveClickForBestPriceAddToBagFromWishlistPlaceOrderPercentageDiscountEditAddressPayUsingOnline() {
    //     loginAndCleanup().searchProductUsingStyleID()
    //                      .verifyProductDetailsInPDP()
    //                      .saveProductToWishlist()
    //                      .selectBestPriceAddProductToBagAndGoToBag()
    //                      .addMoreFromWishlist()
    //                      .placeOrderAndEditAddress()
    //                      .payUsingNetBanking()
    //                      .returnToMerchantSiteAfterNetbankingPayment()
    //                      .verifyOrderNumberFromMyOrders();
    // }

    // @Test(groups = {"Online", "wip-ios", "wip-android", "wip-mobile"},
    //       description = "TC_ID:2518 - Search, Save, Click For Best Price, View Size Chart, Remove Product From Bag, Add To Bag From Wishlist, Apply Coupon, Percentage Discount, Add Address Home, Pay Using EMI",
    //       alwaysRun = true)
    // //TODO - EMI Testdata is not available
    // public void saveClickedForBestPriceViewSizeChartRemoveProductAddFromWishlistApplyCouponPercentageDiscountAddAddressHomePayUsingNetBanking() {
    //     loginAndCleanup().searchProductUsingStyleID()
    //                      .verifyProductDetailsInPDP()
    //                      .saveProductToWishlist()
    //                      .viewSizeChartAndTapForBestPrice()
    //                      .addProductFromPDPToShoppingBag()
    //                      .emptyTheCartAndAddProductsFromWishlist()
    //                      .applyCouponAndPlaceOrderWithNewAddress()
    //                      .payUsingNetBanking()
    //                      .returnToMerchantSiteAfterNetbankingPayment()
    //                      .verifyOrderNumberFromMyOrders();
    // }

    // @Test(groups = {"Online", "wip-ios", "wip-android", "wip-mobile"},
    //       description = "TC_ID:3148 - Search, Save, Add To Bag, Apply Coupon, BOGO, Edit Address, Pay Using Gift Card",
    //       alwaysRun = true)
    // //TODO - GiftCard Testdata is not available
    // public void saveMoveToBagBOGOApplyCouponEditAddressPayUsingGiftCard() {
    //     loginAndCleanup().searchProductUsingStyleID()
    //                      .verifyProductDetailsInPDP()
    //                      .saveProductToWishlist()
    //                      .addProductFromPDPToShoppingBag()
    //                      .applyCouponAndAddNewAddressAndEditAddress()
    //                      .payUsingNetBanking()
    //                      .returnToMerchantSiteAfterNetbankingPayment()
    //                      .verifyOrderNumberFromMyOrders();
    // }
    
    // @Test(groups = {"Online", "wip-ios", "wip-android"},
    //       description = "TC_ID:3088 - Search, save, view size chart, Add To Bag, Remove product from cart, Add more from wishlist, Place Order, Add New Address, edit address,  Pay Using NetBanking",
    //       alwaysRun = true)
    // public void saveViewSizeChartAddToBagRemoveProductPercentageDiscountEditAddressPayUsingOnlineAndGiftCard() {
    //     loginAndCleanup().searchProductUsingStyleID()
    //                      .verifyProductDetailsInPDP()
    //                      .saveProductToWishlist()
    //                      .viewSizeChartAndSelectSizeAddToBagAndGoToShoppingBag()
    //                      .removeProductFromCart()
    //                      .addMoreFromWishlist()
    //                      .placeOrderAndEditAddress()
    //                      .payUsingNetBanking()
    //                      .returnToMerchantSiteAfterNetbankingPayment()
    //                      .verifyOrderNumberFromMyOrders();
    // }

}
