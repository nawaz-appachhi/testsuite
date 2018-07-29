package com.myntra.core.business;

import com.myntra.core.pages.ProductDescPage;
import com.myntra.core.pages.ProductListPage;
import com.myntra.core.pages.WishListPage;
import com.myntra.utils.test_utils.Assert;
import io.qameta.allure.Step;

import java.util.HashMap;

public class Product extends BusinessFlow {

    public static Product getInstance() {
        return (Product) getInstance(Of.PRODUCT);
    }

    @Step
    public Product saveProductToWishlist() {
        boolean isProductAvailableInWishList = ProductDescPage.createInstance()
                                                              .saveProductToWishlist()
                                                              .isProductSavedToWishList();
        Assert.assertTrue(isProductAvailableInWishList, "Product NOT found in WishList");
        return this;
    }

    @Step
    public Product checkDeliveryAvailability() {
        boolean isDeliveryAvailableInPinCode = ProductDescPage.createInstance()
                                                              .isDeliveryAvailableInPinCode();
        Assert.assertTrue(isDeliveryAvailableInPinCode,
                "Delivery is NOT avilable for the selected product in the provided PinCode");
        return this;
    }

    @Step
    public ShoppingBag addProductFromWishlistToShoppingBag() {
        WishListPage wishListPage = ProductDescPage.createInstance()
                                                   .navigateToWishlist();
        Assert.assertTrue(wishListPage.isProductAddedToWishList(), "Product added is NOT found in Wishlist");
        wishListPage.moveProductToBag()
                    .navigateToBag();
        return ShoppingBag.getInstance();
    }

    @Step
    public ShoppingBag addProductFromPDPToShoppingBag() {
        boolean isProductPresentInBag = ProductDescPage.createInstance()
                                                       .addProductMoveToBag()
                                                       .isProductPresentInBag();
        Assert.assertTrue(isProductPresentInBag, "Product NOT added in Bag");
        return ShoppingBag.getInstance();
    }

    @Step
    public ShoppingBag selectBestPriceAddProductToBagAndGoToBag() {
        ProductDescPage productDescriptionPage = ProductDescPage.createInstance()
                                                                .tapForBestPrice();
        Assert.assertTrue(productDescriptionPage.isTapForBestPriceSuccessful(),
                "Best price description is not displayed");
        boolean isProductPresentInBag = productDescriptionPage.addProductMoveToBag()
                                                              .isProductPresentInBag();
        Assert.assertTrue(isProductPresentInBag, "Product NOT added in Bag");
        return ShoppingBag.getInstance();
    }

    @Step
    public ShoppingBag selectSizeAddToBagAndMoveToShoppingBag() {
        ProductDescPage productDescPage = ProductDescPage.createInstance()
                                                         .selectSizeAndAddToBag();
        boolean isProductAvailableInBag = productDescPage.isProductAddedToBag();
        Assert.assertTrue(isProductAvailableInBag, "Product NOT added to Bag");
        productDescPage.goToBag();
        return ShoppingBag.getInstance();
    }

    @Step
    public Product filterSearchResult() {
        ProductListPage productListPage = ProductListPage.createInstance();
        Assert.assertTrue(productListPage.isProductListDisplayed(), "Failed to display the product list");
        ProductDescPage productDescriptionPage = productListPage.applyFilter("filterOne")
                                                                .selectFirstProductFromListPage();
        Assert.assertTrue(productDescriptionPage.isProductNameAvailable(),
                "Failed to navigate to Product Description Page");
        //TODO - 'filterOne' needs to be added in testdata.ini
        return this;
    }

    @Step
    public Product navigateThroughImagesOfProduct() {
        Assert.assertTrue(ProductDescPage.createInstance()
                                         .allThumbnailImagesAvailableInPDP(),
                "Thumbnail Images are not available in PDP");
        return this;
    }

    @Step
    public Product viewSimilarProducts() {
        ProductDescPage.createInstance()
                       .viewSimilarProducts();
        return this;
    }

    @Step
    public Product verifyProductDetailsInPDP() {
        /// TODO - need to implement it in a better way
        HashMap productDetails = ProductDescPage.createInstance()
                                                .getProductDetails();
        boolean isProductNameAvailable = productDetails.get("Name") != null;
        boolean isSellingPriceAvailable = productDetails.get("Selling Price") != null;
        boolean isProductCodeCorrect = ((String) productDetails.get("ProductCodeActual")).equals(
                (String) productDetails.get("ProductCodeExpected"));
        Assert.assertTrue((isProductNameAvailable && isSellingPriceAvailable && isProductCodeCorrect),
                String.format("Product Details are not available in PDP \n%s", productDetails.toString()));
        return this;
    }

    @Step
    public ShoppingBag viewSizeChartAndSelectSizeAddToBagAndGoToShoppingBag() {
        ProductDescPage productDescriptionPage = ProductDescPage.createInstance()
                                                                .viewSizeChart();
        boolean isProductAvailableInBag = productDescriptionPage.selectSizeAndAddToBag()
                                                                .isProductAddedToBag();
        Assert.assertTrue(isProductAvailableInBag, "Product NOT added to Bag");
        productDescriptionPage.goToBag();
        return ShoppingBag.getInstance();
    }

    @Step
    public Product viewSizeChartAndTapForBestPrice() {
        ProductDescPage productDescriptionPage = ProductDescPage.createInstance()
                                                                .viewSizeChart();
        Assert.assertTrue(productDescriptionPage.isSizeChartLinkAvailable(), "Size chart link is not available");
        productDescriptionPage.tapForBestPrice();
        Assert.assertTrue(productDescriptionPage.isTapForBestPriceSuccessful(),
                "Best price description is not displayed");
        return this;
    }

    @Step
    public Product saveProductToWishlistFromListPage() {
        boolean isProductSavedFromListPage = ProductListPage.createInstance()
                                                            .saveToWishlistFromListPage()
                                                            .isProductSaved();
        Assert.assertTrue(isProductSavedFromListPage, "Product is NOT Saved to Wishlist");
        return this;
    }

    @Step
    public Product addProductToBagFromListPage() {
        boolean isProductAddedToBagFromListPage = ProductListPage.createInstance()
                                                            .addToBagFromListPage()
                                                            .isProductAddedToBag();
        Assert.assertTrue(isProductAddedToBagFromListPage, "Product is NOT added to Bag");
        return this;
    }
}
