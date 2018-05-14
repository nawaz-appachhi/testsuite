package com.automation.mobile.iOS.apps.ObjectRepository.Categories;

import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.automation.core.mobile.iOS.iOSGenericMethods;

import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;

public class MenCategoriesPageObjects {

	iOSGenericMethods objiOSGenericMethods;

	public IOSDriver<IOSElement> iDriver;

	public MenCategoriesPageObjects(IOSDriver<IOSElement> iDriver) {
		PageFactory.initElements(new AppiumFieldDecorator(iDriver), this);
		objiOSGenericMethods = new iOSGenericMethods(iDriver);
	}

	/**
	 * @author 300019221 / Aravindanath accessibility @iOSFindBy(accessibility =
	 *         "Categories") to xpath
	 */
	@iOSFindBy(xpath = "//XCUIElementTypeButton[@name='Categories']")
	public IOSElement categories;

	/**
	 * Modified By Subhasis //XCUIElementTypeOther[@name='Men T-Shirts, Shirts,
	 * Jeans, Shoes, Accessories '] Men T-Shirts, Shirts, Jeans, Shoes, Accessories
	 */
	@iOSFindBy(xpath = "//XCUIElementTypeOther[@name=\"Men T-Shirts, Shirts, Jeans, Shoes, Accessories \"]")
	public IOSElement men;

	@iOSFindBy(className = "XCUIElementTypeOther")
	public List<IOSElement> menList;
	/**
	 * Men Categories
	 */

	@iOSFindBy(accessibility = "Brands")
	public IOSElement menBrands;

	@iOSFindBy(xpath = "//XCUIElementTypeOther[@name=\"New Season\"])[2]")
	public IOSElement menNewSeason;

	@iOSFindBy(xpath = "//XCUIElementTypeOther[@name=\"Global Trends\"])[2]")
	public IOSElement menGlobalTrends;

	/**
	 * Modified By Subhasis
	 * 
	 * @iOSFindBy(accessibility = "Topwear ÃƒÂ¯Ã¯Â¿Â½Ã‚Â¸")
	 * @author 300019221 / Aravindanath modified to xpath
	 */

	@iOSFindBy(xpath = "//XCUIElementTypeOther[@name=\"Topwear ï�¸\"]")
	public IOSElement menTopwear;

	//

	@iOSFindBy(accessibility = "T-Shirts")
	public IOSElement menTshirt;

	@iOSFindBy(accessibility = "Jeans")
	public IOSElement jeans;
	// T-Shirts

	@iOSFindBy(accessibility = "Track Pants/Joggers")
	public IOSElement trackPants;

	/**
	 * modified by subhasis (//XCUIElementTypeOther[@name=\"
	 * \"])[2]/XCUIElementTypeOther[1]/XCUIElementTypeOther
	 * 
	 * @author 300019221 / Aravindanath (//XCUIElementTypeOther[@name="
	 *         "])[2]/XCUIElementTypeOther[1]/XCUIElementTypeOther
	 *         
	 *         @author 300019221 Updated xpath
	 */

	@iOSFindBy(xpath = "//XCUIElementTypeOther[@name=\" \"][2]/XCUIElementTypeOther[1]/XCUIElementTypeOther")
	public IOSElement TshirtFirstBanner;

	@iOSFindBy(xpath = "(//XCUIElementTypeOther[@name=\" \"])[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther")
	public IOSElement JeansFirstBanner;

	@iOSFindBy(xpath = "//XCUIElementTypeOther[@name=\"Bottomwear ÃƒÆ’Ã‚Â¯ÃƒÂ¯Ã‚Â¿Ã‚Â½Ãƒâ€šÃ‚Â¸\"]")
	public IOSElement menBottomwear;

	@iOSFindBy(xpath = "//XCUIElementTypeOther[@name=\"Sports & Active Wear ÃƒÆ’Ã‚Â¯ÃƒÂ¯Ã‚Â¿Ã‚Â½Ãƒâ€šÃ‚Â¸\"])[2]")
	public IOSElement menSportsAndActiveWear;

	@iOSFindBy(xpath = "//XCUIElementTypeOther[@name=\"Inner & Sleepwear\"]")
	public IOSElement menInnerAndSleepWear;

	@iOSFindBy(xpath = "//XCUIElementTypeOther[@name=\"Indian & Festive Wear ÃƒÆ’Ã‚Â¯ÃƒÂ¯Ã‚Â¿Ã‚Â½Ãƒâ€šÃ‚Â¸\"]")
	public IOSElement menIndianFestivalWear;

	@iOSFindBy(xpath = "//XCUIElementTypeOther[@name=\"Plus Size\"]")
	public IOSElement menPlusSize;

	@iOSFindBy(xpath = "//XCUIElementTypeOther[@name=\"Footwear ÃƒÆ’Ã‚Â¯ÃƒÂ¯Ã‚Â¿Ã‚Â½Ãƒâ€šÃ‚Â¸\"])[2]")
	public IOSElement menFootWear;

	@iOSFindBy(accessibility = "Watches")
	public IOSElement menWatches;

	@iOSFindBy(xpath = "//XCUIElementTypeOther[@name=\"Sunglasses & Frames\"])[2]")
	public IOSElement menSunglassess;

	@iOSFindBy(xpath = "//XCUIElementTypeOther[@name=\"Headphones & Speakers\"])[2]")
	public IOSElement menHeadPhones;

	@iOSFindBy(xpath = "//XCUIElementTypeOther[@name=\"Bags & Backpacks\"]")
	public IOSElement menBagsAndBackpacks;

	@iOSFindBy(xpath = "//XCUIElementTypeOther[@name=\"Luggage & Trolleys\"])[2]")
	public IOSElement menLuggageAndTrolleys;

	@iOSFindBy(xpath = "//XCUIElementTypeOther[@name=\"Personal Care ÃƒÆ’Ã‚Â¯ÃƒÂ¯Ã‚Â¿Ã‚Â½Ãƒâ€šÃ‚Â¸\"]")
	public IOSElement menPersonalCare;

	@iOSFindBy(xpath = "//XCUIElementTypeOther[@name=\"Accessories ÃƒÆ’Ã‚Â¯ÃƒÂ¯Ã‚Â¿Ã‚Â½Ãƒâ€šÃ‚Â¸\"])[2]")
	public IOSElement menAccessories;

	@iOSFindBy(xpath = "(//XCUIElementTypeOther[@name=\" \"])[3]/XCUIElementTypeOther[1]/XCUIElementTypeOther")
	// @iOSFindBy(xpath = "(//XCUIElementTypeOther[@name=\"
	// \"])[2]/XCUIElementTypeOther[1]/XCUIElementTypeOther")
	public IOSElement menFirstBanner;

	@iOSFindBy(xpath = "//XCUIElementTypeButton[@name='OK']")
	public WebElement tapAndHold;

	@iOSFindBy(xpath = "//XCUIElementTypeOther[@name=\"T-Shirts\"]")
	public IOSElement menT_Shirts;

	/********* getters ************/

	public IOSElement getCategories() {
		return categories;
	}

	public IOSElement getMenT_Shirts() {
		return menT_Shirts;
	}

	public IOSElement getjeans() {
		return jeans;
	}

	public IOSElement getTrackPants() {
		return trackPants;
	}

	/**
	 * @author 300019221 / Aravindanath Modified this method to list, As xpath and
	 *         accessibility was not helping to handle the flow.
	 */
	public void clickOnMen() {

		getmenList().get(18).click();

		// try {
		// Thread.sleep(1000);
		// if(getMen().isDisplayed()) {
		// getMen().click();
		// System.out.println("Succesfully click on men");
		// }
		// } catch (Exception e) {
		// System.out.println("Men not found!");
		// }

	}

	/**
	 * @author 300019221 / Aravindanath This method will take the items from
	 *         category in to a list.
	 */
	public void categoryList() {

		System.out.println(getmenList().get(18).getText());
		getmenList().get(18).click();

		// for (Iterator<IOSElement> iterator = getmenList().iterator();
		// iterator.hasNext();) {
		//
		// WebElement WebElement = (WebElement) iterator.next();
		//
		// System.err.println("List of Items on category page --> " +
		// WebElement.getText());
		//
		//
		// }

	}

	public void clickOnMenBrands() {
		getMenBrands().click();
		System.out.println("Succesfully click on men Brands");
	}

	public void clickOnMenNewSeason() {
		menNewSeason.click();
		System.out.println("Succesfully click on men New Season");
	}

	public void clickOnMenGlobalTrends() {
		menGlobalTrends.click();
		System.out.println("Succesfully click on men Global Trends");
	}

	/**
	 * @author 300019221 / Aravindanth Modified clickOnMenTopwear method to list,
	 *         due to special charter in the xpath and accessibility id. No 39 is
	 *         for Top-wear in the list. No 43
	 */
	public void clickOnMenTopwear() {
		getmenList().get(39).click();
		System.out.println("User clicked on " + getmenList().get(39).getText());
		// for (Iterator<IOSElement> iterator = getmenList().iterator();
		// iterator.hasNext();) {
		//
		// WebElement WebElement = (WebElement) iterator.next();
		//
		// System.err.println("Verifying Details of Page --> " + WebElement.getText());
		//
		// // filterDiscountPercent.get(i).click();
		//
		// }

		// if (getMenTopwear().isDisplayed()) {
		// getMenTopwear().click();
		// System.out.println("Succesfully click on men Topwear");
		// }
	}

	/**
	 * @author 300019221 / Aravinda This method will click on men bottom wear
	 */

	public void clickOnMenBottomWear() {
		getmenList().get(43).click();
		System.out.println("User clicked on " + getmenList().get(43).getText());
	}

	/**
	 * @author 300019221 / Aravinda This method will click on men jean wear
	 */
	public void clickOnJeans() {

		try {
			if (getjeans().isDisplayed()) {
				getjeans().click();

			}
		} catch (Exception e) {
			System.out.println("Men Jeans is not displayed!");
		}

	}

	/**
	 * @author 300019221 / Aravinda This method will click on men Track wear
	 * 
	 * @author 300019221 Added tap and hold method
	 */
	public void clickOnTrackPants() {
		try {
			if (tapAndHold.isDisplayed()) {
				System.err.println("Tap and Hold button is displayed!");
				tapAndHold.click();
			}

		} catch (Exception e) {

		}
		try {
			if (getTrackPants().isDisplayed()) {
				getTrackPants().click();

			}
		} catch (Exception ee) {
			System.out.println("Men track is not displayed!");
		}

	}

	public void clickOnMenBottomwear() {
		menBottomwear.click();
		System.out.println("Succesfully click on men Bottomwear");
	}

	public void clickOnMenSportsAndActiveWear() {
		menSportsAndActiveWear.click();
		System.out.println("Succesfully click on men Sports And Active Wear");
	}

	public void clickOnMenInnerAndSleepWear() {
		menInnerAndSleepWear.click();
		System.out.println("Succesfully click on men Inner And Sleep Wear");
	}

	public void clickOnMenIndianFestivalWear() {
		menIndianFestivalWear.click();
		System.out.println("Succesfully click on men Indian Festival Wear");
	}

	public void clickOnMenPlusSize() {
		menPlusSize.click();
		System.out.println("Succesfully click on men Plus Size");
	}

	public void clickOnMenFootWear() {
		menFootWear.click();
		System.out.println("Succesfully click on men Foot Wear");
	}

	public void clickOnMenWatches() {
		menWatches.click();
		System.out.println("Succesfully click on men Watches");
	}

	public void clickOnMenSunglassess() {
		menSunglassess.click();
		System.out.println("Succesfully click on men Sunglassess");
	}

	public void clickOnMenHeadPhones() {
		getMenHeadPhones().click();
		System.out.println("Succesfully click on men HeadPhones");
	}

	public void clickOnMenBagsAndBackpacks() {
		menBagsAndBackpacks.click();
		System.out.println("Succesfully click on men Bags And Backpacks");
	}

	public void clickOnMenLuggageAndTrolleys() {
		menLuggageAndTrolleys.click();
		System.out.println("Succesfully click on men Luggage And Trolleys");
	}

	public void clickOnMenPersonalCare() {
		menPersonalCare.click();
		System.out.println("Succesfully click on men Personal Care");
	}

	public void clickOnMenAccessories() {
		menAccessories.click();
		System.out.println("Succesfully click on men Accessories");
	}

	public IOSElement getTshirtFirstBanner() {

		return TshirtFirstBanner;
	}

	public IOSElement getJeansFirstBanner() {

		return JeansFirstBanner;
	}

	public List<IOSElement> getmenList() {

		return menList;
	}

	public IOSElement getMen() {
		return men;
	}

	public IOSElement getMenBrands() {
		return menBrands;
	}

	public IOSElement getMenNewSeason() {
		return menNewSeason;
	}

	public IOSElement getMenGlobalTrends() {
		return menGlobalTrends;
	}

	public IOSElement getMenTopwear() {
		return menTopwear;
	}

	public IOSElement getMenBottomwear() {
		return menBottomwear;
	}

	public IOSElement getMenSportsAndActiveWear() {
		return menSportsAndActiveWear;
	}

	public IOSElement getMenInnerAndSleepWear() {
		return menInnerAndSleepWear;
	}

	public IOSElement getMenIndianFestivalWear() {
		return menIndianFestivalWear;
	}

	public IOSElement getMenPlusSize() {
		return menPlusSize;
	}

	public IOSElement getMenFootWear() {
		return menFootWear;
	}

	public IOSElement getMenWatches() {
		return menWatches;
	}

	public IOSElement getMenSunglassess() {
		return menSunglassess;
	}

	public IOSElement getMenHeadPhones() {
		return menHeadPhones;
	}

	public IOSElement getMenBagsAndBackpacks() {
		return menBagsAndBackpacks;
	}

	public IOSElement getMenLuggageAndTrolleys() {
		return menLuggageAndTrolleys;
	}

	public IOSElement getMenPersonalCare() {
		return menPersonalCare;
	}

	public IOSElement getMenAccessories() {
		return menAccessories;
	}

	public void clickOnMenTshirts() {
		objiOSGenericMethods.clickOnIOSElement(getMenT_Shirts(), "Successfully click on T-shirt button");

	}

	/**
	 * @author 300019221 / Aravindanath Getter was created and added try block and
	 *         if conditions.
	 * @throws InterruptedException
	 */
	public void clickOnCategories() throws InterruptedException {
		try {
			if (getCategories().isDisplayed()) {
				objiOSGenericMethods.clickOnIOSElement(getCategories(), "Successfully click on Categories");
				System.out.println("Succesfully click on Categories");
			}
		} catch (Exception e) {
			System.out.println("Categories tab was not found!");
		}

	}

	public void clickOnMenFirstBanner() throws InterruptedException {
		menFirstBanner.click();
		System.out.println("Succesfully click on men First Banner");
		try {
			if (tapAndHold.isDisplayed()) {
				System.err.println("Tap and Hold button is displayed!");
				tapAndHold.click();
			}

		} catch (Exception e) {
		}
	}

	@iOSFindBy(xpath = "//XCUIElementTypeOther")
	public List<IOSElement> productList;

	public List<IOSElement> getProductList() {
		return productList;
	}

	public void clickOnProduct(int i) {
		getProductList().get(i).click();
	}

	public IOSElement getMenTshirt() {
		return menTshirt;
	}

	public void clickOnMenTshirt() {

		try {
			if (getMenTshirt().isDisplayed()) {
				getMenTshirt().click();

			}
		} catch (Exception e) {
			System.out.println("Men Tshirt is not displayed!");
		}

	}

	/**
	 * @author 300019221
	 * Used generic method to click.
	 */
	public void clickOnTshirtFirstBanner() {
		try {
			objiOSGenericMethods.waitForElementVisibility(getTshirtFirstBanner());
			if (getTshirtFirstBanner().isDisplayed()) {
				objiOSGenericMethods.clickOnIOSElement(getTshirtFirstBanner(),
						"T-Shirt first banner clicked succesfully");

			}
		} catch (Exception e) {
			System.out.println("T-Shirt first banner is not found!");
		}
	}
	/**
	 * @author 300019221
	 * Used generic method to click.
	 */
	public void clickOnJeansFirstBanner() {
		try {
			objiOSGenericMethods.waitForElementVisibility(getJeansFirstBanner());
			if (getJeansFirstBanner().isDisplayed()) {
				objiOSGenericMethods.clickOnIOSElement(getJeansFirstBanner(),	"Jeans first banner clicked succesfully");
			}
		} catch (Exception e) {
			System.out.println("Jeans first banner is not found!");
		}
	}

}
