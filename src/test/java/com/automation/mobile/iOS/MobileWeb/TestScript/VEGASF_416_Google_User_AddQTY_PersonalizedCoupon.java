package com.automation.mobile.iOS.MobileWeb.TestScript;

import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.ini4j.InvalidFileFormatException;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import com.automation.core.Common.AppiumServer;
import com.automation.core.Common.GlobalVariables;
import com.automation.core.Common.MobileDrivers;
import com.automation.core.mobile.iOS.iOSGenericMethods;
import com.automation.mobile.iOS.MobileWeb.ObjectRepository.WishList.*;
import com.automation.mobile.iOS.MobileWeb.ObjectRepository.AddressPageObjects.AddressPageObjects;
import com.automation.mobile.iOS.MobileWeb.ObjectRepository.AddressPageObjects.Edit_ChangeButtonPageObjects;
import com.automation.mobile.iOS.MobileWeb.ObjectRepository.Bag.BagPageObjects;
import com.automation.mobile.iOS.MobileWeb.ObjectRepository.HomeObjects.HamburgerPageObjects;
import com.automation.mobile.iOS.MobileWeb.ObjectRepository.HomeObjects.HomePageObjects;
import com.automation.mobile.iOS.MobileWeb.ObjectRepository.HomeObjects.MenuPageObjects;
import com.automation.mobile.iOS.MobileWeb.ObjectRepository.PDPageObjects.PDPageObjects;
import com.automation.mobile.iOS.MobileWeb.ObjectRepository.PLPageObjects.FilterPageObjects;
import com.automation.mobile.iOS.MobileWeb.ObjectRepository.PLPageObjects.PLPageObjects;
import com.automation.mobile.iOS.MobileWeb.ObjectRepository.PaymentObjects.PaymentPageObjects;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;

/**
 * @author 300022495 - Shivaprasad
 *
 *         Steps to do Automation "LoginWithGoogle SearchItem AddToBag
 *         SelectSizeLink SelectSize_Qty ApplyGenericCoupons EditAddress
 *         PaymentWithWallet"
 */

public class VEGASF_416_Google_User_AddQTY_PersonalizedCoupon {
	GlobalVariables objGlobalVariables;
	AppiumServer objAppiumServer;
	AddressPageObjects objAddressPageObjects;
	Edit_ChangeButtonPageObjects objEdit_ChangeButtonPageObjects;
	BagPageObjects objBagPageObjects;
	MenuPageObjects objMenuPageObjects;
	PaymentPageObjects objPaymentPageObjects;
	FilterPageObjects objFilterPageObjects;
	MobileDrivers objMobileDrivers;
	PDPageObjects objPDPageObject;
	PLPageObjects objPLPageObjects;
	HamburgerPageObjects objHamburgerPageObjects;
	HomePageObjects objHomePageObjects;
	IOSDriver<IOSElement> iDriver;
	iOSGenericMethods objiOSGenericMethods;
	WishListPageObject objWishlistPageObject;

	String testName = "VEGASF_416";

	// @Test(priority = 1)
	public void LoginWithEmail() throws InterruptedException, InvalidFileFormatException, IOException {
		objHomePageObjects.clickOnHamburgerButton();
		objHamburgerPageObjects.clickOnSignInOption();
		objHamburgerPageObjects.clickOnGoogle();
//		objiOSGenericMethods.switchToNewWindow();
		objHamburgerPageObjects.enterGoogleEmailField("subhasistest04@gmail.com", "munuasis44");
		objHomePageObjects.clickOnHamburgerButton();
		objHamburgerPageObjects.clickOnMyAccount();
		objHamburgerPageObjects.assertUserEmailID(objiOSGenericMethods.getValueByKeyiOSWeb(testName, "UserName"));
		iDriver.navigate().back();
	}

	@Test(priority = 1)
	public void LoginWithGoogle() throws InterruptedException, InvalidFileFormatException, IOException {
		Reporter.log("LoginWithGoogle test case started successfully");
		System.out.println("=====================VEGASF_416_START=====================");
		System.out.println("LogiLoginWithGooglenWithFacebook");
		objHomePageObjects.clickOnHamburgerButton();
		if (objHamburgerPageObjects.isLoggedIn()) {
			objHamburgerPageObjects.clickOnMyAccount();
			objHamburgerPageObjects.clickOnLogout();
			objHomePageObjects.clickOnHamburgerButton();
		}
		objHamburgerPageObjects.clickOnSignInOption();
		objHamburgerPageObjects.enterEmailAddress(objiOSGenericMethods.getValueByKeyiOSWeb(testName, "UserName"),
				objiOSGenericMethods.getValueByKeyiOSWeb(testName, "Password"));
		objHamburgerPageObjects.clickOnSignInButton();
		objHomePageObjects.clickOnHamburgerButton();
		objHamburgerPageObjects.clickOnMyAccount();
		objHamburgerPageObjects.assertUserEmailID(objiOSGenericMethods.getValueByKeyiOSWeb(testName, "UserName"));
		iDriver.navigate().back();
	}

	@Test(priority = 2)
	public void resetData() throws InterruptedException {
		Reporter.log("resetData test case started successfully");
		System.out.println("Reset Bag, Wishlist & Address!");
		objBagPageObjects.resetBag();
		objWishlistPageObject.resetWishlist();
		objAddressPageObjects.resetAddress();
	}

	@Test(priority = 3)
	public void SearchItem() throws InterruptedException, InvalidFileFormatException, IOException {
		Reporter.log("SearchItem test case started successfully");
		System.out.println("SearchItem!");
		objHomePageObjects.clickOnSearchIcon();
		objHomePageObjects.enterSearchItem(objiOSGenericMethods.getValueByKeyiOSWeb(testName, "SearchItem"));
		objPLPageObjects.verifySearchResult("Result Search Title");
		objHomePageObjects.getSearchAutoSuggestList();
		objPLPageObjects.VerifyProductDetails();
	}

	@Test(priority = 4)
	public void AddToBag() throws InterruptedException {
		Reporter.log("AddToBag test case started successfully");
		System.out.println("AddToBag!");
		objPDPageObject.clickFirstProductSearchResult();
		objPDPageObject.VerifyProductTitle();
		objPDPageObject.assertProductDiscount();
		objPDPageObject.assertProductPrice();
		objPDPageObject.clickOnSaveButton();
		objPDPageObject.clickOnAddtoBag();
	}

	@Test(priority = 5)
	public void SelectSizeLink() throws InterruptedException {
		Reporter.log("SelectSizeLink test case started successfully");
		System.out.println("SelectSizeLink!");
		objPDPageObject.clickOnSizeChart();
		objPDPageObject.clickOnCloseSizeChartButton();
		objPDPageObject.selectSizeOfProduct();
		objPDPageObject.clickOnConfirmButton();
		objPDPageObject.clickOngoToBag();
	}

	@Test(priority = 6)
	public void SelectSize_Qty() throws InterruptedException {
		Reporter.log("SelectSize_Qty test case started successfully");
		System.out.println("SelectSize_Qty!");
		// objBagPageObjects.ChangeQuantity();
		objBagPageObjects.ChangeSize();
		objBagPageObjects.assertBagPageTitle("bag");
	}

	// @Test(priority = 7)
	// public void ApplyCoupon() throws InterruptedException {
	// objiOSGenericMethods.swipeDown(100, 1);
	// objBagPageObjects.clickOnApplyCouponButton();
	// objBagPageObjects.SelectCoupon();
	// objBagPageObjects.clickOnCancelButton();
	// }
	
	public void MoveToBagfromWishlist() throws InterruptedException {
		objHomePageObjects.clickOnWishlistButton();
		objWishlistPageObject.VerifyWishlistPageTitle();
		objWishlistPageObject.VerifyProductTitle();
		objWishlistPageObject.VerifyDiscountedPrice();
		objWishlistPageObject.VerifySellingPrice();
		objWishlistPageObject.ClickOnMoveToBag();
		objWishlistPageObject.ClickSizeButtons();
		objWishlistPageObject.ClickOnDoneButton();
		objWishlistPageObject.clickOnCartFromWishlist();
	}

	@Test(priority = 7)
	public void GoToCart() throws InterruptedException {
		objBagPageObjects.VerifyProductTitle();
		objBagPageObjects.getProductImage();
		objBagPageObjects.VerifySellingPrice();
		objBagPageObjects.clickOnViewDetails();
	}

//	@Test(priority = 8)
//	public void ApplyCoupon() throws InterruptedException {
//		objiOSGenericMethods.swipeDown(100, 1);
//		objBagPageObjects.clickOnApplyCouponButton();
//		objBagPageObjects.SelectCoupon();
//		objBagPageObjects.clickOnCancelButton();
//	}

	@Test(priority = 8)
	public void PlaceOrder() {
		Reporter.log("PlaceOrder test case started successfully");
		System.out.println("PlaceOrder!");
		objBagPageObjects.clickOnPlaceOrder();
	}

	@Test(priority = 9)
	public void EditAddress() throws InterruptedException, InvalidFileFormatException, IOException {
		Reporter.log("EditAddress test case started successfully");
		System.out.println("EditAddress!");
		objAddressPageObjects.ClickOnPincodeAddress();
		objAddressPageObjects.enterPincone(objiOSGenericMethods.getValueByKeyiOSWeb(testName, "Pincode"));
		objAddressPageObjects.clickOnChoose();
		objAddressPageObjects.selectTownLocality(objiOSGenericMethods.getValueByKeyiOSWeb(testName, "Locality"));
		objAddressPageObjects.entername(objiOSGenericMethods.getValueByKeyiOSWeb(testName, "Name"));
		objAddressPageObjects.enteraddress(objiOSGenericMethods.getValueByKeyiOSWeb(testName, "Address"));
		objAddressPageObjects.enterMobileNumber(objiOSGenericMethods.getValueByKeyiOSWeb(testName, "MobileNumber"));
		objAddressPageObjects.clickOnOfficeCommercial();
		objAddressPageObjects.clickOnOpenOnSaturdays();
		objAddressPageObjects.clickOnSaveAddress();
		objAddressPageObjects.VerifyAddressAdded();
		objEdit_ChangeButtonPageObjects.clickOnEditChangeButton();
		objEdit_ChangeButtonPageObjects.clickOnEDITButton();
		objAddressPageObjects.enterEditName(objiOSGenericMethods.getValueByKeyiOSWeb(testName, "EditName"));
		objAddressPageObjects.clickOnSaveAddress();
	}

	@Test(priority = 10)
	public void PaymentWithCOD() throws InterruptedException, InvalidFileFormatException, IOException {
		Reporter.log("PaymentWithCOD test case started successfully");
		System.out.println("Payment With COD!");
		objBagPageObjects.clickOnDemoId();
		objBagPageObjects.clickOnContinueButton();
		objBagPageObjects.clickOnDemoId();
		objBagPageObjects.assertPaymentPageTitle("Payment");
		objPaymentPageObjects.VerifyDeliveryAddress();
		objPaymentPageObjects.ClickOnCOD();
	}

	@Parameters({ "browserName_", "deviceName_", "UDID_", "platformVersion_", "URL_", "appUrl_", "screenshotPath_" })
	@BeforeTest
	public void beforeTest(String browserName_, String deviceName_, String UDID_, String platformVersion_, String URL_,
			String appUrl_, String screenshotPath_) throws InterruptedException {
		objGlobalVariables = new GlobalVariables();
		objAppiumServer = new AppiumServer();
		objMobileDrivers = new MobileDrivers();
		Map<String, String> params = new HashMap<String, String>();
		params.put("browserName_", browserName_);
		params.put("deviceName_", deviceName_);
		params.put("UDID_", UDID_);
		params.put("platformVersion_", platformVersion_);
		params.put("URL_", URL_);
		params.put("appUrl_", appUrl_);
		params.put("screenshotPath_", screenshotPath_);
		iDriver = objMobileDrivers.launchAppiOS(params);
		objiOSGenericMethods = new iOSGenericMethods(iDriver);
		iDriver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		objAddressPageObjects = new AddressPageObjects(iDriver);
		objEdit_ChangeButtonPageObjects = new Edit_ChangeButtonPageObjects(iDriver);
		objBagPageObjects = new BagPageObjects(iDriver);
		objMenuPageObjects = new MenuPageObjects(iDriver);
		objPaymentPageObjects = new PaymentPageObjects(iDriver);
		objFilterPageObjects = new FilterPageObjects(iDriver);
		objPDPageObject = new PDPageObjects(iDriver);
		objPLPageObjects = new PLPageObjects(iDriver);
		objHamburgerPageObjects = new HamburgerPageObjects(iDriver);
		objHomePageObjects = new HomePageObjects(iDriver);
		objWishlistPageObject = new WishListPageObject(iDriver);
	}

	@AfterTest
	public void afterTest() {
		// quite browser object
		// check condition before closing or quite
		// iDriver.quit();
		// System.out.println("=====================VEGASF_416_END=====================");
	}
}
