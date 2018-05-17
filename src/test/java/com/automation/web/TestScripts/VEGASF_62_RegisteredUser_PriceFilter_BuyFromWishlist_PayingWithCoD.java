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
import com.automation.web.ObjectRepository.Pages.HomePage.HomePageObject;
import com.automation.web.ObjectRepository.Pages.Login.LoginPageObject;
import com.automation.web.ObjectRepository.Pages.PDP.PDPObject;
import com.automation.web.ObjectRepository.Pages.PLP.PLPPageObject;
import com.automation.web.ObjectRepository.Pages.PaymentPage.PaymentPageObjects;
import com.automation.web.ObjectRepository.Pages.WishlistPage.WishlistPageObjects;

/**
 * @author 300021279 Neeraj
 
 * Test Steps
 * Email registered User
 * Home Page
 * Search (by clicking banner or nested banner on Home Page)
 * Filter
 * Add to bag
 * Move to bag
 * Place Order
 * Buy One get One
 * View Details
 * Myntra Credit
 */

public class VEGASF_62_RegisteredUser_PriceFilter_BuyFromWishlist_PayingWithCoD extends GlobalVariables {
	// Only methods need to call
	// all class reference create here
	GenericMethods objGenericMethods;
	DriverInit objDriverInit;
	HeaderPageObject objHeaderPageObject;
	LoginPageObject objLoginPageObject;
	HomePageObject objHomePageObject;
	PLPPageObject objPLPPageObject;
	PDPObject objPDPObject;
	CartPageObject objCartPageObject;
	WishlistPageObjects objWishlistPageObjects;
	AddressPageObjects objAddressPageObjects;
	PaymentPageObjects objPaymentPageObjects;

	public WebDriver driver;
	String testName = "VEGASF_FF_62";

	@Test(priority = 1)
	public void Login()  {
		System.out.println("=====================VEGASF_62_RegisteredUser_PriceFilter_BuyFromWishlist_PayingWithCoD_START=====================");
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
		objCartPageObject.resetCart();
		objAddressPageObjects.resetAddress();
		objWishlistPageObjects.resetWishlist();
	}
	
	/**
	 * Modified by : Irfan Shariff
	 * Description 1 : Added Thread.sleep(MIDSLEEP) before "Searching an Item". If this sleep not added search function fails.
	 * Description 2 : Added Thread.sleep(MIDSLEEP) after "selectBuy1Get1", as it takes some time to load the page. If this sleep not added, then wrong item gets selected.
	 */
	@Test(priority = 3)
	public void HomePage()  {
		System.out.println("Home Page");
		// objHomePageObject.clickOnExclusiveOfferImage4th();
		objHeaderPageObject.SearchInputBox(objGenericMethods.getValueByKey(testName, "SearchItem"));
		//objHeaderPageObject.VerifyAutoSuggestSearch();
		objHeaderPageObject.ClickOnSearchLens();
//		objPLPPageObject.VerifyProductDetails();
////		objPLPPageObject.verifyBrandTitle();
//		objPLPPageObject.verifyCategoriesTitle();
//		objPLPPageObject.verifyPriceTitle();
//		objPLPPageObject.verifyColourTitle();
//		objPLPPageObject.verifyDiscountTitle();	
//		objPLPPageObject.clickOnPromotions();
//		objPLPPageObject.selectBuy1Get1();
	}

//	@Test(priority = 4)
//	public void sortByLowToHigh()  {
//		System.out.println("Sort By Low To High");
//		objPLPPageObject.HowerOnSortByDropdown();//will be uncommented when the site is up and running
//		objPLPPageObject.ClickOnSortDropdownByDroddownValue("Price: Low to High");
//	}

//	@Test(priority = 5)
//	public void ProductListPage()  {
//		System.out.println("Product List Page");
//		objPLPPageObject.verifyColourTitle();
//		objPLPPageObject.ClickOnFirstProduct();
//		objPDPObject.verifyPDPTitleFromPDP();
//		objPDPObject.VerifySellingPriceFromPDP();
//		objPDPObject.VerifyLargeThumbnailFromPDP();
//		objPDPObject.VerifySmallThumbnailsFromPDP();
//		objPDPObject.VerifyBestOfferLinkFromPDP();
//	}

//	@Test(priority = 6)
//	public void CheckDeliveryLocation()  {
//		System.out.println("Check Delivery Location");
//		objGenericMethods.scrollPage0to250();
//		objPDPObject.ClickCheckDeliveryOptionsLink();
//		objPDPObject.EnterPincode("560068");
//		objPDPObject.ClickCheckPinCodeLink();		
//	}
/**
 * name-shivaprasad
 * Modified-objPDPObject.ClickSizeButtons();
 */
	@Test(priority = 7)
	public void MoveToTheBag()  {
		System.out.println("Move To The Bag");
		objGenericMethods.scrollPageUp();
		objPDPObject.ClickSizeButtons();
		objPDPObject.ClickOnSaveBtn();
		objPDPObject.HoverAllSmallThumbnails();
		objPDPObject.verifyPDPTitleFromPDP();
		objPDPObject.VerifySellingPriceFromPDP();
		objPDPObject.VerifyLargeThumbnailFromPDP();
		objPDPObject.VerifySmallThumbnailsFromPDP();
		objGenericMethods.HoverOnWebElement(objHeaderPageObject.getUserIcon());
		objHeaderPageObject.ClickonWishlistLink();
		objWishlistPageObjects.VerifyWishlistTitle();
		objWishlistPageObjects.VerifyWishlistProductName();
		objWishlistPageObjects.VerifySellingPrice();
		objWishlistPageObjects.ClickMoveToBagButton();
		objWishlistPageObjects.ClickDoneButtons();
	}

	@Test(priority = 8)
	public void PlaceAnOrderFromCart()  {
		System.out.println("Place An Order From Cart");
		objHeaderPageObject.ClickOnCart();
		objCartPageObject.VerfiyProductIsAddedToCart();
		objCartPageObject.VerfiyTotalPrice();
		objCartPageObject.ClickPlaceOrder();
	}

	@Test(priority = 9)
	public void EditAddress()  {
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
		objAddressPageObjects.EditNameTextField("VegaSF62 " + objGenericMethods.datetime("ddMMyyyyHHmmss"));
		objAddressPageObjects.clickToSaveAddress();
		objAddressPageObjects.VerifyAddressAdded();
	}

	@Test(priority = 10)
	public void CompletePayment() 
	{
		System.out.println("Complete Payment");
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
		objHomePageObject = new HomePageObject(driver);
		objPLPPageObject = new PLPPageObject(driver);
		objPDPObject = new PDPObject(driver);
		objWishlistPageObjects = new WishlistPageObjects(driver);
		objPaymentPageObjects = new PaymentPageObjects(driver);
		objCartPageObject = new CartPageObject(driver);
		objAddressPageObjects = new AddressPageObjects(driver);
		// Open URL
		objGenericMethods.openURL(System.getProperty("url"));
	}

	@AfterTest
	public void afterTest() {
		// quit browser object
		// check condition before closing or quite
		driver.quit();
		System.out.println("=====================VEGASF_62_RegisteredUser_PriceFilter_BuyFromWishlist_PayingWithCoD_END=======================");
	}
}
