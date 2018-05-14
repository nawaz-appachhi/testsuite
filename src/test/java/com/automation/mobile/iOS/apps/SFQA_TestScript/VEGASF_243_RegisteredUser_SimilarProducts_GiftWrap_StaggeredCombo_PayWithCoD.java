package com.automation.mobile.iOS.apps.SFQA_TestScript;

import org.testng.annotations.Test;
import org.testng.annotations.Test;
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
import com.automation.core.Common.AppiumServer;
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
import com.automation.mobile.iOS.apps.ObjectRepository.Pages.ProfileSignUpPage.ProfileSignUpPageObject;
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
Add to Bag
Similar Products
Size Chart
Gift wrap
Staggered Combo ( Deviation in the flow due to test data)
Add New address - Home
COD
 */

/**
 * 
 * @author 300019221
 *
 */

public class VEGASF_243_RegisteredUser_SimilarProducts_GiftWrap_StaggeredCombo_PayWithCoD {

	GlobalVariables objGlobalVariables;
	AppiumServer objAppiumServer;
	ProfileLoginPageObject objLoginPageObject;
	ProfileSignUpPageObject objProfileSignUpPageObject;
	PLPageObjects objPLPageObjets;
	PDPageObject objPDPageObject;
	AddNewAdressPageObjects objAddNewAdressPageObjects;
	HomePageObject2 objHomePageObject2;
	EditAdressPageObject objEditAdressPageObject;
	MobileDrivers objMobileDrivers;
	IOSDriver<IOSElement> iDriver;
	ProfileLoginPageObject objProfileLoginPageObject;
	AssertionPageObject objAssertionPageObject;
	PaymentPageObject objPaymentPageObject;
	WishlistPageObject objWishlistPageObject;
	CartPageObject objCartPageObject;
	iOSGenericMethods objiOSGenericMethods;

	String TestName = "VEGASF_243";

	@Test(priority = 1)
	public void LoginInApp() throws InterruptedException, InvalidFileFormatException, IOException {
		objLoginPageObject.clickOnProfileButton();
		objiOSGenericMethods.swipeDown(100, 8);
		objLoginPageObject.clickOnLogOut();
		objLoginPageObject.clickOnLogin();
		String email = iOSGenericMethods.getValueByKey(TestName, "UserName");
		String password = iOSGenericMethods.getValueByKey(TestName, "Password");
		objLoginPageObject.loginInApp(email, password);
		objLoginPageObject.clickOnLoginButton();
		objAssertionPageObject.verifyUserName();
		objiOSGenericMethods.acceptAlert();
		objiOSGenericMethods.swipeDown(100, 6);
		objLoginPageObject.removeAddress();
	}

	@Test(priority = 2)
	public void SearchItem() throws InterruptedException, InvalidFileFormatException, IOException {
		objHomePageObject2.emptyBag();
		objHomePageObject2.clickOnHomeButton();
		objHomePageObject2.clickOnSearchButton();
		objAssertionPageObject.VerifyAutoSuggestionList();
		String search = objiOSGenericMethods.getValueByKey(TestName, "Search");
		objHomePageObject2.enterSearchitem(search);
	}

//	@Test(priority = 3)
//
//	public void productListingPage() throws InterruptedException {
//		objAssertionPageObject.verifyProductname();
//		objAssertionPageObject.verifyPLPHeader();
//		objAssertionPageObject.verifyPLPProductCount();
//		objPLPageObjets.clickOnBrandNamePLP();
//
//	}

	@Test(priority = 4)
	public void AddToBag() throws InterruptedException {
		objPDPageObject.clickOnAddToBag();
		objPDPageObject.clickOnSizeChart();
		objPDPageObject.clickOnSizeChartBackButton();
//		objiOSGenericMethods.swipeDown(100, 14);
//		objPDPageObject.clickOnViewSimilar();
//		objPLPageObjets.clickOnBrandNamePLP();
		objPDPageObject.clickOnAddToBag();
		objPDPageObject.getSizeListinString(0);
		objPDPageObject.clickonDoneButton();
		objPDPageObject.clickOnGoToBag();

	}

	@Test(priority = 5)
	public void PlaceOrder() throws InterruptedException, InvalidFileFormatException, IOException {
		objAssertionPageObject.verifyMyBag();
		objiOSGenericMethods.swipeDown(100, 3);
		objCartPageObject.clickOnGiftWrapButton();
		String recipentName = objiOSGenericMethods.getValueByKey(TestName, "RecipentName");
		String msg = objiOSGenericMethods.getValueByKey(TestName, "GiftMessage");
		String senderName = objiOSGenericMethods.getValueByKey(TestName, "SenderName");
		objCartPageObject.giftWrap(recipentName, msg, senderName);
		objiOSGenericMethods.SwipeUp(100, 3);
		objCartPageObject.clickOnSaveGift();
		objiOSGenericMethods.swipeDown(100, 5);
		objAssertionPageObject.verifyPriceDetails();
		objCartPageObject.clickOnplaceOrder();

	}

	@Test(priority = 6)
	public void AddNewAddress() throws InterruptedException, InvalidFileFormatException, IOException {
		objAssertionPageObject.verifyAddressHeaders();
		objAddNewAdressPageObjects.clickOnAddNewAddress();
		String pincode = iOSGenericMethods.getValueByKey(TestName, "Pincode");
		String locality = iOSGenericMethods.getValueByKey(TestName, "Locality");
		String name = iOSGenericMethods.getValueByKey(TestName, "Name");
		String address = iOSGenericMethods.getValueByKey(TestName, "Address");
		String mobile = iOSGenericMethods.getValueByKey(TestName, "Mobile");
		objAddNewAdressPageObjects.EnterContinueOrderAddingAddress(pincode, locality, name, address, mobile);
		objiOSGenericMethods.swipeDown(100, 1);
		objCartPageObject.clickOnContinueOrder();
	}

	@Test(priority = 7)
	public void payment() throws InterruptedException {
		objAssertionPageObject.verifypaymenttext();
		objPaymentPageObject.clickOnCOD();
	}

	@Parameters({ "deviceName_","UDID_","platformVersion_", "URL_", "appUrl_", "screenshotPath_" })
	@BeforeTest
	public void beforeTest(String deviceName_, String UDID_, String platformVersion_, String URL_, String appUrl_, String screenshotPath_) throws InterruptedException {

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
		iDriver = objMobileDrivers.launchAppiOS(params);
		iDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		System.out.println("Test Name " + TestName);
		objPLPageObjets = new PLPageObjects(iDriver);
		objPDPageObject = new PDPageObject(iDriver);
		objWishlistPageObject = new WishlistPageObject(iDriver);
		objPLPageObjets = new PLPageObjects(iDriver);
		objLoginPageObject = new ProfileLoginPageObject(iDriver);
		objCartPageObject = new CartPageObject(iDriver);
		objAssertionPageObject = new AssertionPageObject(iDriver);
		objEditAdressPageObject = new EditAdressPageObject(iDriver);
		objAddNewAdressPageObjects = new AddNewAdressPageObjects(iDriver);
		objPaymentPageObject = new PaymentPageObject(iDriver);
		objHomePageObject2 = new HomePageObject2(iDriver);
		objiOSGenericMethods = new iOSGenericMethods(iDriver);
	}
	 @AfterTest
		public void quit() {
			iDriver.quit();
		}
}
