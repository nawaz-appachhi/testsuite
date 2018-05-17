package com.automation.core.mobile.Android;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.ini4j.Ini;
import org.ini4j.InvalidFileFormatException;
import org.ini4j.Profile.Section;
import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class MobileNumberVerification {

	public AndroidDriver<AndroidElement> aDriver;

	public MobileNumberVerification(AndroidDriver<AndroidElement> aDriver) {
		PageFactory.initElements(new AppiumFieldDecorator(aDriver), this);
		this.aDriver = aDriver;
	}

	/**
	 * Method to get the mobile number from ini file;
	 * 
	 * @author 300021278 -Rakesh
	 * @param gettitle
	 * @param getKey
	 * @return
	 * @throws InvalidFileFormatException
	 * @throws IOException
	 */
	public static String getValueByKey(String gettitle, String getKey) throws InvalidFileFormatException, IOException {
		Ini ini = new Ini(
				new File(System.getProperty("user.dir") + "//TestData//mobile//Android//apps//mobileNumbers.ini"));
		String value = ini.get(gettitle, getKey);
		if (value == null) {
			System.err.println("Enter Proper Title or Key!");
		}
		System.out.println(getKey + "-->" + value);
		return value;
	}
	/**
	 * Method to enter valid number into the device; Gets the udid from the capabilities and matches according to it;
	 * @return
	 * @throws InvalidFileFormatException
	 * @throws IOException
	 */
	public String mobileNumberVerification() throws InvalidFileFormatException, IOException {
		String MobileNumber = null;
		ArrayList<String> sims = new ArrayList<String>();
		String DeviceUDID = aDriver.getCapabilities().getCapability("udid").toString();
		System.out.println(DeviceUDID);
		for (int i = 1; i <= 25; i++) {
			String Value = getValueByKey(("Device" + i), "udid");
			String DeviceType = ("Device" + i);
			sims.add(Value);
			if (Value == null) {
				break;
			} else if (Value.contains(DeviceUDID)) {
				MobileNumber = getValueByKey(DeviceType, "MobileNumber");
				return MobileNumber;
			}
		}
		System.out.println(sims.size());
		return MobileNumber;

	}

	/*
	 * public static void main(String[] args) throws InvalidFileFormatException,
	 * IOException { MobileNumberVerification obj = new
	 * MobileNumberVerification(aDriver); obj.count();
	 * 
	 * }
	 */
}
