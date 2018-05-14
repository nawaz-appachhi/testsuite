/**

 * 

 */
package com.automation.core.Common;

import java.io.File;
import java.net.MalformedURLException;

import java.net.URL;
import java.util.Map;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.automation.core.mobile.iOS.iOSGenericMethods;
import com.relevantcodes.extentreports.ExtentReports;

import com.relevantcodes.extentreports.ExtentTest;

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
	public ExtentReports rep = ExtentManager.getInstance();
	public IOSDriver<IOSElement> iDriver;
	public AndroidDriver<AndroidElement> aDriver;
	public ExtentTest test;
	String xcode = "Configuration/Config.xcconfig";
	iOSGenericMethods objiOSGenericMethods;

	public IOSDriver<IOSElement> launchAppiOS(Map<String, String> params) throws InterruptedException {

		DesiredCapabilities cap;
		System.out.println("======================== configuration parameter values ==================");
		System.out.println("Param Value: " + params);
		System.out.println("GlobalVariables.ANDROID: " + GlobalVariables.ANDROID);
		System.out.println("GlobalVariables.iOS: " + GlobalVariables.iOS);
		System.out.println("GlobalVariables.iOS_MWEB: " + GlobalVariables.iOS_MWEB);
		System.out.println("GlobalVariables.ANDROID_MWEB: " + GlobalVariables.ANDROID_MWEB);
		if ((GlobalVariables.iOS).equalsIgnoreCase("true")) {

			cap = DesiredCapabilities.iphone();
			// cap.setCapability(MobileCapabilityType.DEVICE_NAME,
			// GlobalVariables.iOSDEVICE_NAME); // Commented By Neeraj
			cap.setCapability(MobileCapabilityType.DEVICE_NAME, params.get("deviceName_"));
			cap.setCapability(MobileCapabilityType.PLATFORM_NAME, GlobalVariables.iOSPLATFORM); // Commented By Neeraj
			// cap.setCapability(MobileCapabilityType.PLATFORM_VERSION,
			// GlobalVariables.iOSDEVICE_VERSION); // Commented By Neeraj
			cap.setCapability(MobileCapabilityType.PLATFORM_VERSION, params.get("platformVersion_"));
			// cap.setCapability("waitForAppScript", "$.delay(1000);");
			cap.setCapability(IOSMobileCapabilityType.AUTO_ACCEPT_ALERTS, true);
			cap.setCapability(IOSMobileCapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, true);
			if (!params.get("screenshotPath_").equalsIgnoreCase("")) {
				GlobalVariables.SCREENSHOT_PATH = params.get("screenshotPath_");
			}
			cap.setCapability("xcodeConfigFile", xcode);
			// String ipa="/Users/300019221/Documents/IOSfiles/Myntra.ipa"; // Commented By
			// neeraj
			// File app=new File(ipa); // Commented By neeraj
			File app = new File(params.get("appUrl_"));
			cap.setCapability("app", app.getAbsolutePath());
			// cap.setCapability("udid", GlobalVariables.UDID); // Commented By Neeraj
			cap.setCapability("udid", params.get("UDID_"));
			cap.setCapability("autoAcceptAlerts", true);
			cap.setCapability(MobileCapabilityType.AUTOMATION_NAME, "XCUITest");
			// cap.setCapability(MobileCapabilityType.FULL_RESET, true);
			cap.setCapability("newCommandTimeout", 120);

			cap.setCapability(MobileCapabilityType.NO_RESET, true);

			cap.setCapability(IOSMobileCapabilityType.AUTO_ACCEPT_ALERTS, true);
			try {
				// test.log(LogStatus.INFO, "Launching the App");
				// iDriver = new IOSDriver<IOSElement>(new URL(GlobalVariables.HUB_URL), cap);
				// // Commented By Neeraj
				iDriver = new IOSDriver<IOSElement>(new URL(params.get("URL_")), cap);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if ((GlobalVariables.iOS_MWEB).equalsIgnoreCase("true")) {

			if (params.get("browserName_") == "safari") {
				cap = DesiredCapabilities.safari();
			} else if (params.get("browserName_") == "firefox") {
				cap = DesiredCapabilities.firefox();
			} else if (params.get("browserName_") == "chrome") {
				cap = DesiredCapabilities.chrome();
			} else {
				cap = DesiredCapabilities.safari();
			}

			if (!params.get("screenshotPath_").equalsIgnoreCase("")) {
				GlobalVariables.SCREENSHOT_PATH = params.get("screenshotPath_");

			}

			// cap.setCapability(MobileCapabilityType.DEVICE_NAME,
			// GlobalVariables.iOSDEVICE_NAME);
			cap.setCapability(MobileCapabilityType.DEVICE_NAME, params.get("deviceName_"));
			cap.setCapability(MobileCapabilityType.PLATFORM_NAME, GlobalVariables.iOSPLATFORM);
			// cap.setCapability(MobileCapabilityType.PLATFORM_VERSION,
			// GlobalVariables.iOSDEVICE_VERSION);
			cap.setCapability(MobileCapabilityType.PLATFORM_VERSION, params.get("platformVersion_"));
			cap.setCapability(MobileCapabilityType.BROWSER_NAME, params.get("browserName_"));
			// cap.setCapability("app", GlobalVariables.APP_PATH);
			cap.setCapability(MobileCapabilityType.AUTOMATION_NAME, "XCUITest");
			cap.setCapability("xcodeConfigFile", xcode);
			cap.setCapability("nativeWebTap", true);// This is for Site Window Pop-up
			// cap.setCapability("safariIgnoreFraudWarning",true);
			// cap.setCapability("appPackage", GlobalVariables.PACKAGE);
			// cap.setCapability("appActivity", GlobalVariables.ACTIVITY);
			cap.setCapability("udid", params.get("UDID_"));
			try {
				// test.log(LogStatus.INFO, "Launching the App");
				iDriver = new IOSDriver<IOSElement>(new URL(params.get("URL_")), cap);
				iDriver.get(params.get("appUrl_"));
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if ((GlobalVariables.iOS_PWA).equalsIgnoreCase("true")) {
			cap = DesiredCapabilities.safari();
			cap.setCapability(MobileCapabilityType.DEVICE_NAME, GlobalVariables.iOSDEVICE_NAME);
			cap.setCapability(MobileCapabilityType.PLATFORM_NAME, GlobalVariables.iOSPLATFORM);
			cap.setCapability(MobileCapabilityType.PLATFORM_VERSION, GlobalVariables.iOSDEVICE_VERSION);
			cap.setCapability("app", GlobalVariables.APP_PATH);
			cap.setCapability(MobileCapabilityType.AUTOMATION_NAME, "XCUITest");
			// cap.setCapability("appPackage", GlobalVariables.PACKAGE);
			// cap.setCapability("appActivity", GlobalVariables.ACTIVITY);
			cap.setCapability("udid", GlobalVariables.UDID);
			try {
				// test.log(LogStatus.INFO, "Launching the App");
				iDriver = new IOSDriver<IOSElement>(new URL(GlobalVariables.HUB_URL), cap);
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

	public AndroidDriver<AndroidElement> launchAppAndroid(Map<String, String> params) {
		DesiredCapabilities cap;
		System.out.println("======================== configuration parameter values ==================");
		System.out.println("Param Value: " + params);
		System.out.println("GlobalVariables.ANDROID: " + GlobalVariables.ANDROID);
		System.out.println("GlobalVariables.iOS: " + GlobalVariables.iOS);
		System.out.println("GlobalVariables.iOS_MWEB: " + GlobalVariables.iOS_MWEB);
		System.out.println("GlobalVariables.ANDROID_MWEB: " + GlobalVariables.ANDROID_MWEB);

		if ((GlobalVariables.ANDROID).equalsIgnoreCase("true")) {
			cap = DesiredCapabilities.android();
			// cap.setCapability(MobileCapabilityType.DEVICE_NAME,
			// GlobalVariables.aDEVICE_NAME); // Commented By neeraj
			cap.setCapability(MobileCapabilityType.DEVICE_NAME, params.get("deviceName_"));
			cap.setCapability("udid", params.get("UDID_"));
			cap.setCapability(MobileCapabilityType.PLATFORM_NAME, GlobalVariables.aPLATFORM);
			// cap.setCapability(MobileCapabilityType.PLATFORM_VERSION,
			// GlobalVariables.aDEVICE_VERSION); // Commented By neeraj

			if (!params.get("screenshotPath_").equalsIgnoreCase("")) {
				GlobalVariables.SCREENSHOT_PATH = params.get("screenshotPath_");
				System.out.println("Screen shot path is --->");
			}

			cap.setCapability(MobileCapabilityType.PLATFORM_VERSION, params.get("platformVersion_"));
			cap.setCapability("appPackage", GlobalVariables.aPACKAGE);
			cap.setCapability("appActivity", GlobalVariables.aACTIVITY);
			File app = new File(params.get("appUrl_"));
			cap.setCapability("app", app.getAbsolutePath());
			// cap.setCapability("app", GlobalVariables.aAPK_PATH); // Commented By neeraj
	//		cap.setCapability(AndroidMobileCapabilityType.CHROMEDRIVER_EXECUTABLE,GlobalVariables.aChromePath);
			cap.setCapability("automationName", "uiautomator2");
		//	cap.setCapability(MobileCapabilityType.NO_RESET, true);
		//	cap.setCapability(MobileCapabilityType.FULL_RESET, true);
			// cap.setCapability("autoGrantPermissions","true");
			// cap.setCapability(AndroidMobileCapabilityType.UNICODE_KEYBOARD, true);
			try {
				// test.log(LogStatus.INFO, "Launching the App");
				// aDriver = new AndroidDriver<AndroidElement>(new URL(GlobalVariables.HUB_URL),
				// cap); // Commented By neeraj
				aDriver = new AndroidDriver<AndroidElement>(new URL(params.get("URL_")), cap);

			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if ((GlobalVariables.ANDROID_PWA).equalsIgnoreCase("true")) {

			// device details
			cap = new DesiredCapabilities();
			cap.setCapability("deviceName", "ENU7N15B10001097");
			cap.setCapability("platformName", "Android");
			cap.setCapability("platformVersion", "8.0.0");
			// app details
			cap.setCapability("appPackage", "org.chromium.webapk.ae78ef491371263f6");
			cap.setCapability("appActivity", "org.chromium.webapk.shell_apk.MainActivity");
			// Appium server details
			// AndroidDriver driver = new AndroidDriver(new
			// URL("http://0.0.0.0:4723/wd/hub"), capabilities);
			try {
				// test.log(LogStatus.INFO, "Launching the App");
				aDriver = new AndroidDriver<AndroidElement>(new URL(GlobalVariables.HUB_URL), cap);
				// aDriver = (AndroidDriver<AndroidElement>) driver;
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if ((GlobalVariables.ANDROID_MWEB).equalsIgnoreCase("true")) {

			if (params.get("browserName_") == "safari") {
				cap = DesiredCapabilities.safari();
			} else if (params.get("browserName_") == "firefox") {
				cap = DesiredCapabilities.firefox();
			} else if (params.get("browserName_") == "chrome") {
				cap = DesiredCapabilities.chrome();

				ChromeOptions options = new ChromeOptions();
				// options.setExperimentalOption("prefs", prefs);
				options.addArguments("disable-notifications");
				cap.setCapability(ChromeOptions.CAPABILITY, options);
			} else {
				cap = DesiredCapabilities.safari();
			}
			if (!params.get("screenshotPath_").equalsIgnoreCase("")) {
				GlobalVariables.SCREENSHOT_PATH = params.get("screenshotPath_");
			}

			cap.setCapability(MobileCapabilityType.DEVICE_NAME, params.get("deviceName_"));
			// cap.setCapability(MobileCapabilityType.DEVICE_NAME,
			// GlobalVariables.amDEVICE_NAME);
			cap.setCapability(MobileCapabilityType.PLATFORM_NAME, GlobalVariables.amPLATFORM);
			cap.setCapability(MobileCapabilityType.PLATFORM_VERSION, params.get("platformVersion_"));
			cap.setCapability(MobileCapabilityType.BROWSER_NAME, params.get("browserName_"));
			// cap.setCapability(MobileCapabilityType.PLATFORM_VERSION,
			// GlobalVariables.amDEVICE_VERSION);
			// cap.setCapability("appPackage", GlobalVariables.amPACKAGE);
			// cap.setCapability("appActivity", GlobalVariables.amACTIVITY);
			// cap.setCapability("autoAcceptAlerts", true);
			cap.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, "UnexpectedAlertBehaviour.ACCEPT");
			cap.setCapability("autoDismissAlerts", true);
			// Map<String, Object> prefs = new HashMap<String, Object>();
			// prefs.put("profile.default_content_setting_values.notifications", 2);
			// cap.setCapability(AndroidMobileCapabilityType.AUTO_GRANT_PERMISSIONS, true);
			cap.setCapability("udid", params.get("UDID_"));
			cap.setCapability("unicodeKeyboard", true);
			cap.setCapability("resetKeyboard", true);

			try {
				// test.log(LogStatus.INFO, "Launching the App");
				aDriver = new AndroidDriver<AndroidElement>(new URL(params.get("URL_")), cap);
				aDriver.get(params.get("appUrl_"));
				// aDriver = (AndroidDriver<AndroidElement>) driver;
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
