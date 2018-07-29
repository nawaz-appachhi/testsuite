package com.myntra.core.business;

import com.myntra.core.pages.OrderConfirmationPage;
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

}
