package com.automation.web.TestScripts;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
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
 * * TEST STEPS
* 1. Login(Email registered user)
* 2. Reset Cart, Wishlist and Address Pages
* 3. Search (using menu item e.g. Men -> Topwear - T-Shirts)
* 4. Sort and Filter from PLP
* 5. Navigate to PDP, Select a size and add the product to Bag
* 6. Place the order from Bag page
* 7. Add New Address for Delivery
* 8. Payment through Credit/Debit Card
*/
public class VEGASF_143_RegisteredUser_BrowseMenu_SelectSize_ChangeSizeAtCheckout_PayWithCC extends GlobalVariables {
    GenericMethods objGenericMethods;
    DriverInit objDriverInit;
    PLPPageObject objPLPPageObject;
    HeaderPageObject objHeaderPageObject;
    LoginPageObject objLoginPageObject;
    PDPObject objPDPObject;
    CartPageObject objCartPageObject;
    AddressPageObjects objAddressPageObjects;
    PaymentPageObjects objPaymentPageObjects;
    WishlistPageObjects objWishlistPageObject;
    String ExcelPath;
    public static int CURRENT_ROW;

    public WebDriver driver;

    String testName = "VEGASF_FF_143";

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
        objAddressPageObjects = new AddressPageObjects(driver);
        objPaymentPageObjects = new PaymentPageObjects(driver);
        objWishlistPageObject = new WishlistPageObjects(driver);

        // Open URL
        objGenericMethods.openURL(System.getProperty("url"));
    }
    
    @Test(priority = 1)
    public void LoginInMyntra()  {
        System.out.println("=====================VEGASF_143_RegisteredUser_BrowseMenu_SortPrice_Discount_BOGO_PayWithCoD_START=====================");
        System.out.println("Login In Myntra");
        objGenericMethods.HoverOnWebElement(objHeaderPageObject.getUserIcon());
        objHeaderPageObject.LoginUnderUserIcon();
        objLoginPageObject.readSession("BoforeLogin");
        objLoginPageObject.Login(objGenericMethods.getValueByKey(testName, "UserName"),objGenericMethods.getValueByKey(testName, "Password"));
        objLoginPageObject.LogInButtonClick();
        objLoginPageObject.VerifyUserEmailId();
        objLoginPageObject.readSession("AfterLogin");
    }

    @Test(priority = 2)
    public void ResetAll()  {
        System.out.println("Reset All");
        objCartPageObject.resetCart();
        objAddressPageObjects.resetAddress();
        objWishlistPageObject.resetWishlist();
    }

    /**
     * @
     * @author 300019224 Aishurya
     */
    @Test(priority = 3)
    public void SearchItem()  {
        System.out.println("Search Item");
        objHeaderPageObject.SearchInputBox(objGenericMethods.getValueByKey(testName,"SearchItem"));
////        objHeaderPageObject.VerifyAutoSuggestSearch();
          objHeaderPageObject.ClickOnSearchLens();
//        objPLPPageObject.VerifyProductDetails(); 
//        objPLPPageObject.verifySearchResultTittle();
////      objPLPPageObject.verifyBrandTitle();
//  		objPLPPageObject.verifyCategoriesTitle();
//  		objPLPPageObject.verifyPriceTitle();
//		objPLPPageObject.verifyColourTitle();
//		objPLPPageObject.verifyDiscountTitle();
//      objGenericMethods.HoverOnWebElement(objHeaderPageObject.getHeaderMenu("Men"));
//      objHeaderPageObject.clickOnSubMenuUnderMen("T-Shirts");        
    }

    @Test(priority = 4)
    public void SortAndFilter()  {
        System.out.println("Sort And Filter");
       // objGenericMethods.FetchAllNameBeforeSort(objPLPPageObject.getAllProductName());
        if(System.getProperty("url").contains("sfqa")) {
			objPLPPageObject.HowerOnSortByDropdown();
			objPLPPageObject.ClickOnSortDropdownByDroddownValue("Popularity");
			//objGenericMethods.FetchAllNameAfterSort(objPLPPageObject.getSearchResultList());
			objPLPPageObject.FilterByColour(objGenericMethods.getValueByKey(testName,"ColorName"));
			
		}
		else {
			objPLPPageObject.HowerOnSortByDropdown();
			objPLPPageObject.ClickOnSortDropdownByDroddownValue("Popularity");
			objPLPPageObject.ClickmoreColors();
			objPLPPageObject.FilterByColour(objGenericMethods.getValueByKey(testName,"ColorName"));
			//objGenericMethods.FetchAllNameAfterSort(objPLPPageObject.getSearchResultList());
		}
//        objGenericMethods.FetchAllNameAfterSort(objPLPPageObject.getAllProductName());
//        objGenericMethods.verifySortByEffectiveness();
//        objPLPPageObject.VerifyProductDetails();
//        objPLPPageObject.verifySearchResultTittle();
//        objPLPPageObject.verifyBrandTitle();
      //  objPLPPageObject.verifyCategoriesTitle();
        objPLPPageObject.verifyColourTitle();
    //    objPLPPageObject.verifyDiscountTitle();
        objPLPPageObject.verifyPriceTitle();
//        objPLPPageObject.VerifyAllItemBrand();
//        objPLPPageObject.VerifyAllItemImage();
//        objPLPPageObject.VerifyAllItemName();
//        objPLPPageObject.VerifyAllItemOffer();
//        objPLPPageObject.ClickOnFirstProduct();
        {
//          System.out.println("Navigate from PLP to PDP");
      	try {
      		objPLPPageObject.FilterByColour("NOCOLOR");
  		} catch (Exception e) {
  		}
      	 objPLPPageObject.getClickProductByProductCode(objGenericMethods.getValueByKey(testName,"productCode"));
        }
    }

    @Test(priority = 5)
    public void AddToBagFromPDP()  {
        System.out.println("Add To Bag From PDP");
//        objPDPObject.VerifyProductCodefromPDP(objGenericMethods.getValueByKey(testName, "productCode"));      
       // objPDPObject.verifyPDPTitleFromPDP();
    //    objPDPObject.VerifySimilarProductsHeaderFromPDP();
        objPDPObject.ClickSizeButtons();
        objPDPObject.ClickAddToBagBtn();
        objPDPObject.verifyPDPTitleFromPDP();
 		objPDPObject.VerifySellingPriceFromPDP();
 		objPDPObject.VerifyLargeThumbnailFromPDP();
 		objPDPObject.VerifySmallThumbnailsFromPDP();
 		objPDPObject.VerifyBestOfferLinkFromPDP();
        objPDPObject.ClickGoToBagBtn();
        objCartPageObject.VerfiyTotalPrice();
        objCartPageObject.VerfiyProductIsAddedToCart();
    }
    
    /**
	 * Modified by  :Anu
	 * Gift wrap test data is reading from testdata.ini
	 */
    @Test(priority = 6)
    public void PlaceOrderFromCart()  {
        System.out.println("Place Order From Cart");
//        objCartPageObject.giftPackCheckbox();
//        objCartPageObject.SendGiftCard(objGenericMethods.getValueByKey(testName, "RecipientName"), objGenericMethods.getValueByKey(testName, "Message"), objGenericMethods.getValueByKey(testName, "SenderName"));
          objCartPageObject.ClickPlaceOrder();
    }
    /**
	 * Modified by  :Anu
	 * Address details is reading from testdata.ini 
	 */
    @Test(priority = 7)
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

    @Test(priority = 8)
    public void PaymentThroughCredit_DebitCards()  {
        System.out.println("Payment Through Credit/Debit Card");
        objAddressPageObjects.ClickToContinue();
        objPaymentPageObjects.VerifyOrderSumamry();
        objPaymentPageObjects.VerifyDeliveryAddress();
        objPaymentPageObjects.SelectCreditDebitCard();
        objPaymentPageObjects.CreditDebitPaymentComplete(objGenericMethods.getValueByKey(testName, "CardNumber"),
				objGenericMethods.getValueByKey(testName, "CardHolderName"), objGenericMethods.getValueByKey(testName, "CVVnumber"));
        objPaymentPageObjects.handelAlert();
        objPaymentPageObjects.VerifyOrderNumber();
		objPaymentPageObjects.GoToOrderDetailsPage();
		objPaymentPageObjects.VerifyOrdernumber();
    }
    @Test(priority = 9)
	public void LogOut()  {
		objLoginPageObject.LogOut();
		objLoginPageObject.readSession("AfterLogout");
	}

    @AfterTest
    public void afterTest() {
        driver.quit();
        System.out.println("=====================VEGASF_143_RegisteredUser_BrowseMenu_SelectSize_ChangeSizeAtCheckout_PayWithCC_END=======================");
    }
}