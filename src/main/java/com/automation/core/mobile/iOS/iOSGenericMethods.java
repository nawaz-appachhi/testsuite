/**
 * 
 */
package com.automation.core.mobile.iOS;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

import static org.testng.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.maven.surefire.shade.org.apache.maven.shared.utils.io.FileUtils;
import org.ini4j.Ini;
import org.ini4j.InvalidFileFormatException;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import com.automation.core.Common.GlobalVariables;
import com.automation.core.Common.JavaUtils;
import com.google.common.base.Function;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.sun.org.apache.xerces.internal.dom.DeferredTextImpl;

/**
 * @author 300020817
 *
 */
public class iOSGenericMethods extends GlobalVariables {
	public ExtentTest test;
	public IOSDriver<IOSElement> iDriver;
	public JavaUtils objJavaUtils = new JavaUtils();
	int level = 0;
	Logger log = Logger.getLogger("devpinoyLogger");

	public iOSGenericMethods(IOSDriver<IOSElement> iDriver) {
		PageFactory.initElements(new AppiumFieldDecorator(iDriver), this);
		this.iDriver = iDriver;
	}

	public boolean isElementPresent(String locator) {
		test.log(LogStatus.INFO, "Finding presence of element " + locator);
		int s = iDriver.findElements(By.xpath(locator)).size();
		if (s == 0) {
			return false;
		}else {
			return true;
	}}

	public boolean verifyText(String locator, String expectedText) {
		return false;
	}

	/*****************************
	 * Reporting functions
	 *****************************************/
	public void reportPass(String passMsg) {
		test.log(LogStatus.PASS, passMsg);
	}

	public void reportFail(String failureMsg) {
		takeScreenshot();
		test.log(LogStatus.FAIL, failureMsg);
	}

	public void takeScreenshot() {
		Date d = new Date();
		String screenshotFile = d.toString().replace(":", "_").replace(" ", "_") + ".png";
		String path = SCREENSHOT_PATH + screenshotFile;
		File scrFile = ((TakesScreenshot) iDriver).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(scrFile, new File(path));
		} catch (IOException e) {
		}
		test.log(LogStatus.INFO, "Snapshot below: (" + screenshotFile + ")" + test.addScreenCapture(path));
	}

	

	
	public boolean isElementPresent_webelement(WebElement element) {
		try {
			element.isDisplayed();
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	public String datetime(String dateFormat) {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		return sdf.format(cal.getTime());
	}

	/**
	 * 
	 * This method takes the screenshot for WEB and store in screenshots folder with
	 * yyyyMMddHHmmss format
	 * 
	 * 
	 */
	public void takeSnapShot() {
		try {
			// Convert web driver object to TakeScreenshot
			TakesScreenshot scrShot = ((TakesScreenshot) iDriver);
			// Call getScreenshotAs method to create image file
			File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);
			// Move image file to new destination
			File DestFile = new File(
					// GlobalVariables.PROJECT_ROOT_FOLDER + "/src/test/resources/" +
					// datetime("yyyyMMddHHmmss") + ".jpg");
					GlobalVariables.SCREENSHOT_PATH + datetime("yyyyMMddHHmmss") + ".jpg");
			// Copy file at destination
			FileUtils.copyFile(SrcFile, DestFile);
		} catch (WebDriverException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @param elementName
	 *            String name of the WebElement
	 * 
	 *            This method will write into the Testng report whether object is
	 *            found or not. you can call in getter method of the object
	 * 
	 * 
	 */
	
	/*
	 * CheckIOSElementFound Modified by- Nitesh Commented assert statement for reset
	 * methods
	 */

	public void CheckIOSElementFound(IOSElement element, String elementName) {
		try {
			if (element.isDisplayed()) {
				Reporter.log("'" + elementName + "' Element object is Found");
			} else {
				Reporter.log("'" + elementName + "'  Element object is NOT Found");
			}
		} catch (NoSuchElementException e) {
			Reporter.log("No such element: Unable to locate element. Please check the Element");
		}
	}

	/**
	 * 
	 * @param element
	 *            The WebElement for which you wanted to check
	 * 
	 * @param elementName
	 *            String name of the WebElement
	 * 
	 *            This method will write into the Testng report whether object is
	 *            found or not. you can call in getter method of the object
	 * 
	 */
	public void CheckIOSElementsListFound(List<IOSElement> element, String elementName) {
		try {
			if (element.size() >= 0) {
				Reporter.log("'" + elementName + "' Element object is Found");
			} else {
				Reporter.log("'" + elementName + "'  Element object is NOT Found");
			}
		} catch (NoSuchElementException e) {
			Reporter.log("No such element: Unable to locate element. Please check the Element");
		}
	}

	public void VerifyTwoString(IOSElement element, String expectedString) {
		if (element.getText().equalsIgnoreCase(expectedString)) {
			Reporter.log("Passed :: '" + expectedString + "' is Matched");
		} else {
			Reporter.log("Failed :: '" + expectedString + "' is Not Matched :: Expected :'" + expectedString
					+ "' Actual Found :'" + element.getText() + "'");
		}
	}

	public void VerifyStringContains(IOSElement element, String expectedString) {
		if (element.getText().contains(expectedString)) {
			Reporter.log("Passed :: '" + expectedString + "' is Matched");
		} else {
			Reporter.log("Failed :: '" + expectedString + "' is Not Matched :: Expected :'" + expectedString
					+ "' Actual Found :'" + element.getText() + "'");
		}
	}

	/**
	 * 
	 * @param element
	 *            Pass the WebElement object on which user want to verify
	 * 
	 * @param str
	 *            Assertion string that user is expected
	 * 
	 *            This method verify that given object text is empty or not
	 * 
	 */
	public void VerifyStringShouldNotEmpty(IOSElement element, String str) {
		if (!element.getText().isEmpty()) {
			Reporter.log("Passed :: '" + element.getText() + "' Text is Displayed and Not Empty");
		} else {
			Reporter.log("Failed :: '" + str + "' Expected is NOT EMPTY text but found EMPTY");
		}
	}

	/**
	 * 
	 * @param elementName
	 *            Name of the WebElement Object
	 *
	 * 
	 *            This method will write the given text into log for Click event
	 * 
	 */
	public void ReportClickEvent(String elementName) {
		Reporter.log("'" + elementName + "' Element Object Clicked Succesfully.");
	}

	/**
	 * 
	 * @param elementName
	 *            Name of the SetDiscount Object
	 * 
	 * 
	 */
	public void setDiscount(String parameterName) throws IOException {
		log.info("writing the center name to a .ini file");
		Ini ini = new Ini(new File("./TestData/Mobile/Android/apps/test-data.ini"));
		ini.put("CenterName", "Center", parameterName);
		ini.store();
	}

	/**
	 * 
	 * @param elementName
	 *            Name of the getDiscount Object
	 * 
	 * 
	 */
	public String getDiscount() throws InvalidFileFormatException, IOException {
		log.info("reading the center name details from a .ini file");
		Ini ini = new Ini(new File("/TestData/Mobile/Android/apps/test-data.ini"));
		String centerName = ini.get("CenterName", "Center");
		System.err.println("CenterName-->" + centerName);
		return centerName;
	}

	public void scrollByDriver() {
		MobileElement slider = iDriver.findElement(MobileBy
				.IosUIAutomation(".tableViews()[0]" + ".scrollToElementWithPredicate(\"name CONTAINS 'Slider'\")"));
		assertEquals(slider.getAttribute("name"), "Sliders");
	}

	public void scrollByElement() {
		MobileElement table = iDriver.findElement(MobileBy.IosUIAutomation(".tableViews()[0]"));
		MobileElement slider = table
				.findElement(MobileBy.IosUIAutomation(".scrollToElementWithPredicate(\"name CONTAINS 'Slider'\")"));
		assertEquals(slider.getAttribute("name"), "Sliders");
	}

	public void swipe(IOSElement ele) {
		JavascriptExecutor js = (JavascriptExecutor) iDriver;
		Map<String, Object> params = new HashMap<>();
		params.put("direction", "down");
		params.put("element", ((RemoteWebElement) ele).getId());
		js.executeScript("mobile: swipe", params);
	}

	public void getDimention() {
		int h = iDriver.manage().window().getSize().height;
		int w = iDriver.manage().window().getSize().width;
		System.out.println("Hight of the screen --> " + h);
		System.out.println("Width of the screen -->" + w);
	}

	public void bottomTopSwipe(int duration) {
		Dimension size = iDriver.manage().window().getSize();
		System.out.println(size);
		int starty = (int) (size.height * 0.80);
		int endy = (int) (size.height * 0.50);
		int width = size.width / 2;
		new TouchAction(iDriver).press(width, starty).waitAction(Duration.ofMillis(duration)).moveTo(width, endy)
				.release().perform();
	}

	public void scroll(WebElement giftWrap) {
		JavascriptExecutor js = (JavascriptExecutor) iDriver;
		Map<String, Object> params = new HashMap<>();
		params.put("direction", "down");
		params.put("element", giftWrap);
		js.executeScript("mobile: swipe", params);
	}

	public void swipeDown(int duration, int noOfSwipes) {
		org.openqa.selenium.Dimension size = iDriver.manage().window().getSize();
		int starty = (int) (size.height * 0.85);
		int endy = (int) (size.height * 0.45);
		int width = size.width / 2;
		for (int i = 0; i < noOfSwipes; i++) {
			new TouchAction(iDriver).press(width, starty).waitAction(Duration.ofMillis(duration)).moveTo(width, endy)
					.release().perform();
		}
		System.out.println("Scroll down is done!");
	}

	public static void setValueByKeyAndTitel(String title, String Key, String parameterName) throws IOException {
		Ini ini = new Ini(new File(System.getProperty("user.dir") + "//TestData//Mobile//iOS//apps//test-data.ini"));
		ini.put(title, Key, parameterName);
		ini.store();
	}

	/**
	 * 
	 * @param elementName
	 *            Name of the getDiscount Object
	 *
	 * 
	 * 
	 */
	public static String getValueByKey(String gettitle, String getKey) throws InvalidFileFormatException, IOException {
		Ini ini = new Ini(new File(System.getProperty("user.dir") + "//TestData//Mobile//iOS//apps//test-data.ini"));
		String value = ini.get(gettitle, getKey);
		if (value == null) {
			System.err.println("Enter Proper Title or Key!");
		}
		System.out.println(getKey + "-->" + value);
		return value;
	}

	/**
	 * Addedby: Nitesh
	 * 
	 * added getValueByKeyiOSWeb method to access test data file for iOS mobile web
	 */

	public String getValueByKeyiOSWeb(String gettitle, String getKey) throws InvalidFileFormatException, IOException {
		Ini ini = new Ini(
				new File(System.getProperty("user.dir") + "//TestData//Mobile//iOS//MobileWeb//test-data.ini"));
		String value = ini.get(gettitle, getKey);
		if (value == null) {
			System.err.println("Enter Proper Title or Key!");
		}
		System.out.println(getKey + "-->" + value);
		return value;
	}

	/*
	 * Added By -Nitesh
	 * 
	 * getValueByKeyiOSPWA method to access test data for ios pwa
	 */

	public String getValueByKeyiOSPWA(String gettitle, String getKey) throws InvalidFileFormatException, IOException {
		Ini ini = new Ini(
				new File(System.getProperty("user.dir") + "//TestData//Mobile//iOS//MobileWeb//test-data.ini"));
		String value = ini.get(gettitle, getKey);
		if (value == null) {
			System.err.println("Enter Proper Title or Key!");
		}
		System.out.println(getKey + "-->" + value);
		return value;
	}

	public void SwipeUp(int duration) {
		Dimension size = iDriver.manage().window().getSize();
		System.out.println(size);
		int starty = (int) (size.height * 0.50);
		int endy = (int) (size.height * 0.80);
		int width = size.width / 2;
		new TouchAction(iDriver).press(width, endy).waitAction(Duration.ofMillis(duration)).moveTo(width, starty)
				.release().perform();
	}

	public void SwipeUp(int duration, int noOfSwipes) {
		Dimension size = iDriver.manage().window().getSize();
		System.out.println(size);
		int starty = (int) (size.height * 0.50);
		int endy = (int) (size.height * 0.80);
		int width = size.width / 2;
		for (int i = 0; i < noOfSwipes; i++) {
			new TouchAction(iDriver).press(width, starty).waitAction(Duration.ofMillis(duration)).moveTo(width, endy)
					.release().perform();
		}
	}

	/**
	 * @param elementName
	 * @param timeout
	 * @return This methods is used to check the element is present or not
	 */
	public boolean isElementPresent(IOSElement elementName, int timeout) {
		try {
			WebDriverWait wait = new WebDriverWait(iDriver, timeout);
			wait.until(ExpectedConditions.visibilityOf(elementName));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * This method is use to open Mobile web URL from Config.ini file
	 */
	public void openURL() {
		String Url = null;
		try {
			Url = objJavaUtils.getGValueByKey("Common-MobileWeb", "URL");
			iDriver.get(Url);
			System.out.println("URL: " + Url);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}

	public void clickUsingCoordiates(int x, int y) {
		TouchAction touch = new TouchAction(iDriver);
		// touch.press(x, y).perform();
		touch.tap(x, y).perform();

	}

	/**
	 * @author Aravindanath D.M
	 * @param waitTillElementLoads
	 * @param iDriver
	 */

	public void waitAndClickPermission(final String permssion, final IOSDriver iDriver) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(iDriver).withTimeout(20, TimeUnit.SECONDS)
				.pollingEvery(5, TimeUnit.SECONDS).ignoring(NoSuchElementException.class);
		WebElement foo = wait.until(new Function<WebDriver, WebElement>() {
			public IOSElement apply(WebDriver driver) {
				System.out.println("Pooling is in progress.. ");
				return (IOSElement) iDriver.findElement(By.xpath(permssion));
			}
		});
		Reporter.log("Element found user is attempting to click!");
		foo.click();
		Reporter.log("Element found user has clicked!");
	}

	/**
	 * 
	 * @param element
	 * @param reportContent
	 *            this methods used to click on ios element, for click on element
	 *            just call the method
	 */

	public void clickOnIOSElement(IOSElement element, String reportContent) {
		takeSnapShot();
		element.click();
		ReportClickEvent(reportContent);

	}

	/**
	 * @param element
	 *            Modified By: Neeraj Reason : Removed assert condition and changed
	 *            exception type to 'Exception' in catch section
	 */
	public void VerifyNumberShouldNotZeroOrLess(IOSElement element) {
		try {
			VerifyStringShouldNotEmpty(element, "Numbers");
			String actString = element.getText().replaceAll("[^0-9]", "");
			int num = Integer.parseInt(actString);
			if ((num > 0)) {
				Reporter.log("Passed :: '" + actString + "' digit is Displayed and '" + actString + "' > to 0");
			} else {
				Reporter.log("Failed :: '" + actString + "' digit is displayed but Found '" + actString + "' <= to 0");
				// Assert.fail("'" + actString + "' Expected is > 0 ");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Reporter.log(e.getMessage());
		}
	}

	/**
	 * @author 300019221 / Aravindanth switchToWebView method will switch to WebView
	 *         from Native
	 */
	public void switchToWebView(IOSDriver<IOSElement> iDriver) {
		Set<String> contextNames = iDriver.getContextHandles();
		for (String contextName : contextNames) {
			if (contextName.contains("WEBVIEW"))
				iDriver.context(contextName);
			System.out.println("Context --> " + iDriver.getContext());
		}
	}

	/**
	 * @author 300019221 / Aravindanth switchToWebView method will switch to Native
	 *         from WebView
	 */

	public void switchToNative(IOSDriver<IOSElement> iDriver) {
		Set<String> contextNames = iDriver.getContextHandles();
		for (String contextName : contextNames) {
			if (contextName.contains("NATIVE_APP"))
				iDriver.context(contextName);
			System.out.println("Context --> " + iDriver.getContext());

		}
	}

	/**
	 * @author 300019221 / Aravindanth ScrollToElement method will Scroll till the
	 *         element found
	 */

	public void ScrollToElement(IOSDriver<IOSElement> iDriver, By iosElement) throws InterruptedException {
		RemoteWebElement elements = (RemoteWebElement) iDriver.findElement((iosElement));
		JavascriptExecutor js = (JavascriptExecutor) iDriver;
		HashMap<String, String> scrollObject = new HashMap<String, String>();
		scrollObject.put("element", ((RemoteWebElement) elements).getId());
		scrollObject.put("toVisible", "true");
		js.executeScript("mobile: scroll", scrollObject);
//		Thread.sleep(5000);
	}

	/**
	 * @author 300019221 / Aravindanth TaptheElement method will click on the
	 *         element.
	 */

	public void TapTheElement(IOSDriver<IOSElement> iDriver, By element) throws InterruptedException {
		TouchAction finger = new TouchAction(iDriver);
		finger.tap(iDriver.findElement((element)));
		finger.perform().release();
//		Thread.sleep(5000);
	}

	/**
	 * @author 300019221 / Aravindanath FluentWait method only for " continue Button
	 *         " method it's not Generic.
	 * @param ele
	 */
	public void fluentWait(WebElement ele) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(iDriver).withTimeout(30, TimeUnit.SECONDS)
				.pollingEvery(10, TimeUnit.SECONDS).ignoring(NoSuchElementException.class);
		ele = wait.until(new Function<WebDriver, WebElement>() {
			public WebElement apply(WebDriver driver) {
				System.out.println("Fluent wait");
				return driver.findElement(By.cssSelector("#u_0_3"));

			}
		});
	}

	/**
	 * @author 300021275 - Lata This method will helps us to switch to a New
	 */

	public void switchToNewWindow() {
		log.info("switchToNewWindow method is in progress");
		Set<String> s = iDriver.getWindowHandles();
		Iterator<String> itr = s.iterator();
		itr.next();
		String w2 = (String) itr.next();
		iDriver.switchTo().window(w2);
		log.info("switchToNewWindow executed sucessfully");
	}

	/**
	 * @author 300021275 - Lata This method will switchTo Main window
	 */
	public void switchToMainWindow() throws InterruptedException {

		List<String> windows = new ArrayList<String>(iDriver.getWindowHandles());
		for (int i = 1; i < windows.size(); i++) {
			iDriver.switchTo().window(windows.get(i));
			iDriver.close();
		}
		iDriver.switchTo().window(windows.get(0));
	}

	/**
	 * @author 300019221 / Aravind This method can be used to click the elements.
	 */

	public void click(WebElement ele) {

		JavascriptExecutor executor = (JavascriptExecutor) iDriver;
		executor.executeScript("arguments[0].click();", ele);

	}
	public boolean waitDriver(IOSElement element, String strElement) {
		try {
			WebDriverWait wait = new WebDriverWait(iDriver, 20);
			wait.until(ExpectedConditions.visibilityOf(element));
			return true;
		} catch (Exception e) {
			Reporter.log("ELEMENT NOT FOUND :: Waited for 20 seconds to get the element '" + strElement + "'.");
			return false;
		}
	}

	/**
	 * @author 300019221 / Aravind This method can be used to wait till the element
	 *         visible.
	 */
	public void waitForElementVisibility(WebElement element) {
		WebDriverWait wait = new WebDriverWait(iDriver, 10);
		wait.until(ExpectedConditions.visibilityOf(element));
		assert element.isDisplayed();

	}

	/**
	 * @author 300019221 / Aravind This method can be used to wait till the element
	 *         locate.
	 */

	public void waitForElementLocate(WebElement element) {
		WebDriverWait wait = new WebDriverWait(iDriver, 10);
		wait.until(ExpectedConditions.invisibilityOfElementLocated((By) element));
		assert element.isDisplayed();

	}

	/**
	 * @author 300019221 / Aravind This method can be used to wait till the list of
	 *         elements visible. waitForElementsVisibility
	 */
	public void waitForElementsVisibility(String element) {
		WebDriverWait wait = new WebDriverWait(iDriver, 10);
		By inputArea = By.className(element);
		List<WebElement> titles = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(inputArea));
		int titleCount = titles.size();
		System.out.println("title count = " + titleCount);

	}

	/**
	 * @author 300019221 Aravindnath Alert method to handle the alert.
	 */

	public void acceptAlert() {

		try {
			Alert alert = iDriver.switchTo().alert();
			alert.accept();
			System.out.println("Alert accepted! :)");
		} catch (Exception e) {
			 try {
				IOSElement ele = iDriver.findElement(By.xpath("//XCUIElementTypeAlert//XCUIElementTypeButton[@name='Allow']"));
				 click(ele);
			} catch (Exception e1) {
				
			}
			 System.out.println("Alert not Found! :(");
		}

	}

	/**
	 * @author 300019221 Re use able method for handling ok popup
	 * 
	 */
	public void clickOkButton() {
		try {
			IOSElement tapAndHold = iDriver.findElement(By.xpath("//XCUIElementTypeButton[@name='OK']"));
			if (tapAndHold.isDisplayed()) {
				System.err.println("Tap and Hold button is displayed!");
				tapAndHold.click();
			}

		} catch (Exception e) {

			System.out.println("Tap and Hold button not found !");

		}
	}
	/**
	 * @author 300019221 Aravindanath
	 * This method will scroll till the element.
	 * @param element
	 * @param duration
	 */
	public void scrolltoElementAndClick(IOSElement element, int duration) {
		while (true) {
			Reporter.log("'" + element + "'  Looking for Element; Scroll in progress");
			bottomTopSwipe(duration);
			try {
				element.click();
				System.out.println("element clicked successfully");
				Thread.sleep(2000);
				break;
			} catch (Exception e) {
				System.out.println("Scrolled:But element not found");
				Reporter.log("No such element: Unable to locate element. Please check the Element");
			}
		}
	}
		/**
		 * @author 300019221 This method will scroll till the element is Displayed. 
		 * @param element
		 * @param duration
		 */
		public void scrolltoElement(IOSElement element, int duration) {
			while (true) {
				Reporter.log("'" + element + "'  Looking for Element; Scroll in progress");
				bottomTopSwipe(duration);
				break;
			}
		
		
	}
		public void getAlertText() {

			try {
				Alert alert = iDriver.switchTo().alert();
				System.err.println(alert.getText());
				System.out.println("Alert accepted! :)");
			} catch (Exception e) {
				 
				 System.out.println("Alert not Found! :(");
			}

		}
	
	
}
