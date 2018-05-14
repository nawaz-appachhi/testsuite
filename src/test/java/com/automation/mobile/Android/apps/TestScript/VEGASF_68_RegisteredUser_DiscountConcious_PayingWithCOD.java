package com.automation.mobile.Android.apps.TestScript;

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
import com.automation.mobile.Android.apps.ObjectRepository.Home.HomePageObject;
import com.automation.mobile.Android.apps.ObjectRepository.Login.LoginPageObject;
import com.automation.mobile.Android.apps.ObjectRepository.PLP.ProductListPageObject;
import com.automation.mobile.Android.apps.ObjectRepository.Payment.PaymentPageObject;
import com.automation.mobile.Android.apps.ObjectRepository.ProductDes.ProductDescriptionPageObject;
import com.automation.mobile.Android.apps.ObjectRepository.WishList.WishListPageObject;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.AndroidKeyCode;

public class VEGASF_68_RegisteredUser_DiscountConcious_PayingWithCOD {
	GlobalVariables objGlobalVariables;
	AppiumServer objAppiumServer;
	LoginPageObject objLoginPageObject;
	HomePageObject objHomePageObject;
	AndroidDriver<AndroidElement> aDriver;
	MobileDrivers objMobileDrivers;
	ProductListPageObject objProductListPageObject;
	AndroidGenericMethods objAndroidGenericMethods;
	ProductDescriptionPageObject objProductDescriptionPageObject;
	CheckOutPageObject objCheckOutPageObject;
	AddCartPageObject objAddCartPageObject;
	PaymentPageObject objPaymentPageObject;
	WishListPageObject objWishListPageObject; 
	String testName = "VEGASF_68"; 
	@Test(priority = 1)
	public void LoginInApp() throws InterruptedException, InvalidFileFormatException, IOException {
		Reporter.log("LoginInApp");
		objLoginPageObject.clickFirstLogin();
		objLoginPageObject.loginInApp(AndroidGenericMethods.getValueByKey(testName, "UserName") , AndroidGenericMethods.getValueByKey(testName,"Password"));
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
	public void EnterProduct() throws InterruptedException {
		Reporter.log("EnterProduct");
		objProductDescriptionPageObject.verifyPdpTitle("pdpTitle");
		objProductDescriptionPageObject.assertProductPrice();
		objProductDescriptionPageObject.assertProductDiscount();
	}
  
	@Test(priority = 5)
	public void AddProductToBag() throws InterruptedException {
		Reporter.log("Add Product to Cart");
		objProductDescriptionPageObject.clickSaveToWishlist();
		objProductDescriptionPageObject.clickAddToBagbtn();
		objProductDescriptionPageObject.selectASize();
	}

	@Test(priority = 6)
	public void AddProductFromWishlist() throws InterruptedException {
		Reporter.log("AddProductFromWishlist");
		objProductDescriptionPageObject.clickWishListbtn();
		objWishListPageObject.clickMoveToBag();
		objWishListPageObject.clickSizeWishList();
		objWishListPageObject.clickDoneWishListbtn();
		objWishListPageObject.clickBagBtn();
	}

	@Test(priority = 7)
	public void PlaceOrder() throws InterruptedException {
		Reporter.log("PLACEORDER");
		objProductListPageObject.clickOkButton();
		objAddCartPageObject.verifyShoppingBagTitle();
		objAddCartPageObject.verifyWishlistIcon(); 
		objAddCartPageObject.clickPlaceOrder();
	}

	@Test(priority = 9)
	public void addAdress() throws InterruptedException, InvalidFileFormatException, IOException {
		Reporter.log("Add Adderess");
		objCheckOutPageObject.verifyUserAddress();
		objCheckOutPageObject.CheckAddress();
		objCheckOutPageObject.clickContinue();
		
	}
		

		@Test(priority = 10)
		public void PaymnetWithCOD() throws InterruptedException, InvalidFileFormatException, IOException {
			Reporter.log("PaymnetWithCOD");	
		objPaymentPageObject.verifyPaymentHeader();
		try {
			objPaymentPageObject.selectPaymentOption("Cash/Card On Delivery");
		} catch (Exception e) {
			Reporter.log("COD Option is not enabled for the that amoutn");
		}
	}
	
	@Parameters({"deviceName_","UDID_","platformVersion_", "URL_", "appUrl_", "screenshotPath_"})
	@BeforeTest
	public void beforeTest(String deviceName_, String UDID_, String platformVersion_, String URL_, String appUrl_, String screenshotPath_) throws InterruptedException, MalformedURLException {
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
		aDriver = objMobileDrivers.launchAppAndroid(params);
		aDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		// Page object object creation used for the tests
		objLoginPageObject = new LoginPageObject(aDriver);
		objHomePageObject = new HomePageObject(aDriver);
		objProductListPageObject = new ProductListPageObject(aDriver);
		objProductDescriptionPageObject = new ProductDescriptionPageObject(aDriver);
		objCheckOutPageObject = new CheckOutPageObject(aDriver);
		objAddCartPageObject = new AddCartPageObject(aDriver);
		objPaymentPageObject = new PaymentPageObject(aDriver);
		objWishListPageObject = new WishListPageObject(aDriver); 
	}
	@AfterTest
	public void quit() {
		aDriver.quit();
	}

}
