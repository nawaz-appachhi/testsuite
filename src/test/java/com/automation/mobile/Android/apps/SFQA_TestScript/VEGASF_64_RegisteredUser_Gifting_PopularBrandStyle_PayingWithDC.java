package com.automation.mobile.Android.apps.SFQA_TestScript;

import org.testng.annotations.Test;
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
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.AndroidKeyCode;

/**
 * @author 300019225-Amba Jha
 *
 */
/*
 * Pending from Payment ,Gift wrap Apply Personalized Coupons Wallet
 */

public class VEGASF_64_RegisteredUser_Gifting_PopularBrandStyle_PayingWithDC {
	GlobalVariables objGlobalVariables;
	AppiumServer objAppiumServer;
	LoginPageObject objLoginPageObject;
	HomePageObject objHomePageObject;
	AndroidDriver<AndroidElement> aDriver;
	MobileDrivers objMobileDrivers;
	ProductListPageObject objProductListPage;
	WishListPageObject objWishlistPageObject;
	ProductListPageObject objProductListPageObject;
	ProductDescriptionPageObject objProductDescriptionPageObject;
	CheckOutPageObject objCheckOutPageObject;
	AddCartPageObject objAddCartPageObject;
	AndroidGenericMethods objAndroidGenericMethods;
	PaymentPageObject objPaymentPageObject;
	WishListPageObject objWishListPageObject;

	String testName = "VEGASF_64";
  
	@Test(priority = 1)
	public void LoginWithEmail() throws InterruptedException, InvalidFileFormatException, IOException {
		Reporter.log("LoginWithEmail Test case Started Successfully");
		System.out.println("=====================VEGASF_64_START=====================");
		objLoginPageObject.clickFirstLogin();
		objLoginPageObject.loginInApp(AndroidGenericMethods.getValueByKey(testName, "UserName"),
				AndroidGenericMethods.getValueByKey(testName, "Password"));
		objLoginPageObject.clickLogin();
		objLoginPageObject.clickpopUp();
		objLoginPageObject.clickhamburger();
		objLoginPageObject.verifyUserId();
		aDriver.pressKeyCode(AndroidKeyCode.BACK);
	}

	@Test(priority = 2)
	public void reset() throws InterruptedException, InvalidFileFormatException, IOException {
		Reporter.log("reset");
		objAddCartPageObject.resetBag();
		objWishListPageObject.resetWishlist();
		//objCheckOutPageObject.resetAddress();
	}

	@Test(priority = 3)
	public void SearchItem() throws InterruptedException, InvalidFileFormatException, IOException {
		Reporter.log("LoginWithEmail Test case Started Successfully");
		objHomePageObject.verifyBigBannerView();
		objHomePageObject.clickOnSearch();
		objHomePageObject.enterSearchText(AndroidGenericMethods.getValueByKey(testName, "SearchItem"));
		aDriver.pressKeyCode(AndroidKeyCode.ENTER);
	}
	
	@Test(priority = 4) 
	public void SaveProduct() throws Exception {
		Reporter.log("SaveProduct Test case Started Successfully");
		objProductDescriptionPageObject.verifyPdpTitle("pdpTitle");
		objProductDescriptionPageObject.clickSaveToWishlist();
		objProductDescriptionPageObject.clickAddToBagbtn();
		objProductDescriptionPageObject.selectASize();
	}

	@Test(priority = 5)
	public void EmptyWishlist() throws InterruptedException {
		objProductDescriptionPageObject.clickWishListbtn();
		objWishlistPageObject.removeAllItemsFromWishlist();
		objWishlistPageObject.clickBagBtn();
	}

	/*@Test(priority = 6)
	public void ApplyPersonalizedCoupon() throws Exception {
		Reporter.log("ApplyPersonalizedCoupon Test case Started Successfully");
		//objProductListPageObject.clickOkButton();
		objAndroidGenericMethods.scrollDown(objAddCartPageObject.getApplyCouponbtn(), 400);
		objAddCartPageObject.enterCouponCode(AndroidGenericMethods.getValueByKey(testName, "couponcode"));
		objAddCartPageObject.clickApplyCoupon();
	}
	
	@Test(priority = 7)
	public void ViewDetail() throws Exception {
		Reporter.log("ViewDetail Test case Started Successfully");
		objAddCartPageObject.clickViewDetails();
		objAddCartPageObject.clickPlaceOrder();
		objCheckOutPageObject.AddNewAddress();
		objCheckOutPageObject.clickContinue();
	}

	@Test(priority = 8)
	public void PaymentWithWallet() throws InterruptedException, InvalidFileFormatException, IOException {
		objPaymentPageObject.verifyPaymentHeader();
		objPaymentPageObject.selectPaymentOption("Credit/Debit Card");
		objPaymentPageObject.enterCardNumber(AndroidGenericMethods.getValueByKey(testName, "CardNumber"));
		objPaymentPageObject.enterNameOnCard(AndroidGenericMethods.getValueByKey(testName, "NameOnCard"));
		objPaymentPageObject.clickExpiryMonts();
		objPaymentPageObject.clickExpiryYears();
		objPaymentPageObject.enterCVVNumber(AndroidGenericMethods.getValueByKey(testName, "CVVNumber"));
		objPaymentPageObject.clickPayNowBtn();
	}
	@Test(priority = 11)
	public void Verifypayment() throws InvalidFileFormatException, IOException, InterruptedException {
		aDriver.pressKeyCode(AndroidKeyCode.BACK);
		objPaymentPageObject.readOrderNumberConfirmationPage();
		objPaymentPageObject.clickOnViewOrder();
		objPaymentPageObject.VerifyOrderNumberOrderDetailsPage();
	}
*/
	@Parameters({ "deviceName_", "UDID_", "platformVersion_", "URL_", "appUrl_", "screenshotPath_" })
	@BeforeTest
	public void beforeTest(String deviceName_, String UDID_, String platformVersion_, String URL_, String appUrl_,
			String screenshotPath_) throws InterruptedException, MalformedURLException {
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
		aDriver = objMobileDrivers.launchAppAndroid(params);
		Reporter.log("AppLaunch Successfully");
		aDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		// objects are created
		objLoginPageObject = new LoginPageObject(aDriver);
		objHomePageObject = new HomePageObject(aDriver);
		objProductListPageObject = new ProductListPageObject(aDriver);
		objProductDescriptionPageObject = new ProductDescriptionPageObject(aDriver);
		objCheckOutPageObject = new CheckOutPageObject(aDriver);
		objAddCartPageObject = new AddCartPageObject(aDriver);
		objWishlistPageObject = new WishListPageObject(aDriver);
		objAndroidGenericMethods = new AndroidGenericMethods(aDriver);
		objPaymentPageObject = new PaymentPageObject(aDriver);
		objWishlistPageObject= new WishListPageObject(aDriver);

	}

	@AfterTest
	public void quit() {
		aDriver.quit();
		System.out.println("=====================VEGASF_64_END=====================");

	}

}
