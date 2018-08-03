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
        String personalisedCoupons = getCouponTestData().getCouponCode();
        utils.sendKeys(getLocator("txtCouponCode"), personalisedCoupons);
        utils.click(getLocator("btnApply"), true);
        utils.wait((ExpectedConditions.invisibilityOfElementLocated(getLocator("btnApply"))));
        return this;
    }

    @Step
    @Override
    protected ShoppingBagPage giftWrapThisProduct() {
        utils.scrollDown_m(3);
        utils.click(getLocator("btnGiftWrap"), true);
        utils.sendKeys(getLocator("txtRecipient"), (String) getTestData().getAdditionalProperties()
                                                                         .get("RecipientName"));
        utils.sendKeys(getLocator("txtMessage"), (String) getTestData().getAdditionalProperties()
                                                                       .get("GiftMessage"));
        utils.sendKeys(getLocator("txtSender"), (String) getTestData().getAdditionalProperties()
                                                                      .get("SenderName"));
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


    @Step
    @Override
    public boolean isProductPresentInBag() {
        //TODO Myntra Team please remove this if condition once the bug is fixed; Jira ID:VEGASF_579
        if (!super.isProductPresentInBag()) {
            utils.refreshPage();
        }
        return super.isProductPresentInBag();
    }

    @Override
    @Step
    public ShoppingBagPage applyMyntCouponCode() {
        utils.click(getLocator("btnMyntApplyCode"), true);
        utils.sendKeys(getLocator("txtMyntDiscount"), (String) getCouponTestData().getAdditionalProperties()
                                                                                  .get("MyntCoupon"));
        utils.click(getLocator("btnMyntApply"), true);
        utils.wait(ExpectedConditions.invisibilityOfElementLocated(getLocator("btnMyntApply")));
        return this;
    }
}

