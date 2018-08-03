package com.myntra.core.pages.NativeAndroid;

import com.myntra.core.pages.ProductDescPage;
import com.myntra.core.pages.ProductListPage;
import com.myntra.utils.test_utils.Assert;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.HashMap;
import java.util.List;


public class NativeAndroidProductListPage extends ProductListPage {

    @Step
    @Override
    public ProductDescPage selectFirstProductFromListPage() {
        utils.click(getLocator("lnkFirstProduct"));
        return ProductDescPage.createInstance();
    }

    @Step
    @Override
    public boolean isProductListDisplayed() {
        return utils.isElementPresent(getLocator("allProductList"), 10);
    }

    @Step
    @Override
    protected ProductListPage filterUsingCategories() {
        utils.click(getLocator("btnFilter"), true);
        utils.click(getLocator("btnFilterOptionCategories"), true);
        utils.click(getLocator("chkFilterOption"), true);
        utils.isElementPresent(getLocator("btnSave"), 10);
        utils.waitForElementToBeVisible(getLocator("chkFilterOption"));
        utils.click(getLocator("btnApply"), true);
        return this;
    }

    @Step
    @Override
    public ProductListPage saveToWishlistFromListPage() {
        utils.wait(ExpectedConditions.elementToBeClickable(getLocator("btnSave")));
        utils.click(getLocator("btnSave"));
        return this;
    }

    @Step
    @Override
    public boolean isProductSaved() {
        return (utils.isElementPresent(getLocator("btnSaved"), 10));
    }

    @Step
    @Override
    public HashMap<String, String> getProductDetails() {
        HashMap<String, String> productDetails = new HashMap<>();
        if (utils.isElementPresent(getLocator("txaSellingPrice"), 5)) {
            productDetails.put("Selling Price", utils.findElement(getLocator("txaSellingPrice"))
                                                     .getText()
                                                     .replace(",", "")
                                                     .substring(1)
                                                     .trim());
        }
        if (utils.isElementPresent(getLocator("txaStrikedPrice"), 4)) {
            productDetails.put("Striked Price", utils.findElement(getLocator("txaStrikedPrice"))
                                                     .getText());
        }
        if (utils.isElementPresent(getLocator("txaDiscount"), 2)) {
            productDetails.put("Product Discount", utils.findElement(getLocator("txaDiscount"))
                                                        .getText());
        }
        testExecutionContext.addTestState("productDetailsInPLP", productDetails);
        return productDetails;
    }

    @Step
    @Override
    protected ProductListPage filterUsingDiscount() {
        utils.click(getLocator("btnFilter"), true);
        utils.click(getLocator("btnDiscountInFilter"), true);
        utils.waitForElementToBeVisible(getLocator("chkFilterOption"));
        utils.click(getLocator("chkFilterOption"), true);
        utils.waitForElementToBeVisible(getLocator("chkFilterOption"));
        utils.click(getLocator("btnApply"), true);
        return this;
    }

    @Step
    @Override
    public ProductListPage sortSearchResult() {
        utils.click(getLocator("btnSort"), true);
        List<WebElement> sortOptions = utils.findElements(getLocator("lstSort"));
        boolean sort = false;
        for (WebElement sortOption : sortOptions) {
            String sortRequired = (String) getTestData().getAdditionalProperties()
                                                        .get("sortRequired");
            if ((sortOption.getText()).equals(sortRequired)) {
                sortOption.click();
                sort = true;
                utils.waitForElementToBeVisible(getLocator("btnSort"));
                break;
            }
        }
        Assert.assertTrue(sort, "Sort Required is not available");
        return this;
    }
}