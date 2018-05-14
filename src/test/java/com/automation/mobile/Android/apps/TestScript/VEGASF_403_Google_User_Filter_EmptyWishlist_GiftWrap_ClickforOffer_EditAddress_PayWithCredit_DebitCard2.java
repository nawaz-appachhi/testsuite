package com.automation.mobile.Android.apps.TestScript;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.ini4j.InvalidFileFormatException;
import org.testng.Reporter;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
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
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.AndroidKeyCode;

/**
 * @author 300018281 Subhasis
 *
 */

public class VEGASF_403_Google_User_Filter_EmptyWishlist_GiftWrap_ClickforOffer_EditAddress_PayWithCredit_DebitCard2 { 

	GlobalVariables objGlobalVariables;
	AppiumServer objAppiumServer;
	LoginPageObject objLoginPageObject;
	HomePageObject objHomePageObject;
	AndroidDriver<AndroidElement> aDriver;
	MobileDrivers objMobileDrivers;
	ProductListPageObject objProductListPage;
	PaymentPageObject objPaymentPageObject;
	ProductListPageObject objProductListPageObject;
	ProductDescriptionPageObject objProductDescriptionPageObject;
	CheckOutPageObject objCheckOutPageObject;
	AddCartPageObject objAddCartPageObject;
	AndroidGenericMethods objAndroidGenericMethods;
	WishListPageObject objWishListPageObject;
	String testName = "VEGASF_403"; 

	@Test(priority = 1)
	public void LoginWithGoogle() throws InterruptedException, InvalidFileFormatException, IOException {
		System.out.println("=====================VEGASF_403_START=====================");
		Reporter.log("LoginWithGoogle");
		objLoginPageObject.clickFirstLogin();
		objLoginPageObject.loginInApp(AndroidGenericMethods.getValueByKey(testName, "UserName") , AndroidGenericMethods.getValueByKey(testName,"Password"));
		objLoginPageObject.clickLogin();
		objLoginPageObject.clickpopUp();
	}
	
	@Test(priority = 2)
	public void HomePage() throws InterruptedException, InvalidFileFormatException, IOException {
		Reporter.log("HomePage");
		objLoginPageObject.clickhamburger();
		objLoginPageObject.verifyUserId();
		aDriver.pressKeyCode(AndroidKeyCode.BACK);
	}
	@Test(priority = 3)
	public void reset() throws InterruptedException, InvalidFileFormatException, IOException {
		Reporter.log("reset");
		objAddCartPageObject.resetBag();
		objWishListPageObject.resetWishlist();
		objCheckOutPageObject.resetAddress();
	}	
	@Test(priority = 4)
	public void SearchItem() throws InterruptedException, InvalidFileFormatException, IOException {
		Reporter.log("SearchItem");
		objHomePageObject.clickOnSearch();
		objHomePageObject.enterSearchText(AndroidGenericMethods.getValueByKey(testName, "SearchItem"));
		aDriver.pressKeyCode(AndroidKeyCode.ENTER);
	}
	
	@Test(priority = 5)
	public void ProductDescriptionPage() throws InterruptedException {
		Reporter.log("ProductDescriptionPage");
		objProductDescriptionPageObject.verifyPdpTitle("pdpTitle"); // Assertion
		objProductDescriptionPageObject.clickSaveToWishlist();
		objProductDescriptionPageObject.clickAddToBagbtn();
		objProductDescriptionPageObject.selectASize();
		objProductDescriptionPageObject.clickWishListbtn();
	}
	
	@Test(priority = 6)
	public void EmptyWishlist() throws Exception {
		Reporter.log("EmptyWishlist");
		objWishListPageObject.verifyWishlistIcon();
		objWishListPageObject.clickBagBtn();
	}
	
	@Test(priority = 7)
	public void AddCartPage_ApplyCoupon() throws InterruptedException, InvalidFileFormatException, IOException {
		Reporter.log("ApplyCoupon");
		objAndroidGenericMethods.scrollDown(objAddCartPageObject.getApplyCouponbtn(), 100);
		objAddCartPageObject.ClickCouponCancelbtn();
	}
	
	@Test(priority = 8)
	public void GiftWrap() throws InterruptedException, InvalidFileFormatException, IOException {
		Reporter.log("Gift wrap");
		objAndroidGenericMethods.scrollDown(objAddCartPageObject.getGiftWrapbtn(), 50);
		objAddCartPageObject.GiftCardtxt(AndroidGenericMethods.getValueByKey("OBJECTREPO", "RecipientName"),AndroidGenericMethods.getValueByKey("OBJECTREPO", "GiftMessage"),AndroidGenericMethods.getValueByKey("OBJECTREPO", "SenderName"));
		aDriver.pressKeyCode(AndroidKeyCode.BACK);
		objAddCartPageObject.SaveGiftcard();
		objAddCartPageObject.getWishListbtn();
	}
	
	@Test(priority = 9)
	public void EditAddress() throws InterruptedException, InvalidFileFormatException, IOException {
		Reporter.log("EditAddress");
		objAddCartPageObject.clickPlaceOrder();
		objCheckOutPageObject.CheckAddress();
		objCheckOutPageObject.clickContinue();
	}
	
	@Test(priority = 10)
	public void PaymentWithCreditDebitCard() throws InterruptedException {
		Reporter.log("PaymentWithCreditDebitCard");
		objPaymentPageObject.verifyPaymentHeader();
		objPaymentPageObject.selectPaymentOption("Credit/Debit Card");
	}
	@Parameters({"deviceName_","UDID_","platformVersion_", "URL_", "appUrl_", "screenshotPath_"})
	@BeforeTest
	public void beforeTest(String deviceName_, String UDID_, String platformVersion_, String URL_, String appUrl_, String screenshotPath_) throws InterruptedException, MalformedURLException {
	
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
		aDriver = objMobileDrivers.launchAppAndroid(params);
		Reporter.log("AppLaunch Successfully");
		aDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		// objects are created
		objLoginPageObject = new LoginPageObject(aDriver);
		objHomePageObject = new HomePageObject(aDriver);
		objProductListPageObject = new ProductListPageObject(aDriver);
		objProductDescriptionPageObject = new ProductDescriptionPageObject(aDriver);
		objCheckOutPageObject = new CheckOutPageObject(aDriver);
		objAddCartPageObject = new AddCartPageObject(aDriver);
		objWishListPageObject = new WishListPageObject(aDriver);
		objPaymentPageObject = new PaymentPageObject(aDriver);
		objAndroidGenericMethods = new AndroidGenericMethods(aDriver);
		objPaymentPageObject= new PaymentPageObject(aDriver);
	}
	@AfterTest
	public void quit() {
		aDriver.quit();
		System.out.println("=====================VEGASF_403_END=====================");

	}


}
