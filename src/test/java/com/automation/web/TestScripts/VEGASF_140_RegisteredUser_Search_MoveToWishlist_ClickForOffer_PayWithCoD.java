package com.automation.web.TestScripts;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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
 * @author 300020817 - Nitesh
 *
 * TEST STEPS
 * App(Android, IOS, PWA, Web App)
 * Email registered User
 * Home Page
 * Search (by clicking banner or nested banner on Home Page)
 * Save
 * Price
 * Show Similar
 * Move to wishlist
 * Click for offer
 * Remove address
 * Mynt+ COD
 */

public class VEGASF_140_RegisteredUser_Search_MoveToWishlist_ClickForOffer_PayWithCoD extends GlobalVariables {

	// Only methods need to call
	// all class reference create here

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

	public WebElement ExpectedAddressDetail;

	public WebDriver driver;

	String testName="VEGASF_FF_140";


	@Test(priority = 1)
	public void LoginInMyntra()  {
		System.out.println("=====================VEGASF_140_RegisteredUser_Search_MoveToWishlist_ClickForOffer_PayWithCoD_START=====================");
		System.out.println("Login In Myntra");
		objGenericMethods.HoverOnWebElement(objHeaderPageObject.getUserIcon());
		objHeaderPageObject.LoginUnderUserIcon();
		objLoginPageObject.readSession("BoforeLogin");
		objLoginPageObject.Login(objGenericMethods.getValueByKey(testName, "UserName"),objGenericMethods.getValueByKey(testName, "Password"));
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
	public void SearchItem() {
		System.out.println("Search Item");
		objHeaderPageObject.SearchInputBox(objGenericMethods.getValueByKey(testName,"SearchItem"));
		//  objHeaderPageObject.VerifyAutoSuggestSearch();
		objHeaderPageObject.ClickOnSearchLens();
		//        objPLPPageObject.VerifyProductDetails(); //comment it when the wish list is working
		//        objPLPPageObject.verifySearchResultTittle();
		////      objPLPPageObject.verifyBrandTitle();
		//    		objPLPPageObject.verifyCategoriesTitle();
		//    		objPLPPageObject.verifyPriceTitle();
		//		objPLPPageObject.verifyColourTitle();
		//		objPLPPageObject.verifyDiscountTitle();
	}

	//    @Test(priority = 4)//comment it when the wish list is working
	//    public void NavigatefromPLPtoPDP() 
	//    {
	//        System.out.println("Navigate from PLP to PDP");
	//        objPLPPageObject.ClickOnFirstProduct();
	//        objPDPObject.verifyPDPTitleFromPDP();
	//		objPDPObject.VerifySellingPriceFromPDP();
	//		objPDPObject.VerifyLargeThumbnailFromPDP();
	//		objPDPObject.VerifySmallThumbnailsFromPDP();
	//		objPDPObject.VerifyBestOfferLinkFromPDP();
	////      objPDPObject.VerifySimilarProductsHeaderFromPDP();
	//    }
	//    
	//    @Test(priority = 5)
	//    public void SaveProduct() 
	//    {
	//        System.out.println("Save Product");
	//        objPDPObject.ClickSizeButtons();
	//        objPDPObject.ClickOnSaveBtn();
	//    }
	//    
	//    @Test(priority = 6)
	//    public void MoveToBagFromWishlist() 
	//    {
	//        System.out.println("Move To Bag From Wishlist");
	//        objGenericMethods.HoverOnWebElement(objHeaderPageObject.getUserIcon());
	//        objHeaderPageObject.ClickOnWishlist();
	//        objWishlistPageObjects.VerifyWishlistTitle();
	//        objWishlistPageObjects.VerifyWishlistProductName();
	//        //objWishlistPageObjects.VerifyActualPrice();
	//        objWishlistPageObjects.VerifySellingPrice();
	//        //objWishlistPageObjects.VerifyDiscountPercentage();
	//        objWishlistPageObjects.ClickMoveToBagButton();
	//        objWishlistPageObjects.ClickDoneButton();
	//    }
	//    
	//    @Test(priority = 7)
	//    public void MoveToWishlistFromBag() 
	//    {
	//        System.out.println("Move To Wishlist From Bag");
	//        objHeaderPageObject.ClickOnCart();
	//        objCartPageObject.ClickMoveToWishlist();
	//        objHeaderPageObject.ClickOnMyntraHeaderLogoFromCartPage();
	//    }
	//    
	//    @Test(priority = 8)
	//    public void MoveAgainToBagFromWishlist() 
	//    {
	//        System.out.println("Move Again To Bag From Wishlist");
	//        objGenericMethods.HoverOnWebElement(objHeaderPageObject.getUserIcon());
	//        objHeaderPageObject.ClickOnWishlist();
	//        objWishlistPageObjects.VerifyWishlistTitle();
	//        objWishlistPageObjects.VerifyWishlistProductName();
	//        //objWishlistPageObjects.VerifyActualPrice();
	//        objWishlistPageObjects.VerifySellingPrice();
	//        //objWishlistPageObjects.VerifyDiscountPercentage();
	//        objWishlistPageObjects.ClickMoveToBagButton();
	//        objWishlistPageObjects.ClickDoneButton();
	//    }
	//    
	//    @Test(priority = 9)
	//    public void GoToCart() 
	//    {
	//        System.out.println("Go To Cart");
	//        objHeaderPageObject.ClickOnCart();
	//        objCartPageObject.VerfiyProductIsAddedToCart();
	//		objCartPageObject.VerfiyTotalPrice();
	//    }
	
	@Test(priority = 6)//comment it when the wish list is working
	public void newMethodForPdp() {
		 objPDPObject.ClickSizeButtons();
	     objPDPObject.ClickAddToBagBtn();
	     objPDPObject.verifyPDPTitleFromPDP();
	 	 objPDPObject.VerifySellingPriceFromPDP();
	 	 objPDPObject.VerifyLargeThumbnailFromPDP();
	 	 objPDPObject.VerifySmallThumbnailsFromPDP();
	 	 objPDPObject.VerifyBestOfferLinkFromPDP();
	     objPDPObject.ClickGoToBagBtn();
	     objCartPageObject.VerfiyProductIsAddedToCart();
	     objCartPageObject.VerfiyTotalPrice();
	}

	@Test(priority = 10)
	public void PlaceOrder() {
		System.out.println("Place Order");
		objCartPageObject.ClickPlaceOrder();
	}
	/**
	 * Modified by  :Anu
	 * Address details is reading from testdata.ini 
	 */
	@Test(priority = 11)
	public void AddAddress() {
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
	public void CompletePayment() {
		System.out.println("Complete Payment");
		objAddressPageObjects.ClickToContinue();
		objPaymentPageObjects.VerifyOrderSumamry();
		objPaymentPageObjects.VerifyDeliveryAddress();
		objPaymentPageObjects.VerifyFinalPayableAmount();
		objPaymentPageObjects.SelectCashOnDelivery();
		objPaymentPageObjects.ClickCODPayOnDeliveryBtn();
		objPaymentPageObjects.VerifyCODOrderConfirmedTxt();
		objPaymentPageObjects.VerifyOrderNumber();
		objPaymentPageObjects.GoToOrderDetailsPage();
		objPaymentPageObjects.VerifyOrdernumber();
	}
	@Test(priority = 13)
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
		driver.manage().window().maximize();
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
		System.out.println("=====================VEGASF_140_RegisteredUser_Search_MoveToWishlist_ClickForOffer_PayWithCoD_END=======================");
	}
}