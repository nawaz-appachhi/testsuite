package com.automation.mobile.Android.MobileWeb.ObjectRepository.Assertions;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;
import com.automation.core.mobile.Android.AndroidGenericMethods;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class AssertionPageObject {

	AndroidGenericMethods objAndroidGenericMethods;

	public AssertionPageObject(AndroidDriver<AndroidElement> aDriver) {
		PageFactory.initElements(new AppiumFieldDecorator(aDriver), this);
		objAndroidGenericMethods = new AndroidGenericMethods(aDriver);
	}

	// ***********************HAMBURGERPAGEOBJECT***************************//
	@FindBy(xpath = "//div[@class='mobile']/h4")
	public AndroidElement verifyUsermailID;

	public AndroidElement getVerifyUsermailID() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(verifyUsermailID, "verifyUsermailID");
		return verifyUsermailID;
	}

	/**
	 * this is to verify the user id
	 */
	public void assertUserEmailID(String namemailID) throws InterruptedException {
		AndroidElement element = getVerifyUsermailID();
		objAndroidGenericMethods.VerifyTwoString(element, namemailID);
	}
	// ******************************PRODUCT LIST PAGE**************************//

	@FindBy(xpath = "//div[@class='page searchResults']/h1")
	public AndroidElement searchResultHeader;

	public AndroidElement getSearchResultHeader() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(searchResultHeader, "searchResultHeader");
		return searchResultHeader;
	}

	public void verifySearchResult(String searchText) throws InterruptedException {
		AndroidElement element = (AndroidElement) getSearchResultHeader();
		objAndroidGenericMethods.VerifyStringShouldNotEmpty(element, searchText);
	}
	// ************************PRODUCTLISTPAGE*************************************//

	@FindBy(xpath = "//div[@class='content-wrap']")
	public List<AndroidElement> productDescription;

	public List<AndroidElement> getProductDescription() {
		objAndroidGenericMethods.CheckAndroidElementsListFoundMWeb(productDescription, "productDescription");
		return productDescription;
	}

	/**
	 * objPLPageObjects
	 */
	public void VerifyProductDetails() {
		List<AndroidElement> productDescriptionList = getProductDescription();
		int s = productDescriptionList.size();

		if (s >= 0) {
			Reporter.log("Passed : Products are displayed in PLP Page with count =" + s);
		} else {
			Reporter.log("Failed : Products are not displayed in PLP Page ");
		}
		int i = 1;
		for (AndroidElement product : productDescriptionList) {
			AndroidElement productTitle = (AndroidElement) product
					.findElement(By.xpath("(//div[@class='content-wrap']/div/div[1])[" + (i) + "]"));
			AndroidElement productStrikedPrice = (AndroidElement) product
					.findElement(By.xpath("(//div[@class='price-container']/span[2])[" + (i) + "]"));
			AndroidElement productPercentageDiscount = (AndroidElement) product
					.findElement(By.xpath("(//span[@class='disc-percent'])[" + (i) + "]"));
			objAndroidGenericMethods.VerifyStringShouldNotEmpty(productTitle, productTitle.getText());
			objAndroidGenericMethods.VerifyStringShouldNotEmpty(productStrikedPrice, productStrikedPrice.getText());
			objAndroidGenericMethods.VerifyStringShouldNotEmpty(productPercentageDiscount,
					productPercentageDiscount.getText());
			i++;
		}

	}

	// ***************************ProductDescription**********************************//

	@FindBy(className = "product-title")
	public AndroidElement productTitle;

	public AndroidElement getProductTitle() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(productTitle, "productTitle");
		return productTitle;
	}

	public void VerifyProductTitle() {
		objAndroidGenericMethods.VerifyStringShouldNotEmpty(getProductTitle(), "Product Title");
	}

	// ***************************ProductDescription**********************************//

	@FindAll({ @FindBy(xpath = "//div[starts-with(@class,'dot')]") })
	public List<AndroidElement> imageVerification;

	public List<AndroidElement> getImageVerification() {
		objAndroidGenericMethods.CheckAndroidElementsListFoundMWeb(imageVerification, "imageVerification");
		return imageVerification;
	}

	public void imageVerification() {
		int sizeima = getImageVerification().size();
		if (sizeima > 0) {
			Reporter.log("Passed : Thumb Images are present and the count is " + sizeima);
		} else {
			Reporter.log("Failed : Thumb Images are not present.");
		}
	}

	// ***************************ProductDescription**********************************//
	@FindBy(xpath = "//div[@class='pdp-price-info']/span[2]/span[2]")
	public AndroidElement discountedPrice;

	public AndroidElement getDiscountedPrice() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(discountedPrice, "discountedPrice");
		return discountedPrice;
	}

	public void assertProductDiscount() {
		try {
			AndroidElement element = (AndroidElement) getDiscountedPrice();
			objAndroidGenericMethods.VerifyStringShouldNotEmpty(element, "Discount percentage");
		} catch (Exception e) {
			System.out.println("discount not available for the product");
		}
	}
	// ***************************ProductDescription**********************************//

	@FindBy(xpath = "//span[@class='price']")
	public AndroidElement productPrice;

	public AndroidElement getProductPrice() throws InterruptedException {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(productPrice, "productPrice");
		return productPrice;
	}

	public void assertProductPrice() throws InterruptedException {
		AndroidElement element = (AndroidElement) getProductPrice();
		objAndroidGenericMethods.VerifyStringShouldNotEmpty(element, "Product price not empty");
	}

	// ***************************Bag page**********************************//

	@FindBy(xpath = "//div[text()='Bag']")
	public AndroidElement bagPageTitle;

	public AndroidElement getBagPageTitle() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(bagPageTitle, "bagPageTitle");
		return bagPageTitle;
	}

	public void assertBagPageTitle(String pageName) throws InterruptedException {
		AndroidElement element = (AndroidElement) getBagPageTitle();
		objAndroidGenericMethods.VerifyTwoString(element, pageName);
	}
	// ***************************ADDRESS PAGE**********************************//

	@FindBy(className = "address-content")
	public AndroidElement addressAdded;

	public AndroidElement getAddressAdded() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(addressAdded, "addressAdded");
		return addressAdded;
	}

	public void VerifyAddressAdded() {
		objAndroidGenericMethods.VerifyStringShouldNotEmpty(getAddressAdded(), "Address Added");
	}
	// ***************************BAG PAGE **********************************//

	@FindBy(xpath = "//div[text()='Payment']")
	public AndroidElement paymentPageTitle;

	public AndroidElement getPaymentPageTitle() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(paymentPageTitle, "paymentPageTitle");
		return paymentPageTitle;
	}

	public void assertPaymentPageTitle(String pageName) throws InterruptedException {
		AndroidElement element = (AndroidElement) getPaymentPageTitle();
		objAndroidGenericMethods.VerifyTwoString(element, pageName);
	}

	@FindBy(xpath = "//div[text()='Delivery']")
	public AndroidElement deliveryPageTitle;

	public AndroidElement getDeliveryPageTitle() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(deliveryPageTitle, "deliveryPageTitle");
		return deliveryPageTitle;
	}

	public void assertDeliveryPageTitle(String pageName) throws InterruptedException {
		AndroidElement element = (AndroidElement) getDeliveryPageTitle();
		objAndroidGenericMethods.VerifyTwoString(element, pageName);
	}

	// ***************************WISHLIST PAGE **********************************//

	@FindBy(className = "index-headingDiv")
	public AndroidElement wishlistPageTitle;

	@FindBy(className = "itemdetails-itemDetailsLabel")
	public AndroidElement wishlistproductTitle;

	@FindBy(className = "itemdetails-boldFont")
	public AndroidElement wishlistdiscountedPrice;

	@FindBy(className = "itemdetails-strike")
	public AndroidElement strikedPrice;

	public AndroidElement getStrikedPrice() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(strikedPrice, "strikedPrice");
		return strikedPrice;
	}

	public AndroidElement getwishlistDiscountedPrice() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(discountedPrice, "discountedPrice");
		return discountedPrice;
	}

	public AndroidElement getwishlistProductTitle() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(productTitle, "productTitle");
		return productTitle;
	}

	public AndroidElement getWishlistPageTitle() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(wishlistPageTitle, "wishlistPageTitle");
		return wishlistPageTitle;
	}

	public void VerifyWishlistPageTitle() {
		objAndroidGenericMethods.VerifyStringShouldNotEmpty(getWishlistPageTitle(), "My Wishlist");
	}

	public void VerifywishlistProductTitle() {
		objAndroidGenericMethods.VerifyStringShouldNotEmpty(getwishlistProductTitle(), "Product Title");
	}

	public void VerifyDiscountedPrice() {
		objAndroidGenericMethods.VerifyStringShouldNotEmpty(getwishlistDiscountedPrice(), "Discounted Price");
	}

	public void VerifyStrikedPrice() {
		objAndroidGenericMethods.VerifyStringShouldNotEmpty(getStrikedPrice(), "Striked Price");
	}

	public void VerifySellingPrice() {
		objAndroidGenericMethods.VerifyStringShouldNotEmpty(getwishlistDiscountedPrice(), "Selling Price");
	}
}
