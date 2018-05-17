package com.automation.mobile.Android.apps.ObjectRepository.Hamburger;

import java.io.IOException;
import java.util.List;

import org.ini4j.InvalidFileFormatException;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;

import com.automation.core.mobile.Android.AndroidGenericMethods;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.PressesKeyCode;
import io.appium.java_client.android.Activity;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.AndroidKeyCode;
import io.appium.java_client.android.StartsActivity;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class HamburgerPageObject {
	public AppiumDriver<MobileElement> aDriver;
	AndroidGenericMethods objAndroidGenericMethods;

	/**
	 * Modified By Aishurya: aDriver is throwing null pointer
	 */
	public HamburgerPageObject(AppiumDriver<MobileElement> aDriver) {
		PageFactory.initElements(new AppiumFieldDecorator(aDriver), this);
		objAndroidGenericMethods = new AndroidGenericMethods(aDriver);
		this.aDriver = aDriver;
	}

	/**
	 * @author 300019224-Aishurya: To handel if already number verified
	 */
	public static boolean verified=false;

	@FindBy(xpath = "//android.widget.TextView[@text='CONTACT US']")
	public AndroidElement contactUsbtn;

	@FindBy(xpath = "//*[@content-desc='leftElement']")
	public AndroidElement humburgerbtn;

	/**
	 * Modified by: Aishurya: made generic xpath
	 */
	@FindBy(xpath = "//android.widget.TextView[@text = 'Account']")
	public AndroidElement myAccountBtn;
	/**
	 * @author 300021278 -Rakesh
	 * @ModifiedBy:-Rakesh Modified reason: path was changed in the build
	 */
	@FindBy(xpath = "//android.widget.TextView[@text = 'Address']")
	public AndroidElement myAccountAdddresses;
	/**
	 * @author 300021278 -Rakesh
	 * @ModifiedBy:-Rakesh Modified reason: changed the xpath as it contianed ? in
	 *                     text.
	 */
	@FindBy(xpath = "//*[@content-desc='leftElement' or @text='leftElement']")
	public AndroidElement myAccountClosebtn;

	/**
	 * @author 300019224-Aishurya Added objects for MVN
	 */
	@FindBy(xpath = "//android.widget.TextView[@index = '3']")
	public AndroidElement MyntraUserLink;

	@FindBy(xpath = "//android.widget.TextView[@text = 'Verify Your Mobile Number']")
	public AndroidElement ProfileMnvTitleMsg;

	@FindBy(xpath = "//android.widget.TextView[@text = 'Get timely order updates and special offers']")
	public AndroidElement ProfileMnvMsg;

	@FindBy(xpath = "//android.widget.EditText[@index = '4']")
	public AndroidElement ProfileMobileNumberTxtBox;

	@FindBy(xpath = "//android.widget.TextView[@text = 'VERIFY']")
	public AndroidElement ProfileVerifyBtn;

	@FindBy(xpath = "//android.widget.TextView[@text = 'Orders']")
	public AndroidElement OrdersLink;

	@FindBy(xpath = "//android.widget.TextView[@text = 'Profile Details']")
	public AndroidElement ProfileDetailsLink;

	@FindBy(xpath = "//android.widget.TextView[@text = 'MOBILE NUMBER']")
	public AndroidElement MOBILENUMBER;

	@FindBy(xpath = "//android.view.ViewGroup[@index = '5']//android.widget.EditText")
	public AndroidElement PdMobileNumberTxtBox;

	@FindBy(xpath = "//android.widget.TextView[@text = 'Please add valid mobile number']")
	public AndroidElement InvalidMobileMsg;

	@FindBy(xpath = "//android.widget.TextView[@text = 'VERIFY']")
	public AndroidElement PdVerifyBtn;

	@FindBy(xpath = "//android.widget.TextView[@text = 'SAVE DETAILS']")
	public AndroidElement PdSaveDetailsBtn;

	@FindBy(xpath = "//android.widget.TextView[@text = 'Verification']")
	public AndroidElement VerificationPageHeader;

	@FindBy(xpath = "//android.widget.TextView[@text = 'Enter OTP']")
	public AndroidElement PdEnterOtpTitleMsg;

	@FindBy(xpath = "//android.widget.TextView[@text = 'Waiting For OTP']")
	public AndroidElement PdWaitingForOtpTitleMsg;

	@FindBy(xpath = "//android.widget.TextView[@index='3']")
	public AndroidElement EnterSentOtpMsg;

	/**
	 * Modified By:Aishurya: variable name changed
	 */
	@FindBy(xpath = "//android.view.ViewGroup[@index = '4']/android.view.ViewGroup[@index = '0']")
	public AndroidElement OtpFieldOne;

	/**
	 * Added By Aishurya: For 2nd OTP
	 */
	@FindBy(xpath = "//android.view.ViewGroup[@index = '4']/android.view.ViewGroup[@index = '1']")
	public AndroidElement OtpFieldTwo;

	/**
	 * Added By Aishurya: For 3rd Otp
	 */
	@FindBy(xpath = "//android.view.ViewGroup[@index = '4']/android.view.ViewGroup[@index = '2']")
	public AndroidElement OtpFieldThree;
	/**
	 * Added By Aishurya: For 4th OTP
	 */
	@FindBy(xpath = "//android.view.ViewGroup[@index = '4']/android.view.ViewGroup[@index = '3']")
	public AndroidElement OtpFieldFour;

	@FindBy(xpath = "//android.widget.TextView[@text = 'RESEND OTP']")
	public AndroidElement ResendOtpBtn;

	@FindBy(xpath = "//android.widget.TextView[@text = 'Trying to auto-fill OTP']")
	public AndroidElement ResendOtpMsg;

	@FindBy(xpath = "//android.widget.TextView[@text = 'Incorrect OTP']")
	public AndroidElement IncorrectOtpMsg;

	@FindBy(xpath = "//android.widget.TextView[@index = '6']")
	public AndroidElement OtpCounter;

	@FindBy(xpath = "//android.widget.TextView[@resource-id = 'com.myntra.android:id/tv_title']")
	public AndroidElement OrdersPageHeader;

	/**
	 * Modified by : Aishurya: modified to webview xpath
	 */
	@FindBy(className = "mobileVerification-heading")
	// @FindBy(xpath = "//android.view.View[@content-desc = 'Verify Your Mobile
	// Number']")
	public AndroidElement OrdersMnvTitleMsg;
	/**
	 * Modified by : Aishurya: modified to webview xpath
	 */
	@FindBy(className = "mobileVerification-subHeading")
	// @FindBy(xpath = "//android.view.View[@content-desc = 'Get timely order
	// updates and special offers']")
	public AndroidElement OrdersMnvInfoMsg;

	/**
	 * Modified by : Aishurya: modified to webview xpath
	 */
	@FindBy(xpath = "//*[@class='mobileVerification-input']/div/input")
	// @FindBy(xpath = "//android.widget.EditText[@resource-id = 'input_1']")
	public AndroidElement OrdersMobileNumberTxtBox;
	/**
	 * Modified by : Aishurya: modified to webview xpath
	 */
	@FindBy(xpath = "(//div[contains(text(),'Verify')])[2]")
	// @FindBy(xpath = "mobileVerification-verifyButtonv1")
	// @FindBy(xpath = "//android.view.View[@content-desc = 'VERIFY']")
	public AndroidElement OrdersVerifyBtn;

	/******************* Getters *********************/
	/**
	 * Modified by : Aishurya: modified to webview
	 */
	public AndroidElement getOrdersMnvInfoMsg() {
		objAndroidGenericMethods.CheckAndroidElementFoundForWebView(OrdersMnvInfoMsg, "MVN orders page info message ");
		objAndroidGenericMethods.VerifyTwoString(OrdersMnvInfoMsg, "Get timely order updates and special offers");
		return OrdersMnvInfoMsg;
	}

	public AndroidElement getMyntraUserLink() {
		objAndroidGenericMethods.CheckAndroidElementFound(MyntraUserLink, "Myntra User Link");
		return MyntraUserLink;
	}

	public AndroidElement getProfileMnvTitleMsg() {
		objAndroidGenericMethods.CheckAndroidElementFound(ProfileMnvTitleMsg, "MNV orders page Title message");
		objAndroidGenericMethods.VerifyTwoString(ProfileMnvTitleMsg,
				"<font color='green'>Verify Your Mobile Number</font>");
		return ProfileMnvTitleMsg;
	}

	public AndroidElement getProfileMnvMsg() {
		objAndroidGenericMethods.CheckAndroidElementFound(ProfileMnvMsg, "MNV orders page Info message");
		objAndroidGenericMethods.VerifyTwoString(ProfileMnvMsg,
				"<font color='green'>Get timely order updates and special offers</font>");
		return ProfileMnvMsg;
	}

	public AndroidElement getProfileMobileNumberTxtBox() {
		objAndroidGenericMethods.CheckAndroidElementFound(ProfileMobileNumberTxtBox,
				"Profile Mobile Number Text field ");
		return ProfileMobileNumberTxtBox;
	}

	public AndroidElement getProfileVerifyBtn() {
		objAndroidGenericMethods.CheckAndroidElementFound(ProfileVerifyBtn, "Profile Verify Button ");
		return ProfileVerifyBtn;
	}

	public AndroidElement getOrdersLink() {
		objAndroidGenericMethods.CheckAndroidElementFound(OrdersLink, "Orders Link ");
		return OrdersLink;
	}

	public AndroidElement getProfileDetailsLink() {
		objAndroidGenericMethods.CheckAndroidElementFound(ProfileDetailsLink, "Profile details Link ");
		return ProfileDetailsLink;
	}

	public AndroidElement getMOBILENUMBER() {
		objAndroidGenericMethods.CheckAndroidElementFound(MOBILENUMBER, "MOBILENUMBER msg in text field");
		objAndroidGenericMethods.VerifyTwoString(MOBILENUMBER, "MOBILE NUMBER");
		return MOBILENUMBER;
	}

	public AndroidElement getPdMobileNumberTxtBox() {
		objAndroidGenericMethods.CheckAndroidElementFound(PdMobileNumberTxtBox,
				"Profile details mobile number text field");
		return PdMobileNumberTxtBox;
	}

	public AndroidElement getInvalidMobileMsg() {
		objAndroidGenericMethods.CheckAndroidElementFound(InvalidMobileMsg, "Invalid mobile number validation message");
		objAndroidGenericMethods.VerifyTwoString(InvalidMobileMsg, "Please add valid mobile number");
		return InvalidMobileMsg;
	}

	public AndroidElement getPdVerifyBtn() {
		objAndroidGenericMethods.CheckAndroidElementFound(PdVerifyBtn, "Profile Details Verify Button");
		return PdVerifyBtn;
	}

	public AndroidElement getPdSaveDetailsBtn() {
		objAndroidGenericMethods.CheckAndroidElementFound(PdSaveDetailsBtn, "Profile Details \"Save Details\" Button");
		return PdSaveDetailsBtn;
	}

	public AndroidElement getVerificationPageHeader() {
		objAndroidGenericMethods.CheckAndroidElementFound(VerificationPageHeader, "Verification Page Header");
		return VerificationPageHeader;
	}

	public AndroidElement getEnterOtpTitleMsg() {
		objAndroidGenericMethods.CheckAndroidElementFound(PdEnterOtpTitleMsg, "Profile details Enter OTP message");
		objAndroidGenericMethods.VerifyTwoString(PdEnterOtpTitleMsg, "Enter OTP");
		return PdEnterOtpTitleMsg;
	}

	public AndroidElement getWaitingForOtpTitleMsg() {
		objAndroidGenericMethods.CheckAndroidElementFound(PdWaitingForOtpTitleMsg,
				"Profile details Waiting OTP message");
		objAndroidGenericMethods.VerifyTwoString(PdWaitingForOtpTitleMsg, "Waiting For OTP");
		return PdWaitingForOtpTitleMsg;
	}

	/**
	 * Modified by: Aishurya:separating testname and udidName
	 */
	public AndroidElement getEnterSentOtpMsg(String mobileNumber) throws InvalidFileFormatException, IOException {
		String expected = "OTP sent to mobile number " + mobileNumber;
		objAndroidGenericMethods.CheckAndroidElementFound(EnterSentOtpMsg, "Enter sent OTP to ur no msg");
		objAndroidGenericMethods.VerifyTwoString(EnterSentOtpMsg, expected);
		return EnterSentOtpMsg;
	}

	public AndroidElement getOtpFieldOne() {
		objAndroidGenericMethods.CheckAndroidElementFound(OtpFieldOne, "Enter OTP field");
		return OtpFieldOne;
	}

	public AndroidElement getResendOtpBtn() {
		objAndroidGenericMethods.CheckAndroidElementFound(ResendOtpBtn, "Resend Otp Button");
		return ResendOtpBtn;
	}

	public AndroidElement getResendOtpMsg() {
		objAndroidGenericMethods.CheckAndroidElementFound(ResendOtpMsg, "Resend OTP message");
		objAndroidGenericMethods.VerifyStringShouldNotEmpty(ResendOtpMsg, "Trying to auto-fill OTP");
		return ResendOtpMsg;
	}

	public AndroidElement getIncorrectOtpMsg() {
		objAndroidGenericMethods.CheckAndroidElementFound(IncorrectOtpMsg, "Incorrect OTP");
		objAndroidGenericMethods.VerifyTwoString(IncorrectOtpMsg, "Incorrect OTP");
		return IncorrectOtpMsg;
	}

	public AndroidElement getOtpCounter() {
		objAndroidGenericMethods.CheckAndroidElementFound(OtpCounter, "Otp Counter");
		objAndroidGenericMethods.VerifyStringShouldNotEmpty(OtpCounter, "Otp Counter");
		return OtpCounter;
	}

	public AndroidElement getOrdersPageHeader() {
		objAndroidGenericMethods.CheckAndroidElementFound(OrdersPageHeader, "Orders page Header");
		objAndroidGenericMethods.VerifyTwoString(OrdersPageHeader, "Orders");
		return OrdersPageHeader;
	}

	/**
	 * Modified by : Aishurya: modified to webview
	 */
	public AndroidElement getOrdersMnvTitleMsg() {
		objAndroidGenericMethods.CheckAndroidElementFoundForWebView(OrdersMnvTitleMsg, "Orders page MNV msg");
		objAndroidGenericMethods.VerifyTwoString(OrdersMnvTitleMsg, "Verify Your Mobile Number");
		return OrdersMnvTitleMsg;
	}

	/**
	 * Modified by : Aishurya: modified to webview
	 */
	public AndroidElement getOrdersMobileNumberTxtBox() {
		objAndroidGenericMethods.CheckAndroidElementFoundForWebView(OrdersMobileNumberTxtBox,
				"Orders page Mobile number field");
		return OrdersMobileNumberTxtBox;
	}

	/**
	 * Modified by : Aishurya: modified to webview
	 */
	public AndroidElement getOrdersVerifyBtn() {
		objAndroidGenericMethods.CheckAndroidElementFoundForWebView(OrdersVerifyBtn, "Orders page Verify btn");
		return OrdersVerifyBtn;
	}

	public AndroidElement gethumburgerbtn() {
		objAndroidGenericMethods.CheckAndroidElementFound(humburgerbtn, "humburgerbtn");
		return humburgerbtn;
	}

	public AndroidElement getmyAccountAdddresses() {
		objAndroidGenericMethods.CheckAndroidElementFound(myAccountAdddresses, "myAccountAdddresses");
		return myAccountAdddresses;
	}

	public AndroidElement getContactUsbtn() {
		objAndroidGenericMethods.CheckAndroidElementFound(contactUsbtn, "contactUsbtn");
		return contactUsbtn;
	}

	public AndroidElement getmyAccountBtn() {
		objAndroidGenericMethods.CheckAndroidElementFound(myAccountBtn, "myAccountBtn");
		return myAccountBtn;
	}

	/******************* Methods **********************/
	/**
	 * Added By Aishurya: Methods for MNV feature
	 */
	public void clickOnMyntraUserLink() {
		objAndroidGenericMethods.clickOnAndroidElement(getMyntraUserLink(), "Myntra User Link");
	}

	/**
	 * @author 300019224-Aishurya: Reset to enable MNV feature Modified By:
	 *         Aishurya: Scroll method changed
	 */
	public void resetMnv() throws InvalidFileFormatException, IOException {
		clickOnProfileDetailsLink();
		enterPdPageMobileNumber("9876543210");
		clickSaveDetailsBtn();
		System.out.println("Clicked on save one time");
		// clickSaveDetailsBtn();
		// System.out.println("Clicked on save second time");

		objAndroidGenericMethods.topBottomSwipe(15000);
		objAndroidGenericMethods.topBottomSwipe(15000);
		// objAndroidGenericMethods.topBottomSwipe(15000);
	}

	/**
	 * @author 300019224-Aishurya: To handel if already number verified Modified By:
	 *         Aishurya: isDisplayed was throwing error
	 */
	public void isVerified() {
		try {
			ProfileMobileNumberTxtBox.click();
			verified = false;
			System.out.println("Verification status No");
		} catch (Exception e) {
			verified = true;
			System.out.println("Verification status Yes");
		}
	}

	/**
	 * @author 300019224-Aishurya: Check point if already number verified
	 */
	public void verifyProfilePageTextMsg() {
		if (!verified) {
			getProfileMnvTitleMsg();
			getProfileMnvMsg();
		} else {
			Reporter.log("Number is already verified,Profile number verification is not available");
		}
	}

	/**
	 * @author 300019224-Aishurya: Check point if already number verified
	 */
	public void enterProfilePageMobileNumber(String MobileNumber) {
		if (!verified) {
			getProfileMobileNumberTxtBox().clear();
			objAndroidGenericMethods.enterTexAndroidElement(getProfileMobileNumberTxtBox(), MobileNumber,
					"Profile page Mobile Number ");
		} else {
			Reporter.log("Number is already verified,Profile number verification is not available");
		}
	}

	/**
	 * @author 300019224-Aishurya: Check point if already number verified
	 */
	public void clickProfilePageVerifyBtn() {
		if (!verified) {
			objAndroidGenericMethods.clickOnAndroidElement(getProfileVerifyBtn(), "Profile Verify Btn");
			getResendOtpMsg();
			getOtpCounter();
		}
	}

	public void clickOnOrdersLink() {
		objAndroidGenericMethods.clickOnAndroidElement(getOrdersLink(), "Orders Page Link");
	}

	public void clickOnProfileDetailsLink() {
		objAndroidGenericMethods.scrolltoElementAndClick(getProfileDetailsLink(), 2000);
	}

	public void verifyTextMsgOrdersPage() {
		getOrdersPageHeader();
		getOrdersMnvTitleMsg();
		getOrdersMnvInfoMsg();
	}

	public void enterOrdersPageMobileNumber(String MobileNumber) {
		getOrdersMobileNumberTxtBox().clear();
		objAndroidGenericMethods.enterTexAndroidElement(getOrdersMobileNumberTxtBox(), MobileNumber,
				"Order page Mobile Number ");
	}

	/**
	 * @author 300019224-Aishurya: Added message and countdown verification
	 * Modified by Aishurya: webView Click
	 */
	public void clickOrdersPageVerifyBtn() {
		objAndroidGenericMethods.clickOnAndroidElementforwebVIew(getOrdersVerifyBtn(), "Orders Page Verify Btn");
		getResendOtpMsg();
		getOtpCounter();
	}

	/**
	 * Auther: Aishurya: to read msg from inbox
	 * Modified By Aishurya: Made xpath compatible for all device msg application
	 */
	public String readOtpFromInbox(String msgAppPackage,String msgAppActivity) throws InterruptedException {
		((StartsActivity) aDriver).startActivity(new Activity(msgAppPackage, msgAppActivity));
//		aDriver.pressKeyCode(AndroidKeyCode.BACK);
		List<MobileElement> msgThread=aDriver.findElements(By.xpath("//android.widget.TextView[contains(@text,'-MYNTRA') or contains(@text,'-myntra')]"));
		msgThread.get(0).click();
		List<MobileElement> msgList = aDriver.findElements(By.xpath("//android.widget.ListView//android.widget.TextView[contains(@text,'[#]')]"));
		String msg = msgList.get(msgList.size() - 1).getText();
		String otp = msg.split("Use")[1].split(" ")[1].trim();
		System.out.println("After trim " + otp);
		((PressesKeyCode) aDriver).pressKeyCode(AndroidKeyCode.BACK);
		((PressesKeyCode) aDriver).pressKeyCode(AndroidKeyCode.BACK);
		return otp;
	}

	public void enterPdPageMobileNumber(String MobileNumber) {
		objAndroidGenericMethods.clickOnAndroidElement(getMOBILENUMBER(), "MOBILE NUMBER Field");
		getPdMobileNumberTxtBox().clear();
		objAndroidGenericMethods.enterTexAndroidElement(getPdMobileNumberTxtBox(), MobileNumber,
				"Profile Details page Mobile Number ");
		objAndroidGenericMethods.clickOnAndroidElement(getMOBILENUMBER(), "MOBILE NUMBER Field");
	}

	/**
	 * Modified By:Aishurya: Clicking on verify btn twice
	 */
	public void clickPdPageVerifyBtn() {
		objAndroidGenericMethods.clickOnAndroidElement(getPdVerifyBtn(), "Profile Details Page Verify Btn");
		try {
			objAndroidGenericMethods.clickOnAndroidElement(getPdVerifyBtn(), "Profile Details Page Verify Btn");
		} catch (Exception e) {
		}
		getResendOtpMsg();
		getOtpCounter();
	}

	/**
	 * @author 300019224-Aishurya: Scroll is not working Modified By: Aishurya:
	 *         Fixed the scroll, Removed commented portion
	 */
	public void clickSaveDetailsBtn() {
		objAndroidGenericMethods.scrolltoElementAndClick(getPdSaveDetailsBtn(), 2000);
	}

	public void verifyTextMsgVerificationPage(String mobileNumber) throws InvalidFileFormatException, IOException {
		if (!verified) {
			getVerificationPageHeader();
			getWaitingForOtpTitleMsg();
			getEnterSentOtpMsg(mobileNumber);
		}
	}

	/**
	 * @author 300019224-Aishurya: Try catch if already OTP entered and method to
	 *         enter OTP
	 */
	public void EnterOtp(String otp) {
		if (!verified) {
			objAndroidGenericMethods.clickOnAndroidElement(getOtpFieldOne(), "To Enter Otp, OTP field ");
			try {
				((PressesKeyCode) aDriver).pressKeyCode(AndroidKeyCode.BACKSPACE);
				((PressesKeyCode) aDriver).pressKeyCode(AndroidKeyCode.BACKSPACE);
				((PressesKeyCode) aDriver).pressKeyCode(AndroidKeyCode.BACKSPACE);
				((PressesKeyCode) aDriver).pressKeyCode(AndroidKeyCode.BACKSPACE);
			} catch (Exception e) {

			}
			int OtpValue = Integer.parseInt(otp);
			processOtp(OtpValue / 1000);
			OtpValue = OtpValue % 1000;
			processOtp(OtpValue / 100);
			OtpValue = OtpValue % 100;
			processOtp(OtpValue / 10);
			OtpValue = OtpValue % 10;
			processOtp(OtpValue);
		}
	}

	/**
	 * @author 300019224-Aishurya: Method to enter OTP
	 */
	public void processOtp(int otp) {
		if (otp == 0) {
			((PressesKeyCode) aDriver).pressKeyCode(AndroidKeyCode.KEYCODE_0);
		} else if (otp == 1) {
			((PressesKeyCode) aDriver).pressKeyCode(AndroidKeyCode.KEYCODE_1);
		} else if (otp == 2) {
			((PressesKeyCode) aDriver).pressKeyCode(AndroidKeyCode.KEYCODE_2);
		} else if (otp == 3) {
			((PressesKeyCode) aDriver).pressKeyCode(AndroidKeyCode.KEYCODE_3);
		} else if (otp == 4) {
			((PressesKeyCode) aDriver).pressKeyCode(AndroidKeyCode.KEYCODE_4);
		} else if (otp == 5) {
			((PressesKeyCode) aDriver).pressKeyCode(AndroidKeyCode.KEYCODE_5);
		} else if (otp == 6) {
			((PressesKeyCode) aDriver).pressKeyCode(AndroidKeyCode.KEYCODE_6);
		} else if (otp == 7) {
			((PressesKeyCode) aDriver).pressKeyCode(AndroidKeyCode.KEYCODE_7);
		} else if (otp == 8) {
			((PressesKeyCode) aDriver).pressKeyCode(AndroidKeyCode.KEYCODE_8);
		} else {
			((PressesKeyCode) aDriver).pressKeyCode(AndroidKeyCode.KEYCODE_9);
		}
	}

	public void VerifyIncorrectOtpMsg() {
		getIncorrectOtpMsg();
	}

	/**
	 * @author 300019224-Aishurya: Removed if as not requred after resend btn
	 *         displayed
	 */
	public void ClickOnResendOtp() {
		objAndroidGenericMethods.waitDriver(getResendOtpBtn(), "Resend Otp Btn");
		objAndroidGenericMethods.waitDriver(getResendOtpBtn(), "Resend Otp Btn");
		objAndroidGenericMethods.waitDriver(getResendOtpBtn(), "Resend Otp Btn");
		objAndroidGenericMethods.waitDriver(getResendOtpBtn(), "Resend Otp Btn");
		objAndroidGenericMethods.waitDriver(getResendOtpBtn(), "Resend Otp Btn");
		objAndroidGenericMethods.waitDriver(getResendOtpBtn(), "Resend Otp Btn");
		getEnterOtpTitleMsg();
		objAndroidGenericMethods.clickOnAndroidElement(getResendOtpBtn(), "Resend Otp Btn ");
	}

	public void VerifyOtpCountDownMsg() {
		getResendOtpMsg();
		getOtpCounter();
		System.out.println("Timer Value is " + OtpCounter.getText());
	}

	public void clickmyAccountAdddresses() {
		objAndroidGenericMethods.clickOnAndroidElement(getmyAccountAdddresses(), "Adddresses");
	}

	public void clickContactUsbtn() {
		objAndroidGenericMethods.clickOnAndroidElement(getContactUsbtn(), "contactUsbtn");
	}

	public void clickHamburgerbtn() {
		objAndroidGenericMethods.clickOnAndroidElement(gethumburgerbtn(), "humburgerbtn");
	}

	public void clickmyAccountBtn() {
		objAndroidGenericMethods.clickOnAndroidElement(getmyAccountBtn(), "myAccountBtn");
	}

}
