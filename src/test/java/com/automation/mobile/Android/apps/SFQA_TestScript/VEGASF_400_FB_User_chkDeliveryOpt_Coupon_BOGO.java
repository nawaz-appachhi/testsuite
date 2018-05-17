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
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

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

/*Facebook registered user - Login
Home Page
Search - Using Autosuggest (use any query from Golden Set)
Sort by Discount
Check Delivery Option
Move to bag
Apply Coupon
Buy One get One
Add New address - Home*/

public class VEGASF_400_FB_User_chkDeliveryOpt_Coupon_BOGO extends BaseAndroidTest{
	
	GlobalVariables objGlobalVariables;
	AppiumServer objAppiumServer;
	LoginPageObject objLoginPageObject;
	HomePageObject objHomePageObject;
	ProductListPageObject objProductListPageObject;
	ProductDescriptionPageObject objProductDescriptionPageObject;
	AddCartPageObject objAddCartPageObject;
	CheckOutPageObject objCheckOutPageObject;
	PaymentPageObject objPaymentPageObject;
	
	MobileDrivers objMobileDrivers; 
	WishListPageObject objWishListPageObject;
	AndroidGenericMethods objAndroidGenericMethods;
	String ExcelPath;

	String testName = "VEGASF_400"; 

	@Test(priority = 1)
	public void LoginInApp() throws InterruptedException, InvalidFileFormatException, IOException {
		System.out.println("=====================VEGASF_400_START=====================");
		Reporter.log("LoginInApp Test case Started Successfully");
		objLoginPageObject.clickFirstLogin();
		// objLoginPageObject.clickFacebookbtn();
		// objLoginPageObject.FacebookLogin(AndroidGenericMethods.getValueByKey(testName,
		// "UserName"),
		// AndroidGenericMethods.getValueByKey(testName, "Password"));
		// objLoginPageObject.clickOnFacebookContinuebtn();
		objLoginPageObject.loginInApp(AndroidGenericMethods.getValueByKey(testName, "UserName"),
				AndroidGenericMethods.getValueByKey(testName, "Password"));
		objLoginPageObject.clickLogin();
		objLoginPageObject.clickpopUp(); 
		objLoginPageObject.clickhamburger();
		objLoginPageObject.verifyUserId();
		wd.navigate().back();
	}

	@Test(priority = 2)
	public void resetdata() throws InterruptedException {
		objAddCartPageObject.resetBag();
//		objWishListPageObject.resetWishlist();
//		objCheckOutPageObject.resetAddress();
	}
	
	@Test(priority = 3)
	public void searchBrand() throws InterruptedException, InvalidFileFormatException, IOException {
		Reporter.log("searchBrand Test case Started Successfully");
		objHomePageObject.clickOnSearch();
		objHomePageObject.enterSearchText(AndroidGenericMethods.getValueByKey(testName, "SearchItem")+ "\\n");
		
	}

	@Test(priority = 4)
	public void CheckForDelivery() throws InterruptedException {
		Reporter.log("Check for Delivery Test case Started Successfully");
		objProductDescriptionPageObject.verifyPdpTitle("pdpTitle");
		objProductDescriptionPageObject.assertProductPrice();
		objProductDescriptionPageObject.clickAddToBagbtn();
		objProductDescriptionPageObject.selectASize();
		// objAndroidGenericMethods.scrollToText(wd, "CHECK DELIVERY");
		// objProductDescriptionPageObject.CheckDeliverOption("560068");
		// objAndroidGenericMethods.scrollToText(wd, "+INFO");
	}

	@Test(priority = 5)
	public void MoveToBag() throws InterruptedException {
		Reporter.log("add ProductToBag Test case Started Successfully");
		// objAndroidGenericMethods.scrollToText(wd, "GO TO BAG");
		objProductDescriptionPageObject.clickGoToBag();

	}

	@Test(priority = 6)
	public void ApplyCoupon() throws InterruptedException {
		Reporter.log("Apply Coupan Test case Started Successfully");
		//objProductListPageObject.clickOkButton();
		objAddCartPageObject.verifyShoppingBagTitle();
		objAndroidGenericMethods.scrollDown(objAddCartPageObject.getApplyCouponbtn(), 400);
		objAddCartPageObject.ClickCouponCancelbtn();
	}

	@Test(priority = 8)
	public void BuyOneGetOne() throws InterruptedException {
		Reporter.log("BuyOneGet One Test case Started Successfully");
		objAddCartPageObject.clickPlaceOrder();

	}

	@Test(priority = 9)
	public void AddNewAddress_Home() throws InterruptedException, InvalidFileFormatException, IOException {
		Reporter.log("AddNewAddress case Started Successfully");
		objCheckOutPageObject.verifyUserAddress();
		objCheckOutPageObject.AddNewAddress();
		objCheckOutPageObject.clickContinue();
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
		objPaymentPageObject = new PaymentPageObject(wd);
		objAndroidGenericMethods = new AndroidGenericMethods(wd);
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
		System.out.println("=====================VEGASF_400_END=====================");


	}
	
}
