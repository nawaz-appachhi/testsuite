package com.automation.web.TestScripts;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
//import com.automation.core.Common.ExcelUtils;
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
 * Modified By: Aishurya
 * Reason: Removed product code
 *
 */
public class VEGASF_52_RegisteredUser_Gifting_PopularBrandStyle_PayingWithDC extends GlobalVariables {

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

	String testName="VEGASF_FF_52";
	
	@Test(priority = 1)
	public void LoginInMyntra() {
		System.out.println("=====================VEGASF_52_RegisteredUser_Gifting_PopularBrandStyle_PayingWithDC_START=====================");
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
	public void ResetAll()  
	{
		System.out.println("Reset All");
		objWishlistPageObjects.resetWishlist();
		objAddressPageObjects.resetAddress();
		objCartPageObject.resetCart();
	}

	@Test(priority = 3)
	public void SearchItem() 
	{
		System.out.println("Search Item");
		objHeaderPageObject.SearchInputBox(objGenericMethods.getValueByKey(testName,"SearchItem"));
//		objHeaderPageObject.VerifyAutoSuggestSearch();
		objHeaderPageObject.ClickOnSearchLens();
		objPLPPageObject.verifyColourTitle();
	//	objPLPPageObject.verifyDiscountTitle();
		objPLPPageObject.verifyPriceTitle();
	//    objPLPPageObject.verifyBrandTitle();//need to uncomment when the site is up and running
		//objGenericMethods.FetchAllNameBeforeSort(objPLPPageObject.getSearchResultList());//uncomment its when it is up and running
		if(System.getProperty("url").contains("sfqa")) {
			objPLPPageObject.HowerOnSortByDropdown();
			objPLPPageObject.ClickOnSortDropdownByDroddownValue("Popularity");
			//objGenericMethods.FetchAllNameAfterSort(objPLPPageObject.getSearchResultList());
			objPLPPageObject.FilterByColour(objGenericMethods.getValueByKey(testName,"ColorName"));
			
		}
		else {
			objPLPPageObject.HowerOnSortByDropdown();
			objPLPPageObject.ClickOnSortDropdownByDroddownValue("Popularity");
			objPLPPageObject.ClickmoreColors();
			objPLPPageObject.FilterByColour(objGenericMethods.getValueByKey(testName,"ColorName"));
			//objGenericMethods.FetchAllNameAfterSort(objPLPPageObject.getSearchResultList());
		}
		
//		objGenericMethods.verifySortByEffectiveness();
//		objPLPPageObject.verifySearchResultTittle();

	//	objPDPObject.ClickSizeButtons();
     //	objPLPPageObject.verifyCategoriesTitle();
//		objPLPPageObject.ClickOnFirstProduct();
     	try {
			objPLPPageObject.FilterByColour("NOCOLOR");
		} catch (Exception e) {
		}
     	objPLPPageObject.getClickProductByProductCode(objGenericMethods.getValueByKey(testName,"productCode"));
	}

	@Test(priority = 4)
	public void SaveProduct() 
	{
		System.out.println("Save Product");
		objPDPObject.VerifyProductCodefromPDP("xyz");//String parameter is not getting utilized in calling function
		objPDPObject.VerifyLargeThumbnailFromPDP();
		objPDPObject.VerifySmallThumbnailsFromPDP();
		objPDPObject.VerifyBestOfferLinkFromPDP();
		//objPDPObject.VerifySimilarProductsHeaderFromPDP();	//need to uncomment when the site is up and running
		objPDPObject.ClickSizeButtons();
		objPDPObject.ClickOnSaveBtn();
		objPDPObject.HoverAllSmallThumbnails();
	}

	@Test(priority = 5)
	public void GoToWishlist() {
		System.out.println("Go To Wishlist");
		objGenericMethods.HoverOnWebElement(objHeaderPageObject.getUserIcon());
		objHeaderPageObject.ClickOnWishlist();
	}
	
	@Test(priority = 6)
	public void MoveProductFromWishlist() {
		System.out.println("Move Product From Wishlist");
		objWishlistPageObjects.ClickMoveToBagButton();
		objWishlistPageObjects.ClickDoneButton();
	}

	@Test(priority = 7)
	public void GoToCart() {
		System.out.println("Go To Cart");
		objHeaderPageObject.ClickOnCart();
		objCartPageObject.VerfiyProductIsAddedToCart();
		objCartPageObject.VerfiyTotalPrice();
	}
	
//	@Test(priority = 6)//comment it when the wish list is working
//	public void newMethodForPdp() {
//		objPDPObject.ClickSizeButtons();
//		objPDPObject.ClickAddToBagBtn();
//		objPDPObject.ClickGoToBagBtn();
//	}
//	
	/**
	 * Modified by  :Anu
	 * Gift wrap test data is reading from testdata.ini
	 * Modify by : Neeraj
	 * In SFQA, no gift wrap checkbox displaying
	 */
//	@Test(priority = 8)
//	public void GiftWrap() 
//	{
//		System.out.println("Gift Wrap");
//		objCartPageObject.giftPackCheckbox();
//		objCartPageObject.SendGiftCard(objGenericMethods.getValueByKey(testName, "RecipientName"), objGenericMethods.getValueByKey(testName, "Message"), objGenericMethods.getValueByKey(testName, "SenderName"));
//	}
	
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
		if (driver == null) {
			AssertJUnit.assertFalse(false);
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
		System.out.println("=====================VEGASF_52_RegisteredUser_Gifting_PopularBrandStyle_PayingWithDC_END=======================");
	}
}
