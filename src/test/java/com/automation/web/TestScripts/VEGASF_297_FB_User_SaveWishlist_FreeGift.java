package com.automation.web.TestScripts;

/**
 * VEGASF-297 test for Casual Browsing User
 * Test steps for VEGASF-300:
 * Facebook registered user - Login
 * Home Page	
 * Search - Using Autosuggest (use any query from Golden Set)	
 * Save Product	
 * Filter Discount	
 * View Size Chart
 * Add more from wishlist
 * Free Gift
 * Remove address
 */

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.automation.core.Common.GlobalVariables;
import com.automation.core.web.DriverInit;
import com.automation.core.web.GenericMethods;
import com.automation.web.ObjectRepository.Pages.Header.HeaderPageObject;
import com.automation.web.ObjectRepository.Pages.Login.LoginPageObject;
import com.automation.web.ObjectRepository.Pages.PLP.PLPPageObject;
import com.automation.web.ObjectRepository.Pages.PDP.*;
import com.automation.web.ObjectRepository.Pages.WishlistPage.*;
import com.automation.web.ObjectRepository.Pages.Cart.*;
import com.automation.web.ObjectRepository.Pages.AddressPage.*;
import com.automation.web.ObjectRepository.Pages.PaymentPage.*;

/**
 * @author 300019239 - Nitesh
 *
 */
public class VEGASF_297_FB_User_SaveWishlist_FreeGift extends GlobalVariables {

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

	String testName="VEGASF_297";

//	@Test(priority = 1)
//	public void LoginWithFacebook()  {
//		System.out.println("=====================VEGASF_297_START=====================");
//		objGenericMethods.HoverOnWebElement(objHeaderPageObject.getUserIcon());
//		objHeaderPageObject.LoginUnderUserIcon();
//		objLoginPageObject.readSession("BoforeLogin");
//		objLoginPageObject.LoginWithFaceBook(objGenericMethods.getValueByKey(testName, "FbEmailId"), objGenericMethods.getValueByKey(testName, "FbPassword"));
//		objLoginPageObject.VerifyUserEmailId();
//		objLoginPageObject.readSession("AfterLogin");
//	}
	@Test(priority = 1)
	public void Login()  {//Need to replace with Login with FaceBook
		System.out.println("=====================VEGASF_297_FB_User_SaveWishlist_FreeGift_START=====================");
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
		objAddressPageObjects.resetAddress();
		objWishlistPageObjects.resetWishlist();
		objCartPageObject.resetCart();
	}

	@Test(priority = 3)
	public void SearchItem() 	{
		System.out.println("Search Item");
		objHeaderPageObject.SearchInputBox(objGenericMethods.getValueByKey(testName,"SearchItem"));
		objHeaderPageObject.VerifyAutoSuggestSearch();
		objHeaderPageObject.ClickOnSearchLens();
		objPLPPageObject.verifySearchResultTittle();
		objPLPPageObject.VerifyProductDetails();
		objPLPPageObject.verifyCategoriesTitle();
		objPLPPageObject.verifyColourTitle();
		objPLPPageObject.verifyPriceTitle();
	}
	
	@Test(priority = 4)
	public void SaveToWishlistFromPLP(){
		System.out.println("Save second product available on plp page to wishlist");
		objPLPPageObject.clickToSaveToWishlist();
	}

	@Test(priority = 5)
	public void NavigatefromPLPtoPDP() 	{
		System.out.println("Navigate from PLP to PDP");
		objPLPPageObject.ClickOnFirstProduct();
		objPDPObject.VerifyBestOfferLinkFromPDP();
	}

	@Test(priority = 6)
	public void ViewSizeChart() 	{
		System.out.println("View Size chart on PDP page.");
		objPDPObject.ClickOnSizeChartLink();
	}
	
	@Test(priority = 7)
	public void AddToBag() 	{
		System.out.println("Add to Bag");
		objPDPObject.ClickSizeButtons();
		objPDPObject.ClickAddToBagBtn();
	}

	@Test(priority = 8)
	public void MoveProductFromWishlist() 	{
		System.out.println("Move Product From Wishlist");
		objGenericMethods.HoverOnWebElement(objHeaderPageObject.getUserIcon());
		objHeaderPageObject.ClickonWishlistLink();
		objWishlistPageObjects.ClickMoveToBagButton();
		objWishlistPageObjects.ClickSizeButtons();
		objWishlistPageObjects.ClickDoneButton();
		objWishlistPageObjects.VerifyProductMovedFromWishlist();
	}

	@Test(priority = 9)
	public void GoToCart()  	{
		System.out.println("Go To Cart");
		objHeaderPageObject.ClickOnCart();
		objCartPageObject.VerfiyProductIsAddedToCart();
		objCartPageObject.VerfiyTotalPrice();
	}

	@Test(priority = 10)
	public void PlaceOrder() 	{
		System.out.println("Place Order");
		objCartPageObject.ClickPlaceOrder();
	}

	@Test(priority = 11)
	public void AddAddress() 	{
		System.out.println("Add Address");
		objAddressPageObjects.AddAddress(objGenericMethods.getValueByKey(testName, "Pincode"),
				objGenericMethods.getValueByKey(testName, "Name"), objGenericMethods.getValueByKey(testName, "Mobile"),
				objGenericMethods.getValueByKey(testName, "Address"),
				objGenericMethods.getValueByKey(testName, "Locality"));
		objAddressPageObjects.SelectHomeAddressType();
		objAddressPageObjects.clickToSaveAddress();
		objAddressPageObjects.VerifyAddressAdded();
		objAddressPageObjects.VerifyPriceDetails();
	}
	
	@Test(priority = 12)
	public void LogOut()  {
		objLoginPageObject.LogOut();
		objLoginPageObject.readSession("AfterLogout");
	}

	@BeforeTest
	public void beforeTest() {
		// Initial browser Objects and methods objects
		objDriverInit = new DriverInit();
		System.out.println("Browser Type : " + System.getProperty("browserType"));
		System.out.println("URL : " + System.getProperty("url"));
		driver = objDriverInit.getWebDriver(System.getProperty("browserType"), "Local");
		driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
		if (driver == null) {
			Assert.assertFalse(false,"WebDriver Object not Initited and Found Null Value");
		}
		objHeaderPageObject = new HeaderPageObject(driver);
		objGenericMethods = new GenericMethods(driver);
		objLoginPageObject = new LoginPageObject(driver);
		objPLPPageObject = new PLPPageObject(driver);
		objPDPObject = new PDPObject(driver);
		objWishlistPageObjects = new WishlistPageObjects(driver);
		objCartPageObject = new CartPageObject(driver);
		objAddressPageObjects = new AddressPageObjects(driver);
		objPaymentPageObjects = new PaymentPageObjects(driver);

		// Open URL
		objGenericMethods.openURL(System.getProperty("url"));
	}

	@AfterTest
	public void afterTest() {
		// quit browser object
		// check condition before closing or quite
		driver.quit();
		System.out.println("=====================VEGASF_297_FB_User_SaveWishlist_FreeGift_END=======================");
	}
}
