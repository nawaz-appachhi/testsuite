package com.automation.mobile.Android.apps.ObjectRepository.Home;

import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;

import com.automation.core.mobile.Android.AndroidGenericMethods;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.AndroidKeyCode;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class HomePageObject {
	public AndroidDriver<AndroidElement> aDriver;
	AndroidGenericMethods objAndroidGenericMethods;

	public HomePageObject(AndroidDriver<AndroidElement> aDriver) {
		PageFactory.initElements(new AppiumFieldDecorator(aDriver), this);
		objAndroidGenericMethods = new AndroidGenericMethods(aDriver);
	}

	@FindBy(xpath = "//android.widget.ImageView[@index='0']")
	public List<AndroidElement> BigBannerView;

	@FindBy(xpath = "//android.widget.ImageView[@index='4]")
	public AndroidElement NestedBannerView;
    /**
     *  
     * modified by 300021280 Sneha
     * old xpath ://android.view.ViewGroup[@index='3']
     */
	@FindBy(xpath = "//android.widget.ImageView[@content-desc='rightElement0']")
	public AndroidElement searchicon;

	@FindBy(xpath = "//*[@index='3']/*[@index='3']/android.widget.ImageView")
	public AndroidElement searchicon1;

	@FindBy(xpath = "//android.widget.EditText[@content-desc ='search_default_search_text_input']")
	public AndroidElement SearchText;

	@FindBy(xpath = "//android.widget.TextView[@text=' MEN ']")
	public AndroidElement searchOptionMen;
	/**
	 * @author 300021278 -Rakesh
	 */
	@FindBy(xpath = "//android.widget.ImageView[@content-desc='rightElement3']")
	public AndroidElement RHWishlistbtn;

	/*
	 * //*[@text='Clothing']
	 * 
	 * Modified by subhasis
	 * 
	 */

	@FindBy(xpath = "//android.widget.TextView[@text='Clothing']")
	public AndroidElement searchOptionClothing;

	@FindBy(xpath = "//android.widget.TextView[@text='Topwear']")
	public AndroidElement searchClothingTopwear;

	@FindBy(xpath = "//android.widget.TextView[@text='T-Shirts']")
	public AndroidElement searchTShirts;
	/**
	 * Object identified for the banner displayed after selecting t shirts in
	 * search;
	 * 
	 * @author 300021278 -Rakesh
	 * @ModifiedBy:-Rakesh Reason: Alternative xapth
	 */
	// @FindBy(xpath = "//android.view.ViewGroup[@index='0']")
	@FindBy(xpath = "(//android.widget.ImageView[@index='0'])[4]")
	public AndroidElement SeachTopImage;
	/**
	 * Object identified for bag button displayed in header;
	 * 
	 * @author 300021278 -Rakesh
	 */
	@FindBy(xpath = "//android.widget.ImageView[@content-desc='rightElement1']")
	public AndroidElement bagbtn;
	
	@FindBy(xpath="//android.widget.Switch[@text='OFF']")
	public AndroidElement grantPermission;

	/***************** getters *********************/

	/**
	 * getter for wishlist button displayed in the header;
	 * 
	 * @author 300021278 -Rakesh
	 * @return
	 */
	public AndroidElement getRHWishlistbtn() {
		objAndroidGenericMethods.CheckAndroidElementFound(RHWishlistbtn, "RHwishlistbtn");
		return RHWishlistbtn;
	}
	public AndroidElement getGrantPermission() {
		return grantPermission;
	}
	public AndroidElement getBagbtn() {
		objAndroidGenericMethods.CheckAndroidElementFound(bagbtn, "bagbtn");
		return bagbtn;
	}

	public List<AndroidElement> getBigBannerView() {
		return BigBannerView;
	}

	public AndroidElement getNestedBannerView() {
		objAndroidGenericMethods.CheckAndroidElementFound(NestedBannerView, "NestedBannerView");
		return NestedBannerView;
	}

	public AndroidElement getSearchicon() {
		objAndroidGenericMethods.CheckAndroidElementFound(searchicon, "searchicon");
		return searchicon;
	}

	public AndroidElement getSearchicon1() {
		objAndroidGenericMethods.CheckAndroidElementFound(searchicon, "searchicon");
		return searchicon1;
	}

	public AndroidElement getSearchText() {
		objAndroidGenericMethods.CheckAndroidElementFound(SearchText, "SearchText");
		return SearchText;
	}

	public AndroidElement getSearchOptionMen() {
		objAndroidGenericMethods.CheckAndroidElementFound(searchOptionMen, "searchOptionMen");
		return searchOptionMen;
	}

	public AndroidElement getSearchOptionClothing() {
		objAndroidGenericMethods.CheckAndroidElementFound(searchOptionClothing, "searchOptionClothing");
		return searchOptionClothing;
	}

	public AndroidElement getSearchClothingTopwear() {
		objAndroidGenericMethods.CheckAndroidElementFound(searchClothingTopwear, "searchClothingTopwear");
		return searchClothingTopwear;
	}

	public AndroidElement getSearchTShirts() {
		objAndroidGenericMethods.CheckAndroidElementFound(searchTShirts, "searchTShirts");
		return searchTShirts;
	}

	public AndroidElement getSearchTopImage() {
		objAndroidGenericMethods.CheckAndroidElementFound(SeachTopImage, "SeachTopImage");
		return SeachTopImage;
	}

	/******************************** methods *************************/
	/**
	 * Method to click on wishlist button displayed in home page at header section;
	 * 
	 * @author 300021278 -Rakesh
	 */
	public void clickRHWishlistbtn() {
		objAndroidGenericMethods.clickOnAndroidElement(getRHWishlistbtn(), "clicked on RHwishlist btn");
	}

	public void clickBagbtn() {
		objAndroidGenericMethods.clickOnAndroidElement(getBagbtn(), "clicked on bag btn");
	}

	public void clickOnSearch() { 
		objAndroidGenericMethods.clickOnAndroidElement(getSearchicon(), "clicked on search");
	}

	public void clickOnSearch1() {
		objAndroidGenericMethods.clickOnAndroidElement(getSearchicon1(), "clicked on search");
	}

	public void enterSearchText(String text) {
		objAndroidGenericMethods.enterTexAndroidElement(getSearchText(), text, "enter the search text");
	}

	public List<AndroidElement> clickBigBanner(int i) {
		try {
			getBigBannerView().get(i).click();
			System.out.println("clicked on banner1");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return BigBannerView;
	}

	public void clickSearchOptionMen() {
		objAndroidGenericMethods.clickOnAndroidElement(getSearchOptionMen(), "clicked on men search");
	}

	public void clickSearchOptionClothing() {
		objAndroidGenericMethods.clickOnAndroidElement(getSearchOptionClothing(), "clicked on men clothing search");
	}

	public void clickSearchClothingTopwear() {
		objAndroidGenericMethods.clickOnAndroidElement(getSearchClothingTopwear(),
				"clicked on men clothing top wear search");
	}

	public void clickSearchTShirts() {
		objAndroidGenericMethods.clickOnAndroidElement(getSearchTShirts(), "clicked on men clothing T shirts search");
	}

	public void clickSearchTopImage() {
		objAndroidGenericMethods.clickOnAndroidElement(getSearchTopImage(), "clicked on men TopImage search");
	}

	public void clickNestedBanner() {
		// objAndroidGenericMethods.clickOnAndroidElement(NestedBannerView, "clicked on
		// nested banner");
		TouchAction act = new TouchAction(aDriver);
		act.tap(532, 1108).perform();
	}

	public void doubleClick() throws InterruptedException {
		//// android.widget.TextView
		// Object row =
		//// driver.findElements(By.xpath("//android.widget.TextView")).get(4);
		HashMap<String, Double> tapObject = new HashMap<String, Double>();
		tapObject.put("x", 0.5); // in pixels from left
		tapObject.put("y", 0.5); // in pixels from top
		tapObject.put("tapCount", 2.0); // double tap
		// tapObject.put("element", ((RemoteWebElement) row).getId()); // the id of the
		// element we want tso tap
		JavascriptExecutor js = (JavascriptExecutor) aDriver;
		js.executeScript("mobile: tap", tapObject);
	}
	public void clickGrantPermission() {
		try {
			getGrantPermission().click();
			Reporter.log("Got Grant permission screen");
			Thread.sleep(2000);
			aDriver.navigate().back();
		//	aDriver.pressKeyCode(AndroidKeyCode.BACK);

		} catch (Exception e) {
			Reporter.log("grant permission screen was not displayed");
		}
	}
	// ********************************Assertions***************************************

	@FindBy(xpath = "//android.widget.EditText[@text ='Search for brands & products']")
	public AndroidElement SearchTextPresent;

	public void assertSearchText(String text) {
		objAndroidGenericMethods.VerifyTwoString(getSearchText(), text);
		objAndroidGenericMethods.CheckAndroidElementFound(SearchTextPresent, "SearchTextPresent");

	}

	@FindBy(xpath = "//android.widget.ImageView[@index='0']")
	public AndroidElement verifyBigBannerView;

	public void verifyBigBannerView() {
		objAndroidGenericMethods.CheckAndroidElementFound(verifyBigBannerView, "Big Banner Verified");

	}

	public void AutoSearch() {
		List<AndroidElement> allElements = aDriver
				.findElements(By.xpath("//android.widget.EditText[@content-desc ='search_default_search_text_input']"));

		for (AndroidElement element : allElements) {
			System.out.println(element.getText());
			if (((List<AndroidElement>) element).contains("Nike")) {
				break;
			}
		}

	}

	public void verifySearchText() {
		objAndroidGenericMethods.VerifyTwoString(SearchTextPresent, "Search for brands & products");
	}

}
