package com.automation.web.ObjectRepository.Pages.HomePage;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.automation.core.web.GenericMethods;

public class HomePageObject {

	public WebElement element = null;
	public List<WebElement> elements;
	GenericMethods objGenericMethods;

	public HomePageObject(WebDriver driver) {
		super();
		PageFactory.initElements(driver, this);
		objGenericMethods = new GenericMethods(driver);
	}

	@FindBy(xpath = ".//*[@href='/welcome-to-myntra']")
	public WebElement ExclusiveOfferImage4th;

	/**
	 * Modified by Neeraj
	 * Old Locator xpath="img[@class='image-image undefined image-hand'])[1]"
	 * new Locator :xpath=".//a[@href='/nike?f=discount%3A30.0']" 
	 */
	@FindBy(xpath=".//a[@href='/nike?f=discount%3A30.0']")
	public WebElement BannerOnHomePage;
	

	public WebElement getBannerOnHomePage() {
		objGenericMethods.CheckWebElementFound(BannerOnHomePage, "BannerOnHomePage");
		return BannerOnHomePage;
	}
	
	public void clickOnBannerOnHomePage() {
		objGenericMethods.takeSnapShot();
		getBannerOnHomePage().click();
		objGenericMethods.ReportClickEvent("BannerOnHomePage");

	}

	public WebElement getExclusiveOfferImage4th() {
		objGenericMethods.CheckWebElementFound(ExclusiveOfferImage4th, "ExclusiveOfferImage4th");
		return ExclusiveOfferImage4th;
	}

	public void clickOnExclusiveOfferImage4th() {
		objGenericMethods.takeSnapShot();
		getExclusiveOfferImage4th().click();
		objGenericMethods.ReportClickEvent("ExclusiveOfferImage4th");

	}

}
