package com.automation.mobile.Android.MobileWeb.TestScript;

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

/*Amba
 * 
 * Google registered user - Login
Home Page
Search (using a keyword in the Golden Set)
Save Product
Verify Product Price
Empty Wishlist
Change Size & add Quantity in cart Page
Click for offer
Remove address
 */
public class VEGASF_325_GoogleRegistered_User_AddQTY_Click4OfferGoogleRegistered_User_AddQTY_Click4Offer
		extends BaseAndroidTest {
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
	AndroidGenericMethods objAndroidGenericMethods;
	String testName = "VEGASF_325";

	// @Parameters({ "browserType" })
	@Parameters({ "browserName_","deviceName_","UDID_","platformVersion_", "URL_", "appUrl_", "screenshotPath_","engine_", "platform_" })
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
				setUpTest(params.get("platform_"));
				System.out.println("TestDroid Execution Started");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println("Error :: Please change suite parameter to run locally.");
			}
		}
		wd.get(params.get("appUrl_"));
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
		objHambergerPageObjects = new HamburgerPageObjects(wd);
		objHomePageObjects = new HomePageObjects(wd);
		objPLPageObjects = new PLPageObjects(wd);
		objWishlistPageObject = new WishListPageObject(wd);
		objAndroidGenericMethods = new AndroidGenericMethods(wd);
	}

	@Test(priority = 1)
	public void Login() throws InvalidFileFormatException, IOException {
		System.out.println("=====================VEGASF_325_START=====================");
		objHomePageObjects.clickOnHamburgerButton();
		objHambergerPageObjects.clickOnSignInOption();
		objHambergerPageObjects.enterEmailAddress(objAndroidGenericMethods.getValueByKeyWeb(testName, "UserName"),
				objAndroidGenericMethods.getValueByKeyWeb(testName, "Password"));
		objAndroidGenericMethods.scrollDown(objHambergerPageObjects.getSignInButton(), 10);
	}

//	@Test(priority = 2)
//	public void resetData() throws InterruptedException {
//		System.out.println("Reset Bag, Wishlist & Address!");
//		objBagPageObjects.resetBag();
//		objWishlistPageObject.resetWishlist();
//		objAddressPageObjects.resetAddress();
//	}
//
//	@Test(priority = 3)
//	public void SearchItem() throws InvalidFileFormatException, IOException {
//		objHomePageObjects.clickOnSearchIcon();
//		objHomePageObjects.enterSearchItem(objAndroidGenericMethods.getValueByKeyWeb(testName, "SearchItem"));
//		objPLPageObjects.VerifyProductDetails();
//	}
//
//	@Test(priority = 4)
//	public void Save() throws InvalidFileFormatException, IOException {
//		objPDPageObject.clickFirstProductSearchResult();
//		System.out.println("First Product Clicked");
//		objPDPageObject.clickSaveToWishlist();
//		System.out.println("Save Clicked");
//		objPDPageObject.VerifyProductTitle();
//		objPDPageObject.imageVerification();
//		objPDPageObject.assertProductPrice();
//	}
//
//	@Test(priority = 5)
//	public void AddToBag() throws InterruptedException {
//		objPDPageObject.clickOnAddtoBag();
//		objPDPageObject.setFirstAvailableSize();
//		objPDPageObject.clickOnConfirmButton();
//	}
//
//	@Test(priority = 6)
//	public void EmptyWishList() throws InterruptedException {
//		objWishlistPageObject.resetWishlist();
//	}
//
//	@Test(priority = 7)
//	public void MoveToBag() throws InterruptedException {
//		objHomePageObjects.clickOnBagIcon();
//	}
//
//	@Test(priority = 8)
//	public void SelectSize_Qty() throws InterruptedException {
//		objBagPageObjects.changeSize();
//		objBagPageObjects.assertBagPageTitle("Bag");
//		objBagPageObjects.VerifyProductTitle();
//		objBagPageObjects.getProductImage();
//		objBagPageObjects.VerfiyProductIsAddedToCart();
//		objBagPageObjects.VerifySellingPrice();
//	}
//
//	@Test(priority = 9)
//	public void CheckForOffer() throws InterruptedException {
//		objBagPageObjects.clickOnPlaceOrder();
//	}
//
//	@Test(priority = 10)
//	public void RemoveExistingAddress() throws InvalidFileFormatException, IOException {
//		objAddressPageObjects.ClickOnPincodeAddress();
//		objAddressPageObjects.enterPincone(objAndroidGenericMethods.getValueByKeyWeb(testName, "Pincode"));
//		objAddressPageObjects.clickOnChoose();
//		objAddressPageObjects.selectTownLocality(objAndroidGenericMethods.getValueByKeyWeb(testName, "Locality"));
//		objAddressPageObjects.entername(objAndroidGenericMethods.getValueByKeyWeb(testName, "Name"));
//		objAndroidGenericMethods.scrollDown(objAddressPageObjects.Address, 20);
//		objAddressPageObjects.enteraddress(objAndroidGenericMethods.getValueByKeyWeb(testName, "Address"));
//		objAndroidGenericMethods.scrollDown(objAddressPageObjects.getMobileNumber(), 20);
//		objAddressPageObjects.enterMobileNumber(objAndroidGenericMethods.getValueByKeyWeb(testName, "MobileNumber"));
//		objAndroidGenericMethods.scrollDown(objAddressPageObjects.getOfficeCommercial(), 20);
//		objAndroidGenericMethods.scrollDown(objAddressPageObjects.getopenOnSaturdays(), 20);
//		objAndroidGenericMethods.scrollDown(objAddressPageObjects.getSaveAddress(), 20);
//		objAndroidGenericMethods.scrollDown(objEdit_ChangeButtonPageObjects.getEditChangeButton(), -20);
//		objEdit_ChangeButtonPageObjects.clickOnremoveButton();
//	}
//
//	@Test(priority = 12)
//	public void LogOut() throws InterruptedException {
//		Reporter.log("Logout");
//		objAndroidGenericMethods.scrollDown(objMenuPageObjects.myntraLogoFromPaymentpage, -100);
//		objHambergerPageObjects.logoutAndVerifySessionId();
//	}

	@AfterTest
	public void afterTest() {
		System.out.println("=====================VEGASF_325_END=====================");
		try {
			quitAppiumSession();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		wd.quit();
	}
}