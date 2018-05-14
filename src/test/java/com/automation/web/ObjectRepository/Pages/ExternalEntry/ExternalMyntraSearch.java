package com.automation.web.ObjectRepository.Pages.ExternalEntry;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.automation.core.web.GenericMethods;


public class ExternalMyntraSearch {

	public WebElement element = null;
	public List<WebElement> elements ;
	WebDriver driver;
	GenericMethods objGenericMethods;
	/**
	 * @param driver
	 */
	public ExternalMyntraSearch(WebDriver driver) {
		this.driver = driver;
		objGenericMethods = new GenericMethods(this.driver);
	}
	
	public void OpenGoogle()
	{
		
	}
}
