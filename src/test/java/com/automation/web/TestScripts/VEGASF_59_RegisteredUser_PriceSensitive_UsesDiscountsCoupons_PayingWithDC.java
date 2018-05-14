package com.automation.web.TestScripts;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.testng.AssertJUnit;
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
 * @author 300021279
 *
 */

public class VEGASF_59_RegisteredUser_PriceSensitive_UsesDiscountsCoupons_PayingWithDC extends GlobalVariables {

	// Only methods need to call
	// all class reference create here

	GenericMethods objGenericMethods;
	DriverInit objDriverInit;
	PLPPageObject objPLPPageObject;
	HeaderPageObject objHeaderPageObject;
	LoginPageObject objLoginPageObject;
	PDPObject objPDPObject;
	CartPageObject objCartPageObject;
	AddressPageObjects objAddressPageObjects;
	PaymentPageObjects objPaymentPageObjects;
	WishlistPageObjects objWishlistPageObjects;

	public WebDriver driver;

	String testName = "VEGASF_FF_59";

	@Test(priority = 1)

	public void LoginInMyntra()  {
		System.out.println("=====================VEGASF_59_RegisteredUser_PriceSensitive_UsesDiscountsCoupons_PayingWithDC_START=====================");
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

	/**
	 * @Modified By :Anu Added "ResetAll" method
	 */
	@Test(priority = 2)
	public void ResetAll()  {
		System.out.println("Reset All");
		objWishlistPageObjects.resetWishlist();
		objAddressPageObjects.resetAddress();
		objCartPageObject.resetCart();
	}

	@Test(priority = 3)
	public void SearchItem()  {
		System.out.println("Search Item");
		objHeaderPageObject.SearchInputBox(objGenericMethods.getValueByKey(testName, "SearchItem"));
//		objHeaderPageObject.VerifyAutoSuggestSearch();
		objHeaderPageObject.ClickOnSearchLens();
//		objPLPPageObject.verifySearchResultTittle();
////		objPLPPageObject.verifyBrandTitle();
////		objPLPPageObject.verifyCategoriesTitle();
//		objPLPPageObject.verifyColourTitle();
//		objPLPPageObject.verifyDiscountTitle();
//		objPLPPageObject.verifyPriceTitle();
//		// objPLPPageObject.verifyProductBrand(objGenericMethods.getValueByKey(testName,
//		// "productCode"));
//		// objPLPPageObject.verifyProductName(objGenericMethods.getValueByKey(testName,
//		// "productCode"));
//		// objPLPPageObject.verifyStrickedPrice(objGenericMethods.getValueByKey(testName,
//		// "productCode"));
//		// objPLPPageObject.verifyDiscountedPrice(objGenericMethods.getValueByKey(testName,
//		// "productCode"));
//		// objPLPPageObject.verifyDiscountPercentage(objGenericMethods.getValueByKey(testName,
//		// "productCode"));
	}

//	@Test(priority = 4)
//	public void AddToWishlist()  {
//		System.out.println("Add To Wishlist");
//		objPLPPageObject.SaveFirstProduct();
//	}
//
//	@Test(priority = 5)
//	public void NavigateToPDP()  {
//		System.out.println("Navigate To PDP");
//		objPLPPageObject.ClickOnFirstProduct();
//		System.out.println("Verify All Products Details From PDP");
//		objPDPObject.verifyPDPTitleFromPDP();
//		objPDPObject.VerifySellingPriceFromPDP();
//		// objPDPObject.VerifyActualPriceFromPDP();
//		// objPDPObject.VerifyDiscountPercentageFromPDP();
//		objPDPObject.VerifyLargeThumbnailFromPDP();
//		objPDPObject.VerifySmallThumbnailsFromPDP();
//		objPDPObject.VerifyBestOfferLinkFromPDP();
//		// objPLPPageObject.getClickProductByProductCode(objGenericMethods.getValueByKey(testName,
//		// "productCode"));
//	}

	@Test(priority = 6)
	public void CheckForBestOffer()  {
		System.out.println("Check For Best Offer");
		objPDPObject.ClickOnBestOfferLink();
	}

	@Test(priority = 7)
	public void AddToBag()  {
		System.out.println("Add To Bag");
		objPDPObject.ClickSizeButtons();
		objPDPObject.ClickAddToBagBtn();
		objPDPObject.HoverAllSmallThumbnails();
		objPDPObject.verifyPDPTitleFromPDP();
		objPDPObject.VerifySellingPriceFromPDP();
		objPDPObject.VerifyLargeThumbnailFromPDP();
		objPDPObject.VerifySmallThumbnailsFromPDP();
	}

	@Test(priority = 8)
	public void GoToCart()  {
		System.out.println("Go To Cart");
		objPDPObject.ClickGoToBagBtn();
		objCartPageObject.VerfiyTotalPrice();
		objCartPageObject.VerfiyProductIsAddedToCart();
	}

	@Test(priority = 9)
	public void ApplyCoupon()  {
//		 System.out.println("Apply Coupon");
//		 objCartPageObject.ClickApplyCouponBtn();
//		objGenericMethods.applyCoupon(objGenericMethods.getValueByKey(testName, "coupon"));
//		 objCartPageObject.ClickCancelButton();
	}
	@Test(priority = 10)
	public void PlaceOrderFromCart()  {
		System.out.println("Place Order From Cart");
		objCartPageObject.ClickPlaceOrder();
	}
	

	/**
	 * Modified by : Anu Method to Add Address is added
	 * /**
	 * Modified by  :Anu
	 * Address details is reading from testdata.ini 
	 */
	@Test(priority = 11)
	public void AddressPage()  {
		System.out.println("Address Page");
		objAddressPageObjects.AddAddress(objGenericMethods.getValueByKey(testName, "Pincode"),
				objGenericMethods.getValueByKey(testName, "Name"), objGenericMethods.getValueByKey(testName, "Mobile"),
				objGenericMethods.getValueByKey(testName, "Address"),
				objGenericMethods.getValueByKey(testName, "Locality"));
		objAddressPageObjects.SelectHomeAddressType();
		objAddressPageObjects.clickToSaveAddress();
		objAddressPageObjects.VerifyAddressAdded();
	}

//	@Test(priority = 12)
//	public void ViewPriceDetails()  {
//		System.out.println("View Price Details");
//		objAddressPageObjects.ClickViewDetails();
//	}

	@Test(priority =13)
	public void PaymentWithCreditDebit_Card()  {
		System.out.println("Complete Payment");
		objAddressPageObjects.ClickToContinue();
		objPaymentPageObjects.VerifyOrderSumamry();
		objPaymentPageObjects.VerifyDeliveryAddress();
		objPaymentPageObjects.VerifyFinalPayableAmount();
		objPaymentPageObjects.SelectCreditDebitCard();
		objPaymentPageObjects.CreditDebitPaymentComplete(objGenericMethods.getValueByKey(testName, "CardNumber"),
				objGenericMethods.getValueByKey(testName, "CardHolderName"), objGenericMethods.getValueByKey(testName, "CVVnumber"));
		objPaymentPageObjects.VerifyOrderNumber();
		objPaymentPageObjects.GoToOrderDetailsPage();
		objPaymentPageObjects.VerifyOrdernumber();
	}
	@Test(priority = 14)
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
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		if (driver == null) {
			AssertJUnit.assertFalse(false);
		}
		objHeaderPageObject = new HeaderPageObject(driver);
		objGenericMethods = new GenericMethods(driver);
		objLoginPageObject = new LoginPageObject(driver);
		objPLPPageObject = new PLPPageObject(driver);
		objPDPObject = new PDPObject(driver);
		objCartPageObject = new CartPageObject(driver);
		objAddressPageObjects = new AddressPageObjects(driver);
		objPaymentPageObjects = new PaymentPageObjects(driver);
		objWishlistPageObjects = new WishlistPageObjects(driver);

		// Open URL
		objGenericMethods.openURL(System.getProperty("url"));
	}

	@AfterTest
	public void afterTest() {
		// quit browser object
		// check condition before closing or quite
		driver.quit();
		System.out.println("=====================VEGASF_59_RegisteredUser_PriceSensitive_UsesDiscountsCoupons_PayingWithDC_END=======================");
	}

}