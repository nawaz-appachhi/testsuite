package com.automation.web.TestScripts;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
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
 * @author 300019224- Aishurya
 * Steps:
 * LoginWithGoogle
 * SearchItem
 * Fitter_ByNew
 * AddToBag
 * CheckForSimilarProductLink
 * EmptyWishlist
 * CheckForContactUs
 * BuyOneGetOne
 * ViewDetails
 * PaymentWithEMI
 *
 */
public class VEGASF_312_Google_User_EmptyWishlist_BOGO_ViewDetails_PayWithCC extends GlobalVariables{
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
	String testName = "VEGASF_FF_312";

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

//	@Test(priority = 1)
//	public void LoginWithGoogle()  {
//		System.out.println("=====================VEGASF_308_START=====================");
//		System.out.println("Login In Myntra");
//		objGenericMethods.HoverOnWebElement(objHeaderPageObject.getUserIcon());
//		objHeaderPageObject.LoginUnderUserIcon();
//		objLoginPageObject.LoginWithGmail(objGenericMethods.getValueByKey(testName, "GmailUserName"), objGenericMethods.getValueByKey(testName, "GmailPassword"));
//		objLoginPageObject.VerifyUserEmailId();
//			}
	
	@Test(priority = 1)
	public void LoginInMyntra()  {
		System.out.println("=====================VEGASF_312_Google_User_EmptyWishlist_BOGO_ViewDetails_PayWithCC_START=====================");
		System.out.println("Login In Myntra");
		objGenericMethods.HoverOnWebElement(objHeaderPageObject.getUserIcon());
		objHeaderPageObject.LoginUnderUserIcon();
		objLoginPageObject.Login(objGenericMethods.getValueByKey(testName, "UserName"),
				objGenericMethods.getValueByKey(testName, "Password"));
		objLoginPageObject.LogInButtonClick();
		objLoginPageObject.VerifyUserEmailId();
	}
	
	@Test(priority = 2)
	public void SearchItem()  {
		System.out.println("Reset All");
		objCartPageObject.resetCart();
		objAddressPageObjects.resetAddress();
		objWishlistPageObjects.resetWishlist();
		System.out.println("Search Item");
		objHeaderPageObject.SearchInputBox(objGenericMethods.getValueByKey(testName, "SearchItem"));
//		objHeaderPageObject.VerifyAutoSuggestSearch();
		objHeaderPageObject.ClickOnSearchLens();
	}
//	@Test(priority = 3)
//	public void Fitter_ByNew()  {
//		objGenericMethods.FetchAllNameBeforeSort(objPLPPageObject.AllProductName);
//		objPLPPageObject.HowerOnSortByDropdown(); //sorting is changing the product so commented
//		objPLPPageObject.ClickOnSortDropdownByDroddownValue("New");
//		objGenericMethods.FetchAllNameAfterSort(objPLPPageObject.AllProductName);
//		objGenericMethods.verifySortByEffectiveness();
//		objPLPPageObject.verifySearchResultTittle();
//		 objPLPPageObject.verifyCategoriesTitle();
//		objPLPPageObject.verifyColourTitle();
//		 objPLPPageObject.verifyDiscountTitle();
//		 objPLPPageObject.clickToSaveToWishlist();
//			objPLPPageObject.clickOnPromotions();
//			objPLPPageObject.selectBuy1Get1();
//		 
//	}
//	@Test(priority = 4)
//	public void AddToBag()  {
//		objPLPPageObject.AddToBagFirstProduct();
//		objPLPPageObject.ClickOnFirstProduct();
//	}
//	@Test(priority = 5)
//	public void CheckForSimilarProductLink() {
////		objPDPObject.VerifySimilarProductsHeaderFromPDP();//Not avilable in sfqa
//	}
	
	@Test(priority = 5)//comment it when the wish list is working
	public void newMethodForPdp() {
		 objPDPObject.ClickSizeButtons();
	        objPDPObject.ClickOnSaveBtn();
	}
	
	
	@Test(priority = 6)
	public void EmptyWishlist()  {
		objGenericMethods.HoverOnWebElement(objHeaderPageObject.getUserIcon());
		objHeaderPageObject.ClickonWishlistLink();
		objWishlistPageObjects.VerifyWishlistTitle();
		objWishlistPageObjects.ClickMoveToBagButton();
		objWishlistPageObjects.ClickSizeButtons();
		objWishlistPageObjects.ClickDoneButton();
	}
	@Test(priority = 7)
	public void CheckForContactUs() {
		objHeaderPageObject.ClickOnCartIcon();
		objCartPageObject.VerfiyProductIsAddedToCart();
		objCartPageObject.VerfiyTotalPrice();
	}
//	@Test(priority = 8)
//	public void BuyOneGetOne()  {
//
//	}
	@Test(priority = 9)
	public void ViewDetails()  {
		System.out.println("Place Order");
		objCartPageObject.ClickPlaceOrder();
		System.out.println("Add Address");
		objAddressPageObjects.AddAddress(objGenericMethods.getValueByKey(testName, "Pincode"),
				objGenericMethods.getValueByKey(testName, "Name"), objGenericMethods.getValueByKey(testName, "Mobile"),
				objGenericMethods.getValueByKey(testName, "Address"),
				objGenericMethods.getValueByKey(testName, "Locality"));
		objAddressPageObjects.SelectOfficeAddressType();
		objAddressPageObjects.clickToSaveAddress();
		objAddressPageObjects.VerifyAddressAdded();
		objAddressPageObjects.VerifyPriceDetails ();
	}
	
	@Test(priority = 10)
	public void CompletePaymentCreditCard() 	{
		System.out.println("Complete Payment");
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
		// quit browser object
		// check condition before closing or quite
		driver.quit();
		System.out.println("=====================VEGASF_312_Google_User_EmptyWishlist_BOGO_ViewDetails_PayWithCC_END=======================");
	}
}
