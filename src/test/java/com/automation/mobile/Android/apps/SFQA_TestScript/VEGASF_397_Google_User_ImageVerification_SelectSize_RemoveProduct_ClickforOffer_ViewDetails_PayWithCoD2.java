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
Search - Using Autosuggest (use any query from Golden Set)
Top buttons
Image Verification
Select Size from wishlist page
Remove Product
Click for offer
View Details
Payment : Mynt+ COD
*/

public class VEGASF_397_Google_User_ImageVerification_SelectSize_RemoveProduct_ClickforOffer_ViewDetails_PayWithCoD2 extends BaseAndroidTest{

	GlobalVariables objGlobalVariables;
	AppiumServer objAppiumServer;
	LoginPageObject objLoginPageObject;
	HomePageObject objHomePageObject;
	
	MobileDrivers objMobileDrivers; 
	ProductListPageObject objProductListPage;
	PaymentPageObject objPaymentPageObject;
	WishListPageObject objWishlistPageObject;
	ProductListPageObject objProductListPageObject;
	ProductDescriptionPageObject objProductDescriptionPageObject;
	CheckOutPageObject objCheckOutPageObject;
	AddCartPageObject objAddCartPageObject;
	AndroidGenericMethods objAndroidGenericMethods;
	WishListPageObject objWishListPageObject;

	String testName = "VEGASF_397"; 

	@Test(priority = 1)
	public void LoginWithGoogle() throws InterruptedException, InvalidFileFormatException, IOException {
		System.out.println("=====================VEGASF_397_START=====================");
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
//		objLoginPageObject.GoogleAcceptButton();
	}
//	@Test(priority = 2)
	public void HomePage() throws InterruptedException, InvalidFileFormatException, IOException {
		Reporter.log("HomePage Test case Started Successfully");
		objLoginPageObject.clickpopUp();
		objLoginPageObject.clickhamburger();
		objLoginPageObject.verifyUserId();
		wd.navigate().back();
	}
	@Test(priority = 3)
		public void reset() throws InterruptedException {
			Reporter.log("reset");
			objAddCartPageObject.resetBag();
//			objWishListPageObject.resetWishlist();
//			objCheckOutPageObject.resetAddress();
	}

	@Test(priority = 4)
	public void SearchItem() throws InterruptedException, InvalidFileFormatException, IOException {
		Reporter.log("SearchItem Test case Started Successfully");
		objHomePageObject.clickOnSearch();
		objHomePageObject.enterSearchText(AndroidGenericMethods.getValueByKey(testName, "SearchItem")+ "\\n");
		
	}
	
	@Test(priority = 5)
	public void SelectSize() throws InterruptedException {
		Reporter.log("SelectSize");
		objProductDescriptionPageObject.verifyPdpTitle("pdpTitle"); // Assertion
		objProductDescriptionPageObject.clickSaveToWishlist();
		objProductDescriptionPageObject.clickAddToBagbtn();
		objProductDescriptionPageObject.selectASize();
		objProductDescriptionPageObject.clickWishListbtn();
	}

	@Test(priority = 6)
	public void AddToBag() throws Exception {
		Reporter.log("SelectSize Test case Started Successfully");
		objWishlistPageObject.verifyWishlistIcon();
		objWishlistPageObject.clickMoveToBag();
		objWishlistPageObject.clickSizeWishList();
		objWishlistPageObject.clickDoneWishListbtn();
		objWishlistPageObject.clickBagBtn();
	}
	@Test(priority = 7)
	public void ApplyCoupan() throws InterruptedException {
		Reporter.log("ApplyCoupan");
	//	objProductListPageObject.clickOkButton(); // no need to apply if reset is applied
		objAddCartPageObject.verifyShoppingBagTitle();
		objAndroidGenericMethods.scrollDown(objAddCartPageObject.getApplyCouponbtn(), 100);
		objAddCartPageObject.ClickCouponCancelbtn();
	}
	
	@Test(priority = 8)
	public void ViewDetails() throws InterruptedException, InvalidFileFormatException, IOException {
		Reporter.log("ViewDetails Test case Started Successfully");
		objAddCartPageObject.clickViewDetails();
		objAddCartPageObject.clickPlaceOrder();
		objCheckOutPageObject.AddNewAddress();
		objCheckOutPageObject.clickContinue();
	}

	@Test(priority = 9)
	public void PaymenWithMyntCOD() {
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
		objPaymentPageObject = new PaymentPageObject(wd);
		objAndroidGenericMethods = new AndroidGenericMethods(wd);
		objWishListPageObject=new WishListPageObject(wd);
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
		System.out.println("=====================VEGASF_397_END=====================");

	}


}
