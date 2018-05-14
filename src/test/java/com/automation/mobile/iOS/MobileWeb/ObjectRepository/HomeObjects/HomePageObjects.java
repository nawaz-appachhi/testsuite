package com.automation.mobile.iOS.MobileWeb.ObjectRepository.HomeObjects;


import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;

import com.automation.core.mobile.iOS.iOSGenericMethods;

import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;

public class HomePageObjects {

	public IOSDriver<IOSElement> iDriver;
	iOSGenericMethods objiOSGenericMethods;

	public HomePageObjects(IOSDriver<IOSElement> iDriver) {
		PageFactory.initElements(new AppiumFieldDecorator(iDriver), this);
		objiOSGenericMethods = new iOSGenericMethods(iDriver);
	}

	/*
	 * Hamburger Menu Author 300021281
	 */

	@FindBy(xpath = "//span[@class='icon icon-menu']")
	public IOSElement hamburgerIcon;

	/*
	 * Home screen search icon
	 */

	@FindBy(id = "header-search-icon")
	public IOSElement searchIcon;

	@FindBy(xpath = "//div[@class='search-wrap']//input[@class='form-control search-box-input']")
	public IOSElement enterInSeacrchField;

	/*
	 * Home screen wishlist icon
	 */

	@FindBy(xpath = "//a[@href='/wishlist' and @id='header-wishlist-icon']")
	public IOSElement wishlistIcon;

	/*
	 * home screen BAG icon
	 */

	@FindBy(xpath = "//span[@class='icon icon-bag']")
	public IOSElement bagIcon;
	

	/*
	 * Banner
	 */
	
	@FindBy(xpath = "//*[@class='img-responsive']")
	public List<IOSElement> banner;


	@FindBy(xpath = "//*[@id=\"reactPageContent\"]/div/div/div[4]/div[2]/div/div/a/div/div/picture/img")
	public IOSElement nestedBanner;

	@FindBy(xpath = "//*[@class='img-responsive']/img")
	public List<IOSElement> nestedBannerList;

	@FindBy(xpath = "//button[contains(text(),'Popularity')]")
	public IOSElement popularityButton;

	@FindBy(xpath = "//*[@id='reactPageContent']/div/div/div/div/ul/li[1]/div/div[1]/a/div[2]/div[2]")
	public IOSElement saveButton;
	
	/*
	 * Search Icon
	 */
	
	@FindBy(xpath = "//a[@id='header-search-icon']/span")
	public IOSElement searchButton;

	@FindBy(xpath = "//div[@class='search-wrap']/input[@placeholder='Search for products' or @class='form-control search-box-input']")
	public IOSElement searchPlaceholder;

	@FindAll({ @FindBy(xpath = "//div[@class='search-results-cont']/div") })
	public List<IOSElement> searchAutoSuggestList;
	
	@FindBy(xpath = "//*[@id=\" .//*[@class=\"mobile-backButton\"]")
	public IOSElement MobileBackButton;
	
	
	@FindBy(xpath = ".//*[@class='icon-close']")
	public IOSElement CloseIcon;
	
	/*****************getters*********************/
	


	public IOSElement getHamburgerIcon() {
		objiOSGenericMethods.CheckIOSElementFound(hamburgerIcon, "hamburgerIcon");
		return hamburgerIcon;
	}

	public IOSElement getSearchIcon() {
		objiOSGenericMethods.CheckIOSElementFound(searchIcon, "searchIcon");
		return searchIcon;
	}


	public IOSElement getCloseIcon() {
		return CloseIcon;
	}

	public IOSElement getMobileBackButton() {
		return MobileBackButton;
	}

	public IOSElement getEnterInSearchField() {
		objiOSGenericMethods.CheckIOSElementFound(enterInSeacrchField, "enterInSeacrchField");
		return enterInSeacrchField;
	}

	public IOSElement getWishlistIcon() {
		objiOSGenericMethods.CheckIOSElementFound(wishlistIcon, "wishlistIcon");
		return wishlistIcon;
	}

	public IOSElement getBagIcon() {
		objiOSGenericMethods.CheckIOSElementFound(bagIcon, "BagIcon");
		return bagIcon;
	}

	public List<IOSElement> getBanner() {
		objiOSGenericMethods.CheckIOSElementsListFound( banner, "banner");
		return banner;
	}

	public IOSElement getNestedBanner() {
		objiOSGenericMethods.CheckIOSElementFound(nestedBanner, "nestedBanner");
		return nestedBanner;
	}

	public List<IOSElement> getNestedBannerList() {
		objiOSGenericMethods.CheckIOSElementsListFound(nestedBannerList, "nestedBannerList");
		return nestedBannerList;
	}

	public IOSElement getPopularityButton() {
		objiOSGenericMethods.CheckIOSElementFound(popularityButton, "popularityButton");
		return popularityButton;
	}

	public IOSElement getSaveButton() {
		objiOSGenericMethods.CheckIOSElementFound(saveButton, "saveButton");
		return saveButton;
	}

	public IOSElement getSearchButton() {
		objiOSGenericMethods.CheckIOSElementFound(searchButton, "searchButton");
		return searchButton;
	}

	public IOSElement getSearchPlaceholder() {
		objiOSGenericMethods.CheckIOSElementFound(searchPlaceholder, "searchPlaceholder");
		return searchPlaceholder;
	}

	public List<IOSElement> getSearchAutoSuggestList() {
		objiOSGenericMethods.CheckIOSElementsListFound(searchAutoSuggestList, "searchAutoSuggestList");
		return searchAutoSuggestList;
	}
/**
 * @author 300019221  Aravindanath
 * Added if condition  
 * @author 300019221  Aravindanath
 * Added try block and web driver wait
 * 
 */
	public void clickOnHamburgerButton() {
		try {
			if(getHamburgerIcon().isDisplayed()) {
				objiOSGenericMethods.waitForElementVisibility(getHamburgerIcon());
				objiOSGenericMethods.clickOnIOSElement(getHamburgerIcon(),"clicked on HamburgerIcon");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			 
		}
		
	}

	public void clickOnCloseIcon() {
		getCloseIcon().click();
	}
	public void clickOnMobileBackButton() {
		getMobileBackButton().click();
	}
	
	public void clickOnSearchIcon() {
		objiOSGenericMethods.waitDriver(getSearchIcon(), "Search icon");
		objiOSGenericMethods.clickOnIOSElement(getSearchIcon(),"clicked on SearchIcon");
	}

	public void enterSearchItem(String Bname) {
		getEnterInSearchField().sendKeys(Bname);
		VerifyAutoSuggestionList();
		getEnterInSearchField().sendKeys(Keys.ENTER);
	}

	public void clickOnWishlistButton() {
		objiOSGenericMethods.waitDriver(getWishlistIcon(), "Wishlist");
		objiOSGenericMethods.clickOnIOSElement(getWishlistIcon(),"clicked on WishlistIcon");
	}
/**
 * @author 300019221 Aravindanath
 * Added Try block and if condition 
 * 
 * Used JS executor to click
 */
	public void clickOnBagIcon() {
		try {
			objiOSGenericMethods.waitForElementVisibility(getBagIcon());
			if (getBagIcon().isDisplayed()) {
				objiOSGenericMethods.click(getBagIcon());
			//	objiOSGenericMethods.clickOnIOSElement(getBagIcon(), "clicked on BagIcon");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void clickOnbanner() {
		objiOSGenericMethods.clickOnIOSElement((IOSElement) getBanner(),"clicked on Banner");
	}

	/**
	 * @author 300019221 / aravinda
	 * Added Try block
	 * @param i
	 */
	public void clickOnbanner(int i) {
		try {
			getBanner().get(i).click();
			System.out.println("User clicked on Banner!");
		} catch (Exception e) {
			System.out.println("Banner not found!");
			e.printStackTrace();
		}

	}

	public void popularityButton() {
		objiOSGenericMethods.clickOnIOSElement(getPopularityButton(),"clicked on PopularityButton");
	}

	public void saveButton() {
		objiOSGenericMethods.clickOnIOSElement(getSaveButton(),"clicked on SaveButton");
	}

	public void nestedBanner() {
		objiOSGenericMethods.clickOnIOSElement(getNestedBanner(),"clicked on NestedBanner");
	}

	public void clickOnSearchButton() throws InterruptedException {
//		Thread.sleep(1000);
		if (searchButton.isDisplayed()) {
			objiOSGenericMethods.clickOnIOSElement(getSearchButton(),"clicked on searchButton");
			System.out.println("User has clicked on search icon!");
		}
	}

	public IOSElement setSearchPlaceholder(String search) throws InterruptedException {
//		Thread.sleep(1500);
		if (searchPlaceholder.isDisplayed()) {
			searchPlaceholder.sendKeys(search);
			System.out.println("User is has typed ");
		}
		return searchPlaceholder;
	}

	public List<IOSElement> getSearchAutoSuggestList(int i) throws InterruptedException {
//		Thread.sleep(1000);
		System.out.println("Total number of search items " + searchAutoSuggestList.size());
		for (Iterator iterator = searchAutoSuggestList.iterator(); iterator.hasNext();) {
			IOSElement iosElement = (IOSElement) iterator.next();
			System.out.println("List of autosuggestion --> " + iosElement.getText());
		}
		searchAutoSuggestList.get(i).click();
		return searchAutoSuggestList;
	}

//	public void getNestedBannerList(int i) {
//		System.out.println("Total number of nested banners " + nestedBannerList.get(i).getText());
//		nestedBannerList.get(i).click();
//	}
	
	/**
	 * @author 300019221 / aravindanath
	 * Modified method getNestedBannerList to ClickOnNestedBannerList
	 * @param i
	 */
	
	
	public void clickOnNestedBannerList(int i) {
	//	System.out.println("Total number of nested banners " + nestedBannerList.get(i).getText());
		try {
			nestedBannerList.get(i).click();
			System.out.println("User cliked on  nested banner!");
			
		} catch (Exception e) {
			System.out.println("Nested banner not found!");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/* Method to verify auto suggestion list */
	
	public void VerifyAutoSuggestionList()
	{
		List<IOSElement> autoList = getSearchAutoSuggestList();

		int s = autoList.size();
		if(s>=0)
		{
			Reporter.log("Passed : Able to Enter in search Field");
			Reporter.log("Passed : Auto Suggestion List is displayed with count =" + s);
		}
		else
		{
			Reporter.log("Failed : Auto Suggestion List is not displayed.");
		}

		for(IOSElement search : autoList)
		{
			IOSElement productName = (IOSElement)search.findElement(By.className("search-result-name"));
			IOSElement productCount = (IOSElement)search.findElement(By.className("search-result-count"));
			objiOSGenericMethods.VerifyStringShouldNotEmpty(productName, productName.getText());
			objiOSGenericMethods.VerifyNumberShouldNotZeroOrLess(productCount);
		}

	}

}
