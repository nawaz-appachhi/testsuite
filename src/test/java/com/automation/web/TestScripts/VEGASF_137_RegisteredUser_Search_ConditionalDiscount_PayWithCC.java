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
 * @author 300020817 - Irfan_Shariff
 * TEST STEPS
 * 1. Login In Myntra (using Registered Email & Password).
 * 2. Reset Wishlist, Cart & Saved Addresses.
 * 3. Search any item from Golden Set.
 * 4. Save item from PLP (ie, Item should go to Wishlist).
 * 5. From PLP go to PDP by clicking on the item.
 * 6. From PLP add that item to Bag by selecting a Size.
 * 7. Click on 'Contact Us' link.
 * 8. Click on Cart icon.
 * 9. Click on Place Order.
 * 10. Add New Address & save it.
 * 11. Click on View Details.
 * 12. Click on Continue button.
 * 13. Select Credit/Debit Card option in payment page.
 * 14. Quit Browser.
 */

public class VEGASF_137_RegisteredUser_Search_ConditionalDiscount_PayWithCC extends GlobalVariables {

	// Only methods need to call
	// all class reference create here

	GenericMethods objGenericMethods;
	DriverInit objDriverInit;
	HeaderPageObject objHeaderPageObject;
	LoginPageObject objLoginPageObject;
	PLPPageObject objPLPPageObject;
	PDPObject objPDPPageObject;
	CartPageObject objCartPageObject;
	AddressPageObjects objAddressPageObject;
	PaymentPageObjects objPaymentPageObjects;
	WishlistPageObjects objWishlistPageObject;

	public WebDriver driver;

	String testName = "VEGASF_FF_137";

	@BeforeTest
	public void beforeTest() {

		// Initial browser Objects and methods objects
		objDriverInit = new DriverInit();
		System.out.println("Browser Type : " + System.getProperty("browserType"));
		System.out.println("URL : " + System.getProperty("url"));
		driver = objDriverInit.getWebDriver(System.getProperty("browserType"), "Local");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		if (driver == null) {
			Assert.assertFalse(false, "WebDriver Object not Initited and Found Null Value");
		}
		objHeaderPageObject = new HeaderPageObject(driver);
		objGenericMethods = new GenericMethods(driver);
		objLoginPageObject = new LoginPageObject(driver);
		objPLPPageObject = new PLPPageObject(driver);
		objPDPPageObject = new PDPObject(driver);
		objCartPageObject = new CartPageObject(driver);
		objAddressPageObject = new AddressPageObjects(driver);
		objPaymentPageObjects = new PaymentPageObjects(driver);
		objWishlistPageObject = new WishlistPageObjects(driver);

		// Open URL
		objGenericMethods.openURL(System.getProperty("url"));
	}

	@Test(priority = 1)
	public void LoginInMyntra()  {
		System.out.println("=====================VEGASF_137_RegisteredUser_Search_ConditionalDiscount_PayWithCC_START=====================");
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
		objWishlistPageObject.resetWishlist();
		objCartPageObject.resetCart();
		objAddressPageObject.resetAddress();
	}

	@Test(priority = 3)
	public void SearchItem()  {
		System.out.println("Search Item");
		objHeaderPageObject.SearchInputBox(objGenericMethods.getValueByKey(testName, "SearchItem"));
		//objHeaderPageObject.VerifyAutoSuggestSearch();
		objHeaderPageObject.ClickOnSearchLens();
//		objPLPPageObject.verifySearchResultTittle();
//		objPLPPageObject.verifyCategoriesTitle();
//		objPLPPageObject.verifyBrandTitle();
//		objPLPPageObject.verifyPriceTitle();
//		objPLPPageObject.verifyColourTitle();
//		objPLPPageObject.verifyDiscountTitle();
	}

//	@Test(priority = 4)
//	public void SaveItemFromPLP()  {
//		System.out.println("Save Item From PLP");
//		objPLPPageObject.SaveFirstProduct();
////		objPLPPageObject.verifyProductBrand(objGenericMethods.getValueByKey(testName, "productCode"));
////		objPLPPageObject.verifyProductName(objGenericMethods.getValueByKey(testName, "productCode"));
////		objPLPPageObject.verifyStrickedPrice(objGenericMethods.getValueByKey(testName, "productCode"));
////		objPLPPageObject.verifyDiscountedPrice(objGenericMethods.getValueByKey(testName, "productCode"));
////		objPLPPageObject.verifyDiscountPercentage(objGenericMethods.getValueByKey(testName, "productCode"));
////		objPLPPageObject.PlpAddToWishlist(objGenericMethods.getValueByKey(testName, "productCode"));
//		Reporter.log("Item successfully added to wishlist");
//	}

	
	/**
	* Name: Irfan Shariff
	* Description: Here small sleep is required before "objPLPObject.AddToBagFirstProduct();", if not given the item fails to select the size.
	* Modified by <Irfan Shariff>
	*/
//	@Test(priority = 5)
//	public void AddToBag()  {
//		System.out.println("Add To Bag");
//		objPDPPageObject.ClickOnSaveBtn();
//		objPLPPageObject.AddToBagFirstProduct();
////		objPLPPageObject.PlpAddToBag(objGenericMethods.getValueByKey(testName, "productCode"),
////				objGenericMethods.getValueByKey(testName, "size"));
//		Reporter.log("Product clicked from list page");
//	}
//
//	// Do not uncomment as this is not working on all test environment
////	@Test(priority = 6)
////	public void ContactUs()  {
////		System.out.println("Contact Us");
////		objHeaderPageObject.ClickContactUs();
////	}
//
//	@Test(priority = 7)
//	public void CartIcon()  {
//		System.out.println("Cart Icon");
//		objHeaderPageObject.ClickOnCartIcon();
//	}

	@Test(priority = 7)//comment it when the wish list is working
	public void newMethodForPdp() {
		objPDPPageObject.ClickSizeButtons();
		objPDPPageObject.ClickAddToBagBtn();
		objPDPPageObject.HoverAllSmallThumbnails();
		objPDPPageObject.verifyPDPTitleFromPDP();
		objPDPPageObject.VerifySellingPriceFromPDP();
		objPDPPageObject.VerifyLargeThumbnailFromPDP();
		objPDPPageObject.VerifySmallThumbnailsFromPDP();
		objPDPPageObject.ClickGoToBagBtn();
		objCartPageObject.VerfiyTotalPrice();
		objCartPageObject.VerfiyProductIsAddedToCart();
	}
	
	@Test(priority = 8)
	public void PlaceOrder()  {
		System.out.println("Place Order");
		objCartPageObject.ClickPlaceOrder();
	}
	/**
	 * Modified by  :Anu
	 * Address details is reading from testdata.ini 
	 */

	@Test(priority = 9)
	public void AddressPage()  {
		System.out.println("Address Page");
		objAddressPageObject.AddAddress(objGenericMethods.getValueByKey(testName, "Pincode"),
				objGenericMethods.getValueByKey(testName, "Name"), objGenericMethods.getValueByKey(testName, "Mobile"),
				objGenericMethods.getValueByKey(testName, "Address"),
				objGenericMethods.getValueByKey(testName, "Locality"));
		objAddressPageObject.SelectHomeAddressType();
		objAddressPageObject.clickToSaveAddress();
		objAddressPageObject.VerifyAddressAdded();
		objAddressPageObject.VerifyPriceDetails();
	}

	@Test(priority = 10)
	public void CompletePaymentUsingCreditOrDebiCard()  {
		System.out.println("Complete Payment Using Credit Or DebiCard");
		objAddressPageObject.ClickToContinue();
		objPaymentPageObjects.VerifyOrderSumamry();
		objPaymentPageObjects.VerifyDeliveryAddress();
		objPaymentPageObjects.SelectCreditDebitCard();
		objPaymentPageObjects.CreditDebitPaymentComplete(objGenericMethods.getValueByKey(testName, "CardNumber"),
				objGenericMethods.getValueByKey(testName, "CardHolderName"), objGenericMethods.getValueByKey(testName, "CVVnumber"));
		objPaymentPageObjects.handelAlert();
		objPaymentPageObjects.VerifyOrderNumber();
		objPaymentPageObjects.GoToOrderDetailsPage();
		objPaymentPageObjects.VerifyOrdernumber();
	}
	
	@Test(priority = 11)
	public void LogOut()  {
		objLoginPageObject.LogOut();
		objLoginPageObject.readSession("AfterLogout");
	}

	@AfterTest
	public void afterTest() {
		// quit browser object
		// check condition before closing or quite
		driver.quit();
		System.out.println("=====================VEGASF_137_RegisteredUser_Search_ConditionalDiscount_PayWithCC_END=======================");
	}
}