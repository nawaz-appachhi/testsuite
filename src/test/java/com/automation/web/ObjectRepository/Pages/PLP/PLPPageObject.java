package com.automation.web.ObjectRepository.Pages.PLP;

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


/**
 * @author 300019224
 *
 */
public class PLPPageObject {
	public GenericMethods objGenericMethods;
	HeaderPageObject objHeaderPageObject;
	WebElement element = null;

	/**
	 * @param driver
	 */

	public PLPPageObject(WebDriver driver) {
		PageFactory.initElements(driver, this);
		objGenericMethods = new GenericMethods(driver);
		objHeaderPageObject = new HeaderPageObject(driver);
	}

	public String PRODUCT_BRAND;
	public String PRODUCT_NAME;
	public String STRIKED_PRICE;
	public String PRODUCT_DISCOUNT;
	public String DISCOUNTED_PRICE;
	public String PRODUCT_CODE;

	@FindBy(className = "horizontal-filters-title")
	public WebElement SearchResultTitle;

	@FindAll({ @FindBy(xpath = "//*[@class='categories-list']/li/label") })
	public List<WebElement> Categories;

	@FindAll({ @FindBy(xpath = "//*[@class='price-list']/li/label") })
	public List<WebElement> Price;

	@FindAll({ @FindBy(xpath = "//*[@class='brand-list']/li/label") })
	public List<WebElement> Brand;

	@FindBy(className = "horizontal-filters-sub")
	public WebElement SearchResultItemCount;

	@FindAll({ @FindBy(className = "colour-listItem") })
	public List<WebElement> colourList;

	@FindAll({ @FindBy(xpath = "//*[@class='discount-list']/li/label") })
	public List<WebElement> discountList;

	@FindBy(className = "sort-sortBy")
	public WebElement SortBy;

	@FindAll({ @FindBy(xpath = ".//*[@class='sort-sortBy']/ul/li") })
	public List<WebElement> SortByTypesList;

	@FindBy(className = "filter-summary-resetAll")
	public WebElement ClearFilterBtn;

	@FindBy(className = "product-base")
	public List<WebElement> SearchResultList;

	@FindAll({ @FindBy(className = "product-sizeButton") })
	public List<WebElement> ProductSize;

	@FindBy(className = "scrollToTop-button sprites-gototop myntraweb-sprite scrollToTop-isVisible")
	public WebElement TopIcon;

	@FindBy(className = "product-base")
	public WebElement ProductBase;

	@FindBy(xpath = "//*[@id='desktopSearchResults']/div[2]/section/ul/li[1]/a/img")
	public WebElement SaveProduct;

	@FindBy(xpath = "//span[@class='vertical-filters-header' and text()='Categories']")
	public WebElement CategoryTitle;

	@FindBy(xpath = "//span[@class='vertical-filters-header' and text()='Brand']")
	public WebElement BrandTitle;

	@FindBy(xpath = "//span[@class='vertical-filters-header' and text()='Price']")
	public WebElement PriceTitle;

	@FindBy(xpath = "//span[@class='vertical-filters-header' and text()='Colour']")
	public WebElement ColourTitle;

	@FindBy(xpath = "//span[@class='vertical-filters-header' and text()='Discount']")
	public WebElement DiscountTitle;

	@FindBy(xpath = "//*[@id='desktopSearchResults']/div[2]/section/div[1]")
	public WebElement TopButton;

	@FindBy(xpath = "//div[@class='results-showMoreContainer']/div[@class='results-showmore']")
	public WebElement ShowMoreProductsLink;
	
	@FindBy(xpath = "(//li[@class='product-base'])[2]")
	public WebElement secondProduct;

	/**
	 * Author-Irfan Shariff
	 * Description: Created xpaths for 'SaveFirstItem' & 'AddToBagFirstItem'
	 */

	@FindBy(xpath="//span[text()='save']")
	public WebElement SaveFirstItem;

	/**
	 * Modified by  :Anu
	 * xpath changed
	 */
	@FindBy(xpath="(//li[@class='product-base']/div[@class='product-actions']/span[@class='product-actionsButton product-addToBag']/span[text()='Add to bag'])[1]")
	public WebElement AddToBagFirstItem;

	/**
	 * Created by  :Anu
	 * Added Locator to select first available size in PLP
	 */
	@FindBy(xpath="(//div[@class='product-sizeButtonsContaier']/button)[1]")
	public WebElement FirstSizeAvailable;


	public WebElement getFirstSizeAvailable() {
		return FirstSizeAvailable;
	}

	/**
	 * firstProduct
	 * Author-shivaprasad
	 * 
	 */
	@FindBy(xpath = "//li[@class='product-base']")
	public WebElement firstProduct;


	@FindAll({@FindBy(className = "product-product")})
	public List<WebElement> AllProductName;

	/**
	 * Modified By - Anu Changed xpath for promotions.
	 * Modified By - Aishurya modified if offer is present instead of promotion
	 */
	@FindBy(xpath = "//h4[text()='Promotions' or text()='Offers']")
	public WebElement Promotions;

	@FindBy(xpath = "//ul[@class='atsa-values']/li[1]")
	public WebElement buy1get1;

	@FindBy(xpath = "//*[@class='categories-list']/li")
	public WebElement categoriesFilterchk;

	@FindAll({ @FindBy(xpath = "//ul[@class='atsa-values']/li") })
	public List<WebElement> FreeGiftKamaProductchk;
	
	/**
	 * Created By : Neeraj
	 * Added Object for Free gift under promotion link
	 */
	@FindBy(xpath = "//ul[@class='atsa-values']/li/label[text()='Free Gift']")
	public WebElement freeGiftChk;

	/**
	 * @return
	 * Created By : Neeraj
	 * Getter method for freegiftchk object
	 */
	public WebElement getFreeGiftChk() {
		objGenericMethods.CheckWebElementFound(freeGiftChk, "freeGiftChk");
		return freeGiftChk;
	}

	/**
	 * author - Anu
	 * Added method to filter by price
	 */
	@FindBy(xpath = "//ul[@class='price-list']/li[4]")
	public WebElement filterByPrice;

	@FindBy(xpath = "//div[@class='horizontal-filters-sub']")
	public List<WebElement> productDescription;
	
	/**
	 * Author-shiva prasad
	 */
	@FindBy(xpath = "//div[@class='colour-more']/span[text()=' more']")
	public WebElement moreColors;
	


	//	@FindBy(xpath = "(//div[@class='wishlist text-md icon-add'])[2]")
	@FindBy(xpath="(//div[@class='product-actions']/span[1])[2]")
	public WebElement saveToWishlist;

	public List<WebElement> getFreeGiftKamaProductchk() {
		return FreeGiftKamaProductchk;
	}

	public WebElement getCategoriesFilterchk() {
		return categoriesFilterchk;
	}

	public void ClickOnCategoriesByName(String name) {
		getCategoriesFilterchk().findElement(By.xpath("//input[@value='" + name + "']"));

	}


	public WebElement getFilterByPrice() {
		return filterByPrice;
	}

	@FindBy(xpath = "(//div[@class='vertical-filters-filters']/ul/li/label/div[@class='common-checkboxIndicator'])[2]")
	public WebElement filterPrice;

	public WebElement getFilterPrice() {
		return filterPrice;
	}

	/**
	 * Author-Irfan Shariff
	 * Description: Created getter's for 'SaveFirstItem' & 'AddToBagFirstItem'
	 */
	public WebElement getSaveFirstItem() {
		objGenericMethods.CheckWebElementFound(SaveFirstItem, "SaveFirstItem");
		return SaveFirstItem;
	}

	public WebElement getAddToBagFirstItem() {
		return AddToBagFirstItem;
	}

	public WebElement getMoreColors() {
		objGenericMethods.CheckWebElementFound(moreColors, "moreColors");
		return moreColors;
	}
	
	public List<WebElement> getAllProductName() {
		// objGenericMethods.CheckWebElementsListFound(AllProductName,
		// "AllProductName");
		return AllProductName;
	}

	public WebElement getFirstProduct() {
		return firstProduct;
	}

	public WebElement getTopIcon() {
		objGenericMethods.CheckWebElementFound(TopIcon, "TopIcon");
		return TopIcon;
	}

	public WebElement getCategoryTitle() {
		objGenericMethods.CheckWebElementFound(CategoryTitle, "CategoryTitle");
		return CategoryTitle;
	}

	public WebElement getBrandTitle() {
		objGenericMethods.CheckWebElementFound(BrandTitle, "BrandTitle");
		return BrandTitle;
	}

	public WebElement getPriceTitle() {
		objGenericMethods.CheckWebElementFound(PriceTitle, "PriceTitle");
		return PriceTitle;
	}

	public WebElement getColourTitle() {
		objGenericMethods.CheckWebElementFound(ColourTitle, "ColourTitle");
		return ColourTitle;
	}

	public WebElement getDiscountTitle() {
		objGenericMethods.CheckWebElementFound(DiscountTitle, "DiscountTitle");
		return DiscountTitle;
	}

	public WebElement getTopButton() {
		objGenericMethods.CheckWebElementFound(TopButton, "TopButton");
		return TopButton;
	}

	public WebElement getSaveProduct() {
		objGenericMethods.CheckWebElementFound(SaveProduct, "SaveProduct");
		return SaveProduct;
	}

	public WebElement getProductBase() {
		objGenericMethods.CheckWebElementFound(ProductBase, "ProductBase");
		return ProductBase;
	}

	public WebElement getSearchResultTitle() {
		objGenericMethods.CheckWebElementFound(SearchResultTitle, "SearchResultTitle");
		return SearchResultTitle;
	}

	public List<WebElement> getPrice() {
		objGenericMethods.CheckWebElementsListFound(Price, "Price");
		return Price;
	}

	public List<WebElement> getCategories() {
		objGenericMethods.CheckWebElementsListFound(Categories, "Categories");
		return Categories;
	}

	public List<WebElement> getBrand() {
		objGenericMethods.CheckWebElementsListFound(Brand, "Brand");
		return Brand;
	}

	public List<WebElement> getColourList() {
		objGenericMethods.CheckWebElementsListFound(colourList, "colourList");
		return colourList;
	}

	public List<WebElement> getDiscountList() {
		objGenericMethods.CheckWebElementsListFound(discountList, "discountList");
		return discountList;
	}

	public WebElement getSortBy() {
		objGenericMethods.CheckWebElementFound(SortBy, "SortBy");
		return SortBy;
	}

	public List<WebElement> getSortByTypesList() {
		objGenericMethods.CheckWebElementsListFound(SortByTypesList, "SortByTypesList");
		return SortByTypesList;
	}

	public WebElement getClearFilterBtn() {
		objGenericMethods.CheckWebElementFound(ClearFilterBtn, "ClearFilterBtn");
		return ClearFilterBtn;
	}

	public List<WebElement> getSearchResultList() {
		objGenericMethods.CheckWebElementsListFound(SearchResultList, "SearchResultList");
		return SearchResultList;
	}

	public WebElement getPromotions() {
		objGenericMethods.CheckWebElementFound(Promotions, "Promotions");
		return Promotions;
	}

	public WebElement getBuy1get1() {
		objGenericMethods.CheckWebElementFound(buy1get1, "buy1get1");
		return buy1get1;
	}

	/**
	 * 
	 * ModifiedBy : Neeraj
	 * Description: Removed Thread.sleep
	 */
	public void FilterByCategory(String category) {
		objGenericMethods.takeSnapShot();
		objGenericMethods.getWebElementByName(getCategories(), category).click();
		objGenericMethods.ReportClickEvent(category);
	}

	/**
	 * Modify by:Aishu - temporary solution for selecting price
	 */
	public void FilterByPrice(String Price) {
		objGenericMethods.takeSnapShot();
		getPrice().get(0).click();
//		objGenericMethods.getWebElementByName(getPrice(), Price).click();
		objGenericMethods.ReportClickEvent(Price);
	}

	public void FilterByBrand(String brand) {
		objGenericMethods.takeSnapShot();
		objGenericMethods.getWebElementByName(getBrand(), brand).click();
		objGenericMethods.ReportClickEvent(brand);
	}

	/**
	 * 
	 * ModifiedBy : Neeraj
	 * Description: Removed Thread.sleep
	 */
	public void FilterByColour(String colour) {
		objGenericMethods.takeSnapShot();
		for(int i=0;i<colourList.size();i++) {
			if(colourList.get(i).getAttribute("data-colorhex").equalsIgnoreCase(colour)) {
				colourList.get(i).click();
			}
		}
		objGenericMethods.ReportClickEvent(colour);
	}

	/**
	 *Modified by:Aishurya: added scroll,
	 *bydefault selecting the last option as passed value wont be available every time
	 *Modified By Aishu: Removed thread and added verification for clear all link
	 */
	public void FilterByDiscount(String discount) throws InterruptedException {
		objGenericMethods.takeSnapShot();
		objGenericMethods.scrollPage0to250();
		List<WebElement> allDiscount=getDiscountList();
//		objGenericMethods.getWebElementByName(getDiscountList(), discount).click();
		allDiscount.get(allDiscount.size()-1).click();
		objGenericMethods.ReportClickEvent(discount);
		objGenericMethods.scrollPageUp();
		objGenericMethods.waitDriverWhenReady(getClearFilterBtn(), "Clear Filter button");
	}

	/**
	 * Modify by Neeraj
	 * Reason : Added takeSnapShot() and ReportClickEvent()
	 */
	public void ClickOnFilterPrice() {
		objGenericMethods.takeSnapShot();
		getFilterPrice().click();
		objGenericMethods.ReportClickEvent("Price Filter");
	}

	/**
	 * Modify by Neeraj
	 * Reason : Added takeSnapShot() and ReportClickEvent()
	 */
	public void ClickOnFirstProduct() {
		objGenericMethods.takeSnapShot();
		getFirstProduct().click();
		objGenericMethods.ReportClickEvent("First Product");
	}

	public WebElement getShowMoreProductsLink() {
		objGenericMethods.CheckWebElementFound(ShowMoreProductsLink, "ShowMoreProductsLink");
		return ShowMoreProductsLink;
	}

	public List<WebElement> getProductSize() {
		objGenericMethods.CheckWebElementsListFound(ProductSize, "ProductSize");
		return ProductSize;
	}

	public void SelectBrand(String brand) {
		objGenericMethods.takeSnapShot();
		objGenericMethods.getWebElementByName(getBrand(), brand).click();
		objGenericMethods.ReportClickEvent(brand);
	}

	public void SelectColour(String colour) {
		objGenericMethods.takeSnapShot();
		objGenericMethods.getWebElementByName(getColourList(), colour).click();
		objGenericMethods.ReportClickEvent(colour);
	}

	public void SelectDiscount(String discount) {
		objGenericMethods.takeSnapShot();
		objGenericMethods.getWebElementByName(getDiscountList(), discount).click();
		objGenericMethods.ReportClickEvent(discount);
	}

	public void ClearFilter() {
		objGenericMethods.takeSnapShot();
		getClearFilterBtn().click();
		objGenericMethods.ReportClickEvent("ClearFilter");
	}

	/**
	 * Modified by: Nitesh
	 * Description: Added else condition to search by product code if product is not available on PLP page
	 * @param ProductCode
	 * @param searchItem
	 */
	/**
	 * Modified by: Anu
	 * Description: Modified code to search all products from PLP page with product code provided and selecting it. If desired product is not available, catch block will execute. 
	 */
	public void getClickProductByProductCode(String ProductCode) {
		List<WebElement> resultList = getSearchResultList();
		try {
			for (WebElement result : resultList) {
				WebElement link = result.findElement(By.tagName("a"));
				String hrefString = link.getAttribute("href");
					if (hrefString.contains(ProductCode)) {
						objGenericMethods.takeSnapShot();
						link.click();
						objGenericMethods.ReportClickEvent("Required Product");
						Reporter.log("Passed : Product with product code "+ ProductCode +", found on PLP page!");
					}
				}
		} catch (Exception e) {
			// TODO Auto-generated catch block
				Reporter.log("Failed : Product with product code "+ ProductCode +", not found on PLP page!");
				objHeaderPageObject.SearchInputBox(ProductCode);
				objHeaderPageObject.ClickOnSearchLens();
			
		} 
		}
	


	/**
	 * 
	 * ModifiedBy : Neeraj
	 * Description: Removed Thread.sleep and added WaitDriverWhenReady()
	 */
	public void HowerOnSortByDropdown() {
		objGenericMethods.waitDriver(getSortBy(), "Sort by Dropdown");
		objGenericMethods.HoverOnWebElement(getSortBy());
	}

	public void ClickOnSortDropdownByDroddownValue(String value) {
		List<WebElement> sortList = getSortByTypesList();
		WebElement SortWebElement = objGenericMethods.getWebElementByName(sortList, value);
		objGenericMethods.takeSnapShot();
		objGenericMethods.waitDriverWhenReady(SortWebElement, "Sort by Value");
		SortWebElement.click();
		
		objGenericMethods.ReportClickEvent(value + "sort type");
	}

	public void verifySearchResultTittle() {
		objGenericMethods.VerifyStringShouldNotEmpty(getSearchResultTitle(), "Search Result Title");
	}

	public WebElement getSearchResultItemCount() {
		objGenericMethods.CheckWebElementFound(SearchResultItemCount, "SearchResultItemCount");
		return SearchResultItemCount;
	}
	
	/**
	 * 
	 * ModifiedBy : Neeraj
	 * Description: commented unwanted method
	 */
//	public void PlpAddToBag(String ProductCode, String size) throws InterruptedException {
//		WebElement product = FindProduct(ProductCode, getSearchResultList());
//		objGenericMethods.HoverOnWebElement(product);
//		objGenericMethods.takeSnapShot();
//		product.findElement(By.xpath(".//*[@class='product-actionsButton product-addToBag']")).click();
//		objGenericMethods.ReportClickEvent("AddToBag button");
//		SelectProductSize(product, size);
//	}

//	public void PlpAddToWishlist(String ProductCode) throws InterruptedException {
//		WebElement product = FindProduct(ProductCode, getSearchResultList());
//		objGenericMethods.HoverOnWebElement(product);
//		objGenericMethods.takeSnapShot();
//		product.findElement(By.xpath(".//*[@class='product-actionsButton product-wishlist']")).click();
//		objGenericMethods.ReportClickEvent("PlpSave button");
//	}

//	 public PLPPageObject FetchProductDetails(String ProductCode) throws
//	 InterruptedException {
//	 WebElement product = FindProduct(ProductCode, getSearchResultList());
//	 PRODUCT_BRAND = product.findElement(By.className("product-brand")).getText();
//	 PRODUCT_NAME =
//	 product.findElement(By.className("product-product")).getText();
//	 STRIKED_PRICE =
//	 product.findElement(By.className("product-strike")).getText();
//	 PRODUCT_DISCOUNT =
//	 product.findElement(By.className("product-discountPercentage")).getText();
//	 return this;
//	 }
	public void ComparePrice(String ProductCode) {
		WebElement product = FindProduct(ProductCode, getSearchResultList());
		DISCOUNTED_PRICE = product.findElement(By.className("product-discountedPrice")).getText();
		System.out.println(DISCOUNTED_PRICE);
	}

	public void SelectProductSize(WebElement result, String size) {
		List<WebElement> SizeList = result.findElements(By.className("product-sizeButton"));
		objGenericMethods.takeSnapShot();
		objGenericMethods.getWebElementByName(SizeList, size).click();
		objGenericMethods.ReportClickEvent("ProductSize in PLP");
	}

	public WebElement FindProduct(String ProductCode, List<WebElement> ListOfItems) {
		List<WebElement> resultList = ListOfItems;
		WebElement product = null;
		for (WebElement result : resultList) {
			WebElement link = result.findElement(By.tagName("a"));
			String hrefString = link.getAttribute("href");
			if (hrefString.contains(ProductCode)) {
				product = result;
				break;
			}
		}
		return product;
	}

	public void verifySearchResultItemCount() {
		objGenericMethods.VerifyNumberShouldNotZeroOrLess(getSearchResultItemCount());
	}

	public void compareSortResult(List<WebElement> beforeSortList, List<WebElement> afterSortList) {

	}

	/**
	 * Modified By:Aishurya
	 * Reason: Removed duplicate method of ClickTopButton(), which was not working and not used anywhere
	 */

	/**
	 * Modified By:Aishurya
	 * Reason: Added wait
	 * 
	 * 
	 * 
	 * ModifiedBy : Neeraj
	 * Description: Removed Thread.sleep
	 */
	public void ClickTopButton() {
		objGenericMethods.takeSnapShot();
		getTopButton().click();
		objGenericMethods.ReportClickEvent("TopButton");
	}

	public void ShowMoreProductsLinkClick() {
		objGenericMethods.takeSnapShot();
		getShowMoreProductsLink().click();
		objGenericMethods.ReportClickEvent("ShowMoreProductsLink");
	}

	/**
	 * @param ProductCode
	 * @param data
	 *            :
	 *            brand,name,StrikedPrice,DiscountedPercentage,DiscountedPrice,SellingPrice
	 * @return
	 * @throws InterruptedException
	 */
	public WebElement SubElementOfItem(String ProductCode, String data) {
		WebElement product = FindProduct(ProductCode, getSearchResultList());
		WebElement item = null;
		if (data.equalsIgnoreCase("brand")) {
			item = product.findElement(By.className("product-brand"));
		} else if (data.equalsIgnoreCase("name")) {
			item = product.findElement(By.className("product-product"));
		} else if (data.equalsIgnoreCase("StrikedPrice")) {
			item = product.findElement(By.className("product-strike"));
		} else if (data.equalsIgnoreCase("DiscountedPercentage")) {
			item = product.findElement(By.className("product-discountPercentage"));
		} else if (data.equalsIgnoreCase("DiscountedPrice")) {
			item = product.findElement(By.className("product-discountedPrice"));
		} else if (data.equalsIgnoreCase("SellingPrice")) {
			item = product.findElement(By.className("product-price"));
		}
		return item;
	}

	public void verifyCategoriesTitle() {
		objGenericMethods.VerifyTwoString(getCategoryTitle(), "Categories");
	}

	public void verifyBrandTitle() {
		objGenericMethods.VerifyTwoString(getBrandTitle(), "Brand");
	}

	public void verifyPriceTitle() {
		objGenericMethods.VerifyTwoString(getPriceTitle(), "Price");
	}

	public void verifyColourTitle() {
		objGenericMethods.VerifyTwoString(getColourTitle(), "Colour");

	}

	public void verifyDiscountTitle() {
		objGenericMethods.VerifyTwoString(getDiscountTitle(), "Discount");
	}

	public void verifyProductBrand(String productCode) {
		WebElement element = SubElementOfItem(productCode, "brand");
		objGenericMethods.VerifyStringShouldNotEmpty(element, "Product brand name");
	}

	public void verifyProductName(String productCode) {
		WebElement element = SubElementOfItem(productCode, "name");
		objGenericMethods.VerifyStringShouldNotEmpty(element, "Product Name");
	}

	public void verifyDiscountedPrice(String productCode) {

		WebElement element = SubElementOfItem(productCode, "DiscountedPrice");
		objGenericMethods.VerifyStringShouldNotEmpty(element, "Discount Price");
	}

	public void verifyStrickedPrice(String productCode) {
		WebElement element = SubElementOfItem(productCode, "StrikedPrice");
		objGenericMethods.VerifyStringShouldNotEmpty(element, "Striked Price");
	}

	public void verifyDiscountPercentage(String productCode) {

		WebElement element = SubElementOfItem(productCode, "DiscountedPercentage");
		objGenericMethods.VerifyStringShouldNotEmpty(element, "Discounted Percentage");
	}

	public void clickOnPromotions() {
		objGenericMethods.takeSnapShot();
		getPromotions().click();
		objGenericMethods.ReportClickEvent("Promotions");
	}

	public void selectBuy1Get1() {
		objGenericMethods.takeSnapShot();
		getBuy1get1().click();
		objGenericMethods.ReportClickEvent("Buy1Get1");
	}

	public void ClickFilterByPrice() {
		objGenericMethods.takeSnapShot();
		getFilterByPrice().click();
		objGenericMethods.ReportClickEvent("Filter By Price");
	}

	public void ClickOnFreeGiftKamaProduct() {
		objGenericMethods.takeSnapShot();
		getFreeGiftKamaProductchk().get(getFreeGiftKamaProductchk().size() - 1).click();
		objGenericMethods.ReportClickEvent("Free Gift");
	}
	
	/**
	 * Author-Irfan Shariff
	 * Description: Created Methods for 'SaveFirstProduct' & 'AddToBagFirstItem'
	 */
	public void SaveFirstProduct()
	{
		objGenericMethods.HoverOnWebElement(firstProduct);
		getSaveFirstItem().click();	
	}


	/**
	 * Modified by : Anu
	 * Added Method for selecting first available size from PLP page.
	 * 
	 * ModifiedBy : Neeraj
	 * Description: Removed Thread.sleep and added WaitDriverWhenReady()
	 */
	public void AddToBagFirstProduct()	{
		objGenericMethods.HoverOnWebElement(firstProduct);
		objGenericMethods.waitDriverWhenReady(getAddToBagFirstItem(), "Add to bag of 1st product");
		getAddToBagFirstItem().click();
		objGenericMethods.waitDriverWhenReady(getFirstSizeAvailable(), "First Available Size");
		getFirstSizeAvailable().click();


	}

	/**
	 * Addded by : Nitesh
	 * Added Method for verifying product brand, product name and product price availability
	 */

	public List<WebElement> getProductDescription() {
		objGenericMethods.CheckWebElementsListFound(productDescription, "productDescription");
		return productDescription;
	}

	public void VerifyProductDetails()	{
		List<WebElement> productDescriptionList = getSearchResultList();
		int s = productDescriptionList.size();

		if(s>=0)
		{
			Reporter.log("Passed : Products are displayed in PLP Page with count =" + s);
		}
		else
		{
			Reporter.log("Failed : Products are not displayed in PLP Page ");
		}
		int i=1;
		for(WebElement product : productDescriptionList)
		{
			WebElement productBrand = (WebElement)product.findElement(By.xpath("(//div[@class='product-brand'])[" + (i) +"]"));
			WebElement productPrice = (WebElement)product.findElement(By.xpath("(//div[@class='product-price'])[" + (i) +"]"));
			WebElement productName = (WebElement)product.findElement(By.xpath("(//h4[@class='product-product'])[" + (i) +"]"));
			objGenericMethods.VerifyStringShouldNotEmpty(productBrand, productBrand.getText());
			objGenericMethods.VerifyStringShouldNotEmpty(productPrice, productPrice.getText());
			objGenericMethods.VerifyStringShouldNotEmpty(productName, productName.getText());
			i++;
		}
	}
	
	/**
	 * Addded by : Nitesh
	 * Added Method for verifying product brand, product name and product price, discounted price, discount percentage availability
	 */
	
	public void VerifyDiscountedProductDetails()	{
		List<WebElement> productDescriptionList = getSearchResultList();
		int s = productDescriptionList.size();

		if(s>=0)
		{
			Reporter.log("Passed : Products are filtered and total products displayed in PLP Page, count is =" + s);
		}
		else
		{
			Reporter.log("Failed : Products are not displayed in PLP Page after applying filter.");
		}
		int i=1;
		for(WebElement product : productDescriptionList)
		{
			WebElement productBrand = (WebElement)product.findElement(By.xpath("(//div[@class='product-brand'])[" + (i) +"]"));
			WebElement productName = (WebElement)product.findElement(By.xpath("(//h4[@class='product-product'])[" + (i) +"]"));
			WebElement productSellingPrice = (WebElement)product.findElement(By.xpath("(//span[@class='product-discountedPrice'])[" + (i) +"]"));
			WebElement productStrikedPrice = (WebElement)product.findElement(By.xpath("(//span[@class='product-strike'])[" + (i) +"]"));
			WebElement productPercentageDiscount = (WebElement)product.findElement(By.xpath("(//span[@class='product-discountPercentage'])[" + (i) +"]"));
			
			objGenericMethods.VerifyStringShouldNotEmpty(productBrand, productBrand.getText());
			objGenericMethods.VerifyStringShouldNotEmpty(productName, productName.getText());
			objGenericMethods.VerifyStringShouldNotEmpty(productSellingPrice, productSellingPrice.getText());
			objGenericMethods.VerifyStringShouldNotEmpty(productStrikedPrice, productStrikedPrice.getText());
			objGenericMethods.VerifyStringShouldNotEmpty(productPercentageDiscount, productPercentageDiscount.getText());
			i++;
		}
	}
	
	/**
	 * Added by - Nitesh
	 * Method to save 2nd product to wishlist
	 * @return
	 */
	
	public WebElement getSaveSecondProductToWishlist() {
		objGenericMethods.CheckWebElementFound(saveToWishlist, "saveToWishlist");
		return saveToWishlist;
	}
	
	public void clickToSaveToWishlist() {
		objGenericMethods.HoverOnWebElement(secondProduct);
		objGenericMethods.takeSnapShot();
		getSaveSecondProductToWishlist().click();
		objGenericMethods.ReportClickEvent("Save second product to wishlist");
	}
	
	/**
	 * Created By: Neeraj
	 * Description: Click Method for Free Gift Checkbox
	 */
	public void clickOnFreeGiftCheckBox()
	{
		objGenericMethods.takeSnapShot();
		getFreeGiftChk().click();
		objGenericMethods.ReportClickEvent("Free Gift Checkbox");
	}
	public PLPPageObject ReadProductCodeOfFirstItem() {
		List<WebElement> SearchList=getSearchResultList();
		WebElement aTag =SearchList.get(0).findElement(By.tagName("a"));
			String str=aTag.getAttribute("href");
			PRODUCT_CODE=str.substring(str.indexOf("/buy")-6, str.indexOf("/buy"));
			return this;
	}
	
	public void ClickmoreColors() {
		objGenericMethods.takeSnapShot();
		getMoreColors().click();
		Reporter.log("passed : MoreColor clicked sucessfully");
	}
	
	
}

