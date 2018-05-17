
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
import com.automation.core.Common.GlobalVariables;
import com.automation.core.Common.MobileDrivers;
import com.automation.core.mobile.iOS.iOSGenericMethods;
import com.automation.mobile.iOS.apps.ObjectRepository.Pages.AddressPage.AddNewAdressPageObjects;
import com.automation.mobile.iOS.apps.ObjectRepository.Pages.AddressPage.EditAdressPageObject;
import com.automation.mobile.iOS.apps.ObjectRepository.Pages.Assertions.AssertionPageObject;
import com.automation.mobile.iOS.apps.ObjectRepository.Pages.CartPage.CartPageObject;
import com.automation.mobile.iOS.apps.ObjectRepository.Pages.HomePage2.HomePageObject2;
import com.automation.mobile.iOS.apps.ObjectRepository.Pages.PDPage.PDPageObject;
import com.automation.mobile.iOS.apps.ObjectRepository.Pages.PLPage.PLPageObjects;
import com.automation.mobile.iOS.apps.ObjectRepository.Pages.PaymentPage.PaymentPageObject;
import com.automation.mobile.iOS.apps.ObjectRepository.Pages.ProfileLoginPage.ProfileLoginPageObject;
import com.automation.mobile.iOS.apps.ObjectRepository.Pages.WishListPage.WishlistPageObject;

import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;

/*
 * Steps to do Automation
App(Android, IOS, PWA, Web App)
Email registered User
Home Page
List page to PDP navigation
Top buttons
Select Size
Select Size
Contact Us
Buy One get One	View Details ( Deviation in the flow due to test data)
Credit/Debit Card

 */

/**
 * 
 * @author 300019221 / Aravindanath
 *
 */

public class VEGASF_230_RegisteredUser_SizeSensitive_BOGO_PayWithCoD extends BaseIOSTest{

	GlobalVariables objGlobalVariables;
	iOSGenericMethods objiOSGenericMethods;
	PLPageObjects objPLPageObjets;
	PDPageObject objPDPageObject;
	AddNewAdressPageObjects objAddNewAdressPageObjects;
	HomePageObject2 objHomePageObjects;
	MobileDrivers objMobileDrivers;
	//IOSDriver<IOSElement> wd;
	ProfileLoginPageObject objLoginPageObject;
	WishlistPageObject objWishlistPageObject;
	CartPageObject objCartPage;
	PaymentPageObject objPaymentPageObjects;
	EditAdressPageObject objEditAddressObjects;
	AssertionPageObject objAssertionPageObject;
	String TestName = "VEGASF_230";

	@Test(priority = 1)

	public void LoginInApp() throws InterruptedException, InvalidFileFormatException, IOException

	{
		try {
			objLoginPageObject.clickOnOnBoardingCrossButton();
			System.out.println("On Boarding screen appeared and closed it");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("On Boarding screen did not appear");
		}
		objLoginPageObject.clickOnProfileButton();
		objiOSGenericMethods.swipeDown(100, 7);
		objLoginPageObject.clickOnLogOut();
		objLoginPageObject.clickOnLogin();
		String email = iOSGenericMethods.getValueByKey(TestName, "UserName");
		String password = iOSGenericMethods.getValueByKey(TestName, "Password");
		objLoginPageObject.loginInApp(email, password);
		objLoginPageObject.clickOnLoginButton();
		objAssertionPageObject.verifyUserName();
		objiOSGenericMethods.acceptAlert();
		objiOSGenericMethods.swipeDown(100, 4);
		objLoginPageObject.removeAddress();
	}

	@Test(priority = 2)

	public void SearchItem() throws InterruptedException, InvalidFileFormatException, IOException {
		objHomePageObjects.emptyBag();
		objHomePageObjects.clickOnHomeButton();
		objHomePageObjects.clickOnSearchButton();
		String search = objiOSGenericMethods.getValueByKey(TestName, "Search");
		objHomePageObjects.enterSearchitem(search);
//		objAssertionPageObject.VerifyAutoSuggestionList();
	}

//	@Test(priority = 3)
//
//	public void productListingPage() throws InterruptedException {
//		objAssertionPageObject.verifyProductname();
//		objAssertionPageObject.verifyPLPHeader();
//		objAssertionPageObject.verifyPLPProductCount();
//		objPLPageObjets.clickOnBrandNamePLP();
//	}

	@Test(priority = 4)

	public void productDiscriptionPage() throws InterruptedException {

		objPDPageObject.clickOnAddToBag();
		objPDPageObject.getSizeListinString(0);
		objPDPageObject.clickonDoneButton();
		objPDPageObject.clickOnGoToBag();
	}

	@Test(priority = 5)

	public void cartPage() throws InterruptedException {
		objAssertionPageObject.verifyMyBag();
		objiOSGenericMethods.swipeDown(100, 7);
		objAssertionPageObject.verifyPriceDetails();
		objCartPage.clickOnplaceOrder();

	}

	@Test(priority = 6)
	public void address() throws InterruptedException, InvalidFileFormatException, IOException {
		objAssertionPageObject.verifyAddressHeaders();
		objAddNewAdressPageObjects.clickOnAddNewAddress();
		String pincode = iOSGenericMethods.getValueByKey(TestName, "Pincode");
		String locality = iOSGenericMethods.getValueByKey(TestName, "Locality");
		String name = iOSGenericMethods.getValueByKey(TestName, "Name");
		String address = iOSGenericMethods.getValueByKey(TestName, "Address");
		String mobile = iOSGenericMethods.getValueByKey(TestName, "Mobile");
		objAddNewAdressPageObjects.EnterContinueOrderAddingAddress(pincode, locality, name, address, mobile);
		objiOSGenericMethods.swipeDown(100, 1);
		objCartPage.clickOnContinueOrder();
	}

	@Test(priority = 7)
	public void payment() throws InterruptedException {
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
		wd.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		System.out.println("Test Name " + TestName);
		// Make sure that Page object object creation should be after this line
		objPLPageObjets = new PLPageObjects(wd);
		objPDPageObject = new PDPageObject(wd);
		objHomePageObjects = new HomePageObject2(wd);
		objAddNewAdressPageObjects = new AddNewAdressPageObjects(wd);
		objEditAddressObjects = new EditAdressPageObject(wd);
		objLoginPageObject = new ProfileLoginPageObject(wd);
		objPaymentPageObjects = new PaymentPageObject(wd);
		objWishlistPageObject = new WishlistPageObject(wd);
		objCartPage = new CartPageObject(wd);
		objAssertionPageObject = new AssertionPageObject(wd);
		objiOSGenericMethods = new iOSGenericMethods(wd);
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
