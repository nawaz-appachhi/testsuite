package com.automation.mobile.iOS.MobileWeb.ObjectRepository.AddressPageObjects;

import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.automation.core.mobile.iOS.iOSGenericMethods;

import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class Edit_ChangeButtonPageObjects {

	public IOSDriver<IOSElement> iDriver;
	iOSGenericMethods objiOSGenericMethods;

	public Edit_ChangeButtonPageObjects(IOSDriver<IOSElement> iDriver) {
		PageFactory.initElements(new AppiumFieldDecorator(iDriver), this);
		objiOSGenericMethods = new iOSGenericMethods(iDriver);

	}

	/*
	 * Edit/Change Button
	 */
	@FindBy(xpath = "(//div[@class=\"row white-row change-address new-ui\"])")
	public IOSElement EditChangeButton;

	@FindBy(xpath = "(//button[@class='tappable edit'])")
	public IOSElement EDITButton;
	
	/**
	 * Remove Button xpath
	 * Added by subahsis
	 */
	
	@FindBy(xpath="//button[@class='tappable delete' and text()='Remove']")
	public IOSElement removeButton;

	public IOSElement getRemoveButton() {
		objiOSGenericMethods.CheckIOSElementFound(removeButton, "Remove Button");
		return removeButton;
	}

	public IOSElement getEditChangeButton() {
		objiOSGenericMethods.CheckIOSElementFound(EditChangeButton, "EditChangeButton");
		return EditChangeButton;
	}

	public IOSElement getEDITButtton() {
		objiOSGenericMethods.CheckIOSElementFound(EDITButton, "EDITButton");
		return EDITButton;
	}

	public void clickOnEditChangeButton() {
		objiOSGenericMethods.clickOnIOSElement(getEditChangeButton(), "Edit Change Button");
	}

	public void clickOnEDITButton() {
		objiOSGenericMethods.clickOnIOSElement(getEDITButtton(), "EDIT Buttton");
	}
	
	public void clickOnRemoveButton() {
		objiOSGenericMethods.clickOnIOSElement(getRemoveButton(), "Remove Button");
	}

}