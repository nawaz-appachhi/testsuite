package com.automation.mobile.Android.apps.SFQA_TestScript;

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

 /* Google registered user - Login
Home Page
Search (using menu item e.g. Men -> Topwear - T-Shirts
Sort
Verify Product Code
View Size Chart
Remove Product
Percentage Discount
Edit address	Manual
Payment : GC +Online
*/
 
public class VEGASF_401_Google_User_Sort_SelectSize_RemoveProduct_PercentageDiscount_EditAddress_PayWithDD2 {

	GlobalVariables objGlobalVariables;
	AppiumServer objAppiumServer;
	LoginPageObject objLoginPageObject;
	HomePageObject objHomePageObject;
	AndroidDriver<AndroidElement> aDriver;
	MobileDrivers objMobileDrivers;
	ProductListPageObject objProductListPage;
	WishListPageObject objWishlistPageObject;
	ProductListPageObject objProductListPageObject;
	ProductDescriptionPageObject objProductDescriptionPageObject;
	CheckOutPageObject objCheckOutPageObject;
	AddCartPageObject objAddCartPageObject;
	AndroidGenericMethods objAndroidGenericMethods;
	PaymentPageObject objPaymentPageObject;

	String testName = "VEGASF_401"; 

	@Test(priority = 1)
	public void LoginWithGoogle() throws InterruptedException, InvalidFileFormatException, IOException {
		System.out.println("=====================VEGASF_401_START=====================");
		Reporter.log("LoginWithGoogle Test case Started Successfully");
		objLoginPageObject.clickFirstLogin();
		objLoginPageObject.loginInApp(AndroidGenericMethods.getValueByKey(testName, "UserName"),
				AndroidGenericMethods.getValueByKey(testName, "Password"));
		objLoginPageObject.clickLogin();
		// objLoginPageObject.clickOnGoogleSignUpOption();
		// objLoginPageObject.clickOnUseAnotherAccount();
		// objLoginPageObject.EnterGmailEmail(AndroidGenericMethods.getValueByKey(testName,
		// "UserName"));
		// objLoginPageObject.AppEmailNextButton();
		// objLoginPageObject.EnterGmailPassword(AndroidGenericMethods.getValueByKey(testName,
		// "Password"));
		// objLoginPageObject.AppPasswordNextButton();
		// objLoginPageObject.clickOnAgree();
		objLoginPageObject.clickpopUp();
		objLoginPageObject.clickhamburger();
		objLoginPageObject.verifyUserId();
		Thread.sleep(1000);
		aDriver.pressKeyCode(AndroidKeyCode.BACK);
	}

	@Test(priority = 2)
	public void resetdata() throws InterruptedException {
		objAddCartPageObject.resetBag();
//		objWishlistPageObject.resetWishlist();
//		objCheckOutPageObject.resetAddress();
	}
 
	@Test(priority = 3)
	public void HomePage() throws InterruptedException, InvalidFileFormatException, IOException {
		Reporter.log("HomePage Test case Started Successfully");
		objHomePageObject.clickOnSearch();
		objHomePageObject.enterSearchText(AndroidGenericMethods.getValueByKey(testName, "SearchItem"));
		aDriver.pressKeyCode(AndroidKeyCode.ENTER);
	}
	@Test(priority = 4)
	public void productDescriptionPage() throws InterruptedException {
		Reporter.log("productDescriptionPage");
		objProductDescriptionPageObject.assertProductPrice();
		objProductDescriptionPageObject.clickSaveToWishlist();
		objProductDescriptionPageObject.clickAddToBagbtn();
	}

	@Test(priority = 5)
	public void CheckSizeChartLink() throws InterruptedException {
		Reporter.log("CheckSizeChartLink Test case Started Successfully");
		objProductDescriptionPageObject.clickSizeChartbtn();
		objProductDescriptionPageObject.clickCloseSizeChartbtn();
		objProductDescriptionPageObject.selectASize();
		objProductDescriptionPageObject.clickWishListbtn();

	}

	@Test(priority = 6)
	public void AddToBag() throws InterruptedException {
		Reporter.log("AddToBag");
		objWishlistPageObject.clickMoveToBag();
		objWishlistPageObject.clickSizeWishList();
		objWishlistPageObject.clickDoneWishListbtn();
		objWishlistPageObject.clickBagBtn();
	}

	@Test(priority = 7)
	public void RemoveItem() throws InterruptedException {
		Reporter.log("RemoveItem Test case Started Successfully");
		//objProductListPageObject.clickOkButton();     // no need to apply if reset applied
		objAddCartPageObject.verifyShoppingBagTitle();
		objAddCartPageObject.clickPlaceOrder();
	}

	@Test(priority = 8)
	public void EditAddress() throws InterruptedException, InvalidFileFormatException, IOException {
		Reporter.log("EditAddress Test case Started Successfully");
	//	objCheckOutPageObject.CheckAddress();
		objCheckOutPageObject.editAddress();
		objCheckOutPageObject.clickContinue();
	}

	@Test(priority = 9)
	public void PaymenWithManualGC_Online() throws InterruptedException, InvalidFileFormatException, IOException {
		Reporter.log("PaymenWithManualGC_Online Test case Started Successfully");
		objPaymentPageObject.verifyPaymentHeader();
		objPaymentPageObject.selectPaymentOption("Credit/Debit Card");
		objPaymentPageObject.enterCardNumber(AndroidGenericMethods.getValueByKey(testName, "CardNumber"));
		objPaymentPageObject.enterNameOnCard(AndroidGenericMethods.getValueByKey(testName, "NameOnCard"));
		objPaymentPageObject.clickExpiryMonts();
		objPaymentPageObject.clickExpiryYears();
		objPaymentPageObject.enterCVVNumber(AndroidGenericMethods.getValueByKey(testName, "CVVNumber"));
		objPaymentPageObject.clickPayNowBtn();

	}
	
	@Test(priority = 10)
	public void Verifypayment() throws InvalidFileFormatException, IOException, InterruptedException {
		aDriver.pressKeyCode(AndroidKeyCode.BACK);
		objPaymentPageObject.readOrderNumberConfirmationPage();
		objPaymentPageObject.clickOnViewOrder();
		objPaymentPageObject.VerifyOrderNumberOrderDetailsPage();
	}
	
	@Parameters({ "deviceName_", "UDID_", "platformVersion_", "URL_", "appUrl_", "screenshotPath_" })
	@BeforeTest
	public void beforeTest(String deviceName_, String UDID_, String platformVersion_, String URL_, String appUrl_,
			String screenshotPath_) throws InterruptedException, MalformedURLException {
		// create Excel Reference
		objGlobalVariables = new GlobalVariables();
		// objExcelUtilities = new ExcelUtils();
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
		objWishlistPageObject = new WishListPageObject(aDriver);
		objAndroidGenericMethods = new AndroidGenericMethods(aDriver);
		objPaymentPageObject = new PaymentPageObject(aDriver);
	}

	@AfterTest
	public void quit() {
		aDriver.quit();
		System.out.println("=====================VEGASF_401_END=====================");

	}
}
