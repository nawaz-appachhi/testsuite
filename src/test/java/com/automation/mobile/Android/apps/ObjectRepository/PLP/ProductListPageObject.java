package com.automation.mobile.Android.apps.ObjectRepository.PLP;

import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.Reporter;
import com.automation.core.mobile.Android.AndroidGenericMethods;

import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.AndroidKeyCode;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class ProductListPageObject {
	AndroidElement productElement = null;
	AndroidGenericMethods objAndroidGenericMethods;
	AndroidDriver<AndroidElement> aDriver;

	public ProductListPageObject(AndroidDriver<AndroidElement> aDriver) {
		this.aDriver = aDriver;
		PageFactory.initElements(new AppiumFieldDecorator(aDriver), this);
		objAndroidGenericMethods = new AndroidGenericMethods(aDriver);
	}

	/**
	 * object for ok button displayed on product image for the first time
	 * 
	 * @author 300021278 -Rakesh
	 */
	@FindBy(id = "com.myntra.android:id/tv_ok_button")
	public AndroidElement okButton;

	/**
	 * object for shop bag button displayed in product list page right side headder
	 * 
	 * @author 300021278 -Rakesh
	 */
	@FindBy(id = "com.myntra.android:id/action_cart")
	public AndroidElement RHBagbtn;

	/**
	 * First Product element is identified.
	 * 
	 * @author 300021278 -Rakesh Modified by Rakesh Reason: As path was till parent;
	 *         added till child "/android.widget.TextView"
	 */
	@FindAll({ @FindBy(xpath = "//android.widget.RelativeLayout[@index='1']/android.widget.TextView") })
	public List<AndroidElement> firstProductDetails;

	/**
	 * Object Identified the sortfilter slider element in PL page;
	 * 
	 * @author 300021278 -Rakesh
	 */
	@FindBy(xpath = "//android.widget.RelativeLayout[@resource-id='com.myntra.android:id/guide_filter']")
	public AndroidElement sortFilterButton;

	/**
	 * Identified the sort element in PL page displayed with filter without slider;
	 * 
	 * @author 300021278 -Rakesh
	 */
	//@FindBy(xpath = "//android.widget.LinearLayout[@resource-id='com.myntra.android:id/ll_product_list_bottombar']/android.widget.LinearLayout/android.widget.LinearLayout")
	@FindBy(xpath="//android.widget.ImageView[@resource-id='com.myntra.android:id/iv_product_list_sort_icon']")
	public AndroidElement sortButton;

	/**
	 * Identified the filter element in PL page;
	 * 
	 * @author 300021278 -Rakesh
	 */
	@FindBy(xpath = "//android.widget.TextView[@resource-id='com.myntra.android:id/ttv_product_list_filter_label']")
	public AndroidElement filterBtn;

	/**
	 * object identified for offer and discounts displayed in filter page
	 * 
	 * @author 300021278 -Rakesh
	 */
	@FindBy(xpath = "//android.widget.TextView[@text='Offers & Discounts' and @resource-id='com.myntra.android:id/tv_search_filter_title']")
	public AndroidElement offerAndDiscounts;

	/**
	 * object identified for first percentage button displayed in offer and
	 * discounts in filter page
	 * 
	 * @author 300021278 -Rakesh
	 */
	@FindBy(xpath = "//android.widget.RadioButton[@text='30% and higher' and @resource-id='com.myntra.android:id/radio_filter_detail']")
	public AndroidElement percentageRadioButton;

	/**
	 * Identified the WhatsNew sort radio button element in sort PL page;
	 * 
	 * @author 300021278 -Rakesh
	 */
	@FindBy(xpath = "//android.widget.RadioButton[@resource-id='com.myntra.android:id/button_sort_whatsnew']")
	public AndroidElement sortByWhatsNewRadioButton;

	/**
	 * Identified the WhatsNew sort section displayed in sort PL page;
	 * 
	 * @author 300021278 -Rakesh
	 */
	@FindBy(id = "com.myntra.android:id/btn_sort_whatsnew")
	public AndroidElement sortByWhatsNewbtn;

	/**
	 * Identified the Discount radio button sort element in sort PL page;
	 * 
	 * @author 300021278 -Rakesh
	 */
	@FindBy(xpath = "//android.widget.RadioButton[@resource-id='com.myntra.android:id/button_sort_discount' and @index='6']")
	public AndroidElement sortByDiscountbtn;
	/**
	 * Object identified for discount section displayed after clicking on sort
	 * button;
	 * 
	 * @author 300021278 -Rakesh
	 */
	@FindBy(xpath = "//android.widget.TextView[@resource-id='com.myntra.android:id/btn_sort_discount']")
	public AndroidElement sortDiscountSectionbtn;

	/**
	 * Object identified for popularity section displayed after clicking on sort
	 * button;
	 * 
	 * @author 300021278 -Rakesh
	 */
	@FindBy(xpath = "//android.widget.RadioButton[@resource-id='com.myntra.android:id/button_sort_popularity']")
	public AndroidElement sortPopularitySectionbtn;

	/**
	 * 
	 */
	@FindBy(xpath = "//android.widget.TextView[@resource-id='com.myntra.android:id/btn_sort_price_asc']")
	public AndroidElement sortPriceLowToHigh;

	/**
	 * Identified the Apply sort element in sort PL page;
	 */
	@FindBy(xpath = "//android.widget.Button[@resource-id='com.myntra.android:id/action_searchfilter_apply']")
	public AndroidElement sortApplybtn;
	/**
	 * Identified the Price sort element in sort PL page;
	 */
	@FindBy(xpath = "//*[@resource-id='com.myntra.android:id/tv_search_filter_title'][@text='Price']")
	public AndroidElement pricebtn;
	/**
	 * Identified the Size/Age sort element in sort PL page;
	 */
	@FindBy(xpath = "//*[@resource-id='com.myntra.android:id/tv_search_filter_title'][@text='Price']")
	public AndroidElement Sizebtn;
	/**
	 * Identified the FiLter elements displayed in PL page; which contains
	 * Categories,
	 *
	 */
	@FindAll({ @FindBy(xpath = "//*[@resource-id='com.myntra.android:id/guide_rl' and @index ='2']") })
	public List<AndroidElement> FilterSliderbtn;
	/**
	 * Identified the Right header elements in PL page; which contains Search,
	 * Cart,Notifications,Wishlist.
	 */
	@FindAll({ @FindBy(xpath = "//android.widget.ImageButton") })
	public List<AndroidElement> RightHeaderButtons;

	@FindAll({ @FindBy(id = "com.myntra.android:id/action_cart") })
	public List<AndroidElement> buttons;

	@FindBy(xpath = "//android.widget.TextView[@text='Fabric']")
	public AndroidElement febricbtn;

	@FindBy(xpath = "//android.widget.CheckBox[@text='cotton']")
	public AndroidElement cottonbtn;
	// @FindBy(xpath="//android.widget.RadioButton[@text='Price — low to high' and
	// resource-id='com.myntra.android:id/button_sort_price_asc']")
	@FindBy(xpath = "//android.widget.RadioButton[@resource-id='com.myntra.android:id/button_sort_price_desc']")
	public AndroidElement price;

	@FindBy(xpath = "//android.widget.ImageButton[@content-desc='Navigate up']")
	public AndroidElement backbtn;

	@FindBy(id = "com.myntra.android:id/iv_search_list")
	public AndroidElement selectSimilarProduct;

	@FindBy(xpath = "//android.view.ViewGroup[@index='9']/android.widget.ImageView")
	public AndroidElement RHwishlistbtn;
	/**
	 * Objects identified for the sort radio button displayed in sort page;
	 * 
	 * @author 300021278 -Rakesh
	 */
	@FindAll({ @FindBy(xpath = "//android.widget.RadioGroup/android.widget.RadioButton") })
	public List<AndroidElement> sortRadioButtons;
	/**
	 * Objects identified for the sort sections displayed in sort page;
	 * 
	 * @author 300021278 -Rakesh
	 */
	@FindAll({ @FindBy(xpath = "//android.widget.LinearLayout[starts-with(@resource-id,'com.myntra.android')]") })
	public List<AndroidElement> sortSections;
	/**
	 * object identified for the options displayed in filter screen for gender,
	 * color, size, etc.
	 * 
	 * @author 300021278 -Rakesh
	 */
	@FindAll({
			@FindBy(xpath = "//android.widget.TextView[@resource-id='com.myntra.android:id/tv_search_filter_title']") })
	public List<AndroidElement> filterHeadOptions;
	/**
	 * Object identified for the sub options displayed after selecting filter option
	 * for brand, Categories, Gender, Bundles,Price,offers; NOTE==>> Sub options
	 * having color and radio buttons are inculded in this xapth
	 * 
	 * @author 300021278 -Rakesh
	 */
	@FindAll({ @FindBy(xpath = "//android.widget.CheckBox[@resource-id='com.myntra.android:id/cb_filter_detail']") })
	public List<AndroidElement> filterSubOptions;
	/**
	 * Object identified savetowishlist icon displayed on product tile
	 * 
	 * @author 300021278 -Rakesh
	 */
	@FindAll({
			@FindBy(xpath = "//android.widget.ImageView[@resource-id='com.myntra.android:id/iv_product_list_item_options_similar']") })
	public List<AndroidElement> saveProduct;
	/**
	 * object identified to click on a product
	 * 
	 * @author 300021278 -Rakesh
	 */
	@FindBy(xpath = "//android.widget.LinearLayout[@index='0']")
	public AndroidElement randomProduct;

	/**
	 * object identified for top button displayed after scrolling in product list
	 * page
	 * 
	 * @author 300021278 -Rakesh
	 */
	@FindBy(xpath = "//android.widget.TextView[@resource-id='com.myntra.android:id/search_count_toast']")
	public AndroidElement topBtn;

	/******** getMethods **********/
	/**
	 * getter for top button displayed after scrolling in product list page
	 * 
	 * @author 300021278 -Rakesh
	 */
	public AndroidElement getTopBtn() {
		objAndroidGenericMethods.CheckAndroidElementFound(topBtn, "topBtn");
		return topBtn;
	}

	/**
	 * getter for savetowishlist icon displayed on product tile
	 * 
	 * @author 300021278 -Rakesh
	 */
	public List<AndroidElement> getSaveProduct() {
		objAndroidGenericMethods.CheckAndroidElementsListFound(saveProduct, "saveProduct");
		return saveProduct;
	}

	/**
	 * getter for the sub options displayed after selecting filter option for brand,
	 * Categories, Gender, Bundles,Price,offers; NOTE==>> Sub options having color
	 * and radio buttons are not inculded in this xapth
	 * 
	 * @author 300021278 -Rakesh
	 * @return
	 */
	public List<AndroidElement> getFilterSubOptions() {
		objAndroidGenericMethods.CheckAndroidElementsListFound(filterSubOptions, "filterSubOptions");
		return filterSubOptions;
	}

	/**
	 * getter for the options displayed in filter screen for gender,color, size,
	 * etc.
	 * 
	 * @author 300021278 -Rakesh
	 * @return
	 */
	public List<AndroidElement> getFilterHeadOptions() {
		objAndroidGenericMethods.CheckAndroidElementsListFound(filterHeadOptions, "filterHeadOptions");
		return filterHeadOptions;
	}

	/**
	 * getter for the sort sections displayed in the sort page;
	 * 
	 * @author 300021278 -Rakesh
	 * @return
	 */
	public List<AndroidElement> getSortSections() {
		objAndroidGenericMethods.CheckAndroidElementsListFound(sortSections, "sortSections");
		return sortSections;
	}

	/**
	 * getter for the sort radio buttons displayed in the sort page;
	 * 
	 * @author 300021278 -Rakesh
	 * @return
	 */
	public List<AndroidElement> getSortRadioButtons() {
		objAndroidGenericMethods.CheckAndroidElementsListFound(sortRadioButtons, "sortRadioButtons");
		return sortRadioButtons;
	}

	public AndroidElement getRHwishlistbtn() {
		objAndroidGenericMethods.CheckAndroidElementFound(RHwishlistbtn, "RHwishlistbtn");
		return RHwishlistbtn;
	}

	public AndroidElement getSelectSimilarProduct() {
		objAndroidGenericMethods.CheckAndroidElementFound(selectSimilarProduct, "selectSimilarProduct");
		return selectSimilarProduct;
	}

	public AndroidElement getBackbtn() {
		objAndroidGenericMethods.CheckAndroidElementFound(backbtn, "backbtn");
		return backbtn;
	}

	/**
	 * -Rakesh Modifed the name as it was not included in auto getters;
	 * 
	 * @return
	 */
	public AndroidElement getprice() {
		objAndroidGenericMethods.CheckAndroidElementFound(price, "price");
		return price;
	}

	/**
	 * Added check method(which was missing) Edited by Subhasis
	 */
	public AndroidElement getSortPriceLowToHigh() {
		objAndroidGenericMethods.CheckAndroidElementFound(sortPriceLowToHigh, "Sort price low to high");
		return sortPriceLowToHigh;
	}

	public AndroidElement getOkButton() {
		objAndroidGenericMethods.CheckAndroidElementFound(okButton, "okButton");
		return okButton;
	}

	/**
	 * Getter for sort popularity section
	 * 
	 * @author 300021278 -Rakesh Modifed the name as it was included in auto
	 *         getters;
	 * @return
	 */
	public AndroidElement getSortPopularitySectionbtn() {
		objAndroidGenericMethods.CheckAndroidElementFound(sortPopularitySectionbtn, "sortPopularitySectionbtn");
		return sortPopularitySectionbtn;
	}

	public AndroidElement getRHBagbtn() {
		objAndroidGenericMethods.CheckAndroidElementFound(RHBagbtn, "RHBagbtn");
		return RHBagbtn;
	}

	public AndroidElement getOfferAndDiscounts() {
		objAndroidGenericMethods.CheckAndroidElementFound(offerAndDiscounts, "Offer&Discount");
		return offerAndDiscounts;
	}

	public AndroidElement getPercentageRadioButton() {
		objAndroidGenericMethods.CheckAndroidElementFound(percentageRadioButton, "percentage radio button");
		return percentageRadioButton;
	}

	public List<AndroidElement> getFirstProductDetails() {
		return firstProductDetails;
	}

	public AndroidElement getSortFilterButton() {
		objAndroidGenericMethods.CheckAndroidElementFound(sortFilterButton, "sortFilterButton");
		return sortFilterButton;
	}

	public AndroidElement getSortButton() {
		objAndroidGenericMethods.CheckAndroidElementFound(sortButton, "sortButton");
		return sortButton;
	}

	public AndroidElement getSortByWhatsNewRadioButton() {
		objAndroidGenericMethods.CheckAndroidElementFound(sortByWhatsNewRadioButton, "sortByWhatsNewRadioButton");
		return sortByWhatsNewRadioButton;
	}

	public AndroidElement getSortByWhatsNewbtn() {
		objAndroidGenericMethods.CheckAndroidElementFound(sortByWhatsNewbtn, "sortByWhatsNewbtn");
		return sortByWhatsNewbtn;
	}

	public AndroidElement getSortByDiscountbtn() {
		objAndroidGenericMethods.CheckAndroidElementFound(sortByDiscountbtn, "sortByDiscountbtn");
		return sortByDiscountbtn;
	}

	public AndroidElement getSortDiscountSectionbtn() {
		objAndroidGenericMethods.CheckAndroidElementFound(sortDiscountSectionbtn, "sortDiscountSectionbtn");
		return sortDiscountSectionbtn;
	}

	public AndroidElement getSortApplybtn() {
		objAndroidGenericMethods.CheckAndroidElementFound(sortApplybtn, "sortApplybtn");
		return sortApplybtn;
	}

	public AndroidElement getPricebtn() {
		objAndroidGenericMethods.CheckAndroidElementFound(pricebtn, "pricebtn");
		return pricebtn;
	}

	public AndroidElement getSizebtn() {
		objAndroidGenericMethods.CheckAndroidElementFound(Sizebtn, "Sizebtn");
		return Sizebtn;
	}

	public List<AndroidElement> getFilterSliderbtn() {
		return FilterSliderbtn;
	}

	public List<AndroidElement> getRightHeaderButtons() {
		return RightHeaderButtons;
	}

	public List<AndroidElement> getButtons() {
		return buttons;
	}

	public AndroidElement getFilterBtn() {
		objAndroidGenericMethods.CheckAndroidElementFound(filterBtn, "filterBtn");
		return filterBtn;
	}

	public AndroidElement getFebricbtn() {
		objAndroidGenericMethods.CheckAndroidElementFound(febricbtn, "febricbtn");
		return febricbtn;
	}

	public AndroidElement getCottonbtn() {
		objAndroidGenericMethods.CheckAndroidElementFound(cottonbtn, "cottonbtn");
		return cottonbtn;
	}

	/******************* methods ***********************/
	/**
	 * Method to click on top button displayed after scrolling in product list page.
	 * 
	 * @author 300021278 -Rakesh
	 */
	public void clickTopButton() {
		objAndroidGenericMethods.clickOnAndroidElement(getTopBtn(), "Clicked on top button ");
	}

	/**
	 * Method to click save to wishlist icon displayed on product tile
	 * 
	 * @author 300021278 -Rakesh
	 * @throws InterruptedException
	 */
	public void saveProductToWishlist() throws InterruptedException {
		List<AndroidElement> products = getSaveProduct();
		System.out.println("the size of the product save icon displayed is" + products.size());
		
		int size = products.size();
			for (int i =0; i<=1;i++) {
				products.get(i).click();
			System.out.println("Saved the product");
			}
		//Thread.sleep(8000);
	}

	/**
	 * Method to select the sub filter option displayed in the selected filters ;
	 * sub options displayed after selecting filter option for brand, Categories,
	 * Gender, Bundles,Price,offers; NOTE==>> Sub options having color and radio
	 * buttons are not inculded in this method
	 * 
	 * @param filterType
	 * @author 300021278 -Rakesh
	 */
	public void selectSubFilterOption(String subFilterValue) {
		List<AndroidElement> subOptions = getFilterSubOptions();
		System.out.println("the size of the filter options is" + subOptions.size());
		System.out.println("the sub options are");
		for (AndroidElement option : subOptions) {
			System.out.println(option.getText());
			String optionValue = option.getText();
			if (optionValue.equalsIgnoreCase(subFilterValue)) {
				option.click();
				System.out.println("Clicked on the suboption");
				break;
			} else {
				System.out.println("search in progress");
			}
		}
	}

	/**
	 * Method to select the filter option displayed in the filters ; Note===>
	 * Options include Gender,Brand, Colour, Sizes
	 * 
	 * @param filterType
	 * @author 300021278 -Rakesh
	 * @throws InterruptedException
	 */
	public void selectFiter(String filterType) throws InterruptedException {
		List<AndroidElement> options = getFilterHeadOptions();
		System.out.println("the size of the filter options is" + options.size());
		for (AndroidElement option : options) {
			String optionValue = option.getText();
			if (optionValue.equalsIgnoreCase(filterType)) {
				option.click();
				System.out.println("Clicked on the option");
				break;
			} else {
			}
		}
	//	Thread.sleep(5000);

	}

	/**
	 * Method to select radio button displayed in sort page displayed in product
	 * list
	 * 
	 * @param option
	 * 
	 *            NOTE=====> Options include pricehtl, pricelth, popularity,
	 *            discount, whatsnew, deliverytime;
	 * @author 300021278 -Rakesh
	 */
	public void selectSortRadio(String option) {
		System.out.println("the size of the radio button" + getSortRadioButtons().size());
		if (option.equalsIgnoreCase("pricehtl")) {
			getSortRadioButtons().get(0).click();
			Reporter.log("clicked on the Price High to Low button successfully");
		} else if (option.equalsIgnoreCase("pricelth")) {
			getSortRadioButtons().get(1).click();
			Reporter.log("clicked on the Price Low to High button successfully");
		} else if (option.equalsIgnoreCase("popularity")) {
			getSortRadioButtons().get(2).click();
			Reporter.log("clicked on the popularity button successfully");
		} else if (option.equalsIgnoreCase("discount")) {
			getSortRadioButtons().get(3).click();
			Reporter.log("clicked on the discount button successfully");
		} else if (option.equalsIgnoreCase("whatsnew")) {
			getSortRadioButtons().get(4).click();
			Reporter.log("clicked on the whatsnew button successfully");
		} else if (option.equalsIgnoreCase("deliverytime")) {
			getSortRadioButtons().get(5).click();
			Reporter.log("clicked on the deliverytime button successfully");
		}
	}

	/**
	 * Method to select sort sections displayed in sort page displayed in product
	 * list
	 * 
	 * @param option
	 * 
	 *            NOTE=====> Options include pricehtl, pricelth, popularity,
	 *            discount, whatsnew, deliverytime;
	 * @author 300021278 -Rakesh
	 */
	public void selectSortSection(String option) {
		System.out.println("the size of the sections " + getSortSections().size());
		if (option.equalsIgnoreCase("pricehtl")) {
			getSortSections().get(0).click();
			Reporter.log("clicked on the Price High to Low button successfully");
		} else if (option.equalsIgnoreCase("pricelth")) {
			getSortSections().get(1).click();
			Reporter.log("clicked on the Price Low to High button successfully");
		} else if (option.equalsIgnoreCase("popularity")) {
			getSortSections().get(2).click();
			Reporter.log("clicked on the popularity button successfully");
		} else if (option.equalsIgnoreCase("discount")) {
			getSortSections().get(3).click();
			Reporter.log("clicked on the discount button successfully");
		} else if (option.equalsIgnoreCase("whatsnew")) {
			getSortSections().get(4).click();
			Reporter.log("clicked on the whatsnew button successfully");
		} else if (option.equalsIgnoreCase("deliverytime")) {
			getSortSections().get(5).click();
			Reporter.log("clicked on the deliverytime button successfully");
		}
	}

	public void clickSimilarProduct() {
		objAndroidGenericMethods.clickOnAndroidElement(selectSimilarProduct, "Clicked on selectSimilarProduct");
	}

	/**
	 * Method to click on header button displayed in product list page;
	 * 
	 * 
	 * NOTE====> HeaderButton include back, logo, search, bag, notifications,
	 * wishlist;
	 * 
	 * @author 300021278 -Rakesh
	 * @modifiedby: Rakesh changed the logic as it was not working
	 * @param HeaderButton
	 */
	public void clickHeaderButton(String HeaderButton) {
		System.out.println("the count is " + getRightHeaderButtons().size());
		if (HeaderButton.equalsIgnoreCase("back")) {
			objAndroidGenericMethods.clickOnAndroidElement(getRightHeaderButtons().get(0), "back");
			System.out.println("clicked on the backButton successfully");
		} else if (HeaderButton.equalsIgnoreCase("logo")) {
			objAndroidGenericMethods.clickOnAndroidElement(getRightHeaderButtons().get(1), "logo");
			System.out.println("clicked on the logo successfully");
		} else if (HeaderButton.equalsIgnoreCase("search")) {
			objAndroidGenericMethods.clickOnAndroidElement(getRightHeaderButtons().get(2), "search");
			System.out.println("clicked on the searchButton successfully");
		} else if (HeaderButton.equalsIgnoreCase("bag")) {
			objAndroidGenericMethods.clickOnAndroidElement(getRightHeaderButtons().get(3), "bag");
			System.out.println("clicked on the bagButton successfully");
		} else if (HeaderButton.equalsIgnoreCase("notifications")) {
			objAndroidGenericMethods.clickOnAndroidElement(getRightHeaderButtons().get(4), "notifications");
			System.out.println("clicked on the notificationsButton successfully");
		} else if (HeaderButton.equalsIgnoreCase("wishlist")) {
			objAndroidGenericMethods.clickOnAndroidElement(getRightHeaderButtons().get(5), "wishlist");
			System.out.println("clicked on the wishlistButton successfully");
		} else {
			Assert.fail("user is unable to click on selected field/WrongInput");
		}
	}

	/**
	 * Method to get the details of the first product displayed; Reason: wrong
	 * getter was used in method modified by rakesh
	 * 
	 * @author 300021278 -Rakesh
	 * @param ProductType
	 * @return
	 */
	public AndroidElement productDetails(String ProductType) {
		if (ProductType.equalsIgnoreCase("productName")) {
			productElement = getFirstProductDetails().get(1);
		} else if (ProductType.equalsIgnoreCase("price")) {
			productElement = getFirstProductDetails().get(2);
		} else if (ProductType.equalsIgnoreCase("mrpprice")) {
			productElement = getFirstProductDetails().get(3);
		} else if (ProductType.equalsIgnoreCase("Discount")) {
			productElement = getFirstProductDetails().get(4);
		} else if (ProductType.equalsIgnoreCase("wishlist")) {
			productElement = getFirstProductDetails().get(5);
		} else {
			Assert.fail("user is unable to click on selected field/WrongInput");
		}
		return productElement;
	}

	public void clickProduct(String ProductType) {
		productDetails(ProductType);
		objAndroidGenericMethods.clickOnAndroidElement(productElement, "Clicked on product button ");
	}

	public void clickRandomProduct() { 
		objAndroidGenericMethods.clickOnAndroidElement(randomProduct, "randomProduct ");
	}

	public void clickFilter(int FilterPosition) {
		if (getFilterSliderbtn().get(FilterPosition).isDisplayed()) {
			getFilterSliderbtn().get(FilterPosition).click();
			Reporter.log("user is able to click on the BackButton successfully");
		} else {
			Assert.fail("user is unable to click on selected field/WrongInput");
		}
	}

	/**
	 * method to click on apply button displayed in sort/Filter screen
	 * 
	 * @throws InterruptedException
	 */
	public void ClickOnSortApplyButton() throws InterruptedException {
		objAndroidGenericMethods.clickOnAndroidElement(getSortApplybtn(), "Click on apply button");
	//	Thread.sleep(3000);
	}

	/**
	 * Click on whatsnew radio button displayed
	 */
	public void clickWhatsNewRadioButton() {
		objAndroidGenericMethods.clickOnAndroidElement(getSortByWhatsNewRadioButton(),
				"Clicked on what's new radio button ");
	}

	/**
	 * Click on discount radio button displayed
	 * 
	 * @author 300021278 -Rakesh
	 */
	public void ClickOnSortDiscountButton() {
		objAndroidGenericMethods.clickOnAndroidElement(getSortDiscountSectionbtn(), "click on discount");
	}

	public void ClickOnBack() {
		objAndroidGenericMethods.clickOnAndroidElement(getBackbtn(), "Click on back button");
	}

	public void ClickOnprice() {
		objAndroidGenericMethods.clickOnAndroidElement(getprice(), "Click on price button");
	}

	public void Clicksortfilterbutton() {
		objAndroidGenericMethods.clickOnAndroidElement(getSortFilterButton(), "click on sort filter");
	}

	public void clickOnsortAppy() {
		objAndroidGenericMethods.clickOnAndroidElement(getSortApplybtn(), "click on apply button");
	}

	/**
	 * @author 300021278 -Rakesh Modifed the getter name as it was changed for
	 *         adding auto getters;
	 */
	public void clicksortPopularitySectionbton() {
		objAndroidGenericMethods.clickOnAndroidElement(getSortPopularitySectionbtn(), "click on apply button");
		sortPopularitySectionbtn.click();
	}

	public void clickOnDiscount() {
		objAndroidGenericMethods.clickOnAndroidElement(getSortByDiscountbtn(), "clicked on discount");
	}

	/**
	 * Method to click on ok button displayed in pl page; Added sleep before click
	 * as we need it for device coverage;
	 * 
	 * @author 300021278 -Rakesh
	 * @throws InterruptedException
	 */
	public void clickOk() throws InterruptedException {
	//	Thread.sleep(5000);
		objAndroidGenericMethods.clickOnAndroidElement(getOkButton(), "Click on okpop button");
	}

	public void clickSort() {
		objAndroidGenericMethods.clickOnAndroidElement(getSortButton(), "Click on okpop button");
	}

	public void clickOkButton() {
		try {
			objAndroidGenericMethods.clickOnAndroidElement(getOkButton(), "click ok button");
			Reporter.log("ok button available");
		} catch (Exception e) {
		Reporter.log("Ok button not available");
		}
	}

	public void clickOnOfferAndDiscount() {
		objAndroidGenericMethods.clickOnAndroidElement(getOfferAndDiscounts(), "click offer and discount button");
	}

	public void clickOnPercentage() {
		objAndroidGenericMethods.clickOnAndroidElement(getPercentageRadioButton(), "click on percentage radio button");
	}

	public void clickRHBagbtn() {
		objAndroidGenericMethods.clickOnAndroidElement(getRHBagbtn(), "clicked");
	}

	public void clickWhatsNewButton() {
		objAndroidGenericMethods.clickOnAndroidElement(getSortByWhatsNewbtn(), "Clicked on what's new button ");
	}

	public void clickOnfilter() {
		objAndroidGenericMethods.clickOnAndroidElement(getFilterBtn(), "cliked");
	}

	public void clickfebricbtn() {
		objAndroidGenericMethods.clickOnAndroidElement(febricbtn, "clicked febric button");
	}

	public void clickcottonbtn() {
		objAndroidGenericMethods.clickOnAndroidElement(cottonbtn, "clicked cotton button");
	}

	public void clickOnPricelow() {
		objAndroidGenericMethods.clickOnAndroidElement(getSortPriceLowToHigh(), "Price low to high selected");
	}

	public void clickSortPlaneBtn() {
		objAndroidGenericMethods.clickOnAndroidElement(getSortButton(), "SortButton");
	}

	/**
	 * object identified for the first product image displayed in the pl pageused
	 * for viewSimiliarProducts methods;
	 * 
	 * @author 300021278 -Rakesh
	 * 
	 * modified by : sneha
	 * old xpath :: "//android.widget.RelativeLayout[@resource-id='com.myntra.android:id/product_list_item_container' and @index= '1']/android.widget.LinearLayout[@resource-id='com.myntra.android:id/ll_primary_image_info']"
	 */
	@FindBy(xpath = "//android.widget.RelativeLayout[@resource-id='com.myntra.android:id/product_list_item_container']/android.widget.LinearLayout[@resource-id='com.myntra.android:id/ll_primary_image_info']")
	public AndroidElement firstProductImage;

	/**
	 * getter for the first product image displayed in the pl page used for
	 * viewSimiliarProducts ;
	 * 
	 * @author 300021278 -Rakesh
	 */
	public AndroidElement getfirstProductImage() {
		objAndroidGenericMethods.CheckAndroidElementFound(firstProductImage, "firstProductImage");
		return firstProductImage;
	}

	/**
	 * Method to view similiar products displayed in pl page after long press on an
	 * image
	 * 
	 * @author 300021278 -Rakesh
	 * @param aDriver
	 */
	public void viewSimiliarProducts(AndroidDriver<AndroidElement> aDriver) {
		TouchAction action = new TouchAction(aDriver);
		action.longPress(getfirstProductImage()).release().perform();
	}

	/**
	 * This method will click on the sort button irrespective of button type plane
	 * or slider;
	 * 
	 * @author Rakesh
	 */
	public void clickSortBtn() {
		try {
			sortButton.isDisplayed();
			clickSortPlaneBtn();
			System.out.println("Clicked on plain sort Button ");
		} catch (Exception e) {
			sortFilterButton.isDisplayed();
			Clicksortfilterbutton();
		}
	}

	/**
	 * This method will click on the filter button irrespective of button type plane
	 * or slider;
	 * 
	 * @ModifiedBy:-Rakesh removed is displayed condition
	 * 
	 * @author Rakesh
	 */
	public void clickFiltertBtn() {
		try {
			clickOnfilter();
			System.out.println("Clicked on plane Filter Button ");
		} catch (Exception e) {
			Clicksortfilterbutton();
		}
	}

	/**
	 * This method will click on the sort Discount button irrespective of button
	 * type radio button or section
	 * 
	 * 
	 * NOTE ====> Apply Button is also included in the method;
	 * 
	 * @author Rakesh
	 * @throws InterruptedException
	 * @date: 06-Feb-2018
	 */
	public void clickDiscountBtn() throws InterruptedException {
		try {
			sortByDiscountbtn.isDisplayed();
			selectSortRadio("discount");
			ClickOnSortApplyButton();
		} catch (Exception e) {
			getSortDiscountSectionbtn();
			ClickOnSortDiscountButton();
		}

	}

	/**
	 * This method will click on the sort option irrespective of button type radio
	 * button or section NOTE=====> Options include pricehtl, pricelth, popularity,
	 * discount, whatsnew, deliverytime;
	 * 
	 * NOTE ====> Apply Button is also included in the method;
	 * 
	 * @author Rakesh
	 * @throws InterruptedException
	 * @date: 16-Feb-2018
	 */
	public void selectSortOption(String option) throws InterruptedException {
		try {
			selectSortRadio(option);
			ClickOnSortApplyButton();
		} catch (Exception e) {
			selectSortSection(option);
		}

	}

	/***************** Assertions ***************/

	@FindAll({
			@FindBy(xpath = "//android.support.v7.widget.RecyclerView[@resource-id='com.myntra.android:id/product_list_recycler_view']/*") })
	public List<AndroidElement> plpelements;

	@FindBy(xpath = "//android.support.v7.widget.RecyclerView[@resource-id='com.myntra.android:id/product_list_recycler_view']/android.widget.RelativeLayout/android.widget.TextView[@index = '2']")
	public AndroidElement firstProductPrice;

	public List<AndroidElement> getPlpelements() {
		return plpelements;
	}

	public void assertPlpSearchTitle() {
		AndroidElement search = getPlpelements().get(0);
		objAndroidGenericMethods.CheckAndroidElementFound(search, "search");
	}

	public void assertPlpFirstProduct() {
		AndroidElement firstProduct = getPlpelements().get(1);
		objAndroidGenericMethods.CheckAndroidElementFound(firstProduct, "firstProduct");
	}

	public void assertPlpFirstProductPrice() {
		AndroidElement firstProduct = getPlpelements().get(1);
		objAndroidGenericMethods.CheckAndroidElementFound(firstProduct, "firstProduct");

	}

}
