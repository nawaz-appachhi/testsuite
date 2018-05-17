package com.automation.mobile.iOS.apps.TestScript;

/**
 * @author 300021276
 * 
 * Facebook registered user
Home Page
List page to PDP navigation
Save to wishlist
Check Delivery Option
Move to bag - cant able to identify the object
Remove Product
Buy One get One- test data not there
Add New address - Office
Payment : Online + LP
 */
import org.testng.annotations.Test;

import org.testng.annotations.Test;
import org.testng.annotations.Test;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.ini4j.InvalidFileFormatException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
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
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;

/*
 * Shopper:

Facebook registered user
Home Page
List page to PDP navigation
Save to wishlist
Check Delivery Option
Move to bag
Remove Product
Buy One get One
Add New address - Office
Payment : Online + LP

 */
public class VEGASF_365_FB_User_CheckDeliveryOption_RemoveProduct_BOGO_AddNewAddress_Office_PayWithCC extends BaseIOSTest{

	GlobalVariables objGlobalVariables;
	AppiumServer objAppiumServer;
	ProfileSignUpPageObject objProfileSignUpPageObject;
	PLPageObjects objPLPageObjets;
	PDPageObject objPDPageObject;
	AddNewAdressPageObjects objAddAddressPageObjects;
	HomePageObject2 objHomePageObject2;
	MobileDrivers objMobileDrivers;
	//IOSDriver<IOSElement> wd;
	ProfileLoginPageObject objProfileLoginPageObject;
	AssertionPageObject objAssertionPageObject;
	PaymentPageObject objPaymentPageObject;
	WishlistPageObject objWishlistPageObject;
	iOSGenericMethods objiOSGenericMethods;
	CartPageObject objCartPageObject;

	String TestName = "VEGASF_365";

	//@Test(priority = 1)
	public void LoginWithFacebook() throws InterruptedException, InvalidFileFormatException, IOException {
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
		objProfileLoginPageObject.clickOnFaceBook();
		Thread.sleep(5000);
		Set<String> contextNames = wd.getContextHandles();
		for (String contextName : contextNames) {
			if (contextName.contains("WEBVIEW"))
				wd.context(contextName);
			System.out.println(contextName);
			System.out.println("Context --> " + wd.getContext());
		}

		try {

			WebElement emailid = wd.findElement(By.cssSelector("#m_login_email"));
			WebElement pass = wd.findElement(By.cssSelector("#m_login_password"));
			WebElement loginButton = wd.findElement(By.cssSelector("#u_0_5"));
			WebElement continueLogin = wd.findElement(By.cssSelector("#u_0_9"));
			 WebElement continueLogin2 = wd.findElement(By.cssSelector("#u_0_3"));
			Thread.sleep(2000);
			String email = iOSGenericMethods.getValueByKey(TestName, "UserName");
			String password = iOSGenericMethods.getValueByKey(TestName, "Password");
			if (emailid.isDisplayed()) {
				emailid.sendKeys(email);
				pass.sendKeys(password);
				loginButton.click(); 
			}

			if (continueLogin.isDisplayed()) {
				objiOSGenericMethods.fluentWait(continueLogin);
				continueLogin.click();
			}else if(continueLogin2.isDisplayed()) {
				//objiOSGenericMethods.fluentWait(continueLogin);
				continueLogin2.click();
			}
		} catch (Exception e) {
			System.out.println("user can continue with face book login!");
		}
		try {
			WebElement cont = wd.findElement(By.cssSelector("#u_0_3"));
			objiOSGenericMethods.fluentWait(cont);
			cont.click();
			
		} catch (Exception e) {
			System.out.println("User is already login, No continue button to click!");
		}
		Set<String> contextNames2 = wd.getContextHandles();
		for (String contextName : contextNames2) {
			if (contextName.contains("NATIVE_APP"))
				wd.context(contextName);
			System.out.println(contextName);
			System.out.println("Context --> " + wd.getContext());
		}
		objAssertionPageObject.verifyUserName();

	}

	
	@Test(priority = 1)
	public void LoginInApp() throws InterruptedException, InvalidFileFormatException, IOException {
		objProfileLoginPageObject.clickOnProfileButton();
		objiOSGenericMethods.swipeDown(100, 8);
		objProfileLoginPageObject.clickOnLogOut();
		objProfileLoginPageObject.clickOnLogin();
		String email = iOSGenericMethods.getValueByKey(TestName, "UserName");
		String password = iOSGenericMethods.getValueByKey(TestName, "Password");
		objProfileLoginPageObject.loginInApp(email, password);
		objProfileLoginPageObject.clickOnLoginButton();
		objAssertionPageObject.verifyUserName();
		objiOSGenericMethods.acceptAlert();
		objiOSGenericMethods.swipeDown(100, 6);
		objProfileLoginPageObject.removeAddress();
	}
	
	
	@Test(priority = 2)
	public void RemoveAddress() throws InterruptedException, InvalidFileFormatException, IOException {
		objiOSGenericMethods.swipeDown(0, 6);
		objProfileLoginPageObject.removeAddress();
	}

	@Test(priority = 3)
	public void SearchItem() throws InterruptedException, InvalidFileFormatException, IOException {
		objHomePageObject2.emptyBag();
		objHomePageObject2.clickOnHomeButton();
		objHomePageObject2.clickOnSearchButton();
//		objAssertionPageObject.VerifyAutoSuggestionList();
		String search = objiOSGenericMethods.getValueByKey(TestName, "Search");
		objHomePageObject2.enterSearchitem(search);
	}

	@Test(priority = 4)
	public void ListPageToPDP() throws InterruptedException, InvalidFileFormatException, IOException {
		objAssertionPageObject.verifyProductname();
		objAssertionPageObject.verifyPLPHeader();
		objAssertionPageObject.verifyPLPProductCount();
		objPLPageObjets.clickOnFirstproductofPLP();
	}

	@Test(priority = 5)
	public void SaveProduct() throws InterruptedException {
		objPDPageObject.clickOnSaveButton();
		// objPDPageObject.getSizeListinString(1);
		objPDPageObject.clickOnAddToBag();
		objPDPageObject.getSizeListinString(0);
		objPDPageObject.clickonDoneButton();
		objPDPageObject.clickOnGoToBag();
	}

//	@Test(priority = 6)
//	public void CheckForDeliveryOption() throws InterruptedException {
//	
//		objiOSGenericMethods.swipeDown(1000, 16);
////		objAssertionPageObject.verifyMoreSimilarProducts();
//		objPDPageObject.clickMoreSimilarProducts();
//		objPLPageObjets.clickOnFirstproductofPLP();
//		objPDPageObject.clickOnSaveButton();
//		objPDPageObject.clickOnAddToBag();
//		objPDPageObject.getSizeListinString(0);
//		objPDPageObject.clickonDoneButton();
//		objPDPageObject.clickOnGoToBag();
//
//	}

	@Test(priority = 6)
	public void RemoveProduct() throws InterruptedException {
//		objCartPageObject.clickOnremoveButton();
//		objCartPageObject.ClickonRemoveItemPopup();
		objAssertionPageObject.verifyMyBag();
//		objAssertionPageObject.verifyProductTitleCartPage();
//		objAssertionPageObject.veirfyCartPageWishlist();
		objiOSGenericMethods.swipeDown(100, 7);
		objCartPageObject.clickOnplaceOrder();

	}

	@Test(priority = 7)
	public void AddNewAddress_Office() throws InterruptedException, InvalidFileFormatException, IOException {
		objAssertionPageObject.verifyAddressHeaders();
		objAddAddressPageObjects.clickOnAddNewAddress();
		String pincode = iOSGenericMethods.getValueByKey(TestName, "Pincode");
		String locality = iOSGenericMethods.getValueByKey(TestName, "Locality");
		String name = iOSGenericMethods.getValueByKey(TestName, "Name");
		String address = iOSGenericMethods.getValueByKey(TestName, "Address");
		String mobile = iOSGenericMethods.getValueByKey(TestName, "Mobile");
		objAddAddressPageObjects.EnterContinueOrderAddingOfficeAddress(pincode, locality, name, address, mobile);
		objiOSGenericMethods.swipeDown(0, 2);
		objCartPageObject.clickOnViewDetails();
		objCartPageObject.clickOnContinueOrder();

	}

	@Test(priority = 8)
	public void PaymentWithPhonePe() throws InterruptedException, InvalidFileFormatException, IOException {
		objAssertionPageObject.verifypaymenttext();
		objiOSGenericMethods.swipeDown(100, 1);
		objAssertionPageObject.verifyDeliverTo();
		objiOSGenericMethods.SwipeUp(100, 1);
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
		wd.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		System.out.println("Test Name " + TestName);
		objPLPageObjets = new PLPageObjects(wd);
		objPDPageObject = new PDPageObject(wd);
		objWishlistPageObject = new WishlistPageObject(wd);
		objPLPageObjets = new PLPageObjects(wd);
		objProfileLoginPageObject = new ProfileLoginPageObject(wd);
		objCartPageObject = new CartPageObject(wd);
		objAssertionPageObject = new AssertionPageObject(wd);
		objPaymentPageObject = new PaymentPageObject(wd);
		objHomePageObject2 = new HomePageObject2(wd);
		objAddAddressPageObjects = new AddNewAdressPageObjects(wd);
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
