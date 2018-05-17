package com.automation.mobile.iOS.apps.TestScript;

import org.testng.annotations.Test;
import org.testng.annotations.Test;
import org.testng.annotations.Test;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.codehaus.plexus.component.configurator.converters.composite.ObjectWithFieldsConverter;
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

import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;

/*
 * Steps to do Automation

App(Android, IOS, PWA, Web App)
Email registered User
Home Page
Search (by clicking banner or nested banner on Home Page)
Filter
Save
Move to bag
Apply Coupon
Buy One get One
Edit address
GIft card
 */

/**
 * Stabilize by @author 300019221 Aravindanath
 * 
 * @author Monica
 *
 */
public class VEGASF_242_RegisteredUser_BOGO_PayWithGCAndCC extends BaseIOSTest{

	GlobalVariables objGlobalVariables;
	AppiumServer objAppiumServer;
	ProfileSignUpPageObject objProfileSignUpPageObject;
	PLPageObjects objPLPageObjets;
	PDPageObject objPDPageObject;
	AddNewAdressPageObjects objAddNewAdressPageObjects;
	HomePageObject2 objHomePageObject2;
	EditAdressPageObject objEditAdressPageObject;
	MobileDrivers objMobileDrivers;
	//IOSDriver<IOSElement> iDriver;
	ProfileLoginPageObject objProfileLoginPageObject;
	AssertionPageObject objAssertionPageObject;
	PaymentPageObject objPaymentPageObject;
	WishlistPageObject objWishlistPageObject;
	CartPageObject objCartPageObject;
	iOSGenericMethods objiOSGenericMethods;

	String TestName = "VEGASF_242";

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
		objiOSGenericMethods.swipeDown(0, 8);
		objProfileLoginPageObject.clickOnLogOut();
		objProfileLoginPageObject.clickOnLogin();
		objProfileLoginPageObject.loginInApp(iOSGenericMethods.getValueByKey(TestName, "UserName"),
				iOSGenericMethods.getValueByKey(TestName, "Password"));
		objProfileLoginPageObject.clickOnLoginButton();
		objAssertionPageObject.verifyUserName();
		objiOSGenericMethods.swipeDown(100, 6);
		objProfileLoginPageObject.removeAddress();
	}

	@Test(priority = 2)
	public void SearchItem() throws InterruptedException, InvalidFileFormatException, IOException {
		objHomePageObject2.emptyBag();
		objHomePageObject2.clickOnHomeButton();
		// objHomePageObject2.clickOnBigBanner(15);
		// objHomePageObject2.clickOnNestedBanner(20);
		// objPLPageObjets.clickOnOkAndTouch();
		objHomePageObject2.clickOnSearchButton();
		objAssertionPageObject.VerifyAutoSuggestionList();
		String search = objiOSGenericMethods.getValueByKey(TestName, "Search");
		objHomePageObject2.enterSearchitem(search);
		objAssertionPageObject.verifyProductname();
		objAssertionPageObject.verifyPLPHeader();
		objAssertionPageObject.verifyPLPProductCount();

	}

	@Test(priority = 3)
	public void Filter() throws InterruptedException, InvalidFileFormatException, IOException {

		objPLPageObjets.clickOnFilter();
		objAssertionPageObject.verifyDiscount();
		objPLPageObjets.clickOnFilterDiscount();
		objPLPageObjets.clickOnSelectFirstFilterDiscount();
		objPLPageObjets.clickOnApplyDiscount();
	}

	@Test(priority = 4)
	public void Save() throws InterruptedException, InvalidFileFormatException, IOException {
		objPLPageObjets.clickOnFirstproductofPLP();
		objPDPageObject.clickOnSaveButton();
	}

	@Test(priority = 5)
	public void AddToBag() throws InterruptedException {
		objPDPageObject.clickOnAddToBag();
		// objPDPageObject.selectSizeIfPresent();
		objPDPageObject.getSizeListinString(1);
		objAssertionPageObject.verifySelectSize();
		objPDPageObject.clickonDoneButton();
		objPDPageObject.clickOnGoToBag();
		objiOSGenericMethods.swipeDown(100, 6);
	

	}

	@Test(priority = 6)
	public void ApplyCoupons() throws InterruptedException, InvalidFileFormatException, IOException {
		objAssertionPageObject.verifyMyBag();
//		objAssertionPageObject.veirfyCartPageWishlist();
		objAssertionPageObject.verifyProductTitleCartPage();
		objiOSGenericMethods.swipeDown(100, 2);
		objCartPageObject.ClickOnApplyCoupon();
		objAssertionPageObject.verifyApplyCouponHeaders();
//		String coupon = objiOSGenericMethods.getValueByKey(TestName, "Coupon");
//		objCartPage.enterCoupon(coupon);
		objCartPageObject.clickOnApplyButton();
		objiOSGenericMethods.swipeDown(100, 6);
		objAssertionPageObject.verifyPriceDetails();
		objCartPageObject.clickOnplaceOrder();
	
	}

	@Test(priority = 7)
	public void EditAddress() throws InterruptedException, InvalidFileFormatException, IOException {
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
	public void PaymentWithGiftCard() throws InterruptedException, InvalidFileFormatException, IOException {

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
		objPLPageObjets = new PLPageObjects(wd);
		objPDPageObject = new PDPageObject(wd);
		objWishlistPageObject = new WishlistPageObject(wd);
		objPLPageObjets = new PLPageObjects(wd);
		objProfileLoginPageObject = new ProfileLoginPageObject(wd);
		objCartPageObject = new CartPageObject(wd);
		objAssertionPageObject = new AssertionPageObject(wd);
		objEditAdressPageObject = new EditAdressPageObject(wd);
		objAddNewAdressPageObjects = new AddNewAdressPageObjects(wd);
		objPaymentPageObject = new PaymentPageObject(wd);
		objHomePageObject2 = new HomePageObject2(wd);
		objiOSGenericMethods = new iOSGenericMethods(wd);
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