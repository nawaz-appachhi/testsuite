package com.automation.web.TestScripts;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

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
 * @author 300019224
 *
 */

/**
 * TEST STEPS 1. Login(Email registered user) 2. Reset Cart, Wishlist and
 * Address Pages 3. Search (using a keyword in the Golden Set) 4. PlP page
 * assertion verification 5. Add to bag from PLP and move to PDP 6. Save to
 * wishlist 7. Check delivery option and click for offer in PDP 8. Empty
 * wishlist and go to bag 9. Moveto wishlist from bag 10. Moveto bag from
 * wishlist & Place order 11.add address and Verify price details 12. Payment
 * using MyntraCredit And COD
 */



public class VEGASF_58_RegisteredUser_BrandProfile_BuyFromWishlist_ChangeAddress_PaywithCoD {

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
	String testName = "VEGASF_FF_58";

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

	@Test(priority = 1)
	public void LoginInMyntra()  {
		System.out.println("=====================VEGASF_58_RegisteredUser_BrandProfile_BuyFromWishlist_ChangeAddress_PaywithCoD_START=====================");
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
		driver.navigate().back();
		objCartPageObject.resetCart();
	}

	@Test(priority = 3)
	public void SearchItem()  {
		System.out.println("Search Item");
		objHeaderPageObject.SearchInputBox(objGenericMethods.getValueByKey(testName, "SearchItem"));
//		objHeaderPageObject.VerifyAutoSuggestSearch();
		objHeaderPageObject.ClickOnSearchLens();
	}

//	@Test(priority = 4)
//	public void PlpPageAssertVerification()  {
//		System.out.println("Plp Page Assert Verification");
//		objPLPPageObject.verifySearchResultTittle();
//		// objPLPPageObject.verifyBrandTitle();
////		objPLPPageObject.verifyCategoriesTitle();
//		objPLPPageObject.verifyColourTitle();
//		objPLPPageObject.verifyDiscountTitle();
//		objPLPPageObject.verifyPriceTitle();
//		// objPLPPageObject.verifyProductBrand(objGenericMethods.getValueByKey(testName,"productCode"));
//		// objPLPPageObject.verifyProductName(objGenericMethods.getValueByKey(testName,"productCode"));
//		// objPLPPageObject.verifyStrickedPrice(objGenericMethods.getValueByKey(testName,"productCode"));
//		// objPLPPageObject.verifyDiscountedPrice(objGenericMethods.getValueByKey(testName,"productCode"));
//		// objPLPPageObject.verifyDiscountPercentage(objGenericMethods.getValueByKey(testName,"productCode"));
//	}

//	@Test(priority = 5)
//	public void AddToBagFromPLP()  {
//		System.out.println("Add To Bag From PLP");
//		objPLPPageObject.AddToBagFirstProduct();
//
//		objPLPPageObject.ClickOnFirstProduct();
//		
//	}

	@Test(priority = 6)
	public void CheckDeliveryOptionAndClickForOfferInPDP()  {
		System.out.println("Verify All Products Details From PDP");
		//objPDPObject.ClickSizeButtons();//uncomment its when whishlist is working in mjint
		//objPDPObject.ClickAddToBagBtn();//uncomment its when whishlist is working in mjint
		//objPDPObject.verifyPDPTitleFromPDP();
		//objPDPObject.VerifySellingPriceFromPDP();//uncomment its when whishlist is working in mjint
		// objPDPObject.VerifyActualPriceFromPDP();
		// objPDPObject.VerifyDiscountPercentageFromPDP();
		objPDPObject.VerifyLargeThumbnailFromPDP();
		objPDPObject.VerifySmallThumbnailsFromPDP();
		objPDPObject.VerifyBestOfferLinkFromPDP();
		System.out.println("Check Delivery Option And Click For Offer In PDP");
		//objPDPObject.ClickOnBestOfferLink();
	//	objPDPObject.ClickOnSaveBtn();//uncomment its when whishlist is working in mjint
		objPDPObject.ClickCheckDeliveryOptionsLink();
		objPDPObject.EnterPincode(objGenericMethods.getValueByKey(testName, "Pincode"));
		objPDPObject.ClickCheckPinCodeLink();
		objPDPObject.VerifyCheckDelivaryOption();
	}

	/**
	 * @Modified by : Anu Changed the method name "Emptywishlist" to
	 *           "resetWishlist"
	 */
//	@Test(priority = 7)//uncomment its when wishlist is working in mjint
//	public void EmptyWishList()  {
//		System.out.println("Empty WishList");
//		objWishlistPageObjects.resetWishlist();
//
//	}

	/**
	 * @Modified by : Anu Changed the method name "GoToHome" to
	 *           "clickOnMyntraHeaderLogo"
	 */
//	@Test(priority = 8)//uncomment its when wishlist is working in mjint
//	public void MoveToWishListFromBag()  {
//		System.out.println("Move To WishList From Bag");
//		objHeaderPageObject.ClickOnCart();
//		objCartPageObject.ClickMoveToWishlist();
//		objHeaderPageObject.ClickOnMyntraHeaderLogoFromCartPage();
//		objGenericMethods.HoverOnWebElement(objHeaderPageObject.getUserIcon());
//		objHeaderPageObject.ClickonWishlistLink();
//		objWishlistPageObjects.ClickMoveToBagButton();
//		objWishlistPageObjects.ClickDoneButtons();
//		objHeaderPageObject.ClickOnCart();
//	}
	
	@Test(priority = 7) // comment it when the wish list is working
	public void newMethodForPdp()  {
		objGenericMethods.scrollDown(objPDPObject.getSizeButtons(), -200);
//		objPDPObject.ClickSizeButtons();
		objPDPObject.ClickAddToBagBtn();
		objPDPObject.HoverAllSmallThumbnails();
		objPDPObject.verifyPDPTitleFromPDP();
		objPDPObject.VerifySellingPriceFromPDP();
		objPDPObject.VerifyLargeThumbnailFromPDP();
		objPDPObject.VerifySmallThumbnailsFromPDP();
		objPDPObject.ClickGoToBagBtn();
	}
	
	@Test(priority = 9)
	public void PlaceOrder()  {
		System.out.println("Place Order");
		objCartPageObject.ClickPlaceOrder();
		objCartPageObject.VerfiyTotalPrice();
		objCartPageObject.VerfiyProductIsAddedToCart();
	}
	
	/**
	 * Modified by  :Anu
	 * Address details is reading from testdata.ini 
	 */
	@Test(priority = 10)
	public void RemoveAddress()  {
		System.out.println("Remove Address");
		//objAddressPageObjects.ClickAddNewAddress();
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

	@Test(priority = 11)
	public void PaymentMyntra_Cod()  {
		System.out.println("Payment Myntra Credit And Cod");
//		objPaymentPageObjects.SelectGiftCards();
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

	@AfterTest
	public void afterTest() {
		driver.quit();
		System.out.println("=====================VEGASF_58_RegisteredUser_BrandProfile_BuyFromWishlist_ChangeAddress_PaywithCoD_END=======================");
	}

}
