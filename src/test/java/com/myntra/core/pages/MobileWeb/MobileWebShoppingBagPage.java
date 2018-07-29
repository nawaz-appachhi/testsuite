package com.myntra.core.pages.MobileWeb;

import com.myntra.core.pages.ShoppingBagPage;
import io.qameta.allure.Step;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class MobileWebShoppingBagPage extends ShoppingBagPage {

    @Step
    @Override
    public ShoppingBagPage applyPersonalisedCoupon() {
        utils.scrollDown_m(3);
        utils.click(getLocator("tabApplyCoupon"), true);
        String PersonalisedCoupons = getTestData().get("PersonalisedCoupons");
        utils.sendKeys(getLocator("txtCouponCode"), PersonalisedCoupons);
        utils.click(getLocator("btnApply"), true);
        utils.wait((ExpectedConditions.invisibilityOfElementLocated(getLocator("btnApply"))));
        return this;
    }

    @Step
    @Override
    protected ShoppingBagPage giftWrapThisProduct() {
        utils.scrollDown_m(3);
        utils.click(getLocator("btnGiftWrap"), true);
        utils.sendKeys(getLocator("txtRecipient"), getTestData().get("RecipientName"));
        utils.sendKeys(getLocator("txtMessage"), getTestData().get("GiftMessage"));
        utils.sendKeys(getLocator("txtSender"), getTestData().get("SenderName"));
        utils.click(getLocator("btnSaveGift"), true);
        utils.wait(ExpectedConditions.invisibilityOfElementLocated(getLocator("btnSaveGift")));
        return this;
    }

    @Step
    @Override
    protected ShoppingBagPage removeExistingGiftWrapAndAddItAgain() {
        utils.click(getLocator("btnRemoveGiftWrap"));
        LOG.debug("GiftWrap removed");
        giftWrapThisProduct();
        return this;
    }
}

