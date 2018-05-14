package com.automation.mobile.Android.apps.TestScript;

import org.ini4j.InvalidFileFormatException;
import org.testng.Reporter;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
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
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.AndroidKeyCode;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.service.local.AppiumDriverLocalService;


public class VEGASF_160_RegisteredUser_Search_ConditionalDiscount_PayWithCOD {
	GlobalVariables objGlobalVariables;
	AppiumServer objAppiumServer;
	LoginPageObject objLoginPageObject;
	HomePageObject objHomePageObject;
	ProductListPageObject objProductListPageObject;
	ProductDescriptionPageObject objProductDescriptionPageObject;
	AddCartPageObject objAddCartPageObject;
	CheckOutPageObject objCheckOutPageObject;
	WishListPageObject objWishListPageObject; 
	AndroidDriver<AndroidElement> aDriver;
	MobileDrivers objMobileDrivers;
	AndroidGenericMethods objAndroidGenericMethods;
	PaymentPageObject objPaymentPageObject;
	IOSDriver<IOSElement> iDriver;
	String ExcelPath;
 
	String testName = "VEGASF_160";
	@Test(priority = 1) 
	public void LoginWithEmail() throws InterruptedException, InvalidFileFormatException, IOException {
		System.out.println("=====================VEGASF_160_START=====================");
		Reporter.log("LoginWithEmail");
		objLoginPageObject.clickFirstLogin();
		objLoginPageObject.loginInApp(AndroidGenericMethods.getValueByKey(testName, "UserName") , AndroidGenericMethods.getValueByKey(testName,"Password"));
		objLoginPageObject.clickLogin();
		objLoginPageObject.clickpopUp();
		objLoginPageObject.clickhamburger();
		objLoginPageObject.verifyUserId();
		aDriver.pressKeyCode(AndroidKeyCode.BACK); 
	}
	@Test(priority = 2)
	public void reset() throws InterruptedException, InvalidFileFormatException, IOException {
		Reporter.log("reset");
		objAddCartPageObject.resetBag(); 
		objWishListPageObject.resetWishlist();
		objCheckOutPageObject.resetAddress();
	}
	@Test(priority = 3)
	public void HomePage() throws InterruptedException, InvalidFileFormatException, IOException {
		Reporter.log("HomePage");
		objHomePageObject.clickOnSearch();
		objHomePageObject.enterSearchText(AndroidGenericMethods.getValueByKey(testName, "SearchItem"));
		aDriver.pressKeyCode(AndroidKeyCode.ENTER);
	}
	
	@Test(priority = 4)
	public void ProductDescriptionPage() throws InterruptedException {
		Reporter.log("ProductDescriptionPage");
		objProductDescriptionPageObject.verifyPdpTitle("pdpTitle");
		objProductDescriptionPageObject.assertProductPrice();
		objProductDescriptionPageObject.clickSaveToWishlist();
		objProductDescriptionPageObject.clickWishListbtn();
		objWishListPageObject.clickMoveToBag();
		objWishListPageObject.clickSizeWishList();
		objWishListPageObject.clickDoneWishListbtn();
		objWishListPageObject.clickBagBtn();
	}
	
	@Test(priority = 5)
	public void PlaceOrder() throws InterruptedException, InvalidFileFormatException, IOException {
		Reporter.log("PlaceOrder");
		//objProductListPageObject.clickOkButton();
		objAddCartPageObject.verifyShoppingBagTitle();
		objAddCartPageObject.verifyWishlistIcon();  
		objAddCartPageObject.clickPlaceOrder();
		objCheckOutPageObject.CheckAddress();
		objCheckOutPageObject.clickContinue(); 
	}
		
	@Test(priority = 6)
	public void PaymentWithMynt_COD() throws InterruptedException {
		Reporter.log("PaymentWithMynt_COD");
		objPaymentPageObject.verifyPaymentHeader();
		objPaymentPageObject.selectPaymentOption("Credit/Debit Card");

	}
	
	@Parameters({"deviceName_","UDID_","platformVersion_", "URL_", "appUrl_", "screenshotPath_"})
	@BeforeTest
	public void beforeTest(String deviceName_, String UDID_, String platformVersion_, String URL_, String appUrl_, String screenshotPath_) throws Exception {
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
		aDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		// Page object object creation should be after this line
		
		objLoginPageObject = new LoginPageObject(aDriver);
		objHomePageObject = new HomePageObject(aDriver);
		objProductListPageObject = new ProductListPageObject(aDriver);
		objProductDescriptionPageObject = new ProductDescriptionPageObject(aDriver);
		objAddCartPageObject = new AddCartPageObject(aDriver);
		objCheckOutPageObject = new CheckOutPageObject(aDriver);
		objAndroidGenericMethods = new AndroidGenericMethods(aDriver);
		objWishListPageObject = new WishListPageObject(aDriver);
		objPaymentPageObject = new PaymentPageObject(aDriver);
	}
	@AfterTest
	public void quit() {
		aDriver.quit();
		System.out.println("=====================VEGASF_160_END=====================");

	}
}