package com.automation.web.ObjectRepository.Pages.WishlistPage;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;

import com.automation.core.web.GenericMethods;
import com.automation.web.ObjectRepository.Pages.Header.HeaderPageObject;
import com.automation.web.ObjectRepository.Pages.PLP.PLPPageObject;

public class WishlistPageObjects {

	public WebElement element = null;
	public List<WebElement> elements;
	GenericMethods objGenericMethods;
	HeaderPageObject objHeaderPageObject;
	PLPPageObject objPLPPageObject;
	WebDriver driver;

	/**
	 * @param driver
	 * Modified By : Neeraj
	 * Description: added this.driver
	 */
	public WishlistPageObjects(WebDriver driver) {
		super();
		PageFactory.initElements(driver, this);
		objGenericMethods = new GenericMethods(driver);
		objHeaderPageObject = new HeaderPageObject(driver);
		objPLPPageObject = new PLPPageObject(driver);
		this.driver = driver;
	}

	@FindBy(className = "itemcard-itemImage")
	public WebElement ItemImage;

	@FindAll({ @FindBy(className = "desktop-navContent") })
	public List<WebElement> AllMenuList;

	@FindBy(xpath = "//div[@id='item0']/div[3]/div[2]/a")
	public WebElement MoveToBagButton;

	@FindBy(linkText = "DONE")
	public WebElement DoneButton;

	@FindBy(xpath = "//div[text()='WISHLIST EMPTY']")
	public WebElement EmptyWishlist;

	@FindAll(@FindBy(xpath = "//div[@class='itemcard-removeIcon']"))
	public List<WebElement> ProductListInWishlist;

	@FindBy(xpath = "//div[@class='itemcard-removeIcon']")
	public WebElement CrossmarkButton1;

	@FindBy(xpath = "//div[@class='itemcard-itemActions']/div[@class ='itemcard-actionDiv']")
	public WebElement DoneBtn;

	
	/**
	 * Modify by : Neeraj
	 * Old locator : //button[@class='sizeselect-sizeButton']
	 * New Locator : //button[@class='sizeselect-sizeButton'] or //button[@class='sizeselect-sizeButton sizeselect-sizeButtonSelected']
	 * Description: Object was failing if ONESIZE size comes. Hence added OR condition 
	 */
	@FindAll({ @FindBy(xpath = "//button[@class='sizeselect-sizeButton']") })
	public List<WebElement> SizeButtons;
	
	@FindBy(xpath = "//div[@class='index-headingDiv']")
	public WebElement WishlistTitle;
	
	@FindBy(xpath = "//div[@id='item0']/div[3]/div[1]/div[1]")
	public WebElement WishlistProductName;
	
	@FindBy(xpath = "//span[@class='itemdetails-strike']")
	public WebElement ActualPrice;
	
	@FindBy(xpath = "//span[@class='itemdetails-boldFont']")
	public WebElement SellingPrice;
	
	@FindBy(xpath = "//span[@class='itemdetails-discountPercent']")
	public WebElement DiscountPercentage;
	
	@FindBy(xpath="//div[@class='itemdetails-itemDetailsLabel']")
	public List<WebElement> productNamelist;
	
	public List<WebElement> getProductName() {
		objGenericMethods.CheckWebElementsListFound(productNamelist, "productNamelist");
		return productNamelist;
	}

	public WebElement getDoneBtn() {
		objGenericMethods.CheckWebElementFound(DoneBtn, "DoneBtn");
		return DoneBtn;
	}

	public WebElement getCrossmarkButton1() {
		objGenericMethods.CheckWebElementFound(CrossmarkButton1, "CrossmarkButton1");
		return CrossmarkButton1;
	}

	public WebElement getItemImage() {
		objGenericMethods.CheckWebElementFound(ItemImage, "ItemImage");
		return ItemImage;
	}

	public WebElement getMoveToBagButton() {
		objGenericMethods.CheckWebElementFound(MoveToBagButton, "MoveToBagButton");
		return MoveToBagButton;
	}

	public List<WebElement> getProductListInWishlist() {
		objGenericMethods.CheckWebElementsListFound(ProductListInWishlist, "ProductListInWishlist");
		return ProductListInWishlist;
	}

	public WebElement getDoneButton() {
		objGenericMethods.CheckWebElementFound(DoneButton, "DoneButton");
		return DoneButton;
	}

	public WebElement getEmptyWishlist() {
		objGenericMethods.CheckWebElementFound(EmptyWishlist, "EmptyWishlist");
		return EmptyWishlist;
	}

	public List<WebElement> getSizeButtonContainer() {
		objGenericMethods.CheckWebElementsListFound(SizeButtons, "SizeButtons");
		return SizeButtons;
	}
	
	public WebElement getWishlistTitle() {
		objGenericMethods.CheckWebElementFound(WishlistTitle, "WishlistTitle");
		return WishlistTitle;
	}
	
	public WebElement getWishlistProductName() {
		objGenericMethods.CheckWebElementFound(WishlistProductName, "WishlistProductName");
		return WishlistProductName;
	}
	
	public WebElement getActualPrice() {
		objGenericMethods.CheckWebElementFound(ActualPrice, "ActualPrice");
		return ActualPrice;
	}

	public WebElement getSellingPrice() {
		objGenericMethods.CheckWebElementFound(SellingPrice, "SellingPrice");
		return SellingPrice;
	}
	
	public WebElement getDiscountPercentage() {
		objGenericMethods.CheckWebElementFound(DiscountPercentage, "DiscountPercentage");
		return DiscountPercentage;
	}
	
	public void EmptyWishlist() {
		if (IsWishlistEmpty()) {
			objGenericMethods.takeSnapShot();
			objHeaderPageObject.ClickOnHome();
			objGenericMethods.ReportClickEvent("Myntra Header Logo");
		} else {
			RemoveAllItemsFromWishlist();
		}
	}

	public void ClickItemImage() {
		objGenericMethods.takeSnapShot();
		getItemImage().click();
		objGenericMethods.ReportClickEvent("Item Image");
	}

	public void ClickDoneButton() {
		objGenericMethods.takeSnapShot();
		getDoneButton().click();
		objGenericMethods.ReportClickEvent("Done Button");
	}

	public void ClickMoveToBagButton() {
		objGenericMethods.takeSnapShot();
		getMoveToBagButton().click();
		objGenericMethods.ReportClickEvent("Move To Bag Button");
	}

	public void ClickDoneButtons() {
		objGenericMethods.takeSnapShot();
		getDoneButton().click();
		objGenericMethods.ReportClickEvent("Done Button");
	}

	public void CrossmarkButton1() {
		objGenericMethods.takeSnapShot();
		getCrossmarkButton1().click();
		objGenericMethods.ReportClickEvent("Crossmark Button 1");
	}

	public void RemoveAllItemsFromWishlist() {
		List<WebElement> closeIconList = getProductListInWishlist();

		for (int i = 0; i < closeIconList.size();) {

			CrossmarkButton1();
		}
	}

	public boolean IsWishlistEmpty() {
		try {
			getEmptyWishlist().getText();
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Wishlist has items");
			return false;
		}
	}

	/**
	 * 
	 * ModifiedBy : Neeraj
	 * Description: Removed Thread.sleep and added WaitDriverWhenReady() and added navigate.back()
	 * 
	 *  ModifiedBy : Nitesh
	 * Description: Added try catch block for click on Myntra logo and navigate back
	 */
	public void resetWishlist() {
		objGenericMethods.HoverOnWebElement(objHeaderPageObject.getUserIcon());
		objHeaderPageObject.ClickonWishlistLink();
		objGenericMethods.waitDriverWhenReady(getEmptyWishlist(), "Empty Wishlist Text");
		try {
			getEmptyWishlist().getText();
			Reporter.log("No products present in the wishlist to be removed!");
			try {
				objHeaderPageObject.ClickOnMyntraLogo();
			} catch (Exception e) {
				driver.navigate().back();
			}
		} catch (Exception e) {
			Reporter.log("Wishlist have Products to be removed!");
			RemoveAllItemsFromWishlist();
			try {
				getEmptyWishlist().getText();
				Reporter.log("Passed : Products are successfully removed from wishlist!");
				objHeaderPageObject.ClickOnMyntraLogo();
			} catch (Exception e1) {
				objGenericMethods.takeSnapShot();
				driver.navigate().back();
			}
		}
	}

	public void ClickSizeButtons() {
		List<WebElement> sizeButtons = getSizeButtonContainer();
		if(sizeButtons.size() != 0)
		{
			for (int i = 0; i < sizeButtons.size(); i++) {
				if (sizeButtons.get(i).isEnabled()) {
					objGenericMethods.takeSnapShot();
					sizeButtons.get(i).click();
					objGenericMethods.ReportClickEvent("Size Buttons");
					break;
				}
			}
		}
		else
		{
			System.out.println("ONESize found");
		}
	}
	public void VerifyWishlistTitle()
	{
		objGenericMethods.VerifyStringShouldNotEmpty(getWishlistTitle(), "My Wishlist Title");
	}
	
	public void VerifyWishlistProductName()
	{
		objGenericMethods.VerifyStringShouldNotEmpty(getWishlistProductName(), "Wishlist Product Name");
	}

	public void VerifyActualPrice() {
		objGenericMethods.VerifyStringShouldNotEmpty(getActualPrice(), "Actual Price");
	}
	
	public void VerifySellingPrice() {
		objGenericMethods.VerifyStringShouldNotEmpty(getSellingPrice(), "Selling Price");
	}

	public void VerifyDiscountPercentage() {
		objGenericMethods.VerifyStringShouldNotEmpty(getDiscountPercentage(), "Discount Percentage");
	}
	
	/**
	 * Added By: Nitesh
	 * Reason: Added verification for verifying whether product is moved to bag
	 */
	public void VerifyProductMovedFromWishlist()	{
		try {
			getEmptyWishlist().getText();
			Reporter.log("Passed : Products has been moved to Bag from Wishlist!");
		} catch (Exception e) {
		}
	}
	
	/**
	 * Added By: Nitesh
	 * Reason: Added verification for verifying product saved to wishlist
	 */
	
	public void VerfiyProductIsSavedToWishlist()	{
		try {
			if (getCrossmarkButton1().isDisplayed())
			{
				int productNumber = getProductName().size();
				Reporter.log("Passed : "+productNumber+" Product is present in the Wishlist!");
				for (int i=1; i<= productNumber; i++)
				{
					WebElement productName = driver.findElement(By.xpath("(//div[@class='itemdetails-itemDetailsLabel'])["+ i + "]"));
					String ProductName = productName.getText();
					WebElement productcode = driver.findElement(By.xpath("(//div[contains(@id,'item')])["+ i + "]"));
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
