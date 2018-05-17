package com.automation.core.Common;

import org.openqa.selenium.WebDriver;

/**
 * 
 * @author 300020817
 * 
 */
public class GlobalVariables {
	/**
	 * 
	 * // Android App parameters
	 * 
	 */
	public static String PROJECT_ROOT_FOLDER = System.getProperty("user.dir");
	public long SMALLSLEEP = 2000;
	public long MIDSLEEP = 4000;
	public long LONGSLEEP = 8000;
	/**
	 * Added by: Aishurya: for screenshotListener class
	 */
	public static WebDriver Gdriver;
	//// Reports
	// public static final String REPORT ="";
	/**
	 * 
	 * // Android App parameters
	 * 
	 */
	public static final String aAPK_PATH = System.getProperty("user.dir")
			+ "/apps/Android/apps/Stage/Myntra-Android-debug.apk";
	public static final String aDEVICE_NAME = "192.168.56.101:5555"; // emulator-5556 Nexus 5 // emulator-5556
	public static final String aDEVICE_VERSION = "6.0";
	public static final String aPLATFORM = "Android";
	public static final String aPACKAGE = "com.myntra.android";
	public static final String aACTIVITY = "com.myntra.android.retention.activities.D7OnBoardingActivity";
	public static final String aChromePath = System.getProperty("user.dir") + "/driver/AndroidApp/chromedriver";

	/*
	 * com.myntra.android.activities.LoginRegisterActivity --> Login activity
	 */
	public static final String pAPK_PATH = System.getProperty("user.dir") + "/apps/Android/PWA/Stage/Myntra_3.21.0.apk";
	public static final String pDEVICE_NAME = "372fd32a"; // emulator-5556 Nexus 5 // emulator-5556
	public static final String pDEVICE_VERSION = "8.0";
	public static final String pPLATFORM = "Android";
	public static final String pPACKAGE = "com.myntra.android";
	public static final String pACTIVITY = "com.myntra.android.activities.HomeActivity";
	public static final String amAPK_PATH = System.getProperty("user.dir")
			+ "/apps/Android/MobileWeb/Stage/Myntra_3.21.0.apk";
	public static final String amDEVICE_NAME = "372fd32a"; // emulator-5556 Nexus 5 // emulator-5556
	public static final String amDEVICE_VERSION = "8.0";
	public static final String amPLATFORM = "Android";
	public static final String amPACKAGE = "org.chromium.webapk.ae78ef491371263f6";
	public static final String amACTIVITY = "org.chromium.webapk.shell_apk.MainActivity";
	/*
	 * IOS App parameters
	 */
	public static final String APP_PATH = System.getProperty("user.dir") + "/apps/iOS/apps/Myntra.app";
	public static final String UDID = "C3C360FE-D19C-4B58-A474-26FACEE892";
	public static final String iOSDEVICE_VERSION = "11.2";

	public static final String iOSPLATFORM = "iOS";
	public static final String iOSDEVICE_NAME = "iPhone 7";
	

	public static final String HUB_URL = "http://127.0.0.1:4723/wd/hub";
	public static final String REPORT_PATH = System.getProperty("user.dir") + "//Reports//";
	public static final String XLS_PATH = System.getProperty("user.dir") + "//TestData//Mobile//TestData.xlsx";
	public static String SCREENSHOT_PATH = System.getProperty("user.dir") + "//screenshots//";
	
	
	//============= Which platform are you going to run=========================
	public static final String ANDROID = "false";
	public static final String iOS = "true";
	public static final String ANDROID_PWA = "false";
	public static final String ANDROID_MWEB = "false";
	public static final String iOS_PWA = "false";
	public static final String iOS_MWEB = "false";
}