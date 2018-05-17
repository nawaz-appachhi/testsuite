package com.automation.mobile.iOS.apps.ObjectRepository.Pages.AddressPage;

import java.util.List;

import org.openqa.selenium.Keys;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.Reporter;

import com.automation.core.mobile.iOS.iOSGenericMethods;

import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;

public class EditAdressPageObject {

	iOSGenericMethods objiOSGenericMethods;
	public IOSDriver<IOSElement> iDriver;

	public EditAdressPageObject(IOSDriver<IOSElement> iDriver) {
		PageFactory.initElements(new AppiumFieldDecorator(iDriver), this);
		objiOSGenericMethods = new iOSGenericMethods(iDriver);
	}

	@iOSFindBy(accessibility = "Edit/Change (1)")
	public IOSElement editAddressTxt;

	@iOSFindBy(accessibility = "Edit/Change (2)")
	public IOSElement editAddressTxt2;
	
	@iOSFindBy(accessibility = "Edit/Change (3)")
	public IOSElement editAddressTxt3;

	@iOSFindBy(accessibility = "EDIT")
	public IOSElement editButton;

	@iOSFindBy(accessibility = "REMOVE")
	public IOSElement removeAddressButton;

	@iOSFindBy(accessibility = "CONFIRM")
	public IOSElement confirmBtn;

	@iOSFindBy(accessibility = "Name")
	public IOSElement nameAddAddressTxt;

	@FindBy(xpath = "//XCUIElementTypeAlert//XCUIElementTypeButton[@name='Allow']")
	public IOSElement permssionBtn;

	@iOSFindBy(accessibility = "SAVE")
	public IOSElement saveAddressBtn;

	@iOSFindBy(accessibility = "EDIT")
	public IOSElement editAddressEditTxt;

	/************** getters **************/

	public IOSElement getEditAddressTxt() {
		objiOSGenericMethods.CheckIOSElementFound(editAddressTxt, "editAddressTxt");
		return editAddressTxt;
	}

	public IOSElement getEditButton() {
		objiOSGenericMethods.CheckIOSElementFound(editButton, "editButton");
		return editButton;
	}

	public IOSElement getNameAddAddressTxt() {
		objiOSGenericMethods.CheckIOSElementFound(nameAddAddressTxt, "nameAddAddressTxt");
		return nameAddAddressTxt;
	}

	public IOSElement getPermssionBtn() {
		objiOSGenericMethods.CheckIOSElementFound(permssionBtn, "permssionBtn");
		return permssionBtn;
	}

	public IOSElement getSaveAddressBtn() {
		objiOSGenericMethods.CheckIOSElementFound(saveAddressBtn, "saveAddressBtn");
		return saveAddressBtn;
	}

	public IOSElement getEditAddressEditTxt() {
		objiOSGenericMethods.CheckIOSElementFound(editAddressEditTxt, "editAddressEditTxt");
		return editAddressEditTxt;
	}

	public IOSElement getConfirmBtn() {
		objiOSGenericMethods.CheckIOSElementFound(confirmBtn, "Confirm Button");
		return confirmBtn;
	}

	/************* methods **********/

	public void clickOnEditAddress() {
		try {
			if (editAddressTxt.isDisplayed()) {
				editAddressTxt.click();
				System.out.println("User is has clicked on edit address!");
			}
		} catch (Exception e) {
			if (editAddressTxt2.isDisplayed()) {
				editAddressTxt2.click();
				System.out.println("User is has clicked on edit address!");
			} 
		}

	}

	public void clickOnEditButton() {
		if (getEditButton().isDisplayed()) {
			objiOSGenericMethods.clickOnIOSElement(getEditButton(), "edit button click succesfully");
		}
	}

	public void clickOnSaveAdressBtn() {
		objiOSGenericMethods.clickOnIOSElement(getSaveAddressBtn(), "saveAddress button click succesfully");

	}

	public void ClickOneditMoreAddress(int i) {
		List<IOSElement> remove = iDriver.findElementsByAccessibilityId("Edit/Change ('" + i + "')");
		System.out.println("Xpath --> " + remove);
		remove.get(i).click();
	}

	public void clickOnEditAddressEdit() {
		objiOSGenericMethods.clickOnIOSElement(getEditAddressEditTxt(), "editAddressEditTxt button click succesfully");
	}


	public IOSElement getRemoveAddressButton() {
		objiOSGenericMethods.CheckIOSElementFound(removeAddressButton, "editButton");
		return removeAddressButton;
	}
	
	/**
	 * @author 300019221 
	 * Replaced thread.sleep with webdriver wait
	 * @throws InterruptedException
	 */

	public void clickOnRemoveAddressBtn() throws InterruptedException {
		objiOSGenericMethods.waitForElementVisibility(removeAddressButton);
		try {
			if (removeAddressButton.isDisplayed()) {
				System.out.println("Remove button is displayed!");
				Reporter.log("Remove button is displayed!");
				objiOSGenericMethods.clickOnIOSElement(getRemoveAddressButton(), "Remove button click succesfully");
			}
		} catch (Exception e) {
			System.out.println("Remove button is not displayed!");
			Reporter.log("Remove button is not displayed!");
		}

	}
/**
 * @author 300019221 
 * Replaced thread.sleep with webdriver wait
 * @throws InterruptedException
 */
	public void clickOnConfirmBtn() throws InterruptedException {
		objiOSGenericMethods.waitForElementVisibility(confirmBtn);
		try {
			if (confirmBtn.isDisplayed()) {
				System.out.println("confirm button is displayed!");
				objiOSGenericMethods.clickOnIOSElement(getConfirmBtn(), "confirm button click succesfully");
			}
		} catch (Exception e) {
			System.out.println("confirm button is not displayed!");
			Reporter.log("confirm button is not displayed!");
		}

	}
}
