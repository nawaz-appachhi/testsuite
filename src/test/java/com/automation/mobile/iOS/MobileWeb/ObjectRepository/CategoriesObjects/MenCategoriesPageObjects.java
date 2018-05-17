package com.automation.mobile.iOS.MobileWeb.ObjectRepository.CategoriesObjects;

import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.automation.core.mobile.iOS.iOSGenericMethods;

import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class MenCategoriesPageObjects {

	public IOSDriver<IOSElement> iDriver;
	iOSGenericMethods objiOSGenericMethods;

	public MenCategoriesPageObjects(IOSDriver<IOSElement> iDriver) {
		PageFactory.initElements(new AppiumFieldDecorator(iDriver), this);
		objiOSGenericMethods = new iOSGenericMethods(iDriver);

	}

	/**
	 * Hamburger
	 */
	@FindBy(className = "icon icon-menu")
	public IOSElement hamburger;

	/**
	 * Men
	 */

	@FindBy(xpath = "(//div[@class='clearfix naviLevel level-1'])[1]")
	public IOSElement men;

	@FindBy(xpath = "(//div[@class='clearfix naviLevel level-2'])[1]")
	public IOSElement topWare;

	/*xpath changed by - NItesh
	 * older - @FindBy(xpath = "(//div[@class = 'clearfix naviLevel level-2']/../../div[2])[1]")
	 * New - @FindBy(xpath = "//a[@href='/men-tshirts?src=tNav']")
	 * */
	
//	@FindBy(xpath = "(//div[@class = 'clearfix naviLevel level-2']/../../div[2])[1]")
	@FindBy(xpath = "//a[@href='/men-tshirts?src=tNav']")
	public IOSElement tShirts;

	@FindBy(xpath = "(//div[@class = 'clearfix naviLevel level-2']/../../div[3])[1]")
	public IOSElement casualShirts;

	@FindBy(xpath = "(//div[@class = 'clearfix naviLevel level-2']/../../div[4])[1]")
	public IOSElement formalShirts;

	@FindBy(xpath = "(//div[@class = 'clearfix naviLevel level-2']/../../div[5])[1]")
	public IOSElement sweatersAndSweatshirts;

	@FindBy(xpath = "(//div[@class = 'clearfix naviLevel level-2']/../../div[6])[1]")
	public IOSElement jackets;

	@FindBy(xpath = "(//div[@class = 'clearfix naviLevel level-2']/../../div[7])[1]")
	public IOSElement blazersAndCoats;

	@FindBy(xpath = "(//div[@class = 'clearfix naviLevel level-2']/../../div[8])[1]")
	public IOSElement suits;

	@FindBy(xpath = "(//div[@class='clearfix naviLevel level-2'])[2]")
	public IOSElement bottomware;

	@FindBy(xpath = "(//div[@class = 'clearfix naviLevel level-2']/../../div[2])[2]")
	public IOSElement jeans;

	@FindBy(xpath = "(//div[@class = 'clearfix naviLevel level-2']/../../div[3])[2]")
	public IOSElement casualTrousers;

	@FindBy(xpath = "(//div[@class = 'clearfix naviLevel level-2']/../../div[4])[2]")
	public IOSElement formalTrousers;

	@FindBy(xpath = "(//div[@class = 'clearfix naviLevel level-2']/../../div[5])[2]")
	public IOSElement shorts;

	@FindBy(xpath = "(//div[@class = 'clearfix naviLevel level-2']/../../div[6])[2]")
	public IOSElement trackPantsJoggers;

	@FindBy(xpath = "(//div[@class='clearfix naviLevel level-2'])[3]")
	public IOSElement sportsActiveWare;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[1]")
	public IOSElement activeTShirt;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[2]")
	public IOSElement trackPantShorts;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[3]")
	public IOSElement jacketsSweatshirts;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[4]")
	public IOSElement swimwear;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[5]")
	public IOSElement smartWearables;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[6]")
	public IOSElement sportsAccessories;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[7]")
	public IOSElement sportsEquipment;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[8]")
	public IOSElement sportsShoes;

	@FindBy(xpath = "(//div[@class='clearfix naviLevel level-2'])[4]")
	public IOSElement IndianFestiveWare;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[1]")
	public IOSElement kurtasAndKurtaSets;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[2]")
	public IOSElement sherwanis;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[3]")
	public IOSElement nehruJackets;

	@FindBy(xpath = "(//div[@class='clearfix naviLevel level-2'])[5]")
	public IOSElement InnerWareSleepWare;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[1]")
	public IOSElement briefeTrunks;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[2]")
	public IOSElement boxers;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[3]")
	public IOSElement veets;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[4]")
	public IOSElement sleepwearLoungewaer;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[5]")
	public IOSElement thermals;

	@FindBy(xpath = "(//div[@class='clearfix naviLevel level-2'])[6]")
	public IOSElement FootWare;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[1]")
	public IOSElement casualShoes;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[2]")
	public IOSElement sportShoes;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[3]")
	public IOSElement formalShoes;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[4]")
	public IOSElement sandalsFloaters;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[5]")
	public IOSElement flipFlops;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[6]")
	public IOSElement customizableShoes;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[7]")
	public IOSElement socks;

	@FindBy(xpath = "(//div[@class='clearfix naviLevel level-2'])[7]")
	public IOSElement FashionAccessories;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[1]")
	public IOSElement wallets;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[2]")
	public IOSElement belts;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[3]")
	public IOSElement tiesCufflinksPocketSquares;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[4]")
	public IOSElement accessoryGiftSets;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[5]")
	public IOSElement helmets;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[6]")
	public IOSElement capsAndHats;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[7]")
	public IOSElement MufflersScarvesGloves;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[8]")
	public IOSElement phoneCases;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[9]")
	public IOSElement travelAccessories;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[10]")
	public IOSElement ringsAndWristwear;

	@FindBy(xpath = "(//a[@class='naviLevel level-2'])[1]")
	public IOSElement PlusSize;

	@FindBy(xpath = "(//a[@class='naviLevel level-2'])[2]")
	public IOSElement WatchesWearables;

	@FindBy(xpath = "(//a[@class='naviLevel level-2'])[3]")
	public IOSElement SunglassesAndFrames;

	@FindBy(xpath = "(//a[@class='naviLevel level-2'])[4]")
	public IOSElement HeadphonesAndSpeakers;

	@FindBy(xpath = "(//a[@class='naviLevel level-2'])[5]")
	public IOSElement BagsAndBagpacks;

	@FindBy(xpath = "(//a[@class='naviLevel level-2'])[6]")
	public IOSElement LuggageAndTrolleys;

	@FindBy(xpath = "(//a[@class='naviLevel level-2'])[7]")
	public IOSElement PersonalCareAndGrooming;

	public void clickOnhamburger() {
		objiOSGenericMethods.clickOnIOSElement(getHamburger(), "clicked on hamburger");
	}

	public void clickOnmen() {
		objiOSGenericMethods.clickOnIOSElement(getMen(), "clicked on men");
	}

	public void clickOntopWare() {
		objiOSGenericMethods.clickOnIOSElement(getTopWare(), "clicked on topWare");
	}

	public void clickOntShirts() {
		objiOSGenericMethods.clickOnIOSElement(gettShirts(), "clicked on tShirts");
	}

	public void clickOnformalShirts() {
		objiOSGenericMethods.clickOnIOSElement(getCasualShirts(), "clicked on casualShirts");
	}

	public void clickOnsweatersAndSweatshirts() {
		objiOSGenericMethods.clickOnIOSElement(getSweatersAndSweatshirts(), "clicked on sweatersAndSweatshirts");
	}

	public void clickOncasualShirts() {
		objiOSGenericMethods.clickOnIOSElement(getCasualShirts(), "clicked on casualShirts");
	}

	public void clickOnjackets() {
		objiOSGenericMethods.clickOnIOSElement(getJackets(), "clicked on jackets");
	}

	public void clickOnblazersAndCoats() {
		objiOSGenericMethods.clickOnIOSElement(getBlazersAndCoats(), "clicked on blazersAndCoats");
	}

	public void clickOnsuits() {
		objiOSGenericMethods.clickOnIOSElement(getSuits(), "clicked on suits");
	}

	public void clickOnbottomware() {
		objiOSGenericMethods.clickOnIOSElement(getBottomware(), "clicked on bottomware");
	}

	public void clickOnformalTrousers() {
		objiOSGenericMethods.clickOnIOSElement(getFormalTrousers(), "clicked on formalTrousers");
	}

	public void clickOntrackPantsJoggers() {
		objiOSGenericMethods.clickOnIOSElement(getTrackPantsJoggers(), "clicked on trackPantsJoggers");
	}

	public void clickOnactiveTShirt() {
		objiOSGenericMethods.clickOnIOSElement(getActiveTShirt(), "clicked on activeTShirt");
	}

	public void clickOntrackPantShorts() {
		objiOSGenericMethods.clickOnIOSElement(getTrackPantShorts(), "clicked on trackPantShorts");
		activeTShirt.click();
	}

	public void clickOnjacketsSweatshirts() {
		objiOSGenericMethods.clickOnIOSElement(getJacketsSweatshirts(), "clicked on jacketsSweatshirts");
	}

	public void clickOnswimwear() {
		objiOSGenericMethods.clickOnIOSElement(getSwimwear(), "clicked on swimwear");
	}

	public void clickOnsmartWearables() {
		objiOSGenericMethods.clickOnIOSElement(getSmartWearables(), "clicked on smartWearables");
	}

	public void clickOnsportsAccessories() {
		objiOSGenericMethods.clickOnIOSElement(getSportsAccessories(), "clicked on sportsAccessories");
	}

	public void clickOnnehruJackets() {
		objiOSGenericMethods.clickOnIOSElement(getNehruJackets(), "clicked on nehruJackets");
	}

	public void clickOnInnerWareSleepWare() {
		objiOSGenericMethods.clickOnIOSElement(getInnerWareSleepWare(), "clicked on InnerWareSleepWare");
	}

	public void clickOnsportsEquipment() {
		objiOSGenericMethods.clickOnIOSElement(getSportsEquipment(), "clicked on sportsEquipment");
	}

	public void clickOnsportsShoes() {
		objiOSGenericMethods.clickOnIOSElement(getSportsShoes(), "clicked on sportsShoes");
	}

	public void clickOnIndianFestiveWare() {
		objiOSGenericMethods.clickOnIOSElement(getIndianFestiveWare(), "clicked on IndianFestiveWare");
	}

	public void clickOnkurtasAndKurtaSets() {
		objiOSGenericMethods.clickOnIOSElement(getKurtasAndKurtaSets(), "clicked on kurtasAndKurtaSets");
	}

	public void clickOnsherwanis() {
		objiOSGenericMethods.clickOnIOSElement(getSherwanis(), "clicked on sherwanis");
	}

	public void clickOnbriefeTrunks() {
		objiOSGenericMethods.clickOnIOSElement(getBriefeTrunks(), "clicked on briefeTrunks");
	}

	public void clickOnsleepwearLoungewaer() {
		objiOSGenericMethods.clickOnIOSElement(getSleepwearLoungewaer(), "clicked on sleepwearLoungewaer");
	}

	public void clickOnthermals() {
		objiOSGenericMethods.clickOnIOSElement(getThermals(), "clicked on thermals");
	}

	public void clickOnboxers() {
		objiOSGenericMethods.clickOnIOSElement(getBoxers(), "clicked on boxers");
	}

	public void clickOnveets() {
		objiOSGenericMethods.clickOnIOSElement(getVeets(), "clicked on veets");
	}

	public void clickOnFootWare() {
		objiOSGenericMethods.clickOnIOSElement(getFootWare(), "clicked on FootWare");
	}

	public void clickOncasualShoes() {
		objiOSGenericMethods.clickOnIOSElement(getCasualShoes(), "clicked on casualShoes");
		System.out.println("Succesfully click on casualShoes");
	}

	public void clickOnsportShoes() {
		objiOSGenericMethods.clickOnIOSElement(getSportShoes(), "clicked on sportShoes");
	}

	public void clickOnformalShoes() {
		objiOSGenericMethods.clickOnIOSElement(getFormalShoes(), "clicked on formalShoes");
	}

	public void clickOnsandalsFloaters() {
		objiOSGenericMethods.clickOnIOSElement(getSandalsFloaters(), "clicked on sandalsFloaters");
	}

	public void clickOnflipFlops() {
		objiOSGenericMethods.clickOnIOSElement(getFlipFlops(), "clicked on flipFlops");
	}

	public void clickOncustomizableShoes() {
		objiOSGenericMethods.clickOnIOSElement(getCustomizableShoes(), "clicked on customizableShoes");
	}

	public void clickOnsocks() {
		objiOSGenericMethods.clickOnIOSElement(getSocks(), "clicked on socks");
	}

	public void clickOnFashionAccessories() {
		objiOSGenericMethods.clickOnIOSElement(getFashionAccessories(), "clicked on FashionAccessories");
	}

	public void clickOnwallets() {
		objiOSGenericMethods.clickOnIOSElement(getWallets(), "clicked on wallets");
	}

	public void clickOnbelts() {
		objiOSGenericMethods.clickOnIOSElement(getBelts(), "clicked on belts");
	}

	public void clickOntiesCufflinksPocketSquares() {
		objiOSGenericMethods.clickOnIOSElement(getTiesCufflinksPocketSquares(), "clicked on tiesCufflinksPocketSquares");
	}

	public void clickOnaccessoryGiftSets() {
		objiOSGenericMethods.clickOnIOSElement(getAccessoryGiftSets(), "clicked on accessoryGiftSets");
	}

	public void clickOnhelmets() {
		objiOSGenericMethods.clickOnIOSElement(getHelmets(), "clicked on helmets");
	}

	public void clickOncapsAndHats() {
		objiOSGenericMethods.clickOnIOSElement(getCapsAndHats(), "clicked on capsAndHats");
	}

	public void clickOnMufflersScarvesGloves() {
		objiOSGenericMethods.clickOnIOSElement(getMufflersScarvesGloves(), "clicked on MufflersScarvesGloves");
	}

	public void clickOnphoneCases() {
		objiOSGenericMethods.clickOnIOSElement(getPhoneCases(), "clicked on phoneCases");
	}

	public void clickOntravelAccessories() {
		objiOSGenericMethods.clickOnIOSElement(getTravelAccessories(), "clicked on travelAccessories");
	}

	public void clickOnringsAndWristwear() {
		objiOSGenericMethods.clickOnIOSElement(getRingsAndWristwear(), "clicked on ringsAndWristwear");
	}

	public void clickOnPlusSize() {
		objiOSGenericMethods.clickOnIOSElement(getPlusSize(), "clicked on PlusSize");
	}

	public void clickOnWatchesWearables() {
		objiOSGenericMethods.clickOnIOSElement(getWatchesWearables(), "clicked on WatchesWearables");
	}

	public void clickOnSunglassesAndFrames() {
		objiOSGenericMethods.clickOnIOSElement(getSunglassesAndFrames(), "clicked on SunglassesAndFrames");
	}

	public void clickOnHeadphonesAndSpeakers() {
		objiOSGenericMethods.clickOnIOSElement(getHeadphonesAndSpeakers(), "clicked on HeadphonesAndSpeakers");
	}

	public void clickOnBagsAndBagpacks() {
		objiOSGenericMethods.clickOnIOSElement(getBagsAndBagpacks(), "clicked on BagsAndBagpacks");
	}

	public void clickOnLuggageAndTrolleys() {
		objiOSGenericMethods.clickOnIOSElement(getLuggageAndTrolleys(), "clicked on LuggageAndTrolleys");
	}

	public void clickOnPersonalCareAndGrooming() {
		objiOSGenericMethods.clickOnIOSElement(getPersonalCareAndGrooming(), "clicked on PersonalCareAndGrooming");
	}

	public IOSElement getHamburger() {
		objiOSGenericMethods.CheckIOSElementFound(hamburger, "hamburger");
		return hamburger;
	}

	public IOSElement getMen() {
		objiOSGenericMethods.CheckIOSElementFound(men, "men");
		return men;
	}

	public IOSElement getTopWare() {
		objiOSGenericMethods.CheckIOSElementFound(topWare, "topWare");
		return topWare;
	}

	public IOSElement gettShirts() {
		objiOSGenericMethods.CheckIOSElementFound(tShirts, "tShirts");
		return tShirts;
	}

	public IOSElement getCasualShirts() {
		objiOSGenericMethods.CheckIOSElementFound(casualShirts, "casualShirts");
		return casualShirts;
	}

	public IOSElement getFormalShirts() {
		objiOSGenericMethods.CheckIOSElementFound(formalShirts, "formalShirts");
		return formalShirts;
	}

	public IOSElement getSweatersAndSweatshirts() {
		objiOSGenericMethods.CheckIOSElementFound(sweatersAndSweatshirts, "sweatersAndSweatshirts");
		return sweatersAndSweatshirts;
	}

	public IOSElement getJackets() {
		objiOSGenericMethods.CheckIOSElementFound(jackets, "jackets");
		return jackets;
	}

	public IOSElement getBlazersAndCoats() {
		objiOSGenericMethods.CheckIOSElementFound(blazersAndCoats, "blazersAndCoats");
		return blazersAndCoats;
	}

	public IOSElement getSuits() {
		objiOSGenericMethods.CheckIOSElementFound(suits, "suits");
		return suits;
	}

	public IOSElement getBottomware() {
		objiOSGenericMethods.CheckIOSElementFound(bottomware, "bottomware");
		return bottomware;
	}

	public IOSElement getJeans() {
		objiOSGenericMethods.CheckIOSElementFound(jeans, "jeans");
		return jeans;
	}

	public IOSElement getCasualTrousers() {
		objiOSGenericMethods.CheckIOSElementFound(casualTrousers, "casualTrousers");
		return casualTrousers;
	}

	public IOSElement getFormalTrousers() {
		objiOSGenericMethods.CheckIOSElementFound(formalTrousers, "formalTrousers");
		return formalTrousers;
	}

	public IOSElement getShorts() {
		objiOSGenericMethods.CheckIOSElementFound(shorts, "shorts");
		return shorts;
	}

	public IOSElement getTrackPantsJoggers() {
		objiOSGenericMethods.CheckIOSElementFound(trackPantsJoggers, "trackPantsJoggers");
		return trackPantsJoggers;
	}

	public IOSElement getSportsActiveWare() {
		objiOSGenericMethods.CheckIOSElementFound(sportsActiveWare, "sportsActiveWare");
		return sportsActiveWare;
	}

	public IOSElement getActiveTShirt() {
		objiOSGenericMethods.CheckIOSElementFound(activeTShirt, "activeTShirt");
		return activeTShirt;
	}

	public IOSElement getTrackPantShorts() {
		objiOSGenericMethods.CheckIOSElementFound(trackPantShorts, "trackPantShorts");
		return trackPantShorts;
	}

	public IOSElement getJacketsSweatshirts() {
		objiOSGenericMethods.CheckIOSElementFound(jacketsSweatshirts, "jacketsSweatshirts");
		return jacketsSweatshirts;
	}

	public IOSElement getSwimwear() {
		objiOSGenericMethods.CheckIOSElementFound(swimwear, "swimwear");
		return swimwear;
	}

	public IOSElement getSmartWearables() {
		objiOSGenericMethods.CheckIOSElementFound(smartWearables, "smartWearables");
		return smartWearables;
	}

	public IOSElement getSportsAccessories() {
		objiOSGenericMethods.CheckIOSElementFound(sportsAccessories, "sportsAccessories");
		return sportsAccessories;
	}

	public IOSElement getSportsEquipment() {
		objiOSGenericMethods.CheckIOSElementFound(sportsEquipment, "sportsEquipment");
		return sportsEquipment;
	}

	public IOSElement getSportsShoes() {
		objiOSGenericMethods.CheckIOSElementFound(sportsShoes, "sportsShoes");
		return sportsShoes;
	}

	public IOSElement getIndianFestiveWare() {
		objiOSGenericMethods.CheckIOSElementFound(IndianFestiveWare, "IndianFestiveWare");
		return IndianFestiveWare;
	}

	public IOSElement getKurtasAndKurtaSets() {
		objiOSGenericMethods.CheckIOSElementFound(kurtasAndKurtaSets, "kurtasAndKurtaSets");
		return kurtasAndKurtaSets;
	}

	public IOSElement getSherwanis() {
		objiOSGenericMethods.CheckIOSElementFound(sherwanis, "sherwanis");
		return sherwanis;
	}

	public IOSElement getNehruJackets() {
		objiOSGenericMethods.CheckIOSElementFound(nehruJackets, "nehruJackets");
		return nehruJackets;
	}

	public IOSElement getInnerWareSleepWare() {
		objiOSGenericMethods.CheckIOSElementFound(InnerWareSleepWare, "InnerWareSleepWare");
		return InnerWareSleepWare;
	}

	public IOSElement getBriefeTrunks() {
		objiOSGenericMethods.CheckIOSElementFound(briefeTrunks, "briefeTrunks");
		return briefeTrunks;
	}

	public IOSElement getBoxers() {
		objiOSGenericMethods.CheckIOSElementFound(boxers, "boxers");
		return boxers;
	}

	public IOSElement getVeets() {
		objiOSGenericMethods.CheckIOSElementFound(veets, "veets");
		return veets;
	}

	public IOSElement getSleepwearLoungewaer() {
		objiOSGenericMethods.CheckIOSElementFound(sleepwearLoungewaer, "sleepwearLoungewaer");
		return sleepwearLoungewaer;
	}

	public IOSElement getThermals() {
		objiOSGenericMethods.CheckIOSElementFound(thermals, "thermals");
		return thermals;
	}

	public IOSElement getFootWare() {
		objiOSGenericMethods.CheckIOSElementFound(FootWare, "FootWare");
		return FootWare;
	}

	public IOSElement getCasualShoes() {
		objiOSGenericMethods.CheckIOSElementFound(casualShoes, "casualShoes");
		return casualShoes;
	}

	public IOSElement getSportShoes() {
		objiOSGenericMethods.CheckIOSElementFound(sportShoes, "sportShoes");
		return sportShoes;
	}

	public IOSElement getFormalShoes() {
		objiOSGenericMethods.CheckIOSElementFound(formalShoes, "formalShoes");
		return formalShoes;
	}

	public IOSElement getSandalsFloaters() {
		objiOSGenericMethods.CheckIOSElementFound(sandalsFloaters, "sandalsFloaters");
		return sandalsFloaters;
	}

	public IOSElement getFlipFlops() {
		objiOSGenericMethods.CheckIOSElementFound(flipFlops, "flipFlops");
		return flipFlops;
	}

	public IOSElement getCustomizableShoes() {
		objiOSGenericMethods.CheckIOSElementFound(customizableShoes, "customizableShoes");
		return customizableShoes;
	}

	public IOSElement getSocks() {
		objiOSGenericMethods.CheckIOSElementFound(socks, "socks");
		return socks;
	}

	public IOSElement getFashionAccessories() {
		objiOSGenericMethods.CheckIOSElementFound(FashionAccessories, "FashionAccessories");
		return FashionAccessories;
	}

	public IOSElement getWallets() {
		objiOSGenericMethods.CheckIOSElementFound(wallets, "wallets");
		return wallets;
	}

	public IOSElement getBelts() {
		objiOSGenericMethods.CheckIOSElementFound(belts, "belts");
		return belts;
	}

	public IOSElement getTiesCufflinksPocketSquares() {
		objiOSGenericMethods.CheckIOSElementFound(tiesCufflinksPocketSquares, "tiesCufflinksPocketSquares");
		return tiesCufflinksPocketSquares;
	}

	public IOSElement getAccessoryGiftSets() {
		objiOSGenericMethods.CheckIOSElementFound(accessoryGiftSets, "accessoryGiftSets");
		return accessoryGiftSets;
	}

	public IOSElement getHelmets() {
		objiOSGenericMethods.CheckIOSElementFound(helmets, "helmets");
		return helmets;
	}

	public IOSElement getCapsAndHats() {
		objiOSGenericMethods.CheckIOSElementFound(capsAndHats, "capsAndHats");
		return capsAndHats;
	}

	public IOSElement getMufflersScarvesGloves() {
		objiOSGenericMethods.CheckIOSElementFound(MufflersScarvesGloves, "MufflersScarvesGloves");
		return MufflersScarvesGloves;
	}

	public IOSElement getPhoneCases() {
		objiOSGenericMethods.CheckIOSElementFound(phoneCases, "phoneCases");
		return phoneCases;
	}

	public IOSElement getTravelAccessories() {
		objiOSGenericMethods.CheckIOSElementFound(travelAccessories, "travelAccessories");
		return travelAccessories;
	}

	public IOSElement getRingsAndWristwear() {
		objiOSGenericMethods.CheckIOSElementFound(ringsAndWristwear, "AddreringsAndWristwearss");
		return ringsAndWristwear;
	}

	public IOSElement getPlusSize() {
		objiOSGenericMethods.CheckIOSElementFound(PlusSize, "PlusSize");
		return PlusSize;
	}

	public IOSElement getWatchesWearables() {
		objiOSGenericMethods.CheckIOSElementFound(WatchesWearables, "WatchesWearables");
		return WatchesWearables;
	}

	public IOSElement getSunglassesAndFrames() {
		objiOSGenericMethods.CheckIOSElementFound(SunglassesAndFrames, "SunglassesAndFrames");
		return SunglassesAndFrames;
	}

	public IOSElement getHeadphonesAndSpeakers() {
		objiOSGenericMethods.CheckIOSElementFound(HeadphonesAndSpeakers, "HeadphonesAndSpeakers");
		return HeadphonesAndSpeakers;
	}

	public IOSElement getBagsAndBagpacks() {
		objiOSGenericMethods.CheckIOSElementFound(BagsAndBagpacks, "BagsAndBagpacks");
		return BagsAndBagpacks;
	}

	public IOSElement getLuggageAndTrolleys() {
		objiOSGenericMethods.CheckIOSElementFound(LuggageAndTrolleys, "LuggageAndTrolleys");
		return LuggageAndTrolleys;
	}

	public IOSElement getPersonalCareAndGrooming() {
		objiOSGenericMethods.CheckIOSElementFound(PersonalCareAndGrooming, "PersonalCareAndGrooming");
		return PersonalCareAndGrooming;
	}

}
