package com.automation.mobile.iOS.apps.TestScript;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.ini4j.InvalidFileFormatException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.BaseIOSTest;
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
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;

/**
 * 
 * @author 300019221 / Aravindanath
 *
 */
/*
 * Casual Browsing User
 * 
 * Facebook registered user - Login Home Page List page to PDP navigation Filter
 * Products Verify ProductPrice Move to bag Add more from wishlist Apply
 * Personalized Coupons Remove address
 */
public class VEGASF_357_FB_User_Offers_Concious extends BaseIOSTest {
	GlobalVariables objGlobalVariables;
	iOSGenericMethods objiOSGenericMethods;
	PLPageObjects objPLPageObjets;
	PDPageObject objPDPageObject;
	AddNewAdressPageObjects objAddNewAdressPageObjects;
	AssertionPageObject objAssertionPageObject;
	HomePageObject2 objHomePageObjects;
	MobileDrivers objMobileDrivers;
	// IOSDriver<IOSElement> wd;
	ProfileLoginPageObject objLoginPageObject;
	WishlistPageObject objWishlistPageObject;
	CartPageObject objCartPage;
	PaymentPageObject objPaymentPageObjects;
	String TestName = "VEGASF_357";

	@Test(priority = 1)
	public void LoginApp() throws InterruptedException, InvalidFileFormatException, IOException {
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
		objiOSGenericMethods.acceptAlert();
		// objiOSGenericMethods.swipeDown(100, 6);
		// objLoginPageObject.removeAddress();
	}

	@Test(priority = 2, dependsOnMethods = { "LoginApp" })
	public void SearchItem() throws InterruptedException, InvalidFileFormatException, IOException {
		objHomePageObjects.emptyBag();
		objHomePageObjects.clickOnHomeButton();
		objHomePageObjects.clickOnSearchButton();// Impulse Unisex Orange Rucksack
		String search = objiOSGenericMethods.getValueByKey(TestName, "Search");
		objHomePageObjects.enterSearchitem(search);
	}

//	@Test(priority = 4, dependsOnMethods = { "SearchItem" })
//	public void productDiscriptionPage() throws InterruptedException, InvalidFileFormatException, IOException {
//		objPDPageObject.clickOnAddToBag();
//		objPDPageObject.getSizeListinString(0);
//		// objPDPageObject.getSizeListinString(1);
//		objPDPageObject.clickonDoneButton();
//		objPDPageObject.clickOnGoToBag();
//	}
//
//	@Test(priority = 5, dependsOnMethods = { "productDiscriptionPage" })
//	public void couponPage() throws InterruptedException, InvalidFileFormatException, IOException {
//		objAssertionPageObject.verifyMyBag();
//		objiOSGenericMethods.swipeDown(100, 3);
//		objCartPage.ClickOnApplyCoupon();
//		objAssertionPageObject.verifyApplyCouponHeaders();
//		// String coupon = objiOSGenericMethods.getValueByKey(TestName, "Coupon");
//		// objCartPage.enterCoupon(coupon);
//		objCartPage.clickOnApplyButton();
//	}
//
//	@Test(priority = 6, dependsOnMethods = { "couponPage" })
//	public void cartPage() throws InterruptedException {
//		objAssertionPageObject.verifyMyBag();
//		objiOSGenericMethods.swipeDown(100, 6);
//		objAssertionPageObject.verifyPriceDetails();
//		objCartPage.clickOnplaceOrder();
//	}
//
//	@Test(priority = 7, dependsOnMethods = { "cartPage" })
//	public void AddNewAddress_Home() throws InterruptedException, InvalidFileFormatException, IOException {
//		objAssertionPageObject.verifyAddressHeaders();
//		objAddNewAdressPageObjects.clickOnAddNewAddress();
//		String pincode = iOSGenericMethods.getValueByKey(TestName, "Pincode");
//		String locality = iOSGenericMethods.getValueByKey(TestName, "Locality");
//		String name = iOSGenericMethods.getValueByKey(TestName, "Name");
//		String address = iOSGenericMethods.getValueByKey(TestName, "Address");
//		String mobile = iOSGenericMethods.getValueByKey(TestName, "Mobile");
//		objAddNewAdressPageObjects.EnterContinueOrderAddingAddress(pincode, locality, name, address, mobile);
//		objiOSGenericMethods.swipeDown(100, 1);
//		objCartPage.clickOnContinueOrder();
//	}
//
//	@Test(priority = 8, dependsOnMethods = { "AddNewAddress_Home" })
//	public void payment() throws InterruptedException {
//		objAssertionPageObject.verifypaymenttext();
//		objPaymentPageObjects.clickOnCOD();
//	}

	@Parameters({ "deviceName_", "UDID_", "platformVersion_", "URL_", "appUrl_", "screenshotPath_", "engine_",
			"platform_" })
	@BeforeTest
	public void beforeTest(@Optional("TD") String deviceName_, @Optional("TD") String UDID_,
			@Optional("TD") String platformVersion_, @Optional("TD") String URL_, @Optional("TD") String appUrl_,
			@Optional("TD") String screenshotPath_, @Optional("TD") String engine_, @Optional("TD") String platform_)
			throws Exception {
		objGlobalVariables = new GlobalVariables();
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
		if (!(params.get("engine_").equalsIgnoreCase("TD"))) {
			wd = objMobileDrivers.launchAppiOS(params);
		} else {
			try {
				setUpTest();
				System.out.println("TestDroid Execution Started");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println("Error :: Please change suite parameter to run locally.");
			}
		}
		wd.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		System.out.println("Test Name " + TestName);
		// Make sure that Page object object creation should be after this line
		objPLPageObjets = new PLPageObjects(wd);
		objPDPageObject = new PDPageObject(wd);
		objHomePageObjects = new HomePageObject2(wd);
		objAddNewAdressPageObjects = new AddNewAdressPageObjects(wd);
		objLoginPageObject = new ProfileLoginPageObject(wd);
		objPaymentPageObjects = new PaymentPageObject(wd);
		objWishlistPageObject = new WishlistPageObject(wd);
		objCartPage = new CartPageObject(wd);
		objAssertionPageObject = new AssertionPageObject(wd);
		objiOSGenericMethods = new iOSGenericMethods(wd);
	}

	@Parameters({ "engine_" })
	@AfterTest
	public void quit(@Optional("TD") String engine_) {
		try {
			if ((engine_.equalsIgnoreCase("TD"))) {
				quitAppiumSession();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Locally test completed");
		}
		wd.quit();
	}
}
