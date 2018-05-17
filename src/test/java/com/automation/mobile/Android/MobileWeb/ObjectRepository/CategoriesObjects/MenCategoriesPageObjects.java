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
 * Modified By:Aishurya:Changed string parameter,which is being used in reporing
 */

public class MenCategoriesPageObjects {

	AndroidGenericMethods objAndroidGenericMethods;
	public MenCategoriesPageObjects(AppiumDriver<MobileElement> aDriver) {
		PageFactory.initElements(new AppiumFieldDecorator(aDriver), this);
		objAndroidGenericMethods = new AndroidGenericMethods(aDriver);
	}

	/**
	 * Hamburger
	 */
	@FindBy(className = "icon icon-menu")
	public AndroidElement hamburger;

	/**
	 * Men
	 * Modified by Rakesh, 
	 * 
	 *old path: (//div[@class='clearfix naviLevel level-1'])[1]
	 */
	@FindBy(xpath = "//span[contains(text(),'Men')]")
	public AndroidElement men;

	@FindBy(xpath = "(//div[@class='clearfix naviLevel level-2'])[1]")
	public AndroidElement topWare;

	@FindBy(xpath = "(//div[@class = 'clearfix naviLevel level-2']/../../div[2])[1]")
	public AndroidElement tShirts;

	@FindBy(xpath = "(//div[@class = 'clearfix naviLevel level-2']/../../div[3])[1]")
	public AndroidElement casualShirts;

	@FindBy(xpath = "(//div[@class = 'clearfix naviLevel level-2']/../../div[4])[1]")
	public AndroidElement formalShirts;

	@FindBy(xpath = "(//div[@class = 'clearfix naviLevel level-2']/../../div[5])[1]")
	public AndroidElement sweatersAndSweatshirts;

	@FindBy(xpath = "(//div[@class = 'clearfix naviLevel level-2']/../../div[6])[1]")
	public AndroidElement jackets;

	@FindBy(xpath = "(//div[@class = 'clearfix naviLevel level-2']/../../div[7])[1]")
	public AndroidElement blazersAndCoats;

	@FindBy(xpath = "(//div[@class = 'clearfix naviLevel level-2']/../../div[8])[1]")
	public AndroidElement suits;

	@FindBy(xpath = "(//div[@class='clearfix naviLevel level-2'])[2]")
	public AndroidElement bottomware;

	@FindBy(xpath = "(//div[@class = 'clearfix naviLevel level-2']/../../div[2])[2]")
	public AndroidElement jeans;

	@FindBy(xpath = "(//div[@class = 'clearfix naviLevel level-2']/../../div[3])[2]")
	public AndroidElement casualTrousers;

	@FindBy(xpath = "(//div[@class = 'clearfix naviLevel level-2']/../../div[4])[2]")
	public AndroidElement formalTrousers;

	@FindBy(xpath = "(//div[@class = 'clearfix naviLevel level-2']/../../div[5])[2]")
	public AndroidElement shorts;

	@FindBy(xpath = "(//div[@class = 'clearfix naviLevel level-2']/../../div[6])[2]")
	public AndroidElement trackPantsJoggers;

	@FindBy(xpath = "(//div[@class='clearfix naviLevel level-2'])[3]")
	public AndroidElement sportsActiveWare;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[1]")
	public AndroidElement activeTShirt;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[2]")
	public AndroidElement trackPantShorts;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[3]")
	public AndroidElement jacketsSweatshirts;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[4]")
	public AndroidElement swimwear;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[5]")
	public AndroidElement smartWearables;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[6]")
	public AndroidElement sportsAccessories;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[7]")
	public AndroidElement sportsEquipment;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[8]")
	public AndroidElement sportsShoes;

	@FindBy(xpath = "(//div[@class='clearfix naviLevel level-2'])[4]")
	public AndroidElement IndianFestiveWare;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[1]")
	public AndroidElement kurtasAndKurtaSets;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[2]")
	public AndroidElement sherwanis;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[3]")
	public AndroidElement nehruJackets;

	@FindBy(xpath = "(//div[@class='clearfix naviLevel level-2'])[5]")
	public AndroidElement InnerWareSleepWare;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[1]")
	public AndroidElement briefeTrunks;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[2]")
	public AndroidElement boxers;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[3]")
	public AndroidElement veets;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[4]")
	public AndroidElement sleepwearLoungewaer;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[5]")
	public AndroidElement thermals;

	@FindBy(xpath = "(//div[@class='clearfix naviLevel level-2'])[6]")
	public AndroidElement FootWare;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[1]")
	public AndroidElement casualShoes;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[2]")
	public AndroidElement sportShoes;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[3]")
	public AndroidElement formalShoes;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[4]")
	public AndroidElement sandalsFloaters;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[5]")
	public AndroidElement flipFlops;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[6]")
	public AndroidElement customizableShoes;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[7]")
	public AndroidElement socks;

	@FindBy(xpath = "(//div[@class='clearfix naviLevel level-2'])[7]")
	public AndroidElement FashionAccessories;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[1]")
	public AndroidElement wallets;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[2]")
	public AndroidElement belts;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[3]")
	public AndroidElement tiesCufflinksPocketSquares;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[4]")
	public AndroidElement accessoryGiftSets;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[5]")
	public AndroidElement helmets;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[6]")
	public AndroidElement capsAndHats;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[7]")
	public AndroidElement MufflersScarvesGloves;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[8]")
	public AndroidElement phoneCases;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[9]")
	public AndroidElement travelAccessories;

	@FindBy(xpath = "(//a[@class = 'naviLevel level-3'])[10]")
	public AndroidElement ringsAndWristwear;

	@FindBy(xpath = "(//a[@class='naviLevel level-2'])[1]")
	public AndroidElement PlusSize;

	@FindBy(xpath = "(//a[@class='naviLevel level-2'])[2]")
	public AndroidElement WatchesWearables;

	@FindBy(xpath = "(//a[@class='naviLevel level-2'])[3]")
	public AndroidElement SunglassesAndFrames;

	@FindBy(xpath = "(//a[@class='naviLevel level-2'])[4]")
	public AndroidElement HeadphonesAndSpeakers;

	@FindBy(xpath = "(//a[@class='naviLevel level-2'])[5]")
	public AndroidElement BagsAndBagpacks;

	@FindBy(xpath = "(//a[@class='naviLevel level-2'])[6]")
	public AndroidElement LuggageAndTrolleys;

	@FindBy(xpath = "(//a[@class='naviLevel level-2'])[7]")
	public AndroidElement PersonalCareAndGrooming;

	public void clickOnhamburger() {
		objAndroidGenericMethods.clickOnAndroidElement(getHamburger(), "hamburger");
		System.out.println("Succesfully click on Hamburger");
	}

	public void clickOnmen() throws InterruptedException {
		objAndroidGenericMethods.clickOnAndroidElement(getMen(), "men");
		System.out.println("Succesfully click on men");
	}

	public void clickOntopWare() throws InterruptedException {
		objAndroidGenericMethods.clickOnAndroidElement(getTopWare(), "topWare");
		System.out.println("Succesfully click on topWare");
	}

	public void clickOntShirts() throws InterruptedException {
		objAndroidGenericMethods.clickOnAndroidElement(gettShirts(), "tShirts");
		System.out.println("Succesfully click on tShirts");
	}

	public void clickOnformalShirts() {
		objAndroidGenericMethods.clickOnAndroidElement(getCasualShirts(), "casualShirts");
		System.out.println("Succesfully click on formalShirts");
	}

	public void clickOnsweatersAndSweatshirts() {
		objAndroidGenericMethods.clickOnAndroidElement(getSweatersAndSweatshirts(), "sweatersAndSweatshirts");
		System.out.println("Succesfully click on sweatersAndSweatshirts");
	}

	public void clickOncasualShirts() {
		objAndroidGenericMethods.clickOnAndroidElement(getCasualShirts(), "casualShirts");
		System.out.println("Succesfully click on casualShirts");
	}

	public void clickOnjackets() {
		objAndroidGenericMethods.clickOnAndroidElement(getJackets(), "jackets");
		System.out.println("Succesfully click on jackets");
	}

	public void clickOnblazersAndCoats() {
		objAndroidGenericMethods.clickOnAndroidElement(getBlazersAndCoats(), "blazersAndCoats");
		System.out.println("Succesfully click on blazersAndCoats");
	}

	public void clickOnsuits() {
		objAndroidGenericMethods.clickOnAndroidElement(getSuits(), "suits");
		System.out.println("Succesfully click on suits");
	}

	public void clickOnbottomware() {
		objAndroidGenericMethods.clickOnAndroidElement(getBottomware(), "bottomware");
		System.out.println("Succesfully click on bottomware");
	}

	public void clickOnformalTrousers() {
		objAndroidGenericMethods.clickOnAndroidElement(getFormalTrousers(), "formalTrousers");
		System.out.println("Succesfully click on formalTrousers");
	}

	public void clickOntrackPantsJoggers() {
		objAndroidGenericMethods.clickOnAndroidElement(getTrackPantsJoggers(), "trackPantsJoggers");
		System.out.println("Succesfully click on trackPantsJoggers");
	}

	public void clickOnactiveTShirt() throws InterruptedException {
		objAndroidGenericMethods.clickOnAndroidElement(getActiveTShirt(), "activeTShirt");
		System.out.println("Succesfully click on activeTShirt");
	}

	public void clickOntrackPantShorts() {
		objAndroidGenericMethods.clickOnAndroidElement(getTrackPantShorts(), "trackPantShorts");
		System.out.println("Succesfully click on trackPantShorts");
	}

	public void clickOnjacketsSweatshirts() {
		objAndroidGenericMethods.clickOnAndroidElement(getJacketsSweatshirts(), "jacketsSweatshirts");
		System.out.println("Succesfully click on jacketsSweatshirts");
	}

	public void clickOnswimwear() {
		objAndroidGenericMethods.clickOnAndroidElement(getSwimwear(), "swimwear");
		System.out.println("Succesfully click on swimwear");
	}

	public void clickOnsmartWearables() {
		objAndroidGenericMethods.clickOnAndroidElement(getSmartWearables(), "smartWearables");
		System.out.println("Succesfully click on smartWearables");
	}

	public void clickOnsportsAccessories() {
		objAndroidGenericMethods.clickOnAndroidElement(getSportsAccessories(), "sportsAccessories");
		System.out.println("Succesfully click on sportsAccessories");
	}

	public void clickOnnehruJackets() {
		objAndroidGenericMethods.clickOnAndroidElement(getNehruJackets(), "nehruJackets");
		System.out.println("Succesfully click on nehruJackets");
	}

	public void clickOnInnerWareSleepWare() {
		objAndroidGenericMethods.clickOnAndroidElement(getInnerWareSleepWare(), "InnerWareSleepWare");
		System.out.println("Succesfully click on InnerWareSleepWare");
	}

	public void clickOnsportsEquipment() {
		objAndroidGenericMethods.clickOnAndroidElement(getSportsEquipment(), "sportsEquipment");
		System.out.println("Succesfully click on sportsEquipment");
	}

	public void clickOnsportsShoes() {
		objAndroidGenericMethods.clickOnAndroidElement(getSportsShoes(), "sportsShoes");
		System.out.println("Succesfully click on sportsShoes");
	}

	public void clickOnIndianFestiveWare() {
		objAndroidGenericMethods.clickOnAndroidElement(getIndianFestiveWare(), "IndianFestiveWare");
		System.out.println("Succesfully click on IndianFestiveWare");
	}

	public void clickOnkurtasAndKurtaSets() {
		objAndroidGenericMethods.clickOnAndroidElement(getKurtasAndKurtaSets(), "kurtasAndKurtaSets");
		System.out.println("Succesfully click on kurtasAndKurtaSets");
	}

	public void clickOnsherwanis() {
		objAndroidGenericMethods.clickOnAndroidElement(getSherwanis(), "sherwanis");
		System.out.println("Succesfully click on sherwanis");
	}

	public void clickOnbriefeTrunks() {
		objAndroidGenericMethods.clickOnAndroidElement(getBriefeTrunks(), "briefeTrunks");
		System.out.println("Succesfully click on briefeTrunks");
	}

	public void clickOnsleepwearLoungewaer() {
		objAndroidGenericMethods.clickOnAndroidElement(getSleepwearLoungewaer(), "sleepwearLoungewaer");
		System.out.println("Succesfully click on sleepwearLoungewaer");
	}

	public void clickOnthermals() {
		objAndroidGenericMethods.clickOnAndroidElement(getThermals(), "thermals");
		System.out.println("Succesfully click on thermals");
	}

	public void clickOnboxers() {
		objAndroidGenericMethods.clickOnAndroidElement(getBoxers(), "boxers");
		System.out.println("Succesfully click on boxers");
	}

	public void clickOnveets() {
		objAndroidGenericMethods.clickOnAndroidElement(getVeets(), "veets");
		System.out.println("Succesfully click on veets");
	}

	public void clickOnFootWare() {
		objAndroidGenericMethods.clickOnAndroidElement(getFootWare(), "FootWare");
		System.out.println("Succesfully click on FootWare");
	}

	public void clickOncasualShoes() {
		objAndroidGenericMethods.clickOnAndroidElement(getCasualShoes(), "casualShoes");
		System.out.println("Succesfully click on casualShoes");
	}

	public void clickOnsportShoes() {
		objAndroidGenericMethods.clickOnAndroidElement(getSportShoes(), "sportShoes");
		System.out.println("Succesfully click on sportShoes");
	}

	public void clickOnformalShoes() {
		objAndroidGenericMethods.clickOnAndroidElement(getFormalShoes(), "formalShoes");
		System.out.println("Succesfully click on formalShoes");
	}

	public void clickOnsandalsFloaters() {
		objAndroidGenericMethods.clickOnAndroidElement(getSandalsFloaters(), "sandalsFloaters");
		System.out.println("Succesfully click on sandalsFloaters");
	}

	public void clickOnflipFlops() {
		objAndroidGenericMethods.clickOnAndroidElement(getFlipFlops(), "flipFlops");
		System.out.println("Succesfully click on flipFlops");
	}

	public void clickOncustomizableShoes() {
		objAndroidGenericMethods.clickOnAndroidElement(getCustomizableShoes(), "customizableShoes");
		System.out.println("Succesfully click on customizableShoes");
	}

	public void clickOnsocks() {
		objAndroidGenericMethods.clickOnAndroidElement(getSocks(), "socks");
		System.out.println("Succesfully click on socks");
	}

	public void clickOnFashionAccessories() {
		objAndroidGenericMethods.clickOnAndroidElement(getFashionAccessories(), "FashionAccessories");
		System.out.println("Succesfully click on FashionAccessories");
	}

	public void clickOnwallets() {
		objAndroidGenericMethods.clickOnAndroidElement(getWallets(), "wallets");
		System.out.println("Succesfully click on wallets");
	}

	public void clickOnbelts() {
		objAndroidGenericMethods.clickOnAndroidElement(getBelts(), "belts");
		System.out.println("Succesfully click on belts");
	}

	public void clickOntiesCufflinksPocketSquares() {
		objAndroidGenericMethods.clickOnAndroidElement(getTiesCufflinksPocketSquares(), "tiesCufflinksPocketSquares");
		System.out.println("Succesfully click on tiesCufflinksPocketSquares");
	}

	public void clickOnaccessoryGiftSets() {
		objAndroidGenericMethods.clickOnAndroidElement(getAccessoryGiftSets(), "accessoryGiftSets");
		System.out.println("Succesfully click on accessoryGiftSets");
	}

	public void clickOnhelmets() {
		objAndroidGenericMethods.clickOnAndroidElement(getHelmets(), "helmets");
		System.out.println("Succesfully click on helmets");
	}

	public void clickOncapsAndHats() {
		objAndroidGenericMethods.clickOnAndroidElement(getCapsAndHats(), "capsAndHats");
		System.out.println("Succesfully click on capsAndHats");
	}

	public void clickOnMufflersScarvesGloves() {
		objAndroidGenericMethods.clickOnAndroidElement(getMufflersScarvesGloves(), "MufflersScarvesGloves");
		System.out.println("Succesfully click on MufflersScarvesGloves");
	}

	public void clickOnphoneCases() {
		objAndroidGenericMethods.clickOnAndroidElement(getPhoneCases(), "phoneCases");
		System.out.println("Succesfully click on phoneCases");
	}

	public void clickOntravelAccessories() {
		objAndroidGenericMethods.clickOnAndroidElement(getTravelAccessories(), "travelAccessories");
		System.out.println("Succesfully click on travelAccessories");
	}

	public void clickOnringsAndWristwear() {
		objAndroidGenericMethods.clickOnAndroidElement(getRingsAndWristwear(), "ringsAndWristwear");
		System.out.println("Succesfully click on ringsAndWristwear");
	}

	public void clickOnPlusSize() {
		objAndroidGenericMethods.clickOnAndroidElement(getPlusSize(), "PlusSize");
		System.out.println("Succesfully click on PlusSize");
	}

	public void clickOnWatchesWearables() {
		objAndroidGenericMethods.clickOnAndroidElement(getWatchesWearables(), "WatchesWearables");
		System.out.println("Succesfully click on WatchesWearables");
	}

	public void clickOnSunglassesAndFrames() {
		objAndroidGenericMethods.clickOnAndroidElement(getSunglassesAndFrames(), "SunglassesAndFrames");
		System.out.println("Succesfully click on SunglassesAndFrames");
	}

	public void clickOnHeadphonesAndSpeakers() {
		objAndroidGenericMethods.clickOnAndroidElement(getHeadphonesAndSpeakers(), "HeadphonesAndSpeakers");
		System.out.println("Succesfully click on HeadphonesAndSpeakers");
	}

	public void clickOnBagsAndBagpacks() {
		objAndroidGenericMethods.clickOnAndroidElement(getBagsAndBagpacks(), "BagsAndBagpacks");
		System.out.println("Succesfully click on BagsAndBagpacks");
	}

	public void clickOnLuggageAndTrolleys() {
		objAndroidGenericMethods.clickOnAndroidElement(getLuggageAndTrolleys(), "LuggageAndTrolleys");
		System.out.println("Succesfully click on LuggageAndTrolleys");
	}

	public void clickOnPersonalCareAndGrooming() {
		objAndroidGenericMethods.clickOnAndroidElement(getPersonalCareAndGrooming(), "PersonalCareAndGrooming");
		System.out.println("Succesfully click on PersonalCareAndGrooming");
	}

	public AndroidElement getHamburger() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(hamburger, "hamburger");
		return hamburger;
	}

	public AndroidElement getMen() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(men, "men");
		return men;
	}

	public AndroidElement getTopWare() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(topWare, "topWare");
		return topWare;
	}

	public AndroidElement gettShirts() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(tShirts, "tShirts");
		return tShirts;
	}

	public AndroidElement getCasualShirts() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(casualShirts, "casualShirts");
		return casualShirts;
	}

	public AndroidElement getFormalShirts() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(formalShirts, "formalShirts");
		return formalShirts;
	}

	public AndroidElement getSweatersAndSweatshirts() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(sweatersAndSweatshirts, "sweatersAndSweatshirts");
		return sweatersAndSweatshirts;
	}

	public AndroidElement getJackets() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(jackets, "jackets");
		return jackets;
	}

	public AndroidElement getBlazersAndCoats() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(blazersAndCoats, "blazersAndCoats");
		return blazersAndCoats;
	}

	public AndroidElement getSuits() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(suits, "suits");
		return suits;
	}

	public AndroidElement getBottomware() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(bottomware, "bottomware");
		return bottomware;
	}

	public AndroidElement getJeans() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(jeans, "jeans");
		return jeans;
	}

	public AndroidElement getCasualTrousers() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(casualTrousers, "casualTrousers");
		return casualTrousers;
	}

	public AndroidElement getFormalTrousers() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(formalTrousers, "formalTrousers");
		return formalTrousers;
	}

	public AndroidElement getShorts() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(shorts, "shorts");
		return shorts;
	}

	public AndroidElement getTrackPantsJoggers() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(trackPantsJoggers, "trackPantsJoggers");
		return trackPantsJoggers;
	}

	public AndroidElement getSportsActiveWare() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(sportsActiveWare, "sportsActiveWare");
		return sportsActiveWare;
	}

	public AndroidElement getActiveTShirt() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(activeTShirt, "activeTShirt");
		return activeTShirt;
	}

	public AndroidElement getTrackPantShorts() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(trackPantShorts, "trackPantShorts");
		return trackPantShorts;
	}

	public AndroidElement getJacketsSweatshirts() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(jacketsSweatshirts, "jacketsSweatshirts");
		return jacketsSweatshirts;
	}

	public AndroidElement getSwimwear() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(swimwear, "swimwear");
		return swimwear;
	}

	public AndroidElement getSmartWearables() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(smartWearables, "smartWearables");
		return smartWearables;
	}

	public AndroidElement getSportsAccessories() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(sportsAccessories, "sportsAccessories");
		return sportsAccessories;
	}

	public AndroidElement getSportsEquipment() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(sportsEquipment, "sportsEquipment");
		return sportsEquipment;
	}

	public AndroidElement getSportsShoes() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(sportsShoes, "sportsShoes");
		return sportsShoes;
	}

	public AndroidElement getIndianFestiveWare() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(IndianFestiveWare, "IndianFestiveWare");
		return IndianFestiveWare;
	}

	public AndroidElement getKurtasAndKurtaSets() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(kurtasAndKurtaSets, "kurtasAndKurtaSets");
		return kurtasAndKurtaSets;
	}

	public AndroidElement getSherwanis() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(sherwanis, "sherwanis");
		return sherwanis;
	}

	public AndroidElement getNehruJackets() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(nehruJackets, "nehruJackets");
		return nehruJackets;
	}

	public AndroidElement getInnerWareSleepWare() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(InnerWareSleepWare, "InnerWareSleepWare");
		return InnerWareSleepWare;
	}

	public AndroidElement getBriefeTrunks() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(briefeTrunks, "briefeTrunks");
		return briefeTrunks;
	}

	public AndroidElement getBoxers() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(boxers, "boxers");
		return boxers;
	}

	public AndroidElement getVeets() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(veets, "veets");
		return veets;
	}

	public AndroidElement getSleepwearLoungewaer() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(sleepwearLoungewaer, "sleepwearLoungewaer");
		return sleepwearLoungewaer;
	}

	public AndroidElement getThermals() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(thermals, "thermals");
		return thermals;
	}

	public AndroidElement getFootWare() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(FootWare, "FootWare");
		return FootWare;
	}

	public AndroidElement getCasualShoes() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(casualShoes, "casualShoes");
		return casualShoes;
	}

	public AndroidElement getSportShoes() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(sportShoes, "sportShoes");
		return sportShoes;
	}

	public AndroidElement getFormalShoes() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(formalShoes, "formalShoes");
		return formalShoes;
	}

	public AndroidElement getSandalsFloaters() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(sandalsFloaters, "sandalsFloaters");
		return sandalsFloaters;
	}

	public AndroidElement getFlipFlops() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(flipFlops, "flipFlops");
		return flipFlops;
	}

	public AndroidElement getCustomizableShoes() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(customizableShoes, "customizableShoes");
		return customizableShoes;
	}

	public AndroidElement getSocks() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(socks, "socks");
		return socks;
	}

	public AndroidElement getFashionAccessories() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(FashionAccessories, "FashionAccessories");
		return FashionAccessories;
	}

	public AndroidElement getWallets() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(wallets, "wallets");
		return wallets;
	}

	public AndroidElement getBelts() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(belts, "belts");
		return belts;
	}

	public AndroidElement getTiesCufflinksPocketSquares() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(tiesCufflinksPocketSquares, "tiesCufflinksPocketSquares");
		return tiesCufflinksPocketSquares;
	}

	public AndroidElement getAccessoryGiftSets() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(accessoryGiftSets, "accessoryGiftSets");
		return accessoryGiftSets;
	}

	public AndroidElement getHelmets() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(helmets, "helmets");
		return helmets;
	}

	public AndroidElement getCapsAndHats() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(capsAndHats, "capsAndHats");
		return capsAndHats;
	}

	public AndroidElement getMufflersScarvesGloves() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(MufflersScarvesGloves, "MufflersScarvesGloves");
		return MufflersScarvesGloves;
	}

	public AndroidElement getPhoneCases() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(phoneCases, "phoneCases");
		return phoneCases;
	}

	public AndroidElement getTravelAccessories() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(travelAccessories, "travelAccessories");
		return travelAccessories;
	}

	public AndroidElement getRingsAndWristwear() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(ringsAndWristwear, "AddreringsAndWristwearss");
		return ringsAndWristwear;
	}

	public AndroidElement getPlusSize() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(PlusSize, "PlusSize");
		return PlusSize;
	}

	public AndroidElement getWatchesWearables() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(WatchesWearables, "WatchesWearables");
		return WatchesWearables;
	}

	public AndroidElement getSunglassesAndFrames() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(SunglassesAndFrames, "SunglassesAndFrames");
		return SunglassesAndFrames;
	}

	public AndroidElement getHeadphonesAndSpeakers() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(HeadphonesAndSpeakers, "HeadphonesAndSpeakers");
		return HeadphonesAndSpeakers;
	}

	public AndroidElement getBagsAndBagpacks() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(BagsAndBagpacks, "BagsAndBagpacks");
		return BagsAndBagpacks;
	}

	public AndroidElement getLuggageAndTrolleys() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(LuggageAndTrolleys, "LuggageAndTrolleys");
		return LuggageAndTrolleys;
	}

	public AndroidElement getPersonalCareAndGrooming() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(PersonalCareAndGrooming, "PersonalCareAndGrooming");
		return PersonalCareAndGrooming;
	}

}
