package com.automation.mobile.iOS.MobileWeb.ObjectRepository.HomeObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.automation.core.mobile.iOS.iOSGenericMethods;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class HamburgerPageObjects {

	public IOSDriver<IOSElement> iDriver;
	iOSGenericMethods objiOSGenericMethods;

	public HamburgerPageObjects(IOSDriver<IOSElement> iDriver) {
		PageFactory.initElements(new AppiumFieldDecorator(iDriver), this);
		objiOSGenericMethods = new iOSGenericMethods(iDriver);

	}
	/*
	 * Sign In option 300021281
	 */

	@FindBy(xpath = "//*[@class='naviWrapper']/a[contains(text(),'Sign in')]")
	public IOSElement signInOption;

	@FindBy(xpath = "//input[ @type='text' and @class=\"form-control\"]")
	public IOSElement emailField;

	@FindBy(xpath = "//input[ @type='password' and @class='form-control has-feedback']")
	public IOSElement passwordField;

	@FindBy(xpath = "//button[contains (text(),'SIGN IN') and @class='btn primary  lg block ']")
	public IOSElement signInButton;
	
	/**
	 * @author 300021275 - Lata
	 * For Sign In with Facebbok.
	 */
	
	@FindBy(xpath="//button[@class='btn default   block ' and @id='fbLoginButton']")
	public IOSElement facebookLoginOption;
	
	@FindBy(xpath="//input[@type='email' and @class='_56bg _4u9z _5ruq']")
	public IOSElement facebookEmailField;
	
	@FindBy(xpath="//input[@type='password' and @class='_56bg _4u9z _27z2']")
	public IOSElement facebookPasswordField;
	
	@FindBy(xpath="//button[@type='button' and @name='login']")
	public IOSElement facebookLoginButton;
	
	/**
	 * @author 300021275 - Lata
	 * For sign in with Google
	 * 
	 */
	
	@FindBy(xpath="//button[@class='btn default   block ' and @id='gpLoginButton']")
	public IOSElement googleLoginOption;
	
	@FindBy(xpath="//input[@type='email' and @class='whsOnd zHQkBf']")
	public IOSElement googleEmailFiled;
	
	@FindBy(xpath="//div[@role='button' and @id='identifierNext']")
	public IOSElement nextButton;
	
	@FindBy(xpath="//input[@type='password' and @class='whsOnd zHQkBf']")
	public IOSElement googlePasswordField;
	
	@FindBy(xpath="//div[@role='button' and @id='passwordNext']")
	public IOSElement passwordNextButton;
	
	@FindBy(xpath = "//span[text()='Next']")
	public IOSElement NextButton;

	/*
	 * SignUp option 300021281
	 */

	@FindBy(xpath = "//a[contains (text(),'Sign up') and @class='naviLevel']")
	public IOSElement signUpOption;

	@FindBy(xpath = "//input[@type=\"text\" and @class='form-control' and @placeholder='Your Email Address']")
	public IOSElement signupEmailField;

	@FindBy(xpath = "//input[@type='password' and @class='form-control has-feedback' and @placeholder='Choose Password']")
	public IOSElement signupPasswordField;

	@FindBy(xpath = "//input[@type='text' and @class='form-control' and @placeholder='Full Name']")
	public IOSElement signupFullName;

	@FindBy(xpath = "//input[@type='number' and @class='form-control' and @placeholder='Mobile (For Order Updates)']")
	public IOSElement signupMobilenumberField;

	@FindBy(xpath = "//button[contains (text(),'Man') and @class='btn default  lg  ']")
	public IOSElement signupGenderButton;

	@FindBy(xpath = "//button[contains (text(),'CREATE ACCOUNT') and @class='btn primary  lg block ']")
	public IOSElement createAccount;

	@FindBy(xpath = "//div[@class=\"mobile\"]/h4")
	public IOSElement verifyUsermailID;

	@FindBy(xpath = "//a[contains(text(),'My Account')]")
	public IOSElement myAccount;

	@FindBy(xpath="(//div[@class='naviWrapper']/a)[5]")
	public IOSElement contactUs;

	
	/**
	 * Modified By - Lata
	 * Old locator - //button[@type='submit']
	 */
	
	@FindBy(xpath="//button[text()='Logout' and @type='submit']")
	public IOSElement logout;

	public IOSElement getLogout() {
		objiOSGenericMethods.CheckIOSElementFound(logout, "logout");
		return logout;
	}

	public IOSElement getcontactUs() {
		objiOSGenericMethods.CheckIOSElementFound(contactUs, "contactUs");
		return contactUs;
	}

	public IOSElement getSignInOption() {
		objiOSGenericMethods.CheckIOSElementFound(signInOption, "signInOption");
		return signInOption;
	}

	public IOSElement getEmailField() {
		objiOSGenericMethods.CheckIOSElementFound(emailField, "emailField");
		return emailField;
	}

	public IOSElement getPasswordField() {
		objiOSGenericMethods.CheckIOSElementFound(passwordField, "passwordField");
		return passwordField;
	}

	public IOSElement getSignInButton() {
		objiOSGenericMethods.CheckIOSElementFound(signInButton, "signInButton");
		return signInButton;
	}

	public IOSElement getSignUpOption() {
		objiOSGenericMethods.CheckIOSElementFound(signUpOption, "signUpOption");
		return signUpOption;
	}

	public IOSElement getSignupEmailField() {
		objiOSGenericMethods.CheckIOSElementFound(signupEmailField, "signupEmailField");
		return signupEmailField;
	}

	public IOSElement getSignupPasswordField() {
		objiOSGenericMethods.CheckIOSElementFound(signupPasswordField, "signupPasswordField");
		return signupPasswordField;
	}

	public IOSElement getSignupFullName() {
		objiOSGenericMethods.CheckIOSElementFound(signupFullName, "signupFullName");
		return signupFullName;
	}

	public IOSElement getSignupMobilenumberField() {
		objiOSGenericMethods.CheckIOSElementFound(signupMobilenumberField, "signupMobilenumberField");
		return signupMobilenumberField;
	}

	public IOSElement getSignupGenderButton() {
		objiOSGenericMethods.CheckIOSElementFound(signupGenderButton, "signupGenderButton");
		return signupGenderButton;
	}

	public IOSElement getCreateAccount() {
		objiOSGenericMethods.CheckIOSElementFound(createAccount, "createAccount");
		return createAccount;
	}

	public IOSElement getVerifyUsermailID() {
		objiOSGenericMethods.CheckIOSElementFound(verifyUsermailID, "verifyUsermailID");
		return verifyUsermailID;
	}
	public IOSElement getNextButton() {
		objiOSGenericMethods.CheckIOSElementFound(NextButton, " NextButton");
		return NextButton;
	}
	/**
	 * @author 300021275 - Lata
	 * Facebook getter
	 * 
	 */
	
	public IOSElement getFacebookLoginOption() {
		objiOSGenericMethods.CheckIOSElementFound(facebookLoginOption, "Facebook Button");
		return facebookLoginOption;
	}
	
	public IOSElement getFacebookEmailField() {
		objiOSGenericMethods.CheckIOSElementFound(facebookEmailField, "Facebook Email");
		return facebookEmailField;
	}
	public IOSElement getFacebookPasswordField() {
		objiOSGenericMethods.CheckIOSElementFound(facebookPasswordField, "Facebook Password");
		return facebookPasswordField;
	}
	
	public IOSElement getFacebookLoginButton() {
		objiOSGenericMethods.CheckIOSElementFound(facebookLoginButton, "Login Button");
		return facebookLoginButton;
	}

	/**
	 * @author 300021275 - Lata
	 * Google Getter
	 */
	
	public IOSElement getGoogleLoginOption() {
		objiOSGenericMethods.CheckIOSElementFound(googleLoginOption, "Login Button");
		return googleLoginOption;
	}
	
	public IOSElement getGoogleEmailFiled() {
		objiOSGenericMethods.CheckIOSElementFound(googleEmailFiled, "Login Email");
		return googleEmailFiled;
	}
	
	public IOSElement getGooglePasswordField() {
		objiOSGenericMethods.CheckIOSElementFound(googlePasswordField, "Login Password");
		return googlePasswordField;
	}
	
	public IOSElement getPasswordNextButton() {
		objiOSGenericMethods.CheckIOSElementFound(passwordNextButton, "Password next button");
		return passwordNextButton;
	}
	/*
	 * SignIn
	 */

	public IOSElement getMyAccount() {
		objiOSGenericMethods.CheckIOSElementFound(myAccount, "myAccount");
		return myAccount;
	}

	public void clickOnSignInOption() {
		objiOSGenericMethods.clickOnIOSElement(getSignInOption(), "clicked on signInOption");
		System.out.println("SignIn option is clicked succesfully");
	}

	public void enterEmailAddress(String em, String pw) {
		emailField.sendKeys(em);
		passwordField.sendKeys(pw);
	}

	public void clickOnSignInButton() {
		objiOSGenericMethods.clickOnIOSElement(getSignInButton(), "clicked on signInOption");
		System.out.println("Login Button clicked succesfully ");

	}
	
	

	/**
	 * commented the method as the sign in method is already available
	 */

	// public void clickOnSignInbutton() {
	// objiOSGenericMethods.clickOnIOSElement(getSignInOption(),"clicked on
	// signInOption");
	// System.out.println("SignIn option is clicked succesfully");
	// }

	/*
	 * SignUp
	 */

	public void clickOnSignUpOption() {
		objiOSGenericMethods.clickOnIOSElement(getSignUpOption(), "signUp Option");
		System.out.println("Succesfully click on signIn button");
	}

	public void signUpInApp(String em, String pw, String name, String mnumber) {
		signupEmailField.sendKeys(em);
		signupPasswordField.sendKeys(pw);
		signupFullName.sendKeys(name);
		signupMobilenumberField.sendKeys(mnumber);
		System.out.println("Succesfully enterd email,password,name and mobile number ");
	}

	public void clickOnGender() {
		objiOSGenericMethods.clickOnIOSElement(getSignupGenderButton(), "signup Gender Button");
		System.out.println("Succesfully click gender button ");

	}

	public void clickOnCreateAccountButton() {
		objiOSGenericMethods.clickOnIOSElement(getCreateAccount(), "create Account");
		System.out.println("Create an account click succesfully");
	}

	public void clickOnLoginButton() {
		objiOSGenericMethods.clickOnIOSElement(getSignInButton(), "signIn Button");
		System.out.println("SignIn button is clicked succesfully ");

	}

	public void clickOnMyAccount() {
		objiOSGenericMethods.clickOnIOSElement(getMyAccount(), "MyAccount");
		System.out.println("My account clicked succesfully ");

	}

	/**
	 * Assertions
	 */
	public void assertUserEmailID(String namemailID) {

		IOSElement element = getVerifyUsermailID();
		objiOSGenericMethods.VerifyTwoString(element, namemailID);
	}

	public void clickOnContactUs(){
		objiOSGenericMethods.clickOnIOSElement(getcontactUs(), "Contact Us");
	}


	
	/** Added by - Nitesh										
	 * Method to verify whether user is loggedin or logged out
	 */
	

	public boolean isLoggedIn() {
		try {
			getMyAccount().getText();
			System.out.println("User is logged in.");
			return true;
		} catch (Exception e) {
			System.out.println("User is not logged in.");
			return false;
		}
	}

	/**
	 * modified by madhu
	 * method as been changed
	 * before objiOSGenericMethods.clickOnIOSElement(getLogout(), "clicked on Logout.");
	 * it was not clicking on logout
	 * modified to objiOSGenericMethods.click(getLogout()); now it is clicking on logout1
	 */
	public void clickOnLogout(){
		objiOSGenericMethods.click(getLogout());
		//objiOSGenericMethods.clickOnIOSElement(getLogout(), "clicked on Logout.");
	}
	
	/**
	 * @author 300021275 - Lata
	 * Facebook Sign In Flow.
	 */
	
	
	public void clickOnFacebook() {
		objiOSGenericMethods.clickOnIOSElement(getFacebookLoginOption(), "FacebookOption");
		System.out.println("Succesfully clicked on Facebook button");
	}
	
	public void enterFacebookEmailId(String em,String pw) {
		getFacebookEmailField().clear();
		getFacebookEmailField().sendKeys(em);
		getFacebookPasswordField().sendKeys(pw);
		System.out.println("Enter email and password in email and password field");
		
	}
	
	public void clickOnFacebookLoginButton() {
		objiOSGenericMethods.clickOnIOSElement(getFacebookLoginButton(),"Login Button");
	}
	
	/**
	 * @author 300021275 - Lata
	 * Google Sign In Flow
	 */
	
	public void clickOnGoogle() {
		objiOSGenericMethods.clickOnIOSElement(getGoogleLoginOption(), "Google Option");
		System.out.println("Succesfully clicked on Google Button");
	}
	
	public void enterGoogleEmailField(String em,String pw) {
		getGoogleEmailFiled().sendKeys(em);
		getNextButton().click();
		getGooglePasswordField().sendKeys(pw);
		getPasswordNextButton().click();
	}
	public void SwitchToOtherWindow() {
		String winHandleBefore = iDriver.getWindowHandle();
		System.out.println(winHandleBefore);
		for (String handle : iDriver.getWindowHandles()) {

			iDriver.switchTo().window(handle);
		}
	}
	
}
