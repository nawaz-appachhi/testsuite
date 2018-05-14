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
 * @author 300019224-Aishurya
 * 
 * LoginWithFacebook
 * SearchItem
 * CheckForTopButtons
 * Filter_Price
 * EmptyWislist
 * AddMoreFromWishlist
 * PercentageDiscount
 * RemoveAddress
 * PaymentWithNetBanking
 * 
 */
public class VEGASF_319_FB_User_EmptyWishlist_AddMoreFromWishlist_PercentageDiscount_RemoveAddress_PayWithDD extends GlobalVariables{
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
	String testName = "VEGASF_FF_319";

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
//	public void LoginWithFacebook()  {
//		System.out.println("=====================VEGASF_319_START=====================");
//		System.out.println("Login In Myntra");
//		objGenericMethods.HoverOnWebElement(objHeaderPageObject.getUserIcon());
//		objHeaderPageObject.LoginUnderUserIcon();
//		objLoginPageObject.readSession("BoforeLogin");
//		objLoginPageObject.LoginWithFaceBook(objGenericMethods.getValueByKey(testName, "FbUserName"), objGenericMethods.getValueByKey(testName, "FbPassword"));
//		objLoginPageObject.VerifyUserEmailId();
//		objLoginPageObject.readSession("AfterLogin");
//			}
	@Test(priority = 1)
	public void LoginInMyntra() {//Need to replace with faceBook login
		System.out.println("=====================VEGASF_319_FB_User_EmptyWishlist_AddMoreFromWishlist_PercentageDiscount_RemoveAddress_PayWithDD_START=====================");
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
//	}
	
	@Test(priority = 4)
	public void Filter_Price() {
		//objPLPPageObject.ClickOnFirstProduct();
		objPDPObject.ClickOnSaveBtn();
		objPDPObject.ClickSizeButtons();
		objPDPObject.ClickAddToBagBtn();
		
	}
	@Test(priority = 5)
	public void EmptyWislist() {
		objGenericMethods.HoverOnWebElement(objHeaderPageObject.getUserIcon());
		objHeaderPageObject.ClickonWishlistLink();
		objWishlistPageObjects.VerifyWishlistTitle();
		objWishlistPageObjects.RemoveAllItemsFromWishlist();
	}
	@Test(priority = 6)
	public void AddMoreFromWishlist()  {
		objHeaderPageObject.ClickOnCartIcon();
		objCartPageObject.ClickToAddMoreFromWishlist();
		objHeaderPageObject.ClickOnCartIcon();
		System.out.println("Place Order");
		objCartPageObject.ClickPlaceOrder();
	}
	@Test(priority = 7)
	public void PercentageDiscount() {
		
	}
	@Test(priority = 8)
	public void RemoveAddress()  {
		System.out.println("Add Address");
		objAddressPageObjects.AddAddress(objGenericMethods.getValueByKey(testName, "Pincode"),
				objGenericMethods.getValueByKey(testName, "Name"), objGenericMethods.getValueByKey(testName, "Mobile"),
				objGenericMethods.getValueByKey(testName, "Address"),
				objGenericMethods.getValueByKey(testName, "Locality"));
		objAddressPageObjects.SelectOfficeAddressType();
		objAddressPageObjects.clickToSaveAddress();
		objAddressPageObjects.clickToRemoveAddress();
		System.out.println("Add Address");
		objAddressPageObjects.AddAddress(objGenericMethods.getValueByKey(testName, "Pincode"),
				objGenericMethods.getValueByKey(testName, "Name"), objGenericMethods.getValueByKey(testName, "Mobile"),
				objGenericMethods.getValueByKey(testName, "Address"),
				objGenericMethods.getValueByKey(testName, "Locality"));
		objAddressPageObjects.SelectOfficeAddressType();
		objAddressPageObjects.clickToSaveAddress();
		System.out.println("Complete Payment");
		objAddressPageObjects.ClickToContinue();
//		objPaymentPageObjects.VerifyOrderSumamry();
//		objPaymentPageObjects.VerifyDeliveryAddress();
//		objPaymentPageObjects.VerifyFinalPayableAmount();
	}
	
	@Test(priority =9)
	public void PaymentWithCreditDebit_Card()  {
		System.out.println("Complete Payment");
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
	@Test(priority = 10)
	public void LogOut()  {
		objLoginPageObject.LogOut();
		objLoginPageObject.readSession("AfterLogout");
	}
	
	@AfterTest
	public void afterTest() {
		// quit browser object
		// check condition before closing or quite
		driver.quit();
		System.out.println("=====================VEGASF_319_FB_User_EmptyWishlist_AddMoreFromWishlist_PercentageDiscount_RemoveAddress_PayWithDD_END=======================");
	}
}
