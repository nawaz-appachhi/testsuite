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
import com.automation.web.ObjectRepository.Pages.AddressPage.AddressPageObjects;
import com.automation.web.ObjectRepository.Pages.Cart.CartPageObject;
import com.automation.web.ObjectRepository.Pages.Header.HeaderPageObject;
import com.automation.web.ObjectRepository.Pages.Login.LoginPageObject;
import com.automation.web.ObjectRepository.Pages.PDP.PDPObject;
import com.automation.web.ObjectRepository.Pages.PLP.PLPPageObject;
import com.automation.web.ObjectRepository.Pages.PaymentPage.PaymentPageObjects;
import com.automation.web.ObjectRepository.Pages.WishlistPage.WishlistPageObjects;

/**
 * @author 300019227 - Anu
 *
 */

/**
* TEST STEPS
* 1. Login(Email registered user)
* 2. Reset Cart, Wishlist and Address Pages
* 3. Search (Brand Profile - Search for a brand like Nike, Roadster)
* 4. Click on 'Show more products' link from PLP
* 5. Save the item from PLP and Add to Bag from PLP
* 6. Navigate to Bag page,Move the item from Cart to wishlist
* 7. Navigate to Wishlist, move the item to Bag
* 8. Navigate to Bag page and place the order
* 9. Add a new Address and Edit it
* 10 .Payment through Gift card and Netbanking   
*/

public class VEGASF_134_RegisteredUser_BrandProfile_ShowMoreProducts_PayWithGiftCardAndCC extends GlobalVariables {
	GenericMethods objGenericMethods;
	DriverInit objDriverInit;
	HeaderPageObject objHeaderPageObject;
	LoginPageObject objLoginPageObject;
	PLPPageObject objPLPObject;
	PDPObject objPDPObject;
	CartPageObject objCartObject;
	AddressPageObjects objAddressPageObjects;
	PaymentPageObjects objPaymentPageObjects;
	WishlistPageObjects objWishlistPageObject;

	public WebElement eleProductSellingdPrice;
	public WebElement eleProductActualPrice;
	public WebElement eleDiscountPercentage;

	public WebDriver driver;

	String testName = "VEGASF_FF_134";

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
		objPLPObject = new PLPPageObject(driver);
		objPDPObject = new PDPObject(driver);
		objCartObject = new CartPageObject(driver);
		objAddressPageObjects = new AddressPageObjects(driver);
		objPaymentPageObjects = new PaymentPageObjects(driver);
		objWishlistPageObject = new WishlistPageObjects(driver);

		// Open URL
		objGenericMethods.openURL(System.getProperty("url"));
	}

	@Test(priority = 1)
	public void LoginInMyntra()  {
		System.out.println("=====================VEGASF_134_RegisteredUser_BrandProfile_ShowMoreProducts_PayWithGiftCardAndCC_START=====================");
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
		objCartObject.resetCart();
		objAddressPageObjects.resetAddress();
		objWishlistPageObject.resetWishlist();
	}

	@Test(priority = 3)
	public void SearchItem()  {
		System.out.println("Search Item");
		objHeaderPageObject.SearchInputBox(objGenericMethods.getValueByKey(testName, "SearchItem"));
		//objHeaderPageObject.VerifyAutoSuggestSearch();
		objHeaderPageObject.ClickOnSearchLens();
//		objPLPObject.verifySearchResultTittle();
//		objPLPObject.verifyCategoriesTitle();
//		objPLPObject.verifyBrandTitle();
//		objPLPObject.verifyPriceTitle();
//		objPLPObject.verifyColourTitle();
//		objPLPObject.verifyDiscountTitle();
//		objPLPObject.verifyProductBrand(objGenericMethods.getValueByKey(testName, "productCode"));
//		objPLPObject.verifyProductName(objGenericMethods.getValueByKey(testName, "productCode"));
//		objPLPObject.verifyStrickedPrice(objGenericMethods.getValueByKey(testName, "productCode"));
//		objPLPObject.verifyDiscountedPrice(objGenericMethods.getValueByKey(testName, "productCode"));
//		objPLPObject.verifyDiscountPercentage(objGenericMethods.getValueByKey(testName, "productCode"));
	}

	/**
	* Name: Irfan Shariff
	* Description: Here small sleep is required before "objPLPObject.AddToBagFirstProduct();", if not given the item fails to select the size.
	* Modified by <Irfan Shariff>
	*/
//	@Test(priority = 4)
//	public void SaveFromPLP()  {
//		System.out.println("Save From PLP");
//		objPLPObject.ShowMoreProductsLinkClick();
//		objPLPObject.SaveFirstProduct();
//		objPLPObject.AddToBagFirstProduct();
//	}

	@Test(priority = 5)
	public void MoveToWishlistFromCart()  {
		System.out.println("Move To Wishlist From Cart");
		objPDPObject.ClickOnSaveBtn();
		objPDPObject.ClickSizeButtons();
		objPDPObject.ClickAddToBagBtn();
		objPDPObject.HoverAllSmallThumbnails();
		objPDPObject.verifyPDPTitleFromPDP();
		objPDPObject.VerifySellingPriceFromPDP();
		objPDPObject.VerifyLargeThumbnailFromPDP();
		objPDPObject.VerifySmallThumbnailsFromPDP();
		
		objHeaderPageObject.ClickOnCartIcon();
		objCartObject.VerfiyTotalPrice();
		objCartObject.VerfiyProductIsAddedToCart();
		objCartObject.ClickMoveToWishlist();
		objHeaderPageObject.ClickOnMyntraHeaderLogoFromCartPage();
		
		objGenericMethods.HoverOnWebElement(objHeaderPageObject.getUserIcon());
		objHeaderPageObject.ClickonWishlistLink();
		objWishlistPageObject.VerifyWishlistTitle();
		objWishlistPageObject.VerifyWishlistProductName();
//		objWishlistPageObject.VerifyActualPrice();
		objWishlistPageObject.VerifySellingPrice();
//		objWishlistPageObject.VerifyDiscountPercentage();
		objWishlistPageObject.ClickMoveToBagButton();
		objWishlistPageObject.ClickSizeButtons();
		objWishlistPageObject.ClickDoneButtons();
		objWishlistPageObject.VerifyProductMovedFromWishlist();
	}

	/**
	* Name: Irfan Shariff
	* Description 1: To click on 'Place Order' option, SMALLSLEEP thread is required.
	* Description 2: Cancelling coupon, as some products are not having coupons.
	* Modified by <Irfan Shariff>
	*/
	
	@Test(priority = 6)
	public void PlaceOrderFromCart()  {
		System.out.println("Place Order From Cart");
		objHeaderPageObject.ClickOnCartIcon();
		objCartObject.VerfiyProductIsAddedToCart();
		objCartObject.VerfiyTotalPrice();
//		objCartObject.ClickApplyCouponBtn();
//		objGenericMethods.applyCoupon(objGenericMethods.getValueByKey(testName, "coupon"));
//		objCartObject.ClickToApplyCoupon();
		objCartObject.ClickPlaceOrder();
	}

	/**
	* Name: Irfan Shariff
	* Description: Here try catch is uncommented so that 'Continue' button can be clicked.
	* Modified by <Irfan Shariff>
	*/
	/**
	 * Modified by  :Anu
	 * Address details is reading from testdata.ini 
	 */
	@Test(priority = 7)
	public void EditAddress()  {
		System.out.println("Edit Address");
		objAddressPageObjects.AddAddress(objGenericMethods.getValueByKey(testName, "Pincode"),
				objGenericMethods.getValueByKey(testName, "Name"), objGenericMethods.getValueByKey(testName, "Mobile"),
				objGenericMethods.getValueByKey(testName, "Address"),
				objGenericMethods.getValueByKey(testName, "Locality"));
		objAddressPageObjects.SelectOfficeAddressType();
		objAddressPageObjects.clickToSaveAddress();
		objAddressPageObjects.VerifyAddressAdded();
		objAddressPageObjects.VerifyPriceDetails();
		objAddressPageObjects.clickToEditAddress();
		objAddressPageObjects.EditNameTextField("VEGASF134 " + objGenericMethods.datetime("ddMMyyyyHHmmss"));
		objAddressPageObjects.clickToSaveAddress();
		objAddressPageObjects.VerifyAddressAdded();
	}

	@Test(priority =12)
	public void PaymentWithCreditDebit_Card()  {
		System.out.println("Complete Payment");
		objAddressPageObjects.ClickToContinue();
		objPaymentPageObjects.VerifyOrderSumamry();
		objPaymentPageObjects.VerifyDeliveryAddress();
		objPaymentPageObjects.VerifyFinalPayableAmount();
	//	objPaymentPageObjects.SelectGiftCards();
//		objPaymentPageObjects.EnterGiftCardData(objGenericMethods.getValueByKey(testName, "GiftCardNumber"), 
//				objGenericMethods.getValueByKey(testName, "GiftCardPin"));
		objPaymentPageObjects.SelectCreditDebitCard();
		objPaymentPageObjects.CreditDebitPaymentComplete(objGenericMethods.getValueByKey(testName, "CardNumber"),
				objGenericMethods.getValueByKey(testName, "CardHolderName"), objGenericMethods.getValueByKey(testName, "CVVnumber"));
		objPaymentPageObjects.VerifyOrderNumber();
		objPaymentPageObjects.GoToOrderDetailsPage();
		objPaymentPageObjects.VerifyOrdernumber();
	}

	@AfterTest
	public void afterTest() {
		driver.quit();
		System.out.println("=====================VEGASF_134_RegisteredUser_BrandProfile_ShowMoreProducts_PayWithGiftCardAndCC_END=====================");
	}
}
