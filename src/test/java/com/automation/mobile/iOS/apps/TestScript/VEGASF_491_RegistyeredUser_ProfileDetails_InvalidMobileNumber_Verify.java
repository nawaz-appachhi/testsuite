package com.automation.mobile.iOS.apps.TestScript;

/**
 * @author 300021275 - Lata
 * 
 * Test Steps : Unverified number Flow (Profile)

Sign Up(Email)
Hamburger
Account
Profile Details
Enter Phone Number(incorrect number)
Verify
OTP Screen(30 sec)
 */

import org.testng.annotations.Test;
import org.testng.annotations.Test;
import org.testng.annotations.Test;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.ini4j.InvalidFileFormatException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.automation.core.Common.AppiumServer;
import com.automation.core.Common.GlobalVariables;
import com.automation.core.Common.MobileDrivers;
import com.automation.core.mobile.iOS.iOSGenericMethods;
import com.automation.mobile.iOS.apps.ObjectRepository.Pages.AddressPage.AddNewAdressPageObjects;
import com.automation.mobile.iOS.apps.ObjectRepository.Pages.AddressPage.EditAdressPageObject;
import com.automation.mobile.iOS.apps.ObjectRepository.Pages.Assertions.AssertionPageObject;
import com.automation.mobile.iOS.apps.ObjectRepository.Pages.CartPage.CartPageObject;
import com.automation.mobile.iOS.apps.ObjectRepository.Pages.HomePage2.HomePageObject2;
import com.automation.mobile.iOS.apps.ObjectRepository.Pages.PDPage.PDPageObject;
import com.automation.mobile.iOS.apps.ObjectRepository.Pages.PLPage.PLPageObjects;
import com.automation.mobile.iOS.apps.ObjectRepository.Pages.PaymentPage.PaymentPageObject;
import com.automation.mobile.iOS.apps.ObjectRepository.Pages.ProfileDetailsPage.ProfileDetailsPageObject;
import com.automation.mobile.iOS.apps.ObjectRepository.Pages.ProfileLoginPage.ProfileLoginPageObject;
import com.automation.mobile.iOS.apps.ObjectRepository.Pages.ProfileSignUpPage.ProfileSignUpPageObject;
import com.automation.mobile.iOS.apps.ObjectRepository.Pages.WishListPage.WishlistPageObject;

import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;

public class VEGASF_491_RegistyeredUser_ProfileDetails_InvalidMobileNumber_Verify {

	GlobalVariables objGlobalVariables;
	AppiumServer objAppiumServer;
	ProfileSignUpPageObject objProfileSignUpPageObject;
	PLPageObjects objPLPageObjets;
	PDPageObject objPDPageObject;
	AddNewAdressPageObjects objAddNewAdressPageObjects;
	HomePageObject2 objHomePageObject2;
	EditAdressPageObject objEditAdressPageObject;
	MobileDrivers objMobileDrivers;
	IOSDriver<IOSElement> iDriver;
	ProfileLoginPageObject objProfileLoginPageObject;
	AssertionPageObject objAssertionPageObject;
	PaymentPageObject objPaymentPageObject;
	WishlistPageObject objWishlistPageObject;
	CartPageObject objCartPageObject;
	iOSGenericMethods objiOSGenericMethods;
	ProfileDetailsPageObject objProfileDetailsPageObject;

	String TestName = "VEGASF_491";
	String UDID1;

	@Test(priority = 1)
	public void SignUp() throws InterruptedException, InvalidFileFormatException, IOException {
		objProfileLoginPageObject.clickOnProfileButton();
		objiOSGenericMethods.swipeDown(0, 6);
		objProfileLoginPageObject.clickOnLogOut();
		objProfileSignUpPageObject.clickOnSignUp();
		objProfileSignUpPageObject.signUpInApp(iOSGenericMethods.getValueByKey(TestName, "MailID"),
				iOSGenericMethods.getValueByKey(TestName, "Password"),
				iOSGenericMethods.getValueByKey(TestName, "FullName"),
				iOSGenericMethods.getValueByKey(TestName, "Mobile"));
		objProfileSignUpPageObject.clickOnFemale();
		objProfileSignUpPageObject.clickOnCreateAccountButton();
		objProfileSignUpPageObject.clickOnSkipReferralCode();
		objProfileSignUpPageObject.clickOnContinueShoppingButton();
		objAssertionPageObject.verifyUserName();
	}

	@Test(priority = 2)
	public void ResetMNV() throws InterruptedException, InvalidFileFormatException, IOException {
		objiOSGenericMethods.swipeDown(0, 5);
		objProfileDetailsPageObject.clickOnProfileDetails();
		objiOSGenericMethods.swipeDown(0, 2);
		//objAssertionPageObject.verifyGenderInProfileDetails();
		objProfileDetailsPageObject.enterMobileNumber(iOSGenericMethods.getValueByKey(TestName, "TempMobileNumber"));
		objProfileDetailsPageObject.clickOnLocation();
		objProfileDetailsPageObject.clickOnSaveDetails();
	}

	@Test(priority = 3)
	public void ProfileDetails() throws InterruptedException, InvalidFileFormatException, IOException {
		objProfileDetailsPageObject.clickOnProfileDetails();
		objAssertionPageObject.verifyProfileDetailsPageTitle();
		objiOSGenericMethods.swipeDown(0, 2);
		try {
			if(UDID1.equalsIgnoreCase(iOSGenericMethods.getValueByKey(TestName, "Device_1"))) {
				objProfileDetailsPageObject.enterMobileNumber((iOSGenericMethods.getValueByKey(TestName, "Num1")));
				
			}
			else if (UDID1.equalsIgnoreCase(iOSGenericMethods.getValueByKey(TestName, "Device_2"))){
				objProfileDetailsPageObject.enterMobileNumber((iOSGenericMethods.getValueByKey(TestName, "Num2")));
			}
		} catch (Exception e) {
		}
		objProfileDetailsPageObject.clickOnLocation();
		objProfileDetailsPageObject.clickOnSaveDetails();
		objProfileDetailsPageObject.clickOnProfileDetails();
		objiOSGenericMethods.swipeDown(0, 2);
		objProfileDetailsPageObject.clickOnVerifyButton();

	}

	@Parameters({ "deviceName_", "UDID_", "platformVersion_", "URL_", "appUrl_", "screenshotPath_" })
	@BeforeTest
	public void beforeTest(String deviceName_, String UDID_, String platformVersion_, String URL_, String appUrl_,
			String screenshotPath_) throws InterruptedException {
		objGlobalVariables = new GlobalVariables();
		objAppiumServer = new AppiumServer();
		objMobileDrivers = new MobileDrivers();
		// objAppiumServer.startServer();
		Map<String, String> params = new HashMap<String, String>();
		params.put("deviceName_", deviceName_);
		params.put("UDID_", UDID_);
		params.put("platformVersion_", platformVersion_);
		params.put("URL_", URL_);
		params.put("appUrl_", appUrl_);
		params.put("screenshotPath_", screenshotPath_);
		UDID1 = UDID_;
		iDriver = objMobileDrivers.launchAppiOS(params);
		iDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		System.out.println("Test Name " + TestName);
		objPLPageObjets = new PLPageObjects(iDriver);
		objPDPageObject = new PDPageObject(iDriver);
		objWishlistPageObject = new WishlistPageObject(iDriver);
		objPLPageObjets = new PLPageObjects(iDriver);
		objProfileLoginPageObject = new ProfileLoginPageObject(iDriver);
		objCartPageObject = new CartPageObject(iDriver);
		objAssertionPageObject = new AssertionPageObject(iDriver);
		objEditAdressPageObject = new EditAdressPageObject(iDriver);
		objAddNewAdressPageObjects = new AddNewAdressPageObjects(iDriver);
		objPaymentPageObject = new PaymentPageObject(iDriver);
		objHomePageObject2 = new HomePageObject2(iDriver);
		objiOSGenericMethods = new iOSGenericMethods(iDriver);
		objProfileDetailsPageObject = new ProfileDetailsPageObject(iDriver);
		objProfileSignUpPageObject = new ProfileSignUpPageObject(iDriver);
	}

	@AfterTest
	public void quit() {
		// iDriver.quit();
	}
}