
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
import com.automation.web.ObjectRepository.Pages.Header.HeaderPageObject;
import com.automation.web.ObjectRepository.Pages.Login.LoginPageObject;
import com.automation.web.ObjectRepository.Pages.PLP.PLPPageObject;
import com.automation.web.ObjectRepository.Pages.PDP.*;
import com.automation.web.ObjectRepository.Pages.WishlistPage.*;
import com.automation.web.ObjectRepository.Pages.Cart.*;
import com.automation.web.ObjectRepository.Pages.AddressPage.*;
import com.automation.web.ObjectRepository.Pages.PaymentPage.*;

/**
 * @author shivaprasad
 *
 */
public class VEGASF_305_FB_FreeGift_AddOfficeAddress_DC extends GlobalVariables {

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

	String testName="VEGASF_FF_305";

//	@Test(priority = 1)
//	public void LoginWithFacebook()  {
//		System.out.println("=====================VEGASF_305_START=====================");
//		objGenericMethods.HoverOnWebElement(objHeaderPageObject.getUserIcon());
//		objHeaderPageObject.LoginUnderUserIcon();
//		objLoginPageObject.LoginWithFaceBook(objGenericMethods.getValueByKey(testName, "FbEmailId"), objGenericMethods.getValueByKey(testName, "FbPassword"));
//		objLoginPageObject.VerifyUserEmailId();
//	}
	@Test(priority = 1)
	public void Login()  {//Need to replace with FaceBook login
		System.out.println("=====================VEGASF_305_FB_FreeGift_AddOfficeAddress_DC_START=====================");
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
		objAddressPageObjects.resetAddress();
		objWishlistPageObjects.resetWishlist();
		objCartPageObject.resetCart();
	}

	@Test(priority = 3)//comment it when the sub menu are working
	public void SearchItem() 	{
		System.out.println("Search Item");
		objHeaderPageObject.SearchInputBox(objGenericMethods.getValueByKey(testName,"SearchItem"));
		//objHeaderPageObject.VerifyAutoSuggestSearch();
		objHeaderPageObject.ClickOnSearchLens();
//		objPLPPageObject.verifySearchResultTittle();
//		objPLPPageObject.VerifyProductDetails();
//		objPLPPageObject.verifyCategoriesTitle();
//		objPLPPageObject.verifyColourTitle();
//		objPLPPageObject.verifyPriceTitle();
//		objPLPPageObject.FilterByDiscount("20% and above");
	}
	
	
//	@Test(priority = 3)
//	public void TShirtsFromMenMenu()  {
//
//		// Need to uncomment below 3 lines once once SFQA is up
//		System.out.println("Men >> Casual Shirts");
//		objGenericMethods.HoverOnWebElement(objHeaderPageObject.getHeaderMenu("Men"));
//		objHeaderPageObject.clickOnSubMenuUnderMen("Casual Shirts");	
//		//		objHeaderPageObject.ClickOnSearchLens();
//		objPLPPageObject.VerifyProductDetails();
//		objPLPPageObject.verifySearchResultTittle();
//		//		objPLPPageObject.verifyCategoriesTitle();
//		objPLPPageObject.verifyBrandTitle();
//		objPLPPageObject.verifyPriceTitle();
//		objPLPPageObject.verifyColourTitle();
//		objPLPPageObject.verifyDiscountTitle();	
//	}
//
//	@Test(priority = 4)
//	public void FilterByDiscount()  {
//		System.out.println("Filter By Discount");
//		objPLPPageObject.FilterByDiscount("30% and above");
//		objPLPPageObject.verifySearchResultTittle();
//		//objPLPPageObject.verifyCategoriesTitle();
//		//		objPLPPageObject.verifyBrandTitle();
//		objPLPPageObject.verifyPriceTitle();
//		objPLPPageObject.verifyColourTitle();
//		objPLPPageObject.verifyDiscountTitle();
//		objPLPPageObject.VerifyProductDetails();
//		objGenericMethods.FetchAllNameAfterSort(objPLPPageObject.AllProductName);
//		objGenericMethods.verifySortByEffectiveness();
//		objGenericMethods.scrollPage0to250();
//
//	}

	@Test(priority = 6)
	public void SaveToWishlist() 	{
		System.out.println("Save to Wishlist");
		//objPLPPageObject.ClickOnFirstProduct();
		objPDPObject.ClickSizeButtons();
		objPDPObject.ClickOnSaveBtn();
	}

	@Test(priority = 7)
	public void MoveProductFromWishlist() 	{
		System.out.println("Move Product From Wishlist");
		objGenericMethods.HoverOnWebElement(objHeaderPageObject.getUserIcon());
		objHeaderPageObject.ClickonWishlistLink();
		objWishlistPageObjects.ClickMoveToBagButton();
		objWishlistPageObjects.ClickSizeButtons();
		objWishlistPageObjects.ClickDoneButton();
		objWishlistPageObjects.VerifyProductMovedFromWishlist();
	}

	@Test(priority = 8)
	public void GoToCart()  	{
		System.out.println("Go To Cart");
		objHeaderPageObject.ClickOnCart();
		objCartPageObject.VerfiyProductIsAddedToCart();
		objCartPageObject.VerfiyTotalPrice();
	}

	@Test(priority = 9)
	public void PlaceOrder() 	{
		System.out.println("Place Order");
		objCartPageObject.ClickPlaceOrder();
	}

	@Test(priority = 10)
	public void AddAddress() 	{
		System.out.println("Add Address");
		objAddressPageObjects.AddAddress(objGenericMethods.getValueByKey(testName, "Pincode"),
				objGenericMethods.getValueByKey(testName, "Name"), objGenericMethods.getValueByKey(testName, "Mobile"),
				objGenericMethods.getValueByKey(testName, "Address"),
				objGenericMethods.getValueByKey(testName, "Locality"));
		objAddressPageObjects.SelectOfficeAddressType();
		objAddressPageObjects.clickToSaveAddress();
		objAddressPageObjects.VerifyAddressAdded();
		objAddressPageObjects.VerifyPriceDetails();
	}

	@Test(priority =11)
	public void PaymentWithCreditDebit_Card()  {
		System.out.println("Complete Payment");
		objAddressPageObjects.ClickToContinue();
		objPaymentPageObjects.VerifyOrderSumamry();
		objPaymentPageObjects.VerifyDeliveryAddress();
		objPaymentPageObjects.VerifyFinalPayableAmount();
		objPaymentPageObjects.SelectCreditDebitCard();
		objPaymentPageObjects.CreditDebitPaymentComplete(objGenericMethods.getValueByKey(testName, "CardNumber"),
				objGenericMethods.getValueByKey(testName, "CardHolderName"), objGenericMethods.getValueByKey(testName, "CVVnumber"));
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
		driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
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
		System.out.println("=====================VEGASF_305_FB_FreeGift_AddOfficeAddress_DC_END=======================");
	}
}
