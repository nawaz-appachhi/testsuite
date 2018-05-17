package com.automation.mobile.Android.MobileWeb.ObjectRepository.CategoriesObjects;

import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.automation.core.mobile.Android.AndroidGenericMethods;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class HomeAndLivingCategoriesPageObjects {
	
	AndroidGenericMethods objAndroidGenericMethods;
	public HomeAndLivingCategoriesPageObjects(AppiumDriver<MobileElement> aDriver) {
		PageFactory.initElements(new AppiumFieldDecorator(aDriver), this);
		objAndroidGenericMethods = new AndroidGenericMethods(aDriver);
	}

	/**
	 * Home & Living
	 */

	@FindBy(xpath = "(//div[@class='clearfix naviLevel level-1'])[4]")
	public AndroidElement HomeLiving;

	@FindBy(xpath = "(//div[@class='clearfix naviLevel level-2'])[1]")
	public AndroidElement bedLinenFurnishing;

	@FindBy(xpath = "(//div[@class='clearfix naviLevel level-2'])[2]")
	public AndroidElement bath;

	@FindBy(xpath = "(//div[@class='clearfix naviLevel level-2'])[3]")
	public AndroidElement floorMatsDhurries;

	@FindBy(xpath = "(//div[@class='clearfix naviLevel level-2'])[4]")
	public AndroidElement kitchenTable;

	@FindBy(xpath = "(//div[@class='clearfix naviLevel level-2'])[5]")
	public AndroidElement homeDecor;

	@FindBy(xpath = "(//div[@class='clearfix naviLevel level-2'])[6]")
	public AndroidElement lampsAndLighting;

	@FindBy(xpath = "(//div[@class='clearfix naviLevel level-2'])[7]")
	public AndroidElement storage;

	@FindBy(xpath = "(//div[@class='clearfix naviLevel level-2'])[8]")
	public AndroidElement brand;

	@FindBy(xpath = "(//a[@class='naviLevel level-2'])[1]")
	public AndroidElement curtains;

	@FindBy(xpath = "(//a[@class='naviLevel level-2'])[2]")
	public AndroidElement cushionsareCushionCover;

	/**
	 * Modified By:Aishurya:Changed string parameter,which is being used in reporing
	 */
	public void clickOnHomeLiving() {
		objAndroidGenericMethods.clickOnAndroidElement(getHomeLiving(), "HomeLiving");
		System.out.println("Succesfully click on HomeLiving");
	}

	/**
	 * Modified By:Aishurya:Changed string parameter,which is being used in reporing
	 */
	public void clickOnbedLinenFurnishing() {
		objAndroidGenericMethods.clickOnAndroidElement(getBedLinenFurnishing(), "BedLinenFurnishing");
		System.out.println("Succesfully click on bedLinenFurnishing");
	}

	/**
	 * Modified By:Aishurya:Changed string parameter,which is being used in reporing
	 */
	public void clickOnbath() {
		objAndroidGenericMethods.clickOnAndroidElement(getBath(), "Bath");
		System.out.println("Succesfully click on bath");
	}

	/**
	 * Modified By:Aishurya:Changed string parameter,which is being used in reporing
	 */
	public void clickOnfloorMatsDhurries() {
		objAndroidGenericMethods.clickOnAndroidElement(getFloorMatsDhurries(), "FloorMatsDhurries");
		System.out.println("Succesfully click on floorMatsDhurries");
	}

	/**
	 * Modified By:Aishurya:Changed string parameter,which is being used in reporing
	 */
	public void clickOnkitchenTable() {
		objAndroidGenericMethods.clickOnAndroidElement(getKitchenTable(), "KitchenTable");
		System.out.println("Succesfully click on kitchenTable");
	}

	/**
	 * Modified By:Aishurya:Changed string parameter,which is being used in reporing
	 */
	public void clickOnhomeDecor() {
		objAndroidGenericMethods.clickOnAndroidElement(getHomeDecor(), "HomeDecor");
		System.out.println("Succesfully click on homeDecor");
	}

	/**
	 * Modified By:Aishurya:Changed string parameter,which is being used in reporting
	 */
	public void clickOnlampsAndLighting() {
		objAndroidGenericMethods.clickOnAndroidElement(getLampsAndLighting(), "LampsAndLighting");
		System.out.println("Succesfully click on lampsAndLighting");
	}

	/**
	 * Modified By:Aishurya:Changed string parameter,which is being used in reporting
	 */
	public void clickOnstorage() {
		objAndroidGenericMethods.clickOnAndroidElement(getStorage(), "Storage");
		System.out.println("Succesfully click on storage");
	}

	/**
	 * Modified By:Aishurya:Changed string parameter,which is being used in reporting
	 */
	public void clickOnbrand() {
		objAndroidGenericMethods.clickOnAndroidElement(getBrand(), "Brand");
		System.out.println("Succesfully click on brand");
	}

	/**
	 * Modified By:Aishurya:Changed string parameter,which is being used in reporting
	 */
	public void clickOncurtains() {
		objAndroidGenericMethods.clickOnAndroidElement(getCurtains(), "Curtains");
		System.out.println("Succesfully click on curtains");
	}

	/**
	 * Modified By:Aishurya:Changed string parameter,which is being used in reporting
	 */
	public void clickOncushionsareCushionCover() {
		objAndroidGenericMethods.clickOnAndroidElement(getCushionsareCushionCover(), "CushionsareCushionCover");
		System.out.println("Succesfully click on cushionsareCushionCover");
	}

	public AndroidElement getHomeLiving() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(HomeLiving, "HomeLiving");
		return HomeLiving;
	}

	public AndroidElement getBedLinenFurnishing() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(bedLinenFurnishing, "bedLinenFurnishing");
		return bedLinenFurnishing;
	}

	public AndroidElement getBath() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(bath, "bath");
		return bath;
	}

	public AndroidElement getFloorMatsDhurries() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(floorMatsDhurries, "floorMatsDhurries");
		return floorMatsDhurries;
	}

	public AndroidElement getKitchenTable() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(kitchenTable, "kitchenTable");
		return kitchenTable;
	}

	public AndroidElement getHomeDecor() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(homeDecor, "homeDecor");
		return homeDecor;
	}

	public AndroidElement getLampsAndLighting() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(lampsAndLighting, "lampsAndLighting");
		return lampsAndLighting;
	}

	public AndroidElement getStorage() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(storage, "storage");
		return storage;
	}

	public AndroidElement getBrand() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(brand, "brand");
		return brand;
	}

	public AndroidElement getCurtains() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(curtains, "curtains");
		return curtains;
	}

	public AndroidElement getCushionsareCushionCover() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(cushionsareCushionCover, "cushionsareCushionCover");
		return cushionsareCushionCover;
	}
}
