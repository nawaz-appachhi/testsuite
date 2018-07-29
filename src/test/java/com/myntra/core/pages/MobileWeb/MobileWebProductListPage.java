package com.myntra.core.pages.MobileWeb;

import com.myntra.core.pages.ProductDescPage;
import com.myntra.core.pages.ProductListPage;
import com.myntra.ui.Direction;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class MobileWebProductListPage extends ProductListPage {

    @Step
    @Override
    public ProductDescPage selectFirstProductFromListPage() {
        List<WebElement> resultList = getDriver().findElements(getLocator("allProductList"));
        String productCode = getTestData().get("ProductCode");
        boolean isProductFound = false;
        for (WebElement result : resultList) {
            WebElement link = result.findElement(By.tagName("a"));
            String hrefString = link.getAttribute("href");
            if (hrefString.contains(productCode)) {
                utils.click(link, true);
                isProductFound = true;
                break;
            }
            utils.scroll(Direction.DOWN, 40L);
        }
        if (!isProductFound) {
            utils.scroll(Direction.UP, 5);
            utils.wait(ExpectedConditions.elementToBeClickable(getLocator("lnkFirstProduct")));
            getDriver().findElement(getLocator("lnkFirstProduct")).click();
        }
        return ProductDescPage.createInstance();
    }
}
