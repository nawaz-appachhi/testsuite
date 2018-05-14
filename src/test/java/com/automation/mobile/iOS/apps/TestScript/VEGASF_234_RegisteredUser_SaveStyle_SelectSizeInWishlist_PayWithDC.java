package com.automation.mobile.iOS.apps.TestScript;

import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.ini4j.InvalidFileFormatException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.automation.core.Common.GlobalVariables;
import com.automation.core.Common.MobileDrivers;
import com.automation.core.mobile.iOS.iOSGenericMethods;
import com.automation.mobile.iOS.apps.ObjectRepository.Pages.AddressPage.AddNewAdressPageObjects;
import com.automation.mobile.iOS.apps.ObjectRepository.Pages.AddressPage.EditAdressPageObject;
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
 * Steps to do Automation

App(Android, IOS, PWA, Web App)
Email registered User
Home Page
List page to PDP navigation
Save
Price
Select Size
Contact Us
Apply Generic Coupons
Add New address - Home
Net Banking

 */
/**
 * 
 * @author 300019221 / Aravindanath
 *
 */
public class VEGASF_234_RegisteredUser_SaveStyle_SelectSizeInWishlist_PayWithDC {

	GlobalVariables objGlobalVariables;
	iOSGenericMethods objiOSGenericMethods;
	PLPageObjects objPLPageObjets;
	PDPageObject objPDPageObject;
	AddNewAdressPageObjects objAddNewAdressPageObjects;
	HomePageObject2 objHomePageObjects;
	MobileDrivers objMobileDrivers;
	IOSDriver<IOSElement> iDriver;
	ProfileLoginPageObject objLoginPageObject;
	WishlistPageObject objWishlistPageObject;
	CartPageObject objCartPage;
	PaymentPageObject objPaymentPageObjects;
	EditAdressPageObject objEditAddressObjects;
	AssertionPageObject objAssertionPageObject;
	String TestName = "VEGASF_234";

	@Test(priority = 1)

	public void LoginInApp() throws InterruptedException, InvalidFileFormatException, IOException

	{
		objLoginPageObject.clickOnProfileButton();
		objiOSGenericMethods.swipeDown(100, 7);
		objLoginPageObject.clickOnLogOut();
		objLoginPageObject.clickOnLogin();
		String email = iOSGenericMethods.getValueByKey(TestName, "UserName");
		String password = iOSGenericMethods.getValueByKey(TestName, "Password");
		objLoginPageObject.loginInApp(email, password);
		objLoginPageObject.clickOnLoginButton();
		objAssertionPageObject.verifyUserName();
//		objiOSGenericMethods.acceptAlert();
		objiOSGenericMethods.swipeDown( 100, 4);
		objLoginPageObject.removeAddress();
	}

	@Test(priority = 2)

	public void SearchItem() throws InterruptedException, InvalidFileFormatException, IOException {
		objHomePageObjects.emptyBag();
		objHomePageObjects.clickOnHomeButton();
		objHomePageObjects.clickOnSearchButton();
		String search = objiOSGenericMethods.getValueByKey(TestName, "Search");
		objHomePageObjects.enterSearchitem(search);
		objAssertionPageObject.VerifyAutoSuggestionList();
	}

	@Test(priority = 3)

	public void productListingPage() throws InterruptedException {
		objPLPageObjets.clickOnFilter();
		objPLPageObjets.clickOnPriceButton();
		objPLPageObjets.clickOnPrice(14);
		objPLPageObjets.clickOnApplyDiscount();
		objAssertionPageObject.verifyProductname();
		objAssertionPageObject.verifyPLPHeader();
		objAssertionPageObject.verifyPLPProductCount();
		objPLPageObjets.clickOnBrandNamePLP();
	}
	
	@Test(priority = 4)

	public void productDiscriptionPage() throws InterruptedException {
		objPDPageObject.clickOnAddToBag();
		objPDPageObject.getSizeListinString(0);
		objPDPageObject.clickonDoneButton();
		objPDPageObject.clickOnGoToBag();
	}	
	@Test(priority = 5)

	public void couponPage() throws InterruptedException {
		objAssertionPageObject.verifyMyBag();
		objiOSGenericMethods.swipeDown( 100, 3);
		objCartPage.ClickOnApplyCoupon();
		//String coupon = "MYNTRANEW300";
		//objCartPage.enterCoupon(coupon);
		objCartPage.clickOnApplyButton();

	}
	
	
	@Test(priority = 6)

	public void cartPage() throws InterruptedException {
		objiOSGenericMethods.swipeDown( 100, 6);
		objAssertionPageObject.verifyPriceDetails();
		objCartPage.clickOnplaceOrder();
		
	}

	@Test(priority = 7)
	public void newAddress() throws InterruptedException, InvalidFileFormatException, IOException {
		// Pincode,Location,Name,Address,Mobile Number
		objAssertionPageObject.verifyAddressHeaders();
		objAddNewAdressPageObjects.clickOnAddNewAddress();
		String pincode = iOSGenericMethods.getValueByKey(TestName, "Pincode");
		String locality = iOSGenericMethods.getValueByKey(TestName, "Locality");
		String name = iOSGenericMethods.getValueByKey(TestName, "Name");
		String address = iOSGenericMethods.getValueByKey(TestName, "Address");
		String mobile = iOSGenericMethods.getValueByKey(TestName, "Mobile");
		objAddNewAdressPageObjects.EnterContinueOrderAddingAddress(pincode, locality, name, address, mobile);
		objiOSGenericMethods.swipeDown( 100, 1);
		objCartPage.clickOnContinueOrder();
	}

	@Test(priority = 8)
	public void payment() throws InterruptedException {
		objAssertionPageObject.verifypaymenttext();
		objPaymentPageObjects.clickOnCreditCardPayment();
	}
	@Parameters({ "deviceName_","UDID_","platformVersion_", "URL_", "appUrl_", "screenshotPath_" })
	@BeforeTest
	public void beforeTest(String deviceName_, String UDID_, String platformVersion_, String URL_, String appUrl_, String screenshotPath_) throws InterruptedException {
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
		iDriver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		System.out.println("Test Name " + TestName);
		// Make sure that Page object object creation should be after this line
		objPLPageObjets = new PLPageObjects(iDriver);
		objPDPageObject = new PDPageObject(iDriver);
		objHomePageObjects = new HomePageObject2(iDriver);
		objAddNewAdressPageObjects = new AddNewAdressPageObjects(iDriver);
		objEditAddressObjects = new EditAdressPageObject(iDriver);
		objLoginPageObject = new ProfileLoginPageObject(iDriver);
		objPaymentPageObjects = new PaymentPageObject(iDriver);
		objWishlistPageObject = new WishlistPageObject(iDriver);
		objAssertionPageObject = new AssertionPageObject(iDriver);
		objCartPage = new CartPageObject(iDriver);
		 objiOSGenericMethods = new iOSGenericMethods(iDriver);
	}
	 @AfterTest
		public void quit() {
			iDriver.quit();
		}

	

}
