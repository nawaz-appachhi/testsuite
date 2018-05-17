package com.automation.mobile.iOS.apps.ObjectRepository.Categories;

import org.openqa.selenium.support.PageFactory;

import com.automation.core.mobile.iOS.iOSGenericMethods;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;
	
	
public class AudioGadgetAndTravelCategoriesPageObjects {
	iOSGenericMethods objiOSGenericMethods;
	
	public AudioGadgetAndTravelCategoriesPageObjects(AppiumDriver<MobileElement> iDriver) {
		PageFactory.initElements(new AppiumFieldDecorator(iDriver), this);
		
	}

	@iOSFindBy(accessibility = "Audio Gadgets & Travel Head phones, Rucksacks, Trolleys")
	public IOSElement audioGadgetAndTravel;
	/**
	 * Audio Gadgets & Travel Categories
	 */
	@iOSFindBy(accessibility = "Explore Audio Store")
	public IOSElement audioExploreAudioStore;
	@iOSFindBy(accessibility = "Headphones")
	public IOSElement audioHeadphones;
	@iOSFindBy(accessibility = "Speakers")
	public IOSElement audioSpeakers;
	@iOSFindBy(accessibility = "Explore Travel Store ï�¸")
	public IOSElement audioExploreTravelStore;
	@iOSFindBy(accessibility = "Women")
	public IOSElement audioExploreTravelStoreWomen;
	@iOSFindBy(accessibility = "Men")
	public IOSElement audioExploreTravelStoreMen;
	@iOSFindBy(accessibility = "Trolleys")
	public IOSElement audioTrolleys;
	@iOSFindBy(accessibility = "Rucksacks")
	public IOSElement audioRucksacks;

	/****************getters**************************/
	
	public IOSElement getAudioGadgetAndTravel() {
		objiOSGenericMethods.CheckIOSElementFound(audioGadgetAndTravel, "audioGadgetAndTravel");
		return audioGadgetAndTravel;
	}

	public IOSElement getAudioExploreAudioStore() {
		objiOSGenericMethods.CheckIOSElementFound(audioExploreAudioStore, "audioExploreAudioStore");
		return audioExploreAudioStore;
	}

	public IOSElement getAudioHeadphones() {
		objiOSGenericMethods.CheckIOSElementFound(audioHeadphones, "audioHeadphones");
		return audioHeadphones;
	}

	public IOSElement getAudioSpeakers() {
		objiOSGenericMethods.CheckIOSElementFound(audioSpeakers, "audioSpeakers");
		return audioSpeakers;
	}

	public IOSElement getAudioExploreTravelStore() {
		objiOSGenericMethods.CheckIOSElementFound(audioExploreTravelStore, "audioExploreTravelStore");
		return audioExploreTravelStore;
	}

	public IOSElement getAudioExploreTravelStoreWomen() {
		objiOSGenericMethods.CheckIOSElementFound(audioExploreTravelStoreWomen, "audioExploreTravelStoreWomen");
		return audioExploreTravelStoreWomen;
	}

	public IOSElement getAudioExploreTravelStoreMen() {
		objiOSGenericMethods.CheckIOSElementFound(audioExploreTravelStoreMen, "audioExploreTravelStoreMen");
		return audioExploreTravelStoreMen;
	}

	public IOSElement getAudioTrolleys() {
		objiOSGenericMethods.CheckIOSElementFound(audioTrolleys, "audioTrolleys");
		return audioTrolleys;
	}

	public IOSElement getAudioRucksacks() {
		objiOSGenericMethods.CheckIOSElementFound(audioRucksacks, "audioRucksacks");
		return audioRucksacks;
	}

	/****************Methods**************************/
	
	
	
	public void clickOnAudioExploreAudioStore() {
		objiOSGenericMethods.clickOnIOSElement(getAudioExploreAudioStore(), "Successfully click on Explore Audio Store button");
	}

	public void clickOnHeadphones() {
		objiOSGenericMethods.clickOnIOSElement(getAudioHeadphones(), "Successfully click on Headphones button");
	}

	public void clickOnSpeakers() {
		objiOSGenericMethods.clickOnIOSElement(getAudioSpeakers(), "Successfully click on Speakers button");
	}

	public void clickOnAudioExploreTravelStore() {
		objiOSGenericMethods.clickOnIOSElement(getAudioExploreTravelStore(), "Successfully click on Explore Travel Store button");
	}

	public void clickOnAudioExploreTravelStoreWomen() {
		objiOSGenericMethods.clickOnIOSElement(getAudioExploreTravelStoreWomen(), "Successfully click on Explore Travel Store Women button");
	}

	public void clickOnAudioExploreTravelStoreMen() {
		objiOSGenericMethods.clickOnIOSElement(getAudioExploreTravelStoreMen(), "Successfully click on Explore Travel Store Men button");
	}

	public void clickOnAudioTrolleys() {
		objiOSGenericMethods.clickOnIOSElement(getAudioTrolleys(), "Successfully click on Trolleys button");
	}

	public void clickOnAudioRucksacks() {
		objiOSGenericMethods.clickOnIOSElement(getAudioRucksacks(), "Successfully click on Rucksacks button");
	}

	
}
