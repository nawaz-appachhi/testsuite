package com.automation.web.ObjectRepository.Pages.AddressPage;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.automation.core.web.GenericMethods;
import com.automation.web.ObjectRepository.Pages.Header.HeaderPageObject;

/**
 * @author 300019227 - Anu
 *
 */
public class ProfileAddressPage{

	public WebElement element = null;
	public List<WebElement> elements;
	GenericMethods objGenericMethods;
	HeaderPageObject objHeaderPageObject;

	public ProfileAddressPage(WebDriver driver) {
		super();
		PageFactory.initElements(driver, this);
		objGenericMethods = new GenericMethods(driver);
		objHeaderPageObject = new HeaderPageObject(driver);

	}

	//@FindAll(@FindBy(xpath = "//div[@class='addressAccordian-address']"))
	@FindAll(@FindBy(xpath = "//div[@class='address-description']"))
	public List<WebElement> AllSavedAddresses;
	/**
	 * RemoveAllSavedAddresses
	 * modified by -shivaprasad
	 * old xpath for production environment-.//*[text()=' REMOVE ']
	 * new xpath for sfqa env-//div[@class='header-sprite mlogo']
	 */
	@FindBy(xpath = "//a[@class='delete remove-address']")
	public WebElement RemoveAllSavedAddresses;

	@FindBy(xpath = "//div[text()='Delete']")
	public WebElement DeleteSelectedAddress;

	//@FindBy(xpath = "//div[text()=' Save your addresses now ']")
	@FindBy(xpath = "//div[@class='blankmessage']")
	public WebElement NoSavedAddress;
	
	@FindBy(xpath="//div[@class='header-sprite mlogo']")
	public WebElement MyntraLogo;
	
	public WebElement getMyntraLogo() {
		return MyntraLogo;
	}

	public List<WebElement> getAllSavedAddresses() {
		objGenericMethods.CheckWebElementsListFound(AllSavedAddresses, "AllSavedAddresses");
		return AllSavedAddresses;
	}

	public WebElement getRemoveAllSavedAddresses() {
		objGenericMethods.CheckWebElementFound(RemoveAllSavedAddresses, "RemoveAllSavedAddresses");
		return RemoveAllSavedAddresses;
	}

	public WebElement getDeleteSelectedAddress() {
		objGenericMethods.CheckWebElementFound(DeleteSelectedAddress, "DeleteSelectedAddress");
		return DeleteSelectedAddress;
	}

	public WebElement getNoSavedAddress() {
		objGenericMethods.CheckWebElementFound(NoSavedAddress, "No Saved Address");
		return NoSavedAddress;
	}

	/**
	 * 
	 * ModifiedBy : Neeraj
	 * Description: Removed Thread.sleep
	 */
	public void RemoveAllSavedAddressesFromAddressPage() {
		List<WebElement> links = getAllSavedAddresses();
		
		for(WebElement link : links)
		{
			objGenericMethods.takeSnapShot();
			getRemoveAllSavedAddresses().click();
			objGenericMethods.ReportClickEvent("Removed Addresses");
			objGenericMethods.takeSnapShot();
			objGenericMethods.ReportClickEvent("Delete Selected Address");
		}
		
	}

	public boolean IsAddressEmpty() {
		try {
			getNoSavedAddress().getText();
			return true;
		} catch (Exception e) {
			System.out.println("Saved Addresses are there");
			return false;
		}
	}
	
	public void ClickMyntraLogoFromAddresspage(){
		getMyntraLogo().click();
	}
}
