package com.myntra.core.pages.NativeIOS;

import com.myntra.core.pages.ProductDescPage;
import com.myntra.core.pages.ProductListPage;
import io.qameta.allure.Step;
import org.apache.commons.lang3.NotImplementedException;

public class NativeIOSProductListPage extends ProductListPage {

    @Step
    @Override
    public ProductDescPage selectFirstProductFromListPage() {
        throw new NotImplementedException(getClass().getSimpleName() + "-" + new Object() {
        }.getClass()
         .getEnclosingMethod()
         .getName() + " - NOT YET IMPLEMENTED");
    }
}
