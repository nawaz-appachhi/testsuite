package com.automation.mobile.Android.MobileWeb.ObjectRepository.HomeObjects;

import java.util.Iterator;
import java.util.List;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.automation.core.mobile.Android.AndroidGenericMethods;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class HomePageObjects {

	AndroidGenericMethods objAndroidGenericMethods;
	public HomePageObjects(AppiumDriver<MobileElement> aDriver) {
		PageFactory.initElements(new AppiumFieldDecorator(aDriver), this);
		objAndroidGenericMethods = new AndroidGenericMethods(aDriver);
	}

	/**
	 * Object Identified for the hamburger icon displayed in leftheader
	 * @author 300021278 -Rakesh
	 */
	@FindBy(xpath = "//span[@class='icon icon-menu']")
	public AndroidElement hamburgerIcon;

	/**
	 * Object Identified for the search icon displayed in header
	 * @author 300021278 -Rakesh
	 */
	@FindBy(id = "header-search-icon")
	public AndroidElement searchIcon;
	
	/**
	 * Object Identified for the search field displayed after clicking on search icon
	 * @author 300021278 -Rakesh
	 */
	@FindBy(xpath = "//div[@class='search-wrap']//input[@class='form-control search-box-input']")
	public AndroidElement enterInSeacrchField;

	/**
	 * Object Identified for the wishlist icon displayed in header
	 * @author 300021278 -Rakesh
	 */
	@FindBy(xpath = "//a[@href='/wishlist' and @id='header-wishlist-icon']")
	public AndroidElement wishlistIcon;

	/**
	 * Object Identified for the cart/bag icon displayed in header
	 * @author 300021278 -Rakesh
	 */
	@FindBy(xpath = "//span[@class='icon icon-bag']")
	public AndroidElement cartIcon;

	/*
	 * Banner
	 */

	@FindBy(xpath = "//*[@id='mountRoot']/div/main/div/div[6]/div/div/div/div/div[3]/div/div/div/div/div/a/div/div[1]/div/div/div/img")
	public List<AndroidElement> banner;

	@FindBy(xpath = "//*[@id=\"reactPageContent\"]/div/div/div[4]/div[2]/div/div/a/div/div/picture/img")
	public AndroidElement nestedBanner;

	@FindBy(xpath = "//*[@class='img-responsive']/img")
	public List<AndroidElement> nestedBannerList;

	@FindBy(xpath = "//button[contains(text(),'Popularity')]")
	public AndroidElement popularityButton;

	@FindBy(xpath = "//*[@id='reactPageContent']/div/div/div/div/ul/li[1]/div/div[1]/a/div[2]/div[2]")
	public AndroidElement saveButton;

	@FindBy(xpath = "//a[@id='header-search-icon']/span")
	public AndroidElement searchButton;

	@FindBy(xpath = "//div[@class='search-wrap']/input[@placeholder='Search for products' or @class='form-control search-box-input']")
	public AndroidElement searchPlaceholder;

	@FindAll({ @FindBy(xpath = "//div[@class='search-results-cont']/div") })
	public List<AndroidElement> searchAutoSuggestList;
	/**
	 * modified by: Rakesh
	 * Reason: Xpath is wrong
	 * old: //a[@class='myntraweb-sprite mobile-mBag sprites-bag']
	 */
	@FindBy(xpath = "//span[@class='icon icon-bag']")
	public AndroidElement bagIcon;
	
	
	
	public AndroidElement getBagIcon() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(bagIcon, "BagIcon");
		return bagIcon;
	}

	public AndroidElement getHamburgerIcon() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(hamburgerIcon, "hamburgerIcon");
		return hamburgerIcon;
	}

	public AndroidElement getSearchIcon() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(searchIcon, "searchIcon");
		return searchIcon;
	}

	public AndroidElement getEnterInSeacrchField() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(enterInSeacrchField, "enterInSeacrchField");
		return enterInSeacrchField;
	}

	public AndroidElement getWishlistIcon() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(wishlistIcon, "wishlistIcon");
		return wishlistIcon;
	}

	public AndroidElement getCartIcon() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(cartIcon, "cartIcon");
		return cartIcon;
	}

	public List<AndroidElement> getBanner() {
		objAndroidGenericMethods.CheckAndroidElementsListFoundMWeb(banner, "banner");
		return banner;
	}

	public AndroidElement getNestedBanner() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(nestedBanner, "nestedBanner");
		return nestedBanner;
	}

	public List<AndroidElement> getNestedBannerList() {
		objAndroidGenericMethods.CheckAndroidElementsListFoundMWeb(nestedBannerList, "nestedBannerList");
		return nestedBannerList;
	}

	public AndroidElement getPopularityButton() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(popularityButton, "popularityButton");
		return popularityButton;
	}

	public AndroidElement getSaveButton() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(saveButton, "saveButton");
		return saveButton;
	}

	public AndroidElement getSearchButton() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(searchButton, "searchButton");
		return searchButton;
	}

	public AndroidElement getSearchPlaceholder() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(searchPlaceholder, "searchPlaceholder");
		return searchPlaceholder;
	}

	public List<AndroidElement> getSearchAutoSuggestList() {
		objAndroidGenericMethods.CheckAndroidElementsListFoundMWeb(searchAutoSuggestList, "searchAutoSuggestList");
		return searchAutoSuggestList;
	}

	/**
	 * Modified By:Aishurya:Changed string parameter,which is being used in reporing
	 */
	public void clickOnHamburgerButton()  {
		objAndroidGenericMethods.clickOnAndroidElement(getHamburgerIcon(),"HamburgerIcon");
	}

	/**
	 * Modified By:Aishurya:Changed string parameter,which is being used in reporing
	 */
	public void clickOnSearchIcon()  {
		objAndroidGenericMethods.clickOnAndroidElement(getSearchIcon(),"SearchIcon");
	}

	public void enterSearchItem(String Bname) {
		getEnterInSeacrchField().sendKeys(Bname);
		getEnterInSeacrchField().sendKeys(Keys.ENTER);
	}

	/**
	 * Modified By:Aishurya:Changed string parameter,which is being used in reporing
	 */
	public void clickOnWishlistButton() {
		objAndroidGenericMethods.clickOnAndroidElement(getWishlistIcon(),"WishlistIcon");
	}

	/**
	 * Modified By:Aishurya:Changed string parameter,which is being used in reporing
	 */
	public void clickOnCartIcon()  {
		objAndroidGenericMethods.clickOnAndroidElement(getCartIcon(),"CartIcon");
	}
	public List<AndroidElement> clickOnbanner(int i) {
		try {
		getBanner().get(i).click();
		System.out.println("clicked on banner1");
		} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}
		return banner;
		}
	//public void clickOnbanner() {
		//objAndroidGenericMethods.clickOnAndroidElement(getBanner(),"Banner");
	//}
	
	/**
	 * Modified By:Aishurya:Changed string parameter,which is being used in reporing
	 */
	
	public void popularityButton() {
		objAndroidGenericMethods.clickOnAndroidElement(getPopularityButton(),"PopularityButton");
	}

	/**
	 * Modified By:Aishurya:Changed string parameter,which is being used in reporing
	 */
	public void saveButton()  {
		objAndroidGenericMethods.clickOnAndroidElement(getSaveButton(),"SaveButton");
	}
	
	/**
	 * Modified By:Aishurya:Changed string parameter,which is being used in reporing
	 */
	public void nestedBanner() {
		objAndroidGenericMethods.clickOnAndroidElement(getNestedBanner(),"NestedBanner");
	}

	/**
	 * Modified By:Aishurya:Changed string parameter,which is being used in reporing
	 */
	public void clickOnSearchButton()  {
		if (searchButton.isDisplayed()) {
			objAndroidGenericMethods.clickOnAndroidElement(getSearchButton(),"SearchButton");
			System.out.println("User has clicked on search icon!");
		}
	}

	public AndroidElement setSearchPlaceholder(String search)  {
		if (searchPlaceholder.isDisplayed()) {
			searchPlaceholder.sendKeys(search);
			System.out.println("User is has typed ");
		}
		return searchPlaceholder;
	}

	public List<AndroidElement> getSearchAutoSuggestList(int i)  {
		System.out.println("Total number of search items " + searchAutoSuggestList.size());
		for (Iterator iterator = searchAutoSuggestList.iterator(); iterator.hasNext();) {
			AndroidElement AndroidElement = (AndroidElement) iterator.next();
			System.out.println("List of autosuggestion --> " + AndroidElement.getText());
		}
		searchAutoSuggestList.get(i).click();
		return searchAutoSuggestList;
	}

	public void getNestedBannerList(int i) {
		System.out.println("Total number of nested banners " + nestedBannerList.get(i).getText());
		nestedBannerList.get(i).click();
	}
	
	/**
	 * Modified By:Aishurya:Changed string parameter,which is being used in reporing
	 */
	public void clickOnBagIcon() {
		objAndroidGenericMethods.clickOnAndroidElement(getBagIcon(),"BagIcon");
	}
//********************************Assertions***************************************
	
	@FindBy(xpath = "//div[@class='search-wrap']//input[@class='form-control search-box-input']")
	public AndroidElement SearchTextPresent;	
	
	public void assertSearchText() {
		objAndroidGenericMethods.VerifyTwoString(getEnterInSeacrchField(),"Search text Present");
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(SearchTextPresent,"SearchTextPresent");
		
		
	}
	@FindBy(xpath = "//*[@id=\"reactPageContent\"]/div/div/div[3]/div/a/div/div/picture/img")
	public AndroidElement verifyBigBannerView;	
	
	public void verifyBigBannerView() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(verifyBigBannerView,"Big Banner Verified");
		
	}
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
}