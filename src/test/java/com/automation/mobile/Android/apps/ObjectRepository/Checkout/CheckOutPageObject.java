package com.automation.mobile.Android.apps.ObjectRepository.Checkout;

import java.io.IOException;
import java.time.Duration;
import java.util.Iterator;
import java.util.List;

import javax.naming.directory.ModificationItem;

import org.apache.tools.ant.taskdefs.Sync.MyCopy;
import org.ini4j.InvalidFileFormatException;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.automation.core.mobile.Android.AndroidGenericMethods;
import com.automation.mobile.Android.apps.ObjectRepository.Hamburger.HamburgerPageObject;

import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.AndroidKeyCode;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

/**
 * Modified By Aishurya: WebView Object identification added
 *
 */
public class CheckOutPageObject {
	public AndroidDriver<AndroidElement> aDriver;
	AndroidGenericMethods objAndroidGenericMethods;
	HamburgerPageObject objHamburgerPageObject;

	public CheckOutPageObject(AndroidDriver<AndroidElement> aDriver) {
		this.aDriver = aDriver;
		PageFactory.initElements(new AppiumFieldDecorator(aDriver), this);
		objAndroidGenericMethods = new AndroidGenericMethods(aDriver);
		objHamburgerPageObject = new HamburgerPageObject(aDriver);
		// String pageName= "CheckOutPageObjec";

	}

	/**
	 * Name: Vinay
	 * 
	 * Description: This xpath help us to click on "Continue" button on CheckOut
	 * Page
	 */
	@FindBy(xpath="//button[text()='continue']")
//	@FindBy(xpath = "//android.widget.Button[@content-desc='CONTINUE' or @text='CONTINUE']")
	public AndroidElement clickContinue;
	/**
	 * Name: Vinay
	 * 
	 * Description: This xapth help us to enter the PinCode on fill address page
	 */
	@FindBy(xpath = "//input[@id='pincode']")
	public AndroidElement pinCodetxt;
	/**
	 * Name: Vinay
	 * 
	 * Description: This xapth help us to click on "choose" button in Edit addrss
	 * Page.
	 */

	@FindBy(xpath = "//button[@class='choose-locality link-tappable']")
	public AndroidElement choosebtn;
	/**
	 * Name: Vinay
	 * 
	 * Description: This xapth help us to enter the Locality which will display
	 * after click on Choose button.
	 */
	@FindBy(xpath = "//input[@id='locality']")
	public AndroidElement enterLocality;
	/**
	 * Name: Vinay
	 * 
	 * Description: This xapth help us to click on the "Save" button on address
	 * screen which will dsiplay after click on choose button.
	 */
	@FindBy(xpath = "//button[@class='close-locality']")
	public AndroidElement localitySaveButton;

	/**
	 * Name: Vinay
	 * 
	 * Description: This xapth help us to enter the "Name" in name text field on
	 * edit address page
	 */
	@FindBy(xpath = "//input[@id='name']")
	public AndroidElement nametxt;
	
	@FindBy(xpath = "//label[@class='name-label']")
	public AndroidElement nametxtHeader;
	/**
	 * Name: Vinay
	 * 
	 * Description: This xpath help us to enter the Address on fill address page
	 * 
	 * Modified by : Sneha
	 * old Xpath ://android.widget.EditText[@text='Address*' or @content-desc='Address*' or @index='6']"
	 */
	@FindBy(id = "address")
	public AndroidElement addresstxt;
	/**
	 * Name: Vinay
	 * 
	 * Description: This xpath help us to enter the "Mobile Number on fill address
	 * page
	 * Modified by vinay content-desc added
	 */
	@FindBy(xpath = "//input[@id='mobile']")
	public AndroidElement mobileNumbertxt;

	/**
	 * Name: Vinay
	 * 
	 * Description: This xpath help us to click on the "Home Radio Button" button on
	 * fill address page
	 */
	@FindBy(xpath = "//div[@id='home']")
	public AndroidElement homeRadiobtn;
	/**
	 * Name: Vinay
	 * 
	 * Description: This xpath help us to click on the "Office Radio Button" on fill
	 * address page
	 */
	@FindBy(xpath = "//div[@id='ofc']")
	public AndroidElement officeRadiobtn;

	/**
	 * Name: Vinay
	 * 
	 * Description: This xapth help us to click on the "SAVE" button on fill address
	 * page
	 */
	@FindBy(xpath = "//button[@class='green-button submit clickable']")
	public AndroidElement saveAddressbtn;

	/**
	 * Name: Vinay
	 * 
	 * Description: This xapth help us to click on the "EditOrChange Address button"
	 * on fill address page which will display after add the address
	 * 
	 * modified by sneha
	 * old xpath -"//android.view.View[(starts-with(@content-desc,'Edit/Change'))]
	 */
	
	@FindBy(xpath = "//span[contains(text(),'Edit/Change')]")
//	@FindBy(xpath = "//android.view.View[(starts-with(@content-desc,'Edit/Change')) or (starts-with(@text,'Edit/Change'))]")
	public AndroidElement editOrchangeAddressbtn;
	/**
	 * Name: Vinay
	 * 
	 * Description: This xapth help us to click on the "Remove" button to remove the
	 * address
	 */
	
	@FindBy(xpath = "//button[contains(@class,'delete')]")
//	@FindBy(xpath = "//android.widget.Button[@content-desc='REMOVE' or @index='0']")
	public AndroidElement removeAddressbtn;
	/**
	 * Name: Vinay
	 * 
	 * Description: This xapth help us to click on the "Edit" button to edit the
	 * address
	 */
	@FindBy(xpath = "//button[contains(@class,'edit')]")
//	@FindBy(xpath = "//android.widget.Button[@content-desc='EDIT' or @index='1']")
	public AndroidElement editAddressbtn;
	/**
	 * Name: Vinay
	 * 
	 * Description: This xapth help us to click on the "ADDNew Address" button on
	 * address page
	 */
	@FindBy(className = "add-new-address")
//	@FindBy(xpath = "//android.view.View[@text='Add New Address' or @content-desc='Add New Address']")
	public AndroidElement addNewAddressbtn;
	/**
	 * Name: Vinay
	 * 
	 * Description: This xapth help us to click on the "Continue" button on check
	 * out page
	 */
	@FindBy(xpath = "//button[text()='continue']")
	public AndroidElement continueOrderbtn;
	/**
	 * Name: Vinay
	 * 
	 * Description: This xapth help us to click on the "PayNow" button on Payment
	 * page
	 */
	@FindBy(xpath = "//*[@class='button-sticky-buttom']/button[2]")
//	@FindBy(xpath = "//android.widget.Button[@content-desc='PAY NOW']")
	public AndroidElement paynowbtn;

	@FindBy(className = "android.widget.LinearLayout")
	public List<AndroidElement> listOfCartPage;
	/**
	 * this object help to click on confirm button while editing the address
	 */
	@FindBy(xpath="//button[contains(text(),'Confirm')]")
	public AndroidElement addressConfirmbtn;

	/************* GetterMtehods ******************/

	public AndroidElement getAddressConfirmbtn() {
		return addressConfirmbtn;
	}

	public AndroidElement getSaveurAddressMsg() {
		return saveurAddressMsg;
	}

	/**
	 * All the Getter methods written below for all the above element
	 * 
	 */

	public AndroidElement getPinCodetxt() {
		objAndroidGenericMethods.CheckAndroidElementFoundForWebView(pinCodetxt, "pinCodetxt");
		return pinCodetxt;
	}

	public AndroidElement getchoosebtn() {
		objAndroidGenericMethods.CheckAndroidElementFoundForWebView(choosebtn, "choosebtn");
		return choosebtn;
	}

	public AndroidElement getNametxt() throws InterruptedException {
		objAndroidGenericMethods.CheckAndroidElementFoundForWebView(nametxt, "nametxt");
		return nametxt;
	}

	public AndroidElement getAddresstxt() throws InterruptedException {
		objAndroidGenericMethods.CheckAndroidElementFoundForWebView(addresstxt, "addresstxt");
		return addresstxt;
	}

	public AndroidElement getMobileNumbertxt() throws InterruptedException {
		objAndroidGenericMethods.CheckAndroidElementFoundForWebView(mobileNumbertxt, "mobileNumbertxt");
		return mobileNumbertxt;
	}

	public AndroidElement getHomeRadiobtn() {
		objAndroidGenericMethods.CheckAndroidElementFoundForWebView(homeRadiobtn, "homeRadiobtn");
		return homeRadiobtn;
	}

	public AndroidElement getOfficeRadiobtn() {
		objAndroidGenericMethods.CheckAndroidElementFoundForWebView(officeRadiobtn, "officeRadiobtn");
		return officeRadiobtn;
	}

	public AndroidElement getSaveAddressbtn() throws InterruptedException {
		objAndroidGenericMethods.CheckAndroidElementFoundForWebView(saveAddressbtn, "saveAddressbtn");
		return saveAddressbtn;
	}

	public AndroidElement getEnterLocality() throws InterruptedException {
		objAndroidGenericMethods.CheckAndroidElementFoundForWebView(enterLocality, "enterLocality");
		return enterLocality;
	}

	public AndroidElement getLocalitySaveButton() {
		objAndroidGenericMethods.CheckAndroidElementFoundForWebView(localitySaveButton, "localitySaveButton");
		return localitySaveButton;
	}

	public AndroidElement getEditOrchangeAddressbtn() {
		objAndroidGenericMethods.CheckAndroidElementFoundForWebView(editOrchangeAddressbtn,"editOrchangeAddressbtn");
		return editOrchangeAddressbtn;
	}

	public AndroidElement getRemoveAddressbtn() {
		objAndroidGenericMethods.CheckAndroidElementFoundForWebView(removeAddressbtn, "RemoveAddressBtn");
		return removeAddressbtn;
	}

	public AndroidElement getEditAddressbtn() {
		objAndroidGenericMethods.CheckAndroidElementFoundForWebView(editAddressbtn, "EditAddressBtn");
		return editAddressbtn;
	}

	public AndroidElement getAddNewAddressbtn() {
		objAndroidGenericMethods.CheckAndroidElementFoundForWebView(addNewAddressbtn, "AddNewAddressBtn");
		return addNewAddressbtn;
	}

	public AndroidElement getContinueOrderbtn() {
		objAndroidGenericMethods.CheckAndroidElementFoundForWebView(continueOrderbtn, "ContinueOrderBtn");
		return continueOrderbtn;
	}

	public AndroidElement getPaynowbtn() {
		objAndroidGenericMethods.CheckAndroidElementFound(paynowbtn, "PayNowBtn");
		return paynowbtn;
	}

	/**
	 * This is genric method please dont change anything if you need anything please
	 * create another method this metnod looking for "AddNewAddress" button and then
	 * it will add the new address If "AddNewAddress" is not available then it will
	 * start filling the address
	 * 
	 * @author VInay ALERT: contact before making any modifications
	 * @throws IOException 
	 * @throws InvalidFileFormatException 
	 * 
	 */
	public void AddNewAddress() throws InterruptedException, InvalidFileFormatException, IOException {
		try {
			clickAddNewAddressbtn();
			fillAddressOffc();
		} catch (Exception e) {
			fillAddressOffc();
		}
	}

	public void clickOfficRadiobtn() {
		objAndroidGenericMethods.clickOnAndroidElement(getOfficeRadiobtn(), "OfficeRadioBtn");
	}

	/**
	 * This is genric method please dont change anything if you need anything please
	 * create another method
	 * it is switching into webview then performing all task
	 * 
	 * @author VInay ALERT: contact before making any modifications contact no:
	 *         8792604667
	 * @throws InterruptedException 
	 * @throws IOException 
	 * @throws InvalidFileFormatException 
	 * 
	 * 
	 */
	public void fillAddressOffc() throws InterruptedException, InvalidFileFormatException, IOException{
		getPinCodetxt().clear();
		getPinCodetxt().sendKeys(AndroidGenericMethods.getValueByKey("OBJECTREPO", "Pincode"));
		clickChoosebtn();
		getEnterLocality().clear();
		getEnterLocality().sendKeys(AndroidGenericMethods.getValueByKey("OBJECTREPO", "Locality"));
		clickLocalitySavebtn();
		getNametxt().clear();
		getNametxt().sendKeys(AndroidGenericMethods.getValueByKey("OBJECTREPO", "Name"));
		getAddresstxt().clear();
		getAddresstxt().sendKeys(AndroidGenericMethods.getValueByKey("OBJECTREPO", "Address"));
		aDriver.hideKeyboard();
		// objAndroidGenericMethods.scrollToText(aDriver, "Type of Address *");
		// objAndroidGenericMethods.bottomTopSwipe(2000);
		getMobileNumbertxt().clear();
		getMobileNumbertxt().sendKeys(AndroidGenericMethods.getValueByKey("OBJECTREPO", "MobileNumber"));
		getOfficeRadiobtn().click();
		clickAddressSave();
	}

	/**
	 * modified by sneha : added sleep
	 * 
	 * Moodified by vinay : method wsitch into webview
	 * @throws IOException 
	 * @throws InvalidFileFormatException 
	 */
	public void fillAddressHome() throws InterruptedException, InvalidFileFormatException, IOException {
		getPinCodetxt().clear();
		getPinCodetxt().sendKeys(AndroidGenericMethods.getValueByKey("OBJECTREPO", "Pincode"));
		clickChoosebtn();
		getEnterLocality().clear();
		getEnterLocality().sendKeys(AndroidGenericMethods.getValueByKey("OBJECTREPO", "Locality"));
		clickLocalitySavebtn();
		getNametxt().clear();
		getNametxt().sendKeys(AndroidGenericMethods.getValueByKey("OBJECTREPO", "Name"));
		getAddresstxt().clear();
		getAddresstxt().sendKeys(AndroidGenericMethods.getValueByKey("OBJECTREPO", "Address"));
		aDriver.hideKeyboard();
		// objAndroidGenericMethods.scrollToText(aDriver, "Type of Address *");
		// objAndroidGenericMethods.bottomTopSwipe(2000);
		getMobileNumbertxt().clear();
		getMobileNumbertxt().sendKeys(AndroidGenericMethods.getValueByKey("OBJECTREPO", "MobileNumber"));
		clickHomeRadioBtn();
		clickAddressSave();
	}

	public void clickOfficeRadio() {
		objAndroidGenericMethods.clickOnAndroidElement(getOfficeRadiobtn(), "OfficeRadiobtn");

	}

	public void clickChoosebtn() throws InterruptedException {
		objAndroidGenericMethods.clickOnAndroidElementforwebVIew(getchoosebtn(), "Choose Button");
	}

	public void clickEditOrchangeAddress() throws InterruptedException {
		objAndroidGenericMethods.clickOnAndroidElementforwebVIew(getEditOrchangeAddressbtn(), "EditAddressBtn");

	}

	public void clickEditAddress() throws InterruptedException {
		objAndroidGenericMethods.clickOnAndroidElementforwebVIew(getEditAddressbtn(), "EditAddressBtn");

	}

	public void clickOnRemoveAddress() throws InterruptedException {
		objAndroidGenericMethods.clickOnAndroidElementforwebVIew(getRemoveAddressbtn(), "RemoveAddressBtn");
	}

	
	/**
	 * This is genric method please dont change anything if you need anything please
	 * create another method This Mthod help us to check whether address is
	 * available or not. If address is already added then it will directly click on
	 * "continue" button and go forward and if address is not added then it will
	 * start filliing the addres and click on "continue" button
	 * 
	 * @author VInay ALERT: contact before making any modifications
	 * Modified by vinay : changed the method in an optimized way
	 * @throws IOException 
	 * @throws InvalidFileFormatException 
	 */
	public void CheckAddress() throws InterruptedException, InvalidFileFormatException, IOException {
		try { 
			getContinueOrderbtn().getText();
			System.out.println(getContinueOrderbtn().getText());
			
		} catch (Exception e) {
			fillAddressOffc();
		}
	}

	/**
	 * This is genric method please dont change anything if you need anything please
	 * create another method This methods use for remove the address so this method
	 * always looking for "EditOR Change" button then click on "REMOVE" Then again
	 * it will start fill the address
	 * 
	 * Modified by vinay : changed the method in an optimized way
	 * @throws IOException 
	 * @throws InvalidFileFormatException 
	 * 
	 */
	public void removeAndAddAddress() throws InterruptedException, InvalidFileFormatException, IOException {
		
		Boolean editAddr = false;
		try {
			editAddr = getEditOrchangeAddressbtn().isDisplayed();
		} catch (Exception e1) {
		}
		if (editAddr) {
			clickEditOrchangeAddress();
			clickOnRemoveAddress();
			fillAddressOffc();
		} else {
			fillAddressOffc();
		}

	}
	/**
	 * This method use for remove the address
	 *@author 300021282 VINAY
	 * @throws IOException 
	 * @throws InvalidFileFormatException 
	 */
	
	public void removeAddress() throws InterruptedException, InvalidFileFormatException, IOException {
		Boolean editAddr = false;
		try {
			editAddr = getEditOrchangeAddressbtn().isDisplayed();
		} catch (Exception e1) {
		}
		if (editAddr) {
			clickEditOrchangeAddress(); 
			clickOnRemoveAddress();
		} else {
			fillAddressOffc();
			clickEditOrchangeAddress();
			clickOnRemoveAddress();  
			
		}

	}

	public void enterpincode(String pinCodeValue) {
		getPinCodetxt().sendKeys(pinCodeValue);
	}
	/**
	 * This genric method help to edit address on checkout page
	 * @author Vinay:: changed the method in an optimized way
	 * @throws IOException 
	 * @throws InvalidFileFormatException 
	 */
	public void editAddress() throws InterruptedException, InvalidFileFormatException, IOException {
		
		try {  
			System.out.println("came in try block");
			clickEditOrchangeAddress();
			System.out.println("clicked editaddrschange");
			clickEditAddress();
			System.out.println("in try for edit");
			Thread.sleep(4000);
			getNametxt().clear();
			getNametxt().sendKeys(AndroidGenericMethods.getValueByKey("OBJECTREPO", "Name"));
			getAddresstxt().clear();
			getAddresstxt().sendKeys(AndroidGenericMethods.getValueByKey("OBJECTREPO", "Address"));
			aDriver.hideKeyboard();
			clickAddressSave();		
		} catch (Exception e1) {
			fillAddressOffc();  
		} 
		
	}
	
	public void addAddressForHome() throws InvalidFileFormatException, InterruptedException, IOException{
		try {
			fillAddressHome();
		} catch (Exception e) {
			CheckAddress();
		}

	}
	

	public void enterpincode() {
		// getPinCodetxt().sendKeys(pinCodeValue);
		getPinCodetxt().sendKeys("560098");
	}

	public void clickAddNewAddressbtn() throws InterruptedException {
		objAndroidGenericMethods.clickOnAndroidElementforwebVIew(getAddNewAddressbtn(), "Add Address");
	}

	public void clickAddressSave() throws InterruptedException {
		objAndroidGenericMethods.clickOnAndroidElementforwebVIew(getSaveAddressbtn(), "SaveButton");

	}
	
	public void clickConfirmAddress() {
		objAndroidGenericMethods.swithchInToWebview();
		objAndroidGenericMethods.clickOnAndroidElement(getAddressConfirmbtn(), "Confirm Address Button");
		objAndroidGenericMethods.switchInToNativeView();
	}
	public void clickLocalitySavebtn() throws InterruptedException {
		objAndroidGenericMethods.clickOnAndroidElementforwebVIew(getLocalitySaveButton(), "localitySaveButton");
	}
	public void clickHomeRadioBtn() throws InterruptedException{
		objAndroidGenericMethods.clickOnAndroidElementforwebVIew(getHomeRadiobtn(), "homeRadiobtn");
	}

	// ********************* Android Assertion element************************/

	@FindBy(xpath = "//android.widget.TextView[@text='ADDRESS']")
	public AndroidElement addressHeader;

	public AndroidElement getAddressHeader() {
		objAndroidGenericMethods.CheckAndroidElementFound(addressHeader, "addressHeader");
		return addressHeader;
	}

	public void verifyUserAddress() {
		objAndroidGenericMethods.VerifyTwoString(getAddressHeader(), "ADDRESS");
	}

	public void clickContinue() throws InterruptedException {
		objAndroidGenericMethods.clickOnAndroidElementforwebVIew(getContinueOrderbtn(), "Continue Button");
	//	Thread.sleep(2000);
	}

	public List<AndroidElement> getListOfElements() throws InterruptedException {

		//Thread.sleep(2000);
		System.err.println("List of Elements found " + listOfCartPage.size());
		for (Iterator<AndroidElement> iterator = listOfCartPage.iterator(); iterator.hasNext();) {
			WebElement WebElement = (WebElement) iterator.next();
			System.err.println("List of Elements   --> " + WebElement.getText());
			System.err.println("List of Elements   --> " + WebElement.getTagName());
		}
	//	Thread.sleep(1000);
		return listOfCartPage;

	}

	// **********************RESET ADDRESS***************//
	/**
	 * object for remove button displayed for adjacent address in address page;
	 */
	@FindBy(xpath = "//*[@text='REMOVE']")
	public AndroidElement addressRemove;

	/**
	 * object for delete button displayed after tapped on remove address;
	 */
	@FindBy(xpath = "//*[@text='DELETE']")
	public AndroidElement addressDelete;

	/**
	 * object to identify the message displayed for empty address condition;
	 */
	@FindBy(xpath = "//*[@text='SAVE YOUR ADDRESSES NOW']")
	public AndroidElement saveurAddressMsg;

	/**
	 * object to identify the message displayed for empty address condition;
	 */
	@FindBy(xpath = "//*[@resource-id='com.myntra.android:id/toolbar']/android.widget.ImageButton")
	public AndroidElement closeAddressBtn;

	/**
	 * Method to check whether no address message is displayed;
	 * 
	 * @author 300021278 -Rakesh
	 * @throws InterruptedException
	 */
	public boolean saveurAddressMsgDisplayed() {
		try {
			saveurAddressMsg.isDisplayed();
			return true;
		} catch (Exception e) {
			System.out.println("saveurAddressMsg is not displayed");
			// e.printStackTrace();
			return false;
		}
	}

	/**
	 * Method to check for the condition whether remove button is displayed in
	 * address page
	 * 
	 * @author 300021278 -Rakesh
	 * @throws InterruptedException
	 */
	public boolean removeBtnDisplayed() {
		try {
			addressRemove.isDisplayed();
			return true;
		} catch (Exception e) {
			System.out.println("remove is not displayed");
			// e.printStackTrace();
			return false;
		}
	}

	/**
	 * Method to delete address in address page
	 * 
	 * @author 300021278 -Rakesh
	 * @throws InterruptedException
	 * 
	 * modified by : Sneha, while condion changed
	 */
	public void RemoveAllSavedAddressesFromAddressPage() throws InterruptedException {
		while (!saveurAddressMsgDisplayed()) {
			addressRemove.click();
		//	Thread.sleep(2000);
			addressDelete.click();
		}
	}

	/**
	 * Method to reset address
	 * 
	 * @author 300021278 -Rakesh
	 * @throws InterruptedException
	 */
	public void resetAddress() throws InterruptedException {
		/*try {
			objHamburgerPageObject.clickHamburgerbtn();
			objAndroidGenericMethods.scrolltoElementAndClick( objHamburgerPageObject.getmyAccountBtn(), 2000);
			objAndroidGenericMethods.scrolltoElementAndClick( objHamburgerPageObject.getmyAccountAdddresses(), 2000);
			System.out.println("clicked on address");
			if (saveurAddressMsgDisplayed()) { 
				closeAddressBtn.click();
				objHamburgerPageObject.myAccountClosebtn.click();
				Thread.sleep(3000);
				aDriver.pressKeyCode(AndroidKeyCode.BACK);
			} else {
				RemoveAllSavedAddressesFromAddressPage();
			//	Thread.sleep(1000);
				closeAddressBtn.click();
				objHamburgerPageObject.myAccountClosebtn.click();
				Thread.sleep(3000);
				aDriver.pressKeyCode(AndroidKeyCode.BACK);
			}
		} catch (InterruptedException e) {
			e.printStackTrace(); 
		} */
	}

}
