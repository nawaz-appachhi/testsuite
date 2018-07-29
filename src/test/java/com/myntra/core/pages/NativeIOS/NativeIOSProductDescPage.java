package com.myntra.core.pages.NativeIOS;

import com.myntra.core.pages.ProductDescPage;
import com.myntra.core.pages.ShoppingBagPage;
import com.myntra.utils.test_utils.Assert;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;

import java.util.List;

public class NativeIOSProductDescPage extends ProductDescPage {

    @Step
    @Override
    public ShoppingBagPage addProductMoveToBag() {
        utils.findElement(getLocator("btnAddToBag")).click();
        utils.findElement(getLocator("btnSelectSize")).click();
        utils.findElement(getLocator("btnDone")).click();
        utils.findElement(getLocator("btnGoToBag")).click();
        return ShoppingBagPage.createInstance();
    }

    @Step
    @Override
    public ProductDescPage saveProductToWishlist() {
        utils.findElement(getLocator("btnSave")).click();
        return this;
    }

    @Step
    @Override
    public ProductDescPage tapForBestPrice() {
        utils.findElement(getLocator("lnkTapBestPrice")).click();
        Assert.assertTrue(utils.isElementPresent(getLocator("btnAddToBag"), 30), String.format("Page - %s - NOT Loaded", getClass().getSimpleName()));
        return this;
    }

    @Step
    @Override
    public ProductDescPage selectSizeAndAddToBag() {
        if (utils.isElementPresent(getLocator("btnAddToBag"), 5)) {
            utils.click(getLocator("btnAddToBag"), true);
            selectSize();
            utils.click(getLocator("btnDone"));
        }
        return this;
    }

    @Step
    private void selectSize() {
        List<WebElement> listOfSizeElement = utils.findElements(getLocator("btnSelectSize"), 5);
        for (WebElement eachSizeElementInListOfSizeElement : listOfSizeElement) {
            eachSizeElementInListOfSizeElement.click();
            break;
        }
    }
}

