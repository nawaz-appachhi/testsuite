package com.myntra.core.pages.NativeAndroid;

import com.myntra.core.pages.ProductDescPage;
import com.myntra.core.pages.ProductListPage;
import io.qameta.allure.Step;

public class NativeAndroidProductListPage extends ProductListPage {

    @Step
    @Override
    public ProductDescPage selectFirstProductFromListPage() {
        utils.click(getLocator("lnkFirstProduct"));
        return ProductDescPage.createInstance();
    }
}
