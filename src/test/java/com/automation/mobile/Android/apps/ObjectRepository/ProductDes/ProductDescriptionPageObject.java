package com.automation.mobile.Android.apps.ObjectRepository.ProductDes;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;

import org.apache.tools.ant.types.selectors.modifiedselector.ModifiedSelector;
import org.codehaus.plexus.util.cli.EnhancedStringTokenizer;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import com.automation.core.mobile.Android.AndroidGenericMethods;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class ProductDescriptionPageObject {
	AppiumDriver<MobileElement> aDriver;
	AndroidGenericMethods objAndroidGenericMethods;
	public AndroidElement element = null;
	public AndroidElement productElement = null;
	String pdpProductPrice;
	String pdpProductDiscount;

	public ProductDescriptionPageObject(AppiumDriver<MobileElement> aDriver) {
		PageFactory.initElements(new AppiumFieldDecorator(aDriver), this);
		objAndroidGenericMethods = new AndroidGenericMethods(aDriver);
		this.aDriver = aDriver;
	}
	/**
	 * Author: Sneha
	 * 
	 * Description : object identified to view product image
	 */
	@FindBy(xpath = "//android.widget.ImageView[@index='0']")
	public AndroidElement productImageView;
	
	/**
	 * Author: Sneha
	 * 
	 * Description : object identified to scroll product image 
	 */
	@FindBy(xpath = "//*[@index='0' or @content-desc='image_swipe']")
	public AndroidElement productImageScroll;
	
	/**
	 * Author: Sneha
	 * 
	 * Description : object identified for scroll product image in full image view 
	 */
	@FindAll({
			@FindBy(xpath = "//android.widget.FrameLayout[@resource-id='com.myntra.android:id/fl_fullscreenimageswipe']") })
	public List<AndroidElement> productImageView2;

	/**
	 * Author: Sneha
	 * 
	 * Description : object identified for show similar icon displayed on product tile.
	 */
	@FindBy(xpath = "//*[@content-desc ='similar_icon']")
	public AndroidElement similarIconbtn;

	/**
	 * Author: Sneha
	 * 
	 * Description : object identified for selecting first similar product.
	 */
	@FindBy(xpath = "//*[starts-with(@content-desc,'similar_item_0')]")
	public AndroidElement firstSimilarProduct;

	/**
	 * Author: Sneha
	 * 
	 * Description : object identified for selecting product size.
	 */
	@FindAll({ @FindBy(xpath = "//*[starts-with(@content-desc,'size_select')]") })
	public List<AndroidElement> productSizesbtn;

	

	

	/**
	 * Author: Sneha
	 * 
	 * Description : object identified for clicking add to bag button
	 */
	@FindBy(xpath = "//*[@content-desc ='buy_button']")
	public AndroidElement addToBagbtn;

	/**
	 * Author: Sneha
	 * 
	 * Description : object identified for clicking Save to wishlist button
	 */
	@FindBy(xpath = "//*[@content-desc ='save_button']")
	public AndroidElement saveToWishlistbtn;

	/**
	 * Author: Sneha
	 * 
	 * Description : object identified for clicking on Wishlist button
	 */
	@FindBy(xpath = "//*[@index='5']/android.widget.ImageView")
	public AndroidElement wishListbtn;

	/**
	 * Author: Sneha
	 * 
	 * Description : object identified for clicking on Go To Bag button
	 * modified as xpath was not working
	 */
	//@FindBy(xpath = "//android.widget.TextView[@text ='GO TO BAG']")
	@FindBy(xpath="//*[@content-desc='buy_button' or @text='buy_button']")
	public AndroidElement goToBagbtn;

	/**
	 * Author: Sneha
	 * 
	 * Description : object identified for clicking on Enter Pin code button.
	 * modified by vinay
	 * old was public AndroidElement pincodebtn;
	 */
	@FindBy(xpath = "//android.widget.TextView[@text ='Enter PIN Code']")
	public AndroidElement enterpincodebtn;

	/**
	 * Author: Sneha
	 * 
	 * Description : object identified for Entering Pin code.
	 */
	@FindBy(xpath = "//android.widget.EditText[@content-desc='delivery_pincode_input']")
	public AndroidElement pincodeTxt;

	/**
	 * Author: Sneha
	 * 
	 * Description : object identified for checking Pin code.
	 */
	@FindBy(xpath = "//android.widget.TextView[@text = 'CHECK']")
	public AndroidElement checkDeliveryOptionsbtn;

	/**
	 * Author: Sneha
	 * 
	 * Description : object identified for changing Pin code.
	 * Modified by Vinay 
	 */
	@FindBy(xpath = "//android.widget.TextView[@text = 'CHANGE']")
	public AndroidElement changePinCodebtn;

	/**
	 * Author: Sneha
	 * 
	 * Description : object identified for product price.
	 */
	@FindAll({ @FindBy(xpath = "//android.view.ViewGroup[@content-desc='pdp_price']") })
	public List<AndroidElement> pdp_Price;

	/**
	 * Author: Sneha
	 * 
	 * Description : object identified for clicking Best price button.
	 * @ModifiedBy:-Rakesh Alternative xpath
	 */
	@FindBy(xpath = "//android.widget.TextView[@content-desc = 'Tap for best price' or @text= 'Tap for best price']")
	public AndroidElement BestPricebtn;

	/**
	 * Author: Sneha
	 * 
	 * Description : object identified for clicking Done button.
	 */
	@FindBy(xpath = "//android.widget.TextView[@text='DONE']")
	public AndroidElement Donebtn;
 
	/**
	 * Author: Sneha
	 * 
	 * Description : object identified for clicking Size chart button.
	 */
	@FindBy(xpath = "//android.widget.TextView[@text='SIZE CHART']")
	public AndroidElement SizeChartbtn;

	/**
	 * Author: Sneha
	 * 
	 * Description : object identified for clicking close Size chart button.
	 */
	@FindBy(xpath = "//android.widget.ImageView[@index='0']")
	public AndroidElement CloseSizeChartbtn;

	
	/**
	 * Author: Sneha
	 * 
	 * Description : object identified for clicking Bag header button.
	 */
	//@FindBy(xpath = "//android.view.ViewGroup[@index='3']/android.view.ViewGroup/android.widget.TextView | //android.view.View[@index='3']/android.view.View/android.widget.TextView")
	@FindBy(xpath = "//android.widget.ImageView[@content-desc='rightElement0']")
	public AndroidElement bagHeaderbtn;

	
	@FindBy(xpath = "(//*[starts-with(@content-desc,'delivery_')]/android.widget.TextView")
	public AndroidElement pincodeHeader;
	
	/************ Getters *********/
	/**
	 * All the Getter methods written below are for the above elements
	 * 
	 */
	public AndroidElement getProductImageView() {
		objAndroidGenericMethods.CheckAndroidElementFound(productImageView, "productImageView");
		return productImageView;
	}

	public AndroidElement getProductImageScroll() {
		objAndroidGenericMethods.CheckAndroidElementFound(productImageScroll, "productImageScroll");
		return productImageScroll;
	}

	public List<AndroidElement> getProductImageView2() {
		return productImageView2;
	}

	public AndroidElement getSimilarIconbtn() {
		objAndroidGenericMethods.CheckAndroidElementFound(similarIconbtn, "similarIconbtn");
		return similarIconbtn;
	}

	public AndroidElement getFirstSimilarProduct() {
		objAndroidGenericMethods.CheckAndroidElementFound(firstSimilarProduct, "firstSimilarProduct");
		return firstSimilarProduct;
	}

	public List<AndroidElement> getProductSizesbtn() {
		return productSizesbtn;
	}

	

	
  
	public AndroidElement getAddToBagbtn() {
		objAndroidGenericMethods.CheckAndroidElementFound(addToBagbtn, "addToBagbtn");
		return addToBagbtn;
	}

	public AndroidElement getSaveToWishlistbtn() {
		objAndroidGenericMethods.CheckAndroidElementFound(saveToWishlistbtn, "saveToWishlistbtn");
		return saveToWishlistbtn;
	}

	public AndroidElement getWishListbtn() {
		objAndroidGenericMethods.CheckAndroidElementFound(wishListbtn, "wishListbtn");
		return wishListbtn;
	}

	public AndroidElement getGoToBagbtn() {
		objAndroidGenericMethods.CheckAndroidElementFound(goToBagbtn, "goToBagbtn");
		return goToBagbtn;
	}

	public AndroidElement getenterpincodebtn() {
		objAndroidGenericMethods.CheckAndroidElementFound(enterpincodebtn, "shoppingBagbtn");
		return enterpincodebtn;
	}

	public AndroidElement getPincodeTxt() {
		objAndroidGenericMethods.CheckAndroidElementFound(pincodeTxt, "pincodeTxt");
		return pincodeTxt;
	}

	public AndroidElement getCheckDeliveryOptionsbtn() {
		objAndroidGenericMethods.CheckAndroidElementFound(checkDeliveryOptionsbtn, "checkDeliveryOptionsbtn");
		return checkDeliveryOptionsbtn;
	}

	public AndroidElement getchangePinCodebtn() {
		objAndroidGenericMethods.CheckAndroidElementFound(changePinCodebtn, "checkPinCodebtn");
		return changePinCodebtn;
	}

	public List<AndroidElement> getPdp_Price() {
		return pdp_Price;
	}

	public AndroidElement getBestPricebtn() {
		objAndroidGenericMethods.CheckAndroidElementFound(BestPricebtn, "BestPricebtn");
		return BestPricebtn;
	}

	public AndroidElement getDonebtn() {
		objAndroidGenericMethods.CheckAndroidElementFound(Donebtn, "Donebtn");
		return Donebtn;
	}

	public AndroidElement getSizeChartbtn() {
		objAndroidGenericMethods.CheckAndroidElementFound(SizeChartbtn, "SizeChartbtn");
		return SizeChartbtn;
	}

	public AndroidElement getCloseSizeChartbtn() {
		objAndroidGenericMethods.CheckAndroidElementFound(CloseSizeChartbtn, "CloseSizeChartbtn");
		return CloseSizeChartbtn;
	}



	public AndroidElement getBagHeaderbtn() {
		objAndroidGenericMethods.CheckAndroidElementFound(bagHeaderbtn, "bagHeaderbtn");
		return bagHeaderbtn;
	}

	/************* Methods **************/

	public void tempProductSize(int index) {
		AndroidElement size = getProductSizesbtn().get(index);
		objAndroidGenericMethods.clickOnAndroidElement(size, "click on size button");

	}

	/**
	 * Method to select a size displayed in product description page;
	 * 
	 * 
	 * NOTE====> Done button is also included in the method;
	 * 
	 * @author 300021278 -Rakesh
	 * 
	 * 
	 */
	public void selectASize() {
		List<AndroidElement> lst = getProductSizesbtn();
		System.out.println("the size of the chart is" + lst.size());
		{

			for (AndroidElement e : lst) {
				List<MobileElement> sizecount = e.findElements(By.xpath("//android.view.ViewGroup"));
				System.out.println("the size of the singlesize is" + sizecount.size());
				if (sizecount.size() == 1) {
					System.out.println("clicked successfully");
					e.click();
					getDonebtn().click();
					break;
				}

			}
			try {
				if (getDonebtn().isDisplayed()) {
					for (AndroidElement e1 : lst) {
						e1.click();
						getDonebtn().click();
						try {
							if (Donebtn.isDisplayed()) {
								System.out.println("Done button displayed");
							} else {
								System.out.println("else part");
								break;
							}

						} catch (Exception e5) {
							System.out.println("In Catch");
							break;
						}
					}

				}
			} catch (Exception e1) {
				
			}

		}

	}
	

	public void productPriceElements(String Price) {
		if (Price.equalsIgnoreCase("SellingPrice")) {
			getPdp_Price().get(1);
		} else if (Price.equalsIgnoreCase("ActualPrice")) {
			getPdp_Price().get(2);
		} else if (Price.equalsIgnoreCase("Discount")) {
			getPdp_Price().get(3);
		}
	}

	public void productImageElements(String ImageViewOptions) {
		if (ImageViewOptions.equalsIgnoreCase("SwipeLeft")) {
			getProductImageView2().get(1);
		} else if (ImageViewOptions.equalsIgnoreCase("Close")) {
			getProductImageView2().get(2);
		} else if (ImageViewOptions.equalsIgnoreCase("SwipeRight")) {
			getProductImageView2().get(3);
		} else if (ImageViewOptions.equalsIgnoreCase("ImageView")) {
			getProductImageView2().get(0);
		}
	}

	public void VerifySearchResult() {
		objAndroidGenericMethods.VerifyStringShouldNotEmpty(element, "Shipping Bag Title");
	}

	public void clickSaveToWishlist() {
		objAndroidGenericMethods.clickOnAndroidElement(getSaveToWishlistbtn(), "click on saveToWishlist button");
	}

	public void EnterPinValue(String pinCodeValue) {
		getPincodeTxt().clear();
		getPincodeTxt().sendKeys(pinCodeValue);

	}

	public void checkDeliveryOptionsbtn() {
		objAndroidGenericMethods.clickOnAndroidElement(getCheckDeliveryOptionsbtn(), "click on checkDeliveryOption");
	}

	public void clickSimilarIconbtn() throws InterruptedException {
		objAndroidGenericMethods.clickOnAndroidElement(getSimilarIconbtn(), "click on similarIcon");
		//Thread.sleep(5000);
	}

	public void clickGoToBag() throws InterruptedException {
		objAndroidGenericMethods.clickOnAndroidElement(getGoToBagbtn(), "click on goToBag button");
	//	Thread.sleep(5000);

	}

	public void clickFirstSimilarProduct() {
		objAndroidGenericMethods.clickOnAndroidElement(getFirstSimilarProduct(), "click on firstSimilarProduct button");
	}

	

	public void clickAddToBagbtn() {
		objAndroidGenericMethods.clickOnAndroidElement(getAddToBagbtn(), "click on addToBag button");
	}

	public void clickWishListbtn() {
		objAndroidGenericMethods.clickOnAndroidElement(getWishListbtn(), "click on WishList button");
	} 


	public void clickEnterPincodebtn() {
		objAndroidGenericMethods.clickOnAndroidElement(getenterpincodebtn(), "click on pincode button");
	}

	public void clickPincodeTxt(String PincodeValue) {
		objAndroidGenericMethods.enterTexAndroidElement(getPincodeTxt(), PincodeValue, "entered the pincode");
	}

	public void clickBestPricebtn() {
		objAndroidGenericMethods.clickOnAndroidElement(getBestPricebtn(), "click on BestPrice button");
	}

	public void clickDonebtn() {
		objAndroidGenericMethods.clickOnAndroidElement(getDonebtn(), "click on Done button");
	}

	public void clickSizeChartbtn() {
		objAndroidGenericMethods.clickOnAndroidElement(getSizeChartbtn(), "click on SizeChartbtn button");
	}

	public void clickCloseSizeChartbtn() {
		objAndroidGenericMethods.clickOnAndroidElement(getCloseSizeChartbtn(), "click on CloseSizeChart button");
	}

	public void clickbagHeaderbtn() {
		objAndroidGenericMethods.clickOnAndroidElement(getBagHeaderbtn(), "click on bagHeader button");
	}

	public void clicklHBagbtn() {
		objAndroidGenericMethods.clickOnAndroidElement(getBagHeaderbtn(), "click on lHbag button");
	}
	/**
	 * this method help to click on Chnage pincode button on PDP page
	 * @author 300021282 VINAY
	 */
	public void clickChangePinCodebtn() {
		objAndroidGenericMethods.clickOnAndroidElement(getchangePinCodebtn(), "change pincode");
	}
/**
 * method help to identify whether check delivery options are added or not
 * Note:==> This method includes scroll method also.
 * @author 300021282 VINAY
 */
	public void CheckDeliverOption(String pinCodeValue) {
		try {
			objAndroidGenericMethods.scrolltoElementAndClick("down",pincodeHeader, 5000);
			//clickEnterPincodebtn();
			EnterPinValue(pinCodeValue);
			checkDeliveryOptionsbtn();
		} catch (Exception e) {
			clickChangePinCodebtn();
			EnterPinValue(pinCodeValue);
			checkDeliveryOptionsbtn();
			
		}
		
	}
	public void bottomTopSwipe(int duration) {
		Dimension size = aDriver.manage().window().getSize();
		System.out.println("Dimensions of the screen" + size);
		int starty = (int) (size.height * 0.50);
		int endy = (int) (size.height * 0.20);
		int width = size.width / 2;
		new TouchAction(aDriver).press(width, starty).waitAction(Duration.ofMillis(duration)).moveTo(width, endy)
				.release().perform();
	}

	public void scrollIntoView(AndroidDriver<AndroidElement> aDriver, AndroidElement element) {
		((JavascriptExecutor) aDriver).executeScript("arguments[0].scrollIntoView(true);", element);
	}

	/************ Assertion *************/

	// SELECT SIZE
	@FindBy(xpath = "//android.widget.TextView[@text ='Select Size']")
	public AndroidElement size_selector;

	// PDP title
	@FindBy(xpath = "//*[@content-desc ='product_head']/*/android.widget.TextView" )
	public AndroidElement pdpTitle;

	/**
	 * author : Sneha
	 */
	@FindBy(xpath = "//*[@content-desc ='tap_for_best_price' or @text ='Tap for best price']")
	public AndroidElement best_Price;

	// ProductCode

	@FindBy(xpath = "//android.widget.TextView[@text='Product Code: 2194304']")
	public AndroidElement productCode;

	
	public AndroidElement getProductCode() {
		return productCode;
	}

	public AndroidElement getSize_selector() {
		return size_selector;
	}

	public void verifySize_selector(String ExpectedData) {
		objAndroidGenericMethods.VerifyStringShouldNotEmpty(size_selector, "SizeSelector");
	}

	public AndroidElement getPdpTitle() {
		return pdpTitle;
	}

	public void verifyPdpTitle(String ExpectedData) {
		objAndroidGenericMethods.VerifyStringShouldNotEmpty(pdpTitle, "PdpTitle");
	}

	public AndroidElement getBest_Price() {
		return best_Price;
	}

	public void verifyBestPrice(String ExpectedData) {
		objAndroidGenericMethods.VerifyStringShouldNotEmpty(best_Price, "Best Price");
	}

	@FindAll({ @FindBy(xpath = "//*[@content-desc='pdp_price']/*") })
	public List<AndroidElement> productdetailelements;

	public List<AndroidElement> getProductdetailelements() {
		return productdetailelements;
	}

	public void assertProductPrice() {
		productElement = getProductdetailelements().get(0);
		pdpProductPrice = getProductdetailelements().get(0).getText();
		objAndroidGenericMethods.VerifyStringShouldNotEmpty(productElement, "product price");
	}

	public void assertProductDiscount() {
		productElement = getProductdetailelements().get(2);
		pdpProductDiscount = getProductdetailelements().get(2).getText();
		objAndroidGenericMethods.VerifyStringShouldNotEmpty(productElement, "product discount");
	}

	public void assertProductStrikedPrice() {
		productElement = getProductdetailelements().get(1);
		pdpProductPrice = getProductdetailelements().get(1).getText();
		objAndroidGenericMethods.VerifyStringShouldNotEmpty(productElement, "Strikedprice");
	}

	public void verifyProductCode() {
		objAndroidGenericMethods.VerifyStringShouldNotEmpty(productCode, "Product code found Successfully");
	}
	/**
	 * @author 300021280 Sneha
	 * method to scroll and click on Tap for best price btn
	 */
	public void scrollToBestPriceNClick() {
		MobileElement element = aDriver.findElement(MobileBy.AndroidUIAutomator(
				"new UiScrollable(new UiSelector().className(\"android.widget.ScrollView\")).getChildByText("
				+ "new UiSelector().className(\"android.widget.TextView\"), \"Tap for best price\")"));
		element.click();
	}
	/**
	 * @author 300021280 Sneha
	 * method to scroll and click on Size Chart
	 */
	public void scrollToSizeChartNClick() {
		MobileElement element = aDriver.findElement(MobileBy.AndroidUIAutomator(
				"new UiScrollable(new UiSelector().className(\"android.widget.ScrollView\")).getChildByText("
				+ "new UiSelector().className(\"android.widget.TextView\"), \"SIZE CHART\")"));
		element.click();
	}
}
