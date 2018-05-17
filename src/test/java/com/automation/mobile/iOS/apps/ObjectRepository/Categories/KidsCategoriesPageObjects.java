package com.automation.mobile.iOS.apps.ObjectRepository.Categories;


import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;

public class KidsCategoriesPageObjects {
	public AppiumDriver<MobileElement> iDriver;
	public KidsCategoriesPageObjects(AppiumDriver<MobileElement> iDriver) {
		PageFactory.initElements(new AppiumFieldDecorator(iDriver), this);
	}
	
	@iOSFindBy(accessibility="Kids Brands, Clothing, Footwear, Accessories Brands ï�¸ Boys Clothing ï�¸ Girls Clothing ï�¸ Boys Footwear ï�¸ Girls Footwear ï�¸ Kids Accessories ï�¸ Toys")
	public IOSElement kids;
	//Kids Brands, Clothing, Footwear, Accessories

	public IOSElement getKids() {
		return kids;
	}
	public void clickOnKids() {
		getKids().click();
	}

	/**
	 * 
	 * Kids Categories
	 * 
	 */
	@iOSFindBy(accessibility = "Brands ï�¸")
	public IOSElement kidsBrands;
	
	@iOSFindBy(accessibility = "Mothercare")
	public IOSElement kidsBrandMothercare;
	
	@iOSFindBy(accessibility = "Gini and Jony")
	public IOSElement kidsBrandGiniandJony;
	
	@iOSFindBy(accessibility = "U.S. Polo Assn. Kids")
	public IOSElement kidsBrandUSPoloAssn;
	
	@iOSFindBy(accessibility = "Lilliput")
	public IOSElement kidsBrandLilliput;
	
	@iOSFindBy(accessibility = "United Colors of Benetton")
	public IOSElement kidsBrandUnitedColorsofBenetton;
	
	@iOSFindBy(accessibility = "YK")
	public IOSElement kidsBrandYK;
	
	@iOSFindBy(accessibility = "Allen Solly Junior")
	public IOSElement kidsBrandAllenSollyJunior;
	
	@iOSFindBy(accessibility = "Mango Kids")
	public IOSElement kidsBrandMangoKids;
	
	@iOSFindBy(accessibility = "Marks & Spencer")
	public IOSElement kidsBrandMarksSpencer;
	
	@iOSFindBy(accessibility = "Tommy Hilfiger")
	public IOSElement kidsBrandTommyHilfiger;
	
	@iOSFindBy(accessibility = "612 League")
	public IOSElement kidsBrand612League;
	
	@iOSFindBy(accessibility = "Boys Clothing ï�¸")
	public IOSElement kidsBoysClothing;
	
	@iOSFindBy(accessibility = "T-Shirts")
	public IOSElement kidsBoysClothingTShirts;
	
	@iOSFindBy(accessibility = "Shirts")
	public IOSElement kidsBoysClothingShirts;
	
	@iOSFindBy(accessibility = "Jeans & Trousers")
	public IOSElement kidsBoysClothingJeansTrousers;
	
	@iOSFindBy(accessibility = "Shorts & Dungarees")
	public IOSElement kidsBoysClothingShortsDungarees;
	
	@iOSFindBy(accessibility = "Track Pants & Pyjamas")
	public IOSElement kidsBoysClothingTrackPantsPyjamas;
	
	@iOSFindBy(accessibility = "Clothing Sets")
	public IOSElement kidsBoysClothingClothingSets;
	
	@iOSFindBy(accessibility = "Indian Wear")
	public IOSElement kidsBoysClothingIndianWear;
	
	@iOSFindBy(accessibility = "Sweaters, Sweatshirts & Jackets")
	public IOSElement kidsBoysClothingSweatersSweatshirtsJackets;
	
	@iOSFindBy(accessibility = "Inner & Sleep Wear")
	public IOSElement kidsBoysClothingInnerSleepWear;
	
	@iOSFindBy(accessibility = "Girls Clothing ï�¸")
	public IOSElement kidsGirlsClothing;
	
	@iOSFindBy(accessibility = "Dresses")
	public IOSElement kidsGirlsClothingDresses;
	
	@iOSFindBy(accessibility = "Tops & T-shirts")
	public IOSElement kidsGirlsClothingTopsTshirts;
	
	@iOSFindBy(accessibility = "Clothing Sets")
	public IOSElement kidsGirlsClothingClothingSets;
	
	@iOSFindBy(accessibility = "Indian Wear")
	public IOSElement kidsGirlsClothingIndianWear;
	
	@iOSFindBy(accessibility = "Skirts, Shorts & Jumpsuits")
	public IOSElement kidsGirlsClothingSkirtsShortsJumpsuits;
	
	@iOSFindBy(accessibility = "Tights & Leggings")
	public IOSElement kidsGirlsClothingTightsLeggings;
	
	@iOSFindBy(accessibility = "Jeans, Trousers & Capris")
	public IOSElement kidsGirlsClothingJeansTrousersCapris;
	
	@iOSFindBy(accessibility = "Track Pants")
	public IOSElement kidsGirlsClothingTrackPants;
	
	@iOSFindBy(accessibility = "Sweaters, Sweatshirts & Jackets")
	public IOSElement kidsGirlsClothingSweatersSweatshirtsJackets;
	
	@iOSFindBy(accessibility = "Inner & Sleep Wear")
	public IOSElement kidsGirlsClothingInnerSleepWear;
	
	@iOSFindBy(accessibility = "Boys Footwear ï�¸")
	public IOSElement kidsBoysFootwear;
	
	@iOSFindBy(accessibility = "Casual Shoes")
	public IOSElement kidsBoysFootwearCasualShoes;
	
	@iOSFindBy(accessibility = "Sports Shoes")
	public IOSElement kidsBoysFootwearSportsShoes;
	
	@iOSFindBy(accessibility = "Sandals & Flip flops")
	public IOSElement kidsBoysFootwearSandalsFlipflops;
	
	@iOSFindBy(accessibility = "Girls Footwear ï�¸")
	public IOSElement kidsGirlsFootwear;
	
	@iOSFindBy(accessibility = "Flats & Casual Shoes")
	public IOSElement kidsGirlsFootwearFlatsCasualShoes;
	
	@iOSFindBy(accessibility = "Heels")
	public IOSElement kidsGirlsFootwearHeels;
	
	@iOSFindBy(accessibility = "Sports Shoes")
	public IOSElement kidsGirlsFootwearSportsShoes;
	
	@iOSFindBy(accessibility = "Sandals & Flip flops")
	public IOSElement kidsGirlsFootwearSandalsFlipflops;
	
	@iOSFindBy(accessibility = "Kids Accessories ï�¸")
	public IOSElement KidsAccessories;
	
	@iOSFindBy(accessibility = "Bags & Backpacks")
	public IOSElement KidsAccessoriesBagsBackpacks;
	
	@iOSFindBy(accessibility = "Watches")
	public IOSElement KidsAccessoriesWatches;
	
	@iOSFindBy(accessibility = "Jewellery & Hair Accessories")
	public IOSElement KidsAccessoriesJewelleryHairAccessories;
	
	@iOSFindBy(accessibility = "Sunglasses & Frames")
	public IOSElement KidsAccessoriesSunglassesFrames;
	
	@iOSFindBy(accessibility = "Toys")
	public IOSElement KidsToys;

	/***************getters****************/
	
	public IOSElement getKidsBrands() {
		return kidsBrands;
	}

	public IOSElement getKidsBrandMothercare() {
		return kidsBrandMothercare;
	}

	public IOSElement getKidsBrandGiniandJony() {
		return kidsBrandGiniandJony;
	}

	public IOSElement getKidsBrandUSPoloAssn() {
		return kidsBrandUSPoloAssn;
	}

	public IOSElement getKidsBrandLilliput() {
		return kidsBrandLilliput;
	}

	public IOSElement getKidsBrandUnitedColorsofBenetton() {
		return kidsBrandUnitedColorsofBenetton;
	}

	public IOSElement getKidsBrandYK() {
		return kidsBrandYK;
	}

	public IOSElement getKidsBrandAllenSollyJunior() {
		return kidsBrandAllenSollyJunior;
	}

	public IOSElement getKidsBrandMangoKids() {
		return kidsBrandMangoKids;
	}

	public IOSElement getKidsBrandMarksSpencer() {
		return kidsBrandMarksSpencer;
	}

	public IOSElement getKidsBrandTommyHilfiger() {
		return kidsBrandTommyHilfiger;
	}

	public IOSElement getKidsBrand612League() {
		return kidsBrand612League;
	}

	public IOSElement getKidsBoysClothing() {
		return kidsBoysClothing;
	}

	public IOSElement getKidsBoysClothingTShirts() {
		return kidsBoysClothingTShirts;
	}

	public IOSElement getKidsBoysClothingShirts() {
		return kidsBoysClothingShirts;
	}

	public IOSElement getKidsBoysClothingJeansTrousers() {
		return kidsBoysClothingJeansTrousers;
	}

	public IOSElement getKidsBoysClothingShortsDungarees() {
		return kidsBoysClothingShortsDungarees;
	}

	public IOSElement getKidsBoysClothingTrackPantsPyjamas() {
		return kidsBoysClothingTrackPantsPyjamas;
	}

	public IOSElement getKidsBoysClothingClothingSets() {
		return kidsBoysClothingClothingSets;
	}

	public IOSElement getKidsBoysClothingIndianWear() {
		return kidsBoysClothingIndianWear;
	}

	public IOSElement getKidsBoysClothingSweatersSweatshirtsJackets() {
		return kidsBoysClothingSweatersSweatshirtsJackets;
	}

	public IOSElement getKidsBoysClothingInnerSleepWear() {
		return kidsBoysClothingInnerSleepWear;
	}

	public IOSElement getKidsGirlsClothing() {
		return kidsGirlsClothing;
	}

	public IOSElement getKidsGirlsClothingDresses() {
		return kidsGirlsClothingDresses;
	}

	public IOSElement getKidsGirlsClothingTopsTshirts() {
		return kidsGirlsClothingTopsTshirts;
	}

	public IOSElement getKidsGirlsClothingClothingSets() {
		return kidsGirlsClothingClothingSets;
	}

	public IOSElement getKidsGirlsClothingIndianWear() {
		return kidsGirlsClothingIndianWear;
	}

	public IOSElement getKidsGirlsClothingSkirtsShortsJumpsuits() {
		return kidsGirlsClothingSkirtsShortsJumpsuits;
	}

	public IOSElement getKidsGirlsClothingTightsLeggings() {
		return kidsGirlsClothingTightsLeggings;
	}

	public IOSElement getKidsGirlsClothingJeansTrousersCapris() {
		return kidsGirlsClothingJeansTrousersCapris;
	}

	public IOSElement getKidsGirlsClothingTrackPants() {
		return kidsGirlsClothingTrackPants;
	}

	public IOSElement getKidsGirlsClothingSweatersSweatshirtsJackets() {
		return kidsGirlsClothingSweatersSweatshirtsJackets;
	}

	public IOSElement getKidsGirlsClothingInnerSleepWear() {
		return kidsGirlsClothingInnerSleepWear;
	}

	public IOSElement getKidsBoysFootwear() {
		return kidsBoysFootwear;
	}

	public IOSElement getKidsBoysFootwearCasualShoes() {
		return kidsBoysFootwearCasualShoes;
	}

	public IOSElement getKidsBoysFootwearSportsShoes() {
		return kidsBoysFootwearSportsShoes;
	}

	public IOSElement getKidsBoysFootwearSandalsFlipflops() {
		return kidsBoysFootwearSandalsFlipflops;
	}

	public IOSElement getKidsGirlsFootwear() {
		return kidsGirlsFootwear;
	}

	public IOSElement getKidsGirlsFootwearFlatsCasualShoes() {
		return kidsGirlsFootwearFlatsCasualShoes;
	}

	public IOSElement getKidsGirlsFootwearHeels() {
		return kidsGirlsFootwearHeels;
	}

	public IOSElement getKidsGirlsFootwearSportsShoes() {
		return kidsGirlsFootwearSportsShoes;
	}

	public IOSElement getKidsGirlsFootwearSandalsFlipflops() {
		return kidsGirlsFootwearSandalsFlipflops;
	}

	public IOSElement getKidsAccessories() {
		return KidsAccessories;
	}

	public IOSElement getKidsAccessoriesBagsBackpacks() {
		return KidsAccessoriesBagsBackpacks;
	}

	public IOSElement getKidsAccessoriesWatches() {
		return KidsAccessoriesWatches;
	}

	public IOSElement getKidsAccessoriesJewelleryHairAccessories() {
		return KidsAccessoriesJewelleryHairAccessories;
	}

	public IOSElement getKidsAccessoriesSunglassesFrames() {
		return KidsAccessoriesSunglassesFrames;
	}

	public IOSElement getKidsToys() {
		return KidsToys;
	}

	
	
	
	/***********methods***********************/
	
	public void clickOnkidsBrands() {
		kidsBrands.click();
		System.out.println("Succesfully click on Kids Brands");
	}

	public void clickOnkidsBrandsMothercare() {
		kidsBrandMothercare.click();
		System.out.println("Succesfully click on Kids Brand-Mothercare");
	}

	public void clickOnkidsBrandGiniandJony() {
		kidsBrandGiniandJony.click();
		System.out.println("Succesfully click on Kids Brand-GiniandJony");
	}

	public void clickOnkidsBrandUSPoloAssn() {
		kidsBrandUSPoloAssn.click();
		System.out.println("Succesfully click on Kids Brand-USPoloAssn");
	}

	public void clickOnkidsBrandLilliput() {
		kidsBrandLilliput.click();
		System.out.println("Succesfully click on Kids Brand-Lilliput");
	}

	public void clickOnkidsBrandUnitedColorsofBenetton() {
		kidsBrandUnitedColorsofBenetton.click();
		System.out.println("Succesfully click on Kids Brand-UnitedColorsofBenetton");
	}

	public void clickOnkidsBrandYK() {
		kidsBrandYK.click();
		System.out.println("Succesfully click on Kids Brand-YK");
	}

	public void clickOnkidsBrandAllenSollyJunior() {
		kidsBrandAllenSollyJunior.click();
		System.out.println("Succesfully click on Kids Brand-llenSollyJunior");
	}

	public void clickOnkidsBrandMangoKids() {
		kidsBrandMangoKids.click();
		System.out.println("Succesfully click on Kids Brand-MangoKids");
	}

	public void clickOnkidsBrandMarksSpencer() {
		kidsBrandMarksSpencer.click();
		System.out.println("Succesfully click on Kids Brand-Marks & Spencer");
	}

	public void clickOnkidsBrandTommyHilfiger() {
		kidsBrandTommyHilfiger.click();
		System.out.println("Succesfully click on Kids Brand-TommyHilfiger");
	}

	public void clickOnkidsBrand612League() {
		kidsBrand612League.click();
		System.out.println("Succesfully click on Kids Brand-612League");
	}

	public void clickOnkidsBoysClothing() {
		kidsBoysClothing.click();
		System.out.println("Succesfully click on kids Boys Clothing");
	}

	public void clickOnkidsBoysClothingTShirts() {
		kidsBoysClothingTShirts.click();
		System.out.println("Succesfully click on kids Boys Clothing T-Shirts");
	}

	public void clickOnkidsBoysClothingShirts() {
		kidsBoysClothingShirts.click();
		System.out.println("Succesfully click on kids Boys Clothing Shirts");
	}

	public void clickOnkidsBoysClothingJeansTrousers() {
		kidsBoysClothingJeansTrousers.click();
		System.out.println("Succesfully click on kids Boys Clothing Jeans&Trousers");
	}

	public void clickOnkidsBoysClothingShortsDungarees() {
		kidsBoysClothingShortsDungarees.click();
		System.out.println("Succesfully click on kids Boys Clothing Shorts&Dungarees");
	}

	public void clickOnkidsBoysClothingTrackPantsPyjamas() {
		kidsBoysClothingTrackPantsPyjamas.click();
		System.out.println("Succesfully click on kids Boys Clothing TrackPants&Pyjamas");
	}

	public void clickOnkidsBoysClothingClothingSets() {
		kidsBoysClothingClothingSets.click();
		System.out.println("Succesfully click on kids Boys Clothing ClothingSets");
	}

	public void clickOnkidsBoysClothingIndianWear() {
		kidsBoysClothingIndianWear.click();
		System.out.println("Succesfully click on kids Boys Clothing Indian Wear");
	}

	public void clickOnkidsBoysClothingSweatersSweatshirtsJackets() {
		kidsBoysClothingSweatersSweatshirtsJackets.click();
		System.out.println("Succesfully click on kids Boys Clothing Sweaters,Sweatshirts & Jackets");
	}

	public void clickOnkidsBoysClothingInnerSleepWear() {
		kidsBoysClothingInnerSleepWear.click();
		System.out.println("Succesfully click on kids Boys Clothing Inner SleepWear");
	}

	public void clickOnkidsGirlsClothing() {
		kidsGirlsClothing.click();
		System.out.println("Succesfully click on kids Girls Clothing");
	}

	public void clickOnkidsGirlsClothingDresses() {
		kidsGirlsClothingDresses.click();
		System.out.println("Succesfully click on kids Girls Clothing Dresses");
	}

	public void clickOnkidsGirlsClothingTopsTshirts() {
		kidsGirlsClothingTopsTshirts.click();
		System.out.println("Succesfully click on kids Girls Clothing Tops & Tshirts");
	}

	public void clickOnkidsGirlsClothingClothingSets() {
		kidsGirlsClothingClothingSets.click();
		System.out.println("Succesfully click on kids Girls Clothing ClothingSets");
	}

	public void clickOnkidsGirlsClothingIndianWear() {
		kidsGirlsClothingIndianWear.click();
		System.out.println("Succesfully click on kids Girls Clothing IndianWear");
	}

	public void clickOnkidsGirlsClothingSkirtsShortsJumpsuits() {
		kidsGirlsClothingSkirtsShortsJumpsuits.click();
		System.out.println("Succesfully click on kids Girls Clothing Skirts,Shorts & Jumpsuits");
	}

	public void clickOnkidsGirlsClothingTightsLeggings() {
		kidsGirlsClothingTightsLeggings.click();
		System.out.println("Succesfully click on kids Girls Clothing Tights & Leggings");
	}

	public void clickOnkidsGirlsClothingJeansTrousersCapris() {
		kidsGirlsClothingJeansTrousersCapris.click();
		System.out.println("Succesfully click on kids Girls Clothing Jeans,Trousers & Capris");
	}

	public void clickOnkidsGirlsClothingTrackPants() {
		kidsGirlsClothingTrackPants.click();
		System.out.println("Succesfully click on kids Girls Clothing TrackPants");
	}

	public void clickOnkidsGirlsClothingSweatersSweatshirtsJackets() {
		kidsGirlsClothingSweatersSweatshirtsJackets.click();
		System.out.println("Succesfully click on kids Girls Clothing Sweaters,Sweatshirts & Jackets");
	}

	public void clickOnkidsGirlsClothingInnerSleepWear() {
		kidsGirlsClothingInnerSleepWear.click();
		System.out.println("Succesfully click on kids Girls Clothing Inner & SleepWear");
	}

	public void clickOnkidsBoysFootwear() {
		kidsBoysFootwear.click();
		System.out.println("Succesfully click on kids Boys Footwear");
	}

	public void clickOnkidsBoysFootwearCasualShoes() {
		kidsBoysFootwearCasualShoes.click();
		System.out.println("Succesfully click on kids Boys Footwear CasualShoes");
	}

	public void clickOnkidsBoysFootwearSportsShoes() {
		kidsBoysFootwearSportsShoes.click();
		System.out.println("Succesfully click on kids Boys Footwear Sports Shoes");
	}

	public void clickOnkidsBoysFootwearSandalsFlipflops() {
		kidsBoysFootwearSandalsFlipflops.click();
		System.out.println("Succesfully click on kids Boys Footwear Sandals & Flipflops");
	}

	public void clickOnkidsGirlsFootwear() {
		kidsGirlsFootwear.click();
		System.out.println("Succesfully click on kids Girls Footwear");
	}

	public void clickOnkidsGirlsFootwearFlatsCasualShoes() {
		kidsGirlsFootwearFlatsCasualShoes.click();
		System.out.println("Succesfully click on kids Girls Footwear Flats & CasualShoes");
	}

	public void clickOnkidsGirlsFootwearHeels() {
		kidsGirlsFootwearHeels.click();
		System.out.println("Succesfully click on kids Girls Footwear Heels");
	}

	public void clickOnkidsGirlsFootwearSportsShoes() {
		kidsGirlsFootwearSportsShoes.click();
		System.out.println("Succesfully click on kids Girls Footwear SportsShoes");
	}

	public void clickOnkidsGirlsFootwearSandalsFlipflops() {
		kidsGirlsFootwearSandalsFlipflops.click();
		System.out.println("Succesfully click on kids Girls Footwear Sandals & Flipflops");
	}

	public void clickOnKidsAccessories() {
		KidsAccessories.click();
		System.out.println("Succesfully click on Kids Accessories");
	}

	public void clickOnKidsAccessoriesBagsBackpacks() {
		KidsAccessoriesBagsBackpacks.click();
		System.out.println("Succesfully click on Kids Accessories BagsBackpacks");
	}

	public void clickKidsAccessoriesWatches() {
		KidsAccessoriesWatches.click();
		System.out.println("Succesfully click on Kids Accessories Watches");
	}

	public void clickOnKidsAccessoriesJewelleryHairAccessories() {
		KidsAccessoriesJewelleryHairAccessories.click();
		System.out.println("Succesfully click on Kids Accessories Jewellery HairAccessories");
	}

	public void clickOnKidsAccessoriesSunglassesFrames() {
		KidsAccessoriesSunglassesFrames.click();
		System.out.println("Succesfully click on Kids Accessories Sunglasses & Frames");
	}

	public void clickOnKidsToys() {
		KidsToys.click();
		System.out.println("Succesfully click on Kids Toys");
	}

	}
