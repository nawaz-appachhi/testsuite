package com.automation.mobile.iOS.apps.TestScript;

/**
 * @author 300021276
 * 
 * App(Android, IOS, PWA, Web App)
Email registered User
Home Page
Search (by clicking banner or nested banner on Home Page)
Filter
Add to bag
Move to bag
Place Order
Buy One get One
View Details
Myntra Credit
 */
import org.testng.annotations.Test;
import org.testng.annotations.Test;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.ini4j.InvalidFileFormatException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.BaseIOSTest;
import com.automation.core.Common.AppiumServer;
import com.automation.core.Common.GlobalVariables;
import com.automation.core.Common.MobileDrivers;
import com.automation.core.mobile.iOS.iOSGenericMethods;
import com.automation.mobile.iOS.apps.ObjectRepository.Pages.AddressPage.AddNewAdressPageObjects;
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

public class VEGASF_102_RegisteredUser_PriceFilter_BuyFromWishlist_PayingWithCoD extends BaseIOSTest{

	GlobalVariables objGlobalVariables;
	AppiumServer objAppiumServer;
	ProfileSignUpPageObject objProfileSignUpPageObject;
	PLPageObjects objPLPageObjets;
	PDPageObject objPDPageObject;
	AddNewAdressPageObjects objAddNewAdressPageObjects;
	HomePageObject2 objHomePageObject2;
	MobileDrivers objMobileDrivers;
	//IOSDriver<IOSElement> iDriver;
	ProfileLoginPageObject objProfileLoginPageObject;
	AssertionPageObject objAssertionPageObject;
	PaymentPageObject objPaymentPageObject;
	WishlistPageObject objWishlistPageObject;
	CartPageObject objCartPageObject;
	iOSGenericMethods objiOSGenericMethods;

	String ExcelPath;
	String TestName = "VEGASF_102";

	@Test(priority = 1)
	public void LoginInApp() throws InterruptedException, InvalidFileFormatException, IOException {
		try {
			objProfileLoginPageObject.clickOnOnBoardingCrossButton();
			System.out.println("On Boarding screen appeared and closed it");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("On Boarding screen did not appear");
		}
		objProfileLoginPageObject.clickOnProfileButton();
		objiOSGenericMethods.swipeDown(0, 7);
		objProfileLoginPageObject.clickOnLogOut();
		objProfileLoginPageObject.clickOnLogin();
		objProfileLoginPageObject.loginInApp(iOSGenericMethods.getValueByKey(TestName, "UserName"),
				iOSGenericMethods.getValueByKey(TestName, "Password"));
		objProfileLoginPageObject.clickOnLoginButton();
		// objiOSGenericMethods.acceptAlert();
		objiOSGenericMethods.swipeDown(100, 4);
		objProfileLoginPageObject.removeAddress();

	}

	@Test(priority = 2)
	public void SearchItem() throws InterruptedException, InvalidFileFormatException, IOException {
		objHomePageObject2.emptyBag();
		objHomePageObject2.clickOnHomeButton();
		objHomePageObject2.clickOnSearchButton();
		objHomePageObject2.enterSearchitem(objiOSGenericMethods.getValueByKey(TestName, "Search"));
		// objHomePageObject2.clickOnBigBanner(0);
		// objHomePageObject2.clickOnNestedBanner(0);
		// objPLPageObjets.clickOnBrandNamePLP();

	}

//	@Test(priority = 3)
//	public void PLPage() throws InterruptedException {
//		objPLPageObjets.clickOnFilter();
//		objAssertionPageObject.verifyDiscount();
//		objPLPageObjets.clickOnFilterDiscount();
//		objPLPageObjets.clickOnSelectFirstFilterDiscount();
//		objPLPageObjets.clickOnApplyDiscount();
//		objAssertionPageObject.verifyProductname();
//		objAssertionPageObject.verifyPLPHeader();
//		objAssertionPageObject.verifyPLPProductCount();
//		objPLPageObjets.clickOnBrandNamePLP();
//	}

	@Test(priority = 4)
	public void PDPage() throws InterruptedException {
		// objAssertionPageObject.verifyStrickPrice();
		objPDPageObject.clickOnSaveButton();
		objPDPageObject.clickOnAddToBag();
		objPDPageObject.getSizeListinString(0);
		objPDPageObject.clickonDoneButton();
		objPDPageObject.clickOnGoToBag();
		objPLPageObjets.clickOnOkAndTouch();
		objAssertionPageObject.verifyMyBag();
		objAssertionPageObject.veirfyCartPageWishlist();
		objAssertionPageObject.verifyProductTitleCartPage();
	}

	@Test(priority = 5)
	public void CheckoutPage() throws InterruptedException {

		objiOSGenericMethods.swipeDown(100, 7);
		objAssertionPageObject.verifyPriceDetails();
		objCartPageObject.clickOnplaceOrder();
	}

	@Test(priority = 6)
	public void AddNewAddress() throws InterruptedException, InvalidFileFormatException, IOException {
		objAssertionPageObject.verifyAddressHeaders();
		objAddNewAdressPageObjects.clickOnAddNewAddress();
		String pincode = iOSGenericMethods.getValueByKey(TestName, "Pincode");
		String locality = iOSGenericMethods.getValueByKey(TestName, "Locality");
		String name = iOSGenericMethods.getValueByKey(TestName, "Name");
		String address = iOSGenericMethods.getValueByKey(TestName, "Address");
		String mobile = iOSGenericMethods.getValueByKey(TestName, "Mobile");
		objAddNewAdressPageObjects.EnterContinueOrderAddingOfficeAddress(pincode, locality, name, address, mobile);
		objiOSGenericMethods.swipeDown(100, 1);
		objCartPageObject.clickOnContinueOrder();

	}

	@Test(priority = 7)
	public void NetBanking() throws InterruptedException, InvalidFileFormatException, IOException {
		objiOSGenericMethods.swipeDown(100, 3);
		objAssertionPageObject.verifyDeliverTo();
		objiOSGenericMethods.SwipeUp(100, 3);
		objPaymentPageObject.clickOnCOD();
	}

	@Parameters({ "deviceName_", "UDID_", "platformVersion_", "URL_", "appUrl_", "screenshotPath_", "engine_",
			"platform_" })
	@BeforeTest
	public void beforeTest(@Optional("TD") String deviceName_, @Optional("TD") String UDID_,
			@Optional("TD") String platformVersion_, @Optional("TD") String URL_, @Optional("TD") String appUrl_,
			@Optional("TD") String screenshotPath_, @Optional("TD") String engine_, @Optional("TD") String platform_)
			throws Exception {
		objGlobalVariables = new GlobalVariables();
		// objExcelUtilities = new ExcelUtils();
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
		objProfileLoginPageObject = new ProfileLoginPageObject(wd);
		objPLPageObjets = new PLPageObjects(wd);
		objPDPageObject = new PDPageObject(wd);
		objHomePageObject2 = new HomePageObject2(wd);
		objWishlistPageObject = new WishlistPageObject(wd);
		objPaymentPageObject = new PaymentPageObject(wd);
		objCartPageObject = new CartPageObject(wd);
		objiOSGenericMethods = new iOSGenericMethods(wd);
		objAssertionPageObject = new AssertionPageObject(wd);
		objAddNewAdressPageObjects = new AddNewAdressPageObjects(wd);
	}

	@AfterTest
	public void quit() {
		wd.quit();
	}

}
