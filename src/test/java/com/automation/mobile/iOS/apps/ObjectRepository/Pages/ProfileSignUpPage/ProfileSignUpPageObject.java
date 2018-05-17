
package com.automation.mobile.iOS.apps.ObjectRepository.Pages.ProfileSignUpPage;

import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;

import com.automation.core.mobile.iOS.iOSGenericMethods;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;

/**
 * @author 300021281
 *
 */
public class ProfileSignUpPageObject {

	iOSGenericMethods objiOSGenericMethods;

	public AppiumDriver<MobileElement> iDriver;

	public ProfileSignUpPageObject(AppiumDriver<MobileElement> iDriver) {
		PageFactory.initElements(new AppiumFieldDecorator(iDriver), this);
		objiOSGenericMethods = new iOSGenericMethods(iDriver);
	}

	/**
	 * @author 300021275 -Lata - xpath changed by accessibility id
	 * 
	 *         Old Locator - xpath ="//XCUIElementTypeButton[@name=\"Profile\"]"
	 */
	@iOSFindBy(accessibility = "Profile")
	public IOSElement profile;

	/**
	 * @author 300021275 - Lata - xpath changed by accessibility id
	 * 
	 *         Old Locator - xpath ="//XCUIElementTypeOther[@name=\"SIGN UP\"]"
	 */

	@iOSFindBy(accessibility = "SIGN UP")
	public IOSElement signup;

	/**
	 * @author 300021275 - Lata - xpath changed by accessibility id
	 * 
	 *         Old Locator - xpath ="//XCUIElementTypeStaticText[@name=\"Email
	 *         address\"]"
	 */
	@iOSFindBy(accessibility = "Email address")
	public IOSElement emailField;
	/**
	 * @author 300021275 - Lata - xpath changed by accessibility id
	 * 
	 *         Old Locator - xpath
	 *         ="//XCUIElementTypeStaticText[@name=\"Password\"]"
	 */
	@iOSFindBy(accessibility = "Password")
	public IOSElement passwordField;
	/**
	 * @author 300021275 - Lata - xpath changed by accessibility id
	 * 
	 *         Old Locator - xpath ="//XCUIElementTypeStaticText[@name=\"Full
	 *         Name\"]"
	 */
	@iOSFindBy(accessibility = "Full Name")
	public IOSElement nameField;
	/**
	 * @author 300021275 - Lata - xpath changed by accessibility id
	 * 
	 *         Old Locator - xpath ="//XCUIElementTypeStaticText[@name=\"Mobile
	 *         Number (for order updates)\"]"
	 */
	@iOSFindBy(accessibility = "Mobile Number (for order updates)")
	public IOSElement mobileNumber;

	@FindBy(xpath = "//XCUIElementTypeButton[@name=\"Male\"]")
	public IOSElement gender;

	/**
	 * @author 300021275 - Lata - xpath changed by accessibility id
	 * 
	 *         Old Locator - xpath ="//XCUIElementTypeButton[@name=\"CREATE
	 *         ACCOUNT\"]"
	 */
	@iOSFindBy(accessibility = "CREATE ACCOUNT")
	public IOSElement createAccount;

	@FindBy(xpath = "//XCUIElementTypeButton[@name=\"SKIP\"]")
	public IOSElement nextButton;

	/**
	 * @author 300021275 - Created by Lata
	 */
	@iOSFindBy(accessibility = "Female")
	public IOSElement genderFemale;

	/**
	 * @author 300021275 Created by Lata
	 */
	@iOSFindBy(accessibility = "Male")
	public IOSElement genderMale;

	/**
	 * @author 300021275 Created by Lata
	 */
	@iOSFindBy(accessibility = "CONTINUE SHOPPING")
	public IOSElement continueShoppingButton;

	/**
	 * @author 300021276 object created by - Madhu
	 * 
	 */

	@iOSFindBy(accessibility = "SKIP")
	public IOSElement skipReferralCode;

	/************* getters ************/


	public IOSElement getProfile() {
		objiOSGenericMethods.CheckIOSElementFound(profile, "profile");
		return profile;
	}

	public IOSElement getSignup() {
		objiOSGenericMethods.CheckIOSElementFound(signup, "signup");
		return signup;
	}

	public IOSElement getEmailField() {
		objiOSGenericMethods.CheckIOSElementFound(emailField, "emailField");
		return emailField;
	}

	public IOSElement getPasswordField() {
		objiOSGenericMethods.CheckIOSElementFound(passwordField, "passwordField");
		return passwordField;
	}

	public IOSElement getNameField() {
		objiOSGenericMethods.CheckIOSElementFound(nameField, "nameField");
		return nameField;
	}

	public IOSElement getMobileNumber() {
		objiOSGenericMethods.CheckIOSElementFound(mobileNumber, "mobileNumber");
		return mobileNumber;
	}

	public IOSElement getGender() {
		objiOSGenericMethods.CheckIOSElementFound(gender, "gender");
		return gender;
	}

	public IOSElement getCreateAccount() {
		objiOSGenericMethods.CheckIOSElementFound(createAccount, "createAccount");
		return createAccount;
	}

	public IOSElement getNextButton() {
		objiOSGenericMethods.CheckIOSElementFound(nextButton, "nextButton");
		return nextButton;
	}

	/**
	 * @author 300021275 Created by Lata
	 */
	public IOSElement getGenderFemale() {
		objiOSGenericMethods.CheckIOSElementFound(genderFemale, "genderFemale");
		return genderFemale;
	}

	/**
	 * @author 300021275 Created by Lata
	 */
	public IOSElement getGenderMale() {
		objiOSGenericMethods.CheckIOSElementFound(genderMale, "genderMale");
		return genderMale;
	}

	/**
	 * @author 300021275 Created by Lata
	 */
	public IOSElement getContinueShoppingButton() {
		objiOSGenericMethods.CheckIOSElementFound(continueShoppingButton, "continueShoppingButton");
		return continueShoppingButton;
	}

	/**
	 * @author 300021276 getters created By - Madhu
	 * 
	 *
	 */

	public IOSElement getSkipReferralCode() {
		objiOSGenericMethods.CheckIOSElementFound(skipReferralCode, "skipReferralCode");
		return skipReferralCode;
	}

	/************ methods ***********/

	public void clickOnProfileButton() {
		objiOSGenericMethods.clickOnIOSElement(getProfile(), "Profile Button");
	}

	public void clickOnSignUp() {
		objiOSGenericMethods.clickOnIOSElement(getSignup(), "Signup Button");
	}

	public void clickOnGenderButton() {
		objiOSGenericMethods.clickOnIOSElement(getGender(), "Gender Button");
	}

	/**
	 * @author 300021275 Modified By Lata - Used try catch block and getters in the
	 *         method and also used datetime method to generate unique mail ids
	 * 
	 * @param mailID
	 * @param password
	 * @param name
	 * @param number
	 * 
	 * 
	 */
	public void signUpInApp(String mailID, String password, String name, String number) {
		try {

			// if (getEmailField().isDisplayed()) {
			String newemaild = mailID + objiOSGenericMethods.datetime("ddMMyyyyHHmmss") + "@gmail.com";
			getEmailField().sendKeys(newemaild);
			Reporter.log("Email succesfully entered");
			// }
			// if (getPasswordField().isDisplayed()) {
			getPasswordField().sendKeys(password);
			Reporter.log("password succesfully entered");
			// }

			// if (getNameField().isDisplayed()) {
			getNameField().sendKeys(name);
			Reporter.log("Name succesfully entered");
			// }

			// if (getMobileNumber().isDisplayed()) {
			getMobileNumber().sendKeys(number);
			Reporter.log("mobileNumber succesfully entered");
			// }

			System.out.println("Registration details succesfully entered");
		} catch (Exception e) {
			System.out.println("Unable to enter details");
		}
	}

	public void clickOnCreateAccountButton() {
		objiOSGenericMethods.clickOnIOSElement(getCreateAccount(), "Create Account Button");
	}

	public void clicOnNextButton() {
		objiOSGenericMethods.clickOnIOSElement(getNextButton(), "Next Button");
	}

	/**
	 * @author 300021275 Created by Lata
	 * 
	 *         This method is to select Female option in SignUp
	 */
	public void clickOnFemale() {
		try {
			// if (getGenderFemale().isDisplayed()) {
			objiOSGenericMethods.clickOnIOSElement(getGenderFemale(), "Female Button");
			// }
		} catch (Exception e) {
			System.out.println("Unable to click on Profile Details!");
		}
	}

	/**
	 * @author 300021275 Created by Lata
	 * 
	 *         This method is to select Male option in SignUp
	 */
	public void clickOnMales() {
		try {
			// if (getGenderMale().isDisplayed()) {
			objiOSGenericMethods.clickOnIOSElement(getGenderMale(), "Male Button");
			// }
		} catch (Exception e) {
			System.out.println("Unable to click on Profile Details!");
		}
	}

	/**
	 * @author 300021275 Created by Lata
	 * 
	 *         This method is to click on Continue Button after sign up
	 */
	public void clickOnContinueShoppingButton() {
		try {
			// if (getContinueShoppingButton().isDisplayed()) {
			objiOSGenericMethods.clickOnIOSElement(getContinueShoppingButton(), "Profile Details Button");
			// }
		} catch (Exception e) {
			System.out.println("Unable to click on Profile Details!");
		}
	}

	public void clickOnSkipReferralCode() {
		try {
			// if (getSkipReferralCode().isDisplayed()) {
			objiOSGenericMethods.clickOnIOSElement(getSkipReferralCode(), "Skip");
			// }
		} catch (Exception e) {
			System.out.println("Unable to click on Location!");
		}
	}

}
