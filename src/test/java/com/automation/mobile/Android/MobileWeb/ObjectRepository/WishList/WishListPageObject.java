package com.automation.mobile.Android.MobileWeb.ObjectRepository.WishList;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;

import com.automation.core.mobile.Android.AndroidGenericMethods;
import com.automation.mobile.Android.MobileWeb.ObjectRepository.HomeObjects.HomePageObjects;
import com.automation.mobile.Android.MobileWeb.ObjectRepository.HomeObjects.MenuPageObjects;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

/**
 * Modified By:Aishurya:Changed string parameter,which is being used in reporting
 */
public class WishListPageObject {

	AndroidGenericMethods objAndroidGenericMethods;
	HomePageObjects objHomepageObjects;
	MenuPageObjects objMenupageObjects;
	AppiumDriver<MobileElement> aDriver;

	public WishListPageObject(AppiumDriver<MobileElement> aDriver) {
		PageFactory.initElements(new AppiumFieldDecorator(aDriver), this);

		objAndroidGenericMethods = new AndroidGenericMethods(aDriver);
		objHomepageObjects = new HomePageObjects(aDriver);
		objMenupageObjects = new MenuPageObjects(aDriver);		
		this.aDriver = aDriver;
	}

	@FindBy(linkText = "MOVE TO BAG")
	public AndroidElement MoveToBaglnk;

	@FindBy(xpath = "//button/div[contains(text(),'DONE')]")
	public AndroidElement DoneButton;

	@FindBy(xpath = ".//div[@class='itemcard-removeIcon']")
	public AndroidElement RemoveIcon;

	@FindBy(xpath = ".//*[text()='CONTINUE SHOPPING']")
	public AndroidElement ContinueShopping;
	@FindBy(xpath = "//div[@class='mobile-left']/a[2]")
	public AndroidElement myntraLogo;
	@FindBy(xpath = "//img[@class='itemcard-itemImage']")
	public AndroidElement ProductImage;
	@FindBy(xpath = "//div[@class='mobile-right']/a[@href='/checkout/cart']")
	public AndroidElement CartIconFromWishlist;
	@FindBy(xpath = "//a[text()='MOVE TO BAG']")
	public AndroidElement wishlistMoveToBag;
	@FindBy(xpath = "//button[@class='sizeselect-doneBtn sizeselect-active']/div[text()='DONE']")
	public AndroidElement wishlistDonebtn;
	@FindBy(xpath = "//div[text()='WISHLIST EMPTY']")
	public WebElement EmptyWishlist;
	/**
	 * Modifed by :Anu, changed 'Webelement' to 'Androidelement'
	 */
	@FindBy(xpath = "//div[@class='itemcard-removeIcon']")
	public AndroidElement CrossmarkButton1;

	@FindAll(@FindBy(xpath = "//div[@class='itemcard-removeIcon']"))
	public List<WebElement> ProductListInWishlist;

	public List<WebElement> getProductListInWishlist() {
		return ProductListInWishlist;
	}

	public WebElement getEmptyWishlist() {
		return EmptyWishlist;
	}
	/**
	 * Modifed by :Anu, changed 'Webelement' to 'Androidelement'
	 */
	public AndroidElement getCrossmarkButton1() {
		return CrossmarkButton1;
	}

	public AndroidElement getWishlistDonebtn() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(wishlistDonebtn, "wishlistDonebtn");
		return wishlistDonebtn;
	}

	public AndroidElement getWishlistMoveToBag() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(wishlistMoveToBag, "WishlistMoveToBag");
		return wishlistMoveToBag;
	}

	public AndroidElement getCartIconFromWishlist() {
		return CartIconFromWishlist;
	}

	public AndroidElement getProductImage() {
		return ProductImage;
	}

	public AndroidElement getContinueShopping() {
		return ContinueShopping;
	}

	public AndroidElement getRemoveIcon() {
		return RemoveIcon;
	}

	public AndroidElement getmyntraLogo() {
		return myntraLogo;
	}

	public AndroidElement getDoneButton()  {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(DoneButton, "DoneButton");
		return DoneButton;
	}

	public AndroidElement getMoveToBaglnk() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(MoveToBaglnk, "MoveToBaglnk");
		return MoveToBaglnk;
	}

	public void clickOnAndroidElement(IOSElement AndroidElement, String reportContent) {
		AndroidElement.click();

	}

	public void ClickOnMoveToBag()  {
		objAndroidGenericMethods.clickOnAndroidElement(getMoveToBaglnk(), "MoveToBaglnk");
	}

	public void ClickOnDoneButton()  {
		objAndroidGenericMethods.clickOnAndroidElement(getDoneButton(), "DoneButton");
	}

	public void ClickOnRemoveIcon()  {
		objAndroidGenericMethods.clickOnAndroidElement(getRemoveIcon(), "RemoveIcon");
	}

	public void ClickOnContinueShopping()  {
		getContinueShopping().click();
	}

	public void clickOnMyntraLogo()  {
		objAndroidGenericMethods.clickOnAndroidElement(getmyntraLogo(), "Myntra Logo");
	}

	public void ClickonProductImage()  {
		getProductImage().click();
	}

	public void ClickOnCartIconFromWishlist()  {
		getCartIconFromWishlist().click();
	}

	public void clickWishListMoveToBag()  {
		objAndroidGenericMethods.clickOnAndroidElement(getWishlistMoveToBag(), "wishlistMoveToBag");
	}

	public void clickDoneWishListbtn()  {
		objAndroidGenericMethods.clickOnAndroidElement(getWishlistDonebtn(), "done button on wishlist page");

	}

	public void CrossmarkButton1() {
		getCrossmarkButton1().click();
	}

	/**
	 * This method is to reset wishlist
	 * 
	 * @author 300019227 Anu;
	 */
	/**
	 * Modified by : Anu
	 * Description : modified for loop, removed 'i++' since method is failed remove more than one product from Wishlist
	 */
	public void RemoveAllItemsFromWishlist() {
		List<WebElement> closeIconList = getProductListInWishlist();

		for (int i = 0; i < closeIconList.size();) {
			try{
			if(getEmptyWishlist().isDisplayed()){
				getmyntraLogo().click();
			}
			}
			catch(Exception e){
			CrossmarkButton1();}
		}
	}
	/**
	 * @ModifiedBy:-Rakesh  Reason: catch block had the same as try. Logic fails for empty wishlist condition
	 * @
	 */
	public void resetWishlist() {
		objHomepageObjects.clickOnWishlistButton();

		// try {
		// Empty wishlist Page
		try {
			//getEmptyWishlist().getText();
			RemoveAllItemsFromWishlist();
			clickOnMyntraLogo();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			clickOnMyntraLogo();
		}

	}

	@FindBy(className = "index-headingDiv")
	public AndroidElement wishlistPageTitle;

	@FindBy(className = "itemdetails-itemDetailsLabel")
	public AndroidElement wishlistproductTitle;

	@FindBy(className = "itemdetails-boldFont")
	public AndroidElement wishlistdiscountedPrice;

	@FindBy(className = "itemdetails-strike")
	public AndroidElement strikedPrice;

	@FindBy(className = "itemdetails-itemDetailsLabel")
	public AndroidElement productTitle;

	@FindBy(className = "itemdetails-boldFont")
	public AndroidElement discountedPrice;

	public AndroidElement getStrikedPrice() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(strikedPrice, "strikedPrice");
		return strikedPrice;
	}

	public AndroidElement getwishlistDiscountedPrice() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(discountedPrice, "discountedPrice");
		return discountedPrice;
	}

	public AndroidElement getwishlistProductTitle() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(productTitle, "productTitle");
		return productTitle;
	}

	public AndroidElement getWishlistPageTitle() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(wishlistPageTitle, "wishlistPageTitle");
		return wishlistPageTitle;
	}

	public void VerifyWishlistPageTitle() {
		objAndroidGenericMethods.VerifyStringShouldNotEmpty(getWishlistPageTitle(), "My Wishlist");
	}

	public void VerifywishlistProductTitle() {
		objAndroidGenericMethods.VerifyStringShouldNotEmpty(getwishlistProductTitle(), "Product Title");
	}

	public void VerifyDiscountedPrice() {
		objAndroidGenericMethods.VerifyStringShouldNotEmpty(getwishlistDiscountedPrice(), "Discounted Price");
	}

	public void VerifyStrikedPrice() {
		objAndroidGenericMethods.VerifyStringShouldNotEmpty(getStrikedPrice(), "Striked Price");
	}

	public void VerifySellingPrice() {
		objAndroidGenericMethods.VerifyStringShouldNotEmpty(getwishlistDiscountedPrice(), "Selling Price");
	}
	
	@FindBy(className="sizeselect-sizeButton")
	public List<AndroidElement> sizeButtons;
	
	public List<AndroidElement> getsizeButtons() {
		objAndroidGenericMethods.CheckAndroidElementsListFoundMWeb(sizeButtons, "sizeButtons");
		return sizeButtons;
	}
	
	public void ClickSizeButtons() {
		List<AndroidElement>	elements = (List<AndroidElement>) getsizeButtons();
		for (int i = 0; i < elements.size(); i++) {
			if (elements.get(i).isEnabled()) {
				elements.get(i).click();
				break;
			}
		}
		
	}
	/**
	 * Created by :Anu
	 * Description : Verification method for no. of products added in Wishlist, along with product name and product ID
	 */
	/**
	 * Modified by :Anu
	 * Description : Modified method to print product code
	 */
	public void VerfiyProductIsAddedToWishlist()	{
		try {

			if (getCrossmarkButton1().isDisplayed())

			{

				int productNumber = getProductListInWishlist().size();

				Reporter.log("Passed : "+productNumber+" Product is present in the Wishlist!");

				for (int i=1; i<= productNumber; i++)

				{

					WebElement productName = aDriver.findElement(By.xpath("(//div[@class='itemdetails-itemDetailsLabel'])["+ i + "]"));

					String ProductName = productName.getText();

					WebElement productcode = aDriver.findElement(By.xpath("(//div[contains(@id,'item')])["+ i + "]"));

					WebElement aTag =productcode.findElement(By.tagName("a"));

 					String str=aTag.getAttribute("href");

					String[] href=str.split("/");

					String ProductCode =href[6];

					Reporter.log("Passed : Product "+i+" saved to the Wishlist is "+ProductName+" and it's product code is "+ProductCode+" !");

				}

			}

		} catch (Exception e) {

			Reporter.log("Failed : No Products are present in the Wishlist to remove or move to the bag!");

		}
	}
}
