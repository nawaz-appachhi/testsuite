package com.automation.mobile.iOS.apps.TestScript;

import java.io.IOException;
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
import org.testng.annotations.Test;
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
import com.automation.mobile.iOS.apps.ObjectRepository.Pages.WishListPage.WishlistPageObject;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;

/**
 * 
 * @author 300019221 / Aravindanath
 *
 */
/*
 * Casual Browsing User
 * 
 * Facebook registered user - Login Home Page Search - Using Autosuggest (use
 * any query from Golden Set) Save Product Filter Discount View Size Chart Add
 * more from wishlist Free Gift Remove address
 * 
 */

public class VEGASF_358_FB_User_SaveWishlist_FreeGift {

	GlobalVariables objGlobalVariables;
	iOSGenericMethods objiOSGenericMethods;
	PLPageObjects objPLPageObjets;
	PDPageObject objPDPageObject;
	AddNewAdressPageObjects objAddNewAdressPageObjects;
	AssertionPageObject objAssertionPageObject;
	HomePageObject2 objHomePageObjects;
	MobileDrivers objMobileDrivers;
	IOSDriver<IOSElement> iDriver;
	ProfileLoginPageObject objLoginPageObject;
	WishlistPageObject objWishlistPageObject;
	CartPageObject objCartPage;
	PaymentPageObject objPaymentPageObjects;
	String TestName = "VEGASF_358";

//	@Test(priority = 1)

	public void LoginApp() throws InterruptedException, InvalidFileFormatException, IOException {
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
		objLoginPageObject.clickOnFaceBook();
		Thread.sleep(5000);
		Set<String> contextNames = iDriver.getContextHandles();
		for (String contextName : contextNames) {
			if (contextName.contains("WEBVIEW"))
				iDriver.context(contextName);
			System.out.println(contextName);
			System.out.println("Context --> " + iDriver.getContext());
		}
//		objiOSGenericMethods.acceptAlert();

		try {
			WebElement emailid = iDriver.findElement(By.cssSelector("#m_login_email"));
			WebElement pass = iDriver.findElement(By.cssSelector("#m_login_password"));
			WebElement loginButton = iDriver.findElement(By.cssSelector("#u_0_5"));
			WebElement continueLogin = iDriver.findElement(By.cssSelector("#u_0_9"));
			 WebElement continueLogin2 = iDriver.findElement(By.cssSelector("#u_0_3"));
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
			WebElement cont = iDriver.findElement(By.cssSelector("#u_0_3"));
			objiOSGenericMethods.fluentWait(cont);
			cont.click();
		} catch (Exception e) {
			System.out.println("User is already login, No continue button to click!");
		}
		Set<String> contextNames2 = iDriver.getContextHandles();
		for (String contextName : contextNames2) {
			if (contextName.contains("NATIVE_APP"))
				iDriver.context(contextName);
			System.out.println(contextName);
			System.out.println("Context --> " + iDriver.getContext());
		}
		objAssertionPageObject.verifyUserName();
		objiOSGenericMethods.swipeDown(100, 6);
		objLoginPageObject.removeAddress();
	}

	
	
	
	@Test(priority = 1)
	public void LoginInApp() throws InterruptedException, InvalidFileFormatException, IOException {
		objLoginPageObject.clickOnProfileButton();
		objiOSGenericMethods.swipeDown(100, 8);
		objLoginPageObject.clickOnLogOut();
		objLoginPageObject.clickOnLogin();
		String email = iOSGenericMethods.getValueByKey(TestName, "UserName");
		String password = iOSGenericMethods.getValueByKey(TestName, "Password");
		objLoginPageObject.loginInApp(email, password);
		objLoginPageObject.clickOnLoginButton();
		objAssertionPageObject.verifyUserName();
		objiOSGenericMethods.acceptAlert();
		objiOSGenericMethods.swipeDown(100, 6);
		objLoginPageObject.removeAddress();
	}
	
	@Test(priority = 2)

	public void SearchItem() throws InterruptedException, InvalidFileFormatException, IOException {
		objHomePageObjects.emptyBag();
		objHomePageObjects.clickOnHomeButton();
		objHomePageObjects.clickOnSearchButton();// Impulse Unisex Orange Rucksack
		String search = objiOSGenericMethods.getValueByKey(TestName, "Search");
		objHomePageObjects.enterSearchitem(search);
	}

	@Test(priority = 3)

	public void productListingPage() throws InterruptedException {
		objPLPageObjets.clickOnFilter();
		objPLPageObjets.clickOnFilterDiscount();
		objPLPageObjets.clickOnSelectFirstFilterDiscount();
		objPLPageObjets.clickOnApplyDiscount();
		objAssertionPageObject.verifyProductname();
		objAssertionPageObject.verifyPLPProductCount();
		objPLPageObjets.clickOnBrandNamePLP();
	}

	@Test(priority = 4)

	public void productDiscriptionPage() throws InterruptedException, InvalidFileFormatException, IOException {
		objPDPageObject.clickOnAddToBag();
		objPDPageObject.clickOnSizeChart();
		objPDPageObject.clickOnSizeChartBackButton();
		objPDPageObject.clickOnBack();
		objPLPageObjets.clickOnSearchOnPLP();
		String search = objiOSGenericMethods.getValueByKey(TestName, "FreeGift");
		objHomePageObjects.enterSearchitem(search);
		objAssertionPageObject.verifyProductname();
		objAssertionPageObject.verifyPLPProductCount();
		objPLPageObjets.clickOnBrandNamePLP();
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
	public void AddNewAddress_Home() throws InterruptedException, InvalidFileFormatException, IOException {
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
		objPaymentPageObjects.clickOnCreditCardPayment();
	}

	@Parameters({ "deviceName_","UDID_","platformVersion_", "URL_", "appUrl_", "screenshotPath_" })
	@BeforeTest

	public void beforeTest(String deviceName_, String UDID_, String platformVersion_, String URL_, String appUrl_, String screenshotPath_) throws InterruptedException {
		objGlobalVariables = new GlobalVariables();
		objMobileDrivers = new MobileDrivers();
		Map<String, String> params = new HashMap<String, String>();
        params.put("deviceName_", deviceName_);
        params.put("UDID_", UDID_);
        params.put("platformVersion_", platformVersion_);
        params.put("URL_", URL_);
        params.put("appUrl_", appUrl_);
        params.put("screenshotPath_", screenshotPath_);
		iDriver = objMobileDrivers.launchAppiOS(params);
		iDriver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		System.out.println("Test Name " + TestName);
		// Make sure that Page object object creation should be after this line
		objPLPageObjets = new PLPageObjects(iDriver);
		objPDPageObject = new PDPageObject(iDriver);
		objHomePageObjects = new HomePageObject2(iDriver);
		objAddNewAdressPageObjects = new AddNewAdressPageObjects(iDriver);
		objLoginPageObject = new ProfileLoginPageObject(iDriver);
		objPaymentPageObjects = new PaymentPageObject(iDriver);
		objWishlistPageObject = new WishlistPageObject(iDriver);
		objCartPage = new CartPageObject(iDriver);
		objAssertionPageObject = new AssertionPageObject(iDriver);
		objiOSGenericMethods = new iOSGenericMethods(iDriver);

	}
	 @AfterTest
		public void quit() {
			iDriver.quit();
		}
}
