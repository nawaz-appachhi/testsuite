package com.automation.mobile.Android.apps.SFQA_TestScript;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.ini4j.InvalidFileFormatException;
import org.testng.Reporter;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.automation.core.Common.AppiumServer;
import com.automation.core.Common.GlobalVariables;
import com.automation.core.Common.MobileDrivers;
import com.automation.core.mobile.Android.AndroidGenericMethods;
import com.automation.mobile.Android.apps.ObjectRepository.AddCart.AddCartPageObject;
import com.automation.mobile.Android.apps.ObjectRepository.Checkout.CheckOutPageObject;
import com.automation.mobile.Android.apps.ObjectRepository.Hamburger.HamburgerPageObject;
import com.automation.mobile.Android.apps.ObjectRepository.Home.HomePageObject;
import com.automation.mobile.Android.apps.ObjectRepository.Login.LoginPageObject;
import com.automation.mobile.Android.apps.ObjectRepository.PLP.ProductListPageObject;
import com.automation.mobile.Android.apps.ObjectRepository.Payment.PaymentPageObject;
import com.automation.mobile.Android.apps.ObjectRepository.ProductDes.ProductDescriptionPageObject;
import com.automation.mobile.Android.apps.ObjectRepository.WishList.WishListPageObject;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.AndroidKeyCode;
/**
 * @author 300019224-Aishurya
 *
 */
public class VEGASF_460 {
	GlobalVariables objGlobalVariables;
	AppiumServer objAppiumServer;
	LoginPageObject objLoginPageObject;
	HomePageObject objHomePageObject;
	AndroidDriver<AndroidElement> aDriver;
	MobileDrivers objMobileDrivers;
	ProductListPageObject objProductListPage;
	PaymentPageObject objPaymentPageObject;
	WishListPageObject objWishlistPageObject;
	ProductListPageObject objProductListPageObject;
	ProductDescriptionPageObject objProductDescriptionPageObject;
	CheckOutPageObject objCheckOutPageObject;
	AddCartPageObject objAddCartPageObject;
	AndroidGenericMethods objAndroidGenericMethods;
	HamburgerPageObject objHamburgerPageObject;
	 
	String testName = "VEGASF_460";
	String udidNumber ;
	
	@Parameters({"deviceName_","UDID_","platformVersion_", "URL_", "appUrl_", "screenshotPath_"})
	@BeforeTest
	public void beforeTest(String deviceName_, String UDID_, String platformVersion_, String URL_, String appUrl_, String screenshotPath_) throws InterruptedException, MalformedURLException {
		objGlobalVariables = new GlobalVariables();
		objAppiumServer = new AppiumServer();
		objMobileDrivers = new MobileDrivers();
		Map<String, String> params = new HashMap<String, String>();
        params.put("deviceName_", deviceName_);
        params.put("UDID_", UDID_);
        params.put("platformVersion_", platformVersion_);
        params.put("URL_", URL_);
        params.put("appUrl_", appUrl_);
        params.put("screenshotPath_", screenshotPath_);
        udidNumber = params.get("UDID_");
        System.out.println("testName is getting updated to UDID"+testName);
		aDriver = objMobileDrivers.launchAppAndroid(params);
		Reporter.log("AppLaunch Successfully");
		aDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		objLoginPageObject = new LoginPageObject(aDriver);
		objHomePageObject = new HomePageObject(aDriver);
		objProductListPageObject = new ProductListPageObject(aDriver);
		objProductDescriptionPageObject = new ProductDescriptionPageObject(aDriver);
		objCheckOutPageObject = new CheckOutPageObject(aDriver);
		objAddCartPageObject = new AddCartPageObject(aDriver);
		objWishlistPageObject = new WishListPageObject(aDriver);
		objAndroidGenericMethods = new AndroidGenericMethods(aDriver);
		objPaymentPageObject = new PaymentPageObject(aDriver);
		objHamburgerPageObject = new HamburgerPageObject(aDriver);
	}
	
	@Test(priority = 1)
	public void Login() throws InterruptedException, InvalidFileFormatException, IOException {
		System.out.println("=====================VEGASF_460_START=====================");
		Reporter.log("LoginInApp");
		objLoginPageObject.clickFirstLogin();
		objLoginPageObject.loginInApp(AndroidGenericMethods.getValueByKey(testName, "UserName"),
				AndroidGenericMethods.getValueByKey(testName, "Password"));
		objLoginPageObject.clickLogin();
		objLoginPageObject.givePermission.click();
		objLoginPageObject.allowBtn.click();
		objLoginPageObject.allowBtn.click();
		objLoginPageObject.allowBtn.click();
		//objLoginPageObject.clickpopUp();
		objLoginPageObject.clickhamburger();
		objLoginPageObject.verifyUserId();
		aDriver.pressKeyCode(AndroidKeyCode.BACK);
	}
	
	@Test(priority = 2)
	public void Account() {
		objLoginPageObject.clickhamburger();
	} 
	@Test(priority = 3)
	public void ProfileDetails() throws InvalidFileFormatException, IOException {
		objAndroidGenericMethods.scrolltoElementAndClick( objHamburgerPageObject.getmyAccountBtn(), 2000);
		objHamburgerPageObject.resetMnv();
	} 
	@Test(priority = 4)
	public void MobileNumber() throws InvalidFileFormatException, IOException {
		objHamburgerPageObject.isVerified();
		objHamburgerPageObject.verifyProfilePageTextMsg();
		objHamburgerPageObject.enterProfilePageMobileNumber(AndroidGenericMethods.getValueByKey(udidNumber, "MobileNumber"));
		} 
	@Test(priority = 5)
	public void Verify() throws InvalidFileFormatException, IOException, InterruptedException {
		objHamburgerPageObject.clickProfilePageVerifyBtn();
		objHamburgerPageObject.verifyTextMsgVerificationPage(AndroidGenericMethods.getValueByKey(udidNumber, "MobileNumber"));
		String otp=objHamburgerPageObject.readOtpFromInbox(AndroidGenericMethods.getValueByKey(udidNumber, "msgAppPackage"),AndroidGenericMethods.getValueByKey(udidNumber, "msgAppActivity"));
		objHamburgerPageObject.EnterOtp(otp);
	} 
	
	@AfterTest
	public void quit() {
	aDriver.quit();
	System.out.println("=====================VEGASF_460_END=====================");
	}

}
