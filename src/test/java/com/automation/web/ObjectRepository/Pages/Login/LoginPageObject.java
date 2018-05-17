package com.automation.web.ObjectRepository.Pages.Login;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.automation.core.web.GenericMethods;
import com.automation.web.ObjectRepository.Pages.Header.HeaderPageObject;

/**
 * @author 300020817
 *
 */
public class LoginPageObject {

	public WebElement element = null;
	public List<WebElement> elements;
	GenericMethods objGenericMethods;
	HeaderPageObject objHeaderPageObject;
	WebDriver driver;

	/**
	 * @param driver
	 */
	public LoginPageObject(WebDriver driver) {
		super();
		PageFactory.initElements(driver, this);
		objGenericMethods = new GenericMethods(driver);
		objHeaderPageObject = new HeaderPageObject(driver);
		this.driver=driver;
	}

	@FindBy(name = "email")
	public WebElement EmailAddress;

	@FindBy(name = "password")
	public WebElement Password;

	@FindBy(xpath = ".//button[text()='Log in']")
	public WebElement LogInButton;

	@FindBy(xpath = "//div[@class='desktop-infoEmail']")
	public WebElement userEmailId;
	
	@FindBy(xpath="//div[@class='desktop-accActions']/div/div[@class='desktop-accInfoSection']")
	public WebElement LogOutButton;
	


	/**
	 * Added by-Aishu- for LoginWithFaceBook() method
	 */
	@FindBy(xpath="//button[contains(@class, 'login-facebook')]")
	public WebElement LogInFaceBookBtn;
	
	/**
	 * Added by-Aishu- for LoginWithGmail() method
	 */
	@FindBy(xpath="//button[@id='gPlusLogin']")
	public WebElement GmailBtn;
	

	public WebElement getGmailBtn() {
		return GmailBtn;
	}

	public WebElement getLogInFaceBookBtn() {
		return LogInFaceBookBtn;
	}

	public WebElement getUserEmailId() {
		objGenericMethods.CheckWebElementFound(userEmailId, "userEmailId");
		return userEmailId;
	}

	public WebElement getEmailAddress() {
		objGenericMethods.CheckWebElementFound(EmailAddress, "EmailAddress");
		return EmailAddress;
	}

	public WebElement getPassword() {
		objGenericMethods.CheckWebElementFound(Password, "Password");
		return Password;
	}

	public WebElement getLogInButton() {
		objGenericMethods.CheckWebElementFound(LogInButton, "LogInButton");
		return LogInButton;
	}
	
	public WebElement getLogOutButton() {
		objGenericMethods.CheckWebElementFound(LogOutButton, "LogOutButton");
		return LogOutButton;
		}

	/**
	 * ModifiedBy : Nitesh
	 * Description: Added WaitDriverWhenReady()
	 */
	
	public void Login(String emailAddress, String password) {
		objGenericMethods.waitDriverWhenReady(getEmailAddress(), "Email Id");
		getEmailAddress().sendKeys(emailAddress);
		objGenericMethods.waitDriverWhenReady(getPassword(), "Password");
		getPassword().sendKeys(password);
	}
	

	public void setLogInButton(WebElement logInButton) {
		LogInButton = logInButton;
	}

	public void LogInButtonClick() {
		objGenericMethods.takeSnapShot();
		getLogInButton().click();
		objGenericMethods.ReportClickEvent("LogIn Button");
	}
	
	public void ClickLogOutButton() {
		objGenericMethods.takeSnapShot();
		getLogOutButton().click();
		objGenericMethods.ReportClickEvent("LogOut Button");
		}

	/**
	 * Modified by: Neeraj 
	 * Reason: isElementPresent was printing assertion on log so
	 * commented isElementPresent() and added verify VerifyStringShouldNotEmpty()
	 * method
	 */
	public void VerifyUserEmailId(){
		objGenericMethods.HoverOnWebElement(objHeaderPageObject.getUserIcon());
		objGenericMethods.VerifyStringShouldNotEmpty(getUserEmailId(), "Login Email ID ");
	}
	/**
	 * Added By:Aishu- Login using FaceBook credentials
	 * Modified By Aishu:Removed driver.close()
	 * 
	 * 
	 * 
	 * ModifiedBy : Neeraj
	 * Description: Removed Thread.sleep and added WaitDriverWhenReady()
	 */
	public void LoginWithFaceBook(String FbUserName,String FbPassWord) {
		String mainWindow=driver.getWindowHandle();
		objGenericMethods.waitDriverWhenReady(getLogInFaceBookBtn(), "Login In Facebook");
		getLogInFaceBookBtn().click();
		for(String currentWindow:driver.getWindowHandles()) {
			if(!currentWindow.equals(mainWindow)) {
				driver.switchTo().window(currentWindow);
			}	
		}		
		driver.findElement(By.name("email")).sendKeys(FbUserName);
		driver.findElement(By.name("pass")).sendKeys(FbPassWord);
		objGenericMethods.waitDriverWhenReady(driver.findElement(By.id("u_0_0")), "Login button");
		driver.findElement(By.id("u_0_0")).click();
		driver.switchTo().window(mainWindow);
		}
	
	/**
	 *Added By -Aishu-Login using gmail account
	 *
	 * 
	 * ModifiedBy : Neeraj
	 * Description: Removed Thread.sleep and added WaitDriverWhenReady()
	 */
	public void LoginWithGmail(String GmailUserName,String GmailPassWord)  {
		
		String mainWindow=driver.getWindowHandle();
		objGenericMethods.waitDriverWhenReady(getGmailBtn(), "Google Login");
		System.out.println(mainWindow);
		getGmailBtn().click();
		for(String currentWindow:driver.getWindowHandles()) {
			if(!currentWindow.equals(mainWindow)) {
				driver.switchTo().window(currentWindow);
				System.out.println(currentWindow);
			}	
		}	
		objGenericMethods.waitDriverWhenReady(driver.findElement(By.name("identifier")), "User Name");
		driver.findElement(By.name("identifier")).sendKeys(GmailUserName);
		driver.findElement(By.className("CwaK9")).click();
		objGenericMethods.waitDriverWhenReady(driver.findElement(By.name("password")), "Password");
		driver.findElement(By.name("password")).sendKeys(GmailPassWord);
		driver.findElement(By.className("CwaK9")).click();		
		//objGenericMethods.waitDriverWhenReady(driver.findElement(By.name("identifier")), "Google Login Button");
		System.out.println("B4 close"+mainWindow);
		System.out.println("After close"+mainWindow);
		driver.switchTo().window(mainWindow);
	}
	}
