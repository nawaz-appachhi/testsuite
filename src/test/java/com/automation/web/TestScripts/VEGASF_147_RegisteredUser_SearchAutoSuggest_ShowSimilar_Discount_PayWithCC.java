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
 * 3. Search (using a keyword in the Golden Set)
 * 4. Click on 'Show more products' link from PLP
 * 5. Navigate to PDP, verify Similar Products header, select a size and Add the product to Bag
 * 6. Navigate to Cart page and place the order
 * 7. Add a new address for Delivery
 * 8. Payment through Netbanking
 */

public class VEGASF_147_RegisteredUser_SearchAutoSuggest_ShowSimilar_Discount_PayWithCC extends GlobalVariables {
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
	
	String testName = "VEGASF_FF_147";
	
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
		System.out.println("=====================VEGASF_147_RegisteredUser_SearchAutoSuggest_ShowSimilar_Discount_PayWithCC_START=====================");
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
		////		objPLPObject.verifyBrandTitle();
		//		objPLPObject.verifyCategoriesTitle();
		//		objPLPObject.verifyPriceTitle();
		//		objPLPObject.verifyColourTitle();
		//		objPLPObject.verifyDiscountTitle();
	}
	
	//	@Test(priority = 4)
	//	public void ShowMoreProductsFromPLP() {
	//		System.out.println("Show More Products From PLP");
	//		objPLPObject.ShowMoreProductsLinkClick();	
	//	}
	
	@Test(priority = 5)
	public void SelectSizeFromPDP()  {
		System.out.println("Select Size From PDP");
		//	objPDPObject.VerifyProductCodefromPDP(objGenericMethods.getValueByKey(testName, "productCode"));
		//		objPLPObject.ClickOnFirstProduct();
		//		objPDPObject.VerifySellingPriceFromPDP();
		//		objPDPObject.verifyPDPTitleFromPDP();
		//	//	objPDPObject.VerifyDiscountPercentageFromPDP();
		//		objPDPObject.VerifyLargeThumbnailFromPDP();
		//		objPDPObject.VerifySmallThumbnailsFromPDP();
		//		objPDPObject.VerifyBestOfferLinkFromPDP();
		//		objPDPObject.VerifySimilarProductsHeaderFromPDP();
		//		objPDPObject.getSimilarProductsHeader().getText();
		//		System.out.println("Verified Similar Products section");
		//		objGenericMethods.scrollPageUp();
		objPDPObject.ClickSizeButtons();
		objPDPObject.ClickAddToBagBtn();
		objPDPObject.ClickGoToBagBtn();
		objCartObject.VerfiyProductIsAddedToCart();
		objCartObject.VerfiyTotalPrice();
	}
	
	@Test(priority = 6)
	public void PlaceOrderFromCart()  {
		System.out.println("Place Order From Cart");
		objCartObject.ClickPlaceOrder();
	}
	
	/**
	 * Modified by  :Anu
	 * Address details is reading from testdata.ini 
	 */
	@Test(priority = 7)
	public void AddNewAddress()  {
		System.out.println("Add New Address");
		objAddressPageObjects.AddAddress(objGenericMethods.getValueByKey(testName, "Pincode"),
				objGenericMethods.getValueByKey(testName, "Name"), objGenericMethods.getValueByKey(testName, "Mobile"),
				objGenericMethods.getValueByKey(testName, "Address"),
				objGenericMethods.getValueByKey(testName, "Locality"));
		objAddressPageObjects.SelectHomeAddressType();
		objAddressPageObjects.clickToSaveAddress();
		objAddressPageObjects.VerifyAddressAdded();
		objAddressPageObjects.VerifyPriceDetails();
	}
	
	@Test(priority = 8)
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
	
	@AfterTest
	public void afterTest() {
		driver.quit();
		System.out.println("=====================VEGASF_147_RegisteredUser_SearchAutoSuggest_ShowSimilar_Discount_PayWithCC_END=======================");
	}
}
