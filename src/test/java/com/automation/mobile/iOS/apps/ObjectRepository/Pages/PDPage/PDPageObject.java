package com.automation.mobile.iOS.apps.ObjectRepository.Pages.PDPage;

import java.time.Duration;
import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.touch.TouchActions;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

import com.automation.core.mobile.iOS.iOSGenericMethods;

import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

import io.appium.java_client.pagefactory.iOSFindBy;

public class PDPageObject {

	public IOSDriver<IOSElement> iDriver;
	public WebDriverWait wait;
	iOSGenericMethods objiOSGenericMethods;

	public PDPageObject(IOSDriver<IOSElement> iDriver) {
		PageFactory.initElements(new AppiumFieldDecorator(iDriver), this);
		objiOSGenericMethods = new iOSGenericMethods(iDriver);
	}

	/**
	 * @author 300021275 - xpath changed by accessibility id
	 * 
	 *         Old Locator - xpath =
	 *         "((//XCUIElementTypeOther[@name=\"tap_for_best_price\"])[3]"
	 */
	@iOSFindBy(accessibility = "tap_for_best_price")
	public IOSElement selectBestPrice;

	@FindAll({ @FindBy(xpath = "(//XCUIElementTypeOther[@name=\"size_selector\"])[1]") })
	public List<IOSElement> SizeButtons;

	@iOSFindBy(accessibility = "buy_button")
	public IOSElement goToBag;

	@FindAll({ @FindBy(xpath = "//XCUIElementTypeOther[@name='size_selector'])[2]") })
	public List<IOSElement> productSize;

	@iOSFindBy(accessibility = "save_button")
	public IOSElement saveButton;

	@iOSFindBy(accessibility = "save_button")
	public IOSElement saveButton1;

	@iOSFindBy(xpath = "//XCUIElementTypeNavigationBar[@name='Featured']/XCUIElementTypeButton[2]")
	public IOSElement moveToBag;

	@iOSFindBy(accessibility = "buy_button")
	public IOSElement addToBag;

	@iOSFindBy(xpath = "//XCUIElementTypeButton[@name='OK']")
	public IOSElement OK;
	/**
	 * @author 300021275 - xpath changed by accessibility id
	 * 
	 *         Old Locator - xpath =
	 *         "//XCUIElementTypeOther[@name=\"delivery_options\"]"
	 */

	@iOSFindBy(accessibility = "delivery_options")
	public IOSElement enterPinCode;

	/**
	 * @author 300021275 - xpath changed by accessibility id
	 * 
	 *         Old Locator - xpath = "//XCUIElementTypeOther[@name=\"complete_look
	 *         \"]"
	 */

	@iOSFindBy(accessibility = "complete_look")
	public IOSElement checkPinCode;

	@iOSFindBy(xpath = "//XCUIElementTypeNavigationBar[@name='Featured']/XCUIElementTypeButton[1]")
	public IOSElement back;

	@iOSFindBy(xpath = "//XCUIElementTypeNavigationBar[@name=\"Featured\"]/XCUIElementTypeButton[2]")
	public IOSElement viewMyBag;

	@iOSFindBy(xpath = "//XCUIElementTypeNavigationBar[@name=\"Featured\"]/XCUIElementTypeButton[3]")
	public IOSElement viewWishList;

	@iOSFindBy(accessibility = "MOVE TO WISHLIST")
	public IOSElement moveToWishList;

	@iOSFindBy(accessibility = "tap_for_best_price")
	public IOSElement bestPrice;
	/*
	 * modsified by madhu for nike bottles
	 */
	// @iOSFindBy(xpath = "//XCUIElementTypeOther[@name=\"similar_link_0\"]")
	@iOSFindBy(accessibility = "similar_link_0")
	public IOSElement viewSimilar;

	@iOSFindBy(accessibility = "similar_link_1")
	public IOSElement viewMoreSimilar;

	@iOSFindBy(accessibility = "best_price_view")
	public List<IOSElement> bestPriceList;

	@iOSFindBy(accessibility = "similar_link_0")
	public IOSElement SimilarProduct;

	@iOSFindBy(accessibility = "delivery_options")
	public List<IOSElement> deliveryOptionsList;
	/*
	 * @Owner: Madhu
	 * 
	 * @Modified: renamed savetag has wishlistTag
	 */
	@iOSFindBy(xpath = "//XCUIElementTypeNavigationBar[@name=\"Featured\"]/XCUIElementTypeButton[3]")
	public IOSElement wishlistTag;

	@iOSFindBy(className = "similar_product_component")
	public List<IOSElement> similarProcuctList;

	@iOSFindBy(accessibility = "nav search")
	public IOSElement searchPDP;

	@iOSFindBy(xpath = "//XCUIElementTypeButton[@name='OK']")
	public IOSElement tapAndHold;

	@iOSFindBy(accessibility = "product_head")
	public List<IOSElement> sizeList;

	@iOSFindBy(accessibility = "size_list")
	public List<IOSElement> selectSize;

	/**
	 * @author 300019221 / Aravindanath
	 * 
	 */
	@iOSFindBy(accessibility = "size_list")
	public IOSElement selectSizes;

	@iOSFindBy(className = "XCUIElementTypeButton")
	public IOSElement BackButtonOnSizeChart;

	@iOSFindBy(className = "XCUIElementTypeOther")
	public List<IOSElement> moveToWIshlistSize;

	/*
	 * size Chart Name: Monika
	 * 
	 */

	@iOSFindBy(accessibility = "SIZE CHART")
	public IOSElement SizeChart;

	// @iOSFindBy(accessibility = "")
	@iOSFindBy(xpath = "//XCUIElementTypeButton")
	public IOSElement SizeChartBackButton;
	// size_list

	@iOSFindBy(accessibility = "buy_done_button")
	public IOSElement doneButton;

	@iOSFindBy(accessibility = "similar_link_1")
	public IOSElement moreSimilarProducts;

	@FindAll({ @FindBy(className = "XCUIElementTypeButton") })
	public List<WebElement> topButtons;

	/*********** getters *********/
	public IOSElement getSizeChart() {
		objiOSGenericMethods.CheckIOSElementFound(SizeChart, "SizeChart");
		return SizeChart;
	}

	public IOSElement getSizeChartBackButton() {
		objiOSGenericMethods.CheckIOSElementFound(SizeChartBackButton, "SizeChartBackButton");
		return SizeChartBackButton;
	}

	public IOSElement getSelectBestPrice() {
		objiOSGenericMethods.CheckIOSElementFound(selectBestPrice, "selectBestPrice");
		return selectBestPrice;
	}

	public List<IOSElement> getMoveToWIshlistSize() {
		objiOSGenericMethods.CheckIOSElementsListFound(moveToWIshlistSize, "moveToWIshlistSize");
		return moveToWIshlistSize;
	}

	public List<IOSElement> getSizeButtons() {
		objiOSGenericMethods.CheckIOSElementsListFound(SizeButtons, "SizeButtons");
		return SizeButtons;
	}

	public IOSElement getGoToBag() throws InterruptedException {
		objiOSGenericMethods.CheckIOSElementFound(goToBag, "goToBag");
		return goToBag;
	}

	public IOSElement getSimilarProduct() {
		objiOSGenericMethods.CheckIOSElementFound(SimilarProduct, "SimilarProduct");
		return SimilarProduct;
	}

	public List<IOSElement> getProductSize() {
		objiOSGenericMethods.CheckIOSElementsListFound(productSize, "productSize");
		return productSize;
	}

	public IOSElement getSaveButton() {
		objiOSGenericMethods.CheckIOSElementFound(saveButton, "saveButton");
		return saveButton;
	}

	public IOSElement getMoreSimilarProducts() {
		objiOSGenericMethods.CheckIOSElementFound(moreSimilarProducts, "saveButton1");
		return moreSimilarProducts;
	}

	public IOSElement getMoveToBag() {
		objiOSGenericMethods.CheckIOSElementFound(moveToBag, "moveToBag");
		return moveToBag;
	}

	public IOSElement getAddToBag() {
		objiOSGenericMethods.CheckIOSElementFound(addToBag, "addToBag");
		return addToBag;
	}

	public IOSElement getOK() {
		objiOSGenericMethods.CheckIOSElementFound(OK, "OK");
		return OK;
	}

	public IOSElement getEnterPinCode() {
		objiOSGenericMethods.CheckIOSElementFound(enterPinCode, "enterPinCode");
		return enterPinCode;
	}

	public IOSElement getCheckPinCode() {
		objiOSGenericMethods.CheckIOSElementFound(checkPinCode, "checkPinCode");
		return checkPinCode;
	}

	public IOSElement getBack() {
		objiOSGenericMethods.CheckIOSElementFound(back, "back");
		return back;
	}

	public IOSElement getViewMyBag() {
		objiOSGenericMethods.CheckIOSElementFound(viewMyBag, "viewMyBag");
		return viewMyBag;
	}

	public IOSElement getViewWishList() {
		objiOSGenericMethods.CheckIOSElementFound(viewWishList, "viewWishList");
		return viewWishList;
	}

	public IOSElement getMoveToWishList() {
		objiOSGenericMethods.CheckIOSElementFound(moveToWishList, "moveToWishList");
		return moveToWishList;
	}

	public IOSElement getBestPrice() {
		objiOSGenericMethods.CheckIOSElementFound(bestPrice, "bestPrice");
		return bestPrice;
	}

	public IOSElement getViewSimilar() {
		objiOSGenericMethods.CheckIOSElementFound(viewSimilar, "viewSimilar");
		return viewSimilar;
	}

	public IOSElement getViewMoreSimilar() {
		objiOSGenericMethods.CheckIOSElementFound(viewMoreSimilar, "viewSimilar");
		return viewMoreSimilar;
	}

	public List<IOSElement> getDeliveryOptionsList() {
		objiOSGenericMethods.CheckIOSElementsListFound(deliveryOptionsList, "deliveryOptionsList");
		return deliveryOptionsList;
	}

	public IOSElement getWishlistTag() {
		objiOSGenericMethods.CheckIOSElementFound(wishlistTag, "savetag");
		return wishlistTag;
	}

	public List<IOSElement> getSimilarProcuctList() {
		objiOSGenericMethods.CheckIOSElementsListFound(similarProcuctList, "similarProcuctList");
		return similarProcuctList;
	}

	public IOSElement getSearchPDP() {
		objiOSGenericMethods.CheckIOSElementFound(searchPDP, "searchPDP");
		return searchPDP;
	}

	public IOSElement getTapAndHold() {
		objiOSGenericMethods.CheckIOSElementFound(tapAndHold, "tapAndHold");
		return tapAndHold;
	}

	public List<IOSElement> getSizeList() {
		objiOSGenericMethods.CheckIOSElementsListFound(sizeList, "sizeList");
		return sizeList;
	}

	public IOSElement getdoneButton() {
		objiOSGenericMethods.CheckIOSElementFound(doneButton, "Done Button");
		return doneButton;
	}

	public List<IOSElement> getSelectSize() {
		objiOSGenericMethods.CheckIOSElementsListFound(selectSize, "selectSize");
		return selectSize;
	}

	public IOSElement getBackButtonOnSizeChart() {
		objiOSGenericMethods.CheckIOSElementFound(BackButtonOnSizeChart, "Back Button On Size Chart");
		return BackButtonOnSizeChart;
	}

	/*************** methods ***************/

	public void clickOnSelectBestPrice() {
		objiOSGenericMethods.clickOnIOSElement(getSelectBestPrice(), "Tap For Best Price button");
	}

	// selecting the size
	public void enterProductSizeElements(String Size) {
		int size = getProductSize().size();
		for (int i = 0; i < size; i++) {
			if ((getProductSize().get(i).isEnabled())) {
//				(getProductSize()).get(i).click();
				objiOSGenericMethods.clickOnIOSElement(getProductSize().get(i), "Tap For Best Price button");
				break;

			}
		}
	}

	public void clickOnSize() {
		System.out.println(getSizeButtons().size());
		System.out.println(getSizeButtons().get(1).getText());
//		SizeButtons.get(0).click();
		objiOSGenericMethods.clickOnIOSElement(getSizeButtons().get(0), "Tap For Best Price button");
	}

	public void clickOnSaveButton() {
		objiOSGenericMethods.clickOnIOSElement(getSaveButton(), "Save Button");
	}

	/**
	 * @author 300019221 Replaced thread.sleep with webdriver wait
	 * @throws InterruptedException
	 * @author 300019221 Added try block
	 */
	public void clickOnSizeChart() throws InterruptedException {
		try {
			// objiOSGenericMethods.waitForElementVisibility(getSizeChart());
			// if (getSizeChart().isDisplayed()) {
			objiOSGenericMethods.clickOnIOSElement(getSizeChart(), "Size Chart Button");
			// }
		} catch (Exception e) {
		}
	}

	/**
	 * @author 300019221 Replaced thread.sleep with webdriver wait
	 * @throws InterruptedException
	 */

	public void clickOnSizeChartBackButton() throws InterruptedException {
		// objiOSGenericMethods.waitForElementVisibility(getSizeChartBackButton());
		try {

			if (getSizeChartBackButton().isDisplayed()) {
				System.out.println("Back Button is " + getSizeChartBackButton().isDisplayed());
				objiOSGenericMethods.clickOnIOSElement(getSizeChartBackButton(), "SSize Chart Back Button");
			}
		} catch (Exception e) {
			System.out.println("Back Button is " + getSizeChartBackButton().isDisplayed());
		}

	}

	/**
	 * @author 300019221 Replaced thread.sleep with webdriver wait
	 * @throws InterruptedException
	 */
	public void clickOnViewSimilar() throws InterruptedException {
		// objiOSGenericMethods.waitForElementVisibility(getViewSimilar());
		// if (getViewSimilar().isDisplayed()) {
		objiOSGenericMethods.clickOnIOSElement(getViewSimilar(), "View Similar Button");
		// }

	}

	/**
	 * @author 300019221 /Aravindanath Created this method to select more similar
	 *         products for eg. nike bags *
	 * @author 300019221 Replaced thread.sleep with webdriver wait
	 * @throws InterruptedException
	 */
	public void clickOnViewMoreSimilar() throws InterruptedException {
		// objiOSGenericMethods.waitForElementVisibility(getViewMoreSimilar());
		// if (getViewMoreSimilar().isDisplayed()) {
		objiOSGenericMethods.clickOnIOSElement(getViewMoreSimilar(), "View Similar Button");
		// }

	}

	public void clickOnEnterPinCode() {
		objiOSGenericMethods.clickOnIOSElement(getEnterPinCode(), "Enter PinCode Button");
	}

	public void clickOnCheckPinCode() {
		objiOSGenericMethods.clickOnIOSElement(getCheckPinCode(), "Check PinCode Button");
	}

	public void clickOnBack() {
		objiOSGenericMethods.clickOnIOSElement(getBack(), "Back Button");
	}

	public void clickOnViewMyBag() {
		objiOSGenericMethods.clickOnIOSElement(getViewMyBag(), "View My Bag Button");
	}

	public void clickOnViewWishList() {
		objiOSGenericMethods.clickOnIOSElement(getViewWishList(), "View WishList Button");
	}

	public void clickOnMoveToWishList() {
		objiOSGenericMethods.clickOnIOSElement(getMoveToWishList(), "Move To WishList Button");
	}

	public void clickbackButton() {
		objiOSGenericMethods.clickOnIOSElement(getBackButtonOnSizeChart(), "Back Button");
	}

	/**
	 * * @author 300019221 Replaced thread.sleep with webdriver wait
	 * 
	 * @throws InterruptedException
	 */
	public void clickOnGoToBag() throws InterruptedException {

		// objiOSGenericMethods.waitForElementVisibility(getGoToBag());
		try {
			// if (getGoToBag().isDisplayed()) {
			objiOSGenericMethods.clickOnIOSElement(getGoToBag(), "Go to Bag Button");
			// }
		} catch (Exception e) {
		}

	}

	/**
	 * @author 300019221 /Aravindanath Added try and If condition. Added webdriver
	 *         wait
	 */

	public void clickOnBestPrice() {
		try {

			// if (getBestPrice().isDisplayed()) {
			objiOSGenericMethods.clickOnIOSElement(getBestPrice(), "Best Price Button");
			// } else {
			// System.out.println("No Best Price for this product!");
			// }
		} catch (Exception e) {
			System.out.println("No Best Price for this product!");
		}

	}

	public void verifyViewSimilar() {
		// if (viewSimilar.isDisplayed()) {
		System.err.println("Similar header is present --> " + viewSimilar.getText());
		// }

	}

	public List<IOSElement> clickOnBestPriceList() throws InterruptedException {

		System.out.println("Best Price products are " + bestPriceList.size());
		for (Iterator iterator = bestPriceList.iterator(); iterator.hasNext();) {
			IOSElement IOSElement = (IOSElement) iterator.next();
			System.err.println("List of Similar products --> " + IOSElement.getText());
		}

		return bestPriceList;
	}

	public List<IOSElement> clickOnDeliveryOptionsList() throws InterruptedException {

		for (Iterator<IOSElement> iterator = deliveryOptionsList.iterator(); iterator.hasNext();) {
			IOSElement IOSElement = (IOSElement) iterator.next();
			System.err.println("Delivery Options  are --> " + IOSElement.getText());
		}

		return deliveryOptionsList;
	}

	public void clickOnWishlistTag() {
		objiOSGenericMethods.clickOnIOSElement(getWishlistTag(), "Save Tag Button");
	}

	public List<IOSElement> getSimilarProducts() throws InterruptedException {

		System.out.println("Total number is similar products are " + similarProcuctList.size());
		for (Iterator iterator = similarProcuctList.iterator(); iterator.hasNext();) {
			IOSElement IOSElement = (IOSElement) iterator.next();
			System.err.println("List of Similar products --> " + IOSElement.getText());
		}

		return similarProcuctList;
	}

	public void getSizeList(int i) throws InterruptedException {

		try {
			if (selectSizes.isDisplayed()) {
//				sizeList.get(i).click();
				objiOSGenericMethods.clickOnIOSElement(getSizeList().get(i), "Save Tag Button");
				System.out.println("Size list is diplayed!");
			}
		} catch (Exception e) {
			System.out.println("Size list not diplayed!");
		}
	}

	/**
	 * @author 300019221 / Aravindanath
	 * @param 0
	 *            or 1
	 */
	public void getSizeListinString(int i) {
		try {
			if (selectSizes.isDisplayed()) {
				selectSize.get(i).click();
			}
		} catch (Exception e) {
			System.out.println("Size list in not displayed!");
		}

	}

	public void selectSizeIfPresent() {
		try {// SizeChart
			if (SizeChart.isDisplayed()) {
				int size = getSizeList().size();
				for (int i = 0; i < size; i++) {
					if (getSizeList().get(i).isEnabled()) {
						(getSizeList()).get(i).click();
						break;

					}
				}

			}

		} catch (Exception e) {
			System.out.println("This element does not have any size");
		}
	}

	// /**
	// * @author 300019221 Aravindanath
	// * JS Executor click is used in this method.
	// */
	public void clickonDoneButton() {
		try {
			// if (getdoneButton().isDisplayed()) {
			objiOSGenericMethods.clickOnIOSElement(getdoneButton(), "Done Button");
			// getdoneButton().click();
			// }
		} catch (Exception e) {
			System.out.println("Size is not displayed, so no done button");
		}

	}

	public void clickonSizeList(int i) {
		getSizeList().get(i).click();
	}

	/**
	 * * @author 300019221 Replaced thread.sleep with webdriver wait
	 * 
	 * @throws InterruptedException
	 */
	public void clickOnAddToBag() throws InterruptedException {

		// objiOSGenericMethods.waitForElementVisibility(getAddToBag());
		try {
			// if (getAddToBag().isDisplayed()) {
			objiOSGenericMethods.clickOnIOSElement(getAddToBag(), "Add To Bag Button");
			// getAddToBag().click();

			// }
		} catch (Exception e) {
			System.out.println("Add to Bag button is not displayed!");
		}

	}

	public void selectSizeListinCoordinates() {
		TouchAction action = new TouchAction(iDriver);
		action.tap(0, 64).perform();
	}

	public void clickOnSearchButtonPDP() {
		objiOSGenericMethods.clickOnIOSElement(getSearchPDP(), "Search PDP Button");
	}

	public void clickOnSimilarProduct() {

		List<IOSElement> similarProduct = getSimilarProcuctList();
		for (IOSElement result : similarProduct) {
			IOSElement element = (IOSElement) result.findElement(By.tagName("XCUIElementTypeOther"));
			element.click();
			break;
		}

	}

	/**
	 * * @author 300019221 Replaced thread.sleep with webdriver wait
	 * 
	 * @throws InterruptedException
	 */

	public void clickMoreSimilarProducts() throws InterruptedException {
		// Thread.sleep(500);

		try {

			if (getMoreSimilarProducts().isDisplayed()) {
				// objiOSGenericMethods.waitForElementVisibility(getMoreSimilarProducts());
				System.out.println("User found more similar products");
				objiOSGenericMethods.clickOnIOSElement(getMoreSimilarProducts(), "More Similar Products Button");
				System.out.println("User found more similar products and clicked!");
			} else if (getSimilarProduct().isDisplayed()) {
				// objiOSGenericMethods.waitForElementVisibility(getSimilarProduct());
				System.out.println("User found more similar products");
				objiOSGenericMethods.clickOnIOSElement(getSimilarProduct(), "Similar Products Button");
				System.out.println("User found more similar products and clicked!");
			}
		} catch (Exception e) {
			System.out.println("NO Similar products found!");
		}

	}

	public void getTopButtons(int i) {
		try {
			topButtons.get(i).click();
			System.out.println("User clicked on " + topButtons.get(i).getTagName());
		} catch (Exception e) {

		}

	}

	/**
	 * @author 300021277
	 * 
	 *         added new method for select size
	 * 
	 */
	public void selectASize() {
		List<IOSElement> lst = getSelectSize();
		System.out.println("the size of the chart is" + lst.size());
		{

			for (IOSElement e : lst) {
				List<MobileElement> sizecount = e.findElements(By.id("size_list"));

				System.out.println("the size of the singlesize is" + sizecount.size());
				if (sizecount.size() == 1) {
					System.out.println("clicked successfully");
					e.click();
					getdoneButton().click();
					break;
				}

			}
			try {
				if (getdoneButton().isDisplayed()) {
					for (IOSElement e1 : lst) {
						e1.click();
						getdoneButton().click();
						try {
							if (getdoneButton().isDisplayed()) {
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
}
