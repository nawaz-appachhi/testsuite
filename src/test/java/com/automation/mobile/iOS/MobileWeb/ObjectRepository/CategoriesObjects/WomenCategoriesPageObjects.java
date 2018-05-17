package com.automation.mobile.iOS.MobileWeb.ObjectRepository.CategoriesObjects;

import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.automation.core.mobile.iOS.iOSGenericMethods;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class WomenCategoriesPageObjects {

	public AppiumDriver<MobileElement>  iDriver;
	iOSGenericMethods objiOSGenericMethods;

	public WomenCategoriesPageObjects(AppiumDriver<MobileElement>  iDriver) {
		PageFactory.initElements(new AppiumFieldDecorator(iDriver), this);
		objiOSGenericMethods = new iOSGenericMethods(iDriver);

	}

	@FindBy(xpath = "(//div[@class='clearfix naviLevel level-1'])[2]")
	public IOSElement Women;

	@FindBy(xpath = "(//div[@class='clearfix naviLevel level-2'])[1]")
	public IOSElement indianFusionWear;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[1]")
	public IOSElement kurtasSuits;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[2]")
	public IOSElement kurtisTunicTops;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[3]")
	public IOSElement leggingSalwarsChudidars;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[4]")
	public IOSElement skirtsPalazzos;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[5]")
	public IOSElement sareesBlousses;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[6]")
	public IOSElement dressMaterial;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[7]")
	public IOSElement lehnegaCholi;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[8]")
	public IOSElement dupattasShawls;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[9]")
	public IOSElement jacketsWaistcoats;

	@FindBy(xpath = "(//div[@class='clearfix naviLevel level-2'])[2]")
	public IOSElement westernWear;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[1]")
	public IOSElement dressesJumpsuits;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[2]")
	public IOSElement topsTShirtsShirts;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[3]")
	public IOSElement jeansJeggings;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[4]")
	public IOSElement trousersAndCapris;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[5]")
	public IOSElement shortsSkirts;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[6]")
	public IOSElement shrugs;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[7]")
	public IOSElement sweatersSweatshirts;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[8]")
	public IOSElement jacketsAndWaistcoats;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[9]")
	public IOSElement coatsAndBlazers;

	@FindBy(xpath = "(//div[@class='clearfix naviLevel level-2'])[3]")
	public IOSElement lingerieAndSleepwear;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[1]")
	public IOSElement brasLingerieSets;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[2]")
	public IOSElement briefs;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[3]")
	public IOSElement shapewear;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[4]")
	public IOSElement sleepwearAndLoungewear;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[5]")
	public IOSElement swimwears;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[6]")
	public IOSElement camisolesThermals;

	@FindBy(xpath = "(//div[@class='clearfix naviLevel level-2'])[4]")
	public IOSElement footWare;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[1]")
	public IOSElement FlatsAndCasualShoes;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[2]")
	public IOSElement heels;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[3]")
	public IOSElement sportsShoesFloaters;

	@FindBy(xpath = "(//div[@class='clearfix naviLevel level-2'])[5]")
	public IOSElement sportsActiveWear;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[1]")
	public IOSElement clothing;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[2]")
	public IOSElement footwear;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[3]")
	public IOSElement accessories;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[4]")
	public IOSElement sportsEquipments;

	@FindBy(xpath = "(//div[@class='clearfix naviLevel level-2'])[6]")
	public IOSElement fashionAccessories;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[1]")
	public IOSElement belt;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[2]")
	public IOSElement scarvesStolesGloves;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[3]")
	public IOSElement capsHats;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[4]")
	public IOSElement hairAccessories;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[5]")
	public IOSElement sock;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[6]")
	public IOSElement helmet;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[7]")
	public IOSElement travelAccessorie;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[8]")
	public IOSElement phonecase;

	@FindBy(xpath = "(//a[@class='naviLevel level-2'])[1]")
	public IOSElement handbagsBagsWallets;

	@FindBy(xpath = "(//a[@class='naviLevel level-2'])[2]")
	public IOSElement WatchesAndWearables;

	@FindBy(xpath = "(//a[@class='naviLevel level-2'])[3]")
	public IOSElement SunglassesFrames;

	@FindBy(xpath = "(//a[@class='naviLevel level-2'])[4]")
	public IOSElement HeadphonesSpeakers;

	@FindBy(xpath = "(//a[@class='naviLevel level-2'])[5]")
	public IOSElement LuggageTrolleys;

	@FindBy(xpath = "(//a[@class='naviLevel level-2'])[6]")
	public IOSElement beautyPersonalCare;

	@FindBy(xpath = "(//a[@class='naviLevel level-2'])[7]")
	public IOSElement jewellery;

	public void clickOnWomen() {
		objiOSGenericMethods.clickOnIOSElement(getWomen(), "clicked on Women");
		System.out.println("Succesfully click on Women");
	}

	public void clickOnindianFusionWear() {
		objiOSGenericMethods.clickOnIOSElement(getIndianFusionWear(), "clicked on indianFusionWear");
		System.out.println("Succesfully click on indianFusionWear");
	}

	public void clickOnkurtasSuits() {
		objiOSGenericMethods.clickOnIOSElement(getKurtasSuits(), "clicked on kurtasSuits");
		System.out.println("Succesfully click on kurtasSuits");
	}

	public void clickOnkurtisTunicTops() {
		objiOSGenericMethods.clickOnIOSElement(getKurtisTunicTops(), "clicked on kurtisTunicTops");
		System.out.println("Succesfully click on kurtisTunicTops");
	}

	public void clickOnleggingSalwarsChudidars() {
		objiOSGenericMethods.clickOnIOSElement(getLeggingSalwarsChudidars(), "clicked on leggingSalwarsChudidars");
		System.out.println("Succesfully click on leggingSalwarsChudidars");
	}

	public void clickOnskirtsPalazzos() {
		objiOSGenericMethods.clickOnIOSElement(getSkirtsPalazzos(), "clicked on skirtsPalazzos");
		System.out.println("Succesfully click on skirtsPalazzos");
	}

	public void clickOndressMaterial() {
		objiOSGenericMethods.clickOnIOSElement(getDressMaterial(), "clicked on dressMaterial");
		System.out.println("Succesfully click on dressMaterial");
	}

	public void clickOnlehnegaCholi() {
		objiOSGenericMethods.clickOnIOSElement(getLehnegaCholi(), "clicked on lehnegaCholi");
		System.out.println("Succesfully click on lehnegaCholi");
	}

	public void clickOndupattasShawls() {
		objiOSGenericMethods.clickOnIOSElement(getDupattasShawls(), "clicked on dupattasShawls");
		System.out.println("Succesfully click on dupattasShawls");
	}

	public void clickOnjacketsWaistcoats() {
		objiOSGenericMethods.clickOnIOSElement(getJacketsWaistcoats(), "clicked on jacketsWaistcoats");
		System.out.println("Succesfully click on jacketsWaistcoats");
	}

	public void clickOnwesternWear() {
		objiOSGenericMethods.clickOnIOSElement(getWesternWear(), "clicked on westernWear");
		System.out.println("Succesfully click on westernWear");
	}

	public void clickOndressesJumpsuits() {
		objiOSGenericMethods.clickOnIOSElement(getDressesJumpsuits(), "clicked on dressesJumpsuits");
		System.out.println("Succesfully click on dressesJumpsuits");
	}

	public void clickOntopsTShirtsShirts() {
		objiOSGenericMethods.clickOnIOSElement(getTopsTShirtsShirts(), "clicked on topsTShirtsShirts");
		System.out.println("Succesfully click on topsTShirtsShirts");
	}

	public void clickOnjeansJeggings() {
		objiOSGenericMethods.clickOnIOSElement(getJeansJeggings(), "clicked on jeansJeggings");
		System.out.println("Succesfully click on jeansJeggings");
	}

	public void clickOntrousersAndCapris() {
		objiOSGenericMethods.clickOnIOSElement(getTrousersAndCapris(), "clicked on trousersAndCapris");
		System.out.println("Succesfully click on trousersAndCapris");
	}

	public void clickOnshortsSkirts() {
		objiOSGenericMethods.clickOnIOSElement(getShortsSkirts(), "clicked on shortsSkirts");
		System.out.println("Succesfully click on shortsSkirts");
	}

	public void clickOnshrugs() {
		objiOSGenericMethods.clickOnIOSElement(getShrugs(), "clicked on shrugs");
		System.out.println("Succesfully click on shrugs");
	}

	public void clickOnsweatersSweatshirts() {
		objiOSGenericMethods.clickOnIOSElement(getSweatersSweatshirts(), "clicked on sweatersSweatshirts");
		System.out.println("Succesfully click on sweatersSweatshirts");
	}

	public void clickOnjacketsAndWaistcoats() {
		objiOSGenericMethods.clickOnIOSElement(getJacketsAndWaistcoats(), "clicked on jacketsAndWaistcoats");
		System.out.println("Succesfully click on jacketsAndWaistcoats");
	}

	public void clickOncoatsAndBlazers() {
		objiOSGenericMethods.clickOnIOSElement(getCoatsAndBlazers(), "clicked on coatsAndBlazers");
		System.out.println("Succesfully click on coatsAndBlazers");
	}

	public void clickOnlingerieAndSleepwear() {
		objiOSGenericMethods.clickOnIOSElement(getLingerieAndSleepwear(), "clicked on lingerieAndSleepwear");
		System.out.println("Succesfully click on lingerieAndSleepwear");
	}

	public void clickOnbrasLingerieSets() {
		objiOSGenericMethods.clickOnIOSElement(getBrasLingerieSets(), "clicked on brasLingerieSets");
		System.out.println("Succesfully click on brasLingerieSets");
	}

	public void clickOnbriefs() {
		objiOSGenericMethods.clickOnIOSElement(getBriefs(), "clicked on briefs");
		System.out.println("Succesfully click on briefs");
	}

	public void clickOnshapewear() {
		objiOSGenericMethods.clickOnIOSElement(getShapewear(), "clicked on shapewear");
		System.out.println("Succesfully click on shapewear");
	}

	public void clickOnsleepwearAndLoungewear() {
		objiOSGenericMethods.clickOnIOSElement(getSleepwearAndLoungewear(), "clicked on sleepwearAndLoungewear");
		System.out.println("Succesfully click on sleepwearAndLoungewear");
	}

	public void clickOnswimwears() {
		objiOSGenericMethods.clickOnIOSElement(getSwimwears(), "clicked on swimwears");
		System.out.println("Succesfully click on swimwears");
	}

	public void clickOncamisolesThermals() {
		objiOSGenericMethods.clickOnIOSElement(getCamisolesThermals(), "clicked on camisolesThermals");
		System.out.println("Succesfully click on camisolesThermals");
	}

	public void clickOnfootWare() {
		objiOSGenericMethods.clickOnIOSElement(getFootWare(), "clicked on footWare");
		System.out.println("Succesfully click on footWare");
	}

	public void clickOnFlatsAndCasualShoes() {
		objiOSGenericMethods.clickOnIOSElement(getFlatsAndCasualShoes(), "clicked on FlatsAndCasualShoes");
		System.out.println("Succesfully click on FlatsAndCasualShoes");
	}

	public void clickOnheels() {
		objiOSGenericMethods.clickOnIOSElement(getHeels(), "clicked on heels");
		System.out.println("Succesfully click on heels");
	}

	public void clickOnsportsShoesFloaters() {
		objiOSGenericMethods.clickOnIOSElement(getSportsShoesFloaters(), "clicked on sportsShoesFloaters");
		System.out.println("Succesfully click on sportsShoesFloaters");
	}

	public void clickOnsportsActiveWear() {
		objiOSGenericMethods.clickOnIOSElement(getSportsActiveWear(), "clicked on sportsActiveWear");
		System.out.println("Succesfully click on sportsActiveWear");
	}

	public void clickOnclothing() {
		objiOSGenericMethods.clickOnIOSElement(getClothing(), "clicked on clothing");
		System.out.println("Succesfully click on clothing");
	}

	public void clickOnfootwear() {
		objiOSGenericMethods.clickOnIOSElement(getFootwear(), "clicked on footwear");
		System.out.println("Succesfully click on footwear");
	}

	public void clickOnaccessories() {
		objiOSGenericMethods.clickOnIOSElement(getAccessories(), "clicked on accessories");
		System.out.println("Succesfully click on accessories");
	}

	public void clickOnsportsEquipments() {
		objiOSGenericMethods.clickOnIOSElement(getSportsEquipments(), "clicked on ConfirmButton");
		System.out.println("Succesfully click on sportsEquipments");
	}

	public void clickOnfashionAccessories() {
		objiOSGenericMethods.clickOnIOSElement(getFashionAccessories(), "clicked on fashionAccessories");
		System.out.println("Succesfully click on fashionAccessories");
	}

	public void clickOnbelt() {
		objiOSGenericMethods.clickOnIOSElement(getBelt(), "clicked on belt");
		System.out.println("Succesfully click on belt");
	}

	public IOSElement getWomen() {
		objiOSGenericMethods.CheckIOSElementFound(Women, "Women");
		return Women;
	}

	public IOSElement getIndianFusionWear() {
		objiOSGenericMethods.CheckIOSElementFound(indianFusionWear, "indianFusionWear");
		return indianFusionWear;
	}

	public IOSElement getKurtasSuits() {
		objiOSGenericMethods.CheckIOSElementFound(kurtasSuits, "kurtasSuits");
		return kurtasSuits;
	}

	public IOSElement getKurtisTunicTops() {
		objiOSGenericMethods.CheckIOSElementFound(kurtisTunicTops, "kurtisTunicTops");
		return kurtisTunicTops;
	}

	public IOSElement getLeggingSalwarsChudidars() {
		objiOSGenericMethods.CheckIOSElementFound(leggingSalwarsChudidars, "leggingSalwarsChudidars");
		return leggingSalwarsChudidars;
	}

	public IOSElement getSkirtsPalazzos() {
		objiOSGenericMethods.CheckIOSElementFound(skirtsPalazzos, "skirtsPalazzos");
		return skirtsPalazzos;
	}

	public IOSElement getSareesBlousses() {
		objiOSGenericMethods.CheckIOSElementFound(sareesBlousses, "sareesBlousses");
		return sareesBlousses;
	}

	public IOSElement getDressMaterial() {
		objiOSGenericMethods.CheckIOSElementFound(dressMaterial, "dressMaterial");
		return dressMaterial;
	}

	public IOSElement getLehnegaCholi() {
		objiOSGenericMethods.CheckIOSElementFound(lehnegaCholi, "lehnegaCholi");
		return lehnegaCholi;
	}

	public IOSElement getDupattasShawls() {
		objiOSGenericMethods.CheckIOSElementFound(dupattasShawls, "dupattasShawls");
		return dupattasShawls;
	}

	public IOSElement getJacketsWaistcoats() {
		objiOSGenericMethods.CheckIOSElementFound(jacketsWaistcoats, "jacketsWaistcoats");
		return jacketsWaistcoats;
	}

	public IOSElement getWesternWear() {
		objiOSGenericMethods.CheckIOSElementFound(westernWear, "westernWear");
		return westernWear;
	}

	public IOSElement getDressesJumpsuits() {
		objiOSGenericMethods.CheckIOSElementFound(dressesJumpsuits, "dressesJumpsuits");
		return dressesJumpsuits;
	}

	public IOSElement getTopsTShirtsShirts() {
		objiOSGenericMethods.CheckIOSElementFound(topsTShirtsShirts, "topsTShirtsShirts");
		return topsTShirtsShirts;
	}

	public IOSElement getJeansJeggings() {
		objiOSGenericMethods.CheckIOSElementFound(jeansJeggings, "jeansJeggings");
		return jeansJeggings;
	}

	public IOSElement getTrousersAndCapris() {
		objiOSGenericMethods.CheckIOSElementFound(trousersAndCapris, "trousersAndCapris");
		return trousersAndCapris;
	}

	public IOSElement getShortsSkirts() {
		objiOSGenericMethods.CheckIOSElementFound(shortsSkirts, "shortsSkirts");
		return shortsSkirts;
	}

	public IOSElement getShrugs() {
		objiOSGenericMethods.CheckIOSElementFound(shrugs, "shrugs");
		return shrugs;
	}

	public IOSElement getSweatersSweatshirts() {
		objiOSGenericMethods.CheckIOSElementFound(sweatersSweatshirts, "sweatersSweatshirts");
		return sweatersSweatshirts;
	}

	public IOSElement getJacketsAndWaistcoats() {
		objiOSGenericMethods.CheckIOSElementFound(jacketsAndWaistcoats, "jacketsAndWaistcoats");
		return jacketsAndWaistcoats;
	}

	public IOSElement getCoatsAndBlazers() {
		objiOSGenericMethods.CheckIOSElementFound(coatsAndBlazers, "coatsAndBlazers");
		return coatsAndBlazers;
	}

	public IOSElement getLingerieAndSleepwear() {
		objiOSGenericMethods.CheckIOSElementFound(lingerieAndSleepwear, "lingerieAndSleepwear");
		return lingerieAndSleepwear;
	}

	public IOSElement getBrasLingerieSets() {
		objiOSGenericMethods.CheckIOSElementFound(brasLingerieSets, "brasLingerieSets");
		return brasLingerieSets;
	}

	public IOSElement getBriefs() {
		objiOSGenericMethods.CheckIOSElementFound(briefs, "briefs");
		return briefs;
	}

	public IOSElement getShapewear() {
		objiOSGenericMethods.CheckIOSElementFound(shapewear, "shapewear");
		return shapewear;
	}

	public IOSElement getSleepwearAndLoungewear() {
		objiOSGenericMethods.CheckIOSElementFound(sleepwearAndLoungewear, "sleepwearAndLoungewear");
		return sleepwearAndLoungewear;
	}

	public IOSElement getSwimwears() {
		objiOSGenericMethods.CheckIOSElementFound(swimwears, "swimwears");
		return swimwears;
	}

	public IOSElement getCamisolesThermals() {
		objiOSGenericMethods.CheckIOSElementFound(camisolesThermals, "camisolesThermals");
		return camisolesThermals;
	}

	public IOSElement getFootWare() {
		objiOSGenericMethods.CheckIOSElementFound(footWare, "footWare");
		return footWare;
	}

	public IOSElement getFlatsAndCasualShoes() {
		objiOSGenericMethods.CheckIOSElementFound(FlatsAndCasualShoes, "FlatsAndCasualShoes");
		return FlatsAndCasualShoes;
	}

	public IOSElement getHeels() {
		objiOSGenericMethods.CheckIOSElementFound(heels, "heels");
		return heels;
	}

	public IOSElement getSportsShoesFloaters() {
		objiOSGenericMethods.CheckIOSElementFound(sportsShoesFloaters, "sportsShoesFloaters");
		return sportsShoesFloaters;
	}

	public IOSElement getSportsActiveWear() {
		objiOSGenericMethods.CheckIOSElementFound(sportsActiveWear, "sportsActiveWear");
		return sportsActiveWear;
	}

	public IOSElement getClothing() {
		objiOSGenericMethods.CheckIOSElementFound(clothing, "clothing");
		return clothing;
	}

	public IOSElement getFootwear() {
		objiOSGenericMethods.CheckIOSElementFound(footwear, "footwear");
		return footwear;
	}

	public IOSElement getAccessories() {
		objiOSGenericMethods.CheckIOSElementFound(accessories, "accessories");
		return accessories;
	}

	public IOSElement getSportsEquipments() {
		objiOSGenericMethods.CheckIOSElementFound(sportsEquipments, "sportsEquipments");
		return sportsEquipments;
	}

	public IOSElement getFashionAccessories() {
		objiOSGenericMethods.CheckIOSElementFound(fashionAccessories, "fashionAccessories");
		return fashionAccessories;
	}

	public IOSElement getBelt() {
		objiOSGenericMethods.CheckIOSElementFound(belt, "belt");
		return belt;
	}

	public IOSElement getScarvesStolesGloves() {
		objiOSGenericMethods.CheckIOSElementFound(scarvesStolesGloves, "scarvesStolesGloves");
		return scarvesStolesGloves;
	}

	public IOSElement getCapsHats() {
		objiOSGenericMethods.CheckIOSElementFound(capsHats, "capsHats");
		return capsHats;
	}

	public IOSElement getHairAccessories() {
		objiOSGenericMethods.CheckIOSElementFound(hairAccessories, "hairAccessories");
		return hairAccessories;
	}

	public IOSElement getSock() {
		objiOSGenericMethods.CheckIOSElementFound(sock, "sock");
		return sock;
	}

	public IOSElement getHelmet() {
		objiOSGenericMethods.CheckIOSElementFound(helmet, "helmet");
		return helmet;
	}

	public IOSElement getTravelAccessorie() {
		objiOSGenericMethods.CheckIOSElementFound(travelAccessorie, "travelAccessorie");
		return travelAccessorie;
	}

	public IOSElement getPhonecase() {
		objiOSGenericMethods.CheckIOSElementFound(phonecase, "phonecase");
		return phonecase;
	}

	public IOSElement getHandbagsBagsWallets() {
		objiOSGenericMethods.CheckIOSElementFound(handbagsBagsWallets, "handbagsBagsWallets");
		return handbagsBagsWallets;
	}

	public IOSElement getWatchesAndWearables() {
		objiOSGenericMethods.CheckIOSElementFound(WatchesAndWearables, "WatchesAndWearables");
		return WatchesAndWearables;
	}

	public IOSElement getSunglassesFrames() {
		objiOSGenericMethods.CheckIOSElementFound(SunglassesFrames, "SunglassesFrames");
		return SunglassesFrames;
	}

	public IOSElement getHeadphonesSpeakers() {
		objiOSGenericMethods.CheckIOSElementFound(HeadphonesSpeakers, "HeadphonesSpeakers");
		return HeadphonesSpeakers;
	}

	public IOSElement getLuggageTrolleys() {
		objiOSGenericMethods.CheckIOSElementFound(LuggageTrolleys, "LuggageTrolleys");
		return LuggageTrolleys;
	}

	public IOSElement getBeautyPersonalCare() {
		objiOSGenericMethods.CheckIOSElementFound(beautyPersonalCare, "beautyPersonalCare");
		return beautyPersonalCare;
	}

	public IOSElement getJewellery() {
		objiOSGenericMethods.CheckIOSElementFound(jewellery, "jewellery");
		return jewellery;
	}
}
