package com.automation.mobile.iOS.apps.TestScript;

import java.io.IOException;
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

/**
 * Stabilize by @author 300019221 Aravindanath
 * 
 * @author 300021276 /madhu
 *
 */

/**
 * Steps to do Automation App(Android, IOS, PWA, Web App) Email registered User
 * Home Page Free Gift List page to PDP navigation Filter Click for best Price
 * (Coupon) Show Similar Move to wishlist Add New address - Home Mynt+ COD
 * 
 */
public class VEGASF_238_RegisteredUser_Filter_ShowSimilar_AddHomeAddress_PayWithCoD extends BaseIOSTest{

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
	EditAdressPageObject objEditAdressPageObject;
	MenCategoriesPageObjects objMenCategoriesPageObjects;
	WomenCategoriesPageObjects objWomenCategoriesPageObjects;
	KidsCategoriesPageObjects objKidsCategoriesPageObjects;
	HomeAndLivingCategoriesPageObject objHomeAndLivingCategoriesPageObject;

	String TestName = "VEGASF_238";

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
		objiOSGenericMethods.swipeDown(100, 8);
		objProfileLoginPageObject.clickOnLogOut();
		objProfileLoginPageObject.clickOnLogin();
		String email = iOSGenericMethods.getValueByKey(TestName, "UserName");
		String password = iOSGenericMethods.getValueByKey(TestName, "Password");
		objProfileLoginPageObject.loginInApp(email, password);
		objProfileLoginPageObject.clickOnLoginButton();
		objAssertionPageObject.verifyUserName();
//		objiOSGenericMethods.acceptAlert();
		objiOSGenericMethods.swipeDown(100, 6);
		objProfileLoginPageObject.removeAddress();
	}

	@Test(priority = 2)
	public void SearchItem() throws InterruptedException, InvalidFileFormatException, IOException {
		objHomePageObject2.emptyBag();
		objHomePageObject2.clickOnHomeButton();
		objHomePageObject2.clickOnSearchButton();
		objAssertionPageObject.VerifyAutoSuggestionList();
		String search = objiOSGenericMethods.getValueByKey(TestName, "Search");
		objHomePageObject2.enterSearchitem(search);
//		objiOSGenericMethods.acceptAlert();
	}

	@Test(priority = 3)
	public void PLPage() throws InterruptedException, InvalidFileFormatException, IOException {
		objPLPageObjets.clickOnFilter();
		objAssertionPageObject.verifyGenderFilter();
		objPLPageObjets.clickOnFilterGender();
		objPLPageObjets.clickOnFirstFilterGender();
		objPLPageObjets.clickOnApplyDiscount();
		objAssertionPageObject.verifyProductname();
		objAssertionPageObject.verifyPLPHeader();
		objAssertionPageObject.verifyPLPProductCount();
//		objPLPageObjets.clickOnFirstproductofPLP();
		objPLPageObjets.clickOnBrandNamePLP();
	}

	@Test(priority = 4)
	public void PDPage() throws InterruptedException, InvalidFileFormatException, IOException {
//		objPDPageObject.clickOnAddToBag();
		// objAssertionPageObject.verifyStrickPrice();
		objiOSGenericMethods.swipeDown(100, 3);
		// objAssertionPageObject.verifyTapForBestPrice();
		objPDPageObject.clickOnBestPrice();
		// objPDPageObject.clickOnAddToBag();
//		objiOSGenericMethods.swipeDown(100, 10);
		/**
		 * @author 300019221 Modified this method //
		 *         objPDPageObject.clickOnViewSimilar(); as it was not working as
		 *         excepted.
		 */

//		objPDPageObject.clickMoreSimilarProducts();
//		objAssertionPageObject.verifyPLPHeader();
//		objAssertionPageObject.verifyPLPProductCount();
//		objPLPageObjets.clickOnBrandNamePLP();
		objPDPageObject.clickOnAddToBag();
		objPDPageObject.getSizeListinString(0);
		objPDPageObject.clickonDoneButton();
		objPDPageObject.clickOnGoToBag();

	}

	@Test(priority = 5)
	public void cartPage() throws InterruptedException, InvalidFileFormatException, IOException {
		objAssertionPageObject.verifyMyBag();
//		objAssertionPageObject.veirfyCartPageWishlist();
//		objAssertionPageObject.verifyProductTitleCartPage();
//		objiOSGenericMethods.swipeDown(100, 3);
//		objPDPageObject.clickOnMoveToWishList();
		objiOSGenericMethods.swipeDown(100, 8);
		objAssertionPageObject.verifyPriceDetails();
		objAddNewAdressPageObjects.clickOnPlaceOrder();
	}

	@Test(priority = 6)
	public void AddressPage() throws InterruptedException, InvalidFileFormatException, IOException {
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

	@Test(priority = 7)
	public void PaymentPage() throws InterruptedException, InvalidFileFormatException, IOException {
		objAssertionPageObject.verifypaymenttext();
		objiOSGenericMethods.swipeDown(100, 4);
		objAssertionPageObject.verifyDeliverTo();
		objiOSGenericMethods.SwipeUp(100, 4);
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
		wd.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		System.out.println("Test Name " + TestName);
		objPLPageObjets = new PLPageObjects(wd);
		objPDPageObject = new PDPageObject(wd);
		objWishlistPageObject = new WishlistPageObject(wd);
		objProfileLoginPageObject = new ProfileLoginPageObject(wd);
		objCartPageObject = new CartPageObject(wd);
		objAssertionPageObject = new AssertionPageObject(wd);
		objEditAdressPageObject = new EditAdressPageObject(wd);
		objPaymentPageObject = new PaymentPageObject(wd);
		objiOSGenericMethods = new iOSGenericMethods(wd);
		objHomePageObject2 = new HomePageObject2(wd);
		objAddNewAdressPageObjects = new AddNewAdressPageObjects(wd);

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