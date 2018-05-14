package com.automation.mobile.iOS.MobileWeb.TestScript;

/**
 * @author 300021275 - Lata
 *
 *         Shopper:
 * 
 *         Facebook registered user Home Page Search - Autosuggest - Brand
 *         Profile (Search for a brand like Nike, Roadster) Sort Product View
 *         Similar Products Empty Wishlist Apply Coupon Click for offer Add New
 *         address - Office Payment : Net Banking
 */
import org.testng.annotations.Test;
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
import com.automation.core.Common.AppiumServer;
import com.automation.core.Common.GlobalVariables;
import com.automation.core.Common.MobileDrivers;
import com.automation.core.mobile.iOS.iOSGenericMethods;
import com.automation.mobile.iOS.MobileWeb.ObjectRepository.AddressPageObjects.AddressPageObjects;
import com.automation.mobile.iOS.MobileWeb.ObjectRepository.AddressPageObjects.Edit_ChangeButtonPageObjects;
import com.automation.mobile.iOS.MobileWeb.ObjectRepository.Bag.BagPageObjects;
import com.automation.mobile.iOS.MobileWeb.ObjectRepository.CategoriesObjects.HomeAndLivingCategoriesPageObjects;
import com.automation.mobile.iOS.MobileWeb.ObjectRepository.CategoriesObjects.KidsCategoriesPageObjects;
import com.automation.mobile.iOS.MobileWeb.ObjectRepository.CategoriesObjects.MenCategoriesPageObjects;
import com.automation.mobile.iOS.MobileWeb.ObjectRepository.CategoriesObjects.WomenCategoriesPageObjects;
import com.automation.mobile.iOS.MobileWeb.ObjectRepository.HomeObjects.HamburgerPageObjects;
import com.automation.mobile.iOS.MobileWeb.ObjectRepository.HomeObjects.HomePageObjects;
import com.automation.mobile.iOS.MobileWeb.ObjectRepository.HomeObjects.MenuPageObjects;
import com.automation.mobile.iOS.MobileWeb.ObjectRepository.PDPageObjects.PDPageObjects;
import com.automation.mobile.iOS.MobileWeb.ObjectRepository.PLPageObjects.FilterPageObjects;
import com.automation.mobile.iOS.MobileWeb.ObjectRepository.PLPageObjects.PLPageObjects;
import com.automation.mobile.iOS.MobileWeb.ObjectRepository.PaymentObjects.PaymentPageObjects;
import com.automation.mobile.iOS.MobileWeb.ObjectRepository.WishList.WishListPageObject;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;

public class VEGASF_379_FB_User_Sort_ApplyCoupon_AddNewAddress_Home_PayWithDC {
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
	PLPageObjects objPLPageObjects;
	HamburgerPageObjects objHambergerPageObjects;
	HomePageObjects objHomePageObjects;
	PaymentPageObjects objPaymentageObjects;
	WishListPageObject objWishlistPageObject;
	IOSDriver<IOSElement> iDriver;
	iOSGenericMethods objiOSGenericMethods;

	String testName = "VEGASF_379";

	// @Test(priority = 1)
	// public void LoginInApp() throws InterruptedException,
	// InvalidFileFormatException, IOException {
	// objHomePageObjects.clickOnHamburgerButton();
	// if (objHambergerPageObjects.isLoggedIn())
	// {
	// objHambergerPageObjects.clickOnMyAccount();
	// objHambergerPageObjects.clickOnLogout();
	// objHomePageObjects.clickOnHamburgerButton();
	// }
	// objHomePageObjects.clickOnHamburgerButton();
	// objHambergerPageObjects.clickOnSignInOption();
	// objHambergerPageObjects.clickOnFacebook();
	//// objiOSGenericMethods.switchToNewWindow();
	// Thread.sleep(10000);
	// objHambergerPageObjects.enterFacebookEmailId(objiOSGenericMethods.getValueByKeyiOSWeb(testName,
	// "UserName"),
	// objiOSGenericMethods.getValueByKeyiOSWeb(testName, "Password"));
	// objHambergerPageObjects.clickOnFacebookLoginButton();
	// Thread.sleep(5000);
	//// objHambergerPageObjects.
	//// objiOSGenericMethods.switchToMainWindow();
	// // objHambergerPageObjects.clickOnGoogle();
	//
	// }

	@Test(priority = 1)
	public void LoginWithFacebook() throws InterruptedException, InvalidFileFormatException, IOException {
		Reporter.log("LoginWithFacebook test case started successfully");
		System.out.println("=====================VEGASF_379_START=====================");
		System.out.println("LoginWithFacebook");
		objHomePageObjects.clickOnHamburgerButton();
		if (objHambergerPageObjects.isLoggedIn()) {
			objHambergerPageObjects.clickOnMyAccount();
			objHambergerPageObjects.clickOnLogout();
			objHomePageObjects.clickOnHamburgerButton();
		}
		objHambergerPageObjects.clickOnSignInOption();
		objHambergerPageObjects.enterEmailAddress(objiOSGenericMethods.getValueByKeyiOSWeb(testName, "UserName"),
				objiOSGenericMethods.getValueByKeyiOSWeb(testName, "Password"));
		objHambergerPageObjects.clickOnSignInButton();
		objHomePageObjects.clickOnHamburgerButton();
		objHambergerPageObjects.clickOnMyAccount();
		objHambergerPageObjects.assertUserEmailID(objiOSGenericMethods.getValueByKeyiOSWeb(testName, "UserName"));
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
		System.out.println("Search Item!");
		objHomePageObjects.clickOnSearchIcon();
		objHomePageObjects.enterSearchItem(objiOSGenericMethods.getValueByKeyiOSWeb(testName, "SearchItem"));
		objPLPageObjects.verifySearchResult("Result Search Title");
		objHomePageObjects.getSearchAutoSuggestList();
		objPLPageObjects.VerifyProductDetails();
	}

	@Test(priority = 4)
	public void SortByHightoLow() {
		Reporter.log("SortByHightoLow test case started successfully");
		System.out.println("Sort By High to Low!");
		objFilterPageObjects.clickOnSortButton();
		objFilterPageObjects.clickOnPriceHightoLow();
	}

	@Test(priority = 5)
	public void EmptyWishlist() throws InterruptedException {
		Reporter.log("EmptyWishlist test case started successfully");
		System.out.println("EmptyWishlist!");
		objPDPageObject.clickFirstProductSearchResult();
		objPDPageObject.VerifyProductTitle();
		objPDPageObject.assertProductDiscount();
		objPDPageObject.assertProductPrice();
		objPDPageObject.clickOnSaveButton();
		objPDPageObject.clickOnAddtoBag();
		objPDPageObject.selectSizeOfProduct();
		objPDPageObject.clickOnConfirmButton();
		objHomePageObjects.clickOnWishlistButton();
		objWishlistPageObject.CrossmarkButton1();
	}

	// @Test(priority = 6)
	// public void ShowSimilarProducts() {
	// Reporter.log("ShowSimilarProducts test case started successfully");
	// System.out.println("Show Similar Products!");
	// objiOSGenericMethods.swipeDown(1000, 4);
	// objPDPageObject.getSimilarProductTitle();
	// }

	@Test(priority = 7)
	public void MoveToBag() throws InterruptedException {
		Reporter.log("MoveToBag test case started successfully");
		System.out.println("MoveToBag!");
		objWishlistPageObject.clickOnCartFromWishlist();
		objBagPageObjects.VerifyProductTitle();
		objBagPageObjects.VerifySellingPrice();
		objBagPageObjects.VerifyDiscountedPrice();
	}

	// @Test(priority = 8)
	// public void ApplyCoupon() throws InterruptedException {
	// Reporter.log("ApplyCoupon test case started successfully");
	// System.out.println("ApplyCoupon!");
	// objBagPageObjects.clickOnApplyCouponButton();
	// objBagPageObjects.EnterCouponCode("5676787878");
	// objBagPageObjects.clickOnApplyButton();
	// }

	@Test(priority = 9)
	public void PlaceOrder() {
		Reporter.log("PlaceOrder test case started successfully");
		System.out.println("PlaceOrder!");
		objBagPageObjects.clickOnViewDetails();
		objBagPageObjects.clickOnPlaceOrder();
	}

	@Test(priority = 10)
	public void AddNewAddress_Office() throws InterruptedException, InvalidFileFormatException, IOException {
		Reporter.log("AddNewAddress_Office test case started successfully");
		System.out.println("AddNewAddress Office!");
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
	}

	@Test(priority = 11)
	public void PaymentWithNetBanking() throws InterruptedException {
		Reporter.log("PaymentWithNetBanking test case started successfully");
		System.out.println("Payment With NetBanking!");
		objBagPageObjects.clickOnContinueButton();
		objBagPageObjects.clickOnDemoId();
		objBagPageObjects.assertPaymentPageTitle("payment");
		objPaymentPageObjects.VerifyDeliveryAddress();
		// objPaymentPageObjects.clickOnNetBanking();
		// objPaymentPageObjects.clickFirstNetbankingOption();
		// objPaymentPageObjects.clickNetbankingPayNowButton();
		objPaymentageObjects.clickOnCCandDC();
	}

	@Parameters({ "browserName_", "deviceName_", "UDID_", "platformVersion_", "URL_", "appUrl_", "screenshotPath_" })
	@BeforeTest
	public void beforeTest(String browserName_, String deviceName_, String UDID_, String platformVersion_, String URL_,
			String appUrl_, String screenshotPath_) throws InterruptedException, MalformedURLException {

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
		System.out.println("Test Name " + testName);
		objAddressPageObjects = new AddressPageObjects(iDriver);
		objEdit_ChangeButtonPageObjects = new Edit_ChangeButtonPageObjects(iDriver);
		objBagPageObjects = new BagPageObjects(iDriver);
		objHomeAndLivingCategoriesPageObjects = new HomeAndLivingCategoriesPageObjects(iDriver);
		objKidsCategoriesPageObjects = new KidsCategoriesPageObjects(iDriver);
		objMenCategoriesPageObjects = new MenCategoriesPageObjects(iDriver);
		objWomenCategoriesPageObjects = new WomenCategoriesPageObjects(iDriver);
		objMenuPageObjects = new MenuPageObjects(iDriver);
		objPaymentPageObjects = new PaymentPageObjects(iDriver);
		objFilterPageObjects = new FilterPageObjects(iDriver);
		objPDPageObject = new PDPageObjects(iDriver);
		objHambergerPageObjects = new HamburgerPageObjects(iDriver);
		objHomePageObjects = new HomePageObjects(iDriver);
		objPLPageObjects = new PLPageObjects(iDriver);
		objWishlistPageObject = new WishListPageObject(iDriver);
	}

	@AfterTest
	public void afterTest() {
		iDriver.quit();
		System.out.println("=====================VEGASF_379_END=====================");
	}

}