package com.myntra.core.pages.NativeAndroid;

import com.myntra.core.pages.OrderConfirmationPage;
import com.myntra.core.pages.PaymentPage;
import com.myntra.ui.SelectBy;
import io.qameta.allure.Step;

import java.util.Calendar;

public class NativeAndroidPaymentPage extends PaymentPage {

    @Step
    @Override
    public OrderConfirmationPage payUsingCashOnDelivery() {
        utils.click(getLocator("tabCOD"), true);
        utils.click(getLocator("btnPayOnDelivery"), true);
        return OrderConfirmationPage.createInstance();
    }

    @Step
    @Override
    public PaymentPage payUsingCreditAndDebitCard() {
        utils.click(getLocator("tabCreditCard"), true);
        utils.waitForElementToBeVisible(getLocator("txtCreditCardNumber"));
        utils.sendKeys(getLocator("txtCreditCardNumber"), getTestData().get("CreditCardNumber"));
        utils.sendKeys(getLocator("txtNameOnCreditCard"), getTestData().get("NameOnCreditCard"));
        utils.select(getLocator("ddlExpiryMonthField"), SelectBy.VALUE, String.format("%02d", ((Calendar.getInstance()
                                                                                                        .get(Calendar.MONTH) +
                1) % 12)));
        utils.select(getLocator("ddlExpiryYearField"), SelectBy.VISIBLE_TEXT, String.format("%s",
                (Calendar.getInstance()
                         .get(Calendar.YEAR) + 2)));
        utils.sendKeys(getLocator("txtCVV"), getTestData().get("CVV"));
        utils.click(getLocator("btnPayNowForCreditCardPayment"), true);
        return this;
    }
}
