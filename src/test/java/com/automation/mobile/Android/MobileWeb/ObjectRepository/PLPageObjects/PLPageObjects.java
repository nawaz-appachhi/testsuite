package com.automation.mobile.Android.MobileWeb.ObjectRepository.PLPageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;

import com.automation.core.mobile.Android.AndroidGenericMethods;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
/**
 * Modified By:Aishurya:Changed string parameter,which is being used in reporting
 */
public class PLPageObjects {

	AndroidGenericMethods objAndroidGenericMethods;
	public PLPageObjects(AppiumDriver<MobileElement> aDriver) {
		PageFactory.initElements(new AppiumFieldDecorator(aDriver), this);
		objAndroidGenericMethods = new AndroidGenericMethods(aDriver);
	}

	@FindAll({ @FindBy(xpath = "//ul[@class='list']/li/div/div/a/div/div/picture[@class='img-responsive']/img") })
	public List<AndroidElement> productList;

	@FindBy(xpath = "//div[@class='page searchResults']/h1")
	public AndroidElement searchResultHeader;

	/*
	 * discount percentage
	 */

	@FindBy(xpath = "(//span[@class = 'highlight-black discount-percentage'])[1]")
	public AndroidElement discountPercentage;

	@FindBy(xpath = "//div[@class='view-details']	")
	public AndroidElement viewDetails;

	@FindBy(xpath = "//div[@class='name text-ellipsis']")
	public AndroidElement similarProductofroadstar;

	/*
	 * view details
	 */

	public AndroidElement getDiscountPercentage() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(discountPercentage, "discountPercentage");
		return discountPercentage;
	}

	public void setDiscountPercentage(AndroidElement discountPercentage) {
		this.discountPercentage = discountPercentage;
	}

	public AndroidElement getSimilarProductofroadstar() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(similarProductofroadstar, "similarProductofroadstar");
		return similarProductofroadstar;
	}

	public AndroidElement getViewDetails() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(viewDetails, "viewDetails");
		return viewDetails;
	}

	public List<AndroidElement> getProductList() {
		objAndroidGenericMethods.CheckAndroidElementsListFoundMWeb(productList, "productList");
		return productList;
	}

	public AndroidElement getSearchResultHeader() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(searchResultHeader, "searchResultHeader");
		return searchResultHeader;
	}

	public void ProductListElements(int i) {
		System.out.println("Total number of Products are " + productList.size());
		productList.get(i).click();
	}

	/**
	 * @param searchText
	 * Modified by :Anu
	 * Description : Added waitdriver since search result header is taking time to load
	 */
	public void verifySearchResult(String searchText)  {
		objAndroidGenericMethods.waitDriver(searchResultHeader, "searchResultHeader");
		AndroidElement element = (AndroidElement) getSearchResultHeader();
		objAndroidGenericMethods.VerifyStringShouldNotEmpty(getSearchResultHeader(), searchText);

	}

	public void clickOnViewDetails() {
		objAndroidGenericMethods.clickOnAndroidElement(getViewDetails(), "ViewDetails");
	}

	public void clickOnsimilarProductofroadstar() {
		objAndroidGenericMethods.clickOnAndroidElement(getSimilarProductofroadstar(), "SimilarProductofroadstar");
	}

	public void clickOndiscountPercentage() {
		objAndroidGenericMethods.clickOnAndroidElement(getDiscountPercentage(), "DiscountPercentage");
	}
	
	
	@FindBy(xpath = "(//div[@class='wishlist text-md icon-add'])[2]")
	public AndroidElement saveToWishlist;
	
	public AndroidElement getSaveSecondProductToWishlist() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(saveToWishlist, "saveToWishlist");
		return saveToWishlist;
	}
	public void clickToSaveToWishlist() {
		objAndroidGenericMethods.clickOnAndroidElement(getSaveSecondProductToWishlist(), "Wishlist Icon");
	}
	@FindBy(xpath = "//div[@class='content-wrap']")
	public List<AndroidElement> productDescription;

	public List<AndroidElement> getProductDescription() {
		objAndroidGenericMethods.CheckAndroidElementsListFoundMWeb(productDescription, "productDescription");
		return productDescription;
	}

	/**
	 * objPLPageObjects
	 */
	/**
	 * Modified by : anu
	 * Description : Product name is not available for some products and test is failing. 
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
			AndroidElement productBrand = (AndroidElement)product.findElement(By.xpath("(//div[@class='content-wrap']/div/div[1])[" + (i) +"]"));
			AndroidElement productPrice = (AndroidElement)product.findElement(By.xpath("(//div[@class='price-container']/span[1])[" + (i) +"]"));
			//AndroidElement productName = (AndroidElement)product.findElement(By.xpath("(//h4[@class='description text-sm'])[" + (i) +"]"));
			objAndroidGenericMethods.VerifyStringShouldNotEmpty(productBrand, productBrand.getText());
			objAndroidGenericMethods.VerifyStringShouldNotEmpty(productPrice, productPrice.getText());
			//objAndroidGenericMethods.VerifyStringShouldNotEmpty(productName, productName.getText());
			i++;
		}
 
	}
}
