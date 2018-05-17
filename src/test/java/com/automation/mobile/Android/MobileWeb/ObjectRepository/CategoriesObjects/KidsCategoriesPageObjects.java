package com.automation.mobile.Android.MobileWeb.ObjectRepository.CategoriesObjects;

import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.automation.core.mobile.Android.AndroidGenericMethods;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class KidsCategoriesPageObjects {
	AndroidGenericMethods objAndroidGenericMethods;
	public KidsCategoriesPageObjects(AndroidDriver<AndroidElement> aDriver) {
		PageFactory.initElements(new AppiumFieldDecorator(aDriver), this);
		objAndroidGenericMethods = new AndroidGenericMethods(aDriver);
	}

	/**
	 * Kids
	 */

	@FindBy(xpath = "(//div[@class='clearfix naviLevel level-1'])[3]")
	public AndroidElement kids;

	@FindBy(xpath = "(//div[@class='clearfix naviLevel level-2'])[1]")
	public AndroidElement boysClothing;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[1]")
	public AndroidElement tShirt;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[2]")
	public AndroidElement shirt;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[3]")
	public AndroidElement jeansTrousers;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[4]")
	public AndroidElement shortsDungarees;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[5]")
	public AndroidElement trackPantsPyjamas;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[6]")
	public AndroidElement clothingSets;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[7]")
	public AndroidElement indianWear;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[8]")
	public AndroidElement sweatersSweatshirtsJackets;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[9]")
	public AndroidElement rompersSleepwear;

	@FindBy(xpath = "(//div[@class='clearfix naviLevel level-2'])[2]")
	public AndroidElement girlsClothings;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[1]")
	public AndroidElement dresses;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[2]")
	public AndroidElement topsTshirts;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[3]")
	public AndroidElement clothingSet;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[4]")
	public AndroidElement indianWears;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[5]")
	public AndroidElement skirtShortsJumpsuit;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[6]")
	public AndroidElement tightsLeggings;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[7]")
	public AndroidElement jeansTrousersCapris;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[8]")
	public AndroidElement trackPants;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[9]")
	public AndroidElement sweateraSweatshirtsJackets;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[10]")
	public AndroidElement rompersAndSleepwear;

	@FindBy(xpath = "(//div[@class='clearfix naviLevel level-2'])[3]")
	public AndroidElement boysFootwear;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[1]")
	public AndroidElement casualShoesboys;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[2]")
	public AndroidElement sportsShoesboys;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[3]")
	public AndroidElement sandalsboys;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[4]")
	public AndroidElement flipFlopsboys;

	@FindBy(xpath = "(//div[@class='clearfix naviLevel level-2'])[4]")
	public AndroidElement girlsFootwear;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[1]")
	public AndroidElement casualShoesgirls;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[2]")
	public AndroidElement flatsgirls;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[3]")
	public AndroidElement heelsgirls;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[4]")
	public AndroidElement sportsShoesgirls;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[5]")
	public AndroidElement flipflopGirls;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[6]")
	public AndroidElement sandalsgirls;

	@FindBy(xpath = "(//div[@class='clearfix naviLevel level-2'])[5]")
	public AndroidElement kidsAccessories;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[1]")
	public AndroidElement bagsbackpackKids;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[2]")
	public AndroidElement watchesKids;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[3]")
	public AndroidElement jewelleryKids;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[4]")
	public AndroidElement hairAccessorieskids;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[5]")
	public AndroidElement sunglassesKids;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[6]")
	public AndroidElement framesKids;

	@FindBy(xpath = "(//div[@class='clearfix naviLevel level-2'])[6]")
	public AndroidElement brands;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[1]")
	public AndroidElement mothercare;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[2]")
	public AndroidElement giniAndJony;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[3]")
	public AndroidElement usPoloAssnKids;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[4]")
	public AndroidElement unitedColorsOfBenetton;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[5]")
	public AndroidElement yk;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[6]")
	public AndroidElement allenSollyJunior;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[7]")
	public AndroidElement mangoKids;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[8]")
	public AndroidElement marksSpencer;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[9]")
	public AndroidElement tommyHilfiger;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[10]")
	public AndroidElement league;

	@FindBy(xpath = "(//a[@class='naviLevel level-2'])[1]")
	public AndroidElement toys;

	public AndroidElement getKids() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(kids, "kids");
		return kids;
	}

	public AndroidElement getBoysClothing() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(boysClothing, "boysClothing");
		return boysClothing;
	}

	public AndroidElement gettShirt() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(tShirt, "tShirt");
		return tShirt;
	}

	public AndroidElement getShirt() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(shirt, "shirt");
		return shirt;
	}

	public AndroidElement getJeansTrousers() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(jeansTrousers, "jeansTrousers");
		return jeansTrousers;
	}

	public AndroidElement getShortsDungarees() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(shortsDungarees, "shortsDungarees");
		return shortsDungarees;
	}

	public AndroidElement getTrackPantsPyjamas() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(trackPantsPyjamas, "trackPantsPyjamas");
		return trackPantsPyjamas;
	}

	public AndroidElement getClothingSets() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(clothingSets, "clothingSets");
		return clothingSets;
	}

	public AndroidElement getIndianWear() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(indianWear, "indianWear");
		return indianWear;
	}

	public AndroidElement getSweatersSweatshirtsJackets() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(sweatersSweatshirtsJackets, "sweatersSweatshirtsJackets");
		return sweatersSweatshirtsJackets;
	}

	public AndroidElement getRompersSleepwear() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(rompersSleepwear, "rompersSleepwear");
		return rompersSleepwear;
	}

	public AndroidElement getGirlsClothings() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(girlsClothings, "girlsClothings");
		return girlsClothings;
	}

	public AndroidElement getDresses() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(dresses, "dresses");
		return dresses;
	}

	public AndroidElement getTopsTshirts() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(topsTshirts, "topsTshirts");
		return topsTshirts;
	}

	public AndroidElement getClothingSet() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(clothingSet, "clothingSet");
		return clothingSet;
	}

	public AndroidElement getIndianWears() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(indianWears, "indianWears");
		return indianWears;
	}

	public AndroidElement getSkirtShortsJumpsuit() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(skirtShortsJumpsuit, "skirtShortsJumpsuit");
		return skirtShortsJumpsuit;
	}

	public AndroidElement getTightsLeggings() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(tightsLeggings, "tightsLeggings");
		return tightsLeggings;
	}

	public AndroidElement getJeansTrousersCapris() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(jeansTrousersCapris, "jeansTrousersCapris");
		return jeansTrousersCapris;
	}

	public AndroidElement getTrackPants() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(trackPants, "trackPants");
		return trackPants;
	}

	public AndroidElement getSweateraSweatshirtsJackets() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(sweateraSweatshirtsJackets, "sweateraSweatshirtsJackets");
		return sweateraSweatshirtsJackets;
	}

	public AndroidElement getRompersAndSleepwear() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(rompersAndSleepwear, "rompersAndSleepwear");
		return rompersAndSleepwear;
	}

	public AndroidElement getBoysFootwear() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(boysFootwear, "boysFootwear");
		return boysFootwear;
	}

	public AndroidElement getCasualShoesboys() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(casualShoesboys, "casualShoesboys");
		return casualShoesboys;
	}

	public AndroidElement getSportsShoesboys() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(sportsShoesboys, "sportsShoesboys");
		return sportsShoesboys;
	}

	public AndroidElement getSandalsboys() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(sandalsboys, "sandalsboys");
		return sandalsboys;
	}

	public AndroidElement getFlipFlopsboys() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(flipFlopsboys, "flipFlopsboys");
		return flipFlopsboys;
	}

	public AndroidElement getGirlsFootwear() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(girlsFootwear, "girlsFootwear");
		return girlsFootwear;
	}

	public AndroidElement getCasualShoesgirls() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(casualShoesgirls, "casualShoesgirls");
		return casualShoesgirls;
	}

	public AndroidElement getFlatsgirls() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(flatsgirls, "flatsgirls");
		return flatsgirls;
	}

	public AndroidElement getHeelsgirls() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(heelsgirls, "heelsgirls");
		return heelsgirls;
	}

	public AndroidElement getSportsShoesgirls() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(sportsShoesgirls, "sportsShoesgirls");
		return sportsShoesgirls;
	}

	public AndroidElement getFlipflopGirls() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(flipflopGirls, "flipflopGirls");
		return flipflopGirls;
	}

	public AndroidElement getSandalsgirls() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(sandalsgirls, "sandalsgirls");
		return sandalsgirls;
	}

	public AndroidElement getKidsAccessories() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(kidsAccessories, "kidsAccessories");
		return kidsAccessories;
	}

	public AndroidElement getBagsbackpackKids() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(bagsbackpackKids, "bagsbackpackKids");
		return bagsbackpackKids;
	}

	public AndroidElement getWatchesKids() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(watchesKids, "watchesKids");
		return watchesKids;
	}

	public AndroidElement getJewelleryKids() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(jewelleryKids, "jewelleryKids");
		return jewelleryKids;
	}

	public AndroidElement getHairAccessorieskids() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(hairAccessorieskids, "hairAccessorieskids");
		return hairAccessorieskids;
	}

	public AndroidElement getSunglassesKids() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(sunglassesKids, "sunglassesKids");
		return sunglassesKids;
	}

	public AndroidElement getFramesKids() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(framesKids, "framesKids");
		return framesKids;
	}

	public AndroidElement getBrands() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(brands, "brands");
		return brands;
	}

	public AndroidElement getMothercare() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(mothercare, "mothercare");
		return mothercare;
	}

	public AndroidElement getGiniAndJony() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(giniAndJony, "giniAndJony");
		return giniAndJony;
	}

	public AndroidElement getUsPoloAssnKids() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(usPoloAssnKids, "usPoloAssnKids");
		return usPoloAssnKids;
	}

	public AndroidElement getUnitedColorsOfBenetton() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(unitedColorsOfBenetton, "unitedColorsOfBenetton");
		return unitedColorsOfBenetton;
	}

	public AndroidElement getYk() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(yk, "yk");
		return yk;
	}

	public AndroidElement getAllenSollyJunior() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(allenSollyJunior, "allenSollyJunior");
		return allenSollyJunior;
	}

	public AndroidElement getMangoKids() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(mangoKids, "mangoKids");
		return mangoKids;
	}

	public AndroidElement getMarksSpencer() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(marksSpencer, "marksSpencer");
		return marksSpencer;
	}

	public AndroidElement getTommyHilfiger() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(tommyHilfiger, "tommyHilfiger");
		return tommyHilfiger;
	}

	public AndroidElement getLeague() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(league, "league");
		return league;
	}

	public AndroidElement getToys() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(toys, "toys");
		return toys;
	}
}
