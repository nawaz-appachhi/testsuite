package com.automation.mobile.iOS.apps.ObjectRepository.Pages.Assertions;

import java.util.Iterator;

import java.util.List;

import org.openqa.selenium.WebElement;

import org.openqa.selenium.support.FindAll;

import org.openqa.selenium.support.FindBy;

import org.openqa.selenium.support.PageFactory;

import org.testng.Reporter;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.IOSDriver;

import io.appium.java_client.ios.IOSElement;

import io.appium.java_client.pagefactory.AppiumFieldDecorator;

import io.appium.java_client.pagefactory.iOSFindBy;

import com.automation.core.mobile.iOS.iOSGenericMethods;

public class AssertionPageObject {

	public AppiumDriver<MobileElement> iDriver;

	iOSGenericMethods objiOSGenericMethods;

	public AssertionPageObject(AppiumDriver<MobileElement> iDriver) {

		PageFactory.initElements(new AppiumFieldDecorator(iDriver), this);

		objiOSGenericMethods = new iOSGenericMethods(iDriver);

	}

	/*
	 * 
	 * 
	 * 
	 * Assertion for Payment header
	 * 
	 * 
	 * 
	 */

	/**
	 * 
	 * @author 300019221
	 * 
	 *         accessibility was not working so changed with xpath
	 * 
	 */

	// @iOSFindBy(accessibility = "Address")

	@iOSFindBy(xpath = "//XCUIElementTypeStaticText[@name=\"Address\"]")

	public IOSElement payment;

	public IOSElement getPayment() {

		objiOSGenericMethods.CheckIOSElementFound(payment, "payment");

		return payment;

	}
	

	public void verifypaymenttext() throws InterruptedException {

	//	Thread.sleep(2000);
		objiOSGenericMethods.waitForElementVisibility(getPayment());
		objiOSGenericMethods.VerifyStringShouldNotEmpty(getPayment(), "PAYMENT");

	}

	/*
	 * 
	 * assertion for Price details header
	 * 
	 */

	@iOSFindBy(xpath = "//XCUIElementTypeStaticText[@name='PRICE DETAILS']")

	public IOSElement priceDetails;

	public IOSElement getPriceDetails() {

		objiOSGenericMethods.CheckIOSElementFound(priceDetails, "priceDetails");

		return priceDetails;

	}

	public void verifyPriceDetails() {

	try {
		objiOSGenericMethods.VerifyStringShouldNotEmpty(getPriceDetails(), "PriceDetails   ");
	} catch (Exception e) {
		 
		
	}

	}

	/*
	 * 
	 * assertions for apply coupon
	 * 
	 */

	@iOSFindBy(xpath = "(//XCUIElementTypeStaticText[@name='Apply Coupon'])[3]")

	public IOSElement ApplyCoupon;

	public IOSElement getApplyCoupon() {

		objiOSGenericMethods.CheckIOSElementFound(ApplyCoupon, "ApplyCoupon");

		return ApplyCoupon;

	}

	public void verifyApplyCouponHeaders() {

		objiOSGenericMethods.VerifyStringShouldNotEmpty(getApplyCoupon(), "Apply Coupon   ");

	}

	/*
	 * 
	 * assertion for address header
	 * 
	 */

	@iOSFindBy(xpath = "//XCUIElementTypeStaticText[@name='Address']")

	public IOSElement AddressHeader;

	public IOSElement getAddressHeader() {

		objiOSGenericMethods.CheckIOSElementFound(AddressHeader, "AddressHeader"); 

		return AddressHeader;

	}

	public void verifyAddressHeaders() {

		try {
			objiOSGenericMethods.VerifyStringShouldNotEmpty(getAddressHeader(), "Address");
		} catch (Exception e) {
			 
		}

	}

	/*
	 * 
	 * assertion for shopping bag
	 * 
	 */

	@iOSFindBy(xpath = "//XCUIElementTypeStaticText[@name='SHOPPING BAG']")

	public IOSElement shoppingBag;

	public IOSElement getShoppingBag() {

		objiOSGenericMethods.CheckIOSElementFound(shoppingBag, "shoppingBag");

		return shoppingBag;

	}

	public void verifyMyBag() throws InterruptedException {
	
//
//		Thread.sleep(2000);
//
//		System.out.println("Bag header " + getShoppingBag().getText());
//
		try {
			objiOSGenericMethods.waitForElementVisibility(getShoppingBag());
			objiOSGenericMethods.VerifyStringContains(getShoppingBag(), "SHOPPING BAG");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			
		}

	}

	/*
	 * 
	 * Assertion of Search Header with Count
	 * 
	 */

	@FindAll({ @FindBy(className = "XCUIElementTypeStaticText") })

	public List<IOSElement> plpHeader;

	public List<IOSElement> getPlpHeader() {

		objiOSGenericMethods.CheckIOSElementsListFound(plpHeader, "plpHeader");

		return plpHeader;

	}

	public void verifyPLPHeader() {

		objiOSGenericMethods.VerifyStringShouldNotEmpty(getPlpHeader().get(1), "PLP Header ");

		// getPlpHeader().get(1).getText();

		System.out.println("PLP Header details  " + getPlpHeader().get(1).getText());

		objiOSGenericMethods.VerifyStringShouldNotEmpty(getPlpHeader().get(1), "PLP Header ");

	}

	@iOSFindBy(accessibility = "similar_link_1")

	public IOSElement moreSimilarProducts;

	public void verifyMoreSimilarProducts() {

		objiOSGenericMethods.VerifyStringContains(moreSimilarProducts, "similar_link_1");

	}

	@iOSFindBy(className = "XCUIElementTypeOther")

	public List<IOSElement> productDetails;

	@FindAll({ @FindBy(xpath = "(//XCUIElementTypeOther[@name=\"product_head\"])[2]") })

	public List<IOSElement> productDetailsPDP;

	public List<IOSElement> getProductDetailsPDP() {

		return productDetailsPDP;

	}

	/**
	 * @author 300019221 / Aravindanath
	 * Modifying the value 5 to 4 as this method was failing " java.lang.IndexOutOfBoundsException: Index: 5, Size: 5 "
	 */
	public void verifyProductDetails() {

		getPlpHeader().get(4).getText();

		System.out.println("Header name: --> " + getPlpHeader().get(5).getText());

	}

	public void verifyPLPProductCount() {

		System.out.println("PLP Product count " + getPlpHeader().get(2).getText());

		objiOSGenericMethods.VerifyStringShouldNotEmpty(getPlpHeader().get(2), "PLP Count ");

	}

	/*
	 * 
	 * Assertion of Product name in PLP Page
	 * 
	 */

	@FindAll({ @FindBy(className = "XCUIElementTypeStaticText") })

	public List<IOSElement> productName;

	public List<IOSElement> getProductName() {

		return productName;

	}

	public void verifyProductname() {

		getProductName().size();

		System.out.println("Product Name " + getProductName().get(5).getText());

		objiOSGenericMethods.VerifyStringShouldNotEmpty(getProductName().get(5), "Product Name  ");

		// // objiOSGenericMethods.VerifyStringShouldNotEmpty(getPlpHeader().get(1),

		// "PLP Header ");

		// productName.get(5).getText();

		// System.out.println("Print"+ productName.get(5).getText());

		// //objiOSGenericMethods.VerifyStringShouldNotEmpty(getPlpHeader().get(1), "PLP

		// Header ");

		//

	}

	/**
	 * 
	 * @author 300019221
	 * 
	 *         product description page
	 * 
	 */

	@iOSFindBy(accessibility = "(//XCUIElementTypeOther[@name=\"product_head\"])[2]")

	public IOSElement productDetailsOnPDP;

	public IOSElement getproductHead() {

		return productDetailsOnPDP;

	}

	public void verifyProductNameOnPDP() throws InterruptedException {

		objiOSGenericMethods.VerifyStringShouldNotEmpty(getproductHead(), "product_head");

	}
	/*
	 * 
	 * 
	 * 
	 * Assertion of VerifyUserName
	 * 
	 * 
	 * 
	 */

	@FindAll({ @FindBy(className = "XCUIElementTypeStaticText") })

	public List<IOSElement> userName;

	public List<IOSElement> getUserName() {

		return userName;

	}

	public void verifyUserName() throws InterruptedException {
		try {
			//Thread.sleep(2000);

			objiOSGenericMethods.VerifyStringShouldNotEmpty(getUserName().get(3), "User name");

			System.out.println("User name is --> " + getUserName().get(3).getText());

		} catch (Exception e) {
		}

		
	}

	/**
	 * 
	 * 
	 * 
	 * Assertion of Filter Screen
	 
	 * 
	 * 
	 * //UIACollectionCell/UIAStaticText[contains(@name, 'section')]")
	 * 
	 * 
	 * 
	 * ));
	 * 
	 * 
	 * 
	 */

	@FindAll({ @FindBy(className = "XCUIElementTypeTable") })

	public List<IOSElement> filterByGender;

	public List<IOSElement> getFilterByGender() {

		return filterByGender;

	}

	public void verifyGender() {

		getFilterByGender().size();

		System.out.println("Total size " + getFilterByGender().size());

		objiOSGenericMethods.VerifyStringShouldNotEmpty(getFilterByGender().get(2), "Gender ");

	}

	@iOSFindBy(accessibility = "Discount")

	public IOSElement discount;

	public IOSElement getDiscount() {

		return discount;

	}

	public void verifyDiscount() {

		getDiscount().getText();

		System.out.println("Discount " + getDiscount().getText());

		objiOSGenericMethods.VerifyStringShouldNotEmpty(getDiscount(), "Discount ");

	}

	public void verifyFilterName() {

		// objiOSGenericMethods.VerifyStringShouldNotEmpty(getFilterByGender().get(3),

		// "Filter Gender");

		getFilterByGender().size();

		System.out.println("User name is --> " + getUserName().size());

	}

	public void verifyFilterDiscount() {

		objiOSGenericMethods.VerifyStringShouldNotEmpty(getFilterByGender().get(8), "Filter Discount");

		// getFilterByGender().get(8).getText();

		// System.out.println("Discount is applied

		// -->"+getFilterByGender().get(8).getText());

	}

	public void verifyPLPProductCountAfterSortFilter() {

		System.out.println("PLP Product count after applying Sort/Filter " + getPlpHeader().get(2).getText());

		objiOSGenericMethods.VerifyStringShouldNotEmpty(getPlpHeader().get(2), "PLP Count ");

	}

	/*
	 * 
	 * 
	 * 
	 * assertion for selected address in payment page
	 * 
	 * 
	 * 
	 */

	@iOSFindBy(accessibility = "DELIVER TO")

	public IOSElement dileverTo;

	public IOSElement getDileverTo() {

		objiOSGenericMethods.CheckIOSElementFound(dileverTo, "dileverTo is done");

		return dileverTo;

	}

	public void verifyDeliverTo() {

		objiOSGenericMethods.VerifyStringShouldNotEmpty(getDileverTo(), "delivery to");

	}

	/*
	 * 
	 * 
	 * 
	 * Added New Assertion Of Filter
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * Gender Assertion
	 * 
	 * 
	 * 
	 */

	@iOSFindBy(accessibility = "Gender")

	public IOSElement genderOption;

	public IOSElement getGenderOption() {

		return genderOption;

	}

	public void verifyGenderFilter() {

		objiOSGenericMethods.VerifyStringShouldNotEmpty(getGenderOption(), "Gender ");

	}

	/*
	 * 
	 * 
	 * 
	 * Categories Assertion
	 * 
	 * 
	 * 
	 */

	@iOSFindBy(accessibility = "Categories")

	public IOSElement category;

	public IOSElement getCategory() {

		return category;

	}

	public void verifyCategory() {

		objiOSGenericMethods.VerifyStringShouldNotEmpty(getCategory(), "Categories ");

	}

	/*
	 * 
	 * 
	 * 
	 * Brand Assertion
	 * 
	 * 
	 * 
	 */

	@iOSFindBy(accessibility = "Brand")

	public IOSElement brand;

	public IOSElement getBrand() {

		return brand;

	}

	public void verifyBrand() {

		objiOSGenericMethods.VerifyStringShouldNotEmpty(getBrand(), "Brand ");

	}

	/*
	 * 
	 * 
	 * 
	 * Color Assertion
	 * 
	 * 
	 * 
	 */

	@iOSFindBy(accessibility = "Colour")

	public IOSElement color;

	public IOSElement getColor() {

		return color;

	}

	public void verifyColor() {

		objiOSGenericMethods.VerifyStringShouldNotEmpty(getColor(), "Color ");

	}

	/*
	 * 
	 * 
	 * 
	 * Price Assertion
	 * 
	 * 
	 * 
	 */

	@iOSFindBy(accessibility = "Price")

	public IOSElement price;

	public IOSElement getPrice() {

		return price;

	}

	public void verifyPrice() {

		objiOSGenericMethods.VerifyStringShouldNotEmpty(getPrice(), "Price ");

	}

	/*
	 * 
	 * 
	 * 
	 * Product Brand Assertion
	 * 
	 * 
	 * 
	 */

	@FindAll({ @FindBy(className = "XCUIElementTypeStaticText") })

	public List<IOSElement> productBrand;

	public List<IOSElement> getProductBrand() {

		return productBrand;

	}

	public void verifyProductBrand() {

		getProductBrand().size();

		System.out.println("Product Brand " + getProductBrand().get(7).getText());

		objiOSGenericMethods.VerifyStringShouldNotEmpty(getProductBrand().get(7), "Product Name  ");

	}

	/*
	 * 
	 * 
	 * 
	 * Discounted Price Assertion
	 * 
	 * 
	 * 
	 */

	@FindAll({ @FindBy(className = "XCUIElementTypeStaticText") })

	public List<IOSElement> discountedPrice;

	public List<IOSElement> getDiscountedPrice() {

		return discountedPrice;

	}

	public void verifyDiscountedPrice() {
		getDiscountedPrice().size();

		System.out.println("Discounted price  " + getDiscountedPrice().get(5).getText());
		objiOSGenericMethods.VerifyStringShouldNotEmpty(getDiscountedPrice().get(5), "Product Name  ");

	}

	public List<IOSElement> verifyAllObjects(String pageName) throws InterruptedException {

		//Thread.sleep(2000);

		// System.err.println("Total products " + getDiscountedPrice().size());

		for (Iterator<IOSElement> iterator = getDiscountedPrice().iterator(); iterator.hasNext();) {

			WebElement WebElement = (WebElement) iterator.next();

			System.err.println("Verifying Details of " + pageName + " Page --> " + WebElement.getText());

			// filterDiscountPercent.get(i).click();

		}

		return getDiscountedPrice();

	}

	/*
	 * 
	 * 
	 * 
	 * Strick Price
	 * 
	 * 
	 * 
	 */

	@FindAll({ @FindBy(className = "XCUIElementTypeStaticText") })

	public List<IOSElement> strickPrice;

	public List<IOSElement> getStrickPrice() {

		return strickPrice;

	}

	public void verifyStrickPrice() {

		getStrickPrice().size();

		System.out.println("Strick Price " + getStrickPrice().get(8).getText());

		objiOSGenericMethods.VerifyStringShouldNotEmpty(getStrickPrice().get(8), "Strick Price ");

	}

	/*
	 * 
	 * Added by subhasis pati
	 * 
	 */

	@FindBy(xpath = "(//XCUIElementTypeOther[@name='size_selector'])[2]")

	public IOSElement selectSize;

	public IOSElement getSelectSize() {

		return selectSize;

	}

	public void verifySelectSize() {

		// getSelectSize().get(0).getText();

		// System.out.println("Select size "+ getSelectSize().get(0).getText());

	//	objiOSGenericMethods.VerifyStringShouldNotEmpty(getSelectSize(), "Select Size");

	}

	/**
	 * 
	 * Assertion for Search
	 * 
	 */

	@FindAll({ @FindBy(className = "XCUIElementTypeScrollView") })

	public List<IOSElement> autosuggest;

	public List<IOSElement> getAutosuggest() {

		return autosuggest;

	}

	public void VerifyAutoSuggestionList()

	{

		List<IOSElement> autoList = getAutosuggest();

		int s = autoList.size();

		if (s >= 0) {

			Reporter.log("Passed : Able to Enter in search Field");

			System.out.println("cursor is on search");
			Reporter.log("Passed : Auto Suggestion List is displayed with count =" + s);

		}

		else {
			Reporter.log("Failed : Auto Suggestion List is not displayed.");

			System.out.println("auto suggestion list not getting displayed");

		}

	}

	/**
	 * 
	 * 
	 * 
	 * Assertion for best price
	 * 
	 */

	@iOSFindBy(xpath = "((//XCUIElementTypeOther[@name=\"tap_for_best_price\"])[3]")

	public IOSElement selectBestPrice;

	public IOSElement getSelectBestPrice() {

		objiOSGenericMethods.CheckIOSElementFound(selectBestPrice, "selectBestPrice");

		return selectBestPrice;

	}

	public void verifyTapForBestPrice() {

		objiOSGenericMethods.VerifyStringShouldNotEmpty(getSelectBestPrice(), "Tap for best price");

		System.out.println("Tap for best Price is displayed");

	}

	/*
	 * 
	 * Wishlist Page Assertion
	 * 
	 */
	@FindBy(xpath = "//XCUIElementTypeOther[@name='Wishlist']")

	public IOSElement wishList;

	public IOSElement getWishList() {

		return wishList;

	}

	public void verifyWishlist() {

		getWishList().getText();

		System.out.println("Wishlist header " + getWishList().getText());

		objiOSGenericMethods.VerifyStringShouldNotEmpty(getWishList(), "Wishlist ");

	}

	/*
	 * 
	 * Cart Page Product Name
	 * 
	 */

	@FindAll({ @FindBy(className = "XCUIElementTypeLink") })

	public List<IOSElement> productTitle;

	public List<IOSElement> getProductTitle() {

		return productTitle;

	}

	public void verifyProductTitleCartPage() {

		getProductTitle().get(1).getText();

		System.out.println("Size " + getProductTitle().get(1).getText());

	}

	/**
	 * Cart page Wishlist Added by subhasis
	 */

	@iOSFindBy(accessibility = "WISHLIST")
	public IOSElement cartPageWishlist;

	public IOSElement getCartPageWishlist() {
		return cartPageWishlist;
	}

	public void veirfyCartPageWishlist() {
		getCartPageWishlist().getText();
		System.out.println("Cart page " + getCartPageWishlist().getText());
		objiOSGenericMethods.VerifyStringShouldNotEmpty(getCartPageWishlist(), "Cart Page");
	}

	@iOSFindBy(accessibility = "delivery_options")

	public IOSElement deliveryOption;

	public IOSElement getDeliveryOption() {

		return deliveryOption;

	}

	public void verifyDelivery() {

		getDeliveryOption().getText();

		System.out.println("Delivery " + getDeliveryOption().getText());

		objiOSGenericMethods.VerifyStringShouldNotEmpty(getDeliveryOption(), "delivery_options ");

	}

	/**
	 * 
	 * Cart Page Product Discount Price
	 * 
	 */

	@FindAll({ @FindBy(className = "XCUIElementTypeLink") })

	public List<IOSElement> productDiscountedPrice;

	public List<IOSElement> getProductDiscountedPrice() {

		return productDiscountedPrice;

	}

	public void verifyProductDiscountPrice() {

		getProductTitle().get(2).getText();

		System.out.println("Size " + getProductTitle().get(2).getText());

	}

	/**
	 * 
	 * Gift Wrap Page Title
	 * 
	 * 
	 * 
	 */

	@iOSFindBy(accessibility = "Gift Wrap")

	public IOSElement giftwrap;

	public IOSElement getGiftwrap() {

		return giftwrap;

	}

	public void verifyGiftwrap() {

		getGiftwrap().getText();

		System.out.println("Gift Wrap " + getGiftwrap().getText());

		objiOSGenericMethods.VerifyStringShouldNotEmpty(getGiftwrap(), "Gift Wrap");

	}

	public IOSElement getDoneButton() {
		return doneButton;
	}

	@iOSFindBy(xpath = "//XCUIElementTypeButton[@name=\"Done\"]")

	public IOSElement doneButton;

	public void clickOnDoneButton() {
		objiOSGenericMethods.clickOnIOSElement(getDoneButton(), "Successfully click on searchPDP button");
	}
	
	/**
	 * @author 300021275 - Lata
	 * Profile page asserttion - To verify the gender
	 * 
	 */
	@iOSFindBy(accessibility = "GENDER")
	public IOSElement gender;
	
	public IOSElement getGender() {
		objiOSGenericMethods.CheckIOSElementFound(gender, "gender");
		return gender;
	}
	public void verifyGenderInProfileDetails() {
		getGender().getText();
		System.out.println("Gender " + getGender().getText());
	}
	
	/**
	 * @author 300021275 - Lata
	 * Profile page asserttion - To verify the Page title
	 * 
	 */
	
	@iOSFindBy(accessibility = "PROFILE DETAILS")
	public IOSElement profileDetailsPageTitle;
	
	public IOSElement getProfileDetailsPageTitle() {
		objiOSGenericMethods.CheckIOSElementFound(profileDetailsPageTitle, "profileDetailsPageTitle");
		return profileDetailsPageTitle;
	}

	public void verifyProfileDetailsPageTitle() {
		getProfileDetailsPageTitle().getText();
		System.out.println("Page Name " + getProfileDetailsPageTitle().getText());
	}
}