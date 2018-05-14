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

public class VEGASF_151_RegisteredUser_SimilarProducts_GiftWrap_StaggeredCombo_PayWithCoD extends GlobalVariables {

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

	String testName="VEGASF_FF_151";
	
	
	@Test(priority = 1)
	public void LoginInMyntra() {
		System.out.println("=====================VEGASF_151_RegisteredUser_SimilarProducts_GiftWrap_StaggeredCombo_PayWithCoD_START=====================");
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
	public void ResetAll() {
		System.out.println("Reset All");
		objAddressPageObjects.resetAddress();
		objWishlistPageObjects.resetWishlist();
		objCartPageObject.resetCart();
	}
	
	@Test(priority = 3)
	public void SearchItem() {
		System.out.println("Search Item");
		objHeaderPageObject.SearchInputBox(objGenericMethods.getValueByKey(testName,"SearchItem"));
		//objHeaderPageObject.VerifyAutoSuggestSearch();
		objHeaderPageObject.ClickOnSearchLens();
		//objPLPPageObject.VerifyProductDetails();
//		objPLPPageObject.verifySearchResultTittle();
////      objPLPPageObject.verifyBrandTitle();
//    		objPLPPageObject.verifyCategoriesTitle();
//    		objPLPPageObject.verifyPriceTitle();
//		objPLPPageObject.verifyColourTitle();
//		objPLPPageObject.verifyDiscountTitle();
	}

//	@Test(priority = 4)
//	public void NavigatefromPLPtoPDP() 
//	{
//		System.out.println("Navigate from PLP to PDP");
//		objPLPPageObject.ClickOnFirstProduct();
//		objPDPObject.verifyPDPTitleFromPDP();
//		objPDPObject.VerifySellingPriceFromPDP();
//		objPDPObject.VerifyLargeThumbnailFromPDP();
//		objPDPObject.VerifySmallThumbnailsFromPDP();
//		objPDPObject.VerifyBestOfferLinkFromPDP();
////		objPDPObject.VerifySimilarProductsHeaderFromPDP();
//	}
//	
	
	@Test(priority = 5)
	public void ViewSizeChart() {
		System.out.println("View Size Chart");
		objPDPObject.ClickOnSizeChartLink();
	}
	
	@Test(priority = 6)
	public void AddToBag() {
		System.out.println("Add To Bag");
		objPDPObject.ClickSizeButtons();
		objPDPObject.ClickAddToBagBtn();
		objPDPObject.verifyPDPTitleFromPDP();
		objPDPObject.VerifySellingPriceFromPDP();
		objPDPObject.VerifyLargeThumbnailFromPDP();
		objPDPObject.VerifySmallThumbnailsFromPDP();
		objPDPObject.VerifyBestOfferLinkFromPDP();
	}
	
	@Test(priority = 7)
	public void GoToCart() {
		System.out.println("Go To Cart");
		objHeaderPageObject.ClickOnCart();
		objCartPageObject.VerfiyProductIsAddedToCart();
		objCartPageObject.VerfiyTotalPrice();
	}
	/**
	 * Modified by  :Anu
	 * Gift wrap test data is reading from testdata.ini
	 */
	@Test(priority = 8)
	public void PlaceOrder() {
		System.out.println("Place Order");
		objCartPageObject.ClickPlaceOrder();
	}
	/**
	 * Modified by  :Anu
	 * Address details is reading from testdata.ini 
	 */
	@Test(priority = 9)
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

	@Test(priority = 10)
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
	@Test(priority = 11)
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
		System.out.println("=====================VEGASF_151_RegisteredUser_SimilarProducts_GiftWrap_StaggeredCombo_PayWithCoD_END=======================");
	}
}
