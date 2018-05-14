package com.automation.web.ObjectRepository.Pages.BreadCrumbsMenu;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;



public class BreadCrumbsMenu  {

	List<WebElement> lstBreadCrumbsMenu = null;
	public WebElement Element = null;
	WebDriver driver;
	
	public BreadCrumbsMenu(WebDriver driver)
	{
		this.driver = driver;
	}
	public List<WebElement> getBreadCrumsMenu()
	{
		return driver.findElements(By.xpath("//*[@class='_218svP']/a"));
	}
	
	public WebElement getMenuByName(String Name)
	{
		List<WebElement> menuItems = getBreadCrumsMenu(); 
		for(WebElement menu : menuItems)
		{
			if(menu.getText().equals(Name))
			{
				Element = menu;
				break;
			}			
		}
		return Element;
	}
}
