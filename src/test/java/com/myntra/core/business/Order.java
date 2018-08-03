package com.myntra.core.business;

import com.myntra.core.pages.OrderConfirmationPage;
import com.myntra.core.pages.ShoppingBagPage;
import com.myntra.utils.test_utils.Assert;
import io.qameta.allure.Step;

public class Order extends BusinessFlow {

    public static Order getInstance() {
        return (Order) getInstance(Of.ORDER);
    }

    @Step
    public Order verifyOrderNumberFromMyOrders() {
        if (shouldExecuteIfNotInProd(getClass().getSimpleName(), new Object() {
        }.getClass()
         .getEnclosingMethod()
         .getName())) {
            boolean isOrderConfirmed = OrderConfirmationPage.createInstance()
                                                            .isOrderConfirmed();
            Assert.assertTrue(isOrderConfirmed, "Order is not confirmed");
            boolean isOrderNumberAvailableInMyOrders = OrderConfirmationPage.createInstance()
                                                                            .isOrderNumberAvailableInMyOrders();
            Assert.assertTrue(isOrderNumberAvailableInMyOrders, "Order Number is not available in My Orders page");
        }
        return this;
    }

    @Step
    public Home goToHomePageAfterOrderConfirmation() {
        OrderConfirmationPage orderConfirmationPage = OrderConfirmationPage.createInstance();
        Assert.assertTrue(orderConfirmationPage.isOrderConfirmed(), "Order is confirmed");
        orderConfirmationPage.navigateToHomePage();
        return Home.getInstance();
    }

    @Step
    public Order verifyOrderIsConfirmed() {
        return verifyOrderStatus("Ordered", OrderConfirmationPage.createInstance());
    }

    @Step
    public Order cancelOrder() {
        return verifyOrderStatus("Cancelled", OrderConfirmationPage.createInstance()
                                                                   .cancelOrder());
    }

    @Step
    private Order verifyOrderStatus(String expectedOrderStatus, OrderConfirmationPage orderConfirmationPage) {
        String placedOrderStatus = orderConfirmationPage.getOrderStatus();
        Assert.assertEquals(placedOrderStatus, expectedOrderStatus, "Product Order status is not as expected");
        return this;
    }

    @Step
    public Order verifyOneProductCanBeRemovedAfterPlaceOrderInCompletedConditionDiscountProducts() {
        Assert.assertTrue(ShoppingBagPage.createInstance()
                                         .isOneMoreProductTobeAddedTOCompleteFreeGiftOffer(),
                "Free Gift is Applicable without adding One More Product,Combo Fail");
        return this;
    }
}
