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
import com.automation.mobile.Android.apps.ObjectRepository.Payment.NetBankingPageObject;
import com.automation.mobile.Android.apps.ObjectRepository.Payment.PaymentPageObject;
import com.automation.mobile.Android.apps.ObjectRepository.ProductDes.ProductDescriptionPageObject;
import com.automation.mobile.Android.apps.ObjectRepository.WishList.WishListPageObject;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.AndroidKeyCode;

//App(Android, IOS, PWA, Web App)
//Email registered User
//Home Page
//Search (by clicking banner or nested banner on Home Page)
//Filter
//Discount
//Show Similar
//Gift wrap
//Click for offer
//Add New address - Office 
//Net Banking
public class VEGASF_167_RegisteredUser_Filter_Discount_GiftWrap_PayWithCredit_DebitCard {
	
	GlobalVariables objGlobalVariables;
	AppiumServer objAppiumServer;
	LoginPageObject objLoginPageObject; 
	AndroidDriver<AndroidElement> aDriver;
	MobileDrivers objMobileDrivers;
	HomePageObject objHomePageObject;
	ProductListPageObject objProductListPageObject;
	ProductDescriptionPageObject objProductDescriptionPageObject;
	WishListPageObject objWishListPageObject;
	AddCartPageObject objAddCartPageObject;
	CheckOutPageObject objCheckOutPageObject;
	AndroidGenericMethods objAndroidGenericMethods;
	NetBankingPageObject objNetBankingPageObject;
	PaymentPageObject objPaymentPageObject;

	String testName = "VEGASF_167";
	@Test(priority = 1)
	public void LoginInApp() throws Exception {
		System.out.println("=====================VEGASF_167_START=====================");
		Reporter.log("LoginInApp Test case Started Successfully");
		objLoginPageObject.clickFirstLogin();
		objLoginPageObject.loginInApp(AndroidGenericMethods.getValueByKey(testName, "UserName") , AndroidGenericMethods.getValueByKey(testName,"Password"));
		objLoginPageObject.clickLogin();
		objLoginPageObject.clickpopUp();
		objLoginPageObject.clickhamburger();
		objLoginPageObject.verifyUserId();
		aDriver.pressKeyCode(AndroidKeyCode.BACK);  
	}
	
	@Test(priority = 2)
	public void reset() throws InterruptedException {
		Reporter.log("reset");
		objAddCartPageObject.resetBag();
		//objWishListPageObject.resetWishlist();
		//objCheckOutPageObject.resetAddress();
	}
	
	@Test(priority = 3)
	public void HomePage() throws Exception {
		objHomePageObject.clickOnSearch();
		objHomePageObject.enterSearchText(AndroidGenericMethods.getValueByKey(testName, "SearchItem"));
		aDriver.pressKeyCode(AndroidKeyCode.ENTER);
	}
	
	@Test(priority = 5)
	public void PDP() throws Exception {
		objProductDescriptionPageObject.verifyPdpTitle("pdpTitle");
		objProductDescriptionPageObject.assertProductPrice();
		objProductDescriptionPageObject.assertProductStrikedPrice();
		objProductDescriptionPageObject.assertProductDiscount();
		objProductDescriptionPageObject.clickAddToBagbtn();
		objProductDescriptionPageObject.selectASize();
		objProductDescriptionPageObject.clickGoToBag();
		
	}
	@Test(priority = 6)
	public void Checkout() throws Exception {
	//	objProductListPageObject.clickOkButton();
		objAddCartPageObject.verifyShoppingBagTitle();// Assertion
	    objAddCartPageObject.verifyWishlistIcon();    //Assertion
		objAndroidGenericMethods.scrolltoElementAndClick(objAddCartPageObject.getGiftWrapbtn(), 100);
		objAddCartPageObject.enterCouponCode(AndroidGenericMethods.getValueByKey(testName, "couponcode"));
		objAddCartPageObject.clickApplyCoupon();
	    objAddCartPageObject.ClickCouponCancelbtn();
		objAddCartPageObject.clickPlaceOrder();
		objCheckOutPageObject.verifyUserAddress();
		objCheckOutPageObject.AddNewAddress();
		objCheckOutPageObject.clickContinue(); 
	}
	@Test(priority = 7)
	public void paymentPage() throws Exception {
		objPaymentPageObject.verifyPaymentHeader();
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
		aDriver.pressKeyCode(AndroidKeyCode.BACK);
		objPaymentPageObject.readOrderNumberConfirmationPage();
		objPaymentPageObject.clickOnViewOrder();
		objPaymentPageObject.VerifyOrderNumberOrderDetailsPage();
	}
	
	@Parameters({"deviceName_","UDID_","platformVersion_", "URL_", "appUrl_", "screenshotPath_"})
	@BeforeTest
	public void beforeTest(String deviceName_, String UDID_, String platformVersion_, String URL_, String appUrl_, String screenshotPath_) throws InterruptedException, MalformedURLException {

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
		// Make sure that Page object object creation should be after this line
		objAndroidGenericMethods = new AndroidGenericMethods(aDriver);
		objLoginPageObject = new LoginPageObject(aDriver);
		objHomePageObject = new HomePageObject(aDriver);
		objProductListPageObject = new ProductListPageObject(aDriver);
		objProductDescriptionPageObject = new ProductDescriptionPageObject(aDriver);
		objWishListPageObject = new WishListPageObject(aDriver);
		objAddCartPageObject = new AddCartPageObject(aDriver);
		objCheckOutPageObject = new CheckOutPageObject(aDriver);
		objAndroidGenericMethods = new AndroidGenericMethods(aDriver);
		objNetBankingPageObject = new NetBankingPageObject(aDriver);
		objPaymentPageObject= new PaymentPageObject(aDriver);

	}
	@AfterTest
	public void quit() {
		aDriver.quit();
		System.out.println("=====================VEGASF_167_END=====================");
	}
}

