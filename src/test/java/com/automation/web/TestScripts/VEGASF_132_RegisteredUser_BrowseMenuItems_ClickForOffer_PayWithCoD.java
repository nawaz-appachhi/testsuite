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
 * 3. Search item from Men Menu (Men -> Topwear -> T-Shirts).
 * 4. Save item from PLP (ie, Item should go to Wishlist).
 * 5. From PLP go to PDP by clicking on the item.
 * 6. Click on 'Best Offer Link'.
 * 7. Click on 'Size Chart Link'.
 * 8. Select a size.
 * 9. Click on 'Add to Bag' button.
 * 10. Click on 'Contact Us' link.
 * 11. Click on Myntra Logo, and search for a product which has a Offer.
 * 12. Select a size.
 * 13. Click on 'Add to Bag' button.
 * 14. Click on Cart icon.
 * 15. Click on 'Place Order' button.
 * 16. Add New Address.
 * 17. Click on View Details.
 * 18. Click on Continue button.
 * 19. Select Gift Card option in payment page.
 * 20. Enter Gift Card Data.
 * 21. Select Net Banking.
 * 22. Enter Net Banking Data.
 * 23. Quit Browser.
 */
/**
 * Steps to do automation
 * open browser in the system and enter valid url
 * login with the valid Email-id and password
 * Go to Home Page 
 * Search the product using menu item e.g. Men -> Topwear - T-Shirts
 * save any product
 * Click on best product price of the product in pdp page 
 * click on size chart
 * Click for offer
 * Add New address - Home
 * Continue payment through Manual GC + Online
 *
 */
public class VEGASF_132_RegisteredUser_BrowseMenuItems_ClickForOffer_PayWithCoD extends GlobalVariables {
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
	String testName = "VEGASF_FF_132";

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
		System.out.println("=====================VEGASF_132_RegisteredUser_BrowseMenuItems_ClickForOffer_PayWithCoD_START=====================");
		System.out.println("Login In Myntra");
		objGenericMethods.HoverOnWebElement(objHeaderPageObject.getUserIcon());
		objHeaderPageObject.LoginUnderUserIcon();
		objLoginPageObject.Login(objGenericMethods.getValueByKey(testName, "UserName"),
				objGenericMethods.getValueByKey(testName, "Password"));
		objLoginPageObject.LogInButtonClick();
		objLoginPageObject.VerifyUserEmailId();
	}

	@Test(priority = 2)
	public void ResetAll()  {
		System.out.println("Reset All");
		objWishlistPageObject.resetWishlist();
		objCartPageObject.resetCart();
		objAddressPageObject.resetAddress();
	}

	/**
	 * Modified by : Irfan Shariff
	 * Description : Added Thread.sleep(MIDSLEEP) before "hovering on Men". If this sleep not added hover function fails.
	 */
//	@Test(priority = 3)
//	public void TShirtsFromMenMenu()  {
//		
//		// Need to uncomment below 3 lines once once SFQA is up
//		System.out.println("Men >> Casual Shirts");
//		objGenericMethods.HoverOnWebElement(objHeaderPageObject.getHeaderMenu("Men"));
//		objHeaderPageObject.clickOnSubMenuUnderMen("Casual Shirts");	
////		objHeaderPageObject.ClickOnSearchLens();
//		objPLPPageObject.VerifyProductDetails();
//		objPLPPageObject.verifySearchResultTittle();
////		objPLPPageObject.verifyCategoriesTitle();
//		objPLPPageObject.verifyBrandTitle();
//		objPLPPageObject.verifyPriceTitle();
//		objPLPPageObject.verifyColourTitle();
//		objPLPPageObject.verifyDiscountTitle();	
//	}
//
//	@Test(priority = 4)
//	public void SaveItemFromPLP()  {
//		System.out.println("Save Item From PLP");		
//		objPLPPageObject.SaveFirstProduct();
//		Reporter.log("Item successfully added to wishlist");
//	}
//
//	@Test(priority = 5)
//	public void NavigatefromPLPtoPDP()  {
//		System.out.println("Navigate from PLP to PDP");
//		objPLPPageObject.ClickOnFirstProduct();
//		Reporter.log("Product clicked from list page");
//		objPDPPageObject.verifyPDPTitleFromPDP();
//		objPDPPageObject.VerifySellingPriceFromPDP();
//		objPDPPageObject.VerifyLargeThumbnailFromPDP();
//		objPDPPageObject.VerifySmallThumbnailsFromPDP();
//		objPDPPageObject.ClickOnSizeChartLink();
//		objPDPPageObject.VerifyBestOfferLinkFromPDP();
//		objPDPPageObject.ClickSizeButtons();
//		objPDPPageObject.ClickAddToBagBtn();
//		Reporter.log("Product clicked from PDP page");
//	}

	/*
	 * @Test(priority = 6) public void ContactUs()  {
	 * System.out.println("Contact Us"); objHeaderPageObject.ClickContactUs(); }
	 */
	
	/**
	 * Name: Irfan_Shariff
	 * Description 1 : For "Click For Offer" product is not available in sfqa env. Once up, "SearchItem" would be changed accordingly in 'test-data.ini' file.
	 * Description 2 : Added Thread.sleep(MIDSLEEP) before "Searching an Item". If this sleep not added search function fails.
	 * Created By: Irfan_Shariff
	 */
	@Test(priority = 7)
	public void ClickForOffer()  {
		objHeaderPageObject.SearchInputBox(objGenericMethods.getValueByKey(testName, "SearchItem"));
//		objHeaderPageObject.VerifyAutoSuggestSearch();
		objHeaderPageObject.ClickOnSearchLens();
//		objPLPPageObject.VerifyProductDetails();
//		objPLPPageObject.verifySearchResultTittle();
//		objPLPPageObject.verifyCategoriesTitle();
//		objPLPPageObject.verifyPriceTitle();
//		objPLPPageObject.verifyColourTitle();
//		objPLPPageObject.verifyDiscountTitle();
//		objPLPPageObject.ClickOnFirstProduct();
//		objPDPPageObject.verifyPDPTitleFromPDP();
//		objPDPPageObject.VerifySellingPriceFromPDP();
//		objPDPPageObject.VerifyLargeThumbnailFromPDP();
//		objPDPPageObject.VerifySmallThumbnailsFromPDP();
		objPDPPageObject.VerifyBestOfferLinkFromPDP();
		objPDPPageObject.ClickSizeButtons();
		objPDPPageObject.ClickAddToBagBtn();
		objPDPPageObject.HoverAllSmallThumbnails();
		objPDPPageObject.verifyPDPTitleFromPDP();
		objPDPPageObject.VerifySellingPriceFromPDP();
		objPDPPageObject.VerifyLargeThumbnailFromPDP();
		objPDPPageObject.VerifySmallThumbnailsFromPDP();		
	}

	@Test(priority = 8)
	public void CartIcon()  {
		System.out.println("Cart Icon");
		objHeaderPageObject.ClickOnCartIcon();
		objCartPageObject.VerfiyProductIsAddedToCart();
		objCartPageObject.VerfiyTotalPrice();
	}

	@Test(priority = 9)
	public void PlaceOrder()  {
		System.out.println("Place Order");
		objCartPageObject.ClickPlaceOrder();
	}

	/**
	 * Modified by :Anu Address details is reading from testdata.ini
	 */
	@Test(priority = 10)
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

	@Test(priority = 12)
	public void CompletePayment() {
		System.out.println("Complete Payment");
		objAddressPageObject.ClickToContinue();
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


	@AfterTest
	public void afterTest() {
		// quit browser object
		// check condition before closing or quite
		driver.quit();
		System.out.println("=====================VEGASF_132_RegisteredUser_BrowseMenuItems_ClickForOffer_PayWithCoD_END=======================");
	}
}