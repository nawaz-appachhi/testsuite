package com.automation.mobile.iOS.MobileWeb.ObjectRepository.CategoriesObjects;

import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.automation.core.mobile.iOS.iOSGenericMethods;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class KidsCategoriesPageObjects {
	public AppiumDriver<MobileElement>  iDriver;
	iOSGenericMethods objiOSGenericMethods;

	public KidsCategoriesPageObjects(AppiumDriver<MobileElement>  iDriver) {
		PageFactory.initElements(new AppiumFieldDecorator(iDriver), this);
		objiOSGenericMethods = new iOSGenericMethods(iDriver);

	}

	/**
	 * Kids
	 */

	@FindBy(xpath = "(//div[@class='clearfix naviLevel level-1'])[3]")
	public IOSElement kids;

	@FindBy(xpath = "(//div[@class='clearfix naviLevel level-2'])[1]")
	public IOSElement boysClothing;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[1]")
	public IOSElement tShirt;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[2]")
	public IOSElement shirt;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[3]")
	public IOSElement jeansTrousers;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[4]")
	public IOSElement shortsDungarees;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[5]")
	public IOSElement trackPantsPyjamas;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[6]")
	public IOSElement clothingSets;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[7]")
	public IOSElement indianWear;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[8]")
	public IOSElement sweatersSweatshirtsJackets;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[9]")
	public IOSElement rompersSleepwear;

	@FindBy(xpath = "(//div[@class='clearfix naviLevel level-2'])[2]")
	public IOSElement girlsClothings;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[1]")
	public IOSElement dresses;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[2]")
	public IOSElement topsTshirts;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[3]")
	public IOSElement clothingSet;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[4]")
	public IOSElement indianWears;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[5]")
	public IOSElement skirtShortsJumpsuit;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[6]")
	public IOSElement tightsLeggings;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[7]")
	public IOSElement jeansTrousersCapris;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[8]")
	public IOSElement trackPants;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[9]")
	public IOSElement sweateraSweatshirtsJackets;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[10]")
	public IOSElement rompersAndSleepwear;

	@FindBy(xpath = "(//div[@class='clearfix naviLevel level-2'])[3]")
	public IOSElement boysFootwear;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[1]")
	public IOSElement casualShoesboys;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[2]")
	public IOSElement sportsShoesboys;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[3]")
	public IOSElement sandalsboys;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[4]")
	public IOSElement flipFlopsboys;

	@FindBy(xpath = "(//div[@class='clearfix naviLevel level-2'])[4]")
	public IOSElement girlsFootwear;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[1]")
	public IOSElement casualShoesgirls;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[2]")
	public IOSElement flatsgirls;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[3]")
	public IOSElement heelsgirls;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[4]")
	public IOSElement sportsShoesgirls;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[5]")
	public IOSElement flipflopGirls;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[6]")
	public IOSElement sandalsgirls;

	@FindBy(xpath = "(//div[@class='clearfix naviLevel level-2'])[5]")
	public IOSElement kidsAccessories;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[1]")
	public IOSElement bagsbackpackKids;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[2]")
	public IOSElement watchesKids;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[3]")
	public IOSElement jewelleryKids;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[4]")
	public IOSElement hairAccessorieskids;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[5]")
	public IOSElement sunglassesKids;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[6]")
	public IOSElement framesKids;

	@FindBy(xpath = "(//div[@class='clearfix naviLevel level-2'])[6]")
	public IOSElement brands;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[1]")
	public IOSElement mothercare;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[2]")
	public IOSElement giniAndJony;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[3]")
	public IOSElement usPoloAssnKids;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[4]")
	public IOSElement unitedColorsOfBenetton;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[5]")
	public IOSElement yk;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[6]")
	public IOSElement allenSollyJunior;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[7]")
	public IOSElement mangoKids;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[8]")
	public IOSElement marksSpencer;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[9]")
	public IOSElement tommyHilfiger;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[10]")
	public IOSElement league;

	@FindBy(xpath = "(//a[@class='naviLevel level-2'])[1]")
	public IOSElement toys;

	public IOSElement getKids() {
		objiOSGenericMethods.CheckIOSElementFound(kids, "kids");
		return kids;
	}

	public IOSElement getBoysClothing() {
		objiOSGenericMethods.CheckIOSElementFound(boysClothing, "boysClothing");
		return boysClothing;
	}

	public IOSElement gettShirt() {
		objiOSGenericMethods.CheckIOSElementFound(tShirt, "tShirt");
		return tShirt;
	}

	public IOSElement getShirt() {
		objiOSGenericMethods.CheckIOSElementFound(shirt, "shirt");
		return shirt;
	}

	public IOSElement getJeansTrousers() {
		objiOSGenericMethods.CheckIOSElementFound(jeansTrousers, "jeansTrousers");
		return jeansTrousers;
	}

	public IOSElement getShortsDungarees() {
		objiOSGenericMethods.CheckIOSElementFound(shortsDungarees, "shortsDungarees");
		return shortsDungarees;
	}

	public IOSElement getTrackPantsPyjamas() {
		objiOSGenericMethods.CheckIOSElementFound(trackPantsPyjamas, "trackPantsPyjamas");
		return trackPantsPyjamas;
	}

	public IOSElement getClothingSets() {
		objiOSGenericMethods.CheckIOSElementFound(clothingSets, "clothingSets");
		return clothingSets;
	}

	public IOSElement getIndianWear() {
		objiOSGenericMethods.CheckIOSElementFound(indianWear, "indianWear");
		return indianWear;
	}

	public IOSElement getSweatersSweatshirtsJackets() {
		objiOSGenericMethods.CheckIOSElementFound(sweatersSweatshirtsJackets, "sweatersSweatshirtsJackets");
		return sweatersSweatshirtsJackets;
	}

	public IOSElement getRompersSleepwear() {
		objiOSGenericMethods.CheckIOSElementFound(rompersSleepwear, "rompersSleepwear");
		return rompersSleepwear;
	}

	public IOSElement getGirlsClothings() {
		objiOSGenericMethods.CheckIOSElementFound(girlsClothings, "girlsClothings");
		return girlsClothings;
	}

	public IOSElement getDresses() {
		objiOSGenericMethods.CheckIOSElementFound(dresses, "dresses");
		return dresses;
	}

	public IOSElement getTopsTshirts() {
		objiOSGenericMethods.CheckIOSElementFound(topsTshirts, "topsTshirts");
		return topsTshirts;
	}

	public IOSElement getClothingSet() {
		objiOSGenericMethods.CheckIOSElementFound(clothingSet, "clothingSet");
		return clothingSet;
	}

	public IOSElement getIndianWears() {
		objiOSGenericMethods.CheckIOSElementFound(indianWears, "indianWears");
		return indianWears;
	}

	public IOSElement getSkirtShortsJumpsuit() {
		objiOSGenericMethods.CheckIOSElementFound(skirtShortsJumpsuit, "skirtShortsJumpsuit");
		return skirtShortsJumpsuit;
	}

	public IOSElement getTightsLeggings() {
		objiOSGenericMethods.CheckIOSElementFound(tightsLeggings, "tightsLeggings");
		return tightsLeggings;
	}

	public IOSElement getJeansTrousersCapris() {
		objiOSGenericMethods.CheckIOSElementFound(jeansTrousersCapris, "jeansTrousersCapris");
		return jeansTrousersCapris;
	}

	public IOSElement getTrackPants() {
		objiOSGenericMethods.CheckIOSElementFound(trackPants, "trackPants");
		return trackPants;
	}

	public IOSElement getSweateraSweatshirtsJackets() {
		objiOSGenericMethods.CheckIOSElementFound(sweateraSweatshirtsJackets, "sweateraSweatshirtsJackets");
		return sweateraSweatshirtsJackets;
	}

	public IOSElement getRompersAndSleepwear() {
		objiOSGenericMethods.CheckIOSElementFound(rompersAndSleepwear, "rompersAndSleepwear");
		return rompersAndSleepwear;
	}

	public IOSElement getBoysFootwear() {
		objiOSGenericMethods.CheckIOSElementFound(boysFootwear, "boysFootwear");
		return boysFootwear;
	}

	public IOSElement getCasualShoesboys() {
		objiOSGenericMethods.CheckIOSElementFound(casualShoesboys, "casualShoesboys");
		return casualShoesboys;
	}

	public IOSElement getSportsShoesboys() {
		objiOSGenericMethods.CheckIOSElementFound(sportsShoesboys, "sportsShoesboys");
		return sportsShoesboys;
	}

	public IOSElement getSandalsboys() {
		objiOSGenericMethods.CheckIOSElementFound(sandalsboys, "sandalsboys");
		return sandalsboys;
	}

	public IOSElement getFlipFlopsboys() {
		objiOSGenericMethods.CheckIOSElementFound(flipFlopsboys, "flipFlopsboys");
		return flipFlopsboys;
	}

	public IOSElement getGirlsFootwear() {
		objiOSGenericMethods.CheckIOSElementFound(girlsFootwear, "girlsFootwear");
		return girlsFootwear;
	}

	public IOSElement getCasualShoesgirls() {
		objiOSGenericMethods.CheckIOSElementFound(casualShoesgirls, "casualShoesgirls");
		return casualShoesgirls;
	}

	public IOSElement getFlatsgirls() {
		objiOSGenericMethods.CheckIOSElementFound(flatsgirls, "flatsgirls");
		return flatsgirls;
	}

	public IOSElement getHeelsgirls() {
		objiOSGenericMethods.CheckIOSElementFound(heelsgirls, "heelsgirls");
		return heelsgirls;
	}

	public IOSElement getSportsShoesgirls() {
		objiOSGenericMethods.CheckIOSElementFound(sportsShoesgirls, "sportsShoesgirls");
		return sportsShoesgirls;
	}

	public IOSElement getFlipflopGirls() {
		objiOSGenericMethods.CheckIOSElementFound(flipflopGirls, "flipflopGirls");
		return flipflopGirls;
	}

	public IOSElement getSandalsgirls() {
		objiOSGenericMethods.CheckIOSElementFound(sandalsgirls, "sandalsgirls");
		return sandalsgirls;
	}

	public IOSElement getKidsAccessories() {
		objiOSGenericMethods.CheckIOSElementFound(kidsAccessories, "kidsAccessories");
		return kidsAccessories;
	}

	public IOSElement getBagsbackpackKids() {
		objiOSGenericMethods.CheckIOSElementFound(bagsbackpackKids, "bagsbackpackKids");
		return bagsbackpackKids;
	}

	public IOSElement getWatchesKids() {
		objiOSGenericMethods.CheckIOSElementFound(watchesKids, "watchesKids");
		return watchesKids;
	}

	public IOSElement getJewelleryKids() {
		objiOSGenericMethods.CheckIOSElementFound(jewelleryKids, "jewelleryKids");
		return jewelleryKids;
	}

	public IOSElement getHairAccessorieskids() {
		objiOSGenericMethods.CheckIOSElementFound(hairAccessorieskids, "hairAccessorieskids");
		return hairAccessorieskids;
	}

	public IOSElement getSunglassesKids() {
		objiOSGenericMethods.CheckIOSElementFound(sunglassesKids, "sunglassesKids");
		return sunglassesKids;
	}

	public IOSElement getFramesKids() {
		objiOSGenericMethods.CheckIOSElementFound(framesKids, "framesKids");
		return framesKids;
	}

	public IOSElement getBrands() {
		objiOSGenericMethods.CheckIOSElementFound(brands, "brands");
		return brands;
	}

	public IOSElement getMothercare() {
		objiOSGenericMethods.CheckIOSElementFound(mothercare, "mothercare");
		return mothercare;
	}

	public IOSElement getGiniAndJony() {
		objiOSGenericMethods.CheckIOSElementFound(giniAndJony, "giniAndJony");
		return giniAndJony;
	}

	public IOSElement getUsPoloAssnKids() {
		objiOSGenericMethods.CheckIOSElementFound(usPoloAssnKids, "usPoloAssnKids");
		return usPoloAssnKids;
	}

	public IOSElement getUnitedColorsOfBenetton() {
		objiOSGenericMethods.CheckIOSElementFound(unitedColorsOfBenetton, "unitedColorsOfBenetton");
		return unitedColorsOfBenetton;
	}

	public IOSElement getYk() {
		objiOSGenericMethods.CheckIOSElementFound(yk, "yk");
		return yk;
	}

	public IOSElement getAllenSollyJunior() {
		objiOSGenericMethods.CheckIOSElementFound(allenSollyJunior, "allenSollyJunior");
		return allenSollyJunior;
	}

	public IOSElement getMangoKids() {
		objiOSGenericMethods.CheckIOSElementFound(mangoKids, "mangoKids");
		return mangoKids;
	}

	public IOSElement getMarksSpencer() {
		objiOSGenericMethods.CheckIOSElementFound(marksSpencer, "marksSpencer");
		return marksSpencer;
	}

	public IOSElement getTommyHilfiger() {
		objiOSGenericMethods.CheckIOSElementFound(tommyHilfiger, "tommyHilfiger");
		return tommyHilfiger;
	}

	public IOSElement getLeague() {
		objiOSGenericMethods.CheckIOSElementFound(league, "league");
		return league;
	}

	public IOSElement getToys() {
		objiOSGenericMethods.CheckIOSElementFound(toys, "toys");
		return toys;
	}
}
