package com.automation.mobile.Android.MobileWeb.ObjectRepository.Bag;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;
import com.automation.core.mobile.Android.AndroidGenericMethods;
import com.automation.mobile.Android.MobileWeb.ObjectRepository.HomeObjects.HomePageObjects;
import com.automation.mobile.Android.MobileWeb.ObjectRepository.HomeObjects.MenuPageObjects;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class BagPageObjects {

	AndroidGenericMethods objAndroidGenericMethods;
	HomePageObjects objHomepageObjects;
	MenuPageObjects objMenupageObjects;
	AndroidDriver<AndroidElement> aDriver;

	public BagPageObjects(AndroidDriver<AndroidElement> aDriver) {
		PageFactory.initElements(new AppiumFieldDecorator(aDriver), this);
		this.aDriver = aDriver;
		objAndroidGenericMethods = new AndroidGenericMethods(aDriver);
		objHomepageObjects = new HomePageObjects(aDriver);
		objMenupageObjects = new MenuPageObjects(aDriver);
	}

	/*
	 * Move to Wishlist
	 */
	@FindBy(xpath = "//span[text()='MOVE TO WISHLIST']")
	public AndroidElement MoveToWishlist;

	@FindBy(xpath = "//a[@class='apply-coupon']//span[contains(text(),'Apply Coupon')]")
	public AndroidElement Coupon;

	@FindBy(xpath = "//a[@class='apply-coupon']/span[text()='Apply Coupon']")
	public AndroidElement ApplyCouponButton;

	@FindBy(xpath = "//button[text()='PLACE ORDER']")
	public AndroidElement placeOrder;
	/*
	 * Modified by :Amba
	 */
	@FindBy(xpath = "//*[contains(text(),'Gift wrap for Rs 25')]")
	public AndroidElement GiftWrap;
	/*
	 * Continue Button
	 */

	@FindBy(xpath = "//button[text()='continue']")
	public AndroidElement continueButton;

	@FindBy(xpath = "//button/div[contains(text(),'DONE')]")
	public AndroidElement DoneButton;

	@FindBy(xpath = "//a[text()='GO TO WISHLIST']")
	public AndroidElement gotoWishListlnk;

	/**
	 * Object identified for RecipientName displayed in gift wrap tile
	 * 
	 * @author 300021278 -Rakesh
	 */
	@FindBy(xpath = "(//input[@class='m-textbox'])[1]")
	public AndroidElement RecipientName;

	/**
	 * Object identified for GiftMsg displayed in gift wrap tile
	 * 
	 * @author 300021278 -Rakesh
	 */
	@FindBy(xpath = "//textarea[@id='gift-msg']")
	public AndroidElement GiftMsg;
	/**
	 * Object identified for SenderName displayed in gift wrap tile
	 * 
	 * @author 300021278 -Rakesh
	 */
	@FindBy(xpath = "//input[@id='gift-from']")
	public AndroidElement SenderName;
	/**
	 * Object identified for Save GiftWrap displayed in gift wrap tile
	 * 
	 * @author 300021278 -Rakesh
	 */
	@FindBy(xpath = ".//*[text()='Save']")
	public AndroidElement SaveGiftWrap;
	/**
	 * object identified for view details button displayed in cart page
	 */
	/**
	 * Modified by : Anu
	 * Description : 'View details' locator was wrong
	 */
	@FindBy(xpath = ".//*[text()='VIEW DETAILS']")
	public AndroidElement viewDetails;

	@FindBy(xpath = "//button[@class='mobile edit-giftcard c-blue link-tappable']")
	public AndroidElement EditGiftWrap;

	@FindBy(xpath = "//button[text()='Remove']")
	public AndroidElement RemoveGiftWrap;

	@FindBy(xpath = "//span[@class='mobile-itemCount mobile-melon']")
	public AndroidElement viewMyBag;

	@FindBy(xpath = "//button[@class='btn primary-btn btn-apply m-button c-white clickable']")
	public AndroidElement ApplyButtonForCoupon;

	@FindBy(xpath = "//div[@class='prod-set']/./././/span[text()='REMOVE']")
	public List<AndroidElement> ProductSetList;

	@FindBy(xpath = "//span[text()='REMOVE']")
	public AndroidElement Removelink;

	@FindBy(xpath = "//button[text()='REMOVE']")
	public AndroidElement Remove;
	/*
	 * Add more from wishlist
	 * 
	 */
	@FindBy(xpath = "//span[text()='Add more from wishlist']")
	public AndroidElement addMoreFromWishlist;

	@FindBy(xpath = "//div[text()='Bag']")
	public AndroidElement bagPageTitle;

	@FindBy(xpath = "//div[text()='Delivery']")
	public AndroidElement deliveryPageTitle;

	@FindBy(xpath = "//div[text()='Payment']")
	public AndroidElement paymentPageTitle;
	
	@FindBy(xpath = "//div[contains(text(),'Your order will arrive gift wrapped')]")
	public AndroidElement giftWrapMsg;
	/**
	 * object to verify the product price details of the product available in the bag
	 * @author 300021278 -Rakesh
	 */
	@FindBy(xpath = "//div[contains(@class,'order-summary-span') and contains(@class,'br-b-grey')]/div")
	public List<AndroidElement> verifyPriceDetails;
	
	/**
	 * object to verify the product price details of the product available in the bag
	 * @author 300021278 -Rakesh
	 */
	@FindBy(xpath = "//div[contains(@class,'b-white') and contains(@class,'row') and contains(@class,'prod-item')]")
	public List<AndroidElement> verifyProductCount;
	
	@FindBy(xpath = "//div[contains(@class,'row') and contains(@class,'br-b-grey') and contains(text(), 'OPTIONS')]")
	public AndroidElement optionsHeader;
	
	

	// >>>>>>>>>>>>>>>>>>>>GETTERS<<<<<<<<<<<<<<<<<<<<<//
	
	/**
	 * getter to verify the products details of the product available in the bag
	 * @author 300021278 -Rakesh
	 */
	public List<AndroidElement> getVerifyProductCount() {
		objAndroidGenericMethods.CheckAndroidElementsListFoundMWeb(verifyProductCount, "verifyProductCount");
		return verifyProductCount;
	}
	/**
	 * getter to verify the product price details of the product available in the bag
	 * @author 300021278 -Rakesh
	 */
	public List<AndroidElement> getVerifyPriceDetails() {
		objAndroidGenericMethods.CheckAndroidElementsListFoundMWeb(verifyPriceDetails, "verifyPriceDetails");
		return verifyPriceDetails;
	}
	

	public AndroidElement getEditGiftWrap() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(EditGiftWrap, "EditGiftWrap");
		return EditGiftWrap;
	}

	public AndroidElement getRemoveGiftWrap() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(RemoveGiftWrap, "RemoveGiftWrap");
		return RemoveGiftWrap;
	}

	public AndroidElement getAddMoreFromWishlist() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(addMoreFromWishlist, "addMoreFromWishlist");
		return addMoreFromWishlist;
	}

	public AndroidElement getRemove() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(Remove, "Remove");
		return Remove;
	}

	public AndroidElement getRemovelink() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(Removelink, "Removelink");
		return Removelink;
	}

	public List<AndroidElement> getProductSetList() {
		return ProductSetList;
	}

	public AndroidElement getEmptyCart() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(RecipientName, "RecipientName");
		return EmptyCart;
	}

	public AndroidElement getApplyButtonForCoupon() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(RecipientName, "RecipientName");
		return ApplyButtonForCoupon;
	}

	public AndroidElement getRecipientName() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(RecipientName, "RecipientName");
		return RecipientName;
	}

	public AndroidElement getGiftMsg() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(GiftMsg, "GiftMsg");
		return GiftMsg;
	}

	public AndroidElement getSenderName() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(SenderName, "SenderName");
		return SenderName;
	}

	public AndroidElement getSaveGiftWrap() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(SaveGiftWrap, "SaveGiftWrap");
		return SaveGiftWrap;
	}

	public AndroidElement getGiftWrap() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(GiftWrap, "GiftWrap");
		return GiftWrap;
	}

	public AndroidElement getViewDetails() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(viewDetails, "ViewDetails");
		return viewDetails;
	}

	public AndroidElement getViewMyBag() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(viewMyBag, "viewMyBag");
		return viewMyBag;
	}

	public AndroidElement getBagPageTitle() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(bagPageTitle, "bagPageTitle");
		return bagPageTitle;
	}

	public AndroidElement getDeliveryPageTitle() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(deliveryPageTitle, "deliveryPageTitle");
		return deliveryPageTitle;
	}

	public AndroidElement getPaymentPageTitle() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(paymentPageTitle, "paymentPageTitle");
		return paymentPageTitle;
	}

	public AndroidElement getGotoWishListlnk() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(gotoWishListlnk, "gotoWishListlnk");
		return gotoWishListlnk;
	}

	public AndroidElement getDoneButton() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(DoneButton, "DoneButton");
		return DoneButton;
	}

	public AndroidElement getContinueButton() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(continueButton, "continueButton");
		return continueButton;
	}

	public AndroidElement getMoveToWishlist() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(MoveToWishlist, "MoveToWishlist");
		return MoveToWishlist;
	}

	public AndroidElement getPlaceOrder() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(placeOrder, "placeOrder");
		return placeOrder;
	}

	public AndroidElement getCoupon() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(Coupon, "Coupon");
		return Coupon;
	}

	public AndroidElement getApplyCouponButton() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(ApplyCouponButton, "ApplyCouponButton");
		return ApplyCouponButton;
	}

	// >>>>>>>>>>>>>>>>>>>>>METHODS>>>>>>>>>>>>>>>>>>>>>>>>/
	/**
	 * Method to verify the product details of the product available in the bag
	 * @author 300021278 -Rakesh
	 */
	public void verifyProduct() {
		objAndroidGenericMethods.scrollPagedown(-300);
		List<AndroidElement> searchResultList = getVerifyProductCount();
		for (AndroidElement result : searchResultList) {
			AndroidElement label = (AndroidElement) result.findElement(By.xpath("//div[@class='col2']/div[@class='prod-name']"));
			String productDetails = label.getText();
			System.out.println("The product details contains " + productDetails);
			Reporter.log("The product details contains " + productDetails);
		}
	}
	/**
	 * Method to verify the product price details of the product available in the bag
	 * @author 300021278 -Rakesh
	 */
	public void verifyPriceDetails() {
		List<AndroidElement> searchResultList = getVerifyPriceDetails();
		for (AndroidElement result : searchResultList) {
			AndroidElement label = (AndroidElement) result.findElement(By.tagName("label"));
			AndroidElement labelCount = (AndroidElement) result.findElement(By.tagName("span"));
			String priceDetails = label.getText();
			String priceDetailsCount = labelCount.getText();
			//String priceDetailsCount = labelCount.getText();
			System.out.println("Bag price Details contains " + priceDetails + " == " + priceDetailsCount );
			Reporter.log("Bag price Details contains " + priceDetails + " == " + priceDetailsCount);

		}
	}
	

	/**
	 * This method Help user to click change the size on Addcart page
	 * 
	 * @author 300021282 VINAY
	 * @throws InterruptedException
	 */
	public void changeSize() {
		aDriver.findElementByClassName("icon").click();
		List<AndroidElement> selectSize = aDriver.findElementsByXPath(".//div[@class='sizes']/button");
		for (AndroidElement i : selectSize) {
			String att = i.getAttribute("class");
			if (att.equals("btn size-btn-group size-btn  ")) {
				i.click();
				break;
			}
		}
	}

	/**
	 * Modified By:Aishurya:Changed string parameter,which is being used in reporing
	 */
	public void enterRecipientName(String RecipientName) {
		objAndroidGenericMethods.clickOnAndroidElement(getRecipientName(), "RecipientName");
		getRecipientName().clear();
		getRecipientName().sendKeys(RecipientName);
	}

	/**
	 * Modified By:Aishurya:Changed string parameter,which is being used in reporing
	 */
	public void enterGiftMsg(String GiftMsg) {
		objAndroidGenericMethods.clickOnAndroidElement(getGiftMsg(), "GiftMsg");
		getGiftMsg().clear();
		getGiftMsg().sendKeys(GiftMsg);
	}

	/**
	 * Modified By:Aishurya:Changed string parameter,which is being used in reporing
	 */
	public void enterSenderName(String SenderName) {
		objAndroidGenericMethods.clickOnAndroidElement(getSenderName(), "SenderName");
		getSenderName().clear();
		getSenderName().sendKeys(SenderName);
	}

	/**
	 * Modified By:Aishurya:Changed string parameter,which is being used in reporing
	 */
	public void clickOnviewMyBag() {
		objAndroidGenericMethods.clickOnAndroidElement(getViewMyBag(), "ViewMyBag");
	}

	/**
	 * Modified By:Aishurya:Changed string parameter,which is being used in reporing
	 * @throws InterruptedException 
	 */
	public void clickOnSaveGiftWrap()  {
		objAndroidGenericMethods.clickOnAndroidElement(getSaveGiftWrap(), "SaveGiftWrap");
	}

	/**
	 * Modified:Amba Modified By:Aishurya:Changed string parameter,which is being
	 * used in reporting
	 */

	/**
	 * Modified by : Anu
	 * Description : Modified the string to be printed in reporting. And calling IfGiftwrapadded() from clickongiftWrap() to make the method in proper flow
	 */
	public void clickOnGiftWrap() {
		try {
			if (IfGiftWrapAdded()) {
				objAndroidGenericMethods.clickOnAndroidElement(getGiftWrap(), "GiftWrap");
			}
		} catch (Exception e) {
			System.out.println("New Giftwrap is adding ...");

		}

	}

	/**
	 * This method is for IfGiftWrapAdded
	 * 
	 * @author 300019225 Amba;
	 */

	public boolean IfGiftWrapAdded() {
		try {
			if (EditGiftWrap.isDisplayed()) {
				objAndroidGenericMethods.clickOnAndroidElement(getRemoveGiftWrap(), "GiftWrap removed");
			}
		} catch (Exception e) {
			System.out.println("No existing Giftwrap!!");
		}
		return true;

	}
	/**
	 * method to check and add gift wrap 
	 * @author 300021278 -Rakesh
	 * Modified By Aishurya:scrollDownwithout click is not working
	 */
	public void checkGiftWrap() {
		objAndroidGenericMethods.scrollDown(optionsHeader, 500);
		IfGiftWrapAdded();
		clickOnGiftWrap();
		enterRecipientName("Test");
		enterGiftMsg("Test Message");
		enterSenderName("Tester");
		clickOnSaveGiftWrap();
	}

	/**
	 * Modified By:Aishurya:Changed string parameter,which is being used in reporing
	 */
	public void clickOnDoneButton() {
		objAndroidGenericMethods.clickOnAndroidElement(getDoneButton(), "DoneButton");
	}

	/**
	 * Modified By:Aishurya:Changed string parameter,which is being used in reporing
	 */
	public void clickOnMoveToWishlist() {
		objAndroidGenericMethods.clickOnAndroidElement(getMoveToWishlist(), "MoveToWishlist");
	}

	/**
	 * Modified By:Aishurya:Changed string parameter,which is being used in reporing
	 */
	public void clickOnPlaceOrder(){
		objAndroidGenericMethods.clickOnAndroidElement(getPlaceOrder(), "PlaceOrder");
	}

	/**
	 * Modified By:Aishurya:Changed string parameter,which is being used in reporing
	 */
	public void SelectCoupon() {
		objAndroidGenericMethods.clickOnAndroidElement(getCoupon(), "Coupon");
	}

	/**
	 * Modified By:Aishurya:Changed string parameter,which is being used in reporing
	 */
	public void clickOnApplyCouponButton() {
		objAndroidGenericMethods.clickOnAndroidElement(getApplyCouponButton(), "ApplyCouponButton");
	}

	/**
	 * Modified By:Aishurya:Changed string parameter,which is being used in reporing
	 */
	public void clickOnContinueButton() {
		objAndroidGenericMethods.clickOnAndroidElement(getContinueButton(), "ContinueButton");
	}

	/**
	 * Modified By:Aishurya:Changed string parameter,which is being used in reporing
	 */
	public void ClickOnGotoWishlist() {
		objAndroidGenericMethods.clickOnAndroidElement(getGotoWishListlnk(), "GotoWishListlnk");
	}

	public void assertBagPageTitle(String pageName) {
		AndroidElement element = (AndroidElement) getBagPageTitle();
		// System.out.println("-->" + element.getText());
		objAndroidGenericMethods.VerifyTwoString(element, pageName);
	}

	public void assertDeliveryPageTitle(String pageName){
		AndroidElement element = (AndroidElement) getDeliveryPageTitle();
		// System.out.println("-->" + element.getText());
		objAndroidGenericMethods.VerifyTwoString(element, pageName);
	}

	public void assertPaymentPageTitle(String pageName) {
		AndroidElement element = (AndroidElement) getPaymentPageTitle();
		// System.out.println("-->" + element.getText());
		objAndroidGenericMethods.VerifyTwoString(element, pageName);
	}
	/**
	 * Method to click on view details displayed in cart page
	 * @ModifiedBy:-Rakesh Added getter
	 * @throws InterruptedException
	 */
	public void clickOnViewDetails() {
		//getViewDetails().click();
		objAndroidGenericMethods.clickOnAndroidElement(getViewDetails(), "ViewDetails");
	}
	/**
	 * Method to click on apply button displayed in cart page
	 * @ModifiedBy:-Rakesh Added getter
	 * @throws InterruptedException
	 */
	public void ClickOnApplyButtonForCoupon(){
		objAndroidGenericMethods.clickOnAndroidElement(getApplyButtonForCoupon(), "ApplyButton");
	}

	/**
	 * Modified By:Aishurya:Changed string parameter,which is being used in reporing
	 */
	public void clickToAddMoreFromWishlist() {
		objAndroidGenericMethods.clickOnAndroidElement(getAddMoreFromWishlist(), "Add More from Wishlist");
	}

	/**
	 * This method is to reset cart page
	 * 
	 * @author 300019227 Anu;
	 */
	/**
	 * Modified by :Anu
	 * 
	 * Enabled scroll and click on 'Remove' link since remove link is not visible without scroll down if screen size is small
	 */
	public void ClickRemoveLink() {
		List<AndroidElement> removeLinkList = getProductSetList();
		{
			for (int i = 0; i < removeLinkList.size(); i++) {
				objAndroidGenericMethods.scrollDown(getRemovelink(), 20);
				// getRemovelink().click();
				getRemove().click();
			}
		}
	}

	
	/**
	 * Modified by :Anu Description: Corrected the locator since it was not
	 * identifying the Myntra logo in Bag page
	 */
	@FindBy(className = "m_logo")
	public AndroidElement myntraLogo;

	public AndroidElement getmyntraLogo() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(myntraLogo, "myntraLogo");
		return myntraLogo;
	}

	/**
	 * Modified By:Aishurya:Changed string parameter,which is being used in reporing
	 */
	public void clickOnMyntraLogo() {
		objAndroidGenericMethods.clickOnAndroidElement(getmyntraLogo(), "Myntra Logo.");
	}
	/**
	 * Modified By:Anu:Changed xpath for 'EmptyCart'.
	 */
	@FindBy(xpath = "//div[text()='Shopping Bag empty']")
	public AndroidElement EmptyCart;

	public AndroidElement getEmptyBag() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(EmptyCart, "EmptyCart");
		return EmptyCart;
	}

	public boolean isBagEmpty() {
		try {
			getEmptyBag().getText();
			return true;
		} catch (Exception e) {
			System.out.println("Cart has items");
			return false;
		}
	}

	/**
	 * Modified by - Anu Modified to remove more than one product presented in Cart
	 */
	public void resetBag() {

		try {
			objHomepageObjects.clickOnBagIcon();
			if (isBagEmpty()) {
				clickOnMyntraLogo();
			} else {
				ClickRemoveLink();
				objAndroidGenericMethods.backKeyButton();
			}
		} catch (Exception e) {
		}
	}

	@FindBy(className = "prod-name")
	public AndroidElement productTitle;

	@FindBy(xpath = "//div[@class='amount red']/div")
	public AndroidElement sellingPrice;

	@FindBy(xpath = "//div[@class='amount red']/span/span[1]")
	public AndroidElement strikedPrice;

	@FindBy(className = "col1")
	public AndroidElement productImage;

	public AndroidElement getProductTitle() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(productTitle, "productTitle");
		return productTitle;
	}

	public AndroidElement getSellingPrice() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(sellingPrice, "sellingPrice");
		return sellingPrice;
	}

	public AndroidElement getStrikedPrice() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(strikedPrice, "strikedPrice");
		return strikedPrice;
	}

	public AndroidElement getProductImage() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(productImage, "productImage");
		return productImage;
	}

	public void VerifyProductTitle() {
		objAndroidGenericMethods.VerifyStringShouldNotEmpty(getProductTitle(), "Product Title");
	}

	public void VerifySellingPrice() {
		objAndroidGenericMethods.VerifyStringShouldNotEmpty(getSellingPrice(), "Selling Price");
	}

	public void VerifyStrikedPrice() {
		objAndroidGenericMethods.VerifyStringShouldNotEmpty(getStrikedPrice(), "Striked Price");
	}

	public void VerifyDiscountedPrice() {
		objAndroidGenericMethods.VerifyStringShouldNotEmpty(getSellingPrice(), "Discounted Price");
	}

	public void ClickOnAddMoreFromWishlistLink() {
		getAddMoreFromWishlist().click();
	}

	public void EnterCouponCode(String EnterCouponCode) {
		getEnterCouponCode().click();
		getEnterCouponCode().sendKeys(EnterCouponCode);
	}

	public AndroidElement getEnterCouponCode() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(EnterCouponCode, "EnterCouponCode");
		return EnterCouponCode;
	}

	/**
	 * Modified By:Aishurya:Changed string parameter,which is being used in reporing
	 */
	public void clickOnCancelButton() {
		objAndroidGenericMethods.clickOnAndroidElement(getCancelButton(), "Cancel Button.");
	}

	public AndroidElement getCancelButton() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(CancelButton, "CancelButton");
		return CancelButton;
	}

	@FindBy(xpath = ".//*[text()='Cancel']")
	public AndroidElement CancelButton;
	@FindBy(xpath = "//input[@class='enter-coupon m-textbox']")
	public AndroidElement EnterCouponCode;

	// added by sangam
	public void ChangeQuantity() {
		getQtyDrpdwnicon().click();
		List<AndroidElement> quantityList = getQtyInsideDrpdwn();
		int s = quantityList.size();
		System.out.println("size is:" + s);
		AndroidElement selectQuantity = aDriver
				.findElement(By.xpath("(//div[@class='quantity']/button)[" + (s - 1) + "]"));
		selectQuantity.click();
		String selectedSize = selectQuantity.getText();

		if (Integer.parseInt(selectedSize) == s - 1) {
			Reporter.log("Passed : Quantity of products is changed to: " + selectedSize + " in cart page!");
		} else {
			Reporter.log("Failed : Unable to change the Quantity of products in cart page!");
		}
	}

	public AndroidElement getQtyDrpdwnicon() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(qtyDrpdwnicon, "qtyDrpdwnicon");
		return qtyDrpdwnicon;
	}

	public List<AndroidElement> getQtyInsideDrpdwn() {
		objAndroidGenericMethods.CheckAndroidElementsListFoundMWeb(qtyInsideDrpdwn, "qtyInsideDrpdwn");
		return qtyInsideDrpdwn;
	}

	@FindAll(@FindBy(xpath = "//div[@class='quantity']/button[contains(@class,'sel-qty qty-btn-group')]"))
	public List<AndroidElement> qtyInsideDrpdwn;
	@FindBy(xpath = "//span[@class='mk-custom-drop-down qty  ']/span[@class='icon']")
	public AndroidElement qtyDrpdwnicon;

	@FindBy(xpath = ".//*[text()='Apply']")
	public AndroidElement ApplyButton;

	public AndroidElement getApplyButton() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(ApplyButton, "ApplyButton");
		return ApplyButton;
	}

	/**
	 * @author 300019221 Aravindanth Added try block and if condition. Java Script
	 *         executor method is implemented for click
	 */
	public void clickOnApplyButton() {
		try {
			if (getApplyButton().isDisplayed()) {
				getApplyButton().click();
				// objiOSGenericMethods.clickOnIOSElement(getApplyButton(), "clicked on Apply
				// Button.");
			}
		} catch (Exception e) {
		}
	}
	/**
	 * Created by :Anu
	 * Description : Verification method for no. of products added in cart, along with product name and product ID
	 */
	/**
	 * Modified by :Anu
	 * Description : Modified method to print product code
	 */
	public void VerfiyProductIsAddedToCart()	{

		try {

			if (getRemovelink().getText() != null)

			{

				int productNumber = getProductSetList().size();

				Reporter.log("Passed : "+productNumber+" Product is present in Cart!");

				for (int i=1; i<= productNumber;)

				{

					AndroidElement productName = aDriver.findElement(By.xpath("(//div[@class='prod-name'])["+ i + "]"));

					String ProductName = productName.getText();

					AndroidElement productcode = aDriver.findElement(By.xpath("(//div[contains(@id,'prod-item-')])["+ i + "]"));

					MobileElement aTag =productcode.findElement(By.tagName("a"));

					String str=aTag.getAttribute("href");
					String[] href=str.split("/");

					String ProductCode =href[6];

					Reporter.log("Passed : Product "+i+" added to the Cart is "+ProductName+" and it's product code is "+ProductCode+" !");

					i++;

				}

			}

		} catch (Exception e) {

			Reporter.log("Failed : No Products are present in the Cart to place Order!");

		}

	}


}
