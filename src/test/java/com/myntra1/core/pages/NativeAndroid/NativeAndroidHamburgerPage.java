package com.myntra.core.pages.NativeAndroid;

import com.myntra.core.pages.AddressPage;
import com.myntra.core.pages.HamburgerPage;
import com.myntra.ui.Direction;
import io.qameta.allure.Step;

public class NativeAndroidHamburgerPage extends HamburgerPage {

    @Step
    @Override
    public AddressPage goToAddress() {
        scrollTillElementVisible(getLocator("mnuAccount"),Direction.DOWN);
        utils.click(getLocator("mnuAccount"), true);
        utils.scrollTillElementVisible(getLocator("mnuAddress"));
        utils.click(getLocator("mnuAddress"), true);
        return AddressPage.createInstance();
    }
}
