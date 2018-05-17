package com.automation.mobile.Android.MobileWeb.ObjectRepository.CategoriesObjects;

import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.automation.core.mobile.Android.AndroidGenericMethods;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

/**
 * Modified By:Aishurya:Changed string parameter,which is being used in reporting
 */
public class WomenCategoriesPageObjects {

	AndroidGenericMethods objAndroidGenericMethods;
	public WomenCategoriesPageObjects(AppiumDriver<MobileElement> aDriver) {
		PageFactory.initElements(new AppiumFieldDecorator(aDriver), this);
		objAndroidGenericMethods = new AndroidGenericMethods(aDriver);
	}

	@FindBy(xpath = "(//div[@class='clearfix naviLevel level-1'])[2]")
	public AndroidElement Women;

	@FindBy(xpath = "(//div[@class='clearfix naviLevel level-2'])[1]")
	public AndroidElement indianFusionWear;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[1]")
	public AndroidElement kurtasSuits;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[2]")
	public AndroidElement kurtisTunicTops;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[3]")
	public AndroidElement leggingSalwarsChudidars;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[4]")
	public AndroidElement skirtsPalazzos;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[5]")
	public AndroidElement sareesBlousses;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[6]")
	public AndroidElement dressMaterial;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[7]")
	public AndroidElement lehnegaCholi;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[8]")
	public AndroidElement dupattasShawls;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[9]")
	public AndroidElement jacketsWaistcoats;

	@FindBy(xpath = "(//div[@class='clearfix naviLevel level-2'])[2]")
	public AndroidElement westernWear;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[1]")
	public AndroidElement dressesJumpsuits;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[2]")
	public AndroidElement topsTShirtsShirts;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[3]")
	public AndroidElement jeansJeggings;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[4]")
	public AndroidElement trousersAndCapris;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[5]")
	public AndroidElement shortsSkirts;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[6]")
	public AndroidElement shrugs;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[7]")
	public AndroidElement sweatersSweatshirts;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[8]")
	public AndroidElement jacketsAndWaistcoats;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[9]")
	public AndroidElement coatsAndBlazers;

	@FindBy(xpath = "(//div[@class='clearfix naviLevel level-2'])[3]")
	public AndroidElement lingerieAndSleepwear;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[1]")
	public AndroidElement brasLingerieSets;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[2]")
	public AndroidElement briefs;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[3]")
	public AndroidElement shapewear;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[4]")
	public AndroidElement sleepwearAndLoungewear;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[5]")
	public AndroidElement swimwears;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[6]")
	public AndroidElement camisolesThermals;

	@FindBy(xpath = "(//div[@class='clearfix naviLevel level-2'])[4]")
	public AndroidElement footWare;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[1]")
	public AndroidElement FlatsAndCasualShoes;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[2]")
	public AndroidElement heels;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[3]")
	public AndroidElement sportsShoesFloaters;

	@FindBy(xpath = "(//div[@class='clearfix naviLevel level-2'])[5]")
	public AndroidElement sportsActiveWear;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[1]")
	public AndroidElement clothing;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[2]")
	public AndroidElement footwear;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[3]")
	public AndroidElement accessories;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[4]")
	public AndroidElement sportsEquipments;

	@FindBy(xpath = "(//div[@class='clearfix naviLevel level-2'])[6]")
	public AndroidElement fashionAccessories;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[1]")
	public AndroidElement belt;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[2]")
	public AndroidElement scarvesStolesGloves;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[3]")
	public AndroidElement capsHats;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[4]")
	public AndroidElement hairAccessories;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[5]")
	public AndroidElement sock;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[6]")
	public AndroidElement helmet;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[7]")
	public AndroidElement travelAccessorie;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[8]")
	public AndroidElement phonecase;

	@FindBy(xpath = "(//a[@class='naviLevel level-2'])[1]")
	public AndroidElement handbagsBagsWallets;

	@FindBy(xpath = "(//a[@class='naviLevel level-2'])[2]")
	public AndroidElement WatchesAndWearables;

	@FindBy(xpath = "(//a[@class='naviLevel level-2'])[3]")
	public AndroidElement SunglassesFrames;

	@FindBy(xpath = "(//a[@class='naviLevel level-2'])[4]")
	public AndroidElement HeadphonesSpeakers;

	@FindBy(xpath = "(//a[@class='naviLevel level-2'])[5]")
	public AndroidElement LuggageTrolleys;

	@FindBy(xpath = "(//a[@class='naviLevel level-2'])[6]")
	public AndroidElement beautyPersonalCare;

	@FindBy(xpath = "(//a[@class='naviLevel level-2'])[7]")
	public AndroidElement jewellery;

	public void clickOnWomen() {
		objAndroidGenericMethods.clickOnAndroidElement(getWomen(), "Women");
		System.out.println("Succesfully click on Women");
	}

	public void clickOnindianFusionWear() {
		objAndroidGenericMethods.clickOnAndroidElement(getIndianFusionWear(), "indianFusionWear");
		System.out.println("Succesfully click on indianFusionWear");
	}

	public void clickOnkurtasSuits() {
		objAndroidGenericMethods.clickOnAndroidElement(getKurtasSuits(), "kurtasSuits");
		System.out.println("Succesfully click on kurtasSuits");
	}

	public void clickOnkurtisTunicTops() {
		objAndroidGenericMethods.clickOnAndroidElement(getKurtisTunicTops(), "kurtisTunicTops");
		System.out.println("Succesfully click on kurtisTunicTops");
	}

	public void clickOnleggingSalwarsChudidars() {
		objAndroidGenericMethods.clickOnAndroidElement(getLeggingSalwarsChudidars(), "leggingSalwarsChudidars");
		System.out.println("Succesfully click on leggingSalwarsChudidars");
	}

	public void clickOnskirtsPalazzos() {
		objAndroidGenericMethods.clickOnAndroidElement(getSkirtsPalazzos(), "skirtsPalazzos");
		System.out.println("Succesfully click on skirtsPalazzos");
	}

	public void clickOndressMaterial() {
		objAndroidGenericMethods.clickOnAndroidElement(getDressMaterial(), "dressMaterial");
		System.out.println("Succesfully click on dressMaterial");
	}

	public void clickOnlehnegaCholi() {
		objAndroidGenericMethods.clickOnAndroidElement(getLehnegaCholi(), "lehnegaCholi");
		System.out.println("Succesfully click on lehnegaCholi");
	}

	public void clickOndupattasShawls() {
		objAndroidGenericMethods.clickOnAndroidElement(getDupattasShawls(), "dupattasShawls");
		System.out.println("Succesfully click on dupattasShawls");
	}

	public void clickOnjacketsWaistcoats() {
		objAndroidGenericMethods.clickOnAndroidElement(getJacketsWaistcoats(), "jacketsWaistcoats");
		System.out.println("Succesfully click on jacketsWaistcoats");
	}

	public void clickOnwesternWear() {
		objAndroidGenericMethods.clickOnAndroidElement(getWesternWear(), "westernWear");
		System.out.println("Succesfully click on westernWear");
	}

	public void clickOndressesJumpsuits() {
		objAndroidGenericMethods.clickOnAndroidElement(getDressesJumpsuits(), "dressesJumpsuits");
		System.out.println("Succesfully click on dressesJumpsuits");
	}

	public void clickOntopsTShirtsShirts() {
		objAndroidGenericMethods.clickOnAndroidElement(getTopsTShirtsShirts(), "topsTShirtsShirts");
		System.out.println("Succesfully click on topsTShirtsShirts");
	}

	public void clickOnjeansJeggings() {
		objAndroidGenericMethods.clickOnAndroidElement(getJeansJeggings(), "jeansJeggings");
		System.out.println("Succesfully click on jeansJeggings");
	}

	public void clickOntrousersAndCapris() {
		objAndroidGenericMethods.clickOnAndroidElement(getTrousersAndCapris(), "trousersAndCapris");
		System.out.println("Succesfully click on trousersAndCapris");
	}

	public void clickOnshortsSkirts() {
		objAndroidGenericMethods.clickOnAndroidElement(getShortsSkirts(), "shortsSkirts");
		System.out.println("Succesfully click on shortsSkirts");
	}

	public void clickOnshrugs() {
		objAndroidGenericMethods.clickOnAndroidElement(getShrugs(), "shrugs");
		System.out.println("Succesfully click on shrugs");
	}

	public void clickOnsweatersSweatshirts() {
		objAndroidGenericMethods.clickOnAndroidElement(getSweatersSweatshirts(), "sweatersSweatshirts");
		System.out.println("Succesfully click on sweatersSweatshirts");
	}

	public void clickOnjacketsAndWaistcoats() {
		objAndroidGenericMethods.clickOnAndroidElement(getJacketsAndWaistcoats(), "jacketsAndWaistcoats");
		System.out.println("Succesfully click on jacketsAndWaistcoats");
	}

	public void clickOncoatsAndBlazers() {
		objAndroidGenericMethods.clickOnAndroidElement(getCoatsAndBlazers(), "coatsAndBlazers");
		System.out.println("Succesfully click on coatsAndBlazers");
	}

	public void clickOnlingerieAndSleepwear() {
		objAndroidGenericMethods.clickOnAndroidElement(getLingerieAndSleepwear(), "lingerieAndSleepwear");
		System.out.println("Succesfully click on lingerieAndSleepwear");
	}

	public void clickOnbrasLingerieSets() {
		objAndroidGenericMethods.clickOnAndroidElement(getBrasLingerieSets(), "brasLingerieSets");
		System.out.println("Succesfully click on brasLingerieSets");
	}

	public void clickOnbriefs() {
		objAndroidGenericMethods.clickOnAndroidElement(getBriefs(), "briefs");
		System.out.println("Succesfully click on briefs");
	}

	public void clickOnshapewear() {
		objAndroidGenericMethods.clickOnAndroidElement(getShapewear(), "shapewear");
		System.out.println("Succesfully click on shapewear");
	}

	public void clickOnsleepwearAndLoungewear() {
		objAndroidGenericMethods.clickOnAndroidElement(getSleepwearAndLoungewear(), "sleepwearAndLoungewear");
		System.out.println("Succesfully click on sleepwearAndLoungewear");
	}

	public void clickOnswimwears() {
		objAndroidGenericMethods.clickOnAndroidElement(getSwimwears(), "swimwears");
		System.out.println("Succesfully click on swimwears");
	}

	public void clickOncamisolesThermals() {
		objAndroidGenericMethods.clickOnAndroidElement(getCamisolesThermals(), "camisolesThermals");
		System.out.println("Succesfully click on camisolesThermals");
	}

	public void clickOnfootWare() {
		objAndroidGenericMethods.clickOnAndroidElement(getFootWare(), "footWare");
		System.out.println("Succesfully click on footWare");
	}

	public void clickOnFlatsAndCasualShoes() {
		objAndroidGenericMethods.clickOnAndroidElement(getFlatsAndCasualShoes(), "FlatsAndCasualShoes");
		System.out.println("Succesfully click on FlatsAndCasualShoes");
	}

	public void clickOnheels() {
		objAndroidGenericMethods.clickOnAndroidElement(getHeels(), "heels");
		System.out.println("Succesfully click on heels");
	}

	public void clickOnsportsShoesFloaters() {
		objAndroidGenericMethods.clickOnAndroidElement(getSportsShoesFloaters(), "sportsShoesFloaters");
		System.out.println("Succesfully click on sportsShoesFloaters");
	}

	public void clickOnsportsActiveWear() {
		objAndroidGenericMethods.clickOnAndroidElement(getSportsActiveWear(), "sportsActiveWear");
		System.out.println("Succesfully click on sportsActiveWear");
	}

	public void clickOnclothing() {
		objAndroidGenericMethods.clickOnAndroidElement(getClothing(), "clothing");
		System.out.println("Succesfully click on clothing");
	}

	public void clickOnfootwear() {
		objAndroidGenericMethods.clickOnAndroidElement(getFootwear(), "footwear");
		System.out.println("Succesfully click on footwear");
	}

	public void clickOnaccessories() {
		objAndroidGenericMethods.clickOnAndroidElement(getAccessories(), "accessories");
		System.out.println("Succesfully click on accessories");
	}

	public void clickOnsportsEquipments() {
		objAndroidGenericMethods.clickOnAndroidElement(getSportsEquipments(), "ConfirmButton");
		System.out.println("Succesfully click on sportsEquipments");
	}

	public void clickOnfashionAccessories() {
		objAndroidGenericMethods.clickOnAndroidElement(getFashionAccessories(), "fashionAccessories");
		System.out.println("Succesfully click on fashionAccessories");
	}

	public void clickOnbelt() {
		objAndroidGenericMethods.clickOnAndroidElement(getBelt(), "belt");
		System.out.println("Succesfully click on belt");
	}

	public AndroidElement getWomen() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(Women, "Women");
		return Women;
	}

	public AndroidElement getIndianFusionWear() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(indianFusionWear, "indianFusionWear");
		return indianFusionWear;
	}

	public AndroidElement getKurtasSuits() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(kurtasSuits, "kurtasSuits");
		return kurtasSuits;
	}

	public AndroidElement getKurtisTunicTops() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(kurtisTunicTops, "kurtisTunicTops");
		return kurtisTunicTops;
	}

	public AndroidElement getLeggingSalwarsChudidars() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(leggingSalwarsChudidars, "leggingSalwarsChudidars");
		return leggingSalwarsChudidars;
	}

	public AndroidElement getSkirtsPalazzos() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(skirtsPalazzos, "skirtsPalazzos");
		return skirtsPalazzos;
	}

	public AndroidElement getSareesBlousses() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(sareesBlousses, "sareesBlousses");
		return sareesBlousses;
	}

	public AndroidElement getDressMaterial() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(dressMaterial, "dressMaterial");
		return dressMaterial;
	}

	public AndroidElement getLehnegaCholi() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(lehnegaCholi, "lehnegaCholi");
		return lehnegaCholi;
	}

	public AndroidElement getDupattasShawls() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(dupattasShawls, "dupattasShawls");
		return dupattasShawls;
	}

	public AndroidElement getJacketsWaistcoats() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(jacketsWaistcoats, "jacketsWaistcoats");
		return jacketsWaistcoats;
	}

	public AndroidElement getWesternWear() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(westernWear, "westernWear");
		return westernWear;
	}

	public AndroidElement getDressesJumpsuits() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(dressesJumpsuits, "dressesJumpsuits");
		return dressesJumpsuits;
	}

	public AndroidElement getTopsTShirtsShirts() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(topsTShirtsShirts, "topsTShirtsShirts");
		return topsTShirtsShirts;
	}

	public AndroidElement getJeansJeggings() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(jeansJeggings, "jeansJeggings");
		return jeansJeggings;
	}

	public AndroidElement getTrousersAndCapris() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(trousersAndCapris, "trousersAndCapris");
		return trousersAndCapris;
	}

	public AndroidElement getShortsSkirts() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(shortsSkirts, "shortsSkirts");
		return shortsSkirts;
	}

	public AndroidElement getShrugs() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(shrugs, "shrugs");
		return shrugs;
	}

	public AndroidElement getSweatersSweatshirts() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(sweatersSweatshirts, "sweatersSweatshirts");
		return sweatersSweatshirts;
	}

	public AndroidElement getJacketsAndWaistcoats() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(jacketsAndWaistcoats, "jacketsAndWaistcoats");
		return jacketsAndWaistcoats;
	}

	public AndroidElement getCoatsAndBlazers() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(coatsAndBlazers, "coatsAndBlazers");
		return coatsAndBlazers;
	}

	public AndroidElement getLingerieAndSleepwear() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(lingerieAndSleepwear, "lingerieAndSleepwear");
		return lingerieAndSleepwear;
	}

	public AndroidElement getBrasLingerieSets() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(brasLingerieSets, "brasLingerieSets");
		return brasLingerieSets;
	}

	public AndroidElement getBriefs() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(briefs, "briefs");
		return briefs;
	}

	public AndroidElement getShapewear() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(shapewear, "shapewear");
		return shapewear;
	}

	public AndroidElement getSleepwearAndLoungewear() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(sleepwearAndLoungewear, "sleepwearAndLoungewear");
		return sleepwearAndLoungewear;
	}

	public AndroidElement getSwimwears() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(swimwears, "swimwears");
		return swimwears;
	}

	public AndroidElement getCamisolesThermals() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(camisolesThermals, "camisolesThermals");
		return camisolesThermals;
	}

	public AndroidElement getFootWare() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(footWare, "footWare");
		return footWare;
	}

	public AndroidElement getFlatsAndCasualShoes() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(FlatsAndCasualShoes, "FlatsAndCasualShoes");
		return FlatsAndCasualShoes;
	}

	public AndroidElement getHeels() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(heels, "heels");
		return heels;
	}

	public AndroidElement getSportsShoesFloaters() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(sportsShoesFloaters, "sportsShoesFloaters");
		return sportsShoesFloaters;
	}

	public AndroidElement getSportsActiveWear() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(sportsActiveWear, "sportsActiveWear");
		return sportsActiveWear;
	}

	public AndroidElement getClothing() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(clothing, "clothing");
		return clothing;
	}

	public AndroidElement getFootwear() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(footwear, "footwear");
		return footwear;
	}

	public AndroidElement getAccessories() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(accessories, "accessories");
		return accessories;
	}

	public AndroidElement getSportsEquipments() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(sportsEquipments, "sportsEquipments");
		return sportsEquipments;
	}

	public AndroidElement getFashionAccessories() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(fashionAccessories, "fashionAccessories");
		return fashionAccessories;
	}

	public AndroidElement getBelt() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(belt, "belt");
		return belt;
	}

	public AndroidElement getScarvesStolesGloves() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(scarvesStolesGloves, "scarvesStolesGloves");
		return scarvesStolesGloves;
	}

	public AndroidElement getCapsHats() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(capsHats, "capsHats");
		return capsHats;
	}

	public AndroidElement getHairAccessories() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(hairAccessories, "hairAccessories");
		return hairAccessories;
	}

	public AndroidElement getSock() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(sock, "sock");
		return sock;
	}

	public AndroidElement getHelmet() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(helmet, "helmet");
		return helmet;
	}

	public AndroidElement getTravelAccessorie() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(travelAccessorie, "travelAccessorie");
		return travelAccessorie;
	}

	public AndroidElement getPhonecase() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(phonecase, "phonecase");
		return phonecase;
	}

	public AndroidElement getHandbagsBagsWallets() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(handbagsBagsWallets, "handbagsBagsWallets");
		return handbagsBagsWallets;
	}

	public AndroidElement getWatchesAndWearables() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(WatchesAndWearables, "WatchesAndWearables");
		return WatchesAndWearables;
	}

	public AndroidElement getSunglassesFrames() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(SunglassesFrames, "SunglassesFrames");
		return SunglassesFrames;
	}

	public AndroidElement getHeadphonesSpeakers() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(HeadphonesSpeakers, "HeadphonesSpeakers");
		return HeadphonesSpeakers;
	}

	public AndroidElement getLuggageTrolleys() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(LuggageTrolleys, "LuggageTrolleys");
		return LuggageTrolleys;
	}

	public AndroidElement getBeautyPersonalCare() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(beautyPersonalCare, "beautyPersonalCare");
		return beautyPersonalCare;
	}

	public AndroidElement getJewellery() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(jewellery, "jewellery");
		return jewellery;
	}
}
