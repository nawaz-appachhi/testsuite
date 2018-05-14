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
import com.automation.web.ObjectRepository.Pages.Header.HeaderPageObject;
import com.automation.web.ObjectRepository.Pages.Login.LoginPageObject;
import com.automation.web.ObjectRepository.Pages.PLP.PLPPageObject;
import com.automation.web.ObjectRepository.Pages.PDP.*;
import com.automation.web.ObjectRepository.Pages.WishlistPage.*;
import com.automation.web.ObjectRepository.Pages.Cart.*;
import com.automation.web.ObjectRepository.Pages.AddressPage.*;
import com.automation.web.ObjectRepository.Pages.PaymentPage.*;

/**
 * @author 300020817 - Nitesh
 *
 */
public class VEGASF_138_RegisteredUser_SizeSensitive_BOGO_PayWithCoD extends GlobalVariables {

	// Only methods need to call
	// all class reference create here

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

	public WebElement ExpectedAddressDetail;

	public WebDriver driver;

	String testName="VEGASF_FF_138";
	
	
	@Test(priority = 1)
	public void LoginInMyntra()  {
		System.out.println("=====================VEGASF_138_RegisteredUser_SizeSensitive_BOGO_PayWithCoD_START=====================");
		System.out.println("Login In Myntra");
		objGenericMethods.HoverOnWebElement(objHeaderPageObject.getUserIcon());
		objHeaderPageObject.LoginUnderUserIcon();
		objLoginPageObject.readSession("BoforeLogin");
		objLoginPageObject.Login(objGenericMethods.getValueByKey(testName, "UserName"),objGenericMethods.getValueByKey(testName, "Password"));
		objLoginPageObject.LogInButtonClick();
		objLoginPageObject.VerifyUserEmailId();
		objLoginPageObject.readSession("AfterLogin");
	}
	
	@Test(priority = 2)
	public void ResetAll()  {
		System.out.println("Reset All");
		objAddressPageObjects.resetAddress();
		objWishlistPageObjects.resetWishlist();
		objCartPageObject.resetCart();
	}
	
	/**
	* Name: Irfan Shariff
	* Description: thread.smallsleep is required after "objPLPPageObject.selectBuy1Get1();", so that the page loads up and the first item can be selected.
	* Modified by <Irfan Shariff>
	*/
	@Test(priority = 3)
	public void SearchItem() {
		System.out.println("Search Item");
		objHeaderPageObject.SearchInputBox(objGenericMethods.getValueByKey(testName,"SearchItem"));
//		objHeaderPageObject.VerifyAutoSuggestSearch();
		objHeaderPageObject.ClickOnSearchLens();
//		objPLPPageObject.clickOnPromotions();
//		objPLPPageObject.selectBuy1Get1();
//		objPLPPageObject.verifySearchResultTittle();
////		objPLPPageObject.verifyBrandTitle();
//		objPLPPageObject.verifyPriceTitle();
//		objPLPPageObject.verifyColourTitle();	
//		objPLPPageObject.verifyProductBrand(objGenericMethods.getValueByKey(testName,"productCode"));
//		objPLPPageObject.verifyProductName(objGenericMethods.getValueByKey(testName,"productCode"));
//		objPLPPageObject.verifyDiscountPercentage(objGenericMethods.getValueByKey(testName,"productCode"));
	}
	
	/**
	 * Name : Irfan
	 * Reason for change : Whole @Test(priority = 4) is commented because only 4 products are displayed in buy 1 get 1 search result
	 * so, 'Top button' is not displayed
	 * 
	 */
//	@Test(priority = 4)
//	public void Scroll_PLPupDown() , AWTException
//	{
//		System.out.println("Scroll PLP UpDown");
//		objGenericMethods.scrollPage0to250();
//		objGenericMethods.scrollPage0to250();
//		objGenericMethods.scrollPage0to250();
//		objPLPPageObject.ClickTopButton();
//	}
	
//	@Test(priority = 5)
//	public void NavigatefromPLPtoPDP() 
//	{
//		System.out.println("Navigate from PLP to PDP");
//		objPLPPageObject.ClickOnFirstProduct();
////		objPLPPageObject.getClickProductByProductCode(objGenericMethods.getValueByKey(testName,"productCode"));
////		objPDPObject.VerifyProductCodefromPDP(objGenericMethods.getValueByKey(testName,"productCode"));
//		objPDPObject.verifyPDPTitleFromPDP();
//		objPDPObject.VerifyLargeThumbnailFromPDP();
//		objPDPObject.VerifySmallThumbnailsFromPDP();
//		objPDPObject.VerifySellingPriceFromPDP();
//		objPDPObject.ClickSizeButtons();
//		objPDPObject.ClickOnSaveBtn();
//	}
	
	@Test(priority = 6)
	public void SelectSizeAddToBag() {
		System.out.println("Select Size Add To Bag");
     	objPDPObject.ClickOnSaveBtn();
     	objPDPObject.ClickSizeButtons();
		objPDPObject.ClickAddToBagBtn();
		objPDPObject.verifyPDPTitleFromPDP();
 		objPDPObject.VerifySellingPriceFromPDP();
 		objPDPObject.VerifyLargeThumbnailFromPDP();
 		objPDPObject.VerifySmallThumbnailsFromPDP();
 		objPDPObject.VerifyBestOfferLinkFromPDP();
	}
	
	// Do not uncomment this section as contact us not working on all test environment
//	@Test(priority = 7)
//	public void NavigateToContactUsPage() 
//	{
//		System.out.println("Navigate To Contact Us Page");
//		objHeaderPageObject.ClickContactUs();
//	}

	@Test(priority = 8)
	public void GoToCart() {
		System.out.println("Go To Cart");
		objHeaderPageObject.ClickOnCart();
		objCartPageObject.VerfiyProductIsAddedToCart();
		objCartPageObject.VerfiyTotalPrice();
	}

	@Test(priority = 9)
	public void PlaceOrder() {
		System.out.println("Place Order");
		objCartPageObject.ClickPlaceOrder();
	}
	/**
	 * Modified by  :Anu
	 * Address details is reading from testdata.ini 
	 */
	
	@Test(priority = 10)
	public void AddAddress() {
		System.out.println("Add Address");
		objAddressPageObjects.AddAddress(objGenericMethods.getValueByKey(testName, "Pincode"),
				objGenericMethods.getValueByKey(testName, "Name"), objGenericMethods.getValueByKey(testName, "Mobile"),
				objGenericMethods.getValueByKey(testName, "Address"),
				objGenericMethods.getValueByKey(testName, "Locality"));
		objAddressPageObjects.SelectHomeAddressType();
		objAddressPageObjects.clickToSaveAddress();
		objAddressPageObjects.VerifyAddressAdded();
		objAddressPageObjects.VerifyPriceDetails();
	}
	
	@Test(priority = 11)
	public void CompletePayment_COD() {
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
	@Test(priority = 12)
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
		driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
		driver.manage().window().maximize();
		if (driver == null) {
			Assert.assertFalse(false,"WebDriver Object not Initited and Found Null Value");
		}
		objHeaderPageObject = new HeaderPageObject(driver);
		objGenericMethods = new GenericMethods(driver);
		objLoginPageObject = new LoginPageObject(driver);
		objPLPPageObject = new PLPPageObject(driver);
		objPDPObject = new PDPObject(driver);
		objWishlistPageObjects = new WishlistPageObjects(driver);
		objCartPageObject = new CartPageObject(driver);
		objAddressPageObjects = new AddressPageObjects(driver);
		objPaymentPageObjects = new PaymentPageObjects(driver);

		// Open URL
		objGenericMethods.openURL(System.getProperty("url"));


	}

	@AfterTest
	public void afterTest() {
		// quit browser object
		// check condition before closing or quite
		driver.quit();
		System.out.println("=====================VEGASF_138_RegisteredUser_SizeSensitive_BOGO_PayWithCoD_END=========================");
	}
}
