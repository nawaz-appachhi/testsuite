package com.automation.mobile.Android.MobileWeb.ObjectRepository.AddressPageObjects;

import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.automation.core.mobile.Android.AndroidGenericMethods;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class Edit_ChangeButtonPageObjects {

	AndroidGenericMethods objAndroidGenericMethods;
	public Edit_ChangeButtonPageObjects(AppiumDriver<MobileElement> aDriver) {
		PageFactory.initElements(new AppiumFieldDecorator(aDriver), this);
		objAndroidGenericMethods = new AndroidGenericMethods(aDriver);
	}

	/*
	 * Edit/Change Button
	 * modified by aishurya
	 */
	@FindBy(xpath = "//span[contains(text(),'Edit/Change')]")
	public AndroidElement EditChangeButton;

	@FindBy(xpath = "(//button[@class='tappable edit'])")
	public AndroidElement EDITButton;
	@FindBy(xpath = "(//button[@class='tappable delete'])")
	public AndroidElement removeButton;
	
	@FindBy(xpath="(//button[@class='close close-address-list clickable' and text()='Confirm']")
	public AndroidElement ConfirmButton;

	public AndroidElement getConfirmButton() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(ConfirmButton, "ConfirmButton");
		return ConfirmButton;
	}

	public AndroidElement getEditChangeButton() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(EditChangeButton, "EditChangeButton");
		return EditChangeButton;
	}

	public AndroidElement getremoveButton() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(removeButton, "removeButton");
		return removeButton;
	}
	public AndroidElement getEDITButtton() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(EDITButton, "EDITButton");
		return EDITButton;
	}
	
	/**
	 * Modified By:Aishurya:Changed string parameter,which is being used in reporing
	 */
	public void clickOnEditChangeButton() {
		objAndroidGenericMethods.clickOnAndroidElement(getEditChangeButton(), "EditChangeButton");
	}
	
	/**
	 * Modified By:Aishurya:Changed string parameter,which is being used in reporing
	 */
	public void clickOnConfirmButton() {
		objAndroidGenericMethods.clickOnAndroidElement(getConfirmButton(), "ConfirmTButtton");
	}
	
	/**
	 * Modified By:Aishurya:Changed string parameter,which is being used in reporing
	 */
	public void clickOnEDITButton() {
		objAndroidGenericMethods.clickOnAndroidElement(getEDITButtton(), "EDITButtton");
	}
	/**
	 * Modified by:Anu
	 * Description : Changed the return type of method to 'Void'
	 */
	public void clickOnremoveButton() {
		objAndroidGenericMethods.clickOnAndroidElement(getremoveButton(), "removeButton");
	}

}