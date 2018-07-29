package com.myntra.core.business;

import com.myntra.core.pages.HomePage;
import io.qameta.allure.Step;

public class Home extends BusinessFlow {

    public static Home getInstance() {
        return (Home) getInstance(Of.HOME);
    }

    @Step
    public Product searchProductUsingStyleID() {
        return Search.getInstance()
                     .searchProductUsingStyleID();
    }

    @Step
    public Product searchProductUsingName() {
        return Search.getInstance()
                     .searchProductUsingName();
    }

    @Step
    public Home cleanState() {
        HomePage.createInstance()
                .reset();
        return this;
    }

    @Step
    public User loginSuccessfully() {
        return User.getInstance()
                   .loginSuccessfully();
    }

    @Step
    public Home verifyContactUsHasDetails() {
        HomePage.createInstance()
                .openContactUsPage()
                .closeContactUsPage();
        return this;
    }
}
