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

/**
 * @author 300018281 Subhasis
 *
 */

/*
 * Google registered user - Login
Home Page
Search (using a keyword in the Golden Set)
Add to Bag
Verify Product Code
Show Similar Products
Place Order
Apply Personalized Coupons
Add New address - Office
*/

public class VEGASF_402_Google_User_PersonalizedCoupon extends BaseAndroidTest{ 

	GlobalVariables objGlobalVariables;
	AppiumServer objAppiumServer;
	LoginPageObject objLoginPageObject;
	HomePageObject objHomePageObject;
	
	MobileDrivers objMobileDrivers;
	ProductListPageObject objProductListPage;
	WishListPageObject objWishlistPageObject;
	ProductListPageObject objProductListPageObject;
	ProductDescriptionPageObject objProductDescriptionPageObject;
	CheckOutPageObject objCheckOutPageObject;
	AddCartPageObject objAddCartPageObject;
	AndroidGenericMethods objAndroidGenericMethods;
	PaymentPageObject objPaymentPageObject;

	String testName = "VEGASF_402"; 

	@Test(priority = 1)
	public void LoginWithGoogle() throws InterruptedException, InvalidFileFormatException, IOException {
		System.out.println("=====================VEGASF_402_START=====================");
		Reporter.log("LoginWithGoogle Test case Started Successfully");
		objLoginPageObject.clickFirstLogin();
		objLoginPageObject.loginInApp(AndroidGenericMethods.getValueByKey(testName, "UserName") , AndroidGenericMethods.getValueByKey(testName,"Password"));
		objLoginPageObject.clickLogin();
//		objLoginPageObject.clickOnGoogleSignUpOption();
//		objLoginPageObject.clickOnUseAnotherAccount();
//		objLoginPageObject.EnterGmailEmail(AndroidGenericMethods.getValueByKey(testName, "UserName"));
//		objLoginPageObject.AppEmailNextButton();
//		objLoginPageObject.EnterGmailPassword(AndroidGenericMethods.getValueByKey(testName, "Password"));
//		objLoginPageObject.AppPasswordNextButton();
//		objLoginPageObject.clickOnAgree();
		objLoginPageObject.clickpopUp(); 
		objLoginPageObject.clickhamburger();
		objLoginPageObject.verifyUserId(); 
		wd.navigate().back();
	}
	
	@Test(priority = 2)
		public void resetdata() throws InterruptedException {
			objAddCartPageObject.resetBag();
			//objWishlistPageObject.resetWishlist();
			//objCheckOutPageObject.resetAddress();

		}
	
	@Test(priority = 3)
	public void SearchItem() throws InterruptedException, InvalidFileFormatException, IOException {
		Reporter.log("SearchItem Test case Started Successfully");
		objHomePageObject.clickOnSearch();
		objHomePageObject.enterSearchText(AndroidGenericMethods.getValueByKey(testName, "SearchItem")+ "\\n");
		
	}
	
	@Test(priority = 4)
	public void AddToBag() throws InterruptedException {
		Reporter.log("AddToBag Test case Started Successfully");
		objProductDescriptionPageObject.verifyPdpTitle("pdpTitle"); // Assertion
		objProductDescriptionPageObject.assertProductPrice();
	}
	
	@Test(priority = 5)
	public void CheckForSimilarProductLink() throws InterruptedException {
		Reporter.log("CheckForSimilarProductLink Test case Started Successfully");
		objProductDescriptionPageObject.clickAddToBagbtn();
		objProductDescriptionPageObject.selectASize();
		objProductDescriptionPageObject.clickGoToBag();
	}
	
	@Test(priority = 6)
	public void 	ApplyPersonalizedCoupon() throws InterruptedException {
		Reporter.log("ApplyPersonalizedCoupon Test case Started Successfully");
		//objProductListPageObject.clickOkButton();
		objAddCartPageObject.verifyShoppingBagTitle();
        objAndroidGenericMethods.scrollDown(objAddCartPageObject.getApplyCouponbtn(), 400);
		objAddCartPageObject.ClickCouponCancelbtn();
	}

	@Test(priority = 7)
	public void 	PlaceOrder() throws InterruptedException {
		Reporter.log("PlaceOrder Test case Started Successfully");
		objAddCartPageObject.clickPlaceOrder();
	}
	
	@Test(priority = 8)
	public void AddNewAddress() throws InterruptedException, InvalidFileFormatException, IOException {
		Reporter.log("AddNewAddress Test case Started Successfully");
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
					setUpTest();
					System.out.println("TestDroid Execution Started");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println("Error :: Please change suite parameter to run locally.");
				}
        		
        }
		Reporter.log("AppLaunch Successfully");
		wd.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		// objects are created
		objLoginPageObject = new LoginPageObject(wd);
		objHomePageObject = new HomePageObject(wd);
		objProductListPageObject = new ProductListPageObject(wd);
		objProductDescriptionPageObject = new ProductDescriptionPageObject(wd);
		objCheckOutPageObject = new CheckOutPageObject(wd);
		objAddCartPageObject = new AddCartPageObject(wd);
		objWishlistPageObject = new WishListPageObject(wd);
		objAndroidGenericMethods = new AndroidGenericMethods(wd);
		objPaymentPageObject= new PaymentPageObject(wd);

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
	System.out.println("=====================VEGASF_402_END=====================");

	}

}
