/**

 * 

 */
package com.automation.core.Common;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import java.net.URL;
import java.util.Map;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.automation.core.mobile.Android.AndroidGenericMethods;
import com.automation.core.mobile.iOS.iOSGenericMethods;
import com.relevantcodes.extentreports.ExtentReports;

import com.relevantcodes.extentreports.ExtentTest;

import freemarker.template.ObjectWrapperAndUnwrapper;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

import io.appium.java_client.android.AndroidElement;

import io.appium.java_client.ios.IOSDriver;

import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;

/**
 * 
 * @author 300019221
 *
 * 
 * 
 */
public class MobileDrivers {
	// public ExtentReports rep = ExtentManager.getInstance();
	public AppiumDriver<MobileElement> iDriver;
	public AppiumDriver<MobileElement> aDriver;
	public ExtentTest test;
	String xcode = "Configuration/Config.xcconfig";
	iOSGenericMethods objiOSGenericMethods;
	AndroidGenericMethods objAndroidGenericMethods;

	public AppiumDriver<MobileElement> launchAppiOS(Map<String, String> params) throws InterruptedException {
		objiOSGenericMethods = new iOSGenericMethods();
		DesiredCapabilities cap;
		System.out.println("======================== configuration parameter values ==================");
		System.out.println("Param Value: " + params);
		if(params.get("platform_").equalsIgnoreCase("iOS")) {
			cap = DesiredCapabilities.iphone();
			cap.setCapability(MobileCapabilityType.DEVICE_NAME, params.get("deviceName_"));
			cap.setCapability(MobileCapabilityType.PLATFORM_NAME, GlobalVariables.iOSPLATFORM); 
			cap.setCapability(MobileCapabilityType.PLATFORM_VERSION, params.get("platformVersion_"));
			cap.setCapability(IOSMobileCapabilityType.AUTO_ACCEPT_ALERTS, true);
			cap.setCapability(IOSMobileCapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, true);
			if ( params.get("engine_").equalsIgnoreCase("TD") ) {
				GlobalVariables.SCREENSHOT_PATH = System.getProperty("user.dir") + "/screenshots/" +  objiOSGenericMethods.datetime("yyyyMMddHHmmss") + ".jpg";
			} else if (!params.get("screenshotPath_").equalsIgnoreCase("")) {
				GlobalVariables.SCREENSHOT_PATH = params.get("screenshotPath_");
			}
			cap.setCapability("xcodeConfigFile", xcode);
			File app = new File(params.get("appUrl_"));
			cap.setCapability("app", app.getAbsolutePath());
			cap.setCapability("udid", params.get("UDID_"));
			cap.setCapability("autoAcceptAlerts", true);
			cap.setCapability(MobileCapabilityType.AUTOMATION_NAME, "XCUITest");
			cap.setCapability("newCommandTimeout", 120);
			cap.setCapability(MobileCapabilityType.NO_RESET, true);
			cap.setCapability(IOSMobileCapabilityType.AUTO_ACCEPT_ALERTS, true);
			try {
				iDriver = new AppiumDriver<MobileElement>(new URL(params.get("URL_")), cap);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if(params.get("platform_").equalsIgnoreCase("iOS_mWeb")) {
			if (params.get("browserName_") == "safari") {
				cap = DesiredCapabilities.safari();
			} else if (params.get("browserName_") == "firefox") {
				cap = DesiredCapabilities.firefox();
			} else if (params.get("browserName_") == "chrome") {
				cap = DesiredCapabilities.chrome();
			} else {
				cap = DesiredCapabilities.safari();
			}
			if ( params.get("engine_").equalsIgnoreCase("TD") ) {
				GlobalVariables.SCREENSHOT_PATH = System.getProperty("user.dir") + "/screenshots/" +  objiOSGenericMethods.datetime("yyyyMMddHHmmss") + ".jpg";
			} else if (!params.get("screenshotPath_").equalsIgnoreCase("")) {
				GlobalVariables.SCREENSHOT_PATH = params.get("screenshotPath_");
			}
			cap.setCapability(MobileCapabilityType.DEVICE_NAME, params.get("deviceName_"));
			cap.setCapability(MobileCapabilityType.PLATFORM_NAME, GlobalVariables.iOSPLATFORM);
			cap.setCapability(MobileCapabilityType.PLATFORM_VERSION, params.get("platformVersion_"));
			cap.setCapability(MobileCapabilityType.BROWSER_NAME, params.get("browserName_"));
			cap.setCapability(MobileCapabilityType.AUTOMATION_NAME, "XCUITest");
			cap.setCapability("xcodeConfigFile", xcode);
			cap.setCapability("nativeWebTap", true);// This is for Site Window Pop-up
			cap.setCapability("udid", params.get("UDID_"));
			try {
				iDriver = new AppiumDriver<MobileElement>(new URL(params.get("URL_")), cap);
				iDriver.get(params.get("appUrl_"));
			} catch (Exception e) {
				e.printStackTrace();
			}
		} 
		if (iDriver == null) {
			System.out.println("Driver is null please verify global variable");
			System.out.println("======================== configuration parameter values end ==================");
		}
		return iDriver;
	}

	public AppiumDriver<MobileElement> launchAppAndroid(Map<String, String> params) {
		objAndroidGenericMethods = new AndroidGenericMethods();
		DesiredCapabilities cap;
		System.out.println("======================== configuration parameter values ==================");
		System.out.println("Param Value: " + params);

		
		if(params.get("platform_").equalsIgnoreCase("ANDROID")) {
			cap = DesiredCapabilities.android();
			cap.setCapability(MobileCapabilityType.DEVICE_NAME, params.get("deviceName_"));
			cap.setCapability("udid", params.get("UDID_"));
			cap.setCapability(MobileCapabilityType.PLATFORM_NAME, GlobalVariables.aPLATFORM);
			if ( params.get("engine_").equalsIgnoreCase("TD") ) {
				GlobalVariables.SCREENSHOT_PATH = System.getProperty("user.dir") + "/screenshots/" +  objAndroidGenericMethods.datetime("yyyyMMddHHmmss") + ".jpg";
			} else if (!params.get("screenshotPath_").equalsIgnoreCase("")) {
				GlobalVariables.SCREENSHOT_PATH = params.get("screenshotPath_");
				System.out.println("Screen shot path is --->");
			}
			cap.setCapability(MobileCapabilityType.PLATFORM_VERSION, params.get("platformVersion_"));
			cap.setCapability("appPackage", GlobalVariables.aPACKAGE);
			cap.setCapability("appActivity", GlobalVariables.aACTIVITY);
			File app = new File(params.get("appUrl_"));
			cap.setCapability("app", app.getAbsolutePath());
			cap.setCapability("automationName", "uiautomator2");
			try {
				aDriver = new AppiumDriver<MobileElement>(new URL(params.get("URL_")), cap);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}  else if(params.get("platform_").equalsIgnoreCase("ANDROID_mWeb")) {
			if (params.get("browserName_") == "safari") {
				cap = DesiredCapabilities.safari();
			} else if (params.get("browserName_") == "firefox") {
				cap = DesiredCapabilities.firefox();
			} else if (params.get("browserName_") == "chrome") {
				cap = DesiredCapabilities.chrome();
				ChromeOptions options = new ChromeOptions();
				options.addArguments("disable-notifications");
				cap.setCapability(ChromeOptions.CAPABILITY, options);
			} else {
				cap = DesiredCapabilities.safari();
			}
			if ( params.get("engine_").equalsIgnoreCase("TD") ) {
				GlobalVariables.SCREENSHOT_PATH = System.getProperty("user.dir") + "/screenshots/" +  objAndroidGenericMethods.datetime("yyyyMMddHHmmss") + ".jpg";
			} else if (!params.get("screenshotPath_").equalsIgnoreCase("")) {
				GlobalVariables.SCREENSHOT_PATH = params.get("screenshotPath_");
			}
			cap.setCapability(MobileCapabilityType.DEVICE_NAME, params.get("deviceName_"));
			cap.setCapability(MobileCapabilityType.PLATFORM_NAME, GlobalVariables.amPLATFORM);
			cap.setCapability(MobileCapabilityType.PLATFORM_VERSION, params.get("platformVersion_"));
			cap.setCapability(MobileCapabilityType.BROWSER_NAME, params.get("browserName_"));
			cap.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, "UnexpectedAlertBehaviour.ACCEPT");
			cap.setCapability("autoDismissAlerts", true);
			cap.setCapability("udid", params.get("UDID_"));
			cap.setCapability("unicodeKeyboard", true);
			cap.setCapability("resetKeyboard", true);
			try {
				aDriver = new AppiumDriver<MobileElement>(new URL(params.get("URL_")), cap);
				aDriver.get(params.get("appUrl_"));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (aDriver == null) {
			System.out.println("Driver is null please verify global variable");
			System.out.println("======================== configuration parameter values end ==================");
		}
		return aDriver;
	}
}
