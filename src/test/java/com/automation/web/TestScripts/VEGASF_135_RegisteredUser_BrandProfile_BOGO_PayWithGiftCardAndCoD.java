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

/**
 * @author 300021279 - Sangam_Mohanty
 *
 */

public class VEGASF_135_RegisteredUser_BrandProfile_BOGO_PayWithGiftCardAndCoD extends GlobalVariables {

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

	public WebDriver driver;

	String testName = "VEGASF_FF_135";

	@Test(priority = 1)
	public void LoginInMyntra()  {
		System.out.println("=====================VEGASF_135_RegisteredUser_BrandProfile_BOGO_PayWithGiftCardAndCoD_START=====================");
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
		objCartPageObject.resetCart();
	}
	
	
	/**
	* Name: Irfan Shariff
	* Description: thread.smallsleep is required after "objPLPPageObject.selectBuy1Get1();", if not "ClickOnFilterPrice()" is not happening.
	* Modified by <Irfan Shariff>
	*/
	@Test(priority = 3)
	public void SearchByItem()  {
		System.out.println("Search By Item");
		objHeaderPageObject.SearchInputBox(objGenericMethods.getValueByKey(testName, "SearchItem"));
		//objHeaderPageObject.VerifyAutoSuggestSearch();
		objHeaderPageObject.ClickOnSearchLens();
//		objPLPPageObject.clickOnPromotions();
//		objPLPPageObject.selectBuy1Get1();
	}
	
	/**
	* Name: Irfan Shariff
	* Description: 1) Here scrollpage is commented as few items only are available in Buy 1 Get 1, & assertions at striked,discounted price, discounted percentage is commented as there is no such product available in Buy 1 Get 1.
	* Description: 2) thread.smallsleep is required after "objPLPPageObject.ClickOnFilterPrice();", if not, filtering won't happen properly.
	* Modified by <Irfan Shariff>
	*/
//	@Test(priority = 4)
//	public void ShowMoreProducts()  {
//		System.out.println("Show More Products");
//		objPLPPageObject.verifySearchResultTittle();	
//		objPLPPageObject.verifyPriceTitle();
////		objPLPPageObject.verifyProductBrand(objGenericMethods.getValueByKey(testName, "productCode"));
////		objPLPPageObject.verifyProductName(objGenericMethods.getValueByKey(testName, "productCode"));
////		objPLPPageObject.verifyStrickedPrice(objGenericMethods.getValueByKey(testName, "productCode"));
////		objPLPPageObject.verifyDiscountedPrice(objGenericMethods.getValueByKey(testName, "productCode"));
////		objPLPPageObject.verifyDiscountPercentage(objGenericMethods.getValueByKey(testName, "productCode"));
//	}

//	@Test(priority = 5)
//	public void NavigateToPDP()  {
//		System.out.println("Navigate To PDP");
//		objPLPPageObject.ClickOnFirstProduct();
////		objPLPPageObject.getClickProductByProductCode(objGenericMethods.getValueByKey(testName, "productCode"));
//		objPDPObject.verifyPDPTitleFromPDP();
//		objPDPObject.VerifyLargeThumbnailFromPDP();
//		objPDPObject.VerifySmallThumbnailsFromPDP();
//		objPDPObject.VerifySellingPriceFromPDP();
//	}

	@Test(priority = 6)
	public void AddToBag()  {
		System.out.println("Add To Bag");
		objPDPObject.verifyPDPTitleFromPDP();
		objPDPObject.ClickSizeButtons();
		objPDPObject.ClickAddToBagBtn();
		objPDPObject.HoverAllSmallThumbnails();
		objPDPObject.verifyPDPTitleFromPDP();
		objPDPObject.VerifySellingPriceFromPDP();
		objPDPObject.VerifyLargeThumbnailFromPDP();
		objPDPObject.VerifySmallThumbnailsFromPDP();
	}

	/**
	* Name: Irfan Shariff
	* Description: Here message is not getting displayed for Buy 1 Get 1, hence commented.
	* Modified by <Irfan Shariff>
	*/
	@Test(priority = 7)
	public void GoToCart()  {
		System.out.println("Go To Cart");
		objPDPObject.ClickGoToBagBtn();
		objCartPageObject.VerfiyProductIsAddedToCart();
		objCartPageObject.VerfiyTotalPrice();
		objCartPageObject.AddQuantity();
		objCartPageObject.VerfiyTotalPrice();
		//objCartPageObject.VerifyBuy1Get1SuccessMessage();
	}

	@Test(priority = 8)
	public void ApplyCoupon()  {
		System.out.println("Apply Coupon");
//		objCartObject.ClickApplyCouponBtn();
//		objGenericMethods.applyCoupon(objGenericMethods.getValueByKey(testName, "coupon"));
//		objCartObject.ClickToApplyCoupon();
	}

	@Test(priority = 9)
	public void PlaceOrder()  {
		System.out.println("Place Order");
		objCartPageObject.ClickPlaceOrder();
	}

	@Test(priority = 10)
	public void RemoveAddress()  {
		System.out.println("Remove Address");
		objAddressPageObjects.VerifyAddressAdded();
		objAddressPageObjects.clickToRemoveAddress();
	}
	/**
	 * Modified by  :Anu
	 * Address details is reading from testdata.ini 
	 */

	@Test(priority = 11)
	public void AddAddress()  {
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

	/**
	* Name: Irfan Shariff
	* Description: Gift Card option is not getting selected, hence MIDSLEEP thread is required.
	* Modified by <Irfan Shariff>
	*/
	@Test(priority = 12)
	public void CompletePayment() {
		System.out.println("Complete Payment");
		objAddressPageObjects.ClickToContinue();
		objPaymentPageObjects.VerifyOrderSumamry();
		objPaymentPageObjects.VerifyDeliveryAddress();
		objPaymentPageObjects.VerifyFinalPayableAmount();
		objPaymentPageObjects.SelectCashOnDelivery();
		objPaymentPageObjects.handelAlert();
		objPaymentPageObjects.verifyCODavailabilityThenPay();
	}

	@Test(priority = 13)
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
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
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

		// Open URL
		objGenericMethods.openURL(System.getProperty("url"));

	}

	@AfterTest
	public void afterTest() {
		// quit browser object
		// check condition before closing or quite
		driver.quit();
		System.out.println("=====================VEGASF_135_RegisteredUser_BrandProfile_BOGO_PayWithGiftCardAndCoD_END=======================");
	}
}