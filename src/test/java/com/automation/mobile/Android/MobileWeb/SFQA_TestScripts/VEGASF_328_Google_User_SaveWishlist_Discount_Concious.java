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
 * @author 300021279 - Sangam TEST STEPS # Google registered user - Login # Home
 *         Page # Search (using menu item e.g. Men -> Topwear - T-Shirts # Save
 *         Product # Verify Product Code # Move to bag # Place Order #
 *         Percentage Discount # Remove address
 */
public class VEGASF_328_Google_User_SaveWishlist_Discount_Concious {
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

	String testName = "VEGASF_328";

	@Parameters({ "browserName_", "deviceName_", "UDID_", "platformVersion_", "URL_", "appUrl_", "screenshotPath_" })
	// @Parameters({ "browserType" })
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

	// @Test(priority = 1)
	// public void LoginInApp() throws InterruptedException,
	// InvalidFileFormatException, IOException {
	// objHomePageObjects.clickOnHamburgerButton();
	// objHambergerPageObjects.clickOnSignInOption();
	// objHambergerPageObjects.clickOnGoogle();
	// objAndroidGenericMethods.switchToNewWindow();
	// objHambergerPageObjects.enterGoogleEmailField(objAndroidGenericMethods.getValueByKeyWeb(testName,
	// "UserName"),objAndroidGenericMethods.getValueByKeyWeb(testName,
	// "Password"));
	// objAndroidGenericMethods.switchToMainWindow();
	// objHambergerPageObjects.clickOnGoogle();
	//// objHambergerPageObjects.clickOnFacebook();
	//// objAndroidGenericMethods.switchToNewWindow();
	//// objHambergerPageObjects.enterFacebookEmailId(objAndroidGenericMethods.getValueByKeyWeb(testName,
	// "UserName"),objAndroidGenericMethods.getValueByKeyWeb(testName,
	// "Password"));
	//// objHambergerPageObjects.clickOnFacebookLoginButton();
	//// objAndroidGenericMethods.switchToMainWindow();
	//// objHambergerPageObjects.enterEmailAddress(objAndroidGenericMethods.getValueByKeyWeb(testName,
	// "UserName"),
	//// objAndroidGenericMethods.getValueByKeyWeb(testName, "Password"));
	// objAndroidGenericMethods.backKeyButton();
	//// objHambergerPageObjects.clickOnSignInButton();
	// }

	@Test(priority = 1)
	public void Login() throws InvalidFileFormatException, IOException {
		System.out.println("=====================VEGASF_328_START=====================");
		objHomePageObjects.clickOnHamburgerButton();

		objHambergerPageObjects.clickOnSignInOption();
		objHambergerPageObjects.enterEmailAddress(objAndroidGenericMethods.getValueByKeyWeb(testName, "UserName"),
				objAndroidGenericMethods.getValueByKeyWeb(testName, "Password"));
		objAndroidGenericMethods.scrollDown(objHambergerPageObjects.getSignInButton(), 10);
	}

	@Test(priority = 2)
	public void resetData() throws InterruptedException {
//		objBagPageObjects.resetBag();
//		objWishlistPageObject.resetWishlist();
//		objAddressPageObjects.resetAddress();
	}

	@Test(priority = 3)
	public void SearchItem() throws InvalidFileFormatException, IOException, InterruptedException

	{
		objHomePageObjects.clickOnHamburgerButton();
		objMenCategoriesPageObjects.clickOnmen();
		objMenCategoriesPageObjects.clickOntopWare();
		objMenCategoriesPageObjects.clickOnactiveTShirt();
		objPLPageObjects.VerifyProductDetails();
	}

	@Test(priority = 5)
	public void NavigateFromPLPtoPDP() throws InterruptedException {
		objPDPageObject.clickFirstProductSearchResult();
		objPDPageObject.VerifyProductTitle();
		objPDPageObject.imageVerification();
		objPDPageObject.assertProductPrice();
	}

	@Test(priority = 6)
	public void SaveProduct() throws InterruptedException {
		objPDPageObject.clickSaveToWishlist();
	}

	@Test(priority = 8)
	public void AddToBag() throws InterruptedException {
		objPDPageObject.clickOnAddtoBag();
		objPDPageObject.setFirstAvailableSize();
		objPDPageObject.clickOnConfirmButton();
		objHomePageObjects.clickOnWishlistButton();
		objWishlistPageObject.VerifyWishlistPageTitle();
		objWishlistPageObject.VerifySellingPrice();	
		objWishlistPageObject.VerfiyProductIsAddedToWishlist();
		objWishlistPageObject.ClickOnMoveToBag();
		objWishlistPageObject.ClickSizeButtons();
		objWishlistPageObject.ClickOnDoneButton();
		objWishlistPageObject.clickOnMyntraLogo();
		objHomePageObjects.clickOnBagIcon();
		objBagPageObjects.assertBagPageTitle("Bag");
		objBagPageObjects.VerifyProductTitle();
		objBagPageObjects.getProductImage();
		objBagPageObjects.VerfiyProductIsAddedToCart();
		objBagPageObjects.VerifySellingPrice();
	}

	@Test(priority = 9)
	public void PlaceOrder() throws InterruptedException {
		objBagPageObjects.clickOnPlaceOrder();
	}

	@Test(priority = 10)
	public void RemoveAddress() throws InvalidFileFormatException, IOException {
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
		objEdit_ChangeButtonPageObjects.clickOnremoveButton();
	}
	@Test(priority = 12)
	public void LogOut() throws InterruptedException  {
		Reporter.log("Logout");
		objAndroidGenericMethods.scrollDown(objMenuPageObjects.myntraLogoFromPaymentpage, -100);
		objHambergerPageObjects.logoutAndVerifySessionId();
	}

	@AfterTest
	public void afterTest() {
		System.out.println("=====================VEGASF_328_END=====================");
		aDriver.quit();
	}

}