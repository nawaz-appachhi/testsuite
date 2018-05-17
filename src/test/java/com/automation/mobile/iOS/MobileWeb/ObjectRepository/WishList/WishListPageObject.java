package com.automation.mobile.iOS.MobileWeb.ObjectRepository.WishList;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.automation.core.mobile.iOS.iOSGenericMethods;
import com.automation.mobile.iOS.MobileWeb.ObjectRepository.HomeObjects.*;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class WishListPageObject {

	public AppiumDriver<MobileElement>  iDriver;
	iOSGenericMethods objiOSGenericMethods;
	public List<IOSElement> elements;
	public WebElement element = null;
	HomePageObjects objHomePageObjects;

	public WishListPageObject(AppiumDriver<MobileElement>  iDriver) {
		PageFactory.initElements(new AppiumFieldDecorator(iDriver), this);
		objiOSGenericMethods = new iOSGenericMethods(iDriver);
		objHomePageObjects = new HomePageObjects(iDriver);
	}

	@FindBy(xpath = "//a[@class='itemcard-moveToBag itemcard-boldFont']")
	public IOSElement MoveToBaglnk;

	@FindBy(xpath="//button/div[contains(text(),'DONE')]")
	public IOSElement DoneButton;

	@FindBy(xpath=".//div[@class='itemcard-removeIcon']")
	public IOSElement RemoveIcon;

	@FindBy(xpath=".//*[text()='CONTINUE SHOPPING']")
	public IOSElement ContinueShopping;

	@FindBy(className="sizeselect-sizeButton")
	public List<IOSElement> sizeButtons;

	@FindBy(xpath="//div[@class='mobile-left']/a[2]")
	public IOSElement myntraLogo;

	@FindBy(xpath = "//div[text()='WISHLIST EMPTY']")
	public IOSElement EmptyWishlist;

	@FindAll(@FindBy(xpath = "//div[@class='itemcard-itemCard']"))
	public List<IOSElement> ProductListInWishlist;

	@FindBy(xpath = "//div[@class='itemcard-removeIcon']")
	public IOSElement CrossmarkButton1;
	
	@FindBy(className = "index-headingDiv")
	public IOSElement wishlistPageTitle;
	
	@FindBy(className = "itemdetails-itemDetailsLabel")
	public IOSElement productTitle;
	
	@FindBy(className = "itemdetails-boldFont")
	public IOSElement discountedPrice;
	
	@FindBy(className = "itemdetails-strike")
	public IOSElement strikedPrice;
	
	/**
	 * This method will help user to move cart page
	 * Created by subhasis 
	 */
	
	@FindBy(xpath="//a[@href='/checkout/cart']")
	public IOSElement cartIcon;

	public IOSElement getCartIcon() {
		objiOSGenericMethods.CheckIOSElementFound(cartIcon, "CartIcon");
		return cartIcon;
	}

	public IOSElement getStrikedPrice() {
		objiOSGenericMethods.CheckIOSElementFound(strikedPrice, "strikedPrice");
		return strikedPrice;
	}
	
	public IOSElement getDiscountedPrice() {
		objiOSGenericMethods.CheckIOSElementFound(discountedPrice, "discountedPrice");
		return discountedPrice;
	}
	
	public IOSElement getProductTitle() {
		objiOSGenericMethods.CheckIOSElementFound(productTitle, "productTitle");
		return productTitle;
	}
	
	public IOSElement getWishlistPageTitle() {
		objiOSGenericMethods.CheckIOSElementFound(wishlistPageTitle, "wishlistPageTitle");
		return wishlistPageTitle;
	}
	
	public List<IOSElement> getsizeButtons() {
		objiOSGenericMethods.CheckIOSElementsListFound(sizeButtons, "sizeButtons");
		return sizeButtons;
	}

	public IOSElement getmyntraLogo() {
		objiOSGenericMethods.CheckIOSElementFound(myntraLogo, "myntraLogo");
		return myntraLogo;
	}

	public IOSElement getContinueShopping() {
		objiOSGenericMethods.CheckIOSElementFound(ContinueShopping, "ContinueShopping");
		return ContinueShopping;
	}

	public IOSElement getRemoveIcon() {
		objiOSGenericMethods.CheckIOSElementFound(RemoveIcon, "RemoveIcon");
		return RemoveIcon;
	}

	public IOSElement getDoneButton() throws InterruptedException {
		objiOSGenericMethods.CheckIOSElementFound(DoneButton, "DoneButton");
//		Thread.sleep(2000);
		return DoneButton;
	}

	public IOSElement getMoveToBaglnk() {
		objiOSGenericMethods.CheckIOSElementFound(MoveToBaglnk, "MoveToBaglnk");
		return MoveToBaglnk;
	}

	public void ClickOnMoveToBag() {
		objiOSGenericMethods.clickOnIOSElement(getMoveToBaglnk(), "MoveToBaglnk");
	}

	public void ClickOnContinueShopping()	{
		objiOSGenericMethods.clickOnIOSElement(getContinueShopping(), "Continue to shop button");
	}

	public void ClickOnDoneButton() throws InterruptedException	{
		objiOSGenericMethods.clickOnIOSElement(getDoneButton(), "Done button");
	}

	public void ClickOnRemoveIcon() {
		objiOSGenericMethods.clickOnIOSElement(getRemoveIcon(), "RemoveIcon");
	}

	public WebElement ClickSizeButtons() {
		elements = (List<IOSElement>) getsizeButtons();
		for (int i = 0; i < elements.size(); i++) {
			if (elements.get(i).isEnabled()) {
				elements.get(i).click();
				break;
			}
		}
		return element;
	}
/**
 * @author 300019221 aravinda
 * Added try block and if condition
 * 
 * Used JS executor click
 */
	public void clickOnMyntraLogo(){
		
		try {
			if (getmyntraLogo().isDisplayed()) {
				objiOSGenericMethods.waitForElementVisibility(getmyntraLogo());
				objiOSGenericMethods.click(getmyntraLogo());
			//	objiOSGenericMethods.clickOnIOSElement(getmyntraLogo(), "clicked on Myntra Logo");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public IOSElement getEmptyWishlist() {
		objiOSGenericMethods.CheckIOSElementFound(EmptyWishlist, "EmptyWishlist");
		return EmptyWishlist;
	}

	public List<IOSElement> getProductListInWishlist() {
		objiOSGenericMethods.CheckIOSElementsListFound(ProductListInWishlist, "ProductListInWishlist");
		return ProductListInWishlist;
	}

	public IOSElement getCrossmarkButton1() {
		objiOSGenericMethods.CheckIOSElementFound(CrossmarkButton1, "CrossmarkButton1");
		return CrossmarkButton1;
	}

	public boolean IsWishlistEmpty() {
		try {
			getEmptyWishlist().getText();
			System.out.println("Wishlist is empty");
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Wishlist has items");
			return false;
		}
	}
/**
 * @author 300019221 Aravinda
 * JS executor is used
 */
	public void CrossmarkButton1() {
		objiOSGenericMethods.click(getCrossmarkButton1());
		//objiOSGenericMethods.clickOnIOSElement(getCrossmarkButton1(), "clicked on Remove Button.");
	}

	public void RemoveAllItemsFromWishlist() {
		List<IOSElement> list = getProductListInWishlist();
		for (int i = 0; i < list.size(); i++) {
			CrossmarkButton1();
		}
	}

	/*resetWishlist
	 * Added by - Nitesh
	 * to remove all the added address
	 * */

	public void resetWishlist() throws InterruptedException {
		objHomePageObjects.clickOnWishlistButton();
		try {
			// Empty wishlist Page
            objiOSGenericMethods.waitDriver(getEmptyWishlist(), "Empty Wishlist");
			getEmptyWishlist().getText();
			clickOnMyntraLogo();
		}
		catch (Exception e) {
			RemoveAllItemsFromWishlist();
			objiOSGenericMethods.waitDriver(getmyntraLogo(), "Myntralogo");
			clickOnMyntraLogo();
		}
	}
	/**
	 * Created by subhasis
	 */
	
	public void clickOnCartFromWishlist() {
		objiOSGenericMethods.clickOnIOSElement(getCartIcon(), "CartIcon");
	}
	
	public void VerifyWishlistPageTitle()	{
		objiOSGenericMethods.VerifyStringShouldNotEmpty(getWishlistPageTitle(), "My Wishlist");
	}
	
	public void VerifyProductTitle()	{
		objiOSGenericMethods.VerifyStringShouldNotEmpty(getProductTitle(), "Product Title");
	}
	
	public void VerifyDiscountedPrice()	{
		objiOSGenericMethods.VerifyStringShouldNotEmpty(getDiscountedPrice(), "Discounted Price");
	}
	
	public void VerifyStrikedPrice()	{
		objiOSGenericMethods.VerifyStringShouldNotEmpty(getStrikedPrice(), "Striked Price");
	}
	
	public void VerifySellingPrice()	{
		objiOSGenericMethods.VerifyStringShouldNotEmpty(getDiscountedPrice(), "Selling Price");
	}

}
