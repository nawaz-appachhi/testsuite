package com.automation.mobile.iOS.apps.SFQA_TestScript;

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
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
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

public class VEGASF_368_FB_User_ClickforBestPrice_ApplyCoupon_PercentageDiscount_RemoveAddress_PayWithCD {

	GlobalVariables objGlobalVariables;
	AppiumServer objAppiumServer;
	ProfileSignUpPageObject objProfileSignUpPageObject;
	PLPageObjects objPLPageObjets;
	PDPageObject objPDPageObject;
	AddNewAdressPageObjects objAddAddressPageObjects;
	HomePageObject2 objHomePageObject2;
	MobileDrivers objMobileDrivers;
	IOSDriver<IOSElement> iDriver;
	ProfileLoginPageObject objProfileLoginPageObject;
	AssertionPageObject objAssertionPageObject;
	PaymentPageObject objPaymentPageObject;
	WishlistPageObject objWishlistPageObject;
	iOSGenericMethods objiOSGenericMethods;
	CartPageObject objCartPageObject;

	String TestName = "VEGASF_368";

	/**
	 * @author 300021275
	 * 
	 * 
	 *         Facebook registered user Home Page Search (using a keyword in the
	 *         Golden Set) Filter Products Check Delivery Option Move to bag Add
	 *         more from wishlist Click for offer Add New address - Office Payment :
	 *         Myntra Credit + CO
	 * 
	 */

	@Test(priority = 1)
	public void LoginWithFacebook() throws InterruptedException, InvalidFileFormatException, IOException {
		objProfileLoginPageObject.clickOnProfileButton();
		objiOSGenericMethods.swipeDown(0, 7);
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
		
	}

	@Test(priority = 2)
	public void RemoveAddress() throws InterruptedException, InvalidFileFormatException, IOException {
		objiOSGenericMethods.swipeDown(0, 4);
		objProfileLoginPageObject.removeAddress();
	}

	@Test(priority = 3)
	public void SearchItem() throws InterruptedException, InvalidFileFormatException, IOException {
		objHomePageObject2.emptyBag();
		objHomePageObject2.clickOnHomeButton();
		objHomePageObject2.clickOnSearchButton();
		objAssertionPageObject.VerifyAutoSuggestionList();
		String search = objiOSGenericMethods.getValueByKey(TestName, "Search");
		objHomePageObject2.enterSearchitem(search);
	}

//	@Test(priority = 4)
//	public void ListPageToPDP() throws InterruptedException, InvalidFileFormatException, IOException {
//		objAssertionPageObject.verifyProductname();
//		objAssertionPageObject.verifyPLPHeader();
//		objAssertionPageObject.verifyPLPProductCount();
//		objPLPageObjets.clickOnFirstproductofPLP();
//	}

	@Test(priority = 5)
	public void AddToBag() throws InterruptedException {
//		objiOSGenericMethods.swipeDown(0, 3);
//		objPDPageObject.clickOnBestPrice();
		objPDPageObject.clickOnAddToBag();
		objPDPageObject.getSizeListinString(0);
		objPDPageObject.clickonDoneButton();
		objPDPageObject.clickOnGoToBag();
	}

//	
//	@Test(priority = 7)
//	public void CheckSimilarProducts() throws InterruptedException {
//		objiOSGenericMethods.swipeDown(0, 16);
//		objPDPageObject.clickOnSimilarProduct();
//		objPDPageObject.clickOnViewSimilar();
//		objPLPageObjets.clickOnBrandNamePLP();
//		objPDPageObject.clickOnViewMyBag();
//		objAssertionPageObject.verifyProductTitleCartPage();
//	}

	@Test(priority = 8)
	public void ConditionalDiscount() throws InterruptedException, InvalidFileFormatException, IOException {
		/**
		 * @author 300019221 Aravindanath
		 * This is commented because of Test data issue.
		 */
		objAssertionPageObject.verifyMyBag();
		objiOSGenericMethods.swipeDown(100, 3);
		objCartPageObject.ClickOnApplyCoupon();
		objAssertionPageObject.verifyApplyCouponHeaders();
		String coupon = objiOSGenericMethods.getValueByKey(TestName, "Coupon");
	//	objCartPageObject.enterCoupon(coupon);
		objCartPageObject.clickOnApplyButton();
		
	}	

	@Test(priority = 9)

	public void cartPage() throws InterruptedException {
		objiOSGenericMethods.swipeDown(100, 7);
		objAssertionPageObject.verifyPriceDetails();
		objCartPageObject.clickOnplaceOrder();
	}

	@Test(priority = 10)
	public void AddAddress() throws InterruptedException, InvalidFileFormatException, IOException {
		objAssertionPageObject.verifyAddressHeaders();
		objAddAddressPageObjects.clickOnAddNewAddress();
		String pincode = iOSGenericMethods.getValueByKey(TestName, "Pincode");
		String locality = iOSGenericMethods.getValueByKey(TestName, "Locality");
		String name = iOSGenericMethods.getValueByKey(TestName, "Name");
		String address = iOSGenericMethods.getValueByKey(TestName, "Address");
		String mobile = iOSGenericMethods.getValueByKey(TestName, "Mobile");
		objAddAddressPageObjects.EnterContinueOrderAddingOfficeAddress(pincode, locality, name, address, mobile);
		objCartPageObject.clickOnContinueOrder();
	}

	@Test(priority = 11)
	public void PaymentWithPhonePe() throws InterruptedException, InvalidFileFormatException, IOException {
		objAssertionPageObject.verifyAddressHeaders();
		objPaymentPageObject.clickOnCreditCardPayment();
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
		iDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		System.out.println("Test Name " + TestName);
		objPLPageObjets = new PLPageObjects(iDriver);
		objPDPageObject = new PDPageObject(iDriver);
		objWishlistPageObject = new WishlistPageObject(iDriver);
		objPLPageObjets = new PLPageObjects(iDriver);
		objProfileLoginPageObject = new ProfileLoginPageObject(iDriver);
		objCartPageObject = new CartPageObject(iDriver);
		objAssertionPageObject = new AssertionPageObject(iDriver);
		objPaymentPageObject = new PaymentPageObject(iDriver);
		objHomePageObject2 = new HomePageObject2(iDriver);
		objAddAddressPageObjects = new AddNewAdressPageObjects(iDriver);
		objiOSGenericMethods = new iOSGenericMethods(iDriver);
	}
	 @AfterTest
		public void quit() {
			iDriver.quit();
		}
}