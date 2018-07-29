package com.myntra.core.pages;

import com.myntra.core.pages.Desktop.DesktopProductDescPage;
import com.myntra.core.pages.MobileWeb.MobileWebProductDescPage;
import com.myntra.core.pages.NativeAndroid.NativeAndroidProductDescPage;
import com.myntra.core.pages.NativeIOS.NativeIOSProductDescPage;
import com.myntra.core.utils.DynamicEnhancer;
import com.myntra.core.utils.DynamicLogger;
import com.myntra.ui.Direction;
import io.qameta.allure.Step;
import org.apache.commons.lang3.NotImplementedException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.HashMap;
import java.util.List;

public abstract class ProductDescPage extends Page {

    public static ProductDescPage createInstance() {
        ProductDescPage derivedProductDescPage;
        switch (CHANNEL) {
            case NATIVE_ANDROID:
                derivedProductDescPage = (ProductDescPage) DynamicEnhancer.create(NativeAndroidProductDescPage.class,
                        new DynamicLogger());
                break;

            case NATIVE_IOS:
                derivedProductDescPage = (ProductDescPage) DynamicEnhancer.create(NativeIOSProductDescPage.class,
                        new DynamicLogger());
                break;

            case DESKTOP_WEB:
                derivedProductDescPage = (ProductDescPage) DynamicEnhancer.create(DesktopProductDescPage.class,
                        new DynamicLogger());
                break;

            case MOBILE_WEB:
                derivedProductDescPage = (ProductDescPage) DynamicEnhancer.create(MobileWebProductDescPage.class,
                        new DynamicLogger());
                break;

            default:
                throw new NotImplementedException("Invalid Channel");
        }
        return derivedProductDescPage;
    }

    @Override
    public String pageName() {
        return ProductDescPage.class.getSimpleName();
    }

    @Step
    public abstract ShoppingBagPage addProductMoveToBag();

    @Step
    public WishListPage navigateToWishlist() {
        utils.click(getLocator("icoWishlist"), true);
        return WishListPage.createInstance();
    }

    @Step
    public ProductDescPage selectSizeAndAddToBag() {
        throw new NotImplementedException(getClass().getSimpleName() + "-" + new Object() {
        }.getClass()
         .getEnclosingMethod()
         .getName() + " - NOT YET IMPLEMENTED");
    }

    @Step
    public boolean isProductSavedToWishList() {
        return (utils.isElementPresent(getLocator("btnSaved"), 5));
    }

    @Step
    public boolean isProductAddedToBag() {
        return (utils.isElementPresent(getLocator("btnGoToBag"), 5));
    }

    @Step
    public boolean isDeliveryAvailableInPinCode() {
        utils.scroll(Direction.DOWN, 3);
        utils.waitForElementToBeVisible(getLocator("btnEnterPinCode"));
        utils.click(getLocator("btnEnterPinCode"), true);
        utils.sendKeys(getLocator("txtPinCode"), getTestData().get("Pincode"));
        utils.click(getLocator("btnCheckDeliveryOption"), true);
        if (utils.isElementPresent(getLocator("txaDeliveryconfirmation"), 2)) {
            utils.scroll(Direction.UP, 3);
            return true;
        } else {
            return false;
        }
    }

    @Step
    public boolean isProductDisplayed() {
        return (utils.isElementPresent(getLocator("lblProductName"), 2));
    }

    @Step
    public ProductDescPage saveProductToWishlist() {
        utils.click(getLocator("btnSave"), true);
        return this;
    }

    @Step
    public ProductDescPage tapForBestPrice() {
        utils.wait(ExpectedConditions.elementToBeClickable(getLocator("lnkTapBestPrice")));
        utils.click(getLocator("lnkTapBestPrice"));
        return this;
    }

    @Step
    public boolean isTapForBestPriceSuccessful() {
        return (utils.isElementPresent(getLocator("txaBestPriceDetails"), 2));
    }

    @Step
    public ShoppingBagPage goToBag() {
        utils.waitForElementToBeVisible(getLocator("btnGoToBag"));
        utils.click(getLocator("btnGoToBag"));
        return ShoppingBagPage.createInstance();
    }

    @Step
    public boolean isProductNameAvailable() {
        throw new NotImplementedException(this.getClass()
                                              .getSimpleName() + "-" + new Object() {
        }.getClass()
         .getEnclosingMethod()
         .getName() + " - NOT YET IMPLEMENTED");
    }

    @Step
    public boolean isProductBrandAvailable() {
        throw new NotImplementedException(this.getClass()
                                              .getSimpleName() + "-" + new Object() {
        }.getClass()
         .getEnclosingMethod()
         .getName() + " - NOT YET IMPLEMENTED");
    }

    @Step
    public boolean isProductPriceAvailable() {
       return utils.isElementPresent(getLocator("lblProductPrice"),5);
    }

    @Step
    public boolean isStrikedPriceAvailable() {
        return utils.isElementPresent(getLocator("lblStrikedPrice"),5);
    }

    @Step
    public boolean isProductDiscountAvailable() {
        return utils.isElementPresent(getLocator("lblProductDiscount"),5);
    }

    @Step
    public boolean isProductDetailsAvailable() {
        throw new NotImplementedException(this.getClass()
                                              .getSimpleName() + "-" + new Object() {
        }.getClass()
         .getEnclosingMethod()
         .getName() + " - NOT YET IMPLEMENTED");
    }

    @Step
    public boolean isSimilarProductAvailable() {
        throw new NotImplementedException(this.getClass()
                                              .getSimpleName() + "-" + new Object() {
        }.getClass()
         .getEnclosingMethod()
         .getName() + " - NOT YET IMPLEMENTED");
    }

    @Step
    public ProductDescPage viewSimilarProducts() {
        utils.scrollTillElementVisible_m(getLocator("lnkMoreProducts"));
        utils.click(getLocator("lnkMoreProducts"), true);
        goBack();
        return this;
    }

    @Step
    public boolean isProductImagePresentInPDP() {
        return (utils.isElementPresent(getLocator("imgLargeThumbnail"), 5));
    }

    @Step
    public ProductDescPage viewSizeChart() {
        utils.click(getLocator("lnkSizeChart"), true);
        utils.click(getLocator("btnCloseSizeChart"), true);
        return this;
    }

    @Step
    public boolean isSizeChartLinkAvailable() {
        return (utils.isElementPresent(getLocator("lnkSizeChart"), 5));
    }

    @Step
    public boolean allThumbnailImagesAvailableInPDP() {
        boolean imageAvailable = true;
        List<WebElement> smallThumbnails = getDriver().findElements(getLocator("imgSmallThumbnails"));
        for (WebElement smallThumbnail : smallThumbnails) {
            smallThumbnail.click();
            imageAvailable = imageAvailable && isProductImagePresentInPDP();
        }
        return imageAvailable;
    }

    @Step
    public HashMap<String, String> getProductDetails() {
        HashMap<String, String> productDetails = new HashMap<String, String>();
        productDetails.put("Name", utils.findElement(getLocator("lblProductName"))
                                        .getText());
        productDetails.put("Selling Price", utils.findElement(getLocator("lblSellingPrice"))
                                                 .getText());
        productDetails.put("ProductCodeActual", utils.findElement(getLocator("lblProductCode"))
                                                     .getText());
        productDetails.put("ProductCodeExpected", getTestData().get("SearchItem"));
        testExecutionContext.addTestState("productDetails", productDetails);
        soft.assertTrue(isProductPriceAvailable(), "Product price is not present");
        soft.assertTrue(isStrikedPriceAvailable(), "Striked price is not present");
        soft.assertTrue(isProductDiscountAvailable(), "Product discount is not present");
        return productDetails;
    }
}