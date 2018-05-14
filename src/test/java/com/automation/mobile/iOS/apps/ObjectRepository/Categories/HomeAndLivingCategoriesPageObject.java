package com.automation.mobile.iOS.apps.ObjectRepository.Categories;

import java.nio.channels.AcceptPendingException;

import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.automation.core.mobile.iOS.iOSGenericMethods;

import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;

public class HomeAndLivingCategoriesPageObject {
	iOSGenericMethods objiOSGenericMethods;
	public IOSDriver<IOSElement> iDriver;
	
	public HomeAndLivingCategoriesPageObject(IOSDriver<IOSElement> iDriver) {
		PageFactory.initElements(new AppiumFieldDecorator(iDriver), this);
		objiOSGenericMethods = new iOSGenericMethods(iDriver);
	}

	@iOSFindBy(accessibility = "Home & Living Bedsheets, Blankets, Towels, Curtains, Pillows")
	public IOSElement homeAndLiving;

	/*
	 * Home And Living categories
	 */

	@iOSFindBy(accessibility = "Explore Home Store")
	public IOSElement homeExploreHomeStore;

	@iOSFindBy(accessibility = "Gifting ideas")
	public IOSElement homeGiftingIdeas;

	@iOSFindBy(accessibility = "Brands ")
	public IOSElement homeBrands;

	/*
	 * different brands
	 */

	@iOSFindBy(accessibility = "Raymond Home")
	public IOSElement homeBrandRaymondHome;

	@iOSFindBy(accessibility = "Spaces")
	public IOSElement homeBrandSpaces;

	@iOSFindBy(accessibility = "Corelle")
	public IOSElement homeBrandCorelle;

	@iOSFindBy(accessibility = "Home sparkle")
	public IOSElement homeBrandSparkle;

	@iOSFindBy(accessibility = "Random")
	public IOSElement homeBrandRandom;

	@iOSFindBy(accessibility = "Portico")
	public IOSElement homeBrandPortico;

	@iOSFindBy(accessibility = "Swayam")
	public IOSElement homeBrandSwayam;

	@iOSFindBy(accessibility = "Sej by Nisha Gupta")
	public IOSElement homeBrandSejbyNishaGupta;

	@iOSFindBy(accessibility = "Tangerine")
	public IOSElement homeBrandTangerine;

	@iOSFindBy(accessibility = "Trident")
	public IOSElement homeBrandTrident;

	@iOSFindBy(accessibility = "Bed Linen & Furnishing ")
	public IOSElement homeBedLinenAndFurnishing;

	/*
	 * different Bed Linen & Furnishing
	 */

	@iOSFindBy(accessibility = "Bedsheets")
	public IOSElement homeBedLinenBedsheets;

	@iOSFindBy(accessibility = "Bed Covers")
	public IOSElement homeBedLinenBedCovers;

	@iOSFindBy(accessibility = "Bedding Sets")
	public IOSElement homeBedLinenBeddingSets;

	@iOSFindBy(accessibility = "Blankets, Quilts & Dohars")
	public IOSElement homeBedLinenBlanketsQuiltsAndDohars;

	@iOSFindBy(accessibility = "Pillows & Pillow Covers")
	public IOSElement homeBedLinenPillowsAndCovers;

	@iOSFindBy(accessibility = "Curtains")
	public IOSElement homecurtains;

	@iOSFindBy(accessibility = "Cushions & Cushion Covers")
	public IOSElement homeCushionsAndCushionCovers;

	@iOSFindBy(accessibility = "Home Décor ")
	public IOSElement homeHomeDecor;

	/*
	 * different home decor
	 */

	@iOSFindBy(accessibility = "Artificial Flowers & Plants")
	public IOSElement homeHomeDecorArtificialFlowersAndPlants;

	@iOSFindBy(accessibility = "Candles & Candle Holders")
	public IOSElement homeHomeDecorCandlesAndHolders;

	@iOSFindBy(accessibility = "Clocks")
	public IOSElement homeHomeDecorClocks;

	@iOSFindBy(accessibility = "Fountains")
	public IOSElement homeHomeDecorFountains;

	@iOSFindBy(accessibility = "Photo Frames")
	public IOSElement homeHomeDecorPhotoFrames;

	@iOSFindBy(accessibility = "Showpieces")
	public IOSElement homeHomeDecorShowpieces;

	@iOSFindBy(accessibility = "Wall shelves")
	public IOSElement homeHomeDecorWallshelves;

	@iOSFindBy(accessibility = "Wall Decals & Stickers")
	public IOSElement homeHomeDecorWallDecalsAndStickers;

	@iOSFindBy(accessibility = "Wall Art")
	public IOSElement homeHomeDecorWallArt;

	@iOSFindBy(accessibility = "Lamps & Lighting ")
	public IOSElement homelampsAndLightning;

	/*
	 * different Lamps & Lighting
	 */

	@iOSFindBy(accessibility = "Wall & Pendant Lamps")
	public IOSElement homeLampsAndLightingWallAndPendantLamps;

	@iOSFindBy(accessibility = "Table Lamps")
	public IOSElement homeLampsAndLightingTableLamps;

	@iOSFindBy(accessibility = "Floor Lamps")
	public IOSElement homeLampsAndLightingFloorLamps;

	@iOSFindBy(accessibility = "Floor Mats & Dhurries ")
	public IOSElement homeFloorMatsAndDhurries;

	/*
	 * different Floor Mats & Dhurries
	 */

	@iOSFindBy(accessibility = "Floor Mats and Dhurries")
	public IOSElement homeFloorFloorMatsAndDhurries;

	@iOSFindBy(accessibility = "Door Mats")
	public IOSElement homeFloorDoorMats;

	@iOSFindBy(accessibility = "Bath ")
	public IOSElement homeBath;

	/*
	 * different Bath
	 */

	@iOSFindBy(accessibility = "Bath Towels")
	public IOSElement homeBathBathTowels;

	@iOSFindBy(accessibility = "Hand & Face Towels")
	public IOSElement homeBathHandAndFaceTowels;

	@iOSFindBy(accessibility = "Beach Towels")
	public IOSElement homeBathBeachTowels;

	@iOSFindBy(accessibility = "Towels Set")
	public IOSElement homeBathTowelsSet;

	@iOSFindBy(accessibility = "Bath Rugs")
	public IOSElement homeBathBathRugs;

	@iOSFindBy(accessibility = "Bath Robes")
	public IOSElement homeBathBathRobes;

	@iOSFindBy(accessibility = "Bathroom Accessories")
	public IOSElement homeBathBathroomAccessories;

	@iOSFindBy(accessibility = "Kitchen & Table ")
	public IOSElement homeKitchenAndTable;

	/*
	 * different Kitchen & Table
	 */

	@iOSFindBy(accessibility = "Tableware")
	public IOSElement homeKitchenTableware;

	@iOSFindBy(accessibility = "Table & Kitchen Linen")
	public IOSElement homeKitchenTableAndKitchenLinen;

	@iOSFindBy(accessibility = "Trays")
	public IOSElement homeKitchenTrays;

	@iOSFindBy(accessibility = "Kitchen Storage")
	public IOSElement homeKitchenKitchenStorage;

	@iOSFindBy(accessibility = "Coasters")
	public IOSElement homeKitchenCoasters;

	@iOSFindBy(accessibility = "Kitchen Tools")
	public IOSElement homeKitchenKitchenTools;

	@iOSFindBy(accessibility = "Storage ")
	public IOSElement homeStorage;

	/*
	 * different Storage
	 */

	@iOSFindBy(accessibility = "Organisers")
	public IOSElement homeStorageOrganisers;

	@iOSFindBy(accessibility = "Hooks & Holders")
	public IOSElement homeStorageHooksAndHolders;

	/*
	 * getters method for above one
	 */
	public IOSElement getHomeAndLiving() {
		objiOSGenericMethods.CheckIOSElementFound(homeAndLiving, "homeAndLiving");
		return homeAndLiving;
	}

	public IOSElement getHomeBath() {
		objiOSGenericMethods.CheckIOSElementFound(homeBath, "homeBath");
		return homeBath;
	}

	public IOSElement getHomeExploreHomeStore() {
		objiOSGenericMethods.CheckIOSElementFound(homeExploreHomeStore, "homeExploreHomeStore");
		return homeExploreHomeStore;
	}

	public IOSElement getHomeGiftingIdeas() {
		objiOSGenericMethods.CheckIOSElementFound(homeGiftingIdeas, "homeGiftingIdeas");
		return homeGiftingIdeas;
	}

	public IOSElement getHomeBrands() {
		objiOSGenericMethods.CheckIOSElementFound(homeBrands, "homeBrands");
		return homeBrands;
	}

	public IOSElement getHomeBrandRaymondHome() {
		objiOSGenericMethods.CheckIOSElementFound(homeBrandRaymondHome, "homeBrandRaymondHome");
		return homeBrandRaymondHome;
	}

	public IOSElement getHomeBrandSpaces() {
		objiOSGenericMethods.CheckIOSElementFound(homeBrandSpaces, "homeBrandSpaces");
		return homeBrandSpaces;
	}

	public IOSElement getHomeBrandCorelle() {
		objiOSGenericMethods.CheckIOSElementFound(homeBrandCorelle, "homeBrandCorelle");
		return homeBrandCorelle;
	}

	public IOSElement getHomeBrandSparkle() {
		objiOSGenericMethods.CheckIOSElementFound(homeBrandSparkle, "homeBrandSparkle");
		return homeBrandSparkle;
	}

	public IOSElement getHomeBrandRandom() {
		objiOSGenericMethods.CheckIOSElementFound(homeBrandRandom, "homeBrandRandom");
		return homeBrandRandom;
	}

	public IOSElement getHomeBrandPortico() {
		objiOSGenericMethods.CheckIOSElementFound(homeBrandPortico, "homeBrandPortico");
		return homeBrandPortico;
	}

	public IOSElement getHomeBrandSwayam() {
		objiOSGenericMethods.CheckIOSElementFound(homeBrandSwayam, "homeBrandSwayam");
		return homeBrandSwayam;
	}

	public IOSElement getHomeBrandSejbyNishaGupta() {
		objiOSGenericMethods.CheckIOSElementFound(homeBrandSejbyNishaGupta, "homeBrandSejbyNishaGupta");
		return homeBrandSejbyNishaGupta;
	}

	public IOSElement getHomeBrandTangerine() {
		objiOSGenericMethods.CheckIOSElementFound(homeBrandTangerine, "homeBrandTangerine");
		return homeBrandTangerine;
	}

	public IOSElement getHomeBrandTrident() {
		objiOSGenericMethods.CheckIOSElementFound(homeBrandTrident, "homeBrandTrident");
		return homeBrandTrident;
	}

	public IOSElement getHomeBedLinenAndFurnishing() {
		objiOSGenericMethods.CheckIOSElementFound(homeBedLinenAndFurnishing, "homeBedLinenAndFurnishing");
		return homeBedLinenAndFurnishing;
	}

	public IOSElement getHomeBedLinenBedsheets() {
		objiOSGenericMethods.CheckIOSElementFound(homeBedLinenBedsheets, "homeBedLinenBedsheets");
		return homeBedLinenBedsheets;
	}

	public IOSElement getHomeBedLinenBedCovers() {
		objiOSGenericMethods.CheckIOSElementFound(homeBedLinenBedCovers, "homeBedLinenBedCovers");
		return homeBedLinenBedCovers;
	}

	public IOSElement getHomeBedLinenBeddingSets() {
		objiOSGenericMethods.CheckIOSElementFound(homeBedLinenBeddingSets, "homeBedLinenBeddingSets");
		return homeBedLinenBeddingSets;
	}

	public IOSElement getHomeBedLinenBlanketsQuiltsAndDohars() {
		objiOSGenericMethods.CheckIOSElementFound(homeBedLinenBlanketsQuiltsAndDohars, "homeBedLinenBlanketsQuiltsAndDohars");
		return homeBedLinenBlanketsQuiltsAndDohars;
	}

	public IOSElement getHomeBedLinenPillowsAndCovers() {
		objiOSGenericMethods.CheckIOSElementFound(homeBedLinenPillowsAndCovers, "homeBedLinenPillowsAndCovers");
		return homeBedLinenPillowsAndCovers;
	}

	public IOSElement getHomecurtains() {
		objiOSGenericMethods.CheckIOSElementFound(homecurtains, "homecurtains");
		return homecurtains;
	}

	public IOSElement getHomeCushionsAndCushionCovers() {
		objiOSGenericMethods.CheckIOSElementFound(homeCushionsAndCushionCovers, "homeCushionsAndCushionCovers");
		return homeCushionsAndCushionCovers;
	}

	public IOSElement getHomeHomeDecor() {
		objiOSGenericMethods.CheckIOSElementFound(homeHomeDecor, "homeHomeDecor");
		return homeHomeDecor;
	}

	public IOSElement getHomeHomeDecorArtificialFlowersAndPlants() {
		objiOSGenericMethods.CheckIOSElementFound(homeHomeDecorArtificialFlowersAndPlants, "homeHomeDecorArtificialFlowersAndPlants");
		return homeHomeDecorArtificialFlowersAndPlants;
	}

	public IOSElement getHomeHomeDecorCandlesAndHolders() {
		objiOSGenericMethods.CheckIOSElementFound(homeHomeDecorCandlesAndHolders, "homeHomeDecorCandlesAndHolders");
		return homeHomeDecorCandlesAndHolders;
	}

	public IOSElement getHomeHomeDecorClocks() {
		objiOSGenericMethods.CheckIOSElementFound(homeHomeDecorClocks, "homeHomeDecorClocks");
		return homeHomeDecorClocks;
	}

	public IOSElement getHomeHomeDecorFountains() {
		objiOSGenericMethods.CheckIOSElementFound(homeHomeDecorFountains, "homeHomeDecorFountains");
		return homeHomeDecorFountains;
	}

	public IOSElement getHomeHomeDecorPhotoFrames() {
		objiOSGenericMethods.CheckIOSElementFound(homeHomeDecorPhotoFrames, "homeHomeDecorPhotoFrames");
		return homeHomeDecorPhotoFrames;
	}

	public IOSElement getHomeHomeDecorShowpieces() {
		objiOSGenericMethods.CheckIOSElementFound(homeHomeDecorShowpieces, "homeHomeDecorShowpieces");
		return homeHomeDecorShowpieces;
	}

	public IOSElement getHomeHomeDecorWallshelves() {
		objiOSGenericMethods.CheckIOSElementFound(homeHomeDecorWallshelves, "homeHomeDecorWallshelves");
		return homeHomeDecorWallshelves;
	}

	public IOSElement getHomeHomeDecorWallDecalsAndStickers() {
		objiOSGenericMethods.CheckIOSElementFound(homeHomeDecorWallDecalsAndStickers, "homeHomeDecorWallDecalsAndStickers");
		return homeHomeDecorWallDecalsAndStickers;
	}

	public IOSElement getHomeHomeDecorWallArt() {
		objiOSGenericMethods.CheckIOSElementFound(homeHomeDecorWallArt, "homeHomeDecorWallArt");
		return homeHomeDecorWallArt;
	}

	public IOSElement getHomelampsAndLightning() {
		objiOSGenericMethods.CheckIOSElementFound(homelampsAndLightning, "homelampsAndLightning");
		return homelampsAndLightning;
	}

	public IOSElement getHomeLampsAndLightingWallAndPendantLamps() {
		objiOSGenericMethods.CheckIOSElementFound(homeLampsAndLightingWallAndPendantLamps, "homeLampsAndLightingWallAndPendantLamps");
		return homeLampsAndLightingWallAndPendantLamps;
	}

	public IOSElement getHomeLampsAndLightingTableLamps() {
		objiOSGenericMethods.CheckIOSElementFound(homeLampsAndLightingTableLamps, "homeLampsAndLightingTableLamps");
		return homeLampsAndLightingTableLamps;
	}

	public IOSElement getHomeLampsAndLightingFloorLamps() {
		objiOSGenericMethods.CheckIOSElementFound(homeLampsAndLightingFloorLamps, "homeLampsAndLightingFloorLamps");
		return homeLampsAndLightingFloorLamps;
	}

	public IOSElement getHomeFloorMatsAndDhurries() {
		objiOSGenericMethods.CheckIOSElementFound(homeFloorMatsAndDhurries, "homeFloorMatsAndDhurries");
		return homeFloorMatsAndDhurries;
	}
	public IOSElement getHomeFloorFloorMatsAndDhurries() {
		objiOSGenericMethods.CheckIOSElementFound(homeFloorFloorMatsAndDhurries, "HomeFloorFloorMatsAndDhurries");
		return homeFloorFloorMatsAndDhurries;
	}
	

	public IOSElement getHomeFloorDoorMats() {
		objiOSGenericMethods.CheckIOSElementFound(homeFloorDoorMats, "homeFloorDoorMats");
		return homeFloorDoorMats;
	}

	public IOSElement getHomeBathBathTowels() {
		objiOSGenericMethods.CheckIOSElementFound(homeBathBathTowels, "homeBathBathTowels");
		return homeBathBathTowels;
	}

	public IOSElement getHomeBathHandAndFaceTowels() {
		objiOSGenericMethods.CheckIOSElementFound(homeBathHandAndFaceTowels, "homeBathHandAndFaceTowels");
		return homeBathHandAndFaceTowels;
	}

	public IOSElement getHomeBathBeachTowels() {
		objiOSGenericMethods.CheckIOSElementFound(homeBathBeachTowels, "homeBathBeachTowels");
		return homeBathBeachTowels;
	}

	public IOSElement getHomeBathTowelsSet() {
		objiOSGenericMethods.CheckIOSElementFound(homeBathTowelsSet, "homeBathTowelsSet");
		return homeBathTowelsSet;
	}

	public IOSElement getHomeBathBathRugs() {
		objiOSGenericMethods.CheckIOSElementFound(homeBathBathRugs, "homeBathBathRugs");
		return homeBathBathRugs;
	}

	public IOSElement getHomeBathBathRobes() {
		objiOSGenericMethods.CheckIOSElementFound(homeBathBathRobes, "homeBathBathRobes");
		return homeBathBathRobes;
	}

	public IOSElement getHomeBathBathroomAccessories() {
		objiOSGenericMethods.CheckIOSElementFound(homeBathBathroomAccessories, "homeBathBathroomAccessories");
		return homeBathBathroomAccessories;
	}

	public IOSElement getHomeKitchenAndTable() {
		objiOSGenericMethods.CheckIOSElementFound(homeKitchenAndTable, "homeKitchenAndTable");
		return homeKitchenAndTable;
	}

	public IOSElement getHomeKitchenTableware() {
		objiOSGenericMethods.CheckIOSElementFound(homeKitchenTableware, "homeKitchenTableware");
		return homeKitchenTableware;
	}

	public IOSElement getHomeKitchenTableAndKitchenLinen() {
		objiOSGenericMethods.CheckIOSElementFound(homeKitchenTableAndKitchenLinen, "homeKitchenTableAndKitchenLinen");
		return homeKitchenTableAndKitchenLinen;
	}

	public IOSElement getHomeKitchenTrays() {
		objiOSGenericMethods.CheckIOSElementFound(homeKitchenTrays, "homeKitchenTrays");
		return homeKitchenTrays;
	}

	public IOSElement getHomeKitchenKitchenStorage() {
		objiOSGenericMethods.CheckIOSElementFound(homeKitchenKitchenStorage, "homeKitchenKitchenStorage");
		return homeKitchenKitchenStorage;
	}

	public IOSElement getHomeKitchenCoasters() {
		objiOSGenericMethods.CheckIOSElementFound(homeKitchenCoasters, "homeKitchenCoasters");
		return homeKitchenCoasters;
	}

	public IOSElement getHomeKitchenKitchenTools() {
		objiOSGenericMethods.CheckIOSElementFound(homeKitchenKitchenTools, "homeKitchenKitchenTools");
		return homeKitchenKitchenTools;
	}

	public IOSElement getHomeStorage() {
		objiOSGenericMethods.CheckIOSElementFound(homeStorage, "homeStorage");
		return homeStorage;
	}

	public IOSElement getHomeStorageOrganisers() {
		objiOSGenericMethods.CheckIOSElementFound(homeStorageOrganisers, "homeStorageOrganisers");
		return homeStorageOrganisers;
	}

	public IOSElement getHomeStorageHooksAndHolders() {
		objiOSGenericMethods.CheckIOSElementFound(homeStorageHooksAndHolders, "homeStorageHooksAndHolders");
		return homeStorageHooksAndHolders;
	}

	/*
	 * Home And Living categories methods
	 */

	public void clickOnHomeAndLiving() {
		objiOSGenericMethods.clickOnIOSElement(getHomeAndLiving(), "Successfully click on HomeAndLiving button");
	}
	public void clickOnHomeExploreHomeStore() {
		objiOSGenericMethods.clickOnIOSElement(getHomeExploreHomeStore(), "Successfully click on permission button");
	}

	public void clickOnHomeGiftingIdeas() {
		objiOSGenericMethods.clickOnIOSElement(getHomeGiftingIdeas(), "Successfully click on Home gifting ideas button");
	}

	/*
	 * Brand and its different brand methods
	 */
	public void clickOnHomeBrands() {
		objiOSGenericMethods.clickOnIOSElement(getHomeBrands(), "Successfully click on homeBrands button");
	}

	public void clickOnHomeBrandRaymondHome() {
		objiOSGenericMethods.clickOnIOSElement(getHomeBrandRaymondHome(), "Successfully click on homeBrandRaymondHome button");
	}

	public void clickOnHomeBrandSpaces() {
		objiOSGenericMethods.clickOnIOSElement(getHomeBrandSpaces(), "Successfully click on homeBrandSpaces button");
	}

	public void clickOnHomeBrandCorelle() {
		objiOSGenericMethods.clickOnIOSElement(getHomeBrandCorelle(), "Successfully click on homeBrandCorelle button");
	}

	public void clickOnHomeBrandSparkle() {
		objiOSGenericMethods.clickOnIOSElement(getHomeBrandSparkle(), "Successfully click on homeBrandSparkle button");
	}

	public void clickOnHomeBrandRandom() {
		objiOSGenericMethods.clickOnIOSElement(getHomeBrandRandom(), "Successfully click on homeBrandRandom button");
	}

	public void clickOnHomeBrandPortico() {
		objiOSGenericMethods.clickOnIOSElement(getHomeBrandPortico(), "Successfully click on homeBrandPortico button");
	}

	public void clickOnHomeBrandSwayam() {
		objiOSGenericMethods.clickOnIOSElement(getHomeBrandSwayam(), "Successfully click on homeBrandSwayam button");
	}

	public void clickOnHomeBrandSejbyNishaGupta() {
		objiOSGenericMethods.clickOnIOSElement(getHomeBrandSejbyNishaGupta(), "Successfully click on homeBrandSejbyNishaGupta button");
	}

	public void clickOnHomeBrandTangerine() {
		objiOSGenericMethods.clickOnIOSElement(getHomeBrandTangerine(), "Successfully click on homeBrandTangerine button");
	}

	public void clickOnHomeBrandTrident() {
		objiOSGenericMethods.clickOnIOSElement(getHomeBrandTrident(), "Successfully click on homeBrandTrident button");
	}

	public void clickOnHomeBedLinenAndFurnishing() {
		objiOSGenericMethods.clickOnIOSElement(getHomeBedLinenAndFurnishing(), "Successfully click on homeBedLinenAndFurnishing button");
	}

	/*
	 * Bed and different Bed Linen & Furnishing methods
	 */

	public void clickOnHomeBedLinenBedsheets() {
		objiOSGenericMethods.clickOnIOSElement(getHomeBedLinenBedsheets(), "Successfully click on homeBedLinenBedsheets button");
	}

	public void clickOnHomeBedLinenBedCovers() {
		objiOSGenericMethods.clickOnIOSElement(getHomeBedLinenBedCovers(), "Successfully click on homeBedLinenBedCovers button");
	}

	public void clickOnHomeBedLinenBeddingSets() {
		objiOSGenericMethods.clickOnIOSElement(getHomeBedLinenBeddingSets(), "Successfully click on homeBedLinenBeddingSets button");
	}

	public void clickOnHomeBedLinenBlanketsQuiltsAndDohars() {
		objiOSGenericMethods.clickOnIOSElement(getHomeBedLinenBlanketsQuiltsAndDohars(), "Successfully click on homeBedLinenBlanketsQuiltsAndDohars button");
	}

	public void clickOnHomeBedLinenPillowsAndCovers() {
		objiOSGenericMethods.clickOnIOSElement(getHomeBedLinenPillowsAndCovers(), "Successfully click on homeBedLinenPillowsAndCovers button");
	}

	public void clickOnHomecurtains() {
		objiOSGenericMethods.clickOnIOSElement(getHomecurtains(), "Successfully click on homecurtains button");
	}

	public void clickOnHomeCushionsAndCushionCovers() {
		objiOSGenericMethods.clickOnIOSElement(getHomeCushionsAndCushionCovers(), "Successfully click on homeCushionsAndCushionCovers button");
	}
	/*
	 * decor and different home decor methods
	 */

	public void clickOnHomeHomeDecor() {
		objiOSGenericMethods.clickOnIOSElement(getHomeHomeDecor(), "Successfully click on homeHomeDecor button");
	}

	public void clickOnHomeHomeDecorArtificialFlowersAndPlants() {
		objiOSGenericMethods.clickOnIOSElement(getHomeHomeDecorArtificialFlowersAndPlants(), "Successfully click on homeHomeDecorArtificialFlowersAndPlants button");
	}

	public void clickOnHomeHomeDecorCandlesAndHolders() {
		objiOSGenericMethods.clickOnIOSElement(getHomeHomeDecorCandlesAndHolders(), "Successfully click on homeHomeDecorCandlesAndHolders button");
	}

	public void clickOnHomeHomeDecorClocks() {
		objiOSGenericMethods.clickOnIOSElement(getHomeHomeDecorClocks(), "Successfully click on homeHomeDecorClocks button");
	}

	public void clickOnHomeHomeDecorFountains() {
		objiOSGenericMethods.clickOnIOSElement(getHomeHomeDecorFountains(), "Successfully click on homeHomeDecorFountains button");
	}

	public void clickOnHomeHomeDecorPhotoFrames() {
		objiOSGenericMethods.clickOnIOSElement(getHomeHomeDecorPhotoFrames(), "Successfully click on homeHomeDecorPhotoFrames button");
	}

	public void clickOnHomeHomeDecorShowpieces() {
		objiOSGenericMethods.clickOnIOSElement(getHomeHomeDecorShowpieces(), "Successfully click on homeHomeDecorShowpieces button");
	}

	public void clickOnHomeHomeDecorWallshelves() {
		objiOSGenericMethods.clickOnIOSElement(getHomeHomeDecorWallshelves(), "Successfully click on homeHomeDecorWallshelves button");
	}

	public void clickOnHomeHomeDecorWallDecalsAndStickers() {
		objiOSGenericMethods.clickOnIOSElement(getHomeHomeDecorWallDecalsAndStickers(), "Successfully click on homeHomeDecorWallDecalsAndStickers button");
	}

	public void clickOnHomeHomeDecorWallArt() {
		objiOSGenericMethods.clickOnIOSElement(getHomeHomeDecorWallArt(), "Successfully click on homeHomeDecorWallArt button");
	}
	/*
	 * different Lamps & Lighting
	 */

	public void clickOnHomelampsAndLightning() {
		objiOSGenericMethods.clickOnIOSElement(getHomelampsAndLightning(), "Successfully click on homelampsAndLightning button");
	}

	public void clickOnHomeLampsAndLightingWallAndPendantLamps() {
		objiOSGenericMethods.clickOnIOSElement(getHomeLampsAndLightingWallAndPendantLamps(), "Successfully click on homeLampsAndLightingWallAndPendantLamps button");
	}

	public void clickOnHomeLampsAndLightingTableLamps() {
		objiOSGenericMethods.clickOnIOSElement(getHomeLampsAndLightingTableLamps(), "Successfully click on homeLampsAndLightingTableLamps button");
	}

	public void clickOnHomeLampsAndLightingFloorLamps() {
		objiOSGenericMethods.clickOnIOSElement(getHomeLampsAndLightingFloorLamps(), "Successfully click on homeLampsAndLightingFloorLamps button");
	}
	/*
	 * Floor Mates and different Floor Mats & Dhurries
	 */

	public void clickOnHomeFloorMatsAndDhurries() {
		objiOSGenericMethods.clickOnIOSElement(getHomeFloorMatsAndDhurries(), "Successfully click on homeFloorMatsAndDhurries button");
	}

	public void clickOnHomeFloorFloorMatsAndDhurries() {
		objiOSGenericMethods.clickOnIOSElement(getHomeFloorFloorMatsAndDhurries(), "Successfully click on homeHomeDecor button");
	}

	public void clickOnHomeFloorDoorMats() {
		objiOSGenericMethods.clickOnIOSElement(getHomeFloorDoorMats(), "Successfully click on homeFloorDoorMats button");
	}

	/*
	 * Bath different Bath methods
	 */

	public void clickOnHomeBath() {
		objiOSGenericMethods.clickOnIOSElement(getHomeBath(), "Successfully click on homeBath button");
	}

	public void clickOnHomeBathBathTowels() {
		objiOSGenericMethods.clickOnIOSElement(getHomeBathBathTowels(), "Successfully click on homeBathBathTowels button");
	}

	public void clickOnHomeBathHandAndFaceTowels() {
		objiOSGenericMethods.clickOnIOSElement(getHomeBathHandAndFaceTowels(), "Successfully click on homeBathHandAndFaceTowels button");
	}

	public void clickOnHomeBathBeachTowels() {
		objiOSGenericMethods.clickOnIOSElement(getHomeBathBeachTowels(), "Successfully click on homeBathBeachTowels button");
	}

	public void clickOnHomeBathTowelsSet() {
		objiOSGenericMethods.clickOnIOSElement(getHomeBathTowelsSet(), "Successfully click on homeBathTowelsSet button");
	}

	public void clickOnHomeBathBathRugs() {
		objiOSGenericMethods.clickOnIOSElement(getHomeBathBathRugs(), "Successfully click on homeBathBathRugs button");
	}

	public void clickOnHomeBathBathRobes() {
		objiOSGenericMethods.clickOnIOSElement(getHomeBathBathRobes(), "Successfully click on homeBathBathRobes button");
	}

	public void clickOnHomeBathBathroomAccessories() {
		objiOSGenericMethods.clickOnIOSElement(getHomeBathBathroomAccessories(), "Successfully click on homeBathBathroomAccessories button");
	}
	/*
	 * Kitchen different Kitchen & Table methods
	 */

	public void clickOnHomeKitchenAndTable() {
		objiOSGenericMethods.clickOnIOSElement(getHomeKitchenAndTable(), "Successfully click on homeKitchenAndTable button");
	}

	public void clickOnHomeKitchenTableware() {
		objiOSGenericMethods.clickOnIOSElement(getHomeKitchenTableware(), "Successfully click on homeKitchenTableware button");
	}

	public void clickOnHomeKitchenTableAndKitchenLinen() {
		objiOSGenericMethods.clickOnIOSElement(getHomeKitchenTableAndKitchenLinen(), "Successfully click on homeKitchenTableAndKitchenLinen button");
	}

	public void clickOnHomeKitchenTrays() {
		objiOSGenericMethods.clickOnIOSElement(getHomeKitchenTrays(), "Successfully click on homeKitchenTrays button");
	}

	public void clickOnHomeKitchenKitchenStorage() {
		objiOSGenericMethods.clickOnIOSElement(getHomeKitchenKitchenStorage(), "Successfully click on homeKitchenKitchenStorage button");
	}

	public void clickOnHomeKitchenCoasters() {
		objiOSGenericMethods.clickOnIOSElement(getHomeKitchenCoasters(), "Successfully click on homeKitchenCoasters button");
	}

	public void clickOnHomeKitchenKitchenTools() {
		objiOSGenericMethods.clickOnIOSElement(getHomeKitchenKitchenTools(), "Successfully click on homeKitchenKitchenTools button");
	}
	/*
	 * Storage and its different methods
	 */

	public void clickOnHomeStorage() {
		objiOSGenericMethods.clickOnIOSElement(getHomeStorage(), "Successfully click on homeStorage button");
	}

	public void clickOnHomeStorageOrganisers() {
		objiOSGenericMethods.clickOnIOSElement(getHomeStorageOrganisers(), "Successfully click on homeStorageOrganisers button");
	}

	public void clickOnHomeStorageHooksAndHolders() {
		objiOSGenericMethods.clickOnIOSElement(getHomeStorageHooksAndHolders(), "Successfully click on homeStorageHooksAndHolders button");
	}
}
