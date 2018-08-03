package com.myntra.core.pages.MobileWeb;

import com.myntra.core.pages.ProductDescPage;
import com.myntra.core.pages.ProductListPage;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.HashMap;

public class MobileWebProductListPage extends ProductListPage {

    @Step
    @Override
    public ProductDescPage selectFirstProductFromListPage() {
        utils.click(getLocator("lnkFirstProduct"), true);
        return ProductDescPage.createInstance();
    }

    @Step
    @Override
    public ProductListPage saveToWishlistFromListPage() {
        utils.wait(ExpectedConditions.invisibilityOfElementLocated(getLocator("btnApply")), 10);
        utils.wait(ExpectedConditions.elementToBeClickable(getLocator("btnSave")));
        utils.click(getLocator("btnSave"), true);
        return this;
    }

    @Step
    @Override
    public boolean isProductSaved() {
        utils.wait(ExpectedConditions.elementToBeClickable(getLocator("btnSaved")));
        return (utils.isElementPresent(getLocator("btnSaved"), 10));
    }

    @Step
    private boolean isDiscountPriceAvailable() {
        return (utils.isElementPresent(getLocator("txaDiscount"), 10));
    }

    @Step
    @Override
    public HashMap<String, String> getProductDetails() {
        HashMap<String, String> productDetails = new HashMap<>();
        WebElement firstProduct = utils.findElement(getLocator("lstAllProducts"));
        if (isDiscountPriceAvailable()) {
            productDetails.put("Selling Price", firstProduct.findElement(getLocator("txaSellingPriceIfStrikedPriceAvailable"))
                                                            .getText()
                                                            .split(" ")[0]);
            productDetails.put("Striked Price", utils.findElement(getLocator("txaStrikedPrice"))
                                                     .getText());

            productDetails.put("Product Discount", utils.findElement(getLocator("txaDiscount"))
                                                        .getText());
        } else {
            productDetails.put("Selling Price", firstProduct.findElement(getLocator("txaSellingPrice"))
                                                            .getText()
                                                            .split(" ")[0]);
        }
        testExecutionContext.addTestState("productDetailsInPLP", productDetails);
        return productDetails;
    }
}

