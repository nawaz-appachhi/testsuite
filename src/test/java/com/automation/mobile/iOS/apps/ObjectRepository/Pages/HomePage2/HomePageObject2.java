package com.automation.mobile.iOS.apps.ObjectRepository.Pages.HomePage2;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.Reporter;

import com.automation.core.mobile.iOS.iOSGenericMethods;

import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;

public class HomePageObject2 {
	iOSGenericMethods objiOSGenericMethods;

	public IOSDriver<IOSElement> iDriver;

	public HomePageObject2(IOSDriver<IOSElement> iDriver) {
		PageFactory.initElements(new AppiumFieldDecorator(iDriver), this);
		objiOSGenericMethods = new iOSGenericMethods(iDriver);
	}

	/**
	 * @author 300021275 - xpath changed by accessibility id
	 * 
	 *         Old Locator - xpath = "//XCUIElementTypeButton[@name=\"Home\"]"
	 */

	@iOSFindBy(accessibility = "Home")
	public IOSElement homeButton;

	@iOSFindBy(xpath = "//XCUIElementTypeOther[@name=\"Nike 206\"]")
	public IOSElement nike;

	@FindBy(xpath = "//XCUIElementTypeNavigationBar[@name=\"Featured\"]/XCUIElementTypeButton[1]")
	public IOSElement search;

	@FindBy(className = "XCUIElementTypeSearchField")
	public IOSElement search1;
	/**
	 * @author 300019221 This object is added to handle allow popup.
	 */
	@iOSFindBy(xpath = "//XCUIElementTypeAlert//XCUIElementTypeButton[@name='Allow']")
	public IOSElement allowButton;

	@iOSFindBy(accessibility = "OK")
	public IOSElement oKAndTouch;

	@iOSFindBy(xpath = "//XCUIElementTypeButton[@name='OK']")
	public IOSElement tapAndHold;

	@iOSFindBy(xpath = "//XCUIElementTypeApplication[@name=\"Myntra\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeCollectionView/XCUIElementTypeCell[1]/XCUIElementTypeOther/XCUIElementTypeImage")
	public IOSElement image1;

	@iOSFindBy(accessibility = "Men Md Runner 2 Lw Running")
	public IOSElement nikeRunningShoes;

	@iOSFindBy(className = "XCUIElementTypeOther")
	public List<IOSElement> bigBanner;

	@iOSFindBy(className = "XCUIElementTypeOther")
	public List<IOSElement> nestedBanner;

	@FindAll({ @FindBy(className = "XCUIElementTypeButton") })
	public List<IOSElement> topButtons;

	@iOSFindBy(className = "XCUIElementTypeSearchField")
	public IOSElement searchBox;

	@FindBy(xpath = "//XCUIElementTypeAlert//XCUIElementTypeButton[@name='Allow']")
	public IOSElement permssion;

	@FindAll({ @FindBy(className = "XCUIElementTypeButton") })
	public List<IOSElement> searchHomePageList;

	// @iOSFindBy(xpath = "//XCUIElementTypeButton[@name=\"Categories\"]")
	@iOSFindBy(accessibility = "Categories")
	public IOSElement Categories;

	/**
	 * element added by madhu
	 */
	@iOSFindBy(accessibility = "REMOVE OUT OF STOCK ITEM(S)")
	public IOSElement removeOutOfStock;
	/*
	 * Home bar
	 */

	@iOSFindBy(accessibility = "Bag")
	public IOSElement BagIcon;

	@iOSFindBy(accessibility = "REMOVE")
	public IOSElement removeButton;

	@iOSFindBy(xpath = "//XCUIElementTypeButton[@name=\"REMOVE\"] ")
	public IOSElement removeButton2;

	/************** getters ******************/
	public IOSElement getBagIcon() {
		objiOSGenericMethods.CheckIOSElementFound(BagIcon, "BagIcon");
		return BagIcon;
	}

	public IOSElement getHomeButton() {
		objiOSGenericMethods.CheckIOSElementFound(homeButton, "homeButton");
		return homeButton;
	}

	public IOSElement getNike() {
		objiOSGenericMethods.CheckIOSElementFound(nike, "nike");
		return nike;
	}

	public IOSElement getSearch() {
		objiOSGenericMethods.CheckIOSElementFound(search, "search");
		return search;
	}

	public IOSElement getSearch1() {
		objiOSGenericMethods.CheckIOSElementFound(search1, "search1");
		return search1;
	}

	/**
	 * @author 300019221 This getter is created for allow popup
	 * @return
	 */
	public IOSElement getAllowButton() {
		objiOSGenericMethods.CheckIOSElementFound(allowButton, "allow Button");
		return allowButton;
	}

	public IOSElement getoKAndTouch() {
		objiOSGenericMethods.CheckIOSElementFound(oKAndTouch, "oKAndTouch");
		return oKAndTouch;
	}

	public IOSElement getTapAndHold() {
		objiOSGenericMethods.CheckIOSElementFound(tapAndHold, "tapAndHold");
		return tapAndHold;
	}

	public IOSElement getImage1() {
		objiOSGenericMethods.CheckIOSElementFound(image1, "image1");
		return image1;
	}

	public IOSElement getNikeRunningShoes() {
		objiOSGenericMethods.CheckIOSElementFound(nikeRunningShoes, "nikeRunningShoes");
		return nikeRunningShoes;
	}

	public List<IOSElement> getBigBanner() {
		return bigBanner;
	}

	public List<IOSElement> getNestedBanner() {
		return nestedBanner;
	}

	public List<IOSElement> getTopButtons() {
		return topButtons;
	}

	public IOSElement getSearchBox() {
		return searchBox;
	}

	public List<IOSElement> getSearchHomePageList() {
		return searchHomePageList;
	}

	public IOSElement getCategories() {
		return Categories;
	}

	public IOSElement getRemoveBtn() {
		objiOSGenericMethods.CheckIOSElementFound(removeButton, "Remove Button");
		return removeButton;
	}

	public IOSElement getRemoveBtn2() {
		objiOSGenericMethods.CheckIOSElementFound(removeButton2, "Remove Button");
		return removeButton2;
	}

	public IOSElement getRemoveOutOfStock() {
		objiOSGenericMethods.CheckIOSElementFound(removeOutOfStock, "removeOutOfStock");
		return removeOutOfStock;
	}

	/******************* methods ****************/

	public void clickOnHomeButton() {
		objiOSGenericMethods.waitForElementVisibility(getHomeButton());
		if (getHomeButton().isDisplayed()) {
			objiOSGenericMethods.clickOnIOSElement(getHomeButton(), "Successfully click on home button");
		}
		// objiOSGenericMethods.acceptAlert();
	}

	public void clickOnBagIcon() {
		if (getBagIcon().isDisplayed()) {
			objiOSGenericMethods.clickOnIOSElement(getBagIcon(), "Successfully click on BagIcon");
		}
	}

	public void clickOnRemoveBtn() {
		if (getRemoveBtn().isDisplayed()) {
			objiOSGenericMethods.clickOnIOSElement(getRemoveBtn(), "Successfully click on Remove");
		}
	}

	public void clickOnRemoveBtn2() {
		if (getRemoveBtn2().isDisplayed()) {
			objiOSGenericMethods.clickOnIOSElement(getRemoveBtn2(), "Successfully click on Remove");
		}
	}

	/**
	 * @author 300019221 / Aravindanath Added Ok Button method
	 */

	public void clickOnSearchButton() {
		if (getSearch().isDisplayed()) {
			objiOSGenericMethods.clickOnIOSElement(getSearch(), "Successfully click on search button");
		}
		try {
			if (tapAndHold.isDisplayed()) {
				tapAndHold();
			}

		} catch (Exception e2) {
			// TODO Auto-generated catch block

		}
	}

	/**
	 * @author 300019221 aravindanath
	 * @throws InterruptedException
	 *             Added Allow pop and OK button method is added
	 * @author 300019221 Allow pop modified
	 */

	public void enterSearchitem(String Pname) throws InterruptedException {
		getSearch1().clear();
		getSearch1().sendKeys(Pname);
		getSearch1().sendKeys(Keys.ENTER);
		try {
			if (tapAndHold.isDisplayed()) {
				System.err.println("Tap and Hold button is displayed!");
				tapAndHold.click();
			}
		} catch (Exception e) {
			System.out.println("Ok button not found!");
		}

	}

	public void pressNikeRunningShoes() {
		objiOSGenericMethods.clickOnIOSElement(getImage1(), "Successfully click on image1 button");
	}

	public void tapAndHold() {

		try {
			if (tapAndHold.isDisplayed()) {
				System.err.println("Tap and Hold button is displayed!");
				tapAndHold.click();
			}
		} catch (Exception e) {
			System.out.println("Ok button not found!");
		}
	}

	public void clickOnBigBanner(int i) throws InterruptedException {
		getBigBanner().get(i).click();
		System.out.println("Successfully clicked on BigBanner! ");
	}

	public void clickOnNestedBanner(int i) throws InterruptedException {
		try {

			getNestedBanner().get(i).click();
			System.out.println("Successfully clicked on nestedBanner! ");
			System.out.println("Successfully click on Nested Banner and moved to Product List Page");
			tapAndHold.click();

		} catch (Exception e) {
		}
	}

	public void getTopButtons(int i) {
		try {
			topButtons.get(i).click();
		} catch (Exception e) {

		}

	}

	public void pressEnter() {
		objiOSGenericMethods.clickOnIOSElement(getNike(), "Successfully click on nike button");
	}

	public void setSearchBox(String Pname) throws InterruptedException {
		if (searchBox.isDisplayed()) {
			searchBox.sendKeys(Pname);
			searchBox.sendKeys(Keys.ENTER);
			try {
				if (tapAndHold.isDisplayed()) {
					System.err.println("Tap and Hold button is displayed!");
					tapAndHold.click();
				}

			} catch (Exception e) {
			}
			Reporter.log("Product name entered succesfully");
		} else {
			Assert.fail("Unable to enter Product name ");
		}
	}

	public void handlePermission() {

		try {
			if (permssion.isDisplayed()) {
				permssion.click();
				Reporter.log("Successfully click on permission button");
			} else {
				Assert.fail("Unable to click on permission button");
			}
		} catch (Exception e) {
			System.out.println("Permission not displayed");
		}

	}

	public void clickOnSearchButtonHomePage(int i) {
		searchHomePageList.get(i).click();
		// System.out.println(searchHomePageList.get(i).getText());
		Reporter.log("Successfully click on search button");

	}

	public void clickOnCategories() {
		objiOSGenericMethods.clickOnIOSElement(getCategories(), "Successfully click on Categories");
	}

	public void clickOnRemoveOutOfStock() {
		objiOSGenericMethods.clickOnIOSElement(getRemoveOutOfStock(), "Successfully click on Categories");
	}

	/**
	 * @author 300019221 / Aravindanath This method will remove the products from
	 *         the bag. Added Tap and Hold method
	 * 
	 *         modified by madhu out of stock items remove method added
	 * 
	 * @author 300019221 added alert method
	 * 
	 */
	public void emptyBag() throws InterruptedException {
		objiOSGenericMethods.waitForElementVisibility(getBagIcon());
		if (getBagIcon().isDisplayed()) {
			getBagIcon().click();
		}
		try {
			if (tapAndHold.isDisplayed()) {
				tapAndHold();
			}

		} catch (Exception e2) {

		}
		try {
			if (getRemoveOutOfStock().isDisplayed()) {
				clickOnRemoveOutOfStock();
			}
		} catch (Exception e1) {
			tapAndHold();
			System.out.println("No Out of stock item to remove!");
		}

		 objiOSGenericMethods.swipeDown(100, 1);
		try {
			if (getRemoveBtn().isDisplayed()) {
				clickOnRemoveBtn();
				objiOSGenericMethods.waitForElementVisibility(getRemoveBtn2());
				clickOnRemoveBtn2();
			}
		} catch (Exception e) {
			System.out.println("Bag is already Empty :) !");
		}

	}

}
