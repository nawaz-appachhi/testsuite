package com.automation.mobile.iOS.apps.TestScript;

/**
 * @author 300021275 - Lata
 * 
 * Test Steps : Verified number Flow (Profile)

Sign Up(Facebook)
Continue with Facebook
Hamburger
Account
Profile Details
Enter Verified Phone Number synced with other registered account
Verify
 */

import org.testng.annotations.Test;
import org.testng.annotations.Test;
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
import com.automation.mobile.iOS.apps.ObjectRepository.Pages.ProfileDetailsPage.ProfileDetailsPageObject;
import com.automation.mobile.iOS.apps.ObjectRepository.Pages.ProfileLoginPage.ProfileLoginPageObject;
import com.automation.mobile.iOS.apps.ObjectRepository.Pages.ProfileSignUpPage.ProfileSignUpPageObject;
import com.automation.mobile.iOS.apps.ObjectRepository.Pages.WishListPage.WishlistPageObject;

import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;

public class VEGASF_487_FacebookUser_ProfileDetails_MobileNumber_SyncWithOtherAccount_Verify extends BaseIOSTest{

	GlobalVariables objGlobalVariables;
	AppiumServer objAppiumServer;
	ProfileSignUpPageObject objProfileSignUpPageObject;
	PLPageObjects objPLPageObjets;
	PDPageObject objPDPageObject;
	AddNewAdressPageObjects objAddNewAdressPageObjects;
	HomePageObject2 objHomePageObject2;
	EditAdressPageObject objEditAdressPageObject;
	MobileDrivers objMobileDrivers;
	//IOSDriver<IOSElement> wd;
	ProfileLoginPageObject objProfileLoginPageObject;
	AssertionPageObject objAssertionPageObject;
	PaymentPageObject objPaymentPageObject;
	WishlistPageObject objWishlistPageObject;
	CartPageObject objCartPageObject;
	iOSGenericMethods objiOSGenericMethods;
	ProfileDetailsPageObject objProfileDetailsPageObject;
	String TestName = "VEGASF_487";
	
	
	String UDID1;

	@Test(priority = 1)
	public void LoginApp() throws InterruptedException, InvalidFileFormatException, IOException {
		try {
			objProfileLoginPageObject.clickOnOnBoardingCrossButton();
			System.out.println("On Boarding screen appeared and closed it");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("On Boarding screen did not appear");
		}
		objProfileLoginPageObject.clickOnProfileButton();
		objiOSGenericMethods.swipeDown(100, 7);
		objProfileLoginPageObject.clickOnLogOut();
		objProfileLoginPageObject.clickOnFaceBook();
		/**
		 * @author 300019221 explicitly we are using Thread.sleep to handle Native to
		 *         Web view switch.
		 */
		Thread.sleep(5000);
		Set<String> contextNames = wd.getContextHandles();
		for (String contextName : contextNames) {
			if (contextName.contains("WEBVIEW"))
				wd.context(contextName);
			System.out.println(contextName);
			System.out.println("Context --> " + wd.getContext());
			System.out.println("Context --> " + wd.getContext());
		}

		// objiOSGenericMethods.acceptAlert();
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
			} else if (continueLogin2.isDisplayed()) {
				// objiOSGenericMethods.fluentWait(continueLogin);
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

	@Test(priority = 2)
	public void ResetMNV() throws InterruptedException, InvalidFileFormatException, IOException {
		objiOSGenericMethods.swipeDown(0, 5);
		objProfileDetailsPageObject.clickOnProfileDetails();
		objiOSGenericMethods.swipeDown(0, 2);
		objProfileDetailsPageObject.enterMobileNumber(iOSGenericMethods.getValueByKey(TestName, "TempMobileNumber"));
		objProfileDetailsPageObject.clickOnLocation();
		objProfileDetailsPageObject.clickOnSaveDetails();
	}
	@Test(priority = 3)
	public void ProfileDetails() throws InterruptedException, InvalidFileFormatException, IOException {
		objiOSGenericMethods.swipeDown(0, 5);
		objProfileDetailsPageObject.clickOnProfileDetails();
		objAssertionPageObject.verifyProfileDetailsPageTitle();
		objiOSGenericMethods.swipeDown(0, 2);
		try {
			if(UDID1.equalsIgnoreCase(iOSGenericMethods.getValueByKey(TestName, "Device_1"))) {
				objProfileDetailsPageObject.enterMobileNumber((iOSGenericMethods.getValueByKey(TestName, "Num1")));
				
			}
			else if (UDID1.equalsIgnoreCase(iOSGenericMethods.getValueByKey(TestName, "Device_2"))){
				objProfileDetailsPageObject.enterMobileNumber((iOSGenericMethods.getValueByKey(TestName, "Num2")));
			}
		} catch (Exception e) {
		}
		objProfileDetailsPageObject.clickOnLocation();
		objProfileDetailsPageObject.clickOnSaveDetails();
		objProfileDetailsPageObject.clickOnProfileDetails();
		objiOSGenericMethods.swipeDown(0, 2);
		objProfileDetailsPageObject.clickOnVerifyButton();
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
		// objAppiumServer.startServer();
		Map<String, String> params = new HashMap<String, String>();
		params.put("deviceName_", deviceName_);
		params.put("UDID_", UDID_);
		params.put("platformVersion_", platformVersion_);
		params.put("URL_", URL_);
		params.put("appUrl_", appUrl_);
		params.put("screenshotPath_", screenshotPath_);
		UDID1 = UDID_;
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
		objPLPageObjets = new PLPageObjects(wd);
		objPDPageObject = new PDPageObject(wd);
		objWishlistPageObject = new WishlistPageObject(wd);
		objPLPageObjets = new PLPageObjects(wd);
		objProfileLoginPageObject = new ProfileLoginPageObject(wd);
		objCartPageObject = new CartPageObject(wd);
		objAssertionPageObject = new AssertionPageObject(wd);
		objEditAdressPageObject = new EditAdressPageObject(wd);
		objAddNewAdressPageObjects = new AddNewAdressPageObjects(wd);
		objPaymentPageObject = new PaymentPageObject(wd);
		objHomePageObject2 = new HomePageObject2(wd);
		objiOSGenericMethods = new iOSGenericMethods(wd);
		objProfileDetailsPageObject = new ProfileDetailsPageObject(wd);
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