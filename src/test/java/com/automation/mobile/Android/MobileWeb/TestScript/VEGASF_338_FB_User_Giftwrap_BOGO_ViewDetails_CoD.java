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
 * @author 300021278 -Rakesh TEST STEPS 1. Login(Email registered user) 2. Reset
 *         Cart, Wishlist and Address Pages 3. Search (using a keyword in the
 *         Golden Set) 4. Sort(By Discount) from PLP 5. Navigate to PDP, select
 *         a size and add the product to Bag 6. Verify 'Add more from wishlist
 *         link' and Place the order from Bag 7. Add a new address and verify
 *         'view details' link 8. Payment through COD
 */
public class VEGASF_338_FB_User_Giftwrap_BOGO_ViewDetails_CoD {
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

	String testName = "VEGASF_338";

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

	@Test(priority = 1)
	public void LoginWithFacebook() throws InvalidFileFormatException, IOException {
		System.out.println("=====================VEGASF_338_START=====================");
		objHomePageObjects.clickOnHamburgerButton();

		objHambergerPageObjects.clickOnSignInOption();
		objHambergerPageObjects.enterEmailAddress(objAndroidGenericMethods.getValueByKeyWeb(testName, "UserName"),
				objAndroidGenericMethods.getValueByKeyWeb(testName, "Password"));
		objAndroidGenericMethods.scrollDown(objHambergerPageObjects.getSignInButton(), 10);
		// objHomePageObjects.clickOnHamburgerButton();
		// objHambergerPageObjects.clickOnMyAccount();
		// objHambergerPageObjects.assertUserEmailID(objAndroidGenericMethods.getValueByKeyWeb(testName,
		// "UserName"));
		// aDriver.navigate().back();
	}

	@Test(priority = 2)
	public void resetData() {
		objBagPageObjects.resetBag();
		objWishlistPageObject.resetWishlist();
		objAddressPageObjects.resetAddress();
	}

	@Test(priority = 3)
	public void SearchItem() throws InvalidFileFormatException, IOException {
		objHomePageObjects.clickOnSearchIcon();
		objHomePageObjects.enterSearchItem(objAndroidGenericMethods.getValueByKeyWeb(testName, "SearchItem"));
		objAndroidGenericMethods.waitDriver(objPLPageObjects.searchResultHeader, "searchResultHeader");
		objHomePageObjects.getSearchAutoSuggestList();
		objPLPageObjects.VerifyProductDetails();
	}

	@Test(priority = 4)
	public void SaveToWishlist() {
		objPLPageObjects.clickToSaveToWishlist();
	}

	@Test(priority = 5)
	public void FilterProducts() {
		objFilterPageObjects.clickOnFilterButton();
		objFilterPageObjects.clickOnDiscount();
		objFilterPageObjects.clickOnFirstDiscountOption();
		objFilterPageObjects.clickOnApplyButton();
	}

	@Test(priority = 7)
	public void MoveToWishlist() {
		objHomePageObjects.clickOnWishlistButton();
		objWishlistPageObject.VerifyWishlistPageTitle();
		objWishlistPageObject.VerifywishlistProductTitle();
		objWishlistPageObject.VerifySellingPrice();
		objWishlistPageObject.ClickOnMoveToBag();
		objWishlistPageObject.ClickSizeButtons();
		objWishlistPageObject.ClickOnDoneButton();
		objWishlistPageObject.clickOnMyntraLogo();
	}

	@Test(priority = 8)
	public void GoToCart() {
		objHomePageObjects.clickOnBagIcon();
		objBagPageObjects.VerifyProductTitle();
		objBagPageObjects.getProductImage();
		objBagPageObjects.VerifySellingPrice();
	}

	@Test(priority = 9)
	public void GiftWrap() throws InvalidFileFormatException, IOException {
		objBagPageObjects.clickOnViewDetails();
		objBagPageObjects.clickOnGiftWrap();
		objBagPageObjects.enterRecipientName(objAndroidGenericMethods.getValueByKeyWeb(testName, "Receiver"));
		objBagPageObjects.enterGiftMsg(objAndroidGenericMethods.getValueByKeyWeb(testName, "GiftMessage"));
		objBagPageObjects.enterSenderName(objAndroidGenericMethods.getValueByKeyWeb(testName, "Sender"));
		objBagPageObjects.clickOnSaveGiftWrap();
		objBagPageObjects.VerifySellingPrice();
		objBagPageObjects.VerifyProductTitle();
		objBagPageObjects.verifyProduct();
		objBagPageObjects.assertBagPageTitle("bag");
		objBagPageObjects.clickOnViewDetails();
	}

	@Test(priority = 10)
	public void PlaceOrder() {
		objBagPageObjects.clickOnPlaceOrder();
	}

	@Test(priority = 11)
	public void AddNewAddress() throws InvalidFileFormatException, IOException {
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
	}

	@Test(priority = 12)
	public void ViewDetails() {
		objBagPageObjects.clickOnViewDetails();
	}

	@Test(priority = 13)
	public void PaymentWithGiftcard() throws InvalidFileFormatException, IOException {
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

	@AfterTest
	public void afterTest() {
		System.out.println("=====================VEGASF_338_START=====================");
		aDriver.quit();
	}

}