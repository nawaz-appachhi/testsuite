package com.automation.mobile.iOS.apps.SFQA_TestScript;

import org.testng.annotations.Test;

import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.ini4j.InvalidFileFormatException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.BaseIOSTest;
import com.automation.core.Common.AppiumServer;
import com.automation.core.Common.GlobalVariables;
import com.automation.core.Common.MobileDrivers;
import com.automation.core.mobile.iOS.iOSGenericMethods;
import com.automation.mobile.iOS.apps.ObjectRepository.Categories.MenCategoriesPageObjects;
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

/**
 * 
 * 
 * Modified / Stabilized by @author 300019221 /Aravindanath  
 * 
 *
 */

/*
 * Steps to Automate: App(Android, IOS, PWA, Web App) Email registered User Home
 * Page Search (using menu item e.g. Men -> Topwear - T-Shirts Top buttons Click
 * for best Price (Coupon) Select Size Move to wishlist Apply Generic Coupons
 * Edit address Wallet
 */

public class VEGASF_99_RegisteredUser_Category_Click4BestOffer_Wishlist_PayDC extends BaseIOSTest{

	GlobalVariables objGlobalVariables;
	AppiumServer objAppiumServer;
	ProfileSignUpPageObject objProfileSignUpPageObject;
	PLPageObjects objPLPageObjets;
	PDPageObject objPDPageObject;
	AddNewAdressPageObjects objAddNewAdressPageObjects;
	HomePageObject2 objHomePageObject;
	MobileDrivers objMobileDrivers;
	//IOSDriver<IOSElement> wd;
	ProfileLoginPageObject objProfileLoginPageObject;
	AssertionPageObject objAssertionPageObject;
	PaymentPageObject objPaymentPageObject;
	MenCategoriesPageObjects objMenCategoriesPageObjects;
	WishlistPageObject objWishlistPageObject;
	EditAdressPageObject objEditAdressPageObject;
	CartPageObject objCartPageObject;
	iOSGenericMethods objiOSGenericMethods;

	String TestName = "VEGASF_99";

	@Test(priority = 1)
	public void LoginInApp() throws InterruptedException, InvalidFileFormatException, IOException

	{
		try {
			objProfileLoginPageObject.clickOnOnBoardingCrossButton();
			System.out.println("On Boarding screen appeared and closed it");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("On Boarding screen did not appear");
		}
		objProfileLoginPageObject.clickOnProfileButton();
		objiOSGenericMethods.swipeDown(100, 7);
		objProfileLoginPageObject.clickOnLogOut();
		objProfileLoginPageObject.clickOnLogin();
		String email = iOSGenericMethods.getValueByKey(TestName, "UserName");
		String password = iOSGenericMethods.getValueByKey(TestName, "Password");
		objProfileLoginPageObject.loginInApp(email, password);
		objProfileLoginPageObject.clickOnLoginButton();
		objAssertionPageObject.verifyUserName();
		objiOSGenericMethods.swipeDown(100, 4);
		objProfileLoginPageObject.removeAddress();

	}

	@Test(priority = 2)
	public void Categories() throws InterruptedException, InvalidFileFormatException, IOException {
		objHomePageObject.emptyBag();
//		objMenCategoriesPageObjects.clickOnCategories();
//		objMenCategoriesPageObjects.clickOnMen();
//		objMenCategoriesPageObjects.clickOnMenTopwear();
//		objMenCategoriesPageObjects.clickOnMenTshirt();
//		objMenCategoriesPageObjects.clickOnTshirtFirstBanner();
		objHomePageObject.clickOnHomeButton();
		objHomePageObject.clickOnSearchButton();
		objAssertionPageObject.VerifyAutoSuggestionList();
		String search = objiOSGenericMethods.getValueByKey(TestName, "Search");
		objHomePageObject.enterSearchitem(search);
//		objiOSGenericMethods.acceptAlert();

	}

	@Test(priority = 3)
	public void PLPage() throws InterruptedException {

//		objAssertionPageObject.verifyPLPHeader();
//		objAssertionPageObject.verifyPLPProductCount();
//		objPLPageObjets.clickOnFirstproductofPLP();
		objiOSGenericMethods.swipeDown(1000, 1);
		objPDPageObject.clickOnBestPrice();
//		objiOSGenericMethods.swipeDown(1000, 2);
		objPDPageObject.clickOnAddToBag();
		objPDPageObject.getSizeListinString(0);
//		objPDPageObject.clickonDoneButton();
		objPDPageObject.clickOnGoToBag();

	}

	@Test(priority = 4)
	public void MoveToWishlist() throws InterruptedException {

		//objPLPageObjets.clickOnOkAndTouch();
		objAssertionPageObject.verifyMyBag();
		objAssertionPageObject.verifyProductTitleCartPage();

	}

	@Test(priority = 5)
	public void PlaceOrder() throws InterruptedException {
		objiOSGenericMethods.swipeDown(100, 7);
		objCartPageObject.clickOnplaceOrder();
	}

	@Test(priority = 6)
	public void EditAddress() throws InterruptedException, InvalidFileFormatException, IOException {
		objAssertionPageObject.verifyAddressHeaders();
		objAddNewAdressPageObjects.clickOnAddNewAddress();
		String pincode = iOSGenericMethods.getValueByKey(TestName, "Pincode");
		String locality = iOSGenericMethods.getValueByKey(TestName, "Locality");
		String name = iOSGenericMethods.getValueByKey(TestName, "Name");
		String address = iOSGenericMethods.getValueByKey(TestName, "Address");
		String mobile = iOSGenericMethods.getValueByKey(TestName, "Mobile");
		objAddNewAdressPageObjects.EnterContinueOrderAddingAddress(pincode, locality, name, address, mobile);
		objiOSGenericMethods.swipeDown(0, 1);
		objCartPageObject.clickOnContinueOrder();
	}

	@Test(priority = 7)
	public void ConfirmOrder() throws InterruptedException {
		objAssertionPageObject.verifypaymenttext();
		objPaymentPageObject.clickOnCreditCardPayment();
	}

	@Parameters({ "deviceName_", "UDID_", "platformVersion_", "URL_", "appUrl_", "screenshotPath_", "engine_",
			"platform_" })
	@BeforeTest
	public void beforeTest(@Optional("TD") String deviceName_, @Optional("TD") String UDID_,
			@Optional("TD") String platformVersion_, @Optional("TD") String URL_, @Optional("TD") String appUrl_,
			@Optional("TD") String screenshotPath_, @Optional("TD") String engine_, @Optional("TD") String platform_)
			throws Exception {
		objGlobalVariables = new GlobalVariables();
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
	                wd =   objMobileDrivers.launchAppiOS(params);
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
		System.out.println("Test Name " + TestName);
		// Make sure that Page object object creation should be after this line
		objHomePageObject = new HomePageObject2(wd);
		objPLPageObjets = new PLPageObjects(wd);
		objPDPageObject = new PDPageObject(wd);
		objWishlistPageObject = new WishlistPageObject(wd);
		objPLPageObjets = new PLPageObjects(wd);
		objProfileLoginPageObject = new ProfileLoginPageObject(wd);
		objCartPageObject = new CartPageObject(wd);
		objAssertionPageObject = new AssertionPageObject(wd);
		objEditAdressPageObject = new EditAdressPageObject(wd);
		objPaymentPageObject = new PaymentPageObject(wd);
		objMenCategoriesPageObjects = new MenCategoriesPageObjects(wd);
		objiOSGenericMethods = new iOSGenericMethods(wd);
		objAddNewAdressPageObjects = new AddNewAdressPageObjects(wd);

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
		}
	
}
