package com.myntra.core.pages.NativeAndroid;

import com.myntra.core.pages.ProductDescPage;
import com.myntra.core.pages.ShoppingBagPage;
import com.myntra.core.pages.WishListPage;
import com.myntra.ui.Direction;
import com.myntra.utils.test_utils.Assert;
import io.appium.java_client.MobileBy;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.HashMap;
import java.util.List;

public class NativeAndroidProductDescPage extends ProductDescPage {

    String retryLocatorValue = "//android.widget.TextView[@text='Tap to retry']";

    @Step
    @Override
    protected void isLoaded() {
        boolean isPDPElementsPresent;
        if (!utils.isElementPresent(By.xpath("//android.widget.TextView[@text='Tap to retry']"), 5)) {
            isPDPElementsPresent = utils.isElementPresent(MobileBy.AccessibilityId("image_swipe"), 5) &&
                    utils.isElementPresent(By.id("com.myntra.android:id/ll_react_container"), 5) &&
                    utils.isElementPresent(MobileBy.AccessibilityId("product_head"), 5) &&
                    utils.isElementPresent(MobileBy.AccessibilityId("pdp_price"), 5) && utils.isElementPresent(getLocator("btnAddToBag"), 5);

            Assert.assertTrue(isPDPElementsPresent, String.format("Page - %s - NOT Loaded", getClass().getSimpleName()));
        }
    }

    @Step
    @Override
    protected void load() {
        int retryCount = 0;
        int maxRetryCount = 5;
        // TODO Myntra Team please remove this if condition; Once the bug is fixed Jira ID: VEGASF-588
        if (utils.isElementPresent(By.xpath(retryLocatorValue), 5)) {
            while (isTapToRetryAvailable() && (retryCount < maxRetryCount)) {
                utils.findElement(By.xpath(retryLocatorValue))
                     .click();
                retryCount++;
            }
        } else {
            uiAutomatorScrollByDescription("com.myntra.android:id/ll_react_container", "android.view.ViewGroup", "similar_icon");
        }
    }

    @Step
    private boolean isTapToRetryAvailable() {
        return utils.isElementPresent(By.xpath(retryLocatorValue), 5);
    }

    @Step
    @Override
    public ShoppingBagPage addProductMoveToBag() {
        if (utils.isElementPresent(getLocator("btnAddToBag"), 3)) {
            selectSizeAndAddToBag();
        }
        utils.click(getLocator("btnGoToBag"));
        return ShoppingBagPage.createInstance();
    }

    @Step
    private void selectSize() {
        boolean isSizeAvailable, isNumberOfItemsRemainingMessageAvailable;
        boolean isSizeSelected = false;
        int numberOfSizes = utils.findElements(getLocator("btnSelectSize"))
                                 .size();
        for (int eachSize = 0; eachSize < numberOfSizes; eachSize++) {
            String eachSizeLocator = "//*[starts-with(@content-desc,'size_select_" + eachSize + "')]";
            isSizeAvailable = utils.isElementPresent(By.xpath(eachSizeLocator + "/*[@index='1']"), 2);
            isNumberOfItemsRemainingMessageAvailable = utils.isElementPresent(By.xpath(eachSizeLocator + "/*[@index='1']/android.widget.TextView"),
                    2);
            if (!isSizeAvailable || isNumberOfItemsRemainingMessageAvailable) {
                utils.click(By.xpath(eachSizeLocator));
                isSizeSelected = true;
                break;
            }
        }
        Assert.assertTrue(isSizeSelected, String.format("Size not available for selected product code %s", getTestData().getAdditionalProperties()
                                                                                                                        .get("SearchItem")));
    }

    @Step
    @Override
    public ProductDescPage selectSizeAndAddToBag() {
        utils.click(getLocator("btnAddToBag"), true);
        selectSize();
        if (utils.isElementPresent(getLocator("btnDone"), 3)) {
            utils.click(getLocator("btnDone"));
        } else if (utils.isElementPresent(getLocator("btnAddToBag"), 2)) {
            utils.click(getLocator("btnAddToBag"), true);
        }
        return this;
    }

    @Step
    @Override
    public ProductDescPage saveProductToWishlist() {
        utils.click(getLocator("btnSave"), true);
        return this;
    }

    @Step
    @Override
    public ShoppingBagPage goToBag() {
        return addProductMoveToBag();
    }

    @Step
    @Override
    public ProductDescPage tapForBestPrice() {
        utils.swipeDown_m(2);
        if (utils.isElementPresent(getLocator("lnkTapBestPrice"), 5)) {
            utils.click(getLocator("lnkTapBestPrice"), true);
            utils.isElementPresent(getLocator("txaBestPriceDetails"), 3);
        } else {
            uiAutomatorScrollByText("com.myntra.android:id/ll_react_container", "android.widget.TextView", "Tap for best price");
            utils.click(getLocator("lnkTapBestPrice"), true);
            utils.isElementPresent(getLocator("txaBestPriceDetails"), 3);
        }
        // TODO Myntra Team please this condition once issue is resolved
        if (utils.isElementPresent(getLocator("lnkTapBestPrice"), 5)) {
            utils.click(getLocator("lnkTapBestPrice"), true);
        }
        return this;
    }

    @Step
    @Override
    public WishListPage navigateToWishlist() {
        utils.click(getLocator("icoWishlist"));
        return WishListPage.createInstance();
    }

    @Step
    @Override
    public boolean isDeliveryAvailableInPinCode() {
        uiAutomatorScrollByDescription("com.myntra.android:id/ll_react_container", "android.view.ViewGroup", "delivey_enter_pincode");
        //Added this try catch for stability purpose
        if (!utils.isElementPresent(getLocator("btnEnterPinCode"), 5)) {
            try {
                scrollTillElementVisible(getLocator("btnEnterPinCode"), Direction.DOWN);
            } catch (Exception e) {
                scrollTillElementVisible(getLocator("btnEnterPinCode"), Direction.UP);
            }
        }
        utils.click(getLocator("btnEnterPinCode"), true);
        utils.sendKeys(getLocator("txtPinCode"), getAddressTestData().getPincode() + Keys.ENTER);
        if (!utils.isElementPresent(getLocator("txaDeliveryconfirmation"), 10)) {
            return false;
        } else {
            uiAutomatorScrollByDescription("com.myntra.android:id/ll_react_container", "android.view.ViewGroup", "similar_icon");
            return true;
        }
    }

    @Step
    @Override
    public HashMap<String, String> getProductDetails() {
        HashMap<String, String> productDetails = new HashMap<>();
        productDetails.put("Name", utils.findElement(getLocator("lblProductName"))
                                        .getText());
        productDetails.put("Selling Price", utils.findElement(getLocator("lblSellingPrice"))
                                                 .getText()
                                                 .substring(1)
                                                 .replace(",", ""));
        uiAutomatorScrollByDescription("com.myntra.android:id/ll_react_container", "android.view.ViewGroup", "more_info");
        if (!utils.isElementPresent(getLocator("lblProductCode"), 5)) {
            scrollTillElementVisible(getLocator("lblProductCode"), Direction.DOWN);
        }
        String productCodeActual = utils.findElement(getLocator("lblProductCode"))
                                        .getText()
                                        .split(" ")[2];
        productDetails.put("ProductCodeActual", productCodeActual);
        productDetails.put("ProductCodeExpected", (String) getTestData().getAdditionalProperties()
                                                                        .get("SearchItem"));
        testExecutionContext.addTestState("productDetails", productDetails);
        uiAutomatorScrollByDescription("com.myntra.android:id/ll_react_container", "android.view.ViewGroup", "similar_icon");
        return productDetails;
    }

    @Step
    @Override
    public ProductDescPage viewSizeChart() {
        utils.click(getLocator("btnAddToBag"), true);
        utils.wait(ExpectedConditions.elementToBeClickable(getLocator("lnkSizeChart")), 5);
        utils.click(getLocator("lnkSizeChart"), true);
        utils.wait(ExpectedConditions.elementToBeClickable(getLocator("btnCloseSizeChart")), 5);
        goBack();
        return this;
    }

    @Step
    @Override
    public boolean areAllThumbnailImagesAvailableInPDP() {
        List<WebElement> allImages = utils.findElements(getLocator("imgSmallThumbnails"));
        checkProductImageEllipsis(allImages);
        swipeProductImages(allImages);
        return isProductImagePresentInPDP();
    }

    @Step
    private ProductDescPage checkProductImageEllipsis(List<WebElement> allImages) {
        for (WebElement image : allImages) {
            utils.click(image, true);
        }
        return this;
    }

    @Step
    private ProductDescPage swipeProductImages(List<WebElement> allImages) {
        utils.click(getLocator("imgLargeThumbnail"), true);
        for (WebElement image : allImages) {
            utils.click(getLocator("imgNavigateArrowR"), true);
        }
        utils.click(getLocator("closeZoomImage"), true);
        return this;
    }

    @Step
    @Override
    public ProductDescPage viewSimilarProducts() {
        utils.click(getLocator("icoSimiliar"), true);
        utils.wait(ExpectedConditions.visibilityOfElementLocated(MobileBy.AccessibilityId("similar_component")), 5);
        goBack();
        return this;
    }

    @Step
    @Override
    public boolean isTapForBestPriceSuccessful() {
        boolean isBestPriceDisplayed = utils.isElementPresent(getLocator("txaBestPriceDetails"), 10);
        if (!isBestPriceDisplayed) {
            tapForBestPrice();
        }
        return super.isTapForBestPriceSuccessful();
    }
}
