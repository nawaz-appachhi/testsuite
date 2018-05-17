package com.automation.mobile.iOS.apps.ObjectRepository.Pages.CartPage;

import java.util.List;

import org.apache.commons.collections.set.SynchronizedSortedSet;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.Reporter;
import com.automation.core.mobile.iOS.iOSGenericMethods;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;

public class CartPageObject {

	iOSGenericMethods objiOSGenericMethods;

	public IOSDriver<IOSElement> iDriver;
	public AppiumDriver aDriver;

	public CartPageObject(IOSDriver<IOSElement> iDriver) {
		PageFactory.initElements(new AppiumFieldDecorator(iDriver), this);
		objiOSGenericMethods = new iOSGenericMethods(iDriver);
	}

	@iOSFindBy(accessibility = "PLACE ORDER")
	public IOSElement placeOrder;

	@iOSFindBy(accessibility = "CONTINUE")
	public IOSElement continueOrder;

	@FindAll({ @FindBy(className = "XCUIElementTypeImage") })
	public List<IOSElement> saveToWIshList;

	@FindBy(xpath = "//XCUIElementTypeAlert//XCUIElementTypeButton[@name='Allow']")
	public IOSElement permssion;

	@iOSFindBy(accessibility = "Gift Card")
	public IOSElement giftButton;

	@iOSFindBy(className = "XCUIElementTypeTextField")
	public IOSElement giftNumber;

	@iOSFindBy(className = "XCUIElementTypeSecureTextField")
	public IOSElement giftPin;

	@iOSFindBy(accessibility = "APPLY")
	public IOSElement applyButton;

	/**
	 * @author 300019221 / Aravindanath Created new object CouponNotVaild
	 */
	@iOSFindBy(xpath = "//XCUIElementTypeStaticText[@name=\"Coupons not valid for this order\"]")
	public IOSElement couponNotVaild;

	@iOSFindBy(accessibility = "CANCEL")
	public IOSElement CancelApplyCoupon;

	@iOSFindBy(accessibility = "REMOVE")
	public IOSElement removeButton;

	@iOSFindBy(xpath = "	//XCUIElementTypeNavigationBar[@name=\"Featured\"]/XCUIElementTypeButton[1]")
	public IOSElement BackButtonFromCart;

	@iOSFindBy(xpath = "//XCUIElementTypeButton[@label='REMOVE']")
	public IOSElement RemoveItemPopup;

	@iOSFindBy(xpath = "//XCUIElementTypeTextField[@value='Type coupon code here']")
	public IOSElement applyCouponTxtBox;

	/**
	 * @author 300019221 / Aravindanath Modified Xpath.
	 */
	@iOSFindBy(xpath = "//XCUIElementTypeButton[@name=\"REMOVE\"] ")
	public IOSElement removeButton2;

	// @iOSFindBy(accessibility = "Apply Coupon")
	@iOSFindBy(xpath = "(//XCUIElementTypeStaticText[@name='Apply Coupon'])[1]")
	public IOSElement applyCoupon;

	@iOSFindBy(accessibility = "Edit")
	public IOSElement editApplyCoupon;

	/**
	 * Created by subhasis Create Size and Quantity
	 */

	@iOSFindBy(accessibility = "Size:")
	public IOSElement changeSize;

	@iOSFindBy(className = "XCUIElementTypeButton")
	public List<IOSElement> changeSizeList;

	@iOSFindBy(accessibility = "Qty:")
	public IOSElement changequantity;

	@iOSFindBy(accessibility = "XCUIElementTypeButton")
	public List<IOSElement> changequantityList;

	/*
	 * Gift wrap
	 */
	@iOSFindBy(accessibility = "Gift wrap for Rs 25")
	public IOSElement GiftWrap;
	/**
	 * Modified: Lata Old Locator: //XCUIElementTypeOther[@name='SHOPPING
	 * BAG']/XCUIElementTypeOther[41]/XCUIElementTypeOther[5]/XCUIElementTypeTextField
	 */

	@iOSFindBy(xpath = "//XCUIElementTypeTextField")
	public List<IOSElement> ToAndFrom;

	@iOSFindBy(xpath = ".//XCUIElementTypeTextField[@value='Recipient Name']")
	public IOSElement RecipentName;
	/**
	 * Modified: Lata Old Locator: //XCUIElementTypeOther[@name='SHOPPING
	 * BAG']/XCUIElementTypeOther[41]/XCUIElementTypeOther[7]/XCUIElementTypeTextView
	 */
	@iOSFindBy(accessibility = "Gift Message")
	public IOSElement GiftMessage;

	/**
	 * Modified: Lata Old Locator: //XCUIElementTypeOther[@name='SHOPPING
	 * BAG']/XCUIElementTypeOther[41]/XCUIElementTypeOther[9]/XCUIElementTypeTextField
	 */

	@iOSFindBy(xpath = "//XCUIElementTypeTextField[@value='Sender Name']")
	public IOSElement SenderName;

	@iOSFindBy(accessibility = "SAVE")
	public IOSElement SaveGift;

	@iOSFindBy(accessibility = "CANCEL")
	public IOSElement CancelGift;

	@iOSFindBy(xpath = "(//XCUIElementTypeOther[@name=\"size_selector\"])[2]")
	public IOSElement handlePopup;

	/**
	 * 
	 * Modified By: lata Older Locator: accessibility = "View"
	 * 
	 */

	@iOSFindBy(xpath = "//XCUIElementTypeStaticText[@name=\"View\"]")
	public IOSElement viewDetails;

	@iOSFindBy(accessibility = "Remove")
	public IOSElement removeGiftwrap;

	@iOSFindBy(xpath = "//XCUIElementTypeButton[@name=\"Done\"]")
	public IOSElement doneButton;

	/*************** getters ************/

	/**
	 * Created By Subhasis Create Size and Quantity
	 * 
	 * @return
	 */

	public IOSElement getChangeSize() {
		objiOSGenericMethods.CheckIOSElementFound(changeSize, "changeSize");
		return changeSize;
	}

	public List<IOSElement> getChangeSizeList() {
		objiOSGenericMethods.CheckIOSElementsListFound(changeSizeList, "changeSizeList");
		return changeSizeList;
	}

	public IOSElement getChangequantity() {
		objiOSGenericMethods.CheckIOSElementFound(changequantity, "changequantity");
		return changequantity;
	}

	public List<IOSElement> getChangequantityList() {
		objiOSGenericMethods.CheckIOSElementsListFound(changequantityList, "changequantityList");
		return changequantityList;
	}

	public IOSElement getGoToHomePage() {
		objiOSGenericMethods.CheckIOSElementFound(BackButtonFromCart, "BackButtonFromCart");
		return BackButtonFromCart;
	}

	public IOSElement getApplyCouponTxtBox() {
		objiOSGenericMethods.CheckIOSElementFound(applyCouponTxtBox, "applyCouponTxtBox");
		return applyCouponTxtBox;
	}

	public IOSElement getGiftWrap() {
		objiOSGenericMethods.CheckIOSElementFound(GiftWrap, "GiftWrap");
		return GiftWrap;
	}

	public IOSElement getRecipentName() {
		objiOSGenericMethods.CheckIOSElementFound(RecipentName, "RecipentName");
		return RecipentName;
	}

	public IOSElement getGiftMessage() {
		objiOSGenericMethods.CheckIOSElementFound(GiftMessage, "GiftMessage");
		return GiftMessage;
	}

	public IOSElement getSenderName() {
		objiOSGenericMethods.CheckIOSElementFound(SenderName, "SenderName");
		return SenderName;
	}

	public IOSElement getSaveGift() {
		objiOSGenericMethods.CheckIOSElementFound(SaveGift, "SaveGift");
		return SaveGift;
	}

	public IOSElement getCancelGift() {
		objiOSGenericMethods.CheckIOSElementFound(CancelGift, "CancelGift");
		return CancelGift;
	}

	public IOSElement getCancelApplyCoupon() {
		objiOSGenericMethods.CheckIOSElementFound(CancelApplyCoupon, "CancelApplyCoupon");
		return CancelApplyCoupon;
	}

	public IOSElement getEditApplyCoupon() {
		objiOSGenericMethods.CheckIOSElementFound(editApplyCoupon, "editApplyCoupon");
		return editApplyCoupon;
	}

	public IOSElement getPlaceOrder() {
		objiOSGenericMethods.CheckIOSElementFound(placeOrder, "placeOrder");
		return placeOrder;
	}

	public List<IOSElement> getSaveToWIshList() {
		objiOSGenericMethods.CheckIOSElementFound((IOSElement) saveToWIshList, "saveToWIshList");
		return saveToWIshList;
	}

	public IOSElement getPermssion() {
		objiOSGenericMethods.CheckIOSElementFound(permssion, "permssion");
		return permssion;
	}

	public IOSElement getGiftButton() {
		objiOSGenericMethods.CheckIOSElementFound(giftButton, "giftButton");
		return giftButton;
	}

	public IOSElement getGiftNumber() {
		objiOSGenericMethods.CheckIOSElementFound(giftNumber, "giftNumber");
		return giftNumber;
	}

	public IOSElement getGiftPin() {
		objiOSGenericMethods.CheckIOSElementFound(giftPin, "giftPin");
		return giftPin;
	}

	public IOSElement getApplyButton() {
		objiOSGenericMethods.CheckIOSElementFound(applyButton, "applyButton");
		return applyButton;
	}

	public IOSElement getRemoveButton() {
		objiOSGenericMethods.CheckIOSElementFound(removeButton, "removeButton");
		return removeButton;
	}

	public IOSElement getContinueOrder() {
		objiOSGenericMethods.CheckIOSElementFound(continueOrder, "continueOrder");
		return continueOrder;
	}

	public IOSElement getApplyCoupon() {
		objiOSGenericMethods.CheckIOSElementFound(applyCoupon, "applyCoupon");
		return applyCoupon;
	}

	public IOSElement getRemoveItemPopup() {
		objiOSGenericMethods.CheckIOSElementFound(RemoveItemPopup, "RemoveItemPopup");
		return RemoveItemPopup;
	}

	public IOSElement getHandlePopup() {
		objiOSGenericMethods.CheckIOSElementFound(handlePopup, "handlePopup");
		return handlePopup;
	}

	public IOSElement getViewDetails() {
		objiOSGenericMethods.CheckIOSElementFound(viewDetails, "viewDetails");
		return viewDetails;
	}

	public IOSElement getRemoveGiftwrap() {
		objiOSGenericMethods.CheckIOSElementFound(removeGiftwrap, "removeGiftwrap");
		return removeGiftwrap;
	}

	public IOSElement getDoneButton() {
		objiOSGenericMethods.CheckIOSElementFound(doneButton, "doneButton");
		return doneButton;
	}

	public IOSElement getCouponNotVaild() {
		objiOSGenericMethods.CheckIOSElementFound(couponNotVaild, "couponNotVaild");
		return couponNotVaild;
	}

	/***************
	 * methods
	 * 
	 * @author 300019221 Aravinda IF condition is added for remove gift wrap
	 * 
	 * @throws InterruptedException
	 ***************/

	public void clickOnGiftWrap() throws InterruptedException {
		try {
			if (getRemoveGiftwrap().isDisplayed()) {
				// objiOSGenericMethods.waitForElementVisibility(getRemoveGiftwrap());
				objiOSGenericMethods.clickOnIOSElement(getRemoveGiftwrap(), "Remove GiftWrap");
			}
			// objiOSGenericMethods.waitForElementVisibility(getGiftWrap());
			objiOSGenericMethods.clickOnIOSElement(getGiftWrap(), "GiftWrap");

		} catch (Exception e) {
			objiOSGenericMethods.clickOnIOSElement(getGiftWrap(), "GiftWrap");
		}
	}

	public void clickOnGiftWrapButton() throws InterruptedException {
		// objiOSGenericMethods.waitForElementVisibility(getGiftWrap());
		// if (getGiftWrap().isDisplayed()) {
		objiOSGenericMethods.clickOnIOSElement(getGiftWrap(), "GiftWrap");
		// }
	}

	/**
	 * @author 300019221 Replaced thread.sleep with webdriver wait
	 * @param recipentName
	 * @throws InterruptedException
	 */
	public void clickOnRecipentName(String recipentName) throws InterruptedException {
		// objiOSGenericMethods.waitForElementVisibility(getRecipentName());
		// if (getRecipentName().isDisplayed()) {
		objiOSGenericMethods.clickOnIOSElement(getRecipentName(), "RecipentName");
		getRecipentName().clear();
		getRecipentName().sendKeys(recipentName);
		// }
	}

	/**
	 * @author 300019221 Replaced thread.sleep with webdriver wait
	 * @param giftMessage
	 * @throws InterruptedException
	 */
	public void clickOnGiftMessage(String giftMessage) throws InterruptedException {
		// objiOSGenericMethods.waitForElementVisibility(getGiftMessage());
		// if (getGiftMessage().isDisplayed()) {
		objiOSGenericMethods.clickOnIOSElement(getGiftMessage(), "GiftMessage");
		getGiftMessage().clear();
		getGiftMessage().sendKeys(giftMessage);
		// }
	}

	/**
	 * @author 300019221 Replaced with thread.sleep with webdriver wait
	 * @param senderName
	 * @throws InterruptedException
	 */

	public void clickOnSenderName(String senderName) throws InterruptedException {
		// objiOSGenericMethods.waitForElementVisibility(getSenderName());
		// if (getSenderName().isDisplayed()) {
		objiOSGenericMethods.clickOnIOSElement(getSenderName(), "SenderName");
		getSenderName().clear();
		getSenderName().sendKeys(senderName);
		// }
	}

	public void clickOnSaveGift() {
		// if (getSaveGift().isDisplayed()) {
		objiOSGenericMethods.clickOnIOSElement(getSaveGift(), "SaveGift");
		// }
	}

	/**
	 * @author 300019221 / Aravindanath This method will click on back button based
	 *         on number given
	 * @param noOfBack
	 * @throws InterruptedException
	 * 
	 * @author 300019221 replaced thread.sleep with webdriver wait
	 */

	public void clickGoBackPage() throws InterruptedException {
		// objiOSGenericMethods.waitForElementVisibility(getGoToHomePage());
		// if (getGoToHomePage().isDisplayed()) {
		objiOSGenericMethods.clickOnIOSElement(getGoToHomePage(), "Go TO HomePage");
		// }
	}

	/**
	 * @author 300019221 Added wait for cancel button
	 */
	public void clickOnCancelApplyCoupon() {
		// objiOSGenericMethods.waitForElementVisibility(getCancelApplyCoupon());
		objiOSGenericMethods.clickOnIOSElement(getCancelApplyCoupon(), "Cancel Apply Coupon");
	}

	public void clickOnCancelGift() {
		objiOSGenericMethods.clickOnIOSElement(getCancelGift(), "Cancel Gift");
	}

	/**
	 * @author 300019221 Added click using JS
	 * @throws InterruptedException
	 * 
	 * @author 300019221 replaced thread.sleep with webdriver wait
	 */
	public void clickOnplaceOrder() throws InterruptedException {
		// objiOSGenericMethods.waitForElementVisibility(getPlaceOrder());
		// if (getPlaceOrder().isDisplayed()) {
		objiOSGenericMethods.clickOnIOSElement(getPlaceOrder(), "Place Order Button");
		// }
	}

	/**
	 * @author 300019221 replaced thread.sleep with webdriver wait
	 * @throws InterruptedException
	 */
	public void clickOnContinueOrder() throws InterruptedException {
		// objiOSGenericMethods.waitForElementVisibility(getContinueOrder());
		objiOSGenericMethods.clickOnIOSElement(getContinueOrder(), "Continue Order Button");
	}

	public void clickOnViewDetails() {
		objiOSGenericMethods.clickOnIOSElement(getViewDetails(), "View Details Button");
	}

	public IOSElement getRemoveBtn2() {
		objiOSGenericMethods.CheckIOSElementFound(removeButton2, "Remove Button");
		return removeButton2;
	}

	public IOSElement getRemoveBtn() {
		objiOSGenericMethods.CheckIOSElementFound(removeButton, "Remove Button");
		return removeButton;
	}

	public void clickOnRemoveBtn() {
		// if (getRemoveBtn().isDisplayed()) {
		// objiOSGenericMethods.waitForElementVisibility(getRemoveBtn());
		objiOSGenericMethods.clickOnIOSElement(getRemoveBtn(), "Remove Button");
		// }
	}

	/**
	 * @author 300019221 Added web driver wait
	 */
	public void clickOnRemoveBtn2() {
		// if (getRemoveBtn2().isDisplayed()) {
		// objiOSGenericMethods.waitForElementVisibility(getRemoveBtn2());
		objiOSGenericMethods.clickOnIOSElement(getRemoveBtn2(), "Remove Button");
		// }
	}

	/**
	 * @author 300019221 commented thread.sleep
	 * @param i
	 * @throws InterruptedException
	 */
	public void getSaveToWIshList(int i) throws InterruptedException {
		try {
			objiOSGenericMethods.clickOnIOSElement(saveToWIshList.get(i), "Save To WishList Button");
//			saveToWIshList.get(i).click();
			System.err.println(saveToWIshList.get(i).getText());
			System.out.println("Total No of products " + saveToWIshList.size());
			System.out.println("USer has click save to wish list!");

		} catch (Exception e) {

		}

	}

	/**
	 * @author 300019221
	 * @param gift
	 * @throws InterruptedException
	 * 
	 * @author 300019221 replace thread.sleep with webdriver wait
	 */
	public void setGiftNumber(String gift) throws InterruptedException {
		objiOSGenericMethods.waitForElementVisibility(giftNumber);
		if (giftNumber.isDisplayed()) {
			try {
				if (permssion.isDisplayed()) {
					permssion.click();
					Reporter.log("Successfully click on permission button");
				} else {
					Assert.fail("Unable to click on permission button");
				}
			} catch (Exception e) {
				System.out.println("Permission not displayed");
			}
			giftNumber.sendKeys(gift);
			System.out.println("user has entered gift number!");
		}
	}

	public void setGiftPin(String pin) throws InterruptedException {
		if (giftPin.isDisplayed()) {
			try {
				if (permssion.isDisplayed()) {
					permssion.click();
					Reporter.log("Successfully click on permission button");
				} else {
					Assert.fail("Unable to click on permission button");
				}
			} catch (Exception e) {
				System.out.println("Permission not displayed");
			}
			giftPin.sendKeys(pin);
			System.out.println("user has entered gift pin!");
		}
	}

	public void clickOnApplyGiftCard() throws InterruptedException {
		objiOSGenericMethods.clickOnIOSElement(getApplyButton(), "Apply Button");
	}

	/**
	 * @author 300019221 / Aravindanath
	 * @throws InterruptedException
	 *             Added If conditon and Try block
	 */

	public void clickOnremoveButton() throws InterruptedException {
		// try {
		// if (getRemoveButton().isDisplayed()) {
		objiOSGenericMethods.clickOnIOSElement(getRemoveButton(), "Remove Button");
		// }
		// } catch (Exception e) {
		// System.out.println("Remove Button is not displayed!");
		// }
	}

	/**
	 * @author 300019221 replaced thread.sleep with webdriver wait
	 * @throws InterruptedException
	 */
	public void emptyProduct() throws InterruptedException {
		try {

			if (getRemoveBtn().isDisplayed()) {
				clickOnRemoveBtn();
				clickOnRemoveBtn2();
				System.out.println("Products removed!");
			}
		} catch (Exception e) {
			System.out.println("Bag is already Empty :) !");
		}

	}

	public void clickOnGiftCard() throws InterruptedException {
		objiOSGenericMethods.clickOnIOSElement(getGiftButton(), "Gift Button");
	}

	/**
	 * @author 300019221 / Aravindanath Added Try Block and If condition. used click
	 *         with JS
	 * 
	 * @author 300019221 Aravindanath Added WebDriver wait method and commented JS
	 *         Click
	 */
	public void ClickOnApplyCoupon() throws InterruptedException {
		// objiOSGenericMethods.waitForElementVisibility(getApplyCoupon());
		try {
			// if (getApplyCoupon().isDisplayed()) {
			objiOSGenericMethods.clickOnIOSElement(getApplyCoupon(), "Apply Coupon Button");
			System.out.println("Apply coupon is clicked!");
			// }
		} catch (Exception e) {
			objiOSGenericMethods.click(getApplyCoupon());
			System.out.println("Apply coupon re tried to click!");
		}

	}

	public void clickOnEditApplyCoupon() {
		try {
			// if (getEditApplyCoupon().isDisplayed()) {
			objiOSGenericMethods.clickOnIOSElement(getEditApplyCoupon(), "Edit Coupon Button");
			// getEditApplyCoupon().click();
			System.out.println("Edit Apply coupon is clicked");
			// }
		} catch (Exception e) {
//			getApplyCoupon().click();
			objiOSGenericMethods.clickOnIOSElement(getApplyCoupon(), "Apply Coupon Button");
		}
	}

	public void ClickonRemoveItemPopup() {
		objiOSGenericMethods.clickOnIOSElement(getRemoveItemPopup(), "Remove Item popup");
		// getRemoveItemPopup().click();
	}

	public void ClickOnPopup() {
		objiOSGenericMethods.clickOnIOSElement(getHandlePopup(), "Handle popup");
		// getHandlePopup().click();
	}

	public void ClickOnApplyButton() {
		// objiOSGenericMethods.waitForElementVisibility(getApplyButton());
		objiOSGenericMethods.clickOnIOSElement(getApplyButton(), "Apply Button");
	}

	public void enterCoupon(String coupon) {
		// if (getApplyCouponTxtBox().isDisplayed()) {
		getApplyCouponTxtBox().sendKeys(coupon);
		System.err.println("User has entered " + coupon);
		// }
	}

	/**
	 * @author 300019221 /Aravindanath Permission method is removed.
	 * @throws InterruptedException
	 * @author 300019221 /Aravindanath Added clickOnCancelApplyCoupon(); Modifed the
	 *         method using getter
	 * @author 300019221 Added swipe method
	 * @author 300019221 Aravindanath commented not vaild coupon.
	 * @author 300019221 Below is commented due to lack of test data for coupon.
	 */
	public void clickOnApplyButton() throws InterruptedException {
		clickOnCancelApplyCoupon();
		System.out.println("Clicked on Apply / cancel button");
		// try {
		// objiOSGenericMethods.swipeDown(100, 1);
		// objiOSGenericMethods.waitForElementVisibility(getCouponNotVaild());
		// if (getCouponNotVaild().isDisplayed()) {
		// clickOnCancelApplyCoupon();
		// System.out.println("Coupons not valid for this order, hence No coupon applied
		// !");
		// } else if (getApplyButton().isDisplayed()) {
		// ClickOnApplyButton();
		// System.out.println("user has clicked on apply button");
		// }
		// } catch (Exception e) {
		// System.out.println("Apply button is not found!");
		// }

	}

	/**
	 * @author 300019221 Replaced thread.sleep with webdriver wait
	 * @param recipentName
	 * @param msg
	 * @param senderName
	 * @throws InterruptedException
	 */

	public void giftWrap(String recipentName, String msg, String senderName) throws InterruptedException {

		getRecipentName().clear();
		getRecipentName().sendKeys(recipentName);
		clickOnGiftMessage(msg);
		// objiOSGenericMethods.waitForElementVisibility(getSenderName());
		getSenderName().clear();
		getSenderName().sendKeys(senderName);
		// objiOSGenericMethods.waitForElementVisibility(getDoneButton());
//		getDoneButton().click();
		objiOSGenericMethods.clickOnIOSElement(getDoneButton(), "Done Button");
		// objiOSGenericMethods.waitForElementVisibility(getSaveGift());
//		getSaveGift().click();
		objiOSGenericMethods.clickOnIOSElement(getSaveGift(), "Save Gift Button");

	}

	/**
	 * @author 300019221 / Aravindanath
	 * @throws InterruptedException
	 *             Add try block and if condition for clickOnDoneButton() method.
	 * 
	 * @author 300019221 replaced thread.sleep with webdriver wait
	 */
	public void clickOnDoneButton() throws InterruptedException {
//		objiOSGenericMethods.waitForElementVisibility(getDoneButton());
		try {
//			if (getDoneButton().isDisplayed()) {
				objiOSGenericMethods.clickOnIOSElement(getDoneButton(), "Done Button");
//			}
		} catch (Exception e) {
			System.out.println("Done button is not displayed!");
		}
	}

	/**
	 * @author 300019221 Aravindanth This feature is a blocker on iOS hence its
	 *         commented.
	 */
	public void clickOnChangeSize() {
		// getChangeSize().click();

	}

	/**
	 * @author 300019221 Aravindanth This feature is a blocker on iOS hence its
	 *         commented.
	 */

	public void clickOnChangeSizeFromList() {
		// getChangeSizeList().get(3).getText();
		// System.out.println("Select size from list " +
		// getChangeSizeList().get(3).getText());
		// objiOSGenericMethods.clickOnIOSElement(getChangeSizeList().get(3), "Size
		// chart");
	}

	/**
	 * @author 300019221 Aravindanth This feature is a blocker on iOS hence its
	 *         commented.
	 */
	public void clickOnQuantity() {
		// getChangequantity().click();
	}

	/**
	 * @author 300019221 Aravindanth This feature is a blocker on iOS hence its
	 *         commented.
	 */
	public void clickOnChangequantityList() {
		// getChangequantityList().get(4).getText();
		// System.out.println("Select number of quantity from list " +
		// getChangequantityList().get(4).getText());
		// objiOSGenericMethods.clickOnIOSElement(getChangequantityList().get(4),
		// "Quantity List");

	}

}
