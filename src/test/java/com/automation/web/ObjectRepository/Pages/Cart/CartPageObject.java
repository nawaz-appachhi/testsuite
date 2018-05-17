package com.automation.web.ObjectRepository.Pages.Cart;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.testng.Reporter;

import com.automation.core.web.GenericMethods;
import com.automation.web.ObjectRepository.Pages.Header.HeaderPageObject;
import com.automation.web.ObjectRepository.Pages.PLP.PLPPageObject;

/**
 * @author 300019224
 *
 */
public class CartPageObject {

	public WebElement element = null;
	public List<WebElement> elements;
	WebDriver driver;
	GenericMethods objGenericMethods;
	HeaderPageObject objHeaderPageObject;
	PLPPageObject objPLPPageObject;

	public CartPageObject(WebDriver driver) {
		super();
		PageFactory.initElements(driver, this);
		this.driver = driver;
		objGenericMethods = new GenericMethods(driver);
		objHeaderPageObject = new HeaderPageObject(driver);
		objPLPPageObject = new PLPPageObject(driver);
	}

	@FindBy(xpath = ".//*[text()='PLACE ORDER']")
	public WebElement PlaceOrder;

	@FindBy(xpath = "//button[text()='REMOVE']")
	public WebElement Remove;

	@FindBy(xpath = "//span[text()='MOVE TO WISHLIST']")
	public WebElement MoveToWishlist;

	/**
	 * Modified by Aishurya:was not detecting linkText, replaced with xpath
	 */
	@FindBy(xpath ="//span[text()='Add more from wishlist']")
	public WebElement AddMoreFromWishlistLink;
	
	/**
	 * Added by Aishurya: when cart is empty, add from wishlist
	 */
	@FindBy(xpath ="//*[@class='m-hide']/a")
	public WebElement AddItemsFromWishlistLink;

	public WebElement getAddItemsFromWishlist() {
		return AddItemsFromWishlistLink;
	}

	@FindBy(linkText = "Apply Coupon")
	public WebElement applyCouponBtn;

	@FindBy(xpath = "//*[@class='m-hide gift-wrap-checkbox']")
	public WebElement giftWrapCheckbox;

	@FindBy(xpath = "//div[@class='btns']/button[2]")
	public WebElement SaveApplyCoupons;

	@FindBy(className = "icon")
	public WebElement SizeDropDown;

	@FindBy(xpath = "//*[@id='gift-to']")
	public WebElement RecepientName;

	@FindBy(xpath = "//*[@id='gift-msg']")
	public WebElement GiftMessage;

	@FindBy(xpath = "//*[@id='gift-from']")
	public WebElement SenderName;

	@FindBy(xpath = "//*[@id='lb-gift-wrap']/div[2]/button[2]")
	public WebElement SaveGiftWrap;

	@FindBy(xpath = "//span[text()='Need help ? Contact Us']")
	public WebElement contactUsLink;

	@FindBy(xpath = "//span[@class='mk-custom-drop-down size  ']/span[@class='value c-dblue']")
	public WebElement sizeTextDisplayed;

	@FindAll(@FindBy(xpath = "//div[@class='sizes']/button[contains(@class,'btn size-btn-group size-btn')]"))
	public List<WebElement> sizesInsideDrpdwn;

	@FindAll(@FindBy(xpath = "//div[@class='quantity']/button[contains(@class,'sel-qty qty-btn-group')]"))
	public List<WebElement> qtyInsideDrpdwn;

	@FindBy(xpath = "//div[@class='product-combo']")
	public WebElement freeGift;

	@FindBy(xpath = "//span[text()='REMOVE']")
	public WebElement Removelink;

	@FindBy(xpath = "//span[@class='mk-custom-drop-down qty  ']/span[@class='icon']")
	public WebElement qtyDrpdwnicon;

	@FindBy(xpath = "//div[text()='Your Shopping Bag is empty']")
	public WebElement EmptyCart;

	@FindBy(xpath = "//div[@class='prod-set']/.//span[text()='REMOVE']")
	public List<WebElement> ProductSetList;

	@FindBy(xpath = "//span[@class='myntra-logo']")
	public WebElement CartToHome;

	@FindBy(xpath = "//div[@class='edit-move-delete']/div/span[@class='move-item move-item-text']")
	public WebElement moveTowishList;

	@FindBy(xpath = "//div[@class='btns']/button[1]")
	public WebElement CancelButton;

	@FindBy(xpath="//span[@class='total-rupees fw-600 c-dblue']")
	public WebElement orderTotal;
	
	//*[@id="prod-item-1517125499"]/div[2]/div[3]/div[1]/span[3]
	
	@FindBy(xpath="//*[@class='sel-qty qty-btn-group  ' and @value='2']")
	public WebElement add2Qty;

	public WebElement georderTotal() {
		return orderTotal;
	}
	
	@FindBy(className="combo-text")
	public WebElement buy1Get1ComboSuccessText;
	
	public WebElement getBuy1Get1ComboSuccessText() {
		return buy1Get1ComboSuccessText;
	}

	public WebElement getAdd2Qty() {
		return add2Qty;
	}

	@FindBy(xpath = "(//div[@class='mk-checkout-header'])[1]")
	public WebElement ShoppingBagTitle;

	@FindBy(xpath = "(//div[@class='prod-name'])[1]")
	public WebElement ProductTitle;

	@FindBy(xpath = "(//div[@class='c-dblue total-rupees'])[1]")
	public WebElement DiscountedPrice;

	@FindBy(xpath = "(//div[@class='discount m-hide']/span)[2]")
	public WebElement StrikedPrice;

	@FindBy(xpath = "//div[@class='col1']/a/img")
	public WebElement ProductImage;

	@FindBy(xpath = "(//div[@class='total-amount fw-600 c-dblue b-white br-b-grey']/span)[2]")
	public WebElement OrderTotal;
	
	@FindBy(xpath = "//div[@class='cart-section']/div[1]/div[2]/a")
	public WebElement addItemFromWishlist;
	
	@FindBy(xpath = "//div[@class='total-price']")
	public WebElement totalPrice;
	
	@FindBy(xpath = "//button[@class='close']")
	public WebElement giftWrapCloseButton;
	
	@FindBy(xpath = "//*[text()='Your order will arrive gift wrapped']")
	public WebElement giftWrapSuccessMessage;
	
	/**
	 * Created by : Anu
	 * Description : Added locator for entering Coupon code
	 */
	@FindBy(xpath="//input[@name='coupon_code']")
	public WebElement coupon_Code;
	
	@FindBy(xpath="//div[@class='prod-name']")
	public List<WebElement> productNamelist;
	
	public List<WebElement> getProductName() {
		objGenericMethods.CheckWebElementsListFound(productNamelist, "prodname");
		return productNamelist;
	}

	public WebElement getCoupon_Code() {
		objGenericMethods.CheckWebElementFound(coupon_Code, "coupon_Code");
		return coupon_Code;
	}

	public void setCoupon_Code(WebElement coupon_Code) {
		this.coupon_Code = coupon_Code;
	}

	public WebElement getGiftWrapSuccessMessage() {
		objGenericMethods.CheckWebElementFound(giftWrapSuccessMessage, "giftWrapSuccessMessage");
		return giftWrapSuccessMessage;
	}
	
	public WebElement getGiftWrapCloseButton() {
		objGenericMethods.CheckWebElementFound(giftWrapCloseButton, "giftWrapCloseButton");
		return giftWrapCloseButton;
	}

	public WebElement getAddItemFromWishlist() {
		objGenericMethods.CheckWebElementFound(addItemFromWishlist, "addItemFromWishlist");
		return addItemFromWishlist;
	}

	public WebElement getOrderTotal() {
		objGenericMethods.CheckWebElementFound(OrderTotal, "OrderTotal");
		return OrderTotal;
	}

	public WebElement getProductImage() {
		objGenericMethods.CheckWebElementFound(ProductImage, "ProductImage");
		return ProductImage;
	}

	public WebElement getStrikedPrice() {
		objGenericMethods.CheckWebElementFound(StrikedPrice, "StrikedPrice");
		return StrikedPrice;
	}

	public WebElement getDiscountedPrice() {
		objGenericMethods.CheckWebElementFound(DiscountedPrice, "DiscountedPrice");
		return DiscountedPrice;
	}

	public WebElement getProductTitle() {
		objGenericMethods.CheckWebElementFound(ProductTitle, "ProductTitle");
		return ProductTitle;
	}

	public WebElement getShoppingBagTitle() {

		objGenericMethods.CheckWebElementFound(ShoppingBagTitle, "ShoppingBagTitle");
		return ShoppingBagTitle;
	}

	public WebElement getCancelButton() {
		objGenericMethods.CheckWebElementFound(CancelButton, "CancelButton");
		return CancelButton;
	}

	public WebElement getMoveTowishList() {
		objGenericMethods.CheckWebElementFound(moveTowishList, "moveTowishList");
		return moveTowishList;
	}

	public WebElement getSaveGiftWrap() {
		objGenericMethods.CheckWebElementFound(SaveGiftWrap, "SaveGiftWrap");
		return SaveGiftWrap;
	}

	public WebElement getRecepientName() {
		objGenericMethods.CheckWebElementFound(RecepientName, "RecepientName");
		return RecepientName;
	}

	public WebElement getCartToHome() {
		objGenericMethods.CheckWebElementFound(CartToHome, "CartToHome");
		return CartToHome;
	}

	public WebElement getGiftMessage() {
		objGenericMethods.CheckWebElementFound(GiftMessage, "GiftMessage");
		return GiftMessage;
	}

	public WebElement getSenderName() {
		objGenericMethods.CheckWebElementFound(SenderName, "SenderName");
		return SenderName;
	}

	public WebElement getSizeDropDown() {
		objGenericMethods.CheckWebElementFound(SizeDropDown, "SizeDropDown");
		return SizeDropDown;
	}

	public WebElement getSaveApplyCoupons() {
		objGenericMethods.CheckWebElementFound(SaveApplyCoupons, "SaveApplyCoupons");
		return SaveApplyCoupons;
	}

	public WebElement getApplyCouponBtn() {
		objGenericMethods.CheckWebElementFound(applyCouponBtn, "applyCouponBtn");
		return applyCouponBtn;
	}

	public WebElement getPlaceOrder() {
		objGenericMethods.CheckWebElementFound(PlaceOrder, "PlaceOrder");
		return PlaceOrder;
	}

	public WebElement getRemove() {
		objGenericMethods.CheckWebElementFound(Remove, "Remove");
		return Remove;
	}

	public WebElement getMoveToWishlist() {
		objGenericMethods.CheckWebElementFound(MoveToWishlist, "MoveToWishlist");
		return MoveToWishlist;
	}

	public WebElement getAddMoreFromWishlist() {
		objGenericMethods.CheckWebElementFound(AddMoreFromWishlistLink, "AddMoreFromWishlist");
		return AddMoreFromWishlistLink;
	}

	public WebElement getGiftPackCheckbox() {
		objGenericMethods.CheckWebElementFound(giftWrapCheckbox, "giftWrapCheckbox");
		return giftWrapCheckbox;
	}

	public WebElement getEmptyCart() {
		objGenericMethods.CheckWebElementFound(EmptyCart, "EmptyCart");
		return EmptyCart;
	}

	public WebElement getQtyDrpdwnicon() {
		objGenericMethods.CheckWebElementFound(qtyDrpdwnicon, "qtyDrpdwnicon");
		return qtyDrpdwnicon;
	}

	public WebElement getRemovelink() {
		objGenericMethods.CheckWebElementFound(Removelink, "Removelink");
		return Removelink;
	}

	public WebElement getFreeGift() {
		objGenericMethods.CheckWebElementFound(freeGift, "freeGift");
		return freeGift;
	}

	public List<WebElement> getProductSetList() {
		objGenericMethods.CheckWebElementsListFound(ProductSetList, "ProductSetList");
		return ProductSetList;
	}

	public WebElement getContactUsLink() {
		objGenericMethods.CheckWebElementFound(contactUsLink, "contactUsLink");
		return contactUsLink;
	}

	public WebElement getSizeTextDisplayed() {
		objGenericMethods.CheckWebElementFound(sizeTextDisplayed, "sizeTextDisplayed");
		return sizeTextDisplayed;
	}

	public void GoToHome() {
		objGenericMethods.takeSnapShot();
		getCartToHome().click();
		objGenericMethods.ReportClickEvent("CartToHome");
	}

	public void giftPackCheckbox() {
		objGenericMethods.takeSnapShot();
		getGiftPackCheckbox().click();
		objGenericMethods.ReportClickEvent("giftPackCheckbox");
	}

	public void ClickApplyCouponBtn() {
		objGenericMethods.takeSnapShot();
		getApplyCouponBtn().click();
		objGenericMethods.ReportClickEvent("ApplyCouponBtn");

	}

	public void ClickToAddMoreFromWishlist() {
		objGenericMethods.takeSnapShot();
		try {
			objGenericMethods.waitDriver(AddMoreFromWishlistLink, "AddMoreFromWishlistLink");
			getAddMoreFromWishlist().click();
		} catch (Exception e) {
			objGenericMethods.waitDriver(AddItemsFromWishlistLink, "AddMoreFromWishlistLink");
			getAddItemsFromWishlist().click();
		}
		objGenericMethods.ReportClickEvent("AddMoreFromWishlist");
	}

	public void ClickMoveToWishlist() {
		objGenericMethods.takeSnapShot();
		getMoveToWishlist().click();
		objGenericMethods.ReportClickEvent("MoveToWishlist");
	}

	public void ClickToRemove() {
		objGenericMethods.takeSnapShot();
		getRemove().click();
		objGenericMethods.ReportClickEvent("Remove");
	}

	/**
	 * Modified By:Aishurya
	 * Reason: Added wait after place order
	 * Modified By:Aishurya
	 * Reason: Added waitDriver().N.B.Thread sleep required if flow has gift wrap.
	 * 
	 * 
	 * ModifiedBy : Neeraj
	 * Description: Removed Thread.sleep and added WaitDriverWhenReady()
	 */
	public void ClickPlaceOrder() {
		objGenericMethods.takeSnapShot();
		objGenericMethods.waitDriverWhenReady(PlaceOrder, "Placeorder Button");
		try {
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			driver.findElement(By.xpath("//div[@class='myntraPlaceOrder']"));
		} catch (Exception e) {
		}
		getPlaceOrder().click();
		objGenericMethods.ReportClickEvent("PlaceOrder");
	}

	public void ClickToApplyCoupon() {
		objGenericMethods.takeSnapShot();
		getSaveApplyCoupons().click();
		objGenericMethods.ReportClickEvent("SaveApplyCoupons");
	}
	/**
	 * Created by : Anu
	 * Description : Added method for entering Coupon code
	 */
	public void EnterCouponCode(String couponCode)
	{
		getCoupon_Code().sendKeys(couponCode);
	}

	public void ClickSizeDropDown() {
		objGenericMethods.takeSnapShot();
		getSizeDropDown().click();
		objGenericMethods.ReportClickEvent("SizeDropDown");
	}

	public void moveTowishList() {
		objGenericMethods.takeSnapShot();
		getMoveTowishList().click();
		objGenericMethods.ReportClickEvent("MoveTowishList");
	}

	public void ClickCancelButton() {
		objGenericMethods.takeSnapShot();
		getCancelButton().click();
		objGenericMethods.ReportClickEvent("CancelButton");
	}

	public void ClickContactUS() {
		objGenericMethods.takeSnapShot();
		getContactUsLink().click();
		objGenericMethods.ReportClickEvent("ContactUsLink");
	}
	
	public void ClickGiftWrapCloseButton() {
		objGenericMethods.takeSnapShot();
		getGiftWrapCloseButton().click();
		objGenericMethods.ReportClickEvent("Click on Gift Wrap pop up Close Button");
	}
	
	public void VerifyGiftWrapSuccessMessage() {
		objGenericMethods.verifyTwoStringData(getGiftWrapSuccessMessage().getText(), "Your order will arrive gift wrapped");
	}

	public void VerifyMyShoppingBagTitle() {
		objGenericMethods.VerifyStringShouldNotEmpty(getShoppingBagTitle(), "ShoppingTitle");
	}

	public void VerifyMyProductTitle() {
		objGenericMethods.VerifyStringShouldNotEmpty(getProductTitle(), "ProductTitle");
	}

	public void VerifyDiscountedPrice() {

		objGenericMethods.VerifyTwoString(getDiscountedPrice(),objPLPPageObject.DISCOUNTED_PRICE);
	}

	public void VerifyOrderTotal() {
		objGenericMethods.VerifyNumberShouldNotZeroOrLess(OrderTotal);
	}

	/**
	 * 
	 * ModifiedBy : Neeraj
	 * Description: Removed Thread.sleep and added WaitDriverWhenReady()
	 * 
	 * ModifiedBy : Nitesh
	 * Description: added wait between two products removal
	 */
	public void ClickRemoveLink() {
		List<WebElement> removeLinkList = getProductSetList();
	//	System.out.println("Total items found : " + removeLinkList.size());
		for (int i = 0; i < removeLinkList.size();) {
			System.out.println("Removing Product no: + " + i);
			objGenericMethods.takeSnapShot();
			objGenericMethods.waitDriverWhenReady(getRemovelink(), "Remove Link");
			getRemovelink().click();
			objGenericMethods.ReportClickEvent("Cart Remove link");
			objGenericMethods.waitDriverWhenReady(getRemove(), "Cart Remove button");
			objGenericMethods.takeSnapShot();
			getRemove().click();
			objGenericMethods.ReportClickEvent("Pop up Remove button");
			System.out.println("Removed Successfully Product no: + " + i);
			try {
				driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
				driver.findElement(By.xpath("//div[@class='myntraClass']"));
			} catch (Exception e) {
			}
		}
	}

	public void selectsize() {
		Select sizeDrpDwn = new Select(driver.findElement(By.className("mk-custom-drop-down size  ")));
		sizeDrpDwn.selectByVisibleText("");
	}

	public void selectquantity() {
		Select qtyDrpDwn = new Select(driver.findElement(By.className("mk-custom-drop-down qty  ")));
		qtyDrpDwn.selectByVisibleText("");
	}

	/**
	 * 
	 * ModifiedBy : Neeraj
	 * Description: Removed Thread.sleep and added WaitDriverWhenReady()
	 * 
	 * ModifiedBy : Nitesh
	 * Reason: Added verification step for gift wrap, as it failed to click on Place order
	 */
	public void SendGiftCard(String RecepientName, String GiftMessage, String SenderName) {
		getRecepientName().click();
		getRecepientName().sendKeys(RecepientName);
		getGiftMessage().sendKeys(GiftMessage);
		getSenderName().sendKeys(SenderName);
		objGenericMethods.takeSnapShot();
		getSaveGiftWrap().click();
		objGenericMethods.ReportClickEvent("SaveGiftWrap");
		try {
			VerifyGiftWrapSuccessMessage();
			ClickGiftWrapCloseButton();
			Reporter.log("Passed : Gift Wrap Success Message is displayed!");
		} catch (Exception e) {
			Reporter.log("Failed : Gift Wrap Success Message is not displayed!");
		}
	}

	public void VerifyFreeGiftFromCart() {
		if (getFreeGift().isDisplayed()) {
			WebElement freeGiftElement = getFreeGift();
			String IdentifyFreeGift = freeGiftElement.getText();
			Reporter.log(IdentifyFreeGift + "Free Gift is applicable");
		}
	}

	public boolean isCartEmpty() {
		try {
			getEmptyCart().getText();
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Cart has items");
			return false;
		}

	}
	
	/**
	 * Modified by Nitesh
	 * Removed extra action to hover on user - icon as it was not required
	 * Removed commented lines from the method
	 * @throws InterruptedException
	 * 
	 * 
	 * ModifiedBy : Neeraj
	 * Description: Added navigateBack()
	 * 
	 * ModifiedBy : Nitesh
	 * Description: Added try catch block for click on myntra logo and navigate back, 
	 * 				as it was unable to remove products from cart due to fast actions
	 */
	public void resetCart()  {
		objHeaderPageObject.ClickOnCart();

		try {
			getEmptyCart().getText();
			Reporter.log("No products present in the Cart to be removed!");
			try {
				objHeaderPageObject.ClickOnMyntraHeaderLogoFromCartPage();
			} catch (Exception e) {
				driver.navigate().back();
			}
		} catch (Exception e) {
			Reporter.log("Products to be removed are available in the Cart!");
			ClickRemoveLink();
			try {
				getEmptyCart().getText();
				Reporter.log("Passed : Products are removed successfully from the Cart!");
				objHeaderPageObject.ClickOnMyntraHeaderLogoFromCartPage();
			} catch (Exception e1) {
				objGenericMethods.takeSnapShot();
				driver.navigate().back();
			}
		}
	}
	
	public void AddQuantity()
	{
		getQtyDrpdwnicon().click();
		getAdd2Qty().click();
		
	}
	
	public void VerifyBuy1Get1SuccessMessage()
	{
		String msg = getBuy1Get1ComboSuccessText().getText();
		if(msg.contains("You got special combo discount of"))
		{
			Reporter.log("Passed :: Buy 1 Get 1 Product Successfully Applied and message displayed as " + getBuy1Get1ComboSuccessText().getText());
		}
		else
		{
			Reporter.log("Failed :: Buy 1 Get 1 Product not Applied");
		}
	}
	
	public void ClickToAddItemFromWIshlist(){
		objGenericMethods.takeSnapShot();
		getAddItemFromWishlist().click();
		objGenericMethods.ReportClickEvent("Add Item From Wishlist");
	}
	
	public WebElement getTotalPrice() {
		objGenericMethods.CheckWebElementFound(totalPrice, "totalPrice");
		return totalPrice;
	}
	
	/**
	 * Added By - Nitesh
	 * Method to verify whether products are added to bad or not
	 * Modified by _ Nitesh, 
	 * Added Try Catch to get only failure message to be displayed in test report
	 * Modified by _ Nitesh, 
	 * modified method to print count, name and product id of products present in the cart.
	 */
	
	public void VerfiyProductIsAddedToCart()	{
		try {
			if (getRemovelink().getText() != null)
			{
				int productNumber = getProductName().size();
				Reporter.log("Passed : "+productNumber+" Product is present in Cart!");
				for (int i=1; i<= productNumber; i++)
				{
					WebElement productName = driver.findElement(By.xpath("(//div[@class='prod-name'])["+ i + "]"));
					String ProductName = productName.getText();
					WebElement productcode = driver.findElement(By.xpath("(//div[contains(@id,'prod-item-')])["+ i + "]"));
					WebElement aTag =productcode.findElement(By.tagName("a"));
					String str=aTag.getAttribute("href");
					String[] href=str.split("/");
					String ProductCode =href[6];
					Reporter.log("Passed : Product "+i+" added to the Cart is "+ProductName+" and it's product code is "+ProductCode+" !");
				}
			}
		} catch (Exception e) {
			Reporter.log("Failed : No Products are present in the Cart to place Order!");
		}
	}
	
	/**
	 * Added By - Nitesh
	 * Method to verify Total price availability in cart page
	 * 
	 * Modified by _ Nitesh, 
	 * Added Try Catch to get only failure message to be displayed in test report
	 */
	
	public void VerfiyTotalPrice()	{
		try {
			String totalPrice = getTotalPrice().getText();
			int TotalPrice = Integer.parseInt( totalPrice.replace("Total: Rs. ", "").replace(",", ""));
			if (TotalPrice != 0)
			{
				Reporter.log("Passed : Total price of products added to cart is Rs. " + TotalPrice + " !");
			}
			
		} catch (Exception e) {
			Reporter.log("Failed : Total price of products added to cart is not displayed!");
		}
		
	}
	
	/**
	 * Added by - Nitesh - Method to change quantity of product
	 * @return
	 */
	public List<WebElement> getQtyInsideDrpdwn() {
		objGenericMethods.CheckWebElementsListFound(qtyInsideDrpdwn, "qtyInsideDrpdwn");
		return qtyInsideDrpdwn;
	}
	
	public void ChangeQuantity()	{
		getQtyDrpdwnicon().click();
		List<WebElement> quantityList = getQtyInsideDrpdwn();
		int s = quantityList.size();
		WebElement selectQuantity = driver.findElement(By.xpath("(//div[@class='quantity']/button)[" + (s-1) +"]"));
		objGenericMethods.takeSnapShot();
		selectQuantity.click();
		objGenericMethods.ReportClickEvent("select quantity");
		String selectedSize = selectQuantity.getText();
		
		if ( Integer.parseInt(selectedSize)  == s-1)
		{
			Reporter.log("Passed : Quantity of products is changed to: " + selectedSize + " in cart page!");
		}else{
			Reporter.log("Failed : Unable to change the Quantity of products in cart page!");
		}
	}
}


