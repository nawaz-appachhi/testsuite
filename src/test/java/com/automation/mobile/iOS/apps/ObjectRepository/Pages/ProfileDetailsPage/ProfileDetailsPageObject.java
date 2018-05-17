
package com.automation.mobile.iOS.apps.ObjectRepository.Pages.ProfileDetailsPage;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.testng.Assert;
import org.testng.Reporter;
import com.automation.core.mobile.iOS.iOSGenericMethods;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.PerformsTouchActions;
import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

/**
 * 
 * @author 300021275 Lata Naik
 *
 */

public class ProfileDetailsPageObject {
	iOSGenericMethods objiOSGenericMethods;
	AppiumDriver<MobileElement> iDriver;

	public ProfileDetailsPageObject(AppiumDriver<MobileElement> iDriver) {

		PageFactory.initElements(new AppiumFieldDecorator(iDriver), this);
		objiOSGenericMethods = new iOSGenericMethods(iDriver);

	}

	/**
	 * @author 300021275 Objects Created By - Lata
	 * 
	 */
	@iOSFindBy(accessibility = "Profile Details")
	public IOSElement profileDetails;
	/**
	 * @author 300021275 Objects Created By - Lata
	 * 
	 */
	@iOSFindBy(accessibility = "MOBILE NUMBER")
	public IOSElement mobileNumberField;

	/**
	 * @author 300021275 Objects Created By - Lata
	 * 
	 */
	@iOSFindBy(xpath = "(//XCUIElementTypeOther[@name=' VERIFY '])[1]")
	public IOSElement verifyButton;

	/**
	 * @author 300021275 Objects Created By - Lata
	 * 
	 */
	@iOSFindBy(accessibility = "VERIFIED")
	public IOSElement verifiedText;
	/**
	 * @author 300021275 Objects Created By - Lata
	 * 
	 */
	@iOSFindBy(accessibility = "SAVE DETAILS")
	public IOSElement saveDetails;

	/**
	 * @author 300021275 Objects Created By - Lata
	 * 
	 */
	@iOSFindBy(accessibility = "PROFILE DETAILS")
	public IOSElement profileDetailsPageTitle;

	/**
	 * @author 300019221 object created by Aravindanath
	 */
	@iOSFindBy(className = "XCUIElementTypeOther")
	public List<IOSElement> otpText;

	/**
	 * @author 300021275 Objects Created By - Lata
	 * 
	 */
	@iOSFindBy(accessibility = "LOCATION")
	public IOSElement location;

	/**
	 * @author 300021275 Getters created by - Lata
	 */

	public IOSElement getProfileDetails() {
		objiOSGenericMethods.CheckIOSElementFound(profileDetails, "profileDetails");
		return profileDetails;
	}

	/**
	 * @author 300021275 Getters created by - Lata
	 */

	public IOSElement getMobileNumberField() {
		objiOSGenericMethods.CheckIOSElementFound(mobileNumberField, "mobileNumberField");
		return mobileNumberField;
	}

	/**
	 * @author 300021275 Getters created by - Lata
	 */
	public IOSElement getVerifyButton() {
		objiOSGenericMethods.CheckIOSElementFound(verifyButton, "verifyButton");
		return verifyButton;
	}

	/**
	 * @author 300021275 Getters created by - Lata
	 */
	public IOSElement getVerifiedText() {
		objiOSGenericMethods.CheckIOSElementFound(verifiedText, "verifiedText");
		return verifiedText;
	}

	/**
	 * @author 300021275 Getters created by - Lata
	 */
	public IOSElement getSaveDetails() {
		objiOSGenericMethods.CheckIOSElementFound(saveDetails, "saveDetails");
		return saveDetails;
	}

	/**
	 * @author 300021275 Getters created by - Lata
	 */
	public IOSElement getProfileDetailsPageTitle() {
		objiOSGenericMethods.CheckIOSElementFound(profileDetailsPageTitle, "profileDetailsPageTitle");
		return profileDetailsPageTitle;
	}

	/**
	 * @author 300021275 Getters created by - Lata
	 */
	public IOSElement getLocation() {
		objiOSGenericMethods.CheckIOSElementFound(location, "location");
		return location;
	}

	/**
	 * @author 300021275 This method is used to click on Profile Details button
	 */
	public void clickOnProfileDetails() {
		try {
			// if (getProfileDetails().isDisplayed()) {
			objiOSGenericMethods.clickOnIOSElement(getProfileDetails(), "Profile Details Button");
			// }
		} catch (Exception e) {
			System.out.println("Unable to click on Profile Details!");
		}
	}

	/**
	 * @author 300021275 - Lata
	 * @param mobileNumber
	 *            This method is used to enter the mobile number in Profile details
	 *            screen
	 * 
	 *            Modified by Lata - Added Keys.DELETE method as the clear method
	 *            was not working.
	 */
	public void enterMobileNumber(String mobileNumber) {
		try {
			if (getMobileNumberField().isDisplayed()) {
				// getMobileNumberField().click();
				objiOSGenericMethods.clickOnIOSElement(getMobileNumberField(), "Mobile Number Button");
				String number = mobileNumberField.getText();
				int maxChar = number.length();
				for (int i = 0; i < maxChar; i++) {
					getMobileNumberField().sendKeys(Keys.DELETE);
				}
				getMobileNumberField().sendKeys(mobileNumber);
				Reporter.log("Mobile number succesfully entered");
				// getMobileNumberField().click();
				objiOSGenericMethods.clickOnIOSElement(getMobileNumberField(), "Mobile Number Button");
			}

		} catch (Exception e) {
			Reporter.log("Unable to enter mobile number");
		}
	}

	/**
	 * @author 300021275 - Lata This method is used to click on Verify text
	 */
	public void clickOnVerifyButton() {
		try {
			// if (getVerifyButton().isDisplayed()) {
			objiOSGenericMethods.clickOnIOSElement(getVerifyButton(), "Verify Button");
			// }
		} catch (Exception e) {
			System.out.println("User is already verified!");
		}
	}

	/**
	 * @author 300021275 - Lata This method is used to click on Save Details button
	 */
	public void clickOnSaveDetails() {
		try {
			// if (getSaveDetails().isDisplayed()) {
			objiOSGenericMethods.clickOnIOSElement(getSaveDetails(), "Save Details Button");
			// }
		} catch (Exception e) {
			System.out.println("Unable to click on Save Button!");
		}
	}

	/**
	 * @author 300021275 - Lata This method is used to click on Profile details page
	 *         name
	 */
	public void clickOnProfileDetailsPageTitle() {
		try {
			// if (getProfileDetailsPageTitle().isDisplayed()) {
			objiOSGenericMethods.clickOnIOSElement(getProfileDetailsPageTitle(), "Profile Details Page Title");
			// }
		} catch (Exception e) {
			System.out.println("Unable to click on Profile Details Page Title!");
		}
	}

	/**
	 * @author 300021275 - Lata This method is used to click on Location button
	 */
	public void clickOnLocation() {
		try {
			// if (getLocation().isDisplayed()) {
			objiOSGenericMethods.clickOnIOSElement(getLocation(), "Location");
			// }
		} catch (Exception e) {
			System.out.println("Unable to click on Location!");
		}
	}

	/**
	 * @author 300019221
	 */
	public void sendOTP() {
		otpText.get(24).sendKeys("0000");
	}

}
