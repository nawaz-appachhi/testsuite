package com.myntra.core.pages;

import com.myntra.core.pages.Desktop.DesktopLoginPage;
import com.myntra.core.pages.MobileWeb.MobileWebLoginPage;
import com.myntra.core.pages.NativeAndroid.NativeAndroidLoginPage;
import com.myntra.core.pages.NativeIOS.NativeIOSLoginPage;
import com.myntra.core.utils.DynamicEnhancer;
import com.myntra.core.utils.DynamicLogger;
import io.qameta.allure.Step;
import org.apache.commons.lang3.NotImplementedException;

public abstract class LoginPage extends Page {

    public static LoginPage createInstance() {
        LoginPage derivedLoginPage;
        switch (CHANNEL) {
            case NATIVE_ANDROID:
                derivedLoginPage = (LoginPage) DynamicEnhancer.create(NativeAndroidLoginPage.class, new DynamicLogger());
                break;

            case NATIVE_IOS:
                derivedLoginPage = (LoginPage) DynamicEnhancer.create(NativeIOSLoginPage.class, new DynamicLogger());
                break;

            case DESKTOP_WEB:
                derivedLoginPage = (LoginPage) DynamicEnhancer.create(DesktopLoginPage.class, new DynamicLogger());
                break;

            case MOBILE_WEB:
                derivedLoginPage = (LoginPage) DynamicEnhancer.create(MobileWebLoginPage.class, new DynamicLogger());
                break;

            default:
                throw new NotImplementedException("Invalid Channel");
        }
        return derivedLoginPage;
    }

    @Override
    public String pageName() {
        return LoginPage.class.getSimpleName();
    }

    @Step
    public abstract HomePage login();

    @Step
    public abstract LoginPage navigateToLogin();
}
