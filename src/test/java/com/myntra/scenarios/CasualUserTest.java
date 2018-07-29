package com.myntra.scenarios;

import org.testng.annotations.Test;

public class CasualUserTest extends SupportTest {

    @Test(groups = {"CasualUser"},
          description = "TC_ID:190 - Add Product to Cart,add More from wishlist,freegift,palceOrder,Remove Address",
          alwaysRun = true)
    public void addProductToCartAndAddAddress() {
        loginAndCleanup().searchProductUsingStyleID()
                         .saveProductToWishlist()
                         .viewSizeChartAndSelectSizeAddToBagAndGoToShoppingBag()
                         .addMoreFromWishlist()
                         .verifyFreeGift()
                         .placeOrderAndAddAddress()
                         .removeExistingAddress();
    }

    @Test(groups = {"CasualUser1"},
          description = "TC_ID:842 - Save Product, Add Product to Cart and Add new Address",
          alwaysRun = true)
    public void addToBagApplyCouponPlaceOrderAndAddNewAddress() {
        loginAndCleanup().searchProductUsingStyleID()
                         .verifyProductDetailsInPDP()
                         .selectSizeAddToBagAndMoveToShoppingBag()
                         .applyCouponAndPlaceOrderWithNewAddress();
    }

    @Test(groups = {"CasualUser1"},
          description = "TC_ID:180 - Save product, Verify Product Code, Move to bag, Place Order, Percentage Discount, Remove Address",
          alwaysRun = true)
    public void saveProductMoveToBagPlaceOrderRemoveAddress() {
        loginAndCleanup().searchProductUsingStyleID()
                         .saveProductToWishlist()
                         .verifyProductDetailsInPDP()
                         .selectSizeAddToBagAndMoveToShoppingBag()
                         .placeOrderAndAddAddress()
                         .removeExistingAddress();
    }

    @Test(groups = {"CasualUser1"},
          description = "TC_ID:928 - Save product, Add to bag from Wishlist, Change Size and Add Quantity in Shopping bag,Place Order, Percentage Discount, EditAddress",
          alwaysRun = true)
    public void selectSizeFromWishlistChangeSizeAndAddQuantityApplyCouponPlaceOrderAndAddNewAddressEditAddress() {
        loginAndCleanup().searchProductUsingStyleID()
                         .saveProductToWishlist()
                         .addProductFromWishlistToShoppingBag()
                         .changeSizeQuantityOfProductInCart()
                         .applyCouponAndAddNewAddressAndEditAddress();
    }

    @Test(groups = {"CasualUser1"},
          description = "TC_ID:1673 - Search, Select Best Price, Add to Bag, Go to bag, Free Gift, Edit Address",
          alwaysRun = true)
    public void searchFreeGiftProductCheckBestPricePlaceOrderEditAddress() {
        loginAndCleanup().searchProductUsingStyleID()
                         .saveProductToWishlist()
                         .selectBestPriceAddProductToBagAndGoToBag()
                         .removeProductFromCart()
                         .moveToWishlistAndAddFromWishlist()
                         .verifyFreeGift()
                         .placeOrderAndEditAddress();
    }

    @Test(groups = {"CasualUser1"},
          description = "TC_ID:239 - Search, Check delivery, Add to Bag, Go to bag, Apply Coupon, Free Gift, Edit Address",
          alwaysRun = true)
    public void searchProductCheckDeliveryOptionMoveToBagApplyCouponAddNewAddress() {
        loginAndCleanup().searchProductUsingStyleID()
                         .verifyProductDetailsInPDP()
                         .checkDeliveryAvailability()
                         .selectSizeAddToBagAndMoveToShoppingBag()
                         .applyCouponAndPlaceOrderWithNewAddress();
    }

    @Test(groups = {"CasualUser1"},
          description = "TC_ID:113 - Save product, Verify Product Price, Empty Wishlist, Change Size & Add Quantity from Cart, Place Order, Remove Address",
          alwaysRun = true)
    public void saveProductEmptyWishlistaddProductToBagChangeSizeAndQuantityAndRemoveAddress() {
        loginAndCleanup().searchProductUsingStyleID()
                         .saveProductToWishlist()
                         .verifyProductDetailsInPDP()
                         .addProductFromWishlistToShoppingBag()
                         .changeSizeQuantityOfProductInCart()
                         .placeOrderAndAddAddress()
                         .removeExistingAddress();
    }

    @Test(groups = {"CasualUser1"},
          description = "TC_ID:174 - Save product, Verify Product Price, Move to Bag, Add more from Wishlist, Apply Personalized Coupons, Remove Address",
          alwaysRun = true)
    public void addToBagAddMoreFromWishlistApplyCouponAndRemoveAddress() {
        loginAndCleanup().searchProductUsingStyleID()
                         .saveProductToWishlist()
                         .addProductFromPDPToShoppingBag()
                         .addMoreFromWishlist()
                         .applyCouponAndAddNewAddress()
                         .removeExistingAddress();
    }

    @Test(groups = {"CasualUser1"},
          description = "TC_ID:240 - Save product, Empty Wishlist, Free Gift, Place Order, Edit Address",
          alwaysRun = true)
    public void saveEmptyWishlistFreeGiftAndEditAddress() {
        loginAndCleanup().searchProductUsingStyleID()
                         .saveProductToWishlist()
                         .addProductFromWishlistToShoppingBag()
                         .applyCouponAndAddNewAddressAndEditAddress();
    }

    @Test(groups = {"CasualUser1", "wip-ios"},
          description = "TC_ID:346 - Search, Check Best Price, Add to Bag, Go to Bag, Select Size from Wishlist, Place order, Remove Address",
          alwaysRun = true)
    public void homePageSearchClickForBestPriceSelectSizeFromWishlistPlaceOrderRemoveaddress() {
        loginAndCleanup().searchProductUsingStyleID()
                         .saveProductToWishlist()
                         .selectBestPriceAddProductToBagAndGoToBag()
                         .addMoreFromWishlist()
                         .applyCouponAndAddNewAddress()
                         .removeExistingAddress();
    }
}
