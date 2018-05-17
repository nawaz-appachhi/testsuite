package com.automation.mobile.Android.MobileWeb.SFQA_TestScripts;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.ini4j.InvalidFileFormatException;
import org.testng.Reporter;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.BaseAndroidTest;
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

/**
 * @author 300021279 - Sangam TEST STEPS Casual Browsing User # Google
 *         registered user - Login # Home Page # Search - Autosuggest - Brand
 *         Profile (Search for a brand like Nike, Roadster) # Add to Bag # View
 *         Similar Products # Select Size from wishlist # Change Size & add
 *         Quantity in Cart Page # Apply Personalized Coupons # Edit address
 */
public class VEGASF_340_FB_User_ClickforOffer_AddNewAddress_Home_PayWithDC extends BaseAndroidTest {
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
	HamburgerPageObjects objHamburgerPageObjects;
	HomePageObjects objHomePageObjects;
	PLPageObjects objPLPageObjects;
	WishListPageObject objWishlistPageObject;
	AndroidGenericMethods objAndroidGenericMethods;
	String testName = "VEGASF_340";

	@Test(priority = 1)
	public void LoginWithGoogle() throws InvalidFileFormatException, IOException {
		System.out.println("=====================VEGASF_340_START=====================");
		Reporter.log("LoginInMyntra");
		objHomePageObjects.clickOnHamburgerButton();
		objHamburgerPageObjects.clickOnSignInOption();
		objHamburgerPageObjects.enterEmailAddress(objAndroidGenericMethods.getValueByKeyWeb(testName, "UserName"),
				objAndroidGenericMethods.getValueByKeyWeb(testName, "Password"));
		objAndroidGenericMethods.scrollDown(objHamburgerPageObjects.getSignInButton(), 10);
	}

	@Test(priority = 2)
	public void SearchItem() throws InvalidFileFormatException, IOException {
		Reporter.log("resetData");
		objBagPageObjects.resetBag();
		objWishlistPageObject.resetWishlist();
		objAddressPageObjects.resetAddress();
		Reporter.log("HomePage");
		objHomePageObjects.clickOnSearchIcon();
		objHomePageObjects.enterSearchItem(objAndroidGenericMethods.getValueByKeyWeb(testName, "productcode"));
	}

	@Test(priority = 3)
	public void CheckForTopButtons() {
		Reporter.log("SelectSize");
		objPDPageObject.clickSaveToWishlist();
		objPDPageObject.VerifyProductTitle();
		objPDPageObject.imageVerification();
		objPDPageObject.assertProductPrice();
		// objPDPageObject.clickFirstProductSearchResult();
		objHomePageObjects.clickOnWishlistButton();
		objWishlistPageObject.VerifyWishlistPageTitle();
		objWishlistPageObject.VerifySellingPrice();
		objWishlistPageObject.VerfiyProductIsAddedToWishlist();
	}

	@Test(priority = 4)
	public void AddToBag() {
		objWishlistPageObject.ClickOnMoveToBag();
		objWishlistPageObject.ClickSizeButtons();
		objWishlistPageObject.ClickOnDoneButton();
	}

	@Test(priority = 6)
	public void PlaceOrder() {
		Reporter.log("PlaceOder");
		objWishlistPageObject.ClickOnCartIconFromWishlist();
		// objBagPageObjects.changeSize();
		objBagPageObjects.assertBagPageTitle("Bag");
		objBagPageObjects.VerifyProductTitle();
		objBagPageObjects.getProductImage();
		objBagPageObjects.VerfiyProductIsAddedToCart();
		objBagPageObjects.VerifySellingPrice();
		objBagPageObjects.clickOnPlaceOrder();
	}

	@Test(priority = 7)
	public void CheckForOffers() {
	}

	@Test(priority = 8)
	public void AddNewAddress_Home() throws InvalidFileFormatException, IOException {
		Reporter.log("AddNew Address Test Started");
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
		objAddressPageObjects.VerifyAddressAdded();
		objBagPageObjects.clickOnContinueButton();
	}

	@Test(priority = 9)
	public void PaymentWithCreditDebitCard() {
		Reporter.log("CompletePayment");
		objPaymentPageObjects.VerifyDeliveryAddress();
		objPaymentPageObjects.clickOnCreditCard();
	}

	@Test(priority = 12)
	public void LogOut() throws InterruptedException {
		Reporter.log("Logout");
		objAndroidGenericMethods.scrollDown(objMenuPageObjects.myntraLogoFromPaymentpage, -100);
		objHamburgerPageObjects.logoutAndVerifySessionId();
	}

	@Parameters({ "browserName_","deviceName_","UDID_","platformVersion_", "URL_", "appUrl_", "screenshotPath_","engine_", "platform_" })
	// @Parameters({ "browserType" })
	@BeforeTest
	public void beforeTest(@Optional("TD") String browserName_, @Optional("TD") String deviceName_, @Optional("TD") String UDID_, @Optional("TD") String platformVersion_, @Optional("TD") String URL_, @Optional("TD") String appUrl_, @Optional("TD") String screenshotPath_, @Optional("TD") String engine_, @Optional("TD") String platform_) throws Exception {
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
		 params.put("engine_", engine_);
        params.put("platform_", platform_);
		 if(!(params.get("engine_").equalsIgnoreCase("TD"))) {
			wd = objMobileDrivers.launchAppAndroid(params);
		} else {
			try {
				setUpTest();
				System.out.println("TestDroid Execution Started");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println("Error :: Please change suite parameter to run locally.");
			}
		}
		objAndroidGenericMethods = new AndroidGenericMethods(wd);
		wd.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		objAddressPageObjects = new AddressPageObjects(wd);
		objEdit_ChangeButtonPageObjects = new Edit_ChangeButtonPageObjects(wd);
		objBagPageObjects = new BagPageObjects(wd);
		objHomeAndLivingCategoriesPageObjects = new HomeAndLivingCategoriesPageObjects(wd);
		objKidsCategoriesPageObjects = new KidsCategoriesPageObjects(wd);
		objMenCategoriesPageObjects = new MenCategoriesPageObjects(wd);
		objWomenCategoriesPageObjects = new WomenCategoriesPageObjects(wd);
		objMenuPageObjects = new MenuPageObjects(wd);
		objPaymentPageObjects = new PaymentPageObjects(wd);
		objFilterPageObjects = new FilterPageObjects(wd);
		objPDPageObject = new PDPageObjects(wd);
		objHamburgerPageObjects = new HamburgerPageObjects(wd);
		objHomePageObjects = new HomePageObjects(wd);
		objPLPageObjects = new PLPageObjects(wd);
		objWishlistPageObject = new WishListPageObject(wd);
		objAndroidGenericMethods = new AndroidGenericMethods(wd);
	}

	@AfterTest
	public void afterTest() {
		System.out.println("=====================VEGASF_340_END=====================");
		try {
			quitAppiumSession();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		wd.quit();
	}
}