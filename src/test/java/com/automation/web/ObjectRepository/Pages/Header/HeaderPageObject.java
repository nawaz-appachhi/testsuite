package com.automation.web.ObjectRepository.Pages.Header;

import java.util.List;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;

import com.automation.core.web.GenericMethods;

/**
 * @author 300019227
 *
 */
public class HeaderPageObject {

	public WebElement element = null;
	public List<WebElement> elements;
	GenericMethods objGenericMethods;
	WebDriver driver;

	public HeaderPageObject(WebDriver driver) {
		super();
		PageFactory.initElements(driver, this);
		objGenericMethods = new GenericMethods(driver);
		this.driver = driver;
	}

	@FindAll({ @FindBy(className = "desktop-navContent") })
	public List<WebElement> AllMenuList;

	@FindAll({ @FindBy(className = "desktop-navBlock") })
	public List<WebElement> AllSubMenuList;

	@FindAll({ @FindBy(xpath = ".//div[@class='desktop-categoryContainer' and @data-group='men']/ul/li") })
	public List<WebElement> AllSubMenuListByMen;

	@FindAll({ @FindBy(xpath = ".//div[@class='desktop-categoryContainer' and @data-group='women']/ul/li") })
	public List<WebElement> AllSubMenuListByWomen;

	@FindAll({ @FindBy(xpath = ".//div[@class='desktop-categoryContainer' and @data-group='kids']/ul/li") })
	public List<WebElement> AllSubMenuListByKids;

	@FindAll({ @FindBy(xpath = ".//div[@class='desktop-categoryContainer' and @data-group='home-&-living']/ul/li") })
	public List<WebElement> AllSubMenuListByHomeAndLiving;

	@FindBy(className = "desktop-user")
	public WebElement UserIcon;

	@FindBy(className = "desktop-cart")
	public WebElement CartIcon;

	@FindBy(linkText = "SIGN UP")
	public WebElement SignUp;

	@FindBy(xpath = "//*[@class='desktop-userActionsContent']/div[2]/a[2]")
	public WebElement Login;

	@FindBy(className = "desktop-searchBar")
	public WebElement Search;

	@FindBy(className = "desktop-submit")
	public WebElement SearchLens;

	@FindBy(xpath = "//div[@class='desktop-getInLinks']/a[2]")
	public WebElement Wishlist;

	@FindBy(xpath = "//*[@id='mountRoot']/div/main/div/div[1]/div/div/div/div/div[1]/div/div/div/div/div/div/div/div/div/div/a/div/img")
	public WebElement smallBanner1;

	@FindBy(linkText = "Wishlist")
	public WebElement wishListUnderAccountMenu;

	@FindBy(xpath = "//span[@class='myntra-logo']")
	public WebElement MyntraHeaderLogofromCartPage;
	/**
	 * myntraheader logo
	 * modified by -shivaprasad
	 * old xpath for production environment-//a[@class='myntraweb-sprite desktop-logo sprites-logo']
	 * new xpath for sfqa env-//div[@class='header-sprite mlogo']
	 * Modified By Aishurya: Changed xpath as class name had spaces
	 */
	@FindBy(xpath = "//*[@class='desktop-logoContainer']/a")
	public WebElement MyntraHeaderLogo;

	@FindBy(xpath = "//div[text()='Wishlist']")
	public WebElement WishlistLink;

	@FindBy(xpath = "//div[text()='My Wishlist']")
	public WebElement MyWishlistlink;

	@FindBy(xpath = "//div[text()='Saved Addresses']")
	public WebElement SavedAddressesLink;

	@FindBy(xpath = "//header[@class='desktop-container']/div/a[3]")
	public WebElement ContactUs;
	
	/**
	 * Added by: Aishurya
	 * Reason: To get all Auto suggest search
	 */
	@FindAll({@FindBy(xpath = "//*[@class='desktop-group']/li")})
	public List<WebElement> AllAutosuggestList;
	
	public List<WebElement> getAllAutosuggestList() {
		return AllAutosuggestList;
	}

	/**
	 * Created by : Anu
	 * Description : Added locator for 'my orders'
	 */
	@FindBy(xpath="//div[@class='desktop-getInLinks']/a[@href='/my/orders']")
	public WebElement MyOrders;
	
	@FindBy(xpath = "//div[@data-track='logout']")
	public WebElement logout;

	public WebElement getLogout() {
		objGenericMethods.CheckWebElementFound(logout, "logout");
		return logout;
	}

	public WebElement getMyOrders() {
		objGenericMethods.CheckWebElementFound(MyOrders, "MyOrders");
		return MyOrders;
	}

	public WebElement getWishListUnderAccountMenu() {
		objGenericMethods.CheckWebElementFound(wishListUnderAccountMenu, "wishListUnderAccountMenu");
		return wishListUnderAccountMenu;
	}

	public WebElement getContactUs() {
		objGenericMethods.CheckWebElementFound(ContactUs, "ContactUs");
		return ContactUs;
	}

	public WebElement getSmallBanner1() {
		objGenericMethods.CheckWebElementFound(smallBanner1, "smallBanner1");
		return smallBanner1;
	}

	public WebElement getMyntraHome() {
		objGenericMethods.CheckWebElementFound(MyntraHeaderLogo, "MyntraHeaderLogo");
		return MyntraHeaderLogo;
	}

	public WebElement getWishlistLink() {
		objGenericMethods.CheckWebElementFound(Wishlist, "Wishlist");
		return Wishlist;
	}

	public WebElement getUserIcon() {
		objGenericMethods.CheckWebElementFound(UserIcon, "UserIcon");
		return UserIcon;
	}

	public WebElement getCartIcon() {
		objGenericMethods.CheckWebElementFound(CartIcon, "CartIcon");
		return CartIcon;
	}

	public List<WebElement> getAllMenuList() {
		objGenericMethods.CheckWebElementsListFound(AllMenuList, "AllMenuList");
		return AllMenuList;
	}

	public List<WebElement> getAllSubMenuList() {
		objGenericMethods.CheckWebElementsListFound(AllSubMenuList, "AllSubMenuList");
		return AllSubMenuList;
	}

	public List<WebElement> getAllSubMenuListByMen() {
		objGenericMethods.CheckWebElementsListFound(AllSubMenuListByMen, "AllSubMenuListByMen");
		return AllSubMenuListByMen;
	}

	public List<WebElement> getAllSubMenuListByWomen() {
		objGenericMethods.CheckWebElementsListFound(AllSubMenuListByWomen, "AllSubMenuListByWomen");
		return AllSubMenuListByWomen;
	}

	public List<WebElement> getAllSubMenuListByKids() {
		objGenericMethods.CheckWebElementsListFound(AllSubMenuListByKids, "AllSubMenuListByKids");
		return AllSubMenuListByKids;
	}

	public List<WebElement> getAllSubMenuListByHomeAndLiving() {
		objGenericMethods.CheckWebElementsListFound(AllSubMenuListByHomeAndLiving, "AllSubMenuListByHomeAndLiving");
		return AllSubMenuListByHomeAndLiving;
	}

	public WebElement getHeaderMenu(String menuName) {
		List<WebElement> elements = getAllMenuList();
		return objGenericMethods.getWebElementByName(elements, menuName);
	}

	public List<WebElement> getAllHorizontalMenuByMainMenu(String MainMenu) {
		if (MainMenu.equalsIgnoreCase("Men")) {
			elements = getAllSubMenuListByMen();
		} else if (MainMenu.equalsIgnoreCase("Women")) {
			elements = getAllSubMenuListByWomen();
		} else if (MainMenu.equalsIgnoreCase("Kids")) {
			elements = getAllSubMenuListByKids();
		} else if (MainMenu.equalsIgnoreCase("Home & Living")) {
			elements = getAllSubMenuListByHomeAndLiving();
		}
		return elements;
	}

	public WebElement getMyntraHeaderLogofromCartPage() {
		objGenericMethods.CheckWebElementFound(MyntraHeaderLogofromCartPage, "MyntraHeaderLogofromCartPage");
		return MyntraHeaderLogofromCartPage;
	}

	public WebElement getMyWishlistlink() {
		objGenericMethods.CheckWebElementFound(MyWishlistlink, "MyWishlistlink");
		return MyWishlistlink;
	}

	public WebElement getSavedAddressesLink() {
		objGenericMethods.CheckWebElementFound(SavedAddressesLink, "SavedAddressesLink");
		return SavedAddressesLink;
	}

	public WebElement getMyntraHeaderLogo() {
		objGenericMethods.CheckWebElementFound(MyntraHeaderLogo, "MyntraHeaderLogo");
		return MyntraHeaderLogo;
	}
	
	public WebElement getSignUp() {
		objGenericMethods.CheckWebElementFound(SignUp, "SignUp");
		return SignUp;
	}

	public WebElement getLogin() {
		objGenericMethods.CheckWebElementFound(Login, "Login");
		return Login;
	}

	public WebElement getSearch() {
		objGenericMethods.CheckWebElementFound(Search, "Search");
		return Search;
	}

	public WebElement getSearchLens() {
		objGenericMethods.CheckWebElementFound(SearchLens, "SearchLens");
		return SearchLens;
	}

	public WebElement getWishlist() {
		objGenericMethods.CheckWebElementFound(Wishlist, "Wishlist");
		return Wishlist;
	}
	
	/**
	 * ModifiedBy : Nitesh
	 * Description: Added WaitDriverWhenReady()
	 */

	public void LoginUnderUserIcon() {
		objGenericMethods.takeSnapShot();
		objGenericMethods.waitDriverWhenReady(getLogin(), "Login Option");
		getLogin().click();
		objGenericMethods.ReportClickEvent("Login");
	}

	/**
	 * 
	 * ModifiedBy : Neeraj
	 * Description: Removed Thread.sleep and added WaitDriverWhenReady()
	 */
	public void SearchInputBox(String searchItem) {
		objGenericMethods.waitDriverWhenReady(getSearch(), "Search Inputbox");
		objGenericMethods.takeSnapShot();
		getSearch().sendKeys(searchItem);
		objGenericMethods.ReportClickEvent("Search");
	}

	public void ClickOnSearchLens() {
		objGenericMethods.takeSnapShot();
		getSearchLens().click();
		objGenericMethods.ReportClickEvent("SearchLens");
	}

	/**
	 * 
	 * ModifiedBy : Neeraj
	 * Description: Removed Thread.sleep and added WaitDriverWhenReady()
	 */
	public void ClickOnCart() {
		objGenericMethods.takeSnapShot();
		objGenericMethods.waitDriverWhenReady(getCartIcon(), "Cart Icon");
		getCartIcon().click();
		objGenericMethods.ReportClickEvent("Cart Icon");
	}

	public void ClickOnWishlist() {
		objGenericMethods.takeSnapShot();
		getWishlistLink().click();
		objGenericMethods.ReportClickEvent("Wishlist Link");
	}

	public void ClickSmallBanner1() {
		objGenericMethods.takeSnapShot();
		getSmallBanner1().click();
		objGenericMethods.ReportClickEvent("Small Banner1");
	}

	public void ClickOnHome() {
		objGenericMethods.takeSnapShot();
		getMyntraHome().click();
		objGenericMethods.ReportClickEvent("Myntra Home");
	}
	public void ClickOnWishlistUnderAccountMenu() {
		objGenericMethods.takeSnapShot();
		getWishListUnderAccountMenu().click();
		objGenericMethods.ReportClickEvent("Wish List Under Account Menu");
	}

	public void ClickOnCartIcon() {
		objGenericMethods.takeSnapShot();
		getCartIcon().click();
		objGenericMethods.ReportClickEvent("Cart Icon");
	}

	public void ClickContactUs() {
		objGenericMethods.takeSnapShot();
		getContactUs().click();
		objGenericMethods.ReportClickEvent("Contact Us");
	}

	/**
	 * 
	 * ModifiedBy : Neeraj
	 * Description: Removed Thread.sleep and added WaitDriverWhenReady()
	 */
	public void ClickOnMyntraHeaderLogoFromCartPage() {
		objGenericMethods.takeSnapShot();
		objGenericMethods.waitDriverWhenReady(getMyntraHeaderLogofromCartPage(), "Myntra Logo");
		getMyntraHeaderLogofromCartPage().click();
		objGenericMethods.ReportClickEvent("Myntra Header Logo from CartPage");
	}

	public void ClickOnMyntraLogo() {
		objGenericMethods.takeSnapShot();
		getMyntraHeaderLogo().click();
		objGenericMethods.ReportClickEvent("Myntra Header Logo");
	}

	/**
	 * Modified By-Aishu - Added waitDriver()
	 */
	public void ClickonWishlistLink() {
		objGenericMethods.takeSnapShot();
		objGenericMethods.waitDriver(getWishlistLink(), "Wishlist link");
		getWishlistLink().click();
		objGenericMethods.ReportClickEvent("Myntra Header Logo");
	}

	public void ClickOnSavedAddressesLink() {
		objGenericMethods.takeSnapShot();
		getSavedAddressesLink().click();
		objGenericMethods.ReportClickEvent("Saved Addresses Link");
	}

	public void ClickOnMyWishlistLink() {
		objGenericMethods.takeSnapShot();
		getMyWishlistlink().click();
		objGenericMethods.ReportClickEvent("My Wishlist link");
	}

	/**
	 * 
	 * ModifiedBy : Neeraj
	 * Description: added WaitDriverWhenReady() and reporterEvent()
	 */
	public void clickOnSubMenuUnderMen(String SubMenuName)
	{
		List<WebElement> allSubMenuListByMen = getAllSubMenuListByMen();
		WebElement link =  objGenericMethods.getWebElementByName(allSubMenuListByMen, SubMenuName);
		objGenericMethods.waitDriverWhenReady(link, SubMenuName);
		link.click();
		objGenericMethods.ReportClickEvent(SubMenuName);
	}
	
	/**
	 * Created by : Anu
	 * Description : Added method for Click on 'MyOrders'
	 */
	public void ClickonMyOrders(){
		objGenericMethods.takeSnapShot();
		getMyOrders().click();
		objGenericMethods.ReportClickEvent("My Orders");
	}
	/**
	 * Added by: Aishurya
	 * Reason: To verify Auto suggest search
	 */
	public void VerifyAutoSuggestSearch() {
		List<WebElement> AutoSearchList =getAllAutosuggestList();
		if(AutoSearchList.size() <= 0)
		{
			Reporter.log("Failed : Auto-Suggestion list is not displayed!");
		}
		else
		{
			Reporter.log("Passed : "+ AutoSearchList.size() +" options are displayed in Auto-suggestion search list!");
			for(int i=0; i<AutoSearchList.size();i++) {
				objGenericMethods.VerifyStringShouldNotEmpty(AutoSearchList.get(i), "Auto Suggest Search");
			}
		}
	}
	
	public void clickLogout()	{
		objGenericMethods.HoverOnWebElement(getUserIcon());
		objGenericMethods.waitDriverWhenReady(getLogout(), "LogOut Option");
		getLogout().click();
	}

	public void verifyUserIsLoggedOut()	{
		objGenericMethods.HoverOnWebElement(getUserIcon());
		try {
			if (getLogin().getText().equalsIgnoreCase("Login"))
			{
				Reporter.log("Passed : User has logged out!");
			}
		} catch (Exception e) {
			Reporter.log("Failed : User is not logged out!");
		}
	}
	
	/**
	 * Added by - Nitesh
	 * method to verify session id before and after logout
	 */
	public void logoutAndVerifySessionId()	{
		Cookie LoginCookie= driver.manage().getCookieNamed("fox-user_uuid");
		String loginSessionId = LoginCookie.getValue();
		Reporter.log("Session Id before logout is : " + loginSessionId);
       
		clickLogout();
		verifyUserIsLoggedOut();
		
		Cookie LogoutCookie= driver.manage().getCookieNamed("fox-user_uuid");
		String logoutSessionId = LogoutCookie.getValue();
		Reporter.log("Session Id after logout is : " +logoutSessionId);
		if (loginSessionId.equalsIgnoreCase(logoutSessionId))
		{
			Reporter.log("Failed: Session Id's before and after logout are same!");
		}else{
			Reporter.log("Passed: Session Id's before and after logout are different!");
		}
	}
	
}
	
