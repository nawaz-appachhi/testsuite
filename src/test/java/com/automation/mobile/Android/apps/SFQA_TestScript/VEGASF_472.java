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
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.BaseAndroidTest;
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

import io.appium.java_client.PressesKeyCode;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.AndroidKeyCode;
/**
 * @author 300019224-Aishurya
 *
 */
public class VEGASF_472 extends BaseAndroidTest{
	GlobalVariables objGlobalVariables;
	AppiumServer objAppiumServer;
	LoginPageObject objLoginPageObject;
	HomePageObject objHomePageObject;
	
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
	 
	String testName = "VEGASF_472"; 
	String udidNumber ;
	
	@Parameters({ "deviceName_", "UDID_", "platformVersion_", "URL_", "appUrl_", "screenshotPath_", "engine_",
			"platform_" })
	@BeforeTest
	public void beforeTest(@Optional("TD") String deviceName_, @Optional("TD") String UDID_,
			@Optional("TD") String platformVersion_, @Optional("TD") String URL_, @Optional("TD") String appUrl_,
			@Optional("TD") String screenshotPath_, @Optional("TD") String engine_, @Optional("TD") String platform_)
			throws Exception {
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
		 params.put("engine_", engine_);
		params.put("platform_", platform_);
		if (!(params.get("engine_").equalsIgnoreCase("TD")))
        {
        		wd = objMobileDrivers.launchAppAndroid(params);
        }
        else
        {
        		try {
					setUpTest();
					System.out.println("TestDroid Execution Started");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println("Error :: Please change suite parameter to run locally.");
				}
        		
        }
		Reporter.log("AppLaunch Successfully");
		wd.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		objLoginPageObject = new LoginPageObject(wd);
		objHomePageObject = new HomePageObject(wd);
		objProductListPageObject = new ProductListPageObject(wd);
		objProductDescriptionPageObject = new ProductDescriptionPageObject(wd);
		objCheckOutPageObject = new CheckOutPageObject(wd);
		objAddCartPageObject = new AddCartPageObject(wd);
		objWishlistPageObject = new WishListPageObject(wd);
		objAndroidGenericMethods = new AndroidGenericMethods(wd);
		objPaymentPageObject = new PaymentPageObject(wd);
		objHamburgerPageObject = new HamburgerPageObject(wd);
	}
	
	@Test(priority = 1)
	public void SignUp() throws InterruptedException, InvalidFileFormatException, IOException {
		System.out.println("=====================VEGASF_472_START=====================");
		Reporter.log("LoginInApp");
		objLoginPageObject.clickFirstLogin();
		objLoginPageObject.loginInApp(AndroidGenericMethods.getValueByKey(testName, "UserName"),
				AndroidGenericMethods.getValueByKey(testName, "Password"));//Code need to repace with signUp
		objLoginPageObject.clickLogin();
		objLoginPageObject.clickpopUp();
		objLoginPageObject.clickhamburger();
		objLoginPageObject.verifyUserId();
		wd.navigate().back();
	}
	@Test(priority = 2)
	public void Hamburger() {
		objLoginPageObject.clickhamburger();
	}
	@Test(priority = 3)
	public void Account() throws InvalidFileFormatException, IOException {
		objAndroidGenericMethods.scrolltoElementAndClick( objHamburgerPageObject.getmyAccountBtn(), 2000);
		objHamburgerPageObject.resetMnv();
		objHamburgerPageObject.isVerified();
	}
	@Test(priority = 4)
	public void ProfileDetails() {
		objHamburgerPageObject.clickOnProfileDetailsLink();
	}
	@Test(priority = 5)
	public void enterIncorrectPhoneNumber() throws InvalidFileFormatException, IOException {
		objHamburgerPageObject.enterPdPageMobileNumber(AndroidGenericMethods.getValueByKey(udidNumber, "MobileNumber"));
	}
	@Test(priority = 6)
	public void Verify() throws InvalidFileFormatException, IOException, InterruptedException {
		objHamburgerPageObject.clickPdPageVerifyBtn();
		objHamburgerPageObject.verifyTextMsgVerificationPage(AndroidGenericMethods.getValueByKey(udidNumber, "MobileNumber"));
		String otp=objHamburgerPageObject.readOtpFromInbox(AndroidGenericMethods.getValueByKey(udidNumber, "msgAppPackage"),AndroidGenericMethods.getValueByKey(udidNumber, "msgAppActivity"));
		objHamburgerPageObject.EnterOtp(otp);
	}
	@AfterTest
	public void quit() {
	try {
			quitAppiumSession();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		wd.quit();
	System.out.println("=====================VEGASF_472_END=====================");
	}
}
