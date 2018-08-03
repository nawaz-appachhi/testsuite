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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class ProductListPage extends Page {
    public static ProductListPage createInstance() {
        ProductListPage derivedProductListPage;
        switch (CHANNEL) {
            case NATIVE_ANDROID:
                derivedProductListPage = (ProductListPage) DynamicEnhancer.create(NativeAndroidProductListPage.class, new DynamicLogger());
                break;

            case NATIVE_IOS:
                derivedProductListPage = (ProductListPage) DynamicEnhancer.create(NativeIOSProductListPage.class, new DynamicLogger());
                break;

            case DESKTOP_WEB:
                derivedProductListPage = (ProductListPage) DynamicEnhancer.create(DesktopProductListPage.class, new DynamicLogger());
                break;

            case MOBILE_WEB:
                derivedProductListPage = (ProductListPage) DynamicEnhancer.create(MobileWebProductListPage.class, new DynamicLogger());
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
    public WishListPage navigateToWishlist() {
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
            String sortRequired = (String) getTestData().getAdditionalProperties()
                                                        .get("sortRequired");
            if ((sortOption.getText()).equals(sortRequired)) {
                sortOption.click();
                sort = true;
                break;
            }
        }
        Assert.assertTrue(sort, "Sort Required is not available");
        return this;
    }

    @Step
    public boolean isProductListDisplayed() {
        utils.wait(ExpectedConditions.elementToBeClickable(getLocator("allProductList")));
        List<WebElement> saveButtons = getDriver().findElements(getLocator("allProductList"));
        return (!saveButtons.isEmpty());
    }

    @Step
    public ProductListPage applyFilter(String filterType) {
        switch (((String) getTestData().getAdditionalProperties()
                                       .get(filterType)).toLowerCase()) {
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
        utils.wait(ExpectedConditions.elementToBeClickable(getLocator("btnSave")));
        utils.click(getLocator("btnSave"));
        return this;
    }

    @Step
    public boolean isProductSaved() {
        utils.moveToElement(getLocator("lstAllProducts"));
        return (utils.isElementPresent(getLocator("btnSaved"), 10));
    }

    @Step
    public ProductListPage addToBagFromListPage() {
        utils.moveToElement(getLocator("lstAllProducts"));
        utils.wait(ExpectedConditions.elementToBeClickable(getLocator("btnAddToBag")));
        utils.click(getLocator("btnAddToBag"), true);
        utils.click(getLocator("btnSizes"), true);
        return this;
    }

    @Step
    public boolean isProductAddedToBag() {
        return (utils.isElementPresent(getLocator("txaAddToBagConfirmationMessage"), 1));
    }

    @Step
    public Map<String, String> getProductDetails() {
        HashMap<String, String> productDetails = new HashMap<>();
        WebElement firstProduct = utils.findElement(getLocator("lstAllProducts"));
        if (firstProduct.findElement(getLocator("txaDiscount"))
                        .getText()
                        .contains("OFF)")) {
            productDetails.put("Selling Price", firstProduct.findElement(getLocator("txaSellingPriceIfStrikedPriceAvailable"))
                                                            .getText()
                                                            .split(" ")[1]);
            productDetails.put("Striked Price", utils.findElement(getLocator("txaStrikedPrice"))
                                                     .getText());

        } else {
            productDetails.put("Selling Price", firstProduct.findElement(getLocator("txaSellingPrice"))
                                                            .getText()
                                                            .split(" ")[1]);
        }
        if (firstProduct.findElement(getLocator("txaDiscount"))
                        .getText()
                        .contains("OFF")) {
            productDetails.put("Product Discount", utils.findElement(getLocator("txaDiscount"))
                                                        .getText());
        }
        testExecutionContext.addTestState("productDetailsInPLP", productDetails);
        return productDetails;
    }

    @Step
    public ProductListPage navigateToPromotion() {
        throw new NotImplementedException(getClass().getSimpleName() + "-" + new Object() {
        }.getClass()
         .getEnclosingMethod()
         .getName() + " - NOT YET IMPLEMENTED");
    }

    @Step
    public boolean isPromotionAvailableInPLP() {
        throw new NotImplementedException(getClass().getSimpleName() + "-" + new Object() {
        }.getClass()
         .getEnclosingMethod()
         .getName() + " - NOT YET IMPLEMENTED");
    }

    @Step
    public ProductListPage selectFreeGiftUnderPromotions() {
        throw new NotImplementedException(getClass().getSimpleName() + "-" + new Object() {
        }.getClass()
         .getEnclosingMethod()
         .getName() + " - NOT YET IMPLEMENTED");
    }

    @Step
    public ProductListPage selectConditionalDiscountUnderPromotions() {
        throw new NotImplementedException(getClass().getSimpleName() + "-" + new Object() {
        }.getClass()
         .getEnclosingMethod()
         .getName() + " - NOT YET IMPLEMENTED");
    }

    @Step
    public ProductListPage selectStaggeredComboUnderPromotions() {
        throw new NotImplementedException(getClass().getSimpleName() + "-" + new Object() {
        }.getClass()
         .getEnclosingMethod()
         .getName() + " - NOT YET IMPLEMENTED");
    }

    @Step
    public ProductListPage selectBOGOUnderPromotions() {
        throw new NotImplementedException(getClass().getSimpleName() + "-" + new Object() {
        }.getClass()
         .getEnclosingMethod()
         .getName() + " - NOT YET IMPLEMENTED");
    }

    @Step
    public ProductDescPage selectProductAloneNotApplicableForFreeGift() {
        throw new NotImplementedException(getClass().getSimpleName() + "-" + new Object() {
        }.getClass()
         .getEnclosingMethod()
         .getName() + " - NOT YET IMPLEMENTED");
    }

    @Step
    public ProductDescPage selectProductNotApplicableForBOGO() {
        throw new NotImplementedException(getClass().getSimpleName() + "-" + new Object() {
        }.getClass()
         .getEnclosingMethod()
         .getName() + " - NOT YET IMPLEMENTED");
    }

    @Step
    public ProductDescPage selectProductNotApplicableForConditionalDiscount() {
        throw new NotImplementedException(getClass().getSimpleName() + "-" + new Object() {
        }.getClass()
         .getEnclosingMethod()
         .getName() + " - NOT YET IMPLEMENTED");
    }

    @Step
    public ProductDescPage selectProductsToCompleteConditionalDiscount() {
        throw new NotImplementedException(getClass().getSimpleName() + "-" + new Object() {
        }.getClass()
         .getEnclosingMethod()
         .getName() + " - NOT YET IMPLEMENTED");
    }

    @Step
    public ProductDescPage selectProductsNotApplicableForConditionalDiscount() {
        throw new NotImplementedException(getClass().getSimpleName() + "-" + new Object() {
        }.getClass()
         .getEnclosingMethod()
         .getName() + " - NOT YET IMPLEMENTED");
    }

    @Step
    public ProductDescPage selectOneProductInConditionalDiscountProducts() {
        throw new NotImplementedException(getClass().getSimpleName() + "-" + new Object() {
        }.getClass()
         .getEnclosingMethod()
         .getName() + " - NOT YET IMPLEMENTED");
    }
}