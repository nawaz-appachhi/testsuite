
package com.automation.mobile.iOS.MobileWeb.ObjectRepository.AddressPageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.automation.core.mobile.iOS.iOSGenericMethods;
import com.automation.mobile.iOS.MobileWeb.ObjectRepository.HomeObjects.HamburgerPageObjects;
import com.automation.mobile.iOS.MobileWeb.ObjectRepository.HomeObjects.HomePageObjects;
import com.automation.mobile.iOS.MobileWeb.ObjectRepository.WishList.WishListPageObject;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

/**
 * Add new address - Monika
 */

public class AddressPageObjects {

	public AppiumDriver<MobileElement> iDriver;
	iOSGenericMethods objiOSGenericMethods;
	HomePageObjects objHomePageObjects;
	HamburgerPageObjects objHamburgerPageObjects;
	WishListPageObject objWishListPageObject;

	public AddressPageObjects(AppiumDriver<MobileElement>  iDriver) {
		PageFactory.initElements(new AppiumFieldDecorator(iDriver), this);
		objiOSGenericMethods = new iOSGenericMethods(iDriver);
		objHomePageObjects = new HomePageObjects(iDriver);
		objHamburgerPageObjects = new HamburgerPageObjects(iDriver);
		objWishListPageObject = new WishListPageObject(iDriver);
	}

	@FindBy(xpath = ".//input[@id='pincode']")
	public IOSElement PincodeAddress;

	@FindBy(xpath = ".//*[text()='Choose']")
	public IOSElement ChooseTown;

	@FindBy(xpath = ".//div[@class=\"suggestions\"]")
	public List<IOSElement> ChooseByTown;

	@FindBy(xpath = ".//input[@id='name']")
	public IOSElement Name;

	@FindBy(xpath = ".//input[@id='name']")
	public IOSElement Editname;

	@FindBy(xpath = ".//textarea[@id='address']")
	public IOSElement Address;

	@FindBy(xpath = ".//input[@id='mobile']")
	public IOSElement MobileNumber;

	/**
	 * This xpath is modified by subhasis
	 * @FindBy(xpath = ".//div[@value='Office']")
	 */
	
	@FindBy(xpath = "//i[@id='ofc' and text()='Office/Commercial']")
	public IOSElement OfficeCommercial;

	/**
	 * Modified by Subahsis
	 */
	// @FindBy(xpath = ".//div[@class='home']")
//	@FindBy(id = "home")
	@FindBy(xpath="//i[@id='home' and text()='Home']")
	public IOSElement Home;

	@FindBy(xpath = ".//div[@value='sat']")
	public IOSElement OpenOnSaturdays;
	/**
	 * @author 300019221 / Aravindanath
	 */
	@FindBy(xpath = "//button[@class='green-button submit clickable'][contains(text(),'SAVE')]")
	public IOSElement SaveAddress;

	@FindBy(xpath = ".//*[text()='Add new address']")
	public IOSElement AddNewAddress;

	/**
	 * @author 300019221 Aravindanath Modified linktext to xpath
	 */

	@FindBy(xpath = "//*[@class='info'][contains(text(),'Addresses')]")
	public IOSElement profileAddresslink;

	@FindBy(xpath = "//div[text()=' Save your addresses now ']")
	public IOSElement NoSavedAddress;

	@FindAll(@FindBy(xpath = "//div[@class='addressAccordian-address']"))
	public List<IOSElement> AllSavedAddresses;

	@FindBy(xpath = "//div[@class='addressAccordian-buttons']/div[3]")
	public IOSElement RemoveAllSavedAddresses;

	@FindBy(xpath = "//div[text()='Delete']")
	public IOSElement DeleteSelectedAddress;

	@FindBy(className = "address-content")
	public IOSElement addressAdded;

	@FindBy(xpath = "//div[@class='address']")
	public IOSElement addressSelected;

	public IOSElement getAddressAdded() {
		objiOSGenericMethods.CheckIOSElementFound(addressAdded, "addressAdded");
		return addressAdded;
	}

	public IOSElement getAddressSelected() {
		objiOSGenericMethods.CheckIOSElementFound(addressSelected, "addressSelected");
		return addressSelected;
	}

	public IOSElement getProfileAddresslink() {
		objiOSGenericMethods.CheckIOSElementFound(profileAddresslink, "profileAddresslink");
		return profileAddresslink;
	}

	public IOSElement getAddNewAddress() {
		objiOSGenericMethods.CheckIOSElementFound(AddNewAddress, "AddNewAddress");
		return AddNewAddress;
	}

	public IOSElement getPincodeAddress() {
		objiOSGenericMethods.CheckIOSElementFound(PincodeAddress, "PincodeAddress");
		return PincodeAddress;
	}

	public IOSElement getChooseTown() {
		objiOSGenericMethods.CheckIOSElementFound(ChooseTown, "ChooseTown");
		return ChooseTown;
	}

	public List<IOSElement> getChooseByTown() {
		objiOSGenericMethods.CheckIOSElementsListFound(ChooseByTown, "ChooseByTown");
		return ChooseByTown;
	}

	public IOSElement getName() {
		objiOSGenericMethods.CheckIOSElementFound(Name, "Name");
		return Name;
	}

	public IOSElement getEditname() {
		objiOSGenericMethods.CheckIOSElementFound(Editname, "Editname");
		return Editname;
	}

	public IOSElement getAddress() {
		objiOSGenericMethods.CheckIOSElementFound(Address, "Address");
		return Address;
	}

	public IOSElement getMobileNumber() {
		objiOSGenericMethods.CheckIOSElementFound(MobileNumber, "MobileNumber");
		return MobileNumber;
	}

	public IOSElement getOfficeCommercial() {
		objiOSGenericMethods.CheckIOSElementFound(OfficeCommercial, "OfficeCommercial");
		return OfficeCommercial;
	}

	public IOSElement getHome() {
		objiOSGenericMethods.CheckIOSElementFound(Home, "Home");
		return Home;
	}

	public IOSElement getopenOnSaturdays() {
		objiOSGenericMethods.CheckIOSElementFound(OpenOnSaturdays, "OpenOnSaturdays");
		return OpenOnSaturdays;
	}

	public IOSElement getSaveAddress() {
		objiOSGenericMethods.CheckIOSElementFound(SaveAddress, "SaveAddress");
		return SaveAddress;
	}

	/**
	 * @author 300019221 added try block
	 * @param pincode
	 */
	public void enterPincone(String pincode) {
		try {
			if (PincodeAddress.isDisplayed()) {
				PincodeAddress.sendKeys(pincode);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void ClickOnPincodeAddress() {
		objiOSGenericMethods.waitDriver(getPincodeAddress(), "Pincode");
		try {
			if(getPincodeAddress().isDisplayed())
			objiOSGenericMethods.waitDriver(getPincodeAddress(), "Pincode");
			objiOSGenericMethods.clickOnIOSElement(getPincodeAddress(), "pincode");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @author 300019221 Added try block and if condition Added Java script executor
	 *         click
	 */
	public void clickOnSaveAddress() {
		try {
//			Thread.sleep(500);
			objiOSGenericMethods.waitDriver(getSaveAddress(), "Save Address");
			if (getSaveAddress().isDisplayed()) {
				objiOSGenericMethods.click(getSaveAddress());
				// objiOSGenericMethods.clickOnIOSElement(getSaveAddress(), "clicked on save
				// addres");
				System.out.println("User clicked on save button!");
			}
		} catch (Exception e) {
			System.out.println("Save button is not displayed!");
			e.printStackTrace();
		}
	}

	public void clickOnChoose() {
		//getChooseTown().click();
		 objiOSGenericMethods.clickOnIOSElement(getChooseTown(), "choose town");
	}

	public void selectTownLocality(String town) {
		List<IOSElement> selectTownLocalityList = getChooseByTown();
		for (IOSElement townLocaity : selectTownLocalityList) {
			IOSElement button = (IOSElement) townLocaity.findElement(By.tagName("button"));
			String townString = button.getText();
			if (townString.contains(town)) {
				button.click();
				break;
			}
		}
	}

	public void entername(String name) {
		objiOSGenericMethods.clickOnIOSElement(getName(), "Name");
		getName().sendKeys(name);
	}

	public void enterEditName(String name) {
		objiOSGenericMethods.clickOnIOSElement(getEditname(), "edit Name");
		getEditname().clear();
		getEditname().sendKeys(name);
	}

	public void enteraddress(String address) {
		objiOSGenericMethods.clickOnIOSElement(getAddress(), "address");
		getAddress().sendKeys(address);
	}

	public void enterMobileNumber(String mobile) {
		objiOSGenericMethods.clickOnIOSElement(getMobileNumber(), "mobile number");
		getMobileNumber().sendKeys(mobile);
	}

	/**
	 * @author 300019221 / Aravindanath Added try block and if condition
	 */
	public void clickOnOfficeCommercial() {
		try {
			if (getOfficeCommercial().isDisplayed()) {
				objiOSGenericMethods.clickOnIOSElement(getOfficeCommercial(), "office/commercial");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * Modified by - Nitesh
	 * called generic method to get click message
	 */
	public void clickOnHome() {
		objiOSGenericMethods.clickOnIOSElement(getHome(), "Home radio button ");
	}

	public void clickOnOpenOnSaturdays() {
		objiOSGenericMethods.clickOnIOSElement(getopenOnSaturdays(), "Opens on saturday");
	}

	public void clickOnAddNewAddress() {
		objiOSGenericMethods.clickOnIOSElement(getAddNewAddress(), "Opnes on sunday");
	}

	public void clickOnProfileAddresslink() {
		try {
			if (getProfileAddresslink().isDisplayed()) {
				objiOSGenericMethods.waitForElementVisibility(getProfileAddresslink());
				objiOSGenericMethods.click(getProfileAddresslink());
				// objiOSGenericMethods.clickOnIOSElement(getProfileAddresslink(),
				// "clicked on Address link under my account");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public WebElement getNoSavedAddress() {
		objiOSGenericMethods.CheckIOSElementFound(NoSavedAddress, "NoSavedAddress");
		return NoSavedAddress;
	}

	public List<IOSElement> getAllSavedAddresses() {
		objiOSGenericMethods.CheckIOSElementsListFound(AllSavedAddresses, "AllSavedAddresses");
		return AllSavedAddresses;
	}

	public WebElement getRemoveAllSavedAddresses() {
		objiOSGenericMethods.CheckIOSElementFound(RemoveAllSavedAddresses, "RemoveAllSavedAddresses");
		return RemoveAllSavedAddresses;
	}

	public WebElement getDeleteSelectedAddress() {
		objiOSGenericMethods.CheckIOSElementFound(DeleteSelectedAddress, "DeleteSelectedAddress");
		return DeleteSelectedAddress;
	}

	public boolean IsAddressEmpty() {
		try {
			getNoSavedAddress().getText();
			return true;
		} catch (Exception e) {
			System.out.println("Saved Addresses are there");
			return false;
		}
	}

	/**
	 * @author 300019221 / Aravinda JS executor click is used
	 */
	public void RemoveAllSavedAddressesFromAddressPage() throws InterruptedException {
		List<IOSElement> links = getAllSavedAddresses();
		for (int i = 0; i < links.size(); i++) {
			objiOSGenericMethods.click(getRemoveAllSavedAddresses());
			// getRemoveAllSavedAddresses().click();
			//Thread.sleep(1000);
			objiOSGenericMethods.click(getDeleteSelectedAddress());
			// getDeleteSelectedAddress().click();
			//Thread.sleep(1000);
		}
	}

	/*
	 * Method - Reset Address Added by - Nitesh
	 */

	/**
	 * @author 300019221 aravinda
	 * Replaced thread.sleep to webdriver wait
	 * @throws InterruptedException
	 */
	public void resetAddress() throws InterruptedException {
		try {
			// Empty Address Page
			objHomePageObjects.clickOnHamburgerButton();
			objHamburgerPageObjects.clickOnMyAccount();
//			Thread.sleep(2000);
			clickOnProfileAddresslink();
			System.out.println("clicked on address");
			if (IsAddressEmpty()) {
				objWishListPageObject.clickOnMyntraLogo();
			} else {
				RemoveAllSavedAddressesFromAddressPage();
//				Thread.sleep(1000);
				objiOSGenericMethods.waitDriver(objWishListPageObject.getmyntraLogo(), "Myntra Logo");
				objWishListPageObject.clickOnMyntraLogo();
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void VerifyAddressAdded() {
		objiOSGenericMethods.VerifyStringShouldNotEmpty(getAddressAdded(), "Address Added");
	}

	public void VerifyAddressSelected() {
		objiOSGenericMethods.VerifyStringShouldNotEmpty(getAddressSelected(), "Address Added");
	}

}
