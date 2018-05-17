package com.automation.mobile.iOS.apps.TestScript;

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
import org.testng.annotations.Test;

import com.BaseIOSTest;
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

import com.automation.mobile.iOS.apps.ObjectRepository.Categories.*;

import io.appium.java_client.TouchAction;

import io.appium.java_client.ios.IOSDriver;

import io.appium.java_client.ios.IOSElement;

/**

 * @author 300019240-Rahul Saxena

 *

 */

/**
 * 
 * 
 * 
 * Steps to do Automation
 * 
 * 
 * 
 * App(Android, IOS, PWA, Web App)
 * 
 * Email registered User
 * 
 * Home Page
 * 
 * Search (using menu item e.g. Men -> Topwear - T-Shirts
 * 
 * Save
 * 
 * Click for best Price (Coupon)
 * 
 * Size Chart
 * 
 * Contact Us
 * 
 * Click for offer
 * 
 * Add New address - Home
 * 
 * Manual GC + Online
 * 
 * 
 * 
 * 
 * 
 */

public class VEGASF_224_RegisteredUser_BrowseMenuItems_ClickForOffer_PayWithCoD extends BaseIOSTest{

	GlobalVariables objGlobalVariables;

	iOSGenericMethods objiOSGenericMethods;
	AppiumServer objAppiumServer;
	ProfileSignUpPageObject objProfileSignUpPageObjects;
	PLPageObjects objPLPageObjets;
	PDPageObject objPDPageObject;
	WishlistPageObject objWishListPageObject;
	AddNewAdressPageObjects objAddNewAdressPageObjects;
	ProfileLoginPageObject objLoginPageObject;
	EditAdressPageObject objEditAdressPageObject;
	HomePageObject2 objHomePageObjects;
	MobileDrivers objMobileDrivers;
	CartPageObject objCartPageObject;
	AssertionPageObject objAssertionPageObject;
	MenCategoriesPageObjects objMenCategoriesPageObjects;
	//IOSDriver<IOSElement> iDriver;
	ProfileLoginPageObject objProfileLoginPageObject;
	String TestName = "VEGASF_224";

	PaymentPageObject objPaymentPageObjects;

	@Test(priority = 1)
	public void LoginInApp() throws InterruptedException, InvalidFileFormatException, IOException {
		try {
			objLoginPageObject.clickOnOnBoardingCrossButton();
			System.out.println("On Boarding screen appeared and closed it");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("On Boarding screen did not appear");
		}
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
		objiOSGenericMethods.swipeDown(100, 4);
		objProfileLoginPageObject.removeAddress();

	}

	@Test(priority = 2)
	public void SearchMen_topwear_tshirt_Item() throws InterruptedException, InvalidFileFormatException, IOException {
		objHomePageObjects.emptyBag();
//		objMenCategoriesPageObjects.clickOnCategories();
//		objMenCategoriesPageObjects.clickOnMen();
//		objMenCategoriesPageObjects.clickOnMenTopwear();
//		objMenCategoriesPageObjects.clickOnMenTshirt();
		objiOSGenericMethods.clickOkButton();
//		objMenCategoriesPageObjects.clickOnTshirtFirstBanner();
		objHomePageObjects.clickOnHomeButton();
		objHomePageObjects.clickOnSearchButton();
		String search = objiOSGenericMethods.getValueByKey(TestName, "Search");
		objHomePageObjects.enterSearchitem(search);
		objiOSGenericMethods.clickOkButton();
	}

	@Test(priority = 3)
	public void selectProductPLPage() throws InterruptedException {

		// objPLPageObjets.clickOnOkAndTouch();
		objAssertionPageObject.verifyPLPHeader();
		objAssertionPageObject.verifyPLPProductCount();
		objPLPageObjets.clickOnFirstproductofPLP();

	}

	@Test(priority = 4)
	public void checkForBestPriceAndaddProductToBag() throws InterruptedException {

		objPDPageObject.clickOnSaveButton();
		objPDPageObject.clickOnAddToBag();
		objAssertionPageObject.verifySelectSize();
		objPDPageObject.getSizeListinString(1);
		objPDPageObject.clickonDoneButton();
		objiOSGenericMethods.swipeDown(100, 4);
		objPDPageObject.clickOnBestPrice();

	}

	@Test(priority = 5)
	public void checkForSizechartOnPDPageAndGoToBag() throws InterruptedException {

		objPDPageObject.clickOnGoToBag();

	}

	@Test(priority = 6)
	public void placeOrder() throws InterruptedException {

		objAssertionPageObject.verifyMyBag();
		objAssertionPageObject.verifyProductTitleCartPage();
		objiOSGenericMethods.swipeDown(100, 6);
		objAssertionPageObject.verifyPriceDetails();
		objCartPageObject.clickOnplaceOrder();

	}

	@Test(priority = 7)
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

	@Test(priority = 8)
	public void placOrder() throws InterruptedException, InvalidFileFormatException, IOException {
		objAssertionPageObject.verifypaymenttext();
		objPaymentPageObjects.clickOnCOD();


	}

	@Test(priority = 9)
	public void payment() throws InterruptedException {
		objAssertionPageObject.verifypaymenttext();
		objPaymentPageObjects.clickOnNetBanking();
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
		// Make sure that Page object object creation should be after this line
		System.out.println("Test Name " + TestName);
		objPLPageObjets = new PLPageObjects(wd);
		objPDPageObject = new PDPageObject(wd);
		objHomePageObjects = new HomePageObject2(wd);
		objAddNewAdressPageObjects = new AddNewAdressPageObjects(wd);
		objProfileLoginPageObject = new ProfileLoginPageObject(wd);
		objAssertionPageObject = new AssertionPageObject(wd);
		objPaymentPageObjects = new PaymentPageObject(wd);
		objCartPageObject = new CartPageObject(wd);
		objMenCategoriesPageObjects = new MenCategoriesPageObjects(wd);
		objiOSGenericMethods = new iOSGenericMethods(wd);
		 objLoginPageObject = new ProfileLoginPageObject(wd);
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