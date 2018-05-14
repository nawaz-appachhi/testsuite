package com.automation.mobile.Android.MobileWeb.TestScript;

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
 *         for a brand like Nike, Roadster) 4. Navigate from PLP to PDP 5. Save
 *         the item from PDP 6. Select a size and Add to bag 7. Place the order
 *         from Bag Page 8. Add the new address and Edit the address 9. Payment
 *         through Gift card and Netbanking
 */
public class VEGASF_203_RegisteredUser_BrandProfile_ShowMoreProducts_PayWithGiftCardAndCC {

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

	String testName = "VEGASF_203";

	@Test(priority = 1)
	public void LoginWithEmail() throws InvalidFileFormatException, IOException {
		System.out.println("=====================VEGASF_203_START=====================");
		Reporter.log("LoginWithEmail test started");
		objHomePageObjects.clickOnHamburgerButton();
		objHambergerPageObjects.clickOnSignInOption();
		objHambergerPageObjects.enterEmailAddress(objAndroidGenericMethods.getValueByKeyWeb(testName, "UserName"),
				objAndroidGenericMethods.getValueByKeyWeb(testName, "Password"));
		objAndroidGenericMethods.scrollDown(objHambergerPageObjects.getSignInButton(), 10);

	}

	@Test(priority = 2)
	public void resetData() throws InterruptedException {
		Reporter.log("resetData test Started");
		objBagPageObjects.resetBag();
		objWishlistPageObject.resetWishlist();
		objAddressPageObjects.resetAddress();
	}

	@Test(priority = 3)
	public void SearchItem() throws InterruptedException, InvalidFileFormatException, IOException {
		Reporter.log("SearchItem test Started");
		objHomePageObjects.clickOnSearchIcon();
		objHomePageObjects.enterSearchItem(objAndroidGenericMethods.getValueByKeyWeb(testName, "SearchItem"));
	}

	@Test(priority = 4)
	public void PLPToPDPNavigation() throws InterruptedException {
		Reporter.log("PLPToPDPNavigation tets Started");
		objPDPageObject.clickFirstProductSearchResult();
		objPDPageObject.VerifyProductTitle();
		objPDPageObject.imageVerification();
		objPDPageObject.assertProductPrice();
	}

	@Test(priority = 5)
	public void AddToBag() throws InterruptedException {
		Reporter.log("AddToBag test started");
		objPDPageObject.clickOnSaveButton();
		objPDPageObject.clickOnAddtoBag();
		objPDPageObject.setFirstAvailableSize();
		objPDPageObject.clickOnConfirmButton();
		objPDPageObject.clickOngoToBag();
		objBagPageObjects.assertBagPageTitle("bag");
		objAndroidGenericMethods.scrollDown(objBagPageObjects.getMoveToWishlist(), 50);
		objBagPageObjects.ClickOnGotoWishlist();
		objWishlistPageObject.VerifySellingPrice();
		objWishlistPageObject.VerifyWishlistPageTitle();
		objWishlistPageObject.VerifywishlistProductTitle();
		objWishlistPageObject.clickWishListMoveToBag();
		objWishlistPageObject.clickDoneWishListbtn();
		objWishlistPageObject.ClickOnCartIconFromWishlist();
		objBagPageObjects.assertBagPageTitle("bag");

	}

	@Test(priority = 6)
	public void PlaceOrder() throws InterruptedException {
		Reporter.log("PlaceOrder test Started");
		objAndroidGenericMethods.scrollDown(objBagPageObjects.getApplyCouponButton(), 50);
		objBagPageObjects.clickOnCancelButton();
		objBagPageObjects.clickOnPlaceOrder();

	}

	@Test(priority = 7)
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

	@Test(priority = 8)
	public void GiftCardAndNetbanking() throws InvalidFileFormatException, IOException {
		Reporter.log("PaymentWithCG_Online test Started");
		objBagPageObjects.clickOnContinueButton();
		objBagPageObjects.assertPaymentPageTitle("payment");
		objPaymentPageObjects.VerifyDeliveryAddress();
		objAndroidGenericMethods.scrollDown(objPaymentPageObjects.getGiftCard(), 50);
		objPaymentPageObjects
				.enterGiftCardNumber(objAndroidGenericMethods.getValueByKeyWeb(testName, "GiftCardNumber"));
		objPaymentPageObjects.enterGiftCardpin(objAndroidGenericMethods.getValueByKeyWeb(testName, "GIftCardPin"));
		objAndroidGenericMethods.scrollDown(objPaymentPageObjects.giftCardApplyButton, 5);
		objAndroidGenericMethods.scrollDown(objPaymentPageObjects.changePaymentMode, -10);
		objPaymentPageObjects.clickOnNetBanking();
		objPaymentPageObjects.clickFirstNetbankingOption();
		objPaymentPageObjects.clickNetbankingPayNowButton();
	}

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

	}

	@AfterTest
	public void afterTest() {
		System.out.println("=====================VEGASF_203_END=====================");
		aDriver.quit();
	}

}
