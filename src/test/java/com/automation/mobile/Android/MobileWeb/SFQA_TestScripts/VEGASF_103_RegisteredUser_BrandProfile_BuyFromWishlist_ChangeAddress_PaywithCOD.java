package com.automation.mobile.Android.MobileWeb.SFQA_TestScripts;

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
import com.automation.mobile.Android.MobileWeb.ObjectRepository.AddressPageObjects.AddressPageObjects;
import com.automation.mobile.Android.MobileWeb.ObjectRepository.AddressPageObjects.Edit_ChangeButtonPageObjects;
import com.automation.mobile.Android.MobileWeb.ObjectRepository.Bag.BagPageObjects;
import com.automation.mobile.Android.MobileWeb.ObjectRepository.CategoriesObjects.HomeAndLivingCategoriesPageObjects;
import com.automation.mobile.Android.MobileWeb.ObjectRepository.CategoriesObjects.KidsCategoriesPageObjects;
import com.automation.mobile.Android.MobileWeb.ObjectRepository.CategoriesObjects.MenCategoriesPageObjects;
import com.automation.mobile.Android.MobileWeb.ObjectRepository.CategoriesObjects.WomenCategoriesPageObjects;
import com.automation.mobile.Android.MobileWeb.ObjectRepository.HomeObjects.HamburgerPageObjects;
import com.automation.mobile.Android.MobileWeb.ObjectRepository.HomeObjects.HomePageObjects;
import com.automation.mobile.Android.MobileWeb.ObjectRepository.HomeObjects.MenuPageObjects;
import com.automation.mobile.Android.MobileWeb.ObjectRepository.PDPageObjects.PDPageObjects;
import com.automation.mobile.Android.MobileWeb.ObjectRepository.PLPageObjects.FilterPageObjects;
import com.automation.mobile.Android.MobileWeb.ObjectRepository.PLPageObjects.PLPageObjects;
import com.automation.mobile.Android.MobileWeb.ObjectRepository.PaymentObjects.PaymentPageObjects;
import com.automation.mobile.Android.MobileWeb.ObjectRepository.WishList.WishListPageObject;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

/**
 * @author 300019227 - Anu TEST STEPS 1. Login(Email registered user) 2. Reset
 *         Cart, Wishlist and Address Pages 3. Search (Brand Profile - Search
 *         for a brand like Nike, Roadster) 4. Navigate from PLP to PDP 5.
 *         Select a size and Add to Bag from PDP 6. Navigate to Bag page add
 *         move the product to wishlist 7. Move the product from Wishlist to Bag
 *         and place the order 8. Add New Address for Delivery 9. Payment
 *         through COD
 */
public class VEGASF_103_RegisteredUser_BrandProfile_BuyFromWishlist_ChangeAddress_PaywithCOD {

	GlobalVariables objGlobalVariables;
	AppiumServer objAppiumServer;
	AddressPageObjects objAddressPageObjects;
	Edit_ChangeButtonPageObjects objEdit_ChangeButtonPageObjects;
	BagPageObjects objBagPageObjects;
	HomeAndLivingCategoriesPageObjects objHomeAndLivingCategoriesPageObjects;
	KidsCategoriesPageObjects objKidsCategoriesPageObjects;
	MenCategoriesPageObjects objMenCategoriesPageObjects;
	WomenCategoriesPageObjects objWomenCategoriesPageObjects;
	MenuPageObjects objMenuPageObjects;
	PaymentPageObjects objPaymentPageObjects;
	FilterPageObjects objFilterPageObjects;
	MobileDrivers objMobileDrivers;
	PDPageObjects objPDPageObject;
	HamburgerPageObjects objHambergerPageObjects;
	HomePageObjects objHomePageObjects;
	PLPageObjects objPLPageObjects;
	WishListPageObject objWishlistPageObject;
	AndroidDriver<AndroidElement> aDriver;
	AndroidGenericMethods objAndroidGenericMethods;

	String testName = "VEGASF_103";

	@Parameters({ "browserName_", "deviceName_", "UDID_", "platformVersion_", "URL_", "appUrl_", "screenshotPath_" })

	@BeforeTest
	public void beforeTest(String browserName_, String deviceName_, String UDID_, String platformVersion_, String URL_,
			String appUrl_, String screenshotPath_) throws MalformedURLException {

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
		aDriver = objMobileDrivers.launchAppAndroid(params);
		objAndroidGenericMethods = new AndroidGenericMethods(aDriver);
		aDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		objAddressPageObjects = new AddressPageObjects(aDriver);
		objEdit_ChangeButtonPageObjects = new Edit_ChangeButtonPageObjects(aDriver);
		objBagPageObjects = new BagPageObjects(aDriver);
		objHomeAndLivingCategoriesPageObjects = new HomeAndLivingCategoriesPageObjects(aDriver);
		objKidsCategoriesPageObjects = new KidsCategoriesPageObjects(aDriver);
		objMenCategoriesPageObjects = new MenCategoriesPageObjects(aDriver);
		objWomenCategoriesPageObjects = new WomenCategoriesPageObjects(aDriver);
		objMenuPageObjects = new MenuPageObjects(aDriver);
		objPaymentPageObjects = new PaymentPageObjects(aDriver);
		objFilterPageObjects = new FilterPageObjects(aDriver);
		objPDPageObject = new PDPageObjects(aDriver);
		objHambergerPageObjects = new HamburgerPageObjects(aDriver);
		objHomePageObjects = new HomePageObjects(aDriver);
		objPLPageObjects = new PLPageObjects(aDriver);
		objWishlistPageObject = new WishListPageObject(aDriver);
		objAndroidGenericMethods = new AndroidGenericMethods(aDriver);
	}

	@Test(priority = 1)
	public void LoginWithEmail() throws InvalidFileFormatException, IOException {
		System.out.println("=====================VEGASF_103_START=====================");
		Reporter.log("LoginWithEmail test started");
		objHomePageObjects.clickOnHamburgerButton();
		objHambergerPageObjects.clickOnSignInOption();
		objHambergerPageObjects.enterEmailAddress(objAndroidGenericMethods.getValueByKeyWeb(testName, "UserName"),
				objAndroidGenericMethods.getValueByKeyWeb(testName, "Password"));
		objAndroidGenericMethods.scrollDown(objHambergerPageObjects.getSignInButton(), 10);

	}

	@Test(priority = 2)
	public void resetData() throws InterruptedException {
		Reporter.log("ResetData test started");
		objBagPageObjects.resetBag();
		objWishlistPageObject.resetWishlist();
		objAddressPageObjects.resetAddress();
	}

	@Test(priority = 3)
	public void SearchItem() throws InvalidFileFormatException, IOException {
		Reporter.log("SearchItem test started");
		objHomePageObjects.clickOnSearchIcon();
		objHomePageObjects.enterSearchItem(objAndroidGenericMethods.getValueByKeyWeb(testName, "productcode"));
	}

	@Test(priority = 4)
	public void NavigateFromPLPtoPDP() throws InterruptedException {
		Reporter.log("NavigateFromPLPtoPDP test Started");
//		objPDPageObject.clickFirstProductSearchResult();
		objPDPageObject.VerifyProductTitle();
		objPDPageObject.imageVerification();
		objPDPageObject.assertProductPrice();
	}

	@Test(priority = 5)
	public void AddToBag() throws InterruptedException {
		Reporter.log("AddToBag test Started");
		objPDPageObject.clickOnAddtoBag();
		objPDPageObject.setFirstAvailableSize();
		objPDPageObject.clickOnConfirmButton();
		objPDPageObject.clickOngoToBag();
		objAndroidGenericMethods.scrollDown(objBagPageObjects.MoveToWishlist, 20);
		objAndroidGenericMethods.backKeyButton();
	}

	@Test(priority = 6)
	public void MoveToWishlist() throws InterruptedException {
		Reporter.log("MoveToWishlist test Started");
		objHomePageObjects.clickOnWishlistButton();
		objWishlistPageObject.VerifyWishlistPageTitle();
		objWishlistPageObject.VerifySellingPrice();	
		objWishlistPageObject.VerfiyProductIsAddedToWishlist();
		objWishlistPageObject.ClickOnMoveToBag();
		objWishlistPageObject.ClickOnDoneButton();
	}

	@Test(priority = 7)
	public void MoveToBag() throws InterruptedException {
		Reporter.log("MoveToBag test Started");
		objWishlistPageObject.ClickOnCartIconFromWishlist();
		objBagPageObjects.assertBagPageTitle("Bag");
		objBagPageObjects.VerifyProductTitle();
		objBagPageObjects.getProductImage();
		objBagPageObjects.VerfiyProductIsAddedToCart();
		objBagPageObjects.VerifySellingPrice();
	}

	@Test(priority = 8)
	public void CheckForOffers() throws InterruptedException {
		Reporter.log("CheckForOffers test Started");
		objBagPageObjects.clickOnPlaceOrder();
	}

	@Test(priority = 9)
	public void EditAddress() throws InvalidFileFormatException, IOException {
		objAddressPageObjects.ClickOnPincodeAddress();
		objAddressPageObjects.enterPincone(objAndroidGenericMethods.getValueByKeyWeb(testName, "Pincode"));
		objAddressPageObjects.clickOnChoose();
		objAddressPageObjects.selectTownLocality(objAndroidGenericMethods.getValueByKeyWeb(testName, "Locality"));
		objAddressPageObjects.entername(objAndroidGenericMethods.getValueByKeyWeb(testName, "Name"));
		objAndroidGenericMethods.scrollDown(objAddressPageObjects.Address, 20);
		objAddressPageObjects.enteraddress(objAndroidGenericMethods.getValueByKeyWeb(testName, "Address"));
		objAndroidGenericMethods.scrollDown(objAddressPageObjects.getMobileNumber(), 20);
		objAddressPageObjects.enterMobileNumber(objAndroidGenericMethods.getValueByKeyWeb(testName, "MobileNumber"));
		objAndroidGenericMethods.scrollDown(objAddressPageObjects.getOfficeCommercial(), 20);
		objAndroidGenericMethods.scrollDown(objAddressPageObjects.getopenOnSaturdays(), 20);
		objAndroidGenericMethods.scrollDown(objAddressPageObjects.getSaveAddress(), 20);
		objAndroidGenericMethods.scrollDown(objEdit_ChangeButtonPageObjects.getEditChangeButton(), -20);
		objEdit_ChangeButtonPageObjects.clickOnEDITButton();
		objAddressPageObjects.enterEditName(objAndroidGenericMethods.getValueByKeyWeb(testName, "Name2"));
		objAndroidGenericMethods.scrollDown(objAddressPageObjects.getSaveAddress(), 50);
		objAddressPageObjects.VerifyAddressAdded();
	}

	@Test(priority = 10)
	public void PaymentWithMyntraCredit_COD() throws InterruptedException {
		Reporter.log(" PaymentWithMyntraCredit_COD test started");
		objBagPageObjects.clickOnContinueButton();
		objBagPageObjects.assertPaymentPageTitle("payment");
		objPaymentPageObjects.VerifyDeliveryAddress();
		objPaymentPageObjects.ClickOnCOD();

	}
	@Test(priority = 11)
	public void LogOut() throws InterruptedException  {
		Reporter.log("Logout");
		objAndroidGenericMethods.scrollDown(objMenuPageObjects.myntraLogoFromPaymentpage, -100);
		objHambergerPageObjects.logoutAndVerifySessionId();
	}

	@AfterTest
	public void afterTest() {
		System.out.println("=====================VEGASF_103_END=====================");
		aDriver.quit();
	}

}
