package com.automation.mobile.Android.apps.TestScript;

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


public class VEGASF_400_FB_User_chkDeliveryOpt_Coupon_BOGO {
	
	GlobalVariables objGlobalVariables;
	AppiumServer objAppiumServer;
	LoginPageObject objLoginPageObject;
	HomePageObject objHomePageObject;
	ProductListPageObject objProductListPageObject;
	ProductDescriptionPageObject objProductDescriptionPageObject;
	AddCartPageObject objAddCartPageObject;
	CheckOutPageObject objCheckOutPageObject;
	PaymentPageObject objPaymentPageObject;
	AndroidDriver<AndroidElement> aDriver;
	MobileDrivers objMobileDrivers; 
	WishListPageObject objWishListPageObject;
	AndroidGenericMethods objAndroidGenericMethods;
	String testName = "VEGASF_400"; 

	@Test(priority = 1)
	public void LoginInWithEmail() throws InterruptedException, InvalidFileFormatException, IOException {
		System.out.println("=====================VEGASF_400_START=====================");
		Reporter.log("LoginInWithEmail");
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
	public void resetdata() throws InterruptedException, InvalidFileFormatException, IOException {
		Reporter.log("reset");
		objAddCartPageObject.resetBag();
		objWishListPageObject.resetWishlist();
		objCheckOutPageObject.resetAddress();
	}
	
	@Test(priority = 3)
	public void SearchItem() throws InterruptedException, InvalidFileFormatException, IOException {
		Reporter.log("SearchItem");
		objHomePageObject.clickOnSearch();
		objHomePageObject.enterSearchText(AndroidGenericMethods.getValueByKey(testName, "SearchItem"));
		aDriver.pressKeyCode(AndroidKeyCode.ENTER);
	}

	@Test(priority = 4)
	public void CheckForDelivery() throws InterruptedException {
		Reporter.log("Check for Delivery");
		objProductDescriptionPageObject.verifyPdpTitle("pdpTitle");
		objProductDescriptionPageObject.assertProductPrice();
		objProductDescriptionPageObject.clickAddToBagbtn();
		objProductDescriptionPageObject.selectASize();
		objAndroidGenericMethods.scrollToText(aDriver, "CHECK DELIVERY");
		objProductDescriptionPageObject.clickEnterPincodebtn();
		objProductDescriptionPageObject.clickPincodeTxt("560068");
		objProductDescriptionPageObject.checkDeliveryOptionsbtn();
		//objAndroidGenericMethods.scrollToText(aDriver, "+INFO");
	}

	@Test(priority = 5)
	public void MoveToBag() throws InterruptedException {
		Reporter.log("MoveToBag");
		// objAndroidGenericMethods.scrollToText(aDriver, "GO TO BAG");
		//objProductDescriptionPageObject.clickGoToBag();
		objProductDescriptionPageObject.clickbagHeaderbtn();

	}

	@Test(priority = 6)
	public void ApplyCoupon() throws InterruptedException {
		Reporter.log("Apply Coupon");
		objProductListPageObject.clickOkButton();
		objAddCartPageObject.verifyShoppingBagTitle();
		objAndroidGenericMethods.scrollDown(objAddCartPageObject.getApplyCouponbtn(), 400);
		objAddCartPageObject.ClickCouponCancelbtn();
	}

	@Test(priority = 8)
	public void BuyOneGetOne() throws InterruptedException {
		Reporter.log("BuyOneGet");
		objAddCartPageObject.clickPlaceOrder();

	}

	@Test(priority = 9)
	public void AddNewAddress_Home() throws InterruptedException, InvalidFileFormatException, IOException {
		Reporter.log("AddNewAddress");
		objCheckOutPageObject.verifyUserAddress();
		objCheckOutPageObject.CheckAddress();
		objCheckOutPageObject.clickContinue();
	}
	
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
		aDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		objLoginPageObject = new LoginPageObject(aDriver);
		objHomePageObject = new HomePageObject(aDriver);
		objProductListPageObject = new ProductListPageObject(aDriver);
		objProductDescriptionPageObject = new ProductDescriptionPageObject(aDriver);
		objAddCartPageObject = new AddCartPageObject(aDriver);
		objCheckOutPageObject = new CheckOutPageObject(aDriver);
		objPaymentPageObject = new PaymentPageObject(aDriver);
		objAndroidGenericMethods = new AndroidGenericMethods(aDriver);
		objWishListPageObject = new WishListPageObject(aDriver);
	}

	@AfterTest
	public void quit() {
		aDriver.quit();
		System.out.println("=====================VEGASF_400_END=====================");


	}
	
}
