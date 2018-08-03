package com.myntra.core.pages.NativeAndroid;

import com.myntra.core.pages.UserProfilePage;
import com.myntra.ui.SelectBy;
import io.qameta.allure.Step;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class NativeAndroidUserProfilePage extends UserProfilePage {

    @Step
    @Override
    public UserProfilePage editUserAccountDetails() {
        utils.findElement(getLocator("txaFirstName"))
             .clear();
        utils.sendKeys(getLocator("txaFirstName"), (String) getTestData().getAdditionalProperties()
                                                                         .get("firstname"));
        utils.click(getLocator("btnGender"));
        utils.click(getLocator("selectGenderMale"));
        utils.findElement(getLocator("txaMobileNumber"))
             .clear();
        utils.sendKeys(getLocator("txaMobileNumber"), getUserTestData().getPhoneDetails()
                                                                       .get(0)
                                                                       .getPhone());
        utils.select(getLocator("drpDobDate"), SelectBy.INDEX, "1");
        utils.select(getLocator("drpDobMonth"), SelectBy.INDEX, "2");
        utils.select(getLocator("drpDobYear"), SelectBy.INDEX, "5");
        utils.findElement(getLocator("txaLocation"))
             .clear();
        utils.sendKeys(getLocator("txaLocation"), getAddressTestData().getLocality());
        utils.click(getLocator("btnSave"), true);
        utils.wait(ExpectedConditions.invisibilityOfElementLocated(getLocator("btnSave")), 5);
        return this;
    }
}
