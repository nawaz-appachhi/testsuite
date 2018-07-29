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
        utils.click(getLocator("lstAllProducts"), true);
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
        if (!isDiscountavailable) {
            soft.assertTrue(isDiscountavailable, "Discount filter is not available");
        }
        return this;
    }

    @Step
    @Override
    public ProductListPage filterUsingGender() {
        String genderType = getTestData().get("Gender");
        String locatorName = "lnkFilterBy" + genderType;
        boolean isGenderFilterAvailable = utils.isElementPresent(getLocator(locatorName), 2);
        if (isGenderFilterAvailable) {
            utils.click(getLocator(locatorName));
        } else {
            soft.assertTrue(isGenderFilterAvailable, genderType + " filter option is not displayed");
        }
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
        if (!isCategoriesFilterAvailable) {
            soft.assertTrue(isCategoriesFilterAvailable, "Categories filter is not available");
        }
        return this;
    }

    @Step
    @Override
    public boolean isProductListDisplayed() {
        utils.wait(ExpectedConditions.elementToBeClickable(getLocator("allProductList")));
        List<WebElement> saveButtons = getDriver().findElements(getLocator("allProductList"));
        return (saveButtons.size() > 0);
    }

}
