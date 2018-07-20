package com.automation.web.TestScripts;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.Reporter;
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
 * Modified By: Aishurya
 * Reason: Removed product code
 *
 */

/**
 * TEST STEPS
 * 1. Login(Email registered user)
 * 2. Reset Cart, Wishlist and Address Pages
 * 3. Search (using a keyword in the Golden Set)
 * 4. Click on 'Show more products' link from PLP
 * 5. Click on a product from PLP and Navigate to PDP
 * 6. Save the product to Wishlist from PDP
 * 7. Select a size and add the product to Bag
 * 8. Verify 'Contact us' link from Bag Page and Place the order
 * 9. Add New Address for Delivery
 * 10. Payment through Gift Card and Netbanking
 */

public class VEGASF_RupeesDiscount extends GlobalVariables {
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
	
	String testName = "VEGASF_RupeesDiscount";

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
		System.out.println("=====================VEGASF_RupeesDiscount_START=====================");
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
		objAddressPageObjects.resetAddress();
		objCartObject.resetCart();
	}

	@Test(priority = 3)
	public void SearchItem()  {
		System.out.println("Search Item");
		objHeaderPageObject.SearchInputBox(objGenericMethods.getValueByKey(testName, "SearchItem"));
		//objHeaderPageObject.VerifyAutoSuggestSearch();
		objHeaderPageObject.ClickOnSearchLens();
//		objPLPObject.verifySearchResultTittle();
//		//objPLPObject.verifyBrandTitle();
//		objPLPObject.verifyCategoriesTitle();
//		objPLPObject.verifyColourTitle();
//		objPLPObject.verifyDiscountTitle();
//		objPLPObject.verifyPriceTitle();
////		objPLPObject.verifyProductBrand(objGenericMethods.getValueByKey(testName, "productCode"));
////		objPLPObject.verifyProductName(objGenericMethods.getValueByKey(testName, "productCode"));
////		objPLPObject.verifyStrickedPrice(objGenericMethods.getValueByKey(testName, "productCode"));
////		objPLPObject.verifyDiscountedPrice(objGenericMethods.getValueByKey(testName, "productCode"));
////		objPLPObject.verifyDiscountPercentage(objGenericMethods.getValueByKey(testName, "productCode"));
	}

	/**
	 * Modified By: Aishurya Reason :Added ClickTopButton() button to select a product from top
	 * 
	 */
//	@Test(priority = 4)
//	public void ShowMoreProductsFromPLP()  {
//		System.out.println("Show More Products From PLP");
////		eleProductSellingdPrice = objPLPObject.SubElementOfItem(testProductCode, "DiscountedPrice");
////		eleProductActualPrice = objPLPObject.SubElementOfItem(testProductCode, "StrikedPrice");
////		eleDiscountPercentage = objPLPObject.SubElementOfItem(testProductCode, "DiscountedPercentage");
//		objPLPObject.ShowMoreProductsLinkClick();
//		Reporter.log("More products link clicked");
//		objPLPObject.ClickTopButton();
//	}
//	
//	@Test(priority = 5)
//	public void ClickOnProductFromPLP()  {
//		System.out.println("Click On Product From PLP");
//	//	objPLPObject.getClickProductByProductCode(objGenericMethods.getValueByKey(testName, "productCode"));
//		objPLPObject.ClickOnFirstProduct();
//		Reporter.log("Product clicked from list page");
//	}
//
//	@Test(priority = 6)
//	public void VerifyAllProductsDetailsFromPDP()  {
//		System.out.println("Verify All Products Details From PDP");
//		// objPDPObject.VerifySellingPrice(eleProductSellingdPrice);
//		// objPDPObject.VerifyActualPrice(eleProductActualPrice);
//		// objPDPObject.VerifyDiscountPercentage(eleDiscountPercentage);
//		objPDPObject.VerifyProductCodefromPDP("xyz");//String parameter is not getting utilized in calling function
//		objPDPObject.VerifyLargeThumbnailFromPDP();
//		objPDPObject.VerifySmallThumbnailsFromPDP();
//		objPDPObject.VerifyBestOfferLinkFromPDP();
////		objPDPObject.VerifySimilarProductsHeaderFromPDP();
//	}
//
//	

	@Test(priority = 7)
	public void ClickOnSaveFromPDP()  {
		System.out.println("Click On Save From PDP");
		objPDPObject.VerifyRupeeDiscount();
		objPDPObject.ClickOnSaveBtn();
		objPDPObject.HoverAllSmallThumbnails();
		objPDPObject.verifyPDPTitleFromPDP();
		objPDPObject.VerifySellingPriceFromPDP();
		objPDPObject.VerifyLargeThumbnailFromPDP();
		objPDPObject.VerifySmallThumbnailsFromPDP();
		objGenericMethods.HoverOnWebElement(objHeaderPageObject.getUserIcon());
		objHeaderPageObject.ClickonWishlistLink();
		objWishlistPageObject.ClickItemImage();
		Reporter.log("Item successfully added to wishlist");
//		objPDPObject.getSimilarProductsHeader().getText();
//		System.out.println("Verified Similar Products section");
	}

	@Test(priority = 8)
	public void SizeSelectionFromPDP()  {
		System.out.println("Size Selection From PDP");
		objPDPObject.ClickSizeButtons();
	}

	@Test(priority = 9)
	public void AddToBagFromPDP()  {
		System.out.println("Add To Bag From PDP");
		objPDPObject.ClickAddToBagBtn();
		objPDPObject.HoverAllSmallThumbnails();
		objPDPObject.verifyPDPTitleFromPDP();
		objPDPObject.VerifySellingPriceFromPDP();
		objPDPObject.VerifyLargeThumbnailFromPDP();
		objPDPObject.VerifySmallThumbnailsFromPDP();
		objPDPObject.ClickGoToBagBtn();
		objCartObject.VerfiyProductIsAddedToCart();
		objCartObject.VerfiyTotalPrice();
	}

	@Test(priority = 10)
	public void PlaceOrder() {
		System.out.println("Place Order");
		objCartObject.ClickPlaceOrder();
	}
	/**
	 * Modified by  :Anu
	 * Address details is reading from testdata.ini 
	 */
	@Test(priority = 11)
	public void AddNewAddress()  {
		System.out.println("Add New Address");
		objAddressPageObjects.AddAddress(objGenericMethods.getValueByKey(testName, "Pincode"),
				objGenericMethods.getValueByKey(testName, "Name"), objGenericMethods.getValueByKey(testName, "Mobile"),
				objGenericMethods.getValueByKey(testName, "Address"),
				objGenericMethods.getValueByKey(testName, "Locality"));
		objAddressPageObjects.getOfficeRadioButton();
		objAddressPageObjects.SelectOfficeAddressType();
		objAddressPageObjects.clickToSaveAddress();
		objAddressPageObjects.VerifyAddressAdded();
		objAddressPageObjects.VerifyPriceDetails();
		objAddressPageObjects.ClickToContinue();
		objPaymentPageObjects.VerifyOrderSumamry();
		objPaymentPageObjects.VerifyDeliveryAddress();
		objPaymentPageObjects.VerifyFinalPayableAmount();	
		objAddressPageObjects.VerifyShippingCharge();
	}
	
	@Test(priority = 12)
	public void LogOut()  {
		objLoginPageObject.LogOut();
		objLoginPageObject.readSession("AfterLogout");
	}


	@AfterTest
	public void afterTest() {
		driver.quit();
		System.out.println("=====================VEGASF_RupeesDiscount_END=======================");
	}
}
