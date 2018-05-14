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
/**Modified By: Aishurya
 * Reason: Removed product code
 * 
 * TEST STEPS 
 * 1. Login(Email Registered user)
 * 2. Reset Cart, Wishlist and Address Pages
 * 3. Search (Brand Profile - Search for a brand like Nike, Roadster)
 * 4. 'Sort by New' from PLP and navigate to PDP
 * 5. Select a size from PDP, Add the product to Bag
 * 6. Place the order from Bag page
 * 7. Add new Address for delivery
 * 8. Payment through Gift card and Netbanking.
 */

public class VEGASF_55_RegisteredUser_BrandConcious_SizeSensitive_PayingWithCC extends GlobalVariables {
	// Only methods need to call
	// all class reference create here

	GenericMethods objGenericMethods;
	DriverInit objDriverInit;
	HeaderPageObject objHeaderPageObject;
	LoginPageObject objLoginPageObject;
	PLPPageObject objPLPObject;
	PDPObject objPDPObject;
	CartPageObject objCartObject;
	AddressPageObjects objAddressPageObject;
	PaymentPageObjects objPaymentPageObjects;
	WishlistPageObjects objWishlistPageObject;

	public WebDriver driver;
	public WebElement eleProductSellingdPrice;

	String testName = "VEGASF_FF_55";

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
		objAddressPageObject = new AddressPageObjects(driver);
		objPaymentPageObjects = new PaymentPageObjects(driver);
		objWishlistPageObject = new WishlistPageObjects(driver);

		// Open URL
		objGenericMethods.openURL(System.getProperty("url"));
	}

	@Test(priority = 1)
	public void LoginInMyntra()  {
		System.out.println("=====================VEGASF_55_RegisteredUser_BrandConcious_SizeSensitive_PayingWithCC_START=====================");
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
		objAddressPageObject.resetAddress();
		objCartObject.resetCart();
	}

	@Test(priority = 3)
	public void SearchItem()  {
		System.out.println("Search Item");
		objHeaderPageObject.SearchInputBox(objGenericMethods.getValueByKey(testName, "SearchItem"));
//		objHeaderPageObject.VerifyAutoSuggestSearch();
		objHeaderPageObject.ClickOnSearchLens();
//		objPLPObject.verifySearchResultTittle();
		// objPLPObject.verifyBrandTitle();
////		objPLPObject.verifyCategoriesTitle();
		objPLPObject.verifyColourTitle();
//		objPLPObject.verifyDiscountTitle();
		objPLPObject.verifyPriceTitle();
		// objPLPObject.verifyProductBrand(objGenericMethods.getValueByKey(testName,"productCode"));
		// objPLPObject.verifyProductName(objGenericMethods.getValueByKey(testName,"productCode"));
		// objPLPObject.verifyStrickedPrice(objGenericMethods.getValueByKey(testName,"productCode"));
		// objPLPObject.verifyDiscountedPrice(objGenericMethods.getValueByKey(testName,"productCode"));
		// objPLPObject.verifyDiscountPercentage(objGenericMethods.getValueByKey(testName,"productCode"));
	}

	@Test(priority = 4)
	public void SortByNewFromPLP()  {
		System.out.println("Sort By New From PLP");
		if(System.getProperty("url").contains("sfqa")) {
			objPLPObject.HowerOnSortByDropdown();
			objPLPObject.ClickOnSortDropdownByDroddownValue("Popularity");
			//objGenericMethods.FetchAllNameAfterSort(objPLPPageObject.getSearchResultList());
			objPLPObject.FilterByColour(objGenericMethods.getValueByKey(testName,"ColorName"));
			
		}
		else {
			objPLPObject.HowerOnSortByDropdown();
			objPLPObject.ClickOnSortDropdownByDroddownValue("Popularity");
			objPLPObject.ClickmoreColors();
			objPLPObject.FilterByColour(objGenericMethods.getValueByKey(testName,"ColorName"));
			//objGenericMethods.FetchAllNameAfterSort(objPLPPageObject.getSearchResultList());
		}
	//	objPLPObject.verifyPriceTitle();
		objPDPObject.ClickSizeButtons();
//		objPLPPageObject.verifyCategoriesTitle();
//		objPLPPageObject.ClickOnFirstProduct();
		//objPLPObject.getClickProductByProductCode(objGenericMethods.getValueByKey(testName,"productCode"));
		try {
			objPLPObject.FilterByColour("NOCOLOR");
		} catch (Exception e) {
		}
		objPLPObject.getClickProductByProductCode(objGenericMethods.getValueByKey(testName,"productCode"));
	}
//
//	@Test(priority = 5)
//	public void VerifyAllProductsDetailsFromPDP()  {
//		System.out.println("Verify All Products Details From PDP");
//		objPDPObject.verifyPDPTitleFromPDP();
//		objPDPObject.VerifySellingPriceFromPDP();
//		objPDPObject.VerifyProductCodefromPDP("xyz");//String parameter is not getting utilized in calling function
//		objPDPObject.VerifyLargeThumbnailFromPDP();
//		objPDPObject.VerifySmallThumbnailsFromPDP();
//		objPDPObject.VerifyBestOfferLinkFromPDP();
////		objPDPObject.VerifySimilarProductsHeaderFromPDP();
//	}
//
	@Test(priority = 6)
	public void SizeSelectionFromPDP()  {
		System.out.println("Size Selection From PDP");
		objPDPObject.ClickSizeButtons();
	}
	
	@Test(priority = 7)
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

	@Test(priority = 8)
	public void PlaceOrderFromCart()  {
		System.out.println("Place Order From Cart");
		objCartObject.ClickPlaceOrder();
	}
	/**
	 * Modified by  :Anu
	 * Address details is reading from testdata.ini 
	 */
	@Test(priority = 9)
	public void AddNewAddress()  {
		System.out.println("AddNewAddress");
		objAddressPageObject.AddAddress(objGenericMethods.getValueByKey(testName, "Pincode"),
				objGenericMethods.getValueByKey(testName, "Name"), objGenericMethods.getValueByKey(testName, "Mobile"),
				objGenericMethods.getValueByKey(testName, "Address"),
				objGenericMethods.getValueByKey(testName, "Locality"));
		objAddressPageObject.getOfficeRadioButton();
		objAddressPageObject.SelectOfficeAddressType();
		objAddressPageObject.clickToSaveAddress();
		objAddressPageObject.VerifyAddressAdded();
		objAddressPageObject.VerifyPriceDetails();
		objAddressPageObject.ClickToContinue();
	}

	@Test(priority =10)
	public void PaymentWithCreditDebit_Card()  {
		System.out.println("Complete Payment");

		// Enable when Gift Card provided
//		objPaymentPageObjects.SelectGiftCards();
//		objPaymentPageObjects.EnterGiftCardData(objGenericMethods.getValueByKey(testName, "GiftCardNumber"), 
//				objGenericMethods.getValueByKey(testName, "GiftCardPin"));

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
		driver.quit();
		System.out.println("=====================VEGASF_55_RegisteredUser_BrandConcious_SizeSensitive_PayingWithCC_END=======================");
	}
}
