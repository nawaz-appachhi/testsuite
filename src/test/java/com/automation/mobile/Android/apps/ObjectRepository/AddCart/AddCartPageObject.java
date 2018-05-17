package com.automation.mobile.Android.apps.ObjectRepository.AddCart;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.automation.core.mobile.Android.AndroidGenericMethods;
import com.automation.mobile.Android.apps.ObjectRepository.Home.HomePageObject;
import com.automation.mobile.Android.apps.ObjectRepository.PLP.ProductListPageObject;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.PressesKeyCode;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.AndroidKeyCode;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class AddCartPageObject {
	public AppiumDriver<MobileElement> aDriver;
	AndroidGenericMethods objAndroidGenericMethods;
	HomePageObject objHomePageObject;
	ProductListPageObject objProductListPageObject;

	public AddCartPageObject(AppiumDriver<MobileElement> aDriver) {
		PageFactory.initElements(new AppiumFieldDecorator(aDriver), this);
		objAndroidGenericMethods = new AndroidGenericMethods(aDriver);
		objHomePageObject = new HomePageObject(aDriver);
		objProductListPageObject = new ProductListPageObject(aDriver);
		this.aDriver = aDriver;
	}

	/**
	 * Name: Vinay
	 * 
	 * Description: This xpath help us to click on "Shopping Bag" icon on Home
	 * screen
	 */
	@FindBy(xpath = "//android.view.ViewGroup[@index='5'] | //android.view.View[@index='5']")
	public AndroidElement ShoppingBagBtn;

	/**
	 * Object identified for remove button displayed on product tile in bag page;
	 * @ModifiedBy:-Rakesh reason: added html xpath bcoz of web view
	 * 
	 * @author 300021278 -Rakesh
	 */
	@FindBy(xpath = "//span[text()='REMOVE']")
	public AndroidElement removeBtn;

	/**
	 * Object identified for remove button displayed in popup after clicking on
	 * remove product displayed on product tile in bag page;
	 * 
	 * @author 300021278 -Rakesh
	 */
	@FindBy(xpath = "//button[contains(text(),'REMOVE')]")
	public AndroidElement PopUpRemoveBtn;

	//button[contains(text(),'REMOVE')]
	/**
	 * Name:Vinay
	 * 
	 * Description: This xpath help us to click on "Apply Coupon" option inside the
	 * Cart page
	 * Modified by vinay 
	 */
	@FindBy(xpath = "//span[text()='Apply Coupon']")
//	@FindBy(xpath = "//android.view.View[@text='Apply Coupon']")
	public AndroidElement ApplyCouponBtn;
	/**
	 * Name=Vinayss
	 * 
	 * Description=This xpath will help user to click on "coupon code box" on Apply
	 * coupon screen
	 */
	@FindBy(xpath = "//input[@class='enter-coupon m-textbox']")
//	@FindBy(xpath = "//android.widget.Spinner[@text='Type coupon code here']")
	public AndroidElement couponCodeBox;

	/**
	 * 
	 * Description= This xpath will help user to click "cross icon" on Apply coupon
	 * screen.
	 */
	@FindBy(xpath = "//android.widget.ImageButton[@content-desc='Navigate up']")
	public AndroidElement CrossBtn;
	/**
	 * Name= Vinay
	 * 
	 * Description= This xpath will help user to click "Cancel button" on Apply
	 * coupon screen.
	 */
	@FindBy(xpath = "//button[text()='Cancel']")
//	@FindBy(xpath = "//android.widget.Button[@content-desc='CANCEL' or @text='CANCEL']")
	public AndroidElement couponCancelBtn;

	/**
	 * Name:Vinay
	 * 
	 * Description: This xpath help us to click on "Apply Button" on Apply coupon
	 * screen.
	 */
	@FindBy(xpath = "//button[@name='btn-apply']")
//	@FindBy(xpath = "//android.widget.Button[@content-desc='APPLY' or @text='APPLY']")
	public AndroidElement ApplyBtn;
	/**
	 * Name: Vinay
	 * 
	 * Description: This xpath help us to click on "Gift wrap for Rs 25" option
	 * inside the Shopping Bag page.
	 */
	@FindBy(xpath = "//div[text()='Gift wrap for Rs 25']")
//	@FindBy(xpath = "//android.view.View[@content-desc='Gift wrap for Rs 25' or @text='Gift wrap for Rs 25']")
	public AndroidElement GiftWrapBtn;
	/**
	 * Name: Vinay
	 * 
	 * Description: This xpath help us to Enter the "Recipient Name" on GIFT WRAP
	 * Screen.
	 */
	@FindBy(name = "giftto")
//	@FindBy(xpath = "//android.widget.EditText[@content-desc='Recipient Name' or @text='Recipient Name']")
	public AndroidElement RecipientNameTxt;
	/**
	 * Name: Vinay
	 * 
	 * Description: This xpath help us to Enter the "Gift Message text" on GIFT WRAP
	 * Screen.
	 */
	@FindBy(id = "gift-msg")
//	@FindBy(xpath = "//android.widget.EditText[@content-desc='Gift Message' or @text='Gift Message']")
	public AndroidElement GiftMessagetxt;
	/**
	 * Name: Vinay
	 * 
	 * Description: This xpath help us to Enter the "Sender Name" on GIFT WRAP
	 * Screen.
	 */
	
	@FindBy(id = "gift-from")
//	@FindBy(xpath = "//android.widget.EditText[@content-desc='Sender Name' or @text='Sender Name']")
	public AndroidElement SenderNameTxt;
	/**
	 * Name: Vinay
	 * 
	 * Description: This xpath help us to click "Save" button on GIFT WRAP Screen.
	 */
	@FindBy(xpath = "//button[text()='Save']")
//	@FindBy(xpath = "//android.widget.Button[@content-desc='SAVE' or @index='1']")
	public AndroidElement SaveGiftBtn;
	
	@FindBy(xpath = "//button[text()='Cancel']")
//	@FindBy(xpath = "//android.widget.Button[@content-desc='Cancel' or @index='1']")
	public AndroidElement CancelGiftbtn;
	/**
	 * Name: Vinay Description: This xpath help us to click on "WISHLIST" Icon
	 * inside the Shopping Bag page
	 */
	@FindBy(xpath = "//android.widget.Button[@text='Wishlist' or @resource-id='com.myntra.android:id/tb_wishlist']")
	public AndroidElement WishListBtn;
	/**
	 * Name: Vinay
	 * 
	 * Description: This xpath help us to view details "ViewDetails" button inside
	 * the Shopping Bag page
	 */
	@FindBy(xpath = "//div[text()='VIEW DETAILS']")
	public AndroidElement ViewDetailsBtn;
	/**
	 * Name: Vinay
	 * 
	 * Description: This xpath help us to click on "Place Order" button inside the
	 * Shopping Bag page
	 * 
	 */
	@FindBy(xpath = "//button[text()='PLACE ORDER']")
//	@FindBy(xpath = "//android.widget.Button[@text='PLACE ORDER']")
	public AndroidElement PlaceOrderBtn;
	
	@FindBy(xpath = "//div[@class='prod-set']/./././/span[text()='REMOVE']")
	public List<AndroidElement> ProductSetList;

	/**
	 * Name: Vinay
	 * 
	 * Description: this xpath will help user to click on size
	 */
	@FindBy(xpath = "//span[@class='mk-custom-drop-down size  ']/span[@class='icon']")
	public AndroidElement SizeBtn;
	/**
	 * object identified for move to bag
	 */
	@FindBy(xpath = "//android.widget.TextView[@resource-id='com.myntra.android:id/ttv_move_to_bag_button']")
	public AndroidElement moveToBag;

	@FindBy(xpath = "//android.widget.RelativeLayout[@resource-id='com.myntra.android:id/rl_size_label']")
	public List<AndroidElement> PopUpSelectSize;
	
	@FindBy(xpath = "//button[@data-count='1']")
	public List<AndroidElement> CartPageSelectSize;

	@FindBy(xpath="//div[text()='Shopping Bag empty']")
//	@FindBy(xpath = "//android.view.View[@text='SHOPPING BAG EMPTY' or @content-desc='SHOPPING BAG EMPTY']")
	public AndroidElement emptyBagMsg;
	/**
	 * Object identified for the remove button displayed on the gift wrap tile
	 */
	@FindBy(xpath="//button[text()='Remove']")
//	@FindBy(xpath = "//android.view.View[@text='SHOPPING BAG EMPTY' or @content-desc='SHOPPING BAG EMPTY']")
	public AndroidElement GiftWrapRemoveBtn;
	/**
	 * Name:Amba
	 * Object identified for the move to Wishlist from Cart Page
	 */
	@FindBy(xpath = "//android.view.View[@text='REMOVEMOVE TO WISHLIST']")
	public AndroidElement MoveToWishlistBtn;
	@FindBy(xpath="//button[text()='Edit Message']")
	public AndroidElement GiftWrapEditMsg;

	/*********** getters ***************/
	/**
	 * Name:Amba
	 * Getter for move to Wishlist from Cart Page
	 */
	public AndroidElement getMoveToWishlistBtn() {
		objAndroidGenericMethods.CheckAndroidElementFound(MoveToWishlistBtn, "MoveToWishlistBtn");
		return MoveToWishlistBtn;
	}

	public AndroidElement getCancelGiftbtn() {
		objAndroidGenericMethods.CheckAndroidElementFound(CancelGiftbtn, "CancelGiftbtn");
		return CancelGiftbtn;
	}

	public AndroidElement getSizebtn() {
		objAndroidGenericMethods.CheckAndroidElementFound(SizeBtn, "sizebtn");
		return SizeBtn;
	}

	public List<AndroidElement> getCartPageSelectSize() {
		objAndroidGenericMethods.CheckAndroidElementsListFound(CartPageSelectSize, "CartPageSelectSize");
		return CartPageSelectSize;
	}

	public AndroidElement getGiftWrapRemoveBtn() {
		objAndroidGenericMethods.CheckAndroidElementFound(GiftWrapRemoveBtn, "giftWrapRemoveBtn");
		return GiftWrapRemoveBtn;
	}

	public AndroidElement getGiftWrapEditMsg() {
		objAndroidGenericMethods.CheckAndroidElementFound(GiftWrapEditMsg, "giftWrapEditMsg");
		return GiftWrapEditMsg;
	}

	/**
	 * getter for move to bag
	 * 
	 * @author 300021278 -Rakesh
	 * 
	 */
	public AndroidElement getMoveToBag() {
		objAndroidGenericMethods.CheckAndroidElementFound(moveToBag, "moveToBag");
		return moveToBag;
	}

	/**
	 * getter for sizes available for product
	 * 
	 * @author 300021278 -Rakesh
	 * @return
	 */
	public List<AndroidElement> getProductSetList() {
		return ProductSetList;
	}
	
	public List<AndroidElement> getPopupSelectSize() {
		objAndroidGenericMethods.CheckAndroidElementsListFound(PopUpSelectSize, "popupSelectSize");
		return PopUpSelectSize;
	}

	public AndroidElement getShoppingBagbtn() {
		objAndroidGenericMethods.CheckAndroidElementFound(ShoppingBagBtn, "shoppingBagbtn");
		return ShoppingBagBtn;
	}

	public AndroidElement getRemoveBtn() {
		objAndroidGenericMethods.CheckAndroidElementFoundForWebView(removeBtn, "removeBtn");
		return removeBtn;
	}

	public AndroidElement getApplyCouponbtn() {
		objAndroidGenericMethods.CheckAndroidElementFoundForWebView(ApplyCouponBtn, "ApplyCouponbtn");
		return ApplyCouponBtn;
	}

	public AndroidElement getApplybtn() {
		objAndroidGenericMethods.CheckAndroidElementFoundForWebView(ApplyBtn, "Applybtn");
		return ApplyBtn; 
	}

	public AndroidElement getGiftWrapbtn() {
		objAndroidGenericMethods.CheckAndroidElementFoundForWebView(GiftWrapBtn, "GiftWrapbtn");
		return GiftWrapBtn;
	}

	/**
	 * Modified by Aishurya: Modified to webView click method
	 */
	public AndroidElement getRecipientNametxt() {
		objAndroidGenericMethods.CheckAndroidElementFoundForWebView(RecipientNameTxt, "RecipientNametxt");
		RecipientNameTxt.clear();
		return RecipientNameTxt;
	}
	/**
	 * Modified by Aishurya: Modified to webView click method
	 */
	public AndroidElement getGiftMessagetxt() {
		objAndroidGenericMethods.CheckAndroidElementFoundForWebView(GiftMessagetxt, "GiftMessagetxt");
		GiftMessagetxt.clear();
		return GiftMessagetxt;
	}
	/**
	 * Modified by Aishurya: Modified to webView click method
	 */
	public AndroidElement getSenderNametxt() {
		objAndroidGenericMethods.CheckAndroidElementFoundForWebView(SenderNameTxt, "SenderNametxt");
		SenderNameTxt.clear();
		return SenderNameTxt;
	}

	public AndroidElement getSaveGiftbtn() {
		objAndroidGenericMethods.CheckAndroidElementFoundForWebView(SaveGiftBtn, "SaveGiftbtn");
		return SaveGiftBtn;
	}

	public AndroidElement getWishListbtn() {
		objAndroidGenericMethods.CheckAndroidElementFound(WishListBtn, "WishListbtn");
		return WishListBtn;
	}

	/**
	 * added correct object in getter last getter was:
	 * objAndroidGenericMethods.CheckAndroidElementFound(shoppingBagbtn,
	 * "shoppingBagbtn")
	 * @author 300021282 VINAY
	 * 
	 */
	public AndroidElement getViewDetailsbtn() {
		objAndroidGenericMethods.CheckAndroidElementFoundForWebView(ViewDetailsBtn, "viewDetailsbtn");
		return ViewDetailsBtn;
	}

	public AndroidElement getPlaceOrderbtn() throws InterruptedException {
		objAndroidGenericMethods.CheckAndroidElementFoundForWebView(PlaceOrderBtn, "PlaceOrderbtn");
		return PlaceOrderBtn;
	}

	public AndroidElement getcrossbtn() {
		objAndroidGenericMethods.CheckAndroidElementFound(CrossBtn, "crossbtn");
		return CrossBtn;
	}

	public AndroidElement getCouponCodebox() {
		objAndroidGenericMethods.CheckAndroidElementFoundForWebView(couponCodeBox, "couponCodebox");
		return couponCodeBox;
	}

	public AndroidElement getCouponCancelBtn() {
		objAndroidGenericMethods.CheckAndroidElementFoundForWebView(couponCancelBtn, "couponCancelbtn");
		return couponCancelBtn;
	}

	public AndroidElement getRemovebtnfromlowerpop() {
		objAndroidGenericMethods.CheckAndroidElementFoundForWebView(PopUpRemoveBtn, "popupremoveBtn");
		return PopUpRemoveBtn;
	}

	/**
	 * getter for the message displayed for empty bag condition
	 * 
	 * @author 300021278 -Rakesh
	 * @return
	 */
	public AndroidElement getEmptyBagMsg() {
		objAndroidGenericMethods.CheckAndroidElementFoundForWebView(emptyBagMsg, "emptyBagMsg");
		return emptyBagMsg;
	}

	/************* Mtehods ******************/
	/**
	 * Name:Amba
	 * Method for  move to Wishlist from Cart Page
	 */
	public AndroidElement clickOnMovetoWishlist() {
		objAndroidGenericMethods.clickOnAndroidElement(getMoveToWishlistBtn(), "MoveToWishlistBtn");
		return MoveToWishlistBtn;
	}
	public void selectSizePopUp() {
		List<AndroidElement> Sizes = getPopupSelectSize();
		for (AndroidElement e : Sizes) {
			List<MobileElement> sizecount = e.findElements(By.xpath("//android.view.View[@resource-id='com.myntra.android:id/view_saparator']"));
			System.out.println("the size of the singlesize is" + sizecount.size());
			if (sizecount.size() == 0) {
				System.out.println("clicked successfully");
				e.click();
				break;
			}
				
			}
		}

	/**
	 * 
	 * All the Required methods are written below please call the method from the
	 * below list Note: If you need to wriite new mehod then please first create the
	 * getter for particular element then create here the new method
	 */

	public AndroidElement clickOncrossbtn() {
		objAndroidGenericMethods.clickOnAndroidElement(getcrossbtn(), "CrossBtn");
		return CrossBtn;
	}

	/* amba-clickSaveGiftbtn() */
	public AndroidElement clickGiftWrapbtn() {
		objAndroidGenericMethods.clickOnAndroidElement(getGiftWrapbtn(), "GiftWrapbtn");
		return GiftWrapBtn;
	}

	public AndroidElement clickSaveGiftbtn() {
		objAndroidGenericMethods.clickOnAndroidElement(getSaveGiftbtn(), "SaveGiftbtn");
		return SaveGiftBtn;
	}

	public AndroidElement clickRecipientNametxt() {
		objAndroidGenericMethods.clickOnAndroidElement(getRecipientNametxt(), "RecipientNameTxtField");
		return RecipientNameTxt;
	}

	public AndroidElement clickGiftMessagetxt() {
		objAndroidGenericMethods.clickOnAndroidElement(getGiftMessagetxt(), "GiftMessageTxtField");
		return GiftMessagetxt;
	}

	public AndroidElement clickSenderNametxt() {
		objAndroidGenericMethods.clickOnAndroidElement(getSenderNametxt(), "SenderNameTxtField");
		return SenderNameTxt;
	}

	public void enterRecipientNametxt() {
		objAndroidGenericMethods.clickOnAndroidElement(getRecipientNametxt(), "EnterRecipientName");
	}

	public void enterGiftMessagetxt() {
		objAndroidGenericMethods.clickOnAndroidElement(getGiftMessagetxt(), "Enter GiftMessageTxt Field");
	}

	public void enterSenderNametxt() {
		objAndroidGenericMethods.clickOnAndroidElement(getSenderNametxt(), "Enter SenderName Txt Field");
	}

	public void clickShoppingBag() {
		objAndroidGenericMethods.clickOnAndroidElement(getShoppingBagbtn(), "Shooping Bag");
	}

	public void clickWishList() {
		objAndroidGenericMethods.clickOnAndroidElement(getWishListbtn(), "WishList");
	}
	/**
	 * First this method switch into the webview and then click on view details and
	 * again it will swith into native view
	 * 
	 * @author 300021282 VINAY
	 */
	public void clickViewDetails() throws InterruptedException {
		objAndroidGenericMethods.clickOnAndroidElementforwebVIew(getViewDetailsbtn(), "Clicked on View Details");
	}

	/**
	 * Method to click on place order displayed in cart page;this method inculdes switch into the webview and then click on place order and
	 * again it will swith into native view
	 * 
	 * @author 300021282 VINAY
	 */
	public void clickPlaceOrder() throws InterruptedException { 
		objAndroidGenericMethods.clickOnAndroidElementforwebVIew(getPlaceOrderbtn(), "Place Order");
	}
	/**
	 * First this method switch into the webview and then click on Apply coupon and
	 * again it will switch into native view
	 * 
	 * @author 300021282 VINAY
	 * @throws InterruptedException 
	 */
	public void clickApplyCoupon() throws InterruptedException {
		objAndroidGenericMethods.clickOnAndroidElementforwebVIew(getApplybtn(), "ApplyCouponBtn");
		Thread.sleep(4000);

	}

	public AndroidElement clickCrossbtn() {
		objAndroidGenericMethods.clickOnAndroidElement(getcrossbtn(), "CrossBtn");
		return CrossBtn;
	}

	public void enterCouponCodebox(String couponcode) {
		objAndroidGenericMethods.clickOnAndroidElement(getCouponCodebox(), "CouponCodeBox");
		getCouponCodebox().sendKeys(couponcode);
	}

	public void clickRemoveButton() {
		objAndroidGenericMethods.clickOnAndroidElementforwebVIew(getRemoveBtn(), "Remove Button");
		
	}
	/**
	 * Method to remove one item from the cart page;
	 * Note===>> Method inculdes switching of webview and switching back to native view
	 * @throws InterruptedException
	 * @author 300021280 Sneha
	 */
	public void RemoveOneItem() throws InterruptedException {
		//objAndroidGenericMethods.swithchInToWebview();
		clickRemoveButton();
		clickRemovebtnfromlowerpop();
		//objAndroidGenericMethods.switchInToNativeView();
	} 

	public void ClickCouponCancelbtn() {
		objAndroidGenericMethods.clickOnAndroidElementforwebVIew(getCouponCancelBtn(),"Cancel button on Applly coupon screen");
	}

	public void clickRemovebtnfromlowerpop() {
		objAndroidGenericMethods.clickOnAndroidElement(getRemovebtnfromlowerpop(),"Remove buttton afetr click on remove");
	}

	public void clickGiftWrap() {
		objAndroidGenericMethods.clickOnAndroidElement(getGiftWrapbtn(), "GiftWrap ");
	}

	
	
	/**
	 * @author 300021280 Sneha
	 * 
	 * method to enter text in Gift card 
	 * 
	 * This Clear method added by subhasis
	 * This method will clear recipient name,message and Sender name in GiftCard before entering text.
	 */
	public void GiftCardtxt(String receipntName, String Message, String senderName) {
//		objAndroidGenericMethods.bottomTopSwipe(2000);
		getRecipientNametxt().clear();
		getRecipientNametxt().sendKeys(receipntName);
		getGiftMessagetxt().clear();
		getGiftMessagetxt().sendKeys(Message);
		getSenderNametxt().clear();
		getSenderNametxt().sendKeys(senderName);
	}

	public void SaveGiftcard() {
		objAndroidGenericMethods.clickOnAndroidElementforwebVIew(getSaveGiftbtn(), "SaveGiftCard ");
	}

	
	/**
	 * This method Help user to click change the size on Addcart page
	 * 
	 * @author 300021282 VINAY
	 * @throws InterruptedException 
	 * 
	 * modified by : sneha 
	 * added switching to web view and then return to native inside the method
	 * 
	 */
	public void changeSize() throws InterruptedException {
		objAndroidGenericMethods.swithchInToWebview();
		aDriver.findElementByClassName("icon").click();
		List<MobileElement> selectSize = aDriver.findElementsByXPath(".//div[@class='sizes']/button");
		for (MobileElement i : selectSize) {
			String att = i.getAttribute("class");
			if (att.equals("btn size-btn-group size-btn  ")) {
				i.click();
				break;
			}
		}
		objAndroidGenericMethods.switchInToNativeView();
	}

	/**
	 * Method to click on wishlist button displayed in header
	 * 
	 * @author 300021278 -Rakesh
	 */
	public void clickWishlistRH() {
		objAndroidGenericMethods.clickOnAndroidElement(getWishListicon(), "WishListIcon");
	}

	/**
	 * Method to click on move to bag button displayed on wishlistpop up;
	 * 
	 * @author 300021278 -Rakesh
	 */
	public void clickMoveToBag() {
		objAndroidGenericMethods.clickOnAndroidElement(getMoveToBag(), "MoveToBag");
	}

	/**
	 * Method to add product displayed on wishlist pop up;
	 * 
	 * @author 300021278 -Rakesh
	 */
	public void addProductToBag() {
		try {
			while (moveToBag.isDisplayed()) {
				clickMoveToBag();
				System.out.println("Added the products");
				selectSizePopUp();
			}
		} catch (Exception e) {
			((PressesKeyCode) aDriver).pressKeyCode(AndroidKeyCode.BACK);
		}
	}
	
	public AndroidElement enterCouponCode(String couponcode) {
		objAndroidGenericMethods.enterTexAndroidElement(getCouponCodebox(), couponcode, "coupon_code");
		return couponCodeBox;
	}

	/**
	 * 
	 * All the Required "ASSERTION" are written below please call the method from
	 * the below list Note: If you need to wriite new "ASSERTION" then please first
	 * create the getter for particular element then create here the new method for
	 * assertion
	 */
	/************* Android Assertion element ***************/

	@FindBy(xpath = "//android.widget.TextView[@text='SHOPPING BAG' or @resource-id='com.myntra.android:id/tv_title']")
	public AndroidElement shoppingBagTitle;

	/**
	 * Wrong Xpath
	 * 
	 * @modified 300021278 -Rakesh
	 */
	@FindBy(xpath = "//android.widget.Button[@resource-id='com.myntra.android:id/tb_wishlist']")
	public AndroidElement wishListicon;
	
	/**
	 * iteam toal assertion 
	 * @author 300021282 VINAY
	 */
	@FindBy(xpath="//span[contains(text(),'Total:')]")
	public AndroidElement itemsTotal;

	public AndroidElement getItemsTotal() {
		objAndroidGenericMethods.CheckAndroidElementFound(itemsTotal, "itemsTotal");
		return itemsTotal;
	}

	public AndroidElement getShoppingBagTitle() {
		objAndroidGenericMethods.CheckAndroidElementFound(shoppingBagTitle, "shoppingBagTitle");
		return shoppingBagTitle;
	}

	public AndroidElement getWishListicon() {
		objAndroidGenericMethods.CheckAndroidElementFound(wishListicon, "wishListicon");
		return wishListicon;
	}

	public void verifyShoppingBagTitle() {
		
		objAndroidGenericMethods.VerifyTwoString(getShoppingBagTitle(), "SHOPPING BAG");
	}

	public void verifyWishlistIcon() {
		objAndroidGenericMethods.VerifyTwoString(getWishListicon(), "WISHLIST");
	}
	/**
	 * this help to assert items total in addcart
	 */
	public void verifyitemsTotalAddCart() {
		objAndroidGenericMethods.swithchInToWebview();
		objAndroidGenericMethods.VerifyStringShouldNotEmpty(getItemsTotal(), "item total");
		objAndroidGenericMethods.switchInToNativeView();
	}

	/**
	 * Method to remove all the products from the bag page; this method switches
	 * into web view and switches back to native view
	 * 
	 * @author 300021278 -Rakesh
	 * @throws InterruptedException
	 */
	public void emptybag() throws InterruptedException {
		objAndroidGenericMethods.scrollDown(removeBtn, 100);
		while (getRemoveBtn().isDisplayed()) {
			getRemoveBtn().click();
			getRemovebtnfromlowerpop().click();
		}
	}
	
	/**
	 * @author Aishurya: added to remove list of bag item
	 * @throws InterruptedException 
	 * @modified by rakesh
	 */
	public void clickRemoveLink() {
		System.out.println("ClickRemovelink() loop");
		List<AndroidElement> removeLinkList=getProductSetList() ;
			System.out.println("number of items "+removeLinkList.size());
			for (int i = 0; i < removeLinkList.size();) {
					try {
						getRemoveBtn().isDisplayed();
						getRemoveBtn().click();
						getRemovebtnfromlowerpop().click();
						System.out.println("Removed product ");
					} catch (Exception e) {
						System.out.println("scrolling for remove product ");
						objAndroidGenericMethods.scrollPagedown(60);
					}
				}
			}
		

	/**
	 * added try catch block
	 * 
	 * @ModifiedBy:-Rakesh
	 * @author 300021278 -Rakesh
	 * @return
	 */
	public boolean isBagEmpty() {
		try {
			getEmptyBagMsg();
			System.out.println("Cart is empty");
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Method to reset bag data
	 * 
	 * @author 300021278 -Rakesh
	 * @throws InterruptedException
	 * @ModifiedBy:-Rakesh added switch methods for proper flow
	 * 
	 * @ModifiedBy: Sneha added scroll, remove button is not visble in devices with less screen size
	 * Modified By Aishurya: Repaced emptybag() by ClickRemoveLink()
	 */
	public void resetBag() throws InterruptedException {
		objHomePageObject.clickBagbtn(); 
		objProductListPageObject.clickOk();
		try {
			System.out.println("entered reset try block");
			objAndroidGenericMethods.swithchInToWebview();
			clickRemoveLink();
			//aDriver.navigate().back();
			clickOncrossbtn();
		} catch (Exception e) {
			System.out.println("entered reset catch block");
			 clickOncrossbtn();
			//aDriver.navigate().back();
	}
	}
		
	public void selectGiftWrap() {
		
	}
	
	/**
	 * @author 300021280 Sneha
	 * method to scroll and click on Apply Coupon
	 */
//	public void scrollToApplyCouponNClick() {
//		MobileElement element = aDriver.findElement(MobileBy.AndroidUIAutomator(
//				"new UiScrollable(new UiSelector().className(\"android.webkit.WebView\")).getChildByText("
//				+ "new UiSelector().className(\"android.view.View\"), \"Apply Coupon\")"));
//		element.click();
//	}

}
