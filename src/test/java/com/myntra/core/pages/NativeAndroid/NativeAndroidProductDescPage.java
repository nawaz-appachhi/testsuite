package com.myntra.core.pages.NativeAndroid;

import com.myntra.core.pages.ProductDescPage;
import com.myntra.core.pages.ShoppingBagPage;
import com.myntra.core.pages.WishListPage;
import com.myntra.ui.Direction;
import com.myntra.utils.test_utils.Assert;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.HashMap;

import static com.myntra.ui.Direction.DOWN;
import static com.myntra.ui.Direction.UP;

public class NativeAndroidProductDescPage extends ProductDescPage {

    @Override
    protected void isLoaded() throws Error {
        Assert.assertTrue(utils.isElementPresent(getLocator("btnAddToBag"), 10),
                String.format("Page - %s - NOT Loaded", getClass().getSimpleName()));
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
            isNumberOfItemsRemainingMessageAvailable = utils.isElementPresent(
                    By.xpath(eachSizeLocator + "/*[@index='1']/android.widget.TextView"), 2);
            if (!isSizeAvailable || isNumberOfItemsRemainingMessageAvailable) {
                utils.click(By.xpath(eachSizeLocator));
                isSizeSelected = true;
                break;
            }
        }
        Assert.assertTrue(isSizeSelected,
                String.format("Size not available for selected product code %s", getTestData().get("SearchItem")));
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
        utils.scroll(Direction.UP, 1);
        if (utils.isElementPresent(getLocator("lnkTapBestPrice"), 5)) {
            utils.click(getLocator("lnkTapBestPrice"), true);
        }else if(utils.isElementPresent(getLocator("txProductInBestPrice"), 10)){
        	return this;
        }
        Assert.assertTrue(utils.isElementPresent(getLocator("btnAddToBag"), 10),
                String.format("Page - %s - NOT Loaded", getClass().getSimpleName()));
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
        scrollTillElementVisible(getLocator("btnEnterPinCode"), DOWN);
        utils.waitForElementToBeVisible(getLocator("btnEnterPinCode"));
        utils.click(getLocator("btnEnterPinCode"), true);
        utils.sendKeys(getLocator("txtPinCode"), getTestData().get("Pincode"));
        swipeUp_m(7);
        // TODO return based on the delivery available message
        return true;
    }

    @Step
    @Override
    public boolean allThumbnailImagesAvailableInPDP() {
        utils.click(getLocator("imgLargeThumbnail"));
        for (int i = 0; i < 3; i++) {
            utils.click(getLocator("imgSmallThumbnails"));
        }
        goBack();
        return true;
    }

    @Step
    @Override
    public HashMap<String, String> getProductDetails() {
        HashMap<String, String> productDetails = new HashMap<String, String>();
        productDetails.put("Name", utils.findElement(getLocator("lblProductName"))
                                        .getText());
        productDetails.put("Selling Price", utils.findElement(getLocator("lblSellingPrice"))
                                                 .getText().substring(1));
        scrollTillElementVisible(getLocator("lblProductCode"), DOWN);
        String productCodeActual =utils.findElement(getLocator("lblProductCode"))
                                       .getText().split(" ")[2];
        productDetails.put("ProductCodeActual", productCodeActual);
        productDetails.put("ProductCodeExpected", getTestData().get("SearchItem"));
        testExecutionContext.addTestState("productDetails", productDetails);
        scrollTillElementVisible(getLocator("lnkSizeChart"), UP);
        return productDetails;
    }

    @Step
    @Override
    public ProductDescPage viewSizeChart() {
        utils.click(getLocator("btnAddToBag"),true);
        utils.wait(ExpectedConditions.elementToBeClickable(getLocator("lnkSizeChart")),5);
        utils.click(getLocator("lnkSizeChart"), true);
        utils.wait(ExpectedConditions.elementToBeClickable(getLocator("btnCloseSizeChart")),5);
        goBack();
        return this;
    }
}
