package com.automation.mobile.Android.MobileWeb.ObjectRepository.AddressPageObjects;

import java.io.IOException;
import java.util.List;

import org.ini4j.InvalidFileFormatException;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.automation.core.mobile.Android.AndroidGenericMethods;
import com.automation.mobile.Android.MobileWeb.ObjectRepository.HomeObjects.HamburgerPageObjects;
import com.automation.mobile.Android.MobileWeb.ObjectRepository.HomeObjects.HomePageObjects;
import com.automation.mobile.Android.MobileWeb.ObjectRepository.HomeObjects.MenuPageObjects;
import com.automation.mobile.Android.MobileWeb.ObjectRepository.WishList.WishListPageObject;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

/**
 * Add new address - Monika
 */

public class AddressPageObjects {

	AndroidDriver<AndroidElement> aDriver;
	AndroidGenericMethods objAndroidGenericMethods;
	HomePageObjects objHomepageObjects;
	MenuPageObjects objMenupageObjects;
	WishListPageObject objwishlistPageObject;
	HamburgerPageObjects objHamburgerpageObjects;
	Edit_ChangeButtonPageObjects objEdit_ChangeButtonPageObjects;

	public AddressPageObjects(AndroidDriver<AndroidElement> aDriver) {
		PageFactory.initElements(new AppiumFieldDecorator(aDriver), this);
		objAndroidGenericMethods = new AndroidGenericMethods(aDriver);
		objHomepageObjects = new HomePageObjects(aDriver);
		objMenupageObjects = new MenuPageObjects(aDriver);
		objwishlistPageObject = new WishListPageObject(aDriver);
		objHamburgerpageObjects = new HamburgerPageObjects(aDriver);
		objEdit_ChangeButtonPageObjects = new Edit_ChangeButtonPageObjects(aDriver);
	}

	@FindBy(id = "pincode")
	public AndroidElement PincodeAddress;

	@FindBy(xpath = ".//*[text()='Choose']")
	public AndroidElement ChooseTown;

	@FindBy(xpath = ".//div[@class=\"suggestions\"]")
	public List<AndroidElement> ChooseByTown;

	@FindBy(id = "name")
	public AndroidElement Name;

	@FindBy(xpath = ".//input[@id='name']")
	public AndroidElement Editname;

	@FindBy(xpath = ".//textarea[@id='address']")
	public AndroidElement Address;

	@FindBy(xpath = ".//input[@id='mobile']")
	public AndroidElement MobileNumber;

	@FindBy(xpath = ".//div[@value='Office']")
	public AndroidElement OfficeCommercial;

	@FindBy(id = "home")
	public AndroidElement HomeRadioBtn;

	public AndroidElement getHomeRadioBtn() {
		return HomeRadioBtn;
	}

	@FindBy(xpath = "//div[@id='home']")
	public AndroidElement Home;

	@FindBy(xpath = ".//div[@value='sat']")
	public AndroidElement OpenOnSaturdays;

	/**
	 * Modified: Aishurya: xpath for save address
	 */
	@FindBy(xpath = "//button[contains(@class,'green-button') and text()='SAVE']")
	//div[@class='white-row buttons']/button[contains(text(),'SAVE')]
	// span[@class='tappable']/button[contains(text(),'Edit/Change']
	public AndroidElement SaveAddress;
	
	@FindBy(xpath = "//input[@id='locality']")
	public AndroidElement EnterLocality;
	
	@FindBy(xpath = "//span[text()='Add new address']")
	public AndroidElement AddNewAddress;
	
	@FindBy(xpath = ".//*[text()=' REMOVE ']")
	public AndroidElement RemoveAllSavedAddresses;

	@FindBy(xpath = "//div[text()='Delete']")
	public AndroidElement DeleteSelectedAddress;
	
	@FindBy(xpath = "//div[text()=' Save your addresses now ']")
	public AndroidElement NoSavedAddress;

	@FindBy(xpath = "//span[text()='Addresses']")
	public AndroidElement profileAddresslink;

	@FindAll(@FindBy(xpath = "//div[@class='addressAccordian-address']"))
	public List<AndroidElement> AllSavedAddresses;

	public List<AndroidElement> getAllSavedAddresses() {
		objAndroidGenericMethods.CheckAndroidElementsListFoundMWeb(AllSavedAddresses, "AllSavedAddresses");
		return AllSavedAddresses;
	}

	public AndroidElement getNoSavedAddress() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(NoSavedAddress, "NoSavedAddress");
		return NoSavedAddress;
	}

	public AndroidElement getRemoveAllSavedAddresses() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(RemoveAllSavedAddresses, "RemoveAllSavedAddresses");
		return RemoveAllSavedAddresses;
	}

	public AndroidElement getDeleteSelectedAddress() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(DeleteSelectedAddress, "DeleteSelectedAddress");
		return DeleteSelectedAddress;
	}

	@FindBy(xpath = "//button[@class='close-locality' ]")
	public AndroidElement SaveLocality;

	public AndroidElement getHome() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(Home, " Home");
		return Home;
	}

	public AndroidElement getSaveLocality() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(SaveLocality, " SaveLocality");
		return SaveLocality;
	}

	public AndroidElement getEnterLocality() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(EnterLocality, "EnterLocality");
		return EnterLocality;
	}

	public AndroidElement getAddNewAddress() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(AddNewAddress, "AddNewAddress");
		return AddNewAddress;
	}

	public AndroidElement getPincodeAddress() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(PincodeAddress, "PincodeAddress");
		PincodeAddress.clear();
		return PincodeAddress;
	}

	public AndroidElement getChooseTown() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(ChooseTown, "ChooseTown");
		return ChooseTown;
	}

	public List<AndroidElement> getChooseByTown() {
		objAndroidGenericMethods.CheckAndroidElementsListFoundMWeb(ChooseByTown, "ChooseByTown");
		return ChooseByTown;
	}

	public AndroidElement getName() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(Name, "Name");
		return Name;
	}

	public AndroidElement getEditname() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(Editname, "Editname");
		return Editname;
	}

	public AndroidElement getAddress() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(Address, "Address");
		return Address;
	}

	public AndroidElement getMobileNumber() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(MobileNumber, "MobileNumber");
		return MobileNumber;
	}

	public AndroidElement getOfficeCommercial() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(OfficeCommercial, "OfficeCommercial");
		return OfficeCommercial;
	}

	public AndroidElement getopenOnSaturdays() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(OpenOnSaturdays, "OpenOnSaturdays");
		return OpenOnSaturdays;
	}

	public AndroidElement getSaveAddress() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(SaveAddress, "SaveAddress");
		return SaveAddress;
	}

	public AndroidElement getProfileAddresslink() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(profileAddresslink, "profileAddresslink");
		return profileAddresslink;
	}

	public void enterPincone(String pincode) {
		PincodeAddress.sendKeys(pincode);
	}

	public void enterPincone() {
		PincodeAddress.sendKeys("560068");
	}

	/**
	 * Modified By:Aishurya:Changed string parameter,which is being used in reporing
	 */
	public void ClickOnPincodeAddress()  {
		objAndroidGenericMethods.clickOnAndroidElement(getPincodeAddress(), "Pincode");
	}

	/**
	 * Modified By:Aishurya:Changed string parameter,which is being used in reporing
	 */
	public void clickOnSaveAddress(){
		// SaveAddress.click();
		objAndroidGenericMethods.clickOnAndroidElement(getSaveAddress(), "Save addres");
	}

	/**
	 * Modified By:Aishurya:Changed string parameter,which is being used in reporing
	 */
	public void clickOnHome() {
		objAndroidGenericMethods.clickOnAndroidElement(getHome(), "HomeLogo");

	}

	public void clickOnSaveLocality() {
		getSaveLocality().click();
	}

	public void clickOnChoose() {
		getChooseTown().click();
	}

	public void EnterLocality() {
		// objAndroidGenericMethods.clickOnAndroidElement(getEnterLocality(),"EnterLocality");
		EnterLocality.sendKeys("Madiwala");

	}

	public void selectTownLocality(String town) {
		List<AndroidElement> selectTownLocalityList = getChooseByTown();
		for (AndroidElement townLocaity : selectTownLocalityList) {
			AndroidElement button = (AndroidElement) townLocaity.findElement(By.tagName("button"));
			String townString = button.getText();
			if (townString.contains(town)) {
				button.click();
				break;
			}
		}
	}

	@FindBy(xpath = "(//div[@class='bd']/button)[1]")
	public AndroidElement localityfirstchoice;

	/**
	 * Modified By:Aishurya:Changed string parameter,which is being used in reporing
	 */
	public void clicklocalityfirstchoice() {
		objAndroidGenericMethods.clickOnAndroidElement(localityfirstchoice, "LocalityFirstChoice");
	}

	/**
	 * Modified By:Aishurya:Changed string parameter,which is being used in reporing
	 */
	public void entername(String name) {
		objAndroidGenericMethods.clickOnAndroidElement(getName(), "Name");
		getName().sendKeys(name);
	}

	/**
	 * Modified By:Aishurya:Changed string parameter,which is being used in reporing
	 */
	public void enterEditName(String name) {
		objAndroidGenericMethods.clickOnAndroidElement(getEditname(), "Edit Name");
		getEditname().clear();
		getEditname().sendKeys(name);
	}

	/**
	 * Modified By:Aishurya:Changed string parameter,which is being used in reporing
	 */
	public void enteraddress(String address) {
		objAndroidGenericMethods.clickOnAndroidElement(getAddress(), "Address");
		getAddress().sendKeys(address);
	}

	/**
	 * Modified By:Aishurya:Changed string parameter,which is being used in reporing
	 */
	public void enterMobileNumber(String mobile) {
		objAndroidGenericMethods.clickOnAndroidElement(getMobileNumber(), "Mobile number");
		getMobileNumber().sendKeys(mobile);
	}

	/**
	 * Modified By:Aishurya:Changed string parameter,which is being used in reporing
	 */
	public void clickOnOfficeCommercial() {
		objAndroidGenericMethods.clickOnAndroidElement(getOfficeCommercial(), "Office/commercial");
	}

	/**
	 * Modified By:Aishurya:Changed string parameter,which is being used in reporing
	 */
	public void clickOnOpenOnSaturdays() {
		objAndroidGenericMethods.clickOnAndroidElement(getopenOnSaturdays(), "Opens on saturday RadioBtn");
	}

	/**
	 * Modified By:Aishurya:Changed string parameter,which is being used in reporing
	 */
	public void clickOnAddNewAddress() {
		objAndroidGenericMethods.clickOnAndroidElement(getAddNewAddress(), "Opnes on sunday RadioBtn");
	}

	/**
	 * This method is to RemoveAddress
	 * 
	 * @author 300019225 Amba;
	 */

	public void RemoveandAddNewAddress() {
		ClickOnPincodeAddress();
		enterPincone();
		clickOnChoose();
		clicklocalityfirstchoice();
		selectTownLocality("Madiwala");
		clickOnChoose();
		EnterLocality();
		clickOnSaveLocality();
		entername("abc");
		enteraddress("11/5 Ayapa Temple");
		enterMobileNumber("9977198014");
		clickOnOfficeCommercial();
		clickOnOpenOnSaturdays();
		objAndroidGenericMethods.scrollPage();
		clickOnSaveAddress();
	}

	@FindBy(xpath = "//div[text()='Delivery']")
	public AndroidElement addressHeader;

	public AndroidElement getAddressHeader() {
		return addressHeader;
	}

	@FindBy(className = "address-content")
	public AndroidElement addressAdded;

	/**
	 * Modified By:Aishurya:Changed string parameter,which is being used in reporing
	 */
	public void clickOnProfileAddresslink() {
		objAndroidGenericMethods.clickOnAndroidElement(getProfileAddresslink(),
				"Address link under my account");
	}

	/**
	 * This method is to click on 'Home' radio button
	 * @author 300019227 Anu;
	 * Modified By:Aishurya:Changed string parameter,which is being used in reporing
	 */
	public void clickOnHomeRadioBtn() {
		objAndroidGenericMethods.clickOnAndroidElement(getHomeRadioBtn(), "Home Radio button");
	}

	/**
	 * This method is to reset address page
	 * 
	 * @author 300019227 Anu;
	 */

	public void RemoveAllSavedAddressesFromAddressPage() throws InterruptedException {
		List<AndroidElement> links = getAllSavedAddresses();
		for (int i = 0; i < links.size(); i++) {
			getRemoveAllSavedAddresses().click();
			getDeleteSelectedAddress().click();
		}
	}

	public AndroidElement getAddressAdded() {
		objAndroidGenericMethods.CheckAndroidElementFoundMWeb(addressAdded, "addressAdded");
		return addressAdded;
	}

	public void VerifyAddressAdded() {
		objAndroidGenericMethods.VerifyStringShouldNotEmpty(getAddressAdded(), "Address Added");
	}

	public void verifyUserAddress() {
		objAndroidGenericMethods.VerifyTwoString(getAddressHeader(), "Delivery");

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

	public void resetAddress() {
		try {
			// Empty Address Page
			objHomepageObjects.clickOnHamburgerButton();
			objHamburgerpageObjects.clickOnMyAccount();
			clickOnProfileAddresslink();
			System.out.println("clicked on address");

			if (IsAddressEmpty()) {
				objwishlistPageObject.clickOnMyntraLogo();
			} else {
				RemoveAllSavedAddressesFromAddressPage();
				objwishlistPageObject.clickOnMyntraLogo();
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * method to fill address; input should be given from ini file
	 * @author 300021278 -Rakesh
	 * @throws InvalidFileFormatException
	 * @throws IOException
	 */
	public void fillAddressOffice() throws InvalidFileFormatException, IOException  {
		ClickOnPincodeAddress();
		enterPincone(objAndroidGenericMethods.getValueByKeyWeb("ADDRESSREPO", "Pincode"));
		clickOnChoose();
		selectTownLocality(objAndroidGenericMethods.getValueByKeyWeb("ADDRESSREPO", "Locality"));
		entername(objAndroidGenericMethods.getValueByKeyWeb("ADDRESSREPO", "Name"));
		enteraddress(objAndroidGenericMethods.getValueByKeyWeb("ADDRESSREPO", "Address"));
		enterMobileNumber(objAndroidGenericMethods.getValueByKeyWeb("ADDRESSREPO", "MobileNumber"));
		objAndroidGenericMethods.scrollPage();
		clickOnOfficeCommercial();
		clickOnOpenOnSaturdays();
		clickOnSaveAddress();
	}
	/**
	 * method to edit address; input should be given from ini file
	 * @author 300021278 -Rakesh
	 * @throws InvalidFileFormatException
	 * @throws IOException
	 */
	public void editAddress() throws InvalidFileFormatException, IOException {
		try {
			if (objEdit_ChangeButtonPageObjects.EditChangeButton.isDisplayed()) {
			System.out.println("Address is saved already");
			objEdit_ChangeButtonPageObjects.clickOnEditChangeButton();
			objEdit_ChangeButtonPageObjects.clickOnEDITButton();
			enteraddress(objAndroidGenericMethods.getValueByKeyWeb("ADDRESSREPO", "Address2"));
			clickOnSaveAddress();
			}
		} catch (Exception e) {
			System.out.println("Adding the Address;");
			fillAddressOffice();
			objEdit_ChangeButtonPageObjects.clickOnEditChangeButton();
			objEdit_ChangeButtonPageObjects.clickOnEDITButton();
			enteraddress(objAndroidGenericMethods.getValueByKeyWeb("ADDRESSREPO", "Address2"));
			clickOnSaveAddress();
		}
	}
}
