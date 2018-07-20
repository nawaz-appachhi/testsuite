package com.automation.mobile.Android.MobileWeb.ObjectRepository.HomeObjects;

import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.automation.core.mobile.Android.AndroidGenericMethods;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

/**
 * Modified By:Aishurya:Changed string parameter,which is being used in reporting
 */
/**
 * @author 300019227
 *
 */
public class MenuPageObjects {

	AndroidGenericMethods objAndroidGenericMethods;
	public MenuPageObjects(AndroidDriver<AndroidElement> aDriver) {
		PageFactory.initElements(new AppiumFieldDecorator(aDriver), this);
		objAndroidGenericMethods = new AndroidGenericMethods(aDriver);
	}

	/*
	 * MyAccount options
	 * 
	 */
	@FindBy(xpath = "//a[contains(text(),'My Account') and @class='naviLevel']")
	public AndroidElement myAccountOptions;

	@FindBy(xpath = "//a[@href='/cart' and @class='list-item leaf-menu']")
	public AndroidElement myaccountMyBag;

	@FindBy(xpath = "//a[@href='/checkout/wishlist' and @class='list-item leaf-menu']")
	public AndroidElement myaccountMyWishlist;

	@FindBy(xpath = "//a[@href='/login?referer=/my/orders' and @class='list-item leaf-menu']")
	public AndroidElement myaccountTrackOrder;

	@FindBy(xpath = "//a[@href='/my-notifications' and @class='list-item leaf-menu']")
	public AndroidElement myaccountMyNotification;

	@FindBy(xpath = "//a[@href='/login?referer=/my/coupons' and @class='list-item leaf-menu']")
	public AndroidElement myaccountMyCoupons;

	@FindBy(xpath = "//a[@href='/login?referer=/my/phonepe' and @class='list-item leaf-menu']")
	public AndroidElement myaccountMyPhonepe;

	@FindBy(xpath = "//a[@href='/login?referer=/my/cashback' and @class='list-item leaf-menu']")
	public AndroidElement myaccountMyCashback;

	@FindBy(xpath = "//a[@href='/login?referer=/my/myntrapoints' and @class='list-item leaf-menu']")
	public AndroidElement myaccountMyntraPoints;

	@FindBy(xpath = "//a[@href='/login?referer=/my/dashboard' and @class='list-item leaf-menu']")
	public AndroidElement myaccountMyMyntra;

	@FindBy(xpath = "//a[@href='/giftcard' and @class='list-item leaf-menu']")
	public AndroidElement myaccountGiftcard;

	@FindBy(xpath = "//a[@href='/faqs' and @class='list-item leaf-menu']")
	public AndroidElement myaccountFreqAskQstn;

	@FindBy(xpath = "//a[text()='CONTACT US']")
	public AndroidElement myaccountContactus;
	
	@FindBy(xpath="//div[@class='m_logo']")
	public AndroidElement myntraLogoFromCartPage;

	/*
	 * My Orders options 300021281
	 */

	public AndroidElement getMyntraLogoFromCartPage() {
		return myntraLogoFromCartPage;
	}

	@FindBy(xpath = "//a[contains(text(),'My Orders') and @class='naviLevel']")
	public AndroidElement myOrders;

	/*
	 * Giftcards options 300021281
	 */

	@FindBy(xpath = ".//li[@id='tab_gift_card']")
	public AndroidElement giftCards;

	/*
	 * ShoppingGroup 300021281
	 */

	@FindBy(xpath = "//a[contains(text(),'Shopping Group') and @class='naviLevel']")
	public AndroidElement shoppingGroup;
	
	/**
	 * Created by :Anu
	 * Added locator for myntra logo from payment page
	 */
	@FindBy(xpath = "//div[@class='m_logo']")
	public AndroidElement myntraLogoFromPaymentpage;
	

	/**
	 * Created by :Anu
	 * Added getter for myntra logo from payment page
	 */
	public AndroidElement getMyntraLogoFromPaymentpage() {
		return myntraLogoFromPaymentpage;
	}

	public AndroidElement getMyOrders() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(myOrders, "myOrders");
		return myOrders;
	}

	public AndroidElement getGiftCards() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(giftCards, "giftCards");
		return giftCards;
	}

	public AndroidElement getShoppingGroup() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(shoppingGroup, "shoppingGroup");
		return shoppingGroup;
	}

	public AndroidElement getMyAccountOptions() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(myAccountOptions, "myAccountOptions");
		return myAccountOptions;
	}

	public AndroidElement getMyaccountMyBag() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(myaccountMyBag, "myaccountMyBag");
		return myaccountMyBag;
	}

	public AndroidElement getMyaccountMyWishlist() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(myaccountMyWishlist, "myaccountMyWishlist");
		return myaccountMyWishlist;
	}

	public AndroidElement getMyaccountTrackOrder() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(myaccountTrackOrder, "myaccountTrackOrder");
		return myaccountTrackOrder;
	}

	public AndroidElement getMyaccountMyNotification() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(myaccountMyNotification, "myaccountMyNotification");
		return myaccountMyNotification;
	}

	public AndroidElement getMyaccountMyCoupons() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(myaccountMyCoupons, "myaccountMyCoupons");
		return myaccountMyCoupons;
	}

	public AndroidElement getMyaccountMyPhonepe() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(myaccountMyPhonepe, "myaccountMyPhonepe");
		return myaccountMyPhonepe;
	}

	public AndroidElement getMyaccountMyCashback() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(myaccountMyCashback, "myaccountMyCashback");
		return myaccountMyCashback;
	}

	public AndroidElement getMyaccountMyntraPoints() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(myaccountMyntraPoints, "myaccountMyntraPoints");
		return myaccountMyntraPoints;
	}

	public AndroidElement getMyaccountMyMyntra() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(myaccountMyMyntra, "myaccountMyMyntra");
		return myaccountMyMyntra;
	}

	public AndroidElement getMyaccountGiftcard() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(myaccountGiftcard, "myaccountGiftcard");
		return myaccountGiftcard;
	}

	public AndroidElement getMyaccountFreqAskQstn() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(myaccountFreqAskQstn, "myaccountFreqAskQstn");
		return myaccountFreqAskQstn;
	}

	public AndroidElement getMyaccountContactus() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(myaccountContactus, "myaccountContactus");
		return myaccountContactus;
	}

	public void clickOnMyAccoutOptions() {
		objAndroidGenericMethods.clickOnAndroidElement(getMyAccountOptions(), "MyAccountOptions");
	}

	public void clickOnMyAccoutMyBag() {
		objAndroidGenericMethods.clickOnAndroidElement(getMyaccountMyBag(), "Bag");
	}

	public void clickOnMyAccoutMyWishlist() {
		objAndroidGenericMethods.clickOnAndroidElement(getMyaccountMyWishlist(), "MyWishlist");
	}

	public void clickOnMyAccountTrackOrder() {
		objAndroidGenericMethods.clickOnAndroidElement(getMyaccountTrackOrder(), "TrackOrder");
	}

	public void clickOnMyAccoutMyNotification() {
		objAndroidGenericMethods.clickOnAndroidElement(getMyaccountMyNotification(), "Notifications");
	}

	public void clickOnMyAccoutMyCoupons() {
		objAndroidGenericMethods.clickOnAndroidElement(getMyaccountMyCoupons(), "MyCoupons");
	}

	public void clickOnMyAccoutPhonePeWallet() {
		objAndroidGenericMethods.clickOnAndroidElement(getMyaccountMyPhonepe(), "MyPhonepe");
	}

	public void clickOnMyAccoutMyCashback() {
		objAndroidGenericMethods.clickOnAndroidElement(getMyaccountMyCashback(), "MyCashback");
	}

	public void clickOnMyAccoutMyntraPoints() {
		objAndroidGenericMethods.clickOnAndroidElement(getMyaccountMyntraPoints(), "MyntraPoints");
	}

	public void clickOnMyAccoutMyMyntra() {
		objAndroidGenericMethods.clickOnAndroidElement(getMyaccountMyMyntra(), "MyMyntra");
	}

	public void clickOnMyAccoutGiftcard() {
		objAndroidGenericMethods.clickOnAndroidElement(getMyaccountGiftcard(), "Giftcard");
	}

	public void clickOnMyAccoutFrequentAskQuestion() {
		objAndroidGenericMethods.clickOnAndroidElement(getMyaccountFreqAskQstn(), "FreqAskQstn");
	}

	public void clickOnMyAccoutContactUs() throws InterruptedException {
		objAndroidGenericMethods.clickOnAndroidElement(getMyaccountContactus(), "Contactus");
	}

	public void clickOnMyWishList() {
		objAndroidGenericMethods.clickOnAndroidElement(getMyOrders(), "MyOrders");
	}

	public void clickOnGiftcard() {
		objAndroidGenericMethods.clickOnAndroidElement(getGiftCards(), "GiftCards");
	}

	public void clickOnShoppingGroup() {
		objAndroidGenericMethods.clickOnAndroidElement(getShoppingGroup(), "ShoppingGroup");
	}
	public void ClickMyntraLogoFromCartPage() throws InterruptedException{
		getMyntraLogoFromCartPage().click();
	}
	/**
	 * Created by :Anu
	 * Added method to click myntra logo from payment page
	 */
	public void ClickMyntraLogoFromPaymentpage() throws InterruptedException{
		getMyntraLogoFromPaymentpage().click();
	}

}
