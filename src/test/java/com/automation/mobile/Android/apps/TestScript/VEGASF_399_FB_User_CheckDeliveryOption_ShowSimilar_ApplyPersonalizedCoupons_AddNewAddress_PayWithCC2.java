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
import com.automation.mobile.Android.apps.ObjectRepository.Payment.NetBankingPageObject;
import com.automation.mobile.Android.apps.ObjectRepository.Payment.PaymentPageObject;
import com.automation.mobile.Android.apps.ObjectRepository.ProductDes.ProductDescriptionPageObject;
import com.automation.mobile.Android.apps.ObjectRepository.WishList.WishListPageObject;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.AndroidKeyCode;

/**
 * @author 300021278 -Rakesh
 */

	public class VEGASF_399_FB_User_CheckDeliveryOption_ShowSimilar_ApplyPersonalizedCoupons_AddNewAddress_PayWithCC2 { 

		GlobalVariables objGlobalVariables;
		AppiumServer objAppiumServer;
		LoginPageObject objLoginPageObject;
		HomePageObject objHomePageObject;
		ProductListPageObject objProductListPageObject;
		ProductDescriptionPageObject objProductDescriptionPageObject; 
		AddCartPageObject objAddCartPageObject;
		CheckOutPageObject objCheckOutPageObject;
		AndroidDriver<AndroidElement> aDriver;
		MobileDrivers objMobileDrivers;
		WishListPageObject objWishListPageObject;
		AndroidGenericMethods objAndroidGenericMethods;
		PaymentPageObject objPaymentPageObject;
		NetBankingPageObject objNetBankingPageObject;
		
		String testName = "VEGASF_399"; 

		@Test(priority = 1)
		public void LoginInWithEmail() throws InterruptedException, InvalidFileFormatException, IOException {
			System.out.println("=====================VEGASF_399_START=====================");
			Reporter.log("LoginInWithEmail");
			objLoginPageObject.clickFirstLogin();
			objLoginPageObject.loginInApp(AndroidGenericMethods.getValueByKey(testName, "UserName") , AndroidGenericMethods.getValueByKey(testName,"Password"));
			objLoginPageObject.clickLogin();
			objLoginPageObject.clickpopUp();
			objLoginPageObject.clickhamburger();
			objLoginPageObject.verifyUserId();
			aDriver.pressKeyCode(AndroidKeyCode.BACK);
		}
		
		@Test(priority = 2)
		public void resetdata() throws InterruptedException, InvalidFileFormatException, IOException {
			Reporter.log("resetData");
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
		public void MoveToBag() throws InterruptedException {
			Reporter.log("MoveToBag");
			objProductDescriptionPageObject.clickAddToBagbtn();
			objProductDescriptionPageObject.selectASize();
			objAndroidGenericMethods.scrollToText(aDriver, "CHECK DELIVERY");
			objProductDescriptionPageObject.clickEnterPincodebtn();
			objProductDescriptionPageObject.clickPincodeTxt("560068");
			objProductDescriptionPageObject.checkDeliveryOptionsbtn();
			objAndroidGenericMethods.scrollToText(aDriver, "+INFO");
			objProductDescriptionPageObject.clickGoToBag();
		}
		
		@Test(priority = 5)
		public void PlaceOrder() throws InterruptedException {
			Reporter.log("PlaceOrder");
	//		objProductListPageObject.clickOkButton(); // no need to apply if reset is applied
			objAddCartPageObject.clickWishlistRH();
			objAddCartPageObject.addProductToBag();
			objAddCartPageObject.clickPlaceOrder();
		}

		@Test(priority = 6)
		public void AddNewAddressOffice() throws InterruptedException, InvalidFileFormatException, IOException {
			Reporter.log("AddNewAddressOffice");
			objCheckOutPageObject.CheckAddress();
			objCheckOutPageObject.clickContinue();
			
		}
		
		@Test(priority = 7)
		public void PaymentWithCreditDebit() throws InterruptedException {
			Reporter.log("PaymentWithCreditDebit");
			objPaymentPageObject.selectPaymentOption("Credit/Debit Card");
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
		Reporter.log("AppLaunch Successfully");
		aDriver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		// objects are created
		objLoginPageObject = new LoginPageObject(aDriver);
		objHomePageObject = new HomePageObject(aDriver);
		objProductListPageObject = new ProductListPageObject(aDriver);
		objProductDescriptionPageObject = new ProductDescriptionPageObject(aDriver);
		objCheckOutPageObject = new CheckOutPageObject(aDriver);
		objAddCartPageObject = new AddCartPageObject(aDriver);
		objWishListPageObject = new WishListPageObject(aDriver);
		objAndroidGenericMethods = new AndroidGenericMethods(aDriver);
		objPaymentPageObject = new PaymentPageObject(aDriver);

	}
	@AfterTest
	public void quit() {
		aDriver.quit();
		System.out.println("=====================VEGASF_399_END=====================");

	}


}
