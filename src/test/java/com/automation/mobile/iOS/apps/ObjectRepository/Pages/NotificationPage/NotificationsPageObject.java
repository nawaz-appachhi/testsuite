package com.automation.mobile.iOS.apps.ObjectRepository.Pages.NotificationPage;


import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.automation.core.mobile.iOS.iOSGenericMethods;

import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class NotificationsPageObject {
	
	iOSGenericMethods objiOSGenericMethods;
	public NotificationsPageObject(IOSDriver<IOSElement> iDriver) {
		PageFactory.initElements(new AppiumFieldDecorator(iDriver), this);
		objiOSGenericMethods = new iOSGenericMethods(iDriver);
	}
	
	
	@FindBy(xpath="//XCUIElementTypeButton[@name=\"Notifications\"]")
	public IOSElement notifications;
	
	@FindBy(xpath = "(//XCUIElementTypeOther[@x =\"40\" and @y=\"171\"])[2]")
	public IOSElement notification;
	
	@FindBy(xpath = "//XCUIElementTypeOther[3]/XCUIElementTypeOther[2]/XCUIElementTypeOther")
	public IOSElement allowNotification;
	
	/*************getters************/
	
	public IOSElement getNotifications() {
		objiOSGenericMethods.CheckIOSElementFound(notifications, "notifications");
		return notifications;
	}

	public IOSElement getNotification() {
		objiOSGenericMethods.CheckIOSElementFound(notification, "notification");
		return notification;
	}

	public IOSElement getAllowNotification() {
		objiOSGenericMethods.CheckIOSElementFound(allowNotification, "allowNotification");
		return allowNotification;
	}
	
	/**************8methods***********/
	
	public void clickOnNotifications() {
		objiOSGenericMethods.clickOnIOSElement(getNotifications(), "Successfully click on Notifications button");
	}
	public void clickOnNotification() {
		objiOSGenericMethods.clickOnIOSElement(getNotification(), "Successfully click on Notification button");
		
	}
	public void clickOnnAllowNotification() {
		objiOSGenericMethods.clickOnIOSElement(getAllowNotification(), "Successfully click on allowNotification button");
		
	}
	
	

}
