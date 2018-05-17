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
 * Home Page	List page to PDP navigation
 * Save
 * Move to bag
 * Apply Coupon
 * Free Gift
 * Add New address - Office
 * Net Banking
 */

public class VEGASF_148_RegisteredUser_Wishlist_Coupon_FreeGift_PayWithDC extends GlobalVariables {
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
	
	String testName="VEGASF_FF_148";
	
	@Test(priority = 1)
	public void LoginInMyntra() {
		System.out.println("=====================VEGASF_148_RegisteredUser_Wishlist_Coupon_FreeGift_PayWithDC_START=====================");
		System.out.println("Login In Myntra");
		objGenericMethods.HoverOnWebElement(objHeaderPageObject.getUserIcon());
		objHeaderPageObject.LoginUnderUserIcon();
		objLoginPageObject.Login(objGenericMethods.getValueByKey(testName, "UserName"),objGenericMethods.getValueByKey(testName, "Password"));
		objLoginPageObject.LogInButtonClick();
		objLoginPageObject.VerifyUserEmailId();
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
		//objHeaderPageObject.VerifyAutoSuggestSearch();
		objHeaderPageObject.ClickOnSearchLens();
		//		objPLPPageObject.VerifyProductDetails(); 
		//		objPLPPageObject.verifySearchResultTittle();
		//		objPLPPageObject.verifyCategoriesTitle();
		//		objPLPPageObject.verifyPriceTitle();
		//		objPLPPageObject.verifyColourTitle();
		//		objPLPPageObject.verifyDiscountTitle();
	}
	
	//	@Test(priority = 4)
	//	public void SaveProduct() 	{
	//		System.out.println("Save Product");
	//		objPLPPageObject.ClickOnFirstProduct();
	//		objPDPObject.verifyPDPTitleFromPDP();
	//		objPDPObject.VerifySellingPriceFromPDP();
	//		objPDPObject.VerifyLargeThumbnailFromPDP();
	//		objPDPObject.VerifySmallThumbnailsFromPDP();
	//		objPDPObject.VerifyBestOfferLinkFromPDP();
	////		objPDPObject.VerifySimilarProductsHeaderFromPDP();	
	//		
	//	}
	
	@Test(priority = 5)
	public void MoveToBagFromWishlist() 	{
		System.out.println("Move To Bag From Wishlist");
		objPDPObject.ClickSizeButtons();
		objPDPObject.ClickOnSaveBtn();
		objGenericMethods.HoverOnWebElement(objHeaderPageObject.getUserIcon());
		objHeaderPageObject.ClickOnWishlist();
//	    objWishlistPageObjects.VerifyActualPrice();
		objWishlistPageObjects.VerifyWishlistTitle();
		objWishlistPageObjects.VerifyWishlistProductName();
		objWishlistPageObjects.VerifySellingPrice();
//		objWishlistPageObjects.VerifyDiscountPercentage();
		objWishlistPageObjects.ClickMoveToBagButton();
		objWishlistPageObjects.ClickDoneButton();	
	}
	
	@Test(priority = 6)
	public void GoToCart() 	{
		System.out.println("Go To Cart");
		objHeaderPageObject.ClickOnCart();
		objCartPageObject.VerfiyProductIsAddedToCart();
		objCartPageObject.VerfiyTotalPrice();
	}
	
//	@Test(priority = 7)
//	public void ApplyCoupon() 	{
//		System.out.println("Apply Coupon");
//		//		objGenericMethods.applyCoupon("sfqa");
//		objCartPageObject.ClickApplyCouponBtn();
//		objCartPageObject.EnterCouponCode(objGenericMethods.getValueByKey(testName, "coupon"));
//		objCartPageObject.ClickToApplyCoupon();
//		objCartPageObject.ClickCancelButton();
//	}
	
	@Test(priority = 8)
	public void PlaceOrder() 	{
		System.out.println("Place Order");
		objCartPageObject.ClickPlaceOrder();
	}
	
	/**
	 * Modified by  :Anu
	 * Address details is reading from testdata.ini 
	 */
	
	@Test(priority = 9)
	public void AddAddress() 	{
		System.out.println("Add Address");
		objAddressPageObjects.AddAddress(objGenericMethods.getValueByKey(testName, "Pincode"),
				objGenericMethods.getValueByKey(testName, "Name"), objGenericMethods.getValueByKey(testName, "Mobile"),
				objGenericMethods.getValueByKey(testName, "Address"),
				objGenericMethods.getValueByKey(testName, "Locality"));
		objAddressPageObjects.SelectOfficeAddressType();
		objAddressPageObjects.clickToSaveAddress();
		objAddressPageObjects.VerifyAddressAdded();
		objAddressPageObjects.VerifyPriceDetails();
	}
	
	@Test(priority =10)
	public void CompletePaymentUsingCreditOrDebiCard()  {
		System.out.println("Complete Payment Using Credit Or DebiCard");
		objAddressPageObjects.ClickToContinue();
		objPaymentPageObjects.VerifyOrderSumamry();
		objPaymentPageObjects.VerifyDeliveryAddress();
		objPaymentPageObjects.SelectCreditDebitCard();
		objPaymentPageObjects.CreditDebitPaymentComplete(objGenericMethods.getValueByKey(testName, "CardNumber"),
				objGenericMethods.getValueByKey(testName, "CardHolderName"), objGenericMethods.getValueByKey(testName, "CVVnumber"));
		objPaymentPageObjects.VerifyOrderNumber();
		objPaymentPageObjects.GoToOrderDetailsPage();
		objPaymentPageObjects.VerifyOrdernumber();
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
		System.out.println("=====================VEGASF_148_RegisteredUser_Wishlist_Coupon_FreeGift_PayWithDC_END=======================");
	}
}
