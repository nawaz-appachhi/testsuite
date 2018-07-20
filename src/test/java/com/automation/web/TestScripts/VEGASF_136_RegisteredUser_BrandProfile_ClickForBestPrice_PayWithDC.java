package com.automation.web.TestScripts;

import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

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
 * @author 300022495-Shivaprasad
 *
 */

/**
 *  Steps to do Automation
 *  Login with valid user name and password
 *  Clear all the products present in the cart,wish list.
 *  Search product using  Auto suggestion
 *  Navigate from ProductListPage to ProductDiscriptionPage
 *  Click for the best Offer 
 *  Save and Add the product  to the bag
 *  remove the product from the bag
 *  Edit the address
 *  Continue payment through PaymentGateway
 */

public class VEGASF_136_RegisteredUser_BrandProfile_ClickForBestPrice_PayWithDC extends GlobalVariables {

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

		String testName = "VEGASF_FF_136";
		
		@BeforeTest
		public void beforeTest() {
			// Initial browser Objects and methods objects
			objDriverInit = new DriverInit();
			System.out.println("Browser Type : " + System.getProperty("browserType"));
			System.out.println("URL : " + System.getProperty("url"));
			driver = objDriverInit.getWebDriver(System.getProperty("browserType"), "Local");
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			driver.manage().window().maximize();
			if (driver == null) {
				Assert.assertFalse(false, "WebDriver Object not Initited and Found Null Value");
			}
			objHeaderPageObject = new HeaderPageObject(driver);
			objGenericMethods = new GenericMethods(driver);
			objLoginPageObject = new LoginPageObject(driver);
			objPLPPageObject = new PLPPageObject(driver);
			objPDPObject= new PDPObject(driver);
			objPDPObject = new PDPObject(driver);
			objWishlistPageObjects = new WishlistPageObjects(driver);
			objCartPageObject = new CartPageObject(driver);
			objAddressPageObjects = new AddressPageObjects(driver);
			objPaymentPageObjects = new PaymentPageObjects(driver);

			// Open URL
			objGenericMethods.openURL(System.getProperty("url"));
		}
			
		@Test(priority = 1)
		public void LoginInMyntra()  {
			System.out.println("=====================VEGASF_136_RegisteredUser_BrandProfile_ClickForBestPrice_PayWithDC_START=====================");
			System.out.println("Login In Myntra");
			objGenericMethods.HoverOnWebElement(objHeaderPageObject.getUserIcon());
			objHeaderPageObject.LoginUnderUserIcon();
			objLoginPageObject.readSession("BoforeLogin");
			objLoginPageObject.Login(objGenericMethods.getValueByKey(testName, "UserName"),	objGenericMethods.getValueByKey(testName, "Password"));
			objLoginPageObject.LogInButtonClick();
			objLoginPageObject.VerifyUserEmailId();
			objLoginPageObject.readSession("AfterLogin");
		}
		
		@Test(priority = 2)
		public void ResetAll()  {
			System.out.println("Reset All");
			objCartPageObject.resetCart();
			objWishlistPageObjects.resetWishlist();
			objAddressPageObjects.resetAddress();
		}
	
		@Test(priority = 3)
		public void SearchItem()  {
			System.out.println("Search Item");
			objHeaderPageObject.SearchInputBox(objGenericMethods.getValueByKey(testName,"SearchItem"));
//			objHeaderPageObject.VerifyAutoSuggestSearch();
			objHeaderPageObject.ClickOnSearchLens();
//			objPLPPageObject.verifySearchResultTittle();
////			objPLPPageObject.verifyCategoriesTitle();
////			objPLPPageObject.verifyBrandTitle();
//			objPLPPageObject.verifyPriceTitle();
//			objPLPPageObject.verifyColourTitle();
////			objPLPPageObject.verifyDiscountTitle();
		}

//		@Test(priority = 4)
//		public void NavigateToPDPfromPLP()  {
//			System.out.println("Navigate To PDP from PLP");
//			objPLPPageObject.ClickOnFirstProduct();
////			objPLPPageObject.getClickProductByProductCode(objGenericMethods.getValueByKey(testName,"productCode"));	
//			objPDPObject.verifyPDPTitleFromPDP();
//			objPDPObject.VerifySellingPriceFromPDP();
////			objPDPObject.VerifyDiscountPercentageFromPDP();
////			objPDPObject.VerifyActualPriceFromPDP();
//			objPDPObject.VerifyLargeThumbnailFromPDP();
//			objPDPObject.VerifySmallThumbnailsFromPDP();
////			objPDPObject.VerifyBestOfferLinkFromPDP();
////			objPDPObject.VerifySimilarProductsHeaderFromPDP();
//		}
		
		@Test(priority = 5)
		public void CheckForBestOffer()  {
			System.out.println("Check For Best Offer");
			objPDPObject.ClickOnBestOfferLink();
		}
		
		@Test(priority = 6)
		public void AddToBag()  {
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
		public void RemoveProduct() {
			System.out.println("Remove Product");
			objCartPageObject.resetCart();	
		}
		
		@Test(priority = 8)
		public void SearchItemAgain()  {
			System.out.println("Search Item Again");
			objHeaderPageObject.SearchInputBox(objGenericMethods.getValueByKey(testName,"SearchItem"));
			objHeaderPageObject.ClickOnSearchLens();
//			objPLPPageObject.verifySearchResultTittle();
////			objPLPPageObject.verifyCategoriesTitle();
////			objPLPPageObject.verifyBrandTitle();
//			objPLPPageObject.verifyPriceTitle();
//			objPLPPageObject.verifyColourTitle();
//			objPLPPageObject.verifyDiscountTitle();
		}
		
		@Test(priority = 9)
		public void NavigateToPDPfromPLPAgain()  {
//			System.out.println("Navigate To PDP from PLP Again");
//			objPLPPageObject.ClickOnFirstProduct();
////			objPLPPageObject.getClickProductByProductCode(objGenericMethods.getValueByKey(testName,"productCode"));
//			objPDPObject.verifyPDPTitleFromPDP();
//			objPDPObject.VerifySellingPriceFromPDP();
////			objPDPObject.VerifyDiscountPercentageFromPDP();
////			objPDPObject.VerifyActualPriceFromPDP();
//			objPDPObject.VerifyLargeThumbnailFromPDP();
//			objPDPObject.VerifySmallThumbnailsFromPDP();
////			objPDPObject.VerifyBestOfferLinkFromPDP();
////			objPDPObject.VerifySimilarProductsHeaderFromPDP();
			objPDPObject.ClickSizeButtons();
			objPDPObject.ClickAddToBagBtn();
			objPDPObject.verifyPDPTitleFromPDP();
			objPDPObject.VerifySellingPriceFromPDP();
			objPDPObject.VerifyLargeThumbnailFromPDP();
			objPDPObject.VerifySmallThumbnailsFromPDP();
			objPDPObject.VerifyBestOfferLinkFromPDP();
			objPDPObject.ClickGoToBagBtn();
			objCartPageObject.VerfiyProductIsAddedToCart();
			objCartPageObject.VerfiyTotalPrice();
			objCartPageObject.ClickPlaceOrder();
			}
		
		/**
		* Name: Irfan Shariff
		* Description: Here MIDSLEEP thread is required before "objAddressPageObjects.clickToEditAddress();", if not sometimes Edit Address gets failed. Hence a MIDSLEEP is required.
		* Modified by <Irfan Shariff>
		*/
		/**
		 * Modified by  :Anu
		 * Modified EditAddress()
		 */
		@Test(priority = 10)
		public void EditAddress()  {
			System.out.println("Edit Address");
			objAddressPageObjects.AddAddress(objGenericMethods.getValueByKey(testName, "Pincode"),
					objGenericMethods.getValueByKey(testName, "Name"), objGenericMethods.getValueByKey(testName, "Mobile"),
					objGenericMethods.getValueByKey(testName, "Address"),
					objGenericMethods.getValueByKey(testName, "Locality"));
			objAddressPageObjects.SelectOfficeAddressType();
			objAddressPageObjects.clickToSaveAddress();
			objAddressPageObjects.VerifyAddressAdded();
			objAddressPageObjects.VerifyPriceDetails();
			objAddressPageObjects.clickToEditAddress();
			objAddressPageObjects.EditNameTextField("VEGASF131 " + objGenericMethods.datetime("ddMMyyyyHHmmss"));
			objAddressPageObjects.clickToSaveAddress();
			objAddressPageObjects.VerifyAddressAdded();
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
			objPaymentPageObjects.handelAlert();
			objPaymentPageObjects.VerifyOrderNumber();
			objPaymentPageObjects.GoToOrderDetailsPage();
			objPaymentPageObjects.VerifyOrdernumber();
		}
		
		@Test(priority = 12)
		public void LogOut()  {
			objLoginPageObject.LogOut();
			objLoginPageObject.readSession("AfterLogout");
		}
		
		@AfterTest
		public void afterTest() {
			// quit browser object
			// check condition before closing or quite
			   driver.quit();
			   System.out.println("=====================VEGASF_136_RegisteredUser_BrandProfile_ClickForBestPrice_PayWithDC_END=======================");
		}
	}
