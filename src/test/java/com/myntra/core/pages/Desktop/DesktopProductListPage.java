package com.myntra.core.pages.Desktop;

import com.myntra.core.pages.ProductDescPage;
import com.myntra.core.pages.ProductListPage;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class DesktopProductListPage extends ProductListPage {

    @Step
    @Override
    public ProductDescPage selectFirstProductFromListPage() {
        utils.moveToElement(getLocator("lstAllProducts"));
        utils.click(getLocator("lstAllProducts"));
        return ProductDescPage.createInstance();
    }

    @Step
    @Override
    public ProductListPage filterUsingDiscount() {
        boolean isDiscountavailable = false;
        if (utils.isElementPresent(getLocator("btnDiscountInFilter"), 2)) {
            List<WebElement> allDiscountsAvailable = utils.findElements(getLocator("rdoDiscountFilterOptions"));
            for (WebElement discount : allDiscountsAvailable) {
                isDiscountavailable = true;
                discount.click();
                break;
            }
        }
        soft.assertTrue(isDiscountavailable, "Discount filter is not available");
        utils.wait(ExpectedConditions.elementToBeClickable(getLocator("lnkClearAll")));
        return this;
    }

    @Step
    @Override
    public ProductListPage filterUsingGender() {
        String genderType = (String) getTestData().getAdditionalProperties()
                                                  .get("Gender");
        String locatorName = "lnkFilterBy" + genderType;
        boolean isGenderFilterAvailable = utils.isElementPresent(getLocator(locatorName), 2);
        soft.assertTrue(isGenderFilterAvailable, genderType + " filter option is not displayed");
        if (isGenderFilterAvailable) {
            utils.click(getLocator(locatorName));
        }
        utils.wait(ExpectedConditions.elementToBeClickable(getLocator("lnkClearAll")));
        return this;
    }

    @Step
    @Override
    public ProductListPage filterUsingCategories() {
        boolean isCategoriesFilterAvailable = false;
        if (utils.isElementPresent(getLocator("btnFilterOptionCategories"), 2)) {
            List<WebElement> allCategoriesAvailable = utils.findElements(getLocator("chkCategoriesFilterOptions"));
            for (WebElement category : allCategoriesAvailable) {
                isCategoriesFilterAvailable = true;
                category.click();
                break;
            }
        }
        soft.assertTrue(isCategoriesFilterAvailable, "Categories filter is not available");
        utils.wait(ExpectedConditions.elementToBeClickable(getLocator("lnkClearAll")));
        return this;
    }

    @Step
    @Override
    public boolean isProductListDisplayed() {
        utils.wait(ExpectedConditions.elementToBeClickable(getLocator("allProductList")));
        List<WebElement> saveButtons = getDriver().findElements(getLocator("allProductList"));
        return (!saveButtons.isEmpty());
    }

}
