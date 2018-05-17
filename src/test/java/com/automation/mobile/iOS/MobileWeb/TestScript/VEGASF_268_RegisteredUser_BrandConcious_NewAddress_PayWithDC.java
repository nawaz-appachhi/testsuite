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
 * @author  Lata Naik
 * 
 * Steps to do Automation

* App(Android, IOS, PWA, Web App)	
* Email registered User	
* Home Page	
* Search - Autosuggest - Brand Profile (Search for a brand like Nike, Roadster)	
* Add to Bag	
* Similar Products	
* Size Chart	
* Move to wishlist	
* Percentage Discount	
* Add New address - Office	
* Credit/Debit Card
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
import com.automation.mobile.iOS.MobileWeb.ObjectRepository.HomeObjects.HamburgerPageObjects;
import com.automation.mobile.iOS.MobileWeb.ObjectRepository.HomeObjects.HomePageObjects;
import com.automation.mobile.iOS.MobileWeb.ObjectRepository.HomeObjects.MenuPageObjects;
import com.automation.mobile.iOS.MobileWeb.ObjectRepository.PDPageObjects.PDPageObjects;
import com.automation.mobile.iOS.MobileWeb.ObjectRepository.PLPageObjects.FilterPageObjects;
import com.automation.mobile.iOS.MobileWeb.ObjectRepository.PLPageObjects.PLPageObjects;
import com.automation.mobile.iOS.MobileWeb.ObjectRepository.PaymentObjects.PaymentPageObjects;
import com.automation.mobile.iOS.MobileWeb.ObjectRepository.WishList.WishListPageObject;

public class VEGASF_268_RegisteredUser_BrandConcious_NewAddress_PayWithDC extends BaseIOSTest{

	GlobalVariables objGlobalVariables;
	AppiumServerUtils objAppiumServerUtils;
	AddressPageObjects objAddressPageObjects;
	Edit_ChangeButtonPageObjects objEdit_ChangeButtonPageObjects;
	BagPageObjects objBagPageObjects;
	MenuPageObjects objMenuPageObjects;
	PaymentPageObjects objPaymentPageObjects;
	FilterPageObjects objFilterPageObjects;
	MobileDrivers objMobileDrivers;
	PDPageObjects objPDPageObject;
	PLPageObjects objPLPageObjects;
	WishListPageObject objWishListPageObjects;
	HamburgerPageObjects objHamburgerPageObjects;
	HomePageObjects objHomePageObjects;
	//IOSDriver<IOSElement> wd;
	iOSGenericMethods objiOSGenericMethods;
	WishListPageObject objWishListPageObject;

	String testName = "VEGASF_268";

	@Test(priority = 1)
	public void LoginWithEmail() throws InterruptedException, InvalidFileFormatException, IOException{
		Reporter.log("LoginWithEmail test case started successfully");
		System.out.println("=====================VEGASF_268_START=====================");
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
	public void AddToBag() throws InterruptedException{
		Reporter.log("AddToBag test case started successfully");
		System.out.println("AddToBag");
		objPDPageObject.clickFirstProductSearchResult();
		objPDPageObject.VerifyProductTitle();
		objPDPageObject.assertProductDiscount();
		objPDPageObject.assertProductPrice();
		objPDPageObject.clickOnAddtoBag();
		
	}

	@Test(priority = 5)
	public void CheckSizeChart() throws InterruptedException	{
		Reporter.log("CheckSizeChart test case started successfully");
		System.out.println("CheckSizeChart!");
		objPDPageObject.clickOnSizeChart();
		objPDPageObject.clickOnCloseSizeChartButton();
		objPDPageObject.selectSizeOfProduct();
		objPDPageObject.clickOnConfirmButton();
		
	}

//	@Test(priority = 6)
//	public void SimilarProduct() throws InterruptedException	{
//	Reporter.log("SimilarProduct test case started successfully");
//	System.out.println("SimilarProduct");
//		objPDPageObject.clickOnMoreProducts();
//		objPDPageObject.getSimilarProductTitle();
//	}

	@Test(priority = 7)
	public void MoveToWishlist() throws InterruptedException	{
		Reporter.log("MoveToWishlist test case started successfully");
		System.out.println("MoveToWishlist!");
		objPDPageObject.clickCartIcon();
		objBagPageObjects.assertBagPageTitle("Bag");
//		objBagPageObjects.clickOnMoveToWishlist();
//		objBagPageObjects.clickOnMyntraLogo();
		objBagPageObjects.ClickMoveToWishListLink();
		objHomePageObjects.clickOnWishlistButton();
		objWishListPageObject.VerifyWishlistPageTitle();
		objWishListPageObject.VerifyProductTitle();
		objWishListPageObject.VerifyDiscountedPrice();
		objWishListPageObjects.ClickOnMoveToBag();
		objWishListPageObject.ClickSizeButtons();
		objWishListPageObjects.ClickOnDoneButton();
		objWishListPageObject.clickOnCartFromWishlist();
	}

	@Test(priority = 8)
	public void PercentageDiscount() throws InterruptedException {
		Reporter.log("PercentageDiscount test case started successfully");
		System.out.println("PercentageDiscount!");
		objBagPageObjects.VerifyProductTitle();
		objBagPageObjects.VerifySellingPrice();
		objBagPageObjects.VerifyDiscountedPrice();
		objBagPageObjects.clickOnPlaceOrder();
	}

	@Test(priority = 9)
	public void AddNewAddressOffice() throws InterruptedException, InvalidFileFormatException, IOException{
		Reporter.log("AddNewAddressOffice test case started successfully");
		System.out.println("AddNewAddressOffice!");
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

	@Test(priority = 10)
	public void PaymentWithCreditDebitCard() throws InterruptedException	{
		Reporter.log("PaymentWithCreditDebitCard test case started successfully");
		System.out.println("PaymentWithCreditDebitCard!");
		objBagPageObjects.clickOnContinueButton();
		objBagPageObjects.clickOnDemoId();
		objBagPageObjects.assertPaymentPageTitle("Payment"); 
		objPaymentPageObjects.VerifyDeliveryAddress();
		objPaymentPageObjects.clickOnCCandDC();
		
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
		wd.manage().timeouts().implicitlyWait(20,TimeUnit.SECONDS);
		objAddressPageObjects = new AddressPageObjects(wd);
		objEdit_ChangeButtonPageObjects = new Edit_ChangeButtonPageObjects(wd);
		objBagPageObjects = new BagPageObjects(wd);
		objMenuPageObjects = new MenuPageObjects(wd);
		objPaymentPageObjects = new PaymentPageObjects(wd);
		objWishListPageObjects = new WishListPageObject(wd);
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
		System.out.println("=====================VEGASF_268_END=====================");
	}

}
