package com.automation.mobile.iOS.MobileWeb.ObjectRepository.Bag;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;

import com.automation.core.mobile.iOS.iOSGenericMethods;
import com.automation.mobile.iOS.MobileWeb.ObjectRepository.HomeObjects.*;

import io.appium.java_client.MobileElement;
import io.appium.java_client.PressesKeyCode;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class BagPageObjects {

	public IOSDriver<IOSElement> iDriver;
	iOSGenericMethods objiOSGenericMethods;
	HomePageObjects objHomePageObjects;

	public BagPageObjects(IOSDriver<IOSElement> iDriver) {
		PageFactory.initElements(new AppiumFieldDecorator(iDriver), this);
		objiOSGenericMethods = new iOSGenericMethods(iDriver);
		objHomePageObjects = new HomePageObjects(iDriver);
		this.iDriver = iDriver;
	}
	/*
	 * home screen BAG icon
	 */

	@FindBy(xpath = "//span[@class=\"mobile-itemCount mobile-melon\"]")
	public IOSElement bagIcon;

	/*
	 * Move to Wishlist
	 * .//*[text()='MOVE TO WISHLIST']
	 */
	@FindBy(xpath = "//div[@class='edit-move-delete']//span[text()='MOVE TO WISHLIST']")
	public IOSElement MoveToWishlist;

	@FindBy(xpath = "//a[@class='apply-coupon']//span[contains(text(),'Apply Coupon')]")
	public IOSElement Coupon;

	@FindBy(xpath = "//input[@class='enter-coupon m-textbox']")
	public IOSElement EnterCouponCode;

	@FindBy(xpath = "//span[@class='label apply-coupon loggedin coupon-label']")
	public IOSElement ApplyCouponButton;

	@FindBy(xpath = ".//*[text()='Apply']")
	public IOSElement ApplyButton;

	@FindBy(xpath = ".//*[text()='Cancel']")
	public IOSElement CancelButton;

	@FindBy(xpath = ".//*[text()='PLACE ORDER']")
	public IOSElement placeOrder;

	/**
	 * Modified xpath by subhasis 
	 */
//	@FindBy(xpath="//div[@id='giftwrap_ae99da31-7769-4fc5-8574-e6e018393f40' and @class='gift-wrapper']")
//	@FindBy(xpath = ".//div[contains(text(),'Gift wrap')]")
	@FindBy(xpath=".//div[contains(text(),'Gift wrap for Rs 25')]")
	public IOSElement GiftWrap;

	@FindBy(xpath = "//a[@class='wishlist-link br-b-grey']")
	public IOSElement addMoreFromWishlist;
	/*
	 * Continue Button
	 */

	@FindBy(xpath = "//button[contains(text(),'continue')]")
	public IOSElement continueButton;

	@FindBy(xpath = "//button/div[contains(text(),'DONE')]")
	public IOSElement DoneButton;
	
	/**
	 * Modified by Subhasis
	 * "//a[@class='empty-wishlist-link c-blue fw-500 clickable']"
	 */

	@FindBy(xpath = "//a[text()='GO TO WISHLIST']")
	public IOSElement gotoWishListlnk;

	/*
	 * Gift Wrap
	 */

	/**
	 * Modified By subhasis
	 */
//	@FindBy(xpath = "//input[@class='m-textbox']")
	
	@FindBy(xpath="//input[@type='text' and @class='m-textbox']")
	public IOSElement RecipientName;

//	@FindBy(xpath = "//textarea[@id='gift-msg']")
	@FindBy(xpath="//textarea[@id='gift-msg' and @class='m-textbox']")
	public IOSElement GiftMsg;

//	@FindBy(xpath = "//input[@id='gift-from']")
	@FindBy(xpath="//input[@id='gift-from' and @class='m-textbox']")
	public IOSElement SenderName;

	@FindBy(xpath = ".//*[text()='Save']")
	public IOSElement SaveGiftWrap;
	
	@FindBy(xpath=".//div[contains(text(),'Gift Wrap Details')]")
	public IOSElement giftWrapDetails;

	/*
	 * view my bag
	 * 
	 */

	@FindBy(xpath = "//span[@class='mobile-itemCount mobile-melon']")
	public IOSElement viewMyBag;

	@FindBy(xpath = "//div[text()='Bag']")
	public IOSElement bagPageTitle;

	@FindBy(xpath = "//div[text()='Delivery']")
	public IOSElement deliveryPageTitle;

	@FindBy(xpath = "//div[text()='Payment']")
	public IOSElement paymentPageTitle;

	@FindBy(xpath = "//span[@class='confirm-delete-item']/span")
	public IOSElement RemoveProduct;

	@FindBy(xpath = "//button[@class='btn primary-btn btn-remove m-button']")
	public IOSElement Remove;
	
	/**
	 * Created By Subhasis
	 * This method will help user to move product to wishlist page from cart.
	 */
	
	@FindBy(xpath="//button[@class='btn primary-btn btn-move m-button']")
	public IOSElement moveToWishlistPopup;
	
	@FindBy(id="demoid")
	public IOSElement demoid;

	@FindBy(className = "m_logo")
	// @FindBy(xpath="//a[@href='https://www.myntra.com/']")
	public IOSElement myntraLogo;

	@FindBy(xpath = "//div[text()='Your Shopping Bag is empty']")
	public IOSElement EmptyCart;

	@FindBy(xpath = "//div[contains(@id,'prod-item-')]/div[1]")
	public List<IOSElement> ProductSetList;

	@FindBy(xpath = "//span[text()='REMOVE']")
	public IOSElement Removelink;

	@FindBy(className = "prod-name")
	public IOSElement productTitle;

	// @FindBy(className = "c-dblue total-rupees")
	@FindBy(xpath = "//div[@class='amount red']/div")
	public IOSElement sellingPrice;

	// @FindBy(className = "strike gray original-amount")
	@FindBy(xpath = "//div[@class='amount red']/span/span[1]")
	public IOSElement strikedPrice;

	@FindBy(className = "col1")
	public IOSElement productImage;

	/*
	 * view details
	 */

	@FindBy(xpath = ".//*[text()='VIEW DETAILS']")
	public IOSElement ViewDetails;

	@FindAll(@FindBy(xpath = "//div[@class='quantity']/button[contains(@class,'sel-qty qty-btn-group')]"))
	public List<IOSElement> qtyInsideDrpdwn;

	@FindBy(xpath = "//span[@class='mk-custom-drop-down qty  ']/span[@class='icon']")
	public IOSElement qtyDrpdwnicon;

	@FindBy(xpath = "//span[@class='mk-custom-drop-down size  ']/span[@class='icon']")
	public IOSElement sizeDrpdwnicon;

	@FindAll({ @FindBy(xpath = "//div[@class='sizes']/button") })
	public List<IOSElement> selectSize;

	/***************** getters *********************/

	public IOSElement getProductTitle() {
		objiOSGenericMethods.CheckIOSElementFound(productTitle, "productTitle");
		return productTitle;
	}

	public IOSElement getSellingPrice() {
		objiOSGenericMethods.CheckIOSElementFound(sellingPrice, "sellingPrice");
		return sellingPrice;
	}

	public IOSElement getStrikedPrice() {
		objiOSGenericMethods.CheckIOSElementFound(strikedPrice, "strikedPrice");
		return strikedPrice;
	}

	public IOSElement getProductImage() {
		objiOSGenericMethods.CheckIOSElementFound(productImage, "productImage");
		return productImage;
	}

	public IOSElement getRemoveProduct() {
		objiOSGenericMethods.CheckIOSElementFound(RemoveProduct, "RemoveProduct");
		return RemoveProduct;
	}

	public IOSElement getRemove() {
		objiOSGenericMethods.CheckIOSElementFound(Remove, "Remove");
		return Remove;
	}
	/**
	 * Created By Subhasis
	 * This getter move to wishlist from 
	 * @return
	 */
	
	public IOSElement getMoveToWishlistPopup() {
		objiOSGenericMethods.CheckIOSElementFound(moveToWishlistPopup, "Move To WishList");
		return moveToWishlistPopup;
	}
	
	/**
	 * Created by subhasis 
	 * Demo id for delay the screen and load element in DOM
	 * @return
	 */
	
	public IOSElement getDemoid() {
		objiOSGenericMethods.CheckIOSElementFound(demoid, "Demoid");
		return demoid;
	}

	public IOSElement getAddMoreFromWishlist() {
		objiOSGenericMethods.CheckIOSElementFound(addMoreFromWishlist, "addMoreFromWishlist");
		return addMoreFromWishlist;
	}

	public IOSElement getmyntraLogo() {
		objiOSGenericMethods.CheckIOSElementFound(myntraLogo, "myntraLogo");
		return myntraLogo;
	}

	public IOSElement getRecipientName() {
		objiOSGenericMethods.CheckIOSElementFound(RecipientName, "RecipientName");
		return RecipientName;
	}

	public IOSElement getCancelButton() {
		objiOSGenericMethods.CheckIOSElementFound(CancelButton, "CancelButton");
		return CancelButton;
	}

	public IOSElement getApplyButton() {
		objiOSGenericMethods.CheckIOSElementFound(ApplyButton, "ApplyButton");
		return ApplyButton;
	}

	public IOSElement getEnterCouponCode() {
		objiOSGenericMethods.CheckIOSElementFound(EnterCouponCode, "EnterCouponCode");
		return EnterCouponCode;
	}

	public IOSElement getViewDetails() {
		objiOSGenericMethods.CheckIOSElementFound(ViewDetails, "ViewDetails");
		return ViewDetails;
	}

	public IOSElement getGiftMsg() {
		objiOSGenericMethods.CheckIOSElementFound(GiftMsg, "GiftMsg");
		return GiftMsg;
	}

	public IOSElement getSenderName() {
		objiOSGenericMethods.CheckIOSElementFound(SenderName, "SenderName");
		return SenderName;
	}

	/**
	 * giftWrapDetails.click added by subhasis 
	 * This method will help user to show the save button and click on it.
	 */
	public IOSElement getSaveGiftWrap() {
		giftWrapDetails.click();
		objiOSGenericMethods.CheckIOSElementFound(SaveGiftWrap, "SaveGiftWrap");
		return SaveGiftWrap;
	}

	public IOSElement getGiftWrap() throws InterruptedException {
//		Thread.sleep(3000);
//		objiOSGenericMethods.waitDriver(GiftWrap, "GiftWrap");
		objiOSGenericMethods.CheckIOSElementFound(GiftWrap, "GiftWrap");
		return GiftWrap;
	}

	public IOSElement getViewMyBag() {
		objiOSGenericMethods.CheckIOSElementFound(viewMyBag, "viewMyBag");
		return viewMyBag;
	}

	public IOSElement getBagPageTitle() {
		objiOSGenericMethods.CheckIOSElementFound(bagPageTitle, "bagPageTitle");
		return bagPageTitle;
	}

	public IOSElement getBagIcon() {
		objiOSGenericMethods.CheckIOSElementFound(bagIcon, "BagIcon");
		return bagIcon;
	}

	public IOSElement getDeliveryPageTitle() {
		objiOSGenericMethods.CheckIOSElementFound(deliveryPageTitle, "deliveryPageTitle");
		return deliveryPageTitle;
	}

	public IOSElement getPaymentPageTitle() {
		objiOSGenericMethods.CheckIOSElementFound(paymentPageTitle, "paymentPageTitle");
		return paymentPageTitle;
	}

	public IOSElement getGotoWishListlnk() {
		objiOSGenericMethods.CheckIOSElementFound(gotoWishListlnk, "gotoWishListlnk");
		return gotoWishListlnk;
	}

	public IOSElement getDoneButton() {
		objiOSGenericMethods.CheckIOSElementFound(DoneButton, "DoneButton");
		return DoneButton;
	}

	public IOSElement getContinueButton()  {
		
		objiOSGenericMethods.CheckIOSElementFound(continueButton, "continueButton");
		return continueButton;
	}

	public IOSElement getMoveToWishlist() {
		objiOSGenericMethods.CheckIOSElementFound(MoveToWishlist, "MoveToWishlist");
		return MoveToWishlist;
	}

	public IOSElement getPlaceOrder() {
		objiOSGenericMethods.CheckIOSElementFound(placeOrder, "placeOrder");
		return placeOrder;
	}

	public IOSElement getCoupon() {
		objiOSGenericMethods.CheckIOSElementFound(Coupon, "Coupon");
		return Coupon;
	}

	public IOSElement getApplyCouponButton() {
		objiOSGenericMethods.CheckIOSElementFound(ApplyCouponButton, "ApplyCouponButton");
		return ApplyCouponButton;
	}

	public void EnterCouponCode(String EnterCouponCode) throws InterruptedException {
		getEnterCouponCode().click();
		getEnterCouponCode().sendKeys(EnterCouponCode);
	}

	public void enterRecipientName(String RecipientName) throws InterruptedException {
		objiOSGenericMethods.clickOnIOSElement(getRecipientName(), "clicked on RecipientName");
		getRecipientName().clear();
		getRecipientName().sendKeys(RecipientName);
	}

	public void enterGiftMsg(String GiftMsg) throws InterruptedException {
		objiOSGenericMethods.clickOnIOSElement(getGiftMsg(), "clicked on GiftMsg");
		getGiftMsg().clear();
		getGiftMsg().sendKeys(GiftMsg);
	}

	public void enterSenderName(String SenderName) throws InterruptedException {
		objiOSGenericMethods.clickOnIOSElement(getSenderName(), "clicked on SenderName");
		getSenderName().clear();
		getSenderName().sendKeys(SenderName);
	}

	public void clickOnviewMyBag() {
		objiOSGenericMethods.clickOnIOSElement(getViewMyBag(), "clicked on ViewMyBag");
	}

	public void clickOnCancelButton() {
		objiOSGenericMethods.clickOnIOSElement(getCancelButton(), "clicked on Cancel Button.");
	}

	/**
	 * @author 300019221 Aravindanth Added try block and if condition. Java
	 *         Script executor method is implemented for click
	 */
	public void clickOnApplyButton() {
		try {
			if (getApplyButton().isDisplayed()) {
				objiOSGenericMethods.click(getApplyButton());
				// objiOSGenericMethods.clickOnIOSElement(getApplyButton(),
				// "clicked on Apply
				// Button.");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void clickOnViewDetails() {
		objiOSGenericMethods.clickOnIOSElement(getViewDetails(), "clicked on View Details link.");
	}

	public void clickOnBagIcon() {
		objiOSGenericMethods.clickOnIOSElement(getBagIcon(), "clicked on BagIcon");
	}

	public void clickOnSaveGiftWrap() {
		objiOSGenericMethods.clickOnIOSElement(getSaveGiftWrap(), "clicked on SaveGiftWrap");
	}

	public void clickOnGiftWrap() throws InterruptedException {
		objiOSGenericMethods.clickOnIOSElement(getGiftWrap(), "clicked on GiftWrap");
	}

	public void clickOnDoneButton() {
		objiOSGenericMethods.clickOnIOSElement(getDoneButton(), "clicked on DoneButton");
	}

	public void clickOnMoveToWishlist() {
		objiOSGenericMethods.waitDriver(getMoveToWishlist(), "move to wishlist");
		try {
			if(getMoveToWishlist().isDisplayed())
			objiOSGenericMethods.clickOnIOSElement(getMoveToWishlist(), "clicked on MoveToWishlist");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void clickOnPlaceOrder() {
		objiOSGenericMethods.waitDriver(getPlaceOrder(), "Place order");
		objiOSGenericMethods.clickOnIOSElement(getPlaceOrder(), "clicked on PlaceOrder");
	}

	public void SelectCoupon() {
		objiOSGenericMethods.clickOnIOSElement(getCoupon(), "clicked on Coupon");
	}

	/**
	 * @author 300019221 / aravinda Added JS click method
	 */
	public void clickOnApplyCouponButton() {
		try {
			if (getApplyCouponButton().isDisplayed()) {
				// objiOSGenericMethods.clickOnIOSElement(getApplyCouponButton(),
				// "clicked on
				// ApplyCouponButton");
				objiOSGenericMethods.click(getApplyCouponButton());
				System.out.println("User clicked on Coupon tab !");
			}
		} catch (Exception e) {
			System.out.println("Coupon tab is not displayed!");
			e.printStackTrace();
		}
	}

	/**
	 * @author 300019221 / Aravindanath Added try block and if Condition, JS
	 *         executor click is used.
	 */
	public void clickOnContinueButton()  {
		objiOSGenericMethods.waitDriver(getContinueButton(), "Continue Button");
		try {
			if (getContinueButton().isDisplayed()) {
//				objiOSGenericMethods.click(getContinueButton());
				objiOSGenericMethods.clickOnIOSElement(getContinueButton(),"clicked on ContinueButton");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void ClickOnGotoWishlist() {
		objiOSGenericMethods.clickOnIOSElement(getGotoWishListlnk(), "clicked on GotoWishListlnk");
	}

	public void assertBagPageTitle(String pageName) throws InterruptedException {
		objiOSGenericMethods.waitDriver(getBagPageTitle(), "Bag Page Title");
		IOSElement element = (IOSElement) getBagPageTitle();
		objiOSGenericMethods.VerifyTwoString(element, pageName);
	}

	public void assertDeliveryPageTitle(String pageName) throws InterruptedException {
//		Thread.sleep(5000);
		objiOSGenericMethods.waitDriver(getDeliveryPageTitle(), "Delivery Page Title");
		IOSElement element = (IOSElement) getDeliveryPageTitle();
		objiOSGenericMethods.VerifyTwoString(element, pageName);
	}

	public void assertPaymentPageTitle(String pageName) throws InterruptedException {
		objiOSGenericMethods.waitDriver(getPaymentPageTitle(), "Payment");
		IOSElement element = (IOSElement) getPaymentPageTitle();
		objiOSGenericMethods.VerifyTwoString(element, pageName);
	}

	/**
	 * @author 300019221 / Aravindanath Added try block and If condition.
	 * 
	 *         js executor click is used
	 */
	public void clickOnMyntraLogo() {
		try {

			if (getmyntraLogo().isDisplayed()) {
				objiOSGenericMethods.click(getmyntraLogo());
				// objiOSGenericMethods.clickOnIOSElement(getmyntraLogo(),
				// "clicked on Myntra Logo.");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void clickToAddMoreFromWishlist() {
		objiOSGenericMethods.clickOnIOSElement(getAddMoreFromWishlist(), "clicked on Add More from Wishlist");
	}

	public void RemoveProduct() {
		objiOSGenericMethods.clickOnIOSElement(getRemoveProduct(), "clicked on Remove Product icon.");
	}

	public void Remove() {
		objiOSGenericMethods.clickOnIOSElement(getRemove(), "clicked on Remove icon.");
	}

	public IOSElement getEmptyBag() {
		objiOSGenericMethods.CheckIOSElementFound(EmptyCart, "EmptyCart");
		return EmptyCart;
	}

	public List<IOSElement> getProductSetList() {
		objiOSGenericMethods.CheckIOSElementsListFound(ProductSetList, "ProductSetList");
		return ProductSetList;
	}

	public IOSElement getRemovelink() {
		objiOSGenericMethods.CheckIOSElementFound(Removelink, "Removelink");
		return Removelink;
	}

	/**
	 * Added by - Nitesh - getter method for click on quantity dropdown icon
	 * 
	 * @return
	 */

	public IOSElement getQtyDrpdwnicon() {
		objiOSGenericMethods.CheckIOSElementFound(qtyDrpdwnicon, "qtyDrpdwnicon");
		return qtyDrpdwnicon;
	}

	/**
	 * Added by - Nitesh - getter method for click on size dropdown icon
	 * 
	 * @return
	 */

	public IOSElement getSizeDrpdwnicon() {
		objiOSGenericMethods.CheckIOSElementFound(sizeDrpdwnicon, "sizeDrpdwnicon");
		return sizeDrpdwnicon;
	}

	/**
	 * Added by - Nitesh - getter method to get list of quantity available from
	 * dropdown
	 * 
	 * @return
	 */

	public List<IOSElement> getQtyInsideDrpdwn() {
		objiOSGenericMethods.CheckIOSElementsListFound(qtyInsideDrpdwn, "qtyInsideDrpdwn");
		return qtyInsideDrpdwn;
	}

	/**
	 * Added by - Nitesh - getter method to get list of size available from
	 * dropdown
	 * 
	 * @return
	 */

	public List<IOSElement> getSelectSizesInCart() {
		objiOSGenericMethods.CheckIOSElementsListFound(selectSize, "selectSize");
		return selectSize;
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
	 * @author 300019221 Aravinda
	 * 
	 * 
	 *         Used JS executor click
	 * @throws InterruptedException
	 */

	public void ClickRemoveLink() throws InterruptedException {
		List<IOSElement> links = getProductSetList();
		for (int i = 0; i <= links.size(); i++) {
			objiOSGenericMethods.click(getRemovelink());
			// getRemovelink().click();
//			Thread.sleep(1000);
			objiOSGenericMethods.click(getRemove());
			// getRemove().click();
//			Thread.sleep(2000);
			objiOSGenericMethods.waitDriver(getmyntraLogo(), "Myntra logo");
		}
		objiOSGenericMethods.waitDriver(getmyntraLogo(), "Myntra logo");
		clickOnMyntraLogo();
	}
	
	
	
	

	public void resetBag() throws InterruptedException {

		try {
			// Empty the Cart page

			objHomePageObjects.clickOnBagIcon();
			if (isBagEmpty()) {
				objiOSGenericMethods.waitDriver(getmyntraLogo(), "Myntralogo");
				clickOnMyntraLogo();
//				Thread.sleep(1000);
			} else {
				ClickRemoveLink();
			}
		} catch (Exception e) {
			// e.printStackTrace();
		}
	}

	public void VerifyProductTitle() {
		objiOSGenericMethods.VerifyStringShouldNotEmpty(getProductTitle(), "Product Title");
	}

	public void VerifySellingPrice() {
		objiOSGenericMethods.VerifyStringShouldNotEmpty(getSellingPrice(), "Selling Price");
	}

	/**
	 * @author 300019221 
	 * Added try block
	 */
	public void VerifyStrikedPrice() {
		try {
			objiOSGenericMethods.VerifyStringShouldNotEmpty(getStrikedPrice(), "Striked Price");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			 
		}
	}

	public void VerifyDiscountedPrice() {
		objiOSGenericMethods.VerifyStringShouldNotEmpty(getSellingPrice(), "Discounted Price");
	}

	/**
	 * Added by - Nitesh - Added click method to click on quantity dropdown icon
	 */

	public void clickQuantityDropdown(){
		try {
			if (getQtyDrpdwnicon().isDisplayed()) {
				objiOSGenericMethods.click(getQtyDrpdwnicon());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Added by - Nitesh - Added click method to click on size dropdown icon
	 * 
	 * @author 300019221 Aravindanth
	 * Added webdriver wait
	 */
	
	public void clickSizeDropdown(){
		try {
			objiOSGenericMethods.waitForElementVisibility(getSizeDrpdwnicon());
			if (getSizeDrpdwnicon().isDisplayed()) {
				objiOSGenericMethods.click(getSizeDrpdwnicon());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Added by - Nitesh - Method to change quantity and change size of the
	 * product from bag page
	 * 
	 * @return
	 */
	
	public void ChangeQuantity() {
		List<IOSElement> quantityList = getQtyInsideDrpdwn();
		int s = quantityList.size();
		System.out.println("size is:" + s);
		IOSElement selectQuantity =
				iDriver.findElement(By.xpath("(//div[@class='quantity']/button)[" +
						(s - 1) + "]"));
		selectQuantity.click();
		String selectedSize = selectQuantity.getText();

		if (Integer.parseInt(selectedSize) == s - 1) {
			Reporter.log("Passed : Quantity of products is changed to: " +
					selectedSize + " in cart page!");
		} else {
			Reporter.log("Failed : Unable to change the Quantity of products in cart page!");
		}
	}
	
	/**
	 * Created By Subhasis
	 * This Method will help user to move the product from bag page
	 * @throws InterruptedException
	 */
	
	public void ClickMoveToWishListLink() throws InterruptedException {
		List<IOSElement> links = getProductSetList();
		for (int i = 0; i <= links.size(); i++) {
			objiOSGenericMethods.click(getRemovelink());
			objiOSGenericMethods.click(getMoveToWishlistPopup());
			objiOSGenericMethods.waitDriver(getmyntraLogo(), "Myntra Logo");
		}
		objiOSGenericMethods.waitDriver(getmyntraLogo(), "Myntra Logo");
		clickOnMyntraLogo();
	}
	
	/**
	 * Createed by Subhais 
	 * This method will help user to verify the element which element is not present in the screen, for delay.
	 */
	
	public void clickOnDemoId() {
		try {
			objiOSGenericMethods.clickOnIOSElement(getDemoid(), "Demoid");
		} catch (Exception e) {
			
		}
	}

	/**
	 * Added by - Nitesh - Method to change the size of product from bag page
	 * 
	 * @return
	 */

	public void ChangeSize() {
		clickSizeDropdown();

		List<IOSElement> selectSizesInList = getSelectSizesInCart();

		for (IOSElement result : selectSizesInList) {
			
			String att = result.getAttribute("class");
			if(att.equalsIgnoreCase("btn size-btn-group size-btn  "))
			{
				result.findElement(By.xpath(".//span[1]")).click();
				break;
			}
			
		}

	}
}
