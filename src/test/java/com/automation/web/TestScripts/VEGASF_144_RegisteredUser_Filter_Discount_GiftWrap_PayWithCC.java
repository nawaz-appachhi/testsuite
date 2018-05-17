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
 * @author 300019227 - Anu
 *
 */
/**
 * TEST STEPS 
 * 1. Login(Email Registered user)
 * 2. Reset Cart, Wishlist and Address Pages
 * 3. Search (Search for an item)
 * 4. Filter by Discount from PLP
 * 5. Navigate to PDP, Select a size and Add the item to Bag 
 * 6. Navigate to Bag page, Send a Gift card and Place the order
 * 7. Add New Address for Delivery
 * 8. Payment through Netbanking
 */

public class VEGASF_144_RegisteredUser_Filter_Discount_GiftWrap_PayWithCC extends GlobalVariables {
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

	String testName = "VEGASF_FF_144";
	
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
		System.out.println("=====================VEGASF_144_RegisteredUser_Filter_Discount_GiftWrap_PayWithCC_START=====================");
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
		objWishlistPageObject.resetWishlist();
	}
	
	@Test(priority = 3)
	public void SearchItem()   {
		System.out.println("Search Item");
		objHeaderPageObject.SearchInputBox(objGenericMethods.getValueByKey(testName, "SearchItem"));
		//objHeaderPageObject.VerifyAutoSuggestSearch();
		objHeaderPageObject.ClickOnSearchLens();
		//		objPLPObject.VerifyProductDetails();
		//		objPLPObject.verifySearchResultTittle();
		////	    objPLPPageObject.verifyBrandTitle();
		//	    objPLPObject.verifyCategoriesTitle();
		objPLPObject.verifyPriceTitle();
		objPLPObject.verifyColourTitle();
		// objPLPObject.verifyDiscountTitle();
	}
	
	@Test(priority = 4)
	public void FilterByDiscount()   {
		System.out.println("Filter By Discount");
		if(System.getProperty("url").contains("sfqa")) {
	
			//objGenericMethods.FetchAllNameAfterSort(objPLPPageObject.getSearchResultList());
			objPLPObject.FilterByColour(objGenericMethods.getValueByKey(testName,"ColorName"));
			
		}
		else {
			objPLPObject.ClickmoreColors();
			objPLPObject.FilterByColour(objGenericMethods.getValueByKey(testName,"ColorName"));
			//objGenericMethods.FetchAllNameAfterSort(objPLPPageObject.getSearchResultList());
		}
	}
	
	@Test(priority = 5)
	public void AddToBagFromPDP()   {
		System.out.println("Add To Bag From PDP");
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
		//		objPDPObject.VerifySimilarProductsHeaderFromPDP();
		objPDPObject.ClickOnBestOfferLink();
		//objPDPObject.getSimilarProductsHeader().getText();
		//System.out.println("Verified Similar Products section");
		//objGenericMethods.scrollPageUp();
		objPDPObject.ClickSizeButtons();
		objPDPObject.ClickAddToBagBtn();
		objPDPObject.verifyPDPTitleFromPDP();
 		objPDPObject.VerifySellingPriceFromPDP();
 		objPDPObject.VerifyLargeThumbnailFromPDP();
 		objPDPObject.VerifySmallThumbnailsFromPDP();
 		objPDPObject.VerifyBestOfferLinkFromPDP();
		objPDPObject.ClickGoToBagBtn();
		objCartObject.VerfiyProductIsAddedToCart();
		objCartObject.VerfiyTotalPrice();
	}
	
	/**
	 * Modified by  :Anu
	 * Gift wrap test data is reading from testdata.ini
	 * @ 
	 */
	//	@Test(priority = 6)
	//	public void SendGiftCardFromCart()  {
	//		System.out.println("Send Gift Card From Cart");
	//		objCartObject.giftPackCheckbox();
	//		objCartObject.SendGiftCard(objGenericMethods.getValueByKey(testName, "RecipientName"), objGenericMethods.getValueByKey(testName, "Message"), objGenericMethods.getValueByKey(testName, "SenderName"));
	//	}
	@Test(priority = 7)
	public void PlaceOrderFromCart()   {
		System.out.println("Place Order From Cart");
		objCartObject.ClickPlaceOrder();
	}
	
	/**
	 * Modified by  :Anu
	 * Address details is reading from testdata.ini 
	 * @ 
	 */
	@Test(priority = 8)
	public void AddNewAddress()   {
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
	
	@Test(priority = 12)
	public void CompletePaymentUsingCreditOrDebiCard()   {
		System.out.println("Complete Payment Using Credit Or DebiCard");
		objAddressPageObjects.ClickToContinue();
		objPaymentPageObjects.VerifyOrderSumamry();
		objPaymentPageObjects.VerifyDeliveryAddress();
		objPaymentPageObjects.SelectCreditDebitCard();
		objPaymentPageObjects.CreditDebitPaymentComplete(objGenericMethods.getValueByKey(testName, "CardNumber"),
				objGenericMethods.getValueByKey(testName, "CardHolderName"), objGenericMethods.getValueByKey(testName, "CVVnumber"));
		objPaymentPageObjects.VerifyOrderNumber();
		objPaymentPageObjects.GoToOrderDetailsPage();
		objPaymentPageObjects.VerifyOrdernumber();
	}
	
	@AfterTest
	public void afterTest() {
		driver.quit();
		System.out.println("=====================VEGASF_144_RegisteredUser_Filter_Discount_GiftWrap_PayWithCC_END=======================");
	}
}
