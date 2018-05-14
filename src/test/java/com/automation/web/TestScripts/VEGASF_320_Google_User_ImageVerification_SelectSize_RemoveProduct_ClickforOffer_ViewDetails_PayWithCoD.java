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
import com.automation.web.ObjectRepository.Pages.Login.LoginPageObject;
import com.automation.web.ObjectRepository.Pages.PDP.PDPObject;
import com.automation.web.ObjectRepository.Pages.PLP.PLPPageObject;
import com.automation.web.ObjectRepository.Pages.PaymentPage.PaymentPageObjects;
import com.automation.web.ObjectRepository.Pages.WishlistPage.WishlistPageObjects;

/**
 * @author 300019224 - Aishurya
 *
 * Steps:
 * LoginWithGoogle
 * HomePage
 * SearchItem
 * CheckForTopButtons
 * CheckImageVerification
 * SlelectSize
 * AddToBag
 * RemoveProduct
 * ApplyCoupan
 * ViewDetails
 * PaymenWithMynt+COD
 * 
 */
public class VEGASF_320_Google_User_ImageVerification_SelectSize_RemoveProduct_ClickforOffer_ViewDetails_PayWithCoD extends GlobalVariables{
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
	String testName = "VEGASF_FF_320";

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
//		System.out.println("=====================VEGASF_320_START=====================");
//		System.out.println("Login In Myntra");
//		objGenericMethods.HoverOnWebElement(objHeaderPageObject.getUserIcon());
//		objHeaderPageObject.LoginUnderUserIcon();
//		objLoginPageObject.readSession("BoforeLogin");
//		objLoginPageObject.LoginWithGmail(objGenericMethods.getValueByKey(testName, "GmailUserName"), objGenericMethods.getValueByKey(testName, "GmailPassword"));
//		objLoginPageObject.VerifyUserEmailId();
//		objLoginPageObject.readSession("AfterLogin");
//			}
	
	@Test(priority = 1)
	public void LoginInMyntra()  {
		System.out.println("=====================VEGASF_320_Google_User_ImageVerification_SelectSize_RemoveProduct_ClickforOffer_ViewDetails_PayWithCoD_START=====================");
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
//	public void CheckForTopButtons()  {
//		objPLPPageObject.ShowMoreProductsLinkClick();
//		objPLPPageObject.ClickTopButton();
//		objPLPPageObject.SaveFirstProduct();
//		objPLPPageObject.ClickOnFirstProduct();
//	}
//	@Test(priority = 4)
//	public void CheckImageVerification() {
//		objPDPObject.VerifyLargeThumbnailFromPDP();
//		objPDPObject.VerifySmallThumbnailsFromPDP();
//	}
	@Test(priority = 5)
	public void SlelectSize() {
	    objPDPObject.ClickOnSaveBtn();
		objPDPObject.SelectSize();
	}
	@Test(priority = 6)
	public void AddToBag() {
		objPDPObject.ClickAddToBagBtn();
	}
	@Test(priority = 7)
	public void RemoveProduct()  {
		objHeaderPageObject.ClickOnCartIcon();
		objCartPageObject.ClickRemoveLink();
		objHeaderPageObject.ClickOnMyntraHeaderLogoFromCartPage();
		objGenericMethods.HoverOnWebElement(objHeaderPageObject.getUserIcon());
		objHeaderPageObject.ClickonWishlistLink();
		objWishlistPageObjects.VerifyWishlistTitle();
		objWishlistPageObjects.ClickMoveToBagButton();
		objWishlistPageObjects.ClickSizeButtons();
		objWishlistPageObjects.ClickDoneButton();
	}
	@Test(priority = 8)
	public void ApplyCoupan()  {
		objHeaderPageObject.ClickOnCartIcon();
		System.out.println("Apply Coupon");
		objCartPageObject.ClickApplyCouponBtn();
		objCartPageObject.ClickCancelButton();
	}
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
		System.out.println("Complete Payment");
		objAddressPageObjects.ClickToContinue();
	}
	
	@Test(priority = 10)
	public void CompletePaymentOnline() 	{
		System.out.println("Complete Payment");
		objPaymentPageObjects.VerifyOrderSumamry();
		objPaymentPageObjects.VerifyDeliveryAddress();
		objPaymentPageObjects.SelectCashOnDelivery();
		objPaymentPageObjects.verifyCODavailabilityThenPay();
	}
	
	@Test(priority = 11)
	public void LogOut()  {
		objLoginPageObject.LogOut();
		objLoginPageObject.readSession("AfterLogout");
	}
	@AfterTest
	public void afterTest() {
		// quit browser object
		// check condition before closing or quite
		driver.quit();
		System.out.println("=====================VEGASF_320_Google_User_ImageVerification_SelectSize_RemoveProduct_ClickforOffer_ViewDetails_PayWithCoD_END=======================");
	}
}
