package com.automation.mobile.iOS.MobileWeb.TestScript;

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
/**
 * @author  Monika Rani
 * 
 * Steps to do automation

* App(Android, IOS, PWA, Web App)	
* Email registered User	
* Home Page	
* Search (using menu item e.g. Men -> Topwear - T-Shirts	
* Save	
* Click for best Price (Coupon)	
* Size Chart	
* Contact Us	
* Click for offer	
* Add New address - Home
* Manual GC +Online
 * 
 */
import org.testng.annotations.Test;

import com.BaseIOSTest;
import com.automation.core.Common.AppiumServerUtils;
import com.automation.core.Common.GlobalVariables;
import com.automation.core.Common.MobileDrivers;
import com.automation.core.mobile.iOS.iOSGenericMethods;
import com.automation.mobile.iOS.MobileWeb.ObjectRepository.AddressPageObjects.AddressPageObjects;
import com.automation.mobile.iOS.MobileWeb.ObjectRepository.AddressPageObjects.Edit_ChangeButtonPageObjects;
import com.automation.mobile.iOS.MobileWeb.ObjectRepository.Bag.BagPageObjects;
import com.automation.mobile.iOS.MobileWeb.ObjectRepository.CategoriesObjects.MenCategoriesPageObjects;
import com.automation.mobile.iOS.MobileWeb.ObjectRepository.HomeObjects.HamburgerPageObjects;
import com.automation.mobile.iOS.MobileWeb.ObjectRepository.HomeObjects.HomePageObjects;
import com.automation.mobile.iOS.MobileWeb.ObjectRepository.HomeObjects.MenuPageObjects;
import com.automation.mobile.iOS.MobileWeb.ObjectRepository.PDPageObjects.PDPageObjects;
import com.automation.mobile.iOS.MobileWeb.ObjectRepository.PLPageObjects.FilterPageObjects;
import com.automation.mobile.iOS.MobileWeb.ObjectRepository.PLPageObjects.PLPageObjects;
import com.automation.mobile.iOS.MobileWeb.ObjectRepository.PaymentObjects.PaymentPageObjects;
import com.automation.mobile.iOS.MobileWeb.ObjectRepository.WishList.WishListPageObject;

public class VEGASF_270_RegisteredUser_BrowseMenuItems_ClickForOffer_PayWithCoD extends BaseIOSTest{

	GlobalVariables objGlobalVariables;
	AppiumServerUtils objAppiumServerUtils;

	AddressPageObjects objAddressPageObjects;
	Edit_ChangeButtonPageObjects objEdit_ChangeButtonPageObjects;
	BagPageObjects objBagPageObjects;
	MenuPageObjects objMenuPageObjects;
	PaymentPageObjects objPaymentPageObjects;
	MenCategoriesPageObjects objMenCategoriesPageObjects;
	FilterPageObjects objFilterPageObjects;
	MobileDrivers objMobileDrivers;
	PDPageObjects objPDPageObject;
	PLPageObjects objPLPageObjects;
	HamburgerPageObjects objHamburgerPageObjects;
	HomePageObjects objHomePageObjects;
	//IOSDriver<IOSElement> wd;
	iOSGenericMethods objiOSGenericMethods;
	WishListPageObject objWishListPageObject;

	String testName = "VEGASF_270";

	@Test(priority = 1)
	public void LoginWithEmail() throws InterruptedException, InvalidFileFormatException, IOException{
		Reporter.log("LoginWithEmail test case started successfully");
		System.out.println("=====================VEGASF_270_START=====================");
		System.out.println("LoginWithEmail");
		objHomePageObjects.clickOnHamburgerButton();
		if (objHamburgerPageObjects.isLoggedIn())
		{
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
	public void resetData() throws InterruptedException	{
		Reporter.log("resetData test case started successfully");
		System.out.println("Reset Bag, Wishlist & Address!");
		objBagPageObjects.resetBag();
		objWishListPageObject.resetWishlist();
		objAddressPageObjects.resetAddress();
	}

	@Test(priority = 3)
	public void SearchItem() throws InterruptedException	{
		Reporter.log("SearchItem test case started successfully");
		System.out.println("SearchItem!");
		objHomePageObjects.clickOnHamburgerButton();
		objMenCategoriesPageObjects.clickOnmen();
		objMenCategoriesPageObjects.clickOntopWare();
		objMenCategoriesPageObjects.clickOnactiveTShirt();
		objPLPageObjects.VerifyProductDetails();
	}

	@Test(priority = 4)
	public void Save()	{
		Reporter.log("Save test case started successfully");
		System.out.println("Save!");
		objPLPageObjects.clickToSaveToWishlist();
	}

	@Test(priority = 5)
	public void CheckSizeChart() throws InterruptedException	{
		Reporter.log("CheckSizeChart test case started successfully");
		System.out.println("CheckSizeChart!");
		objPDPageObject.clickFirstProductSearchResult();
		objPDPageObject.VerifyProductTitle();
		objPDPageObject.assertProductDiscount();
		objPDPageObject.assertProductPrice();
		objPDPageObject.clickOnAddtoBag();
		objPDPageObject.clickOnSizeChart();
		objPDPageObject.clickOnCloseSizeChartButton();
		objPDPageObject.selectSizeOfProduct();
		objPDPageObject.clickOnConfirmButton();
	}

	@Test(priority = 6)
	public void CheckOnBestPriceLink() throws InterruptedException	{
		Reporter.log("CheckOnBestPriceLink test case started successfully");
		System.out.println("CheckOnBestPriceLink!");
		objiOSGenericMethods.swipeDown(100,3);
		objPDPageObject.clickOnBestPrice();
		objPDPageObject.clickOngoToBag();
		objBagPageObjects.assertBagPageTitle("Bag");
		objBagPageObjects.VerifyProductTitle();
		objBagPageObjects.VerifySellingPrice();
		objBagPageObjects.VerifyDiscountedPrice();
	}

	@Test(priority = 7)
	public void Offers()	{
		Reporter.log("Offers test case started successfully");
		System.out.println("Offers!");
//		objBagPageObjects.clickOnApplyCouponButton();
//		objBagPageObjects.EnterCouponCode("5676787878");
//		objBagPageObjects.clickOnApplyButton();
		objBagPageObjects.clickOnPlaceOrder();
	}

	@Test(priority = 8)
	public void AddNewAddress() throws InterruptedException, InvalidFileFormatException, IOException{
		Reporter.log("AddNewAddress test case started successfully");
		System.out.println("AddNewAddress!");
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

	@Test(priority = 9)
	public void Payement() throws InterruptedException	{
		Reporter.log("Payement test case started successfully");
		System.out.println("Payement!");
		objBagPageObjects.clickOnContinueButton();
		objBagPageObjects.clickOnDemoId();
		objBagPageObjects.assertPaymentPageTitle("Payment");
		objPaymentPageObjects.VerifyDeliveryAddress();
//		objPaymentPageObjects.clickOnGiftcard();
//		objPaymentPageObjects.enterGiftCardNumber("67276237329");
//		objPaymentPageObjects.enterGiftCardpin("6237");
//		objPaymentPageObjects.clickOnGiftCardApplyButton();
//		objPaymentPageObjects.clickOnChangePaymentMode();
//		objPaymentPageObjects.clickOnNetBanking();
//		objPaymentPageObjects.clickFirstNetbankingOption();
//		objPaymentPageObjects.clickNetbankingPayNowButton();
		objPaymentPageObjects.ClickOnCOD();
	}

	@Parameters({ "browserName_","deviceName_","UDID_","platformVersion_", "URL_", "appUrl_", "screenshotPath_","engine_", "platform_" })
	@BeforeTest
	public void beforeTest(@Optional("TD") String browserName_, @Optional("TD") String deviceName_, @Optional("TD") String UDID_, @Optional("TD") String platformVersion_, @Optional("TD") String URL_, @Optional("TD") String appUrl_, @Optional("TD") String screenshotPath_, @Optional("TD") String engine_, @Optional("TD") String platform_) throws Exception {
		objGlobalVariables = new GlobalVariables();
		objAppiumServerUtils = new AppiumServerUtils();
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
		wd.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		objAddressPageObjects = new AddressPageObjects(wd);
		objMenCategoriesPageObjects = new MenCategoriesPageObjects(wd);
		objEdit_ChangeButtonPageObjects = new Edit_ChangeButtonPageObjects(wd);
		objBagPageObjects = new BagPageObjects(wd);
		objMenuPageObjects = new MenuPageObjects(wd);
		objPaymentPageObjects = new PaymentPageObjects(wd);
		objFilterPageObjects = new FilterPageObjects(wd);
		objPDPageObject = new PDPageObjects(wd);
		objPLPageObjects = new PLPageObjects(wd);
		objHamburgerPageObjects = new HamburgerPageObjects(wd);
		objHomePageObjects = new HomePageObjects(wd);
		objWishListPageObject = new WishListPageObject(wd);
	}

	@AfterTest
	public void quit() {
		 try {
			quitAppiumSession();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 wd.quit();
		System.out.println("=====================VEGASF_270_END=====================");
	}
}
