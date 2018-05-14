package com.automation.mobile.iOS.MobileWeb.ObjectRepository.CategoriesObjects;

import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.automation.core.mobile.iOS.iOSGenericMethods;

import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class HomeAndLivingCategoriesPageObjects {
	public IOSDriver<IOSElement> iDriver;
	iOSGenericMethods objiOSGenericMethods;

	public HomeAndLivingCategoriesPageObjects(IOSDriver<IOSElement> iDriver) {
		PageFactory.initElements(new AppiumFieldDecorator(iDriver), this);
		objiOSGenericMethods = new iOSGenericMethods(iDriver);

	}

	/**
	 * Home & Living
	 */

	@FindBy(xpath = "(//div[@class='clearfix naviLevel level-1'])[4]")
	public IOSElement HomeLiving;

	@FindBy(xpath = "(//div[@class='clearfix naviLevel level-2'])[1]")
	public IOSElement bedLinenFurnishing;

	@FindBy(xpath = "(//div[@class='clearfix naviLevel level-2'])[2]")
	public IOSElement bath;

	@FindBy(xpath = "(//div[@class='clearfix naviLevel level-2'])[3]")
	public IOSElement floorMatsDhurries;

	@FindBy(xpath = "(//div[@class='clearfix naviLevel level-2'])[4]")
	public IOSElement kitchenTable;

	@FindBy(xpath = "(//div[@class='clearfix naviLevel level-2'])[5]")
	public IOSElement homeDecor;

	@FindBy(xpath = "(//div[@class='clearfix naviLevel level-2'])[6]")
	public IOSElement lampsAndLighting;

	@FindBy(xpath = "(//div[@class='clearfix naviLevel level-2'])[7]")
	public IOSElement storage;

	@FindBy(xpath = "(//div[@class='clearfix naviLevel level-2'])[8]")
	public IOSElement brand;

	@FindBy(xpath = "(//a[@class='naviLevel level-2'])[1]")
	public IOSElement curtains;

	@FindBy(xpath = "(//a[@class='naviLevel level-2'])[2]")
	public IOSElement cushionsareCushionCover;

	public void clickOnHomeLiving() {
		objiOSGenericMethods.clickOnIOSElement(getHomeLiving(), "clicked on HomeLiving");
		System.out.println("Succesfully click on HomeLiving");
	}

	public void clickOnbedLinenFurnishing() {
		objiOSGenericMethods.clickOnIOSElement(getBedLinenFurnishing(), "clicked on bedLinenFurnishing");
		System.out.println("Succesfully click on bedLinenFurnishing");
	}

	public void clickOnbath() {
		objiOSGenericMethods.clickOnIOSElement(getBath(), "clicked on bath");
		System.out.println("Succesfully click on bath");
	}

	public void clickOnfloorMatsDhurries() {
		objiOSGenericMethods.clickOnIOSElement(getFloorMatsDhurries(), "clicked on floorMatsDhurries");
		System.out.println("Succesfully click on floorMatsDhurries");
	}

	public void clickOnkitchenTable() {
		objiOSGenericMethods.clickOnIOSElement(getKitchenTable(), "clicked on kitchenTable");
		System.out.println("Succesfully click on kitchenTable");
	}

	public void clickOnhomeDecor() {
		objiOSGenericMethods.clickOnIOSElement(getHomeDecor(), "clicked on homeDecor");
		System.out.println("Succesfully click on homeDecor");
	}

	public void clickOnlampsAndLighting() {
		objiOSGenericMethods.clickOnIOSElement(getLampsAndLighting(), "clicked on lampsAndLighting");
		System.out.println("Succesfully click on lampsAndLighting");
	}

	public void clickOnstorage() {
		objiOSGenericMethods.clickOnIOSElement(getStorage(), "clicked on storage");
		System.out.println("Succesfully click on storage");
	}

	public void clickOnbrand() {
		objiOSGenericMethods.clickOnIOSElement(getBrand(), "clicked on brand");
		System.out.println("Succesfully click on brand");
	}

	public void clickOncurtains() {
		objiOSGenericMethods.clickOnIOSElement(getCurtains(), "clicked on curtains");
		System.out.println("Succesfully click on curtains");
	}

	public void clickOncushionsareCushionCover() {
		objiOSGenericMethods.clickOnIOSElement(getCushionsareCushionCover(), "clicked on cushionsareCushionCover");
		System.out.println("Succesfully click on cushionsareCushionCover");
	}

	public IOSElement getHomeLiving() {
		objiOSGenericMethods.CheckIOSElementFound(HomeLiving, "HomeLiving");
		return HomeLiving;
	}

	public IOSElement getBedLinenFurnishing() {
		objiOSGenericMethods.CheckIOSElementFound(bedLinenFurnishing, "bedLinenFurnishing");
		return bedLinenFurnishing;
	}

	public IOSElement getBath() {
		objiOSGenericMethods.CheckIOSElementFound(bath, "bath");
		return bath;
	}

	public IOSElement getFloorMatsDhurries() {
		objiOSGenericMethods.CheckIOSElementFound(floorMatsDhurries, "floorMatsDhurries");
		return floorMatsDhurries;
	}

	public IOSElement getKitchenTable() {
		objiOSGenericMethods.CheckIOSElementFound(kitchenTable, "kitchenTable");
		return kitchenTable;
	}

	public IOSElement getHomeDecor() {
		objiOSGenericMethods.CheckIOSElementFound(homeDecor, "homeDecor");
		return homeDecor;
	}

	public IOSElement getLampsAndLighting() {
		objiOSGenericMethods.CheckIOSElementFound(lampsAndLighting, "lampsAndLighting");
		return lampsAndLighting;
	}

	public IOSElement getStorage() {
		objiOSGenericMethods.CheckIOSElementFound(storage, "storage");
		return storage;
	}

	public IOSElement getBrand() {
		objiOSGenericMethods.CheckIOSElementFound(brand, "brand");
		return brand;
	}

	public IOSElement getCurtains() {
		objiOSGenericMethods.CheckIOSElementFound(curtains, "curtains");
		return curtains;
	}

	public IOSElement getCushionsareCushionCover() {
		objiOSGenericMethods.CheckIOSElementFound(cushionsareCushionCover, "cushionsareCushionCover");
		return cushionsareCushionCover;
	}
}
