package com.automation.mobile.Android.apps.SFQA_TestScript;

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
import com.automation.mobile.Android.apps.ObjectRepository.Payment.PaymentPageObject;
import com.automation.mobile.Android.apps.ObjectRepository.ProductDes.ProductDescriptionPageObject;
import com.automation.mobile.Android.apps.ObjectRepository.WishList.WishListPageObject;

import io.appium.java_client.PressesKeyCode;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.AndroidKeyCode;

/**
 * @author 300021280
 * 
 * Facebook registered user
 * 
 *Home Page
 *List page to PDP navigation
 *Add to Bag
 *Click for best Price (Coupon)
 *Show Similar Products
 *Apply Coupon
 *Percentage Discount
 *Remove address
 *Payment : Phone Pe 
 *
 */
public class VEGASF_393_FB_User_ClickforBestPrice_ApplyCoupon_PercentageDiscount_RemoveAddress_PayWithCD extends BaseAndroidTest{
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
	
	String testName = "VEGASF_393";  
	@Test(priority = 1)
	public void LoginInApp() throws InterruptedException, InvalidFileFormatException, IOException {
		System.out.println("=====================VEGASF_393_START=====================");
		Reporter.log("LoginInApp Test case Started Successfully");
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
	public void searchBrand() throws InterruptedException, InvalidFileFormatException, IOException {
		Reporter.log("searchBrand Test case Started Successfully");
		objHomePageObject.clickOnSearch();
		objHomePageObject.enterSearchText(AndroidGenericMethods.getValueByKey(testName, "SearchItem")+ "\\n");
		
	}
	
	@Test(priority = 5)
	public void addProductToBag() throws InterruptedException {
		Reporter.log("addProductToBag Test case Started Successfully");
		objProductDescriptionPageObject.verifyPdpTitle("pdpTitle");
		objProductDescriptionPageObject.assertProductPrice();
        objProductDescriptionPageObject.clickAddToBagbtn();
        objProductDescriptionPageObject.selectASize();
      //  objProductDescriptionPageObject.scrollToBestPriceNClick();;
        objProductDescriptionPageObject.clickGoToBag();
		
	}

	@Test(priority = 6)
	public void placeOrder() throws InterruptedException {
		Reporter.log("addProductFromBag Test case Started Successfully");
		//objProductListPageObject.clickOkButton();
		objAddCartPageObject.verifyShoppingBagTitle();
		objAndroidGenericMethods.scrollDown(objAddCartPageObject.getApplyCouponbtn(), 100);
		objAddCartPageObject.ClickCouponCancelbtn();
		objAddCartPageObject.clickPlaceOrder();
	}
	@Test(priority = 7)
	public void checkout() throws InterruptedException, InvalidFileFormatException, IOException {
		objCheckOutPageObject.verifyUserAddress();
		objCheckOutPageObject.removeAndAddAddress();
		objCheckOutPageObject.clickContinue();
		objPaymentPageObject.verifyPaymentHeader();
	}
	@Test(priority = 8)
	public void PaymenWithMyntCOD() throws InvalidFileFormatException, IOException, InterruptedException {
		Reporter.log("PaymenWithMynt+COD Test case Started Successfully");
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
		wd.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		// Make sure that Page object object creation should be after this line
		// "wd= objMobileDrivers.launchAppAndroid();"
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
		System.out.println("=====================VEGASF_393_START=====================");

	}

}
