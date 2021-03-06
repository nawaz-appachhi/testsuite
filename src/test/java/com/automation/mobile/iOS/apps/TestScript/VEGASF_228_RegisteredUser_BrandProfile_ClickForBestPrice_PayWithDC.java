package com.automation.mobile.iOS.apps.TestScript;

import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.ini4j.InvalidFileFormatException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
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

public class VEGASF_228_RegisteredUser_BrandProfile_ClickForBestPrice_PayWithDC extends GlobalVariables {

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
	IOSDriver<IOSElement> iDriver;
	ProfileLoginPageObject objProfileLoginPageObject;
	PaymentPageObject objPaymentPageObjects;
	WishlistPageObject objWishlistPageObject;

	String TestName = "VEGASF_228";

	@Test(priority = 1)

	public void LoginInApp() throws InterruptedException, InvalidFileFormatException, IOException

	{

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

	@Test(priority = 3)

	public void selectProductPLPage() throws InterruptedException {

		objAssertionPageObject.verifyPLPHeader();
		objAssertionPageObject.verifyPLPProductCount();
		objAssertionPageObject.verifyProductBrand();
		objPLPageObjets.clickOnBrandNamePLP();
	}

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

	@Parameters({ "deviceName_", "UDID_", "platformVersion_", "URL_", "appUrl_", "screenshotPath_" })
	@BeforeTest
	public void beforeTest(String deviceName_, String UDID_, String platformVersion_, String URL_, String appUrl_,
			String screenshotPath_) throws InterruptedException {
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
		iDriver = objMobileDrivers.launchAppiOS(params);
		iDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		System.out.println("Test Name " + TestName);
		// Make sure that Page object object creation should be after this line
		objPLPageObjets = new PLPageObjects(iDriver);
		objPDPageObject = new PDPageObject(iDriver);
		objHomePageObjects = new HomePageObject2(iDriver);
		objAddNewAdressPageObjects = new AddNewAdressPageObjects(iDriver);
		objProfileLoginPageObject = new ProfileLoginPageObject(iDriver);
		objPaymentPageObjects = new PaymentPageObject(iDriver);
		objCartPageObject = new CartPageObject(iDriver);
		objAssertionPageObject = new AssertionPageObject(iDriver);
		objiOSGenericMethods = new iOSGenericMethods(iDriver);
		objEditAdressPageObject = new EditAdressPageObject(iDriver);
		objPaymentPageObjects = new PaymentPageObject(iDriver);
		objWishlistPageObject = new WishlistPageObject(iDriver);
		objCartPageObject = new CartPageObject(iDriver);
		objAssertionPageObject = new AssertionPageObject(iDriver);
		objiOSGenericMethods = new iOSGenericMethods(iDriver);
		objEditAdressPageObject = new EditAdressPageObject(iDriver);
		objPaymentPageObjects = new PaymentPageObject(iDriver);
		objCartPageObject = new CartPageObject(iDriver);

	}
	 @AfterTest
		public void quit() {
			iDriver.quit();
		}
}