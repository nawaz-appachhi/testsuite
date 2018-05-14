package com.automation.web.TestScripts;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.automation.core.Common.GlobalVariables;
import com.automation.core.web.DriverInit;
import com.automation.core.web.GenericMethods;
import com.automation.web.ObjectRepository.Pages.AddressPage.AddressPageObjects;
import com.automation.web.ObjectRepository.Pages.Cart.CartPageObject;
import com.automation.web.ObjectRepository.Pages.Header.HeaderPageObject;
import com.automation.web.ObjectRepository.Pages.Login.LoginPageObject;
import com.automation.web.ObjectRepository.Pages.PDP.PDPObject;
import com.automation.web.ObjectRepository.Pages.PLP.PLPPageObject;
import com.automation.web.ObjectRepository.Pages.PaymentPage.PaymentPageObjects;
import com.automation.web.ObjectRepository.Pages.WishlistPage.WishlistPageObjects;

/**
 * @author 300019224 - Aishurya
 * LoginWithFacebook
 * SearchItem
 * Sort_PriceLtoH
 * CheckForDelivery
 * MoveToBag
 * ApplyCoupan
 * BuyOneGetOne
 * AddNewAddress_Home
 * PaymenWithCredit/DebitCard
 */
public class VEGASF_298_FB_User_chkDeliveryOpt_Coupon_BOGO extends GlobalVariables{
	
	GenericMethods objGenericMethods;
	DriverInit objDriverInit;
	HeaderPageObject objHeaderPageObject;
	LoginPageObject objLoginPageObject;
	PLPPageObject objPLPPageObject;
	PDPObject objPDPObject;
	WishlistPageObjects objWishlistPageObjects;
	CartPageObject objCartPageObject;
	AddressPageObjects objAddressPageObjects;
	PaymentPageObjects objPaymentPageObjects;

	public WebDriver driver;
	String testName = "VEGASF_298";

	@BeforeTest
	public void beforeTest() {

		// Initial browser Objects and methods objects
		objDriverInit = new DriverInit();
		System.out.println("Browser Type : " + System.getProperty("browserType"));
		System.out.println("URL : " + System.getProperty("url"));
		driver = objDriverInit.getWebDriver(System.getProperty("browserType"), "Local");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		if (driver == null) {
			Assert.assertFalse(false, "WebDriver Object not Initited and Found Null Value");
		}
		objGenericMethods = new GenericMethods(driver);
		objHeaderPageObject = new HeaderPageObject(driver);
		objLoginPageObject = new LoginPageObject(driver);
		objPLPPageObject = new PLPPageObject(driver);
		objPDPObject = new PDPObject(driver);
		objWishlistPageObjects = new WishlistPageObjects(driver);
		objCartPageObject = new CartPageObject(driver);
		objAddressPageObjects = new AddressPageObjects(driver);
		objPaymentPageObjects = new PaymentPageObjects(driver);

		objGenericMethods.openURL(System.getProperty("url"));
	}

//	@Test(priority = 1)
//	public void LoginWithFacebook()  {
//		System.out.println("=====================VEGASF_298_START=====================");
//		System.out.println("Login In Myntra");
//		objGenericMethods.HoverOnWebElement(objHeaderPageObject.getUserIcon());
//		objHeaderPageObject.LoginUnderUserIcon();
//		objLoginPageObject.readSession("BoforeLogin");
//		objLoginPageObject.LoginWithFaceBook(objGenericMethods.getValueByKey(testName, "FbUserName"), objGenericMethods.getValueByKey(testName, "FbPassword"));
//		objLoginPageObject.VerifyUserEmailId();
//		objLoginPageObject.readSession("AfterLogin");
//			}
	
	@Test(priority = 1)
	public void Login()  {//Need to replace with Login with FaceBook
		System.out.println("=====================VEGASF_298_FB_User_chkDeliveryOpt_Coupon_BOGO_START=====================");
		System.out.println("Login In Myntra");
		objGenericMethods.HoverOnWebElement(objHeaderPageObject.getUserIcon());
		objHeaderPageObject.LoginUnderUserIcon();
		objLoginPageObject.readSession("BoforeLogin");
		objLoginPageObject.Login(objGenericMethods.getValueByKey(testName, "UserName"),
				objGenericMethods.getValueByKey(testName, "Password"));
		objLoginPageObject.LogInButtonClick();
		objLoginPageObject.VerifyUserEmailId();
		objLoginPageObject.readSession("AfterLogin");
	}
	
	
	@Test(priority = 2)
	public void ResetAll()  {
		System.out.println("Reset All");
		objCartPageObject.resetCart();
		objAddressPageObjects.resetAddress();
		objWishlistPageObjects.resetWishlist();
	}
	
	@Test(priority = 3)
	public void SearchItem()  {
		System.out.println("Search Item");
		objHeaderPageObject.SearchInputBox(objGenericMethods.getValueByKey(testName, "SearchItem"));
//		objHeaderPageObject.VerifyAutoSuggestSearch();
		objHeaderPageObject.ClickOnSearchLens();
	}
	
	@Test(priority = 4)
	public void Sort_PriceLtoH()   {
		System.out.println("Select first product from PLP page");
//		objPLPPageObject.HowerOnSortByDropdown();//sorting is changing the product so commented
//		objPLPPageObject.ClickOnSortDropdownByDroddownValue("Price: Low to High");
		System.out.println("Plp Page Assert Verification");
//		objPLPPageObject.verifySearchResultTittle();
//		objPLPPageObject.verifyCategoriesTitle();
//		objPLPPageObject.verifyColourTitle();
//		objPLPPageObject.verifyPriceTitle();
		objPLPPageObject.ClickOnFirstProduct();
	}
	
	@Test(priority = 5)
	public void CheckForDelivery()  {
		System.out.println("Check delivery Location");
		objPDPObject.verifyPDPTitleFromPDP();
		System.out.println("Check Delivery Location");
		objPDPObject.ClickCheckDeliveryOptionsLink();
		objPDPObject.EnterPincode(objGenericMethods.getValueByKey(testName, "Pincode"));
		objPDPObject.ClickCheckPinCodeLink();
		objPDPObject.VerifyCheckDelivaryOption();	
		}
	
	@Test(priority = 6)
	public void AddToBag(){
		System.out.println("Add to Bag");
		objGenericMethods.scrollDown(objPDPObject.getSizeButtons(), -200);
//		objPDPObject.ClickSizeButtons();
		objPDPObject.ClickAddToBagBtn();
		objPDPObject.verifyPDPTitleFromPDP();
 		objPDPObject.VerifySellingPriceFromPDP();
 		objPDPObject.VerifyLargeThumbnailFromPDP();
 		objPDPObject.VerifySmallThumbnailsFromPDP();
 		objPDPObject.VerifyBestOfferLinkFromPDP();
		System.out.println("Go to Cart");
		objHeaderPageObject.ClickOnCartIcon();
		objCartPageObject.VerfiyProductIsAddedToCart();
		objCartPageObject.VerfiyTotalPrice();
	}
	
	@Test(priority = 7)
	public void PlaceOrder() {
//		System.out.println("Apply Coupon");
//		objCartPageObject.ClickApplyCouponBtn();
//		objGenericMethods.applyCoupon(objGenericMethods.getValueByKey(testName, "coupon"));
//		objCartPageObject.ClickToApplyCoupon();
		System.out.println("Place Order");
		objCartPageObject.ClickPlaceOrder();
	}
	
	@Test(priority = 8)
	public void AddNewAddress_Home() {
		System.out.println("Add New Address");
		objAddressPageObjects.AddAddress(objGenericMethods.getValueByKey(testName, "Pincode"),
				objGenericMethods.getValueByKey(testName, "Name"), objGenericMethods.getValueByKey(testName, "Mobile"),
				objGenericMethods.getValueByKey(testName, "Address"),
				objGenericMethods.getValueByKey(testName, "Locality"));
		objAddressPageObjects.SelectOfficeAddressType();
		objAddressPageObjects.clickToSaveAddress();
		objAddressPageObjects.VerifyAddressAdded();
		objAddressPageObjects.VerifyPriceDetails();
		objAddressPageObjects.ClickToContinue();
	}
	
	@Test(priority = 10)
	public void LogOut()  {
		objLoginPageObject.LogOut();
		objLoginPageObject.readSession("AfterLogout");
	}
	
	@AfterTest
	public void afterTest() {
		// quit browser object
		// check condition before closing or quit
		driver.quit();
		System.out.println("=====================VEGASF_298_FB_User_chkDeliveryOpt_Coupon_BOGO_END=======================");
	}

}
