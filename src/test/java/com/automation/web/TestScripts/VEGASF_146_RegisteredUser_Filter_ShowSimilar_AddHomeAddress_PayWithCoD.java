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
import com.automation.web.ObjectRepository.Pages.AddressPage.AddressPageObjects;
import com.automation.web.ObjectRepository.Pages.Cart.CartPageObject;
import com.automation.web.ObjectRepository.Pages.Header.HeaderPageObject;
import com.automation.web.ObjectRepository.Pages.Login.LoginPageObject;
import com.automation.web.ObjectRepository.Pages.PDP.PDPObject;
import com.automation.web.ObjectRepository.Pages.PLP.PLPPageObject;
import com.automation.web.ObjectRepository.Pages.PaymentPage.PaymentPageObjects;
import com.automation.web.ObjectRepository.Pages.WishlistPage.WishlistPageObjects;

/**
 * @author 300019227-Anu
 *
 */

/**
 * TEST STEPS 
 * 1. Login(Email Registered user)
 * 2. Reset Cart, Wishlist and Address Pages
 * 3. Search (Search for an item)
 * 4. Sort and Filter from PLP
 * 5. Navigate to PDP, Verify 'Click for Best Price link'
 * 6. Save the item to Wishlist, Select a size and add the item to Bag
 * 7. Navigate to Cart page, Place the order
 * 8. Add new Address for Delivery
 * 9. Payment through COD
 *
 */

public class VEGASF_146_RegisteredUser_Filter_ShowSimilar_AddHomeAddress_PayWithCoD extends GlobalVariables {
	GenericMethods objGenericMethods;
	DriverInit objDriverInit;
	HeaderPageObject objHeaderPageObject;
	LoginPageObject objLoginPageObject;
	PLPPageObject objPLPObject;
	PDPObject objPDPObject;
	CartPageObject objCartObject;
	AddressPageObjects objAddressPageObjects;
	PaymentPageObjects objPaymentPageObjects;
	WishlistPageObjects objWishlistPageObject;
	public WebElement eleProductSellingdPrice;
	public WebElement eleProductActualPrice;
	public WebElement eleDiscountPercentage;
	public WebDriver driver;
	
	String testName = "VEGASF_FF_146";
	
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
		objPLPObject = new PLPPageObject(driver);
		objPDPObject = new PDPObject(driver);
		objCartObject = new CartPageObject(driver);
		objAddressPageObjects = new AddressPageObjects(driver);
		objPaymentPageObjects = new PaymentPageObjects(driver);
		objWishlistPageObject = new WishlistPageObjects(driver);
		// Open URL
		objGenericMethods.openURL(System.getProperty("url"));
	}
	
	@Test(priority = 1)
	public void LoginInMyntra()  {
		System.out.println("=====================VEGASF_146_RegisteredUser_Filter_ShowSimilar_AddHomeAddress_PayWithCoD_START=====================");
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
		objCartObject.resetCart();
		objAddressPageObjects.resetAddress();
		//objWishlistPageObject.resetWishlist();
	}
	
	@Test(priority = 3)
	public void SearchItem()  {
		System.out.println("Search Item");
		objHeaderPageObject.SearchInputBox(objGenericMethods.getValueByKey(testName, "SearchItem"));
		//objHeaderPageObject.VerifyAutoSuggestSearch();
		objHeaderPageObject.ClickOnSearchLens();
		//objPLPObject.VerifyProductDetails(); 
	}
	
	@Test(priority = 4)
	public void SortAndFilter()  {
		System.out.println("Sort And Filter");
		//	objGenericMethods.FetchAllNameBeforeSort(objPLPObject.AllProductName);
		// Uncomment below 4 lines when sfqa is up and running
		if(System.getProperty("url").contains("sfqa")) {
			objPLPObject.HowerOnSortByDropdown();
			objPLPObject.ClickOnSortDropdownByDroddownValue("Popularity");
			//objGenericMethods.FetchAllNameAfterSort(objPLPObject.getSearchResultList());
			objPLPObject.FilterByColour(objGenericMethods.getValueByKey(testName,"ColorName"));
			
		}
		else {
			objPLPObject.HowerOnSortByDropdown();
			objPLPObject.ClickOnSortDropdownByDroddownValue("Popularity");
			objPLPObject.ClickmoreColors();
			objPLPObject.FilterByColour(objGenericMethods.getValueByKey(testName,"ColorName"));
			//objGenericMethods.FetchAllNameAfterSort(objPLPObject.getSearchResultList());
		}
		//		objPLPObject.VerifyProductDetails(); 
		//        objPLPObject.verifySearchResultTittle();
		//      objPLPObject.verifyBrandTitle();
		//objPLPObject.verifyCategoriesTitle();
		objPLPObject.verifyPriceTitle();
		objPLPObject.verifyColourTitle();
		//	objPLPObject.verifyDiscountTitle();
	}
	
	@Test(priority = 5)
	public void NavigateToPDP()  {
		System.out.println("Navigate To PDP");
		try {
			objPLPObject.FilterByColour("NOCOLOR");
  		} catch (Exception e) {
  		}
		objPLPObject.getClickProductByProductCode(objGenericMethods.getValueByKey(testName,"productCode"));
		//		objPDPObject.VerifySellingPriceFromPDP();
		//		objPDPObject.verifyPDPTitleFromPDP();
		//		objPDPObject.VerifyLargeThumbnailFromPDP();
		//		objPDPObject.VerifySmallThumbnailsFromPDP();
		objPDPObject.VerifyBestOfferLinkFromPDP();
		objPDPObject.ClickOnBestOfferLink();
		objGenericMethods.scrollPageUp();
	}
	
	@Test(priority = 6)//uncomment it when the wish list is working in mjint environment
	public void PDPToWishlist()  {
		System.out.println("PDP To Wishlist");
		objPDPObject.ClickOnSaveBtn();
		objGenericMethods.HoverOnWebElement(objHeaderPageObject.getUserIcon());
		objHeaderPageObject.ClickonWishlistLink();
		objWishlistPageObject.VerifyWishlistTitle();
		objWishlistPageObject.VerifyWishlistProductName();
		objWishlistPageObject.VerifySellingPrice();
		objWishlistPageObject.ClickMoveToBagButton();
		objWishlistPageObject.ClickSizeButtons();
		objWishlistPageObject.ClickDoneButtons();
	}
	
	//	@Test(priority = 6)//comment it when the wish list is working
	//	public void newMethodForPdp() {
	//		 objPDPObject.ClickSizeButtons();
	//	        objPDPObject.ClickAddToBagBtn();
	//	        objPDPObject.ClickGoToBagBtn();
	//		objCartObject.VerfiyProductIsAddedToCart();
	//		objCartObject.VerfiyTotalPrice();
	//	}
	
	@Test(priority = 7)
	public void PlaceOrderFromCart()  {
		System.out.println("Place Order From Cart");
		objHeaderPageObject.ClickOnCartIcon();//uncomment it when the wish list is working in mjint environment
		objCartObject.VerfiyProductIsAddedToCart();
		objCartObject.VerfiyTotalPrice();
		objCartObject.ClickPlaceOrder();
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
		objAddressPageObjects.getOfficeRadioButton();
		objAddressPageObjects.SelectOfficeAddressType();
		objAddressPageObjects.clickToSaveAddress();
		objAddressPageObjects.VerifyAddressAdded();
		objAddressPageObjects.VerifyPriceDetails();
	}
	
	@Test(priority = 9)
	public void CompletePayment_COD()  {
		System.out.println("Comple Payement COD");
		objAddressPageObjects.ClickToContinue();
		objPaymentPageObjects.VerifyOrderSumamry();
		objPaymentPageObjects.VerifyDeliveryAddress();
		
		objPaymentPageObjects.SelectCashOnDelivery();
		objPaymentPageObjects.ClickCODPayOnDeliveryBtn();
		objPaymentPageObjects.VerifyCODOrderConfirmedTxt();
		objPaymentPageObjects.VerifyOrderNumber();
		objPaymentPageObjects.GoToOrderDetailsPage();
		objPaymentPageObjects.VerifyOrdernumber();
	}
	
	@AfterTest
	public void afterTest() {
		driver.quit();
		System.out.println("=====================VEGASF_146_RegisteredUser_Filter_ShowSimilar_AddHomeAddress_PayWithCoD_END=======================");
	}
}
