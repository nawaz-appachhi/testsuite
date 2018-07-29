package com.myntra.core.pages.NativeAndroid;

import com.myntra.core.pages.HomePage;
import com.myntra.core.pages.ShoppingBagPage;
import com.myntra.core.pages.WishListPage;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class NativeAndroidWishListPage extends WishListPage {

    @Step
    @Override
    public WishListPage moveProductToBag() {
        utils.click(getLocator("lnkMoveToBag"), true);
        selectASize();
        utils.click(getLocator("btnDone"), true);
        return this;
    }

    @Step
    private WishListPage selectASize() {
        List<WebElement> listOfSizeElement = utils.findElements(getLocator("btnSelectSize"));
        for (WebElement eachSizeElementInListOfSizeElement : listOfSizeElement) {
            if (eachSizeElementInListOfSizeElement.isDisplayed()) {
                eachSizeElementInListOfSizeElement.click();
                break;
            }
        }
        return this;
    }

    @Step
    @Override
    public HomePage emptyWishlist() {
        removeAllProductsFromWishlist();
        getDriver().navigate()
                   .back();
        return HomePage.createInstance();
    }

    @Step
    @Override
    public WishListPage removeAllProductsFromWishlist() {
        while (!isWishlistEmpty()) {
            /// TODO - split into separate, specific methods
            if (utils.isElementPresent(getLocator("icoRemoveProduct"), 2)) {
                utils.click(getLocator("icoRemoveProduct"), true);
            }
        }
        return this;
    }

    @Step
    @Override
    public boolean isProductAddedToWishList() {
        utils.wait(ExpectedConditions.visibilityOfElementLocated(getLocator("lblWishlistPageTitle")));
        return (utils.isElementPresent(getLocator("lnkMoveToBag"), 5));
    }

    @Step
    @Override
    public ShoppingBagPage navigateToBag() {
        utils.click(getLocator("tlbBag"), true);
        return ShoppingBagPage.createInstance();
    }

}
