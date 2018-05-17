package com.automation.web.TestScripts;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

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
 * @author 300019224- Aishu
 * 
 * Steps
 * LoginWithFacebook
 * HomePage
 * ListPageToPDP
 * CheckForTopButtons
 * CheckForShowSimilarProduct
 * AddToBag
 * ApplyPersonalizedCoupons
 * AddNewAddress_Office
 * PaymentWithCreditDebit_Card
 */
public class VEGASF_321_FB_User_CheckDeliveryOption_ShowSimilar_ApplyPersonalizedCoupons_AddNewAddress_PayWithCC {
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
	String testName = "VEGASF_FF_321";

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
//		System.out.println("=====================VEGASF_FF_321_START=====================");
//		System.out.println("Login In Myntra");
//		objGenericMethods.HoverOnWebElement(objHeaderPageObject.getUserIcon());
//		objHeaderPageObject.LoginUnderUserIcon();
//		objLoginPageObject.LoginWithFaceBook(objGenericMethods.getValueByKey(testName, "FbUserName"), objGenericMethods.getValueByKey(testName, "FbPassword"));
//		objLoginPageObject.VerifyUserEmailId();
//	}

	@Test(priority = 1)
	public void LoginInMyntra() {
		System.out.println("=====================VEGASF_321_FB_User_CheckDeliveryOption_ShowSimilar_ApplyPersonalizedCoupons_AddNewAddress_PayWithCC_START=====================");
		System.out.println("Login In Myntra");
		objGenericMethods.HoverOnWebElement(objHeaderPageObject.getUserIcon());
		objHeaderPageObject.LoginUnderUserIcon();
		objLoginPageObject.Login(objGenericMethods.getValueByKey(testName, "UserName"),
				objGenericMethods.getValueByKey(testName, "Password"));
		objLoginPageObject.LogInButtonClick();
		objLoginPageObject.VerifyUserEmailId();
	}

	
	@Test(priority = 2)
	public void HomePage()  {
		System.out.println("Reset All");
		objCartPageObject.resetCart();
		objAddressPageObjects.resetAddress();
		//objWishlistPageObjects.resetWishlist();
		System.out.println("Search Item");
		objHeaderPageObject.SearchInputBox(objGenericMethods.getValueByKey(testName, "SearchItem"));
		//		objHeaderPageObject.VerifyAutoSuggestSearch();
		objHeaderPageObject.ClickOnSearchLens();
	}

//	@Test(priority = 3)
//	public void ListPageToPDP()  {
//		System.out.println("Plp Page Assert Verification");
//		objPLPPageObject.verifySearchResultTittle();
//		objPLPPageObject.verifyCategoriesTitle();
//		objPLPPageObject.verifyColourTitle();
//		objPLPPageObject.verifyPriceTitle();
//	}
//	
//	@Test(priority = 4)
//	public void CheckForTopButtons()  {
//		objPLPPageObject.ShowMoreProductsLinkClick();
//		Reporter.log("More products link clicked");
//		objPLPPageObject.ClickTopButton();
//		objPLPPageObject.ClickOnFirstProduct();
//	}
//	
	@Test(priority = 5)
	public void CheckForShowSimilarProduct() {
//		objPDPObject.verifyPDPTitleFromPDP();
//		objPDPObject.VerifySellingPriceFromPDP();
//		objPDPObject.VerifyProductCodefromPDP("xyz");//String parameter is not getting utilized in calling function
//		objPDPObject.VerifyLargeThumbnailFromPDP();
//		objPDPObject.VerifySmallThumbnailsFromPDP();
		objPDPObject.VerifyBestOfferLinkFromPDP();
		objPDPObject.getBestOfferlink().click();
		System.out.println("Not available in SFQA environment");
//		//objPDPObject.VerifySimilarProductsHeaderFromPDP();//show similar is not available in sfqa
	}
//	
	@Test(priority = 6)
	public void AddToBag() {
		System.out.println("Check Delivery Location");
		objPDPObject.ClickCheckDeliveryOptionsLink();
		objPDPObject.EnterPincode(objGenericMethods.getValueByKey(testName, "Pincode"));
		objPDPObject.ClickCheckPinCodeLink();
		objPDPObject.VerifyCheckDelivaryOption();
		objGenericMethods.scrollDown(objPDPObject.getSizeButtons(), -200);
//		objPDPObject.ClickSizeButtons();
		objPDPObject.ClickAddToBagBtn();
	}
	
	@Test(priority = 7)
	public void ApplyGenericCoupons()  {
		objHeaderPageObject.ClickOnCartIcon();
		objCartPageObject.VerfiyTotalPrice();
		objCartPageObject.VerfiyProductIsAddedToCart();
		System.out.println("Apply Coupon");
		objCartPageObject.ClickApplyCouponBtn();
		objCartPageObject.ClickCancelButton();
		System.out.println("Place Order");
		objCartPageObject.ClickPlaceOrder();
	}
	
	@Test(priority = 8)
	public void AddNewAddress_Office()  {
		objAddressPageObjects.AddAddress(objGenericMethods.getValueByKey(testName, "Pincode"),
				objGenericMethods.getValueByKey(testName, "Name"), objGenericMethods.getValueByKey(testName, "Mobile"),
				objGenericMethods.getValueByKey(testName, "Address"),
				objGenericMethods.getValueByKey(testName, "Locality"));
		objAddressPageObjects.SelectOfficeAddressType();
		objAddressPageObjects.clickToSaveAddress();
	}

	@Test(priority = 9)
	public void PaymentWithCreditDebit_Card()  {
		System.out.println("Complete Payment");
		objAddressPageObjects.ClickToContinue();
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

	@AfterTest
	public void afterTest() {
		// quit browser object
		// check condition before closing or quite
		driver.quit();
		System.out.println("=====================VEGASF_321_FB_User_CheckDeliveryOption_ShowSimilar_ApplyPersonalizedCoupons_AddNewAddress_PayWithCC_END=======================");
	}
}
