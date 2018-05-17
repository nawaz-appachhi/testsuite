package com.automation.mobile.iOS.apps.SFQA_TestScript;

import org.testng.annotations.Test;
import org.testng.annotations.Test;
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

import com.BaseIOSTest;
import com.automation.core.Common.AppiumServer;
//import com.automation.core.Common.ExcelUtils;
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
import com.automation.mobile.iOS.apps.ObjectRepository.Pages.ProfileSignUpPage.ProfileSignUpPageObject;
import com.automation.mobile.iOS.apps.ObjectRepository.Pages.WishListPage.WishlistPageObject;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;

public class VEGASF_91_RegisteredUser_ShowSimilarProducts_BOGO_PayWithDC  extends BaseIOSTest{


	GlobalVariables objGlobalVariables;
	AppiumServer objAppiumServer;
	ProfileSignUpPageObject objProfileSignUpPageObject;
	PLPageObjects objPLPageObjets;
	PDPageObject objPDPageObject;
	AddNewAdressPageObjects objAddNewAdressPageObjects;
	HomePageObject2 objHomePageObject2;
	MobileDrivers objMobileDrivers;
	//IOSDriver<IOSElement> wd;
	ProfileLoginPageObject objProfileLoginPageObject;
	AssertionPageObject objAssertionPageObject;
	PaymentPageObject objPaymentPageObject;
	WishlistPageObject objWishlistPageObject;
	CartPageObject objCartPageObject;
	iOSGenericMethods objiOSGenericMethods;
	EditAdressPageObject objEditAdressPageObject;
	
	String ExcelPath;
	String TestName = "VEGASF_91";

	@Test(priority=1)
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
		objiOSGenericMethods.swipeDown(0, 7);
		objProfileLoginPageObject.clickOnLogOut();
		objProfileLoginPageObject.clickOnLogin();
		objProfileLoginPageObject.loginInApp(iOSGenericMethods.getValueByKey(TestName, "UserName"),
				iOSGenericMethods.getValueByKey(TestName, "Password"));
		objProfileLoginPageObject.clickOnLoginButton();
		objAssertionPageObject.verifyUserName();
//		objiOSGenericMethods.acceptAlert();
		objiOSGenericMethods.swipeDown(100, 6);
		objProfileLoginPageObject.removeAddress();
	}
	@Test(priority=2)
	public void SearchItem() throws InterruptedException, InvalidFileFormatException, IOException
	{
		objHomePageObject2.emptyBag();
		objHomePageObject2.clickOnHomeButton();
		objHomePageObject2.clickOnSearchButton();
		objAssertionPageObject.VerifyAutoSuggestionList();
		objHomePageObject2.setSearchBox(iOSGenericMethods.getValueByKey(TestName, "Search"));	
//		objiOSGenericMethods.acceptAlert();
		
	}
	@Test(priority=3)
	public void PLPPage() throws InterruptedException, InvalidFileFormatException, IOException
	{
	
//		objAssertionPageObject.verifyPLPHeader();
//		objAssertionPageObject.verifyPLPProductCount();
//		objPLPageObjets.clickOnFirstproductofPLP();
		objPDPageObject.clickOnSaveButton();
		objPDPageObject.clickOnAddToBag();
		objPDPageObject.getSizeListinString(0);
		objPDPageObject.clickonDoneButton();
		objPDPageObject.clickOnGoToBag();
//		objAssertionPageObject.verifySelectSize();
//		objiOSGenericMethods.swipeDown(0, 14);
//		objPDPageObject.clickOnViewSimilar();
//		objPLPageObjets.clickOnFirstproductofPLP();
//		
	}
	  
//	@Test(priority=4)
//	public void PDPage() throws InterruptedException {
//		objPDPageObject.clickOnAddToBag();
//		objPDPageObject.getSizeListinString(0);
//		objPDPageObject.clickonDoneButton();
//		objPDPageObject.clickOnGoToBag();
//		
//		
//	}
	@Test(priority=4)
	public void ShoppingBag() throws InterruptedException, InvalidFileFormatException, IOException {
		objiOSGenericMethods.SwipeUp(100, 3);
		objCartPageObject.ClickOnApplyCoupon();
		objAssertionPageObject.verifyApplyCouponHeaders();
		//String coupon = objiOSGenericMethods.getValueByKey(TestName, "Coupon");
		//objCartPageObject.enterCoupon(coupon);
		objCartPageObject.clickOnApplyButton();
		objiOSGenericMethods.swipeDown(100, 4);
	//	objAssertionPageObject.verifyPriceDetails();
		objCartPageObject.clickOnplaceOrder();
	}
	
	
	
	@Test(priority = 5)
	public void addressPage() throws InterruptedException, InvalidFileFormatException, IOException {

		objAssertionPageObject.verifyAddressHeaders();
		objAddNewAdressPageObjects.clickOnAddNewAddress();
		String pincode = iOSGenericMethods.getValueByKey(TestName, "Pincode");
		String locality = iOSGenericMethods.getValueByKey(TestName, "Locality");
		String name = iOSGenericMethods.getValueByKey(TestName, "Name");
		String address = iOSGenericMethods.getValueByKey(TestName, "Address");
		String mobile = iOSGenericMethods.getValueByKey(TestName, "Mobile");
		objAddNewAdressPageObjects.EnterContinueOrderAddingOfficeAddress(pincode, locality, name, address, mobile);
		objiOSGenericMethods.swipeDown(100, 1);
		objCartPageObject.clickOnViewDetails();
		objCartPageObject.clickOnContinueOrder();
	}

	@Test(priority = 6)
	public void PaymentByWallet() throws InterruptedException {

		objAssertionPageObject.verifypaymenttext();
		objPaymentPageObject.clickOnCreditCardPayment();
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
		wd.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		System.out.println("Test Name " + TestName);
		objHomePageObject2 = new HomePageObject2(wd);
		objAddNewAdressPageObjects = new AddNewAdressPageObjects(wd);
		objProfileLoginPageObject = new ProfileLoginPageObject(wd);
		objPaymentPageObject = new PaymentPageObject(wd);
		objCartPageObject = new CartPageObject(wd);
		objWishlistPageObject = new WishlistPageObject(wd);
		objAssertionPageObject = new AssertionPageObject(wd);
		objiOSGenericMethods = new iOSGenericMethods(wd);
		objEditAdressPageObject = new EditAdressPageObject(wd);
		objPLPageObjets = new PLPageObjects(wd);
		objPDPageObject = new PDPageObject(wd);

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
