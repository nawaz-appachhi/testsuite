package com.myntra.core.pages.Desktop;

import com.myntra.core.pages.OrderConfirmationPage;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class DesktopOrderConfirmationPage extends OrderConfirmationPage {

    @Step
    @Override
    public boolean isOrderNumberAvailableInMyOrders() {
        boolean orderNumberVerifiedAndisSame = false;
        String orderNumberConfirmation = utils.getText(getLocator("txaOrderNumber"))
                                              .replaceAll("-", "");
        String orderNumberFromOrdersPage = null;
        navigateToOrder();
        utils.wait(ExpectedConditions.visibilityOfElementLocated(getLocator("txaOrderNumberInMyOrdersPage")));
        List<WebElement> orderNumbersInMyOrdersPage = getDriver().findElements(
                getLocator("txaOrderNumberInMyOrdersPage"));
        for (WebElement orderNumber : orderNumbersInMyOrdersPage) {
            orderNumberFromOrdersPage = orderNumber.getText()
                                                   .replaceAll("-", "");
            if (orderNumberFromOrdersPage.equalsIgnoreCase(orderNumberConfirmation)) {
                orderNumberVerifiedAndisSame = true;
            }
        }
        return orderNumberVerifiedAndisSame;
    }

    @Step
    private OrderConfirmationPage navigateToOrder() {
        utils.wait(ExpectedConditions.visibilityOfElementLocated(getLocator("btnContinueShopping")));
        utils.waitForElementToBeVisible(getLocator("btnContinueShopping"));
        utils.click(getLocator("btnContinueShopping"), true);
        utils.waitForElementToBeVisible(getLocator("icoUser"));
        utils.click(getLocator("icoUser"));
        utils.click(getLocator("lnkOrder"));
        return this;
    }
}
