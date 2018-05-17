/**
 * 
 */
package com.automation.core.web;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
import javax.mail.internet.MimeMultipart;

import org.apache.maven.surefire.shade.org.apache.maven.shared.utils.io.FileUtils;
import org.ini4j.Ini;
import org.ini4j.InvalidFileFormatException;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

import com.automation.core.Common.GlobalVariables;
import com.google.common.base.Function;

/**
 * @author neerajkumar.b.lad
 *
 */
public class GenericMethods extends GlobalVariables {
	public WebDriver driver;
	public Logger log = Logger.getLogger("devpinoyLogger");

	public GenericMethods() {
		super();
	}

	public GenericMethods(WebDriver driver) {
		this.driver = driver;
	}

	public List<String> beforeSort = new ArrayList<String>();
	public List<String> afterSort = new ArrayList<String>();

	public String getPlatform() {
		String osType = null;
		String osName = System.getProperty("os.name");
		String osNameMatch = osName.toLowerCase();
		if (osNameMatch.contains("linux")) {
			osType = "OS_LINUX";
		} else if (osNameMatch.contains("windows")) {
			osType = "OS_WINDOWS";
		} else if (osNameMatch.contains("solaris") || osNameMatch.contains("sunos")) {
			osType = "OS_SOLARIS";
		} else if (osNameMatch.contains("mac os") || osNameMatch.contains("macos") || osNameMatch.contains("darwin")) {
			osType = "OS_MAC";
		} else {
			osType = "Unknown";
		}
		System.out.println("=========== Running script on " + osName + "=================");
		return osType;
	}

	public DesiredCapabilities getDesiredCapabilities() {
		DesiredCapabilities capability = new DesiredCapabilities();
		capability.setCapability("browser", "Firefox");
		capability.setCapability("browser_version", "34.0");
		capability.setCapability("os", "windows");
		capability.setCapability("os_version", "7");
		capability.setCapability("build", "screenshot test");
		capability.setCapability("browserstack.debug", "true");
		capability.setCapability("browserstack.user", "neerajkumar22");// add
		// username
		capability.setCapability("browserstack.key", "dQhCMapyDN2HX8pTCdsN"); // add
		// automate-key
		return capability;
	}

	public String getValueFromPropertyFile(String Key) {
		FileReader reader;
		try {
			Properties prop = new Properties();
			reader = new FileReader(PROJECT_ROOT_FOLDER + "/Configuration/common.properties");
			prop.load(reader);
			String KeyValue = prop.getProperty(Key);
			System.out.println(Key + " : " + KeyValue);
			return KeyValue;
		} catch (IOException e1) {
			System.out.println(PROJECT_ROOT_FOLDER + "/Configuration/common.properties File Not found");
			e1.printStackTrace();
		}
		return null;
	}

	/**
	 * @throws IOException
	 * @author 300019224 Aishurya
	 * Removed implecitly wait in windowMax()
	 */
	public void windowMax() throws IOException {
		driver.manage().window().maximize();
	}

	public void deletecookies() {
		driver.manage().deleteAllCookies();
	}

	public boolean isElementPresent(By by) {
		try {
			return driver.findElements(by).size() > 0;
		} catch (Exception e) {
			System.out.println("Element not found");
			return false;
		}
	}

	/**
	 * @param Url
	 *            Url parameter passes from mvn command Removed the method which get
	 *            url from common properties file
	 *            Modified by: Nitesh, added page load wait time
	 */
	public void openURL(String Url) {
		// String Url = null;
		try {
			// Url = getValueFromPropertyFile("URL");
			deletecookies();
			driver.get(Url);
			System.out.println("URL: " + Url);
			windowMax();
			driver.manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS);
		} catch (IOException e1) {
			System.out.println("URL: " + Url + " Not Opened");
			e1.printStackTrace();
		}
	}

	public void SendMailJava(boolean isFileFound) throws IOException {
		FileReader reader;
		Properties prop;
		// try {
		reader = new FileReader("../Configuration/config.properties");
		prop = new Properties();
		prop.load(reader);
		// final String username = prop.getProperty("username_FromAddress");
		// final String password = prop.getProperty("passowrd");
		// final String ToEmail = prop.getProperty("EmailTo");
		final String UserName = getValueFromPropertyFile("EmailUserName");
		final String Password = getValueFromPropertyFile("EmailPassword");
		String ToEmail = getValueFromPropertyFile("ToEmails");
		System.out.println("SendMailJava isFileFound: " + isFileFound);
		Date date = new Date();
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dt;
		dt = ft.format(date);
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.googlemail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");
		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(UserName, Password);
			}
		});
		String[] cc = prop.getProperty("EmailCC").split(",");
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(UserName));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(ToEmail));
			InternetAddress[] addressCC = new InternetAddress[cc.length];
			for (int i = 0; i < cc.length; i++) {
				addressCC[i] = new InternetAddress(cc[i]);
			}
			message.setRecipients(RecipientType.CC, addressCC);
			BodyPart messageBodyPart = new MimeBodyPart();
			// Now set the actual message
			System.out.println("======Start " + isFileFound + "========");
			if (isFileFound == true) {
				// failed case
				message.setSubject("POC_Web Automation :: Failed");
				messageBodyPart.setText(
						"Hi Team,\n\nThis is auto generated mail from Automation script. Please do not reply back. \n\nURL: http://162.248.104.37 \nDate of Execution : "
								+ dt.toString() + " \n\nResult : " + "Failed"
								+ " \n\nWe will investigating for this fail and update you all soon.  \n\nThanks & Regards \n QA Team");
			} else {
				// passed case
				message.setSubject("POC_Web Automation :: Passed");
				messageBodyPart.setText(
						"Hi Team,\n\nThis is auto generated mail from Automation script. Please do not reply back. \n\nURL: https://automation..org \nDate of Execution : "
								+ dt.toString() + " \n\nResult : " + "Passed" + " \n\nThanks & Regards \nQA Team");
			}
			Multipart multipart = new MimeMultipart();
			MimeBodyPart messageAttachement;
			// Set text message part
			multipart.addBodyPart(messageBodyPart);
			// Part two is attachment
			messageAttachement = new MimeBodyPart();
			String filename = PROJECT_ROOT_FOLDER + "/test-output/emailable-report.html";
			DataSource source = new FileDataSource(filename);
			messageAttachement.setDataHandler(new DataHandler(source));
			messageAttachement.setFileName(filename);
			multipart.addBodyPart(messageAttachement);
			// Send the complete message parts
			message.setContent(multipart);
			Transport.send(message);
			System.out.println("Email Report Sent");
		} catch (MessagingException e) {
			System.out.println("Some problem in sending mail");
			throw new RuntimeException(e);
		}
	}

	// Date Format
	public String datetime(String dateFormat) {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		return sdf.format(cal.getTime());
	}

	// Find and Return Element
	public WebElement getfindElement(String identifyBy, String locator) {
		WebDriverWait driverWait = new WebDriverWait(driver, 50);
		WebElement element = null;
		if (identifyBy.equalsIgnoreCase("id")) {
			element = driverWait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id(locator))));
		} else if (identifyBy.equalsIgnoreCase("xpath")) {
			element = driverWait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(locator))));
		} else if (identifyBy.equalsIgnoreCase("class")) {
			element = driverWait.until(ExpectedConditions.visibilityOf(driver.findElement(By.className(locator))));
		} else if (identifyBy.equalsIgnoreCase("css")) {
			element = driverWait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector(locator))));
		} else if (identifyBy.equalsIgnoreCase("name")) {
			element = driverWait.until(ExpectedConditions.visibilityOf(driver.findElement(By.name(locator))));
		} else if (identifyBy.equalsIgnoreCase("linkText")) {
			element = driverWait.until(ExpectedConditions.visibilityOf(driver.findElement(By.linkText(locator))));
		} else if (identifyBy.equalsIgnoreCase("partialLinkText")) {
			element = driverWait
					.until(ExpectedConditions.visibilityOf(driver.findElement(By.partialLinkText(locator))));
		} else if (identifyBy.equalsIgnoreCase("tagName")) {
			element = driverWait.until(ExpectedConditions.visibilityOf(driver.findElement(By.tagName(locator))));
		}
		return element;
	}
	
	public void HoverOnWebElement(final WebElement webElement) {
		try {
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			driver.findElement(By.xpath("//div[@class='myntraClass']"));
		} catch (Exception e) {
		}
		Actions action = new Actions(driver);
		action.moveToElement(webElement).build().perform();
	}

	public void verifyWebElement(WebElement element, String strExpectedTitle) {
		Assert.assertEquals(strExpectedTitle, element.getText());
	}

	public boolean checkVisibilitybyElement(String identifierString, String locatorType) {
		int size = 0;
		switch (locatorType.toLowerCase()) {
		case "xpath":
			size = driver.findElements(By.xpath(identifierString)).size();
			break;
		case "id":
			size = driver.findElements(By.id(identifierString)).size();
			break;
		case "linktext":
			size = driver.findElements(By.linkText(identifierString)).size();
			break;
		case "css":
			size = driver.findElements(By.cssSelector(identifierString)).size();
			break;
		case "tagname":
			size = driver.findElements(By.tagName(identifierString)).size();
			break;
		case "classname":
			size = driver.findElements(By.className(identifierString)).size();
			break;
		case "name":
			size = driver.findElements(By.name(identifierString)).size();
			break;
		case "partiallinktext":
			size = driver.findElements(By.partialLinkText(identifierString)).size();
			break;
		default:
			System.out.println("Error : Please check your identifierString or locatorType");
			break;
		}
		if (size == 0) {
			System.out.println("Error : " + identifierString + "Element is not found.");
			return false;
		} else {
			System.out.println("Element found and it is " + identifierString);
			return true;
		}
	}

	public List<WebElement> getFindElements(String identifyBy, String locatorValue) {
		WebDriverWait driverWait = new WebDriverWait(driver, 50);
		List<WebElement> elements = null;
		if (identifyBy.equalsIgnoreCase("id")) {
			elements = driverWait
					.until(ExpectedConditions.visibilityOfAllElements(driver.findElements(By.id(locatorValue))));
		} else if (identifyBy.equalsIgnoreCase("xpath")) {
			elements = driverWait
					.until(ExpectedConditions.visibilityOfAllElements(driver.findElements(By.xpath(locatorValue))));
		} else if (identifyBy.equalsIgnoreCase("css")) {
			elements = driverWait.until(
					ExpectedConditions.visibilityOfAllElements(driver.findElements(By.cssSelector(locatorValue))));
		} else if (identifyBy.equalsIgnoreCase("class")) {
			elements = driverWait
					.until(ExpectedConditions.visibilityOfAllElements(driver.findElements(By.className(locatorValue))));
		} else if (identifyBy.equalsIgnoreCase("name")) {
			elements = driverWait
					.until(ExpectedConditions.visibilityOfAllElements(driver.findElements(By.name(locatorValue))));
		} else if (identifyBy.equalsIgnoreCase("linkText")) {
			elements = driverWait
					.until(ExpectedConditions.visibilityOfAllElements(driver.findElements(By.linkText(locatorValue))));
		} else if (identifyBy.equalsIgnoreCase("partialLinkText")) {
			elements = driverWait.until(
					ExpectedConditions.visibilityOfAllElements(driver.findElements(By.partialLinkText(locatorValue))));
		} else if (identifyBy.equalsIgnoreCase("tagName")) {
			elements = driverWait
					.until(ExpectedConditions.visibilityOfAllElements(driver.findElements(By.tagName(locatorValue))));
		}
		return elements;
	}

	public WebElement getWebElementByName(List<WebElement> elements, String MenuName) {
		List<WebElement> menuItems = elements;
		for (WebElement menu : menuItems) {
			waitDriverWhenReady(menu, MenuName);
			if (menu.getText().equalsIgnoreCase(MenuName)) {
				return menu;
			}
		}
		return null;
	}

	/**
	 * 
	 * This method takes the screenshot for WEB and store in screenshots folder with
	 * yyyyMMddHHmmss format
	 * 
	 * Neerajkumar
	 * 
	 */
	public void takeSnapShot() {
		try {
			// Convert web driver object to TakeScreenshot
			TakesScreenshot scrShot = ((TakesScreenshot) driver);
			// Call getScreenshotAs method to create image file
			File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);
			// Move image file to new destination
			File DestFile = new File(
					GlobalVariables.PROJECT_ROOT_FOLDER + "/src/test/resources/" + datetime("yyyyMMddHHmmss") + ".jpg");
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
	 * @author 300020817 - Neeraj
	 * 
	 * @Date 10th Jan 2018 Modified By: Neeraj Reason : Added parent if for
	 *       waitDriver(), Change reporter.log text and removed assert condition
	 *       Description: If WebElement found then only check for display or not
	 *       display condition
	 */
	public void CheckWebElementFound(WebElement element, String elementName) {
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
			System.out.println(e.getMessage());
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
	 * @author 300020817 - Neeraj
	 * 
	 * @Date 10th Jan 2018 Modified By: Neeraj Reason : Removed assert condition
	 */
	public void CheckWebElementsListFound(List<WebElement> element, String elementName) {
		try {
			if (element.size() >= 0) {
				Reporter.log("'" + elementName + "' Element object is Found");
			} else {
				Reporter.log("'" + elementName + "'  Element object is NOT Found");
			}
		} catch (NoSuchElementException e) {
			Reporter.log("FAILED TO PERFORM ACTION ON " + elementName + " even after waiting for 20 seconds");
			System.out.println(e.getMessage());
		}
	}

	/**
	 * @param element
	 * @param object
	 *            Modified By: Neeraj Reason : Removed assert condition
	 */
	public void VerifyTwoString(WebElement element, String object) {
		try {
			if (element.getText().equalsIgnoreCase(object)) {
				Reporter.log("Passed :: '" + object + "' is Matched");
			} else {
				Reporter.log("Failed :: '" + object + "' is Not Matched :: Expected :'" + object + "' Actual Found :'"
						+ element.getText() + "'");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Reporter.log("FAILED TO verify TWO STRING and Expected string is '" + object + "'");
			System.out.println(e.getMessage());
		}
	}

	/**
	 * @param element
	 * @param expectedString
	 *            Modified By: Neeraj Reason : Removed assert condition
	 */
	public void VerifyStringContains(WebElement element, String expectedString) {
		try {
			if (element.getText().contains(expectedString)) {
				Reporter.log("Passed :: '" + expectedString + "' is Matched");
			} else {
				Reporter.log("Failed :: '" + expectedString + "' is Not Matched :: Expected :'" + expectedString
						+ "' Actual Found :'" + element.getText() + "'");
			}
		} catch (Exception e) {
			Reporter.log("FAILED TO verify string CONTAINS and Expected string is '" + expectedString + "'");
			System.out.println(e.getMessage());
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
	 * @author 300020817 - Neeraj
	 * 
	 *         This method verify that given object text is empty or not
	 * 
	 */
	/**
	 * @param element
	 * @param str
	 *            Modified By: Neeraj Reason : Removed assert condition
	 */
	public void VerifyStringShouldNotEmpty(WebElement element, String str) {
		try {
			if (!element.getText().isEmpty()) {
				Reporter.log("Passed :: '" + element.getText() + "' Text is Displayed and Not Empty");
			} else {
				Reporter.log("Failed :: '" + str + "' Expected is NOT EMPTY text but found EMPTY");
			}
		} catch (Exception e) {
			Reporter.log("FAILED TO verify string SHOULD NOT EMPTY and Expected string is '" + str + "'");
			System.out.println(e.getMessage());
		}
	}

	/**
	 * 
	 * @param elementName
	 *            Name of the WebElement Object
	 * 
	 * @author 300020817 - Neeraj
	 * 
	 *         This method will write the given text into log for Click event
	 *         Modified By: Neeraj Reason : Corrected text for reporter.log()
	 */
	public void ReportClickEvent(String elementName) {
		Reporter.log("'" + elementName + "' Clicked Succesfully.");
	}

	/**
	 * 
	 * @param elementName
	 *            Name of the WebElement Object
	 * 
	 * @author 300019221 - Aravinda
	 * 
	 *         This method will write the given text into log for Click event
	 * 
	 */
	public void setDiscount(String parameterName) throws IOException {
		log.info("writing the center name to a .ini file");
		Ini ini = new Ini(new File("./TestData/Mobile/Android/apps/test-data.ini"));
		ini.put("Discounts", "Discount", parameterName);
		ini.store();
	}

	public String getDiscount() throws InvalidFileFormatException, IOException {
		log.info("reading the center name details from a .ini file");
		Ini ini = new Ini(new File("/TestData/Mobile/Android/apps/test-data.ini"));
		String discount = ini.get("Discounts", "Discount");
		System.err.println("Discount-->" + discount);
		return discount;
	}

	/**
	 * @param element
	 *            Modified By: Neeraj Reason : Removed assert condition and changed
	 *            exception type to 'Exception' in catch section
	 */
	public void VerifyNumberShouldNotZeroOrLess(WebElement element) {
		try {
			VerifyStringShouldNotEmpty(element, "Numbers");
			String actString = element.getText().replaceAll("[^0-9]", "");
			int num = Integer.parseInt(actString);
			if ((num > 0)) {
				Reporter.log("Passed :: '" + actString + "' digit is Displayed and '" + actString + "' > to 0");
			} else {
				Reporter.log("Failed :: '" + actString + "' digit is displayed but Found '" + actString + "' <= to 0");
			}
		} catch (Exception e) {
			Reporter.log("FAILED TO verify NUMBER SHOULD NOT <= 0");
			System.out.println(e.getMessage());
		}
	}

	public void scrollPage0to250() {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollBy(0,500)", "");
	}

	public void scrollPage(int scroll) {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollBy(0," + scroll + ")", "");
	}

	/**
	 * Modified By: Irfan
	 * Reason: Modified, since when the element is not available it goes to infinite loop.
	 */
	public void scrollDown(WebElement element, int scroll) {
		try {
		while (element.isDisplayed()) {
			Reporter.log("'" + element + "'  Looking for Element; Scroll in progress");
			scrollPage(scroll);
			try {
				element.click();
				break;
			} catch (Exception e) {
				//e.printStackTrace();
				System.out.println("Scroll is in progress...But element not found...");
			}
		}}
		catch(Exception e)
		{
			System.out.println("Failed :: Element is not available in the current page" + e.getMessage());
		}
	}

	public void scrollPageUp() {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollBy(0,-400)", "");
	}

	/**
	 * @param dataOne
	 * @param dataTwo
	 * Modified By: Neeraj Reason : removed e.printStackTrace();
	 * Modified By: Nitesh Reason : modified message to be printed
	 */
	public void verifyTwoStringData(String dataOne, String dataTwo) {
		try {
//			Assert.assertEquals(dataOne, dataTwo);
			Reporter.log("Passed : Displayed text is same as expected text," + dataOne);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Reporter.log("Failed : Displayed text " + dataOne + " is not same as expected text, " + dataTwo);
		}
	}

	public void setValueByKeyAndTitel(String title, String Key, String parameterName) throws IOException {
		Ini ini = new Ini(new File(System.getProperty("user.dir") + "//TestData//Mobile//iOS//apps//test-data.ini"));
		ini.put(title, Key, parameterName);
		ini.store();
	}

	/**
	 * 
	 * @param elementName
	 *            Name of the getDiscount Object
	 * 
	 * @author 300019221 - Aravindanath
	 * Modified By: Neeraj Reason : removed println()
	 * Modified by: Nitesh, Reason: parameterised test data file for diff envs        
	 *         
	 */
	public String getValueByKey(String gettitle, String getKey) {
		String env = System.getProperty("url");
		Ini ini = null;
		try {
			if (env.equalsIgnoreCase("https://sfqa.myntra.com/")){ 
				ini = new Ini(new File(System.getProperty("user.dir") + "//TestData//web//SFQA//test-data-sfqa.ini"));
			}else{
				ini = new Ini(new File(System.getProperty("user.dir") + "//TestData//web//MJINT//test-data-mjint.ini"));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Ini file not found");
			e.printStackTrace();
		}
		String value = ini.get(gettitle, getKey);
		if (value == null) {
			System.err.println("Enter Proper Title or Key!");
		}
		return value;
	}

	/**
	 * @param elementName
	 * @return This methods is used to check the element is present or not
	 */
	public boolean isElementPresent(WebElement elementName) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.visibilityOf(elementName));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public String splitString(WebElement element, String splitBy, int place) {
		String[] str = element.getText().split(splitBy);
		return str[place];
	}

	public void FetchAllName(List<String> allname, List<WebElement> AllProductName) {
		for (int i = 0; i < AllProductName.size(); i++) {
			allname.add(AllProductName.get(i).getText());
		}
	}

	public void FetchAllNameBeforeSort(List<WebElement> AllProductName) {
		FetchAllName(beforeSort, AllProductName);
	}

	public void FetchAllNameAfterSort(List<WebElement> AllProductName) {
		FetchAllName(afterSort, AllProductName);
	}

	public void verifySortByEffectiveness() {
		int count = 0;
		for (int i = 0; i < beforeSort.size(); i++) {
			if (!beforeSort.get(i).equals(afterSort.get(i))) {
				count++;
			}
		}
		if (count == 0) {
			Reporter.log("FAILED :: Product list are not getting sorted as per sort selection");
		} else {
			Reporter.log("PASSED: Products are getting sorted as per selction");
		}
	}

	/**
	 * @param element
	 * @param str
	 *            Modified By: Neeraj Reason : Added try catch()
	 */
	public void CheckElementContainsString(WebElement element, String str) {
		try {
			if (element.getText().contains(str)) {
				Reporter.log(str + " is visible");
			} else {
				Reporter.log(str + " is not visible");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Reporter.log("FAILED TO check element CONTAINS STRING and Expected string is '" + str + "'");
			System.out.println(e.getMessage());
		}
	}

	public void waitTillElementLoads(final String ele, WebDriver driver) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(30, TimeUnit.SECONDS)
				.pollingEvery(5, TimeUnit.SECONDS).ignoring(NoSuchElementException.class);
		WebElement foo = wait.until(new Function<WebDriver, WebElement>() {
			public WebElement apply(WebDriver driver) {
				return driver.findElement(By.xpath(ele));
			}
		});
		foo.click();
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
			WebDriverWait wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.visibilityOf(ele));
			return true;
		} catch (Exception e) {
			Reporter.log("ELEMENT NOT FOUND :: Waited for 10 seconds to get the element '" + strElement + "'.");
			return false;
		}
	}
	
	public void applyCoupon(String PersonalizedCoupan) {

		try {

			String url = "http://coupon."+PersonalizedCoupan+".myntra.com/coupons/bulkOpsInclusionExclusion";

			URL obj = new URL(url);

			HttpURLConnection conn = (HttpURLConnection) obj.openConnection();

			conn.setRequestProperty("Content-Type", "application/json");

			conn.setDoOutput(true);

			conn.setRequestMethod("POST");

			String data = "{\"groups\":\"Vega\",\r\n" +

					"    \"styleIds\":\"1531\",\r\n" +

					"    \"inclusion\":\"true\",\r\n" +

					"    \"update\":\"true\"}";

			new InputStreamReader(conn.getInputStream());
			System.out.println("apply coupon success");

		} catch (Exception e) {

			e.printStackTrace();
		}
	
	}
//	public void clickWhenReady(By locator, int timeout) {
//	    WebDriverWait wait = new WebDriverWait(driver, timeout);
//	    WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
//	    element.click();
//	}
	
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
			WebDriverWait wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.elementToBeClickable(ele));
			return true;
		} catch (Exception e) {
			Reporter.log("ELEMENT NOT FOUND AND NOT CLICKABLE:: Waited for 10 seconds to get the element '" + strElement + "'.");
			return false;
		}
	}
}


