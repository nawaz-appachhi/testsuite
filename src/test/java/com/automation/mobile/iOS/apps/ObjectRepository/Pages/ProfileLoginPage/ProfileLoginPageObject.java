
package com.automation.mobile.iOS.apps.ObjectRepository.Pages.ProfileLoginPage;

import java.time.Duration;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.testng.Assert;
import org.testng.Reporter;
import com.automation.core.mobile.iOS.iOSGenericMethods;
import io.appium.java_client.PerformsTouchActions;
import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

/**
 * @author 300021281
 */

public class ProfileLoginPageObject {
	iOSGenericMethods objiOSGenericMethods;
	IOSDriver<IOSElement> iDriver;

	public ProfileLoginPageObject(IOSDriver<IOSElement> iDriver) {

		PageFactory.initElements(new AppiumFieldDecorator(iDriver), this);
		objiOSGenericMethods = new iOSGenericMethods(iDriver);


	}

	@iOSXCUITFindBy(iOSClassChain = "**/XCUIElementTypeButton[`name == \"Profile\"`]")
	public IOSElement profileBtn;
	@FindBy(xpath = "//XCUIElementTypeOther[@name=\"LOG IN\"]")
	public IOSElement login;

	@FindBy(xpath = "//XCUIElementTypeStaticText[@name=\"Email address\"]")
	public IOSElement emailFieldTxt;

	@FindBy(xpath = "//XCUIElementTypeStaticText[@name=\"Password\"]")
	public IOSElement passwordFieldTxt;

	@FindBy(xpath = "//XCUIElementTypeButton[@name=\"LOG IN\"]")
	public IOSElement loginBtn;

	@iOSFindBy(xpath = "(//XCUIElementTypeOther[@name=\"LOG OUT\"])[1]")
	public IOSElement logoutBtn;

	@iOSFindBy(accessibility = "Orders")
	public IOSElement orderTab;

	public void swipeDown(IOSDriver<IOSElement> iDriver, int duration, int noOfSwipes) {

		org.openqa.selenium.Dimension size = iDriver.manage().window().getSize();
		int starty = (int) (size.height * 0.85);
		int endy = (int) (size.height * 0.45);
		int width = size.width / 2;
		for (int i = 0; i < noOfSwipes; i++) {
			new TouchAction(iDriver).press(width, starty).waitAction(Duration.ofMillis(duration)).moveTo(width, endy)
					.release().perform();
		}
	}

	

	public void fluentWait(WebElement ele) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(iDriver).withTimeout(30, TimeUnit.SECONDS)
				.pollingEvery(10, TimeUnit.SECONDS).ignoring(NoSuchElementException.class);
		ele = wait.until(new Function<WebDriver, WebElement>() {
			public WebElement apply(WebDriver driver) {
				System.out.println("Fluent wait");
				return driver.findElement(By.cssSelector("#u_0_3"));

			}
		});
	}


	/**
	 * @author 300019221 / Aravindanath Address objects
	 */
	@iOSFindBy(xpath = "//XCUIElementTypeOther[@name=\"Addresses\"]")
	public IOSElement AddressesTab;

	/**
	 * @author 300021275 Lata
	 * 
	 */

	@iOSFindBy(xpath = "//XCUIElementTypeOther[@name=\"Address\"]")
	public IOSElement AddressTab;

	@iOSFindBy(xpath = "//XCUIElementTypeStaticText[@name=\"REMOVE\"]")
	public IOSElement removeAddressButton;

	@iOSFindBy(xpath = "//XCUIElementTypeStaticText[@name=\"DELETE\"]")
	public IOSElement deleteButton;

	@iOSFindBy(xpath = "//XCUIElementTypeNavigationBar[@name=\"MYNWebView\"]/XCUIElementTypeButton")
	public IOSElement BackButtonOnAddress;

	/**
	 * @author 300019221 / Aravindanath
	 * 
	 */

	@iOSFindBy(xpath = "(//XCUIElementTypeOther[@name=\" FACEBOOK\"])[2]")
	public IOSElement faceBookButton;

	@iOSFindBy(xpath = "//XCUIElementTypeAlert//XCUIElementTypeButton[@name='Continue']")
	public IOSElement continueButton;
	/**
	 * @author 300019221 /Arvindanath
	 */
	@iOSFindBy(accessibility = "Continue")
	public IOSElement ContinueButtonOnFBLogin;

	@iOSFindBy(xpath = "//XCUIElementTypeAlert//XCUIElementTypeButton[@name='Allow']")
	public IOSElement allowButton;

	/******************* getters **********/

	public IOSElement getProfile() {
		objiOSGenericMethods.CheckIOSElementFound(profileBtn, "profileBtn");
		return profileBtn;
	}

	public IOSElement getLogin() {
		objiOSGenericMethods.CheckIOSElementFound(login, "login");
		return login;
	}

	public IOSElement getEmailField() {
		objiOSGenericMethods.CheckIOSElementFound(emailFieldTxt, "emailFieldTxt");
		return emailFieldTxt;
	}

	public IOSElement getPasswordField() {
		objiOSGenericMethods.CheckIOSElementFound(passwordFieldTxt, "passwordFieldTxt");
		return passwordFieldTxt;
	}

	public IOSElement getLoginButton() {
		objiOSGenericMethods.CheckIOSElementFound(loginBtn, "loginBtn");
		return loginBtn;
	}

	public IOSElement getLogout() {
		objiOSGenericMethods.CheckIOSElementFound(logoutBtn, "logoutBtn");
		return logoutBtn;
	}

	public IOSElement getAddressTab() {
		objiOSGenericMethods.CheckIOSElementFound(AddressesTab, "Address Tab");
		return AddressesTab;
	}

	public IOSElement getSingleAddressTab() {
		objiOSGenericMethods.CheckIOSElementFound(AddressTab, "Address Tab");
		return AddressTab;
	}

	public IOSElement getRemoveAddressTab() {
		objiOSGenericMethods.CheckIOSElementFound(removeAddressButton, "Remove address");
		return removeAddressButton;
	}

	public IOSElement getDeleteButton() {
		objiOSGenericMethods.CheckIOSElementFound(deleteButton, "Delete Button");
		return deleteButton;
	}

	public IOSElement getOrderTab() {
		objiOSGenericMethods.CheckIOSElementFound(orderTab, "Order Tab");
		return orderTab;
	}

	public IOSElement getBackButtonOnAddress() {
		objiOSGenericMethods.CheckIOSElementFound(BackButtonOnAddress, "Address tab");
		return BackButtonOnAddress;
	}

	public IOSElement getFaceBookButton() {
		objiOSGenericMethods.CheckIOSElementFound(faceBookButton, "FaceBook");
		return faceBookButton;
	}

	public IOSElement getContinueButton() {
		objiOSGenericMethods.CheckIOSElementFound(continueButton, "continue Button");
		return continueButton;
	}

	/**
	 * @author 300019221 / Aravindanath created new getter
	 * @return
	 */
	public IOSElement getContinueButtonOnFb() {
		objiOSGenericMethods.CheckIOSElementFound(ContinueButtonOnFBLogin, "continue Button on FB");
		return continueButton;
	}

	public IOSElement getAllowButton() {
		objiOSGenericMethods.CheckIOSElementFound(allowButton, "allow Button");
		return allowButton;
	}

	/*****************
	 * methods
	 * 
	 * @throws InterruptedException
	 **********/
	/**
	 * @author 300019221 Aravainda Added popup handling method
	 * @author 300019221 Added wait
	 */

	public void clickOnProfileButton() throws InterruptedException {
		objiOSGenericMethods.waitForElementVisibility(getProfile());
		if (getProfile().isDisplayed()) {
			// objiOSGenericMethods.acceptAlert();
			objiOSGenericMethods.clickOnIOSElement(getProfile(), "Successfully click on profile button");
			// objiOSGenericMethods.acceptAlert();
		}
	}

	/**
	 * @author 300019221 Aravainda Added popup handling method
	 */
	public void clickOnLogin() throws InterruptedException {
		try {
			if (getLogin().isDisplayed()) {
				objiOSGenericMethods.clickOnIOSElement(getLogin(), "Successfully click on login button");
			}
		} catch (Exception e) {

			System.out.println("User is already log in! hence no need to login");
		}

		// objiOSGenericMethods.acceptAlert();

	}

	/**
	 * @author 300019221 / Aravindanath This method will log-out if any other user
	 *         is already logged in!
	 */

	public void clickOnLogOut() {
		try {
			if (getLogout().isDisplayed()) {
				objiOSGenericMethods.clickOnIOSElement(getLogout(), "Successfully click on Logout button");
				System.err.println("User has logged out!");
			}
		} catch (Exception e) {
			System.out.println("User is not logged in!");
		}

	}

	public void loginInApp(String em, String pw) {
		try {
			if (emailFieldTxt.isDisplayed()) {
				emailFieldTxt.sendKeys(em);
				Reporter.log("Email succesfully entered");
			}
			if (passwordFieldTxt.isDisplayed()) {
				passwordFieldTxt.sendKeys(pw);
				Reporter.log("Password succesfully entered");
			}
		} catch (Exception e) {
		}

	}

	/**
	 * @author 300019221 / Aravindanath This method will click on login button if
	 *         any other user is not logged in!
	 * 
	 * @author 300019221 Aravinda Added Allow popup method.
	 */

	public void clickOnLoginButton() {
		try {
			if (getLoginButton().isDisplayed()) {
				objiOSGenericMethods.clickOnIOSElement(getLoginButton(), "Successfully click on login button button");
			}
		} catch (Exception e) {
			System.out.println("User is already log in!");
		}
	}

	/**
	 * @author 300019221 / Aravindanath This method will click on facebook button
	 * 
	 * @author 300019221 Replaced thread.sleep with webdriver wait
	 */

	public void clickOnFaceBook() {
		try {
			if (getFaceBookButton().isDisplayed()) {
				objiOSGenericMethods.clickOnIOSElement(getFaceBookButton(), "Successfully clicked on Facebook button");
				objiOSGenericMethods.waitForElementVisibility(getContinueButton());
				objiOSGenericMethods.clickOnIOSElement(getContinueButton(), "Successfully clicked on continue button");
			}

		} catch (Exception e) {
			System.out.println("Facebook button is not displayed!");
		}

	}

	/**
	 * @author 300019221 Aravindanath
	 * 
	 */
	public void clickOnContinueFB() {
		try {
			objiOSGenericMethods.waitForElementVisibility(getContinueButtonOnFb());
			if (getContinueButtonOnFb().isDisplayed()) {
				objiOSGenericMethods.click(getContinueButtonOnFb());
				System.out.println("Continue button is displayed!");
			}

		} catch (Exception e) {
			System.out.println("Continue button is not displayed!");
		}

	}

	/**
	 * @author 300019221 / Aravindanath This method will reset the address
	 * @throws InterruptedException
	 */

	/**
	 * @author 300021275 - Lata
	 * 
	 *         Modified - This method will click on Address tab when there is
	 *         single/multiple address
	 * 
	 * @author 300019221 Relaced thread.sleep with webdriver wait
	 * 
	 * 
	 * 
	 *
	 */

	public void removeAddress() throws InterruptedException {
		if (getSingleAddressTab().isDisplayed()) {
			getSingleAddressTab().click();
		} else if (getAddressTab().isDisplayed()) {
			getAddressTab().click();
		}
		try {
			objiOSGenericMethods.swipeDown(100, 1);
			// objiOSGenericMethods.waitForElementVisibility(getRemoveAddressTab());
			if (getRemoveAddressTab().isDisplayed()) {
				getRemoveAddressTab().click();
				getDeleteButton().click();
				getBackButtonOnAddress().click();
			}
		} catch (Exception e) {
			if (getBackButtonOnAddress().isDisplayed()) {
				getBackButtonOnAddress().click();
			}
			System.out.println("No address to remove!");
		}

	}

	

	/**
	 * @author 300019221 / Aravindanath This method will login to facebook
	 * @param email
	 * @param password
	 * @throws InterruptedException
	 */
	public void loginToFaceBook(String email, String password) throws InterruptedException {

		try {
			WebElement emailid = iDriver.findElement(By.cssSelector("#m_login_email"));
			WebElement pass = iDriver.findElement(By.cssSelector("#m_login_password"));
			WebElement loginButton = iDriver.findElement(By.cssSelector("#u_0_5"));
			WebElement continueLogin = iDriver.findElement(By.cssSelector("#u_0_9"));
			if (emailid.isDisplayed()) {
				emailid.sendKeys(email);
				pass.sendKeys(password);
				loginButton.click();
			}
			if (continueLogin.isDisplayed()) {
				continueLogin.click();
			}
		} catch (Exception e) {
			System.out.println("user can continue with face book login!");
		}

	}

	
}
