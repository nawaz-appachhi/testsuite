package com.automation.mobile.iOS.MobileWeb.ObjectRepository.PLPageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;

import com.automation.core.mobile.iOS.iOSGenericMethods;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class PLPageObjects {

	public PLPageObjects(AppiumDriver<MobileElement>  iDriver) {
		PageFactory.initElements(new AppiumFieldDecorator(iDriver), this);
		objiOSGenericMethods = new iOSGenericMethods(iDriver);
	}

	public AppiumDriver<MobileElement>  iDriver;
	iOSGenericMethods objiOSGenericMethods;

	@FindAll({ @FindBy(xpath = "//ul[@class='list']/li/div/div/a/div/div/picture[@class='img-responsive']/img") })
	public List<IOSElement> productList;

	@FindBy(xpath = "//div[@class='page searchResults']/h1")
	public IOSElement searchResultHeader;

	/*
	 * discount percentage
	 */

	@FindBy(xpath = "(//span[@class = 'highlight-black discount-percentage'])[1]")
	public IOSElement discountPercentage;

	@FindBy(xpath = "//div[@class='view-details']	")
	public IOSElement viewDetails;

	@FindBy(xpath = "//div[@class='name text-ellipsis']")
	public IOSElement similarProductofroadstar;
	
	@FindBy(xpath = "(//div[@class='wishlist text-md icon-add'])[2]")
	public IOSElement saveToWishlist;
	
	@FindBy(xpath = "//div[@class='content-wrap']/div/div[1]")
	public List<IOSElement> productTitle;
	
	@FindBy(xpath = "//div[@class='content-wrap']")
	public List<IOSElement> productDescription;

	/*
	 * view details
	 */

	public List<IOSElement> getProductDescription() {
		objiOSGenericMethods.CheckIOSElementsListFound(productDescription, "productDescription");
		return productDescription;
	}
	
	public IOSElement getDiscountPercentage() {
		objiOSGenericMethods.CheckIOSElementFound(discountPercentage, "discountPercentage");
		return discountPercentage;
	}
	
	public IOSElement getSaveSecondProductToWishlist() {
		objiOSGenericMethods.CheckIOSElementFound(saveToWishlist, "saveToWishlist");
		return saveToWishlist;
	}

	public void setDiscountPercentage(IOSElement discountPercentage) {
		this.discountPercentage = discountPercentage;
	}

	public IOSElement getSimilarProductofroadstar() {
		objiOSGenericMethods.CheckIOSElementFound(similarProductofroadstar, "similarProductofroadstar");
		return similarProductofroadstar;
	}

	public IOSElement getViewDetails() {
		objiOSGenericMethods.CheckIOSElementFound(viewDetails, "viewDetails");
		return viewDetails;
	}

	public List<IOSElement> getProductList() {
		objiOSGenericMethods.CheckIOSElementsListFound(productList, "productList");
		return productList;
	}

	public IOSElement getSearchResultHeader() {
		objiOSGenericMethods.CheckIOSElementFound(searchResultHeader, "searchResultHeader");
		return searchResultHeader;
	}

	public void ProductListElements(int i) {
		System.out.println("Total number of Products are " + productList.size());
		productList.get(i).click();
	}

	public void verifySearchResult(String searchText) throws InterruptedException {
//		Thread.sleep(2000);
		objiOSGenericMethods.waitDriver(getSearchResultHeader(), "Search Result");
		IOSElement element = (IOSElement) getSearchResultHeader();
		objiOSGenericMethods.VerifyStringShouldNotEmpty(element, searchText);

	}

	public void clickOnViewDetails() {
		objiOSGenericMethods.clickOnIOSElement(getViewDetails(), "ViewDetails");
	}

	public void clickOnsimilarProductofroadstar() {
		objiOSGenericMethods.clickOnIOSElement(getSimilarProductofroadstar(), "Similar Product of roadstar");
	}

	public void clickOndiscountPercentage() {
		objiOSGenericMethods.clickOnIOSElement(getDiscountPercentage(), "Discount Percentage");
	}
	
	public void clickToSaveToWishlist() {
		objiOSGenericMethods.clickOnIOSElement(getSaveSecondProductToWishlist(), " Wishlist Icon");
	}
	
	public void VerifyProductDetails()
	{
		List<IOSElement> productDescriptionList = getProductDescription();
		int s = productDescriptionList.size();

		if(s>=0)
		{
			Reporter.log("Passed : Products are displayed in PLP Page with count =" + s);
		}
		else
		{
			Reporter.log("Failed : Products are not displayed in PLP Page ");
		}
		int i=1;
		for(IOSElement product : productDescriptionList)
		{
			IOSElement productBrand = (IOSElement)product.findElement(By.xpath("(//div[@class='content-wrap']/div/div[1])[" + (i) +"]"));
			IOSElement productPrice = (IOSElement)product.findElement(By.xpath("(//div[@class='price-container']/span[1])[" + (i) +"]"));
			IOSElement productName = (IOSElement)product.findElement(By.xpath("(//h4[@class='description text-sm'])[" + (i) +"]"));
			objiOSGenericMethods.VerifyStringShouldNotEmpty(productBrand, productBrand.getText());
			objiOSGenericMethods.VerifyStringShouldNotEmpty(productPrice, productPrice.getText());
			objiOSGenericMethods.VerifyStringShouldNotEmpty(productName, productName.getText());
			i++;
		}
		
	}
}
