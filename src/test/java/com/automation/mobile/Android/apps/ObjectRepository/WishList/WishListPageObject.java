package com.automation.mobile.Android.apps.ObjectRepository.WishList;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;
import com.automation.core.mobile.Android.AndroidGenericMethods;
import com.automation.mobile.Android.apps.ObjectRepository.Home.HomePageObject;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class WishListPageObject {
	AndroidGenericMethods objAndroidGenericMethods;
	HomePageObject objHomepageObjects;

	public WishListPageObject(AndroidDriver<AndroidElement> aDriver) {
		PageFactory.initElements(new AppiumFieldDecorator(aDriver), this);
		objAndroidGenericMethods = new AndroidGenericMethods(aDriver);
		objHomepageObjects = new HomePageObject(aDriver);
	}
	
	
	/**
	 * Owner of this page
	 * @author 300021282 VINAY
	 */

	/****************** OBJECTS ***********************/
	/**
	 * Object identified for move to bag button displayed on products;
	 * 
	 * @author 300021278 -Rakesh
	 * 
	 */
	@FindBy(xpath = "//android.widget.Button[@text='MOVE TO BAG' or resource-id='com.myntra.android:id/move_to_bag']")
	public AndroidElement moveToBag;

	/**
	 * Object identified for cross button displayed on the product tile;
	 * 
	 * @author 300021278 -Rakesh
	 */
	@FindBy(xpath = "//android.widget.RelativeLayout[@resource-id='com.myntra.android:id/ll_remove_from_list_container']")
	public AndroidElement crossBtn;

	/**
	 * Object identified for back button displayed in the header;
	 * 
	 * @author 300021278 -Rakesh
	 */
	@FindBy(xpath = "//android.view.ViewGroup[@resource-id='com.myntra.android:id/toolbar']/android.widget.ImageButton")
	public AndroidElement backBtn;

	/**
	 * Object identified for message displayed in wishlist for no products found
	 * condition;
	 * 
	 * @author 300021278 -Rakesh
	 */
	@FindBy(xpath = "//android.widget.TextView[@resource-id='com.myntra.android:id/tv_list_no_items2']")
	public AndroidElement emptyProductsMsg;

	/**
	 * Object identified to select a size for the product in wishlist page
	 * 
	 * @author Rakesh
	 * 
	 */
	@FindAll({
			@FindBy(xpath = "//android.support.v7.widget.RecyclerView[@resource-id='com.myntra.android:id/rv_pdp_sizeselector']/android.widget.LinearLayout[@resource-id='com.myntra.android:id/rl_main_container']") })
	public List<AndroidElement> SizeWishlistbtn;

	/**
	 * Object identified for done button displayed after selecting a size of a
	 * product.
	 * 
	 * @author 300021282 VINAY
	 */
	@FindBy(xpath = "//android.widget.Button[@text='DONE']")
	public AndroidElement wishlistDonebtn;
	/**
	 * Object Identified for bag icon displayed in header in wishlist page.
	 * 
	 * @author 300021282 VINAY
	 */
	@FindBy(xpath = "//android.widget.RelativeLayout[@resource-id = 'com.myntra.android:id/action_cart']")
	public AndroidElement bagBtn;

	/************ getters *****************/

	/**
	 * getter for the empty Products Msg displayed in the wishlist;
	 * 
	 * @author 300021278 -Rakesh
	 * @return
	 */
	public AndroidElement getEmptyProductsMsg() {
		return emptyProductsMsg;
	}

	/**
	 * getter for the bag button displayed at the header in wihlist page;
	 * 
	 * @author 300021282 VINAY
	 * @return
	 */
	public AndroidElement getBagBtn() {
		objAndroidGenericMethods.CheckAndroidElementFound(bagBtn, "bagBtn");
		return bagBtn;
	}

	/**
	 * getter for the back button displayed in the header;
	 * 
	 * @author 300021278 -Rakesh
	 * @return
	 */
	public AndroidElement getBackBtn() {
		objAndroidGenericMethods.CheckAndroidElementFound(backBtn, "backBtn");
		return backBtn;
	}

	/**
	 * getter for the cross button displayed on the product tile;
	 * 
	 * @author 300021278 -Rakesh
	 * @return
	 */
	public AndroidElement getCrossBtn() {
		objAndroidGenericMethods.CheckAndroidElementFound(crossBtn, "crossBtn");
		return crossBtn;
	}

	/**
	 * getter for move to bag button displayed on a product;
	 * 
	 * @return
	 * @author 300021278 -Rakesh
	 */
	public AndroidElement getMoveToBag() {
		objAndroidGenericMethods.CheckAndroidElementFound(moveToBag, "moveToBag");
		return moveToBag;
	}

	/**
	 * getter for selecting a size after clicking on move to bag button;
	 * 
	 * @author 300021282 VINAY
	 * @return
	 */
	public List<AndroidElement> getSizeWishlistbtn() {
		return SizeWishlistbtn;
	}

	/**
	 * getter for done button displayed after selecting a size;
	 * 
	 * @author 300021282 VINAY
	 * @return
	 */
	public AndroidElement getWishlistDonebtn() {
		objAndroidGenericMethods.CheckAndroidElementFound(wishlistDonebtn, "wishlistDonebtn");
		return wishlistDonebtn;
	}

	/************ Methods *****************/

	/**
	 * Method to click on the back button displayed in the header;
	 * 
	 * @author 300021278 -Rakesh
	 */
	public void clickBackBtn() {
		objAndroidGenericMethods.clickOnAndroidElement(getBackBtn(), "click on Back Buttton");
	}

	/**
	 * Method to click on the cross button displayed on the product tile;
	 * 
	 * @author 300021278 -Rakesh
	 */
	public void clickCrossBtn() {
		objAndroidGenericMethods.clickOnAndroidElement(getCrossBtn(), "click on crossBtn");
	}

	/**
	 * method to click on size displayed in wishlist
	 * 
	 * @author Rakesh
	 */
	public void clickSizeWishList() {
		List<AndroidElement> lst = getSizeWishlistbtn();
		System.out.println("the size of total count " + lst.size());
		try {
			for (AndroidElement e : lst) {
				List<MobileElement> sizecount = e.findElements(By.xpath(
						"//android.widget.RelativeLayout[@resource-id='com.myntra.android:id/rl_size_label']/*[starts-with(@resource-id,'com.myntra.android:id')]"));
				System.out.println("the size of count" + sizecount.size());
				if (sizecount.size() == 1) {
					e.click();
					break;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Method to click on done button displayed after selecting a size in wishlist
	 * page;
	 * 
	 * @throws InterruptedException
	 */
	public void clickDoneWishListbtn() throws InterruptedException {
		objAndroidGenericMethods.clickOnAndroidElement(getWishlistDonebtn(), "clicked on done button on wishlist page");
	//	Thread.sleep(2000);

	}

	/**
	 * Method to click on move to bag button displayed on product tile;
	 * 
	 * @throws InterruptedException
	 * @author 300021278 -Rakesh
	 */
	public void clickMoveToBag() throws InterruptedException {
		objAndroidGenericMethods.clickOnAndroidElement(getMoveToBag(), "clickMoveToBag");
	//	Thread.sleep(3000);
	}
	
	/**
	 * Method to click on  bag button displayed in header;
	 * 
	 * @throws InterruptedException
	 * @author 300021278 -Rakesh
	 */
	public void clickBagBtn() throws InterruptedException {
		objAndroidGenericMethods.clickOnAndroidElement(getBagBtn(), "Clicked on BagBtn");
		//Thread.sleep(3000);
	}
	
	
	

	/******************* ASSERTION **********************/
	
	@FindBy(xpath = "//android.widget.TextView[@text='Wishlist']")
	public AndroidElement wishlistIcon;

	public AndroidElement getWishlistIcon() {
		objAndroidGenericMethods.CheckAndroidElementFound(wishlistIcon, "wishlistIcon");
		return wishlistIcon;
	}

	public void verifyWishlistIcon() {
		objAndroidGenericMethods.VerifyTwoString(getWishlistIcon(), "Wishlist");
	}

	/*********************** RESETMETHODS ******************/

	/**
	 * Method to check the condition whether the empty product message is displayed;
	 * 
	 * @author 300021278 -Rakesh
	 * @return
	 */
	public boolean emptyWishlistmsg() {
		try {
			getEmptyProductsMsg().isDisplayed();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Method to remove all items from wishlist;
	 * 
	 * @throws InterruptedException
	 * @author 300021278 -Rakesh
	 */
	public void removeAllItemsFromWishlist() throws InterruptedException {
		while (!emptyWishlistmsg()) {
			clickCrossBtn();
		//	Thread.sleep(1000);
		}
	}

	/**
	 * Method to reset data for wishlist from the account
	 * 
	 * @throws InterruptedException
	 * @author 300021278 -Rakesh
	 */ 
	public void resetWishlist() throws InterruptedException {
		objHomepageObjects.clickRHWishlistbtn();
		if (!emptyWishlistmsg()) { 
			Reporter.log("Products are found in Wishlist");
			System.out.println("Products are found in Wishlist");
			removeAllItemsFromWishlist();
			clickBackBtn();
		} else {
			clickBackBtn();
			Reporter.log("Wishlist found empty");
			System.out.println("Wishlist found empty");
		}

	}

}
