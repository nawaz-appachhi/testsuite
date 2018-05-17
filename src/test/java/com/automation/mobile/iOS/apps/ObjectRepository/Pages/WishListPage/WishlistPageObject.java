package com.automation.mobile.iOS.apps.ObjectRepository.Pages.WishListPage;

import java.util.List;

import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.Reporter;

import com.automation.core.mobile.iOS.iOSGenericMethods;

import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;

public class WishlistPageObject {

	iOSGenericMethods objiOSGenericMethods;

	public WishlistPageObject(IOSDriver<IOSElement> iDriver) {
		PageFactory.initElements(new AppiumFieldDecorator(iDriver), this);
		objiOSGenericMethods = new iOSGenericMethods(iDriver);
	}

	@iOSFindBy(xpath = "//XCUIElementTypeOther[@name=\"BROWSE ITEMS\"]")
	public IOSElement browseItems;

	
	@iOSFindBy(accessibility = "Allow")
	public IOSElement permssion;
	
	@iOSFindBy(accessibility = "OK")
	public IOSElement oKAndTouch;
	
	@iOSFindBy(accessibility = "MOVE TO BAG")
	public IOSElement moveToBag;

	@iOSFindBy(xpath = "//XCUIElementTypeButton[@name=\"Back\"]")
	public IOSElement crossMark;

	@FindAll({ @FindBy(xpath = "//XCUIElementTypeOther[@name=\\\"size_selector\\\"])[2]") })
	public List<IOSElement> selectSize;

	@iOSFindBy(xpath = "//XCUIElementTypeNavigationBar[@name=\"Featured\"]/XCUIElementTypeButton[2]")
	public IOSElement viewMyBag;

	@iOSFindBy(xpath="//XCUIElementTypeButton[@name=\"Back\"]")
	public IOSElement backBtn;
	
	@iOSFindBy(xpath = "//XCUIElementTypeStaticText[@name=\"View\"]")
	public IOSElement viewDetails;

	@iOSFindBy(xpath = "(//XCUIElementTypeStaticText[@name=\"MOVE TO WISHLIST\"])[1]")
	public IOSElement moveToWishlist;
	
	@iOSFindBy(xpath = "(//XCUIElementTypeStaticText[@name=\"MOVE TO WISHLIST\"])[1]")
	public List<IOSElement> productInWishlist;

	public List<IOSElement> getProductInWishlist() {
		return productInWishlist;
	}

	/********* getters ************/

	public IOSElement getBrowseItems() {
		objiOSGenericMethods.CheckIOSElementFound(browseItems, "browseItems");
		return browseItems;
	}

	public IOSElement getMoveToBag() {
		objiOSGenericMethods.CheckIOSElementFound(moveToBag, "moveToBag");
		return moveToBag;
	}

	public IOSElement getCrossMark() {
		objiOSGenericMethods.CheckIOSElementFound(crossMark, "crossMark");
		return crossMark;
	}
	
	public IOSElement getBackBtn() {
		objiOSGenericMethods.CheckIOSElementFound(backBtn, "backBtn");
		return backBtn;
	}

	public List<IOSElement> getSelectSize() {
		return selectSize;
	}

	public IOSElement getViewMyBag() {
		objiOSGenericMethods.CheckIOSElementFound(viewMyBag, "viewMyBag");
		return viewMyBag;
	}


	public IOSElement getViewDetails() {
		objiOSGenericMethods.CheckIOSElementFound(viewDetails, "viewDetails");
		return viewDetails;
	}

	public IOSElement getMoveToWishlist() {
		objiOSGenericMethods.CheckIOSElementFound(moveToWishlist, "moveToWishlist");
		return moveToWishlist;
	}

	/********* methods ***********/

	public void clickOnBrowseItems() {
		objiOSGenericMethods.clickOnIOSElement(getBrowseItems(), "Successfully click on browseItems button");
	}

	public void clickOnMoveToBag() {
		objiOSGenericMethods.clickOnIOSElement(getMoveToBag(), "Successfully click on moveToBag button");
	}

	public void clickOnCrossMark() {
		objiOSGenericMethods.clickOnIOSElement(getCrossMark(), "Successfully click on crossMark button");
	}

	public void ProductSizeElements(String Size) {
		int size = getSelectSize().size();
		for (int i = 0; i < size; i++) {
			if ((getSelectSize().get(i).isEnabled())) {
				(getSelectSize()).get(i).click();
				break;
			}
		}
		return;
	}
	
	/**
	 * @author 300019221 aravindanath
	 * Replaced thread.sleep with webdriver wait
	 * @throws InterruptedException
	 */

	public void clickOnViewDetails() throws InterruptedException {
		objiOSGenericMethods.scrolltoElementAndClick(getViewDetails(), 100);
		if(getViewDetails().isDisplayed()) {
			objiOSGenericMethods.clickOnIOSElement(getViewDetails(), "Successfully click on viewDetails button");
		}
		
	}

	public void clickOnMoveToWishlist() {
		objiOSGenericMethods.clickOnIOSElement(getMoveToWishlist(), "Successfully click on moveToWishlist button");

	}
	
	public void clickOnProductInWishlist() {
		getProductInWishlist().get(1).click();
	}
	
	/**
	 * @author 300019221 added wait method
	 */
	public void clickOnBackBtn() {
		objiOSGenericMethods.waitForElementVisibility(getBackBtn());
		objiOSGenericMethods.clickOnIOSElement(getBackBtn(), "Successfully click on moveToWishlist button");

	}
	
	@FindBy(xpath= "//AppiumAUT/XCUIElementTypeApplication/XCUIElementTypeWindow/XCUIElementTypeOther/XCUIElementTypeOther[2]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeScrollView/XCUIElementTypeOther/XCUIElementTypeOther[2]/XCUIElementTypeOther/XCUIElementTypeOther")
	public IOSElement movetobagtrial;

	
	@iOSFindBy(xpath="XCUIElementTypeOther ")
	public List<IOSElement> clickOnProduct;
	
	public List<IOSElement> getClickOnProduct() {
		return clickOnProduct;
	}

	public void clickOnProduct(int i){	
		getClickOnProduct().get(i).click();
		
	}
	/**
	 * @author 300019221
	 * Replaced   thread.sleep with webdriver wait
	 * @throws InterruptedException
	 */
	
	public void handlePermission() throws InterruptedException {
		try {
			if (permssion.isDisplayed()) {
				objiOSGenericMethods.waitForElementVisibility(permssion);
				Reporter.log("Alert found !");
				permssion.click();
				Reporter.log("Successfully click on permission button");
			} else {
				Assert.fail("Unable to click on permission button");
			}
		} catch (Exception e) {
			System.out.println("Permission not displayed");
		}  

	}
	
	public void clickOnOkAndTouch() {
		try {
			if (oKAndTouch.isDisplayed()) {
				System.err.println("Tap and Hold button is displayed!");
				oKAndTouch.click();
			}
		} catch (Exception e) {

		}

	}
	
	
}