package com.myntra.core.pages;

import com.myntra.core.pages.Desktop.DesktopOrderConfirmationPage;
import com.myntra.core.pages.MobileWeb.MobileWebOrderConfirmationPage;
import com.myntra.core.pages.NativeAndroid.NativeAndroidOrderConfirmationPage;
import com.myntra.core.pages.NativeIOS.NativeIOSOrderConfirmationPage;
import com.myntra.core.utils.DynamicEnhancer;
import com.myntra.core.utils.DynamicLogger;
import io.qameta.allure.Step;
import org.apache.commons.lang3.NotImplementedException;

public abstract class OrderConfirmationPage extends Page {

    public static OrderConfirmationPage createInstance() {
        OrderConfirmationPage derivedOrderConfirmationPage;
        switch (CHANNEL) {
            case NATIVE_ANDROID:
                derivedOrderConfirmationPage = (OrderConfirmationPage) DynamicEnhancer.create(NativeAndroidOrderConfirmationPage.class, new DynamicLogger());
                break;

            case NATIVE_IOS:
                derivedOrderConfirmationPage = (OrderConfirmationPage) DynamicEnhancer.create(NativeIOSOrderConfirmationPage.class, new DynamicLogger());
                break;

            case DESKTOP_WEB:
                derivedOrderConfirmationPage = (OrderConfirmationPage) DynamicEnhancer.create(DesktopOrderConfirmationPage.class, new DynamicLogger());
                break;

            case MOBILE_WEB:
                derivedOrderConfirmationPage = (OrderConfirmationPage) DynamicEnhancer.create(MobileWebOrderConfirmationPage.class, new DynamicLogger());
                break;

            default:
                throw new NotImplementedException("Invalid Channel");
        }
        return derivedOrderConfirmationPage;
    }

    @Override
    public String pageName() {
        return OrderConfirmationPage.class.getSimpleName();
    }

    @Step
    public boolean isOrderConfirmed() {
        return (utils.isElementPresent(getLocator("txaOrderConfirmed"), 2));
    }

    @Step
    public boolean isOrderNumberAvailableInMyOrders() {
        throw new NotImplementedException(getClass().getSimpleName() + "-" + new Object() {
        }.getClass()
         .getEnclosingMethod()
         .getName() + " - NOT YET IMPLEMENTED");
    }
}
