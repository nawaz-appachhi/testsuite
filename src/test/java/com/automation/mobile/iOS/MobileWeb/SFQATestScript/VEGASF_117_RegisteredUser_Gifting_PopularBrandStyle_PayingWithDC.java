package com.automation.mobile.iOS.MobileWeb.SFQATestScript;
/**
 * @author Aravindanath
 * 
 * Steps
 * App(Android, IOS, PWA, Web App)
Email registered User
Home Page
Search (by clicking banner or nested banner on Home Page)
Sort (By Popularity)
Save
Empty Wishlist
Gift wrap
Apply
Personalized Coupons
View Details
Wallet
 */

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

import com.BaseIOSTest;
import com.automation.core.Common.AppiumServerUtils;
import com.automation.core.Common.GlobalVariables;
import com.automation.core.Common.MobileDrivers;
import com.automation.core.mobile.iOS.iOSGenericMethods;
import com.automation.mobile.iOS.MobileWeb.ObjectRepository.AddressPageObjects.AddressPageObjects;
import com.automation.mobile.iOS.MobileWeb.ObjectRepository.AddressPageObjects.Edit_ChangeButtonPageObjects;
import com.automation.mobile.iOS.MobileWeb.ObjectRepository.Bag.BagPageObjects;
import com.automation.mobile.iOS.MobileWeb.ObjectRepository.CategoriesObjects.HomeAndLivingCategoriesPageObjects;
import com.automation.mobile.iOS.MobileWeb.ObjectRepository.CategoriesObjects.KidsCategoriesPageObjects;
import com.automation.mobile.iOS.MobileWeb.ObjectRepository.CategoriesObjects.MenCategoriesPageObjects;
import com.automation.mobile.iOS.MobileWeb.ObjectRepository.CategoriesObjects.WomenCategoriesPageObjects;
import com.automation.mobile.iOS.MobileWeb.ObjectRepository.HomeObjects.HamburgerPageObjects;
import com.automation.mobile.iOS.MobileWeb.ObjectRepository.HomeObjects.HomePageObjects;
import com.automation.mobile.iOS.MobileWeb.ObjectRepository.HomeObjects.MenuPageObjects;
import com.automation.mobile.iOS.MobileWeb.ObjectRepository.PDPageObjects.PDPageObjects;
import com.automation.mobile.iOS.MobileWeb.ObjectRepository.PLPageObjects.FilterPageObjects;
import com.automation.mobile.iOS.MobileWeb.ObjectRepository.PLPageObjects.PLPageObjects;
import com.automation.mobile.iOS.MobileWeb.ObjectRepository.PaymentObjects.PaymentPageObjects;
import com.automation.mobile.iOS.MobileWeb.ObjectRepository.WishList.WishListPageObject;

public class VEGASF_117_RegisteredUser_Gifting_PopularBrandStyle_PayingWithDC extends BaseIOSTest{

	GlobalVariables objGlobalVariables;
	AppiumServerUtils objAppiumServerUtils;

	AddressPageObjects objAddressPageObjects;
	Edit_ChangeButtonPageObjects objEdit_ChangeButtonPageObjects;
	BagPageObjects objBagPageObjects;
	HomeAndLivingCategoriesPageObjects objHomeAndLivingCategoriesPageObjects;
	KidsCategoriesPageObjects objKidsCategoriesPageObjects;
	MenCategoriesPageObjects objMenCategoriesPageObjects;
	WomenCategoriesPageObjects objWomenCategoriesPageObjects;
	MenuPageObjects objMenuPageObjects;
	PaymentPageObjects objPaymentPageObjects;
	FilterPageObjects objFilterPageObjects;
	MobileDrivers objMobileDrivers;
	PDPageObjects objPDPageObject;
	HamburgerPageObjects objHamburgerPageObjects;
	HomePageObjects objHomePageObjects;
	WishListPageObject objWishListPageObject;
	PLPageObjects objPLPageObjects;
	//IOSDriver<IOSElement> wd;
	iOSGenericMethods objiOSGenericMethods;
	WishListPageObject objWishlistPageObject;


	String testName = "VEGASF_117";

	@Test(priority = 1)
	public void LoginWithEmail() throws InterruptedException, InvalidFileFormatException, IOException	{
		Reporter.log("LoginWithEmail test case started successfully");
		System.out.println("=====================VEGASF_117_START=====================");
		System.out.println("LoginWithEmail");
		objHomePageObjects.clickOnHamburgerButton();
		if (objHamburgerPageObjects.isLoggedIn())
		{
			objHamburgerPageObjects.clickOnMyAccount(); 
			objHamburgerPageObjects.clickOnLogout();
			objHomePageObjects.clickOnHamburgerButton();
		}
		objHamburgerPageObjects.clickOnSignInOption();
		objHamburgerPageObjects.enterEmailAddress(objiOSGenericMethods.getValueByKeyiOSWeb(testName, "UserName"),
				objiOSGenericMethods.getValueByKeyiOSWeb(testName, "Password"));
		objHamburgerPageObjects.clickOnSignInButton();
//		objHomePageObjects.clickOnHamburgerButton();
//		objHamburgerPageObjects.clickOnMyAccount();
//		objHamburgerPageObjects.assertUserEmailID(objiOSGenericMethods.getValueByKeyiOSWeb(testName, "UserName"));
//		wd.navigate().back();
	}  

	@Test(priority = 2)
	public void resetData() throws InterruptedException	{
		Reporter.log("resetData test case started successfully");
		System.out.println("Reset Bag, Wishlist & Address!");
		objBagPageObjects.resetBag();
		objWishListPageObject.resetWishlist();
		objAddressPageObjects.resetAddress();
	}

	@Test(priority = 3)
	public void SearchItem() throws InterruptedException, InvalidFileFormatException, IOException	{
		Reporter.log("SearchItem test case started successfully");
		System.out.println("Search Item!");
		objHomePageObjects.clickOnSearchIcon();
		objHomePageObjects.enterSearchItem(objiOSGenericMethods.getValueByKeyiOSWeb(testName, "SearchItem"));
//		objPLPageObjects.verifySearchResult("Result Search Title");
//		objHomePageObjects.getSearchAutoSuggestList();
//		objPLPageObjects.VerifyProductDetails();
	}

//	@Test(priority = 4)
//	public void Sort() throws InterruptedException, InvalidFileFormatException, IOException	{
//		Reporter.log("Sort test case started successfully");
//		System.out.println("Sort Item!");
//		objFilterPageObjects.clickOnSortButton();
//		objFilterPageObjects.clickOnPopularity();
//	}
	
	@Test(priority = 5)
	public void SaveProduct() throws InterruptedException {
		Reporter.log("SaveProduct test case started successfully");
		System.out.println("SaveProduct Item!");
//		objPDPageObject.clickFirstProductSearchResult();
		objPDPageObject.clickOnSaveButton();
		objPDPageObject.clickOnAddtoBag();
		objPDPageObject.selectSizeOfProduct();
		objPDPageObject.clickOnConfirmButton();
	}
	
	@Test(priority = 6)
	public void EmptyWishlist() throws InvalidFileFormatException, IOException, InterruptedException{
		Reporter.log("EmptyWishlist test case started successfully");
		System.out.println("EmptyWishlist Item!");
		objHomePageObjects.clickOnWishlistButton();
		objWishlistPageObject.VerifyWishlistPageTitle();
		objWishlistPageObject.VerifyProductTitle();
 		objWishlistPageObject.VerifySellingPrice();
		objWishListPageObject.RemoveAllItemsFromWishlist();
		objWishListPageObject.clickOnCartFromWishlist();

	}

	@Test(priority = 7)
	public void GiftWrap() throws InterruptedException	{
		Reporter.log("GiftWrap test case started successfully");
		System.out.println("GiftWrap Item!");
		objBagPageObjects.assertBagPageTitle("Bag");
		objBagPageObjects.clickOnViewDetails();
		objBagPageObjects.clickOnGiftWrap();
		objBagPageObjects.enterRecipientName("monika");
		objBagPageObjects.enterGiftMsg("myntra shopping");
		objBagPageObjects.enterSenderName("marry");
		objBagPageObjects.clickOnSaveGiftWrap();
		objBagPageObjects.clickOnPlaceOrder();
		
	}
//	@Test(priority = 8)
//	public void ApplyPersonalizedCoupon() throws InterruptedException {
	//Reporter.log("ApplyCoupon test case started successfully");
	//System.out.println("ApplyCoupon Item!");
//		objBagPageObjects.clickOnApplyCouponButton();
//		objBagPageObjects.EnterCouponCode("5676787878");
//		objBagPageObjects.clickOnApplyButton();
//	}
	
	
//	@Test(priority = 9)
//	public void ViewDetail() throws InterruptedException	{
//		Reporter.log("ViewDetail test case started successfully");
//		System.out.println("ViewDetail");
//		objBagPageObjects.clickOnViewDetails();
//		objBagPageObjects.clickOnPlaceOrder();
//	}

	@Test(priority = 10)
	public void AddNewAddress() throws InterruptedException, InvalidFileFormatException, IOException{
		Reporter.log("AddNewAddress test case started successfully");
		System.out.println("AddNewAddress");
		objAddressPageObjects.ClickOnPincodeAddress();
		objAddressPageObjects.enterPincone(objiOSGenericMethods.getValueByKeyiOSWeb(testName, "Pincode"));
		objAddressPageObjects.clickOnChoose();
		objAddressPageObjects.selectTownLocality(objiOSGenericMethods.getValueByKeyiOSWeb(testName, "Locality"));
		objAddressPageObjects.entername(objiOSGenericMethods.getValueByKeyiOSWeb(testName, "Name"));
		objAddressPageObjects.enteraddress(objiOSGenericMethods.getValueByKeyiOSWeb(testName, "Address"));
		objAddressPageObjects.enterMobileNumber(objiOSGenericMethods.getValueByKeyiOSWeb(testName, "MobileNumber"));
		objAddressPageObjects.clickOnOfficeCommercial();
		objAddressPageObjects.clickOnOpenOnSaturdays();
		objAddressPageObjects.clickOnSaveAddress();
		objAddressPageObjects.VerifyAddressAdded();
	}
	
	@Test(priority = 11)
	public void PaymentWithWallet() throws InterruptedException	{
		Reporter.log("Payment test case started successfully");
		System.out.println("Payment");
		objBagPageObjects.clickOnDemoId();
		objBagPageObjects.clickOnContinueButton();
		objBagPageObjects.clickOnDemoId();
		objBagPageObjects.assertPaymentPageTitle("Payment");
		objPaymentPageObjects.VerifyDeliveryAddress();
		//objPaymentPageObjects.ClickOnWallets();
		objPaymentPageObjects.clickOnCCandDC();
		
	}

	@Parameters({ "browserName_","deviceName_","UDID_","platformVersion_", "URL_", "appUrl_", "screenshotPath_","engine_", "platform_" })
	@BeforeTest
	public void beforeTest(@Optional("TD") String browserName_, @Optional("TD") String deviceName_, @Optional("TD") String UDID_, @Optional("TD") String platformVersion_, @Optional("TD") String URL_, @Optional("TD") String appUrl_, @Optional("TD") String screenshotPath_, @Optional("TD") String engine_, @Optional("TD") String platform_) throws Exception {
		objGlobalVariables = new GlobalVariables();
		objAppiumServerUtils = new AppiumServerUtils();
		objMobileDrivers = new MobileDrivers();
		Map<String, String> params = new HashMap<String, String>();
        params.put("browserName_", browserName_);
        params.put("deviceName_", deviceName_);
        params.put("UDID_", UDID_);
        params.put("platformVersion_", platformVersion_);
        params.put("URL_", URL_);
        params.put("appUrl_", appUrl_);
        params.put("screenshotPath_", screenshotPath_);
        
		 params.put("engine_", engine_);
        params.put("platform_", platform_);
		 if(!(params.get("engine_").equalsIgnoreCase("TD")))
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

		objiOSGenericMethods = new iOSGenericMethods(wd);
		wd.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		objAddressPageObjects = new AddressPageObjects(wd);
		objEdit_ChangeButtonPageObjects = new Edit_ChangeButtonPageObjects(wd);
		objBagPageObjects = new BagPageObjects(wd);
		objHomeAndLivingCategoriesPageObjects = new HomeAndLivingCategoriesPageObjects(wd);
		objKidsCategoriesPageObjects = new KidsCategoriesPageObjects(wd);
		objMenCategoriesPageObjects = new MenCategoriesPageObjects(wd);
		objWomenCategoriesPageObjects = new WomenCategoriesPageObjects(wd);
		objMenuPageObjects = new MenuPageObjects(wd);
		objPaymentPageObjects = new PaymentPageObjects(wd);
		objFilterPageObjects = new FilterPageObjects(wd);
		objPDPageObject = new PDPageObjects(wd);
		objHamburgerPageObjects = new HamburgerPageObjects(wd);
		objHomePageObjects = new HomePageObjects(wd);
		objWishListPageObject = new WishListPageObject(wd);
		objPLPageObjects = new PLPageObjects(wd);
		objWishlistPageObject = new WishListPageObject(wd);

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
		System.out.println("=====================VEGASF_117_END=====================");
	}

}
