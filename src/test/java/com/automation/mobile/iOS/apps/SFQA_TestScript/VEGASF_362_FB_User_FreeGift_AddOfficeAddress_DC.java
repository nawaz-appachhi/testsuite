package com.automation.mobile.iOS.apps.SFQA_TestScript;

import org.testng.annotations.Test;

import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.ini4j.InvalidFileFormatException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import com.automation.core.Common.AppiumServer;
import com.automation.core.Common.GlobalVariables;
import com.automation.core.Common.MobileDrivers;
import com.automation.core.mobile.iOS.iOSGenericMethods;
import com.automation.mobile.iOS.apps.ObjectRepository.Categories.MenCategoriesPageObjects;
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

/**
 * @author 300021275 - Lata
 * 
 *         Shopper: # Facebook registered user # Home Page # Search (using menu
 *         item e.g. Men -> Topwear - T-Shirts # Filter Product # View Similar
 *         Products # Move to bag # Place Order # Free Gift # Add New address -
 *         Office # Payment : EMI
 * 
 */

public class VEGASF_362_FB_User_FreeGift_AddOfficeAddress_DC {

	GlobalVariables objGlobalVariables;
	AppiumServer objAppiumServer;
	ProfileSignUpPageObject objProfileSignUpPageObject;
	PLPageObjects objPLPageObjets;
	PDPageObject objPDPageObject;
	HomePageObject2 objHomePageObject;
	MobileDrivers objMobileDrivers;
	IOSDriver<IOSElement> iDriver;
	ProfileLoginPageObject objProfileLoginPageObject;
	AssertionPageObject objAssertionPageObject;
	PaymentPageObject objPaymentPageObject;
	MenCategoriesPageObjects objMenCategoriesPageObjects;
	WishlistPageObject objWishlistPageObject;
	EditAdressPageObject objEditAdressPageObject;
	CartPageObject objCartPageObject;
	iOSGenericMethods objiOSGenericMethods;
	AddNewAdressPageObjects objAddNewAdressPageObjects;

	String TestName = "VEGASF_362";

	@Test(priority = 1)
	public void LoginWithFacebook() throws InterruptedException, InvalidFileFormatException, IOException {
		objProfileLoginPageObject.clickOnProfileButton();
		objiOSGenericMethods.swipeDown(100, 7);
		objProfileLoginPageObject.clickOnLogOut();
//		objProfileLoginPageObject.clickOnFaceBook();
//		Thread.sleep(5000);
//		Set<String> contextNames = iDriver.getContextHandles();
//		for (String contextName : contextNames) {
//			if (contextName.contains("WEBVIEW"))
//				iDriver.context(contextName);
//			System.out.println(contextName);
//			System.out.println("Context --> " + iDriver.getContext());
//		}
////		objiOSGenericMethods.acceptAlert();
//		try {
//
//			WebElement emailid = iDriver.findElement(By.cssSelector("#m_login_email"));
//			WebElement pass = iDriver.findElement(By.cssSelector("#m_login_password"));
//			WebElement loginButton = iDriver.findElement(By.cssSelector("#u_0_5"));
//			WebElement continueLogin = iDriver.findElement(By.cssSelector("#u_0_9"));
//			 WebElement continueLogin2 = iDriver.findElement(By.cssSelector("#u_0_3"));
//			Thread.sleep(2000);
//			String email = iOSGenericMethods.getValueByKey(TestName, "UserName");
//			String password = iOSGenericMethods.getValueByKey(TestName, "Password");
//			if (emailid.isDisplayed()) {
//				emailid.sendKeys(email);
//				pass.sendKeys(password);
//				loginButton.click(); 
//			}
//
//			if (continueLogin.isDisplayed()) {
//				objiOSGenericMethods.fluentWait(continueLogin);
//				continueLogin.click();
//			}else if(continueLogin2.isDisplayed()) {
//				//objiOSGenericMethods.fluentWait(continueLogin);
//				continueLogin2.click();
//			}
//		} catch (Exception e) {
//			System.out.println("user can continue with face book login!");
//		}
//		try {
//			WebElement cont = iDriver.findElement(By.cssSelector("#u_0_3"));
//			objiOSGenericMethods.fluentWait(cont);
//			cont.click();
//		} catch (Exception e) {
//			System.out.println("User is already login, No continue button to click!");
//		}
//		Set<String> contextNames2 = iDriver.getContextHandles();
//		for (String contextName : contextNames2) {
//			if (contextName.contains("NATIVE_APP"))
//				iDriver.context(contextName);
//			System.out.println(contextName);
//			System.out.println("Context --> " + iDriver.getContext());
//		}
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
	public void Categories() throws InterruptedException, InvalidFileFormatException, IOException {
		objHomePageObject.emptyBag();
//		objMenCategoriesPageObjects.clickOnCategories();
//		objMenCategoriesPageObjects.clickOnMen();
//		objMenCategoriesPageObjects.clickOnMenBottomWear();
//		objiOSGenericMethods.swipeDown(100, 1);
//		objMenCategoriesPageObjects.clickOnTrackPants();
		objHomePageObject.clickOnHomeButton();
		objHomePageObject.clickOnSearchButton();
		objAssertionPageObject.VerifyAutoSuggestionList();
		String search = objiOSGenericMethods.getValueByKey(TestName, "Search");
		objHomePageObject.enterSearchitem(search);

	}

	@Test(priority = 3)
	public void Filter_Newest() throws InterruptedException, InvalidFileFormatException, IOException {
//		objPLPageObjets.clickOnFilter();
//		objPLPageObjets.clickOnFilterDiscount();
//		objPLPageObjets.clickOnDiscountPercent();
//		objPLPageObjets.clickOnApplyDiscount();
//		objPLPageObjets.clickOnBrandNamePLP();
		objPDPageObject.clickOnAddToBag();
		objPDPageObject.getSizeListinString(0);
		objPDPageObject.clickonDoneButton();
		//objAssertionPageObject.verifySelectSize();
		objPDPageObject.clickOnGoToBag();
	}

//	@Test(priority = 4)
//	public void CheckSimilarProducts() throws InterruptedException {
////		objiOSGenericMethods.swipeDown(0, 16);
////		objPDPageObject.clickOnSimilarProduct();
////		objPDPageObject.clickOnViewSimilar();
////		objPLPageObjets.clickOnBrandNamePLP();
////		objPDPageObject.clickOnAddToBag();
////		objPDPageObject.getSizeListinString(1);
////		objPDPageObject.clickonDoneButton();
////		objPDPageObject.clickOnGoToBag();
//		objAssertionPageObject.verifyProductTitleCartPage();
//	}

	@Test(priority = 4)
	public void PlaceOrder() throws InterruptedException {
		objAssertionPageObject.verifyMyBag();
		objiOSGenericMethods.swipeDown(100, 7);
		objAssertionPageObject.verifyPriceDetails();
		objCartPageObject.clickOnplaceOrder();
	}

	@Test(priority = 5)
	public void AddNewAddress_Office() throws InterruptedException, InvalidFileFormatException, IOException {
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

	@Test(priority = 6)
	public void PaymenWithEMI() throws InterruptedException, InvalidFileFormatException, IOException {
		objAssertionPageObject.verifypaymenttext();
		objPaymentPageObject.clickOnCreditCardPayment();

	}

	@Parameters({ "deviceName_","UDID_","platformVersion_", "URL_", "appUrl_", "screenshotPath_" })
	@BeforeTest
	public void beforeTest(String deviceName_, String UDID_, String platformVersion_, String URL_, String appUrl_, String screenshotPath_) throws InterruptedException {
		objGlobalVariables = new GlobalVariables();
		objAppiumServer = new AppiumServer();
		objMobileDrivers = new MobileDrivers();
		// objAppiumServer.startServer();
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
		objHomePageObject = new HomePageObject2(iDriver);
		objPLPageObjets = new PLPageObjects(iDriver);
		objPDPageObject = new PDPageObject(iDriver);
		objWishlistPageObject = new WishlistPageObject(iDriver);
		objPLPageObjets = new PLPageObjects(iDriver);
		objProfileLoginPageObject = new ProfileLoginPageObject(iDriver);
		objCartPageObject = new CartPageObject(iDriver);
		objAssertionPageObject = new AssertionPageObject(iDriver);
		objEditAdressPageObject = new EditAdressPageObject(iDriver);
		objPaymentPageObject = new PaymentPageObject(iDriver);
		objMenCategoriesPageObjects = new MenCategoriesPageObjects(iDriver);
		objiOSGenericMethods = new iOSGenericMethods(iDriver);
		objAddNewAdressPageObjects = new AddNewAdressPageObjects(iDriver);

	}
	 @AfterTest
		public void quit() {
			iDriver.quit();
		}
}
