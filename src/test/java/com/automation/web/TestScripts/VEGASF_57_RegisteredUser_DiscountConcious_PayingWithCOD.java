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
 * @author 300020817 - Irfan_Shariff TEST STEPS 1. Login In Myntra (using
 *         Registered Email & Password). 2. Reset Wishlist, Cart & Saved
 *         Addresses. 3. Search any product from Golden Set. 4. Add that item to
 *         Wishlist. 5. Search another product from Golden Set. 6. Sort by
 *         Discount. 7. Select any product from PLP page. 8. Hover on all small
 *         thumbnails. 9. Select any Size. 10. Add to Bag. 11. Go to Bag. 12.
 *         Then from Bag go to Wishlist by clicking on "Add More From Wishlist"
 *         Link. 13. Click on Move To Bag button. 14. Select a Size. 15. Click
 *         on Done button. 16. Click on Cart icon. 17. Click on 'Place Order'
 *         button. 18. Click on View Details. 19. Add New Address. 20. Select
 *         COD option. 21. Quit Browser.
 */
public class VEGASF_57_RegisteredUser_DiscountConcious_PayingWithCOD extends GlobalVariables {

	// Only methods need to call
	// all class reference create here

	GenericMethods objGenericMethods;
	DriverInit objDriverInit;
	HeaderPageObject objHeaderPageObject;
	LoginPageObject objLoginPageObject;
	PLPPageObject objPLPPageObject;
	PDPObject objPDPObject;
	CartPageObject objCartPageObject;
	AddressPageObjects objAddressPageObject;
	PaymentPageObjects objPaymentPageObjects;
	WishlistPageObjects objWishlistPageObject;

	public static int CURRENT_ROW;

	public WebDriver driver;

	String testName = "VEGASF_FF_57";

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
		objAddressPageObject = new AddressPageObjects(driver);
		objPaymentPageObjects = new PaymentPageObjects(driver);
		objWishlistPageObject = new WishlistPageObjects(driver);

		// Open URL
		objGenericMethods.openURL(System.getProperty("url"));

	}

	@Test(priority = 1)
	public void LoginInMyntra()  {
		System.out.println("=====================VEGASF_57_RegisteredUser_DiscountConcious_PayingWithCOD_START=====================");
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
		objAddressPageObject.resetAddress();
		objCartPageObject.resetCart();
	}

	/**
	 * Modified by :Anu Added Assertions
	 */
	 @Test(priority = 3)
	 public void SearchItem()  {
	 System.out.println("Search Item");
	 objHeaderPageObject.SearchInputBox(objGenericMethods.getValueByKey(testName,
	 "SearchItem1"));
	//// objHeaderPageObject.VerifyAutoSuggestSearch();
     objHeaderPageObject.ClickOnSearchLens();
	// objPLPPageObject.verifySearchResultTittle();
	// objPLPPageObject.verifyCategoriesTitle();
	// objPLPPageObject.verifyColourTitle();
	// objPLPPageObject.verifyDiscountTitle();
	// objPLPPageObject.verifyPriceTitle();

	// objPLPPageObject.VerifyAllItemImage();
	// objPLPPageObject.VerifyAllItemBrand();
	// objPLPPageObject.VerifyAllItemName();
	// objPLPPageObject.VerifyAllItemOffer();

	// objPLPPageObject.verifyProductName(objGenericMethods.getValueByKey(testName,"productCode1"));
	// objPLPPageObject.verifyStrickedPrice(objGenericMethods.getValueByKey(testName,"productCode1"));
	// objPLPPageObject.verifyDiscountedPrice(objGenericMethods.getValueByKey(testName,"productCode1"));
	// objPLPPageObject.verifyDiscountPercentage(objGenericMethods.getValueByKey(testName,"productCode1"));
	 }

	// @Test(priority = 4)
	// public void PlpAddToWishlist()  {
	// System.out.println("Plp Add To Wishlist");
	// objPLPPageObject.SaveFirstProduct();
	// }
	//
	// @Test(priority = 5)
	// public void SearchSecondItem()  {
	// System.out.println("Search Second Item");
	//
	// objHeaderPageObject.SearchInputBox(objGenericMethods.getValueByKey(testName,
	// "SearchItem2"));
	// objHeaderPageObject.ClickOnSearchLens();
	//
	// }
	//
	// /**
	// * Modified by :Anu Added Assertions
	// */
	//
	// @Test(priority = 6)
	// public void PlpSortByDiscount()  {
	// System.out.println("Plp Sort By Discount");
	// objPLPPageObject.HowerOnSortByDropdown();
	// objPLPPageObject.ClickOnSortDropdownByDroddownValue("Discount");
	// objPLPPageObject.verifySearchResultTittle();
	// objPLPPageObject.verifyCategoriesTitle();
	// objPLPPageObject.verifyColourTitle();
	// objPLPPageObject.verifyDiscountTitle();
	// objPLPPageObject.verifyPriceTitle();
	// // objPLPPageObject.VerifyAllItemImage();
	// // objPLPPageObject.VerifyAllItemBrand();
	// // objPLPPageObject.VerifyAllItemName();
	// // objPLPPageObject.VerifyAllItemOffer();
	//
	// //
	// objPLPPageObject.verifyProductName(objGenericMethods.getValueByKey(testName,"productCode2"));
	// //
	// objPLPPageObject.verifyStrickedPrice(objGenericMethods.getValueByKey(testName,"productCode2"));
	// //
	// objPLPPageObject.verifyDiscountedPrice(objGenericMethods.getValueByKey(testName,"productCode2"));
	// //
	// objPLPPageObject.verifyDiscountPercentage(objGenericMethods.getValueByKey(testName,"productCode2"));
	// }
	//
	// /**
	// * Modified by :Anu Replaced 'getClickProductByProductCode()' method with
	// * 'ClickOnFirstProduct()'
	// */

	// @Test(priority = 7)
	// public void SelectAnyProduct()  {
	// System.out.println("Select Any Product");
	// objPLPPageObject.ClickOnFirstProduct();
	// }
	//
	// @Test(priority = 8)
	// public void VerificationInPDP()  {
	// System.out.println("Verify All Products Details From PDP");
	// objPDPObject.verifyPDPTitleFromPDP();
	// objPDPObject.VerifySellingPriceFromPDP();
	// // objPDPObject.VerifyActualPriceFromPDP();
	// // objPDPObject.VerifyDiscountPercentageFromPDP();
	// //
	// objPDPObject.VerifyProductCodefromPDP(objGenericMethods.getValueByKey(testName,"productCode"));
	// objPDPObject.VerifyLargeThumbnailFromPDP();
	// objPDPObject.VerifySmallThumbnailsFromPDP();
	// objPDPObject.VerifyBestOfferLinkFromPDP();
	// //
	// objPDPObject.VerifySellingPrice(objPLPPageObject.VerifyFirstItemSellingPrice());
	// objPDPObject.HoverAllSmallThumbnails();
	// }
	//
	// @Test(priority = 9)
	// public void SelectProductSize()  {
	// System.out.println("Select Product Size");
	// objPDPObject.ClickSizeButtons();
	// }
	//
	// @Test(priority = 10)
	// public void AddToBag()  {
	// System.out.println("Add To Bag");
	// objPDPObject.ClickAddToBagBtn();
	// }
	//
	// @Test(priority = 11)
	// public void GoToBag()  {
	// System.out.println("Go To Bag");
	// objPDPObject.ClickGoToBagBtn();
	// }
	//
	// @Test(priority = 12)
	// public void AddMoreFromWishlist()  {
	// System.out.println("Add More From Wishlist");
	// objCartPageObject.ClickToAddMoreFromWishlist();
	// }
	//
	// @Test(priority = 13)
	// public void MoveToBag()  {
	// System.out.println("Move To Bag");
	// objWishlistPageObject.VerifyWishlistTitle();
	// objWishlistPageObject.VerifyWishlistProductName();
	// objWishlistPageObject.ClickMoveToBagButton();
	// }
	//
	// @Test(priority = 14)
	// public void SizeButtons()  {
	// System.out.println("Size Buttons");
	// objWishlistPageObject.ClickSizeButtons();
	// }
	//
	// @Test(priority = 15)
	// public void DoneButton()  {
	// System.out.println("Done Button");
	// objWishlistPageObject.ClickDoneButton();
	// }
	//
	// @Test(priority = 16)
	// public void ClickOnCart()  {
	// System.out.println("Click On Cart");
	// objHeaderPageObject.ClickOnCart();
	// }

	@Test(priority = 6) // comment it when the wish list is working
	public void newMethodForPdp()  {
		objPDPObject.ClickSizeButtons();
		objPDPObject.ClickAddToBagBtn();
		objPDPObject.HoverAllSmallThumbnails();
		objPDPObject.verifyPDPTitleFromPDP();
		objPDPObject.VerifySellingPriceFromPDP();
		objPDPObject.VerifyLargeThumbnailFromPDP();
		objPDPObject.VerifySmallThumbnailsFromPDP();
	}

	@Test(priority = 7)
	public void PlaceOrder()  {
		System.out.println("Place Order");
		objPDPObject.ClickGoToBagBtn();
		objCartPageObject.VerfiyTotalPrice();
		objCartPageObject.VerfiyProductIsAddedToCart();
		objCartPageObject.ClickPlaceOrder();
	}

	@Test(priority = 8)
	public void ViewDetails()  {
		System.out.println("View Details");
		objPaymentPageObjects.ClickOnViewDetailLink();
	}

	/**
	 * Modified by :Anu Address details is reading from testdata.ini
	 */
	@Test(priority = 9)
	public void AddressPage()  {
		System.out.println("Address Page");
		objAddressPageObject.AddAddress(objGenericMethods.getValueByKey(testName, "Pincode"),
				objGenericMethods.getValueByKey(testName, "Name"), objGenericMethods.getValueByKey(testName, "Mobile"),
				objGenericMethods.getValueByKey(testName, "Address"),
				objGenericMethods.getValueByKey(testName, "Locality"));
		objAddressPageObject.SelectHomeAddressType();
		objAddressPageObject.clickToSaveAddress();
		objAddressPageObject.VerifyAddressAdded();
		objAddressPageObject.VerifyPriceDetails();
	}

	@Test(priority = 10)
	public void COD()  {
		System.out.println("COD");
		objAddressPageObject.ClickToContinue();
		objPaymentPageObjects.SelectCashOnDelivery();
		objPaymentPageObjects.handelAlert();
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
		// check condition before closing or quit
		driver.quit();
		System.out.println("=====================VEGASF_57_RegisteredUser_DiscountConcious_PayingWithCOD_END=======================");
	}
}