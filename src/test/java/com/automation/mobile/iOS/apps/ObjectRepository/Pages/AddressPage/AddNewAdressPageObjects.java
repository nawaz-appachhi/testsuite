package com.automation.mobile.iOS.apps.ObjectRepository.Pages.AddressPage;

import org.openqa.selenium.Keys;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.automation.core.mobile.iOS.iOSGenericMethods;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;

public class AddNewAdressPageObjects {

	public IOSDriver<IOSElement> iDriver;
	iOSGenericMethods objiOSGenericMethods;

	public AddNewAdressPageObjects(IOSDriver<IOSElement> iDriver) {
		PageFactory.initElements(new AppiumFieldDecorator(iDriver), this);
		objiOSGenericMethods = new iOSGenericMethods(iDriver);
	}

	// PLACE ORDER
	@iOSFindBy(accessibility = "PLACE ORDER")
	public IOSElement placeOrder;

	@iOSFindBy(accessibility = "Add New Address")
	public IOSElement addNewAddressBtn;

	@iOSFindBy(accessibility = "Pin Code *")
	public IOSElement pincodeAddressTxt;

	@iOSFindBy(accessibility = "Choose")
	public IOSElement chooseTownBtn;

	@FindBy(xpath = "//XCUIElementTypeOther[@name=\"ADDRESS\"]/XCUIElementTypeTextField")
	public IOSElement townLocalityTxt;

	@iOSFindBy(accessibility = "Name *")
	public IOSElement nameAddAddressTxt;

	@iOSFindBy(accessibility = "Address *")
	public IOSElement addressAddAddressTxt;

	@iOSFindBy(accessibility = "Mobile No *")
	public IOSElement mobileNumberTxt;

	@iOSFindBy(accessibility = "Office/Commercial")
	public IOSElement officeCommercialBtn;

	@iOSFindBy(accessibility = "Home")
	public IOSElement homeBtn;

	@iOSFindBy(xpath = "//XCUIElementTypeStaticText[@value='Home']")
	public IOSElement homeRadioBtn;

	/**
	 * @author 300019221 / Aravindanath Modified from xpath to accessibility
	 */
	// @iOSFindBy(accessibility = "SAVE")
	@iOSFindBy(xpath = "//XCUIElementTypeButton[@name=\"SAVE\"]")
	public IOSElement saveAddressBtn;

	@iOSFindBy(accessibility = "CONTINUE")
	public IOSElement continuePlaceOrderBtn;

	@FindBy(xpath = "//XCUIElementTypeAlert//XCUIElementTypeButton[@name='Allow']")
	public IOSElement permssionBtn;

	@iOSFindBy(xpath = "//XCUIElementTypeButton[@label='SAVE'][1]")
	public IOSElement saveBtnofTownLocality;

	@iOSFindBy(xpath = "//XCUIElementTypeOther[@name=\"ADDRESS\"]/XCUIElementTypeOther[21]/XCUIElementTypeOther")
	public IOSElement openOnSaturdays;

	/************************ getters ******************/

	public IOSElement getPlaceOrder() {
		objiOSGenericMethods.CheckIOSElementFound(placeOrder, "placeOrder");
		return placeOrder;
	}

	public IOSElement getAddNewAddressBtn() {
		objiOSGenericMethods.CheckIOSElementFound(addNewAddressBtn, "addNewAddressBtn");
		return addNewAddressBtn;
	}

	public IOSElement getPincodeAddressTxt() {
		objiOSGenericMethods.CheckIOSElementFound(pincodeAddressTxt, "pincodeAddressTxt");
		return pincodeAddressTxt;
	}

	public IOSElement getChooseTownBtn() {
		objiOSGenericMethods.CheckIOSElementFound(chooseTownBtn, "chooseTownBtn");
		return chooseTownBtn;
	}

	public IOSElement getTownLocalityTxt() {
		objiOSGenericMethods.CheckIOSElementFound(townLocalityTxt, "townLocalityTxt");
		return townLocalityTxt;
	}

	public IOSElement getNameAddAddressTxt() {
		objiOSGenericMethods.CheckIOSElementFound(nameAddAddressTxt, "nameAddAddressTxt");
		return nameAddAddressTxt;
	}

	public IOSElement getAddressAddAddressTxt() {
		objiOSGenericMethods.CheckIOSElementFound(addressAddAddressTxt, "addressAddAddressTxt");
		return addressAddAddressTxt;
	}

	public IOSElement getMobileNumberTxt() {
		objiOSGenericMethods.CheckIOSElementFound(mobileNumberTxt, "mobileNumberTxt");
		return mobileNumberTxt;
	}

	public IOSElement getOfficeCommercialBtn() {
		objiOSGenericMethods.CheckIOSElementFound(officeCommercialBtn, "officeCommercialBtn");
		return officeCommercialBtn;
	}

	public IOSElement getHomeBtn() {
		objiOSGenericMethods.CheckIOSElementFound(homeBtn, "homeBtn");
		return homeBtn;
	}

	public IOSElement getSaveAddressBtn() {
		objiOSGenericMethods.CheckIOSElementFound(saveAddressBtn, "saveAddressBtn");
		return saveAddressBtn;
	}

	public IOSElement getContinuePlaceOrderBtn() {
		objiOSGenericMethods.CheckIOSElementFound(continuePlaceOrderBtn, "continuePlaceOrderBtn");
		return continuePlaceOrderBtn;
	}

	public IOSElement getPermssionBtn() {
		objiOSGenericMethods.CheckIOSElementFound(permssionBtn, "permssionBtn");
		return permssionBtn;
	}

	public IOSElement getOpenOnSaturdays() {
		objiOSGenericMethods.CheckIOSElementFound(openOnSaturdays, "openOnSaturdays");
		return openOnSaturdays;
	}

	/**
	 * @author 300021275
	 * @return
	 */
	public IOSElement getSaveBtnofTownLocality() {
		objiOSGenericMethods.CheckIOSElementFound(saveBtnofTownLocality, "saveBtnofTownLocality");
		return saveBtnofTownLocality;
	}

	/******************** methods *************************/

	public void clickOnPlaceOrder() {
		objiOSGenericMethods.clickOnIOSElement(getPlaceOrder(), "Place Order button");
	}

	/**
	 * @author 300019221 /Aravindanath Added try block and if condition.
	 */

	public void clickOnAddNewAddress() {
		try {
			objiOSGenericMethods.clickOnIOSElement(getAddNewAddressBtn(), "Add New Address button ");
		} catch (Exception e) {
			System.out.println("Add new button is not displayed!");
		}
	}

	public void enterPinCode(String pincode) throws InterruptedException {
		objiOSGenericMethods.clickOnIOSElement(getPincodeAddressTxt(), "Enter Pincode");
		getPincodeAddressTxt().sendKeys(pincode);
	}

	public void clickOnSaveAddressBtn() {
		objiOSGenericMethods.clickOnIOSElement(getSaveAddressBtn(), "Save Address button");
	}

	public void clickOnContinuePlaceOrder() {
		objiOSGenericMethods.clickOnIOSElement(getContinuePlaceOrderBtn(), "Continue PlaceOrder button");
	}

	public void clickOnEnterTownLocalityBtn() {
		objiOSGenericMethods.clickOnIOSElement(getTownLocalityTxt(), "Edit address button");
	}

	public void enterTownLocality(String townLocality) {
		getTownLocalityTxt().sendKeys(townLocality);
		getTownLocalityTxt().sendKeys(Keys.ENTER);
		System.out.println("town locality entered");
	}

	public void clickOnLocalitySave() {
		objiOSGenericMethods.clickOnIOSElement(getSaveBtnofTownLocality(), "Save button");
	}

	public void clickOnChoose() {
		objiOSGenericMethods.clickOnIOSElement(getChooseTownBtn(), "Choose button");
	}

	/**
	 * @author 300019221 Replaced thread.sleep with webdriver wait
	 * @param name
	 * @throws InterruptedException
	 */
	public void enterNameAddAddress(String name) throws InterruptedException {
		// objiOSGenericMethods.waitForElementVisibility(getNameAddAddressTxt());
		// getNameAddAddressTxt().click();
		objiOSGenericMethods.clickOnIOSElement(getNameAddAddressTxt(), "Name");
		getNameAddAddressTxt().sendKeys(name);
		System.out.println("Name entered");
	}

	public void enterMobileNumber(String mobileNumber) {
		getMobileNumberTxt().sendKeys(mobileNumber);
		System.out.println("Mobile number entered");
	}

	public void clickOnOfficeCommercial() throws InterruptedException {
		objiOSGenericMethods.clickOnIOSElement(getOfficeCommercialBtn(), "Office/Commercial Button");
	}

	public void clickOnHome() throws InterruptedException {
		objiOSGenericMethods.clickOnIOSElement(getHomeBtn(), "Home Button");
	}

	public void clickOnPermissionBtn() {
		objiOSGenericMethods.clickOnIOSElement(getPermssionBtn(), "Permission button");
	}

	public void clickOnOpenOnSaturdays() {
		objiOSGenericMethods.clickOnIOSElement(getOpenOnSaturdays(), "Open on saturday Button");
	}

	/**
	 * * @author 300019221 Replaced thread.sleep with webdriver wait
	 * 
	 * @param pincode
	 * @param locality
	 * @param name
	 * @param address
	 * @param mobile
	 * @throws InterruptedException
	 */

	public void EnterContinueOrderAddingAddress(String pincode, String locality, String name, String address,
			String mobile) throws InterruptedException {

		objiOSGenericMethods.waitForElementVisibility(getPincodeAddressTxt());
		try {
			if (getPincodeAddressTxt().isDisplayed()) {
				getPincodeAddressTxt().sendKeys(pincode);
				clickOnChoose();
				// objiOSGenericMethods.waitForElementVisibility(getTownLocalityTxt());
				getTownLocalityTxt().sendKeys(locality);
				clickOnLocalitySave();
				// objiOSGenericMethods.waitForElementVisibility(getNameAddAddressTxt());
				objiOSGenericMethods.clickOnIOSElement(getNameAddAddressTxt(), "Name");
				// getNameAddAddressTxt().click();
				// objiOSGenericMethods.waitForElementVisibility(getNameAddAddressTxt());
				getNameAddAddressTxt().sendKeys(name);
				// objiOSGenericMethods.waitForElementVisibility(getAddressAddAddressTxt());
				getAddressAddAddressTxt().sendKeys(address);
				// objiOSGenericMethods.waitForElementVisibility(getMobileNumberTxt());
				getMobileNumberTxt().sendKeys(mobile);
				// objiOSGenericMethods.waitForElementVisibility(getHomeBtn());
				if (getHomeBtn().isDisplayed()) {
//					getHomeBtn().click();
					objiOSGenericMethods.clickOnIOSElement(getHomeBtn(), "Home");
				}
				clickOnSaveAddressBtn();
			}
		} catch (Exception e) {
		}
	}

	/**
	 * @author 300019221 Replaced thread.sleep with webdriver wait
	 * @param pincode
	 * @param locality
	 * @param name
	 * @param address
	 * @param mobile
	 * @throws InterruptedException
	 */

	public void EnterContinueOrderAddingOfficeAddress(String pincode, String locality, String name, String address,

			String mobile) throws InterruptedException {

		objiOSGenericMethods.waitForElementVisibility(getPincodeAddressTxt());
		try {
			if (getPincodeAddressTxt().isDisplayed()) {
				getPincodeAddressTxt().sendKeys(pincode);
				clickOnChoose();
				// objiOSGenericMethods.waitForElementVisibility(getTownLocalityTxt());
				getTownLocalityTxt().sendKeys(locality);
				clickOnLocalitySave();
				// objiOSGenericMethods.waitForElementVisibility(getNameAddAddressTxt());
				objiOSGenericMethods.clickOnIOSElement(getNameAddAddressTxt(), "Name");
				// getNameAddAddressTxt().click();
				// objiOSGenericMethods.waitForElementVisibility(getNameAddAddressTxt());
				getNameAddAddressTxt().sendKeys(name);
				// objiOSGenericMethods.waitForElementVisibility(getAddressAddAddressTxt());
				getAddressAddAddressTxt().sendKeys(address);
				// objiOSGenericMethods.waitForElementVisibility(getMobileNumberTxt());
				getMobileNumberTxt().sendKeys(mobile);
				// objiOSGenericMethods.waitForElementVisibility(getOfficeCommercialBtn());
				if (getOfficeCommercialBtn().isDisplayed()) {
//					getOfficeCommercialBtn().click();
					objiOSGenericMethods.clickOnIOSElement(getOfficeCommercialBtn(), "Office/Commercial");
				}
				objiOSGenericMethods.swipeDown(100, 1);
				// objiOSGenericMethods.waitForElementVisibility(getSaveAddressBtn());
				if (getSaveAddressBtn().isDisplayed()) {
//					getSaveAddressBtn().click();
					objiOSGenericMethods.clickOnIOSElement(getSaveAddressBtn(), "Save Address");
				}

			}

		} catch (

		Exception e) {

		}
	}

}
