package com.automation.mobile.Android.MobileWeb.ObjectRepository.HomeObjects;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;

import com.automation.core.mobile.Android.AndroidGenericMethods;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class HamburgerPageObjects {

	AndroidGenericMethods objAndroidGenericMethods;

	AndroidDriver<AndroidElement> aDriver;
	HomePageObjects objHomepageObjects;

	public HamburgerPageObjects(AndroidDriver<AndroidElement> aDriver) {
		PageFactory.initElements(new AppiumFieldDecorator(aDriver), this);
		objAndroidGenericMethods = new AndroidGenericMethods(aDriver);
		objHomepageObjects = new HomePageObjects(aDriver);
		this.aDriver = aDriver;
	}
	/*
	 * Sign In option 300021281
	 */

	@FindBy(xpath = "//*[@class='naviWrapper']/a[contains(text(),'Sign in')]")
	public AndroidElement signInOption;

	@FindBy(xpath = "//input[ @type='text' and @class=\"form-control\"]")
	public AndroidElement emailField;

	@FindBy(xpath = "//input[ @type='password' and @class='form-control has-feedback']")
	public AndroidElement passwordField;

	@FindBy(xpath = "//button[@class='btn primary  lg block ']")
	public AndroidElement signInButton;

	/*
	 * Created by subhasis For Sign In with Facebbok.
	 */

	@FindBy(xpath = "//button[@class='btn default   block ' and @id='fbLoginButton']")
	public AndroidElement facebookLoginOption;

	/*
	 * //input[@type='email' and @class='_56bg _4u9z _5ruq']
	 * 
	 * //input[@type='text' and @class ='inputtext _55r1 inputtext _1kbt
	 * inputtext _1kbt']
	 */

	@FindBy(xpath = "//input[@type='email' and @class='_56bg _4u9z _5ruq']")
	public AndroidElement facebookEmailField;

	@FindBy(xpath = "//input[@type='password' and @class='_56bg _4u9z _27z2']")
	public AndroidElement facebookPasswordField;

	@FindBy(xpath = "//button[@type='button' and @name='login']")
	public AndroidElement facebookLoginButton;

	/**
	 * For sign in with Google Cretaed By Subhasis
	 */

	@FindBy(xpath = "//button[@class='btn default   block ' and @id='gpLoginButton']")
	public AndroidElement googleLoginOption;

	@FindBy(xpath = "//input[@type='email' and @class='whsOnd zHQkBf']")
	public AndroidElement googleEmailFiled;

	@FindBy(xpath = "//div[@role='button' and @id='identifierNext']")
	public AndroidElement nextButton;

	@FindBy(xpath = "//input[@type='password' and @class='whsOnd zHQkBf']")
	public AndroidElement googlePasswordField;

	@FindBy(xpath = "//div[@role='button' and @id='passwordNext']")
	public AndroidElement passwordNextButton;

	/*
	 * SignUp option 300021281
	 */

	// public void setFacebookPasswordField(AndroidElement
	// facebookPasswordField) {
	// this.facebookPasswordField = facebookPasswordField;
	// }

	@FindBy(xpath = "//a[contains (text(),'Sign up') and @class='naviLevel']")
	public AndroidElement signUpOption;

	@FindBy(xpath = "//input[@type=\"text\" and @class='form-control' and @placeholder='Your Email Address']")
	public AndroidElement signupEmailField;

	@FindBy(xpath = "//input[@type='password' and @class='form-control has-feedback' and @placeholder='Choose Password']")
	public AndroidElement signupPasswordField;

	@FindBy(xpath = "//input[@type='text' and @class='form-control' and @placeholder='Full Name']")
	public AndroidElement signupFullName;

	@FindBy(xpath = "//input[@type='number' and @class='form-control' and @placeholder='Mobile (For Order Updates)']")
	public AndroidElement signupMobilenumberField;

	@FindBy(xpath = "//button[contains (text(),'Man') and @class='btn default  lg  ']")
	public AndroidElement signupGenderButton;

	@FindBy(xpath = "//button[contains (text(),'CREATE ACCOUNT') and @class='btn primary  lg block ']")
	public AndroidElement createAccount;

	@FindBy(xpath = "//div[@class=\"mobile\"]/h4")
	public AndroidElement verifyUsermailID;

	@FindBy(xpath = "//a[contains(text(),'My Account')]")
	public AndroidElement myAccount;

	public AndroidElement getSignInOption() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(signInOption, "signInOption");
		return signInOption;
	}

	public AndroidElement getEmailField() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(emailField, "emailField");
		return emailField;
	}

	public AndroidElement getPasswordField() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(passwordField, "passwordField");
		return passwordField;
	}

	public AndroidElement getSignInButton() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(signInButton, "signInButton");
		return signInButton;
	}

	public AndroidElement getSignUpOption() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(signUpOption, "signUpOption");
		return signUpOption;
	}

	public AndroidElement getSignupEmailField() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(signupEmailField, "signupEmailField");
		return signupEmailField;
	}

	public AndroidElement getSignupPasswordField() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(signupPasswordField, "signupPasswordField");
		return signupPasswordField;
	}

	public AndroidElement getSignupFullName() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(signupFullName, "signupFullName");
		return signupFullName;
	}

	public AndroidElement getSignupMobilenumberField() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(signupMobilenumberField, "signupMobilenumberField");
		return signupMobilenumberField;
	}

	public AndroidElement getSignupGenderButton() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(signupGenderButton, "signupGenderButton");
		return signupGenderButton;
	}

	public AndroidElement getCreateAccount() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(createAccount, "createAccount");
		return createAccount;
	}

	public AndroidElement getVerifyUsermailID() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(verifyUsermailID, "verifyUsermailID");
		return verifyUsermailID;
	}

	/*
	 * SignIn
	 */

	public AndroidElement getMyAccount() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(myAccount, "myAccount");
		return myAccount;
	}

	/**
	 * Facebook getter created by subhasis
	 */

	public AndroidElement getFacebookLoginOption() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(facebookLoginOption, "Facebook Button");
		return facebookLoginOption;
	}

	public AndroidElement getFacebookEmailField() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(facebookEmailField, "Facebook Email");
		return facebookEmailField;
	}

	public AndroidElement getFacebookPasswordField() {
		return facebookPasswordField;
	}

	public AndroidElement getFacebookLoginButton() {
		return facebookLoginButton;
	}

	/**
	 * Google Getter Created By Subhasis
	 */

	public AndroidElement getGoogleLoginOption() {
		return googleLoginOption;
	}

	public AndroidElement getGoogleEmailFiled() {
		return googleEmailFiled;
	}

	public AndroidElement getGooglePasswordField() {
		return googlePasswordField;
	}

	public AndroidElement getPasswordNextButton() {
		return passwordNextButton;
	}

	/**
	 * Modified By:Aishurya:Changed string parameter,which is being used in
	 * reporing
	 */
	public void clickOnSignInOption() {
		objAndroidGenericMethods.clickOnAndroidElement(getSignInOption(), "SignInOption");
		System.out.println("SignIn option is clicked succesfully");
	}

	public void enterEmailAddress(String em, String pw) {
		emailField.sendKeys(em);
		passwordField.sendKeys(pw);
	}

	/**
	 * Modified By:Aishurya:Changed string parameter,which is being used in
	 * reporing
	 */
	public void clickOnSignInButton() {
		objAndroidGenericMethods.clickOnAndroidElement(getSignInButton(), "SignInOption");
		System.out.println("Login Button clicked succesfully ");

	}

	/**
	 * commented the method as the sign in method is already available
	 */

	// public void clickOnSignInbutton() {
	// objAndroidGenericMethods.clickOnAndroidElement(getSignInOption(),"signInOption");
	// System.out.println("SignIn option is clicked succesfully");
	// }

	/*
	 * SignUp
	 */

	/**
	 * Modified By:Aishurya:Changed string parameter,which is being used in
	 * reporing
	 */
	public void clickOnSignUpOption() {
		objAndroidGenericMethods.clickOnAndroidElement(getSignUpOption(), "SignUpOption");
		System.out.println("Succesfully click on signIn button");
	}

	public void signUpInApp(String em, String pw, String name, String mnumber) {
		signupEmailField.sendKeys(em);
		signupPasswordField.sendKeys(pw);
		signupFullName.sendKeys(name);
		signupMobilenumberField.sendKeys(mnumber);
		System.out.println("Succesfully enterd email,password,name and mobile number ");
	}

	/**
	 * Modified By:Aishurya:Changed string parameter,which is being used in
	 * reporing
	 */
	public void clickOnGender() {
		objAndroidGenericMethods.clickOnAndroidElement(getSignupGenderButton(), "SignupGenderButton");
		System.out.println("Succesfully click gender button ");

	}

	/**
	 * Modified By:Aishurya:Changed string parameter,which is being used in
	 * reporing
	 */
	public void clickOnCreateAccountButton() {
		objAndroidGenericMethods.clickOnAndroidElement(getCreateAccount(), "CreateAccount");
		System.out.println("Create an account click succesfully");
	}

	/**
	 * Modified By:Aishurya:Changed string parameter,which is being used in
	 * reporing
	 */
	public void clickOnLoginButton() {
		objAndroidGenericMethods.clickOnAndroidElement(getSignInButton(), "SignInButton");
		System.out.println("SignIn button is clicked succesfully ");

	}

	/**
	 * Modified By:Aishurya:Changed string parameter,which is being used in
	 * reporing
	 */
	public void clickOnMyAccount() {
		objAndroidGenericMethods.clickOnAndroidElement(getMyAccount(), "MyAccount");
		System.out.println("My account clicked succesfully ");

	}

	/*
	 * Created By Subhasis Facebook Sign In Flow.
	 */

	/**
	 * Modified By:Aishurya:Changed string parameter,which is being used in
	 * reporing
	 */
	public void clickOnFacebook() {
		objAndroidGenericMethods.clickOnAndroidElement(getFacebookLoginOption(), "FacebookOption");
		System.out.println("Succesfully clicked on Facebook button");
	}

	public void enterFacebookEmailId(String em, String pw) {

		getFacebookEmailField().clear();
		getFacebookEmailField().sendKeys(em);
		getFacebookPasswordField().sendKeys(pw);
		System.out.println("Enter email and password in email and password field");

	}

	public void clickOnFacebookLoginButton() {
		objAndroidGenericMethods.clickOnAndroidElement(getFacebookLoginButton(), "Login Button");
	}

	/**
	 * Created By Subhasis Google Sign In Flow Modified By:Aishurya:Changed
	 * string parameter,which is being used in reporing
	 */

	public void clickOnGoogle() {
		objAndroidGenericMethods.clickOnAndroidElement(getGoogleLoginOption(), "GoogleOption");
		System.out.println("Succesfully clicked on Google Button");
	}

	public void enterGoogleEmailField(String em, String pw) {
		getGoogleEmailFiled().sendKeys(em);
		getNextButton().click();
		getGooglePasswordField().sendKeys(pw);
		getPasswordNextButton().click();
	}

	/**
	 * Assertions
	 */
	public void assertUserEmailID(String namemailID) throws InterruptedException {

		AndroidElement element = getVerifyUsermailID();
		objAndroidGenericMethods.VerifyTwoString(element, namemailID);
	}

	// @FindBy(xpath = "//android.widget.Button[@text='ALLOW']")
	// public AndroidElement allowbutton;
	// }
	/****************************** Assertions ******************************************/
	/**
	 * Assertions::Amba
	 */

	@FindBy(xpath = "//span[@class='icon icon-menu']")
	public AndroidElement hamburgerIcon;
	@FindBy(xpath = "('/html[1]/body[1]/div[1]/div[2]/div[2]/div[1]/div[1]/h4[1]')")
	public AndroidElement userName;

	public void verifyUserId(String ExpectedData) {

		hamburgerIcon.click();
		clickOnMyAccount();

		objAndroidGenericMethods.VerifyStringShouldNotEmpty(userName, "userName");
	}

	/*
	 * SignUp option Gmail amba
	 */
	@FindBy(id = "gpLoginButton")
	public AndroidElement GmailLogin;

	@FindBy(xpath = "//p[text()='Use another account']")
	public AndroidElement GmailOtherAccount;

	@FindBy(xpath = "//input[@type='email']")
	public AndroidElement EnterGmailEmail;

	@FindBy(xpath = "//span[text()='Next']")
	public AndroidElement NextButton;

	@FindBy(xpath = "//input[text()='Enter your password']")
	public AndroidElement EnterGmailPassword;
	@FindBy(xpath = "//button[text()='Logout']")
	public AndroidElement logoutbtn;

	public AndroidElement getLogoutbtn() {
		return logoutbtn;
	}

	public AndroidElement getNextButton() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(NextButton, " NextButton");
		return NextButton;
	}

	public AndroidElement getEnterGmailEmail() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(EnterGmailEmail, "EnterGmailEmail");
		return EnterGmailEmail;
	}

	public AndroidElement getEnterGmailPassword() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(EnterGmailPassword, "EnterGmailPassword");
		return EnterGmailPassword;
	}

	public AndroidElement getGmailLogin() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(GmailLogin, "GmailLogin");
		return GmailLogin;
	}

	public AndroidElement getGmailOtherAccount() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(GmailOtherAccount, " GmailOtherAccount");
		return GmailOtherAccount;
	}

	// ***********Methods************//

	/**
	 * Modified By:Aishurya:Changed string parameter,which is being used in
	 * reporing
	 */
	public void clickOnGoogleSignUpOption() {
		objAndroidGenericMethods.clickOnAndroidElement(getGmailLogin(), "GmailOption");
		System.out.println("Succesfully clicked on Google button");
	}

	/**
	 * Modified By:Aishurya:Changed string parameter,which is being used in
	 * reporing
	 */
	public void clickOnGmailOtherAccount() {
		objAndroidGenericMethods.clickOnAndroidElement(getGmailOtherAccount(), "GmailOption");
		System.out.println("Succesfully clicked on other Google button");
	}

	/**
	 * Modified By:Aishurya:Changed string parameter,which is being used in
	 * reporing
	 */
	public void clickOnNext() {
		objAndroidGenericMethods.clickOnAndroidElement(getNextButton(), "NextButton");
		System.out.println("Succesfully clicked on NextButton");
	}

	public void EnterGmailPassword(String pw) {
		EnterGmailPassword.sendKeys(pw);
		System.out.println("Succesfully Entered Password");
	}

	public void EnterGmailEmail(String em) {
		EnterGmailEmail.sendKeys(em);
		System.out.println("Succesfully GmailId Entered");
	}

	public void signUpGoogle(String em, String pw) {
		clickOnGoogleSignUpOption();
		clickOnGmailOtherAccount();
		EnterGmailEmail.sendKeys(em);
		NextButton.click();
		EnterGmailPassword.sendKeys(pw);
		NextButton.click();

		System.out.println("Succesfully enterd email,password,name and mobile number ");
	}

	public void SwitchToOtherWindow() {
		String winHandleBefore = aDriver.getWindowHandle();
		System.out.println(winHandleBefore);
		for (String handle : aDriver.getWindowHandles()) {
			aDriver.switchTo().window(handle);
		}
	}
	/**
	 * 
	 * Added by - Anu
	 * 
	 * method to click on logout button
	 * 
	 */

	public void clickLogout() {

		objHomepageObjects.clickOnHamburgerButton();
		clickOnMyAccount();
		objAndroidGenericMethods.scrollDown(getLogoutbtn(), 20);
	}
	/**
	 * 
	 * Added by - Anu
	 * 
	 * method to verify user is logged out or not
	 * 
	 */

	public void verifyUserIsLoggedOut() {

		objHomepageObjects.clickOnHamburgerButton();
		try {
			if (getSignInOption().isDisplayed())

			{
				Reporter.log("Passed : User has logged out!");
			}
		} catch (Exception e) {
			Reporter.log("Failed : User is not logged out!");
		}
	}

	/**
	 * 
	 * Added by - Anu
	 * 
	 * method to verify session id before and after logout
	 * 
	 */

	public void logoutAndVerifySessionId() {

		Cookie LoginCookie = aDriver.manage().getCookieNamed("user_uuid");
		String loginSessionId = LoginCookie.getValue();
		Reporter.log("Session Id before logout is : " + loginSessionId);
		clickLogout();
		verifyUserIsLoggedOut();
		Cookie LogoutCookie = aDriver.manage().getCookieNamed("user_uuid");
		String logoutSessionId = LogoutCookie.getValue();
		Reporter.log("Session Id after logout is : " + logoutSessionId);
		if (loginSessionId.equalsIgnoreCase(logoutSessionId))
		{
			Reporter.log("Failed: Session Id's before and after logout are same!");
		} else {
			Reporter.log("Passed: Session Id's before and after logout are different!");

		}
	}
}