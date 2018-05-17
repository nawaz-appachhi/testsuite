package com.automation.mobile.iOS.MobileWeb.ObjectRepository.PDPageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
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

public class PDPageObjects {

	public AppiumDriver<MobileElement>  iDriver;
	iOSGenericMethods objiOSGenericMethods;

	public PDPageObjects(AppiumDriver<MobileElement>  iDriver) {
		PageFactory.initElements(new AppiumFieldDecorator(iDriver), this);
		objiOSGenericMethods = new iOSGenericMethods(iDriver);
	}

	/*
	 * Add to Bag 30021281
	 */

	@FindBy(xpath = ".//*[text()='ADD TO BAG']")
	public IOSElement addToBag;
	
	/*
	 * Save Button
	 */
	/**
	 * Modified by- Nitesh, changed xpath
	 * earlier - @FindBy(xpath = ".//*[text()='SAVE']")
	 */
	@FindBy(xpath = "//span[@class='icon-add']")
	public IOSElement saveButton;

	/*
	 * Check Pincode
	 */
	@FindBy(xpath = "(//input[@class='form-control'])")
	public IOSElement checkPinCode;
	
	/**
	 * Created by subhasis 
	 * This is for change pincode.
	 */
	@FindBy(xpath="//button[@class='btn primary flat   ' and text()='CHANGE PIN']")
	public IOSElement changePinCode;

	@FindBy(xpath = "(//button[text()='CHECK'])")
	public IOSElement checkPin;
	
	@FindBy(xpath = "//h4[@class='card-title' and text()='Material & Care']")
	public IOSElement MaterialAndCare;
	
	

	/*
	 * size chart
	 */

	@FindBy(xpath = ".//*[text()='Size Chart']")
	public IOSElement sizeChart;
	
	@FindBy(xpath="//span[@class='icon-close']")
	public IOSElement closeSizeChart;
	
	@FindBy(className="icon-add")
	public IOSElement saveProduct;

	public WebElement getsaveProduct() {
		objiOSGenericMethods.CheckIOSElementFound(saveProduct, "saveProduct");
		return saveProduct;
	}

	/*
	 * select size
	 */
	
	@FindBy(xpath = "(//h4[@class='card-title'])[7]")
	public IOSElement SimilarProductTitle;
	
	@FindAll({ @FindBy(xpath = "//img[@class='img-responsive']\"") })
	public List<IOSElement> similarProduct;

	@FindAll({ @FindBy(xpath = ".//*[@class='sizes-list-ctn']/ul/li/div/button") })
	public List<IOSElement> selectSizesInPdp;

	@FindBy(xpath = ".//*[text()='CONFIRM']")
	public IOSElement confirmButton;

	@FindAll({ @FindBy(xpath = "//div[@class=\"searchProduct\"]") })
	public List<IOSElement> searchResult;

	/*
	 * Go to Bag 300021281
	 */

	@FindBy(xpath = ".//*[text()='GO TO BAG']")
	public IOSElement goToBag;
	
	@FindBy(xpath="//a[@id='header-cart-icon']")
	public IOSElement cartIcon;
	
	/**
	 * Product Details Xpath
	 * Created by subhasis
	 */
	
	@FindBy(xpath="//h4[@class='card-title' and text()='Product Details']")
	public IOSElement productDetails;

	/*
	 * image verification
	 */

	@FindAll({ @FindBy(xpath = "//div[starts-with(@class,'dot')]") })
	public List<IOSElement> imageVerification;

//	@FindBy(xpath = "//div[@class='tapForBest']")
	@FindBy(xpath ="//div[@class='tapForBest' and text()='Tap for best price']")
	public IOSElement tapForBestPrice;
	
	@FindBy(xpath = "//span[@class='original-price']")
	public IOSElement originalPrice;

	@FindBy(xpath = "//span[@class='price']")
	public IOSElement productPrice;

	@FindBy(xpath = "//div[@class='pdp-price-info']/span[2]/span[2]")
	public IOSElement discountedPrice;
	
	@FindBy(className = "product-title")
	public IOSElement productTitle;

	public IOSElement getProductTitle() {
		objiOSGenericMethods.CheckIOSElementFound(productTitle, "productTitle");
		return productTitle;
	}
	
	public IOSElement getSimilarProductTitle() {
		objiOSGenericMethods.CheckIOSElementFound(SimilarProductTitle, "SimilarProductTitle");
		return SimilarProductTitle;
	}
	
	public IOSElement getcartIcon() {
		objiOSGenericMethods.CheckIOSElementFound(cartIcon, "cartIcon");
		return cartIcon;
	}
	
	public IOSElement getTapForBestPrice() {
		objiOSGenericMethods.CheckIOSElementFound(tapForBestPrice, "tapForBestPrice");
		return tapForBestPrice;
	}
	
	/*
	 * Click on best price
	 */
	
	@FindBy(xpath = "//div[@class='tapForBest']")
	public IOSElement BestPrice;
	
	/*
	 * More about online shopping
	 */
	
	@FindBy(xpath = "//div[@class='footer-header clearfix']")
	public IOSElement MoreAboutOnlineShopping;
	
	@FindBy(xpath = " .//*[text()=\" Contact Us \"]")
	public IOSElement ContactUs;
	
	/*
	 * Show More Products
	 */
	
	@FindBy(xpath = "(.//span[@class=\"icon-chevron pull-right\"])[1]")
	public IOSElement MoreProducts;
	
	@FindBy(className="logo-inline")
	public IOSElement myntraLogo;
	
	/*****************getters*********************/
	
	public IOSElement getmyntraLogo() {
		return myntraLogo;
	}
	
	public IOSElement getMoreAboutOnlineShopping() {
		return MoreAboutOnlineShopping;
	}
	
	public IOSElement getMoreProducts() {
		objiOSGenericMethods.CheckIOSElementFound(MoreProducts, "MoreProducts");
		return MoreProducts;
	}
	
	public IOSElement getContactUs() {
		objiOSGenericMethods.CheckIOSElementFound(ContactUs, "ContactUs");
		return ContactUs;
	}
	
	public IOSElement getBestPrice() {
		objiOSGenericMethods.CheckIOSElementFound(BestPrice, "BestPrice");
		return BestPrice;
	}
	
	public IOSElement getgoToBag() {
		objiOSGenericMethods.CheckIOSElementFound(goToBag, "goToBag");
		return goToBag;
	}

	public List<IOSElement> getSimilarProduct() {
		objiOSGenericMethods.CheckIOSElementsListFound(similarProduct, "similarProduct");
		return similarProduct;
	}

	public List<IOSElement> getSearchResult() throws InterruptedException {
		objiOSGenericMethods.CheckIOSElementsListFound(searchResult, "searchResult");
		return searchResult;
	}

	public IOSElement getAddToBag() {
		objiOSGenericMethods.CheckIOSElementFound(addToBag, "addToBag");
		return addToBag;
	}

	public IOSElement getSaveButton() {
		objiOSGenericMethods.CheckIOSElementFound(saveButton, "saveButton");
		return saveButton;
	}

	public IOSElement getCheckPinCode() {
		objiOSGenericMethods.CheckIOSElementFound(checkPinCode, "checkPinCode");
		return checkPinCode;
	}
	
	/**
	 * This getter is created by subhasis
	 * Emi option getter
	 * @return
	 */
	public IOSElement getProductDetails() {
		return productDetails;
	}
	
	/**
	 * Created by subahsis
	 * This Getter will help user to change the enter pin code.
	 * @return
	 */
	public IOSElement getChangePinCode() {
		objiOSGenericMethods.CheckIOSElementFound(changePinCode, "checkPinCode");
		return changePinCode;
	}

	public IOSElement getCheckPin() {
		objiOSGenericMethods.CheckIOSElementFound(checkPin, "checkPin");
		return checkPin;
	}

	public IOSElement getSizeChart() {
		objiOSGenericMethods.CheckIOSElementFound(sizeChart, "sizeChart");
		return sizeChart;
	}
	
	public IOSElement getCloseSizeChart() {
		objiOSGenericMethods.CheckIOSElementFound(closeSizeChart, "closeSizeChart");
		return closeSizeChart;
	}

	public List<IOSElement> getSelectSizesInPdp() {
		objiOSGenericMethods.CheckIOSElementsListFound(selectSizesInPdp, "selectSizesInPdp");
		return selectSizesInPdp;
	}

	public IOSElement getConfirmButton() {
		objiOSGenericMethods.CheckIOSElementFound(confirmButton, "confirmButton");
		return confirmButton;
	}

	public IOSElement getOriginalPrice() {
		objiOSGenericMethods.CheckIOSElementFound(originalPrice, "originalPrice");
		return originalPrice;
	}

	public IOSElement getProductPrice() throws InterruptedException {
		//Thread.sleep(1000);
		objiOSGenericMethods.CheckIOSElementFound(productPrice, "productPrice");
		return productPrice;
	}

	public IOSElement getDiscountedPrice() {
		objiOSGenericMethods.CheckIOSElementFound(discountedPrice, "discountedPrice");
		return discountedPrice;
	}

	public List<IOSElement> getImageVerification() {
		objiOSGenericMethods.CheckIOSElementsListFound(imageVerification, "imageVerification");
		return imageVerification;
	}
	
	public void clickOnMoreProducts() {
		objiOSGenericMethods.clickOnIOSElement(getMoreProducts(), "More Products.");
	}

	public void clickOnSaveButton() {
		//objiOSGenericMethods.waitDriver(getSaveButton(), "SaveButton");
		objiOSGenericMethods.clickOnIOSElement(getSaveButton(), "SaveButton");
	}
	
	public void clickOnContactUs() {
		objiOSGenericMethods.clickOnIOSElement(getContactUs(), "SaveButton");
	}

	public void clickOnSizeChart() {
		//objiOSGenericMethods.waitDriver(getSizeChart(), "Size Chart");
		objiOSGenericMethods.clickOnIOSElement(getSizeChart(), "select size");
	}
	
	public void clickOnCloseSizeChartButton() {
		objiOSGenericMethods.clickOnIOSElement(getCloseSizeChart(), "close Size chart button");
	}
	
	public void clickOngoToBag() {
		objiOSGenericMethods.clickOnIOSElement(getgoToBag(), "goToBag");
	}

	public void clickOnTapForBestPrice() {
		try {
			if(getTapForBestPrice().isDisplayed())
			objiOSGenericMethods.clickOnIOSElement(getTapForBestPrice(), "Tap For BestPrice ");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * This method will help user to remove the existing pincode from pin field.
	 * @param Created by Subhaasis 
	 */
	
	public void clickOnChangePinCode() {
		objiOSGenericMethods.clickOnIOSElement(changePinCode, "change pincode");
	}

	public void checkPinCode(String name) {
			try {
				if(getChangePinCode().isDisplayed())
				objiOSGenericMethods.clickOnIOSElement(getChangePinCode(), "change pincode");
				System.out.println("Change pin code clicked succesfully");
				getCheckPinCode().sendKeys(name);
			} catch (Exception e) {
				objiOSGenericMethods.clickOnIOSElement(getCheckPinCode(), "CheckPinCode");
				getCheckPinCode().sendKeys(name);
			}
		}

	public void clickOnCheckPin() {
		objiOSGenericMethods.clickOnIOSElement(getCheckPin(), "CheckPin");
	}
	
	public void clickOnMoreAboutOnlineShopping() {
		objiOSGenericMethods.clickOnIOSElement(getMoreAboutOnlineShopping(), "More About Online Shopping.");
	}

	/**
	 * Lata element not mentioned
	 */

	public List<IOSElement> getSelectSize() {
		return getSelectSize();
	}

	public void clickOnConfirmButton() {
		objiOSGenericMethods.clickOnIOSElement(getConfirmButton(), "ConfirmButton");
	}

	public void setSelectSizesInPdp(List<IOSElement> selectSizesInPdp) {
		this.selectSizesInPdp = selectSizesInPdp;
	}
	
	public void clickOnBestPrice() {
		objiOSGenericMethods.waitDriver(getBestPrice(), "BestPrice");
		objiOSGenericMethods.clickOnIOSElement(getBestPrice(), "Tab for Best Price link.");
	}

	public List<IOSElement> getSelectSizesInPdp(int i, String size) throws InterruptedException {
//		Thread.sleep(1000);
		selectSizesInPdp.get(i).click();
		System.out.println("User selected " + size + " Size");
//		Thread.sleep(5000);
		objiOSGenericMethods.CheckIOSElementsListFound(selectSizesInPdp, "selectSizesInPdp");
		return selectSizesInPdp;
	}

	public void SelectSizeElements(String Size) {
		int size = getSelectSize().size();
		for (int i = 0; i < size; i++) {
			if ((getSelectSize().get(i).isEnabled())) {
				(getSelectSize()).get(i).click();
				break;
			}
		}
		return;
	}

	public void selectSizeOfProduct(String size) throws InterruptedException {
		List<IOSElement> selectSizesInList = getSelectSizesInPdp(1, size);
		for (IOSElement result : selectSizesInList) {
			IOSElement span = (IOSElement) result.findElement(By.tagName("span"));
			String sizeString = span.getText();
			if (sizeString.contains(size)) {
				span.click();
				break;
			}
		}
	}

	public void selectSizeOfProduct() throws InterruptedException {
		List<IOSElement> selectSizesInList = getSelectSizesInPdp();

		for (IOSElement result : selectSizesInList) {
			List<MobileElement> spans = result.findElements(By.tagName("span"));

			if (spans.size() != 2) {
				result.click();
				break;
			}
		}
	}

	public void clickOnSize() {
		objiOSGenericMethods.clickOnIOSElement(getSizeChart(), "SizeChart");
	}

	public void clickFirstProductSearchResult() throws InterruptedException {
		List<IOSElement> searchResultList = getSearchResult();
		for (IOSElement result : searchResultList) {
			IOSElement link = (IOSElement) result.findElement(By.tagName("a"));
			link.click();
			break;
		}
	}

	public void ProductSizeElements(String Size) {
		int size = getSimilarProduct().size();
		for (int i = 0; i < size; i++) {
			if ((getSimilarProduct().get(i).isEnabled())) {
				(getSimilarProduct()).get(i).click();
				break;
			}
		}
		return;
	}

	public void imageVerification() {
		int sizeima = getImageVerification().size();
		if (sizeima > 0) {
			Reporter.log("Passed : Thumb Images are present and the count is " + sizeima);
		} else {
			Reporter.log("Failed : Thumb Images are not present.");
		}
	}

	public void assertProductStrikedPrice() {
		IOSElement element = (IOSElement) getOriginalPrice();
		objiOSGenericMethods.VerifyStringShouldNotEmpty(element, "Striked Price");
	}

	public void assertProductPrice() throws InterruptedException {
		IOSElement element = (IOSElement) getProductPrice();
		objiOSGenericMethods.VerifyStringShouldNotEmpty(element, "Product price not empty");
	}

	public void assertProductDiscount() {
		try {
			IOSElement element = (IOSElement) getDiscountedPrice();
			objiOSGenericMethods.VerifyStringShouldNotEmpty(element, "Discount percentage");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("discount not available for the product");
		}
	}

	public void clickOnAddtoBag() {
		if (getAddToBag().isDisplayed()) {
			try {
				getAddToBag().click();
//				Thread.sleep(3000);
			} catch (Exception e) {
			}
		}
	}
	
	public void clickSaveToWishlist(){
		objiOSGenericMethods.clickOnIOSElement(getSaveButton(), "Save Button.");
	}
	
	public void clickMyntraLogo(){
		objiOSGenericMethods.clickOnIOSElement(getmyntraLogo(), "Myntra Logo");
	}
	
	public void clickCartIcon(){
		objiOSGenericMethods.waitDriver(getcartIcon(), "Cart");
		objiOSGenericMethods.clickOnIOSElement(getcartIcon(), "Cart Icon");
	}
	
	public void VerifyProductTitle()	{
		objiOSGenericMethods.VerifyStringShouldNotEmpty(getProductTitle(), "Product Title");
	}
}