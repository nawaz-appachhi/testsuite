package com.automation.mobile.iOS.MobileWeb.ObjectRepository.PLPageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.automation.core.mobile.iOS.iOSGenericMethods;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindAll;

public class FilterPageObjects {
	
	public AppiumDriver<MobileElement>  iDriver;
	iOSGenericMethods objiOSGenericMethods;

	public FilterPageObjects(AppiumDriver<MobileElement> iDriver) {
		PageFactory.initElements(new AppiumFieldDecorator(iDriver), this);
		objiOSGenericMethods = new iOSGenericMethods(iDriver);

	}
	
	/**
	 * @author 300019221 Aravindanath
	 * Modified xpath
	 */

	@FindBy(xpath = "//button[contains(text(),'FILTER')]")
	public IOSElement FilterButton;

	/**
	 * modified by madhu 
	 * xpath of DiscountButtonInFilter before //div[@class='ripple-container ']/button)[3]
	 * after //label[contains(text(),'Discount')]
	 * Modified By subhasis
	 * //label[contains(text(),'Discount')]
	 */
	
	@FindBy(xpath = "//button[contains(text(),'Discount')]")
	//@FindBy(xpath="(//div[@class='ripple-container ']/button)[3]")
	public IOSElement DiscountButtonInFilter;

	@FindBy(xpath = "(//label[@class='customCheckbox'])[1]")
	public IOSElement FilterOption;

	@FindAll({ @FindBy(xpath = "//ul[@class=\"filterOptions\"]") })
	public List<IOSElement> optionsInsideFilter;
	
	@FindBy(xpath = ".//*[text()='Apply']")
	public IOSElement ApplyButton;
	
	
	/**
	 * Modified By Subhasis
	 * //ul[@class='filtersPane']/div[6]/li/label
	 */

	@FindAll({ @FindBy(xpath = "//label[contains(text(),'Discount')]//..//..") })
	public IOSElement Discount;

	/**
	 * @author Subhasis
	 * Modified xpath "//label[contains(text(),'Price')]") })
	 */
	
	@FindAll({ @FindBy(xpath = "//label[contains(text(),'Price')]//..//..") })
	public IOSElement Price;
	
	/**
	 * Created By Subhasis
	 * This xpath for Gender
	 * Modified by subhasis
	 * //ul[@class='filtersPane']/div[1]/li/label
	 */
	@FindAll({ @FindBy(xpath = "//label[contains(text(),'Gender')]//..//..") })
	public IOSElement Gender;

	/**
	 * Second Price xpath
	 * Added By Subhasis
	 */
	
	@FindBy(xpath="//ul[@class='filterOptions']/li[2]")
	public IOSElement secondPrice;
	
	@FindBy(xpath="//ul[@class='filterOptions']/li[1]")
	public IOSElement menOption;

	@FindAll({ @FindBy(xpath = "//*[@class='searchProduct']") })
	public List<IOSElement> allItems;

	// @FindBy(xpath = "//button[contains(text(),'SORT')]")
	@FindBy(xpath = ".//*[text()='SORT']")
	public IOSElement SortButton;

	@FindBy(xpath = "//button[contains(text(),\"Discount\")]")
	public IOSElement DiscountButton;

	@FindBy(xpath = ".//button/i[@class='icon-pricehightolow text-md']")
	public IOSElement PriceHightoLow;
	
	@FindBy(xpath = ".//button/i[@class='icon-pricelowtohigh text-md']")
	public IOSElement PriceLowToHigh;

	@FindBy(xpath = ".//button/i[@class='icon-popular text-md']")
	public IOSElement Popularity;
	
	@FindBy(xpath = "(//label[@class='customCheckbox'])[3]")
	public IOSElement selectFirstDiscountOption;
	
	@FindBy(xpath = "(//div[@class='price-container']/span[1])[1]")
	public IOSElement discountedPrice;
	

	public IOSElement getselectFirstDiscountOption() {
		objiOSGenericMethods.CheckIOSElementFound(selectFirstDiscountOption, "selectFirstDiscountOption");
		return selectFirstDiscountOption;
	}
	
	public IOSElement getDiscountedPrice() {
		objiOSGenericMethods.CheckIOSElementFound(discountedPrice, "discountedPrice");
		return discountedPrice;
	}
	
	public IOSElement getPrice() {
		objiOSGenericMethods.CheckIOSElementFound(Price, "Price");
		return Price;
	}
	
	/**
	 * Getter of second price
	 * Added By subhasis
	 * 
	 */
	
	public IOSElement getSecondPrice() {
		objiOSGenericMethods.CheckIOSElementFound(secondPrice, "Secaond Price");
		return secondPrice;
	}
	
	
	/**
	 * Getter of Gender
	 * Added By subhasis
	 */
	
	public IOSElement getGender() {
		objiOSGenericMethods.CheckIOSElementFound(getGender(), "Gender");
		return Gender;
	}
	
	public IOSElement getMenOption() {
		objiOSGenericMethods.CheckIOSElementFound(getMenOption(), "Men");
		return menOption;
	}
	

	public IOSElement getDiscount() {
		objiOSGenericMethods.CheckIOSElementFound(Discount, "Discount");
		return Discount;
	}
	
	public IOSElement getFilterButton() {
		objiOSGenericMethods.CheckIOSElementFound(FilterButton, "FilterButton");
		return FilterButton;
	}

	public IOSElement getDiscountButtonInFilter() {
		objiOSGenericMethods.CheckIOSElementFound(DiscountButtonInFilter, "DiscountButtonInFilter");
		return DiscountButtonInFilter;
	}

	public IOSElement getFilterOption() {
		objiOSGenericMethods.CheckIOSElementFound(FilterOption, "FilterOption");
		return FilterOption;
	}

	public IOSElement getApplyButton() {
		objiOSGenericMethods.CheckIOSElementFound(ApplyButton, "ApplyButton");
		return ApplyButton;
	}

	public IOSElement getPopularity() {
		objiOSGenericMethods.CheckIOSElementFound(Popularity, "Popularity");
		return Popularity;
	}

	public IOSElement getPriceHightoLow() {
		objiOSGenericMethods.CheckIOSElementFound(PriceHightoLow, "PriceHightoLow");
		return PriceHightoLow;
	}
	
	public IOSElement getPriceLowToHigh() {
		objiOSGenericMethods.CheckIOSElementFound(PriceLowToHigh, "PriceLowToHigh");
		return PriceLowToHigh;
	}

	public IOSElement getSortButton() {
		objiOSGenericMethods.CheckIOSElementFound(SortButton, "SortButton");
		return SortButton;
	}

	public IOSElement getDiscountButton() {
		objiOSGenericMethods.CheckIOSElementFound(DiscountButton, "DiscountButton");
		return DiscountButton;
	}

	public List<IOSElement> getOptionsInsideFilter() {
		objiOSGenericMethods.CheckIOSElementsListFound(optionsInsideFilter, "DiscountButton");
		return optionsInsideFilter;
	}
	
	public void clickOnSortButton() {
		objiOSGenericMethods.waitDriver(getSortButton(),"Sort Button");
		if (getSortButton().isDisplayed()) {
			objiOSGenericMethods.clickOnIOSElement(getSortButton(), "Sort Button");
			System.out.println("User has clicked on sort");
		}
	}

	public void clickOnDiscountButton() {
		objiOSGenericMethods.clickOnIOSElement(getDiscountButton(), "Discount Button");
	}

	public void clickOnPriceHightoLow() {
		if (PriceHightoLow.isDisplayed()) {
			getPriceHightoLow().click();
		}
	}
	
	public void clickOnPriceLowToHigh() {
		if (PriceLowToHigh.isDisplayed()) {
			getPriceLowToHigh().click();
		}
	}

	public void clickOnPopularity() {
		objiOSGenericMethods.clickOnIOSElement(getPopularity(), "Popularity");
	}

	/**
	 * @author 300019221 / Aravindanath
	 * Added try block
	 * 
	 * used JS executor
	 */
	public void clickOnFilterButton() {
		try {
			objiOSGenericMethods.waitDriver(getFilterButton(),"Filter Button");
			if (getFilterButton().isDisplayed()) {
			//	objiOSGenericMethods.click(getFilterButton());
				objiOSGenericMethods.clickOnIOSElement(getFilterButton(), "FilterButton");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void clickOnDiscountButtonInFilter() {
		objiOSGenericMethods.clickOnIOSElement(getDiscountButtonInFilter(), "Discount Button In Filter");
	}

	public void clickOnFilterOption() {
		getFilterOption().isDisplayed();
	}

	public void clickOnApplyButton() {
		objiOSGenericMethods.clickOnIOSElement(getApplyButton(), "ApplyButton");
	}

	public void clickOnDiscount() {

		if (Discount.isDisplayed()) {
			objiOSGenericMethods.clickOnIOSElement(getDiscount(), "Discount");
		}
	}
	
	
	public void clickOnPrice() {

		if (Price.isDisplayed()) {
			objiOSGenericMethods.clickOnIOSElement(getPrice(), "Price");
		}
	}
	/*
	 * For verifying amount after discount 300021281
	 */

	public void getVerifyAmountAfterDiscount(String ProductCode) {

		System.out.println("Total Items present --> " + allItems.size());
		System.err.println(allItems.get(1).getText());

		for (IOSElement result : allItems) {
			IOSElement link = (IOSElement) result.findElement(By.tagName("a"));
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
	 * Name: Monika
	 * Description: This method will select the first option from the filter list
	 */
	
	public void selectOptionsInsideFilter() {
		List<IOSElement> searchResultList = getOptionsInsideFilter();
		for (IOSElement result : searchResultList) {
			IOSElement div = (IOSElement) result.findElement(By.tagName("div"));
			div.click();
			break;
		}
	}
	
	public void clickOnFirstDiscountOption(){
		objiOSGenericMethods.clickOnIOSElement(getselectFirstDiscountOption(), "selectFirstDiscountOption");
	}
	
	public void VerifyBrandTitle()	{
		objiOSGenericMethods.VerifyStringShouldNotEmpty(getDiscountButtonInFilter(), "Brand Title");
	}
	
	public void VerifyDiscountedPrice()	{
		objiOSGenericMethods.VerifyStringShouldNotEmpty(getDiscountedPrice(), "Brand Title");
	}
	
	/**
	 * Adeed By Subahsis 
	 */
	public void clickOnSecondPrice() {
		
		if(getSecondPrice().isDisplayed()) {
		objiOSGenericMethods.clickOnIOSElement(getSecondPrice(), "Second Price");
		}
	}
	
	public void clickOnGender() {
		
		if(getGender().isDisplayed()) {
		objiOSGenericMethods.clickOnIOSElement(getGender(), "Gender");
		}
	}
	public void clickOnMenGender() {
		if(getMenOption().isDisplayed()) {
			objiOSGenericMethods.clickOnIOSElement(getMenOption(), "Men");
		}
	}
}