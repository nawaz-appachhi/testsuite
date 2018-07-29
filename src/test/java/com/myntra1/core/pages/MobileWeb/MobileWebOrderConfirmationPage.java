package com.myntra.core.pages.MobileWeb;


import com.myntra.core.pages.HamburgerPage;
import com.myntra.core.pages.HomePage;
import com.myntra.core.pages.OrderConfirmationPage;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class MobileWebOrderConfirmationPage extends OrderConfirmationPage {

    @Step
    @Override
    public boolean isOrderNumberAvailableInMyOrders() {
        utils.click(getLocator("lnkViewOrder"), true);
        String orderNumberConfirmation = utils.getText(getLocator("txaOrderNumber"))
                                              .replaceAll("-", "");
        String orderNumberFromOrdersPage = null;
        utils.wait(ExpectedConditions.visibilityOfElementLocated(getLocator("imgMyntraLogo")));
        utils.click(getLocator("imgMyntraLogo"), true);
        HomePage.createInstance()
                .openMenu();
        HamburgerPage.createInstance()
                     .goToMyOrdersPage();
        List<WebElement> orderNumbersInMyOrdersPage = getDriver().findElements(
                getLocator("txaOrderNumberInMyOrdersPage"));
        for (WebElement orderNumber : orderNumbersInMyOrdersPage) {
            orderNumberFromOrdersPage = orderNumber.getText()
                                                   .replaceAll("-", "");
            if (orderNumberFromOrdersPage.equals(orderNumberConfirmation)) {
                break;
            }
        }
        return (orderNumberFromOrdersPage.equalsIgnoreCase(orderNumberConfirmation));
    }
}
