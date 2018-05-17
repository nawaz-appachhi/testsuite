package com.automation.web.ObjectRepository.Pages.PDP;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;

import com.automation.core.web.GenericMethods;
import com.automation.web.ObjectRepository.Pages.PLP.PLPPageObject;

/**
 * @author 300019227 - Anu
 *
 */
public class PDPObject extends GenericMethods {

	public WebElement element = null;

	public List<WebElement> elements;

	GenericMethods objGenericMethods;
	PLPPageObject objPLPPageObject;

	/**
	 * @param driver
	 * Modified by : Neeraj
	 * Description : corrected spelling mistake 
	 */

	public PDPObject(WebDriver driver) {
		super();
		PageFactory.initElements(driver, this);
		objGenericMethods = new GenericMethods(driver);
		objPLPPageObject = new PLPPageObject(driver);
	}

	@FindAll(@FindBy(xpath = "//div[@class='thumbnails-vertical-container']/button[@class='thumbnails-thumbnail-button']"))
	public List<WebElement> AllSmallThumbnails;

	@FindBy(xpath = "//img[@class='thumbnails-selected-image']")
	public WebElement LargeThumbnail;

	@FindBy(className = "desktop-image-zoom-close desktop-image-zoom-icon")
	public WebElement closeImage;

	@FindBy(className = "pdp-title")
	public WebElement PdpTitle;

	@FindBy(className = "supplier-product-code")
	public WebElement productCode;

	@FindBy(xpath = "//span[@class='supplier-supplier']/span/span[@class='supplier-productSellerName']")
	public WebElement sellerName;

	@FindBy(xpath = "//div[@class='pdp-description-container']/div[@class='pdp-price-info']/p[@class='pdp-selling-price']/strong[@class='pdp-price']")
	public WebElement SellingPrice;

	@FindBy(xpath = "//div[@class='pdp-description-container']/div[@class='pdp-price-info']/p[@class='pdp-discount-container']/s")
	public WebElement ActualPrice;

	@FindBy(xpath = "//div[@class='pdp-description-container']/div[@class='pdp-price-info']/p[@class='pdp-discount-container']/span")
	public WebElement DiscountPrice;

	@FindBy(className = "pdp-offers-moreOffersButton")
	public WebElement BestOfferlink;

	@FindBy(xpath = "//button[@class='pdp-add-to-wishlist pdp-button pdp-add-to-wishlist pdp-button']/span[2]")
	public WebElement SaveBtn;

	@FindBy(className = "size-buttons-show-size-chart")
	public WebElement SizeChartlink;

	@FindBy(xpath = "//button[contains(@class,'size-buttons-size-button-default')and not(contains(@class,'disabled'))][1]")
	public WebElement SizeButtons;

	@FindBy(xpath = "//span[text()='ADD TO BAG']")
	public WebElement AddToBagBtn;

	@FindBy(xpath = "//span[text()='GO TO BAG']")
	public WebElement goToCartBtn;

	@FindBy(className = "pincode-button")
	public WebElement checkDeliveryOptionsLink;

	@FindBy(className = "pincode-code")
	public WebElement enterPincodeTextbox;

	@FindBy(xpath = "//input[@type='submit']")
	public WebElement checkPinCodeLink;

	@FindBy(className = "similar-heading")
	public WebElement SimilarProductsHeader;

	@FindAll(@FindBy(className = "product-item-image"))
	public List<WebElement> SimilarProductsList;

	@FindBy(xpath = "//*[@class='sizeChartWeb-header']/button")
	public WebElement CloseSizeChartBtn;

	@FindBy(id = "cm")
	public WebElement CentiMeterBtn;

	@FindBy(id = "inch")
	public WebElement InchBtn;
	
	/**
	 * author-shivaprasad
	 * 
	 */
	@FindBy(xpath = "//p[@class='pincode-error pincode-enterPincode']")
	public WebElement CheckDeliveryMessage;

	
	@FindBy(xpath = "//button[text()='Check Another Pin Code']")
	public WebElement CheckAnotherPinCodeBtn;

	@FindBy(xpath = "//h4[@class='size-buttons-select-size']")
	public WebElement SelectSizeHeader;

	@FindBy(xpath = "//span[@class='myntraweb-sprite sizeChartWeb-modalclose sprites-modalclose']")
	public WebElement closeSizeChart;

	@FindBy(xpath = "(//button[contains(@class,'size-buttons-size-button') and contains(@class,'size-buttons-size-button-default') and contains(@class,'size-buttons-big-size') and not(contains(@class,'size-buttons-size-button-disabled'))])[1]")
	public WebElement firstAvailableSize;
	/**
	 * @Author: Aishurya
	 * Reason:Added size count to handele product with single size
	 */
	
	/**
	 * @author Neeraj
	 * Description: Object for Success Message when user add to bag
	 */
	@FindBy(className = "notify-container")
	public WebElement addToBagSuccessMessage;
	
	
	/**
	 * @author Neeraj
	 * Description: Getter method of addToBagSuccessMessage
	 */
	public WebElement getAddToBagSuccessMessage() {
		objGenericMethods.CheckWebElementFound(addToBagSuccessMessage, "addToBagSuccessMessage");
		return addToBagSuccessMessage;
	}
	
	public WebElement getCheckDeliveryMessage() {
		objGenericMethods.CheckWebElementFound(CheckDeliveryMessage, "CheckDeliveryMessage");
		return CheckDeliveryMessage;
	}

	

	@FindAll({@FindBy(xpath = "//*[@class='size-buttons-size-buttons']/button")})
	public List<WebElement> SizeCount;

	public List<WebElement> getSizeCount() {
		return SizeCount;
	}

	public WebElement getFirstAvailableSize() {
		objGenericMethods.CheckWebElementFound(firstAvailableSize, "firstAvailableSize");
		return firstAvailableSize;
	}

	public WebElement getCloseSizeChart() {
		objGenericMethods.CheckWebElementFound(closeSizeChart, "closeSizeChart");
		return closeSizeChart;
	}

	public WebElement getSelectSizeHeader() {
		objGenericMethods.CheckWebElementFound(SelectSizeHeader, "SelectSizeHeader");
		return SelectSizeHeader;
	}

	public WebElement getCloseSizeChartBtn() {
		objGenericMethods.CheckWebElementFound(CloseSizeChartBtn, "CloseSizeChartBtn");
		return CloseSizeChartBtn;
	}

	public WebElement getCentiMeterBtn() {
		objGenericMethods.CheckWebElementFound(CentiMeterBtn, "CentiMeterBtn");
		return CentiMeterBtn;
	}

	public WebElement getInchBtn() {
		objGenericMethods.CheckWebElementFound(CentiMeterBtn, "CentiMeterBtn");
		return InchBtn;
	}

	public List<WebElement> getAllSmallThumbnails() {
		objGenericMethods.CheckWebElementsListFound(AllSmallThumbnails, "AllSmallThumbnails");
		return AllSmallThumbnails;
	}

	public WebElement getLargeThumbnail() {
		objGenericMethods.CheckWebElementFound(LargeThumbnail, "LargeThumbnail");
		return LargeThumbnail;
	}

	public WebElement getCloseImage() {
		objGenericMethods.CheckWebElementFound(closeImage, "closeImage");
		return closeImage;
	}

	public WebElement getPdpTitle() {
		objGenericMethods.CheckWebElementFound(PdpTitle, "PdpTitle");
		return PdpTitle;
	}

	public WebElement getProductCode() {
		objGenericMethods.CheckWebElementFound(productCode, "productCode");
		return productCode;
	}

	public WebElement getSellerName() {
		objGenericMethods.CheckWebElementFound(sellerName, "sellerName");
		return sellerName;
	}

	public WebElement getSellingPrice() {
		objGenericMethods.CheckWebElementFound(SellingPrice, "SellingPrice");
		return SellingPrice;
	}

	public WebElement getActualPrice() {
		objGenericMethods.CheckWebElementFound(ActualPrice, "ActualPrice");
		return ActualPrice;
	}

	public WebElement getDiscountPrice() {
		objGenericMethods.CheckWebElementFound(DiscountPrice, "DiscountPrice");
		return DiscountPrice;
	}

	public WebElement getBestOfferlink() {
		objGenericMethods.CheckWebElementFound(BestOfferlink, "BestOfferlink");
		return BestOfferlink;
	}

	public WebElement getSaveBtn() {
		objGenericMethods.CheckWebElementFound(SaveBtn, "SaveBtn");
		return SaveBtn;
	}

	public WebElement getSizeChartlink() {
		objGenericMethods.CheckWebElementFound(SizeChartlink, "SizeChartlink");
		return SizeChartlink;
	}

	public WebElement getSizeButtons() {
		objGenericMethods.CheckWebElementFound(SizeButtons, "SizeButtons");
		return SizeButtons;
	}

	public WebElement getAddToBagBtn() {
		objGenericMethods.CheckWebElementFound(AddToBagBtn, "AddToBagBtn");
		return AddToBagBtn;
	}

	public WebElement getGoToCartBtn() {
		objGenericMethods.CheckWebElementFound(goToCartBtn, "goToCartBtn");
		return goToCartBtn;
	}

	public WebElement getCheckAnotherPinCodeBtn() {
		return CheckAnotherPinCodeBtn;
	}

	public WebElement getCheckDeliveryOptionsLink() {
		objGenericMethods.CheckWebElementFound(checkDeliveryOptionsLink, "checkDeliveryOptionsLink");
		return checkDeliveryOptionsLink;
	}

	public WebElement getEnterPincodeTextbox() {
		objGenericMethods.CheckWebElementFound(enterPincodeTextbox, "enterPincodeTextbox");
		return enterPincodeTextbox;
	}

	public WebElement getCheckPinCodeLink() {
		objGenericMethods.CheckWebElementFound(checkPinCodeLink, "checkPinCodeLink");
		return checkPinCodeLink;
	}

	public WebElement getSimilarProductsHeader() {
		objGenericMethods.CheckWebElementFound(SimilarProductsHeader, "SimilarProductsHeader");
		return SimilarProductsHeader;
	}

	public List<WebElement> getSimilarProductsList() {
		objGenericMethods.CheckWebElementsListFound(SimilarProductsList, "SimilarProductsList");
		return SimilarProductsList;
	}

	/**
	 * 
	 * ModifiedBy : Neeraj
	 * Description: Removed Thread.sleep and added WaitDriverWhenReady()
	 */
	public void HoverAllSmallThumbnails() {
		elements = getAllSmallThumbnails();
		for (WebElement image : elements) {
			objGenericMethods.HoverOnWebElement(image);
			objGenericMethods.waitDriverWhenReady(image, "Thumb Image");
		}
	}

	public void ClickOnLargeThumbnail() {
		objGenericMethods.takeSnapShot();
		getLargeThumbnail().click();
		objGenericMethods.ReportClickEvent("LargeThumbnail");

		objGenericMethods.takeSnapShot();
		getCloseImage().click();
		objGenericMethods.ReportClickEvent("closeImage");
	}

	/**
	 * 
	 * ModifiedBy : Neeraj
	 * Description: Removed Thread.sleep and added WaitDriverWhenReady()
	 */
	public void ClickOnBestOfferLink() {
		objGenericMethods.takeSnapShot();
		getBestOfferlink().click();
		objGenericMethods.ReportClickEvent("BestOffer");
	}

	public void ClickOnSaveBtn() {
		objGenericMethods.takeSnapShot();
		getSaveBtn().click();
		objGenericMethods.ReportClickEvent("SaveBtn");
	}

	/**
	 * 
	 * ModifiedBy : Neeraj
	 * Description: Removed Thread.sleep and added WaitDriverWhenReady()
	 */
	public void ClickOnSizeChartLink() {
		objGenericMethods.takeSnapShot();
		getSizeChartlink().click();
		objGenericMethods.ReportClickEvent("SizeChart");

		objGenericMethods.waitDriverWhenReady(getCentiMeterBtn(), "CentiMeterBtn");
		objGenericMethods.takeSnapShot();
		getCentiMeterBtn().click();
		objGenericMethods.ReportClickEvent("CentiMeterBtn");

	//	objGenericMethods.waitDriverWhenReady(getInchBtn(), "InchBtn");
		objGenericMethods.takeSnapShot();
		getInchBtn().click();
		objGenericMethods.ReportClickEvent("InchBtn");

	//	objGenericMethods.waitDriverWhenReady(getInchBtn(), "InchBtn");
		objGenericMethods.takeSnapShot();
		getCloseSizeChartBtn().click();
		objGenericMethods.ReportClickEvent("CloseSizeChartBtn");
	}

	/**
	 * Modified By: Aishurya Reason : Added condition not to select size,
	 * If product has one size.
	 * 
	 * 
	 * ModifiedBy : Neeraj
	 * Description: added WaitDriverWhenReady()
	 */
	public void ClickSizeButtons() {
		if (getSizeCount().size() > 1) {
			objGenericMethods.waitDriverWhenReady(getSizeButtons(), "Size Round Button");
			getSizeButtons().click();
		}
	}

	/**
	 * 
	 * ModifiedBy : Neeraj
	 * Description: Removed Thread.sleep and added WaitDriverWhenReady()
	 */
	public void ClickAddToBagBtn()  {
		objGenericMethods.takeSnapShot();
		objGenericMethods.waitDriverWhenReady(getAddToBagBtn(), "Add to Bag Button");
		getAddToBagBtn().click();
		objGenericMethods.ReportClickEvent("AddToBagBtn");
	}

	public void ClickGoToBagBtn() {
		objGenericMethods.takeSnapShot();
		getGoToCartBtn().click();
		objGenericMethods.ReportClickEvent("goToCartBtn");
	}

	/**
	 * 
	 * ModifiedBy : Neeraj
	 * Description: Removed Thread.sleep and added WaitDriverWhenReady()
	 */
	public void ClickCheckDeliveryOptionsLink() {
		objGenericMethods.takeSnapShot();
		getCheckDeliveryOptionsLink().click();
		objGenericMethods.ReportClickEvent("checkDeliveryOptionsLink");
	}

	public void EnterPincode(String pin) {
		getEnterPincodeTextbox().sendKeys(pin);
	}
	
	public void ClickCheckPinCodeLink() {
		objGenericMethods.takeSnapShot();
		getCheckPinCodeLink().click();
		objGenericMethods.ReportClickEvent("checkPinCodeLink");
	}

	public void ClickAnotherPinDelivery() {
		objGenericMethods.takeSnapShot();
		getCheckAnotherPinCodeBtn().click();
		objGenericMethods.ReportClickEvent("CheckAnotherPINcode");

	}

	public void ClickSimilarProduct() {
		elements = getSimilarProductsList();
		for (int i = 0; i <= elements.size(); i++) {
			objGenericMethods.takeSnapShot();
			elements.get(i).click();
			objGenericMethods.ReportClickEvent("SimilarProductsList");
		}
	}

	public void ClickFirstAvailableSize() {
		getFirstAvailableSize().click();
	}

	public void VerifySellingPriceFromPDP() {
		objGenericMethods.VerifyStringShouldNotEmpty(getSellingPrice(), "selling price");
	}

	public void verifyPDPTitleFromPDP() {
		objGenericMethods.VerifyStringShouldNotEmpty(getPdpTitle(), "Product Title from PDP");
	}

	public void VerifyDiscountPercentageFromPDP() {
		objGenericMethods.VerifyStringShouldNotEmpty(getDiscountPrice(), " product discount");
	}

	/**
	 * Modified by : Anu
	 * Replaced 'verifyTwoString()' with 'Verifystringshouldnotbeempty()'
	 */
	public void VerifyActualPriceFromPDP() {
		objGenericMethods.VerifyStringShouldNotEmpty(getActualPrice(), "Actual price");

	}

	public void VerifyProductCodefromPDP(String testproductcode) {
		objGenericMethods.VerifyStringShouldNotEmpty(getProductCode(), "Product code");
	}

	public boolean IsBestOfferLinkPresent() {
		try {
			getBestOfferlink().getText();
			return true;
		} catch (Exception e) {
			System.out.println("Best Offer link is not available");
			return false;
		}
	}

	public void VerifyBestOfferLinkFromPDP() {
		if (IsBestOfferLinkPresent()) {
			objGenericMethods.VerifyStringShouldNotEmpty(getBestOfferlink(), "Best Offer Link");
		}
	}

	public void VerifySelectSizeHeaderFromPDP() {
		objGenericMethods.VerifyStringShouldNotEmpty(getSelectSizeHeader(), "Select Size Header");
	}

	public boolean IsSimilarProductsPresent() {
		try {
			getSimilarProductsHeader().getText();
			return true;
		} catch (Exception e) {
			System.out.println("Similar Products are not available");
			return false;
		}
	}

	/**
	 * Modified By: Neeraj Reason : IsSimilarProductsPresent() not required to write
	 * as object check in getter method itself
	 */
	public void VerifySimilarProductsHeaderFromPDP() {
		// if (IsSimilarProductsPresent())
		objGenericMethods.VerifyStringShouldNotEmpty(getSimilarProductsHeader(), "Similar Products Header");
	}

	public void VerifyLargeThumbnailFromPDP() {
		getLargeThumbnail();
	}

	public void VerifySmallThumbnailsFromPDP() {
		getAllSmallThumbnails();
	}
public void readPlpData(){
		System.out.println(objPLPPageObject.ReadProductCodeOfFirstItem().PRODUCT_CODE);
}

	public void VerifySellingPrice(WebElement ExpectedProductElement) {
		String Expected = ExpectedProductElement.getText();
		String Actual = getSellingPrice().getText();
		objGenericMethods.verifyTwoStringData(Expected, Actual);
	}

	public void VerifyActualPrice(WebElement ExpectedProductElement) {
		String Expected = ExpectedProductElement.getText();
		String Actual = getActualPrice().getText();
		objGenericMethods.verifyTwoStringData(Expected, Actual);
	}

	public void VerifyDiscountPercentage(WebElement ExpectedProductElement) {
		String Expected = ExpectedProductElement.getText();
		String Actual = getDiscountPrice().getText();
		objGenericMethods.verifyTwoStringData(Expected, Actual);
	}

	public void clickCloseSizeChart() {
		objGenericMethods.takeSnapShot();
		getCloseSizeChart().click();
		objGenericMethods.ReportClickEvent("SizeChartButton");
	}
	
	/**
	 * Created by : Anu
	 * Select size from list of sizes available
	 * 
	 */
	public void SelectSize()
	{
		List<WebElement> sizebuttons=getSizeCount();
		for(int i =0;i<=getSizeCount().size();i++)
		{
			getSizeCount().get(i).click();
			break;
		}
	}
	public void VerifyCheckDelivaryOption() {
		try {
			String message = getCheckDeliveryMessage().getText();
			Reporter.log("Failed :: '" + message );
		} catch (Exception e) {
			Reporter.log("Passed :: '" + "Item is deliverable to the entered pin code" );
		}
	}
}
