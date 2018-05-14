package com.automation.mobile.iOS.apps.TestScript;

import java.io.IOException;
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
import com.automation.mobile.iOS.apps.ObjectRepository.Categories.HomeAndLivingCategoriesPageObject;
import com.automation.mobile.iOS.apps.ObjectRepository.Categories.KidsCategoriesPageObjects;
import com.automation.mobile.iOS.apps.ObjectRepository.Categories.MenCategoriesPageObjects;
import com.automation.mobile.iOS.apps.ObjectRepository.Categories.WomenCategoriesPageObjects;
import com.automation.mobile.iOS.apps.ObjectRepository.Pages.AddressPage.AddNewAdressPageObjects;
import com.automation.mobile.iOS.apps.ObjectRepository.Pages.AddressPage.EditAdressPageObject;
import com.automation.mobile.iOS.apps.ObjectRepository.Pages.Assertions.AssertionPageObject;
import com.automation.mobile.iOS.apps.ObjectRepository.Pages.CartPage.CartPageObject;
import com.automation.mobile.iOS.apps.ObjectRepository.Pages.HomePage2.HomePageObject2;
import com.automation.mobile.iOS.apps.ObjectRepository.Pages.PDPage.PDPageObject;
import com.automation.mobile.iOS.apps.ObjectRepository.Pages.PLPage.PLPageObjects;
import com.automation.mobile.iOS.apps.ObjectRepository.Pages.PaymentPage.PaymentPageObject;
import com.automation.mobile.iOS.apps.ObjectRepository.Pages.ProfileLoginPage.ProfileLoginPageObject;
import com.automation.mobile.iOS.apps.ObjectRepository.Pages.ProfileSignUpPage.ProfileSignUpPageObject;
import com.automation.mobile.iOS.apps.ObjectRepository.Pages.WishListPage.WishlistPageObject;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;

/*
 * Steps to do Automation

App(Android, IOS, PWA, Web App)
Email registered User
Home Page
Search (using menu item e.g. Men -> Topwear - T-Shirts
Filter
Select Size
Move to bag
Size & Quantity
Free Gift
View Details
Credit/Debit Card

 */

/**
 * 
 * @author 300021276 /madhu
 * Stabilized by @author 300019221 Aravindanath
 *
 */
public class VEGASF_235_RegisteredUser_BrowseMenu_SelectSize_ChangeSizeAtCheckout_PayWithCC {

	GlobalVariables objGlobalVariables;
	AppiumServer objAppiumServer;
	ProfileSignUpPageObject objProfileSignUpPageObject;
	PLPageObjects objPLPageObjets;
	PDPageObject objPDPageObject;
	AddNewAdressPageObjects objAddNewAdressPageObjects;
	HomePageObject2 objHomePageObject2;
	MobileDrivers objMobileDrivers;
	IOSDriver<IOSElement> iDriver;
	ProfileLoginPageObject objProfileLoginPageObject;
	AssertionPageObject objAssertionPageObject;
	PaymentPageObject objPaymentPageObject;
	WishlistPageObject objWishlistPageObject;
	CartPageObject objCartPageObject;
	iOSGenericMethods objiOSGenericMethods;
	EditAdressPageObject objEditAdressPageObject;
	MenCategoriesPageObjects objMenCategoriesPageObjects;
	WomenCategoriesPageObjects objWomenCategoriesPageObjects;
	KidsCategoriesPageObjects objKidsCategoriesPageObjects;
	HomeAndLivingCategoriesPageObject objHomeAndLivingCategoriesPageObject;

	String TestName = "VEGASF_235";

	@Test(priority = 1)
	public void LoginInApp() throws InterruptedException, InvalidFileFormatException, IOException {
		objProfileLoginPageObject.clickOnProfileButton();
		objiOSGenericMethods.swipeDown(100, 7);
		objProfileLoginPageObject.clickOnLogOut();
		objProfileLoginPageObject.clickOnLogin();
		String email = iOSGenericMethods.getValueByKey(TestName, "UserName");
		String password = iOSGenericMethods.getValueByKey(TestName, "Password");
		objProfileLoginPageObject.loginInApp(email, password);
		objProfileLoginPageObject.clickOnLoginButton();
		objAssertionPageObject.verifyUserName();
//		objiOSGenericMethods.acceptAlert();
		objiOSGenericMethods.swipeDown(100, 4);
		objProfileLoginPageObject.removeAddress();
	}

	@Test(priority = 2)
	public void SearchItem() throws InterruptedException, InvalidFileFormatException, IOException {
		objHomePageObject2.emptyBag();
		objHomePageObject2.clickOnHomeButton();
//		objMenCategoriesPageObjects.clickOnCategories();
//		objMenCategoriesPageObjects.clickOnMen();
//		objMenCategoriesPageObjects.clickOnMenTopwear();
//		objMenCategoriesPageObjects.clickOnMenTshirt();
//		objMenCategoriesPageObjects.clickOnProduct(0);
//		objPLPageObjets.clickOnOkAndTouch();
		objHomePageObject2.clickOnSearchButton();
		String search = objiOSGenericMethods.getValueByKey(TestName, "Search");
		objHomePageObject2.enterSearchitem(search);
	}

	@Test(priority = 3)
	public void PLPage() throws InterruptedException {
		objPLPageObjets.clickOnFilter();
		objAssertionPageObject.verifyDiscount();
		objPLPageObjets.clickOnFilterDiscount();
		objPLPageObjets.clickOnSelectFirstFilterDiscount();
		objPLPageObjets.clickOnApplyDiscount();
		objAssertionPageObject.verifyProductname();
		objAssertionPageObject.verifyPLPHeader();
		objAssertionPageObject.verifyPLPProductCount();
		objPLPageObjets.clickOnBrandNamePLP();
	}

	@Test(priority = 4)
	public void PDPage() throws InterruptedException, InvalidFileFormatException, IOException {
		// objAssertionPageObject.verifyStrickPrice();
		objPDPageObject.clickOnAddToBag();
		objPDPageObject.getSizeListinString(0);
		objPDPageObject.clickonDoneButton();
		objPDPageObject.clickOnGoToBag();
		objCartPageObject.clickGoBackPage();
		objCartPageObject.clickGoBackPage();
		objHomePageObject2.clickOnSearchButton();
		objAssertionPageObject.VerifyAutoSuggestionList();
		String search = objiOSGenericMethods.getValueByKey(TestName, "Search2");
		objHomePageObject2.enterSearchitem(search);
		objPLPageObjets.clickOnBrandNamePLP();
		objPDPageObject.clickOnAddToBag();
		objPDPageObject.getSizeListinString(0);
		objPDPageObject.clickonDoneButton();
		objPDPageObject.clickOnGoToBag();

	}

	@Test(priority = 5)
	public void ShoppingBagPage() throws InterruptedException {
		objAssertionPageObject.verifyMyBag();
		objiOSGenericMethods.swipeDown(100, 7);
		objAssertionPageObject.verifyPriceDetails();
		objAddNewAdressPageObjects.clickOnPlaceOrder();

	}
	
	

	@Test(priority = 6)
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
		objCartPageObject.clickOnViewDetails();
		objCartPageObject.clickOnContinueOrder();
	}


	@Test(priority = 7)
	public void paymentPage() throws InterruptedException {
		objAssertionPageObject.verifypaymenttext();
		objiOSGenericMethods.swipeDown(100, 4);
		objAssertionPageObject.verifyDeliverTo();
		objiOSGenericMethods.SwipeUp(100, 4);
		objPaymentPageObject.clickOnCreditCardPayment();

	}

	@Parameters({ "deviceName_","UDID_","platformVersion_", "URL_", "appUrl_", "screenshotPath_" })
	@BeforeTest
	public void beforeTest(String deviceName_, String UDID_, String platformVersion_, String URL_, String appUrl_, String screenshotPath_) throws InterruptedException {
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
		objPLPageObjets = new PLPageObjects(iDriver);
		objPDPageObject = new PDPageObject(iDriver);
		objWishlistPageObject = new WishlistPageObject(iDriver);
		objProfileLoginPageObject = new ProfileLoginPageObject(iDriver);
		objCartPageObject = new CartPageObject(iDriver);
		objAssertionPageObject = new AssertionPageObject(iDriver);
		objEditAdressPageObject = new EditAdressPageObject(iDriver);
		objPaymentPageObject = new PaymentPageObject(iDriver);
		objiOSGenericMethods = new iOSGenericMethods(iDriver);
		objHomePageObject2 = new HomePageObject2(iDriver);
		objMenCategoriesPageObjects = new MenCategoriesPageObjects(iDriver);
		objWomenCategoriesPageObjects = new WomenCategoriesPageObjects(iDriver);
		objKidsCategoriesPageObjects = new KidsCategoriesPageObjects(iDriver);
		objHomeAndLivingCategoriesPageObject = new HomeAndLivingCategoriesPageObject(iDriver);
		objAddNewAdressPageObjects = new AddNewAdressPageObjects(iDriver);
	}
	 @AfterTest
		public void quit() {
			iDriver.quit();
		}
}
