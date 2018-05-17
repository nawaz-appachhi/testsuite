package com.automation.mobile.iOS.MobileWeb.ObjectRepository.HomeObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.automation.core.mobile.iOS.iOSGenericMethods;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class MenuPageObjects {

	public AppiumDriver<MobileElement>  iDriver;
	iOSGenericMethods objiOSGenericMethods;

	public MenuPageObjects(AppiumDriver<MobileElement>  iDriver) {
		PageFactory.initElements(new AppiumFieldDecorator(iDriver), this);
		objiOSGenericMethods = new iOSGenericMethods(iDriver);
	}

	/*
	 * MyAccount options
	 * 
	 */
	@FindBy(xpath = "//a[contains(text(),'My Account') and @class='naviLevel']")
	public IOSElement myAccountOptions;

	@FindBy(xpath = "//a[@href='/cart' and @class='list-item leaf-menu']")
	public IOSElement myaccountMyBag;

	@FindBy(xpath = "//a[@href='/checkout/wishlist' and @class='list-item leaf-menu']")
	public IOSElement myaccountMyWishlist;

	@FindBy(xpath = "//a[@href='/login?referer=/my/orders' and @class='list-item leaf-menu']")
	public IOSElement myaccountTrackOrder;

	@FindBy(xpath = "//a[@href='/my-notifications' and @class='list-item leaf-menu']")
	public IOSElement myaccountMyNotification;

	@FindBy(xpath = "//a[@href='/login?referer=/my/coupons' and @class='list-item leaf-menu']")
	public IOSElement myaccountMyCoupons;

	@FindBy(xpath = "//a[@href='/login?referer=/my/phonepe' and @class='list-item leaf-menu']")
	public IOSElement myaccountMyPhonepe;

	@FindBy(xpath = "//a[@href='/login?referer=/my/cashback' and @class='list-item leaf-menu']")
	public IOSElement myaccountMyCashback;

	@FindBy(xpath = "//a[@href='/login?referer=/my/myntrapoints' and @class='list-item leaf-menu']")
	public IOSElement myaccountMyntraPoints;

	@FindBy(xpath = "//a[@href='/login?referer=/my/dashboard' and @class='list-item leaf-menu']")
	public IOSElement myaccountMyMyntra;

	@FindBy(xpath = "//a[@href='/giftcard' and @class='list-item leaf-menu']")
	public IOSElement myaccountGiftcard;

	@FindBy(xpath = "//a[@href='/faqs' and @class='list-item leaf-menu']")
	public IOSElement myaccountFreqAskQstn;

	@FindBy(xpath = "//a[@href='/contactus' and @class='list-item leaf-menu contactus']")
	public IOSElement myaccountContactus;

	/*
	 * My Orders options 300021281
	 */

	@FindBy(xpath = "//a[contains(text(),'My Orders') and @class='naviLevel']")
	public IOSElement myOrders;

	/*
	 * Giftcards options 300021281
	 */

	@FindBy(xpath = ".//li[@id='tab_gift_card']")
	public IOSElement giftCards;

	/*
	 * ShoppingGroup 300021281
	 */

	@FindBy(xpath = "//a[contains(text(),'Shopping Group') and @class='naviLevel']")
	public IOSElement shoppingGroup;

	public IOSElement getMyOrders() {
		objiOSGenericMethods.CheckIOSElementFound(myOrders, "myOrders");
		return myOrders;
	}

	public IOSElement getGiftCards() {
		objiOSGenericMethods.CheckIOSElementFound(giftCards, "giftCards");
		return giftCards;
	}

	public IOSElement getShoppingGroup() {
		objiOSGenericMethods.CheckIOSElementFound(shoppingGroup, "shoppingGroup");
		return shoppingGroup;
	}

	public IOSElement getMyAccountOptions() {
		objiOSGenericMethods.CheckIOSElementFound(myAccountOptions, "myAccountOptions");
		return myAccountOptions;
	}

	public IOSElement getMyaccountMyBag() {
		objiOSGenericMethods.CheckIOSElementFound(myaccountMyBag, "myaccountMyBag");
		return myaccountMyBag;
	}

	public IOSElement getMyaccountMyWishlist() {
		objiOSGenericMethods.CheckIOSElementFound(myaccountMyWishlist, "myaccountMyWishlist");
		return myaccountMyWishlist;
	}

	public IOSElement getMyaccountTrackOrder() {
		objiOSGenericMethods.CheckIOSElementFound(myaccountTrackOrder, "myaccountTrackOrder");
		return myaccountTrackOrder;
	}

	public IOSElement getMyaccountMyNotification() {
		objiOSGenericMethods.CheckIOSElementFound(myaccountMyNotification, "myaccountMyNotification");
		return myaccountMyNotification;
	}

	public IOSElement getMyaccountMyCoupons() {
		objiOSGenericMethods.CheckIOSElementFound(myaccountMyCoupons, "myaccountMyCoupons");
		return myaccountMyCoupons;
	}

	public IOSElement getMyaccountMyPhonepe() {
		objiOSGenericMethods.CheckIOSElementFound(myaccountMyPhonepe, "myaccountMyPhonepe");
		return myaccountMyPhonepe;
	}

	public IOSElement getMyaccountMyCashback() {
		objiOSGenericMethods.CheckIOSElementFound(myaccountMyCashback, "myaccountMyCashback");
		return myaccountMyCashback;
	}

	public IOSElement getMyaccountMyntraPoints() {
		objiOSGenericMethods.CheckIOSElementFound(myaccountMyntraPoints, "myaccountMyntraPoints");
		return myaccountMyntraPoints;
	}

	public IOSElement getMyaccountMyMyntra() {
		objiOSGenericMethods.CheckIOSElementFound(myaccountMyMyntra, "myaccountMyMyntra");
		return myaccountMyMyntra;
	}

	public IOSElement getMyaccountGiftcard() {
		objiOSGenericMethods.CheckIOSElementFound(myaccountGiftcard, "myaccountGiftcard");
		return myaccountGiftcard;
	}

	public IOSElement getMyaccountFreqAskQstn() {
		objiOSGenericMethods.CheckIOSElementFound(myaccountFreqAskQstn, "myaccountFreqAskQstn");
		return myaccountFreqAskQstn;
	}

	public IOSElement getMyaccountContactus() {
		objiOSGenericMethods.CheckIOSElementFound(myaccountContactus, "myaccountContactus");
		return myaccountContactus;
	}

	public void clickOnMyAccoutOptions() {
		objiOSGenericMethods.clickOnIOSElement(getMyAccountOptions(), "clicked on MyAccountOptions");
	}

	public void clickOnMyAccoutMyBag() {
		objiOSGenericMethods.clickOnIOSElement(getMyaccountMyBag(), "clicked on Bag");
	}

	public void clickOnMyAccoutMyWishlist() {
		objiOSGenericMethods.clickOnIOSElement(getMyaccountMyWishlist(), "clicked on MyWishlist");
	}

	public void clickOnMyAccountTrackOrder() {
		objiOSGenericMethods.clickOnIOSElement(getMyaccountTrackOrder(), "clicked on TrackOrder");
	}

	public void clickOnMyAccoutMyNotification() {
		objiOSGenericMethods.clickOnIOSElement(getMyaccountMyNotification(), "clicked on Notifications");
	}

	public void clickOnMyAccoutMyCoupons() {
		objiOSGenericMethods.clickOnIOSElement(getMyaccountMyCoupons(), "clicked on MyCoupons");
	}

	public void clickOnMyAccoutPhonePeWallet() {
		objiOSGenericMethods.clickOnIOSElement(getMyaccountMyPhonepe(), "clicked on MyPhonepe");
	}

	public void clickOnMyAccoutMyCashback() {
		objiOSGenericMethods.clickOnIOSElement(getMyaccountMyCashback(), "clicked on MyCashback");
	}

	public void clickOnMyAccoutMyntraPoints() {
		objiOSGenericMethods.clickOnIOSElement(getMyaccountMyntraPoints(), "clicked on MyntraPoints");
	}

	public void clickOnMyAccoutMyMyntra() {
		objiOSGenericMethods.clickOnIOSElement(getMyaccountMyMyntra(), "clicked on MyMyntra");
	}

	public void clickOnMyAccoutGiftcard() {
		objiOSGenericMethods.clickOnIOSElement(getMyaccountGiftcard(), "clicked on Giftcard");
	}

	public void clickOnMyAccoutFrequentAskQuestion() {
		objiOSGenericMethods.clickOnIOSElement(getMyaccountFreqAskQstn(), "clicked on FreqAskQstn");
	}

	public void clickOnMyAccoutContactUs() {
		objiOSGenericMethods.clickOnIOSElement(getMyaccountContactus(), "clicked on Contactus");
	}

	public void clickOnMyWishList() {
		objiOSGenericMethods.clickOnIOSElement(getMyOrders(), "clicked on MyOrders");
	}

	public void clickOnGiftcard() {
		objiOSGenericMethods.clickOnIOSElement(getGiftCards(), "clicked on GiftCards");
	}

	public void clickOnShoppingGroup() {
		objiOSGenericMethods.clickOnIOSElement(getShoppingGroup(), "clicked on ShoppingGroup");
	}

}
