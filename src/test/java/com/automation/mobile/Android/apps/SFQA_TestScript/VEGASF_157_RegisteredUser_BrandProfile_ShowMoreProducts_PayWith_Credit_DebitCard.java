package com.automation.mobile.Android.apps.SFQA_TestScript;

import org.ini4j.InvalidFileFormatException;
import org.testng.Reporter;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import com.BaseAndroidTest;
import com.automation.core.Common.AppiumServer;
import com.automation.core.Common.GlobalVariables;

import com.automation.core.Common.MobileDrivers;
import com.automation.core.mobile.Android.AndroidGenericMethods;
import com.automation.mobile.Android.apps.ObjectRepository.AddCart.AddCartPageObject;

import com.automation.mobile.Android.apps.ObjectRepository.Checkout.CheckOutPageObject;

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

import io.appium.java_client.ios.IOSDriver;

import io.appium.java_client.ios.IOSElement;

public class VEGASF_157_RegisteredUser_BrandProfile_ShowMoreProducts_PayWith_Credit_DebitCard extends BaseAndroidTest{
	GlobalVariables objGlobalVariables;
	AppiumServer objAppiumServer;
	LoginPageObject objLoginPageObject;
	HomePageObject objHomePageObject;
	ProductListPageObject objProductListPageObject;
	ProductDescriptionPageObject objProductDescriptionPageObject;
	AddCartPageObject objAddCartPageObject;
	CheckOutPageObject objCheckOutPageObject;
	WishListPageObject objWishListPageObject;
	PaymentPageObject objPaymentPageObject;
	
	AndroidGenericMethods objAndroidGenericMethods;
	MobileDrivers objMobileDrivers;
	IOSDriver<IOSElement> iDriver;

	String testName = "VEGASF_157"; 

	@Test(priority = 1)
	public void LoginInApp() throws InterruptedException, InvalidFileFormatException, IOException {
		System.out.println("=====================VEGASF_157_START=====================");
		Reporter.log("LoginInApp Test case Started Successfully");
		objLoginPageObject.clickFirstLogin();
		objLoginPageObject.loginInApp(AndroidGenericMethods.getValueByKey(testName, "UserName"),
				AndroidGenericMethods.getValueByKey(testName, "Password"));
		objLoginPageObject.clickLogin();
		objLoginPageObject.clickpopUp();
		objLoginPageObject.clickhamburger();
		objLoginPageObject.verifyUserId();
		wd.navigate().back();

	}

	@Test(priority = 2)
	public void reset() throws InterruptedException {
		Reporter.log("reset");
		objAddCartPageObject.resetBag();
		//objWishListPageObject.resetWishlist();
		//objCheckOutPageObject.resetAddress();
	}

	@Test(priority = 3)
	public void HomePage() throws InterruptedException, InvalidFileFormatException, IOException {
		objHomePageObject.clickOnSearch();
		objHomePageObject.enterSearchText(AndroidGenericMethods.getValueByKey(testName, "SearchItem")+ "\\n");
		
	}

	@Test(priority = 4)
	public void ProductDescriptionPage() throws InterruptedException {
		objProductDescriptionPageObject.verifyPdpTitle("pdpTitle"); // Assertion
		objProductDescriptionPageObject.assertProductPrice(); // Assertion
		objProductDescriptionPageObject.clickAddToBagbtn();
		objProductDescriptionPageObject.selectASize();
		objProductDescriptionPageObject.clickSaveToWishlist();
		objProductDescriptionPageObject.clickWishListbtn();
	}

	@Test(priority = 5)
	public void WishListPage() throws InterruptedException {
		objWishListPageObject.verifyWishlistIcon(); // Assertion: verifying "Wishlist" Header
		objWishListPageObject.clickMoveToBag();
		objWishListPageObject.clickSizeWishList();
		objWishListPageObject.clickDoneWishListbtn();
		objWishListPageObject.clickBagBtn();
	}

	@Test(priority = 6)
	public void AddCartPage() throws InterruptedException, InvalidFileFormatException, IOException {
		//objProductListPageObject.clickOkButton();     // no need to apply if reset applied
		objAddCartPageObject.verifyShoppingBagTitle();// Assertion:veryfing "SHOPPINGBAF" Header
		objAddCartPageObject.verifyWishlistIcon(); // Assertion: veryfing "WISHLIST" Header
		objAndroidGenericMethods.scrollDown(objAddCartPageObject.getApplyCouponbtn(), 100);
		objAddCartPageObject.enterCouponCode(AndroidGenericMethods.getValueByKey(testName, "couponcode"));
		objAddCartPageObject.clickApplyCoupon();		// wd.hideKeyboard();

		/*
		 * Required test data for apply the coupon
		 */

		objAddCartPageObject.ClickCouponCancelbtn();
		objAddCartPageObject.clickPlaceOrder();
	}

	@Test(priority = 7)
	public void CheckOutPage() throws InterruptedException, InvalidFileFormatException, IOException {
     	objCheckOutPageObject.verifyUserAddress(); // Assertion: veryfing CheckoutPage Header "ADDRESS"
		objCheckOutPageObject.AddNewAddress();
		objCheckOutPageObject.clickContinue();

	}

	@Test(priority = 9)
	public void PaymentPage() throws InterruptedException, InvalidFileFormatException, IOException {
		objPaymentPageObject.verifyPaymentHeader(); // Assertion: Verifying payment page header "PAYMENT"
		objPaymentPageObject.selectPaymentOption("Credit/Debit Card");
		objPaymentPageObject.enterCardNumber(AndroidGenericMethods.getValueByKey(testName, "CardNumber"));
		objPaymentPageObject.enterNameOnCard(AndroidGenericMethods.getValueByKey(testName, "NameOnCard"));
		objPaymentPageObject.clickExpiryMonts();
		objPaymentPageObject.clickExpiryYears();
		objPaymentPageObject.enterCVVNumber(AndroidGenericMethods.getValueByKey(testName, "CVVNumber"));
		objPaymentPageObject.clickPayNowBtn();
	}
	@Test(priority = 10)
	public void Verifypayment() throws InvalidFileFormatException, IOException, InterruptedException {
		wd.navigate().back();
		objPaymentPageObject.readOrderNumberConfirmationPage();
		objPaymentPageObject.clickOnViewOrder();
		objPaymentPageObject.VerifyOrderNumberOrderDetailsPage();
	}
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
		wd.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		objLoginPageObject = new LoginPageObject(wd);
		objHomePageObject = new HomePageObject(wd);
		objProductListPageObject = new ProductListPageObject(wd);
		objProductDescriptionPageObject = new ProductDescriptionPageObject(wd);
		objAddCartPageObject = new AddCartPageObject(wd);
		objCheckOutPageObject = new CheckOutPageObject(wd);
		objWishListPageObject = new WishListPageObject(wd);
		objPaymentPageObject = new PaymentPageObject(wd);
		objAndroidGenericMethods = new AndroidGenericMethods(wd);
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
		System.out.println("=====================VEGASF_157_END=====================");

	}
}