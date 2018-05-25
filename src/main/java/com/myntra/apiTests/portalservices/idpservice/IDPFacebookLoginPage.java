package com.myntra.apiTests.portalservices.idpservice;

import org.openqa.selenium.By;

import com.myntra.apiTests.InitializeFramework;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.aragorn.pages.WebPage;

/**
 * @author shankara.c
 * 
 */
public class IDPFacebookLoginPage extends WebPage 
{
	static Initialize  initialize = InitializeFramework.init;

	public IDPFacebookLoginPage(Initialize initialize) {
		super(initialize);
	}

	public static void main(String[] args) {
		IDPFacebookLoginPage test = new IDPFacebookLoginPage(initialize);
		test.fbLogin("mtra1234@gmail.com", "subhadeep");
	}

	public void fbLogin(String fbLoginId, String fbPassword) 
	{
		webDriver.navigate().to("https://developers.facebook.com");
		webDriver.findElement(By.xpath("//span[text()='Log In']")).click();
		webDriver.findElement(By.id("email")).clear();
		webDriver.findElement(By.id("email")).sendKeys(fbLoginId);
		webDriver.findElement(By.id("pass")).clear();
		webDriver.findElement(By.id("pass")).sendKeys(fbPassword);
		webDriver.findElement(By.id("u_0_1")).click();

		String CurrentUrl = webDriver.getCurrentUrl();
		System.out.println(CurrentUrl);
		if (!CurrentUrl.contains("developers")) {
			System.out.println("changing the URL");
			webDriver.navigate().to("https://developers.facebook.com/tools/explorer/");
		} else {
			System.out.println("correct URL exists so not changing");
		}
		//webDriver.findElement(By.xpath("(//span[text()='Tools'])[2]")).click();
		//new MyntActions(webDriver).moveToElement(wb).perform();
		//webDriver.findElement(By.xpath("(//span[text()='Graph API Explorer'])[2]")).click();
		webDriver.findElement(By.id("access_token")).clear();
		webDriver.findElement(By.id("get_access_token")).click();
		webDriver.findElement(By.xpath("//button[text()='Get Access Token']")).click();
		String accessToken = webDriver.findElement(By.xpath("(//td[@class='full_width_cell'])[1]")).getText();
		System.out.println("access tocken---->>" + accessToken);
	}

}
