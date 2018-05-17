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
import com.automation.mobile.Android.apps.ObjectRepository.Home.HomePageObject;
import com.automation.mobile.Android.apps.ObjectRepository.Login.LoginPageObject;
import com.automation.mobile.Android.apps.ObjectRepository.PLP.ProductListPageObject;
import com.automation.mobile.Android.apps.ObjectRepository.Payment.NetBankingPageObject;
import com.automation.mobile.Android.apps.ObjectRepository.Payment.PaymentPageObject;
import com.automation.mobile.Android.apps.ObjectRepository.ProductDes.ProductDescriptionPageObject;
import com.automation.mobile.Android.apps.ObjectRepository.WishList.WishListPageObject;

import io.appium.java_client.PressesKeyCode;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.AndroidKeyCode;

/**
 * @author 300021278 -Rakesh
 */
/*
 * Shopper: Facebook registered user - Login Home Page List page to
 * PDPnavigation Top buttons Check Delivery Option Show Similar Place Order
 * Apply Personalized Coupons Add New address - Office Payment :Credit/Debit
 * Card
 */
	public class VEGASF_399_FB_User_CheckDeliveryOption_ShowSimilar_ApplyPersonalizedCoupons_AddNewAddress_PayWithCC2 extends BaseAndroidTest{ 

		GlobalVariables objGlobalVariables;
		AppiumServer objAppiumServer;
		LoginPageObject objLoginPageObject;
		HomePageObject objHomePageObject;
		ProductListPageObject objProductListPageObject;
		ProductDescriptionPageObject objProductDescriptionPageObject; 
		AddCartPageObject objAddCartPageObject;
		CheckOutPageObject objCheckOutPageObject;
		
		MobileDrivers objMobileDrivers;
		WishListPageObject objWishListPageObject;
		AndroidGenericMethods objAndroidGenericMethods;
		PaymentPageObject objPaymentPageObject;
		NetBankingPageObject objNetBankingPageObject;
		String ExcelPath;

		String testName = "VEGASF_399"; 

		@Test(priority = 1)
		public void LoginInApp() throws InterruptedException, InvalidFileFormatException, IOException {
			System.out.println("=====================VEGASF_399_START=====================");
			Reporter.log("LoginInApp Test case Started Successfully");
			objLoginPageObject.clickFirstLogin();
			objLoginPageObject.loginInApp(AndroidGenericMethods.getValueByKey(testName, "UserName") , AndroidGenericMethods.getValueByKey(testName,"Password"));
			objLoginPageObject.clickLogin();
			//objLoginPageObject.clickFacebookbtn();
			//objLoginPageObject.FacebookLogin(AndroidGenericMethods.getValueByKey(testName, "UserName"), AndroidGenericMethods.getValueByKey(testName, "Password"));
			//objLoginPageObject.clickOnFacebookContinuebtn();
			objLoginPageObject.clickpopUp();
			objLoginPageObject.clickhamburger();
			objLoginPageObject.verifyUserId();
			Thread.sleep(1000);
			wd.navigate().back();
		}
		
		@Test(priority = 2)
		public void resetdata() throws InterruptedException {
			objAddCartPageObject.resetBag();
//			objWishListPageObject.resetWishlist();
//			objCheckOutPageObject.resetAddress();
		}

		@Test(priority = 3)
		public void homePage() throws InterruptedException, InvalidFileFormatException, IOException {
			Reporter.log("searchBrand Test case Started Successfully");
			objHomePageObject.clickOnSearch();
			objHomePageObject.enterSearchText(AndroidGenericMethods.getValueByKey(testName, "SearchItem")+ "\\n");
			
		}
		
		@Test(priority = 4)
		public void moveToBag() throws InterruptedException {
			objProductDescriptionPageObject.clickAddToBagbtn();
			objProductDescriptionPageObject.selectASize();
			objProductDescriptionPageObject.clickGoToBag();
		}
		
		@Test(priority = 5)
		public void placeOrder() throws InterruptedException {
			//objProductListPageObject.clickOkButton(); // no need to apply if reset is applied
			objAddCartPageObject.clickWishlistRH();
			objAddCartPageObject.addProductToBag();
			objAddCartPageObject.clickPlaceOrder();
		}

		@Test(priority = 6)
		public void addNewAddressOffice() throws InterruptedException, InvalidFileFormatException, IOException {
			objCheckOutPageObject.AddNewAddress();
			objCheckOutPageObject.clickContinue();
			
		}
		
		@Test(priority = 7)
		public void paymentWithCreditDebit() throws InterruptedException, InvalidFileFormatException, IOException {
			objPaymentPageObject.selectPaymentOption("Credit/Debit Card");
			objPaymentPageObject.enterCardNumber(AndroidGenericMethods.getValueByKey(testName, "CardNumber"));
			objPaymentPageObject.enterNameOnCard(AndroidGenericMethods.getValueByKey(testName, "NameOnCard"));
			objPaymentPageObject.clickExpiryMonts();
			objPaymentPageObject.clickExpiryYears();
			objPaymentPageObject.enterCVVNumber(AndroidGenericMethods.getValueByKey(testName, "CVVNumber"));
			objPaymentPageObject.clickPayNowBtn();
		}
		@Test(priority = 8)
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
		Reporter.log("AppLaunch Successfully");
		wd.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		// objects are created
		objLoginPageObject = new LoginPageObject(wd);
		objHomePageObject = new HomePageObject(wd);
		objProductListPageObject = new ProductListPageObject(wd);
		objProductDescriptionPageObject = new ProductDescriptionPageObject(wd);
		objCheckOutPageObject = new CheckOutPageObject(wd);
		objAddCartPageObject = new AddCartPageObject(wd);
		objWishListPageObject = new WishListPageObject(wd);
		objAndroidGenericMethods = new AndroidGenericMethods(wd);
		objPaymentPageObject = new PaymentPageObject(wd);

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
		System.out.println("=====================VEGASF_399_END=====================");

	}


}
