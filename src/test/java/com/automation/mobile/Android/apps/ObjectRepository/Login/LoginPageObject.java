package com.automation.mobile.Android.apps.ObjectRepository.Login;

import java.io.IOException;

import org.ini4j.InvalidFileFormatException;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;

import com.automation.core.mobile.Android.AndroidGenericMethods;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class LoginPageObject {
	AndroidGenericMethods objAndroidGenericMethods;
	AppiumDriver<MobileElement>aDriver;

	public LoginPageObject(AppiumDriver<MobileElement> aDriver) {
		PageFactory.initElements(new AppiumFieldDecorator(aDriver), this);
		objAndroidGenericMethods = new AndroidGenericMethods(aDriver);
		this.aDriver = aDriver;
	}

	/**
	 * Name: Vinay Description: This Xpath help to click on PopUp
	 */
	@FindBy(id = "com.myntra.android:id/tv_permissions_skip")
	public AndroidElement popUpbtn;

	/**
	 * Name: Vinay Description: this xapth help to click on "Hamburger" on
	 * homescreen 
	 */
	@FindBy(xpath = "//*[@content-desc='leftElement']")
	public AndroidElement hamburgerbtn;
	
	
	
	
	/**
	 * Name: Vinay Description: This xpath help to click "Login" buttin on
	 * Launching screen.
	 */
	@FindBy(xpath = "//android.widget.TextView[@text='LOG IN']")
	public AndroidElement firstloginbtn;
	@FindBy(xpath = "//android.widget.EditText[@index='0' and @resource-id='com.myntra.android:id/et_email_address']")
	public AndroidElement emailtxt;
	@FindBy(xpath = "//android.widget.EditText[@index='0' and @resource-id='com.myntra.android:id/et_loginregister_password']")
	public AndroidElement passwordtxt;
	@FindBy(xpath = "//android.widget.Button[@index='0']")
	public AndroidElement loginbtn;

	@FindBy(xpath = "//android.widget.TextView[@text='CONTACT US']")
	public AndroidElement contactUs;

	@FindBy(xpath = "android.widget.ImageButton[@content-desc='Navigate up']")
	public AndroidElement contactUsCancelButton;

	/**
	 * @author 300021280 Sneha
	 * Object identified for clicking facebook button
	 */
	@FindBy(xpath = "//android.widget.LinearLayout[@resource-id='com.myntra.android:id/btn_login_facebook_dummy']")
	public AndroidElement facebookbtn;
	
	/**
	 * @author 300021280 Sneha
	 * Object identified for email field of facebook web page
	 */
	@FindBy(xpath = "//android.widget.EditText[@content-desc='Email address or phone number' or @text='Email address or phone number' or @resource-id='m_login_email' ]")
	public AndroidElement emailFbtxt;

	/**
	 * @author 300021280 Sneha
	 * Object identified for password feild of facebook web page
	 */
	
	@FindBy(xpath = "//android.widget.EditText[@text='Facebook password' or @resource-id='m_login_password']")
	public AndroidElement passwordFbtxt;
	
	/**
	 * @author 300021280 Sneha
	 * Object identified for Login button of facebook web page
	 */
	@FindBy(xpath = "//android.widget.Button[@content-desc='Log In ' or @text='Log In ']")
	public AndroidElement LoginFbbtn;
	
	/**
	 * @author 300021280 Sneha
	 *  Object identified for continue button of facebook web page
	 */
	@FindBy(xpath = "//android.widget.Button[@text='Continue' or @resource-id='u_0_3' or @content-desc='Continue']")
	public AndroidElement confirmFb;
	
	/**
	 * Added by Rakesh: For permission
	 */
	@FindBy(xpath = "//android.widget.Button[@resource-id='com.android.packageinstaller:id/permission_allow_button']")
	public AndroidElement allowBtn;

	/**
	 * Added by:Rakesh:For permission
	 */
	@FindBy(xpath = "//android.widget.Button[@resource-id='com.myntra.android:id/btn_permission_ask']")
	public AndroidElement givePermission;

	/** Object identified for the signUp button displayed at launch screen
	 * @author 300021278 -Rakesh
	 */
	@FindBy(xpath = "//android.widget.RelativeLayout[@resource-id='com.myntra.android:id/btn_signup']")
	public AndroidElement firstSignUpBtn;
	
	/**
	 * Object identified for the signUp button displayed at launch screen
	 * @author 300021278 -Rakesh
	 */
	@FindBy(xpath = "//android.widget.LinearLayout[@resource-id='com.myntra.android:id/btn_email_address']")
	public AndroidElement eMailAddress;
	/**
	 * Object identified for the email signUp field displayed at signup screen
	 * @author 300021278 -Rakesh
	 */
	@FindBy(xpath = "//android.widget.EditText[@resource-id='com.myntra.android:id/et_email_address']")
	public AndroidElement signUpEmailTxt;
	
	/**
	 * Object identified for the password signUp field displayed at signup screen
	 * @author 300021278 -Rakesh
	 */
	@FindBy(xpath = "//android.widget.EditText[@resource-id='com.myntra.android:id/et_loginregister_regpassword']")
	public AndroidElement signUpPasswordTxt;
	
	/**
	 * Object identified for the FullName signUp field displayed at signup screen
	 * @author 300021278 -Rakesh
	 */
	@FindBy(xpath = "//android.widget.EditText[@resource-id='com.myntra.android:id/et_loginregister_name']")
	public AndroidElement signUpFullNameTxt;
	
	/**
	 * Object identified for the FullName signUp field displayed at signup screen
	 * @author 300021278 -Rakesh
	 */
	@FindBy(xpath = "//android.widget.EditText[@resource-id='com.myntra.android:id/et_loginregister_phonenumber']")
	public AndroidElement signUpMobileNoTxt;
	/**
	 * Object identified for the male signUp field displayed at signup screen
	 * @author 300021278 -Rakesh
	 */
	@FindBy(xpath = "//android.widget.TextView[@resource-id='com.myntra.android:id/btn_male']")
	public AndroidElement signUpMaleBtn;
	
	/**
	 * Object identified for the CreateAccount btn signUp field displayed at signup screen
	 * @author 300021278 -Rakesh
	 */
	@FindBy(xpath = "//android.widget.Button[@resource-id='com.myntra.android:id/btn_loginregister_register']")
	public AndroidElement createAccountBtn;
	
	/**
	* @author 300021278 -Rakesh
	* Object identified for clicking permissionSwitch button
	*/
	@FindBy(xpath = "//android.widget.Switch[@resource-id='android:id/switch_widget' or @text='OFF' or @index='0']")
	public AndroidElement permissionSwitch;
	
	/**
	* @author 300021278 -Rakesh
	* Object identified for clicking google smartlock never button
	*/
	@FindBy(xpath = "//android.widget.Button[@resource-id='com.google.android.gms:id/credential_save_reject' or @text='NEVER']")
	public AndroidElement smartLockGoogleNeverBtn;

	/************* GetterMtehods ******************/

	/**
	 * getter for firstSignUp button displayed at the launch screen;
	 * @author 300021278 -Rakesh
	 * @return
	 */
	public AndroidElement getEMailAddress() {
		objAndroidGenericMethods.CheckAndroidElementFound(eMailAddress, "eMailAddress");
		return eMailAddress;
	}
	
	/**
	 * @author 300019224-Aishurya: getter for givePermission
	 */
	public AndroidElement getGivePermission() {
		return givePermission;
	}
	
	/**
	 * @author 300019224-Aishurya: getter for givePermission
	 */
	public AndroidElement getAllowBtn() {
		return allowBtn;
	}
	
	/**
	 * getter for firstSignUp button displayed at the launch screen;
	 * @author 300021278 -Rakesh
	 * @return
	 */
	public AndroidElement getFirstSignUpBtn() {
		objAndroidGenericMethods.CheckAndroidElementFound(firstSignUpBtn, "firstSignUpBtn");
		return firstSignUpBtn;
	}
	
	public AndroidElement getPopUpbtn() {
		objAndroidGenericMethods.CheckAndroidElementFound(popUpbtn, "popUpbtn");
		return popUpbtn;
	}

	public AndroidElement getContactUs() {
		objAndroidGenericMethods.CheckAndroidElementFound(contactUs, "Contact option is present");
		return contactUs;
	}

	public AndroidElement getContactUsCancelButton() {
		objAndroidGenericMethods.CheckAndroidElementFound(contactUsCancelButton,
				"Contact screen cancel button is present");
		return contactUsCancelButton;
	}

	public AndroidElement getHamburgerbtn() {
		objAndroidGenericMethods.CheckAndroidElementFound(hamburgerbtn, "hamburgerbtn");
		return hamburgerbtn;
	}


	public AndroidElement getFirstloginbtn() {
		objAndroidGenericMethods.CheckAndroidElementFound(firstloginbtn, "firstloginbtn");
		return firstloginbtn;
	}

	public AndroidElement getEmailtxt() {
		objAndroidGenericMethods.CheckAndroidElementFound(emailtxt, "emailtxt");
		return emailtxt;
	}

	public AndroidElement getPasswordtxt() {
		objAndroidGenericMethods.CheckAndroidElementFound(passwordtxt, "passwordtxt");
		return passwordtxt;
	}

	public AndroidElement getLoginbtn() {
		objAndroidGenericMethods.CheckAndroidElementFound(loginbtn, "loginbtn");
		return loginbtn;
	}

	/**
	 * Login with facebook getters @ Vinay
	 */
	public AndroidElement getFacebookbtn() {
		objAndroidGenericMethods.CheckAndroidElementFound(facebookbtn, "facebookbtn");
		return facebookbtn;
	}

	public AndroidElement getEmailFbtxt() {
		objAndroidGenericMethods.CheckAndroidElementFound(emailFbtxt, "emailFbtxt");
		return emailFbtxt;
	}

	public AndroidElement getPasswordFbtxt() {
		objAndroidGenericMethods.CheckAndroidElementFound(passwordFbtxt, "passwordFbtxt");
		return passwordFbtxt;
	}

	public AndroidElement getLoginFbbtn() {
		objAndroidGenericMethods.CheckAndroidElementFound(LoginFbbtn, "LoginFbbtn");
		return LoginFbbtn;
	}

	public AndroidElement getConfirmFb() {
		objAndroidGenericMethods.CheckAndroidElementFound(confirmFb, "confirmFb");
		return confirmFb;
	}

	/************* Methods ******************/
	/**
	 * Method to click on sign up button displayed at the launch screen;
	 * @author 300021278 -Rakesh
	 */
	public void clickFirstSignUpBtn() {
		objAndroidGenericMethods.clickOnAndroidElement(getFirstSignUpBtn(), "FirstSignUpBtn");
	}
	

	/**
	 * This Method helpful to click on facebook button
	 * 
	 * @author 300021282 VINAY
	 */
	public void clickFacebookbtn() {
		objAndroidGenericMethods.clickOnAndroidElement(getFacebookbtn(), "facebookbtn");
	}

	/**
	 * this use to click on Login button on Login with facebook page
	 * 
	 * @author 300021282 VINAY
	 */
	public void clickLoginFBbtn() {
		objAndroidGenericMethods.clickOnAndroidElement(getLoginFbbtn(), "LoginFbbtn");
	}

	/**
	 * This Method use to click on "Continue button" on Login with Facebook
	 * screen
	 * 
	 * @author 300021282 VINAY
	 */
	public void clickOnFacebookContinuebtn() {
		objAndroidGenericMethods.clickOnAndroidElement(getConfirmFb(), "ConfirmFb");
	}

	/**
	 * @author 300021280-Sneha Medhod for Login via Facebook
	 * 
	 * @param EmailValue
	 * @param PasswordValue
	 */
	public void FacebookLogin(String EmailValue, String PasswordValue) throws InterruptedException {
		objAndroidGenericMethods.enterTexAndroidElement(getEmailFbtxt(), EmailValue, "entered the email/ username ");
		objAndroidGenericMethods.enterTexAndroidElement(getPasswordFbtxt(), PasswordValue, "Entered the password");
		objAndroidGenericMethods.clickOnAndroidElement(getLoginFbbtn(), "LoginFBbtn");
	}
	/**
	 * @author 300019224-Aishurya: Accept all permissions
	 */
	public void giveAllPermissions() { 
		objAndroidGenericMethods.clickOnAndroidElement(getGivePermission(), "Give permission btn");
		objAndroidGenericMethods.clickOnAndroidElement(getAllowBtn(), "First Allow btn");
		objAndroidGenericMethods.clickOnAndroidElement(getAllowBtn(), "Second Allow btn");
		objAndroidGenericMethods.clickOnAndroidElement(getAllowBtn(), "Third Allow btn");
	}
//	public void clickpopUp() throws InterruptedException {
//		objAndroidGenericMethods.clickOnAndroidElement(getPopUpbtn(), "clicked on popUpbtn");
//	//	Thread.sleep(4000);
//	}
	
	/**
	 * Modified by :: 300021280 Sneha
	 * Added try catch for handling Permissions pop-up
	 * 
	 */
	public void clickpopUp() {
		try {
			objAndroidGenericMethods.clickOnAndroidElement(getPopUpbtn(), "click on Later");
			System.out.println("permissions popup is available");
			Reporter.log("permissions popup is available");
		} catch (Exception e) {
		Reporter.log("permissions popup is not available");
		System.out.println("permissions popup is not available");
		}
	}
	public void clickFirstLogin() {
		objAndroidGenericMethods.clickOnAndroidElement(getFirstloginbtn(), "clicked on firstloginbtn");
	}

	public void loginInApp(String EmailValue, String PasswordValue) throws InterruptedException {
		emailtxt.clear();
		objAndroidGenericMethods.enterTexAndroidElement(getEmailtxt(), EmailValue, "entered the email/ username ");
		passwordtxt.clear();
		objAndroidGenericMethods.enterTexAndroidElement(getPasswordtxt(), PasswordValue, "Entered the password");
	//	Thread.sleep(3000);
	}

	public void clickLogin() {
		objAndroidGenericMethods.clickOnAndroidElement(getLoginbtn(), "clicked on loginbtn");
		checkPermissionSwitch();
		checkSmartLockButton();
	}
	
	/**
	 * Method to click on email adddress displayed in SignUp page
	 */
	public void clickEMailAddress() {
		objAndroidGenericMethods.clickOnAndroidElement(getEMailAddress(), "EMailAddress");

	}

	/**
	 * added thread.sleep
	 * 
	 * @author 300021282 VINAY
	 */
	public void clickhamburger()  {
		objAndroidGenericMethods.clickOnAndroidElement(getHamburgerbtn(), "clicked on hamburgerbtn");
	}

	

	public void clickOnContactUs() {
		objAndroidGenericMethods.clickOnAndroidElement(getContactUs(), "ContactUs clicked");
	}

	public void clickOnContactCancelButton() {
		objAndroidGenericMethods.CheckAndroidElementFound(getContactUsCancelButton(), "Cancel button clicked");
	}

	/****************************** Assertions ******************************************/

	@FindBy(xpath = "//android.widget.TextView[@index='3']")
	public AndroidElement userName;

	/**
	 * Modified By Aishurya: Removed userName parameter
	 */
	public void verifyUserId() {
		// objAndroidGenericMethods.VerifyTwoString(userName, ExpectedData);
		objAndroidGenericMethods.VerifyStringShouldNotEmpty(userName, "userName");
	}

	/**
	 * Cretaed By subhasis Agree button
	 */

	@FindBy(xpath = "//android.widget.Button[@text='I AGREE']")
	public AndroidElement agreeButton;
	
	/**
	 * 
	 * Cretaed By Subhasis Agree button getter
	 */

	public AndroidElement getAgreeButton() {
		objAndroidGenericMethods.CheckAndroidElementFound(agreeButton, "Agree Button");
		return agreeButton;
	}

	/**
	 * Created By Subhasis Click on Agree Button
	 */

	public void clickOnAgree() {
		objAndroidGenericMethods.clickOnAndroidElement(getAgreeButton(), "Agree Button");
	}

	/********************** SignInByGoogle ********************************/
	/**
	 * Amba
	 * 
	 * @param LoginViaGamil
	 */

	@FindBy(xpath = "//android.widget.LinearLayout[@resource-id='com.myntra.android:id/googleplus_sign_in_button']")

	public AndroidElement GmailLogin;

	@FindBy(xpath = "//android.widget.Button[@text='Use another account' or @text='Add account']")
	public AndroidElement useNewAccount;

	@FindBy(xpath = "//android.widget.EditText[@text='Email or phone' or @resource-id='identifierId']")

	public AndroidElement EnterGmailEmail;

	@FindBy(xpath = "//android.widget.Button[@text='NEXT' or resource-id='identifierNext' or @index='7']")

	public AndroidElement EmailNextButton1;
	@FindBy(xpath = "//android.widget.Button[@text='NEXT' or resource-id='passwordNext' or @index='5']")

	public AndroidElement PasswordNextButton1;

	@FindBy(xpath = "//*[@text='NEXT' or resource-id='passwordNext']")

	public AndroidElement PasswordNextButton2;

	@FindBy(xpath = "//*[@text='NEXT' or resource-id='identifierNext']")

	public AndroidElement EmailNextButton2;

	@FindBy(xpath = "//android.widget.EditText[@text='Enter your password' or @text='Password']")

	public AndroidElement EnterGmailPassword;
	/**
	 * Cretaed By Amba
	 * 
	 * @param Accept button
	 */

	@FindBy(xpath = "//*[@text='ACCEPT' or resource-id='next']")
	public AndroidElement AcceptButton;

	/**
	 * 
	 * Cretaed By Amba
	 * 
	 * @param Accept button getter
	 */

	public AndroidElement getAcceptButton() {
		objAndroidGenericMethods.CheckAndroidElementFound(AcceptButton, "Accept Button");
		return agreeButton;
	}

	/**
	 * 
	 * Cretaed By Amba
	 * 
	 * @param Email button getter for android 6
	 */
	public AndroidElement getEmailNextButton1() {

		objAndroidGenericMethods.CheckAndroidElementFound(EmailNextButton1, " NextButton");

		return EmailNextButton1;

	}

	/**
	 * 
	 * Cretaed By Amba
	 * 
	 * @param Email button getter for android 7
	 */
	public AndroidElement getEmailNextButton2() {

		objAndroidGenericMethods.CheckAndroidElementFound(EmailNextButton2, " NextButton");

		return EmailNextButton2;

	}

	/**
	 * 
	 * Cretaed By Amba
	 * 
	 * @param Email button getter for android 6
	 */
	public AndroidElement getPasswordNextButton1() {

		objAndroidGenericMethods.CheckAndroidElementFound(PasswordNextButton1, " PasswordNextButton1");

		return PasswordNextButton1;

	}

	/**
	 * 
	 * Cretaed By Amba
	 * 
	 * @param Email button getter for android 7
	 */
	public AndroidElement getPasswordNextButton2() {

		objAndroidGenericMethods.CheckAndroidElementFound(PasswordNextButton2, " PasswordNextButton2");

		return PasswordNextButton2;

	}

	/**
	 * 
	 * Cretaed By Amba
	 * 
	 * @param Enter Gmail Email getter method
	 */
	public AndroidElement getEnterGmailEmail() {

		objAndroidGenericMethods.CheckAndroidElementFound(EnterGmailEmail, "EnterGmailEmail");

		return EnterGmailEmail;

	}

	/**
	 * 
	 * Cretaed By Amba
	 * 
	 * @param Enter Gmail Password getter method
	 */
	public AndroidElement getEnterGmailPassword() {

		objAndroidGenericMethods.CheckAndroidElementFound(EnterGmailPassword, "EnterGmailPassword");

		return EnterGmailPassword;

	}

	/**
	 * 
	 * Cretaed By Amba
	 * 
	 * @param Enter Gmail Login Button getter method
	 */
	public AndroidElement getGmailLogin() {

		objAndroidGenericMethods.CheckAndroidElementFound(GmailLogin, "GmailLogin");

		return GmailLogin;

	}

	/**
	 * 
	 * Cretaed By Amba
	 * 
	 * @param Add New Account getter method
	 */
	public AndroidElement getUseNewAccount() {
		objAndroidGenericMethods.CheckAndroidElementFound(useNewAccount, "Use another account");
		return useNewAccount;
	}

	// ***********Methods************//

	/**
	 * 
	 * Cretaed By Amba
	 * 
	 * @param Method to Click on Accept
	 */
	public void clickOnAccept() {
		objAndroidGenericMethods.clickOnAndroidElement(getAcceptButton(), "Accept Button");
	}

	/**
	 * 
	 * Amba
	 * 
	 * @param This Methods clicks on GooglesignUp button on launch screen.
	 */
	public void clickOnGoogleSignUpOption() {

		objAndroidGenericMethods.clickOnAndroidElement(getGmailLogin(), "clicked on GmailOption");

		System.out.println("Succesfully clicked on Google button");

	}

	/**
	 * Amba
	 * 
	 * @param This Methods clicks on Google use another account button on launch
	 *            screen.
	 */
	public void clickOnUseAnotherAccount() {
		objAndroidGenericMethods.clickOnAndroidElement(getUseNewAccount(), "Use another account");
		System.out.println("Use another account option is clicked successfully");
	}

	/**
	 * Amba
	 * 
	 * @paramThis Method clicks on EmailNext button for android version 6
	 */
	public void clickOnEmailNextButton1() {

		objAndroidGenericMethods.clickOnAndroidElement(getEmailNextButton1(), "clicked on EmailNextButton");

		System.out.println("Succesfully clicked on EmailNextButton");

	}

	/**
	 * Amba
	 * 
	 * @paramThis Method clicks on EmailNext button for android version 6
	 */
	public void clickOnPasswordNextButton1() {

		objAndroidGenericMethods.clickOnAndroidElement(getPasswordNextButton1(), "clicked on PasswordNextButton");

		System.out.println("Succesfully clicked on PasswordNextButton");

	}

	/**
	 * Amba
	 * 
	 * @paramThis Method clicks on EmailNext button for android version 7
	 */
	public void clickOnPasswordNextButton2() {

		objAndroidGenericMethods.clickOnAndroidElement(getPasswordNextButton2(), "clicked on PasswordNextButton");

		System.out.println("Succesfully clicked on PasswordNextButton");

	}

	/**
	 * Amba
	 * 
	 * @param Method Only For Password
	 */
	public void EnterGmailPassword(String pw) {
		// objAndroidGenericMethods.clickOnAndroidElement(getEnterGmailPassword(),
		// "Password filed");

		EnterGmailPassword.sendKeys(pw);

		System.out.println("Succesfully Entered Password");

	}

	/**
	 * Amba
	 * 
	 * @param Method only for Email
	 */
	public void EnterGmailEmail(String em) {

		objAndroidGenericMethods.clickOnAndroidElement(getEnterGmailEmail(), "Email");
		EnterGmailEmail.sendKeys(em);

		System.out.println("Succesfully GmailId Entered");

	}

	/**
	 * Amba
	 * 
	 * @param Method Only For Email NextButton on different Android Version
	 */
	public void AppEmailNextButton() throws InterruptedException {
		Boolean deviceNextButton = false;
		try {
			deviceNextButton = getEmailNextButton1().isDisplayed();
		} catch (Exception e1) {
		}
		if (deviceNextButton) {
			getEmailNextButton1().click();
			// Thread.sleep(2000);

		} else {
		//	Thread.sleep(3000);
			getEmailNextButton2().click();
			// Thread.sleep(2000);
		}

	}

	/**
	 * Amba
	 * 
	 * @param Method Only For Password NextButton on different Android Version(6,7)
	 */
	public void AppPasswordNextButton() throws InterruptedException {
		Boolean deviceNextButton = false;
		try {
			deviceNextButton = getPasswordNextButton1().isDisplayed();
		} catch (Exception e1) {
		}
		if (deviceNextButton) {
			getPasswordNextButton1().click();
			// Thread.sleep(2000);

		} else {
		//	Thread.sleep(3000);
			getPasswordNextButton2().click();
			// Thread.sleep(2000);
		}

	}

	/**
	 * Amba
	 * 
	 * @param Method Only For Accept button on different Android Version (6,7)
	 */
	public void GoogleAcceptButton() throws InterruptedException {
		Boolean GoogleAcceptButton = false;
		try {
			GoogleAcceptButton = getAgreeButton().isDisplayed();
		} catch (Exception e1) {
		}
		if (GoogleAcceptButton) {
			getAgreeButton().click();
			// Thread.sleep(2000);

		} else {
			Thread.sleep(3000);
			getAcceptButton().click();
			// Thread.sleep(2000);
		}

	}

	/**
	 * Amba
	 * 
	 * @param Method for Email and Password together
	 */
	public void LoginWithGoogle(String EmailValue, String PasswordValue) throws InterruptedException {
		// emailtxt.clear();
		objAndroidGenericMethods.enterTexAndroidElement(getEnterGmailEmail(), EmailValue,
				"entered the email/ username ");
		AppEmailNextButton();
		// passwordtxt.clear();
		objAndroidGenericMethods.enterTexAndroidElement(getEnterGmailPassword(), PasswordValue, "Entered the password");
		AppPasswordNextButton();
	//	Thread.sleep(3000);
	}

	/**
	 * Method to fill the sign up for email used for mnv feature
	 * 
	 * @author 300021278 -Rakesh
	 * Modified By: Aishurya: Parameterized mobile number
	 */
	public void signUpforEmail(String MailFirstValue, String MobileNumber) throws InvalidFileFormatException, IOException {
		clickFirstSignUpBtn();
		clickEMailAddress();
		String newemaild = MailFirstValue + objAndroidGenericMethods.datetime("ddMMyyyyHHmmss") + "@gmail.com";
		objAndroidGenericMethods.enterTexAndroidElement(signUpEmailTxt, newemaild, "Entered Email id");
		objAndroidGenericMethods.enterTexAndroidElement(signUpPasswordTxt, "test123", "EnteredPassword");
		objAndroidGenericMethods.enterTexAndroidElement(signUpFullNameTxt, "TestUser", "Entered UserName");
		objAndroidGenericMethods.enterTexAndroidElement(signUpMobileNoTxt,MobileNumber, "EnteredMobileNumber");
		objAndroidGenericMethods.clickOnAndroidElement(signUpMaleBtn, "signUpMaleBtn");
		objAndroidGenericMethods.clickOnAndroidElement(createAccountBtn, "createAccountBtn");
	}
	
	public void checkPermissionSwitch() {
		try {
			permissionSwitch.click();
			System.out.println("Overdraw Permission is Displayed");
			aDriver.navigate().back();
		} catch (Exception e) {
			System.out.println("Overdraw Permission didnot apper");
		}
	}

	public void checkSmartLockButton() {
		try {
			smartLockGoogleNeverBtn.click();
			System.out.println("smartLockGoogle NeverBtn is Displayed");
			aDriver.navigate().back();
		} catch (Exception e) {
			System.out.println("smartLockGoogle NeverBtn didnot apper");
		}
	}
}
