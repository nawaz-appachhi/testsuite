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
 * @author 300022495-shivaprasad
 *
 */

/**
 *  Steps to do Automation  
 *  Login with valid user name and password  
 *  Clear all the products present in the cart,wish list and clear the address 
 *  Search product using  Auto suggestion  
 *   Filter	product by Discount   
 *   Navigate from ProductListPage to ProductDiscriptionPage  
 *  Save and Add the product  to the bag
 *   Add New address - Home
 *  Continue payment through NetBanking
 */

    public class VEGASF_133_RegisteredUser_PDPToWishlist_Checkout_PayWithDC extends GlobalVariables {

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

		public WebDriver driver;

		String testName="VEGASF_FF_133";

		@Test(priority = 1)
		public void LoginInMyntra()  {
			System.out.println("=====================VEGASF_133_RegisteredUser_PDPToWishlist_Checkout_PayWithDC_START=====================");
			System.out.println("Login In Myntra");
			objGenericMethods.HoverOnWebElement(objHeaderPageObject.getUserIcon());
			objHeaderPageObject.LoginUnderUserIcon();
			objLoginPageObject.readSession("BoforeLogin");
			objLoginPageObject.Login(objGenericMethods.getValueByKey(testName, "UserName"),objGenericMethods.getValueByKey(testName, "Password"));
			objLoginPageObject.LogInButtonClick();
			objLoginPageObject.VerifyUserEmailId();
			objLoginPageObject.readSession("AfterLogin");
		}
		
		/**
		 * Name: Irfan_Shariff
		 * Description: This (priority = 2) will help user to Reset their account.
		 * Created By: Irfan_Shariff
		 */
		@Test(priority = 2)
	    public void ResetAll()  {
			System.out.println("Reset All");
	        objWishlistPageObjects.resetWishlist();
	        objCartPageObject.resetCart();
	        objAddressPageObjects.resetAddress();
	    }
	
		/**
		 * Modified by : Irfan Shariff
		 * Description : Added Thread.sleep(MIDSLEEP) before "Searching an Item". If this sleep not added search function fails.
		 */
//		@Test(priority = 3)
//		public void SearchItem()  {
//			System.out.println("Search Item");
//			objHeaderPageObject.SearchInputBox(objGenericMethods.getValueByKey(testName,"SearchItem"));
//			//objHeaderPageObject.VerifyAutoSuggestSearch();
//			objHeaderPageObject.ClickOnSearchLens();
//			objPLPPageObject.VerifyProductDetails();
//			objPLPPageObject.verifySearchResultTittle();
//			objPLPPageObject.verifyCategoriesTitle();
////			objPLPPageObject.verifyBrandTitle();
//			objPLPPageObject.verifyPriceTitle();
//			objPLPPageObject.verifyColourTitle();
//			objPLPPageObject.verifyDiscountTitle();
//		}
		
//		@Test(priority = 4)
//		public void FilterByDiscount()  {
//			System.out.println("Filter By Discount");
//			objGenericMethods.FetchAllNameBeforeSort(objPLPPageObject.AllProductName);
//			objPLPPageObject.HowerOnSortByDropdown();
//			objPLPPageObject.ClickOnSortDropdownByDroddownValue("Popularity");
//			objPLPPageObject.verifySearchResultTittle();
//			objPLPPageObject.verifyCategoriesTitle();
////			objPLPPageObject.verifyBrandTitle();
//			objPLPPageObject.verifyPriceTitle();
//			objPLPPageObject.verifyColourTitle();
//			objPLPPageObject.verifyDiscountTitle();
//			objPLPPageObject.VerifyProductDetails();
//			objGenericMethods.FetchAllNameAfterSort(objPLPPageObject.AllProductName);
//			objGenericMethods.verifySortByEffectiveness();
//			objGenericMethods.scrollPage0to250();
//		}
//		
//		@Test(priority = 5)
//		public void SaveItem()  
//		{
//			System.out.println("Save Item");
//			objPLPPageObject.SaveFirstProduct();
//		}
		
//		@Test(priority = 6)
//		public void ResetWishlist()  
//		{
//			System.out.println("Reset Wishlist");
//			objWishlistPageObjects.resetWishlist();
//		}
		
		/**
		 * Modified by : Irfan Shariff
		 * Description : Added Thread.sleep(MIDSLEEP) before "Searching an Item". If this sleep not added search function fails.
		 */
		@Test(priority = 7)
		public void SearchItemAgain()  {
			System.out.println("Search Item Again");
			objHeaderPageObject.SearchInputBox(objGenericMethods.getValueByKey(testName,"SearchItem"));
			//objHeaderPageObject.VerifyAutoSuggestSearch();
			objHeaderPageObject.ClickOnSearchLens();
//			objPLPPageObject.VerifyProductDetails();
//			objPLPPageObject.verifySearchResultTittle();
//			objPLPPageObject.verifyCategoriesTitle();
////			objPLPPageObject.verifyBrandTitle();
//			objPLPPageObject.verifyPriceTitle();
//			objPLPPageObject.verifyColourTitle();
//			objPLPPageObject.verifyDiscountTitle();
		}
		
		/**
		 * Modified by : Irfan Shariff
		 * Description : Added Thread.sleep(MIDSLEEP) before "ClickAddToBagBtn()". If this sleep not added "add to bag" function fails.
		 */
		@Test(priority = 8)
		public void NavigateToPDPfromPLPAgain()  {
			System.out.println("Navigate To PDP from PLP Again");
//			objPLPPageObject.ClickOnFirstProduct();
//			objPDPObject.verifyPDPTitleFromPDP();
////			objPDPObject.VerifyActualPriceFromPDP();
//			objPDPObject.VerifySellingPriceFromPDP();
////			objPDPObject.VerifyDiscountPercentageFromPDP();
//			objPDPObject.VerifyLargeThumbnailFromPDP();
//			objPDPObject.VerifySmallThumbnailsFromPDP();
//			objPDPObject.VerifyBestOfferLinkFromPDP();	
//			Reporter.log("Product clicked from list page");
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
		 * Name: Irfan_Shariff
		 * Description: This (priority = 10) will help user to Add New Address & also verify the price details.
		 * Modified By: Irfan_Shariff
		 */

		@Test(priority = 9)
	    public void AddressPage()  {
			System.out.println("Address Page");
			objAddressPageObjects.AddAddress(objGenericMethods.getValueByKey(testName, "Pincode"),
					objGenericMethods.getValueByKey(testName, "Name"), objGenericMethods.getValueByKey(testName, "Mobile"),
					objGenericMethods.getValueByKey(testName, "Address"),
					objGenericMethods.getValueByKey(testName, "Locality"));
	        objAddressPageObjects.SelectHomeAddressType();
	        objAddressPageObjects.clickToSaveAddress();
	        objAddressPageObjects.VerifyAddressAdded();
	        objAddressPageObjects.VerifyPriceDetails();
	    }
		
		@Test(priority =10)
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
		
		@Test(priority = 11)
		public void LogOut()  {
			objLoginPageObject.LogOut();
			objLoginPageObject.readSession("AfterLogout");
		}
		
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
			// close excel reference
			// check condition before closing or quite
		    driver.quit();
		    System.out.println("=====================VEGASF_133_RegisteredUser_PDPToWishlist_Checkout_PayWithDC_END=======================");
		}
}