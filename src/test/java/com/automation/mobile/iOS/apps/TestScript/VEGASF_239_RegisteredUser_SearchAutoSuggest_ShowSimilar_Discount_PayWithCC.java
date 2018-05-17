package com.automation.mobile.iOS.apps.TestScript;

import org.testng.annotations.Test;
import org.testng.annotations.Test;
import java.io.IOException;
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
import com.automation.mobile.iOS.apps.ObjectRepository.Pages.AddressPage.AddNewAdressPageObjects;
import com.automation.mobile.iOS.apps.ObjectRepository.Pages.Assertions.AssertionPageObject;
import com.automation.mobile.iOS.apps.ObjectRepository.Pages.CartPage.CartPageObject;
import com.automation.mobile.iOS.apps.ObjectRepository.Pages.HomePage2.HomePageObject2;
import com.automation.mobile.iOS.apps.ObjectRepository.Pages.PDPage.PDPageObject;
import com.automation.mobile.iOS.apps.ObjectRepository.Pages.PLPage.PLPageObjects;
import com.automation.mobile.iOS.apps.ObjectRepository.Pages.PaymentPage.PaymentPageObject;
import com.automation.mobile.iOS.apps.ObjectRepository.Pages.ProfileLoginPage.ProfileLoginPageObject;
import com.automation.mobile.iOS.apps.ObjectRepository.Pages.ProfileSignUpPage.ProfileSignUpPageObject;
import com.automation.mobile.iOS.apps.ObjectRepository.Pages.WishListPage.WishlistPageObject;

import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;

public class VEGASF_239_RegisteredUser_SearchAutoSuggest_ShowSimilar_Discount_PayWithCC extends BaseIOSTest{

	GlobalVariables objGlobalVariables;
	AppiumServer objAppiumServer;
	ProfileSignUpPageObject objProfileSignUpPageObject;
	PLPageObjects objPLPageObjets;
	PDPageObject objPDPageObject;
	AddNewAdressPageObjects objAddAddressPageObjects;
	HomePageObject2 objHomePageObject2;
	MobileDrivers objMobileDrivers;
	//IOSDriver<IOSElement> wd;
	ProfileLoginPageObject objProfileLoginPageObject;
	AssertionPageObject objAssertionPageObject;
	PaymentPageObject objPaymentPageObject;
	WishlistPageObject objWishlistPageObject;
	iOSGenericMethods objiOSGenericMethods;
	CartPageObject objCartPageObject;

	String TestName = "VEGASF_239";

	/**
	 * 
	 * Lata
	 * 
	 * Steps to do Automation
	 * 
	 * App(Android, IOS, PWA, Web App) Email registered User Home Page Search -
	 * Using Autosuggest (use any query from Golden Set) Show More Products Similar
	 * Products Select Size Place Order Conditional Discount Add New address - Home
	 * Net Banking
	 * 
	 * 
	 */

	@Test(priority = 1)
	public void Login() throws InterruptedException, InvalidFileFormatException, IOException {
		try {
			objProfileLoginPageObject.clickOnOnBoardingCrossButton();
			System.out.println("On Boarding screen appeared and closed it");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("On Boarding screen did not appear");
		}
		objProfileLoginPageObject.clickOnProfileButton();
		objiOSGenericMethods.swipeDown(100, 8);
		objProfileLoginPageObject.clickOnLogOut();
		objProfileLoginPageObject.clickOnLogin();
		String email = iOSGenericMethods.getValueByKey(TestName, "UserName");
		String password = iOSGenericMethods.getValueByKey(TestName, "Password");
		objProfileLoginPageObject.loginInApp(email, password);
		objProfileLoginPageObject.clickOnLoginButton();
		objAssertionPageObject.verifyUserName();
//		objiOSGenericMethods.acceptAlert();
		objiOSGenericMethods.swipeDown(100, 6);
//		objiOSGenericMethods.acceptAlert();
		objProfileLoginPageObject.removeAddress();
	}

	@Test(priority = 2)
	public void SearchItem() throws InterruptedException, InvalidFileFormatException, IOException {
		objHomePageObject2.emptyBag();
		objHomePageObject2.clickOnHomeButton();
		objHomePageObject2.clickOnSearchButton();
		objAssertionPageObject.VerifyAutoSuggestionList();
		objHomePageObject2.setSearchBox(iOSGenericMethods.getValueByKey(TestName, "Search"));
//		objAssertionPageObject.verifyProductname();
//		objAssertionPageObject.verifyPLPHeader();
//		objAssertionPageObject.verifyPLPProductCount();
//		objPLPageObjets.clickOnFirstproductofPLP();
	}

//	@Test(priority = 3)
//	public void CheckForShowMoreProducts() throws InterruptedException {
////		objiOSGenericMethods.swipeDown(0, 16);
//		objPDPageObject.clickOnViewSimilar();
//		objAssertionPageObject.verifyProductname();
//		objAssertionPageObject.verifyPLPHeader();
//		objPLPageObjets.clickOnFirstproductofPLP();

//	}

	@Test(priority = 3)
	public void MoveToBag() throws InterruptedException {
		objPDPageObject.clickOnAddToBag();
		objPDPageObject.getSizeListinString(1);
		objPDPageObject.clickonDoneButton();
		objPDPageObject.clickOnGoToBag();
		objAssertionPageObject.verifyProductTitleCartPage();
		objiOSGenericMethods.swipeDown(100, 3);

	}

	@Test(priority = 4)
	public void ConditionalDiscount() throws InterruptedException, InvalidFileFormatException, IOException {
		objCartPageObject.ClickOnApplyCoupon();
		objAssertionPageObject.verifyApplyCouponHeaders();
		String coupon = objiOSGenericMethods.getValueByKey(TestName, "Coupon");
		//objCartPageObject.enterCoupon(coupon);
		objCartPageObject.clickOnApplyButton();
		objiOSGenericMethods.swipeDown(100, 5);
		objAssertionPageObject.verifyPriceDetails();
		objCartPageObject.clickOnplaceOrder();
	}

	@Test(priority = 5)
	public void AddNewAddress_Home() throws InterruptedException, InvalidFileFormatException, IOException {
		objAssertionPageObject.verifyAddressHeaders();
		objAddAddressPageObjects.clickOnAddNewAddress();
		String pincode = iOSGenericMethods.getValueByKey(TestName, "Pincode");
		String locality = iOSGenericMethods.getValueByKey(TestName, "Locality");
		String name = iOSGenericMethods.getValueByKey(TestName, "Name");
		String address = iOSGenericMethods.getValueByKey(TestName, "Address");
		String mobile = iOSGenericMethods.getValueByKey(TestName, "Mobile");
		objAddAddressPageObjects.EnterContinueOrderAddingAddress(pincode, locality, name, address, mobile);
		objiOSGenericMethods.swipeDown(100, 1);
		objCartPageObject.clickOnContinueOrder();

	}

	@Test(priority = 6)
	public void PaymentWithNetBanking() throws InterruptedException {

		objiOSGenericMethods.swipeDown(100, 3);
		objAssertionPageObject.verifyDeliverTo();
		objiOSGenericMethods.SwipeUp(0, 3);
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
		wd.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		System.out.println("Test Name " + TestName);
		objPLPageObjets = new PLPageObjects(wd);
		objPDPageObject = new PDPageObject(wd);
		objWishlistPageObject = new WishlistPageObject(wd);
		objPLPageObjets = new PLPageObjects(wd);
		objProfileLoginPageObject = new ProfileLoginPageObject(wd);
		objCartPageObject = new CartPageObject(wd);
		objAssertionPageObject = new AssertionPageObject(wd);
		objPaymentPageObject = new PaymentPageObject(wd);
		objHomePageObject2 = new HomePageObject2(wd);
		objiOSGenericMethods = new iOSGenericMethods(wd);
		objAddAddressPageObjects = new AddNewAdressPageObjects(wd);
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