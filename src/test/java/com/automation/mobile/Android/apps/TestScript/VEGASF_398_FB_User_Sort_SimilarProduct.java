package com.automation.mobile.Android.apps.TestScript;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.ini4j.InvalidFileFormatException;
import org.testng.Reporter;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.BaseAndroidTest;
import com.automation.core.Common.AppiumServer;
import com.automation.core.Common.GlobalVariables;
import com.automation.core.Common.MobileDrivers;
import com.automation.core.mobile.Android.AndroidGenericMethods;
import com.automation.mobile.Android.apps.ObjectRepository.AddCart.AddCartPageObject;
import com.automation.mobile.Android.apps.ObjectRepository.Checkout.CheckOutPageObject;
import com.automation.mobile.Android.apps.ObjectRepository.Home.HomePageObject;
import com.automation.mobile.Android.apps.ObjectRepository.Login.LoginPageObject;
import com.automation.mobile.Android.apps.ObjectRepository.PLP.ProductListPageObject;
import com.automation.mobile.Android.apps.ObjectRepository.Payment.NetBankingPageObject;
import com.automation.mobile.Android.apps.ObjectRepository.Payment.PaymentPageObject;
import com.automation.mobile.Android.apps.ObjectRepository.ProductDes.ProductDescriptionPageObject;
import com.automation.mobile.Android.apps.ObjectRepository.WishList.WishListPageObject;

import io.appium.java_client.PressesKeyCode;
import io.appium.java_client.android.AndroidKeyCode;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;

/**
 * @author 300021280 -sneha
 */
public class VEGASF_398_FB_User_Sort_SimilarProduct extends BaseAndroidTest {
	GlobalVariables objGlobalVariables;
	AppiumServer objAppiumServer;
	LoginPageObject objLoginPageObject;
	HomePageObject objHomePageObject;
	ProductListPageObject objProductListPageObject;
	ProductDescriptionPageObject objProductDescriptionPageObject;
	AddCartPageObject objAddCartPageObject;
	CheckOutPageObject objCheckOutPageObject;
	PaymentPageObject objPaymentPageObject;
	MobileDrivers objMobileDrivers;
	WishListPageObject objWishListPageObject;
	AndroidGenericMethods objAndroidGenericMethods;
	NetBankingPageObject objNetBankingPageObject;
	IOSDriver<IOSElement> iDriver;
	String ExcelPath;
	String testName = "VEGASF_398";

	@Test(priority = 1)
	public void LoginInWithEmail() throws InterruptedException, InvalidFileFormatException, IOException {
		System.out.println("=====================VEGASF_398_START=====================");
		Reporter.log("LoginInWithEmail");
		objLoginPageObject.clickFirstLogin();
		objLoginPageObject.loginInApp(AndroidGenericMethods.getValueByKey(testName, "UserName"),
				AndroidGenericMethods.getValueByKey(testName, "Password"));
		objLoginPageObject.clickLogin();
		objLoginPageObject.clickpopUp();
		objLoginPageObject.clickhamburger();
		objLoginPageObject.verifyUserId();
		wd.navigate().back();
	}

	@Test(priority = 2 , dependsOnMethods = {"LoginInWithEmail"})
	public void reset() throws InterruptedException {
		Reporter.log("reset");
		objAddCartPageObject.resetBag();
		objWishListPageObject.resetWishlist();
		objCheckOutPageObject.resetAddress();
	}

	@Test(priority = 3, dependsOnMethods = {"LoginInWithEmail"})
	public void searchItem() throws InterruptedException, InvalidFileFormatException, IOException {
		Reporter.log("searchItem");
		objHomePageObject.clickOnSearch();
		objHomePageObject.enterSearchText(AndroidGenericMethods.getValueByKey(testName, "SearchItem")+ "\\n");
		
	}

	@Test(priority = 5, dependsOnMethods = {"searchItem"})
	public void EmptyWishlist() throws InterruptedException {
		Reporter.log("EmptyWishlist");
		objProductDescriptionPageObject.verifyPdpTitle("pdpTitle");
		objProductDescriptionPageObject.assertProductPrice();
		objProductDescriptionPageObject.clickAddToBagbtn();
		objProductDescriptionPageObject.selectASize();
		objProductDescriptionPageObject.clickSaveToWishlist();
		objProductDescriptionPageObject.clickWishListbtn();
		objWishListPageObject.removeAllItemsFromWishlist();
		objWishListPageObject.clickBagBtn();
	}

	@Test(priority = 6, dependsOnMethods = {"EmptyWishlist"})
	public void EditAddress() throws InterruptedException, InvalidFileFormatException, IOException {
		Reporter.log("EditAddress");
		// objProductListPageObject.clickOkButton();
		objAddCartPageObject.verifyShoppingBagTitle();
		objAddCartPageObject.clickPlaceOrder();
		objCheckOutPageObject.verifyUserAddress();
		objCheckOutPageObject.editAddress();
		objCheckOutPageObject.clickContinue();
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
		if (!(params.get("engine_").equalsIgnoreCase("TD"))) {
			wd = objMobileDrivers.launchAppAndroid(params);
		} else {
			try {
				setUpTest();
				System.out.println("TestDroid Execution Started");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println("Error :: Please change suite parameter to run locally.");
			}
		}
		wd.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		// Make sure that Page object object creation should be after this line
		// "wd= objMobileDrivers.launchAppAndroid();"
		objLoginPageObject = new LoginPageObject(wd);
		objHomePageObject = new HomePageObject(wd);
		objProductListPageObject = new ProductListPageObject(wd);
		objProductDescriptionPageObject = new ProductDescriptionPageObject(wd);
		objAddCartPageObject = new AddCartPageObject(wd);
		objCheckOutPageObject = new CheckOutPageObject(wd);
		objPaymentPageObject = new PaymentPageObject(wd);
		objAndroidGenericMethods = new AndroidGenericMethods(wd);
		objWishListPageObject = new WishListPageObject(wd);
		objNetBankingPageObject = new NetBankingPageObject(wd);
	}

	@AfterTest
	public void quit() {
		try {
			quitAppiumSession();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		wd.quit();
		System.out.println("=====================VEGASF_398_END=====================");
	}
}
