package com.automation.mobile.iOS.MobileWeb.TestScript;

/**
 * @author 300021275 - Lata
 *
 *         Shopper:
 * "LoginWithFacebook
SearchItem
Filter_Price
EmptyWislist
AddMoreFromWishlist
PercentageDiscount
RemoveAddress
PaymentWithNetBanking
"
 */
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.ini4j.InvalidFileFormatException;
import org.testng.Reporter;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.BaseIOSTest;
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

public class VEGASF_425_FB_User_EmptyWishlist_AddMoreFromWishlist_PercentageDiscount_RemoveAddress_PayWithDD extends BaseIOSTest{

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
	//IOSDriver<IOSElement> wd;
	iOSGenericMethods objiOSGenericMethods;
	WishListPageObject objWishlistPageObject;

	String testName = "VEGASF_425";



	@Test(priority = 1)
	public void LoginWithFacebook() throws InterruptedException, InvalidFileFormatException, IOException {
		Reporter.log("LoginWithFacebook test case started successfully");
		System.out.println("=====================VEGASF_425_START=====================");
		System.out.println("LoginWithFacebook");
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
		wd.navigate().back();
	}

	@Test(priority = 2)
	public void resetData() throws InterruptedException {
		Reporter.log("resetData test case started successfully");
		System.out.println("Reset Bag, Wishlist & Address!");
		 objBagPageObjects.resetBag();
         objWishlistPageObject.resetWishlist();
	}

	@Test(priority = 3)
	public void SearchItem() throws InterruptedException, InvalidFileFormatException, IOException {
		Reporter.log("SearchItem test case started successfully");
		System.out.println("Search Item!");
		objHomePageObjects.clickOnbanner(2);
		objHomePageObjects.clickOnNestedBannerList(2);
		objPLPageObjects.VerifyProductDetails();
	}

	@Test(priority = 4)
	public void FilterProducts() throws InterruptedException {
		Reporter.log("FilterProducts test case started successfully");
		System.out.println("Filter Products!");
		objFilterPageObjects.clickOnFilterButton();
		objFilterPageObjects.clickOnDiscount();
		objFilterPageObjects.clickOnFirstDiscountOption();
		objFilterPageObjects.clickOnApplyButton();
	}

	@Test(priority = 5)
	public void EmptyWislist() throws InterruptedException {
		Reporter.log("EmptyWislist test case started successfully");
		System.out.println("EmptyWislist!");
		objPDPageObject.clickFirstProductSearchResult();
		objPDPageObject.VerifyProductTitle();
		objPDPageObject.assertProductPrice();
		objPDPageObject.clickOnSaveButton();
		objPDPageObject.clickOnAddtoBag();
		objPDPageObject.selectSizeOfProduct();
		objPDPageObject.clickOnConfirmButton();
		objHomePageObjects.clickOnWishlistButton();
		objWishlistPageObject.CrossmarkButton1();
		objWishlistPageObject.clickOnCartFromWishlist();

	}
	
//	@Test(priority = 6)
//	public void AddMoreFromWishlist() throws InterruptedException {
//		objiOSGenericMethods.swipeDown(0, 2);
//		objBagPageObjects.clickToAddMoreFromWishlist();
//		objWishlistPageObject.clickOnMyntraLogo();
//		objHomePageObjects.clickOnBagIcon();
//	}
	
//	@Test(priority = 7)
//	public void PercentageDiscount() {
//		objBagPageObjects.clickOnApplyCouponButton();
//		objBagPageObjects.EnterCouponCode(objiOSGenericMethods.getValueByKeyiOSWeb(testName, "Coupon"));
//		objBagPageObjects.clickOnApplyButton();
//	}

	@Test(priority = 8)
	public void CartPage() throws InterruptedException {
		Reporter.log("CartPage test case started successfully");
		System.out.println("CartPage!");
		objBagPageObjects.clickOnPlaceOrder();
	}

	@Test(priority = 9)
	public void RemoveAddress() throws InvalidFileFormatException, IOException {
		Reporter.log("RemoveAddress test case started successfully");
		System.out.println("RemoveAddress!");
		objEdit_ChangeButtonPageObjects.clickOnEditChangeButton();
		objEdit_ChangeButtonPageObjects.clickOnRemoveButton();
		objAddressPageObjects.ClickOnPincodeAddress();
		objAddressPageObjects.enterPincone(objiOSGenericMethods.getValueByKeyiOSWeb(testName, "Pincode"));
		objAddressPageObjects.clickOnChoose();
		objAddressPageObjects.selectTownLocality(objiOSGenericMethods.getValueByKeyiOSWeb(testName, "Locality"));
		objAddressPageObjects.entername(objiOSGenericMethods.getValueByKeyiOSWeb(testName, "Name"));
		objAddressPageObjects.enteraddress(objiOSGenericMethods.getValueByKeyiOSWeb(testName, "Address"));
		objAddressPageObjects.enterMobileNumber(objiOSGenericMethods.getValueByKeyiOSWeb(testName, "MobileNumber"));
		objAddressPageObjects.clickOnHome();
		objAddressPageObjects.clickOnSaveAddress();
		objAddressPageObjects.VerifyAddressAdded();
	}

	@Test(priority = 10)
	public void PaymentWithNetBanking() throws InterruptedException, InvalidFileFormatException, IOException {
		Reporter.log("PaymentWithNetBanking test case started successfully");
		System.out.println("Payment With NetBanking!");
		objBagPageObjects.clickOnDemoId();
		objBagPageObjects.clickOnContinueButton();
		objBagPageObjects.clickOnDemoId();
		objBagPageObjects.assertPaymentPageTitle("Payment");
		objPaymentPageObjects.VerifyDeliveryAddress();
//		objPaymentPageObjects.clickOnNetBanking();
		objPaymentPageObjects.clickOnCCandDC();
	}

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
		 if(!(params.get("engine_").equalsIgnoreCase("TD")))
	        {
	                wd =   objMobileDrivers.launchAppiOS(params);
	        }
	        else
	        {
	                try {
	                     setUpTest();
	                     System.out.println("TestDroid Execution Started");
	                 } catch (Exception e) {
	                     // TODO Auto-generated catch block
	                     System.out.println("Error :: Please change suite parameter to run locally.");
	                 }
	                
	        }
		objiOSGenericMethods = new iOSGenericMethods(wd);
		wd.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		System.out.println("Test Case Name:" + testName);
		objAddressPageObjects = new AddressPageObjects(wd);
		objEdit_ChangeButtonPageObjects = new Edit_ChangeButtonPageObjects(wd);
		objBagPageObjects = new BagPageObjects(wd);
		objMenuPageObjects = new MenuPageObjects(wd);
		objPaymentPageObjects = new PaymentPageObjects(wd);
		objFilterPageObjects = new FilterPageObjects(wd);
		objPDPageObject = new PDPageObjects(wd);
		objPLPageObjects = new PLPageObjects(wd);
		objHamburgerPageObjects = new HamburgerPageObjects(wd);
		objHomePageObjects = new HomePageObjects(wd);
		objWishlistPageObject = new WishListPageObject(wd);
	}

	// @AfterTest
	public void quit() {
		 try {
			quitAppiumSession();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 wd.quit();
		System.out.println("=====================VEGASF_425_END=====================");
	}

}
