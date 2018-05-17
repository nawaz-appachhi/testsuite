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
import com.automation.mobile.Android.apps.ObjectRepository.Payment.PaymentPageObject;
import com.automation.mobile.Android.apps.ObjectRepository.ProductDes.ProductDescriptionPageObject;
import com.automation.mobile.Android.apps.ObjectRepository.WishList.WishListPageObject;

import io.appium.java_client.PressesKeyCode;
import io.appium.java_client.android.AndroidKeyCode;

/**
 * @author 300019225-Vinay Kumar
 *
 */
public class VEGASF_383_FB_User_CheckDeliveryOption_RemoveProduct_BOGO_AddNewAddress_Office_PayWithCoD
		extends BaseAndroidTest {
	GlobalVariables objGlobalVariables;
	AppiumServer objAppiumServer;
	LoginPageObject objLoginPageObject;
	HomePageObject objHomePageObject;
	ProductDescriptionPageObject objProductDescriptionPageObject;
	MobileDrivers objMobileDrivers;
	ProductListPageObject objProductListPageObject;
	AddCartPageObject objAddCartPageObject;
	CheckOutPageObject objCheckOutPageObject;
	AndroidGenericMethods objAndroidGenericMethods;
	WishListPageObject objWishListPageObject;
	PaymentPageObject objPaymentPageObject;
	String testName = "VEGASF_383";

	@Test(priority = 1)
	public void LoginWithFacebook() throws InterruptedException, InvalidFileFormatException, IOException {
		System.out.println("=====================VEGASF_383_START=====================");
		Reporter.log("LoginWithfacebook");
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
		objWishListPageObject.resetWishlist();
		objCheckOutPageObject.resetAddress();
	}

	@Test(priority = 3)
	public void SearchItem() throws InterruptedException, InvalidFileFormatException, IOException {
		Reporter.log("SearchItem");
		objHomePageObject.clickOnSearch();
		objHomePageObject.enterSearchText(AndroidGenericMethods.getValueByKey(testName, "SearchItem")+ "\\n");
		
	}

	@Test(priority = 4)
	public void CheckForDeliveryOption() throws InterruptedException {
		Reporter.log("CheckForDeliveryOption");
		objProductDescriptionPageObject.verifyPdpTitle("pdpTitle"); // Assertion
		objProductDescriptionPageObject.assertProductPrice(); // Assertion
		objProductDescriptionPageObject.clickSaveToWishlist();
		// objAndroidGenericMethods.scrollDown(objProductDescriptionPageObject.getenterpincodebtn(),
		// 1000);
//		objAndroidGenericMethods.scrollToText(wd, "CHECK DELIVERY");
//		objProductDescriptionPageObject.clickEnterPincodebtn();
//		objProductDescriptionPageObject.clickPincodeTxt("560068");
//		objProductDescriptionPageObject.checkDeliveryOptionsbtn();
		objProductDescriptionPageObject.clickWishListbtn();
	}

	@Test(priority = 5)
	public void MoveToBag() throws InterruptedException {
		Reporter.log("MoveToBag");
		objWishListPageObject.clickMoveToBag();
		objWishListPageObject.clickSizeWishList();
		objWishListPageObject.clickDoneWishListbtn();
	}

	@Test(priority = 6)
	public void RemoveProduct() throws InterruptedException {
		Reporter.log("RemoveFromWishlist");
		objWishListPageObject.removeAllItemsFromWishlist();
		objWishListPageObject.verifyWishlistIcon(); // Assertion: verifying,"Wishlist" Header
		objWishListPageObject.clickBagBtn();
	}

	@Test(priority = 7)
	public void AddNewAddress_Office() throws InterruptedException, InvalidFileFormatException, IOException {
		Reporter.log("AddNewAddress_Office");
		// objProductListPageObject.clickOkButton();
		objAddCartPageObject.verifyShoppingBagTitle();// Assertion:veryfing "SHOPPINGBAF" Header
		objAddCartPageObject.clickPlaceOrder();
		objCheckOutPageObject.AddNewAddress();
		objCheckOutPageObject.clickContinue();
	}

	@Test(priority = 8)
	public void PaymentWithCreditDebitCard() {
		Reporter.log("PaymentWithCreditDebitCard");
		objPaymentPageObject.verifyPaymentHeader();
		objPaymentPageObject.selectPaymentOption("Credit/Debit Card");
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
		if (!(params.get("engine_").equalsIgnoreCase("TD"))) {
			wd = objMobileDrivers.launchAppAndroid(params);
		} else {
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
		objAndroidGenericMethods = new AndroidGenericMethods(wd);
		objWishListPageObject = new WishListPageObject(wd);
		objPaymentPageObject = new PaymentPageObject(wd);
	}

	@AfterTest
	public void quit() {
		try {
			quitAppiumSession();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		wd.quit();
		System.out.println("=====================VEGASF_383_END=====================");
	}
}
