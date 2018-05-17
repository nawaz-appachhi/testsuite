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
import com.automation.mobile.Android.apps.ObjectRepository.Payment.PaymentPageObject;
import com.automation.mobile.Android.apps.ObjectRepository.ProductDes.ProductDescriptionPageObject;
import com.automation.mobile.Android.apps.ObjectRepository.WishList.WishListPageObject;

import io.appium.java_client.PressesKeyCode;
import io.appium.java_client.android.AndroidKeyCode;

/**
 * @author 300019225-Amba Jha
 *
 */
public class VEGASF_174_RegisteredUser_SimilarProducts_GiftWrap_StaggeredCombo_PayWithCredit_DebitCard
		extends BaseAndroidTest {
	GlobalVariables objGlobalVariables;
	AppiumServer objAppiumServer;
	LoginPageObject objLoginPageObject;
	HomePageObject objHomePageObject;
	MobileDrivers objMobileDrivers;
	ProductListPageObject objProductListPage;
	WishListPageObject objWishlistPageObject;
	ProductListPageObject objProductListPageObject;
	ProductDescriptionPageObject objProductDescriptionPageObject;
	CheckOutPageObject objCheckOutPageObject;
	AddCartPageObject objAddCartPageObject;
	AndroidGenericMethods objAndroidGenericMethods;
	PaymentPageObject objPaymentPageObject;
	String testName = "VEGASF_174";

	@Test(priority = 1)
	public void LoginInWithEmail() throws InterruptedException, InvalidFileFormatException, IOException {
		System.out.println("=====================VEGASF_174_START=====================");
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

	@Test(priority = 2)
	public void resetdata() throws InterruptedException {
		Reporter.log("resetdata");
		objCheckOutPageObject.resetAddress();
		objAddCartPageObject.resetBag();
		objWishlistPageObject.resetWishlist();
	}

	@Test(priority = 3)
	public void HomePage() throws InterruptedException, InvalidFileFormatException, IOException {
		Reporter.log("Home Page");
		objHomePageObject.clickOnSearch();
		objHomePageObject.enterSearchText(AndroidGenericMethods.getValueByKey(testName, "SearchItem")+ "\\n");
		
	}

	@Test(priority = 4)
	public void ProductDescriptionPage() throws Exception {
		Reporter.log("Product Description Page");
		objProductDescriptionPageObject.verifyPdpTitle("pdpTitle");
		objProductDescriptionPageObject.assertProductPrice();
		objProductDescriptionPageObject.clickAddToBagbtn();
		objProductDescriptionPageObject.clickSizeChartbtn();
		objProductDescriptionPageObject.clickCloseSizeChartbtn();
		objProductDescriptionPageObject.selectASize();
		objProductDescriptionPageObject.clickSaveToWishlist();
		objProductDescriptionPageObject.clickGoToBag();
	}

	@Test(priority = 5)
	public void SelectSize_Qty() throws Exception {
		Reporter.log("Change size and quantity");
		objAddCartPageObject.changeSize();
		objAddCartPageObject.verifyShoppingBagTitle();
	}

	@Test(priority = 6)
	public void GiftWrap() throws Exception {
		Reporter.log("Gift wrap");
		objAndroidGenericMethods.scrollDown(objAddCartPageObject.getGiftWrapbtn(), 50);
		objAddCartPageObject.GiftCardtxt(AndroidGenericMethods.getValueByKey("OBJECTREPO", "RecipientName"),
				AndroidGenericMethods.getValueByKey("OBJECTREPO", "GiftMessage"),
				AndroidGenericMethods.getValueByKey("OBJECTREPO", "SenderName"));
		wd.navigate().back();
		objAddCartPageObject.SaveGiftcard();
		objAddCartPageObject.getWishListbtn();
	}

	@Test(priority = 7)
	public void Checkout() throws InterruptedException, InvalidFileFormatException, IOException {
		Reporter.log("Checkout");
		objAddCartPageObject.clickPlaceOrder();
		objCheckOutPageObject.AddNewAddress();
		objCheckOutPageObject.verifyUserAddress();
		objCheckOutPageObject.clickContinue();
	}

	@Test(priority = 7)
	public void PaymentWithCOD() throws InterruptedException {
		Reporter.log("Payment");
		objPaymentPageObject.verifyPaymentHeader();
		try {
			objPaymentPageObject.selectPaymentOption("Cash/Card On Delivery");
		} catch (Exception e) {
			Reporter.log("COD Option is not enabled for the that amoutn");
		}
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
		Reporter.log("AppLaunch Successfully");
		wd.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		// objects are created
		objLoginPageObject = new LoginPageObject(wd);
		objHomePageObject = new HomePageObject(wd);
		objProductListPageObject = new ProductListPageObject(wd);
		objProductDescriptionPageObject = new ProductDescriptionPageObject(wd);
		objCheckOutPageObject = new CheckOutPageObject(wd);
		objAddCartPageObject = new AddCartPageObject(wd);
		objWishlistPageObject = new WishListPageObject(wd);
		objPaymentPageObject = new PaymentPageObject(wd);
		objAndroidGenericMethods = new AndroidGenericMethods(wd);
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
		System.out.println("=====================VEGASF_174_END=====================");
	}
}
