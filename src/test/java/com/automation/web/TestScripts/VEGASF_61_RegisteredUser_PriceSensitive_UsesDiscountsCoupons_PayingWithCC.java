package com.automation.web.TestScripts;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

//import com.automation.core.Common.ExcelUtils;
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
 */

/**
 * TEST STEPS 1. Login(Email registered user) 2. Reset Cart, Wishlist and
 * Address Pages 3. Search (using a keyword in the Golden Set) 4. Filter by
 * Discount and sort by Popularity 5. Verify size chart in PDP 6. Add to bag
 * after selection size in PDP 7. Add to bag and place order from bag page 8.
 * Verify buy 1 get 1 9. Add address, verify price details and continue 10.
 * Payment using Manual GC And Online payment
 */
public class VEGASF_61_RegisteredUser_PriceSensitive_UsesDiscountsCoupons_PayingWithCC {

	// Only methods need to call
	// all class reference create here

	GenericMethods objGenericMethods;
	DriverInit objDriverInit;
	HeaderPageObject objHeaderPageObject;
	LoginPageObject objLoginPageObject;
	PLPPageObject objPLPPageObject;
	PDPObject objPDPObject;
	CartPageObject objCartPageObject;
	AddressPageObjects objAddressPageObjects;
	PaymentPageObjects objPaymentPageObjects;
	WishlistPageObjects objWishlistPageObjects;

	public WebDriver driver;

	String testName = "VEGASF_FF_61";

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
		objPLPPageObject = new PLPPageObject(driver);
		objPDPObject = new PDPObject(driver);
		objCartPageObject = new CartPageObject(driver);
		objWishlistPageObjects = new WishlistPageObjects(driver);
		objAddressPageObjects = new AddressPageObjects(driver);
		objPaymentPageObjects = new PaymentPageObjects(driver);
		// Open URL
		objGenericMethods.openURL(System.getProperty("url"));
	}

	@Test(priority = 1)
	public void LoginInMyntra()  {
		System.out.println("=====================VEGASF_61_RegisteredUser_PriceSensitive_UsesDiscountsCoupons_PayingWithCC_START=====================");
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
	public void ResetAll()  {
		System.out.println("Reset All");
		objWishlistPageObjects.resetWishlist();
		objAddressPageObjects.resetAddress();
		objCartPageObject.resetCart();
	}

	@Test(priority = 3)
	public void SearchItem()  {
		System.out.println("Search Item");
		objHeaderPageObject.SearchInputBox(objGenericMethods.getValueByKey(testName, "SearchItem"));
		//objHeaderPageObject.VerifyAutoSuggestSearch();
		objHeaderPageObject.ClickOnSearchLens();
		// Enable if promotions get link 
//		objPLPPageObject.clickOnPromotions();
//		objPLPPageObject.selectBuy1Get1();
	}

//	@Test(priority = 4)
//	public void FilterByDiscount()  {
//		System.out.println("Filter By Discount");
//		objGenericMethods.FetchAllNameBeforeSort(objPLPPageObject.AllProductName);
//		objPLPPageObject.HowerOnSortByDropdown();
//		objPLPPageObject.ClickOnSortDropdownByDroddownValue("Popularity");
//		objPLPPageObject.verifySearchResultTittle();
////		objPLPPageObject.verifyBrandTitle();
//		 objPLPPageObject.verifyCategoriesTitle();
//		objPLPPageObject.verifyColourTitle();
//		 objPLPPageObject.verifyDiscountTitle();
//		objPLPPageObject.verifyPriceTitle();
//		objGenericMethods.FetchAllNameAfterSort(objPLPPageObject.AllProductName);
//		// objGenericMethods.verifySortByEffectiveness();
//		// objGenericMethods.scrollPage0to250();
//		// objPLPPageObject.getClickProductByProductCode(objGenericMethods.getValueByKey(testName,
//		// "productCode"));
//		// objPLPPageObject.FilterByDiscount("30% and above");
//		objPLPPageObject.ClickOnFirstProduct();
//	}
//
//	// @Test(priority = 5)
//	// public void VerifySizeChart()  {
//	// System.out.println("Verify Size Chart");
//	// objPDPObject.ClickOnSizeChartLink();
//	// }

	@Test(priority = 6)
	public void SelectSizeAndQuantity()  {
		System.out.println("Select Size And Quantity");	
//		objPDPObject.VerifyActualPriceFromPDP();
//		objPDPObject.VerifyDiscountPercentageFromPDP();
		objPDPObject.ClickSizeButtons();
		objPDPObject.ClickAddToBagBtn();
		objPDPObject.verifyPDPTitleFromPDP();
		objPDPObject.VerifySellingPriceFromPDP();
		objPDPObject.VerifyLargeThumbnailFromPDP();
		objPDPObject.VerifySmallThumbnailsFromPDP();
		objPDPObject.VerifyBestOfferLinkFromPDP();
		objHeaderPageObject.ClickOnCart();
		objCartPageObject.VerfiyTotalPrice();
		objCartPageObject.VerfiyProductIsAddedToCart();
	}

	@Test(priority = 7)
	public void VerifyBuyOneGetOne()  {
		System.out.println("Verify Buy One Get One");
		// Enable when bogo get enable
		// objCartPageObject.AddQuantity();
		// objCartPageObject.VerifyBuy1Get1SuccessMessage();
		// objCartPageObject.SelectSize("2034849","M");
		objCartPageObject.ClickPlaceOrder();
	}
	/**
	 * Modified by  :Anu
	 * Address details is reading from testdata.ini 
	 */
	@Test(priority = 8)
	public void AddNewAddress()  {
		System.out.println("Add New Address");
		objAddressPageObjects.AddAddress(objGenericMethods.getValueByKey(testName, "Pincode"),
				objGenericMethods.getValueByKey(testName, "Name"), objGenericMethods.getValueByKey(testName, "Mobile"),
				objGenericMethods.getValueByKey(testName, "Address"),
				objGenericMethods.getValueByKey(testName, "Locality"));
		objAddressPageObjects.SelectOfficeAddressType();
		objAddressPageObjects.clickToSaveAddress();
		objAddressPageObjects.VerifyAddressAdded();
		objAddressPageObjects.VerifyPriceDetails();
		objAddressPageObjects.ClickToContinue();
	}

	@Test(priority =10)
	public void PaymentWithCreditDebit_Card()  {
		
		System.out.println("Complete Payment");
		objPaymentPageObjects.VerifyOrderSumamry();
		objPaymentPageObjects.VerifyDeliveryAddress();
		objPaymentPageObjects.VerifyFinalPayableAmount();
//		objPaymentPageObjects.SelectGiftCards();
//		objPaymentPageObjects.EnterGiftCardData(objGenericMethods.getValueByKey(testName, "GiftCardNumber"), 
//				objGenericMethods.getValueByKey(testName, "GiftCardPin"));
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

	@AfterTest
	public void afterTest() {
		driver.quit();
		System.out.println("=====================VEGASF_61_RegisteredUser_PriceSensitive_UsesDiscountsCoupons_PayingWithCC_END=======================");
	}
}
