package com.automation.web.TestScripts;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
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
 * @author 300019227-Anu
 *
 */
/**
 * TEST STEPS
 * 1. Login(Email Registered user)
 * 2. Reset Cart, Wishlist and Address Pages
 * 3. Search (Brand Profile - Search for a brand like Nike, Roadster)
 * 4. Add the item to Bag from PLP
 * 5. Navigate to PDP, Verify 'Size chart' link and Save the product to Wishlist
 * 6. Place Order from Bag page
 * 7. Add New Address for Delivery
 * 8. Payment through Credit/Debit card
 */

public class VEGASF_130_RegisteredUser_BrandConcious_NewAddress_PayWithDC extends GlobalVariables {
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

	String ExcelPath;
	public int CURRENT_ROW;
	public WebDriver driver;

	String testName = "VEGASF_FF_130";

	@BeforeTest
	public void beforeTest() {

		
		// Initial browser Objects and methods objects
		objDriverInit = new DriverInit();
		// driver = objDriverInit.getWebDriverWin("Chrome");
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
		System.out.println("=====================VEGASF_130_RegisteredUser_BrandConcious_NewAddress_PayWithDC_START=====================");
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

	/**
	 * Modified by : Irfan Shariff
	 * Description : Added Thread.sleep(MIDSLEEP) before "Searching an Item". If this sleep not added search function fails.
	 */
	@Test(priority = 3)
	public void SearchItem()  {
		System.out.println("Search Item");
		objHeaderPageObject.SearchInputBox(objGenericMethods.getValueByKey(testName, "SearchItem"));
		
		objHeaderPageObject.ClickOnSearchLens();
//		objPLPObject.FilterByColour("blue");
//		objPLPObject.VerifyProductDetails();
//		objPLPObject.verifySearchResultTittle();
//		objPLPObject.verifyCategoriesTitle();
//		objPLPObject.verifyBrandTitle();
//		objPLPObject.verifyPriceTitle();
//		objPLPObject.verifyColourTitle();
		//objPLPObject.verifyDiscountTitle();	
	}

//	@Test(priority = 4)
//	public void AddtoBagFromPLP()  {
//		System.out.println("Add to Bag From PLP");
//		objPLPObject.AddToBagFirstProduct();
//		objPLPObject.getProductSize();
//		objPLPObject.ClickOnFirstProduct();
//		objPDPObject.verifyPDPTitleFromPDP();
////		objPDPObject.VerifyActualPriceFromPDP();
//		objPDPObject.VerifySellingPriceFromPDP();
////		objPDPObject.VerifyDiscountPercentageFromPDP();
//		objPDPObject.VerifyLargeThumbnailFromPDP();
//		objPDPObject.VerifySmallThumbnailsFromPDP();
//		objPDPObject.VerifyBestOfferLinkFromPDP();	
//		Reporter.log("Product clicked from list page");
//	}

	/**
	 * @
	 */
	@Test(priority = 5)
	public void SaveFromPDP()  {
		System.out.println("Save From PDP");
		objPDPObject.ClickOnSaveBtn();
		objGenericMethods.HoverOnWebElement(objHeaderPageObject.getUserIcon());
		objHeaderPageObject.ClickonWishlistLink();
		objWishlistPageObject.VerifyWishlistTitle();
		objWishlistPageObject.VerifyWishlistProductName();
//		objWishlistPageObject.VerifyActualPrice();
		objWishlistPageObject.VerifySellingPrice();
//		objWishlistPageObject.VerifyDiscountPercentage();
		objWishlistPageObject.ClickItemImage();
		Reporter.log("Item successfully added to wishlist");

		objPDPObject.ClickOnSizeChartLink();
//		objPDPObject.getSimilarProductsHeader().getText();
		System.out.println("Verified Similar Products section");
//		objHeaderPageObject.ClickOnCartIcon();
		  objPDPObject.ClickSizeButtons();
	        objPDPObject.ClickAddToBagBtn();
	        objPDPObject.ClickGoToBagBtn();
		objCartObject.VerfiyProductIsAddedToCart();
		objCartObject.VerfiyTotalPrice();
	}

	@Test(priority = 6)
	public void PlaceOrder()  {
		System.out.println("Place Order");
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
		objAddressPageObjects.SelectOfficeAddressType();
		objAddressPageObjects.clickToSaveAddress();
		objAddressPageObjects.VerifyAddressAdded();
		objAddressPageObjects.VerifyPriceDetails();
	}

	@Test(priority = 8)
	public void CompletePayment_CreditCard()  {
		System.out.println("Payment");
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
		System.out.println("====================VEGASF_130_RegisteredUser_BrandConcious_NewAddress_PayWithDC_END=======================");
	}

}
