package com.automation.mobile.iOS.apps.SFQA_TestScript;

import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.ini4j.InvalidFileFormatException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.BaseIOSTest;
import com.automation.core.Common.AppiumServer;
import com.automation.core.Common.GlobalVariables;
import com.automation.core.Common.MobileDrivers;
import com.automation.core.mobile.iOS.iOSGenericMethods;
import com.automation.mobile.iOS.apps.ObjectRepository.Pages.WishListPage.WishlistPageObject;
import com.automation.mobile.iOS.apps.ObjectRepository.Pages.AddressPage.*;
import com.automation.mobile.iOS.apps.ObjectRepository.Pages.Assertions.*;
import com.automation.mobile.iOS.apps.ObjectRepository.Pages.CartPage.CartPageObject;
import com.automation.mobile.iOS.apps.ObjectRepository.Pages.HomePage2.HomePageObject2;
import com.automation.mobile.iOS.apps.ObjectRepository.Pages.ProfileLoginPage.*;
import com.automation.mobile.iOS.apps.ObjectRepository.Pages.PDPage.*;
import com.automation.mobile.iOS.apps.ObjectRepository.Pages.PLPage.*;
import com.automation.mobile.iOS.apps.ObjectRepository.Pages.PaymentPage.*;
import com.automation.mobile.iOS.apps.ObjectRepository.Pages.ProfileSignUpPage.ProfileSignUpPageObject;

import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;

/**
 * 
 * @author 300019240-Rahul Saxena
 *
 * 
 * 
 */

/*
 * 
 * 
 * 
 * Steps to do Automation
 * 
 * 
 * 
 * AApp(Android, IOS, PWA, Web App),Email registered User
 * 
 * Home Page
 * 
 * Search - Autosuggest - Brand Profile (Search for a brand like Nike, Roadster)
 * 
 * Add to Bag Click for best Price (Coupon)
 * 
 * Move to bag
 * 
 * Remove Product
 * 
 * Free Gift
 * 
 * Edit address
 * 
 * Check Eligibility
 * 
 * 
 * 
 * 
 * 
 */

public class VEGASF_228_RegisteredUser_BrandProfile_ClickForBestPrice_PayWithDC extends BaseIOSTest {

	GlobalVariables objGlobalVariables;
	iOSGenericMethods objiOSGenericMethods;
	AppiumServer objAppiumServer;
	ProfileSignUpPageObject objProfileSignUpPageObjects;
	PLPageObjects objPLPageObjets;
	PDPageObject objPDPageObject;
	WishlistPageObject objWishListPageObject;
	AddNewAdressPageObjects objAddNewAdressPageObjects;
	EditAdressPageObject objEditAdressPageObject;
	HomePageObject2 objHomePageObjects;
	MobileDrivers objMobileDrivers;
	CartPageObject objCartPageObject;
	AssertionPageObject objAssertionPageObject;
	//IOSDriver<IOSElement> wd;
	ProfileLoginPageObject objProfileLoginPageObject;
	PaymentPageObject objPaymentPageObjects;
	WishlistPageObject objWishlistPageObject;

	String TestName = "VEGASF_228";

	@Test(priority = 1)

	public void LoginInApp() throws InterruptedException, InvalidFileFormatException, IOException

	{
		try {
			objProfileLoginPageObject.clickOnOnBoardingCrossButton();
			System.out.println("On Boarding screen appeared and closed it");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("On Boarding screen did not appear");
		}
		objProfileLoginPageObject.clickOnProfileButton();
		objiOSGenericMethods.swipeDown(100, 7);
		objProfileLoginPageObject.clickOnLogOut();
		objProfileLoginPageObject.clickOnLogin();
		String email = iOSGenericMethods.getValueByKey(TestName, "UserName");
		String password = iOSGenericMethods.getValueByKey(TestName, "Password");
		objProfileLoginPageObject.loginInApp(email, password);
		objProfileLoginPageObject.clickOnLoginButton();
		objAssertionPageObject.verifyUserName();
		objiOSGenericMethods.acceptAlert();
		objiOSGenericMethods.swipeDown(100, 4);
		objProfileLoginPageObject.removeAddress();
	}

	@Test(priority = 2)

	public void SearchFreeGiftItem() throws InterruptedException, InvalidFileFormatException, IOException

	{
		objiOSGenericMethods.swipeDown(100, 1);
		objHomePageObjects.emptyBag();
		objHomePageObjects.clickOnHomeButton();
		objHomePageObjects.clickOnSearchButton();
		objHomePageObjects.enterSearchitem(objiOSGenericMethods.getValueByKey(TestName, "Search"));
	}

//	@Test(priority = 3)
//
//	public void selectProductPLPage() throws InterruptedException {
//
//		objAssertionPageObject.verifyPLPHeader();
//		objAssertionPageObject.verifyPLPProductCount();
//		objAssertionPageObject.verifyProductBrand();
//		objPLPageObjets.clickOnBrandNamePLP();
//	}

	@Test(priority = 4)

	public void addProductToBag() throws InterruptedException {
		// objAssertionPageObject.verifyDiscountedPrice();

		objPDPageObject.clickOnAddToBag();
		objPDPageObject.getSizeListinString(1);
		objPDPageObject.clickonDoneButton();
		objiOSGenericMethods.swipeDown(100, 3);
		objPDPageObject.clickOnBestPrice();
		objPDPageObject.clickOnGoToBag();

	}

	@Test(priority = 5)

	public void RemoveProductFromCartPage() throws InterruptedException, InvalidFileFormatException, IOException {
		objAssertionPageObject.verifyMyBag();
		objCartPageObject.emptyProduct();
		objCartPageObject.clickGoBackPage();
		objCartPageObject.clickGoBackPage();
		objPLPageObjets.clickOnSearchOnPLP();
		objAssertionPageObject.VerifyAutoSuggestionList();
		String search = objiOSGenericMethods.getValueByKey(TestName, "Search");
		objHomePageObjects.enterSearchitem(search);
		objPLPageObjets.clickOnBrandNamePLP();
		objPDPageObject.clickOnAddToBag();
		objPDPageObject.getSizeListinString(0);
		objPDPageObject.clickonDoneButton();
		objPDPageObject.clickOnGoToBag();

	}

	@Test(priority = 6)

	public void placeOrder() throws InterruptedException {
		objAssertionPageObject.verifyMyBag();
		objiOSGenericMethods.swipeDown(100, 7);
		objAssertionPageObject.verifyPriceDetails();
		objCartPageObject.clickOnplaceOrder();

	}

	@Test(priority = 7)

	public void EditAddress() throws InterruptedException, InvalidFileFormatException, IOException {
		objAssertionPageObject.verifyAddressHeaders();
		objAddNewAdressPageObjects.clickOnAddNewAddress();
		String pincode = iOSGenericMethods.getValueByKey(TestName, "Pincode");
		String locality = iOSGenericMethods.getValueByKey(TestName, "Locality");
		String name = iOSGenericMethods.getValueByKey(TestName, "Name");
		String address = iOSGenericMethods.getValueByKey(TestName, "Address");
		String mobile = iOSGenericMethods.getValueByKey(TestName, "Mobile");
		objAddNewAdressPageObjects.EnterContinueOrderAddingAddress(pincode, locality, name, address, mobile);
		objiOSGenericMethods.swipeDown(100, 1);
		objCartPageObject.clickOnContinueOrder();
	}


	@Test(priority = 8)

	public void paymentMode() throws InterruptedException

	{
		objAssertionPageObject.verifypaymenttext();
		objPaymentPageObjects.clickOnCOD();

	}

	@Parameters({ "deviceName_", "UDID_", "platformVersion_", "URL_", "appUrl_", "screenshotPath_", "engine_",
			"platform_" })
	@BeforeTest
	public void beforeTest(@Optional("TD") String deviceName_, @Optional("TD") String UDID_,
			@Optional("TD") String platformVersion_, @Optional("TD") String URL_, @Optional("TD") String appUrl_,
			@Optional("TD") String screenshotPath_, @Optional("TD") String engine_, @Optional("TD") String platform_)
			throws Exception {
		objGlobalVariables = new GlobalVariables();
		objAppiumServer = new AppiumServer();
		objMobileDrivers = new MobileDrivers();
		Map<String, String> params = new HashMap<String, String>();
		params.put("deviceName_", deviceName_);
		params.put("UDID_", UDID_);
		params.put("platformVersion_", platformVersion_);
		params.put("URL_", URL_);
		params.put("appUrl_", appUrl_);
		params.put("screenshotPath_", screenshotPath_);
		params.put("engine_", engine_);
		params.put("platform_", platform_);
		if (!(params.get("engine_").equalsIgnoreCase("TD")))
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
		wd.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		System.out.println("Test Name " + TestName);
		// Make sure that Page object object creation should be after this line
		objPLPageObjets = new PLPageObjects(wd);
		objPDPageObject = new PDPageObject(wd);
		objHomePageObjects = new HomePageObject2(wd);
		objAddNewAdressPageObjects = new AddNewAdressPageObjects(wd);
		objProfileLoginPageObject = new ProfileLoginPageObject(wd);
		objPaymentPageObjects = new PaymentPageObject(wd);
		objCartPageObject = new CartPageObject(wd);
		objAssertionPageObject = new AssertionPageObject(wd);
		objiOSGenericMethods = new iOSGenericMethods(wd);
		objEditAdressPageObject = new EditAdressPageObject(wd);
		objPaymentPageObjects = new PaymentPageObject(wd);
		objWishlistPageObject = new WishlistPageObject(wd);
		objCartPageObject = new CartPageObject(wd);
		objAssertionPageObject = new AssertionPageObject(wd);
		objiOSGenericMethods = new iOSGenericMethods(wd);
		objEditAdressPageObject = new EditAdressPageObject(wd);
		objPaymentPageObjects = new PaymentPageObject(wd);
		objCartPageObject = new CartPageObject(wd);

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
		}
}