package com.automation.mobile.Android.MobileWeb.ObjectRepository.PLPageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;

import com.automation.core.mobile.Android.AndroidGenericMethods;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

/**
 * Modified By:Aishurya:Changed string parameter,which is being used in reporting
 */
public class FilterPageObjects {

	AndroidGenericMethods objAndroidGenericMethods;

	public FilterPageObjects(AndroidDriver<AndroidElement> aDriver) {
		PageFactory.initElements(new AppiumFieldDecorator(aDriver), this);
		objAndroidGenericMethods = new AndroidGenericMethods(aDriver);
	}

	@FindBy(xpath = ".//*[text()='FILTER']")
	public AndroidElement FilterButton;
	/**
	 * @author unkn
	 * @modified- Rakesh Reason: Wrong Xpath written Changed:
	 *            //ul/div/li/label[contains(text(),'Discount')]
	 *            Reason2: corrected button instead label
	 */
	@FindBy(xpath = "//label[contains(text(),'Discount')]")
	public AndroidElement DiscountButtonInFilter;

	@FindBy(xpath = "(//label[@class='customCheckbox'])[1]")
	public AndroidElement FilterOption;

	@FindBy(xpath = ".//*[text()='Apply']")
	public AndroidElement ApplyButton;
	/**
	 * @author- unknow
	 * @modified01: Rakesh Change because the path is wrong
	 * @FindAll({ @FindBy(xpath = "//ul[@class='filtersPane']/div[6]/li/label") })
	 * @anu //label[text()='Discount']
	 */
	@FindBy(xpath = "//div[@class='ripple-container ']/li[label ='Discount']")
	public AndroidElement Discount;

	@FindAll({ @FindBy(xpath = "//*[@class='searchProduct']") })
	public List<AndroidElement> allItems;

	@FindBy(xpath = ".//*[text()='SORT']")
	public AndroidElement SortButton;
	
	/**
	 * @author- Amba
	 * @written xpath for Latest sort with getter and click method: xpath was not written
	 */
	@FindBy(xpath = ".//*[text()='Latest']")
	public AndroidElement Latest;

	@FindBy(xpath = "//button[contains(text(),\"Discount\")]")
	public AndroidElement DiscountButton;
	/**
	 * @author- unknow
	 * @modified01: Anu Change because the path is wrong
	 */
	@FindBy(xpath = ".//button/i[@class='icon-pricehightolow text-md']")
	public AndroidElement PriceHightoLow;
	

	@FindBy(xpath = ".//button/i[@class='icon-popular text-md']")
	public AndroidElement Popularity;
	public AndroidElement getLatest() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(Latest, "Latest");
		return Latest;
	}

	public AndroidElement getDiscount() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(Discount, "Discount");
		return Discount;
	}

	public AndroidElement getFilterButton() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(FilterButton, "FilterButton");
		return FilterButton;
	}

	public AndroidElement getDiscountButtonInFilter() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(DiscountButtonInFilter, "DiscountButtonInFilter");
		return DiscountButtonInFilter;
	}

	public AndroidElement getFilterOption() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(FilterOption, "FilterOption");
		return FilterOption;
	}

	public AndroidElement getApplyButton() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(ApplyButton, "ApplyButton");
		return ApplyButton;
	}

	public AndroidElement getPopularity() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(Popularity, "Popularity");
		return Popularity;
	}

	public AndroidElement getPriceHightoLow() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(PriceHightoLow, "PriceHightoLow");
		return PriceHightoLow;
	}

	public AndroidElement getSortButton() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(SortButton, "SortButton");
		return SortButton;
	}

	public AndroidElement getDiscountButton() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(DiscountButton, "DiscountButton");
		return DiscountButton;
	}

	public void clickOnSortButton()  {
		objAndroidGenericMethods.clickOnAndroidElement(getSortButton(), "SortButton");
	}

	public void clickOnDiscountButton() {
		objAndroidGenericMethods.clickOnAndroidElement(getDiscountButton(), "DiscountButton");
	}
	public void clickOnLatest() {
		objAndroidGenericMethods.clickOnAndroidElement(getLatest(), "LatestButton");
	}

	public void clickOnpriceHightoLow()  {
		if (PriceHightoLow.isDisplayed()) {
			PriceHightoLow.click();
		}
	}

	public void clickOnPopularity() {
		objAndroidGenericMethods.clickOnAndroidElement(getPopularity(), "Popularity");
	}

	public void clickOnFilterButton()  {
		objAndroidGenericMethods.clickOnAndroidElement(getFilterButton(), "FilterButton");
	}

	public void clickOnDiscountButtonInFilter()  {
		objAndroidGenericMethods.clickOnAndroidElement(getDiscountButtonInFilter(),
				"DiscountButtonInFilter");
	}

	public void clickOnFilterOption()  {
		getFilterOption().isDisplayed();
	}

	public void clickOnApplyButton()  {
		objAndroidGenericMethods.clickOnAndroidElement(getApplyButton(), "ApplyButton");
	}

	public void clickOnDiscount()  {
			objAndroidGenericMethods.clickOnAndroidElement(getDiscount(), "Discount");
	}
	/*
	 * For verifying amount after discount 300021281
	 */

	public void getVerifyAmountAfterDiscount(String ProductCode) {

		System.out.println("Total Items present --> " + allItems.size());
		System.err.println(allItems.get(1).getText());

		for (AndroidElement result : allItems) {
			AndroidElement link = (AndroidElement) result.findElement(By.tagName("a"));
			String hrefString = link.getAttribute("href");
			// System.out.println("Item"+count+" href="+hrefString);
			if (hrefString.contains(ProductCode)) {
				System.out.println("Element found with href" + hrefString);
				link.click();
				// System.out.println(link.getText());
				break;
			}
		}

	}

	/**
	 * @author 300021278 Rakesh Path to click on first option displayed in discount
	 *         section
	 */
	@FindBy(xpath = "(//label[@class='customCheckbox'])[1]")
	public AndroidElement selectFirstDiscountOption;

	public AndroidElement getselectFirstDiscountOption() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(selectFirstDiscountOption, "selectFirstDiscountOption");
		return selectFirstDiscountOption;
	}

	/**
	 * @author 300021278 Rakesh Method to click on first option displayed in
	 *         discount section
	 */
	public void clickOnFirstDiscountOption() {
		objAndroidGenericMethods.clickOnAndroidElement(getselectFirstDiscountOption(), "selectFirstDiscountOption");
	}

	/**
	 * Object for price
	 * 
	 * @author 300021278 -Rakesh
	 */
	@FindAll({ @FindBy(xpath = "//ul[@class='filtersPane']/div[4]/li/label") })
	public AndroidElement Price;

	/**
	 * getter for price
	 * 
	 * @author 300021278 -Rakesh
	 */
	public AndroidElement getPrice() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(Price, "Price");
		return Price;
	}

	/**
	 * Method to click on price
	 * 
	 * @author 300021278 -Rakesh
	 */
	public void clickOnPrice() {

		if (Price.isDisplayed()) {
			objAndroidGenericMethods.clickOnAndroidElement(getPrice(), "Price");
		}
	}
	/**
	 * Object identified for the filter options displayed after selecting a filter;
	 * @ModifiedBy:-Rakesh
	 */
	@FindAll({ @FindBy(xpath = "//ul[@class='filterOptions']/li") })
	public List<AndroidElement> optionsInsideFilter;
	
	public List<AndroidElement> getOptionsInsideFilter() {
		objAndroidGenericMethods.CheckAndroidElementsListFoundMWeb(optionsInsideFilter, "DiscountButton");
		return optionsInsideFilter;
	}
	
	/**
	 * @author 300021278 - Rakesh
	 * Description: This method will select the first option from the filter list
	 */
	public void selectOptionsInsideFilter() {
		List<AndroidElement> searchResultList = getOptionsInsideFilter();
		for (AndroidElement result : searchResultList) {
			AndroidElement div = (AndroidElement) result.findElement(By.tagName("div"));
			String filterOption = div.getText();
			System.out.println(filterOption);
			Reporter.log(filterOption);
			objAndroidGenericMethods.clickOnAndroidElement(div, "FirstOption");
			break; 
		}
	}
	/**
	 * Method to verify the filter options displayed after selecting a filter
	 * @author 300021278 -Rakesh
	 */
	public void verifyFilterOptions() {
		List<AndroidElement> searchResultList = getOptionsInsideFilter();
		for (AndroidElement result : searchResultList) {
			AndroidElement label = (AndroidElement) result.findElement(By.tagName("label"));
			String filterOption = label.getText();
			System.out.println("The selected filter option contains" + filterOption + " is the count");
			Reporter.log("The selected filter option contains" + filterOption);
		}
	}
	/**
	 * @author 300021278 -Rakesh
	 */
	@FindBy(xpath = ".//button/i[@class='icon-pricelowtohigh text-md']")
	public AndroidElement PriceLowToHigh;
	
	public AndroidElement getPriceLowToHigh() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(PriceLowToHigh, "PriceLowToHigh");
		return PriceLowToHigh;
	}
	public void clickOnPriceHightoLow() {
			getPriceHightoLow().click();
	}
}