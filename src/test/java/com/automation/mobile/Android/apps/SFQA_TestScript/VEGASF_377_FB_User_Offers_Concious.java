package com.automation.mobile.Android.apps.SFQA_TestScript;

import java.io.IOException;
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
import com.automation.mobile.Android.apps.ObjectRepository.ProductDes.ProductDescriptionPageObject;
import com.automation.mobile.Android.apps.ObjectRepository.WishList.WishListPageObject;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.AndroidKeyCode;

public class VEGASF_377_FB_User_Offers_Concious {
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
	String ExcelPath;

	String testName = "VEGASF_377"; 

	@Test(priority = 1)
	public void LoginWithFacebook() throws InterruptedException, InvalidFileFormatException, IOException {
		System.out.println("=====================VEGASF_377_START=====================");
		objLoginPageObject.clickFirstLogin();
//		objLoginPageObject.clickFacebookbtn();
//		objLoginPageObject.FacebookLogin(AndroidGenericMethods.getValueByKey(testName, "UserName"), AndroidGenericMethods.getValueByKey(testName, "Password"));
//		objLoginPageObject.clickOnFacebookContinuebtn();
		objLoginPageObject.loginInApp(AndroidGenericMethods.getValueByKey(testName, "UserName"),
				AndroidGenericMethods.getValueByKey(testName, "Password"));
		objLoginPageObject.clickLogin();
		objLoginPageObject.clickpopUp();
		objLoginPageObject.clickhamburger();
		objLoginPageObject.verifyUserId();
		aDriver.pressKeyCode(AndroidKeyCode.BACK);
	} 
	@Test(priority = 2)
	public void reset() throws InterruptedException, InvalidFileFormatException, IOException, InvalidFileFormatException, IOException { 
		Reporter.log("reset");
		objAddCartPageObject.resetBag();
		objWishListPageObject.resetWishlist();
		//objCheckOutPageObject.resetAddress();
	}

	@Test(priority = 3)
	public void HomePage() throws InterruptedException, InvalidFileFormatException, IOException {
		
		objHomePageObject.clickOnSearch();
		objHomePageObject.enterSearchText(AndroidGenericMethods.getValueByKey(testName, "SearchItem"));
		aDriver.pressKeyCode(AndroidKeyCode.ENTER);
	}

	@Test(priority = 6)
	public void MoveToBag() throws InterruptedException {
		objProductDescriptionPageObject.verifyPdpTitle("pdpTitle");
		objProductDescriptionPageObject.assertProductPrice();
		objProductDescriptionPageObject.clickAddToBagbtn();
		objProductDescriptionPageObject.selectASize();
	}

	@Test(priority = 7)
	public void AddMoreFromWishlist() throws InterruptedException {
		objProductDescriptionPageObject.clickSaveToWishlist();
		objProductDescriptionPageObject.clickWishListbtn();
		objWishListPageObject.clickMoveToBag();
		objWishListPageObject.clickSizeWishList();
		objWishListPageObject.clickDoneWishListbtn();
		objWishListPageObject.clickBagBtn();
	}

	/*@Test(priority = 8)
	public void ApplyGenericCoupons() throws InterruptedException, InvalidFileFormatException, IOException {
		//objProductListPageObject.clickOkButton();
		objAddCartPageObject.verifyShoppingBagTitle();
		objAndroidGenericMethods.scrollDown(objAddCartPageObject.getApplyCouponbtn(), 100);
		objAddCartPageObject.ClickCouponCancelbtn();
		
//		objAddCartPageObject.enterCouponCode(AndroidGenericMethods.getValueByKey(testName, "couponcode"));
//		objAddCartPageObject.clickApplyCoupon();	
		
		objAddCartPageObject.clickPlaceOrder();
		
	}

	@Test(priority = 9)
	public void RemoveAddress() throws InterruptedException, InvalidFileFormatException, IOException {
		objCheckOutPageObject.verifyUserAddress();
		objCheckOutPageObject.CheckAddress();
		objCheckOutPageObject.clickContinue();
			
		}
*/
	@Parameters({"deviceName_","UDID_","platformVersion_", "URL_", "appUrl_", "screenshotPath_"})
	@BeforeTest
	public void beforeTest(String deviceName_, String UDID_, String platformVersion_, String URL_, String appUrl_, String screenshotPath_) throws Exception {
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
		aDriver = objMobileDrivers.launchAppAndroid(params);
		aDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		// Make sure that Page object object creation should be after this line
		// "aDriver= objMobileDrivers.launchAppAndroid();"
		objLoginPageObject = new LoginPageObject(aDriver);
		objHomePageObject = new HomePageObject(aDriver);
		objProductListPageObject = new ProductListPageObject(aDriver);
		objProductDescriptionPageObject = new ProductDescriptionPageObject(aDriver);
		objAddCartPageObject = new AddCartPageObject(aDriver);
		objCheckOutPageObject = new CheckOutPageObject(aDriver);
		objAndroidGenericMethods = new AndroidGenericMethods(aDriver);
		objWishListPageObject = new WishListPageObject(aDriver);

	}
	@AfterTest
	public void quit() {
		aDriver.quit();
		System.out.println("=====================VEGASF_377_END=====================");
	}

}
