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
import com.automation.mobile.Android.apps.ObjectRepository.Payment.PaymentPageObject;
import com.automation.mobile.Android.apps.ObjectRepository.ProductDes.ProductDescriptionPageObject;
import com.automation.mobile.Android.apps.ObjectRepository.WishList.WishListPageObject;

import io.appium.java_client.PressesKeyCode;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.AndroidKeyCode;
//App(Android, IOS, PWA, Web App)
//Email registered User
//Home Page
//Search - Using Autosuggest (use any query from Golden Set)
//Show More Products
//Similar Products
//Show Similar
//Gift wrap
//Click for offer	
//View Details
//EMI
public class VEGASF_162_RegisteredUser_Search_ShowMore_ShowSimilar_PayWithCredit_DebitCard extends BaseAndroidTest{
	GlobalVariables objGlobalVariables;
	AppiumServer objAppiumServer;
	LoginPageObject objLoginPageObject;
	HomePageObject objHomePageObject;
	MobileDrivers objMobileDrivers;
	ProductListPageObject objProductListPageObject;
	AndroidGenericMethods objAndroidGenericMethods;
	ProductDescriptionPageObject objProductDescriptionPageObject;
	CheckOutPageObject objCheckOutPageObject;
	AddCartPageObject objAddCartPageObject;
	PaymentPageObject objPaymentPageObject;
	
	WishListPageObject objWishListPageObject;
 
	String testName = "VEGASF_162";
	@Test(priority = 1)
	public void LoginInApp() throws InterruptedException, InvalidFileFormatException, IOException {
		System.out.println("=====================VEGASF_162_START=====================");
		Reporter.log("LoginInApp");
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
		objAddCartPageObject.resetBag();
//		objWishListPageObject.resetWishlist();
//		objCheckOutPageObject.resetAddress();
	}
	
	@Test(priority = 2)
	public void searchBrand() throws InterruptedException, InvalidFileFormatException, IOException {
		Reporter.log("searchBrand");
		objHomePageObject.clickOnSearch();
		objHomePageObject.enterSearchText(AndroidGenericMethods.getValueByKey(testName, "SearchItem")+ "\\n");
		
	}

	@Test(priority = 3)
	public void addProductToBag() throws InterruptedException {
		Reporter.log("addProductToBag");
		objProductDescriptionPageObject.verifyPdpTitle("pdpTitle");
		objProductDescriptionPageObject.assertProductPrice();
		objProductDescriptionPageObject.clickAddToBagbtn(); 
		objProductDescriptionPageObject.selectASize();
		objProductDescriptionPageObject.clickGoToBag();
	}

	@Test(priority = 4)
	public void placeOrder() throws InterruptedException, InvalidFileFormatException, IOException {
		Reporter.log("placeOrder");
		//objProductListPageObject.clickOkButton();     // no need to apply if reset applied
		objAddCartPageObject.verifyShoppingBagTitle();// Assertion
		objAddCartPageObject.verifyWishlistIcon();    //Assertion
		objAndroidGenericMethods.scrollDown(objAddCartPageObject.getApplyCouponbtn(), 100);
		objAddCartPageObject.enterCouponCode(AndroidGenericMethods.getValueByKey(testName, "couponcode"));
		objAddCartPageObject.clickApplyCoupon();
		objAddCartPageObject.ClickCouponCancelbtn();
		objAddCartPageObject.clickPlaceOrder();
	}

	@Test(priority = 8)
	public void checkout() throws InterruptedException, InvalidFileFormatException, IOException {
		Reporter.log("checkout");
		objCheckOutPageObject.verifyUserAddress();
		objCheckOutPageObject.CheckAddress();
		objCheckOutPageObject.clickContinue();
		objPaymentPageObject.verifyPaymentHeader();
		objPaymentPageObject.selectPaymentOption("Credit/Debit Card");
		objPaymentPageObject.enterCardNumber(AndroidGenericMethods.getValueByKey(testName, "CardNumber"));
		objPaymentPageObject.enterNameOnCard(AndroidGenericMethods.getValueByKey(testName, "NameOnCard"));
		objPaymentPageObject.clickExpiryMonts();
		objPaymentPageObject.clickExpiryYears();
		objPaymentPageObject.enterCVVNumber(AndroidGenericMethods.getValueByKey(testName, "CVVNumber"));
		objPaymentPageObject.clickPayNowBtn();
	}
	@Test(priority = 9)
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
		// objAppiumServer.startServer();
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
		// Page object object creation used for the tests
		objAndroidGenericMethods = new AndroidGenericMethods(wd);
		objLoginPageObject = new LoginPageObject(wd);
		objHomePageObject = new HomePageObject(wd);
		objProductListPageObject = new ProductListPageObject(wd);
		objProductDescriptionPageObject = new ProductDescriptionPageObject(wd);
		objCheckOutPageObject = new CheckOutPageObject(wd);
		objAddCartPageObject = new AddCartPageObject(wd);
		objPaymentPageObject= new PaymentPageObject(wd);
		objWishListPageObject = new WishListPageObject(wd);

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
		System.out.println("=====================VEGASF_162_END=====================");


	}
}
