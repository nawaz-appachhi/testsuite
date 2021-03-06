package com.automation.mobile.iOS.apps.SFQA_TestScript;

import org.testng.annotations.Test;
import java.awt.AWTException;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.ini4j.InvalidFileFormatException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import com.automation.core.Common.GlobalVariables;
import com.automation.core.Common.MobileDrivers;
import com.automation.core.mobile.iOS.iOSGenericMethods;
import com.automation.mobile.iOS.apps.ObjectRepository.Pages.AddressPage.AddNewAdressPageObjects;
import com.automation.mobile.iOS.apps.ObjectRepository.Pages.Assertions.AssertionPageObject;
import com.automation.mobile.iOS.apps.ObjectRepository.Pages.CartPage.CartPageObject;
import com.automation.mobile.iOS.apps.ObjectRepository.Pages.HomePage2.HomePageObject2;
import com.automation.mobile.iOS.apps.ObjectRepository.Pages.PDPage.PDPageObject;
import com.automation.mobile.iOS.apps.ObjectRepository.Pages.PLPage.PLPageObjects;
import com.automation.mobile.iOS.apps.ObjectRepository.Pages.PaymentPage.PaymentPageObject;
import com.automation.mobile.iOS.apps.ObjectRepository.Pages.ProfileLoginPage.ProfileLoginPageObject;
import com.automation.mobile.iOS.apps.ObjectRepository.Pages.WishListPage.WishlistPageObject;

import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;

/*
 * Steps to automate script :
App(Android, IOS, PWA, Web App)
Email registered User
Home Page
Search (by clicking banner or nested banner on Home Page)
Sort (By Popularity)
Save
Empty Wishlist
Gift wrap
Apply
Personalized Coupons ( Due to no test data we are using random coupon hence deviation in the flow)
View Details
Wallet
 */

/**
 * 
 * @author 300019221 / Aravindanath
 *
 */

public class VEGASF_81_RegisteredUser_Gifting_PopularBrandStyle_PayingWithDC {

	GlobalVariables objGlobalVariables;
	iOSGenericMethods objiOSGenericMethods;
	PLPageObjects objPLPageObjets;
	PDPageObject objPDPageObject;
	AddNewAdressPageObjects objAddNewAdressPageObjects;
	AssertionPageObject objAssertionPageObject;
	HomePageObject2 objHomePageObjects;
	MobileDrivers objMobileDrivers;
	IOSDriver<IOSElement> iDriver;
	ProfileLoginPageObject objLoginPageObject;
	WishlistPageObject objWishlistPageObject;
	CartPageObject objCartPage;
	PaymentPageObject objPaymentPageObjects;

	String TestName = "VEGASF_81";

	@Test(priority = 1)

	public void Login() throws InterruptedException, InvalidFileFormatException, IOException

	{
		//objiOSGenericMethods.acceptAlert();
		objLoginPageObject.clickOnProfileButton();
		objiOSGenericMethods.swipeDown(100, 8);
		objLoginPageObject.clickOnLogOut();
		objLoginPageObject.clickOnLogin();
		String email = iOSGenericMethods.getValueByKey(TestName, "UserName");
		String password = iOSGenericMethods.getValueByKey(TestName, "Password");
		objLoginPageObject.loginInApp(email, password);
		objLoginPageObject.clickOnLoginButton();
		objAssertionPageObject.verifyUserName();
	//	objiOSGenericMethods.acceptAlert();
		objiOSGenericMethods.swipeDown(100, 8);
		objLoginPageObject.removeAddress();
	}

	@Test(priority = 2)
	public void SearchItem() throws InterruptedException, AWTException, InvalidFileFormatException, IOException {
		objHomePageObjects.emptyBag();
		objHomePageObjects.clickOnHomeButton();
		objHomePageObjects.clickOnBigBanner(0);
		objHomePageObjects.clickOnNestedBanner(1);
		objHomePageObjects.clickOnHomeButton();
		objHomePageObjects.clickOnSearchButton();
		objAssertionPageObject.VerifyAutoSuggestionList();
		String search = objiOSGenericMethods.getValueByKey(TestName, "Search");
		objHomePageObjects.enterSearchitem(search);
	//	objiOSGenericMethods.acceptAlert();
	}

//	@Test(priority = 3)
//	public void Sort() throws InterruptedException, AWTException, InvalidFileFormatException, IOException {
//
//		objPLPageObjets.clickOnSort();
//		objPLPageObjets.clickOnPopularitySort();
//		objAssertionPageObject.verifyProductname();
//		objAssertionPageObject.verifyPLPHeader();
//		objAssertionPageObject.verifyPLPProductCount();
//		objPLPageObjets.clickOnBrandNamePLP();
//
//	}

	@Test(priority = 4)
	public void SaveProduct() throws InterruptedException, AWTException, InvalidFileFormatException, IOException {
		objPDPageObject.clickOnAddToBag();
		objPDPageObject.getSizeListinString(0);
		objPDPageObject.clickonDoneButton();
		objPDPageObject.clickOnGoToBag();
	}

	public void EmptyWishList() {

	}

	@Test(priority = 5)
	public void GiftWrap() throws InterruptedException, InvalidFileFormatException, IOException {
		objiOSGenericMethods.clickOkButton();
		objAssertionPageObject.verifyMyBag();
		objiOSGenericMethods.swipeDown(100, 3);
		objCartPage.clickOnGiftWrap();
		objAssertionPageObject.verifyGiftwrap();
		String recipent = objiOSGenericMethods.getValueByKey(TestName, "RecipentName");
		String msg = objiOSGenericMethods.getValueByKey(TestName, "GiftMessage");
		String sender = objiOSGenericMethods.getValueByKey(TestName, "SenderName");
		objCartPage.giftWrap(recipent, msg, sender);


	}

	@Test(priority = 6)
	public void ApplyPersonalizedCoupon() throws InterruptedException, InvalidFileFormatException, IOException {
		
		objiOSGenericMethods.SwipeUp(100, 3);
		objCartPage.ClickOnApplyCoupon();
		//objAssertionPageObject.verifyApplyCouponHeaders();
		//String coupon = objiOSGenericMethods.getValueByKey(TestName, "Coupon");
		// objCartPage.enterCoupon(coupon);
		objCartPage.clickOnApplyButton();
		objiOSGenericMethods.swipeDown(100, 7);
	//	objAssertionPageObject.verifyPriceDetails();
		objCartPage.clickOnplaceOrder();
	}

	@Test(priority = 7)
	public void addressPage() throws InterruptedException, InvalidFileFormatException, IOException {

		objAssertionPageObject.verifyAddressHeaders();
		objAddNewAdressPageObjects.clickOnAddNewAddress();
		String pincode = iOSGenericMethods.getValueByKey(TestName, "Pincode");
		String locality = iOSGenericMethods.getValueByKey(TestName, "Locality");
		String name = iOSGenericMethods.getValueByKey(TestName, "Name");
		String address = iOSGenericMethods.getValueByKey(TestName, "Address");
		String mobile = iOSGenericMethods.getValueByKey(TestName, "Mobile");
		objAddNewAdressPageObjects.EnterContinueOrderAddingOfficeAddress(pincode, locality, name, address, mobile);
		objiOSGenericMethods.swipeDown(100, 1);
		objCartPage.clickOnViewDetails();
		objCartPage.clickOnContinueOrder();
	}

	@Test(priority = 8)
	public void Payment() throws InterruptedException {

	 	objAssertionPageObject.verifypaymenttext();
		objPaymentPageObjects.clickOnCreditCardPayment();
	}

	@Parameters({ "deviceName_", "UDID_", "platformVersion_", "URL_", "appUrl_", "screenshotPath_" })
	@BeforeTest

	public void beforeTest(String deviceName_, String UDID_, String platformVersion_, String URL_, String appUrl_,
			String screenshotPath_) throws InterruptedException {
		objGlobalVariables = new GlobalVariables();
		objMobileDrivers = new MobileDrivers();
		Map<String, String> params = new HashMap<String, String>();
		params.put("deviceName_", deviceName_);
		params.put("UDID_", UDID_);
		params.put("platformVersion_", platformVersion_);
		params.put("URL_", URL_);
		params.put("appUrl_", appUrl_);
		params.put("screenshotPath_", screenshotPath_);
		iDriver = objMobileDrivers.launchAppiOS(params);
		iDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		System.out.println("Test Name " + TestName);
		// Make sure that Page object object creation should be after this line
		objPLPageObjets = new PLPageObjects(iDriver);
		objPDPageObject = new PDPageObject(iDriver);
		objHomePageObjects = new HomePageObject2(iDriver);
		objAddNewAdressPageObjects = new AddNewAdressPageObjects(iDriver);
		objLoginPageObject = new ProfileLoginPageObject(iDriver);
		objPaymentPageObjects = new PaymentPageObject(iDriver);
		objWishlistPageObject = new WishlistPageObject(iDriver);
		objCartPage = new CartPageObject(iDriver);
		objiOSGenericMethods = new iOSGenericMethods(iDriver);
		objAssertionPageObject = new AssertionPageObject(iDriver);
	}

	 @AfterTest
	public void quit() {
		iDriver.quit();
	}

}
