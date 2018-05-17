package com.automation.mobile.Android.apps.TestScript;

import java.io.IOException;
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
import com.automation.mobile.Android.apps.ObjectRepository.ProductDes.ProductDescriptionPageObject;
import com.automation.mobile.Android.apps.ObjectRepository.WishList.WishListPageObject;

import io.appium.java_client.PressesKeyCode;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.AndroidKeyCode;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
/**
 * @author 300021280 -Sneha
 *Facebook registered user - Login
 *Home Page
 *Search - Using Autosuggest (use any query from Golden Set)
 *Save Product
 *Filter Discount
 *View Size Chart
 *Add more from wishlist
 *Free Gift
 *Remove address
 */

public class VEGASF_382_FB_User_SaveWishlist_FreeGift2 extends BaseAndroidTest{
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
	IOSDriver<IOSElement> iDriver;
	
	String testName = "VEGASF_382";
	@Test(priority = 1)
	public void LoginWithFacebook() throws InterruptedException, InvalidFileFormatException, IOException {
		System.out.println("=====================VEGASF_382_START=====================");
		objLoginPageObject.clickFirstLogin();
		//objLoginPageObject.clickFacebookbtn();
		//objLoginPageObject.FacebookLogin(AndroidGenericMethods.getValueByKey(testName, "UserName"), AndroidGenericMethods.getValueByKey(testName, "Password"));
		//objLoginPageObject.clickOnFacebookContinuebtn();
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
//		objWishListPageObject.resetWishlist();
//		objCheckOutPageObject.resetAddress();
	}
	@Test(priority = 3)
	public void SearchItem() throws InterruptedException, InvalidFileFormatException, IOException {
		Reporter.log("SearchItem");
		objHomePageObject.clickOnSearch();
		objHomePageObject.enterSearchText(AndroidGenericMethods.getValueByKey(testName, "SearchItem")+ "\\n");
		
	}

	@Test(priority = 4)
	public void PDP_SelectSizeChart() throws InterruptedException {
		objProductDescriptionPageObject.verifyPdpTitle("pdpTitle");
		objProductDescriptionPageObject.assertProductPrice();
		objProductDescriptionPageObject.clickSaveToWishlist();
        objProductDescriptionPageObject.clickAddToBagbtn();
        objProductDescriptionPageObject.clickSizeChartbtn();
        objProductDescriptionPageObject.clickCloseSizeChartbtn();
        objProductDescriptionPageObject.selectASize();
		objProductDescriptionPageObject.clickWishListbtn();
        objWishListPageObject.clickMoveToBag();
        objWishListPageObject.clickSizeWishList();
        objWishListPageObject.clickDoneWishListbtn();
        objWishListPageObject.clickBagBtn();
	}

	@Test(priority = 5)
	public void CheckDiscount() throws InterruptedException, InvalidFileFormatException, IOException {
		//objProductListPageObject.clickOkButton();     // no need to apply if reset applied
		objAndroidGenericMethods.scrollDown(objAddCartPageObject.getApplyCouponbtn(), 100);
		objAddCartPageObject.enterCouponCode(AndroidGenericMethods.getValueByKey(testName, "couponcode"));
		objAddCartPageObject.clickApplyCoupon();
		objAddCartPageObject.clickPlaceOrder();
	}
	@Test(priority = 6)
	public void RemoveAddress() throws InterruptedException, InvalidFileFormatException, IOException {
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
			// create Excel Reference
			objGlobalVariables = new GlobalVariables();
			// objExcelUtilities = new ExcelUtils();
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
					setUpTest(params.get("platform_"));
					System.out.println("TestDroid Execution Started");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println("Error :: Please change suite parameter to run locally.");
				}
        		
        }
			wd.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			// Make sure that Page object object creation should be after this line
			// "wd= objMobileDrivers.launchAppAndroid();"
			objLoginPageObject = new LoginPageObject(wd);
			objHomePageObject = new HomePageObject(wd);
			objProductListPageObject = new ProductListPageObject(wd);
			objProductDescriptionPageObject = new ProductDescriptionPageObject(wd);
			objAddCartPageObject = new AddCartPageObject(wd);
			objCheckOutPageObject = new CheckOutPageObject(wd);
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
			System.out.println("=====================VEGASF_382_END=====================");
		}
 
}
