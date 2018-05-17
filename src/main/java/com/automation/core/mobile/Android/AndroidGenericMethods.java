/**
 * 
 */
package com.automation.core.mobile.Android;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.FindsByAndroidUIAutomator;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.PressesKeyCode;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.AndroidKeyCode;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.maven.surefire.shade.org.apache.maven.shared.utils.io.FileUtils;
import org.ini4j.Ini;
import org.ini4j.InvalidFileFormatException;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import com.automation.core.Common.GlobalVariables;
import com.automation.core.Common.JavaUtils;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.sun.org.apache.xerces.internal.dom.DeferredTextImpl;

import freemarker.template.ObjectWrapperAndUnwrapper;

/**
 * @author 300019221
 *
 */
/**
 * @author 300020817
 *
 */
/**
 * @author 300020817
 *
 */
public class AndroidGenericMethods extends GlobalVariables {
	public AppiumDriver<MobileElement> aDriver;

	public JavaUtils objJavaUtils = new JavaUtils();
	public ExtentTest test;
	int level = 0;
	static Logger log = Logger.getLogger("devpinoyLogger");
	public AndroidGenericMethods(AppiumDriver<MobileElement> aDriver) {
		PageFactory.initElements(new AppiumFieldDecorator(aDriver), this);
		this.aDriver = aDriver;
	}

	public AndroidGenericMethods() {
		super();
		// TODO Auto-generated constructor stub
	}

	public boolean isElementPresent(String locator) {
		test.log(LogStatus.INFO, "Finding presence of element " + locator);
		int s = aDriver.findElements(By.xpath(locator)).size();
		if (s == 0)
			return false;
		else
			return true;
	}

	public boolean verifyText(String locator, String expectedText) {
		return false;
	}

	public void compareText(String firstValue, String secondValue) {

		if (secondValue.equals(firstValue)) {
			Reporter.log("Failed :: '" + "' both the values are same but not NOT EMPTY");
			// Assert.fail("'" + "' both the values are same but not NOT EMPTY");
		} else {
			Reporter.log("Passed :: '" + "' Change in object value and is Not Empty");

		}
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
		Assert.fail(failureMsg);
	}

	public void takeScreenshot() {
		// decide the file name
		Date d = new Date();
		String screenshotFile = d.toString().replace(":", "_").replace(" ", "_") + ".png";
		String path = SCREENSHOT_PATH + screenshotFile;
		// take screenshot
		File scrFile = ((TakesScreenshot) aDriver).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(scrFile, new File(path));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// add screenshot to report
		test.log(LogStatus.INFO, "Snapshot below: (" + screenshotFile + ")" + test.addScreenCapture(path));
	}

	public Node initXML() {
		// aDriver.findElement(By.xpath("//android.widget.ImageButton[@content-desc='Open
		// Drawer']").click();
		File fXmlFile = new File(System.getProperty("user.dir") + "//menu.xml");
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = null;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Document doc = null;
		try {
			doc = dBuilder.parse(fXmlFile);
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		doc.getDocumentElement().normalize();
		Node baseNode = doc.getChildNodes().item(0);
		return baseNode;
	}

	public void verifyChildValues(Node currentNode) {
		if (!(currentNode instanceof DeferredTextImpl)) {
			Element e = (Element) currentNode;
			if (!e.getAttribute("id").equals("")) { // expanded/clicked
				if (!e.getAttribute("id").equals("mainmenu")) {
					boolean res = verifyElement(e.getAttribute("id"));
					System.out.println(
							"Level - " + level + ".Verifying Node " + e.getAttribute("id") + " -result -> " + res);
					if (!res)
						reportFail("Item not found in menu - " + e.getAttribute("id"));
					aDriver.findElement(By.xpath("//android.widget.TextView[@text='" + e.getAttribute("id") + "']"))
					.click();
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					level++;
				}
				Node child = currentNode.getFirstChild();
				while (child != null) {
					verifyChildValues(child);
					child = child.getNextSibling();
				}
				System.out.println();
				level--;
				if (level == 0) {
					aDriver.findElement(By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']")).click();
					aDriver.findElement(By.xpath("//android.widget.ImageButton[@content-desc='Open Drawer']")).click();
				}
			} else {// value is present
				boolean res = verifyElement(e.getTextContent());
				System.out.println("Level - " + level + ". Verifying " + e.getTextContent() + " -result -> " + res);
				if (!res)
					reportFail("Item not found in menu - " + e.getTextContent());
				currentNode = currentNode.getNextSibling();
				verifyChildValues(currentNode);
			}
		}
	}

	public boolean verifyElement(String nextElement) {
		System.out.println("Verifying - " + nextElement);
		List<MobileElement> e = null;
		if (level == 0) {
			e = ((FindsByAndroidUIAutomator<MobileElement>) aDriver).findElementsByAndroidUIAutomator(
					"UiSelector().resourceId(\"com.myntra.android:id/flyout_parent_title\")");
		} else {
			e = ((FindsByAndroidUIAutomator<MobileElement>) aDriver).findElementsByAndroidUIAutomator("UiSelector().resourceId(\"com.myntra.android:id/title\")");
		}
		boolean scroll = true;
		String lastBeforeScroll = "x";
		String lastAfterScroll = "y";
		while (scroll) {
			// finding element
			for (MobileElement a : e) {
				if (a.getText().equals(nextElement)) {
					return true;
				}
			}
			if (lastAfterScroll.equals(lastBeforeScroll)) { // end of the list
				System.out.println("Not found");
				return false; // not found
			}
			int h = aDriver.manage().window().getSize().height;
			lastBeforeScroll = e.get(e.size() - 1).getText();
			// aDriver.swipe(300, h-100, 300, h/2, 4000);
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if (level == 0) {
				e = ((FindsByAndroidUIAutomator<MobileElement>) aDriver).findElementsByAndroidUIAutomator(
						"UiSelector().resourceId(\"com.flipkart.android:id/flyout_parent_title\")");
			} else {
				e = ((FindsByAndroidUIAutomator<MobileElement>) aDriver)
						.findElementsByAndroidUIAutomator("UiSelector().resourceId(\"com.flipkart.android:id/title\")");
			}
			lastAfterScroll = e.get(e.size() - 1).getText();
		}
		// System.out.println("------**----------------------------");
		for (MobileElement a : e) {
			// System.out.println(a.getText());
			if (a.getText().equals(nextElement)) {
				return true;
			}
		}
		return false;
	}

	public void failThisStep(String reasonForFailing) {
		Assert.fail(reasonForFailing);
	}

	public boolean isElementPresent_webelement(AndroidElement element) {
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
	 * Aravindanath
	 * 
	 */
	public void takeSnapShot() {
		try {
			JavaUtils objJavaUtils = new JavaUtils();
			
			
			// Convert web driver object to TakeScreenshot
			TakesScreenshot scrShot = ((TakesScreenshot) aDriver);
			// Call getScreenshotAs method to create image file
			File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);
			File DestFile = null;
			// Move image file to new destination
//			if(isTestDroid.equalsIgnoreCase("true"))
//			{
//				DestFile = new File(
//						System.getProperty("user.dir") + "/screenshots/" + datetime("yyyyMMddHHmmss") + ".jpg");
//			}
//			else
//			{
				DestFile = new File(
						GlobalVariables.SCREENSHOT_PATH + datetime("yyyyMMddHHmmss") + ".jpg");
//			}

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
	 * This method will write into the Testng report whether object is found or not.
	 * you can call in getter method of the object
	 * 
	 * @param element
	 *            The WebElement for which you wanted to check
	 * @param elementName
	 *            String name of the WebElement
	 * @author 300019221 - Aravindanath
	 * @Date 10th Jan 2018
	 */
	public void CheckAndroidElementFound(WebElement element, String elementName) {
		if(!aDriver.getContext().contains("NATIVE_APP"))
		{
			switchInToNativeView();	
		}
		try { 
			if (waitDriver(element, elementName)) {
				if (element.isDisplayed()) {
					Reporter.log("'" + elementName + "' Element object is Displayed");
				} else {
					Reporter.log(
							"'" + elementName + "'  Element NOT Displayed on page. Please check the Object Property.");
				}
			}
		} catch (Exception e) {
			Reporter.log("FAILED TO PERFORM ACTION ON " + elementName + " even after waiting for 20 seconds");
		}
	}
	
	public void CheckAndroidElementFoundForWebView(WebElement element, String elementName) {
		if(!aDriver.getContext().contains("WEBVIEW"))
		{
			swithchInToWebview();	
		}
		try {
			if (waitDriver(element, elementName)) {
				if (element.isDisplayed()) {
					Reporter.log("'" + elementName + "' Element object is Displayed");
				} else {
					Reporter.log(
							"'" + elementName + "'  Element NOT Displayed on page. Please check the Object Property.");
				}
			}
		} catch (Exception e) {
			Reporter.log("FAILED TO PERFORM ACTION ON " + elementName + " even after waiting for 20 seconds");
		}

	}
	
	/**
	 * Method to check andriod element for mobile web;
	 * @author 300019227 Anu
	 * @param element
	 * @param elementName
	 */
	public void CheckAndroidElementFoundMWeb(WebElement element, String elementName) {
		try { 
			if (waitDriver(element, elementName)) {
				if (element.isDisplayed()) {
					Reporter.log("'" + elementName + "' Element object is Displayed");
				} else {
					Reporter.log(
							"'" + elementName + "'  Element NOT Displayed on page. Please check the Object Property.");
				}
			}
		} catch (Exception e) {
			Reporter.log("FAILED TO PERFORM ACTION ON " + elementName + " even after waiting for 20 seconds");
		}
	}
	/**
	 * Method to check android element for list condition for mobile web';
	 * @author 300019227 Anu
	 * @param element
	 * @param elementName
	 */
	public void CheckAndroidElementsListFoundMWeb(List<AndroidElement> element, String elementName) {
		try {
			if (element.size() >= 0) {
				Reporter.log("Passed :: '" + elementName + "' Element object is Found");
			} else {
				Reporter.log("'" + elementName + "'  Element object is NOT Found");
				Assert.fail("Element NOT Found. Please check the Object Property.");
			}
		} catch (NoSuchElementException e) {
			Reporter.log("No such element: Unable to locate element. Please check the Element");
			Assert.fail(e.getMessage());
		}
	}

	/**
	 * This method will write into the Testng report whether object is found or not.
	 * you can call in getter method of the object
	 * 
	 * @param element
	 *            The WebElement for which you wanted to check
	 * @param elementName
	 *            String name of the WebElement
	 * @author 300019221 - Aravindanath
	 * @Date 10th Jan 2018
	 */
	public void CheckAndroidElementsListFound(List<AndroidElement> element, String elementName) {
		try {
			if (element.size() >= 0) {
				Reporter.log("Passed :: '" + elementName + "' Element object is Found");
			} else {
				Reporter.log("'" + elementName + "'  Element object is NOT Found");
				Assert.fail("Element NOT Found. Please check the Object Property.");
			}
		} catch (NoSuchElementException e) {
			Reporter.log("No such element: Unable to locate element. Please check the Element");
			Assert.fail(e.getMessage());
		}
	}
	
	
	public void CheckAndroidElementsListFoundforWebView(List<AndroidElement> element, String elementName) {
		if(!aDriver.getContext().contains("WEBVIEW"))
		{
			swithchInToWebview();	
		}
		try {
			if (element.size() >= 0) {
				Reporter.log("Passed :: '" + elementName + "' Element object is Found");
			} else {
				Reporter.log("'" + elementName + "'  Element object is NOT Found");
				Assert.fail("Element NOT Found. Please check the Object Property.");
			}
		} catch (NoSuchElementException e) {
			Reporter.log("No such element: Unable to locate element. Please check the Element");
			Assert.fail(e.getMessage());
		}
	}

	/**
	 * Modified By Aishurya: Commented Assert.fail()
	 */
	public void VerifyTwoString(AndroidElement element, String expectedString) {
		if (element.getText().equalsIgnoreCase(expectedString)) {
			Reporter.log("Passed :: '" + expectedString + "' is Matched");
		} else {
			Reporter.log("Failed :: '" + expectedString + "' is Not Matched :: Expected :'" + expectedString
					+ "' Actual Found :'" + element.getText() + "'");
//			Assert.fail("Expected and Actual is not Matched :: Expected :'" + expectedString + "' Actual Found :'"
//					+ element.getText() + "'");
		}
	}

	/**
	 * This method verify that given object text is empty or not
	 * 
	 * @param element
	 *            Pass the WebElement object on which user want to verify
	 * @param str
	 *            Assertion string that user is expected
	 * @author 300019221 - Aravindanath
	 */
	public void VerifyStringShouldNotEmpty(AndroidElement element, String str) {
		if (!element.getText().isEmpty()) {
			Reporter.log("Passed :: '" + element.getText() + "' Text is Displayed and Not Empty");
		} else {
			Reporter.log("Failed :: '" + str + "' Expected is NOT EMPTY text but found EMPTY");
			Assert.fail("'" + str + "' Expected is NOT EMPTY text but found EMPTY");
		}
	}

	/**
	 * This method will write the given text into log for Click event
	 * 
	 * @param elementName
	 *            Name of the WebElement Object
	 * @author 300020817 - Neeraj
	 */
	public void ReportClickEvent(String elementName) {
		Reporter.log("'" + elementName + "' Element Object Clicked Succesfully.");
	}
	
	/**
	 * @author 300019224-Aishurya
	 * Message for sendKeys action
	 */
	public void ReportEnterEvent(String elementName) {
		Reporter.log("'" + elementName + "' Entered Succesfully.");
	}

	/**
	 * @author 300019221
	 * 
	 */
	public void getDimention() {
		int h = aDriver.manage().window().getSize().height;
		int w = aDriver.manage().window().getSize().width;
		System.out.println("Hight of the screen --> " + h);
		System.out.println("Width of the screen -->" + w);
	}

	/**
	 * Method to set data for ini file
	 * 
	 * @param elementName
	 * @author 300019221 - Aravindanath
	 * 
	 */
	public static void setValueByKeyAndTitel(String title, String Key, String parameterName) throws IOException {
		Ini ini = new Ini(
				new File(System.getProperty("user.dir") + "//TestData//mobile//Android//apps//test-data.ini"));
		ini.put(title, Key, parameterName);
		ini.store();
	}

	/**
	 * Method to get data from the ini file
	 * 
	 * @param elementName
	 * @author 300019221 - Aravindanath
	 */
	public static String getValueByKey(String gettitle, String getKey) throws InvalidFileFormatException, IOException {
		Ini ini = new Ini(
				new File(System.getProperty("user.dir") + "//TestData//mobile//Android//apps//test-data.ini"));
		String value = ini.get(gettitle, getKey);
		if (value == null) {
			System.err.println("Enter Proper Title or Key!");
		}
		System.out.println(getKey + "-->" + value);
		return value;
	}

	/**
	 * Method to get data from the ini file
	 * 
	 * @param elementName
	 * @author 300019221 - Nitesh
	 */
	public String getValueByKeyAndroidPWA(String gettitle, String getKey)
			throws InvalidFileFormatException, IOException {
		Ini ini = new Ini(new File(System.getProperty("user.dir") + "//TestData//mobile//Android//PWA//test-data.ini"));
		String value = ini.get(gettitle, getKey);
		if (value == null) {
			System.err.println("Enter Proper Title or Key!");
		}
		System.out.println(getKey + "-->" + value);
		return value;
	}

	/**
	 * scroll Method, User need to pass how much duration to be scrolled
	 * 
	 * @author 300021278-Rakesh
	 * @param duration
	 */
	public void bottomTopSwipe(int duration) {
		Dimension size = aDriver.manage().window().getSize();
		System.out.println("Dimensions of the screen" + size);
		int starty = (int) (size.height * 0.50);
		int endy = (int) (size.height * 0.20);
		int width = size.width / 2;
		new TouchAction(aDriver).press(width, starty).waitAction(Duration.ofMillis(duration)).moveTo(width, endy)
		.release().perform();
	}
	/**
	 * Method to scroll up, User need to pass how much duration to be scrolled
	 * 
	 * @author 300021278-Rakesh
	 * @param duration
	 */
	public void topBottomSwipe(int duration) {
		Dimension size = aDriver.manage().window().getSize();
		System.out.println("Dimensions of the screen" + size);
		int starty = (int) (size.height * 0.20);
		int endy = (int) (size.height * 0.50);
		int width = size.width / 2;
		new TouchAction(aDriver).press(width, starty).waitAction(Duration.ofMillis(duration)).moveTo(width, endy)
		.release().perform();
	}

	/**
	 * scroll Method, User need to pass element and how much duration to be scrolled
	 * 
	 * @author 300021278-Rakesh
	 * @param duration
	 */
	public void scrolltoElementAndClick(AndroidElement element, int duration) {
		while (true) {
			Reporter.log("'" + element + "'  Looking for Element; Scroll in progress");
			bottomTopSwipe(duration);
			try {
				element.click();
				System.out.println("element clicked successfully");
				Thread.sleep(2000);
				break;
			} catch (Exception e) {
				// e.printStackTrace();
				System.out.println("Scrolled:But element not found");
				Reporter.log("No such element: Unable to locate element. Please check the Element");
			}
		}
 
	}
	
	
	public void scrollByTextandClick(String ClassName, String ChildClassName, String Text) {
		MobileElement element = aDriver.findElement(MobileBy.AndroidUIAutomator(
				"new UiScrollable(new UiSelector().className(\" "+ ClassName +" \")).getChildByText("
				+ "new UiSelector().className(\""+ChildClassName+"\"), \""+Text+"\")"));
		element.click();
	}
	
	/**Method to scroll the element to the centre of the screen.
	 * @author 300021278 -Rakesh
	 */
	public void scrolltoCentre(AndroidElement element) {
		try {
			for (int i=0; i<=15;i++) {
				Reporter.log("'" + element + "'  Looking for Element; Scroll in progress");
				bottomTopSwipe(4000);
			if (element.isDisplayed()) {
				Dimension size = aDriver.manage().window().getSize();
				System.out.println("Dimensions of the screen" + size);
				 Point location = element.getLocation();
				System.out.println("Location of the element : "+location);
				int starty = (int) (location.getY()* 1);
				int width = size.width / 2;
				int endy = (int) (size.height * 0.50);
				new TouchAction(aDriver).press(width, starty).waitAction(Duration.ofMillis(4000)).moveTo(width, endy)
				.release().perform();
				System.out.println("element is at the centre");
				break;
			}
			else  {
			}}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("In cath failed scroll to centre");
		}
		
	}
	
	/**
	 * Method to scroll to the element and click;User need to pass element and how much duration to be scrolled and
	 *  Direction needs to provided whether up, down;
	 * @param Direction
	 * @param element
	 * @param duration
	 */
	public void scrolltoElementAndClick(String Direction, AndroidElement element, int duration) {
		if (Direction.contains("down")) {
		for (int i=0; i<=15;i++) {
			if (element.isDisplayed()) {
				element.click();
				System.out.println("element clicked successfully");
				break;
			}
			else {
				Reporter.log("'" + element + "'  Looking for Element; Scroll in progress");
				bottomTopSwipe(duration);
				System.out.println("element search is in progress:: Scrolling");
			}
		}
		}
		else if(Direction.contains("up")) {
			for (int i=0; i<=15;i++) {
				Reporter.log("'" + element + "'  Looking for Element; Scroll in progress");
				topBottomSwipe(duration);
				if (element.isDisplayed()) {
					element.click();
					System.out.println("element clicked successfully");
					break;
				}
				else {
					System.out.println("element search is in progress:: Scrolling");
				}
			}
			}
		}
	

	public void swipeDown(int duration, int noOfSwipes) {
		org.openqa.selenium.Dimension size = aDriver.manage().window().getSize();
		int starty = (int) (size.height * 0.85);
		int endy = (int) (size.height * 0.45);
		int width = size.width / 2;
		for (int i = 0; i < noOfSwipes; i++) {
			new TouchAction(aDriver).press(width, starty).waitAction(Duration.ofMillis(duration)).moveTo(width, endy)
			.release().perform();
		}
	}

	/**
	 * This method is use to open Mobile web URL from Config.ini file
	 */
	public void openURL() {
		String Url = null;
		try {
			Url = objJavaUtils.getGValueByKey("Common-MobileWeb", "URL");
			aDriver.get(Url);
			System.out.println("URL: " + Url);
		} catch (IOException e1) {
			// System.out.println("URL: " + Url + " Not Opened");
			e1.printStackTrace();
		}
	}

	/**
	 * @param elementName
	 * @param timeout
	 * @return This methods is used to check the element is present or not
	 */
	public boolean isElementPresent(AndroidElement elementName, int timeout) {
		try {
			WebDriverWait wait = new WebDriverWait(aDriver, timeout);
			wait.until(ExpectedConditions.visibilityOf(elementName));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * this method is used to click on android element, for click on element just
	 * call the method
	 * 
	 * @param element
	 * @param reportContent
	 * 
	 */

	public void clickOnAndroidElement(AndroidElement element, String reportContent) {
		//waitDriverWhenReady(element, reportContent);
		takeSnapShot(); 
		element.click();
		ReportClickEvent(reportContent);
 
	}

	/**
	 * this method is used to click on android element, for click on element just
	 * call the method
	 * Note: this is just work around for taking screenshot in webview
	 * 
	 * @param element
	 * @param reportContent
	 * 
	 */

	public void clickOnAndroidElementforwebVIew(AndroidElement element, String reportContent) {
	//	waitDriverWhenReady(element, reportContent);
		switchInToNativeView();
		takeSnapShot();
		swithchInToWebview();
		element.click();
		ReportClickEvent(reportContent);

	}
	/**
	 *Modified By Aishurya: Was showing clicked instead of enter Succesfully
	 */
	public void enterTexAndroidElement(AndroidElement element, String textValue, String reportContent) {
		element.sendKeys(textValue);
		ReportEnterEvent(reportContent);
	}

	public void VerifyStringContains(AndroidElement element, String expectedString) {
		if (element.getText().contains(expectedString)) {
			Reporter.log("Passed :: '" + expectedString + "' is Matched");
		} else {
			Reporter.log("Failed :: '" + expectedString + "' is Not Matched :: Expected :'" + expectedString
					+ "' Actual Found :'" + element.getText() + "'");
			Assert.fail("Expected and Actual is not Matched :: Expected :'" + expectedString + "' Actual Found :'"
					+ element.getText() + "'");
		}
	}

	public void clickUsingCoordiates(int x, int y) {
		TouchAction touch = new TouchAction(aDriver);
		touch.press(x, y).perform();

	}

	public void VerifyNumberShouldNotZeroOrLess(List<AndroidElement> element) {
		int actNumber = element.size();
		if ((actNumber > 0)) {
			Reporter.log("Passed :: '" + actNumber + "' digit is Displayed and '" + actNumber + "' > to 0");
		} else {
			Reporter.log("Failed :: '" + actNumber + "' digit is displayed but Found '" + actNumber + "' <= to 0");
			Assert.fail("'" + actNumber + "' Expected is > 0 ");
		}
	}

	/**
	 * This method is used to scroll, Proper input should be given this
	 * method is functional
	 * 
	 * @param aDriver
	 * @param text
	 * @author Sneha
	 */
	public void scrollToText(AppiumDriver<MobileElement> aDriver, String text) {
		((FindsByAndroidUIAutomator<MobileElement>) aDriver).findElementByAndroidUIAutomator(
				"new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().text(\""
						+ text + "\").instance(0))");
	}
	
	/**
	 * 
	 * @author 300021278 -Rakesh
	 * @param parentClass
	 * @param childClass
	 * @param text
	 */
	public void scrollByChild(String parentClass,String childClass, String text) {
		aDriver.findElement(MobileBy.AndroidUIAutomator(
				"new UiScrollable(new UiSelector().className(\"" + parentClass + "\")).getChildByText("
				+ "new UiSelector().className(\""+childClass+"\"), \""+text+"\")"));
	}
	
	
	

	/**
	 * This method is to scroll and u need to pass element
	 * 
	 * @param aDriver
	 * @param element
	 */
	public void scrollIntoView(AndroidElement element) {
		((JavascriptExecutor) aDriver).executeScript("arguments[0].scrollIntoView(true);", element);
	}

	/**
	 * This method is to have a back action
	 * 
	 * @author 300019227 Anu;
	 */
	public void backKeyButton() {
		((PressesKeyCode) aDriver).pressKeyCode(AndroidKeyCode.BACK);
	}

	/**
	 * This method is to scroll the page up; to move downwards small scroll
	 * 
	 * @author Anu
	 */
	public void scrollPage() {
		JavascriptExecutor jse = (JavascriptExecutor) aDriver;
		jse.executeScript("window.scrollBy(0,100)", "");
	}

	public void scrollup() {
		JavascriptExecutor jse = (JavascriptExecutor) aDriver;
		jse.executeScript("window.scrollBy(100,0)", "");
	}

	/**
	 * This method is to scroll the page up; to move downwards; User needs to pass
	 * the scroll speed value in terms of 100, 200, 300, 400, 500 etc;
	 * 
	 * @author Anu
	 */
	public void scrollPagedown(int scroll) {
		JavascriptExecutor jse = (JavascriptExecutor) aDriver;
		jse.executeScript("window.scrollBy(0," + scroll + ")", "");
	}

	/**
	 * This method is to scroll the page up and click on the element; User needs to
	 * pass the element till where u want to scroll and click and need to pass the
	 * Scroll speed value 100, 200, 300, 400, 500 etc;
	 * 
	 * NotE====>> This has click action
	 * 
	 * @param element
	 * @param scroll
	 * @author 300019227 Anu
	 */
	/**
	 * @param element
	 * @param scroll
	 * modified by :Anu
	 * Description : increased maximum no.of scroll to 200
	 */
	public void scrollDown(AndroidElement element, int scroll) {
		for(int i=0;i<=15;i++) {
			Reporter.log("'" + element + "'  Looking for Element; Scroll in progress");
			scrollPagedown(scroll);
			try {
				element.click();
				System.out.println("element isDisplayed successfully");
				Thread.sleep(5000);
				break;
			} catch (Exception e) {
				// e.printStackTrace();
				System.out.println("Scrolled:But element not found");
				Reporter.log("Scrolled:But element not found");
			}
		}

	}
	/**
	 * Method to scroll, User needs to provide the direction ; Method is used for mWeb
	 * @author 300021278 -Rakesh
	 */
	public void scrollDown(String Direction, AndroidElement element, int scroll) {
		if (Direction.contains("down")) {
			for (int i=0; i<=15;i++) {
				if (element.isDisplayed()) {
					element.click();
					System.out.println("element clicked successfully");
					break;
				}
				else {
					Reporter.log("'" + element + "'  Looking for Element; Scroll in progress");
					scrollPagedown(scroll);
					System.out.println("element search is in progress:: Scrolling");
				}
			}
			}
			else if(Direction.contains("up")) {
				for (int i=0; i<=15;i++) {
					Reporter.log("'" + element + "'  Looking for Element; Scroll in progress");
					if (element.isDisplayed()) {
						element.click();
						System.out.println("element clicked successfully");
						break;
					}
					else {
						System.out.println("element search is in progress:: Scrolling");
						scrollPagedown(-scroll);
					}
				}
				}
	}

	/**
	 * 
	 * This method is to scroll the page up; to move downwards; User needs to pass
	 * the element till where u want to scroll and need to pass the Scroll speed
	 * value 100, 200, 300, 400, 500 etc;
	 * 
	 * @param element
	 * @param scroll
	 * @author 300019227 Anu
	 */
	public void ScrollDownWithoutClick(AndroidElement element, int scroll) {
		while (true) {
			Reporter.log("'" + element + "'  Looking for Element; Scroll in progress");
			scrollPagedown(scroll);
			try {
				element.isDisplayed();
				System.out.println("element isDisplayed successfully");
				Thread.sleep(2000);
				break;
			} catch (Exception e) {
				// e.printStackTrace();
				System.out.println("Scrolled:But element not found");
				Reporter.log("Scrolled:But element not found");
			}
		}

	}

	/**
	 * This is the method to call the data from ini file for android mobile web
	 * 
	 * @author 300021278 -Rakesh
	 * @param gettitle
	 * @param getKey
	 * @return
	 * @throws InvalidFileFormatException
	 * @throws IOException
	 */
	public String getValueByKeyWeb(String gettitle, String getKey) throws InvalidFileFormatException, IOException {
		Ini ini = new Ini(
				new File(System.getProperty("user.dir") + "//TestData//mobile//Android//MobileWeb//test-data.ini"));
		String value = ini.get(gettitle, getKey);
		if (value == null) {
			System.err.println("Enter Proper Title or Key!");
		}
		System.out.println(getKey + "-->" + value);
		return value;
	}

	public void waitUntilElementAppears(AndroidElement element) {
		WebDriverWait wait = new WebDriverWait(aDriver, 120);
		wait.until(ExpectedConditions.visibilityOf((AndroidElement) element));
	}
	
	public void waitUntilElementclickable(AndroidElement element){

		WebDriverWait wait = new WebDriverWait(aDriver, 200);
		wait.until(ExpectedConditions.elementToBeClickable(element));
	}
	/**
	* @param ele
	* @param strElement
	* @return Created By : Neeraj Description: wait for the passed element to be
	*         available on web page untill 30 sec if not then report as element not
	*         found error message. This method gets call in CheckWebElementFound()
	*         for sync
	*
	*/
	public boolean waitDriverWhenReady(WebElement ele, String strElement) {
	try {
	WebDriverWait wait = new WebDriverWait(aDriver, 20);
	wait.until(ExpectedConditions.elementToBeClickable(ele));
	return true;
	} catch (Exception e) {
	Reporter.log("ELEMENT NOT FOUND AND NOT CLICKABLE:: Waited for 20 seconds to get the element '" + strElement + "'.");
	return false;
	}
	}
	/**
	* @param ele
	* @param strElement
	* @return Created By : Neeraj Description: wait for the passed element to be
	*         available on web page untill 30 sec if not then report as element not
	*         found error message. This method gets call in CheckWebElementFound()
	*         for sync
	*
	*/
	public boolean waitDriver(WebElement ele, String strElement) {
	try {
	WebDriverWait wait = new WebDriverWait(aDriver, 20);
	wait.until(ExpectedConditions.visibilityOf(ele));
	return true;
	} catch (Exception e) {
	Reporter.log("ELEMENT NOT FOUND :: Waited for 20 seconds to get the element '" + strElement + "'.");
	return false;
	}
	}
	
	/**
	 * This method will helps us to switch to a New
	 * Created By Subhasis 
	 */

	public void switchToNewWindow() {
		log.info("switchToNewWindow method is in progress");
		Set<String> s = aDriver.getWindowHandles();
		Iterator<String> itr = s.iterator();
		itr.next();
		String w2 = (String) itr.next();
		aDriver.switchTo().window(w2);
		log.info("switchToNewWindow executed sucessfully");
	}

	/**
	 * Created By Subhasis 
	 * This method will switchTo Main window
	 */
	public void switchToMainWindow() throws InterruptedException {

		List<String> windows = new ArrayList<String>(aDriver.getWindowHandles());
		for (int i = 1; i < windows.size(); i++) {
			aDriver.switchTo().window(windows.get(i));
			aDriver.close();
		}
		// driver.switchTo().window(windows.get(1));
		// driver.close();
		aDriver.switchTo().window(windows.get(0));
	}
	
	/**
	 * This method help to swith into webview
	 */
	public void swithchInToWebview() {
		Set<String> contextNames = aDriver.getContextHandles();
		System.out.println(contextNames);
		
//		System.out.println("------------------");
		aDriver.context("WEBVIEW_com.myntra.android");
//		System.out.println("------------------");
//		System.out.println(aDriver.getContext());
//		System.out.println("------------------");
	}
	
	public void switchInToNativeView() {
		Set<String> contextNames = aDriver.getContextHandles();
	//	System.out.println(contextNames);
		
	//	System.out.println("------------------");
		aDriver.context("NATIVE_APP");
	//	System.out.println("------------------");
	//	System.out.println(aDriver.getContext());
	//	System.out.println("------------------");
		
	}
	
	
	

	public void applyCoupon() {

		try {

			String url = "http://coupon.stage.myntra.com/coupons/bulkOpsInclusionExclusion";

			URL obj = new URL(url);

			HttpURLConnection conn = (HttpURLConnection) obj.openConnection();

			conn.setRequestProperty("Content-Type", "application/json");

			conn.setDoOutput(true);

			conn.setRequestMethod("POST");

			String data = "{\"groups\":\"Vega\",\r\n" +

					"    \"styleIds\":\"2345\",\r\n" +

					"    \"inclusion\":\"true\",\r\n" +

					"    \"update\":\"true\"}";

			OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());

			out.write(data);

			out.close();

			new InputStreamReader(conn.getInputStream());
			System.out.println("apply coupon success");

		} catch (Exception e) {

			e.printStackTrace();

		}

	}
	
	
	/**
	 * Method to scroll; Infinite loop has been handled
	 * @author 300021282 VINAY
	 * @param element
	 */
	public void scrolltoElementAndClick(AndroidElement element) {
		try {
		for(int i=0;i<=7;i++) {
		try {
		element.isDisplayed();
		System.out.println("element is displayed successfully");
		takeSnapShot();
		element.click();
		break;
		} catch (Exception e) {
		bottomTopSwipe(4000);
		System.out.println("Scrolling successfully");
		Reporter.log("'" + element + "'  Looking for Element; Scroll in progress");
		}
		}
		} catch (Exception e) {
		System.out.println("Scrolled:But element not found");
		Reporter.log("No such element: Unable to locate element. Please check the Element");
		}

		}

}
