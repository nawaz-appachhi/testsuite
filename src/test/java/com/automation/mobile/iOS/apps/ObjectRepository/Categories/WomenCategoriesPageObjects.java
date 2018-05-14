package com.automation.mobile.iOS.apps.ObjectRepository.Categories;


import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;

public class WomenCategoriesPageObjects {
	public IOSDriver<IOSElement> iDriver;
	public WomenCategoriesPageObjects(IOSDriver<IOSElement> iDriver) {
		PageFactory.initElements(new AppiumFieldDecorator(iDriver), this);
	}

	
	@iOSFindBy(xpath = "//XCUIElementTypeOther[@name=\"Women Kurtas & Suits, Top & Tees, Dresses, Footwear \"]")
	public IOSElement women;

	/**
	 * Women Categories
	 */
	
	@iOSFindBy(xpath = "//XCUIElementTypeOther[@name=\"Brands\"]")
	public IOSElement womenBrands;
	
	@iOSFindBy(accessibility = "Indian & Fusion Wear ï�¸")
	public IOSElement indianAndFusionWear;
	
	@iOSFindBy(accessibility = "Western Wear ï�¸")
	public IOSElement westernWear;
	
	@iOSFindBy(accessibility = "Lingerie & Sleepwear ï�¸")
	public IOSElement lingerieAndSleepwear;
	
	@iOSFindBy(accessibility = "	Footwear ï�¸")
	public IOSElement footWear;
	
	@iOSFindBy(accessibility = "Sports & Active Wear ï�¸")
	public IOSElement sportsAndActiveWear;
	
	@iOSFindBy(accessibility = "Handbags, Bags & Wallets")
	public IOSElement handBagAndWallets;
	
	@iOSFindBy(accessibility = "Watches & Wearables")
	public IOSElement watchesAndWearables;
	
	@iOSFindBy(accessibility = "Sunglasses & Frames")
	public IOSElement sunglassessAndFrames;
	
	@iOSFindBy(accessibility = "Jewellery ï�¸")
	public IOSElement jewellery;
	
	@iOSFindBy(accessibility = "Beauty & Personal care ï�¸")
	public IOSElement beautyAndPersonal;
	
	@iOSFindBy(xpath = "//XCUIElementTypeOther[@name=\"Accessories ï�¸\"])[1]")
	public IOSElement accessories;
	
	@iOSFindBy(accessibility = "//XCUIElementTypeOther[@name=\"Luggage & Trolleys\"])[1]")
	public IOSElement luggageAndTrolleys;

	public void clickOnWomen() {
		women.click();
		System.out.println("Succesfully click on women");
	}
	public void clickOnWomenBrands() {
		womenBrands.click();
		System.out.println("Succesfully click on women Brands");
	}
	public void clickOnIndianAndFusionWear() {
		indianAndFusionWear.click();
		System.out.println("Succesfully click on indian And Fusion Wear");
	}
	public void clickOnWesternWear() {
		westernWear.click();
		System.out.println("Succesfully click on western Wear");
	}
	public void clickOnLingerieAndSleepwear() {
		lingerieAndSleepwear.click();
		System.out.println("Succesfully click on lingerie And Sleepwear");
	}
	public void clickOnFootWear() {
		footWear.click();
		System.out.println("Succesfully click on foot Wear");
	}
	public void clickOnSportsAndActiveWear() {
		sportsAndActiveWear.click();
		System.out.println("Succesfully click on sports And Active Wear");
	}
	public void clickOnHandBagAndWallets() {
		handBagAndWallets.click();
		System.out.println("Succesfully click on hand Bag And Wallets");
	}
	public void clickOnWatchesAndWearables() {
		watchesAndWearables.click();
		System.out.println("Succesfully click on watches And Wearables");
	}
	public void clickOnSunglassessAndFrames() {
		sunglassessAndFrames.click();
		System.out.println("Succesfully click on sun glassess AndF rames");
	}
	public void clickOnJewellery() {
		jewellery.click();
		System.out.println("Succesfully click on jewellery");
	}
	public void clickOnBeautyAndPersonal() {
		beautyAndPersonal.click();
		System.out.println("Succesfully click on beauty And Personal");
	}
	public void clickOnAccessories() {
		accessories.click();
		System.out.println("Succesfully click on accessories");
	}
	public void clickOnLuggageAndTrolleys() {
		luggageAndTrolleys.click();
		System.out.println("Succesfully click on luggage And Trolleys");
	}
	public IOSElement getWomen() {
		return women;
	}

	public IOSElement getWomenBrands() {
		return womenBrands;
	}

	public IOSElement getIndianAndFusionWear() {
		return indianAndFusionWear;
	}

	public IOSElement getWesternWear() {
		return westernWear;
	}

	public IOSElement getLingerieAndSleepwear() {
		return lingerieAndSleepwear;
	}

	public IOSElement getFootWear() {
		return footWear;
	}

	public IOSElement getSportsAndActiveWear() {
		return sportsAndActiveWear;
	}

	public IOSElement getHandBagAndWallets() {
		return handBagAndWallets;
	}

	public IOSElement getWatchesAndWearables() {
		return watchesAndWearables;
	}

	public IOSElement getSunglassessAndFrames() {
		return sunglassessAndFrames;
	}

	public IOSElement getJewellery() {
		return jewellery;
	}

	public IOSElement getBeautyAndPersonal() {
		return beautyAndPersonal;
	}

	public IOSElement getAccessories() {
		return accessories;
	}

	public IOSElement getLuggageAndTrolleys() {
		return luggageAndTrolleys;
	}	
}
