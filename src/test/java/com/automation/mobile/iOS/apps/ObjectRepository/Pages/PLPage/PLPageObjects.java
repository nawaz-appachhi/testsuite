package com.automation.mobile.iOS.apps.ObjectRepository.Pages.PLPage;

import java.util.Iterator;
import java.util.List;

import org.apache.maven.doxia.logging.Log;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.Reporter;

import com.automation.core.mobile.iOS.iOSGenericMethods;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;

public class PLPageObjects {

	iOSGenericMethods objiOSGenericMethods;

	public PLPageObjects(IOSDriver<IOSElement> iDriver) {
		PageFactory.initElements(new AppiumFieldDecorator(iDriver), this);
		objiOSGenericMethods = new iOSGenericMethods(iDriver);
	}

	@iOSFindBy(accessibility = "SORT")
	public IOSElement sort;
	/**
	 * Sort options
	 */
	@iOSFindBy(accessibility = "Popularity")
	public IOSElement popularitySort;

	@iOSFindBy(accessibility = "30 % and above")
	public IOSElement discountPercent;

	@iOSFindBy(accessibility = "New")
	public IOSElement newSort;

	@iOSFindBy(accessibility = "Popularity")
	public IOSElement popularity;

	@iOSFindBy(accessibility = "Discount")
	public IOSElement discountSort;

	@iOSFindBy(accessibility = "Price: High - Low")
	public IOSElement priceHighLowSort;

	@iOSFindBy(accessibility = "Price: Low - High")
	public IOSElement priceLowHighSort;

	@iOSFindBy(accessibility = "Delivery Time")
	public IOSElement deliveryTimeSort;

	@iOSFindBy(xpath = "//XCUIElementTypeApplication[@name=\"Myntra\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeTextField")
	public IOSElement typePincodeTextField;

	@iOSFindBy(accessibility = "CHECK")
	public IOSElement checkDeliveryTimeSort;

	@iOSFindBy(xpath = "//XCUIElementTypeNavigationBar[@name='MYNPincodeView']/XCUIElementTypeButton")
	public IOSElement cancelDeliveryTimeSort;

	@iOSFindBy(accessibility = "APPLY")
	public IOSElement applyDiscount;

	@iOSFindBy(accessibility = "Discount")
	public IOSElement filterDiscount;

	@iOSFindBy(className = "XCUIElementTypeStaticText")
	public List<IOSElement> filterDiscountPercent;

	@iOSFindBy(accessibility = "OK")
	public IOSElement oKAndTouch;

	@iOSFindBy(accessibility = "Price")
	public IOSElement priceButton;

	@iOSFindBy(className = "XCUIElementTypeStaticText")
	public List<IOSElement> listOfPrice;

	/**
	 * @author 300021275 - Lata
	 * 
	 *         object for filter apply button
	 */

	@iOSFindBy(accessibility = "APPLY")
	public IOSElement applyFilter;

	/**
	 * Filter
	 */

	/**
	 * Name: Monika Old Locator: xpath =
	 * "//XCUIElementTypeImage[@name=\"action_filter\"]" New Locator: accessibility
	 * = "REFINE"
	 */
	@iOSFindBy(accessibility = "REFINE")
	public IOSElement filter;

	// @iOSFindBy(accessibility = "action_filter")
	// public IOSElement filter;

	@iOSFindBy(xpath = "(//XCUIElementTypeImage[@name='pdp-wishlist'])[1]")
	public IOSElement saveWishlist;

	@iOSFindBy(xpath = "//XCUIElementTypeAlert//XCUIElementTypeButton[@name='Allow']")
	public IOSElement allowButton;

	/**
	 * xpath of First product in PLP
	 */

	@iOSFindBy(xpath = "//XCUIElementTypeApplication[@name=\"Myntra\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeCollectionView/XCUIElementTypeCell[2]/XCUIElementTypeOther")
	public IOSElement brandNamePLP;

	/**
	 * Modified by subhasis
	 * 
	 * //XCUIElementTypeApplication[@name=\"Myntra\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther[2]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeCollectionView/XCUIElementTypeCell[2]/XCUIElementTypeOther/XCUIElementTypeOther[2]
	 */

	@iOSFindBy(xpath = "//XCUIElementTypeApplication[@name=\"Myntra\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeCollectionView/XCUIElementTypeCell[2]/XCUIElementTypeOther/XCUIElementTypeImage")
	public IOSElement FirstproductofPLP;

	@iOSFindBy(xpath = "//XCUIElementTypeApplication[@name=\"Myntra\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeCollectionView/XCUIElementTypeCell[1]/XCUIElementTypeOther/XCUIElementTypeImage")
	public IOSElement selectProduct;

	@iOSFindBy(xpath = "//XCUIElementTypeStaticText[@name='₹ 949']")
	public IOSElement pricePLP;

	// @iOSFindBy(xpath = " //XCUIElementTypeButton[@name='nav search']")
	@iOSFindBy(className = "nav search")
	public IOSElement SearchBtnOnPLP;

	// @iOSFindBy(accessibility = "REFINE")
	// public IOSElement filter;
	/*
	 * modifier
	 */
	@iOSFindBy(className = "XCUIElementTypeImage")
	public List<IOSElement> listOfProducts;

	/*
	 * added filter gender xpath/method/getters
	 */

	@iOSFindBy(accessibility = "Gender")
	public IOSElement filterGender;

	@iOSFindBy(accessibility = "Men")
	public IOSElement firstFilterGender;

	/************ getters ******/
	public IOSElement getSort() {
		objiOSGenericMethods.CheckIOSElementFound(sort, "sort");
		return sort;
	}

	public IOSElement getFirstproductofPLP() {
		objiOSGenericMethods.CheckIOSElementFound(FirstproductofPLP, "FirstproductofPLP");
		return FirstproductofPLP;
	}

	public IOSElement getPopularitySort() {
		objiOSGenericMethods.CheckIOSElementFound(popularitySort, "popularitySort");
		return popularitySort;
	}

	public IOSElement getDiscountPercent() {
		objiOSGenericMethods.CheckIOSElementFound(discountPercent, "discountPercent");
		return discountPercent;
	}

	public IOSElement getNewSort() {
		objiOSGenericMethods.CheckIOSElementFound(newSort, "newSort");
		return newSort;
	}

	public IOSElement getDiscountSort() {
		objiOSGenericMethods.CheckIOSElementFound(discountSort, "discountSort");
		return discountSort;
	}

	public IOSElement getPriceHighLowSort() {
		objiOSGenericMethods.CheckIOSElementFound(priceHighLowSort, "priceHighLowSort");
		return priceHighLowSort;
	}

	public IOSElement getPriceLowHighSort() {
		objiOSGenericMethods.CheckIOSElementFound(priceLowHighSort, "priceLowHighSort");
		return priceLowHighSort;
	}

	public IOSElement getDeliveryTimeSort() {
		objiOSGenericMethods.CheckIOSElementFound(deliveryTimeSort, "deliveryTimeSort");
		return deliveryTimeSort;
	}

	public IOSElement getTypePincodeTextField() {
		objiOSGenericMethods.CheckIOSElementFound(typePincodeTextField, "typePincodeTextField");
		return typePincodeTextField;
	}

	public IOSElement getCheckDeliveryTimeSort() {
		objiOSGenericMethods.CheckIOSElementFound(checkDeliveryTimeSort, "checkDeliveryTimeSort");
		return checkDeliveryTimeSort;
	}

	public IOSElement getCancelDeliveryTimeSort() {
		objiOSGenericMethods.CheckIOSElementFound(cancelDeliveryTimeSort, "cancelDeliveryTimeSort");
		return cancelDeliveryTimeSort;
	}

	public IOSElement getApplyDiscount() {
		objiOSGenericMethods.CheckIOSElementFound(applyDiscount, "applyDiscount");
		return applyDiscount;
	}

	public IOSElement getFilterDiscount() {
		objiOSGenericMethods.CheckIOSElementFound(filterDiscount, "filterDiscount");
		return filterDiscount;
	}

	public List<IOSElement> getFilterDiscountPercent() {
		objiOSGenericMethods.CheckIOSElementsListFound(filterDiscountPercent, "filterDiscountPercent");
		return filterDiscountPercent;
	}

	/**
	 * @author 300019221 created new getter
	 * @return
	 */

	public List<IOSElement> getPriceList() {
		objiOSGenericMethods.CheckIOSElementsListFound(listOfPrice, "listOfPrice");
		return listOfPrice;
	}

	public IOSElement getoKAndTouch() {
		objiOSGenericMethods.CheckIOSElementFound(oKAndTouch, "oKAndTouch");
		return oKAndTouch;
	}

	public IOSElement getSaveWishlist() {
		objiOSGenericMethods.CheckIOSElementFound(saveWishlist, "saveWishlist");
		return saveWishlist;
	}

	public IOSElement getBrandNamePLP() {
		objiOSGenericMethods.CheckIOSElementFound(brandNamePLP, "brandNamePLP");
		return brandNamePLP;
	}

	public IOSElement getAllowButton() {
		objiOSGenericMethods.CheckIOSElementFound(allowButton, "allow Button");
		return allowButton;
	}

	public IOSElement getSelectProduct() {
		objiOSGenericMethods.CheckIOSElementFound(selectProduct, "selectProduct");
		return selectProduct;
	}

	public IOSElement getPricePLP() {
		objiOSGenericMethods.CheckIOSElementFound(pricePLP, "pricePLP");
		return pricePLP;
	}

	public IOSElement getFilter() {
		objiOSGenericMethods.CheckIOSElementFound(filter, "filter");
		return filter;
	}

	public IOSElement getPopularity() {
		objiOSGenericMethods.CheckIOSElementFound(popularity, "popularity");
		return popularity;
	}

	public IOSElement getSearchOnPLP() {
		objiOSGenericMethods.CheckIOSElementFound(SearchBtnOnPLP, "Search");
		return SearchBtnOnPLP;
	}

	public List<IOSElement> getListOfProducts() {
		return listOfProducts;
	}

	public IOSElement getFilterGender() {
		objiOSGenericMethods.CheckIOSElementFound(filterGender, "filterGender");
		return filterGender;
	}

	public IOSElement getFirstFilterGender() {
		objiOSGenericMethods.CheckIOSElementFound(firstFilterGender, "firstFilterGender");
		return firstFilterGender;
	}

	public IOSElement getApplyFilter() {
		objiOSGenericMethods.CheckIOSElementFound(applyFilter, "applyFilter");
		return applyFilter;
	}

	public IOSElement getPriceButton() {
		objiOSGenericMethods.CheckIOSElementFound(priceButton, "priceButton");
		return priceButton;
	}

	/************* methods ********************/

	public void clickOnSort() {
		objiOSGenericMethods.clickOnIOSElement(getSort(), "Sort Button");
	}

	public void clickOnFirstproductofPLP() {
		objiOSGenericMethods.clickOnIOSElement(getFirstproductofPLP(), "First Product of PLP");
	}

	public void clickOnSearchOnPLP() {
		// if (getSearchOnPLP().isDisplayed()) {
		objiOSGenericMethods.clickOnIOSElement(getSearchOnPLP(), "Search Button");
		// }
	}

	public void clickOnDiscountSort() {
		objiOSGenericMethods.clickOnIOSElement(getDiscountSort(), "Discount Sort Button");
	}

	public void clickOnPopularitySort() {
		objiOSGenericMethods.clickOnIOSElement(getPopularitySort(), "Popularity Sort Button");
	}

	public void clickOnNewSort() {
		objiOSGenericMethods.clickOnIOSElement(getNewSort(), "New Sort Button");
	}

	public void clickOnPriceHighLowSort() {
		objiOSGenericMethods.clickOnIOSElement(getPriceHighLowSort(), "Price High Low Sort Button");
	}

	public void clickOnPriceLowHighSort() {
		objiOSGenericMethods.clickOnIOSElement(getPriceLowHighSort(), "Price Low High Sort Button");
	}

	public void clickOnDeliveryTimeSort() {
		objiOSGenericMethods.clickOnIOSElement(getDeliveryTimeSort(), "Delivery Time Sort Button");
	}

	public void clickOnCancelDeliveryTimeSort() {
		objiOSGenericMethods.clickOnIOSElement(getCancelDeliveryTimeSort(), "Cancel Delivery Time Sort Button");
	}

	public void clickOnTypePincodeTextField(String pincode) {
		objiOSGenericMethods.clickOnIOSElement(getTypePincodeTextField(), "Type Pincode Text Field");
		// getTypePincodeTextField().click();
		getTypePincodeTextField().sendKeys(pincode);
		System.out.println("Successfully click on deliveryTime and entered pincode " + pincode);
	}

	public void clickOnCheckDeliveryTimeSort() {
		objiOSGenericMethods.clickOnIOSElement(getCheckDeliveryTimeSort(), "Check Delivery Time Sort Button");
	}

	/**
	 * @author 300019221 Aravindanath Add wait for element visibility method. Using
	 *         WebDriverWait method.
	 */
	public void clickOnFilter() {

		try {
			objiOSGenericMethods.waitForElementVisibility(getFilter());
			// if (getFilter().isDisplayed()) {

			objiOSGenericMethods.clickOnIOSElement(getFilter(), "Filter Button");
			// objiOSGenericMethods.click(getFilter());
			// }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Filter button is not displayed!");
		}
	}

	public void clickOnSaveWishlist() {
		objiOSGenericMethods.clickOnIOSElement(getSaveWishlist(), "Save Wishlist Button");
	}

	/**
	 * @author 300019221 Added webdriver wait
	 * @throws InterruptedException
	 */
	public void clickOnBrandNamePLP() throws InterruptedException {
		// Thread.sleep(1000);

//		objiOSGenericMethods.waitForElementVisibility(getBrandNamePLP());
		try {
			// if (getBrandNamePLP().isDisplayed()) {
			objiOSGenericMethods.clickOnIOSElement(getBrandNamePLP(), "Brand Name PLP Button");
			// }
		} catch (Exception e) {
			System.out.println("User did not find product!");
		}

		try {
			if (getAllowButton().isDisplayed()) {
//				objiOSGenericMethods.click(getAllowButton());
				objiOSGenericMethods.clickOnIOSElement(getAllowButton(), "Allow Button");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
		}
	}

	public void clickOnPricePLP() {
		objiOSGenericMethods.clickOnIOSElement(getPricePLP(), "Price PLP Button");
	}

	public void clickOnselectProduct() {
		objiOSGenericMethods.clickOnIOSElement(getSelectProduct(), "Price PLP button");
	}

	public void clickOnDiscountPercent() {
		objiOSGenericMethods.clickOnIOSElement(getDiscountPercent(), "Discount Percent Button");
	}

	public void clickOnApplyDiscount() {
		objiOSGenericMethods.clickOnIOSElement(getApplyDiscount(), "Apply Discount Button");
	}

	/**
	 * @author 300019221 /Aravindanath try block add
	 */

	public void clickOnFilterDiscount() {
		try {
			// if (getFilterDiscount().isDisplayed()) {
			objiOSGenericMethods.clickOnIOSElement(getFilterDiscount(), "Filter Discount Button");
			// }
		} catch (Exception e) {
		}
	}

	public List<IOSElement> ClickOnFilterDiscountPercent(int i) throws InterruptedException {
		System.err.println("Filter by Discount " + filterDiscountPercent.size());
		for (Iterator<IOSElement> iterator = filterDiscountPercent.iterator(); iterator.hasNext();) {
			WebElement WebElement = (WebElement) iterator.next();
			System.err.println("Filter by Discount --> " + WebElement.getText());
		}
		return filterDiscountPercent;
	}

	public void clickOnPopularity() {
		objiOSGenericMethods.clickOnIOSElement(getPopularity(), "Popularity Button");
	}

	/**
	 * 
	 * Modified By: monika Reason : If and else condition added to ok and touch
	 * button method
	 * 
	 */

	/*
	 * @author 300019221 / Aravindanath Added try catch block
	 */

	public void clickOnOkAndTouch() {
		try {
			if (oKAndTouch.isDisplayed()) {
				System.err.println("Tap and Hold button is displayed!");
				oKAndTouch.click();
			} else {
				Reporter.log("Unable to click on OK button");
			}
		} catch (Exception e) {
			System.out.println("OK button not displayed");
		}
	}

	public void clickOnProduct(int i) throws InterruptedException {

		try {
			getListOfProducts().get(i).click();
			System.out.println("Clicked on product!");
		} catch (Exception e) {
			System.out.println(" product not found to click!");
		}

	}

	public void clickOnFilterDiscountPercentage(int i) {
		getFilterDiscountPercent().get(i).click();
	}

	public void clickOnPriceButton() {
		// if (priceButton.isDisplayed()) {
		objiOSGenericMethods.clickOnIOSElement(getPriceButton(), "Price Button");
		// priceButton.click();
		System.out.println("User clicked on price button!");
		// }
	}

	/**
	 * @author 300019221 20 / 22 / 24 / 26 uses these values to CLICK on price range
	 */

	public void clickOnPrice(int i) {
//		getPriceList().get(i).click();
		objiOSGenericMethods.clickOnIOSElement(getPriceList().get(i), "Price List");
		System.out.println("Filter by Price --> " + getPriceList().get(i).getText());

	}

	/*
	 * this is for filter discount
	 */
	@iOSFindBy(xpath = "//XCUIElementTypeApplication[@name=\"Myntra\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeTable[2]/XCUIElementTypeCell[1]")
	public IOSElement selectFirstFilterDiscount;

	public IOSElement getselectFirstFilterDiscount() {
		return selectFirstFilterDiscount;
	}

	public void clickOnSelectFirstFilterDiscount() {
		objiOSGenericMethods.clickOnIOSElement(getselectFirstFilterDiscount(), "First Filter Discount Button");
		// getselectFirstFilterDiscount().click();
	}

	public void clickOnFilterGender() {
		objiOSGenericMethods.clickOnIOSElement(getFilterGender(), "Filter Gender Button");
		// getFilterGender().click();
	}

	public void clickOnFirstFilterGender() {
		objiOSGenericMethods.clickOnIOSElement(getFirstFilterGender(), "First Filter Gender Button");
		// getFirstFilterGender().click();
	}

	/**
	 * @author 300019221 aravinda Added try and if JS Exe click is used
	 */
	public void clickOnApplyFilter() {

		try {
			if (getApplyFilter().isDisplayed()) {
				// objiOSGenericMethods.clickOnIOSElement(getApplyFilter(), "Successfully click
				// on apply button");
				objiOSGenericMethods.click(getApplyFilter());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block

			objiOSGenericMethods.click(getApplyFilter());
		}
	}
}
