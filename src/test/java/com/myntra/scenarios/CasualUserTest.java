package com.myntra.scenarios;

import com.myntra.core.business.Home;
import org.testng.annotations.Test;

public class CasualUserTest extends SupportTest {

    @Test(groups = {"CasualUser", "na-ios", "wip-android", "wip-mobile"},
          description = "TC_ID:190 - Add Product to Cart,add More from wishlist,freegift,palceOrder,Remove Address",
          alwaysRun = true)
    public void addProductToCartAndAddAddress() {
        setupLoginAndReset().searchProductUsingName()
                            .filterSearchResult()
                            .saveProductToWishlistFromListPage()
                            .selectFirstProductAndGoToPDP()
                            .verifyProductDetailsInPDP()
                            .viewSizeChartAndSelectSizeAddToBagAndGoToShoppingBag()
                            .verifyProductPriceInShoppingBagAndCompareWithPDP()
                            .addMoreFromWishlist()
                            .placeOrderAndAddAddress()
                            .removeExistingAddress();
    }

    @Test(groups = {"CasualUser", "na-ios"},
          description = "TC_ID:842 - Save Product, Add Product to Cart and Add new Address",
          alwaysRun = true)
    public void addToBagApplyCouponPlaceOrderAndAddNewAddress() {
        setupLoginAndReset().searchProductUsingName()
                            .selectFirstProductAndGoToPDP()
                            .verifyProductDetailsInPDP()
                            .viewSimilarProducts()
                            .selectSizeAddToBagAndMoveToShoppingBag()
                            .verifyProductPriceInShoppingBagAndCompareWithPDPAndPLP()
                            .placeOrderAndAddAddress();
    }

    @Test(groups = {"CasualUser", "na-ios"},
          description = "TC_ID:180 - Save product, Verify Product Code, Move to bag, Place Order, Percentage Discount, Remove Address",
          alwaysRun = true)
    public void saveProductMoveToBagPlaceOrderRemoveAddress() {
        setupLoginAndReset().searchProductUsingName()
                            .selectFirstProductAndGoToPDP()
                            .verifyProductDetailsInPDP()
                            .saveProductToWishlist()
                            .selectSizeAddToBagAndMoveToShoppingBag()
                            .verifyProductPriceInShoppingBagAndCompareWithPDP()
                            .placeOrderAndAddAddress()
                            .removeExistingAddress();
    }

    @Test(groups = {"CasualUser"},
          description = "TC_ID:1673 - Search(PLP), Select Best Price, Add to Bag, Go to bag,Empty cart and add product from wishlist, Edit Address",
          alwaysRun = true)
    public void searchFreeGiftProductCheckBestPricePlaceOrderEditAddress() {
        setupLoginAndReset().searchProductUsingName()
                            .selectFirstProductAndGoToPDP()
                            .verifyProductDetailsInPDP()
                            .saveProductToWishlist()
                            .selectBestPriceAddProductToBagAndGoToBag()
                            .verifyProductDiscountInShoppingBagAndCompareWithPDP()
                            .emptyTheCartAndAddProductsFromWishlist()
                            .placeOrderAndEditAddress();
    }

    @Test(groups = {"CasualUser", "na-ios", "wip-mobile"},
          description = "TC_ID:239 - Search, Check delivery, Add to Bag, Go to bag, Apply Coupon, Free Gift, Edit Address",
          alwaysRun = true)
    public void searchProductCheckDeliveryOptionMoveToBagApplyCouponAddNewAddress() {
        setupLoginAndReset().searchProductUsingName()
                            .sortSearchResult()
                            .selectFirstProductAndGoToPDP()
                            .verifyProductDetailsInPDP()
                            .checkDeliveryAvailability()
                            .selectSizeAddToBagAndMoveToShoppingBag()
                            .verifyProductPriceInShoppingBagAndCompareWithPDPAndPLP()
                            .placeOrderAndAddAddress();
    }

    @Test(groups = {"CasualUser", "na-ios"},
          description = "TC_ID:113 - Save product, Verify Product Price, Empty Wishlist, Change Size & Add Quantity from Cart, Place Order, Remove Address",
          alwaysRun = true)
    public void saveProductEmptyWishlistaddProductToBagChangeSizeAndQuantityAndRemoveAddress() {
        setupLoginAndReset().searchProductUsingName()
                            .selectFirstProductAndGoToPDP()
                            .saveProductToWishlist()
                            .verifyProductDetailsInPDP()
                            .addProductFromWishlistToShoppingBag()
                            .verifyProductPriceInShoppingBagAndCompareWithPDPAndPLP()
                            .changeSizeQuantityOfProductInCart()
                            .placeOrderAndAddAddress()
                            .removeExistingAddress();
    }

    @Test(groups = {"CasualUser", "na-ios"},
          description = "TC_ID:174 - Save product, Verify Product Price, Move to Bag, Add more from Wishlist, Apply Personalized Coupons, Remove Address",
          alwaysRun = true)
    public void addToBagAddMoreFromWishlistApplyCouponAndRemoveAddress() {
        setupLoginAndReset().searchProductUsingName()
                            .filterSearchResult()
                            .selectFirstProductAndGoToPDP()
                            .verifyProductDetailsInPDP()
                            .saveProductToWishlist()
                            .addProductFromPDPToShoppingBag()
                            .verifyProductPriceInShoppingBagAndCompareWithPDPAndPLP()
                            .addMoreFromWishlist()
                            .placeOrderAndAddAddress()
                            .removeExistingAddress();
    }

    @Test(groups = {"CasualUser", "na-ios"},
          description = "TC_ID:240 - Save product, Empty Wishlist, Free Gift, Place Order, Edit Address",
          alwaysRun = true)
    public void saveEmptyWishlistFreeGiftAndEditAddress() {
        setupLoginAndReset().verifyContactUsHasDetails()
                            .searchProductUsingName()
                            .sortSearchResult()
                            .selectFirstProductAndGoToPDP()
                            .verifyProductDetailsInPDP()
                            .viewSimilarProducts()
                            .saveProductToWishlist()
                            .addProductFromWishlistToShoppingBag()
                            .verifyProductPriceInShoppingBagAndCompareWithPDPAndPLP()
                            .placeOrderAndAddAddress()
                            .editAddressAndProceedToPayment();
    }

    @Test(groups = {"CasualUser", "na-ios"},
          description = "TC_ID:346 - Search, Check Best Price, Add to Bag, Go to Bag, Select Size from Wishlist, Place order, Remove Address",
          alwaysRun = true)
    public void homePageSearchClickForBestPriceSelectSizeFromWishlistPlaceOrderRemoveaddress() {
        setupLoginAndReset().verifyContactUsHasDetails()
                            .searchProductUsingName()
                            .selectFirstProductAndGoToPDP()
                            .verifyProductDetailsInPDP()
                            .saveProductToWishlist()
                            .selectBestPriceAddProductToBagAndGoToBag()
                            .addMoreFromWishlist()
                            .placeOrderAndAddAddress()
                            .removeExistingAddress();
    }

    @Test(groups = {"PhaseTwo", "na-ios"},
          description = "TC_ID:95 - Login as Registered user, Go to saved address page, Add address under account section, Verify added address",
          alwaysRun = true)
    public void verifyRegisteredUserAbleToAddNewAddress() {
        setupLoginAndReset().goToSavedAddressPage()
                            .addNewAddressInSavedAddress();
    }

    @Test(groups = {"PhaseTwo", "na-ios"},
          description = "TC_ID:339 - Login as Registered user, Search, Add To Bag, Go To Bag, Apply Invalid Coupon, Verify Error Message",
          alwaysRun = true)
    public void verifyRegisteredUserAbleToAddInvalidCoupon() {
        setupLoginAndReset().searchProductUsingStyleID()
                            .verifyProductDetailsInPDP()
                            .addProductFromPDPToShoppingBag()
                            .verifyErrorMessageForInvalidCouponCode();
    }

    @Test(groups = {"PhaseTwo", "na-ios"},
          description = "TC_ID:341 - Login as Registered user, Search, Add To Bag, Go To Bag, Apply 'Not Applicable' Coupon",
          alwaysRun = true)
    public void verifyRegisteredUserAbleToAddNotApplicableCoupon() {
        setupLoginAndReset().searchProductUsingStyleID()
                            .verifyProductDetailsInPDP()
                            .addProductFromPDPToShoppingBag()
                            .verifyNotApplicableCouponIsNotAbleToSelect();
    }

    @Test(groups = {"PhaseTwo", "na-ios"},
          description = "TC_ID:97 - Login as Registered user, Go to saved address page, Add address ,Remove address, Verify added address got removed",
          alwaysRun = true)
    public void verifyRegisteredUserAbleToRemoveAddedAddress() {
        setupLoginAndReset().goToSavedAddressPage()
                            .addNewAddressInSavedAddress()
                            .removeSavedAddressAndVerify();
    }

    @Test(groups = {"PhaseTwo", "na-ios", "na-android"},
          description = "TC_ID:104 - Login as Registered user, Go To Saved Cards Page, Add New Card",
          alwaysRun = true)
    public void addCardInSavedCardsPage() {
        setupLoginAndReset().goToSavedCardsPage()
                            .addNewCard();
    }

    @Test(groups = {"PhaseTwo", "na-ios", "na-android"},
          description = "TC_ID:103 - Login as Registered user, Go To Saved Cards Page, Remove Added Card",
          alwaysRun = true)
    public void removeCardInSavedCardsPage() {
        setupLoginAndReset().goToSavedCardsPage()
                            .removeAllSavedCard();
    }

    @Test(groups = {"PhaseTwo", "na-ios", "na-android"},
          description = "TC_ID:102 - Login as Registered user, Go To Saved Cards Page, Edit Saved Card",
          alwaysRun = true)
    public void editCardInSavedCardsPage() {
        setupLoginAndReset().goToSavedCardsPage()
                            .addNewCard()
                            .editSavedCard();
    }

    @Test(groups = {"PhaseTwo", "na-ios", "wip-android"},
          description = "TC_ID:98 - Login as Registered user, Edit profile ,Change password and verify",
          alwaysRun = true)
    public void verifyRegisteredUserAbleToEditProfileAndChangePassword() {
        setupLoginAndReset().editProfile()
                            .changeAccountPassword();
    }

    @Test(groups = {"PhaseTwo", "na-ios"},
          description = "TC_ID:134 - Login as Registered user, Add Item To Bag, Place Order, Add New Address, Verify Necessary Payment Options Are Enabled ",
          alwaysRun = true)
    public void verifyPaymentModeSelection() {
        setupLoginAndReset().searchProductUsingStyleID()
                            .addProductFromPDPToShoppingBag()
                            .placeOrderAddAddressAndContinueToPayment()
                            .verifyAllNecessaryPaymentOptionsAreEnabled();
    }

    @Test(groups = {"PhaseTwo", "na-ios", "wip-android",},
          description = "TC_ID:263 - Login as Registered user, Add Item To Bag, Place Order, Add New Address, Verify Necessary Payment Options Are Enabled ",
          alwaysRun = true)
    public void verifyCartPageMergedWithNewlyAddedItems() {
        setupLoginAndReset().searchProductUsingStyleID()
                            .verifyProductDetailsInPDP()
                            .addProductFromPDPToShoppingBag()
                            .verifyProductPriceInShoppingBagAndCompareWithPDP()
                            .verifyProductDiscountInShoppingBagAndCompareWithPDP()
                            .productCountInBag()
                            .naviagteToHome()
                            .logOut()
                            .searchProductUsingHovering()
                            .selectFirstProductAndGoToPDP()
                            .addProductFromPDPToShoppingBag()
                            .verifyItemsMerged();
    }

    @Test(groups = {"PhaseTwo", "na-ios", "wip-android", "wip-desktop"},
          description = "TC_ID:99 - SignUp, navigate to profile details from user name,Add profile details  and verify",
          alwaysRun = true)
    public void signUpAddProfileDetailsFromUserNameSectionAndVerify() {
        Home.getInstance()
            .signUpSuccessfully()
            .addUserInformationFromProfilePage();
    }

    @Test(groups = {"PhaseTwo", "na-ios", "wip-android"},
          description = "TC_ID:100 - Login as Registered user,navigate to profile details from user name,Cancel Edit Profile",
          alwaysRun = true)
    public void verifyCancellationInEditProfile() {
        setupLoginAndReset().cancelEditUserFromProfilePage();
    }

    @Test(groups = {"PhaseTwo", "na-ios", "wip-android", "wip-mobile"},
          description = "TC_ID:340 - Login as Registered user, Search, Add To Bag, Go To Bag, Apply 'Not Applicable' Coupon",
          alwaysRun = true)
    public void verifyRegisteredUserAbleToEnterNotApplicableCouponText() {
        setupLoginAndReset().searchProductUsingStyleID()
                            .addProductFromPDPToShoppingBag()
                            .verifyNotApplicableCouponIsNotAbleToEnter();
    }

    @Test(groups = {"PhaseTwo", "na-ios", "wip-android",},
          description = "TC_ID:368 - Login as Registered user, Search Using Query, verify Product Discount in PLP, Add Item To Bag, Verify That Product Discount Is Same For PLP,PDP And Bag",
          alwaysRun = true)
    public void verifyDiscountAvailableInPLPAndPDPAndBagIsSame() {
        setupLoginAndReset().searchProductUsingName()
                            .filterSearchResult()
                            .selectFirstProductAndGoToPDP()
                            .verifyProductDetailsInPDP()
                            .addProductFromPDPToShoppingBag()
                            .verifyProductDiscountInShoppingBagAndCompareWithPDPAndPLP();
    }

    @Test(groups = {"PhaseTwo", "na-ios", "wip-android", "wip-mobile", "wip-desktop", "stub"},
          description = "TC_ID:412 - Login as Registered user, Search by name, Save,verify details in wishlist with PDP",
          alwaysRun = true)
    public void verifyProductDetailsInWishlistPageAreSameAsInPDP() {
        setupLoginAndReset().searchProductUsingStyleID()
                            .verifyProductDetailsInPDP()
                            .saveProductToWishlist()
                            .goToWishlistPageFromPDP()
                            .verifyProductDetailsinWishlistWithPDP();
    }

    @Test(groups = {"PhaseTwo", "na-ios", "wip-android", "wip-mobile", "wip-desktop", "stub"},
          description = "TC_ID:411 - Login as Registered user, Search by name, Save,verify details in wishlist with PLP",
          alwaysRun = true)
    public void verifyProductDetailsInWishlistPageAreSameAsInPLP() {
        setupLoginAndReset().searchProductUsingName()
                            .saveProductToWishlistFromListPage()
                            .goToWishlistPageFromPLP()
                            .verifyProductDetailsinWishlistWithPLP();
    }

    @Test(groups = {"PhaseTwo", "na-ios", "wip-android", "wip-mobile", "wip-desktop", "stub"},
          description = "TC_ID:387 - Login as Registered user, Search by Promotions(Free Gift product), add product to bag, verify free giftP",
          alwaysRun = true)
    public void searchSelectFreeGiftProductThroughPromotionAddToCartVerifyFreeGift() {
        setupLoginAndReset().searchProductUsingName()
                            .selectFreeGiftProductThroughPromotions()
                            .addProductFromPDPToShoppingBag()
                            .verifyFreeGift();

    }

    @Test(groups = {"PhaseTwo", "na-ios", "wip-android", "wip-mobile", "wip-desktop", "stub"},
          description = "TC_ID:386 - Login as Registered user, Search by Promotions, add product to bag, verify free giftP",
          alwaysRun = true)
    public void searchSelectFreeGiftWhichAloneISNotApplicableThroughPromotionAddToCartVerifyFreeGift() {
        setupLoginAndReset().searchProductUsingName()
                            .selectFreeGiftProductNotApplicableAloneThroughPromotions()
                            .addProductFromPDPToShoppingBag()
                            .verifyOneMoreProductTobeAddedTOCompleteFreeGiftOffer();

    }

    @Test(groups = {"PhaseTwo", "na-ios", "wip-android", "wip-mobile", "wip-desktop", "stub"},
          description =
                  "TC_ID:385 - Login as Registered user, Search by Promotions, add Completed conditional discount product to bag, place order," +
                          "remove one of the product in completed conditional discount products",
          alwaysRun = true)
    public void searchCompleteConditionalDiscountOfProductsThroughPromotionAddToCarPlaceOrderRemoveOneProductInConditionalDiscount() {
        setupLoginAndReset().searchProductUsingName()
                            .selectAndCompleteConditionalDiscountProductsThroughPromotions()
                            .addProductFromPDPToShoppingBag()
                            .placeOrderAddAddressAndContinueToPayment()
                            .payUsingNetBanking()
                            .returnToMerchantSiteAfterNetbankingPayment()
                            .verifyOrderNumberFromMyOrders()
                            .verifyOrderIsConfirmed()
                            .verifyOneProductCanBeRemovedAfterPlaceOrderInCompletedConditionDiscountProducts();
    }

    @Test(groups = {"PhaseTwo", "na-ios", "wip-android", "wip-mobile", "wip-desktop", "stub"},
          description =
                  "TC_ID:384 - Login as Registered user, Search by Promotions, add Completed conditional discount product to bag, place order," +
                          "remove one of the product in completed conditional discount products",
          alwaysRun = true)
    public void searchOneConditionalDiscountOfProductThroughPromotionAddToCarVerifyConditionalDiscountCompleteConditionalDiscount() {
        setupLoginAndReset().searchProductUsingName()
                            .selectOneProductInConditionalDiscountProductsThroughPromotions()
                            .addProductFromPDPToShoppingBag()
                            .completeAndVerifyConditionalDiscount();
    }

    @Test(groups = {"PhaseTwo", "na-ios", "wip-android", "wip-mobile"},
          description = "TC_ID:333 - Login as Registered user, Search, Add To Bag, Go To Bag, Apply unauthorized Coupon ",
          alwaysRun = true)
    public void verifyPersonalizedCouponForUnAuthorizedUser() {
        setupLoginAndReset().searchProductUsingStyleID()
                            .addProductFromPDPToShoppingBag()
                            .verifyPersonalizedCouponForOtherUser();
    }

    @Test(groups = {"PhaseTwo", "na-ios", "wip-android", "wip-mobile"},
          description = "TC_ID:336 - Login as Registered user, Add Item To Bag, Verify That User Is Able To Apply Multiple Coupons For Single Product",
          alwaysRun = true)
    public void verifyUserAbleToApplyMultipleCouponsForSingleProduct() {
        setupLoginAndReset().searchProductUsingStyleID()
                            .verifyProductDetailsInPDP()
                            .addProductFromPDPToShoppingBag()
                            .enterCouponCodeAndChooseCouponFromValidCoupons();
    }
}