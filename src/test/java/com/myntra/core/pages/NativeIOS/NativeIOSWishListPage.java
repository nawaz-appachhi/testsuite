package com.myntra.core.pages.NativeIOS;

import com.myntra.core.pages.ShoppingBagPage;
import com.myntra.core.pages.WishListPage;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;

import java.util.List;

public class NativeIOSWishListPage extends WishListPage {

    @Step
    @Override
    public WishListPage moveProductToBag() {
        utils.click(getLocator("lnkMoveToBag"), true);
        selectSize();
        utils.click(getLocator("btnDone"));
        return this;
    }

    @Step
    @Override
    public WishListPage removeAllProductsFromWishlist() {
        while (!isWishlistEmpty()) {
            utils.waitForElementToBeVisible(getLocator("icoRemoveProduct"));
            utils.click(getLocator("icoRemoveProduct"), true);
        }
        return this;
    }

    @Step
    @Override
    public boolean isWishlistEmpty() {
        utils.waitForElementToBeVisible(getLocator("lblEmptyWishlistMsg"));
        return utils.isElementPresent(getLocator("lblEmptyWishlistMsg"), 2);
    }

    @Step
    @Override
    public ShoppingBagPage navigateToBag() {
        utils.waitForElementToBeVisible(getLocator("tlbBag"));
        utils.click(getLocator("tlbBag"), true);
        handleOkPermission();
        return ShoppingBagPage.createInstance();
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
