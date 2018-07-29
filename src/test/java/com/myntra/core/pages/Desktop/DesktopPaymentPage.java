package com.myntra.core.pages.Desktop;

import com.myntra.core.pages.PaymentPage;
import com.myntra.utils.test_utils.Assert;

public class DesktopPaymentPage extends PaymentPage {
    @Override
    protected void isLoaded() throws Error {
        utils.waitForElementToBeVisible(getLocator("txtPaymentHeader"));
        Assert.assertTrue(utils.isElementPresent(getLocator("txtPaymentHeader"), 5),
                "Desktop Payment page is not loaded");
    }
}
