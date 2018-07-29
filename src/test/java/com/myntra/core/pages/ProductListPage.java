package com.myntra.core.pages;

import com.myntra.core.pages.Desktop.DesktopProductListPage;
import com.myntra.core.pages.MobileWeb.MobileWebProductListPage;
import com.myntra.core.pages.NativeAndroid.NativeAndroidProductListPage;
import com.myntra.core.pages.NativeIOS.NativeIOSProductListPage;
import com.myntra.core.utils.DynamicEnhancer;
import com.myntra.core.utils.DynamicLogger;
import com.myntra.utils.test_utils.Assert;
import io.qameta.allure.Step;
import org.apache.commons.lang3.NotImplementedException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public abstract class ProductListPage extends Page {
    public static ProductListPage createInstance() {
        ProductListPage derivedProductListPage;
        switch (CHANNEL) {
            case NATIVE_ANDROID:
                derivedProductListPage = (ProductListPage) DynamicEnhancer.create(NativeAndroidProductListPage.class,
                        new DynamicLogger());
                break;

            case NATIVE_IOS:
                derivedProductListPage = (ProductListPage) DynamicEnhancer.create(NativeIOSProductListPage.class,
                        new DynamicLogger());
                break;

            case DESKTOP_WEB:
                derivedProductListPage = (ProductListPage) DynamicEnhancer.create(DesktopProductListPage.class,
                        new DynamicLogger());
                break;

            case MOBILE_WEB:
                derivedProductListPage = (ProductListPage) DynamicEnhancer.create(MobileWebProductListPage.class,
                        new DynamicLogger());
                break;

            default:
                throw new NotImplementedException("Invalid Channel");
        }
        return derivedProductListPage;
    }

    @Step
    public abstract ProductDescPage selectFirstProductFromListPage();

    @Step
    protected ProductListPage filterUsingDiscount() {
        utils.click(getLocator("btnFilter"), true);
        utils.click(getLocator("btnDiscountInFilter"), true);
        utils.click(getLocator("chkFilterOption"), true);
        utils.click(getLocator("btnApply"), true);
        return this;
    }

    @Step
    protected ProductListPage filterUsingGender() {
        throw new NotImplementedException(getClass().getSimpleName() + "-" + new Object() {
        }.getClass()
         .getEnclosingMethod()
         .getName() + " - NOT YET IMPLEMENTED");
    }

    @Step
    protected ProductListPage filterUsingCategories() {
        throw new NotImplementedException(getClass().getSimpleName() + "-" + new Object() {
        }.getClass()
         .getEnclosingMethod()
         .getName() + " - NOT YET IMPLEMENTED");
    }

    @Override
    public String pageName() {
        return ProductListPage.class.getSimpleName();
    }

    @Step
    public ProductListPage navigateToWishlist() {
        throw new NotImplementedException(getClass().getSimpleName() + "-" + new Object() {
        }.getClass()
         .getEnclosingMethod()
         .getName() + " - NOT YET IMPLEMENTED");
    }

    @Step
    public ProductListPage sortSearchResult() {
        utils.click(getLocator("btnSort"), true);
        List<WebElement> sortOptions = getDriver().findElements(getLocator("lstSort"));
        boolean sort = false;
        for (WebElement sortOption : sortOptions) {
            String sortRequired = getTestData().get("sortRequired");
            if ((sortOption.getText()).equals(sortRequired)) {
                sortOption.click();
                sort = true;
                break;
            }
        }
        if (!sort) {
            Assert.assertTrue(sort, "Sort Required is not available");
        }
        return this;
    }

    @Step
    public boolean isProductListDisplayed() {
        utils.wait(ExpectedConditions.elementToBeClickable(getLocator("allProductList")));
        List<WebElement> saveButtons = getDriver().findElements(getLocator("allProductList"));
        return (saveButtons.size() > 0);
    }

    @Step
    public ProductListPage applyFilter(String filterType) {
        switch (getTestData().get(filterType)
                             .toLowerCase()) {
            case "discount":
                return filterUsingDiscount();
            case "categories":
                return filterUsingCategories();
            case "gender":
                return filterUsingGender();
            default:
                throw new NotImplementedException("Invalid filter option");
        }
    }

    @Step
    public ProductListPage saveToWishlistFromListPage() {
        utils.moveToElement(getLocator("lstAllProducts"));
        utils.click(getLocator("btnSave"));
        return this;
    }

    @Step
    public boolean isProductSaved() {
        utils.moveToElement(getLocator("lstAllProducts"));
        return (utils.isElementPresent(getLocator("btnSaved"), 2));
    }

    @Step
    public ProductListPage addToBagFromListPage() {
        utils.moveToElement(getLocator("lstAllProducts"));
        utils.click(getLocator("btnAddToBag"),true);
        utils.click(getLocator("btnSizes"),true);
        return this;
    }

    @Step
    public boolean isProductAddedToBag() {
        return(utils.isElementPresent(getLocator("txaAddToBagConfirmationMessage"),1));
    }
}
