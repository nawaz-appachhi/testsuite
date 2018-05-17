/**
 * 
 * rahul
 */
package com.automation.core.Common;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;

import org.openqa.selenium.remote.DesiredCapabilities;

import com.automation.core.Common.GlobalVariables;
import com.automation.core.web.GenericMethods;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;

/**
 * @author 300020817
 *
 */
public class AppiumServer extends GlobalVariables {
	GenericMethods objGenerticMethods = new GenericMethods();
	public AndroidDriver<AndroidElement> aDriver;
	public IOSDriver<IOSElement> iDriver;
	private static AppiumDriverLocalService service;
	private AppiumServiceBuilder builder;
	private DesiredCapabilities cap;

	public void startServer() {
		// Set Capabilities
		cap = new DesiredCapabilities();
		cap.setCapability("noReset", "false");
		// Build the Appium service
		builder = new AppiumServiceBuilder();
		builder.withIPAddress("127.0.0.1");
		builder.usingPort(4723);
		builder.withCapabilities(cap);
		builder.withArgument(GeneralServerFlag.SESSION_OVERRIDE);
		builder.withArgument(GeneralServerFlag.LOG_LEVEL, "error");
		// Start the server with the builder
		service = AppiumDriverLocalService.buildService(builder);
		service.start();
		service = AppiumDriverLocalService
				.buildService(new AppiumServiceBuilder().usingDriverExecutable(new File("/usr/local/bin/node"))
						.withAppiumJS(new File("/usr/local/lib/node_modules/appium/bin/appium.js"))
						.withIPAddress("127.0.0.1").usingPort(4723));
	}

	public static AppiumDriverLocalService getService() {
		// Runtime.getRuntime().exec("")
		service = new AppiumServiceBuilder().usingPort(4723).withIPAddress("127.0.0.1")
				// .withAppiumJS(new File("/usr/local/lib/node_modules/appium/lib/appium.js"))
				// .usingDriverExecutable(new File("/usr/local/bin/node"))
				.withArgument(GeneralServerFlag.SESSION_OVERRIDE).build();
		return service;
	}

	public void stopServer() {
		service.stop();
	}

	public boolean checkIfServerIsRunnning(int port) {
		boolean isServerRunning = false;
		ServerSocket serverSocket;
		try {
			serverSocket = new ServerSocket(port);
			serverSocket.close();
		} catch (IOException e) {
			// If control comes here, then it means that the port is in use
			isServerRunning = true;
		} finally {
			serverSocket = null;
		}
		return isServerRunning;
	}
	// public IOSDriver<IOSElement> launchiOSApp(String mobilePlatform)
	// {
	// DesiredCapabilities caps = new DesiredCapabilities();
	// caps.setCapability("platformName", "iOS");
	// caps.setCapability("platformVersion", "11.2");
	// if(mobilePlatform.equalsIgnoreCase("app"))
	// {
	// caps.setCapability("deviceName", iOSDEVICE_NAME);
	// }else if(mobilePlatform.equalsIgnoreCase("mobileWeb"))
	// {
	// caps.setCapability("browser", iOSBrowser);
	// }else if(mobilePlatform.equalsIgnoreCase("pwa"))
	// {
	// caps.setCapability("browser", iOSBrowser);
	// }
	// //caps.setCapability("bundleid", "com.example.apple-samplecode.UICatalog");
	// caps.setCapability("app", APP_PATH);
	// try {
	// iDriver = new IOSDriver(new URL(HUB_URL), caps);
	// } catch (MalformedURLException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	//
	// return iDriver;
	// }
	// public AndroidDriver<AndroidElement> launchAndroidApp() {
	//
	// DesiredCapabilities cap = DesiredCapabilities.android();
	// // DesiredCapabilities cap = DesiredCapabilities();
	// cap.setCapability(MobileCapabilityType.DEVICE_NAME, DEVICE_NAME);
	// cap.setCapability(MobileCapabilityType.PLATFORM_NAME, PLATFORM);
	// cap.setCapability(MobileCapabilityType.PLATFORM_VERSION, DEVICE_VERSION);
	// cap.setCapability("appPackage", PACKAGE);
	// cap.setCapability("appActivity", ACTIVITY);
	// // cap.setCapability("avd", MyConstants.DEVICE_NAME);
	// try {
	// // test.log(LogStatus.INFO, "Launching the App");
	// aDriver = new AndroidDriver<AndroidElement>(new URL(HUB_URL), cap);
	// // aDriver = (AndroidDriver<AndroidElement>) driver;
	// } catch (Exception e) {
	//
	// e.printStackTrace();
	// }
	// return aDriver;
	// }
}
