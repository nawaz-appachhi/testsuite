package com.myntra.core.business;

import com.myntra.core.pages.WishListPage;
import com.myntra.utils.test_utils.Assert;
import io.qameta.allure.Step;

public class WishList extends BusinessFlow {
    public static WishList getInstance() {
        return (WishList) getInstance(Of.WISHLIST);
    }

    @Step
    public WishList verifyProductDetailsinWishlistWithPDP() {
        WishListPage wishListPage = WishListPage.createInstance();
        Assert.assertTrue(wishListPage.isProductAddedToWishList(), "Product is not added to wishlist");
        wishListPage.isProductDetailsOfWishlistWithPDPSame();
        return this;
    }

    @Step
    public WishList verifyProductDetailsinWishlistWithPLP() {
        WishListPage wishListPage = WishListPage.createInstance();
        Assert.assertTrue(wishListPage.isProductAddedToWishList(), "Product is not added to wishlist");
        wishListPage.isProductDetailsOfWishlistWithPLPSame();
        return this;
    }

}
