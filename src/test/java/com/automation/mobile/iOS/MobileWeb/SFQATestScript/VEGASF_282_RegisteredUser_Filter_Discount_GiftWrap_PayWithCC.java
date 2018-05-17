 package com.automation.mobile.iOS.MobileWeb.SFQATestScript;

import java.io.IOException;
import java.time.Duration;
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
 * @author 300019239 - Nitesh
 *
 *Steps to do Automation

* App(Android, IOS, PWA, Web App)	
* Email registered User	
* Home Page	
* Search (by clicking banner or nested banner on Home Page)	
* Filter	
* Discount	
* Show Similar	
* Gift wrap	
* Click for offer	
* Add New address - Office	
* Net Banking
 */
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

import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;



public class VEGASF_282_RegisteredUser_Filter_Discount_GiftWrap_PayWithCC extends BaseIOSTest{

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

	String testName = "VEGASF_282";

	@Test(priority = 1)
	public void LoginWithEmail() throws InterruptedException, InvalidFileFormatException, IOException{
		Reporter.log("LoginWithEmail test case started successfully");
		System.out.println("=====================VEGASF_282_START=====================");
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
//		objHomePageObjects.clickOnHamburgerButton();
//		objHamburgerPageObjects.clickOnMyAccount();
//		objHamburgerPageObjects.assertUserEmailID(objiOSGenericMethods.getValueByKeyiOSWeb(testName, "UserName"));
//		wd.navigate().back();
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
		System.out.println("SearchItem!");
		objHomePageObjects.clickOnSearchIcon();
		objHomePageObjects.enterSearchItem(objiOSGenericMethods.getValueByKeyiOSWeb(testName, "SearchItem"));
//		objPLPageObjects.verifySearchResult("Result Search Title");
//		objHomePageObjects.getSearchAutoSuggestList();
//		objPLPageObjects.VerifyProductDetails();
	}

//	@Test(priority = 4)
//	public void Filter_Discount(){
//		Reporter.log("Filter_Discount test case started successfully");
//		System.out.println("Filter_Discount!");
//		objFilterPageObjects.clickOnFilterButton();
//		objFilterPageObjects.clickOnDiscount();
//		objFilterPageObjects.clickOnFirstDiscountOption();
//		objFilterPageObjects.clickOnApplyButton();
//	}

	@Test(priority = 5)
	public void NavigateFromPLPtoPDP() throws InterruptedException {
		Reporter.log("NavigateFromPLPtoPDP test case started successfully");
		System.out.println("NavigateFromPLPtoPDP!");
//		objPDPageObject.clickFirstProductSearchResult();
		objPDPageObject.VerifyProductTitle();
		objPDPageObject.assertProductDiscount();
		objPDPageObject.assertProductPrice();
		objPDPageObject.clickOnAddtoBag();
		objPDPageObject.selectSizeOfProduct();
		objPDPageObject.clickOnConfirmButton();
	}

//	@Test(priority = 6)
//	public void ShowSimilarProducts(){
//		objiOSGenericMethods.swipeDown(1000,4);
//		objPDPageObject.getSimilarProductTitle();
//	}

	@Test(priority = 7)
	public void GoToCart() throws InterruptedException {
		Reporter.log("GoToCart test case started successfully");
		System.out.println("GoToCart!");
		objPDPageObject.clickOngoToBag();
		objBagPageObjects.VerifyProductTitle();
		objBagPageObjects.VerifySellingPrice();
		objBagPageObjects.VerifyDiscountedPrice();
	}

	@Test(priority = 8)
	public void GiftWrap() throws InterruptedException{
		Reporter.log("GiftWrap test case started successfully");
		System.out.println("GiftWrap!");
		objBagPageObjects.clickOnViewDetails();
		objBagPageObjects.clickOnGiftWrap();
		objBagPageObjects.enterRecipientName("Vegasf");
		objBagPageObjects.enterGiftMsg("myntra shopping");
		objBagPageObjects.enterSenderName("Test");
		objBagPageObjects.clickOnSaveGiftWrap();
		objBagPageObjects.clickOnViewDetails();
		objBagPageObjects.clickOnPlaceOrder();
	}
	
//	@Test(priority = 9)
//	public void Offers() {
//		objBagPageObjects.clickOnApplyCouponButton();
//		objBagPageObjects.EnterCouponCode("5676787878");
//		objBagPageObjects.clickOnApplyButton();
//	}

	@Test(priority = 10)
	public void AddNewAddress_Office() throws InterruptedException, InvalidFileFormatException, IOException{
		Reporter.log("AddNewAddress_Office test case started successfully");
		System.out.println("AddNewAddress_Office!");
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
	public void PaymentWithNetBanking() throws InterruptedException	{
		Reporter.log("PaymentWithNetBanking test case started successfully");
		System.out.println("PaymentWithNetBanking!");
		objBagPageObjects.clickOnDemoId();
		objBagPageObjects.clickOnContinueButton();
		objBagPageObjects.clickOnDemoId();
		objBagPageObjects.assertPaymentPageTitle("Payment");
		objPaymentPageObjects.VerifyDeliveryAddress();
		objPaymentPageObjects.clickOnCCandDC();
//		objPaymentPageObjects.clickOnNetBanking();
//		objPaymentPageObjects.clickFirstNetbankingOption();
//		objPaymentPageObjects.clickNetbankingPayNowButton();
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
		wd.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

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

	public void swipeDown(IOSDriver<IOSElement> wd, int duration, int noOfSwipes) {

		org.openqa.selenium.Dimension size = wd.manage().window().getSize();
		int starty = (int) (size.height * 0.85);
		int endy = (int) (size.height * 0.45);
		int width = size.width / 2;
		for (int i = 0; i < noOfSwipes; i++) {
			new TouchAction(wd).press(width, starty).waitAction(Duration.ofMillis(duration)).moveTo(width, endy)
			.release().perform();
		}
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
		System.out.println("=====================VEGASF_282_END=====================");
	}

}
