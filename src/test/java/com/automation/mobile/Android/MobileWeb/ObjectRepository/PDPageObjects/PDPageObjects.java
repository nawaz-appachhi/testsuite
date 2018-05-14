package com.automation.mobile.Android.MobileWeb.ObjectRepository.PDPageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;

import com.automation.core.mobile.Android.AndroidGenericMethods;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
/**
 * Modified By:Aishurya:Changed string parameter,which is being used in reporting
 */

public class PDPageObjects {

	AndroidGenericMethods objAndroidGenericMethods;

	public PDPageObjects(AndroidDriver<AndroidElement> aDriver) {
		PageFactory.initElements(new AppiumFieldDecorator(aDriver), this);
		objAndroidGenericMethods = new AndroidGenericMethods(aDriver);
	}

	/*
	 * Add to Bag 30021281
	 */

	@FindBy(xpath = ".//*[text()='ADD TO BAG']")
	public AndroidElement addToBag;

	/*
	 * Save Button
	 */

	@FindBy(xpath = ".//*[text()='SAVE']")
	public AndroidElement saveButton;

	/*
	 * Check Pincode
	 */
	@FindBy(xpath = "(//input[@class='form-control'])")
	public AndroidElement checkPinCode;

	@FindBy(xpath = "(//button[text()='CHECK'])")
	public AndroidElement checkPin;

	/*
	 * Select size
	 */

	@FindBy(xpath = ".//*[text()='Size Chart']")
	public AndroidElement sizeChart;

	@FindBy(xpath = "//span[@class='icon-close']")
	public AndroidElement closeSizeChart;

	public AndroidElement getCloseSizeChart() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(closeSizeChart, "closeSizeChart");
		return closeSizeChart;
	}

	@FindAll({ @FindBy(xpath = "//img[@class='img-responsive']\"") })
	public List<AndroidElement> similarProduct;

	/*
	 * select size
	 */

	@FindAll({ @FindBy(xpath = ".//*[@class='sizes-list-ctn']/ul/li/div/button") })
	public List<AndroidElement> selectSizesInPdp;

	@FindBy(xpath = ".//*[text()='CONFIRM']")
	public AndroidElement confirmButton;

	@FindAll({ @FindBy(xpath = "//div[@class='searchProduct']") })
	public List<AndroidElement> searchResult;

	/*
	 * Go to Bag 300021281
	 */

	@FindBy(xpath = ".//*[text()='GO TO BAG']")
	public AndroidElement goToBag;

	/*
	 * image verification
	 */

	@FindAll({ @FindBy(xpath = "//div[starts-with(@class,'dot')]") })
	public List<AndroidElement> imageVerification;

	@FindBy(xpath = "//div[@class='tapForBest']")
	public AndroidElement tapForBestPrice;
	/**
	 * original price
	 * 
	 * @return
	 */
	@FindBy(xpath = "//span[@class='original-price']")
	public AndroidElement originalPrice;

	@FindBy(xpath = "//span[@class='price']")
	public AndroidElement productPrice;

	@FindBy(xpath = "//div[@class='pdp-price-info']/span[2]/span[2]")
	public AndroidElement discountedPrice;

	@FindBy(xpath = "(.//span[@class=\"icon-chevron pull-right\"])[1]")
	public AndroidElement MoreProducts;

	@FindBy(xpath = "//span[@class='logo']")
	public AndroidElement myntralogo;

	@FindBy(xpath = "(//button[contains(@class,'btn') and contains(@class,'default') and contains(@class,'outline') and contains(@class,'size-btn') and not(contains(@class,'disabled'))])[1]")
	public AndroidElement firstAvailableSize;

	@FindBy(xpath = "//h4[text()='Similar Products']")
	public AndroidElement SimilarProductsHeader;
	
	
	@FindBy(xpath = "//h4[contains(text(),'Product Details')]")
	public AndroidElement productdetails;
	
	@FindBy(xpath = "//button[@class='btn primary flat   ' and text()='CHECK']")
	public AndroidElement CHECK;

	public AndroidElement getCHECK() {
		return CHECK;
	}
	public AndroidElement getSimilarProductsHeader() {
		return SimilarProductsHeader;
	}
	public AndroidElement getFirstAvailableSize() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(firstAvailableSize, "firstAvailableSize");
		return firstAvailableSize;
	}
	/**
	 * getter for  myntra logo displayed in product description page in header
	 * @ModifiedBy:-Rakesh
	 * added check method
	 * @
	 */
	public AndroidElement getMyntralogo() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(myntralogo, "myntralogo");
		return myntralogo;
	}

	public AndroidElement getMoreProducts() {
		return MoreProducts;
	}

	public AndroidElement getTapForBestPrice() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(tapForBestPrice, "tapForBestPrice");
		return tapForBestPrice;
	}

	public AndroidElement getgoToBag() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(goToBag, "goToBag");
		return goToBag;
	}

	public List<AndroidElement> getSimilarProduct() {
		objAndroidGenericMethods.CheckAndroidElementsListFoundMWeb(similarProduct, "similarProduct");
		return similarProduct;
	}

	public List<AndroidElement> getSearchResult()  {
		objAndroidGenericMethods.CheckAndroidElementsListFoundMWeb(searchResult, "searchResult");
		return searchResult;
	}

	public AndroidElement getAddToBag() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(addToBag, "addToBag");
		return addToBag;
	}

	public AndroidElement getSaveButton() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(saveButton, "saveButton");
		return saveButton;
	}

	public AndroidElement getCheckPinCode() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(checkPinCode, "checkPinCode");
		return checkPinCode;
	}

	public AndroidElement getCheckPin() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(checkPin, "checkPin");
		return checkPin;
	}

	public AndroidElement getSizeChart() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(sizeChart, "sizeChart");
		return sizeChart;
	}

	public List<AndroidElement> getSelectSizesInPdp() {
		objAndroidGenericMethods.CheckAndroidElementsListFoundMWeb(selectSizesInPdp, "selectSizesInPdp");
		return selectSizesInPdp;
	}

	public AndroidElement getConfirmButton() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(confirmButton, "confirmButton");
		return confirmButton;
	}

	public AndroidElement getOriginalPrice() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(originalPrice, "originalPrice");
		return originalPrice;
	}

	public AndroidElement getProductPrice()  {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(productPrice, "productPrice");
		return productPrice;
	}

	public AndroidElement getDiscountedPrice() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(discountedPrice, "discountedPrice");
		return discountedPrice;
	}

	public List<AndroidElement> getImageVerification() {
		objAndroidGenericMethods.CheckAndroidElementsListFoundMWeb(imageVerification, "imageVerification");
		return imageVerification;
	}

	public void clickOnSaveButton()  {
		objAndroidGenericMethods.clickOnAndroidElement(getSaveButton(), "SaveButton");
	}

	public void clickOngoToBag()  {
		objAndroidGenericMethods.clickOnAndroidElement(getgoToBag(), "goToBag");
	}

	public void clickOnTapForBestPrice()  {
		objAndroidGenericMethods.clickOnAndroidElement(getTapForBestPrice(), "TapForBestPrice");
	}
	
	//public void clickOnSimilarProduct() {
		//objAndroidGenericMethods.clickOnAndroidElement(getTapForBestPrice(), "TapForBestPrice");
	//}

	/**
	 * @param string
	 * Modified by : Anu
	 * Decription : modified hardcoded pincode
	 */
	public void checkPinCode(String string) {
		objAndroidGenericMethods.clickOnAndroidElement(getCheckPinCode(), "CheckPinCode");
		getCheckPinCode().sendKeys(string);
	}

	public void clickOnCheckPin() {
		objAndroidGenericMethods.clickOnAndroidElement(getCheckPin(), "CheckPin");
	}
	public void clickOnCHECK() {
		objAndroidGenericMethods.clickOnAndroidElement(getCHECK(), "CHECK");
	}

	/**
	 * Lata element not mentioned
	 */

	public List<AndroidElement> getSelectSize() {
		return getSelectSize();
	}

	/**
	 * Modified by : Anu
	 * Description : Added try catch block Since 'one size' product does not need to click on confirm button
	 */
	public void clickOnConfirmButton()  {
		try{
		objAndroidGenericMethods.clickOnAndroidElement(getConfirmButton(), "ConfirmButton");}
		catch(Exception e){
			System.out.println("'One Size' product");
		}
	}

	public void setSelectSizesInPdp(List<AndroidElement> selectSizesInPdp) {
		this.selectSizesInPdp = selectSizesInPdp;
	}

	// public List<AndroidElement> SelectSize() {
	// return getSelectSize();
	// }

	public List<AndroidElement> getSelectSizesInPdp(int i, String size)  {
		selectSizesInPdp.get(i).click();
		System.out.println("User selected " + size + " Size");
		objAndroidGenericMethods.CheckAndroidElementsListFoundMWeb(selectSizesInPdp, "selectSizesInPdp");
		return selectSizesInPdp;
	}

	/**
	 * method to slect size auto
	 * 
	 * @param Size
	 */
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

	// .//*[@class='sizes-list-ctn']/ul/li/div/button/span[@class='strike']")
	public void selectSizeOfProduct(String size)  {
		List<AndroidElement> selectSizesInList = getSelectSizesInPdp(1, size);
		for (AndroidElement result : selectSizesInList) {
			AndroidElement span = (AndroidElement) result.findElement(By.tagName("span"));
			String sizeString = span.getText();
			if (sizeString.contains(size)) {
				span.click();
				break;
			}
		}
	}

	public void selectSizeOfProduct()  {
		List<AndroidElement> selectSizesInList = getSelectSizesInPdp();
		// System.out.println("Size:" + selectSizesInList.size());

		for (AndroidElement result : selectSizesInList) {
			List<MobileElement> spans = result.findElements(By.tagName("span"));
			if (spans.size() != 2) {
				objAndroidGenericMethods.CheckAndroidElementFoundMWeb(result, "SizeOfProduct");
				objAndroidGenericMethods.clickOnAndroidElement(result, "SizeOfProduct");
				//result.click();
				break;
			}
		}

	}

	public void clickOnSize() {
		objAndroidGenericMethods.clickOnAndroidElement(getSizeChart(), "SizeChart");
		getSizeChart().click();
	}

	public void clickFirstProductSearchResult()  {
		List<AndroidElement> searchResultList = getSearchResult();
		for (AndroidElement result : searchResultList) {
			// result.click();
			// break;
			AndroidElement link = (AndroidElement) result.findElement(By.tagName("a"));
			// String hrefString = link.getAttribute("href");
			// if (hrefString.contains(ProductCode)) {
			objAndroidGenericMethods.clickOnAndroidElement(link, "FirstProduct");
			//link.click();
			break;
			// }
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


	public void assertProductStrikedPrice() {
		AndroidElement element = (AndroidElement) getOriginalPrice();
		objAndroidGenericMethods.VerifyStringShouldNotEmpty(element, "Striked Price");
	}

	public void assertProductPrice()  {
		AndroidElement element = (AndroidElement) getProductPrice();
		objAndroidGenericMethods.VerifyStringShouldNotEmpty(element, "Product price not empty");
	}


	public void clickOnAddtoBag() {
		if (getAddToBag().isDisplayed()) {
			try {
				objAndroidGenericMethods.clickOnAndroidElement(getAddToBag(), "AddToBag");
			} catch (Exception e) {
			}
		}
	}

	public void ClickOnSizeChartLink()  {
		objAndroidGenericMethods.clickOnAndroidElement(getSizeChart(), "SizeChart");
	}

	public void clickOnMoreProducts() {
		getMoreProducts().click();
	}
	/**
	 * method to click on myntra logo displayed in product description page in header
	 * @ModifiedBy:-Rakesh
	 * added Click method
	 * @
	 */
	public void clickOnMyntraLogo()  {
		objAndroidGenericMethods.clickOnAndroidElement(getMyntralogo(), "Myntralogo");
	}

	public void VerifySimilarProductsHeader()  {
		getSimilarProductsHeader().getText();
		System.out.println("Verified Similar products");
	}

	public void ClickCloseSizeChart()  {
		getCloseSizeChart().click();
	}
	/**
	 * Method to select the first available size displayed for a product
	 * Added proper format for click event and check events
	 * @
	 */
	/**
	 * Modified by : Anu
	 * Description : Added try catch block Since 'one size' product does not have sizes to select
	 */
	public void setFirstAvailableSize()  {
		try{
		objAndroidGenericMethods.clickOnAndroidElement(getFirstAvailableSize(), "FirstAvailableSize");}
		catch(Exception e){
			System.out.println("'One Size' product");
		}
	}

	@FindBy(xpath = "(//h4[@class='card-title'])[7]")
	public AndroidElement SimilarProductTitle;

	public AndroidElement getSimilarProductTitle() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(SimilarProductTitle, "SimilarProductTitle");
		return SimilarProductTitle;
	}

	@FindBy(xpath = "//a[@id='header-cart-icon']")
	public AndroidElement cartIcon;

	public AndroidElement getcartIcon() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(cartIcon, "cartIcon");
		return cartIcon;
	}

	public void clickCartIcon() {
		objAndroidGenericMethods.clickOnAndroidElement(getcartIcon(), "Cart Icon");
	}
	
	
	
	@FindBy(className = "product-title")
	public AndroidElement productTitle;

	public AndroidElement getProductTitle() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(productTitle, "productTitle");
		return productTitle;
	}

	public void VerifyProductTitle() {
		objAndroidGenericMethods.VerifyStringShouldNotEmpty(getProductTitle(), "Product Title");
	}
	
	

	

	public void imageVerification() {
		int sizeima = getImageVerification().size();
		if (sizeima > 0) {
			Reporter.log("Passed : Thumb Images are present and the count is " + sizeima);
		} else {
			Reporter.log("Failed : Thumb Images are not present.");
		}
	}
	

	

	public void assertProductDiscount() {
		try {
			AndroidElement element = (AndroidElement) getDiscountedPrice();
			objAndroidGenericMethods.VerifyStringShouldNotEmpty(element, "Discount percentage");
		} catch (Exception e) {
			System.out.println("discount not available for the product");
		}
	}
	
	

	

	@FindBy(xpath = "//span[@id='signin']")
	public AndroidElement HeadercartIcon;

	public AndroidElement getHeadercartIcon() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(HeadercartIcon, "cartIcon");
		return cartIcon;
	}

	public void clickHeaderCartIcon() {
		objAndroidGenericMethods.clickOnAndroidElement(getHeadercartIcon(), "Cart Icon");
	}
	
	
	public void clickOnCloseSizeChartButton() {
		objAndroidGenericMethods.clickOnAndroidElement(getCloseSizeChart(), "close Size chart button");
	}

	public void clickSaveToWishlist(){
		objAndroidGenericMethods.clickOnAndroidElement(getSaveButton(), "Save Button.");
	}

}