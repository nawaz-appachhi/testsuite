package com.automation.core.web;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.Reporter;

import com.automation.core.Common.GlobalVariables;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author neerajkumar.b.lad
 *
 */

public class DriverInit extends GlobalVariables{

	public WebDriver driver;
	GenericMethods objGenerticMethods = new GenericMethods();
	
	public static final String USERNAME = "neerajkumar22";
	public static final String AUTOMATE_KEY = "dQhCMapyDN2HX8pTCdsN";
	public static final String URL = "https://" + USERNAME + ":" + AUTOMATE_KEY + "@hub-cloud.browserstack.com/wd/hub";
	
	public WebDriver getWebDriver(String Browser,String Location)
	{
		if(objGenerticMethods.getPlatform().equalsIgnoreCase("OS_LINUX"))
		{
			driver = getWebDriverLinux(Browser,Location);
		}else if(objGenerticMethods.getPlatform().equalsIgnoreCase("OS_WINDOWS"))
		{
			driver = getWebDriverWin(Browser,Location);
			
		}else if (objGenerticMethods.getPlatform().equalsIgnoreCase("OS_SOLARIS"))
		{
			driver = getWebDriverSolaris(Browser,Location);
		}else if(objGenerticMethods.getPlatform().equalsIgnoreCase("OS_MAC"))
		{
			driver = getWebDriverMac(Browser,Location);
		}
		else if(objGenerticMethods.getPlatform().equalsIgnoreCase("Unknown")){
			Reporter.log("Unknown OS Detected");
		}
		return driver;
	}
	
	public WebDriver getWebDriverLinux(String Browser,String Location)
	{
		return null;
	}
	
	public WebDriver getWebDriverSolaris(String Browser,String Location)
	{
		return null;
	}
	
	public WebDriver getWebDriverWin(String Browser,String Location) {

		// Created object of DesiredCapabilities
		DesiredCapabilities caps = new DesiredCapabilities();
		
		if(Location.equalsIgnoreCase("Cloud"))
		{
			if(Browser.equalsIgnoreCase("IE")){
				caps.setCapability("name", "Selenium Test Example");
				caps.setCapability("build", "1.0");
				caps.setCapability("browser_api_name", "Edge20");
				caps.setCapability("os_api_name", "Win10");
				caps.setCapability("screen_resolution", "1024x768");
				caps.setCapability("selenium_version", "2.53.1");
				
			}else if(Browser.equalsIgnoreCase("firefox")){
				caps.setCapability("name", "Selenium Test Example");
				caps.setCapability("build", "1.0");
				caps.setCapability("browser_api_name", "Edge20");
				caps.setCapability("os_api_name", "Win10");
				caps.setCapability("screen_resolution", "1024x768");
				caps.setCapability("selenium_version", "2.53.1");

			}else if(Browser.equalsIgnoreCase("chrome")){
				caps.setCapability("name", "Selenium Test Example");
				caps.setCapability("build", "1.0");
				caps.setCapability("browser_api_name", "Edge20");
				caps.setCapability("os_api_name", "Win10");
				caps.setCapability("screen_resolution", "1024x768");
				caps.setCapability("selenium_version", "2.53.1");
			}
			
			try {
				driver = new RemoteWebDriver(new URL(URL), caps);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				Reporter.log("Remote session failed to create");
				e.printStackTrace();
			}

		}
		else if(Location.equalsIgnoreCase("Local"))
		{
			if(Browser.equalsIgnoreCase("IE"))
			{
				try {
					System.setProperty("webdriver.ie.driver", PROJECT_ROOT_FOLDER + "/driver/Windows/IEDriverServer.exe");
					DesiredCapabilities ieCapabilities = DesiredCapabilities.internetExplorer();
					ieCapabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
					driver = new InternetExplorerDriver(ieCapabilities);
				} catch (Exception e) {
					System.out.println("IE Browser not initited");
					e.printStackTrace();
				}
			}
			
			if(Browser.equalsIgnoreCase("Edge"))
			{
				try {
					try {
						Runtime.getRuntime().exec("taskkill /im MicrosoftEdge.exe /t /f");
						Reporter.log("Passed : Edge browser process killed successfully!");
					} catch (Exception e) {
						Reporter.log("Passed : No edge browser process running in the background!");
					}
					
					System.setProperty("webdriver.edge.driver", PROJECT_ROOT_FOLDER + "/driver/Windows/MicrosoftWebDriver.exe");
					DesiredCapabilities capabilities = DesiredCapabilities.edge();
				    driver = new EdgeDriver(capabilities);
				} catch (Exception e) {
					System.out.println("Microsoft Edge Browser not initited");
					e.printStackTrace();
				}
			}
			
			else if(Browser.equalsIgnoreCase("firefox")){
				try {
					FirefoxProfile Profile = new FirefoxProfile();
					Profile.setPreference( "browser.helperApps.neverAsk.saveToDisk", "application/xml" );
					System.setProperty("webdriver.gecko.driver", PROJECT_ROOT_FOLDER + "/driver/Windows/geckodriver.exe");
					driver = new FirefoxDriver();
				} catch (Exception e) {
					System.out.println("Firefox Browser not initited");
					e.printStackTrace();
				}
			}
			
			else if(Browser.equalsIgnoreCase("chrome")){
				ChromeOptions options = new ChromeOptions();
				options.addArguments("--disable-notifications");
				options.addArguments("--disable-infobars");
			    DesiredCapabilities capabilities = DesiredCapabilities.chrome();
			    capabilities.setCapability(ChromeOptions.CAPABILITY, options);
			    capabilities.setCapability("pageLoadStrategy", "none");
				try {
					System.setProperty("webdriver.chrome.driver", PROJECT_ROOT_FOLDER + "/driver/Windows/chromedriver.exe");
					driver = new ChromeDriver(capabilities);
				} catch (Exception e) {
					System.out.println("Chrome Browser not initited");
					e.printStackTrace();
				}
			}
		}
		return driver;
	}
	
	/**
	 * @param Browser
	 * @param Location
	 * @return
	 * Modified By:Neeraj
	 * Reason: Corrected browser capability for cloud section
	 * Reason: Added chrome capabilities for pageLoadStrategy = none. Which will not wail for page to load completely.
	 */
	public WebDriver getWebDriverMac(String Browser,String Location) {
		// Created object of DesiredCapabilities
				DesiredCapabilities caps = new DesiredCapabilities();
				
				if(Location.equalsIgnoreCase("Cloud"))
				{
					if(Browser.equalsIgnoreCase("IE")){
						caps.setCapability("name", "IE Test");
						caps.setCapability("os", "Windows");
						caps.setCapability("os_version", "10");
						caps.setCapability("browser", "IE");
						caps.setCapability("browser_version", "11.0");
						caps.setCapability("browserstack.selenium_version", "3.5.2");
						
					}else if(Browser.equalsIgnoreCase("firefox")){
						caps.setCapability("name", "FireFox Test");
						caps.setCapability("os", "Windows");
						caps.setCapability("os_version", "10");
						caps.setCapability("browser", "Firefox");
						caps.setCapability("browser_version", "58.0 beta");
						caps.setCapability("browserstack.selenium_version", "3.5.2");
						
					}else if(Browser.equalsIgnoreCase("chrome")){
						caps.setCapability("name", "Chrome Test");
						caps.setCapability("os", "Windows");
						caps.setCapability("os_version", "10");
						caps.setCapability("browser", "Chrome");
						caps.setCapability("browser_version", "63.0");
						caps.setCapability("browserstack.selenium_version", "3.5.2");
						
					}else if(Browser.equalsIgnoreCase("Safari")){
						caps.setCapability("name", "Safari Test");
						caps.setCapability("os", "OS X");
						caps.setCapability("os_version", "Sierra");
						caps.setCapability("browser", "Safari");
						caps.setCapability("browser_version", "10.0");
						caps.setCapability("browserstack.selenium_version", "3.5.2");
					}
					
					try {
						driver = new RemoteWebDriver(new URL(URL), caps);
					} catch (MalformedURLException e) {
						// TODO Auto-generated catch block
						Reporter.log("Remote session failed to create");
						e.printStackTrace();
					}
				}
				
				else if(Location.equalsIgnoreCase("Local"))
				{
				if(Browser.equalsIgnoreCase("Safari"))
				{
					try {
						driver = new SafariDriver();
					} catch (Exception e) {
						System.out.println("Safari Browser not initited");
						e.printStackTrace();
					}
				}
				
				else if(Browser.equalsIgnoreCase("chrome")){
					ChromeOptions options = new ChromeOptions();
					options.addArguments("--disable-notifications");
					options.addArguments("--disable-infobars");
				    DesiredCapabilities capabilities = DesiredCapabilities.chrome();
				    capabilities.setCapability(ChromeOptions.CAPABILITY, options);
				    capabilities.setCapability("pageLoadStrategy", "none");
					try {
						System.setProperty("webdriver.chrome.driver", PROJECT_ROOT_FOLDER + "/driver/mac/chromedriver");
						driver = new ChromeDriver(capabilities);
					//	driver.manage().window().maximize();
					} catch (Exception e) {
						System.out.println("Chrome Browser not initited");
						e.printStackTrace();
					}
				}
				
				else if(Browser.equalsIgnoreCase("firefox")){
					try {
						FirefoxProfile Profile = new FirefoxProfile();
						Profile.setPreference("permissions.default.desktop-notification", 1);
						Profile.setPreference( "browser.helperApps.neverAsk.saveToDisk", "application/xml" );
						driver = new FirefoxDriver();
					} catch (Exception e) {
						System.out.println("Firefox Browser not initited");
						e.printStackTrace();
					}
				}
				
				else if(Browser.equalsIgnoreCase("Edge")){
					try {
						try {
							Runtime.getRuntime().exec("taskkill /im MicrosoftEdge.exe /t /f");
							Reporter.log("Passed : Edge browser process killed successfully!");
						} catch (Exception e) {
							Reporter.log("Passed : No edge browser process running in the background!");
						}
						
						System.setProperty("webdriver.edge.driver", PROJECT_ROOT_FOLDER + "driver/MicrosoftWebDriver.exe");
						driver = new EdgeDriver();
					} catch (Exception e) {
						System.out.println("Microsoft Edge Browser not initited");
						e.printStackTrace();
					}
				}
			}
		return driver;
	}
}
