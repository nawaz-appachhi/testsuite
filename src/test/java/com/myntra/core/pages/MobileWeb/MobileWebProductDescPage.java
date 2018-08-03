package com.myntra.core.pages.MobileWeb;

import com.myntra.core.pages.ProductDescPage;
import com.myntra.core.pages.ShoppingBagPage;
import com.myntra.core.pages.WishListPage;
import com.myntra.ui.Direction;
import com.myntra.utils.test_utils.Assert;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class MobileWebProductDescPage extends ProductDescPage {

    @Step
    @Override
    public ProductDescPage selectSizeAndAddToBag() {
        if (utils.isElementPresent(getLocator("lnkSizeChart"), 3)) {
            utils.click(getLocator("btnSelectSize"), true);
            utils.click(getLocator("btnAddToBag"), true);
        } else {
            utils.click(getLocator("btnAddToBag"), true);
            utils.click(getLocator("btnSelectSize"), true);
            utils.click(getLocator("btnDone"), true);
        }
        return this;
    }

    @Step
    @Override
    public ShoppingBagPage addProductMoveToBag() {
        selectSizeAndAddToBag();
        return goToBag();
    }

    @Step
    @Override
    public ShoppingBagPage goToBag() {
        utils.wait(ExpectedConditions.elementToBeClickable(getLocator("btnGoToBag")), 10);
        utils.click(getLocator("btnGoToBag"), true);
        return ShoppingBagPage.createInstance();
    }

    @Step
    @Override
    public ProductDescPage tapForBestPrice() {
        scrollTillElementVisible(getLocator("lnkTapBestPrice"));
        utils.wait(ExpectedConditions.elementToBeClickable(getLocator("lnkTapBestPrice")));
        utils.click(getLocator("lnkTapBestPrice"), true);
        scrollTillElementVisible(getLocator("btnAddToBag"));
        Assert.assertTrue(utils.isElementPresent(getLocator("btnAddToBag"), 30), String.format("Page - %s - NOT Loaded", getClass().getSimpleName()));
        return this;
    }

    @Step
    @Override
    public WishListPage navigateToWishlist() {
        utils.click(getLocator("lnkWishlist"), true);
        return WishListPage.createInstance();
    }

    @Step
    @Override
    public boolean isDeliveryAvailableInPinCode() {
        utils.scroll(Direction.DOWN, 5);
        utils.waitForElementToBeVisible(getLocator("txaDeliveryAndServices"));
        if (utils.isElementPresent(getLocator("lnkChangePin"), 1)) {
            utils.click(getLocator("lnkChangePin"), true);
        }
        utils.click(getLocator("btnEnterPinCode"), true);
        utils.sendKeys(getLocator("txtPinCode"), getAddressTestData().getPincode());
        utils.click(getLocator("btnCheckDeliveryOption"), true);
        /// TODO - Need to replace scroll method with Utils scroll method once the issue is fixed
        ((JavascriptExecutor) getDriver()).executeScript("scroll(0,-300)");//utils.scroll(Direction.UP,5) is not working

        // TODO return based on the delivery available message
        return true;
    }

    @Step
    @Override
    public boolean isProductImagePresentInPDP() {
        return (utils.isElementPresent(getLocator("imgSmallThumbnails"), 5));
    }

    @Step
    @Override
    public boolean areAllThumbnailImagesAvailableInPDP() {
        List<WebElement> allImages = utils.findElements(getLocator("imgSmallThumbnails"));
        smallImgeVerification(allImages);
        zoomedImgeVerification(allImages);
        return isProductImagePresentInPDP();
    }

    @Step
    private ProductDescPage smallImgeVerification(List<WebElement> allImages) {
        for (WebElement image : allImages) {
            utils.click(image, true);
        }
        return this;
    }

    @Step
    private ProductDescPage zoomedImgeVerification(List<WebElement> allImages) {
        int imgCount = allImages.size();
        utils.click(By.xpath(String.format("//*[@class='carousel-box-container']/div[%s]//img[1][contains(@class,'img-loaded')]", imgCount)), true);
        for (WebElement image : allImages) {
            utils.click(getLocator("imgNavigateArrowR"), true);
        }
        utils.click(getLocator("closeZoomImage"), true);
        return this;
    }

    @Step
    @Override
    public ProductDescPage saveProductToWishlist() {
        // TODO Myntra team please remove this condition once bug is fixed; Jira ID:
        if (!utils.isElementPresent(getLocator("btnSave"), 5)) {
            utils.refreshPage();
        }
        utils.click(getLocator("btnSave"), true);
        return this;
    }
}
