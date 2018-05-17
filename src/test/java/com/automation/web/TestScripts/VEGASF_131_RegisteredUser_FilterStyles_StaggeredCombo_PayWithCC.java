/**
 * 
 */
package com.automation.web.TestScripts;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import com.automation.core.Common.GlobalVariables;
import com.automation.core.web.DriverInit;
import com.automation.core.web.GenericMethods;
import com.automation.web.ObjectRepository.Pages.AddressPage.AddressPageObjects;
import com.automation.web.ObjectRepository.Pages.Cart.CartPageObject;
import com.automation.web.ObjectRepository.Pages.Header.HeaderPageObject;
import com.automation.web.ObjectRepository.Pages.HomePage.HomePageObject;
import com.automation.web.ObjectRepository.Pages.Login.LoginPageObject;
import com.automation.web.ObjectRepository.Pages.PDP.PDPObject;
import com.automation.web.ObjectRepository.Pages.PLP.PLPPageObject;
import com.automation.web.ObjectRepository.Pages.PaymentPage.PaymentPageObjects;
import com.automation.web.ObjectRepository.Pages.WishlistPage.WishlistPageObjects;

/**
 * @author 300021279 - Sangam_Mohanty
 *
 */

/**
 * Steps to do automation

* login with valid user name and password
 *  
 *  clear all the products present in the cart,wish list and clear the address
 *  
 *  Search product using  banner.
* 
* Filter	 product by Brand
* 
* navigate to pdp page and add a product and Move to bag	
* 
* Place an Order by using Staggered Combo	
* 
* Edit address	
* 
* Continue payment through Manual GC +Online
 *
 */

public class VEGASF_131_RegisteredUser_FilterStyles_StaggeredCombo_PayWithCC extends GlobalVariables {
	// Only methods need to call
	// all class reference create here
	GenericMethods objGenericMethods;
	DriverInit objDriverInit;
	PLPPageObject objPLPPageObject;
	HeaderPageObject objHeaderPageObject;
	LoginPageObject objLoginPageObject;
	PDPObject objPDPObject;
	CartPageObject objCartPageObject;
	AddressPageObjects objAddressPageObjects;
	PaymentPageObjects objPaymentPageObjects;
	HomePageObject objHomePageObject;
	WishlistPageObjects objwishlistpageobjects;
	String ExcelPath;
	public static int CURRENT_ROW;
	public WebDriver driver;
	String testName = "VEGASF_FF_131";

	@Test(priority = 1)
	public void LoginInMyntra()  {
		System.out.println("=====================VEGASF_131_RegisteredUser_FilterStyles_StaggeredCombo_PayWithCC_START=====================");
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
		objCartPageObject.resetCart();
		objAddressPageObjects.resetAddress();
	//	objwishlistpageobjects.resetWishlist();
		
	}
	
	/**
	 * Modified by : Irfan Shariff
	 * Description : Added Thread.sleep(MIDSLEEP) before "Searching an Item". If this sleep not added search function fails.
	 */
	@Test(priority = 3)
	public void SearchByBanner()  {
		System.out.println("Search By Banner");
		//objHomePageObject.clickOnBannerOnHomePage();
	objHeaderPageObject.SearchInputBox(objGenericMethods.getValueByKey(testName, "SearchItem"));
//		objHeaderPageObject.VerifyAutoSuggestSearch();
		objHeaderPageObject.ClickOnSearchLens();
		//objPLPPageObject.VerifyProductDetails();
	}

//	@Test(priority = 4)
//	public void FilterByBrand()  {
//		System.out.println("Filter By Brand");
//		// objPLPPageObject.ClearFilter();
//		//objPLPPageObject.ClickOnCategoriesByName("Tshirts");
//		objPLPPageObject.verifySearchResultTittle();
//		objPLPPageObject.verifyCategoriesTitle();
//		objPLPPageObject.verifyColourTitle();
//		objPLPPageObject.verifyDiscountTitle();
//		objPLPPageObject.verifyPriceTitle();
//	}
//
//	@Test(priority = 5)
//	public void NavigateToPDP()  {
//		System.out.println("Navigate To PDP");
//		objPLPPageObject.ClickOnFirstProduct();
//		objPDPObject.verifyPDPTitleFromPDP();
//		objPDPObject.VerifySellingPriceFromPDP();
//		objPDPObject.VerifyLargeThumbnailFromPDP();
//		objPDPObject.VerifySmallThumbnailsFromPDP();
//		objPDPObject.VerifyBestOfferLinkFromPDP();
//	}

	@Test(priority = 6)
	public void AddToBag()  {
		System.out.println("Add To Bag");
		objPDPObject.VerifyBestOfferLinkFromPDP();
		objPDPObject.ClickSizeButtons();
		objPDPObject.ClickAddToBagBtn();
	}

	@Test(priority = 7)
	public void GoToCart()  {
		System.out.println("Go To Cart");
		objPDPObject.ClickGoToBagBtn();
		objCartPageObject.VerfiyProductIsAddedToCart();
		objCartPageObject.VerfiyTotalPrice();
	}

	@Test(priority = 8)
	public void PlaceOrder()  {
		System.out.println("Place Order");
		objCartPageObject.ClickPlaceOrder();
	}

	/**
	 * Modified by  :Anu
	 * Modified EditAddress()
	 */
	@Test(priority = 9)
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

	@Test(priority = 10)
	public void ContinueToPay()  {
		System.out.println("Continue To Pay");
		objAddressPageObjects.ClickToContinue();
		objPaymentPageObjects.VerifyOrderSumamry();
		objPaymentPageObjects.VerifyDeliveryAddress();
		//objPaymentPageObjects.SelectGiftCards();
//		objPaymentPageObjects.EnterGiftCardData(objGenericMethods.getValueByKey(testName, "GiftCardNumber"), 
//				objGenericMethods.getValueByKey(testName, "GiftCardPin"));
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
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		if (driver == null) {
			AssertJUnit.assertFalse(false);
		}
		objHeaderPageObject = new HeaderPageObject(driver);
		objGenericMethods = new GenericMethods(driver);
		objLoginPageObject = new LoginPageObject(driver);
		objPLPPageObject = new PLPPageObject(driver);
		objPDPObject = new PDPObject(driver);
		objCartPageObject = new CartPageObject(driver);
		objAddressPageObjects = new AddressPageObjects(driver);
		objPaymentPageObjects = new PaymentPageObjects(driver);
		objHomePageObject = new HomePageObject(driver);
		objwishlistpageobjects=new WishlistPageObjects(driver);
		// Open URL
		objGenericMethods.openURL(System.getProperty("url"));
	}

	@AfterTest
	public void afterTest() {
		// quit browser object
		// check condition before closing or quite
		driver.quit();
		System.out.println("=====================VEGASF_131_RegisteredUser_FilterStyles_StaggeredCombo_PayWithCC_END=======================");
	}
}