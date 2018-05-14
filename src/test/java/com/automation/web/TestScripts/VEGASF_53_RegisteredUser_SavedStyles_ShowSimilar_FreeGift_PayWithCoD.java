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
 * Modified By: Aishurya
 * Reason: Removed product code
 */

public class VEGASF_53_RegisteredUser_SavedStyles_ShowSimilar_FreeGift_PayWithCoD extends GlobalVariables {

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

	public WebDriver driver;

	String testName="VEGASF_FF_53";

	@Test(priority = 1)
	public void LoginInMyntra()  {
		System.out.println("=====================VEGASF_53_RegisteredUser_SavedStyles_ShowSimilar_FreeGift_PayWithCoD_START=====================");
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
		objWishlistPageObjects.resetWishlist();
		objAddressPageObjects.resetAddress();
		objCartPageObject.resetCart();
	}

	@Test(priority = 3)
	public void SearchItem() {
		System.out.println("Search Item");
		objHeaderPageObject.SearchInputBox(objGenericMethods.getValueByKey(testName,"SearchItem"));
//		objHeaderPageObject.VerifyAutoSuggestSearch();
		objHeaderPageObject.ClickOnSearchLens();
//		objPLPPageObject.clickOnPromotions();
//		objPLPPageObject.clickOnFreeGiftCheckBox();
		//objPLPPageObject.verifyCategoriesTitle();
//		objPLPPageObject.verifyColourTitle();
//		objPLPPageObject.verifyPriceTitle();
//		objPLPPageObject.ClickOnFirstProduct();
	}

	@Test(priority = 4)
	public void SaveProduct() {
		System.out.println("Save Product");
		objPDPObject.ClickSizeButtons();
		objPDPObject.ClickOnSaveBtn();
		objPDPObject.HoverAllSmallThumbnails();
		objPDPObject.verifyPDPTitleFromPDP();
		objPDPObject.VerifySellingPriceFromPDP();
		objPDPObject.VerifyLargeThumbnailFromPDP();
		objPDPObject.VerifySmallThumbnailsFromPDP();
	}
	
	@Test(priority = 5)
	public void GoToWishlist() {
		System.out.println("Go To Wishlist");
		objGenericMethods.HoverOnWebElement(objHeaderPageObject.getUserIcon());
		objHeaderPageObject.ClickOnWishlist();
		objWishlistPageObjects.VerifyWishlistTitle();
	}
	
	@Test(priority = 6)
	public void MoveProductsToCartFromWishlist() {
		System.out.println("Move Products To Cart From Wishlist");
		objWishlistPageObjects.ClickMoveToBagButton();
		objWishlistPageObjects.ClickDoneButton();
		objWishlistPageObjects.VerifyProductMovedFromWishlist();
	}

	@Test(priority = 7)
	public void GoToCart() {
		System.out.println("Go To Cart");
		objHeaderPageObject.ClickOnCart();
		objCartPageObject.VerifyMyProductTitle();
		objCartPageObject.VerfiyProductIsAddedToCart();
		objCartPageObject.VerfiyTotalPrice();
	}
	
	@Test(priority = 8)
	public void ApplyCoupon() {	
		System.out.println("Apply Coupon");
//		objCartPageObject.ClickApplyCouponBtn();
//		objCartPageObject.ClickCancelButton();
	}
	
	@Test(priority = 9)
	public void PlaceOrder() {
		System.out.println("Place Order");
		objCartPageObject.ClickPlaceOrder();
	}
	
	@Test(priority = 10)
	public void EditAddress() {
		objAddressPageObjects.AddAddress(objGenericMethods.getValueByKey(testName, "Pincode"),
				objGenericMethods.getValueByKey(testName, "Name"), objGenericMethods.getValueByKey(testName, "Mobile"),
				objGenericMethods.getValueByKey(testName, "Address"),
				objGenericMethods.getValueByKey(testName, "Locality"));
		objAddressPageObjects.SelectOfficeAddressType();
		objAddressPageObjects.clickToSaveAddress();
		objAddressPageObjects.VerifyAddressAdded();
		objAddressPageObjects.VerifyPriceDetails();
		System.out.println("Edit Address");
		objAddressPageObjects.clickToEditAddress();
		objAddressPageObjects.EditNameTextField("VegaSF_FF_53 " + objGenericMethods.datetime("ddMMyyyyHHmmss"));
		objAddressPageObjects.clickToSaveAddress();
		objAddressPageObjects.VerifyAddressAdded();
	}

	@Test(priority = 11)
	public void CompletePayment() {
		System.out.println("Complete Payment");
		objGenericMethods.waitDriverWhenReady(objAddressPageObjects.getContinue(), "Continue");
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
		System.out.println("=====================VEGASF_53_RegisteredUser_SavedStyles_ShowSimilar_FreeGift_PayWithCoD_END=======================");
	}

}
