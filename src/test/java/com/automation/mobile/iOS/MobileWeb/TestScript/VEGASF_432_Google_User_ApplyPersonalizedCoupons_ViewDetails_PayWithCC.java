package com.automation.mobile.iOS.MobileWeb.TestScript;
/***
 * @author subasis
*
Google registered user - Login
Home Page
Search (using a keyword in the Golden Set)
Sort
Verify Discounted price in PDP page
Select Size in wishlist page
Place Order
Apply Personalized Coupons
View Details
Payment : Manual GC +Online
*
*
 */
import java.io.IOException;
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
import com.automation.core.mobile.iOS.iOSGenericMethods;
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
import com.automation.mobile.iOS.MobileWeb.ObjectRepository.WishList.WishListPageObject;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;

public class VEGASF_432_Google_User_ApplyPersonalizedCoupons_ViewDetails_PayWithCC {

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

	String testName = "VEGASF_432";

	@Test(priority = 1)
	public void LoginWithGoogle() throws InterruptedException, InvalidFileFormatException, IOException{
		Reporter.log("LoginWithGoogle test case started successfully");
		System.out.println("=====================VEGASF_432_START=====================");
		System.out.println("LoginWithGoogle");
		objHomePageObjects.clickOnHamburgerButton();
		if (objHamburgerPageObjects.isLoggedIn())
		{
			objHamburgerPageObjects.clickOnMyAccount(); 
			objHamburgerPageObjects.clickOnLogout();
			objHomePageObjects.clickOnHamburgerButton();
		}
//		objHamburgerPageObjects.clickOnSignInOption();
//		Thread.sleep(3000);
//		objHamburgerPageObjects.clickOnFacebook();
//		objiOSGenericMethods.switchToNewWindow();
//		objHamburgerPageObjects.enterFacebookEmailId(objiOSGenericMethods.getValueByKeyiOSWeb(testName, "UserName"),(objiOSGenericMethods.getValueByKeyiOSWeb(testName, "Password")));
//		objHamburgerPageObjects.clickOnFacebookLoginButton();
//		objiOSGenericMethods.switchToMainWindow();
//		
//	}
	
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
	public void resetData() throws InterruptedException	{
		Reporter.log("resetData test case started successfully");
		System.out.println("Reset Bag, Wishlist & Address!");
		objBagPageObjects.resetBag();
		objWishlistPageObject.resetWishlist();
		objAddressPageObjects.resetAddress();
	}

	@Test(priority = 3)
	public void SearchItem() throws InterruptedException, InvalidFileFormatException, IOException{
		Reporter.log("SearchItem test case started successfully");
		System.out.println("Search Item!");
		objHomePageObjects.clickOnSearchIcon();
		objHomePageObjects.enterSearchItem(objiOSGenericMethods.getValueByKeyiOSWeb(testName, "SearchItem"));
		objPLPageObjects.verifySearchResult("Result Search Title");
		objHomePageObjects.getSearchAutoSuggestList();
		objPLPageObjects.VerifyProductDetails();
	}

	@Test(priority = 4)
	public void Sort() throws InterruptedException	{
		Reporter.log("Sort test case started successfully");
		System.out.println("Sort Item!");
		objFilterPageObjects.clickOnSortButton();
		objFilterPageObjects.clickOnPriceHightoLow();
		objPDPageObject.clickFirstProductSearchResult();
		objPDPageObject.VerifyProductTitle();
		objPDPageObject.assertProductDiscount();
		objPDPageObject.assertProductPrice();
		objPDPageObject.clickOnSaveButton();
	
		
	}


	@Test(priority = 5)
	public void SelectSizeWishlist() throws InterruptedException {
		Reporter.log("SelectSizeWishlist test case started successfully");
		System.out.println("SelectSizeWishlist!");
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
	
	@Test(priority = 6)
	public void PlaceOder() throws InterruptedException, InvalidFileFormatException, IOException {
		Reporter.log("PlaceOder test case started successfully");
		System.out.println("PlaceOrder!");
		objBagPageObjects.clickOnPlaceOrder();
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
	
//	@Test(priority = 7)
//	public void ApplyCoupon() throws InterruptedException {
	//Reporter.log("ApplyCoupon test case started successfully");
	//System.out.println("ApplyCoupon!");
//		objBagPageObjects.clickOnApplyCouponButton();
//		objBagPageObjects.EnterCouponCode("5676787878");
//		objBagPageObjects.clickOnApplyButton();
//	}
	
	@Test(priority = 8)
	public void ViewDetails() throws InterruptedException	{
		Reporter.log("ViewDetails test case started successfully");
		System.out.println("ViewDetails!");
		objBagPageObjects.clickOnViewDetails();
		objBagPageObjects.clickOnContinueButton();
	}

	@Test(priority = 9)
	public void PaymentWithManualGC_Online() throws InterruptedException, InvalidFileFormatException, IOException	{
		Reporter.log("PaymentWithManualGC_Online test case started successfully");
		System.out.println("PaymentWithManualGC_Online!");
		objBagPageObjects.assertPaymentPageTitle("payment");
		objPaymentPageObjects.VerifyDeliveryAddress();
//		objPaymentPageObjects.clickOnGiftcard();
//		objPaymentPageObjects.enterGiftCardNumber(objiOSGenericMethods.getValueByKeyiOSWeb(testName, "GiftCardNumber"));
//		objPaymentPageObjects.enterGiftCardpin(objiOSGenericMethods.getValueByKeyiOSWeb(testName, "GIftCardPin"));
//		objPaymentPageObjects.clickOnGiftCardApplyButton();
//		objPaymentPageObjects.clickOnChangePaymentMode();
//		objPaymentPageObjects.clickOnNetBanking();
//		objPaymentPageObjects.clickFirstNetbankingOption();
//		objPaymentPageObjects.clickNetbankingPayNowButton();
		objPaymentPageObjects.clickOnCCandDC();
	}
	
	
	@Parameters({ "browserName_","deviceName_","UDID_","platformVersion_", "URL_", "appUrl_", "screenshotPath_" })
	@BeforeTest
	public void beforeTest(String browserName_, String deviceName_, String UDID_, String platformVersion_, String URL_, String appUrl_, String screenshotPath_) throws InterruptedException  {
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
			iDriver.quit();
			System.out.println("=====================VEGASF_432_END=====================");
		}
	
	
	
}
