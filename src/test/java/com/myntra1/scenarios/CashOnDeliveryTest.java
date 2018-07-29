package com.myntra1.scenarios;

import org.testng.annotations.Test;

public class CashOnDeliveryTest extends SupportTest {

    @Test(groups = {"CashOnDelivery", "wip-desktop"},
          description = "TC_ID:xxx - Save the Product, Add Product from Wishlist and Apply Coupon",
          alwaysRun = true)
    public void addProductToWishlistApplyCouponPlaceOrder() {
        loginAndCleanup().searchProductUsingStyleID()
                         .verifyProductDetailsInPDP()
                         .saveProductToWishlist()
                         .addProductFromWishlistToShoppingBag()
                         .applyCouponAndPlaceOrderWithNewAddress()
                         .payUsingCOD();
    }

    @Test(groups = {"CashOnDelivery"},
          description = "TC_ID:3218 - Add To Bag, Size Chart, Gift Wrap, Staggered Combo, Add New Address_Home, Pay using COD",
          alwaysRun = true)
    public void addProductToBagGiftWrapAddNewAddressHomePayUsingCOD() {
        loginAndCleanup().searchProductUsingStyleID()
                         .selectSizeAddToBagAndMoveToShoppingBag()
                         .placeOrderAddAddressAndContinueToPayment()
                         .payUsingCOD()
                         .verifyOrderNumberFromMyOrders();
    }

    @Test(groups = {"CashOnDelivery"},
          description = "TC_ID:2768 - Image Verification, Save To Wishlist, Tap For Best Price, Add to Bag, Empty Bag, Add Items From Wishlist, Place Order, Add New Address, View Price Details, Pay Using COD+Mynt",
          alwaysRun = true)
    public void imageVerificationSaveTapforBestPriceEmptyCartAddItemsFromWishlistViewPriceDetailsPayUsingCOD() {
        loginAndCleanup().searchProductUsingStyleID()
                         .navigateThroughImagesOfProduct()
                         .saveProductToWishlist()
                         .selectBestPriceAddProductToBagAndGoToBag()
                         .emptyTheCartAndAddProductsFromWishlist()
                         .placeOrderAndAddAddress()
                         .viewPriceDetailsAndProceedToPayment()
                         .payUsingCOD()
                         .verifyOrderNumberFromMyOrders();
        //TODO - Mynt as payment option, but testdata is not available
    }

    @Test(groups = {"CashOnDelivery"},
          description = "TC_ID:2762 - Search, Check for Best Price, Show Similar, Move to Wishlist, Free Gift, Add New Address_Home, Pay using Mynt+COD",
          alwaysRun = true)
    public void clickForBestPriceAddToBagMoveToWishlistFreeGiftAddAddressAndCOD() {
        loginAndCleanup().searchProductUsingStyleID()
                         .verifyProductDetailsInPDP()
                         //.viewSimilarProduct()   TODO - SimilarProducts feature is not available on stage environment
                         .saveProductToWishlist()
                         .selectBestPriceAddProductToBagAndGoToBag()
                         .moveToWishlistAndAddFromWishlist()
                         .verifyFreeGift()
                         .applyCouponAndPlaceOrderWithNewAddress()
                         .payUsingCOD()
                         .verifyOrderNumberFromMyOrders();
    }

    @Test(
    		groups = {"CashOnDelivery"},
          description = "TC_ID:1792 -Search, Save Product to Wishlist, Check for Best Offer, Pay using Mynt+COD",
          alwaysRun = true
          )
    public void searchSaveToWishlistCheckBestPriceRemoveandAddAddressAnPayUsingCOD() {
        loginAndCleanup().searchProductUsingStyleID()
                         .saveProductToWishlist()
                         .selectBestPriceAddProductToBagAndGoToBag()
                         .placeOrderAndAddAddress()
                         .reEnterAddressAndProceedToPayment()
                         .payUsingCOD()
                         .verifyOrderNumberFromMyOrders();
    }

    @Test(groups = {"CashOnDelivery","wip-ios"},
          description = "TC_ID:2453 - BUG - Search, Check for Delivery Option, Add product, Add More from Wishlist, Pay using Mynt+COD",
          alwaysRun = true)
    public void homeSearchMoveToBagAddMoreFromWishlistAddNewAddressOfficeMyntraCreditCOD() {
        loginAndCleanup().searchProductUsingStyleID()
                         .verifyProductDetailsInPDP()
                         .saveProductToWishlist()
                         .checkDeliveryAvailability()
                         .addProductFromPDPToShoppingBag()
                         .addMoreFromWishlist()
                         .applyCouponAndPlaceOrderWithNewAddress()
                         .payUsingCOD()
                         .verifyOrderNumberFromMyOrders();
    }


    @Test(groups = {"CashOnDelivery", "wip-ios", "wip-android"},
          description = "TC_ID:412 - Search, Save, Add To Bag, Add more from Wishlist, Place Order, Pay using COD",
          alwaysRun = true)
    public void imageVerificationSaveAddToBagAddMoreFromWishlistViewDetailsCOD() {
        loginAndCleanup().searchProductUsingStyleID()
                         .navigateThroughImagesOfProduct()
                         .saveProductToWishlist()
                         .addProductFromPDPToShoppingBag()
                         .addMoreFromWishlist()
                         .placeOrderAndAddAddress()
                         .viewPriceDetailsAndProceedToPayment()
                         .payUsingCOD()
                         .verifyOrderNumberFromMyOrders();
    }

    @Test(groups = {"CashOnDelivery", "wip-ios", "wip-android"},
          description = "TC_ID:944 - Search, Save, Empty Wishlist, Move to Bag, Place Order, Remove Address, Add New Address, Pay using COD",
          alwaysRun = true)
    public void saveEmptyWishlistMoveToBagPlaceOrderRemoveAddressAddAddressAndCOD() {
        loginAndCleanup().searchProductUsingStyleID()
                         .saveProductToWishlist()
                         .checkDeliveryAvailability()
                         .addProductFromWishlistToShoppingBag()
                         .placeOrderAndAddAddress()
                         .reEnterAddressAndProceedToPayment()
                         .payUsingCOD()
                         .verifyOrderNumberFromMyOrders();
    }

    @Test(groups = {"CashOnDelivery", "wip-ios", "wip-android"},
          description = "TC_ID:1843 - Search, Size Chart, Add To Bag, Move To Wishlist, Move To Bag From Wishlist, Place Order, Add New Address, Pay using COD+Mynt",
          alwaysRun = true)
    public void sizeChartAddToBagMoveToWishlistMoveToBagFromWishlistBOGOViewDetailsPayUsingCOD() {
        loginAndCleanup().searchProductUsingStyleID()
                         .addProductFromPDPToShoppingBag()
                         .moveToWishlistAndAddFromWishlist()
                         .placeOrderAndAddAddress()
                         .viewPriceDetailsAndProceedToPayment()
                         .payUsingCOD()
                         .verifyOrderNumberFromMyOrders();
    }


    @Test(groups = {"CashOnDelivery", "wip-ios", "wip-android"},
          description = "TC_ID:1851 - Search, Add To Bag, Giftwrap, BOGO, Place Order, Add New Address, View Price Details, Pay using COD+Mynt",
          alwaysRun = true)
    public void addToBagGiftWrapBOGOPlaceOrderAddNewAddressViewDetailsPayUsingCOD() {
        loginAndCleanup().searchProductUsingStyleID()
                         .addProductFromPDPToShoppingBag()
                         .giftWrapThisProduct()
                         .placeOrderAndAddAddress()
                         .viewPriceDetailsAndProceedToPayment()
                         .payUsingCOD()
                         .verifyOrderNumberFromMyOrders();
    }

    @Test(groups = {"CashOnDelivery", "wip-ios", "wip-android", "wip-mobile"},
          description = "TC_ID:2705 - Search, Save, Click For Best Price, View Similar Products, Go To Bag, Add More From Wishlist, Remove Address, Add Address, Pay Using Netbanking",
          alwaysRun = true)
    //TODO - Myntra Credit Testdata is not available
    public void saveClickForBestPriceShowSimilarProductsAddToBagAddMoreFromWishListRemoveAddressAddAddressPayUsingCODAndMyntraCredit() {
        loginAndCleanup().searchProductUsingStyleID()
                         .verifyProductDetailsInPDP()
                         .saveProductToWishlist()
                         .viewSimilarProducts()
                         .selectBestPriceAddProductToBagAndGoToBag()
                         .addMoreFromWishlist()
                         .placeOrderAndAddAddress()
                         .reEnterAddressAndProceedToPayment()
                         .payUsingNetBanking()
                         .returnToMerchantSiteAfterNetbankingPayment()
                         .verifyOrderNumberFromMyOrders();
    }
}
